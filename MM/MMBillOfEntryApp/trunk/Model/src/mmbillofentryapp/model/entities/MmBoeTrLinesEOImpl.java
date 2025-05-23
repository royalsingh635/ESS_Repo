package mmbillofentryapp.model.entities;

import java.math.BigDecimal;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.domain.Number;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Apr 15 11:00:51 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MmBoeTrLinesEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        SlocId,
        OrgId,
        DocId,
        DocIdSrc,
        DlvSchdlNo,
        ItmId,
        ItmUom,
        TaxRuleId,
        TaxRuleFlg,
        TaxType,
        TaxCoaId,
        TaxAmtSp,
        TaxAmtBs,
        TaxPer,
        TaxRuleSlno,
        TaxRulePriority,
        EoId,
        InvcCreated,
        MmBoeTr;
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
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int DOCIDSRC = AttributesEnum.DocIdSrc.index();
    public static final int DLVSCHDLNO = AttributesEnum.DlvSchdlNo.index();
    public static final int ITMID = AttributesEnum.ItmId.index();
    public static final int ITMUOM = AttributesEnum.ItmUom.index();
    public static final int TAXRULEID = AttributesEnum.TaxRuleId.index();
    public static final int TAXRULEFLG = AttributesEnum.TaxRuleFlg.index();
    public static final int TAXTYPE = AttributesEnum.TaxType.index();
    public static final int TAXCOAID = AttributesEnum.TaxCoaId.index();
    public static final int TAXAMTSP = AttributesEnum.TaxAmtSp.index();
    public static final int TAXAMTBS = AttributesEnum.TaxAmtBs.index();
    public static final int TAXPER = AttributesEnum.TaxPer.index();
    public static final int TAXRULESLNO = AttributesEnum.TaxRuleSlno.index();
    public static final int TAXRULEPRIORITY = AttributesEnum.TaxRulePriority.index();
    public static final int EOID = AttributesEnum.EoId.index();
    public static final int INVCCREATED = AttributesEnum.InvcCreated.index();
    public static final int MMBOETR = AttributesEnum.MmBoeTr.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MmBoeTrLinesEOImpl() {
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("mmbillofentryapp.model.entities.MmBoeTrLinesEO");
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
     * Gets the attribute value for DlvSchdlNo, using the alias name DlvSchdlNo.
     * @return the value of DlvSchdlNo
     */
    public Integer getDlvSchdlNo() {
        return (Integer) getAttributeInternal(DLVSCHDLNO);
    }

    /**
     * Sets <code>value</code> as the attribute value for DlvSchdlNo.
     * @param value value to set the DlvSchdlNo
     */
    public void setDlvSchdlNo(Integer value) {
        setAttributeInternal(DLVSCHDLNO, value);
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
     * Gets the attribute value for ItmUom, using the alias name ItmUom.
     * @return the value of ItmUom
     */
    public String getItmUom() {
        return (String) getAttributeInternal(ITMUOM);
    }

    /**
     * Sets <code>value</code> as the attribute value for ItmUom.
     * @param value value to set the ItmUom
     */
    public void setItmUom(String value) {
        setAttributeInternal(ITMUOM, value);
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
     * Gets the attribute value for TaxType, using the alias name TaxType.
     * @return the value of TaxType
     */
    public Integer getTaxType() {
        return (Integer) getAttributeInternal(TAXTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for TaxType.
     * @param value value to set the TaxType
     */
    public void setTaxType(Integer value) {
        setAttributeInternal(TAXTYPE, value);
    }

    /**
     * Gets the attribute value for TaxCoaId, using the alias name TaxCoaId.
     * @return the value of TaxCoaId
     */
    public Integer getTaxCoaId() {
        return (Integer) getAttributeInternal(TAXCOAID);
    }

    /**
     * Sets <code>value</code> as the attribute value for TaxCoaId.
     * @param value value to set the TaxCoaId
     */
    public void setTaxCoaId(Integer value) {
        setAttributeInternal(TAXCOAID, value);
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
     * Gets the attribute value for TaxPer, using the alias name TaxPer.
     * @return the value of TaxPer
     */
    public Number getTaxPer() {
        return (Number) getAttributeInternal(TAXPER);
    }

    /**
     * Sets <code>value</code> as the attribute value for TaxPer.
     * @param value value to set the TaxPer
     */
    public void setTaxPer(Number value) {
        setAttributeInternal(TAXPER, value);
    }

    /**
     * Gets the attribute value for TaxRuleSlno, using the alias name TaxRuleSlno.
     * @return the value of TaxRuleSlno
     */
    public Integer getTaxRuleSlno() {
        return (Integer) getAttributeInternal(TAXRULESLNO);
    }

    /**
     * Sets <code>value</code> as the attribute value for TaxRuleSlno.
     * @param value value to set the TaxRuleSlno
     */
    public void setTaxRuleSlno(Integer value) {
        setAttributeInternal(TAXRULESLNO, value);
    }

    /**
     * Gets the attribute value for TaxRulePriority, using the alias name TaxRulePriority.
     * @return the value of TaxRulePriority
     */
    public Integer getTaxRulePriority() {
        return (Integer) getAttributeInternal(TAXRULEPRIORITY);
    }

    /**
     * Sets <code>value</code> as the attribute value for TaxRulePriority.
     * @param value value to set the TaxRulePriority
     */
    public void setTaxRulePriority(Integer value) {
        setAttributeInternal(TAXRULEPRIORITY, value);
    }

    /**
     * Gets the attribute value for EoId, using the alias name EoId.
     * @return the value of EoId
     */
    public Long getEoId() {
        return (Long) getAttributeInternal(EOID);
    }

    /**
     * Sets <code>value</code> as the attribute value for EoId.
     * @param value value to set the EoId
     */
    public void setEoId(Long value) {
        setAttributeInternal(EOID, value);
    }

    /**
     * Gets the attribute value for InvcCreated, using the alias name InvcCreated.
     * @return the value of InvcCreated
     */
    public String getInvcCreated() {
        return (String) getAttributeInternal(INVCCREATED);
    }

    /**
     * Sets <code>value</code> as the attribute value for InvcCreated.
     * @param value value to set the InvcCreated
     */
    public void setInvcCreated(String value) {
        setAttributeInternal(INVCCREATED, value);
    }

    /**
     * @return the associated entity MmBoeTrEOImpl.
     */
    public MmBoeTrEOImpl getMmBoeTr() {
        return (MmBoeTrEOImpl) getAttributeInternal(MMBOETR);
    }

    /**
     * Sets <code>value</code> as the associated entity MmBoeTrEOImpl.
     */
    public void setMmBoeTr(MmBoeTrEOImpl value) {
        setAttributeInternal(MMBOETR, value);
    }


    /**
     * @param cldId key constituent
     * @param slocId key constituent
     * @param orgId key constituent
     * @param docId key constituent
     * @param docIdSrc key constituent
     * @param dlvSchdlNo key constituent
     * @param itmId key constituent
     * @param itmUom key constituent
     * @param taxRuleId key constituent
     * @param taxCoaId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String cldId, Integer slocId, String orgId, String docId, String docIdSrc,
                                       Integer dlvSchdlNo, String itmId, String itmUom, Integer taxRuleId,
                                       Integer taxCoaId) {
        return new Key(new Object[] {
                       cldId, slocId, orgId, docId, docIdSrc, dlvSchdlNo, itmId, itmUom, taxRuleId, taxCoaId });
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
      //  super.lock();
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

