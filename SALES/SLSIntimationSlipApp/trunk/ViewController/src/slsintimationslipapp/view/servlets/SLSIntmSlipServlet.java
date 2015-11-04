package slsintimationslipapp.view.servlets;

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

public class SLSIntmSlipServlet extends HttpServlet {
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
            String path;
            
            
            ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

            rs = ps.executeQuery();
             path = null;
            while (rs.next()) {
                path = rs.getString(1);
                // System.out.println("Path is :" + rs.getString(1));
            }
            path=path+"SLS/";
            if (linkType.equalsIgnoreCase("itmdetail")) {
                reportName = "SLS_Intm_DetailReport";
            }
            System.out.println("Path is "+path);
            InputStream input = new FileInputStream(new File(path + reportName + ".jrxml"));

            //InputStream input = new FileInputStream(new File("D:/Report/SLS_Opp_DetailReport.jrxml"));


            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design);

            //Getting Parameter from the servlet
            
            String DocId = request.getParameter("DocId").toString();
            String Org = request.getParameter("Org").toString();
            String cldid = request.getParameter("cldid").toString();
            Integer slocid = Integer.parseInt(request.getParameter("slocid"));
            String hoOrgid = request.getParameter("hoOrgid").toString(); 
            Integer CatId;
            String assigned;
            String status;
            String docno;
            String eoid;
            String salesman;
            String itmgrp;
            String itmName;
            String Todate;
            String Fromdate;
            Integer currid;
            Integer FormStatus;
           
            
            
            
            //Report Type----------------
          
            String reporttype = "pdf";

             
           
            //putting the parameter to map to report jrxml file

             

            if (request.getParameter("currid").equals("")) {
                currid = null;
            } else {
                /*  Double y = Double.parseDouble(request.getParameter("currid"));
                BigDecimal z = new BigDecimal(y.doubleValue()); */
                currid = Integer.parseInt(request.getParameter("currid"));
            }

            //Fromdate
            if (request.getParameter("fromdate").equals("")) {
                Fromdate = null;
            } else {
                Fromdate = request.getParameter("fromdate");

            }


            //Todate
            if (request.getParameter("todate").equals("")) {
                Todate = null;
            } else {
                Todate = request.getParameter("todate");
            }


            //CatId
            if (request.getParameter("catid").equals("")) {
                CatId = null;
            } else {
                System.out.println("Category ID is-->"+request.getParameter("catid"));
                CatId = Integer.parseInt(request.getParameter("catid"));
                
            }
            //Form status
            if (request.getParameter("formstatus").equals("")) {
                FormStatus = null;
            } else {
                System.out.println("status is --->"+request.getParameter("formstatus"));
                FormStatus = Integer.parseInt(request.getParameter("formstatus"));
                    
            }

            /*      //assigned
            if (request.getParameter("assign").equals("")) {
                assigned = null;
            } else {
                assigned = request.getParameter("assign");
            } */


            //status
            if (request.getParameter("status").equals("")) {
                status = null;
            } else {
                status = request.getParameter("status");
            }


            //oppno
            if (request.getParameter("DocId").equals("")) {
                docno = null;
            } else {
                System.out.println("Doc ID Is -->"+request.getParameter("DocId"));
                docno = request.getParameter("docno");
            }


            //eoid
            if (request.getParameter("eoid").equals("")) {
                eoid = null;
            } else {
                eoid = request.getParameter("eoid");
            }

            //salesman
            if (request.getParameter("salesman").equals("")) {
                salesman = null;
            } else {
                salesman = request.getParameter("salesman");
            }

            //itmgrp
            if (request.getParameter("itmgrp").equals("")) {
                itmgrp = null;
            } else {
                itmgrp = request.getParameter("itmgrp");
            }


            //itmName
            if (request.getParameter("itmName").equals("")) {
                itmName = null;
            } else {
                itmName = request.getParameter("itmName");
            }


            //Report Type
            if (request.getParameter("ReportType").equals("")) {
                reporttype = "pdf";
            } else {
                reporttype = request.getParameter("ReportType");
            }

            //System.out.println(Todate + " --- " + Fromdate);
               
            
             
            
            Map parameters = new HashMap();

            parameters.put("OrgId", Org);
            parameters.put("SlocId", slocid);
            parameters.put("CldId", cldid);
            parameters.put("HoOrgId", hoOrgid);
           /*  parameters.put("EoCatId", CatId);
            parameters.put("FromDate", Fromdate);
            parameters.put("ToDate", Todate);
            parameters.put("ItmId", itmName);
            parameters.put("GrpId", itmgrp);
            parameters.put("EoId", eoid); */
            parameters.put("DocId", DocId);
            /* parameters.put("Status", status);
            parameters.put("SoId", salesman);  *///DocId
            //parameters.put("AssignTo", assigned);
            // parameters.put("CurrId", currid);
            parameters.put("Path", path);
           // parameters.put("Status",FormStatus); 


            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();
            JRExporter exporter = null;


            if ("pdf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachement; filename=\"Sales Intimation.pdf\"");

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
