package appwfservice.model.views;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Jul 12 11:32:11 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class WfHistVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CldId {
            public Object get(WfHistVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(WfHistVORowImpl obj, Object value) {
                obj.setCldId((String)value);
            }
        }
        ,
        OrgId {
            public Object get(WfHistVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(WfHistVORowImpl obj, Object value) {
                obj.setOrgId((String)value);
            }
        }
        ,
        SlocId {
            public Object get(WfHistVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(WfHistVORowImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        DocId {
            public Object get(WfHistVORowImpl obj) {
                return obj.getDocId();
            }

            public void put(WfHistVORowImpl obj, Object value) {
                obj.setDocId((Integer)value);
            }
        }
        ,
        TxnDocId {
            public Object get(WfHistVORowImpl obj) {
                return obj.getTxnDocId();
            }

            public void put(WfHistVORowImpl obj, Object value) {
                obj.setTxnDocId((String)value);
            }
        }
        ,
        UsrIdBy {
            public Object get(WfHistVORowImpl obj) {
                return obj.getUsrIdBy();
            }

            public void put(WfHistVORowImpl obj, Object value) {
                obj.setUsrIdBy((Integer)value);
            }
        }
        ,
        UsrIdTo {
            public Object get(WfHistVORowImpl obj) {
                return obj.getUsrIdTo();
            }

            public void put(WfHistVORowImpl obj, Object value) {
                obj.setUsrIdTo((Integer)value);
            }
        }
        ,
        WfStatusId {
            public Object get(WfHistVORowImpl obj) {
                return obj.getWfStatusId();
            }

            public void put(WfHistVORowImpl obj, Object value) {
                obj.setWfStatusId((String)value);
            }
        }
        ,
        WfActnSeq {
            public Object get(WfHistVORowImpl obj) {
                return obj.getWfActnSeq();
            }

            public void put(WfHistVORowImpl obj, Object value) {
                obj.setWfActnSeq((String)value);
            }
        }
        ,
        TxnHist {
            public Object get(WfHistVORowImpl obj) {
                return obj.getTxnHist();
            }

            public void put(WfHistVORowImpl obj, Object value) {
                obj.setTxnHist((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(WfHistVORowImpl object);

        public abstract void put(WfHistVORowImpl object, Object value);

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
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int TXNDOCID = AttributesEnum.TxnDocId.index();
    public static final int USRIDBY = AttributesEnum.UsrIdBy.index();
    public static final int USRIDTO = AttributesEnum.UsrIdTo.index();
    public static final int WFSTATUSID = AttributesEnum.WfStatusId.index();
    public static final int WFACTNSEQ = AttributesEnum.WfActnSeq.index();
    public static final int TXNHIST = AttributesEnum.TxnHist.index();

    /**
     * This is the default constructor (do not remove).
     */
    public WfHistVORowImpl() {
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
     * Gets the attribute value for the calculated attribute DocId.
     * @return the DocId
     */
    public Integer getDocId() {
        return (Integer) getAttributeInternal(DOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute DocId.
     * @param value value to set the  DocId
     */
    public void setDocId(Integer value) {
        setAttributeInternal(DOCID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TxnDocId.
     * @return the TxnDocId
     */
    public String getTxnDocId() {
        return (String) getAttributeInternal(TXNDOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TxnDocId.
     * @param value value to set the  TxnDocId
     */
    public void setTxnDocId(String value) {
        setAttributeInternal(TXNDOCID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrIdBy.
     * @return the UsrIdBy
     */
    public Integer getUsrIdBy() {
        return (Integer) getAttributeInternal(USRIDBY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrIdBy.
     * @param value value to set the  UsrIdBy
     */
    public void setUsrIdBy(Integer value) {
        setAttributeInternal(USRIDBY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrIdTo.
     * @return the UsrIdTo
     */
    public Integer getUsrIdTo() {
        return (Integer) getAttributeInternal(USRIDTO);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrIdTo.
     * @param value value to set the  UsrIdTo
     */
    public void setUsrIdTo(Integer value) {
        setAttributeInternal(USRIDTO, value);
    }

    /**
     * Gets the attribute value for the calculated attribute WfStatusId.
     * @return the WfStatusId
     */
    public String getWfStatusId() {
        return (String) getAttributeInternal(WFSTATUSID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute WfStatusId.
     * @param value value to set the  WfStatusId
     */
    public void setWfStatusId(String value) {
        setAttributeInternal(WFSTATUSID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute WfActnSeq.
     * @return the WfActnSeq
     */
    public String getWfActnSeq() {
        return (String) getAttributeInternal(WFACTNSEQ);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute WfActnSeq.
     * @param value value to set the  WfActnSeq
     */
    public void setWfActnSeq(String value) {
        setAttributeInternal(WFACTNSEQ, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TxnHist.
     * @return the TxnHist
     */
    public String getTxnHist() {
        return (String) getAttributeInternal(TXNHIST);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TxnHist.
     * @param value value to set the  TxnHist
     */
    public void setTxnHist(String value) {
        setAttributeInternal(TXNHIST, value);
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
