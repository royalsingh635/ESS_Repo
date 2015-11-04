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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.SimpleDateFormat;

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
import net.sf.jasperreports.engine.util.JRLoader;
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
            Integer ccId = null;
            String projId = null;
            String reqId=null; 

            Integer coaNm = null;
            String bankCash = null;
            Integer entityGrp = null; //for Entity Group

            Integer slocId = Integer.parseInt(request.getParameter("slocid"));
            String cldId = request.getParameter("cldid");
            String hoOrgId = request.getParameter("hoorgid");
            String org = request.getParameter("organisation");

            String sysDate = request.getParameter("todate").toString();
            java.util.Date date = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(sysDate);
            System.out.println("date__________" + date);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            String toDate = sdf1.format(date);
            System.out.println("SYS DAATE---------" + sysDate + "fromDate-------------" + toDate);
            //String toDate=null;

            String fromDate = null;
            String reportType = request.getParameter("reporttype");
            String postFlag = request.getParameter("postflag");
            String GroupAccount = request.getParameter("GroupAccount");
            String zeroAmt = request.getParameter("zeroAmt");
            String ZeroAmtvalue = "Y";

            String asondate = null;
            /* String asOnSysDt = (request.getParameter("asondate"));
            java.util.Date dateAsON = null;
            SimpleDateFormat sdfAsON = new SimpleDateFormat("yyyy-MM-dd");
            dateAsON = sdfAsON.parse(asOnSysDt);
            SimpleDateFormat sdfAsON1 = new SimpleDateFormat("yyyy-MM-dd");
            String asondate = sdfAsON1.format(dateAsON);
            System.out.println("SYS AS ON DATE---------" + asOnSysDt + "AS ON DATE-------------" + asondate); */


            String check = null; // For csv or pdf format
            Integer adjType = null; // For Adjustment Type CR/DR
            String includeGrp = null; //For calling COG Report conditionally .
            String prv=null; // For PNL Provision Wise.
            String includeGrpChk = "N"; // For reverting value of includeGrp as Y or N in report.

            BigDecimal amt = null;
            BigDecimal amtX1 = null;
            BigDecimal amtX2 = null;

            String bgcolor = null;
            String head = null;
            String margin = null; //For selecting Orgnisation in Margin Area.
            Integer currId = null; // For multi- currency.
            String logo=null; // FOR ORG LOGO
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
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/FINDS");
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

            /**
             * FROM DATE
             */
            if (request.getParameter("fromdate") != null) {
                if (request.getParameter("fromdate").equals("")) {
                    fromDate = null;
                } else {
                    fromDate = request.getParameter("fromdate");
                }
            }

            /**
             * AS ON DATE
             */
            if (request.getParameter("asondate") != null) {
                if (request.getParameter("asondate").equals("")) {
                    asondate = null;
                } else {
                    asondate = request.getParameter("asondate");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date dt = dateFormat.parse(asondate.toString());
                    asondate = dateFormat.format(dt);
                }
            }

            /**
             * TO DATE
             */
            /* if (request.getParameter("todate") != null) {
                if (request.getParameter("todate").equals("")) {
                    toDate = null;
                } else {
                    toDate = request.getParameter("todate");
                    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
                    Date dt = (Date) dateFormat.parse(toDate.toString());
                    toDate = dateFormat.format(dt);
                }
            } */

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

            if (request.getParameter("bgcolor") != null) {
                if (request.getParameter("bgcolor").equals("")) {
                    bgcolor = "D";
                    System.out.println("--->" + request.getParameter("bgcolor"));
                } else {
                    bgcolor = request.getParameter("bgcolor");
                }
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

            /** Currency Id **/
            if (request.getParameter("currId") != null) {
                if (request.getParameter("currId").equals("")) {
                    currId = null;
                } else {
                    currId = Integer.parseInt(request.getParameter("currId"));
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

            /** COST CENTER ID **/
            if (request.getParameter("ccId") != null) {
                if (request.getParameter("ccId").equals("")) {
                    ccId = null;
                } else {
                    ccId = Integer.parseInt(request.getParameter("ccId"));
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

            /** PROJECT NAME **/
            if (request.getParameter("projId") != null) {
                if (request.getParameter("projId").equals("")) {
                    projId = null;
                } else {
                    projId = request.getParameter("projId");
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

            /** INCLUDE SUB GROUP. **/
            System.out.println(request.getParameter("includeSubGrp") + "________________________");
            if (request.getParameter("includeSubGrp") != null) {
                if (request.getParameter("includeSubGrp").equals("")) {
                    includeGrp = null;
                } else {
                    includeGrp = request.getParameter("includeSubGrp");
                    if (includeGrp.equalsIgnoreCase("true")) {
                        includeGrpChk = "Y";
                        System.out.println("IF........" + includeGrpChk);
                    } else if (includeGrp.equalsIgnoreCase("false")) {
                        includeGrpChk = "N";
                        System.out.println("Else........" + includeGrpChk);
                    }

                }
            }
            
            /** ADD PROVISION **/
            if (request.getParameter("prv") != null) {
                if (request.getParameter("prv").equals("")) {
                    prv = null;
                } else {
                    prv = request.getParameter("prv");
                    if (prv.equalsIgnoreCase("true")) {
                        prv = "Y";
                        
                    } else if (prv.equalsIgnoreCase("false")) {
                        prv = "N";
                    }

                }
            }

            /** Amount **/
            if (request.getParameter("amountnet") != null) {
                if (request.getParameter("amountnet").equals("")) {
                    amt = null;
                } else {
                    Double y = Double.parseDouble(request.getParameter("amountnet"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    amt = z;
                }
            }

            /** AmountX1 **/
            if (request.getParameter("amountx1") != null) {
                if (request.getParameter("amountx1").equals("")) {
                    amtX1 = null;
                } else {
                    Double y = Double.parseDouble(request.getParameter("amountx1"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    amtX1 = z;
                }
            }

            /** AmountX2 **/
            if (request.getParameter("amountx2") != null) {
                if (request.getParameter("amountx2").equals("")) {
                    amtX2 = null;
                } else {

                    Double y = Double.parseDouble(request.getParameter("amountx2"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    amtX2 = z;
                }
            }

            /** REQUISTION ID **/
            if (request.getParameter("reqId") != null) {
                if (request.getParameter("reqId").equals("")) {
                    reqId = null;
                } else {
                    reqId = request.getParameter("reqId");
                }
                
                /**
                 * For ORG LOG HO WISE.
                 */
                if (request.getParameter("logo") != null) {
                    if (request.getParameter("logo").equals("")) {
                        logo = null;
                    } else {
                        logo = request.getParameter("logo");
                        System.out.println("logo__________________"+logo);
                    }
                }

            }
            System.out.println("logo__"+request.getParameter("logo"));
            String logo1=request.getParameter("logo");
            System.out.println("logo 1"+logo1);
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

            System.out.println("org " + org + " To Date " + toDate + " from date " + fromDate + " coa " + coaId +
                               "Na Id " + naId + " cog " + cogId + " post " + postFlag + " sloc " + slocId + " cld " +
                               cldId + " ho " + hoOrgId + " path " + path + " Book Type " + bookType + "To Vou No. " +
                               toVocNo + "From Voucher No." + fromVocNo + " Voucher Type " + vouType + " Bank Cash" +
                               bankCash + "COA NAME " + coaNm + "Entity Group " + entityGrp + "ADJ Type " + adjType +
                               "Include Grp " + includeGrp + "Include Chk Grp " + includeGrpChk + "Currency Id " +
                               currId + "Amount X1 " + amtX1 + "Amount X2 " + amtX2 + "CC ID " + ccId + " Project Id " +
                               projId + "REQ ID "+reqId + "Provision :  "+prv +" LOGO :  "+logo1);

            System.out.println("Color is " + bgcolor + " Header color is " + head);
            System.out.println("Margin Details :- " + margin);

            /** Get Report Name to be generated... **/

            if (tab.equalsIgnoreCase("gl")) {
                if (linkType.equalsIgnoreCase("genLed")) {
                    if (ccId != null && !ccId.toString().equalsIgnoreCase("")) {
                        reportName = "GeneralLedger_CostCenter";
                    } else if (projId != null && !projId.toString().equalsIgnoreCase("")) {
                        reportName = "GeneralLedger_ProjectWise";
                    } else
                        reportName = "GeneralLedger";
                } else if (linkType.equalsIgnoreCase("genLedCons")) {
                    reportName = "GeneralLedgerConsolidated";
                } else if (linkType.equalsIgnoreCase("iouDetails")) {
                    reportName = "IOUDetails";
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
                    } else if ((request.getParameter("postflag").equalsIgnoreCase("P") && (vouType == 3)) ||
                               (vouType == 15 && adjType == 2)) {
                        reportName = "Posted_Receipt_Advice_Gl";
                    } else if (request.getParameter("postflag").equalsIgnoreCase("A") && (vouType == 2)) {
                        reportName = "Draft_Advice_Gl";
                    }
                } else if (linkType.equalsIgnoreCase("otherVou")) {
                    reportName = "Other_voucher";
                } else if (linkType.equalsIgnoreCase("usrgl")) {
                    reportName = "GeneralLedgerUser";
                } else if (linkType.equalsIgnoreCase("usrvoutrans")) {
                    reportName = "Voucher_Count_UserWise";
                }
            } else if (tab.equalsIgnoreCase("str")) {
                if (linkType.equalsIgnoreCase("taxcoawise")) {
                    if (request.getParameter("strType").equalsIgnoreCase("coawise")) {
                        reportName = "TaxRegister_Coawise";
                    }
                } else if (linkType.equalsIgnoreCase("taxcoawisecons")) {
                    if (request.getParameter("strType").equalsIgnoreCase("coawise")) {
                        reportName = "TaxRegister_Coawise";
                    }
                } else if (linkType.equalsIgnoreCase("taxtaxwise")) {
                    if (request.getParameter("strType").equalsIgnoreCase("taxwise")) {
                        reportName = "TaxRegister_TaxWise";
                    }
                } else if (linkType.equalsIgnoreCase("taxtaxwisecons")) {
                    if (request.getParameter("strType").equalsIgnoreCase("taxwise")) {
                        reportName = "TaxRegister_TaxWise";
                    }
                } else if (linkType.equalsIgnoreCase("tdscoawise")) {
                    if (request.getParameter("strType").equalsIgnoreCase("coawise")) {
                        reportName = "TDSRegister_Coawise";
                    }
                } else if (linkType.equalsIgnoreCase("tdscoawisecons")) {
                    if (request.getParameter("strType").equalsIgnoreCase("coawise")) {
                        reportName = "TDSRegister_Coawise";
                    }
                } else if (linkType.equalsIgnoreCase("tdstdswise")) {
                    if (request.getParameter("strType").equalsIgnoreCase("tdswise")) {
                        reportName = "TDSRegister_TaxWise";
                    }
                } else if (linkType.equalsIgnoreCase("tdstdswisecons")) {
                    if (request.getParameter("strType").equalsIgnoreCase("tdswise")) {
                        reportName = "TDSRegister_TaxWise";
                    }
                }
            } else if (tab.equalsIgnoreCase("daybook")) {
                if (linkType.equalsIgnoreCase("daybook")) {
                    if (bookType == 80 || bookType == 81) {
                        reportName = "Bank_cash_book_report";
                    } else {
                        reportName = "Bank_cash_book_report_Others";
                    }
                } else if (linkType.equalsIgnoreCase("daybookcons")) {
                    if (bookType == 80 || bookType == 81) {
                        reportName = "Day_Book_Consolidate";
                    } else {
                        reportName = "Day_Book_Consolidate_Others";
                    }
                } else if (linkType.equalsIgnoreCase("daybooksum")) {
                    if (bookType == 80 || bookType == 81) {
                        reportName = "Bank_cash_book_HeadWise_SubReport";
                    } else {
                        reportName = "Bank_cash_book_HeadWise_SubReport_Others";
                    }
                } else if (linkType.equalsIgnoreCase("daybooksumcons")) {
                    reportName = "Day_Book_Summary_Consolidate";
                } else if (linkType.equalsIgnoreCase("booktypewisesum")) {
                    reportName = "Book_Type_Wise_Summary";
                } else if (linkType.equalsIgnoreCase("register")) {
                    reportName = "Register";
                } else if (linkType.equalsIgnoreCase("pnlanalysis")) {
                    reportName = "PnLAnalysis";
                } else if (linkType.equalsIgnoreCase("bookprod")) {
                    reportName = "book_production";
                } else if (linkType.equalsIgnoreCase("bookprodtitle")) {
                    reportName = "Book_production_Title_Wise";
                } else if (linkType.equalsIgnoreCase("cashflow")) {
                    reportName = "CashInflowVsOutFlow";
                } else if (linkType.equalsIgnoreCase("pdcRegsiter")) {
                    reportName = "Requistion_Register";
                }
            } else if (tab.equalsIgnoreCase("bspnl")) {
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
                    reportName = "pnl_rep_tree";
                } else if (linkType.equalsIgnoreCase("pnldtlcons")) {
                    reportName = "pnl_rep_consolidate";
                } else if (linkType.equalsIgnoreCase("pnldtlconssum")) {
                    reportName = "pnl_rep_consolidate_tree";
                } else if (linkType.equalsIgnoreCase("pnlcostcenter")) {
                    reportName = "Pnl_CostCenter";
                } else if (linkType.equalsIgnoreCase("pnlactualbudget")) {
                    reportName = "Pnl_ActualVsBudget";
                } else if (linkType.equalsIgnoreCase("pnlprojwise")) {
                    reportName = "Pnl_ProjectWise";
                }
            } else if (tab.equalsIgnoreCase("budget")) {
                if (linkType.equalsIgnoreCase("budget")) {
                    reportName = "Budget_Report";
                }
            } else if (tab.equalsIgnoreCase("global")) {
                if (linkType.equalsIgnoreCase("11516") && request.getParameter("fileName") != null &&
                    request.getParameter("fileName").toString().length() > 0) {
                    reportName = request.getParameter("fileName");
                    System.out.println("Inside global report ");
                }
            }

            System.out.println("report name..." + reportName);

            /**
             * For Path either module wise or Portal wise
             */
            if (linkType.equalsIgnoreCase("11516") && request.getParameter("fileName") != null) {
                path = path + "Portal/";
            } else {
                path = path + "FIN/";
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
            parameters.put("Include_Grp", includeGrpChk);
            parameters.put("Include_Prv", prv);
            parameters.put("Curr_Id", currId);
            parameters.put("AmountX1", amtX1);
            parameters.put("AmountX2", amtX2);
            parameters.put("Proj_Id", projId);
            parameters.put("Cc_Id", ccId);
            parameters.put("Req_Id", reqId);

            parameters.put("BgColor", bgcolor);
            parameters.put("Head", head);
            parameters.put("Margin_Detail", margin);
            parameters.put("Org_Logo", logo1);

            /* InputStream input = new FileInputStream(new File(path + reportName + ".jrxml"));
            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design); */

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
}


