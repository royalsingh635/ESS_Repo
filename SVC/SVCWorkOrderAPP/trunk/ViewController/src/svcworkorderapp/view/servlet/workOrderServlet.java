package svcworkorderapp.view.servlet;

import java.io.IOException;
import java.io.OutputStream;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import javax.sql.DataSource;

@WebServlet(name = "workOrderServlet", urlPatterns = { "/workorderservlet" })
public class workOrderServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        openReportFun(request, response);
       //SVC_WorkOrder

    }
    private void openReportFun(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                                IOException {


        try {
            Connection conn = null;
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/SVCDS");
            conn = ds.getConnection();
            PreparedStatement ps =
                conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

            ResultSet rs = ps.executeQuery();
            String path = null;
            while (rs.next()) {
                path = rs.getString(1);
            }
            path=path+"SVC/";
            String orgId = (String)request.getParameter("orgId");
            String cldId = (String)request.getParameter("cldId");
            Integer slocId = Integer.parseInt(request.getParameter("slocId"));
            String docId = (String)request.getParameter("docId");
            String hoOrgId =request.getParameter("hoOrgId").toString();
            String workordno=(String)request.getParameter("workordno");
           
          

           // InputStream input = new FileInputStream(new File(path + "QuotationAnalysis.jrxml"));
            System.out.println("path is "+path);
           // JasperDesign design = JRXmlLoader.load(input);
           // JasperReport report = JasperCompileManager.compileReport(design);
            //    am.openReportUpdate(paramDocId,usrId);
            JasperReport report =(JasperReport) JRLoader.loadObject(path + "SVC_WorkOrder.jasper");
            
            Map<String, Object> parameters = new HashMap<String, Object>();
            System.out.println("--" + cldId + "----" + orgId + "------" + hoOrgId);
            parameters.put("Org_Id", orgId);
            parameters.put("Cld_Id", cldId);
            parameters.put("Sloc_Id", slocId);
            parameters.put("Ho_Org_Id",hoOrgId);
            parameters.put("WO_Doc_Id",docId);
            parameters.put("Path", path);
            System.out.println("doc----"+docId);
            JasperPrint jasperPrint = null;
            jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();
            String fileName = workordno + ".pdf";
            JRExporter exporter = null;
            if (true) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + fileName + "\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            }
            try {
                exporter.exportReport();
            } catch (JRException e) {
                throw new ServletException(e);
            } catch (Exception ex) {
            }

            finally {
                if (conn != null && rs != null) {

                    conn.close();
                    rs.close();
                }
                if (ouputStream != null) {
                    try {
                        ouputStream.flush();
                        ouputStream.close();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
