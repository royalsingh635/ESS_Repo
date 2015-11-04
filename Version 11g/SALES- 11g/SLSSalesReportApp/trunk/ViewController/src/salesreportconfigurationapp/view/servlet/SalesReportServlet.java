package salesreportconfigurationapp.view.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;


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

            ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

            rs = ps.executeQuery();
            String path = null;
            while (rs.next()) {
                path = rs.getString(1);
                // System.out.println("Path is :" + rs.getString(1));
            }

            System.out.println("Path is " + path + reportName);
            //InputStream input = new FileInputStream(new File("D:/Report/" + reportName + ".jrxml"));
            InputStream input = new FileInputStream(new File(path + reportName + ".jrxml"));

            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design);


            String reporttype;
            String Org = request.getParameter("organisation");
            String cldid = request.getParameter("cldid");
            String hoOrgid = request.getParameter("hoorgid");
            Integer slocid = Integer.parseInt(request.getParameter("slocid"));
            Integer CatId;
           // String assigned;
            String status;
            String docno;
            String eoid;
            String salesman;
            String itmgrp;
            String itmName;
            String Todate;
            String Fromdate;
            Integer currid;
            Integer FormStatus;
        //    String tmparid;  // for entry of template id temparay 25-March-2014

            //currid
            if (request.getParameter("currid").equals("")) {
                currid = null;
            } else {
                /*  Double y = Double.parseDouble(request.getParameter("currid"));
                BigDecimal z = new BigDecimal(y.doubleValue()); */
                currid = Integer.parseInt(request.getParameter("currid").toString());
            }

            //Fromdate
            if (request.getParameter("fromdate").equals("")) {
                Fromdate = null;
            } else {
                Fromdate = request.getParameter("fromdate");

            }


            //Todate
            if (request.getParameter("todate").equals("")) {
                Todate = null;
            } else {
                Todate = request.getParameter("todate");
            }


            //CatId
            if (request.getParameter("catid").equals("")) {
                CatId = null;
            } else {
                System.out.println("Category ID is-->"+request.getParameter("catid"));
                CatId = Integer.parseInt(request.getParameter("catid").toString());
                
            }
            //Form status
            if (request.getParameter("formstatus").equals("")) {
                FormStatus = null;
            } else {
                System.out.println("status is --->"+request.getParameter("formstatus"));
                FormStatus = Integer.parseInt(request.getParameter("formstatus").toString());
                    
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
            if (request.getParameter("docno").equals("")) {
                docno = null;
            } else {
                System.out.println("Doc ID Is -->"+request.getParameter("docno"));
                docno = request.getParameter("docno") ;
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


            //Report Type
            if (request.getParameter("ReportType").equals("")) {
                reporttype = "pdf";
            } else {
                reporttype = request.getParameter("ReportType");
            }

            //System.out.println(Todate + " --- " + Fromdate);
              System.out.println("Report Type " + request.getParameter("ReportType"));
            System.out.println("Org " + Org + " Sloc " + slocid + " cld " + cldid + " hoorg " + hoOrgid + " catid " +
                               CatId + " todate " + Todate + " from " + Fromdate + " itm name " + itmName +
                               " itmgrp " + itmgrp + " eoid " + eoid + " + status " + FormStatus + " sales " +
                               salesman + " curr id "+currid); 
            
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
        //   parameters.put("Status", status);
         parameters.put("AssignTo",salesman ); //DocId
       // parameters.put("AssignTo", assigned);
            parameters.put("CurrId", currid);
            parameters.put("Path", path);
            parameters.put("Status",FormStatus);
      //      parameters.put("tmparid", tmparid);

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
                response.setContentType("application/xls");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".xls\"");

                exporter = new JRXlsExporter();
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
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
