package mmqcprocess.model.entities;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.RowIterator;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Aug 22 18:09:31 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MmMtlQcEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CldId {
            public Object get(MmMtlQcEOImpl obj) {
                return obj.getCldId();
            }

            public void put(MmMtlQcEOImpl obj, Object value) {
                obj.setCldId((String)value);
            }
        }
        ,
        SlocId {
            public Object get(MmMtlQcEOImpl obj) {
                return obj.getSlocId();
            }

            public void put(MmMtlQcEOImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        OrgId {
            public Object get(MmMtlQcEOImpl obj) {
                return obj.getOrgId();
            }

            public void put(MmMtlQcEOImpl obj, Object value) {
                obj.setOrgId((String)value);
            }
        }
        ,
        QcTxnId {
            public Object get(MmMtlQcEOImpl obj) {
                return obj.getQcTxnId();
            }

            public void put(MmMtlQcEOImpl obj, Object value) {
                obj.setQcTxnId((String)value);
            }
        }
        ,
        QcTxnDt {
            public Object get(MmMtlQcEOImpl obj) {
                return obj.getQcTxnDt();
            }

            public void put(MmMtlQcEOImpl obj, Object value) {
                obj.setQcTxnDt((Timestamp)value);
            }
        }
        ,
        QcItmType {
            public Object get(MmMtlQcEOImpl obj) {
                return obj.getQcItmType();
            }

            public void put(MmMtlQcEOImpl obj, Object value) {
                obj.setQcItmType((Integer)value);
            }
        }
        ,
        QcChkType {
            public Object get(MmMtlQcEOImpl obj) {
                return obj.getQcChkType();
            }

            public void put(MmMtlQcEOImpl obj, Object value) {
                obj.setQcChkType((Integer)value);
            }
        }
        ,
        QcSrcTxnId {
            public Object get(MmMtlQcEOImpl obj) {
                return obj.getQcSrcTxnId();
            }

            public void put(MmMtlQcEOImpl obj, Object value) {
                obj.setQcSrcTxnId((String)value);
            }
        }
        ,
        QcSrcTxnDt {
            public Object get(MmMtlQcEOImpl obj) {
                return obj.getQcSrcTxnDt();
            }

            public void put(MmMtlQcEOImpl obj, Object value) {
                obj.setQcSrcTxnDt((Timestamp)value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(MmMtlQcEOImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(MmMtlQcEOImpl obj, Object value) {
                obj.setUsrIdCreate((Integer)value);
            }
        }
        ,
        UsrIdCreateDt {
            public Object get(MmMtlQcEOImpl obj) {
                return obj.getUsrIdCreateDt();
            }

            public void put(MmMtlQcEOImpl obj, Object value) {
                obj.setUsrIdCreateDt((Timestamp)value);
            }
        }
        ,
        UsrIdMod {
            public Object get(MmMtlQcEOImpl obj) {
                return obj.getUsrIdMod();
            }

            public void put(MmMtlQcEOImpl obj, Object value) {
                obj.setUsrIdMod((Integer)value);
            }
        }
        ,
        UsrIdModDt {
            public Object get(MmMtlQcEOImpl obj) {
                return obj.getUsrIdModDt();
            }

            public void put(MmMtlQcEOImpl obj, Object value) {
                obj.setUsrIdModDt((Timestamp)value);
            }
        }
        ,
        QcComplete {
            public Object get(MmMtlQcEOImpl obj) {
                return obj.getQcComplete();
            }

            public void put(MmMtlQcEOImpl obj, Object value) {
                obj.setQcComplete((String)value);
            }
        }
        ,
        MmMtlQcRslt {
            public Object get(MmMtlQcEOImpl obj) {
                return obj.getMmMtlQcRslt();
            }

            public void put(MmMtlQcEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        MmMtlQcSrc {
            public Object get(MmMtlQcEOImpl obj) {
                return obj.getMmMtlQcSrc();
            }

            public void put(MmMtlQcEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(MmMtlQcEOImpl object);

        public abstract void put(MmMtlQcEOImpl object, Object value);

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
    public static final int QCTXNID = AttributesEnum.QcTxnId.index();
    public static final int QCTXNDT = AttributesEnum.QcTxnDt.index();
    public static final int QCITMTYPE = AttributesEnum.QcItmType.index();
    public static final int QCCHKTYPE = AttributesEnum.QcChkType.index();
    public static final int QCSRCTXNID = AttributesEnum.QcSrcTxnId.index();
    public static final int QCSRCTXNDT = AttributesEnum.QcSrcTxnDt.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int QCCOMPLETE = AttributesEnum.QcComplete.index();
    public static final int MMMTLQCRSLT = AttributesEnum.MmMtlQcRslt.index();
    public static final int MMMTLQCSRC = AttributesEnum.MmMtlQcSrc.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MmMtlQcEOImpl() {
    }


    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("mmqcprocess.model.entities.MmMtlQcEO");
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
     * Gets the attribute value for QcTxnId, using the alias name QcTxnId.
     * @return the value of QcTxnId
     */
    public String getQcTxnId() {
        return (String)getAttributeInternal(QCTXNID);
    }

    /**
     * Sets <code>value</code> as the attribute value for QcTxnId.
     * @param value value to set the QcTxnId
     */
    public void setQcTxnId(String value) {
        setAttributeInternal(QCTXNID, value);
    }

    /**
     * Gets the attribute value for QcTxnDt, using the alias name QcTxnDt.
     * @return the value of QcTxnDt
     */
    public Timestamp getQcTxnDt() {
        return (Timestamp)getAttributeInternal(QCTXNDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for QcTxnDt.
     * @param value value to set the QcTxnDt
     */
    public void setQcTxnDt(Timestamp value) {
        setAttributeInternal(QCTXNDT, value);
    }

    /**
     * Gets the attribute value for QcItmType, using the alias name QcItmType.
     * @return the value of QcItmType
     */
    public Integer getQcItmType() {
        return (Integer)getAttributeInternal(QCITMTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for QcItmType.
     * @param value value to set the QcItmType
     */
    public void setQcItmType(Integer value) {
        setAttributeInternal(QCITMTYPE, value);
    }

    /**
     * Gets the attribute value for QcChkType, using the alias name QcChkType.
     * @return the value of QcChkType
     */
    public Integer getQcChkType() {
        return (Integer)getAttributeInternal(QCCHKTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for QcChkType.
     * @param value value to set the QcChkType
     */
    public void setQcChkType(Integer value) {
        setAttributeInternal(QCCHKTYPE, value);
    }

    /**
     * Gets the attribute value for QcSrcTxnId, using the alias name QcSrcTxnId.
     * @return the value of QcSrcTxnId
     */
    public String getQcSrcTxnId() {
        return (String)getAttributeInternal(QCSRCTXNID);
    }

    /**
     * Sets <code>value</code> as the attribute value for QcSrcTxnId.
     * @param value value to set the QcSrcTxnId
     */
    public void setQcSrcTxnId(String value) {
        setAttributeInternal(QCSRCTXNID, value);
    }

    /**
     * Gets the attribute value for QcSrcTxnDt, using the alias name QcSrcTxnDt.
     * @return the value of QcSrcTxnDt
     */
    public Timestamp getQcSrcTxnDt() {
        return (Timestamp)getAttributeInternal(QCSRCTXNDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for QcSrcTxnDt.
     * @param value value to set the QcSrcTxnDt
     */
    public void setQcSrcTxnDt(Timestamp value) {
        setAttributeInternal(QCSRCTXNDT, value);
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
     * Gets the attribute value for QcComplete, using the alias name QcComplete.
     * @return the value of QcComplete
     */
    public String getQcComplete() {
        return (String)getAttributeInternal(QCCOMPLETE);
    }

    /**
     * Sets <code>value</code> as the attribute value for QcComplete.
     * @param value value to set the QcComplete
     */
    public void setQcComplete(String value) {
        setAttributeInternal(QCCOMPLETE, value);
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
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getMmMtlQcRslt() {
        return (RowIterator)getAttributeInternal(MMMTLQCRSLT);
    }

    /**
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getMmMtlQcSrc() {
        return (RowIterator)getAttributeInternal(MMMTLQCSRC);
    }


    /**
     * @param cldId key constituent
     * @param slocId key constituent
     * @param orgId key constituent
     * @param qcTxnId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String cldId, Integer slocId, String orgId, String qcTxnId) {
        return new Key(new Object[]{cldId, slocId, orgId, qcTxnId});
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
