package mmstockadjustment.model.view;

import java.math.BigDecimal;

import java.util.HashSet;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import mmstockadjustment.model.service.MMStockAdjustmentAMImpl;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.ViewObject;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Sep 16 12:50:17 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class transLotVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        Dummy {
            public Object get(transLotVORowImpl obj) {
                return obj.getDummy();
            }

            public void put(transLotVORowImpl obj, Object value) {
                obj.setDummy((String)value);
            }
        }
        ,
        LotId {
            public Object get(transLotVORowImpl obj) {
                return obj.getLotId();
            }

            public void put(transLotVORowImpl obj, Object value) {
                obj.setLotId((String)value);
            }
        }
        ,
        AdjtQty {
            public Object get(transLotVORowImpl obj) {
                return obj.getAdjtQty();
            }

            public void put(transLotVORowImpl obj, Object value) {
                obj.setAdjtQty((BigDecimal)value);
            }
        }
        ,
        ItemId {
            public Object get(transLotVORowImpl obj) {
                return obj.getItemId();
            }

            public void put(transLotVORowImpl obj, Object value) {
                obj.setItemId((String)value);
            }
        }
        ,
        BinId {
            public Object get(transLotVORowImpl obj) {
                return obj.getBinId();
            }

            public void put(transLotVORowImpl obj, Object value) {
                obj.setBinId((String)value);
            }
        }
        ,
        SrId {
            public Object get(transLotVORowImpl obj) {
                return obj.getSrId();
            }

            public void put(transLotVORowImpl obj, Object value) {
                obj.setSrId((String)value);
            }
        }
        ,
        CldId {
            public Object get(transLotVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(transLotVORowImpl obj, Object value) {
                obj.setCldId((String)value);
            }
        }
        ,
        SlocId {
            public Object get(transLotVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(transLotVORowImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        OrgId {
            public Object get(transLotVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(transLotVORowImpl obj, Object value) {
                obj.setOrgId((String)value);
            }
        }
        ,
        WhId {
            public Object get(transLotVORowImpl obj) {
                return obj.getWhId();
            }

            public void put(transLotVORowImpl obj, Object value) {
                obj.setWhId((String)value);
            }
        }
        ,
        docId {
            public Object get(transLotVORowImpl obj) {
                return obj.getdocId();
            }

            public void put(transLotVORowImpl obj, Object value) {
                obj.setdocId((String)value);
            }
        }
        ,
        MmStkSummLotVO1 {
            public Object get(transLotVORowImpl obj) {
                return obj.getMmStkSummLotVO1();
            }

            public void put(transLotVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        STKADJTITMVO1 {
            public Object get(transLotVORowImpl obj) {
                return obj.getSTKADJTITMVO1();
            }

            public void put(transLotVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        MmStkSummBinVO1 {
            public Object get(transLotVORowImpl obj) {
                return obj.getMmStkSummBinVO1();
            }

            public void put(transLotVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        MmStkSummSrVO1 {
            public Object get(transLotVORowImpl obj) {
                return obj.getMmStkSummSrVO1();
            }

            public void put(transLotVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(transLotVORowImpl object);

        public abstract void put(transLotVORowImpl object, Object value);

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
    public static final int DUMMY = AttributesEnum.Dummy.index();
    public static final int LOTID = AttributesEnum.LotId.index();
    public static final int ADJTQTY = AttributesEnum.AdjtQty.index();
    public static final int ITEMID = AttributesEnum.ItemId.index();
    public static final int BINID = AttributesEnum.BinId.index();
    public static final int SRID = AttributesEnum.SrId.index();
    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int WHID = AttributesEnum.WhId.index();
    public static final int DOCID = AttributesEnum.docId.index();
    public static final int MMSTKSUMMLOTVO1 = AttributesEnum.MmStkSummLotVO1.index();
    public static final int STKADJTITMVO1 = AttributesEnum.STKADJTITMVO1.index();
    public static final int MMSTKSUMMBINVO1 = AttributesEnum.MmStkSummBinVO1.index();
    public static final int MMSTKSUMMSRVO1 = AttributesEnum.MmStkSummSrVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public transLotVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute Dummy.
     * @return the Dummy
     */
    public String getDummy() {
        return (String) getAttributeInternal(DUMMY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Dummy.
     * @param value value to set the  Dummy
     */
    public void setDummy(String value) {
        setAttributeInternal(DUMMY, value);
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
     * Gets the attribute value for the calculated attribute AdjtQty.
     * @return the AdjtQty
     */
    public BigDecimal getAdjtQty() {
        return (BigDecimal) getAttributeInternal(ADJTQTY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute AdjtQty.
     * @param value value to set the  AdjtQty
     */
    public void setAdjtQty(BigDecimal value) {
        setAttributeInternal(ADJTQTY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ItemId.
     * @return the ItemId
     */
    public String getItemId() {
        return (String) getAttributeInternal(ITEMID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ItemId.
     * @param value value to set the  ItemId
     */
    public void setItemId(String value) {
        setAttributeInternal(ITEMID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute BinId.
     * @return the BinId
     */
    public String getBinId() {
        return (String) getAttributeInternal(BINID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute BinId.
     * @param value value to set the  BinId
     */
    public void setBinId(String value) {
        setAttributeInternal(BINID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SrId.
     * @return the SrId
     */
    public String getSrId() {
        return (String) getAttributeInternal(SRID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SrId.
     * @param value value to set the  SrId
     */
    public void setSrId(String value) {
        setAttributeInternal(SRID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CldId.
     * @return the CldId
     */
    public String getCldId() {
        String cldId=(String)evaluateEL("#{pageFlowScope.GLBL_APP_CLD_ID}");
      
        if(cldId != null)
        {return cldId;}
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
        Integer slocId =Integer.parseInt(evaluateEL("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
       
        if(slocId !=null)
        {return slocId;}
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
        String orgId=(String)evaluateEL("#{pageFlowScope.GLBL_APP_USR_ORG}");
         if(orgId != null)
        {return orgId; }
        
       
        
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
        MMStockAdjustmentAMImpl am =  (MMStockAdjustmentAMImpl)resolvElDC("MMStockAdjustmentAMDataControl");
        ViewObject voStk = am.getSTKADJT1();
        String whId= (String)voStk.getCurrentRow().getAttribute("WhId");
        
        if(whId != null)
        {return whId;}
        
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
     * Gets the attribute value for the calculated attribute docId.
     * @return the docId
     */
    public String getdocId() {
        
        MMStockAdjustmentAMImpl am =  (MMStockAdjustmentAMImpl)resolvElDC("MMStockAdjustmentAMDataControl");
        ViewObject voStk = am.getSTKADJT1();
        String docId= (String)voStk.getCurrentRow().getAttribute("DocId");
    
        if(docId != null)
        {return docId;}
        return (String) getAttributeInternal(DOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute docId.
     * @param value value to set the  docId
     */
    public void setdocId(String value) {
        setAttributeInternal(DOCID, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> MmStkSummLotVO1.
     */
    public RowSet getMmStkSummLotVO1() {
        return (RowSet)getAttributeInternal(MMSTKSUMMLOTVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> STKADJTITMVO1.
     */
    public RowSet getSTKADJTITMVO1() {
        
        MMStockAdjustmentAMImpl am =  (MMStockAdjustmentAMImpl)resolvElDC("MMStockAdjustmentAMDataControl");
        ViewObject voStk = am.getSTKADJT1();
        String orgId=(String)evaluateEL("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String cldId=(String)evaluateEL("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer slocId =Integer.parseInt(evaluateEL("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String whId= (String)voStk.getCurrentRow().getAttribute("WhId");
        String docId =(String)voStk.getCurrentRow().getAttribute("DocId"); 
        RowSet rs =(RowSet)getAttributeInternal(STKADJTITMVO1);
         rs.getRowCount();
         rs.setNamedWhereClauseParam("bindCldID", cldId);
         rs.setNamedWhereClauseParam("bindDocID", docId);
         rs.setNamedWhereClauseParam("bindOrgID", orgId);
         rs.setNamedWhereClauseParam("bindSlocID", slocId);
         rs.setNamedWhereClauseParam("bindWHID", whId);
         rs.getRowCount();
         rs.executeQuery();
        return rs;
       // ((RowSet)getAttributeInternal(STKADJTITMVO1)).executeQuery();
      //  System.out.println("Rowe count "+((RowSet)getAttributeInternal(STKADJTITMVO1)).getRowCount());
     //return (RowSet)getAttributeInternal(STKADJTITMVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> MmStkSummBinVO1.
     */
    public RowSet getMmStkSummBinVO1() {
        return (RowSet)getAttributeInternal(MMSTKSUMMBINVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> MmStkSummSrVO1.
     */
    public RowSet getMmStkSummSrVO1() {
        return (RowSet)getAttributeInternal(MMSTKSUMMSRVO1);
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
    ValueExpression valueExp =
    elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
    return valueExp.getValue(elContext);
    }
}
