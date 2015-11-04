package procurementreportapp.view.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;

import javax.naming.InitialContext;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;

@WebServlet(name = "ProcurementServlet", urlPatterns = { "/procurementservlet" })
public class ProcurementServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        // PrintWriter out = response.getWriter();
        Connection conn = null;
        DataSource ds = null;
        String link = request.getParameter("linkId");
      //  String tab = request.getParameter("tab");

        try {
            String reportName = null;
            String bgcolor = null;
            String hdcolor = null;
            String margin = null;
            String reportType = null;
            String orgId = null;
            String cldId = null;
            Integer slocId = null;
            String poDocId = null;
            Integer poStat = null;
            Integer eoId = null;
            String itmId = null;
            String fromDate = null;
            String toDate = null;

            String cpoDocId = null;
            String cpoId = null;
            String coaId = null;

            String soDocId = null;
            String ordPoDocId = null;

            String quotDocId = null;
            String quotId = null;

            String quotStat = null;
            String evalId = null;

            String quotAnaStat = null;

            String rfqId = null;
            String IDFDocId = null;
            String geChk = null;

            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MMDS");
            conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");
            ResultSet rs = ps.executeQuery();
            String path = null;
            while (rs.next()) {
                path = rs.getString(1);
            }
            /**For Path either module wise or Portal wise*/
            if (link.equalsIgnoreCase("l20") && request.getParameter("fileName") != null) {
                path = path + "Portal/";
            } else {
                path = path + "MM/";
            }
            System.out.println("Path..." + path);
            if (request.getParameter("ReportType") != null) {
                if (request.getParameter("ReportType") == null || request.getParameter("ReportType").length() <= 0) {
                    reportType = "pdf";
                } else {
                    reportType = request.getParameter("ReportType");
                }
            }

            orgId = request.getParameter("OrgId");
            cldId = request.getParameter("CldId");
            slocId = Integer.parseInt(request.getParameter("SlocId"));
            if (request.getParameter("EoId") != null) {
                if (request.getParameter("EoId").equals("")) {
                    eoId = null;
                } else {
                    eoId = Integer.parseInt(request.getParameter("EoId"));
                }
            }
            if (request.getParameter("ItmId") != null) {
                if (request.getParameter("ItmId").equals("")) {
                    itmId = null;
                } else {
                    itmId = request.getParameter("ItmId");
                }
            }
            Object stDt = null;
            Object edDt = null;
            if (request.getParameter("FromDate") != null) {
                if (request.getParameter("FromDate").equals("")) {
                    fromDate = null;
                } else {
//                    fromDate = request.getParameter("FromDate").toString().substring(0, 10);
//                    System.out.println(" from date---"+fromDate);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    stDt = request.getParameter("FromDate");
                    Date dt = dateFormat.parse(stDt.toString());
                    fromDate = dateFormat.format(dt);
                    System.out.println(" from date---"+fromDate);
        
                }
            }
            if (request.getParameter("ToDate") != null) {
                if (request.getParameter("ToDate").equals("")) {
                    toDate = null;
                } else {
//                   toDate = request.getParameter("ToDate").toString().substring(0, 10);
//                   System.out.println("to Date--"+toDate);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    edDt = request.getParameter("ToDate");
                    Date dt = dateFormat.parse(edDt.toString());
                    toDate = dateFormat.format(dt);
                    System.out.println("to Date--"+toDate);

                }
            }

            //PO & its related reports
            if (request.getParameter("DocId") != null) {
                if (request.getParameter("DocId") == null||request.getParameter("DocId").equals("")) {
                    poDocId = null;
                    System.out.println("inside podocid getparameter."+poDocId);
                } else {
                    poDocId = request.getParameter("DocId");
                }
            }

            if (request.getParameter("PoStat") != null) {
                if (request.getParameter("PoStat")==null||request.getParameter("PoStat").equals("")) {
                    poStat = null;
                } else {
                    poStat = Integer.parseInt(request.getParameter("PoStat"));
                }
            }
            //Cpo & cpo Summ
            if (request.getParameter("CpoId") != null) {
                if (request.getParameter("CpoId") == null || request.getParameter("CpoId").equals("")) {
                    cpoId = null;
                } else
                    cpoId = request.getParameter("CpoId");
            }
            if (request.getParameter("CpoDocId") != null) {
                if (request.getParameter("CpoDocId") == null || request.getParameter("CpoDocId").equals("")) {
                    cpoDocId = null;
                } else {
                    cpoDocId = request.getParameter("CpoDocId");
                }
            }
            if (request.getParameter("CoaId") != null) {
                if (request.getParameter("CoaId") == null || request.getParameter("CoaId").equals("")) {
                    coaId = null;
                } else
                    coaId = request.getParameter("CoaId");
            }
            //So & So supp
            if (request.getParameter("SoDocId") != null) {
                if (request.getParameter("SoDocId") == null || request.getParameter("SoDocId").equals("")) {
                    soDocId = null;
                } else
                    soDocId = request.getParameter("SoDocId");
            }
            if (request.getParameter("OrdPoDocId") != null) {
                if (request.getParameter("OrdPoDocId") == null || request.getParameter("OrdPoDocId").equals("")) {
                    ordPoDocId = null;
                } else {
                    ordPoDocId = request.getParameter("OrdPoDocId");
                }
            }
            //Quot & list of quot
            if (request.getParameter("QuotDocId") != null) {
                if (request.getParameter("QuotDocId") == null || request.getParameter("QuotDocId").equals("")) {
                    quotDocId = null;
                } else
                    quotDocId = request.getParameter("QuotDocId");
            }
            if (request.getParameter("QuotId") != null) {
                if (request.getParameter("QuotId") == null || request.getParameter("QuotId").equals("")) {
                    quotId = null;
                } else {
                    quotId = request.getParameter("QuotId");
                }
            }
            //QuotAna
            if (request.getParameter("QuotStat") != null) {
                if (request.getParameter("QuotStat") == null || request.getParameter("QuotStat").equals("")) {
                    quotStat = null;
                } else
                    quotStat = request.getParameter("QuotStat");
            }
            //error part
            if (request.getParameter("EvalId") != null) {
                if (request.getParameter("EvalId") == null || request.getParameter("EvalId").equals("")) {
                    evalId = null;
                } else {
                    evalId = request.getParameter("EvalId");
                }
            }

            //QuotAnaConso
            if (request.getParameter("QuotAnaStat") != null) {
                if (request.getParameter("QuotAnaStat") == null || request.getParameter("QuotAnaStat").equals("")) {
                    quotAnaStat = null;
                } else {
                    quotAnaStat = request.getParameter("QuotAnaStat");
                }
            }

            //RFQ
            if (request.getParameter("RfqId") != null) {
                if (request.getParameter("RfqId") == null || request.getParameter("RfqId").equals("")) {
                    rfqId = null;
                } else {
                    rfqId = request.getParameter("RfqId");
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
            System.out.println("--->" + request.getParameter("bgcolor"));
            if (request.getParameter("bgcolor") != null) {
                if (request.getParameter("bgcolor")==null||request.getParameter("bgcolor").equals("")) {
                    bgcolor = "D";
                } else {
                    bgcolor = request.getParameter("bgcolor");
                }
            }

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
            //IDFDocId
            if(request.getParameter("IDFDocId")!=null){
                if(request.getParameter("IDFDocId")==null||request.getParameter("IDFDocId").equalsIgnoreCase("")){
                    IDFDocId = null;
                }else{ 
                    IDFDocId = request.getParameter("IDFDocId");
                    System.out.println("IdfDocId---"+IDFDocId);
                }
            }
            //GE Check
            if(request.getParameter("GEChk")!=null){
                if(request.getParameter("GEChk")==null||request.getParameter("GEChk").equalsIgnoreCase("")){
                    geChk = "Y";
                }else{ 
                    geChk = request.getParameter("GEChk");
                    System.out.println("ge Chk---"+geChk);
                }
            }

            //define the reportName
          //  if (tab.equals("1")) {
                if (link.equalsIgnoreCase("l1")) {
                    reportName = "PurchaseOrder";
                }  if (link.equalsIgnoreCase("l2")) {
                    reportName = "PO STATUS";
                }  if (link.equalsIgnoreCase("l3")) {
                    reportName = "PurchaseOrderSummary";
                }  if (link.equalsIgnoreCase("l4")) {
                    reportName = "PurchaseOrderDeliverySchedule";
                }  if (link.equalsIgnoreCase("l5")) {
                    reportName = "PurchaseOrderTracking";
                }  if (link.equalsIgnoreCase("l6")) {
                    reportName = "RateContractPO";
                }  if (link.equalsIgnoreCase("l7")) {
                    reportName = "RateContractPOItemWise";
                }  if (link.equalsIgnoreCase("l8")) {
                    reportName = "OpenOrderPO";
                }  if (link.equalsIgnoreCase("l9")) {
                    reportName = "OpenOrderPOItemWise";
                }  if (link.equalsIgnoreCase("l18")) {
                    reportName = "Cash_Purchase_Order";
                }  if (link.equalsIgnoreCase("l19")) {
                    reportName = "Cash_Purchase_Order_Summary";
                }  if (link.equalsIgnoreCase("l16")) {
                    reportName = "Suggested_Order_Report";
                }  if (link.equalsIgnoreCase("l17")) {
                    reportName = "Suggested_Order_Report_SupplierWise";
                }  if (link.equalsIgnoreCase("l22")){
               reportName = "SupplierWiseDeleiverySchedule";
               } if(link.equalsIgnoreCase("l23")){
                   reportName = "Supplier_Wise_Purchase_Order_Pending Quantity";
               }
            if(link.equalsIgnoreCase("l24")){
                reportName = "OrderPendingReceiptItemWise";
                }
            if(link.equalsIgnoreCase("IDF")){
                    reportName = "ImportDeclarationForm";
            }else if(link.equalsIgnoreCase("Port")){
                reportName = "PortTracking";
            }else if(link.equalsIgnoreCase("prTrack")){
                reportName = "PurchaseRequisitionTracking";
            }else if(link.equalsIgnoreCase("SoGrpPODetails")){
                reportName = "SO_Grp_PO";
            }else if (link.equalsIgnoreCase("purPriceHist")){
                reportName = "PurchasePriceHistory";
            }
        //    }
        //else if (tab.equals("2")) {
                if (link.equalsIgnoreCase("l10") || link.equalsIgnoreCase("l11")) {
                    reportName = "RFQ";
                }  if (link.equalsIgnoreCase("l12")) {
                    reportName = "Quotation";
                }  if (link.equalsIgnoreCase("l13")) {
                    reportName = "ListOfQuotation";
                }  if (link.equalsIgnoreCase("l14")) {
                    reportName = "QuotationAnalysis";
                }  if (link.equalsIgnoreCase("l15")) {
                    reportName = "Quotation_Analysis_Consolidated";
                }
          //  } 
        //else if (tab.equalsIgnoreCase("procurement")) {
                if (link.equalsIgnoreCase("l20") && request.getParameter("fileName") != null &&
                    request.getParameter("fileName").toString().length() > 0) {
                    reportName = request.getParameter("fileName");
                    System.out.println("Inside Profile link ");
                }
            //}
            Map parameters = new HashMap();
            parameters.put("OrgId", orgId);
            parameters.put("CldId", cldId);
            parameters.put("SlocId", slocId);
            /*      if (link.equalsIgnoreCase("l1") || link.equalsIgnoreCase("l2") || link.equalsIgnoreCase("l3") ||
                link.equalsIgnoreCase("l4") || link.equalsIgnoreCase("l4") || link.equalsIgnoreCase("l5") ||
                link.equalsIgnoreCase("l6") || link.equalsIgnoreCase("l7") || link.equalsIgnoreCase("l8") ||
                link.equalsIgnoreCase("l9")) {
                parameters.put("DocId", poDocId);
            } */

            parameters.put("PoStat", poStat);
            /*      if (request.getParameter("EoId") != null) {
                if (request.getParameter("EoId") == null || request.getParameter("EoId").equals("")) {
                    parameters.put("EoId", null);
                } else
                    parameters.put("EoId", eoId);
            } */
            if (poDocId != null) {
                parameters.put("DocId", poDocId);
                System.out.println("poDocId--"+poDocId+"--po");
            } else if (cpoDocId != null) {
                parameters.put("DocId", cpoDocId);
                System.out.println("cpoDocId--"+cpoDocId);
            } else if (soDocId != null) {
                parameters.put("DocId", soDocId);
                System.out.println("soDocId--"+soDocId);
            } else if (quotDocId != null) {
                parameters.put("DocId", quotDocId);
                System.out.println("quotDocId--"+quotDocId);
            } 
            if(IDFDocId!=null){
                parameters.put("IDFDocId",IDFDocId);
                System.out.println("IDFDocId--"+IDFDocId);
            }
            parameters.put("EoId", eoId);
            parameters.put("ItmId", itmId);
            parameters.put("FromDate", fromDate);
            parameters.put("ToDate", toDate);
            /*   if (link.equalsIgnoreCase("l8") || link.equalsIgnoreCase("l9")) {
                parameters.put("DocId", cpoDocId);
            } */
            parameters.put("CpoId", cpoId);
            parameters.put("CoaId", coaId);
            /*   if (link.equalsIgnoreCase("l6") || link.equalsIgnoreCase("l7")) {
                parameters.put("DocId", soDocId);
            } */
            parameters.put("OrdPoDocId", ordPoDocId);


            parameters.put("Quot_stat", quotStat);
              /*  if (link.equalsIgnoreCase("l12") || link.equalsIgnoreCase("l13")) {
                parameters.put("DocId", quotDocId);
            } */ 
         //   parameters.put("QuotId", quotId);
            parameters.put("Quot_stat", quotAnaStat);
System.out.println("before evalId");
            parameters.put("EvalId", evalId);
            System.out.println("before evalId");
            parameters.put("eval_id", evalId);

            parameters.put("quot_ana_status", quotAnaStat);
            parameters.put("RfqNo", rfqId);

            parameters.put("BgColor", bgcolor);
            parameters.put("Head", hdcolor);

            parameters.put("Margin_Detail", margin);

            if(reportType.equalsIgnoreCase("XLS")){
                parameters.put("ReportType","E");
            }else{
                parameters.put("ReportType","P");
            }
            parameters.put("GEChk",geChk);
            parameters.put("Path", path);

System.out.println("paarameters--"+parameters);
            /*System.out.println("reportType--" + reportType + "orgId--" + orgId + "..DocId--" + poDocId + "..postat--" +
                               poStat + "..eoId--" + eoId + "..ItmId--" + itmId + "..Fromdate--" + fromDate +
                               "..todate--" + toDate + " ..cpoDocId--" + cpoDocId + "..cpoId--" + cpoId + "..coaId--" +
                               coaId + "..soDocId--" + soDocId + "..ordPoDocId--" + ordPoDocId + "..quotDocId--" +
                               quotDocId + "..quotStat--" + quotStat + "..quotId--" + quotId + "..quotAnaStat--" +
                               quotAnaStat + "..evalId--" + evalId + "..rfqId--" + rfqId + "..hdcolor--" + hdcolor +
                               "..bgcolor--" + bgcolor + "..marginDetails--" + margin);
            *///jasper report calling
            JasperReport report = (JasperReport) JRLoader.loadObject(path + reportName + ".jasper");
            System.out.println("ReportName to be print..." + reportName + " path " + path);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();
            JRExporter exporter = null;
            System.out.println("before pdf");
            System.out.println("reporttype      " + reportType);
            
            if (reportType.equalsIgnoreCase("PDF")) {
                System.out.println("After pdf");
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".pdf\"");
                System.out.println("before expo in pdf---");
                exporter = new JRPdfExporter();
                System.out.println("After expo in pdf---" + exporter);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if (reportType.equalsIgnoreCase("RTF")) {
                response.setContentType("application/rtf");
                response.setHeader("Pragma", "no-cache");
                System.out.println("before expo in pdf---");
                exporter = new JRRtfExporter();
                System.out.println("After expo in pdf---" + exporter);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if (reportType.equalsIgnoreCase("HTML")) {
                exporter = new JRHtmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if (reportType.equalsIgnoreCase("XLS")) {
                response.setContentType("application/csv");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".csv\"");
                exporter = new JRCsvExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if (reportType.equalsIgnoreCase("DOCX")) {
                response.setContentType("application/docx");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".docx\"");
                exporter = new JRDocxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if (reportType.equalsIgnoreCase("XLSX")) {
                response.setContentType("application/xlsx");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".xlsx\"");
                exporter = new JRXlsxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if (reportType.equalsIgnoreCase("XML")) {
                response.setContentType("application/xml");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".xml\"");
                exporter = new JRXmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            }

            try {
                exporter.exportReport();
                System.out.println("expo--" + exporter);
            } catch (JRException e) {
                throw new ServletException(e);
            } finally {
                if (ouputStream != null) {
                    try {
                        ouputStream.flush();
                        ouputStream.close();
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (IOException ex) {
                        throw (ex);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
