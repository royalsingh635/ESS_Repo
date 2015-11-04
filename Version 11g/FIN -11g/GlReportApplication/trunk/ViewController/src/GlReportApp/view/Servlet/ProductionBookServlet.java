package GlReportApp.view.Servlet;

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
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class ProductionBookServlet extends HttpServlet {
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

            /*  PreparedStatement st = null;

    String amDef = "GlReportApplication.model.module.GlReportAMImpl";
            String config = "GlReportAMLocal";
           GlReportAMImpl am =
                (GlReportAMImpl)Configuration.createRootApplicationModule(amDef,
                                                                                  config);
    st = am.getDBTransaction().createPreparedStatement("select 1 from dual", 0);
    Connection conn = st.getConnection(); */


            Context ctx = new InitialContext();
           // System.out.println("time befoe connection"+System.currentTimeMillis() % 1000);
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/FINDS");
            conn = ds.getConnection();
          //  System.out.println("time after connection"+System.currentTimeMillis() % 1000);
            ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");
    
            rs = ps.executeQuery();
            
           // System.out.println("time after executing query"+System.currentTimeMillis() % 1000);
            String path = null;
            while (rs.next()) {
                path = rs.getString(1);
                //path="D:\\Report from 220\\Report\\";
                //System.out.println("Path is :" + rs.getString(1));
            }
    
           

           
           

            String reporttype = request.getParameter("ReportType");
            String Org = request.getParameter("organisation");
            String Todate = request.getParameter("todate");
            String Fromdate = request.getParameter("fromdate");
            BigDecimal AmountX1 = null;
            BigDecimal AmountX2 = null;
            Integer CoaId = null;
            String PostFlag = request.getParameter("PostFlag");
            // System.out.println("vouchers type is---->>"+PostFlag);
            String CogId = request.getParameter("cogid");

            String cldid = request.getParameter("cldid");
            String hoOrgid = request.getParameter("hoorgid");

            Integer naid;
            Integer slocid = Integer.parseInt(request.getParameter("slocid"));
            Integer i=Integer.parseInt(request.getParameter("detail"));

           // System.out.println("time before parameter setting"+System.currentTimeMillis() % 1000);
            
            //NAID
            if (request.getParameter("naid").equals("")) {

                naid = null;

            } else {


                naid =Integer.parseInt(request.getParameter("naid"));
            }


            //Report Type
            if (reporttype.equals("")) {
                reporttype = "pdf";
            }


            //AmountX1----------------
            if (request.getParameter("amountx1").equals("")) {

                AmountX1 = null;

            } else {

                Double y = Double.parseDouble(request.getParameter("amountx1"));
                BigDecimal z = new BigDecimal(y.doubleValue());
                AmountX1 = z;
            }


            //AmountX2----------------

            if (request.getParameter("amountx2").equals("")) {
                AmountX2 = null;
            } else {
                Double y = Double.parseDouble(request.getParameter("amountx2"));
                BigDecimal z = new BigDecimal(y.doubleValue());
                AmountX2 = z;
            }


            //CoaId-----------
            if (request.getParameter("coaid").equals("")) {
                CoaId = null;
            } else {
                /* Double y = Double.parseDouble(request.getParameter("coaid"));
                BigDecimal z = new BigDecimal(y.doubleValue()); */
                CoaId = Integer.parseInt(request.getParameter("coaid"));
            }

            System.out.println("org "+Org+" amt x1 "+AmountX1+" x2 "+AmountX2+" to "+Todate+" fro "+Fromdate+" coa "+CoaId+" cog "+CogId+" post "+PostFlag+" sloc "+slocid+" cld "+cldid+" ho "+hoOrgid+" path "+path);
            Map parameters = new HashMap();

            parameters.put("Org_ID", Org);
          //  parameters.put("Amount-X1", AmountX1);
           // parameters.put("Amount-X2", AmountX2);
            parameters.put("ToDate", Todate);
            parameters.put("FromDate", Fromdate);
            parameters.put("Coa_ID", CoaId);
           // parameters.put("COGID", CogId);
           // parameters.put("PostFlag", PostFlag);
            parameters.put("SlocId", slocid);
            parameters.put("cld_Id", cldid);
          //  parameters.put("HoOrgId", hoOrgid);
           // parameters.put("NA_ID", naid);
            parameters.put("Path",path);
            
            InputStream input=null;
            String detail=null;
            
            
                if(i==0){
              
                  input = new FileInputStream(new File(path + "book_production.jrxml"));
                  detail="";
                }
            if(i==1){
                input = new FileInputStream(new File(path + "Book_production_Title_Wise.jrxml"));
                detail="Detail";
                
            }
            //InputStream input = new FileInputStream(new File("D:/Report/GeneralLedger.jrxml"));

            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design);
            
          

            System.out.println("time before filling"+System.currentTimeMillis() ); 
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();
            System.out.println("time afetr filling"+System.currentTimeMillis() ); 

            JRExporter exporter = null;
            

            if ("pdf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/pdf");

                response.setHeader("Content-Disposition", "attachement; filename=\"BookProduction"+detail+".pdf\"");


                exporter = new JRPdfExporter();
                //exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("rtf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/rtf");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Content-Disposition", "attachement; filename=\"BookProduction"+detail+".rtf\"");

                exporter = new JRRtfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("html".equalsIgnoreCase(reporttype)) {
                exporter = new JRXhtmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xls".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xls");
                response.setHeader("Content-Disposition", "attachement; filename=\"BookProduction"+detail+".xls\"");

                exporter = new JRXlsExporter();
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("docx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/docx");
                response.setHeader("Content-Disposition", "attachement; filename=\"BookProduction"+detail+".docx\"");

                exporter = new JRDocxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xlsx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xlsx");
                response.setHeader("Content-Disposition", "attachement; filename=\"BookProduction"+detail+".xlsx\"");

                exporter = new JRXlsxExporter();
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xml".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xml");
                response.setHeader("Content-Disposition", "attachement; filename=\"BookProduction"+detail+".xml\"");

                exporter = new JRXmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            }

          
              
            try {
             
              exporter.exportReport();
                
            } catch (JRException  e) {
                e.printStackTrace();
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
                        
                        throw (ex);
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
