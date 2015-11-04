package salesreportconfigurationapp.view.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

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
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import oracle.jbo.client.Configuration;

import salesreportconfigurationapp.model.services.SaleReportAMImpl;

public class SalesReportServ extends HttpServlet {
    
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PreparedStatement st = null;
        String amDef = "salesreportconfigurationapp.model.services.SaleReportAMImpl";
        String config = "SaleReportAMLocal";
        SaleReportAMImpl am = (SaleReportAMImpl)Configuration.createRootApplicationModule(amDef, config);
        st = am.getDBTransaction().createPreparedStatement("select 1 from dual", 0);
        Connection conn = null;
        try {
            conn = st.getConnection();
            System.out.println("Conn "+conn.isClosed());
        } catch (SQLException e) {
            System.out.println("Connection returns to null !");
        }
       
       /**
        * Input Parameters to the servlet
        */
        StringBuffer reportId = this.getInputParameter(request, new StringBuffer("reportId"));
        StringBuffer fileType = this.getInputParameter(request, new StringBuffer("fileType"));
        
        if(fileType.toString().equals("")){
            System.out.println("File type not found !");
        }
        else if(!reportId.toString().equals("")){
            if(reportId.toString().equals("1")){
                try {
                    StringBuffer orgId = this.getInputParameter(request, new StringBuffer("orgId"));
                    Integer slocId = Integer.parseInt(this.getInputParameter(request, new StringBuffer("slocId")).toString()) ;
                    StringBuffer hoOrgId = this.getInputParameter(request, new StringBuffer("hoOrgId"));
                    StringBuffer cldId = this.getInputParameter(request, new StringBuffer("cldId"));
                    StringBuffer docId = this.getInputParameter(request, new StringBuffer("docId"));
                    
                    Map params = new HashMap();
                    params.put("ORG_ID", orgId.toString());
                    params.put("SLOC_ID", slocId);
                    params.put("CLD_ID", cldId.toString());
                    params.put("HO_ORG_ID", hoOrgId.toString());
                    params.put("INVOICE_ID", docId.toString());
                    
                    StringBuffer reportPath = new StringBuffer("D:/Report/SalesInvoice.jrxml");
                    this.generateReport(conn, reportPath, new StringBuffer("Sales Invoice Report"), params , fileType, response);
                      
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(reportId.toString().equals("2")){
                try {
                    StringBuffer orgId = this.getInputParameter(request, new StringBuffer("orgId"));
                    Integer slocId = Integer.parseInt(this.getInputParameter(request, new StringBuffer("slocId")).toString()) ;
                    StringBuffer hoOrgId = this.getInputParameter(request, new StringBuffer("hoOrgId"));
                    StringBuffer cldId = this.getInputParameter(request, new StringBuffer("cldId"));
                    StringBuffer oppId = this.getInputParameter(request, new StringBuffer("oppId"));
                    StringBuffer fromdate = this.getInputParameter(request, new StringBuffer("FromDate"));
                    StringBuffer todate = this.getInputParameter(request, new StringBuffer("ToDate"));
                    Map params = new HashMap();
                    params.put("OrgId", orgId.toString());
                    params.put("SlocId", slocId);
                    params.put("CldId", cldId.toString());
                    params.put("HoOrgId", hoOrgId.toString());
                    params.put("OppId", oppId.toString());
                    params.put("FromDate", fromdate.toString());
                    params.put("ToDate", todate.toString());
                   // params.put("OppDt", todate.toString());
                    StringBuffer reportPath = new StringBuffer("D:/Report/SalesOpportunityReport.jrxml");
                    this.generateReport(conn, reportPath, new StringBuffer("Sales Opportunity Report"), params , fileType, response);
                      
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(reportId.toString().equals("3")){
                try {
                    StringBuffer orgId = this.getInputParameter(request, new StringBuffer("orgId"));
                    Integer slocId = Integer.parseInt(this.getInputParameter(request, new StringBuffer("slocId")).toString()) ;
                    StringBuffer hoOrgId = this.getInputParameter(request, new StringBuffer("hoOrgId"));
                    StringBuffer cldId = this.getInputParameter(request, new StringBuffer("cldId"));
                    StringBuffer soId = this.getInputParameter(request, new StringBuffer("soId"));
                
                    Map params = new HashMap();
                    params.put("OrgId", orgId.toString());
                    params.put("SlocId", slocId);
                    params.put("CldId", cldId.toString());
                    params.put("HoOrgId", hoOrgId.toString());
                    params.put("SoId", soId.toString());
                    //params.put("FromDate", docId.toString());
                    //params.put("ToDate", docId.toString());
                    //params.put("OppDt", docId.toString());
                    StringBuffer reportPath = new StringBuffer("D:/Report/SalesOrderStatusReport.jrxml");
                    this.generateReport(conn, reportPath, new StringBuffer("Sales Order Report"), params , fileType, response);
                      
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(reportId.toString().equals("4")){
                try {
                    StringBuffer orgId = this.getInputParameter(request, new StringBuffer("orgId"));
                    Integer slocId = Integer.parseInt(this.getInputParameter(request, new StringBuffer("slocId")).toString()) ;
                    StringBuffer hoOrgId = this.getInputParameter(request, new StringBuffer("hoOrgId"));
                    StringBuffer cldId = this.getInputParameter(request, new StringBuffer("cldId"));
                    StringBuffer intmId = this.getInputParameter(request, new StringBuffer("intmId"));
                
                    Map params = new HashMap();
                    params.put("OrgId", orgId.toString());
                    params.put("SlocId", slocId);
                    params.put("CldId", cldId.toString());
                    params.put("HoOrgId", hoOrgId.toString());
                    params.put("IntimationId", intmId.toString());
                    //params.put("FromDate", docId.toString());
                    //params.put("ToDate", docId.toString());
                    //params.put("OppDt", docId.toString());
                    StringBuffer reportPath = new StringBuffer("D:/Report/SlsIntimationSlipReport.jrxml");
                    this.generateReport(conn, reportPath, new StringBuffer("Sales Intimation Report"), params , fileType, response);
                      
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(reportId.toString().equals("5")){
                try {
                    StringBuffer orgId = this.getInputParameter(request, new StringBuffer("orgId"));
                    Integer slocId = Integer.parseInt(this.getInputParameter(request, new StringBuffer("slocId")).toString()) ;
                    StringBuffer hoOrgId = this.getInputParameter(request, new StringBuffer("hoOrgId"));
                    StringBuffer cldId = this.getInputParameter(request, new StringBuffer("cldId"));
                    //StringBuffer intmId = this.getInputParameter(request, new StringBuffer("intmId"));
                    Integer catgId = Integer.parseInt(this.getInputParameter(request, new StringBuffer("catgId")).toString()) ;
                    Integer eoId = Integer.parseInt(this.getInputParameter(request, new StringBuffer("eoId")).toString()) ;
                    Map params = new HashMap();
                    params.put("OrgId", orgId.toString());
                    params.put("SlocId", slocId);
                    params.put("CatgId", catgId);
                    params.put("EoId", eoId);
                    params.put("CldId", cldId.toString());
                    params.put("HoOrgId", hoOrgId.toString());
                    //params.put("IntimationId", intmId.toString());
                    //params.put("FromDate", docId.toString());
                    //params.put("ToDate", docId.toString());
                    //params.put("OppDt", docId.toString());
                    StringBuffer reportPath = new StringBuffer("D:/Report/PriceMasterReport.jrxml");
                    this.generateReport(conn, reportPath, new StringBuffer("Price Master Report"), params , fileType, response);
                      
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(reportId.toString().equals("6")){
                try {
                    StringBuffer orgId = this.getInputParameter(request, new StringBuffer("orgId"));
                    System.out.println(orgId);
                    Integer slocId = Integer.parseInt(this.getInputParameter(request, new StringBuffer("slocId")).toString()) ;
                    System.out.println(slocId);
                    StringBuffer hoOrgId = this.getInputParameter(request, new StringBuffer("hoOrgId"));
                    System.out.println(hoOrgId);
                    StringBuffer cldId = this.getInputParameter(request, new StringBuffer("cldId"));
                    System.out.println(cldId);
                    StringBuffer pickId = this.getInputParameter(request, new StringBuffer("pickId"));
                    System.out.println(pickId);
                    //Integer catgId = Integer.parseInt(this.getInputParameter(request, new StringBuffer("catgId")).toString()) ;
                    //Integer eoId = Integer.parseInt(this.getInputParameter(request, new StringBuffer("eoId")).toString()) ;
                    Map params = new HashMap();
                    params.put("OrgId", orgId.toString());
                    params.put("SlocId", slocId);
                    params.put("PickId", pickId.toString());
                    params.put("CldId", cldId.toString());
                    params.put("HoOrgId", hoOrgId.toString());
                    StringBuffer reportPath = new StringBuffer("D:/Report/SLSPickListRespot.jrxml");
                    this.generateReport(conn, reportPath, new StringBuffer("Pick List Report"), params , fileType, response);
                      
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(reportId.toString().equals("7")){
                try {
                    StringBuffer orgId = this.getInputParameter(request, new StringBuffer("orgId"));
                    System.out.println(orgId);
                    Integer slocId = Integer.parseInt(this.getInputParameter(request, new StringBuffer("slocId")).toString()) ;
                    System.out.println(slocId);
                    StringBuffer hoOrgId = this.getInputParameter(request, new StringBuffer("hoOrgId"));
                    System.out.println(hoOrgId);
                    StringBuffer cldId = this.getInputParameter(request, new StringBuffer("cldId"));
                    System.out.println(cldId);
                    StringBuffer docId = this.getInputParameter(request, new StringBuffer("docId"));
                    System.out.println(docId);
                    //Integer catgId = Integer.parseInt(this.getInputParameter(request, new StringBuffer("catgId")).toString()) ;
                    //Integer eoId = Integer.parseInt(this.getInputParameter(request, new StringBuffer("eoId")).toString()) ;
                    Map params = new HashMap();
                    params.put("OrgId", orgId.toString());
                    params.put("SlocId", slocId);
                    //params.put("CatgId", catgId);
                    params.put("docId", docId.toString());
                    params.put("CldId", cldId.toString());
                    params.put("HoOrgId", hoOrgId.toString());
                    StringBuffer reportPath = new StringBuffer("D:/Report/CancelOrderReport.jrxml");
                    this.generateReport(conn, reportPath, new StringBuffer("Cancel Order Report"), params , fileType, response);
                      
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(reportId.toString().equals("8")){
                try {
                    StringBuffer orgId = this.getInputParameter(request, new StringBuffer("orgId"));
                    System.out.println(orgId);
                    Integer slocId = Integer.parseInt(this.getInputParameter(request, new StringBuffer("slocId")).toString()) ;
                    System.out.println(slocId);
                    StringBuffer hoOrgId = this.getInputParameter(request, new StringBuffer("hoOrgId"));
                    System.out.println(hoOrgId);
                    StringBuffer cldId = this.getInputParameter(request, new StringBuffer("cldId"));
                    System.out.println(cldId);
                    StringBuffer invId = this.getInputParameter(request, new StringBuffer("invId"));
                    System.out.println(invId);
                    //Integer eoId = Integer.parseInt(this.getInputParameter(request, new StringBuffer("eoId")).toString()) ;
                    Map params = new HashMap();
                    params.put("OrgId", orgId.toString());
                    params.put("SlocId", slocId);
                    params.put("InvId", invId.toString());
                    //params.put("EoId", eoId);
                    params.put("CldId", cldId.toString());
                    //params.put("HoOrgId", hoOrgId.toString());
                    StringBuffer reportPath = new StringBuffer("D:/Report/InvoiceRegisterReport.jrxml");
                    this.generateReport(conn, reportPath, new StringBuffer("Invoice Register Report"), params , fileType, response);
                      
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("No report of Report Id "+reportId+" is found !");
            }
            
        }else{
            System.out.println("Please provide reportId.");
        }
        
    }
    
    
    /**
     * Method to generate a report in the Servlet
     * @param con
     * @param filePath
     * @param fileName
     * @param inputParams
     * @param ReportType
     * @param response
     * @throws FileNotFoundException
     * @throws JRException
     */
    
    
    public void generateReport(Connection con, StringBuffer filePath,StringBuffer fileName,Map inputParams,StringBuffer ReportType, HttpServletResponse response) throws FileNotFoundException, JRException, IOException,
                                                                                             ServletException {
        if ((con != null)) {
            Connection conn = con;
            if (!filePath.toString().equalsIgnoreCase("")) {
                InputStream input = new FileInputStream(new File(filePath.toString()));
                
                JasperDesign design = JRXmlLoader.load(input);
                JasperReport report = JasperCompileManager.compileReport(design);
                
                JasperPrint jasperPrint = JasperFillManager.fillReport(report, inputParams, conn);
                OutputStream outputStream = response.getOutputStream();
                OutputStream o = outputStream;
                System.out.println(o);
                JRExporter exporter = null;
                if ("pdf".equalsIgnoreCase(ReportType.toString())) {
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "attachement; filename=\""+fileName.toString()+".pdf\"");
                    //response.setHeader("Content-Disposition:attachement;", "filename=Bank_cash_book_report.pdf");
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
                } else if ("rtf".equalsIgnoreCase(ReportType.toString())) {
                    response.setContentType("application/rtf");
                    response.setHeader("Pragma", "no-cache");
                    response.setHeader("Content-Disposition", "attachement; filename=\""+fileName.toString()+".rtf\"");
                    exporter = new JRRtfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
                } else if ("html".equalsIgnoreCase(ReportType.toString())) {
                    exporter = new JRHtmlExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
                } else if ("xls".equalsIgnoreCase(ReportType.toString())) {
                    response.setContentType("application/xls");
                    response.setHeader("Content-Disposition", "attachement; filename=\""+fileName.toString()+".xls\"");
                    exporter = new JRXlsExporter();
                    exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
                } else if ("docx".equalsIgnoreCase(ReportType.toString())) {
                    response.setContentType("application/docx");
                    response.setHeader("Content-Disposition", "attachement; filename=\""+fileName.toString()+".docx\"");
                    exporter = new JRDocxExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
                } else if ("xlsx".equalsIgnoreCase(ReportType.toString())) {
                    response.setContentType("application/xlsx");
                    response.setHeader("Content-Disposition", "attachement; filename=\""+fileName.toString()+".xlsx\"");
                    exporter = new JRXlsxExporter();
                    exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
                } else if ("xml".equalsIgnoreCase(ReportType.toString())) {
                    response.setContentType("application/xml");
                    response.setHeader("Content-Disposition", "attachement; filename=\""+fileName.toString()+".xls\"");
                    exporter = new JRXmlExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
                }
                
                try {
                    exporter.exportReport();
                } catch (JRException e) {
                    throw new ServletException(e);
                } finally {
                    if (outputStream != null) {
                        outputStream.flush();
                        outputStream.close();
                    }
                }
                
            } else {
                System.out.println("No report path is provided !");
            }
        } else {
            System.out.println("Connection is not working !");
        }
    }
    
    /**
     * Method checks for the null value in the InputParameter and returns the inputParameter
     * @param request
     * @param param
     * @return
     */
    public StringBuffer getInputParameter(HttpServletRequest request,StringBuffer param){
        if(request != null || !param.toString().equals("")){
            String parameter = request.getParameter(param.toString());
            if(!parameter.equals("")){
                return new StringBuffer(parameter);
            }
        }else{
            System.out.println("Invalid parameter : "+param);
        }
        return null;
    }
}
