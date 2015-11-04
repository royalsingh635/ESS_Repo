package hcmsalaryreportapp.view.servlet;

import java.io.IOException;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import oracle.jbo.domain.Timestamp;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//@WebServlet(name = "HcmSalaryReportServlet", urlPatterns = { "/hcmsalaryreportservlet" })
public class HcmSalaryReportServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String link = request.getParameter("link");
        String reportName = null;
        if (request != null) {
            System.out.println("Request sent by(getRemoteAddr)=" + request.getRemoteAddr());
            System.out.println("Request sent by(getRemoteUser)=" + request.getRemoteUser());
            System.out.println("Request sent by(getRemoteHost)=" + request.getRemoteHost());
        }
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/HCMDS");
            conn = ds.getConnection();
            System.out.println("Conn=" + conn);

            ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");
            //System.out.println(request.getParameter("desg"));
            rs = ps.executeQuery();
            String path = null;
            while (rs.next()) {
                path = rs.getString(1);
            }
            if (link.equalsIgnoreCase("Report") && request.getParameter("fileName") != null) {
               path = path + "Portal/";
            } else {
               path = path + "HCM/";
            }
           // path="D:/newreport/";
            System.out.println("value of link=" + link);
            if (link != null) {
                if (link.toString().equalsIgnoreCase("PaySlip")) {
                    reportName = "Salary_Slip";
                } else if (link.equalsIgnoreCase("ESIReport")) {
                    reportName = "ESI_CHALLAN";
                } else if (link.equalsIgnoreCase("PFReport")) {
                    reportName = "Pf_Challan";
                } else if (link.equalsIgnoreCase("SalReg")){
                    reportName = "Salary_Register";
                } else if (link.equalsIgnoreCase("AttReport")){
                    reportName = "Atten_Register";
                }else if (link.equalsIgnoreCase("SalStruct")){
                    reportName = "Salary_Structure";
                }else if (link.equalsIgnoreCase("idreg")){
                    reportName = "IdentificationRegister";
                }else if (link.equalsIgnoreCase("ctc")){
                    reportName = "StaffCTCDetails";
                }else if (link.equalsIgnoreCase("esidet")){
                    reportName = "ESIMonth";
                }else if (link.equalsIgnoreCase("pfdet")){
                    reportName = "PF_Detail";
                }else if (link.equalsIgnoreCase("daily")){
                    reportName = "DailyHours_Detail";
                }else if (link.equalsIgnoreCase("monthly")){
                    reportName = "EmployeesWiseMonthlySalarySummary";
                }else if (link.equalsIgnoreCase("leave")){
                    reportName = "LeaveDetail";
                }
                else if(link.equalsIgnoreCase("mpesa")) {
                    reportName = "MPesa";
                }
                else if(link.equalsIgnoreCase("bank")) {
                    reportName = "Bank_Payroll";
                }
                
                else if(link.equalsIgnoreCase("nhif")) {
                    reportName = "NHIF_Monthly_Payroll";
                }
                else if(link.equalsIgnoreCase("nssf")) {
                    reportName = "NSSF_Report";
                }
                else if(link.equalsIgnoreCase("payeReturn")) {
                    reportName = "Paye_Return";
                }
                
               // EmpList Report
               else if(link.equalsIgnoreCase("empList")) {
                   reportName = "Employee_List";
               }
                
             // Holiday List
             else if(link.equalsIgnoreCase("holidayList")) {
                 reportName = "HolidayListReport";
             }
              
              // Leave Balance Summary
              else if(link.equalsIgnoreCase("leaveBalSum")) {
                  reportName = "LeaveBalance_Summary";
              }
                 // For SuperAnnuation Summary
                else if(link.equalsIgnoreCase("superAnnuaSumm")) {
                    reportName = "Super_annuation_Summary";
                }
                 
              // For LWF Report
                
              else if(link.equalsIgnoreCase("lwf")) {
                  reportName = "LWF_Report";
              }
              
              //For Bank Lettr Report
                else if(link.equalsIgnoreCase("bnkLettr")) {
                    reportName = "Bank_Letter_Report";
                }
                
                //For SuperAnnuation data
                
                else if(link.equalsIgnoreCase("superAnnuData")) {
                    reportName = "Superannuation_Data";
                }
                
                // For gratuity
                else if(link.equalsIgnoreCase("gratuity")) {
                    reportName = "Gratuity_Report";
                }
              
                  // Full_n_Final
                  else if(link.equalsIgnoreCase("fullNfinal")) 
                {
                      reportName = "Full_n_Final";
                  }
                  
                 
                //Early Departure Report
              
                else if(link.equalsIgnoreCase("earlyDepReport")) 
                {
                    reportName = "Early_Departure_report";
                }


                // Late arrival Report
                else if(link.equalsIgnoreCase("lateArrReport")) 
                {
                    reportName = "Late_Arrival_Report";
                }
                
                
                else if(link.equalsIgnoreCase("musterReport")) 
                {
                    reportName = "Muster_Report";
                }
                
                // Salary Component Wise Summ
                else if(link.equalsIgnoreCase("salaryCompWiseSumm")) 
                {
                    reportName = "SalComponentWiseSumm";
                }
                
               
               
                
                
            }
            if (link.equalsIgnoreCase("Report") && request.getParameter("fileName") != null &&
                request.getParameter("fileName").toString().length() > 0) {
                reportName = request.getParameter("fileName");

                System.out.println("Inside global report ");
            }

            System.out.println("Report Name=" + reportName);
            JasperReport report = (JasperReport) JRLoader.loadObject(path + reportName + ".jasper");

            String reportType = null;
            String bgcolor = null;
            String head = null;
            String margin = null;
            String check = null; // For csv or pdf format
            Object stDt = null;
            String fromDate = null;
            Object edDt = null;
            String toDate = null;
            Integer crt = null;
            String crtVal = null;
            String empDocId = null;
            String shift   = null;
            String designation=null;
            String cldId = request.getParameter("cldId");
            Integer slocId = Integer.parseInt(request.getParameter("slocId"));
            String hoOrgId = request.getParameter("hoOrgId");
            String orgId = request.getParameter("orgId");

            // For Crtiteria
            if (request.getParameter("crt") != null) {
                if (request.getParameter("crt").equals("")) {
                    crt = null;
                } else {
                    crt = Integer.parseInt(request.getParameter("crt").toString());
                }
            }

            // For Crtiteria Value
            if (request.getParameter("crtVal") != null) {
                if (request.getParameter("crtVal").equals("")) {
                    crtVal = null;
                } else {
                    crtVal = request.getParameter("crtVal");
                }
            }

            // For EmpDocId
            if (request.getParameter("empId") != null) {
                if (request.getParameter("empId").equals("")) {
                    empDocId = null;
                } else {
                    empDocId = request.getParameter("empId");
                }
            }

            // For End date
            if (request.getParameter("edDt") != null) {
                if (request.getParameter("edDt").equals("")) {
                    edDt = null;
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    edDt = request.getParameter("edDt");
                    java.util.Date dt = dateFormat.parse(edDt.toString());
                    toDate = dateFormat.format(dt);
                }
            }

            // For Start date
            if (request.getParameter("stDt") != null) {
                if (request.getParameter("stDt").equals("")) {
                    stDt = null;
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    stDt = request.getParameter("stDt");
                    java.util.Date dt = dateFormat.parse(stDt.toString());
                    fromDate = dateFormat.format(dt);
                }
            }

            //Report Type
            if (request.getParameter("reportType") != null) {
                if (request.getParameter("reportType").equals("")) {
                    reportType = "pdf";
                } else {
                    reportType = request.getParameter("reportType");
                }
            }
            /**
             * Header Color
             */
            if (request.getParameter("head") != null) {
                if (request.getParameter("head").equals("")) {
                    head = "D";
                } else {
                    head = request.getParameter("head");
                }
            }

            /**
             * BgColor
             */
            System.out.println("--->" + request.getParameter("bgcolor"));
            if (request.getParameter("bgcolor") != null) {
                if (request.getParameter("bgcolor").equals("")) {
                    bgcolor = "D";
                } else {
                    bgcolor = request.getParameter("bgcolor");
                }
            }
            
            /**MARGIN DETAILS. **/
            if (request.getParameter("margin") != null) {
                if (request.getParameter("margin").equals("")) {
                    margin = null;
                } else {
                    margin = request.getParameter("margin");
                    if (margin.equalsIgnoreCase("true")) {
                        margin = "Y";
                    } else if (margin.equalsIgnoreCase("false")) {
                        margin = "N";

                    }
                }
            }
            
            // Desg
            if (request.getParameter("desg") != null) {
                if (request.getParameter("desg").equals("")) {
                    designation = null;
                } else {
                    designation = request.getParameter("desg");
                }
            }
            
            
        // Shift
        if (request.getParameter("shft") != null) {
            if (request.getParameter("shft").equals("")) {
                shift = null;
            } else {
                shift = request.getParameter("shft");
            }
        }


            System.out.println("Link is " + link + " path " + path + " reportName " + reportName +
                               " Actual Report name is :" + report + " stdt " + fromDate + " edDt " + toDate + " cld " +
                               cldId + " Hoorg " + hoOrgId + " org " + orgId + " sloc " + slocId + " empDocId " +
                               empDocId + " crt= " + crt + "  crtVal= " + crtVal);

            //For Csv format

            if ("xls".equalsIgnoreCase(reportType)) {
                check = "E";
            } else {
                check = "P";
            }
            if(crtVal.length()>14){
                empDocId=crtVal;
            }
            Map parameters = new HashMap();
            parameters.put("cldId", cldId);
            parameters.put("slocId", slocId);
            parameters.put("hoOrgId", hoOrgId);
            parameters.put("orgId", orgId);
            parameters.put("FromDate", fromDate);
            parameters.put("ToDate", toDate);
            parameters.put("ReportType", check);
            parameters.put("Path", path);
            parameters.put("crtValue", crtVal);
            parameters.put("empDocId", empDocId);
            parameters.put("desgId", designation);
            parameters.put("shiftId", shift);
           // System.out.println("Designation-->"+designation);
            //System.out.println("Shift--"+shift);
            

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
                System.out.println("Inside HTML exporter---------");
                exporter = new JRXhtmlExporter();
                System.out.println("object created---");
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                System.out.println("parameter set---");
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                System.out.println("output stream with parameter value----->>");
            } else if ("xls".equalsIgnoreCase(reportType)) {
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
            System.out.println("Error is =");
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
