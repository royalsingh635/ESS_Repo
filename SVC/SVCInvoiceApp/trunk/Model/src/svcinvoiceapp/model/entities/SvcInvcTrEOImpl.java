package svcinvoiceapp.model.entities;

import oracle.jbo.domain.Timestamp;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.RowIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Nov 07 11:45:33 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class SvcInvcTrEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        SlocId,
        HoOrgId,
        OrgId,
        DocId,
        DocIdSrc,
        ItmId,
        TaxRuleId,
        TaxableAmtSp,
        TaxAmtSp,
        TaxRuleFlg,
        TaxAmtBs,
        TaxExmptFlg,
        PmtSchdlDt,
        SvcInvcItm,
        SvcInvcTrLines;
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
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int DOCIDSRC = AttributesEnum.DocIdSrc.index();
    public static final int ITMID = AttributesEnum.ItmId.index();
    public static final int TAXRULEID = AttributesEnum.TaxRuleId.index();
    public static final int TAXABLEAMTSP = AttributesEnum.TaxableAmtSp.index();
    public static final int TAXAMTSP = AttributesEnum.TaxAmtSp.index();
    public static final int TAXRULEFLG = AttributesEnum.TaxRuleFlg.index();
    public static final int TAXAMTBS = AttributesEnum.TaxAmtBs.index();
    public static final int TAXEXMPTFLG = AttributesEnum.TaxExmptFlg.index();
    public static final int PMTSCHDLDT = AttributesEnum.PmtSchdlDt.index();
    public static final int SVCINVCITM = AttributesEnum.SvcInvcItm.index();
    public static final int SVCINVCTRLINES = AttributesEnum.SvcInvcTrLines.index();

    /**
     * This is the default constructor (do not remove).
     */
    public SvcInvcTrEOImpl() {
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("svcinvoiceapp.model.entities.SvcInvcTrEO");
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
     * Gets the attribute value for DocIdSrc, using the alias name DocIdSrc.
     * @return the value of DocIdSrc
     */
    public String getDocIdSrc() {
        return (String) getAttributeInternal(DOCIDSRC);
    }

    /**
     * Sets <code>value</code> as the attribute value for DocIdSrc.
     * @param value value to set the DocIdSrc
     */
    public void setDocIdSrc(String value) {
        setAttributeInternal(DOCIDSRC, value);
    }

    /**
     * Gets the attribute value for ItmId, using the alias name ItmId.
     * @return the value of ItmId
     */
    public String getItmId() {
        return (String) getAttributeInternal(ITMID);
    }

    /**
     * Sets <code>value</code> as the attribute value for ItmId.
     * @param value value to set the ItmId
     */
    public void setItmId(String value) {
        setAttributeInternal(ITMID, value);
    }

    /**
     * Gets the attribute value for TaxRuleId, using the alias name TaxRuleId.
     * @return the value of TaxRuleId
     */
    public Integer getTaxRuleId() {
        return (Integer) getAttributeInternal(TAXRULEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for TaxRuleId.
     * @param value value to set the TaxRuleId
     */
    public void setTaxRuleId(Integer value) {
        setAttributeInternal(TAXRULEID, value);
    }

    /**
     * Gets the attribute value for TaxableAmtSp, using the alias name TaxableAmtSp.
     * @return the value of TaxableAmtSp
     */
    public Number getTaxableAmtSp() {
        return (Number) getAttributeInternal(TAXABLEAMTSP);
    }

    /**
     * Sets <code>value</code> as the attribute value for TaxableAmtSp.
     * @param value value to set the TaxableAmtSp
     */
    public void setTaxableAmtSp(Number value) {
        setAttributeInternal(TAXABLEAMTSP, value);
    }

    /**
     * Gets the attribute value for TaxAmtSp, using the alias name TaxAmtSp.
     * @return the value of TaxAmtSp
     */
    public Number getTaxAmtSp() {
        return (Number) getAttributeInternal(TAXAMTSP);
    }

    /**
     * Sets <code>value</code> as the attribute value for TaxAmtSp.
     * @param value value to set the TaxAmtSp
     */
    public void setTaxAmtSp(Number value) {
        setAttributeInternal(TAXAMTSP, value);
    }

    /**
     * Gets the attribute value for TaxRuleFlg, using the alias name TaxRuleFlg.
     * @return the value of TaxRuleFlg
     */
    public String getTaxRuleFlg() {
        return (String) getAttributeInternal(TAXRULEFLG);
    }

    /**
     * Sets <code>value</code> as the attribute value for TaxRuleFlg.
     * @param value value to set the TaxRuleFlg
     */
    public void setTaxRuleFlg(String value) {
        setAttributeInternal(TAXRULEFLG, value);
    }

    /**
     * Gets the attribute value for TaxAmtBs, using the alias name TaxAmtBs.
     * @return the value of TaxAmtBs
     */
    public Number getTaxAmtBs() {
        return (Number) getAttributeInternal(TAXAMTBS);
    }

    /**
     * Sets <code>value</code> as the attribute value for TaxAmtBs.
     * @param value value to set the TaxAmtBs
     */
    public void setTaxAmtBs(Number value) {
        setAttributeInternal(TAXAMTBS, value);
    }

    /**
     * Gets the attribute value for TaxExmptFlg, using the alias name TaxExmptFlg.
     * @return the value of TaxExmptFlg
     */
    public String getTaxExmptFlg() {
        return (String) getAttributeInternal(TAXEXMPTFLG);
    }

    /**
     * Sets <code>value</code> as the attribute value for TaxExmptFlg.
     * @param value value to set the TaxExmptFlg
     */
    public void setTaxExmptFlg(String value) {
        setAttributeInternal(TAXEXMPTFLG, value);
    }

    /**
     * Gets the attribute value for PmtSchdlDt, using the alias name PmtSchdlDt.
     * @return the value of PmtSchdlDt
     */
    public Timestamp getPmtSchdlDt() {
        return (Timestamp) getAttributeInternal(PMTSCHDLDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for PmtSchdlDt.
     * @param value value to set the PmtSchdlDt
     */
    public void setPmtSchdlDt(Timestamp value) {
        setAttributeInternal(PMTSCHDLDT, value);
    }

    /**
     * @return the associated entity SvcInvcItmEOImpl.
     */
    public SvcInvcItmEOImpl getSvcInvcItm() {
        return (SvcInvcItmEOImpl) getAttributeInternal(SVCINVCITM);
    }

    /**
     * Sets <code>value</code> as the associated entity SvcInvcItmEOImpl.
     */
    public void setSvcInvcItm(SvcInvcItmEOImpl value) {
        setAttributeInternal(SVCINVCITM, value);
    }

    /**
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getSvcInvcTrLines() {
        return (RowIterator) getAttributeInternal(SVCINVCTRLINES);
    }


    /**
     * @param cldId key constituent
     * @param slocId key constituent
     * @param orgId key constituent
     * @param docId key constituent
     * @param docIdSrc key constituent
     * @param itmId key constituent
     * @param taxRuleId key constituent
     * @param pmtSchdlDt key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String cldId, Integer slocId, String orgId, String docId, String docIdSrc,
                                       String itmId, Integer taxRuleId, Timestamp pmtSchdlDt) {
        return new Key(new Object[] { cldId, slocId, orgId, docId, docIdSrc, itmId, taxRuleId, pmtSchdlDt });
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        super.create(attributeList);
    }

    /**
     * Add locking logic here.
     */
    public void lock() {
       // super.lock();
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

