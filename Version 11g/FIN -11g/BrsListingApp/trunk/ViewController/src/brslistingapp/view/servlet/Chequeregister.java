package brslistingapp.view.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.*;
import javax.servlet.http.*;

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
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import oracle.jbo.domain.Timestamp;

public class Chequeregister extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        Connection conn = null;
        try {

            /* PreparedStatement st = null;

    String amDef = "GlReportApplication.model.module.GlReportAMImpl";
            String config = "GlReportAMLocal";
           GlReportAMImpl am =
                (GlReportAMImpl)Configuration.createRootApplicationModule(amDef,
                                                                                  config);
    st = am.getDBTransaction().createPreparedStatement("select 1 from dual", 0);
    Connection conn = st.getConnection(); */


            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/FINDS");
            conn = ds.getConnection();
            PreparedStatement ps =
                conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

            ResultSet rs = ps.executeQuery();
            String path = null;
            while (rs.next()) {
                path = rs.getString(1);
                System.out.println("Path is :" + rs.getString(1));
            }
            InputStream input = null;
            if (request.getParameter("ConsChk").equalsIgnoreCase("true")) {
                System.out.println("Inside chk" + request.getParameter("ConsChk").toString());
                input = new FileInputStream(new File(path + "Cheque_Register_Consolidated.jrxml"));
            } else {
                System.out.println("inside else.....");

                //    System.out.println("getting the connection "+conn);


                // InputStream input = new FileInputStream(new File("D:/Report/Cheque_Register.jrxml"));
                input = new FileInputStream(new File(path + "Cheque_Register.jrxml"));
            }
            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design);


            BigDecimal AmountX1 = null;
            BigDecimal AmountX2 = null;
            Integer Bank_Coa_Id = null;
            String Vou_From_Date = null;
            String Vou_To_Date = null;
            String Disp_Vou_Id = null;
            BigDecimal instid = null;
            BigDecimal stat = null;
            String toDate = null;
            String fromDate = null;
            BigDecimal coaid = null;
            Integer currid = null;


            String Org = String.valueOf(request.getParameter("orgid"));

            //String reporttype = String.valueOf(request.getParameter("ReportType"));

            //System.out.println("Report Type " + reporttype);

            // Disp_Vou_Id-------------------
            if (request.getParameter("Disp_Vou_Id") != null) {
                if (request.getParameter("Disp_Vou_Id").equals("")) {
                    Disp_Vou_Id = null;
                } else {
                    // toDate = request.getParameter("todate").toString();
                    Disp_Vou_Id = request.getParameter("Disp_Vou_Id").toString();
                }
            } else {
                Disp_Vou_Id = null;
            }


            //Vou_From_Date
            if (request.getParameter("Vou_From_Date") != null) {
                if (request.getParameter("Vou_From_Date").equals("")) {
                    Vou_From_Date = null;
                } else {
                    Vou_From_Date = (request.getParameter("Vou_From_Date"));
                    System.out.println(Vou_From_Date);
                }
            } else {
                Vou_From_Date = null;
            }

            //Vou_To_Date

            if (request.getParameter("Vou_To_Date") != null) {
                if (request.getParameter("Vou_To_Date").equals("")) {
                    Vou_To_Date = null;
                } else {
                    // toDate = request.getParameter("todate").toString();
                    Vou_To_Date = (request.getParameter("Vou_To_Date"));
                }
            } else {
                Vou_To_Date = null;
            }

            //fromdate----------------
            if (request.getParameter("fromdate") != null) {
                if (request.getParameter("fromdate").equals("")) {
                    fromDate = null;
                } else {
                    // fromDate = request.getParameter("fromdate").toString();
                    fromDate = new Timestamp(request.getParameter("fromdate")).dateValue().toString();
                }
            } else {
                fromDate = null;
            }

            //todate----------------
            if (request.getParameter("todate") != null) {
                if (request.getParameter("todate").equals("")) {
                    toDate = null;
                } else {
                    // toDate = request.getParameter("todate").toString();
                    toDate = new Timestamp(request.getParameter("todate")).dateValue().toString();
                }
            } else {
                toDate = null;
            }

            //Currency id--------
            if (request.getParameter("currid") != null) {
                if (request.getParameter("currid").equals("")) {
                    currid = null;
                } else {
                    currid = Integer.parseInt(request.getParameter("currid"));
                }
            } else {
                currid = null;
            }
            // Coa Id-----------
            if (request.getParameter("coaid") != null) {
                if (request.getParameter("coaid").equals("")) {
                    coaid = null;
                } else {
                    coaid = new BigDecimal(request.getParameter("coaid"));
                }
            } else {
                coaid = null;
            }


            //AmountX1--------------
            if (request.getParameter("amountx1") != null) {
                if (request.getParameter("amountx1").equals("")) {

                    AmountX1 = null;

                } else {

                    Double y = Double.parseDouble(request.getParameter("amountx1"));
                    BigDecimal z = new BigDecimal(y.doubleValue());


                    AmountX1 = z;
                }
            } else {
                AmountX1 = null;
            }


            //AmountX2--------------
            if (request.getParameter("amountx2") != null) {
                if (request.getParameter("amountx2").equals("")) {
                    AmountX2 = null;
                } else {
                    Double y = Double.parseDouble(request.getParameter("amountx2"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    AmountX2 = z;
                }
            } else {
                AmountX2 = null;
            }


            // Bank_Coa_Id
            if (request.getParameter("Bank_Coa_Id") != null) {
                if (request.getParameter("Bank_Coa_Id").equals("")) {
                    Bank_Coa_Id = null;
                } else {
                    Bank_Coa_Id = Integer.parseInt(request.getParameter("Bank_Coa_Id").toString());

                }
            } else {
                Bank_Coa_Id = null;
            }

            //Instrument Id--------------
            if (request.getParameter("instrumenttype") != null) {
                if (request.getParameter("instrumenttype").equals("")) {
                    instid = null;
                } else {
                    Double y = Double.parseDouble(request.getParameter("instrumenttype"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    instid = z;
                }
            } else {
                instid = null;
            }

            //Status--------------
            if (request.getParameter("Status") != null) {
                if (request.getParameter("Status").equals("")) {
                    stat = null;
                } else {
                    Double y = Double.parseDouble(request.getParameter("Status"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    stat = z;
                }
            } else {
                stat = null;
            }

            String reporttype = "pdf";
            //Report Type:----------------------
            if (reporttype.equals("")) {
                reporttype = "pdf";
            }

            Map parameters = new HashMap();

            parameters.put("Org_ID", Org);

            parameters.put("Disp_Vou_Id", Disp_Vou_Id);
            parameters.put("Vou_To_Date", Vou_To_Date);
            parameters.put("Vou_From_Date", Vou_From_Date);
            parameters.put("Bank_Coa_Id", Bank_Coa_Id);

            parameters.put("Amount-X1", AmountX1);
            parameters.put("Amount-X2", AmountX2);
            parameters.put("ToDate", toDate);
            parameters.put("FromDate", fromDate);
            parameters.put("CurrencyID", currid);
            parameters.put("Coa_ID", coaid);
            parameters.put("CLD_ID", request.getParameter("cldid"));
            parameters.put("Status", stat);
            parameters.put("Instrument_Type", instid);
            parameters.put("Path", path);
            parameters.put("Ho_Org_ID", request.getParameter("HoOrgId"));

            System.out.println("In servlet -------------");
            System.out.println("bankcoa--" + Bank_Coa_Id + " vou to date--" + Vou_To_Date + "vou frm date-- " +
                               Vou_From_Date + " voucher id---" + Disp_Vou_Id);
            System.out.println("Org:" + Org + " Amount1 :" + AmountX1 + " 2 " + AmountX2 + " toDate " + toDate +
                               " Fromdate " + fromDate + " Currid " + currid + " cldid " +
                               request.getParameter("cldid") + " status " + stat + " instTYPE-- " + instid +
                               "coaId->" + coaid);


            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);


            OutputStream ouputStream = response.getOutputStream();


            JRExporter exporter = null;


            if ("pdf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/pdf");

                response.setHeader("Content-Disposition", "attachement; filename=\"Cheque_Register.pdf\"");


                exporter = new JRPdfExporter();
                exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("rtf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/rtf");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Content-Disposition", "attachement; filename=\"Cheque_Register.rtf\"");

                exporter = new JRRtfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("html".equalsIgnoreCase(reporttype)) {
                exporter = new JRHtmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xls".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xls");
                response.setHeader("Content-Disposition", "attachement; filename=\"Cheque_Register.xls\"");

                exporter = new JRXlsExporter();
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("docx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/docx");
                response.setHeader("Content-Disposition", "attachement; filename=\"Cheque_Register.docx\"");

                exporter = new JRDocxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xlsx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xlsx");
                response.setHeader("Content-Disposition", "attachement; filename=\"Cheque_Register.xlsx\"");

                exporter = new JRXlsxExporter();
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xml".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xml");
                response.setHeader("Content-Disposition", "attachement; filename=\"Cheque_Register.xml\"");

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
