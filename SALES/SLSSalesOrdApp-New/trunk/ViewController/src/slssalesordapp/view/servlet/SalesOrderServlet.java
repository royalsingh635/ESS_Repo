package slssalesordapp.view.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

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
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class SalesOrderServlet extends HttpServlet {
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
                        DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/SLSDS");
                        conn = ds.getConnection();
                        String reportName = null;
                        String linkType =
                            request.getParameter("golink").toString();
                        System.out.println("cashso"+request.getParameter("ReportType"));
                        int att=Integer.parseInt(request.getParameter("ReportType")); 
                        System.out.println("The Link is-->"+linkType);
                        String path;
                        System.out.println(att);
                        
                        ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

                        rs = ps.executeQuery();
                         path = null;
                        while (rs.next()) {
                            path = rs.getString(1);
                            // System.out.println("Path is :" + rs.getString(1));
                        }
                        path = path+"SLS/";
                        if (linkType.equalsIgnoreCase("odrdetail") & att==481) {
                           reportName =  "Sls_OpenContract_Report";
                            
                            System.out.println("Open Contract order Report");
                        }
                        else{
                            reportName = "SLS_OrderPrint_Report";
                            System.out.println("Not Cash Sales Order");
                        }
                      
                        System.out.println("the path is -->"+path);
                        InputStream input = new FileInputStream(new File(path + reportName + ".jrxml"));

                        //InputStream input = new FileInputStream(new File("D:/Report/SLS_Opp_DetailReport.jrxml"));

            JasperDesign design = JRXmlLoader.load(input);
                        JasperReport report = JasperCompileManager.compileReport(design);
            //Getting Parameter from the servlet


            
            String DocId = request.getParameter("DocId").toString();
            String orgId = request.getParameter("orgId").toString();
            String cldId = request.getParameter("cldId").toString();
            Integer slocId = Integer.parseInt(request.getParameter("slocId"));
            String hoOrgId = request.getParameter("hoorgId").toString(); 
             
            String reporttype = "pdf";

            System.out.println("DocId "+DocId+" org "+orgId+" ho "+hoOrgId+" cld "+cldId+" sloc "+slocId);
           
            //putting the parameter to map to report jrxml file

            Map parameters = new HashMap();

            parameters.put("OrgId", orgId);
            parameters.put("CldId", cldId);
            parameters.put("SlocId", slocId);
            parameters.put("HoOrgId", hoOrgId);
            parameters.put("DocId", DocId);
            parameters.put("Path", path);
            parameters.put("Att",att);
            
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();
            JRExporter exporter = null;


            if ("pdf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachement; filename=\"Sales Order.pdf\"");

                exporter = new JRPdfExporter();
                //exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, true);
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
