package slsreptemplateapp.model.views;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.RowSet;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Mar 03 18:45:00 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class SearchTemplateVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        TmpName {
            public Object get(SearchTemplateVORowImpl obj) {
                return obj.getTmpName();
            }

            public void put(SearchTemplateVORowImpl obj, Object value) {
                obj.setTmpName((String)value);
            }
        }
        ,
        TmpId {
            public Object get(SearchTemplateVORowImpl obj) {
                return obj.getTmpId();
            }

            public void put(SearchTemplateVORowImpl obj, Object value) {
                obj.setTmpId((String)value);
            }
        }
        ,
        CldId {
            public Object get(SearchTemplateVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(SearchTemplateVORowImpl obj, Object value) {
                obj.setCldId((String)value);
            }
        }
        ,
        HoOrgId {
            public Object get(SearchTemplateVORowImpl obj) {
                return obj.getHoOrgId();
            }

            public void put(SearchTemplateVORowImpl obj, Object value) {
                obj.setHoOrgId((String)value);
            }
        }
        ,
        SlocId {
            public Object get(SearchTemplateVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(SearchTemplateVORowImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        OrgId {
            public Object get(SearchTemplateVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(SearchTemplateVORowImpl obj, Object value) {
                obj.setOrgId((String)value);
            }
        }
        ,
        SlsSearchTmplVO1 {
            public Object get(SearchTemplateVORowImpl obj) {
                return obj.getSlsSearchTmplVO1();
            }

            public void put(SearchTemplateVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(SearchTemplateVORowImpl object);

        public abstract void put(SearchTemplateVORowImpl object, Object value);

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


    public static final int TMPNAME = AttributesEnum.TmpName.index();
    public static final int TMPID = AttributesEnum.TmpId.index();
    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int SLSSEARCHTMPLVO1 = AttributesEnum.SlsSearchTmplVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public SearchTemplateVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute TmpName.
     * @return the TmpName
     */
    public String getTmpName() {
        return (String) getAttributeInternal(TMPNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TmpName.
     * @param value value to set the  TmpName
     */
    public void setTmpName(String value) {
        setAttributeInternal(TMPNAME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TmpId.
     * @return the TmpId
     */
    public String getTmpId() {
        return (String) getAttributeInternal(TMPID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TmpId.
     * @param value value to set the  TmpId
     */
    public void setTmpId(String value) {
        setAttributeInternal(TMPID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CldId.
     * @return the CldId
     */
    public String getCldId() {
        
        String str = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        if(str != null)
            return str;
        
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
     * Gets the attribute value for the calculated attribute HoOrgId.
     * @return the HoOrgId
     */
    public String getHoOrgId() {
        String str = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        if(str != null)
            return str;
        return (String) getAttributeInternal(HOORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute HoOrgId.
     * @param value value to set the  HoOrgId
     */
    public void setHoOrgId(String value) {
        setAttributeInternal(HOORGID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SlocId.
     * @return the SlocId
     */
    public Integer getSlocId() {
        String str = resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}");
        if(str != null)
            return (Integer.parseInt(str));
        
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
         String str = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        if(str != null)
            return str; 
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
     * Gets the view accessor <code>RowSet</code> SlsSearchTmplVO1.
     */
    public RowSet getSlsSearchTmplVO1() {
        return (RowSet)getAttributeInternal(SLSSEARCHTMPLVO1);
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
    
    public String resolvEl(String data){
    FacesContext fc = FacesContext.getCurrentInstance();
    Application app = fc.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = fc.getELContext();
    ValueExpression valueExp = elFactory.createValueExpression(elContext, data,
    Object.class);
    String Message=valueExp.getValue(elContext).toString();
    return Message;
    }
}
