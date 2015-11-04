package mnfreportapp.view.servlet;

import java.io.IOException;
import java.io.OutputStream;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import oracle.jbo.domain.Timestamp;

/* @WebServlet(name = "MnfReportAppServlet", urlPatterns = { "/mnfreportappservlet" }) */
public class MnfReportAppServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String link = request.getParameter("link");
        String reportName = null;
        Integer fcType = null;
         try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/MNFDS");
            conn = ds.getConnection();


            ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

            rs = ps.executeQuery();
            String path = null;
            while (rs.next()) {
                path = rs.getString(1);
            }
            if (link.equalsIgnoreCase("PortalLink") && request.getParameter("fileName") != null) {
                          path = path + "Portal/";
                      } else {
                          path = path + "MNF/";
                      }
//            path = path + "MNF/";
System.out.println("path after define--"+path);
            if(request.getParameter("bdgMthd")!=null){
                if((request.getParameter("bdgMthd").toString()==null)||request.getParameter("bdgMthd").toString().equals("")){
                    fcType = null;
                    System.out.println("inside fctype null");
                }
                else
                fcType = Integer.parseInt(request.getParameter("bdgMthd").toString());
                
            }

            if (link != null) {
                if (link.equalsIgnoreCase("bomExp")) {
                    reportName = "BOM_Explosion";
                } else if (link.equalsIgnoreCase("bomRev")) {
                    reportName = "BOM_Revision_History";
                } else if (link.equalsIgnoreCase("pdoPrint")) {
                    reportName = "Production_Order_Print";
                } else if (link.equalsIgnoreCase("pdoSchdPrint")) {
                    reportName = "Production_Schedule_Based_On_PDO";
                } else if (link.equalsIgnoreCase("pdoStatus")) {
                    reportName = "Production_Order_Status";
                } else if (link.equalsIgnoreCase("pdoSumm")) {
                    reportName = "Production_Summary";
                } else if (link.equalsIgnoreCase("dailyPdo")) {
                    reportName = "Daily_Production";
                } else if (link.equalsIgnoreCase("jcPrint")) {
                    reportName = "Job_Card_Print";
                } else if (link.equalsIgnoreCase("jcStatus")) {
                    reportName = "Job_Card_Status";
                } else if (link.equalsIgnoreCase("jcBarcode")) {
                    reportName = "Job_Card_Barcode";
                } else if (link.equalsIgnoreCase("jcCoBy")) {
                    reportName = "Job_Card_CoByProduct";
                } else if (link.equalsIgnoreCase("jcCost")) {
                    reportName = "Job_Card_Costing";
                } else if (link.equalsIgnoreCase("jcRej")) {
                    reportName = "Job_Card_Rejection_Summary";
                } else if (link.equalsIgnoreCase("jcScrap")) {
                    reportName = "Job_Card_Scrap_Summary";
                } else if (link.equalsIgnoreCase("rcPrint")) {
                    reportName = "Route_Card_Print";
                } else if (link.equalsIgnoreCase("rcBarcode")) {
                    reportName = "Route_Card_Barcode";
                } else if (link.equalsIgnoreCase("rcCoBy")) {
                    reportName = "Route_Card_CoByProduct";
                } else if (link.equalsIgnoreCase("rcCosting")) {
                    reportName = "Route_Card_Costing";
                } else if (link.equalsIgnoreCase("rcRejection")) {
                    reportName = "Rejection_Summary_rc";
                }else if (link.equalsIgnoreCase("rcRejection_summ")) {
                    reportName = "Rejection_Details_rc"; 
                } else if (link.equalsIgnoreCase("rcScrap")) {
                    reportName = "Route_Card_Scrap_Summary";
                } else if (link.equalsIgnoreCase("stockStatus")) {
                    reportName = "Stock_Status_At_Shop_Floor";
                } else if (link.equalsIgnoreCase("wsRead")) {
                    reportName = "Work_Station_Reading";
                }else if(link.equalsIgnoreCase("outSO")){
                    reportName = "OutStandingSalesOrder";
                }else if (link.equalsIgnoreCase("PlanVSActual")){
                    reportName = "PlanVSActual";
                }else if (link.equalsIgnoreCase("ProdMoveSlip")) {
                    reportName = "ProductMovementSlip";
                } else if (link.equalsIgnoreCase("ProdRatio")) {
                    reportName = "ProductivityRatio";
                } else if (link.equalsIgnoreCase("ProdRejDet")) {
                    reportName = "Rejection_Details_jc";
                } else if (link.equalsIgnoreCase("ProdRejSumm")) {
                    reportName = "Rejection_Summary_jc";
                } else if (link.equalsIgnoreCase("PDOSO")) {
                    reportName = "OutstandingSalesOrderPDO";
                } else if (link.equalsIgnoreCase("RCConsump")) {
                    reportName = "ConsumptionOnRC";
                } else if (link.equalsIgnoreCase("RCIssuedQty")) {
                    reportName = "IssuedQtyRouteCard";
                } else if (link.equalsIgnoreCase("RCMRS")) {
                    reportName = "MRS_RC";
                } else if (link.equalsIgnoreCase("JCConsump")) {
                    reportName = "ConsumptionOnJC";
                } else if (link.equalsIgnoreCase("JCIssuedQty")) {
                    reportName = "IssueQtyJobCard";
                } else if (link.equalsIgnoreCase("JCMRS")) {
                    reportName = "MRSJC_REPORT";
                } else if (link.equalsIgnoreCase("MachDownTime")) {
                    reportName = "MachineDowntimeReport";
                } else if (link.equalsIgnoreCase("MachNoPlan")) {
                    reportName = "MachineHavingNoPlan";
                }else if(link.equalsIgnoreCase("MachPlan")){
                    reportName = "MachineHavingPlan";
                }else if (link.equalsIgnoreCase("WSDet")){
                    reportName = "WorkStationDetails";
                }else if (link.equalsIgnoreCase("ShiftList")){
                    reportName = "ListOfShift";
                }else if (link.equalsIgnoreCase("WSUtil")){
                    reportName = "WorkStation_Utilization";
                }else if (link.equalsIgnoreCase("WSUtilItem")){
                    reportName = "WorkStation_Utilization_Itemwise";
                }else if (link.equalsIgnoreCase("WSDowntime")){
                    reportName = "MachineDowntimeReport";
                }else if (link.equalsIgnoreCase("WSPlan")){
                    reportName = "MachineHavingPlan";
                }else if (link.equalsIgnoreCase("WSNoPlan")){
                    reportName = "MachineHavingNoPlan";
                }else if (link.equalsIgnoreCase("LOShift")){
                    reportName = "ListOfShift";
                }else if (link.equalsIgnoreCase("rcProdMove")){
                    reportName = "ProductMovementSlip_RC";
                }else if (link.equalsIgnoreCase("rcPlanVsActual")){
                    reportName = "PlanVSActual_RC";
                }else if (link.equalsIgnoreCase("PortalLink")){
                    reportName = request.getParameter("fileName");
                    System.out.println("--inside file link "+reportName);
                }else if (link.equalsIgnoreCase("QCTestCertificate")){
                    reportName = "QC_Test_Certificate";
                }else if (link.equalsIgnoreCase("gpp")){
                    reportName = "GPP_Print";
                }else if (link.equalsIgnoreCase("bomHierarchy")){
                    reportName = "BOM_HIERARCHY";
                }else if(link.equalsIgnoreCase("prodFc")){
                    if(fcType==1){
                 reportName = "ProductionForecastDaily";
                    }else if(fcType ==2){
                 reportName = "ProductionForecastWeekly";
                    }else if(fcType ==3){
                        reportName = "ProductionForecastQuarterly";
                    }else if(fcType ==4){
                        reportName = "ProductionForecastHalfyearly";
                    }else if (fcType ==5){
                        reportName = "ProductionForecastMonthly";
                    }
                 
                }
            }

            JasperReport report = (JasperReport)JRLoader.loadObject(path + reportName + ".jasper");

            String reportType = null;
            String check = null; // For csv or pdf format
            String opId = null;
            String wcId = null;
            String wsId = null;
            String itmId = null;
            Object stDt = null;
            String fromDate = null;
            Object edDt = null;
            String toDate = null;
            String bomId = null;
            String pdoId = null;
            String jcId = null;
            String rcId = null;
            String grpId = null;
            String hdcolor = null;
            String bgcolor = null;
            String margin = null;
            String QCDocId = null;
            String gppDocId = null;
            String cldId = request.getParameter("cldId");
            Integer slocId = Integer.parseInt(request.getParameter("slocId"));
            String hoOrgId = request.getParameter("hoOrgId");
            String orgId = request.getParameter("orgId");

            // For Rc Id
            if (request.getParameter("rcId") != null) {
                if (request.getParameter("rcId").equals("")) {
                    rcId = null;
                } else {
                    rcId = request.getParameter("rcId");
                }
            }

            // For Jc Id
            if (request.getParameter("jcId") != null) {
                if (request.getParameter("jcId").equals("")) {
                    jcId = null;
                } else {
                    jcId = request.getParameter("jcId");
                }
            }

            // For Pdo Id
            if (request.getParameter("pdoId") != null) {
                if (request.getParameter("pdoId").equals("")) {
                    pdoId = null;
                } else {
                    pdoId = request.getParameter("pdoId");
                }
            }

            // For BOM Id
            if (request.getParameter("bomId") != null) {
                if (request.getParameter("bomId").equals("")) {
                    bomId = null;
                } else {
                    bomId = request.getParameter("bomId");
                }
            }

            // For End date
            if (request.getParameter("edDt") != null) {
                if (request.getParameter("edDt").equals("")) {
                    edDt = null;
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    edDt = request.getParameter("edDt");
                    Date dt = dateFormat.parse(edDt.toString());
                    toDate = dateFormat.format(dt);
                }
            }

            // For Start date
            if (request.getParameter("stDt") != null) {
                if (request.getParameter("stDt").equals("")) {
                    stDt = null;
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    stDt = request.getParameter("stDt");
                    Date dt = dateFormat.parse(stDt.toString());
                    fromDate = dateFormat.format(dt);
                }
            }

            // For Item Id
            if (request.getParameter("itmId") != null) {
                if (request.getParameter("itmId").equals("")) {
                    itmId = null;
                } else {
                    itmId = request.getParameter("itmId");
                }
            }

            // For Work Station Id
            if (request.getParameter("wsId") != null) {
                if (request.getParameter("wsId").equals("")) {
                    wsId = null;
                } else {
                    wsId = request.getParameter("wsId");
                }
            }

            // For Work Center Id
            if (request.getParameter("wcId") != null) {
                if (request.getParameter("wcId").equals("")) {
                    wcId = null;
                } else {
                    wcId = request.getParameter("wcId");
                }
            }

            // For Operation Id
            if (request.getParameter("opId") != null) {
                if (request.getParameter("opId").equals("")) {
                    opId = null;
                } else {
                    opId = request.getParameter("opId");
                }
            }

            //Report Type
            if (request.getParameter("reportType") != null) {
                if (request.getParameter("reportType").equals("")) {
                    reportType = "pdf";
                } else {
                    reportType = request.getParameter("reportType");
                    System.out.println("reportype "+reportType);
                }
            }
            System.out.println("reportype "+reportType);
            //for grp id
            if(request.getParameter("grpId")!=null){
                if(request.getParameter("grpId").equals("")){
                    grpId = null;
                    
                }else {
                    grpId = request.getParameter("grpId");
                }
            }
            //QC Doc Id
            if(request.getParameter("QCDocId")!=null){
                if(request.getParameter("QCDocId").equals("")){
                    QCDocId = null;
                    
                }else {
                    grpId = request.getParameter("QCDocId");
                }
            }
            
            //GPP Doc Id
            if(request.getParameter("gppId")!=null){
                if(request.getParameter("gppId").equals("")){
                    gppDocId = null;
                    
                }else {
                    gppDocId = request.getParameter("gppId");
                }
            }            
            /* Header Color
                                              */
                       if (request.getParameter("hdcolor") != null) {
                           if (request.getParameter("hdcolor")==null||request.getParameter("hdcolor").equals("")) {
                               hdcolor = "D";
                           } else {
                               hdcolor = request.getParameter("hdcolor");
                           }
                       }

                       /**
                                              * BgColor
                                              */
                      
                       if (request.getParameter("bgcolor") != null) {
                           if (request.getParameter("bgcolor")==null||request.getParameter("bgcolor").equals("")) {
                               bgcolor = "D";
                           } else {
                               bgcolor = request.getParameter("bgcolor");
                           }
                       }
            System.out.println("--->" + request.getParameter("bgcolor"));
                       /**MARGIN DETAILS. **/
                       if (request.getParameter("Margin_Detail") != null) {
                           if (request.getParameter("Margin_Detail")==null||request.getParameter("Margin_Detail").equals("")) {
                               margin = null;
                               System.out.println("equal    ... " + margin);
                           } else {
                               margin = request.getParameter("margin");
                               System.out.println("margin.." + margin);
                               if (request.getParameter("Margin_Detail").equalsIgnoreCase("true")) {
                                   margin = "Y";
                                   System.out.println("margin..Y" + margin);
                               } else if (request.getParameter("Margin_Detail").equalsIgnoreCase("false")) {
                                   margin = "N";
                                   System.out.println("magin...N" + margin);
                               }
                           }
                       }
          
            System.out.println("Link is " + link + " path " + path + " reportName " + reportName +
                               " Actual Report name is :" + report + " opId " + opId + " wcid " + wcId + " wsId " +
                               wsId + " item id " + itmId + " stdt " + fromDate + " edDt " + toDate + " bom " + bomId +
                               " pdo " + pdoId + " jc " + jcId + " rc " + rcId + " cld " + cldId + " Hoorg " + hoOrgId +
                               " org " + orgId + " sloc " + slocId+ " GrpId "+grpId+" Gpp doc id-"+gppDocId);

            //For Csv format

            if ("CSV".equalsIgnoreCase(reportType)) {
                check = "E";

            } else {
                check = "P";
            }

            Map parameters = new HashMap();
            parameters.put("CldId", cldId);
            parameters.put("SlocId", slocId);
            parameters.put("HoOrgId", hoOrgId);
            parameters.put("OrgId", orgId);
            parameters.put("OpDocId", opId);
            parameters.put("WcId", wcId);
            parameters.put("WsId", wsId);
            parameters.put("ItmId", itmId);
            parameters.put("FromDate", fromDate);
            parameters.put("ToDate", toDate);
            parameters.put("BomDocId", bomId);
            parameters.put("PdoDocId", pdoId);
            parameters.put("JcDocId", jcId);
            parameters.put("RcDocId", rcId);
            parameters.put("ReportType", check);
            parameters.put("GrpId",grpId);
            parameters.put("Path", path);
            parameters.put("BgColor", bgcolor);
            parameters.put("Head", hdcolor);
            parameters.put("Margin_Detail", margin);
            parameters.put("QCNoDocId",QCDocId);
            parameters.put("GPPDocId",gppDocId);
            System.out.println("Link is "+link + " -- "+parameters);  

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();

            JRExporter exporter = null;

            if ("pdf".equalsIgnoreCase(reportType)) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".pdf\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("rtf".equalsIgnoreCase(reportType)) {
                response.setContentType("application/rtf");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".rtf\"");
                exporter = new JRRtfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("html".equalsIgnoreCase(reportType)) {
                exporter = new JRXhtmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("CSV".equalsIgnoreCase(reportType)) {
                response.setContentType("application/csv");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".csv\"");
                exporter = new JRCsvExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("docx".equalsIgnoreCase(reportType)) {
                response.setContentType("application/docx");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".docx\"");
                exporter = new JRDocxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xlsx".equalsIgnoreCase(reportType)) {
                response.setContentType("application/xlsx");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".xlsx\"");
                exporter = new JRXlsxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xml".equalsIgnoreCase(reportType)) {
                response.setContentType("application/xml");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".xml\"");
                exporter = new JRXmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            }
            try {
                exporter.exportReport();
            } catch (JRException e) {
                throw new ServletException(e);
            } finally {
                if (ouputStream != null) {
                    try {
                        ouputStream.flush();
                        ouputStream.close();
                    } catch (IOException ex) {
                        throw (ex);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
