package scissue.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import appwfservice.view.bean.ADFUtil;

import java.sql.SQLException;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;
import oracle.jbo.server.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class MaterialIssueCreateBean {
    private RichInputText issuQtyBind;

    private RichPopup itmSrPopup;
    private RichTable srTableBind;
    private RichTable lotBinTableBind;
    private RichPopup itmBinPopup;
    private RichInputText totalAvlQty;
    private RichTable lotTableBind;
    private RichPopup itmLotPopup;
    private RichInputDate issueDateBind;
    private RichTable sourceDocTableBind;
    private RichPanelFormLayout itemPanelFormBindVar;
    private RichPopup itmSrPopupInward;
    private RichPopup itmBinPopupInward;
    private RichPopup itmLotPopupInward;
    private RichOutputText totalAvlQtyInward;
    private RichSelectOneChoice headerWhIdBindVar;
    private RichSelectOneChoice scIssueTypeBindVar;
    private RichTable srcItemTableBindVar;

    public MaterialIssueCreateBean() {
    }

    private String wfId = null;
    Number zero = new Number(0);

    public void setWfId(String wfId) {
        this.wfId = wfId;
    }

    public String getWfId() {
        return wfId;
    }

    public void setIssuQtyBind(RichInputText issuQtyBind) {
        this.issuQtyBind = issuQtyBind;
    }

    public RichInputText getIssuQtyBind() {
        return issuQtyBind;
    }

    /**
     * Set Switcher Facet
     * */
    private UIXSwitcher switcherBind;
    private String linkColor = "Lot";

    public void setLinkColor(String linkColor) {
        this.linkColor = linkColor;
    }

    public String getLinkColor() {
        return linkColor;
    }

    public void setSwitcherBind(UIXSwitcher switcherBind) {
        this.switcherBind = switcherBind;
    }

    public UIXSwitcher getSwitcherBind() {
        return switcherBind;
    }

    public void checkLotACL(ActionEvent actionEvent) {
        this.switcherBind.setFacetName("Lot");
        this.setLinkColor("Lot");
    }

    public void checkBinACL(ActionEvent actionEvent) {
        this.switcherBind.setFacetName("Bin");
        this.setLinkColor("Bin");
    }

    public void checkSerialACL(ActionEvent actionEvent) {
        this.switcherBind.setFacetName("Sr");
        this.setLinkColor("Sr");
    }

    /**
     * ******************************************************************************************
     * */

    /**Page Mode Change In Create Mode
     * */
    public void changePageMode_Create() {
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("ISSUE_PAGE_MODE", "C");
    }

    /**Page Mode Change In View Mode
     * */
    public void changePageMode_View() {
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("ISSUE_PAGE_MODE", "V");
    }

    /**Commit Method
     * This Method Contain Call Work Flow Function
     * And Used In Save & Save and Forward Method Links
     * */
    public void commitSubContractMaterialIssue() {
        OperationBinding binding = ADFBeanUtils.findOperation("Commit");
        Object execute = binding.execute();
        if (execute == null) {
            changePageMode_View();
            StringBuilder message = new StringBuilder();
            message.append("<P>Material Issued Successfully.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Material Issue", message.toString(), FacesMessage.SEVERITY_INFO);
            callFuncWf();
            ADFBeanUtils.findOperation("Commit").execute();
        } else {
            changePageMode_Create();
            StringBuilder message = new StringBuilder();
            message.append("<P>Unable to Issue Material.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Material Issue", message.toString(), FacesMessage.SEVERITY_ERROR);
        }
    }

    /**
     * Call Functions of Work Flow And WorkFLow Id's
     * */
    public void callFuncWf() {
        callWfId();
        ADFBeanUtils.findOperation("callWfFunctions").execute();
    }

    public void callWfId() {
        OperationBinding binding = ADFBeanUtils.findOperation("getWfId");
        binding.execute();
        String wId = (String) binding.getResult();
        setWfId(wId);
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
        OperationBinding binding = ADFBeanUtils.findOperation("validateHeaderMandatoryFields");
        binding.execute();
        Object object = binding.getResult();
        Integer i = object == null ? -1 : (Integer) object;
        if (i.equals(1)) {
            ADFModelUtils.showFacesMessage("WareHouse not selected !", "Please select WareHouse.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "soc5"));
        }
        if (i.equals(2)) {
            ADFModelUtils.showFacesMessage("Project not selected !", "Please select Project.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "soc8"));
        } else if (i.equals(3)) {
            ADFModelUtils.showFacesMessage("Issue Type not selected !", "Please select Issue Type.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "soc6"));
        } else if (i.equals(4)) {
            ADFModelUtils.showFacesMessage("Entity not selected !", "Please select Entity.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "transEoNmId"));
        } else if (i.equals(5)) {
            ADFModelUtils.showFacesMessage("Currency not selected !", "Please select Currency.",
                                           FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(ui, "currDescTransId"));
        }
        return i;
    }

    /**Save Button Action Event
     * Call Commit Method
     * */
    public void saveLinkACL(ActionEvent actionEvent) {
        if (relMode == 0) {
            Integer i = HeaderMandatoryFields(issueDateBind);
            if (i == 0) {
                Integer j = sourceDocTableBind.getRowCount();
                if (j == 0) {
                    StringBuilder message = new StringBuilder();
                    message.append("<P>Please Add Sub Contracting Order.</P><BR>");
                    ADFModelUtils.showFormattedFacesMessage("Material Issue", message.toString(),
                                                            FacesMessage.SEVERITY_INFO);
                } else {
                    ADFBeanUtils.findOperation("backUpdateOrder").execute();
                    commitSubContractMaterialIssue();
                }
            }
        }
        if (relMode == 1) {
            Integer i = ValidateItemIssueQuantity(issuQtyBind);
            if (i == 0) {
                String binEntry = ValidateItemLotBinEntries();
                if ("Y".equals(binEntry)) {
                    ADFBeanUtils.findOperation("backUpdateOrder").execute();
                    ADFBeanUtils.findOperation("deleteUnissueOrderAndItem").execute();
                    MaterialIssueRelease();
                } else {
                    StringBuilder message = new StringBuilder();
                    message.append("<P>Lot Quantity don't match for the following Item.</P><BR>");
                    message.append("<UL><LI> [ " + binEntry + " ] </LI></UL>");
                    ADFModelUtils.showFormattedFacesMessage("Material Issue", message.toString(),
                                                            FacesMessage.SEVERITY_INFO);
                }
            }
        }
    }

    /**
     * Function for Validation of Item Lot Bin Entry
     * */
    public String ValidateItemLotBinEntries() {
        OperationBinding binding = ADFBeanUtils.findOperation("ValidateItemLotBinEntry");
        binding.execute();
        String ItmVal = binding.getResult().toString();
        return ItmVal;
    }


    /**
     * On Release Call Function
     * */
    public void MaterialIssueRelease() {
        OperationBinding binding = ADFBeanUtils.findOperation("ReleaseCallFunctions");
        Object execute = binding.execute();
        if (execute == null) {
            StringBuilder message = new StringBuilder();
            message.append("<P>Released Successfully.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Material Issue", message.toString(), FacesMessage.SEVERITY_INFO);
            this.setRelMode(0);
        } else {
            StringBuilder message = new StringBuilder();
            message.append("<P>Unable to Release Material Issue.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Material Issue", message.toString(), FacesMessage.SEVERITY_ERROR);
        }
    }

    /**Save & Forward Button Action Event
     * Call Commit Method
     * */
    public String SaveAndForwardLinkACL() {
        String s = null;
        Integer i = HeaderMandatoryFields(issueDateBind);
        if (i == 0) {
            Integer j = sourceDocTableBind.getRowCount();
            if (j == 0) {
                StringBuilder message = new StringBuilder();
                message.append("<P>Please Add Sub Contracting Order.</P><BR>");
                ADFModelUtils.showFormattedFacesMessage("Material Issue", message.toString(),
                                                        FacesMessage.SEVERITY_INFO);
            } else {
                ADFBeanUtils.findOperation("backUpdateOrder").execute();
                commitSubContractMaterialIssue();
                return "workFlowGo";
            }
        }
        return s;
    }

    /**Edit Button Action Event
     * It Check Document Approved or Not
     * On this basis edit button enable/disable
     * */
    public void EditLinkACL(ActionEvent actionEvent) {
        Integer usr_Id = EbizParams.GLBL_APP_USR();
        Integer sloc_id = EbizParams.GLBL_APP_SERV_LOC();
        Integer pending = 0;
        OperationBinding binding = ADFBeanUtils.findOperation("getDocUsrFromWF");
        binding.execute();
        Integer chkUsr = (Integer) binding.getResult();
        if (chkUsr != null) {
            pending = chkUsr;
        }
        if (pending.compareTo(usr_Id) == 0) {
            changePageMode_Create();
        } else if (pending.compareTo(new Integer(-1)) == 0) {
            StringBuilder msg = new StringBuilder();
            msg.append("<p>Material Issue Document has been Approved, You can not edit.</p>");
            ADFModelUtils.showFormattedFacesMessage("Material Issue", msg.toString(), FacesMessage.SEVERITY_INFO);
        } else {
            OperationBinding findOperation = ADFBeanUtils.findOperation("getUserName");
            findOperation.getParamsMap().put("u_Id", pending);
            findOperation.getParamsMap().put("slc_id", sloc_id);
            findOperation.execute();
            if (findOperation.getResult() != null) {
                StringBuilder mssg = new StringBuilder();
                mssg.append("<p>This Document is pending at other user<b><i> [ " +
                            findOperation.getResult().toString() +
                            "] </i></b>for approval, You can not edit this document.</p>");
                ADFModelUtils.showFormattedFacesMessage("Material Issue", mssg.toString(), FacesMessage.SEVERITY_INFO);
            }
        }
    }

    /**Create New Order Button Action Event In Create Page
     * Call HexDocNo Function and set value on hexadecimal number
     * Than Create and Insert Call In Header Table
     * Change Page Mode In Create Mode
     * */
    public void AddNewIssueACL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("GetHexDocNo").execute();
        ADFBeanUtils.findOperation("CreateInsertInMmIssu").execute();
        changePageMode_Create();
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

    public void setItmSrPopup(RichPopup itmSrPopup) {
        this.itmSrPopup = itmSrPopup;
    }

    public RichPopup getItmSrPopup() {
        return itmSrPopup;
    }

    /**
     * Select Item Link Action Listner
     * */
    public void seleectSrItmAction(ActionEvent actionEvent) {
        Integer i = ValidateItemIssueQuantity(actionEvent.getComponent());
        if (i == 0) {
            if (zero.compareTo((Number) issuQtyBind.getValue()) >= 0) {
                String msg2 = "Issue Quantity Must be greater than Zero (0).";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(issuQtyBind.getClientId(), message2);
            } else {
                ADFBeanUtils.findOperation("srNoFilterRow").execute();
                showPopup(itmSrPopup, true);
            }
        }
    }

    public void setSrTableBind(RichTable srTableBind) {
        this.srTableBind = srTableBind;
    }

    public RichTable getSrTableBind() {
        return srTableBind;
    }

    public Number getIssuQty() {
        Number issQtyBal = zero;
        if (issuQtyBind.getValue() != null) {
            Number issuQty = (Number) issuQtyBind.getValue();
            Integer tableCount = srTableBind.getRowCount();
            Number tableValue = zero;

            try {
                tableValue = new Number(tableCount);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("issuQty------  " + issuQty + "tableCount-----  " + tableCount + "tableValue----  " +
                               tableValue);
            if (tableValue != null) {
                issQtyBal = (Number) issuQty.minus(tableValue);
            }
        }
        return issQtyBal;
    }


    /**
     * Serial Popup Dialog Listner
     * */
    public void srNoSelectDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            Number retTotVal = new Number(0);
            Number zero = new Number(0);
            Number issQtyBal = getIssuQty();
            System.out.println("balance   : " + issQtyBal);
            if (issuQtyBind.getValue() != null) {
                // Method for check total issue quantity from  Summ$SR table and  return total issue Quantity method define in VoImplclass
                OperationBinding totIssu = ADFBeanUtils.findOperation("getTotalSRIssuQty");
                totIssu.execute();
                if (totIssu.getResult() != null) {
                    retTotVal = (Number) totIssu.getResult();
                }
            }
            // Check conditon zero quantity issue
            if (retTotVal.compareTo(zero) == 0) {
                System.out.println("inside popup -- condotion zero");
                String msg2 = "Add Issue Quantity.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                //  }else if((retTotVal.compareTo(issuQtyBind.getValue())>0) || (retTotVal.compareTo(issuQtyBind.getValue()) < 0)){  // check conditon more or less quantity issue
            } else if ((retTotVal.compareTo(issQtyBal) > 0) || (retTotVal.compareTo(issQtyBal) < 0)) { // check conditon more or less quantity issue
                System.out.println("inside popup -- condition mis match");
                String msg2 = "Insert Issue Quantity not equals required Quantity.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                //  }else  if(retTotVal.compareTo(issuQtyBind.getValue())==0){  // if Issue quantity and required quantity same
            } else if (retTotVal.compareTo(issQtyBal) == 0) { // if Issue quantity and required quantity same
                System.out.println("inside popup");
                // Method for inserting row from SRView to main issue SR table method define in AmImplclass
                OperationBinding srNoOp = ADFBeanUtils.findOperation("insrtSelectSrFrmView");
                srNoOp.execute();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(srTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemPanelFormBindVar);
        }
    }


    /**
     * Select Lot Bin Link Listner
     * */
    public void selectBinQtyForItm(ActionEvent actionEvent) {
        Integer i = ValidateItemIssueQuantity(actionEvent.getComponent());
        if (i == 0) {
            Integer lotBinR = lotBinTableBind.getRowCount();
            System.out.println("table count lot       " + lotBinR);
            if (lotBinR == 0) {
                OperationBinding resetLotBinView = ADFBeanUtils.findOperation("resetViewLotBinValue");
                resetLotBinView.execute();
            }
            if (zero.compareTo((Number) issuQtyBind.getValue()) >= 0) {
                String msg2 = "Issue Quantity Must be greater than Zero (0).";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(issuQtyBind.getClientId(), message2);
            } else {
                showPopup(itmBinPopup, true);
            }
        }
    }

    public void setLotBinTableBind(RichTable lotBinTableBind) {
        this.lotBinTableBind = lotBinTableBind;
    }

    public RichTable getLotBinTableBind() {
        return lotBinTableBind;
    }

    public void setItmBinPopup(RichPopup itmBinPopup) {
        this.itmBinPopup = itmBinPopup;
    }

    public RichPopup getItmBinPopup() {
        return itmBinPopup;
    }

    public void BinNoSelectDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            Number retTotVal = new Number(0);
            Number zero = new Number(0);
            if (issuQtyBind.getValue() != null) {
                // Method for check total issue quantity from  Summ$Bin table and  return total issue Quantity method define in VoImplclass
                OperationBinding totIssu = ADFBeanUtils.findOperation("getTotalBinIssuQty");
                totIssu.execute();
                if (totIssu.getResult() != null) {
                    retTotVal = (Number) totIssu.getResult();
                }
            }
            // Check conditon zero quantity issue
            if (retTotVal.compareTo(zero) == 0) {
                System.out.println("inside popup -- condotion zero");
                String msg2 = "Insert Issue Quantity";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            } else if ((retTotVal.compareTo(issuQtyBind.getValue()) > 0) ||
                       (retTotVal.compareTo(issuQtyBind.getValue()) <
                        0)) { // check conditon more or less quantity issue
                System.out.println("inside popup -- condition mis match");
                String msg2 = "Insert Issue Quantity not equals required Quantity";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            } else if (retTotVal.compareTo(issuQtyBind.getValue()) == 0) { // if Issue quantity and required quantity same
                System.out.println("inside -- popup");
                // Method for inserting row from BinView to main issue Bin table method define in AmImplclass
                OperationBinding binNoOp = ADFBeanUtils.findOperation("insrtSelectedBinForView");
                binNoOp.execute();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(srTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemPanelFormBindVar);
        }
    }

    public void setTotalAvlQty(RichInputText totalAvlQty) {
        this.totalAvlQty = totalAvlQty;
    }

    public RichInputText getTotalAvlQty() {
        return totalAvlQty;
    }

    /**
     *
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

    public void issuBinQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number value = (Number) object;
        if (value != null) {
            Boolean flag = isPrecisionValid(26, 6, value);
            System.out.println("Bin Validator inside " + value);
            System.out.println("Total Available Qty ---" + totalAvlQty.getValue());
            Number totalAvl = (Number) totalAvlQty.getValue();
            if (value.compareTo(zero) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Issue Quantity must be positive value.", null));

            } else if (value.compareTo(totalAvl) == 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Issue Quantity can not more than Total Available Quantity ",
                                                              null));

            } else if (flag.equals(false)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision scale (26,6)", null));
            }
        }
    }

    /**
     * Select Lot Link Action Listner
     * */
    public void selectLotForItm(ActionEvent actionEvent) {
        Integer i = ValidateItemIssueQuantity(actionEvent.getComponent());
        if (i == 0) {
            Integer lotR = lotTableBind.getRowCount();
            if (lotR == 0) {

                OperationBinding resetLotView = ADFBeanUtils.findOperation("resetViewLotValue");
                resetLotView.execute();
            } else {
                OperationBinding setLotView = ADFBeanUtils.findOperation("setViewLotValue");
                setLotView.execute();
            }
            if (zero.compareTo((Number) issuQtyBind.getValue()) >= 0) {
                String msg2 = "Issue Quantity Must be greater than Zero (0).";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(issuQtyBind.getClientId(), message2);
            } else {
                showPopup(itmLotPopup, true);
            }
        }
    }

    public void setLotTableBind(RichTable lotTableBind) {
        this.lotTableBind = lotTableBind;
    }

    public RichTable getLotTableBind() {
        return lotTableBind;
    }

    public void setItmLotPopup(RichPopup itmLotPopup) {
        this.itmLotPopup = itmLotPopup;
    }

    public RichPopup getItmLotPopup() {
        return itmLotPopup;
    }

    public void lotNoSelectDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            Number retTotVal = new Number(0);
            Number zero = new Number(0);
            if (issuQtyBind.getValue() != null) {
                // Method for check total issue quantity from  MM$Stk$Summ$lot table and  return total issue Quantity method define in VoImplclass
                OperationBinding totIssu = ADFBeanUtils.findOperation("getTotalIssuQty");
                totIssu.execute();
                if (totIssu.getResult() != null) {
                    retTotVal = (Number) totIssu.getResult();
                }
            }
            // Check conditon zero quantity issue
            if (retTotVal.compareTo(zero) == 0) {
                System.out.println("inside popup condotion zero");
                String msg2 = "Insert Issue Quantity";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            } else if ((retTotVal.compareTo(issuQtyBind.getValue()) > 0) ||
                       (retTotVal.compareTo(issuQtyBind.getValue()) <
                        0)) { // check conditon more or less quantity issue
                System.out.println("inside popup condition mis match");
                String msg2 = "Insert Issue Quantity not equals required Quantity";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            } else if (retTotVal.compareTo(issuQtyBind.getValue()) == 0) { // if Issue quantity and required quantity same

                System.out.println("inside popup");
                // Method for inserting row from lotView to main issue lot table method define in AmImplclass
                OperationBinding binNoOp = ADFBeanUtils.findOperation("insrtSelectedLotForView");
                binNoOp.execute();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(srTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemPanelFormBindVar);
        }
    }

    public void issueLotQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number value = (Number) object;
        if (value != null) {
            Boolean flag = isPrecisionValid(26, 6, value);
            if (value.compareTo(zero) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Issue Quantity must be positive value.", null));

            } else if (flag.equals(false)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision scale (26,6)", null));
            }
        }
    }

    public void autoIssueItmAction(ActionEvent actionEvent) {
        Integer i = ValidateItemIssueQuantity(actionEvent.getComponent());
        if (i == 0) {
            if (zero.compareTo((Number) issuQtyBind.getValue()) >= 0) {
                String msg2 = "Issue Quantity Must be greater than Zero (0).";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(issuQtyBind.getClientId(), message2);
            } else {
                OperationBinding autoIssu = ADFBeanUtils.findOperation("autoIssueItemfromSystem");
                autoIssu.execute();
                if (autoIssu.getResult() != null) {
                    Integer retrnVal = Integer.parseInt(autoIssu.getResult().toString());
                    System.out.println("ret--------" + retrnVal);
                    if (retrnVal == 1) {
                        String msg2 = "Item Issue successfully.";
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message2);
                    } else {
                        String msg2 = "Item can't Issue successfully some error occur .!!";
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                    }
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(srTableBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTableBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itemPanelFormBindVar);
            }
        }
    }

    public void setIssueDateBind(RichInputDate issueDateBind) {
        this.issueDateBind = issueDateBind;
    }

    public RichInputDate getIssueDateBind() {
        return issueDateBind;
    }

    /**
     * Method to validate Source Documents
     * @return
     */
    public Integer ValidateSourceDocument(UIComponent ui) {
        OperationBinding binding = ADFBeanUtils.findOperation("ValidateSourceDocuments");
        binding.execute();
        Object object = binding.getResult();
        Integer i = object == null ? -1 : (Integer) object;
        if (i.equals(1)) {
            ADFModelUtils.showFacesMessage("Order not selected !", "Please select Sc Order.",
                                           FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(ui, "transScoDispIdId"));
        } else if (i.equals(2)) {
            ADFModelUtils.showFacesMessage("Delivery Schedule not selected !", "Please select Delivery Schedule No.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "soc7"));

        } else if (i.equals(3)) {
            ADFModelUtils.showFacesMessage("Duplicate Delivery Schedule Selected !",
                                           "Please select Another Delivery Schedule.", FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(ui, "soc7"));
        }
        return i;
    }

    /**
     * Add Source
     * */
    public void addSourceACL(ActionEvent actionEvent) {
        Integer i = HeaderMandatoryFields(issueDateBind);
        if (i == 0) {
            Integer j = ValidateSourceDocument(actionEvent.getComponent());
            if (j == 0) {
                ADFBeanUtils.findOperation("AddSourceDoc").execute();
            }
        }
    }

    public void setSourceDocTableBind(RichTable sourceDocTableBind) {
        this.sourceDocTableBind = sourceDocTableBind;
    }

    public RichTable getSourceDocTableBind() {
        return sourceDocTableBind;
    }

    /**
     * Mode Change Variable For Release
     * */
    private Integer relMode = 0;

    public void setRelMode(Integer relMode) {
        this.relMode = relMode;
    }

    public Integer getRelMode() {
        return relMode;
    }

    /**
     * Method to Issue Quantity
     * @return
     */
    public Integer ValidateItemIssueQuantity(UIComponent ui) {
        OperationBinding binding = ADFBeanUtils.findOperation("ValidateIssueItemQuantity");
        binding.execute();
        Object object = binding.getResult();
        Integer i = object == null ? -1 : (Integer) object;
        if (i.equals(1)) {
            StringBuilder message = new StringBuilder();
            message.append("<P>Total available Stock of Item is Zero(0) !</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Material Issue", message.toString(), FacesMessage.SEVERITY_INFO);
        } else if (i.equals(2)) {
            ADFModelUtils.showFacesMessage("Please Enter Issue Quantity.", "Please Enter Issue Quantity.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "it32"));
        } else if (i.equals(3)) {
            ADFModelUtils.showFacesMessage("Issue Quantity Must be greater than Zero (0). !",
                                           "Issue Quantity Must be greater than Zero (0). !",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "it32"));
        } else if (i.equals(4)) {
            ADFModelUtils.showFacesMessage("Issue Quantity Can not be a Negative Number !",
                                           "Issue Quantity Can not be a Negative Number !", FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(ui, "it32"));
        } else if (i.equals(5)) {
            ADFModelUtils.showFacesMessage("Issue Quantity Must Be Less Than or Equal to Pending Quantity !",
                                           "Issue Quantity Must Be Less Than or Equal to Pending Quantity ",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "it32"));
        }
        return i;
    }


    /**
     * Release Stock Button
     * Action Change Listner
     * */
    public void ReleaseLinkActionListner(ActionEvent actionEvent) {
        this.setRelMode(1);
    }

    /**
     * Item Table Selection Listner overwrite
     * */
    public void ItemTableSelectionListner(SelectionEvent selectionEvent) {
        ADFUtil.invokeEL("#{bindings.MmScIssuItmVo1.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class }, new Object[] {
                         selectionEvent });
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemPanelFormBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(srTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBind);
    }

    public void setItemPanelFormBindVar(RichPanelFormLayout itemPanelFormBindVar) {
        this.itemPanelFormBindVar = itemPanelFormBindVar;
    }

    public RichPanelFormLayout getItemPanelFormBindVar() {
        return itemPanelFormBindVar;
    }

    /**
     * Delete Source Order and Its Items
     * */
    public void deleteSourceOrderLink(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("DeleteSourceDocument").execute();
    }

    /**
     * Delete Lot Entry
     * */
    public void DeleteLotEntryLink(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("DeleteLotEntry").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemPanelFormBindVar);
    }

    /**
     * Select Items In Inward Link
     * */
    public void seleectSrItmInwardAction(ActionEvent actionEvent) {
        Integer i = ValidateItemIssueQuantity(actionEvent.getComponent());
        if (i == 0) {
            if (zero.compareTo((Number) issuQtyBind.getValue()) >= 0) {
                String msg2 = "Issue Quantity Must be greater than Zero (0).";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(issuQtyBind.getClientId(), message2);
            } else {
                OperationBinding binding = ADFBeanUtils.findOperation("FilterRqmtViews");
                binding.getParamsMap().put("VoName", "ViewMmItmStkSrRqmtVwVO1");
                binding.execute();
                showPopup(itmSrPopupInward, true);
            }
        }
    }

    /**
     * Select Lot Bin In Inward Link
     * */
    public void selectBinQtyForItmInward(ActionEvent actionEvent) {
        Integer i = ValidateItemIssueQuantity(actionEvent.getComponent());
        if (i == 0) {
            if (zero.compareTo((Number) issuQtyBind.getValue()) >= 0) {
                String msg2 = "Issue Quantity Must be greater than Zero (0).";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(issuQtyBind.getClientId(), message2);
            } else {
                OperationBinding binding = ADFBeanUtils.findOperation("FilterRqmtViews");
                binding.getParamsMap().put("VoName", "ViewMmItmStkBinRqmtVwVO1");
                binding.execute();
                showPopup(itmBinPopupInward, true);
            }
        }
    }

    /**
     * Select Lot Link In Inward
     * */
    public void selectLotForItmInward(ActionEvent actionEvent) {
        Integer i = ValidateItemIssueQuantity(actionEvent.getComponent());
        if (i == 0) {
            if (zero.compareTo((Number) issuQtyBind.getValue()) >= 0) {
                String msg2 = "Issue Quantity Must be greater than Zero (0).";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(issuQtyBind.getClientId(), message2);
            } else {
                OperationBinding binding = ADFBeanUtils.findOperation("FilterRqmtViews");
                binding.getParamsMap().put("VoName", "ViewMmItmStkLotRqmtVwVO1");
                binding.execute();
                showPopup(itmLotPopupInward, true);
            }
        }
    }

    /**
     * Auto Issue Link In Inward
     * */
    public void autoIssueItmInwardAction(ActionEvent actionEvent) {
        Integer i = ValidateItemIssueQuantity(actionEvent.getComponent());
        if (i == 0) {
            if (zero.compareTo((Number) issuQtyBind.getValue()) >= 0) {
                String msg2 = "Issue Quantity Must be greater than Zero (0).";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(issuQtyBind.getClientId(), message2);
            } else {
                OperationBinding autoIssu = ADFBeanUtils.findOperation("autoIssueItemfromSystemInward");
                autoIssu.execute();
                if (autoIssu.getResult() != null) {
                    Integer retrnVal = Integer.parseInt(autoIssu.getResult().toString());
                    System.out.println("ret--------" + retrnVal);
                    if (retrnVal == 1) {
                        String msg2 = "Item Issue successfully.";
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message2);
                    } else {
                        String msg2 = "Item can't Issue successfully some error occur .!!";
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                    }
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(srTableBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTableBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itemPanelFormBindVar);
            }
        }
    }

    public void setItmSrPopupInward(RichPopup itmSrPopupInward) {
        this.itmSrPopupInward = itmSrPopupInward;
    }

    public RichPopup getItmSrPopupInward() {
        return itmSrPopupInward;
    }

    public void setItmBinPopupInward(RichPopup itmBinPopupInward) {
        this.itmBinPopupInward = itmBinPopupInward;
    }

    public RichPopup getItmBinPopupInward() {
        return itmBinPopupInward;
    }

    public void setItmLotPopupInward(RichPopup itmLotPopupInward) {
        this.itmLotPopupInward = itmLotPopupInward;
    }

    public RichPopup getItmLotPopupInward() {
        return itmLotPopupInward;
    }

    public void srNoSelectDialogListenerInward(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            Number retTotVal = new Number(0);
            Number zero = new Number(0);
            Number issQtyBal = getIssuQty();
            System.out.println("balance   : " + issQtyBal);
            if (issuQtyBind.getValue() != null) {
                // Method for check total issue quantity from  Summ$SR table and  return total issue Quantity method define in VoImplclass
                OperationBinding totIssu = ADFBeanUtils.findOperation("getTotalSRIssuQtyInward");
                totIssu.execute();
                if (totIssu.getResult() != null) {
                    retTotVal = (Number) totIssu.getResult();
                }
            }
            // Check conditon zero quantity issue
            if (retTotVal.compareTo(zero) == 0) {
                System.out.println("inside popup -- condotion zero");
                String msg2 = "Add Issue Quantity.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                //  }else if((retTotVal.compareTo(issuQtyBind.getValue())>0) || (retTotVal.compareTo(issuQtyBind.getValue()) < 0)){  // check conditon more or less quantity issue
            } else if ((retTotVal.compareTo(issQtyBal) > 0) || (retTotVal.compareTo(issQtyBal) < 0)) { // check conditon more or less quantity issue
                System.out.println("inside popup -- condition mis match");
                String msg2 = "Insert Issue Quantity not equals required Quantity.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                //  }else  if(retTotVal.compareTo(issuQtyBind.getValue())==0){  // if Issue quantity and required quantity same
            } else if (retTotVal.compareTo(issQtyBal) == 0) { // if Issue quantity and required quantity same
                System.out.println("inside popup");
                // Method for inserting row from SRView to main issue SR table method define in AmImplclass
                OperationBinding srNoOp = ADFBeanUtils.findOperation("insrtSelectSrFrmViewInward");
                srNoOp.execute();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(srTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemPanelFormBindVar);
        }
    }

    public void BinNoSelectDialogListenerInward(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            Number retTotVal = new Number(0);
            Number zero = new Number(0);
            if (issuQtyBind.getValue() != null) {
                // Method for check total issue quantity from  Summ$Bin table and  return total issue Quantity method define in VoImplclass
                OperationBinding totIssu = ADFBeanUtils.findOperation("getTotalBinIssuQtyInward");
                totIssu.execute();
                if (totIssu.getResult() != null) {
                    retTotVal = (Number) totIssu.getResult();
                }
            }
            // Check conditon zero quantity issue
            if (retTotVal.compareTo(zero) == 0) {
                System.out.println("inside popup -- condotion zero");
                String msg2 = "Insert Issue Quantity";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            } else if ((retTotVal.compareTo(issuQtyBind.getValue()) > 0) ||
                       (retTotVal.compareTo(issuQtyBind.getValue()) <
                        0)) { // check conditon more or less quantity issue
                System.out.println("inside popup -- condition mis match");
                String msg2 = "Insert Issue Quantity not equals required Quantity";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            } else if (retTotVal.compareTo(issuQtyBind.getValue()) == 0) { // if Issue quantity and required quantity same
                System.out.println("inside -- popup");
                // Method for inserting row from BinView to main issue Bin table method define in AmImplclass
                OperationBinding binNoOp = ADFBeanUtils.findOperation("insrtSelectedBinForViewInward");
                binNoOp.execute();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(srTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemPanelFormBindVar);
        }
    }

    public void LotNoSelectDialogListenerInward(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            Number retTotVal = new Number(0);
            Number zero = new Number(0);
            if (issuQtyBind.getValue() != null) {
                // Method for check total issue quantity from  MM$Stk$Summ$lot table and  return total issue Quantity method define in VoImplclass
                OperationBinding totIssu = ADFBeanUtils.findOperation("getTotalIssuQtyInward");
                totIssu.execute();
                if (totIssu.getResult() != null) {
                    retTotVal = (Number) totIssu.getResult();
                }
            }
            // Check conditon zero quantity issue
            if (retTotVal.compareTo(zero) == 0) {
                System.out.println("inside popup condotion zero");
                String msg2 = "Insert Issue Quantity";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            } else if ((retTotVal.compareTo(issuQtyBind.getValue()) > 0) ||
                       (retTotVal.compareTo(issuQtyBind.getValue()) <
                        0)) { // check conditon more or less quantity issue
                System.out.println("inside popup condition mis match");
                String msg2 = "Insert Issue Quantity not equals required Quantity";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            } else if (retTotVal.compareTo(issuQtyBind.getValue()) == 0) { // if Issue quantity and required quantity same

                System.out.println("inside popup");
                // Method for inserting row from lotView to main issue lot table method define in AmImplclass
                OperationBinding binNoOp = ADFBeanUtils.findOperation("insrtSelectedLotForViewInward");
                binNoOp.execute();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(srTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemPanelFormBindVar);
        }
    }

    public void issuBinQtyInwardValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number value = (Number) object;
        if (value != null) {
            Boolean flag = isPrecisionValid(26, 6, value);
            System.out.println("Bin Validator inside " + value);
            System.out.println("Total Available Qty ---" + totalAvlQtyInward.getValue());
            Number totalAvl = (Number) totalAvlQtyInward.getValue();
            if (value.compareTo(zero) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Issue Quantity must be positive value.", null));

            } else if (value.compareTo(totalAvl) == 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Issue Quantity can not more than Total Available Quantity ",
                                                              null));

            } else if (flag.equals(false)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision scale (26,6)", null));
            }
        }
    }

    public void setTotalAvlQtyInward(RichOutputText totalAvlQtyInward) {
        this.totalAvlQtyInward = totalAvlQtyInward;
    }

    public RichOutputText getTotalAvlQtyInward() {
        return totalAvlQtyInward;
    }

    public void issueLotQtyInwardValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number value = (Number) object;
        if (value != null) {
            Boolean flag = isPrecisionValid(26, 6, value);
            if (value.compareTo(zero) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Issue Quantity must be positive value.", null));

            } else if (flag.equals(false)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision scale (26,6)", null));
            }
        }
    }

    public void setHeaderWhIdBindVar(RichSelectOneChoice headerWhIdBindVar) {
        this.headerWhIdBindVar = headerWhIdBindVar;
    }

    public RichSelectOneChoice getHeaderWhIdBindVar() {
        return headerWhIdBindVar;
    }

    public void setScIssueTypeBindVar(RichSelectOneChoice scIssueTypeBindVar) {
        this.scIssueTypeBindVar = scIssueTypeBindVar;
    }

    public RichSelectOneChoice getScIssueTypeBindVar() {
        return scIssueTypeBindVar;
    }

    public void EntityVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(headerWhIdBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(scIssueTypeBindVar);
        }
    }

    public void SourceDocTableSelectionListner(SelectionEvent selectionEvent) {
        ADFUtil.invokeEL("#{bindings.MmScIssuSrcVo1.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class }, new Object[] {
                         selectionEvent });
        AdfFacesContext.getCurrentInstance().addPartialTarget(srcItemTableBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemPanelFormBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(srTableBind);
    }

    public void setSrcItemTableBindVar(RichTable srcItemTableBindVar) {
        this.srcItemTableBindVar = srcItemTableBindVar;
    }

    public RichTable getSrcItemTableBindVar() {
        return srcItemTableBindVar;
    }

    /**
     * Process Update for Model
     * */
    public void ScoTypeVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    /**
     * Auto Issue for ALL Items On Single Click
     * */
    public void AutoIssueForAllItemACL(ActionEvent actionEvent) {
        OperationBinding autoIssu = ADFBeanUtils.findOperation("AutoIssueOnSingleClick");
        autoIssu.execute();
        if (autoIssu.getResult() != null) {
            Integer retrnVal = Integer.parseInt(autoIssu.getResult().toString());
            System.out.println("ret--------" + retrnVal);
            if (retrnVal == 1) {
                String msg2 = "Item Issue successfully.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            } else {
                String msg2 = "Item can't Issue successfully some error occur .!!";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(srTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemPanelFormBindVar);

    }
}

