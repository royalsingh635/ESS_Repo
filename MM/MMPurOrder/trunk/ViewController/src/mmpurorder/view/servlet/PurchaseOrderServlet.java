package mmpurorder.view.servlet;

/* import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*; */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.util.JRLoader;

public class PurchaseOrderServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        System.out.println("do get");
                try {
                    Context ctx = new InitialContext();
                    DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/MMDS");
                    conn = ds.getConnection();
                    PreparedStatement ps =
                        conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

                    ResultSet rs = ps.executeQuery();
                    String path = null;
                    while (rs.next()) {
                        path = rs.getString(1);
                    }
                    path =path+"MM/";
                    String cld_id = String.valueOf(request.getParameter("CLD_ID"));
                    Integer sloc_id = Integer.parseInt(request.getParameter("SLOC_ID"));
                    String org_id = String.valueOf(request.getParameter("ORG_ID"));
                    String doc_id = String.valueOf(request.getParameter("DOC_ID"));
                    String request_id=String.valueOf(request.getParameter("RequestId"));
                    System.out.println("cld="+cld_id+" and sloc="+sloc_id+" and org="+org_id+" and doc="+doc_id+" and request="+request_id);
     
     
                    if(request_id.equals(" po")){
                        /* InputStream inputPO = new FileInputStream(new File(path + "PurchaseOrder.jrxml"));
                        JasperDesign designPO = JRXmlLoader.load(inputPO);
                        JasperReport reportPO = JasperCompileManager.compileReport(designPO); */
                        JasperReport inputPO =(JasperReport)JRLoader.loadObject(path+"PurchaseOrder.jasper");
                        String reporttypePO =  "pdf";

                      
                  
                        Map parametersPO = new HashMap();
                        parametersPO.put("OrgId", org_id);
                        parametersPO.put("SlocId", sloc_id);
                        parametersPO.put("CldId", cld_id);
                        parametersPO.put("DocId", doc_id);
                        parametersPO.put("Path", path);
                        JasperPrint jasperPrintPO = null;
                        jasperPrintPO = JasperFillManager.fillReport(inputPO, parametersPO, conn);
                        OutputStream ouputStreamPO = response.getOutputStream();
                        JRExporter exporterPO = null;
                        if (reporttypePO.equalsIgnoreCase("pdf")) {
                            response.setContentType("application/pdf");
                            response.setHeader("Content-Disposition", "attachement; filename=\"PurchaseOrder.pdf\"");
                            exporterPO = new JRPdfExporter();
                            exporterPO.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrintPO);
                            exporterPO.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStreamPO);
                        } 
                    System.out.println("exporter of PO");     
                    try {
                        System.out.println("exporting of PO");     
                        exporterPO.exportReport();
                        System.out.println("exported of PO");   
                    } catch (JRException e) {
                        throw new ServletException(e);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }   
                    
                    if (ouputStreamPO != null) {
                        try {
                            ouputStreamPO.flush();
                            ouputStreamPO.close();

                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                           // throw (ex);
                        }
                    }
                    }
                    
                    
                    if(request_id.equals(" opo")){
                        /* InputStream inputPO = new FileInputStream(new File(path + "PurchaseOrder.jrxml"));
                        JasperDesign designPO = JRXmlLoader.load(inputPO);
                        JasperReport reportPO = JasperCompileManager.compileReport(designPO); */
                        JasperReport inputPO =(JasperReport)JRLoader.loadObject(path+"OpenOrderPO.jasper");
                        String reporttypePO =  "pdf";

                      
                    
                        Map parametersPO = new HashMap();
                        parametersPO.put("OrgId", org_id);
                        parametersPO.put("SlocId", sloc_id);
                        parametersPO.put("CldId", cld_id);
                        parametersPO.put("DocId", doc_id);
                        parametersPO.put("Path", path);
                        JasperPrint jasperPrintPO = null;
                        jasperPrintPO = JasperFillManager.fillReport(inputPO, parametersPO, conn);
                        OutputStream ouputStreamPO = response.getOutputStream();
                        JRExporter exporterPO = null;
                        if (reporttypePO.equalsIgnoreCase("pdf")) {
                            response.setContentType("application/pdf");
                            response.setHeader("Content-Disposition", "attachement; filename=\"OpenOrderPO.pdf\"");
                            exporterPO = new JRPdfExporter();
                            exporterPO.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrintPO);
                            exporterPO.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStreamPO);
                        } 
                    System.out.println("exporter of PO");     
                    try {
                        System.out.println("exporting of OPO");     
                        exporterPO.exportReport();
                        System.out.println("exported of OPO");   
                    } catch (JRException e) {
                        throw new ServletException(e);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }   
                    
                    if (ouputStreamPO != null) {
                        try {
                            ouputStreamPO.flush();
                            ouputStreamPO.close();

                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                           // throw (ex);
                        }
                    }
                    }
                    System.out.println("PO Report Called now go for Dlv Schdl");
                    
                    if(request_id.equals(" rc")){
                        /* InputStream inputPO = new FileInputStream(new File(path + "PurchaseOrder.jrxml"));
                        JasperDesign designPO = JRXmlLoader.load(inputPO);
                        JasperReport reportPO = JasperCompileManager.compileReport(designPO); */
                        JasperReport inputPO =(JasperReport)JRLoader.loadObject(path+"RateContractPO.jasper");
                        String reporttypePO =  "pdf";

                      
                    
                        Map parametersPO = new HashMap();
                        parametersPO.put("OrgId", org_id);
                        parametersPO.put("SlocId", sloc_id);
                        parametersPO.put("CldId", cld_id);
                        parametersPO.put("DocId", doc_id);
                        parametersPO.put("Path", path);
                        JasperPrint jasperPrintPO = null;
                        jasperPrintPO = JasperFillManager.fillReport(inputPO, parametersPO, conn);
                        OutputStream ouputStreamPO = response.getOutputStream();
                        JRExporter exporterPO = null;
                        if (reporttypePO.equalsIgnoreCase("pdf")) {
                            response.setContentType("application/pdf");
                            response.setHeader("Content-Disposition", "attachement; filename=\"RateContractPO.pdf\"");
                            exporterPO = new JRPdfExporter();
                            exporterPO.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrintPO);
                            exporterPO.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStreamPO);
                        } 
                    System.out.println("exporter of RCPO");     
                    try {
                        System.out.println("exporting of RCPO");     
                        exporterPO.exportReport();
                        System.out.println("exported of RCPO");   
                    } catch (JRException e) {
                        throw new ServletException(e);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }   
                    
                    if (ouputStreamPO != null) {
                        try {
                            ouputStreamPO.flush();
                            ouputStreamPO.close();

                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                           // throw (ex);
                        }
                    }
                    }
                    System.out.println("PO Report Called now go for Dlv Schdl");
                    
                    
                    System.out.println("PO Report Called now go for Dlv Schdl");
                // Delivery Schedule (PO Wise)----------------------------------------------
                    if(request_id.equals("dlv")){
                    /* InputStream inputDlv = new FileInputStream(new File(path + "PurchaseOrderDeliverySchedule.jrxml"));
                    JasperDesign designDlv = JRXmlLoader.load(inputDlv);
                    JasperReport reportDlv = JasperCompileManager.compileReport(designDlv); */
                    JasperReport reportDlv =(JasperReport)JRLoader.loadObject(path+"PurchaseOrderDeliverySchedule.jasper");
                    String reporttypeDlv = "pdf";
        
                    Map parametersDlv = new HashMap();
                    parametersDlv.put("OrgId", org_id);
                    parametersDlv.put("SlocId", sloc_id);
                    parametersDlv.put("CldId", cld_id);
                    parametersDlv.put("DocId", doc_id);
                    parametersDlv.put("Path", path);
                    JasperPrint jasperPrintDlv = null;
                    jasperPrintDlv = JasperFillManager.fillReport(reportDlv, parametersDlv, conn);
                    OutputStream ouputStreamDlv = response.getOutputStream();
                    JRExporter exporterDlv = null;
                    if (reporttypeDlv.equalsIgnoreCase("pdf")) {
                        response.setContentType("application/pdf");
                        response.setHeader("Content-Disposition", "attachement; filename=\"PurchaseOrderDeliverySchedule.pdf\"");
                        exporterDlv = new JRPdfExporter();
                        exporterDlv.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrintDlv);
                        exporterDlv.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStreamDlv);
                    }       
                    System.out.println("exporter of DlvSchdl");
                    try {
                        System.out.println("Exporting dlvSchdl");
                        exporterDlv.exportReport();
                        System.out.println("exported of DlvSchdl");
                    } catch (JRException e) {
                        throw new ServletException(e);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    
                        if (ouputStreamDlv != null) {
                            try {
                                ouputStreamDlv.flush();
                                ouputStreamDlv.close();

                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());
                               // throw (ex);
                            }
                        } 
                   
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                finally {
                    try {
                        if (conn != null) {
                            conn.close();
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

    }
}
