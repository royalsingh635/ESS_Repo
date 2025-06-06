package slsrmaapp.model.entities;

import adf.utils.bean.StaticValue;

import adf.utils.ebiz.EbizParams;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

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
// ---    Fri Jan 17 16:21:02 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class SlsRmaItmSrEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CldId {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getCldId();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setCldId((String) value);
            }
        }
        ,
        SlocId {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getSlocId();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setSlocId((Integer) value);
            }
        }
        ,
        OrgId {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getOrgId();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setOrgId((String) value);
            }
        }
        ,
        HoOrgId {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getHoOrgId();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setHoOrgId((String) value);
            }
        }
        ,
        DocId {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getDocId();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setDocId((String) value);
            }
        }
        ,
        DocDt {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getDocDt();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setDocDt((Timestamp) value);
            }
        }
        ,
        ShipmntId {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getShipmntId();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setShipmntId((String) value);
            }
        }
        ,
        ShipmntDt {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getShipmntDt();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setShipmntDt((Timestamp) value);
            }
        }
        ,
        LotId {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getLotId();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setLotId((String) value);
            }
        }
        ,
        SrNo {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getSrNo();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setSrNo((String) value);
            }
        }
        ,
        BinId {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getBinId();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setBinId((String) value);
            }
        }
        ,
        ItmId {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getItmId();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setItmId((String) value);
            }
        }
        ,
        ItmUom {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getItmUom();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setItmUom((String) value);
            }
        }
        ,
        SrQty {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getSrQty();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setSrQty((Number) value);
            }
        }
        ,
        SrQtyBs {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getSrQtyBs();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setSrQtyBs((Number) value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setUsrIdCreate((Integer) value);
            }
        }
        ,
        UsrIdCreateDt {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getUsrIdCreateDt();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setUsrIdCreateDt((Timestamp) value);
            }
        }
        ,
        UsrIdMod {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getUsrIdMod();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setUsrIdMod((Integer) value);
            }
        }
        ,
        UsrIdModDt {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getUsrIdModDt();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setUsrIdModDt((Timestamp) value);
            }
        }
        ,
        WhId {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getWhId();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setWhId((String) value);
            }
        }
        ,
        SoId {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getSoId();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setSoId((String) value);
            }
        }
        ,
        PickId {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getPickId();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setPickId((String) value);
            }
        }
        ,
        SrStat {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getSrStat();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setSrStat((String) value);
            }
        }
        ,
        SlsRmaItem {
            public Object get(SlsRmaItmSrEOImpl obj) {
                return obj.getSlsRmaItem();
            }

            public void put(SlsRmaItmSrEOImpl obj, Object value) {
                obj.setSlsRmaItem((SlsRmaItemEOImpl) value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(SlsRmaItmSrEOImpl object);

        public abstract void put(SlsRmaItmSrEOImpl object, Object value);

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
    public static final int SHIPMNTID = AttributesEnum.ShipmntId.index();
    public static final int SHIPMNTDT = AttributesEnum.ShipmntDt.index();
    public static final int LOTID = AttributesEnum.LotId.index();
    public static final int SRNO = AttributesEnum.SrNo.index();
    public static final int BINID = AttributesEnum.BinId.index();
    public static final int ITMID = AttributesEnum.ItmId.index();
    public static final int ITMUOM = AttributesEnum.ItmUom.index();
    public static final int SRQTY = AttributesEnum.SrQty.index();
    public static final int SRQTYBS = AttributesEnum.SrQtyBs.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int WHID = AttributesEnum.WhId.index();
    public static final int SOID = AttributesEnum.SoId.index();
    public static final int PICKID = AttributesEnum.PickId.index();
    public static final int SRSTAT = AttributesEnum.SrStat.index();
    public static final int SLSRMAITEM = AttributesEnum.SlsRmaItem.index();

    /**
     * This is the default constructor (do not remove).
     */
    public SlsRmaItmSrEOImpl() {
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("slsrmaapp.model.entities.SlsRmaItmSrEO");
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
     * Gets the attribute value for HoOrgId, using the alias name HoOrgId.
     * @return the value of HoOrgId
     */
    public String getHoOrgId() {
        return (String)getAttributeInternal(HOORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for HoOrgId.
     * @param value value to set the HoOrgId
     */
    public void setHoOrgId(String value) {
        setAttributeInternal(HOORGID, value);
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
     * Gets the attribute value for DocDt, using the alias name DocDt.
     * @return the value of DocDt
     */
    public Timestamp getDocDt() {
        return (Timestamp)getAttributeInternal(DOCDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for DocDt.
     * @param value value to set the DocDt
     */
    public void setDocDt(Timestamp value) {
        setAttributeInternal(DOCDT, value);
    }

    /**
     * Gets the attribute value for ShipmntId, using the alias name ShipmntId.
     * @return the value of ShipmntId
     */
    public String getShipmntId() {
        return (String)getAttributeInternal(SHIPMNTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for ShipmntId.
     * @param value value to set the ShipmntId
     */
    public void setShipmntId(String value) {
        setAttributeInternal(SHIPMNTID, value);
    }

    /**
     * Gets the attribute value for ShipmntDt, using the alias name ShipmntDt.
     * @return the value of ShipmntDt
     */
    public Timestamp getShipmntDt() {
        return (Timestamp)getAttributeInternal(SHIPMNTDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for ShipmntDt.
     * @param value value to set the ShipmntDt
     */
    public void setShipmntDt(Timestamp value) {
        setAttributeInternal(SHIPMNTDT, value);
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
     * Gets the attribute value for SrNo, using the alias name SrNo.
     * @return the value of SrNo
     */
    public String getSrNo() {
        return (String)getAttributeInternal(SRNO);
    }

    /**
     * Sets <code>value</code> as the attribute value for SrNo.
     * @param value value to set the SrNo
     */
    public void setSrNo(String value) {
        setAttributeInternal(SRNO, value);
    }

    /**
     * Gets the attribute value for BinId, using the alias name BinId.
     * @return the value of BinId
     */
    public String getBinId() {
        return (String)getAttributeInternal(BINID);
    }

    /**
     * Sets <code>value</code> as the attribute value for BinId.
     * @param value value to set the BinId
     */
    public void setBinId(String value) {
        setAttributeInternal(BINID, value);
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
     * Gets the attribute value for SrQty, using the alias name SrQty.
     * @return the value of SrQty
     */
    public Number getSrQty() {
        return (Number)getAttributeInternal(SRQTY);
    }

    /**
     * Sets <code>value</code> as the attribute value for SrQty.
     * @param value value to set the SrQty
     */
    public void setSrQty(Number value) {
        setAttributeInternal(SRQTY, value);
    }

    /**
     * Gets the attribute value for SrQtyBs, using the alias name SrQtyBs.
     * @return the value of SrQtyBs
     */
    public Number getSrQtyBs() {
        return (Number)getAttributeInternal(SRQTYBS);
    }

    /**
     * Sets <code>value</code> as the attribute value for SrQtyBs.
     * @param value value to set the SrQtyBs
     */
    public void setSrQtyBs(Number value) {
        setAttributeInternal(SRQTYBS, value);
    }

    /**
     * Gets the attribute value for UsrIdCreate, using the alias name UsrIdCreate.
     * @return the value of UsrIdCreate
     */
    public Integer getUsrIdCreate() {
        return (Integer)getAttributeInternal(USRIDCREATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdCreate.
     * @param value value to set the UsrIdCreate
     */
    public void setUsrIdCreate(Integer value) {
        setAttributeInternal(USRIDCREATE, value);
    }

    /**
     * Gets the attribute value for UsrIdCreateDt, using the alias name UsrIdCreateDt.
     * @return the value of UsrIdCreateDt
     */
    public Timestamp getUsrIdCreateDt() {
        return (Timestamp)getAttributeInternal(USRIDCREATEDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdCreateDt.
     * @param value value to set the UsrIdCreateDt
     */
    public void setUsrIdCreateDt(Timestamp value) {
        setAttributeInternal(USRIDCREATEDT, value);
    }

    /**
     * Gets the attribute value for UsrIdMod, using the alias name UsrIdMod.
     * @return the value of UsrIdMod
     */
    public Integer getUsrIdMod() {
        return (Integer)getAttributeInternal(USRIDMOD);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdMod.
     * @param value value to set the UsrIdMod
     */
    public void setUsrIdMod(Integer value) {
        setAttributeInternal(USRIDMOD, value);
    }

    /**
     * Gets the attribute value for UsrIdModDt, using the alias name UsrIdModDt.
     * @return the value of UsrIdModDt
     */
    public Timestamp getUsrIdModDt() {
        return (Timestamp)getAttributeInternal(USRIDMODDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdModDt.
     * @param value value to set the UsrIdModDt
     */
    public void setUsrIdModDt(Timestamp value) {
        setAttributeInternal(USRIDMODDT, value);
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
     * Gets the attribute value for SoId, using the alias name SoId.
     * @return the value of SoId
     */
    public String getSoId() {
        return (String)getAttributeInternal(SOID);
    }

    /**
     * Sets <code>value</code> as the attribute value for SoId.
     * @param value value to set the SoId
     */
    public void setSoId(String value) {
        setAttributeInternal(SOID, value);
    }

    /**
     * Gets the attribute value for PickId, using the alias name PickId.
     * @return the value of PickId
     */
    public String getPickId() {
        return (String) getAttributeInternal(PICKID);
    }

    /**
     * Sets <code>value</code> as the attribute value for PickId.
     * @param value value to set the PickId
     */
    public void setPickId(String value) {
        setAttributeInternal(PICKID, value);
    }

    /**
     * Gets the attribute value for SrStat, using the alias name SrStat.
     * @return the value of SrStat
     */
    public String getSrStat() {
        return (String) getAttributeInternal(SRSTAT);
    }

    /**
     * Sets <code>value</code> as the attribute value for SrStat.
     * @param value value to set the SrStat
     */
    public void setSrStat(String value) {
        setAttributeInternal(SRSTAT, value);
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
     * @return the associated entity SlsRmaItemEOImpl.
     */
    public SlsRmaItemEOImpl getSlsRmaItem() {
        return (SlsRmaItemEOImpl)getAttributeInternal(SLSRMAITEM);
    }

    /**
     * Sets <code>value</code> as the associated entity SlsRmaItemEOImpl.
     */
    public void setSlsRmaItem(SlsRmaItemEOImpl value) {
        setAttributeInternal(SLSRMAITEM, value);
    }


    /**
     * @param cldId key constituent
     * @param slocId key constituent
     * @param orgId key constituent
     * @param hoOrgId key constituent
     * @param docId key constituent
     * @param docDt key constituent
     * @param shipmntId key constituent
     * @param shipmntDt key constituent
     * @param lotId key constituent
     * @param srNo key constituent
     * @param binId key constituent
     * @param itmId key constituent
     * @param whId key constituent
     * @param soId key constituent
     * @param pickId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String cldId, Integer slocId, String orgId, String hoOrgId, String docId,
                                       Timestamp docDt, String shipmntId, Timestamp shipmntDt, String lotId,
                                       String srNo, String binId, String itmId, String whId, String soId,
                                       String pickId) {
        return new Key(new Object[] {
                       cldId, slocId, orgId, hoOrgId, docId, docDt, shipmntId, shipmntDt, lotId, srNo, binId, itmId,
                       whId, soId, pickId
        });
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
              setDocDt(StaticValue.getCurrDtWidTimestamp());
              setUsrIdCreate(EbizParams.GLBL_APP_USR());
              setUsrIdCreateDt(StaticValue.getCurrDtWidTimestamp());
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
        //super.lock();
    }
    /**
     * Custom DML update/insert/delete logic here.
     * @param operation the operation type
     * @param e the transaction event
     */
    protected void doDML(int operation, TransactionEvent e) {
        super.doDML(operation, e);
        if (operation == DML_UPDATE) {
            setUsrIdCreate(EbizParams.GLBL_APP_USR());
            setUsrIdCreateDt(StaticValue.getCurrDtWidTimestamp());
            setUsrIdMod(EbizParams.GLBL_APP_USR());
            setUsrIdModDt(StaticValue.getCurrDtWidTimestamp());
        }
       
    }
}
