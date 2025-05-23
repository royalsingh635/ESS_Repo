package hcmfullandfinalapp.model.views;

import adf.utils.ebiz.EbizParams;

import hcmfullandfinalapp.model.entities.HcmEmpFnfExitQuesEOImpl;

import hcmfullandfinalapp.model.service.HCMFullAndFinalAppAMImpl;

import oracle.jbo.Row;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Jul 27 14:17:09 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class HcmEmpFnfExitQuesVORowImpl extends ViewRowImpl {
    public static final int ENTITY_HCMEMPFNFEXITQUESEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        SlocId,
        HoOrgId,
        OrgId,
        DocId,
        ParamId,
        UsrIdCreate,
        UsrIdCreateDt,
        UsrIdMod,
        UsrIdModDt,
        ExitAns,
        TransQuesDesc;
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
    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int PARAMID = AttributesEnum.ParamId.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int EXITANS = AttributesEnum.ExitAns.index();
    public static final int TRANSQUESDESC = AttributesEnum.TransQuesDesc.index();

    /**
     * This is the default constructor (do not remove).
     */
    public HcmEmpFnfExitQuesVORowImpl() {
    }

    /**
     * Gets HcmEmpFnfExitQuesEO entity object.
     * @return the HcmEmpFnfExitQuesEO
     */
    public HcmEmpFnfExitQuesEOImpl getHcmEmpFnfExitQuesEO() {
        return (HcmEmpFnfExitQuesEOImpl) getEntity(ENTITY_HCMEMPFNFEXITQUESEO);
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
     * Gets the attribute value for DOC_ID using the alias name DocId.
     * @return the DOC_ID
     */
    public String getDocId() {
        return (String) getAttributeInternal(DOCID);
    }

    /**
     * Sets <code>value</code> as attribute value for DOC_ID using the alias name DocId.
     * @param value value to set the DOC_ID
     */
    public void setDocId(String value) {
        setAttributeInternal(DOCID, value);
    }

    /**
     * Gets the attribute value for PARAM_ID using the alias name ParamId.
     * @return the PARAM_ID
     */
    public String getParamId() {
        return (String) getAttributeInternal(PARAMID);
    }

    /**
     * Sets <code>value</code> as attribute value for PARAM_ID using the alias name ParamId.
     * @param value value to set the PARAM_ID
     */
    public void setParamId(String value) {
        setAttributeInternal(PARAMID, value);
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
     * Gets the attribute value for EXIT_ANS using the alias name ExitAns.
     * @return the EXIT_ANS
     */
    public String getExitAns() {
        return (String) getAttributeInternal(EXITANS);
    }

    /**
     * Sets <code>value</code> as attribute value for EXIT_ANS using the alias name ExitAns.
     * @param value value to set the EXIT_ANS
     */
    public void setExitAns(String value) {
        setAttributeInternal(EXITANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransQuesDesc.
     * @return the TransQuesDesc
     */
    public String getTransQuesDesc() {
        if (getParamId() != null) {
            HCMFullAndFinalAppAMImpl am = (HCMFullAndFinalAppAMImpl) this.getApplicationModule();
            am.getExitInterveiwQues1().setNamedWhereClauseParam("BindCldId", EbizParams.GLBL_APP_CLD_ID());
            am.getExitInterveiwQues1().setNamedWhereClauseParam("BindHoOrgId", EbizParams.GLBL_HO_ORG_ID());
            am.getExitInterveiwQues1().setNamedWhereClauseParam("BindOrgId", EbizParams.GLBL_APP_USR_ORG());
            am.getExitInterveiwQues1().setNamedWhereClauseParam("BindSlocId", EbizParams.GLBL_APP_SERV_LOC());
            am.getExitInterveiwQues1().executeQuery();
            Row[] filteredRows = am.getExitInterveiwQues1().getFilteredRows("ParamId", getParamId());
            if (filteredRows.length > 0) {
                return filteredRows[0].getAttribute("ParamDesc").toString();
            }
        }
        return (String) getAttributeInternal(TRANSQUESDESC);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransQuesDesc.
     * @param value value to set the  TransQuesDesc
     */
    public void setTransQuesDesc(String value) {
        setAttributeInternal(TRANSQUESDESC, value);
    }
}

