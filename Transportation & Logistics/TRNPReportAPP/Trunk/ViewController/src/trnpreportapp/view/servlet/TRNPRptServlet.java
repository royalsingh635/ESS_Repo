package trnpreportapp.view.servlet;

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

@WebServlet(name = "TRNPRptServlet", urlPatterns = { "/trnprptservlet" })
public class TRNPRptServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        //
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String link = request.getParameter("Link");
        String reportName = null;

        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/SLSDS");
            conn = ds.getConnection();


            ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

            rs = ps.executeQuery();
            String path = null;
            while (rs.next()) {
                path = rs.getString(1);
            }
            path = path + "TRNP/";
            System.out.println("path after define--" + path);
            if (link != null) {
                if (link.equalsIgnoreCase("FuelConsDet")) {
                    reportName = "FuelConsumptionDetails";
                } else if (link.equalsIgnoreCase("FuelConsDetVeh")) {
                    reportName = "FuelConsDetByVehicle";
                } else if (link.equalsIgnoreCase("FuelRcptPending")) {
                    reportName = "FuelRcptPending";
                } else if (link.equalsIgnoreCase("FuelAnalysis")) {
                    reportName = "FuelExpensesAnalysis";
                }else if(link.equalsIgnoreCase("PetrolStatnAnalysis")){
                    reportName = "PetrolAnalysisStationWise";
                }else if (link.equalsIgnoreCase("TripAnalysis")){
                    reportName = "TripAnalysis";
                }
            }


            String reportType = null;
            Object stDt = null;
            String fromDate = null;
            Object edDt = null;
            String toDate = null;
            
            Integer custId = null;
            Integer statnId = null;
            Integer driverId = null;
            String vehicleNo = null;
            String suppInvcNo = null;

            String cldId = request.getParameter("CldId");
            Integer slocId = Integer.parseInt(request.getParameter("SlocId"));
            String hoOrgId = request.getParameter("HoOrgId");
            String orgId = request.getParameter("OrgId");
            // For TO date
            if (request.getParameter("ToDate") != null) {
                if (request.getParameter("ToDate").equals("")) {
                    edDt = null;
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    edDt = request.getParameter("ToDate");
                    Date dt = dateFormat.parse(edDt.toString());
                    toDate = dateFormat.format(dt);
                }
            }

            // For From date
            if (request.getParameter("FromDate") != null) {
                if (request.getParameter("FromDate").equals("")) {
                    stDt = null;
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    stDt = request.getParameter("FromDate");
                    Date dt = dateFormat.parse(stDt.toString());
                    fromDate = dateFormat.format(dt);
                }
            }

           //custId 
            if(request.getParameter("CustId")!=null){
                if(request.getParameter("CustId").equals("")){
                    custId = null;
                }else  custId = Integer.parseInt(request.getParameter("CustId"));
            }
            //driver id
            if(request.getParameter("DriverId")!=null){
                if(request.getParameter("DriverId").equals("")){
                    driverId = null;
                }else  driverId = Integer.parseInt(request.getParameter("DriverId"));
            }
            //station id
            if(request.getParameter("StatId")!=null){
                if(request.getParameter("StatId").equals("")){
                    statnId = null;
                }else  statnId = Integer.parseInt(request.getParameter("StatId"));
            }
            //vehicle no
            if(request.getParameter("VehicleNo")!=null){
                if(request.getParameter("VehicleNo").equals("")){
                    vehicleNo = null;
                }else  vehicleNo = request.getParameter("VehicleNo").toString();
            }
            //supp invc no
            if(request.getParameter("supInvcNo")!=null){
                if(request.getParameter("supInvcNo").equals("")){
                    suppInvcNo = null;
                }else  suppInvcNo = request.getParameter("supInvcNo").toString();
            }

            //Report Type
            if (request.getParameter("ReportType") != null) {
                if (request.getParameter("ReportType").equals("")) {
                    reportType = "pdf";
                } else {
                    reportType = request.getParameter("ReportType");
                    System.out.println("Reportype " + reportType);
                }
            }
            
            
            System.out.println("reportype " + reportType);
            Map parameters = new HashMap();
            parameters.put("CldId", cldId);
            parameters.put("SlocId", slocId);
            parameters.put("HoOrgId", hoOrgId);
            parameters.put("OrgId", orgId);
            parameters.put("FromDate", fromDate);
            parameters.put("ToDate", toDate);
            parameters.put("CustId", custId);
            
            parameters.put("DriverId", driverId);
            parameters.put("StationId", statnId);
            parameters.put("VehicleNo", vehicleNo);
            parameters.put("SuppInvcDocId", suppInvcNo);
            
            if (reportType.equalsIgnoreCase("CSV")) {
                parameters.put("ReportType", "E");
            } else
                parameters.put("ReportType", "P");

            parameters.put("Path", path);
     
            System.out.println("Link & Parameters are " + link + " -- " + parameters);
            JasperReport report = (JasperReport) JRLoader.loadObject(path + reportName + ".jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();
            JRExporter exporter = null;
            if ("pdf".equalsIgnoreCase(reportType)) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".pdf\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, true);
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
            } else if ("CSV".equalsIgnoreCase(reportType)) {
                response.setContentType("application/csv");
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
                exporter = new JRXlsxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xml".equalsIgnoreCase(reportType)) {
                response.setContentType("application/xml");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".xml\"");
                exporter = new JRXmlExporter();
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
        //
    
}
