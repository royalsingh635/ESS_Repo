package mmquotation.view.servlet;

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

public class MmQuotationReportServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        openReportFun(request,response);
    }
    private void openReportFun(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                            IOException {
        
    
        try{
                 String quotNo=(String)request.getParameter("QuotNo");
                 String orgId=(String)request.getParameter("ORG_ID");
                 String cldId=(String)request.getParameter("CLD_ID");
                 Integer slocId = Integer.parseInt(request.getParameter("SLOC_ID"));
                 String DocId=request.getParameter("DocId").toString();
                 Integer supplierId = Integer.parseInt(request.getParameter("SupplierId"));
            System.out.println("quotNo  "+quotNo+" orgId  "+orgId+" cldId  "+cldId+" slocId "+slocId+" DocId "+DocId+" supplierId "+supplierId);
                    Connection conn = null;
                    Context ctx = new InitialContext();
                    DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/MMDS");
                    conn = ds.getConnection();
                    PreparedStatement ps =
                        conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

                    ResultSet rs = ps.executeQuery();
                    String path = null;
                    while (rs.next()) {
                        path = rs.getString(1);
                    }

                                    InputStream input = new FileInputStream(new File(path+"Quotation.jrxml"));
                                    JasperDesign design = JRXmlLoader.load(input);
                                    JasperReport report = JasperCompileManager.compileReport(design);
                //    am.openReportUpdate(paramDocId,usrId);
        
                                  Map<String,Object> parameters = new HashMap<String,Object>();
                                    System.out.println("--"+cldId+"----"+orgId+"------"+slocId);
                    parameters.put("DocId", DocId);
                    parameters.put("OrgId", orgId);
                    parameters.put("SlocId", slocId);
                    parameters.put("CldId", cldId);
                     parameters.put("supplierId", supplierId);
                     parameters.put("Path", path);
                    
                     
                                  JasperPrint jasperPrint=null;
                                  jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
                                  OutputStream ouputStream = response.getOutputStream();
                                  String fileName = quotNo+".pdf";
                                 JRExporter exporter = null;
                                         if(true)
                                                 {
                                                 response.setContentType("application/pdf");
                                                 response.setHeader("Content-Disposition", "attachement; filename=\""+fileName+"\"");
                                                 exporter = new JRPdfExporter();
                                                 exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                                                 exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                                                 }
                                  try
                                    {exporter.exportReport();}
                                    catch (JRException e)
                                    {throw new ServletException(e);}
                                    catch(Exception ex) {}
                                  
                                    finally
                                    {
                                        if (conn != null && rs != null)
                                        {
                                      
                                            conn.close();
                                            rs.close();
                                        }
                                        if(ouputStream != null)
                                        {
                                                   try
                                                      {
                                                      ouputStream.flush(); 
                                                      ouputStream.close();
                                                      }
                                                      catch (IOException ex)
                                                      {System.out.println(ex.getMessage());}
                                        }
                                    }
               
                }catch(Exception ex){ex.printStackTrace();}
    
    }
    }
