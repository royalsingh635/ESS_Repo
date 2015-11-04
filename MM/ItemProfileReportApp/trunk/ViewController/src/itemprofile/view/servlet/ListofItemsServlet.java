package itemprofile.view.servlet;


import itemprofile.model.services.AppModuleImpl;

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
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;


import oracle.jbo.client.Configuration;

public class ListofItemsServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);


        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            String reportName = null; // Used to get Report Name conditionally
            String tab = request.getParameter("tab").toString(); // To know which tab is expanded
            String link = request.getParameter("link").toString(); // To know which type of report user want to print

            String bgcolor = null;
            String hdcolor = null;
            String margin = null;
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MMDS");
            conn = ds.getConnection();

            if (tab.equalsIgnoreCase("itm")) {
                if (link.equalsIgnoreCase("loi")) {
                    String img = request.getParameter("img").toString();
                    if (img.equalsIgnoreCase("true")) {
                        reportName = "ListofItemsWithImage";
                    } else if (img.equalsIgnoreCase("false")) {
                        reportName = "ListofItems";
                    } else {
                        System.out.println("some error in params in list of items link");
                    }
                } else if (link.equalsIgnoreCase("grploi")) {
                    String img = request.getParameter("img").toString();
                    if (img.equalsIgnoreCase("true")) {
                        reportName = "GroupwiseListOfItemsWithImage";
                    } else if (img.equalsIgnoreCase("false")) {
                        reportName = "GroupwiseListOfItems";
                    } else {
                        System.out.println("some error in params in list of items group wise");
                    }

                } else if (link.equalsIgnoreCase("barcode")) {
                    reportName = "BarCodeReport";
                } else if (link.equalsIgnoreCase("loai")) {
                    reportName = "AlternateItemReport";
                } else if (link.equalsIgnoreCase("loisp")) {
                    reportName = "ListOfItemSingleAttribute";
                } else if (link.equalsIgnoreCase("aloi")) {
                    reportName = "AttributeWiseItemReport";
                } else if (link.equalsIgnoreCase("Ploi")) {
                    reportName = "ListOfItemsWithPrice";
                }

            } else if (tab.equalsIgnoreCase("wp")) {
                if (link.equalsIgnoreCase("low")) {
                    reportName = "ListofWareHouse";
                } else if (link.equalsIgnoreCase("lob")) {
                    reportName = "ListOfBins";
                    System.out.println("bc.." + reportName);
                } else if (link.equalsIgnoreCase("bc")) {
                    reportName = "GroupwiseBinsReport";
                    System.out.println("bc.." + reportName);
                }

            } else if (tab.equalsIgnoreCase("kp")) {
                if (link.equalsIgnoreCase("lok")) {
                    reportName = "KitItemsReport";
                }
            } else if (tab.equalsIgnoreCase("grp")) {
                if (link.equalsIgnoreCase("igc")) {
                    reportName = "ListofGroups";
                } else if (link.equalsIgnoreCase("loig")) {
                    reportName = "ListOfItemGroup";
                }

            } else if (tab.equalsIgnoreCase("uom")) {
                if (link.equalsIgnoreCase("lou")) {
                    reportName = "UOMReport";
                } else if (link.equalsIgnoreCase("louc")) {
                    reportName = "UomConversionReport";
                }

            } else if (tab.equalsIgnoreCase("sp")) {   
                if (link.equalsIgnoreCase("los")) {
                    reportName = "ListofSuppliersReport";
                } else if (link.equalsIgnoreCase("SuppEval")) {
                    reportName = "Supplier Evaluation";
                }else if(link.equalsIgnoreCase("SuppAdds")){
                    reportName = "ListOfSupplierDetail_Adds"; 
                }else if(link.equalsIgnoreCase("SPP")){
                    reportName = "SupplierPricePolicy";
                }else if(link.equalsIgnoreCase("SPPH")){
                    reportName = "SupplierPricePolicyHistory";
                }else if(link.equalsIgnoreCase("SPL")){
                    reportName = "SupplierPriceLevel";
                }else if(link.equalsIgnoreCase("SPLItem")){
                    reportName = "SupplierPriceLevelItemWise";
                } else if (link.equalsIgnoreCase("supploi")) {
                    reportName = "SupplierwiseListOfItems";
                }
            } else if(tab.equalsIgnoreCase("QCS")){
                if(link.equalsIgnoreCase("QCG")){
                    reportName = "QC_GroupWise";
                }else if(link.equalsIgnoreCase("QCI")){
                    reportName = "QC_ItemWise";
                }
            }      
             else if (tab.equalsIgnoreCase("oth")) {
                if (link.equalsIgnoreCase("perf")) {
                    reportName = "PerformanceEvalParamReport";
                } else if (link.equalsIgnoreCase("mm")) {
                    reportName = "MMProfileSetup";
                }else if(link.equalsIgnoreCase("stp")){
                    reportName = "StockTakeProfile";
                }else if(link.equalsIgnoreCase("PPPrf")){
                    reportName = "PricePolicyProfile";
                }else if(link.equalsIgnoreCase("PPList")){
                    reportName = "PricePolicyWiseList";
                }else if(link.equalsIgnoreCase("LOIChaptr")){
                    reportName = "ListofItemsWithChapterItem";
                }else if (link.equalsIgnoreCase("ExciseChapter")){
                    reportName = "ExciseChapterWiseListOfItem";
                }else if(link.equalsIgnoreCase("ReqArea")){
                    reportName = "RequirementArea";
                }
            }

       
            if (link.equalsIgnoreCase("Report") && request.getParameter("filename") != null &&
                request.getParameter("filename").toString().length() > 0) {
                reportName = request.getParameter("filename");

                System.out.println("Inside global report ");
            }
            ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

            rs = ps.executeQuery();
            String path = null;
            while (rs.next()) {
                path = rs.getString(1);
                // System.out.println("Path is :" + rs.getString(1));
            }
            if (link.equalsIgnoreCase("Report") && request.getParameter("filename") != null) {
                path = path + "Portal/";
            } else {
                path = path + "MM/";
            }


            System.out.println("Path is " + path + reportName);

             JasperReport report = (JasperReport) JRLoader.loadObject(path + reportName + ".jasper");
          
            String Attid = null;
            String attId1 = null;
            String attId2 = null;
            String attId3 = null;
            String attId4 = null;
            String attId5 = null;
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
            String eoId = null;
            String chptrId = null;
            String plcyId = null;
            String reqAreaId = null;
            String  paramId = null;
            String serializedItem =  null;
            String QCReqItem = null;
            String sampleItem = null;
          
            
            cldId = String.valueOf(request.getParameter("CldId"));
            slocId = Integer.parseInt(request.getParameter("SlocId"));
            hoOrgId = String.valueOf(request.getParameter("HoOrgId"));
            System.out.println("before getting org while call any report");
            orgId = String.valueOf(request.getParameter("OrgId"));
            System.out.println("after get org--" + orgId);
            //Price Basic minimum
            // System.out.println("Price Basic min" + request.getParameter("PriceBasicMin"));
            // if (request.getParameter("PriceBasicMin")!=null || request.getParameter("PriceBasicMin").equals("")){
            // System.out.println("Price Basic min" + request.getParameter("PriceBasicMin"));
            
            if (request.getParameter("ReportType") != null) {
                       if (request.getParameter("ReportType").equals("")
                           ||request.getParameter("ReportType") == null) {
                           reporttype = "pdf";
                           System.out.println("inside reportType -"+reporttype);
                       } else {
                           reporttype = request.getParameter("ReportType");
                           System.out.println("inside reportType -"+reporttype);
                       }
             }
//Group id
            if(request.getParameter("GrpId")!=null){
                if (request.getParameter("GrpId").trim().equals("") || request.getParameter("GrpId") == null) {
                    grpId = null;
                } else {
                    grpId = String.valueOf(request.getParameter("GrpId").trim());
                }
            }
            //Item id
            if(request.getParameter("ItmId")!=null){
                if (request.getParameter("ItmId").equals("") || request.getParameter("ItmId") == null) {
                    itmId = null;
                } else {
                    itmId = String.valueOf(request.getParameter("ItmId"));
                }
            }
            System.out.println("link----" + link);
            if (link.equals("loi") || link.equals("grploi") || link.equals("barcode") ||
                link.equals("loai") || link.equals("loisp") || link.equals("aloi") || link.equals("Ploi") ||
                link.equals("Report")||link.equalsIgnoreCase("LOIChaptr")||link.equalsIgnoreCase("ExciseChapter")) {
                System.out.println("link----Inside..." + link);
                if (request.getParameter("AttId1") != null) {
                    if (request.getParameter("AttId1").trim().equals("") || request.getParameter("AttId1") == null) {
                        attId1 = null;
                    } else {
                        attId1 = String.valueOf(request.getParameter("AttId1").trim());
                        System.out.println("AttId1---" + attId1);
                    }
                }
                if (request.getParameter("AttId2") != null) {
                    if (request.getParameter("AttId2").trim().equals("") || request.getParameter("AttId2") == null) {
                        attId2 = null;
                    } else {
                        attId2 = String.valueOf(request.getParameter("AttId2").trim());
                        System.out.println("AttId2---" + attId2);
                    }
                }
                if (request.getParameter("AttId3") != null) {
                    if (request.getParameter("AttId3").trim().equals("") || request.getParameter("AttId3") == null) {
                        attId3 = null;
                    } else {
                        attId3 = String.valueOf(request.getParameter("AttId3").trim());
                        System.out.println("AttId3---" + attId3);
                    }
                }
                if (request.getParameter("AttId4") != null) {
                    if (request.getParameter("AttId4").trim().equals("") || request.getParameter("AttId4") == null) {
                        attId4 = null;
                    } else {
                        attId4 = String.valueOf(request.getParameter("AttId4").trim());
                        System.out.println("AttId1---" + attId4);
                    }
                }
                if (request.getParameter("AttId5") != null) {
                    if (request.getParameter("AttId5").trim().equals("") || request.getParameter("AttId5") == null) {
                        attId5 = null;
                    } else {
                        attId5 = String.valueOf(request.getParameter("AttId5").trim());
                        System.out.println("attId5---" + attId5);
                    }
                }
                if (request.getParameter("PriceBasicMin").equals("0.0") ||
                    request.getParameter("PriceBasicMin").equals("0") ||
                    request.getParameter("PriceBasicMin") == null || request.getParameter("PriceBasicMin").equals("")) {
                    System.out.println("Price Basic min" + request.getParameter("PriceBasicMin"));
                    pricebasicmin = null;
                } else {
                    System.out.println(request.getParameter("PriceBasicMin"));
                    Double y = Double.parseDouble(request.getParameter("PriceBasicMin"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    pricebasicmin = z;
                }
                // }
                // if (request.getParameter("PriceBasicMax")!=null || request.getParameter("PriceBasicMax").equals("")){
                //Price Basic Max
                if (request.getParameter("PriceBasicMax").equals("0.0") ||
                    request.getParameter("PriceBasicMax").equals("0") ||
                    request.getParameter("PriceBasicMax") == null || request.getParameter("PriceBasicMax").equals("")) {
                    pricebasicmax = null;
                } else {
                    Double y = Double.parseDouble(request.getParameter("PriceBasicMax"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    pricebasicmax = z;
                }

                //Price Purchase minimum
                // if (request.getParameter("PricePurMin")!=null || request.getParameter("PricePurMin").equals("")){
                if (request.getParameter("PricePurMin").equals("0.0") ||
                    request.getParameter("PricePurMin").equals("0") || request.getParameter("PricePurMin") == null ||
                    request.getParameter("PricePurMin").equals("")) {
                    pricepurmin = null;
                } else {
                    Double y = Double.parseDouble(request.getParameter("PricePurMin"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    pricepurmin = z;
                }

                //Price Purchase maximum
                //if (request.getParameter("PricePurMax")!=null || request.getParameter("PricePurMax").equals("")){
                if (request.getParameter("PricePurMax").equals("0.0") ||
                    request.getParameter("PricePurMax").equals("0") || request.getParameter("PricePurMax") == null ||
                    request.getParameter("PricePurMax").equals("")) {
                    pricepurmax = null;
                } else {

                    Double y = Double.parseDouble(request.getParameter("PricePurMax"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    pricepurmax = z;
                }

                //Price sales minimum

                if (request.getParameter("PriceSlsMin").equals("0.0") ||
                    request.getParameter("PriceSlsMin").equals("0") || request.getParameter("PriceSlsMin") == null ||
                    request.getParameter("PriceSlsMin").equals("")) {
                    priceslsmin = null;
                } else {
                    Double y = Double.parseDouble(request.getParameter("PriceSlsMin"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    priceslsmin = z;

                }


                //Price sales maximum

                if (request.getParameter("PriceSlsMax").equals("0.0") ||
                    request.getParameter("PriceSlsMax").equals("0") || request.getParameter("PriceSlsMax") == null ||
                    request.getParameter("PriceSlsMax").equals("")) {
                    priceslsmax = null;
                } else {
                    Double y = Double.parseDouble(request.getParameter("PriceSlsMax"));
                    BigDecimal z = new BigDecimal(y.doubleValue());
                    priceslsmax = z;
                }

                //Reorder Level
                // if (request.getParameter("ReorderLvl")!=null || request.getParameter("ReorderLvl").equals("")){
                if (request.getParameter("ReorderLvl").equals("D") || request.getParameter("ReorderLvl") == null) {
                    reorderlvl = null;
                } else {
                    reorderlvl = String.valueOf(request.getParameter("ReorderLvl"));
                }

                //Safety Level
                //if (request.getParameter("SafeQty")!=null || request.getParameter("SafeQty").equals("")){

                if (request.getParameter("SafeQty").equals("D") || request.getParameter("SafeQty") == null) {
                    safeqty = null;
                } else {
                    safeqty = String.valueOf(request.getParameter("SafeQty"));
                }

                //Min Stock
                //if (request.getParameter("MinStk")!=null || request.getParameter("MinStk").equals("")){

                if (request.getParameter("MinStk").equals("D") || request.getParameter("MinStk") == null) {
                    minstk = null;
                } else {
                    minstk = String.valueOf(request.getParameter("MinStk"));
                }

                //Max Stock
                //if (request.getParameter("MaxStk")!=null || request.getParameter("MaxStk").equals("")){

                if (request.getParameter("MaxStk").equals("D") || request.getParameter("MaxStk") == null) {
                    maxstk = null;
                } else {
                    maxstk = String.valueOf(request.getParameter("MaxStk"));
                }

                //UOMSales
                // if (request.getParameter("UomSls")!=null || request.getParameter("UomSls").equals("")){

                if (request.getParameter("UomSls").equals("") || request.getParameter("UomSls") == null) {
                    uomsls = null;
                } else {
                    uomsls = String.valueOf(request.getParameter("UomSls"));
                }

                //UOMPurchase
                //if (request.getParameter("UomPur")!=null || request.getParameter("UomPur").equals("")){

                if (request.getParameter("UomPur").equals("") || request.getParameter("UomPur") == null) {
                    uompur = null;
                } else {
                    uompur = String.valueOf(request.getParameter("UomPur"));
                }

                //UOMBasic
                //if (request.getParameter("UomBasic")!=null || request.getParameter("UomBasic").equals("")){
                if (request.getParameter("UomBasic").equals("") || request.getParameter("UomBasic") == null) {
                    uombasic = null;
                } else {
                    uombasic = String.valueOf(request.getParameter("UomBasic"));
                }

                //Active Items----------
                // if (request.getParameter("Actv")!=null || request.getParameter("Actv").equals("")){

                if (request.getParameter("Actv").equals("") || request.getParameter("Actv") == null) {
                    actv = null;
                } else {
                    actv = String.valueOf(request.getParameter("Actv"));
                }


                //TaxExmptItems----------
                // if (request.getParameter("TaxExmptFlg")!=null || request.getParameter("TaxExmptFlg").equals("")){

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
                if (request.getParameter("ConsumableFlg").equals("") || request.getParameter("ConsumableFlg") == null) {
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
           /*            if (request.getParameter("ReportType").equals("")||request.getParameter("ReportType")==null) {
                    System.out.println("report type is null found.-----------");
                    reporttype = "pdf";
                    System.out.println("report type is null found.-----------" + reporttype);
                }   else if (request.getParameter("ReportType") == null) {
                    System.out.println("1report type is null found.-----------");
                    reporttype = "pdf";
                    System.out.println("1report type is null found.-----------" + reporttype); 
                } else {
                    System.out.println("report type not null    ------- ");
                    reporttype = request.getParameter("ReportType");
                    System.out.println("report type not null    " + reporttype);
                }  */
    
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
            if (link.equals("low") || link.equals("lob") || link.equals("bc") || link.equals("Report")) {
                //Report Type----------
                /*    if (request.getParameter("ReportType").equals("")) {
                    reporttype = "pdf";
                } else {
                    reporttype = String.valueOf(request.getParameter("ReportType"));
                }
            */     //GrpId----------------
                if (request.getParameter("OrgId").trim().equals("") || request.getParameter("OrgId") == null ||
                    link.equalsIgnoreCase("gl23")) {
                    orgId = null;
                } else {
                    orgId = String.valueOf(request.getParameter("OrgId").trim());
                }
                //GrpId----------------
                if (request.getParameter("WhId").trim().equals("") || request.getParameter("WhId") == null ||
                    link.equalsIgnoreCase("gl23")) {
                    whId = null;
                } else {
                    whId = String.valueOf(request.getParameter("WhId").trim());
                }
            }

            if (link.equals("lok") || link.equals("Report")) {
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

            if (link.equals("igc") || link.equals("loig") || link.equals("Report")) {
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
            if (link.equals("los") || link.equals("Report")) {
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

            if (link.equals("perf") || link.equals("Report")) {
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

            if (link.equals("lou") || link.equals("louc") || link.equals("Report")) {
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
            if (link.equals("mm")) {

                if (request.getParameter("ReportType").equals("")) {
                    reporttype = "pdf";
                } else {
                    reporttype = String.valueOf(request.getParameter("ReportType"));
                }
            }
            /*             if(link.equalsIgnoreCase("SuppEval")){
                if (request.getParameter("ReportType").equals("")) {
                    reporttype = "pdf";
                    System.out.println("reprotyep suppp"+reporttype);
                } else {
                    reporttype = String.valueOf(request.getParameter("ReportType"));
                    System.out.println("reprotyep suppp"+reporttype);
                } 
            }
            if(link.equalsIgnoreCase("stp")){
                if (request.getParameter("ReportType").equals("")) {
                    reporttype = "pdf";
                } else {
                    reporttype = String.valueOf(request.getParameter("ReportType"));
                } 
            } */
            /**
                             * Header Color
                             */
            if (request.getParameter("hdcolor") != null) {
                if (request.getParameter("hdcolor").equals("")) {
                    hdcolor = "D";
                } else {
                    hdcolor = request.getParameter("hdcolor");
                }
            }

            /**
                             * BgColor
                             */
            System.out.println("--->" + request.getParameter("bgcolor"));
            if (request.getParameter("bgcolor") != null) {
                if (request.getParameter("bgcolor").equals("")) {
                    bgcolor = "D";
                } else {
                    bgcolor = request.getParameter("bgcolor");
                }
            }

            /**MARGIN DETAILS. **/
            if (request.getParameter("Margin_Detail") != null) {
                if (request.getParameter("Margin_Detail").equals("")) {
                    margin = null;
                    System.out.println("equal    ... " + margin);
                } else {
                    margin = request.getParameter("margin");
                    System.out.println("margin.." + margin);
                    if (request.getParameter("Margin_Detail").equalsIgnoreCase("true")) {
                        margin = "Y";
                        System.out.println("margin..Y" + margin);
                    } else if (request.getParameter("Margin_Detail").equalsIgnoreCase("false")) {
                        margin = "N";
                        System.out.println("magin...N" + margin);
                    }
                }
            }
//EoId
            if (request.getParameter("EoId") != null) {
                if (request.getParameter("EoId") == null || request.getParameter("EoId").equals("")) {
                    eoId = null;
                } else
                    eoId = request.getParameter("EoId");
            }
            //Policy Id
            if(request.getParameter("PlcyId")!=null){
                if(request.getParameter("PlcyId")==null||request.getParameter("PlcyId").equals("")){
                    plcyId = null;
                }else plcyId = request.getParameter("PlcyId");
            }
            //Chapter Id
            if(request.getParameter("ChptrId")!=null){
                if(request.getParameter("ChptrId")==null||request.getParameter("ChptrId").equals("")){
                    chptrId = null;
                }else chptrId = request.getParameter("ChptrId").toString();
            }
           //Requirement Area id 
            if(request.getParameter("ReqAreaId")!=null){
                if(request.getParameter("ReqAreaId")==null||request.getParameter("ReqAreaId").equals("")) {
                    reqAreaId = null;
                }else{ 
                    reqAreaId = request.getParameter("ReqAreaId").toString();
                    System.out.println("reqAreaId "+reqAreaId);
                }
            }
//QC param id
            if(request.getParameter("ParamId")!=null){
               if(request.getParameter("ParamId")==null||request.getParameter("ParamId").equals(""))
               {
                paramId = null;
            }else     paramId = request.getParameter("ParamId");
            }
            
             
            //QC Required.
            if(request.getParameter("QCReq")!=null){
               if(request.getParameter("QCReq")==null||request.getParameter("QCReq").equals(""))
               {
                QCReqItem = null;
               }  else  
                   QCReqItem = request.getParameter("QCReq");
            }
            
            //sample
            if(request.getParameter("SampleItem")!=null){
               if(request.getParameter("SampleItem")==null||request.getParameter("SampleItem").equals(""))
               {
                sampleItem = null;
               }  else  
                   sampleItem = request.getParameter("SampleItem");
            }
            //Serialized 
            if(request.getParameter("SerializedItem")!=null){
               if(request.getParameter("SerializedItem")==null||request.getParameter("SerializedItem").equals(""))
               {
                serializedItem = null;
               }  else  
                   serializedItem = request.getParameter("SerializedItem");
            }
            
            
            
            Map parameters = new HashMap();
            parameters.put("Path", path);
            parameters.put("CldId", cldId);
            parameters.put("HoOrgId", hoOrgId);
            parameters.put("OrgId", orgId);
            parameters.put("SlocId", slocId);
            parameters.put("ChptrId",chptrId);
            parameters.put("PlcyId",plcyId);
            parameters.put("RqmtAreaId",reqAreaId);
            parameters.put("GrpId",grpId);
            parameters.put("ItmId",itmId);
            parameters.put("EoId",eoId);
            parameters.put("ParamId",paramId);
            
            parameters.put("SampleItm",sampleItem);
            parameters.put("QCReqItm",QCReqItem);
            parameters.put("SerializedItm",serializedItem);
      
           
            if (reporttype.equalsIgnoreCase("XLS")) 
            {
              parameters.put("ReportType", "E");
            } else{
              parameters.put("ReportType", "P");
             }
            System.out.println("after HashMap"+chptrId+".."+plcyId+" reqArea "+reqAreaId);

            if (link.equals("loi") || link.equals("grploi") || link.equals("supploi") || link.equals("barcode") ||
                link.equals("loai") || link.equals("loisp") || link.equals("aloi") || link.equals("Ploi")
                ||link.equalsIgnoreCase("LOIChaptr")||link.equalsIgnoreCase("ExciseChapter")) {
                parameters.put("CldId", cldId);
                parameters.put("HoOrgId", hoOrgId);
                parameters.put("OrgId", orgId);
                parameters.put("SlocId", slocId);
                System.out.println("ATTID put" + Attid);
                parameters.put("AttId1", attId1);
                parameters.put("AttId2", attId2);
                parameters.put("AttId3", attId3);
                parameters.put("AttId4", attId4);
                parameters.put("AttId5", attId5);
                parameters.put("Show_Tech_Name", show_Tech_Name);
                parameters.put("ItmId", request.getParameter("ItmId").toString());
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
                parameters.put("Attid", Attid);
                parameters.put("AttId1", attId1);
                parameters.put("AttId2", attId2);
                parameters.put("AttId3", attId3);
                parameters.put("AttId4", attId4);
                parameters.put("AttId5", attId5);

                parameters.put("Path", path);
            }
            if (link.equals("low") || link.equals("lob") || link.equals("bc")) {
                parameters.put("Path", path);
                parameters.put("WhId", whId);
                parameters.put("OrgId", orgId);
            }
            if (link.equals("lok")) {
                parameters.put("ItmId", itmId);
                parameters.put("Path", path);
            }
            if (link.equals("igc") || link.equals("loig")) {
                parameters.put("GrpId", grpId);
                parameters.put("Path", path);
                System.out.println(path);
            }
            if (link.equals("los")) {
                parameters.put("BlackListed", blackListed);
                parameters.put("OnHold", onHold);
                parameters.put("Org_Id", orgId);
                parameters.put("Path", path);
            }
            if (link.equals("perf")) {
                parameters.put("ParamSetId", paramSetId);
                parameters.put("Path", path);
                System.out.println(path);
            }
            if (link.equals("lou") || link.equals("louc")) {
                parameters.put("UomClassId", uomClassId);
                parameters.put("Path", path);
                System.out.println(path);
            }
            if (link.equals("mm")) {
                System.out.println("--" + cldId + "----" + orgId + "------" + slocId);
                parameters.put("CldId", cldId);
                parameters.put("OrgId", orgId);
                parameters.put("SlocId", slocId);
                parameters.put("Path", path);
                System.out.println(path);
            }
          
            if (link.equalsIgnoreCase("SuppEval")) {
                parameters.put("HoOrgId", hoOrgId);
                parameters.put("OrgId", orgId);
                String slocId1 = String.valueOf(request.getParameter("SlocId"));
                parameters.put("slocid", slocId1);
                parameters.put("cldid", cldId);
                parameters.put("suppid", eoId);
                parameters.put("Path", path);
                System.out.println(hoOrgId+orgId+slocId+cldId+eoId+reporttype);
            }
            System.out.println("before report params");
//Portal Report Parameters
            if (link.equalsIgnoreCase("Report")) {
                System.out.println("inside report parasm");
                parameters.put("CldId", cldId);
                parameters.put("cldid", cldId);
                parameters.put("HoOrgId", hoOrgId);
                parameters.put("OrgId", orgId);
                parameters.put("Org_Id", orgId);
                parameters.put("SlocId", slocId);
                parameters.put("slocid", slocId);
                parameters.put("suppid", eoId);
                parameters.put("EoId", eoId);
                parameters.put("Show_Tech_Name", show_Tech_Name);
                parameters.put("ItmId", request.getParameter("ItmId").toString());
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
                parameters.put("AttId1", attId1);
                parameters.put("AttId2", attId2);
                parameters.put("AttId3", attId3);
                parameters.put("AttId4", attId4);
                parameters.put("AttId5", attId5);
                parameters.put("Attid", Attid);
                parameters.put("WhId", whId);
                parameters.put("BlackListed", blackListed);
                parameters.put("OnHold", onHold);
                parameters.put("ParamSetId", paramSetId);
                parameters.put("UomClassId", uomClassId);
                parameters.put("Path", path);
                parameters.put("Attid", Attid);
                parameters.put("ParamId",paramId);
                //  parameters.put("filename",reportName);
                parameters.put("BgColor", bgcolor);
                parameters.put("Head", hdcolor);
                parameters.put("Margin_Detail", margin);
                System.out.println("ItmId.." + request.getParameter("ItmId").toString() + "margin" + margin +
                                   "bgcolor" + bgcolor + "hdColor" + hdcolor);
            }
            // System.out.println("ItmId.."+ request.getParameter("ItmId").toString()+"margin"+margin+"bgcolor"+bgcolor+"hdColor"+hdcolor);
            System.out.println("Report Parameters are :"+parameters);
            System.out.println(reporttype+"before jasperfill manager");
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();
            JRExporter exporter = null;
            if (reporttype.equalsIgnoreCase("pdf")) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".pdf\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if (reporttype.equalsIgnoreCase("rtf")) {
                response.setContentType("application/rtf");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".rtf\"");
                exporter = new JRRtfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);

            } else if (reporttype.equalsIgnoreCase("html")) {
                exporter = new JRXhtmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                exporter.exportReport();

            } else if (reporttype.equalsIgnoreCase("xls")) {
                response.setContentType("application/csv");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".csv\"");
                exporter = new JRCsvExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if (reporttype.equalsIgnoreCase("docx")) {
                response.setContentType("application/docx");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".docx\"");
                exporter = new JRDocxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if (reporttype.equalsIgnoreCase("xlsx")) {
                response.setContentType("application/xlsx");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".xlsx\"");
                exporter = new JRXlsxExporter();
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if (reporttype.equalsIgnoreCase("xml")) {
                response.setContentType("application/xml");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".xml\"");
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
