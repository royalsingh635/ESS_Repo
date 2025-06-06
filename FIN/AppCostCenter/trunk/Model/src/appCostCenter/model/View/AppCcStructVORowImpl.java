package appCostCenter.model.View;

import appCostCenter.model.entity.AppCcStructEOImpl;

import oracle.jbo.RowSet;
import oracle.jbo.domain.Date;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Apr 30 12:47:38 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppCcStructVORowImpl extends ViewRowImpl {


    public static final int ENTITY_APPCCSTRUCTEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CcEntId {
            public Object get(AppCcStructVORowImpl obj) {
                return obj.getCcEntId();
            }

            public void put(AppCcStructVORowImpl obj, Object value) {
                obj.setCcEntId((Long) value);
            }
        }
        ,
        CcEntIdParent {
            public Object get(AppCcStructVORowImpl obj) {
                return obj.getCcEntIdParent();
            }

            public void put(AppCcStructVORowImpl obj, Object value) {
                obj.setCcEntIdParent((Long) value);
            }
        }
        ,
        CcPos {
            public Object get(AppCcStructVORowImpl obj) {
                return obj.getCcPos();
            }

            public void put(AppCcStructVORowImpl obj, Object value) {
                obj.setCcPos((Integer) value);
            }
        }
        ,
        CcPrfId {
            public Object get(AppCcStructVORowImpl obj) {
                return obj.getCcPrfId();
            }

            public void put(AppCcStructVORowImpl obj, Object value) {
                obj.setCcPrfId((Integer) value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(AppCcStructVORowImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(AppCcStructVORowImpl obj, Object value) {
                obj.setUsrIdCreate((Integer) value);
            }
        }
        ,
        UsrIdMod {
            public Object get(AppCcStructVORowImpl obj) {
                return obj.getUsrIdMod();
            }

            public void put(AppCcStructVORowImpl obj, Object value) {
                obj.setUsrIdMod((Integer) value);
            }
        }
        ,
        CcMan {
            public Object get(AppCcStructVORowImpl obj) {
                return obj.getCcMan();
            }

            public void put(AppCcStructVORowImpl obj, Object value) {
                obj.setCcMan((String) value);
            }
        }
        ,
        CcDepndnt {
            public Object get(AppCcStructVORowImpl obj) {
                return obj.getCcDepndnt();
            }

            public void put(AppCcStructVORowImpl obj, Object value) {
                obj.setCcDepndnt((String) value);
            }
        }
        ,
        UsrIdCreateDt {
            public Object get(AppCcStructVORowImpl obj) {
                return obj.getUsrIdCreateDt();
            }

            public void put(AppCcStructVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UsrIdModDt {
            public Object get(AppCcStructVORowImpl obj) {
                return obj.getUsrIdModDt();
            }

            public void put(AppCcStructVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        SlocId {
            public Object get(AppCcStructVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(AppCcStructVORowImpl obj, Object value) {
                obj.setSlocId((Integer) value);
            }
        }
        ,
        CldId {
            public Object get(AppCcStructVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(AppCcStructVORowImpl obj, Object value) {
                obj.setCldId((String) value);
            }
        }
        ,
        OrgId {
            public Object get(AppCcStructVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(AppCcStructVORowImpl obj, Object value) {
                obj.setOrgId((String) value);
            }
        }
        ,
        HoOrgId {
            public Object get(AppCcStructVORowImpl obj) {
                return obj.getHoOrgId();
            }

            public void put(AppCcStructVORowImpl obj, Object value) {
                obj.setHoOrgId((String) value);
            }
        }
        ,
        EntityLOV1 {
            public Object get(AppCcStructVORowImpl obj) {
                return obj.getEntityLOV1();
            }

            public void put(AppCcStructVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ParentEntityLOV1 {
            public Object get(AppCcStructVORowImpl obj) {
                return obj.getParentEntityLOV1();
            }

            public void put(AppCcStructVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        static AttributesEnum[] vals = null;
        ;
        private static int firstIndex = 0;

        public abstract Object get(AppCcStructVORowImpl object);

        public abstract void put(AppCcStructVORowImpl object, Object value);

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


    public static final int CCENTID = AttributesEnum.CcEntId.index();
    public static final int CCENTIDPARENT = AttributesEnum.CcEntIdParent.index();
    public static final int CCPOS = AttributesEnum.CcPos.index();
    public static final int CCPRFID = AttributesEnum.CcPrfId.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int CCMAN = AttributesEnum.CcMan.index();
    public static final int CCDEPNDNT = AttributesEnum.CcDepndnt.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int ENTITYLOV1 = AttributesEnum.EntityLOV1.index();
    public static final int PARENTENTITYLOV1 = AttributesEnum.ParentEntityLOV1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public AppCcStructVORowImpl() {
    }

    /**
     * Gets AppCcStructEO entity object.
     * @return the AppCcStructEO
     */
    public AppCcStructEOImpl getAppCcStructEO() {
        return (AppCcStructEOImpl)getEntity(ENTITY_APPCCSTRUCTEO);
    }

    /**
     * Gets the attribute value for CC_ENT_ID using the alias name CcEntId.
     * @return the CC_ENT_ID
     */
    public Long getCcEntId() {
        return (Long) getAttributeInternal(CCENTID);
    }

    /**
     * Sets <code>value</code> as attribute value for CC_ENT_ID using the alias name CcEntId.
     * @param value value to set the CC_ENT_ID
     */
    public void setCcEntId(Long value) {
        setAttributeInternal(CCENTID, value);
    }

    /**
     * Gets the attribute value for CC_ENT_ID_PARENT using the alias name CcEntIdParent.
     * @return the CC_ENT_ID_PARENT
     */
    public Long getCcEntIdParent() {
        return (Long) getAttributeInternal(CCENTIDPARENT);
    }

    /**
     * Sets <code>value</code> as attribute value for CC_ENT_ID_PARENT using the alias name CcEntIdParent.
     * @param value value to set the CC_ENT_ID_PARENT
     */
    public void setCcEntIdParent(Long value) {
        setAttributeInternal(CCENTIDPARENT, value);
    }

    /**
     * Gets the attribute value for CC_POS using the alias name CcPos.
     * @return the CC_POS
     */
    public Integer getCcPos() {
        return (Integer) getAttributeInternal(CCPOS);
    }

    /**
     * Sets <code>value</code> as attribute value for CC_POS using the alias name CcPos.
     * @param value value to set the CC_POS
     */
    public void setCcPos(Integer value) {
        setAttributeInternal(CCPOS, value);
    }

    /**
     * Gets the attribute value for CC_PRF_ID using the alias name CcPrfId.
     * @return the CC_PRF_ID
     */
    public Integer getCcPrfId() {
        return (Integer) getAttributeInternal(CCPRFID);
    }

    /**
     * Sets <code>value</code> as attribute value for CC_PRF_ID using the alias name CcPrfId.
     * @param value value to set the CC_PRF_ID
     */
    public void setCcPrfId(Integer value) {
        setAttributeInternal(CCPRFID, value);
    }

    /**
     * Gets the attribute value for USR_ID_CREATE using the alias name UsrIdCreate.
     * @return the USR_ID_CREATE
     */
    public Integer getUsrIdCreate() {
        return (Integer) getAttributeInternal(USRIDCREATE);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_CREATE using the alias name UsrIdCreate.
     * @param value value to set the USR_ID_CREATE
     */
    public void setUsrIdCreate(Integer value) {
        setAttributeInternal(USRIDCREATE, value);
    }

    /**
     * Gets the attribute value for USR_ID_MOD using the alias name UsrIdMod.
     * @return the USR_ID_MOD
     */
    public Integer getUsrIdMod() {
        return (Integer) getAttributeInternal(USRIDMOD);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_MOD using the alias name UsrIdMod.
     * @param value value to set the USR_ID_MOD
     */
    public void setUsrIdMod(Integer value) {
        setAttributeInternal(USRIDMOD, value);
    }

    /**
     * Gets the attribute value for CC_MAN using the alias name CcMan.
     * @return the CC_MAN
     */
    public String getCcMan() {
        return (String) getAttributeInternal(CCMAN);
    }

    /**
     * Sets <code>value</code> as attribute value for CC_MAN using the alias name CcMan.
     * @param value value to set the CC_MAN
     */
    public void setCcMan(String value) {
        setAttributeInternal(CCMAN, value);
    }

    /**
     * Gets the attribute value for CC_DEPNDNT using the alias name CcDepndnt.
     * @return the CC_DEPNDNT
     */
    public String getCcDepndnt() {
        return (String) getAttributeInternal(CCDEPNDNT);
    }

    /**
     * Sets <code>value</code> as attribute value for CC_DEPNDNT using the alias name CcDepndnt.
     * @param value value to set the CC_DEPNDNT
     */
    public void setCcDepndnt(String value) {
        setAttributeInternal(CCDEPNDNT, value);
    }

    /**
     * Gets the attribute value for USR_ID_CREATE_DT using the alias name UsrIdCreateDt.
     * @return the USR_ID_CREATE_DT
     */
    public Date getUsrIdCreateDt() {
        return (Date) getAttributeInternal(USRIDCREATEDT);
    }


    /**
     * Gets the attribute value for USR_ID_MOD_DT using the alias name UsrIdModDt.
     * @return the USR_ID_MOD_DT
     */
    public Date getUsrIdModDt() {
        return (Date) getAttributeInternal(USRIDMODDT);
    }


    /**
     * Gets the attribute value for SLOC_ID using the alias name SlocId.
     * @return the SLOC_ID
     */
    public Integer getSlocId() {
        return (Integer) getAttributeInternal(SLOCID);
    }

    /**
     * Sets <code>value</code> as attribute value for SLOC_ID using the alias name SlocId.
     * @param value value to set the SLOC_ID
     */
    public void setSlocId(Integer value) {
        setAttributeInternal(SLOCID, value);
    }

    /**
     * Gets the attribute value for CLD_ID using the alias name CldId.
     * @return the CLD_ID
     */
    public String getCldId() {
        return (String) getAttributeInternal(CLDID);
    }

    /**
     * Sets <code>value</code> as attribute value for CLD_ID using the alias name CldId.
     * @param value value to set the CLD_ID
     */
    public void setCldId(String value) {
        setAttributeInternal(CLDID, value);
    }

    /**
     * Gets the attribute value for ORG_ID using the alias name OrgId.
     * @return the ORG_ID
     */
    public String getOrgId() {
        return (String) getAttributeInternal(ORGID);
    }

    /**
     * Sets <code>value</code> as attribute value for ORG_ID using the alias name OrgId.
     * @param value value to set the ORG_ID
     */
    public void setOrgId(String value) {
        setAttributeInternal(ORGID, value);
    }

    /**
     * Gets the attribute value for HO_ORG_ID using the alias name HoOrgId.
     * @return the HO_ORG_ID
     */
    public String getHoOrgId() {
        return (String) getAttributeInternal(HOORGID);
    }

    /**
     * Sets <code>value</code> as attribute value for HO_ORG_ID using the alias name HoOrgId.
     * @param value value to set the HO_ORG_ID
     */
    public void setHoOrgId(String value) {
        setAttributeInternal(HOORGID, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> EntityLOV1.
     */
    public RowSet getEntityLOV1() {
        return (RowSet)getAttributeInternal(ENTITYLOV1);
    }


    /**
     * Gets the view accessor <code>RowSet</code> ParentEntityLOV1.
     */
    public RowSet getParentEntityLOV1() {
        return (RowSet)getAttributeInternal(PARENTENTITYLOV1);
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
