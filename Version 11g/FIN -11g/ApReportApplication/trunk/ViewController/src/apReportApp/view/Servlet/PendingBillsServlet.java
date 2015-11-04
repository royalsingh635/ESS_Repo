package apReportApp.view.Servlet;

import apReportApp.model.module.ApReportAMImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


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

import oracle.jbo.client.Configuration;

public class PendingBillsServlet extends HttpServlet {
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

            

            /* String amDef = "apReportApp.model.module.ApReportAMImpl";
            String config = "ApReportAMLocal";
            ApReportAMImpl am = (ApReportAMImpl)Configuration.createRootApplicationModule(amDef, config);
            st = am.getDBTransaction().createPreparedStatement("select 1 from dual", 0);
            conn = st.getConnection(); */
            
            Context ctx = new InitialContext();
                      DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/FINDS");
                      conn = ds.getConnection();

            ps =
                conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

             rs = ps.executeQuery();
            String path = null;
            while (rs.next()) {
                path = rs.getString(1);
               // System.out.println("Path is :" + rs.getString(1));
            }
            //InputStream input = new FileInputStream(new File("D:/Report/PendingBills.jrxml"));
            InputStream input = new FileInputStream(new File(path + "PendingBills.jrxml"));

            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design);


            String Org = String.valueOf(request.getParameter("organisation"));
            BigDecimal Amt = null;
            BigDecimal AmtX1 = null;
            BigDecimal AmtX2 = null;
            Integer CurrID = null;
            String ToDate = String.valueOf(request.getParameter("todate"));
            String FromDate = String.valueOf(request.getParameter("fromdate"));
            BigDecimal CoaId = null;
            String reporttype = null;


               // System.out.println("No Additional parameters");

            //Report Type
            if (request.getParameter("ReportType").equals("")) {
                reporttype = "pdf";
            } else {
                reporttype = String.valueOf(request.getParameter("ReportType"));
            }


            //Amount----------------
            if (request.getParameter("amountnet").equals("")) {

                Amt = null;

            } else {

                Double y = Double.parseDouble(request.getParameter("amountnet"));
                BigDecimal z = new BigDecimal(y.doubleValue());
                Amt = z;
            }


            //AmountX1----------------
            if (request.getParameter("amountx1").equals("")) {

                AmtX1 = null;

            } else {

                Double y = Double.parseDouble(request.getParameter("amountx1"));
                BigDecimal z = new BigDecimal(y.doubleValue());
                AmtX1 = z;
            }


            //AmountX2----------------
            if (request.getParameter("amountx2").equals("")) {

                AmtX2 = null;

            } else {

                Double y = Double.parseDouble(request.getParameter("amountx2"));
                BigDecimal z = new BigDecimal(y.doubleValue());
                AmtX2 = z;
            }

            //COA----------------
            if (request.getParameter("coaid").equals("")) {

                CoaId = null;

            } else {

                Double y = Double.parseDouble(request.getParameter("coaid"));
                BigDecimal z = new BigDecimal(y.doubleValue());
                CoaId = z;
            }


            //Currency----------------
            if (request.getParameter("currencyid").equals("")) {

                CurrID = null;

            } else {


                CurrID = Integer.parseInt(String.valueOf(request.getParameter("currencyid")));
            }

            //Cog
            String cogId=null;
            if(request.getParameter("cogId")==null){
                 cogId=null;
                 
            }else{
                  cogId=String.valueOf(request.getParameter("cogId"));   
                         
                }
                    
            String PostFlag = request.getParameter("post");

            Map parameters = new HashMap();

            parameters.put("PostFlag", PostFlag);

            parameters.put("Cog_ID", cogId);
            parameters.put("Org_id", Org);
            parameters.put("Amount", Amt);
            parameters.put("Amount-X1", AmtX1);
            parameters.put("Amount-X2", AmtX2);
            parameters.put("CurrencyID", CurrID);
            parameters.put("ToDate", ToDate);
            parameters.put("FromDate", FromDate);
            parameters.put("Coa_Id", CoaId);
            parameters.put("Path", path);
            


            //System.out.println(reporttype);
           // System.out.println("Connection Established");


            //System.out.println("Report Called...");
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);

            //System.out.println("Report Created...");

            OutputStream ouputStream = response.getOutputStream();
            //  System.out.println(response.getOutputStream());
           // System.out.println("1");

            JRExporter exporter = null;


            if ("pdf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/pdf");

                response.setHeader("Content-Disposition", "attachement; filename=\"PendingBills.pdf\"");


                exporter = new JRPdfExporter();
                exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("rtf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/rtf");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Content-Disposition", "attachement; filename=\"PendingBills.rtf\"");

                exporter = new JRRtfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("html".equalsIgnoreCase(reporttype)) {
                exporter = new JRHtmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xls".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xls");
                response.setHeader("Content-Disposition", "attachement; filename=\"PendingBills.xls\"");

                exporter = new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("docx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/docx");
                response.setHeader("Content-Disposition", "attachement; filename=\"PendingBills.docx\"");

                exporter = new JRDocxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xlsx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xlsx");
                response.setHeader("Content-Disposition", "attachement; filename=\"PendingBills.xlsx\"");

                exporter = new JRXlsxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xml".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xml");
                response.setHeader("Content-Disposition", "attachement; filename=\"PendingBills.xml\"");

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
                       // System.out.println(ex.getMessage());
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
                if(conn!=null){
                    conn.close();
                }
               /*  if(st!=null){
                    st.close();
                } */
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("Finally Report Generated");
    }
}
