package schemepolicyapp.model.views;

import adf.utils.ebiz.EbizParams;

import adf.utils.ebiz.EbizParamsMMUtils;

import oracle.jbo.RowSet;
import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewRowImpl;

import schemepolicyapp.model.service.SchemePolicyAppAMImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Jun 02 17:34:02 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class SearchVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Selectobjects0,
        SchemeBasisTrans,
        EoNmTrans,
        ExecNmTrans,
        ValidToTrans,
        ValidFromTrans,
        EoIdTrans,
        ExecIdTrans,
        CldIdTrans,
        HoOrgIdTrans,
        OrgIdTrans,
        SlocIdTrans,
        catgIdTrans,
        SchmIdTrans,
        SchmNmTrans,
        SchmDocIdTrans,
        FyIdTrans,
        LovSchmeBasicVO1,
        LOVCustVO1,
        LovExecIdToNmVO1,
        LovEoCatgVO1,
        LOVSchemeIdVO1;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int SELECTOBJECTS0 = AttributesEnum.Selectobjects0.index();
    public static final int SCHEMEBASISTRANS = AttributesEnum.SchemeBasisTrans.index();
    public static final int EONMTRANS = AttributesEnum.EoNmTrans.index();
    public static final int EXECNMTRANS = AttributesEnum.ExecNmTrans.index();
    public static final int VALIDTOTRANS = AttributesEnum.ValidToTrans.index();
    public static final int VALIDFROMTRANS = AttributesEnum.ValidFromTrans.index();
    public static final int EOIDTRANS = AttributesEnum.EoIdTrans.index();
    public static final int EXECIDTRANS = AttributesEnum.ExecIdTrans.index();
    public static final int CLDIDTRANS = AttributesEnum.CldIdTrans.index();
    public static final int HOORGIDTRANS = AttributesEnum.HoOrgIdTrans.index();
    public static final int ORGIDTRANS = AttributesEnum.OrgIdTrans.index();
    public static final int SLOCIDTRANS = AttributesEnum.SlocIdTrans.index();
    public static final int CATGIDTRANS = AttributesEnum.catgIdTrans.index();
    public static final int SCHMIDTRANS = AttributesEnum.SchmIdTrans.index();
    public static final int SCHMNMTRANS = AttributesEnum.SchmNmTrans.index();
    public static final int SCHMDOCIDTRANS = AttributesEnum.SchmDocIdTrans.index();
    public static final int FYIDTRANS = AttributesEnum.FyIdTrans.index();
    public static final int LOVSCHMEBASICVO1 = AttributesEnum.LovSchmeBasicVO1.index();
    public static final int LOVCUSTVO1 = AttributesEnum.LOVCustVO1.index();
    public static final int LOVEXECIDTONMVO1 = AttributesEnum.LovExecIdToNmVO1.index();
    public static final int LOVEOCATGVO1 = AttributesEnum.LovEoCatgVO1.index();
    public static final int LOVSCHEMEIDVO1 = AttributesEnum.LOVSchemeIdVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public SearchVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute Selectobjects0.
     * @return the Selectobjects0
     */
    public String getSelectobjects0() {
        return (String) getAttributeInternal(SELECTOBJECTS0);
    }

    /**
     * Gets the attribute value for the calculated attribute SchemeBasisTrans.
     * @return the SchemeBasisTrans
     */
    public Integer getSchemeBasisTrans() {
        return (Integer) getAttributeInternal(SCHEMEBASISTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SchemeBasisTrans.
     * @param value value to set the  SchemeBasisTrans
     */
    public void setSchemeBasisTrans(Integer value) {
        setAttributeInternal(SCHEMEBASISTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute EoNmTrans.
     * @return the EoNmTrans
     */
    public String getEoNmTrans() {
        return (String) getAttributeInternal(EONMTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute EoNmTrans.
     * @param value value to set the  EoNmTrans
     */
    public void setEoNmTrans(String value) {
        setAttributeInternal(EONMTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ExecNmTrans.
     * @return the ExecNmTrans
     */
    public String getExecNmTrans() {
        return (String) getAttributeInternal(EXECNMTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ExecNmTrans.
     * @param value value to set the  ExecNmTrans
     */
    public void setExecNmTrans(String value) {
        setAttributeInternal(EXECNMTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ValidToTrans.
     * @return the ValidToTrans
     */
    public Date getValidToTrans() {
        return (Date) getAttributeInternal(VALIDTOTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ValidToTrans.
     * @param value value to set the  ValidToTrans
     */
    public void setValidToTrans(Date value) {
        setAttributeInternal(VALIDTOTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ValidFromTrans.
     * @return the ValidFromTrans
     */
    public Date getValidFromTrans() {
        return (Date) getAttributeInternal(VALIDFROMTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ValidFromTrans.
     * @param value value to set the  ValidFromTrans
     */
    public void setValidFromTrans(Date value) {
        setAttributeInternal(VALIDFROMTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute EoIdTrans.
     * @return the EoIdTrans
     */
    public Integer getEoIdTrans() {
        return (Integer) getAttributeInternal(EOIDTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute EoIdTrans.
     * @param value value to set the  EoIdTrans
     */
    public void setEoIdTrans(Integer value) {
        setAttributeInternal(EOIDTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ExecIdTrans.
     * @return the ExecIdTrans
     */
    public Integer getExecIdTrans() {
        return (Integer) getAttributeInternal(EXECIDTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ExecIdTrans.
     * @param value value to set the  ExecIdTrans
     */
    public void setExecIdTrans(Integer value) {
        setAttributeInternal(EXECIDTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CldIdTrans.
     * @return the CldIdTrans
     */
    public String getCldIdTrans() {
     return   EbizParams.GLBL_APP_CLD_ID();
       // return (String) getAttributeInternal(CLDIDTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CldIdTrans.
     * @param value value to set the  CldIdTrans
     */
    public void setCldIdTrans(String value) {
        setAttributeInternal(CLDIDTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute HoOrgIdTrans.
     * @return the HoOrgIdTrans
     */
    public String getHoOrgIdTrans() {
        return EbizParams.GLBL_HO_ORG_ID();
       // return (String) getAttributeInternal(HOORGIDTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute HoOrgIdTrans.
     * @param value value to set the  HoOrgIdTrans
     */
    public void setHoOrgIdTrans(String value) {
        
        setAttributeInternal(HOORGIDTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute OrgIdTrans.
     * @return the OrgIdTrans
     */
    public String getOrgIdTrans() {
        return EbizParams.GLBL_APP_USR_ORG();
        //return (String) getAttributeInternal(ORGIDTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute OrgIdTrans.
     * @param value value to set the  OrgIdTrans
     */
    public void setOrgIdTrans(String value) {
        setAttributeInternal(ORGIDTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SlocIdTrans.
     * @return the SlocIdTrans
     */
    public Integer getSlocIdTrans() {
        return (Integer) EbizParams.GLBL_APP_SERV_LOC();
        //return (Integer) getAttributeInternal(SLOCIDTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SlocIdTrans.
     * @param value value to set the  SlocIdTrans
     */
    public void setSlocIdTrans(Integer value) {
        setAttributeInternal(SLOCIDTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute catgIdTrans.
     * @return the catgIdTrans
     */
    public Integer getcatgIdTrans() {
        return (Integer) getAttributeInternal(CATGIDTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute catgIdTrans.
     * @param value value to set the  catgIdTrans
     */
    public void setcatgIdTrans(Integer value) {
        setAttributeInternal(CATGIDTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SchmIdTrans.
     * @return the SchmIdTrans
     */
    public String getSchmIdTrans() {
        return (String) getAttributeInternal(SCHMIDTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SchmIdTrans.
     * @param value value to set the  SchmIdTrans
     */
    public void setSchmIdTrans(String value) {
        setAttributeInternal(SCHMIDTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SchmNmTrans.
     * @return the SchmNmTrans
     */
    public String getSchmNmTrans() {
        return (String) getAttributeInternal(SCHMNMTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SchmNmTrans.
     * @param value value to set the  SchmNmTrans
     */
    public void setSchmNmTrans(String value) {
        setAttributeInternal(SCHMNMTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SchmDocIdTrans.
     * @return the SchmDocIdTrans
     */
    public String getSchmDocIdTrans() {
        return (String) getAttributeInternal(SCHMDOCIDTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SchmDocIdTrans.
     * @param value value to set the  SchmDocIdTrans
     */
    public void setSchmDocIdTrans(String value) {
        setAttributeInternal(SCHMDOCIDTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute FyIdTrans.
     * @return the FyIdTrans
     */
    public Integer getFyIdTrans() {
        return(Integer) EbizParams.getFyIdOnCurrDtAndCurrOrg((SchemePolicyAppAMImpl)getApplicationModule());
      //  return (Integer) getAttributeInternal(FYIDTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute FyIdTrans.
     * @param value value to set the  FyIdTrans
     */
    public void setFyIdTrans(Integer value) {
        setAttributeInternal(FYIDTRANS, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovSchmeBasicVO1.
     */
    public RowSet getLovSchmeBasicVO1() {
        return (RowSet) getAttributeInternal(LOVSCHMEBASICVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVCustVO1.
     */
    public RowSet getLOVCustVO1() {
        return (RowSet) getAttributeInternal(LOVCUSTVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovExecIdToNmVO1.
     */
    public RowSet getLovExecIdToNmVO1() {
        return (RowSet) getAttributeInternal(LOVEXECIDTONMVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovEoCatgVO1.
     */
    public RowSet getLovEoCatgVO1() {
        return (RowSet) getAttributeInternal(LOVEOCATGVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVSchemeIdVO1.
     */
    public RowSet getLOVSchemeIdVO1() {
        return (RowSet) getAttributeInternal(LOVSCHEMEIDVO1);
    }
}

