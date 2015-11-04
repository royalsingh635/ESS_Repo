package GlReportApp.view.Servlet;

import GlReportApplication.model.module.GlReportAMImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;

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
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

//import oracle.igf.ids.Org;

import oracle.jbo.client.Configuration;

public class GlReportServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    private String StartDate;
    private String EndDate;
    private String Org;
    private String AsonDate;
    private String reporttype;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /**Process the HTTP doGet request.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map parameters = new HashMap();
        String Link = String.valueOf(request.getParameter("lnkID"));

        try {
            /* PreparedStatement st = null;
              String amDef = "GlReportApplication.model.module.GlReportAMImpl";
              String config = "GlReportAMLocal";
              GlReportAMImpl am =(GlReportAMImpl)Configuration.createRootApplicationModule(amDef,config);
              st = am.getDBTransaction().createPreparedStatement("select 1 from dual", 0);
              Connection conn = st.getConnection(); */
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

            //InputStream input = new FileInputStream(new File("D:/Report/PnLReport_Summary.jrxml"));
            InputStream input = new FileInputStream(new File(path + "PnLReport_Summary.jrxml"));

            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design);

            Integer bankCash = null;
            if (Link.equals("gl1")) {
                StartDate = String.valueOf(request.getParameter("startdate"));
                EndDate = String.valueOf(request.getParameter("enddate"));
                Org = String.valueOf(request.getParameter("organisation"));
                //String AsonDate =String.valueOf(request.getParameter("AsOnDate"));
                reporttype = String.valueOf(request.getParameter("ReportType"));
            }
            if (Link.equals("gl2")) {
                StartDate = String.valueOf(request.getParameter("startdate"));
                EndDate = String.valueOf(request.getParameter("enddate"));
                Org = String.valueOf(request.getParameter("organisation"));
                //AsonDate =String.valueOf(request.getParameter("AsOnDate"));
                reporttype = String.valueOf(request.getParameter("ReportType"));
            }
            if (Link.equals("gl3")) {
                StartDate = String.valueOf(request.getParameter("startdate"));
                EndDate = String.valueOf(request.getParameter("enddate"));
                Org = String.valueOf(request.getParameter("organisation"));
                // AsonDate =String.valueOf(request.getParameter("AsOnDate"));
                reporttype = String.valueOf(request.getParameter("ReportType"));
            }
            if (Link.equals("gl4")) {
                StartDate = String.valueOf(request.getParameter("startdate"));
                EndDate = String.valueOf(request.getParameter("enddate"));
                Org = String.valueOf(request.getParameter("organisation"));
                // AsonDate =String.valueOf(request.getParameter("AsOnDate"));
                reporttype = String.valueOf(request.getParameter("ReportType"));
            }
            if (Link.equals("gl5")) {
                StartDate = String.valueOf(request.getParameter("startdate"));
                EndDate = String.valueOf(request.getParameter("enddate"));
                Org = String.valueOf(request.getParameter("organisation"));
                AsonDate = String.valueOf(request.getParameter("AsOnDate"));
                reporttype = String.valueOf(request.getParameter("ReportType"));
            }
            if (Link.equals("gl6")) {
                StartDate = String.valueOf(request.getParameter("startdate"));
                EndDate = String.valueOf(request.getParameter("enddate"));
                Org = String.valueOf(request.getParameter("organisation"));
                AsonDate = String.valueOf(request.getParameter("AsOnDate"));
                reporttype = String.valueOf(request.getParameter("ReportType"));
            }
            if (Link.equals("gl7")) {
                StartDate = String.valueOf(request.getParameter("startdate"));
                EndDate = String.valueOf(request.getParameter("enddate"));
                Org = String.valueOf(request.getParameter("organisation"));
                AsonDate = String.valueOf(request.getParameter("AsOnDate"));
                reporttype = String.valueOf(request.getParameter("ReportType"));
            }
            if (Link.equals("gl9")) {
                StartDate = String.valueOf(request.getParameter("startdate"));
                EndDate = String.valueOf(request.getParameter("enddate"));
                Org = String.valueOf(request.getParameter("organisation"));
                AsonDate = String.valueOf(request.getParameter("AsOnDate"));
                reporttype = String.valueOf(request.getParameter("ReportType"));
            }
            if (Link.equals("gl10")) {
                StartDate = String.valueOf(request.getParameter("startdate"));
                EndDate = String.valueOf(request.getParameter("enddate"));
                Org = String.valueOf(request.getParameter("organisation"));
                AsonDate = String.valueOf(request.getParameter("AsOnDate"));
                reporttype = String.valueOf(request.getParameter("ReportType"));
            }
            if (Link.equals("gl11")) {
                StartDate = String.valueOf(request.getParameter("startdate"));
                EndDate = String.valueOf(request.getParameter("enddate"));
                Org = String.valueOf(request.getParameter("organisation"));
                AsonDate = String.valueOf(request.getParameter("AsOnDate"));
                reporttype = String.valueOf(request.getParameter("ReportType"));
            }
            if (Link.equals("gl12")) {
                StartDate = String.valueOf(request.getParameter("startdate"));
                EndDate = String.valueOf(request.getParameter("enddate"));
                Org = String.valueOf(request.getParameter("organisation"));
                AsonDate = String.valueOf(request.getParameter("AsOnDate"));
                reporttype = String.valueOf(request.getParameter("ReportType"));
            }
            if (Link.equals("gl13")) {
                StartDate = String.valueOf(request.getParameter("startdate"));
                EndDate = String.valueOf(request.getParameter("enddate"));
                Org = String.valueOf(request.getParameter("organisation"));
                AsonDate = String.valueOf(request.getParameter("AsOnDate"));
                reporttype = String.valueOf(request.getParameter("ReportType"));

                parameters.put("ORG_ID", Org);
                parameters.put("START_DATE", StartDate);
                parameters.put("END_DATE", EndDate);
                parameters.put("AS_ON_DATE", AsonDate);
                parameters.put("Path", path);
            }


            if (request.getParameter("bankcash").equals("")) {
                bankCash = null;
            } else {
                bankCash = Integer.parseInt(request.getParameter("bankcash"));
            }
            //Report Type:----------------------
            if (reporttype.equals("")) {
                reporttype = "pdf";
            }


            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();
            JRExporter exporter = null;
            if ("pdf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/pdf");

                response.setHeader("Content-Disposition", "attachement; filename=\"Bank_cash_book_report.pdf\"");


                exporter = new JRPdfExporter();
                exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("rtf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/rtf");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Content-Disposition", "attachement; filename=\"Bank_cash_book_report.rtf\"");

                exporter = new JRRtfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("html".equalsIgnoreCase(reporttype)) {
                exporter = new JRXhtmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xls".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xls");
                response.setHeader("Content-Disposition", "attachement; filename=\"Bank_cash_book_report.xls\"");

                exporter = new JRXlsExporter();
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("docx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/docx");
                response.setHeader("Content-Disposition", "attachement; filename=\"Bank_cash_book_report.docx\"");

                exporter = new JRDocxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xlsx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xlsx");
                response.setHeader("Content-Disposition", "attachement; filename=\"Bank_cash_book_report.xlsx\"");

                exporter = new JRXlsxExporter();
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xml".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xml");
                response.setHeader("Content-Disposition", "attachement; filename=\"Bank_cash_book_report.xml\"");

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
                        //System.out.println(ex.getMessage());
                        throw (ex);
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    //end of doGet()

}
