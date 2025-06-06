package appcntrysetup.model.views;

import appcntrysetup.model.entitiy.AppCntryCurrEOImpl;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Oct 15 18:20:14 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppCntryCurrVORowImpl extends ViewRowImpl {
    public static final int ENTITY_APPCNTRYCURREO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CntryBaseCurr {
            public Object get(AppCntryCurrVORowImpl obj) {
                return obj.getCntryBaseCurr();
            }

            public void put(AppCntryCurrVORowImpl obj, Object value) {
                obj.setCntryBaseCurr((String)value);
            }
        }
        ,
        CntryCldId {
            public Object get(AppCntryCurrVORowImpl obj) {
                return obj.getCntryCldId();
            }

            public void put(AppCntryCurrVORowImpl obj, Object value) {
                obj.setCntryCldId((String)value);
            }
        }
        ,
        CntryId {
            public Object get(AppCntryCurrVORowImpl obj) {
                return obj.getCntryId();
            }

            public void put(AppCntryCurrVORowImpl obj, Object value) {
                obj.setCntryId((Integer)value);
            }
        }
        ,
        CurrActvForCntry {
            public Object get(AppCntryCurrVORowImpl obj) {
                return obj.getCurrActvForCntry();
            }

            public void put(AppCntryCurrVORowImpl obj, Object value) {
                obj.setCurrActvForCntry((String)value);
            }
        }
        ,
        CurrId {
            public Object get(AppCntryCurrVORowImpl obj) {
                return obj.getCurrId();
            }

            public void put(AppCntryCurrVORowImpl obj, Object value) {
                obj.setCurrId((Integer)value);
            }
        }
        ,
        SlocId {
            public Object get(AppCntryCurrVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(AppCntryCurrVORowImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(AppCntryCurrVORowImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(AppCntryCurrVORowImpl obj, Object value) {
                obj.setUsrIdCreate((Integer)value);
            }
        }
        ,
        UsrIdCreateDt {
            public Object get(AppCntryCurrVORowImpl obj) {
                return obj.getUsrIdCreateDt();
            }

            public void put(AppCntryCurrVORowImpl obj, Object value) {
                obj.setUsrIdCreateDt((Timestamp)value);
            }
        }
        ,
        UsrIdMod {
            public Object get(AppCntryCurrVORowImpl obj) {
                return obj.getUsrIdMod();
            }

            public void put(AppCntryCurrVORowImpl obj, Object value) {
                obj.setUsrIdMod((Integer)value);
            }
        }
        ,
        UsrIdModDt {
            public Object get(AppCntryCurrVORowImpl obj) {
                return obj.getUsrIdModDt();
            }

            public void put(AppCntryCurrVORowImpl obj, Object value) {
                obj.setUsrIdModDt((Timestamp)value);
            }
        }
        ,
        cntryIdTrans {
            public Object get(AppCntryCurrVORowImpl obj) {
                return obj.getcntryIdTrans();
            }

            public void put(AppCntryCurrVORowImpl obj, Object value) {
                obj.setcntryIdTrans((String)value);
            }
        }
        ,
        LOVAppCntryCurrVO1 {
            public Object get(AppCntryCurrVORowImpl obj) {
                return obj.getLOVAppCntryCurrVO1();
            }

            public void put(AppCntryCurrVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(AppCntryCurrVORowImpl object);

        public abstract void put(AppCntryCurrVORowImpl object, Object value);

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
    public static final int CNTRYBASECURR = AttributesEnum.CntryBaseCurr.index();
    public static final int CNTRYCLDID = AttributesEnum.CntryCldId.index();
    public static final int CNTRYID = AttributesEnum.CntryId.index();
    public static final int CURRACTVFORCNTRY = AttributesEnum.CurrActvForCntry.index();
    public static final int CURRID = AttributesEnum.CurrId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int CNTRYIDTRANS = AttributesEnum.cntryIdTrans.index();
    public static final int LOVAPPCNTRYCURRVO1 = AttributesEnum.LOVAppCntryCurrVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public AppCntryCurrVORowImpl() {
    }

    /**
     * Gets AppCntryCurrEO entity object.
     * @return the AppCntryCurrEO
     */
    public AppCntryCurrEOImpl getAppCntryCurrEO() {
        return (AppCntryCurrEOImpl)getEntity(ENTITY_APPCNTRYCURREO);
    }

    /**
     * Gets the attribute value for CNTRY_BASE_CURR using the alias name CntryBaseCurr.
     * @return the CNTRY_BASE_CURR
     */
    public String getCntryBaseCurr() {
        return (String) getAttributeInternal(CNTRYBASECURR);
    }

    /**
     * Sets <code>value</code> as attribute value for CNTRY_BASE_CURR using the alias name CntryBaseCurr.
     * @param value value to set the CNTRY_BASE_CURR
     */
    public void setCntryBaseCurr(String value) {
        setAttributeInternal(CNTRYBASECURR, value);
    }

    /**
     * Gets the attribute value for CNTRY_CLD_ID using the alias name CntryCldId.
     * @return the CNTRY_CLD_ID
     */
    public String getCntryCldId() {
        return (String) getAttributeInternal(CNTRYCLDID);
    }

    /**
     * Sets <code>value</code> as attribute value for CNTRY_CLD_ID using the alias name CntryCldId.
     * @param value value to set the CNTRY_CLD_ID
     */
    public void setCntryCldId(String value) {
        setAttributeInternal(CNTRYCLDID, value);
    }

    /**
     * Gets the attribute value for CNTRY_ID using the alias name CntryId.
     * @return the CNTRY_ID
     */
    public Integer getCntryId() {
        return (Integer) getAttributeInternal(CNTRYID);
    }

    /**
     * Sets <code>value</code> as attribute value for CNTRY_ID using the alias name CntryId.
     * @param value value to set the CNTRY_ID
     */
    public void setCntryId(Integer value) {
        setAttributeInternal(CNTRYID, value);
    }

    /**
     * Gets the attribute value for CURR_ACTV_FOR_CNTRY using the alias name CurrActvForCntry.
     * @return the CURR_ACTV_FOR_CNTRY
     */
    public String getCurrActvForCntry() {
        return (String) getAttributeInternal(CURRACTVFORCNTRY);
    }

    /**
     * Sets <code>value</code> as attribute value for CURR_ACTV_FOR_CNTRY using the alias name CurrActvForCntry.
     * @param value value to set the CURR_ACTV_FOR_CNTRY
     */
    public void setCurrActvForCntry(String value) {
        setAttributeInternal(CURRACTVFORCNTRY, value);
    }

    /**
     * Gets the attribute value for CURR_ID using the alias name CurrId.
     * @return the CURR_ID
     */
    public Integer getCurrId() {
        return (Integer) getAttributeInternal(CURRID);
    }

    /**
     * Sets <code>value</code> as attribute value for CURR_ID using the alias name CurrId.
     * @param value value to set the CURR_ID
     */
    public void setCurrId(Integer value) {
        setAttributeInternal(CURRID, value);
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
     * Gets the attribute value for USR_ID_MOD using the alias name UsrIdMod.
     * @return the USR_ID_MOD
     */
    public Integer getUsrIdMod() {
        return (Integer) getAttributeInternal(USRIDMOD);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_MOD using the alias name UsrIdMod.
     * @param value value to set the USR_ID_MOD
     */
    public void setUsrIdMod(Integer value) {
        setAttributeInternal(USRIDMOD, value);
    }

    /**
     * Gets the attribute value for USR_ID_MOD_DT using the alias name UsrIdModDt.
     * @return the USR_ID_MOD_DT
     */
    public Timestamp getUsrIdModDt() {
        return (Timestamp) getAttributeInternal(USRIDMODDT);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_MOD_DT using the alias name UsrIdModDt.
     * @param value value to set the USR_ID_MOD_DT
     */
    public void setUsrIdModDt(Timestamp value) {
        setAttributeInternal(USRIDMODDT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute cntryIdTrans.
     * @return the cntryIdTrans
     */
    public String getcntryIdTrans() {
        
        RowSet set = this.getLOVAppCntryCurrVO1();
        Row[] filteredRows = set.getFilteredRows("GlblCurrId", this.getCurrId());
        if(filteredRows.length>0)
        {
            return filteredRows[0].getAttribute("GlblCurrDesc").toString();
        }
        
        return (String) "";
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute cntryIdTrans.
     * @param value value to set the  cntryIdTrans
     */
    public void setcntryIdTrans(String value) {
        setAttributeInternal(CNTRYIDTRANS, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVAppCntryCurrVO1.
     */
    public RowSet getLOVAppCntryCurrVO1() {
        return (RowSet)getAttributeInternal(LOVAPPCNTRYCURRVO1);
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
