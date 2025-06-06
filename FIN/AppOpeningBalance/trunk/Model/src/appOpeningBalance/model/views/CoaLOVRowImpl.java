package appOpeningBalance.model.views;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sun Jan 05 16:02:17 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CoaLOVRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CoaId {
            public Object get(CoaLOVRowImpl obj) {
                return obj.getCoaId();
            }

            public void put(CoaLOVRowImpl obj, Object value) {
                obj.setCoaId((Integer)value);
            }
        }
        ,
        CoaCogId {
            public Object get(CoaLOVRowImpl obj) {
                return obj.getCoaCogId();
            }

            public void put(CoaLOVRowImpl obj, Object value) {
                obj.setCoaCogId((String)value);
            }
        }
        ,
        CoaNm {
            public Object get(CoaLOVRowImpl obj) {
                return obj.getCoaNm();
            }

            public void put(CoaLOVRowImpl obj, Object value) {
                obj.setCoaNm((String)value);
            }
        }
        ,
        CoaAccId {
            public Object get(CoaLOVRowImpl obj) {
                return obj.getCoaAccId();
            }

            public void put(CoaLOVRowImpl obj, Object value) {
                obj.setCoaAccId((Integer)value);
            }
        }
        ,
        CoaSlocId {
            public Object get(CoaLOVRowImpl obj) {
                return obj.getCoaSlocId();
            }

            public void put(CoaLOVRowImpl obj, Object value) {
                obj.setCoaSlocId((Integer)value);
            }
        }
        ,
        CoaCldId {
            public Object get(CoaLOVRowImpl obj) {
                return obj.getCoaCldId();
            }

            public void put(CoaLOVRowImpl obj, Object value) {
                obj.setCoaCldId((String)value);
            }
        }
        ,
        CoaHoOrgId {
            public Object get(CoaLOVRowImpl obj) {
                return obj.getCoaHoOrgId();
            }

            public void put(CoaLOVRowImpl obj, Object value) {
                obj.setCoaHoOrgId((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(CoaLOVRowImpl object);

        public abstract void put(CoaLOVRowImpl object, Object value);

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


    public static final int COAID = AttributesEnum.CoaId.index();
    public static final int COACOGID = AttributesEnum.CoaCogId.index();
    public static final int COANM = AttributesEnum.CoaNm.index();
    public static final int COAACCID = AttributesEnum.CoaAccId.index();
    public static final int COASLOCID = AttributesEnum.CoaSlocId.index();
    public static final int COACLDID = AttributesEnum.CoaCldId.index();
    public static final int COAHOORGID = AttributesEnum.CoaHoOrgId.index();

    /**
     * This is the default constructor (do not remove).
     */
    public CoaLOVRowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute CoaId.
     * @return the CoaId
     */
    public Integer getCoaId() {
        return (Integer) getAttributeInternal(COAID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CoaId.
     * @param value value to set the  CoaId
     */
    public void setCoaId(Integer value) {
        setAttributeInternal(COAID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CoaCogId.
     * @return the CoaCogId
     */
    public String getCoaCogId() {
        return (String) getAttributeInternal(COACOGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CoaCogId.
     * @param value value to set the  CoaCogId
     */
    public void setCoaCogId(String value) {
        setAttributeInternal(COACOGID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CoaNm.
     * @return the CoaNm
     */
    public String getCoaNm() {
        return (String) getAttributeInternal(COANM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CoaNm.
     * @param value value to set the  CoaNm
     */
    public void setCoaNm(String value) {
        setAttributeInternal(COANM, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CoaAccId.
     * @return the CoaAccId
     */
    public Integer getCoaAccId() {
        return (Integer) getAttributeInternal(COAACCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CoaAccId.
     * @param value value to set the  CoaAccId
     */
    public void setCoaAccId(Integer value) {
        setAttributeInternal(COAACCID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CoaSlocId.
     * @return the CoaSlocId
     */
    public Integer getCoaSlocId() {
        return (Integer) getAttributeInternal(COASLOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CoaSlocId.
     * @param value value to set the  CoaSlocId
     */
    public void setCoaSlocId(Integer value) {
        setAttributeInternal(COASLOCID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CoaCldId.
     * @return the CoaCldId
     */
    public String getCoaCldId() {
        return (String) getAttributeInternal(COACLDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CoaCldId.
     * @param value value to set the  CoaCldId
     */
    public void setCoaCldId(String value) {
        setAttributeInternal(COACLDID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CoaHoOrgId.
     * @return the CoaHoOrgId
     */
    public String getCoaHoOrgId() {
        return (String) getAttributeInternal(COAHOORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CoaHoOrgId.
     * @param value value to set the  CoaHoOrgId
     */
    public void setCoaHoOrgId(String value) {
        setAttributeInternal(COAHOORGID, value);
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
