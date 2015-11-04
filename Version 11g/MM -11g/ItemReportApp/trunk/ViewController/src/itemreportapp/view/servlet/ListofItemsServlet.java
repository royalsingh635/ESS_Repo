package itemreportapp.view.servlet;

import itemreportapp.model.module.ItemReportAppAMImpl;

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
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import oracle.jbo.client.Configuration;

public class ListofItemsServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String link = request.getParameter("link");
        response.setContentType(CONTENT_TYPE);
        Connection conn = null;
        DataSource ds = null;

        try {

            //PreparedStatement st = null;
            // String amDef = "itemreportapp.model.module.ItemReportAppAMImpl";
            //String config = "ItemReportAppAMLocal";
            if (link.equals("gl3") || link.equals("gl15")) {
                // config = "ItemReportAppMMAMLocal";
                Context ctx = new InitialContext();
                ds = (DataSource)ctx.lookup("java:comp/env/jdbc/MMDS");
                System.out.println(" inside if config");

            } else {
                // config = "ItemReportAppAMLocal";
                System.out.println(" inside else config");
                Context ctx = new InitialContext();
                ds = (DataSource)ctx.lookup("java:comp/env/jdbc/APPDS");

            }
            // ItemReportAppAMImpl am =
            //    (ItemReportAppAMImpl)Configuration.createRootApplicationModule(amDef,config);

            Boolean checkImg = null;
            // Image------------------------------------
            checkImg = Boolean.parseBoolean(request.getParameter("checkImg"));
            System.out.println("Image check is--" + checkImg);

            // st = am.getDBTransaction().createPreparedStatement("select 1 from dual", 0);
            // Connection conn = st.getConnection();

            conn = ds.getConnection();
            PreparedStatement ps =
                                    conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

                                ResultSet rs = ps.executeQuery();
                                String path = null;
                                while (rs.next()) {
                                    path = rs.getString(1);
                                }
            System.out.println(path);
            

            InputStream input = null;
            System.out.println("link value ----   " + link);
            System.out.println("report typer    " + request.getParameter("ReportType"));
            if (link.equals("gl1")) {
                if (checkImg == false) {
                    System.out.println("report without image call---");
                    input = new FileInputStream(new File(path+"ListofItems.jrxml"));
                } else if (checkImg == true) {
                    System.out.println("report with image call---");
                    input = new FileInputStream(new File(path+"ListofItemsWithImage.jrxml"));
                } else {
                    System.out.println("there is some problem in params");
                }
            }
            if(link.equals("gl22")){
                input = new FileInputStream(new File(path+"ListOfItemsWithPrice.jrxml"));
            }
            if (link.equals("gl2")) {
                if (checkImg == false) {
                    System.out.println("gropureport without image call---");
                    input = new FileInputStream(new File(path+"GroupwiseListOfItems.jrxml"));
                } else if (checkImg == true) {
                    System.out.println("gropureport with image call---");
                    input = new FileInputStream(new File(path+"GroupwiseListOfItemsWithImage.jrxml"));
                } else {
                    System.out.println("some problem in calling group report params---");
                }
            }
            if (link.equals("gl3"))
                input = new FileInputStream(new File(path+"SupplierwiseListOfItems.jrxml"));
            if (link.equals("gl4"))
                input = new FileInputStream(new File(path+"BarCodeReport.jrxml"));
            if (link.equals("gl5"))
                input = new FileInputStream(new File(path+"AlternateItemReport.jrxml"));
            if (link.equals("gl6"))
                input = new FileInputStream(new File(path+"GroupwiseAlternateItem.jrxml"));
            if (link.equals("gl7"))
                input = new FileInputStream(new File(path+"ListofWareHouse.jrxml"));
            if (link.equals("gl8"))
                input = new FileInputStream(new File(path+"GroupwiseListofWareHouse.jrxml"));
            if (link.equals("gl9"))
                input = new FileInputStream(new File(path+"ListOfBins.jrxml"));
            if (link.equals("gl10"))
                input = new FileInputStream(new File(path+"GroupwiseBinsReport.jrxml"));
            if (link.equals("gl11"))
                input = new FileInputStream(new File(path+"KitItemsReport.jrxml"));
            if (link.equals("gl12"))
                input = new FileInputStream(new File(path+"GroupwiseKitItems.jrxml"));
            if (link.equals("gl13"))
                input = new FileInputStream(new File(path+"ListofGroups.jrxml"));
            if (link.equals("gl14"))
                input = new FileInputStream(new File(path+"ListofSuppliersReport.jrxml"));
            if (link.equals("gl15"))
                input = new FileInputStream(new File(path+"PerformanceEvalParamReport.jrxml"));
            if (link.equals("gl16"))
                input = new FileInputStream(new File(path+"UOMReport.jrxml"));
            if (link.equals("gl17"))
                input = new FileInputStream(new File(path+"UomConversionReport.jrxml"));
            if (link.equals("gl18"))
                input = new FileInputStream(new File(path+"MMProfileSetup.jrxml"));
            if (link.equals("gl19"))
                input = new FileInputStream(new File(path+"ListOfItemSingleAttribute.jrxml"));
            if (link.equals("gl20"))
                input = new FileInputStream(new File(path+"AttributeWiseItemReport.jrxml"));
            if (link.equals("gl21")){
                System.out.println("New report Item Group$$$$$@@@@@-");
                input = new FileInputStream(new File(path+"ListOfItemGroup.jrxml"));
            }

            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design);


            //get parameter from form
            String Attid = null;
            String show_Tech_Name = "null";
            String show_Legacy_Code = "null";
            String reporttype = null;
            String grpId = null;
            String itmId = null;
            String consflg = null;
            String capitalgdflg = null;
            String cashpurflg = null;
            String stkflg = null;
            String srvcitmflg = null;
            String slsitmflg = null;
            String puritmflg = null;
            String wipitmflg = null;
            String taxexmptflg = null;
            String actv = null;
            String uombasic = null;
            String uompur = null;
            String uomsls = null;
            String maxstk = null;
            String minstk = null;
            String safeqty = null;
            String reorderlvl = null;
            BigDecimal pricebasicmin = null;
            BigDecimal pricebasicmax = null;
            BigDecimal pricepurmin = null;
            BigDecimal pricepurmax = null;
            BigDecimal priceslsmin = null;
            BigDecimal priceslsmax = null;
            String whId = null;
            String blackListed = null;
            String onHold = null;
            String paramSetId = null;
            String uomClassId = null;
            String cldId = null;
            Integer slocId = null;
            String hoOrgId = null;
            String orgId = null;
            String usrId = null;
            cldId = String.valueOf(request.getParameter("CldId"));
            slocId = Integer.parseInt(request.getParameter("SlocId"));
            hoOrgId = String.valueOf(request.getParameter("HoOrgId"));
            System.out.println("before getting org while call any report");
            orgId = String.valueOf(request.getParameter("OrgId"));
            System.out.println("after get org--" + orgId);
            // usrId=String.valueOf(request.getParameter("UsrId"));
            if (request.getParameter("link").equals("gl1") || request.getParameter("link").equals("gl2") ||
                request.getParameter("link").equals("gl3") || request.getParameter("link").equals("gl4") ||
                request.getParameter("link").equals("gl5") || request.getParameter("link").equals("gl6") ||
                request.getParameter("link").equals("gl19") || request.getParameter("link").equals("gl20")
                || request.getParameter("link").equals("gl22")) {
//
                if (request.getParameter("Attid").trim().equals("") || request.getParameter("Attid") == null) {
                    Attid = null;
                } else {
                    Attid = String.valueOf(request.getParameter("Attid").trim());
                }
                //Price Basic minimum
                if (request.getParameter("PriceBasicMin").equals("0.0") ||
                    request.getParameter("PriceBasicMin").equals("0")) {
                    pricebasicmin = null;
                } else {
                    Double y = Double.parseDouble(request.getParameter("PriceBasicMin"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    pricebasicmin = z;
                }

                //Price Basic Max
                if (request.getParameter("PriceBasicMax").equals("0.0") ||
                    request.getParameter("PriceBasicMax").equals("0")) {
                    pricebasicmax = null;
                } else {
                    Double y = Double.parseDouble(request.getParameter("PriceBasicMax"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    pricebasicmax = z;
                }

                //Price Purchase minimum
                if (request.getParameter("PricePurMin").equals("0.0") ||
                    request.getParameter("PricePurMin").equals("0")) {
                    pricepurmin = null;
                } else {
                    Double y = Double.parseDouble(request.getParameter("PricePurMin"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    pricepurmin = z;
                }

                //Price Purchase maximum
                if (request.getParameter("PricePurMax").equals("0.0") ||
                    request.getParameter("PricePurMax").equals("0")) {
                    pricepurmax = null;
                } else {
                    Double y = Double.parseDouble(request.getParameter("PricePurMax"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    pricepurmax = z;
                }

                //Price sales minimum
                if (request.getParameter("PriceSlsMin").equals("0.0") ||
                    request.getParameter("PriceSlsMin").equals("0")) {
                    priceslsmin = null;
                } else {
                    Double y = Double.parseDouble(request.getParameter("PriceSlsMin"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    priceslsmin = z;
                }

                //Price sales maximum
                if (request.getParameter("PriceSlsMax").equals("0.0") ||
                    request.getParameter("PriceSlsMax").equals("0")) {
                    priceslsmax = null;
                } else {
                    Double y = Double.parseDouble(request.getParameter("PriceSlsMax"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    priceslsmax = z;
                }

                //Reorder Level
                if (request.getParameter("ReorderLvl").equals("D") || request.getParameter("ReorderLvl") == null) {
                    reorderlvl = null;
                } else {
                    reorderlvl = String.valueOf(request.getParameter("ReorderLvl"));
                }

                //Safety Level
                if (request.getParameter("SafeQty").equals("D") || request.getParameter("SafeQty") == null) {
                    safeqty = null;
                } else {
                    safeqty = String.valueOf(request.getParameter("SafeQty"));
                }

                //Min Stock
                if (request.getParameter("MinStk").equals("D") || request.getParameter("MinStk") == null) {
                    minstk = null;
                } else {
                    minstk = String.valueOf(request.getParameter("MinStk"));
                }

                //Max Stock
                if (request.getParameter("MaxStk").equals("D") || request.getParameter("MaxStk") == null) {
                    maxstk = null;
                } else {
                    maxstk = String.valueOf(request.getParameter("MaxStk"));
                }

                //UOMSales
                if (request.getParameter("UomSls").equals("") || request.getParameter("UomSls") == null) {
                    uomsls = null;
                } else {
                    uomsls = String.valueOf(request.getParameter("UomSls"));
                }

                //UOMPurchase
                if (request.getParameter("UomPur").equals("") || request.getParameter("UomPur") == null) {
                    uompur = null;
                } else {
                    uompur = String.valueOf(request.getParameter("UomPur"));
                }

                //UOMBasic
                if (request.getParameter("UomBasic").equals("") || request.getParameter("UomBasic") == null) {
                    uombasic = null;
                } else {
                    uombasic = String.valueOf(request.getParameter("UomBasic"));
                }

                //Active Items----------
                if (request.getParameter("Actv").equals("") || request.getParameter("Actv") == null) {
                    actv = null;
                } else {
                    actv = String.valueOf(request.getParameter("Actv"));
                }


                //TaxExmptItems----------
                if (request.getParameter("TaxExmptFlg").equals("") || request.getParameter("TaxExmptFlg") == null) {
                    taxexmptflg = null;
                } else {
                    taxexmptflg = String.valueOf(request.getParameter("TaxExmptFlg"));
                }


                //WIP Items----------
                if (request.getParameter("WipItmFlg").equals("") || request.getParameter("WipItmFlg") == null) {
                    wipitmflg = null;
                } else {
                    wipitmflg = String.valueOf(request.getParameter("WipItmFlg"));
                }

                //Purchase Items----------
                if (request.getParameter("PurItmFlg").equals("") || request.getParameter("PurItmFlg") == null) {
                    puritmflg = null;
                } else {
                    puritmflg = String.valueOf(request.getParameter("PurItmFlg"));
                }

                //Capital Gd Items----------
                if (request.getParameter("CapitalGdFlg").equals("") || request.getParameter("CapitalGdFlg") == null) {
                    capitalgdflg = null;
                } else {
                    capitalgdflg = String.valueOf(request.getParameter("CapitalGdFlg"));
                }


                //Cash Purchase items-------
                if (request.getParameter("CashPurFlg").equals("") || request.getParameter("CashPurFlg") == null) {
                    cashpurflg = null;
                } else {
                    cashpurflg = String.valueOf(request.getParameter("CashPurFlg"));
                }


                //Service Items---------
                if (request.getParameter("SlsItmFlg").equals("") || request.getParameter("SlsItmFlg") == null) {
                    slsitmflg = null;
                } else {
                    slsitmflg = String.valueOf(request.getParameter("SlsItmFlg"));
                }


                //Service Items---------
                if (request.getParameter("SrvcItmFlg").equals("") || request.getParameter("SrvcItmFlg") == null) {
                    srvcitmflg = null;
                } else {
                    srvcitmflg = String.valueOf(request.getParameter("SrvcItmFlg"));
                }


                //Consumable---------
                if (request.getParameter("ConsumableFlg").equals("") ||
                    request.getParameter("ConsumableFlg") == null) {
                    consflg = null;
                } else {
                    consflg = String.valueOf(request.getParameter("ConsumableFlg"));
                }


                //Stockable----------
                if (request.getParameter("StockableFlg").equals("") || request.getParameter("StockableFlg") == null) {
                    stkflg = null;
                } else {
                    stkflg = String.valueOf(request.getParameter("StockableFlg"));
                }


                //Report Type----------
                if (request.getParameter("ReportType").equals("")) {
                    System.out.println("report type is null found.-----------");
                    reporttype = "pdf";
                    System.out.println("report type is null found.-----------" + reporttype);

                } else if (request.getParameter("ReportType") == null) {
                    System.out.println("1report type is null found.-----------");
                    reporttype = "pdf";
                    System.out.println("1report type is null found.-----------" + reporttype);

                } else {
                    System.out.println("report type not null    ------- ");
                    reporttype = String.valueOf(request.getParameter("ReportType"));
                    System.out.println("report type not null    " + reporttype);
                }


                //Tech Name----------------
                Boolean stn = Boolean.parseBoolean(request.getParameter("Show_Tech_Name"));
                if (stn == true) {
                    show_Tech_Name = "Y";
                } else if (stn == false) {
                    show_Tech_Name = "N";
                } else {
                    show_Tech_Name = stn.toString();
                }


                //Item Id----------------
                if (request.getParameter("ItmId").equals("") || request.getParameter("ItmId") == null) {
                    itmId = null;
                } else {
                    itmId = String.valueOf(request.getParameter("ItmId"));
                }


                //Legacy Code----------------
                Boolean sln = Boolean.parseBoolean(request.getParameter("Show_Legacy_Code"));
                if (sln == true) {
                    show_Legacy_Code = "Y";
                } else if (sln == false) {
                    show_Legacy_Code = "N";
                } else {
                    show_Legacy_Code = sln.toString();
                }


                //GrpId----------------
                if (request.getParameter("GrpId").trim().equals("") || request.getParameter("GrpId") == null) {
                    grpId = null;
                } else {
                    grpId = String.valueOf(request.getParameter("GrpId").trim());
                }


            }

            if (link.equals("gl7") || link.equals("gl8") || link.equals("gl9") || link.equals("gl10")) {
                //Report Type----------
                if (request.getParameter("ReportType").equals("")) {
                    reporttype = "pdf";
                } else {
                    reporttype = String.valueOf(request.getParameter("ReportType"));
                }
                //GrpId----------------
                if (request.getParameter("OrgId").trim().equals("") || request.getParameter("OrgId") == null) {
                    orgId = null;
                } else {
                    orgId = String.valueOf(request.getParameter("OrgId").trim());
                }
                //GrpId----------------
                if (request.getParameter("WhId").trim().equals("") || request.getParameter("WhId") == null) {
                    whId = null;
                } else {
                    whId = String.valueOf(request.getParameter("WhId").trim());
                }
            }

            if (link.equals("gl11") || link.equals("gl12")) {
                if (request.getParameter("ItmId").trim().equals("") || request.getParameter("ItmId") == null) {
                    itmId = null;
                } else {
                    itmId = String.valueOf(request.getParameter("ItmId").trim());
                }
                if (request.getParameter("ReportType").equals("")) {
                    reporttype = "pdf";
                } else {
                    reporttype = String.valueOf(request.getParameter("ReportType"));
                }

            }

            if (link.equals("gl13") || link.equals("gl21")) {
                if (request.getParameter("GrpId").trim().equals("") || request.getParameter("GrpId") == null) {
                    grpId = null;
                } else {
                    grpId = String.valueOf(request.getParameter("GrpId").trim());
                }
                if (request.getParameter("ReportType").equals("")) {
                    reporttype = "pdf";
                } else {
                    reporttype = String.valueOf(request.getParameter("ReportType"));
                }

            }
            if (link.equals("gl14")) {
                if (request.getParameter("ReportType").equals("")) {
                    reporttype = "pdf";
                } else {
                    reporttype = String.valueOf(request.getParameter("ReportType"));
                }
                if (request.getParameter("BlackListed").equals("") || request.getParameter("BlackListed") == null) {
                    blackListed = null;
                } else {
                    blackListed = String.valueOf(request.getParameter("BlackListed"));
                }
                if (request.getParameter("OnHold").equals("") || request.getParameter("OnHold") == null) {
                    onHold = null;
                } else {
                    onHold = String.valueOf(request.getParameter("OnHold"));
                }
            }

            if (link.equals("gl15")) {
                if (request.getParameter("ReportType").equals("")) {
                    reporttype = "pdf";
                } else {
                    reporttype = String.valueOf(request.getParameter("ReportType"));
                }
                if (request.getParameter("ParamSetId").trim().equals("") ||
                    request.getParameter("ParamSetId") == null) {
                    paramSetId = null;
                } else {
                    paramSetId = String.valueOf(request.getParameter("ParamSetId").trim());
                }
            }

            if (link.equals("gl16") || link.equals("gl17")) {
                if (request.getParameter("ReportType").equals("")) {
                    reporttype = "pdf";
                } else {
                    reporttype = String.valueOf(request.getParameter("ReportType"));
                }
                if (request.getParameter("UomClassId").trim().equals("") ||
                    request.getParameter("UomClassId") == null) {
                    uomClassId = null;
                } else {
                    uomClassId = String.valueOf(request.getParameter("UomClassId").trim());
                }
            }
            if (link.equals("gl18")) {

                if (request.getParameter("ReportType").equals("")) {
                    reporttype = "pdf";
                } else {
                    reporttype = String.valueOf(request.getParameter("ReportType"));
                }
            }

            Map parameters = new HashMap();

            if (request.getParameter("link").equals("gl1") || request.getParameter("link").equals("gl2") ||
                request.getParameter("link").equals("gl3") || request.getParameter("link").equals("gl4") ||
                request.getParameter("link").equals("gl5") || request.getParameter("link").equals("gl6") ||
                request.getParameter("link").equals("gl19") || request.getParameter("link").equals("gl20")
                || request.getParameter("link").equals("gl22")) {
                //set parameter for report
                //  parameters.put("OrgId", orgId);
                parameters.put("Show_Tech_Name", show_Tech_Name);
                System.out.println("tech name ---" + show_Tech_Name + "   legacy code---" + show_Legacy_Code);
                parameters.put("ItmId", itmId);
                parameters.put("Show_Legacy_Code", show_Legacy_Code);
                parameters.put("GrpId", grpId);
                parameters.put("StockableFlg", stkflg);
                parameters.put("ConsumableFlg", consflg);
                parameters.put("SlsItmFlg", slsitmflg);
                parameters.put("SrvcItmFlg", srvcitmflg);
                parameters.put("PurItmFlg", puritmflg);
                parameters.put("WipItmFlg", wipitmflg);
                parameters.put("CashPurFlg", cashpurflg);
                parameters.put("CapitalGdFlg", capitalgdflg);
                parameters.put("TaxExmptFlg", taxexmptflg);
                parameters.put("Actv", actv);
                parameters.put("UomBasic", uombasic);
                parameters.put("UomPur", uompur);
                parameters.put("UomSls", uomsls);
                parameters.put("MaxStk", maxstk);
                parameters.put("MinStk", minstk);
                parameters.put("SaftQty", safeqty);
                parameters.put("ReorderLvl", reorderlvl);
                parameters.put("PriceBasicMin", pricebasicmin);
                parameters.put("PriceBasicMax", pricebasicmax);
                parameters.put("PricePurMin", pricepurmin);
                parameters.put("PricePurMax", pricepurmax);
                parameters.put("PriceSlsMin", priceslsmin);
                parameters.put("PriceSlsMax", priceslsmax);
                System.out.println("attribute id ---" + Attid);
                parameters.put("Attid", Attid);
                parameters.put("CldId", cldId);
                parameters.put("HoOrgId", hoOrgId);
                parameters.put("OrgId", orgId);
                parameters.put("SlocId", slocId);
                parameters.put("Path", path);
                System.out.println(path);
                //parameters.put("Path",path);

            }
            if (link.equals("gl7") || link.equals("gl8") || link.equals("gl9") || link.equals("gl10")) {
                parameters.put("OrgId", orgId);
                System.out.println("org" + orgId + "wh-" + whId);
                parameters.put("WhId", whId);
                parameters.put("Path", path);
                System.out.println(path);
            }
            if (link.equals("gl10") || link.equals("gl11")) {
                parameters.put("ItmId", itmId);
                parameters.put("Path", path);
                System.out.println(path);
            }
            if (link.equals("gl13") || link.equals("gl21")) {
                parameters.put("GrpId", grpId);
                parameters.put("Path", path);
                System.out.println(path);

            }
            if (link.equals("gl14")) {
                parameters.put("BlackListed", blackListed);
                parameters.put("OnHold", onHold);
                parameters.put("Path", path);
                System.out.println(path);
            }
            if (link.equals("gl15")) {
                parameters.put("ParamSetId", paramSetId);
                parameters.put("Path", path);
                System.out.println(path);
            }
            if (link.equals("gl16") || link.equals("gl17")) {
                parameters.put("UomClassId", uomClassId);
                parameters.put("Path", path);
                System.out.println(path);
            }
            if (link.equals("gl18")) {
                System.out.println("--" + cldId + "----" + orgId + "------" + slocId);
                parameters.put("CldId", cldId);
                parameters.put("OrgId", orgId);
                parameters.put("SlocId", slocId);
                parameters.put("Path", path);
                System.out.println(path);
            }

            parameters.put("CldId", cldId);
            parameters.put("HoOrgId", hoOrgId);
            parameters.put("OrgId", orgId);
            parameters.put("SlocId", slocId);
            System.out.println("out side condition params are--" + orgId + "  sloc--" + slocId + " hoorgid ->" +
                               hoOrgId);
            //    parameters.put("UsrId",usrId);

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);


            OutputStream ouputStream = response.getOutputStream();


            JRExporter exporter = null;

            System.out.println("before pdf");
            System.out.println("reporttype      " + reporttype);
            if ("pdf".equalsIgnoreCase(reporttype)) {
                System.out.println("After pdf");
                response.setContentType("application/pdf");
                if (link.equals("gl1"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofItems.pdf\"");
                if (link.equals("gl22"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListOfItemsWithPrice.pdf\"");
                if (link.equals("gl2"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseListofItems.pdf\"");
                if (link.equals("gl3"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"SupplierwiseListofItems.pdf\"");
                if (link.equals("gl4"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"BarcodeListofItems.pdf\"");
                if (link.equals("gl5"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"AlternateItems.pdf\"");
                if (link.equals("gl6"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseAlternateItems.pdf\"");
                if (link.equals("gl7"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofWarehouse.pdf\"");
                if (link.equals("gl8"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"GroupwiseListofWarehouse.pdf\"");
                if (link.equals("gl9"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofBins.pdf\"");
                if (link.equals("gl10"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseListofBins.pdf\"");
                if (link.equals("gl11"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofKits.pdf\"");
                if (link.equals("gl12"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseListofKits.pdf\"");
                if (link.equals("gl13"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofGroups.pdf\"");
                if (link.equals("gl14"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofSuppliers.pdf\"");
                if (link.equals("gl15"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"ParformanceEvaluationParameterList.pdf\"");
                if (link.equals("gl16"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofUnits.pdf\"");
                if (link.equals("gl17"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofUnitsConversion.pdf\"");
                if (link.equals("gl18")) {
                    System.out.println("In mm profile rpt pdf link-----");
                    response.setHeader("Content-Disposition", "attachement; filename=\"MMProfileSetup.pdf\"");
                }
                if (link.equals("gl19")) {
                    System.out.println("In single attribute pdf link-----");
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"ListOfItemSingleAttribute.pdf\"");
                }
                if (link.equals("gl20")) {
                    System.out.println("In single attribute pdf link-----");
                    response.setHeader("Content-Disposition", "attachement; filename=\"AttributeWiseItemReport.pdf\"");
                }
                if (link.equals("gl21"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListOfItemGroup.pdf\"");

                System.out.println("before expo in pdf---");
                exporter = new JRPdfExporter();
                System.out.println("After expo in pdf---" + exporter);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            }

            else if ("rtf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/rtf");
                response.setHeader("Pragma", "no-cache");

                if (link.equals("gl1"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofItems.rtf\"");
                if (link.equals("gl22"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListOfItemsWithPrice.rtf\"");
                if (link.equals("gl2"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseListofItems.rtf\"");
                if (link.equals("gl3"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"SupplierwiseListofItems.rtf\"");
                if (link.equals("gl4"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"BarcodeListofItems.rtf\"");
                if (link.equals("gl5"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"AlternateItems.rtf\"");
                if (link.equals("gl6"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseAlternateItems.rtf\"");
                if (link.equals("gl7"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofWarehouse.rtf\"");
                if (link.equals("gl8"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"GroupwiseListofWarehouse.rtf\"");
                if (link.equals("gl9"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofBins.rtf\"");
                if (link.equals("gl10"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseListofBins.rtf\"");
                if (link.equals("gl11"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofKits.rtf\"");
                if (link.equals("gl12"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseListofKits.rtf\"");
                if (link.equals("gl13"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofGroups.rtf\"");
                if (link.equals("gl14"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofSuppliers.rtf\"");
                if (link.equals("gl15"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"ParformanceEvaluationParameterList.rtf\"");
                if (link.equals("gl16"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofUnits.rtf\"");
                if (link.equals("gl17"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofUnitsConversion.rtf\"");
                if (link.equals("gl18"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"MMProfileSetup.rtf\"");
                if (link.equals("gl19"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"ListOfItemSingleAttribute.rtf\"");
                if (link.equals("gl20"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"AttributeWiseItemReport.rtf\"");
                if (link.equals("gl21"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListOfItemGroup.rtf\"");
                exporter = new JRRtfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            }

            else if ("html".equalsIgnoreCase(reporttype)) {
                exporter = new JRHtmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            }

            else if ("xls".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xls");

                if (link.equals("gl1"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofItems.xls\"");

                if (link.equals("gl22"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListOfItemsWithPrice.xls\"");
                if (link.equals("gl2"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseListofItems.xls\"");
                if (link.equals("gl3"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"SupplierwiseListofItems.xls\"");
                if (link.equals("gl4"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"BarcodeListofItems.xls\"");
                if (link.equals("gl5"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"AlternateItems.xls\"");
                if (link.equals("gl6"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseAlternateItems.xls\"");
                if (link.equals("gl7"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofWarehouse.xls\"");
                if (link.equals("gl8"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"GroupwiseListofWarehouse.xls\"");
                if (link.equals("gl9"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofBins.xls\"");
                if (link.equals("gl10"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseListofBins.xls\"");
                if (link.equals("gl11"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofKits.xls\"");
                if (link.equals("gl12"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseListofKits.xls\"");
                if (link.equals("gl13"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofGroups.xls\"");
                if (link.equals("gl14"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofSuppliers.xls\"");
                if (link.equals("gl15"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"ParformanceEvaluationParameterList.xls\"");
                if (link.equals("gl16"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofUnits.xls\"");
                if (link.equals("gl17"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofUnitsConversion.xls\"");
                if (link.equals("gl18"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"MMProfileSetup.xls\"");
                if (link.equals("gl19"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"ListOfItemSingleAttribute.xls\"");
                if (link.equals("gl20"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"AttributeWiseItemReport.xls\"");
                if (link.equals("gl21"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListOfItemGroup.xls\"");

                exporter = new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("docx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/docx");

                if (link.equals("gl1"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofItems.docx\"");
                if (link.equals("gl22"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListOfItemsWithPrice.docx\"");
                if (link.equals("gl2"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseListofItems.docx\"");
                if (link.equals("gl3"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"SupplierwiseListofItems.docx\"");
                if (link.equals("gl4"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"BarcodeListofItems.docx\"");
                if (link.equals("gl5"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"AlternateItems.docx\"");
                if (link.equals("gl6"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"GroupwiseAlternateItems.docx\"");
                if (link.equals("gl7"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofWarehouse.docx\"");
                if (link.equals("gl8"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"GroupwiseListofWarehouse.docx\"");
                if (link.equals("gl9"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofBins.docx\"");
                if (link.equals("gl10"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseListofBins.docx\"");
                if (link.equals("gl11"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofKits.docx\"");
                if (link.equals("gl12"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseListofKits.docx\"");
                if (link.equals("gl13"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofGroups.docx\"");
                if (link.equals("gl14"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofSuppliers.docx\"");
                if (link.equals("gl15"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"ParformanceEvaluationParameterList.docx\"");
                if (link.equals("gl16"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofUnits.docx\"");
                if (link.equals("gl17"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofUnitsConversion.docx\"");
                if (link.equals("gl18"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"MMProfileSetup.docx\"");
                if (link.equals("gl19"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"ListOfItemSingleAttribute.docx\"");
                if (link.equals("gl20"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"AttributeWiseItemReport.docx\"");
                if (link.equals("gl21"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListOfItemGroup.docx\"");


                exporter = new JRDocxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xlsx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xlsx");

                if (link.equals("gl1"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofItems.xlsx\"");
                if (link.equals("gl22"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListOfItemsWithPrice.xlsx\"");
                if (link.equals("gl2"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseListofItems.xlsx\"");
                if (link.equals("gl3"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"SupplierwiseListofItems.xlsx\"");
                if (link.equals("gl4"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"BarcodeListofItems.xlsx\"");
                if (link.equals("gl5"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"AlternateItems.xlsx\"");
                if (link.equals("gl6"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"GroupwiseAlternateItems.xlsx\"");
                if (link.equals("gl7"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofWarehouse.xlsx\"");
                if (link.equals("gl8"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"GroupwiseListofWarehouse.xlsx\"");
                if (link.equals("gl9"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofBins.xlsx\"");
                if (link.equals("gl10"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseListofBins.xlsx\"");
                if (link.equals("gl11"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofKits.xlsx\"");
                if (link.equals("gl12"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseListofKits.xlsx\"");
                if (link.equals("gl13"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofGroups.xlsx\"");
                if (link.equals("gl14"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofSuppliers.xlsx\"");
                if (link.equals("gl15"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"ParformanceEvaluationParameterList.xlsx\"");
                if (link.equals("gl16"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofUnits.xlsx\"");
                if (link.equals("gl17"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofUnitsConversion.xlsx\"");
                if (link.equals("gl18"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"MMProfileSetup.xlsx\"");
                if (link.equals("gl19"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"ListOfItemSingleAttribute.xlsx\"");
                if (link.equals("gl20"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"AttributeWiseItemReport.xlsx\"");
                if (link.equals("gl21"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListOfItemGroup.xlsx\"");

                exporter = new JRXlsxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xml".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xml");

                if (link.equals("gl1"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofItems.xml\"");
                if (link.equals("gl22"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListOfItemsWithPrice.xml\"");
                if (link.equals("gl2"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseListofItems.xml\"");
                if (link.equals("gl3"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"SupplierwiseListofItems.xml\"");
                if (link.equals("gl4"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"BarcodeListofItems.xml\"");
                if (link.equals("gl5"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"AlternateItems.xml\"");
                if (link.equals("gl6"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseAlternateItems.xml\"");
                if (link.equals("gl7"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofWarehouse.xml\"");
                if (link.equals("gl8"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"GroupwiseListofWarehouse.xml\"");
                if (link.equals("gl9"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofBins.xml\"");
                if (link.equals("gl10"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseListofBins.xml\"");
                if (link.equals("gl11"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofKits.xml\"");
                if (link.equals("gl12"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"GroupwiseListofKits.xml\"");
                if (link.equals("gl13"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofGroups.xml\"");
                if (link.equals("gl14"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofSuppliers.xml\"");
                if (link.equals("gl15"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"ParformanceEvaluationParameterList.xml\"");
                if (link.equals("gl16"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofUnits.xml\"");
                if (link.equals("gl17"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListofUnitsConversion.xml\"");
                if (link.equals("gl18"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"MMProfileSetup.xml\"");
                if (link.equals("gl19"))
                    response.setHeader("Content-Disposition",
                                       "attachement; filename=\"ListOfItemSingleAttribute.xml\"");
                if (link.equals("gl20"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"AttributeWiseItemReport.xml\"");
                if (link.equals("gl21"))
                    response.setHeader("Content-Disposition", "attachement; filename=\"ListOfItemGroup.xml\"");

                System.out.println("before expo");
                exporter = new JRXmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                System.out.println("after expo---" + exporter);
            }

            try {
                System.out.println("expo--" + exporter);
                exporter.exportReport();
            } catch (JRException e) {
                throw new ServletException(e);
            } finally {
                if (ouputStream != null) {
                    try {
                        ouputStream.flush();
                        ouputStream.close();
                        if (conn != null) {
                            conn.close();
                        }
                    }

                    catch (IOException ex) {

                        throw (ex);
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
