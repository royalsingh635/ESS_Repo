package salesreportconfigurationapp.view.servlet;

import java.io.IOException;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.List;
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
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;


@SuppressWarnings("oracle.jdeveloper.java.serialversionuid-field-missing")
public class SalesReportServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);


        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            String reportName = null; // Used to get Report Name conditionally
            String tab = request.getParameter("tab").toString(); // To know which tab is expanded
            String linkType =
                request.getParameter("golink").toString(); // To know which type of report user want to print
                String Lblid=null;
                String Lblval=null;
               
            // System.out.println("++++" + tab);
            //System.out.println("-----" + linkType);

            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/SLSDS");
            conn = ds.getConnection();


            /**
        * To get Exact report name which is to be opened
        */
            if (tab.equalsIgnoreCase("Opp")) {
                if (linkType.equalsIgnoreCase("custDetail")) {
                    reportName = "Sls_Opp_CustomerWise_Detail";
                } else if (linkType.equalsIgnoreCase("custSumm")) {
                    reportName = "Sls_Opp_CustomerWise_Summary";
                } else if (linkType.equalsIgnoreCase("salesDetail")) {
                    reportName = "Sls_Opp_SalesExecWise_Detail";
                } else if (linkType.equalsIgnoreCase("salesSumm")) {
                    reportName = "Sls_Opp_SalesExeWise_Summary";
                } else if (linkType.equalsIgnoreCase("productGrpDetail")) {
                    reportName = "Sls_Opp_GroupWise_Detail";
                } else if (linkType.equalsIgnoreCase("productGrpSumm")) {
                    reportName = "Sls_Opp_GroupWise_Summary";
                } else if (linkType.equalsIgnoreCase("itmDetail")) {
                    reportName = "Sls_Opp_ItemWise_Detail";
                } else if (linkType.equalsIgnoreCase("itmSumm")) {
                    reportName = "Sls_Opp_ItemWise_Summary";
                } else if (linkType.equalsIgnoreCase("otherOpp")) {
                    reportName = "Sls_Opp_Register";
                } else if (linkType.equalsIgnoreCase("custPrice")) {
                    reportName = "Sls_Price_Setup";
                }
            } else if (tab.equalsIgnoreCase("Quo")) {
                if (linkType.equalsIgnoreCase("custDetail")) {
                    reportName = "Sls_Quot_CustomerWise_Detail";
                } else if (linkType.equalsIgnoreCase("custSumm")) {
                    reportName = "Sls_Quot_CustomerWise_Summary";
                } else if (linkType.equalsIgnoreCase("salesDetail")) {
                    reportName = "Sls_Quot_SalesExecWise_Detail";
                } else if (linkType.equalsIgnoreCase("salesSumm")) {
                    reportName = "Sls_Quot_SaleExecWise_Summary";
                } else if (linkType.equalsIgnoreCase("productGrpDetail")) {
                    reportName = "Sls_Quot_PrdouctGroupWise_Detail";
                } else if (linkType.equalsIgnoreCase("productGrpSumm")) {
                    reportName = "Sls_Quot_ProductGroupWise_Summary";
                } else if (linkType.equalsIgnoreCase("itmDetail")) {
                    reportName = "Sls_Quot_ItemWise_Detail";
                } else if (linkType.equalsIgnoreCase("itmSumm")) {
                    reportName = "Sls_Quot_ItemWise_Summary";
                } else if (linkType.equalsIgnoreCase("otherReg")) {
                    reportName = "Sls_Quot_Register";
                }
            } else if (tab.equalsIgnoreCase("SO")) {
                if (linkType.equalsIgnoreCase("custDetail")) {
                    reportName = "Sls_SO_CustomerWise_Detail";
                } else if (linkType.equalsIgnoreCase("custSumm")) {
                    reportName = "Sls_SO_CustomerWise_Summary";
                } else if (linkType.equalsIgnoreCase("salesDetail")) {
                    reportName = "Sls_SO_SalesExecWise_Detail";
                } else if (linkType.equalsIgnoreCase("salesSumm")) {
                    reportName = "Sls_SO_SalesExecWise_Summary";
                } else if (linkType.equalsIgnoreCase("productGrpDetail")) {
                    reportName = "Sls_SO_ProductGroupWise_Detail";
                } else if (linkType.equalsIgnoreCase("productGrpSumm")) {
                    reportName = "Sls_SO_ProductGroupWise_Summary";
                } else if (linkType.equalsIgnoreCase("itmDetail")) {
                    reportName = "Sls_SO_ItemWise_Detail";
                } else if (linkType.equalsIgnoreCase("itmSumm")) {
                    reportName = "Sls_SO_ItemWise_Summary";
                } else if (linkType.equalsIgnoreCase("otherReg")) {
                    reportName = "Sls_So_Register";
                } else if (linkType.equalsIgnoreCase("OrderTrack")) {
                    reportName = "Sales_Order_Tracking";
                } else if (linkType.equalsIgnoreCase("OrderPending")) {
                    reportName = "Sls_Pending_Order_Summary";
                } else if (linkType.equalsIgnoreCase("OrderAmd")) {
                    reportName = "SLS_SO_Amend_Summary";
                }
                
            }else if (tab.equalsIgnoreCase("DS")) {
                if (linkType.equalsIgnoreCase("custDetail")) {
                    reportName = "Sls_SO_Delv_Schedule_CustDetail";
                } else if (linkType.equalsIgnoreCase("custSumm")) {
                    reportName = "Sls_SO_Delv_Schedule_CustSumm";
                } else if (linkType.equalsIgnoreCase("whDet")) {
                    reportName = "Sls_SO_Delv_Schedule_WarehouseDetail";
                } else if (linkType.equalsIgnoreCase("whSumm")) {
                    reportName = "Sls_SO_Delv_Schedule_WarehouseSumm";
                } else if (linkType.equalsIgnoreCase("productGrpDetail")) {
                    reportName = "Sls_SO_Delv_Schedule_ProductGroupDet";
                } else if (linkType.equalsIgnoreCase("productGrpSumm")) {
                    reportName = "Sls_SO_Delv_Schedule_ProductGroupSumm";
                } else if (linkType.equalsIgnoreCase("itmDetail")) {
                    reportName = "Sls_SO_Delv_Schedule_ItmDetail";
                } else if (linkType.equalsIgnoreCase("itmSumm")) {
                    reportName = "Sls_SO_Delv_Schedule_ItemSumm";
                } 
                
            } else if (tab.equalsIgnoreCase("PL")) {
                if (linkType.equalsIgnoreCase("custDetail")) {
                    reportName = "SLS_PickList_CustomerWise_Detail";
                } else if (linkType.equalsIgnoreCase("custSumm")) {
                    reportName = "SLS_PickList_CustomerWise_Summary";
                } else if (linkType.equalsIgnoreCase("salesDetail")) {
                    reportName = ""; // Later add the report name
                } else if (linkType.equalsIgnoreCase("salesSumm")) {
                    reportName = ""; // Later add the report name
                } else if (linkType.equalsIgnoreCase("productGrpDetail")) {
                    reportName = "SLS_PickList_ProductGroupWise_Detail";
                } else if (linkType.equalsIgnoreCase("productGrpSumm")) {
                    reportName = "SLS_PickList_ProductGroupWise_Summary";
                } else if (linkType.equalsIgnoreCase("itmDetail")) {
                    reportName = "SLS_PickList_ProductWise_Detail";
                } else if (linkType.equalsIgnoreCase("itmSumm")) {
                    reportName = "SLS_PickList_ProductWise_Summary";
                } else if (linkType.equalsIgnoreCase("othersReg")) {
                    reportName = "SLS_PickList_Summary";
                } else if (linkType.equalsIgnoreCase("status")) {
                    reportName = "SLS_PickList_Register";
                } else if (linkType.equalsIgnoreCase("pickbar")){
                    reportName = "SLS_PickList_Detail";
                }
            } else if (tab.equalsIgnoreCase("PK")) {
                if (linkType.equalsIgnoreCase("custDetail")) {
                    reportName = "SLS_Pack_CustomerWise_Detail";
                } else if (linkType.equalsIgnoreCase("custSumm")) {
                    reportName = "SLS_Pack_CustomerWise_Summary";
                } else if (linkType.equalsIgnoreCase("salesDetail")) {
                    reportName = ""; // Later add the report name
                } else if (linkType.equalsIgnoreCase("salesSumm")) {
                    reportName = ""; // Later add the report name
                } else if (linkType.equalsIgnoreCase("productGrpDetail")) {
                    reportName = "SLS_Pack_ProductGroupWise_Detail";
                } else if (linkType.equalsIgnoreCase("productGrpSumm")) {
                    reportName = "SLS_Pack_ProductGroupWise_Summary";
                } else if (linkType.equalsIgnoreCase("itmDetail")) {
                    reportName = "SLS_Pack_ProductWise_Detail";
                } else if (linkType.equalsIgnoreCase("itmSumm")) {
                    reportName = "SLS_Pack_ProductWise_Summary";
                } else if (linkType.equalsIgnoreCase("status")) {
                    reportName = "SLS_Pack_Summary";
                } else if (linkType.equalsIgnoreCase("othersReg")) {
                    reportName = "SLS_Pack_Register";
                }
            } else if (tab.equalsIgnoreCase("Ship")) {
                if (linkType.equalsIgnoreCase("custDetail")) {
                    reportName = "Sls_Ship_CustWise_Detail";
                } else if (linkType.equalsIgnoreCase("custSumm")) {
                    reportName = "Sls_Ship_CustWise_Summary";
                } else if (linkType.equalsIgnoreCase("productGrpDetail")) {
                    reportName = "Sls_Ship_PrdGrpWise_Detail";
                } else if (linkType.equalsIgnoreCase("productGrpSumm")) {
                    reportName = "Sls_Ship_PrdGrpWise_Summary";
                } else if (linkType.equalsIgnoreCase("itmDetail")) {
                    reportName = "Sls_Ship_ItmWise_Detail";
                } else if (linkType.equalsIgnoreCase("itmSumm")) {
                    reportName = "Sls_Ship_ItmWise_Summary";
                } else if (linkType.equalsIgnoreCase("othersReg")) {
                    reportName = "Sls_Shipt_DetailRegister";
                } else if (linkType.equalsIgnoreCase("status")) {
                    reportName = "Sls_Ship_Status_Detail";
                } else if (linkType.equalsIgnoreCase("intmreg")) {
                    reportName = "Sls_Intimation_Register";
                } else if (linkType.equalsIgnoreCase("install")) {
                    reportName = "SLS_Ship_installation";
                }
            } else if (tab.equalsIgnoreCase("Inv")) {
                if (linkType.equalsIgnoreCase("custDetail")) {
                    reportName = "Sls_Inv_CustWise_Detail";
                } else if (linkType.equalsIgnoreCase("custSumm")) {
                    reportName = "Sls_Inv_CustWise_Summary";
                } else if (linkType.equalsIgnoreCase("salesDetail")) {
                    reportName = "Sls_Inv_SaleExec_Wise_Detail";
                } else if (linkType.equalsIgnoreCase("salesSumm")) {
                    reportName = "Sls_Inv_SaleExec_Wise_Summary";
                } else if (linkType.equalsIgnoreCase("productGrpDetail")) {
                    reportName = "Sls_Inv_PrdGrpWise_Detail";
                } else if (linkType.equalsIgnoreCase("productGrpSumm")) {
                    reportName = "Sls_Inv_PrdGrpWise_Summary";
                } else if (linkType.equalsIgnoreCase("itmDetail")) {
                    reportName = "Sls_Inv_ItmWise_Detail";
                } else if (linkType.equalsIgnoreCase("itmSumm")) {
                    reportName = "Sls_Inv_ItmWise_Summary";
                } else if (linkType.equalsIgnoreCase("invreg")) {
                    if(request.getParameter("ReportType").equalsIgnoreCase("pdf")){
                    reportName = "Sls_Inv_Register";
                    }else{
                        reportName = "Sls_Inv_Register_E";
                    }
                }else if (linkType.equalsIgnoreCase("invregsumm")) {
                    reportName = "Sls_Inv_Register_Summary";
                } else if (linkType.equalsIgnoreCase("suppreg")) {
                    reportName = "Sls_Inv_Supplementary_Register";
                }else if (linkType.equalsIgnoreCase("custgrp")) {
                    reportName = "Sls_Inv_CustWiseGroup";
                }else if (linkType.equalsIgnoreCase("invvehsumm")) {
                    reportName = "Sls_Inv_VehicleSummary";
                }else if (linkType.equalsIgnoreCase("tax")) {
                    reportName = "Sls_TaxForm";
                }
            } else if (tab.equalsIgnoreCase("Rma")) {
                if (linkType.equalsIgnoreCase("custDetail")) {
                    reportName = "Sls_RMA_CustomerWise_Detail";
                } else if (linkType.equalsIgnoreCase("custSumm")) {
                    reportName = "Sls_RMA_CustomerWiseSummary";
                } else if (linkType.equalsIgnoreCase("productGrpDetail")) {
                    reportName = "Sls_RMA_ProductGroupWise_Detail";
                } else if (linkType.equalsIgnoreCase("productGrpSumm")) {
                    reportName = "Sls_RMA_ProductGroupWise_Summary";
                } else if (linkType.equalsIgnoreCase("itmDetail")) {
                    reportName = "Sls_RMA_Itm_Wise_Detail";
                } else if (linkType.equalsIgnoreCase("itmSumm")) {
                    reportName = "Sls_RMA_ItmWise_Summary";
                } else if (linkType.equalsIgnoreCase("othersReg")) {
                    reportName = "Sls_RMA_Detail_Register";
                } else if (linkType.equalsIgnoreCase("status")) {
                    reportName = "Sls_RMA_Detail";
                }
            } else if (tab.equalsIgnoreCase("Prft")) {
                if (linkType.equalsIgnoreCase("custDetail")) {
                    reportName = "Sls_prft_Cust_Wise_Detail";
                } else if (linkType.equalsIgnoreCase("custSumm")) {
                    reportName = "Sls_Prft_Cust_Wise_Summary";
                } else if (linkType.equalsIgnoreCase("salesDetail")) {
                    reportName = "Sls_Prft_SalesEexec_Wise_Detail";
                } else if (linkType.equalsIgnoreCase("salesSumm")) {
                    reportName = "Sls_Prft_SalesExec_Wise_Summary";
                } else if (linkType.equalsIgnoreCase("productGrpDetail")) {
                    reportName = "Sls_Prft_Product_Group_Wise_Detail";
                } else if (linkType.equalsIgnoreCase("productGrpSumm")) {
                    reportName = "Sls_Prft_Product_Group_Wise_Summary";
                } else if (linkType.equalsIgnoreCase("itmDetail")) {
                    reportName = "Sls_Prft_Itm_Wise_Detail";
                } else if (linkType.equalsIgnoreCase("itmSumm")) {
                    reportName = "Sls_prft_Itm_Wise_Summary";
                }
            }
                else if (tab.equalsIgnoreCase("Analysis")) {
                                if (linkType.equalsIgnoreCase("custwise")) {
                                    reportName = "SLS_Analysis_Cust_Wise_Report";
                                } else if (linkType.equalsIgnoreCase("itemwise")) {
                                    reportName = "SLS_Analysis_Itm_Wise_Report";
                                } else if (linkType.equalsIgnoreCase("grpwise")) {
                                    reportName = "SLS_Analysis_Grp_Wise_Report";
                                } else if (linkType.equalsIgnoreCase("salesman")){
                                    reportName = "SLS_SLSMan_Analysis_Report";
                                }
            }
            else if (tab.equalsIgnoreCase("DC")) {
                if (linkType.equalsIgnoreCase("DCLink")) {
                    reportName = "SLS_DC_Register";
                } 
                    if (linkType.equalsIgnoreCase("DCProductiveLink")) {
                        reportName = "Sls_Productive_Salon_Report";
                    } 
                }
            if (linkType.equalsIgnoreCase("Report") && request.getParameter("fileName") != null &&
                request.getParameter("fileName").toString().length() > 0) {
                reportName = request.getParameter("fileName");

                System.out.println("Inside global report ");
            }

            ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");
        
            rs = ps.executeQuery();
            String path = null;
            while (rs.next()) {
                path = rs.getString(1);
                // System.out.println("Path is :" + rs.getString(1));
            }
            
            if (linkType.equalsIgnoreCase("Report") && request.getParameter("fileName") != null) {
               path = path + "Portal/";
               if(reportName.endsWith(")")){
                   reportName = reportName.substring(0,reportName.indexOf("("));
                   System.out.println("portal Report Nme is " + reportName);
               }
            } else {
               path = path + "SLS/";
            }
            System.out.println("Path is " + path + reportName);
           // path="D:/SVN_REPORT/Report/SLS/";
            //InputStream input = new FileInputStream(new File("D:/Report/" + reportName + ".jrxml"));
            //InputStream input = new FileInputStream(new File(path  + reportName + ".jrxml"));
            JasperReport report = (JasperReport)JRLoader.loadObject(path + reportName + ".jasper");
           // JasperDesign design = JRXmlLoader.load(input);
            //JasperReport report = JasperCompileManager.compileReport(design);
            

            String reporttype;

            String Org = request.getParameter("organisation");
            String cldid = request.getParameter("cldid");
            String hoOrgid = request.getParameter("hoorgid");
            Integer slocid = Integer.parseInt(request.getParameter("slocid"));
            Integer CatId;
            String bgcolor = null;
            String head = null;
            String margin = null;
           // String assigned;
            String status;
            String whId;
            String empty="false";
            String docno="";
            String eoid;
            String salesman;
            String itmgrp;
            String itmName;
            String Todate;
            String Fromdate;
            Integer currid;
            Integer FormStatus;
            Integer Stage=null;
            String todocno="";
            List<String> attribute=null;
            
            /* ArrayList<String> attribut=null; */
            
        //    String tmparid;  // for entry of template id temparay 25-March-2014

            //currid
            if (request.getParameter("currid").equals("")) {
                currid = null;
            } else {
                /*  Double y = Double.parseDouble(request.getParameter("currid"));
                BigDecimal z = new BigDecimal(y.doubleValue()); */
                currid = Integer.parseInt(request.getParameter("currid").toString());
            }
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
            System.out.println("--->" + request.getParameter("bgcolor"));
            if (request.getParameter("bgcolor") != null) {
                if (request.getParameter("bgcolor").equals("")) {
                    bgcolor = "D";
                } else {
                    bgcolor = request.getParameter("bgcolor");
                }
            }
            
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
            //Fromdate
            if (request.getParameter("fromdate").equals("")) {
                Fromdate = null;
            } else {
                Fromdate = request.getParameter("fromdate").toString();

            }


            //Todate
            if (request.getParameter("todate").equals("")) {
                Todate = null;
            } else {
                Todate = request.getParameter("todate").toString();
            }


            //CatId
            if (request.getParameter("catid").equals("")) {
                CatId = null;
            } else {
                System.out.println("Category ID is-->"+request.getParameter("catid"));
                CatId = Integer.parseInt(request.getParameter("catid").toString());
                
            }
            //Form status
            if(request.getParameter("stage")!=null){
            if (request.getParameter("stage").equals("")) {
                Stage = null;
            } else {
                System.out.println("status is --->"+request.getParameter("stage"));
                Stage = Integer.parseInt(request.getParameter("stage").toString());
                    
            }
                System.out.println("stage "+Stage);
            }
            
                if ((linkType.equalsIgnoreCase("DCProductiveLink")) || (linkType.equalsIgnoreCase("DCLink"))) {
                if (request.getParameter("lblid").equals("")) {
                    Lblid = null;
                } else {
                    System.out.println("label is --->"+request.getParameter("lblid"));
                    Lblid = request.getParameter("lblid").toString();
                        
                }
                }
           
               
            
       
            
       /*      //assigned
            if (request.getParameter("assign").equals("")) {
                assigned = null;
            } else {
                assigned = request.getParameter("assign");
            } */


            //status
          /*   if (request.getParameter("status").equals("")) {
                status = null;
            } else {
                status = request.getParameter("status").toString();
            }  */


            //oppno
            if(request.getParameter("docno")!=null){
            if (request.getParameter("docno").equals("")) {
                docno = null;
            } else {
                System.out.println("Doc ID Is -->"+request.getParameter("docno"));
                docno = request.getParameter("docno") ;
            }
            }
            if(request.getParameter("todocno")!=null){
            if (request.getParameter("todocno").equals("")) {
                todocno = null;
            } else {
                System.out.println("To Doc ID Is -->"+request.getParameter("todocno"));
                todocno = request.getParameter("todocno") ;
            }
            }

            //eoid
            if (request.getParameter("eoid").equals("")) {
                eoid = null;
            } else {
                eoid =  request.getParameter("eoid").toString()  ;
            }
System.out.println(request.getParameter("salesman").toString().length()+"salesman is-->"+request.getParameter("salesman").toString());
            //salesman
            if (request.getParameter("salesman").equals("")) {
                
                salesman = null;
            } else {
                salesman = request.getParameter("salesman").toString();
            }

            //itmgrp
            if (request.getParameter("itmgrp").equals("")) {
                itmgrp = null;
            } else {
                System.out.println("request.getParameter(\"itmgrp\").toString()"+request.getParameter("itmgrp").toString());
                itmgrp = request.getParameter("itmgrp").toString();
            }


            //itmName
            if (request.getParameter("itmName").equals("")) {
                itmName = null;
            } else {
                itmName = request.getParameter("itmName");
            }

            if(request.getParameter("whid")==null || request.getParameter("whid").equals("")){
                whId=null;
            } else{
                whId=request.getParameter("whid");
            }
            //Report Type
            if (request.getParameter("ReportType").equals("")) {
                reporttype = "pdf";
            } else {
                reporttype = request.getParameter("ReportType");
            }
              if(request.getParameter("attribute")!=null)
            {
                String att=request.getParameter("attribute").substring(1,request.getParameter("attribute").length()-1);
                //attribute.add(att);
            
            }
            if (request.getParameter("lblval") != null) {
                if (request.getParameter("lblval").equals("")) {
                    Lblval = null;
                } else {
                    Lblval = request.getParameter("lblval").toString();
                }
            }
            
            /*     
            if(request.getParameter("att").equals("")){
                attribut=null;
            } else {
               // attribut.add(request.getParameter("att").substring(1,request.getParameter("att").length()-1) );
               attribut = new ArrayList(Arrays.asList(request.getParameter("att").toString().substring(1, request.getParameter("att").toString().length()-1).split(",")));
                System.out.println("att in servlet"+attribut);
            }
            
            
System.out.println("att"+attribut); */
            //System.out.println(Todate + " --- " + Fromdate);
              System.out.println("Report Type " + request.getParameter("ReportType"));
            System.out.println("Org " + Org + " Sloc " + slocid + " cld " + cldid + " hoorg " + hoOrgid + " catid " +
                               CatId + " todate " + Todate + " from " + Fromdate + " itm name " + itmName +
                               " itmgrp " + itmgrp + " eoid " + eoid + " sales " +
                               salesman + " curr id "+currid + " lblid " + Lblid+ " lblval "+Lblval); 
            System.out.println("attribute in servlet are");
            System.out.println(attribute);
            
            // for passing the value of template para id 25- march -2014
            
        /*     if (request.getParameter("tmparid").equals("")) {
                tmparid = null;
                System.out.println("Tempalte Id is not Coming");
            } else {
                
                tmparid = request.getParameter("tmparid");
                System.out.println("Tempalte Id is  Coming"+  tmparid );
            } */
            
            Map parameters = new HashMap();

            parameters.put("OrgId", Org);
            parameters.put("SlocId", slocid);
            parameters.put("CldId", cldid);
            parameters.put("HoOrgId", hoOrgid);
            parameters.put("EoCatId", CatId);
            parameters.put("FromDate", Fromdate);
            parameters.put("ToDate", Todate);
            parameters.put("ItmId", itmName);
            parameters.put("GrpId", itmgrp);
            parameters.put("EoId", eoid);
            parameters.put("DocId", docno);
            parameters.put("ToDocId",todocno);
        //   parameters.put("Status", status);
         parameters.put("AssignTo",salesman ); //DocId
       // parameters.put("AssignTo", assigned);
            parameters.put("CurrId", currid);
            parameters.put("Path", path);
            parameters.put("StageId",Stage);
            if (linkType.equalsIgnoreCase("DCProductiveLink") || linkType.equalsIgnoreCase("DCLink")) {
            parameters.put("LblId",Lblid);
          
            }
            parameters.put("BgColor", bgcolor);
            parameters.put("Head", head);
            parameters.put("Margin_Detail", margin);
            if(reporttype.equalsIgnoreCase("xls")){
                
                    parameters.put("ReportType","E");
                }
            else{
                    parameters.put("ReportType","P");
                }
            parameters.put("Wh_Id",whId);
            System.out.println("warehouse id is" + whId);
            parameters.put("Att",attribute);
            parameters.put("LblVal", Lblval);
            //parameters.put("AttEmp",empty);
          //  parameters.put("AttCol",attribut);
      //      parameters.put("tmparid", tmparid);
            //System.out.println(empty);
            System.out.println("parameters are-"+parameters);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);


            OutputStream ouputStream = response.getOutputStream();


            JRExporter exporter = null;


            if ("pdf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/pdf");

                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".pdf\"");


                exporter = new JRPdfExporter();
                exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("rtf".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/rtf");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".rtf\"");

                exporter = new JRRtfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                
            } else if ("html".equalsIgnoreCase(reporttype)) {
              //  response.setContentType("application/html");
              //  response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".html\"");
                
                exporter = new JRXhtmlExporter();
              //  exporter.setParameter(JRPdfExporterParameter.is, true);
                
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                exporter.exportReport();
                
            } else if ("xls".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/csv");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".csv\"");

                exporter = new JRCsvExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);                
            } else if ("docx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/docx");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".docx\"");

                exporter = new JRDocxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xlsx".equalsIgnoreCase(reporttype)) {
                response.setContentType("application/xlsx");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".xlsx\"");

                exporter = new JRXlsxExporter();
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if ("xml".equalsIgnoreCase(reporttype)) {
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
