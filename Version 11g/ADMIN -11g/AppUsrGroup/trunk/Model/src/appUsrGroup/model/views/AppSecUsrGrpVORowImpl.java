package appUsrGroup.model.views;

import appUsrGroup.model.entities.AppSecUsrGrpEOImpl;

import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Jun 22 15:11:24 IST 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppSecUsrGrpVORowImpl extends ViewRowImpl {

    public static final int ENTITY_APPSECUSRGRPEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        SlocId {
            public Object get(AppSecUsrGrpVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(AppSecUsrGrpVORowImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        UsrGrpActv {
            public Object get(AppSecUsrGrpVORowImpl obj) {
                return obj.getUsrGrpActv();
            }

            public void put(AppSecUsrGrpVORowImpl obj, Object value) {
                obj.setUsrGrpActv((String)value);
            }
        }
        ,
        UsrGrpDef {
            public Object get(AppSecUsrGrpVORowImpl obj) {
                return obj.getUsrGrpDef();
            }

            public void put(AppSecUsrGrpVORowImpl obj, Object value) {
                obj.setUsrGrpDef((String)value);
            }
        }
        ,
        UsrGrpEntId {
            public Object get(AppSecUsrGrpVORowImpl obj) {
                return obj.getUsrGrpEntId();
            }

            public void put(AppSecUsrGrpVORowImpl obj, Object value) {
                obj.setUsrGrpEntId((Long)value);
            }
        }
        ,
        UsrGrpId {
            public Object get(AppSecUsrGrpVORowImpl obj) {
                return obj.getUsrGrpId();
            }

            public void put(AppSecUsrGrpVORowImpl obj, Object value) {
                obj.setUsrGrpId((Integer)value);
            }
        }
        ,
        UsrGrpMailId {
            public Object get(AppSecUsrGrpVORowImpl obj) {
                return obj.getUsrGrpMailId();
            }

            public void put(AppSecUsrGrpVORowImpl obj, Object value) {
                obj.setUsrGrpMailId((String)value);
            }
        }
        ,
        UsrGrpNm {
            public Object get(AppSecUsrGrpVORowImpl obj) {
                return obj.getUsrGrpNm();
            }

            public void put(AppSecUsrGrpVORowImpl obj, Object value) {
                obj.setUsrGrpNm((String)value);
            }
        }
        ,
        UsrGrpResv {
            public Object get(AppSecUsrGrpVORowImpl obj) {
                return obj.getUsrGrpResv();
            }

            public void put(AppSecUsrGrpVORowImpl obj, Object value) {
                obj.setUsrGrpResv((String)value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(AppSecUsrGrpVORowImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(AppSecUsrGrpVORowImpl obj, Object value) {
                obj.setUsrIdCreate((Integer)value);
            }
        }
        ,
        UsrIdCreateDt {
            public Object get(AppSecUsrGrpVORowImpl obj) {
                return obj.getUsrIdCreateDt();
            }

            public void put(AppSecUsrGrpVORowImpl obj, Object value) {
                obj.setUsrIdCreateDt((Timestamp)value);
            }
        }
        ,
        UsrIdMod {
            public Object get(AppSecUsrGrpVORowImpl obj) {
                return obj.getUsrIdMod();
            }

            public void put(AppSecUsrGrpVORowImpl obj, Object value) {
                obj.setUsrIdMod((Integer)value);
            }
        }
        ,
        UsrIdModDt {
            public Object get(AppSecUsrGrpVORowImpl obj) {
                return obj.getUsrIdModDt();
            }

            public void put(AppSecUsrGrpVORowImpl obj, Object value) {
                obj.setUsrIdModDt((Timestamp)value);
            }
        }
        ,
        AppSecUsrGrpLnk {
            public Object get(AppSecUsrGrpVORowImpl obj) {
                return obj.getAppSecUsrGrpLnk();
            }

            public void put(AppSecUsrGrpVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        OrgSecUsrGrp {
            public Object get(AppSecUsrGrpVORowImpl obj) {
                return obj.getOrgSecUsrGrp();
            }

            public void put(AppSecUsrGrpVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UserLOV1 {
            public Object get(AppSecUsrGrpVORowImpl obj) {
                return obj.getUserLOV1();
            }

            public void put(AppSecUsrGrpVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(AppSecUsrGrpVORowImpl object);

        public abstract void put(AppSecUsrGrpVORowImpl object, Object value);

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

    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int USRGRPACTV = AttributesEnum.UsrGrpActv.index();
    public static final int USRGRPDEF = AttributesEnum.UsrGrpDef.index();
    public static final int USRGRPENTID = AttributesEnum.UsrGrpEntId.index();
    public static final int USRGRPID = AttributesEnum.UsrGrpId.index();
    public static final int USRGRPMAILID = AttributesEnum.UsrGrpMailId.index();
    public static final int USRGRPNM = AttributesEnum.UsrGrpNm.index();
    public static final int USRGRPRESV = AttributesEnum.UsrGrpResv.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int APPSECUSRGRPLNK = AttributesEnum.AppSecUsrGrpLnk.index();
    public static final int ORGSECUSRGRP = AttributesEnum.OrgSecUsrGrp.index();
    public static final int USERLOV1 = AttributesEnum.UserLOV1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public AppSecUsrGrpVORowImpl() {
    }

    /**
     * Gets AppSecUsrGrpEO entity object.
     * @return the AppSecUsrGrpEO
     */
    public AppSecUsrGrpEOImpl getAppSecUsrGrpEO() {
        return (AppSecUsrGrpEOImpl)getEntity(ENTITY_APPSECUSRGRPEO);
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
     * Gets the attribute value for USR_GRP_ACTV using the alias name UsrGrpActv.
     * @return the USR_GRP_ACTV
     */
    public String getUsrGrpActv() {
        return (String) getAttributeInternal(USRGRPACTV);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_GRP_ACTV using the alias name UsrGrpActv.
     * @param value value to set the USR_GRP_ACTV
     */
    public void setUsrGrpActv(String value) {
        setAttributeInternal(USRGRPACTV, value);
    }

    /**
     * Gets the attribute value for USR_GRP_DEF using the alias name UsrGrpDef.
     * @return the USR_GRP_DEF
     */
    public String getUsrGrpDef() {
        return (String) getAttributeInternal(USRGRPDEF);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_GRP_DEF using the alias name UsrGrpDef.
     * @param value value to set the USR_GRP_DEF
     */
    public void setUsrGrpDef(String value) {
        setAttributeInternal(USRGRPDEF, value);
    }

    /**
     * Gets the attribute value for USR_GRP_ENT_ID using the alias name UsrGrpEntId.
     * @return the USR_GRP_ENT_ID
     */
    public Long getUsrGrpEntId() {
        return (Long) getAttributeInternal(USRGRPENTID);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_GRP_ENT_ID using the alias name UsrGrpEntId.
     * @param value value to set the USR_GRP_ENT_ID
     */
    public void setUsrGrpEntId(Long value) {
        setAttributeInternal(USRGRPENTID, value);
    }

    /**
     * Gets the attribute value for USR_GRP_ID using the alias name UsrGrpId.
     * @return the USR_GRP_ID
     */
    public Integer getUsrGrpId() {
        return (Integer) getAttributeInternal(USRGRPID);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_GRP_ID using the alias name UsrGrpId.
     * @param value value to set the USR_GRP_ID
     */
    public void setUsrGrpId(Integer value) {
        setAttributeInternal(USRGRPID, value);
    }

    /**
     * Gets the attribute value for USR_GRP_MAIL_ID using the alias name UsrGrpMailId.
     * @return the USR_GRP_MAIL_ID
     */
    public String getUsrGrpMailId() {
        return (String) getAttributeInternal(USRGRPMAILID);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_GRP_MAIL_ID using the alias name UsrGrpMailId.
     * @param value value to set the USR_GRP_MAIL_ID
     */
    public void setUsrGrpMailId(String value) {
        setAttributeInternal(USRGRPMAILID, value);
    }

    /**
     * Gets the attribute value for USR_GRP_NM using the alias name UsrGrpNm.
     * @return the USR_GRP_NM
     */
    public String getUsrGrpNm() {
        return (String) getAttributeInternal(USRGRPNM);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_GRP_NM using the alias name UsrGrpNm.
     * @param value value to set the USR_GRP_NM
     */
    public void setUsrGrpNm(String value) {
        setAttributeInternal(USRGRPNM, value);
    }

    /**
     * Gets the attribute value for USR_GRP_RESV using the alias name UsrGrpResv.
     * @return the USR_GRP_RESV
     */
    public String getUsrGrpResv() {
        return (String) getAttributeInternal(USRGRPRESV);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_GRP_RESV using the alias name UsrGrpResv.
     * @param value value to set the USR_GRP_RESV
     */
    public void setUsrGrpResv(String value) {
        setAttributeInternal(USRGRPRESV, value);
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
     * Gets the attribute value for USR_ID_CREATE_DT using the alias name UsrIdCreateDt.
     * @return the USR_ID_CREATE_DT
     */
    public Timestamp getUsrIdCreateDt() {
        return (Timestamp) getAttributeInternal(USRIDCREATEDT);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_CREATE_DT using the alias name UsrIdCreateDt.
     * @param value value to set the USR_ID_CREATE_DT
     */
    public void setUsrIdCreateDt(Timestamp value) {
        setAttributeInternal(USRIDCREATEDT, value);
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
     * Gets the associated <code>RowIterator</code> using master-detail link AppSecUsrGrpLnk.
     */
    public RowIterator getAppSecUsrGrpLnk() {
        return (RowIterator)getAttributeInternal(APPSECUSRGRPLNK);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link OrgSecUsrGrp.
     */
    public RowIterator getOrgSecUsrGrp() {
        return (RowIterator)getAttributeInternal(ORGSECUSRGRP);
    }

    /**
     * Gets the view accessor <code>RowSet</code> UserLOV1.
     */
    public RowSet getUserLOV1() {
        return (RowSet)getAttributeInternal(USERLOV1);
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
