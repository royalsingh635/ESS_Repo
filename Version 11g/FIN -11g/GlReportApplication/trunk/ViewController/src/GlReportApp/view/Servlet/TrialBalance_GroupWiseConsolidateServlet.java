package GlReportApp.view.Servlet;


import GlReportApplication.model.module.GlReportAMImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;

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
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import oracle.jbo.client.Configuration;

import oracle.jdbc.proxy.annotation.Pre;


public class TrialBalance_GroupWiseConsolidateServlet extends HttpServlet {
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

            /*  PreparedStatement st = null;

        String amDef = "GlReportApplication.model.module.GlReportAMImpl";
                String config = "GlReportAMLocal";
               GlReportAMImpl am =
                    (GlReportAMImpl)Configuration.createRootApplicationModule(amDef,
                                                                                      config);
        st = am.getDBTransaction().createPreparedStatement("select 1 from dual", 0);
        Connection conn = st.getConnection();
         */

            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/FINDS");
            conn = ds.getConnection();
            ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

            rs = ps.executeQuery();
            String path = null;
            while (rs.next()) {
                //path="D:\\Report from 220\\Report\\";
                path = rs.getString(1);
                // System.out.println("Path is :" + rs.getString(1));
            }

            //InputStream input = new FileInputStream(new File("D:/Report/TrialBalance_GroupWise_Consolidate.jrxml"));
            InputStream input = new FileInputStream(new File(path + "TrialBalance_GroupWise_Consolidate.jrxml"));

            JasperDesign design = JRXmlLoader.load(input);

            JasperReport report = JasperCompileManager.compileReport(design);


            String reporttype = String.valueOf(request.getParameter("ReportType"));
            String Org = String.valueOf(request.getParameter("organisation"));
            String Todate = String.valueOf(request.getParameter("todate"));
            String Fromdate = String.valueOf(request.getParameter("fromdate"));
            String CogId = String.valueOf(request.getParameter("cogid"));
            String cldid = request.getParameter("cldid");

            String PostFlag = request.getParameter("PostFlag");
            System.out.println("vouchers type is---->>" + PostFlag);
            BigDecimal CoaId = null;
            BigDecimal NaId = null;


            //Report Type----------------
            if (reporttype.equals("")) {
                reporttype = "pdf";
            }


            //Natural Account--------------------
            if (request.getParameter("naid").equals("")) {
                NaId = null;
            } else {
                Double y = Double.parseDouble(request.getParameter("naid"));
                BigDecimal z = new BigDecimal(y.doubleValue());
                NaId = z;
            }


            // COA ---------------
            if (request.getParameter("coaId").equals("")) {
                CoaId = null;
            } else {
                Double y = Double.parseDouble(request.getParameter("coaId"));
                BigDecimal z = new BigDecimal(y.doubleValue());
                CoaId = z;
            }

            Map parameters = new HashMap();


            parameters.put("Org_ID", Org);
            parameters.put("ToDate", Todate);
            parameters.put("FromDate", Fromdate);
            parameters.put("COGID", CogId);
            parameters.put("PostFlag", PostFlag);
            parameters.put("COA_ID", CoaId);
            parameters.put("NA_ID", NaId);
            parameters.put("ReportType", reporttype);
            parameters.put("CLD_ID", cldid);
            parameters.put("Path", path);

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);


            OutputStream ouputStream = response.getOutputStream();


            JRExporter exporter = null;


            if ("pdf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/pdf");

                response.setHeader("Content-Disposition",
                                   "attachement; filename=\"TrialBalance_GroupWiseConsolidate.pdf\"");


                exporter = new JRPdfExporter();
                exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("rtf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/rtf");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Content-Disposition",
                                   "attachement; filename=\"TrialBalance_GroupWiseConsolidate.rtf\"");

                exporter = new JRRtfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("html".equalsIgnoreCase(reporttype)) {
                exporter = new JRXhtmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xls".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xls");
                response.setHeader("Content-Disposition",
                                   "attachement; filename=\"TrialBalance_GroupWiseConsolidate.xls\"");
                exporter = new JRXlsExporter();
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);

            } else if ("docx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/docx");
                response.setHeader("Content-Disposition",
                                   "attachement; filename=\"TrialBalance_GroupWiseConsolidate.docx\"");
                exporter = new JRDocxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xlsx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xlsx");
                response.setHeader("Content-Disposition",
                                   "attachement; filename=\"TrialBalance_GroupWiseConsolidate.xlsx\"");

                exporter = new JRXlsxExporter();
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xml".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xml");
                response.setHeader("Content-Disposition",
                                   "attachement; filename=\"TrialBalance_GroupWiseConsolidate.xml\"");

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
                        if (rs != null) {
                            rs.close();
                        }
                        if (ps != null) {
                            ps.close();
                        }
                        if (conn != null) {
                            conn.close();
                        }
                        ouputStream.flush();
                        ouputStream.close();

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                        throw (ex);
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
