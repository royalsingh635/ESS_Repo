package daybookandvoucherprintapp.view.servlet;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;
import java.util.HashMap;


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




public class DayBookAndVoucherPrintServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        try{
        
        
        Connection conn = null;
              
                   
                   Context ctx = new InitialContext();
                   DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/FINDS");
                   conn=ds.getConnection();
                   
                           System.out.println("getting the connection"+conn);
               
        
          
        InputStream input = new FileInputStream(new File("D:/Report/Print_voucher.jrxml"));
        System.out.println("input file is...>>>>>"+input);
        JasperDesign design = JRXmlLoader.load(input);
        JasperReport report = JasperCompileManager.compileReport(design); 
          
            
          
              
            
            
            BigDecimal AmountX1=null ;
            BigDecimal AmountX2 = null;
        //    BigDecimal CoaId = null;
           
           
            String toDate = String.valueOf(request.getParameter("todate"));
            String fromDate = String.valueOf(request.getParameter("fromdate"));
           // String cogId = String.valueOf(request.getParameter("cogid"));
            String Org = String.valueOf(request.getParameter("organisation"));
            String VocNo = String.valueOf(request.getParameter("vouchernumber"));
            String reporttype =request.getParameter("ReportType");
            Integer VouType =null;
            
           
            
            
            //Voucher Type
            if(request.getParameter("voutype").equals("")){
            VouType=null;
            System.out.println("voucher typoe is----->>>from if block"+VouType);
            }
            else {
            VouType=Integer.parseInt(request.getParameter("voutype"));
                System.out.println("voucher typoe is----->>>from elsez block"+VouType);
            }
            
            
            
            
            
            //AmountX1--------------
            if (request.getParameter("amountx1").equals("")){
            
                AmountX1=null;
                
            }
            else{
                
                Double y=Double.parseDouble(request.getParameter("amountx1"));
                BigDecimal z=new BigDecimal(y.doubleValue());
                
                
                AmountX1=z;
            }
          
            
            
            
            
            
            //AmountX2--------------
            if(request.getParameter("amountx2").equals("")){
                AmountX2=null;
            }
                    else{
                        Double y=Double.parseDouble(request.getParameter("amountx2"));
                        BigDecimal z=new BigDecimal(y.doubleValue());
                        AmountX2=z; 
                    }
            
            
            
            
            
            
            
            
            
            
         /*    //COA_ID--------------
            if(request.getParameter("coaid").equals("")){
                CoaId=null;
            }
            else{
                Double y=Double.parseDouble(request.getParameter("coaid"));
                BigDecimal z=new BigDecimal(y.doubleValue());
                CoaId=z; 
            }
             */
           
           //Report Type:----------------------
            if(reporttype.trim().equals("")){
                reporttype="PDF";
            }
            
                 Map<String,Object> parameters = new HashMap<String,Object>();
          
                 parameters.put("Org_id", Org.trim());
                 parameters.put("Amount-X1", AmountX1);
                 parameters.put("Amount-X2", AmountX2);   
                 parameters.put("ToDate", toDate.trim());
                 parameters.put("FromDate",fromDate.trim());
                // parameters.put("Cog_Id", cogId);   
               //  parameters.put("Coa_Id", CoaId);   
                 parameters.put("Voucher_No", VocNo.trim());  
                 parameters.put("VoucherType", VouType);  
            System.out.println("organisation ........>>>"+Org);
            System.out.println("amountx1 ........>>>"+AmountX1);
            System.out.println("amount x2 ........>>>"+AmountX2);
            System.out.println("todate ........>>>"+toDate);
            System.out.println("fromdate ........>>>"+fromDate);
            System.out.println("vouchernumber ........>>>"+VocNo);         
            System.out.println("voucher typoe from collection ........>>>"+VouType);
            
            
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
          
        
        OutputStream ouputStream = response.getOutputStream();
        
          
        JRExporter exporter = null;
        System.out.println("Len--"+reporttype.length());
        System.out.println(reporttype.trim() +" -uu---rt"+ reporttype.trim().equalsIgnoreCase("PDF"));
                      
            if(reporttype.trim().equals("PDF"))
                    {
                System.out.println("generated pffd formet from pdf block");
                    response.setContentType("application/pdf");
                    
                    response.setHeader("Content-Disposition", "attachement; filename=\"Print_voucher.pdf\"");
                    
                     
                    exporter = new JRPdfExporter();
                     
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                    }
            else if( "rtf".equalsIgnoreCase(reporttype) )
                    {
                    response.setContentType("application/rtf");
                    response.setHeader("Pragma", "no-cache");
                    response.setHeader("Content-Disposition", "attachement; filename=\"Print_voucher.rtf\"");
                    
                    exporter = new JRRtfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                    }
            else if( "html".equalsIgnoreCase(reporttype) )
                    {
                    exporter = new JRHtmlExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                    }
            else if( "xls".equalsIgnoreCase(reporttype) )
                    {
                    response.setContentType("application/xls");
                    response.setHeader("Content-Disposition", "attachement; filename=\"Print_voucher.xls\"");
                    
                    exporter = new JRXlsExporter();
                        exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                    }
            else if( "docx".equalsIgnoreCase(reporttype) )
                    {
                    response.setContentType("application/docx");
                    response.setHeader("Content-Disposition", "attachement; filename=\"Print_voucher.docx\"");
                    
                    exporter = new JRDocxExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                    }
             else if( "xlsx".equalsIgnoreCase(reporttype) )
                    {
                    response.setContentType("application/xlsx");
                    response.setHeader("Content-Disposition", "attachement; filename=\"Print_voucher.xlsx\"");
                    
                    exporter = new JRXlsxExporter();
                    exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                    }
            else if( "xml".equalsIgnoreCase(reporttype) )
                   {
                   response.setContentType("application/xml");
                   response.setHeader("Content-Disposition", "attachement; filename=\"Print_voucher.xml\"");
                   
                   exporter = new JRXmlExporter();
                   exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                   exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                   }
        
        try
        {
        exporter.exportReport();
        }
        catch (JRException e)
        {
        throw new ServletException(e);
        }
        finally
        {
        if (ouputStream != null)
        {
        try
        {
            if(conn != null){
                            conn.close();
                        }
        ouputStream.flush(); 
        ouputStream.close();
        
        }
        catch (IOException ex)
        {
          System.out.println(ex.getMessage());
          throw (ex);
        }
        }
        }
        
        }catch(Exception ex) {
          ex.printStackTrace();
        }
        
        
        }
    
}
