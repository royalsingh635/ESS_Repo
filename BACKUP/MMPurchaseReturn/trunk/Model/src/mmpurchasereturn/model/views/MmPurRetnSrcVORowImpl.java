package mmpurchasereturn.model.views;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import mmpurchasereturn.model.services.MmPurchaseReturnAMImpl;

import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Dec 12 16:06:49 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MmPurRetnSrcVORowImpl extends ViewRowImpl {
    public static final int ENTITY_MMPURRETNSRCEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CldId {
            public Object get(MmPurRetnSrcVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(MmPurRetnSrcVORowImpl obj, Object value) {
                obj.setCldId((String)value);
            }
        }
        ,
        DocId {
            public Object get(MmPurRetnSrcVORowImpl obj) {
                return obj.getDocId();
            }

            public void put(MmPurRetnSrcVORowImpl obj, Object value) {
                obj.setDocId((String)value);
            }
        }
        ,
        DocIdMrn {
            public Object get(MmPurRetnSrcVORowImpl obj) {
                return obj.getDocIdMrn();
            }

            public void put(MmPurRetnSrcVORowImpl obj, Object value) {
                obj.setDocIdMrn((String)value);
            }
        }
        ,
        OrgId {
            public Object get(MmPurRetnSrcVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(MmPurRetnSrcVORowImpl obj, Object value) {
                obj.setOrgId((String)value);
            }
        }
        ,
        SlocId {
            public Object get(MmPurRetnSrcVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(MmPurRetnSrcVORowImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        WhId {
            public Object get(MmPurRetnSrcVORowImpl obj) {
                return obj.getWhId();
            }

            public void put(MmPurRetnSrcVORowImpl obj, Object value) {
                obj.setWhId((String)value);
            }
        }
        ,
        TransMrnNo {
            public Object get(MmPurRetnSrcVORowImpl obj) {
                return obj.getTransMrnNo();
            }

            public void put(MmPurRetnSrcVORowImpl obj, Object value) {
                obj.setTransMrnNo((String)value);
            }
        }
        ,
        TransMrnDocDt {
            public Object get(MmPurRetnSrcVORowImpl obj) {
                return obj.getTransMrnDocDt();
            }

            public void put(MmPurRetnSrcVORowImpl obj, Object value) {
                obj.setTransMrnDocDt((Timestamp)value);
            }
        }
        ,
        MmPurRetnItm {
            public Object get(MmPurRetnSrcVORowImpl obj) {
                return obj.getMmPurRetnItm();
            }

            public void put(MmPurRetnSrcVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(MmPurRetnSrcVORowImpl object);

        public abstract void put(MmPurRetnSrcVORowImpl object, Object value);

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
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int DOCIDMRN = AttributesEnum.DocIdMrn.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int WHID = AttributesEnum.WhId.index();
    public static final int TRANSMRNNO = AttributesEnum.TransMrnNo.index();
    public static final int TRANSMRNDOCDT = AttributesEnum.TransMrnDocDt.index();
    public static final int MMPURRETNITM = AttributesEnum.MmPurRetnItm.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MmPurRetnSrcVORowImpl() {
    }

    /**
     * Gets MmPurRetnSrcEO entity object.
     * @return the MmPurRetnSrcEO
     */
    public EntityImpl getMmPurRetnSrcEO() {
        return (EntityImpl)getEntity(ENTITY_MMPURRETNSRCEO);
    }

    /**
     * Gets the attribute value for CLD_ID using the alias name CldId.
     * @return the CLD_ID
     */
    public String getCldId() {
        return (String) getAttributeInternal(CLDID);
    }

    /**
     * Sets <code>value</code> as attribute value for CLD_ID using the alias name CldId.
     * @param value value to set the CLD_ID
     */
    public void setCldId(String value) {
        setAttributeInternal(CLDID, value);
    }

    /**
     * Gets the attribute value for DOC_ID using the alias name DocId.
     * @return the DOC_ID
     */
    public String getDocId() {
        return (String) getAttributeInternal(DOCID);
    }

    /**
     * Sets <code>value</code> as attribute value for DOC_ID using the alias name DocId.
     * @param value value to set the DOC_ID
     */
    public void setDocId(String value) {
        setAttributeInternal(DOCID, value);
    }

    /**
     * Gets the attribute value for DOC_ID_MRN using the alias name DocIdMrn.
     * @return the DOC_ID_MRN
     */
    public String getDocIdMrn() {
        return (String) getAttributeInternal(DOCIDMRN);
    }

    /**
     * Sets <code>value</code> as attribute value for DOC_ID_MRN using the alias name DocIdMrn.
     * @param value value to set the DOC_ID_MRN
     */
    public void setDocIdMrn(String value) {
        setAttributeInternal(DOCIDMRN, value);
    }

    /**
     * Gets the attribute value for ORG_ID using the alias name OrgId.
     * @return the ORG_ID
     */
    public String getOrgId() {
        return (String) getAttributeInternal(ORGID);
    }

    /**
     * Sets <code>value</code> as attribute value for ORG_ID using the alias name OrgId.
     * @param value value to set the ORG_ID
     */
    public void setOrgId(String value) {
        setAttributeInternal(ORGID, value);
    }

    /**
     * Gets the attribute value for SLOC_ID using the alias name SlocId.
     * @return the SLOC_ID
     */
    public Integer getSlocId() {
        return (Integer) getAttributeInternal(SLOCID);
    }

    /**
     * Sets <code>value</code> as attribute value for SLOC_ID using the alias name SlocId.
     * @param value value to set the SLOC_ID
     */
    public void setSlocId(Integer value) {
        setAttributeInternal(SLOCID, value);
    }

    /**
     * Gets the attribute value for WH_ID using the alias name WhId.
     * @return the WH_ID
     */
    public String getWhId() {
        return (String) getAttributeInternal(WHID);
    }

    /**
     * Sets <code>value</code> as attribute value for WH_ID using the alias name WhId.
     * @param value value to set the WH_ID
     */
    public void setWhId(String value) {
        setAttributeInternal(WHID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransMrnNo.
     * @return the TransMrnNo
     */
    public String getTransMrnNo() {
        if((String) getAttributeInternal(TRANSMRNNO)!=null)
        return (String) getAttributeInternal(TRANSMRNNO);
        else
        {
            if(getDocIdMrn()!=null)
            {
                System.out.println("Mrn DocId="+getDocIdMrn());
        MmPurchaseReturnAMImpl am = (MmPurchaseReturnAMImpl)resolvElDC("MmPurchaseReturnAMDataControl");
           //     am.getLovMrnNo().setRangeSize(-1);
      //  System.out.println("NO of rows in mrn="+am.getLovMrnNo().getEstimatedRowCount());
         Row[] fr=am.getLovMrnNoNew().getFilteredRows("DocId",getDocIdMrn());
       //  System.out.println("No. of filtered rows="+fr.length);
         if(fr.length>0)
         { 
             String mrnNo=null;
             if(fr[0].getAttribute("MrnNo")!=null)
              mrnNo= (String)fr[0].getAttribute("MrnNo");
             System.out.println("mrn no="+mrnNo);
             return mrnNo;
         }
         else
                return (String) getAttributeInternal(TRANSMRNNO);  
            }
            else
                return (String) getAttributeInternal(TRANSMRNNO);  
            }
    }
    
    public Object resolvElDC(String data) {
    FacesContext fc = FacesContext.getCurrentInstance();
    Application app = fc.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = fc.getELContext();
    ValueExpression valueExp =
    elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
    return valueExp.getValue(elContext);
    }
    
    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransMrnNo.
     * @param value value to set the  TransMrnNo
     */
    public void setTransMrnNo(String value) {
        setAttributeInternal(TRANSMRNNO, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransMrnDocDt.
     * @return the TransMrnDocDt
     */
    public Timestamp getTransMrnDocDt() {
        if((Timestamp) getAttributeInternal(TRANSMRNDOCDT)!=null)
        return (Timestamp) getAttributeInternal(TRANSMRNDOCDT);
        else
        {
            if(getDocIdMrn()!=null)
            {
        MmPurchaseReturnAMImpl am = (MmPurchaseReturnAMImpl)resolvElDC("MmPurchaseReturnAMDataControl");
         Row[] fr=am.getLovMrnNoNew().getFilteredRows("DocId",getDocIdMrn());
         if(fr.length>0)
         {
               Timestamp mrnDt=null;
             if(fr[0].getAttribute("DocDt")!=null)
              mrnDt = (Timestamp)fr[0].getAttribute("DocDt");
             return mrnDt;
         }
         else
             return (Timestamp) getAttributeInternal(TRANSMRNDOCDT);
            }
            else
                return (Timestamp) getAttributeInternal(TRANSMRNDOCDT);  
            }
        
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransMrnDocDt.
     * @param value value to set the  TransMrnDocDt
     */
    public void setTransMrnDocDt(Timestamp value) {
        setAttributeInternal(TRANSMRNDOCDT, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link MmPurRetnItm.
     */
    public RowIterator getMmPurRetnItm() {
        return (RowIterator)getAttributeInternal(MMPURRETNITM);
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
