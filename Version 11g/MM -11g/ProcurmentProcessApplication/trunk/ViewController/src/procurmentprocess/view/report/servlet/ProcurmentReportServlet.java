package procurmentprocess.view.report.servlet;

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

import oracle.jbo.domain.Timestamp;

public class ProcurmentReportServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Connection conn = null;
        
        //System.out.println(path);
        try {
            

            String requestId = request.getParameter("requestId");
            
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MMDS");
            conn = ds.getConnection();
            PreparedStatement ps =
                conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");

            ResultSet rs = ps.executeQuery();
            String path = null;
            while (rs.next()) {
                path = rs.getString(1);
            }
            System.out.println(path);
            String cld_id = String.valueOf(request.getParameter("CLD_ID"));
            //  System.out.println("CLD="+cld_id+" and slocid="+request.getParameter("SLOC_ID"));
            //Integer sloc_id = Integer.valueOf(request.getParameter("SLOC_ID"));
             Integer sloc_id = Integer.parseInt(request.getParameter("SLOC_ID"));
            String org_id = String.valueOf(request.getParameter("ORG_ID"));

            String doc_id = String.valueOf(request.getParameter("DOC_ID"));
            System.out.println("docIdPO--"+doc_id);
            
            if (requestId.equals("gl1")) {
                String po_no = String.valueOf(request.getParameter("PO_NO"));


                InputStream input = new FileInputStream(new File(path+"PurchaseOrder.jrxml"));
                JasperDesign design = JRXmlLoader.load(input);
                JasperReport report = JasperCompileManager.compileReport(design);
                String reporttype = null;

                //Report Type
                if (request.getParameter("ReportType") == null || request.getParameter("ReportType").length() <= 0) {
                    reporttype = "pdf";
                } else {
                    reporttype = String.valueOf(request.getParameter("ReportType"));
                }


                //    po_no=String.valueOf(request.getParameter("PO_NO"));

                //  System.out.println("doc_id="+doc_id);
                Map parameters = new HashMap();
                //parameters.put("PO_No", po_no);
                System.out.println("OrgId--"+org_id);
                parameters.put("orgId", org_id);
                System.out.println("sloc--"+sloc_id);
                parameters.put("slocId", sloc_id);
                System.out.println("cld--"+cld_id);
                parameters.put("cldId", cld_id);
                parameters.put("docId", doc_id);
                System.out.println("Path--"+path);
                parameters.put("Path", path);
                JasperPrint jasperPrint = null;
                jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
                OutputStream ouputStream = response.getOutputStream();
                JRExporter exporter = null;
                if (reporttype.equalsIgnoreCase("pdf")) {
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "attachement; filename=\"PurchaseOrder.pdf\"");
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                } else if (reporttype.equalsIgnoreCase("xls")) {
                    response.setContentType("application/xls");
                    response.setHeader("Content-Disposition", "attachement; filename=\"PurchaseOrder.xls\"");
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                }
                try {
                    exporter.exportReport();
                } catch (JRException e) {
                    throw new ServletException(e);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                finally {
                    if (ouputStream != null) {
                        try {
                            ouputStream.flush();
                            ouputStream.close();

                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                            throw (ex);
                        }
                    }
                }
            }
            
     // Po Status Wise-----------------------------------
     if (requestId.equals("gl9")) {
         String FromDate;
         String ToDate;
         String Eo_Id;
         Integer Po_Stat;
         if(request.getParameter("Eo_Id")!=null){
             if(request.getParameter("Eo_Id").equals("")){
                 Eo_Id=null;
             }
             else{
             Eo_Id=String.valueOf(request.getParameter("Eo_Id"));
         }
         }
         else{
             Eo_Id=null;
         }
         
         if(request.getParameter("Po_Stat")!=null){
             if(request.getParameter("Po_Stat").equals("")){
                 Po_Stat=null;
             }
             else{
             Po_Stat=Integer.valueOf(request.getParameter("Po_Stat"));
         }
         }
         else{
             Po_Stat=null;
         }
          
         
         if (request.getParameter("FromDate") != null) {
             if (request.getParameter("FromDate").equals("")) {
                 FromDate = null;
             } else {
                 FromDate = request.getParameter("FromDate");
             }
         } else {
             FromDate = null;
         }

         if (request.getParameter("ToDate") != null) {
             if (request.getParameter("ToDate").equals("")) {
                 ToDate = null;
             } else {
                 ToDate = request.getParameter("ToDate");
             }
         } else {
             ToDate = null;
         }


         InputStream input = new FileInputStream(new File(path+"PO STATUS.jrxml"));
         JasperDesign design = JRXmlLoader.load(input);
         JasperReport report = JasperCompileManager.compileReport(design);
         String reporttype = null;

         //Report Type
         if (request.getParameter("ReportType") == null || request.getParameter("ReportType").length() <= 0) {
             reporttype = "pdf";
         } else {
             reporttype = String.valueOf(request.getParameter("ReportType"));
         }


         //    po_no=String.valueOf(request.getParameter("PO_NO"));

         //  System.out.println("doc_id="+doc_id);
         Map parameters = new HashMap();
         //parameters.put("PO_No", po_no);
         parameters.put("Eo_Id", Eo_Id);
         parameters.put("Po_Stat", Po_Stat);
         parameters.put("FromDate", FromDate);
         parameters.put("ToDate", ToDate);
         parameters.put("OrgId", org_id);
         parameters.put("SlocId", sloc_id);
         parameters.put("CldId", cld_id);
         parameters.put("DocId", doc_id);
         System.out.println("Path--"+path);
         parameters.put("Path", path);
         
         JasperPrint jasperPrint = null;
         jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
         OutputStream ouputStream = response.getOutputStream();
         JRExporter exporter = null;
         if (reporttype.equalsIgnoreCase("pdf")) {
             response.setContentType("application/pdf");
             response.setHeader("Content-Disposition", "attachement; filename=\"PO STATUS.pdf\"");
             exporter = new JRPdfExporter();
             exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
             exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
         } else if (reporttype.equalsIgnoreCase("xls")) {
             response.setContentType("application/xls");
             response.setHeader("Content-Disposition", "attachement; filename=\"PO STATUS.xls\"");
             exporter = new JRPdfExporter();
             exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
             exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
         }
         try {
             exporter.exportReport();
         } catch (JRException e) {
             throw new ServletException(e);
         } catch (Exception ex) {
             ex.printStackTrace();
         }

         finally {
             if (ouputStream != null) {
                 try {
                     ouputStream.flush();
                     ouputStream.close();

                 } catch (IOException ex) {
                     System.out.println(ex.getMessage());
                     throw (ex);
                 }
             }
         }
     }
     // PO Summary--------------------------------------------------
     
     if (requestId.equals("gl10")) {
         String FromDate;
         String ToDate;
         Integer  Eo_Id;
         Integer Po_Stat;
         if(request.getParameter("Eo_Id")!=null){
             if(request.getParameter("Eo_Id").equals("")){
                 Eo_Id=null;
             }
             else{
             Eo_Id=Integer .valueOf(request.getParameter("Eo_Id"));
         }
         }
         else{
             Eo_Id=null;
         }
         
         if(request.getParameter("Po_Stat")!=null){
             if(request.getParameter("Po_Stat").equals("")){
                 Po_Stat=null;
             }
             else{
             Po_Stat=Integer.valueOf(request.getParameter("Po_Stat"));
         }
         }
         else{
             Po_Stat=null;
         }
          
         
         if (request.getParameter("FromDate") != null) {
             if (request.getParameter("FromDate").equals("")) {
                 FromDate = null;
             } else {
                 FromDate = request.getParameter("FromDate");
             }
         } else {
             FromDate = null;
         }

         if (request.getParameter("ToDate") != null) {
             if (request.getParameter("ToDate").equals("")) {
                 ToDate = null;
             } else {
                 ToDate = request.getParameter("ToDate");
             }
         } else {
             ToDate = null;
         }


         InputStream input = new FileInputStream(new File(path+"PurchaseOrderSummary.jrxml"));
         JasperDesign design = JRXmlLoader.load(input);
         JasperReport report = JasperCompileManager.compileReport(design);
         String reporttype = null;

         //Report Type
         if (request.getParameter("ReportType") == null || request.getParameter("ReportType").length() <= 0) {
             reporttype = "pdf";
         } else {
             reporttype = String.valueOf(request.getParameter("ReportType"));
         }


         //    po_no=String.valueOf(request.getParameter("PO_NO"));

         //  System.out.println("doc_id="+doc_id);
         Map parameters = new HashMap();
         //parameters.put("PO_No", po_no);
         parameters.put("Eo_Id", Eo_Id);
         parameters.put("PoStat", Po_Stat);
         parameters.put("FromDate", FromDate);
         parameters.put("ToDate", ToDate);
         parameters.put("OrgId", org_id);
         parameters.put("SlocId", sloc_id);
         parameters.put("CldId", cld_id);
         parameters.put("DocId", doc_id);
         System.out.println("Path--"+path);
         parameters.put("Path", path);
         
         JasperPrint jasperPrint = null;
         jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
         OutputStream ouputStream = response.getOutputStream();
         JRExporter exporter = null;
         if (reporttype.equalsIgnoreCase("pdf")) {
             response.setContentType("application/pdf");
             response.setHeader("Content-Disposition", "attachement; filename=\"PurchaseOrderSummary.pdf\"");
             exporter = new JRPdfExporter();
             exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
             exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
         } else if (reporttype.equalsIgnoreCase("xls")) {
             response.setContentType("application/xls");
             response.setHeader("Content-Disposition", "attachement; filename=\"PurchaseOrderSummary.xls\"");
             exporter = new JRPdfExporter();
             exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
             exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
         }
         try {
             exporter.exportReport();
         } catch (JRException e) {
             throw new ServletException(e);
         } catch (Exception ex) {
             ex.printStackTrace();
         }

         finally {
             if (ouputStream != null) {
                 try {
                     ouputStream.flush();
                     ouputStream.close();

                 } catch (IOException ex) {
                     System.out.println(ex.getMessage());
                     throw (ex);
                 }
             }
         }
     }
        
        // Delivery Schedule (PO Wise)----------------------------------------------
     
     
        if (requestId.equals("gl12")) {
            InputStream input = new FileInputStream(new File(path+"PurchaseOrderDeliverySchedule.jrxml"));
            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design);
            String reporttype = null;

            //Report Type
            if (request.getParameter("ReportType") == null || request.getParameter("ReportType").length() <= 0) {
                reporttype = "pdf";
            } else {
                reporttype = String.valueOf(request.getParameter("ReportType"));
            }


            //    po_no=String.valueOf(request.getParameter("PO_NO"));

            //  System.out.println("doc_id="+doc_id);
            Map parameters = new HashMap();
            //parameters.put("PO_No", po_no);
            parameters.put("OrgId", org_id);
            parameters.put("SlocId", sloc_id);
            parameters.put("CldId", cld_id);
            parameters.put("DocId", doc_id);
            System.out.println("Path--"+path);
            parameters.put("Path", path);
            
            JasperPrint jasperPrint = null;
            jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();
            JRExporter exporter = null;
            if (reporttype.equalsIgnoreCase("pdf")) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachement; filename=\"PurchaseOrderDeliverySchedule.pdf\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if (reporttype.equalsIgnoreCase("xls")) {
                response.setContentType("application/xls");
                response.setHeader("Content-Disposition", "attachement; filename=\"PurchaseOrderDeliverySchedule.xls\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            }
            try {
                exporter.exportReport();
            } catch (JRException e) {
                throw new ServletException(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            finally {
                if (ouputStream != null) {
                    try {
                        ouputStream.flush();
                        ouputStream.close();

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                        throw (ex);
                    }
                }
            }
        }
            //--------Rate Contract purchase order 
            if (requestId.equals("gl15")) {
                //String po_no = String.valueOf(request.getParameter("PO_NO"));


                InputStream input = new FileInputStream(new File(path+"RateContractPO.jrxml"));
                JasperDesign design = JRXmlLoader.load(input);
                JasperReport report = JasperCompileManager.compileReport(design);
                String reporttype = null;

                //Report Type
                if (request.getParameter("ReportType") == null || request.getParameter("ReportType").length() <= 0) {
                    reporttype = "pdf";
                } else {
                    reporttype = String.valueOf(request.getParameter("ReportType"));
                }


                //    po_no=String.valueOf(request.getParameter("PO_NO"));

                //  System.out.println("doc_id="+doc_id);
                Map parameters = new HashMap();
                //parameters.put("PO_No", po_no);
                System.out.println("OrgId--"+org_id);
                parameters.put("OrgId", org_id);
                System.out.println("sloc--"+sloc_id);
                parameters.put("SlocId", sloc_id);
                System.out.println("cld--"+cld_id);
                parameters.put("CldId", cld_id);
                parameters.put("DocId", doc_id);
                System.out.println("Path--"+path);
                parameters.put("Path", path);
                JasperPrint jasperPrint = null;
                jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
                OutputStream ouputStream = response.getOutputStream();
                JRExporter exporter = null;
                if (reporttype.equalsIgnoreCase("pdf")) {
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "attachement; filename=\"RateContractPurchaseOrder.pdf\"");
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                } else if (reporttype.equalsIgnoreCase("xls")) {
                    response.setContentType("application/xls");
                    response.setHeader("Content-Disposition", "attachement; filename=\"RateContractPurchaseOrder.xls\"");
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                }
                try {
                    exporter.exportReport();
                } catch (JRException e) {
                    throw new ServletException(e);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                finally {
                    if (ouputStream != null) {
                        try {
                            ouputStream.flush();
                            ouputStream.close();

                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                            throw (ex);
                        }
                    }
                }
            }
//------Rate Contract Purchase Order Itemwise
                        if (requestId.equals("gl16")) {
                //String po_no = String.valueOf(request.getParameter("PO_NO"));


                InputStream input = new FileInputStream(new File(path+"RateContractPOItemWise.jrxml"));
                JasperDesign design = JRXmlLoader.load(input);
                JasperReport report = JasperCompileManager.compileReport(design);
                String reporttype = null;

                //Report Type
                if (request.getParameter("ReportType") == null || request.getParameter("ReportType").length() <= 0) {
                    reporttype = "pdf";
                } else {
                    reporttype = String.valueOf(request.getParameter("ReportType"));
                }


                //    po_no=String.valueOf(request.getParameter("PO_NO"));

                //  System.out.println("doc_id="+doc_id);
                Map parameters = new HashMap();
                //parameters.put("PO_No", po_no);
                System.out.println("OrgId--"+org_id);
                parameters.put("OrgId", org_id);
                System.out.println("sloc--"+sloc_id);
                parameters.put("SlocId", sloc_id);
                System.out.println("cld--"+cld_id);
                parameters.put("CldId", cld_id);
                parameters.put("DocId", doc_id);
                System.out.println("Path--"+path);
                parameters.put("Path", path);
                JasperPrint jasperPrint = null;
                jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
                OutputStream ouputStream = response.getOutputStream();
                JRExporter exporter = null;
                if (reporttype.equalsIgnoreCase("pdf")) {
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "attachement; filename=\"RateContractPurchaseOrderItemWise.pdf\"");
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                } else if (reporttype.equalsIgnoreCase("xls")) {
                    response.setContentType("application/xls");
                    response.setHeader("Content-Disposition", "attachement; filename=\"RateContractPurchaseOrderItemWise.xls\"");
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                }
                try {
                    exporter.exportReport();
                } catch (JRException e) {
                    throw new ServletException(e);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                finally {
                    if (ouputStream != null) {
                        try {
                            ouputStream.flush();
                            ouputStream.close();

                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                            throw (ex);
                        }
                    }
                }
            }
                        //----------------Open Order Purchase Order Report
                        if (requestId.equals("gl17")) {
                        //String po_no = String.valueOf(request.getParameter("PO_NO"));


                        InputStream input = new FileInputStream(new File(path+"OpenOrderPO.jrxml"));
                        JasperDesign design = JRXmlLoader.load(input);
                        JasperReport report = JasperCompileManager.compileReport(design);
                        String reporttype = null;

                        //Report Type
                        if (request.getParameter("ReportType") == null || request.getParameter("ReportType").length() <= 0) {
                        reporttype = "pdf";
                        } else {
                        reporttype = String.valueOf(request.getParameter("ReportType"));
                        }


                        //    po_no=String.valueOf(request.getParameter("PO_NO"));

                        //  System.out.println("doc_id="+doc_id);
                        Map parameters = new HashMap();
                        //parameters.put("PO_No", po_no);
                        System.out.println("OrgId--"+org_id);
                        parameters.put("OrgId", org_id);
                        System.out.println("sloc--"+sloc_id);
                        parameters.put("SlocId", sloc_id);
                        System.out.println("cld--"+cld_id);
                        parameters.put("CldId", cld_id);
                        parameters.put("DocId", doc_id);
                        System.out.println("Path--"+path);
                        parameters.put("Path", path);
                        JasperPrint jasperPrint = null;
                        jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
                        OutputStream ouputStream = response.getOutputStream();
                        JRExporter exporter = null;
                        if (reporttype.equalsIgnoreCase("pdf")) {
                        response.setContentType("application/pdf");
                        response.setHeader("Content-Disposition", "attachement; filename=\"OpenOrderPurchaseOrder.pdf\"");
                        exporter = new JRPdfExporter();
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                        } else if (reporttype.equalsIgnoreCase("xls")) {
                        response.setContentType("application/xls");
                        response.setHeader("Content-Disposition", "attachement; filename=\"OpenOrderPurchaseOrder.xls\"");
                        exporter = new JRPdfExporter();
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                        }
                        try {
                        exporter.exportReport();
                        } catch (JRException e) {
                        throw new ServletException(e);
                        } catch (Exception ex) {
                        ex.printStackTrace();
                        }

                        finally {
                        if (ouputStream != null) {
                        try {
                            ouputStream.flush();
                            ouputStream.close();

                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                            throw (ex);
                        }
                        }
                        }
                        }
                        //------Open Order Item Wise Purchase Order---------------
                        if (requestId.equals("gl18")) {
                        //String po_no = String.valueOf(request.getParameter("PO_NO"));


                        InputStream input = new FileInputStream(new File(path+"OpenOrderPOItemWise.jrxml"));
                        JasperDesign design = JRXmlLoader.load(input);
                        JasperReport report = JasperCompileManager.compileReport(design);
                        String reporttype = null;

                        //Report Type
                        if (request.getParameter("ReportType") == null || request.getParameter("ReportType").length() <= 0) {
                        reporttype = "pdf";
                        } else {
                        reporttype = String.valueOf(request.getParameter("ReportType"));
                        }


                        //    po_no=String.valueOf(request.getParameter("PO_NO"));

                        //  System.out.println("doc_id="+doc_id);
                        Map parameters = new HashMap();
                        //parameters.put("PO_No", po_no);
                        System.out.println("OrgId--"+org_id);
                        parameters.put("OrgId", org_id);
                        System.out.println("sloc--"+sloc_id);
                        parameters.put("SlocId", sloc_id);
                        System.out.println("cld--"+cld_id);
                        parameters.put("CldId", cld_id);
                        parameters.put("DocId", doc_id);
                        System.out.println("Path--"+path);
                        parameters.put("Path", path);
                        JasperPrint jasperPrint = null;
                        jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
                        OutputStream ouputStream = response.getOutputStream();
                        JRExporter exporter = null;
                        if (reporttype.equalsIgnoreCase("pdf")) {
                        response.setContentType("application/pdf");
                        response.setHeader("Content-Disposition", "attachement; filename=\"OpenOrderItemWisePurchaseOrder.pdf\"");
                        exporter = new JRPdfExporter();
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                        } else if (reporttype.equalsIgnoreCase("xls")) {
                        response.setContentType("application/xls");
                        response.setHeader("Content-Disposition", "attachement; filename=\"OpenOrderItemWisePurchaseOrder.xls\"");
                        exporter = new JRPdfExporter();
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                        }
                        try {
                        exporter.exportReport();
                        } catch (JRException e) {
                        throw new ServletException(e);
                        } catch (Exception ex) {
                        ex.printStackTrace();
                        }

                        finally {
                        if (ouputStream != null) {
                        try {
                            ouputStream.flush();
                            ouputStream.close();

                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                            throw (ex);
                        }
                        }
                        }
                        }

            //-----------------------------------RFQ-----------------------------------------------


            else if (requestId.equals("gl2") || requestId.equals("gl3")) {
                String reqNo = request.getParameter("rfqId");
                String suplier = request.getParameter("supplierId");
                System.out.println("suplier " + suplier);

                InputStream input = new FileInputStream(new File(path+"RFQ.jrxml"));
                JasperDesign design = JRXmlLoader.load(input);
                JasperReport report = JasperCompileManager.compileReport(design);
                String reporttype = null;

                //Report Type
                if (request.getParameter("ReportType") == null || request.getParameter("ReportType").length() <= 0) {
                    reporttype = "pdf";
                } else {
                    reporttype = String.valueOf(request.getParameter("ReportType"));
                }
//                String path = "c:/";


                Map<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("rfqNo", reqNo);
                parameters.put("orgId", org_id);
                parameters.put("slocId", sloc_id);
                parameters.put("cldId", cld_id);
                System.out.println("Path--"+path);
                parameters.put("Path", path);
                if (suplier == null || suplier.length() <= 0) {
                    parameters.put("supplierId", null);
                } else {
                    parameters.put("supplierId", Integer.valueOf(suplier));
                }
                JasperPrint jasperPrint = null;
                jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
                OutputStream ouputStream = response.getOutputStream();
                JRExporter exporter = null;
                if (reporttype.equalsIgnoreCase("pdf")) {
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "attachement; filename=\"RFQ.pdf\"");
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                } else if (reporttype.equalsIgnoreCase("xls")) {
                    response.setContentType("application/xls");
                    response.setHeader("Content-Disposition", "attachement; filename=\"RFQ.xls\"");
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                }
                try {
                    exporter.exportReport();
                } catch (JRException e) {
                    throw new ServletException(e);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                finally {
                    if (ouputStream != null) {
                        try {
                            ouputStream.flush();
                            ouputStream.close();

                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                            throw (ex);
                        }
                    }
                }
            }


            //-----------------------------------------------Quotation------------------
            else if (requestId.equals("gl4")) {
                String quot_no = request.getParameter("quotNo");
                String suplier = request.getParameter("supplierId");
                System.out.println("quot " + quot_no);

                InputStream input = new FileInputStream(new File(path+"Quotation.jrxml"));
                JasperDesign design = JRXmlLoader.load(input);
                JasperReport report = JasperCompileManager.compileReport(design);
                String reporttype = null;

                //Report Type
                if (request.getParameter("ReportType") == null || request.getParameter("ReportType").length() <= 0) {
                    reporttype = "pdf";
                } else {
                    reporttype = String.valueOf(request.getParameter("ReportType"));
                }
//                String path = "c:/";


                Map<String, Object> parameters = new HashMap<String, Object>();
                System.out.println("quotno"+quot_no);
                parameters.put("DocId", quot_no);
                parameters.put("OrgId", org_id);
                parameters.put("SlocId", sloc_id);
                parameters.put("CldId", cld_id);
                System.out.println("Path--"+path);
                parameters.put("Path", path);
                if (suplier == null || suplier.length() <= 0) {
                    parameters.put("supplierId", null);
                } else {
                    parameters.put("supplierId", Integer.valueOf(suplier));
                }
                JasperPrint jasperPrint = null;
                jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
                OutputStream ouputStream = response.getOutputStream();
                JRExporter exporter = null;
                if (reporttype.equalsIgnoreCase("pdf")) {
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "attachement; filename=\"Quotation.pdf\"");
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                } else if (reporttype.equalsIgnoreCase("xls")) {
                    response.setContentType("application/xls");
                    response.setHeader("Content-Disposition", "attachement; filename=\"Quotation.xls\"");
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                }
                try {
                    exporter.exportReport();
                } catch (JRException e) {
                    throw new ServletException(e);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                finally {
                    if (ouputStream != null) {
                        try {
                            ouputStream.flush();
                            ouputStream.close();

                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                            throw (ex);
                        }
                    }
                }
            }
        
        // List Of quotation---------------------------------------------------------------
        
        else if (requestId.equals("gl13")) {
            System.out.println("list of quotation-----");
            String quot_no = request.getParameter("quotNo");
            String suplier = request.getParameter("supplierId");
            System.out.println("quot " + quot_no);

            InputStream input = new FileInputStream(new File(path+"ListOfQuotation.jrxml"));
            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design);
            String reporttype = null;

            //Report Type
            if (request.getParameter("ReportType") == null || request.getParameter("ReportType").length() <= 0) {
                reporttype = "pdf";
            } else {
                reporttype = String.valueOf(request.getParameter("ReportType"));
            }
//            String path = "c:/";


            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("DocId", quot_no);
            parameters.put("OrgId", org_id);
            parameters.put("SlocId", sloc_id);
            parameters.put("CldId", cld_id);
            System.out.println("Path--"+path);
            parameters.put("Path", path);
            if (suplier == null || suplier.length() <= 0) {
                parameters.put("EoId", null);
            } else {
                parameters.put("EoId", Integer.valueOf(suplier));
            }
            JasperPrint jasperPrint = null;
            jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();
            JRExporter exporter = null;
            if (reporttype.equalsIgnoreCase("pdf")) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachement; filename=\"ListOfQuotation.pdf\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if (reporttype.equalsIgnoreCase("xls")) {
                response.setContentType("application/xls");
                response.setHeader("Content-Disposition", "attachement; filename=\"ListOfQuotation.xls\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            }
            try {
                exporter.exportReport();
            } catch (JRException e) {
                throw new ServletException(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            finally {
                if (ouputStream != null) {
                    try {
                        ouputStream.flush();
                        ouputStream.close();

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                        throw (ex);
                    }
                }
            }
        }

            // Quotation Analysis ----------------------------------------------

            else if (requestId.equals("gl5")) {
                Integer EoId ;
                String EvalId;
               
                if(request.getParameter("EoId")!=null){
                    if(request.getParameter("EoId").equals("")){
                        EoId=null;
                    }else{
                        EoId = Integer.parseInt(request.getParameter("EoId").toString());
                    }
                }
                else{
                    EoId=null;
                }
               
                if(request.getParameter("EvalId")!=null){
                    if(request.getParameter("EvalId").equals("")){
                        EvalId=null;
                    }else{
                        EvalId = request.getParameter("EvalId").toString();
                    }
                }
                else{
                    EvalId=null;
                }
       

                InputStream input = new FileInputStream(new File(path+"QuotationAnalysis.jrxml"));
                JasperDesign design = JRXmlLoader.load(input);
                JasperReport report = JasperCompileManager.compileReport(design);
                String reporttype = null;

                //Report Type
                if (request.getParameter("ReportType") == null || request.getParameter("ReportType").length() <= 0) {
                    reporttype = "pdf";
                } else {
                    reporttype = String.valueOf(request.getParameter("ReportType"));
                }

                Map parameters = new HashMap();
                parameters.put("EoId", EoId);
                parameters.put("orgId", org_id);
                parameters.put("slocId", sloc_id);
                parameters.put("cldId", cld_id);
                parameters.put("EvalId", EvalId);
                System.out.println("Path--"+path);
                parameters.put("Path", path);
                JasperPrint jasperPrint = null;
                jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
                OutputStream ouputStream = response.getOutputStream();
                JRExporter exporter = null;
                if (reporttype.equalsIgnoreCase("pdf")) {
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "attachement; filename=\"QuotationAnalysis.pdf\"");
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                } else if (reporttype.equalsIgnoreCase("xls")) {
                    response.setContentType("application/xls");
                    response.setHeader("Content-Disposition", "attachement; filename=\"QuotationAnalysis.xls\"");
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                }
                try {
                    exporter.exportReport();
                } catch (JRException e) {
                    throw new ServletException(e);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                finally {
                    if (ouputStream != null) {
                        try {
                            ouputStream.flush();
                            ouputStream.close();

                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                            throw (ex);
                        }
                    }
                }
            }


        // quotation analysis consolidate----------------------------------------
        
        else if (requestId.equals("gl14")) {
            
            String eval_id;
            String FromDate;
            String ToDate;
           
            if (request.getParameter("FromDate") != null) {
                if (request.getParameter("FromDate").equals("")) {
                    FromDate = null;
                } else {
                    FromDate = request.getParameter("FromDate");
                }
            } else {
                FromDate = null;
            }

            if (request.getParameter("ToDate") != null) {
                if (request.getParameter("ToDate").equals("")) {
                    ToDate = null;
                } else {
                    ToDate = request.getParameter("ToDate");
                }
            } else {
                ToDate = null;
            }

           
           
            if(request.getParameter("eval_id")!=null){
                if(request.getParameter("eval_id").equals("")){
                    eval_id=null;
                }else{
                    eval_id = request.getParameter("eval_id").toString();
                }
            }
            else{
                eval_id=null;
            }
        

            InputStream input = new FileInputStream(new File(path+"Quotation_Analysis_Consolidated.jrxml"));
            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design);
            String reporttype = null;

            //Report Type
            if (request.getParameter("ReportType") == null || request.getParameter("ReportType").length() <= 0) {
                reporttype = "pdf";
            } else {
                reporttype = String.valueOf(request.getParameter("ReportType"));
            }

            Map parameters = new HashMap();
        
            parameters.put("orgId", org_id);
            parameters.put("slocId", sloc_id);
            parameters.put("cldId", cld_id);
            parameters.put("eval_id", eval_id);
            parameters.put("FromDate",FromDate);
            parameters.put("ToDate",ToDate);
            System.out.println("Path--"+path);
            parameters.put("Path", path);
            JasperPrint jasperPrint = null;
            jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();
            JRExporter exporter = null;
            if (reporttype.equalsIgnoreCase("pdf")) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachement; filename=\"Quotation_Analysis_Consolidated.pdf\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if (reporttype.equalsIgnoreCase("xls")) {
                response.setContentType("application/xls");
                response.setHeader("Content-Disposition", "attachement; filename=\"Quotation_Analysis_Consolidated.xls\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            }
            try {
                exporter.exportReport();
            } catch (JRException e) {
                throw new ServletException(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            finally {
                if (ouputStream != null) {
                    try {
                        ouputStream.flush();
                        ouputStream.close();

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                        throw (ex);
                    }
                }
            }
        }

        





            // CASH Purchase Order---------------------------

            else if (requestId.equals("gl6")) {
                String FromDate;
                String ToDate;
                Integer CoaId;
               
                String DocId;
               
                if(request.getParameter("DocId")!=null){
                    if(request.getParameter("DocId").equals("")){
                        DocId=null;
                    }else{
                    DocId=request.getParameter("DocId");
                }
                }
                else{
                    DocId=null;
                }
               
                if (request.getParameter("CoaId") != null) {
                    if(request.getParameter("CoaId").equals("")){
                        CoaId = null;
                    }
                    else{
                    CoaId = Integer.parseInt(request.getParameter("CoaId").toString());
                        System.out.println("CoaId in CPO--"+CoaId);
                }
                }
                else {
                    CoaId = null;
                }
                if (request.getParameter("FromDate") != null) {
                    if (request.getParameter("FromDate").equals("")) {
                        FromDate = null;
                    } else {
                        FromDate = request.getParameter("FromDate");
                    }
                } else {
                    FromDate = null;
                }

                if (request.getParameter("ToDate") != null) {
                    if (request.getParameter("ToDate").equals("")) {
                        ToDate = null;
                    } else {
                        ToDate = request.getParameter("ToDate");
                    }
                } else {
                    ToDate = null;
                }


                InputStream input = new FileInputStream(new File(path+"Cash_Purchase_Order.jrxml"));
                JasperDesign design = JRXmlLoader.load(input);
                JasperReport report = JasperCompileManager.compileReport(design);
                String reporttype = null;

                //Report Type
                if (request.getParameter("ReportType") == null || request.getParameter("ReportType").length() <= 0) {
                    reporttype = "pdf";
                } else {
                    reporttype = String.valueOf(request.getParameter("ReportType"));
                }

                Map parameters = new HashMap();
                parameters.put("CoaId", CoaId);
                parameters.put("OrgId", org_id);
                parameters.put("SlocId", sloc_id);
                parameters.put("CldId", cld_id);
               
                parameters.put("DocId", DocId);
                System.out.println("Path--"+path);
                parameters.put("Path", path);
                //parameters.put("FromDate", FromDate);
                //parameters.put("ToDate", ToDate);

                JasperPrint jasperPrint = null;
                jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
                OutputStream ouputStream = response.getOutputStream();
                JRExporter exporter = null;
                if (reporttype.equalsIgnoreCase("pdf")) {
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "attachement; filename=\"Cash_Purchase_Order.pdf\"");
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                } else if (reporttype.equalsIgnoreCase("xls")) {
                    response.setContentType("application/xls");
                    response.setHeader("Content-Disposition", "attachement; filename=\"Cash_Purchase_Order.xls\"");
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                }
                try {
                    exporter.exportReport();
                } catch (JRException e) {
                    throw new ServletException(e);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                finally {
                    if (ouputStream != null) {
                        try {
                            ouputStream.flush();
                            ouputStream.close();

                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                            throw (ex);
                        }
                    }
                }
            }
     
     // CPO Summary-------------------------------------------------
     
     else if (requestId.equals("gl11")) {
         String FromDate;
         String ToDate;
         Integer CoaId;
         String DocId;
        
         if(request.getParameter("DocId")!=null){
             if(request.getParameter("DocId").equals("")){
                 DocId=null;
             }else{
             DocId=request.getParameter("DocId");
         }
         }
         else{
             DocId=null;
         }
        
         if (request.getParameter("CoaId") != null) {
             if(request.getParameter("CoaId").equals("")){
                 CoaId = null;
             }
             else{
             CoaId = Integer.parseInt(request.getParameter("CoaId").toString());
                 System.out.println("CoaId in CPO--"+CoaId);
         }
         }
         else {
             CoaId = null;
         }
         if (request.getParameter("FromDate") != null) {
             if (request.getParameter("FromDate").equals("")) {
                 FromDate = null;
             } else {
                 FromDate = request.getParameter("FromDate");
             }
         } else {
             FromDate = null;
         }

         if (request.getParameter("ToDate") != null) {
             if (request.getParameter("ToDate").equals("")) {
                 ToDate = null;
             } else {
                 ToDate = request.getParameter("ToDate");
             }
         } else {
             ToDate = null;
         }


         InputStream input = new FileInputStream(new File(path+"Cash_Purchase_Order_Summary.jrxml"));
         JasperDesign design = JRXmlLoader.load(input);
         JasperReport report = JasperCompileManager.compileReport(design);
         String reporttype = null;

         //Report Type
         if (request.getParameter("ReportType") == null || request.getParameter("ReportType").length() <= 0) {
             reporttype = "pdf";
         } else {
             reporttype = String.valueOf(request.getParameter("ReportType"));
         }

         Map parameters = new HashMap();
         parameters.put("CoaId", CoaId);
         parameters.put("OrgId", org_id);
         parameters.put("SlocId", sloc_id);
         parameters.put("CldId", cld_id);
        
         parameters.put("DocId", DocId);
         parameters.put("FromDate", FromDate);
         parameters.put("ToDate", ToDate);
         parameters.put("Path",path);

         JasperPrint jasperPrint = null;
         jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
         OutputStream ouputStream = response.getOutputStream();
         JRExporter exporter = null;
         if (reporttype.equalsIgnoreCase("pdf")) {
             response.setContentType("application/pdf");
             response.setHeader("Content-Disposition", "attachement; filename=\"Cash_Purchase_Order_Summary.pdf\"");
             exporter = new JRPdfExporter();
             exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
             exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
         } else if (reporttype.equalsIgnoreCase("xls")) {
             response.setContentType("application/xls");
             response.setHeader("Content-Disposition", "attachement; filename=\"Cash_Purchase_Order_Summary.xls\"");
             exporter = new JRPdfExporter();
             exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
             exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
         }
         try {
             exporter.exportReport();
         } catch (JRException e) {
             throw new ServletException(e);
         } catch (Exception ex) {
             ex.printStackTrace();
         }

         finally {
             if (ouputStream != null) {
                 try {
                     ouputStream.flush();
                     ouputStream.close();

                 } catch (IOException ex) {
                     System.out.println(ex.getMessage());
                     throw (ex);
                 }
             }
         }
     }
     
     
     
            // Suggested Order--------------
           
            else if (requestId.equals("gl8")) {
                String FromDate;
                String ToDate;
                Integer EoId;
                String DocId;
                String OrdPoDocId;
                String ItmId=null;
                if(request.getParameter("ItmId")!=null){
                    if(request.getParameter("ItmId").equals("")){
                        ItmId=null;
                    }else{
                    ItmId=request.getParameter("ItmId");
                }
                }
                else{
                    ItmId=null;
                }
               
                if(request.getParameter("OrdPoDocId")!=null){
                    if(request.getParameter("OrdPoDocId").equals("")){
                        OrdPoDocId=null;
                    }else{
                    OrdPoDocId=request.getParameter("OrdPoDocId");
                }
                }
                else{
                    OrdPoDocId=null;
                }
               
                if(request.getParameter("DocId")!=null){
                    if(request.getParameter("DocId").equals("")){
                        DocId=null;
                    }else{
                    DocId=request.getParameter("DocId");
                }
                }
                else{
                    DocId=null;
                }
               
                if (request.getParameter("EoId") != null) {
                    if(request.getParameter("EoId").equals("")){
                        EoId = null;
                    }else{
                    EoId = Integer.parseInt(request.getParameter("EoId").toString());
                }
                }
                else {
                    EoId = null;
                }
                if (request.getParameter("FromDate") != null) {
                    if (request.getParameter("FromDate").equals("")) {
                        FromDate = null;
                    } else {
                       String fromDate = request.getParameter("FromDate");
                        Timestamp frm=new Timestamp(fromDate);
                        FromDate=frm.dateValue().toString();
                    }
                } else {
                    FromDate = null;
                }

                if (request.getParameter("ToDate") != null) {
                    if (request.getParameter("ToDate").equals("")) {
                        ToDate = null;
                    } else {
                        String ToDt = request.getParameter("ToDate");
                        Timestamp to=new Timestamp(ToDt);
                       ToDate= to.dateValue().toString();
                    }
                } else {
                    ToDate = null;
                }


                InputStream input = new FileInputStream(new File(path+"Suggested_Order_Report.jrxml"));
                JasperDesign design = JRXmlLoader.load(input);
                JasperReport report = JasperCompileManager.compileReport(design);
                String reporttype = null;

                //Report Type
                if (request.getParameter("ReportType") == null || request.getParameter("ReportType").length() <= 0) {
                    reporttype = "pdf";
                } else {
                    reporttype = String.valueOf(request.getParameter("ReportType"));
                }

                Map parameters = new HashMap();
                parameters.put("EoId", EoId);
                parameters.put("orgId", org_id);
                parameters.put("slocId", sloc_id);
                parameters.put("cldId", cld_id);
                parameters.put("ItmId", ItmId);
                parameters.put("OrdPoDocId", OrdPoDocId);
                parameters.put("DocId", DocId);
                parameters.put("FromDate", FromDate);
                parameters.put("ToDate", ToDate);
                System.out.println("Path--"+path);
                parameters.put("Path", path);
                System.out.println("frmdt-"+FromDate+"todate-"+ToDate+"itmid-"+ItmId);

                JasperPrint jasperPrint = null;
                jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
                OutputStream ouputStream = response.getOutputStream();
                JRExporter exporter = null;
                if (reporttype.equalsIgnoreCase("pdf")) {
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "attachement; filename=\"Suggested_Order_Report.pdf\"");
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                } else if (reporttype.equalsIgnoreCase("xls")) {
                    response.setContentType("application/xls");
                    response.setHeader("Content-Disposition", "attachement; filename=\"Suggested_Order_Report.xls\"");
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                }
                try {
                    exporter.exportReport();
                } catch (JRException e) {
                    throw new ServletException(e);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                finally {
                    if (ouputStream != null) {
                        try {
                            ouputStream.flush();
                            ouputStream.close();

                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                            throw (ex);
                        }
                    }
                }
            }
           
     // Suggested Oredr Supplierwise-------------------
     else if (requestId.equals("gl7")) {
         String FromDate;
         String ToDate;
         Integer EoId;
         String DocId;
         String OrdPoDocId;
         String ItmId=null;
         if(request.getParameter("ItmId")!=null){
             if(request.getParameter("ItmId").equals("")){
                 ItmId=null;
             }else{
             ItmId=request.getParameter("ItmId");
         }
         }
         else{
             ItmId=null;
         }
        
         if(request.getParameter("OrdPoDocId")!=null){
             if(request.getParameter("OrdPoDocId").equals("")){
                 OrdPoDocId=null;
             }else{
             OrdPoDocId=request.getParameter("OrdPoDocId");
         }
         }
         else{
             OrdPoDocId=null;
         }
        
         if(request.getParameter("DocId")!=null){
             if(request.getParameter("DocId").equals("")){
                 DocId=null;
             }else{
             DocId=request.getParameter("DocId");
         }
         }
         else{
             DocId=null;
         }
        
         if (request.getParameter("EoId") != null) {
             if(request.getParameter("EoId").equals("")){
                 EoId = null;
             }else{
             EoId = Integer.parseInt(request.getParameter("EoId").toString());
         }
         }
         else {
             EoId = null;
         }
         if (request.getParameter("FromDate") != null) {
             if (request.getParameter("FromDate").equals("")) {
                 FromDate = null;
             } else {
                String fromDate = request.getParameter("FromDate");
                 Timestamp frm=new Timestamp(fromDate);
                 FromDate=frm.dateValue().toString();
             }
         } else {
             FromDate = null;
         }

         if (request.getParameter("ToDate") != null) {
             if (request.getParameter("ToDate").equals("")) {
                 ToDate = null;
             } else {
                 String ToDt = request.getParameter("ToDate");
                 Timestamp to=new Timestamp(ToDt);
                ToDate= to.dateValue().toString();
             }
         } else {
             ToDate = null;
         }


         InputStream input = new FileInputStream(new File(path+"Suggested_Order_Report_SupplierWise.jrxml"));
         JasperDesign design = JRXmlLoader.load(input);
         JasperReport report = JasperCompileManager.compileReport(design);
         String reporttype = null;

         //Report Type
         if (request.getParameter("ReportType") == null || request.getParameter("ReportType").length() <= 0) {
             reporttype = "pdf";
         } else {
             reporttype = String.valueOf(request.getParameter("ReportType"));
         }

         Map parameters = new HashMap();
         parameters.put("EoId", EoId);
         parameters.put("orgId", org_id);
         parameters.put("slocId", sloc_id);
         parameters.put("cldId", cld_id);
         parameters.put("ItmId", ItmId);
         parameters.put("OrdPoDocId", OrdPoDocId);
         parameters.put("DocId", DocId);
         parameters.put("FromDate", FromDate);
         parameters.put("ToDate", ToDate);
         System.out.println("Path--"+path);
         parameters.put("Path", path);
         System.out.println("frmdt-"+FromDate+"todate-"+ToDate+"itmid-"+ItmId);

         JasperPrint jasperPrint = null;
         jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
         OutputStream ouputStream = response.getOutputStream();
         JRExporter exporter = null;
         if (reporttype.equalsIgnoreCase("pdf")) {
             response.setContentType("application/pdf");
             response.setHeader("Content-Disposition", "attachement; filename=\"Suggested_Order_Report_SupplierWise.pdf\"");
             exporter = new JRPdfExporter();
             exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
             exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
         } else if (reporttype.equalsIgnoreCase("xls")) {
             response.setContentType("application/xls");
             response.setHeader("Content-Disposition", "attachement; filename=\"Suggested_Order_Report_SupplierWise.xls\"");
             exporter = new JRPdfExporter();
             exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
             exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
         }
         try {
             exporter.exportReport();
         } catch (JRException e) {
             throw new ServletException(e);
         } catch (Exception ex) {
             ex.printStackTrace();
         }

         finally {
             if (ouputStream != null) {
                 try {
                     ouputStream.flush();
                     ouputStream.close();

                 } catch (IOException ex) {
                     System.out.println(ex.getMessage());
                     throw (ex);
                 }
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