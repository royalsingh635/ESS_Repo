package GlReportApp.view.Servlet;

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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;


public class TaxRegisterServlet extends HttpServlet {
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


            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/FINDS");
            conn = ds.getConnection();

            ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

            rs = ps.executeQuery();
            String path = null;
            while (rs.next()) {
               // path="D:\\Report from 220\\Report\\";
                path = rs.getString(1);
                // System.out.println("Path is :" + rs.getString(1));
            }

            String fileName = null;
            if (request.getParameter("Type").equalsIgnoreCase("tax")) {
                fileName = "TaxRegister_TaxWise.jrxml";
            } else if (request.getParameter("Type").equalsIgnoreCase("party")) {
                fileName = "TaxRegister_Coawise.jrxml";
            }
           // InputStream input = new FileInputStream(new File(fileName));

           InputStream input = new FileInputStream(new File(path + fileName));
            
            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design);

            Integer coaid = null;
            String reporttype = String.valueOf(request.getParameter("ReportType"));
            String Org = (request.getParameter("organisation"));

            String fromdate = (request.getParameter("fromdate"));
            String CLD_ID = (request.getParameter("CLD_ID"));
            String todate = (request.getParameter("todate"));

            if (request.getParameter("coaid").equals("")) {
                coaid = null;
            } else {
                coaid = (Integer.parseInt(request.getParameter("coaid")));
            }


            if (reporttype.equals("")) {
                reporttype = "pdf";
            }


            //Report Type


            Map parameters = new HashMap();

            parameters.put("Org_ID", Org);

            parameters.put("FromDate", fromdate);


            parameters.put("ToDate", todate);

            parameters.put("CldId", CLD_ID);

            parameters.put("Coa_Id", coaid);
            
            parameters.put("Path",path);

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);


            OutputStream ouputStream = response.getOutputStream();


            JRExporter exporter = null;


            if ("pdf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/pdf");

                if (request.getParameter("Type").equalsIgnoreCase("party"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"TaxRegister_Coawise.pdf\"");
                else
                    response.setHeader("Content-Disposition", "attachement; filename=\"TaxRegister_TaxWise.pdf\"");

                exporter = new JRPdfExporter();
                //exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("rtf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/rtf");

                if (request.getParameter("Type").equalsIgnoreCase("party"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"TaxRegister_Coawise.rtf\"");
                else
                    response.setHeader("Content-Disposition", "attachement; filename=\"TaxRegister_TaxWise.rtf\"");


                exporter = new JRHtmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("html".equalsIgnoreCase(reporttype))

            {


                exporter = new JRXhtmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xls".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xls");

                if (request.getParameter("Type").equalsIgnoreCase("party"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"TaxRegister_Coawise.xls\"");
                else
                    response.setHeader("Content-Disposition", "attachement; filename=\"TaxRegister_TaxWise.xls\"");


                exporter = new JRXlsExporter();
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("docx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/docx");

                if (request.getParameter("Type").equalsIgnoreCase("party"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"TaxRegister_Coawise.docx\"");
                else
                    response.setHeader("Content-Disposition", "attachement; filename=\"TaxRegister_TaxWise.docx\"");


                exporter = new JRDocxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xlsx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xlsx");

                if (request.getParameter("Type").equalsIgnoreCase("party"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"TaxRegister_Coawise.xlsx\"");
                else
                    response.setHeader("Content-Disposition", "attachement; filename=\"TaxRegister_TaxWise.xlsx\"");


                exporter = new JRXlsxExporter();
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xml".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xml");

                if (request.getParameter("Type").equalsIgnoreCase("party"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"TaxRegister_Coawise.xml\"");
                else
                    response.setHeader("Content-Disposition", "attachement; filename=\"TaxRegister_TaxWise.xml\"");


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
                        
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps!=null){
                            ps.close();
                        }


                        if (conn != null) {
                            conn.close();
                        }
                        ouputStream.flush();
                        ouputStream.close();

                    } catch (IOException ex) {
                        // System.out.println(ex.getMessage());
                        throw (ex);
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
