package mmreceiptmtl.model.entities;

import java.math.BigDecimal;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Apr 28 15:05:21 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MmMtlRcptLotEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CldId {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getCldId();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setCldId((String) value);
            }
        }
        ,
        SlocId {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getSlocId();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setSlocId((Integer) value);
            }
        }
        ,
        OrgId {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getOrgId();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setOrgId((String) value);
            }
        }
        ,
        WhId {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getWhId();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setWhId((String) value);
            }
        }
        ,
        DocId {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getDocId();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setDocId((String) value);
            }
        }
        ,
        DocIdSrc {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getDocIdSrc();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setDocIdSrc((String) value);
            }
        }
        ,
        DlvSchdlNo {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getDlvSchdlNo();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setDlvSchdlNo((Integer) value);
            }
        }
        ,
        LotId {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getLotId();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setLotId((String) value);
            }
        }
        ,
        ItmId {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getItmId();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setItmId((String) value);
            }
        }
        ,
        ItmUom {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getItmUom();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setItmUom((String) value);
            }
        }
        ,
        DocDtSrc {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getDocDtSrc();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setDocDtSrc((Timestamp) value);
            }
        }
        ,
        LotQty {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getLotQty();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setLotQty((Number) value);
            }
        }
        ,
        LotPrice {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getLotPrice();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setLotPrice((Number) value);
            }
        }
        ,
        WarrantyDt {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getWarrantyDt();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setWarrantyDt((Timestamp) value);
            }
        }
        ,
        ExpiryDt {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getExpiryDt();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setExpiryDt((Timestamp) value);
            }
        }
        ,
        ItmUomBs {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getItmUomBs();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setItmUomBs((String) value);
            }
        }
        ,
        LotQtyBs {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getLotQtyBs();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setLotQtyBs((Number) value);
            }
        }
        ,
        LotQtySp {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getLotQtySp();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setLotQtySp((Number) value);
            }
        }
        ,
        RejQtySp {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getRejQtySp();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setRejQtySp((Number) value);
            }
        }
        ,
        RejQtyBs {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getRejQtyBs();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setRejQtyBs((Number) value);
            }
        }
        ,
        RwkQtySp {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getRwkQtySp();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setRwkQtySp((Number) value);
            }
        }
        ,
        RwkQtyBs {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getRwkQtyBs();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setRwkQtyBs((Number) value);
            }
        }
        ,
        MfgDt {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getMfgDt();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setMfgDt((Timestamp) value);
            }
        }
        ,
        MmMtlRcptItm {
            public Object get(MmMtlRcptLotEOImpl obj) {
                return obj.getMmMtlRcptItm();
            }

            public void put(MmMtlRcptLotEOImpl obj, Object value) {
                obj.setMmMtlRcptItm((MmMtlRcptItmEOImpl) value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(MmMtlRcptLotEOImpl object);

        public abstract void put(MmMtlRcptLotEOImpl object, Object value);

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
    public static final int WHID = AttributesEnum.WhId.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int DOCIDSRC = AttributesEnum.DocIdSrc.index();
    public static final int DLVSCHDLNO = AttributesEnum.DlvSchdlNo.index();
    public static final int LOTID = AttributesEnum.LotId.index();
    public static final int ITMID = AttributesEnum.ItmId.index();
    public static final int ITMUOM = AttributesEnum.ItmUom.index();
    public static final int DOCDTSRC = AttributesEnum.DocDtSrc.index();
    public static final int LOTQTY = AttributesEnum.LotQty.index();
    public static final int LOTPRICE = AttributesEnum.LotPrice.index();
    public static final int WARRANTYDT = AttributesEnum.WarrantyDt.index();
    public static final int EXPIRYDT = AttributesEnum.ExpiryDt.index();
    public static final int ITMUOMBS = AttributesEnum.ItmUomBs.index();
    public static final int LOTQTYBS = AttributesEnum.LotQtyBs.index();
    public static final int LOTQTYSP = AttributesEnum.LotQtySp.index();
    public static final int REJQTYSP = AttributesEnum.RejQtySp.index();
    public static final int REJQTYBS = AttributesEnum.RejQtyBs.index();
    public static final int RWKQTYSP = AttributesEnum.RwkQtySp.index();
    public static final int RWKQTYBS = AttributesEnum.RwkQtyBs.index();
    public static final int MFGDT = AttributesEnum.MfgDt.index();
    public static final int MMMTLRCPTITM = AttributesEnum.MmMtlRcptItm.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MmMtlRcptLotEOImpl() {
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("mmreceiptmtl.model.entities.MmMtlRcptLotEO");
    }


    /**
     * Gets the attribute value for CldId, using the alias name CldId.
     * @return the value of CldId
     */
    public String getCldId() {
        return (String)getAttributeInternal(CLDID);
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
        return (Integer)getAttributeInternal(SLOCID);
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
        return (String)getAttributeInternal(ORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for OrgId.
     * @param value value to set the OrgId
     */
    public void setOrgId(String value) {
        setAttributeInternal(ORGID, value);
    }

    /**
     * Gets the attribute value for WhId, using the alias name WhId.
     * @return the value of WhId
     */
    public String getWhId() {
        return (String)getAttributeInternal(WHID);
    }

    /**
     * Sets <code>value</code> as the attribute value for WhId.
     * @param value value to set the WhId
     */
    public void setWhId(String value) {
        setAttributeInternal(WHID, value);
    }

    /**
     * Gets the attribute value for DocId, using the alias name DocId.
     * @return the value of DocId
     */
    public String getDocId() {
        return (String)getAttributeInternal(DOCID);
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
        return (String)getAttributeInternal(DOCIDSRC);
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
        return (Integer)getAttributeInternal(DLVSCHDLNO);
    }

    /**
     * Sets <code>value</code> as the attribute value for DlvSchdlNo.
     * @param value value to set the DlvSchdlNo
     */
    public void setDlvSchdlNo(Integer value) {
        setAttributeInternal(DLVSCHDLNO, value);
    }

    /**
     * Gets the attribute value for LotId, using the alias name LotId.
     * @return the value of LotId
     */
    public String getLotId() {
        return (String)getAttributeInternal(LOTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for LotId.
     * @param value value to set the LotId
     */
    public void setLotId(String value) {
        setAttributeInternal(LOTID, value);
    }

    /**
     * Gets the attribute value for ItmId, using the alias name ItmId.
     * @return the value of ItmId
     */
    public String getItmId() {
        return (String)getAttributeInternal(ITMID);
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
        return (String)getAttributeInternal(ITMUOM);
    }

    /**
     * Sets <code>value</code> as the attribute value for ItmUom.
     * @param value value to set the ItmUom
     */
    public void setItmUom(String value) {
        setAttributeInternal(ITMUOM, value);
    }

    /**
     * Gets the attribute value for DocDtSrc, using the alias name DocDtSrc.
     * @return the value of DocDtSrc
     */
    public Timestamp getDocDtSrc() {
        return (Timestamp)getAttributeInternal(DOCDTSRC);
    }

    /**
     * Sets <code>value</code> as the attribute value for DocDtSrc.
     * @param value value to set the DocDtSrc
     */
    public void setDocDtSrc(Timestamp value) {
        setAttributeInternal(DOCDTSRC, value);
    }

    /**
     * Gets the attribute value for LotQty, using the alias name LotQty.
     * @return the value of LotQty
     */
    public Number getLotQty() {
        return (Number)getAttributeInternal(LOTQTY);
    }

    /**
     * Sets <code>value</code> as the attribute value for LotQty.
     * @param value value to set the LotQty
     */
    public void setLotQty(Number value) {
        setAttributeInternal(LOTQTY, value);
    }

    /**
     * Gets the attribute value for LotPrice, using the alias name LotPrice.
     * @return the value of LotPrice
     */
    public Number getLotPrice() {
        return (Number)getAttributeInternal(LOTPRICE);
    }

    /**
     * Sets <code>value</code> as the attribute value for LotPrice.
     * @param value value to set the LotPrice
     */
    public void setLotPrice(Number value) {
        setAttributeInternal(LOTPRICE, value);
    }

    /**
     * Gets the attribute value for WarrantyDt, using the alias name WarrantyDt.
     * @return the value of WarrantyDt
     */
    public Timestamp getWarrantyDt() {
        return (Timestamp)getAttributeInternal(WARRANTYDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for WarrantyDt.
     * @param value value to set the WarrantyDt
     */
    public void setWarrantyDt(Timestamp value) {
        setAttributeInternal(WARRANTYDT, value);
    }

    /**
     * Gets the attribute value for ExpiryDt, using the alias name ExpiryDt.
     * @return the value of ExpiryDt
     */
    public Timestamp getExpiryDt() {
        return (Timestamp)getAttributeInternal(EXPIRYDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for ExpiryDt.
     * @param value value to set the ExpiryDt
     */
    public void setExpiryDt(Timestamp value) {
        setAttributeInternal(EXPIRYDT, value);
    }

    /**
     * Gets the attribute value for ItmUomBs, using the alias name ItmUomBs.
     * @return the value of ItmUomBs
     */
    public String getItmUomBs() {
        return (String)getAttributeInternal(ITMUOMBS);
    }

    /**
     * Sets <code>value</code> as the attribute value for ItmUomBs.
     * @param value value to set the ItmUomBs
     */
    public void setItmUomBs(String value) {
        setAttributeInternal(ITMUOMBS, value);
    }

    /**
     * Gets the attribute value for LotQtyBs, using the alias name LotQtyBs.
     * @return the value of LotQtyBs
     */
    public Number getLotQtyBs() {
        return (Number)getAttributeInternal(LOTQTYBS);
    }

    /**
     * Sets <code>value</code> as the attribute value for LotQtyBs.
     * @param value value to set the LotQtyBs
     */
    public void setLotQtyBs(Number value) {
        setAttributeInternal(LOTQTYBS, value);
    }

    /**
     * Gets the attribute value for LotQtySp, using the alias name LotQtySp.
     * @return the value of LotQtySp
     */
    public Number getLotQtySp() {
        return (Number)getAttributeInternal(LOTQTYSP);
    }

    /**
     * Sets <code>value</code> as the attribute value for LotQtySp.
     * @param value value to set the LotQtySp
     */
    public void setLotQtySp(Number value) {
        setAttributeInternal(LOTQTYSP, value);
    }

    /**
     * Gets the attribute value for RejQtySp, using the alias name RejQtySp.
     * @return the value of RejQtySp
     */
    public Number getRejQtySp() {
        return (Number) getAttributeInternal(REJQTYSP);
    }

    /**
     * Sets <code>value</code> as the attribute value for RejQtySp.
     * @param value value to set the RejQtySp
     */
    public void setRejQtySp(Number value) {
        setAttributeInternal(REJQTYSP, value);
    }

    /**
     * Gets the attribute value for RejQtyBs, using the alias name RejQtyBs.
     * @return the value of RejQtyBs
     */
    public Number getRejQtyBs() {
        return (Number) getAttributeInternal(REJQTYBS);
    }

    /**
     * Sets <code>value</code> as the attribute value for RejQtyBs.
     * @param value value to set the RejQtyBs
     */
    public void setRejQtyBs(Number value) {
        setAttributeInternal(REJQTYBS, value);
    }

    /**
     * Gets the attribute value for RwkQtySp, using the alias name RwkQtySp.
     * @return the value of RwkQtySp
     */
    public Number getRwkQtySp() {
        return (Number) getAttributeInternal(RWKQTYSP);
    }

    /**
     * Sets <code>value</code> as the attribute value for RwkQtySp.
     * @param value value to set the RwkQtySp
     */
    public void setRwkQtySp(Number value) {
        setAttributeInternal(RWKQTYSP, value);
    }

    /**
     * Gets the attribute value for RwkQtyBs, using the alias name RwkQtyBs.
     * @return the value of RwkQtyBs
     */
    public Number getRwkQtyBs() {
        return (Number) getAttributeInternal(RWKQTYBS);
    }

    /**
     * Sets <code>value</code> as the attribute value for RwkQtyBs.
     * @param value value to set the RwkQtyBs
     */
    public void setRwkQtyBs(Number value) {
        setAttributeInternal(RWKQTYBS, value);
    }

    /**
     * Gets the attribute value for MfgDt, using the alias name MfgDt.
     * @return the value of MfgDt
     */
    public Timestamp getMfgDt() {
        return (Timestamp) getAttributeInternal(MFGDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for MfgDt.
     * @param value value to set the MfgDt
     */
    public void setMfgDt(Timestamp value) {
        setAttributeInternal(MFGDT, value);
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

    /**
     * @return the associated entity MmMtlRcptItmEOImpl.
     */
    public MmMtlRcptItmEOImpl getMmMtlRcptItm() {
        return (MmMtlRcptItmEOImpl)getAttributeInternal(MMMTLRCPTITM);
    }

    /**
     * Sets <code>value</code> as the associated entity MmMtlRcptItmEOImpl.
     */
    public void setMmMtlRcptItm(MmMtlRcptItmEOImpl value) {
        setAttributeInternal(MMMTLRCPTITM, value);
    }


    /**
     * @param cldId key constituent
     * @param slocId key constituent
     * @param orgId key constituent
     * @param whId key constituent
     * @param docId key constituent
     * @param docIdSrc key constituent
     * @param dlvSchdlNo key constituent
     * @param lotId key constituent
     * @param itmId key constituent
     * @param itmUom key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String cldId, Integer slocId, String orgId, String whId, String docId,
                                       String docIdSrc, Integer dlvSchdlNo, String lotId, String itmId, String itmUom) {
        return new Key(new Object[] { cldId, slocId, orgId, whId, docId, docIdSrc, dlvSchdlNo, lotId, itmId, itmUom });
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
