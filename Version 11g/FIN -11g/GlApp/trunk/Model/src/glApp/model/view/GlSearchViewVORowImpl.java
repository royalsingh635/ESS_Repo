package glApp.model.view;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.RowSet;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;


// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Jul 11 19:40:07 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class GlSearchViewVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        GlCldId {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getGlCldId();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setGlCldId((String)value);
            }
        }
        ,
        GlSlocId {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getGlSlocId();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setGlSlocId((Integer)value);
            }
        }
        ,
        GlApplInstId {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getGlApplInstId();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setGlApplInstId((Integer)value);
            }
        }
        ,
        GlOrgId {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getGlOrgId();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setGlOrgId((String)value);
            }
        }
        ,
        GlVouId {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getGlVouId();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setGlVouId((String)value);
            }
        }
        ,
        DocTxnIdDisp {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getDocTxnIdDisp();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setDocTxnIdDisp((String)value);
            }
        }
        ,
        GlVouDt {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getGlVouDt();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setGlVouDt((Date)value);
            }
        }
        ,
        GlTypeId {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getGlTypeId();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setGlTypeId((Integer)value);
            }
        }
        ,
        GlSubTypeId {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getGlSubTypeId();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setGlSubTypeId((Integer)value);
            }
        }
        ,
        GlCurrIdBs {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getGlCurrIdBs();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setGlCurrIdBs((Integer)value);
            }
        }
        ,
        GlTotAmtBs {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getGlTotAmtBs();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setGlTotAmtBs((Number)value);
            }
        }
        ,
        GlAmtDrBs {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getGlAmtDrBs();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setGlAmtDrBs((Number)value);
            }
        }
        ,
        GlAmtCrBs {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getGlAmtCrBs();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setGlAmtCrBs((Number)value);
            }
        }
        ,
        GlCurrBsDesc {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getGlCurrBsDesc();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setGlCurrBsDesc((String)value);
            }
        }
        ,
        UsrName {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getUsrName();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setUsrName((String)value);
            }
        }
        ,
        GlDesc {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getGlDesc();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setGlDesc((String)value);
            }
        }
        ,
        GlHoOrgIdTrans {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getGlHoOrgIdTrans();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setGlHoOrgIdTrans((String)value);
            }
        }
        ,
        LovOrgIdVO {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getLovOrgIdVO();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovVouIdVO {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getLovVouIdVO();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovDistVouTypVO {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getLovDistVouTypVO();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovSubTypeIdVO {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getLovSubTypeIdVO();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovCurrLatest1 {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getLovCurrLatest1();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovCogIdVO1 {
            public Object get(GlSearchViewVORowImpl obj) {
                return obj.getLovCogIdVO1();
            }

            public void put(GlSearchViewVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(GlSearchViewVORowImpl object);

        public abstract void put(GlSearchViewVORowImpl object, Object value);

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


    public static final int GLCLDID = AttributesEnum.GlCldId.index();
    public static final int GLSLOCID = AttributesEnum.GlSlocId.index();
    public static final int GLAPPLINSTID = AttributesEnum.GlApplInstId.index();
    public static final int GLORGID = AttributesEnum.GlOrgId.index();
    public static final int GLVOUID = AttributesEnum.GlVouId.index();
    public static final int DOCTXNIDDISP = AttributesEnum.DocTxnIdDisp.index();
    public static final int GLVOUDT = AttributesEnum.GlVouDt.index();
    public static final int GLTYPEID = AttributesEnum.GlTypeId.index();
    public static final int GLSUBTYPEID = AttributesEnum.GlSubTypeId.index();
    public static final int GLCURRIDBS = AttributesEnum.GlCurrIdBs.index();
    public static final int GLTOTAMTBS = AttributesEnum.GlTotAmtBs.index();
    public static final int GLAMTDRBS = AttributesEnum.GlAmtDrBs.index();
    public static final int GLAMTCRBS = AttributesEnum.GlAmtCrBs.index();
    public static final int GLCURRBSDESC = AttributesEnum.GlCurrBsDesc.index();
    public static final int USRNAME = AttributesEnum.UsrName.index();
    public static final int GLDESC = AttributesEnum.GlDesc.index();
    public static final int GLHOORGIDTRANS = AttributesEnum.GlHoOrgIdTrans.index();
    public static final int LOVORGIDVO = AttributesEnum.LovOrgIdVO.index();
    public static final int LOVVOUIDVO = AttributesEnum.LovVouIdVO.index();
    public static final int LOVDISTVOUTYPVO = AttributesEnum.LovDistVouTypVO.index();
    public static final int LOVSUBTYPEIDVO = AttributesEnum.LovSubTypeIdVO.index();
    public static final int LOVCURRLATEST1 = AttributesEnum.LovCurrLatest1.index();
    public static final int LOVCOGIDVO1 = AttributesEnum.LovCogIdVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public GlSearchViewVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute GlCldId.
     * @return the GlCldId
     */
    public String getGlCldId() {
        return (String) getAttributeInternal(GLCLDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute GlCldId.
     * @param value value to set the  GlCldId
     */
    public void setGlCldId(String value) {
        setAttributeInternal(GLCLDID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute GlSlocId.
     * @return the GlSlocId
     */
    public Integer getGlSlocId() {
        return (Integer) getAttributeInternal(GLSLOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute GlSlocId.
     * @param value value to set the  GlSlocId
     */
    public void setGlSlocId(Integer value) {
        setAttributeInternal(GLSLOCID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute GlApplInstId.
     * @return the GlApplInstId
     */
    public Integer getGlApplInstId() {
        return (Integer) getAttributeInternal(GLAPPLINSTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute GlApplInstId.
     * @param value value to set the  GlApplInstId
     */
    public void setGlApplInstId(Integer value) {
        setAttributeInternal(GLAPPLINSTID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute GlOrgId.
     * @return the GlOrgId
     */
    public String getGlOrgId() {
       
        return (String) getAttributeInternal(GLORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute GlOrgId.
     * @param value value to set the  GlOrgId
     */
    public void setGlOrgId(String value) {
        setAttributeInternal(GLORGID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute GlVouIdDisp.
     * @return the GlVouIdDisp
     */
    public String getGlVouId() {
        return (String) getAttributeInternal(GLVOUID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute GlVouIdDisp.
     * @param value value to set the  GlVouIdDisp
     */
    public void setGlVouId(String value) {
        setAttributeInternal(GLVOUID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute DocTxnIdDisp.
     * @return the DocTxnIdDisp
     */
    public String getDocTxnIdDisp() {
        return (String) getAttributeInternal(DOCTXNIDDISP);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute DocTxnIdDisp.
     * @param value value to set the  DocTxnIdDisp
     */
    public void setDocTxnIdDisp(String value) {
        setAttributeInternal(DOCTXNIDDISP, value);
    }

    /**
     * Gets the attribute value for the calculated attribute GlVouDt.
     * @return the GlVouDt
     */
    public Date getGlVouDt() {
        return (Date) getAttributeInternal(GLVOUDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute GlVouDt.
     * @param value value to set the  GlVouDt
     */
    public void setGlVouDt(Date value) {
        setAttributeInternal(GLVOUDT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute GlTypeId.
     * @return the GlTypeId
     */
    public Integer getGlTypeId() {
        return (Integer) getAttributeInternal(GLTYPEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute GlTypeId.
     * @param value value to set the  GlTypeId
     */
    public void setGlTypeId(Integer value) {
        setAttributeInternal(GLTYPEID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute GlSubTypeId.
     * @return the GlSubTypeId
     */
    public Integer getGlSubTypeId() {
        return (Integer) getAttributeInternal(GLSUBTYPEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute GlSubTypeId.
     * @param value value to set the  GlSubTypeId
     */
    public void setGlSubTypeId(Integer value) {
        setAttributeInternal(GLSUBTYPEID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute GlCurrIdBs.
     * @return the GlCurrIdBs
     */
    public Integer getGlCurrIdBs() {
        return (Integer) getAttributeInternal(GLCURRIDBS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute GlCurrIdBs.
     * @param value value to set the  GlCurrIdBs
     */
    public void setGlCurrIdBs(Integer value) {
        setAttributeInternal(GLCURRIDBS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute GlTotAmtBs.
     * @return the GlTotAmtBs
     */
    public Number getGlTotAmtBs() {
        return (Number) getAttributeInternal(GLTOTAMTBS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute GlTotAmtBs.
     * @param value value to set the  GlTotAmtBs
     */
    public void setGlTotAmtBs(Number value) {
        setAttributeInternal(GLTOTAMTBS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute GlAmtDrBs.
     * @return the GlAmtDrBs
     */
    public Number getGlAmtDrBs() {
        return (Number) getAttributeInternal(GLAMTDRBS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute GlAmtDrBs.
     * @param value value to set the  GlAmtDrBs
     */
     public void setGlAmtDrBs(Number value) {
           if(value != null){
               Integer decimalPlaces = 2;
                            if(resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}")!=null){
                                System.out.println("Null");
                                decimalPlaces = Integer.parseInt((resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}")).toString());   
                            }
                            
                            value.round(decimalPlaces );

               setAttributeInternal(GLAMTDRBS, value);
           }
          
       }

    /**
     * Gets the attribute value for the calculated attribute GlAmtCrBs.
     * @return the GlAmtCrBs
     */
    public Number getGlAmtCrBs() {
        return (Number) getAttributeInternal(GLAMTCRBS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute GlAmtCrBs.
     * @param value value to set the  GlAmtCrBs
     */
     public void setGlAmtCrBs(Number value) {

           if(value !=null)
           {
                             Integer decimalPlaces = 2;
                          if(resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}")!=null){
                              System.out.println("Null");
                              decimalPlaces = Integer.parseInt((resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}")).toString());   
                          }
                          
                          value.round(decimalPlaces );

               
           }
           setAttributeInternal(GLAMTCRBS, value);
       }

    /**
     * Gets the attribute value for the calculated attribute GlCurrBsDesc.
     * @return the GlCurrBsDesc
     */
    public String getGlCurrBsDesc() {
        return (String) getAttributeInternal(GLCURRBSDESC);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute GlCurrBsDesc.
     * @param value value to set the  GlCurrBsDesc
     */
    public void setGlCurrBsDesc(String value) {
        setAttributeInternal(GLCURRBSDESC, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrName.
     * @return the UsrName
     */
    public String getUsrName() {
        return (String) getAttributeInternal(USRNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrName.
     * @param value value to set the  UsrName
     */
    public void setUsrName(String value) {
        setAttributeInternal(USRNAME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute GlDesc.
     * @return the GlDesc
     */
    public String getGlDesc() {
        return (String) getAttributeInternal(GLDESC);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute GlDesc.
     * @param value value to set the  GlDesc
     */
    public void setGlDesc(String value) {
        setAttributeInternal(GLDESC, value);
    }

    /**
     * Gets the attribute value for the calculated attribute GlHoOrgIdTrans.
     * @return the GlHoOrgIdTrans
     */
    public String getGlHoOrgIdTrans() {
        if(resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}") != null){
            //System.out.println("Ho Org Id is : "+resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}"));
            return resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");    
        }
        return  (String) getAttributeInternal(GLHOORGIDTRANS);
        
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute GlHoOrgIdTrans.
     * @param value value to set the  GlHoOrgIdTrans
     */
    public void setGlHoOrgIdTrans(String value) {
        setAttributeInternal(GLHOORGIDTRANS, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovOrgIdVO.
     */
    public RowSet getLovOrgIdVO() {
        return (RowSet)getAttributeInternal(LOVORGIDVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovVouIdVO.
     */
    public RowSet getLovVouIdVO() {
        return (RowSet)getAttributeInternal(LOVVOUIDVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovDistVouTypVO.
     */
    public RowSet getLovDistVouTypVO() {
        return (RowSet)getAttributeInternal(LOVDISTVOUTYPVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovSubTypeIdVO.
     */
    public RowSet getLovSubTypeIdVO() {
        return (RowSet)getAttributeInternal(LOVSUBTYPEIDVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovCurrLatest1.
     */
    public RowSet getLovCurrLatest1() {
        return (RowSet)getAttributeInternal(LOVCURRLATEST1);
    }


    /**
     * Gets the view accessor <code>RowSet</code> LovCogIdVO1.
     */
    public RowSet getLovCogIdVO1() {
        return (RowSet)getAttributeInternal(LOVCOGIDVO1);
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
         ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
         String Message=valueExp.getValue(elContext).toString();
         return Message;
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

    /* public void filterCoa(String org_id) {
         RowSet rwset=getLovCoaVO1();
        rwset.setRangeSize(-1); 
        rwset.executeQuery(); 
    } */
    
}
