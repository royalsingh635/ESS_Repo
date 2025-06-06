package application.model.services;

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
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Nov 17 10:16:50 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class EbizMmMobileWSAMImpl extends ApplicationModuleImpl {
    
    //private static int VARCHAR = Types.VARCHAR;
    private static int INTEGER = Types.NUMERIC;
    /**
     * This is the default constructor (do not remove).
     */
    public EbizMmMobileWSAMImpl() {
    }

    /**
     * Container's getter for GrpInfo1.
     * @return GrpInfo1
     */
    public ViewObjectImpl getGrpInfo1() {
        return (ViewObjectImpl) findViewObject("GrpInfo1");
    }

    /**
     * Container's getter for Item1.
     * @return Item1
     */
    public ViewObjectImpl getItem1() {
        return (ViewObjectImpl) findViewObject("Item1");
    }
    /**
     * Container's getter for PurchaseHis1.
     * @return PurchaseHis1
     */
    public ViewObjectImpl getPurchaseHis1() {
        return (ViewObjectImpl) findViewObject("PurchaseHis1");
    }
    public void releaseAm(EbizMmMobileWSAMImpl am) {
        Configuration.releaseRootApplicationModule(am, true);
    }
    public EbizMmMobileWSAMImpl getAm() {
        String appModuleName = "application.model.services.EbizMmMobileWSAM";
        String appModuleConfig = "EbizMmMobileWSAMLocal";
        return (EbizMmMobileWSAMImpl)Configuration.createRootApplicationModule(appModuleName, appModuleConfig);
    }

    @SuppressWarnings("unchecked")
    public Map getGrpList(String cldId,int slocId,String horgId,String orgId){
        EbizMmMobileWSAMImpl am = getAm();
        Map map = new HashMap();
        List list = new ArrayList();
        Map innerMap = null;
        ViewObjectImpl grpInfo1 = am.getGrpInfo1();
        grpInfo1.setNamedWhereClauseParam("BindCldId", cldId);
        grpInfo1.setNamedWhereClauseParam("BindSlocId", slocId);
        grpInfo1.setNamedWhereClauseParam("BindHorgId", horgId);
        grpInfo1.setNamedWhereClauseParam("BindOrgId", orgId);
        grpInfo1.executeQuery();
        RowSetIterator grp = grpInfo1.createRowSetIterator("test");
        while(grp.hasNext()){
            innerMap = new HashMap();
            Row row = grp.next();
            innerMap.put("grpId",row.getAttribute("GrpId"));
            innerMap.put("grpNm",row.getAttribute("GrpNm"));
            list.add(innerMap);
        }
        grp.closeRowSetIterator();
        if(list.isEmpty()){
            map.put("grpInfo",list);
            map.put("status","N");
            map.put("msg","No Data Found.");
        }else{
            map.put("grpInfo",list);
            map.put("status","Y");
        }
        
        releaseAm(am);
        am = null;
        return map;
    }

    @SuppressWarnings("unchecked")
    public Map getItmList(String cldId,int slocId,String horgId,String orgId,String capitalItmFlg,String srvsItmFlg,String stockableItmFlg,
                          String grpId,String itmDesc,int lower,int upper){
        EbizMmMobileWSAMImpl am = getAm();
        Map map = new HashMap();
        List list = new ArrayList();
        Map innerMap = null;
        ViewObjectImpl itemList = am.getItem1();
        itemList.setNamedWhereClauseParam("BindCldId", cldId);
        itemList.setNamedWhereClauseParam("BindSlocId", slocId);
        itemList.setNamedWhereClauseParam("BindHorgId", horgId);
        itemList.setNamedWhereClauseParam("BindOrgId", orgId);
        itemList.setNamedWhereClauseParam("BindCapitalItmFlg",capitalItmFlg);
        itemList.setNamedWhereClauseParam("BindSrvsItmFlg", srvsItmFlg);
        itemList.setNamedWhereClauseParam("BindStockableItmFlg",stockableItmFlg);
        itemList.setNamedWhereClauseParam("BindGrpId", (grpId.equals("null")?null:grpId));
        itemList.setNamedWhereClauseParam("BindItmDesc", (itmDesc.equals("null")?null:itmDesc));
        itemList.setNamedWhereClauseParam("BindSTRT", lower);
        itemList.setNamedWhereClauseParam("BindEND", upper);
        itemList.executeQuery();
        RowSetIterator itm = itemList.createRowSetIterator("test");
        while(itm.hasNext()){
            innerMap = new HashMap();
            Row row = itm.next();
            innerMap.put("itmId",row.getAttribute("ItmId"));
            innerMap.put("itmNm",row.getAttribute("ItmDesc"));
            innerMap.put("basicPrice",row.getAttribute("PriceBasic"));
            innerMap.put("grpNm",row.getAttribute("GrpNm"));
            list.add(innerMap);
        }
        itm.closeRowSetIterator();
        if(list.isEmpty()){
            map.put("itmList",list);
            map.put("status","N");
            map.put("msg","No Data Found.");
        }else{
            map.put("itmList",list);
            map.put("status","Y");
        }
        releaseAm(am);
        am = null;
        return map;
    }
    
    @SuppressWarnings("unchecked")
    public Map getItmGnrlInfo(String cldId,int slocId,String horgId,String itmId){
        EbizMmMobileWSAMImpl am = getAm();
        Map map = new HashMap();
        ViewObjectImpl itemList = am.getitemGeneralInfo1();
        itemList.setNamedWhereClauseParam("BindCldId", cldId);
        itemList.setNamedWhereClauseParam("BindSlocId", slocId);
        itemList.setNamedWhereClauseParam("BindHorgId", horgId);
        itemList.setNamedWhereClauseParam("BindItemId", itmId);
        itemList.executeQuery();
        RowSetIterator itm = itemList.createRowSetIterator("test");
        while(itm.hasNext()){
            Row row = itm.next();
            map.put("itmId",row.getAttribute("ItmId"));
            map.put("itmNm",row.getAttribute("ItmDesc"));
            map.put("basicPrice",row.getAttribute("PriceBasic"));
            map.put("purPrice",row.getAttribute("PricePur"));
            map.put("slsPrice",row.getAttribute("PriceSls"));
            map.put("serviceItem",row.getAttribute("SrvcItmFlg"));
            map.put("stockableItem",row.getAttribute("StockableFlg"));
            map.put("slsItem",row.getAttribute("SlsItmFlg"));
            map.put("purItem",row.getAttribute("PurItmFlg"));
            map.put("wipItem",row.getAttribute("WipItmFlg"));
            map.put("taxExemptedItem",row.getAttribute("TaxExmptFlg"));
            map.put("capitalItem",row.getAttribute("CapitalGdFlg"));
            map.put("qcReqdItem",row.getAttribute("QcReqdFlg"));
            map.put("consumableItem",row.getAttribute("ConsumableFlg"));
            map.put("cashPurItem",row.getAttribute("CashPurFlg"));
            map.put("serializedItem",row.getAttribute("SerializedFlg"));
            map.put("sampleItem",row.getAttribute("SamplItmFlg"));
            map.put("uomOfBasic",row.getAttribute("Uombasic"));
            map.put("uomOfPurchase",row.getAttribute("Uompurchase"));
            map.put("uomOfSales",row.getAttribute("Uomsales"));
            map.put("itmAttributes",(row.getAttribute("Att")==null)?"Attribute not define.":row.getAttribute("Att"));
        }
        itm.closeRowSetIterator();
        releaseAm(am);
        am = null;
        if(map.isEmpty()){
            map.put("status","N");
            map.put("msg","No Data Found.");
            return map;
        }else{
            map.put("status","Y");
            return map;
        }
    }
    
    @SuppressWarnings("unchecked")
    public Map getItmStockInfo(String cldId,int slocId,String horgId,String itmId){
        EbizMmMobileWSAMImpl am = getAm();
        Map map = new HashMap();
        ViewObjectImpl itemList = am.getitmStockInfo1();
        itemList.setNamedWhereClauseParam("BindCldId", cldId);
        itemList.setNamedWhereClauseParam("BindSlocId", slocId);
        itemList.setNamedWhereClauseParam("BindHorgId", horgId);
        itemList.setNamedWhereClauseParam("BindItemId", itmId);
        itemList.executeQuery();
        RowSetIterator itm = itemList.createRowSetIterator("tt");
        while(itm.hasNext()){
            Row row = itm.next();
            map.put("itmId",row.getAttribute("ItmId"));
            map.put("itmNm",row.getAttribute("ItmDesc"));
            map.put("maxStock",(row.getAttribute("MaxStk")==null)?0:row.getAttribute("MaxStk"));
            map.put("minStock",(row.getAttribute("MinStk")==null)?0:row.getAttribute("MinStk"));
            map.put("safeStock",(row.getAttribute("SafeQty")==null)?0:row.getAttribute("SafeQty"));
            map.put("pickOrder",row.getAttribute("PickOrder"));
            map.put("recordLvl",(row.getAttribute("ReorderLvl")==null)?0:row.getAttribute("ReorderLvl"));
            map.put("selfLife",(row.getAttribute("ShelfLife")==null)?0:row.getAttribute("ShelfLife"));
            map.put("costMthd",row.getAttribute("CostMthd"));
        }
        itm.closeRowSetIterator();
        releaseAm(am);
        am = null;
        if(map.isEmpty()){
            map.put("status","N");
            map.put("msg","No Data Found.");
            return map;
        }else{
            map.put("status","Y");
            return map;
        }
    }
    
    @SuppressWarnings("unchecked")
    public Map getItmCoaInfo(String cldId,int slocId,String orgId,String itmId){
        EbizMmMobileWSAMImpl am = getAm();
        Map map = new HashMap();
        Map innerMap = null;
        List list = new ArrayList();
        ViewObjectImpl itemList = am.getitmCoaInfo1();
        itemList.setNamedWhereClauseParam("CldIdBind", cldId);
        itemList.setNamedWhereClauseParam("SlocIdBind", slocId);
        itemList.setNamedWhereClauseParam("OrgIdBind", orgId);
        itemList.setNamedWhereClauseParam("ItmIdBind", itmId);
        itemList.executeQuery();
        RowSetIterator itm = itemList.createRowSetIterator("tt");
        while(itm.hasNext()){
            innerMap = new HashMap();
            Row row = itm.next();
            innerMap.put("coaFor",row.getAttribute("CoaForDesc"));
            innerMap.put("coaNm",row.getAttribute("CoaNm"));
            list.add(innerMap);
        }
        itm.closeRowSetIterator();
        releaseAm(am);
        am = null;
        if(list.isEmpty()){
            map.put("coaList", list);
            map.put("status","N");
            map.put("msg","No Data Found.");
        }else{
            map.put("status","Y");
            map.put("coaList", list);
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public Map getPurchaseHistory(String cldId,int slocId,String orgId,String grpId,String itmDesc){
        EbizMmMobileWSAMImpl am = getAm();
        Map map = new HashMap();
        List list = new ArrayList();
        Map innerMap = null;
        ViewObjectImpl purHis = am.getPurchaseHis1();
        purHis.setNamedWhereClauseParam("BindCldId", cldId);
        purHis.setNamedWhereClauseParam("BindSlocId", slocId);
        purHis.setNamedWhereClauseParam("BindOrgId", orgId);
        purHis.setNamedWhereClauseParam("BindGrpId", (grpId.equals("null")?null:grpId));
        purHis.setNamedWhereClauseParam("BindItmDesc", (itmDesc.equals("null")?null:itmDesc));
        purHis.executeQuery();
        RowSetIterator grp = purHis.createRowSetIterator("test");
        while(grp.hasNext()){
            innerMap = new HashMap();
            Row row = grp.next();
            innerMap.put("itmId",row.getAttribute("ItmId"));
            innerMap.put("itmNm",row.getAttribute("ItmDesc"));
            innerMap.put("uomNm",row.getAttribute("UomDesc"));
            innerMap.put("grpNm",row.getAttribute("GrpNm"));
            innerMap.put("itmQty",row.getAttribute("ItmQty"));
            innerMap.put("itmAmount",(row.getAttribute("ItmAmount")==null)?0:row.getAttribute("ItmAmount"));
            list.add(innerMap);
        }
        grp.closeRowSetIterator();
        releaseAm(am);
        am = null;
        if(list.isEmpty()){
            map.put("purHistory",list);
            map.put("status","N");
            map.put("msg","No Data Found.");
        }else{
            map.put("status","Y");
            map.put("purHistory",list);
        }
        return map;
    }
    
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars,EbizMmMobileWSAMImpl am) {
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

    @SuppressWarnings("unchecked")
    public Map getApprovalDocList(String cldId,int slocId,String horgId,String orgId,int usrId){
        Map map = new HashMap();
        Map inner = null;
        List list = new ArrayList();
        int count =0;
        EbizMmMobileWSAMImpl am = getAm();
        //2028 Item Profile
        /* count = getPendingDocumentCount(2028, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 2028);
        inner.put("count", count);
        inner.put("docNm", "Item Profile");
        list.add(inner); */
        //18502	Request For Quotation
        /* count = getPendingDocumentCount(18502, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18502);
        inner.put("count", count);
        inner.put("docNm", "Request For Quotation");
        list.add(inner); */
        //18503	Quotation
        /* count = getPendingDocumentCount(18503, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18503);
        inner.put("count", count);
        inner.put("docNm", "Quotation");
        list.add(inner); */
        //18504	Purchase Order
        count = getPendingDocumentCount(18504, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18504);
        inner.put("count", count);
        inner.put("docNm", "Purchase Order");
        list.add(inner);
        //18508	Scrap Sales
        /* count = getPendingDocumentCount(18508, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18508);
        inner.put("count", count);
        inner.put("docNm", "Scrap Sales");
        list.add(inner);
        //18510	Quotation Analysis
        count = getPendingDocumentCount(18510, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18510);
        inner.put("count", count);
        inner.put("docNm", "Quotation Analysis");
        list.add(inner);
        //18513	Material Requisition Slip
        count = getPendingDocumentCount(18513, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18513);
        inner.put("count", count);
        inner.put("docNm", "Material Requisition Slip");
        list.add(inner);
        //18515	Material Receipt
        count = getPendingDocumentCount(18515, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18515);
        inner.put("count", count);
        inner.put("docNm", "Material Receipt");
        list.add(inner);
        //18517	Stock Adjustment
        count = getPendingDocumentCount(18517, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18517);
        inner.put("count", count);
        inner.put("docNm", "Stock Adjustment");
        list.add(inner);
        //18518	Stock Taking
        count = getPendingDocumentCount(18518, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18518);
        inner.put("count", count);
        inner.put("docNm", "Stock Taking");
        list.add(inner);
        //18519	Transfer Order
        count = getPendingDocumentCount(18519, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18519);
        inner.put("count", count);
        inner.put("docNm", "Transfer Order");
        list.add(inner);
        //18521	Purchase Invoice
        count = getPendingDocumentCount(18521, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18521);
        inner.put("count", count);
        inner.put("docNm", "Purchase Invoice");
        list.add(inner);
        //18528	Material Return Note
        count = getPendingDocumentCount(18528, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18528);
        inner.put("count", count);
        inner.put("docNm", "Material Return Note");
        list.add(inner);
        //18529	Purchase Return
        count = getPendingDocumentCount(18529, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18529);
        inner.put("count", count);
        inner.put("docNm", "Purchase Return");
        list.add(inner);
        //18534	Cash Purchase Order
        count = getPendingDocumentCount(18534, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18534);
        inner.put("count", count);
        inner.put("docNm", "Cash Purchase Order");
        list.add(inner);
        //18535	Import Declaration Form
        count = getPendingDocumentCount(18535, "M", cldId, slocId, orgId, usrId, am);
        inner = new HashMap();
        inner.put("docId", 18535);
        inner.put("count", count);
        inner.put("docNm", "Import Declaration Form");
        list.add(inner); */
        releaseAm(am);
        am=null;
        if(list.isEmpty()){
            map.put("docTyp",list);
            map.put("status","N");
            map.put("msg","No Data Found.");
        }else{
            map.put("status","Y");
            map.put("docTyp",list);
        }
        return map;
    }
    
    public Integer getPendingDocumentCount(int docId, String docOperationTyp, String cldId, int slocId,
                                           String orgId, int usrId,EbizMmMobileWSAMImpl am) {
        Object count = (Object)callStoredFunction(INTEGER,"MM.MM_GET_DOC_CNT(?,?,?,?,?,?)",
                                                new Object[] {cldId,slocId,orgId,docId,usrId,docOperationTyp}, am);
        int docCount = ((Number)count==null? 0:((Number)count).intValue());
            return docCount;
    }
    
    @SuppressWarnings("unchecked")
    public Map getPendingDocumentList(String cldId, int slocId, String horgId, String orgId, int usrId,
                                       String approvalTyp, int docId, int lowerLimit, int upperLimit) {
        Map map = new HashMap();
        List list = new ArrayList();
        EbizMmMobileWSAMImpl am = getAm();
        ViewObjectImpl mmdoc = null;
        switch (docId) {
        case 18504:
            {
                mmdoc = am.getpoApprovalList1();
                break;
            }
        }
        mmdoc.setNamedWhereClauseParam("CldIdBind", cldId);
        mmdoc.setNamedWhereClauseParam("Orgidbind", orgId);
        mmdoc.setNamedWhereClauseParam("SlocIdBind", slocId);
        mmdoc.setNamedWhereClauseParam("WfTypeBind", approvalTyp);
        mmdoc.setNamedWhereClauseParam("UsrIdBind", usrId);
        mmdoc.setNamedWhereClauseParam("RowsLowerLimit", lowerLimit);
        mmdoc.setNamedWhereClauseParam("RowsUpperLimit", upperLimit);
        mmdoc.executeQuery();
        RowSetIterator ctr = mmdoc.createRowSetIterator(null);
        list = this.getWfDocDetailsList(ctr, 18504);
        ctr.closeRowSetIterator();
        releaseAm(am);
        am = null;
        if(list.isEmpty()){
            map.put("docList",list);
            map.put("status","N");
            map.put("msg","No Data Found.");
        }else{
            map.put("status","Y");
            map.put("docList",list);
        }
        return map;
    }

    /**
     * Method to get the Workflow document details list by filtering tables from given parameters.
     *
     * @param itr
     * @param docTypId
     * @return
     */
    @SuppressWarnings({ "oracle.jdeveloper.java.unchecked-conversion-or-cast", "unchecked", "unchecked", "unchecked" })
    public List getWfDocDetailsList(RowSetIterator itr, Integer docTypId) {
        HashMap<Object, Object> h;
        List list = new ArrayList();
        RowSetIterator ct = itr;
        switch (docTypId) {
        case 18504:
            {
                while (ct.hasNext()) {
                    h = new HashMap<Object, Object>();
                    Row next = ct.next();
                    h.put("Id", next.getAttribute("DocId"));
                    h.put("crtDt",DateFormat.getDateInstance().format(next.getAttribute("PoDt")));
                    h.put("docId", next.getAttribute("PoId"));
                    h.put("narr", (next.getAttribute("Remarks")==null?"":next.getAttribute("Remarks")));
                    h.put("docTyp",next.getAttribute("PoTypeNm"));
                    h.put("crtBy",next.getAttribute("UsrIdCreateNm"));
                    list.add(h);
                }
            }
        }
        return list;
    }

    /**
     * Container's getter for poApprovalList1.
     * @return poApprovalList1
     */
    public ViewObjectImpl getpoApprovalList1() {
        return (ViewObjectImpl) findViewObject("poApprovalList1");
    }

    /**
     * Container's getter for itemGeneralInfo1.
     * @return itemGeneralInfo1
     */
    public ViewObjectImpl getitemGeneralInfo1() {
        return (ViewObjectImpl) findViewObject("itemGeneralInfo1");
    }

    /**
     * Container's getter for itmStockInfo1.
     * @return itmStockInfo1
     */
    public ViewObjectImpl getitmStockInfo1() {
        return (ViewObjectImpl) findViewObject("itmStockInfo1");
    }
    
    @SuppressWarnings("unchecked")
    public Map getPurchaseOrderDetail(String cldId, int slocId, String orgId, String docId){
        Map v = new HashMap();
        EbizMmMobileWSAMImpl am = getAm();
        ViewObjectImpl soDtlVO = am.getpoDetail1();
        soDtlVO.setNamedWhereClauseParam("CldIdBind", cldId);
        soDtlVO.setNamedWhereClauseParam("OrgIdBind", orgId);
        soDtlVO.setNamedWhereClauseParam("SlocIdBind", slocId);
        soDtlVO.setNamedWhereClauseParam("PoDocIdBind", docId);
        soDtlVO.executeQuery();
        RowSetIterator ctr = soDtlVO.createRowSetIterator(null);
        ArrayList a = null;
        while(ctr.hasNext()){
            a = new ArrayList();
            HashMap h = new HashMap();
            Row row = ctr.next();
            h.put("DocId", row.getAttribute("DocId"));
            h.put("SoId", row.getAttribute("PoId"));
            h.put("DocDt",DateFormat.getDateInstance().format(row.getAttribute("PoDt")));
            h.put("EoId", row.getAttribute("EoId"));
            h.put("EoNm", row.getAttribute("EoNm"));
            h.put("CurrIdSp", row.getAttribute("CurrIdSp"));
            h.put("CurrDesc", row.getAttribute("CurrDesc"));
            h.put("OrderType", row.getAttribute("PoType"));
            h.put("OrdrTypDesc", row.getAttribute("OrdrTypDesc"));
            h.put("TotAmtSp", row.getAttribute("PoAmtSp").toString());
            h.put("WfId", row.getAttribute("WfNo"));//WfNo//W034
            a.add(h);
        }
        ctr.closeRowSetIterator();
        if(a.isEmpty()){
            v.put("SoDetail", a);
            v.put("status","N");
            v.put("msg","No Data Found.");
        }else{
            v.put("status","Y");
            v.put("SoDetail", a);
        }
        ViewObjectImpl soItmDtlVO = am.getpoItemDetails1();
        soItmDtlVO.setNamedWhereClauseParam("CldIdBind", cldId);
        soItmDtlVO.setNamedWhereClauseParam("OrgIdBind", orgId);
        soItmDtlVO.setNamedWhereClauseParam("SlocIdBind", slocId);
        soItmDtlVO.setNamedWhereClauseParam("PoDocIdBind", docId);
        soItmDtlVO.executeQuery();
        RowSetIterator ctr1 = soItmDtlVO.createRowSetIterator(null);
        a = new ArrayList();
        int count =1;
        while(ctr1.hasNext()){
                HashMap h = new HashMap();
                Row row = ctr1.next();
                h.put("SrNo", count);
                h.put("ItmId", row.getAttribute("ItmId"));
                h.put("ItmDesc", row.getAttribute("ItmDesc"));
                h.put("ItmQty", row.getAttribute("OrdQty"));
                h.put("ItmRate", row.getAttribute("ItmPrice"));
                h.put("ItmDiscTyp", (row.getAttribute("DiscType")==null)?"A":row.getAttribute("DiscType"));
                h.put("DiscVal", (row.getAttribute("DiscVal")==null)?0:row.getAttribute("DiscVal"));
                h.put("ItmAmtSp", row.getAttribute("ItmAmtSp").toString());
                a.add(h);
            count+=1;
        }
        ctr1.closeRowSetIterator();
        v.put("SoItmDetail", a);
        releaseAm(am);
        am = null;
        return v;
    }

    /**
     * Container's getter for poItemDetails1.
     * @return poItemDetails1
     */
    public ViewObjectImpl getpoItemDetails1() {
        return (ViewObjectImpl) findViewObject("poItemDetails1");
    }

    /**
     * Container's getter for poDetail1.
     * @return poDetail1
     */
    public ViewObjectImpl getpoDetail1() {
        return (ViewObjectImpl) findViewObject("poDetail1");
    }

    /**
     * Container's getter for itmCoaInfo1.
     * @return itmCoaInfo1
     */
    public ViewObjectImpl getitmCoaInfo1() {
        return (ViewObjectImpl) findViewObject("itmCoaInfo1");
    }
    
    public Map getImage(){
        
        return new HashMap();
    }

    /**
     * Container's getter for image1.
     * @return image1
     */
    public ViewObjectImpl getimage1() {
        return (ViewObjectImpl) findViewObject("image1");
    }
    
    @SuppressWarnings("unchecked")
    public String getImgPath(String cldId,int slocId,String hoId,String itmId){
        String path ="N";
        EbizMmMobileWSAMImpl am = getAm();
        ViewObjectImpl purHis = am.getimage1();
        purHis.setNamedWhereClauseParam("bindCldId", cldId);
        purHis.setNamedWhereClauseParam("bindHoId", hoId );
        purHis.setNamedWhereClauseParam("bindItmId", itmId);
        purHis.setNamedWhereClauseParam("bindSlocId", slocId);
        purHis.executeQuery();
        RowSetIterator grp = purHis.createRowSetIterator("test");
        while(grp.hasNext()){
            Row row = grp.next();
            path = (String)row.getAttribute("ImgPath");
        }
        grp.closeRowSetIterator();
        releaseAm(am);
        am = null;
        return path;
    }
    
    public Map getAlertCountWS(String cldId,int slocId,String horgId,String orgId,int usrId){
                Map map = new HashMap();
                EbizMmMobileWSAMImpl am = getAm();
                ViewObjectImpl impl = am.getnotification1();
                impl.setNamedWhereClauseParam("BindCldID", cldId);
                impl.setNamedWhereClauseParam("BindHorgId", horgId);
                impl.setNamedWhereClauseParam("BindModId", "00305");
                impl.setNamedWhereClauseParam("BindOrgId", orgId);
                impl.setNamedWhereClauseParam("BindSlocId", slocId);
                impl.setNamedWhereClauseParam("BindUsrId", usrId);
                impl.executeQuery();
                Row[] allRowsInRange = impl.getAllRowsInRange();
                Integer purOrdrCount = getPendingDocumentCount(18504, "M", cldId, slocId, orgId, usrId, am);//PO
                if(purOrdrCount == null){
                            map.put("pendingDocCount",purOrdrCount);
                            map.put("alertCount", allRowsInRange.length);
                            map.put("status", "N");
                            map.put("msg", "No Data Found.");
                        }else{
                            map.put("status", "Y");
                            map.put("pendingDocCount",purOrdrCount);
                            map.put("alertCount", allRowsInRange.length);
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
}

