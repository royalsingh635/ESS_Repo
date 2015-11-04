package slssalesinvoiceapp.view.bean;


import java.io.Serializable;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DropEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.ValidationException;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import slssalesinvoiceapp.model.module.service.SlsSalesInvoiceAppAMImpl;


public class SalesInvoiceAddBean implements Serializable {
    @SuppressWarnings("compatibility:91326906531890025")
    private static final long serialVersionUID = -2983828073976568265L;
    private boolean addButtonMode = false;
    private RichPopup discountPopup;
    private RichInputListOfValues shipmentBinding;
    private RichInputText taxableAmountBindVar;
    private RichTable taxRuleLineTbl;
    private RichPopup taxInvoicewisePopup;
    private RichPopup otherChargePopup;
    private RichSelectOneChoice taxRuleIdBindVar;
    private Integer amt_digit = Integer.parseInt(resolvElAmt("#{pageFlowScope.GLBL_AMT_DIGIT}").toString());
    private RichInputText totalPaymentAMount;
    private RichInputDate paymentDateBindVar;
    private RichTable paymentScheduleTable;
    private RichInputText paymentAmount;
    private RichPopup itemPopup;
    private RichPopup taxItemwisePopup;
    private RichSelectOneChoice taxRuleIdItem;
    private RichTable taxRuleLineItemTbl;
    private RichInputText taxableAmountForItem;
    private RichTable invDtlTableBinding;
    private RichInputText taxAmountBindvar;
    private RichSelectOneChoice invoiceTypeBindVar;
    private RichInputComboboxListOfValues customerNmBindVar;
    private RichInputComboboxListOfValues currencyBindVar;
    private RichInputText rateBindVar;
    private RichTable ocTablebindvar;
    private RichTable tncTableBindVar;
    private RichPanelGroupLayout footerPglBind;
    private RichPanelTabbed detailsPtlBind;
    private RichInputListOfValues shipIdBind;
    private String WfIdForSI;
    private String wfReturn;

    private String adjMode;
    private RichPopup currConvChkPopUpBind;
    private RichInputText adjAmt;
    private RichTable adjTabBind;
    private RichInputListOfValues intmNoBind;
    private RichPopup intimationWarningPopUpBind;
    private RichPopup cancelPopBind;
    private RichCommandImageLink addShipmntButtonBind;
    private RichPanelFormLayout otherChargerPgBind;
    private RichSelectOneRadio currConvTransPgBind;
    /**
     * Mode V = view
     * Mode A = edit
     */
    private StringBuffer invSelectionMode = new StringBuffer("A");
    private BigDecimal suppNewVal = new BigDecimal(0);
    private RichSelectOneRadio suppliOperCbBind;
    private RichSelectOneRadio suppliTypeCbBind;
    private RichInputText transAddBind;

    /* /*
     * Total disable mode 'Y' for total disable and 'N' for vice versa
     *
    private String totmode = "A"; */

    public SalesInvoiceAddBean() {
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    private String mode = "V";
    private String shipMode = "V";

    public String CreateInvoice() {
        OperationBinding binding = this.getBindings().getOperationBinding("isFinancialYearValid");
        binding.execute();
        Object object = binding.getResult();
        if(object.equals((Object)true)){
            this.getBinding("resetSuppliTabs").execute();
            OperationBinding create = getBinding("Createwithparameters");
            create.execute();

            if (create.getErrors().isEmpty()) {
                invSelectionMode = new StringBuffer("A");
                mode = "A";
            } else {
                // getMessage("Faces", "Error while creating.Contact ESS !!!", "E");
                getMessage("Faces", resolvElDCMsg("#{bundle['MSG.1013']}").toString(), "E");
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(detailsPtlBind);
        }
        
        return null;
    }

    public String CreateInvoiceFromSearchPage() {
        mode = "A";
        return null;
    }


    public String EditAction() {
        Integer userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        Integer pendingAtUsr;
        pendingAtUsr = (Integer)this.getBindings().getOperationBinding("slsInvoicePendingAt").execute();
        System.out.println("Pending at :" + pendingAtUsr + " User logged in is :" + userId);
        if (pendingAtUsr.equals(userId) || pendingAtUsr.equals(-1)) {
            mode = "E";
            AdfFacesContext.getCurrentInstance().addPartialTarget(detailsPtlBind);
            return null;
        } else {

            /*  String msg2 ="<html>\n" +
                "<body><table><tr><td></td></tr></table>" +
                "<span style='color:red;font-family:Arial;font-size:11px;font-weight:bold;'>Sales Invoice is pending at another user !</span></br>\n" +
                "<span style='color:blue;font-family:Arial;font-size:10px;font-weight:bold;'>You cannot edit this sales invoice as it is pending at another user in WorkFlow.</span>\n" +
                "</body>" +
                "</html>";*/
            String msg2 = "<html>\n" +
                "<body><table><tr><td></td></tr></table>" +
                "<span style='color:red;font-family:Arial;font-size:11px;font-weight:bold;'>" +
                resolvElDCMsg("#{bundle['MSG.1014']}").toString() + "</span></br>\n" +
                "<span style='color:blue;font-family:Arial;font-size:10px;font-weight:bold;'>" +
                resolvElDCMsg("#{bundle['MSG.1015']}").toString() + "</span>\n" +
                "</body>" + "</html>";

            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            return null;
        }
        /* StringBuffer result = new StringBuffer(saveAndForward());
        System.out.println("IN SAVE AND FORWARD BLOCK!"+ result);
        if (result != null) {
            if (result.toString().equals("pendingAtOtherUser")) {
                String msg2 =
                    "This Sales Invoice is pending at other user for approval. You cannot edit this!";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                return null;
            } else {
                mode = "E";
                AdfFacesContext.getCurrentInstance().addPartialTarget(detailsPtlBind);
                return null;
            }
        } else {
            mode = "E";
            AdfFacesContext.getCurrentInstance().addPartialTarget(detailsPtlBind);
            return null;
        } */

    }

    public String CancelAction() {
        OperationBinding cancel = getBinding("Rollback");
        cancel.execute();
        if (cancel.getErrors().isEmpty()) {
            mode = "V";
            shipMode = "V";
            this.getBinding("resetSuppliTabs").execute();
        } else {
            getMessage("Faces", "Error while cancelling records..Contact ESS !!!", "E");
        }
        return "back";

    }

    public String CreateShipment() {

        if (invoiceTypeBindVar.getValue() == null) {

            // String msg1 = "Invoice Type is required";
            String msg1 = resolvElDCMsg("#{bundle['MSG.1016']}").toString();
            // String msg2 = "Please Select Invoice Type";
            String msg2 = resolvElDCMsg("#{bundle['MSG.1017']}").toString();
            FacesMessage errMsg = new FacesMessage(msg1);
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            errMsg.setDetail(msg2);
            FacesContext.getCurrentInstance().addMessage(invoiceTypeBindVar.getClientId(), errMsg);
        } else if (customerNmBindVar.getValue() == null) {
            // String msg1 = "Customer Name is required";
            String msg1 = resolvElDCMsg("#{bundle['MSG.1018']}").toString();
            // String msg2 = "Please Select Customer Name";
            String msg2 = resolvElDCMsg("#{bundle['MSG.1019']}").toString();
            FacesMessage errMsg = new FacesMessage(msg1);
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            errMsg.setDetail(msg2);
            FacesContext.getCurrentInstance().addMessage(customerNmBindVar.getClientId(), errMsg);
        } else if (currencyBindVar.getValue() == null) {
            // String msg1 = "Currency is required";
            String msg1 = resolvElDCMsg("#{bundle['MSG.21']}").toString();
            // String msg2 = "Please Select Currency";
            String msg2 = resolvElDCMsg("#{bundle['MSG.1020']}").toString();
            FacesMessage errMsg = new FacesMessage(msg1);
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            errMsg.setDetail(msg2);
            FacesContext.getCurrentInstance().addMessage(currencyBindVar.getClientId(), errMsg);
        } else if (rateBindVar.getValue() == null) {
            // String msg1 = "Currency Rate is required";
            String msg1 = resolvElDCMsg("#{bundle['MSG.1021']}").toString();
            // String msg2 = "Please Select Currency Rate";
            String msg2 = resolvElDCMsg("#{bundle['MSG.1022']}").toString();
            FacesMessage errMsg = new FacesMessage(msg1);
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            errMsg.setDetail(msg2);
            FacesContext.getCurrentInstance().addMessage(rateBindVar.getClientId(), errMsg);
        } else {
            OperationBinding getMaxShipSrNo = getBinding("getMaxShipSrNo");
            Integer SrNo = (Integer)getMaxShipSrNo.execute();

            if (SrNo == 1) {
                OperationBinding createShpment = getBinding("Createwithparameters1");
                createShpment.getParamsMap().put("SrNo", SrNo);
                createShpment.execute();

                if (createShpment.getErrors().isEmpty()) {
                    shipMode = "A";

                }


            } else {
                if (shipmentBinding.getValue() == null) {
                    getMessage("Faces", resolvElDCMsg("#{bundle['MSG.1023']}").toString(), "E");
                } else {
                    OperationBinding createShpment = getBinding("Createwithparameters1");
                    createShpment.getParamsMap().put("SrNo", SrNo);
                    createShpment.execute();

                    if (createShpment.getErrors().isEmpty()) {
                        shipMode = "A";
                    }


                }
            }
        }

        return null;
    }

    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    public String DeleteShipment() {
        getBinding("deleteShpDtl").execute();
        //System.out.println("_________________ 8");

        AdfFacesContext.getCurrentInstance().addPartialTarget(invDtlTableBinding);
        SlsSalesInvoiceAppAMImpl am = (SlsSalesInvoiceAppAMImpl)resolvElDC("SlsSalesInvoiceAppAMDataControl");


        //this.getBindings().getOperationBinding("Delete1").execute();

        System.out.println("_________________ 9");

        am.getDBTransaction().postChanges();
        am.getSlsInvDtl1().executeQuery();

        DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("SlsInvShipItmIterator");
        parentIter.executeQuery();
        System.out.println("_________________ 10");

        //getBinding("Execute1").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(invDtlTableBinding);
        return null;
    }

    public String DiscountLink() {
        showPopup(discountPopup, true);
        return null;
    }


    public void RateChangeListner(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            BigDecimal rate = (BigDecimal)vce.getNewValue();
            rate = rate.setScale(amt_digit, RoundingMode.HALF_UP);
            OperationBinding setBaseAmount = getBinding("setBaseAmount");
            setBaseAmount.getParamsMap().put("Rate", rate);
            setBaseAmount.execute();
        }
    }

    public String InvoicewiseTaxAction() {
        /*  OperationBinding taxRowCount = getBinding("invoiceWiseTaxValue");
        Integer value = (Integer)taxRowCount.execute();
        if (value == 0) {
            BigDecimal taxableAmount = new BigDecimal(0);
            if (taxableAmountBindVar.getValue() != null) {
                taxableAmount = (BigDecimal)taxableAmountBindVar.getValue();
                taxableAmount = taxableAmount.setScale(amt_digit, RoundingMode.HALF_UP);
            }
            OperationBinding create = getBinding("Createwithparameters2");
            create.getParamsMap().put("TaxableAmt", taxableAmount);
            create.getParamsMap().put("SrNoDest", "0");
            create.getParamsMap().put("SrNo", 1);
            create.getParamsMap().put("ShipId", 0);
            create.getParamsMap().put("ItmId", 0);

            create.execute();
            if (create.getErrors().isEmpty()) {
                showPopup(taxInvoicewisePopup, true);
            }
        } else { */
        this.getBindings().getOperationBinding("setTaxRuleInTransient").execute();
        showPopup(taxInvoicewisePopup, true);
        
        //}
        return null;
    }

    public void taxRuleInvoicewiseChangeListner(ValueChangeEvent vce) {
        if (vce.getNewValue() != null && taxableAmountBindVar.getValue() != null) {
            StringBuilder d = (vce.getNewValue() == "" ? new StringBuilder("") : new StringBuilder(vce.getNewValue().toString()));
            if(!"".equals(d.toString())){
            BigDecimal taxableAmount = (BigDecimal)taxableAmountBindVar.getValue();
            taxableAmount = taxableAmount.setScale(amt_digit, RoundingMode.HALF_UP);
            OperationBinding create = getBinding("insertIntoSlsInvTrLineForInvoicewise");
            create.getParamsMap().put("p_tax_rule_id", vce.getNewValue());
            create.getParamsMap().put("p_taxable_amount", taxableAmount);
            create.execute();
            }
        }
    }

    public String CreateOtherCharges() {

        OperationBinding chk = getBinding("chkInvOcEnteredOrNot");
        chk.execute();

        if (Integer.parseInt(chk.getResult().toString()) == 0) {
            OperationBinding create = getBinding("Createwithparameters3");
            create.execute();

            AdfFacesContext.getCurrentInstance().addPartialTarget(otherChargerPgBind);

            OperationBinding setCurrOc = getBinding("setCurr_OC");
            setCurrOc.execute();
        } else {
            String msg2 = "<html>\n" +
                "<body><table><tr><td></td></tr></table>" +
                "<span style='color:red;font-family:Arial;font-size:11px;font-weight:bold;'>" +
                "Please enter mandatory fields" + "</span></br>\n" +
                "</body>" + "</html>";

            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        }
        return null;
    }

    public String otherChargeLink() {
        showPopup(otherChargePopup, true);
        return null;
    }

    public void InvoicewiseTaxDialogListner(DialogEvent dialogEvent) {
        //getBinding("setTaxAmount").execute();
    }

    public void DragEndListner(DropEvent dropEvent) {

    }

    public DnDAction dropListner(DropEvent dropEvent) {

        if ("E".equals(mode) || "A".equals(mode)) {

            DCBindingContainer bc = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dcib = bc.findIteratorBinding("TnCViewIterator");
            Row currentRow = dcib.getCurrentRow();

            OperationBinding ob = getBinding("insertIntoTnc");
            ob.getParamsMap().put("CurrRow_Tnc", currentRow);
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tncTableBindVar);
        }
        return DnDAction.NONE;
    }

    public String deleteTnc() {
        getBinding("Delete").execute();
        getBinding("Execute").execute();
        return null;
    }

    public void shipmentNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding NmVal = getBinding("shipmentNameValidation");
            NmVal.getParamsMap().put("ShipmentName", object.toString());
            Object retval = NmVal.execute();
            if (retval != null) {
                if ("Y".equals(retval)) {
                    //String msg2 = "Duplicate Shipment.";
                    String msg2 = resolvElDCMsg("#{bundle['MSG.1024']}").toString();
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                }
            }

        }
    }

    public String reApplyTax() {
        if (taxRuleIdBindVar.getValue() != null && taxableAmountBindVar.getValue() != null) {
            StringBuilder d = (taxRuleIdBindVar.getValue() == "" ? new StringBuilder("") : new StringBuilder(taxRuleIdBindVar.getValue().toString()));
            if(!"".equals(d.toString())){
                BigDecimal taxableAmount = (BigDecimal)taxableAmountBindVar.getValue();
                taxableAmount = taxableAmount.setScale(amt_digit, RoundingMode.HALF_UP);
                OperationBinding create = getBinding("insertIntoSlsInvTrLineForInvoicewise");
                create.getParamsMap().put("p_tax_rule_id", taxRuleIdBindVar.getValue());
                create.getParamsMap().put("p_taxable_amount", taxableAmount);
                create.execute();    
            }    
        }
        //AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLineTbl);
        return null;
    }


    public String reApplyTaxforItem() {

        if (taxRuleIdItem.getValue() != null && taxableAmountForItem.getValue() != null) {
            BigDecimal taxableAmount = (BigDecimal)taxableAmountForItem.getValue();
            taxableAmount = taxableAmount.setScale(amt_digit, RoundingMode.HALF_UP);
            OperationBinding create = getBinding("insertIntoSlsInvTrLineForItemwise");
            create.getParamsMap().put("p_tax_rule_id", taxRuleIdItem.getValue());
            create.getParamsMap().put("p_taxable_amount", taxableAmount);
            create.execute();
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLineItemTbl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxAmountBindvar);

        return null;
    }

    public String createPaymentSchedule() {

        if (paymentDateBindVar.getValue() == null) {

            // String msg2 = "Please specify Payment Date.";
            String msg2 = resolvElDCMsg("#{bundle['MSG.423']}").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message2);

        } else if (paymentAmount.getValue() == null) {

            // String msg2 = "Please specify Payment amount.";
            String msg2 = resolvElDCMsg("#{bundle['MSG.422']}").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message2);

        } else {
            if (totalPaymentAMount.getValue() != null && paymentDateBindVar.getValue() != null) {

                Timestamp date = (Timestamp)paymentDateBindVar.getValue();
                OperationBinding ob = getBinding("paymentDateValidation");
                ob.getParamsMap().put("paydt", date);
                Object ret = ob.execute();
                if (ret != null) {
                    String flg = (String)ret;

                    if ("Y".equalsIgnoreCase(flg)) {
                        //  String msg2 = "Duplicate Payment Date.";
                        String msg2 = resolvElDCMsg("#{bundle['MSG.1025']}").toString();
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, message2);

                    } else {
                        BigDecimal totalPayAmt = (BigDecimal)totalPaymentAMount.getValue();
                        OperationBinding donePaySchdlAction = getBinding("donePaySchdlAction");
                        donePaySchdlAction.getParamsMap().put("totAmt",
                                                              totalPayAmt.setScale(amt_digit, RoundingMode.HALF_UP));
                        donePaySchdlAction.execute();
                        AdfFacesContext.getCurrentInstance().addPartialTarget(paymentScheduleTable);
                    }
                }


            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(paymentScheduleTable);

        return null;
    }

    public void getMessage(String Type, String MessageValue, String ser) {
        FacesMessage msg = new FacesMessage(MessageValue);
        if (Type.equalsIgnoreCase("Faces")) {
            if (ser.equalsIgnoreCase("E")) {
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, msg);
                return;
            } else if (ser.equalsIgnoreCase("I")) {
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, msg);
                return;
            } else if (ser.equalsIgnoreCase("W")) {
                msg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, msg);
                return;
            } else if (ser.equalsIgnoreCase("F")) {
                msg.setSeverity(FacesMessage.SEVERITY_FATAL);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, msg);
                return;
            } else {
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, msg);
                return;
            }
        }

        else {
            throw new ValidatorException(msg);

        }
    }

    public OperationBinding getBinding(String BindingVal) {
        OperationBinding operationBinding =
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding(BindingVal);

        return operationBinding;
    }

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

    public Object resolvElAmt(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object digit = 2;
        if (valueExp != null) {
            digit = valueExp.getValue(elContext);
        }
        if (digit == null) {
            digit = 2;
        }

        return digit;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }


    public void setShipMode(String shipMode) {
        this.shipMode = shipMode;
    }

    public String getShipMode() {
        return shipMode;
    }


    public void setDiscountPopup(RichPopup discountPopup) {
        this.discountPopup = discountPopup;
    }

    public RichPopup getDiscountPopup() {
        return discountPopup;
    }

    public void setShipmentBinding(RichInputListOfValues shipmentBinding) {
        this.shipmentBinding = shipmentBinding;
    }

    public RichInputListOfValues getShipmentBinding() {
        return shipmentBinding;
    }


    public void setTaxableAmountBindVar(RichInputText taxableAmountBindVar) {
        this.taxableAmountBindVar = taxableAmountBindVar;
    }

    public RichInputText getTaxableAmountBindVar() {
        return taxableAmountBindVar;
    }


    public void setTaxRuleLineTbl(RichTable taxRuleLineTbl) {
        this.taxRuleLineTbl = taxRuleLineTbl;
    }

    public RichTable getTaxRuleLineTbl() {
        return taxRuleLineTbl;
    }

    public void setTaxInvoicewisePopup(RichPopup taxInvoicewisePopup) {
        this.taxInvoicewisePopup = taxInvoicewisePopup;
    }

    public RichPopup getTaxInvoicewisePopup() {
        return taxInvoicewisePopup;
    }


    public void setOtherChargePopup(RichPopup otherChargePopup) {
        this.otherChargePopup = otherChargePopup;
    }

    public RichPopup getOtherChargePopup() {
        return otherChargePopup;
    }


    public void setTaxRuleIdBindVar(RichSelectOneChoice taxRuleIdBindVar) {
        this.taxRuleIdBindVar = taxRuleIdBindVar;
    }

    public RichSelectOneChoice getTaxRuleIdBindVar() {
        return taxRuleIdBindVar;
    }


    public void setTotalPaymentAMount(RichInputText totalPaymentAMount) {
        this.totalPaymentAMount = totalPaymentAMount;
    }

    public RichInputText getTotalPaymentAMount() {
        return totalPaymentAMount;
    }


    public void setPaymentDateBindVar(RichInputDate paymentDateBindVar) {
        this.paymentDateBindVar = paymentDateBindVar;
    }

    public RichInputDate getPaymentDateBindVar() {
        return paymentDateBindVar;
    }

    public void setPaymentScheduleTable(RichTable paymentScheduleTable) {
        this.paymentScheduleTable = paymentScheduleTable;
    }

    public RichTable getPaymentScheduleTable() {
        return paymentScheduleTable;
    }

    public void setPaymentAmount(RichInputText paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public RichInputText getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Validate the discount Amount
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void discountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            BigDecimal DiscVal = (BigDecimal)object;
            OperationBinding bindings = getBinding("isDiscountValueValid");
            bindings.getParamsMap().put("val", DiscVal);
            bindings.execute();
            if ((Integer)bindings.getResult() == 1) {
                // FacesMessage message = new FacesMessage("Larger Amount!","Discount Amount cannot be greater than Amount After Tax!");
                FacesMessage message =
                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.1026']}").toString(), resolvElDCMsg("#{bundle['MSG.1027']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            } else if ((Integer)bindings.getResult() == 2) {
                //FacesMessage message = new FacesMessage("Negative Amount!","Negative Amount not Allowed!");
                FacesMessage message = new FacesMessage("Invalid Percentage", "Discount cannot be more than 99.99%");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            } else {
                negativeNumberVAL(facesContext, uIComponent, object);
            }
        }
    }

    public String deleteOtherCharges() {
        getBinding("Delete2").execute();
        getBinding("Execute2").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(ocTablebindvar);
        return null;
    }

    public void OtherChargesDialogListner(DialogEvent de) {

        //        if (de.getOutcome().name().equals("ok")) {
        //            OperationBinding binding1 = this.getBinding("isOtherChargesAmountValid");
        //            binding1.execute();
        //                if(binding1.getResult().equals((Object)false)){
        //                    getMessage("Faces", "<html>\n" +
        //                        "<body><table><tr><td></td></tr></table>" +
        //                        "<span style='color:red;font-family:Arial;font-size:11px;font-weight:bold;'>"+"Other charges should not be debited more than Invoice Amount !"+"</span></br>\n" +
        //                        "<span style='color:blue;font-family:Arial;font-size:10px;font-weight:bold;'>Please apply other charges properly</span>\n" +
        //                        "</body>" +
        //                        "</html>", "E");
        //                }
        getBinding("Execute2").execute();
        // }
    }

    public String removeTaxAction() {
        getBinding("removeTaxInvoiceWise").execute();
        getBinding("setTaxAmount").execute();
        taxInvoicewisePopup.hide();
        return null;
    }

    public String deletePaymentSchedule() {
        getBinding("Delete3").execute();
        getBinding("Execute3").execute();
        return null;
    }

    public void shipmentChangeListner(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding getShipmentId = getBinding("getShipmentId");
            getShipmentId.getParamsMap().put("ShipmentDispId", vce.getNewValue());
            Object ship = getShipmentId.execute();

            if (ship != null) {
                OperationBinding setShpItm = getBinding("setInvShpItm");
                setShpItm.getParamsMap().put("P_ship_ID", ship);
                String ret = "N";
                Object retObj = setShpItm.execute();
                if (retObj != null) {

                    ret = (String)retObj;

                    OperationBinding setTaxRuleFlg = getBinding("setTaxRuleFlg");
                    setTaxRuleFlg.getParamsMap().put("ShpId", ship);
                    setTaxRuleFlg.execute();

                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(invDtlTableBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(taxAmountBindvar);
                if (ret.equals("N")) {
                    //getBinding("Delete1").execute();
                    getBinding("Execute1").execute();
                    //   String msg2 = "Error in inserting data in Shipment Item.";
                    String msg2 = resolvElDCMsg("#{bundle['MSG.1028']}").toString();
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, message2);
                }
            }


        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxAmountBindvar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(footerPglBind);
    }

    public String viewItemDtl() {
        //#{bindings.SlsInvDtl.collectionModel.makeCurrent}
        showPopup(itemPopup, true);
        return null;
    }

    public String ItemwiseTaxAction() {
        getBinding("checkTrforItem").execute();
        showPopup(taxItemwisePopup, true);
        return null;
    }

    public String removeItemTaxAction() {
        getBinding("removeTaxItemWise").execute();
        //getBinding("setTaxAmount").execute();
        taxItemwisePopup.hide();
        return null;
    }

    public void taxRuleItemwiseChangeListner(ValueChangeEvent vce) {
        if (vce.getNewValue() != null && taxableAmountForItem.getValue() != null) {

            BigDecimal taxableAmount = (BigDecimal)taxableAmountForItem.getValue();
            taxableAmount = taxableAmount.setScale(amt_digit, RoundingMode.HALF_UP);
            OperationBinding create = getBinding("insertIntoSlsInvTrLineForItemwise");
            create.getParamsMap().put("p_tax_rule_id", vce.getNewValue());
            create.getParamsMap().put("p_taxable_amount", taxableAmount);
            create.execute();


        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLineItemTbl);


    }


    public void taxItmChkboxChangeLstner(ValueChangeEvent vce) {
        //System.out.println(vce.getNewValue());
        if (vce.getNewValue() != null) {
            //System.out.println("VCL Value :"+vce.getNewValue());
            if (vce.getNewValue() == Boolean.FALSE) {
                getBinding("removeTaxItemWise").execute();
                //getBinding("setTaxAmount").execute();
            }
        }
    }

    public void ItemwiseTaxdialogListner(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("ok")) {
            getBinding("executeVoForItemwiseTax").execute();
            getBinding("setTaxAmount").execute();
        }
    }

    public void ItamDltDialogListner(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("ok")) {
            getBinding("executeVoForItemwiseTax").execute();
            getBinding("setTaxAmount").execute();

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.taxAmountBindvar);
    }

    public void setItemPopup(RichPopup itemPopup) {
        this.itemPopup = itemPopup;
    }

    public RichPopup getItemPopup() {
        return itemPopup;
    }


    public void setTaxItemwisePopup(RichPopup taxItemwisePopup) {
        this.taxItemwisePopup = taxItemwisePopup;
    }

    public RichPopup getTaxItemwisePopup() {
        return taxItemwisePopup;
    }


    public void setTaxRuleIdItem(RichSelectOneChoice taxRuleIdItem) {
        this.taxRuleIdItem = taxRuleIdItem;
    }

    public RichSelectOneChoice getTaxRuleIdItem() {
        return taxRuleIdItem;
    }

    public void setTaxRuleLineItemTbl(RichTable taxRuleLineItemTbl) {
        this.taxRuleLineItemTbl = taxRuleLineItemTbl;
    }

    public RichTable getTaxRuleLineItemTbl() {
        return taxRuleLineItemTbl;
    }

    public void setTaxableAmountForItem(RichInputText taxableAmountForItem) {
        this.taxableAmountForItem = taxableAmountForItem;
    }

    public RichInputText getTaxableAmountForItem() {
        return taxableAmountForItem;
    }


    public void setInvDtlTableBinding(RichTable invDtlTableBinding) {
        this.invDtlTableBinding = invDtlTableBinding;
    }

    public RichTable getInvDtlTableBinding() {
        return invDtlTableBinding;
    }

    public void setTaxAmountBindvar(RichInputText taxAmountBindvar) {
        this.taxAmountBindvar = taxAmountBindvar;
    }

    public RichInputText getTaxAmountBindvar() {
        return taxAmountBindvar;
    }

    public void setInvoiceTypeBindVar(RichSelectOneChoice invoiceTypeBindVar) {
        this.invoiceTypeBindVar = invoiceTypeBindVar;
    }

    public RichSelectOneChoice getInvoiceTypeBindVar() {
        return invoiceTypeBindVar;
    }

    public void setCustomerNmBindVar(RichInputComboboxListOfValues customerNmBindVar) {
        this.customerNmBindVar = customerNmBindVar;
    }

    public RichInputComboboxListOfValues getCustomerNmBindVar() {
        return customerNmBindVar;
    }

    public void setCurrencyBindVar(RichInputComboboxListOfValues currencyBindVar) {
        this.currencyBindVar = currencyBindVar;
    }

    public RichInputComboboxListOfValues getCurrencyBindVar() {
        return currencyBindVar;
    }

    public void setRateBindVar(RichInputText rateBindVar) {
        this.rateBindVar = rateBindVar;
    }

    public RichInputText getRateBindVar() {
        return rateBindVar;
    }

    public void setOcTablebindvar(RichTable ocTablebindvar) {
        this.ocTablebindvar = ocTablebindvar;
    }

    public RichTable getOcTablebindvar() {
        return ocTablebindvar;
    }

    public void setTncTableBindVar(RichTable tncTableBindVar) {
        this.tncTableBindVar = tncTableBindVar;
    }

    public RichTable getTncTableBindVar() {
        return tncTableBindVar;
    }

    public void setFooterPglBind(RichPanelGroupLayout footerPglBind) {
        this.footerPglBind = footerPglBind;
    }

    public RichPanelGroupLayout getFooterPglBind() {
        return footerPglBind;
    }

    public void setDetailsPtlBind(RichPanelTabbed detailsPtlBind) {
        this.detailsPtlBind = detailsPtlBind;
    }

    public RichPanelTabbed getDetailsPtlBind() {
        return detailsPtlBind;
    }

    /**
     * VCL to refresh the customer LOV on the basis of InvType
     * @param valueChangeEvent
     */
    public void invoiceTypeVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent != null) {
            //System.out.println("new Value :"+valueChangeEvent.getNewValue());
            OperationBinding binding = this.getBindings().getOperationBinding("refreshCustomerLOV");
            binding.getParamsMap().put("InvType", (Integer)valueChangeEvent.getNewValue());
            binding.execute();

        }
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     * ActionEvent to pouplate data for adjustment (temporary)
     * @param actionEvent
     */
    public void populateAdjustment(ActionEvent actionEvent) {
        this.adjTabBind.setActiveRowKey(null);
        getBindings().getOperationBinding("insertAdjustment").execute();

    }

    /**
     * VCL to set COA_ID in SlsInvVO
     * @param valueChangeEvent
     */
    public void eoNmChangeVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding binding = this.getBindings().getOperationBinding("setCoaIdFromEo");
            binding.getParamsMap().put("EoNm", new StringBuffer(vce.getNewValue().toString()));
            binding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(intmNoBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipIdBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rateBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(currencyBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(transAddBind);
            //System.out.println("VCL ");
        }
    }

    /**
     * ActionEvent to add shipment to Inv
     * @param actionEvent
     */
    public void addShipmentACTION(ActionEvent actionEvent) {
        
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(shipIdBind);
        boolean b = shipIdBind.isValid();
        //System.out.println("b :"+b);
        Object ship;

        Object object = shipIdBind.getValue();
        if (b == false) {
            String msg2 = "Please select valid Shipment Id.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("shipmentIdTransId", message2);
        } else if (shipIdBind.getValue() != null && !shipIdBind.getValue().equals("")) {
            OperationBinding NmVal = getBinding("shipmentNameValidation");
            NmVal.getParamsMap().put("ShipmentName", object.toString());
            Object retval = NmVal.execute();
            if (retval != null) {
                if ("Y".equals(retval)) {
                    //String msg2 = "Duplicate Shipment.";
                    String msg2 = resolvElDCMsg("#{bundle['MSG.1024']}").toString();
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(shipIdBind.getClientId(), message2);

                } else if (shipIdBind.getValue() != null && !shipIdBind.getValue().equals("")) {
                    OperationBinding getShipmentId = getBinding("getShipmentId");
                    getShipmentId.getParamsMap().put("ShipmentDispId", shipIdBind.getValue().toString());
                    ship = getShipmentId.execute();
                    //System.out.println("Ship doc id  : " + ship);
                    //------------------------------------------------------------


                    if (invoiceTypeBindVar.getValue() == null) {

                        // String msg1 = "Invoice Type is required";
                        String msg1 = resolvElDCMsg("#{bundle['MSG.1016']}").toString();
                        // String msg2 = "Please Select Invoice Type";
                        String msg2 = resolvElDCMsg("#{bundle['MSG.1017']}").toString();
                        FacesMessage errMsg = new FacesMessage(msg1);
                        errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        errMsg.setDetail(msg2);
                        FacesContext.getCurrentInstance().addMessage(invoiceTypeBindVar.getClientId(), errMsg);
                    } else if (customerNmBindVar.getValue() == null) {
                        //  String msg1 = "Customer Name is required";
                        String msg1 = resolvElDCMsg("#{bundle['MSG.1018']}").toString();
                        // String msg2 = "Please Select Customer Name";
                        String msg2 = resolvElDCMsg("#{bundle['MSG.1019']}").toString();
                        FacesMessage errMsg = new FacesMessage(msg1);
                        errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        errMsg.setDetail(msg2);
                        FacesContext.getCurrentInstance().addMessage(customerNmBindVar.getClientId(), errMsg);
                    } else if (currencyBindVar.getValue() == null) {
                        // String msg1 = "Currency is required";
                        String msg1 = resolvElDCMsg("#{bundle['MSG.21']}").toString();
                        // String msg2 = "Please Select Currency";
                        String msg2 = resolvElDCMsg("#{bundle['MSG.1020']}").toString();
                        FacesMessage errMsg = new FacesMessage(msg1);
                        errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        errMsg.setDetail(msg2);
                        FacesContext.getCurrentInstance().addMessage(currencyBindVar.getClientId(), errMsg);
                    } else if (rateBindVar.getValue() == null) {
                        //  String msg1 = "Currency Rate is required";
                        String msg1 = resolvElDCMsg("#{bundle['MSG.1021']}").toString();
                        //  String msg2 = "Please Select Currency Rate";
                        String msg2 = resolvElDCMsg("#{bundle['MSG.1022']}").toString();
                        FacesMessage errMsg = new FacesMessage(msg1);
                        errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        errMsg.setDetail(msg2);
                        FacesContext.getCurrentInstance().addMessage(rateBindVar.getClientId(), errMsg);
                    } else {
                        OperationBinding getMaxShipSrNo = getBinding("getMaxShipSrNo");
                        Integer SrNo = (Integer)getMaxShipSrNo.execute();
                        System.out.println("Sr no :" + SrNo);

                        OperationBinding createShpment = getBinding("Createwithparameters1");
                        createShpment.getParamsMap().put("SrNo", SrNo);
                        createShpment.execute();

                        if (createShpment.getErrors().isEmpty()) {
                            shipMode = "A";
                            //---------------------------------------------------------------------------------
                            if (ship != null) {
                                OperationBinding setShpItm = getBinding("setInvShpItm");
                                setShpItm.getParamsMap().put("P_ship_ID", ship);
                                String ret = "N";
                                Object retObj = setShpItm.execute();
                                this.currConvChkPopUpBind.hide();
                                if (retObj != null) {
                                    ret = (String)retObj;
                                    OperationBinding setTaxRuleFlg = getBinding("setTaxRuleFlg");
                                    setTaxRuleFlg.getParamsMap().put("ShpId", ship);
                                    setTaxRuleFlg.execute();
                                }
                                AdfFacesContext.getCurrentInstance().addPartialTarget(invDtlTableBinding);
                                AdfFacesContext.getCurrentInstance().addPartialTarget(taxAmountBindvar);
                                if (ret.equals("N")) {
                                    //getBinding("Delete1").execute();
                                    getBinding("Execute1").execute();
                                    String msg2 =
                                        resolvElDCMsg("#{bundle['MSG.1028']}").toString(); //Error in inserting data in Shipment Item.
                                    FacesMessage message2 = new FacesMessage(msg2);
                                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext.getCurrentInstance().addMessage(null, message2);
                                }
                            } else {
                                System.out.println("Error in inserting shipment");
                            }
                            //---------------------------------------------------------------------------------
                        }


                    }
                    AdfFacesContext.getCurrentInstance().addPartialTarget(taxAmountBindvar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(footerPglBind);
                }


                //------------------------------------------------------------


                else {
                    System.out.println("Ship doc id returned to null");
                    //   String msg1 = "Shipment Id is required";
                    String msg1 = resolvElDCMsg("#{bundle['MSG.1031']}").toString();
                    // String msg2 = "Please Select Shipment Id";
                    String msg2 = resolvElDCMsg("#{bundle['MSG.1032']}").toString();
                    FacesMessage errMsg = new FacesMessage(msg1);
                    errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    errMsg.setDetail(msg2);
                    FacesContext.getCurrentInstance().addMessage(shipIdBind.getClientId(), errMsg);
                    System.out.println("Error in inserting shipment..!");
                }
                shipIdBind.setValue("");

            }

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(invDtlTableBinding);

    }


    public void setShipIdBind(RichInputListOfValues shipIdBind) {
        this.shipIdBind = shipIdBind;
    }

    public RichInputListOfValues getShipIdBind() {
        return shipIdBind;
    }

    /**
     * Method for resolving the El
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
     * Action for sales invoice save
     * @return
     */
    public String SaveAction() {
        OperationBinding v = this.getBinding("areAllShipmntAmtValid");
        Object shipValid = v.execute();
        OperationBinding c = this.getBindings().getOperationBinding("isSlsInvdtlsCountValid");
        c.execute();
        Object s = c.getResult();
        if(!(Boolean)shipValid){
            
        }else if (s.equals((Object)true)) {

            OperationBinding pymnt = this.getBindings().getOperationBinding("AutoGeneratePaymentSchedule");
            pymnt.execute();

            BigDecimal taxableAmount = new BigDecimal(0);
            OperationBinding bind = this.getBindings().getOperationBinding("isTaxAppliedCorrectly");
            bind.execute();
            this.getBindings().getOperationBinding("advanceAdjCheck").execute();
            OperationBinding pbinding = this.getBindings().getOperationBinding("isPaymntSchduleValid");
            pbinding.execute();
            //            OperationBinding binding1 = this.getBinding("isOtherChargesAmountValid");
            //            binding1.execute();
            //
            //                if(binding1.getResult().equals((Object)false)){
            //                    getMessage("Faces", "<html>\n" +
            //                        "<body><table><tr><td></td></tr></table>" +
            //                        "<span style='color:red;font-family:Arial;font-size:11px;font-weight:bold;'>"+"Other charges should not be debited more than Invoice Amount !"+"</span></br>\n" +
            //                        "<span style='color:blue;font-family:Arial;font-size:10px;font-weight:bold;'>Please apply other charges properly</span>\n" +
            //                        "</body>" +
            //                        "</html>", "E");
            //                }else
            if (pbinding.getResult().equals(false)) {
                /*   getMessage("Faces", "<html>\n" +
                    "<body><table><tr><td></td></tr></table>" +
                    "<span style='color:red;font-family:Arial;font-size:11px;font-weight:bold;'>Payment Schedule for Total Invoice amount have not been made !</span></br>\n" +
                    "<span style='color:blue;font-family:Arial;font-size:10px;font-weight:bold;'> Please make the payment Schedule for the remaining Amount.</span>\n" +
                    "</body>" +
                    "</html>", "E");*/

                getMessage("Faces", "<html>\n" +
                        "<body><table><tr><td></td></tr></table>" +
                        "<span style='color:red;font-family:Arial;font-size:11px;font-weight:bold;'>" +
                        resolvElDCMsg("#{bundle['MSG.1033']}").toString() + "</span></br>\n" +
                        "<span style='color:blue;font-family:Arial;font-size:10px;font-weight:bold;'>" +
                        resolvElDCMsg("#{bundle['MSG.1034']}").toString() + "</span>\n" +
                        "</body>" + "</html>", "E");


            } else if (bind.getResult().equals(false)) {
                /*   getMessage("Faces", "<html>\n" +
                    "<body><table><tr><td></td></tr></table>" +
                    "<span style='color:red;font-family:Arial;font-size:11px;font-weight:bold;'>Taxable Amount is changed !</span></br>\n" +
                    "<span style='color:blue;font-family:Arial;font-size:10px;font-weight:bold;'> Please reapply Tax.</span>\n" +
                    "</body>" +
                    "</html>", "E");*/

                getMessage("Faces", "<html>\n" +
                        "<body><table><tr><td></td></tr></table>" +
                        "<span style='color:red;font-family:Arial;font-size:11px;font-weight:bold;'>" +
                        resolvElDCMsg("#{bundle['MSG.1035']}").toString() + "</span></br>\n" +
                        "<span style='color:blue;font-family:Arial;font-size:10px;font-weight:bold;'>" +
                        resolvElDCMsg("#{bundle['MSG.1036']}").toString() + "</span>\n" +
                        "</body>" + "</html>", "E");


            } else {
                OperationBinding setTotalInvAmt = getBinding("setTotalInvAmt");
                setTotalInvAmt.execute();
                OperationBinding binding1 = this.getBindings().getOperationBinding("getCurrentDocId");
                binding1.execute();
                StringBuffer currDocId = new StringBuffer(binding1.getResult().toString());
                //System.out.println("CurrDocId : " + currDocId);
                String andForward = saveAndForward();
                StringBuffer wf = (andForward == null ? null : new StringBuffer(andForward));

                OperationBinding commit = this.getBindings().getOperationBinding("Commit");
                commit.execute();
                if (commit.getErrors().isEmpty()) {
                    mode = "V";
                    shipMode = "V";
                    //  getMessage("Faces", "Record saved successfully!!!", "I");
                    getMessage("Faces", resolvElDCMsg("#{bundle['MSG.818']}").toString(), "I");
                    OperationBinding calc = this.getBindings().getOperationBinding("InvoiceCalcu");
                    calc.getParamsMap().put("DocId", currDocId);
                    calc.execute();
                    OperationBinding commitAfterCalc = this.getBindings().getOperationBinding("Commit");
                    commitAfterCalc.execute();
                    OperationBinding binding = this.getBindings().getOperationBinding("setSelectDocIdOnCommit");
                    binding.getParamsMap().put("DocId", currDocId);
                    binding.execute();

                    this.getBindings().getOperationBinding("Execute4").execute();
                } else {
                    //  getMessage("Faces", "Error while saving records..Contact ESS !!!", "E");
                    getMessage("Faces", resolvElDCMsg("#{bundle['MSG.1037']}").toString(), "E");
                }
            }
        } else {
            // getMessage("Faces", "Cannot save invoice without adding any shipment !", "I");
            getMessage("Faces", resolvElDCMsg("#{bundle['MSG.1038']}").toString(), "I");
        }


        return null;
    }

    /**
     * Action for save and forward action
     * @return
     */
    public String saveAndForwardACTION() {
        OperationBinding v = this.getBinding("areAllShipmntAmtValid");
        Object shipValid = v.execute();
        

        OperationBinding c = this.getBindings().getOperationBinding("isSlsInvdtlsCountValid");
        c.execute();
        Object s = c.getResult();
        if(!(Boolean)shipValid){
            
        }else if (s.equals((Object)true)) {
            String checktaxValue = "N";
            BigDecimal taxableAmount = new BigDecimal(0);
            this.getBindings().getOperationBinding("advanceAdjCheck").execute();
            OperationBinding pymnt = this.getBindings().getOperationBinding("AutoGeneratePaymentSchedule");
            pymnt.execute();

            OperationBinding bind = this.getBindings().getOperationBinding("isTaxAppliedCorrectly");
            Object pmntSch = this.getBindings().getOperationBinding("isPaymntSchduleValid").execute();
            if (pmntSch.equals((Object)false)) {
                /*   getMessage("Faces", "<html>\n" +
                "<body><table><tr><td></td></tr></table>" +
                "<span style='color:red;font-family:Arial;font-size:11px;font-weight:bold;'>Payment Schedule for Total Invoice amount have not been made !</span></br>\n" +
                "<span style='color:blue;font-family:Arial;font-size:10px;font-weight:bold;'> Please make the payment Schedule for the remaining Amount.</span>\n" +
                "</body>" +
                "</html>", "E");*/

                getMessage("Faces", "<html>\n" +
                        "<body><table><tr><td></td></tr></table>" +
                        "<span style='color:red;font-family:Arial;font-size:11px;font-weight:bold;'>" +
                        resolvElDCMsg("#{bundle['MSG.1033']}").toString() + "</span></br>\n" +
                        "<span style='color:blue;font-family:Arial;font-size:10px;font-weight:bold;'>" +
                        resolvElDCMsg("#{bundle['MSG.1034']}").toString() + "</span>\n" +
                        "</body>" + "</html>", "E");


            } else if (bind.execute().equals((Object)false)) {
                getMessage("Faces", resolvElDCMsg("#{bundle['LBL.2928']}").toString(),
                           "E"); //Taxable Amount changed ,Re-Apply TAX.

            } else {
                OperationBinding setTotalInvAmt = getBinding("setTotalInvAmt");
                setTotalInvAmt.execute();

                StringBuffer currDocId =
                    new StringBuffer(this.getBindings().getOperationBinding("getCurrentDocId").execute().toString());
                //System.out.println("CurrDocId : " + currDocId);
                OperationBinding commit = this.getBindings().getOperationBinding("Commit");
                commit.execute();
                if (commit.getErrors().isEmpty()) {
                    mode = "V";
                    shipMode = "V";
                    getMessage("Faces", resolvElDCMsg("#{bundle['MSG.818']}").toString() + "!!",
                               "I"); //Record saved successfully!!!
                    OperationBinding calc = this.getBindings().getOperationBinding("InvoiceCalcu");
                    calc.getParamsMap().put("DocId", currDocId);
                    calc.execute();
                    this.getBinding("updateShipmentOnSave").execute();
                    OperationBinding commitAfterCalc = this.getBindings().getOperationBinding("Commit");
                    commitAfterCalc.execute();
                    OperationBinding binding = this.getBindings().getOperationBinding("setSelectDocIdOnCommit");
                    binding.getParamsMap().put("DocId", currDocId);
                    binding.execute();

                    this.getBindings().getOperationBinding("Execute4").execute();
                    String andForward = saveAndForward();
                    StringBuffer result = (andForward == null ? null : new StringBuffer(saveAndForward()));
                    //System.out.println("IN SAVE AND FORWARD BLOCK!"+ result);
                    if (result != null) {
                        if (result.toString().equals("pendingAtOtherUser")) {
                            //  String msg2 =
                            // "This Sales Invoice is pending at other user for approval. You cannot Save and Forward this Document!";
                            String msg2 = resolvElDCMsg("#{bundle['MSG.1039']}").toString();

                            FacesMessage message2 = new FacesMessage(msg2);
                            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message2);
                            return null;
                        } else {
                            return result.toString();
                        }
                    } else {
                        this.getBinding("Execute6").execute();
                        return null;
                    }
                } else {
                    //  getMessage("Faces", "Error while saving records..Contact ESS !!!", "E");
                    getMessage("Faces", resolvElDCMsg("#{bundle['MSG.1037']}").toString(), "E");

                }
            }
        } else {
            //  getMessage("Faces", "Cannot save invoice without adding any shipment !", "I");
            getMessage("Faces", resolvElDCMsg("#{bundle['MSG.1038']}").toString(), "I");
        }

        return null;

    }

    /**
     * Method to perform wf operations
     * @return
     */
    public String saveAndForward() {
        Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        Integer userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        StringBuffer cldId = new StringBuffer(resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
        StringBuffer orgId = new StringBuffer(resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"));
        StringBuffer hoOrgId = new StringBuffer(resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}"));

        OperationBinding wfValid = getBindings().getOperationBinding("isWorkFlowAndUserValid");
        wfValid.execute();
        if(wfValid.getResult().equals((Boolean)true)){

            Integer pendingAtUsr;
            OperationBinding binding = this.getBindings().getOperationBinding("slsInvoicePendingAt");
            binding.execute();
            pendingAtUsr = (Integer)binding.getResult();
            System.out.println("Pending " + pendingAtUsr);

            // Check if the DocID is not being attached in WF. Or if pending at current user
            WfIdForSI = null;
            if (pendingAtUsr.compareTo(new Integer(-1)) == 0 || pendingAtUsr.compareTo(userId) == 0) {
                //getWfIdAttachedWithTheDoc
                OperationBinding operationBinding = this.getBindings().getOperationBinding("getWfIdAttachedWithTheDoc");
                operationBinding.execute();
                WfIdForSI = operationBinding.getResult().toString();

                System.out.println("WF_ID FOR SI : " + WfIdForSI);

                //To get the level of current user forward to
                Integer usrLvl = -3;
                binding = this.getBindings().getOperationBinding("getUsrLvl");
                binding.getParamsMap().put("WfId", new StringBuffer(WfIdForSI));
                usrLvl = (Integer)binding.execute();
                System.out.println("THE CURRENT USER LEVEL IS :" + usrLvl);

                // Insert line into wfTxn
                Integer result = -4;
                OperationBinding insTxn = this.getBindings().getOperationBinding("insIntoTxn");
                insTxn.getParamsMap().put("WfId", new StringBuffer(WfIdForSI));
                insTxn.getParamsMap().put("level", usrLvl);
                result = (Integer)insTxn.execute();
                System.out.println("INS TXN line inserted : " + result);

                // Commiting data
                this.getBinding("updateShipmentOnSave").execute();
                OperationBinding execute = this.getBindings().getOperationBinding("Commit");
                execute.execute();

                if (execute.getErrors().isEmpty()) {
                    return "goToWf";
                } else {
                    String msg2 = "Error in performing commit after insTxn in saveAndFOrward method!.";

                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message2);
                    System.out.println("Error in performing commit after insTxn");

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
        return null;
    }

    public void setWfIdForSI(String WfIdForSI) {
        this.WfIdForSI = WfIdForSI;
    }

    public String getWfIdForSI() {
        return WfIdForSI;
    }

    public void setWfReturn(String wfReturn) {
        System.out.println("return from wf :" + wfReturn);
        this.wfReturn = wfReturn;
    }

    public String getWfReturn() {
        return wfReturn;
    }

    /**
     * Method to enable or disable adj tab
     * @return
     */
    public boolean getAdjTabMode() {


        return (Boolean)this.getBindings().getOperationBinding("DisableAdjTab").execute();
    }

    public void setAdjMode(String adjMode) {
        this.adjMode = adjMode;
    }

    /**
     * Method to get the enable or disable mode of adjustment tab
     * @return
     */
    public String getAdjMode() {
        OperationBinding bindingContainer = this.getBindings().getOperationBinding("DisableAdjTab");
        Object obj = false;
        if (bindingContainer != null) {
            OperationBinding binding = this.getBindings().getOperationBinding("DisableAdjTab");
            binding.execute();
            obj = binding.getResult();
            //System.out.println(obj);
        }
        return obj.toString();
    }

    public void adjAmtVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        //adjAmtVal
        if (object != null) {
            OperationBinding binding = this.getBindings().getOperationBinding("adjAmtVal");
            binding.getParamsMap().put("val", (BigDecimal)object);
            Object execute = binding.execute();
            System.out.println("Returned :: " + execute);
            if ((Integer)binding.getResult() == 2) {
                // String msg2 = "The adjusted amount is greater than the amount scheduled for today!";
                String msg2 = resolvElDCMsg("#{bundle['MSG.1040']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            } else if ((Integer)binding.getResult() == 1) {
                String msg2 = resolvElDCMsg("Adjusted amount cannot be greater than outstanding amount.").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

        }

    }

    /**
     * VCL rto set tax to zero initially
     * @param valueChangeEvent
     */
    public void invoiceWiseTaxVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue().equals((Object)true)) {
            //System.out.println("into this");
            getBinding("removeTaxItemWise").execute();
            this.getBindings().getOperationBinding("setTaxToZero").execute();
        }
    }

    /**
     *  ActionEvent to generate Calculations
     *
     **/
    public void generateCalcACTION(ActionEvent actionEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("InvoiceCalcu");
        binding.execute();
        //this.getBindings().getOperationBinding("Execute5").execute();
        //this.getBindings().getOperationBinding("Execute1").execute();
        System.out.println("" + "Executed");
    }

    public void invoiceDtVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding fyChkBind = this.getBindings().getOperationBinding("isFYOpenForCurrentDate");
            fyChkBind.getParamsMap().put("dt", (Timestamp)object);
            fyChkBind.execute();
            if (fyChkBind.getResult().equals((Object)false)) {
                // throw new ValidatorException(new FacesMessage("Cannot create Sales Invoice for the given Sales Invoice Date.",
                //    "There is no Open Financial Year for the given date. So you cannot create Sales Invoice for the given Date."));

                throw new ValidatorException(new FacesMessage(resolvElDCMsg("#{bundle['MSG.1041']}").toString(),
                                                              resolvElDCMsg("#{bundle['MSG.1042']}").toString()));

            }
        }
    }

    public void setCurrConvChkPopUpBind(RichPopup currConvChkPopUpBind) {
        this.currConvChkPopUpBind = currConvChkPopUpBind;
    }

    public RichPopup getCurrConvChkPopUpBind() {
        return currConvChkPopUpBind;
    }

    public void setAdjAmt(RichInputText adjAmt) {
        this.adjAmt = adjAmt;
    }

    public RichInputText getAdjAmt() {
        return adjAmt;
    }

    public void setAdjTabBind(RichTable adjTabBind) {
        this.adjTabBind = adjTabBind;
    }

    public RichTable getAdjTabBind() {
        return adjTabBind;
    }

    public void cancelSalesInvoiceACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("CancelSalesInvoice").execute();
        this.cancelPopBind.hide();
        mode = "V";
    }

    /**
     * To show or hhide sales Invoice Cancel Button
     * @return
     */
    public Integer getCancelButtonMode() {
        //isUserEligibleToCancelSalesInvoice
        OperationBinding binding = this.getBindings().getOperationBinding("isUserEligibleToCancelSalesInvoice");

        if (binding != null) {
            //System.out.println(" Not Null");
            binding.execute();
            if (binding.getResult().equals((Object)true)) {
                //System.out.println("Returned 1");
                return 1;

            } else {
                // System.out.println("else Returned -1");
                return -1;
            }
        }
        System.out.println("Returned -1");
        return -1;
    }

    /**
     * ActionEvent to insert data into itemVo on the basis of intimation
     * @param actionEvent
     */
    public void insertItemOnIntimationACTION(ActionEvent actionEvent) {

        if (intmNoBind.getValue() != "") {
            this.getBindings().getOperationBinding("insertItmOnBasisOfIntimation").execute();
            intimationWarningPopUpBind.hide();
        } else {
            // FacesMessage errMsg = new FacesMessage("Intimation Id is required");
            FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1043']}").toString());
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            //  errMsg.setDetail("Please Select Shipment Id");
            errMsg.setDetail(resolvElDCMsg("#{bundle['MSG.1032']}").toString());
            FacesContext.getCurrentInstance().addMessage(intmNoBind.getClientId(), errMsg);
        }

    }

    /**
     * ActionEvent to add shipment to Inv
     * @param actionEvent
     */
    public void addShipmentACTION1(ActionEvent actionEvent) {
        Object elNew = resolvElNew("#{bindings.ShipmentIdTrans.validator}");
        Validator v = (Validator)elNew;

        if (invoiceTypeBindVar.getValue() == null) {
            // String msg1 = "Invoice Type is required";
            String msg1 = resolvElDCMsg("#{bundle['MSG.1016']}").toString();
            // String msg2 = "Please Select Invoice Type";
            String msg2 = resolvElDCMsg("#{bundle['MSG.1017']}").toString();
            FacesMessage errMsg = new FacesMessage(msg1);
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            errMsg.setDetail(msg2);
            FacesContext.getCurrentInstance().addMessage(invoiceTypeBindVar.getClientId(), errMsg);
        } else if (customerNmBindVar.getValue() == null) {
            // String msg1 = "Customer Name is required";
            String msg1 = resolvElDCMsg("#{bundle['MSG.1018']}").toString();
            // String msg2 = "Please Select Customer Name";
            String msg2 = resolvElDCMsg("#{bundle['MSG.1019']}").toString();
            FacesMessage errMsg = new FacesMessage(msg1);
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            errMsg.setDetail(msg2);
            FacesContext.getCurrentInstance().addMessage(customerNmBindVar.getClientId(), errMsg);
        } else if (currencyBindVar.getValue() == null) {
            //  String msg1 = "Currency is required";
            String msg1 = resolvElDCMsg("#{bundle['MSG.21']}").toString();
            //   String msg2 = "Please Select Currency";
            String msg2 = resolvElDCMsg("#{bundle['MSG.1020']}").toString();
            FacesMessage errMsg = new FacesMessage(msg1);
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            errMsg.setDetail(msg2);
            FacesContext.getCurrentInstance().addMessage(currencyBindVar.getClientId(), errMsg);
        } else if (rateBindVar.getValue() == null) {
            //  String msg1 = "Currency Rate is required";
            String msg1 = resolvElDCMsg("#{bundle['MSG.1021']}").toString();
            //String msg2 = "Please Select Currency Rate";
            String msg2 = resolvElDCMsg("#{bundle['MSG.1022']}").toString();
            FacesMessage errMsg = new FacesMessage(msg1);
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            errMsg.setDetail(msg2);
            FacesContext.getCurrentInstance().addMessage(rateBindVar.getClientId(), errMsg);
        } else if (shipIdBind.getValue() == null || shipIdBind.getValue() == "") {
            // String msg1 = "Shipment Id is required";
            String msg1 = resolvElDCMsg("#{bundle['MSG.1031']}").toString();
            // String msg2 = "Please Select a shipment id for shipment addititon.";
            String msg2 = resolvElDCMsg("#{bundle['MSG.1044']}").toString();
            FacesMessage errMsg = new FacesMessage(msg1);
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            errMsg.setDetail(msg2);
            FacesContext.getCurrentInstance().addMessage(shipIdBind.getClientId(), errMsg);

        } else {
            OperationBinding getMaxShipSrNo = getBinding("getMaxShipSrNo");
            Integer SrNo = (Integer)getMaxShipSrNo.execute();
            System.out.println("Sr no :" + SrNo);

            OperationBinding createShpment = getBinding("Createwithparameters1");
            createShpment.getParamsMap().put("SrNo", SrNo);
            createShpment.execute();

            if (createShpment.getErrors().isEmpty()) {
                shipMode = "A";
                //---------------------------------------------------------------------------------
                OperationBinding setIntimationItm = getBinding("insertItmOnBasisOfIntimation");
                Object retObj = setIntimationItm.execute();
                System.out.println("insertItmOnBasisOfIntimation");
                Integer i = (Integer)setIntimationItm.getResult();
                //this.currConvChkPopUpBind.hide();
                /* if (retObj != null) {
                            ret = (String)retObj;
                            /* OperationBinding setTaxRuleFlg = getBinding("setTaxRuleFlg");
                            setTaxRuleFlg.getParamsMap().put("ShpId", ship);
                            setTaxRuleFlg.execute();
                        } */
                AdfFacesContext.getCurrentInstance().addPartialTarget(invDtlTableBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(taxAmountBindvar);
                if (i == -1) {
                    //getBinding("Delete1").execute();
                    getBinding("Execute1").execute();
                    String msg2 =
                        resolvElDCMsg("#{bundle['MSG.1028']}").toString(); //"Error in inserting data in Shipment Item.";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, message2);
                }

                //---------------------------------------------------------------------------------
            } else {
                System.out.println("Error in inserting data into shipment from inti,mation");
            }


        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxAmountBindvar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(footerPglBind);
    }

    public void setIntmNoBind(RichInputListOfValues intmNoBind) {
        this.intmNoBind = intmNoBind;
    }

    public RichInputListOfValues getIntmNoBind() {
        return intmNoBind;
    }

    public void setIntimationWarningPopUpBind(RichPopup intimationWarningPopUpBind) {
        this.intimationWarningPopUpBind = intimationWarningPopUpBind;
    }

    public RichPopup getIntimationWarningPopUpBind() {
        return intimationWarningPopUpBind;
    }

    /**
     * Validator to validate duplicate Coa in Other Charges
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void ocCoaVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding binding = this.getBindings().getOperationBinding("doOtherChargeCoaAlreadyExist");
            binding.getParamsMap().put("CoaId", (Integer)object);
            binding.execute();
            if (binding.getResult().equals((Object)true)) {
                //  throw new ValidatorException(new FacesMessage("Duplicate Coa !","This other charges COA is already added in Other Charges."));
                throw new ValidatorException(new FacesMessage(resolvElDCMsg("#{bundle['LBL.393']}").toString(),
                                                              resolvElDCMsg("#{bundle['MSG.1045']}").toString()));
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

            int i = ((BigDecimal)object).compareTo(new BigDecimal(0));
            if (i == -1) {
                //  FacesMessage message = new FacesMessage("Invalid number!","Cannot enter negative value!");
                FacesMessage message =
                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.1002']}").toString(), resolvElDCMsg("#{bundle['MSG.1003']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }

        }
    }

    public void setCancelPopBind(RichPopup cancelPopBind) {
        this.cancelPopBind = cancelPopBind;
    }

    public RichPopup getCancelPopBind() {
        return cancelPopBind;
    }

    /**Method to check invalid precision*/
    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    /**
     * Validator to validate the other charges amount
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void invOcAmountVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        this.negativeNumberVAL(facesContext, uIComponent, object);

        Number n = new Number(0);
        try {
            n = new Number((BigDecimal)object);
        } catch (SQLException e) {

        }
        //System.out.println("object /:"+object+" Number is : "+n+" _____"+isPrecisionValid(26, 6, n));
        if (!isPrecisionValid(26, 6, n)) {
            throw new ValidatorException(new FacesMessage("Invalid Precision!", ""));
        } else if (n.compareTo(new Number(0)) <= 0) {
            throw new ValidatorException(new FacesMessage("Value Should be greater than zero!", ""));
        } else if (object != null) {
            OperationBinding binding = this.getBinding("isOtherChargesAmountValid");
            binding.getParamsMap().put("val", (BigDecimal)object);
            binding.execute();
            System.out.println("Result :" + binding.getResult() + " " + binding.getResult().equals((Boolean)false));
            if (binding.getResult().equals((Boolean)false)) {
                //   throw new ValidatorException(new FacesMessage("Invalid Amount in Other Charges !","Other Charges amount cannot be Credited more than Invoice Amount."));
                throw new ValidatorException(new FacesMessage(resolvElDCMsg("#{bundle['MSG.1046']}").toString(),
                                                              resolvElDCMsg("#{bundle['MSG.1047']}").toString()));
            }
        }
    }

    public void shipmentVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent != null) {
            OperationBinding NmVal = getBinding("shipmentNameValidation");
            NmVal.getParamsMap().put("ShipmentName", valueChangeEvent.toString());
            Object retval = NmVal.execute();
            if (retval != null) {
                if ("Y".equals(retval)) {
                    //String msg2 = "Duplicate Shipment.";
                    String msg2 = resolvElDCMsg("#{bundle['MSG.1024']}").toString();
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                }
            }

        }
    }

    public void currencyVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(rateBindVar);
    }


    public void setAddShipmntButtonBind(RichCommandImageLink addShipmntButtonBind) {
        this.addShipmntButtonBind = addShipmntButtonBind;
    }

    public RichCommandImageLink getAddShipmntButtonBind() {
        return addShipmntButtonBind;
    }


    /**
     * Method for resolving the El
     */
    public Object resolvElNew(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void populateSupplimentryInvoiceACTION(ActionEvent actionEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("populateSupplimentryInvoice");
        binding.execute();
        this.invSelectionMode = new StringBuffer("C");
    }

    public void NewRateVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Object val = uIComponent.getAttributes().get("oldValue");
        System.out.println("New Price is  : " + object + " Old Price is  : " + val);
        if (val != null) {
            BigDecimal oldVal = (BigDecimal)val;
            BigDecimal newVal = (BigDecimal)object;
            if (newVal.compareTo(new BigDecimal(0)) == -1) {
                throw new ValidatorException(new FacesMessage("Price cannot be negative."));
            } else if (newVal.compareTo(new BigDecimal(0)) == 0) {

            } else if (oldVal.compareTo(newVal) >= 0) {
                throw new ValidatorException(new FacesMessage("New Price can be zero or greater than Old Price"));
            }
        }
    }

    public void OtherChargesCL(PopupCanceledEvent popupCanceledEvent) {
        OperationBinding chk = getBinding("chkInvOcEnteredOrNot");
        chk.execute();

        if (Integer.parseInt(chk.getResult().toString()) != 0) {
            getBinding("Delete2").execute();
            getBinding("Execute2").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(ocTablebindvar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(otherChargerPgBind);
        }
    }

    public void setOtherChargerPgBind(RichPanelFormLayout otherChargerPgBind) {
        this.otherChargerPgBind = otherChargerPgBind;
    }

    public RichPanelFormLayout getOtherChargerPgBind() {
        return otherChargerPgBind;
    }

    public void AmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        //negativeNumberVAL(facesContext, uIComponent, object);
        System.out.println("New Amt is  : " + object);
        if (object != null) {

            BigDecimal newVal = (BigDecimal)object;
            /* if (newVal.compareTo(new BigDecimal(0)) <= 0) {
                throw new ValidatorException(new FacesMessage("Amount Should be greater than zero."));
            } */
            try {
                if (!isPrecisionValid(26, 6, new Number(newVal))) {
                    throw new ValidatorException(new FacesMessage("Invalid Precision!", ""));
                }
            } catch (SQLException e) {
            }
        }
    }

     public void AddShipmentAL(ActionEvent actionEvent) {
        /* OperationBinding binding = this.getBinding("isCoaIdNull");
        binding.execute();
        Object object = binding.getResult();
        if(object.equals((Object)false)){ */
            OperationBinding val = getBinding("getCurConvType");
            val.execute();
            Object retVal = val.getResult();
            System.out.println("In Bean ret Val is " + retVal);
            if (retVal != null) {
                if ((Integer)retVal == 538 || (Integer)retVal == 539) { // Shipment or Invoice Currency is selected automatically
                   
                    addShipmentACTION(actionEvent);

                } else { // Manual currency is selected where user need to select the currency
                    showPopup(currConvChkPopUpBind, true);
                    
                }
             }    
                /*
            }else{
                FacesMessage message2 = new FacesMessage("COA For the Selected Customer Doesn't exist. Please create COA before adding Shipment.");
                message2.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            }
        
         */
    } 

    public void setCurrConvTransPgBind(RichSelectOneRadio currConvTransPgBind) {
        this.currConvTransPgBind = currConvTransPgBind;
    }

    public RichSelectOneRadio getCurrConvTransPgBind() {
        return currConvTransPgBind;
    }


    public void paymentDtVCL(ValueChangeEvent valueChangeEvent) {
        OperationBinding binding = this.getBinding("setRemainingPayment");
        binding.execute();
    }

    public void coaVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
       
    }
    
    /**
     * Method to filter Supplimentry Invoice ViewObject
     * @param actionEvent
     */
    public void filterInvoicesACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("searchVoForSuppInvoice").execute();
    }

    public void selectAllInvoicesACTION(ActionEvent actionEvent) {
        this.getBinding("selectAllInvoices").execute();
    }

    public void deselectAllInvoicesACTION(ActionEvent actionEvent) {
        this.getBinding("deselectAllInvoices").execute();
    }

    public void proceedWithSelectedInvoicesACTION(ActionEvent actionEvent) {
        invSelectionMode = new StringBuffer("V");
        this.getBinding("saveAndPoplulateDistinctItm").execute();
    }
    
    
    public void applyPriceForSupplimntryInvACTION(ActionEvent actionEvent) {
        Object suppOper = this.getSuppliOperCbBind().getValue();
        Object suppType = this.getSuppliTypeCbBind().getValue();
        
        if(suppOper == null || "".equals(suppOper)){
            FacesMessage message2 = new FacesMessage("Please select an Operation !");
            message2.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(this.getSuppliOperCbBind().getClientId(), message2);
        }else if(suppType == null || "".equals(suppType)){
            FacesMessage message2 = new FacesMessage("Please select Amount Type !");
            message2.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(this.getSuppliTypeCbBind().getClientId(), message2);
        }else if(suppNewVal.compareTo(new BigDecimal(0)) <= 0){
            FacesMessage message2 = new FacesMessage("Amount should be greater than zero !");
            message2.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        }else if("P".equals(suppType.toString()) && suppNewVal.compareTo(new BigDecimal(100)) > 0){
            FacesMessage message2 = new FacesMessage("Percentage should be less than 100 !");
            message2.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        }else{
            OperationBinding binding = this.getBindings().getOperationBinding("applyNewPriceForSuppInv");
            binding.getParamsMap().put("oper", suppOper.toString());
            binding.getParamsMap().put("typ", suppType.toString());
            binding.getParamsMap().put("value", suppNewVal);
            binding.execute();
        }
    }

    public StringBuffer getInvSelectionMode() {
        return invSelectionMode;
    }

    public void setSuppNewVal(BigDecimal suppNewVal) {
        if(suppNewVal == null){
            suppNewVal = new BigDecimal(0);
        }else{
            this.suppNewVal = suppNewVal;    
        }
    }

    public BigDecimal getSuppNewVal() {
        return suppNewVal;
    }

    

    public void setSuppliOperCbBind(RichSelectOneRadio suppliOperCbBind) {
        this.suppliOperCbBind = suppliOperCbBind;
    }

    public RichSelectOneRadio getSuppliOperCbBind() {
        return suppliOperCbBind;
    }

    public void setSuppliTypeCbBind(RichSelectOneRadio suppliTypeCbBind) {
        this.suppliTypeCbBind = suppliTypeCbBind;
    }

    public RichSelectOneRadio getSuppliTypeCbBind() {
        return suppliTypeCbBind;
    }

    public void goBackToSelectInvoicesACTION(ActionEvent actionEvent) {
        invSelectionMode = new StringBuffer("A");
    }

    public void viewItemACTION(ActionEvent ace) {
        /* Object object = ace.getComponent().getAttributes().get("currentKey");
        Key parentKey = null;
        
        RowKeySet k = (RowKeySet)object;
        Iterator<Object> iterator = k.iterator();
        while(iterator.hasNext()){
            Object next = iterator.next();
            System.out.println("Key is : "+next);
            parentKey =  (Key)next;
        }

        DCIteratorBinding parentIter = null;      
        try {
            parentIter = (DCIteratorBinding)getBindings().get("SlsInvDtlIterator");
            if(parentKey != null && parentIter != null){
                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                System.out.println("Setting Parent key to : "+parentKey);
            }
        } catch (Exception e) {
            System.out.println("Error in getting key");
        }
        
        System.out.println("Key is  : "+object); */
    }

    public void setTransAddBind(RichInputText transAddBind) {
        this.transAddBind = transAddBind;
    }

    public RichInputText getTransAddBind() {
        return transAddBind;
    }
}
