package mmstockswapapp.model.entities;

import java.sql.Timestamp;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Jan 09 11:01:09 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MmStkSwapEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        SlocId,
        OrgId,
        WhId,
        DocId,
        FyId,
        SwapId,
        SwapDt,
        SwapType,
        DocStat,
        AuthStat,
        UsrIdCreate,
        UsrIdCreateDt,
        UsrIdMod,
        UsrIdModDt;
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
    public static final int WHID = AttributesEnum.WhId.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int FYID = AttributesEnum.FyId.index();
    public static final int SWAPID = AttributesEnum.SwapId.index();
    public static final int SWAPDT = AttributesEnum.SwapDt.index();
    public static final int SWAPTYPE = AttributesEnum.SwapType.index();
    public static final int DOCSTAT = AttributesEnum.DocStat.index();
    public static final int AUTHSTAT = AttributesEnum.AuthStat.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MmStkSwapEOImpl() {
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
     * Gets the attribute value for WhId, using the alias name WhId.
     * @return the value of WhId
     */
    public String getWhId() {
        return (String) getAttributeInternal(WHID);
    }

    /**
     * Sets <code>value</code> as the attribute value for WhId.
     * @param value value to set the WhId
     */
    public void setWhId(String value) {
        setAttributeInternal(WHID, value);
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
     * Gets the attribute value for FyId, using the alias name FyId.
     * @return the value of FyId
     */
    public Integer getFyId() {
        return (Integer) getAttributeInternal(FYID);
    }

    /**
     * Sets <code>value</code> as the attribute value for FyId.
     * @param value value to set the FyId
     */
    public void setFyId(Integer value) {
        setAttributeInternal(FYID, value);
    }

    /**
     * Gets the attribute value for SwapId, using the alias name SwapId.
     * @return the value of SwapId
     */
    public String getSwapId() {
        return (String) getAttributeInternal(SWAPID);
    }

    /**
     * Sets <code>value</code> as the attribute value for SwapId.
     * @param value value to set the SwapId
     */
    public void setSwapId(String value) {
        setAttributeInternal(SWAPID, value);
    }

    /**
     * Gets the attribute value for SwapDt, using the alias name SwapDt.
     * @return the value of SwapDt
     */
    public Timestamp getSwapDt() {
        return (Timestamp) getAttributeInternal(SWAPDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for SwapDt.
     * @param value value to set the SwapDt
     */
    public void setSwapDt(Timestamp value) {
        setAttributeInternal(SWAPDT, value);
    }

    /**
     * Gets the attribute value for SwapType, using the alias name SwapType.
     * @return the value of SwapType
     */
    public Integer getSwapType() {
        return (Integer) getAttributeInternal(SWAPTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for SwapType.
     * @param value value to set the SwapType
     */
    public void setSwapType(Integer value) {
        setAttributeInternal(SWAPTYPE, value);
    }

    /**
     * Gets the attribute value for DocStat, using the alias name DocStat.
     * @return the value of DocStat
     */
    public Integer getDocStat() {
        return (Integer) getAttributeInternal(DOCSTAT);
    }

    /**
     * Sets <code>value</code> as the attribute value for DocStat.
     * @param value value to set the DocStat
     */
    public void setDocStat(Integer value) {
        setAttributeInternal(DOCSTAT, value);
    }

    /**
     * Gets the attribute value for AuthStat, using the alias name AuthStat.
     * @return the value of AuthStat
     */
    public String getAuthStat() {
        return (String) getAttributeInternal(AUTHSTAT);
    }

    /**
     * Sets <code>value</code> as the attribute value for AuthStat.
     * @param value value to set the AuthStat
     */
    public void setAuthStat(String value) {
        setAttributeInternal(AUTHSTAT, value);
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
     * @param cldId key constituent
     * @param slocId key constituent
     * @param orgId key constituent
     * @param whId key constituent
     * @param docId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String cldId, Integer slocId, String orgId, String whId, String docId) {
        return new Key(new Object[] { cldId, slocId, orgId, whId, docId });
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("mmstockswapapp.model.entities.MmStkSwapEO");
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        
        super.create(attributeList);
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
        //super.lock();
    }

    /**
     * Custom DML update/insert/delete logic here.
     * @param operation the operation type
     * @param e the transaction event
     */
    protected void doDML(int operation, TransactionEvent e) {
        super.doDML(operation, e);
    }
}

