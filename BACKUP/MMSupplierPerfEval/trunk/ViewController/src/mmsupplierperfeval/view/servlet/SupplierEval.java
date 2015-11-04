package mmsupplierperfeval.view.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.naming.NamingException;

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
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.export.JRXhtmlExporter;



public class SupplierEval extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        
        response.setContentType(CONTENT_TYPE);
        
    Connection conn = null;
    try {
        Context ctx = new InitialContext();
        DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/MMDS");
        conn = ds.getConnection();
        PreparedStatement ps =
            conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

        ResultSet rs = ps.executeQuery();
        String path = null;
        while (rs.next()) {
            path = rs.getString(1);
            //System.out.println("Path is :" + rs.getString(1));
        }

        String reportName=request.getParameter("reportName");
        InputStream input = null;
       // InputStream input = new FileInputStream(new File("D:/Report/GateEntry.jrxml"));
      
        
        
        String reporttype="pdf";
        
        String Org = String.valueOf(request.getParameter("OrgId"));
        reporttype = request.getParameter("reporttype");
       // System.out.println("Param report type="+reporttype);
        Integer Sloc=Integer.parseInt(request.getParameter("SlocId").toString());
        String Cld = String.valueOf(request.getParameter("CldId"));
        String HoOrg=String.valueOf(request.getParameter("HoOrgId"));
        Integer EvaluatedBy;
        
       
        String FrmDate;
        String ToDate;
        Integer suppid;
        String EvalId;    
    
    if (request.getParameter("EvalId") != null) {
        if (request.getParameter("EvalId").equals("")) {
            EvalId = null;
            System.out.println("Null EvalId");
        } else {
            EvalId = (request.getParameter("EvalId")).toString();
            System.out.println(EvalId);
        }
    } else {
        System.out.println("Null EvalId");
        EvalId = null;
    }    
        
        
        //EvaluatedBy----------------
        if (request.getParameter("EvaluatedBy") != null) {
            if (request.getParameter("EvaluatedBy").equals("")) {
                EvaluatedBy = null;
                System.out.println("Null EvaluatedBy");
            } else {
                EvaluatedBy = Integer.parseInt(request.getParameter("EvaluatedBy").toString());
                System.out.println(EvaluatedBy);
            }
        } else {
            System.out.println("Null EvaluatedBy");
            EvaluatedBy = null;
        }
       
        
        
        // From Date----------
        
        /* if(request.getParameter("FromDate")!=null){
            if(request.getParameter("FromDate").equals("")){
                FrmDate=null;
                System.out.println("Frm date is null");
            }
            else{
               FrmDate =request.getParameter("FromDate").toString();
                System.out.println("frm date not null "+FrmDate);
            }
        }
            else{
                FrmDate=null;
                System.out.println("Frm date null");
            } */
        // To Date-----------
     /*   if(request.getParameter("ToDate")!=null){
            if(request.getParameter("ToDate").equals("")){
                ToDate=null;
            }
            else{
                ToDate=request.getParameter("ToDate").toString();
              
            }
        }
            else{
                ToDate=null;
            }*/
        
        // supplier id----------------------------------------
        
        if(request.getParameter("suppid")!=null){
            if(request.getParameter("suppid").equals("")){
                suppid=null;
            }
            else{
               suppid= Integer.parseInt(request.getParameter("suppid").toString());
            }
        }
        else{
            suppid=null;
        }
        //Report Type:----------------------
      /*  if (reporttype.equals("")) {
            reporttype = "pdf";
        }*/
        System.out.println("Report name="+reportName);
        Map parameters = new HashMap();
        
        if(reportName.equalsIgnoreCase("Supplier_Evaluation_Detail")){
        input = new FileInputStream(new File(path + "Supplier_Evaluation_Detail.jrxml"));
            parameters.put("OrgId", Org);
            parameters.put("CldId", Cld);
            parameters.put("SlocId", Sloc);
            parameters.put("EvaluatedBy", EvaluatedBy);
            parameters.put("EvalId", EvalId);
            parameters.put("suppid", suppid);
            parameters.put("Path", path);
            parameters.put("reportName", reportName);
            
            System.out.println("Org id:"+Org+" EvalId "+EvalId+"supplier--"+suppid+ "evalby--"+EvaluatedBy+"report type "+reporttype);
        }
    
    
         System.out.println("afte put params---");   
        JasperDesign design = JRXmlLoader.load(input);
        JasperReport report = JasperCompileManager.compileReport(design);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
        OutputStream ouputStream = response.getOutputStream();
        JRExporter exporter = null;
        
       
            System.out.println("inside pdf------");
            response.setContentType("application/pdf");

            response.setHeader("Content-Disposition", "attachement; filename=\""+reportName+".pdf\"");


            exporter = new JRPdfExporter();
            System.out.println("exporter is-----"+exporter);
            exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, true);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
        
        
                try {
            exporter.exportReport();
        } 
        catch (JRException e) {
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
                    //System.out.println(ex.getMessage());
                    throw (ex);
                }
            }
        }
    }
    catch (Exception ex) {
                ex.printStackTrace();
    }
    } 
    

    }


