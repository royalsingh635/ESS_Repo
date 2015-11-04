/**  Author Sudhanshu Raj **/
package GlReportApp.view.Servlet;

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

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

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
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import oracle.igf.ids.Org;


public class GlReportAppServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String reportName = null; //Use to get Report Name.
            String tab = request.getParameter("tab").toString(); //which tab is clicked.
            System.out.println("click on tab....." + tab);
            String linkType = request.getParameter("golink").toString(); // which type of report user want to print.
            System.out.println("Link..." + linkType);
            //String Type = request.getParameter("type"); // For selection of voucher type
            Integer vouType = null;
            Integer bookType = null;

            Integer naId = null;
            String cogId = null;
            BigDecimal coaId = null;
            String fromVocNo = null;
            String toVocNo = null;
            Integer userId = null;

            Integer coaNm = null;
            String bankCash = null;
            Integer entityGrp = null; //for Entity Group

            Integer slocId = Integer.parseInt(request.getParameter("slocid"));
            String cldId = request.getParameter("cldid");
            String hoOrgId = request.getParameter("hoorgid");
            String org = request.getParameter("organisation");
            String toDate = request.getParameter("todate").toString();
            String fromDate = request.getParameter("fromdate").toString();
            String reportType = request.getParameter("reporttype");
            String postFlag = request.getParameter("postflag");
            String GroupAccount = request.getParameter("GroupAccount");
            String zeroAmt = request.getParameter("zeroAmt");
            String ZeroAmtvalue = "Y";
            String asondate = (request.getParameter("asondate"));
            String check = null; // For csv or pdf format
            Integer adjType = null; // For Adjustment Type CR/DR

            // System.out.println("vouchers type is---->>"+PostFlag);

            if (zeroAmt != null) {
                if (zeroAmt.equalsIgnoreCase("false")) {
                    ZeroAmtvalue = "Y";
                } else if (zeroAmt.equalsIgnoreCase("true")) {
                    ZeroAmtvalue = "N";
                }

            } else {
                ZeroAmtvalue = "Y";
            }


            Context ctx = new InitialContext();
            // System.out.println("time befoe connection"+System.currentTimeMillis() % 1000);
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/FINDS");
            conn = ds.getConnection();

            //  System.out.println("time after connection"+System.currentTimeMillis() % 1000);
            ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

            rs = ps.executeQuery();

            // System.out.println("time after executing query"+System.currentTimeMillis() % 1000);
            String path = null;
            while (rs.next()) {
                path = rs.getString(1);
                //path="D:\\Report from 220\\Report\\";
                System.out.println("Path is :" + rs.getString(1));
            }

            // System.out.println("time before parameter setting"+System.currentTimeMillis() % 1000);
            /** COGID **/
            //System.out.println("to be COG........");
            if (request.getParameter("cogid") != null) {
                if (request.getParameter("cogid").equals("")) {
                    //System.out.println("to be COG NULL........");
                    cogId = null;
                } else {
                    cogId = request.getParameter("cogid");
                    System.out.println("to be COG cogId........");
                }
            }

            /** NAID **/
            if (request.getParameter("naid") != null) {
                if (request.getParameter("naid").equals("")) {
                    naId = null;
                } else {
                    naId = Integer.parseInt(request.getParameter("naid"));
                }
            }

            /** Report Type  **/
            if (reportType.equals("")) {
                reportType = "pdf";
            }
            /** COA **/
            if (request.getParameter("coaid") != null) {
                if (request.getParameter("coaid").equals("")) {
                    coaId = null;
                } else {

                    Double y = Double.parseDouble(request.getParameter("coaid"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    coaId = z;
                }
            }


            /** From Voucher No. **/
            if (request.getParameter("vouchernumber") != null) {
                if (request.getParameter("vouchernumber").equals("")) {
                    fromVocNo = null;
                } else {
                    fromVocNo = request.getParameter("vouchernumber").toString();
                }
            }

            /** To  Voucher No. **/
            if (request.getParameter("tovoucher") != null) {
                if (request.getParameter("tovoucher").equals("")) {
                    toVocNo = null;
                } else {
                    toVocNo = request.getParameter("tovoucher").toString();
                }
            }

            /** TYPE **/
            /*  if (request.getParameter("type") != null) {
                if (request.getParameter("type").equals("")) {
                    Type = null;
                } else {
                    Type = request.getParameter("type").toString();
                }
            } */
            /** USER ID **/
            if (request.getParameter("userid") != null) {
                if (request.getParameter("userid").equals("")) {
                    userId = null;
                } else {
                    userId = Integer.parseInt(request.getParameter("userid"));
                }
            }
            /** BOOK TYPE **/
            if (request.getParameter("bookType") != null) {
                if (request.getParameter("bookType").equals("")) {
                    bookType = null;
                } else {
                    bookType = Integer.parseInt(request.getParameter("bookType"));
                }
            }


            /** Voucher Type **/
            if (request.getParameter("voutype") != null) {
                if (request.getParameter("voutype").equals("")) {
                    vouType = null;
                } else {
                    vouType = Integer.parseInt(request.getParameter("voutype"));
                }
            }

            /** COA NAME **/
            if (request.getParameter("coanm") != null) {
                if (request.getParameter("coanm").equals("")) {
                    coaNm = null;
                } else {
                    coaNm = Integer.parseInt(request.getParameter("coanm"));
                }
            }

            /** BANK CASH  **/
            if (request.getParameter("bankcash") != null) {
                if (request.getParameter("bankcash").equals("")) {
                    bankCash = null;
                } else {
                    bankCash = request.getParameter("bankcash");
                }
            }

            /** Entity Group Name **/
            if (request.getParameter("entitygrp") != null) {
                if (request.getParameter("entitygrp").equals("")) {
                    entityGrp = null;
                } else {
                    entityGrp = Integer.parseInt(request.getParameter("entitygrp"));
                }
            }

            /** ADJUSTMENT TYPE **/
            if (request.getParameter("adjtype") != null) {
                if (request.getParameter("adjtype").equals("")) {
                    adjType = null;
                } else {
                    adjType = Integer.parseInt(request.getParameter("adjtype"));
                }
            }

            System.out.println("org " + org + " To Date " + toDate + " from date " + fromDate + " coa " + coaId +
                               "Na Id " + naId + " cog " + cogId + " post " + postFlag + " sloc " + slocId + " cld " +
                               cldId + " ho " + hoOrgId + " path " + path + " Book Type " + bookType + "To Vou No. " +
                               toVocNo + "From Voucher No." + fromVocNo + " Voucher Type " + vouType + " Bank Cash" +
                               bankCash + "COA NAME " + coaNm + "Entity Group " + entityGrp + "ADJ Type " + adjType);
            /** Get Report Name to be opened... **/

            if (tab.equalsIgnoreCase("gl")) {
                if (linkType.equalsIgnoreCase("genLed")) {
                    reportName = "GeneralLedger";
                } else if (linkType.equalsIgnoreCase("genLedCons")) {
                    reportName = "GeneralLedgerConsolidated";
                } else if (linkType.equalsIgnoreCase("trlBalGrp")) {
                    reportName = "TrialBalance_GroupWise";
                } else if (linkType.equalsIgnoreCase("trlBalGrpCons")) {
                    reportName = "TrialBalance_GroupWise_Consolidate";
                } else if (linkType.equalsIgnoreCase("printVou")) {
                    reportName = "Print_voucher";
                } else if (linkType.equalsIgnoreCase("advice")) {
                    if ((request.getParameter("postflag").equalsIgnoreCase("P") && (vouType == 2) ||
                         (vouType == 15 && adjType == 1))) {
                        reportName = "Posted_Advice_Gl";
                    } else if ((request.getParameter("postflag").equalsIgnoreCase("P") &&
                                (vouType == 3)) || (vouType == 15 && adjType == 2)) {
                        reportName = "Posted_Receipt_Advice_Gl";
                    } else if (request.getParameter("postflag").equalsIgnoreCase("A") && (vouType == 2)) {
                        reportName = "Draft_Advice_Gl";
                    }
                } else if (linkType.equalsIgnoreCase("otherVou")) {
                    reportName = "Other_voucher";
                } else if (linkType.equalsIgnoreCase("usrgl")) {
                    reportName = "GeneralLedgerUser";
                }
            }
            if (tab.equalsIgnoreCase("str")) {
                if (linkType.equalsIgnoreCase("taxcoawise")) {
                    if (request.getParameter("strType").equalsIgnoreCase("coawise")) {
                        reportName = "TaxRegister_Coawise";
                    }
                } else if (linkType.equalsIgnoreCase("taxtaxwise")) {
                    if (request.getParameter("strType").equalsIgnoreCase("taxwise")) {
                        reportName = "TaxRegister_TaxWise";
                    }
                } else if (linkType.equalsIgnoreCase("tdscoawise")) {
                    if (request.getParameter("strType").equalsIgnoreCase("coawise")) {
                        reportName = "TDSRegister_Coawise";
                    }
                } else if (linkType.equalsIgnoreCase("tdstdswise")) {
                    if (request.getParameter("strType").equalsIgnoreCase("tdswise")) {
                        reportName = "TDSRegister_TaxWise";
                    }
                }
            }

            if (tab.equalsIgnoreCase("daybook")) {
                if (linkType.equalsIgnoreCase("daybook")) {
                    if (bookType == 80 || bookType == 81) {
                        reportName = "Bank_cash_book_report";
                    } else {
                        reportName = "Bank_cash_book_report_Others";
                    }
                }


                else if (linkType.equalsIgnoreCase("daybookcons")) {
                    if (bookType == 80 || bookType == 81) {
                        reportName = "Day_Book_Consolidate";
                    } else {
                        reportName = "Day_Book_Consolidate_Others";
                    }
                }

                else if (linkType.equalsIgnoreCase("daybooksum")) {
                    reportName = "Bank_cash_book_HeadWise_SubReport";
                } else if (linkType.equalsIgnoreCase("daybooksumcons")) {
                    reportName = "Day_Book_Summary_Consolidate";
                } else if (linkType.equalsIgnoreCase("booktypewisesum")) {
                    reportName = "Book_Type_Wise_Summary";
                } else if (linkType.equalsIgnoreCase("compsheet")) {
                    reportName = "Comparison";
                } else if (linkType.equalsIgnoreCase("bookprod")) {
                    reportName = "book_production";
                } else if (linkType.equalsIgnoreCase("bookprodtitle")) {
                    reportName = "Book_production_Title_Wise";
                }
            }

            if (tab.equalsIgnoreCase("bspnl")) {
                if (linkType.equalsIgnoreCase("balsheetdtl")) {
                    reportName = "BalanceSheet";
                } else if (linkType.equalsIgnoreCase("balsheetsum")) {
                    reportName = "BalanceSheet";
                } else if (linkType.equalsIgnoreCase("balsheetconsdtl")) {
                    reportName = "BalanceSheet_Consolidate";
                } else if (linkType.equalsIgnoreCase("balsheetconssum")) {
                    reportName = "BalanceSheet_Consolidate";
                } else if (linkType.equalsIgnoreCase("pnldtl")) {
                    reportName = "pnl_rep";
                } else if (linkType.equalsIgnoreCase("pnlsum")) {
                    reportName = "pnl_rep";
                } else if (linkType.equalsIgnoreCase("pnldtlcons")) {
                    reportName = "pnl_rep_consolidate";
                } else if (linkType.equalsIgnoreCase("pnldtlconssum")) {
                    reportName = "pnl_rep_consolidate";
                }
            }

            if (tab.equalsIgnoreCase("budget")) {
                if (linkType.equalsIgnoreCase("budget")) {
                    reportName = "Budget_Report";
                }
            }
            System.out.println("report name..." + reportName);

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
            parameters.put("Na_Id", naId);
            parameters.put("Cog_Id", cogId);
            parameters.put("Coa_Id", coaId);
            parameters.put("To_Date", toDate);
            parameters.put("From_Date", fromDate);
            parameters.put("PostFlag", postFlag);
            parameters.put("Path", path);
            parameters.put("Report_Type", check);

            parameters.put("From_Voucher_No", fromVocNo);
            parameters.put("Voucher_Type", vouType);
            parameters.put("To_Voucher_No", toVocNo);
            parameters.put("User_Id", userId);
            parameters.put("Book_Type", bookType);
            parameters.put("Coa_Nm", coaNm);

            parameters.put("Sheet_Type", bankCash);
            parameters.put("GroupAccount", GroupAccount);
            parameters.put("zeroAmt", ZeroAmtvalue);
            parameters.put("ConOrgId", request.getParameter("ConOrgId"));
            parameters.put("AsOnDate", asondate);
            parameters.put("Entity_Group", entityGrp);


            /*     String user=null;

            if(userId==null){
                 input = new FileInputStream(new File(path + "GeneralLedger.jrxml"));
                 user="";
            }
            else{

              parameters.put("UserId", userId);

                input = new FileInputStream(new File(path + "GeneralLedgerUser.jrxml"));
                user="User";
            } */
            InputStream input = new FileInputStream(new File(path + reportName + ".jrxml"));


            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design);


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

                        throw (ex);
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    public Object resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

}


