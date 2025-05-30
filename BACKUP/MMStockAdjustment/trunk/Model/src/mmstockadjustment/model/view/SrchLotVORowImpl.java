package mmstockadjustment.model.view;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.sql.Timestamp;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import mmstockadjustment.model.service.MMStockAdjustmentAMImpl;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Sep 11 15:15:13 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class SrchLotVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CldId {
            public Object get(SrchLotVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(SrchLotVORowImpl obj, Object value) {
                obj.setCldId((String)value);
            }
        }
        ,
        ExpiryDt {
            public Object get(SrchLotVORowImpl obj) {
                return obj.getExpiryDt();
            }

            public void put(SrchLotVORowImpl obj, Object value) {
                obj.setExpiryDt((Timestamp)value);
            }
        }
        ,
        ItmId {
            public Object get(SrchLotVORowImpl obj) {
                return obj.getItmId();
            }

            public void put(SrchLotVORowImpl obj, Object value) {
                obj.setItmId((String)value);
            }
        }
        ,
        ItmUomBs {
            public Object get(SrchLotVORowImpl obj) {
                return obj.getItmUomBs();
            }

            public void put(SrchLotVORowImpl obj, Object value) {
                obj.setItmUomBs((String)value);
            }
        }
        ,
        LotId {
            public Object get(SrchLotVORowImpl obj) {
                return obj.getLotId();
            }

            public void put(SrchLotVORowImpl obj, Object value) {
                obj.setLotId((String)value);
            }
        }
        ,
        OrgId {
            public Object get(SrchLotVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(SrchLotVORowImpl obj, Object value) {
                obj.setOrgId((String)value);
            }
        }
        ,
        SlocId {
            public Object get(SrchLotVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(SrchLotVORowImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        TotStk {
            public Object get(SrchLotVORowImpl obj) {
                return obj.getTotStk();
            }

            public void put(SrchLotVORowImpl obj, Object value) {
                obj.setTotStk((BigDecimal)value);
            }
        }
        ,
        UsrIdMod {
            public Object get(SrchLotVORowImpl obj) {
                return obj.getUsrIdMod();
            }

            public void put(SrchLotVORowImpl obj, Object value) {
                obj.setUsrIdMod((Long)value);
            }
        }
        ,
        UsrIdModDt {
            public Object get(SrchLotVORowImpl obj) {
                return obj.getUsrIdModDt();
            }

            public void put(SrchLotVORowImpl obj, Object value) {
                obj.setUsrIdModDt((Timestamp)value);
            }
        }
        ,
        WarrantyDt {
            public Object get(SrchLotVORowImpl obj) {
                return obj.getWarrantyDt();
            }

            public void put(SrchLotVORowImpl obj, Object value) {
                obj.setWarrantyDt((Timestamp)value);
            }
        }
        ,
        WhId {
            public Object get(SrchLotVORowImpl obj) {
                return obj.getWhId();
            }

            public void put(SrchLotVORowImpl obj, Object value) {
                obj.setWhId((String)value);
            }
        }
        ,
        AdjtQty {
            public Object get(SrchLotVORowImpl obj) {
                return obj.getAdjtQty();
            }

            public void put(SrchLotVORowImpl obj, Object value) {
                obj.setAdjtQty((BigDecimal)value);
            }
        }
        ,
        ItmDesc {
            public Object get(SrchLotVORowImpl obj) {
                return obj.getItmDesc();
            }

            public void put(SrchLotVORowImpl obj, Object value) {
                obj.setItmDesc((String)value);
            }
        }
        ,
        ITMVO1 {
            public Object get(SrchLotVORowImpl obj) {
                return obj.getITMVO1();
            }

            public void put(SrchLotVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(SrchLotVORowImpl object);

        public abstract void put(SrchLotVORowImpl object, Object value);

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
    public static final int EXPIRYDT = AttributesEnum.ExpiryDt.index();
    public static final int ITMID = AttributesEnum.ItmId.index();
    public static final int ITMUOMBS = AttributesEnum.ItmUomBs.index();
    public static final int LOTID = AttributesEnum.LotId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int TOTSTK = AttributesEnum.TotStk.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int WARRANTYDT = AttributesEnum.WarrantyDt.index();
    public static final int WHID = AttributesEnum.WhId.index();
    public static final int ADJTQTY = AttributesEnum.AdjtQty.index();
    public static final int ITMDESC = AttributesEnum.ItmDesc.index();
    public static final int ITMVO1 = AttributesEnum.ITMVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public SrchLotVORowImpl() {
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
     * Gets the attribute value for the calculated attribute ExpiryDt.
     * @return the ExpiryDt
     */
    public Timestamp getExpiryDt() {
        return (Timestamp) getAttributeInternal(EXPIRYDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ExpiryDt.
     * @param value value to set the  ExpiryDt
     */
    public void setExpiryDt(Timestamp value) {
        setAttributeInternal(EXPIRYDT, value);
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
     * Gets the attribute value for the calculated attribute UsrIdMod.
     * @return the UsrIdMod
     */
    public Long getUsrIdMod() {
        return (Long) getAttributeInternal(USRIDMOD);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrIdMod.
     * @param value value to set the  UsrIdMod
     */
    public void setUsrIdMod(Long value) {
        setAttributeInternal(USRIDMOD, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrIdModDt.
     * @return the UsrIdModDt
     */
    public Timestamp getUsrIdModDt() {
        return (Timestamp) getAttributeInternal(USRIDMODDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrIdModDt.
     * @param value value to set the  UsrIdModDt
     */
    public void setUsrIdModDt(Timestamp value) {
        setAttributeInternal(USRIDMODDT, value);
    }


    /**
     * Gets the attribute value for the calculated attribute WarrantyDt.
     * @return the WarrantyDt
     */
    public Timestamp getWarrantyDt() {
        return (Timestamp) getAttributeInternal(WARRANTYDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute WarrantyDt.
     * @param value value to set the  WarrantyDt
     */
    public void setWarrantyDt(Timestamp value) {
        setAttributeInternal(WARRANTYDT, value);
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
     * Gets the attribute value for the calculated attribute AdjtQty.
     * @return the AdjtQty
     */
    public BigDecimal getAdjtQty() {
        
       MMStockAdjustmentAMImpl am = (MMStockAdjustmentAMImpl)resolvElDC("MMStockAdjustmentAMDataControl");
        ViewObjectImpl vo = am.getSTKADJTLOT1();
        
        RowQualifier rq = new RowQualifier(vo);
        rq.setWhereClause("LotId ='"+getLotId()+"' AND ItmId = '"+getItmId()+"' AND OrgId = '"+getOrgId()+"' AND CldId = '"+getCldId()+"' AND SlocId = "+getSlocId()+"");
        Row []rows=vo.getFilteredRows(rq);
       if(rows != null && rows.length == 1)
       { return (BigDecimal)rows[0].getAttribute("AdjtQty");}
       
       else  
       {return (BigDecimal) getAttributeInternal(ADJTQTY);}
      }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute AdjtQty.
     * @param value value to set the  AdjtQty
     */
    public void setAdjtQty(BigDecimal value) {
        setAttributeInternal(ADJTQTY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ItmDesc.
     * @return the ItmDesc
     */
    public String getItmDesc() {
        MMStockAdjustmentAMImpl am =  (MMStockAdjustmentAMImpl)resolvElDC("MMStockAdjustmentAMDataControl");
        ViewObjectImpl voItm = am.getITM1();
        Integer fyId = (Integer)am.getSTKADJT1().getCurrentRow().getAttribute("FyId");
        
        RowQualifier rw = new RowQualifier(voItm);
        rw.setWhereClause("OrgId = '"+getOrgId()+"' AND ItmId = '"+getItmId()+"' AND CldId = '"+getCldId()+"' AND SlocId = "+getSlocId()+" AND WhId = '"+getWhId()+"' AND FyId = "+fyId+"");
        
        Row norows[] =voItm.getFilteredRows(rw);
        if(norows!=null && norows.length >= 1)
        { return (String)norows[0].getAttribute("ItmDesc"); }
        return (String) getAttributeInternal(ITMDESC);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ItmDesc.
     * @param value value to set the  ItmDesc
     */
    public void setItmDesc(String value) {
        setAttributeInternal(ITMDESC, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> ITMVO1.
     */
    public RowSet getITMVO1() {
        return (RowSet)getAttributeInternal(ITMVO1);
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
    
    
    public static Object evaluateEL(String el) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ELContext elContext = facesContext.getELContext();
    ExpressionFactory expressionFactory =
    facesContext.getApplication().getExpressionFactory();
    ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);
    return exp.getValue(elContext);
    }
    
    public Object resolvElDC(String data) {
    FacesContext fc = FacesContext.getCurrentInstance();
    Application app = fc.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = fc.getELContext();
    ValueExpression valueExp =elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
    return valueExp.getValue(elContext);
    }
}
