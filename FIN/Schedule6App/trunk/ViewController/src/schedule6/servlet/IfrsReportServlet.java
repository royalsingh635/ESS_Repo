package schedule6.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
 
import java.math.BigDecimal;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
 
import javax.naming.Context;
import javax.naming.InitialContext;
 
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
 
import javax.sql.DataSource;
 
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;

 

 
 @WebServlet(name = "IfrsReprtServlet", urlPatterns = { "/ifrsreprtservlet" })
 public class IfrsReportServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
 
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
 
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String reportPath = null;
 
        try {
                    Context ctx = new InitialContext();
                    // System.out.println("time befoe connection"+System.currentTimeMillis() % 1000);
                    DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/FINDS");
                    conn = ds.getConnection();
         
                    //  System.out.println("time after connection"+System.currentTimeMillis() % 1000);
                    ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");
         
                    rs = ps.executeQuery();
         
                    // System.out.println("time after executing query"+System.currentTimeMillis() % 1000);
                    
                    while (rs.next()) {
                        reportPath = rs.getString(1);
                        //path="D:\\Report from 220\\Report\\";
                        System.out.println("Path is :" + rs.getString(1));
                    }
               
                String cldId  = request.getParameter("CldId");
                String hoOrgId= request.getParameter("HoOrgId");
                String orgId  = request.getParameter("OrgId");
                String path   = reportPath + "/FIN/";
               
                
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
                Date now = new Date();
                String date = sdfDate.format(now);
                
                
                Integer fyId  =Integer.parseInt( request.getParameter("FyId"));
                String reportType=request.getParameter("ReprtType");
                Integer schId=Integer.parseInt(request.getParameter("SchId"));
                Integer note=request.getParameter("Note") == null ? null : Integer.parseInt(request.getParameter("Note"));
                String reportFor=request.getParameter("Report");
                
                //System.out.println("Date is "+date);
                
                String reportName=null;
             if(reportFor.equals("F"))
                reportName="ScheduleSummary";
             else if(reportFor.equals("N"))
                 reportName="ScheduleDetail"; 
            
            
            Map parameters = new HashMap();
            parameters.put("Path", path);
            parameters.put("Ho_Org_Id", hoOrgId);
            parameters.put("Org_Id", orgId);
            parameters.put("Cld_Id", cldId);
            parameters.put("Date", date);
            parameters.put("Fy_Id", fyId);
            parameters.put("Sch_Id", schId);
            parameters.put("Note", note);

            JasperReport report = (JasperReport) JRLoader.loadObject(path+ reportName + ".jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();
            JRExporter exporter = null;
            if ("pdf".equalsIgnoreCase(reportType)) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".pdf\"");
                exporter = new JRPdfExporter();
                //exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("rtf".equalsIgnoreCase(reportType)) {
                response.setContentType("application/rtf");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".rtf\"");
 
                exporter = new JRRtfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("html".equalsIgnoreCase(reportType)) {
                exporter = new JRXhtmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xls".equalsIgnoreCase(reportType)) {
                response.setContentType("application/csv");
                //response.setHeader("Content-Disposition", "attachement; filename=" + reportName + ".csv\"");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".csv\"");
                exporter = new JRCsvExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
 
 
            } else if ("docx".equalsIgnoreCase(reportType)) {
                response.setContentType("application/docx");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".docx\"");
 
                exporter = new JRDocxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xlsx".equalsIgnoreCase(reportType)) {
                response.setContentType("application/xlsx");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".xlsx\"");
                //response.setHeader("Content-Disposition", "attachement; filename=\"GeneralLedger"+user+".xlsx\"");
 
                exporter = new JRXlsxExporter();
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xml".equalsIgnoreCase(reportType)) {
                response.setContentType("application/xml");
                //response.setHeader("Content-Disposition", "attachement; filename=\"GeneralLedger"+user+".xml\"");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".xml\"");
                exporter = new JRXmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            }
 
 
            try {
 
                exporter.exportReport();
 
            } catch (JRException e) {
                e.printStackTrace();
            } finally {
                if (ouputStream != null) {
                    try {
 
                        if (rs != null) {
                            rs.close();
                        }
                        if (ps != null) {
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
 
 
