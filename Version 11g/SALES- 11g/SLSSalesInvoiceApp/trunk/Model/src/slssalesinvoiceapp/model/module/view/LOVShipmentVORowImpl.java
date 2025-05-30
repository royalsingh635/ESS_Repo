package slssalesinvoiceapp.model.module.view;

import java.math.BigDecimal;

import java.sql.Timestamp;

import oracle.jbo.domain.Date;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Oct 30 09:48:50 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LOVShipmentVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CldId {
            public Object get(LOVShipmentVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(LOVShipmentVORowImpl obj, Object value) {
                obj.setCldId((String)value);
            }
        }
        ,
        SlocId {
            public Object get(LOVShipmentVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(LOVShipmentVORowImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        OrgId {
            public Object get(LOVShipmentVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(LOVShipmentVORowImpl obj, Object value) {
                obj.setOrgId((String)value);
            }
        }
        ,
        HoOrgId {
            public Object get(LOVShipmentVORowImpl obj) {
                return obj.getHoOrgId();
            }

            public void put(LOVShipmentVORowImpl obj, Object value) {
                obj.setHoOrgId((String)value);
            }
        }
        ,
        DocId {
            public Object get(LOVShipmentVORowImpl obj) {
                return obj.getDocId();
            }

            public void put(LOVShipmentVORowImpl obj, Object value) {
                obj.setDocId((String)value);
            }
        }
        ,
        DocDt {
            public Object get(LOVShipmentVORowImpl obj) {
                return obj.getDocDt();
            }

            public void put(LOVShipmentVORowImpl obj, Object value) {
                obj.setDocDt((Date)value);
            }
        }
        ,
        EoId {
            public Object get(LOVShipmentVORowImpl obj) {
                return obj.getEoId();
            }

            public void put(LOVShipmentVORowImpl obj, Object value) {
                obj.setEoId((Integer)value);
            }
        }
        ,
        ShipVal {
            public Object get(LOVShipmentVORowImpl obj) {
                return obj.getShipVal();
            }

            public void put(LOVShipmentVORowImpl obj, Object value) {
                obj.setShipVal((BigDecimal)value);
            }
        }
        ,
        DispId {
            public Object get(LOVShipmentVORowImpl obj) {
                return obj.getDispId();
            }

            public void put(LOVShipmentVORowImpl obj, Object value) {
                obj.setDispId((String)value);
            }
        }
        ,
        CurrConvFctr {
            public Object get(LOVShipmentVORowImpl obj) {
                return obj.getCurrConvFctr();
            }

            public void put(LOVShipmentVORowImpl obj, Object value) {
                obj.setCurrConvFctr((BigDecimal)value);
            }
        }
        ,
        FyId {
            public Object get(LOVShipmentVORowImpl obj) {
                return obj.getFyId();
            }

            public void put(LOVShipmentVORowImpl obj, Object value) {
                obj.setFyId((Integer)value);
            }
        }
        ,
        CurrIdSp {
            public Object get(LOVShipmentVORowImpl obj) {
                return obj.getCurrIdSp();
            }

            public void put(LOVShipmentVORowImpl obj, Object value) {
                obj.setCurrIdSp((Integer)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(LOVShipmentVORowImpl object);

        public abstract void put(LOVShipmentVORowImpl object, Object value);

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


    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int DOCDT = AttributesEnum.DocDt.index();
    public static final int EOID = AttributesEnum.EoId.index();
    public static final int SHIPVAL = AttributesEnum.ShipVal.index();
    public static final int DISPID = AttributesEnum.DispId.index();
    public static final int CURRCONVFCTR = AttributesEnum.CurrConvFctr.index();
    public static final int FYID = AttributesEnum.FyId.index();
    public static final int CURRIDSP = AttributesEnum.CurrIdSp.index();

    /**
     * This is the default constructor (do not remove).
     */
    public LOVShipmentVORowImpl() {
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
     * Gets the attribute value for the calculated attribute HoOrgId.
     * @return the HoOrgId
     */
    public String getHoOrgId() {
        return (String) getAttributeInternal(HOORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute HoOrgId.
     * @param value value to set the  HoOrgId
     */
    public void setHoOrgId(String value) {
        setAttributeInternal(HOORGID, value);
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
     * Gets the attribute value for the calculated attribute ShipVal.
     * @return the ShipVal
     */
    public BigDecimal getShipVal() {
        return (BigDecimal) getAttributeInternal(SHIPVAL);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ShipVal.
     * @param value value to set the  ShipVal
     */
    public void setShipVal(BigDecimal value) {
        setAttributeInternal(SHIPVAL, value);
    }

    /**
     * Gets the attribute value for the calculated attribute DispId.
     * @return the DispId
     */
    public String getDispId() {
        return (String) getAttributeInternal(DISPID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute DispId.
     * @param value value to set the  DispId
     */
    public void setDispId(String value) {
        setAttributeInternal(DISPID, value);
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
     * Gets the attribute value for the calculated attribute FyId.
     * @return the FyId
     */
    public Integer getFyId() {
        return (Integer) getAttributeInternal(FYID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute FyId.
     * @param value value to set the  FyId
     */
    public void setFyId(Integer value) {
        setAttributeInternal(FYID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CurrIdSp.
     * @return the CurrIdSp
     */
    public Integer getCurrIdSp() {
        return (Integer) getAttributeInternal(CURRIDSP);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CurrIdSp.
     * @param value value to set the  CurrIdSp
     */
    public void setCurrIdSp(Integer value) {
        setAttributeInternal(CURRIDSP, value);
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
