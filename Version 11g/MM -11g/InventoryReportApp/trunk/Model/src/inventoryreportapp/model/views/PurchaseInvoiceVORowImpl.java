package inventoryreportapp.model.views;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Apr 24 10:46:54 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PurchaseInvoiceVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        Dummy {
            public Object get(PurchaseInvoiceVORowImpl obj) {
                return obj.getDummy();
            }

            public void put(PurchaseInvoiceVORowImpl obj, Object value) {
                obj.setDummy((String)value);
            }
        }
        ,
        CldId {
            public Object get(PurchaseInvoiceVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(PurchaseInvoiceVORowImpl obj, Object value) {
                obj.setCldId((String)value);
            }
        }
        ,
        SlocId {
            public Object get(PurchaseInvoiceVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(PurchaseInvoiceVORowImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        OrgId {
            public Object get(PurchaseInvoiceVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(PurchaseInvoiceVORowImpl obj, Object value) {
                obj.setOrgId((String)value);
            }
        }
        ,
        HoOrgId {
            public Object get(PurchaseInvoiceVORowImpl obj) {
                return obj.getHoOrgId();
            }

            public void put(PurchaseInvoiceVORowImpl obj, Object value) {
                obj.setHoOrgId((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(PurchaseInvoiceVORowImpl object);

        public abstract void put(PurchaseInvoiceVORowImpl object, Object value);

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
    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PurchaseInvoiceVORowImpl() {
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
     * Gets the attribute value for the calculated attribute CldId.
     * @return the CldId
     */
    public String getCldId() {
        String cldid;
        if(resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}")!=null){
            cldid=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            return cldid;
        }
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
        Integer sloc;
        if(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}")!=null){
         sloc =Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
            return sloc;
        }
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
        String hoorgid;
        if(resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}")!=null){
         hoorgid =resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
            return hoorgid;
        }
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
     * Gets the attribute value for the calculated attribute HoOrgId.
     * @return the HoOrgId
     */
    public String getHoOrgId() {
        String hoorgid;
        if(resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}")!=null){
         hoorgid =resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            return hoorgid;
        }
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
    
    
    private Object resolvEl(String data){
    FacesContext fc = FacesContext.getCurrentInstance();
    Application app = fc.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = fc.getELContext();
    ValueExpression valueExp = elFactory.createValueExpression(elContext, data,
    Object.class);
    Object Message=valueExp.getValue(elContext);
    return Message;
    }
}
