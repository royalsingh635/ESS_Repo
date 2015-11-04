package bdgfinancebudgetapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import bdgfinancebudgetapp.view.utils.ADFUtils;
import bdgfinancebudgetapp.view.utils.JSFUtils;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.layout.RichToolbar;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class CreateFinBdgBean {
    private String mode = (String) JSFUtils.resolveExpression("#{pageFlowScope.P_MODE}");
    private String cldId = (String) JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_CLD_ID}");
    private String hoOrgId = (String) JSFUtils.resolveExpression("#{pageFlowScope.GLBL_HO_ORG_ID}");
    private String orgId = (String) JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_USR_ORG}");
    private Integer slocId =
        (Integer.parseInt(JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString()));
    private Integer usrId = (Integer.parseInt(JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_USR}").toString()));
    private Integer docNo = 34004;
    private Integer docType = 0;
    private String salesDisplayType = "grid";
    private String materislDisplayType = "mGrid";
    private String rightSideDisplayType = "financeTab";
    private String switcherDisplayType = "chartView";

    private RichPanelTabbed itemsDtlTabPgBind;
    private RichInputDate fyStartDtPgBind;
    private RichInputDate fyEndDatePgBind;
    private RichInputDate prdStDtPgBind;
    private RichInputDate prdEnDtPgBind;
    private RichInputText itemAmtSpPgBind;
    private RichInputText itemAmtBsPgBind;
    private RichPanelFormLayout salesBdgFormPgBind;
    private RichTable salesBdgTablePgBind;
    private RichPopup addAnotherSetPopBinding;
    private RichLink populateItemsPgBind;
    private RichPanelFormLayout totalSummPgBind;
    private RichInputText revenueTotPgBind;
    private RichInputText expenseTotPgBind;
    private RichInputText totalAmtPgBind;
    private RichPanelFormLayout othersItemFormPgBind;
    private RichLink othersItemAddBtnPgBind;
    private RichToolbar headerButtonsPgBind;
    private RichLink processBdgAmtDistPgBind;
    private RichPanelBox rightSideBoxPgBind;
    private RichInputText itemAmtSpNotaPgBind;
    private RichInputText itemAmtBsNotaPgBind;
    private RichOutputText amtNotationDescPgBind;
    private RichPanelGroupLayout pannelGroupPgBind;


    public CreateFinBdgBean() {
        OperationBinding ob = ADFUtils.findOperation("filterDetailDate");
        ob.getParamsMap().put("bdgType", 3);
        ob.execute();
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setSwitcherDisplayType(String switcherDisplayType) {
        this.switcherDisplayType = switcherDisplayType;
    }

    public String getSwitcherDisplayType() {
        return switcherDisplayType;
    }

    public void setRightSideDisplayType(String rightSideDisplayType) {
        this.rightSideDisplayType = rightSideDisplayType;
    }

    public String getRightSideDisplayType() {
        return rightSideDisplayType;
    }

    public void setSalesDisplayType(String salesDisplayType) {
        this.salesDisplayType = salesDisplayType;
    }

    public String getSalesDisplayType() {
        return salesDisplayType;
    }

    public void setMaterislDisplayType(String materislDisplayType) {
        this.materislDisplayType = materislDisplayType;
    }

    public String getMaterislDisplayType() {
        return materislDisplayType;
    }

    public void addButtonAL(ActionEvent actionEvent) {
        mode = "C";
        RequestContext.getCurrentInstance().getPageFlowScope().put("P_MODE", "C");

        ADFUtils.findOperation("CreateInsert").execute();

        ADFUtils.findOperation("callAfterInsert").execute();

        executeDataPresentOnDiscloser();
    }

    public void editButtonAL(ActionEvent actionEvent) {
        Integer pending = null;

        OperationBinding chkUsr = ADFUtils.findOperation("pendingCheck");
        chkUsr.getParamsMap().put("SlocId", slocId);
        chkUsr.getParamsMap().put("CldId", cldId);
        chkUsr.getParamsMap().put("OrgId", orgId);
        chkUsr.getParamsMap().put("DocNo", docNo);
        chkUsr.execute();

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
        }
        System.out.println("Pending at=" + pending);
        if (pending.compareTo(-1) != 0 && pending.compareTo(usrId) != 0) {
            OperationBinding gtUsrNm = ADFUtils.findOperation("getUsrName");
            gtUsrNm.getParamsMap().put("usrId", pending);
            gtUsrNm.execute();
            StringBuffer usrName = new StringBuffer("Anonymous");
            if (gtUsrNm.getResult() != null)
                usrName = new StringBuffer("[").append(gtUsrNm.getResult()).append("]");
            String msg2 = ADFModelUtils.resolvRsrc("MSG.1758") + usrName + ADFModelUtils.resolvRsrc("MSG.1501");
            //"Finance Budget Set is pending at other user " + usrName + " for approval, You cannot Edit this.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        } else {
            showPopup(addAnotherSetPopBinding, true);
        }
    }

    /**
     *Method will be be called on both save and save and forward button and will
     */
    public Boolean callMethodsBeforSave() {
        Boolean val = true;

        ADFUtils.findOperation("distributeBudgetAmtPeriodWise").execute();

        //Workflow Start
        String action = "I";
        String remark = "A";

        OperationBinding WfnoOp = ADFUtils.findOperation("getWfNo");
        WfnoOp.getParamsMap().put("sloc_id", slocId);
        WfnoOp.getParamsMap().put("cld_id", cldId);
        WfnoOp.getParamsMap().put("org_id", orgId);
        WfnoOp.getParamsMap().put("doc_no", docNo);
        WfnoOp.execute();

        String wfNum = null;
        Integer level = 0;
        Integer res = -1;

        if (WfnoOp.getResult() != null) {
            wfNum = WfnoOp.getResult().toString();
        }
        if (wfNum != null) {
            OperationBinding usrLevelOp = ADFUtils.findOperation("getUsrLvl");
            usrLevelOp.getParamsMap().put("SlocId", slocId);
            usrLevelOp.getParamsMap().put("CldId", cldId);
            usrLevelOp.getParamsMap().put("OrgId", orgId);
            usrLevelOp.getParamsMap().put("usr_id", usrId);
            usrLevelOp.getParamsMap().put("WfNum", wfNum);
            usrLevelOp.getParamsMap().put("DocNo", docNo);
            usrLevelOp.execute();
            level = -1;
            if (usrLevelOp.getResult() != null) {
                if (usrLevelOp.getResult() != null)
                    level = Integer.parseInt(usrLevelOp.getResult().toString());
            }
            if (level == -1) {
                val = false;

                FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1506"));
                //new FacesMessage("Something went wrong while getting Workflow Level of this user.Contact to ESS.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                OperationBinding insTxnOp = ADFUtils.findOperation("insIntoTxn");
                insTxnOp.getParamsMap().put("sloc_id", slocId);
                insTxnOp.getParamsMap().put("cld_id", cldId);
                insTxnOp.getParamsMap().put("pOrgId", orgId);
                insTxnOp.getParamsMap().put("DOC_NO", docNo);
                insTxnOp.getParamsMap().put("WfNum", wfNum);
                insTxnOp.getParamsMap().put("poDocId", null);
                insTxnOp.getParamsMap().put("usr_idFrm", usrId);
                insTxnOp.getParamsMap().put("usr_idTo", usrId);
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
                    /* OperationBinding operationBinding = ADFUtils.findOperation("Commit");
                    operationBinding.execute(); */
                    val = true;
                } else {
                    val = false;
                    System.out.println("error during insert to WF");
                }
                System.out.println(level + "level--res" + res);
            }
        } else {
            val = true;

            FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1486"));
            //FacesMessage message = new FacesMessage("Workflow not Defined for this Document.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        //Workflow end

        return val;
    }

    public void saveButtonAL(ActionEvent actionEvent) {
        if (callMethodsBeforSave()) {
            ADFUtils.findOperation("Commit").execute();
            mode = "V";
            RequestContext.getCurrentInstance().getPageFlowScope().put("P_MODE", "V");
            JSFUtils.addFacesInformationMessage("Record Saved Successsfully");
        }
    }

    public String saveAndFwdAction() {
        if (callMethodsBeforSave()) {
            //chkDistributedAmtSum
            OperationBinding opChkAmt = ADFBeanUtils.getOperationBinding("chkDistributedAmtSum");
            opChkAmt.execute();
            if (opChkAmt.getResult() != null && opChkAmt.getResult().toString().equals("Y")) {
            } else {
                String coaNm = " ";
                if (opChkAmt.getResult() != null)
                    coaNm = (String) opChkAmt.getResult();
                ADFModelUtils.showFacesMessage(" ", "Distributed Amount should be equal to COA Amount. \n" + coaNm,
                                               FacesMessage.SEVERITY_WARN, null);
                return null;
            }
            ADFUtils.findOperation("Commit").execute();
            mode = "V";
            RequestContext.getCurrentInstance().getPageFlowScope().put("P_MODE", "V");
            JSFUtils.addFacesInformationMessage("Record Saved Successsfully");

            return "goToWf";
        } else {
            return null;
        }
    }

    public String cancelButtonAction() {
        mode = "V";
        ADFUtils.findOperation("Rollback").execute();

        return "back";
    }

    public void executeDataPresentOnDiscloser() {
        RichPanelTabbed uiXIterator = this.getItemsDtlTabPgBind();
        System.out.println("Child count=" + uiXIterator.getChildCount());
        String id = null;
        Integer tabExpanded = null;
        for (UIComponent child : uiXIterator.getChildren()) {
            RichShowDetailItem sdi = (RichShowDetailItem) child;
            System.out.println("Value of sdi = " + sdi.getText() + " disclosed event " + sdi.isDisclosed());
            if (sdi.isDisclosed()) {
                id = sdi.getId();
                if (id.equalsIgnoreCase("tab1")) { /// Sales Tab is expanded
                    tabExpanded = 1;
                } else if (id.equalsIgnoreCase("tab2")) { /// Material Tab is Expanded
                    tabExpanded = 2;
                } else if (id.equalsIgnoreCase("tab3")) { // Others tab is expanded
                    tabExpanded = 3;
                }
            }
        }

        if (tabExpanded != null) {
            OperationBinding ob = ADFUtils.findOperation("executeQuerysBasedOnSelection");
            ob.getParamsMap().put("tabExpanded", tabExpanded);
            ob.execute();
        }
    }

    public void populateItemsAL(ActionEvent actionEvent) {


        ADFUtils.findOperation("insertDataAccordingly").execute();

        executeDataPresentOnDiscloser();

        AdfFacesContext.getCurrentInstance().addPartialTarget(salesBdgFormPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(salesBdgTablePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemsDtlTabPgBind);
    }

    public void addOthersDetailAL(ActionEvent actionEvent) {
        System.out.println("Inside Add Method");
        ADFUtils.findOperation("CreateInsert1").execute();
    }

    public void salesBudgetDL(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {

            OperationBinding ob = ADFUtils.findOperation("filterDetailDate");
            ob.getParamsMap().put("bdgType", 1);
            ob.execute();

            rightSideDisplayType = "salesTab";
            AdfFacesContext.getCurrentInstance().addPartialTarget(rightSideBoxPgBind);
        }
    }

    public void materialBudgetDL(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {

            OperationBinding ob = ADFUtils.findOperation("filterDetailDate");
            ob.getParamsMap().put("bdgType", 2);
            ob.execute();

            rightSideDisplayType = "materialTab";
            AdfFacesContext.getCurrentInstance().addPartialTarget(rightSideBoxPgBind);
        }
    }

    public void othersBudgetDL(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {

            OperationBinding ob = ADFUtils.findOperation("filterDetailDate");
            ob.getParamsMap().put("bdgType", 3);
            ob.execute();

            rightSideDisplayType = "financeTab";
            AdfFacesContext.getCurrentInstance().addPartialTarget(rightSideBoxPgBind);
        }
    }

    public void profitAndLossTabDL(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {


            rightSideDisplayType = "financeTab";
            AdfFacesContext.getCurrentInstance().addPartialTarget(rightSideBoxPgBind);
        }
    }

    public void setItemsDtlTabPgBind(RichPanelTabbed itemsDtlTabPgBind) {
        this.itemsDtlTabPgBind = itemsDtlTabPgBind;
    }

    public RichPanelTabbed getItemsDtlTabPgBind() {
        return itemsDtlTabPgBind;
    }

    public void prdStartDtVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding opFyIdDt = ADFUtils.findOperation("settingFinancialYear");
            opFyIdDt.getParamsMap().put("stDt", vce.getNewValue());
            opFyIdDt.execute();
            //vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            AdfFacesContext.getCurrentInstance().addPartialTarget(fyStartDtPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(fyEndDatePgBind);
        }
    }

    public void setFyStartDtPgBind(RichInputDate fyStartDtPgBind) {
        this.fyStartDtPgBind = fyStartDtPgBind;
    }

    public RichInputDate getFyStartDtPgBind() {
        return fyStartDtPgBind;
    }

    public void setFyEndDatePgBind(RichInputDate fyEndDatePgBind) {
        this.fyEndDatePgBind = fyEndDatePgBind;
    }

    public RichInputDate getFyEndDatePgBind() {
        return fyEndDatePgBind;
    }

    public void prdStrtDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Timestamp stDt = (Timestamp) object;

            OperationBinding opFyIdDt = ADFUtils.findOperation("validateFinancialYear");
            opFyIdDt.getParamsMap().put("stDt", stDt);
            opFyIdDt.execute();
            if (opFyIdDt.getErrors().size() == 0 && opFyIdDt.getResult() != null &&
                opFyIdDt.getResult().toString().equals("Y")) {
                OperationBinding opChkExist = ADFUtils.findOperation("chkDtExistInPeriod");
                opChkExist.getParamsMap().put("validDt", stDt);
                opChkExist.execute();
                if (opChkExist.getErrors().size() == 0 && opChkExist.getResult() != null &&
                    opChkExist.getResult().toString().equals("N")) {
                } else if (opChkExist.getErrors().size() == 0 && opChkExist.getResult() != null) {
                    throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2319") +
                                                                  opChkExist.getResult()));
                    /* throw new ValidatorException(new FacesMessage("Budget for the Selected Date is already exist - " +
                                                                  opChkExist.getResult())); */
                }
            } else {
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2264")));
                //throw new ValidatorException(new FacesMessage("Invalid Date - Please Check Financial Year"));
            }
        }
    }

    public void prdEndDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            Timestamp strtDt = null;
            Timestamp endDt = null;
            Timestamp fyEndDt = null;
            if (prdStDtPgBind.getValue() != null && prdStDtPgBind.getValue().toString().length() > 0) {
                strtDt = (Timestamp) prdStDtPgBind.getValue();
                endDt = (Timestamp) object;

                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2320")));
                        //throw new ValidatorException(new FacesMessage("Period End Date can not be before period Start Date."));
                    }
                }
                OperationBinding opFyIdDt = ADFUtils.findOperation("validateFinancialYear");
                opFyIdDt.getParamsMap().put("stDt", endDt);
                opFyIdDt.execute();
                if (opFyIdDt.getErrors().size() == 0 && opFyIdDt.getResult() != null &&
                    opFyIdDt.getResult().toString().equals("Y")) {
                } else {
                    throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2264")));
                    //throw new ValidatorException(new FacesMessage("Invalid Date - Please Check Financial Year"));
                }
            }
            if (fyEndDatePgBind.getValue() != null && fyEndDatePgBind.getValue().toString().length() > 0) {

                fyEndDt = (Timestamp) fyEndDatePgBind.getValue();
                endDt = (Timestamp) object;

                if (endDt.compareTo(fyEndDt) > 0) {
                    if (endDt.toString().equals(fyEndDt.toString())) {
                    } else {
                        throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2321")));
                        //throw new ValidatorException(new FacesMessage("Period End Date can not be After Financial Year End Date."));
                    }
                }
            }
            endDt = (Timestamp) object;

            OperationBinding opChkExist = ADFUtils.findOperation("chkDtExistInPeriod");
            opChkExist.getParamsMap().put("validDt", endDt);
            opChkExist.execute();
            if (opChkExist.getErrors().size() == 0 && opChkExist.getResult() != null &&
                opChkExist.getResult().toString().equals("N")) {
            } else if (opChkExist.getErrors().size() == 0 && opChkExist.getResult() != null) {
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2319") +
                                                              opChkExist.getResult()));
                /* throw new ValidatorException(new FacesMessage("Budget for the Selected Date is already exist - " +
                                                              opChkExist.getResult())); */
            }
        }
    }

    public void setPrdStDtPgBind(RichInputDate prdStDtPgBind) {
        this.prdStDtPgBind = prdStDtPgBind;
    }

    public RichInputDate getPrdStDtPgBind() {
        return prdStDtPgBind;
    }

    public void setPrdEnDtPgBind(RichInputDate prdEnDtPgBind) {
        this.prdEnDtPgBind = prdEnDtPgBind;
    }

    public RichInputDate getPrdEnDtPgBind() {
        return prdEnDtPgBind;
    }

    /**
     *      Method to Apply Precision Validation(P,S,V)
     *      P- Precision
     *      S- Scale
     *      V- Value to validate
     * */
    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }


    public void itemAmtSpValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1107"), null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null)); */
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.198"), null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than zero.", null)); */
            }
        }
    }

    public void itemAmtSpVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null && vce.getNewValue().toString().length() > 0) {
            OperationBinding ob = ADFUtils.findOperation("setItemAmtBS");
            ob.getParamsMap().put("amtSp", vce.getNewValue());
            ob.execute();

            AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtBsPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(totalAmtPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(expenseTotPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(revenueTotPgBind);

            ADFUtils.findOperation("updateBudgetAmt").execute();
        }
    }

    public void setItemAmtSpPgBind(RichInputText itemAmtSpPgBind) {
        this.itemAmtSpPgBind = itemAmtSpPgBind;
    }

    public RichInputText getItemAmtSpPgBind() {
        return itemAmtSpPgBind;
    }

    public void setItemAmtBsPgBind(RichInputText itemAmtBsPgBind) {
        this.itemAmtBsPgBind = itemAmtBsPgBind;
    }

    public RichInputText getItemAmtBsPgBind() {
        return itemAmtBsPgBind;
    }

    public void setSalesBdgFormPgBind(RichPanelFormLayout salesBdgFormPgBind) {
        this.salesBdgFormPgBind = salesBdgFormPgBind;
    }

    public RichPanelFormLayout getSalesBdgFormPgBind() {
        return salesBdgFormPgBind;
    }

    public void setSalesBdgTablePgBind(RichTable salesBdgTablePgBind) {
        this.salesBdgTablePgBind = salesBdgTablePgBind;
    }

    public RichTable getSalesBdgTablePgBind() {
        return salesBdgTablePgBind;
    }

    private void refreshPage() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        String refreshpage = fctx.getViewRoot().getViewId();
        ViewHandler ViewH = fctx.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fctx, refreshpage);
        UIV.setViewId(refreshpage);
        fctx.setViewRoot(UIV);
    }

    public void addAnotherSetPopUpDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("yes")) {
            ADFUtils.findOperation("amendCurrBdgt").execute();
            mode = "C";
            RequestContext.getCurrentInstance().getPageFlowScope().put("P_MODE", "C");

            refreshPage();
        } else {
            System.out.println("Inside No part of popup");
            OperationBinding ob = ADFUtils.findOperation("chkBdgSetEligableToEditOrNot");
            ob.execute();
            System.out.println("result is " + ob.getResult());
            if (ob.getResult() != null) {
                if (Integer.parseInt(ob.getResult().toString()) == 1) {
                    JSFUtils.addFacesErrorMessage(ADFModelUtils.resolvRsrc("MSG.2322"));
                    //JSFUtils.addFacesErrorMessage("Budget Set has been approved. You cannot edit this Budget Set. Please create new Budget Set.");
                } else if (Integer.parseInt(ob.getResult().toString()) == 2) {
                    mode = "E";
                    RequestContext.getCurrentInstance().getPageFlowScope().put("P_MODE", "E");

                    //JSFUtils.addFacesErrorMessage("Budget Set has been forwarded. You cannot edit this Budget Set.");
                } else {
                    mode = "E";
                    RequestContext.getCurrentInstance().getPageFlowScope().put("P_MODE", "E");
                }
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(populateItemsPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(totalSummPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(revenueTotPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(expenseTotPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(totalAmtPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(othersItemFormPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(othersItemAddBtnPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(headerButtonsPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(processBdgAmtDistPgBind);
        }
    }

    public void setAddAnotherSetPopBinding(RichPopup addAnotherSetPopBinding) {
        this.addAnotherSetPopBinding = addAnotherSetPopBinding;
    }

    public RichPopup getAddAnotherSetPopBinding() {
        return addAnotherSetPopBinding;
    }

    private void showPopup(RichPopup popup, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && popup != null) {
                String popupId = popup.getClientId(context);
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

    public void setPopulateItemsPgBind(RichLink populateItemsPgBind) {
        this.populateItemsPgBind = populateItemsPgBind;
    }

    public RichLink getPopulateItemsPgBind() {
        return populateItemsPgBind;
    }

    public void setTotalSummPgBind(RichPanelFormLayout totalSummPgBind) {
        this.totalSummPgBind = totalSummPgBind;
    }

    public RichPanelFormLayout getTotalSummPgBind() {
        return totalSummPgBind;
    }

    public void setRevenueTotPgBind(RichInputText revenueTotPgBind) {
        this.revenueTotPgBind = revenueTotPgBind;
    }

    public RichInputText getRevenueTotPgBind() {
        return revenueTotPgBind;
    }

    public void setExpenseTotPgBind(RichInputText expenseTotPgBind) {
        this.expenseTotPgBind = expenseTotPgBind;
    }

    public RichInputText getExpenseTotPgBind() {
        return expenseTotPgBind;
    }

    public void setTotalAmtPgBind(RichInputText totalAmtPgBind) {
        this.totalAmtPgBind = totalAmtPgBind;
    }

    public RichInputText getTotalAmtPgBind() {
        return totalAmtPgBind;
    }

    public void setOthersItemFormPgBind(RichPanelFormLayout othersItemFormPgBind) {
        this.othersItemFormPgBind = othersItemFormPgBind;
    }

    public RichPanelFormLayout getOthersItemFormPgBind() {
        return othersItemFormPgBind;
    }

    public void setOthersItemAddBtnPgBind(RichLink othersItemAddBtnPgBind) {
        this.othersItemAddBtnPgBind = othersItemAddBtnPgBind;
    }

    public RichLink getOthersItemAddBtnPgBind() {
        return othersItemAddBtnPgBind;
    }

    public void salesGridViewAL(ActionEvent actionEvent) {
        salesDisplayType = "grid";
    }

    public void salesFormViewAL(ActionEvent actionEvent) {
        salesDisplayType = "form";
    }

    public void processBdgAmtDistributionAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("distributeItemAmtPeriodWise").execute();
        JSFUtils.addFacesInformationMessage("Budget distributed accordingly.");
    }

    public void materialGridViewAL(ActionEvent actionEvent) {
        materislDisplayType = "mGrid";
    }

    public void materialFormViewAL(ActionEvent actionEvent) {
        materislDisplayType = "mForm";
    }

    public void setHeaderButtonsPgBind(RichToolbar headerButtonsPgBind) {
        this.headerButtonsPgBind = headerButtonsPgBind;
    }

    public RichToolbar getHeaderButtonsPgBind() {
        return headerButtonsPgBind;
    }

    public void setProcessBdgAmtDistPgBind(RichLink processBdgAmtDistPgBind) {
        this.processBdgAmtDistPgBind = processBdgAmtDistPgBind;
    }

    public RichLink getProcessBdgAmtDistPgBind() {
        return processBdgAmtDistPgBind;
    }

    public void amountTypeVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtBsPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(totalAmtPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(expenseTotPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(revenueTotPgBind);

            ADFUtils.findOperation("updateBudgetAmt").execute();
        }
    }

    public void setRightSideBoxPgBind(RichPanelBox rightSideBoxPgBind) {
        this.rightSideBoxPgBind = rightSideBoxPgBind;
    }

    public RichPanelBox getRightSideBoxPgBind() {
        return rightSideBoxPgBind;
    }

    public void chartViewButtonAL(ActionEvent actionEvent) {
        switcherDisplayType = "chartView";
    }

    public void detailViewButtonAL(ActionEvent actionEvent) {
        switcherDisplayType = "detailView";
    }

    public void itemDtlDistbAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1107"), null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null)); */
            }
            if (val.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.198"), null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than zero.", null)); */
            }
        }
    }

    public void itmDtlDistbAmtVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding ob = ADFUtils.findOperation("updateItemDisbAmtBase");
            ob.getParamsMap().put("val", vce.getNewValue());
            ob.execute();
        }
    }

    public void addUserDefinedFieldsAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("getFlexiFieldFilter").execute();
    }

    public void itemAmtSpNotaValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1107"), null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null)); */
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.198"), null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than zero.", null)); */
            }
        }
    }

    public void itemAmtSpNotaVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null && vce.getNewValue().toString().length() > 0) {
            Number notaAmt = (Number) JSFUtils.resolveExpression("#{pageFlowScope.P_AMT_NOTATION}");
            notaAmt = notaAmt.multiply((Number) vce.getNewValue());

            OperationBinding ob = ADFUtils.findOperation("setItemAmtBS");
            ob.getParamsMap().put("amtSp", notaAmt);
            ob.execute();

            AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpNotaPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtBsNotaPgBind);

            AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtSpPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemAmtBsPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(totalAmtPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(expenseTotPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(revenueTotPgBind);

            ADFUtils.findOperation("updateBudgetAmt").execute();
        }
    }

    public void setItemAmtSpNotaPgBind(RichInputText itemAmtSpNotaPgBind) {
        this.itemAmtSpNotaPgBind = itemAmtSpNotaPgBind;
    }

    public RichInputText getItemAmtSpNotaPgBind() {
        return itemAmtSpNotaPgBind;
    }

    public void setItemAmtBsNotaPgBind(RichInputText itemAmtBsNotaPgBind) {
        this.itemAmtBsNotaPgBind = itemAmtBsNotaPgBind;
    }

    public RichInputText getItemAmtBsNotaPgBind() {
        return itemAmtBsNotaPgBind;
    }

    public void itemDtlDistbAmtNotaValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1107"), null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null)); */
            }
            if (val.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.198"), null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than zero.", null)); */
            }
        }
    }

    public void itemDtlDistbAmtNotaVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            Number notaAmt = (Number) JSFUtils.resolveExpression("#{pageFlowScope.P_AMT_NOTATION}");
            notaAmt = notaAmt.multiply((Number) vce.getNewValue());

            OperationBinding ob = ADFUtils.findOperation("updateItemDisbAmtBase");
            ob.getParamsMap().put("val", notaAmt);
            ob.execute();
        }
    }

    public void amtNotationVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());

            RequestContext.getCurrentInstance().getPageFlowScope().put("P_AMT_NOTATION", vce.getNewValue());

            Number val = (Number) vce.getNewValue();
            if (val.compareTo(1) > 0) {
                RequestContext.getCurrentInstance().getPageFlowScope().put("P_NOTATION_DISP", "Y");
            } else {
                RequestContext.getCurrentInstance().getPageFlowScope().put("P_NOTATION_DISP", "N");
            }
            RequestContext.getCurrentInstance().getPageFlowScope().put("DISP_AMT_NOTATION",
                                                                       amtNotationDescPgBind.getValue());
            AdfFacesContext.getCurrentInstance().addPartialTarget(pannelGroupPgBind);
        }
    }

    public void setAmtNotationDescPgBind(RichOutputText amtNotationDescPgBind) {
        this.amtNotationDescPgBind = amtNotationDescPgBind;
    }

    public RichOutputText getAmtNotationDescPgBind() {
        return amtNotationDescPgBind;
    }

    public void setPannelGroupPgBind(RichPanelGroupLayout pannelGroupPgBind) {
        this.pannelGroupPgBind = pannelGroupPgBind;
    }

    public RichPanelGroupLayout getPannelGroupPgBind() {
        return pannelGroupPgBind;
    }

    public String expandPnLAction() {

        return "expand";
    }

    public String prdCostCenterAction() {
        OperationBinding binding = ADFUtils.findOperation("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            return "prdCostCenter";
        } else {
            return null;
        }
    }

    public Map getUserMenuAccess() {
        return new HashMap<Integer, Boolean>() {
            @Override
            public Boolean get(Object key) {
                System.out.println("Key =" + key);
                if (key != null) {
                    Boolean retVal = false;
                    OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkUsrMnuSecAccess");
                    opChk.getParamsMap().put("mnuId", key);
                    opChk.execute();
                    if (opChk.getResult() != null)
                        retVal = (Boolean) opChk.getResult();
                    System.out.println("retValue=" + retVal);
                    return retVal;
                } else
                    return true;
            }
        };
    }
}
