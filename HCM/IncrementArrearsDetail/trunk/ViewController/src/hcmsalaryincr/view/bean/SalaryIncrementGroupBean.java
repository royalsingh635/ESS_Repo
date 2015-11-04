package hcmsalaryincr.view.bean;

import adf.utils.bean.ADFBeanUtils;

import java.math.BigDecimal;

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
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class SalaryIncrementGroupBean
{
    private RichInputDate validStartDtBinding;
    private RichInputText salAmntBinding;
    private RichInputDate validEndDtBinding;
    private RichSelectBooleanCheckbox deleteFlagBinding;
    private RichInputListOfValues salIDBinding;
    private RichPopup grpReffSalPopPupBinding;
    private RichOutputText salIdFrReffBinding;
    private RichSelectOneChoice salTypeBinding;

    public SalaryIncrementGroupBean()
    {
    }

    public void AddComponent(ActionEvent actionEvent)
    {
        RequestContext context = RequestContext.getCurrentInstance();
        context.getPageFlowScope().put("ADD_EDIT_MODE", "S");
        ADFBeanUtils.getOperationBinding("CreateInsert").execute();
    }

    public void chkAllValidationAfterAdd(ActionEvent actionEvent)
    {
        RequestContext context = RequestContext.getCurrentInstance();
        DCIteratorBinding di = ADFBeanUtils.findIterator("HCMIncrementGrpSumIterator");
        if (di.getEstimatedRowCount() > 0)
        {
            if (chkSveIncrmtValidation())
            {
                context.getPageFlowScope().put("ADD_EDIT_MODE", "D");
            }
        }
        else
        {
            context.getPageFlowScope().put("ADD_EDIT_MODE", "D");
        }
        OperationBinding opChk = ADFBeanUtils.getOperationBinding("applyPostChanges");
        opChk.execute();

    }

    public void amntValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            String msg = "";
            Boolean flag = isPrecisionValid(26, 6, ((Number) object));

            if (flag == false)
            {
                msg = "MSG.1434";
                showFacesMessage(msg, "E", true, "V");
            }
            else
            {
                BigDecimal frCmpreZro = BigDecimal.ZERO;
                BigDecimal value = new BigDecimal(object.toString());
                int newvalue = value.compareTo(frCmpreZro);
                if (newvalue == -1)
                {
                    msg = "MSG.1434";
                    showFacesMessage(msg, "E", true, "V");
                }
            }
        }
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total)
    {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public boolean chkSveIncrmtValidation()
    {
        String message = "";
        boolean deleteFlag = (Boolean) deleteFlagBinding.getValue();
        if (salTypeBinding.getValue() == null || salTypeBinding.getValue() == "")
        {
            String cid = salTypeBinding.getClientId();
            message = "Please select type.";
            showMessage(message, cid);
            return false;
        }
        if (salIDBinding.getValue() == null || salIDBinding.getValue() == "")
        {
            String cid = salIDBinding.getClientId();
            message = "Please select salary component.";
            showMessage(message, cid);
            return false;
        }
        if (deleteFlag)
        {
            if (validEndDtBinding.getValue() == null || validEndDtBinding.getValue() == "")
            {
                String cid = validEndDtBinding.getClientId();
                message = "Please select end date to end this component from group.";
                showMessage(message, cid);
                return false;
            }

        }
        else
        {
            if (validStartDtBinding.getValue() == null || validStartDtBinding.getValue() == "")
            {
                String cid = validStartDtBinding.getClientId();
                message = "Please select valid start date.";
                showMessage(message, cid);
                return false;
            }
            if (salAmntBinding.getValue() == null || salAmntBinding.getValue() == "")
            {
                String cid = salAmntBinding.getClientId();
                message = "Please enter amount.";
                showMessage(message, cid);
                return false;
            }
        }
        if(salTypeBinding.getValue().equals("P"))
        {
            DCIteratorBinding di = ADFBeanUtils.findIterator("HcmIncrGrpSummReffIterator");
            if (di.getEstimatedRowCount()==0)
            {
                message = "Please add reference or change type to Amount.";
                showMessage(message, null);
                return false;
            }
        }

        return true;
    }


    public void setValidStartDtBinding(RichInputDate validStartDtBinding)
    {
        this.validStartDtBinding = validStartDtBinding;
    }

    public RichInputDate getValidStartDtBinding()
    {
        return validStartDtBinding;
    }

    public void setSalAmntBinding(RichInputText salAmntBinding)
    {
        this.salAmntBinding = salAmntBinding;
    }

    public RichInputText getSalAmntBinding()
    {
        return salAmntBinding;
    }

    public void setValidEndDtBinding(RichInputDate validEndDtBinding)
    {
        this.validEndDtBinding = validEndDtBinding;
    }

    public RichInputDate getValidEndDtBinding()
    {
        return validEndDtBinding;
    }

    public void setDeleteFlagBinding(RichSelectBooleanCheckbox deleteFlagBinding)
    {
        this.deleteFlagBinding = deleteFlagBinding;
    }

    public RichSelectBooleanCheckbox getDeleteFlagBinding()
    {
        return deleteFlagBinding;
    }

    public String showMessage(String message, String cid)
    {

        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(cid, fm);
        return null;
    }


    public void salIDValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            boolean deleteFlag = (Boolean) deleteFlagBinding.getValue();

            OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDuplicateCompFrGroupFromName");
            opChk.getParamsMap().put("salComNm", object);
            opChk.execute();
            if (opChk.getErrors().size() > 0 || opChk.getResult() == null)
                System.out.println("Error on check duplicate=" + opChk.getErrors());
            else
            {
                if (opChk.getResult().toString().equals("Y"))
                {
                    showFacesMessage("MSG.1397", "E", true, "V");
                }
                else if (opChk.getResult().toString().equals("N"))
                {
                    OperationBinding opsetDT = ADFBeanUtils.getOperationBinding("setIncrDateInEndOrStartDate");
                    opsetDT.getParamsMap().put("deleteFlag", deleteFlag);
                    opsetDT.execute();
                    if (opsetDT.getErrors().size() > 0 || opsetDT.getResult() == null)
                        System.out.println("Error on set date=" + opsetDT.getErrors());
                }

            }


        }
    }

    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg)
    {
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true)
        {
            String msg = (String) resolvEl("#{bundle['" + mesg + "']}");
            message = new FacesMessage(msg);
        }
        if (sev.equalsIgnoreCase("E"))
        {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        }
        else if (sev.equalsIgnoreCase("W"))
        {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
        }
        else if (sev.equalsIgnoreCase("I"))
        {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        else
        {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        if (typFlg.equals("F"))
        {
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        else if (typFlg.equals("V"))
        {
            throw new ValidatorException(message);
        }
    }

    public String resolvEl(String data)
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }

    public void setSalIDBinding(RichInputListOfValues salIDBinding)
    {
        this.salIDBinding = salIDBinding;
    }

    public RichInputListOfValues getSalIDBinding()
    {
        return salIDBinding;
    }

    public void deleteActionListner(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("Delete").execute();
    }

    public void editAllActionListner(ActionEvent actionEvent)
    {
        RequestContext context = RequestContext.getCurrentInstance();
        context.getPageFlowScope().put("ADD_EDIT_MODE", "S");
    }

    public void setGrpReffSalPopPupBinding(RichPopup grpReffSalPopPupBinding)
    {
        this.grpReffSalPopPupBinding = grpReffSalPopPupBinding;
    }

    public RichPopup getGrpReffSalPopPupBinding()
    {
        return grpReffSalPopPupBinding;
    }

    public void openGrpReffPopUpActionListner(ActionEvent actionEvent)
    {
        showPopup(grpReffSalPopPupBinding, true);
    }

    private void showPopup(RichPopup pop, boolean visible)
    {
        try
        {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null)
            {
                String popupId = pop.getClientId(context);
                if (popupId != null)
                {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible)
                    {
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                    }
                    else
                    {
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public void addSummReffCompActionListner(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("CreateInsert1").execute();
    }

    public void deleteSummReffComActionListner(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("Delete1").execute();
    }

    public void closeGrpReffSummPopPupActionListner(ActionEvent actionEvent)
    {
        grpReffSalPopPupBinding.hide();
    }

    public void ClosepopupAfterChkValidationActionListner(ActionEvent actionEvent)
    {
        grpReffSalPopPupBinding.hide();
    }

    public void reffSalIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            if (object.equals(salIdFrReffBinding.getValue()))
            {
                showFacesMessage("Salary component and referred salary component both can not be same.", "E", false,
                                 "V");
            }
            else
            {
                OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDuplicateReffComp");
                opChk.getParamsMap().put("RefSalId", object);
                opChk.getParamsMap().put("SalId", salIdFrReffBinding.getValue());
                opChk.execute();
                if (opChk.getErrors().size() > 0 || opChk.getResult() == null)
                    System.out.println("Error on check duplicate=" + opChk.getErrors());
                else
                {
                    if (opChk.getResult().toString().equals("Y"))
                        showFacesMessage("MSG.1436", "E", true, "V");
                }
            }
        }
    }

    public void setSalIdFrReffBinding(RichOutputText salIdFrReffBinding)
    {
        this.salIdFrReffBinding = salIdFrReffBinding;
    }

    public RichOutputText getSalIdFrReffBinding()
    {
        return salIdFrReffBinding;
    }

    public void setSalTypeBinding(RichSelectOneChoice salTypeBinding)
    {
        this.salTypeBinding = salTypeBinding;
    }

    public RichSelectOneChoice getSalTypeBinding()
    {
        return salTypeBinding;
    }

    public void salTypeValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        Number zero = new Number(0);
        salAmntBinding.setValue(zero);
    }
}


