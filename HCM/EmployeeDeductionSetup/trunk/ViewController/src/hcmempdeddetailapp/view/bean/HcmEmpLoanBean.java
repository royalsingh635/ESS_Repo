package hcmempdeddetailapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import java.math.BigDecimal;

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

import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class HcmEmpLoanBean {
    private RichInputText downPaymentBInding;
    private RichInputDate emiStartDtBinding;
    private RichInputText noOfEMIBinding;
    private RichInputText interestRtBinding;
    private RichInputText penalInteresetRateBinding;
    private RichInputText sunsdyRateBinding;
    private RichInputText totLoanAmntBinding;
    private RichInputDate validStrtDtBinding;
    private RichInputDate validEndDtBindng;
    private RichSelectOneChoice loanNMBinding;
    private RichOutputText intrstRateFlagBinding;
    private RichOutputText subsdyFlagBinding;
    private RichOutputText penalRateFlagBinding;
    private RichInputText loanAmntBinding;
    private RichOutputText bindingMaxSubsdyRate;
    private RichOutputText bindingMaxNoOfEmi;
    private RichOutputText bindingMaxPenalIntrstRate;
    private RichOutputText bindingMaxIntrestRate;
    private RichPopup emiPopPupBinding;

    public HcmEmpLoanBean() {
    }

    public void addNewEmpLoanAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("CreateInsert").execute();
        ADFBeanUtils.getOperationBinding("setDocId").execute();
        RequestContext.getCurrentInstance().getPageFlowScope().put("ADD_EDIT_MODE", "D");

    }

    public void deleteLoanAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete").execute();
        // RequestContext.getCurrentInstance().getPageFlowScope().put("ADD_EDIT_MODE", "A");
    }

    public void editLoanAL(ActionEvent actionEvent) {
        RequestContext.getCurrentInstance().getPageFlowScope().put("ADD_EDIT_MODE", "D");
    }

    public boolean chkSveLoanValiddation() {
        DCIteratorBinding di = ADFBeanUtils.findIterator("HcmEmpLoan1Iterator");
        String message = "";
        if (di.getEstimatedRowCount() > 0) {
            if (loanNMBinding.getValue() == null || loanNMBinding.getValue() == "") {
                String cid = loanNMBinding.getClientId();
                message = "Please select loan.";
                showMessage(message, cid);
                return false;
            }

            if (loanAmntBinding.getValue() == null || loanAmntBinding.getValue() == "") {
                String cid = loanAmntBinding.getClientId();
                message = "Please enter loan amount.";
                showMessage(message, cid);
                return false;
            }

            if (noOfEMIBinding.getValue() == null || noOfEMIBinding.getValue() == "") {
                String cid = noOfEMIBinding.getClientId();
                message = "Please enter number of EMI.";
                showMessage(message, cid);
                return false;
            }
            if (emiStartDtBinding.getValue() == null || emiStartDtBinding.getValue() == "") {
                String cid = emiStartDtBinding.getClientId();
                message = "Please enter EMI start date.";
                showMessage(message, cid);
                return false;
            }
            if (intrstRateFlagBinding.getValue().equals("V")) {
                if (interestRtBinding.getValue() == null || interestRtBinding.getValue() == "") {
                    String cid = interestRtBinding.getClientId();
                    message = "Please enter interest rate.";
                    showMessage(message, cid);
                    return false;
                }else
                {
                    Number maxInterrest=(Number)bindingMaxIntrestRate.getValue();
                    Number interrest=(Number)interestRtBinding.getValue();
                    System.out.println("maxInterrest"+maxInterrest);
                        System.out.println("interrest"+interrest);
                       
                    if(interrest.compareTo(maxInterrest)>0)
                    {
                        System.out.println("in if");
                            System.err.println("maxInterrest=="+maxInterrest);
                            String cid = interestRtBinding.getClientId();
                            message = "Interest rate should be less than max Interest rate.";
                            showMessage(message, cid);
                            return false;
                           
                        }
                    }
            }
            else if (intrstRateFlagBinding.getValue().equals("F")) {
//                System.out.println("VLC");
//
//                System.out.println("interset Rate" + bindingMaxIntrestRate.getValue());
//                interestRtBinding.setValue(bindingMaxIntrestRate.getValue());


            }

            if (penalRateFlagBinding.getValue().equals("Y")) {
                if (penalInteresetRateBinding.getValue() == null || penalInteresetRateBinding.getValue() == "") {
                    String cid = penalInteresetRateBinding.getClientId();
                    message = "Please enter penal interest.";
                    showMessage(message, cid);
                    return false;
                }
            }

            if (subsdyFlagBinding.getValue().equals("Y")) {
                if (sunsdyRateBinding.getValue() == null || sunsdyRateBinding.getValue() == "") {
                    String cid = sunsdyRateBinding.getClientId();
                    message = "Please enter subsidy rate.";
                    showMessage(message, cid);
                    return false;
                }
            }

            if (validStrtDtBinding.getValue() == null || validStrtDtBinding.getValue() == "") {
                String cid = validStrtDtBinding.getClientId();
                message = "Please enter valid start date.";
                showMessage(message, cid);
                return false;
            }
        }
        return true;

    }

    public void okChkAllValidationBefroeMoveAL(ActionEvent actionEvent) {
        boolean result = chkSveLoanValiddation();
        if (result) {
            ADFBeanUtils.getOperationBinding("Commit").execute();
            showFacesMessage("Saved successfully.", "I", false, "F");
            RequestContext.getCurrentInstance().getPageFlowScope().put("ADD_EDIT_MODE", "V");
        }

    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }


    public String showMessage(String message, String cid) {
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(cid, fm);
        return null;
    }

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

    public void searchLoanAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("searchLoan").execute();
    }

    public void resetLoanAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("resetLoan").execute();
    }

    public void setDownPaymentBInding(RichInputText downPaymentBInding) {
        this.downPaymentBInding = downPaymentBInding;
    }

    public RichInputText getDownPaymentBInding() {
        return downPaymentBInding;
    }

    public void setEmiStartDtBinding(RichInputDate emiStartDtBinding) {
        this.emiStartDtBinding = emiStartDtBinding;
    }

    public RichInputDate getEmiStartDtBinding() {
        return emiStartDtBinding;
    }

    public void setNoOfEMIBinding(RichInputText noOfEMIBinding) {
        this.noOfEMIBinding = noOfEMIBinding;
    }

    public RichInputText getNoOfEMIBinding() {
        return noOfEMIBinding;
    }

    public void setInterestRtBinding(RichInputText interestRtBinding) {
        this.interestRtBinding = interestRtBinding;
    }

    public RichInputText getInterestRtBinding() {
        return interestRtBinding;
    }

    public void setPenalInteresetRateBinding(RichInputText penalInteresetRateBinding) {
        this.penalInteresetRateBinding = penalInteresetRateBinding;
    }

    public RichInputText getPenalInteresetRateBinding() {
        return penalInteresetRateBinding;
    }

    public void setSunsdyRateBinding(RichInputText sunsdyRateBinding) {
        this.sunsdyRateBinding = sunsdyRateBinding;
    }

    public RichInputText getSunsdyRateBinding() {
        return sunsdyRateBinding;
    }

    public void setTotLoanAmntBinding(RichInputText totLoanAmntBinding) {
        this.totLoanAmntBinding = totLoanAmntBinding;
    }

    public RichInputText getTotLoanAmntBinding() {
        return totLoanAmntBinding;
    }

    public void setValidStrtDtBinding(RichInputDate validStrtDtBinding) {
        this.validStrtDtBinding = validStrtDtBinding;
    }

    public RichInputDate getValidStrtDtBinding() {
        return validStrtDtBinding;
    }

    public void setValidEndDtBindng(RichInputDate validEndDtBindng) {
        this.validEndDtBindng = validEndDtBindng;
    }

    public RichInputDate getValidEndDtBindng() {
        return validEndDtBindng;
    }

    public void setLoanNMBinding(RichSelectOneChoice loanNMBinding) {
        this.loanNMBinding = loanNMBinding;
    }

    public RichSelectOneChoice getLoanNMBinding() {
        return loanNMBinding;
    }

    public void setIntrstRateFlagBinding(RichOutputText intrstRateFlagBinding) {
        this.intrstRateFlagBinding = intrstRateFlagBinding;
    }

    public RichOutputText getIntrstRateFlagBinding() {
        return intrstRateFlagBinding;
    }

    public void setSubsdyFlagBinding(RichOutputText subsdyFlagBinding) {
        this.subsdyFlagBinding = subsdyFlagBinding;
    }

    public RichOutputText getSubsdyFlagBinding() {
        return subsdyFlagBinding;
    }

    public void setPenalRateFlagBinding(RichOutputText penalRateFlagBinding) {
        this.penalRateFlagBinding = penalRateFlagBinding;
    }

    public RichOutputText getPenalRateFlagBinding() {
        return penalRateFlagBinding;
    }

    public void setLoanAmntBinding(RichInputText loanAmntBinding) {
        this.loanAmntBinding = loanAmntBinding;
    }

    public RichInputText getLoanAmntBinding() {
        return loanAmntBinding;
    }

    public void loanAmntVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != "" && valueChangeEvent.getNewValue() != null) {
            downPaymentBInding.setValue(BigDecimal.ZERO);

            BigDecimal finalLnAmnt = new BigDecimal(valueChangeEvent.getNewValue().toString());
            totLoanAmntBinding.setValue(finalLnAmnt);
        }
    }

    public void downPaymentVCL(ValueChangeEvent valueChangeEvent) {
    }

    public void loanAmntValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object != "") {
            String msg = "";
            BigDecimal frCmpreZro = BigDecimal.ZERO;
            BigDecimal value = new BigDecimal(object.toString());
            int newvalue = value.compareTo(frCmpreZro);
            if (newvalue == -1) {
                msg = "Loan amount should not be less than zero!!";
                showFacesMessage(msg, "E", true, "V");
            }
        }

    }

    public void downPaymentValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object != "") {
            String msg = "";
            BigDecimal frCmpreZro = BigDecimal.ZERO;
            BigDecimal value = new BigDecimal(object.toString());
            int newvalue = value.compareTo(frCmpreZro);
            if (newvalue == -1) {
                msg = "downpayment value should not be less than zero!!";
                showFacesMessage(msg, "E", false, "V");
            } else {
                if (loanAmntBinding.getValue() != null && loanAmntBinding.getValue() != "") {
                    BigDecimal dowPayment = new BigDecimal(object.toString());
                    BigDecimal loanPayment = new BigDecimal(loanAmntBinding.getValue().toString());
                    int cparevalue = loanPayment.compareTo(dowPayment);
                    if (cparevalue == -1) {
                        msg = "down payment value should not be less than loan amount value!!";
                        showFacesMessage(msg, "E", false, "V");
                    } else {
                        BigDecimal finalLnAmnt = loanPayment.subtract(dowPayment);
                        totLoanAmntBinding.setValue(finalLnAmnt);
                    }
                }

            }
        }
    }

    public void setBindingMaxSubsdyRate(RichOutputText bindingMaxSubsdyRate) {
        this.bindingMaxSubsdyRate = bindingMaxSubsdyRate;
    }

    public RichOutputText getBindingMaxSubsdyRate() {
        return bindingMaxSubsdyRate;
    }


    public void maxNoOfEmiValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null && object.toString().length() > 0) {
            if (bindingMaxNoOfEmi.getValue() != null) {

                oracle.jbo.domain.Number NoOfEmi = (oracle.jbo.domain.Number) object;
                oracle.jbo.domain.Number MmaxnoOfEmi = (oracle.jbo.domain.Number) bindingMaxNoOfEmi.getValue();
                if (NoOfEmi.compareTo(0) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                                                  "No Of EMI can not be in negative"));

                }
                if (NoOfEmi.compareTo(MmaxnoOfEmi) > 0) {

                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                                                  "No of EMI should be less than Max EMI (" +
                                                                  MmaxnoOfEmi + ") in Loan Profile Setup"));
                }
            }

        }
    }

    public void setBindingMaxNoOfEmi(RichOutputText bindingMaxNoOfEmi) {
        this.bindingMaxNoOfEmi = bindingMaxNoOfEmi;
    }

    public RichOutputText getBindingMaxNoOfEmi() {
        return bindingMaxNoOfEmi;
    }

    public void subsidyRateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            if (subsdyFlagBinding.getValue().equals("Y")) {
                if (bindingMaxSubsdyRate.getValue() != null) {
                    oracle.jbo.domain.Number SubsidyRate = (oracle.jbo.domain.Number) object;

                    oracle.jbo.domain.Number MaxSubsidyRate =
                        (oracle.jbo.domain.Number) bindingMaxSubsdyRate.getValue();
                    if (SubsidyRate.compareTo(0) < 0) {
                        System.out.println("Test1");
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                                                      "SubsidyRate can not be in negative"));

                    }
                    if (SubsidyRate.compareTo(MaxSubsidyRate) > 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                                                      "SubsidyRate should be less than Max SubsidyRate (" +
                                                                      MaxSubsidyRate + ") in Loan Profile Setup"));
                    }

                }
            }

        }
    }

    public void setBindingMaxPenalIntrstRate(RichOutputText bindingMaxPenalIntrstRate) {
        this.bindingMaxPenalIntrstRate = bindingMaxPenalIntrstRate;
    }

    public RichOutputText getBindingMaxPenalIntrstRate() {
        return bindingMaxPenalIntrstRate;
    }

    public void penalIntrstRateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null && object.toString().length() > 0) {
            if (penalRateFlagBinding.getValue().equals("Y")) {
                if (bindingMaxPenalIntrstRate.getValue() != null) {
                    oracle.jbo.domain.Number PenalIntrstRate = (oracle.jbo.domain.Number) object;
                    oracle.jbo.domain.Number MaxPenalIntrstRate =
                        (oracle.jbo.domain.Number) bindingMaxPenalIntrstRate.getValue();

                    if (PenalIntrstRate.compareTo(0) < 0) {

                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                                                      "PenalIntrestRate can not be in negative"));
                    }
                    if (PenalIntrstRate.compareTo(MaxPenalIntrstRate) > 0) {
                        System.out.println("Test2");
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                                                      "PenalIntrestRate should be less than Max PenalIntrestRate (" +
                                                                      MaxPenalIntrstRate + ") Profile Setup"));
                    }

                }
            }
        }

    }

    public void setBindingMaxIntrestRate(RichOutputText bindingMaxIntrestRate) {
        this.bindingMaxIntrestRate = bindingMaxIntrestRate;
    }

    public RichOutputText getBindingMaxIntrestRate() {
        return bindingMaxIntrestRate;
    }

    public void intrestRateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            if (bindingMaxIntrestRate.getValue() != null) {
                oracle.jbo.domain.Number IntrstRate = (oracle.jbo.domain.Number) object;
                oracle.jbo.domain.Number MaxIntrstRate = (oracle.jbo.domain.Number) bindingMaxIntrestRate.getValue();
                if (IntrstRate.compareTo(0) < 0) {

                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                                                  "IntrestRate can not be in negative"));
                }
                if (intrstRateFlagBinding.getValue().equals("V")) {
                    if (IntrstRate.compareTo(MaxIntrstRate) > 0) {

                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                                                      "IntrestRate should be less than Max IntrestRate(" +
                                                                      MaxIntrstRate + ") Profile Setup"));
                    }
                }

            }
        }
    }

    public void LoanVCL(ValueChangeEvent vce) {
//              System.out.println("VLC");
//               if(loanNMBinding.getValue()!=null)
//               {
//                   
//                   interestRtBinding.setValue("");
//                       AdfFacesContext.getCurrentInstance().addPartialTarget(bindingMaxIntrestRate);
//        
//      
//                }
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

    public void showEMIPopAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("callEMICalculatorFunction").execute();
        showPopup(emiPopPupBinding, true);
    }

    public void setEmiPopPupBinding(RichPopup emiPopPupBinding) {
        this.emiPopPupBinding = emiPopPupBinding;
    }

    public RichPopup getEmiPopPupBinding() {
        return emiPopPupBinding;
    }

    public void loneDuplicateValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            Object bindingsMethod = ADFBeanUtils.callBindingsMethod("loneDuplicateValidationAM", new Object[] {
                                                                    object }, new Object[] { "LoanDocId" });
            if (bindingsMethod != null) {
                if (!bindingsMethod.toString().equalsIgnoreCase("Y")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  bindingsMethod.toString(), null));

                }
            }
        }

    }

    public void loanNameVlc(ValueChangeEvent valueChangeEvent) {
                System.out.println("VLC");
                if(loanNMBinding.getValue()!=null)
                {
                    System.out.println("getBindingMaxIntrestRate().getValue()"+getBindingMaxIntrestRate().getValue());
                    interestRtBinding.setValue(getBindingMaxIntrestRate().getValue());
        
        
                    }
            }
        
   
}
