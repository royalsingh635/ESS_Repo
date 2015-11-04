package inventoryreportapp.view.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

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

import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;

@WebServlet(name = "InventoryReports", urlPatterns = { "/inventoryreports" })
public class InventoryReports extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        String ChkSpwFlg = null;
        String SlsItmFlg = null;
        String PurItmFlg = null;
        String WipItmFlg = null;
        String link = null;
        //report design
        String bgcolor = null;
        String hdcolor = null;
        String margin = null;
        String QCNoDocId = null;

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
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MMDS");
            conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement("select distinct srvr_Loc_As_Rpt_Path from APP.App$Servr$Loc");
            ResultSet rs = ps.executeQuery();
            String path = null;
            String reportName = null;
            reportName = request.getParameter("reportName");
            while (rs.next()) {
                path = rs.getString(1);
                //System.out.println("Path is :" + rs.getString(1));
            }
            if (request.getParameter("reportName") != null) {
                if (reportName.equalsIgnoreCase("Inventory") && request.getParameter("fileName") != null) {
                    path = path + "Portal/";
                } else {
                    path = path + "MM/";
                }
            }
          //  path = "D:/DEPLOYMENT/REPORT/MM/";
            System.out.println("path-->" + path);
            String reportType = "pdf";
            String Org = request.getParameter("OrgId");
            reportType = request.getParameter("reporttype");
            // System.out.println("Param report type="+reporttype);
            Integer Sloc = Integer.parseInt(request.getParameter("SlocId").toString());
            String Cld = request.getParameter("CldId");
            String hoOrgId = request.getParameter("HoOrgId");
            String rcptDocId = null;
            String issueDocId = null;
            String PRDocId = null;
            String RMDADocId = null;
            String StockTakeDocId = null;
            String KitDocId = null;
            String PReqDocId = null;
            String QCDocId = null;
            String TODocId = null;
            String InvcDocId = null;
            String GPDocId = null;
            String MRNDocId = null;
            String binId = null;
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
            String stkAdjDocId = null;
            String stkAdjType = null;
            String stkAdjStat = null;
            Integer userId = null;
            String geChk = null;


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

            if (request.getParameter("RcptDocId") != null) {
                if (request.getParameter("RcptDocId").equals("")) {
                    rcptDocId = null;
                } else {
                    rcptDocId = request.getParameter("RcptDocId").toString();
                    System.out.println("rcptDocId/////" + rcptDocId);
                }
            }
            if (request.getParameter("IssueDocId") != null) {
                if (request.getParameter("IssueDocId").equals("")) {
                    issueDocId = null;
                } else {
                    issueDocId = request.getParameter("IssueDocId").toString();
                    System.out.println("docId/////" + issueDocId);
                }
            }
            if (request.getParameter("PRDocId") != null) {
                if (request.getParameter("PRDocId").equals("")) {
                    PRDocId = null;
                } else
                    PRDocId = request.getParameter("PRDocId").toString();
            }
            if (request.getParameter("RMDADocId") != null) {
                if (request.getParameter("RMDADocId").equals("")) {
                    RMDADocId = null;
                } else
                    RMDADocId = request.getParameter("RMDADocId").toString();
            }
            if (request.getParameter("StockTakeDocId") != null) {
                if (request.getParameter("StockTakeDocId").equals("")) {
                    StockTakeDocId = null;
                } else
                    StockTakeDocId = request.getParameter("StockTakeDocId").toString();
            }
            if (request.getParameter("KitDocId") != null) {
                if (request.getParameter("KitDocId").equals("")) {
                    KitDocId = null;
                } else
                    KitDocId = request.getParameter("KitDocId").toString();
            }
            if (request.getParameter("PReqDocId") != null) {
                if (request.getParameter("PReqDocId").equals("")) {
                    PReqDocId = null;
                } else
                    PReqDocId = request.getParameter("PReqDocId").toString();
            }
            if (request.getParameter("QCDocId") != null) {
                if (request.getParameter("QCDocId").equals("")) {
                    QCDocId = null;
                } else
                    QCDocId = request.getParameter("QCDocId").toString();
            }
            if (request.getParameter("TODocId") != null) {
                if (request.getParameter("TODocId").equals("")) {
                    TODocId = null;
                } else
                    TODocId = request.getParameter("TODocId");
            }
            if (request.getParameter("InvcDocId") != null) {
                if (request.getParameter("InvcDocId").equals("")) {
                    InvcDocId = null;
                } else
                    InvcDocId = request.getParameter("InvcDocId").toString();
            }
            if (request.getParameter("GPDocId") != null) {
                if (request.getParameter("GPDocId").equals("") && request.getParameter("GPDocId") == null) {
                    GPDocId = null;
                } else
                    GPDocId = request.getParameter("GPDocId").toString();
            }
            if (request.getParameter("MRNDocId") != null) {
                if (request.getParameter("MRNDocId").equals("")) {
                    MRNDocId = null;
                } else
                    MRNDocId = request.getParameter("MRNDocId").toString();

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
            Object stDt = null;
            Object edDt = null;
            if (request.getParameter("FromDate") != null) {
                if (request.getParameter("FromDate").equals("")) {
                    FrmDate = null;
                    System.out.println("Frm date is null");
                } else {
                  //  FrmDate = request.getParameter("FromDate").toString().substring(0, 10);
                 //   System.out.println("frm date not null " + FrmDate+"--");
                   
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    stDt = request.getParameter("FromDate");
                    Date dt = dateFormat.parse(stDt.toString());
                    FrmDate = dateFormat.format(dt);
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
//                    ToDate = request.getParameter("ToDate").toString().substring(0, 10);
//                    System.out.println(" todate...."+ToDate);
                    
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    edDt = request.getParameter("ToDate");
                    Date dt = dateFormat.parse(edDt.toString());
                    ToDate = dateFormat.format(dt); 
                    System.out.println(" todate...."+ToDate+"--");


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
            if (request.getParameter("reporttype") != null) {
                if (request.getParameter("reporttype") == null || request.getParameter("reporttype").length() <= 0) {
                    reportType = "pdf";
                } else {
                    reportType = request.getParameter("reporttype");
                }
            }
            //portal report
            if (request.getParameter("reportName") != null) {
                if (reportName.equalsIgnoreCase("Inventory") && request.getParameter("fileName") != null &&
                    request.getParameter("fileName").toString().length() > 0) {
                    reportName = request.getParameter("fileName");
                    System.out.println(" FileName getting Loop" + reportName);
                } else
                    reportName = request.getParameter("reportName");
            }

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
                    System.out.println("marging..........." + margin);
                } else {
                    margin = request.getParameter("Margin_Detail");
                    if (margin.equalsIgnoreCase("true")) {
                        margin = "Y";
                        System.out.println("margin...Y.." + margin);
                    } else if (margin.equalsIgnoreCase("false")) {
                        margin = "N";
                        System.out.println("margin...N.." + margin);
                    }
                }
            }

            //StockAdjust doc id
            if (request.getParameter("StkAdjDocId") != null) {
                if (request.getParameter("StkAdjDocId").equals("")) {
                    stkAdjDocId = null;
                } else
                    stkAdjDocId = request.getParameter("StkAdjDocId").toString();

            }
            if (reportName != null) {
                if (reportName.equalsIgnoreCase("StockRegister")) {
                    reportName = "Stock Register";
                } else
                    reportName = request.getParameter("reportName");

            }
            //User Id
             if(request.getParameter("UserId")!=null){
            if (request.getParameter("UserId").equals("") || request.getParameter("UserId") == null) {
                userId = 0;
            } else
                userId = Integer.parseInt(request.getParameter("UserId"));
              }
            //QC No Doc Id
            if(request.getParameter("QCNoDocId")!=null){
                if(request.getParameter("QCNoDocId").equals("")||request.getParameter("QCNoDocId")==null){
                    QCNoDocId = null;
                }else QCNoDocId = request.getParameter("QCNoDocId").toString();
            }
            
            //Bin Id
            if(request.getParameter("BinId")!=null){
                if(request.getParameter("BinId").equals("")||request.getParameter("BinId")==null){
                    binId = null;
                }else binId = request.getParameter("BinId").toString();
            }

            //GE Chk
            if(request.getParameter("GEChk")!=null){
                if(request.getParameter("GEChk").equals("")||request.getParameter("GEChk")==null){
                    geChk = null;
                }else geChk = request.getParameter("GEChk").toString();
            }
            System.out.println("Report name=" + reportName);
            Map parameters = new HashMap();
            String rptname = request.getParameter("fileName");
            System.out.println("reptName...." + rptname);
            parameters.put("OrgId", Org);
            parameters.put("CldId", Cld);
            parameters.put("SlocId", Sloc);
            parameters.put("FyId", Fy);
            parameters.put("ItmId", ItemId);
            parameters.put("PartyId", EoId);
            parameters.put("EoId", EoId);
            parameters.put("WhId", WhId);
            parameters.put("FromDate", FrmDate);
            parameters.put("ToDate", ToDate);
            parameters.put("HoOrgId", hoOrgId);
            parameters.put("UserId", userId);
            parameters.put("BinId", binId);
            parameters.put("Path", path);
            //
            if (reportType.equalsIgnoreCase("XLS")) {
                parameters.put("ReportType", "E");
            } else parameters.put("ReportType", "P");
           
            if (reportName.equalsIgnoreCase("GateEntry")) {
                reportName = "GateEntry";
                parameters.put("DocId", rcptDocId);

            } else if (reportName.equalsIgnoreCase("ReceiptRegister")) {
                reportName = "ReceiptRegister";
                parameters.put("DocId", rcptDocId);
                parameters.put("PartyId", EoId);
            } else if (reportName.equalsIgnoreCase("ReceiptRegisterNoGateEntry")) {
                reportName = "ReceiptRegisterNoGateEntry";
                parameters.put("DocId", rcptDocId);
                parameters.put("Path", path);
            } else if (reportName.equalsIgnoreCase("Receipt_Register_ReceiptNo_Wise")) {
                reportName = "Receipt_Register_ReceiptNo_Wise";
                parameters.put("DocId", rcptDocId);
                parameters.put("Path", path);
            } else if (reportName.equalsIgnoreCase("Receipt_Register_Party_Wise")) {
                reportName = "Receipt_Register_Party_Wise";
                parameters.put("DocId", rcptDocId);
            } else if (reportName.equalsIgnoreCase("Receipt_Register_Item_Wise")) {
                reportName = "Receipt_Register_Item_Wise";
                parameters.put("DocId", rcptDocId);
            } else if (reportName.equalsIgnoreCase("Issue_Register_IssueNo_Wise")) {
                reportName = "Issue_Register_IssueNo_Wise";
                parameters.put("IssuDocId", issueDocId);
                parameters.put("roundoff", roundOff);
                parameters.put("IssuTyp", IssueTyp);
            } else if (reportName.equalsIgnoreCase("MRS")) {
                reportName = "MRS";
                parameters.put("RqmtAreaId", RqmtAreaId);
                parameters.put("MrsTxnId", MrsTxnId);
            } else if (reportName.equalsIgnoreCase("Pr_IndentReport")) {
                reportName = "Pr_IndentReport";
                parameters.put("RequirmentAreaId", RqmtAreaId);
                parameters.put("DocId", PReqDocId);
                parameters.put("prstatus", prstatus);
            } else if (reportName.equalsIgnoreCase("RMDA")) {
                reportName = "RMDA";
                System.out.println("RMDADocId  " + RMDADocId);
                parameters.put("DocId", RMDADocId);
                parameters.put("Eo_Id", SupplierId);
                parameters.put("RmdaType", RmdaType);
                parameters.put("ReturnFlg", ReturnFlg);
            } else if (reportName.equalsIgnoreCase("QcProcess")) {
                reportName = "QcProcess";
                parameters.put("QcSrcTxnId", QCDocId);
                parameters.put("QcChck", QcChck);
                parameters.put("QcStat", QcStat);
                parameters.put("QCNoDocId",QCNoDocId);
            } else if (reportName.equalsIgnoreCase("Stock_Item_Lot_Wise")) {
                reportName = "Stock_Loc_Item_Lot";
                parameters.put("Path", path);
            } else if (reportName.equalsIgnoreCase("Current_Stock_Summary")) {
                reportName = "Current_Stock_Summary";
                parameters.put("GrpId", ItemGrpId);
            } else if (reportName.equalsIgnoreCase("Stock_Loc_Item_Searialized")) {
                reportName = "Stock_Loc_Item_Searialized";
                parameters.put("LotId", LotId);
                parameters.put("BinId", BinId);
                parameters.put("reportName", reportName);
            } else if (reportName.equalsIgnoreCase("Stock_Loc_Item_Bin")) {
                reportName = "Stock_Loc_Item_Bin";
                parameters.put("LotId", LotId);
                parameters.put("BinId", BinId);
                parameters.put("ChkSpwFlg", ChkSpwFlg);
                parameters.put("SlsItmFlg", SlsItmFlg);
                parameters.put("PurItmFlg", PurItmFlg);
                parameters.put("WipItmFlg", WipItmFlg);
            } else if (reportName.equalsIgnoreCase("StockRegister")) {
                reportName = "Stock Register";
                parameters.put("LotId", LotId);
                parameters.put("BinId", BinId);
                parameters.put("ChkSpwFlg", ChkSpwFlg);
                parameters.put("SlsItmFlg", SlsItmFlg);
                parameters.put("PurItmFlg", PurItmFlg);
                parameters.put("WipItmFlg", WipItmFlg);
            } else if (reportName.equalsIgnoreCase("StockTakeProfile")) {
                reportName = "StockTakeProfile";
                parameters.put("PrfId", PrfNm);
                parameters.put("PrfDflt", DeflPrf);
                parameters.put("StkTakeCycleTyp", StckCycl == null ? StckCycl : Integer.parseInt(StckCycl));
                parameters.put("StkTakeCycleMnths", null);
                parameters.put("MtlSelCrt", MatStck == null ? MatStck : Integer.parseInt(MatStck));
            } else if (reportName.equalsIgnoreCase("Stock_Taking_Detail")) {
                reportName = "Stock_Taking_Detail";
                parameters.put("DocId", StockTakeDocId);
                parameters.put("StkTakeStat", StkTakeStat);
                parameters.put("AuthStat", AuthStat);
            } else if (reportName.equalsIgnoreCase("Stock_Taking_Summary")) {
                reportName = "Stock_Taking_Summary";
                parameters.put("DocId", StockTakeDocId);
                parameters.put("StkTakeStat", StkTakeStat);
                parameters.put("AuthStat", AuthStat);
            } else if (reportName.equalsIgnoreCase("KitWorkshop")) {
                reportName = "KitWorkshop";
                parameters.put("DocId", KitDocId);
                parameters.put("KitActnTyp", type);
            } else if (reportName.equalsIgnoreCase("ReturnableGatePassOutstanding")) {
                reportName = "ReturnableGatePassOutstanding";
                parameters.put("DocId", GPDocId);
                parameters.put("EoId", EoId == null ? EoId : Integer.parseInt(EoId));
                parameters.put("DocTypSrc", type == null ? type : Integer.parseInt(type));
            } else if (reportName.equalsIgnoreCase("GatePassReport")) {
                reportName = "GatePassReport";
                parameters.put("DocId", GPDocId);
                parameters.put("EoId", EoId == null ? EoId : Integer.parseInt(EoId));
                parameters.put("DocTypSrc", type == null ? type : Integer.parseInt(type));
            } else if (reportName.equalsIgnoreCase("GatePassSummaryReport")) {
                reportName = "GatePassSummaryReport";
                parameters.put("DocId", GPDocId);
                parameters.put("EoId", EoId == null ? EoId : Integer.parseInt(EoId));
                parameters.put("DocTypSrc", type == null ? type : Integer.parseInt(type));
            } else if (reportName.equalsIgnoreCase("PurchaseReturnSupplierWise")) {
                reportName = "PurchaseReturnSupplierWise";
                parameters.put("DocId", PRDocId);
                parameters.put("EoId", EoId == null ? EoId : Integer.parseInt(EoId));
                parameters.put("AuthStat", type);
                parameters.put("PurRetStat", status);
            } else if (reportName.equalsIgnoreCase("PurchaseReturnItemWise")) {
                reportName = "PurchaseReturnItemWise";
                parameters.put("DocId", PRDocId);
                parameters.put("EoId", EoId == null ? EoId : Integer.parseInt(EoId));
                parameters.put("AuthStat", type);
                parameters.put("PurRetStat", status);
            } else if (reportName.equalsIgnoreCase("PurchaseRequistion")) {
                reportName = "PurchaseRequistion";
                parameters.put("PrTxnId", PReqDocId);
                parameters.put("AuthStat", type);
                parameters.put("PrStat", status);
                parameters.put("RqmtAreaId", RqmtAreaId);
            } else if (reportName.equalsIgnoreCase("TransferOrder")) {
                reportName = "TransferOrder";
                parameters.put("TrfDocId", TODocId);
                parameters.put("TrfSrcType", srcType);
                parameters.put("TrfStat", status);
                parameters.put("TrfType", toType);
                parameters.put("AuthStat", type);
            } else if (reportName.equalsIgnoreCase("Current_Stock_Summary")) {
                reportName = "Current_Stock_Summary";
                parameters.put("GrpId", ItemGrpId);
            } else if (reportName.equalsIgnoreCase("Item_Wise_Stock_Ledger_Summary")) {
                reportName = "Item_Wise_Stock_Ledger_Summary";
                parameters.put("GrpId", ItemGrpId);
            } else if (reportName.equalsIgnoreCase("Item_Movement_Detail")) {
                reportName = "Item_Movement_Detail";
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("FyId", Fy);
                parameters.put("ItmId", ItemId);
                parameters.put("PartyId", EoId);
                parameters.put("WhId", WhId);
                parameters.put("FromDate", FrmDate);
                parameters.put("ToDate", ToDate);
                parameters.put("HoOrgId", hoOrgId);
                parameters.put("Path", path);
                parameters.put("GrpId", ItemGrpId);
                System.out.println(Org + Cld + Sloc + ItemId + ItemGrpId);
            } else if (reportName.equalsIgnoreCase("MRN")) {
                reportName = "MRN";
                parameters.put("Path", path);
                parameters.put("AuthStat", type);
                parameters.put("DocId", MRNDocId);
                parameters.put("RqmtAreaId", RqmtAreaId);
                parameters.put("MrnStatus", status);
                parameters.put("IssuDocId", PrfNm);
                parameters.put("retunOrgId", retunOrgId);
                parameters.put("reportName", reportName);
            }
            // PI REPORT
            else if (reportName.equalsIgnoreCase("PurchaseInvoiceReport")) {
                reportName = "PurchaseInvoiceReport";
                parameters.put("DocId", InvcDocId);
                parameters.put("InvcType", InvcType);
                parameters.put("EoIdSrc", EoIdSrc);
                parameters.put("AuthStat", AuthStat);
            }
            // StockTransferInvoiceReport--------------------------
            else if (reportName.equalsIgnoreCase("StockTransferInvoiceReport")) {
                reportName = "StockTransferInvoiceReport";
                parameters.put("DocId", InvcDocId);
                System.out.println("invoice docid-"+InvcDocId);
                parameters.put("InvcType", InvcType);
                parameters.put("EoIdSrc", EoIdSrc);
                parameters.put("AuthStat", AuthStat);
            } else if (reportName.equalsIgnoreCase("Consumption_Register")) {
                reportName = "Consumption_Register";
                parameters.put("IssuDocId", issueDocId);
                parameters.put("roundoff", roundOff);
                parameters.put("IssuTyp", IssueTyp);
            } else if (reportName.equalsIgnoreCase("GateEntryWorkJob")) {
                reportName = "GateEntryWorkJob";
                parameters.put("DocId", rcptDocId);
                parameters.put("EoId", SupplierId);

            } else if (reportName.equalsIgnoreCase("ProductAgeingItemWise")) {
                reportName = "ProductAgeingItemWise";
                parameters.put("OrgId", Org);
                parameters.put("CldId", Cld);
                parameters.put("SlocId", Sloc);
                parameters.put("FyId", Fy);
                parameters.put("ItmId", ItemId);
                parameters.put("WhId", WhId);
                parameters.put("HoOrgId", hoOrgId);
                parameters.put("Path", path);
                parameters.put("GrpId", ItemGrpId);
                parameters.put("UserId", userId);
                System.out.println("---" + userId + "usrId" + Org + Cld + Sloc + ItemId + ItemGrpId + WhId);

            } else if (reportName.equalsIgnoreCase("PurchaseInvoiceRegister")) {
                if (reportName.equalsIgnoreCase("PurchaseInvoiceRegister")&& (reportType.equalsIgnoreCase("XLS"))){
                    reportName = "PurchaseInvoiceRegister_E";
                }else{
                reportName = "PurchaseInvoiceRegister";
                    }
                parameters.put("EoId", EoId);
                parameters.put("WhId", WhId);
                parameters.put("DocId", InvcDocId);
                parameters.put("InvcType", InvcType);
                parameters.put("EoIdSrc", EoIdSrc);

            } else if (reportName.equalsIgnoreCase("StockAdjustmentReport")) {
                reportName = "StockAdjustmentReport";
                parameters.put("DocId", stkAdjDocId);
                parameters.put("Auth_Stat", AuthStat);
            } else if (reportName.equalsIgnoreCase("ProductAgeingLotWise")) {
                reportName = "ProductAgeingLotWise";
                parameters.put("UserId", userId);
                System.out.println("---" + userId + "usrId" + Org + Cld + Sloc + ItemId + ItemGrpId + WhId);

            } else if (reportName.equalsIgnoreCase("StockReserved")) {
                reportName = "StockReserved";
            } else if (reportName.equalsIgnoreCase("StockReservedItemWise")) {
                reportName = "StockReservedItemWise";
            } else if (reportName.equalsIgnoreCase("QcTestCertificate")) {
                reportName = "QC_Test_Certificate";
                parameters.put("QCNoDocId",QCNoDocId);
            }else if (reportName.equalsIgnoreCase("Issue_Register_Req_Area_Wise")) {
                reportName = "Issue_Register_Req_Area_Wise";
                parameters.put("IssuDocId", issueDocId);
                parameters.put("roundoff", roundOff);
                parameters.put("IssuTyp", IssueTyp);
                parameters.put("RqmtAreaId",RqmtAreaId);
            }else if (reportName.equalsIgnoreCase("Issue_Register_Item_Wise")) {
                reportName = "Issue_Register_Item_Wise";
                parameters.put("IssuDocId", issueDocId);
                parameters.put("roundoff", roundOff);
                parameters.put("IssuTyp", IssueTyp);
            }else if (reportName.equalsIgnoreCase("PurchaseInvoiceItem")) {
                reportName = "PurchaseInvoiceItemWise";
                parameters.put("ItmId",ItemId);
                parameters.put("EoId", EoId);
                parameters.put("WhId", WhId);
                parameters.put("DocId", InvcDocId);
                parameters.put("InvcType", InvcType);
                parameters.put("EoIdSrc", EoIdSrc);

            }else if (reportName.equalsIgnoreCase("PurchaseInvoiceSupplier")) {
                System.out.println("report---"+reportName);
                reportName = "PurchaseInvoiceSupplierWise";
                parameters.put("EoId", EoId);
                parameters.put("WhId", WhId);
                parameters.put("DocId", InvcDocId);
                parameters.put("InvcType", InvcType);
                parameters.put("EoIdSrc", EoIdSrc);

            }
            else if (reportName.equalsIgnoreCase("purInvcDet")) {
                System.out.println("report---"+reportName);
                reportName = "PurchaseInvoiceSupplierDetails";
                parameters.put("EoId", EoId);
                parameters.put("DocId", InvcDocId);
                parameters.put("InvcType", InvcType);
                parameters.put("EoIdSrc", EoIdSrc);
               parameters.put("ItmId",ItemId);
            } else if(reportName.equalsIgnoreCase("TransferOrderTrack")){
                reportName = "TransferOrderTracking";
                parameters.put("TrfDocId", TODocId);
                parameters.put("TrfSrcType", srcType);
                parameters.put("TrfStat", status);
                parameters.put("TrfType", toType);
                parameters.put("AuthStat", type);
                parameters.put("GEChk",geChk);
            }else if(reportName.equalsIgnoreCase("purReqTrack")){
                reportName = "PurchaseRequisitionTracking";
                parameters.put("DocId", PReqDocId);
                parameters.put("AuthStat", type);
                parameters.put("PrStat", status);
                parameters.put("GEChk",geChk);
            }
            
            
            
            //For Portal Parameter
            else if (reportName.equalsIgnoreCase("Inventory")) {
                reportName = rptname;
                if (rcptDocId != null) {
                    parameters.put("DocId", rcptDocId);
                } else if (RMDADocId != null) {
                    parameters.put("DocId", RMDADocId);
                } else if (StockTakeDocId != null) {
                    parameters.put("DocId", StockTakeDocId);
                } else if (KitDocId != null) {
                    parameters.put("DocId", KitDocId);
                } else if (PReqDocId != null) {
                    parameters.put("DocId", PReqDocId);
                } else if (InvcDocId != null) {
                    parameters.put("DocId", InvcDocId);
                    System.out.println("InvcDocId-------" + InvcDocId);
                } else if (GPDocId != null) {
                    parameters.put("DocId", GPDocId);
                    System.out.println("GpDOCID...." + GPDocId);
                } else if (MRNDocId != null) {
                    parameters.put("DocId", MRNDocId);
                } else if (PRDocId != null) {
                    parameters.put("DocId", PRDocId);
                } else if (stkAdjDocId != null) {
                    parameters.put("DocId", stkAdjDocId);
                }
                parameters.put("IssuDocId", issueDocId);
                parameters.put("roundoff", roundOff);
                parameters.put("IssuTyp", IssueTyp);
                parameters.put("AuthStat", type);
                parameters.put("Auth_Stat", AuthStat);
                parameters.put("RqmtAreaId", RqmtAreaId);
                parameters.put("MrnStatus", status);
                parameters.put("retunOrgId", retunOrgId);
                parameters.put("RmdaType", RmdaType);
                parameters.put("ReturnFlg", ReturnFlg);
                parameters.put("LotId", LotId);
                parameters.put("BinId", BinId);
                parameters.put("ChkSpwFlg", ChkSpwFlg);
                parameters.put("SlsItmFlg", SlsItmFlg);
                parameters.put("PurItmFlg", PurItmFlg);
                parameters.put("WipItmFlg", WipItmFlg);
                parameters.put("GrpId", ItemGrpId);
                parameters.put("StkTakeStat", StkTakeStat);
                parameters.put("MrsTxnId", MrsTxnId);
                parameters.put("prstatus", prstatus);
                parameters.put("PrTxnId", PReqDocId);
                parameters.put("PrStat", status);
                parameters.put("QcSrcTxnId", QCDocId);
                parameters.put("QcChck", QcChck);
                parameters.put("QcStat", QcStat);
                parameters.put("TrfDocId", TODocId);
                parameters.put("TrfSrcType", srcType);
                parameters.put("TrfStat", status);
                parameters.put("TrfType", toType);
                parameters.put("InvcType", InvcType);
                parameters.put("EoIdSrc", EoIdSrc);
                parameters.put("KitActnTyp", type);
                parameters.put("EoId", EoId == null ? EoId : Integer.parseInt(EoId));
                parameters.put("DocTypSrc", type == null ? type : Integer.parseInt(type));
                parameters.put("PrfId", PrfNm);
                parameters.put("PrfDflt", DeflPrf);
                parameters.put("StkTakeCycleTyp", StckCycl == null ? StckCycl : Integer.parseInt(StckCycl));
                parameters.put("StkTakeCycleMnths", null);
                parameters.put("MtlSelCrt", MatStck == null ? MatStck : Integer.parseInt(MatStck));
                parameters.put("UserId", userId);
                parameters.put("BgColor", bgcolor);
                parameters.put("Head", hdcolor);
                parameters.put("Margin_Detail", margin);
                parameters.put("Path", path);
                parameters.put("QCNoDocId",QCNoDocId);

            }

         /*   System.out.println("reportNmae" + reportName + "--MRNDocId--" + MRNDocId + rcptDocId + "--rcptDocId--" +
                               issueDocId + "--issueDocId--" + RMDADocId + "--RMDADocId--" + StockTakeDocId +
                               "--StockTakeDocId--" + KitDocId + "--KitDocId--" + PReqDocId + "--PReqDocId--" +
                               InvcDocId + "--InvcDocId--" + GPDocId + "--GPDocId--" + Fy + "--Fy--" + ItemId +
                               "--ItemId--" + EoId + "--EoId--" + WhId + "--WhId--" + FrmDate + "--FrmDate--" + ToDate +
                               "--ToDate--" + roundOff + "--roundOff--" + IssueTyp + "--IssueTyp--" + type +
                               "--type--" + RqmtAreaId + "--RqmtAreaId--" + status + "--status--" + PrfNm +
                               "--PrfNm--" + retunOrgId + "--retunOrgId--" + RmdaType + "--RmdaType--" + ReturnFlg +
                               "--ReturnFlg--" + LotId + "--LotId--" + "--BinId--" + BinId + "--BinId--" + ItemGrpId +
                               "--ItemGrpId--" + StkTakeStat + "--StkTakeStat--" + MrsTxnId + "--MrsTxnId--" +
                               prstatus + "--prstatus--" + PReqDocId + "--PReqDocId--" + status + "--status--" +
                               QCDocId + "--QCDocId--" + QcChck + "--QcChck--" + QcStat + "--QcStat--" + TODocId +
                               "--TODocId--" + srcType + "---srcType-" + toType + "--toType--" + InvcType +
                               "--InvcType--" + EoIdSrc + "--EoIdSrc--" + DeflPrf + "--DeflPrf--" + StckCycl +
                               "--StckCycl--" + MatStck + "--MatStck--" + bgcolor + "--bgcolor--" + hdcolor +
                               "--hdcolor--" + margin + "--margin--");*/
            System.out.println("Report Parameters are-"+parameters);
            System.out.println("Inside global report--path " + path);
            /**change the jasperLoader**/
            System.out.println("before jasperReport" + path + "...." + reportName);
            JasperReport report = (JasperReport) JRLoader.loadObject(path + reportName + ".jasper");
            System.out.println("ReportName to be print..." + reportName + " path " + path);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
            OutputStream ouputStream = response.getOutputStream();
            JRExporter exporter = null;
            System.out.println("before pdf");
            System.out.println("reporttype      " + reportType);
            if (reportType.equalsIgnoreCase("PDF")) {
                System.out.println("After pdf");
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".pdf\"");
                System.out.println("before expo in pdf---");
                exporter = new JRPdfExporter();
                System.out.println("After expo in pdf---" + exporter);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if (reportType.equalsIgnoreCase("RTF")) {
                response.setContentType("application/rtf");
                response.setHeader("Pragma", "no-cache");
                System.out.println("before expo in pdf---");
                exporter = new JRRtfExporter();
                System.out.println("After expo in pdf---" + exporter);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if (reportType.equalsIgnoreCase("HTML")) {
                exporter = new JRHtmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if (reportType.equalsIgnoreCase("XLS")) {
                response.setContentType("application/csv");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".csv\"");
                exporter = new JRCsvExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if (reportType.equalsIgnoreCase("DOCX")) {
                response.setContentType("application/docx");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".docx\"");
                exporter = new JRDocxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if (reportType.equalsIgnoreCase("XLSX")) {
                response.setContentType("application/xlsx");
                response.setHeader("Content-Disposition", "attachement; filename=\"" + reportName + ".xlsx\"");
                exporter = new JRXlsxExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
            } else if (reportType.equalsIgnoreCase("XML")) {
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

                        throw (ex);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
