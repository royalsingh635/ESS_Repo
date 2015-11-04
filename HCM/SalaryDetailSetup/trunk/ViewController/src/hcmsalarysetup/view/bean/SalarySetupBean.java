package hcmsalarysetup.view.bean;

import adf.utils.bean.ADFBeanUtils;

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

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;

import oracle.jbo.domain.Timestamp;

import org.apache.myfaces.trinidad.context.RequestContext;

public class SalarySetupBean {
    private RichInputListOfValues salNmBinding;
    private RichSelectOneChoice salBehavBinding;
    private RichInputDate validFrmBinding;
    private RichInputDate validUptoBind;
    private RichInputDate validFrmDtBinding;
    private RichSelectOneChoice salNameBinding;
    private RichSelectOneChoice salBehaviorBinding;
    private RichSelectOneChoice paidFreqBinding;
    private RichInputText salNotationBinding;
    private String glLink;
    private RichInputText coaIdBind;
    private RichSelectOneChoice salIdBinding;
    private RichSelectOneChoice subSalIdBinding;
    private RichSelectOneChoice subSalBehavBinding;
    private RichInputText subSalNotationBinding;
    private RichSelectOneChoice subSalTypeBinding;
    private RichSelectBooleanCheckbox isOthrDedChk;
    private RichTable subSalTableBinding;
    private RichSelectBooleanCheckbox prntOnSlipBinding;
    private RichSelectBooleanCheckbox isOthrDedChkSrch;
    private String coaMode = "V";

    public void setCoaMode(String coaMode) {
        this.coaMode = coaMode;
    }

    public String getCoaMode() {
        return coaMode;
    }


    public SalarySetupBean() {
    }


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

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public String addSalAction() {
        ADFBeanUtils.getOperationBinding("CreateWithParams").execute();
        ADFBeanUtils.getOperationBinding("executeLovSalIdVoOnCrt").execute();
        RequestContext context = RequestContext.getCurrentInstance();
        context.getPageFlowScope().put("ADD_EDIT_MODE", "A");
        coaMode = "E";
        return null;
    }


    public boolean chkSalSubValidation() {
        String message = "";
        System.out.println(isOthrDedChk.getValue());
        if (!isOthrDedChk.getValue().equals("") && isOthrDedChk.getValue().equals(true)) {
            if (salIdBinding.getValue() == null || salIdBinding.getValue().toString().length() == 0) {
                String cid = salIdBinding.getClientId();
                message=resolvElDCMsg("#{bundle['MSG.2666']}").toString();
               // message = "Please add sub salary component OR unchek is other deduction check box";
                showMessage(message, cid);
                return false;
            }
            if (subSalIdBinding.getValue() == null || subSalIdBinding.getValue().toString().length() == 0) {
                String cid = subSalIdBinding.getClientId();
                //message = "Please select sub salary component";
                message = resolvElDCMsg("#{bundle['MSG.2667']}").toString();;
                showMessage(message, cid);
                return false;
            }
            if (subSalNotationBinding.getValue() == null || subSalNotationBinding.getValue().toString().length() == 0) {
                String cid = subSalNotationBinding.getClientId();
                message =  resolvElDCMsg("#{bundle['MSG.1552']}").toString();
                //message = "Please enter notation";
                showMessage(message, cid);
                return false;
            }
        }

        return true;
    }

    public String showMessage(String message, String cid) {

        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(cid, fm);
        return null;
    }

    public String editSalAction() {
        OperationBinding opCrt = ADFBeanUtils.getOperationBinding("Checksalcomponentlink");
        opCrt.execute();
        String pass = (String) opCrt.getResult();
        if (pass.equals("Y")) {
           // FacesMessage message = new FacesMessage("Salary Component Can't be edited due to its further linking.");
            FacesMessage message = new FacesMessage( resolvElDCMsg("#{bundle['MSG.2668']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            coaMode = "E";
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.getPageFlowScope().put("ADD_EDIT_MODE", "A");
            coaMode = "E";

        }
        return null;
    }

    public void saveSalAction(ActionEvent actionEvent) {
        DCIteratorBinding salIterator = ADFBeanUtils.findIterator("OrgHcmSalaryIterator");
        if (salIterator.getEstimatedRowCount() == 0 || salNameBinding.getValue() != null) {

            if (salIterator.getEstimatedRowCount() == 0 || salBehaviorBinding.getValue() != null) {
                if (salIterator.getEstimatedRowCount() == 0 || paidFreqBinding.getValue() != null) {
                    if (salIterator.getEstimatedRowCount() == 0 ||
                        salNotationBinding.getValue() != null &&
                        salNotationBinding.getValue().toString().length() > 0) {

                        if (salIterator.getEstimatedRowCount() == 0 ||
                            validFrmDtBinding.getValue() != null &&
                            validFrmDtBinding.getValue().toString().length() > 0) {

                            /* if (salIterator.getEstimatedRowCount() == 0 ||
                                (getGlLink().equals("Y") && coaIdBind.getValue() != null) || getGlLink().equals("N")) { */
                            System.out.println("GOING TO COMMIT");
                            if (chkSalSubValidation()) {
                                OperationBinding op = ADFBeanUtils.getOperationBinding("Commit");
                                op.execute();
                                if (op.getErrors().size() == 0) {
                                    showFacesMessage("MSG.91", "I", true, "F");
                                    RequestContext context = RequestContext.getCurrentInstance();
                                    context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                    coaMode = "V";
                                } else {
                                    System.out.println("Error is=" + op.getErrors());
                                }
                            }
                            /*  } else {
                                System.out.println("result for glcode linkng--->" + getGlLink());
                                String msg2 = resolvElDCMsg("Please Select COA Name !!").toString();
                                FacesMessage message2 = new FacesMessage(msg2);
                                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext.getCurrentInstance().addMessage(null, message2);
                            } */

                        } else {
                            String msg2 = resolvElDCMsg("#{bundle['MSG.1551']}").toString();
                            FacesMessage message2 = new FacesMessage(msg2);
                            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext.getCurrentInstance().addMessage(validFrmDtBinding.getClientId(), message2);
                        }
                    } else {
                        String msg2 = resolvElDCMsg("#{bundle['MSG.1552']}").toString();
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(salNotationBinding.getClientId(), message2);
                    }
                } else {
                    String msg2 = resolvElDCMsg("#{bundle['MSG.1553']}").toString();
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(paidFreqBinding.getClientId(), message2);
                }
            } else {
                String msg2 = resolvElDCMsg("#{bundle['MSG.1554']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(salBehaviorBinding.getClientId(), message2);
            }
        } else {
            String msg2 = resolvElDCMsg("#{bundle['MSG.1555']}").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(salNameBinding.getClientId(), message2);
        }


        //return null;
    }

    public String canclAction() {
        //  DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("OrgHcmSalaryIterator");
        //  Key key = parentIter.getCurrentRow().getKey();
        OperationBinding op = ADFBeanUtils.getOperationBinding("Rollback");
        op.execute();
        //  parentIter.setCurrentRowWithKey(key.toStringFormat(true));
        RequestContext context = RequestContext.getCurrentInstance();
        context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
        coaMode = "V";
        return null;
    }

    public String resetSearchAction() {
        salNmBinding.setValue(null);
        salBehavBinding.setValue(null);
        validFrmBinding.setValue(null);
        validUptoBind.setValue(null);
        isOthrDedChkSrch.setValue(null);
        OperationBinding opresetField = ADFBeanUtils.getOperationBinding("fieldValueReset");
        opresetField.execute();
        OperationBinding opreset = ADFBeanUtils.getOperationBinding("searchSalaryComp");
        opreset.getParamsMap().put("salId", null);
        opreset.getParamsMap().put("behav", null);
        opreset.getParamsMap().put("frmDt", null);
        opreset.getParamsMap().put("toDt", null);
        opreset.getParamsMap().put("isOthrDedChk", "X");
        opreset.execute();

        return null;
    }

    public void setSalNmBinding(RichInputListOfValues salNmBinding) {
        this.salNmBinding = salNmBinding;
    }

    public RichInputListOfValues getSalNmBinding() {
        return salNmBinding;
    }

    public void setSalBehavBinding(RichSelectOneChoice salBehavBinding) {
        this.salBehavBinding = salBehavBinding;
    }

    public RichSelectOneChoice getSalBehavBinding() {
        return salBehavBinding;
    }

    public void setValidFrmBinding(RichInputDate validFrmBinding) {
        this.validFrmBinding = validFrmBinding;
    }

    public RichInputDate getValidFrmBinding() {
        return validFrmBinding;
    }

    public void setValidUptoBind(RichInputDate validUptoBind) {
        this.validUptoBind = validUptoBind;
    }

    public RichInputDate getValidUptoBind() {
        return validUptoBind;
    }

    public void dupliSalNotationValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {

            if (object != null) {
                OperationBinding opchk = ADFBeanUtils.getOperationBinding("chkDuplicateNotation");
                opchk.getParamsMap().put("notation", object);
                opchk.execute();
                String valid = "N";
                if (opchk.getErrors().size() == 0 && opchk.getResult() != null)
                    valid = (String) opchk.getResult();
                if (valid.equals("N"))
                    showFacesMessage("MSG.1556", "E", true, "V");
            }
        }

    }

    public void validityEndDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
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
                        showFacesMessage("MSG.1338", "E", true, "V");
                    }
                }
            }

            OperationBinding opchk = ADFBeanUtils.getOperationBinding("chkEnddate");
            opchk.getParamsMap().put("strtDt", object);
            opchk.execute();
            if (opchk.getErrors().size() == 0 && opchk.getResult().toString().equals("Y")) {

            } else {
                showFacesMessage("MSG.1557", "E", true, "V");
            }


        }

    }

    public void setValidFrmDtBinding(RichInputDate validFrmDtBinding) {
        this.validFrmDtBinding = validFrmDtBinding;
    }

    public RichInputDate getValidFrmDtBinding() {
        return validFrmDtBinding;
    }

    public void setSalNameBinding(RichSelectOneChoice salNameBinding) {
        this.salNameBinding = salNameBinding;
    }

    public RichSelectOneChoice getSalNameBinding() {
        return salNameBinding;
    }

    public void setSalBehaviorBinding(RichSelectOneChoice salBehaviorBinding) {
        this.salBehaviorBinding = salBehaviorBinding;
    }

    public RichSelectOneChoice getSalBehaviorBinding() {
        return salBehaviorBinding;
    }

    public void setPaidFreqBinding(RichSelectOneChoice paidFreqBinding) {
        this.paidFreqBinding = paidFreqBinding;
    }

    public RichSelectOneChoice getPaidFreqBinding() {
        return paidFreqBinding;
    }

    public void setSalNotationBinding(RichInputText salNotationBinding) {
        this.salNotationBinding = salNotationBinding;
    }

    public RichInputText getSalNotationBinding() {
        return salNotationBinding;
    }

    public void toDateSearchValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (validFrmBinding.getValue() != null && validFrmBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = (new Timestamp(validFrmBinding.getValue().toString())).dateValue();
                    endDt = (new Timestamp(object.toString())).dateValue();
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

    public String deleteAction() {
        //check if Component is being used in Employee Sal or not
        OperationBinding opchk = ADFBeanUtils.getOperationBinding("chkSalIdToDel");
        opchk.execute();
        if (opchk.getErrors().size() == 0 && opchk.getResult().toString().equals("Y")) {
            OperationBinding opDel = ADFBeanUtils.getOperationBinding("Delete");
            opDel.execute();
        } else {
            showFacesMessage("MSG.1558", "E", true, "F");
        }
        return null;
    }

    public void validStrtDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            OperationBinding opchk = ADFBeanUtils.getOperationBinding("chkValidStrtdt");
            opchk.getParamsMap().put("strtDt", object);
            opchk.execute();
            if (opchk.getErrors().size() == 0 && opchk.getResult().toString().equals("Y")) {

            } else {
                showFacesMessage("MSG.1559", "E", true, "V");
            }
        }
    }

    public String getGlLink() {

        return ADFBeanUtils.getOperationBinding("isGlLinkined").execute().toString();
    }

    public void setCoaIdBind(RichInputText coaIdBind) {
        this.coaIdBind = coaIdBind;
    }

    public RichInputText getCoaIdBind() {
        return coaIdBind;
    }

    public String deleteSubAction() {
        ADFBeanUtils.getOperationBinding("Delete1").execute();
        return null;
    }

    public void setSalIdBinding(RichSelectOneChoice salIdBinding) {
        this.salIdBinding = salIdBinding;
    }

    public RichSelectOneChoice getSalIdBinding() {
        return salIdBinding;
    }

    public void setSubSalIdBinding(RichSelectOneChoice subSalIdBinding) {
        this.subSalIdBinding = subSalIdBinding;
    }

    public RichSelectOneChoice getSubSalIdBinding() {
        return subSalIdBinding;
    }

    public void setSubSalBehavBinding(RichSelectOneChoice subSalBehavBinding) {
        this.subSalBehavBinding = subSalBehavBinding;
    }

    public RichSelectOneChoice getSubSalBehavBinding() {
        return subSalBehavBinding;
    }

    public void setSubSalNotationBinding(RichInputText subSalNotationBinding) {
        this.subSalNotationBinding = subSalNotationBinding;
    }

    public RichInputText getSubSalNotationBinding() {
        return subSalNotationBinding;
    }

    public void setSubSalTypeBinding(RichSelectOneChoice subSalTypeBinding) {
        this.subSalTypeBinding = subSalTypeBinding;
    }

    public RichSelectOneChoice getSubSalTypeBinding() {
        return subSalTypeBinding;
    }

    public void setIsOthrDedChk(RichSelectBooleanCheckbox isOthrDedChk) {
        this.isOthrDedChk = isOthrDedChk;
    }

    public RichSelectBooleanCheckbox getIsOthrDedChk() {
        return isOthrDedChk;
    }

    public void subSalCompValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            OperationBinding opchk = ADFBeanUtils.getOperationBinding("chkSubSalCompAction");
            opchk.getParamsMap().put("subSalId", object.toString());
            opchk.execute();
            if (opchk.getErrors().isEmpty()) {
                if (opchk.getResult().toString().equals("N")) {
                    showFacesMessage("Duplicate Sub salary component.", "E", false, "V");
                }
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(subSalTableBinding);
        }
        // Add event code here...
    }

    public void addSubSal(ActionEvent actionEvent) {
        DCIteratorBinding subDC = ADFBeanUtils.findIterator("OrgHcmSalSubIterator");
        if (subDC.getEstimatedRowCount() > 0) {
            if (chkSalSubValidation()) {
                ADFBeanUtils.getOperationBinding("setDefltValuse").execute();
                ADFBeanUtils.getOperationBinding("executeLovSalIdVoOnCrt").execute();
            }
        } else {
            ADFBeanUtils.getOperationBinding("setDefltValuse").execute();
            ADFBeanUtils.getOperationBinding("executeLovSalIdVoOnCrt").execute();
        }
    }

    public void setSubSalTableBinding(RichTable subSalTableBinding) {
        this.subSalTableBinding = subSalTableBinding;
    }

    public RichTable getSubSalTableBinding() {
        return subSalTableBinding;
    }

    public void othrDedChkBinding(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            prntOnSlipBinding.setValue(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(prntOnSlipBinding);
        }
    }

    public void setPrntOnSlipBinding(RichSelectBooleanCheckbox prntOnSlipBinding) {
        this.prntOnSlipBinding = prntOnSlipBinding;
    }

    public RichSelectBooleanCheckbox getPrntOnSlipBinding() {
        return prntOnSlipBinding;
    }

    public void setIsOthrDedChkSrch(RichSelectBooleanCheckbox isOthrDedChkSrch) {
        this.isOthrDedChkSrch = isOthrDedChkSrch;
    }

    public RichSelectBooleanCheckbox getIsOthrDedChkSrch() {
        return isOthrDedChkSrch;
    }

    public void seqNumberValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding binding = ADFBeanUtils.getOperationBinding("chkDuplicateSeqNo");
            binding.getParamsMap().put("seqNo", object);
            binding.execute();
            if (binding.getErrors().isEmpty() && binding.getResult() != null) {
                String rslt = binding.getResult().toString();
                if (rslt.equalsIgnoreCase("Y")) {
                    String msg = resolvElDCMsg("#{bundle['MSG.2669']}").toString();
                    //String msg = "Duplicate sequence no. not allowed!";
                    showFacesMessage(msg, "E", false, "V");
                }
            }
        }
    }

    public void lagacyCodevalidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("object=="+object);
        if (object != null) {
                    Object bindingsMethod = ADFBeanUtils.callBindingsMethod("validateLegacyCodeId", new Object[] { object }, new Object[] {
                                                                            "legacyCodeId" });
                    if (bindingsMethod != null) {
                        if (!bindingsMethod.toString().equalsIgnoreCase("Y")) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          bindingsMethod.toString(), null));

                        }
                    }
                }

    }
}
