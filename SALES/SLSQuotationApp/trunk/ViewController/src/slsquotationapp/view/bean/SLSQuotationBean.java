package slsquotationapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import java.io.Serializable;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.input.RichTextEditor;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class SLSQuotationBean implements Serializable {
    private static ADFLogger _logger = ADFLogger.createADFLogger(SLSQuotationBean.class);

    /**
     * form_mode used for WHOLE form to enable or disable contents
     * Here an using three modes.
     * 1.Edit mode : E
     * 2.View mode : V
     */
    private StringBuffer form_mode = new StringBuffer("V");

    /**
     * quot_form_mode for ONLY quotation_form
     * Here an using three modes.
     * 1.Edit mode : E
     * 2.View mode : V
     * 3.Add mode  : A
     */
    private StringBuffer quot_form_mode = new StringBuffer("V");

    /**
     * quot_form_mode for ONLY quotation_form
     * Here an using three modes.
     * 1.Edit mode : E
     * 2.View mode : V
     * 3.Add mode  : A
     */
    private StringBuffer itemline_form_mode = new StringBuffer("V");
    private RichPopup delQuotationWarnPopUpBind;
    private RichPopup addEoPopUpBind;
    private RichInputText eoNmPopUpBind;
    private RichSelectOneChoice eoTypePopUpBind;
    private RichPopup deleteItemWarnPopUpBind;
    private RichPopup applyItemTaxRulePopUpBind;
    private RichSelectOneChoice taxRuleIdPopupLovBind;
    private RichPopup quotationTaxApplyPopUpBind;
    private RichSelectOneChoice taxRuleIdQuotationBind;
    // private RichInputComboboxListOfValues itmNmBind;
    private RichInputText itmDiscountVal;
    private RichSelectOneRadio itmDiscTypeNew;
    private RichSelectOneChoice tncLOVBind;
    private RichInputText itmPriceFormBind;
    private RichInputText currConvBind;
    private RichLink saveBtnBind;
    private RichSelectOneChoice itmUnitBind;
    private RichSelectOneRadio taxRuleQuotationBind;
    private RichPanelFormLayout quotationPglBind;
    private StringBuffer showItmDesc = null;
    private RichPanelFormLayout itmPflBind;
    private RichPanelLabelAndMessage taxOrderwiseBind;
    private RichSelectOneRadio afterDiscountBind;
    private RichLink applyTaxBind;
    private RichShowDetailItem itmTab;
    private RichPopup itemAddAlertPopUpBind;
    private RichLink addItemLineBtn;
    private String WfIdForQuot;
    private String wfReturn;
    private Number quotAmt;
    private RichPopup cancelQuotPopUpBind;
    private StringBuffer amd = new StringBuffer("A");
    private RichLink saveAndForwardBind;
    private RichInputText toAddressPgBind;
    private RichInputText subjectPgBind;
    private RichTextEditor messagePgBind;
    private RichInputText dispDocIdPgBind;
    private RichPopup mailPopUpPgBind;
    private RichInputText frightChargePgBind;
    private RichInputDate docDatePgBind;
    private RichInputDate validDatePgBind;
    private RichSelectOneRadio quotTypeBind;
    private RichPanelLabelAndMessage pricePolicyBind;
    private RichInputText totalAmountPriceBindVar;
    private RichInputText totAmtValueBind;
    private RichPanelGroupLayout panelformbinding;
    private RichInputListOfValues specificCurrQuotBind;
    private RichInputListOfValues itmNmBind;
    private RichInputText itmAmtSpBind;
    private StringBuffer usesScheme = null;
    private StringBuffer policyApplied = null;
    private String PopCancelShow = "Y";
    private RichLink crossButtonBind;
    private Integer cancelButtonMode = null;
    private RichLink schemeLinkPgBind;
    private RichInputText quotQtyPgBind;
    private RichSelectOneRadio toleranceQtyPgBind;
    private RichInputText toleranceQtyValPgBind;
    private RichOutputText tableItmNmBind;
    private RichOutputText tableItmIdBind;
    // private RichInputListOfValues eoNmBind;
    // private RichInputListOfValues enquiryTransBind;
    private RichPopup showTaxPopBind;
    // private RichInputComboboxListOfValues eoNmBind;

    public SLSQuotationBean() {
    }


    /**********************************ADD ,EDIT ,CANCEL ,DELETE Button ACTIONS for QUOTATION ********************************************************/

    /**
     * ActionEvent to add quotation
     * @param actionEvent
     */
    public void addQuotationACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("Rollback").execute();
        this.getBindings().getOperationBinding("CreateInsert").execute();
        this.form_mode = new StringBuffer("E");
        this.quot_form_mode = new StringBuffer("A");

    }

    /**
     * ActionEvent to add quotation in create mode from the searchtaskFlow
     *
     * @return string
     */
    public String addQuotationACTION() {
        this.form_mode = new StringBuffer("E");
        this.quot_form_mode = new StringBuffer("A");
        return "CREATED";
    }

    /**
     * ActionEvent to edit quotation
     * @param actionEvent
     */
    public void editQuatationACTION(ActionEvent actionEvent) {
        Integer userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        Integer pendingAtUsr;
        pendingAtUsr = (Integer) this.getBindings().getOperationBinding("slsQuotPendingAt").execute();
        //System.out.println("Pending at :"+pendingAtUsr+" User logged in is :"+userId);
        if (pendingAtUsr == -1 || pendingAtUsr == userId) {
            this.form_mode = new StringBuffer("E");
            this.quot_form_mode = new StringBuffer("E");
            //AdfFacesContext.getCurrentInstance().addPartialTarget(detailsPtlBind);
        } else {

            String msg2 =
                "<html>\n" + "<body><table><tr><td></td></tr></table>" +
                "<span style='color:red;font-family:Arial;font-size:11px;font-weight:bold;'>" +
                ADFModelUtils.resolvRsrc("MSG.1655") + " !</span></br>\n" +
                "<span style='color:blue;font-family:Arial;font-size:10px;font-weight:bold;'>" +
                ADFModelUtils.resolvRsrc("MSG.1656") + ".</span>\n" + "</body>" + "</html>";
            FacesMessage message2 =
                new FacesMessage(msg2); //MSG.1015:You cannot edit this sales invoice as it is pending at another user in WorkFlow   &&  MSG.1015 :Sales Invoice is pending at another user !
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        }

    }

    /**
     * ActionEvent to save quotation
     * @param actionEvent
     */
    public void saveQuotationACTION(ActionEvent actionEvent) {
        //To check if item exist or not
        Boolean val = true;
        System.out.println("In save action front ilne");
        OperationBinding chkEnt = this.getBindings().getOperationBinding("chkEntutyName");
        chkEnt.execute();
        OperationBinding dup =
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("chkTabValidations");
        dup.execute();
        dup.getResult();
        if (dup.getResult().equals(false)) {

        } else {
            if ((Boolean) chkEnt.getResult()) {

                OperationBinding curExst = this.getBindings().getOperationBinding("chkCurrencyNameexist");
                curExst.execute();

                if ((Boolean) curExst.getResult()) {


                    if ("Y".equalsIgnoreCase(getPolicyApplied().toString())) {
                        System.out.println(" Policy is applied ======= ");
                        OperationBinding op = ADFBeanUtils.findOperation("validationOnAddItem");
                        op.execute();
                        if (op != null) {
                            val = (Boolean) op.getResult();
                        }
                    }
                    if (val) {
                        OperationBinding chkFlxFld = this.getBindings().getOperationBinding("isflexFieldNull");
                        chkFlxFld.execute();

                        if ((Boolean) chkFlxFld.getResult()) {
                            ADFModelUtils.showFormattedFacesMessage("Mandatory Field(s) cannnot be Empty!",
                                                                    "Please enter values for Mandatory fields in Other Parameters ", //The function for Generating Warranty returned
                                                                    FacesMessage.SEVERITY_ERROR);
                        } else {

                            if (this.getBindings().getOperationBinding("isItemExistsForQuot").execute().equals((Object) false)) {
                                FacesMessage message =
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                     "<html>" + "<body></br>" +
                                                     "<span style='color:red;font-family:Arial;font-size:11px;font-weight:bold;'>" +
                                                     ADFModelUtils.resolvRsrc("MSG.2196") + "</span></br>" +
                                                     "<b style='color:blue;font-family:Arial;font-size:10px;font-weight:bold;'> " +
                                                     ADFModelUtils.resolvRsrc("MSG.2197") + " </b>" + "</body>" +
                                                     "</html>",
                                                     null); //MSG.2196:Item is not Added., MSG.2197 :Added row dosen't have Item added to it. Please Add Item.
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext.getCurrentInstance().addMessage(null, message);
                            }
                            //to check if orderwise tax is applied correctly or not
                            else if (this.getBindings().getOperationBinding("isOrderWiseAppliedCorrectly").execute().equals((Object) true)) {
                                // to check if itemwise tax is applied correctly or not
                                //System.out.println("set 1");
                                if (this.getBindings().getOperationBinding("isItmWiseAppliedCorrectly").execute().equals((Object) true)) {
                                    // get the docId of the currentRow
                                    System.out.println("st 2");
                                    Object execute =
                                        this.getBindings().getOperationBinding("getSlsQuotCurrRowDocId").execute();
                                    StringBuffer ex = new StringBuffer(execute.toString());
                                    //amendFUNCTION
                                    if (amd.toString().equals("E")) {
                                        this.getBindings().getOperationBinding("amendFUNCTION").execute();
                                        System.out.println("Amendment took place.");
                                        amd = new StringBuffer("A");
                                    }
                                    /*  OperationBinding operationBinding =
                                this.getBindings().getOperationBinding("generateQuotId");
                            operationBinding.getParamsMap().put("DocId", ex.toString());
                            operationBinding.execute(); */


                                    OperationBinding comit = this.getBindings().getOperationBinding("Commit");
                                    comit.execute();
                                    if (comit.getErrors().isEmpty()) {
                                        if (this.quot_form_mode == new StringBuffer("A")) {
                                            FacesMessage message =
                                                new FacesMessage(ADFModelUtils.resolvRsrc("MSG.835")); //"Quotation created Successfully!"
                                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                                            FacesContext.getCurrentInstance().addMessage(null, message);
                                            this.form_mode = new StringBuffer("V");
                                            this.quot_form_mode = new StringBuffer("V");
                                            //setting the docId of currentRow
                                            //this.getBindings().getOperationBinding("refereshAllVoOnCommit").execute();
                                            OperationBinding methodBinding =
                                                this.getBindings().getOperationBinding("setCurrentRowInSlsQuoteVo");
                                            methodBinding.getParamsMap().put("DocId", ex.toString());
                                            methodBinding.execute();

                                        } else {
                                            FacesMessage message =
                                                new FacesMessage(ADFModelUtils.resolvRsrc("MSG.836")); //"Quotation Saved Successfully!"
                                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                                            FacesContext.getCurrentInstance().addMessage(null, message);
                                            this.form_mode = new StringBuffer("V");
                                            this.quot_form_mode = new StringBuffer("V");
                                            //setting the docId of currentRow
                                            //this.getBindings().getOperationBinding("refereshAllVoOnCommit").execute();
                                            OperationBinding methodBinding =
                                                this.getBindings().getOperationBinding("setCurrentRowInSlsQuoteVo");
                                            methodBinding.getParamsMap().put("DocId", ex.toString());
                                            methodBinding.execute();
                                            // to call function FN_INS_QUOT_CALC and GEN_DISP_DOC_ID
                                            methodBinding =
                                                this.getBindings().getOperationBinding("callFunction_FN_INS_QUOT_CALC");
                                            methodBinding.getParamsMap().put("DocId", ex.toString());
                                            methodBinding.execute();
                                            ADFBeanUtils.findOperation("setOputunityStatus").execute();
                                            this.getBindings().getOperationBinding("Commit").execute();
                                            //eoNmBind = null;

                                        }

                                    } else {
                                        FacesMessage message =
                                            new FacesMessage(ADFModelUtils.resolvRsrc("MSG.837")); //"There have been an error in saving quotation! Please try again!"
                                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                        FacesContext.getCurrentInstance().addMessage(null, message);
                                    }
                                } else {
                                    /* FacesMessage message =
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                 "<html>" + "<body></br>" +
                                                 "<span style='color:red;font-family:Arial;font-size:11px;font-weight:bold;'>" +
                                                 resolvElDCMsg("#{bundle['MSG.838']}").toString() + "</span></br>" +
                                                 "<b style='color:blue;font-family:Arial;font-size:10px;font-weight:bold;'>" +
                                                 resolvElDCMsg("#{bundle['MSG.839']}").toString() + " </b>" +
                                                 "</body>" + "</html>",
                                                 null); //MSG.838:Taxable amount has been changed after applying tax in one or more of the items!&& MSG.839 :Please apply tax on the total taxable amount.
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext.getCurrentInstance().addMessage(null, message); */
                                    // Message Shown in AMImpl
                                }

                            } else {
                                /* FacesMessage message =
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                             "<html>" + "<body></br>" +
                                             "<span style='color:red;font-family:Arial;font-size:11px;font-weight:bold;'>" +
                                             resolvElDCMsg("#{bundle['MSG.840']}").toString() + "</span></br>" +
                                             "<b style='color:blue;font-family:Arial;font-size:10px;font-weight:bold;'> " +
                                             resolvElDCMsg("#{bundle['MSG.839']}").toString() + " </b>" + "</body>" +
                                             "</html>", null); //MSG.840&&MSG.839
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, message); */
                                // Message Shown in AMImpl
                            }
                        }
                    } else {
                        FacesMessage message = new FacesMessage("Please select Price Policy");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                } else {
                    FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1731"));
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            } else {
                FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1732"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        }
    }

    /**
     * ActionEvent to cancel quotation
     * @param actionEvent
     */
    public String cancelQuotationACTION() {
        OperationBinding binding = this.getBindings().getOperationBinding("Rollback");
        binding.execute();
        this.form_mode = new StringBuffer("V");
        this.quot_form_mode = new StringBuffer("V");
        return "GoBack";
    }

    /**
     * ActionEvent to delete quotation
     * @param actionEvent
     */
    public void deleteQuotationACTION(ActionEvent actionEvent) {
        /* Object execute = this.getBindings().getOperationBinding("checkQuotationDeletable").execute();
        if(execute.toString().equalsIgnoreCase("true")){
        System.out.println("Message");
            FacesMessage message = new FacesMessage("Items are added to quotation! Cannot delete!");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }else{

        }  */
        this.showPopup(this.delQuotationWarnPopUpBind, true);
    }

    /**
     * ActionEvent Ok action in delete popUp
     * @param actionEvent
     */
    public void delQuotWarnPopUpOkACTION(ActionEvent actionEvent) {

    }

    /**
     * ActionEvent
     * @return
     */
    public String delQuotationACTION() {
        this.getBindings().getOperationBinding("Delete").execute();
        OperationBinding binding = this.getBindings().getOperationBinding("Commit");
        binding.execute();

        if (binding.getErrors().isEmpty()) {
            FacesMessage message =
                new FacesMessage(ADFModelUtils.resolvRsrc("MSG.841")); //"Quotation deleted Successfully!"
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, message);
            this.delQuotationWarnPopUpBind.hide();
            return "GoBack";
        } else {
            FacesMessage message =
                new FacesMessage(ADFModelUtils.resolvRsrc("MSG.842")); //"There have been an error in deleting quotation! Please try again!"
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        return null;
    }

    /**
     *  ActionEvent cancel action in delete popUp
     *  @param actionEvent
     */
    public void delQuotWarnPopUpCancelACTION(ActionEvent actionEvent) {
        this.delQuotationWarnPopUpBind.hide();
    }

    /**
     *  ActionEvent in AddEo action on the link in the LOV EoName
     * @param actionEvent
     */
    public void addEoACTION(ActionEvent actionEvent) {
        this.showPopup(this.addEoPopUpBind, true);
    }

    /**
     *  ActionEvent in Okey action for addEo PopUp
     * @param actionEvent
     */
    public void addEoPopUpOkBind(ActionEvent actionEvent) {
        OperationBinding methodBinding = this.getBindings().getOperationBinding("callFunction_FN_SET_EO_ID");
        if (this.eoNmPopUpBind.getValue() != null || this.eoTypePopUpBind.getValue() != null) {
            methodBinding.getParamsMap().put("EoNm", this.eoNmPopUpBind.getValue().toString());
            methodBinding.getParamsMap().put("EoType", (Integer) this.eoTypePopUpBind.getValue());
            Object execute = methodBinding.execute();
            this.getBindings().getOperationBinding("executeLOVCustomerVO").execute();
            if (execute.toString().equals("INSERTED")) {
                if (true) {
                    FacesMessage message =
                        new FacesMessage(ADFModelUtils.resolvRsrc("MSG.843")); //Entity Created Successfully!
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    this.eoNmPopUpBind.setValue("");
                    this.addEoPopUpBind.hide();
                } else {
                    FacesMessage message =
                        new FacesMessage(ADFModelUtils.resolvRsrc("MSG.844")); //There has been an error in inserting eo. Please try again!
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            } else {
                FacesMessage message =
                    new FacesMessage(ADFModelUtils.resolvRsrc("MSG.848")); //Duplicate Entity name found!
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(this.eoNmPopUpBind.getClientId(), message);
            }

        }

    }

    /**
     *  ActionEvent in Cancel action for addEo PopUp
     * @param actionEvent
     */
    public void addEoPopUpCancelBind(ActionEvent actionEvent) {
        this.addEoPopUpBind.hide();
    }

    /**
     * ActionEvent to open quotationTax Popup
     * @param actionEvent
     */
    public void applyTaxQuotationWiseOpenPopUpACTION(ActionEvent actionEvent) {
        System.out.println("quotation wise tax");
        OperationBinding valiItm = ADFBeanUtils.findOperation("isItemExistsForQuot");
        valiItm.execute();
        Boolean val = valiItm.getResult() == null ? false : (Boolean) valiItm.getResult();
        if (val) {
            this.showPopup(quotationTaxApplyPopUpBind, true);
        } else {
            FacesMessage message =
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                 "<html>" + "<body></br>" +
                                 "<span style='color:red;font-family:Arial;font-size:11px;font-weight:bold;'>" +
                                 ADFModelUtils.resolvRsrc("MSG.2196") + "</span></br>" +
                                 "<b style='color:blue;font-family:Arial;font-size:10px;font-weight:bold;'> " +
                                 ADFModelUtils.resolvRsrc("MSG.2197") + " </b>" + "</body>" + "</html>",
                                 null); //MSG.833:No item is added to the Quotation. Cannot save!, MSG.834 :Please add atleast one item to the quotation.
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);

        }
        /* if (((Integer)this.getBindings().getOperationBinding("checkIfQuotationWiseTaxRuleExistOrNot").execute()) == 0) {
            //this.getBindings().getOperationBinding("CreateInsert3").execute();
            //this.getBindings().getOperationBinding("calculateandSetSrNoTrForQuotationWiseTr").execute();
            this.showPopup(quotationTaxApplyPopUpBind, true);
        } else {
            this.showPopup(quotationTaxApplyPopUpBind, true);
        } */
    }

    /**
     * ActionEvent in Ok button on apply quotation wise tax
     * @param actionEvent
     */
    public void applyQuotationWiseTaxPopUpOkACTION(ActionEvent actionEvent) {
        // System.out.println("Inside");
        this.getBindings().getOperationBinding("executeTrAndTrLinesForQuotationWiseTax").execute();
        //  System.out.println("Middle");
        this.quotationTaxApplyPopUpBind.hide();
        setPopCancelShow("Y");
        // System.out.println("End");
    }

    /**
     * ActionEvent to reapply quotationWise tax
     * @param actionEvent
     */
    public void reapplyQuotTaxACTION(ActionEvent actionEvent) {
        //System.out.println("Value is "+taxRuleIdQuotationBind.getValue());
        if (taxRuleIdQuotationBind.getValue() != null) {

            OperationBinding binding =
                this.getBindings().getOperationBinding("callFunction_ForQuotationWiseTax_FN_INS_QUOTE_TR_LINE");
            binding.getParamsMap().put("taxRuleId", (Integer) taxRuleIdQuotationBind.getValue());
            binding.execute();
            setPopCancelShow("N");
            // this.getBindings().getOperationBinding("Execute5").execute();
        }

    }

    /**
     * ActionEvent to add TnC to a Quotation
     * @param actionEvent
     */
    public void addTnCACTION(ActionEvent actionEvent) {
        if (tncLOVBind.getValue() != null) {
            if (!tncLOVBind.getValue().toString().equals("")) {
                OperationBinding methodBinding = this.getBindings().getOperationBinding("isSelectedTnCAlreadyAdded");
                methodBinding.getParamsMap().put("TnCId", (Integer) tncLOVBind.getValue());
                methodBinding.execute();
                if (methodBinding.getResult().equals((Object) true)) {
                    FacesMessage message =
                        new FacesMessage(ADFModelUtils.resolvRsrc("MSG.850")); //Selected Term and Condition is already added! !
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(this.tncLOVBind.getClientId(), message);
                } else {
                    OperationBinding methodBinding1 =
                        this.getBindings().getOperationBinding("createInsertRowAndSetVarsInSlsTnC1");
                    methodBinding1.getParamsMap().put("TnCId", (Integer) tncLOVBind.getValue());
                    methodBinding1.execute();
                }

            }
        }
    }

    /**
     * ActionEvent to delete TnC to a Quotation
     * @param actionEvent
     */
    public void deleteTnCACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("Delete4").execute();
    }


    /********************************** ADD ,EDIT ,CANCEL ,DELETE Button ACTIONS for ITEM ********************************************************/

    /**
     * ActionEvent to add line to itemVo
     * @param actionEvent
     */
    public void addItemLineACTION(ActionEvent actionEvent) {
        ///isItemNmBlank
        this.itmTab.setDisclosed(true);
        if (this.getBindings().getOperationBinding("isItemNmBlank").execute().equals((Object) true)) {
            FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.852"));
            //Item name cannot be empty!
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);

        } else {
            this.getBindings().getOperationBinding("CreateInsert1").execute();
            this.itemline_form_mode = new StringBuffer("A");
            this.form_mode = new StringBuffer("E");
            this.quot_form_mode = new StringBuffer("E");
            this.getBindings().getOperationBinding("calculateandSetSrNoInSlsItm").execute();
        }
        this.getItemAddAlertPopUpBind().hide();

    }

    /**
     * ActionEvent to edit line to itemVo
     * @param actionEvent
     */
    public void editItemLineACTION(ActionEvent actionEvent) {
        this.itemline_form_mode = new StringBuffer("E");
        this.form_mode = new StringBuffer("E");
    }

    /**
     * ActionEvent to delete line to itemVo
     * @param actionEvent
     */
    public void deleteItemLineACTION(ActionEvent actionEvent) {
        //this.showPopup(deleteItemWarnPopUpBind, true);
        this.getBindings().getOperationBinding("removeItmTaxRuleandTaxRuleLines").execute();
        //this.getBindings().getOperationBinding("CheckItmId").execute();
        DCIteratorBinding binding = ADFBeanUtils.findIterator("SlsQuotItm2Iterator");
        //System.out.println("Item Id in Current row is : " + binding.getCurrentRow().getAttribute("ItmId"));
        binding.getCurrentRow().setAttribute("ItmId", -1);
        //System.out.println("Item Id in Current row is : " + binding.getCurrentRow().getAttribute("ItmId"));
        binding.getCurrentRow().remove();

        // this.getBindings().getOperationBinding("Delete1").execute();
    }

    /**
     * ActionEvent to open taxRule popup
     * @param actionEvent
     */
    public void applyItemWiseTaxPopUpOpenACTION(ActionEvent actionEvent) {

        OperationBinding valiItm = ADFBeanUtils.findOperation("isItemExistsForQuot");
        valiItm.execute();
        Boolean val = valiItm.getResult() == null ? false : (Boolean) valiItm.getResult();
        if (val) {
            if (((Integer) this.getBindings().getOperationBinding("checkIfTaxRuleExistOrNot").execute()) == 0) {
                //System.out.println("Came in if-----------------");
                this.getBindings().getOperationBinding("CreateInsert2").execute();
                this.getBindings().getOperationBinding("calculateandSetSrNoInSlsItmTr").execute();
                this.showPopup(applyItemTaxRulePopUpBind, true);
            } else {
                //System.out.println("Came in Else-----------------");
                this.showPopup(applyItemTaxRulePopUpBind, true);
            }
        } else {
            FacesMessage message =
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                 "<html>" + "<body></br>" +
                                 "<span style='color:red;font-family:Arial;font-size:11px;font-weight:bold;'>" +
                                 ADFModelUtils.resolvRsrc("MSG.2196") + "</span></br>" +
                                 "<b style='color:blue;font-family:Arial;font-size:10px;font-weight:bold;'> " +
                                 ADFModelUtils.resolvRsrc("MSG.2197") + " </b>" + "</body>" + "</html>", null);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);

        }


    }

    /**
     * ActionEvent Ok action for applying tax
     * @param actionEvent
     */
    public void applyItemWiseTaxPopUpOkACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("executeTrAndTrLines").execute();
        this.applyItemTaxRulePopUpBind.hide();
    }

    /**
     * ActionEvent Cancel action for applying tax
     * @param actionEvent
     */
    public void applyItemWiseTaxPopUpCancelACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("removeItmTaxRuleandTaxRuleLines").execute();
        this.applyItemTaxRulePopUpBind.hide();
    }

    /**
     * ActionEvent to reapply tax
     * @param actionEvent
     */
    public void reapplyTaxACTION(ActionEvent actionEvent) {
        if (taxRuleIdPopupLovBind.getValue() != null) {
            OperationBinding binding = this.getBindings().getOperationBinding("callFunction_FN_INS_QUOTE_TR_LINE");
            binding.getParamsMap().put("taxRuleId", (Integer) taxRuleIdPopupLovBind.getValue());
            binding.execute();
            this.getBindings().getOperationBinding("Execute3").execute();
        }

    }

    /**
     * ActionEvent to delete applied tax from Quotation
     * @param actionEvent
     */
    public void removeQuotationwisetaxACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("removeQuotTaxRuleandTaxRuleLines").execute();
        this.quotationTaxApplyPopUpBind.hide();
    }

    /**
     *  To enable and disable ItemPrice field
     * @return
     */
    public boolean isItmPriceDefinedForCust() {
        if (this.itmPriceFormBind.getValue() != null) {
            if (((Number) this.itmPriceFormBind.getValue()).equals(new Number(0))) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    /************************************** METHODS **********************************************************************************************/

    /**
     * For resolving the El
     */
    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    /**
     *  method to get Bindings
     */
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     * method to show or hide popup
     * @param pop
     * @param visible
     */
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

    /**
     * Method to check precision
     * @param precision
     * @param scale
     * @param total
     * @return
     */
    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    /************************************** VALUE CHANGE LISTNERS *****************************************************************************/

    /**
     * Eo Trans Value change Listner
     * @param valueChangeEvent
     */
    /* public void eoTransVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(specificCurrQuotBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(currConvBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleQuotationBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(addItemLineBtn);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pricePolicyBind);
        if (valueChangeEvent.getNewValue() != null) {
            //  System.out.println("New Employee id is :"+valueChangeEvent.getNewValue());
            OperationBinding binding = this.getBindings().getOperationBinding("insertSmanOnEoNm");
            binding.getParamsMap().put("eoNm", new StringBuffer(valueChangeEvent.getNewValue().toString()));
            binding.execute();
        }


    } */

    /**
     * discountType field VCL on Quotation form
     * @param valueChangeEvent
     */
    public void discountTypeVCL(ValueChangeEvent valueChangeEvent) {
        //getslsQuotSetCurrentRowAtt
        if (valueChangeEvent.getNewValue() != null) {
            OperationBinding methodBinding = this.getBindings().getOperationBinding("getslsQuotSetCurrentRowAtt");
            if (valueChangeEvent.getNewValue().equals("P")) {
                //_logger.severe("Setting DiscVal to null");
                methodBinding.getParamsMap().put("name", "DiscVal");
                methodBinding.getParamsMap().put("value", new Number(0));
                methodBinding.execute();
            } else if (valueChangeEvent.getNewValue().equals("A")) {
                //_logger.severe("Setting DiscPerc to null");
                methodBinding.getParamsMap().put("name", "DiscPerc");
                methodBinding.getParamsMap().put("value", new Number(0));
                methodBinding.execute();
            } else if (valueChangeEvent.getNewValue().equals("")) {
                //_logger.severe("Setting DiscVal and DiscPerc to null");
                methodBinding.getParamsMap().put("name", "DiscVal");
                methodBinding.getParamsMap().put("value", new Number(0));
                methodBinding.execute();
                methodBinding.getParamsMap().put("name", "DiscPerc");
                methodBinding.getParamsMap().put("value", new Number(0));
                methodBinding.execute();
            }
        }
    }

    /**
     * Tax rule VCL
     * @param valueChangeEvent
     */
    public void taxRuleVCL(ValueChangeEvent valueChangeEvent) {
        //  System.out.println("-------------------- is " + valueChangeEvent.getNewValue());
        OperationBinding binding = this.getBindings().getOperationBinding("callFunction_FN_INS_QUOTE_TR_LINE");
        binding.getParamsMap().put("taxRuleId", (Integer) valueChangeEvent.getNewValue());
        binding.execute();
        this.getBindings().getOperationBinding("Execute3").execute();
    }

    /**
     * Itemwise tax chkbox VCL
     * @param valueChangeEvent
     */
    public void itmTaxChkBoxVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() == (Object) false) {
            //System.out.println("IN FALSE");
            this.getBindings().getOperationBinding("removeItmTaxRuleandTaxRuleLines").execute();
        }

    }

    /**
     * SchemeId LOV VCL
     * @param valueChangeEvent
     */
    public void schemeVCL(ValueChangeEvent valueChangeEvent) {

        DCIteratorBinding parentIter = (DCIteratorBinding) getBindings().get("SlsQuotItm2Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();
        if (valueChangeEvent.getNewValue() != null && !valueChangeEvent.getNewValue().equals("")) {
            //System.out.println("Added");
            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
            this.getBindings().getOperationBinding("removeSchemeFromItem").execute();
            StringBuffer schIdVar = new StringBuffer(valueChangeEvent.getNewValue().toString());
            //  System.out.println("Scheme ID Is " + schIdVar);
            OperationBinding methodBinding = this.getBindings().getOperationBinding("callFunction_FN_INS_QUOT_SCH_ITM");
            methodBinding.getParamsMap().put("schId", schIdVar);
            methodBinding.execute();

        } else {
            //  System.out.println("removed");
            this.getBindings().getOperationBinding("removeSchemeFromItem").execute();
        }

        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmDiscTypeNew);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmDiscountVal);
        AdfFacesContext.getCurrentInstance().addPartialTarget(schemeLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmPflBind);
        //AdfFacesContext.getCurrentInstance().addPartialTarget(itmNmBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmPriceFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(quotQtyPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(toleranceQtyPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(toleranceQtyValPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmDiscTypeNew);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmDiscountVal);


        this.getBindings().getOperationBinding("Execute1").execute();

        if (parentKey != null) {
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        }


    }

    /**
     * QuotationWise taxRule LOV VCL
     * @param valueChangeEvent
     */
    public void quotationWisetaxRuleVCL(ValueChangeEvent valueChangeEvent) {


        // System.out.println("quotationWisetaxRuleVCL" + valueChangeEvent.getNewValue());
        OperationBinding binding =
            this.getBindings().getOperationBinding("callFunction_ForQuotationWiseTax_FN_INS_QUOTE_TR_LINE");
        binding.getParamsMap().put("taxRuleId", (Integer) valueChangeEvent.getNewValue());
        binding.execute();
        this.getBindings().getOperationBinding("Execute5").execute();
        setPopCancelShow("N");
        AdfFacesContext.getCurrentInstance().addPartialTarget(crossButtonBind);
    }

    /**
     * Itemwise discount change the value to zero on change of type.
     * @param valueChangeEvent
     */
    public void itmDiscTypVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().equals("P")) {
                this.itmDiscountVal.setValue(0);
            } else if (valueChangeEvent.getNewValue().equals("A")) {
                this.itmDiscountVal.setValue(0);
            }
        } else {
            this.itmDiscountVal.setValue(0);

        }

    }

    /**
     * ItmNmVCL to set price on selection of itm
     * @param valueChangeEvent
     */
    public void itmNmVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...call_function_FN_GET_EO_LATEST_PRICE
        if (valueChangeEvent.getNewValue() != null) {
            OperationBinding methodBinding =
                this.getBindings().getOperationBinding("call_function_FN_GET_EO_LATEST_PRICE");
            methodBinding.getParamsMap().put("itmNm", new StringBuffer(valueChangeEvent.getNewValue().toString()));
            Object o = methodBinding.execute();
            //System.out.println("Object returned :"+o);
            if ((Integer) o == -1) {
                FacesMessage msg =
                    new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1299"), ADFModelUtils.resolvRsrc("MSG.1733"));
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(valueChangeEvent.getComponent().getClientId(), msg);

            } else {
                valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
            }


            /* AdfFacesContext.getCurrentInstance().addPartialTarget(itmPflBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tableItmNmBind);
          //  AdfFacesContext.getCurrentInstance().addPartialTarget(tableItmIdBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmPriceFormBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmUnitBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotQtyPgBind); */
        }
    }

    /**
     * EnquiryVCL to call item insert operation through the enquiry selected
     * @param valueChangeEvent
     */
    public void enquiryNoVCL(ValueChangeEvent valueChangeEvent) {
        // System.out.println();
        if (valueChangeEvent.getNewValue() != null) {
            //System.out.println("Called");
            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());

        }

    }

    /**
     * quotTypeVCL is used to set the EnqId to null when the QuotationType is set to Direct
     * @param valueChangeEvent
     */
    public void quotTypeVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent != null) {
            OperationBinding methodBinding = this.getBindings().getOperationBinding("getslsQuotSetCurrentRowAtt");
            //if (valueChangeEvent.getNewValue().equals("D")) {
            methodBinding.getParamsMap().put("name", "EnqId");
            methodBinding.getParamsMap().put("value", null);
            methodBinding.execute();
            methodBinding.getParamsMap().put("name", "enquiryNmTrans");
            methodBinding.getParamsMap().put("value", null);
            methodBinding.execute();

            // AdfFacesContext.getCurrentInstance().addPartialTarget(this.enquiryTransBind);
            //}
        }

    }
    /*********************************************************** VALIDAOTRS *************************************************************/

    /**
     * Item id duplicate check
     * @param facesContext
     * @param uIComponent
     * @param object
     * Modified by Mousham
     */
    public void ItemIdVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("In Validator Item NAme trans::::");

        if (object == null || object == "") {
            FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1856")); //Please select an Item
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        } else {

            OperationBinding methodBinding = this.getBindings().getOperationBinding("getItemLineRowCount");
            System.out.println("Item Nm ::: " + object.toString());
            methodBinding.getParamsMap().put("itemNm", object.toString());
            methodBinding.execute();
            Integer num = (Integer) methodBinding.getResult();
            System.out.println("value of num==== in item Name Valie :" + num);
            if (num > 0) {
                FacesMessage message =
                    new FacesMessage(ADFModelUtils.resolvRsrc("MSG.853")); //Selected item is already added in quotation
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }


        }

    }

    /**
     * Validator to check negative value of fields
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void negativeNumberVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            int i = ((Number) object).compareTo((Object) new Number(0));
            if (i == -1) {
                FacesMessage message =
                    new FacesMessage(ADFModelUtils.resolvRsrc("MSG.827") + "!",
                                     ADFModelUtils.resolvRsrc("MSG.828") +
                                     "!"); //Invalid number! && Cannot enter negative value
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }

        }
    }

    /**
     * Validator to check % range
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void percentageVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Float val = new Float(object.toString());
            if (val > 100) {
                FacesMessage message =
                    new FacesMessage(ADFModelUtils.resolvRsrc("MSG.303") + "!",
                                     ADFModelUtils.resolvRsrc("MSG.854") +
                                     "!"); //Invalid percentage! && Cannot enter value greater than 99.9999999
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            } else if (val < 0) {
                FacesMessage message =
                    new FacesMessage(ADFModelUtils.resolvRsrc("MSG.303") + "!",
                                     ADFModelUtils.resolvRsrc("MSG.857") +
                                     " !"); //Invalid percentage  && Cannot enter value less than 0
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }

    /**
     * Validator to check if discount is greater than total value
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void quotDisValueVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Float val = new Float(object.toString());
            OperationBinding methodBinding = this.getBindings().getOperationBinding("isQuotDiscValueValid");
            methodBinding.getParamsMap().put("discVal", (Number) object);
            if (methodBinding.execute().equals((Object) false)) {
                FacesMessage message =
                    new FacesMessage(ADFModelUtils.resolvRsrc("MSG.859") +
                                     " !"); //Discount Value Cannot be greater than ToTal Quotation value
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            } else if (val < 0) {
                FacesMessage message =
                    new FacesMessage(ADFModelUtils.resolvRsrc("MSG.653") + "!",
                                     ADFModelUtils.resolvRsrc("MSG.857") +
                                     " !"); //Value must be greater than or equal to zero.
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }

    /**
     * Validator to check value  greater than zero and Percision
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void greaterThanZeroVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        //System.out.println("object :"+object);
        Number n = (Number) object;
        if (n.compareTo(new Number(0)) <= 0) {
            FacesMessage message =
                new FacesMessage(ADFModelUtils.resolvRsrc("MSG.198") + " !"); //Value should be greater than zero
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

    /**
     * Validator to validate itm discount
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void itmDiscountVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (itmDiscTypeNew.getValue() != null) {
            //   System.out.println("inside if of disc");
            if (object == null || ((Number) object).compareTo((Object) new Number(0)) == -1) {
                //   System.out.println("------------------------1");
                FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.198") + "!"); //Value should be greater than zero
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            } else if (itmDiscTypeNew.getValue().toString().equalsIgnoreCase("P") &&
                       ((Number) object).compareTo(99.99) > 0) {
                //    System.out.println("------------------------2");
                FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.860") + "!"); //Value cannot be greater than 99.99 in case of %
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            } else if (itmDiscTypeNew.getValue().toString().equalsIgnoreCase("A")) {
                //getItemCurrentRowAttribute
                // System.out.println("------------------------3");
                //  System.out.println("inside else of disc");
                OperationBinding methodBinding = this.getBindings().getOperationBinding("getItemCurrentRowAttribute");
                methodBinding.getParamsMap().put("attribute", new StringBuffer("ItmPrice"));
                methodBinding.execute();
                Number price = (Number) methodBinding.getResult();

                methodBinding = this.getBindings().getOperationBinding("getItemCurrentRowAttribute");
                methodBinding.getParamsMap().put("attribute", new StringBuffer("QuotQty"));
                methodBinding.execute();
                Number qty = (Number) methodBinding.getResult();


                int compareTo = 1;
                Number val = (Number) price.mul(qty);
                //     System.out.println("val=-----------in disc amnt" + val);
                try {
                    //        System.out.println("------------------------4");
                    if (val.compareTo(new Number(0)) == 1) {
                        compareTo = ((Number) price.mul(qty)).compareTo(new Number(object.toString()));
                    }

                } catch (SQLException e) {
                }
                // System.out.println("Compare :" + compareTo);
                if (compareTo <= 0) {
                    FacesMessage message =
                        new FacesMessage(ADFModelUtils.resolvRsrc("MSG.861") +
                                         " !"); //Value cannot be greater than Total Amount
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }
            }
        }

    }

    /**
     * Validator to validate addition of duplicate tnc
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void tncVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding methodBinding = this.getBindings().getOperationBinding("isSelectedTnCAlreadyAdded");
            methodBinding.getParamsMap().put("TnCId", (Integer) object);
            methodBinding.execute();
            if (methodBinding.getResult().equals((Object) true)) {
                FacesMessage message =
                    new FacesMessage(ADFModelUtils.resolvRsrc("MSG.850")); //Selected Term and Condition is already added! !
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }

    }

    /**
     * Validator to validate precision of field
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void QuantityPrecisionValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number n = (Number) object;
            if (n.compareTo(new Number(0)) <= 0) {
                FacesMessage message =
                    new FacesMessage(ADFModelUtils.resolvRsrc("MSG.198") + " !"); //Value should be greater than zero
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            } else {
                OperationBinding binding = this.getBindings().getOperationBinding("isItemTotalPrecisionOk");
                binding.getParamsMap().put("m", (Number) object);
                Object execute = binding.execute();

                if (execute.equals((Object) false)) {
                    FacesMessage message =
                        new FacesMessage(ADFModelUtils.resolvRsrc("MSG.864") + "!"); //Invalid precision
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }
            }
        }

    }

    /**
     *  Value change listener for quotation sp curr to refresh some of the fields
     * @param valueChangeEvent
     */

    public void quotSpSCurrVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.taxRuleQuotationBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.quotationPglBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.specificCurrQuotBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.itmPflBind);

    }

    /**
     * Value Change Listner to Refresh orderwise tax components
     * @param valueChangeEvent
     */
    public void taxApplicableVLC(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.taxOrderwiseBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.applyTaxBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.afterDiscountBind);
    }

    /*************************************************** CONTAINERS GETTERS AND SETTERS ****************************************************/

    public void setForm_mode(StringBuffer form_mode) {
        this.form_mode = form_mode;
    }

    public StringBuffer getForm_mode() {
        return form_mode;
    }

    public void setQuot_form_mode(StringBuffer quot_form_mode) {
        this.quot_form_mode = quot_form_mode;
    }

    public StringBuffer getQuot_form_mode() {
        return quot_form_mode;
    }

    public void setDelQuotationWarnPopUpBind(RichPopup delQuotationWarnPopUpBind) {
        this.delQuotationWarnPopUpBind = delQuotationWarnPopUpBind;
    }

    public RichPopup getDelQuotationWarnPopUpBind() {
        return delQuotationWarnPopUpBind;
    }


    public void setAddEoPopUpBind(RichPopup addEoPopUpBind) {
        this.addEoPopUpBind = addEoPopUpBind;
    }

    public RichPopup getAddEoPopUpBind() {
        return addEoPopUpBind;
    }


    public void setEoNmPopUpBind(RichInputText eoNmPopUpBind) {
        this.eoNmPopUpBind = eoNmPopUpBind;
    }

    public RichInputText getEoNmPopUpBind() {
        return eoNmPopUpBind;
    }

    public void setEoTypePopUpBind(RichSelectOneChoice eoTypePopUpBind) {
        this.eoTypePopUpBind = eoTypePopUpBind;
    }

    public RichSelectOneChoice getEoTypePopUpBind() {
        return eoTypePopUpBind;
    }

    public void setItemline_form_mode(StringBuffer itemline_form_mode) {
        this.itemline_form_mode = itemline_form_mode;
    }

    public StringBuffer getItemline_form_mode() {
        return itemline_form_mode;
    }

    public void setDeleteItemWarnPopUpBind(RichPopup deleteItemWarnPopUpBind) {
        this.deleteItemWarnPopUpBind = deleteItemWarnPopUpBind;
    }

    public RichPopup getDeleteItemWarnPopUpBind() {
        return deleteItemWarnPopUpBind;
    }


    public void setApplyItemTaxRulePopUpBind(RichPopup applyItemTaxRulePopUpBind) {
        this.applyItemTaxRulePopUpBind = applyItemTaxRulePopUpBind;
    }

    public RichPopup getApplyItemTaxRulePopUpBind() {
        return applyItemTaxRulePopUpBind;
    }


    public void setTaxRuleIdPopupLovBind(RichSelectOneChoice taxRuleIdPopupLovBind) {
        this.taxRuleIdPopupLovBind = taxRuleIdPopupLovBind;
    }

    public RichSelectOneChoice getTaxRuleIdPopupLovBind() {
        return taxRuleIdPopupLovBind;
    }


    public void setQuotationTaxApplyPopUpBind(RichPopup quotationTaxApplyPopUpBind) {
        this.quotationTaxApplyPopUpBind = quotationTaxApplyPopUpBind;
    }

    public RichPopup getQuotationTaxApplyPopUpBind() {
        return quotationTaxApplyPopUpBind;
    }


    public void itmDiscPercentVal(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...

















    }


    public void setTaxRuleIdQuotationBind(RichSelectOneChoice taxRuleIdQuotationBind) {
        this.taxRuleIdQuotationBind = taxRuleIdQuotationBind;
    }

    public RichSelectOneChoice getTaxRuleIdQuotationBind() {
        return taxRuleIdQuotationBind;
    }

    /*   public void setItmNmBind(RichInputComboboxListOfValues itmNmBind) {
        this.itmNmBind = itmNmBind;
    }

    public RichInputComboboxListOfValues getItmNmBind() {
        return itmNmBind;
    } */

    public void setItmDiscountVal(RichInputText itmDiscountVal) {
        this.itmDiscountVal = itmDiscountVal;
    }

    public RichInputText getItmDiscountVal() {
        return itmDiscountVal;
    }

    public void setItmDiscTypeNew(RichSelectOneRadio itmDiscTypeNew) {
        this.itmDiscTypeNew = itmDiscTypeNew;
    }

    public RichSelectOneRadio getItmDiscTypeNew() {
        return itmDiscTypeNew;
    }


    public void setTncLOVBind(RichSelectOneChoice tncLOVBind) {
        this.tncLOVBind = tncLOVBind;
    }

    public RichSelectOneChoice getTncLOVBind() {
        return tncLOVBind;
    }


    public void setItmPriceFormBind(RichInputText itmPriceFormBind) {
        this.itmPriceFormBind = itmPriceFormBind;
    }

    public RichInputText getItmPriceFormBind() {
        return itmPriceFormBind;
    }

    public void setCurrConvBind(RichInputText currConvBind) {
        this.currConvBind = currConvBind;
    }

    public RichInputText getCurrConvBind() {
        return currConvBind;
    }

    public void setSaveBtnBind(RichLink saveBtnBind) {
        this.saveBtnBind = saveBtnBind;
    }

    public RichLink getSaveBtnBind() {
        return saveBtnBind;
    }

    public void setItmUnitBind(RichSelectOneChoice itmUnitBind) {
        this.itmUnitBind = itmUnitBind;
    }

    public RichSelectOneChoice getItmUnitBind() {
        return itmUnitBind;
    }

    public void setTaxRuleQuotationBind(RichSelectOneRadio taxRuleQuotationBind) {
        this.taxRuleQuotationBind = taxRuleQuotationBind;
    }

    public RichSelectOneRadio getTaxRuleQuotationBind() {
        return taxRuleQuotationBind;
    }

    public void setQuotationPglBind(RichPanelFormLayout quotationPglBind) {
        this.quotationPglBind = quotationPglBind;
    }

    public RichPanelFormLayout getQuotationPglBind() {
        return quotationPglBind;
    }


    public void setItmPflBind(RichPanelFormLayout itmPflBind) {
        this.itmPflBind = itmPflBind;
    }

    public RichPanelFormLayout getItmPflBind() {
        return itmPflBind;
    }


    public void setTaxOrderwiseBind(RichPanelLabelAndMessage taxOrderwiseBind) {
        this.taxOrderwiseBind = taxOrderwiseBind;
    }

    public RichPanelLabelAndMessage getTaxOrderwiseBind() {
        return taxOrderwiseBind;
    }


    public void setAfterDiscountBind(RichSelectOneRadio afterDiscountBind) {
        this.afterDiscountBind = afterDiscountBind;
    }

    public RichSelectOneRadio getAfterDiscountBind() {
        return afterDiscountBind;
    }

    public void setApplyTaxBind(RichLink applyTaxBind) {
        this.applyTaxBind = applyTaxBind;
    }

    public RichLink getApplyTaxBind() {
        return applyTaxBind;
    }

    /**
     * Method to search if opportunity is allowed or not.
     * @return
     */
    public String getOpportunityVisibility() {
        BindingContainer bindingContainer = this.getBindings();
        if (bindingContainer != null) {
            OperationBinding binding = bindingContainer.getOperationBinding("isOpportunityUseAllowed");

            binding.execute();
            if (binding.getResult() != null) {
                //System.out.println("result : "+binding.getResult());
                return binding.getResult().toString();
            }
        } else {
            return "N";
        }
        return null;

    }
    //isToleranceUseAllowed

    /**
     * Method to search if Tolerance is allowed or not.
     * @return
     */
    public String getToleranceVisibility() {
        BindingContainer bindingContainer = this.getBindings();
        if (bindingContainer != null) {
            OperationBinding binding = bindingContainer.getOperationBinding("isToleranceUseAllowed");

            binding.execute();
            if (binding.getResult() != null) {
                //System.out.println("result : "+binding.getResult());
                return binding.getResult().toString();
            }
        } else {
            return "N";
        }
        return null;

    }


    /**
     * Validate the item tolerance quantity
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void toleranceQuantityVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            negativeNumberVAL(facesContext, uIComponent, object);
            OperationBinding binding = this.getBindings().getOperationBinding("isToleranceQuantityValid");
            binding.getParamsMap().put("val", (Number) object);
            binding.execute();
            Integer i = (Integer) binding.getResult();
            //System.out.println("Returned :"+i);
            if (i == 1) {
                FacesMessage message =
                    new FacesMessage(FacesMessage.SEVERITY_INFO, ADFModelUtils.resolvRsrc("MSG.866") + ".",
                                     null); //Tolerance Amount should be less than Item quantity
                //message.setSeverity(FacesMessage.SEVERITY_INFO);
                throw new ValidatorException(message);
            } else if (i == 2) {
                FacesMessage message =
                    new FacesMessage(FacesMessage.SEVERITY_INFO, ADFModelUtils.resolvRsrc("MSG.303"),
                                     null); //Invalid % value.
                //message.setSeverity(FacesMessage.SEVERITY_INFO);
                throw new ValidatorException(message);
            }

        }
    }

    public void itmQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number curVal = (Number) object;
            Number price = itmPriceFormBind.getValue() == null ? new Number(0) : (Number) itmPriceFormBind.getValue();
            Number toVal = new Number(0);
            toVal = curVal.multiply(price);
            toVal = ADFBeanUtils.roundOff(toVal);
            if (!ADFBeanUtils.isPrecisionValid(curVal)) {
                FacesMessage message =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1266"), //invalid precision
                                     null); //Invalid Precision
                throw new ValidatorException(message);
            } else if (!ADFBeanUtils.isPrecisionValid(toVal)) {
                FacesMessage message =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1266"),
                                     null); //Invalid Precision
                throw new ValidatorException(message);
            } else if (ADFBeanUtils.isNumberNegative(curVal)) {
                FacesMessage message =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.337"),
                                     null); //Value should be greater than zero
                throw new ValidatorException(message);
            } else if (curVal.compareTo(new Number(0)) == 0) {
                FacesMessage message =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.337"),
                                     // ADFModelUtils.resolvRsrc("Quantity must be greater than Zero."),
                                     null); //Value should be greater than zero
                throw new ValidatorException(message);
            }


        }

    }

    /**
     * Item price VALID
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void itemPriceVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            Number curVal = (Number) object;


            Number qty = quotQtyPgBind.getValue() == null ? new Number(0) : (Number) quotQtyPgBind.getValue();
            Number toVal = new Number(0);
            toVal = curVal.multiply(qty);
            toVal = ADFBeanUtils.roundOff(toVal);

            if (!ADFBeanUtils.isPrecisionValid(curVal)) {
                FacesMessage message =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1266"),
                                     null); //Invalid Precision
                throw new ValidatorException(message);
            } else if (!ADFBeanUtils.isPrecisionValid(toVal)) {
                FacesMessage message =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1266"),
                                     null); //Invalid Precision
                throw new ValidatorException(message);
            }

            else if (ADFBeanUtils.isNumberNegative(curVal)) {
                FacesMessage message =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.198"),
                                     null); //Value should be greater than zero
                throw new ValidatorException(message);
            } else if (curVal.compareTo(new Number(0)) == 0) {
                FacesMessage message =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.198"),
                                     null); //Value should be greater than zero
                throw new ValidatorException(message);
            }

        }
    }

    public void setItmTab(RichShowDetailItem itmTab) {
        this.itmTab = itmTab;
    }

    public RichShowDetailItem getItmTab() {
        return itmTab;
    }

    public void setItemAddAlertPopUpBind(RichPopup itemAddAlertPopUpBind) {
        this.itemAddAlertPopUpBind = itemAddAlertPopUpBind;
    }

    public RichPopup getItemAddAlertPopUpBind() {
        return itemAddAlertPopUpBind;
    }

    /**
     * Give alert on item add on conditional basis
     * @param actionEvent
     */
    public void addItemCheckACTION(ActionEvent actionEvent) {
        Boolean val = true;

        if ("Y".equalsIgnoreCase(getPolicyApplied().toString())) {
            System.out.println(" Policy is applied ======= ");
            OperationBinding op = ADFBeanUtils.findOperation("validationOnAddItem");
            op.execute();
            if (op != null) {
                val = (Boolean) op.getResult();
            }
        }
        if (val) {
            OperationBinding dup =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("chkTabValidations");
            dup.execute();
            dup.getResult();
            if (dup.getResult().equals(false)) {

            } else {
                OperationBinding binding = this.getBindings().getOperationBinding("chkEntutyName");
                binding.execute();
                if ((Boolean) binding.getResult()) {

                    binding = this.getBindings().getOperationBinding("chkCurrencyNameexist");
                    binding.execute();

                    if ((Boolean) binding.getResult()) {
                        binding = this.getBindings().getOperationBinding("isAlertApplicable");
                        binding.execute();
                        if ((Integer) binding.getResult() == 0) {
                            this.itemAddAlertPopUpBind.setParent(this.addItemLineBtn);
                            this.showPopup(itemAddAlertPopUpBind, true);
                        } else {
                            this.addItemLineACTION(actionEvent);
                        }
                    } else {
                        FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1731"));
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                } else {
                    FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1732"));
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            }

        } else {
            FacesMessage message = new FacesMessage("Please select Price Policy");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    /**
     * Cancel the note window.
     * @param actionEvent
     */
    public void cancelItemCheckACTION(ActionEvent actionEvent) {
        this.getItemAddAlertPopUpBind().hide();
    }

    public void okAlertACTION(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void setAddItemLineBtn(RichLink addItemLineBtn) {
        this.addItemLineBtn = addItemLineBtn;
    }

    public RichLink getAddItemLineBtn() {
        return addItemLineBtn;
    }

    /**
     * Method to perform wf operations
     * @return
     */
    public String saveAndForward() {
        //  System.out.println("In save and forward action fron line");

        Integer userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));

        OperationBinding binding = this.getBindings().getOperationBinding("getQuotationAmount");
        binding.execute();
        quotAmt = (Number) binding.getResult();

        Integer pendingAtUsr;
        pendingAtUsr = (Integer) this.getBindings().getOperationBinding("slsQuotPendingAt").execute();

        //Check if the DocID is not being attached in WF. Or if pending at current user
        WfIdForQuot = null;
        if (pendingAtUsr.compareTo(new Integer(-1)) == 0 || pendingAtUsr.compareTo(userId) == 0) {
            //getWfIdAttachedWithTheDoc
            //WfIdForQuot = this.getBindings().getOperationBinding("getWfIdAttachedWithTheDoc").execute().toString();
            Object chkwk = this.getBindings().getOperationBinding("getWfIdAttachedWithTheDoc").execute();
            if (chkwk == null) {
                return "noWorkFlowAttatch";
            } else
                WfIdForQuot = chkwk.toString();

            // System.out.println("WF_ID FOR SI : " + WfIdForQuot);

            //To get the level of current user forward to
            Integer usrLvl = -3;
            OperationBinding binding1 = this.getBindings().getOperationBinding("getUsrLvl");
            binding1.getParamsMap().put("WfId", new StringBuffer(WfIdForQuot));
            usrLvl = (Integer) binding1.execute();
            //  System.out.println("THE CURRENT USER LEVEL IS :" + usrLvl);

            if (usrLvl != -1) {
                // Insert line into wfTxn
                Integer result = -4;
                OperationBinding insTxn = this.getBindings().getOperationBinding("insIntoTxn");
                insTxn.getParamsMap().put("WfId", new StringBuffer(WfIdForQuot));
                insTxn.getParamsMap().put("level", usrLvl);
                result = (Integer) insTxn.execute();
                //System.out.println("INS TXN line inserted : " + result);

                // Commiting data
                OperationBinding execute = this.getBindings().getOperationBinding("Commit");
                execute.execute();

                if (execute.getErrors().isEmpty()) {
                    return "goToWf";
                } else {
                    String msg2 =
                        ADFModelUtils.resolvRsrc("MSG.2911") +
                        "!."; //[LBL.2911 :Error in performing commit after insTxn in saveAndFOrward method
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message2);
                    //System.out.println("Error in performing commit after insTxn");

                    return null;
                }
            } else {
                //System.out.println("Inside else part;");
                String msg2 = ADFModelUtils.resolvRsrc("MSG.1302");
                FacesMessage message = new FacesMessage(msg2);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message);
                return null;
            }
        } else {
            /*  String msg2 = "This Sales Invoice is pending at other user for approval. You can not forward this.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2); */
            return "pendingAtOtherUser";
        }

    }


    public String saveAndForwardACTION() {
        //   System.out.println("In save and forward action fron line");
        Boolean val = true;

        if ("Y".equalsIgnoreCase(getPolicyApplied().toString())) {
            System.out.println(" Policy is applied ======= ");
            OperationBinding op = ADFBeanUtils.findOperation("validationOnAddItem");
            op.execute();
            if (op != null) {
                val = (Boolean) op.getResult();
            }
        }
        if (val) {

            OperationBinding chkFlxFld = this.getBindings().getOperationBinding("isflexFieldNull");
            chkFlxFld.execute();

            if ((Boolean) chkFlxFld.getResult()) {
                ADFModelUtils.showFormattedFacesMessage("Mandatory Field(s) cannnot be Empty!",
                                                        "Please enter values for Mandatory fields in Other Parameters ", //The function for Generating Warranty returned
                                                        FacesMessage.SEVERITY_ERROR);
            } else {
                if (this.getBindings().getOperationBinding("isItemExistsForQuot").execute().equals((Object) false)) {
                    FacesMessage message =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         "<html>" + "<body></br>" +
                                         "<span style='color:red;font-family:Arial;font-size:11px;font-weight:bold;'>" +
                                         ADFModelUtils.resolvRsrc("MSG.2196") + "</span></br>" +
                                         "<b style='color:blue;font-family:Arial;font-size:10px;font-weight:bold;'> " +
                                         ADFModelUtils.resolvRsrc("MSG.2197") + " </b>" + "</body>" + "</html>", null);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }

                //to check if orderwise tax is applied correctly or not
                else if (this.getBindings().getOperationBinding("isOrderWiseAppliedCorrectly").execute().equals((Object) true)) {
                    // to check if itemwise tax is applied correctly or not
                    if (this.getBindings().getOperationBinding("isItmWiseAppliedCorrectly").execute().equals((Object) true)) {
                        // get the docId of the currentRow
                        Object execute = this.getBindings().getOperationBinding("getSlsQuotCurrRowDocId").execute();
                        StringBuffer ex = new StringBuffer(execute.toString());
                        if (amd.toString().equals("E")) {
                            this.getBindings().getOperationBinding("amendFUNCTION").execute();
                            //System.out.println("Amendment took place.");
                            amd = new StringBuffer("A");
                        }
                        /*  OperationBinding operationBinding = this.getBindings().getOperationBinding("generateQuotId");
                operationBinding.getParamsMap().put("DocId", ex.toString());
                operationBinding.execute(); */

                        OperationBinding binding = this.getBindings().getOperationBinding("Commit");
                        binding.execute();

                        if (this.quot_form_mode == new StringBuffer("A")) {
                            FacesMessage message =
                                new FacesMessage(ADFModelUtils.resolvRsrc("MSG.835")); //"Quotation created Successfully!"
                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext.getCurrentInstance().addMessage(null, message);
                            this.form_mode = new StringBuffer("V");
                            this.quot_form_mode = new StringBuffer("V");
                            //setting the docId of currentRow
                            //this.getBindings().getOperationBinding("refereshAllVoOnCommit").execute();
                            OperationBinding methodBinding =
                                this.getBindings().getOperationBinding("setCurrentRowInSlsQuoteVo");
                            methodBinding.getParamsMap().put("DocId", ex.toString());
                            methodBinding.execute();

                            StringBuffer result = new StringBuffer(saveAndForward());
                            //System.out.println("IN SAVE AND FORWARD BLOCK!"+ result);
                            if (result != null) {
                                if (result.toString().equals("pendingAtOtherUser")) {
                                    // String msg2 = resolvElDCMsg("#{bundle['MSG.1039']}");

                                    FacesMessage message2 =
                                        new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1039")); //MSG.1039:This Sales Invoice is pending at other user for approval. You cannot forward this!
                                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message2);
                                    return null;
                                } else if (result.toString().equals("noWorkFlowAttatch")) {

                                    FacesMessage message2 = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1734"));
                                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message2);
                                    return null;
                                } else {
                                    return result.toString();
                                }
                            } else {
                                return null;
                            }

                        } else {
                            FacesMessage message =
                                new FacesMessage(ADFModelUtils.resolvRsrc("MSG.836")); //"Quotation Saved Successfully!"
                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext.getCurrentInstance().addMessage(null, message);
                            this.form_mode = new StringBuffer("V");
                            this.quot_form_mode = new StringBuffer("V");
                            //setting the docId of currentRow

                            OperationBinding methodBinding =
                                this.getBindings().getOperationBinding("setCurrentRowInSlsQuoteVo");
                            methodBinding.getParamsMap().put("DocId", ex.toString());
                            methodBinding.execute();
                            // to call function FN_INS_QUOT_CALC and GEN_DISP_DOC_ID
                            methodBinding = this.getBindings().getOperationBinding("callFunction_FN_INS_QUOT_CALC");
                            methodBinding.getParamsMap().put("DocId", ex.toString());
                            methodBinding.execute();
                            this.getBindings().getOperationBinding("Commit").execute();
                            String ret = saveAndForward();


                            //  System.out.println("IN SAVE AND FORWARD BLOCK!" + ret);
                            if (ret != null) {
                                StringBuffer result = new StringBuffer(ret);
                                if (result.toString().equals("pendingAtOtherUser")) {
                                    //    String msg2 =
                                    //   "This Sales Invoice is pending at other user for approval. You cannot forward this!";
                                    FacesMessage message2 = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1735"));
                                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message2);
                                    return null;
                                } else if (result.toString().equals("noWorkFlowAttatch")) {
                                    //   String msg2 = "WorkFlow is not Defined for this Document! Please define a WorkFlow !";
                                    FacesMessage message2 = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1734"));
                                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message2);
                                    return null;
                                } else {
                                    return result.toString();
                                }
                            } else {
                                return null;
                            }

                        }

                    } else {
                        FacesMessage message =
                            new FacesMessage(ADFModelUtils.resolvRsrc("MSG.837")); //"There have been an error in saving quotation! Please try again!"
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    }

                } else {
                    FacesMessage message =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         "<html>" + "<body></br>" +
                                         "<span style='color:red;font-family:Arial;font-size:11px;font-weight:bold;'>" +
                                         ADFModelUtils.resolvRsrc("MSG.838") + "</span></br>" +
                                         "<b style='color:blue;font-family:Arial;font-size:10px;font-weight:bold;'>" +
                                         ADFModelUtils.resolvRsrc("MSG.839") + "  </b>" + "</body>" + "</html>",
                                         null); //MSG.838:Taxable amount has been changed after applying tax in one or more of the items!&& MSG.839:Please apply tax on the total taxable amount.
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
        } else {
            FacesMessage message = new FacesMessage("Please select Price Policy");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }

        return null;
        //---------------------------------------------------------------------------------------------------------------------------------------
        /*   StringBuffer result = new StringBuffer(saveAndForward());
        //System.out.println("IN SAVE AND FORWARD BLOCK!"+ result);
        if (result != null) {
            if (result.toString().equals("pendingAtOtherUser")) {
                String msg2 =
                    "This Sales Invoice is pending at other user for approval. You cannot forward this!";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                return null;
            } else {
                return result.toString();
            }
        } else {
            return null;
        } */
    }

    public void setWfIdForQuot(String WfIdForQuot) {
        this.WfIdForQuot = WfIdForQuot;
    }

    public String getWfIdForQuot() {
        return WfIdForQuot;
    }

    public void setWfReturn(String wfReturn) {
        this.wfReturn = wfReturn;
    }

    public String getWfReturn() {
        return wfReturn;
    }

    public void setQuotAmt(Number quotAmt) {
        this.quotAmt = quotAmt;
    }

    public Number getQuotAmt() {
        return quotAmt;
    }

    public void cancelACTION(ActionEvent actionEvent) {
        form_mode = new StringBuffer("V");
        quot_form_mode = new StringBuffer("V");
        this.getBindings().getOperationBinding("cancelQuotation").execute();
        this.cancelQuotPopUpBind.hide();

    }

    public void setCancelQuotPopUpBind(RichPopup cancelQuotPopUpBind) {
        this.cancelQuotPopUpBind = cancelQuotPopUpBind;
    }

    public RichPopup getCancelQuotPopUpBind() {
        return cancelQuotPopUpBind;
    }

    /**
     * To show or hhide sales Invoice Cancel Button
     * @return
     */
    public Integer getCancelButtonMode() {
        //isUserEligibleToCancelSalesInvoice
        if (cancelButtonMode == null) {
            OperationBinding binding = this.getBindings().getOperationBinding("isUserEligibleToCancelSQuotation");
            if (binding != null) {
                binding.execute();
                if (binding.getResult().equals((Object) true)) {
                    cancelButtonMode = 1;
                } else {
                    cancelButtonMode = -1;
                }
            }
        }
        // System.out.println("Returned -1");
        return cancelButtonMode;
    }

    public void amendACTION(ActionEvent actionEvent) {
        this.amd = new StringBuffer("E");
        editQuatationACTION(actionEvent);
    }

    public void setSaveAndForwardBind(RichLink saveAndForwardBind) {
        this.saveAndForwardBind = saveAndForwardBind;
    }

    public RichLink getSaveAndForwardBind() {
        return saveAndForwardBind;
    }

    public void setToAddressPgBind(RichInputText toAddressPgBind) {
        this.toAddressPgBind = toAddressPgBind;
    }

    public RichInputText getToAddressPgBind() {
        return toAddressPgBind;
    }

    public void setSubjectPgBind(RichInputText subjectPgBind) {
        this.subjectPgBind = subjectPgBind;
    }

    public RichInputText getSubjectPgBind() {
        return subjectPgBind;
    }

    public void setMessagePgBind(RichTextEditor messagePgBind) {
        this.messagePgBind = messagePgBind;
    }

    public RichTextEditor getMessagePgBind() {
        return messagePgBind;
    }

    public void sendMailAL(ActionEvent actionEvent) {
        //  System.out.println("inside AL");
        OperationBinding binding = this.getBindings().getOperationBinding("generateReport");
        binding.getParamsMap().put("quotDocId", dispDocIdPgBind.getValue().toString());
        binding.getParamsMap().put("filePath", "D:\\send\\");
        binding.execute();


        showPopup(mailPopUpPgBind, true);

    }


    public void setDispDocIdPgBind(RichInputText dispDocIdPgBind) {
        this.dispDocIdPgBind = dispDocIdPgBind;
    }

    public RichInputText getDispDocIdPgBind() {
        return dispDocIdPgBind;
    }

    public void setMailPopUpPgBind(RichPopup mailPopUpPgBind) {
        this.mailPopUpPgBind = mailPopUpPgBind;
    }

    public RichPopup getMailPopUpPgBind() {
        return mailPopUpPgBind;
    }

    public void sendReciptantMailAL(ActionEvent actionEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("sendAction");
        binding.getParamsMap().put("reciptantMailId", toAddressPgBind.getValue().toString());
        binding.getParamsMap().put("subject", subjectPgBind.getValue().toString());
        binding.getParamsMap().put("msg", messagePgBind.getValue().toString());
        binding.getParamsMap().put("dispDocId", dispDocIdPgBind.getValue().toString());
        binding.execute();

        Boolean val = (Boolean) binding.getResult();
        System.out.println(val + " The value of return function ++++++++++++++++++++++++++=");
        if (val) {
            FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1303"));
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

            toAddressPgBind.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(toAddressPgBind);
            subjectPgBind.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(subjectPgBind);
            messagePgBind.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(messagePgBind);

        } else {
            FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1304"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    public void emailValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String name = object.toString();

            String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9]+)(\\.[A-Za-z]{2,3}){1,2}$";


            CharSequence inputStr = name;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            // String msg="Email is not in Proper Format";//MSG.283
            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.283"), null));
            }
        }

    }

    public void mailPopUpCancelListener(PopupCanceledEvent popupCanceledEvent) {
        toAddressPgBind.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(toAddressPgBind);
        subjectPgBind.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(subjectPgBind);
        messagePgBind.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(messagePgBind);
    }

    public void FrieghtChargesVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

            } else {
                frightChargePgBind.setValue(null);

            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(frightChargePgBind);
        }
    }

    public void setFrightChargePgBind(RichInputText frightChargePgBind) {
        this.frightChargePgBind = frightChargePgBind;
    }

    public RichInputText getFrightChargePgBind() {
        return frightChargePgBind;
    }

    public void setDocDatePgBind(RichInputDate docDatePgBind) {
        this.docDatePgBind = docDatePgBind;
    }

    public RichInputDate getDocDatePgBind() {
        return docDatePgBind;
    }

    public void validUptoDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (docDatePgBind.getValue() != null) {
                Timestamp docDt = (Timestamp) docDatePgBind.getValue();
                Timestamp validDt = (Timestamp) object;
                Timestamp currdate = new Timestamp(System.currentTimeMillis());


                try {

                    if (validDt.dateValue().toString().compareTo(docDt.dateValue().toString()) < 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      ADFModelUtils.resolvRsrc("MSG.1305"), null));
                    } else if (validDt.dateValue().toString().compareTo(currdate.dateValue().toString()) < 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      ADFModelUtils.resolvRsrc("MSG.1736"), null));
                    }
                } catch (SQLException e) {
                }
            }
        }
    }

    public void DeliveryDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (docDatePgBind.getValue() != null) {
                Timestamp docDt = (Timestamp) docDatePgBind.getValue();
                Timestamp deliDt = (Timestamp) object;
                Timestamp currdate = new Timestamp(System.currentTimeMillis());
                Timestamp validdt = (Timestamp) validDatePgBind.getValue();


                try {

                    if (deliDt.dateValue().toString().compareTo(docDt.dateValue().toString()) < 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      ADFModelUtils.resolvRsrc("MSG.1737"), null));
                    } else if (deliDt.dateValue().toString().compareTo(currdate.dateValue().toString()) < 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      ADFModelUtils.resolvRsrc("MSG.1738"), //"Delivery Date Date should be on or after Current Date",
                                                                      null));
                    } else if (deliDt.dateValue().toString().compareTo(validdt.dateValue().toString()) > 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      ADFModelUtils.resolvRsrc("MSG.1739"), //  "Delivery Date Date should be on or before Valid Upto Date",
                                                                      null));
                    }
                } catch (SQLException e) {
                }
            }
        }
    }

    public void setValidDatePgBind(RichInputDate validDatePgBind) {
        this.validDatePgBind = validDatePgBind;
    }

    public RichInputDate getValidDatePgBind() {
        return validDatePgBind;
    }

    public void categoryNameVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        //eoNmBind.setValue(null);
        OperationBinding binding = this.getBindings().getOperationBinding("setEntityNameToNull");
        binding.execute();
    }

    public void QuotationDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (docDatePgBind.getValue() != null) {

                Timestamp quotDt = (Timestamp) object;
                Timestamp currdate = new Timestamp(System.currentTimeMillis());


                try {

                    if (quotDt.dateValue().toString().compareTo(currdate.dateValue().toString()) > 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      ADFModelUtils.resolvRsrc("MSG.521"), null));
                    }
                } catch (SQLException e) {
                }
            }
        }
    }

    public void setQuotTypeBind(RichSelectOneRadio quotTypeBind) {
        this.quotTypeBind = quotTypeBind;
    }

    public RichSelectOneRadio getQuotTypeBind() {
        return quotTypeBind;
    }

    public StringBuffer getPolicyApplied() {

        if (policyApplied == null) {
            StringBuffer i = new StringBuffer("N");
            OperationBinding binding = this.getBindings().getOperationBinding("checkPolicyApplied");
            if (binding != null) {
                binding.execute();
                Object object = binding.getResult();
                if (object != null) {
                    i = (StringBuffer) object;
                }
            }
            policyApplied = i;
        }
        System.out.println(policyApplied + " Is policy applied");
        return policyApplied;
    }

    public void setPricePolicyBind(RichPanelLabelAndMessage pricePolicyBind) {
        this.pricePolicyBind = pricePolicyBind;
    }

    public RichPanelLabelAndMessage getPricePolicyBind() {
        return pricePolicyBind;
    }

    public void setTotalAmountPriceBindVar(RichInputText totalAmountPriceBindVar) {
        this.totalAmountPriceBindVar = totalAmountPriceBindVar;
    }

    public RichInputText getTotalAmountPriceBindVar() {
        return totalAmountPriceBindVar;
    }

    public void setTotAmtValueBind(RichInputText totAmtValueBind) {
        this.totAmtValueBind = totAmtValueBind;
    }

    public RichInputText getTotAmtValueBind() {
        return totAmtValueBind;
    }

    public void setPanelformbinding(RichPanelGroupLayout panelformbinding) {
        this.panelformbinding = panelformbinding;
    }

    public RichPanelGroupLayout getPanelformbinding() {
        return panelformbinding;
    }

    public void setSpecificCurrQuotBind(RichInputListOfValues specificCurrQuotBind) {
        this.specificCurrQuotBind = specificCurrQuotBind;
    }

    public RichInputListOfValues getSpecificCurrQuotBind() {
        return specificCurrQuotBind;
    }

    public void setItmNmBind(RichInputListOfValues itmNmBind) {
        this.itmNmBind = itmNmBind;
    }

    public RichInputListOfValues getItmNmBind() {
        return itmNmBind;
    }

    public void quotQtyVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmAmtSpBind);
    }

    public void setItmAmtSpBind(RichInputText itmAmtSpBind) {
        this.itmAmtSpBind = itmAmtSpBind;
    }

    public RichInputText getItmAmtSpBind() {
        return itmAmtSpBind;
    }

    public void itmDiscVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmAmtSpBind);
    }

    public void qtyVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (vce.getNewValue() != null) {

            Number qty = (Number) vce.getNewValue();
            OperationBinding ob = executeOperation("setQtyBs");
            ob.getParamsMap().put("itmQty", qty);
            ob.execute();
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());

            this.getBindings().getOperationBinding("getAndSetDiscountForItmFromPolicy").execute();
        }
    }

    public void setUsesScheme(StringBuffer usesScheme) {
        this.usesScheme = usesScheme;
    }

    public StringBuffer getUsesScheme() {
        OperationBinding binding = this.getBindings().getOperationBinding("checkforProfileValues");
        if (binding != null && usesScheme == null) {
            binding.getParamsMap().put("colName", new StringBuffer("USE_SCHEME"));
            binding.execute();
            if (binding.getResult() != null) {
                usesScheme = (StringBuffer) binding.getResult();
            }
        }
        return usesScheme;
    }

    public void setPolicyApplied(StringBuffer policyApplied) {
        this.policyApplied = policyApplied;
    }

    public void pricePolicyVCL(ValueChangeEvent valueChangeEvent) {
        Object newValue = valueChangeEvent.getNewValue();
        if (newValue != null || !"".equals(newValue)) {
            OperationBinding policy = this.getBindings().getOperationBinding("insertPolicyEntry");
            policy.getParamsMap().put("policyId", new StringBuffer(newValue.toString()));
            policy.execute();
            Object result = policy.getResult();
            if (result.equals((Boolean) false)) {
                FacesMessage message = new FacesMessage("", ADFModelUtils.resolvRsrc("MSG.2198"));
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(valueChangeEvent.getComponent().getClientId(), message);
            }
        }
    }

    public void quotionPopupCloseAction(ActionEvent actionEvent) {
        setPopCancelShow("Y");
        quotationTaxApplyPopUpBind.hide();
    }

    public void setPopCancelShow(String PopCancelShow) {
        this.PopCancelShow = PopCancelShow;
    }

    public String getPopCancelShow() {
        return PopCancelShow;
    }

    public void setCrossButtonBind(RichLink crossButtonBind) {
        this.crossButtonBind = crossButtonBind;
    }

    public RichLink getCrossButtonBind() {
        return crossButtonBind;
    }


    /**
     *modified by Mousham
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void itemCodeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {

            OperationBinding methodBinding = this.getBindings().getOperationBinding("chkforDuplicateItemid");
            methodBinding.getParamsMap().put("ItemId", new StringBuffer(object.toString()));
            methodBinding.execute();
            Integer num = (Integer) methodBinding.getResult();
            //    System.out.println("value of num====" + num);
            if (num > 0) {
                //   System.out.println("inside item id validator------");
                FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.853")); //Selected item is already added in quotation
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }

        }


    }

    public OperationBinding executeOperation(String operation) {
        OperationBinding createParam = getBindings().getOperationBinding(operation);
        return createParam;

    }

    public void itemCodevalueChangeListner(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding methodBinding = this.getBindings().getOperationBinding("setLatestPrice");
            methodBinding.getParamsMap().put("ItmId", vce.getNewValue().toString());
            //  System.out.println("item code vcl.value ");
            methodBinding.execute();
            Object o = methodBinding.getResult();
            int value = Integer.parseInt(o.toString());

            //    System.out.println("Value is : " + value);
            if (value == -1) {
                FacesMessage msg =
                    new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1299"), ADFModelUtils.resolvRsrc("MSG.1733"));
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(vce.getComponent().getClientId(), msg);

            }
            /*    AdfFacesContext.getCurrentInstance().addPartialTarget(itmPflBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tableItmNmBind);
           // AdfFacesContext.getCurrentInstance().addPartialTarget(tableItmIdBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmPriceFormBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.itmUnitBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotQtyPgBind); */
        }
    }

    public List getSuggestions(String input) {

        // create a new list to hold matching items
        List<SelectItem> items = new ArrayList<SelectItem>();
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getSuggestedItemDesc");
        binding.getParamsMap().put("itmStr", input);
        binding.execute();
        Object object = binding.getResult();
        //  System.out.println("Item Query Executed");
        if (object != null) {
            for (String item : (ArrayList<String>) object) {
                items.add(new SelectItem(item));
            }
        }


        // return the matching items
        return items;
    }

    public StringBuffer getShowItmDesc() {
        //  System.out.println("ShowItmDesc: " + showItmDesc);
        if (showItmDesc == null) {
            StringBuffer i = new StringBuffer("N");
            OperationBinding binding = this.getBindings().getOperationBinding("checkforProfileValues");
            binding.getParamsMap().put("colName", new StringBuffer("ENT_ITM_ID_IN_SO"));
            if (binding != null) {
                binding.execute();
                Object object = binding.getResult();
                if (object != null) {
                    i = (StringBuffer) object;
                }
                if (i.toString().equals("Y")) {
                    showItmDesc = new StringBuffer("Y");
                } else {
                    showItmDesc = new StringBuffer("N");
                }
            }

        }
        return showItmDesc;
    }

    public void setSchemeLinkPgBind(RichLink schemeLinkPgBind) {
        this.schemeLinkPgBind = schemeLinkPgBind;
    }

    public RichLink getSchemeLinkPgBind() {
        return schemeLinkPgBind;
    }

    public void setQuotQtyPgBind(RichInputText quotQtyPgBind) {
        this.quotQtyPgBind = quotQtyPgBind;
    }

    public RichInputText getQuotQtyPgBind() {
        return quotQtyPgBind;
    }

    public void setToleranceQtyPgBind(RichSelectOneRadio toleranceQtyPgBind) {
        this.toleranceQtyPgBind = toleranceQtyPgBind;
    }

    public RichSelectOneRadio getToleranceQtyPgBind() {
        return toleranceQtyPgBind;
    }

    public void setToleranceQtyValPgBind(RichInputText toleranceQtyValPgBind) {
        this.toleranceQtyValPgBind = toleranceQtyValPgBind;
    }

    public RichInputText getToleranceQtyValPgBind() {
        return toleranceQtyValPgBind;
    }

    public void CalculationTabDL(DisclosureEvent disclosureEvent) {
        this.getBindings().getOperationBinding("Execute7").execute();
    }

    public void setTableItmNmBind(RichOutputText tableItmNmBind) {
        this.tableItmNmBind = tableItmNmBind;
    }

    public RichOutputText getTableItmNmBind() {
        return tableItmNmBind;
    }

    public void setTableItmIdBind(RichOutputText tableItmIdBind) {
        this.tableItmIdBind = tableItmIdBind;
    }

    public RichOutputText getTableItmIdBind() {
        return tableItmIdBind;
    }

    public String setEoNmBindVarToNull() {
        //eoNmBind = null;
        return "finishTask";
    }

    /*   public void setEoNmBind(RichInputComboboxListOfValues eoNmBind) {
        this.eoNmBind = eoNmBind;
    }

    public RichInputComboboxListOfValues getEoNmBind() {
        return eoNmBind;
    } */

    public void abcd(ActionEvent actionEvent) {
        //    System.out.println("Helllloooo");
    }

    public List getCustomerSuggestions(String input) {

        // create a new list to hold matching items
        List<SelectItem> items = new ArrayList<SelectItem>();
        OperationBinding binding = getBindings().getOperationBinding("getCustomerSuggestions");
        binding.getParamsMap().put("custNm", input);
        binding.execute();
        Object object = binding.getResult();
        // System.out.println("Item Query Executed");
        if (object != null) {
            for (String item : (ArrayList<String>) object) {
                items.add(new SelectItem(item));
            }
        }
        return items;
    }

    public void eoTransVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(specificCurrQuotBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(currConvBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleQuotationBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(addItemLineBtn);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pricePolicyBind);
        if (valueChangeEvent.getNewValue() != null) {
            //  System.out.println("New Employee id is :"+valueChangeEvent.getNewValue());
            OperationBinding binding = this.getBindings().getOperationBinding("insertSmanOnEoNm");
            binding.getParamsMap().put("eoNm", new StringBuffer(valueChangeEvent.getNewValue().toString()));
            binding.execute();
        }

    }

    /*  public void setEoNmBind(RichInputListOfValues eoNmBind) {
        this.eoNmBind = eoNmBind;
    }

    public RichInputListOfValues getEoNmBind() {
        return eoNmBind;
    }

    public void setEnquiryTransBind(RichInputListOfValues enquiryTransBind) {
        this.enquiryTransBind = enquiryTransBind;
    }

    public RichInputListOfValues getEnquiryTransBind() {
        return enquiryTransBind;
    } */

    public void showTaxPopUp(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("executeTrAndTrLines").execute();
        showPopup(showTaxPopBind, true);
    }

    public void setShowTaxPopBind(RichPopup showTaxPopBind) {
        this.showTaxPopBind = showTaxPopBind;
    }

    public RichPopup getShowTaxPopBind() {
        return showTaxPopBind;
    }

    public void addOtherDetailsAL(ActionEvent actionEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("insertOtherDetails");
        binding.execute();

    }


    public void createQuotFromOpporACE(ActionEvent actionEvent) {
        OperationBinding methodBinding = this.getBindings().getOperationBinding("setItemsOnTheBasisOfEnquiry");
        methodBinding.getParamsMap().put("EnqNo", "");
        methodBinding.execute();
        this.quot_form_mode = new StringBuffer("E");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.currConvBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.saveBtnBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.saveAndForwardBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.taxRuleQuotationBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.quotationPglBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.specificCurrQuotBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.itmPflBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.quotTypeBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.totalAmountPriceBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.totAmtValueBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.panelformbinding);
    }
}
