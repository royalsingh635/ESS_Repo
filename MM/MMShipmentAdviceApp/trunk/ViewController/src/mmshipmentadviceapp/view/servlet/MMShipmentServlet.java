package mmshipmentadviceapp.view.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DateFormat;

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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;

@WebServlet(name = "MMShipmentServlet", urlPatterns = { "/mmshipmentservlet" })
public class MMShipmentServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /*   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>MMShipmentServlet</title></head>");
        out.println("<body>");
        out.println("<p>The servlet has received a GET. This is the reply.</p>");
        out.println("</body></html>");
        out.close();
    } */
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
              response.setContentType(CONTENT_TYPE);
              //
              Connection conn = null;
              PreparedStatement ps = null;
              ResultSet rs = null;
              try {
                  Context ctx = new InitialContext();
                  DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MMDS");
                  conn = ds.getConnection();


                  ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

                  rs = ps.executeQuery();
                  String path = null;
                  while (rs.next()) {
                      path = rs.getString(1);
                  }
                  path = path + "MM/";
                  System.out.println("path after define--" + path);
                  String shipDocId = null;

                  String cldId = request.getParameter("CldId");
                  Integer slocId = Integer.parseInt(request.getParameter("SlocId"));
                  String hoOrgId = request.getParameter("HoOrgId");
                  String orgId = request.getParameter("OrgId");
                  
                  //Shipment doc doc id
                  if (request.getParameter("DocId") != null) {
                      if (request.getParameter("DocId").equals("")) {
                        shipDocId = null;
                      } else {
                          shipDocId = request.getParameter("DocId"); 
                      }
                  }
                  
                  
                  
                //  System.out.println("reportype " + reportType);
                  Map parameters = new HashMap();
                  parameters.put("CldId", cldId);
                  parameters.put("SlocId", slocId);
                  parameters.put("HoOrgId", hoOrgId);
                  parameters.put("OrgId", orgId);
                  parameters.put("ShpDocId",shipDocId);
                
                  
                  parameters.put("Path", path);
           
                  System.out.println("Link & Parameters are " + parameters);
                  JasperReport report = (JasperReport) JRLoader.loadObject(path + "ShipmentAdviceReport.jasper");
                  JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
                  OutputStream ouputStream = response.getOutputStream();
                  JRExporter exporter = null;
                      response.setContentType("application/pdf");
                      response.setHeader("Content-Disposition", "attachement; filename=\"" + "ShipmentAdvice" + ".pdf\"");
                      exporter = new JRPdfExporter();
                      exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, true);
                      exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                      exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                  try {
                      exporter.exportReport();
                  } catch (JRException e) {
                      throw new ServletException(e);
                  } finally {
                      if (ouputStream != null) {
                          try {
                              ouputStream.flush();
                              ouputStream.close();
                          } catch (IOException ex) {
                              throw (ex);
                          }
                      }
                  }
              } catch (Exception ex) {
                  ex.printStackTrace();
              } finally {
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
                  } catch (SQLException e) {
                      e.printStackTrace();
                  }
              }
              }
}
