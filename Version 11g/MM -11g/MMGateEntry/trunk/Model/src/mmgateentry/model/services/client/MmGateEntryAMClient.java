package mmgateentry.model.services.client;

import java.util.HashSet;

import mmgateentry.model.services.common.MmGateEntryAM;

import oracle.jbo.client.remote.ApplicationModuleImpl;
import oracle.jbo.domain.Timestamp;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Aug 16 16:37:07 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MmGateEntryAMClient extends ApplicationModuleImpl implements MmGateEntryAM {
    /**
     * This is the default constructor (do not remove).
     */
    public MmGateEntryAMClient() {
    }


    public void DeleteGE() {
        Object _ret = this.riInvokeExportedMethod(this,"DeleteGE",null,null);
        return;
    }

    public void LinkItemtoOrganisation(String CldId, Integer SlocId, String OrgId, String HoOrgId, Integer UsrId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"LinkItemtoOrganisation",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.String","java.lang.Integer"},new Object[] {CldId, SlocId, OrgId, HoOrgId, UsrId});
        return;
    }

    public void addItemToGe(String ItmName, String ItmUom, oracle.jbo.domain.Number ItmQty) {
        Object _ret =
            this.riInvokeExportedMethod(this,"addItemToGe",new String [] {"java.lang.String","java.lang.String","oracle.jbo.domain.Number"},new Object[] {ItmName, ItmUom, ItmQty});
        return;
    }

    public String callForValidateToleranceQty(String CldId, Integer SlocId, String OrgId, String p_itm_id,
                                              oracle.jbo.domain.Number retn_qty) {
        Object _ret =
            this.riInvokeExportedMethod(this,"callForValidateToleranceQty",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.String","oracle.jbo.domain.Number"},new Object[] {CldId, SlocId, OrgId, p_itm_id, retn_qty});
        return (String)_ret;
    }

    public String checkCurrCompatibility(String OrgId, String CldId, Integer SlocId, String PoDocId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"checkCurrCompatibility",new String [] {"java.lang.String","java.lang.String","java.lang.Integer","java.lang.String"},new Object[] {OrgId, CldId, SlocId, PoDocId});
        return (String)_ret;
    }

    public String checkForDuplicateDocNo(String CldId, Integer SlocId, String OrgId, String SrcDocNo) {
        Object _ret =
            this.riInvokeExportedMethod(this,"checkForDuplicateDocNo",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.String"},new Object[] {CldId, SlocId, OrgId, SrcDocNo});
        return (String)_ret;
    }

    public HashSet checkForRcvdQtyPresent(String CldId, Integer slocId, String OrgId, String whId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"checkForRcvdQtyPresent",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.String"},new Object[] {CldId, slocId, OrgId, whId});
        return (HashSet)_ret;
    }

    public String checkItmDuplicate(String itmNm, String uomId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"checkItmDuplicate",new String [] {"java.lang.String","java.lang.String"},new Object[] {itmNm, uomId});
        return (String)_ret;
    }

    public String checkNoOfDocument() {
        Object _ret = this.riInvokeExportedMethod(this,"checkNoOfDocument",null,null);
        return (String)_ret;
    }

    public String checkRetQty() {
        Object _ret = this.riInvokeExportedMethod(this,"checkRetQty",null,null);
        return (String)_ret;
    }

    public String chkDaysTolerance() {
        Object _ret = this.riInvokeExportedMethod(this,"chkDaysTolerance",null,null);
        return (String)_ret;
    }

    public String chkItmTrfQty() {
        Object _ret = this.riInvokeExportedMethod(this,"chkItmTrfQty",null,null);
        return (String)_ret;
    }

    public String chkToleranceDays(String orgId, Integer SlocId, String CldId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"chkToleranceDays",new String [] {"java.lang.String","java.lang.Integer","java.lang.String"},new Object[] {orgId, SlocId, CldId});
        return (String)_ret;
    }

    public String chkToleranceQty(String orgId, Integer SlocId, String CldId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"chkToleranceQty",new String [] {"java.lang.String","java.lang.Integer","java.lang.String"},new Object[] {orgId, SlocId, CldId});
        return (String)_ret;
    }

    public HashSet chkdlvryQtyForItem(String CldId, Integer slocId, String OrgId, String whId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"chkdlvryQtyForItem",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.String"},new Object[] {CldId, slocId, OrgId, whId});
        return (HashSet)_ret;
    }

    public String compTotRcptQty(oracle.jbo.domain.Number RetQty) {
        Object _ret = this.riInvokeExportedMethod(this,"compTotRcptQty",new String [] {"oracle.jbo.domain.Number"},new Object[] {RetQty});
        return (String)_ret;
    }

    public void deleteDocument() {
        Object _ret = this.riInvokeExportedMethod(this,"deleteDocument",null,null);
        return;
    }

    public void deleteItemsNonRecvd(String CldId, Integer slocId, String OrgId, String whId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"deleteItemsNonRecvd",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.String"},new Object[] {CldId, slocId, OrgId, whId});
        return;
    }

    public void generateTempDocId(Integer UsrId, String CldId, Integer SlocId, String OrgId, String WhId,
                                  Integer DocTypeSrc) {
        Object _ret =
            this.riInvokeExportedMethod(this,"generateTempDocId",new String [] {"java.lang.Integer","java.lang.String","java.lang.Integer","java.lang.String","java.lang.String","java.lang.Integer"},new Object[] {UsrId, CldId, SlocId, OrgId, WhId, DocTypeSrc});
        return;
    }

    public Integer getFYid(String CldId, String OrgId, Timestamp geDate, String Mode) {
        Object _ret =
            this.riInvokeExportedMethod(this,"getFYid",new String [] {"java.lang.String","java.lang.String","oracle.jbo.domain.Timestamp","java.lang.String"},new Object[] {CldId, OrgId, geDate, Mode});
        return (Integer)_ret;
    }

    public Integer getGESrcCount() {
        Object _ret = this.riInvokeExportedMethod(this,"getGESrcCount",null,null);
        return (Integer)_ret;
    }

    public String getGeNo(Integer SlocId, String CldId, String OrgId, String WhId, Integer fyId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"getGeNo",new String [] {"java.lang.Integer","java.lang.String","java.lang.String","java.lang.String","java.lang.Integer"},new Object[] {SlocId, CldId, OrgId, WhId, fyId});
        return (String)_ret;
    }

    public void getGeRecordForEdit(String CldId, Integer SlocId, String OrgId, String TxnId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"getGeRecordForEdit",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.String"},new Object[] {CldId, SlocId, OrgId, TxnId});
        return;
    }

    public void getItemsForPopUp(Integer docType, Integer SlocId, String CldId, String OrgId, String HoOrgId,
                                 String sourceDocNo) {
        Object _ret =
            this.riInvokeExportedMethod(this,"getItemsForPopUp",new String [] {"java.lang.Integer","java.lang.Integer","java.lang.String","java.lang.String","java.lang.String","java.lang.String"},new Object[] {docType, SlocId, CldId, OrgId, HoOrgId, sourceDocNo});
        return;
    }

    public String isDocumentPresent() {
        Object _ret = this.riInvokeExportedMethod(this,"isDocumentPresent",null,null);
        return (String)_ret;
    }

    public boolean isFYOpenForCurrentDate(String cldId, String orgId, Timestamp dt) {
        Object _ret =
            this.riInvokeExportedMethod(this,"isFYOpenForCurrentDate",new String [] {"java.lang.String","java.lang.String","oracle.jbo.domain.Timestamp"},new Object[] {cldId, orgId, dt});
        return ((Boolean)_ret).booleanValue();
    }

    public String isPoWarehouseCompatible() {
        Object _ret = this.riInvokeExportedMethod(this,"isPoWarehouseCompatible",null,null);
        return (String)_ret;
    }

    public Integer populateGeItmfromCPo() {
        Object _ret = this.riInvokeExportedMethod(this,"populateGeItmfromCPo",null,null);
        return (Integer)_ret;
    }

    public Integer populateGeItmfromPo() {
        Object _ret = this.riInvokeExportedMethod(this,"populateGeItmfromPo",null,null);
        return (Integer)_ret;
    }

    public void populateGeItmfromTrfOrd() {
        Object _ret = this.riInvokeExportedMethod(this,"populateGeItmfromTrfOrd",null,null);
        return;
    }

    public void returnGe(String Flg) {
        Object _ret = this.riInvokeExportedMethod(this,"returnGe",new String [] {"java.lang.String"},new Object[] {Flg});
        return;
    }

    public void setCurrIdSpInMtlGe() {
        Object _ret = this.riInvokeExportedMethod(this,"setCurrIdSpInMtlGe",null,null);
        return;
    }

    public void setCurrIdSpPoToMtlGe(Integer value) {
        Object _ret = this.riInvokeExportedMethod(this,"setCurrIdSpPoToMtlGe",new String [] {"java.lang.Integer"},new Object[] {value});
        return;
    }

    public void setNullforAll() {
        Object _ret = this.riInvokeExportedMethod(this,"setNullforAll",null,null);
        return;
    }

    public void setStatusforGe(String stat) {
        Object _ret = this.riInvokeExportedMethod(this,"setStatusforGe",new String [] {"java.lang.String"},new Object[] {stat});
        return;
    }

    public void setTxnIdForCurrentRow(Integer UsrId, String CldId, Integer SlocId, String OrgId, String whIdDef,
                                      String chkWf) {
        Object _ret =
            this.riInvokeExportedMethod(this,"setTxnIdForCurrentRow",new String [] {"java.lang.Integer","java.lang.String","java.lang.Integer","java.lang.String","java.lang.String","java.lang.String"},new Object[] {UsrId, CldId, SlocId, OrgId, whIdDef, chkWf});
        return;
    }

    public void updateTxnQty(oracle.jbo.domain.Number oldRcptQty, oracle.jbo.domain.Number newRcptQty) {
        Object _ret =
            this.riInvokeExportedMethod(this,"updateTxnQty",new String [] {"oracle.jbo.domain.Number","oracle.jbo.domain.Number"},new Object[] {oldRcptQty, newRcptQty});
        return;
    }

    public void updtRcptQty(oracle.jbo.domain.Number TotRcpt) {
        Object _ret = this.riInvokeExportedMethod(this,"updtRcptQty",new String [] {"oracle.jbo.domain.Number"},new Object[] {TotRcpt});
        return;
    }

    public void updtRcptRetnTmpRcptQty(oracle.jbo.domain.Number TotRcpt) {
        Object _ret =
            this.riInvokeExportedMethod(this,"updtRcptRetnTmpRcptQty",new String [] {"oracle.jbo.domain.Number"},new Object[] {TotRcpt});
        return;
    }

    public void updtRcptTmpRcptQty(oracle.jbo.domain.Number RetnQty) {
        Object _ret =
            this.riInvokeExportedMethod(this,"updtRcptTmpRcptQty",new String [] {"oracle.jbo.domain.Number"},new Object[] {RetnQty});
        return;
    }

    public String validatePoRcptQty(oracle.jbo.domain.Number rcptQty) {
        Object _ret =
            this.riInvokeExportedMethod(this,"validatePoRcptQty",new String [] {"oracle.jbo.domain.Number"},new Object[] {rcptQty});
        return (String)_ret;
    }

    public String validateToleranceDays(String CldId, Integer SlocId, String OrgId, String p_po_no,
                                        Timestamp p_rcpt_date) {
        Object _ret =
            this.riInvokeExportedMethod(this,"validateToleranceDays",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.String","oracle.jbo.domain.Timestamp"},new Object[] {CldId, SlocId, OrgId, p_po_no, p_rcpt_date});
        return (String)_ret;
    }

    public String validateToleranceQty(String CldId, Integer SlocId, String OrgId, String p_itm_id,
                                       oracle.jbo.domain.Number rcpt_qty) {
        Object _ret =
            this.riInvokeExportedMethod(this,"validateToleranceQty",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.String","oracle.jbo.domain.Number"},new Object[] {CldId, SlocId, OrgId, p_itm_id, rcpt_qty});
        return (String)_ret;
    }
}
