package mmsoctktakeapp.model.view;

import java.math.BigDecimal;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import mmsoctktakeapp.model.services.MMStkTakeAMImpl;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Sep 11 11:07:56 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MmStkSummLotVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CldId {
            public Object get(MmStkSummLotVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(MmStkSummLotVORowImpl obj, Object value) {
                obj.setCldId((String)value);
            }
        }
        ,
        SlocId {
            public Object get(MmStkSummLotVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(MmStkSummLotVORowImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        OrgId {
            public Object get(MmStkSummLotVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(MmStkSummLotVORowImpl obj, Object value) {
                obj.setOrgId((String)value);
            }
        }
        ,
        WhId {
            public Object get(MmStkSummLotVORowImpl obj) {
                return obj.getWhId();
            }

            public void put(MmStkSummLotVORowImpl obj, Object value) {
                obj.setWhId((String)value);
            }
        }
        ,
        ItmId {
            public Object get(MmStkSummLotVORowImpl obj) {
                return obj.getItmId();
            }

            public void put(MmStkSummLotVORowImpl obj, Object value) {
                obj.setItmId((String)value);
            }
        }
        ,
        LotId {
            public Object get(MmStkSummLotVORowImpl obj) {
                return obj.getLotId();
            }

            public void put(MmStkSummLotVORowImpl obj, Object value) {
                obj.setLotId((String)value);
            }
        }
        ,
        ItmUomBs {
            public Object get(MmStkSummLotVORowImpl obj) {
                return obj.getItmUomBs();
            }

            public void put(MmStkSummLotVORowImpl obj, Object value) {
                obj.setItmUomBs((String)value);
            }
        }
        ,
        TotStk {
            public Object get(MmStkSummLotVORowImpl obj) {
                return obj.getTotStk();
            }

            public void put(MmStkSummLotVORowImpl obj, Object value) {
                obj.setTotStk((BigDecimal)value);
            }
        }
        ,
        IsSelected {
            public Object get(MmStkSummLotVORowImpl obj) {
                return obj.getIsSelected();
            }

            public void put(MmStkSummLotVORowImpl obj, Object value) {
                obj.setIsSelected((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(MmStkSummLotVORowImpl object);

        public abstract void put(MmStkSummLotVORowImpl object, Object value);

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
    public static final int ITMID = AttributesEnum.ItmId.index();
    public static final int LOTID = AttributesEnum.LotId.index();
    public static final int ITMUOMBS = AttributesEnum.ItmUomBs.index();
    public static final int TOTSTK = AttributesEnum.TotStk.index();
    public static final int ISSELECTED = AttributesEnum.IsSelected.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MmStkSummLotVORowImpl() {
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
     * Gets the attribute value for the calculated attribute WhId.
     * @return the WhId
     */
    public String getWhId() {
        return (String) getAttributeInternal(WHID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute WhId.
     * @param value value to set the  WhId
     */
    public void setWhId(String value) {
        setAttributeInternal(WHID, value);
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
     * Gets the attribute value for the calculated attribute ItmUomBs.
     * @return the ItmUomBs
     */
    public String getItmUomBs() {
        return (String) getAttributeInternal(ITMUOMBS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ItmUomBs.
     * @param value value to set the  ItmUomBs
     */
    public void setItmUomBs(String value) {
        setAttributeInternal(ITMUOMBS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TotStk.
     * @return the TotStk
     */
    public BigDecimal getTotStk() {
        return (BigDecimal) getAttributeInternal(TOTSTK);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TotStk.
     * @param value value to set the  TotStk
     */
    public void setTotStk(BigDecimal value) {
        setAttributeInternal(TOTSTK, value);
    }

    /**
     * Gets the attribute value for the calculated attribute IsSelected.
     * @return the IsSelected
     */
    public String getIsSelected() {
        String Lot=getLotId();
        MMStkTakeAMImpl am=(MMStkTakeAMImpl)resolvElDC("MMStkTakeAMDataControl");
        ViewObjectImpl votakelot=am.getMmStkTakeLot3();
        ViewObject votake=am.getMmStkTake1();
        String DocId = (String)votake.getCurrentRow().getAttribute("DocId");
        RowQualifier rq=new RowQualifier(votakelot);
        rq.setWhereClause("DocId='"+DocId+"' AND LotId='"+Lot+"'");
        Row r[]=votakelot.getFilteredRows(rq);
        if(r.length>0)
            return "Y";
        else
            return "N";
        //return (String) getAttributeInternal(ISSELECTED);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute IsSelected.
     * @param value value to set the  IsSelected
     */
    public void setIsSelected(String value) {
        setAttributeInternal(ISSELECTED, value);
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
