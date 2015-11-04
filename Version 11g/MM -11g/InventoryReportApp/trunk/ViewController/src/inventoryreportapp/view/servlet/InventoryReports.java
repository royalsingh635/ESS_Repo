package inventoryreportapp.view.servlet;

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

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import javax.naming.Context;
import javax.naming.InitialContext;

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
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.export.JRXhtmlExporter;


public class InventoryReports extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        String ChkSpwFlg = null;
        String SlsItmFlg = null;
        String PurItmFlg = null;
        String WipItmFlg = null;
        if (request.getParameter("ChkSpwFlg") != null) {
            System.out.println(request.getParameter("ChkSpwFlg"));
            if (request.getParameter("ChkSpwFlg").equalsIgnoreCase("true")) {
                ChkSpwFlg = "Y";
                System.out.println("checkflg is- true--" + ChkSpwFlg);
            } else if (request.getParameter("ChkSpwFlg").equalsIgnoreCase("false")) {
                ChkSpwFlg = "N";
                System.out.println("checkflg is- false--" + ChkSpwFlg);
            }
        } else {
            ChkSpwFlg = "N";
            System.out.println("checkflg is- null--" + ChkSpwFlg);
        }
        if (request.getParameter("WipItmFlg") != null) {
            if (request.getParameter("WipItmFlg").equalsIgnoreCase("true")) {
                WipItmFlg = "Y";
                System.out.println("wipitemflag is--true ---" + WipItmFlg);
            } else if (request.getParameter("WipItmFlg").equalsIgnoreCase("false")) {
                WipItmFlg = "N";
                System.out.println("wipitemflag is--false-" + WipItmFlg);
            }
        } else {
            WipItmFlg = "N";
            System.out.println("wipitemflag is--null-" + WipItmFlg);
        }
        if (request.getParameter("PurItmFlg") != null) {
            if (request.getParameter("PurItmFlg").equalsIgnoreCase("true")) {
                PurItmFlg = "Y";
                System.out.println("purchaseitemflag is- true--" + PurItmFlg);
            } else if (request.getParameter("PurItmFlg").equalsIgnoreCase("false")) {
                PurItmFlg = "N";
                System.out.println("purchaseitemflag is- false--" + PurItmFlg);
            }
        } else {
            PurItmFlg = "N";
            System.out.println("purchaseitemflag is- null--" + PurItmFlg);
        }
        if (request.getParameter("SlsItmFlg") != null) {
            if (request.getParameter("SlsItmFlg").equalsIgnoreCase("true")) {
                SlsItmFlg = "Y";
                System.out.println("slsitm flg is--true---" + SlsItmFlg);
            } else if (request.getParameter("SlsItmFlg").equalsIgnoreCase("false")) {
                SlsItmFlg = "N";
                System.out.println("slsitm flg is--false---" + SlsItmFlg);
            }
        } else {
            SlsItmFlg = "N";
            System.out.println("slsitm flg is--null---" + SlsItmFlg);
        }


        Connection conn = null;
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
                //System.out.println("Path is :" + rs.getString(1));
            }

            String reportName = request.getParameter("reportName");
            InputStream input = null;
            // InputStream input = new FileInputStream(new File("D:/Report/GateEntry.jrxml"));


            String reporttype = "pdf";

            String Org = String.valueOf(request.getParameter("OrgId"));
            reporttype = request.getParameter("reporttype");
            // System.out.println("Param report type="+reporttype);
            Integer Sloc = Integer.parseInt(request.getParameter("SlocId").toString());
            String Cld = String.valueOf(request.getParameter("CldId"));
            String doc;
            Integer Fy;
            String WhId;
            String FrmDate;
            String ToDate;
            String ItemId;
            String EoId;
            Integer IssueTyp;
            Integer RqmtAreaId;
            String MrsTxnId;
            Integer SupplierId;
            Integer RmdaType;
            String ReturnFlg;
            Integer QcChck;
            String QcStat;
            String LotId;
            String BinId;
            String MatStck;
            String DeflPrf;
            String StckCycl;
            String PrfNm;
            String type;
            Integer status;
            Integer srcType;
            Integer toType;
            String ItemGrpId;
            String retunOrgId;
            Integer StkTakeStat;
            String AuthStat;
            String pr_no;
            String prstatus;
            Integer EoIdSrc;
            Integer InvcType;


            if (request.getParameter("InvcType") != null) {
                if (request.getParameter("InvcType").equals("")) {
                    InvcType = null;
                    // System.out.println("Null InvcType");
                } else {
                    InvcType = Integer.parseInt((request.getParameter("InvcType")).toString());
                }
            } else {
                //System.out.println("Null InvcType");
                InvcType = null;
            }


            if (request.getParameter("EoIdSrc") != null) {
                if (request.getParameter("EoIdSrc").equals("")) {
                    EoIdSrc = null;
                    System.out.println("Null EoIdSrc");
                } else {
                    EoIdSrc = Integer.parseInt((request.getParameter("EoIdSrc")).toString());
                }
            } else {
                //System.out.println("Null EoIdSrc");
                EoIdSrc = null;
            }

            if (request.getParameter("prstatus") != null) {
                if (request.getParameter("prstatus").equals("")) {
                    prstatus = null;
                    //System.out.println("Null prstatus");
                } else {
                    prstatus = request.getParameter("prstatus").toString();
                }
            } else {
                // System.out.println("Null prstatus");
                prstatus = null;
            }


            if (request.getParameter("StkTakeStat") != null) {
                if (request.getParameter("StkTakeStat").equals("")) {
                    StkTakeStat = null;
                    // System.out.println("Null StkTakeStat");
                } else {
                    StkTakeStat = Integer.parseInt((request.getParameter("StkTakeStat")).toString());
                }
            } else {
                //System.out.println("Null StkTakeStat");
                StkTakeStat = null;
            }


            if (request.getParameter("AuthStat") != null) {
                if (request.getParameter("AuthStat").equals("")) {
                    AuthStat = null;
                    //System.out.println("Null AuthStat");
                } else {
                    AuthStat = request.getParameter("AuthStat").toString();
                }
            } else {
                //System.out.println("Null AuthStat");
                AuthStat = null;
            }


            if (request.getParameter("toType") != null) {
                if (request.getParameter("toType").equals("")) {
                    toType = null;
                    // System.out.println("Null toType id");
                } else {
                    toType = Integer.parseInt((request.getParameter("toType")).toString());
                }
            } else {
                // System.out.println("Null toType");
                toType = null;
            }


            if (request.getParameter("retunOrgId") != null) {
                if (request.getParameter("retunOrgId").equals("")) {
                    retunOrgId = null;
                    //  System.out.println("Null retunOrgId id");
                } else {
                    retunOrgId = request.getParameter("retunOrgId").toString();
                }
            } else {
                // System.out.println("Null retunOrgId");
                retunOrgId = null;
            }


            if (request.getParameter("ItemGrpId") != null) {
                if (request.getParameter("ItemGrpId").equals("")) {
                    ItemGrpId = null;
                    // System.out.println("Null ItemGrpId id");
                } else {
                    ItemGrpId = (request.getParameter("ItemGrpId")).toString();
                }
            } else {
                // System.out.println("Null ItemGrpId");
                ItemGrpId = null;
            }


            if (request.getParameter("srcType") != null) {
                if (request.getParameter("srcType").equals("")) {
                    srcType = null;
                    // System.out.println("Null srcType id");
                } else {
                    srcType = Integer.parseInt((request.getParameter("srcType")).toString());
                    //System.out.println(srcType);
                }
            } else {
                // System.out.println("Null srcType");
                srcType = null;
            }


            if (request.getParameter("status") != null) {
                if (request.getParameter("status").equals("")) {
                    status = null;
                    // System.out.println("Null status id");
                } else {
                    status = Integer.parseInt((request.getParameter("status")).toString());
                    // System.out.println(status);
                }
            } else {
                // System.out.println("Null type");
                status = null;
            }


            if (request.getParameter("type") != null) {
                if (request.getParameter("type").equals("")) {
                    type = null;
                    //  System.out.println("Null type id");
                } else {
                    type = (request.getParameter("type")).toString();
                    // System.out.println(type);
                }
            } else {
                // System.out.println("Null type");
                type = null;
            }


            if (request.getParameter("StckCycl") != null) {
                if (request.getParameter("StckCycl").equals("")) {
                    StckCycl = null;
                    //  System.out.println("Null PrfNm id");
                } else {
                    StckCycl = (request.getParameter("StckCycl")).toString();
                    //  System.out.println(StckCycl);
                }
            } else {
                // System.out.println("Null StckCycl");
                StckCycl = null;
            }


            if (request.getParameter("MatStck") != null) {
                if (request.getParameter("MatStck").equals("")) {
                    MatStck = null;
                    //  System.out.println("Null PrfNm id");
                } else {
                    MatStck = (request.getParameter("MatStck")).toString();
                    System.out.println(MatStck);
                }
            } else {
                // System.out.println("Null PrfNm");
                MatStck = null;
            }


            if (request.getParameter("PrfNm") != null) {
                if (request.getParameter("PrfNm").equals("")) {
                    PrfNm = null;
                    //  System.out.println("Null PrfNm id");
                } else {
                    PrfNm = (request.getParameter("PrfNm")).toString();
                    //  System.out.println(PrfNm);
                }
            } else {
                //  System.out.println("Null PrfNm");
                PrfNm = null;
            }


            if (request.getParameter("DeflPrf") != null) {
                if (request.getParameter("DeflPrf").equals("")) {
                    DeflPrf = null;
                    // System.out.println("Null DeflPrf id");
                } else {
                    DeflPrf = (request.getParameter("DeflPrf")).toString();
                    // System.out.println(DeflPrf);
                }
            } else {
                // System.out.println("Null DeflPrf");
                DeflPrf = null;
            }


            /*   if (request.getParameter("ChkSpwFlg") != null) {
            if (request.getParameter("ChkSpwFlg").equals("")) {
                ChkSpwFlg = null;
              //  System.out.println("Null ChkSpwFlg id");
            } else {
                ChkSpwFlg = (request.getParameter("ChkSpwFlg")).toString();
                if(ChkSpwFlg.equals(true)){
                    ChkSpwFlg="Y";
                }
                else{
                    ChkSpwFlg="N";
                    }
                System.out.println(ChkSpwFlg);
            }
        } else {
            System.out.println("Null ChkSpwFlg");
            ChkSpwFlg = null;
        }

        if (request.getParameter("SlsItmFlg") != null) {
            if (request.getParameter("SlsItmFlg").equals("")) {
                SlsItmFlg = null;
                System.out.println("Null SlsItmFlg id");
            } else {
                SlsItmFlg = (request.getParameter("SlsItmFlg")).toString();
                if(SlsItmFlg.equals(true)){
                    SlsItmFlg="Y";
                }
                else{
                     SlsItmFlg="N";
                    }
                System.out.println(SlsItmFlg);
            }
        } else {
            System.out.println("Null SlsItmFlg");
            SlsItmFlg = null;
        }

        if (request.getParameter("PurItmFlg") != null) {
            if (request.getParameter("PurItmFlg").equals("")) {
                PurItmFlg = null;
                System.out.println("Null PurItmFlg id");
            } else {
                PurItmFlg = (request.getParameter("PurItmFlg")).toString();
                if(PurItmFlg.equals(true)){
                    PurItmFlg="Y";
                    System.out.println("purflag---"+PurItmFlg);
                }
                else{
                    PurItmFlg="N";
                }
                System.out.println(PurItmFlg);
            }
        } else {
            System.out.println("Null PurItmFlg");
            PurItmFlg = null;
        }
        if (request.getParameter("WipItmFlg") != null) {
            if (request.getParameter("WipItmFlg").equals("")) {
                WipItmFlg = null;
                System.out.println("Null WipItmFlg id");
            } else {
                WipItmFlg = (request.getParameter("WipItmFlg")).toString();
                if(WipItmFlg.equals(true)){
                    WipItmFlg="Y";
                    System.out.println("WipItmFlg---"+WipItmFlg);
                }
                else{
                    WipItmFlg="N";
                }
                System.out.println(WipItmFlg);
            }
        } else {
            System.out.println("Null WipItmFlg");
            WipItmFlg = null;
        }
        */

            //docId----------------
            if (request.getParameter("DocId") != null) {
                if (request.getParameter("DocId").equals("")) {
                    doc = null;
                    System.out.println("Null doc id");
                } else {
                    doc = (request.getParameter("DocId")).toString();
                    System.out.println(doc);
                }
            } else {
                System.out.println("Null doc id");
                doc = null;
            }
            // EoId------------------------
            if (request.getParameter("EoId") != null) {
                if (request.getParameter("EoId").equals("")) {
                    EoId = null;
                    System.out.println("Null EoId id");
                } else {
                    EoId = (request.getParameter("EoId")).toString();
                    System.out.println(EoId);
                }
            } else {
                System.out.println("Null EoId");
                EoId = null;
            }


            //LotId----------------
            if (request.getParameter("LotId") != null) {
                if (request.getParameter("LotId").equals("")) {
                    LotId = null;
                    System.out.println("Null Lot id");
                } else {
                    LotId = request.getParameter("LotId").toString();

                }
            } else {
                LotId = null;
                System.out.println("Null LotId id");
            }

            //BinId----------------
            if (request.getParameter("BinId") != null) {
                if (request.getParameter("BinId").equals("")) {
                    BinId = null;
                    System.out.println("Null BinId id");
                } else {
                    BinId = request.getParameter("BinId").toString();

                }
            } else {
                BinId = null;
                System.out.println("Null BinId id");
            }

            //FyId----------------
            if (request.getParameter("FyId") != null) {
                if (request.getParameter("FyId").equals("")) {
                    Fy = null;
                    System.out.println("Null fy id");
                } else {
                    Fy = Integer.parseInt(request.getParameter("FyId").toString());
                    System.out.println(Fy);
                }
            } else {
                Fy = null;
                System.out.println("Null fy id");
            }
            // whId---------------
            if (request.getParameter("WhId") != null) {
                if (request.getParameter("WhId").equals("")) {
                    WhId = null;
                    System.out.println("Null wh id");
                } else {
                    WhId = (request.getParameter("WhId")).toString();
                }
            } else {
                WhId = null;
                System.out.println("Null wh id");
            }
            // From Date----------

            if (request.getParameter("FromDate") != null) {
                if (request.getParameter("FromDate").equals("")) {
                    FrmDate = null;
                    System.out.println("Frm date is null");
                } else {
                    FrmDate = request.getParameter("FromDate").toString();
                    System.out.println("frm date not null " + FrmDate);
                }
            } else {
                FrmDate = null;
                System.out.println("Frm date null");
            }
            // To Date-----------
            if (request.getParameter("ToDate") != null) {
                if (request.getParameter("ToDate").equals("")) {
                    ToDate = null;
                } else {
                    ToDate = request.getParameter("ToDate").toString();

                }
            } else {
                ToDate = null;
            }
            // ItemId-----
            if (request.getParameter("ItemId") != null) {
                if (request.getParameter("ItemId").equals("")) {
                    ItemId = null;
                } else {
                    ItemId = request.getParameter("ItemId").toString();
                }
            } else {
                ItemId = null;
            }

            //IssueTyp-------
            if (request.getParameter("IssueTyp") != null) {
                if (request.getParameter("IssueTyp").equals("")) {
                    IssueTyp = null;
                } else {
                    IssueTyp = Integer.parseInt(request.getParameter("IssueTyp").toString());
                }
            } else {
                IssueTyp = null;
            }
            // supplier id----------------------------------------
            if (request.getParameter("SupplierId") != null) {
                if (request.getParameter("SupplierId").equals("")) {
                    SupplierId = null;
                } else {
                    SupplierId = Integer.parseInt(request.getParameter("SupplierId").toString());
                }
            } else {
                SupplierId = null;
            }
            // RMDA TYPE---------------------------------------------------------
            if (request.getParameter("RmdaType") != null) {
                if (request.getParameter("RmdaType").equals("")) {
                    RmdaType = null;
                } else {
                    RmdaType = Integer.parseInt(request.getParameter("RmdaType").toString());
                }
            } else {
                RmdaType = null;
            }
            //QcCheck-----------------------------------------------------
            if (request.getParameter("QcChck") != null) {
                if (request.getParameter("QcChck").equals("")) {
                    QcChck = null;
                } else {
                    QcChck = Integer.parseInt(request.getParameter("QcChck").toString());
                }
            } else {
                QcChck = null;
            }

            // Qc Status------------------------------------------------
            if (request.getParameter("QcStat") != null) {
                if (request.getParameter("QcStat").equals("")) {
                    QcStat = null;
                } else {
                    QcStat = request.getParameter("QcStat").toString();
                }
            } else {
                QcStat = null;
            }

            // ReturnFlg --------------------------------------------------
            if (request.getParameter("ReturnFlg") != null) {
                if (request.getParameter("ReturnFlg").equals("")) {
                    ReturnFlg = null;
                } else {
                    ReturnFlg = request.getParameter("ReturnFlg").toString();
                }
            } else {
                ReturnFlg = null;
            }

            // Requirement AreaID---------------------------------------------

            if (request.getParameter("RqmtAreaId") != null) {
                if (request.getParameter("RqmtAreaId").equals("")) {
                    RqmtAreaId = null;
                } else {
                    RqmtAreaId = Integer.parseInt(request.getParameter("RqmtAreaId").toString());
                }
            } else {
                RqmtAreaId = null;
            }

            // MRS TXNID--------------------------------------------------------
            if (request.getParameter("MrsTxnId") != null) {
                if (request.getParameter("MrsTxnId").equals("")) {
                    MrsTxnId = null;
                } else {
                    MrsTxnId = request.getParameter("MrsTxnId").toString();
                }
            } else {
                MrsTxnId = null;
            }


            // Round Off----------
            Integer roundOff;
            if (request.getParameter("roundOff") != null) {
                if (request.getParameter("roundOff").equals("")) {
                    roundOff = null;
                } else {
                    roundOff = Integer.parseInt(request.getParameter("roundOff"));
                }
            } else {
                roundOff = null;
            }

            //Report Type:----------------------
            if (reporttype.equals("")) {
                reporttype = "pdf";
            }
            System.out.println("Report name=" + reportName);
            Map parameters = new HashMap();

            if (reportName.equalsIgnoreCase("GateEntry")) {
                input = new FileInputStream(new File(path + "GateEntry.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("DocId", doc);
                parameters.put("FyId", Fy);
                parameters.put("Path", path);
                parameters.put("reportName", reportName);

                System.out.println("Org id:" + Org + " doc " + doc + " FyId " + Fy + " repot type " + reporttype);
            } else if (reportName.equalsIgnoreCase("ReceiptRegister")) {
                input = new FileInputStream(new File(path + "ReceiptRegister.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("DocId", doc);
                parameters.put("FyId", Fy);
                parameters.put("PartyId", EoId);
                parameters.put("ItmId", ItemId);
                parameters.put("WhId", WhId);
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("Path", path);
                parameters.put("reportName", reportName);

                System.out.println("Org id:" + Org + " doc " + doc + " FyId " + Fy + " repot type " + reporttype +
                                   "whid " + WhId + "Frmdt " + FrmDate + "Todt " + ToDate);
            } else if (reportName.equalsIgnoreCase("ReceiptRegisterNoGateEntry")) {
                input = new FileInputStream(new File(path + "ReceiptRegisterNoGateEntry.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("DocId", doc);
                parameters.put("FyId", Fy);
                parameters.put("PartyId", EoId);
                parameters.put("ItmId", ItemId);
                parameters.put("WhId", WhId);
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("Path", path);
                parameters.put("reportName", reportName);

            } else if (reportName.equalsIgnoreCase("Receipt_Register_ReceiptNo_Wise")) {
                input = new FileInputStream(new File(path + "Receipt_Register_ReceiptNo_Wise.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("DocId", doc);
                parameters.put("FyId", Fy);
                parameters.put("PartyId", EoId);
                parameters.put("ItmId", ItemId);
                parameters.put("WhId", WhId);
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("Path", path);
                parameters.put("reportName", reportName);

            } else if (reportName.equalsIgnoreCase("Receipt_Register_Party_Wise")) {
                input = new FileInputStream(new File(path + "Receipt_Register_Party_Wise.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("DocId", doc);
                parameters.put("FyId", Fy);
                parameters.put("PartyId", EoId);
                parameters.put("ItmId", ItemId);
                parameters.put("WhId", WhId);
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("Path", path);
                parameters.put("reportName", reportName);
            }

            else if (reportName.equalsIgnoreCase("Receipt_Register_Item_Wise")) {
                input = new FileInputStream(new File(path + "Receipt_Register_Item_Wise.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("DocId", doc);
                parameters.put("PartyId", EoId);
                parameters.put("ItmId", ItemId);
                parameters.put("FyId", Fy);
                parameters.put("WhId", WhId);
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("Path", path);
                parameters.put("reportName", reportName);
            }


            else if (reportName.equalsIgnoreCase("Issue_Register_IssueNo_Wise")) {
                input = new FileInputStream(new File(path + "Issue_Register_IssueNo_Wise.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("IssuDocId", doc);
                parameters.put("roundoff", roundOff);
                parameters.put("IssuTyp", IssueTyp);
                parameters.put("ItmId", ItemId);
                parameters.put("FyId", Fy);
                parameters.put("WhId", WhId);
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("Path", path);
                parameters.put("reportName", reportName);

                System.out.println("Org id:" + Org + " doc " + doc + " FyId " + Fy + " repot type " + reporttype +
                                   "whid " + WhId + "roundof-" + roundOff + "Frmdt " + FrmDate + "Todt " + ToDate +
                                   "reportname " + reportName);
            }


            else if (reportName.equalsIgnoreCase("MRS")) {
                input = new FileInputStream(new File(path + "MRS.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("RqmtAreaId", RqmtAreaId);
                parameters.put("MrsTxnId", MrsTxnId);


                parameters.put("Path", path);
                parameters.put("reportName", reportName);

                System.out.println("Org id:" + Org + " doc " + MrsTxnId + "repot type " + reporttype + "reqarea " +
                                   RqmtAreaId + "Frmdt " + FrmDate + "Todt " + ToDate + "reportname " + reportName);
            }


            else if (reportName.equalsIgnoreCase("Pr_IndentReport")) {
                input = new FileInputStream(new File(path + "Pr_IndentReport.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("RqmtAreaId", RqmtAreaId);
                parameters.put("DocId", doc);
                parameters.put("prstatus", prstatus);


                parameters.put("Path", path);
                parameters.put("reportName", reportName);

                System.out.println("Org id:" + Org + " doc " + MrsTxnId + "repot type " + reporttype + "reqarea " +
                                   RqmtAreaId + "Frmdt " + FrmDate + "Todt " + ToDate + "reportname " + reportName);
            }

            else if (reportName.equalsIgnoreCase("RMDA")) {
                input = new FileInputStream(new File(path + "RMDA.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("WhId", WhId);
                parameters.put("DocId", doc);
                parameters.put("Eo_Id", SupplierId);
                parameters.put("RmdaType", RmdaType);
                parameters.put("ReturnFlg", ReturnFlg);

                parameters.put("Path", path);
                parameters.put("reportName", reportName);

                System.out.println("Org id:" + Org + " doc " + doc + "repot type " + reporttype + "RmdaType " +
                                   RmdaType + "Frmdt " + FrmDate + "Todt " + ToDate + "reportname " + reportName);
            }

            else if (reportName.equalsIgnoreCase("QcProcess")) {
                input = new FileInputStream(new File(path + "QcProcess.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("QcSrcTxnId", doc);
                parameters.put("QcChck", QcChck);
                parameters.put("QcStat", QcStat);
                parameters.put("Path", path);
                parameters.put("reportName", reportName);

                System.out.println("Org id:" + Org + " doc " + doc + "repot type " + reporttype + "QcChck " + QcChck +
                                   "Frmdt " + FrmDate + "Todt " + ToDate + "reportname " + reportName);
            }


            else if (reportName.equalsIgnoreCase("Stock_Item_Lot_Wise")) {
                input = new FileInputStream(new File(path + "Stock_Loc_Item_Lot.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("ItmId", ItemId);
                parameters.put("WhId", WhId);
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("Path", path);
                parameters.put("reportName", reportName);
            }


            else if (reportName.equalsIgnoreCase("Current_Stock_Summary")) {
                input = new FileInputStream(new File(path + "Current_Stock_Summary.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("ItmId", ItemId);
                parameters.put("WhId", WhId);
                parameters.put("Path", path);
                parameters.put("GrpId", ItemGrpId);
                parameters.put("FyId", Fy);
                parameters.put("reportName", reportName);
            }

            else if (reportName.equalsIgnoreCase("Stock_Loc_Item_Searialized")) {
                input = new FileInputStream(new File(path + "Stock_Loc_Item_Searialized.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("ItmId", ItemId);
                parameters.put("WhId", WhId);
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("Path", path);
                parameters.put("LotId", LotId);
                parameters.put("BinId", BinId);
                parameters.put("reportName", reportName);
            }

             else if(reportName.equalsIgnoreCase("Stock_Loc_Item_Bin")){
            input = new FileInputStream(new File(path + "Stock_Loc_Item_Bin.jrxml"));
            parameters.put("OrgId", Org);
            parameters.put("CldId", Cld);
            parameters.put("SlocId",Sloc);
            parameters.put("ItmId", ItemId);
            parameters.put("WhId", WhId);
            parameters.put("FromDate", FrmDate);
            parameters.put("ToDate", ToDate);
            parameters.put("Path", path);
            parameters.put("LotId", LotId);
            parameters.put("BinId", BinId);
            parameters.put("ChkSpwFlg", ChkSpwFlg);
            parameters.put("SlsItmFlg", SlsItmFlg);
            parameters.put("PurItmFlg",PurItmFlg);
            parameters.put("WipItmFlg", WipItmFlg);
            parameters.put("reportName", reportName);
    
           }
            
            /*  else if(reportName.equalsIgnoreCase("Stock_Loc_Item_Bin")){
            input = new FileInputStream(new File(path + "Stock_Loc_Item_Bin.jrxml"));
            System.out.println("inside bin rpt-");
            parameters.put("OrgId", Org);
            parameters.put("CldId", Cld);
            parameters.put("SlocId",Sloc);
            parameters.put("ItmId", ItemId);
            parameters.put("WhId", WhId);
            parameters.put("FromDate", FrmDate);
            parameters.put("ToDate", ToDate);
            parameters.put("Path", path);
            parameters.put("LotId", LotId);
            parameters.put("BinId", BinId);
            System.out.println("check  ---"+ChkSpwFlg);
            System.out.println("sls chek---"+SlsItmFlg);
            System.out.println("pur check--"+PurItmFlg);
            System.out.println("wip chek----"+WipItmFlg);
            parameters.put("ChkSpwFlg", ChkSpwFlg);
            parameters.put("SlsItmFlg", SlsItmFlg);
            parameters.put("PurItmFlg",PurItmFlg);
            parameters.put("WipItmFlg", WipItmFlg);
            parameters.put("reportName", reportName);
           }
            */

            else if (reportName.equalsIgnoreCase("StockRegister")) {
                input = new FileInputStream(new File(path + "Stock Register.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("ItmId", ItemId);
                parameters.put("WhId", WhId);
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("Path", path);
                parameters.put("LotId", LotId);
                parameters.put("BinId", BinId);
                System.out.println("iside register---");
                System.out.println("check  ---" + ChkSpwFlg);
                System.out.println("sls chek---" + SlsItmFlg);
                System.out.println("pur check--" + PurItmFlg);
                System.out.println("wip chek----" + WipItmFlg);
                parameters.put("ChkSpwFlg", ChkSpwFlg);
                parameters.put("SlsItmFlg", SlsItmFlg);
                parameters.put("PurItmFlg", PurItmFlg);
                parameters.put("WipItmFlg", WipItmFlg);
                parameters.put("reportName", reportName);
            }


            else if (reportName.equalsIgnoreCase("StockTakeProfile")) {
                input = new FileInputStream(new File(path + "StockTakeProfile.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);

                parameters.put("PrfId", PrfNm);
                parameters.put("PrfDflt", DeflPrf);
                parameters.put("StkTakeCycleTyp", StckCycl == null ? StckCycl : Integer.parseInt(StckCycl));
                parameters.put("StkTakeCycleMnths", null);
                parameters.put("MtlSelCrt", MatStck == null ? MatStck : Integer.parseInt(MatStck));

                parameters.put("Path", path);
                parameters.put("reportName", reportName);
            }


            else if (reportName.equalsIgnoreCase("Stock_Taking_Detail")) {
                input = new FileInputStream(new File(path + "Stock_Taking_Detail.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);

                parameters.put("FyId", Fy);
                parameters.put("DocId", doc);
                parameters.put("StkTakeStat", StkTakeStat);

                parameters.put("AuthStat", AuthStat);

                parameters.put("Path", path);
                parameters.put("reportName", reportName);
            }


            else if (reportName.equalsIgnoreCase("Stock_Taking_Summary")) {
                input = new FileInputStream(new File(path + "Stock_Taking_Summary.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);

                parameters.put("FyId", Fy);
                parameters.put("DocId", doc);
                parameters.put("StkTakeStat", StkTakeStat);
                parameters.put("WhId", WhId);
                parameters.put("AuthStat", AuthStat);

                parameters.put("Path", path);
                parameters.put("reportName", reportName);

            }


            else if (reportName.equalsIgnoreCase("KitWorkshop")) {
                input = new FileInputStream(new File(path + "KitWorkshop.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("WhId", WhId);
                parameters.put("FyId", Fy);
                parameters.put("DocId", doc);
                parameters.put("KitActnTyp", type);
                parameters.put("Path", path);
                parameters.put("reportName", reportName);
            }

            else if (reportName.equalsIgnoreCase("ReturnableGatePassOutstanding")) {
                input = new FileInputStream(new File(path + "ReturnableGatePassOutstanding.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("WhId", WhId);
                parameters.put("FyId", Fy);
                parameters.put("DocId", doc);
                parameters.put("EoId", EoId == null ? EoId : Integer.parseInt(EoId));
                parameters.put("DocTypSrc", type == null ? type : Integer.parseInt(type));
                parameters.put("Path", path);
                parameters.put("reportName", reportName);
            }
            else if(reportName.equalsIgnoreCase("GatePassReport")){
                input = new FileInputStream(new File(path + "GatePassReport.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("WhId", WhId);
                parameters.put("FyId", Fy);
                parameters.put("DocId", doc);
                parameters.put("EoId", EoId == null ? EoId : Integer.parseInt(EoId));
                parameters.put("DocTypSrc", type == null ? type : Integer.parseInt(type));
                parameters.put("Path", path);
                parameters.put("reportName", reportName);
            }
            else if(reportName.equalsIgnoreCase("GatePassSummaryReport")){
                input = new FileInputStream(new File(path + "GatePassSummaryReport.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("WhId", WhId);
                parameters.put("FyId", Fy);
                parameters.put("DocId", doc);
                parameters.put("EoId", EoId == null ? EoId : Integer.parseInt(EoId));
                parameters.put("DocTypSrc", type == null ? type : Integer.parseInt(type));
                parameters.put("Path", path);
                parameters.put("reportName", reportName);
            }

            else if (reportName.equalsIgnoreCase("PurchaseReturnSupplierWise")) {
                input = new FileInputStream(new File(path + "PurchaseReturnSupplierWise.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("WhId", WhId);
                parameters.put("FyId", Fy);
                parameters.put("DocId", doc);
                parameters.put("EoId", EoId == null ? EoId : Integer.parseInt(EoId));
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("AuthStat", type);
                parameters.put("PurRetStat", status);
                parameters.put("Path", path);
                parameters.put("reportName", reportName);
            }

            else if (reportName.equalsIgnoreCase("PurchaseReturnItemWise")) {
                input = new FileInputStream(new File(path + "PurchaseReturnItemWise.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("WhId", WhId);
                parameters.put("FyId", Fy);
                parameters.put("DocId", doc);
                parameters.put("EoId", EoId == null ? EoId : Integer.parseInt(EoId));
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("AuthStat", type);
                parameters.put("PurRetStat", status);
                parameters.put("ItmId", ItemId);
                parameters.put("Path", path);
                parameters.put("reportName", reportName);
            }

            else if (reportName.equalsIgnoreCase("PurchaseRequistion")) {
                input = new FileInputStream(new File(path + "PurchaseRequistion.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("WhId", WhId);
                parameters.put("FyId", Fy);
                parameters.put("PrTxnId", doc);
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("AuthStat", type);
                parameters.put("PrStat", status);
                parameters.put("RqmtAreaId", RqmtAreaId);
                parameters.put("Path", path);
                parameters.put("reportName", reportName);
            }

            else if (reportName.equalsIgnoreCase("TransferOrder")) {
                input = new FileInputStream(new File(path + "TransferOrder.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("WhId", WhId);
                parameters.put("FyId", Fy);
                parameters.put("TrfDocId", doc);
                System.out.println("FrmDate : " + FrmDate);
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("TrfSrcType", srcType);
                parameters.put("TrfStat", status);
                parameters.put("TrfType", toType);
                parameters.put("AuthStat", type);
                parameters.put("Path", path);
                parameters.put("reportName", reportName);
            }


            else if (reportName.equalsIgnoreCase("Current_Stock_Summary")) {
                input = new FileInputStream(new File(path + "Current_Stock_Summary.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("ItmId", ItemId);
                parameters.put("WhId", WhId);
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("Path", path);
                parameters.put("GrpId", ItemGrpId);
                parameters.put("FyId", Fy);
                parameters.put("reportName", reportName);
            }

            else if (reportName.equalsIgnoreCase("Item_Wise_Stock_Ledger_Summary")) {
                input = new FileInputStream(new File(path + "Item_Wise_Stock_Ledger_Summary.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("ItmId", ItemId);
                parameters.put("WhId", WhId);
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("Path", path);
                parameters.put("GrpId", ItemGrpId);
                parameters.put("FyId", Fy);
                parameters.put("reportName", reportName);
            }

            else if (reportName.equalsIgnoreCase("Item_Movement_Detail")) {
                input = new FileInputStream(new File(path + "Item_Movement_Detail.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("ItmId", ItemId);
                parameters.put("WhId", WhId);
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("Path", path);
                parameters.put("GrpId", ItemGrpId);
                parameters.put("FyId", Fy);
                parameters.put("reportName", reportName);
            }


            else if (reportName.equalsIgnoreCase("MRN")) {
                input = new FileInputStream(new File(path + "MRN.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("WhId", WhId);
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("Path", path);
                parameters.put("AuthStat", type);
                parameters.put("FyId", Fy);
                parameters.put("DocId", doc);
                parameters.put("RqmtAreaId", RqmtAreaId);
                parameters.put("MrnStatus", status);
                parameters.put("IssuDocId", PrfNm);
                parameters.put("retunOrgId", retunOrgId);
                parameters.put("reportName", reportName);
            }
            // PI REPORT
            else if (reportName.equalsIgnoreCase("PurchaseInvoiceReport")) {
                input = new FileInputStream(new File(path + "PurchaseInvoiceReport.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);

                parameters.put("FyId", Fy);
                System.out.println("DocId in purchase invoice ---->" + doc);
                parameters.put("DocId", doc);
                parameters.put("InvcType", InvcType);
                parameters.put("EoIdSrc", EoIdSrc);
                parameters.put("AuthStat", AuthStat);

                parameters.put("Path", path);
                parameters.put("reportName", reportName);
            }
            // StockTransferInvoiceReport--------------------------

            else if (reportName.equalsIgnoreCase("StockTransferInvoiceReport")) {
                input = new FileInputStream(new File(path + "StockTransferInvoiceReport.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);

                parameters.put("FyId", Fy);
                System.out.println("DocId in StockTransferInvoiceReport ---->" + doc);
                parameters.put("DocId", doc);
                parameters.put("InvcType", InvcType);
                parameters.put("EoIdSrc", EoIdSrc);
                parameters.put("AuthStat", AuthStat);

                parameters.put("Path", path);
                parameters.put("reportName", reportName);
            }


            else if (reportName.equalsIgnoreCase("Consumption_Register")) {
                input = new FileInputStream(new File(path + "Consumption_Register.jrxml"));
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("IssuDocId", doc);
                parameters.put("roundoff", roundOff);
                parameters.put("IssuTyp", IssueTyp);
                parameters.put("ItmId", ItemId);
                parameters.put("FyId", Fy);
                parameters.put("WhId", WhId);
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("Path", path);
                parameters.put("reportName", reportName);
            }


            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design);

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
                exporter = new JRXhtmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
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
                        //System.out.println(ex.getMessage());
                        throw (ex);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
