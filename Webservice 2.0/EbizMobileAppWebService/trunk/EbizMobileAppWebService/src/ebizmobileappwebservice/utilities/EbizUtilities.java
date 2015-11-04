package ebizmobileappwebservice.utilities;

import com.ess.conn.DBConnection;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EbizUtilities {
    private DBConnection db;
    private Connection con;

    public EbizUtilities() {
        super();
    }

    public EbizUtilities(DBConnection db, Connection con) {
        this.db = db;
        this.con = con;
    }

    public String loginWS(String cldId, int slocId, String userNm, String userPwd, String tknId, String osNm,
                          String dvcNm, int flag) throws SQLException {

        Object callStoredFunction = db.callStoredFunction(Types.VARCHAR, "APP.PKG_SEC.ncrypt", con, userPwd);
        //   System.out.println("out pur: " + callStoredFunction);

        Object output =
            db.callStoredFunction(Types.VARCHAR, "APP.fn_ws_user_login", con, cldId, slocId, userNm,
                                  callStoredFunction.toString(), tknId, osNm, dvcNm, flag);
        //  System.out.println("out pur: " + output);
        return output.toString();
    }

    public String approvalWS(String cldId, int slocId, String horgId, String orgId, String userId, String type,
                             String start, String end) throws SQLException {

        int usrId = Integer.parseInt(userId);
        int st = Integer.parseInt(start);
        int en = Integer.parseInt(end);
        Object output =
            db.callStoredFunction(Types.VARCHAR, "FIN.fn_ws_user_vou_app", con, cldId, horgId, slocId, orgId, usrId,
                                  type, st, en);
        return output.toString();
    }


    public String detailWS(String cldId, String horgId, String slocId, String orgId, String userId,
                           String vouId) throws SQLException {

        int usrId = Integer.parseInt(userId);
        int scId = Integer.parseInt(slocId);
        Object output =
            db.callStoredFunction(Types.VARCHAR, "FIN.fn_ws_vou_dtl", con, cldId, horgId, scId, orgId, usrId, vouId);
        System.out.println("Output is :  " + output);
        if (output != null) {
            return output.toString();
        } else {
            return "{\"status\":\"N\",\"vouDTL\":[],\"vouLines\":[],\"msg\":\"No Data Found.\"}";
        }
    }


    public Map getDbAgeingInfo(String cldId, String orgId, String btkTyp, int usrId) throws SQLException {
        Map map = new HashMap();
        Map innerMap = new HashMap();
        String query =
            "SELECT BKT_ACTIVE,\n" + "  BKT_ARAP_TYP,\n" + "  BKT_CLD_ID,\n" + "  BKT_DEFAULT,\n" + "  BKT_ID,\n" +
            "  BKT_ORG_ID,\n" + "  BKT_RANGE1,\n" + "  BKT_RANGE2,\n" + "  BKT_RANGE3,\n" + "  BKT_RANGE4,\n" +
            "  BKT_USER_ID,\n" + "  USR_ID_CREATE,\n" + "  USR_ID_CREATE_DT,\n" + "  USR_ID_MOD,\n" +
            "  USR_ID_MOD_DT\n" + "FROM FIN.ORG$USR$AGEING_BKT\n" + "WHERE Bkt_Arap_Typ = ?\n" +
            "AND Bkt_User_Id    = ?\n" + "AND Bkt_Cld_Id     = ?\n" + "AND BKT_ORG_ID     = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setObject(1, btkTyp);
        ps.setObject(2, usrId);
        ps.setObject(3, cldId);
        ps.setObject(4, orgId);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            innerMap.put("r1", rs.getObject("BKT_RANGE1"));
            innerMap.put("r2", rs.getObject("BKT_RANGE2"));
            innerMap.put("r3", rs.getObject("BKT_RANGE3"));
            innerMap.put("r4", rs.getObject("BKT_RANGE4"));
        }
        rs.close();
        ps.close();
        List list = this.getDbAgeingList(cldId, orgId);
        if (list.isEmpty()) {
            map.put("btkDetail", innerMap);
            map.put("ageingList", list);
            map.put("status", "N");
            map.put("msg", "No Data Found For Selected Bucket");
        } else {
            map.put("btkDetail", innerMap);
            map.put("ageingList", list);
            map.put("status", "Y");
        }
        return map;
    }

    public List getDbAgeingList(String cldId, String orgId) throws SQLException {
        String query =
            "SELECT org_desc,ar_coa_id,coa_nm,curr_nm,      \n" +
            "amt_sp,amt_adj,amt_os,ar_amt_bs,os_30_days,os_60_days,os_90_days,os_120_days,os_180_days,os_1_yr,os_greater_1_yr FROM       \n" +
            "(select gl_cld_id,1 ar_sloc_id,org_id,org_desc,ar_coa_id,coa_nm,curr_nm,      \n" +
            "sum (ar_amt_sp_signed) amt_sp,      \n" + "sum (ar_amt_adj_signed) amt_adj,      \n" +
            "sum (ar_amt_os_signed) amt_os,      \n" + "sum(a.ar_amt_bs) ar_amt_bs,      \n" +
            "sum(a.os_30_days) os_30_days,      \n" + "sum(a.os_60_days)os_60_days,      \n" +
            "sum(a.os_90_days) os_90_days,      \n" + "sum(a.os_120_days) os_120_days,      \n" +
            "sum(a.os_180_days) os_180_days,      \n" + "sum(a.os_1_yr) os_1_yr,      \n" +
            "sum(a.os_greater_1_yr)  os_greater_1_yr      \n" + "from (      \n" +
            "select a.gl_cld_id,a.ar_org_id org_id,b.org_desc,a.ar_coa_id,c.coa_nm,a.curr_id_sp,e.glbl_curr_desc curr_nm,      \n" +
            "a.ar_vou_id,      \n" + "a.ar_vou_dt,      \n" + "a.ar_amt_sp,      \n" + "a.ar_amt_sp_signed,      \n" +
            "a.ar_amt_adj,      \n" + "a.ar_amt_adj_signed,      \n" + "ar_amt_os,      \n" +
            "ar_amt_os_signed,      \n" + "os_days,      \n" +
            "nvl(a.ar_amt_sp,0)*nvl(A.AR_CC,1) ar_amt_bs,decode(sign(30 - os_days), -1,0,ar_amt_os_signed)      \n" +
            "os_30_days,decode(sign(60 - os_days) * sign(os_days - 31),      \n" +
            "-1,0,ar_amt_os_signed) os_60_days,      \n" +
            "decode(sign(90 - os_days) * sign(os_days - 61), -1,0,ar_amt_os_signed) os_90_days,      \n" +
            "decode(sign(120 - os_days) * sign(os_days - 91),-1,0,ar_amt_os_signed) os_120_days,      \n" +
            "decode(sign(180 - os_days) * sign(os_days - 121),-1,0,ar_amt_os_signed) os_180_days,      \n" +
            "decode(sign(365 - os_days) * sign(os_days - 181),-1,0,ar_amt_os_signed) os_1_yr,      \n" +
            "decode(sign(os_days - 366), -1,0,ar_amt_os_signed) os_greater_1_yr      \n" + "from (      \n" +
            "select gl_cld_id,ar_org_id,ar_sloc_id,ar_coa_id,ar_curr_id_sp curr_id_sp,ar_vou_id,ar_vou_dt,      \n" +
            "ar_ext_doc_no,ar_ext_doc_dt,ar_vou_due_dt,ar_amt_sp,ar_amt_typ,ar_cc,GL_HO_ORG_ID,      \n" +
            "decode(ar_amt_typ,'Dr',nvl(ar_amt_sp,0),'Cr',-nvl(ar_amt_sp,0),nvl(ar_amt_sp,0))      \n" +
            "ar_amt_sp_signed,      \n" + "nvl(ar_amt_adj,0) ar_amt_adj,      \n" +
            "decode(ar_amt_typ,'Dr',nvl(ar_amt_adj,0),'Cr',-nvl(ar_amt_adj,0),nvl(ar_amt_adj,0))      \n" +
            "ar_amt_adj_signed,      \n" +
            "nvl(ar_amt_sp,0) - nvl(ar_amt_adj,0) ar_amt_os,decode(ar_amt_typ,'Dr',nvl(ar_amt_sp,0) -nvl(ar_amt_adj,0),      \n" +
            "'Cr',-(nvl(ar_amt_sp,0) -nvl(ar_amt_adj,0)),nvl(ar_amt_sp,0) - nvl(ar_amt_adj,0))      \n" +
            "ar_amt_os_signed,      \n" + "nvl(to_date(?,'dd-mm-yyyy'),trunc(sysdate)) -      \n" +
            "decode(?,'I',trunc(nvl(ar_ext_doc_dt,ar_vou_dt)),'V',      \n" +
            "ar_vou_dt,'D',trunc(nvl(ar_vou_due_dt,ar_vou_dt)),ar_vou_dt) os_days      \n" + "from fin.ar      \n" +
            "where nvl(ar_amt_sp,0) - nvl(ar_amt_adj,0) > 0    \n" + "and gl_cld_id = ?      \n" +
            "and ar_org_id = ?      \n" + ") a,      \n" + "app.org b,      \n" + "fin.fin$coa c,      \n" +
            "app.app$doc$txn d,      \n" + "app.app$glbl$curr e      \n" + "where a.os_days > 0      \n" +
            "and not exists (select gl_Vou_id from fin.gl where gl.gl_rev_flg in      \n" +
            "('RVS','RVD') and gl_cld_id=a.GL_CLD_ID       \n" +
            "and gl_ho_org_id=a.GL_HO_ORG_ID and gl_org_id=      \n" +
            "a.AR_ORG_ID and gl_vou_id=a.AR_VOU_ID)      \n" + "and d.doc_cld_id = a.gl_cld_id      \n" +
            "and d.doc_org_id = a.ar_org_id      \n" + "and d.doc_sloc_id = a.ar_sloc_id      \n" +
            "and d.doc_txn_id = a.ar_vou_id      \n" + "and b.org_cld_id = a.gl_cld_id      \n" +
            "and b.org_id = a.ar_org_id      \n" + "and c.coa_cld_id = a.gl_cld_id      \n" +
            "and c.coa_sloc_id = a.ar_sloc_id      \n" + "and c.coa_ho_org_id =      \n" +
            "app.fn_get_parent_org_l0(a.gl_cld_id,a.ar_org_id)      \n" + "and c.coa_id = a.ar_coa_id      \n" +
            "and e.glbl_curr_id = a.curr_id_sp      \n" + "and d.doc_org_id = ?      \n" +
            "and d.doc_cld_id = ?      \n" + ") a      \n" + "group by gl_cld_id,      \n" + "org_id,      \n" +
            "org_desc,      \n" + "ar_coa_id,      \n" + "coa_nm,      \n" + "curr_nm      \n" +
            "order by AMT_OS DESC) XXX      \n" + "WHERE ROWNUM<=10";

        PreparedStatement prepareStatement = con.prepareStatement(query);
        prepareStatement.setObject(1, null);
        prepareStatement.setObject(2, null);
        prepareStatement.setObject(3, cldId);
        prepareStatement.setObject(4, orgId);
        prepareStatement.setObject(5, orgId);
        prepareStatement.setObject(6, cldId);

        List list = new ArrayList();
        Map innerMap = null;
        ResultSet executeQuery = prepareStatement.executeQuery();
        while (executeQuery.next()) {
            innerMap = new HashMap();
            innerMap.put("coaNm", executeQuery.getObject("COA_NM"));
            innerMap.put("currNm", executeQuery.getObject("CURR_NM"));
            innerMap.put("amtSp", executeQuery.getObject("AMT_SP"));
            innerMap.put("amtAdj", executeQuery.getObject("AMT_ADJ"));
            innerMap.put("amtOs", executeQuery.getObject("AMT_OS"));
            innerMap.put("arAmtBs", executeQuery.getObject("AR_AMT_BS"));
            innerMap.put("os30Days", executeQuery.getObject("OS_30_DAYS"));
            innerMap.put("os60Days", executeQuery.getObject("OS_60_DAYS"));
            innerMap.put("os90Days", executeQuery.getObject("OS_90_DAYS"));
            innerMap.put("os120Days", executeQuery.getObject("OS_120_DAYS"));
            innerMap.put("os180Days", executeQuery.getObject("OS_180_DAYS"));
            innerMap.put("os1Yr", executeQuery.getObject("OS_1_YR"));
            innerMap.put("osGreater1Yr", executeQuery.getObject("OS_GREATER_1_YR"));
            list.add(innerMap);
        }
        executeQuery.close();
        prepareStatement.close();
        return list;
    }


    public String setBtkRange(String cldId, String orgId, String btkTyp, int usrId, String r1, String r2, String r3,
                              String r4) throws Exception {
        String status = "N";

        String query =
            "SELECT BKT_ACTIVE,\n" + "  BKT_ARAP_TYP,\n" + "  BKT_CLD_ID,\n" + "  BKT_DEFAULT,\n" + "  BKT_ID,\n" +
            "  BKT_ORG_ID,\n" + "  BKT_RANGE1,\n" + "  BKT_RANGE2,\n" + "  BKT_RANGE3,\n" + "  BKT_RANGE4,\n" +
            "  BKT_USER_ID,\n" + "  USR_ID_CREATE,\n" + "  USR_ID_CREATE_DT,\n" + "  USR_ID_MOD,\n" +
            "  USR_ID_MOD_DT\n" + "FROM FIN.ORG$USR$AGEING_BKT\n" + "WHERE Bkt_Arap_Typ = ?\n" +
            "AND Bkt_User_Id    = ?\n" + "AND Bkt_Cld_Id     = ?\n" + "AND BKT_ORG_ID     = ?";
        PreparedStatement ps =
            con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ps.setObject(1, btkTyp);
        ps.setObject(2, usrId);
        ps.setObject(3, cldId);
        ps.setObject(4, orgId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            rs.updateObject("BKT_RANGE1", new Double(r1));
            rs.updateObject("BKT_RANGE2", new Double(r2));
            rs.updateObject("BKT_RANGE3", new Double(r3));
            rs.updateObject("BKT_RANGE4", new Double(r4));
            rs.updateRow();
            status = "Y";
            con.commit();
        }
        rs.close();
        ps.close();
        return status;
    }


    public Map getCashPositionInfo(String cldId, int slocId, String horgId, String orgId) throws SQLException {
        Map map = new HashMap();
        Map innerMap = null;
        List list = new ArrayList();
        String query =
            "select fc.coa_id,fc.coa_nm,  \n" + "oc.org_coa_cl_bal Balance,  \n" +
            "oc.org_coa_cl_bal_typ bal_TYPE  \n" + "from fin.org$coa$fy oc, fin.fin$acc$na fan, fin.fin$coa fc  \n" +
            "where fan.acc_id = oc.org_coa_na_id  \n" + "and fan.acc_type  in (78,79 ) \n" +
            "and fc.coa_acc_id = fan.acc_id  \n" + "and fc.coa_cld_id = fan.acc_cld_id  \n" +
            "and fc.coa_sloc_id = fan.acc_sloc_id  \n" + "and fc.coa_ho_org_id = fan.ho_org_id  \n" +
            "and oc.org_coa_cld_id=fc.coa_cld_id \n" + "and oc.org_coa_ho_org_id=fc.coa_ho_org_id \n" +
            "and oc.org_coa_id=fc.coa_id \n" +
            "and oc.org_Fy_id=(select max (org_fy_id) from app.org$fy f where f.org_id=oc.org_id)\n" +
            "and oc.org_coa_cld_id=?\n" + "and oc.org_sloc_id=?  \n" + "and oc.Org_Coa_Ho_Org_Id=? \n" +
            "and oc.org_id = ?  ";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setObject(1, cldId);
        ps.setObject(2, slocId);
        ps.setObject(3, horgId);
        ps.setObject(4, orgId);
        ResultSet rsi = ps.executeQuery();
        while (rsi.next()) {
            innerMap = new HashMap();
            innerMap.put("coaNm", rsi.getObject("COA_NM"));
            innerMap.put("Balance", rsi.getObject("BALANCE"));
            innerMap.put("BalType", rsi.getObject("BAL_TYPE"));
            list.add(innerMap);
        }
        rsi.close();
        ps.close();
        if (list.isEmpty()) {
            map.put("cashDetail", list);
            map.put("status", "N");
            map.put("msg", "No Data Found.");
        } else {
            map.put("status", "Y");
            map.put("cashDetail", list);
        }
        return map;
    }

    public Map getOrgInfo(String orgId, String cldId) throws SQLException {

        Map map = new HashMap();
        String query =
            "select o.org_id, \n" + "o.org_type, \n" + "o.org_desc, \n" + "o.org_id_parent, \n" +
            "(select org_desc \n" + "from org \n" + "where org_id = o.org_id_parent \n" +
            "and org_cld_id = o.org_cld_id \n" +
            "and org_create_ref_sloc_id = o.org_create_ref_sloc_id) prnt_org_desc, \n" + "o.org_fy_st_dt, \n" +
            "c.curr_desc, \n" + "cc.cntry_desc, \n" + "d.org_add_id1, \n" + "ad1.ADDS ADDRESS_1, \n" +
            "d.org_add_id2, \n" + "ad2.ADDS ADDRESS_2, \n" + "d.org_phone1, \n" + "d.org_phone2, \n" +
            "d.org_mobile1, \n" + "d.org_mobile2, \n" + "d.org_fax1, \n" + "d.org_fax2, \n" + "d.org_website1, \n" +
            "d.org_website2, \n" + "d.org_email1, \n" + "d.org_email2 \n" + "from app.org o,  \n" +
            "app.org$oth$dtl d,  \n" + "app.app$curr c,  \n" + "app.app$cntry cc, \n" + "APP.APP$ADDS$VW AD1, \n" +
            "APP.APP$ADDS$VW AD2 \n" + "where o.org_cld_id = d.org_cld_id \n" + "and o.org_id = d.org_id \n" +
            "and o.org_cld_id = c.cld_id \n" + "and o.org_curr_id_bs = c.curr_id \n" +
            "and o.org_cld_id = cc.cntry_cld_id \n" + "and o.org_country_id = cc.cntry_id \n" +
            "and ad1.CLD_ID(+) = d.org_cld_id \n" + "and ad1.ADDS_ID(+)= d.org_add_id1 \n" +
            "and ad2.CLD_ID(+) = d.org_cld_id \n" + "and ad2.ADDS_ID(+)= d.org_add_id2 \n" + "and o.ORG_CLD_ID = ?\n" +
            "and o.org_id = ?\n";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setObject(1, cldId);
        ps.setObject(2, orgId);
        ResultSet rsi = ps.executeQuery();

        while (rsi.next()) {
            map.put("orgNm", rsi.getObject("ORG_DESC"));
            map.put("orgParentNm",
                    (rsi.getObject("PRNT_ORG_DESC") == null ? rsi.getObject("ORG_DESC") :
                     rsi.getObject("PRNT_ORG_DESC")));
            map.put("orgFyStDt", DateFormat.getDateInstance().format(rsi.getObject("ORG_FY_ST_DT")));
            map.put("orgCurrDesc", rsi.getObject("CURR_DESC"));
            map.put("orgCntryNm", rsi.getObject("CNTRY_DESC"));
            map.put("orgAdd1", (rsi.getObject("ADDRESS_1") == null ? "" : rsi.getObject("ADDRESS_1")));
            map.put("orgAdd2", (rsi.getObject("ADDRESS_2") == null ? "" : rsi.getObject("ADDRESS_2")));
            map.put("orgPhone1", (rsi.getObject("ORG_PHONE1") == null ? "" : rsi.getObject("ORG_PHONE1")));
            map.put("orgPhone2", (rsi.getObject("ORG_PHONE2") == null ? "" : rsi.getObject("ORG_PHONE2")));
            map.put("orgMb1", (rsi.getObject("ORG_MOBILE1") == null ? "" : rsi.getObject("ORG_MOBILE1")));
            map.put("orgMb2", (rsi.getObject("ORG_MOBILE2") == null ? "" : rsi.getObject("ORG_MOBILE2")));
            map.put("orgFax1", (rsi.getObject("ORG_FAX1") == null ? "" : rsi.getObject("ORG_FAX1")));
            map.put("orgFax2", (rsi.getObject("ORG_FAX2") == null ? "" : rsi.getObject("ORG_FAX2")));
            map.put("orgWebSite1", (rsi.getObject("ORG_WEBSITE1") == null ? "" : rsi.getObject("ORG_WEBSITE1")));
            map.put("orgWebSite2", (rsi.getObject("ORG_WEBSITE2") == null ? "" : rsi.getObject("ORG_WEBSITE2")));
            map.put("orgEmail1", (rsi.getObject("ORG_EMAIL1") == null ? "" : rsi.getObject("ORG_EMAIL1")));
            map.put("orgEmail2", (rsi.getObject("ORG_EMAIL2") == null ? "" : rsi.getObject("ORG_EMAIL2")));
        }
        rsi.close();
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
    public Map getNotification(String cldId, int slocId, String horgId, String orgId, int usrId,
                               String moduleId) throws SQLException {
        Map map = new HashMap();
        Map msg = null;
        List list = new ArrayList();
        String query =
            "select alrt_txt,   \n" + "alrt_dt   \n" + "from APP$MOB$ALRT where   \n" + "cld_id = ?   \n" +
            "AND sloc_id = ?\n" + "AND ho_org_id = ?\n" + "AND org_id = ?\n" + "AND usr_id = ?    \n" +
            "AND glbl_appli_struct_id = ?\n";
        String nQuery =
            "SELECT a.ALRT_TITLE,\n" + "  a.doc_id\n" + "FROM app$alrt a,\n" + "  APP$ALRT$USR b,\n" +
            "  app$alrt$usr$media c ,\n" + "  APP$ALRT$SVRTY$CLR d\n" + "WHERE a.CLD_ID             =?\n" +
            "AND a.sloc_id              =?\n" + "AND a.ho_org_id            =?\n" + "AND a.org_id               =?\n" +
            "AND a.GLBL_APPLI_STRUCT_ID = ? -- for MM\n" + "AND a.CLD_ID               =b.cld_id\n" +
            "AND a.sloc_id              =b.sloc_id\n" + "AND a.ho_org_id            =b.HO_ORG_ID\n" +
            "AND a.org_id               =b.ORG_ID\n" + "AND a.doc_id               =b.doc_id\n" +
            "AND b.USR_ID               =?\n" + "AND b.CLD_ID               =c.Cld_Id\n" +
            "AND b.sloc_id              =c.Sloc_Id\n" + "AND b.ho_org_id            =c.Ho_Org_Id\n" +
            "AND b.org_id               =c.Org_Id\n" + "AND b.USR_ID               =c.Usr_Id\n" +
            "AND b.doc_id               =c.doc_id\n" +
            "AND c.alrt_media           = 783 -- for mobile   --783 for dashboard\n" +
            "AND TRUNC(sysdate) BETWEEN TRUNC(c.disp_strt_dt) AND TRUNC(c.disp_end_dt)\n" +
            "AND b.SVRTY_LVL=d.SVRTY_LVL";
        PreparedStatement ps = con.prepareStatement(nQuery);
        ps.setObject(1, cldId);
        ps.setObject(2, slocId);
        ps.setObject(3, horgId);
        ps.setObject(4, orgId);
        ps.setObject(5, moduleId);
        ps.setObject(6, usrId);
        ResultSet rsi = ps.executeQuery();
        String link =
            "alertData?" + "cldId=" + cldId + "&slocId=" + slocId + "&hoOrgId=" + horgId + "&orgId=" + orgId +
            "&usrId=" + usrId + "&modId=" + moduleId;
        while (rsi.next()) {
            msg = new HashMap();
            //            msg.put("msgTxt", rsi.getObject("ALRT_TXT"));
            msg.put("msgTxt", rsi.getObject("ALRT_TITLE"));
            msg.put("msgDate", "");

            //alerttable?cldId=0000&slocId=1&hoOrgId=01&orgId=01&usrId=1&modId=00305&docId=0000.01.01.0001.04oS.2l.1UI5v0CmlM
            link += "&docId=" + rsi.getString("DOC_ID");
            msg.put("link", link);
            //   msg.put("msgDate", DateFormat.getDateInstance().format(rsi.getObject("ALRT_DT")));
            list.add(msg);
        }
        rsi.close();
        ps.close();
        if (list.isEmpty()) {
            map.put("status", "N");
            map.put("msg", "No Data Found.");
        } else {
            map.put("status", "Y");
            map.put("msgList", list);
        }
        return map;
    }


    public Map getAlertCountWS(String cldId, int slocId, String horgId, String orgId, int usrId) throws SQLException {

        Map map = new HashMap();
        String query =
            "select alrt_txt,   \n" + "alrt_dt   \n" + "from APP$MOB$ALRT where   \n" + "cld_id = ?   \n" +
            "AND sloc_id = ?\n" + "AND ho_org_id = ?\n" + "AND org_id = ?\n" + "AND usr_id = ?    \n" +
            "AND glbl_appli_struct_id = ?\n";

        PreparedStatement ps = con.prepareStatement(query);
        ps.setObject(1, cldId);
        ps.setObject(2, slocId);
        ps.setObject(3, horgId);
        ps.setObject(4, orgId);
        ps.setObject(5, usrId);
        ps.setObject(6, "00303");

        ResultSet rsi = ps.executeQuery();
        int count = 0;
        while (rsi.next()) {
            count++;
        }
        rsi.close();
        ps.close();
        // Row[] allRowsInRange = impl.getAllRowsInRange();
        BigDecimal val = new BigDecimal(0.0);
        Double data = null;
        val =
            (BigDecimal) db.callStoredFunction(Types.NUMERIC, "fin.fn_user_vou_cnt", con, cldId, horgId, slocId, orgId,
                                               usrId, "A");
        data = new Double(val.doubleValue());

        int docCount = (data == null ? 0 : data.intValue());
        if (val == null) {
            map.put("pendingDocCount", docCount);
            map.put("alertCount", count);
            map.put("status", "N");
            map.put("msg", "No Data Found.");
        } else {
            map.put("status", "Y");
            map.put("pendingDocCount", docCount);
            map.put("alertCount", count);
        }
        return map;
    }
}
