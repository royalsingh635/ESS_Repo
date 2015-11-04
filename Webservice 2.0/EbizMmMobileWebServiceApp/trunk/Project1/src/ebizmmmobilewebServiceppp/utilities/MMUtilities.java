package ebizmmmobilewebServiceppp.utilities;

import com.ess.conn.DBConnection;

import com.sun.rowset.internal.Row;


import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Types;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MMUtilities {
    private Connection con;
    private DBConnection db;

    public MMUtilities() {
        super();
    }

    public MMUtilities(DBConnection db, Connection con) {
        super();
        this.db = db;
        this.con = con;
    }

    @SuppressWarnings("unchecked")
    public Map getGrpList(String cldId, int slocId, String horgId, String orgId) throws SQLException {
        Map map = new HashMap();
        List list = new ArrayList();
        Map innerMap = null;
        String query =
            "select grp_id,grp_nm from app.APP$GRP  \n" + "where cld_id=?   \n" + "and sloc_id=?   \n" +
            "and ho_org_id=?   \n" + "and org_id=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setObject(1, cldId);
        ps.setObject(2, slocId);
        ps.setObject(3, horgId);
        ps.setObject(4, orgId);
        ResultSet rs = ps.executeQuery();


        while (rs.next()) {
            innerMap = new HashMap();
            innerMap.put("grpId", rs.getObject("Grp_Id".toUpperCase()));
            innerMap.put("grpNm", rs.getObject("Grp_Nm".toUpperCase()));
            list.add(innerMap);
        }
        rs.close();
        ps.close();
        if (list.isEmpty()) {
            map.put("grpInfo", list);
            map.put("status", "false");
            map.put("msg", "No Data Found.");
        } else {
            map.put("grpInfo", list);
            map.put("status", "true");
            map.put("msg", "Record Found !!");
        }
        return map;
    }


    @SuppressWarnings("unchecked")
    public Map getItmList(String cldId, int slocId, String horgId, String orgId, int lower, int upper, String type,
                          String typeId) throws SQLException {
        Map map = new HashMap();
        List list = new ArrayList();
        System.out.println("in util metod");
        Map innerMap = null;
        /* String query =
              "SELECT * FROM (  \n" + "SELECT       \n" + "a.itm_id ITM_ID,       \n" + "a.itm_desc ITM_DESC,       \n" +
            "a.PRICE_BASIC PRICE_BASIC,       \n" + "C.grp_nm GRP_NM,  \n" + "ROW_NUMBER() OVER (ORDER BY 1) SLNO \n" +
            "FROM app.APP$ITM$PRF a,       \n" + "app.app$itm$org B,   \n" + "app.APP$GRP C   \n" +
            "WHERE A.cld_id=?       \n" + "AND A.sloc_id=?\n" + "AND A.ho_org_id=?\n" + "AND B.org_id=?\n" +
            "AND A.grp_id = nvl(?,a.grp_id)    \n" + "AND A.ACTV='Y'   \n" + "AND A.CLD_ID = B.CLD_ID  \n" +
            "AND A.SLOC_ID = B.SLOC_ID  \n" + "AND A.HO_ORG_ID = B.HO_ORG_ID  \n" + "AND A.ITM_ID = B.ITM_ID  \n" +
            "AND A.CLD_ID = C.CLD_ID  \n" + "AND A.SLOC_ID = C.SLOC_ID  \n" + "AND A.HO_ORG_ID = C.HO_ORG_ID  \n" +
            "AND A.GRP_ID = C.GRP_ID  \n" +
            "AND upper(a.itm_desc) like decode (?, null, upper(a.itm_desc), '%'||upper(?)||'%')  \n" +
            "AND a.Capital_Gd_Flg = ?        \n" + "AND a.Srvc_Itm_Flg = ?\n" + "AND a.STOCKABLE_FLG =  ?) A  \n" +
            "WHERE A.SLNO BETWEEN ? AND ?"; */

        String query =
            "SELECT *\n" + "FROM\n" + "  (SELECT a.itm_id ITM_ID,\n" + "    a.itm_desc ITM_DESC,\n" +
            "    a.PRICE_BASIC PRICE_BASIC,\n" + "    C.grp_nm GRP_NM,\n" +
            "    ROW_NUMBER() OVER (ORDER BY 1) SLNO\n" + "  FROM app.APP$ITM$PRF a,\n" + "    app.app$itm$org B,\n" +
            "    app.APP$GRP C\n" + "  WHERE A.cld_id  =?\n" + "  AND A.sloc_id   =?\n" + "  AND A.ho_org_id =?\n" +
            "  AND B.org_id    =?\n" + "  AND A.grp_id    = NVL(?,a.grp_id)\n" + "  AND A.ACTV      ='Y'\n" +
            "  AND A.CLD_ID    = B.CLD_ID\n" + "  AND A.SLOC_ID   = B.SLOC_ID\n" + "  AND A.HO_ORG_ID = B.HO_ORG_ID\n" +
            "  AND A.ITM_ID    = B.ITM_ID\n" + "  AND A.CLD_ID    = C.CLD_ID\n" + "  AND A.SLOC_ID   = C.SLOC_ID\n" +
            "  AND A.HO_ORG_ID = C.HO_ORG_ID\n" + "  AND A.GRP_ID    = C.GRP_ID\n" +
            "  AND (1=? or APP.FN_GET_ITM_TYPE(A.CLD_ID,A.SLOC_ID,A.HO_ORG_ID,A.ITM_ID)=?)\n" + "  ) A\n" +
            "WHERE A.SLNO BETWEEN ? AND ?";

        PreparedStatement ps = con.prepareStatement(query);
        ps.setObject(1, cldId);
        ps.setObject(2, slocId);
        ps.setObject(3, horgId);
        ps.setObject(4, orgId);

        ps.setObject(8, lower);
        ps.setObject(9, upper);
        //All/type/group

        if (type.equalsIgnoreCase("type")) {
            ps.setObject(5, null); // grpId
            ps.setObject(6, 0);
            switch (typeId) {
            case "1":
                ps.setObject(7, "RM");
                break;
            case "2":
                ps.setObject(7, "FG");
                break;
            case "3":
                ps.setObject(7, "Capital");
                break;
            }

        } else if (type.equalsIgnoreCase("group")) {
            ps.setObject(5, typeId); // grpId
            ps.setObject(6, 1);
            ps.setObject(7, null);

        } else if (type.equalsIgnoreCase("All")) {
            ps.setObject(5, null); // grpId
            ps.setObject(6, 1);
            ps.setObject(7, null);
        }
        //   ps.setObject(5, (grpId.equals("null") ? null : grpId));
        // ps.setObject(6, (itmDesc.equals("null") ? null : itmDesc));
        // ps.setObject(7, (itmDesc.equals("null") ? null : itmDesc));
        // ps.setObject(8, capitalItmFlg);
        // ps.setObject(9, srvsItmFlg);
        // ps.setObject(10, stockableItmFlg);
        //   ps.setObject(11, lower);
        //   ps.setObject(12, upper);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            innerMap = new HashMap();
            innerMap.put("itmId", rs.getObject("Itm_Id".toUpperCase()));
            innerMap.put("itmNm", rs.getObject("Itm_Desc".toUpperCase()));
            innerMap.put("basicPrice", rs.getObject("Price_Basic".toUpperCase()));
            innerMap.put("grpNm", rs.getObject("Grp_Nm".toUpperCase()));
            list.add(innerMap);
        }
        rs.close();
        ps.close();
        System.out.println("here i am");
        if (list.isEmpty()) {
            map.put("itmList", list);
            map.put("status", "false");
            map.put("msg", "No Data Found.");
        } else {
            map.put("itmList", list);
            map.put("status", "true");
            map.put("msg", "Data Found.");
        }
        return map;
    }


    @SuppressWarnings("unchecked")
    public Map getItmGnrlInfo(String cldId, int slocId, String horgId, String itmId) throws SQLException {

        Map map = new HashMap();
        String query =
            "SELECT    \n" + "a.itm_id ITM_ID,          \n" + "a.itm_desc ITM_DESC,          \n" +
            "a.PRICE_BASIC PRICE_BASIC,    \n" + "a.PRICE_PUR,    \n" + "a.PRICE_SLS,    \n" + "a.SRVC_ITM_FLG,    \n" +
            "a.STOCKABLE_FLG,    \n" + "a.SLS_ITM_FLG,    \n" + "a.PUR_ITM_FLG,    \n" + "a.WIP_ITM_FLG,    \n" +
            "a.TAX_EXMPT_FLG,    \n" + "a.CAPITAL_GD_FLG,    \n" + "a.QC_REQD_FLG,    \n" + "a.CONSUMABLE_FLG,    \n" +
            "a.CASH_PUR_FLG,    \n" + "a.SERIALIZED_FLG,    \n" + "a.SAMPL_ITM_FLG,    \n" +
            "uB.uom_nm uomBAsic,    \n" + "uP.uom_nm uomPurchase,    \n" + "uS.uom_nm uomSales,       \n" +
            "app.fn_get_itm_att(a.cld_id,a.sloc_id,a.ho_org_id,a.itm_id,'c',',') att    \n" +
            "FROM app.APP$ITM$PRF a,            \n" + "app.APP$GRP g ,    \n" + "app.APP$UOM$CONV$STD uB,    \n" +
            "app.APP$UOM$CONV$STD uP,    \n" + "app.APP$UOM$CONV$STD uS    \n" + "WHERE a.ACTV='Y'    \n" +
            "and a.UOM_BASIC = uB.uom_id    \n" + "and a.UOM_PUR =uP.uom_id    \n" + "and a.UOM_SLS=uS.uom_id    \n" +
            "AND a.grp_id=g.grp_id      \n" + "AND a.cld_id=? \n" + "AND a.sloc_id=?                   \n" +
            "AND a.ho_org_id=?    \n" + "AND a.itm_id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setObject(1, cldId);
        ps.setObject(2, slocId); /*  */
        ps.setObject(3, horgId);
        ps.setObject(4, itmId);
        ResultSet rs = ps.executeQuery();


        while (rs.next()) {
            map.put("itmId", rs.getObject("Itm_Id".toUpperCase()));
            map.put("itmNm", rs.getObject("Itm_Desc".toUpperCase()));
            map.put("basicPrice", rs.getObject("Price_Basic".toUpperCase()));
            map.put("purPrice", rs.getObject("Price_Pur".toUpperCase()));
            map.put("slsPrice", rs.getObject("Price_Sls".toUpperCase()));
            map.put("serviceItem", rs.getObject("Srvc_Itm_Flg".toUpperCase()));
            map.put("stockableItem", rs.getObject("Stockable_Flg".toUpperCase()));
            map.put("slsItem", rs.getObject("Sls_Itm_Flg".toUpperCase()));
            map.put("purItem", rs.getObject("Pur_Itm_Flg".toUpperCase()));
            map.put("wipItem", rs.getObject("Wip_Itm_Flg".toUpperCase()));
            map.put("taxExemptedItem", rs.getObject("Tax_Exmpt_Flg".toUpperCase()));
            map.put("capitalItem", rs.getObject("Capital_Gd_Flg".toUpperCase()));
            map.put("qcReqdItem", rs.getObject("Qc_Reqd_Flg".toUpperCase()));
            map.put("consumableItem", rs.getObject("Consumable_Flg".toUpperCase()));
            map.put("cashPurItem", rs.getObject("Cash_Pur_Flg".toUpperCase()));
            map.put("serializedItem", rs.getObject("Serialized_Flg".toUpperCase()));
            map.put("sampleItem", rs.getObject("Sampl_Itm_Flg".toUpperCase()));
            map.put("uomOfBasic", rs.getObject("Uombasic".toUpperCase()));
            map.put("uomOfPurchase", rs.getObject("Uompurchase".toUpperCase()));
            map.put("uomOfSales", rs.getObject("Uomsales".toUpperCase()));
            map.put("itmAttributes",
                    (rs.getObject("Att".toUpperCase()) == null) ? "Attribute not define." :
                    rs.getObject("Att".toUpperCase()));
        }
        rs.close();
        ps.close();
        if (map.isEmpty()) {
            map.put("status", "N");
            map.put("msg", "No Data Found.");
            return map;
        } else {
            map.put("status", "Y");
            return map;
        }
    }

    @SuppressWarnings("unchecked")
    public Map getItmStockInfo(String cldId, int slocId, String horgId, String itmId) throws SQLException {
        String query =
            "SELECT     \n" + "a.itm_id ITM_ID,           \n" + "a.itm_desc ITM_DESC,           \n" +
            "a.MAX_STK,     \n" + "a.MIN_STK,     \n" + "a.SAFE_QTY,     \n" + "p.att_nm pick_Order,     \n" +
            "a.REORDER_LVL,     \n" + "a.SHELF_LIFE,     \n" + "d.att_nm cost_mthd     \n" +
            "FROM app.APP$ITM$PRF a,     \n" + "app.app$ds$att d,     \n" + "app.app$ds$att p  \n" +
            "WHERE a.ACTV='Y'     \n" + "AND a.cost_mthd = d.att_id    \n" + "AND a.pick_order = p.att_id  \n" +
            "AND a.cld_id=? \n" + "AND a.sloc_id=?                     \n" + "AND a.ho_org_id=? \n" +
            "AND a.itm_id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setObject(1, cldId);
        ps.setObject(2, slocId); /*  */
        ps.setObject(3, horgId);
        ps.setObject(4, itmId);

        Map map = new HashMap();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            map.put("itmId", rs.getObject("Itm_Id".toUpperCase()));
            map.put("itmNm", rs.getObject("Itm_Desc".toUpperCase()));
            map.put("maxStock",
                    (rs.getObject("Max_Stk".toUpperCase()) == null) ? 0 : rs.getObject("Max_Stk".toUpperCase()));
            map.put("minStock",
                    (rs.getObject("Min_Stk".toUpperCase()) == null) ? 0 : rs.getObject("Min_Stk".toUpperCase()));
            map.put("safeStock",
                    (rs.getObject("Safe_Qty".toUpperCase()) == null) ? 0 : rs.getObject("Safe_Qty".toUpperCase()));
            map.put("pickOrder", rs.getObject("Pick_Order".toUpperCase()));
            map.put("recordLvl",
                    (rs.getObject("Reorder_Lvl".toUpperCase()) == null) ? 0 :
                    rs.getObject("Reorder_Lvl".toUpperCase()));
            map.put("selfLife",
                    (rs.getObject("Shelf_Life".toUpperCase()) == null) ? 0 : rs.getObject("Shelf_Life".toUpperCase()));
            map.put("costMthd", rs.getObject("Cost_Mthd".toUpperCase()));
        }
        rs.close();
        ps.close();
        if (map.isEmpty()) {
            map.put("status", "N");
            map.put("msg", "No Data Found.");
            return map;
        } else {
            map.put("status", "Y");
            return map;
        }
    }


    @SuppressWarnings("unchecked")
    public Map getItmCoaInfo(String cldId, int slocId, String orgId, String itmId) throws SQLException {

        Map map = new HashMap();
        Map innerMap = null;
        List list = new ArrayList();
        String query =
            "SELECT     \n" + "A.CLD_ID,     \n" + "A.SLOC_ID,     \n" + "A.ORG_ID,     \n" + "A.ITM_ID,     \n" +
            "A.COA_FOR,     \n" + "B.ATT_NM COA_FOR_DESC,     \n" + "A.COA_ID,     \n" + "C.COA_NM    \n" +
            "FROM APP.APP$ITM$COA A, APP.APP$DS$ATT B, FIN.FIN$COA C    \n" + "WHERE A.COA_FOR = B.ATT_ID    \n" +
            "AND A.CLD_ID = C.COA_CLD_ID    \n" + "AND A.SLOC_ID = C.COA_SLOC_ID    \n" +
            "AND A.HO_ORG_ID = C.COA_HO_ORG_ID    \n" + "AND A.COA_ID = C.COA_ID \n" + "AND A.CLD_ID  = ?    \n" +
            "AND A.SLOC_ID   = ?\n" + "AND A.ORG_ID    = ?\n" + "AND A.ITM_ID = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setObject(1, cldId);
        ps.setObject(2, slocId); /*  */
        ps.setObject(3, orgId);
        ps.setObject(4, itmId);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            innerMap = new HashMap();
            innerMap.put("coaFor", rs.getObject("Coa_For_Desc".toUpperCase()));
            innerMap.put("coaNm", rs.getObject("Coa_Nm".toUpperCase()));
            list.add(innerMap);
        }
        rs.close();
        ps.close();
        if (list.isEmpty()) {
            map.put("coaList", list);
            map.put("status", "N");
            map.put("msg", "No Data Found.");
        } else {
            map.put("status", "Y");
            map.put("coaList", list);
        }
        return map;
    }


    @SuppressWarnings("unchecked")
    public Map getPurchaseHistory(String cldId, int slocId, String orgId, String grpId,
                                  String itmDesc) throws SQLException {

        Map map = new HashMap();
        List list = new ArrayList();
        Map innerMap = null;
        String query =
            "SELECT A.CLD_ID,\n" + "  A.SLOC_ID,\n" + "  A.ORG_ID,\n" + "  B.ITM_ID,\n" + "  C.ITM_DESC,\n" +
            "  D.UOM_DESC,\n" + "  G.GRP_NM,\n" + "  SUM(B.FINAL_RCPT_QTY_BS) ITM_QTY,\n" +
            "  SUM(B.LND_PRICE_BS * B.FINAL_RCPT_QTY_BS) ITM_AMOUNT\n" + "FROM MM.MM$MTL$RCPT A,\n" +
            "  MM.MM$MTL$RCPT$ITM B,\n" + "  APP.APP$ITM$PRF C,\n" + "  APP.APP$UOM$CONV$STD D,\n" +
            "  APP.APP$GRP G\n" + "WHERE A.FY_ID   = APP.PKG_APP.GET_ORG_FY_ID(SYSDATE, A.ORG_ID,'FY')\n" +
            "AND A.CLD_ID    = B.CLD_ID\n" + "AND A.SLOC_ID   = B.SLOC_ID\n" + "AND A.ORG_ID    = B.ORG_ID\n" +
            "AND A.DOC_ID    = B.DOC_ID\n" + "AND B.CLD_ID    = C.CLD_ID\n" + "AND B.SLOC_ID   = C.SLOC_ID\n" +
            "AND C.HO_ORG_ID = MM.MM_GET_HO_ORG(B.CLD_ID, B.SLOC_ID, B.ORG_ID)\n" + "AND B.ITM_ID    = C.ITM_ID\n" +
            "AND C.CLD_ID    = D.CLD_ID\n" + "AND C.SLOC_ID   = D.SLOC_ID\n" + "AND C.UOM_BASIC = D.UOM_ID\n" +
            "AND c.cld_id    = g.cld_id\n" + "AND c.sloc_id   = g.sloc_id\n" + "AND c.ho_org_id = g.ho_org_id\n" +
            "AND C.grp_id    =G.grp_id\n" + "AND A.CLD_ID    = ?\n" + "AND a.sloc_id   = ?\n" +
            "AND a.org_id    = ?\n" + "AND C.grp_id    =NVL(?,C.grp_id)\n" +
            "AND upper(C.itm_desc) LIKE DECODE (?, NULL, upper(C.itm_desc), '%'\n" + "  ||upper(?)\n" + "  ||'%')\n" +
            "GROUP BY A.CLD_ID,\n" + "  A.SLOC_ID,\n" + "  A.ORG_ID,\n" + "  TO_CHAR(A.RCPT_DT,'MON'),\n" +
            "  B.ITM_ID,\n" + "  C.ITM_DESC,\n" + "  D.UOM_DESC,\n" + "  G.GRP_NM";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setObject(1, cldId);
        ps.setObject(2, slocId);
        ps.setObject(3, orgId);
        ps.setObject(4, grpId);
        ps.setObject(5, (grpId.equals("null") ? null : grpId));
        ps.setObject(6, (itmDesc.equals("null") ? null : itmDesc));

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            innerMap = new HashMap();
            innerMap.put("itmId", rs.getObject("Itm_Id".toUpperCase()));
            innerMap.put("itmNm", rs.getObject("Itm_Desc".toUpperCase()));
            innerMap.put("uomNm", rs.getObject("Uom_Desc".toUpperCase()));
            innerMap.put("grpNm", rs.getObject("Grp_Nm".toUpperCase()));
            innerMap.put("itmQty", rs.getObject("Itm_Qty".toUpperCase()));
            innerMap.put("itmAmount",
                         (rs.getObject("Itm_Amount".toUpperCase()) == null) ? 0 :
                         rs.getObject("Itm_Amount".toUpperCase()));
            list.add(innerMap);
        }
        rs.close();
        ps.close();
        if (list.isEmpty()) {
            map.put("purHistory", list);
            map.put("status", "N");
            map.put("msg", "No Data Found.");
        } else {
            map.put("status", "Y");
            map.put("purHistory", list);
        }
        return map;
    }


    @SuppressWarnings("unchecked")
    public Map getApprovalDocList(String cldId, int slocId, String horgId, String orgId,
                                  int usrId) throws SQLException {
        Map map = new HashMap();
        Map inner = null;
        List list = new ArrayList();
        int count = 0;
        String docQuery =
            "select * from\n" + "(SELECT GLBL_DOC_ID,\n" + "  GLBL_DOC_NM,\n" +
            "  MM.MM_GET_DOC_CNT(?,?,?,GLBL_DOC_ID,?,'M') tot_count\n" + "FROM app.app$glbl$doc\n" +
            "WHERE glbl_doc_appl_struct=00305\n" + "AND GLBL_DOC_WRKFLW       ='Y') a\n" + "where a.tot_count >0\n" +
            "ORDER BY a.GLBL_DOC_NM";
        PreparedStatement ps = con.prepareStatement(docQuery);
        ps.setObject(1, cldId);
        ps.setObject(2, slocId);
        ps.setObject(3, orgId);
        ps.setObject(4, usrId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            inner = new HashMap();
            inner.put("docId", rs.getString("GLBL_DOC_ID"));
            inner.put("count", rs.getString("tot_count"));
            inner.put("docNm", rs.getString("GLBL_DOC_NM"));
            list.add(inner);
        }
        rs.close();
        ps.close();

        //2028 Item Profile
        /* count = getPendingDocumentCount(2028, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 2028);
        inner.put("count", count);
        inner.put("docNm", "Item Profile");
        list.add(inner); */
        //18502 Request For Quotation
        /* count = getPendingDocumentCount(18502, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18502);
        inner.put("count", count);
        inner.put("docNm", "Request For Quotation");
        list.add(inner);; */
        //18503 Quotation
        /*     count = getPendingDocumentCount(18503, "M", cldId, slocId, orgId, usrId);
        inner = new HashMap();
        inner.put("docId", 18503);
        inner.put("count", count);
        inner.put("docNm", "Quotation");
        list.add(inner); */


        //18504 Purchase Order
        /*   count = getPendingDocumentCount(18504, "M", cldId, slocId, orgId, usrId);
        inner = new HashMap();
        inner.put("docId", 18504);
        inner.put("count", count);
        inner.put("docNm", "Purchase Order");
        list.add(inner); */


        //18508 Scrap Sales
        /* count = getPendingDocumentCount(18508, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18508);
        inner.put("count", count);
        inner.put("docNm", "Scrap Sales");
        list.add(inner);
        //18510 Quotation Analysis
        count = getPendingDocumentCount(18510, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18510);
        inner.put("count", count);
        inner.put("docNm", "Quotation Analysis");
        list.add(inner);
        //18513 Material Requisition Slip
        count = getPendingDocumentCount(18513, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18513);
        inner.put("count", count);
        inner.put("docNm", "Material Requisition Slip");
        list.add(inner);
        //18515 Material Receipt
        count = getPendingDocumentCount(18515, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18515);
        inner.put("count", count);
        inner.put("docNm", "Material Receipt");
        list.add(inner);
        //18517 Stock Adjustment
        count = getPendingDocumentCount(18517, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18517);
        inner.put("count", count);
        inner.put("docNm", "Stock Adjustment");
        list.add(inner);
        //18518 Stock Taking
        count = getPendingDocumentCount(18518, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18518);
        inner.put("count", count);
        inner.put("docNm", "Stock Taking");
        list.add(inner);
        //18519 Transfer Order
        count = getPendingDocumentCount(18519, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18519);
        inner.put("count", count);
        inner.put("docNm", "Transfer Order");
        list.add(inner);
        //18521 Purchase Invoice
        count = getPendingDocumentCount(18521, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18521);
        inner.put("count", count);
        inner.put("docNm", "Purchase Invoice");
        list.add(inner);
        //18528 Material Return Note
        count = getPendingDocumentCount(18528, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18528);
        inner.put("count", count);
        inner.put("docNm", "Material Return Note");
        list.add(inner);
        //18529 Purchase Return
        count = getPendingDocumentCount(18529, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18529);
        inner.put("count", count);
        inner.put("docNm", "Purchase Return");
        list.add(inner);
        //18534 Cash Purchase Order
        count = getPendingDocumentCount(18534, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18534);
        inner.put("count", count);
        inner.put("docNm", "Cash Purchase Order");
        list.add(inner);
        //18535 Import Declaration Form
        count = getPendingDocumentCount(18535, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18535);
        inner.put("count", count);
        inner.put("docNm", "Import Declaration Form");
        list.add(inner); */
        if (list.isEmpty()) {
            map.put("docTyp", list);
            map.put("status", "N");
            map.put("msg", "No Data Found.");
        } else {
            map.put("status", "Y");
            map.put("docTyp", list);
        }
        return map;
    }

    public Integer getPendingDocumentCount(int docId, String docOperationTyp, String cldId, int slocId, String orgId,
                                           int usrId) throws SQLException {


        Object count =
            (Object) db.callStoredFunction(Types.INTEGER, "MM.MM_GET_DOC_CNT", con, cldId, slocId, orgId, docId, usrId,
                                           docOperationTyp);
        int docCount = (count == null ? 0 : ((Integer) count).intValue());
        return docCount;
    }

    @SuppressWarnings("unchecked")
    public Map getPendingDocumentList(String cldId, int slocId, String horgId, String orgId, int usrId,
                                      String approvalTyp, int docId, int lowerLimit,
                                      int upperLimit) throws SQLException {
        Map map = new HashMap();
        List list = new ArrayList();
        String query =
            "SELECT *\n" + "FROM\n" + "  (SELECT A.*,\n" + "    ROWNUM RNUM\n" + "  FROM\n" +
            "    (SELECT A.CLD_ID,\n" + "      A.Sloc_Id,\n" + "      --A.HO_ORG_ID,\n" + "      A.ORG_ID,\n" +
            "      A.Doc_Id,\n" + "      A.PO_Id,\n" + "      A.PO_DT,\n" + "      A.EO_NM,\n" +
            "      A.PO_AMT_SP,\n" + "      A.PO_AMT_BS,\n" + "      A.CURR_DESC,\n" + "      A.USR_ID,\n" +
            "      A.OP_TYPE,\n" + "      B.USR_NAME,\n" + "      A.REMARKS,\n" + "      a.po_type_nm,\n" +
            "      a.usr_id_create_nm\n" + "    FROM MM.MM$PO$PND$MOB$VW A,\n" + "      APP.APP$SEC$USR B\n" +
            "    WHERE A.CLD_ID = ?\n" + "    AND A.SLOC_ID  = ?\n" + "    AND A.Org_Id   = ?\n" +
            "    AND A.USR_ID   = ?\n" + "    AND A.OP_TYPE  = ?\n" + "    AND A.USR_ID   = B.USR_ID\n" +
            "    AND A.Sloc_Id  = B.Sloc_Id\n" + "    ORDER BY A.PO_ID DESC\n" + "    ) A\n" + "  WHERE ROWNUM <= ?\n" +
            "  )\n" + "WHERE RNUM >= ?";

        String penDocQuery =
            "SELECT * FROM\n" + "  (SELECT A.*,\n" + "    ROWNUM RNUM\n" + "  FROM\n" + "    (SELECT *\n" +
            "    FROM TABLE( CAST( MM_GET_PND_DOC_DTL( ?, ?, ?, ?,?, ?,? ) AS MM$PND$DOC$TAB$TYPE$NEW))\n" +
            "    ) A WHERE ROWNUM <= ?\n" + "  ) WHERE RNUM       >= ?";
        try {
            PreparedStatement ps = null;
            boolean flg = false;
            /*  switch (docId) {
            case 18504:
                {
                    ps = con.prepareStatement(query);
                    flg = true;
                    break;
                }
            }
            if (flg) {
                ps.setObject(1, cldId);
                ps.setObject(2, slocId);
                ps.setObject(3, orgId);
                ps.setObject(4, usrId);
                ps.setObject(5, approvalTyp);
                ps.setObject(6, upperLimit);
                ps.setObject(7, lowerLimit);

                ResultSet rs = ps.executeQuery();
                list = this.getWfDocDetailsList(rs, 18504);
                rs.close();
                ps.close();
            } */

            ps = con.prepareStatement(penDocQuery);
            ps.setObject(1, cldId);
            ps.setObject(2, slocId);
            ps.setObject(3, orgId);
            ps.setObject(4, 0);
            ps.setObject(5, docId);
            ps.setObject(6, approvalTyp);
            ps.setObject(7, usrId);
            ps.setObject(8, upperLimit);
            ps.setObject(9, lowerLimit);
            ResultSet rs = ps.executeQuery();
            list = this.getWfDocDetailsList(rs);
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list.isEmpty()) {
            map.put("docList", list);
            map.put("status", "N");
            map.put("msg", "No Data Found.");
        } else {
            map.put("status", "Y");
            map.put("docList", list);
        }
        return map;
    }

    @SuppressWarnings({ "oracle.jdeveloper.java.unchecked-conversion-or-cast", "unchecked", "unchecked", "unchecked" })
    public List getWfDocDetailsList(ResultSet rs) throws SQLException {
        HashMap<Object, Object> h;
        List list = new ArrayList();
        while (rs.next()) {
            h = new HashMap<Object, Object>();
            h.put("Id", rs.getObject("Doc_Id".toUpperCase()));
            h.put("crtDt", DateFormat.getDateInstance().format(rs.getObject("DOC_DT".toUpperCase())));
            h.put("docId", rs.getObject("DOC_NO".toUpperCase()));
            h.put("narr", (rs.getObject("Remarks".toUpperCase()) == null ? "" : rs.getObject("Remarks".toUpperCase())));
            h.put("docTyp", rs.getObject("GLBL_DOC_Type_Nm".toUpperCase()));
            h.put("crtBy", rs.getObject("Usr_Id_Create_Nm".toUpperCase()));
            list.add(h);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public Map getPurchaseOrderDetail(String cldId, int slocId, String HoOrgId, String orgId, String docId,
                                      String glblDocId, String usrId) throws SQLException {
        String headerQuery =
            "SELECT *\n" +
            "FROM TABLE(CAST(MM.MM_GET_PND_DOC_DTL(?,?,?,NULL,?,?,?) AS MM.MM$PND$DOC$TAB$TYPE$NEW)) where doc_id=?";

        String dtlQuery =
            "SELECT *\n" + "FROM TABLE(CAST(MM.MM_GET_PND_DOC_ITM_DTL(?,?,?,?,?,?,?) AS MM$PND$DOC$ITM$TAB$TYPE))";

        System.out.println("id are: " + cldId + " -- " + slocId + " -- " + HoOrgId + " -- " + orgId + " -- " + docId +
                           " -- " + glblDocId + " -- " + usrId);
        Map v = new LinkedHashMap();
        PreparedStatement ps = con.prepareStatement(headerQuery);
        ps.setObject(1, cldId);
        ps.setObject(2, slocId);
        ps.setObject(3, orgId);
        ps.setObject(4, glblDocId);
        ps.setObject(5, "M");
        ps.setObject(6, Integer.parseInt(usrId));
        ps.setObject(7, docId);

        ResultSet rs = ps.executeQuery();
        Map screen = new HashMap();
        Map param = new HashMap();
        List soDetail = new ArrayList();
        List soItmDtl = new ArrayList();

        if (rs.next()) {
            screen.put("heading", rs.getString("GLBL_DOC_TYPE_NM")); /*  */
            screen.put("subheading", "Details");

            param.put("WfId", rs.getString("WF_ID"));
            param.put("DocId", rs.getString("GLBL_DOC_ID"));
            param.put("DocTxnId", rs.getString("DOC_ID"));
            param.put("DocTypId", rs.getString("GLBL_DOC_TYPE_ID"));

            soDetail.add(getMapData("Document No", rs.getString("DOC_NO")));
            soDetail.add(getMapData("Date", rs.getString("DOC_DT")));
            soDetail.add(getMapData("Currency", rs.getString("CURR_VAL")));
            soDetail.add(getMapData("Amount", rs.getString("AMOUNT")));
        }
        rs.close();
        ps.close();

        PreparedStatement ps1 = con.prepareStatement(dtlQuery);
        ps1.setObject(1, cldId);
        ps1.setObject(2, slocId);
        ps1.setObject(3, orgId);
        ps1.setObject(4, HoOrgId);
        ps1.setObject(5, docId);
        ps1.setObject(6, glblDocId);
        ps1.setObject(7, "M");
        ResultSet rs1 = ps1.executeQuery();
        int count = 0;
        boolean a = false;
        while (a = rs1.next()) {
            System.out.println("under " + a);
            Map item = new LinkedHashMap();
            List top = new ArrayList();
            List mid = new ArrayList();
            List bottom = new ArrayList();

            top.add(getMapDataForItmDtl("SrNo", "" + ++count, "String", true, false));
            top.add(getMapDataForItmDtl("Item Description", rs1.getString("ITM_ID"), "String", true, false));

            mid.add(getMapDataForItmDtl("Rate", rs1.getString("PRICE"), "String", true, false));
            mid.add(getMapDataForItmDtl("Qty", rs1.getString("QTY"), "String", true, false));
            //  mid.add(getMapDataForItmDtl("Discount", rs1.getString("DISCOUNT"), "String", true, false));
            bottom.add(getMapDataForItmDtl("Amount", rs1.getString("AMOUNT"), "String", true, false));

            item.put("top", top);
            item.put("mid", mid);
            item.put("bottom", bottom);
            Map m1 = new HashMap();
            m1.put("item", item);
            soItmDtl.add(m1);
        }
        System.out.println("out: " + a);
        rs1.close();
        ps1.close();

        /*
        v.put("status", "true");
        v.put("message", "Record Found");
        v.put("screenDetail", screen);
        v.put("parm", param);
        v.put("SoDetail", soDetail);
        v.put("SoItmDetail", soItmDtl); */
        if (screen.size() == 0 || param.size() == 0 || soDetail.size() == 0 || soItmDtl.size() == 0) {
            v.put("status", false);
            v.put("message", "Record not Found");
        } else {
            v.put("status", true);
            v.put("message", "Record Found");
            v.put("screenDetail", screen);
            v.put("parm", param);
            v.put("SoDetail", soDetail);
            v.put("SoItmDetail", soItmDtl);
        }

        return v;
    }

    @SuppressWarnings("unchecked")
    public String getImgPath(String cldId, int slocId, String hoId, String itmId) throws SQLException {
        String path = "N";
        String query =
            "SELECT  \n" + "    ITM_ID,  \n" + "    IMG_PATH \n" + "FROM  \n" + "    APP.APP$ITM$IMG \n" +
            "    WHERE ACTV = 'Y' \n" + "    AND CLD_ID =? \n" + "    AND SLOC_ID =? \n" + "    AND HO_ORG_ID =?\n" +
            "    AND ITM_ID =? ";

        PreparedStatement ps = con.prepareStatement(query);
        ps.setObject(1, cldId);
        ps.setObject(2, slocId);
        ps.setObject(3, hoId);
        ps.setObject(4, itmId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            path = (String) rs.getObject("Img_Path".toUpperCase());
        }
        rs.close();
        ps.close();
        return path;
    }

    public Map getAlertCountWS(String cldId, int slocId, String horgId, String orgId, int usrId) throws SQLException {
        Map map = new HashMap();
        String query =
            "select a.alrt_txt,     \n" + "a.alrt_dt     \n" + "from APP.APP$MOB$ALRT a \n" +
            "where a.cld_id = ?    \n" + "AND a.sloc_id = ?  \n" + "AND a.ho_org_id = ?\n" + "AND a.org_id = ?    \n" +
            "AND a.usr_id = ?\n" + "AND a.glbl_appli_struct_id = ?    \n";

        String totPendDocQuery =
            "SELECT sum(tot_count)\n" + "FROM\n" + "  (SELECT GLBL_DOC_ID,\n" + "    GLBL_DOC_NM,\n" +
            "    MM.MM_GET_DOC_CNT(?,?,?,GLBL_DOC_ID,?,'M') tot_count\n" + "  FROM app.app$glbl$doc\n" +
            "  WHERE glbl_doc_appl_struct=00305\n" + "  AND GLBL_DOC_WRKFLW       ='Y'\n" + "  ) a\n" +
            "WHERE a.tot_count >0\n" + "ORDER BY a.GLBL_DOC_NM";
        System.out.println("MM alert apr: " + cldId + " -- " + slocId + " -- " + horgId + " -- " + orgId + " -- " +
                           usrId);
        PreparedStatement ps = con.prepareStatement(query);
        ps.setObject(1, cldId);
        ps.setObject(2, slocId);
        ps.setObject(3, horgId);
        ps.setObject(4, orgId);
        ps.setObject(5, usrId);
        ps.setObject(6, "00305");
        ResultSet rs = ps.executeQuery();
        int count = 0;
        while (rs.next()) {
            count++;
        }
        rs.close();
        ps.close();

        PreparedStatement ps1 = con.prepareStatement(totPendDocQuery);
        ps1.setObject(1, cldId);
        ps1.setObject(2, slocId);
        ps1.setObject(3, orgId);
        ps1.setObject(4, usrId);
        ResultSet rs1 = ps1.executeQuery();
        String totDoc = null;
        if (rs1.next()) {
            totDoc = rs1.getString(1);
        }
        rs1.close();
        ps1.close();
        //Integer purOrdrCount = getPendingDocumentCount(18504, "M", cldId, slocId, orgId, usrId); //PO
        // if (purOrdrCount == null) {
        if (totDoc == null) {
            map.put("pendingDocCount", totDoc);
            map.put("alertCount", count);
            map.put("status", "N");
            map.put("msg", "No Data Found.");
        } else {
            map.put("status", "Y");
            map.put("pendingDocCount", totDoc);
            map.put("alertCount", count);
        }
        return map;
    }

    public Map getItemInformation(String cldId, Integer slocId, String hoOrgId, String orgId, String itmId) {
        Map map = new HashMap();
        CallableStatement st = null;
        try {
            st = con.prepareCall("{call MM.MM_ITM_DTL_WS(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            st.setObject(1, cldId);
            st.setObject(2, slocId);
            st.setObject(3, hoOrgId);
            st.setObject(4, orgId);
            st.setObject(5, itmId);

            st.registerOutParameter(6, Types.VARCHAR);
            st.registerOutParameter(7, Types.NUMERIC);
            st.registerOutParameter(8, Types.NUMERIC);
            st.registerOutParameter(9, Types.NUMERIC);
            st.registerOutParameter(10, Types.NUMERIC);
            st.registerOutParameter(11, Types.NUMERIC);
            st.registerOutParameter(12, Types.NUMERIC);
            st.registerOutParameter(13, Types.NUMERIC);
            st.registerOutParameter(14, Types.NUMERIC);
            st.executeQuery();


            map.put("itmId", itmId);
            map.put("itmDesc", st.getString(6));

            List l2 = new ArrayList();
            l2.add(returnMapPair("Base Price", st.getString(9)));
            l2.add(returnMapPair("Addon Price", st.getString(10)));
            l2.add(returnMapPair("MRP", st.getString(11)));
            l2.add(returnMapPair("Discount", st.getString(12)));
            l2.add(returnMapPair("Profitability", st.getString(13)));

            /*   Map m2 = new HashMap();
            m2.put("basePrice", st.getString(9));
            m2.put("addOnPrice", st.getString(10));
            m2.put("mrp", st.getString(11));
            m2.put("discount", st.getString(12));
            m2.put("profitability", st.getString(13)); */


            /*   Map m1 = new HashMap();
            m1.put("totQty", st.getString(7));
            m1.put("resQty", st.getString(8));
            m1.put("demanded", st.getString(14));
            m1.put("turnRatio", "0.0"); */
            List l1 = new ArrayList();
            l1.add(returnMapPair("Total Quantity", st.getString(7)));
            l1.add(returnMapPair("Reserved Quantity", st.getString(8)));
            l1.add(returnMapPair("Demanded", st.getString(14)));
            l1.add(returnMapPair("Turnover Ratio", "0.0"));

            String imgPath = "image?id=";
            String imageQuery =
                "SELECT  \n" + "    ITM_ID,  \n" + "    IMG_PATH \n" + "FROM  \n" + "    APP.APP$ITM$IMG \n" +
                "    WHERE ACTV = 'Y' \n" + "    AND CLD_ID =? \n" + "    AND SLOC_ID =? \n" +
                "    AND HO_ORG_ID =?\n" + "    AND ITM_ID =? ";

            String path = "N";
            PreparedStatement ps = con.prepareStatement(imageQuery);
            ps.setObject(1, cldId);
            ps.setObject(2, slocId);
            ps.setObject(3, hoOrgId);
            ps.setObject(4, itmId);
            ResultSet rs = ps.executeQuery();
            List list = new ArrayList();
            String firstPath = "";
            int count = 0;
            while (rs.next()) {
                if (count == 0) {
                    path = (String) rs.getObject("Img_Path".toUpperCase());
                    firstPath = path;
                } else {
                    path = (String) rs.getObject("Img_Path".toUpperCase());
                }
                Map p1 = new HashMap();
                p1.put("img", imgPath + makeBrowserPath(path));
                list.add(p1);
                count++;
            }
            rs.close();
            ps.close();
            System.out.println("-->" + count);
            if (count == 0) {

                CallableStatement call = con.prepareCall("{? = call app.fn_get_app_img_path(?)}");
                call.setInt(2, slocId);
                call.registerOutParameter(1, Types.VARCHAR);
                call.executeQuery();

                path = call.getString(1);
                int i = path.lastIndexOf("Item\\");
                path = path.substring(0, i);
                path += "logo.png";

                firstPath = path;
                Map p1 = new HashMap();
                p1.put("img", imgPath + makeBrowserPath(path));
                list.add(p1);

                call.close();
            }
            rs.close();
            ps.close();

            Map imgDetails = new HashMap();
            imgDetails.put("thumbImage", imgPath + makeBrowserPath(firstPath));
            imgDetails.put("all", list);

            map.put("priceDetail", l2);
            map.put("stockDetail", l1);
            map.put("imgDetails", imgDetails);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (Exception e) {
                }
            }
        }
        Map all = new HashMap();
        if (map.size() == 0) {
            all.put("status", "false");
            all.put("msg", "Record not found ");
            all.put("itmDetail", map);
        } else {
            all.put("status", "true");
            all.put("msg", "Record found ");
            all.put("itmDetail", map);
        }
        return all;
    }

    public String makeBrowserPath(String str) {
        if (str != null) {
            str = str.replace("\\", "%5C");
            str = str.replace("/", "%5C");
            str = str.replace(":", "%3A");
        }
        return str;
    }

    public Map returnMapPair(String key, String val) {
        Map map = new HashMap();
        map.put("key", key);
        map.put("value", val);
        return map;
    }

    public Map getMapData(String key, String val) {
        Map map = new HashMap();
        map.put("key", key);
        map.put("value", val);
        return map;
    }

    public Map getMapDataForItmDtl(String key, String val, String datatype, boolean showOnList, boolean editable) {
        Map map = new HashMap();
        map.put("key", key);
        map.put("value", val);
        map.put("dataType", datatype);
        map.put("showOnList", showOnList);
        map.put("etitable", editable);
        return map;
    }
}
