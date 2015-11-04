package hcmattendancerulesetup.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import java.sql.SQLException;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.jbo.domain.Number;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.rules.JboPrecisionScaleValidator;

public class HCMAttendanceRuleBean {
    private RichPopup leavePopBinding;
    private RichSelectOneChoice ruleTypeBinding;
    private RichSelectOneChoice shiftIdBinding;
    private RichInputText graceMinuteBinding;
    private RichInputText maxAllowBinding;
    private RichInputText leaveAdjDayBinding;
    private RichInputDate validFrmDtBinding;
    private RichInputText docIdBinding;

    public HCMAttendanceRuleBean() {
    }
    private String mode = "V";

    private void showPopup(RichPopup pop, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null) {
                String popupId = pop.getClientId(context);
                if (popupId != null) {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
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

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void addNewRuleAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("CreateInsert").execute();
        this.mode = "E";
    }

    public void editBtnAL(ActionEvent actionEvent) {
        this.mode = "E";
    }

    public void saveBtnAL(ActionEvent actionEvent) {
        boolean chk = validatebeforesave();
        if (chk) {
            OperationBinding ob = ADFBeanUtils.getOperationBinding("checkDuplicateRule");
            ob.getParamsMap().put("docId", docIdBinding.getValue());
            ob.execute();
            if (ob.getErrors().isEmpty() && ob.getResult() != null) {
                String rslt = ob.getResult().toString();
                if (rslt.equalsIgnoreCase("Y")) {
                    FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2433"));   //Duplicate Rule not allowed!
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(ruleTypeBinding.getClientId(), msg);
                } else {
                    ADFBeanUtils.getOperationBinding("Commit").execute();
                    FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.818"));   //Record Saved Successfully!
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                    this.mode = "V";
                }
            }
        }
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void cancelBtnAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Rollback").execute();
        this.mode = "V";
    }

    public boolean validatebeforesave() {
        DCIteratorBinding parentIter = null;
        parentIter = (DCIteratorBinding) getBindings().get("HcmAttenRegRuleLeave1Iterator");
        if (ruleTypeBinding.getValue() == null) {
            FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2434"));    //Rule type can not be blank !
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(ruleTypeBinding.getClientId(), msg);
            return false;
        }
        if (shiftIdBinding.getValue() == null) {
            FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2435"));   //Shift can not be left blank !
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(shiftIdBinding.getClientId(), msg);
            return false;
        }

        if (graceMinuteBinding.getValue() == null) {
            FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2436"));    //Grace time can not be left blank !
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(graceMinuteBinding.getClientId(), msg);
            return false;
        }
        if (maxAllowBinding.getValue() == null) {
            FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2437"));    //Maximum occurence allowed can not be left blank !
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(maxAllowBinding.getClientId(), msg);
            return false;
        }

        if (leaveAdjDayBinding.getValue() == null) {
            FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2438"));    //Adjustment days can not be left blank !
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(leaveAdjDayBinding.getClientId(), msg);
            return false;
        }
        if (validFrmDtBinding.getValue() == null) {
            FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2439"));   //Valid from can not be left blank !
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(validFrmDtBinding.getClientId(), msg);
            return false;
        }

        if (parentIter.getEstimatedRowCount() == 0) {
            FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2440"));             //Please add leaves !
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, msg);
            return false;
        }
        return true;
    }

    public void addLeavesAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("CreateInsert1").execute();
    }

    public void deleteLeaveAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete").execute();
    }

    public void leaveIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String leave = object.toString();
            OperationBinding op = ADFBeanUtils.getOperationBinding("chkDuplicateLeave");
            op.getParamsMap().put("leaveId", leave);
            op.execute();
            if (op.getErrors().isEmpty() && op.getResult() != null) {
                String rslt = op.getResult().toString();
                if (rslt.equalsIgnoreCase("Y")) {
                    String msg = "Duplicate leave not allowed!";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }

    }

    public void addViewLeavePopAL(ActionEvent actionEvent) {
        if (ruleTypeBinding.getValue() == null) {
            FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2434"));    //Rule type can not be blank !
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(ruleTypeBinding.getClientId(), msg);
        } else if (shiftIdBinding.getValue() == null) {
            FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2435"));       //Shift can not be left blank !
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(shiftIdBinding.getClientId(), msg);
        } else {
            showPopup(leavePopBinding, true);
        }

    }

    public void setLeavePopBinding(RichPopup leavePopBinding) {
        this.leavePopBinding = leavePopBinding;
    }

    public RichPopup getLeavePopBinding() {
        return leavePopBinding;
    }

    public void setRuleTypeBinding(RichSelectOneChoice ruleTypeBinding) {
        this.ruleTypeBinding = ruleTypeBinding;
    }

    public RichSelectOneChoice getRuleTypeBinding() {
        return ruleTypeBinding;
    }

    public void setShiftIdBinding(RichSelectOneChoice shiftIdBinding) {
        this.shiftIdBinding = shiftIdBinding;
    }

    public RichSelectOneChoice getShiftIdBinding() {
        return shiftIdBinding;
    }

    public void setGraceMinuteBinding(RichInputText graceMinuteBinding) {
        this.graceMinuteBinding = graceMinuteBinding;
    }

    public RichInputText getGraceMinuteBinding() {
        return graceMinuteBinding;
    }

    public void setMaxAllowBinding(RichInputText maxAllowBinding) {
        this.maxAllowBinding = maxAllowBinding;
    }

    public RichInputText getMaxAllowBinding() {
        return maxAllowBinding;
    }

    public void setLeaveAdjDayBinding(RichInputText leaveAdjDayBinding) {
        this.leaveAdjDayBinding = leaveAdjDayBinding;
    }

    public RichInputText getLeaveAdjDayBinding() {
        return leaveAdjDayBinding;
    }

    public void setValidFrmDtBinding(RichInputDate validFrmDtBinding) {
        this.validFrmDtBinding = validFrmDtBinding;
    }

    public RichInputDate getValidFrmDtBinding() {
        return validFrmDtBinding;
    }

    public void okLinkOnPopAL(ActionEvent actionEvent) {
        leavePopBinding.hide();
    }

    public void setDocIdBinding(RichInputText docIdBinding) {
        this.docIdBinding = docIdBinding;
    }

    public RichInputText getDocIdBinding() {
        return docIdBinding;
    }

    public void replicateToAllShiftDesigAL(ActionEvent actionEvent) {
        boolean chk = validatebeforesave();
        if (chk) {
            OperationBinding ob = ADFBeanUtils.getOperationBinding("checkDuplicateRule");
            ob.getParamsMap().put("docId", docIdBinding.getValue());
            ob.execute();
            if (ob.getErrors().isEmpty() && ob.getResult() != null) {
                String rslt = ob.getResult().toString();
                if (rslt.equalsIgnoreCase("Y")) {
                    FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2433"));  //Duplicate Rule not allowed!
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(ruleTypeBinding.getClientId(), msg);
                } else {
                    ADFBeanUtils.getOperationBinding("replicateToAllShift").execute();
                }
            }
        }
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void leaveAdjDaysValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number not = (Number) object;
            if (not.compareTo(new Integer(0)) < 0) {
                String msg=ADFModelUtils.resolvRsrc("MSG.1006");
//                String msg = resolvElDCMsg("#{bundle['MSG.1006']}").toString();                 //Negative value not allowed!
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
            Boolean flag = isPrecisionValid(26, 6, ((Number) object));
            if (flag == false) {
                String msg=ADFModelUtils.resolvRsrc("MSG.1266");
               // String msg = resolvElDCMsg("#{bundle['MSG.1266']}").toString();                                //Invalid Precision
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }

    public void maxAllowValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer not = (Integer) object;
            if (not.compareTo(new Integer(0)) < 0) {
                String msg=ADFModelUtils.resolvRsrc("MSG.1152");
               // String msg = resolvElDCMsg("#{bundle['MSG.1152']}").toString();                    //Negative value not allowed!
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }

        }
    }

    public void graceTimeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number not = (Number) object;
            if (not.compareTo(new Integer(0)) < 0) {
                String msg=ADFModelUtils.resolvRsrc("MSG.1152");
               // String msg = resolvElDCMsg("#{bundle['MSG.1152']}").toString();                            //Negative value not allowed!
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }
    
}
