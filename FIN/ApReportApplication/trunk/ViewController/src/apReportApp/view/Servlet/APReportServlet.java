/** @Author Sudhanshu Raj **/
package apReportApp.view.Servlet;

import java.io.IOException;
import java.io.OutputStream;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
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
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;


public class APReportServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String reportName = null; //Use to get Report Name.

            String goLink = request.getParameter("golink").toString(); // which type of report user want to print.
            System.out.println("Link..." + goLink);

            Integer slocId = Integer.parseInt(request.getParameter("slocid").toString());
            String cldId = request.getParameter("cldid");
            String hoOrgId = request.getParameter("hoorgid");
            String usrId = request.getParameter("usrId");

            String cogId = null;
            BigDecimal coaId = null;
            String org = request.getParameter("organisation");

            String toDate = null;
            String fromDate = null;

            BigDecimal amt = null;
            BigDecimal amtX1 = null;
            BigDecimal amtX2 = null;
            Integer currId = null;


            String reporttype = null;
            String postFlag = request.getParameter("post");

            String amtSel = null;
            String dateSel = null;
            String asOnDate = null;
            String lineSel = null;
            String bucketType = null;

            String valueBasedOn = null;
            Integer valueOfN = null;
            String currencyGrouping = null;
            String check = null; // For csv or pdf format
            Integer entityGrp = null;
            String bgColor = null; //For Color Configure Background color.
            String headColor = null; //For Color Configure Header Color.
            String chkOrgNm = null; // For Orgnisation side view.
            String logo=request.getParameter("logo");
            
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/FINDS");
            conn = ds.getConnection();


            ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

            rs = ps.executeQuery();
            String path = null;
            while (rs.next()) {
                path = rs.getString(1);

            }
            //request.getParameter("CurrencyGrouping")

            /** CURRENCY GROUPING **/
            if (request.getParameter("CurrencyGrouping") != null) {
                if (request.getParameter("CurrencyGrouping").equals("")) {
                    currencyGrouping = null;
                } else {
                    currencyGrouping = request.getParameter("CurrencyGrouping");
                }
            }

            /** VALUE OF N **/
            if (request.getParameter("valueofn") != null) {
                if (request.getParameter("valueofn").equals("")) {
                    valueOfN = null;
                } else {
                    valueOfN = Integer.parseInt(request.getParameter("valueofn").toString());
                }
            }

            /** DateSelect **/
            if (request.getParameter("DateSel") != null) {
                if (request.getParameter("DateSel").equals("")) {
                    dateSel = null;
                } else {
                    dateSel = request.getParameter("DateSel");
                }
            }

            /** LINE SEL **/
            if (request.getParameter("LineSel") != null) {
                if (request.getParameter("LineSel").equals("")) {
                    lineSel = null;
                } else {
                    lineSel = request.getParameter("LineSel");
                }
            }


            /** AmtSel **/
            if (request.getParameter("AmtSel") != null) {
                if (request.getParameter("AmtSel").equals("")) {
                    amtSel = null;
                } else {
                    amtSel = request.getParameter("AmtSel");
                }
            }


            /** BucketSelect **/
            if (request.getParameter("BucketFor") != null) {
                if (request.getParameter("BucketFor").equals("")) {
                    bucketType = null;
                } else {
                    bucketType = request.getParameter("BucketFor");
                }
            }

            /** Report Type **/
            if (request.getParameter("reportType") != null) {
                if (request.getParameter("reportType").equals("")) {
                    reporttype = "pdf";
                } else {
                    reporttype = request.getParameter("reportType");
                }
            }

            /** AS ON DATE **/
            if (request.getParameter("AsOnDate") != null) {
                if (request.getParameter("AsOnDate").equals("")) {
                    asOnDate = null;

                } else {
                    // asOnDate = Date.getCurrentDate().toString();
                    asOnDate = request.getParameter("AsOnDate");
                }
            }

            /** Amount **/
            if (request.getParameter("amountnet") != null) {
                if (request.getParameter("amountnet").equals("")) {
                    amt = null;
                } else {
                    Double y = Double.parseDouble(request.getParameter("amountnet"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    amt = z;
                }
            }


            /** AmountX1 **/
            if (request.getParameter("amountx1") != null) {
                if (request.getParameter("amountx1").equals("")) {
                    amtX1 = null;
                } else {
                    Double y = Double.parseDouble(request.getParameter("amountx1"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    amtX1 = z;
                }
            }


            /** AmountX2 **/
            if (request.getParameter("amountx2") != null) {
                if (request.getParameter("amountx2").equals("")) {
                    amtX2 = null;
                } else {

                    Double y = Double.parseDouble(request.getParameter("amountx2"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    amtX2 = z;
                }
            }

            /** COA **/
            if (request.getParameter("coaid") != null) {
                if (request.getParameter("coaid").equals("")) {
                    coaId = null;
                } else {

                    Double y = Double.parseDouble(request.getParameter("coaid"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    coaId = z;
                }
            }


            /** Currency **/
            if (request.getParameter("currencyid") != null) {
                if (request.getParameter("currencyid").equals("")) {
                    currId = null;
                } else {
                    currId = Integer.parseInt(request.getParameter("currencyid"));
                }
            }
            /** VALUE BASE ON **/
            if ((request.getParameter("valueBasedOn") != null)) {
                if (request.getParameter("valueBasedOn").equals("")) {
                    valueBasedOn = null;
                } else {
                    valueBasedOn = request.getParameter("valueBasedOn");
                }
            }

            /** COGID **/
            //System.out.println("to be COG........");
            if (request.getParameter("cogid") != null) {
                if (request.getParameter("cogid").equals("")) {
                    //System.out.println("to be COG NULL........");
                    cogId = null;
                } else {
                    cogId = request.getParameter("cogid");
                    //System.out.println("to be COG cogId........");
                }
            }
            /** For From Date **/
            if (request.getParameter("fromdate") != null) {
                if (request.getParameter("fromdate").equals("")) {
                    fromDate = null;
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    fromDate = request.getParameter("fromdate");
                    Date dt = dateFormat.parse(fromDate.toString());
                    fromDate = dateFormat.format(dt);

                }
            }

            /** For To Date **/
            if (request.getParameter("todate") != null) {
                if (request.getParameter("todate").equals("")) {
                    toDate = null;
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    toDate = request.getParameter("todate");
                    java.util.Date dt = dateFormat.parse(toDate);
                    toDate = dateFormat.format(dt);
                }
            }

            /** Entity Group **/
            if (request.getParameter("entitygrp") != null) {
                if (request.getParameter("entitygrp").equals("")) {
                    entityGrp = null;
                } else {
                    entityGrp = Integer.parseInt(request.getParameter("entitygrp").toString());
                }
            }

            /** Back Ground Color. **/
            if (request.getParameter("bgColor") != null) {
                if (request.getParameter("bgColor").equals("")) {
                    bgColor = "D";
                } else {
                    bgColor = request.getParameter("bgColor").toString();
                }

            }

            /** Header  Color. **/
            if (request.getParameter("headColor") != null) {
                if (request.getParameter("headColor").equals("")) {
                    headColor = "D";
                } else {
                    headColor = request.getParameter("headColor").toString();
                }
            }

            /** Orgnisation Side View  **/
            if (request.getParameter("chkOrgNm") != null) {
                if (request.getParameter("chkOrgNm").equals("")) {
                    chkOrgNm = null;
                } else {
                    chkOrgNm = request.getParameter("chkOrgNm");
                    if (chkOrgNm.equalsIgnoreCase("true")) {
                        chkOrgNm = "Y";
                    } else if (chkOrgNm.equalsIgnoreCase("false")) {
                        chkOrgNm = "N";
                    }
                }
            }


            if (goLink != null) {
                if (goLink.equalsIgnoreCase("crst")) {
                    reportName = "CreditorsStatement";
                } else if (goLink.equalsIgnoreCase("pendingbill")) {
                    reportName = "PendingBills";
                } else if (goLink.equalsIgnoreCase("pendingadv")) {
                    reportName = "PendingAdvances";
                } else if (goLink.equalsIgnoreCase("billknockoff")) {
                    reportName = "BillKnockOff";
                } else if (goLink.equalsIgnoreCase("creditorageing")) {
                    reportName = "SupplierAgeing";
                } else if (goLink.equalsIgnoreCase("creditorageingsum")) {
                    reportName = "SupplierAgeing_Summary";
                } else if (goLink.equalsIgnoreCase("creditorageingsumusr")) {
                    reportName = "SupplierAgeing_Summary_Usr_Bkt";
                } else if (goLink.equalsIgnoreCase("creditorageingcons")) {
                    reportName = "SupplierAgeing_Consolidate";
                } else if (goLink.equalsIgnoreCase("creditorageingconssum")) {
                    reportName = "SupplierAgeing_Consolidate_Summary";
                } else if (goLink.equalsIgnoreCase("creditorageingconssumusr")) {
                    reportName = "SupplierAgeing_Consolidate_Summary_Usr_Bkt";
                } else if (goLink.equalsIgnoreCase("topncreditor")) {
                    reportName = "TopNCreditors";
                } else if (goLink.equalsIgnoreCase("11518") && request.getParameter("fileName") != null &&
                           request.getParameter("fileName").toString().length() > 0) {
                    reportName = request.getParameter("fileName");
                }
            }
            System.out.println("Report Name : " + reportName);

            /**
             * For Changing Path accordingly
             */
            if (goLink.equalsIgnoreCase("11518") && request.getParameter("fileName") != null) {
                path = path + "Portal/";
            } else {
                path = path + "FIN/";
            }

            System.out.println("Link " + goLink + " Orgid " + org + " cog " + cogId + " coa " + coaId + " post " +
                               postFlag + " from " + fromDate + " todate " + toDate + " amtx1 " + amtX1 + " amtx2 " +
                               amtX2 + " path " + path + " cld " + cldId + " sloc " + slocId + " hoorg " + hoOrgId +
                               " reporttype " + reporttype + " currid " + currId + " asondate " + asOnDate +
                               " datesel " + dateSel + " bucket " + bucketType + " valueofN " + valueOfN + " amtsel " +
                               amtSel + " currgrp " + currencyGrouping + " valuebasedon " + valueBasedOn +
                               "Entity Group Name : " + entityGrp + " User Id : " + usrId);

            System.out.println("Bg Color : " + bgColor + "Head Color : " + headColor + "Margin : " + chkOrgNm+ " Logo : "+logo);


            //For Csv format

            if ("xls".equalsIgnoreCase(reporttype)) {
                check = "E";

            } else {
                check = "P";
            }

            Map parameters = new HashMap();

            parameters.put("Cog_Id", cogId);
            parameters.put("Org_Id", org);

            parameters.put("AmountX1", amtX1);
            parameters.put("AmountX2", amtX2);

            parameters.put("To_Date", toDate);
            parameters.put("From_Date", fromDate);
            parameters.put("Coa_Id", coaId);


            parameters.put("Cld_Id", cldId);
            parameters.put("Sloc_Id", slocId);
            parameters.put("Ho_Org_Id", hoOrgId);
            parameters.put("Report_Type", check);
            parameters.put("Curr_Id", currId);
            parameters.put("AsOnDate", asOnDate);
            parameters.put("Date_Sel", dateSel);
            parameters.put("Bucket_Select", bucketType);

            parameters.put("ValueOfN", valueOfN);
            parameters.put("Amt_Sel", amtSel);
            parameters.put("Value_Based_On", valueBasedOn);
            parameters.put("Currency_Grp", currencyGrouping);

            parameters.put("PostFlag", postFlag);
            parameters.put("Path", path);
            parameters.put("Entity_Group", entityGrp);
            parameters.put("User_Id", usrId);
            parameters.put("Org_Logo", logo);

            parameters.put("BgColor", bgColor);
            parameters.put("Head", headColor);
            parameters.put("Margin_Detail", chkOrgNm);

            /* InputStream input = new FileInputStream(new File(path + reportName + ".jrxml"));
            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design); */
            JasperReport report = (JasperReport) JRLoader.loadObject(path + reportName + ".jasper");

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);

            //System.out.println("Report Created...");

            OutputStream ouputStream = response.getOutputStream();

            //System.out.println("1");

            JRExporter exporter = null;


            if ("pdf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".pdf\"");
                exporter = new JRPdfExporter();
                //exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("rtf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/rtf");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".rtf\"");

                exporter = new JRRtfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("html".equalsIgnoreCase(reporttype)) {
                exporter = new JRXhtmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xls".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/csv");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".csv\"");
                exporter = new JRCsvExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("docx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/docx");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".docx\"");

                exporter = new JRDocxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xlsx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xlsx");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".xlsx\"");
                //response.setHeader("Content-Disposition", "attachement; filename=\"GeneralLedger"+user+".xlsx\"");

                exporter = new JRXlsxExporter();
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xml".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xml");
                //response.setHeader("Content-Disposition", "attachement; filename=\"GeneralLedger"+user+".xml\"");
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
                        //System.out.println(ex.getMessage());
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
