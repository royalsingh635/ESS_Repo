package mmbillofentryapp.view.servlet;

import java.io.IOException;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

@WebServlet(name = "MmBoeServlet", urlPatterns = { "/mmboeservlet" })
public class MmBoeServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String reportName = null;

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


            String reportType = "pdf";
            String DocId = null;
            String cldId = request.getParameter("CldId");
            Integer slocId = Integer.parseInt(request.getParameter("SlocId"));
            //   String hoOrgId = request.getParameter("hoOrgId");
            String orgId = request.getParameter("OrgId");
            DocId = request.getParameter("DocId");
            /*    if (request.getParameter("DocId") != null) {
                if (request.getParameter("DocId").equals("")) {
                    DocId = null;
                } else {
                    DocId = request.getParameter("DocId");
                }
            }
*/
            reportName = "BillOfEntry_Print";
            Map parameters = new HashMap();
            parameters.put("CldId", cldId);
            parameters.put("SlocId", slocId);
            //  parameters.put("HoOrgId", hoOrgId);
            parameters.put("OrgId", orgId);
            parameters.put("BOEDocId", DocId);
            parameters.put("Path", path);
            System.out.println("parameter is-- " + parameters);
            JasperReport report = (JasperReport) JRLoader.loadObject(path + reportName + ".jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();

            JRExporter exporter = null;
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".pdf\"");
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
