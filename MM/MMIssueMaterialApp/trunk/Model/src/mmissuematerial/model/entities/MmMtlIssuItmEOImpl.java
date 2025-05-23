package mmissuematerial.model.entities;

import oracle.jbo.Key;
import oracle.jbo.RowIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Aug 06 13:59:05 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MmMtlIssuItmEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getCldId();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setCldId((String) value);
            }
        }
        ,
        SlocId {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getSlocId();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setSlocId((Integer) value);
            }
        }
        ,
        OrgId {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getOrgId();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setOrgId((String) value);
            }
        }
        ,
        WhId {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getWhId();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setWhId((String) value);
            }
        }
        ,
        DocId {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getDocId();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setDocId((String) value);
            }
        }
        ,
        IssuDocType {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getIssuDocType();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setIssuDocType((Integer) value);
            }
        }
        ,
        DocIdSrc {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getDocIdSrc();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setDocIdSrc((String) value);
            }
        }
        ,
        DocDtSrc {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getDocDtSrc();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setDocDtSrc((Timestamp) value);
            }
        }
        ,
        ItmId {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getItmId();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setItmId((String) value);
            }
        }
        ,
        ItmUom {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getItmUom();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setItmUom((String) value);
            }
        }
        ,
        PendQty {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getPendQty();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setPendQty((Number) value);
            }
        }
        ,
        IssuQty {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getIssuQty();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setIssuQty((Number) value);
            }
        }
        ,
        ItmUomBs {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getItmUomBs();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setItmUomBs((String) value);
            }
        }
        ,
        IssuQtyBs {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getIssuQtyBs();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setIssuQtyBs((Number) value);
            }
        }
        ,
        FocFlg {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getFocFlg();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setFocFlg((String) value);
            }
        }
        ,
        UomConvFctr {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getUomConvFctr();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setUomConvFctr((Number) value);
            }
        }
        ,
        VehNo {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getVehNo();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setVehNo((String) value);
            }
        }
        ,
        MetrRedng {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getMetrRedng();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setMetrRedng((String) value);
            }
        }
        ,
        CcId {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getCcId();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setCcId((String) value);
            }
        }
        ,
        MmMtlIssuSrc {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getMmMtlIssuSrc();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setMmMtlIssuSrc((EntityImpl) value);
            }
        }
        ,
        MmMtlIssuLot {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getMmMtlIssuLot();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        MmMtlIssuBin {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getMmMtlIssuBin();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        MmMtlIssuSr {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getMmMtlIssuSr();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        MmMtlIssuSrNote {
            public Object get(MmMtlIssuItmEOImpl obj) {
                return obj.getMmMtlIssuSrNote();
            }

            public void put(MmMtlIssuItmEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(MmMtlIssuItmEOImpl object);

        public abstract void put(MmMtlIssuItmEOImpl object, Object value);

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

    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int WHID = AttributesEnum.WhId.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int ISSUDOCTYPE = AttributesEnum.IssuDocType.index();
    public static final int DOCIDSRC = AttributesEnum.DocIdSrc.index();
    public static final int DOCDTSRC = AttributesEnum.DocDtSrc.index();
    public static final int ITMID = AttributesEnum.ItmId.index();
    public static final int ITMUOM = AttributesEnum.ItmUom.index();
    public static final int PENDQTY = AttributesEnum.PendQty.index();
    public static final int ISSUQTY = AttributesEnum.IssuQty.index();
    public static final int ITMUOMBS = AttributesEnum.ItmUomBs.index();
    public static final int ISSUQTYBS = AttributesEnum.IssuQtyBs.index();
    public static final int FOCFLG = AttributesEnum.FocFlg.index();
    public static final int UOMCONVFCTR = AttributesEnum.UomConvFctr.index();
    public static final int VEHNO = AttributesEnum.VehNo.index();
    public static final int METRREDNG = AttributesEnum.MetrRedng.index();
    public static final int CCID = AttributesEnum.CcId.index();
    public static final int MMMTLISSUSRC = AttributesEnum.MmMtlIssuSrc.index();
    public static final int MMMTLISSULOT = AttributesEnum.MmMtlIssuLot.index();
    public static final int MMMTLISSUBIN = AttributesEnum.MmMtlIssuBin.index();
    public static final int MMMTLISSUSR = AttributesEnum.MmMtlIssuSr.index();
    public static final int MMMTLISSUSRNOTE = AttributesEnum.MmMtlIssuSrNote.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MmMtlIssuItmEOImpl() {
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("mmissuematerial.model.entities.MmMtlIssuItmEO");
    }

    /**
     * Gets the attribute value for CldId, using the alias name CldId.
     * @return the value of CldId
     */
    public String getCldId() {
        return (String) getAttributeInternal(CLDID);
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
        return (Integer) getAttributeInternal(SLOCID);
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
        return (String) getAttributeInternal(ORGID);
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
        return (String) getAttributeInternal(WHID);
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
        return (String) getAttributeInternal(DOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for DocId.
     * @param value value to set the DocId
     */
    public void setDocId(String value) {
        setAttributeInternal(DOCID, value);
    }

    /**
     * Gets the attribute value for IssuDocType, using the alias name IssuDocType.
     * @return the value of IssuDocType
     */
    public Integer getIssuDocType() {
        return (Integer) getAttributeInternal(ISSUDOCTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for IssuDocType.
     * @param value value to set the IssuDocType
     */
    public void setIssuDocType(Integer value) {
        setAttributeInternal(ISSUDOCTYPE, value);
    }

    /**
     * Gets the attribute value for DocIdSrc, using the alias name DocIdSrc.
     * @return the value of DocIdSrc
     */
    public String getDocIdSrc() {
        return (String) getAttributeInternal(DOCIDSRC);
    }

    /**
     * Sets <code>value</code> as the attribute value for DocIdSrc.
     * @param value value to set the DocIdSrc
     */
    public void setDocIdSrc(String value) {
        setAttributeInternal(DOCIDSRC, value);
    }

    /**
     * Gets the attribute value for DocDtSrc, using the alias name DocDtSrc.
     * @return the value of DocDtSrc
     */
    public Timestamp getDocDtSrc() {
        return (Timestamp) getAttributeInternal(DOCDTSRC);
    }

    /**
     * Sets <code>value</code> as the attribute value for DocDtSrc.
     * @param value value to set the DocDtSrc
     */
    public void setDocDtSrc(Timestamp value) {
        setAttributeInternal(DOCDTSRC, value);
    }

    /**
     * Gets the attribute value for ItmId, using the alias name ItmId.
     * @return the value of ItmId
     */
    public String getItmId() {
        return (String) getAttributeInternal(ITMID);
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
        return (String) getAttributeInternal(ITMUOM);
    }

    /**
     * Sets <code>value</code> as the attribute value for ItmUom.
     * @param value value to set the ItmUom
     */
    public void setItmUom(String value) {
        setAttributeInternal(ITMUOM, value);
    }

    /**
     * Gets the attribute value for PendQty, using the alias name PendQty.
     * @return the value of PendQty
     */
    public Number getPendQty() {
        return (Number) getAttributeInternal(PENDQTY);
    }

    /**
     * Sets <code>value</code> as the attribute value for PendQty.
     * @param value value to set the PendQty
     */
    public void setPendQty(Number value) {
        setAttributeInternal(PENDQTY, value);
    }

    /**
     * Gets the attribute value for IssuQty, using the alias name IssuQty.
     * @return the value of IssuQty
     */
    public Number getIssuQty() {
        return (Number) getAttributeInternal(ISSUQTY);
    }

    /**
     * Sets <code>value</code> as the attribute value for IssuQty.
     * @param value value to set the IssuQty
     */
    public void setIssuQty(Number value) {
        setAttributeInternal(ISSUQTY, value);
    }

    /**
     * Gets the attribute value for ItmUomBs, using the alias name ItmUomBs.
     * @return the value of ItmUomBs
     */
    public String getItmUomBs() {
        return (String) getAttributeInternal(ITMUOMBS);
    }

    /**
     * Sets <code>value</code> as the attribute value for ItmUomBs.
     * @param value value to set the ItmUomBs
     */
    public void setItmUomBs(String value) {
        setAttributeInternal(ITMUOMBS, value);
    }

    /**
     * Gets the attribute value for IssuQtyBs, using the alias name IssuQtyBs.
     * @return the value of IssuQtyBs
     */
    public Number getIssuQtyBs() {
        return (Number) getAttributeInternal(ISSUQTYBS);
    }

    /**
     * Sets <code>value</code> as the attribute value for IssuQtyBs.
     * @param value value to set the IssuQtyBs
     */
    public void setIssuQtyBs(Number value) {
        setAttributeInternal(ISSUQTYBS, value);
    }

    /**
     * Gets the attribute value for FocFlg, using the alias name FocFlg.
     * @return the value of FocFlg
     */
    public String getFocFlg() {
        return (String) getAttributeInternal(FOCFLG);
    }

    /**
     * Sets <code>value</code> as the attribute value for FocFlg.
     * @param value value to set the FocFlg
     */
    public void setFocFlg(String value) {
        setAttributeInternal(FOCFLG, value);
    }

    /**
     * Gets the attribute value for UomConvFctr, using the alias name UomConvFctr.
     * @return the value of UomConvFctr
     */
    public Number getUomConvFctr() {
        return (Number) getAttributeInternal(UOMCONVFCTR);
    }

    /**
     * Sets <code>value</code> as the attribute value for UomConvFctr.
     * @param value value to set the UomConvFctr
     */
    public void setUomConvFctr(Number value) {
        setAttributeInternal(UOMCONVFCTR, value);
    }

    /**
     * Gets the attribute value for VehNo, using the alias name VehNo.
     * @return the value of VehNo
     */
    public String getVehNo() {
        return (String) getAttributeInternal(VEHNO);
    }

    /**
     * Sets <code>value</code> as the attribute value for VehNo.
     * @param value value to set the VehNo
     */
    public void setVehNo(String value) {
        setAttributeInternal(VEHNO, value);
    }

    /**
     * Gets the attribute value for MetrRedng, using the alias name MetrRedng.
     * @return the value of MetrRedng
     */
    public String getMetrRedng() {
        return (String) getAttributeInternal(METRREDNG);
    }

    /**
     * Sets <code>value</code> as the attribute value for MetrRedng.
     * @param value value to set the MetrRedng
     */
    public void setMetrRedng(String value) {
        setAttributeInternal(METRREDNG, value);
    }

    /**
     * Gets the attribute value for CcId, using the alias name CcId.
     * @return the value of CcId
     */
    public String getCcId() {
        return (String) getAttributeInternal(CCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CcId.
     * @param value value to set the CcId
     */
    public void setCcId(String value) {
        setAttributeInternal(CCID, value);
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
     * @return the associated entity oracle.jbo.server.EntityImpl.
     */
    public EntityImpl getMmMtlIssuSrc() {
        return (EntityImpl) getAttributeInternal(MMMTLISSUSRC);
    }

    /**
     * Sets <code>value</code> as the associated entity oracle.jbo.server.EntityImpl.
     */
    public void setMmMtlIssuSrc(EntityImpl value) {
        setAttributeInternal(MMMTLISSUSRC, value);
    }

    /**
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getMmMtlIssuLot() {
        return (RowIterator) getAttributeInternal(MMMTLISSULOT);
    }

    /**
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getMmMtlIssuBin() {
        return (RowIterator) getAttributeInternal(MMMTLISSUBIN);
    }

    /**
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getMmMtlIssuSr() {
        return (RowIterator) getAttributeInternal(MMMTLISSUSR);
    }

    /**
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getMmMtlIssuSrNote() {
        return (RowIterator) getAttributeInternal(MMMTLISSUSRNOTE);
    }

    /**
     * @param cldId key constituent
     * @param slocId key constituent
     * @param orgId key constituent
     * @param whId key constituent
     * @param docId key constituent
     * @param docIdSrc key constituent
     * @param itmId key constituent
     * @param itmUom key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String cldId, Integer slocId, String orgId, String whId, String docId,
                                       String docIdSrc, String itmId, String itmUom) {
        return new Key(new Object[] { cldId, slocId, orgId, whId, docId, docIdSrc, itmId, itmUom });
    }


}

