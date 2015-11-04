package slspickpackshipapp.view.servlet;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

public class SlsShipmentPrintReportServlet extends HttpServlet {
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
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/SLSDS");
            conn = ds.getConnection();
            String reportName = null;
            String linkType = request.getParameter("golink").toString();
            String path;

            ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

            rs = ps.executeQuery();
            path = null;
            while (rs.next()) {
                path = rs.getString(1);
                // System.out.println("Path is :" + rs.getString(1));
            }
            path=path+"SLS/";
            if (linkType.equalsIgnoreCase("shipdetail")) {
                reportName = "SLS_Ship_Challan";
            }else if(linkType.equalsIgnoreCase("packdetail")){
                reportName = "Sls_Pack_DetailReport";
            }else if(linkType.equalsIgnoreCase("installationReport")){
                reportName = "SLS_Ship_installation";
            }
            
            InputStream input = new FileInputStream(new File(path + reportName + ".jrxml"));
            System.out.println("Report is" + input);
            //InputStream input = new FileInputStream(new File("D:/Report/SLS_Opp_DetailReport.jrxml"));


            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design);
            System.out.println("Path : "+path);
            //Getting Parameter from the servlet

            String DocId = null;
            String Org = request.getParameter("Org").toString();
            String cldid = request.getParameter("cldid").toString();
            Integer slocid = Integer.parseInt(request.getParameter("slocid"));
            String hoOrgid = request.getParameter("hoOrgid").toString();
            Integer CatId;
            String assigned;
            String status;
            String docno;
            String eoid;
            String salesman;
            String itmgrp;
            String itmName;
            String Todate;
            String Fromdate;
            Integer currid;
            Integer FormStatus;


            //Report Type----------------

            String reporttype = "pdf";


            //putting the parameter to map to report jrxml file


            if (request.getParameter("currid").equals("")) {
                currid = null;
            } else {
                /*  Double y = Double.parseDouble(request.getParameter("currid"));
                BigDecimal z = new BigDecimal(y.doubleValue()); */
                currid = Integer.parseInt(request.getParameter("currid"));
            }

            //Fromdate
            if (request.getParameter("fromdate").equals("")) {
                Fromdate = null;
            } else {
                Fromdate = request.getParameter("fromdate");

            }


            //Todate
            if (request.getParameter("todate").equals("")) {
                Todate = null;
            } else {
                Todate = request.getParameter("todate");
            }


            //CatId
            if (request.getParameter("catid").equals("")) {
                CatId = null;
            } else {
                System.out.println("Category ID is-->" + request.getParameter("catid"));
                CatId = Integer.parseInt(request.getParameter("catid"));

            }
            //Form status
            if (request.getParameter("formstatus").equals("")) {
                FormStatus = null;
            } else {
                System.out.println("status is --->" + request.getParameter("formstatus"));
                FormStatus = Integer.parseInt(request.getParameter("formstatus"));

            }

            /*      //assigned
            if (request.getParameter("assign").equals("")) {
                assigned = null;
            } else {
                assigned = request.getParameter("assign");
            } */


            //status
            if (request.getParameter("status").equals("")) {
                status = null;
            } else {
                status = request.getParameter("status");
            }


            //oppno
            if (request.getParameter("DocId").equals("")) {
                DocId = null;
            } else {
                System.out.println("Doc ID Is -->" + request.getParameter("DocId"));
                DocId = request.getParameter("DocId").toString();
            }


            //eoid
            if (request.getParameter("eoid").equals("")) {
                eoid = null;
            } else {
                eoid = request.getParameter("eoid");
            }

            //salesman
            if (request.getParameter("salesman").equals("")) {
                salesman = null;
            } else {
                salesman = request.getParameter("salesman");
            }

            //itmgrp
            if (request.getParameter("itmgrp").equals("")) {
                itmgrp = null;
            } else {
                itmgrp = request.getParameter("itmgrp");
            }


            //itmName
            if (request.getParameter("itmName").equals("")) {
                itmName = null;
            } else {
                itmName = request.getParameter("itmName");
            }


            //Report Type
            if (request.getParameter("ReportType").equals("")) {
                reporttype = "pdf";
            } else {
                reporttype = request.getParameter("ReportType");
            }

            //System.out.println(Todate + " --- " + Fromdate);


            Map parameters = new HashMap();

            parameters.put("OrgId", Org);
            parameters.put("SlocId", slocid);
            parameters.put("CldId", cldid);
            parameters.put("HoOrgId", hoOrgid);
            parameters.put("EoCatId", null);
            parameters.put("FromDate", null);
            parameters.put("ToDate", null);
            parameters.put("ItmId", null);
            parameters.put("GrpId", null);
            parameters.put("EoId", null);
            parameters.put("DocId", DocId);
            //  parameters.put("Status", status);
            parameters.put("SoId", null); //DocId
            parameters.put("AssignTo", null);
            parameters.put("CurrId", null);
            parameters.put("RMATypeId", null);
            parameters.put("Path", path);
            parameters.put("Status", null);
            System.out.println("DocId=  " + DocId + "  Org=  " + Org + "  cldid=  " + cldid + "  slocid=  " + slocid +
                               "  hoOrgid=  " + hoOrgid);

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();
            JRExporter exporter = null;


            if ("pdf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".pdf\"");

                exporter = new JRPdfExporter();
                //exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, true);
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


                        if (conn != null) {
                            conn.close();
                        }
                        ouputStream.flush();
                        ouputStream.close();

                    } catch (IOException ex) {
                        // System.out.println(ex.getMessage());
                        throw (ex);
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

}
