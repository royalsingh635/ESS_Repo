package tempVoucherList.model.views.template;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.sql.Timestamp;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Jan 28 14:36:43 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class TmplVouVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        TmplCldId {
            public Object get(TmplVouVORowImpl obj) {
                return obj.getTmplCldId();
            }

            public void put(TmplVouVORowImpl obj, Object value) {
                obj.setTmplCldId((String)value);
            }
        }
        ,
        TmplCurrIdSp {
            public Object get(TmplVouVORowImpl obj) {
                return obj.getTmplCurrIdSp();
            }

            public void put(TmplVouVORowImpl obj, Object value) {
                obj.setTmplCurrIdSp((Integer)value);
            }
        }
        ,
        TmplHoOrgId {
            public Object get(TmplVouVORowImpl obj) {
                return obj.getTmplHoOrgId();
            }

            public void put(TmplVouVORowImpl obj, Object value) {
                obj.setTmplHoOrgId((String)value);
            }
        }
        ,
        TmplName {
            public Object get(TmplVouVORowImpl obj) {
                return obj.getTmplName();
            }

            public void put(TmplVouVORowImpl obj, Object value) {
                obj.setTmplName((String)value);
            }
        }
        ,
        TmplVouAmtSp {
            public Object get(TmplVouVORowImpl obj) {
                return obj.getTmplVouAmtSp();
            }

            public void put(TmplVouVORowImpl obj, Object value) {
                obj.setTmplVouAmtSp((BigDecimal)value);
            }
        }
        ,
        TmplVouCc {
            public Object get(TmplVouVORowImpl obj) {
                return obj.getTmplVouCc();
            }

            public void put(TmplVouVORowImpl obj, Object value) {
                obj.setTmplVouCc((BigDecimal)value);
            }
        }
        ,
        TmplVouCoaId {
            public Object get(TmplVouVORowImpl obj) {
                return obj.getTmplVouCoaId();
            }

            public void put(TmplVouVORowImpl obj, Object value) {
                obj.setTmplVouCoaId((Integer)value);
            }
        }
        ,
        TmplVouDesc {
            public Object get(TmplVouVORowImpl obj) {
                return obj.getTmplVouDesc();
            }

            public void put(TmplVouVORowImpl obj, Object value) {
                obj.setTmplVouDesc((String)value);
            }
        }
        ,
        TmplVouDt {
            public Object get(TmplVouVORowImpl obj) {
                return obj.getTmplVouDt();
            }

            public void put(TmplVouVORowImpl obj, Object value) {
                obj.setTmplVouDt((Timestamp)value);
            }
        }
        ,
        TmplVouId {
            public Object get(TmplVouVORowImpl obj) {
                return obj.getTmplVouId();
            }

            public void put(TmplVouVORowImpl obj, Object value) {
                obj.setTmplVouId((String)value);
            }
        }
        ,
        TmplVouOrgId {
            public Object get(TmplVouVORowImpl obj) {
                return obj.getTmplVouOrgId();
            }

            public void put(TmplVouVORowImpl obj, Object value) {
                obj.setTmplVouOrgId((String)value);
            }
        }
        ,
        TmplVouSlocId {
            public Object get(TmplVouVORowImpl obj) {
                return obj.getTmplVouSlocId();
            }

            public void put(TmplVouVORowImpl obj, Object value) {
                obj.setTmplVouSlocId((Integer)value);
            }
        }
        ,
        TmplVouTaxTot {
            public Object get(TmplVouVORowImpl obj) {
                return obj.getTmplVouTaxTot();
            }

            public void put(TmplVouVORowImpl obj, Object value) {
                obj.setTmplVouTaxTot((Integer)value);
            }
        }
        ,
        TmplVouTypeId {
            public Object get(TmplVouVORowImpl obj) {
                return obj.getTmplVouTypeId();
            }

            public void put(TmplVouVORowImpl obj, Object value) {
                obj.setTmplVouTypeId((Integer)value);
            }
        }
        ,
        TmplVouSubTypeId {
            public Object get(TmplVouVORowImpl obj) {
                return obj.getTmplVouSubTypeId();
            }

            public void put(TmplVouVORowImpl obj, Object value) {
                obj.setTmplVouSubTypeId((BigInteger)value);
            }
        }
        ,
        TmplTypeLOV {
            public Object get(TmplVouVORowImpl obj) {
                return obj.getTmplTypeLOV();
            }

            public void put(TmplVouVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(TmplVouVORowImpl object);

        public abstract void put(TmplVouVORowImpl object, Object value);

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


    public static final int TMPLCLDID = AttributesEnum.TmplCldId.index();
    public static final int TMPLCURRIDSP = AttributesEnum.TmplCurrIdSp.index();
    public static final int TMPLHOORGID = AttributesEnum.TmplHoOrgId.index();
    public static final int TMPLNAME = AttributesEnum.TmplName.index();
    public static final int TMPLVOUAMTSP = AttributesEnum.TmplVouAmtSp.index();
    public static final int TMPLVOUCC = AttributesEnum.TmplVouCc.index();
    public static final int TMPLVOUCOAID = AttributesEnum.TmplVouCoaId.index();
    public static final int TMPLVOUDESC = AttributesEnum.TmplVouDesc.index();
    public static final int TMPLVOUDT = AttributesEnum.TmplVouDt.index();
    public static final int TMPLVOUID = AttributesEnum.TmplVouId.index();
    public static final int TMPLVOUORGID = AttributesEnum.TmplVouOrgId.index();
    public static final int TMPLVOUSLOCID = AttributesEnum.TmplVouSlocId.index();
    public static final int TMPLVOUTAXTOT = AttributesEnum.TmplVouTaxTot.index();
    public static final int TMPLVOUTYPEID = AttributesEnum.TmplVouTypeId.index();
    public static final int TMPLVOUSUBTYPEID = AttributesEnum.TmplVouSubTypeId.index();
    public static final int TMPLTYPELOV = AttributesEnum.TmplTypeLOV.index();

    /**
     * This is the default constructor (do not remove).
     */
    public TmplVouVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute TmplCldId.
     * @return the TmplCldId
     */
    public String getTmplCldId() {
        return (String)getAttributeInternal(TMPLCLDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TmplCldId.
     * @param value value to set the  TmplCldId
     */
    public void setTmplCldId(String value) {
        setAttributeInternal(TMPLCLDID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TmplCurrIdSp.
     * @return the TmplCurrIdSp
     */
    public Integer getTmplCurrIdSp() {
        return (Integer)getAttributeInternal(TMPLCURRIDSP);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TmplCurrIdSp.
     * @param value value to set the  TmplCurrIdSp
     */
    public void setTmplCurrIdSp(Integer value) {
        setAttributeInternal(TMPLCURRIDSP, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TmplHoOrgId.
     * @return the TmplHoOrgId
     */
    public String getTmplHoOrgId() {
        return (String)getAttributeInternal(TMPLHOORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TmplHoOrgId.
     * @param value value to set the  TmplHoOrgId
     */
    public void setTmplHoOrgId(String value) {
        setAttributeInternal(TMPLHOORGID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TmplName.
     * @return the TmplName
     */
    public String getTmplName() {
        return (String)getAttributeInternal(TMPLNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TmplName.
     * @param value value to set the  TmplName
     */
    public void setTmplName(String value) {
        setAttributeInternal(TMPLNAME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TmplVouAmtSp.
     * @return the TmplVouAmtSp
     */
    public BigDecimal getTmplVouAmtSp() {
        return (BigDecimal)getAttributeInternal(TMPLVOUAMTSP);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TmplVouAmtSp.
     * @param value value to set the  TmplVouAmtSp
     */
    public void setTmplVouAmtSp(BigDecimal value) {
        setAttributeInternal(TMPLVOUAMTSP, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TmplVouCc.
     * @return the TmplVouCc
     */
    public BigDecimal getTmplVouCc() {
        return (BigDecimal)getAttributeInternal(TMPLVOUCC);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TmplVouCc.
     * @param value value to set the  TmplVouCc
     */
    public void setTmplVouCc(BigDecimal value) {
        setAttributeInternal(TMPLVOUCC, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TmplVouCoaId.
     * @return the TmplVouCoaId
     */
    public Integer getTmplVouCoaId() {
        return (Integer)getAttributeInternal(TMPLVOUCOAID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TmplVouCoaId.
     * @param value value to set the  TmplVouCoaId
     */
    public void setTmplVouCoaId(Integer value) {
        setAttributeInternal(TMPLVOUCOAID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TmplVouDesc.
     * @return the TmplVouDesc
     */
    public String getTmplVouDesc() {
        return (String)getAttributeInternal(TMPLVOUDESC);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TmplVouDesc.
     * @param value value to set the  TmplVouDesc
     */
    public void setTmplVouDesc(String value) {
        setAttributeInternal(TMPLVOUDESC, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TmplVouDt.
     * @return the TmplVouDt
     */
    public Timestamp getTmplVouDt() {
        return (Timestamp)getAttributeInternal(TMPLVOUDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TmplVouDt.
     * @param value value to set the  TmplVouDt
     */
    public void setTmplVouDt(Timestamp value) {
        setAttributeInternal(TMPLVOUDT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TmplVouId.
     * @return the TmplVouId
     */
    public String getTmplVouId() {
        return (String)getAttributeInternal(TMPLVOUID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TmplVouId.
     * @param value value to set the  TmplVouId
     */
    public void setTmplVouId(String value) {
        setAttributeInternal(TMPLVOUID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TmplVouOrgId.
     * @return the TmplVouOrgId
     */
    public String getTmplVouOrgId() {
        return (String)getAttributeInternal(TMPLVOUORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TmplVouOrgId.
     * @param value value to set the  TmplVouOrgId
     */
    public void setTmplVouOrgId(String value) {
        setAttributeInternal(TMPLVOUORGID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TmplVouSlocId.
     * @return the TmplVouSlocId
     */
    public Integer getTmplVouSlocId() {
        return (Integer)getAttributeInternal(TMPLVOUSLOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TmplVouSlocId.
     * @param value value to set the  TmplVouSlocId
     */
    public void setTmplVouSlocId(Integer value) {
        setAttributeInternal(TMPLVOUSLOCID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TmplVouTaxTot.
     * @return the TmplVouTaxTot
     */
    public Integer getTmplVouTaxTot() {
        return (Integer)getAttributeInternal(TMPLVOUTAXTOT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TmplVouTaxTot.
     * @param value value to set the  TmplVouTaxTot
     */
    public void setTmplVouTaxTot(Integer value) {
        setAttributeInternal(TMPLVOUTAXTOT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TmplVouTypeId.
     * @return the TmplVouTypeId
     */
    public Integer getTmplVouTypeId() {
        return (Integer)getAttributeInternal(TMPLVOUTYPEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TmplVouTypeId.
     * @param value value to set the  TmplVouTypeId
     */
    public void setTmplVouTypeId(Integer value) {
        setAttributeInternal(TMPLVOUTYPEID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TmplVouSubTypeId.
     * @return the TmplVouSubTypeId
     */
    public BigInteger getTmplVouSubTypeId() {
        return (BigInteger)getAttributeInternal(TMPLVOUSUBTYPEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TmplVouSubTypeId.
     * @param value value to set the  TmplVouSubTypeId
     */
    public void setTmplVouSubTypeId(BigInteger value) {
        setAttributeInternal(TMPLVOUSUBTYPEID, value);
    }


    /**
     * Gets the view accessor <code>RowSet</code> TmplTypeLOV.
     */
    public RowSet getTmplTypeLOV() {
        return (RowSet)getAttributeInternal(TMPLTYPELOV);
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
