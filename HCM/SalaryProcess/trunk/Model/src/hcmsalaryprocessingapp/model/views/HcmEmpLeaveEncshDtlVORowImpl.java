package hcmsalaryprocessingapp.model.views;

import hcmsalaryprocessingapp.model.entities.HcmEmpLeaveEncshDtlEOImpl;

import hcmsalaryprocessingapp.model.module.HcmSalaryProcessingAppAMImpl;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Jul 21 16:14:50 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class HcmEmpLeaveEncshDtlVORowImpl extends ViewRowImpl {


    public static final int ENTITY_HCMEMPLEAVEENCSHDTLEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        DocDt,
        DocId,
        EmpDocId,
        EncshAmt,
        HoOrgId,
        LeaveAvail,
        LeaveBal,
        LeaveEncashLimit,
        LeaveId,
        LeaveOp,
        OrgId,
        SlocId,
        UsrIdCreate,
        UsrIdCreateDt,
        UsrIdMod,
        UsrIdModDt,
        TransGrpId,
        TransEmpName,
        LovEncashEmpVO1;
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
    public static final int DOCDT = AttributesEnum.DocDt.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int EMPDOCID = AttributesEnum.EmpDocId.index();
    public static final int ENCSHAMT = AttributesEnum.EncshAmt.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int LEAVEAVAIL = AttributesEnum.LeaveAvail.index();
    public static final int LEAVEBAL = AttributesEnum.LeaveBal.index();
    public static final int LEAVEENCASHLIMIT = AttributesEnum.LeaveEncashLimit.index();
    public static final int LEAVEID = AttributesEnum.LeaveId.index();
    public static final int LEAVEOP = AttributesEnum.LeaveOp.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int TRANSGRPID = AttributesEnum.TransGrpId.index();
    public static final int TRANSEMPNAME = AttributesEnum.TransEmpName.index();
    public static final int LOVENCASHEMPVO1 = AttributesEnum.LovEncashEmpVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public HcmEmpLeaveEncshDtlVORowImpl() {
    }

    /**
     * Gets HcmEmpLeaveEncshDtlEO entity object.
     * @return the HcmEmpLeaveEncshDtlEO
     */
    public HcmEmpLeaveEncshDtlEOImpl getHcmEmpLeaveEncshDtlEO() {
        return (HcmEmpLeaveEncshDtlEOImpl) getEntity(ENTITY_HCMEMPLEAVEENCSHDTLEO);
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
     * Gets the attribute value for DOC_DT using the alias name DocDt.
     * @return the DOC_DT
     */
    public Timestamp getDocDt() {
        return (Timestamp) getAttributeInternal(DOCDT);
    }

    /**
     * Sets <code>value</code> as attribute value for DOC_DT using the alias name DocDt.
     * @param value value to set the DOC_DT
     */
    public void setDocDt(Timestamp value) {
        setAttributeInternal(DOCDT, value);
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
     * Gets the attribute value for EMP_DOC_ID using the alias name EmpDocId.
     * @return the EMP_DOC_ID
     */
    public String getEmpDocId() {
        return (String) getAttributeInternal(EMPDOCID);
    }

    /**
     * Sets <code>value</code> as attribute value for EMP_DOC_ID using the alias name EmpDocId.
     * @param value value to set the EMP_DOC_ID
     */
    public void setEmpDocId(String value) {
        setAttributeInternal(EMPDOCID, value);
    }

    /**
     * Gets the attribute value for ENCSH_AMT using the alias name EncshAmt.
     * @return the ENCSH_AMT
     */
    public Number getEncshAmt() {
        return (Number) getAttributeInternal(ENCSHAMT);
    }

    /**
     * Sets <code>value</code> as attribute value for ENCSH_AMT using the alias name EncshAmt.
     * @param value value to set the ENCSH_AMT
     */
    public void setEncshAmt(Number value) {
        setAttributeInternal(ENCSHAMT, value);
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
     * Gets the attribute value for LEAVE_AVAIL using the alias name LeaveAvail.
     * @return the LEAVE_AVAIL
     */
    public Number getLeaveAvail() {
        if (getAttributeInternal(LEAVEAVAIL) == null) {
            return new Number(0);
        }
        return (Number) getAttributeInternal(LEAVEAVAIL);
    }

    /**
     * Sets <code>value</code> as attribute value for LEAVE_AVAIL using the alias name LeaveAvail.
     * @param value value to set the LEAVE_AVAIL
     */
    public void setLeaveAvail(Number value) {
        setAttributeInternal(LEAVEAVAIL, value);
    }

    /**
     * Gets the attribute value for LEAVE_BAL using the alias name LeaveBal.
     * @return the LEAVE_BAL
     */
    public Number getLeaveBal() {
        if (this.getLeaveOp() != null && this.getLeaveAvail() != null) {
            Number bal = (Number) this.getLeaveOp().subtract(this.getLeaveAvail());
            return bal;
        }
        return (Number) getAttributeInternal(LEAVEBAL);
    }

    /**
     * Sets <code>value</code> as attribute value for LEAVE_BAL using the alias name LeaveBal.
     * @param value value to set the LEAVE_BAL
     */
    public void setLeaveBal(Number value) {
        setAttributeInternal(LEAVEBAL, value);
    }

    /**
     * Gets the attribute value for LEAVE_ENCASH_LIMIT using the alias name LeaveEncashLimit.
     * @return the LEAVE_ENCASH_LIMIT
     */
    public Number getLeaveEncashLimit() {
        return (Number) getAttributeInternal(LEAVEENCASHLIMIT);
    }

    /**
     * Sets <code>value</code> as attribute value for LEAVE_ENCASH_LIMIT using the alias name LeaveEncashLimit.
     * @param value value to set the LEAVE_ENCASH_LIMIT
     */
    public void setLeaveEncashLimit(Number value) {
        setAttributeInternal(LEAVEENCASHLIMIT, value);
    }

    /**
     * Gets the attribute value for LEAVE_ID using the alias name LeaveId.
     * @return the LEAVE_ID
     */
    public String getLeaveId() {
        return (String) getAttributeInternal(LEAVEID);
    }

    /**
     * Sets <code>value</code> as attribute value for LEAVE_ID using the alias name LeaveId.
     * @param value value to set the LEAVE_ID
     */
    public void setLeaveId(String value) {
        setEncshAmt(new Number(0));
        HcmSalaryProcessingAppAMImpl am = (HcmSalaryProcessingAppAMImpl) this.getApplicationModule();
        Timestamp docDt = (Timestamp) am.getHcmEmpLeaveEncsh1().getCurrentRow().getAttribute("DocDt");
        if (docDt != null) {
            setDocDt(docDt);
        }
        setAttributeInternal(LEAVEID, value);
    }

    /**
     * Gets the attribute value for LEAVE_OP using the alias name LeaveOp.
     * @return the LEAVE_OP
     */
    public Number getLeaveOp() {
        return (Number) getAttributeInternal(LEAVEOP);
    }

    /**
     * Sets <code>value</code> as attribute value for LEAVE_OP using the alias name LeaveOp.
     * @param value value to set the LEAVE_OP
     */
    public void setLeaveOp(Number value) {
        setAttributeInternal(LEAVEOP, value);
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
     * Gets the attribute value for the calculated attribute TransGrpId.
     * @return the TransGrpId
     */
    public String getTransGrpId() {
        HcmSalaryProcessingAppAMImpl am = (HcmSalaryProcessingAppAMImpl) this.getApplicationModule();
        String grpId = (String) am.getHcmEmpLeaveEncsh1().getCurrentRow().getAttribute("EmpGrpId");
        if (grpId != null) {
            System.out.println(grpId);
            return grpId;
        }
        return (String) getAttributeInternal(TRANSGRPID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransGrpId.
     * @param value value to set the  TransGrpId
     */
    public void setTransGrpId(String value) {
        setAttributeInternal(TRANSGRPID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransEmpName.
     * @return the TransEmpName
     */
    public String getTransEmpName() {
        if (this.getDocId() != null) {
            HcmSalaryProcessingAppAMImpl am = (HcmSalaryProcessingAppAMImpl) this.getApplicationModule();
            Row cRow = am.getHcmEmpLeaveEncsh1().getCurrentRow();
            ViewObjectImpl docId = am.getLovEmpNameFrmEmpDocId();
            docId.setNamedWhereClauseParam("BindCldId", this.getCldId());
            docId.setNamedWhereClauseParam("BindDeptId", cRow.getAttribute("EmpDeptId"));
            docId.setNamedWhereClauseParam("BindGrpId", cRow.getAttribute("EmpGrpId"));
            docId.setNamedWhereClauseParam("BindHoOrgId", this.getHoOrgId());
            docId.setNamedWhereClauseParam("BindLocId", cRow.getAttribute("EmpLocId"));
            docId.setNamedWhereClauseParam("BindOrgId", this.getOrgId());
            docId.setNamedWhereClauseParam("BindSlocId", this.getSlocId());
            docId.executeQuery();
            Row[] rows = docId.getFilteredRows("DocId", cRow.getAttribute("EmpDocId"));
            System.out.println(rows.length);
            if (rows.length > 0) {
                return rows[0].getAttribute("EmpNm").toString();
            }

        }
        return (String) getAttributeInternal(TRANSEMPNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransEmpName.
     * @param value value to set the  TransEmpName
     */
    public void setTransEmpName(String value) {
        setAttributeInternal(TRANSEMPNAME, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovEncashEmpVO1.
     */
    public RowSet getLovEncashEmpVO1() {
        return (RowSet) getAttributeInternal(LOVENCASHEMPVO1);
    }

}

