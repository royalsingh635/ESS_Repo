package hcmdedprfsetupapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.bean.StaticValue;
import adf.utils.model.ADFModelUtils;

import java.math.BigDecimal;

import java.util.Calendar;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class LoanProfBean
{
    private RichPopup salPopBinding;
    private RichSelectOneChoice loanTypeBinding;
    private RichSelectOneChoice employeeEligibityBinding;
    private RichSelectOneChoice intrestRuleBinding;
    private RichSelectOneChoice interestTypeBinding;
    private RichInputText interestRateBinding;
    private RichInputText penallInterestRateBinding;
    private RichInputText miximumNoOgEmiBinding;
    private RichInputText maxSubsidyBinding;
    private RichInputText servicePeriodRequiredBinding;
    private RichInputDate validFromBinding;
    private RichInputText loanRecvryBinding;
    private RichSelectBooleanCheckbox penalFlagBinding;
    private RichSelectBooleanCheckbox subsidyFlagBinding;
    private RichOutputText loanInterestFlagBinding;

    public LoanProfBean()
    {
    }

    public void AddSalaryProfAl(ActionEvent actionEvent)
    {
        // Add event code here...

        showPopup(salPopBinding, true);

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

    public void setSalPopBinding(RichPopup salPopBinding)
    {
        this.salPopBinding = salPopBinding;
    }

    public RichPopup getSalPopBinding()
    {
        return salPopBinding;
    }

    public void addLoanProfAL(ActionEvent actionEvent)
    {

        OperationBinding AddLoan = ADFBeanUtils.getOperationBinding("CreateInsert");
        AddLoan.execute();
        OperationBinding AddLoanCrateDocID = ADFBeanUtils.getOperationBinding("CrateDocID");
        AddLoanCrateDocID.execute();
        RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_ADD_EDIT", "D");
    }

    public void dleateLoanProfAL(ActionEvent actionEvent)
    {
        ADFBeanUtils.callBindingsMethod("Delete", null, null);

        // Add event code here...
    }

    public void saveloanProfAL(ActionEvent actionEvent)
    {
        DCIteratorBinding di = ADFBeanUtils.findIterator("hcmLoanPrf1Iterator");
        String message = "";
        boolean check = true;
        if (di.getEstimatedRowCount() > 0)
        {

            check = ValidationallField();
            if (check)
            {
                check = chkSalCompValidation();
            }
        }

        if (check)
        {
            ADFBeanUtils.getOperationBinding("Commit").execute();
            String mssg = "Saved successfully";
            showFacesMessage(mssg, "I", false, "F");
            RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_ADD_EDIT", "E");
        }
    }

    public void rollbackloanProfAL(ActionEvent actionEvent)
    {
        ADFBeanUtils.callBindingsMethod("Rollback", null, null);
        RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_ADD_EDIT", "E");
        // Add event code here...
    }

    public void EditLoanProfAL(ActionEvent actionEvent)
    {
        RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_ADD_EDIT", "D");
        // Add event code here...
    }

    public void searchLoanProfAL(ActionEvent actionEvent)
    {
        // Add event code here...
        ADFBeanUtils.callBindingsMethod("SearchLoanProfAm", null, null);
    }

    public void ResetloanProf(ActionEvent actionEvent)
    {
        ADFBeanUtils.callBindingsMethod("resetLoanProfAm", null, null);
        // Add event code here...
    }

    public boolean ValidationallField()
    {
        if (getLoanTypeBinding().getValue() == null || getLoanTypeBinding().getValue() == "")
        {
            String msg = " Select Loan ";
            ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_ERROR, getLoanTypeBinding().getClientId());
            return false;
        }
        //        else
        //        {
        //            OperationBinding chkLoanNmOP = ADFBeanUtils.getOperationBinding("chkDuplicateLoanName");
        //            chkLoanNmOP.execute();
        //            if (chkLoanNmOP.getErrors().size() > 0 || chkLoanNmOP.getResult() == null)
        //            {
        //                System.out.println(chkLoanNmOP.getErrors());
        //            }
        //            else
        //            {
        //                if (chkLoanNmOP.getResult().toString().equals("Y"))
        //                {
        //                    showMessage("Duplicate Entry!!", getLoanTypeBinding().getClientId());
        //                    return false;
        //                }
        //
        //            }
        //
        //        }
        if (getIntrestRuleBinding().getValue() == null || getIntrestRuleBinding().getValue() == "")
        {
            String msg = " select Interest Rule ";
            ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_ERROR, intrestRuleBinding.getClientId());
            return false;
        }
        if (getInterestTypeBinding().getValue() == null || getInterestTypeBinding().getValue() == "")
        {
            String msg = " select Interest Type ";
            ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_ERROR, interestTypeBinding.getClientId());
            return false;
        }
        if (getEmployeeEligibityBinding().getValue() == null || getEmployeeEligibityBinding().getValue() == "")
        {
            String msg = " select Loan Eligibility";
            ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_ERROR,
                                           employeeEligibityBinding.getClientId());
            return false;
        }

        if (getInterestRateBinding().getValue() == null || getInterestRateBinding().getValue() == "")
        {
            String msg = " select Interest rate ";
            ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_ERROR, interestRateBinding.getClientId());
            return false;


        }
        else if (getInterestRateBinding().getValue() != null)
        {
            BigDecimal CompareZero = BigDecimal.ZERO;
            BigDecimal value = new BigDecimal(getInterestRateBinding().getValue().toString());
            int newValue = (int) value.compareTo(CompareZero);
            if (newValue < 0 || newValue > 100)
            {
                String msg = "Interest Rate should be in between o to 100";
                ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_ERROR,
                                               interestRateBinding.getClientId());
                return false;
            }


        }
        if (loanInterestFlagBinding.getValue() != null || loanInterestFlagBinding.getValue() != "")
        {
            if (loanInterestFlagBinding.getValue().toString().equals("Y"))
            {
                if (getLoanRecvryBinding().getValue() == null || getLoanRecvryBinding().getValue() == "")
                {
                    String msg = "Please loan recovery in months. ";
                    ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_ERROR,
                                                   loanRecvryBinding.getClientId());
                    return false;
                }
                else if (getLoanRecvryBinding().getValue() != null)
                {
                    Object loanAmt = this.getLoanRecvryBinding().getValue();
                    Object Compre = 0;
                    Integer i = (loanAmt == null ? -1 : (Integer) loanAmt);
                    Integer C = (Compre == null ? -1 : (Integer) Compre);
                    if (i.compareTo(C) <= 0)
                    {
                        ADFModelUtils.showFacesMessage("Lone recovery can not be negative",
                                                       "Lone recovery can not be negative", FacesMessage.SEVERITY_ERROR,
                                                       loanRecvryBinding.getClientId());
                        return false;
                    }


                }
            }
        }

        String penalflag = penalFlagBinding.getValue().toString();
        if (penalflag.equals("true"))
        {
            if (penallInterestRateBinding.getValue() == null || penallInterestRateBinding.getValue() == "")
            {
                String msg = "Please enter penal Interest. ";
                ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_ERROR,
                                               penallInterestRateBinding.getClientId());
                return false;
            }
            else if (penallInterestRateBinding.getValue() != null)
            {
                BigDecimal CompareZero = BigDecimal.ZERO;
                BigDecimal value = new BigDecimal(getPenallInterestRateBinding().getValue().toString());
                int newValue = (int) value.compareTo(CompareZero);
                if (newValue <= 0 || newValue > 100)
                {
                    String msg = "penall Interest Rate should be in between o to 100";
                    ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_ERROR,
                                                   penallInterestRateBinding.getClientId());
                    return false;
                }


            }
        }


        String subsidyflag = subsidyFlagBinding.getValue().toString();
        if (subsidyflag.equals("true"))
        {
            if (maxSubsidyBinding.getValue() == null || maxSubsidyBinding.getValue() == "")
            {
                String msg = "Please enter max subsidy ";
                ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_ERROR, maxSubsidyBinding.getClientId());
                return false;
            }
        }
        else if (maxSubsidyBinding.getValue() != null)
        {
            BigDecimal CompareZero = BigDecimal.ZERO;
            BigDecimal value = new BigDecimal(getMaxSubsidyBinding().getValue().toString());
            int newValue = (int) value.compareTo(CompareZero);
            if (newValue <= 0 || newValue > 100)
            {
                String msg = "Max  Rate should be greater than 0 ";
                ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_ERROR, maxSubsidyBinding.getClientId());
                return false;
            }

        }
        if (miximumNoOgEmiBinding.getValue() == null || miximumNoOgEmiBinding.getValue() == "")
        {
            String msg = "Please enter number if EMI ";
            ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_ERROR, miximumNoOgEmiBinding.getClientId());
            return false;
        }
        else if (miximumNoOgEmiBinding.getValue() != null)
        {
            BigDecimal CompareZero = BigDecimal.ZERO;
            BigDecimal value = new BigDecimal(getMiximumNoOgEmiBinding().getValue().toString());
            int newValue = (int) value.compareTo(CompareZero);
            if (newValue <= 0 || newValue > 100)
            {
                String msg = "Max. number of EMI  should be greater than 0 ";
                ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_ERROR,
                                               miximumNoOgEmiBinding.getClientId());
                return false;
            }

        }
        if (getServicePeriodRequiredBinding().getValue() == null || getServicePeriodRequiredBinding().getValue() == "")
        {
            String msg = "Please enter service period require for this loan.";
            ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_ERROR,
                                           servicePeriodRequiredBinding.getClientId());
            return false;
        }
        else if (getServicePeriodRequiredBinding().getValue() != null)
        {
            BigDecimal CompareZero = BigDecimal.ZERO;
            BigDecimal value = new BigDecimal(getServicePeriodRequiredBinding().getValue().toString());
            int newValue = (int) value.compareTo(CompareZero);
            if (newValue <= 0 || newValue > 100)
            {
                String msg = "Max. number of EMI  should be greater than 0 ";
                ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_ERROR,
                                               servicePeriodRequiredBinding.getClientId());
                return false;
            }
        }
        if (getValidFromBinding().getValue() == null || getValidFromBinding().getValue() == "")
        {
            String msg = "Please select valid from date ";
            ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_ERROR, validFromBinding.getClientId());
            return false;
        }


        return true;
    }

    public boolean chkSalCompValidation()
    {
        System.out.println("chkSalCompValidation in bean");
        DCIteratorBinding di = ADFBeanUtils.findIterator("hcmLoanSal1Iterator");
        String message = "";
        if (di.getEstimatedRowCount() > 0)
        {
            return true;
        }
        else
        {
            String msg = "Please add salary component. ";
            ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_ERROR, null);
            return false;
        }
    }

    public void setLoanTypeBinding(RichSelectOneChoice loanTypeBinding)
    {
        this.loanTypeBinding = loanTypeBinding;
    }

    public RichSelectOneChoice getLoanTypeBinding()
    {
        return loanTypeBinding;
    }

    public void setEmployeeEligibityBinding(RichSelectOneChoice employeeEligibityBinding)
    {
        this.employeeEligibityBinding = employeeEligibityBinding;
    }

    public RichSelectOneChoice getEmployeeEligibityBinding()
    {
        return employeeEligibityBinding;
    }

    public void setIntrestRuleBinding(RichSelectOneChoice intrestRuleBinding)
    {
        this.intrestRuleBinding = intrestRuleBinding;
    }

    public RichSelectOneChoice getIntrestRuleBinding()
    {
        return intrestRuleBinding;
    }

    public void setInterestTypeBinding(RichSelectOneChoice interestTypeBinding)
    {
        this.interestTypeBinding = interestTypeBinding;
    }

    public RichSelectOneChoice getInterestTypeBinding()
    {
        return interestTypeBinding;
    }

    public void setInterestRateBinding(RichInputText interestRateBinding)
    {
        this.interestRateBinding = interestRateBinding;
    }

    public RichInputText getInterestRateBinding()
    {
        return interestRateBinding;
    }

    public void setPenallInterestRateBinding(RichInputText penallInterestRateBinding)
    {
        this.penallInterestRateBinding = penallInterestRateBinding;
    }

    public RichInputText getPenallInterestRateBinding()
    {
        return penallInterestRateBinding;
    }

    public void setMiximumNoOgEmiBinding(RichInputText miximumNoOgEmiBinding)
    {
        this.miximumNoOgEmiBinding = miximumNoOgEmiBinding;
    }

    public RichInputText getMiximumNoOgEmiBinding()
    {
        return miximumNoOgEmiBinding;
    }

    public void setMaxSubsidyBinding(RichInputText maxSubsidyBinding)
    {
        this.maxSubsidyBinding = maxSubsidyBinding;
    }

    public RichInputText getMaxSubsidyBinding()
    {
        return maxSubsidyBinding;
    }

    public void setServicePeriodRequiredBinding(RichInputText servicePeriodRequiredBinding)
    {
        this.servicePeriodRequiredBinding = servicePeriodRequiredBinding;
    }

    public RichInputText getServicePeriodRequiredBinding()
    {
        return servicePeriodRequiredBinding;
    }

    public void setValidFromBinding(RichInputDate validFromBinding)
    {
        this.validFromBinding = validFromBinding;
    }

    public RichInputDate getValidFromBinding()
    {
        return validFromBinding;
    }

    public void setLoanRecvryBinding(RichInputText loanRecvryBinding)
    {
        this.loanRecvryBinding = loanRecvryBinding;
    }

    public RichInputText getLoanRecvryBinding()
    {
        return loanRecvryBinding;
    }

    public void addSalComAL(ActionEvent actionEvent)
    {
        OperationBinding AddLoan = ADFBeanUtils.getOperationBinding("CreateInsert1");
        AddLoan.execute();
    }

    public void deletSalCompAL(ActionEvent actionEvent)
    {
        OperationBinding AddLoan = ADFBeanUtils.getOperationBinding("Delete1");
        AddLoan.execute();
    }

    public void closePopupAL(ActionEvent actionEvent)
    {
        salPopBinding.hide();
    }

    public void setPenalFlagBinding(RichSelectBooleanCheckbox penalFlagBinding)
    {
        this.penalFlagBinding = penalFlagBinding;
    }

    public RichSelectBooleanCheckbox getPenalFlagBinding()
    {
        return penalFlagBinding;
    }

    public void setSubsidyFlagBinding(RichSelectBooleanCheckbox subsidyFlagBinding)
    {
        this.subsidyFlagBinding = subsidyFlagBinding;
    }

    public RichSelectBooleanCheckbox getSubsidyFlagBinding()
    {
        return subsidyFlagBinding;
    }

    public void chkDuplicateslryComp(FacesContext facesContext, UIComponent uIComponent, Object object)
    {

        if (object != null && object.toString().length() > 0)
        {
            OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDuplicateSlryCmponentFrLoan");
            opChk.getParamsMap().put("salId", object);
            opChk.execute();
            if (opChk.getErrors().size() > 0 || opChk.getResult() == null)
                System.out.println("Error on check duplicate=" + opChk.getErrors());
            else
            {
                if (opChk.getResult().toString().equals("Y"))
                    showFacesMessage("MSG.1397", "E", true, "V");
            }

        }
    }

    /* public void chkValidFromDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

            System.out.println("*****validate fROm Date*********");
            if (object != null && object.toString().length() > 0) {
                java.sql.Date fromDate = null;
             }
    } */

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

    public String showMessage(String message, String cid)
    {

        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(cid, fm);
        return null;
    }


    public void setLoanInterestFlagBinding(RichOutputText loanInterestFlagBinding)
    {
        this.loanInterestFlagBinding = loanInterestFlagBinding;
    }

    public RichOutputText getLoanInterestFlagBinding()
    {
        return loanInterestFlagBinding;
    }
}
