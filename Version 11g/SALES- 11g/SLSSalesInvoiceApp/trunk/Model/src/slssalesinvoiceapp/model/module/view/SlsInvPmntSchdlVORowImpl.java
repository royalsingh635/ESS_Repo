package slssalesinvoiceapp.model.module.view;

import java.math.BigDecimal;

import oracle.jbo.RowSet;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Sep 16 20:15:01 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class SlsInvPmntSchdlVORowImpl extends ViewRowImpl {


    public static final int ENTITY_SLSINVPMNTSCHDLEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        AdvFlg {
            public Object get(SlsInvPmntSchdlVORowImpl obj) {
                return obj.getAdvFlg();
            }

            public void put(SlsInvPmntSchdlVORowImpl obj, Object value) {
                obj.setAdvFlg((String)value);
            }
        }
        ,
        CldId {
            public Object get(SlsInvPmntSchdlVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(SlsInvPmntSchdlVORowImpl obj, Object value) {
                obj.setCldId((String)value);
            }
        }
        ,
        DocId {
            public Object get(SlsInvPmntSchdlVORowImpl obj) {
                return obj.getDocId();
            }

            public void put(SlsInvPmntSchdlVORowImpl obj, Object value) {
                obj.setDocId((String)value);
            }
        }
        ,
        HoOrgId {
            public Object get(SlsInvPmntSchdlVORowImpl obj) {
                return obj.getHoOrgId();
            }

            public void put(SlsInvPmntSchdlVORowImpl obj, Object value) {
                obj.setHoOrgId((String)value);
            }
        }
        ,
        OrgId {
            public Object get(SlsInvPmntSchdlVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(SlsInvPmntSchdlVORowImpl obj, Object value) {
                obj.setOrgId((String)value);
            }
        }
        ,
        PayAmt {
            public Object get(SlsInvPmntSchdlVORowImpl obj) {
                return obj.getPayAmt();
            }

            public void put(SlsInvPmntSchdlVORowImpl obj, Object value) {
                obj.setPayAmt((BigDecimal)value);
            }
        }
        ,
        PayDt {
            public Object get(SlsInvPmntSchdlVORowImpl obj) {
                return obj.getPayDt();
            }

            public void put(SlsInvPmntSchdlVORowImpl obj, Object value) {
                obj.setPayDt((Timestamp)value);
            }
        }
        ,
        PayMode {
            public Object get(SlsInvPmntSchdlVORowImpl obj) {
                return obj.getPayMode();
            }

            public void put(SlsInvPmntSchdlVORowImpl obj, Object value) {
                obj.setPayMode((Integer)value);
            }
        }
        ,
        SlocId {
            public Object get(SlsInvPmntSchdlVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(SlsInvPmntSchdlVORowImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        TotAmt {
            public Object get(SlsInvPmntSchdlVORowImpl obj) {
                return obj.getTotAmt();
            }

            public void put(SlsInvPmntSchdlVORowImpl obj, Object value) {
                obj.setTotAmt((BigDecimal)value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(SlsInvPmntSchdlVORowImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(SlsInvPmntSchdlVORowImpl obj, Object value) {
                obj.setUsrIdCreate((Integer)value);
            }
        }
        ,
        UsrIdCreateDt {
            public Object get(SlsInvPmntSchdlVORowImpl obj) {
                return obj.getUsrIdCreateDt();
            }

            public void put(SlsInvPmntSchdlVORowImpl obj, Object value) {
                obj.setUsrIdCreateDt((Timestamp)value);
            }
        }
        ,
        LOVPaymentModeVO1 {
            public Object get(SlsInvPmntSchdlVORowImpl obj) {
                return obj.getLOVPaymentModeVO1();
            }

            public void put(SlsInvPmntSchdlVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(SlsInvPmntSchdlVORowImpl object);

        public abstract void put(SlsInvPmntSchdlVORowImpl object, Object value);

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


    public static final int ADVFLG = AttributesEnum.AdvFlg.index();
    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int PAYAMT = AttributesEnum.PayAmt.index();
    public static final int PAYDT = AttributesEnum.PayDt.index();
    public static final int PAYMODE = AttributesEnum.PayMode.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int TOTAMT = AttributesEnum.TotAmt.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int LOVPAYMENTMODEVO1 = AttributesEnum.LOVPaymentModeVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public SlsInvPmntSchdlVORowImpl() {
    }

    /**
     * Gets SlsInvPmntSchdlEO entity object.
     * @return the SlsInvPmntSchdlEO
     */
    public EntityImpl getSlsInvPmntSchdlEO() {
        return (EntityImpl)getEntity(ENTITY_SLSINVPMNTSCHDLEO);
    }

    /**
     * Gets the attribute value for ADV_FLG using the alias name AdvFlg.
     * @return the ADV_FLG
     */
    public String getAdvFlg() {
        return (String) getAttributeInternal(ADVFLG);
    }

    /**
     * Sets <code>value</code> as attribute value for ADV_FLG using the alias name AdvFlg.
     * @param value value to set the ADV_FLG
     */
    public void setAdvFlg(String value) {
        setAttributeInternal(ADVFLG, value);
    }

    /**
     * Gets the attribute value for CLD_ID using the alias name CldId.
     * @return the CLD_ID
     */
    public String getCldId() {
        return (String) getAttributeInternal(CLDID);
    }

    /**
     * Sets <code>value</code> as attribute value for CLD_ID using the alias name CldId.
     * @param value value to set the CLD_ID
     */
    public void setCldId(String value) {
        setAttributeInternal(CLDID, value);
    }

    /**
     * Gets the attribute value for DOC_ID using the alias name DocId.
     * @return the DOC_ID
     */
    public String getDocId() {
        return (String) getAttributeInternal(DOCID);
    }

    /**
     * Sets <code>value</code> as attribute value for DOC_ID using the alias name DocId.
     * @param value value to set the DOC_ID
     */
    public void setDocId(String value) {
        setAttributeInternal(DOCID, value);
    }

    /**
     * Gets the attribute value for HO_ORG_ID using the alias name HoOrgId.
     * @return the HO_ORG_ID
     */
    public String getHoOrgId() {
        return (String) getAttributeInternal(HOORGID);
    }

    /**
     * Sets <code>value</code> as attribute value for HO_ORG_ID using the alias name HoOrgId.
     * @param value value to set the HO_ORG_ID
     */
    public void setHoOrgId(String value) {
        setAttributeInternal(HOORGID, value);
    }

    /**
     * Gets the attribute value for ORG_ID using the alias name OrgId.
     * @return the ORG_ID
     */
    public String getOrgId() {
        return (String) getAttributeInternal(ORGID);
    }

    /**
     * Sets <code>value</code> as attribute value for ORG_ID using the alias name OrgId.
     * @param value value to set the ORG_ID
     */
    public void setOrgId(String value) {
        setAttributeInternal(ORGID, value);
    }

    /**
     * Gets the attribute value for PAY_AMT using the alias name PayAmt.
     * @return the PAY_AMT
     */
    public BigDecimal getPayAmt() {
        return (BigDecimal) getAttributeInternal(PAYAMT);
    }

    /**
     * Sets <code>value</code> as attribute value for PAY_AMT using the alias name PayAmt.
     * @param value value to set the PAY_AMT
     */
    public void setPayAmt(BigDecimal value) {
        setAttributeInternal(PAYAMT, value);
    }

    /**
     * Gets the attribute value for PAY_DT using the alias name PayDt.
     * @return the PAY_DT
     */
    public Timestamp getPayDt() {
        return (Timestamp) getAttributeInternal(PAYDT);
    }

    /**
     * Sets <code>value</code> as attribute value for PAY_DT using the alias name PayDt.
     * @param value value to set the PAY_DT
     */
    public void setPayDt(Timestamp value) {
        setAttributeInternal(PAYDT, value);
    }

    /**
     * Gets the attribute value for PAY_MODE using the alias name PayMode.
     * @return the PAY_MODE
     */
    public Integer getPayMode() {
        return (Integer) getAttributeInternal(PAYMODE);
    }

    /**
     * Sets <code>value</code> as attribute value for PAY_MODE using the alias name PayMode.
     * @param value value to set the PAY_MODE
     */
    public void setPayMode(Integer value) {
        setAttributeInternal(PAYMODE, value);
    }

    /**
     * Gets the attribute value for SLOC_ID using the alias name SlocId.
     * @return the SLOC_ID
     */
    public Integer getSlocId() {
        return (Integer) getAttributeInternal(SLOCID);
    }

    /**
     * Sets <code>value</code> as attribute value for SLOC_ID using the alias name SlocId.
     * @param value value to set the SLOC_ID
     */
    public void setSlocId(Integer value) {
        setAttributeInternal(SLOCID, value);
    }

    /**
     * Gets the attribute value for TOT_AMT using the alias name TotAmt.
     * @return the TOT_AMT
     */
    public BigDecimal getTotAmt() {
        return (BigDecimal) getAttributeInternal(TOTAMT);
    }

    /**
     * Sets <code>value</code> as attribute value for TOT_AMT using the alias name TotAmt.
     * @param value value to set the TOT_AMT
     */
    public void setTotAmt(BigDecimal value) {
        setAttributeInternal(TOTAMT, value);
    }

    /**
     * Gets the attribute value for USR_ID_CREATE using the alias name UsrIdCreate.
     * @return the USR_ID_CREATE
     */
    public Integer getUsrIdCreate() {
        return (Integer) getAttributeInternal(USRIDCREATE);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_CREATE using the alias name UsrIdCreate.
     * @param value value to set the USR_ID_CREATE
     */
    public void setUsrIdCreate(Integer value) {
        setAttributeInternal(USRIDCREATE, value);
    }

    /**
     * Gets the attribute value for USR_ID_CREATE_DT using the alias name UsrIdCreateDt.
     * @return the USR_ID_CREATE_DT
     */
    public Timestamp getUsrIdCreateDt() {
        return (Timestamp) getAttributeInternal(USRIDCREATEDT);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_CREATE_DT using the alias name UsrIdCreateDt.
     * @param value value to set the USR_ID_CREATE_DT
     */
    public void setUsrIdCreateDt(Timestamp value) {
        setAttributeInternal(USRIDCREATEDT, value);
    }


    /**
     * Gets the view accessor <code>RowSet</code> LOVPaymentModeVO1.
     */
    public RowSet getLOVPaymentModeVO1() {
        return (RowSet)getAttributeInternal(LOVPAYMENTMODEVO1);
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
