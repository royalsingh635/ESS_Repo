package hcmleavesetup.view.bean;

import adf.utils.bean.ADFBeanUtils;

import org.apache.myfaces.trinidad.util.Service;

import java.sql.SQLException;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;

public class LeaveSetupBean {
    private RichInputText leaveNmBinding;
    private RichInputDate validFrmDtBinding;
    private RichInputText accRateBinding;
    private RichSelectOneChoice leaveAccUnitBinding;
    private RichInputText maxLimitBinding;
    private RichSelectBooleanCheckbox encashChBinding;
    private RichSelectBooleanCheckbox carryFwdChkBinding;
    private RichInputText dedAdjDaysBinding;
    private RichSelectBooleanCheckbox absentDedChkBinding;
    private RichPopup leaveTypePopUp;
    private RichInputText bindContinueLeaveTaken;
    private RichInputText bindMonthlyAvailLimit;
    private RichSelectBooleanCheckbox leaveComineCheckBind;
    private RichSelectBooleanCheckbox bindingLeaveCheckField;


    public LeaveSetupBean() {
    }
    private String chkValidity = "N";

    public void setChkValidity(String chkValidity) {
        this.chkValidity = chkValidity;
    }

    public String getChkValidity() {
        return chkValidity;
    }
    //BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();


    public Object resolvEl(String data) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, data, Object.class);
        return exp.getValue(elContext);
    }


    /**
     *      Method to show validation message(I,E,W)
     *      mesg:Message to display
     *      sev:Severity(I-Info,E-Error,W-Warning)
     *      chk:true=if resource bundle is used; false=If Resource Bundle is not used.
     *      typFlg: 'F' for FacesMessage , 'V' for ValidatorException
     * */
    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg) {
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true) {
            String msg = (String) resolvEl("#{bundle['" + mesg + "']}");
            message = new FacesMessage(msg);
        }
        if (sev.equalsIgnoreCase("E")) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        } else if (sev.equalsIgnoreCase("W")) {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
        } else if (sev.equalsIgnoreCase("I")) {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        } else {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        if (typFlg.equals("F")) {
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else if (typFlg.equals("V")) {
            throw new ValidatorException(message);
        }
    }


    /**
     *      Method to Apply Precision Validation(P,S,V)
     *      P- Precision
     *      S- Scale
     *      V- Value to validate
     * */

    public String addLeaveDtlAction() {
        OperationBinding opcrt = ADFBeanUtils.getOperationBinding("CreateWithParams");
        opcrt.execute();
        RequestContext context = RequestContext.getCurrentInstance();
        context.getPageFlowScope().put("ADD_EDIT_MODE", "A");
        return null;
    }

    public String editLeaveDtlAction() {
        System.out.println("edit me gya ");
        RequestContext context = RequestContext.getCurrentInstance();
        context.getPageFlowScope().put("ADD_EDIT_MODE", "A");
        return null;
    }

    public boolean checkOnSave() {
        System.out.println("in check on save method");
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding roleIter = bindings.findIteratorBinding("OrgHcmLeaveSubVO1Iterator");
        System.out.println("roleIter.getEstimatedRowCount()::::::::" + roleIter.getEstimatedRowCount());

        String val = bindingLeaveCheckField.getValue().toString();
        System.out.println("value is:::::::" + val);
        if (val != null) {
            if (val.equals("true")) {
                System.out.println("in outert if::::::::::");
                if (roleIter.getEstimatedRowCount() > 0) {
                    System.out.println("in inner if ::::::::");
                    return true;
                } else {
                    FacesMessage msg =
                        new FacesMessage("Either uncheck the Leave Combine check Field or select atleast one leave Type");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, msg);
                    return false;

                }

            }
        }

        return true;
    }


    public String saveLeaveDtlAction() {
        System.out.println("inside save:::::::::::::");
        if (checkOnSave()) {
            if (absentDedChkBinding.getValue() != null) {
                if (absentDedChkBinding.getValue().equals(true)) {
                    if (dedAdjDaysBinding.getValue() != null && dedAdjDaysBinding.getValue().toString().length() > 0) {
                        OperationBinding opsv = ADFBeanUtils.getOperationBinding("Commit");
                        opsv.execute();
                        RequestContext context = RequestContext.getCurrentInstance();
                        context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                        // showFacesMessage("Record Saved Successfully.", "I", false, "F");
                        showFacesMessage("MSG.91", "I", true, "F");
                    } else {
                        FacesMessage msg = new FacesMessage("Adjustment days can not be left blank!");
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(dedAdjDaysBinding.getClientId(), msg);

                    }
                } else {
                    OperationBinding opsv = ADFBeanUtils.getOperationBinding("Commit");
                    opsv.execute();
                    if (opsv.getErrors().size() != 0) {
                    } else {
                        RequestContext context = RequestContext.getCurrentInstance();
                        context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                        // showFacesMessage("Record Saved Successfully.", "I", false, "F");
                        showFacesMessage("MSG.91", "I", true, "F");
                    }
                }
            }
            return null;
        }
        return null;
    }

    public String cancelLeaveDtlAction() {
        OperationBinding opcncl = ADFBeanUtils.getOperationBinding("Rollback");
        opcncl.execute();
        RequestContext context = RequestContext.getCurrentInstance();
        context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
        return null;
    }

    public String addLeaveGrpAction() {
        OperationBinding opcrt = ADFBeanUtils.getOperationBinding("CreateWithParams1");
        opcrt.execute();
        return null;
    }

    public String resetSearchAction() {
        leaveNmBinding.setValue(null);
        OperationBinding opreset = ADFBeanUtils.getOperationBinding("resetSearchMethod");
        opreset.execute();
        return null;
    }

    public void setLeaveNmBinding(RichInputText leaveNmBinding) {
        this.leaveNmBinding = leaveNmBinding;
    }

    public RichInputText getLeaveNmBinding() {
        return leaveNmBinding;
    }

    public void leaveNotationValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {

            if (object != null) {
                OperationBinding opchk = ADFBeanUtils.getOperationBinding("chkDuplicateNotation");
                opchk.getParamsMap().put("notation", object);
                opchk.execute();
                String valid = "N";
                if (opchk.getErrors().size() == 0 && opchk.getResult() != null)
                    valid = (String) opchk.getResult();
                if (valid.equals("N"))
                    // showFacesMessage("Duplicate Leave Notation.", "E", false, "V");
                    showFacesMessage("MSG.1334", "E", true, "V");
            }
        }

    }

    public void grpDescValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {

            if (object != null) {
                OperationBinding opchk = ADFBeanUtils.getOperationBinding("chkDuplicateGrpName");
                opchk.getParamsMap().put("grpName", object);
                opchk.execute();
                String valid = "N";
                if (opchk.getErrors().size() == 0 && opchk.getResult() != null)
                    valid = (String) opchk.getResult();
                if (valid.equals("N"))
                    // showFacesMessage("Group Already Exist for the Leave.", "E", false, "V");
                    showFacesMessage("MSG.1335", "E", true, "V");
            }
        }

    }

    public void accRateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            if (ADFBeanUtils.isPrecisionValid(26, 6, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) < 0) {
                    // showFacesMessage("Rate can not be less than Zero.", "E", false, "V");
                    showFacesMessage("MSG.1336", "E", true, "V");
                } else if (((Number) object).compareTo(new Number(28)) > 0) {
                    // showFacesMessage("Rate can not be Greater than 28.", "E", false, "V");
                    // showFacesMessage("Rate can not be Greater than 28.", "E", true, "V");

























                }
            } else {
                // showFacesMessage("Invalid Precision (26,6).", "E", false, "V");
                showFacesMessage("MSG.57", "E", true, "V");
            }
        } else {
            showFacesMessage("Accrual rate can't be left blank!", "E", false, "V");
        }

    }

    public void encashValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (encashChBinding.getValue() != null && encashChBinding.getValue().toString().equals("true")) {
            if (object != null && object.toString().length() > 0) {
                if (ADFBeanUtils.isPrecisionValid(26, 6, (Number) object)) {
                    if (((Number) object).compareTo(new Number(0)) < 0) {
                        // showFacesMessage("Value can not be less than Zero.", "E", false, "V");
                        showFacesMessage("LBL.3416", "E", true, "V");
                    } else if (leaveAccUnitBinding.getValue() != null && accRateBinding.getValue() != null &&
                               accRateBinding.getValue().toString().length() > 0) {
                        Integer unit = (Integer) leaveAccUnitBinding.getValue();
                        Number accRate = (Number) accRateBinding.getValue();
                        Number limit = new Number(0);
                        if (unit.compareTo(new Integer(23)) == 0)
                            limit = accRate.multiply(new Number(12));
                        else if (unit.compareTo(new Integer(24)) == 0)
                            limit = accRate.multiply(new Number(4));
                        else if (unit.compareTo(new Integer(25)) == 0)
                            limit = accRate.multiply(new Number(2));
                        else if (unit.compareTo(new Integer(26)) == 0)
                            limit = accRate.multiply(new Number(1));

                        /* if(((Number)object).compareTo(limit)<0)
                                    showFacesMessage("Encashable limit can not be less than "+limit+".", "E", false, "V");
                                else */if (maxLimitBinding.getValue() != null &&
                                           maxLimitBinding.getValue().toString().length() > 0) {
                            limit = (Number) maxLimitBinding.getValue();
                            if (((Number) object).compareTo(limit) > 0)
                                showFacesMessage("Encashable limit can not be Greater than Max leave Limit.", "E",
                                                 false, "V");
                            // showFacesMessage("MSG.1337", "E", true,"V");
                        }

                    }
                } else { //  showFacesMessage("Invalid Precision (26,6).", "E", false, "V");
                    showFacesMessage("MSG.57", "E", true, "V");
                }

            }
        }
    }

    public void leaveLimitValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            if (ADFBeanUtils.isPrecisionValid(26, 6, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) < 0)
                    showFacesMessage("Value can not be less than Zero.", "E", false, "V");
                else if (leaveAccUnitBinding.getValue() != null && accRateBinding.getValue() != null &&
                         accRateBinding.getValue().toString().length() > 0) {
                    Integer unit = (Integer) leaveAccUnitBinding.getValue();
                    Number accRate = (Number) accRateBinding.getValue();
                    Number limit = new Number(0);
                    if (unit.compareTo(new Integer(23)) == 0)
                        limit = accRate.multiply(new Number(12));
                    else if (unit.compareTo(new Integer(24)) == 0)
                        limit = accRate.multiply(new Number(4));
                    else if (unit.compareTo(new Integer(25)) == 0)
                        limit = accRate.multiply(new Number(2));
                    else if (unit.compareTo(new Integer(26)) == 0)
                        limit = accRate.multiply(new Number(1));
                    /*   if(((Number)object).compareTo(limit)<0)


                                     showFacesMessage("Maximum Leave limit can not be less than "+limit+".", "E", false, "V"); */
                }
            } else {
                // showFacesMessage("Invalid Precision (26,6).", "E", false, "V");
                showFacesMessage("MSG.57", "E", true, "V");
            }
        }


    }

    public void validFrmDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null && object.toString().length() > 0) {
            java.sql.Date structDt = null;
            try {
                structDt = ((Timestamp) object).dateValue();
            } catch (SQLException e) {
                System.out.println(e.getStackTrace());
            }
            OperationBinding opchk = ADFBeanUtils.getOperationBinding("chkStrtDateWithLeaveAdnGrpStDt");
            opchk.getParamsMap().put("structDt", structDt);
            opchk.execute();
            if (opchk.getErrors().size() == 0 && opchk.getResult() != null && opchk.getResult().equals("Y")) {
            } else if (opchk.getErrors().size() == 0 && opchk.getResult() != null && opchk.getResult().equals("L")) {
                showFacesMessage("Start Date should be under Leave Validity in Parameters.", "E", false, "V");
            } else if (opchk.getErrors().size() == 0 && opchk.getResult() != null && opchk.getResult().equals("G")) {
                showFacesMessage("Start Date should be under Group Validity in Parameters.", "E", false, "V");
            } else {
                System.out.println("Something went wrong.");
            }
        }
    }

    public void validUptoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (validFrmDtBinding.getValue() != null && validFrmDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) validFrmDtBinding.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        // showFacesMessage("End Date can not be before Start Date.", "E", false, "V");
                        showFacesMessage("MSG.1338", "E", true, "V");
                    }
                }
            }
        }

    }

    public void setValidFrmDtBinding(RichInputDate validFrmDtBinding) {
        this.validFrmDtBinding = validFrmDtBinding;
    }

    public RichInputDate getValidFrmDtBinding() {
        return validFrmDtBinding;
    }

    public void accunitValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (accRateBinding.getValue() != null && accRateBinding.toString().length() > 0) {

            if (((Number) (accRateBinding.getValue())).compareTo(new Number(0)) > 0) {
                if (object != null && object.toString().length() > 0) {
                } else {
                    // showFacesMessage("Please Select Accrual Unit.", "E", false, "V");
                    showFacesMessage("MSG.1339", "E", true, "V");
                }
            }

        }

    }

    public void setAccRateBinding(RichInputText accRateBinding) {
        this.accRateBinding = accRateBinding;
    }

    public RichInputText getAccRateBinding() {
        return accRateBinding;
    }

    public void setLeaveAccUnitBinding(RichSelectOneChoice leaveAccUnitBinding) {
        this.leaveAccUnitBinding = leaveAccUnitBinding;
    }

    public RichSelectOneChoice getLeaveAccUnitBinding() {
        return leaveAccUnitBinding;
    }


    public void setMaxLimitBinding(RichInputText maxLimitBinding) {
        this.maxLimitBinding = maxLimitBinding;
    }

    public RichInputText getMaxLimitBinding() {
        return maxLimitBinding;
    }

    public void setEncashChBinding(RichSelectBooleanCheckbox encashChBinding) {
        this.encashChBinding = encashChBinding;
    }

    public RichSelectBooleanCheckbox getEncashChBinding() {
        return encashChBinding;
    }

    public void acrualUnitVCL(ValueChangeEvent valueChangeEvent) {

        System.out.println("inside vcl--" + valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue() != null && ((Integer) valueChangeEvent.getNewValue()).compareTo(26) == 0) {
            accRateBinding.setValue(new Number(0));
            carryFwdChkBinding.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(accRateBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(carryFwdChkBinding);
        }
    }

    public void accrualRateVCL(ValueChangeEvent valueChangeEvent) {

        leaveAccUnitBinding.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(leaveAccUnitBinding);

    }

    public void setCarryFwdChkBinding(RichSelectBooleanCheckbox carryFwdChkBinding) {
        this.carryFwdChkBinding = carryFwdChkBinding;
    }

    public RichSelectBooleanCheckbox getCarryFwdChkBinding() {
        return carryFwdChkBinding;
    }

    public void createCompOffRuleAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("CreateInsert").execute();
    }

    public void compOffChkValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("Check box is --" + object);
        if (object != null) {
            OperationBinding op = ADFBeanUtils.getOperationBinding("chkCompOff");
            op.getParamsMap().put("chkVal", object);
            op.execute();
            if (op.getErrors().isEmpty() && op.getResult() != null) {
                String rslt = op.getResult().toString();
                if (rslt.equalsIgnoreCase("Y")) {
                    showFacesMessage("Can not define more than one leave as Comp Off!!", "E", false, "V");
                }
            }
        }

    }

    public void dedOnAbsentChkVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().equals(false)) {
                dedAdjDaysBinding.setValue(null);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(dedAdjDaysBinding);
        }
    }

    public void setDedAdjDaysBinding(RichInputText dedAdjDaysBinding) {
        this.dedAdjDaysBinding = dedAdjDaysBinding;
    }

    public RichInputText getDedAdjDaysBinding() {
        return dedAdjDaysBinding;
    }

    public void setAbsentDedChkBinding(RichSelectBooleanCheckbox absentDedChkBinding) {
        this.absentDedChkBinding = absentDedChkBinding;
    }

    public RichSelectBooleanCheckbox getAbsentDedChkBinding() {
        return absentDedChkBinding;
    }

    public void seqNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding binding = ADFBeanUtils.getOperationBinding("chkduplicateSequenceNo");
            binding.getParamsMap().put("seqNo", object);
            binding.execute();
            if (binding.getErrors().isEmpty() && binding.getResult() != null) {
                String rslt = binding.getResult().toString();
                if (rslt.equalsIgnoreCase("Y")) {
                    showFacesMessage("Duplicate Sequence No.!", "E", false, "V");
                }
            }

        }
    }

    public void monAvlLimitValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            if (ADFBeanUtils.isPrecisionValid(26, 6, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) < 0)
                    showFacesMessage("Value can not be less than Zero.", "E", false, "V");
            } else {
                // showFacesMessage("Invalid Precision (26,6).", "E", false, "V");
                showFacesMessage("MSG.57", "E", true, "V");
            }
        }


    }

    public void showPopUpForLeaveType(ActionEvent actionEvent) {
        showPopup(leaveTypePopUp, true);
    }

    private void showPopup(RichPopup pop, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null) {
                String popupId = pop.getClientId(context);
                if (popupId != null) {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup =AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible) {
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                    } else {
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setLeaveTypePopUp(RichPopup leaveTypePopUp) {
        this.leaveTypePopUp = leaveTypePopUp;
    }

    public RichPopup getLeaveTypePopUp() {
        return leaveTypePopUp;
    }

    public void addPopupSalaryAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("CreateInsert2").execute();
    }

    public void deleteLeaveTypeAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete1").execute();

    }


    public void validateContinueLeaveTaken(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("***** in validate ContinueLeaveTaken Bean *********");
        System.out.println(" object is :::::::;" + object);

        if (object != null) {

            oracle.jbo.domain.Number ContinueLeave = (oracle.jbo.domain.Number) object;

            System.out.println("ContinueLeave is:::::::::" + ContinueLeave);

            oracle.jbo.domain.Number MonthlyLeave = (oracle.jbo.domain.Number) bindMonthlyAvailLimit.getValue();

            System.out.println("MonthlyLeave is:::::::" + MonthlyLeave);

            if (ContinueLeave.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                                              "ContinueLeave can not be in negative"));
            }
            if (MonthlyLeave.compareTo(ContinueLeave) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                                              "Continue Leave should be less than  totalMonthly Leave Taken"));
            }

        }

    }

    public void setBindContinueLeaveTaken(RichInputText bindContinueLeaveTaken) {
        this.bindContinueLeaveTaken = bindContinueLeaveTaken;
    }

    public RichInputText getBindContinueLeaveTaken() {
        return bindContinueLeaveTaken;
    }

    public void setBindMonthlyAvailLimit(RichInputText bindMonthlyAvailLimit) {
        this.bindMonthlyAvailLimit = bindMonthlyAvailLimit;
    }

    public RichInputText getBindMonthlyAvailLimit() {
        return bindMonthlyAvailLimit;
    }

    public void leaveTypeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println(" object :::***" + object);

        if (object != null && object.toString().length() > 0) {
            OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDuplicateLeave");
            opChk.getParamsMap().put("LeaveId", object);
            opChk.execute();
            if (opChk.getErrors().size() > 0 || opChk.getResult() == null)
                System.out.println("Error on check duplicate=" + opChk.getErrors());
            else {
                if (opChk.getResult().toString().equals("Y"))
                    showFacesMessage("Leave and SubLeave can not be same", "E", false, "V");
            }
        }

    }

    public void okLinkAtPopAL(ActionEvent actionEvent) {
        leaveTypePopUp.hide();
    }

    public void setLeaveComineCheckBind(RichSelectBooleanCheckbox leaveComineCheckBind) {
        this.leaveComineCheckBind = leaveComineCheckBind;
    }

    public RichSelectBooleanCheckbox getLeaveComineCheckBind() {
        return leaveComineCheckBind;
    }

    public void setBindingLeaveCheckField(RichSelectBooleanCheckbox bindingLeaveCheckField) {
        this.bindingLeaveCheckField = bindingLeaveCheckField;
    }

    public RichSelectBooleanCheckbox getBindingLeaveCheckField() {
        return bindingLeaveCheckField;
    }

    public void maternityleaveValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /*
        if (object != null) {
                    //Integer per = Integer.parseInt(object.toString());
                    Number per = (Number) object;
                    Boolean b = ADFBeanUtils.isPrecisionValid(9, 6, per);
                    if (!b) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision/Scale",
                                                                      null));

                    } else if (per.compareTo(0) <= 0 || per.compareTo(100) > 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Percentage Value",
                                                                      null));

                    }
                }
                return "Y";
            }
         */
        if (object != null) {
            Integer mart = (Integer) object;


            if (mart.compareTo(0) <= 0 || mart.compareTo(365) > 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Maternity Leave ",
                                                              null));

            }
        }

    }
}
