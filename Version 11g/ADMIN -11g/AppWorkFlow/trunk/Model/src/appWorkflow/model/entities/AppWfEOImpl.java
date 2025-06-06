package appWorkflow.model.entities;

import appWorkflow.model.module.AppWfAMImpl;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.ViewObject;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewObjectImpl;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.RowIterator;
import oracle.jbo.domain.Date;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Apr 04 13:11:43 IST 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppWfEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        WfSlocId {
            public Object get(AppWfEOImpl obj) {
                return obj.getWfSlocId();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setWfSlocId((Integer)value);
            }
        }
        ,
        WfOrgId {
            public Object get(AppWfEOImpl obj) {
                return obj.getWfOrgId();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setWfOrgId((String)value);
            }
        }
        ,
        WfId {
            public Object get(AppWfEOImpl obj) {
                return obj.getWfId();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setWfId((Integer)value);
            }
        }
        ,
        WfNm {
            public Object get(AppWfEOImpl obj) {
                return obj.getWfNm();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setWfNm((String)value);
            }
        }
        ,
        WfMaxLevel {
            public Object get(AppWfEOImpl obj) {
                return obj.getWfMaxLevel();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setWfMaxLevel((Integer)value);
            }
        }
        ,
        WfTyp {
            public Object get(AppWfEOImpl obj) {
                return obj.getWfTyp();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setWfTyp((String)value);
            }
        }
        ,
        WfAuthDys {
            public Object get(AppWfEOImpl obj) {
                return obj.getWfAuthDys();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setWfAuthDys((Integer)value);
            }
        }
        ,
        WfWarnDys {
            public Object get(AppWfEOImpl obj) {
                return obj.getWfWarnDys();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setWfWarnDys((Integer)value);
            }
        }
        ,
        WfAutoSkip {
            public Object get(AppWfEOImpl obj) {
                return obj.getWfAutoSkip();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setWfAutoSkip((String)value);
            }
        }
        ,
        WfModLvl {
            public Object get(AppWfEOImpl obj) {
                return obj.getWfModLvl();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setWfModLvl((String)value);
            }
        }
        ,
        WfModLvlAuthDys {
            public Object get(AppWfEOImpl obj) {
                return obj.getWfModLvlAuthDys();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setWfModLvlAuthDys((String)value);
            }
        }
        ,
        WfModLvlWarnDys {
            public Object get(AppWfEOImpl obj) {
                return obj.getWfModLvlWarnDys();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setWfModLvlWarnDys((String)value);
            }
        }
        ,
        WfModLvlAutoSkip {
            public Object get(AppWfEOImpl obj) {
                return obj.getWfModLvlAutoSkip();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setWfModLvlAutoSkip((String)value);
            }
        }
        ,
        WfModUsrLvlAuthDys {
            public Object get(AppWfEOImpl obj) {
                return obj.getWfModUsrLvlAuthDys();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setWfModUsrLvlAuthDys((String)value);
            }
        }
        ,
        WfModUsrLvlWarnDys {
            public Object get(AppWfEOImpl obj) {
                return obj.getWfModUsrLvlWarnDys();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setWfModUsrLvlWarnDys((String)value);
            }
        }
        ,
        WfModUsrLvlAutoSkip {
            public Object get(AppWfEOImpl obj) {
                return obj.getWfModUsrLvlAutoSkip();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setWfModUsrLvlAutoSkip((String)value);
            }
        }
        ,
        WfActv {
            public Object get(AppWfEOImpl obj) {
                return obj.getWfActv();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setWfActv((String)value);
            }
        }
        ,
        WfEntId {
            public Object get(AppWfEOImpl obj) {
                return obj.getWfEntId();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setWfEntId((Long)value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(AppWfEOImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setUsrIdCreate((Integer)value);
            }
        }
        ,
        UsrIdCreateDt {
            public Object get(AppWfEOImpl obj) {
                return obj.getUsrIdCreateDt();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UsrIdMod {
            public Object get(AppWfEOImpl obj) {
                return obj.getUsrIdMod();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setUsrIdMod((Integer)value);
            }
        }
        ,
        UsrIdModDt {
            public Object get(AppWfEOImpl obj) {
                return obj.getUsrIdModDt();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        WfSessId {
            public Object get(AppWfEOImpl obj) {
                return obj.getWfSessId();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setWfSessId((String)value);
            }
        }
        ,
        AppWfLvl {
            public Object get(AppWfEOImpl obj) {
                return obj.getAppWfLvl();
            }

            public void put(AppWfEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(AppWfEOImpl object);

        public abstract void put(AppWfEOImpl object, Object value);

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
    public static final int WFSLOCID = AttributesEnum.WfSlocId.index();
    public static final int WFORGID = AttributesEnum.WfOrgId.index();
    public static final int WFID = AttributesEnum.WfId.index();
    public static final int WFNM = AttributesEnum.WfNm.index();
    public static final int WFMAXLEVEL = AttributesEnum.WfMaxLevel.index();
    public static final int WFTYP = AttributesEnum.WfTyp.index();
    public static final int WFAUTHDYS = AttributesEnum.WfAuthDys.index();
    public static final int WFWARNDYS = AttributesEnum.WfWarnDys.index();
    public static final int WFAUTOSKIP = AttributesEnum.WfAutoSkip.index();
    public static final int WFMODLVL = AttributesEnum.WfModLvl.index();
    public static final int WFMODLVLAUTHDYS = AttributesEnum.WfModLvlAuthDys.index();
    public static final int WFMODLVLWARNDYS = AttributesEnum.WfModLvlWarnDys.index();
    public static final int WFMODLVLAUTOSKIP = AttributesEnum.WfModLvlAutoSkip.index();
    public static final int WFMODUSRLVLAUTHDYS = AttributesEnum.WfModUsrLvlAuthDys.index();
    public static final int WFMODUSRLVLWARNDYS = AttributesEnum.WfModUsrLvlWarnDys.index();
    public static final int WFMODUSRLVLAUTOSKIP = AttributesEnum.WfModUsrLvlAutoSkip.index();
    public static final int WFACTV = AttributesEnum.WfActv.index();
    public static final int WFENTID = AttributesEnum.WfEntId.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int WFSESSID = AttributesEnum.WfSessId.index();
    public static final int APPWFLVL = AttributesEnum.AppWfLvl.index();

    /**
     * This is the default constructor (do not remove).
     */
    public AppWfEOImpl() {
    }

    /**
     * Gets the attribute value for WfSlocId, using the alias name WfSlocId.
     * @return the value of WfSlocId
     */
    public Integer getWfSlocId() {
        return (Integer)getAttributeInternal(WFSLOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for WfSlocId.
     * @param value value to set the WfSlocId
     */
    public void setWfSlocId(Integer value) {
        setAttributeInternal(WFSLOCID, value);
    }

    /**
     * Gets the attribute value for WfOrgId, using the alias name WfOrgId.
     * @return the value of WfOrgId
     */
    public String getWfOrgId() {
        return (String)getAttributeInternal(WFORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for WfOrgId.
     * @param value value to set the WfOrgId
     */
    public void setWfOrgId(String value) {
        setAttributeInternal(WFORGID, value);
    }

    /**
     * Gets the attribute value for WfId, using the alias name WfId.
     * @return the value of WfId
     */
    public Integer getWfId() {
        return (Integer)getAttributeInternal(WFID);
    }

    /**
     * Sets <code>value</code> as the attribute value for WfId.
     * @param value value to set the WfId
     */
    public void setWfId(Integer value) {
        setAttributeInternal(WFID, value);
    }

    /**
     * Gets the attribute value for WfNm, using the alias name WfNm.
     * @return the value of WfNm
     */
    public String getWfNm() {
        return (String)getAttributeInternal(WFNM);
    }

    /**
     * Sets <code>value</code> as the attribute value for WfNm.
     * @param value value to set the WfNm
     */
    public void setWfNm(String value) {
        setAttributeInternal(WFNM, value);
    }

    /**
     * Gets the attribute value for WfMaxLevel, using the alias name WfMaxLevel.
     * @return the value of WfMaxLevel
     */
    public Integer getWfMaxLevel() {
        return (Integer)getAttributeInternal(WFMAXLEVEL);
    }

    /**
     * Sets <code>value</code> as the attribute value for WfMaxLevel.
     * @param value value to set the WfMaxLevel
     */
    public void setWfMaxLevel(Integer value) {
        setAttributeInternal(WFMAXLEVEL, value);
    }

    /**
     * Gets the attribute value for WfTyp, using the alias name WfTyp.
     * @return the value of WfTyp
     */
    public String getWfTyp() {
        return (String)getAttributeInternal(WFTYP);
    }

    /**
     * Sets <code>value</code> as the attribute value for WfTyp.
     * @param value value to set the WfTyp
     */
    public void setWfTyp(String value) {
        setAttributeInternal(WFTYP, value);
    }

    /**
     * Gets the attribute value for WfAuthDys, using the alias name WfAuthDys.
     * @return the value of WfAuthDys
     */
    public Integer getWfAuthDys() {
        return (Integer)getAttributeInternal(WFAUTHDYS);
    }

    /**
     * Sets <code>value</code> as the attribute value for WfAuthDys.
     * @param value value to set the WfAuthDys
     */
    public void setWfAuthDys(Integer value) {
        setAttributeInternal(WFAUTHDYS, value);
    }

    /**
     * Gets the attribute value for WfWarnDys, using the alias name WfWarnDys.
     * @return the value of WfWarnDys
     */
    public Integer getWfWarnDys() {
        return (Integer)getAttributeInternal(WFWARNDYS);
    }

    /**
     * Sets <code>value</code> as the attribute value for WfWarnDys.
     * @param value value to set the WfWarnDys
     */
    public void setWfWarnDys(Integer value) {
        setAttributeInternal(WFWARNDYS, value);
    }

    /**
     * Gets the attribute value for WfAutoSkip, using the alias name WfAutoSkip.
     * @return the value of WfAutoSkip
     */
    public String getWfAutoSkip() {
        return (String)getAttributeInternal(WFAUTOSKIP);
    }

    /**
     * Sets <code>value</code> as the attribute value for WfAutoSkip.
     * @param value value to set the WfAutoSkip
     */
    public void setWfAutoSkip(String value) {
        setAttributeInternal(WFAUTOSKIP, value);
    }

    /**
     * Gets the attribute value for WfModLvl, using the alias name WfModLvl.
     * @return the value of WfModLvl
     */
    public String getWfModLvl() {
        return (String)getAttributeInternal(WFMODLVL);
    }

    /**
     * Sets <code>value</code> as the attribute value for WfModLvl.
     * @param value value to set the WfModLvl
     */
    public void setWfModLvl(String value) {
        setAttributeInternal(WFMODLVL, value);
    }

    /**
     * Gets the attribute value for WfModLvlAuthDys, using the alias name WfModLvlAuthDys.
     * @return the value of WfModLvlAuthDys
     */
    public String getWfModLvlAuthDys() {
        return (String)getAttributeInternal(WFMODLVLAUTHDYS);
    }

    /**
     * Sets <code>value</code> as the attribute value for WfModLvlAuthDys.
     * @param value value to set the WfModLvlAuthDys
     */
    public void setWfModLvlAuthDys(String value) {
        setAttributeInternal(WFMODLVLAUTHDYS, value);
    }

    /**
     * Gets the attribute value for WfModLvlWarnDys, using the alias name WfModLvlWarnDys.
     * @return the value of WfModLvlWarnDys
     */
    public String getWfModLvlWarnDys() {
        return (String)getAttributeInternal(WFMODLVLWARNDYS);
    }

    /**
     * Sets <code>value</code> as the attribute value for WfModLvlWarnDys.
     * @param value value to set the WfModLvlWarnDys
     */
    public void setWfModLvlWarnDys(String value) {
        setAttributeInternal(WFMODLVLWARNDYS, value);
    }

    /**
     * Gets the attribute value for WfModLvlAutoSkip, using the alias name WfModLvlAutoSkip.
     * @return the value of WfModLvlAutoSkip
     */
    public String getWfModLvlAutoSkip() {
        return (String)getAttributeInternal(WFMODLVLAUTOSKIP);
    }

    /**
     * Sets <code>value</code> as the attribute value for WfModLvlAutoSkip.
     * @param value value to set the WfModLvlAutoSkip
     */
    public void setWfModLvlAutoSkip(String value) {
        setAttributeInternal(WFMODLVLAUTOSKIP, value);
    }

    /**
     * Gets the attribute value for WfModUsrLvlAuthDys, using the alias name WfModUsrLvlAuthDys.
     * @return the value of WfModUsrLvlAuthDys
     */
    public String getWfModUsrLvlAuthDys() {
        return (String)getAttributeInternal(WFMODUSRLVLAUTHDYS);
    }

    /**
     * Sets <code>value</code> as the attribute value for WfModUsrLvlAuthDys.
     * @param value value to set the WfModUsrLvlAuthDys
     */
    public void setWfModUsrLvlAuthDys(String value) {
        setAttributeInternal(WFMODUSRLVLAUTHDYS, value);
    }

    /**
     * Gets the attribute value for WfModUsrLvlWarnDys, using the alias name WfModUsrLvlWarnDys.
     * @return the value of WfModUsrLvlWarnDys
     */
    public String getWfModUsrLvlWarnDys() {
        return (String)getAttributeInternal(WFMODUSRLVLWARNDYS);
    }

    /**
     * Sets <code>value</code> as the attribute value for WfModUsrLvlWarnDys.
     * @param value value to set the WfModUsrLvlWarnDys
     */
    public void setWfModUsrLvlWarnDys(String value) {
        setAttributeInternal(WFMODUSRLVLWARNDYS, value);
    }

    /**
     * Gets the attribute value for WfModUsrLvlAutoSkip, using the alias name WfModUsrLvlAutoSkip.
     * @return the value of WfModUsrLvlAutoSkip
     */
    public String getWfModUsrLvlAutoSkip() {
        return (String)getAttributeInternal(WFMODUSRLVLAUTOSKIP);
    }

    /**
     * Sets <code>value</code> as the attribute value for WfModUsrLvlAutoSkip.
     * @param value value to set the WfModUsrLvlAutoSkip
     */
    public void setWfModUsrLvlAutoSkip(String value) {
        setAttributeInternal(WFMODUSRLVLAUTOSKIP, value);
    }

    /**
     * Gets the attribute value for WfActv, using the alias name WfActv.
     * @return the value of WfActv
     */
    public String getWfActv() {
        return (String)getAttributeInternal(WFACTV);
    }

    /**
     * Sets <code>value</code> as the attribute value for WfActv.
     * @param value value to set the WfActv
     */
    public void setWfActv(String value) {
        setAttributeInternal(WFACTV, value);
    }

    /**
     * Gets the attribute value for WfEntId, using the alias name WfEntId.
     * @return the value of WfEntId
     */
    public Long getWfEntId() {
        return (Long)getAttributeInternal(WFENTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for WfEntId.
     * @param value value to set the WfEntId
     */
    public void setWfEntId(Long value) {
        setAttributeInternal(WFENTID, value);
    }

    /**
     * Gets the attribute value for UsrIdCreate, using the alias name UsrIdCreate.
     * @return the value of UsrIdCreate
     */
    public Integer getUsrIdCreate() {
        return (Integer)getAttributeInternal(USRIDCREATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdCreate.
     * @param value value to set the UsrIdCreate
     */
    public void setUsrIdCreate(Integer value) {
        setAttributeInternal(USRIDCREATE, value);
    }

    /**
     * Gets the attribute value for UsrIdCreateDt, using the alias name UsrIdCreateDt.
     * @return the value of UsrIdCreateDt
     */
    public Date getUsrIdCreateDt() {
        return (Date)getAttributeInternal(USRIDCREATEDT);
    }

    /**
     * Gets the attribute value for UsrIdMod, using the alias name UsrIdMod.
     * @return the value of UsrIdMod
     */
    public Integer getUsrIdMod() {
        return (Integer)getAttributeInternal(USRIDMOD);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdMod.
     * @param value value to set the UsrIdMod
     */
    public void setUsrIdMod(Integer value) {
        setAttributeInternal(USRIDMOD, value);
    }

    /**
     * Gets the attribute value for UsrIdModDt, using the alias name UsrIdModDt.
     * @return the value of UsrIdModDt
     */
    public Date getUsrIdModDt() {
        return (Date)getAttributeInternal(USRIDMODDT);
    }

    /**
     * Gets the attribute value for WfSessId, using the alias name WfSessId.
     * @return the value of WfSessId
     */
    public String getWfSessId() {
        return (String)getAttributeInternal(WFSESSID);
    }

    /**
     * Sets <code>value</code> as the attribute value for WfSessId.
     * @param value value to set the WfSessId
     */
    public void setWfSessId(String value) {
        setAttributeInternal(WFSESSID, value);
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
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getAppWfLvl() {
        return (RowIterator)getAttributeInternal(APPWFLVL);
    }

    /**
     * @param wfSlocId key constituent
     * @param wfOrgId key constituent
     * @param wfId key constituent
     * @param wfSessId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(Integer wfSlocId, String wfOrgId, Integer wfId, String wfSessId) {
        return new Key(new Object[]{wfSlocId, wfOrgId, wfId, wfSessId});
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("appWorkflow.model.entities.AppWfEO");
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
     public Object resolvElDC(String data) {
         FacesContext fc = FacesContext.getCurrentInstance();
         Application app = fc.getApplication();
         ExpressionFactory elFactory = app.getExpressionFactory();
         ELContext elContext = fc.getELContext();
         ValueExpression valueExp =
             elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
         return valueExp.getValue(elContext);
     }

    protected void create(AttributeList attributeList) {
        super.create(attributeList);
        AppWfAMImpl am=(AppWfAMImpl)resolvElDC("AppWfAMDataControl");
        setUsrIdCreate(am.getGLBL_APP_USR());
        setWfOrgId(am.getGLBL_APP_USR_ORG());
        setWfSlocId(am.getGLBL_APP_SERV_LOC());
    }
}
