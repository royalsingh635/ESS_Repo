package hcmattendancerulesetup.model.views;

import hcmattendancerulesetup.model.entities.HcmAttenRegRuleEOImpl;

import hcmattendancerulesetup.model.module.HCMAttendanceRuleSetUpAMImpl;

import oracle.jbo.domain.NClobDomain;
import oracle.jbo.domain.Timestamp;

import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Jul 17 16:24:28 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class HcmAttenRegRuleVORowImpl extends ViewRowImpl {


    public static final int ENTITY_HCMATTENREGRULEEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        SlocId,
        HoOrgId,
        OrgId,
        RuleId,
        DesgId,
        ShiftId,
        DocId,
        GraceTm,
        MaxAllow,
        ReglrChk,
        LeaveAdjDay,
        ValidStrtDt,
        ValidEndDt,
        UsrIdCreate,
        UsrIdCreateDt,
        UsrIdMod,
        UsrIdModDt,
        TransDesgNm,
        TransShiftStrtTm,
        TransShiftEndTm,
        HcmAttenRegRuleLeave,
        LovDesignationVO1,
        LovRuleTypeVO1,
        LovShiftVO1;
        static AttributesEnum[] vals = null; ;
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
    public static final int RULEID = AttributesEnum.RuleId.index();
    public static final int DESGID = AttributesEnum.DesgId.index();
    public static final int SHIFTID = AttributesEnum.ShiftId.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int GRACETM = AttributesEnum.GraceTm.index();
    public static final int MAXALLOW = AttributesEnum.MaxAllow.index();
    public static final int REGLRCHK = AttributesEnum.ReglrChk.index();
    public static final int LEAVEADJDAY = AttributesEnum.LeaveAdjDay.index();
    public static final int VALIDSTRTDT = AttributesEnum.ValidStrtDt.index();
    public static final int VALIDENDDT = AttributesEnum.ValidEndDt.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int TRANSDESGNM = AttributesEnum.TransDesgNm.index();
    public static final int TRANSSHIFTSTRTTM = AttributesEnum.TransShiftStrtTm.index();
    public static final int TRANSSHIFTENDTM = AttributesEnum.TransShiftEndTm.index();
    public static final int HCMATTENREGRULELEAVE = AttributesEnum.HcmAttenRegRuleLeave.index();
    public static final int LOVDESIGNATIONVO1 = AttributesEnum.LovDesignationVO1.index();
    public static final int LOVRULETYPEVO1 = AttributesEnum.LovRuleTypeVO1.index();
    public static final int LOVSHIFTVO1 = AttributesEnum.LovShiftVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public HcmAttenRegRuleVORowImpl() {
    }

    /**
     * Gets HcmAttenRegRuleEO entity object.
     * @return the HcmAttenRegRuleEO
     */
    public HcmAttenRegRuleEOImpl getHcmAttenRegRuleEO() {
        return (HcmAttenRegRuleEOImpl) getEntity(ENTITY_HCMATTENREGRULEEO);
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
     * Gets the attribute value for RULE_ID using the alias name RuleId.
     * @return the RULE_ID
     */
    public Integer getRuleId() {
        return (Integer) getAttributeInternal(RULEID);
    }

    /**
     * Sets <code>value</code> as attribute value for RULE_ID using the alias name RuleId.
     * @param value value to set the RULE_ID
     */
    public void setRuleId(Integer value) {
        setAttributeInternal(RULEID, value);
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
     * Gets the attribute value for SHIFT_ID using the alias name ShiftId.
     * @return the SHIFT_ID
     */
    public String getShiftId() {
        return (String) getAttributeInternal(SHIFTID);
    }

    /**
     * Sets <code>value</code> as attribute value for SHIFT_ID using the alias name ShiftId.
     * @param value value to set the SHIFT_ID
     */
    public void setShiftId(String value) {
        setAttributeInternal(SHIFTID, value);
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
     * Gets the attribute value for GRACE_TM using the alias name GraceTm.
     * @return the GRACE_TM
     */
    public Number getGraceTm() {
        return (Number) getAttributeInternal(GRACETM);
    }

    /**
     * Sets <code>value</code> as attribute value for GRACE_TM using the alias name GraceTm.
     * @param value value to set the GRACE_TM
     */
    public void setGraceTm(Number value) {
        setAttributeInternal(GRACETM, value);
    }

    /**
     * Gets the attribute value for MAX_ALLOW using the alias name MaxAllow.
     * @return the MAX_ALLOW
     */
    public Integer getMaxAllow() {
        return (Integer) getAttributeInternal(MAXALLOW);
    }

    /**
     * Sets <code>value</code> as attribute value for MAX_ALLOW using the alias name MaxAllow.
     * @param value value to set the MAX_ALLOW
     */
    public void setMaxAllow(Integer value) {
        setAttributeInternal(MAXALLOW, value);
    }

    /**
     * Gets the attribute value for REGLR_CHK using the alias name ReglrChk.
     * @return the REGLR_CHK
     */
    public String getReglrChk() {
        return (String) getAttributeInternal(REGLRCHK);
    }

    /**
     * Sets <code>value</code> as attribute value for REGLR_CHK using the alias name ReglrChk.
     * @param value value to set the REGLR_CHK
     */
    public void setReglrChk(String value) {
        setAttributeInternal(REGLRCHK, value);
    }

    /**
     * Gets the attribute value for LEAVE_ADJ_DAY using the alias name LeaveAdjDay.
     * @return the LEAVE_ADJ_DAY
     */
    public Number getLeaveAdjDay() {
        return (Number) getAttributeInternal(LEAVEADJDAY);
    }

    /**
     * Sets <code>value</code> as attribute value for LEAVE_ADJ_DAY using the alias name LeaveAdjDay.
     * @param value value to set the LEAVE_ADJ_DAY
     */
    public void setLeaveAdjDay(Number value) {
        setAttributeInternal(LEAVEADJDAY, value);
    }

    /**
     * Gets the attribute value for VALID_STRT_DT using the alias name ValidStrtDt.
     * @return the VALID_STRT_DT
     */
    public Timestamp getValidStrtDt() {
        return (Timestamp) getAttributeInternal(VALIDSTRTDT);
    }

    /**
     * Sets <code>value</code> as attribute value for VALID_STRT_DT using the alias name ValidStrtDt.
     * @param value value to set the VALID_STRT_DT
     */
    public void setValidStrtDt(Timestamp value) {
        setAttributeInternal(VALIDSTRTDT, value);
    }

    /**
     * Gets the attribute value for VALID_END_DT using the alias name ValidEndDt.
     * @return the VALID_END_DT
     */
    public Timestamp getValidEndDt() {
        return (Timestamp) getAttributeInternal(VALIDENDDT);
    }

    /**
     * Sets <code>value</code> as attribute value for VALID_END_DT using the alias name ValidEndDt.
     * @param value value to set the VALID_END_DT
     */
    public void setValidEndDt(Timestamp value) {
        setAttributeInternal(VALIDENDDT, value);
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
     * Gets the attribute value for the calculated attribute TransDesgNm.
     * @return the TransDesgNm
     */
    public String getTransDesgNm() {
        if (this.getDesgId() != null) {
            HCMAttendanceRuleSetUpAMImpl am = (HCMAttendanceRuleSetUpAMImpl) this.getApplicationModule();
            am.getLovDesignation1().setNamedWhereClauseParam("BindCldId", getCldId());
            am.getLovDesignation1().setNamedWhereClauseParam("BindHoOrgId", getHoOrgId());
            am.getLovDesignation1().setNamedWhereClauseParam("BindOrgId", getOrgId());
            am.getLovDesignation1().setNamedWhereClauseParam("BindSlocId", getSlocId());
            am.getLovDesignation1().executeQuery();
            Row[] row = am.getLovDesignation1().getFilteredRows("DesgId", getDesgId());
            if (row.length > 0) {
                return row[0].getAttribute("ParamDesc").toString();
            }
        }
        return (String) getAttributeInternal(TRANSDESGNM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransDesgNm.
     * @param value value to set the  TransDesgNm
     */
    public void setTransDesgNm(String value) {
        setAttributeInternal(TRANSDESGNM, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransShiftStrtTm.
     * @return the TransShiftStrtTm
     */
    public String getTransShiftStrtTm() {
        if (getAttributeInternal(TRANSSHIFTSTRTTM) == null || getAttributeInternal(TRANSSHIFTSTRTTM) == " ") {
           // System.out.println("inside if in start time shift");
            HCMAttendanceRuleSetUpAMImpl am = (HCMAttendanceRuleSetUpAMImpl) this.getApplicationModule();
            am.getLovShift1().setNamedWhereClauseParam("BindCldId", getCldId());
            am.getLovShift1().setNamedWhereClauseParam("BindSlocId", getSlocId());
            am.getLovShift1().setNamedWhereClauseParam("BindOrgId", getOrgId());
            am.getLovShift1().setNamedWhereClauseParam("BindHoOrgId", getHoOrgId());
            am.getLovShift1().executeQuery();
            Row r[] = am.getLovShift1().getFilteredRows("ShiftId", getShiftId());
            //System.out.println("start time length" + r.length);
            if (r.length > 0)
                return (String) r[0].getAttribute("ShiftStartTm");
        }
        return (String) getAttributeInternal(TRANSSHIFTSTRTTM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransShiftStrtTm.
     * @param value value to set the  TransShiftStrtTm
     */
    public void setTransShiftStrtTm(String value) {
        setAttributeInternal(TRANSSHIFTSTRTTM, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransShiftEndTm.
     * @return the TransShiftEndTm
     */
    public String getTransShiftEndTm() {
        if (getAttributeInternal(TRANSSHIFTENDTM) == null) {
            HCMAttendanceRuleSetUpAMImpl am = (HCMAttendanceRuleSetUpAMImpl) this.getApplicationModule();
            am.getLovShift1().setNamedWhereClauseParam("BindCldId", getCldId());
            am.getLovShift1().setNamedWhereClauseParam("BindSlocId", getSlocId());
            am.getLovShift1().setNamedWhereClauseParam("BindOrgId", getOrgId());
            am.getLovShift1().setNamedWhereClauseParam("BindHoOrgId", getHoOrgId());
            am.getLovShift1().executeQuery();
            Row r[] = am.getLovShift1().getFilteredRows("ShiftId", getShiftId());
            if (r.length > 0)
                return (String) r[0].getAttribute("ShiftEndTm");
        }
        return (String) getAttributeInternal(TRANSSHIFTENDTM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransShiftEndTm.
     * @param value value to set the  TransShiftEndTm
     */
    public void setTransShiftEndTm(String value) {
        setAttributeInternal(TRANSSHIFTENDTM, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link HcmAttenRegRuleLeave.
     */
    public RowIterator getHcmAttenRegRuleLeave() {
        return (RowIterator) getAttributeInternal(HCMATTENREGRULELEAVE);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovDesignationVO1.
     */
    public RowSet getLovDesignationVO1() {
        return (RowSet) getAttributeInternal(LOVDESIGNATIONVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovRuleTypeVO1.
     */
    public RowSet getLovRuleTypeVO1() {
        return (RowSet) getAttributeInternal(LOVRULETYPEVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovShiftVO1.
     */
    public RowSet getLovShiftVO1() {
        return (RowSet) getAttributeInternal(LOVSHIFTVO1);
    }
}

