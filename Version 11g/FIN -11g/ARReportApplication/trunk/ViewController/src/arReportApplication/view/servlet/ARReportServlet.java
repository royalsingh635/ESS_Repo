package arReportApplication.view.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.math.BigDecimal;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;

import javax.naming.InitialContext;

import javax.servlet.*;
import javax.servlet.http.*;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class ARReportServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        /* Connection conn = null;
        PreparedStatement st = null;



            String amDef = "arReportApplication.model.services.ARReportsAMImpl";
            String config = "ARReportsAMLocal";
            ARReportsAMImpl am = (ARReportsAMImpl)Configuration.createRootApplicationModule(amDef, config);
            st = am.getDBTransaction().createPreparedStatement("select 1 from dual", 0);
            conn = st.getConnection();
        */
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String link = request.getParameter("link");
        String reportName = null;

        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/FINDS");
            conn = ds.getConnection();


            ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

            rs = ps.executeQuery();
            String path = null;
            while (rs.next()) {
                path = rs.getString(1);
                // System.out.println("Path is :" + rs.getString(1));
            }

            if (link != null) {
                if (link.equalsIgnoreCase("stmt")) {
                    reportName = "AR_Statement";
                } else if (link.equalsIgnoreCase("inv")) {
                    reportName = "AR_PendingBills";
                } else if (link.equalsIgnoreCase("adv")) {
                    reportName = "AR_PendingAdvances";
                } else if (link.equalsIgnoreCase("knoc")) {
                    reportName = "AR_BillKnockOff";
                } else if (link.equalsIgnoreCase("ageing")) {
                    reportName = "DetorsAgeing";
                } else if (link.equalsIgnoreCase("ageingsumm")) {
                    reportName = "DetorsAgeing_Summary";
                } else if (link.equalsIgnoreCase("ageingcon")) {
                    reportName = "DetorsAgeing_Consolidate";
                } else if (link.equalsIgnoreCase("ageingconssumm")) {
                    reportName = "DetorsAgeing_Consolidate_Summary";
                } else if (link.equalsIgnoreCase("top")) {
                    reportName = "TopNDebtors";
                }
            }

            InputStream input = new FileInputStream(new File(path + reportName + ".jrxml"));


            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design);


            String Org = request.getParameter("organisation");
            String cldid = request.getParameter("cldid");
            Integer slocid = Integer.parseInt(request.getParameter("slocid").toString());
            String hoorgid = request.getParameter("hoorgid");
            String vouchertype = null;
            BigDecimal AmtX1 = null;
            BigDecimal AmtX2 = null;
            Integer CurrID = null;
            String FromDate = null;
            String ToDate = null;
            String asOnDate = null;
            BigDecimal CoaId = null;
            String reporttype = null;
            String cogId = null;
            Integer eoId = null;
            String bucketType = null;
            String AmtSel = null;
            String DateSelect = null;
            String CurrencyGrouping = null;
            String valueBasedOn = null;
            Integer ValueOfN = null;
            String check = null; // For csv or pdf format
            Integer entityGroup=null;

            //For Voucher Type or PostFlag
            if (request.getParameter("vouchertype") != null) {
                if (request.getParameter("vouchertype").equals("")) {
                    vouchertype = null;
                } else {
                    vouchertype = request.getParameter("vouchertype");
                }
            }

            //For Amount x1
            if (request.getParameter("amountx1") != null) {
                if (request.getParameter("amountx1").equals("")) {
                    AmtX1 = null;
                } else {
                    Double y = Double.parseDouble(request.getParameter("amountx1"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    AmtX1 = z;
                }
            }


            //For Amount x2
            if (request.getParameter("amountx2") != null) {
                if (request.getParameter("amountx2").equals("")) {
                    AmtX2 = null;
                } else {
                    Double y = Double.parseDouble(request.getParameter("amountx2"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    AmtX2 = z;
                }
            }


            // For Currency ID
            if (request.getParameter("currencyid") != null) {
                if (request.getParameter("currencyid").equals("")) {
                    CurrID = null;
                } else {
                    CurrID = Integer.parseInt(request.getParameter("currencyid").toString());
                }
            }

            //For From Date
            if (request.getParameter("fromdate") != null) {
                if (request.getParameter("fromdate").equals("")) {
                    FromDate = null;
                } else {
                    FromDate = request.getParameter("fromdate");
                }
            }

            //For To Date
            if (request.getParameter("todate") != null) {
                if (request.getParameter("todate").equals("")) {
                    ToDate = null;
                } else {
                    ToDate = request.getParameter("todate");
                }
            }

            //For As On Date
            if (request.getParameter("AsOnDate") != null) {
                if (request.getParameter("AsOnDate").equals("")) {
                    asOnDate = null;
                } else {
                    asOnDate = request.getParameter("AsOnDate");
                }
            }

            //COA id
            if (request.getParameter("coaid") != null) {
                if (request.getParameter("coaid").equals("")) {
                    CoaId = null;
                } else {
                    Double y = Double.parseDouble(request.getParameter("coaid"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    CoaId = z;
                }
            }

            //Report Type
            if (request.getParameter("ReportType") != null) {
                if (request.getParameter("ReportType").equals("")) {
                    reporttype = "pdf";
                } else {
                    reporttype = request.getParameter("ReportType");
                }
            }

            //Cog Id
            if (request.getParameter("cogId") != null) {
                if (request.getParameter("cogId").equals("")) {
                    cogId = null;
                } else {
                    cogId = request.getParameter("cogId");
                }
            }


            //For Eo Id
            if (request.getParameter("eoId") != null) {
                if (request.getParameter("eoId").equals("")) {
                    eoId = null;
                } else {
                    eoId = Integer.parseInt(request.getParameter("eoId").toString());
                }
            }


            //BucketSelect
            if (request.getParameter("BucketFor") != null) {
                if (request.getParameter("BucketFor").equals("")) {
                    bucketType = null;
                } else {
                    bucketType = request.getParameter("BucketFor");
                }
            }

            //AmtSel
            if (request.getParameter("AmtSel") != null) {
                if (request.getParameter("AmtSel").equals("")) {
                    AmtSel = null;
                } else {
                    AmtSel = request.getParameter("AmtSel");
                }
            }

            // DateSelect
            if (request.getParameter("DateSelect") != null) {
                if (request.getParameter("DateSelect").equals("")) {
                    DateSelect = null;
                } else {
                    DateSelect = request.getParameter("DateSelect");
                }
            }

            //CurrencyGrouping
            if (request.getParameter("CurrencyGrouping") != null) {
                if (request.getParameter("CurrencyGrouping").equals("")) {
                    CurrencyGrouping = null;
                } else {
                    CurrencyGrouping = request.getParameter("CurrencyGrouping");
                }
            }

            //valueBasedOn
            if (request.getParameter("valueBasedOn") != null) {
                if (request.getParameter("valueBasedOn").equals("")) {
                    valueBasedOn = null;
                } else {
                    valueBasedOn = request.getParameter("valueBasedOn");
                }
            }

            /** Value Of "N"  **/

            if (request.getParameter("valueofn") != null) {
                if (request.getParameter("valueofn").equals("")) {
                    ValueOfN = null;
                } else {
                    ValueOfN = Integer.parseInt(request.getParameter("valueofn").toString());
                }
            }

            /**  Entity Group **/
            if (request.getParameter("entitygrpid") != null) {
                if (request.getParameter("entitygrpid").equals("")) {
                    entityGroup = null;
                } else {
                    entityGroup = Integer.parseInt(request.getParameter("entitygrpid").toString());
                }
            }

            System.out.println("Link " + link + " Orgid " + Org + " cog " + cogId + " coa " + CoaId + " post " +
                               vouchertype + " from " + FromDate + " todate " + ToDate + " amtx1 " + AmtX1 +
                               " amtx2 " + AmtX2 + " path " + path + " cld " + cldid + " sloc " + slocid + " hoorg " +
                               hoorgid + " reporttype " + reporttype + " currid " + CurrID + " asondate " + asOnDate +
                               " datesel " + DateSelect + " bucket " + bucketType + " valueofN " + ValueOfN +
                               " amtsel " + AmtSel + " currgrp " + CurrencyGrouping + " eoid " + eoId +
                               " valuebasedon " + valueBasedOn + " reportname " + reportName+"Entity Group Name "+entityGroup);

            //For Csv format

            if ("xls".equalsIgnoreCase(reporttype)) {
                check = "E";

            } else {
                check = "P";
            }


            Map parameters = new HashMap();

            parameters.put("Org_Id", Org);
            parameters.put("Cog_Id", cogId);
            parameters.put("Coa_Id", CoaId);
            parameters.put("PostFlag", vouchertype);
            parameters.put("From_Date", FromDate);
            parameters.put("To_Date", ToDate);
            parameters.put("AmountX1", AmtX1);
            parameters.put("AmountX2", AmtX2);
            parameters.put("Path", path);
            parameters.put("Cld_Id", cldid);
            parameters.put("Sloc_Id", slocid);
            parameters.put("Ho_Org_Id", hoorgid);
            parameters.put("Report_Type", check); // For conditionally displaying data
            parameters.put("Curr_Id", CurrID);
            parameters.put("AsOnDate", asOnDate);
            parameters.put("Date_Sel", DateSelect);
            parameters.put("Bucket_Select", bucketType);
            parameters.put("ValueOfN", ValueOfN);
            parameters.put("Amt_Sel", AmtSel);
            parameters.put("Currency_Grp", CurrencyGrouping);
            parameters.put("Eo_Id", eoId);
            parameters.put("Value_Based_On", valueBasedOn);
            parameters.put("Entity_Group", entityGroup);

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);

            OutputStream ouputStream = response.getOutputStream();

            JRExporter exporter = null;

            if ("pdf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".pdf\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, true);
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
                exporter = new JRHtmlExporter();
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
                exporter = new JRXlsxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xml".equalsIgnoreCase(reporttype)) {
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
