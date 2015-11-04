package finauthoritypayapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import java.sql.SQLException;

import java.util.Map;

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
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.context.RequestContext;

public class AuthPayBean {
    private RichSelectBooleanCheckbox transSelectChkBoxBind;
    private String mode = "V";
    private String newMode = "V";
    private RichPanelCollection panelColBinding;
    private RichInputText crtNoBind;
    private RichInputText transInsNoBind;
    private RichInputText transInsAmtBind;
    private RichSelectOneChoice FYIdBinding;
    private RichInputDate transPrdStDtBind;
    private RichInputDate transPrdEndDtBind;
    private RichSelectOneChoice authTypBind;
    private RichSelectOneChoice PrdBinding;
    private RichSelectOneChoice FrqBinding;
    private RichSelectOneChoice bankCoaBinding;
    private RichInputText docIdBinding;
    private RichInputText nameOnInstrBinding;
    private RichSelectBooleanCheckbox payFlagBinding;
    private RichSelectOneChoice authorityBinding;
    private RichInputDate intrumentDateBinding;
    private RichInputDate voucherDateBinding;
    private RichInputText instrumentNoBinding;
    private RichSelectOneChoice instrumentModeBinding;
    private RichPanelGroupLayout docIdBind;
    private RichPopup otherChargesPopUpBind;
   

  

    public void setNewMode(String newMode) {
        this.newMode = newMode;
    }

    public String getNewMode() {
        return newMode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public AuthPayBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void autorityVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding ob = getBindings().getOperationBinding("setNameOnInstr");
            ob.getParamsMap().put("eoId", authorityBinding.getValue());
            ob.execute();
            System.out.println("instrumen name is----" + ob.getResult());
            if (ob.getErrors().isEmpty() && ob.getResult() != null) {
                String nm = ob.getResult().toString();
                nameOnInstrBinding.setValue(nm);
                AdfFacesContext.getCurrentInstance().addPartialTarget(nameOnInstrBinding);
            }

        }
    }


    public void setTransSelectChkBoxBind(RichSelectBooleanCheckbox transSelectChkBoxBind) {
        this.transSelectChkBoxBind = transSelectChkBoxBind;
    }

    public RichSelectBooleanCheckbox getTransSelectChkBoxBind() {
        return transSelectChkBoxBind;
    }

    public void setPanelColBinding(RichPanelCollection panelColBinding) {
        this.panelColBinding = panelColBinding;
    }

    public RichPanelCollection getPanelColBinding() {
        return panelColBinding;
    }

    public void authPaymentSaveActionListener(ActionEvent actionEvent) {
        boolean rslt = chkForNullAttributes();
        if (rslt) {
            ADFBeanUtils.callBindingsMethod("executeTaxIdQuery", null, null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(docIdBind);
            if (transInsAmtBind.getValue() == null || transInsAmtBind.getValue() == "" ||
                transInsAmtBind.getValue() ==
                0) {
                // String cid = instrumentModeBinding.getClientId();
                String message =
                    "Instrument Amount can not be left blank and can not be equal to Zero! Please Click on Refresh Link";
                showMessage(message, null);

            } else {
                System.out.println("mode value at save is ---->" + resolvEl("#{pageFlowScope.ADD_EDIT_MODE}"));
                if (resolvEl("#{pageFlowScope.ADD_EDIT_MODE}").equalsIgnoreCase("A")) {
                    System.out.println("mode value at save is ---->" + resolvEl("#{pageFlowScope.ADD_EDIT_MODE}"));
                    //  getBindings().getOperationBinding("genRateDispDocId").execute();
                }
                System.out.println("on save click----");
                getBindings().getOperationBinding("Commit").execute();
                FacesMessage msg = new FacesMessage("Record saved successfully.");
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, msg);
                RequestContext.getCurrentInstance().getPageFlowScope().put("ADD_EDIT_MODE", "V");

                String action = "I";
                String remark = "A";
                String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
                Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
                Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());

                OperationBinding WfnoOp = ADFBeanUtils.getOperationBinding("getWfNo");
                WfnoOp.getParamsMap().put("sloc_id", sloc_id);
                WfnoOp.getParamsMap().put("cld_id", cld_id);
                WfnoOp.getParamsMap().put("org_id", pOrgId);
                WfnoOp.getParamsMap().put("doc_no", 13504);
                WfnoOp.execute();

                String WfNum = null;
                Integer level = 0;
                Integer res = -1;

                if (WfnoOp.getResult() != null) {
                    WfNum = WfnoOp.getResult().toString();
                    //if (WfNum != null) {
                    OperationBinding usrLevelOp = ADFBeanUtils.getOperationBinding("getUsrLvl");
                    usrLevelOp.getParamsMap().put("SlocId", sloc_id);
                    usrLevelOp.getParamsMap().put("CldId", cld_id);
                    usrLevelOp.getParamsMap().put("OrgId", pOrgId);
                    usrLevelOp.getParamsMap().put("usr_id", usr_id);
                    usrLevelOp.getParamsMap().put("WfNum", WfNum);
                    usrLevelOp.getParamsMap().put("DocNo", 13504);
                    usrLevelOp.execute();
                    level = -1;
                    if (usrLevelOp.getResult() != null) {
                        if (usrLevelOp.getResult() != null)
                            level = Integer.parseInt(usrLevelOp.getResult().toString());
                    }
                    if (level == -1) {
                        FacesMessage mssg =
                            new FacesMessage("Something went wrong while getting Workflow Level of this user.Contact to ESS.");
                        mssg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext f = FacesContext.getCurrentInstance();
                        f.addMessage(null, mssg);
                    } else {
                        OperationBinding insTxnOp = ADFBeanUtils.getOperationBinding("insIntoTxn");
                        insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                        insTxnOp.getParamsMap().put("cld_id", cld_id);
                        insTxnOp.getParamsMap().put("pOrgId", pOrgId);
                        insTxnOp.getParamsMap().put("DOC_NO", 13504);
                        insTxnOp.getParamsMap().put("WfNum", WfNum);
                        insTxnOp.getParamsMap().put("poDocId", null);
                        insTxnOp.getParamsMap().put("usr_idFrm", usr_id);
                        insTxnOp.getParamsMap().put("usr_idTo", usr_id);
                        insTxnOp.getParamsMap().put("levelFrm", level);
                        insTxnOp.getParamsMap().put("levelTo", level);
                        insTxnOp.getParamsMap().put("action", action);
                        insTxnOp.getParamsMap().put("remark", remark);
                        insTxnOp.getParamsMap().put("amount", 0);
                        insTxnOp.getParamsMap().put("post", "S");
                        insTxnOp.execute();

                        if (insTxnOp.getResult() != null) {
                            res = Integer.parseInt(insTxnOp.getResult().toString());
                        }
                        if (res == 1) {
                            OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("Commit");
                            operationBinding.execute();

                        } else {
                            System.out.println("error during insert to WF");
                        }
                        System.out.println(level + "level--res" + res);
                    }
                } else {
                    FacesMessage message1 = new FacesMessage("Workflow not Defined for this Document.");
                    message1.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc1 = FacesContext.getCurrentInstance();
                    fc1.addMessage(null, message1);
                }
                /* } else {
            insOp.getErrors();
        } */

            }
        }
    }

    public void setCrtNoBind(RichInputText crtNoBind) {
        this.crtNoBind = crtNoBind;
    }

    public RichInputText getCrtNoBind() {
        return crtNoBind;
    }

    public void setTransInsNoBind(RichInputText transInsNoBind) {
        this.transInsNoBind = transInsNoBind;
    }

    public RichInputText getTransInsNoBind() {
        return transInsNoBind;
    }

    public void setTransInsAmtBind(RichInputText transInsAmtBind) {
        this.transInsAmtBind = transInsAmtBind;
    }

    public RichInputText getTransInsAmtBind() {
        return transInsAmtBind;
    }

    public void setFYIdBinding(RichSelectOneChoice FYIdBinding) {
        this.FYIdBinding = FYIdBinding;
    }

    public RichSelectOneChoice getFYIdBinding() {
        return FYIdBinding;
    }

    public void setTransPrdStDtBind(RichInputDate transPrdStDtBind) {
        this.transPrdStDtBind = transPrdStDtBind;
    }

    public RichInputDate getTransPrdStDtBind() {
        return transPrdStDtBind;
    }

    public void setTransPrdEndDtBind(RichInputDate transPrdEndDtBind) {
        this.transPrdEndDtBind = transPrdEndDtBind;
    }

    public RichInputDate getTransPrdEndDtBind() {
        return transPrdEndDtBind;
    }

    public void fyIdChangeListener(ValueChangeEvent valueChangeEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding op = bindings.getOperationBinding("resetValue");
        op.execute();
    }

    public void transFrqIdVCL(ValueChangeEvent valueChangeEvent) {
        PrdBinding.setValue(null);
        authTypBind.setValue(null);
        authTypBind.setValue("");
        authorityBinding.setValue(null);
        authorityBinding.setValue("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(authTypBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(authorityBinding);
    }

    public void setAuthTypBind(RichSelectOneChoice authTypBind) {
        this.authTypBind = authTypBind;
    }

    public RichSelectOneChoice getAuthTypBind() {
        return authTypBind;
    }


    public void setPrdBinding(RichSelectOneChoice PrdBinding) {
        this.PrdBinding = PrdBinding;
    }

    public RichSelectOneChoice getPrdBinding() {
        return PrdBinding;
    }

    public void setFrqBinding(RichSelectOneChoice FrqBinding) {
        this.FrqBinding = FrqBinding;
    }

    public RichSelectOneChoice getFrqBinding() {
        return FrqBinding;
    }

    public void FrqncyVCL(ValueChangeEvent valueChangeEvent) {
        authTypBind.setValue(null);
        authTypBind.setValue("");
        authorityBinding.setValue(null);
        authorityBinding.setValue("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(authTypBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(authorityBinding);

    }

    public void addTaxPayAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("CreateInsert").execute();
        RequestContext.getCurrentInstance().getPageFlowScope().put("ADD_EDIT_MODE", "A");
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

    public void editBtnAL(ActionEvent actionEvent) {
        //check for pending
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        Integer pending = null;

        OperationBinding chkUsr = ADFBeanUtils.getOperationBinding("pendingCheck");
        chkUsr.getParamsMap().put("SlocId", sloc_id);
        chkUsr.getParamsMap().put("CldId", cld_id);
        chkUsr.getParamsMap().put("OrgId", pOrgId);
        chkUsr.getParamsMap().put("DocNo", 13504);
        chkUsr.execute();

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
            System.out.println("Pending at=" + pending);
            if (pending.compareTo(-1) != 0 && pending.compareTo(usr_id) != 0) {
                OperationBinding gtUsrNm = ADFBeanUtils.getOperationBinding("getUsrName");
                gtUsrNm.getParamsMap().put("usrId", pending);
                gtUsrNm.execute();
                StringBuffer usrName = new StringBuffer("[Anonymous]");
                if (gtUsrNm.getResult() != null)
                    usrName = new StringBuffer("[").append(gtUsrNm.getResult().toString()).append("]");
                String msg = "Document is pending at other user [" + usrName + "] for approval, You cannot Edit this.";
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                RequestContext.getCurrentInstance().getPageFlowScope().put("ADD_EDIT_MODE", "E");
                newMode = "E";
            }
        }

    }

    public void setBankCoaBinding(RichSelectOneChoice bankCoaBinding) {
        this.bankCoaBinding = bankCoaBinding;
    }

    public RichSelectOneChoice getBankCoaBinding() {
        return bankCoaBinding;
    }

    public String saveAndFwdAction() {
        boolean rslt = chkForNullAttributes();
        if (rslt) {
            ADFBeanUtils.callBindingsMethod("executeTaxIdQuery", null, null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(docIdBind);
            if (transInsAmtBind.getValue() == null || transInsAmtBind.getValue() == "" ||
                transInsAmtBind.getValue() ==
                0) {
                // String cid = instrumentModeBinding.getClientId();
                String message =
                    "Instrument Amount can not be left blank and can not be equal to Zero! Please Click on Refresh Link";
                showMessage(message, null);

            } else {
                if (resolvEl("#{pageFlowScope.ADD_EDIT_MODE}").equalsIgnoreCase("A")) {
                    System.out.println("mode value at save is ---->" + resolvEl("#{pageFlowScope.ADD_EDIT_MODE}"));
                    // getBindings().getOperationBinding("genRateDispDocId").execute();
                }
                getBindings().getOperationBinding("Commit").execute();
                RequestContext.getCurrentInstance().getPageFlowScope().put("ADD_EDIT_MODE", "V");
                Map paramMap = RequestContext.getCurrentInstance().getPageFlowScope();
                paramMap.put("PARAM_DOC_TXN_ID", docIdBinding.getValue());

                System.out.println("doc_txn id in bean after putting--->" +
                                   resolvEl("#{pageFlowScope.PARAM_DOC_TXN_ID}"));
                //check for pending
                String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
                Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
                Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
                Integer pending = null;

                OperationBinding chkUsr = ADFBeanUtils.getOperationBinding("pendingCheck");
                chkUsr.getParamsMap().put("SlocId", sloc_id);
                chkUsr.getParamsMap().put("CldId", cld_id);
                chkUsr.getParamsMap().put("OrgId", pOrgId);
                chkUsr.getParamsMap().put("DocNo", 13504);
                chkUsr.execute();

                if (chkUsr.getResult() != null) {
                    pending = Integer.parseInt(chkUsr.getResult().toString());
                    System.out.println("pending result in bean---->>" + pending);
                }
                System.out.println("Pending at=" + pending);
                if (pending.compareTo(usr_id) != 0 && pending.compareTo(-1) != 0) {
                    OperationBinding gtUsrNm = ADFBeanUtils.getOperationBinding("getUsrName");
                    gtUsrNm.getParamsMap().put("usrId", pending);
                    gtUsrNm.execute();
                    StringBuffer usrName = new StringBuffer("Anonymous");
                    if (gtUsrNm.getResult() != null)
                        usrName = new StringBuffer("[").append(usrName).append("]");
                    String msg2 =
                        "Salary is pending at other user " + usrName +
                        " for approval, You cannot Forward/Approve this.";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message2);
                } else {

                    String action = "I";
                    String remark = "A";

                    OperationBinding WfnoOp = ADFBeanUtils.getOperationBinding("getWfNo");
                    WfnoOp.getParamsMap().put("sloc_id", sloc_id);
                    WfnoOp.getParamsMap().put("cld_id", cld_id);
                    WfnoOp.getParamsMap().put("org_id", pOrgId);
                    WfnoOp.getParamsMap().put("doc_no", 13504);
                    WfnoOp.execute();

                    String WfNum = null;
                    Integer level = 0;
                    Integer res = -1;

                    if (WfnoOp.getResult() != null) {
                        WfNum = WfnoOp.getResult().toString();
                        //paramMap.put("WF_NUM", WfNum);
                        if (WfNum != null) {
                            OperationBinding usrLevelOp = ADFBeanUtils.getOperationBinding("getUsrLvl");
                            usrLevelOp.getParamsMap().put("SlocId", sloc_id);
                            usrLevelOp.getParamsMap().put("CldId", cld_id);
                            usrLevelOp.getParamsMap().put("OrgId", pOrgId);
                            usrLevelOp.getParamsMap().put("usr_id", usr_id);
                            usrLevelOp.getParamsMap().put("WfNum", WfNum);
                            usrLevelOp.getParamsMap().put("DocNo", 13504);
                            usrLevelOp.execute();

                            level = -1;
                            System.out.println("user level in bean --->" + usrLevelOp.getResult());
                            if (usrLevelOp.getResult() != null) {
                                if (usrLevelOp.getResult() != null)
                                    level = Integer.parseInt(usrLevelOp.getResult().toString());
                            }
                            if (level == -1) {
                                FacesMessage message =
                                    new FacesMessage("Something went wrong while getting Workflow Level of this user.Contact to ESS.");
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            } else {
                                System.out.println("before inserting in INsTxn in BEAN");
                                OperationBinding insTxnOp = ADFBeanUtils.getOperationBinding("insIntoTxn");
                                insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                                insTxnOp.getParamsMap().put("cld_id", cld_id);
                                insTxnOp.getParamsMap().put("pOrgId", pOrgId);
                                insTxnOp.getParamsMap().put("DOC_NO", 13504);
                                insTxnOp.getParamsMap().put("WfNum", WfNum);
                                insTxnOp.getParamsMap().put("poDocId", null);
                                insTxnOp.getParamsMap().put("usr_idFrm", usr_id);
                                insTxnOp.getParamsMap().put("usr_idTo", usr_id);
                                insTxnOp.getParamsMap().put("levelFrm", level);
                                insTxnOp.getParamsMap().put("levelTo", level);
                                insTxnOp.getParamsMap().put("action", action);
                                insTxnOp.getParamsMap().put("remark", remark);
                                insTxnOp.getParamsMap().put("amount", 0);
                                insTxnOp.getParamsMap().put("post", "S");
                                insTxnOp.execute();
                                System.out.println("After  inserting in INsTxn in BEAN");
                                if (insTxnOp.getResult() != null) {
                                    res = Integer.parseInt(insTxnOp.getResult().toString());
                                    System.out.println("result for insTxn in Bean-->" + res);
                                }
                                if (res == 1) {
                                    System.out.println("result is 1 going to work flow--");
                                    OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("Commit");
                                    operationBinding.execute();
                                    return "goToWf";

                                } else {
                                    System.out.println("error during insert to WF");
                                }
                                System.out.println(level + "level--res" + res);
                            }
                        }
                    } else {
                        FacesMessage message = new FacesMessage("Workflow not Defined for this Document.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                }
                return null;
            }
        }

        return null;
    }

    public void setDocIdBinding(RichInputText docIdBinding) {
        this.docIdBinding = docIdBinding;
    }

    public RichInputText getDocIdBinding() {
        return docIdBinding;
    }

    public void setNameOnInstrBinding(RichInputText nameOnInstrBinding) {
        this.nameOnInstrBinding = nameOnInstrBinding;
    }

    public RichInputText getNameOnInstrBinding() {
        return nameOnInstrBinding;
    }

    public void instrNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (newMode != null && newMode.equalsIgnoreCase("A")) {
                System.out.println("new Mode A is--->" + newMode);
                OperationBinding op = getBindings().getOperationBinding("chkDuplicateInstrumentNo");
                op.getParamsMap().put("instrNo", object);
                op.getParamsMap().put("modeVal", newMode);
                op.execute();
                if (op.getErrors().isEmpty() && op.getResult() != null) {
                    String rslt = op.getResult().toString();
                    if (rslt.equalsIgnoreCase("N")) {
                        String msg = "Instrument no. entered by you is duplicate!";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null));
                    }
                }
            }

            else if (newMode != null && newMode.equalsIgnoreCase("E")) {
                System.out.println("new Mode E is--->" + newMode);
                OperationBinding op = getBindings().getOperationBinding("chkDuplicateInstrumentNo");
                op.getParamsMap().put("instrNo", object);
                op.getParamsMap().put("modeVal", newMode);
                op.execute();
                if (op.getErrors().isEmpty() && op.getResult() != null) {
                    String rslt = op.getResult().toString();
                    if (rslt.equalsIgnoreCase("N")) {
                        String msg = "Instrument no. entered by you is duplicate!";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null));
                    }
                }
            }
        }
    }


    public void refreshAmountSumAL(ActionEvent actionEvent) {
        OperationBinding binding = getBindings().getOperationBinding("updateAmountInRegTbl");
        binding.execute();
        if (binding.getErrors().isEmpty() && binding.getResult() != null) {
            transInsAmtBind.setValue(binding.getResult());
            AdfFacesContext.getCurrentInstance().addPartialTarget(transInsAmtBind);
        }
    }

    public void selectAllAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("selectAll").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(payFlagBinding);
    }

    public void deselectAllAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("deSelectAll").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(payFlagBinding);
    }

    public void setPayFlagBinding(RichSelectBooleanCheckbox payFlagBinding) {
        this.payFlagBinding = payFlagBinding;
    }

    public RichSelectBooleanCheckbox getPayFlagBinding() {
        return payFlagBinding;
    }

    public String showMessage(String message, String cid) {

        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(cid, fm);
        return null;
    }


    public boolean chkForNullAttributes() {
        String message = "";
        oracle.jbo.domain.Timestamp curDt = new oracle.jbo.domain.Timestamp(System.currentTimeMillis());
        oracle.jbo.domain.Timestamp prdEndDt = (oracle.jbo.domain.Timestamp) transPrdEndDtBind.getValue();
        oracle.jbo.domain.Timestamp vouDt = (oracle.jbo.domain.Timestamp) voucherDateBinding.getValue();
        if (transInsAmtBind.getValue() != null && ((Number) transInsAmtBind.getValue()).compareTo(new Number(0)) < 0) {
            message = "Instrument amount can not be negative!";
            showMessage(message, null);
            return false;
        }
        if (bankCoaBinding.getValue() == null || bankCoaBinding.getValue() == "") {
            String cid = bankCoaBinding.getClientId();
            message = "Bank can not be left blank!";
            showMessage(message, cid);
            return false;
        }
        if (FYIdBinding.getValue() == null || FYIdBinding.getValue() == "") {
            String cid = FYIdBinding.getClientId();
            message = "Financial year can not be left blank!";
            showMessage(message, cid);
            return false;
        }
        if (FrqBinding.getValue() == null || FrqBinding.getValue() == "") {
            String cid = FrqBinding.getClientId();
            message = "Frequency can not be left blank!";
            showMessage(message, cid);
            return false;
        }
        if (PrdBinding.getValue() == null || PrdBinding.getValue() == "") {
            String cid = PrdBinding.getClientId();
            message = "Period can not be left blank!";
            showMessage(message, cid);
            return false;
        }
        if (transPrdStDtBind.getValue() == null || transPrdStDtBind.getValue() == "") {
            String cid = transPrdStDtBind.getClientId();
            message = "Start date can not be left blank!";
            showMessage(message, cid);
            return false;
        }
        if (transPrdEndDtBind.getValue() == null || transPrdEndDtBind.getValue() == "") {
            String cid = transPrdEndDtBind.getClientId();
            message = "End date can not be left blank!";
            showMessage(message, cid);
            return false;
        }
        if (authTypBind.getValue() == null || authTypBind.getValue() == "") {
            String cid = authTypBind.getClientId();
            message = "Authority type can not be left blank!";
            showMessage(message, cid);
            return false;
        }

        if (authorityBinding.getValue() == null || authorityBinding.getValue() == "") {
            String cid = authorityBinding.getClientId();
            message = "Authority can not be left blank!";
            showMessage(message, cid);
            return false;
        }

        if (crtNoBind.getValue() == null || crtNoBind.getValue() == "") {
            String cid = crtNoBind.getClientId();
            message = "Certificate No. can not be left blank!";
            showMessage(message, cid);
            return false;
        }

        if (instrumentNoBinding.getValue() == null || instrumentNoBinding.getValue() == "") {
            String cid = instrumentNoBinding.getClientId();
            message = "Instrument No. can not be left blank!";
            showMessage(message, cid);
            return false;
        }
        if (intrumentDateBinding.getValue() == null || intrumentDateBinding.getValue() == "") {
            String cid = intrumentDateBinding.getClientId();
            message = "Instrument date can not be left blank!";
            showMessage(message, cid);
            return false;
        }

        if (voucherDateBinding.getValue() == null || voucherDateBinding.getValue() == "") {
            String cid = voucherDateBinding.getClientId();
            message = "Voucher date can not be left blank!";
            showMessage(message, cid);
            return false;
        }
        if (instrumentModeBinding.getValue() == null || instrumentModeBinding.getValue() == "") {
            String cid = instrumentModeBinding.getClientId();
            message = "Instrument Mode can not be left blank!";
            showMessage(message, cid);
            return false;
        }
        try {
            if (vouDt.dateValue().toString().compareTo(curDt.dateValue().toString()) > 0) {
                message = "Voucher Date should be less than current date.";
                showMessage(message, voucherDateBinding.getClientId());
                return false;
            }

        } catch (SQLException e) {
        }
        try {
            if (vouDt.dateValue().toString().compareTo(prdEndDt.dateValue().toString()) < 0) {
                message = "Voucher Date should be greater than period end date.";
                showMessage(message, voucherDateBinding.getClientId());
                return false;
            }
        } catch (SQLException e) {
        }
        return true;
    }

    public void insertIntoRegDtlAL(ActionEvent actionEvent) {
        boolean rslt = chkForNullAttributes();
        if (rslt) {
            BindingContainer bindings = getBindings();
            OperationBinding op = bindings.getOperationBinding("insIntoRegTbls");
            op.execute();
        }
    }

    public void setAuthorityBinding(RichSelectOneChoice authorityBinding) {
        this.authorityBinding = authorityBinding;
    }

    public RichSelectOneChoice getAuthorityBinding() {
        return authorityBinding;
    }

    public String cancelBtnAL() {
        getBindings().getOperationBinding("Rollback").execute();
        getBindings().getOperationBinding("refreshSearchResult").execute();
        this.mode = "V";
        return "ghumaK";
    }

    public void setIntrumentDateBinding(RichInputDate intrumentDateBinding) {
        this.intrumentDateBinding = intrumentDateBinding;
    }

    public RichInputDate getIntrumentDateBinding() {
        return intrumentDateBinding;
    }

    public void setVoucherDateBinding(RichInputDate voucherDateBinding) {
        this.voucherDateBinding = voucherDateBinding;
    }

    public RichInputDate getVoucherDateBinding() {
        return voucherDateBinding;
    }

    public void setInstrumentNoBinding(RichInputText instrumentNoBinding) {
        this.instrumentNoBinding = instrumentNoBinding;
    }

    public RichInputText getInstrumentNoBinding() {
        return instrumentNoBinding;
    }

    public void setInstrumentModeBinding(RichSelectOneChoice instrumentModeBinding) {
        this.instrumentModeBinding = instrumentModeBinding;
    }

    public RichSelectOneChoice getInstrumentModeBinding() {
        return instrumentModeBinding;
    }


    public void setDocIdBind(RichPanelGroupLayout docIdBind) {
        this.docIdBind = docIdBind;
    }

    public RichPanelGroupLayout getDocIdBind() {
        return docIdBind;
    }

    public void addOtherChargesLinkAction(ActionEvent actionEvent) {
        String message = "Instrument amount should be gretaer than zero(0)!";
        Object ob = ADFBeanUtils.callBindingsMethod("calculateInvoiceAmt", null, null);
        Number insAmt = (Number) ob;
        if (insAmt.compareTo(new Number(0)) <= 0)
            ADFModelUtils.showFacesMessage(message, message, FacesMessage.SEVERITY_ERROR, null);
        else
            ADFBeanUtils.showPopup(otherChargesPopUpBind, true);

    }

    public void setOtherChargesPopUpBind(RichPopup otherChargesPopUpBind) {
        this.otherChargesPopUpBind = otherChargesPopUpBind;
    }

    public RichPopup getOtherChargesPopUpBind() {
        return otherChargesPopUpBind;
    }

    public void addOtherChrges(ActionEvent actionEvent) {
        ADFBeanUtils.callBindingsMethod("CreateInsert1", null, null);
    }

    public void deleteOtherCharges(ActionEvent actionEvent) {
        ADFBeanUtils.callBindingsMethod("Delete", null, null);
    }

    public void ocSubmitActionListener(ActionEvent actionEvent) {
        if (!(resolvEl("#{pageFlowScope.ADD_EDIT_MODE}").equalsIgnoreCase("V")))
            ADFBeanUtils.callBindingsMethod("setCurrId", null, null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(transInsAmtBind);
        otherChargesPopUpBind.hide();
    }

      public void ocCoaNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Object ob = ADFBeanUtils.callBindingsMethod("checkForDuplicateCoa", new Object[] { object }, new Object[] {
                                                        "coaNm" });
            if (ob.toString().equalsIgnoreCase("Y"))
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Record already exists for selected COA.", null));
        }
    }

    public void ocAmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (((Number) object).compareTo(new Number(0)) <= 0)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Amount should be greater than zero(0).", null));
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter specific amount.",
                                                          null));
        }

    }
}
