package appwfservice.uti;

import com.ess.conn.DBConnection;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import java.io.File;

import java.math.BigDecimal;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javapns.Push;

import javapns.notification.PushedNotifications;

public class WorkFlowUtility {
    private Connection con;
    private DBConnection db;
    Map manualData;
    private static final String GOOGLE_SERVER_KEY = "AIzaSyDoEJLL-4QpLdSYYEMOYBBxCwir4TSqo30";
    private static final String MESSAGE_KEY = "message";

    public WorkFlowUtility() {
        super();
    }

    public WorkFlowUtility(DBConnection db, Connection con) {
        this.db = db;
        this.con = con;
    }

    @SuppressWarnings("unchecked")
    public Map getWorkFlowInfo(String cldId, int slocId, String orgId, String horgId, int usrId, int docId,
                               int docTypId, String docTxnId, String amount, String wfId) throws SQLException {
        File file = new File(".\\Certificates.p12");
        System.out.println(file.getName() + ": File Object =======================================");
        Map map = new HashMap();
        List forwardUsrList = new ArrayList();
        Map revert;
        Map forward;

        String usrLevelQur =
            "SELECT usr_id\n" + "FROM APP$WF$USR$LVL$MAP a\n" + "WHERE CLD_ID =?\n" + "AND SLOC_ID  = ?\n" +
            "AND ORG_ID   = ?\n" + "AND DOC_ID   =?\n" + "AND wf_lvl  IN\n" + "  (SELECT MAX(WF_LVL)\n" +
            "  FROM APP$WF$USR$LVL$MAP b\n" + "  WHERE DOC_ID =a.DOC_ID\n" + "  AND CLD_ID   = a.CLD_ID\n" +
            "  AND SLOC_ID  =a.SLOC_ID\n" + "  AND ORG_ID   =a.ORG_ID\n" + "  )";
        PreparedStatement ps = con.prepareStatement(usrLevelQur);
        ps.setObject(1, cldId);
        ps.setObject(2, slocId);
        ps.setObject(3, orgId);
        ps.setObject(4, docId);
        ResultSet rs = ps.executeQuery();
        Map<Integer, Integer> userList = new HashMap<Integer, Integer>();
        while (rs.next()) {
            userList.put(Integer.parseInt(rs.getObject("Usr_Id".toUpperCase()).toString()),
                         Integer.parseInt(rs.getObject("Usr_Id".toUpperCase()).toString()));
        }
        rs.close();
        ps.close();
        /*     Row[] allRowsInRange = docLvl1.getAllRowsInRange();

        if (allRowsInRange.length > 0) {
            for (Row row : allRowsInRange) {
                userList.put(Integer.parseInt(row.getAttribute("UsrId").toString()),
                             Integer.parseInt(row.getAttribute("UsrId").toString()));
            }
        } */
        if (userList.containsKey(usrId)) {
            //T
            int currWfuserLvl = this.getUsrLvl(slocId, cldId, orgId, docId, usrId, wfId, docTypId);
            int docCreatorId = this.getDocCreatorId(slocId, cldId, orgId, docId, docTxnId, docTypId);
            if (usrId != docCreatorId) {
                List revertUsrList = this.getRevertUsrList(cldId, slocId, orgId, docId, docTypId, docTxnId);
                map.put("rvtUsr", revertUsrList);
                map.put("Revert", "Y");
                map.put("Reject", "Y");
            } else {
                map.put("Revert", "N");
                map.put("Reject", "N");
            }
            map.put("Approve", "Y");
            map.put("Forward", "N");
            map.put("UsrLvl", currWfuserLvl);
            map.put("usr", "Top");
        } else {
            //ML
            int docCreator = this.getDocCreatorId(slocId, cldId, orgId, docId, docTxnId, docTypId);
            int currWfuserLvl = this.getUsrLvl(slocId, cldId, orgId, docId, usrId, wfId, docTypId);
            System.out.println(currWfuserLvl + ": Current user Level for this Document.");
            System.out.println(docCreator + ": Doc Creator user ID");
            String usrLvlQuery =
                "SELECT a.USR_ID,\n" + "  a.WF_ID,\n" + "  a.WF_LVL,\n" + "  b.usr_name,\n" + "  a.doc_id ,\n" +
                "  a.org_id ,\n" + "  A.CLD_ID,\n" + "  a.doc_type_id\n" + "FROM APP$WF$USR$LVL$MAP a ,\n" +
                "  app$sec$usr b\n" + "WHERE a.usr_id   = b.usr_id\n" + "AND a.CLD_ID     =?\n" +
                "AND a.org_id     =?\n" + "AND a.doc_id     =?\n" + "AND a.doc_type_id=?\n" + "AND a.WF_ID      =?\n" +
                "AND a.WF_LVL     >?";
            PreparedStatement ps2 = con.prepareStatement(usrLvlQuery);
            ps2.setObject(1, cldId);
            ps2.setObject(2, orgId);
            ps2.setObject(3, docId);
            ps2.setObject(4, docTypId);
            ps2.setObject(5, wfId);
            ps2.setObject(6, currWfuserLvl);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                forward = new HashMap();
                forward.put("usrId", rs2.getObject("Usr_Id".toUpperCase()));
                forward.put("wfLvl", rs2.getObject("Wf_Lvl".toUpperCase()));
                forward.put("name", rs2.getObject("Usr_Name".toUpperCase()).toString());
                forwardUsrList.add(forward);
            }
            rs2.close();
            ps2.close();
            if (usrId == docCreator) { //L
                map.put("Revert", "N");
                map.put("Reject", "N");
                map.put("Approve", "Y");
                map.put("Forward", "Y");
                map.put("UsrLvl", currWfuserLvl);
                map.put("fwdUsr", forwardUsrList);
                map.put("usr", "Lower");
            } else {
                map.put("Revert", "Y");
                map.put("Reject", "Y");
                map.put("Approve", "Y");
                map.put("Forward", "Y");
                map.put("UsrLvl", currWfuserLvl);
                map.put("fwdUsr", forwardUsrList);
                map.put("usr", "Middle");
                List revertUsrList = this.getRevertUsrList(cldId, slocId, orgId, docId, docTypId, docTxnId);
                map.put("rvtUsr", revertUsrList);
            }
        }
        if (map.isEmpty()) {
            map.put("status", "N");
            map.put("msg", "No Data Found.");
            return map;
        } else {
            map.put("status", "Y");
            return map;
        }
    }

    public int getUsrLvl(int slocId, String cldId, String orgId, int docId, int usrId, String wfId,
                         int docTypId) throws SQLException {
        Object obj =
            db.callStoredFunction(Types.INTEGER, "APP.WF_GET_USR_LEVEL", con, slocId, cldId, orgId, usrId, wfId, docId,
                                  docTypId);
        return Integer.parseInt(obj.toString());
    }

    public int getDocCreatorId(int slocId, String cldId, String orgId, int docId, String docTxnId,
                               int docTypId) throws SQLException {
        int docCreator =
            Integer.parseInt(db.callStoredFunction(Types.INTEGER, "APP.WF_GET_INT_USR", con, slocId, cldId, orgId,
                                                   docId, docTxnId, docTypId).toString());
        return docCreator;
    }

    @SuppressWarnings("unchecked")
    public List getRevertUsrList(String cldId, int slocId, String orgId, int docId, int docTypId,
                                 String docTxnId) throws SQLException {
        System.out.println("In getRevertUsrList Function");
        System.out.println(cldId + " " + slocId + " " + orgId + " " + docId + " " + docTypId + " " + docTxnId);
        Map revert;
        List revertUsrList = new ArrayList();
        String UsrRevQuery =
            "SELECT CLD_ID,\n" + "  A.SLOC_ID,\n" + "  ORG_ID,\n" + "  DOC_ID,\n" + "  TXN_DOC_ID,\n" +
            "  USR_ID_BY,\n" + "  WF_LVL_FM,\n" + "  USR_NAME\n" +
            "FROM TABLE (CAST (WF_GET_REVERT_USR_LIST (?,?,?,?, ?, ?) AS APP$WF$REVERT$USR$TAB)) A,\n" +
            "  APP$SEC$USR B\n" + "WHERE USR_ID_BY=USR_ID";

        PreparedStatement ps = con.prepareStatement(UsrRevQuery);
        ps.setObject(1, slocId);
        ps.setObject(2, cldId);
        ps.setObject(3, orgId);
        ps.setObject(4, docId);
        ps.setObject(5, docTypId);
        ps.setObject(6, docTxnId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            revert = new HashMap();
            revert.put("usrId", rs.getObject("Usr_Id_By".toUpperCase()).toString());
            revert.put("name", rs.getObject("Usr_Name".toUpperCase()).toString());
            revert.put("wfLvl", rs.getObject("Wf_Lvl_Fm".toUpperCase()).toString());
            revertUsrList.add(revert);
        }
        return revertUsrList;
    }


    public String docAction(String cldId, String orgId, int slocId, String horgId, String wfId, int docId, int docTypId,
                            String docTxnId, String amount, int usrId, String remark, int usrLvl, int usrIdTo,
                            int usrToLvl, String usrAction, String usr, String module, String doc_no, String doc_dt) {
        String result = null;
        if (usr.equals("Top")) {
            try {
                result =
                    this.okButton(cldId, orgId, slocId, horgId, wfId, docId, docTypId, docTxnId, amount, usrId, remark,
                                  usrLvl, usrIdTo, usrToLvl, usrAction, doc_no, doc_dt);
                if (module.equals("Finance")) {
                    //am.validateTempVouForGl(am, docTxnId, orgId, slocId, horgId, cldId, 1);
                    if (usrAction.equals("Approve")) {
                        this.saveAsGL(docTxnId, usrId, horgId, cldId, orgId, slocId, 1, docTypId);
                    }
                    /*  result =
                        this.okButton(cldId, orgId, slocId, horgId, wfId, docId, docTypId, docTxnId, amount, usrId,
                                      remark, usrLvl, usrIdTo, usrToLvl, usrAction); */
                    System.out.println(result + "================================Result");
                } else {
                    /*  result =
                        this.okButton(cldId, orgId, slocId, horgId, wfId, docId, docTypId, docTxnId, amount, usrId,
                                      remark, usrLvl, usrIdTo, usrToLvl, usrAction); */
                    System.out.println("result of Workflow entry : " + result);
                    if (module.equals("Sales") && (result.equals("F") || result.equals("A"))) {
                        System.out.println("Inner Top Sales");
                        Integer i = -1;
                        Object o =
                            db.callStoredFunction(Types.INTEGER, "SLS.FN_UPDT_SO_DTLS_AFTR_WF_WS", con, slocId, cldId,
                                                  orgId, horgId, docTxnId, result, usrId);
                        i = (o == null ? -1 : (Integer) o);
                        System.out.println("Function Return=================================================" + i);
                        if (i.equals(1)) {
                            con.commit();
                        } else {
                            con.rollback();
                        }
                    } else {
                        if (result.equals("A")) {
                            System.out.println("Inner Top MM");
                            Integer i = -1;
                            System.out.println(cldId + " " + slocId + " " + orgId + " " + docTxnId);
                            Object o =
                                db.callStoredFunction(Types.INTEGER, "MM.MM_UPDT_PO_STAT_ON_APRV", con, cldId, slocId,
                                                      orgId, docTxnId);
                            i = (o == null ? -1 : (Integer) o);
                            System.out.println("Function Return=================================================" + i);
                            if (i.equals(1)) {
                                con.commit();
                            } else {
                                con.rollback();
                            }
                        }
                    }
                    System.out.println(result + "================================Result");
                }
            } catch (SQLException e) {
                System.out.println(e);
                try {
                    con.rollback();
                } catch (SQLException f) {
                    f.printStackTrace();
                }
            }
        } else {
            try {
                if (module.equals("Finance")) {
                    //am.validateTempVouForGl(am, docTxnId, orgId, slocId, horgId, cldId, 1);
                    if (usrAction.equals("Approve")) {
                        this.saveAsGL(docTxnId, usrId, horgId, cldId, orgId, slocId, 1, docTypId);
                    }
                    result =
                        this.okButtonDwn(cldId, orgId, slocId, horgId, wfId, docId, docTypId, docTxnId, amount, usrId,
                                         remark, usrLvl, usrIdTo, usrToLvl, usrAction);
                    System.out.println(result + "================================Result");
                } else {
                    result =
                        this.okButtonDwn(cldId, orgId, slocId, horgId, wfId, docId, docTypId, docTxnId, amount, usrId,
                                         remark, usrLvl, usrIdTo, usrToLvl, usrAction);
                    if (module.equals("Sales") && (result.equals("F") || result.equals("A"))) {
                        System.out.println("Inner Lower Level");
                        Integer i = -1;
                        Object o =
                            db.callStoredFunction(Types.INTEGER, "SLS.FN_UPDT_SO_DTLS_AFTR_WF_WS", con, slocId, cldId,
                                                  orgId, horgId, docTxnId, result, usrId);
                        i = (o == null ? -1 : (Integer) o);
                        if (i.equals(1)) {
                            con.commit();
                        } else {
                            con.rollback();
                        }
                    } else {
                        if (result.equals("A")) {
                            System.out.println("Inner Top MM");
                            Integer i = -1;
                            Object o =
                                db.callStoredFunction(Types.INTEGER, "MM.MM_UPDT_PO_STAT_ON_APRV", con, cldId, slocId,
                                                      orgId, docTxnId);
                            i = (o == null ? -1 : (Integer) o);
                            System.out.println("Function Return=================================================" + i);
                            if (i.equals(1)) {
                                con.commit();
                            } else {
                                con.rollback();
                            }
                        }
                    }
                    System.out.println(result + "================================Result");
                    //Work on Doc status here.
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void saveAsGL(String id, int usrId, String horgId, String cldId, String orId, int sloId, int insId,
                         int vouTy) throws SQLException {
        String vouId = id; //doc_txn
        Integer userId = usrId;
        String hoOrg_id = horgId;
        String cld_id = cldId;
        String orgId = orId;
        Integer slocId = sloId;
        Integer instId = insId; //1
        Integer vouTyp = vouTy; //voc tye
        db.callStoredProcedure("FIN.PROC_TRANSLATE_TO_GL", con, cld_id, slocId, hoOrg_id, orgId, instId, vouId, userId);
        // String Vocid = (String)callStoredFunction1(VARCHAR,"FIN.FN_GL_DISP_NO(?,?,?,?,?)", new Object[] { slocId, cld_id, orgId,vouId },am);
        //System.out.println("disp_glId --------------------------------------------------------------" + id);
    }

    @SuppressWarnings("unchecked")
    public String okButton(String cld_id, String org_id, int sloc_id, String horg_id, String wf_id, int doc_id,
                           int doc_type_id, String doc_txn_id, String amountt, int usr_id, String remark, int level,
                           int usrIdTo, int lvlTo, String usrAction, String doc_no, String doc_dt) throws SQLException {
        String result = null;
        String action = "A";
        /* oracle.jbo.domain.Number amount = null;
        amount = new oracle.jbo.domain.Number(amountt); */
        Double amount = new Double(amountt);
        Map manualData = null;
        manualData = new HashMap();
        manualData.put("sloc_id", sloc_id);
        manualData.put("cld_id", cld_id);
        manualData.put("org_id", org_id);
        manualData.put("doc_id", doc_id);
        manualData.put("doc_type_id", doc_type_id);
        manualData.put("wf_id", wf_id);
        manualData.put("doc_txn_id", doc_txn_id);
        manualData.put("usr_id", usr_id);
        manualData.put("doc_no", doc_no);
        manualData.put("doc_dt", doc_dt);

        Integer usr_id_to = usr_id;
        Integer lvl_to = level;
        if (usrAction.equalsIgnoreCase("Approve")) {
            action = "A";
            //approved case
            result = saveOkButtonAction(usr_id_to, level, lvl_to, action, remark, amount, manualData);
        } else if (usrAction.equalsIgnoreCase("Reject")) {
            action = "R";
            usr_id_to = getDocCreatorId(sloc_id, cld_id, org_id, doc_id, doc_txn_id, doc_type_id);
            lvl_to = getUsrLvl(sloc_id, cld_id, org_id, doc_id, usr_id_to, wf_id, doc_type_id);
        } else if (usrAction.equalsIgnoreCase("Revert")) {
            action = "V";
            usr_id_to = usrIdTo;
            lvl_to = lvlTo;
        }
        //Rejected and Revert
        if (action.equals("R") || action.equals("V")) {
            //String docNm = (String)callStoredFunction(VARCHAR,"App_Get_Glbl_Doc_Nm(?)" , new Object[]{ doc_id});
            result = saveOkButtonAction(usr_id_to, level, lvl_to, action, remark, amount, manualData);
        }
        return result;
    }

    public String saveOkButtonAction(int usr_id_to, int level, int lvl_to, String action, String remark, Double amount,
                                     Map manualData) throws SQLException {
        System.out.println("in case of : " + action);
        String post = "P";
        String res = null;
        // replace to a defined function 16 oct 2015 Shubham Bansal
        /*   String res =
            (db.callStoredFunction(Types.VARCHAR, "APP.WF_INS_TXN", con, manualData.get("sloc_id"),
                                   manualData.get("cld_id"), manualData.get("org_id"), manualData.get("doc_id"),
                                   manualData.get("doc_type_id"), manualData.get("wf_id"), manualData.get("doc_txn_id"),
                                   manualData.get("usr_id"), usr_id_to, level, lvl_to, action, remark, amount,
                                   post).toString()); */

        //int usr_id_to, int level, int lvl_to, String action, String remark, Double amount,
        //                        Map manualData, String post
        res = callWfTxnFctn(usr_id_to, level, lvl_to, action, remark, amount, manualData, post);
        System.out.println("RES : " + res);
        if (res.equals("1")) {
            if (Integer.parseInt(manualData.get("usr_id").toString()) != usr_id_to) {
                String docNm =
                    (String) (db.callStoredFunction(Types.VARCHAR, "App_Get_Glbl_Doc_Nm", con,
                                                    Integer.parseInt(manualData.get("doc_id").toString())).toString());
                String msg = "";
                if (action.equals("A")) {
                    msg = docNm + " has been approved.";
                } else if (action.equals("F")) {
                    msg = docNm + " has been forward.";
                } else if (action.equals("R")) {
                    msg = docNm + " has been reject.";
                } else {
                    msg = docNm + " has been revert.";
                }
                System.out.println("Alert called.. 1");
                Object obj =
                    (db.callStoredFunction(Types.VARCHAR, "FN_WS_INS_ALRT", con, manualData.get("cld_id"),
                                           manualData.get("sloc_id"), manualData.get("ho_org_id"),
                                           manualData.get("org_id"), usr_id_to, msg, manualData.get("doc_id"),
                                           manualData.get("doc_type_id"), manualData.get("doc_txn_id")).toString());
                int resulte = Integer.parseInt(obj.toString());
                try {
                    sendNotification(usr_id_to, docNm);
                } catch (Exception e) {
                    //      e.printStackTrace();
                }
            }
            return action;
        } else {
            return "N";
        }
    }

    @SuppressWarnings("unchecked")
    public String okButtonDwn(String cld_id, String org_id, int sloc_id, String horg_id, String wf_id, int doc_id,
                              int doc_type_id, String doc_txn_id, String amountt, int usr_id, String remark, int level,
                              int usrIdTo, int lvlTo, String usrAction) throws SQLException {
        String result = null;
        String action = "F";
        Double amount = new Double(amountt);
        manualData = new HashMap();
        manualData.put("sloc_id", sloc_id);
        manualData.put("cld_id", cld_id);
        manualData.put("org_id", org_id);
        manualData.put("doc_id", doc_id);
        manualData.put("doc_type_id", doc_type_id);
        manualData.put("wf_id", wf_id);
        manualData.put("doc_txn_id", doc_txn_id);
        manualData.put("usr_id", usr_id);
        manualData.put("ho_org_id", horg_id);
        String post = "P";
        if (usrAction.equalsIgnoreCase("Revert")) {
            action = "V";
        } else if (usrAction.equalsIgnoreCase("Reject")) {
            action = "R";
        } else if (usrAction.equalsIgnoreCase("Approve")) {
            action = "A";
        } else {
            //Forward
            action = "F";
        }
        Integer usr_id_to = usrIdTo;
        Integer level_to = 1;
        if (!action.equalsIgnoreCase("A") && !action.equalsIgnoreCase("V")) {
            level_to = lvlTo;
        } else if (action.equalsIgnoreCase("V")) {
            usr_id_to = usrIdTo;
            level_to = lvlTo;
        }
        //=====================================================================approved case================================================================================
        if (action.equals("A")) {
            result = okButtonDwnAction(usr_id_to, level, level_to, action, remark, amount, post);
        }
        if (action.equals("F") || action.equals("R") || action.equals("V")) {
            Integer usrPend = null;
            Object obj = null;
            if (action.equals("F") && action.equals("V")) {
                obj = usrIdTo;
            } else {
                obj =
                    db.callStoredFunction(Types.NUMERIC, "APP.WF_GET_INT_USR", con, sloc_id, cld_id, org_id, doc_id,
                                          doc_txn_id, doc_type_id);
            }
            //String docNm = (String)callStoredFunction(VARCHAR,"App_Get_Glbl_Doc_Nm(?)" , new Object[]{ doc_id});
            if (obj != null) {
                BigDecimal b = (BigDecimal) obj;
                double doubleValue = b.doubleValue();
                usrPend = (int) (doubleValue);
            }
            result = okButtonDwnAction(usr_id_to, level, level_to, action, remark, amount, post);
        }
        return result;
    }

    public String okButtonDwnAction(int usr_id_to, int level, int level_to, String action, String remark, Double amount,
                                    String post) throws SQLException {
        String res = "";
        if (action.equalsIgnoreCase("A")) {
            /*   res =
                (db.callStoredFunction(Types.VARCHAR, "APP.WF_INS_TXN", con, manualData.get("sloc_id"),
                                       manualData.get("cld_id"), manualData.get("org_id"), manualData.get("doc_id"),
                                       manualData.get("doc_type_id"), manualData.get("wf_id"),
                                       manualData.get("doc_txn_id"), manualData.get("usr_id"), manualData.get("usr_id"),
                                       level, level, action, remark, amount, post).toString()); */

            // chaned to a new defined function 16 oct 2015 Shubham Bansal
            res = callWfTxnFctn(usr_id_to, level, level, action, remark, amount, manualData, post);
            System.out.println("Approved !");
        } else {
            /*  res =
                (db.callStoredFunction(Types.VARCHAR, "APP.WF_INS_TXN", con, manualData.get("sloc_id"),
                                       manualData.get("cld_id"), manualData.get("org_id"), manualData.get("doc_id"),
                                       manualData.get("doc_type_id"), manualData.get("wf_id"),
                                       manualData.get("doc_txn_id"), manualData.get("usr_id"), usr_id_to, level,
                                       level_to, action, remark, amount, post).toString()); */

            // chaned to a new defined function 16 oct 2015 Shubham Bansal
            res = callWfTxnFctn(usr_id_to, level, level_to, action, remark, amount, manualData, post);

            System.out.println("Not in approved block");
            if (res.equals("1")) {
                String docNm =
                    (String) (db.callStoredFunction(Types.VARCHAR, "App_Get_Glbl_Doc_Nm", con,
                                                    Integer.parseInt(manualData.get("doc_id").toString())).toString());
                String msg = "";
                if (action.equals("A")) {
                    msg = docNm + " has been approved.";
                } else if (action.equals("F")) {
                    msg = docNm + " has been forward.";
                } else if (action.equals("R")) {
                    msg = docNm + " has been reject.";
                } else {
                    msg = docNm + " has been revert.";
                }
                System.out.println("Alert called.. 2");

                Object obj =
                    (db.callStoredFunction(Types.VARCHAR, "FN_WS_INS_ALRT", con, manualData.get("cld_id"),
                                           manualData.get("sloc_id"), manualData.get("ho_org_id"),
                                           manualData.get("org_id"), usr_id_to, msg, manualData.get("doc_id"),
                                           manualData.get("doc_type_id"), manualData.get("doc_txn_id")).toString());
                int resulte = Integer.parseInt(obj.toString());
                try {
                    sendNotification(usr_id_to, docNm);
                } catch (Exception e) {
                    //   e.printStackTrace();
                }
                //return action;
            }
        }
        System.out.println(res + "----------------------");
        if (res.equals("1")) {
            return action;
        } else {
            return "N";
        }
    }

    public void sendNotification(int usrId, String docNm) throws SQLException {
        System.out.println("usr id: " + usrId + " --- >? " + docNm);
        String query =
            "select DVC_ID,    \n" + "OS_ID, \n" + "LOGIN_STAT \n" + "from     \n" + "APP.APP$WS$USR$DVC    \n" +
            "where     \n" + "USR_ID = :BindUsrId";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setObject(1, usrId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            if ((rs.getObject("Os_Id".toUpperCase()).toString()).equals("Android") &&
                (rs.getObject("Login_Stat".toUpperCase()).toString()).equals("Y")) {
                Result result = null;
                try {
                    Sender sender = new Sender(GOOGLE_SERVER_KEY);
                    Message message =
                        new Message.Builder().timeToLive(30).delayWhileIdle(true).addData(MESSAGE_KEY,
                                                                                          "New " + docNm +
                                                                                          " created").build();
                    //result = sender.send(message, rs.getObject("Dvc_Id".toUpperCase()).toString(), 1);
                    result =
                        sender.send(message, "376eb005ff54951c76d9e9be29460fbc5cbf02fa3bc897ffa257480c4866614d", 1);
                    System.out.println("result : -" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if ((rs.getObject("Os_Id".toUpperCase()).toString()).equals("iOS") &&
                       (rs.getObject("Login_Stat".toUpperCase()).toString()).equals("Y")) {
                try {
                    //        File file = new File(".\\CertificatesProdAPNSKey.p12");
                    File file = new File("CertificatesProdAPNSKey.p12");
                    System.out.println(file.getName() + ": File Object");
                    PushedNotifications alert =
                        Push.alert("New " + docNm + " created", file, "", true,
                                   rs.getObject("Dvc_Id".toUpperCase()).toString());
                    System.out.println(alert.getFailedNotifications() + " : FMsg");
                    System.out.println(alert.getSuccessfulNotifications() + " : SMsg");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        rs.close();
        ps.close();
    }

    public String callWfTxnFctn(int usr_id_to, int level, int lvl_to, String action, String remark, Double amount,
                                Map manualData, String post) throws SQLException {
        String res = null;
        res =
            (db.callStoredFunction(Types.VARCHAR, "APP.WF_INS_TXN", con, manualData.get("sloc_id"),
                                   manualData.get("cld_id"), manualData.get("org_id"), manualData.get("doc_id"),
                                   manualData.get("doc_type_id"), manualData.get("wf_id"), manualData.get("doc_txn_id"),
                                   manualData.get("usr_id"), usr_id_to, level, lvl_to, action, remark, amount,
                                   post).toString());

        // Function to updated in the alert table only
        // Document no ----  dated date..  is approved/rejected/forwarded/reverted
        String msg = "Document No. " + manualData.get("doc_no") + " dated " + manualData.get("doc_dt") + " is ";
        String type = "";
        if (res != null) {
            switch (action) {
            case "A":
                type = "Approved.";
                break;
            case "F":
                type = "Forwarded.";
                break;
            case "R":
                type = "Rejected.";
                break;
            case "V":
                type = "Reverted.";
                break;
            }
        }
        msg += type;
        db.callStoredFunction(Types.VARCHAR, "APP.FN_CHECK_ACTION_AND_INS_ALRT", con, manualData.get("sloc_id"),
                              manualData.get("cld_id"), manualData.get("org_id"), manualData.get("doc_id"),
                              manualData.get("doc_type_id"), manualData.get("doc_txn_id"), action,
                              manualData.get("usr_id"), manualData.get("usr_id"), usr_id_to, amount, msg);
        return res;
    }
}
