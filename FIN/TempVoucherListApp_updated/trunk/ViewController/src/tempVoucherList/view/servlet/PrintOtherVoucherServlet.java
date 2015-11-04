package tempVoucherList.view.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
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
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;


public class PrintOtherVoucherServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        try {

            Connection conn = null;
            // PreparedStatement st = null;

            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/FINDS");
            conn = ds.getConnection();

            //    System.out.println("getting the connection "+conn);

            PreparedStatement ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

            ResultSet rs = ps.executeQuery();
            String path = null;
            while (rs.next()) {

                path = rs.getString(1);
                path = path + "FIN/";
                System.out.println("New Path is :" + path);
            }

            // InputStream input = new FileInputStream(new File("D:/Report/Print_voucher.jrxml"));
            /* InputStream input = new FileInputStream(new File(path + "Other_voucher.jrxml"));

            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design); */


            BigDecimal AmountX1 = null;
            BigDecimal AmountX2 = null;
            // BigDecimal CoaId = null;
            String prjId = null;


            String toDate = null;

            if (request.getParameter("todate") != null) {
                if (request.getParameter("todate").equals("")) {
                    toDate = null;
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    toDate = request.getParameter("todate");
                    Date dt = dateFormat.parse(toDate.toString());
                    toDate = dateFormat.format(dt);
                }
                System.out.println("toDate " + toDate);

            }
            String fromdate = null;
            if (request.getParameter("fromdate") != null) {

                if (request.getParameter("fromdate").equals("")) {
                    fromdate = null;
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    fromdate = request.getParameter("fromdate");
                    Date dt = dateFormat.parse(fromdate.toString());
                    fromdate = dateFormat.format(dt);
                }
                System.out.println("fromdate " + fromdate);

            }
            //String cogId = String.valueOf(request.getParameter("cogid"));
            String Org = String.valueOf(request.getParameter("organisation"));
            String VocNo = String.valueOf(request.getParameter("vouchernumber"));
            String reporttype = "pdf";
            Integer VouType = null;
            Integer slocId = Integer.parseInt(request.getParameter("slocId").toString());
            String hoOrgId = request.getParameter("hoorgid");
            String cldId = request.getParameter("cldId");

            //Voucher Type
            if (request.getParameter("voutype").equals("")) {
                VouType = null;
            } else {
                VouType = Integer.parseInt(request.getParameter("voutype"));
            }


            //AmountX1--------------
            if (request.getParameter("amountx1").equals("")) {

                AmountX1 = null;

            } else {

                Double y = Double.parseDouble(request.getParameter("amountx1"));
                BigDecimal z = new BigDecimal(y.doubleValue());


                AmountX1 = z;
            }


            //AmountX2--------------
            if (request.getParameter("amountx2").equals("")) {
                AmountX2 = null;
            } else {
                Double y = Double.parseDouble(request.getParameter("amountx2"));
                BigDecimal z = new BigDecimal(y.doubleValue());
                AmountX2 = z;
            }

            //Project Id
            if (request.getParameter("prjId").equals("")) {
                prjId = null;
            } else {
                prjId = request.getParameter("prjId");
            }


            //Report Type:----------------------
            if (reporttype.equals("")) {
                reporttype = "pdf";
            }

            Map parameters = new HashMap();
            String logo = request.getParameter("logo");

            parameters.put("Cld_Id", cldId);
            parameters.put("Sloc_Id", slocId);
            parameters.put("Ho_Org_Id", hoOrgId);
            parameters.put("Org_Id", Org);
            parameters.put("Amount-X1", AmountX1);
            parameters.put("Amount-X2", AmountX2);
            parameters.put("To_Date", toDate);
            parameters.put("From_Date", fromdate);
            parameters.put("Doc_Id", VocNo);
            parameters.put("Voucher_Type", VouType);
            parameters.put("Proj_Id", prjId);
            parameters.put("Path", path);
            parameters.put("Org_Logo", logo);
            System.out.println("Proj_Id" + prjId + "CLD ID " + cldId + "Sloc Id " + slocId + "HO ORG ID " + hoOrgId +
                               "Org Id " + Org);


            JasperReport report = (JasperReport) JRLoader.loadObject(path + "Other_voucher.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();
            JRExporter exporter = null;


            if ("pdf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/pdf");

                response.setHeader("Content-Disposition", "attachement; filename=\"OtherVoucher.pdf\"");

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
                        //System.out.println(ex.getMessage());
                        throw (ex);
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
