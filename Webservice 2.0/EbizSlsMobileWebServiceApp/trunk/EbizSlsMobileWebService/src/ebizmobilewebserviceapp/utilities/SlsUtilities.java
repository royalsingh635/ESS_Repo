package ebizmobilewebserviceapp.utilities;

import com.ess.conn.DBConnection;

import com.sun.org.apache.bcel.internal.generic.Select;
import com.sun.rowset.internal.Row;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.IOException;

import java.math.BigDecimal;

import java.nio.file.Path;

import java.nio.file.Paths;

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
import java.util.Properties;

public class SlsUtilities {
    private DBConnection db;
    private Connection con;

    public SlsUtilities() {
        super();
    }

    public SlsUtilities(DBConnection db, Connection con) {
        this.db = db;
        this.con = con;
    }

    @SuppressWarnings({ "unchecked", "oracle.jdeveloper.java.unchecked-conversion-or-cast" })
    public Map getCatgDisBaseList() throws SQLException {
        String eoCatQuery =
            "SELECT \n" + "   ATT_ID CATG_ID, \n" + "   ATT_NM CATG_NM \n" + "FROM \n" + "  APP.APP$DS$ATT_VW \n" +
            "WHERE \n" + "  ATT_TYPE_ID=74";
        String disBasis =
            "SELECT\n" + "  A.ATT_ID DISC_BASIS_ID,\n" + "  A.ATT_NM DISC_BASIS_NM\n" + "FROM \n" +
            "  APP.APP$DS$ATT_VW A \n" + "WHERE \n" + "  A.ATT_TYPE_ID = 129";
        PreparedStatement ps1 = con.prepareStatement(eoCatQuery);
        Map category = new HashMap();
        HashMap<Object, Object> h;
        List list = new ArrayList();

        ResultSet rs1 = ps1.executeQuery();
        while (rs1.next()) {
            h = new HashMap<Object, Object>();
            Object CatgIdO = rs1.getObject("CATG_ID");
            Object CatgNmO = rs1.getObject("CATG_NM");
            h.put("CatgId", CatgIdO);
            h.put("CatgNm", CatgNmO);
            list.add(h);
        }
        rs1.close();
        ps1.close();
        PreparedStatement ps2 = con.prepareStatement(disBasis);
        ResultSet rs2 = ps2.executeQuery();

        HashMap<Object, Object> h1;
        List list1 = new ArrayList();
        while (rs2.next()) {
            h1 = new HashMap<Object, Object>();
            Object DiscBasisIdO = rs2.getObject("DISC_BASIS_ID");
            Object DiscBasisNmO = rs2.getObject("DISC_BASIS_NM");
            h1.put("discBaseId", DiscBasisIdO);
            h1.put("discBaseNm", DiscBasisNmO);
            list1.add(h1);
        }
        rs2.close();
        ps2.close();
        if (list.isEmpty()) {
            category.put("Categories", list);
            category.put("DiscBase", list1);
            category.put("status", "N");
            category.put("msg", "No Data Found For Categories");
        } else if (list1.isEmpty()) {
            category.put("Categories", list);
            category.put("DiscBase", list1);
            category.put("status", "N");
            category.put("msg", "No Data Found For Discount");
        } else {
            category.put("Categories", list);
            category.put("DiscBase", list1);
            category.put("status", "Y");
        }
        return category;
    }


    @SuppressWarnings("unchecked")
    public Map getDiscPolicyList(String CldId, String OrgId, String HoOrgId, Integer SlocId, String ItmNmStr,
                                 String GrpNmStr, Integer eoCatg, String EoNmStr, String SalesExecNmStr,
                                 Integer DiscBasis, Integer RowsUpperLimit,
                                 Integer RowsLowerLimit) throws SQLException {


        Map discPolicy = new HashMap();
        HashMap<Object, Object> h;
        List list = new ArrayList();
        String query =
            "SELECT *\n" + "FROM\n" + "  (SELECT\n" + "    /*+ FIRST_ROWS(n) */\n" + "    A.*,\n" +
            "    ROWNUM RNUM\n" + "  FROM\n" + "    ( --SELECT * FROM (\n" + "    SELECT DISTINCT A.DISC_TYPE,\n" +
            "      A.DISC_VAL,\n" + "      TRUNC(A.EFFECTIVE_DT) EFFECTIVE_DT,\n" +
            "      TRUNC(A.EXPIRY_DT) EXPIRY_DT,\n" + "      A.DISC_BASIS,\n" + "      A.DISCOUNT_ON\n" +
            "    FROM SLS$DISC$PLC_VW A\n" + "    WHERE A.CLD_ID  = ?\n" + "    AND A.SLOC_ID   = ?\n" +
            "    AND A.HO_ORG_ID = ?\n" + "    AND A.ORG_ID    = ?\n" + "    AND UPPER(A.ITM_DESC) LIKE NVL('%'\n" +
            "      ||UPPER(?)\n" + "      ||'%',UPPER(A.ITM_DESC))\n" + "    AND UPPER(A.GRP_DESC) LIKE NVL('%'\n" +
            "      ||UPPER(?)\n" + "      ||'%',UPPER(A.GRP_DESC))\n" + "    AND A.CATG_ID = NVL(?,A.CATG_ID)\n" +
            "    AND UPPER(A.DISCOUNT_ON) LIKE NVL('%'\n" + "      ||UPPER(?)\n" +
            "      ||'%',UPPER(A.DISCOUNT_ON))\n" +
            "      --AND UPPER(A.SLS_EXEC) = NVL('%'||UPPER(:SalesExecNmBind)||'%',UPPER(A.SLS_EXEC))\n" +
            "    AND A.DISC_BASIS_ID = NVL(?,A.DISC_BASIS_ID)\n" + "    GROUP BY A.DISCOUNT_ON,\n" +
            "      A.DISC_BASIS,\n" + "      A.DISC_TYPE,\n" + "      A.DISC_VAL,\n" +
            "      TRUNC(A.EFFECTIVE_DT),\n" + "      TRUNC(A.EXPIRY_DT)\n" + "      --  )\n" + "    ) A\n" +
            "  WHERE ROWNUM <= ?\n" + "  )\n" + "WHERE RNUM >= ?";

        PreparedStatement ps = con.prepareStatement(query);
        ps.setObject(1, CldId);
        ps.setObject(2, SlocId);
        ps.setObject(3, HoOrgId);
        ps.setObject(4, OrgId);
        ps.setObject(5, ItmNmStr);
        ps.setObject(6, GrpNmStr);
        ps.setObject(7, eoCatg);
        ps.setObject(8, EoNmStr);
        ps.setObject(9, DiscBasis);
        ps.setObject(10, RowsLowerLimit);
        ps.setObject(11, RowsUpperLimit);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            h = new HashMap<Object, Object>();
            h.put("DiscType", rs.getObject("DISC_TYPE"));
            h.put("DiscVal", rs.getObject("DISC_VAL"));
            h.put("EffectiveDt", DateFormat.getDateInstance().format(rs.getObject("EFFECTIVE_DT")));
            h.put("ExpiryDt", rs.getObject("EXPIRY_DT"));
            h.put("DiscBasis", rs.getObject("DISC_BASIS"));
            h.put("DiscountOn", rs.getObject("DISCOUNT_ON"));
            list.add(h);
        }
        rs.close();
        ps.close();
        if (list.isEmpty()) {
            discPolicy.put("DiscPolicy", list);
            discPolicy.put("status", "N");
            discPolicy.put("msg", "No Data Found.");
        } else {
            discPolicy.put("DiscPolicy", list);
            discPolicy.put("status", "Y");
        }
        return discPolicy;
    }


    public Map getPendingDocumentForCountMyApproval(Integer slocId, String cldId, String orgId, String hoOrgId,
                                                    Integer usrId) throws SQLException {
        Map map = new HashMap();
        List g = new ArrayList();
        String query =
            "SELECT glbl_doc_id,\n" + "  glbl_doc_nm,\n" + "  approval_Count\n" + "FROM\n" +
            "  (SELECT glbl_doc_id,\n" + "    glbl_doc_nm,\n" +
            "    SLS.SLS_DOC_WF_CNT(?,?,?,?,glbl_doc_id,'M') approval_Count\n" + "  FROM APP.app$glbl$doc\n" +
            "  WHERE GLBL_DOC_APPL_STRUCT=0030601\n" + "  AND glbl_doc_wrkflw       ='Y'\n" +
            "  ORDER BY glbl_doc_id\n" + "  )\n" + "WHERE approval_Count>0";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setObject(1, cldId);
        ps.setObject(2, slocId);
        ps.setObject(3, orgId);
        ps.setObject(4, usrId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Map h = new HashMap<Object, Object>();
            h.put("docId", rs.getString(1));
            h.put("count", rs.getString(3));
            h.put("docNm", rs.getString(2));
            g.add(h);
        }
        rs.close();
        ps.close();
        //   Integer quotationCount = getPendingDocumentCount(21502, "M", slocId, cldId, orgId, hoOrgId, usrId); //Quotation
        //  Integer invoiceCount = getPendingDocumentCount(21504, "M", slocId, cldId, orgId, hoOrgId, usrId); //Invoice
        //  Integer salesOrdrCount = getPendingDocumentCount(21503, "M", slocId, cldId, orgId, hoOrgId, usrId); //Order
        // Integer rmaCount = getPendingDocumentCount(21510, "M", slocId, cldId, orgId, hoOrgId, usrId); //RMA

        /* Map h = new HashMap<Object, Object>();
        h.put("docId", 21503);
        h.put("count", salesOrdrCount);
        h.put("docNm", "Sales Order"); */

        //   g.add(h);

        if (g.isEmpty()) {
            map.put("docTyp", g);
            map.put("status", "N");
            map.put("msg", "No Data Found.");
        } else {
            map.put("docTyp", g);
            map.put("status", "Y");
            map.put("msg", "Data Found.");
        }
        return map;
    }

    public Integer getPendingDocumentCount(Integer DocTypeId, String CountType, Integer slocId, String cldId,
                                           String orgId, String hoOrgId, Integer usrId) {
        Integer count = 0;
        /* sls_doc_wf_cnt(
        p_cld_id varchar2,
        p_sloc_id number,
        p_org_id varchar2,
        p_usr_id number,
        p_doc_id number,
        p_wf_type varchar2 /* O--Pending with others ,M--Pending of my approval,U--Pending for posting
        )
         */
        try {
            BigDecimal s = null;
            //String driver = getdrivername();
            /* if (driver.equalsIgnoreCase("MySQL-AB JDBC Driver")) {
                s = (Integer) ADFModelUtils.callFunction(this, new StringBuilder("CALL SLS_DOC_WF_CNT(?,?,?,?,?,?)"),
                                                            new Object[] {
                                                                    cldId,
                                                                    orgId,
                                                                    hoOrgId,
                                                                    usrId,
                                                                    DocTypeId,
                                                                    CountType.toString()},
                                                            Types.NUMERIC);
            } else { */
            s = (BigDecimal) db.callStoredFunction(Types.NUMERIC, "SLS_DOC_WF_CNT", con, cldId, slocId, orgId, usrId,
                                                   DocTypeId, CountType.toString());

            //}

            count = (s != null ? ((int) (s.doubleValue())) : 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }


    @SuppressWarnings("unchecked")
    public Map getPendingDocumentList(Integer SlocId, String CldId, String OrgId, String HoOrgId, Integer UsrId,
                                      String wfTyp, Integer DocTypeId, Integer RowsLowerLimit,
                                      Integer RowsUpperLimit) throws SQLException {
        String query =
            "SELECT *\n" + "FROM\n" + "  (SELECT A.*,\n" + "    ROWNUM RNUM\n" + "  FROM\n" + "    (SELECT *\n" +
            "    FROM TABLE(CAST(SLS.SLS_GET_PND_DOC_DTL(?,?,?,NULL,?,?,?) AS SLS.SLS$PND$DOC$TAB$TYPE))\n" +
            "    ) A\n" + "  WHERE ROWNUM <= ?\n" + "  )\n" + "WHERE RNUM >= ?";
        PreparedStatement ps = null;
        ps = con.prepareStatement(query);
        Map map = new HashMap();
        List list = new ArrayList();

        ResultSet ctr = null;

        ps.setObject(1, CldId);
        ps.setObject(2, SlocId);
        ps.setObject(3, OrgId);
        ps.setObject(4, DocTypeId);
        ps.setObject(5, wfTyp);
        ps.setObject(6, UsrId);
        ps.setObject(7, RowsLowerLimit);
        ps.setObject(8, RowsUpperLimit);
        ctr = ps.executeQuery();
        list = this.getWfDocDetailsList(ctr, DocTypeId);
        ctr.close();
        ps.close();
        if (list.isEmpty()) {
            map.put("docList", list);
            map.put("status", "N");
            map.put("msg", "No Data Found.");
        } else {
            map.put("docList", list);
            map.put("status", "Y");
        }
        return map;
    }

    @SuppressWarnings({ "oracle.jdeveloper.java.unchecked-conversion-or-cast", "unchecked", "unchecked", "unchecked" })
    public List getWfDocDetailsList(ResultSet itr, Integer docTypId) throws SQLException {
        HashMap<Object, Object> h;
        List list = new ArrayList();

        //System.out.println("Count : "+itr.getRowCountInRange());

        while (itr.next()) {
            h = new HashMap<Object, Object>();
            h.put("Id", itr.getObject("Doc_Id".toUpperCase()));
            h.put("crtDt", DateFormat.getDateInstance().format(itr.getObject("Doc_Dt".toUpperCase())));
            h.put("docId", itr.getObject("DOC_NO".toUpperCase()));
            h.put("narr",
                  (itr.getObject("Remarks".toUpperCase()) == null ? "" : itr.getObject("Remarks".toUpperCase())));
            h.put("docTyp", itr.getString("GLBL_DOC_NM"));
            h.put("crtBy", itr.getObject("USR_ID_CREATE_NM".toUpperCase()));
            list.add(h);
        }
        //System.out.println("Returned: "+list);
        return list;
    }


    @SuppressWarnings("unchecked")
    public Map getDocumentDetails(Integer SlocId, String CldId, String OrgId, String HoOrgId, String docId,
                                  String itmId, String glblDocId, String usrId) throws SQLException {

        String headerQuery =
            "SELECT *\n" +
            "FROM TABLE(CAST(SLS.SLS_GET_PND_DOC_DTL(?,?,?,NULL,?,?,?) AS SLS.SLS$PND$DOC$TAB$TYPE)) where doc_id=?";

        String dtlQuery =
            "SELECT *\n" + "FROM TABLE(CAST(SLS.SLS_GET_PND_DOC_ITM_DTL(?,?,?,?,?,?,?) AS SLS$PND$DOC$ITM$TAB$TYPE))";
        /*    String SoQuery =
            "SELECT A.DOC_ID,\n" + "  SUBSTR(A.SO_ID,3) SO_ID,\n" + "  A.DOC_DT,\n" + "  A.EO_ID,\n" +
            "  (SELECT EO_NM\n" + "  FROM APP.APP$EO B\n" + "  WHERE A.CLD_ID  = B.EO_CLD_ID\n" +
            "  AND A.SLOC_ID   = B.EO_SLOC_ID\n" + "  AND A.HO_ORG_ID = B.EO_HO_ORG_ID\n" +
            "  AND A.EO_ID     = B.EO_ID\n" + "  AND ROWNUM      = 1\n" + "  ) EO_NM,\n" + "  A.CURR_ID_SP,\n" +
            "  (SELECT CURR_DESC\n" + "  FROM APP.APP$CURR C\n" + "  WHERE C.CLD_ID = A.CLD_ID\n" +
            "  AND C.CURR_ID  = A.CURR_ID_SP\n" + "  ) CURR_DESC,\n" + "  A.ORDER_TYPE,\n" + "  (SELECT ATT_NM\n" +
            "  FROM APP.APP$DS$ATT D\n" + "  WHERE ATT_TYPE_ID = 68\n" + "  AND D.ATT_ID      = A.ORDER_TYPE\n" +
            "  ) ORDR_TYP_DESC,\n" + "  A.TOT_AMT_SP\n" + "FROM SLS$SO A\n" + "WHERE A.CLD_ID  = ?\n" +
            "AND A.SLOC_ID   = ?\n" + "AND A.ORG_ID    = ?\n" + "AND A.HO_ORG_ID = ?\n" + "AND A.DOC_ID    = ?";

        String soItmQuery =
            "SELECT A.SR_NO,\n" + "  A.ITM_ID,\n" + "  (SELECT B.ITM_DESC\n" + "  FROM APP.APP$ITM$PRF B\n" +
            "  WHERE A.ITM_ID  = B.ITM_ID\n" + "  AND A.CLD_ID    = B.CLD_ID\n" + "  AND A.SLOC_ID   = B.SLOC_ID\n" +
            "  AND A.HO_ORG_ID = B.HO_ORG_ID \n" + "  AND A.ITM_ID  = B.ITM_ID\n" + "  ) ITM_DESC,\n" +
            "  A.ITM_QTY,\n" + "  A.ITM_RATE,\n" + "  A.ITM_DISC_TYP,\n" + "  NVL(A.ITM_DISC_VAL,0) DISC_VAL,\n" +
            "  A.ITM_AMT_SP\n" + "FROM SLS$SO$ITM A\n" + "WHERE A.CLD_ID  = ?\n" + "AND A.SLOC_ID   = ?\n" +
            "AND A.ORG_ID    = ?\n" + "AND A.HO_ORG_ID = ?\n" + "AND A.DOC_ID    = ?\n" +
            "AND A.ITM_ID =NVL(?,A.ITM_ID)\n" + "ORDER BY A.SR_NO ASC"; */

        Map v = new LinkedHashMap();
        PreparedStatement ps = con.prepareStatement(headerQuery);
        ps.setObject(1, CldId);
        ps.setObject(2, SlocId);
        ps.setObject(3, OrgId);
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
            param.put("amount", "0");

            soDetail.add(getMapData("Document No", rs.getString("DOC_NO")));
            soDetail.add(getMapData("Date", rs.getString("DOC_DT")));
            soDetail.add(getMapData("Currency", rs.getString("CURR_VAL")));
            soDetail.add(getMapData("Amount", rs.getString("AMOUNT")));
        }
        rs.close();
        ps.close();

        PreparedStatement ps1 = con.prepareStatement(dtlQuery);
        ps1.setObject(1, CldId);
        ps1.setObject(2, SlocId);
        ps1.setObject(3, OrgId);
        ps1.setObject(4, HoOrgId);
        ps1.setObject(5, docId);
        ps1.setObject(6, glblDocId);
        ps1.setObject(7, "M");
        ResultSet rs1 = ps1.executeQuery();
        int count = 0;
        while (rs1.next()) {
            Map item = new LinkedHashMap();
            List top = new ArrayList();
            //   List mid = new ArrayList();
            //   List bottom = new ArrayList();

            item.put("SrNo", ++count);
            item.put("itmName", rs1.getString("ITM_NAME"));
            item.put("itmAmtSp", rs1.getString("AMOUNT"));

            //item.add(getMapDataForItmDtl("itmAmtSp", rs1.getString("AMOUNT"), "String", true, false));
            //  top.add(getMapDataForItmDtl("SrNo", "" + ++count, "String", true, false));
            //  top.add(getMapDataForItmDtl("Item Description", rs1.getString("ITM_NAME"), "String", true, false));

            top.add(getMapDataForItmDtl("Rate", rs1.getString("PRICE"), "String", true, false));
            top.add(getMapDataForItmDtl("Qty", rs1.getString("QTY"), "String", true, false));
            //  mid.add(getMapDataForItmDtl("Discount", rs1.getString("DISCOUNT"), "String", true, false));


            item.put("detail", top);
            //  item.put("mid", mid);
            //  item.put("bottom", bottom);
            Map m1 = new HashMap();
            m1.put("item", item);
            soItmDtl.add(m1);
        }
        rs1.close();
        ps1.close();

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

    public Map updateSalesOrderData(Integer SlocId, String CldId, String OrgId, String HoOrgId, String docId,
                                    String itmId, String ItmQty, String DiscVal) throws SQLException {
        Boolean itmUpdSucessFul = false;
        Double itmQty = new Double(0);
        Double dicVal = new Double(0);
        //   Number itmQty = StaticValue.NUMBER_ZERO;
        //    Number dicVal = StaticValue.NUMBER_ZERO;

        itmQty = new Double(ItmQty);
        dicVal = new Double(DiscVal);

        System.out.println("Inner update");
        Map v = new HashMap();
        if (!("".equals(itmId) || itmQty.compareTo(new Double(0)) <= 0 || itmQty.compareTo(new Double(0)) <= 0)) {
            Object callFunction =
                db.callStoredFunction(Types.INTEGER, "SLS.FN_UPDT_SO_ITM_DTLS_WS", con, SlocId, CldId, OrgId, HoOrgId,
                                      docId, itmId, ItmQty, DiscVal);
            Integer f = (callFunction == null ? -1 : (Integer) callFunction);
            System.out.println("Value of F : " + f);
            if (f == -1) {
                itmUpdSucessFul = false;
            } else {
                itmUpdSucessFul = true;
            }
        }
        if (itmUpdSucessFul) {
            Object callFunction =
                db.callStoredFunction(Types.INTEGER, "SLS.FN_UPDT_SO_DTLS_WS", con, SlocId, CldId, OrgId, HoOrgId,
                                      docId);
            Integer f = (callFunction == null ? -1 : (Integer) callFunction);
            System.out.println("Value of Second F : " + f);
            if (f == -1) {
                itmUpdSucessFul = false;
            } else {
                itmUpdSucessFul = true;
            }
        }

        if (itmUpdSucessFul) {
            con.commit();
            //  v = getSalesOrderDetail(SlocId, CldId, OrgId, HoOrgId, docId, null); 5-oct - 2015
        } else {
            con.rollback();
            v = new HashMap();
            v.put("status", "N");
            v.put("msg", "Data Not Update Successfully. Please Try Again");
        }
        return v;
    }

    public Map getAlertCountWS(String cldId, int slocId, String horgId, String orgId, int usrId) throws SQLException {
        String alrtQuery =
            "SELECT a.alrt_txt,\n" + "  a.alrt_dt\n" + "FROM APP.APP$MOB$ALRT a\n" + "WHERE cld_id             = ?\n" +
            "AND sloc_id              = ?\n" + "AND ho_org_id            = ?\n" + "AND org_id               = ?\n" +
            "AND usr_id               = ?\n" + "AND glbl_appli_struct_id = ?\n";
        Map map = new HashMap();
        PreparedStatement ps = con.prepareStatement(alrtQuery);
        ps.setObject(1, cldId);
        ps.setObject(2, slocId);
        ps.setObject(3, horgId);
        ps.setObject(4, orgId);
        ps.setObject(5, usrId);
        ps.setObject(6, "00306");
        ResultSet rs = ps.executeQuery();
        int count = 0;
        while (rs.next()) {
            count++;
        }
        rs.close();
        ps.close();
        String countQuery =
            "SELECT SUM(approval_Count) to_sum\n" + "FROM\n" + "  (SELECT glbl_doc_id,\n" + "    glbl_doc_nm,\n" +
            "    SLS.SLS_DOC_WF_CNT(?,?,?,?,glbl_doc_id,'M') approval_Count\n" + "  FROM app.app$glbl$doc\n" +
            "  WHERE GLBL_DOC_APPL_STRUCT=0030601\n" + "  AND glbl_doc_wrkflw       ='Y'\n" +
            "  ORDER BY glbl_doc_id\n" + "  )\n" + "WHERE approval_Count>0";
        PreparedStatement ps1 = con.prepareStatement(countQuery);
        ps1.setObject(1, cldId);
        ps1.setObject(2, slocId);
        ps1.setObject(3, orgId);
        ps1.setObject(4, usrId);
        ResultSet rs1 = ps1.executeQuery();
        String salesOrdrCount = "0";
        if (rs1.next()) {
            salesOrdrCount = rs1.getString(1);
        }
        ps1.close();
        rs1.close();
        //   Integer salesOrdrCount = getPendingDocumentCount(21503, "M", slocId, cldId, orgId, horgId, usrId); //Order
        if (salesOrdrCount == null) {
            map.put("pendingDocCount", salesOrdrCount);
            map.put("alertCount", count);
            map.put("status", "N");
            map.put("msg", "No Data Found.");
        } else {
            map.put("status", "Y");
            map.put("pendingDocCount", salesOrdrCount);
            map.put("alertCount", count);
        }
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
