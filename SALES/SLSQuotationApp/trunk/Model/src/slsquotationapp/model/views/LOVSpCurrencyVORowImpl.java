package slsquotationapp.model.views;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Aug 28 12:04:41 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LOVSpCurrencyVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CurrId {
            public Object get(LOVSpCurrencyVORowImpl obj) {
                return obj.getCurrId();
            }

            public void put(LOVSpCurrencyVORowImpl obj, Object value) {
                obj.setCurrId((Integer)value);
            }
        }
        ,
        CurrDesc {
            public Object get(LOVSpCurrencyVORowImpl obj) {
                return obj.getCurrDesc();
            }

            public void put(LOVSpCurrencyVORowImpl obj, Object value) {
                obj.setCurrDesc((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(LOVSpCurrencyVORowImpl object);

        public abstract void put(LOVSpCurrencyVORowImpl object, Object value);

        public int index() {
            return LOVSpCurrencyVORowImpl.AttributesEnum.firstIndex() + ordinal();
        }

        public static int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return LOVSpCurrencyVORowImpl.AttributesEnum.firstIndex() + LOVSpCurrencyVORowImpl.AttributesEnum.staticValues().length;
        }

        public static AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = LOVSpCurrencyVORowImpl.AttributesEnum.values();
            }
            return vals;
        }
    }

    public static final int CURRID = AttributesEnum.CurrId.index();
    public static final int CURRDESC = AttributesEnum.CurrDesc.index();

    /**
     * This is the default constructor (do not remove).
     */
    public LOVSpCurrencyVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute CurrId.
     * @return the CurrId
     */
    public Integer getCurrId() {
        return (Integer) getAttributeInternal(CURRID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CurrId.
     * @param value value to set the  CurrId
     */
    public void setCurrId(Integer value) {
        setAttributeInternal(CURRID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CurrDesc.
     * @return the CurrDesc
     */
    public String getCurrDesc() {
        return (String) getAttributeInternal(CURRDESC);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CurrDesc.
     * @param value value to set the  CurrDesc
     */
    public void setCurrDesc(String value) {
        setAttributeInternal(CURRDESC, value);
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
