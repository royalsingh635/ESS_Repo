package daybookandvoucherprintapp.view.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.math.BigDecimal;

import java.sql.Connection;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class BankCashBookServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        try {


            Integer bankcash = null;
            Integer coa_id = null;


            String toDate = request.getParameter("todate");
            String fromDate = request.getParameter("fromdate");

            String Org = request.getParameter("org_id");

            String reporttype = request.getParameter("ReportType");


            //bankcash--------------
            if (request.getParameter("bankcash").equals("")) {

                bankcash = null;

            } else {

                bankcash = Integer.parseInt(request.getParameter("bankcash"));
            }


            //AmountX2--------------
            if (request.getParameter("coa_id").equals("")) {
                coa_id = null;
            } else {
                coa_id = Integer.parseInt(request.getParameter("coa_id"));
            }


            if (request.getParameter("bankcash").equals("")) {
                FacesMessage Message = new FacesMessage("Please select Book Type");
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, Message);
                
                System.out.println("inside bankcash null");
            } else {
                Connection conn = null;


                Context ctx = new InitialContext();
                DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/FINDS");
                conn = ds.getConnection();


                InputStream input = new FileInputStream(new File("D:/Report/Bank_cash_book_report.jrxml"));

                JasperDesign design = JRXmlLoader.load(input);
                JasperReport report = JasperCompileManager.compileReport(design);
                System.out.println("inside bankcash not null");

                //Report Type:----------------------
                if (reporttype.trim().equals("")) {
                    reporttype = "PDF";
                }
                System.out.println(bankcash + "------bankcash-----");
                Map<String, Object> parameters = new HashMap<String, Object>();

                parameters.put("org_id", Org.trim());
                parameters.put("coa_id", coa_id);
                parameters.put("bankcash", bankcash);
                parameters.put("to_date", toDate.trim());
                parameters.put("from_date", fromDate.trim());


                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);


                OutputStream ouputStream = response.getOutputStream();


                JRExporter exporter = null;


                if (reporttype.trim().equals("PDF")) {
                    System.out.println("generated pffd formet from pdf block");
                    response.setContentType("application/pdf");

                    response.setHeader("Content-Disposition", "attachement; filename=\"Bank_Cash_Book_Report.pdf\"");


                    exporter = new JRPdfExporter();

                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                } else if ("rtf".equalsIgnoreCase(reporttype)) {
                    response.setContentType("application/rtf");
                    response.setHeader("Pragma", "no-cache");
                    response.setHeader("Content-Disposition", "attachement; filename=\"Bank_Cash_Book_Report.rtf\"");

                    exporter = new JRRtfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                } else if ("html".equalsIgnoreCase(reporttype)) {
                    exporter = new JRHtmlExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                } else if ("xls".equalsIgnoreCase(reporttype)) {
                    response.setContentType("application/xls");
                    response.setHeader("Content-Disposition", "attachement; filename=\"Bank_Cash_Book_Report.xls\"");

                    exporter = new JRXlsExporter();
                    exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                } else if ("docx".equalsIgnoreCase(reporttype)) {
                    response.setContentType("application/docx");
                    response.setHeader("Content-Disposition", "attachement; filename=\"Bank_Cash_Book_Report.docx\"");

                    exporter = new JRDocxExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                } else if ("xlsx".equalsIgnoreCase(reporttype)) {
                    response.setContentType("application/xlsx");
                    response.setHeader("Content-Disposition", "attachement; filename=\"Bank_Cash_Book_Report.xlsx\"");

                    exporter = new JRXlsxExporter();
                    exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                } else if ("xml".equalsIgnoreCase(reporttype)) {
                    response.setContentType("application/xml");
                    response.setHeader("Content-Disposition", "attachement; filename=\"Bank_Cash_Book_Report.xml\"");

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
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

}
