package mmqcprocess.model.views;

import mmqcprocess.model.service.QcProcessAMImpl;

import oracle.jbo.RowSet;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Jul 28 12:38:20 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MtlQcSearchVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId {
            public Object get(MtlQcSearchVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(MtlQcSearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        SlocId {
            public Object get(MtlQcSearchVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(MtlQcSearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        OrgId {
            public Object get(MtlQcSearchVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(MtlQcSearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        QcTxnId {
            public Object get(MtlQcSearchVORowImpl obj) {
                return obj.getQcTxnId();
            }

            public void put(MtlQcSearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        QcTxnDt {
            public Object get(MtlQcSearchVORowImpl obj) {
                return obj.getQcTxnDt();
            }

            public void put(MtlQcSearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        QcItmType {
            public Object get(MtlQcSearchVORowImpl obj) {
                return obj.getQcItmType();
            }

            public void put(MtlQcSearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        QcChkType {
            public Object get(MtlQcSearchVORowImpl obj) {
                return obj.getQcChkType();
            }

            public void put(MtlQcSearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        QcSrcTxnId {
            public Object get(MtlQcSearchVORowImpl obj) {
                return obj.getQcSrcTxnId();
            }

            public void put(MtlQcSearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        RcptNo {
            public Object get(MtlQcSearchVORowImpl obj) {
                return obj.getRcptNo();
            }

            public void put(MtlQcSearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        QcSrcTxnDt {
            public Object get(MtlQcSearchVORowImpl obj) {
                return obj.getQcSrcTxnDt();
            }

            public void put(MtlQcSearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        QcComplete {
            public Object get(MtlQcSearchVORowImpl obj) {
                return obj.getQcComplete();
            }

            public void put(MtlQcSearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        QcNo {
            public Object get(MtlQcSearchVORowImpl obj) {
                return obj.getQcNo();
            }

            public void put(MtlQcSearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(MtlQcSearchVORowImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(MtlQcSearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        TransUsrNm {
            public Object get(MtlQcSearchVORowImpl obj) {
                return obj.getTransUsrNm();
            }

            public void put(MtlQcSearchVORowImpl obj, Object value) {
                obj.setTransUsrNm((String) value);
            }
        }
        ,
        LovQcItmTypeVO {
            public Object get(MtlQcSearchVORowImpl obj) {
                return obj.getLovQcItmTypeVO();
            }

            public void put(MtlQcSearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovQcCheckVO {
            public Object get(MtlQcSearchVORowImpl obj) {
                return obj.getLovQcCheckVO();
            }

            public void put(MtlQcSearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovUsrIdVO1 {
            public Object get(MtlQcSearchVORowImpl obj) {
                return obj.getLovUsrIdVO1();
            }

            public void put(MtlQcSearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(MtlQcSearchVORowImpl object);

        public abstract void put(MtlQcSearchVORowImpl object, Object value);

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
    public static final int QCTXNID = AttributesEnum.QcTxnId.index();
    public static final int QCTXNDT = AttributesEnum.QcTxnDt.index();
    public static final int QCITMTYPE = AttributesEnum.QcItmType.index();
    public static final int QCCHKTYPE = AttributesEnum.QcChkType.index();
    public static final int QCSRCTXNID = AttributesEnum.QcSrcTxnId.index();
    public static final int RCPTNO = AttributesEnum.RcptNo.index();
    public static final int QCSRCTXNDT = AttributesEnum.QcSrcTxnDt.index();
    public static final int QCCOMPLETE = AttributesEnum.QcComplete.index();
    public static final int QCNO = AttributesEnum.QcNo.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int TRANSUSRNM = AttributesEnum.TransUsrNm.index();
    public static final int LOVQCITMTYPEVO = AttributesEnum.LovQcItmTypeVO.index();
    public static final int LOVQCCHECKVO = AttributesEnum.LovQcCheckVO.index();
    public static final int LOVUSRIDVO1 = AttributesEnum.LovUsrIdVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MtlQcSearchVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute CldId.
     * @return the CldId
     */
    public String getCldId() {
        return (String) getAttributeInternal(CLDID);
    }

    /**
     * Gets the attribute value for the calculated attribute SlocId.
     * @return the SlocId
     */
    public Integer getSlocId() {
        return (Integer) getAttributeInternal(SLOCID);
    }

    /**
     * Gets the attribute value for the calculated attribute OrgId.
     * @return the OrgId
     */
    public String getOrgId() {
        return (String) getAttributeInternal(ORGID);
    }

    /**
     * Gets the attribute value for the calculated attribute QcTxnId.
     * @return the QcTxnId
     */
    public String getQcTxnId() {
        return (String) getAttributeInternal(QCTXNID);
    }

    /**
     * Gets the attribute value for the calculated attribute QcTxnDt.
     * @return the QcTxnDt
     */
    public Timestamp getQcTxnDt() {
        return (Timestamp) getAttributeInternal(QCTXNDT);
    }

    /**
     * Gets the attribute value for the calculated attribute QcItmType.
     * @return the QcItmType
     */
    public Integer getQcItmType() {
        return (Integer) getAttributeInternal(QCITMTYPE);
    }

    /**
     * Gets the attribute value for the calculated attribute QcChkType.
     * @return the QcChkType
     */
    public Integer getQcChkType() {
        return (Integer) getAttributeInternal(QCCHKTYPE);
    }

    /**
     * Gets the attribute value for the calculated attribute QcSrcTxnId.
     * @return the QcSrcTxnId
     */
    public String getQcSrcTxnId() {
        return (String) getAttributeInternal(QCSRCTXNID);
    }

    /**
     * Gets the attribute value for the calculated attribute RcptNo.
     * @return the RcptNo
     */
    public String getRcptNo() {
        return (String) getAttributeInternal(RCPTNO);
    }

    /**
     * Gets the attribute value for the calculated attribute QcSrcTxnDt.
     * @return the QcSrcTxnDt
     */
    public Timestamp getQcSrcTxnDt() {
        return (Timestamp) getAttributeInternal(QCSRCTXNDT);
    }

    /**
     * Gets the attribute value for the calculated attribute QcComplete.
     * @return the QcComplete
     */
    public String getQcComplete() {
        return (String) getAttributeInternal(QCCOMPLETE);
    }

    /**
     * Gets the attribute value for the calculated attribute QcNo.
     * @return the QcNo
     */
    public String getQcNo() {
        return (String) getAttributeInternal(QCNO);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrIdCreate.
     * @return the UsrIdCreate
     */
    public Integer getUsrIdCreate() {
        return (Integer) getAttributeInternal(USRIDCREATE);
    }

    /**
     * Gets the attribute value for the calculated attribute TransUsrMn.
     * @return the TransUsrMn
     */
    public String getTransUsrNm() {
        String usrId=null;
        //System.out.println("Get usr id "+this.getUsrIdCreate());
        if(this.getUsrIdCreate()!=null){
            LovUsrIdVOImpl usr=((QcProcessAMImpl)this.getApplicationModule()).getLovUsrIdVO1();
           // System.out.println("Row count is "+usr.getRowCount());
            usr.setBindSlocId(this.getSlocId());
            usr.setBindUsrId(this.getUsrIdCreate());
            usr.executeQuery();
            //System.out.println("Row count is "+usr.getRowCount());
            if(usr.getRowCount()==1){
                return usr.first().getAttribute("UsrName").toString();
            }
        }
        
        return (String) getAttributeInternal(TRANSUSRNM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransUsrNm.
     * @param value value to set the  TransUsrNm
     */
    public void setTransUsrNm(String value) {
        setAttributeInternal(TRANSUSRNM, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovQcItmTypeVO.
     */
    public RowSet getLovQcItmTypeVO() {
        return (RowSet) getAttributeInternal(LOVQCITMTYPEVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovQcCheckVO.
     */
    public RowSet getLovQcCheckVO() {
        return (RowSet) getAttributeInternal(LOVQCCHECKVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovUsrIdVO1.
     */
    public RowSet getLovUsrIdVO1() {
        return (RowSet) getAttributeInternal(LOVUSRIDVO1);
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

