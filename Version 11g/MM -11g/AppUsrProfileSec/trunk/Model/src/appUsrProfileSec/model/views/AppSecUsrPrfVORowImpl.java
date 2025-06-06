package appUsrProfileSec.model.views;

import appUsrProfileSec.model.entities.AppSecUsrPrfEOImpl;

import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Jun 16 14:03:11 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppSecUsrPrfVORowImpl extends ViewRowImpl {
    public static final int ENTITY_APPSECUSRPRFEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        SecUsrPrfActv {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getSecUsrPrfActv();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setSecUsrPrfActv((String)value);
            }
        }
        ,
        SecUsrPrfCldId {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getSecUsrPrfCldId();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setSecUsrPrfCldId((String)value);
            }
        }
        ,
        SecUsrPrfDesc {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getSecUsrPrfDesc();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setSecUsrPrfDesc((String)value);
            }
        }
        ,
        SecUsrPrfId {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getSecUsrPrfId();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setSecUsrPrfId((Integer)value);
            }
        }
        ,
        SecUsrPrfMaxLogon {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getSecUsrPrfMaxLogon();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setSecUsrPrfMaxLogon((String)value);
            }
        }
        ,
        SecUsrPrfMaxLogonCnt {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getSecUsrPrfMaxLogonCnt();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setSecUsrPrfMaxLogonCnt((Integer)value);
            }
        }
        ,
        SecUsrPrfPwdCaps {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getSecUsrPrfPwdCaps();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setSecUsrPrfPwdCaps((String)value);
            }
        }
        ,
        SecUsrPrfPwdCapsCnt {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getSecUsrPrfPwdCapsCnt();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setSecUsrPrfPwdCapsCnt((Integer)value);
            }
        }
        ,
        SecUsrPrfPwdMaxLen {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getSecUsrPrfPwdMaxLen();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setSecUsrPrfPwdMaxLen((Integer)value);
            }
        }
        ,
        SecUsrPrfPwdMinLen {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getSecUsrPrfPwdMinLen();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setSecUsrPrfPwdMinLen((Integer)value);
            }
        }
        ,
        SecUsrPrfPwdRenw {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getSecUsrPrfPwdRenw();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setSecUsrPrfPwdRenw((String)value);
            }
        }
        ,
        SecUsrPrfPwdRenwAlert {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getSecUsrPrfPwdRenwAlert();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setSecUsrPrfPwdRenwAlert((Integer)value);
            }
        }
        ,
        SecUsrPrfPwdRenwDays {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getSecUsrPrfPwdRenwDays();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setSecUsrPrfPwdRenwDays((Integer)value);
            }
        }
        ,
        SecUsrPrfPwdRuse {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getSecUsrPrfPwdRuse();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setSecUsrPrfPwdRuse((String)value);
            }
        }
        ,
        SecUsrPrfPwdRuseCnt {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getSecUsrPrfPwdRuseCnt();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setSecUsrPrfPwdRuseCnt((Integer)value);
            }
        }
        ,
        SecUsrPrfPwdSplChar {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getSecUsrPrfPwdSplChar();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setSecUsrPrfPwdSplChar((String)value);
            }
        }
        ,
        SecUsrPrfPwdSplCharCnt {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getSecUsrPrfPwdSplCharCnt();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setSecUsrPrfPwdSplCharCnt((Integer)value);
            }
        }
        ,
        SecUsrPrfResv {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getSecUsrPrfResv();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setSecUsrPrfResv((String)value);
            }
        }
        ,
        SecUsrPrfSlocId {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getSecUsrPrfSlocId();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setSecUsrPrfSlocId((Integer)value);
            }
        }
        ,
        SecUsrPrfUidMaxLen {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getSecUsrPrfUidMaxLen();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setSecUsrPrfUidMaxLen((Integer)value);
            }
        }
        ,
        SecUsrPrfUidMinLen {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getSecUsrPrfUidMinLen();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setSecUsrPrfUidMinLen((Integer)value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setUsrIdCreate((Integer)value);
            }
        }
        ,
        UsrIdCreateDt {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getUsrIdCreateDt();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setUsrIdCreateDt((Timestamp)value);
            }
        }
        ,
        UsrIdMod {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getUsrIdMod();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setUsrIdMod((Integer)value);
            }
        }
        ,
        UsrIdModDt {
            public Object get(AppSecUsrPrfVORowImpl obj) {
                return obj.getUsrIdModDt();
            }

            public void put(AppSecUsrPrfVORowImpl obj, Object value) {
                obj.setUsrIdModDt((Timestamp)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(AppSecUsrPrfVORowImpl object);

        public abstract void put(AppSecUsrPrfVORowImpl object, Object value);

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
    public static final int SECUSRPRFACTV = AttributesEnum.SecUsrPrfActv.index();
    public static final int SECUSRPRFCLDID = AttributesEnum.SecUsrPrfCldId.index();
    public static final int SECUSRPRFDESC = AttributesEnum.SecUsrPrfDesc.index();
    public static final int SECUSRPRFID = AttributesEnum.SecUsrPrfId.index();
    public static final int SECUSRPRFMAXLOGON = AttributesEnum.SecUsrPrfMaxLogon.index();
    public static final int SECUSRPRFMAXLOGONCNT = AttributesEnum.SecUsrPrfMaxLogonCnt.index();
    public static final int SECUSRPRFPWDCAPS = AttributesEnum.SecUsrPrfPwdCaps.index();
    public static final int SECUSRPRFPWDCAPSCNT = AttributesEnum.SecUsrPrfPwdCapsCnt.index();
    public static final int SECUSRPRFPWDMAXLEN = AttributesEnum.SecUsrPrfPwdMaxLen.index();
    public static final int SECUSRPRFPWDMINLEN = AttributesEnum.SecUsrPrfPwdMinLen.index();
    public static final int SECUSRPRFPWDRENW = AttributesEnum.SecUsrPrfPwdRenw.index();
    public static final int SECUSRPRFPWDRENWALERT = AttributesEnum.SecUsrPrfPwdRenwAlert.index();
    public static final int SECUSRPRFPWDRENWDAYS = AttributesEnum.SecUsrPrfPwdRenwDays.index();
    public static final int SECUSRPRFPWDRUSE = AttributesEnum.SecUsrPrfPwdRuse.index();
    public static final int SECUSRPRFPWDRUSECNT = AttributesEnum.SecUsrPrfPwdRuseCnt.index();
    public static final int SECUSRPRFPWDSPLCHAR = AttributesEnum.SecUsrPrfPwdSplChar.index();
    public static final int SECUSRPRFPWDSPLCHARCNT = AttributesEnum.SecUsrPrfPwdSplCharCnt.index();
    public static final int SECUSRPRFRESV = AttributesEnum.SecUsrPrfResv.index();
    public static final int SECUSRPRFSLOCID = AttributesEnum.SecUsrPrfSlocId.index();
    public static final int SECUSRPRFUIDMAXLEN = AttributesEnum.SecUsrPrfUidMaxLen.index();
    public static final int SECUSRPRFUIDMINLEN = AttributesEnum.SecUsrPrfUidMinLen.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();

    /**
     * This is the default constructor (do not remove).
     */
    public AppSecUsrPrfVORowImpl() {
    }

    /**
     * Gets AppSecUsrPrfEO entity object.
     * @return the AppSecUsrPrfEO
     */
    public AppSecUsrPrfEOImpl getAppSecUsrPrfEO() {
        return (AppSecUsrPrfEOImpl)getEntity(ENTITY_APPSECUSRPRFEO);
    }

    /**
     * Gets the attribute value for SEC_USR_PRF_ACTV using the alias name SecUsrPrfActv.
     * @return the SEC_USR_PRF_ACTV
     */
    public String getSecUsrPrfActv() {
        return (String) getAttributeInternal(SECUSRPRFACTV);
    }

    /**
     * Sets <code>value</code> as attribute value for SEC_USR_PRF_ACTV using the alias name SecUsrPrfActv.
     * @param value value to set the SEC_USR_PRF_ACTV
     */
    public void setSecUsrPrfActv(String value) {
        setAttributeInternal(SECUSRPRFACTV, value);
    }

    /**
     * Gets the attribute value for SEC_USR_PRF_CLD_ID using the alias name SecUsrPrfCldId.
     * @return the SEC_USR_PRF_CLD_ID
     */
    public String getSecUsrPrfCldId() {
        return (String) getAttributeInternal(SECUSRPRFCLDID);
    }

    /**
     * Sets <code>value</code> as attribute value for SEC_USR_PRF_CLD_ID using the alias name SecUsrPrfCldId.
     * @param value value to set the SEC_USR_PRF_CLD_ID
     */
    public void setSecUsrPrfCldId(String value) {
        setAttributeInternal(SECUSRPRFCLDID, value);
    }

    /**
     * Gets the attribute value for SEC_USR_PRF_DESC using the alias name SecUsrPrfDesc.
     * @return the SEC_USR_PRF_DESC
     */
    public String getSecUsrPrfDesc() {
        return (String) getAttributeInternal(SECUSRPRFDESC);
    }

    /**
     * Sets <code>value</code> as attribute value for SEC_USR_PRF_DESC using the alias name SecUsrPrfDesc.
     * @param value value to set the SEC_USR_PRF_DESC
     */
    public void setSecUsrPrfDesc(String value) {
        if(value!=null){
           value= value.trim();
        
        //setAttributeInternal(SECUSRPRFDESC, value);
        }
        setAttributeInternal(SECUSRPRFDESC, value.trim());
    }

    /**
     * Gets the attribute value for SEC_USR_PRF_ID using the alias name SecUsrPrfId.
     * @return the SEC_USR_PRF_ID
     */
    public Integer getSecUsrPrfId() {
        return (Integer) getAttributeInternal(SECUSRPRFID);
    }

    /**
     * Sets <code>value</code> as attribute value for SEC_USR_PRF_ID using the alias name SecUsrPrfId.
     * @param value value to set the SEC_USR_PRF_ID
     */
    public void setSecUsrPrfId(Integer value) {
        setAttributeInternal(SECUSRPRFID, value);
    }

    /**
     * Gets the attribute value for SEC_USR_PRF_MAX_LOGON using the alias name SecUsrPrfMaxLogon.
     * @return the SEC_USR_PRF_MAX_LOGON
     */
    public String getSecUsrPrfMaxLogon() {
        return (String) getAttributeInternal(SECUSRPRFMAXLOGON);
    }

    /**
     * Sets <code>value</code> as attribute value for SEC_USR_PRF_MAX_LOGON using the alias name SecUsrPrfMaxLogon.
     * @param value value to set the SEC_USR_PRF_MAX_LOGON
     */
    public void setSecUsrPrfMaxLogon(String value) {
        setAttributeInternal(SECUSRPRFMAXLOGON, value);
    }

    /**
     * Gets the attribute value for SEC_USR_PRF_MAX_LOGON_CNT using the alias name SecUsrPrfMaxLogonCnt.
     * @return the SEC_USR_PRF_MAX_LOGON_CNT
     */
    public Integer getSecUsrPrfMaxLogonCnt() {
        return (Integer) getAttributeInternal(SECUSRPRFMAXLOGONCNT);
    }

    /**
     * Sets <code>value</code> as attribute value for SEC_USR_PRF_MAX_LOGON_CNT using the alias name SecUsrPrfMaxLogonCnt.
     * @param value value to set the SEC_USR_PRF_MAX_LOGON_CNT
     */
    public void setSecUsrPrfMaxLogonCnt(Integer value) {
        setAttributeInternal(SECUSRPRFMAXLOGONCNT, value);
    }

    /**
     * Gets the attribute value for SEC_USR_PRF_PWD_CAPS using the alias name SecUsrPrfPwdCaps.
     * @return the SEC_USR_PRF_PWD_CAPS
     */
    public String getSecUsrPrfPwdCaps() {
        return (String) getAttributeInternal(SECUSRPRFPWDCAPS);
    }

    /**
     * Sets <code>value</code> as attribute value for SEC_USR_PRF_PWD_CAPS using the alias name SecUsrPrfPwdCaps.
     * @param value value to set the SEC_USR_PRF_PWD_CAPS
     */
    public void setSecUsrPrfPwdCaps(String value) {
        setAttributeInternal(SECUSRPRFPWDCAPS, value);
    }

    /**
     * Gets the attribute value for SEC_USR_PRF_PWD_CAPS_CNT using the alias name SecUsrPrfPwdCapsCnt.
     * @return the SEC_USR_PRF_PWD_CAPS_CNT
     */
    public Integer getSecUsrPrfPwdCapsCnt() {
        return (Integer) getAttributeInternal(SECUSRPRFPWDCAPSCNT);
    }

    /**
     * Sets <code>value</code> as attribute value for SEC_USR_PRF_PWD_CAPS_CNT using the alias name SecUsrPrfPwdCapsCnt.
     * @param value value to set the SEC_USR_PRF_PWD_CAPS_CNT
     */
    public void setSecUsrPrfPwdCapsCnt(Integer value) {
        setAttributeInternal(SECUSRPRFPWDCAPSCNT, value);
    }

    /**
     * Gets the attribute value for SEC_USR_PRF_PWD_MAX_LEN using the alias name SecUsrPrfPwdMaxLen.
     * @return the SEC_USR_PRF_PWD_MAX_LEN
     */
    public Integer getSecUsrPrfPwdMaxLen() {
        return (Integer) getAttributeInternal(SECUSRPRFPWDMAXLEN);
    }

    /**
     * Sets <code>value</code> as attribute value for SEC_USR_PRF_PWD_MAX_LEN using the alias name SecUsrPrfPwdMaxLen.
     * @param value value to set the SEC_USR_PRF_PWD_MAX_LEN
     */
    public void setSecUsrPrfPwdMaxLen(Integer value) {
        setAttributeInternal(SECUSRPRFPWDMAXLEN, value);
    }

    /**
     * Gets the attribute value for SEC_USR_PRF_PWD_MIN_LEN using the alias name SecUsrPrfPwdMinLen.
     * @return the SEC_USR_PRF_PWD_MIN_LEN
     */
    public Integer getSecUsrPrfPwdMinLen() {
        return (Integer) getAttributeInternal(SECUSRPRFPWDMINLEN);
    }

    /**
     * Sets <code>value</code> as attribute value for SEC_USR_PRF_PWD_MIN_LEN using the alias name SecUsrPrfPwdMinLen.
     * @param value value to set the SEC_USR_PRF_PWD_MIN_LEN
     */
    public void setSecUsrPrfPwdMinLen(Integer value) {
        setAttributeInternal(SECUSRPRFPWDMINLEN, value);
    }

    /**
     * Gets the attribute value for SEC_USR_PRF_PWD_RENW using the alias name SecUsrPrfPwdRenw.
     * @return the SEC_USR_PRF_PWD_RENW
     */
    public String getSecUsrPrfPwdRenw() {
        return (String) getAttributeInternal(SECUSRPRFPWDRENW);
    }

    /**
     * Sets <code>value</code> as attribute value for SEC_USR_PRF_PWD_RENW using the alias name SecUsrPrfPwdRenw.
     * @param value value to set the SEC_USR_PRF_PWD_RENW
     */
    public void setSecUsrPrfPwdRenw(String value) {
        setAttributeInternal(SECUSRPRFPWDRENW, value);
    }

    /**
     * Gets the attribute value for SEC_USR_PRF_PWD_RENW_ALERT using the alias name SecUsrPrfPwdRenwAlert.
     * @return the SEC_USR_PRF_PWD_RENW_ALERT
     */
    public Integer getSecUsrPrfPwdRenwAlert() {
        return (Integer) getAttributeInternal(SECUSRPRFPWDRENWALERT);
    }

    /**
     * Sets <code>value</code> as attribute value for SEC_USR_PRF_PWD_RENW_ALERT using the alias name SecUsrPrfPwdRenwAlert.
     * @param value value to set the SEC_USR_PRF_PWD_RENW_ALERT
     */
    public void setSecUsrPrfPwdRenwAlert(Integer value) {
        setAttributeInternal(SECUSRPRFPWDRENWALERT, value);
    }

    /**
     * Gets the attribute value for SEC_USR_PRF_PWD_RENW_DAYS using the alias name SecUsrPrfPwdRenwDays.
     * @return the SEC_USR_PRF_PWD_RENW_DAYS
     */
    public Integer getSecUsrPrfPwdRenwDays() {
        return (Integer) getAttributeInternal(SECUSRPRFPWDRENWDAYS);
    }

    /**
     * Sets <code>value</code> as attribute value for SEC_USR_PRF_PWD_RENW_DAYS using the alias name SecUsrPrfPwdRenwDays.
     * @param value value to set the SEC_USR_PRF_PWD_RENW_DAYS
     */
    public void setSecUsrPrfPwdRenwDays(Integer value) {
        setAttributeInternal(SECUSRPRFPWDRENWDAYS, value);
    }

    /**
     * Gets the attribute value for SEC_USR_PRF_PWD_RUSE using the alias name SecUsrPrfPwdRuse.
     * @return the SEC_USR_PRF_PWD_RUSE
     */
    public String getSecUsrPrfPwdRuse() {
        return (String) getAttributeInternal(SECUSRPRFPWDRUSE);
    }

    /**
     * Sets <code>value</code> as attribute value for SEC_USR_PRF_PWD_RUSE using the alias name SecUsrPrfPwdRuse.
     * @param value value to set the SEC_USR_PRF_PWD_RUSE
     */
    public void setSecUsrPrfPwdRuse(String value) {
        setAttributeInternal(SECUSRPRFPWDRUSE, value);
    }

    /**
     * Gets the attribute value for SEC_USR_PRF_PWD_RUSE_CNT using the alias name SecUsrPrfPwdRuseCnt.
     * @return the SEC_USR_PRF_PWD_RUSE_CNT
     */
    public Integer getSecUsrPrfPwdRuseCnt() {
        return (Integer) getAttributeInternal(SECUSRPRFPWDRUSECNT);
    }

    /**
     * Sets <code>value</code> as attribute value for SEC_USR_PRF_PWD_RUSE_CNT using the alias name SecUsrPrfPwdRuseCnt.
     * @param value value to set the SEC_USR_PRF_PWD_RUSE_CNT
     */
    public void setSecUsrPrfPwdRuseCnt(Integer value) {
        setAttributeInternal(SECUSRPRFPWDRUSECNT, value);
    }

    /**
     * Gets the attribute value for SEC_USR_PRF_PWD_SPL_CHAR using the alias name SecUsrPrfPwdSplChar.
     * @return the SEC_USR_PRF_PWD_SPL_CHAR
     */
    public String getSecUsrPrfPwdSplChar() {
        return (String) getAttributeInternal(SECUSRPRFPWDSPLCHAR);
    }

    /**
     * Sets <code>value</code> as attribute value for SEC_USR_PRF_PWD_SPL_CHAR using the alias name SecUsrPrfPwdSplChar.
     * @param value value to set the SEC_USR_PRF_PWD_SPL_CHAR
     */
    public void setSecUsrPrfPwdSplChar(String value) {
        setAttributeInternal(SECUSRPRFPWDSPLCHAR, value);
    }

    /**
     * Gets the attribute value for SEC_USR_PRF_PWD_SPL_CHAR_CNT using the alias name SecUsrPrfPwdSplCharCnt.
     * @return the SEC_USR_PRF_PWD_SPL_CHAR_CNT
     */
    public Integer getSecUsrPrfPwdSplCharCnt() {
        return (Integer) getAttributeInternal(SECUSRPRFPWDSPLCHARCNT);
    }

    /**
     * Sets <code>value</code> as attribute value for SEC_USR_PRF_PWD_SPL_CHAR_CNT using the alias name SecUsrPrfPwdSplCharCnt.
     * @param value value to set the SEC_USR_PRF_PWD_SPL_CHAR_CNT
     */
    public void setSecUsrPrfPwdSplCharCnt(Integer value) {
        setAttributeInternal(SECUSRPRFPWDSPLCHARCNT, value);
    }

    /**
     * Gets the attribute value for SEC_USR_PRF_RESV using the alias name SecUsrPrfResv.
     * @return the SEC_USR_PRF_RESV
     */
    public String getSecUsrPrfResv() {
        return (String) getAttributeInternal(SECUSRPRFRESV);
    }

    /**
     * Sets <code>value</code> as attribute value for SEC_USR_PRF_RESV using the alias name SecUsrPrfResv.
     * @param value value to set the SEC_USR_PRF_RESV
     */
    public void setSecUsrPrfResv(String value) {
        setAttributeInternal(SECUSRPRFRESV, value);
    }

    /**
     * Gets the attribute value for SEC_USR_PRF_SLOC_ID using the alias name SecUsrPrfSlocId.
     * @return the SEC_USR_PRF_SLOC_ID
     */
    public Integer getSecUsrPrfSlocId() {
        return (Integer) getAttributeInternal(SECUSRPRFSLOCID);
    }

    /**
     * Sets <code>value</code> as attribute value for SEC_USR_PRF_SLOC_ID using the alias name SecUsrPrfSlocId.
     * @param value value to set the SEC_USR_PRF_SLOC_ID
     */
    public void setSecUsrPrfSlocId(Integer value) {
        setAttributeInternal(SECUSRPRFSLOCID, value);
    }

    /**
     * Gets the attribute value for SEC_USR_PRF_UID_MAX_LEN using the alias name SecUsrPrfUidMaxLen.
     * @return the SEC_USR_PRF_UID_MAX_LEN
     */
    public Integer getSecUsrPrfUidMaxLen() {
        return (Integer) getAttributeInternal(SECUSRPRFUIDMAXLEN);
    }

    /**
     * Sets <code>value</code> as attribute value for SEC_USR_PRF_UID_MAX_LEN using the alias name SecUsrPrfUidMaxLen.
     * @param value value to set the SEC_USR_PRF_UID_MAX_LEN
     */
    public void setSecUsrPrfUidMaxLen(Integer value) {
        setAttributeInternal(SECUSRPRFUIDMAXLEN, value);
    }

    /**
     * Gets the attribute value for SEC_USR_PRF_UID_MIN_LEN using the alias name SecUsrPrfUidMinLen.
     * @return the SEC_USR_PRF_UID_MIN_LEN
     */
    public Integer getSecUsrPrfUidMinLen() {
        return (Integer) getAttributeInternal(SECUSRPRFUIDMINLEN);
    }

    /**
     * Sets <code>value</code> as attribute value for SEC_USR_PRF_UID_MIN_LEN using the alias name SecUsrPrfUidMinLen.
     * @param value value to set the SEC_USR_PRF_UID_MIN_LEN
     */
    public void setSecUsrPrfUidMinLen(Integer value) {
        setAttributeInternal(SECUSRPRFUIDMINLEN, value);
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
