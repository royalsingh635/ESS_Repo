package mnfjobcardapp.view.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

import net.sf.jasperreports.engine.util.JRLoader;

@WebServlet(name = "JobCardServlet", urlPatterns = { "/jobcardservlet" })
public class JobCardServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        Connection conn = null;

        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MNFDS");
            conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

            ResultSet rs = ps.executeQuery();
            String path = null;
            while (rs.next()) {
                path = rs.getString(1);
            }
            path = path + "MNF/";
            String cld_id = String.valueOf(request.getParameter("CldId"));
            Integer sloc_id = Integer.parseInt(request.getParameter("SlocId"));
            String ho_org_id = String.valueOf(request.getParameter("HoOrgId"));
            String org_id = String.valueOf(request.getParameter("OrgId"));
            String doc_id = String.valueOf(request.getParameter("JcDocId"));
            System.out.println("Path       " + path);
            JasperReport report = (JasperReport) JRLoader.loadObject(path + "Job_Card_Print.jasper");
            String reporttype = "pdf";


            Map parameters = new HashMap();

            parameters.put("CldId", cld_id);
            parameters.put("SlocId", sloc_id);
            parameters.put("OrgId", org_id);
            parameters.put("HoOrgId", ho_org_id);
            parameters.put("JcDocId", doc_id);
            parameters.put("Path", path);

            System.out.println("---" + parameters);
            JasperPrint jasperPrint = null;
            jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();
            JRExporter exporter = null;
            if (reporttype.equalsIgnoreCase("pdf")) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachement; filename=\"Job_Card_Print.pdf\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            }


            try {
                exporter.exportReport();
            } catch (JRException e) {
                throw new ServletException(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            if (ouputStream != null) {
                try {
                    ouputStream.flush();
                    ouputStream.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    // throw (ex);
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
