package scop.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.bean.StaticValue;
import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class AddEditSubContractBean {
    private RichInputText itemPriceBind;
    private RichInputText schdlQuantTrans;
    private RichTable deliveryScheduleTable;
    private String wfId = null;
    private RichInputDate transDelvryDateBind;
    private RichInputDate scoOrderDateBind;
    private RichInputText operationSrNoBind;
    private RichTable bomOperationTableBind;
    private RichSelectBooleanCheckbox principalItemBind;
    private RichPanelFormLayout itemPanelFormBind;
    private RichPopup orderWiseTaxPopupBind;
    private RichPopup itemWisePopupBind;
    private RichSelectOneChoice scoBasisBindVar;
    private RichSelectOneChoice scoTypeBindVar;
    private RichSelectOneChoice orderWiseTrRuleBind;
    private RichSelectOneChoice itemWiseTrRuleBind;
    private RichInputDate paymentSchdlDateBind;
    private Boolean enableCostCenter = null;

    /**Under Constructor of This Class calling View_Other_Charges
     * Method for Showing In Create And View Mode
     * */
    public AddEditSubContractBean() {
        ADFBeanUtils.findOperation("viewOtherChargesVw").execute(); //To execute other charges view
    }

    /**
     * Getter for Item Price Editable
     * */
    public String getEditableItemPrice() {
        OperationBinding binding = ADFBeanUtils.findOperation("allowEditInServiceItemPrice");
        binding.execute();
        System.out.println("Value of Price Edit Field :: " + (String) binding.getResult());
        return (String) binding.getResult();
    }

    /**
     * Getter for taxable amount Editable
     * */
    public String getEditableTaxableAmt() {
        OperationBinding binding = ADFBeanUtils.findOperation("allowEditInTaxableAmount");
        binding.execute();
        System.out.println("Value of Taxable Amount Field :: " + (String) binding.getResult());
        return (String) binding.getResult();
    }

    /**
     * Get Current Today Date
     * */
    public oracle.jbo.domain.Timestamp getTodayDate() {
        return StaticValue.getTruncatedCurrDt();
    }

    /**Method for get Binding Object
     * */
    OperationBinding ob = null;

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**Page Mode Change In Create Mode
     * */
    public void changePageMode_Create() {
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("ADD_VIEW_MODE", "C");
    }

    /**Page Mode Change In View Mode
     * */
    public void changePageMode_View() {
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("ADD_VIEW_MODE", "V");
    }

    /**Commit Method
     * This Method Contain Call Work Flow Function
     * And Used In Save & Save and Forward Method Links
     * */
    public void commitSubContractApp() {
        ob = ADFBeanUtils.findOperation("Commit");
        Object execute = ob.execute();
        if (execute == null) {
            setDocTypeValue();
            changePageMode_View();
            StringBuilder message = new StringBuilder();
            message.append("<P>Your Sub Contracting Order Saved Successfully.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Sub Contracting Order", message.toString(),
                                                    FacesMessage.SEVERITY_INFO);
            callFuncWf();
            if (getEnableCostCenter()) {
                // System.out.println("Cost Center is enable ");
                ADFBeanUtils.findOperation("updateCostCenterOnSave").execute();
                //Inserting Cost center data to sls cost center
                ADFBeanUtils.getOperationBinding("sendDateFromTempCcToMmScoCc").execute();
            }
            ADFBeanUtils.findOperation("Commit").execute();
        } else {
            changePageMode_Create();
            StringBuilder message = new StringBuilder();
            message.append("<P>Unable to save your Order.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Sub Contracting Order", message.toString(),
                                                    FacesMessage.SEVERITY_ERROR);
        }
    }

    /*---------------------------------Set Value of DocTypeId-----------------------------------*/
    public void setDocTypeValue() {
        ADFBeanUtils.findOperation("setDocTypeValue").execute();
    }

    /**
     * Method to fetch Component client id
     * @param comp
     * @param id
     * @return
     */
    public String getComponentCliendIdFromId(UIComponent comp, String id) {
        if (comp != null) {
            StringBuilder clientId = new StringBuilder(comp.getClientId());
            clientId = new StringBuilder(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            return clientId.append(id).toString();
        } else {
            return null;
        }
    }

    /**
     * Method to validate Header before saving
     * @return
     */
    public Integer HeaderMandatoryFields(UIComponent ui) {
        ob = ADFBeanUtils.findOperation("validateHeaderMandatoryFields");
        ob.execute();
        Object object = ob.getResult();
        Integer i = object == null ? -1 : (Integer) object;
        if (i.equals(1)) {
            ADFModelUtils.showFacesMessage("Sub Contracting Type not selected !", "Please select Sc Type.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "soc4"));
        } else if (i.equals(2)) {
            ADFModelUtils.showFacesMessage("Sub Contracting Basis not selected !", "Please select Sc Basis.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "soc1"));
        } else if (i.equals(3)) {
            ADFModelUtils.showFacesMessage("Entity not selected !", "Please select Entity.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "transEoNmId"));
        } else if (i.equals(4)) {
            ADFModelUtils.showFacesMessage("Currency not selected !", "Please select Currency.",
                                           FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(ui, "transCurrNmId"));
        } else if (i.equals(5)) {
            ADFModelUtils.showFacesMessage("External Doc No Required !", "Please Enter External Doc No.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "it3"));
        } else if (i.equals(6)) {
            ADFModelUtils.showFacesMessage("External Doc Date Required !", "Please Enter External Doc Date.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "id4"));
        }
        return i;
    }

    /**Save Button Action Event
     * Call Commit Method
     * */
    public void saveACL(ActionEvent actionEvent) {
        Integer i = HeaderMandatoryFields(scoOrderDateBind);
        if (i == 0) {
            Integer Tabcount = bomOperationTableBind.getRowCount();
            if (Tabcount == 0) {
                StringBuilder message = new StringBuilder();
                message.append("<P>Please Add Operation.</P><BR>");
                ADFModelUtils.showFormattedFacesMessage("Sub Contracting Order", message.toString(),
                                                        FacesMessage.SEVERITY_INFO);
            } else {
                Integer j = validateOpItems(principalItemBind);
                if (j == 0) {
                    commitSubContractApp();
                }
            }
        }
    }


    /**Save & Forward Button Action Event
     * Call Commit Method
     * */
    public String saveAndforwardACL() {
        String s = null;
        Integer i = HeaderMandatoryFields(scoOrderDateBind);
        if (i == 0) {
            Integer Tabcount = bomOperationTableBind.getRowCount();
            if (Tabcount == 0) {
                StringBuilder message = new StringBuilder();
                message.append("<P>Please Add Operation.</P><BR>");
                ADFModelUtils.showFormattedFacesMessage("Sub Contracting Order", message.toString(),
                                                        FacesMessage.SEVERITY_INFO);
            } else {
                Integer j = ItemCheckOnSaveAndForward(principalItemBind);
                if (j == 0) {
                    Integer k = validateOpItems(principalItemBind);
                    if (k == 0) {
                        Integer m = TaxChkBeforeSaveAndForward(scoOrderDateBind);
                        if (m == 0) {
                            Integer n = CheckDefDeliverySchdl(transDelvryDateBind);
                            if (n == 0) {
                                Integer l = CheckDefaultPaymentSchdl(paymentSchdlDateBind);
                                if (l == 0) {
                                    GenDefaultDeliverySchedule();
                                    OrderCalcFunc();
                                    GenDefaultPaymentSchedule();
                                    commitSubContractApp();
                                    return "workFlowGo";
                                }
                            }
                        }
                    }
                }
            }
        }
        return s;
    }

    /**
     * Validate Tax Before Save And Forward
     * */
    public Integer TaxChkBeforeSaveAndForward(UIComponent ui) {
        ob = ADFBeanUtils.findOperation("ValidateTaxBeforeSaveAndForward");
        ob.execute();
        Object object = ob.getResult();
        Integer i = object == null ? -1 : (Integer) object;
        if (i.equals(1)) {
            StringBuilder message = new StringBuilder();
            message.append("<P>Apply Order Wise Tax.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Sub Contracting Order", message.toString(),
                                                    FacesMessage.SEVERITY_INFO);
        } else if (i.equals(2)) {
            StringBuilder message = new StringBuilder();
            message.append("<P>Apply Item Wise Tax.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Sub Contracting Order", message.toString(),
                                                    FacesMessage.SEVERITY_INFO);
        }
        return i;
    }

    /**
     * Check default Delivery Schedule condition.
     * */
    public Integer CheckDefDeliverySchdl(UIComponent ui) {
        ob = ADFBeanUtils.findOperation("ValidateDefDeliverySchdl");
        ob.execute();
        Object object = ob.getResult();
        Integer i = object == null ? -1 : (Integer) object;
        if (i.equals(1)) {
            StringBuilder message = new StringBuilder();
            message.append("<P>Please Add Delivery Schedule for Other Items.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Sub Contracting Order", message.toString(),
                                                    FacesMessage.SEVERITY_INFO);
        }
        return i;
    }

    /**
     * Validation of Item on Save and Forward
     * */
    public Integer ItemCheckOnSaveAndForward(UIComponent ui) {
        ob = ADFBeanUtils.findOperation("ValidateCheckOnSaveAndForward");
        ob.execute();
        Object object = ob.getResult();
        Integer i = object == null ? -1 : (Integer) object;
        if (i.equals(1)) {
            StringBuilder message = new StringBuilder();
            message.append("<P>Please Add Input Items.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Sub Contracting Order", message.toString(),
                                                    FacesMessage.SEVERITY_INFO);
        } else if (i.equals(2)) {
            StringBuilder message = new StringBuilder();
            message.append("<P>Please Add Output Items.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Sub Contracting Order", message.toString(),
                                                    FacesMessage.SEVERITY_INFO);
        }
        return i;
    }

    /**Edit Button Action Event
     * It Check Document Approved or Not
     * On this basis edit button enable/disable
     * */
    public void editACL(ActionEvent actionEvent) {
        setDocTypeValue();
        Integer usr_Id = EbizParams.GLBL_APP_USR();
        Integer sloc_id = EbizParams.GLBL_APP_SERV_LOC();
        Integer pending = 0;
        ob = ADFBeanUtils.findOperation("getDocUsrFromWF");
        ob.execute();
        Integer chkUsr = (Integer) ob.getResult();
        if (chkUsr != null) {
            pending = chkUsr;
        }
        if (pending.compareTo(usr_Id) == 0) {
            changePageMode_Create();
        } else if (pending.compareTo(new Integer(-1)) == 0) {
            StringBuilder msg = new StringBuilder();
            msg.append("<p>Sub Contracting Order has been Approved, You can not edit this Order.</p>");
            ADFModelUtils.showFormattedFacesMessage("Sub Contracting Order", msg.toString(),
                                                    FacesMessage.SEVERITY_INFO);
        } else {
            ob = ADFBeanUtils.findOperation("getUserName");
            ob.getParamsMap().put("u_Id", pending);
            ob.getParamsMap().put("slc_id", sloc_id);
            ob.execute();
            if (ob.getResult() != null) {
                StringBuilder mssg = new StringBuilder();
                mssg.append("<p>This Sub Contracting Order is pending at other user<b><i> [ " +
                            ob.getResult().toString() + "] </i></b>for approval, You can not edit this document.</p>");
                ADFModelUtils.showFormattedFacesMessage("Sub Contracting Order", mssg.toString(),
                                                        FacesMessage.SEVERITY_INFO);
            }
        }
    }

    /**Create New Order Button Action Event In Create Page
     * Call HexDocNo Function and set value on hexadecimal number
     * Than Create and Insert Call In Header Table
     * Change Page Mode In Create Mode
     * */
    public void createNewOrderACL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("GetHexDocNo").execute();
        ADFBeanUtils.findOperation("CreateInsertInMmSco").execute();
        changePageMode_Create();
    }

    /**
     * Method to validate Delivery Scheduling
     * @return
     */
    public Integer validateBomOperation(UIComponent ui) {
        ob = ADFBeanUtils.findOperation("validateBomOperationFields");
        ob.execute();
        Object object = ob.getResult();
        Integer i = object == null ? -1 : (Integer) object;
        if (i.equals(1)) {
            ADFModelUtils.showFacesMessage("BOM not selected !", "Please select BOM.", FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(ui, "transBomIdId"));
        } else if (i.equals(2)) {
            ADFModelUtils.showFacesMessage("Operation not selected !", "Please select Operation.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "transOpIdId"));
        } else if (i.equals(3)) {
            ADFModelUtils.showFacesMessage("Duplicate Operation not Allowed !", "Please select Another Operation.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "transOpIdId"));
        }
        return i;
    }

    /**Add Operation Action Event
     * For disable add operation link check expression on customer NULL
     * Same Expression check for popup disable on showpopup behaviour disable property
     * */
    public void addOperationACL(ActionEvent actionEvent) {
        Integer i = HeaderMandatoryFields(scoOrderDateBind);
        if (i == 0) {
            Integer j = validateBomOperation(operationSrNoBind);
            if (j == 0) {
                ADFBeanUtils.findOperation("AddOperationFunc").execute();
            }
        }
    }

    /**
     * Method to validate Operation Items
     * @return
     */
    public Integer validateOpItems(UIComponent ui) {
        ob = ADFBeanUtils.findOperation("validateOperationItems");
        ob.execute();
        Object object = ob.getResult();
        Integer i = object == null ? -1 : (Integer) object;
        if (i.equals(1)) {
            ADFModelUtils.showFacesMessage("Item not selected !", "Please select Item.", FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(ui, "transItemDescId"));
        } else if (i.equals(2)) {
            ADFModelUtils.showFacesMessage("Price not selected !", "Please Enter Price.", FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(ui, "it19"));
        } else if (i.equals(3)) {
            ADFModelUtils.showFacesMessage("Price should be greater than Zero(0) !", "Please Enter Price.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "it19"));
        } else if (i.equals(4)) {
            ADFModelUtils.showFacesMessage("Negative Price Not Allowed !", "Negative numbers are not allowed.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "it19"));
        } else if (i.equals(5)) {
            ADFModelUtils.showFacesMessage("Invalid Precision. !",
                                           "Invalid Precision. Please enter valid precision(26, 6).",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "it19"));
        } else if (i.equals(6)) {
            ADFModelUtils.showFacesMessage("Quantity not selected !", "Please Enter Quantity.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "it20"));
        } else if (i.equals(7)) {
            ADFModelUtils.showFacesMessage("Quantity should be greater than Zero(0) !", "Please Enter Quantity.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "it20"));
        } else if (i.equals(8)) {
            ADFModelUtils.showFacesMessage("Negative Quantity Not Allowed !", "Negative numbers are not allowed.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "it20"));
        } else if (i.equals(9)) {
            ADFModelUtils.showFacesMessage("Invalid Precision. !",
                                           "Invalid Precision. Please enter valid precision(26, 6).",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "it20"));
        } else if (i.equals(10)) {
            ADFModelUtils.showFacesMessage("Taxable Amount not selected !", "Please Enter Taxable Amount.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "it22"));
        } else if (i.equals(11)) {
            ADFModelUtils.showFacesMessage("Taxable Amount should be greater than Zero(0) !",
                                           "Please Enter Taxable Amount.", FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(ui, "it22"));
        } else if (i.equals(12)) {
            ADFModelUtils.showFacesMessage("Negative Taxable Amount Not Allowed !", "Negative numbers are not allowed.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "it22"));
        } else if (i.equals(13)) {
            ADFModelUtils.showFacesMessage("Invalid Precision. !",
                                           "Invalid Precision. Please enter valid precision(26, 6).",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "it22"));
        }
        return i;
    }

    /*------------------------Add Item ---------------------------------------*/
    public void addItemACL(ActionEvent actionEvent) {
        Integer i = validateOpItems(principalItemBind);
        if (i == 0) {
            ADFBeanUtils.findOperation("CreateInsertInItem").execute();
        }
    }

    public void setItemPriceBind(RichInputText itemPriceBind) {
        this.itemPriceBind = itemPriceBind;
    }

    public RichInputText getItemPriceBind() {
        return itemPriceBind;
    }

    /**Add Other Charges Action Event
     * Create And Insert Perform In Other Charges table
     * Call Add_Other_Charges method for set Values of other fields
     * */
    public void addOtherChargesACL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("CreateInsertInOc").execute();
        ADFBeanUtils.findOperation("addOtherCharges").execute();
    }

    /**Select All Items In delivery Schedule Action Event
     * */
    public void selectAllAction(ActionEvent actionEvent) {
        ob = ADFBeanUtils.findOperation("selectAllCheckBox");
        ob.getParamsMap().put("val", true);
        ob.execute();
    }

    /**UnSelect All Items In delivery Schedule Action Event
     * */
    public void deSelectAllAction(ActionEvent actionEvent) {
        ob = ADFBeanUtils.findOperation("selectAllCheckBox");
        ob.getParamsMap().put("val", false);
        ob.execute();
    }

    public void setSchdlQuantTrans(RichInputText schdlQuantTrans) {
        this.schdlQuantTrans = schdlQuantTrans;
    }

    public RichInputText getSchdlQuantTrans() {
        return schdlQuantTrans;
    }

    /*---------------------------------Check box Delivery value change listner-----------------------------*/
    public void selectCheckDlvSchdVCE(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if ("true".equalsIgnoreCase(vce.getNewValue().toString())) {
                ADFBeanUtils.findOperation("setBalanceQtyToSchdlQty").execute();
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(schdlQuantTrans);
    }

    /*------------------------------Warehouse change listner-----------------------------*/
    public void whChngeVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            OperationBinding binding = this.getBindings().getOperationBinding("setWhIdInDlvSchVw");
            binding.getParamsMap().put("WhId", new StringBuffer(valueChangeEvent.getNewValue().toString()));
            binding.execute();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(deliveryScheduleTable);
    }

    public void setDeliveryScheduleTable(RichTable deliveryScheduleTable) {
        this.deliveryScheduleTable = deliveryScheduleTable;
    }

    public RichTable getDeliveryScheduleTable() {
        return deliveryScheduleTable;
    }

    public void setWfId(String wfId) {
        this.wfId = wfId;
    }

    public String getWfId() {
        return wfId;
    }

    /*----------------------Call Function callWfFunction----------------------*/
    public void callFuncWf() {
        callWfId();
        ADFBeanUtils.findOperation("callWfFunctions").execute();
    }

    /*---------------------Call callWfId Function------------------------------*/
    public void callWfId() {
        ob = ADFBeanUtils.findOperation("getWfId");
        ob.execute();
        String wId = (String) ob.getResult();
        setWfId(wId);
    }

    /**
     * Code for Precision Check
     */
    public Boolean isPrecisionValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    /*--------------------------------------Other Charges Amount Validator----------------------------------*/
    public void otherChargesAmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            if (val.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Negative numbers are not allowed.", null));
            }
            if (val.compareTo(0) == 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please Enter Amount.",
                                                              null));
            }
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please Enter Amount.", null));
        }
    }


    /**
     * Method to validate Delivery Scheduling
     * @return
     */
    public Integer DeliveryScheduleFields(UIComponent ui) {
        ob = ADFBeanUtils.findOperation("validateDelSchdlParams");
        ob.execute();
        Object object = ob.getResult();
        Integer i = object == null ? -1 : (Integer) object;
        if (i.equals(1)) {
            ADFModelUtils.showFacesMessage("Delivery Date not selected !", "Please select Delivery date.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "id7"));
        } else if (i.equals(2)) {
            ADFModelUtils.showFacesMessage("Delivery Mode not selected !", "Please select Delivery Mode.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "soc26"));
        } else if (i.equals(3)) {
            ADFModelUtils.showFacesMessage("Warehouse not selected !", "Please select Warehouse.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "soc27"));
        } else if (i.equals(4)) {
            ADFModelUtils.showFacesMessage("Project not selected !", "Please select Project.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "soc35"));
        } else if (i.equals(5)) {
            StringBuilder message = new StringBuilder();
            message.append("<P>Please select items for delivery schedule.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Sub Contracting Order", message.toString(),
                                                    FacesMessage.SEVERITY_INFO);
        } else if (i.equals(6)) {
            StringBuilder message = new StringBuilder();
            message.append("<P>Duplicate Delivery Schedule.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Sub Contracting Order", message.toString(),
                                                    FacesMessage.SEVERITY_INFO);
        }
        return i;
    }

    /**
     * Add Delivery Schedule of Items
     * */
    public void addDelvSchdlACL(ActionEvent actionEvent) {
        Integer i = validateOpItems(principalItemBind);
        if (i == 0) {
            Integer j = DeliveryScheduleFields(transDelvryDateBind);
            if (j == 0) {
                ob = ADFBeanUtils.findOperation("CreateInsertInScoDlvSchdl");
                Object execute = ob.execute();
                if (execute == null) {
                    PopulateItemsForDelSchdlACL(actionEvent);
                }
            }
        }
    }

    /**
     * Populate Items for Delivery Schedule
     * */
    public void PopulateItemsForDelSchdlACL(ActionEvent actionEvent) {
        Integer i = validateOpItems(principalItemBind);
        if (i == 0) {
            ADFBeanUtils.findOperation("executeDlvSchdlView").execute();
        }
    }

    /**
     * Generate Default Delivery Schedule
     * */
    public void GenDefaultDeliverySchedule() {
        ADFBeanUtils.findOperation("executeDlvSchdlView").execute();
        ADFBeanUtils.findOperation("GenDefaultDeliveryScheduleFunction").execute();
        ADFBeanUtils.findOperation("executeDlvSchdlView").execute();
    }


    /**
     * Delete Delivery Schedule
     * */
    public void deleteDelvScheduleACL(ActionEvent actionEvent) {
        ob = ADFBeanUtils.findOperation("DeleteDlvSchdl");
        Object execute = ob.execute();
        if (execute == null) {
            PopulateItemsForDelSchdlACL(actionEvent);
        }
    }

    /**
     * Delete Operation for remove Current Row of Operation
     * Delete for Remove Items
     * */
    public void DeleteOperationACL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("DeleteOperation").execute();
    }

    /**
     * Generalized Method for Show Hide Popup
     * */
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
     * applyOrderWiseTaxAction Link Action Listner
     * */
    public void applyOrderWiseTaxAction(ActionEvent actionEvent) {
        Integer i = HeaderMandatoryFields(scoOrderDateBind);
        if (i == 0) {
            Integer applyTax = beforeApplyTax();
            if (applyTax == 0) {
                Integer j = validateOpItems(principalItemBind);
                if (j == 0) {
                    showPopup(orderWiseTaxPopupBind, true);
                }
            }
        }
    }

    /**
     * ApplyItemWiseTax Link Action Listner
     * */
    public void applyItemWiseTaxAction(ActionEvent actionEvent) {
        Integer i = HeaderMandatoryFields(scoOrderDateBind);
        if (i == 0) {
            Integer applyTax = beforeApplyTax();
            if (applyTax == 0) {
                Integer j = validateOpItems(principalItemBind);
                if (j == 0) {
                    showPopup(itemWisePopupBind, true);
                }
            }
        }
    }

    /**
     * Validation before apply tax
     * */
    public Integer beforeApplyTax() {
        OperationBinding binding = ADFBeanUtils.findOperation("beforeApplyTaxCheckItem");
        binding.execute();
        Object object = binding.getResult();
        Integer i = object == null ? -1 : (Integer) object;
        if (i.equals(1)) {
            StringBuilder message = new StringBuilder();
            message.append("<P>Please Add Item before applying Tax.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Sub Contracting Order", message.toString(),
                                                    FacesMessage.SEVERITY_INFO);
        }
        return i;
    }

    /**
     * Item Wise Tax Rule Id Value Change Listner
     * */
    public void ItemTaxRuleVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            ob = ADFBeanUtils.findOperation("ItemWiseTaxOrder");
            ob.getParamsMap().put("ruleId", vce.getNewValue());
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemWiseTrRuleBind);
        }
    }

    public void setTransDelvryDateBind(RichInputDate transDelvryDateBind) {
        this.transDelvryDateBind = transDelvryDateBind;
    }

    public RichInputDate getTransDelvryDateBind() {
        return transDelvryDateBind;
    }

    public void setScoOrderDateBind(RichInputDate scoOrderDateBind) {
        this.scoOrderDateBind = scoOrderDateBind;
    }

    public RichInputDate getScoOrderDateBind() {
        return scoOrderDateBind;
    }

    public void setOperationSrNoBind(RichInputText operationSrNoBind) {
        this.operationSrNoBind = operationSrNoBind;
    }

    public RichInputText getOperationSrNoBind() {
        return operationSrNoBind;
    }

    public void setBomOperationTableBind(RichTable bomOperationTableBind) {
        this.bomOperationTableBind = bomOperationTableBind;
    }

    public RichTable getBomOperationTableBind() {
        return bomOperationTableBind;
    }

    public void setPrincipalItemBind(RichSelectBooleanCheckbox principalItemBind) {
        this.principalItemBind = principalItemBind;
    }

    public RichSelectBooleanCheckbox getPrincipalItemBind() {
        return principalItemBind;
    }

    /**
     * Duplicate Item Name Validator
     * */
    public void ItemNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            /*  OperationBinding binding = ADFBeanUtils.findOperation("ValidateItemDuplicate");
            binding.getParamsMap().put("ItmNm", object.toString());
            binding.execute();
            Integer i = (Integer) binding.getResult();
            System.out.println("Value in Validator ---- " + i);
            if (i == 2) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Item Found.",
                                                              null));
            } */



        }
    }

    public void setItemPanelFormBind(RichPanelFormLayout itemPanelFormBind) {
        this.itemPanelFormBind = itemPanelFormBind;
    }

    public RichPanelFormLayout getItemPanelFormBind() {
        return itemPanelFormBind;
    }

    /**
     * Value Change Listner for Item type
     * Getting Value Item Type
     * */
    public void ItemTypeValueChangeListner(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if ((Integer) vce.getNewValue() == 830) {
                this.principalItemBind.setDisabled(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(principalItemBind);
            } else {

                this.principalItemBind.setDisabled(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(principalItemBind);
            }
        }
    }

    /**
     * Inward Input Item Delete
     * */
    public void DeleteInwardInputItem(ActionEvent actionEvent) {
        ob = ADFBeanUtils.findOperation("DeleteInwardOutwardItem");
        ob.getParamsMap().put("voName", "MmScoOpItmVOInwardInput");
        ob.execute();
        ADFBeanUtils.findOperation("deleteCostCenterItem").execute();
    }


    /**
     * Outward Input Item Delete
     * */
    public void DeleteOutwardInputItem(ActionEvent actionEvent) {
        ob = ADFBeanUtils.findOperation("DeleteInwardOutwardItem");
        ob.getParamsMap().put("voName", "MmScoOpItmVOOutwardInput");
        ob.execute();
        ADFBeanUtils.findOperation("deleteCostCenterItem").execute();
    }

    /**
     * Inward Output Item Delete
     * */
    public void DeleteInwardOutputItem(ActionEvent actionEvent) {
        ob = ADFBeanUtils.findOperation("DeleteInwardOutwardItem");
        ob.getParamsMap().put("voName", "MmScoOpItmVOInwardOutput");
        ob.execute();
        ADFBeanUtils.findOperation("deleteCostCenterItem").execute();
    }

    /**
     * Outward Output Item Delete
     * */
    public void DeleteOutwardOutputItem(ActionEvent actionEvent) {
        ob = ADFBeanUtils.findOperation("DeleteInwardOutwardItem");
        ob.getParamsMap().put("voName", "MmScoOpItmVOOutwardOutput");
        ob.execute();
        ADFBeanUtils.findOperation("deleteCostCenterItem").execute();
    }

    public void setOrderWiseTaxPopupBind(RichPopup orderWiseTaxPopupBind) {
        this.orderWiseTaxPopupBind = orderWiseTaxPopupBind;
    }

    public RichPopup getOrderWiseTaxPopupBind() {
        return orderWiseTaxPopupBind;
    }

    public void setItemWisePopupBind(RichPopup itemWisePopupBind) {
        this.itemWisePopupBind = itemWisePopupBind;
    }

    public RichPopup getItemWisePopupBind() {
        return itemWisePopupBind;
    }

    public void ItemWiseTaxPopupCloseACL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("executeHeaderCalView").execute();
        showPopup(itemWisePopupBind, false);
    }

    public void setScoBasisBindVar(RichSelectOneChoice scoBasisBindVar) {
        this.scoBasisBindVar = scoBasisBindVar;
    }

    public RichSelectOneChoice getScoBasisBindVar() {
        return scoBasisBindVar;
    }

    public void setScoTypeBindVar(RichSelectOneChoice scoTypeBindVar) {
        this.scoTypeBindVar = scoTypeBindVar;
    }

    public RichSelectOneChoice getScoTypeBindVar() {
        return scoTypeBindVar;
    }

    public void EntityVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(scoTypeBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(scoBasisBindVar);
        }
    }

    public void OrderWiseTaxApplyACL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("executeHeaderCalView").execute();
        showPopup(orderWiseTaxPopupBind, false);
    }

    public void OrderWiseResetTaxACL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("ResetOrderWiseTax").execute();
        ADFBeanUtils.findOperation("executeHeaderCalView").execute();
        showPopup(orderWiseTaxPopupBind, false);
    }

    public void OrderWiseTaxVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding binding = ADFBeanUtils.findOperation("ApplyOrderWiseTax");
            binding.getParamsMap().put("ruleId", vce.getNewValue());
            binding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(orderWiseTrRuleBind);
        }
    }

    public void ResetTaxItemWiseACL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("ResetItemWiseTax").execute();
        ADFBeanUtils.findOperation("executeHeaderCalView").execute();
        showPopup(orderWiseTaxPopupBind, false);
    }

    /**
     * Set Current Row In Inward Input
     * */
    public void InwardInputSetRowACL(ActionEvent actionEvent) {
        Integer i = validateOpItems(principalItemBind);
        if (i == 0) {
            String itmId = actionEvent.getComponent().getAttributes().get("ItemId").toString();
            ob = ADFBeanUtils.findOperation("MakeCurrentRowInMmScoOpItemTable");
            ob.getParamsMap().put("ItemId", itmId);
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemPanelFormBind);
        }
    }

    /**
     * Set Current Row In Inward Output
     * */
    public void InwardOutputSetRowACL(ActionEvent actionEvent) {
        Integer i = validateOpItems(principalItemBind);
        if (i == 0) {
            String itmId = actionEvent.getComponent().getAttributes().get("ItemId").toString();
            ob = ADFBeanUtils.findOperation("MakeCurrentRowInMmScoOpItemTable");
            ob.getParamsMap().put("ItemId", itmId);
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemPanelFormBind);
        }
    }

    /**
     * Set Current Row In Outward Input
     * */
    public void OutwardInputSetRowACL(ActionEvent actionEvent) {
        Integer i = validateOpItems(principalItemBind);
        if (i == 0) {
            String itmId = actionEvent.getComponent().getAttributes().get("ItemId").toString();
            ob = ADFBeanUtils.findOperation("MakeCurrentRowInMmScoOpItemTable");
            ob.getParamsMap().put("ItemId", itmId);
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemPanelFormBind);
        }
    }

    /**
     * Set Current Row In Outward Output
     * */
    public void OutwardOutputSetRowACL(ActionEvent actionEvent) {
        Integer i = validateOpItems(principalItemBind);
        if (i == 0) {
            String itmId = actionEvent.getComponent().getAttributes().get("ItemId").toString();
            ob = ADFBeanUtils.findOperation("MakeCurrentRowInMmScoOpItemTable");
            ob.getParamsMap().put("ItemId", itmId);
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemPanelFormBind);
        }
    }

    public void setOrderWiseTrRuleBind(RichSelectOneChoice orderWiseTrRuleBind) {
        this.orderWiseTrRuleBind = orderWiseTrRuleBind;
    }

    public RichSelectOneChoice getOrderWiseTrRuleBind() {
        return orderWiseTrRuleBind;
    }

    public void setItemWiseTrRuleBind(RichSelectOneChoice itemWiseTrRuleBind) {
        this.itemWiseTrRuleBind = itemWiseTrRuleBind;
    }

    public RichSelectOneChoice getItemWiseTrRuleBind() {
        return itemWiseTrRuleBind;
    }

    /**
     * For Process Update in Model
     * */
    public void ScoTypeVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    /**
     * Order Calculation Link action Listner
     * */
    public void orderCalculationACL(ActionEvent actionEvent) {
        OrderCalcFunc();
    }

    /**
     * Method Call for Order caculation
     * */
    public void OrderCalcFunc() {
        OperationBinding binding = ADFBeanUtils.findOperation("orderCalculation");
        binding.execute();
        oracle.jbo.domain.Number RetVal = (oracle.jbo.domain.Number) binding.getResult();
        System.out.println("RetVal ::::: " + RetVal);
        if (RetVal.compareTo(new oracle.jbo.domain.Number(1)) == 0) {
            ADFBeanUtils.findOperation("ExecuteCalulationVo").execute();
        }
    }

    @SuppressWarnings("unchecked")
    public void addTermAndConditionACL(ActionEvent actionEvent) {
        UIComponent componentVal = actionEvent.getComponent();
        oracle.adf.view.rich.util.ResetUtils.reset(componentVal);
        Integer Tnc_Id = (Integer) componentVal.getAttributes().get("TncId");
        System.out.println("Value of Tnc In Bean :::: " + Tnc_Id);
        OperationBinding bin = ADFBeanUtils.findOperation("AddTermAndCondition");
        bin.getParamsMap().put("TncId", Tnc_Id);
        bin.execute();
    }

    /**
     * Method to validate Header before saving
     * @return
     */
    public Integer ValidatePaymentSchedule(UIComponent ui) {
        ob = ADFBeanUtils.findOperation("ValidatePaymentSchdule");
        ob.execute();
        Object object = ob.getResult();
        Integer i = object == null ? -1 : (Integer) object;
        if (i.equals(1)) {
            ADFModelUtils.showFacesMessage("Payment Mode Not Selected !", "Please select Payment Mode.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "soc33"));
        } else if (i.equals(2)) {
            ADFModelUtils.showFacesMessage("Payment Date Not Selected !", "Please select Payment Date.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "id5"));
        } else if (i.equals(3)) {
            ADFModelUtils.showFacesMessage("Enter Amount !", "Please Enter Amount.", FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(ui, "it31"));
        } else if (i.equals(4)) {
            ADFModelUtils.showFacesMessage("Amount can't be zero !", "Amount should be greater than zero.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "it31"));
        } else if (i.equals(5)) {
            ADFModelUtils.showFacesMessage("Enter Amount !", "Amount should be less than or equal to pending amount.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "it31"));
        }
        return i;
    }

    /**
     * Add Payment Schedule
     * */
    public void AddPaymentScheduleACL(ActionEvent actionEvent) {
        Integer i = ValidatePaymentSchedule(paymentSchdlDateBind);
        if (i == 0) {
            ADFBeanUtils.findOperation("InsertPaymentSchedule").execute();
        }
    }

    public void setPaymentSchdlDateBind(RichInputDate paymentSchdlDateBind) {
        this.paymentSchdlDateBind = paymentSchdlDateBind;
    }

    public RichInputDate getPaymentSchdlDateBind() {
        return paymentSchdlDateBind;
    }

    /**
     * Value change listner
     * paymode Mode choice List
     * */
    public void PaymentModeVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            ADFBeanUtils.findOperation("SetPendAmtToPaymentAmt").execute();
        }
    }

    /**
     * Check default Payment Schedule condition.
     * */
    public Integer CheckDefaultPaymentSchdl(UIComponent ui) {
        ob = ADFBeanUtils.findOperation("ValidateDefaultPaymentSchdl");
        ob.execute();
        Object object = ob.getResult();
        Integer i = object == null ? -1 : (Integer) object;
        if (i.equals(1)) {
            StringBuilder message = new StringBuilder();
            message.append("<P>Please Complete Payment Schedule.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Sub Contracting Order", message.toString(),
                                                    FacesMessage.SEVERITY_INFO);
        }
        return i;
    }

    /**
     * Generate Default Payment Schedule
     * */
    public void GenDefaultPaymentSchedule() {
        ADFBeanUtils.findOperation("GenDefaultPaymentScheduleFunction").execute();
    }

    public void ToleranceDaysValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            if (val.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Negative numbers are not allowed.", null));
            }
        }
    }


    /**
     * Header Cost Center Link Action
     * */
    public String headCostCenterAction() {
        OperationBinding binding = ADFBeanUtils.findOperation("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            return "headCc";
        } else {
            return null;
        }
    }

    /**
     * Details Cost Center Link Action
     * */
    public String costCenterAction() {
        OperationBinding binding = ADFBeanUtils.findOperation("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            ADFBeanUtils.findOperation("updateCostCenterAmt").execute();
            return "costCenter";
        } else {
            return null;
        }
    }

    //Setting values to check cost center aplicable
    public Boolean getEnableCostCenter() {
        //isCostCenterApplicable
        if (enableCostCenter == null) {
            OperationBinding b = ADFBeanUtils.getOperationBinding("isCostCenterApplicable");
            if (b != null) {
                b.execute();
                Object o = b.getResult();
                enableCostCenter = (o == null ? false : (Boolean) o);
                //  System.out.println("*********************************  " + enableCostCenter);
            }
        }
        return enableCostCenter;
    }

    /**
     * Amendment of Subcontracting Order
     * */
    public void AmendLinkActionListner(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("ScoAmendmentMethod").execute();
        changePageMode_Create();
    }
}
