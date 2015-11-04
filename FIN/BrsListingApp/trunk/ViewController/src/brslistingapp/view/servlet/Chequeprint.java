package brslistingapp.view.servlet;

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

import oracle.jbo.domain.Timestamp;

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
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import oracle.igf.ids.Org;

import oracle.jbo.domain.Timestamp;

public class Chequeprint extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        Connection conn = null;
        try {


            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/FINDS");
            conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

            ResultSet rs = ps.executeQuery();
            String path = null;
            while (rs.next()) {
                path = rs.getString(1);
                path = path + "FIN/";
                System.out.println("Path is :" + rs.getString(1));
                System.out.println("New Path is :" + path);
            }
     // path="D:\\DEPLOYMENT\\Report\\FIN\\";

            //    System.out.println("getting the connection "+conn);


            /*  InputStream input = new FileInputStream(new File("D:/Report/Cheque_Print.jrxml"));
           InputStream input = new FileInputStream(new File(path + "Cheque_Print.jrxml"));
            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design); */


            String toDate = null;
            String Vou_From_Date = null;
            String Vou_To_Date = null;
            BigDecimal AmountX1 = null;
            BigDecimal AmountX2 = null;
            String fromDate = null;
            Integer Bank_Coa_Id = null;
            String Disp_Vou_Id = null;
            String reportName = null;
            Integer chqTypeId = null;
            // String reporttype="pdf";

            String Org = String.valueOf(request.getParameter("org"));
            Integer sloc = Integer.parseInt(request.getParameter("sloc").toString());
            String reporttype = request.getParameter("ReportType");


            if (request.getParameter("fromdate") != null) {
                if (request.getParameter("fromdate").equals("")) {
                    fromDate = null;
                } else {
                    fromDate = new Timestamp(request.getParameter("fromdate")).dateValue().toString();
                    System.out.println(fromDate);
                }
            } else {
                fromDate = null;
            }

            //todate----------------
            if (request.getParameter("todate") != null) {
                if (request.getParameter("todate").equals("")) {
                    toDate = null;
                } else {
                    toDate = new Timestamp(request.getParameter("todate")).dateValue().toString();
                    System.out.println(toDate);
                }
            } else {
                toDate = null;
            }


            //AmountX1--------------
            if (request.getParameter("amountx1") != null) {
                if (request.getParameter("amountx1").equals("")) {

                    AmountX1 = null;

                } else {

                    Double y = Double.parseDouble(request.getParameter("amountx1"));
                    BigDecimal z = new BigDecimal(y.doubleValue());


                    AmountX1 = z;
                }
            } else {
                AmountX1 = null;
            }


            //AmountX2--------------
            if (request.getParameter("amountx2") != null) {
                if (request.getParameter("amountx2").equals("")) {
                    AmountX2 = null;
                } else {
                    Double y = Double.parseDouble(request.getParameter("amountx2"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    AmountX2 = z;
                }
            } else {
                AmountX2 = null;
            }
            //Vou_From_Date
            if (request.getParameter("Vou_From_Date") != null) {
                if (request.getParameter("Vou_From_Date").equals("")) {
                    Vou_From_Date = null;
                } else {
                    System.out.println("Date " + request.getParameter("Vou_From_Date"));
                    Vou_From_Date = (request.getParameter("Vou_From_Date"));
                    System.out.println(Vou_From_Date);
                }
            } else {
                Vou_From_Date = null;
            }

            //Vou_To_Date

            if (request.getParameter("Vou_To_Date") != null) {
                if (request.getParameter("Vou_To_Date").equals("")) {
                    Vou_To_Date = null;
                } else {
                    // toDate = request.getParameter("todate").toString();
                    Vou_To_Date = (request.getParameter("Vou_To_Date"));
                }
            } else {
                Vou_To_Date = null;
            }


            // Bank_Coa_Id
            if (request.getParameter("Bank_Coa_Id") != null) {
                if (request.getParameter("Bank_Coa_Id").equals("")) {
                    Bank_Coa_Id = null;
                } else {
                    Bank_Coa_Id = Integer.parseInt(request.getParameter("Bank_Coa_Id").toString());

                }
            } else {
                Bank_Coa_Id = null;
            }

            // Disp_Vou_Id-------------------
            if (request.getParameter("Disp_Vou_Id") != null) {
                if (request.getParameter("Disp_Vou_Id").equals("")) {
                    Disp_Vou_Id = null;
                } else {
                    // toDate = request.getParameter("todate").toString();
                    Disp_Vou_Id = request.getParameter("Disp_Vou_Id").toString();
                }
            } else {
                Disp_Vou_Id = null;
            }

            if (request.getParameter("chqType") != null) {
                if (request.getParameter("chqType").equals("")) {
                    chqTypeId = null;
                } else {
                    chqTypeId = Integer.parseInt(request.getParameter("chqType").toString());
                    //System.out.println("Cheque Type Id ____" + chqTypeId);
                }
            }
/**
 * For Printing cheque of according to the cheque book.
 */
            if (chqTypeId == 1) {
                reportName = "Cheque_Book_AxisBank.jasper";
            } else if (chqTypeId == 2) {
                reportName = "Cheque_Book_YesBank.jasper";
            } else if (chqTypeId == 3) {
                reportName = "Cheque_Book_CityBank.jasper";
            }else if (chqTypeId == 4) {
                reportName = "Cheque_Book_PNB.jasper";
            }else if (chqTypeId == 5) {
                reportName = "Cheque_Book_Oriental.jasper";
            }else if (chqTypeId == 6) {
                reportName = "Cheque_Book_BMD.jasper";
            }else if (chqTypeId == 7) {
                reportName = "Cheque_Book_IDBI_Bank.jasper";
            }else if (chqTypeId == 8) {
                reportName = "Cheque_Book_Kotak_Bank.jasper";
            }else if (chqTypeId == 9) {
                reportName = "Cheque_Book_SBI_Bank.jasper";
            }else if (chqTypeId == 10) {
                reportName = "Cheque_Book_HDFC_Bank.jasper";
            }else if (chqTypeId == 11) {
                reportName = "Cheque_Book_DhanLaxmiBank.jasper";
            }else if (chqTypeId == 12) {
                reportName = "Cheque_Book_IndusIndBank.jasper";
            }else if (chqTypeId == 13) {
                reportName = "Cheque_Book_SBI_Pat.jasper";
            }else if (chqTypeId == 14) {
                reportName = "Cheque_Book_ICICI.jasper";
            }else if (chqTypeId == 15) {
                reportName = "Cheque_Book_DCB_Bank.jasper";
            }


            //Report Type:----------------------
            if (reporttype.equals("")) {
                reporttype = "pdf";
            }

            Map parameters = new HashMap();

            parameters.put("Org_Id", Org);
            parameters.put("Cld_Id", request.getParameter("cldid"));
            parameters.put("Sloc_Id", sloc);
            parameters.put("Amount-X1", AmountX1);
            parameters.put("Amount-X2", AmountX2);
            parameters.put("Vou_To_Date", Vou_To_Date);
            parameters.put("Vou_From_Date", Vou_From_Date);
            parameters.put("Bank_Coa_Id", Bank_Coa_Id);
            parameters.put("Disp_Vou_Id", Disp_Vou_Id);
            parameters.put("To_Date", toDate);
            parameters.put("From_Date", fromDate);

            parameters.put("Path", path);
            System.out.println("paramter -"+parameters);

            System.out.println("vou to dt---" + Vou_To_Date + "voiu frm dat--" + Vou_From_Date + "  DispVouID-" +
                               Disp_Vou_Id);
            System.out.println("Org:" + Org + "SlocId---" + sloc + " Amount1 :" + AmountX1 + " amt2 " + AmountX2 +
                               " toDate " + toDate + " Fromdate " + fromDate + " cldid " +
                               request.getParameter("cldid") + "Bank_Coa_Id->" + Bank_Coa_Id + "Cheque Type ----" +
                               chqTypeId+ " Report Name -"+reportName);


            JasperReport report = (JasperReport) JRLoader.loadObject(path + reportName);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();
            JRExporter exporter = null;


            if ("PDF".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/pdf");

                response.setHeader("Content-Disposition", "attachement; filename=\"Cheque.pdf\"");


                exporter = new JRPdfExporter();
                //exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("rtf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/rtf");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Content-Disposition", "attachement; filename=\"Cheque.rtf\"");

                exporter = new JRRtfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("html".equalsIgnoreCase(reporttype)) {
                exporter = new JRHtmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("XLS".equalsIgnoreCase(reporttype)) {

                response.setContentType("application/csv");
                response.setHeader("Content-Disposition", "attachement; filename=\"Cheque.csv\"");
                exporter = new JRCsvExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);


            } else if ("docx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/docx");
                response.setHeader("Content-Disposition", "attachement; filename=\"Cheque.docx\"");

                exporter = new JRDocxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xlsx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xlsx");
                response.setHeader("Content-Disposition", "attachement; filename=\"Cheque.xlsx\"");

                exporter = new JRXlsxExporter();
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xml".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xml");
                response.setHeader("Content-Disposition", "attachement; filename=\"Cheque.xml\"");

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
}
