package appitemprofile.model.views;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.context.FacesContext;

import oracle.jbo.RowSet;
import oracle.jbo.domain.Date;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Dec 19 17:05:48 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class TransientViewForListVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        Grouplist {
            public Object get(TransientViewForListVORowImpl obj) {
                return obj.getGrouplist();
            }

            public void put(TransientViewForListVORowImpl obj, Object value) {
                obj.setGrouplist((String)value);
            }
        }
        ,
        Uomlist {
            public Object get(TransientViewForListVORowImpl obj) {
                return obj.getUomlist();
            }

            public void put(TransientViewForListVORowImpl obj, Object value) {
                obj.setUomlist((String)value);
            }
        }
        ,
        TranFromDate {
            public Object get(TransientViewForListVORowImpl obj) {
                return obj.getTranFromDate();
            }

            public void put(TransientViewForListVORowImpl obj, Object value) {
                obj.setTranFromDate((Date)value);
            }
        }
        ,
        TranToDate {
            public Object get(TransientViewForListVORowImpl obj) {
                return obj.getTranToDate();
            }

            public void put(TransientViewForListVORowImpl obj, Object value) {
                obj.setTranToDate((Date)value);
            }
        }
        ,
        cldId {
            public Object get(TransientViewForListVORowImpl obj) {
                return obj.getcldId();
            }

            public void put(TransientViewForListVORowImpl obj, Object value) {
                obj.setcldId((String)value);
            }
        }
        ,
        slocId {
            public Object get(TransientViewForListVORowImpl obj) {
                return obj.getslocId();
            }

            public void put(TransientViewForListVORowImpl obj, Object value) {
                obj.setslocId((Integer)value);
            }
        }
        ,
        hoOrgId {
            public Object get(TransientViewForListVORowImpl obj) {
                return obj.gethoOrgId();
            }

            public void put(TransientViewForListVORowImpl obj, Object value) {
                obj.sethoOrgId((String)value);
            }
        }
        ,
        ViewUOMLOV {
            public Object get(TransientViewForListVORowImpl obj) {
                return obj.getViewUOMLOV();
            }

            public void put(TransientViewForListVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ViewItemGrpLOV {
            public Object get(TransientViewForListVORowImpl obj) {
                return obj.getViewItemGrpLOV();
            }

            public void put(TransientViewForListVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(TransientViewForListVORowImpl object);

        public abstract void put(TransientViewForListVORowImpl object, Object value);

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


    public static final int GROUPLIST = AttributesEnum.Grouplist.index();
    public static final int UOMLIST = AttributesEnum.Uomlist.index();
    public static final int TRANFROMDATE = AttributesEnum.TranFromDate.index();
    public static final int TRANTODATE = AttributesEnum.TranToDate.index();
    public static final int CLDID = AttributesEnum.cldId.index();
    public static final int SLOCID = AttributesEnum.slocId.index();
    public static final int HOORGID = AttributesEnum.hoOrgId.index();
    public static final int VIEWUOMLOV = AttributesEnum.ViewUOMLOV.index();
    public static final int VIEWITEMGRPLOV = AttributesEnum.ViewItemGrpLOV.index();

    /**
     * This is the default constructor (do not remove).
     */
    public TransientViewForListVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute Grouplist.
     * @return the Grouplist
     */
    public String getGrouplist() {
        return (String) getAttributeInternal(GROUPLIST);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Grouplist.
     * @param value value to set the  Grouplist
     */
    public void setGrouplist(String value) {
        setAttributeInternal(GROUPLIST, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Uomlist.
     * @return the Uomlist
     */
    public String getUomlist() {
        return (String) getAttributeInternal(UOMLIST);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Uomlist.
     * @param value value to set the  Uomlist
     */
    public void setUomlist(String value) {
        setAttributeInternal(UOMLIST, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TranFromDate.
     * @return the TranFromDate
     */
    public Date getTranFromDate() {
        return (Date) getAttributeInternal(TRANFROMDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TranFromDate.
     * @param value value to set the  TranFromDate
     */
    public void setTranFromDate(Date value) {
        setAttributeInternal(TRANFROMDATE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TranToDate.
     * @return the TranToDate
     */
    public Date getTranToDate() {
        return (Date) getAttributeInternal(TRANTODATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TranToDate.
     * @param value value to set the  TranToDate
     */
    public void setTranToDate(Date value) {
        setAttributeInternal(TRANTODATE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute cldId.
     * @return the cldId
     */
    public String getcldId() {
        String cldId = evaluateEL("#{pageFlowScope.GLBL_APP_CLD_ID}");
        
      //  System.out.println("Cld : "+cldId);
        
       if(cldId !=null)
        {return cldId;}
         return (String) getAttributeInternal(CLDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute cldId.
     * @param value value to set the  cldId
     */
    public void setcldId(String value) {
        setAttributeInternal(CLDID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute slocId.
     * @return the slocId
     */
    public Integer getslocId() {
        Integer slocId = Integer.parseInt(evaluateEL("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        
     //   System.out.println("slocId : "+slocId);
        if(slocId !=null)
        {return slocId;}
        return (Integer) getAttributeInternal(SLOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute slocId.
     * @param value value to set the  slocId
     */
    public void setslocId(Integer value) {
        setAttributeInternal(SLOCID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute hoOrgId.
     * @return the hoOrgId
     */
    public String gethoOrgId() {
        String hoOrgId = evaluateEL("#{pageFlowScope.GLBL_HO_ORG_ID}");
        
       // System.out.println("hoOrgId : "+hoOrgId);
        
        if(hoOrgId !=null)
        {return hoOrgId;}
        return (String) getAttributeInternal(HOORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute hoOrgId.
     * @param value value to set the  hoOrgId
     */
    public void sethoOrgId(String value) {
        setAttributeInternal(HOORGID, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> ViewUOMLOV.
     */
    public RowSet getViewUOMLOV() {
        return (RowSet)getAttributeInternal(VIEWUOMLOV);
    }

    /**
     * Gets the view accessor <code>RowSet</code> ViewItemGrpLOV.
     */
    public RowSet getViewItemGrpLOV() {
        return (RowSet)getAttributeInternal(VIEWITEMGRPLOV);
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
    
    public static String evaluateEL(String el) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ELContext elContext = facesContext.getELContext();
    ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
    ValueExpression exp =  expressionFactory.createValueExpression(elContext, el, Object.class);
        String retVal = exp.getValue(elContext).toString();
    return retVal;
    }
}
