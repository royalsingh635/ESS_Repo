package suggestedorder.model.views;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.sql.Timestamp;

import oracle.jbo.RowSet;
import oracle.jbo.domain.Date;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Apr 01 17:26:57 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ViewPOQueryRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        SlocId {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getSlocId();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        CldId {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getCldId();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setCldId((String)value);
            }
        }
        ,
        OrgId {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getOrgId();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setOrgId((String)value);
            }
        }
        ,
        DocId {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getDocId();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setDocId((String)value);
            }
        }
        ,
        DocDt {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getDocDt();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setDocDt((Date)value);
            }
        }
        ,
        PoId {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getPoId();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setPoId((String)value);
            }
        }
        ,
        PoDt {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getPoDt();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setPoDt((Timestamp)value);
            }
        }
        ,
        PoType {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getPoType();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setPoType((Integer)value);
            }
        }
        ,
        PoBasis {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getPoBasis();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setPoBasis((Integer)value);
            }
        }
        ,
        SoGrpId {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getSoGrpId();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setSoGrpId((String)value);
            }
        }
        ,
        QuotDocId {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getQuotDocId();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setQuotDocId((String)value);
            }
        }
        ,
        RefPoDocId {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getRefPoDocId();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setRefPoDocId((String)value);
            }
        }
        ,
        EoId {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getEoId();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setEoId((Integer)value);
            }
        }
        ,
        ValidFrmDt {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getValidFrmDt();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setValidFrmDt((Timestamp)value);
            }
        }
        ,
        ValidToDt {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getValidToDt();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setValidToDt((Timestamp)value);
            }
        }
        ,
        SplitPoDocId {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getSplitPoDocId();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setSplitPoDocId((String)value);
            }
        }
        ,
        CurrIdSp {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getCurrIdSp();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setCurrIdSp((Long)value);
            }
        }
        ,
        CurrConvFctr {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getCurrConvFctr();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setCurrConvFctr((BigDecimal)value);
            }
        }
        ,
        TaxRuleFlg {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getTaxRuleFlg();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setTaxRuleFlg((String)value);
            }
        }
        ,
        DiscType {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getDiscType();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setDiscType((String)value);
            }
        }
        ,
        DiscVal {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getDiscVal();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setDiscVal((BigDecimal)value);
            }
        }
        ,
        PoAmtBs {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getPoAmtBs();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setPoAmtBs((BigDecimal)value);
            }
        }
        ,
        PoAmtSp {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getPoAmtSp();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setPoAmtSp((BigDecimal)value);
            }
        }
        ,
        BillingBasis {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getBillingBasis();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setBillingBasis((BigInteger)value);
            }
        }
        ,
        PoStatus {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getPoStatus();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setPoStatus((String)value);
            }
        }
        ,
        TlrncQtyType {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getTlrncQtyType();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setTlrncQtyType((String)value);
            }
        }
        ,
        TlrncQtyVal {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getTlrncQtyVal();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setTlrncQtyVal((BigDecimal)value);
            }
        }
        ,
        TmplDocId {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getTmplDocId();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setTmplDocId((String)value);
            }
        }
        ,
        AuthPoNo {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getAuthPoNo();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setAuthPoNo((String)value);
            }
        }
        ,
        selectPO {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getselectPO();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setselectPO((Boolean)value);
            }
        }
        ,
        ViewSuppLOV1 {
            public Object get(ViewPOQueryRowImpl obj) {
                return obj.getViewSuppLOV1();
            }

            public void put(ViewPOQueryRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(ViewPOQueryRowImpl object);

        public abstract void put(ViewPOQueryRowImpl object, Object value);

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int DOCDT = AttributesEnum.DocDt.index();
    public static final int POID = AttributesEnum.PoId.index();
    public static final int PODT = AttributesEnum.PoDt.index();
    public static final int POTYPE = AttributesEnum.PoType.index();
    public static final int POBASIS = AttributesEnum.PoBasis.index();
    public static final int SOGRPID = AttributesEnum.SoGrpId.index();
    public static final int QUOTDOCID = AttributesEnum.QuotDocId.index();
    public static final int REFPODOCID = AttributesEnum.RefPoDocId.index();
    public static final int EOID = AttributesEnum.EoId.index();
    public static final int VALIDFRMDT = AttributesEnum.ValidFrmDt.index();
    public static final int VALIDTODT = AttributesEnum.ValidToDt.index();
    public static final int SPLITPODOCID = AttributesEnum.SplitPoDocId.index();
    public static final int CURRIDSP = AttributesEnum.CurrIdSp.index();
    public static final int CURRCONVFCTR = AttributesEnum.CurrConvFctr.index();
    public static final int TAXRULEFLG = AttributesEnum.TaxRuleFlg.index();
    public static final int DISCTYPE = AttributesEnum.DiscType.index();
    public static final int DISCVAL = AttributesEnum.DiscVal.index();
    public static final int POAMTBS = AttributesEnum.PoAmtBs.index();
    public static final int POAMTSP = AttributesEnum.PoAmtSp.index();
    public static final int BILLINGBASIS = AttributesEnum.BillingBasis.index();
    public static final int POSTATUS = AttributesEnum.PoStatus.index();
    public static final int TLRNCQTYTYPE = AttributesEnum.TlrncQtyType.index();
    public static final int TLRNCQTYVAL = AttributesEnum.TlrncQtyVal.index();
    public static final int TMPLDOCID = AttributesEnum.TmplDocId.index();
    public static final int AUTHPONO = AttributesEnum.AuthPoNo.index();
    public static final int SELECTPO = AttributesEnum.selectPO.index();
    public static final int VIEWSUPPLOV1 = AttributesEnum.ViewSuppLOV1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public ViewPOQueryRowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute SlocId.
     * @return the SlocId
     */
    public Integer getSlocId() {
        return (Integer) getAttributeInternal(SLOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SlocId.
     * @param value value to set the  SlocId
     */
    public void setSlocId(Integer value) {
        setAttributeInternal(SLOCID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CldId.
     * @return the CldId
     */
    public String getCldId() {
        return (String) getAttributeInternal(CLDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CldId.
     * @param value value to set the  CldId
     */
    public void setCldId(String value) {
        setAttributeInternal(CLDID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute OrgId.
     * @return the OrgId
     */
    public String getOrgId() {
        return (String) getAttributeInternal(ORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute OrgId.
     * @param value value to set the  OrgId
     */
    public void setOrgId(String value) {
        setAttributeInternal(ORGID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute DocId.
     * @return the DocId
     */
    public String getDocId() {
        return (String) getAttributeInternal(DOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute DocId.
     * @param value value to set the  DocId
     */
    public void setDocId(String value) {
        setAttributeInternal(DOCID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute DocDt.
     * @return the DocDt
     */
    public Date getDocDt() {
        return (Date) getAttributeInternal(DOCDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute DocDt.
     * @param value value to set the  DocDt
     */
    public void setDocDt(Date value) {
        setAttributeInternal(DOCDT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute PoId.
     * @return the PoId
     */
    public String getPoId() {
        return (String) getAttributeInternal(POID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute PoId.
     * @param value value to set the  PoId
     */
    public void setPoId(String value) {
        setAttributeInternal(POID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute PoDt.
     * @return the PoDt
     */
    public Timestamp getPoDt() {
        return (Timestamp) getAttributeInternal(PODT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute PoDt.
     * @param value value to set the  PoDt
     */
    public void setPoDt(Timestamp value) {
        setAttributeInternal(PODT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute PoType.
     * @return the PoType
     */
    public Integer getPoType() {
        return (Integer) getAttributeInternal(POTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute PoType.
     * @param value value to set the  PoType
     */
    public void setPoType(Integer value) {
        setAttributeInternal(POTYPE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute PoBasis.
     * @return the PoBasis
     */
    public Integer getPoBasis() {
        return (Integer) getAttributeInternal(POBASIS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute PoBasis.
     * @param value value to set the  PoBasis
     */
    public void setPoBasis(Integer value) {
        setAttributeInternal(POBASIS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SoGrpId.
     * @return the SoGrpId
     */
    public String getSoGrpId() {
        return (String) getAttributeInternal(SOGRPID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SoGrpId.
     * @param value value to set the  SoGrpId
     */
    public void setSoGrpId(String value) {
        setAttributeInternal(SOGRPID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute QuotDocId.
     * @return the QuotDocId
     */
    public String getQuotDocId() {
        return (String) getAttributeInternal(QUOTDOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute QuotDocId.
     * @param value value to set the  QuotDocId
     */
    public void setQuotDocId(String value) {
        setAttributeInternal(QUOTDOCID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute RefPoDocId.
     * @return the RefPoDocId
     */
    public String getRefPoDocId() {
        return (String) getAttributeInternal(REFPODOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute RefPoDocId.
     * @param value value to set the  RefPoDocId
     */
    public void setRefPoDocId(String value) {
        setAttributeInternal(REFPODOCID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute EoId.
     * @return the EoId
     */
    public Integer getEoId() {
        return (Integer) getAttributeInternal(EOID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute EoId.
     * @param value value to set the  EoId
     */
    public void setEoId(Integer value) {
        setAttributeInternal(EOID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ValidFrmDt.
     * @return the ValidFrmDt
     */
    public Timestamp getValidFrmDt() {
        return (Timestamp) getAttributeInternal(VALIDFRMDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ValidFrmDt.
     * @param value value to set the  ValidFrmDt
     */
    public void setValidFrmDt(Timestamp value) {
        setAttributeInternal(VALIDFRMDT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ValidToDt.
     * @return the ValidToDt
     */
    public Timestamp getValidToDt() {
        return (Timestamp) getAttributeInternal(VALIDTODT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ValidToDt.
     * @param value value to set the  ValidToDt
     */
    public void setValidToDt(Timestamp value) {
        setAttributeInternal(VALIDTODT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SplitPoDocId.
     * @return the SplitPoDocId
     */
    public String getSplitPoDocId() {
        return (String) getAttributeInternal(SPLITPODOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SplitPoDocId.
     * @param value value to set the  SplitPoDocId
     */
    public void setSplitPoDocId(String value) {
        setAttributeInternal(SPLITPODOCID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CurrIdSp.
     * @return the CurrIdSp
     */
    public Long getCurrIdSp() {
        return (Long) getAttributeInternal(CURRIDSP);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CurrIdSp.
     * @param value value to set the  CurrIdSp
     */
    public void setCurrIdSp(Long value) {
        setAttributeInternal(CURRIDSP, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CurrConvFctr.
     * @return the CurrConvFctr
     */
    public BigDecimal getCurrConvFctr() {
        return (BigDecimal) getAttributeInternal(CURRCONVFCTR);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CurrConvFctr.
     * @param value value to set the  CurrConvFctr
     */
    public void setCurrConvFctr(BigDecimal value) {
        setAttributeInternal(CURRCONVFCTR, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TaxRuleFlg.
     * @return the TaxRuleFlg
     */
    public String getTaxRuleFlg() {
        return (String) getAttributeInternal(TAXRULEFLG);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TaxRuleFlg.
     * @param value value to set the  TaxRuleFlg
     */
    public void setTaxRuleFlg(String value) {
        setAttributeInternal(TAXRULEFLG, value);
    }

    /**
     * Gets the attribute value for the calculated attribute DiscType.
     * @return the DiscType
     */
    public String getDiscType() {
        return (String) getAttributeInternal(DISCTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute DiscType.
     * @param value value to set the  DiscType
     */
    public void setDiscType(String value) {
        setAttributeInternal(DISCTYPE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute DiscVal.
     * @return the DiscVal
     */
    public BigDecimal getDiscVal() {
        return (BigDecimal) getAttributeInternal(DISCVAL);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute DiscVal.
     * @param value value to set the  DiscVal
     */
    public void setDiscVal(BigDecimal value) {
        setAttributeInternal(DISCVAL, value);
    }

    /**
     * Gets the attribute value for the calculated attribute PoAmtBs.
     * @return the PoAmtBs
     */
    public BigDecimal getPoAmtBs() {
        return (BigDecimal) getAttributeInternal(POAMTBS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute PoAmtBs.
     * @param value value to set the  PoAmtBs
     */
    public void setPoAmtBs(BigDecimal value) {
        setAttributeInternal(POAMTBS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute PoAmtSp.
     * @return the PoAmtSp
     */
    public BigDecimal getPoAmtSp() {
        return (BigDecimal) getAttributeInternal(POAMTSP);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute PoAmtSp.
     * @param value value to set the  PoAmtSp
     */
    public void setPoAmtSp(BigDecimal value) {
        setAttributeInternal(POAMTSP, value);
    }

    /**
     * Gets the attribute value for the calculated attribute BillingBasis.
     * @return the BillingBasis
     */
    public BigInteger getBillingBasis() {
        return (BigInteger) getAttributeInternal(BILLINGBASIS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute BillingBasis.
     * @param value value to set the  BillingBasis
     */
    public void setBillingBasis(BigInteger value) {
        setAttributeInternal(BILLINGBASIS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute PoStatus.
     * @return the PoStatus
     */
    public String getPoStatus() {
        return (String) getAttributeInternal(POSTATUS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute PoStatus.
     * @param value value to set the  PoStatus
     */
    public void setPoStatus(String value) {
        setAttributeInternal(POSTATUS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TlrncQtyType.
     * @return the TlrncQtyType
     */
    public String getTlrncQtyType() {
        return (String) getAttributeInternal(TLRNCQTYTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TlrncQtyType.
     * @param value value to set the  TlrncQtyType
     */
    public void setTlrncQtyType(String value) {
        setAttributeInternal(TLRNCQTYTYPE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TlrncQtyVal.
     * @return the TlrncQtyVal
     */
    public BigDecimal getTlrncQtyVal() {
        return (BigDecimal) getAttributeInternal(TLRNCQTYVAL);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TlrncQtyVal.
     * @param value value to set the  TlrncQtyVal
     */
    public void setTlrncQtyVal(BigDecimal value) {
        setAttributeInternal(TLRNCQTYVAL, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TmplDocId.
     * @return the TmplDocId
     */
    public String getTmplDocId() {
        return (String) getAttributeInternal(TMPLDOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TmplDocId.
     * @param value value to set the  TmplDocId
     */
    public void setTmplDocId(String value) {
        setAttributeInternal(TMPLDOCID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute AuthPoNo.
     * @return the AuthPoNo
     */
    public String getAuthPoNo() {
        return (String) getAttributeInternal(AUTHPONO);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute AuthPoNo.
     * @param value value to set the  AuthPoNo
     */
    public void setAuthPoNo(String value) {
        setAttributeInternal(AUTHPONO, value);
    }

    /**
     * Gets the attribute value for the calculated attribute selectPO.
     * @return the selectPO
     */
    public Boolean getselectPO() {
        return (Boolean) getAttributeInternal(SELECTPO);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute selectPO.
     * @param value value to set the  selectPO
     */
    public void setselectPO(Boolean value) {
        setAttributeInternal(SELECTPO, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> ViewSuppLOV1.
     */
    public RowSet getViewSuppLOV1() {
        return (RowSet)getAttributeInternal(VIEWSUPPLOV1);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index, AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            return AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].get(this);
        }
        return super.getAttrInvokeAccessor(index, attrDef);
    }

    /**
     * setAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param value the value to assign to the attribute
     * @param attrDef the attribute

     * @throws Exception
     */
    protected void setAttrInvokeAccessor(int index, Object value, AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }
}
