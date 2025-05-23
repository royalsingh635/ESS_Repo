package bdgkraeoprfapp.model.views;

import bdgkraeoprfapp.model.entities.BdgKraEoPrfEOImpl;

import bdgkraeoprfapp.model.modules.BdgKraEoPrfAMImpl;

import java.sql.Timestamp;

import oracle.jbo.RowSet;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Dec 16 12:03:51 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class BdgKraEoPrfVORowImpl extends ViewRowImpl {
    public static final int ENTITY_BDGKRAEOPRFEO = 0;
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        CurrConvRate,
        CurrIdSp,
        DesgId,
        DocId,
        EoId,
        FyId,
        HoOrgId,
        KraAmt,
        KraId,
        KraRating,
        KraStatus,
        KraType,
        MgrEoId,
        MgrKraDocId,
        OrgId,
        PrdEndDt,
        PrdStartDt,
        Remarks,
        ReplFlg,
        SlocId,
        TargetDt,
        UsrIdCreate,
        UsrIdCreateDt,
        UsrIdMod,
        UsrIdModDt,
        TransCurrDesc,
        TransEmpNm,
        TransKraDesc,
        TransMgrNm,
        TransMgrKraId,
        LovUsrIdVO,
        LovEmpIdVO,
        LovMgrEoIdVO,
        LovCurrIdVO;
        static AttributesEnum[] vals = null;
        ;
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
    public static final int CURRCONVRATE = AttributesEnum.CurrConvRate.index();
    public static final int CURRIDSP = AttributesEnum.CurrIdSp.index();
    public static final int DESGID = AttributesEnum.DesgId.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int EOID = AttributesEnum.EoId.index();
    public static final int FYID = AttributesEnum.FyId.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int KRAAMT = AttributesEnum.KraAmt.index();
    public static final int KRAID = AttributesEnum.KraId.index();
    public static final int KRARATING = AttributesEnum.KraRating.index();
    public static final int KRASTATUS = AttributesEnum.KraStatus.index();
    public static final int KRATYPE = AttributesEnum.KraType.index();
    public static final int MGREOID = AttributesEnum.MgrEoId.index();
    public static final int MGRKRADOCID = AttributesEnum.MgrKraDocId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int PRDENDDT = AttributesEnum.PrdEndDt.index();
    public static final int PRDSTARTDT = AttributesEnum.PrdStartDt.index();
    public static final int REMARKS = AttributesEnum.Remarks.index();
    public static final int REPLFLG = AttributesEnum.ReplFlg.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int TARGETDT = AttributesEnum.TargetDt.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int TRANSCURRDESC = AttributesEnum.TransCurrDesc.index();
    public static final int TRANSEMPNM = AttributesEnum.TransEmpNm.index();
    public static final int TRANSKRADESC = AttributesEnum.TransKraDesc.index();
    public static final int TRANSMGRNM = AttributesEnum.TransMgrNm.index();
    public static final int TRANSMGRKRAID = AttributesEnum.TransMgrKraId.index();
    public static final int LOVUSRIDVO = AttributesEnum.LovUsrIdVO.index();
    public static final int LOVEMPIDVO = AttributesEnum.LovEmpIdVO.index();
    public static final int LOVMGREOIDVO = AttributesEnum.LovMgrEoIdVO.index();
    public static final int LOVCURRIDVO = AttributesEnum.LovCurrIdVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public BdgKraEoPrfVORowImpl() {
    }

    /**
     * Gets BdgKraEoPrfEO entity object.
     * @return the BdgKraEoPrfEO
     */
    public BdgKraEoPrfEOImpl getBdgKraEoPrfEO() {
        return (BdgKraEoPrfEOImpl) getEntity(ENTITY_BDGKRAEOPRFEO);
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
     * Gets the attribute value for CURR_CONV_RATE using the alias name CurrConvRate.
     * @return the CURR_CONV_RATE
     */
    public Number getCurrConvRate() {
        return (Number) getAttributeInternal(CURRCONVRATE);
    }

    /**
     * Sets <code>value</code> as attribute value for CURR_CONV_RATE using the alias name CurrConvRate.
     * @param value value to set the CURR_CONV_RATE
     */
    public void setCurrConvRate(Number value) {
        setAttributeInternal(CURRCONVRATE, value);
    }

    /**
     * Gets the attribute value for CURR_ID_SP using the alias name CurrIdSp.
     * @return the CURR_ID_SP
     */
    public Integer getCurrIdSp() {
        return (Integer) getAttributeInternal(CURRIDSP);
    }

    /**
     * Sets <code>value</code> as attribute value for CURR_ID_SP using the alias name CurrIdSp.
     * @param value value to set the CURR_ID_SP
     */
    public void setCurrIdSp(Integer value) {
        setAttributeInternal(CURRIDSP, value);
    }

    /**
     * Gets the attribute value for DESG_ID using the alias name DesgId.
     * @return the DESG_ID
     */
    public String getDesgId() {
        return (String) getAttributeInternal(DESGID);
    }

    /**
     * Sets <code>value</code> as attribute value for DESG_ID using the alias name DesgId.
     * @param value value to set the DESG_ID
     */
    public void setDesgId(String value) {
        setAttributeInternal(DESGID, value);
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
     * Gets the attribute value for EO_ID using the alias name EoId.
     * @return the EO_ID
     */
    public Integer getEoId() {
        return (Integer) getAttributeInternal(EOID);
    }

    /**
     * Sets <code>value</code> as attribute value for EO_ID using the alias name EoId.
     * @param value value to set the EO_ID
     */
    public void setEoId(Integer value) {
        setAttributeInternal(EOID, value);
    }

    /**
     * Gets the attribute value for FY_ID using the alias name FyId.
     * @return the FY_ID
     */
    public Integer getFyId() {
        return (Integer) getAttributeInternal(FYID);
    }

    /**
     * Sets <code>value</code> as attribute value for FY_ID using the alias name FyId.
     * @param value value to set the FY_ID
     */
    public void setFyId(Integer value) {
        setAttributeInternal(FYID, value);
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
     * Gets the attribute value for KRA_AMT using the alias name KraAmt.
     * @return the KRA_AMT
     */
    public Number getKraAmt() {
        return (Number) getAttributeInternal(KRAAMT);
    }

    /**
     * Sets <code>value</code> as attribute value for KRA_AMT using the alias name KraAmt.
     * @param value value to set the KRA_AMT
     */
    public void setKraAmt(Number value) {
        setAttributeInternal(KRAAMT, value);
    }

    /**
     * Gets the attribute value for KRA_ID using the alias name KraId.
     * @return the KRA_ID
     */
    public String getKraId() {
        return (String) getAttributeInternal(KRAID);
    }

    /**
     * Sets <code>value</code> as attribute value for KRA_ID using the alias name KraId.
     * @param value value to set the KRA_ID
     */
    public void setKraId(String value) {
        setAttributeInternal(KRAID, value);
    }

    /**
     * Gets the attribute value for KRA_RATING using the alias name KraRating.
     * @return the KRA_RATING
     */
    public Number getKraRating() {
        return (Number) getAttributeInternal(KRARATING);
    }

    /**
     * Sets <code>value</code> as attribute value for KRA_RATING using the alias name KraRating.
     * @param value value to set the KRA_RATING
     */
    public void setKraRating(Number value) {
        setAttributeInternal(KRARATING, value);
    }

    /**
     * Gets the attribute value for KRA_STATUS using the alias name KraStatus.
     * @return the KRA_STATUS
     */
    public Integer getKraStatus() {
        return (Integer) getAttributeInternal(KRASTATUS);
    }

    /**
     * Sets <code>value</code> as attribute value for KRA_STATUS using the alias name KraStatus.
     * @param value value to set the KRA_STATUS
     */
    public void setKraStatus(Integer value) {
        setAttributeInternal(KRASTATUS, value);
    }

    /**
     * Gets the attribute value for KRA_TYPE using the alias name KraType.
     * @return the KRA_TYPE
     */
    public String getKraType() {
        return (String) getAttributeInternal(KRATYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for KRA_TYPE using the alias name KraType.
     * @param value value to set the KRA_TYPE
     */
    public void setKraType(String value) {
        setAttributeInternal(KRATYPE, value);
    }

    /**
     * Gets the attribute value for MGR_EO_ID using the alias name MgrEoId.
     * @return the MGR_EO_ID
     */
    public Integer getMgrEoId() {
        return (Integer) getAttributeInternal(MGREOID);
    }

    /**
     * Sets <code>value</code> as attribute value for MGR_EO_ID using the alias name MgrEoId.
     * @param value value to set the MGR_EO_ID
     */
    public void setMgrEoId(Integer value) {
        setAttributeInternal(MGREOID, value);
    }

    /**
     * Gets the attribute value for MGR_KRA_DOC_ID using the alias name MgrKraDocId.
     * @return the MGR_KRA_DOC_ID
     */
    public String getMgrKraDocId() {
        return (String) getAttributeInternal(MGRKRADOCID);
    }

    /**
     * Sets <code>value</code> as attribute value for MGR_KRA_DOC_ID using the alias name MgrKraDocId.
     * @param value value to set the MGR_KRA_DOC_ID
     */
    public void setMgrKraDocId(String value) {
        setAttributeInternal(MGRKRADOCID, value);
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
     * Gets the attribute value for PRD_END_DT using the alias name PrdEndDt.
     * @return the PRD_END_DT
     */
    public Timestamp getPrdEndDt() {
        return (Timestamp) getAttributeInternal(PRDENDDT);
    }

    /**
     * Sets <code>value</code> as attribute value for PRD_END_DT using the alias name PrdEndDt.
     * @param value value to set the PRD_END_DT
     */
    public void setPrdEndDt(Timestamp value) {
        setAttributeInternal(PRDENDDT, value);
    }

    /**
     * Gets the attribute value for PRD_START_DT using the alias name PrdStartDt.
     * @return the PRD_START_DT
     */
    public Timestamp getPrdStartDt() {
        return (Timestamp) getAttributeInternal(PRDSTARTDT);
    }

    /**
     * Sets <code>value</code> as attribute value for PRD_START_DT using the alias name PrdStartDt.
     * @param value value to set the PRD_START_DT
     */
    public void setPrdStartDt(Timestamp value) {
        setAttributeInternal(PRDSTARTDT, value);
    }

    /**
     * Gets the attribute value for REMARKS using the alias name Remarks.
     * @return the REMARKS
     */
    public String getRemarks() {
        return (String) getAttributeInternal(REMARKS);
    }

    /**
     * Sets <code>value</code> as attribute value for REMARKS using the alias name Remarks.
     * @param value value to set the REMARKS
     */
    public void setRemarks(String value) {
        setAttributeInternal(REMARKS, value);
    }

    /**
     * Gets the attribute value for REPL_FLG using the alias name ReplFlg.
     * @return the REPL_FLG
     */
    public String getReplFlg() {
        return (String) getAttributeInternal(REPLFLG);
    }

    /**
     * Sets <code>value</code> as attribute value for REPL_FLG using the alias name ReplFlg.
     * @param value value to set the REPL_FLG
     */
    public void setReplFlg(String value) {
        setAttributeInternal(REPLFLG, value);
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
     * Gets the attribute value for TARGET_DT using the alias name TargetDt.
     * @return the TARGET_DT
     */
    public Timestamp getTargetDt() {
        return (Timestamp) getAttributeInternal(TARGETDT);
    }

    /**
     * Sets <code>value</code> as attribute value for TARGET_DT using the alias name TargetDt.
     * @param value value to set the TARGET_DT
     */
    public void setTargetDt(Timestamp value) {
        setAttributeInternal(TARGETDT, value);
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
     * Gets the attribute value for the calculated attribute TransCurrDesc.
     * @return the TransCurrDesc
     */
    public String getTransCurrDesc() {
        
        return (String) getAttributeInternal(TRANSCURRDESC);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransCurrDesc.
     * @param value value to set the  TransCurrDesc
     */
    public void setTransCurrDesc(String value) {
        setAttributeInternal(TRANSCURRDESC, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransEmpNm.
     * @return the TransEmpNm
     */
    public String getTransEmpNm() {
        return (String) getAttributeInternal(TRANSEMPNM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransEmpNm.
     * @param value value to set the  TransEmpNm
     */
    public void setTransEmpNm(String value) {
        setAttributeInternal(TRANSEMPNM, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransKraDesc.
     * @return the TransKraDesc
     */
    public String getTransKraDesc() {
        return (String) getAttributeInternal(TRANSKRADESC);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransKraDesc.
     * @param value value to set the  TransKraDesc
     */
    public void setTransKraDesc(String value) {
        setAttributeInternal(TRANSKRADESC, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransMgrNm.
     * @return the TransMgrNm
     */
    public String getTransMgrNm() {
        return (String) getAttributeInternal(TRANSMGRNM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransMgrNm.
     * @param value value to set the  TransMgrNm
     */
    public void setTransMgrNm(String value) {
        setAttributeInternal(TRANSMGRNM, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransMgrKraId.
     * @return the TransMgrKraId
     */
    public String getTransMgrKraId() {
        return (String) getAttributeInternal(TRANSMGRKRAID);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovUsrIdVO.
     */
    public RowSet getLovUsrIdVO() {
        return (RowSet) getAttributeInternal(LOVUSRIDVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovEmpIdVO.
     */
    public RowSet getLovEmpIdVO() {
        return (RowSet) getAttributeInternal(LOVEMPIDVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovMgrEoIdVO.
     */
    public RowSet getLovMgrEoIdVO() {
        return (RowSet) getAttributeInternal(LOVMGREOIDVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovCurrIdVO.
     */
    public RowSet getLovCurrIdVO() {
        return (RowSet) getAttributeInternal(LOVCURRIDVO);
    }
}

