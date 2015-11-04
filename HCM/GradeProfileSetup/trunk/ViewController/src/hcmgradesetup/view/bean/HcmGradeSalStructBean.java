package hcmgradesetup.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import java.math.BigDecimal;

import java.sql.SQLException;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class HcmGradeSalStructBean
{
    private RichPopup gradReffSalPopUpBinding;
    private RichSelectOneChoice salCompNmBinding;
    private RichSelectOneChoice salTypeBinding;
    private RichInputText salAmntBinding;
    private RichInputText salValBinding;
    private RichInputDate validStrtDtBinding;
    private RichInputDate validEndDtBinding;
    private RichSelectOneChoice reffsalCompBinding;
    private RichInputText reffSalPercBinding;
    private RichInputDate reffValidStrtDtBinding;
    private RichInputDate reffValidEndDtBinding;
    private String add_edit_mode = "E";
    private RichSelectOneChoice salCompBinding;

    public void setAdd_edit_mode(String add_edit_mode)
    {
        this.add_edit_mode = add_edit_mode;
    }

    public String getAdd_edit_mode()
    {
        return add_edit_mode;
    }

    public HcmGradeSalStructBean()
    {
    }

    public void setGradReffSalPopUpBinding(RichPopup gradReffSalPopUpBinding)
    {
        this.gradReffSalPopUpBinding = gradReffSalPopUpBinding;
    }

    public RichPopup getGradReffSalPopUpBinding()
    {
        return gradReffSalPopUpBinding;
    }

    public boolean chkReffSallCompSaveValidation()
    {
        DCIteratorBinding di = ADFBeanUtils.findIterator("HcmGrdSalReff1Iterator");
        String message = "";
        if (di.getEstimatedRowCount() > 0)
        {
            if (reffsalCompBinding.getValue() == null || reffsalCompBinding.getValue() == "")
            {
                String cid = reffsalCompBinding.getClientId();
                message = "Please select refrencae .";
                showMessage(message, cid);
                return false;
            }
            if (reffSalPercBinding.getValue() == null || reffSalPercBinding.getValue() == "")
            {
                String cid = reffSalPercBinding.getClientId();
                message = "Please enter percentage.";
                showMessage(message, cid);
                return false;
            }
            if (reffValidStrtDtBinding.getValue() == null || reffValidStrtDtBinding.getValue() == "")
            {
                String cid = reffValidStrtDtBinding.getClientId();
                message = "Please select valid start date.";
                showMessage(message, cid);
                return false;
            }
        }
        return true;
    }

    public boolean chkSveSalStructValidation()
    {
        DCIteratorBinding di = ADFBeanUtils.findIterator("HcmGrdSal1Iterator");
        String message = "";
        if (di.getEstimatedRowCount() > 0)
        {
            if (salCompNmBinding.getValue() == null || salCompNmBinding.getValue() == "")
            {
                String cid = salCompNmBinding.getClientId();
                message = "Please select salary component.";
                showMessage(message, cid);
                return false;
            }
            if (salTypeBinding.getValue() == null || salTypeBinding.getValue() == "")
            {
                String cid = salTypeBinding.getClientId();
                message = "Please select type.";
                showMessage(message, cid);
                return false;
            }

            if (salValBinding.getValue() == null || salValBinding.getValue() == "")
            {
                String cid = salValBinding.getClientId();
                message = "Please enter salary amount.";
                showMessage(message, cid);
                return false;
            }
            else
            {
                if (salTypeBinding.getValue().equals("A"))
                {
                    BigDecimal ComZero = BigDecimal.ZERO;
                    BigDecimal value = new BigDecimal(salValBinding.getValue().toString());
                    int newValue = value.compareTo(ComZero);
                    if (newValue <= 0)
                    {
                        String salmsg = "salary amount can not be zero or less than zero";
                        ADFModelUtils.showFacesMessage(salmsg, salmsg, FacesMessage.SEVERITY_ERROR,
                                                       salValBinding.getClientId());
                        return false;
                    }
                }

            }
            if (validStrtDtBinding.getValue() == null || validStrtDtBinding.getValue() == "")
            {
                String cid = validStrtDtBinding.getClientId();
                message = "Please enter valid start date.";
                showMessage(message, cid);
                return false;
            }
        }

        return true;
    }

    public void openReffSallPopForAddAL(ActionEvent actionEvent)
    {
        boolean result = chkSveSalStructValidation();
        if (result)
        {
            OperationBinding opchk = null;
            opchk = ADFBeanUtils.getOperationBinding("makePostChanges");
            opchk.execute();
            showPopup(gradReffSalPopUpBinding, true);
        }

    }

    public void closeReffSallPopupAL(ActionEvent actionEvent)
    {
        boolean result = chkReffSallCompSaveValidation();
        if (result)
        {
            OperationBinding opchk = null;
            opchk = ADFBeanUtils.getOperationBinding("updateSalAmt");
            opchk.execute();
            gradReffSalPopUpBinding.hide();
        }

    }

    public void addGradeSalComAL(ActionEvent actionEvent)
    {

        OperationBinding opchk = null;
        opchk = ADFBeanUtils.getOperationBinding("CreateInsert");
        opchk.execute();
        opchk = ADFBeanUtils.getOperationBinding("setGrdDocIdInCurrentRow");
        opchk.execute();
        RequestContext.getCurrentInstance().getPageFlowScope().put("Add_Edit_Mode", "D");
    }

    /*  public void editGradeAL(ActionEvent actionEvent)
    {
        RequestContext.getCurrentInstance().getPageFlowScope().put("Add_Edit_Mode", "D");
    } */

    public void addGradeReffSallCompAL(ActionEvent actionEvent)
    {
        OperationBinding opchk = null;
        opchk = ADFBeanUtils.getOperationBinding("CreateInsert1");
        opchk.execute();
        opchk = ADFBeanUtils.getOperationBinding("setValidStartDt");
        opchk.execute();
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
                    script.append("var popup =AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
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
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void setSalCompNmBinding(RichSelectOneChoice salCompNmBinding)
    {
        this.salCompNmBinding = salCompNmBinding;
    }

    public RichSelectOneChoice getSalCompNmBinding()
    {
        return salCompNmBinding;
    }

    public void setSalTypeBinding(RichSelectOneChoice salTypeBinding)
    {
        this.salTypeBinding = salTypeBinding;
    }

    public RichSelectOneChoice getSalTypeBinding()
    {
        return salTypeBinding;
    }

    public void setSalAmntBinding(RichInputText salAmntBinding)
    {
        this.salAmntBinding = salAmntBinding;
    }

    public RichInputText getSalAmntBinding()
    {
        return salAmntBinding;
    }

    public void setSalValBinding(RichInputText salValBinding)
    {
        this.salValBinding = salValBinding;
    }

    public RichInputText getSalValBinding()
    {
        return salValBinding;
    }

    public void setValidStrtDtBinding(RichInputDate validStrtDtBinding)
    {
        this.validStrtDtBinding = validStrtDtBinding;
    }

    public RichInputDate getValidStrtDtBinding()
    {
        return validStrtDtBinding;
    }

    public void setValidEndDtBinding(RichInputDate validEndDtBinding)
    {
        this.validEndDtBinding = validEndDtBinding;
    }

    public RichInputDate getValidEndDtBinding()
    {
        return validEndDtBinding;
    }

    public Object resolvElDCMsg(String data)
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
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


    public String showMessage(String message, String cid)
    {
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(cid, fm);
        return null;
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

    public void setReffsalCompBinding(RichSelectOneChoice reffsalCompBinding)
    {
        this.reffsalCompBinding = reffsalCompBinding;
    }

    public RichSelectOneChoice getReffsalCompBinding()
    {
        return reffsalCompBinding;
    }

    public void setReffSalPercBinding(RichInputText reffSalPercBinding)
    {
        this.reffSalPercBinding = reffSalPercBinding;
    }

    public RichInputText getReffSalPercBinding()
    {
        return reffSalPercBinding;
    }

    public void setReffValidStrtDtBinding(RichInputDate reffValidStrtDtBinding)
    {
        this.reffValidStrtDtBinding = reffValidStrtDtBinding;
    }

    public RichInputDate getReffValidStrtDtBinding()
    {
        return reffValidStrtDtBinding;
    }

    public void setReffValidEndDtBinding(RichInputDate reffValidEndDtBinding)
    {
        this.reffValidEndDtBinding = reffValidEndDtBinding;
    }

    public RichInputDate getReffValidEndDtBinding()
    {
        return reffValidEndDtBinding;
    }

    public void deleteGradeSalStructAL(ActionEvent actionEvent)
    {
        OperationBinding opchk = null;
        opchk = ADFBeanUtils.getOperationBinding("deleteSalComp");
        opchk.execute();
        if (opchk.getResult() != null && opchk.getResult().toString().equals("Y"))
        {
            OperationBinding opDelRw = ADFBeanUtils.getOperationBinding("Delete1");
            opDelRw.execute();
        }
        else
        {
            showFacesMessage("Can not delete this salary component!!.Salary component is being used for refrence..", "E", false, "F");

        }

    }

    public void deleteReffSalCompAL(ActionEvent actionEvent)
    {
        OperationBinding opchk = null;
        opchk = ADFBeanUtils.getOperationBinding("Delete");
        opchk.execute();
    }

    public void salValVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != "")
        {
            salAmntBinding.setValue(valueChangeEvent.getNewValue());
        }
    }

    public void salValValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        System.out.println("object==" + object);
        String msg = "";
        if (object != null || object != "")
        {

            BigDecimal frCmpreZro = BigDecimal.ZERO;
            BigDecimal value = new BigDecimal(object.toString());
            int newvalue = value.compareTo(frCmpreZro);
            if (newvalue == -1)
            {
                msg = "salary value should not be zero!!";
                showFacesMessage(msg, "E", true, "V");
            }
        }

        else
        {
            String msg1 = "enter the salary!";
            ADFModelUtils.showFacesMessage(msg1, msg1, FacesMessage.SEVERITY_ERROR, salValBinding.getClientId());
        }
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total)
    {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void setSalCompBinding(RichSelectOneChoice salCompBinding)
    {
        this.salCompBinding = salCompBinding;
    }

    public RichSelectOneChoice getSalCompBinding()
    {
        return salCompBinding;
    }

    public boolean chkIsRefCompSameAsMainComp(Object object)
    {
        String msg = "";
        if (object != null && object.toString().length() > 0)
        {
            String reffsalid = object.toString();
            String salId = salCompBinding.getValue().toString();
            if (salId.equals(reffsalid))
            {
                msg = "Refrence component can not be same as main component. ";
                showFacesMessage(msg, "E", false, "V");
                return false;
            }
            return true;
        }
        return true;
    }


    public String goBackToPrevPageAction()
    {
        boolean result = chkSveSalStructValidation();
        if (result)
        {
            return "goBackAction";
        }
        return null;
    }

    public void okAllValidationAL(ActionEvent actionEvent)
    {
        boolean result = chkSveSalStructValidation();
        if (result)
        {
            showFacesMessage("Saved successfully.", "I", false, "F");
            RequestContext.getCurrentInstance().getPageFlowScope().put("Add_Edit_Mode", "E");
        }
    }

    public void chkDupliacateValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            String id = uIComponent.getId();
            if (id != null)
            {
                OperationBinding opchk = null;
                if (id.equals("salComponent"))
                {
                    String salId = object.toString();
                    System.out.println("salId=" + salId);
                    opchk = ADFBeanUtils.getOperationBinding("chkDuplicateSalComp");
                    opchk.getParamsMap().put("salId", salId);
                    opchk.execute();
                }
                if (id.equals("refSalComponent"))
                {
                    boolean chkresult = chkIsRefCompSameAsMainComp(object);
                    if (chkresult)
                    {
                        String refsalId = object.toString();
                        System.out.println("refsalId=" + refsalId);
                        opchk = ADFBeanUtils.getOperationBinding("chkDuplicateRefSalComp");
                        opchk.getParamsMap().put("refsalId", refsalId);
                        opchk.execute();
                    }
                }
                if (opchk != null)
                {
                    if (opchk.getErrors().size() == 0 && opchk.getResult() != null)
                    {
                        String chkRsult = (String) opchk.getResult();
                        if (chkRsult.equals("Y"))
                        {
                            showFacesMessage("Duplicate entry!!", "E", false, "V");
                        }
                    }
                    else
                    {
                        System.out.println("Error during duplicate check =" + opchk.getErrors());
                    }
                }

            }

        }
    }

    public void validateSalaryStructureEndDate(FacesContext facesContext, UIComponent uIComponent, Object object)
    {

        System.out.println("*****validateOrganizationEndDate*********");
        if (object != null && object.toString().length() > 0)
        {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;

            if (validStrtDtBinding.getValue() != null && validStrtDtBinding.getValue().toString().length() > 0)
            {
                try
                {
                    strtDt = ((Timestamp) validStrtDtBinding.getValue()).dateValue();
                    System.out.println("start date::::" + strtDt);
                    endDt = ((Timestamp) object).dateValue();
                    System.out.println("end date ::::::::" + endDt);
                }
                catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0)
                {
                    if (strtDt.toString().equals(endDt.toString()))
                    {
                    }
                    else
                    {
                        showFacesMessage("To Date can not be before Valid From Date. ", "E", false, "V");
                    }
                }
            }
        }
    }

    public void editGradeACL(ActionEvent actionEvent)
    {
        RequestContext.getCurrentInstance().getPageFlowScope().put("Add_Edit_Mode", "D");
    }
}
