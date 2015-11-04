package mminvoice.view.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import oracle.jbo.client.Configuration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

import mminvoice.model.services.MMInvoiceAMImpl;

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

public class MMInvoiceReportServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "application/pdf";
    @SuppressWarnings("compatibility:6171335982984910330")
    private static final long serialVersionUID = 1L;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        Integer fyId = Integer.parseInt(request.getParameter("fyId").toString());
        String orgId=request.getParameter("orgId");
        String cldId=request.getParameter("cldId");
        Integer slocId = Integer.parseInt(request.getParameter("slocId").toString());
        String docId = request.getParameter("docId");
        String invoNo =request.getParameter("InvcNo");
        String type =request.getParameter("InvcType");
        
        request.setAttribute("orgId", orgId);
        request.setAttribute("cldId", cldId);
        request.setAttribute("slocId", slocId);
        request.setAttribute("docId", docId);
        request.setAttribute("InvcNo", invoNo);
        request.setAttribute("InvcType", type);
        request.setAttribute("fyId", fyId);
        
        
        performTask(request,response);
      
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        performTask(request,response);
    }
    
    private void performTask(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                IOException {
            response.setContentType("application/pdf");
            try{
                
                     Integer fyId = Integer.parseInt(request.getAttribute("fyId").toString());
                     String orgId=(String)request.getAttribute("orgId");
                     String cldId=(String)request.getAttribute("cldId");
                     Integer slocId = Integer.parseInt(request.getAttribute("slocId").toString());
                     String docId = (String)request.getAttribute("docId");
                     String invoNo =(String)request.getAttribute("InvcNo");
                     Integer typeNo =Integer.parseInt(request.getAttribute("InvcType").toString());
                
                /*      System.out.println("FyId : "+fyId);
                        System.out.println("orgId : "+orgId);
                        System.out.println("cldId : "+cldId);
                        System.out.println("slocId : "+slocId);
                        System.out.println("docId : "+docId);
                        System.out.println("invoNo : "+invoNo);
                 */
                     PreparedStatement st = null;
                     String amDef = "mminvoice.model.services.MMInvoiceAMImpl";
                     String config = "MMInvoiceAMLocal";
                     MMInvoiceAMImpl am = (MMInvoiceAMImpl)Configuration.createRootApplicationModule(amDef,config);
                     st = am.getDBTransaction().createPreparedStatement("select 1 from dual", 0);
                     Connection conn=null;
                     conn = st.getConnection();
                
                        PreparedStatement ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");
                        ResultSet rs = ps.executeQuery();
                                    String path = null;
                                    while (rs.next()) {
                                        path = rs.getString(1);
                                    }
          //  System.out.println("Connection : "+conn);
                
                        ResultSet res=null;
                        InputStream input=null;
                       
                                   if(typeNo == 455 || typeNo == 457 || typeNo == 492 || typeNo == 739 || typeNo == 737)
                                        {input = new FileInputStream(new File(path+"PurchaseInvoiceReport.jrxml"));}
                                    
                                   if(typeNo == 456 || typeNo == 716||typeNo == 738)
                                        {input = new FileInputStream(new File(path+"StockTransferInvoiceReport.jrxml"));}
                                                    
                                     JasperDesign design = JRXmlLoader.load(input);
                                     JasperReport report = JasperCompileManager.compileReport(design);
                          
                                      Map<String,Object> parameters = new HashMap<String,Object>();
                                      parameters.put("DocId", docId);
                                      parameters.put("OrgId",orgId);
                                      parameters.put("CldId",cldId);
                                      parameters.put("SlocId",slocId);
                                      parameters.put("InvcType",typeNo);
                                      parameters.put("Path",path);
                                      parameters.put("AuthStat", "Y");
                                      parameters.put("FyId",fyId);
                
                                      JasperPrint jasperPrint=null;
                                      jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
                                      OutputStream ouputStream = response.getOutputStream();
                                      String fileName = invoNo+".pdf";
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
                                        catch(Exception ex) {ex.printStackTrace();}
                                      
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
            
     //       System.out.println("END OF FUN");
        
        }
}
