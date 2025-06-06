package mmsoctktakeapp.view.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import javax.servlet.annotation.WebServlet;
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

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.client.Configuration;

public class StkTakeServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        System.out.println("inside init");
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);

        // private void openReportFun(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        // IOException {
        Integer FyId = 0;
        String DocId;

        try {

            String ReportName = request.getParameter("reportname");
            String link = request.getParameter("linkid");
            // String StkTkNo=(String)request.getParameter("StkTkNo");
            String OrgId = (String) request.getParameter("orgId");
            if (request.getParameter("docId") != null && request.getParameter("docId") != "") {
                DocId = (String) request.getParameter("docId");
            } else {
                DocId = null;
            }
            String CldId = (String) request.getParameter("cldId");
            Integer SlocId = Integer.parseInt(request.getParameter("slocId"));

            if (request.getParameter("fyId") != null) {
                System.out.println("fy id not null------" + request.getParameter("fyId"));
                if (request.getParameter("fyId") == "") {
                    FyId = null;
                } else {
                    FyId = Integer.parseInt(request.getParameter("fyId").toString());
                }
            } else {
                FyId = null;
            }
            //String WhId=(String)request.getParameter("whId");
            Connection conn = null;
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MMDS");
            conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

            ResultSet rs = ps.executeQuery();
            String path = null;
            while (rs.next()) {
                path = rs.getString(1);
            }
            InputStream input = null;
            if (link.equals("gil1")) {
                System.out.println("link value inside detail---" + link);
                input = new FileInputStream(new File(path + "Stock_Taking_Detail.jrxml"));
            }
            if (link.equals("gil2")) {
                System.out.println("link value inside summ---" + link);
                input = new FileInputStream(new File(path + "Stock_Taking_Summary.jrxml"));
            }

            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design);
            //    am.openReportUpdate(paramDocId,usrId);

            Map<String, Object> parameters = new HashMap<String, Object>();
            System.out.println("cld-- " + CldId + "org-- " + OrgId + "sloc ----" + SlocId);
            System.out.println("docid---" + DocId + " fyid--" + FyId);
            parameters.put("OrgId", OrgId);
            parameters.put("CldId", CldId);
            parameters.put("SlocId", SlocId);
            parameters.put("DocId", DocId);
            parameters.put("FyId", FyId);
            parameters.put("Path", path);
            // parameters.put("WhId", WhId);

            JasperPrint jasperPrint = null;
            jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();
            String fileName = ReportName + ".pdf";
            JRExporter exporter = null;
            if (true) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + fileName + "\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            }
            try {
                exporter.exportReport();
            } catch (JRException e) {
                throw new ServletException(e);
            } catch (Exception ex) {
            }

            finally {
                if (conn != null && rs != null) {

                    conn.close();

                    rs.close();
                }
                if (ouputStream != null) {
                    try {
                        ouputStream.flush();
                        ouputStream.close();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
