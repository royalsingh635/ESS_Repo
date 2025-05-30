package chartofaccount.model.view.views;

import chartofaccount.model.entity.entities.OrgCoaVwEOImpl;

import oracle.jbo.RowSet;
import oracle.jbo.domain.BFileDomain;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Jun 10 12:19:32 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class OrgCoaVWVORowImpl extends ViewRowImpl {


    public static final int ENTITY_ORGCOAVWEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CldId {
            public Object get(OrgCoaVWVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(OrgCoaVWVORowImpl obj, Object value) {
                obj.setCldId((String) value);
            }
        }
        ,
        HoOrgId {
            public Object get(OrgCoaVWVORowImpl obj) {
                return obj.getHoOrgId();
            }

            public void put(OrgCoaVWVORowImpl obj, Object value) {
                obj.setHoOrgId((String) value);
            }
        }
        ,
        OrgCoaActv {
            public Object get(OrgCoaVWVORowImpl obj) {
                return obj.getOrgCoaActv();
            }

            public void put(OrgCoaVWVORowImpl obj, Object value) {
                obj.setOrgCoaActv((String) value);
            }
        }
        ,
        OrgCoaId {
            public Object get(OrgCoaVWVORowImpl obj) {
                return obj.getOrgCoaId();
            }

            public void put(OrgCoaVWVORowImpl obj, Object value) {
                obj.setOrgCoaId((Integer) value);
            }
        }
        ,
        OrgDesc {
            public Object get(OrgCoaVWVORowImpl obj) {
                return obj.getOrgDesc();
            }

            public void put(OrgCoaVWVORowImpl obj, Object value) {
                obj.setOrgDesc((String) value);
            }
        }
        ,
        OrgId {
            public Object get(OrgCoaVWVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(OrgCoaVWVORowImpl obj, Object value) {
                obj.setOrgId((String) value);
            }
        }
        ,
        OrgSlocId {
            public Object get(OrgCoaVWVORowImpl obj) {
                return obj.getOrgSlocId();
            }

            public void put(OrgCoaVWVORowImpl obj, Object value) {
                obj.setOrgSlocId((Integer) value);
            }
        }
        ,
        UsrIdMod {
            public Object get(OrgCoaVWVORowImpl obj) {
                return obj.getUsrIdMod();
            }

            public void put(OrgCoaVWVORowImpl obj, Object value) {
                obj.setUsrIdMod((Integer) value);
            }
        }
        ,
        UsrIdModDt {
            public Object get(OrgCoaVWVORowImpl obj) {
                return obj.getUsrIdModDt();
            }

            public void put(OrgCoaVWVORowImpl obj, Object value) {
                obj.setUsrIdModDt((Timestamp) value);
            }
        }
        ,
        OrgCoaPrjId {
            public Object get(OrgCoaVWVORowImpl obj) {
                return obj.getOrgCoaPrjId();
            }

            public void put(OrgCoaVWVORowImpl obj, Object value) {
                obj.setOrgCoaPrjId((String) value);
            }
        }
        ,
        LovProjectVO {
            public Object get(OrgCoaVWVORowImpl obj) {
                return obj.getLovProjectVO();
            }

            public void put(OrgCoaVWVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        static AttributesEnum[] vals = null;
        ;
        private static int firstIndex = 0;

        public abstract Object get(OrgCoaVWVORowImpl object);

        public abstract void put(OrgCoaVWVORowImpl object, Object value);

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
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int ORGCOAACTV = AttributesEnum.OrgCoaActv.index();
    public static final int ORGCOAID = AttributesEnum.OrgCoaId.index();
    public static final int ORGDESC = AttributesEnum.OrgDesc.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int ORGSLOCID = AttributesEnum.OrgSlocId.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int ORGCOAPRJID = AttributesEnum.OrgCoaPrjId.index();
    public static final int LOVPROJECTVO = AttributesEnum.LovProjectVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public OrgCoaVWVORowImpl() {
    }

    /**
     * Gets OrgCoaVwEO entity object.
     * @return the OrgCoaVwEO
     */
    public OrgCoaVwEOImpl getOrgCoaVwEO() {
        return (OrgCoaVwEOImpl)getEntity(ENTITY_ORGCOAVWEO);
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
     * Gets the attribute value for ORG_COA_ACTV using the alias name OrgCoaActv.
     * @return the ORG_COA_ACTV
     */
    public String getOrgCoaActv() {
        return (String)getAttributeInternal(ORGCOAACTV);
    }

    /**
     * Sets <code>value</code> as attribute value for ORG_COA_ACTV using the alias name OrgCoaActv.
     * @param value value to set the ORG_COA_ACTV
     */
    public void setOrgCoaActv(String value) {
        System.out.println("Value is  "+value );
        setAttributeInternal(ORGCOAACTV, value);
    }

    /**
     * Gets the attribute value for ORG_COA_ID using the alias name OrgCoaId.
     * @return the ORG_COA_ID
     */
    public Integer getOrgCoaId() {
        return (Integer) getAttributeInternal(ORGCOAID);
    }

    /**
     * Sets <code>value</code> as attribute value for ORG_COA_ID using the alias name OrgCoaId.
     * @param value value to set the ORG_COA_ID
     */
    public void setOrgCoaId(Integer value) {
        setAttributeInternal(ORGCOAID, value);
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
     * Gets the attribute value for ORG_SLOC_ID using the alias name OrgSlocId.
     * @return the ORG_SLOC_ID
     */
    public Integer getOrgSlocId() {
        return (Integer) getAttributeInternal(ORGSLOCID);
    }

    /**
     * Sets <code>value</code> as attribute value for ORG_SLOC_ID using the alias name OrgSlocId.
     * @param value value to set the ORG_SLOC_ID
     */
    public void setOrgSlocId(Integer value) {
        setAttributeInternal(ORGSLOCID, value);
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
     * Gets the attribute value for USR_ID_MOD_DT using the alias name UsrIdModDt.
     * @return the USR_ID_MOD_DT
     */
    public Timestamp getUsrIdModDt() {
        return (Timestamp) getAttributeInternal(USRIDMODDT);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_MOD_DT using the alias name UsrIdModDt.
     * @param value value to set the USR_ID_MOD_DT
     */
    public void setUsrIdModDt(Timestamp value) {
        setAttributeInternal(USRIDMODDT, value);
    }

    /**
     * Gets the attribute value for ORG_COA_PRJ_ID using the alias name OrgCoaPrjId.
     * @return the ORG_COA_PRJ_ID
     */
    public String getOrgCoaPrjId() {
        return (String) getAttributeInternal(ORGCOAPRJID);
    }

    /**
     * Sets <code>value</code> as attribute value for ORG_COA_PRJ_ID using the alias name OrgCoaPrjId.
     * @param value value to set the ORG_COA_PRJ_ID
     */
    public void setOrgCoaPrjId(String value) {
        setAttributeInternal(ORGCOAPRJID, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovProjectVO.
     */
    public RowSet getLovProjectVO() {
        return (RowSet) getAttributeInternal(LOVPROJECTVO);
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
