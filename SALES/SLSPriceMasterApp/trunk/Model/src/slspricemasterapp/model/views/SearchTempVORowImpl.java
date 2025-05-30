package slspricemasterapp.model.views;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.jbo.RowSet;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Sep 29 21:19:09 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class SearchTempVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        Dummy {
            public Object get(SearchTempVORowImpl obj) {
                return obj.getDummy();
            }

            public void put(SearchTempVORowImpl obj, Object value) {
                obj.setDummy((String)value);
            }
        }
        ,
        SlocIdTrans {
            public Object get(SearchTempVORowImpl obj) {
                return obj.getSlocIdTrans();
            }

            public void put(SearchTempVORowImpl obj, Object value) {
                obj.setSlocIdTrans((Integer)value);
            }
        }
        ,
        CldIdTrans {
            public Object get(SearchTempVORowImpl obj) {
                return obj.getCldIdTrans();
            }

            public void put(SearchTempVORowImpl obj, Object value) {
                obj.setCldIdTrans((String)value);
            }
        }
        ,
        OrgIdTrans {
            public Object get(SearchTempVORowImpl obj) {
                return obj.getOrgIdTrans();
            }

            public void put(SearchTempVORowImpl obj, Object value) {
                obj.setOrgIdTrans((String)value);
            }
        }
        ,
        HoOrgIdTrans {
            public Object get(SearchTempVORowImpl obj) {
                return obj.getHoOrgIdTrans();
            }

            public void put(SearchTempVORowImpl obj, Object value) {
                obj.setHoOrgIdTrans((String)value);
            }
        }
        ,
        ItmNmTrans {
            public Object get(SearchTempVORowImpl obj) {
                return obj.getItmNmTrans();
            }

            public void put(SearchTempVORowImpl obj, Object value) {
                obj.setItmNmTrans((String)value);
            }
        }
        ,
        ItmIdTrans {
            public Object get(SearchTempVORowImpl obj) {
                return obj.getItmIdTrans();
            }

            public void put(SearchTempVORowImpl obj, Object value) {
                obj.setItmIdTrans((String)value);
            }
        }
        ,
        CatgTrans {
            public Object get(SearchTempVORowImpl obj) {
                return obj.getCatgTrans();
            }

            public void put(SearchTempVORowImpl obj, Object value) {
                obj.setCatgTrans((Integer)value);
            }
        }
        ,
        EoNmTrans {
            public Object get(SearchTempVORowImpl obj) {
                return obj.getEoNmTrans();
            }

            public void put(SearchTempVORowImpl obj, Object value) {
                obj.setEoNmTrans((String)value);
            }
        }
        ,
        EoIdTrans {
            public Object get(SearchTempVORowImpl obj) {
                return obj.getEoIdTrans();
            }

            public void put(SearchTempVORowImpl obj, Object value) {
                obj.setEoIdTrans((String)value);
            }
        }
        ,
        LovItmIdVo1 {
            public Object get(SearchTempVORowImpl obj) {
                return obj.getLovItmIdVo1();
            }

            public void put(SearchTempVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CatagoryTypeVO1 {
            public Object get(SearchTempVORowImpl obj) {
                return obj.getCatagoryTypeVO1();
            }

            public void put(SearchTempVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovEoIdVo1 {
            public Object get(SearchTempVORowImpl obj) {
                return obj.getLovEoIdVo1();
            }

            public void put(SearchTempVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(SearchTempVORowImpl object);

        public abstract void put(SearchTempVORowImpl object, Object value);

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
    public static final int SLOCIDTRANS = AttributesEnum.SlocIdTrans.index();
    public static final int CLDIDTRANS = AttributesEnum.CldIdTrans.index();
    public static final int ORGIDTRANS = AttributesEnum.OrgIdTrans.index();
    public static final int HOORGIDTRANS = AttributesEnum.HoOrgIdTrans.index();
    public static final int ITMNMTRANS = AttributesEnum.ItmNmTrans.index();
    public static final int ITMIDTRANS = AttributesEnum.ItmIdTrans.index();
    public static final int CATGTRANS = AttributesEnum.CatgTrans.index();
    public static final int EONMTRANS = AttributesEnum.EoNmTrans.index();
    public static final int EOIDTRANS = AttributesEnum.EoIdTrans.index();
    public static final int LOVITMIDVO1 = AttributesEnum.LovItmIdVo1.index();
    public static final int CATAGORYTYPEVO1 = AttributesEnum.CatagoryTypeVO1.index();
    public static final int LOVEOIDVO1 = AttributesEnum.LovEoIdVo1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public SearchTempVORowImpl() {
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
     * Gets the attribute value for the calculated attribute SlocIdTrans.
     * @return the SlocIdTrans
     */
    public Integer getSlocIdTrans() {
        return getSlocId();
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SlocIdTrans.
     * @param value value to set the  SlocIdTrans
     */
    public void setSlocIdTrans(Integer value) {
        setAttributeInternal(SLOCIDTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CldIdTrans.
     * @return the CldIdTrans
     */
    public String getCldIdTrans() {
        return getCldId().toString();
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CldIdTrans.
     * @param value value to set the  CldIdTrans
     */
    public void setCldIdTrans(String value) {
        setAttributeInternal(CLDIDTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute OrgIdTrans.
     * @return the OrgIdTrans
     */
    public String getOrgIdTrans() {
        return getOrgId().toString();
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute OrgIdTrans.
     * @param value value to set the  OrgIdTrans
     */
    public void setOrgIdTrans(String value) {
        setAttributeInternal(ORGIDTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute HoOrgIdTrans.
     * @return the HoOrgIdTrans
     */
    public String getHoOrgIdTrans() {
        return getHoOrgId().toString();
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute HoOrgIdTrans.
     * @param value value to set the  HoOrgIdTrans
     */
    public void setHoOrgIdTrans(String value) {
        setAttributeInternal(HOORGIDTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ItmNmTrans.
     * @return the ItmNmTrans
     */
    public String getItmNmTrans() {
        return (String) getAttributeInternal(ITMNMTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ItmNmTrans.
     * @param value value to set the  ItmNmTrans
     */
    public void setItmNmTrans(String value) {
        setAttributeInternal(ITMNMTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ItmIdTrans.
     * @return the ItmIdTrans
     */
    public String getItmIdTrans() {
        return (String) getAttributeInternal(ITMIDTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ItmIdTrans.
     * @param value value to set the  ItmIdTrans
     */
    public void setItmIdTrans(String value) {
        setAttributeInternal(ITMIDTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CatgTrans.
     * @return the CatgTrans
     */
    public Integer getCatgTrans() {
        return (Integer) getAttributeInternal(CATGTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CatgTrans.
     * @param value value to set the  CatgTrans
     */
    public void setCatgTrans(Integer value) {
        setAttributeInternal(CATGTRANS, value);
        setItmNmTrans(null);
        setItmIdTrans(null);
    }

    /**
     * Gets the attribute value for the calculated attribute PopUpEoNmTrans.
     * @return the PopUpEoNmTrans
     */
    public String getEoNmTrans() {
        return (String) getAttributeInternal(EONMTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute PopUpEoNmTrans.
     * @param value value to set the  PopUpEoNmTrans
     */
    public void setEoNmTrans(String value) {
        setAttributeInternal(EONMTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute EoIdTrans.
     * @return the EoIdTrans
     */
    public String getEoIdTrans() {
        return (String) getAttributeInternal(EOIDTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute EoIdTrans.
     * @param value value to set the  EoIdTrans
     */
    public void setEoIdTrans(String value) {
        setAttributeInternal(EOIDTRANS, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovItmIdVo1.
     */
    public RowSet getLovItmIdVo1() {
        return (RowSet)getAttributeInternal(LOVITMIDVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> CatagoryTypeVO1.
     */
    public RowSet getCatagoryTypeVO1() {
        return (RowSet)getAttributeInternal(CATAGORYTYPEVO1);
    }


    /**
     * Gets the view accessor <code>RowSet</code> LovEoIdVo1.
     */
    public RowSet getLovEoIdVo1() {
        return (RowSet)getAttributeInternal(LOVEOIDVO1);
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
    
    /**
     * Method for resolving the El
     */
    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = "-1";
        if (valueExp.getValue(elContext) == null) {
            FacesMessage message = new FacesMessage("There have been an error in fetching global parameters !");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            Message = valueExp.getValue(elContext).toString();
        }
        return Message;
    }

    public Integer getSlocId() {
        return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
    }

    public StringBuilder getCldId() {
        return new StringBuilder(resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
    }

    public Integer getUserId() {
        return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
    }

    public StringBuilder getOrgId() {
        return new StringBuilder(resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"));
    }

    public StringBuilder getHoOrgId() {
        return new StringBuilder(resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}"));
    }
    
}
