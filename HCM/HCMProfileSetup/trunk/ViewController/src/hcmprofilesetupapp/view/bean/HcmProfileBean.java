package hcmprofilesetupapp.view.bean;


import adf.utils.bean.ADFBeanUtils;
import adf.utils.ebiz.EbizParams;

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

import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.igf.ids.Org;

import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class HcmProfileBean
{
    private String mode = "V";
    private RichSelectOneChoice deptSalProcFreqBinding;
    private RichSelectOneChoice locSalProcFreqBinding;
    private RichInputText deptFreqDayBinding;
    private RichInputText locFreqDaysBinding;
    private RichSelectOneChoice holAppBinding;
    private RichInputText eomstNmBinding;
    private RichSelectOneChoice vouPostTypeBinding;
    private RichSelectBooleanCheckbox leaveCoaBinding;
    private RichInputListOfValues cashAcountBinding;
    private RichInputListOfValues bankAcountBinding;
    private RichInputListOfValues debitAcountBinding;
    private RichInputListOfValues netPaybleAcountBinding;
    private RichSelectOneChoice extTimeRuleRateTypeBinding;
    private RichInputText probationPeriodBinding;
    private RichInputText empNoticeBinding;
    private RichSelectOneChoice empTypeBinding;
    private RichSelectBooleanCheckbox empNoticePrdCHkBinding;
    private RichPopup empNotcePrdPopBinding;
    private RichInputText ltaSttlmntPrdBiinding;
    private RichInputText travAlloBinding;
    private RichSelectBooleanCheckbox extraTimeRuleChkBinding;
    private RichSelectBooleanCheckbox glLingingBinding;
    private RichInputListOfValues lwfAccountbinding;
    private RichInputListOfValues leaveEncaseBinding;
    private RichSelectBooleanCheckbox lwfBinding;


    public void setMode(String mode)
    {
        this.mode = mode;
    }

    public String getMode()
    {
        return mode;
    }

    public HcmProfileBean()
    {
    }

    public BindingContainer getbinding()
    {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String resolvEl(String data)
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
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

    public void createPrfAL(ActionEvent actionEvent)
    {
        Object sloc = EbizParams.GLBL_APP_SERV_LOC();
        Object User = EbizParams.GLBL_APP_USR();
        Object Cld = EbizParams.GLBL_APP_CLD_ID();
        Object Org = EbizParams.GLBL_APP_USR_ORG();
        //Object sloc = resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}");
        //Object User = resolvEl("#{pageFlowScope.GLBL_APP_USR}");
        // Object Cld = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        //Object Org = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");

        System.out.println("In bean---" + "cld--" + Cld + " Sloc--" + sloc + "  org--" + Org + " User----" + User);
        OperationBinding op = getbinding().getOperationBinding("checkPrf");
        op.getParamsMap().put("sloc", sloc);
        op.getParamsMap().put("Org", Org);
        op.getParamsMap().put("Cld", Cld);
        op.getParamsMap().put("User", User);
        op.execute();
        if (op.getResult() != null && op.getErrors().isEmpty())
        {
            Integer result = Integer.parseInt(op.getResult().toString());

            if (result == 1)
            {
                //this.extTimeRuleRateTypeBinding.setValue("P");
                AdfFacesContext.getCurrentInstance().addPartialTarget(extTimeRuleRateTypeBinding);
                this.mode = "A";
            }
        }
    }

    public void editPrfAL(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("refreshVo").execute();
        //getbinding().getOperationBinding("refreshVo").execute();
        this.mode = "E";

    }


    public void savePrfAL(ActionEvent actionEvent)
    {
        boolean validationStatus = chkglValidation();
        if (validationStatus)
        {
            validationStatus = chkglValidation();
            if (validationStatus)
            {
                if (mode == "A")
                {
                    OperationBinding binding = ADFBeanUtils.getOperationBinding("chkUniqNessOfGrpNm");
                    binding.execute();
                    if (binding.getErrors().isEmpty() && binding.getResult() != null)
                    {
                        String rslt = binding.getResult().toString();
                        if (rslt.equalsIgnoreCase("Y"))
                        {
                            OperationBinding opGen = ADFBeanUtils.getOperationBinding("generateEoMstIdIfPrfFreezed");
                            opGen.execute();
                            if (opGen.getErrors().size() == 0)
                            {
                                OperationBinding op = ADFBeanUtils.getOperationBinding("Commit");
                                op.execute();
                                ADFBeanUtils.getOperationBinding("refreshVo").execute();
                                FacesMessage message =
                                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.1526']}").toString());
                                message.setSeverity(FacesMessage.SEVERITY_INFO);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                                this.mode = "V";
                            }
                            else
                            {
                                FacesMessage message =
                                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.1528']}").toString());
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(holAppBinding.getClientId(), message);
                            }
                        }
                        else
                        {
                            FacesMessage message = new FacesMessage("Employee Entity Group Name Is Not Unique!!");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }
                    }
                    else
                    {
                        FacesMessage message =
                            new FacesMessage("Something went wrong in checking uniqness of groupName!contact ESS!");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                }
                else if (mode == "E")
                {
                    OperationBinding opGen = ADFBeanUtils.getOperationBinding("generateEoMstIdIfPrfFreezed");
                    opGen.execute();
                    OperationBinding binding = ADFBeanUtils.getOperationBinding("chkuniqueWithIdOfGrp");
                    binding.execute();
                    if (binding.getErrors().isEmpty() && binding.getResult() != null)
                    {
                        String rslt = binding.getResult().toString();
                        if (rslt.equalsIgnoreCase("N"))
                        {
                            FacesMessage message = new FacesMessage("Employee Entity Group Name Is Not Unique!!");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }
                        else
                        {
                            OperationBinding op = ADFBeanUtils.getOperationBinding("Commit");
                            op.execute();
                            ADFBeanUtils.getOperationBinding("refreshVo").execute();
                            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1526']}").toString());
                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                            this.mode = "V";
                        }
                    }
                }
            }

        }
        else
        {
            String message = "Please select all account for GL Code Linking";
            showMessage(message, null);
        }
    }


    public boolean chkALLValidation()
    {
        if (deptSalProcFreqBinding.getValue() != null && deptSalProcFreqBinding.getValue().toString().length() > 0)
        {
            if (locSalProcFreqBinding.getValue() != null && locSalProcFreqBinding.getValue().toString().length() > 0)
            {
                if (holAppBinding.getValue() != null && holAppBinding.getValue().toString().length() > 0)
                {
                    if (eomstNmBinding.getValue() != null && eomstNmBinding.getValue().toString().trim().length() > 0)
                    {
                        if (ltaSttlmntPrdBiinding.getValue() != null &&
                            ltaSttlmntPrdBiinding.getValue().toString().trim().length() > 0)
                        {
                            if (extraTimeRuleChkBinding.getValue() != null &&
                                extraTimeRuleChkBinding.getValue().toString().trim().length() > 0)
                            {
                                String extraTimeRuleChk = extraTimeRuleChkBinding.getValue().toString();
                                if (extraTimeRuleChk.equals("true"))
                                {

                                    if (extTimeRuleRateTypeBinding.getValue() != null &&
                                        extTimeRuleRateTypeBinding.getValue().toString().trim().length() > 0)
                                    {
                                        if (travAlloBinding.getValue() != null &&
                                            travAlloBinding.getValue().toString().trim().length() > 0)
                                        {
                                            if (empNoticePrdCHkBinding.getValue() != null &&
                                                empNoticePrdCHkBinding.getValue().toString().trim().length() > 0)
                                            {
                                                String noticePrd = empNoticePrdCHkBinding.getValue().toString();
                                                if (noticePrd.equals("true"))
                                                {
                                                    DCIteratorBinding di =
                                                        ADFBeanUtils.findIterator("OrgHcmEmpNoticeDtl1Iterator");
                                                    if (di.getEstimatedRowCount() > 0)
                                                    {

                                                    }
                                                    else
                                                    {
                                                        FacesMessage message =
                                                            new FacesMessage("Unchek this or add employee notice period and probation period");
                                                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                        FacesContext fc = FacesContext.getCurrentInstance();
                                                        fc.addMessage(empNoticePrdCHkBinding.getClientId(), message);
                                                        return false;
                                                    }
                                                }
                                            }

                                        }
                                        else
                                        {
                                            FacesMessage message =
                                                new FacesMessage("Please enter travel allowence settelment period.");
                                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                            FacesContext fc = FacesContext.getCurrentInstance();
                                            fc.addMessage(travAlloBinding.getClientId(), message);
                                            return false;
                                        }
                                    }
                                    else
                                    {
                                        FacesMessage message = new FacesMessage("Please select extra time rule type.");
                                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(extTimeRuleRateTypeBinding.getClientId(), message);
                                        return false;

                                    }

                                }
                            }

                        }
                        else
                        {
                            FacesMessage message =
                                new FacesMessage("Please enter leave travel allowence settelment period.");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(ltaSttlmntPrdBiinding.getClientId(), message);
                            return false;
                        }

                    }
                    else
                    {

                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1529']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(eomstNmBinding.getClientId(), message);
                        return false;
                    }
                }
                else
                {

                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1530']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(holAppBinding.getClientId(), message);
                    return false;
                }

            }
            else
            {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1531']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(locSalProcFreqBinding.getClientId(), message);
                return false;
            }
        }
        else
        {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1532']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(deptSalProcFreqBinding.getClientId(), message);
            return false;
        }
        return true;

    }

    public boolean chkglValidation()
    {
        String glLnkg = glLingingBinding.getValue().toString();
        if (glLnkg.equals("true"))
        {
            String lwfChk = lwfBinding.getValue().toString();
            if (lwfChk.equals("true"))
            {
                if (lwfAccountbinding.getValue() == null || lwfAccountbinding.getValue() == "")
                {

                    System.out.println("LWF account is empty");
                    return false;
                }
            }
            String leaveCoa = leaveCoaBinding.getValue().toString();
            if (leaveCoa.equals("true"))
            {
                if (leaveEncaseBinding.getValue() == null || leaveEncaseBinding.getValue() == "")
                {
                    System.out.println("LeaveEncashAccount is empty");
                    return false;
                }
            }

            if (cashAcountBinding.getValue() == null || cashAcountBinding.getValue() == "")
            {
                System.out.println("CashAccount is empty");
                return false;
            }
            if (bankAcountBinding.getValue() == null || bankAcountBinding.getValue() == "")
            {
                System.out.println("BankAccount is empty");
                return false;
            }
            if (debitAcountBinding.getValue() == null || debitAcountBinding.getValue() == "")
            {
                System.out.println("DebitAccount is empty");
                return false;
            }
            if (netPaybleAcountBinding.getValue() == null || netPaybleAcountBinding.getValue() == "")
            {
                System.out.println("NetAccount is empty");
                return false;
            }
        }
        return true;
    }

    public void cancelPrfAL(ActionEvent actionEvent)
    {
        OperationBinding op = ADFBeanUtils.getOperationBinding("Rollback");
        //OperationBinding op = getbinding().getOperationBinding("Rollback");
        op.execute();
        this.mode = "V";
    }


    public void setDeptSalProcFreqBinding(RichSelectOneChoice deptSalProcFreqBinding)
    {
        this.deptSalProcFreqBinding = deptSalProcFreqBinding;
    }

    public RichSelectOneChoice getDeptSalProcFreqBinding()
    {
        return deptSalProcFreqBinding;
    }

    public void setLocSalProcFreqBinding(RichSelectOneChoice locSalProcFreqBinding)
    {
        this.locSalProcFreqBinding = locSalProcFreqBinding;
    }

    public RichSelectOneChoice getLocSalProcFreqBinding()
    {
        return locSalProcFreqBinding;
    }

    public void setDeptFreqDayBinding(RichInputText deptFreqDayBinding)
    {
        this.deptFreqDayBinding = deptFreqDayBinding;
    }

    public RichInputText getDeptFreqDayBinding()
    {
        return deptFreqDayBinding;
    }

    public void setLocFreqDaysBinding(RichInputText locFreqDaysBinding)
    {
        this.locFreqDaysBinding = locFreqDaysBinding;
    }

    public RichInputText getLocFreqDaysBinding()
    {
        return locFreqDaysBinding;
    }

    public void setHolAppBinding(RichSelectOneChoice holAppBinding)
    {
        this.holAppBinding = holAppBinding;
    }

    public RichSelectOneChoice getHolAppBinding()
    {
        return holAppBinding;
    }

    public void deptFreqValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (deptSalProcFreqBinding.getValue() != null && deptSalProcFreqBinding.getValue().toString().length() > 0 &&
            deptSalProcFreqBinding.getValue().toString().equals("9"))
        {
            if (object != null && object.toString().length() > 0)
            {
                if ((new Integer(object.toString())).compareTo(new Integer(0)) > 0)
                {
                    /* try {
                 if ((new Number(object)).compareTo(new Number(0)) < 0) {
                     showFacesMessage("Days Should be Greater than Zero", "E", false, "V");
                 }
             } catch (SQLException e) {
                 System.out.println("error on cast");
             } */
                }
                else
                {
                    //  FacesMessage message = new FacesMessage("Days should be Greater than Zero.");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1532']}").toString());
                    throw new ValidatorException(message);
                }

            }
            else
            {
                // FacesMessage message = new FacesMessage("Please Enter Salary Processing Frequency Days");
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1534']}").toString());
                throw new ValidatorException(message);
            }
        }
    }


    public void locFreqValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (locSalProcFreqBinding.getValue() != null && locSalProcFreqBinding.getValue().toString().length() > 0 &&
            locSalProcFreqBinding.getValue().toString().equals("9"))
        {
            if (object != null && object.toString().length() > 0)
            {
                if ((new Integer(object.toString())).compareTo(new Integer(0)) > 0)
                {
                    /* try {
                    if ((new Number(object)).compareTo(new Number(0)) < 0) {
                        showFacesMessage("Days Should be Greater than Zero", "E", false, "V");
                    }
                } catch (SQLException e) {
                    System.out.println("error on cast");
                } */
                }
                else
                {
                    //FacesMessage message = new FacesMessage("Days should be Greater than Zero.");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1532']}").toString());
                    throw new ValidatorException(message);
                }
            }
            else
            {
                //FacesMessage message = new FacesMessage("Please Enter Salary Processing Frequency Days");
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1534']}").toString());
                throw new ValidatorException(message);
            }
        }
    }


    public void setEomstNmBinding(RichInputText eomstNmBinding)
    {
        this.eomstNmBinding = eomstNmBinding;
    }

    public RichInputText getEomstNmBinding()
    {
        return eomstNmBinding;
    }

    public void glCodeLnkVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null)
        {
            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
            if (valueChangeEvent.getNewValue().equals(false))
            {
                vouPostTypeBinding.setValue(null);
                leaveCoaBinding.setValue(false);
                cashAcountBinding.setValue(null);
                bankAcountBinding.setValue(null);
                debitAcountBinding.setValue(null);
                netPaybleAcountBinding.setValue(null);
            }
            if (valueChangeEvent.getNewValue().equals(true))
            {
                vouPostTypeBinding.setValue(91);
                leaveCoaBinding.setValue(false);

            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(vouPostTypeBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(leaveCoaBinding);
    }

    public void setVouPostTypeBinding(RichSelectOneChoice vouPostTypeBinding)
    {
        this.vouPostTypeBinding = vouPostTypeBinding;
    }

    public RichSelectOneChoice getVouPostTypeBinding()
    {
        return vouPostTypeBinding;
    }

    public void setLeaveCoaBinding(RichSelectBooleanCheckbox leaveCoaBinding)
    {
        this.leaveCoaBinding = leaveCoaBinding;
    }

    public RichSelectBooleanCheckbox getLeaveCoaBinding()
    {
        return leaveCoaBinding;
    }

    public void setCashAcountBinding(RichInputListOfValues cashAcountBinding)
    {
        this.cashAcountBinding = cashAcountBinding;
    }

    public RichInputListOfValues getCashAcountBinding()
    {
        return cashAcountBinding;
    }

    public void setBankAcountBinding(RichInputListOfValues bankAcountBinding)
    {
        this.bankAcountBinding = bankAcountBinding;
    }

    public RichInputListOfValues getBankAcountBinding()
    {
        return bankAcountBinding;
    }

    public void setDebitAcountBinding(RichInputListOfValues debitAcountBinding)
    {
        this.debitAcountBinding = debitAcountBinding;
    }

    public RichInputListOfValues getDebitAcountBinding()
    {
        return debitAcountBinding;
    }

    public void setNetPaybleAcountBinding(RichInputListOfValues netPaybleAcountBinding)
    {
        this.netPaybleAcountBinding = netPaybleAcountBinding;
    }

    public RichInputListOfValues getNetPaybleAcountBinding()
    {
        return netPaybleAcountBinding;
    }

    public void glCodeLinkChkValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null)
        {
            if (object.equals(false))
            {
                OperationBinding ob = getbinding().getOperationBinding("chkGlCode");
                ob.execute();
                if (ob.getResult() != null)
                {
                    String rslt = ob.getResult().toString();
                    if (rslt.equalsIgnoreCase("Y"))
                    {
                        String msg = "Can not uncheck this, Chart of accounts already linked!";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                    }
                }

            }
        }
    }

    public void setExtTimeRuleRateTypeBinding(RichSelectOneChoice extTimeRuleRateTypeBinding)
    {
        this.extTimeRuleRateTypeBinding = extTimeRuleRateTypeBinding;
    }

    public RichSelectOneChoice getExtTimeRuleRateTypeBinding()
    {
        return extTimeRuleRateTypeBinding;
    }

    public void addEmployeesNoticeAL(ActionEvent actionEvent)
    {
        ADFBeanUtils.callBindingsMethod("CreateInsert1", null, null);
        RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_ADD_EDIT", "A");
    }

    public void probationPeriodValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        // Add event code here...
        System.out.println("object==" + object);
        if (object != null)
        {
            if (this.getProbationPeriodBinding().getValue() != null)
            {
                Integer Zero = 0;
                Integer ProbationValue = (Integer) object;
                Integer Val = ProbationValue.compareTo(Zero);
                if (Val <= 0)
                {
                    String err = "";
                    System.out.println("val=" + Val);
                    //ADFModelUtils.showFacesMessage("Invalid probation peroid ", "Value Must be greater than 0", FacesMessage.SEVERITY_ERROR, probationPeriodBinding.getClientId());
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, err,
                                                                  "Value Must be greater than 0"));
                }
            }
        }

    }

    public void ltaSettPeriodValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null)
        {
            if (object != null)
            {
                Integer Zero = 0;
                Integer ProbationValue = (Integer) object;
                Integer Val = ProbationValue.compareTo(Zero);
                if (Val < 0)
                {
                    String err = "";
                    //ADFModelUtils.showFacesMessage("Invalid Notice peroid ", "Value Must be greater than 0", FacesMessage.SEVERITY_ERROR, empNoticeBinding.getClientId());
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, err,
                                                                  "Value should not be in negative"));
                }
            }
        }

    }

    public void taSettPeriodValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null)
        {
            if (object != null)
            {
                Integer Zero = 0;
                Integer ProbationValue = (Integer) object;
                Integer Val = ProbationValue.compareTo(Zero);
                if (Val < 0)
                {
                    String err = "";
                    //ADFModelUtils.showFacesMessage("Invalid Notice peroid ", "Value Must be greater than 0", FacesMessage.SEVERITY_ERROR, empNoticeBinding.getClientId());
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, err,
                                                                  "Value should not be in negative"));
                }
            }
        }

    }

    public void leaveEncashValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null)
        {
            if (object != null)
            {
                Integer Zero = 0;
                Integer ProbationValue = (Integer) object;
                Integer Val = ProbationValue.compareTo(Zero);
                if (Val < 0)
                {
                    String err = "";
                    //ADFModelUtils.showFacesMessage("Invalid Notice peroid ", "Value Must be greater than 0", FacesMessage.SEVERITY_ERROR, empNoticeBinding.getClientId());
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, err,
                                                                  "Value should not be in negative"));
                }
            }
        }

    }

    public void noticePeriodValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null)
        {
            if (this.getEmpNoticeBinding().getValue() != null)
            {
                Integer Zero = 0;
                Integer ProbationValue = (Integer) object;
                Integer Val = ProbationValue.compareTo(Zero);
                if (Val <= 0)
                {
                    String err = "";
                    //ADFModelUtils.showFacesMessage("Invalid Notice peroid ", "Value Must be greater than 0", FacesMessage.SEVERITY_ERROR, empNoticeBinding.getClientId());
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, err,
                                                                  "Value Must be greater than 0"));
                }
            }
        }

    }

    public void setProbationPeriodBinding(RichInputText probationPeriodBinding)
    {
        this.probationPeriodBinding = probationPeriodBinding;
    }

    public RichInputText getProbationPeriodBinding()
    {
        return probationPeriodBinding;
    }

    public void setEmpNoticeBinding(RichInputText empNoticeBinding)
    {
        this.empNoticeBinding = empNoticeBinding;
    }

    public RichInputText getEmpNoticeBinding()
    {
        return empNoticeBinding;
    }

    public boolean ValidationallField()
    {
        if (getEmpTypeBinding().getValue() == 123)
        {
            if (getProbationPeriodBinding().getValue() == null || getProbationPeriodBinding().getValue() == "")
            {
                String msg = " Enter Probation period ";
                ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_ERROR,
                                               getProbationPeriodBinding().getClientId());
                return false;
            }
        }
        if (getEmpNoticeBinding().getValue() == null || getEmpNoticeBinding().getValue() == "")
        {
            String msg = " Enter Notice Period ";
            ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_ERROR, getEmpNoticeBinding().getClientId());
            return false;
        }
        return true;
    }

    public void setEmpTypeBinding(RichSelectOneChoice empTypeBinding)
    {
        this.empTypeBinding = empTypeBinding;
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

    public void openNoticePopuPdAL(ActionEvent actionEvent)
    {
        showPopup(empNotcePrdPopBinding, true);
    }

    public void closeNotcePop(ActionEvent actionEvent)
    {

        boolean result = chkSveEmpNotce();
        if (result)
        {
            empNotcePrdPopBinding.hide();
        }

    }

    public boolean chkSveEmpNotce()
    {
        return true;
    }

    public RichSelectOneChoice getEmpTypeBinding()
    {
        return empTypeBinding;
    }

    public void deleteEmpTypeAL(ActionEvent actionEvent)
    {
        ADFBeanUtils.callBindingsMethod("Delete", null, null);
    }

    public void checkEmpTypeAL(ActionEvent actionEvent)
    {
        boolean check = ValidationallField();

        if (check)
        {

            RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_ADD_EDIT", "E");
        }
    }

    public void setEmpNoticePrdCHkBinding(RichSelectBooleanCheckbox empNoticePrdCHkBinding)
    {
        this.empNoticePrdCHkBinding = empNoticePrdCHkBinding;
    }

    public RichSelectBooleanCheckbox getEmpNoticePrdCHkBinding()
    {
        return empNoticePrdCHkBinding;
    }

    public void setEmpNotcePrdPopBinding(RichPopup empNotcePrdPopBinding)
    {
        this.empNotcePrdPopBinding = empNotcePrdPopBinding;
    }

    public RichPopup getEmpNotcePrdPopBinding()
    {
        return empNotcePrdPopBinding;
    }

    public void setLtaSttlmntPrdBiinding(RichInputText ltaSttlmntPrdBiinding)
    {
        this.ltaSttlmntPrdBiinding = ltaSttlmntPrdBiinding;
    }

    public RichInputText getLtaSttlmntPrdBiinding()
    {
        return ltaSttlmntPrdBiinding;
    }

    public void setTravAlloBinding(RichInputText travAlloBinding)
    {
        this.travAlloBinding = travAlloBinding;
    }

    public RichInputText getTravAlloBinding()
    {
        return travAlloBinding;
    }

    public void setExtraTimeRuleChkBinding(RichSelectBooleanCheckbox extraTimeRuleChkBinding)
    {
        this.extraTimeRuleChkBinding = extraTimeRuleChkBinding;
    }

    public RichSelectBooleanCheckbox getExtraTimeRuleChkBinding()
    {
        return extraTimeRuleChkBinding;
    }

    public void setGlLingingBinding(RichSelectBooleanCheckbox glLingingBinding)
    {
        this.glLingingBinding = glLingingBinding;
    }

    public RichSelectBooleanCheckbox getGlLingingBinding()
    {
        return glLingingBinding;
    }

    public void setLwfAccountbinding(RichInputListOfValues lwfAccountbinding)
    {
        this.lwfAccountbinding = lwfAccountbinding;
    }

    public RichInputListOfValues getLwfAccountbinding()
    {
        return lwfAccountbinding;
    }

    public void setLeaveEncaseBinding(RichInputListOfValues leaveEncaseBinding)
    {
        this.leaveEncaseBinding = leaveEncaseBinding;
    }

    public RichInputListOfValues getLeaveEncaseBinding()
    {
        return leaveEncaseBinding;
    }

    public String showMessage(String message, String cid)
    {
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(cid, fm);
        return null;
    }

    public void setLwfBinding(RichSelectBooleanCheckbox lwfBinding)
    {
        this.lwfBinding = lwfBinding;
    }

    public RichSelectBooleanCheckbox getLwfBinding()
    {
        return lwfBinding;
    }
}
