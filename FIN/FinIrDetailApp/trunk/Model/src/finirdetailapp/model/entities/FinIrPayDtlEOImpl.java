package finirdetailapp.model.entities;

import static adf.utils.ebiz.EbizParams.GLBL_APP_USR;

import java.sql.Timestamp;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.domain.Number;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Jul 09 16:21:53 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class FinIrPayDtlEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        SlocId,
        OrgId,
        HoOrgId,
        DocId,
        PayMode,
        InstrmntSlNo,
        PrjId,
        InstrmntTyp,
        InstrmntNo,
        InstrmntDt,
        AmtSp,
        AmtBs,
        Remarks,
        CcId,
        UsrIdCreate,
        UsrIdCreateDt,
        UsrIdMod,
        UsrIdModDt,
        FinIrEO;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int PAYMODE = AttributesEnum.PayMode.index();
    public static final int INSTRMNTSLNO = AttributesEnum.InstrmntSlNo.index();
    public static final int PRJID = AttributesEnum.PrjId.index();
    public static final int INSTRMNTTYP = AttributesEnum.InstrmntTyp.index();
    public static final int INSTRMNTNO = AttributesEnum.InstrmntNo.index();
    public static final int INSTRMNTDT = AttributesEnum.InstrmntDt.index();
    public static final int AMTSP = AttributesEnum.AmtSp.index();
    public static final int AMTBS = AttributesEnum.AmtBs.index();
    public static final int REMARKS = AttributesEnum.Remarks.index();
    public static final int CCID = AttributesEnum.CcId.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int FINIREO = AttributesEnum.FinIrEO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public FinIrPayDtlEOImpl() {
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("finirdetailapp.model.entities.FinIrPayDtlEO");
    }


    /**
     * Gets the attribute value for CldId, using the alias name CldId.
     * @return the value of CldId
     */
    public String getCldId() {
        return (String) getAttributeInternal(CLDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CldId.
     * @param value value to set the CldId
     */
    public void setCldId(String value) {
        setAttributeInternal(CLDID, value);
    }

    /**
     * Gets the attribute value for SlocId, using the alias name SlocId.
     * @return the value of SlocId
     */
    public Integer getSlocId() {
        return (Integer) getAttributeInternal(SLOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for SlocId.
     * @param value value to set the SlocId
     */
    public void setSlocId(Integer value) {
        setAttributeInternal(SLOCID, value);
    }

    /**
     * Gets the attribute value for OrgId, using the alias name OrgId.
     * @return the value of OrgId
     */
    public String getOrgId() {
        return (String) getAttributeInternal(ORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for OrgId.
     * @param value value to set the OrgId
     */
    public void setOrgId(String value) {
        setAttributeInternal(ORGID, value);
    }

    /**
     * Gets the attribute value for HoOrgId, using the alias name HoOrgId.
     * @return the value of HoOrgId
     */
    public String getHoOrgId() {
        return (String) getAttributeInternal(HOORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for HoOrgId.
     * @param value value to set the HoOrgId
     */
    public void setHoOrgId(String value) {
        setAttributeInternal(HOORGID, value);
    }

    /**
     * Gets the attribute value for DocId, using the alias name DocId.
     * @return the value of DocId
     */
    public String getDocId() {
        return (String) getAttributeInternal(DOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for DocId.
     * @param value value to set the DocId
     */
    public void setDocId(String value) {
        setAttributeInternal(DOCID, value);
    }

    /**
     * Gets the attribute value for PayMode, using the alias name PayMode.
     * @return the value of PayMode
     */
    public Integer getPayMode() {
        return (Integer) getAttributeInternal(PAYMODE);
    }

    /**
     * Sets <code>value</code> as the attribute value for PayMode.
     * @param value value to set the PayMode
     */
    public void setPayMode(Integer value) {
        setAttributeInternal(PAYMODE, value);
    }

    /**
     * Gets the attribute value for InstrmntSlNo, using the alias name InstrmntSlNo.
     * @return the value of InstrmntSlNo
     */
    public Integer getInstrmntSlNo() {
        return (Integer) getAttributeInternal(INSTRMNTSLNO);
    }

    /**
     * Sets <code>value</code> as the attribute value for InstrmntSlNo.
     * @param value value to set the InstrmntSlNo
     */
    public void setInstrmntSlNo(Integer value) {
        setAttributeInternal(INSTRMNTSLNO, value);
    }

    /**
     * Gets the attribute value for PrjId, using the alias name PrjId.
     * @return the value of PrjId
     */
    public String getPrjId() {
        return (String) getAttributeInternal(PRJID);
    }

    /**
     * Sets <code>value</code> as the attribute value for PrjId.
     * @param value value to set the PrjId
     */
    public void setPrjId(String value) {
        setAttributeInternal(PRJID, value);
    }

    /**
     * Gets the attribute value for InstrmntTyp, using the alias name InstrmntTyp.
     * @return the value of InstrmntTyp
     */
    public Integer getInstrmntTyp() {
        return (Integer) getAttributeInternal(INSTRMNTTYP);
    }

    /**
     * Sets <code>value</code> as the attribute value for InstrmntTyp.
     * @param value value to set the InstrmntTyp
     */
    public void setInstrmntTyp(Integer value) {
        setAttributeInternal(INSTRMNTTYP, value);
    }

    /**
     * Gets the attribute value for InstrmntNo, using the alias name InstrmntNo.
     * @return the value of InstrmntNo
     */
    public String getInstrmntNo() {
        return (String) getAttributeInternal(INSTRMNTNO);
    }

    /**
     * Sets <code>value</code> as the attribute value for InstrmntNo.
     * @param value value to set the InstrmntNo
     */
    public void setInstrmntNo(String value) {
        setAttributeInternal(INSTRMNTNO, value);
    }

    /**
     * Gets the attribute value for InstrmntDt, using the alias name InstrmntDt.
     * @return the value of InstrmntDt
     */
    public Timestamp getInstrmntDt() {
        return (Timestamp) getAttributeInternal(INSTRMNTDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for InstrmntDt.
     * @param value value to set the InstrmntDt
     */
    public void setInstrmntDt(Timestamp value) {
        setAttributeInternal(INSTRMNTDT, value);
    }

    /**
     * Gets the attribute value for AmtSp, using the alias name AmtSp.
     * @return the value of AmtSp
     */
    public Number getAmtSp() {
        return (Number) getAttributeInternal(AMTSP);
    }

    /**
     * Sets <code>value</code> as the attribute value for AmtSp.
     * @param value value to set the AmtSp
     */
    public void setAmtSp(Number value) {
        setAttributeInternal(AMTSP, value);
    }

    /**
     * Gets the attribute value for AmtBs, using the alias name AmtBs.
     * @return the value of AmtBs
     */
    public Number getAmtBs() {
        return (Number) getAttributeInternal(AMTBS);
    }

    /**
     * Sets <code>value</code> as the attribute value for AmtBs.
     * @param value value to set the AmtBs
     */
    public void setAmtBs(Number value) {
        setAttributeInternal(AMTBS, value);
    }

    /**
     * Gets the attribute value for Remarks, using the alias name Remarks.
     * @return the value of Remarks
     */
    public String getRemarks() {
        return (String) getAttributeInternal(REMARKS);
    }

    /**
     * Sets <code>value</code> as the attribute value for Remarks.
     * @param value value to set the Remarks
     */
    public void setRemarks(String value) {
        setAttributeInternal(REMARKS, value);
    }

    /**
     * Gets the attribute value for CcId, using the alias name CcId.
     * @return the value of CcId
     */
    public String getCcId() {
        return (String) getAttributeInternal(CCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CcId.
     * @param value value to set the CcId
     */
    public void setCcId(String value) {
        setAttributeInternal(CCID, value);
    }

    /**
     * Gets the attribute value for UsrIdCreate, using the alias name UsrIdCreate.
     * @return the value of UsrIdCreate
     */
    public Integer getUsrIdCreate() {
        return (Integer) getAttributeInternal(USRIDCREATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdCreate.
     * @param value value to set the UsrIdCreate
     */
    public void setUsrIdCreate(Integer value) {
        setAttributeInternal(USRIDCREATE, value);
    }

    /**
     * Gets the attribute value for UsrIdCreateDt, using the alias name UsrIdCreateDt.
     * @return the value of UsrIdCreateDt
     */
    public Timestamp getUsrIdCreateDt() {
        return (Timestamp) getAttributeInternal(USRIDCREATEDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdCreateDt.
     * @param value value to set the UsrIdCreateDt
     */
    public void setUsrIdCreateDt(Timestamp value) {
        setAttributeInternal(USRIDCREATEDT, value);
    }

    /**
     * Gets the attribute value for UsrIdMod, using the alias name UsrIdMod.
     * @return the value of UsrIdMod
     */
    public Integer getUsrIdMod() {
        return (Integer) getAttributeInternal(USRIDMOD);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdMod.
     * @param value value to set the UsrIdMod
     */
    public void setUsrIdMod(Integer value) {
        setAttributeInternal(USRIDMOD, value);
    }

    /**
     * Gets the attribute value for UsrIdModDt, using the alias name UsrIdModDt.
     * @return the value of UsrIdModDt
     */
    public Timestamp getUsrIdModDt() {
        return (Timestamp) getAttributeInternal(USRIDMODDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdModDt.
     * @param value value to set the UsrIdModDt
     */
    public void setUsrIdModDt(Timestamp value) {
        setAttributeInternal(USRIDMODDT, value);
    }

    /**
     * @return the associated entity FinIrEOImpl.
     */
    public FinIrEOImpl getFinIrEO() {
        return (FinIrEOImpl) getAttributeInternal(FINIREO);
    }

    /**
     * Sets <code>value</code> as the associated entity FinIrEOImpl.
     */
    public void setFinIrEO(FinIrEOImpl value) {
        setAttributeInternal(FINIREO, value);
    }


    /**
     * @param cldId key constituent
     * @param slocId key constituent
     * @param orgId key constituent
     * @param hoOrgId key constituent
     * @param docId key constituent
     * @param payMode key constituent
     * @param instrmntSlNo key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String cldId, Integer slocId, String orgId, String hoOrgId, String docId,
                                       Integer payMode, Integer instrmntSlNo) {
        return new Key(new Object[] { cldId, slocId, orgId, hoOrgId, docId, payMode, instrmntSlNo });
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        super.create(attributeList);
        this.setUsrIdCreate(GLBL_APP_USR());
        this.setUsrIdCreateDt(new Timestamp(System.currentTimeMillis()));
    }

    /**
     * Add entity remove logic in this method.
     */
    public void remove() {
        super.remove();
    }

    /**
     * Add locking logic here.
     */
    public void lock() {
        //   super.lock();
    }

    /**
     * Custom DML update/insert/delete logic here.
     * @param operation the operation type
     * @param e the transaction event
     */
    protected void doDML(int operation, TransactionEvent e) {
        super.doDML(operation, e);
        this.setUsrIdMod(GLBL_APP_USR());
        this.setUsrIdModDt(new Timestamp(System.currentTimeMillis()));
    }
}

