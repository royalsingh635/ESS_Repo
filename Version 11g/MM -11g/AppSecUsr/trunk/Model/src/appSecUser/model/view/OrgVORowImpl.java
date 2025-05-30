package appSecUser.model.view;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Jun 16 12:47:55 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class OrgVORowImpl extends ViewRowImpl {
    public static final int ENTITY_ORGEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        OrgCldId {
            public Object get(OrgVORowImpl obj) {
                return obj.getOrgCldId();
            }

            public void put(OrgVORowImpl obj, Object value) {
                obj.setOrgCldId((String)value);
            }
        }
        ,
        OrgId {
            public Object get(OrgVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(OrgVORowImpl obj, Object value) {
                obj.setOrgId((String)value);
            }
        }
        ,
        OrgType {
            public Object get(OrgVORowImpl obj) {
                return obj.getOrgType();
            }

            public void put(OrgVORowImpl obj, Object value) {
                obj.setOrgType((Integer)value);
            }
        }
        ,
        OrgDesc {
            public Object get(OrgVORowImpl obj) {
                return obj.getOrgDesc();
            }

            public void put(OrgVORowImpl obj, Object value) {
                obj.setOrgDesc((String)value);
            }
        }
        ,
        OrgActive {
            public Object get(OrgVORowImpl obj) {
                return obj.getOrgActive();
            }

            public void put(OrgVORowImpl obj, Object value) {
                obj.setOrgActive((String)value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(OrgVORowImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(OrgVORowImpl obj, Object value) {
                obj.setUsrIdCreate((Long)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(OrgVORowImpl object);

        public abstract void put(OrgVORowImpl object, Object value);

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
    public static final int ORGCLDID = AttributesEnum.OrgCldId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int ORGTYPE = AttributesEnum.OrgType.index();
    public static final int ORGDESC = AttributesEnum.OrgDesc.index();
    public static final int ORGACTIVE = AttributesEnum.OrgActive.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();

    /**
     * This is the default constructor (do not remove).
     */
    public OrgVORowImpl() {
    }

    /**
     * Gets OrgEO entity object.
     * @return the OrgEO
     */
    public EntityImpl getOrgEO() {
        return (EntityImpl)getEntity(ENTITY_ORGEO);
    }

    /**
     * Gets the attribute value for ORG_CLD_ID using the alias name OrgCldId.
     * @return the ORG_CLD_ID
     */
    public String getOrgCldId() {
        return (String) getAttributeInternal(ORGCLDID);
    }

    /**
     * Sets <code>value</code> as attribute value for ORG_CLD_ID using the alias name OrgCldId.
     * @param value value to set the ORG_CLD_ID
     */
    public void setOrgCldId(String value) {
        setAttributeInternal(ORGCLDID, value);
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
     * Gets the attribute value for ORG_TYPE using the alias name OrgType.
     * @return the ORG_TYPE
     */
    public Integer getOrgType() {
        return (Integer) getAttributeInternal(ORGTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for ORG_TYPE using the alias name OrgType.
     * @param value value to set the ORG_TYPE
     */
    public void setOrgType(Integer value) {
        setAttributeInternal(ORGTYPE, value);
    }

    /**
     * Gets the attribute value for ORG_DESC using the alias name OrgDesc.
     * @return the ORG_DESC
     */
    public String getOrgDesc() {
        return (String) getAttributeInternal(ORGDESC);
    }

    /**
     * Sets <code>value</code> as attribute value for ORG_DESC using the alias name OrgDesc.
     * @param value value to set the ORG_DESC
     */
    public void setOrgDesc(String value) {
        setAttributeInternal(ORGDESC, value);
    }

    /**
     * Gets the attribute value for ORG_ACTIVE using the alias name OrgActive.
     * @return the ORG_ACTIVE
     */
    public String getOrgActive() {
        return (String) getAttributeInternal(ORGACTIVE);
    }

    /**
     * Sets <code>value</code> as attribute value for ORG_ACTIVE using the alias name OrgActive.
     * @param value value to set the ORG_ACTIVE
     */
    public void setOrgActive(String value) {
        setAttributeInternal(ORGACTIVE, value);
    }

    /**
     * Gets the attribute value for USR_ID_CREATE using the alias name UsrIdCreate.
     * @return the USR_ID_CREATE
     */
    public Long getUsrIdCreate() {
        return (Long) getAttributeInternal(USRIDCREATE);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_CREATE using the alias name UsrIdCreate.
     * @param value value to set the USR_ID_CREATE
     */
    public void setUsrIdCreate(Long value) {
        setAttributeInternal(USRIDCREATE, value);
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
