package scinvoiceapp.model.views;

import java.math.BigDecimal;

import oracle.jbo.domain.NClobDomain;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Jun 20 12:47:55 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ViewRcptItemVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        ItmId,
        ItmUom,
        ItmUomBs,
        UomConvFctr,
        FinalRcptQty,
        FinalRcptQtyBs,
        PurPrice,
        PurPriceBs,
        ItmAmtBs,
        ItmAmtSp,
        TaxableAmtBs,
        TaxableAmtSp,
        TotTaxAmtBs,
        TotTaxAmtSp,
        TaxRuleId,
        OpId,
        OpSrNo;
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


    public static final int ITMID = AttributesEnum.ItmId.index();
    public static final int ITMUOM = AttributesEnum.ItmUom.index();
    public static final int ITMUOMBS = AttributesEnum.ItmUomBs.index();
    public static final int UOMCONVFCTR = AttributesEnum.UomConvFctr.index();
    public static final int FINALRCPTQTY = AttributesEnum.FinalRcptQty.index();
    public static final int FINALRCPTQTYBS = AttributesEnum.FinalRcptQtyBs.index();
    public static final int PURPRICE = AttributesEnum.PurPrice.index();
    public static final int PURPRICEBS = AttributesEnum.PurPriceBs.index();
    public static final int ITMAMTBS = AttributesEnum.ItmAmtBs.index();
    public static final int ITMAMTSP = AttributesEnum.ItmAmtSp.index();
    public static final int TAXABLEAMTBS = AttributesEnum.TaxableAmtBs.index();
    public static final int TAXABLEAMTSP = AttributesEnum.TaxableAmtSp.index();
    public static final int TOTTAXAMTBS = AttributesEnum.TotTaxAmtBs.index();
    public static final int TOTTAXAMTSP = AttributesEnum.TotTaxAmtSp.index();
    public static final int TAXRULEID = AttributesEnum.TaxRuleId.index();
    public static final int OPID = AttributesEnum.OpId.index();
    public static final int OPSRNO = AttributesEnum.OpSrNo.index();

    /**
     * This is the default constructor (do not remove).
     */
    public ViewRcptItemVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute ItmId.
     * @return the ItmId
     */
    public String getItmId() {
        return (String) getAttributeInternal(ITMID);
    }

    /**
     * Gets the attribute value for the calculated attribute ItmUom.
     * @return the ItmUom
     */
    public String getItmUom() {
        return (String) getAttributeInternal(ITMUOM);
    }

    /**
     * Gets the attribute value for the calculated attribute ItmUomBs.
     * @return the ItmUomBs
     */
    public String getItmUomBs() {
        return (String) getAttributeInternal(ITMUOMBS);
    }

    /**
     * Gets the attribute value for the calculated attribute UomConvFctr.
     * @return the UomConvFctr
     */
    public oracle.jbo.domain.Number getUomConvFctr() {
        return (oracle.jbo.domain.Number) getAttributeInternal(UOMCONVFCTR);
    }

    /**
     * Gets the attribute value for the calculated attribute FinalRcptQty.
     * @return the FinalRcptQty
     */
    public Number getFinalRcptQty() {
        return (Number) getAttributeInternal(FINALRCPTQTY);
    }

    /**
     * Gets the attribute value for the calculated attribute FinalRcptQtyBs.
     * @return the FinalRcptQtyBs
     */
    public Number getFinalRcptQtyBs() {
        return (Number) getAttributeInternal(FINALRCPTQTYBS);
    }

    /**
     * Gets the attribute value for the calculated attribute PurPrice.
     * @return the PurPrice
     */
    public Number getPurPrice() {
        return (Number) getAttributeInternal(PURPRICE);
    }

    /**
     * Gets the attribute value for the calculated attribute PurPriceBs.
     * @return the PurPriceBs
     */
    public Number getPurPriceBs() {
        return (Number) getAttributeInternal(PURPRICEBS);
    }

    /**
     * Gets the attribute value for the calculated attribute ItmAmtBs.
     * @return the ItmAmtBs
     */
    public Number getItmAmtBs() {
        return (Number) getAttributeInternal(ITMAMTBS);
    }

    /**
     * Gets the attribute value for the calculated attribute ItmAmtSp.
     * @return the ItmAmtSp
     */
    public Number getItmAmtSp() {
        return (Number) getAttributeInternal(ITMAMTSP);
    }

    /**
     * Gets the attribute value for the calculated attribute TaxableAmtBs.
     * @return the TaxableAmtBs
     */
    public Number getTaxableAmtBs() {
        return (Number) getAttributeInternal(TAXABLEAMTBS);
    }

    /**
     * Gets the attribute value for the calculated attribute TaxableAmtSp.
     * @return the TaxableAmtSp
     */
    public Number getTaxableAmtSp() {
        return (Number) getAttributeInternal(TAXABLEAMTSP);
    }

    /**
     * Gets the attribute value for the calculated attribute TotTaxAmtBs.
     * @return the TotTaxAmtBs
     */
    public Number getTotTaxAmtBs() {
        return (Number) getAttributeInternal(TOTTAXAMTBS);
    }

    /**
     * Gets the attribute value for the calculated attribute TotTaxAmtSp.
     * @return the TotTaxAmtSp
     */
    public Number getTotTaxAmtSp() {
        return (Number) getAttributeInternal(TOTTAXAMTSP);
    }

    /**
     * Gets the attribute value for the calculated attribute TaxRuleId.
     * @return the TaxRuleId
     */
    public Integer getTaxRuleId() {
        return (Integer) getAttributeInternal(TAXRULEID);
    }

    /**
     * Gets the attribute value for the calculated attribute OpId.
     * @return the OpId
     */
    public String getOpId() {
        return (String) getAttributeInternal(OPID);
    }

    /**
     * Gets the attribute value for the calculated attribute OpSrNo.
     * @return the OpSrNo
     */
    public Integer getOpSrNo() {
        return (Integer) getAttributeInternal(OPSRNO);
    }
}

