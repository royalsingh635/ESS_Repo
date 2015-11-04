package slssalesinvoiceapp.model.module.helper;

import adf.utils.ebiz.EbizParams;

import adf.utils.model.ADFModelUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.ServletException;

import javax.sql.DataSource;

import mailservice.MailFactory;
import mailservice.MailService;

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

import oracle.jbo.JboException;
import oracle.jbo.Row;

import slssalesinvoiceapp.model.module.service.SlsSalesInvoiceAppAMImpl;
import slssalesinvoiceapp.model.module.view.SlsInvVOImpl;

public class SendMailHelper {
    public SendMailHelper() {
        super();
    }

    /**
     * Method to generate Report
     * @param am
     * @return
     */
    public static File generateReport(SlsSalesInvoiceAppAMImpl am) {
        File pdfFile = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/SLSDS");
            Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT DISTINCT SRVR_LOC_AS_RPT_PATH FROM APP.APP$SERVR$LOC");
            ResultSet rs = ps.executeQuery();
            String path = null;
            while (rs.next()) {
                path = rs.getString(1);
            }
            path = path + "SLS/";
            Row currInv = am.getSlsInv().getCurrentRow();
            Object invTypeO = currInv.getAttribute("InvType");
            Integer invType = (invTypeO == null ? -1 : (Integer) invTypeO);
            String reportName = null;
            if (invType.equals(795)) {
                reportName = "SLS_Inv_DetailReport_Service.jrxml";
                /* } else if (exciseTradeOrg.equalsIgnoreCase("Y")) {
                reportName = "SLS_Inv_DetailReport_Excise";
            */
            } else {
                reportName = "SLS_Inv_DetailReport.jrxml";
            }
            File dir = new File(path + "tempReport");
            if (dir.exists()) {
                System.out.println("A folder with name " + path + "tempReport is already exist in the path");
            } else {
                dir.mkdir();
            }
            //Org=02&cldid=0000&slocid=1&hoOrgid=02&DocId=0000.01.02.0007.05aq.00.1UHyy3Eesx
            System.out.println("Report : " + path + reportName);
            InputStream input = new FileInputStream(new File(path + reportName));
            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design);
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("OrgId", currInv.getAttribute("OrgId"));
            parameters.put("CldId", currInv.getAttribute("CldId"));
            parameters.put("SlocId", currInv.getAttribute("SlocId"));
            parameters.put("HoOrgId", currInv.getAttribute("HoOrgId"));
            parameters.put("DocId", currInv.getAttribute("DocId"));
            parameters.put("Path", path);
            parameters.put("invType", invType);
            JasperPrint jasperPrint = null;
            jasperPrint = JasperFillManager.fillReport(report, parameters, con);
            pdfFile =
                new File(path + "tempReport/" + currInv.getAttribute("DispDocId") + "_" + currInv.getAttribute("FyId") +
                         ".pdf");
            JRExporter exporter = null;
            if (true) {
                exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE, pdfFile);
            }
            try {
                exporter.exportReport();
            } catch (JRException e) {
                throw new ServletException(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return pdfFile;
    }

    /**
     *Method to send mail on Invoice Approval
     * @throws Exception
     */
    public static void sendMail(SlsSalesInvoiceAppAMImpl am) {
        // String shipDocId = (String)getSlsShipmnt1().getCurrentRow().getAttribute("DocId");
        if (isMailNeedToSend(am)) {
            SlsInvVOImpl siVo = am.getSlsInv();
            Row siRow = siVo.getCurrentRow();
            Object mailId = null;
            try {
                mailId = ADFModelUtils.callFunction(am, new StringBuilder("SLS.FN_GET_EO_MAIL_FRM_EO_ID(?,?,?,?)"), new Object[] {
                                                    EbizParams.GLBL_APP_SERV_LOC(), EbizParams.GLBL_APP_CLD_ID(),
                                                    EbizParams.GLBL_HO_ORG_ID(), siRow.getAttribute("EoId")
                }, Types.VARCHAR);
            } catch (Exception e) {
                System.out.println("Error in SLS.FN_GET_EO_MAIL_FRM_EO_ID from ::::Error in gettin mail ID ");
                // TODO: Add catch code
                e.printStackTrace();
            }
            if (mailId == null) {
                ADFModelUtils.showFormattedFacesMessage("Email Id is not defined for Customer.",
                                                        "Email Id of the Customer is not defined in Personal Details.<br /> We cannot send mail without an email Id.<br /><br /> Please define Email Id for the Current Entity.",
                                                        FacesMessage.SEVERITY_INFO);
            } else {
                String receiver = mailId == null ? null : mailId.toString();
                try {
                    HashMap<String, String> config = getConfig("APP.PR_ALRT_GET_MAIL_CFG (?,?,?,?,?,?)", am);
                    MailFactory fc = new MailFactory();
                    fc.setAsSender();
                    MailService ms =
                        fc.getMailService(config.get("Domain"), config.get("Port"), config.get("Security"));
                    ms.setSender(config.get("From"));
                    // ms.setReciever(new String[] { });
                    ms.setReciever(receiver);
                    ms.setSubject("Invoice No." + siRow.getAttribute("DispDocId") + " Generated");
                    StringBuilder message = new StringBuilder("");
                    message.append("Dear Mr/Ms. ");
                    message.append(EbizParams.getEoDescFrmEoId(am, (Integer) siRow.getAttribute("EoId")));
                    message.append(",");
                    message.append("<br /><br /><br />Sales Invoice No.<b>");
                    message.append(siRow.getAttribute("DispDocId"));
                    message.append("</b> have been generated");
                    message.append("<br /><br/>Please find the Invoice Attached to this mail.<br /><br /><br /><br />");
                    message.append("<span style='color:gray;font-size:8px;'>****************************This is a system generated mail*****************************</span>");
                    ms.addLine(message.toString());
                    ms.addFile(generateReport(am));
                    ms.send(config.get("From"), config.get("Password"));
                    ADFModelUtils.showFormattedFacesMessage("Mail Sent Successfully.",
                                                            "Mail along with the Invoice Report have been sent Successfully!",
                                                            FacesMessage.SEVERITY_INFO);
                } catch (Exception e) {
                    ADFModelUtils.showFormattedFacesMessage("There have been some problem in Sending Mail.",
                                                            e.getMessage(), FacesMessage.SEVERITY_ERROR);
                }
            }

        }
    }

    /**
     * Method to check mail need to send on approval or not
     * FN_CHK_MAIL_SND(
     * P_CLD       VARCHAR2,
     * SLOC_ID     VARCHAR2,
     * P_ORG_ID    VARCHAR2,
     * P_HO_ORG_ID VARCHAR2,
     * P_DOC_ID    NUMBER)
     */
    protected static Boolean isMailNeedToSend(SlsSalesInvoiceAppAMImpl am) {
        Boolean result = false;
        Object r = ADFModelUtils.callFunction(am, new StringBuilder("app.FN_CHK_MAIL_SND(?,?,?,?,?)"), new Object[] {
                                              EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(),
                                              EbizParams.GLBL_APP_USR_ORG(), EbizParams.GLBL_HO_ORG_ID(), 21504
        }, Types.VARCHAR);
        result = r == null ? false : ("N".equalsIgnoreCase(r.toString()) ? false : true);
        return result;
    }

    /**
     *Method to call procedure for mail configuration
     * @param stmt
     * @return
     * @throws Exception
     */
    public static HashMap<String, String> getConfig(String stmt, SlsSalesInvoiceAppAMImpl am) throws Exception {
        try {

            HashMap<String, String> map = new HashMap<String, String>();
            CallableStatement stForSet = am.getDBTransaction().createCallableStatement("begin  " + stmt + ";end;", 0);
            stForSet.setObject(1, null);
            stForSet.registerOutParameter(2, Types.VARCHAR);
            stForSet.registerOutParameter(3, Types.VARCHAR);
            stForSet.registerOutParameter(4, Types.VARCHAR);
            stForSet.registerOutParameter(5, Types.VARCHAR);
            stForSet.registerOutParameter(6, Types.VARCHAR);
            stForSet.execute();
            map.put("Domain", stForSet.getString(2));
            map.put("Port", stForSet.getString(3));
            map.put("Security", stForSet.getString(4));
            map.put("From", stForSet.getString(5));
            map.put("Password", stForSet.getString(6));
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new JboException("Mail Configuration is not configured properly. Mail Configuration is needed to send mail.<br /><br />Please setup the mail configuration properly.");
        }
    }

}
