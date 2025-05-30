package hcmempprfsetup.model.views;

import hcmempprfsetup.model.entities.HcmEmpLangEOImpl;

import hcmempprfsetup.model.modules.HcmEmpPrfAMImpl;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Sep 29 12:55:13 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class HcmEmpLangVORowImpl extends ViewRowImpl
{
    public static final int ENTITY_HCMEMPLANGEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum
    {
        CldId,
        DocId,
        EmpCode,
        EmpLangId,
        EmpLangRd,
        EmpLangSp,
        EmpLangWr,
        HoOrgId,
        OrgId,
        SlocId,
        UsrIdCreate,
        UsrIdCreateDt,
        UsrIdMod,
        UsrIdModDt,
        transLangNm,
        LovLangNmVO1;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public int index()
        {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex()
        {
            return firstIndex;
        }

        public static int count()
        {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues()
        {
            if (vals == null)
            {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }
    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int EMPCODE = AttributesEnum.EmpCode.index();
    public static final int EMPLANGID = AttributesEnum.EmpLangId.index();
    public static final int EMPLANGRD = AttributesEnum.EmpLangRd.index();
    public static final int EMPLANGSP = AttributesEnum.EmpLangSp.index();
    public static final int EMPLANGWR = AttributesEnum.EmpLangWr.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int TRANSLANGNM = AttributesEnum.transLangNm.index();
    public static final int LOVLANGNMVO1 = AttributesEnum.LovLangNmVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public HcmEmpLangVORowImpl()
    {
    }

    /**
     * Gets HcmEmpLangEO entity object.
     * @return the HcmEmpLangEO
     */
    public HcmEmpLangEOImpl getHcmEmpLangEO()
    {
        return (HcmEmpLangEOImpl) getEntity(ENTITY_HCMEMPLANGEO);
    }

    /**
     * Gets the attribute value for CLD_ID using the alias name CldId.
     * @return the CLD_ID
     */
    public String getCldId()
    {
        return (String) getAttributeInternal(CLDID);
    }

    /**
     * Sets <code>value</code> as attribute value for CLD_ID using the alias name CldId.
     * @param value value to set the CLD_ID
     */
    public void setCldId(String value)
    {
        setAttributeInternal(CLDID, value);
    }

    /**
     * Gets the attribute value for DOC_ID using the alias name DocId.
     * @return the DOC_ID
     */
    public String getDocId()
    {
        return (String) getAttributeInternal(DOCID);
    }

    /**
     * Sets <code>value</code> as attribute value for DOC_ID using the alias name DocId.
     * @param value value to set the DOC_ID
     */
    public void setDocId(String value)
    {
        setAttributeInternal(DOCID, value);
    }

    /**
     * Gets the attribute value for EMP_CODE using the alias name EmpCode.
     * @return the EMP_CODE
     */
    public Number getEmpCode()
    {
        return (Number) getAttributeInternal(EMPCODE);
    }

    /**
     * Sets <code>value</code> as attribute value for EMP_CODE using the alias name EmpCode.
     * @param value value to set the EMP_CODE
     */
    public void setEmpCode(Number value)
    {
        setAttributeInternal(EMPCODE, value);
    }

    /**
     * Gets the attribute value for EMP_LANG_ID using the alias name EmpLangId.
     * @return the EMP_LANG_ID
     */
    public String getEmpLangId()
    {
        return (String) getAttributeInternal(EMPLANGID);
    }

    /**
     * Sets <code>value</code> as attribute value for EMP_LANG_ID using the alias name EmpLangId.
     * @param value value to set the EMP_LANG_ID
     */
    public void setEmpLangId(String value)
    {
        setAttributeInternal(EMPLANGID, value);
    }

    /**
     * Gets the attribute value for EMP_LANG_RD using the alias name EmpLangRd.
     * @return the EMP_LANG_RD
     */
    public String getEmpLangRd()
    {
        return (String) getAttributeInternal(EMPLANGRD);
    }

    /**
     * Sets <code>value</code> as attribute value for EMP_LANG_RD using the alias name EmpLangRd.
     * @param value value to set the EMP_LANG_RD
     */
    public void setEmpLangRd(String value)
    {
        setAttributeInternal(EMPLANGRD, value);
    }

    /**
     * Gets the attribute value for EMP_LANG_SP using the alias name EmpLangSp.
     * @return the EMP_LANG_SP
     */
    public String getEmpLangSp()
    {
        return (String) getAttributeInternal(EMPLANGSP);
    }

    /**
     * Sets <code>value</code> as attribute value for EMP_LANG_SP using the alias name EmpLangSp.
     * @param value value to set the EMP_LANG_SP
     */
    public void setEmpLangSp(String value)
    {
        setAttributeInternal(EMPLANGSP, value);
    }

    /**
     * Gets the attribute value for EMP_LANG_WR using the alias name EmpLangWr.
     * @return the EMP_LANG_WR
     */
    public String getEmpLangWr()
    {
        return (String) getAttributeInternal(EMPLANGWR);
    }

    /**
     * Sets <code>value</code> as attribute value for EMP_LANG_WR using the alias name EmpLangWr.
     * @param value value to set the EMP_LANG_WR
     */
    public void setEmpLangWr(String value)
    {
        setAttributeInternal(EMPLANGWR, value);
    }

    /**
     * Gets the attribute value for HO_ORG_ID using the alias name HoOrgId.
     * @return the HO_ORG_ID
     */
    public String getHoOrgId()
    {
        return (String) getAttributeInternal(HOORGID);
    }

    /**
     * Sets <code>value</code> as attribute value for HO_ORG_ID using the alias name HoOrgId.
     * @param value value to set the HO_ORG_ID
     */
    public void setHoOrgId(String value)
    {
        setAttributeInternal(HOORGID, value);
    }

    /**
     * Gets the attribute value for ORG_ID using the alias name OrgId.
     * @return the ORG_ID
     */
    public String getOrgId()
    {
        return (String) getAttributeInternal(ORGID);
    }

    /**
     * Sets <code>value</code> as attribute value for ORG_ID using the alias name OrgId.
     * @param value value to set the ORG_ID
     */
    public void setOrgId(String value)
    {
        setAttributeInternal(ORGID, value);
    }

    /**
     * Gets the attribute value for SLOC_ID using the alias name SlocId.
     * @return the SLOC_ID
     */
    public Integer getSlocId()
    {
        return (Integer) getAttributeInternal(SLOCID);
    }

    /**
     * Sets <code>value</code> as attribute value for SLOC_ID using the alias name SlocId.
     * @param value value to set the SLOC_ID
     */
    public void setSlocId(Integer value)
    {
        setAttributeInternal(SLOCID, value);
    }

    /**
     * Gets the attribute value for USR_ID_CREATE using the alias name UsrIdCreate.
     * @return the USR_ID_CREATE
     */
    public Integer getUsrIdCreate()
    {
        return (Integer) getAttributeInternal(USRIDCREATE);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_CREATE using the alias name UsrIdCreate.
     * @param value value to set the USR_ID_CREATE
     */
    public void setUsrIdCreate(Integer value)
    {
        setAttributeInternal(USRIDCREATE, value);
    }

    /**
     * Gets the attribute value for USR_ID_CREATE_DT using the alias name UsrIdCreateDt.
     * @return the USR_ID_CREATE_DT
     */
    public Timestamp getUsrIdCreateDt()
    {
        return (Timestamp) getAttributeInternal(USRIDCREATEDT);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_CREATE_DT using the alias name UsrIdCreateDt.
     * @param value value to set the USR_ID_CREATE_DT
     */
    public void setUsrIdCreateDt(Timestamp value)
    {
        setAttributeInternal(USRIDCREATEDT, value);
    }

    /**
     * Gets the attribute value for USR_ID_MOD using the alias name UsrIdMod.
     * @return the USR_ID_MOD
     */
    public Integer getUsrIdMod()
    {
        return (Integer) getAttributeInternal(USRIDMOD);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_MOD using the alias name UsrIdMod.
     * @param value value to set the USR_ID_MOD
     */
    public void setUsrIdMod(Integer value)
    {
        setAttributeInternal(USRIDMOD, value);
    }

    /**
     * Gets the attribute value for USR_ID_MOD_DT using the alias name UsrIdModDt.
     * @return the USR_ID_MOD_DT
     */
    public Timestamp getUsrIdModDt()
    {
        return (Timestamp) getAttributeInternal(USRIDMODDT);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_MOD_DT using the alias name UsrIdModDt.
     * @param value value to set the USR_ID_MOD_DT
     */
    public void setUsrIdModDt(Timestamp value)
    {
        setAttributeInternal(USRIDMODDT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute transLangNm.
     * @return the transLangNm
     */
    public String gettransLangNm()
    {
        String langNm = "";
        if (getEmpLangId() != null && getEmpLangId().toString().length() > 0)
        {
            HcmEmpPrfAMImpl am = (HcmEmpPrfAMImpl) this.getApplicationModule();
            Row[] filteredRows = am.getLovLangNm1().getFilteredRows("ParamId", getEmpLangId());
            if (filteredRows.length > 0) 
            {
                langNm = filteredRows[0].getAttribute("ParamDesc").toString(); 
                if(langNm!=null)
                {
                    return langNm;
                }
            }
        }
        return (String) getAttributeInternal(TRANSLANGNM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute transLangNm.
     * @param value value to set the  transLangNm
     */
    public void settransLangNm(String value)
    {
        setAttributeInternal(TRANSLANGNM, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovLangNmVO1.
     */
    public RowSet getLovLangNmVO1()
    {
        return (RowSet) getAttributeInternal(LOVLANGNMVO1);
    }
}

