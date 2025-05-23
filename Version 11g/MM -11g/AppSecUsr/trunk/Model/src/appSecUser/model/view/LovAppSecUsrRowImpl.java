package appSecUser.model.view;

import java.sql.Timestamp;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Jun 16 17:29:55 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LovAppSecUsrRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        SlocId {
            public Object get(LovAppSecUsrRowImpl obj) {
                return obj.getSlocId();
            }

            public void put(LovAppSecUsrRowImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        UsrId {
            public Object get(LovAppSecUsrRowImpl obj) {
                return obj.getUsrId();
            }

            public void put(LovAppSecUsrRowImpl obj, Object value) {
                obj.setUsrId((Long)value);
            }
        }
        ,
        UsrName {
            public Object get(LovAppSecUsrRowImpl obj) {
                return obj.getUsrName();
            }

            public void put(LovAppSecUsrRowImpl obj, Object value) {
                obj.setUsrName((String)value);
            }
        }
        ,
        UsrAlias {
            public Object get(LovAppSecUsrRowImpl obj) {
                return obj.getUsrAlias();
            }

            public void put(LovAppSecUsrRowImpl obj, Object value) {
                obj.setUsrAlias((String)value);
            }
        }
        ,
        UsrPwd {
            public Object get(LovAppSecUsrRowImpl obj) {
                return obj.getUsrPwd();
            }

            public void put(LovAppSecUsrRowImpl obj, Object value) {
                obj.setUsrPwd((String)value);
            }
        }
        ,
        UsrType {
            public Object get(LovAppSecUsrRowImpl obj) {
                return obj.getUsrType();
            }

            public void put(LovAppSecUsrRowImpl obj, Object value) {
                obj.setUsrType((String)value);
            }
        }
        ,
        UsrIdWbl {
            public Object get(LovAppSecUsrRowImpl obj) {
                return obj.getUsrIdWbl();
            }

            public void put(LovAppSecUsrRowImpl obj, Object value) {
                obj.setUsrIdWbl((String)value);
            }
        }
        ,
        UsrLastPwdUpd {
            public Object get(LovAppSecUsrRowImpl obj) {
                return obj.getUsrLastPwdUpd();
            }

            public void put(LovAppSecUsrRowImpl obj, Object value) {
                obj.setUsrLastPwdUpd((Timestamp)value);
            }
        }
        ,
        UsrNextPwdUpd {
            public Object get(LovAppSecUsrRowImpl obj) {
                return obj.getUsrNextPwdUpd();
            }

            public void put(LovAppSecUsrRowImpl obj, Object value) {
                obj.setUsrNextPwdUpd((Timestamp)value);
            }
        }
        ,
        UsrNextPwdAlert {
            public Object get(LovAppSecUsrRowImpl obj) {
                return obj.getUsrNextPwdAlert();
            }

            public void put(LovAppSecUsrRowImpl obj, Object value) {
                obj.setUsrNextPwdAlert((Timestamp)value);
            }
        }
        ,
        UsrResv {
            public Object get(LovAppSecUsrRowImpl obj) {
                return obj.getUsrResv();
            }

            public void put(LovAppSecUsrRowImpl obj, Object value) {
                obj.setUsrResv((String)value);
            }
        }
        ,
        UsrActv {
            public Object get(LovAppSecUsrRowImpl obj) {
                return obj.getUsrActv();
            }

            public void put(LovAppSecUsrRowImpl obj, Object value) {
                obj.setUsrActv((String)value);
            }
        }
        ,
        UsrLangId {
            public Object get(LovAppSecUsrRowImpl obj) {
                return obj.getUsrLangId();
            }

            public void put(LovAppSecUsrRowImpl obj, Object value) {
                obj.setUsrLangId((Integer)value);
            }
        }
        ,
        UsrDtFormat {
            public Object get(LovAppSecUsrRowImpl obj) {
                return obj.getUsrDtFormat();
            }

            public void put(LovAppSecUsrRowImpl obj, Object value) {
                obj.setUsrDtFormat((Integer)value);
            }
        }
        ,
        UsrContactNo {
            public Object get(LovAppSecUsrRowImpl obj) {
                return obj.getUsrContactNo();
            }

            public void put(LovAppSecUsrRowImpl obj, Object value) {
                obj.setUsrContactNo((String)value);
            }
        }
        ,
        UsrContactNoVerified {
            public Object get(LovAppSecUsrRowImpl obj) {
                return obj.getUsrContactNoVerified();
            }

            public void put(LovAppSecUsrRowImpl obj, Object value) {
                obj.setUsrContactNoVerified((String)value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(LovAppSecUsrRowImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(LovAppSecUsrRowImpl obj, Object value) {
                obj.setUsrIdCreate((Long)value);
            }
        }
        ,
        UsrIdCreateDt {
            public Object get(LovAppSecUsrRowImpl obj) {
                return obj.getUsrIdCreateDt();
            }

            public void put(LovAppSecUsrRowImpl obj, Object value) {
                obj.setUsrIdCreateDt((Timestamp)value);
            }
        }
        ,
        UsrIdMod {
            public Object get(LovAppSecUsrRowImpl obj) {
                return obj.getUsrIdMod();
            }

            public void put(LovAppSecUsrRowImpl obj, Object value) {
                obj.setUsrIdMod((Long)value);
            }
        }
        ,
        UsrIdModDt {
            public Object get(LovAppSecUsrRowImpl obj) {
                return obj.getUsrIdModDt();
            }

            public void put(LovAppSecUsrRowImpl obj, Object value) {
                obj.setUsrIdModDt((Timestamp)value);
            }
        }
        ,
        UsrLegId {
            public Object get(LovAppSecUsrRowImpl obj) {
                return obj.getUsrLegId();
            }

            public void put(LovAppSecUsrRowImpl obj, Object value) {
                obj.setUsrLegId((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(LovAppSecUsrRowImpl object);

        public abstract void put(LovAppSecUsrRowImpl object, Object value);

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
    public static final int USRID = AttributesEnum.UsrId.index();
    public static final int USRNAME = AttributesEnum.UsrName.index();
    public static final int USRALIAS = AttributesEnum.UsrAlias.index();
    public static final int USRPWD = AttributesEnum.UsrPwd.index();
    public static final int USRTYPE = AttributesEnum.UsrType.index();
    public static final int USRIDWBL = AttributesEnum.UsrIdWbl.index();
    public static final int USRLASTPWDUPD = AttributesEnum.UsrLastPwdUpd.index();
    public static final int USRNEXTPWDUPD = AttributesEnum.UsrNextPwdUpd.index();
    public static final int USRNEXTPWDALERT = AttributesEnum.UsrNextPwdAlert.index();
    public static final int USRRESV = AttributesEnum.UsrResv.index();
    public static final int USRACTV = AttributesEnum.UsrActv.index();
    public static final int USRLANGID = AttributesEnum.UsrLangId.index();
    public static final int USRDTFORMAT = AttributesEnum.UsrDtFormat.index();
    public static final int USRCONTACTNO = AttributesEnum.UsrContactNo.index();
    public static final int USRCONTACTNOVERIFIED = AttributesEnum.UsrContactNoVerified.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int USRLEGID = AttributesEnum.UsrLegId.index();

    /**
     * This is the default constructor (do not remove).
     */
    public LovAppSecUsrRowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute SlocId.
     * @return the SlocId
     */
    public Integer getSlocId() {
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
     * Gets the attribute value for the calculated attribute UsrId.
     * @return the UsrId
     */
    public Long getUsrId() {
        return (Long) getAttributeInternal(USRID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrId.
     * @param value value to set the  UsrId
     */
    public void setUsrId(Long value) {
        setAttributeInternal(USRID, value);
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
     * Gets the attribute value for the calculated attribute UsrAlias.
     * @return the UsrAlias
     */
    public String getUsrAlias() {
        return (String) getAttributeInternal(USRALIAS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrAlias.
     * @param value value to set the  UsrAlias
     */
    public void setUsrAlias(String value) {
        setAttributeInternal(USRALIAS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrPwd.
     * @return the UsrPwd
     */
    public String getUsrPwd() {
        return (String) getAttributeInternal(USRPWD);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrPwd.
     * @param value value to set the  UsrPwd
     */
    public void setUsrPwd(String value) {
        setAttributeInternal(USRPWD, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrType.
     * @return the UsrType
     */
    public String getUsrType() {
        return (String) getAttributeInternal(USRTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrType.
     * @param value value to set the  UsrType
     */
    public void setUsrType(String value) {
        setAttributeInternal(USRTYPE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrIdWbl.
     * @return the UsrIdWbl
     */
    public String getUsrIdWbl() {
        return (String) getAttributeInternal(USRIDWBL);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrIdWbl.
     * @param value value to set the  UsrIdWbl
     */
    public void setUsrIdWbl(String value) {
        setAttributeInternal(USRIDWBL, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrLastPwdUpd.
     * @return the UsrLastPwdUpd
     */
    public Timestamp getUsrLastPwdUpd() {
        return (Timestamp) getAttributeInternal(USRLASTPWDUPD);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrLastPwdUpd.
     * @param value value to set the  UsrLastPwdUpd
     */
    public void setUsrLastPwdUpd(Timestamp value) {
        setAttributeInternal(USRLASTPWDUPD, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrNextPwdUpd.
     * @return the UsrNextPwdUpd
     */
    public Timestamp getUsrNextPwdUpd() {
        return (Timestamp) getAttributeInternal(USRNEXTPWDUPD);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrNextPwdUpd.
     * @param value value to set the  UsrNextPwdUpd
     */
    public void setUsrNextPwdUpd(Timestamp value) {
        setAttributeInternal(USRNEXTPWDUPD, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrNextPwdAlert.
     * @return the UsrNextPwdAlert
     */
    public Timestamp getUsrNextPwdAlert() {
        return (Timestamp) getAttributeInternal(USRNEXTPWDALERT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrNextPwdAlert.
     * @param value value to set the  UsrNextPwdAlert
     */
    public void setUsrNextPwdAlert(Timestamp value) {
        setAttributeInternal(USRNEXTPWDALERT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrResv.
     * @return the UsrResv
     */
    public String getUsrResv() {
        return (String) getAttributeInternal(USRRESV);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrResv.
     * @param value value to set the  UsrResv
     */
    public void setUsrResv(String value) {
        setAttributeInternal(USRRESV, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrActv.
     * @return the UsrActv
     */
    public String getUsrActv() {
        return (String) getAttributeInternal(USRACTV);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrActv.
     * @param value value to set the  UsrActv
     */
    public void setUsrActv(String value) {
        setAttributeInternal(USRACTV, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrLangId.
     * @return the UsrLangId
     */
    public Integer getUsrLangId() {
        return (Integer) getAttributeInternal(USRLANGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrLangId.
     * @param value value to set the  UsrLangId
     */
    public void setUsrLangId(Integer value) {
        setAttributeInternal(USRLANGID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrDtFormat.
     * @return the UsrDtFormat
     */
    public Integer getUsrDtFormat() {
        return (Integer) getAttributeInternal(USRDTFORMAT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrDtFormat.
     * @param value value to set the  UsrDtFormat
     */
    public void setUsrDtFormat(Integer value) {
        setAttributeInternal(USRDTFORMAT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrContactNo.
     * @return the UsrContactNo
     */
    public String getUsrContactNo() {
        return (String) getAttributeInternal(USRCONTACTNO);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrContactNo.
     * @param value value to set the  UsrContactNo
     */
    public void setUsrContactNo(String value) {
        setAttributeInternal(USRCONTACTNO, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrContactNoVerified.
     * @return the UsrContactNoVerified
     */
    public String getUsrContactNoVerified() {
        return (String) getAttributeInternal(USRCONTACTNOVERIFIED);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrContactNoVerified.
     * @param value value to set the  UsrContactNoVerified
     */
    public void setUsrContactNoVerified(String value) {
        setAttributeInternal(USRCONTACTNOVERIFIED, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrIdCreate.
     * @return the UsrIdCreate
     */
    public Long getUsrIdCreate() {
        return (Long) getAttributeInternal(USRIDCREATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrIdCreate.
     * @param value value to set the  UsrIdCreate
     */
    public void setUsrIdCreate(Long value) {
        setAttributeInternal(USRIDCREATE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrIdCreateDt.
     * @return the UsrIdCreateDt
     */
    public Timestamp getUsrIdCreateDt() {
        return (Timestamp) getAttributeInternal(USRIDCREATEDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrIdCreateDt.
     * @param value value to set the  UsrIdCreateDt
     */
    public void setUsrIdCreateDt(Timestamp value) {
        setAttributeInternal(USRIDCREATEDT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrIdMod.
     * @return the UsrIdMod
     */
    public Long getUsrIdMod() {
        return (Long) getAttributeInternal(USRIDMOD);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrIdMod.
     * @param value value to set the  UsrIdMod
     */
    public void setUsrIdMod(Long value) {
        setAttributeInternal(USRIDMOD, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrIdModDt.
     * @return the UsrIdModDt
     */
    public Timestamp getUsrIdModDt() {
        return (Timestamp) getAttributeInternal(USRIDMODDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrIdModDt.
     * @param value value to set the  UsrIdModDt
     */
    public void setUsrIdModDt(Timestamp value) {
        setAttributeInternal(USRIDMODDT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrLegId.
     * @return the UsrLegId
     */
    public String getUsrLegId() {
        return (String) getAttributeInternal(USRLEGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UsrLegId.
     * @param value value to set the  UsrLegId
     */
    public void setUsrLegId(String value) {
        setAttributeInternal(USRLEGID, value);
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
