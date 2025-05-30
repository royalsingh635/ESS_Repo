package hcmempprfsetup.model.views;

import hcmempprfsetup.model.entities.HcmEmpLeaveEOImpl;
import hcmempprfsetup.model.modules.HcmEmpPrfAMImpl;

import java.math.BigDecimal;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.RowSet;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Sep 19 12:23:24 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class HcmEmpLeaveVORowImpl extends ViewRowImpl {


    public static final int ENTITY_HCMEMPLEAVEEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        DocId,
        EmpCode,
        HoOrgId,
        LeaveAccrualRate,
        LeaveBal,
        LeaveId,
        LeaveYear,
        OrgId,
        SlocId,
        UsrIdCreate,
        UsrIdCreateDt,
        UsrIdMod,
        UsrIdModDt,
        ValidEndDt,
        ValidStrtDt,
        TransEmpGrpId,
        TransMode,
        LeaveOp,
        LovLeaveIdVO;
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
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int EMPCODE = AttributesEnum.EmpCode.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int LEAVEACCRUALRATE = AttributesEnum.LeaveAccrualRate.index();
    public static final int LEAVEBAL = AttributesEnum.LeaveBal.index();
    public static final int LEAVEID = AttributesEnum.LeaveId.index();
    public static final int LEAVEYEAR = AttributesEnum.LeaveYear.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int VALIDENDDT = AttributesEnum.ValidEndDt.index();
    public static final int VALIDSTRTDT = AttributesEnum.ValidStrtDt.index();
    public static final int TRANSEMPGRPID = AttributesEnum.TransEmpGrpId.index();
    public static final int TRANSMODE = AttributesEnum.TransMode.index();
    public static final int LEAVEOP = AttributesEnum.LeaveOp.index();
    public static final int LOVLEAVEIDVO = AttributesEnum.LovLeaveIdVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public HcmEmpLeaveVORowImpl() {
    }

    /**
     * Gets HcmEmpLeaveEO entity object.
     * @return the HcmEmpLeaveEO
     */
    public HcmEmpLeaveEOImpl getHcmEmpLeaveEO() {
        return (HcmEmpLeaveEOImpl) getEntity(ENTITY_HCMEMPLEAVEEO);
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
     * Gets the attribute value for EMP_CODE using the alias name EmpCode.
     * @return the EMP_CODE
     */
    public Integer getEmpCode() {
        return (Integer) getAttributeInternal(EMPCODE);
    }

    /**
     * Sets <code>value</code> as attribute value for EMP_CODE using the alias name EmpCode.
     * @param value value to set the EMP_CODE
     */
    public void setEmpCode(Integer value) {
        setAttributeInternal(EMPCODE, value);
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
     * Gets the attribute value for LEAVE_ACCRUAL_RATE using the alias name LeaveAccrualRate.
     * @return the LEAVE_ACCRUAL_RATE
     */
    public Number getLeaveAccrualRate() {
        return (Number) getAttributeInternal(LEAVEACCRUALRATE);
    }

    /**
     * Sets <code>value</code> as attribute value for LEAVE_ACCRUAL_RATE using the alias name LeaveAccrualRate.
     * @param value value to set the LEAVE_ACCRUAL_RATE
     */
    public void setLeaveAccrualRate(Number value) {
        setAttributeInternal(LEAVEACCRUALRATE, value);
    }

    /**
     * Gets the attribute value for LEAVE_BAL using the alias name LeaveBal.
     * @return the LEAVE_BAL
     */
    public Number getLeaveBal() {
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
        setAttributeInternal(LEAVEID, value);
    }

    /**
     * Gets the attribute value for LEAVE_YEAR using the alias name LeaveYear.
     * @return the LEAVE_YEAR
     */
    public Integer getLeaveYear() {
        return (Integer) getAttributeInternal(LEAVEYEAR);
    }

    /**
     * Sets <code>value</code> as attribute value for LEAVE_YEAR using the alias name LeaveYear.
     * @param value value to set the LEAVE_YEAR
     */
    public void setLeaveYear(Integer value) {
        setAttributeInternal(LEAVEYEAR, value);
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
     * Gets the attribute value for the calculated attribute TransEmpGrpId.
     * @return the TransEmpGrpId
     */
    public String getTransEmpGrpId() {
        if(getAttributeInternal(TRANSEMPGRPID)!=null)
        return (String) getAttributeInternal(TRANSEMPGRPID);
        else
        {
            HcmEmpPrfAMImpl am = (HcmEmpPrfAMImpl) this.getApplicationModule();
            return (String) am.getOrgHcmEmpPrf().getCurrentRow().getAttribute("EmpGrpId");
            }
    }

    /**
     * Gets the attribute value for the calculated attribute TransMode.
     * @return the TransMode
     */
    public String getTransMode() {
        if(resolvEl("#{pageFlowScope.MODE_LEAVE}") != null)
            return (String) resolvEl("#{pageFlowScope.MODE_LEAVE}");
            else
            return "V";
       // return (String) getAttributeInternal(TRANSMODE);
    }

    public String resolvEl(String data) {
                  FacesContext fc = FacesContext.getCurrentInstance();
                  Application app = fc.getApplication();
                  ExpressionFactory elFactory = app.getExpressionFactory();
                  ELContext elContext = fc.getELContext();
                  ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
                  String msg = null;
                  if(valueExp.getValue(elContext) != null)
                   msg = valueExp.getValue(elContext).toString();
                  return msg;
              }    
    
    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransMode.
     * @param value value to set the  TransMode
     */
    public void setTransMode(String value) {
        setAttributeInternal(TRANSMODE, value);
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
     * Gets the view accessor <code>RowSet</code> LovLeaveIdVO.
     */
    public RowSet getLovLeaveIdVO() {
        return (RowSet) getAttributeInternal(LOVLEAVEIDVO);
    }
}

