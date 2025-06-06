package batchpayment.model.views;

import java.math.BigDecimal;

import java.sql.Timestamp;

import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Oct 01 16:03:43 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CurrConRateVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CcCurrId {
            public Object get(CurrConRateVORowImpl obj) {
                return obj.getCcCurrId();
            }

            public void put(CurrConRateVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CcCurrFlg {
            public Object get(CurrConRateVORowImpl obj) {
                return obj.getCcCurrFlg();
            }

            public void put(CurrConRateVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CcCurrIdTxn {
            public Object get(CurrConRateVORowImpl obj) {
                return obj.getCcCurrIdTxn();
            }

            public void put(CurrConRateVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CcEffDate {
            public Object get(CurrConRateVORowImpl obj) {
                return obj.getCcEffDate();
            }

            public void put(CurrConRateVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CcBuy {
            public Object get(CurrConRateVORowImpl obj) {
                return obj.getCcBuy();
            }

            public void put(CurrConRateVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CcSell {
            public Object get(CurrConRateVORowImpl obj) {
                return obj.getCcSell();
            }

            public void put(CurrConRateVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CcBuyRev {
            public Object get(CurrConRateVORowImpl obj) {
                return obj.getCcBuyRev();
            }

            public void put(CurrConRateVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CcSellRev {
            public Object get(CurrConRateVORowImpl obj) {
                return obj.getCcSellRev();
            }

            public void put(CurrConRateVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(CurrConRateVORowImpl object);

        public abstract void put(CurrConRateVORowImpl object, Object value);

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


    public static final int CCCURRID = AttributesEnum.CcCurrId.index();
    public static final int CCCURRFLG = AttributesEnum.CcCurrFlg.index();
    public static final int CCCURRIDTXN = AttributesEnum.CcCurrIdTxn.index();
    public static final int CCEFFDATE = AttributesEnum.CcEffDate.index();
    public static final int CCBUY = AttributesEnum.CcBuy.index();
    public static final int CCSELL = AttributesEnum.CcSell.index();
    public static final int CCBUYREV = AttributesEnum.CcBuyRev.index();
    public static final int CCSELLREV = AttributesEnum.CcSellRev.index();

    /**
     * This is the default constructor (do not remove).
     */
    public CurrConRateVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute CcCurrId.
     * @return the CcCurrId
     */
    public Integer getCcCurrId() {
        return (Integer) getAttributeInternal(CCCURRID);
    }


    /**
     * Gets the attribute value for the calculated attribute CcCurrFlg.
     * @return the CcCurrFlg
     */
    public String getCcCurrFlg() {
        return (String) getAttributeInternal(CCCURRFLG);
    }


    /**
     * Gets the attribute value for the calculated attribute CcCurrIdTxn.
     * @return the CcCurrIdTxn
     */
    public Integer getCcCurrIdTxn() {
        return (Integer) getAttributeInternal(CCCURRIDTXN);
    }


    /**
     * Gets the attribute value for the calculated attribute CcEffDate.
     * @return the CcEffDate
     */
    public Timestamp getCcEffDate() {
        return (Timestamp) getAttributeInternal(CCEFFDATE);
    }


    /**
     * Gets the attribute value for the calculated attribute CcBuy.
     * @return the CcBuy
     */
    public oracle.jbo.domain.Number getCcBuy() {
        return (oracle.jbo.domain.Number) getAttributeInternal(CCBUY);
    }


    /**
     * Gets the attribute value for the calculated attribute CcSell.
     * @return the CcSell
     */
    public Number getCcSell() {
        return (Number) getAttributeInternal(CCSELL);
    }


    /**
     * Gets the attribute value for the calculated attribute CcBuyRev.
     * @return the CcBuyRev
     */
    public Number getCcBuyRev() {
        return (Number) getAttributeInternal(CCBUYREV);
    }


    /**
     * Gets the attribute value for the calculated attribute CcSellRev.
     * @return the CcSellRev
     */
    public Number getCcSellRev() {
        return (Number) getAttributeInternal(CCSELLREV);
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
