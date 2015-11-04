/**
 * @author : Sudhanshu Raj.
 **/
package svcreportapp.view.servlet;

import java.io.IOException;
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
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;

@WebServlet(name = "SvcReportServlet", urlPatterns = { "/svcreportservlet" })
public class SvcReportServlet extends HttpServlet {
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

            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/SVCDS");
            conn = ds.getConnection();

            ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");
            rs = ps.executeQuery();

            String path = null; //For getting path from database.

            while (rs.next()) {
                path = rs.getString(1);
                System.out.println("Path Setting.................." + path);
            }

            String reportName = null; //Use to get Report Name.

            String goLink = request.getParameter("golink").toString(); // which type of report user want to print.
            System.out.println("Link..." + goLink);
            /* String tab = request.getParameter("tab").toString(); //which tab is clicked.
            System.out.println("click on tab....." + tab); */

            String cldId = request.getParameter("cldId");
            Integer slocId = Integer.parseInt(request.getParameter("slocId").toString());
            String hoOrgId = request.getParameter("hoOrgId");
            String org = request.getParameter("orgName");

            String fromDate = request.getParameter("fromDate").toString();
            String toDate = request.getParameter("toDate").toString();

            String reportType = request.getParameter("reportType").toString();
            String tab = null;
            String check = null; // For csv or pdf format

            Integer eoId = null; //For Customers.

            Integer scCntrctTyp = null; // For Service Contract Type.
            Integer scStatus = null; // For Service Status.
            String scContrctNo = null; //For Service Contract No
            Integer scBillCyccle = null; // For Service Contract Tracking Bill Cycle.

            Integer scmCallType = null; // For SCM Call Type
            Integer scmStatus = null; // For SCM Status.
            String scmCallNo = null; // For SCM Call No.

            Integer woDocType = null; // For WO Document Type.
            Integer woStatus = null; // For WO Status.
            String woNo = null; // For WO No.

            String bgColor = null; //For Color Configure Background color.
            String headColor = null; //For Color Configure Header Color.

            String invDocId = null; // For Invoice Document Id.
            Integer invType = null; // For Invoice Type.
            String chkOrgNm = null; // For Orgnisation side view.

            /** EO ID **/
            if (request.getParameter("eoId") != null) {
                if (request.getParameter("eoId").equals("")) {
                    eoId = null;
                } else {
                    eoId = Integer.parseInt(request.getParameter("eoId").toString());
                }
            }

            /** SC Contract Type. **/
            if (request.getParameter("scContrTyp") != null) {
                if (request.getParameter("scContrTyp").equals("")) {
                    scCntrctTyp = null;
                } else {
                    scCntrctTyp = Integer.parseInt(request.getParameter("scContrTyp").toString());
                }
            }

            /** SC Status. **/
            if (request.getParameter("scStatus") != null) {
                if (request.getParameter("scStatus").equals("")) {
                    scStatus = null;
                } else {
                    scStatus = Integer.parseInt(request.getParameter("scStatus").toString());
                }
            }

            /** SC Bill Cycle. **/
            if (request.getParameter("billCycle") != null) {
                if (request.getParameter("billCycle").equals("")) {
                    scBillCyccle = null;
                } else {
                    scBillCyccle = Integer.parseInt(request.getParameter("billCycle").toString());
                }
            }

            /** SCM Call Type. **/
            if (request.getParameter("scmCalTyp") != null) {
                if (request.getParameter("scmCalTyp").equals("")) {
                    scmCallType = null;
                } else {
                    scmCallType = Integer.parseInt(request.getParameter("scmCalTyp").toString());
                }
            }

            /** SCM Status. **/
            if (request.getParameter("scmStatus") != null) {
                if (request.getParameter("scmStatus").equals("")) {
                    scmStatus = null;
                } else {
                    scmStatus = Integer.parseInt(request.getParameter("scmStatus").toString());
                }
            }
            /** SCM Call No. **/
            if (request.getParameter("scmCallNo") != null) {
                if (request.getParameter("scmCallNo").equals("")) {
                    scmCallNo = null;
                } else {
                    scmCallNo = request.getParameter("scmCallNo").toString();
                }
            }

            /** WO Status. **/
            if (request.getParameter("woStatus") != null) {
                if (request.getParameter("woStatus").equals("")) {
                    woStatus = null;
                } else {
                    woStatus = Integer.parseInt(request.getParameter("woStatus").toString());
                }
            }

            /** WO Document Type. **/
            if (request.getParameter("woDocTyp") != null) {
                if (request.getParameter("woDocTyp").equals("")) {
                    woDocType = null;
                } else {
                    woDocType = Integer.parseInt(request.getParameter("woDocTyp").toString());
                }
            }


            /** WO No. **/
            if (request.getParameter("woId") != null) {
                if (request.getParameter("woId").equals("")) {
                    woNo = null;
                } else {
                    woNo = request.getParameter("woId").toString();
                }
            }


            /** Invoice Type. **/
            if (request.getParameter("invType") != null) {
                if (request.getParameter("invType").equals("")) {
                    invType = null;
                } else {
                    invType = Integer.parseInt(request.getParameter("invType").toString());
                }
            }

            /** Invoice Id. **/
            if (request.getParameter("invDocId") != null) {
                if (request.getParameter("invDocId").equals("")) {
                    invDocId = null;
                } else {
                    invDocId = request.getParameter("invDocId").toString();
                }
            }

            /** Service Contrat No. **/
            if (request.getParameter("scContrctNo") != null) {
                if (request.getParameter("scContrctNo").equals("")) {
                    scContrctNo = null;
                } else {
                    scContrctNo = request.getParameter("scContrctNo").toString();
                }
            }
            if (request.getParameter("tab") != null) {
                if (request.getParameter("tab").equals("")) {
                    tab = null;
                } else {
                    tab = request.getParameter("tab").toString(); //which tab is clicked.
                }
            }

            System.out.println("click on tab....." + tab);


            /** Orgnisation Side View  **/
            if (request.getParameter("chkOrgNm") != null) {
                if (request.getParameter("chkOrgNm").equals("")) {
                    chkOrgNm = null;
                } else {
                    chkOrgNm = request.getParameter("chkOrgNm");
                    if (chkOrgNm.equalsIgnoreCase("true")) {
                        chkOrgNm = "Y";
                    } else if (chkOrgNm.equalsIgnoreCase("false")) {
                        chkOrgNm = "N";
                    }
                }
            }

            System.out.println(" GoLink : " + goLink + " Tab : " + tab + " CldId : " + cldId + " SlocId : " + slocId +
                               " HoOrgId : " + hoOrgId + " Org : " + org + " FromDate : " + fromDate + " ToDate : " +
                               toDate + " Eo Id : " + eoId + " SC_Status : " + scStatus + " SC_Cntrct Type : " +
                               scCntrctTyp + " Bill Cycle : " + scBillCyccle + " SCM Status : " + scmStatus +
                               " SCM Cal Type : " + scmCallType + "  WO Status : " + woStatus + "  WO Doc Type : " +
                               woDocType + " Inv Type : " + invType + " Inv Doc Id : " + invDocId + " SC_Contrt_no : " +
                               scContrctNo + " SCM Call No. " + scmCallNo + " Wo No : " + woNo);

            System.out.println("Bg Color : " + bgColor + "Head Color : " + headColor + "Margin : " + chkOrgNm);

            if (tab.equalsIgnoreCase("svc")) {
                if (goLink.equalsIgnoreCase("svccontrc")) {
                    reportName = "SVC_ServiceContract";
                }
                if (goLink.equalsIgnoreCase("svcexec")) {
                    reportName = "SVC_ServiceExecutive";
                }
                if (goLink.equalsIgnoreCase("svccontrack")) {
                    reportName = "SVC_ContractTracking";
                }
            } else if (tab.equalsIgnoreCase("scm")) {
                if (goLink.equalsIgnoreCase("svccallmng")) {
                    reportName = "SVC_Call_Print";
                }
                if (goLink.equalsIgnoreCase("svccalltracking")) {
                    reportName = "SVC_Call_Tracking";
                }
            } else if (tab.equalsIgnoreCase("wo")) {
                if (goLink.equalsIgnoreCase("wrkord")) {
                    reportName = "SVC_WorkOrder";
                }
            } else if (tab.equalsIgnoreCase("inv")) {
                if (goLink.equalsIgnoreCase("invrep")) {
                    reportName = "SVC_ServiceInvoice";
                }
            } else if (tab.equalsIgnoreCase("global")) {
                if (goLink.equalsIgnoreCase("23010") && request.getParameter("fileName") != null &&
                    request.getParameter("fileName").toString().length() > 0)
                    reportName = request.getParameter("fileName");
            }


            System.out.println("report name..." + reportName);

            /**
                        * For Path either module wise or Portal wise
                        */
            if (goLink.equalsIgnoreCase("23010") && request.getParameter("fileName") != null) {
                path = path + "Portal/";
            } else {
                path = path + "SVC/";
            }

            System.out.println("Path is " + path);

            //For Csv format

            if ("xls".equalsIgnoreCase(reportType)) {
                check = "E";

            } else {
                check = "P";
            }


            Map parameters = new HashMap();

            parameters.put("Sloc_Id", slocId);
            parameters.put("Cld_Id", cldId);
            parameters.put("Ho_Org_Id", hoOrgId);
            parameters.put("Org_Id", org);

            parameters.put("To_Date", toDate);
            parameters.put("From_Date", fromDate);

            parameters.put("Path", path);
            parameters.put("Report_Type", check);
            parameters.put("Eo_Id", eoId);

            parameters.put("SC_Status", scStatus);
            parameters.put("SC_Contract_Type", scCntrctTyp);
            parameters.put("SC_Doc_Id", scContrctNo);
            parameters.put("SC_Bill_Cycle", scBillCyccle);

            parameters.put("SCM_Status", scmStatus);
            parameters.put("SCM_Call_Type", scmCallType);
            parameters.put("SCM_Doc_Id", scmCallNo);

            parameters.put("WO_Status", woStatus);
            parameters.put("WO_Doc_Type", woDocType);
            parameters.put("WO_Doc_Id", woNo);

            parameters.put("INV_Doc_Id", invDocId);
            parameters.put("INV_Type", invType);


            parameters.put("BgColor", bgColor);
            parameters.put("Head", headColor);
            parameters.put("Margin_Detail", chkOrgNm);


            JasperReport report = (JasperReport) JRLoader.loadObject(path + reportName + ".jasper");

            System.out.println("time before filling" + System.currentTimeMillis());
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();
            System.out.println("time afetr filling" + System.currentTimeMillis());

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
