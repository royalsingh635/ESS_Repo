package hcmgroupprfapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import java.sql.SQLException;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.jbo.domain.Date;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.model.servlet.ADFBindingFilter;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.domain.Number;
import oracle.jbo.server.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.context.RequestContext;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import sun.security.timestamp.Timestamper;

public class GrpProfileBean {
    private RichInputDate grpStrtDtBinding;
    private RichInputDate grpEndDtBinding;
    private RichInputDate etrStrtDtBinding;
    private RichInputDate shiftStrtDtBinding;
    private RichSelectOneChoice ruleTypeBinding;
    private RichSelectOneChoice salProcFreq;
    private RichInputText freqDays;
    private RichInputDate compStrtDtBinding;
    private RichInputText gratutyDysBinding;
    private RichPopup bonusPopBinding;
    private RichPopup gratuityPopBinding;
    private RichSelectBooleanCheckbox bonusCheckBinding;
    private RichSelectBooleanCheckbox gratuityCheckBinding;
    private RichInputText bonusPercBinding;
    private RichInputText actlAmtBinding;
    private RichInputText grpWagesHourBinding;
    private RichSelectBooleanCheckbox grpWagesHourCheckBinding;
    private RichOutputText isRateInRs;
    private RichSelectOneChoice ltaTypeBinding;
    private RichInputText ltaAmountBinding;
    private RichInputText ltaPercBinding;
    private RichInputText minLeaveDaysBinding;
    private RichInputDate blocFromDtBinding;
    private RichInputDate blocToDtBinding;
    private RichInputText minServiceYrBinding;
    private RichPopup ltALeavePopupBinding;
    private RichPopup ltaSalIdPopPupBinding;
    private RichSelectBooleanCheckbox ltaChkBinding;

    public void setActlAmtBinding(RichInputText actlAmtBinding) {
        this.actlAmtBinding = actlAmtBinding;
    }

    public RichInputText getActlAmtBinding() {
        return actlAmtBinding;
    }
    public String gratMode = "D";
    public String LwfMode = "D";
    public String lTAMode = "D";

    public void setLTAMode(String lTAMode) {
        this.lTAMode = lTAMode;
    }

    public String getLTAMode() {
        return lTAMode;
    }

    private RichInputDate lwfValidTodt;

    public void setLwfMode(String LwfMode) {
        this.LwfMode = LwfMode;
    }

    public String getLwfMode() {
        return LwfMode;
    }

    private RichSelectOneChoice bonusType;
    private RichInputText gratSalDaysBinding;
    private RichInputText bonusRateBinding;
    private RichInputText minNoOfPaidDaysBinding;
    private RichInputText maxCutOfRate;
    private RichInputText maxGratCutOffBinding;
    private RichPopup extraTimeSapPopBinding;
    private RichSelectBooleanCheckbox extraTimeChkBinding;
    private RichInputText extraTimeRuleRateBinding;
    private RichSelectOneChoice ruleRateUnitBinding;
    private RichSelectBooleanCheckbox lwfCheckBinding;
    private RichInputText lwfEmpShareBinding;
    private RichInputText lwfEmprShareBinding;
    private RichInputDate lwfValidFrmDt;

    public void setGratMode(String gratMode) {
        this.gratMode = gratMode;
    }

    public String getGratMode() {
        return gratMode;
    }

    public void setBonusMode(String bonusMode) {
        this.bonusMode = bonusMode;
    }

    public String getBonusMode() {
        return bonusMode;
    }
    public String bonusMode = "D";


    public GrpProfileBean() {
    }

    private static ADFLogger _log = ADFLogger.createADFLogger(GrpProfileBean.class);

    public Object resolvEl(String data) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, data, Object.class);
        return exp.getValue(elContext);
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


    public String addProfileAction() {
        OperationBinding opchk = ADFBeanUtils.getOperationBinding("isOrgItselfHoOrg");
        opchk.execute();
        String isHoOrg = "N";
        if ((resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString()).equals(resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString()))
            isHoOrg = "Y";
        if (isHoOrg.equals("Y")) {
            OperationBinding opCrt = ADFBeanUtils.getOperationBinding("CreateWithParams");
            opCrt.execute();
            RequestContext context = RequestContext.getCurrentInstance();
            context.getPageFlowScope().put("ADD_EDIT_MODE", "A");
        } else {
            OperationBinding op = ADFBeanUtils.getOperationBinding("chkPrfForHoOrg");
            op.execute();
            String prfExist = "N";
            if (op.getErrors().size() == 0 && op.getResult() != null)
                prfExist = (String) op.getResult();
            if (prfExist.equals("N")) {
                System.out.println("create Profile for HO First.");
                _log.info("Logger called.");
                showFacesMessage("MSG.1535", "I", true, "F");
            } else {
                OperationBinding opCrt = ADFBeanUtils.getOperationBinding("CreateWithParams");
                opCrt.execute();
                OperationBinding opIns = ADFBeanUtils.getOperationBinding("insertFromHoOrg");
                opIns.execute();
                RequestContext context = RequestContext.getCurrentInstance();
                context.getPageFlowScope().put("ADD_EDIT_MODE", "A");
            }
        }
        return null;
    }

    public String editPrfAction() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.getPageFlowScope().put("ADD_EDIT_MODE", "A");
        return null;
    }

    public String savePrfAction() {
        boolean result = chkValidation();
        if (result) {
            OperationBinding op = ADFBeanUtils.getOperationBinding("Commit");
            op.execute();
            if (op.getErrors().size() == 0) {
                //  showFacesMessage("Record Saved Successfully.", "I", false, "F");
                showFacesMessage("MSG.91", "I", true, "F");
                RequestContext context = RequestContext.getCurrentInstance();
                context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                String add=resolvEl("#{PageFlowScope.ADD_EDIT_MODE").toString();
                System.out.println("");
            } else {
                System.out.println("Error is=" + op.getErrors());
            }
        }

        return null;
    }

    public String cancelPrfAction() {

        DCIteratorBinding parentIter = ADFBeanUtils.findIterator("GrpVOIterator");
        Key key = parentIter.getCurrentRow().getKey();
        OperationBinding op = ADFBeanUtils.getOperationBinding("Rollback");
        op.execute();
        parentIter.setCurrentRowWithKey(key.toStringFormat(true));
        RequestContext context = RequestContext.getCurrentInstance();
        context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
        return null;
    }


    public void addExtraTimeRuleAction(ActionEvent actionEvent) {

        if (chkExtraTimeValidations()) {
            ADFBeanUtils.getOperationBinding("CreateWithParams1").execute();
            OperationBinding opGenDocId = ADFBeanUtils.getOperationBinding("generateDocId");
            opGenDocId.execute();
        }


    }

    public String addShiftAction() {
        OperationBinding opCrt = ADFBeanUtils.getOperationBinding("CreateWithParams2");
        opCrt.execute();
        return null;
    }


    public String resetSearchAction() {
        OperationBinding op = ADFBeanUtils.getOperationBinding("resetSearch");
        op.execute();
        return null;
    }

    public void etrFrmDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;

            OperationBinding opchk = ADFBeanUtils.getOperationBinding("chkEtrTypValidStrtdt");
            opchk.getParamsMap().put("strtDt", object);
            opchk.execute();
            if (opchk.getErrors().size() == 0 && opchk.getResult().toString().equals("Y")) {

            } else {
                showFacesMessage("MSG.1536", "E", true, "V");
            }
            if (grpStrtDtBinding.getValue() != null && grpStrtDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) grpStrtDtBinding.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1537", "E", true, "V");
                    }
                }
            }
            if (grpEndDtBinding.getValue() != null && grpEndDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) object).dateValue();
                    endDt = ((Timestamp) grpEndDtBinding.getValue()).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1538", "E", true, "V");
                    }
                }
            }
        }
    }


    public void etrToDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (etrStrtDtBinding.getValue() != null && etrStrtDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) etrStrtDtBinding.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1539", "E", true, "V");
                    }
                }
            }
            if (grpEndDtBinding.getValue() != null && grpEndDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) object).dateValue();
                    endDt = ((Timestamp) grpEndDtBinding.getValue()).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1540", "E", true, "V");
                    }
                }
            }
        }

    }

    public void ruleRateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = (Number) object;
            if (val.compareTo(new Number(0)) > 0) {
                if (ADFBeanUtils.isPrecisionValid(26, 6, (Number) object)) {
                    if (isRateInRs.getValue() != null && isRateInRs.getValue().toString().equals("P")) {
                        System.out.println("Inner in A");
                        if (((Number) object).compareTo(new Number(100)) > 0)
                            //Value Should Not Greater Than 100
                            showFacesMessage("MSG.2691", "E", false, "V");
                    }
                } else {
                    showFacesMessage("MSG.57", "E", true, "V");
                }
            } else {
                showFacesMessage("MSG.1541", "E", true, "V");
            }
        }
    }

    public void shiftIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding opchk = ADFBeanUtils.getOperationBinding("chkDuplicateShift");
            opchk.getParamsMap().put("shiftId", object);
            opchk.execute();
            String valid = "N";
            if (opchk.getErrors().size() == 0 && opchk.getResult() != null)
                valid = (String) opchk.getResult();
            if (valid.equals("N"))
                showFacesMessage("MSG.1542", "E", true, "V");
        }
    }


    public void grpValidEndDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (grpStrtDtBinding.getValue() != null && grpStrtDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) grpStrtDtBinding.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1338", "E", true, "V");
                    }
                }
            }
        }
    }

    public void blocValidEndDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (blocFromDtBinding.getValue() != null && blocFromDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) blocFromDtBinding.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1338", "E", true, "V");
                    }
                }
            }
        }
    }

    public void setGrpStrtDtBinding(RichInputDate grpStrtDtBinding) {
        this.grpStrtDtBinding = grpStrtDtBinding;
    }

    public RichInputDate getGrpStrtDtBinding() {
        return grpStrtDtBinding;
    }

    public void setGrpEndDtBinding(RichInputDate grpEndDtBinding) {
        this.grpEndDtBinding = grpEndDtBinding;
    }

    public RichInputDate getGrpEndDtBinding() {
        return grpEndDtBinding;
    }

    public void setEtrStrtDtBinding(RichInputDate etrStrtDtBinding) {
        this.etrStrtDtBinding = etrStrtDtBinding;
    }

    public RichInputDate getEtrStrtDtBinding() {
        return etrStrtDtBinding;
    }

    public void shiftToDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (shiftStrtDtBinding.getValue() != null && shiftStrtDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) shiftStrtDtBinding.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1543", "E", true, "V");
                    }
                }
            }
            if (grpEndDtBinding.getValue() != null && grpEndDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) object).dateValue();
                    endDt = ((Timestamp) grpEndDtBinding.getValue()).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1544", "E", true, "V");
                    }
                }
            }

            //chechk for shift start date and shift end date.
            OperationBinding chkShiftDt = ADFBeanUtils.getOperationBinding("chkShiftValidStrtdt");
            chkShiftDt.getParamsMap().put("strtDt", object);
            chkShiftDt.execute();
            if (chkShiftDt.getErrors().size() == 0 && chkShiftDt.getResult() != null &&
                chkShiftDt.getResult().toString().equals("Y")) {
            } else {
                // showFacesMessage("Invalid Shift End Date. Refer to Shift Validity in Shift Profile.", "E", false, "V");
                showFacesMessage("MSG.1871", "E", true, "V");
            }
        }
    }

    public void shiftFrmDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            OperationBinding opchk = ADFBeanUtils.getOperationBinding("chkGrpValidStrtdt");
            opchk.getParamsMap().put("strtDt", object);
            opchk.execute();
            if (opchk.getErrors().size() == 0 && opchk.getResult().toString().equals("Y")) {

            } else {
                showFacesMessage("MSG.1545", "E", true, "V");
            }
            if (grpStrtDtBinding.getValue() != null && grpStrtDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) grpStrtDtBinding.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1546", "E", true, "V");
                    }
                }
            }
            if (grpEndDtBinding.getValue() != null && grpEndDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) object).dateValue();
                    endDt = ((Timestamp) grpEndDtBinding.getValue()).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1547", "E", true, "V");
                    }
                }
            }

            //chechk for shift start date and shift end date.
            OperationBinding chkShiftDt = ADFBeanUtils.getOperationBinding("chkShiftValidStrtdt");
            chkShiftDt.getParamsMap().put("strtDt", object);
            chkShiftDt.execute();
            if (chkShiftDt.getErrors().size() == 0 && chkShiftDt.getResult() != null &&
                chkShiftDt.getResult().toString().equals("Y")) {
            } else {
                /*    showFacesMessage("Invalid Shift Start Date. Refer to Shift Validity in Shift Profile.", "E", false,
                                 "V");  //1870 */
                showFacesMessage("MSG.1870", "E", true, "V");
            }

        }

    }

    public void setShiftStrtDtBinding(RichInputDate shiftStrtDtBinding) {
        this.shiftStrtDtBinding = shiftStrtDtBinding;
    }

    public RichInputDate getShiftStrtDtBinding() {
        return shiftStrtDtBinding;
    }

    public void setRuleTypeBinding(RichSelectOneChoice ruleTypeBinding) {
        this.ruleTypeBinding = ruleTypeBinding;
    }

    public RichSelectOneChoice getRuleTypeBinding() {
        return ruleTypeBinding;
    }

    public void salIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDupliSalaryId");
            opChk.getParamsMap().put("salId", object);
            opChk.execute();
            if (opChk.getErrors().size() > 0)
                System.out.println("Error on check duplicate=" + opChk.getErrors());
            else {
                if (opChk.getResult() == null || opChk.getResult().toString().equals("Y"))
                    showFacesMessage("MSG.1548", "E", true, "V");
            }

        }

    }

    public void shiftDaysValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            Integer procFreq = 0;
            procFreq = (Integer) salProcFreq.getValue();
            if (procFreq.equals(9))
                if ((new Integer(object.toString())).compareTo(new Integer(0)) > 0) {
                    try {
                        if ((new Number(object)).compareTo(new Number(0)) < 0) {
                            showFacesMessage("MSG.1549", "E", true, "V");
                        }
                    } catch (SQLException e) {
                        System.out.println("error on cast");
                    }
                } else {
                    showFacesMessage("MSG.1549", "E", true, "V");
                }

        }
    }

    public void grpFrmDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            OperationBinding opchk = ADFBeanUtils.getOperationBinding("chkGrpValidStrtdt");
            opchk.getParamsMap().put("strtDt", object);
            opchk.execute();
            if (opchk.getErrors().size() == 0 && opchk.getResult().toString().equals("Y")) {

            } else {
                showFacesMessage("MSG.1550", "E", true, "V");
            }
        }
    }

    public void shiftIdVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void setSalProcFreq(RichSelectOneChoice salProcFreq) {
        this.salProcFreq = salProcFreq;
    }

    public RichSelectOneChoice getSalProcFreq() {
        return salProcFreq;
    }

    public boolean chkExtraTimeValidation() {
        String message = "";
        if (extraTimeChkBinding.getValue() != null || extraTimeChkBinding.getValue() != "") {
            String extraTimeChk = extraTimeChkBinding.getValue().toString();
            message =  resolvElDCMsg("#{bundle['MSG.2693']}").toString();
            //message = "Please fill all Extra Time Rule details.";
            if (extraTimeChk.equals("true")) {

                DCIteratorBinding di = null;
                di = ADFBeanUtils.findIterator("HcmExtraTimeGrpIterator");
                if (di.getEstimatedRowCount() == 0) {
                    message = resolvElDCMsg("#{bundle['MSG.2694']}").toString();
                    //message = "Add Extra Time Rule details or uncheck this.";
                    String cid = extraTimeChkBinding.getClientId();
                    showMessage(message, cid);
                    return false;
                } else {
                    di = ADFBeanUtils.findIterator("HcmExtraTimeSalIterator");
                    if (ruleTypeBinding.getValue() == null || ruleTypeBinding.getValue() == "") {
                        String cid = ruleTypeBinding.getClientId();
                        showMessage(message, cid);
                        return false;
                    }

                    if (extraTimeRuleRateBinding.getValue() == null || extraTimeRuleRateBinding.getValue() == "") {
                        String cid = extraTimeRuleRateBinding.getClientId();
                        showMessage(message, cid);
                        return false;
                    }

                    if (ruleRateUnitBinding.getValue() == null || ruleRateUnitBinding.getValue() == "") {
                        String cid = ruleRateUnitBinding.getClientId();
                        showMessage(message, cid);
                        return false;
                    }
                    if (di.getEstimatedRowCount() == 0) {
                        if (isRateInRs.getValue() != null && isRateInRs.getValue().equals("P")) {
                            message = resolvElDCMsg("#{bundle['MSG.2696']}").toString();
                           // message = "Please add salary components for extra time rule.";
                            showMessage(message, null);
                            return false;
                        } else {
                            return true;
                        }
                    }

                }
                return true;
            }
        }
        return true;
    }

    public boolean chkGratuityValidation() {
        String message = "";
        if (gratuityCheckBinding.getValue() != null || gratuityCheckBinding.getValue() != "") {
            DCIteratorBinding di = null;
            String gratChkValue = gratuityCheckBinding.getValue().toString();
            if (gratChkValue.equals("true")) {
                di = ADFBeanUtils.findIterator("HcmGrpGratCal1Iterator");
                if (di.getEstimatedRowCount() == 0) {
                    message =  resolvElDCMsg("#{bundle['MSG.2697']}").toString();
                    //message = "Please add gratuity details or uncheck this.";
                    String cid = gratuityCheckBinding.getClientId();
                    showMessage(message, cid);
                    return false;
                } else {
                    message = "Please enter value.";
                    di = ADFBeanUtils.findIterator("HcmGrpGrtSal1Iterator");
                    if (gratSalDaysBinding.getValue() == null || gratSalDaysBinding.getValue() == "") {
                        String cid = gratSalDaysBinding.getClientId();
                        showMessage(message, cid);
                        return false;
                    }
                    if (gratutyDysBinding.getValue() == null || gratutyDysBinding.getValue() == "") {
                        String cid = gratutyDysBinding.getClientId();
                        showMessage(message, cid);
                        return false;
                    }
                    if (maxGratCutOffBinding.getValue() == null || maxGratCutOffBinding.getValue() == "") {
                        String cid = maxGratCutOffBinding.getClientId();
                        showMessage(message, cid);
                        return false;
                    }
                    if (di.getEstimatedRowCount() == 0) {
                       // message = "Please add salary components for gratuity calculation."; //1869
                        message = resolvElDCMsg("#{bundle['MSG.2698']}").toString();
                        showMessage(message, null);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean chkLwfValidation() {
        String message = "";
        if (lwfCheckBinding.getValue() != null || lwfCheckBinding.getValue() != "") {
            DCIteratorBinding di = null;
            String lwfChk = lwfCheckBinding.getValue().toString();
            if (lwfChk.equals("true")) {
                di = ADFBeanUtils.findIterator("HcmGrpLwfIterator");
                if (di.getEstimatedRowCount() == 0) {
                    //message = "Please add LWF details or uncheck this."; //1869
                    String cid = lwfCheckBinding.getClientId();
                    showMessage("MSG.2687", cid);
                    return false;
                } else {
                    message =  resolvElDCMsg("#{bundle['MSG.2699']}").toString();
                   // message = "Please enter value.";
                    if (lwfEmpShareBinding.getValue() == null || lwfEmpShareBinding.getValue() == "") {
                        String cid = lwfEmpShareBinding.getClientId();
                        showMessage(message, cid);
                        return false;
                    }
                    if (lwfEmprShareBinding.getValue() == null || lwfEmprShareBinding.getValue() == "") {
                        String cid = lwfEmprShareBinding.getClientId();
                        showMessage(message, cid);
                        return false;
                    }
                    if (lwfValidFrmDt.getValue() == null || lwfValidFrmDt.getValue() == "") {
                        String cid = lwfValidFrmDt.getClientId();
                        showMessage(message, cid);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean chkExgratiaOrBonusValidation() {
        String message = "";
        String message1 = "";
        String message2 = "";
        if (bonusType.getValue() != null && bonusType.getValue() != "") {
            DCIteratorBinding di = null;
            if (bonusType.getValue().toString().equals("83")) {
                message1 =  resolvElDCMsg("#{bundle['MSG.2700']}").toString();
               // message1 = "Please add salary components for  bonus details.";
                message2 =  resolvElDCMsg("#{bundle['MSG.2701']}").toString();
              //  message2 = "Please add bonus details or uncheck this.";

            }
            if (bonusType.getValue().toString().equals("84")) {
                message1 =  resolvElDCMsg("#{bundle['MSG.2702']}").toString();
               // message1 = "Please add salary components for  Exgratia details.";
                message2 =  resolvElDCMsg("#{bundle['MSG.2703']}").toString();
               // message2 = "Please add Exgratia details or uncheck this.";
            }
            di = ADFBeanUtils.findIterator("HcmGrpBonsExgratiaIterator");
            if (di.getEstimatedRowCount() == 0) {
                String cid = bonusType.getClientId();
                showMessage(message2, cid);
                return false;
            } else {
               // message = "Please enter value.";
                message = resolvElDCMsg("#{bundle['MSG.2699']}").toString();
                if (bonusRateBinding.getValue() == null || bonusRateBinding.getValue() == "") {
                    String cid = bonusRateBinding.getClientId();
                    showMessage(message, cid);
                    return false;
                }
                if (actlAmtBinding.getValue() == null || actlAmtBinding.getValue() == "") {
                    String cid = actlAmtBinding.getClientId();
                    showMessage(message, cid);
                    return false;
                }
                if (maxCutOfRate.getValue() == null || maxCutOfRate.getValue() == "") {
                    String cid = maxCutOfRate.getClientId();
                    showMessage(message, cid);
                    return false;
                }
                if (minNoOfPaidDaysBinding.getValue() == null || minNoOfPaidDaysBinding.getValue() == "") {
                    String cid = minNoOfPaidDaysBinding.getClientId();
                    showMessage(message, cid);
                    return false;
                }
                di = ADFBeanUtils.findIterator("HcmGrpBonusSal1Iterator");
                if (di.getEstimatedRowCount() == 0) {
                    showMessage(message1, null);
                    return false;
                }
            }

        }
        return true;
    }

    public boolean chkLTAValidation() {
        DCIteratorBinding di = null;
        String ltaChk = ltaChkBinding.getValue().toString();
        String message = "";
        String cid = "";
        if (ltaChk.equals("true")) {
            di = ADFBeanUtils.findIterator("HcmGrpLTAVO1Iterator");
            if (di.getEstimatedRowCount() == 0) {
               // message = "Please add LTA detail or uncheck this.";
                message =  resolvElDCMsg("#{bundle['MSG.2704']}").toString();
                cid = ltaChkBinding.getClientId();
                showMessage(message, cid);
                return false;
            }
            if (ltaTypeBinding.getValue() == null || ltaTypeBinding.getValue() == "") {
                cid = ltaTypeBinding.getClientId();
                message = "Please select LTA type.";
                showMessage(message, cid);
                return false;
            }
            if (ltaTypeBinding.getValue().toString().equals("A")) {
                if (ltaAmountBinding.getValue() == null || ltaAmountBinding.getValue() == "") {
                    cid = ltaAmountBinding.getClientId();
                    message = "Please enter LTA amount.";
                    showMessage(message, cid);
                    return false;
                }
            }
            if (ltaTypeBinding.getValue().toString().equals("P")) {
                if (ltaPercBinding.getValue() == null || ltaPercBinding.getValue() == "") {
                    cid = ltaPercBinding.getClientId();
                    message = "Please enter LTA percentage.";
                    showMessage(message, cid);
                    return false;
                }

            }
            if (minLeaveDaysBinding.getValue() == null || minLeaveDaysBinding.getValue() == "") {
                cid = minLeaveDaysBinding.getClientId();
                message = resolvElDCMsg("#{bundle['MSG.2705']}").toString();
                //message = "Please enter minimum leave days require for LTA.";
                showMessage(message, cid);
                return false;
            }

            if (blocFromDtBinding.getValue() == null || blocFromDtBinding.getValue() == "") {
                cid = blocFromDtBinding.getClientId();
                message = resolvElDCMsg("#{bundle['MSG.1563']}").toString();
                //message = "Please select from date.";

                showMessage(message, cid);
                return false;
            }
            if (blocToDtBinding.getValue() == null || blocToDtBinding.getValue() == "") {
                cid = blocToDtBinding.getClientId();
               // message = "Please select to date.";
                message =resolvElDCMsg("#{bundle['MSG.1563']}").toString();
                showMessage(message, cid);
                return false;
            }
            if (minServiceYrBinding.getValue() == null || minServiceYrBinding.getValue() == "") {
                cid = minServiceYrBinding.getClientId();
                message = resolvElDCMsg("#{bundle['MSG.2706']}").toString();
               // message = "Please enter minimum service year required for LTA.";
                showMessage(message, cid);
                return false;
            }
            di = ADFBeanUtils.findIterator("HcmGrpLTASalVO1Iterator");
            if (di.getEstimatedRowCount() == 0) {
                message = resolvElDCMsg("#{bundle['MSG.2707']}").toString();
                //message = "Please add salary component for LTA.";
                showMessage(message, null);
                return false;
            }
            di = ADFBeanUtils.findIterator("HcmGrpLTALeaveVO1Iterator");
            if (di.getEstimatedRowCount() == 0) {
                message =  resolvElDCMsg("#{bundle['MSG.2708']}").toString();
               // message = "Please add leave over which LTA will be applicable.";
                showMessage(message, null);
                return false;
            }

        }

        return true;
    }


    public boolean chkValidation() {
        String message = "";
        boolean result = true;
        boolean grpWagesHrChk = (Boolean) grpWagesHourCheckBinding.getValue();
        Integer procFreq = (Integer) salProcFreq.getValue();
        if (procFreq == 9) {
            if (freqDays.getValue() == null || freqDays.getValue() == "") {
                String cid = freqDays.getClientId();
                message = (String) resolvEl("#{bundle['MSG.1869']}");
                showMessage(message, cid);
                result = false;
                return result;
            }
        }
        if (grpWagesHrChk == (true)) {
            if (grpWagesHourBinding.getValue() == null || grpWagesHourBinding.getValue() == "") {
                String cid = grpWagesHourBinding.getClientId();
                message = resolvElDCMsg("#{bundle['MSG.2709']}").toString();
                //message = "Please enter group hours or uncheck group hours wages checkbox.";
                showMessage(message, cid);
                result = false;
                return result;
            }
        }
        if (result)
            result = chkExtraTimeValidation();
        if (result)
            result = chkGratuityValidation();
        if (result)
            result = chkExgratiaOrBonusValidation();
        if (result)
            result = chkLwfValidation();
        if (result)
            result = chkLTAValidation();


        return result;
    }

    public boolean chkExtraTimeValidations() {

        DCIteratorBinding di = null;
        String message;
        di = ADFBeanUtils.findIterator("HcmExtraTimeGrpIterator");
        if (di.getEstimatedRowCount() > 0) {
            if (extraTimeRuleRateBinding.getValue() == null || extraTimeRuleRateBinding.getValue() == "") {
               // message = "Please enter rule rate.";
                message = resolvElDCMsg("#{bundle['MSG.2710']}").toString();;
                
                String cid = extraTimeRuleRateBinding.getClientId();
                showMessage(message, cid);
                return false;
            }

            if (ruleRateUnitBinding.getValue() == null || ruleRateUnitBinding.getValue() == "") {
                message =resolvElDCMsg("#{bundle['MSG.2711']}").toString();
                //message = "Please select rule rate unit.";
                resolvElDCMsg("#{bundle['MSG.2711']}").toString();
                String cid = ruleRateUnitBinding.getClientId();
                showMessage(message, cid);
                return false;
            }
            di = ADFBeanUtils.findIterator("HcmExtraTimeSalIterator");
            if (isRateInRs.getValue() != null && isRateInRs.getValue().equals("P")) {
                if (di.getEstimatedRowCount() == 0) {
                    message =  resolvElDCMsg("#{bundle['MSG.2712']}").toString();
                    //message = "Please add salary components for extra time."; //1869
                    showMessage(message, null);
                    return false;
                }
            }
            return true;
        }
        return true;
    }


    public void setFreqDays(RichInputText freqDays) {
        this.freqDays = freqDays;
    }

    public RichInputText getFreqDays() {
        return freqDays;
    }

    public String showMessage(String message, String cid) {
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(cid, fm);
        return null;
    }


    public void createCompOffRuleAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("CreateInsert").execute();
    }


    public void compOffRuleTypeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDuplicateCompRuleType");
            opChk.getParamsMap().put("compRuleId", object);

            opChk.execute();
            if (opChk.getErrors().size() > 0)
                System.out.println("Error on check duplicate=" + opChk.getErrors());
            else {
                if (opChk.getResult() == null || opChk.getResult().toString().equals("Y"))
                    //Duplicate Comp Off Rule Not Allowed!!
                    showFacesMessage("MSG.2713", "E", false, "V");
            }

        }
    }

    public void compValidStartDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (grpStrtDtBinding.getValue() != null && grpStrtDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) grpStrtDtBinding.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1537", "E", true, "V");
                    }
                }
            }
            if (grpEndDtBinding.getValue() != null && grpEndDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) object).dateValue();
                    endDt = ((Timestamp) grpEndDtBinding.getValue()).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1538", "E", true, "V");
                    }
                }
            }
        }
    }

    public void compValidEndDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (compStrtDtBinding.getValue() != null && compStrtDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) compStrtDtBinding.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1539", "E", true, "V");
                    }
                }
            }
            if (grpEndDtBinding.getValue() != null && grpEndDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) object).dateValue();
                    endDt = ((Timestamp) grpEndDtBinding.getValue()).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1540", "E", true, "V");
                    }
                }
            }
        }


    }

    public void setCompStrtDtBinding(RichInputDate compStrtDtBinding) {
        this.compStrtDtBinding = compStrtDtBinding;
    }

    public RichInputDate getCompStrtDtBinding() {
        return compStrtDtBinding;
    }


    public void gratutyDaysValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (ADFBeanUtils.isPrecisionValid(26, 6, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) < 0) {
                    //Gratuity Days should be greater than or equal to Zero.
                    showFacesMessage("MSG.2713", "E", false, "V");
                }
            } else
                showFacesMessage("MSG.57", "E", true, "V");
        }
    }

    public void setGratutyDysBinding(RichInputText gratutyDysBinding) {
        this.gratutyDysBinding = gratutyDysBinding;
    }

    public RichInputText getGratutyDysBinding() {
        return gratutyDysBinding;
    }

    public void setBonusPopBinding(RichPopup bonusPopBinding) {
        this.bonusPopBinding = bonusPopBinding;
    }

    public RichPopup getBonusPopBinding() {
        return bonusPopBinding;
    }

    public void setGratuityPopBinding(RichPopup gratuityPopBinding) {
        this.gratuityPopBinding = gratuityPopBinding;
    }

    public RichPopup getGratuityPopBinding() {
        return gratuityPopBinding;
    }

    public void openGratuityPop(ActionEvent actionEvent) {
        showPopup(gratuityPopBinding, true);
    }

    public void openExtraTimeSalPop(ActionEvent actionEvent) {
        showPopup(extraTimeSapPopBinding, true);
    }

    public void openBonusPopup(ActionEvent actionEvent) {
        showPopup(bonusPopBinding, true);

    }

    public void openLTALeavePopup(ActionEvent actionEvent) {
        showPopup(ltALeavePopupBinding, true);

    }

    public void openLTASalPopup(ActionEvent actionEvent) {
        showPopup(ltaSalIdPopPupBinding, true);

    }

    public void addGratuitySalComp(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("CreateInsert1").execute();
    }

    public void addLTALeaveComp(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("CreateInsert9").execute();
    }

    public void addLTASalComp(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("CreateInsert10").execute();
    }

    public void addBonusSalComp(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("CreateInsert2").execute();
    }

    public void addLwf(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("CreateInsert5").execute();
        setLwfMode("E");
    }

    public void addBonus(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("CreateInsert6").execute();
        setBonusMode("E");
    }

    public void addLTA(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("CreateInsert8").execute();
        setLTAMode("E");
    }

    public void addExtraTimeSalComp(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("CreateInsert4").execute();
    }

    public void deleteExtraTimeSalComp(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete5").execute();
    }

    public void deleteLwf(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete7").execute();
        setLwfMode("D");
    }

    public void deleteGratFormula(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete6").execute();
        setGratMode("D");
    }

    public void deleteBonus(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete8").execute();
        setBonusMode("D");
    }

    public void deleteLTA(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete9").execute();
        setLTAMode("D");
    }

    public void setBonusCheckBinding(RichSelectBooleanCheckbox bonusCheckBinding) {
        this.bonusCheckBinding = bonusCheckBinding;
    }

    public RichSelectBooleanCheckbox getBonusCheckBinding() {
        return bonusCheckBinding;
    }

    public void setGratuityCheckBinding(RichSelectBooleanCheckbox gratuityCheckBinding) {
        this.gratuityCheckBinding = gratuityCheckBinding;
    }

    public RichSelectBooleanCheckbox getGratuityCheckBinding() {
        return gratuityCheckBinding;
    }

    public void setBonusPercBinding(RichInputText bonusPercBinding) {
        this.bonusPercBinding = bonusPercBinding;
    }

    public RichInputText getBonusPercBinding() {
        return bonusPercBinding;
    }


    public void deleteGratSalComp(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete4").execute();
    }

    public void deleteLTALeave(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete10").execute();
    }

    public void deleteLTASal(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete11").execute();
    }

    public void deleteBonsSalComp(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete3").execute();
    }

    public void editBonusDetaills(ActionEvent actionEvent) {
        setBonusMode("E");
    }

    public void editGratDetails(ActionEvent actionEvent) {
        setGratMode("E");
    }

    public void editLWFDetails(ActionEvent actionEvent) {
        setLwfMode("E");
    }

    public void editLTADetails(ActionEvent actionEvent) {
        setLTAMode("E");
    }

    public void disableGratFields(ActionEvent actionEvent) {

        boolean result = chkGratuityValidation();
        if (result)
            setGratMode("D");
    }

    public void disableBonusFields(ActionEvent actionEvent) {

        boolean result = chkExgratiaOrBonusValidation();
        if (result)
            setBonusMode("D");
    }

    public void disableLTAFields(ActionEvent actionEvent) {

        boolean result = chkLTAValidation();
        if (result)
            setLTAMode("D");
    }

    public void disableLWFFields(ActionEvent actionEvent) {

        boolean result = chkLwfValidation();
        if (result)
            setLwfMode("D");
    }

    public void chkduplSalCompForBonus(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null && object.toString().length() > 0) {
            OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDuplicateBonusSlryCmponent");
            opChk.getParamsMap().put("salId", object);
            opChk.execute();
            if (opChk.getErrors().size() > 0 || opChk.getResult() == null)
                System.out.println("Error on check duplicate=" + opChk.getErrors());
            else {
                if (opChk.getResult().toString().equals("Y"))
                    showFacesMessage("MSG.1397", "E", true, "V");
            }
        }
    }

    public void chkduplSalCompForLTA(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null && object.toString().length() > 0) {
            OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDuplicateLTASlryCmponent");
            opChk.getParamsMap().put("salId", object);
            opChk.execute();
            if (opChk.getErrors().size() > 0 || opChk.getResult() == null)
                System.out.println("Error on check duplicate=" + opChk.getErrors());
            else {
                if (opChk.getResult().toString().equals("Y"))
                    showFacesMessage("MSG.1397", "E", true, "V");
            }
        }
    }


    public void chkduplLeaveForLTA(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null && object.toString().length() > 0) {
            OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDuplicateLTALeave");
            opChk.getParamsMap().put("leaveId", object);
            opChk.execute();
            if (opChk.getErrors().size() > 0 || opChk.getResult() == null)
                System.out.println("Error on check duplicate=" + opChk.getErrors());
            else {
                if (opChk.getResult().toString().equals("Y"))
                    //Duplicate Leave
                    showFacesMessage("MSG.1334", "E", false, "V");
            }
        }
    }

    public void chkdupliSalCompForGratuity(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null && object.toString().length() > 0) {
            OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDuplicateGratSlryCmponent");
            opChk.getParamsMap().put("salId", object);
            opChk.execute();
            if (opChk.getErrors().size() > 0 || opChk.getResult() == null)
                System.out.println("Error on check duplicate=" + opChk.getErrors());
            else {
                if (opChk.getResult().toString().equals("Y"))
                    showFacesMessage("MSG.1397", "E", true, "V");
            }
        }
    }

    public void addGratuity(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("CreateInsert3").execute();
        setGratMode("E");
    }

    public void setBonusType(RichSelectOneChoice bonusType) {
        this.bonusType = bonusType;
    }

    public RichSelectOneChoice getBonusType() {
        return bonusType;
    }

    public void setGratSalDaysBinding(RichInputText gratSalDaysBinding) {
        this.gratSalDaysBinding = gratSalDaysBinding;
    }

    public RichInputText getGratSalDaysBinding() {
        return gratSalDaysBinding;
    }

    public void setBonusRateBinding(RichInputText bonusRateBinding) {
        this.bonusRateBinding = bonusRateBinding;
    }

    public RichInputText getBonusRateBinding() {
        return bonusRateBinding;
    }

    public void setMinNoOfPaidDaysBinding(RichInputText minNoOfPaidDaysBinding) {
        this.minNoOfPaidDaysBinding = minNoOfPaidDaysBinding;
    }

    public RichInputText getMinNoOfPaidDaysBinding() {
        return minNoOfPaidDaysBinding;
    }

    public void setMaxCutOfRate(RichInputText maxCutOfRate) {
        this.maxCutOfRate = maxCutOfRate;
    }

    public RichInputText getMaxCutOfRate() {
        return maxCutOfRate;
    }

    public void setMaxGratCutOffBinding(RichInputText maxGratCutOffBinding) {
        this.maxGratCutOffBinding = maxGratCutOffBinding;
    }

    public RichInputText getMaxGratCutOffBinding() {
        return maxGratCutOffBinding;
    }

    public String getLableForBonus() {
        if (bonusType.getValue() != null) {
            if (bonusType.getValue().toString().equals("83")) {
                return "Bonus Details";
            }
            if (bonusType.getValue().toString().equals("84")) {
                return "Exgratia Details";
            }
        }
        return "Bonus Details";
    }

    public String getLableForRate() {
        if (bonusType.getValue() != null) {
            if (bonusType.getValue().toString().equals("83")) {
                return "Bonus Rate";
            }
            if (bonusType.getValue().toString().equals("84")) {
                return "Exgratia Rate";
            }
        }
        return "Bonus Rate";
    }

    public void setExtraTimeSapPopBinding(RichPopup extraTimeSapPopBinding) {
        this.extraTimeSapPopBinding = extraTimeSapPopBinding;
    }

    public RichPopup getExtraTimeSapPopBinding() {
        return extraTimeSapPopBinding;
    }

    public void setExtraTimeChkBinding(RichSelectBooleanCheckbox extraTimeChkBinding) {
        this.extraTimeChkBinding = extraTimeChkBinding;
    }

    public RichSelectBooleanCheckbox getExtraTimeChkBinding() {
        return extraTimeChkBinding;
    }

    public void setExtraTimeRuleRateBinding(RichInputText extraTimeRuleRateBinding) {
        this.extraTimeRuleRateBinding = extraTimeRuleRateBinding;
    }

    public RichInputText getExtraTimeRuleRateBinding() {
        return extraTimeRuleRateBinding;
    }

    public void setRuleRateUnitBinding(RichSelectOneChoice ruleRateUnitBinding) {
        this.ruleRateUnitBinding = ruleRateUnitBinding;
    }

    public RichSelectOneChoice getRuleRateUnitBinding() {
        return ruleRateUnitBinding;
    }

    public void setLwfCheckBinding(RichSelectBooleanCheckbox lwfCheckBinding) {
        this.lwfCheckBinding = lwfCheckBinding;
    }

    public RichSelectBooleanCheckbox getLwfCheckBinding() {
        return lwfCheckBinding;
    }

    public void setLwfEmpShareBinding(RichInputText lwfEmpShareBinding) {
        this.lwfEmpShareBinding = lwfEmpShareBinding;
    }

    public RichInputText getLwfEmpShareBinding() {
        return lwfEmpShareBinding;
    }

    public void setLwfEmprShareBinding(RichInputText lwfEmprShareBinding) {
        this.lwfEmprShareBinding = lwfEmprShareBinding;
    }

    public RichInputText getLwfEmprShareBinding() {
        return lwfEmprShareBinding;
    }

    public void setLwfValidFrmDt(RichInputDate lwfValidFrmDt) {
        this.lwfValidFrmDt = lwfValidFrmDt;
    }

    public RichInputDate getLwfValidFrmDt() {
        return lwfValidFrmDt;
    }

    public void setLwfValidTodt(RichInputDate lwfValidTodt) {
        this.lwfValidTodt = lwfValidTodt;
    }

    public RichInputDate getLwfValidTodt() {
        return lwfValidTodt;
    }

    public void gratSalDaysValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (ADFBeanUtils.isPrecisionValid(5, 0, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) <= 0) {
                    String message =  resolvElDCMsg("#{bundle['MSG.2715']}").toString();
                    //String message = "Please enter valid days.";
                    showFacesMessage(message, "E", false, "V");
                }
            } else
                showFacesMessage("Invalid Precision (5,0).", "E", false, "V");
        }
    }

    public void mxAmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (ADFBeanUtils.isPrecisionValid(26, 6, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) <= 0) {
                    String message = resolvElDCMsg("#{bundle['MSG.1434']}").toString();
                    //String message = "Please enter valid amount.";
                    showFacesMessage(message, "E", false, "V");
                }
            } else
                showFacesMessage("MSG.57", "E", true, "V");
        }
    }

    public void ToDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (lwfValidFrmDt.getValue() != null && lwfValidFrmDt.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) lwfValidFrmDt.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1539", "E", true, "V");
                    }
                }
            }
            if (grpEndDtBinding.getValue() != null && grpEndDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) object).dateValue();
                    endDt = ((Timestamp) grpEndDtBinding.getValue()).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1540", "E", true, "V");
                    }
                }
            }
        }

    }

    public void empAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (ADFBeanUtils.isPrecisionValid(26, 6, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) <= 0) {
                    //Employee share should be greater than zero.
                    showFacesMessage("MSG.2716", "E", false, "V");
                }
            } else
                showFacesMessage("MSG.57", "E", true, "V");
        }
    }

    public void emprAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (ADFBeanUtils.isPrecisionValid(26, 6, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) <= 0) {
                    //Employeer share should be greater than zero
                    showFacesMessage("MSG.2716", "E", false, "V");
                }
            } else
                showFacesMessage("MSG.57", "E", true, "V");
        }
    }

    public void bonusRateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (ADFBeanUtils.isPrecisionValid(5, 0, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) <= 0) {
                    String message = resolvElDCMsg("#{bundle['MSG.2719']}").toString();
                    //String message = "Please enter valid rate.";
                    showFacesMessage(message, "E", false, "V");
                }
            } else
                showFacesMessage("Invalid Precision (5,0).", "E", false, "V");
        }
    }

    public void wagesHourValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (ADFBeanUtils.isPrecisionValid(3, 0, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) <= 0) {
                    String message = resolvElDCMsg("#{bundle['MSG.2718']}").toString();
                    //String message = "Please enter valid hours.";
                    showFacesMessage(message, "E", false, "V");
                }
            } else
                showFacesMessage("Invalid Precision (3,0).", "E", false, "V");
        }
    }

    public void actualAmtVaalidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (ADFBeanUtils.isPrecisionValid(26, 6, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) <= 0) {
                    String message = resolvElDCMsg("#{bundle['MSG.1434']}").toString();
                    //String message = "Please enter valid amount.";
                    showFacesMessage(message, "E", false, "V");
                }
            } else
                showFacesMessage("MSG.57", "E", true, "V");
        }
    }

    public void maxSalaryAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (ADFBeanUtils.isPrecisionValid(5, 0, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) <= 0) {
                    String message =  resolvElDCMsg("#{bundle['MSG.1434']}").toString();
                    //String message = "Please enter valid amount.";
                    showFacesMessage(message, "E", false, "V");
                }
            } else
                showFacesMessage("Invalid Precision (5,0).", "E", false, "V");
        }
    }

    public void ltaAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (ADFBeanUtils.isPrecisionValid(26, 6, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) <= 0) {
                    String message =resolvElDCMsg("#{bundle['MSG.1434']}").toString();
                    //String message = "Please enter valid amount.";
                    showFacesMessage(message, "E", false, "V");
                }
            } else
                showFacesMessage("Invalid Precision (26,0).", "E", false, "V");
        }
    }

    public void ltaPerValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (ADFBeanUtils.isPrecisionValid(26, 6, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) <= 0) {
                    String message = resolvElDCMsg("#{bundle['MSG.1386']}").toString();
                   // String message = "Please enter valid percentage.";
                    showFacesMessage(message, "E", false, "V");
                }
            } else
                showFacesMessage("Invalid Precision (26,0).", "E", false, "V");
        }
    }

    public void minLeaveDaysValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            try {
                if (ADFBeanUtils.isPrecisionValid(5, 0, (new Number((Integer) object)))) {
                    if (((new Number((Integer) object))).compareTo(new Number(0)) <= 0) {
                        String message =  resolvElDCMsg("#{bundle['MSG.2715']}").toString();
                        //String message = "Please enter valid days.";
                        showFacesMessage(message, "E", false, "V");
                    }
                } else
                    showFacesMessage("Invalid Precision (5,0).", "E", false, "V");
            } catch (SQLException e) {
            }
        }
    }

    public void minServiceMonthsValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            try {
                if (ADFBeanUtils.isPrecisionValid(5, 0, (new Number((Integer) object)))) {
                    if (((new Number((Integer) object))).compareTo(new Number(0)) <= 0) {
                        String message =  resolvElDCMsg("#{bundle['MSG.2720']}").toString();
                        //String message = "Please enter valid months.";
                        showFacesMessage(message, "E", false, "V");
                    }
                } else
                    showFacesMessage("Invalid Precision (5,0).", "E", false, "V");
            } catch (SQLException e) {
            }
        }
    }

    public void minNumberdaysValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (ADFBeanUtils.isPrecisionValid(5, 0, (Number) object)) {
                if (((Number) object).compareTo(new Number(0)) <= 0) {
                    String message =  resolvElDCMsg("#{bundle['MSG.2721']}").toString();
                    //String message = "Please enter valid number of days.";
                    showFacesMessage(message, "E", false, "V");
                }
            } else
                showFacesMessage("Invalid Precision (5,0).", "E", false, "V");
        }
    }


    public void closeExtraTimeSalPopup(ActionEvent actionEvent) {
        extraTimeSapPopBinding.hide();
    }

    public void closeGratSalPopup(ActionEvent actionEvent) {
        gratuityPopBinding.hide();
    }

    public void closeLTASalPopup(ActionEvent actionEvent) {
        ltaSalIdPopPupBinding.hide();
    }

    public void closeLTALeavePopup(ActionEvent actionEvent) {
        ltALeavePopupBinding.hide();
    }

    public void closeBonusSalPopup(ActionEvent actionEvent) {
        bonusPopBinding.hide();
    }

    public void setGrpWagesHourBinding(RichInputText grpWagesHourBinding) {
        this.grpWagesHourBinding = grpWagesHourBinding;
    }

    public RichInputText getGrpWagesHourBinding() {
        return grpWagesHourBinding;
    }

    public void setGrpWagesHourCheckBinding(RichSelectBooleanCheckbox grpWagesHourCheckBinding) {
        this.grpWagesHourCheckBinding = grpWagesHourCheckBinding;
    }

    public RichSelectBooleanCheckbox getGrpWagesHourCheckBinding() {
        return grpWagesHourCheckBinding;
    }

    public void addSalCompToLeaveLiabilityAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("CreateInsert7").execute();
    }

    public void SalForleaveLybilityValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding opchk = ADFBeanUtils.getOperationBinding("chkDupliSalCompoForleavLibility");
            opchk.getParamsMap().put("salId", object);
            opchk.execute();
            String valid = "N";
            if (opchk.getErrors().size() == 0 && opchk.getResult() != null)
                valid = (String) opchk.getResult();
            if (valid.equals("N"))
                //Duplicate Salary Component not allowed !
                showFacesMessage("MSG.2722", "E", false, "V");
        }

    }

    public void setIsRateInRs(RichOutputText isRateInRs) {
        this.isRateInRs = isRateInRs;
    }

    public RichOutputText getIsRateInRs() {
        return isRateInRs;
    }

    public void cutOffValidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String val = (String) object;
            if (val.contains(":")) {
                String[] split = val.split(":");
                if (!split[0].matches("\\d{1,2}") || !split[1].matches("\\d{1,2}")) {
                    //Invalid Value.
                    showFacesMessage("MSG.253 ", "E", false, "V");
                } else {
                    int hor = Integer.parseInt(split[0]);
                    int min = Integer.parseInt(split[1]);
                    System.out.println(hor + " " + min);
                    if (hor < 0 || hor > 12) {
                        //Hours must be in range of 0 to 12.
                        showFacesMessage("MSG.2723", "E", false, "V");
                    }
                    if (min < 0 || min > 60) {
                        //Minutes must be in range of 0 to 60.
                        showFacesMessage("MSG.2724", "E", false, "V");
                    }
                }
            } else {
                //Time Format must be hh:mm(00:00).
                showFacesMessage("MSG.2725 ", "E", false, "V");
            }
        }
    }

    public void setLtaTypeBinding(RichSelectOneChoice ltaTypeBinding) {
        this.ltaTypeBinding = ltaTypeBinding;
    }

    public RichSelectOneChoice getLtaTypeBinding() {
        return ltaTypeBinding;
    }

    public void setLtaAmountBinding(RichInputText ltaAmountBinding) {
        this.ltaAmountBinding = ltaAmountBinding;
    }

    public RichInputText getLtaAmountBinding() {
        return ltaAmountBinding;
    }

    public void setLtaPercBinding(RichInputText ltaPercBinding) {
        this.ltaPercBinding = ltaPercBinding;
    }

    public RichInputText getLtaPercBinding() {
        return ltaPercBinding;
    }

    public void setMinLeaveDaysBinding(RichInputText minLeaveDaysBinding) {
        this.minLeaveDaysBinding = minLeaveDaysBinding;
    }

    public RichInputText getMinLeaveDaysBinding() {
        return minLeaveDaysBinding;
    }

    public void setBlocFromDtBinding(RichInputDate blocFromDtBinding) {
        this.blocFromDtBinding = blocFromDtBinding;
    }

    public RichInputDate getBlocFromDtBinding() {
        return blocFromDtBinding;
    }

    public void setBlocToDtBinding(RichInputDate blocToDtBinding) {
        this.blocToDtBinding = blocToDtBinding;
    }

    public RichInputDate getBlocToDtBinding() {
        return blocToDtBinding;
    }

    public void setMinServiceYrBinding(RichInputText minServiceYrBinding) {
        this.minServiceYrBinding = minServiceYrBinding;
    }

    public RichInputText getMinServiceYrBinding() {
        return minServiceYrBinding;
    }

    public void setLtALeavePopupBinding(RichPopup ltALeavePopupBinding) {
        this.ltALeavePopupBinding = ltALeavePopupBinding;
    }

    public RichPopup getLtALeavePopupBinding() {
        return ltALeavePopupBinding;
    }

    public void setLtaSalIdPopPupBinding(RichPopup ltaSalIdPopPupBinding) {
        this.ltaSalIdPopPupBinding = ltaSalIdPopPupBinding;
    }

    public RichPopup getLtaSalIdPopPupBinding() {
        return ltaSalIdPopPupBinding;
    }

    public void setLtaChkBinding(RichSelectBooleanCheckbox ltaChkBinding) {
        this.ltaChkBinding = ltaChkBinding;
    }

    public RichSelectBooleanCheckbox getLtaChkBinding() {
        return ltaChkBinding;
    }

    public void lwfFromDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (grpStrtDtBinding.getValue() != null && grpStrtDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) grpStrtDtBinding.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1537", "E", true, "V");
                    }
                }
            }
            if (grpEndDtBinding.getValue() != null && grpEndDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) object).dateValue();
                    endDt = ((Timestamp) grpEndDtBinding.getValue()).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1538", "E", true, "V");
                    }
                }
            }
        }
    }

    public void deleteLeaveLibilitySalAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete12").execute();
    }
    public Object resolvElDCMsg(String data) {
           FacesContext fc = FacesContext.getCurrentInstance();
           Application app = fc.getApplication();
           ExpressionFactory elFactory = app.getExpressionFactory();
           ELContext elContext = fc.getELContext();
           ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
           return valueExp.getValue(elContext);
       }

    public void legacyCodeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
       System.out.println("object=="+object);
        if (object != null) {
                    Object result = ADFBeanUtils.callBindingsMethod("validateLegacyCode", new Object[] { object }, new Object[] {
                                                                            "Legacy" });
                    System.out.println("terryrtyt"+result);
                    if (result != null) {
                        if (result.toString().equalsIgnoreCase("Y")) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          "duplicate value exists", null));

                        }
                    }
                }

    }
}

