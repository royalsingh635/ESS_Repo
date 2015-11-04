package slssalesinvoiceapp.view.bean;


import adf.utils.bean.ADFBeanUtils;
import adf.utils.bean.StaticValue;
import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import java.io.Serializable;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
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
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DropEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class SalesInvoiceAddBean implements Serializable {
    
    private String ccCcId;

    public void setCcCcId(String ccCcId) {
        this.ccCcId = ccCcId;
    }

    public String getCcCcId() {
        return ccCcId;
    }

    public void setCcAmtSp(BigDecimal ccAmtSp) {
        this.ccAmtSp = ccAmtSp;
    }

    public BigDecimal getCcAmtSp() {
        return ccAmtSp;
    }
    private BigDecimal ccAmtSp; 
    @SuppressWarnings("compatibility:6289207321942968195")
    private static final long serialVersionUID = -2983828073976568265L;
    private boolean addButtonMode = false;
    private transient RichPopup discountPopup;
    private transient RichInputListOfValues shipmentBinding;
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
    // private RichInputComboboxListOfValues customerNmBindVar;
    private RichInputComboboxListOfValues currencyBindVar;
    private RichInputText rateBindVar;
    private RichTable ocTablebindvar;
    private RichTable tncTableBindVar;
    private RichPanelGroupLayout footerPglBind;
    private RichPanelTabbed detailsPtlBind;
    private RichInputListOfValues shipIdBind;
    private String WfIdForSI;
    private String wfReturn;
    private String exportLink = null;

    private String adjMode;
    private RichPopup currConvChkPopUpBind;
    private RichInputText adjAmt;
    private RichTable adjTabBind;
    private RichInputListOfValues intmNoBind;
    private RichPopup intimationWarningPopUpBind;
    private RichPopup cancelPopBind;
    private RichLink addShipmntButtonBind;
    private Boolean showAsblVal = null;
    private Boolean disableAsblValField = null;
    private Boolean useWty = null;
    private Boolean useLc = null;
    private RichInputListOfValues soIdLovBind;
    /**
     *  Method to hide and unhide part number in Invoice Item
     */
    private Boolean showEoPartNumber = null;
    private RichPopup showShipmSearchForIntmPopBind;
    private RichPanelGroupLayout intimationSelectGroupBinding;
    private Boolean enableAdvanIntmSearch = null;
    private Boolean chkcostcenterVar = null;
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

    public void setChkcostcenterVar(Boolean chkcostcenterVar) {
        this.chkcostcenterVar = chkcostcenterVar;
    }

    public Boolean getChkcostcenterVar() {
        if (chkcostcenterVar == null) {
            OperationBinding var = ADFBeanUtils.findOperation("chkCostCenterEnable_Disable");
            if (var != null) {
                var.execute();
                System.out.println("Value of Var ------- " + (Integer) var.getResult());
                Integer val = (Integer) var.getResult();
                if (val == 1) {
                    chkcostcenterVar = true;
                } else {
                    chkcostcenterVar = false;
                }
            }
        }
        return chkcostcenterVar;
    }    
    
    public Boolean getEnableAdvanIntmSearch() {
        if (enableAdvanIntmSearch == null) {
            OperationBinding b = ADFBeanUtils.getOperationBinding("isAdvanIntmSearchApplicable");
            if (b != null) {
                b.execute();
                Object o = b.getResult();
                enableAdvanIntmSearch = (o == null ? false : (Boolean) o);
                //  System.out.println("*********************************  " + enableCostCenter);
            }
        }

        return enableAdvanIntmSearch;
    }

    public Boolean getShowEoPartNumber() {
        if (showEoPartNumber == null) {
            StringBuffer i = new StringBuffer("N");
            OperationBinding binding = this.getBindings().getOperationBinding("checkforProfileValues");
            binding.getParamsMap().put("colName", new StringBuffer("USE_PART_NO_IN_TRAN")); //EDIT_ASBL_VAL
            if (binding != null) {
                binding.execute();
                Object object = binding.getResult();
                if (object != null) {
                    i = (StringBuffer) object;
                }
                if (i.toString().equals("Y")) {
                    showEoPartNumber = true;
                } else {
                    showEoPartNumber = false;
                }
            }
        }
        return showEoPartNumber;
    }

    public void setUseLc(Boolean useLc) {
        this.useLc = useLc;
    }

    public Boolean getUseLc() {
        if (useLc == null) {
            StringBuffer i = new StringBuffer("N");
            OperationBinding binding = this.getBindings().getOperationBinding("checkforProfileValues");
            binding.getParamsMap().put("colName", new StringBuffer("USE_LC")); //EDIT_ASBL_VAL
            if (binding != null) {
                binding.execute();
                Object object = binding.getResult();
                if (object != null) {
                    i = (StringBuffer) object;
                }
                if (i.toString().equals("Y")) {
                    useLc = true;
                } else {
                    useLc = false;
                }
            }
        }
        return useLc;
    }

    public void setUseWty(Boolean useWty) {
        this.useWty = useWty;
    }

    public Boolean getUseWty() {
        if (useWty == null) {
            StringBuffer i = new StringBuffer("N");
            OperationBinding binding = this.getBindings().getOperationBinding("checkforProfileValues");
            binding.getParamsMap().put("colName", new StringBuffer("USE_WTY")); //EDIT_ASBL_VAL
            if (binding != null) {
                binding.execute();
                Object object = binding.getResult();
                if (object != null) {
                    i = (StringBuffer) object;
                }
                if (i.toString().equals("Y")) {
                    useWty = true;
                } else {
                    useWty = false;
                }
            }
        }
        return useWty;
    }


    public void setShowAsblVal(Boolean showAsblVal) {
        this.showAsblVal = showAsblVal;
    }

    public Boolean getShowAsblVal() {
        if (showAsblVal == null) {
            StringBuffer i = new StringBuffer("N");
            OperationBinding binding = this.getBindings().getOperationBinding("checkforProfileValues");
            binding.getParamsMap().put("colName", new StringBuffer("SHOW_ASBL_VAL")); //EDIT_ASBL_VAL
            if (binding != null) {
                binding.execute();
                Object object = binding.getResult();
                if (object != null) {
                    i = (StringBuffer) object;
                }
                if (i.toString().equals("Y")) {
                    showAsblVal = true;
                } else {
                    showAsblVal = false;
                }
            }
        }
        return showAsblVal;
    }

    public void setDisableAsblValField(Boolean disableAsblValField) {
        this.disableAsblValField = disableAsblValField;
    }

    public Boolean getDisableAsblValField() {
        if (disableAsblValField == null) {
            StringBuffer i = new StringBuffer("N");
            OperationBinding binding = this.getBindings().getOperationBinding("checkforProfileValues");
            binding.getParamsMap().put("colName", new StringBuffer("EDIT_ASBL_VAL")); //
            if (binding != null) {
                binding.execute();
                Object object = binding.getResult();
                if (object != null) {
                    i = (StringBuffer) object;
                }
                if (i.toString().equals("Y")) {
                    disableAsblValField = false;
                } else {
                    disableAsblValField = true;
                }
            }
        }
        return disableAsblValField;
    }


    public void setAddShipmntButtonBind(RichLink addShipmntButtonBind) {
        this.addShipmntButtonBind = addShipmntButtonBind;
    }

    public RichLink getAddShipmntButtonBind() {
        return addShipmntButtonBind;
    }

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
        if (object.equals((Object) true)) {
            //    this.customerNmBindVar = null;
            this.getBinding("resetSuppliTabs").execute();
            OperationBinding create = getBinding("Createwithparameters");
            create.execute();
            if (create.getErrors().isEmpty()) {
                invSelectionMode = new StringBuffer("A");
                mode = "A";
            } else {
                //getMessage("Faces", "Error while creating.Contact ESS !!!", "E");
                getMessage("Faces", ADFModelUtils.resolvRsrc("MSG.1013"), "E");
            }
            //resolvElDCMsg("#{bundle['MSG.1013']}");
            //ADFModelUtils.resolvRsrc("MSG.1013");
            AdfFacesContext.getCurrentInstance().addPartialTarget(detailsPtlBind);
        }
        return null;
    }

    public String CreateInvoiceFromSearchPage() {
        mode = "A";
        return null;
    }

    /**
     * Method to Edit Sales Invoice
     * @return
     */
    public String EditAction() {
        Integer userId = EbizParams.GLBL_APP_USR();
        Integer pendingAtUsr;
        pendingAtUsr = (Integer) this.getBindings().getOperationBinding("slsInvoicePendingAt").execute();
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
            String msg2 =
                "<html>\n" + "<body><table><tr><td></td></tr></table>" +
                "<span style='color:red;font-family:Arial;font-size:11px;font-weight:bold;'>" +
                resolvElDCMsg("#{bundle['MSG.1014']}").toString() + "</span></br>\n" +
                "<span style='color:blue;font-family:Arial;font-size:10px;font-weight:bold;'>" +
                resolvElDCMsg("#{bundle['MSG.1015']}").toString() + "</span>\n" + "</body>" + "</html>";

            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            return null;
        }
    }

    /**
     * Method to rollback Sales Invoice changes
     * @return
     */
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

    /** Validation for customer Name required
     * */
    public Boolean chkReqCustNm() {
        OperationBinding binding = this.getBindings().getOperationBinding("retValForCustNm");
        binding.execute();
        Boolean val = (Boolean) binding.getResult();
        return val;
    }

    /**
     * Method too add shipment to Invoice
     * @return
     */
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
        }
        //else if (customerNmBindVar.getValue() == null) {
        else if (chkReqCustNm()) {
            // String msg1 = "Customer Name is required";
            String msg1 = resolvElDCMsg("#{bundle['MSG.1018']}").toString();
            // String msg2 = "Please Select Customer Name";
            String msg2 = resolvElDCMsg("#{bundle['MSG.1019']}").toString();
            FacesMessage message2 = new FacesMessage(msg1);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            /*FacesMessage errMsg = new FacesMessage(msg1);
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            errMsg.setDetail(msg2);
            FacesContext.getCurrentInstance().addMessage(customerNmBindVar.getClientId(), errMsg); */
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
            Integer SrNo = (Integer) getMaxShipSrNo.execute();

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

    /**
     * Method to remove shipment from Sales Invoice
     * @return
     */
    public String DeleteShipment() {
        getBinding("deleteCostCenterForItem").execute(); // for delete cost center
        getBinding("deleteShpDtl").execute();
        //System.out.println("_________________ 8");
        AdfFacesContext.getCurrentInstance().addPartialTarget(invDtlTableBinding);
        //am.getDBTransaction().postChanges();
        //am.getSlsInvDtl1().executeQuery();
        DCIteratorBinding parentIter = (DCIteratorBinding) getBindings().get("SlsInvShipItmIterator");
        parentIter.executeQuery();
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
            BigDecimal rate = (BigDecimal) vce.getNewValue();
            rate = rate.setScale(amt_digit, RoundingMode.HALF_UP);
            OperationBinding setBaseAmount = getBinding("setBaseAmount");
            setBaseAmount.getParamsMap().put("Rate", rate);
            setBaseAmount.execute();
        }
    }

    /**
     * Method Action event for applying default Tax rule and open popup for  Invoice wise tax
     * @return
     * modified by Mousham
     */
    public String InvoicewiseTaxAction() {
        this.getBindings().getOperationBinding("setTaxRuleInTransient").execute();
        showPopup(taxInvoicewisePopup, true);
        return null;
    }

    /**
     *Value Change event for Tax rule applying
     * @param vce
     */
    public void taxRuleInvoicewiseChangeListner(ValueChangeEvent vce) {
        if (vce.getNewValue() != null && taxableAmountBindVar.getValue() != null) {
            StringBuilder d =
                (vce.getNewValue() == "" ? new StringBuilder("") : new StringBuilder(vce.getNewValue().toString()));
            if (!"".equals(d.toString())) {
                BigDecimal taxableAmount = (BigDecimal) taxableAmountBindVar.getValue();
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
            String msg2 =
                "<html>\n" + "<body><table><tr><td></td></tr></table>" +
                "<span style='color:red;font-family:Arial;font-size:11px;font-weight:bold;'>" +
                ADFModelUtils.resolvRsrc("MSG.2072") + "</span></br>\n" + "</body>" + "</html>";
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
        ADFBeanUtils.findOperation("callPostChange").execute();
    }

    public void DragEndListner(DropEvent dropEvent) {

    }

    public DnDAction dropListner(DropEvent dropEvent) {
        if ("E".equals(mode) || "A".equals(mode)) {

            DCBindingContainer bc = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
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
            StringBuilder d =
                (taxRuleIdBindVar.getValue() == "" ? new StringBuilder("") :
                 new StringBuilder(taxRuleIdBindVar.getValue().toString()));
            if (!"".equals(d.toString())) {
                BigDecimal taxableAmount = (BigDecimal) taxableAmountBindVar.getValue();
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
            BigDecimal taxableAmount = (BigDecimal) taxableAmountForItem.getValue();
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

    /**
     * Method to add Payment schedule line
     * @return
     */
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

                Timestamp date = (Timestamp) paymentDateBindVar.getValue();
                OperationBinding ob = getBinding("paymentDateValidation");
                ob.getParamsMap().put("paydt", date);
                Object ret = ob.execute();
                if (ret != null) {
                    String flg = (String) ret;
                    System.out.println(" the value of valdiation check is " + flg);

                    if ("Y".equalsIgnoreCase(flg)) {
                        //  String msg2 = "Duplicate Payment Date.";
                        //String msg2 = resolvElDCMsg("#{bundle['MSG.1025']}").toString();
                        String msg2 = ADFModelUtils.resolvRsrc("MSG.2073");
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, message2);

                    } else {
                        BigDecimal totalPayAmt = (BigDecimal) totalPaymentAMount.getValue();
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
            BigDecimal DiscVal = (BigDecimal) object;
            OperationBinding bindings = getBinding("isDiscountValueValid");
            bindings.getParamsMap().put("val", DiscVal);
            bindings.execute();
            if ((Integer) bindings.getResult() ==
                1) {
                // FacesMessage message = new FacesMessage("Larger Amount!","Discount Amount cannot be greater than Amount After Tax!");
                FacesMessage message =
                                     new FacesMessage(resolvElDCMsg("#{bundle['MSG.1026']}").toString(),
                                                      resolvElDCMsg("#{bundle['MSG.1027']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            } else if ((Integer) bindings.getResult() ==
                       2) {
                //FacesMessage message = new FacesMessage("Negative Amount!","Negative Amount not Allowed!");
                FacesMessage message =
                    new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1638"), ADFModelUtils.resolvRsrc("MSG.2074"));
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

                    ret = (String) retObj;

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

        return null;
    }

    /**
     * Method to Remove tax from from Item
     * @return
     */
    public String removeItemTaxAction() {
        getBinding("removeTaxItemWise").execute();
        //getBinding("setTaxAmount").execute();
        ADFBeanUtils.findOperation("callPostChange").execute();
        taxItemwisePopup.hide();
        return null;
    }

    public void taxRuleItemwiseChangeListner(ValueChangeEvent vce) {
        if (vce.getNewValue() != null && taxableAmountForItem.getValue() != null) {

            BigDecimal taxableAmount = (BigDecimal) taxableAmountForItem.getValue();
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
            //getBinding("setTaxAmount").execute();
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

    /*   public void setCustomerNmBindVar(RichInputComboboxListOfValues customerNmBindVar) {
        this.customerNmBindVar = customerNmBindVar;
    }

    public RichInputComboboxListOfValues getCustomerNmBindVar() {
        return customerNmBindVar;
    } */

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
            binding.getParamsMap().put("InvType", (Integer) valueChangeEvent.getNewValue());
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
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            AdfFacesContext.getCurrentInstance().addPartialTarget(intmNoBind);
            Object prjAll = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_ORG_PROJ_ALW}");
            String prjAllow = prjAll == null ? "N" : prjAll.toString();
            if (prjAllow.equalsIgnoreCase("N")) {
                AdfFacesContext.getCurrentInstance().addPartialTarget(shipIdBind);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(rateBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(currencyBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(transAddBind);
            if (vce.getNewValue() != vce.getOldValue() && vce.getOldValue() != null) {
                ADFBeanUtils.findOperation("resetLOVonCustomerVCE").execute();
            }
            //System.out.println("VCL ");
        }

    }

    /**
     * ActionEvent to add shipment to Inv
     * @param actionEvent
     */
    public void addShipmentACTION(ActionEvent actionEvent) {
        /**
         * Method to add shipment to an Invoice
         * 1 : Invoice Type is not required.
         * 2 : Customer not Selected.
         * 3 : Currenty is not selected.
         * 4 : Shipment Doc Id is not selected.
         * 5 : Duplicate Shipment Id
         * 6 : Curr Conv type not selected
         * 7 : Error in calling function FN_INS_SHIPITM_INV
         * -1 : Error
         * @return
         */
        AdfFacesContext.getCurrentInstance().addPartialTarget(shipIdBind);
        boolean b = shipIdBind.isValid();
        if (b == false) {
            String msg2 = ADFModelUtils.resolvRsrc("MSG.2075");
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("shipmentIdTransId", message2);
        } else {
            OperationBinding binding = ADFBeanUtils.getOperationBinding("addShipmentToInv");
            Object execute = binding.execute();
            Integer i = (execute == null ? -1 : (Integer) execute);
            if (i.equals(6)) {
                ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2076"),
                                               ADFModelUtils.resolvRsrc("MSG.2077"), FacesMessage.SEVERITY_ERROR, null);
                /*ADFModelUtils.showFacesMessage("Currency Conversion type have not been selected !",
                                               "Please define if you want to use Currency Conversion rate of Shipment or use Currency Conversion rate of Current Invoice.",
                                               FacesMessage.SEVERITY_ERROR, null);*/
            } else if (i.equals(7)) {
                ADFModelUtils.showFormattedFacesMessage("There have been some error in calling function 'FN_INS_SHIPITM_INV' !",
                                                        "Please try again. If the problem persists please contact Ess !",
                                                        FacesMessage.SEVERITY_ERROR);
            } else if (i.equals(0)) {
                this.currConvChkPopUpBind.hide();
                shipIdBind.setValue("");
                AdfFacesContext.getCurrentInstance().addPartialTarget(invDtlTableBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(footerPglBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(taxAmountBindvar);
                ADFBeanUtils.findOperation("iterateSlsInvShpItm").execute();
            }
            // Call this method and verify
        }
    }


    public void setShipIdBind(RichInputListOfValues shipIdBind) {
        this.shipIdBind = shipIdBind;
    }

    public RichInputListOfValues getShipIdBind() {
        return shipIdBind;
    }


    /**
     * Action for sales invoice save
     * @return
     */
    public String SaveAction1() {
        
        OperationBinding v = this.getBinding("areAllShipmntAmtValid");
        Object shipValid = v.execute();
        OperationBinding c = this.getBindings().getOperationBinding("isSlsInvdtlsCountValid");
        c.execute();
        Object s = c.getResult();
        if (!(Boolean) shipValid) {

        } else if (s.equals((Object) true)) {
            OperationBinding bind = this.getBindings().getOperationBinding("isTaxAppliedCorrectly");
            bind.execute();
            OperationBinding pymnt = this.getBindings().getOperationBinding("AutoGeneratePaymentSchedule");
            pymnt.execute();
            this.getBindings().getOperationBinding("advanceAdjCheck").execute();
            OperationBinding pbinding = this.getBindings().getOperationBinding("isPaymntSchduleValid");
            pbinding.execute();
            if (pbinding.getResult().equals(false)) {
                // Payment Schedule not made properly
            } else if (bind.getResult().equals(false)) {
                //Tax amount changed
            } else {


                OperationBinding setTotalInvAmt = getBinding("setTotalInvAmt");
                setTotalInvAmt.execute();
                OperationBinding binding1 = this.getBindings().getOperationBinding("getCurrentDocId");
                binding1.execute();
                StringBuffer currDocId = new StringBuffer(binding1.getResult().toString());
                //System.out.println("CurrDocId : " + currDocId);
                String andForward = saveAndForward();
                StringBuffer wf = (andForward == null ? null : new StringBuffer(andForward));
                //Method to update Shipment Status on Save : Added on 22-08-2015|AJ
                this.getBinding("updateShipmentOnSave").execute();
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
                    ADFBeanUtils.findOperation("sendDateFromTempCcToSlsInvCc").execute();
                    OperationBinding commitAfterCalc = this.getBindings().getOperationBinding("Commit");
                    commitAfterCalc.execute();
                    
                    /* OperationBinding calc1 = ADFBeanUtils.getOperationBinding("insertTrLinesForMismatch");
                    calc1.getParamsMap().put("docId", currDocId.toString());
                    calc1.execute();
                    
                    commitAfterCalc = ADFBeanUtils.getOperationBinding("Commit");
                    commitAfterCalc.execute(); */
                    
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
     * Method to save Sales Invoice
     * @return
     */
    public String SaveAction() {
        Boolean success = true;
        if (success) {
            // Method to validate if Invoice details are added in Invoice or not
            OperationBinding c = this.getBindings().getOperationBinding("isSlsInvdtlsCountValid");
            c.execute();
            Object o = c.getResult();
            success = (o == null ? false : (Boolean) o);
        }
        if (success) {
            // Method to validate if all the shipment amount is valid or not
            OperationBinding v = ADFBeanUtils.getOperationBinding("areAllShipmntAmtValid");
            Object shipValid = v.execute();
            success = (shipValid == null ? false : (Boolean) shipValid);
        }
        if (success) {
            // Method to validate if the Invoice amount is greater than 0
            OperationBinding v1 = ADFBeanUtils.getOperationBinding("isInvoiceValueValid");
            Object invoiceValid = v1.execute();
            success = (invoiceValid == null ? false : (Boolean) invoiceValid);
        }
        if (success) {
            // Method to check if Tax have been applied correctly or not
            OperationBinding bind = ADFBeanUtils.getOperationBinding("isTaxAppliedCorrectly");
            bind.execute();
            Object o = bind.getResult();
            success = (o == null ? false : (Boolean) o);
        }
        if (success) {
            // Method to auto generate payment schedule
            OperationBinding pymnt = ADFBeanUtils.getOperationBinding("AutoGeneratePaymentSchedule");
            pymnt.execute();
            ADFBeanUtils.getOperationBinding("advanceAdjCheck").execute();
            // Method to validate payment shedule
            OperationBinding pbinding = ADFBeanUtils.getOperationBinding("isPaymntSchduleValid");
            pbinding.execute();
            Object o = pbinding.getResult();
            success = (o == null ? false : (Boolean) o);
        }
        StringBuffer currDocId = new StringBuffer("");
        if (success) {
            OperationBinding setTotalInvAmt = ADFBeanUtils.getOperationBinding("setTotalInvAmt");
            setTotalInvAmt.execute();
            OperationBinding binding1 = ADFBeanUtils.getOperationBinding("getCurrentDocId");
            binding1.execute();
            currDocId = new StringBuffer(binding1.getResult().toString());
            String andForward = saveAndForward();
            StringBuffer wf = (andForward == null ? null : new StringBuffer(andForward));
            //Method to update Shipment Status on Save : Added on 22-08-2015|AJ
            ADFBeanUtils.getOperationBinding("updateShipmentOnSave").execute();
        }
        if (success) {
            try {
                OperationBinding commit = ADFBeanUtils.getOperationBinding("Commit");
                commit.execute();
                success = commit.getErrors().isEmpty();
            } catch (Exception e) {
                ADFModelUtils.showFormattedFacesMessage("There have been an error while saving Sales Invoice.",
                                                        e.getMessage(), FacesMessage.SEVERITY_ERROR);
                e.printStackTrace();
                success = false;
            }
            if (!success) {
                ADFModelUtils.showFormattedFacesMessage("There have been an error while saving Sales Invoice.",
                                                        "Please try again !", FacesMessage.SEVERITY_ERROR);
            }
        }
        if (success) {
            mode = "V";
            shipMode = "V";
            //  getMessage("Faces", "Record saved successfully!!!", "I");
            getMessage("Faces", resolvElDCMsg("#{bundle['MSG.818']}").toString(), "I");
            OperationBinding calc = this.getBindings().getOperationBinding("InvoiceCalcu");
            calc.getParamsMap().put("DocId", currDocId);
            calc.execute();
            ADFBeanUtils.findOperation("sendDateFromTempCcToSlsInvCc").execute();
        }
        if (success) {
            try {
                OperationBinding commit = ADFBeanUtils.getOperationBinding("Commit");
                commit.execute();
                success = commit.getErrors().isEmpty();
            } catch (Exception e) {
                ADFModelUtils.showFormattedFacesMessage("There have been an error while saving Sales Invoice.",
                                                        e.getMessage(), FacesMessage.SEVERITY_ERROR);
                e.printStackTrace();
                success = false;
            }
            if (!success) {
                ADFModelUtils.showFormattedFacesMessage("There have been an error while saving Sales Invoice.",
                                                        "Please try again !", FacesMessage.SEVERITY_ERROR);
            }
        }
        if (success) {
            OperationBinding binding = ADFBeanUtils.getOperationBinding("setSelectDocIdOnCommit");
            binding.getParamsMap().put("DocId", currDocId);
            binding.execute();
            ADFBeanUtils.getOperationBinding("Execute4").execute();
        }
        return null;
    }

    /**
     * Action for save and forward action
     * @return
     */
    public String saveAndForwardACTION1() {
        Boolean isPcDataValid = true;
        OperationBinding chkPc = ADFBeanUtils.findOperation("chkprofitCenterValidData");
        chkPc.execute();
        if (chkPc.getResult() != null) {
            isPcDataValid = (Boolean) chkPc.getResult();
        }
        if (isPcDataValid) {

            OperationBinding b2 = ADFBeanUtils.getOperationBinding("isLcValid");
            b2.execute();
            Object object = b2.getResult();
            Boolean b = (object == null ? false : (Boolean) object);
            if (b) {
                OperationBinding v = this.getBinding("areAllShipmntAmtValid");
                Object shipValid = v.execute();
                OperationBinding c = this.getBindings().getOperationBinding("isSlsInvdtlsCountValid");
                c.execute();
                Object s = c.getResult();
                if (!(Boolean) shipValid) {

                } else if (s.equals((Object) true)) {
                    String checktaxValue = "N";
                    BigDecimal taxableAmount = new BigDecimal(0);
                    this.getBindings().getOperationBinding("advanceAdjCheck").execute();
                    OperationBinding pymnt = this.getBindings().getOperationBinding("AutoGeneratePaymentSchedule");
                    pymnt.execute();

                    OperationBinding bind = this.getBindings().getOperationBinding("isTaxAppliedCorrectly");
                    Object pmntSch = this.getBindings().getOperationBinding("isPaymntSchduleValid").execute();
                    if (pmntSch.equals(false)) {

                    } else if (bind.execute().equals((Object) false)) {
                        /* getMessage("Faces", resolvElDCMsg("#{bundle['LBL.2928']}").toString(),
                               "E"); //Taxable Amount changed ,Re-Apply TAX. */

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
                            ADFBeanUtils.findOperation("sendDateFromTempCcToSlsInvCc").execute();
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
            }
        } else {
            ADFModelUtils.showFormattedFacesMessage("Mandatory Levels in Profit Center are not Defined. ",
                                                    "Please insert data for Mandatory Levels in Profit Center!",
                                                    FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }
    
    
    /**
     * Action for save and forward action
     * @return
     */
    public String saveAndForwardACTION() {
        Boolean success = true;
        if (success) {
            OperationBinding chkPc = ADFBeanUtils.getOperationBinding("chkprofitCenterValidData");
            chkPc.execute();
            if (chkPc.getResult() != null) {
                success = (Boolean) chkPc.getResult();
            } else {
                success = false;
            }
        }
        if (success) {
            OperationBinding v = ADFBeanUtils.getOperationBinding("areAllShipmntAmtValid");
            Object shipValid = v.execute();
            success = (shipValid == null ? false : (Boolean) shipValid);
            if (!success) {
                System.out.println("Shipment amount is not valid !");
            }
        }
        if (success) {
            OperationBinding v1 = ADFBeanUtils.getOperationBinding("isInvoiceValueValid");
            Object invoiceValid = v1.execute();
            success = (invoiceValid == null ? false : (Boolean) invoiceValid);
        }
        if (success) {
            OperationBinding c = ADFBeanUtils.getOperationBinding("isSlsInvdtlsCountValid");
            c.execute();
            Object s = c.getResult();
            success = (s == null ? false : (Boolean) s);
            /* if (!success) {
                //  getMessage("Faces", "Cannot save invoice without adding any shipment !", "I");
                ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1038"),
                                                        ADFModelUtils.resolvRsrc("MSG.1038"),
                                                        FacesMessage.SEVERITY_ERROR);
            } */
        }
        if (success) {
            this.getBindings().getOperationBinding("advanceAdjCheck").execute();
        }

        if (success) {
            OperationBinding binding = ADFBeanUtils.getOperationBinding("isTaxAppliedCorrectly");
            binding.execute();
            Object object = binding.getResult();
            success = (object == null ? false : (Boolean) object);
        }
        if (success) {
            OperationBinding pymnt = ADFBeanUtils.getOperationBinding("AutoGeneratePaymentSchedule");
            pymnt.execute();
            
            OperationBinding binding = ADFBeanUtils.getOperationBinding("isPaymntSchduleValid");
            binding.execute();
            Object object = binding.getResult();
            success = (object == null ? false : (Boolean) object);
        }
        if (success) {
            OperationBinding b2 = ADFBeanUtils.getOperationBinding("isLcValid");
            b2.execute();
            Object object = b2.getResult();
            success = (object == null ? false : (Boolean) object);
            if (!success) {
                System.out.println("LC Amount is not valid !");
            }
        }
        
        if (success) {
            OperationBinding setTotalInvAmt = ADFBeanUtils.getOperationBinding("setTotalInvAmt");
            setTotalInvAmt.execute();
            StringBuffer currDocId =
                new StringBuffer(this.getBindings().getOperationBinding("getCurrentDocId").execute().toString());
            try {
                OperationBinding commit = ADFBeanUtils.getOperationBinding("Commit");
                commit.execute();
                if (!commit.getErrors().isEmpty()) {
                    success = false;
                    ADFModelUtils.showFormattedFacesMessage("There have been an error while saving Sales Invoice.",
                                                            "Please try again !  ", FacesMessage.SEVERITY_ERROR);
                }
            } catch (Exception e) {
                ADFModelUtils.showFormattedFacesMessage("There have been an error while saving Sales Invoice.",
                                                        e.getMessage(), FacesMessage.SEVERITY_ERROR);
                e.printStackTrace();
                success = false;
            }
        }
        if (success) {
            this.mode = "V";
            this.shipMode = "V";
            StringBuffer currDocId =
                new StringBuffer(this.getBindings().getOperationBinding("getCurrentDocId").execute().toString());

            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.818"),
                                                    ADFModelUtils.resolvRsrc("MSG.818"), FacesMessage.SEVERITY_INFO);
            //Record saved successfully!!!
            OperationBinding calc = ADFBeanUtils.getOperationBinding("InvoiceCalcu");
            calc.getParamsMap().put("DocId", currDocId);
            calc.execute();
            
            this.getBinding("updateShipmentOnSave").execute();
            OperationBinding commitAfterCalc = ADFBeanUtils.getOperationBinding("Commit");
            commitAfterCalc.execute();
            success = commitAfterCalc.getErrors().isEmpty();
            if(success){
                ADFBeanUtils.findOperation("sendDateFromTempCcToSlsInvCc").execute();
                commitAfterCalc.execute();
                success = commitAfterCalc.getErrors().isEmpty();
            }
            
            
            // Double Commit in case of TrLines not available for Tr
            /* OperationBinding calc1 = ADFBeanUtils.getOperationBinding("insertTrLinesForMismatch");
            calc1.getParamsMap().put("docId", currDocId.toString());
            calc1.execute();
            
            commitAfterCalc = ADFBeanUtils.getOperationBinding("Commit");
            commitAfterCalc.execute(); */
            
            OperationBinding binding = this.getBindings().getOperationBinding("setSelectDocIdOnCommit");
            binding.getParamsMap().put("DocId", currDocId);
            binding.execute();
            ADFBeanUtils.getOperationBinding("Execute4").execute();
        }
        if (success) {
            String result = saveAndForward();
            if (result != null) {
                if (result.toString().equals("pendingAtOtherUser")) {
                    //  String msg2 =
                    // "This Sales Invoice is pending at other user for approval. You cannot Save and Forward this Document!";
                    String msg2 = resolvElDCMsg("#{bundle['MSG.1039']}").toString();
                    ADFModelUtils.showFormattedFacesMessage(msg2, msg2, FacesMessage.SEVERITY_WARN);
                    success = false;
                }
            } else {
                this.getBinding("Execute6").execute();
                success = false;
                
            }
        }
        if (success) {

        }
        return success ? "goToWf" : null;
    }

    /**
     * Method to perform wf operations
     * @return
     */
    public String saveAndForward() {
        OperationBinding wfValid = getBindings().getOperationBinding("isWorkFlowAndUserValid");
        wfValid.execute();
        if (wfValid.getResult().equals((Boolean) true)) {
            Integer pendingAtUsr;
            OperationBinding binding = this.getBindings().getOperationBinding("slsInvoicePendingAt");
            binding.execute();
            pendingAtUsr = (Integer) binding.getResult();
            System.out.println("Pending " + pendingAtUsr);
            // Check if the DocID is not being attached in WF. Or if pending at current user
            WfIdForSI = null;
            if (pendingAtUsr.compareTo(new Integer(-1)) == 0 ||
                pendingAtUsr.compareTo(EbizParams.GLBL_APP_USR()) == 0) {
                //getWfIdAttachedWithTheDoc
                OperationBinding operationBinding = this.getBindings().getOperationBinding("getWfIdAttachedWithTheDoc");
                operationBinding.execute();
                WfIdForSI = operationBinding.getResult().toString();

                System.out.println("WF_ID FOR SI : " + WfIdForSI);

                //To get the level of current user forward to
                Integer usrLvl = -3;
                binding = this.getBindings().getOperationBinding("getUsrLvl");
                binding.getParamsMap().put("WfId", new StringBuffer(WfIdForSI));
                usrLvl = (Integer) binding.execute();
                System.out.println("THE CURRENT USER LEVEL IS :" + usrLvl);

                // Insert line into wfTxn
                Integer result = -4;
                OperationBinding insTxn = this.getBindings().getOperationBinding("insIntoTxn");
                insTxn.getParamsMap().put("WfId", new StringBuffer(WfIdForSI));
                insTxn.getParamsMap().put("level", usrLvl);
                result = (Integer) insTxn.execute();
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
        return (Boolean) this.getBindings().getOperationBinding("DisableAdjTab").execute();
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
            Object b = uIComponent.getAttributes().get("outStandingAmt");
            BigDecimal amt = (BigDecimal) object;
            BigDecimal outstandAmt = (b == null ? BigDecimal.ZERO : (BigDecimal) b);
            if (amt.compareTo(BigDecimal.ZERO) < 0) {
                /* throw new ValidatorException(new FacesMessage("The amount should be greater than zero !",
                                                              "Please enter a valid amount.")); */
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2078"),
                                                              ADFModelUtils.resolvRsrc("MSG.2079")));
            } /* else if(amt.compareTo(outstandAmt) > 0){
                throw new ValidatorException(new FacesMessage("The amount should be less tha or equal to Outstanding Amount !","Please enter a valid amount."));
            } */
            else {
                OperationBinding binding = this.getBindings().getOperationBinding("adjAmtVal");
                binding.getParamsMap().put("val", (BigDecimal) object);
                Object execute = binding.execute();
                System.out.println("Returned :: " + execute);
                if ((Integer) binding.getResult() == 2) {
                    // String msg2 = "The adjusted amount is greater than the amount scheduled for today!";
                    String msg2 = resolvElDCMsg("#{bundle['MSG.1040']}").toString();
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                } else if ((Integer) binding.getResult() == 1) {
                    String msg2 = ADFModelUtils.resolvRsrc("MSG.2080");
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                }
            }
        }

    }

    /**
     * VCL rto set tax to zero initially
     * @param valueChangeEvent
     */
    public void invoiceWiseTaxVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue().equals((Object) true)) {
            //System.out.println("into this");
            //getBinding("removeTaxItemWise").execute();
            //this.getBindings().getOperationBinding("setTaxToZero").execute();
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
            fyChkBind.getParamsMap().put("dt", (Timestamp) object);
            fyChkBind.execute();
            if (fyChkBind.getResult().equals((Object) false)) {
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
    private Integer cancelButtonMode = null;

    /**
     * To show or hhide sales Invoice Cancel Button
     * @return
     */
    public Integer getCancelButtonMode() {
        //isUserEligibleToCancelSalesInvoice
        if (cancelButtonMode == null) {
            OperationBinding binding = this.getBindings().getOperationBinding("isUserEligibleToCancelSalesInvoice");
            if (binding != null) {
                binding.execute();
                if (binding.getResult().equals((Object) true)) {
                    cancelButtonMode = 1;
                } else {
                    cancelButtonMode = -1;
                }
            }
        }
        return cancelButtonMode;
    }

    public void setcancelButtonModeToNull() {
        this.cancelButtonMode = null;
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
        Validator v = (Validator) elNew;

        if (invoiceTypeBindVar.getValue() == null) {
            // String msg1 = "Invoice Type is required";
            String msg1 = resolvElDCMsg("#{bundle['MSG.1016']}").toString();
            // String msg2 = "Please Select Invoice Type";
            String msg2 = resolvElDCMsg("#{bundle['MSG.1017']}").toString();
            FacesMessage errMsg = new FacesMessage(msg1);
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            errMsg.setDetail(msg2);
            FacesContext.getCurrentInstance().addMessage(invoiceTypeBindVar.getClientId(), errMsg);
        }
        //else if (customerNmBindVar.getValue() == null) {
        else if (chkReqCustNm()) {
            // String msg1 = "Customer Name is required";
            String msg1 = resolvElDCMsg("#{bundle['MSG.1018']}").toString();
            // String msg2 = "Please Select Customer Name";
            String msg2 = resolvElDCMsg("#{bundle['MSG.1019']}").toString();
            FacesMessage message2 = new FacesMessage(msg1);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            /* FacesMessage errMsg = new FacesMessage(msg1);
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            errMsg.setDetail(msg2);
            FacesContext.getCurrentInstance().addMessage(customerNmBindVar.getClientId(), errMsg); */
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
            Integer SrNo = (Integer) getMaxShipSrNo.execute();
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
                Integer i = (Integer) setIntimationItm.getResult();
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
            binding.getParamsMap().put("CoaId", (Integer) object);
            binding.execute();
            if (binding.getResult().equals((Object) true)) {
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

            int i = ((BigDecimal) object).compareTo(new BigDecimal(0));
            if (i ==
                -1) {
                //  FacesMessage message = new FacesMessage("Invalid number!","Cannot enter negative value!");
                FacesMessage message =
               new FacesMessage(resolvElDCMsg("#{bundle['MSG.1002']}").toString(),
                                resolvElDCMsg("#{bundle['MSG.1003']}").toString());
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
            n = new Number((BigDecimal) object);
        } catch (SQLException e) {

        }
        //System.out.println("object /:"+object+" Number is : "+n+" _____"+isPrecisionValid(26, 6, n));
        if (!isPrecisionValid(26, 6, n)) {
            throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1266"), ""));
        } else if (n.compareTo(new Number(0)) <= 0) {
            throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.198"), ""));
        } else if (object != null) {
            OperationBinding binding = this.getBinding("isOtherChargesAmountValid");
            binding.getParamsMap().put("val", (BigDecimal) object);
            binding.execute();
            System.out.println("Result :" + binding.getResult() + " " + binding.getResult().equals((Boolean) false));
            if (binding.getResult().equals((Boolean) false)) {
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
        OperationBinding binding1 = this.getBindings().getOperationBinding("checkItemValueChanged");
        binding1.execute();
        Object res = binding1.getResult();
        System.out.println("res is " + res);
        if (res.equals((Object) true)) {
            /* ADFModelUtils.showFormattedFacesMessage("Please Select any one of the item to change its price!",
                                                    "It is mandatory to change of price of atleast one item!!",
                                                    FacesMessage.SEVERITY_ERROR); */
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.2081"),
                                                    ADFModelUtils.resolvRsrc("MSG.2083"), FacesMessage.SEVERITY_ERROR);
        } else {
            OperationBinding binding = this.getBindings().getOperationBinding("populateSupplimentryInvoice");
            binding.execute();
            this.invSelectionMode = new StringBuffer("C");
        }
    }

    public void NewRateVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Object val = uIComponent.getAttributes().get("oldValue");
        Object oprTypObj = uIComponent.getAttributes().get("oprType");
        System.out.println("New Price is  : " + object + " Old Price is  : " + val);
        if (val != null) {
            BigDecimal oldVal = (BigDecimal) val;
            BigDecimal newVal = (BigDecimal) object;
            String oprTyp = oprTypObj.toString();
            if (newVal.compareTo(new BigDecimal(0)) == -1) {
                //throw new ValidatorException(new FacesMessage("Price cannot be negative!"));
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2084")));
            } else if (newVal.compareTo(new BigDecimal(0)) == 0) {

            } else if (oprTyp.equalsIgnoreCase("A")) {
                if (oldVal.compareTo(newVal) >= 0) {
                    //throw new ValidatorException(new FacesMessage("New Price can be zero or greater than Old Price."));
                    throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2085")));
                }
            } else if (oprTyp.equalsIgnoreCase("S")) {
                if (oldVal.compareTo(newVal) < 0) {
                    throw new ValidatorException(new FacesMessage("New Price must be less than Old Price"));
                }
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

            BigDecimal newVal = (BigDecimal) object;
            /* if (newVal.compareTo(new BigDecimal(0)) <= 0) {
                throw new ValidatorException(new FacesMessage("Amount Should be greater than zero."));
            } */
            try {
                if (!isPrecisionValid(26, 6, new Number(newVal))) {
                    //throw new ValidatorException(new FacesMessage("Invalid Precision!", ""));
                    throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1266"), ""));
                }
            } catch (SQLException e) {
            }
        }
    }

    /**
     * Method to validate Header
     * 1 : Invoice Type is not required.
     * 2 : Customer not Selected.
     * 3 : Currenty is not selected.
     * 8 : Billing address is not defined
     * 9 : CoaId is not defined
     * 0 : if Shipment is Valid true
     * @return
     */
    public Integer validateHeaderBeforeAdd() {
        OperationBinding valBinding = ADFBeanUtils.getOperationBinding("validateHeaderBeforeAdd");
        valBinding.execute();
        Object o = valBinding.getResult();
        Integer i = (o == null ? -1 : (Integer) o);

        /* OperationBinding binding = ADFBeanUtils.getOperationBinding("addShipmentToInv");
        Object execute = binding.execute();
        Integer i = (execute == null ? -1 : (Integer)execute);
         */
        if (i.equals(1)) {
            StringBuffer clientId = new StringBuffer(footerPglBind.getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("soc3");
            /* ADFModelUtils.showFacesMessage("Invoice Type is not selected !", "Please select Invoice Type !",
                                           FacesMessage.SEVERITY_ERROR, clientId.toString()); */
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2086"), ADFModelUtils.resolvRsrc("MSG.2087"),
                                           FacesMessage.SEVERITY_ERROR, clientId.toString());
        } else if (i.equals(2)) {
            StringBuffer clientId = new StringBuffer(footerPglBind.getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("eoTransNameId");
            /* ADFModelUtils.showFacesMessage("Customer is not selected !", "Please select a Customer !",
                                           FacesMessage.SEVERITY_ERROR, clientId.toString());MSG.2088 */
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2088"), ADFModelUtils.resolvRsrc("MSG.2089"),
                                           FacesMessage.SEVERITY_ERROR, clientId.toString());
        } else if (i.equals(3)) {
            StringBuffer clientId = new StringBuffer(footerPglBind.getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("currencyTransNameId");
            /* ADFModelUtils.showFacesMessage("Currency is not selected !", "Please select a Currency !",
                                           FacesMessage.SEVERITY_ERROR, clientId.toString()); */
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2090"), ADFModelUtils.resolvRsrc("MSG.2091"),
                                           FacesMessage.SEVERITY_ERROR, clientId.toString());
        } else if (i.equals(8)) {
            /* ADFModelUtils.showFormattedFacesMessage("Billing Address is not selected !",
                                                    "Please select billing address for current Invoice!<br /><br/> By default the Default Billing Address of Customer is Selected in Invoice. Seems the default Billing Address for the Current Customer is not defined in Customer Profile.<br/><br>It is advised to define a Default Billing Address in Customer Profile.",
                                                    FacesMessage.SEVERITY_WARN); */
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.2092"),
                                                    ADFModelUtils.resolvRsrc("MSG.2093"), FacesMessage.SEVERITY_WARN);
        } else if (i.equals(9)) {
            /* ADFModelUtils.showFormattedFacesMessage("Chart of Account is not created for Selected Customer !",
                                                    "Please define Chart of Account for current Customer !<br /><br/> Invoice cannot be created for a Customer whose Chart of Account is not created.",
                                                    FacesMessage.SEVERITY_WARN); */
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.2094"),
                                                    ADFModelUtils.resolvRsrc("MSG.2095"), FacesMessage.SEVERITY_WARN);
        } else if (i.equals(0)) {

        } else {
            ADFModelUtils.showFormattedFacesMessage("There have been some problem in Validating Header !",
                                                    "Please try again. If the problem persists. Contact ESS !",
                                                    FacesMessage.SEVERITY_WARN);
            i = -1;
        }
        return i;
    }

    public void AddShipmentAL(ActionEvent actionEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(shipIdBind);
        boolean b = shipIdBind.isValid();
        if (b == false) {
            String msg2 = ADFModelUtils.resolvRsrc("MSG.2075"); //"Please select valid Shipment Id.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(shipIdBind.getClientId(), message2);
        } else {
            if (validateHeaderBeforeAdd().equals(0)) {
                OperationBinding valBinding = ADFBeanUtils.getOperationBinding("validateShipmentBeforeAdd");
                valBinding.execute();
                Object o = valBinding.getResult();
                Integer i = (o == null ? -1 : (Integer) o);

                /* OperationBinding binding = ADFBeanUtils.getOperationBinding("addShipmentToInv");
                Object execute = binding.execute();
                Integer i = (execute == null ? -1 : (Integer)execute);
                 */
                if (i.equals(-1)) {
                    ADFModelUtils.showFacesMessage("There have been some error in Adding shipment !",
                                                   "There have been some error in Adding shipment !",
                                                   FacesMessage.SEVERITY_ERROR, null);
                } else if (i.equals(4)) {
                    StringBuffer clientId = new StringBuffer(footerPglBind.getClientId());
                    clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
                    clientId = clientId.append("shipmentIdTransId");
                    System.out.println("Client ID : " + clientId);
                    /* ADFModelUtils.showFacesMessage("Shipment is not selected !", "Please select a Shipment !",
                                                   FacesMessage.SEVERITY_ERROR, clientId.toString()); */
                    ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2096"),
                                                   ADFModelUtils.resolvRsrc("MSG.2097"), FacesMessage.SEVERITY_ERROR,
                                                   clientId.toString());
                } else if (i.equals(5)) {
                    /* ADFModelUtils.showFacesMessage("Duplicate Shipment id have been selected !",
                                                   "Selected shipment is already added to the current Invoice. Please Select another Shipment Id !",
                                                   FacesMessage.SEVERITY_ERROR, shipIdBind.getClientId()); */
                    ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2098"),
                                                   ADFModelUtils.resolvRsrc("MSG.2099"), FacesMessage.SEVERITY_ERROR,
                                                   shipIdBind.getClientId());
                } else {
                    OperationBinding val = getBinding("getCurConvType");
                    val.execute();
                    Object retVal = val.getResult();
                    System.out.println("In Bean ret Val is " + retVal);
                    if (retVal != null) {
                        if ((Integer) retVal == 538 || (Integer) retVal == 539) { // Shipment or Invoice Currency is selected automatically
                            addShipmentACTION(actionEvent);
                        } else { // Manual currency is selected where user need to select the currency
                            OperationBinding obr = ADFBeanUtils.findOperation("chkCurConvSource");
                            obr.execute();
                            if (obr.getResult() != null && (Boolean) obr.getResult()) {
                                showPopup(currConvChkPopUpBind, true);
                            } else {
                                addShipmentACTION(actionEvent);
                            }
                        }
                    }
                }
            }
        }
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

        if (suppOper == null || "".equals(suppOper)) {
            //FacesMessage message2 = new FacesMessage("Please select an Operation !");
            FacesMessage message2 = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2100"));
            message2.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(this.getSuppliOperCbBind().getClientId(), message2);
        } else if (suppType == null || "".equals(suppType)) {
            //FacesMessage message2 = new FacesMessage("Please select Amount Type !");
            FacesMessage message2 = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1833"));
            message2.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(this.getSuppliTypeCbBind().getClientId(), message2);
        } else if (suppNewVal.compareTo(new BigDecimal(0)) <= 0) {
            //FacesMessage message2 = new FacesMessage("Amount should be greater than zero !");
            FacesMessage message2 = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2078"));
            message2.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        } else if ("P".equals(suppType.toString()) && suppNewVal.compareTo(new BigDecimal(100)) > 0) {
            //FacesMessage message2 = new FacesMessage("Percentage should be less than 100 !");
            FacesMessage message2 = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2101"));
            message2.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        } else {
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
        if (suppNewVal == null) {
            suppNewVal = new BigDecimal(0);
        } else {
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

    }

    public void setTransAddBind(RichInputText transAddBind) {
        this.transAddBind = transAddBind;
    }

    public RichInputText getTransAddBind() {
        return transAddBind;
    }

    public void amountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            Number n = StaticValue.NUMBER_ZERO;
            try {
                n = new Number(object);
            } catch (SQLException e) {
            }
            int i = ADFBeanUtils.isAmountValid(n);
            System.out.println("I : " + i);
            if (i == -1) {
                /* throw new ValidatorException(new FacesMessage("Amount Cannot be less than zero!!",
                                                              "Please assign a value which is Greater than Zero !")); */
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2102"),
                                                              ADFModelUtils.resolvRsrc("MSG.2103")));
            } else if (i == -2) {
                /* throw new ValidatorException(new FacesMessage("Invalid Precision Value!!",
                                                              "Please enter a value with valid precision !")); */
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1266"),
                                                              ADFModelUtils.resolvRsrc("MSG.2104")));
            }
        }
    }

    public void qtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Object itmRateO = uIComponent.getAttributes().get("itmRate");
            Object discTypO = uIComponent.getAttributes().get("itmDiscTyp");
            Object discValO = uIComponent.getAttributes().get("itmDiscVal");

            Object oldQtyO = uIComponent.getAttributes().get("oldShipQty");
            Number oldQty = StaticValue.NUMBER_ZERO;
            Number itmRate = StaticValue.NUMBER_ZERO;
            Number discVal = StaticValue.NUMBER_ZERO;
            String discType = (discTypO == null ? "P" : discTypO.toString());

            try {
                if (oldQtyO != null)
                    oldQty = new Number(oldQtyO);
                if (itmRateO != null)
                    itmRate = new Number(itmRateO);
                if (discValO != null)
                    discVal = new Number(discValO);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Number discAmt = (discType.equals("P") ? StaticValue.NUMBER_ZERO : discVal);

            Number n = StaticValue.NUMBER_ZERO;
            try {
                n = new Number(object);
            } catch (SQLException e) {
            }
            int i = ADFBeanUtils.isAmountValid(n);
            System.out.println("I : " + i);

            if (i == -1) {
                /* throw new ValidatorException(new FacesMessage("Quantity Cannot be less than zero!!",
                                                              "Please assign a value which is Greater than Zero !")); */
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2105"),
                                                              ADFModelUtils.resolvRsrc("MSG.2103")));
            } else if (i == -2) {
                /* throw new ValidatorException(new FacesMessage("Invalid Precision Value!!",
                                                              "Please enter a value with valid precision !")); */
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.864"),
                                                              ADFModelUtils.resolvRsrc("MSG.2104")));
            } else if (n.compareTo(StaticValue.NUMBER_ZERO) <= 0) {
                /* throw new ValidatorException(new FacesMessage("Quantity Cannot be zero!!",
                                                              "Please enter quantity greater than zero !")); */
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2107"),
                                                              ADFModelUtils.resolvRsrc("MSG.2108")));
            } else if (n.compareTo(oldQty) > 0) {
                /* throw new ValidatorException(new FacesMessage("Item Quantity cannot be greater than " + oldQty +
                                                              " i.e. Shipment Quantity!!",
                                                              "Please enter valid Quantity !")); */
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2109") + oldQty +
                                                              ADFModelUtils.resolvRsrc("MSG.2110"),
                                                              ADFModelUtils.resolvRsrc("MSG.2111")));
            } else if (!isTotAmtValid(n, itmRate, discAmt)) {
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.489"),
                                                              ADFModelUtils.resolvRsrc("MSG.2465")));
            }
        }
    }

    public Boolean isTotAmtValid(Number qty, Number price, Number disc) {
        Boolean b = true;
        Number n = qty.multiply(price);
        n = ADFBeanUtils.roundOff(n);
        disc = ADFBeanUtils.roundOff(disc);
        if (disc.compareTo(n) > 0) {
            b = false;
        }
        return b;
    }


    public void refreshTaxACTION(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("refreshTax").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxAmountBindvar);
    }

    /**
     * Method to add Sales Order to Service Invoice
     * @param actionEvent
     */
    public void addSoToInvACTION(ActionEvent actionEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(soIdLovBind);
        boolean b = soIdLovBind.isValid();
        if (b == false) {
            String msg2 = "Please select valid Sales Order No.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(soIdLovBind.getClientId(), message2);
        } else {
            /**
             * Method to add Sales Order to Service Invoice
             * @return
             * -1 : There have been some error
             * 0 : Insertion Sucessfull.
             * 1 : Sales Order Id is null.
             * 2 : Duplicate Sales Order have been selected.
             *
             */
            if (validateHeaderBeforeAdd().equals(0)) {
                OperationBinding binding = ADFBeanUtils.getOperationBinding("addSoToInvFrSrvcInv");
                binding.execute();
                Object o = binding.getResult();
                Integer i = (o == null ? -1 : (Integer) o);
                if (i.equals(10)) {
                    /* ADFModelUtils.showFacesMessage("Please select a Sales Order !",
                                                   "Sales Order have not been selected ! Please select a Sales Order to add to Invoice !",
                                                   FacesMessage.SEVERITY_ERROR, soIdLovBind.getClientId()); */
                    ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2112"),
                                                   ADFModelUtils.resolvRsrc("MSG.2113"), FacesMessage.SEVERITY_ERROR,
                                                   soIdLovBind.getClientId());
                } else if (i == 11) {
                    /* ADFModelUtils.showFacesMessage("Duplicate Sales Order !",
                                                   "Selected Sales Order is already added to the current Invoice ! Please select another Sales Order to add to Invoice !",
                                                   FacesMessage.SEVERITY_ERROR, soIdLovBind.getClientId()); */
                    ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2114"),
                                                   ADFModelUtils.resolvRsrc("MSG.2115"), FacesMessage.SEVERITY_ERROR,
                                                   soIdLovBind.getClientId());
                } else if (i == 0) {
                    ADFBeanUtils.findOperation("iterateSlsInvShpItm").execute();
                    soIdLovBind.setValue(null);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(soIdLovBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(invDtlTableBinding);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(footerPglBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(taxAmountBindvar);
                }
            }
        }
    }

    public void setSoIdLovBind(RichInputListOfValues soIdLovBind) {
        this.soIdLovBind = soIdLovBind;
    }

    public RichInputListOfValues getSoIdLovBind() {
        return soIdLovBind;
    }

    public String getExportLink() {
        if (exportLink == null) {
            OperationBinding binding = getBindings().getOperationBinding("getExportLink");
            if (binding != null) {
                binding.execute();
                Object object = binding.getResult();
                exportLink = (object == null ? null : object.toString());
            }
        }
        System.out.println("Export link :" + exportLink);
        return exportLink;
    }


    public String headerCostCenterACL() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            return "headCc";
        } else {
            return null;
        }
    }

    public String costCenterAction() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            return "costCenter";
        } else {
            return null;
        }
    }

    /**
     * Method to add Other charges.
     * @return
     * 0 : Other charges added
     * 1 : Coa Id is null
     * 2 : Other charges Desc is null
     * 3 : Amount type is null
     * 4 : Amount is null
     * 5 : Amount is greater than So Amount
     * 6 : Duplicate Coa
     */
    public void addOtherCharges(ActionEvent ace) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("addOtherCharges");
        binding.execute();
        Object o = binding.getResult();
        Integer i = (o == null ? -1 : (Integer) o);
        System.out.println("Add Other charges returned  :" + i);
        if (i.equals(1)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("coaNmTransId");
            /* ADFModelUtils.showFormattedFacesMessage("Chart Of Account for Other Charges have not been selected !",
                                                    "Please select Chart of Account.", FacesMessage.SEVERITY_ERROR); */
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.2116"),
                                                    ADFModelUtils.resolvRsrc("MSG.493"), FacesMessage.SEVERITY_ERROR);
        } else if (i.equals(6)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("coaNmTransId");
            /* ADFModelUtils.showFormattedFacesMessage("Duplicate Other Charge !",
                                                    "Duplicate Other Charge have been selected. Please select Other Charge which is not already added in Other Charges.",
                                                    FacesMessage.SEVERITY_ERROR); */
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.2117"),
                                                    ADFModelUtils.resolvRsrc("MSG.2118"), FacesMessage.SEVERITY_ERROR);
        } else if (i.equals(2)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("soc24");
            /* ADFModelUtils.showFormattedFacesMessage("Other Charges Desc have not been selected !",
                                                    "Please select Other Charges Desc.", FacesMessage.SEVERITY_ERROR); */
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.2119"),
                                                    ADFModelUtils.resolvRsrc("MSG.2120"), FacesMessage.SEVERITY_ERROR);
        } else if (i.equals(3)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("soc25");
            /* ADFModelUtils.showFormattedFacesMessage("Other Charges Amount Type have not been selected !",
                                                    "Please select Amount Type.", FacesMessage.SEVERITY_ERROR); */
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.2121"),
                                                    ADFModelUtils.resolvRsrc("MSG.1833"), FacesMessage.SEVERITY_ERROR);
        } else if (i.equals(4)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("it78");
            /* ADFModelUtils.showFormattedFacesMessage("Other Charges Amount should be greater than zero !",
                                                    "Please select valid Amount.", FacesMessage.SEVERITY_ERROR); */
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.2122"),
                                                    ADFModelUtils.resolvRsrc("MSG.2123"), FacesMessage.SEVERITY_ERROR);
        } else if (i.equals(5)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("it78");
            /* ADFModelUtils.showFacesMessage("Sum of the Charges Amount should not be greater than Sales Invoice Amount !",
                                           "Please select valid Amount.", FacesMessage.SEVERITY_ERROR,
                                           clientId.toString()); */
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2124"), ADFModelUtils.resolvRsrc("MSG.2123"),
                                           FacesMessage.SEVERITY_ERROR, clientId.toString());
            ////;;
        }
    }

    /**
     * Method to delete Other Charges
     * @param actionEvent
     */
    public void deleteOtherChargesACTION(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("deleteOtherCharges").execute();
    }

    // ACE created by Mousham on 24th June 20115- to get taxRuleId
    public void itemWiseTaxACE(ActionEvent actionEvent) {
        if (mode != "V") {
            OperationBinding ob = ADFBeanUtils.findOperation("checkTrforItem");
            System.out.println("tax rule for this itme ===>>> " +
                               actionEvent.getComponent().getAttributes().get("itmId"));
            ob.getParamsMap().put("itmId", actionEvent.getComponent().getAttributes().get("itmId"));
            ob.execute();
        }
        showPopup(taxItemwisePopup, true);

    }

    public void filterOcDiscloserList(DisclosureEvent disclosureEvent) {
        ADFBeanUtils.getOperationBinding("filterAutoOtherChargesVo").execute();
    }

    public void ocAmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (!ADFBeanUtils.isPrecisionValid((Number) object)) {
                /* throw new ValidatorException(new FacesMessage("Invalid Precision Value!!",
                                                              "Please enter a value with valid precision !")); */
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1266"),
                                                              ADFModelUtils.resolvRsrc("MSG.2104")));
            }
            if (ADFBeanUtils.isNumberNegative((Number) object)) {
                /* throw new ValidatorException(new FacesMessage("Amount Cannot be negative!",
                                                              "Please enter valid amount !")); */
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2125"),
                                                              ADFModelUtils.resolvRsrc("MSG.2123")));
            }
        }

    }

    /**
     * Method to check if Invoice can be cancelled or not
     * @param ace
     */
    public void cancelInvoiceFirstCheckACTION(ActionEvent ace) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("isPymntRecvedAgnstInv");
        if (binding != null) {
            binding.execute();
            Object object = binding.getResult();
            Boolean b = (object == null ? true : (Boolean) object);
            if (!b) {
                ADFBeanUtils.showPopup(cancelPopBind, true);
            }
        }
    }

    /**
     * Exempted Flag ItemWise Value Changes Listner
     * @param valueChangeEvent
     */
    public void expmtedFlgItmWiseVCL(ValueChangeEvent valueChangeEvent) {
        if (taxRuleIdItem.getValue() != null && taxableAmountForItem.getValue() != null) {
            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
            BigDecimal taxableAmount = (BigDecimal) taxableAmountForItem.getValue();
            taxableAmount = taxableAmount.setScale(amt_digit, RoundingMode.HALF_UP);
            OperationBinding create = getBinding("insertIntoSlsInvTrLineForItemwise");
            create.getParamsMap().put("p_tax_rule_id", taxRuleIdItem.getValue());
            create.getParamsMap().put("p_taxable_amount", taxableAmount);
            create.execute();
        }
    }

    public void expmtedFlgInvoiceWiseVCL(ValueChangeEvent valueChangeEvent) {
        if (taxRuleIdBindVar.getValue() != null && taxableAmountBindVar.getValue() != null) {
            StringBuilder d =
                (taxRuleIdBindVar.getValue() == "" ? new StringBuilder("") :
                 new StringBuilder(taxRuleIdBindVar.getValue().toString()));
            if (!"".equals(d.toString())) {
                valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
                BigDecimal taxableAmount = (BigDecimal) taxableAmountBindVar.getValue();
                taxableAmount = taxableAmount.setScale(amt_digit, RoundingMode.HALF_UP);
                OperationBinding create = getBinding("insertIntoSlsInvTrLineForInvoicewise");
                create.getParamsMap().put("p_tax_rule_id", taxRuleIdBindVar.getValue());
                create.getParamsMap().put("p_taxable_amount", taxableAmount);
                create.execute();
            }
        }
    }

    public void asblAmtSpVCL(ValueChangeEvent valueChangeEvent) {
        ADFBeanUtils.findOperation("resetTaxableAmount").execute();
    }

    public void searchShipForIntmACE(ActionEvent actionEvent) {
        ADFBeanUtils.showPopup(showShipmSearchForIntmPopBind, true);

    }

    public void setShowShipmSearchForIntmPopBind(RichPopup showShipmSearchForIntmPopBind) {
        this.showShipmSearchForIntmPopBind = showShipmSearchForIntmPopBind;
    }

    public RichPopup getShowShipmSearchForIntmPopBind() {
        return showShipmSearchForIntmPopBind;
    }


    public void selectIntimationACE(ActionEvent actionEvent) {
        OperationBinding o = ADFBeanUtils.findOperation("putIntimation");
        o.getParamsMap().put("intmDocId", actionEvent.getComponent().getAttributes().get("docId"));
        o.getParamsMap().put("intmDispId", actionEvent.getComponent().getAttributes().get("dispId"));
        o.execute();
        showShipmSearchForIntmPopBind.hide();
        ADFBeanUtils.showPopup(showShipmSearchForIntmPopBind, false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(intimationSelectGroupBinding);
    }

    public void searchIntimationForShipmentACE(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("filterShipForIntm").execute();
    }

    public void setIntimationSelectGroupBinding(RichPanelGroupLayout intimationSelectGroupBinding) {
        this.intimationSelectGroupBinding = intimationSelectGroupBinding;
    }

    public RichPanelGroupLayout getIntimationSelectGroupBinding() {
        return intimationSelectGroupBinding;
    }

    public void itmRateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            BigDecimal o = (BigDecimal) object;
            if (o.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.489"),
                                                              ADFModelUtils.resolvRsrc("MSG.315")));
            }
        }
    }

    public void newRateSuplimntryValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("Inside validator ");
        if (object != null) {
            Object oldRateO = uIComponent.getAttributes().get("oldRate");
            Object oprTypO = uIComponent.getAttributes().get("oprTyp");
            Number newRate = new Number(0);
            Number oldRate = new Number(0);
            System.out.println(oldRateO + " ::::::: New Rate");
            System.out.println(oprTypO + " ::::::: Old Rate");
            try {

                newRate = new Number(object);
                oldRate = oldRateO == null ? StaticValue.NUMBER_ZERO : new Number(oldRateO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(oprTypO + " ::: Operation Type");
            System.out.println(newRate + " ::::::: New Rate");
            System.out.println(oldRate + " ::::::: Old Rate");
            if ("A".equalsIgnoreCase(oprTypO.toString())) {
                if (newRate.compareTo(oldRate) < 0) {
                    throw new ValidatorException(new FacesMessage("New Rate is Less",
                                                                  "New Rate must be more than Old rate, Since Opertion type selected is Addition"));
                }
            } else if ("S".equalsIgnoreCase(oprTypO.toString())) {
                if (newRate.compareTo(oldRate) > 0) {
                    throw new ValidatorException(new FacesMessage("New Rate is More",
                                                                  "New Rate must be less than Old rate, Since Opertion type selected is Subtract"));
                }
            }

            if (newRate.compareTo(StaticValue.NUMBER_ZERO) < 0) {
                throw new ValidatorException(new FacesMessage("Negative Value", " Rate must not be in negative"));
            }


        }
    }
    /**
     * Method to set parameters for Cost Center
     * @param ace
     */
    public void calcCostCenterACTION(ActionEvent ace) {
        Object ccId = ace.getComponent().getAttributes().get("ccId");
        Object amtSp = ace.getComponent().getAttributes().get("amtSp");
        ccCcId = (ccId == null ? null  : ccId.toString());
        ccAmtSp = (amtSp == null ? BigDecimal.ZERO : (BigDecimal)amtSp);
    }
}
