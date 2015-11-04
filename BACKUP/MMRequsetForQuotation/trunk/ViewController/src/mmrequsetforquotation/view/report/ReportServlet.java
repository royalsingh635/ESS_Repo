package mmrequsetforquotation.view.report;

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
import javax.servlet.http.*;

import javax.sql.DataSource;

import mmrequsetforquotation.model.services.mmRfqAMImpl;

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

import oracle.jbo.Row;

public class ReportServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "application/pdf";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        downloadReport(request,response);
     }
    
    
    
    protected void downloadReport(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        
        String fyVal =request.getParameter("fyId");
            Integer fyId=null;
        if(fyVal != null && fyVal.length()>0)
        { fyId = Integer.parseInt(request.getParameter("fyId").toString());}
        
        String rstfqNo=(String)request.getParameter("rfqId");
        String filename=rstfqNo+"_"+fyId+".pdf";
        response.setContentType("application/pdf");  
        response.setHeader("Content-Disposition", "attachment; filename=\""+filename+"\""); 
            PrintWriter out = null;
            BufferedInputStream buffIn =null;

                try {
            buffIn = new BufferedInputStream(new FileInputStream("D:/send/"+filename));
            int iBuf;
            byte[] buffer = new byte[1024];
            out = response.getWriter();        
            while ((iBuf=buffIn.read())!=-1)
                out.write((byte) iBuf);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
           openReportFun(request,response);
        }
        if(buffIn !=null)
           buffIn.close();  
        if(out !=null)
            {   out.flush();
                out.close();
            }
        }
    
    
    
    
    private void performTask(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                            IOException {
 
        try{
            
                 Integer fyId = Integer.parseInt(request.getParameter("fyId"));
                 String rstfqNo=(String)request.getParameter("rfqId");
                 String orgId=(String)request.getParameter("orgId");
                 String cldId=(String)request.getParameter("cldId");
                 Integer slocId = Integer.parseInt(request.getParameter("slocId"));
            
                 PreparedStatement st = null;
                 String amDef = "mmrequsetforquotation.model.services.mmRfqAMImpl";
                 String config = "mmRfqAMLocal";
                 mmRfqAMImpl am = (mmRfqAMImpl)Configuration.createRootApplicationModule(amDef,config);
                 st = am.getDBTransaction().createPreparedStatement("select 1 from dual", 0);
                 Connection conn=null;
                 conn = st.getConnection();
                 ResultSet res=null;
            
                                    InputStream input = new FileInputStream(new File("D:/Report/RFQEmail.jrxml"));
                                    JasperDesign design = JRXmlLoader.load(input);
                                    JasperReport report = JasperCompileManager.compileReport(design);
                      
                                  Map<String,Object> parameters = new HashMap<String,Object>();
                                  parameters.put("rfqNo", rstfqNo);
                                  parameters.put("orgId",orgId);
                                  parameters.put("cldId",cldId);
                                  parameters.put("slocId",slocId);
                                  parameters.put("fyId",fyId);
                                  JasperPrint jasperPrint=null;
                                  jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
                                  OutputStream ouputStream = response.getOutputStream();
                                  String fileName = rstfqNo+".pdf";
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
                                        if (conn != null && st !=null && res != null)
                                        {
                                      
                                            conn.close();
                                            st.close();
                                            res.close();
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
    
    public BindingContainer getBindings() {
           return BindingContext.getCurrent().getCurrentBindingsEntry();
       }
    
    private void openReportFun(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                            IOException {
        
    
        try{
                 String rstfqNo=(String)request.getParameter("rfqId");
                 String orgId=(String)request.getParameter("orgId");
                 String cldId=(String)request.getParameter("cldId");
                 Integer slocId = Integer.parseInt(request.getParameter("slocId"));
              Integer usrId=Integer.parseInt(request.getParameter("usrId").toString());
                /* OperationBinding   update = getBindings().getOperationBinding("updateRFQStatus"); 
                update.execute(); */
                 String paramDocId=(String)request.getParameter("param");
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
                    String amDef = "mmrequsetforquotation.model.services.mmRfqAMImpl";
                    String config = "mmRfqAMLocal";
                    mmRfqAMImpl am = (mmRfqAMImpl)Configuration.createRootApplicationModule(amDef,config);
                 ResultSet res=null;
          
                                    InputStream input = new FileInputStream(new File(path+"RFQ.jrxml"));
                                    JasperDesign design = JRXmlLoader.load(input);
                                    JasperReport report = JasperCompileManager.compileReport(design);
                                    am.openReportUpdate(paramDocId,usrId);
        System.out.println("complete path is "+input+path);
                                  Map<String,Object> parameters = new HashMap<String,Object>();
                                  parameters.put("rfqNo", rstfqNo);
                                  parameters.put("orgId",orgId);
                                  parameters.put("cldId",cldId);
                                  parameters.put("slocId",slocId);
                                  parameters.put("Path",path);
                    
                                  JasperPrint jasperPrint=null;
                                  jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
                                  OutputStream ouputStream = response.getOutputStream();
                                  String fileName = rstfqNo+".pdf";
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
                                        if (conn != null && res != null)
                                        {
                                      
                                            conn.close();
                                           // st.close();
                                            res.close();
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
