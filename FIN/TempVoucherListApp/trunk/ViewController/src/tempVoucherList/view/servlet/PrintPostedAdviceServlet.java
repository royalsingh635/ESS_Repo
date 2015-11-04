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
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;


public class PrintPostedAdviceServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        try {


            /* PreparedStatement st = null;

           String amDef = "GlReportApplication.model.module.GlReportAMImpl";
                   String config = "GlReportAMLocal";
                  GlReportAMImpl am =
                       (GlReportAMImpl)Configuration.createRootApplicationModule(amDef,
                                                                                         config);
           st = am.getDBTransaction().createPreparedStatement("select 1 from dual", 0);
           Connection conn = st.getConnection(); */

            Connection conn = null;
            //PreparedStatement st = null;

            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/FINDS");
            conn = ds.getConnection();

            PreparedStatement ps =
                conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

            ResultSet rs = ps.executeQuery();
            String path = null;
            while (rs.next()) {
                path = rs.getString(1);
                path=path + "FIN/"; 
                //path="D:\\Report from 220\\Report\\";
              System.out.println("New Path is :" + path);
            }

            //    System.out.println("getting the connection "+conn);
            String Type = request.getParameter("type");
            String fileName = null;
            if (request.getParameter("voutype").equalsIgnoreCase("P") && Type.equalsIgnoreCase("2")) {
                fileName = "Posted_Advice.jasper";
            } else if (request.getParameter("voutype").equalsIgnoreCase("P") && Type.equalsIgnoreCase("3")) {
                fileName = "Posted_Receipt_Advice.jasper";
            } else if (request.getParameter("voutype").equalsIgnoreCase("P") && !Type.equalsIgnoreCase("3") &&
                       !Type.equalsIgnoreCase("2")) {
                fileName = "PrintInvoice.jasper";
            } else if (request.getParameter("voutype").equalsIgnoreCase("A")) {
                fileName = "Draft_Advice.jasper";
            }
            //if(request.getParameter("voutype").equalsIgnoreCase("P"))
            //InputStream input = new FileInputStream(new File(fileName));

            /* InputStream input = new FileInputStream(new File(path + fileName));

            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design); */


            BigDecimal AmountX1 = null;
            BigDecimal AmountX2 = null;
            BigDecimal CoaId = null;
            String prjId=null;


            String toDate = null;

            if (request.getParameter("todate") != null) {
            if (request.getParameter("todate").equals("")) {
            toDate = null;
            } else {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            toDate = request.getParameter("todate");
            Date dt =  dateFormat.parse(toDate.toString());
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
            Date dt =  dateFormat.parse(fromdate.toString());
            fromdate = dateFormat.format(dt);
            }
                System.out.println("fromdate " + fromdate);

            }
            String Org = request.getParameter("organisation");
            String VocNo = request.getParameter("vouchernumber");
            String reporttype = request.getParameter("ReportType");
            String cldid = request.getParameter("cldid");
            
            Integer slocId = Integer.parseInt(request.getParameter("slocId").toString());
            String hoOrgId = request.getParameter("hoorgid");

            Integer currid = null;

            System.out.println("value of voucher type is==" + Type);


            //Currency Id------------

            if (request.getParameter("currencyid").equals("")) {
                currid = null;
            } else {
                currid = Integer.parseInt(request.getParameter("currencyid"));

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


            //COA_ID--------------
            if (request.getParameter("coa_id").equals("")) {
                CoaId = null;
            } else {
                Double y = Double.parseDouble(request.getParameter("coa_id"));
                BigDecimal z = new BigDecimal(y.doubleValue());
                CoaId = z;
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

            parameters.put("Org_Id", Org);
            parameters.put("Sloc_Id", slocId);
            parameters.put("Ho_Org_Id", hoOrgId);
            parameters.put("Amount-X1", AmountX1);
            parameters.put("Amount-X2", AmountX2);
            parameters.put("To_Date", toDate);
            parameters.put("From_Date", fromdate);
            parameters.put("Curr_Id", currid);
            parameters.put("Cld_Id", cldid);
            parameters.put("Doc_Id", VocNo);
            parameters.put("Coa_Id", CoaId);
            parameters.put("Proj_Id", prjId);
            parameters.put("Path", path);
            
       //    parameters.put("Cld_Id", cldid);
        //   parameters.put("Sloc_Id", slocId);
           parameters.put("Ho_Org_Id", hoOrgId);
           
           // parameters.put("To_Voucher_No", VocNo);
           System.out.println("orgid :" + Org + " Amount1 :" + AmountX1 + " Amount2 :" + AmountX2 + " ToDate :" +
                              toDate + " FromDate :" + fromdate + " Currid :" + currid + " voucher No :" + VocNo +
                              " CoaId :" + CoaId + " cldid :" + cldid+" Ho_Org_Id = "+hoOrgId+" slocId = "+slocId+"Proj_Id"+prjId);
            
            JasperReport report=(JasperReport)JRLoader.loadObject(path + fileName);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();
            JRExporter exporter = null;

            if ("pdf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/pdf");
                if (request.getParameter("voutype").equalsIgnoreCase("P") && Type.equalsIgnoreCase("2"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"Posted_Advice.pdf\"");
                else if (request.getParameter("voutype").equalsIgnoreCase("P") && Type.equalsIgnoreCase("3"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"Posted_Receipt_Advice.pdf\"");
                else if (request.getParameter("voutype").equalsIgnoreCase("P") && !Type.equalsIgnoreCase("3") &&
                         !Type.equalsIgnoreCase("2"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"PrintInvoice.pdf\"");
                else
                    response.setHeader("Content-Disposition", "attachement; filename=\"Draft_Advice.pdf\"");

                exporter = new JRPdfExporter();
                //exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("rtf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/rtf");
                if (request.getParameter("voutype").equalsIgnoreCase("P"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"Posted_Advice.rtf\"");
                else
                    response.setHeader("Content-Disposition", "attachement; filename=\"Draft_Advice.rtf\"");


                exporter = new JRHtmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("html".equalsIgnoreCase(reporttype))

            {


                exporter = new JRXhtmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xls".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xls");

                if (request.getParameter("voutype").equalsIgnoreCase("P"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"Posted_Advice.xls\"");
                else
                    response.setHeader("Content-Disposition", "attachement; filename=\"Draft_Advice.xls\"");


                exporter = new JRXlsExporter();
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("docx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/docx");

                if (request.getParameter("voutype").equalsIgnoreCase("P"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"Posted_Advice.docx\"");
                else
                    response.setHeader("Content-Disposition", "attachement; filename=\"Draft_Advice.docx\"");


                exporter = new JRDocxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xlsx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xlsx");

                if (request.getParameter("voutype").equalsIgnoreCase("P"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"Posted_Advice.xlsx\"");
                else
                    response.setHeader("Content-Disposition", "attachement; filename=\"Draft_Advice.xlsx\"");


                exporter = new JRXlsxExporter();
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xml".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xml");

                if (request.getParameter("voutype").equalsIgnoreCase("P"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"Posted_Advice.xml\"");
                else
                    response.setHeader("Content-Disposition", "attachement; filename=\"Draft_Advice.xml\"");


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
