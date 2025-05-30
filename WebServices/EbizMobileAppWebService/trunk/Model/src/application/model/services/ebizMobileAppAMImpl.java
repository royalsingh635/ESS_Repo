package application.model.services;


import com.google.gson.Gson;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.client.Configuration;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;

import oracle.net.aso.n;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Oct 01 10:08:39 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------

public class ebizMobileAppAMImpl extends ApplicationModuleImpl {
    //private static ADFLogger adfLog = (ADFLogger)ADFLogger.createADFLogger(ebizMobileAppAMImpl.class) ;
    private static int VARCHAR = Types.VARCHAR;
    //private static int INTEGER = Types.NUMERIC;
    /**
     * This is the default constructor (do not remove).
     */
    public ebizMobileAppAMImpl() {
    }

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars,ebizMobileAppAMImpl am) {
        CallableStatement st = null;
        try {
            // 1. Create a JDBC CallabledStatement
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            // 2. Register the first bind variable for the return value
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                // 3. Loop over values for the bind variables passed in, if any
                for (int z = 0; z < bindVars.length; z++) {
                    // 4. Set the value of user-supplied bind vars in the stmt
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            // 5. Set the value of user-supplied bind vars in the stmt
            st.executeUpdate();
            // 6. Return the value of the first bind variable
            return st.getObject(1);
        } catch (SQLException e) {
            throw new JboException(e.getMessage());
        } finally {
            if (st != null) {
                try {
                    // 7. Close the statement
                    st.close();
                } catch (SQLException e) {
                    throw new JboException(e.getMessage());
                }
            }
        }
        
    }
    
    public void releaseAm(ebizMobileAppAMImpl am) {
        Configuration.releaseRootApplicationModule(am, true);
    }
    public ebizMobileAppAMImpl getAm() {
        String appModuleName = "application.model.services.ebizMobileAppAM";
        String appModuleConfig = "ebizMobileAppAMLocal";
        return (ebizMobileAppAMImpl)Configuration.createRootApplicationModule(appModuleName, appModuleConfig);
    }
    
    //flag 1: - Login , 0 :-Logout
    public String loginWS(String cldId, int slocId, String userNm, String userPwd,
                          String tknId , String osNm, String dvcNm ,int flag){
        ebizMobileAppAMImpl am = getAm();
        String pwd = (String)callStoredFunction(VARCHAR, "APP.PKG_SEC.ncrypt(?)", new Object[] {userPwd},am);
        System.out.println(cldId+" "+slocId+" "+userNm+" "+userPwd+" "+tknId+" "+pwd);
        String output =
            (String)callStoredFunction(VARCHAR, "APP.fn_ws_user_login(?,?,?,?,?,?,?,?)", new Object[] {cldId,slocId,userNm,pwd,
                                                                                    tknId,osNm,dvcNm,flag},am);
        if(!output.equals("N")){
        am.getDBTransaction().commit();
        }else{
            Map map = new HashMap();
            map.put("status","N");
            map.put("msg","No Data Found.");
            output =  new Gson().toJson(map).toString();
        }
        releaseAm(am);
        am=null;
        return output;
    }
    
    public String  approvalWS(String cldId,int slocId,String horgId ,String orgId,String userId,String type,String start,String end){
        ebizMobileAppAMImpl am = getAm();
        System.out.println("Inner Web Service");
        int usrId = Integer.parseInt(userId);
        int st = Integer.parseInt(start);
        int en = Integer.parseInt(end);
        Object output =
            callStoredFunction(VARCHAR, "FIN.fn_ws_user_vou_app(?,?,?,?,?,?,?,?)", new Object[] {cldId,horgId,slocId,orgId,usrId,type,st,en},am);
        releaseAm(am);
        am=null;
        System.out.println("Output is :  "+ output);
        if(output!=null){
        return output.toString();
        }else{
            Map map = new HashMap();
            map.put("status","N");
            map.put("msg","No Data Found.");
            map.put("docList",new ArrayList());
            return new Gson().toJson(map).toString();
        }
    }
    
    public String  detailWS(String cldId,String horgId,String slocId ,String orgId,String userId,String vouId){
        ebizMobileAppAMImpl am = getAm();
        int usrId = Integer.parseInt(userId);
        int scId = Integer.parseInt(slocId);
        Object output =
            callStoredFunction(VARCHAR, "FIN.fn_ws_vou_dtl(?,?,?,?,?,?)", new Object[] {cldId,horgId,scId,orgId,usrId,vouId},am);
        releaseAm(am);
        am=null;
        System.out.println("Output is :  "+ output);
        if(output!=null){
        return output.toString();
        }else{
            return "{\"status\":\"N\",\"vouDTL\":[],\"vouLines\":[],\"msg\":\"No Data Found.\"}";
        }
        //return output.toString();
    }

    @SuppressWarnings("unchecked")
    public List getDbAgeingList(String cldId,String orgId){
        ebizMobileAppAMImpl am = getAm();
        List list = new ArrayList();
        Map innerMap = null;
        ViewObjectImpl arList1 = am.getArListVO1();
        arList1.setNamedWhereClauseParam("BindCldId", cldId);
        arList1.setNamedWhereClauseParam("BindOrgId", orgId);
        arList1.executeQuery();
        System.out.println(arList1.getRowCount());
        RowSetIterator createRowSetIterator = arList1.createRowSetIterator("test");
        while(createRowSetIterator.hasNext()){
            innerMap = new HashMap();
            Row row = createRowSetIterator.next();
            innerMap.put("coaNm", row.getAttribute("CoaNm"));
            innerMap.put("currNm", row.getAttribute("CurrNm"));
            innerMap.put("amtSp", row.getAttribute("AmtSp"));
            innerMap.put("amtAdj", row.getAttribute("AmtAdj"));
            innerMap.put("amtOs", row.getAttribute("AmtOs"));
            innerMap.put("arAmtBs", row.getAttribute("ArAmtBs"));
            innerMap.put("os30Days", row.getAttribute("Os30Days"));
            innerMap.put("os60Days", row.getAttribute("Os60Days"));
            innerMap.put("os90Days", row.getAttribute("Os90Days"));
            innerMap.put("os120Days", row.getAttribute("Os120Days"));
            innerMap.put("os180Days", row.getAttribute("Os180Days"));
            innerMap.put("os1Yr", row.getAttribute("Os1Yr"));
            innerMap.put("osGreater1Yr", row.getAttribute("OsGreater1Yr"));
            list.add(innerMap);
        }
        createRowSetIterator.closeRowSetIterator();
        releaseAm(am);
        am=null;
        return list;
    }

    @SuppressWarnings("unchecked")
    public Map getDbAgeingInfo(String cldId,String orgId,String btkTyp,int usrId){
        Map map = new HashMap();
        Map innerMap = new HashMap();
        ebizMobileAppAMImpl am = getAm();
        ViewObjectImpl ageingBkt1 = am.getOrgUsrAgeingBkt1();
        ageingBkt1.setNamedWhereClauseParam("BindBktTyp", btkTyp);
        ageingBkt1.setNamedWhereClauseParam("BindUsrId", usrId);
        ageingBkt1.setNamedWhereClauseParam("BindCldId", cldId);
        ageingBkt1.setNamedWhereClauseParam("BindOrgId", orgId);
        ageingBkt1.executeQuery();
        RowSetIterator rsi = ageingBkt1.createRowSetIterator(null);
        while(rsi.hasNext()){
            Row next = rsi.next();
            innerMap.put("r1",next.getAttribute("BktRange1"));
            innerMap.put("r2",next.getAttribute("BktRange2"));
            innerMap.put("r3",next.getAttribute("BktRange3"));
            innerMap.put("r4",next.getAttribute("BktRange4"));
        }
        rsi.closeRowSetIterator();
        List list = am.getDbAgeingList(cldId, orgId);
        if(list.isEmpty()){
            map.put("btkDetail",innerMap);
            map.put("ageingList",list);
            map.put("status","N");
            map.put("msg","No Data Found For Selected Bucket");
        }else{
            map.put("btkDetail",innerMap);
            map.put("ageingList",list);
            map.put("status","Y");
        }
        releaseAm(am);
        am=null;
        return map;
    }

    @SuppressWarnings("unchecked")
    public Map getCashPositionInfo(String cldId,int slocId,String horgId,String orgId){
        Map map = new HashMap();
        Map innerMap = null;
        List list = new ArrayList();
        ebizMobileAppAMImpl am = getAm();
        ViewObjectImpl cashPosition1 = am.getCashPosition1();
        cashPosition1.setNamedWhereClauseParam("BindCldID", cldId);
        cashPosition1.setNamedWhereClauseParam("BindSlocID", slocId);
        cashPosition1.setNamedWhereClauseParam("BindHoOrgID", horgId);
        cashPosition1.setNamedWhereClauseParam("BindOrgID", orgId);
        cashPosition1.executeQuery();
        RowSetIterator rsi = cashPosition1.createRowSetIterator("test");
        while(rsi.hasNext()){
            Row next = rsi.next();
            innerMap = new HashMap();
            innerMap.put("coaNm",next.getAttribute("CoaNm"));
            innerMap.put("Balance",next.getAttribute("Balance"));
            innerMap.put("BalType",next.getAttribute("BalType"));
            list.add(innerMap);
        }
        rsi.closeRowSetIterator();
        if(list.isEmpty()){
            map.put("cashDetail",list);
            map.put("status","N");
            map.put("msg","No Data Found.");
        }else{
            map.put("status","Y");
            map.put("cashDetail",list);
        }
        releaseAm(am);
        am=null;
        return map;
    }
    
    public String setBtkRange(String cldId,String orgId,String btkTyp,int usrId,String r1,String r2,String r3,String r4) throws Exception{
        ebizMobileAppAMImpl am = getAm();
        String status = "N";
        ViewObjectImpl ageingBkt1 = am.getOrgUsrAgeingBkt1();
        ageingBkt1.setNamedWhereClauseParam("BindBktTyp", btkTyp);
        ageingBkt1.setNamedWhereClauseParam("BindUsrId", usrId);
        ageingBkt1.setNamedWhereClauseParam("BindCldId", cldId);
        ageingBkt1.setNamedWhereClauseParam("BindOrgId", orgId);
        ageingBkt1.executeQuery();
        RowSetIterator rsi = ageingBkt1.createRowSetIterator(null);
        while(rsi.hasNext()){
            Row next = rsi.next();
            next.setAttribute("BktRange1",new Number(r1));
            next.setAttribute("BktRange2",new Number(r2));
            next.setAttribute("BktRange3",new Number(r3));
            next.setAttribute("BktRange4",new Number(r4));
            status = "Y";
            am.getDBTransaction().commit();
        }
        releaseAm(am);
        am=null;
        return status;
    }
    public void getDbAegingDetail(){
        
    }

    /**
     * Container's getter for OrgUsrAgeingBkt1.
     * @return OrgUsrAgeingBkt1
     */
    public ViewObjectImpl getOrgUsrAgeingBkt1() {
        return (ViewObjectImpl) findViewObject("OrgUsrAgeingBkt1");
    }

    
    /**
     * Container's getter for CashPosition1.
     * @return CashPosition1
     */
    public ViewObjectImpl getCashPosition1() {
        return (ViewObjectImpl) findViewObject("CashPosition1");
    }

    /**
     * Container's getter for OrgInfo1.
     * @return OrgInfo1
     */
    public ViewObjectImpl getOrgInfo1() {
        return (ViewObjectImpl) findViewObject("OrgInfo1");
    }

    @SuppressWarnings("unchecked")
    public Map getOrgInfo(String orgId,String cldId){
        ebizMobileAppAMImpl am = getAm();
        Map map = new HashMap();
        ViewObjectImpl org = am.getOrgInfo1();
        org.setNamedWhereClauseParam("BindOrgId", orgId);
        org.setNamedWhereClauseParam("BindCldId", cldId);
        org.executeQuery();
        RowSetIterator rsi = org.createRowSetIterator(null);
        while(rsi.hasNext()){
            Row next = rsi.next();
            map.put("orgNm",next.getAttribute("OrgDesc"));
            map.put("orgParentNm",(next.getAttribute("PrntOrgDesc")==null?next.getAttribute("OrgDesc"):next.getAttribute("PrntOrgDesc")));
            map.put("orgFyStDt",DateFormat.getDateInstance().format(next.getAttribute("OrgFyStDt")));
            map.put("orgCurrDesc",next.getAttribute("CurrDesc"));
            map.put("orgCntryNm",next.getAttribute("CntryDesc"));
            map.put("orgAdd1",(next.getAttribute("Address1")==null?"":next.getAttribute("Address1")));
            map.put("orgAdd2",(next.getAttribute("Address2")==null?"":next.getAttribute("Address2")));
            map.put("orgPhone1",(next.getAttribute("OrgPhone1")==null?"":next.getAttribute("OrgPhone1")));
            map.put("orgPhone2",(next.getAttribute("OrgPhone2")==null?"":next.getAttribute("OrgPhone2")));
            map.put("orgMb1",(next.getAttribute("OrgMobile1")==null?"":next.getAttribute("OrgMobile1")));
            map.put("orgMb2",(next.getAttribute("OrgMobile2")==null?"":next.getAttribute("OrgMobile2")));
            map.put("orgFax1",(next.getAttribute("OrgFax1")==null?"":next.getAttribute("OrgFax1")));
            map.put("orgFax2",(next.getAttribute("OrgFax2")==null?"":next.getAttribute("OrgFax2")));
            map.put("orgWebSite1",(next.getAttribute("OrgWebsite1")==null?"":next.getAttribute("OrgWebsite1")));
            map.put("orgWebSite2",(next.getAttribute("OrgWebsite2")==null?"":next.getAttribute("OrgWebsite2")));
            map.put("orgEmail1",(next.getAttribute("OrgEmail1")==null?"":next.getAttribute("OrgEmail1")));
            map.put("orgEmail2",(next.getAttribute("OrgEmail2")==null?"":next.getAttribute("OrgEmail2")));
        }
        releaseAm(am);
        am=null;
        if(map.isEmpty()){
            map.put("status","N");
            map.put("msg","No Data Found.");
            return map;
        }else{
            return map;
        }  
    }

    @SuppressWarnings("unchecked")
    public Map getNotification(String cldId,int slocId,String horgId,String orgId,int usrId,String moduleId){
        ebizMobileAppAMImpl am = getAm();
        Map map = new HashMap();
        Map msg = null;
        List list = new ArrayList();
//        if(moduleId.equals("00306")){
//            moduleId = moduleId+"01";
//        }
        ViewObjectImpl impl = am.getnotification1();
        impl.setNamedWhereClauseParam("BindCldID", cldId);
        impl.setNamedWhereClauseParam("BindSlocId", slocId);
        impl.setNamedWhereClauseParam("BindHorgId", "01");
        impl.setNamedWhereClauseParam("BindOrgId", orgId);
        impl.setNamedWhereClauseParam("BindUsrId", usrId);
        impl.setNamedWhereClauseParam("BindModId", moduleId);
        impl.executeQuery();
        RowSetIterator itr = impl.createRowSetIterator(null);
        while(itr.hasNext()){
            msg = new HashMap();
            Row next = itr.next();
            msg.put("msgTxt", next.getAttribute("AlrtTxt"));
            msg.put("msgDate",DateFormat.getDateInstance().format(next.getAttribute("AlrtDt")));
            list.add(msg);
        }
        itr.closeRowSetIterator();
        if(list.isEmpty()){
            map.put("status","N");
            map.put("msg","No Data Found.");
        }else{
            map.put("status","Y");
            map.put("msgList",list);
        }
        releaseAm(am);
        am=null;
    return map;
    }

    /**
     * Container's getter for notification1.
     * @return notification1
     */
    public ViewObjectImpl getnotification1() {
        return (ViewObjectImpl) findViewObject("notification1");
    }

    /**
     * Container's getter for ArListVO1.
     * @return ArListVO1
     */
    public ViewObjectImpl getArListVO1() {
        return (ViewObjectImpl) findViewObject("ArListVO1");
    }
    
    public Map getAlertCountWS(String cldId,int slocId,String horgId,String orgId,int usrId){
        Map map = new HashMap();
        ebizMobileAppAMImpl am = getAm();
        ViewObjectImpl impl = am.getnotification1();
        impl.setNamedWhereClauseParam("BindCldID", cldId);
        impl.setNamedWhereClauseParam("BindHorgId", horgId);
        impl.setNamedWhereClauseParam("BindModId", "00303");
        impl.setNamedWhereClauseParam("BindOrgId", orgId);
        impl.setNamedWhereClauseParam("BindSlocId", slocId);
        impl.setNamedWhereClauseParam("BindUsrId", usrId);
        impl.executeQuery();
        Row[] allRowsInRange = impl.getAllRowsInRange();
        BigDecimal val = new BigDecimal(0.0);
        Number data = null;
        val = (BigDecimal)callStoredFunction(Types.NUMERIC,"fin.fn_user_vou_cnt(?,?,?,?,?,?)",
                                                new Object[] {cldId,horgId,slocId,orgId,usrId,"U"}, am);
        try {
            data = new Number(val);
        } catch (SQLException e) {
        }
        int docCount = (data==null? 0:data.intValue());
        if(val == null){
            map.put("pendingDocCount",docCount);
            map.put("alertCount", allRowsInRange.length);
            map.put("status", "N");
            map.put("msg", "No Data Found.");
        }else{
            map.put("status", "Y");
            map.put("pendingDocCount",docCount);
            map.put("alertCount", allRowsInRange.length);
        }
        
        releaseAm(am);
        am=null;
        return map;
    }
}
