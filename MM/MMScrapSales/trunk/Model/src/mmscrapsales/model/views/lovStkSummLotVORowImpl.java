package mmscrapsales.model.views;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import mmscrapsales.model.services.MmScrapSalesAMImpl;

import oracle.jbo.Row;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Jul 07 17:41:58 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class lovStkSummLotVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CldId {
            public Object get(lovStkSummLotVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(lovStkSummLotVORowImpl obj, Object value) {
                obj.setCldId((String)value);
            }
        }
        ,
        SlocId {
            public Object get(lovStkSummLotVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(lovStkSummLotVORowImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        OrgId {
            public Object get(lovStkSummLotVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(lovStkSummLotVORowImpl obj, Object value) {
                obj.setOrgId((String)value);
            }
        }
        ,
        ItmId {
            public Object get(lovStkSummLotVORowImpl obj) {
                return obj.getItmId();
            }

            public void put(lovStkSummLotVORowImpl obj, Object value) {
                obj.setItmId((String)value);
            }
        }
        ,
        LotId {
            public Object get(lovStkSummLotVORowImpl obj) {
                return obj.getLotId();
            }

            public void put(lovStkSummLotVORowImpl obj, Object value) {
                obj.setLotId((String)value);
            }
        }
        ,
        TotStk {
            public Object get(lovStkSummLotVORowImpl obj) {
                return obj.getTotStk();
            }

            public void put(lovStkSummLotVORowImpl obj, Object value) {
                obj.setTotStk((Number)value);
            }
        }
        ,
        ScrpStk {
            public Object get(lovStkSummLotVORowImpl obj) {
                return obj.getScrpStk();
            }

            public void put(lovStkSummLotVORowImpl obj, Object value) {
                obj.setScrpStk((Number)value);
            }
        }
        ,
        TransItmNm {
            public Object get(lovStkSummLotVORowImpl obj) {
                return obj.getTransItmNm();
            }

            public void put(lovStkSummLotVORowImpl obj, Object value) {
                obj.setTransItmNm((String)value);
            }
        }
        ,
        TransItmQty {
            public Object get(lovStkSummLotVORowImpl obj) {
                return obj.getTransItmQty();
            }

            public void put(lovStkSummLotVORowImpl obj, Object value) {
                obj.setTransItmQty((Number)value);
            }
        }
        ,
        TranHoOrgId {
            public Object get(lovStkSummLotVORowImpl obj) {
                return obj.getTranHoOrgId();
            }

            public void put(lovStkSummLotVORowImpl obj, Object value) {
                obj.setTranHoOrgId((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(lovStkSummLotVORowImpl object);

        public abstract void put(lovStkSummLotVORowImpl object, Object value);

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
    public static final int ITMID = AttributesEnum.ItmId.index();
    public static final int LOTID = AttributesEnum.LotId.index();
    public static final int TOTSTK = AttributesEnum.TotStk.index();
    public static final int SCRPSTK = AttributesEnum.ScrpStk.index();
    public static final int TRANSITMNM = AttributesEnum.TransItmNm.index();
    public static final int TRANSITMQTY = AttributesEnum.TransItmQty.index();
    public static final int TRANHOORGID = AttributesEnum.TranHoOrgId.index();

    /**
     * This is the default constructor (do not remove).
     */
    public lovStkSummLotVORowImpl() {
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
     * Gets the attribute value for the calculated attribute ItmId.
     * @return the ItmId
     */
    public String getItmId() {
        return (String) getAttributeInternal(ITMID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ItmId.
     * @param value value to set the  ItmId
     */
    public void setItmId(String value) {
        setAttributeInternal(ITMID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute LotId.
     * @return the LotId
     */
    public String getLotId() {
        return (String) getAttributeInternal(LOTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute LotId.
     * @param value value to set the  LotId
     */
    public void setLotId(String value) {
        setAttributeInternal(LOTID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TotStk.
     * @return the TotStk
     */
    public Number getTotStk() {
        return (Number) getAttributeInternal(TOTSTK);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TotStk.
     * @param value value to set the  TotStk
     */
    public void setTotStk(Number value) {
        setAttributeInternal(TOTSTK, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ScrpStk.
     * @return the ScrpStk
     */
    public Number getScrpStk() {
        return (Number) getAttributeInternal(SCRPSTK);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ScrpStk.
     * @param value value to set the  ScrpStk
     */
     public String resolvEl(String data) {
         FacesContext fc = FacesContext.getCurrentInstance();
         Application app = fc.getApplication();
         ExpressionFactory elFactory = app.getExpressionFactory();
         ELContext elContext = fc.getELContext();
         ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
         String Message = valueExp.getValue(elContext).toString();
         return Message;
     }
    public void setScrpStk(Number value) {
        setAttributeInternal(SCRPSTK, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransItmNm.
     * @return the TransItmNm
     */
    public String getTransItmNm() {
        String itmName=null;
        if(getItmId()!=null){
        MmScrapSalesAMImpl am=(MmScrapSalesAMImpl)this.getApplicationModule();
        ViewObjectImpl itmvo=am.getLovItmVo();
        
        itmvo.setNamedWhereClauseParam("BindCldId", getCldId());
        itmvo.setNamedWhereClauseParam("BindSlocId", getSlocId());
        itmvo.setNamedWhereClauseParam("BindHoOrgId", getTranHoOrgId());
        itmvo.setNamedWhereClauseParam("bindItmId", getItmId());
        itmvo.executeQuery();
        Row [] rr1=itmvo.getAllRowsInRange();
        if(rr1.length>0){
            itmName=rr1[0].getAttribute("ItmDesc").toString();
        }
        
        }
        return itmName;
        //return (String) getAttributeInternal(TRANSITMNM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransItmNm.
     * @param value value to set the  TransItmNm
     */
    public void setTransItmNm(String value) {
        setAttributeInternal(TRANSITMNM, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransItmQty.
     * @return the TransItmQty
     */
    public Number getTransItmQty() {
        return (Number) getAttributeInternal(TRANSITMQTY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransItmQty.
     * @param value value to set the  TransItmQty
     */
    public void setTransItmQty(Number value) {
        if(value==null){
            value =new Number(0);
        }
        setAttributeInternal(TRANSITMQTY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TranHoOrgId.
     * @return the TranHoOrgId
     */
    public String getTranHoOrgId() {
       return resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
       // return (String) getAttributeInternal(TRANHOORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TranHoOrgId.
     * @param value value to set the  TranHoOrgId
     */
    public void setTranHoOrgId(String value) {
        setAttributeInternal(TRANHOORGID, value);
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
