package slssalesordapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.bean.StaticValue;
import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.layout.RichToolbar;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.component.rich.nav.RichCommandNavigationItem;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;
import oracle.adf.view.rich.component.rich.output.RichOutputLabel;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.ContextInfoEvent;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DropEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

//
/**
 * SalesOrder (Add/View/Edit) Managed Bean.
 * Contains all  business logic
 * @author Ashish Kumar
 * Dated -28/08/2013
 * */
public class SalesOrdAddEditBean {
    private StringBuffer EditBtnMode = new StringBuffer("C");
    private StringBuffer EditLotMode = new StringBuffer("L");
    private StringBuffer releaseMode = new StringBuffer("N");
    private RichPanelCollection schemeItemTableBinding;
    private RichTable lotFreeSchemItemBinding;
    private RichTable binFreeSchemItemBinding;
    private RichTable srNoFreeSchemItemBinding;
    private RichOutputText lotItmIdBindVar;
    private RichOutputText lotItemIdBindVar;
    private RichLink refreshItmDtlBinding;
    private RichSelectBooleanCheckbox amcCheckBoxBindVar;
    private RichInputText discValBind;
    private Boolean refStockDetail = true;
    private RichPopup stockDetailPopUpBind;
    private RichSelectOneChoice projectIdBinding;
    private RichPopup cancelOrderPopBinding;
    private RichSelectBooleanCheckbox exmptFlgOrdBind;
    private RichLink itmTaxLinkBinding;
    private RichShowDetailItem othrChargTabBinding;
    private RichPanelFormLayout paymentSchduleFormBinding;
    private RichTable discBasisTableBinding;
    private RichPopup discountBasisPopUpBind;
    private RichPopup amendOrdrConfrmPopUpBind;
    private RichSelectOneChoice eoCatgBinding;

    public void setRefStockDetail(Boolean refStockDetail) {
        this.refStockDetail = refStockDetail;
    }

    public Boolean getRefStockDetail() {
        if (refStockDetail == true) {
            refStockDetail = false;
            return true;
        }
        return refStockDetail;
    }

    public void setReleaseMode(StringBuffer releaseMode) {
        this.releaseMode = releaseMode;
    }

    public StringBuffer getReleaseMode() {
        return releaseMode;
    }
    private RichTable paymentScheduleTable;

    public void setEditLotMode(StringBuffer EditLotMode) {
        this.EditLotMode = EditLotMode;
    }

    public StringBuffer getEditLotMode() {
        return EditLotMode;
    }
    private RichInputText schdlQuantTrans;
    private static ADFLogger _log = ADFLogger.createADFLogger(SalesOrdAddEditBean.class);
    private RichInputText blncQtyBind;
    private RichCommandLink selectAllLinkBind;
    private RichPopup orderTaxPopUpBind;
    private RichSelectOneChoice taxRuleIdOrderBind;
    private RichPopup ocPopUpBind;
    private RichPopup itemTaxPopUpBind;
    private String app_mode = "V";
    private String adds_mode = "V";
    private Date date = new Date();
    private RichPopup discOrderPopUpBind;
    private RichSelectOneRadio discTypSobind;
    private RichInputText itmPriceBind;
    private RichInputText itmQtyBind;
    private RichSelectOneRadio discTypItmBind;

    private String checkTaxYN;
    private RichSelectOneChoice ordTypeBind;
    private RichInputListOfValues eoNmTransBind;
    private RichSelectOneChoice taxRuleIdItmBind;
    private RichSelectBooleanCheckbox exmptFlgItmBind;
    private RichInputListOfValues currIdSpBind;
    private RichInputDate ordDateBind;
    private RichInputText amtSpOcBind;
    private RichInputText itmPriceSgstBind;
    private RichPopup generatedPoPopUpBind;
    private boolean treeOrTab = true;
    private RichTreeTable treeTable;
    private RowKeySet disclosedTreeRowKeySet = new RowKeySetImpl();
    private RichSelectOneChoice ordStatusBind;
    private RichSelectOneChoice backOrdStatusBind;
    private RichSelectOneChoice consignOrdStatusBind;
    private RichInputText currConvRateBind;
    private RichTable itemTableBind;
    private RichSelectOneChoice soBasisBind;
    private RichSelectOneChoice quotIdBind;
    private RichInputListOfValues templateIdBind;
    private RichInputListOfValues refSoDocIdBind;
    private RichInputText templateNameBind;
    private RichPopup templateDescPopUpBind;
    private RichPopup priceCompareSoPopupBind;
    private RichPanelCollection treeTabPanelCollBind;
    private RichPanelTabbed panelTabbedBind;
    private RichShowDetailItem itemDetailsTabBind;
    private RichTable soItmPoTableBind;
    String action = "I";
    String remark = "A";
    String WfNum = "0";
    Integer level = 0;
    Integer res = -1;
    Number amount = new Number(0);
    private String facetName = "Lot";
    private String schmFacetName = "Lot";
    private RichPopup selectLotPopUpBind;
    private RichPopup selectLotBinPopUpBind;
    private RichPopup selectSrNoPopUpBind;
    private RichTable ocTableBind;
    private Boolean chkDlcSchdlItm = null;
    //private RichOutputText itmQtyIssueBind;
    private RichSelectOneChoice whIdSoBind;
    private RichSelectBooleanCheckbox cancelOrderBind;
    private RichSelectOneChoice tlrncQtyTypBind;
    private String amdFlg = "N";
    private Integer amd_No;
    private String canc_Flg = "N";
    private RichInputText pendPayAmtTrans;
    private RichInputText payAmtTransBind;
    private RichInputDate validUpToDtBind;
    private RichInputText totStkLotBind;
    private RichInputText totStkLotBinBind;
    private String isWhUsed = "N";
    private RichPopup trfPopupBind;
    private RichTable deliveryScheduleTable;
    private RichInputText tottaxAmntBind;
    private RichSelectOneRadio frieghtVChargesBind;
    private RichInputText frieghtBind;
    private RichSelectBooleanCheckbox shortCloseOrdrBind;
    private RichInputText dlvAddsBind;
    private RichInputText orderTotCostBind;
    private RichInputText cashSaledlvAddBind;
    private RichSelectOneChoice contratctTypePgBind;
    private RichInputText contractValuePgBind;
    private RichSelectOneRadio taxFlgRBPgBind;
    private RichSelectOneChoice pricePolicyPgBind;
    private RichPopup prospectToCustPopUpBind;
    private RichCommandImageLink addNewAddressPgBind;
    private RichOutputLabel itmQtyIssueLblBind;
    private Boolean isSchemeApplied = null;
    private RichInputListOfValues quotDispIdBind;
    private RichInputText pmntAmtBind;
    private RichShowDetailItem paymentModeTabBVar;
    private Boolean useRefOrg = null;
    private RichPanelFormLayout itmFormLayoutBind;
    private StringBuffer enableOutstandingDtl = null;

    //  private RichInputText discValBind; Commented on 15-June-15, 3:20, for scheme issues.
    private RichOutputLabel itmIssueSrBind;
    private RichInputListOfValues itmIdBind;
    private RichInputText filePathBind;
    private StringBuffer showItmDesc = null;
    private StringBuffer showItmAttribute = null;
    private String exportLink = null;
    private String glblSchmId = null;

    private Boolean validationsEnabled = true;
    private Boolean showAsblVal = null;
    private Boolean showWty = null;
    private Boolean showOldHistory = null;
    private Boolean enableShortclose = null;
    private Boolean enableCostCenter = null;
    private Boolean enableItemBasis = null;
    private Boolean enableTracking = null;
    private Boolean enableFlex = null;
    private Boolean enableEoCatg = null;

    public void setEnableEoCatg(Boolean enableEoCatg) {
        this.enableEoCatg = enableEoCatg;
    }

    public Boolean getEnableEoCatg() {
        if (enableEoCatg == null) {
            StringBuffer i = new StringBuffer("N");
            OperationBinding binding = this.getBindings().getOperationBinding("checkforProfileValues");
            binding.getParamsMap().put("colName", new StringBuffer("SHOW_EO_CATG_FLTR_ON_SO")); //EDIT_ASBL_VAL
            if (binding != null) {
                binding.execute();
                Object object = binding.getResult();
                if (object != null) {
                    i = (StringBuffer) object;
                }
                if (i.toString().equals("Y")) {
                    enableEoCatg = true;
                } else {
                    enableEoCatg = false;
                }
            }
        }
        return enableEoCatg;
    }

    public Boolean getEnableFlex() {
        if (enableFlex == null) {
            OperationBinding b = ADFBeanUtils.getOperationBinding("isFlexApplicable");
            if (b != null) {
                b.execute();
                Object o = b.getResult();
                enableFlex = (o == null ? false : (Boolean) o);
                //  System.out.println("*********************************  " + enableCostCenter);
            }
        }

        return enableFlex;
    }

    public Boolean getEnableTracking() {
        if (enableTracking == null) {
            OperationBinding b = ADFBeanUtils.getOperationBinding("isTrackingApplicable");
            if (b != null) {
                b.execute();
                Object o = b.getResult();
                enableTracking = (o == null ? false : (Boolean) o);
                //  System.out.println("*********************************  " + enableCostCenter);
            }
        }
        return enableTracking;
    }

    public Boolean getEnableItemBasis() {
        if (enableItemBasis == null) {
            OperationBinding b = ADFBeanUtils.getOperationBinding("isItemBasisApplicable");
            if (b != null) {
                b.execute();
                Object o = b.getResult();
                enableItemBasis = (o == null ? false : (Boolean) o);
                //  System.out.println("*********************************  " + enableCostCenter);
            }
        }
        return enableItemBasis;
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

    public Boolean getEnableShortclose() {
        if (enableShortclose == null) {
            OperationBinding binding = ADFBeanUtils.getOperationBinding("isOrderEligibleToShortClose");
            if (binding != null) {
                binding.execute();
                Object o = binding.getResult();
                enableShortclose = (o == null ? false : (Boolean) o);
            }
        }
        return enableShortclose;
    }
    private Boolean poRemoveStae = true;

    public void setPoRemoveStae(Boolean poRemoveStae) {
        this.poRemoveStae = poRemoveStae;
    }

    public Boolean getPoRemoveStae() {
        return poRemoveStae;
    }
    private Boolean PoMode = null;
    private RichOutputText itemNmInTabBind;
    private RichPanelCollection itemTablePanelCollBinding;
    private RichPanelBox itemTablePanelBoxBind;
    private Boolean enablePart = null; // boolean value to enable part no for the org or not from profile

    public Boolean getEnablePart() {

        if (enablePart == null) {
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
                    enablePart = true;
                } else {
                    enablePart = false;
                }
            }
        }

        return enablePart;
    }

    /**
     * @param showOldHistory
     */
    public void setShowOldHistory(Boolean showOldHistory) {
        this.showOldHistory = showOldHistory;
    }

    /**
     * @return
     */
    public Boolean getShowOldHistory() {
        if (showOldHistory == null) {
            StringBuffer i = new StringBuffer("N");
            OperationBinding binding = this.getBindings().getOperationBinding("checkforProfileValues");
            binding.getParamsMap().put("colName", new StringBuffer("SHOW_OLD_PRICE")); //EDIT_ASBL_VAL
            if (binding != null) {
                binding.execute();
                Object object = binding.getResult();
                if (object != null) {
                    i = (StringBuffer) object;
                }
                if (i.toString().equals("Y")) {
                    showOldHistory = true;
                } else {
                    showOldHistory = false;
                }
            }
        }
        return showOldHistory;
    }
    private RichPopup oldPricePopUpBind;
    private RichLink histLinkBind;

    public void setShowWty(Boolean showWty) {
        this.showWty = showWty;
    }

    public Boolean getShowWty() {
        if (showWty == null) {
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
                    showWty = true;
                } else {
                    showWty = false;
                }
            }
        }
        return showWty;
    }
    private Boolean disableAsblValField = null;
    private RichPopup schemeItemPopUpPgBind;
    private RichLink appliedSchemePgBind;
    private RichTable schemeDataTablePgBind;
    private RichOutputText schemeNameTablePgBind;
    private RichPopup selectSrNoFreePopUpBind;
    private RichOutputFormatted itmIssueSrFreeBind;
    private RichOutputFormatted itmQtyIssueFreeBind;
    private RichOutputText totFreeStkLotBind;
    private RichPopup selectFreeLotPopUpBind;
    private RichOutputFormatted itmQtyIssueFreeLblBind;
    private RichOutputText totStkLotBinFreeBind;
    private RichPopup selectLotBinFreePopUpBind;
    private RichLink appliedSchmRushOrdPgBind;
    private RichLink viewSchmLinkPgBind;
    private RichPopup schemePopUpPgBind;
    private RichSelectOneChoice schemeIdPgBind;
    private RichToolbar toolBarBinding;

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

    public SalesOrdAddEditBean() {
        //  System.out.println("date uuis in bean " + date);
    }

    /***
 * Generic Methods to be used in Whole Application
 * **/


    /**
     * Method to resolve expression.
     */
    public String resolvEl(String data) {
        return ADFModelUtils.resolvEl(data).toString();
    }

    /**
     * Method to resolve expression.
     */
    public Object resolvElO(String data) {
        return ADFModelUtils.resolvEl(data);
    }

    /**
     * Method to open a popup using binding.
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

    /**Method to get Binding Container*/
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     * Generic Method to execute operation
     * */
    public OperationBinding executeOperation(String operation) {
        OperationBinding createParam = getBindings().getOperationBinding(operation);
        return createParam;

    }

    /**
     * @Method to disclose First tab on Create and Save of Sales Order
     */
    public void setDisclosedFirstTab() {
        RichPanelTabbed richPanelTabbed = getPanelTabbedBind();
        for (UIComponent child : richPanelTabbed.getChildren()) {
            RichShowDetailItem sdi = (RichShowDetailItem) child;

            if (sdi.getClientId().equals(itemDetailsTabBind.getClientId())) {
                sdi.setDisclosed(true);

            } else {
                sdi.setDisclosed(false);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabbedBind);
    }

    /***************Action and ActionListeners Defined for Sales Order Create,Edit page************/

    /**
     * @param actionEvent
     */
    public void newOrder(ActionEvent actionEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("isFinancialYearValid");
        binding.execute();
        Object object = binding.getResult();
        if (object.equals((Object) true)) {
            setPoModeToNull();

            executeOperation("Createwithparameters").execute();
            this.setApp_mode("A");
            Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
            pageFlowScope.put("PARAM_ORD_MODE", "A");
            setDisclosedFirstTab();
            chkDlcSchdlItm = null;

        }
    }

    /**
     * Call this save function when order  is of type OPEN CONTRACT TYPE
     * */
    public String callSave() {

        String retVal = "N";
        // System.out.println("Before call");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("chkSlsSo");
        operationBinding.execute();
        // System.out.println("After Call " + Integer.parseInt(operationBinding.getResult().toString()));

        if (Integer.parseInt(operationBinding.getResult().toString()) == 0) {

            /* if (amdFlg.equalsIgnoreCase("Y")) {
                _log.info("Amendment Method called");
                OperationBinding setAmd = executeOperation("setAmndmntDtl");
                setAmd.getParamsMap().put("amdNo", amd_No + 1);
                setAmd.execute();
            } */

            if (cancelOrderBind.getValue() != null) {
                String cancelOrd = cancelOrderBind.getValue().toString();
                _log.info("Cancel order Flag-" + cancelOrd);
                if (cancelOrd.equalsIgnoreCase("true")) {
                    OperationBinding cancOrd = executeOperation("updateOrderStausCancel");
                    cancOrd.getParamsMap().put("canc_Flag", cancelOrd);
                    cancOrd.execute();
                }
            }
            if (cancelOrderBind.getValue() != null) {
                String cancelOrd = cancelOrderBind.getValue().toString();
                _log.info("Cancel order Flag-" + cancelOrd);
                if (cancelOrd.equalsIgnoreCase("true")) {
                    OperationBinding cancOrd = executeOperation("updateOrderStausCancel");
                    cancOrd.getParamsMap().put("canc_Flag", cancelOrd);
                    cancOrd.execute();
                }
            }

            if (shortCloseOrdrBind.getValue() != null) {
                String cancelOrd = shortCloseOrdrBind.getValue().toString();
                _log.info("Short close order Flag-" + cancelOrd);
                if (cancelOrd.equalsIgnoreCase("true")) {
                    OperationBinding cancOrd = executeOperation("updateOrderStausShortClose");
                    cancOrd.getParamsMap().put("srt_close_Flag", cancelOrd);
                    cancOrd.execute();
                }
            }

            //Function Call to generate SO Number
            OperationBinding ob = executeOperation("generateDispDocNo");
            ob.execute();
            String dispId = "";
            if (ob.getResult() != null) {
                dispId = ob.getResult().toString();
            }
            _log.fine("Generate display id called : " + ob.getResult());
            operationBinding = this.getBindings().getOperationBinding("Commit");
            operationBinding.execute();
            if (operationBinding.getErrors().isEmpty()) {
                _log.fine("Comitted Sucessfully.");
            } else {
                _log.fine("Error on commit : " + operationBinding.getErrors());
            }
            executeOperation("Commit").execute();
            _log.fine("Commited : ");
            executeOperation("Execute").execute();
            _log.fine("Executed SlsSoVo : ");
            executeOperation("orderCalculation").execute();
            _log.fine("Order Calculations done.");
            executeOperation("Commit").execute();
            _log.fine("Committed.");
            if (dispId != null) {
                String diplsyId = dispId;
                if (diplsyId.startsWith("S")) {
                } else {
                    diplsyId = diplsyId.substring(2);
                }
                String so = resolvEl("#{bundle['MSG.855']}").toString();

                /*StringBuilder saveMsg =
                new StringBuilder("<html><body><p><b>Sales Order " + diplsyId + " Saved Successfully</b></p>");
               saveMsg.append("<ul><li>Total Specific Amount:- <b>" + amtSp.getResult() + "</b></li>");
                saveMsg.append("<li>Total Basic Amount:- <b>" + amtBs.getResult() + "</b></li></ul>");

                saveMsg.append("</body></html>");*/
                StringBuilder saveMsg =
                    new StringBuilder("<html><body><b><p style='color:red'>" + so +
                                      "&nbsp; no : &nbsp; <span style='color:blue;'>" + diplsyId +
                                      "</span> &nbsp; <span style='color:#333'>" +
                                      resolvEl("#{bundle['MSG.347']}").toString() + " !" + "</span></p></b>");
                saveMsg.append("</body></html>");

                /* StringBuilder saveMsg =
                    new StringBuilder("<html><body><p><b> " + so+"&nbsp; " + diplsyId +"&nbsp; "+resolvEl("#{bundle['MSG.347']}").toString() +
                                      "</b></p></body></html>");
                 */ /*  saveMsg.append("<ul><li>" + resolvEl("#{bundle['MSG.856']}").toString() + "<b>" +
                               amtSp.getResult() + "</b></li>");
                saveMsg.append("<li>" + resolvEl("#{bundle['MSG.858']}").toString() + "<b>" +
                               amtBs.getResult() + "</b></li></ul>"); */


                FacesMessage msg = new FacesMessage(saveMsg.toString());
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                _log.fine("Sucessfully Message shown..");
            }

            //   executeOperation("executeViewObjects").execute();
            _log.fine("All view Objects executed.");
            this.setApp_mode("V");
            this.canc_Flg = "N";
            Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
            pageFlowScope.put("PARAM_ORD_MODE", "V");
            this.amdFlg = "N";
            setDisclosedFirstTab();

            /***Code to execute SLS$SO header and set on current docId**/
            String curDocID = "N";
            OperationBinding docIdCur = executeOperation("getcurDocID");
            docIdCur.execute();
            if (docIdCur.getResult() != null) {
                curDocID = docIdCur.getResult().toString();
                if (curDocID != "N") {
                    _log.info("Doc Id is--" + curDocID);
                    executeOperation("Execute5").execute();
                    OperationBinding setCurRow = executeOperation("setOnCurRow");
                    setCurRow.getParamsMap().put("doc_id", curDocID);
                    setCurRow.execute();
                    _log.fine("Method setting current DocId id called..");
                }
            }

            boolean b = callWorkFlowFunctions();
            if (b == false) {
                retVal = "N";
            } else {
                retVal = "Y";
            }


            executeOperation("Commit").execute();
            retVal = "Y";

        } else {
            StringBuilder Msg =
                new StringBuilder("<html><body><b><p style='color:red'>" + "Please enter all required fields." +
                                  "</p></b></body></html>");
            FacesMessage msg = new FacesMessage(Msg.toString());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }

        return retVal;
    }

    /**
     * Save Button code
     * */
    public String saveButtonAction() {
        String retVal = "N";
        System.out.println(" Inside sopmerh ");
        OperationBinding a = ADFBeanUtils.findOperation("isflexFieldNull");
        a.execute();

        if ((Boolean) a.getResult()) {
            ADFModelUtils.showFormattedFacesMessage("Mandatory Field(s) cannnot be Empty",
                                                    "Please enter values for Mandatory fields in Other Details ", //The function for Generating Warranty returned
                                                    FacesMessage.SEVERITY_ERROR);
        } else {

            OperationBinding binding = this.getBindings().getOperationBinding("isItemPriceForEachItmValid");
            binding.execute();
            Object object = (binding.getResult() == null ? false : binding.getResult());


            if (object.equals((Object) false)) {
                _log.info("Invalid itm price");
            } else {
                //checking tax applied oderwise or itemwise
                OperationBinding checkAppTax = executeOperation("checkTaxApplOrNot");
                checkAppTax.execute();
                String appliedOrNot = checkAppTax.getResult().toString();
                _log.info("Applied or Not flag-->" + appliedOrNot);

                if ("N".equalsIgnoreCase(appliedOrNot)) {
                    OperationBinding checkOrdTax = executeOperation("checkOrderTaxAmt");
                    checkOrdTax.execute();
                    StringBuilder flag = new StringBuilder("N");
                    if (checkOrdTax.getResult() != null) {
                        flag = new StringBuilder(checkOrdTax.getResult().toString());
                    }
                    _log.info("Flag is-->" + checkOrdTax.getResult());
                    if ("N".equalsIgnoreCase(flag.toString())) {
                        // FacesMessage msg = new FacesMessage("Taxable Amount changed ,Re-Apply TAX");
                        /* FacesMessage msg = new FacesMessage(resolvEl("#{bundle['MSG.851']}"));
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg); */
                    } else {
                        /* if (amdFlg.equalsIgnoreCase("Y")) {
                        _log.info("Amendment Method called");
                        OperationBinding setAmd = executeOperation("setAmndmntDtl");
                        setAmd.getParamsMap().put("amdNo", amd_No + 1);
                        setAmd.execute();
                    } */

                        if (cancelOrderBind.getValue() != null) {
                            String cancelOrd = cancelOrderBind.getValue().toString();
                            _log.info("Cancel order Flag-" + cancelOrd);
                            if (cancelOrd.equalsIgnoreCase("true")) {
                                OperationBinding cancOrd = executeOperation("updateOrderStausCancel");
                                cancOrd.getParamsMap().put("canc_Flag", cancelOrd);
                                cancOrd.execute();
                            }
                        }
                        if (cancelOrderBind.getValue() != null) {
                            String cancelOrd = cancelOrderBind.getValue().toString();
                            _log.info("Cancel order Flag-" + cancelOrd);
                            if (cancelOrd.equalsIgnoreCase("true")) {
                                OperationBinding cancOrd = executeOperation("updateOrderStausCancel");
                                cancOrd.getParamsMap().put("canc_Flag", cancelOrd);
                                cancOrd.execute();
                            }
                        }

                        if (shortCloseOrdrBind.getValue() != null) {
                            String cancelOrd = shortCloseOrdrBind.getValue().toString();
                            _log.info("Short close order Flag-" + cancelOrd);
                            if (cancelOrd.equalsIgnoreCase("true")) {
                                OperationBinding cancOrd = executeOperation("updateOrderStausShortClose");
                                cancOrd.getParamsMap().put("srt_close_Flag", cancelOrd);
                                cancOrd.execute();
                            }
                        }
                        //Function Call to generate SO Number
                        OperationBinding docIdCur = executeOperation("getcurDocID");
                        docIdCur.execute();
                        // Commented on 11-09-2015 | AJ | 17763
                        //Method call to get Specific and Basic Order Amount
                        //OperationBinding amtSp = executeOperation("TotAmtSp");
                        //OperationBinding amtBs = executeOperation("TotAmtBs");
                        //amtSp.execute();
                        //amtBs.execute();
                        //Object amtSpO = amtSp.getResult();
                        //Object amtBsO = amtBs.getResult();
                        OperationBinding ob = executeOperation("generateDispDocNo");
                        ob.execute();
                        String dispId = "";
                        if (ob.getResult() != null) {
                            dispId = ob.getResult().toString();
                        }
                        _log.fine("Generate display id called : " + ob.getResult());
                        executeOperation("Execute").execute();
                        //_log.fine("Amount sp : " + amtSpO + " Amount bs : " + amtBsO);
                        executeOperation("orderCalculation").execute();
                        _log.fine("Order Calculations done.");

                        Object param_mode = resolvElO("#{pageFlowScope.PARAM_ORD_MODE}");
                        if (this.getApp_mode().equals("A") || (param_mode != null) ?
                            ((param_mode.toString()).equals("A") ? true : false) : false) {
                            OperationBinding quotcheck = getBindings().getOperationBinding("changeQuotationStatus");
                            quotcheck.execute();
                        }
                        /**
                     *  This line is used to avoid another user have changes the row with key
                     *  Here it stores the values before commit and then in another method named setValuesBeforeCommit it sets the values back.
                     *  The value is stored in DispDocId Method
                     */
                        ADFBeanUtils.getOperationBinding("setValuesBeforeCommit").execute();

                        //Updating cost center amount
                        if (getEnableCostCenter()) {
                            // System.out.println("Cost Center is enable ");
                            ADFBeanUtils.findOperation("updateCostCenterOnSave").execute();
                            //Inserting Cost center data to sls cost center
                            ADFBeanUtils.getOperationBinding("sendDateFromTempCcToSlsSoCc").execute();
                        }


                        executeOperation("Commit").execute();

                        _log.fine("Committed.");
                        if (dispId != null) {
                            String diplsyId = dispId;
                            String so = resolvEl("#{bundle['MSG.855']}").toString();

                            ADFBeanUtils.getOperationBinding("showSaveSucessfullMessage").execute();

                        }

                        executeOperation("executeViewObjects").execute();
                        _log.fine("All view Objects executed.");
                        this.setApp_mode("V");
                        this.canc_Flg = "N";
                        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
                        pageFlowScope.put("PARAM_ORD_MODE", "V");
                        this.amdFlg = "N";
                        setDisclosedFirstTab();

                        /***Code to execute SLS$SO header and set on current docId**/
                        String curDocID = "N";
                        //OperationBinding docIdCur = executeOperation("getcurDocID");
                        //docIdCur.execute();
                        if (docIdCur.getResult() != null) {
                            curDocID = docIdCur.getResult().toString();
                            if (curDocID != "N") {
                                _log.info("Doc Id is--" + curDocID);
                                executeOperation("Execute5").execute();
                                OperationBinding setCurRow = executeOperation("setOnCurRow");
                                setCurRow.getParamsMap().put("doc_id", curDocID);
                                setCurRow.execute();
                                _log.fine("Method setting current DocId id called..");
                            }
                        }

                        boolean b = callWorkFlowFunctions();
                        if (b == false) {
                            retVal = "N";
                        } else {
                            retVal = "Y";
                        }
                        /**
                     *  This line is used to avoid another user have changes the row with key
                     *  Here it fetches the previously stored data in setValuesBeforeCommit and set the values before commit.
                     */
                        ADFBeanUtils.getOperationBinding("getValuesBeforeCommit").execute();
                        executeOperation("Commit").execute();

                    }
                } else if ("O".equalsIgnoreCase(appliedOrNot)) {
                    /*  StringBuilder ordtaxMsg =
                    new StringBuilder("<html><body><p><b>Order Wise TAX Selected, but not applied</b></p>");
                ordtaxMsg.append("<ul><li> Either Remove TAX Flag</b></li>");
                ordtaxMsg.append("<li>Or Apply It</b></li></ul>");

                ordtaxMsg.append("</body></html>");*/
                    /* StringBuilder ordtaxMsg =
                    new StringBuilder("<html><body><p><b>" + resolvEl("#{bundle['MSG.862']}").toString() + "</b></p>");
                ordtaxMsg.append("<ul><li>" + resolvEl("#{bundle['MSG.863']}").toString() + "</b></li>");
                ordtaxMsg.append("<li>" + resolvEl("#{bundle['MSG.865']}").toString() + "</b></li></ul>");

                ordtaxMsg.append("</body></html>");
                FacesMessage msg = new FacesMessage(ordtaxMsg.toString());
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg); */
                } else if ("I".equalsIgnoreCase(appliedOrNot)) {
                    /*  StringBuilder itmtaxMsg =
                    new StringBuilder("<html><body><p><b>Item Wise TAX Selected, but not applied</b></p>");
                itmtaxMsg.append("<ul><li> Either Remove TAX Flag</b></li>");
                itmtaxMsg.append("<li>Or Apply It on Item</b></li></ul>");

                itmtaxMsg.append("</body></html>");*/
                    StringBuilder itmtaxMsg =
                        new StringBuilder("<html><body><p><b>" + resolvEl("#{bundle['MSG.868']}").toString() +
                                          "</b></p>");
                    itmtaxMsg.append("<ul><li>" + resolvEl("#{bundle['MSG.863']}").toString() + "</b></li>");
                    itmtaxMsg.append("<li>" + resolvEl("#{bundle['MSG.869']}").toString() + "</b></li></ul>");
                    itmtaxMsg.append("</body></html>");
                    FacesMessage msg = new FacesMessage(itmtaxMsg.toString());
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
                return retVal;
            }
        }
        return retVal;


    }

    private Boolean isExiceValid() {
        Boolean b = true;
        OperationBinding ob = ADFBeanUtils.findOperation("isExciseValid");
        ob.execute();
        b = (Boolean) ob.getResult();
        return b;
    }

    /**
     * @param actionEvent
     */
    public void saveAction(ActionEvent actionEvent) {
        System.out.println("Inside save action of Order");
        /*
        if (!isExiceValid()) { //check for exice organization

        } else  */if (!advanceConditionMet()) { //checking for advance payment

            //} else if (callValidationForLot()) {
            //   saveButtonAction();
        } else if (ordTypeBind.getValue().equals(481)) { // for open contract
            callSave();
        } else {
            //ADFBeanUtils.findOperation("setRushOrderStatus").execute();
            System.out.println("Inside calling save button action");
            saveButtonAction();
        }
        //834
        //parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
    }

    protected boolean callValidationForLot() {
        boolean bool = false;
        if (ordTypeBind.getValue().equals(309) || ordTypeBind.getValue().equals(310)) {
            System.out.println(ordTypeBind.getValue() + " <<<< Order Type");
            OperationBinding valArr = executeOperation("checkItemIssueValidation");
            valArr.execute();
            ArrayList<String> itmArr = (ArrayList<String>) valArr.getResult();

            OperationBinding freVal = executeOperation("checkFreeItemIssueValidation");
            freVal.execute();
            ArrayList<String> freArr = (ArrayList<String>) freVal.getResult();

            if (valArr.getResult() != null && itmArr.size() != 0) { // condition checking for the lot issued for the sales Item
                System.out.println("  item lot not done ===========");

                System.out.println("  Size of the result array ===========" + itmArr.size());
                /* StringBuilder saveMsg =
                                new StringBuilder("<html><body><b><p style='color:red'>Following Items are not issued with full quantity,Can't Move</p></b>");*/
                StringBuilder saveMsg =
                    new StringBuilder("<html><body><b><p style='color:red'>" +
                                      resolvEl("#{bundle['MSG.870']}").toString() + "</p></b>");
                saveMsg.append("<ul>");
                for (String a : itmArr) {
                    saveMsg.append("<li> <b>" + a + "</b></li>");
                }
                saveMsg.append("</ul><br>");
                // saveMsg.append("<b>What to Do:");
                saveMsg.append("<b>" + resolvEl("#{bundle['MSG.871']}").toString() + "");
                // saveMsg.append("<ul style='color:darkgreen'><li>Isuue all Items with full quantity from LOT</li><li>Delete Un-Issued Items</li></ul></b>");
                saveMsg.append("<ul style='color:darkgreen'><li>" + resolvEl("#{bundle['MSG.872']}").toString() +
                               "</li><li>" + resolvEl("#{bundle['MSG.873']}").toString() + "</li></ul></b>");

                saveMsg.append("</body></html>");
                FacesMessage msg = new FacesMessage(saveMsg.toString());
                msg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                bool = false;

            } else if (freVal.getResult() != null && freArr.size() != 0) { //Condition checking for lot quantity  for free item
                System.out.println(" free item lot not done ++++++++++++++++++");


                System.out.println("  Size of the free result array  ++++++++++++++++++ " + freArr.size());
                /* StringBuilder saveMsg =
                                    new StringBuilder("<html><body><b><p style='color:red'>Following Items are not issued with full quantity,Can't Move</p></b>");*/
                StringBuilder saveMsg =
                    new StringBuilder("<html><body><b><p style='color:red'>" +
                                      resolvEl("#{bundle['MSG.870']}").toString() + "</p></b>");
                saveMsg.append("<ul>");
                for (String a : freArr) {
                    saveMsg.append("<li> <b>" + a + "</b></li>");
                }
                saveMsg.append("</ul><br>");
                // saveMsg.append("<b>What to Do:");
                saveMsg.append("<b>" + resolvEl("#{bundle['MSG.871']}").toString() + "");
                // saveMsg.append("<ul style='color:darkgreen'><li>Isuue all Items with full quantity from LOT</li><li>Delete Un-Issued Items</li></ul></b>");
                saveMsg.append("<ul style='color:darkgreen'><li>" + resolvEl("#{bundle['MSG.872']}").toString() +
                               "</li></ul></b>");

                saveMsg.append("</body></html>");
                FacesMessage msg = new FacesMessage(saveMsg.toString());
                msg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                bool = false;

            } else {
                bool = true;
            }
            //

        }

        /*        OperationBinding valArr = executeOperation("checkItemIssueValidation");
        valArr.execute();
        ArrayList<String> itmArr = null;
        if (valArr.getResult() != null) {
            itmArr = (ArrayList<String>) valArr.getResult();
        }
        if ((ordTypeBind.getValue().equals(309) || ordTypeBind.getValue().equals(310)) && valArr.getResult() != null &&
            itmArr.size() != 0) {
             StringBuilder saveMsg =
                    new StringBuilder("<html><body><b><p style='color:red'>Following Items are not issued with full quantity,Can't Move</p></b>");
            StringBuilder saveMsg =
                new StringBuilder("<html><body><b><p style='color:red'>" + resolvEl("#{bundle['MSG.870']}").toString() +
                                  "</p></b>");
            saveMsg.append("<ul>");
            for (String a : itmArr) {
                saveMsg.append("<li> <b>" + a + "</b></li>");
            }
            saveMsg.append("</ul><br>");
            // saveMsg.append("<b>What to Do:");
            saveMsg.append("<b>" + resolvEl("#{bundle['MSG.871']}").toString() + "");
            // saveMsg.append("<ul style='color:darkgreen'><li>Isuue all Items with full quantity from LOT</li><li>Delete Un-Issued Items</li></ul></b>");
            saveMsg.append("<ul style='color:darkgreen'><li>" + resolvEl("#{bundle['MSG.872']}").toString() +
                           "</li><li>" + resolvEl("#{bundle['MSG.873']}").toString() + "</li></ul></b>");

            saveMsg.append("</body></html>");
            FacesMessage msg = new FacesMessage(saveMsg.toString());
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else
         */
        //    System.out.println("the bool value is ===== " + bool);
        return bool;
    }

    private Boolean advanceConditionMet() {
        Boolean b = true;
        OperationBinding binding = this.getBindings().getOperationBinding("isAdvancePaymentConditionTrue");
        binding.execute();
        b = (Boolean) binding.getResult();
        // System.out.println(b + " ====================== The result of advance condition met function");
        return b;
    }

    /**Add item to Order
     * @param actionEvent
     */
    public void addItemButton(ActionEvent actionEvent) {
        //doPoExist
        /**
         * The method below is used to check if the selected entity is customer or not
         */ //
        OperationBinding B3 = this.getBindings().getOperationBinding("mandatoryCheck");
        B3.execute();
        Object o = B3.getResult();
        // Method to check if current eo is a Prospect
        OperationBinding operationBinding = this.getBindings().getOperationBinding("checkIfCoaExistsForSelectedEo");
        operationBinding.execute();

        OperationBinding b1 = ADFBeanUtils.getOperationBinding("isPricePolicyValid");
        b1.execute();

        if (!(Boolean) o) {

        } else if (!(Boolean) b1.getResult()) {

        } else if (operationBinding.getResult().equals((Object) true)) {
            OperationBinding binding = this.getBindings().getOperationBinding("doPoExist");
            binding.execute();
            Object object = binding.getResult();
            _log.info("Result is : " + object);
            if (object.equals((Object) false)) {
                if (checkMandateField().equalsIgnoreCase("Y")) {
                    ADFBeanUtils.getOperationBinding("Execute13").execute();
                    OperationBinding ob = executeOperation("CreateInsert");
                    ob.execute();

                    if (ob.getErrors().isEmpty()) {
                        OperationBinding createParam = executeOperation("serialNumGen");
                        createParam.getParamsMap().put("voName", "SlsSoItm1");
                        createParam.execute();
                        this.app_mode = "A";
                        //AdfFacesContext.getCurrentInstance().addPartialTarget("");
                        try {

                            StringBuffer clientId = new StringBuffer(itmQtyBind.getClientId());
                            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
                            if (getShowItmDesc().toString().equals("N")) {
                                clientId = clientId.append("itmNmTransId");
                            } else {
                                clientId = clientId.append("itmId2Id");
                            }

                            FacesMessage errMsg = new FacesMessage("Please select an Item !");
                            errMsg.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext ctx = FacesContext.getCurrentInstance();
                            ctx.addMessage(clientId.toString(), errMsg);
                            // System.out.println("Client id is : " + clientId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            } else {
                FacesMessage errMsg =
                    new FacesMessage("Purchase Order have already been created. You cannot add another Item.");
                errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, errMsg);
            }
        } else {

        }


    }

    /**
     *      Method to show validation message(I,E,W)
     *      mesg:Message to display
     *      sev:Severity(I,E,W)
     *      chk:true=if resource bundle is used
     * */
    public void showFacesMessage(String mesg, String sev, Boolean chk, String Typflg) {
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true) {
            String msg = resolvEl("#{bundle['" + mesg + "']}");
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
        if (Typflg.equalsIgnoreCase("F")) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        } else if (Typflg.equalsIgnoreCase("V")) {
            throw new ValidatorException(message);
        }
    }

    /**Method to save Delivery Schedule
     * @param actionEvent
     */
    public void saveDelverySchdl(ActionEvent actionEvent) {
        OperationBinding qtyChk = ADFBeanUtils.getOperationBinding("isDeliveryQtyValidForDlvSchdl");
        qtyChk.execute();
        Object object = qtyChk.getResult();
        Boolean v = (object == null ? false : (Boolean) object);
        if (v) {
            OperationBinding ob = executeOperation("saveDeliverySchedule");
            ob.execute();
            if (ob.getResult() != null) {
                String flag = ob.getResult().toString();
                //  System.out.println("Flag from method is-->" + flag);
                if ("W".equalsIgnoreCase(flag)) {
                    showFacesMessage("MSG.411", "E", true, "F");
                } else if ("D".equalsIgnoreCase(flag)) {
                    FacesMessage errMsg = new FacesMessage(resolvEl("#{bundle['MSG.410']}"));
                    errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(schdlQuantTrans.getClientId(), errMsg);
                } else if ("S".equalsIgnoreCase(flag)) {
                    showFacesMessage("MSG.413", "E", true, "F");
                } else if ("T".equals(flag.toString())) {
                    ADFModelUtils.showFacesMessage("Please select a Delivery Mode !",
                                                   "Delivery Mode is mandatory for Creating Delivery Sechedule.",
                                                   FacesMessage.SEVERITY_ERROR, null);
                } else if ("P".equals(flag.toString())) {
                    ADFModelUtils.showFacesMessage("Please select Project !",
                                                   "Project Selection is mandatory for Creating Delivery Sechedule.",
                                                   FacesMessage.SEVERITY_ERROR, null);
                }
            }
            executeOperation("Execute9").execute();
        }
        chkDlcSchdlItm = null;
    }


    /**
     * @param actionEvent
     */
    public void savePmtSchdl(ActionEvent actionEvent) {

        OperationBinding ob = ADFBeanUtils.findOperation("paymentDateValidation");
        ob.getParamsMap().put("paydt", date);
        Object ret = ob.execute();
        if (ret != null) {
            String flg = (String) ret;
            // System.out.println(" the value of valdiation check is " + flg);

            if ("Y".equalsIgnoreCase(flg)) {
                //  String msg2 = "Duplicate Payment Date.";
                //String msg2 = resolvElDCMsg("#{bundle['MSG.1025']}").toString();
                String msg2 = " Duplicate payment Date for same mode is not allowed!";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message2);

            } else {
                OperationBinding pmtDone = executeOperation("donePaySchdlAction");
                pmtDone.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(paymentScheduleTable);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(paymentSchduleFormBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(paymentScheduleTable);

    }

    /**
     * @param actionEvent
     */
    public void deletePaymentLine(ActionEvent actionEvent) {
        OperationBinding ob = executeOperation("Delete");
        ob.execute();
        OperationBinding ob1 = executeOperation("Execute1");
        ob1.execute();
    }

    /**
     * @param actionEvent
     */
    public void deleteDlvSchdl(ActionEvent actionEvent) {
        OperationBinding postChange = executeOperation("postChangesDlvSchdl");
        postChange.execute();
        chkDlcSchdlItm = null;
    }


    /**
     * @param actionEvent
     */
    public void selectAllAction(ActionEvent actionEvent) {
        OperationBinding selectAll = executeOperation("selectAllCheckBox");
        selectAll.getParamsMap().put("val", true);
        selectAll.execute();
    }

    /**
     * @param actionEvent
     */
    public void deSelectAllAction(ActionEvent actionEvent) {
        OperationBinding selectAll = executeOperation("selectAllCheckBox");
        selectAll.getParamsMap().put("val", false);
        selectAll.execute();
    }

    /**
     * Method to apply Order Wise Tax
     * @param actionEvent
     */
    public void applyOrderWiseTaxAction(ActionEvent actionEvent) {
        if (app_mode != "V") {
            OperationBinding binding = this.getBindings().getOperationBinding("areEntriesValidForTaxApplication");
            binding.execute();
            Object object = binding.getResult();
            if (object.equals((Boolean) true)) {
                ADFBeanUtils.getOperationBinding("fetchAndSetDefaultTaxOrderWise").execute();
                showPopup(orderTaxPopUpBind, true);
            } else {
                StringBuilder ordlotMsgHead =
                    new StringBuilder("<html><body><b><p style='color:red'>Item Id for one of the Item Lines is not selected !" +
                                      "</p></b>");

                ordlotMsgHead.append("<b>Please select Item Id before applying tax.");
                ordlotMsgHead.append("</body></html>");
                FacesMessage msg = new FacesMessage(ordlotMsgHead.toString());
                msg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            showPopup(orderTaxPopUpBind, true);
        }
        //To refresh Item table after changes made in Tax
        // ADFBeanUtils.findIterator("SlsSoItm1Iterator").executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailsTabBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmFormLayoutBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
    }

    /**Method to add Other Charges in Sales Order
     * @param actionEvent
     */
    public void addOtherChargesLink(ActionEvent ace) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("addOtherCharges");
        binding.execute();
        Object o = binding.getResult();
        Integer i = (o == null ? -1 : (Integer) o);
        if (i.equals(1)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("coaNmTransId");
            ADFModelUtils.showFacesMessage("Chart Of Account for Other Charges have not been selected !",
                                           "Please select Chart of Account.", FacesMessage.SEVERITY_ERROR,
                                           clientId.toString());
        } else if (i.equals(6)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("coaNmTransId");
            ADFModelUtils.showFacesMessage("Duplicate Chart of Account !",
                                           "Duplicate Chart of Account have been selected. Please select Chart of account which is not already added in Other Charges.",
                                           FacesMessage.SEVERITY_ERROR, clientId.toString());
        } else if (i.equals(2)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("soc1");
            ADFModelUtils.showFacesMessage("Other Charges Desc have not been selected !",
                                           "Please select Other Charges Desc.", FacesMessage.SEVERITY_ERROR,
                                           clientId.toString());
        } else if (i.equals(3)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("soc6");
            ADFModelUtils.showFacesMessage("Other Charges Amount Type have not been selected !",
                                           "Please select Amount Type.", FacesMessage.SEVERITY_ERROR,
                                           clientId.toString());
        } else if (i.equals(4)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("it145");
            ADFModelUtils.showFacesMessage("Other Charges Amount should be greater than zero !",
                                           "Please select valid Amount.", FacesMessage.SEVERITY_ERROR,
                                           clientId.toString());
        } else if (i.equals(5)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("it140");
            ADFModelUtils.showFacesMessage("Sum of the Charges Amount should not be greater than Sales Order Amount !",
                                           "Please select valid Amount.", FacesMessage.SEVERITY_ERROR,
                                           clientId.toString());
            ////;;
        }
    }

    /**
     * Method to apply Item wise tax
     * @param actionEvent
     */
    public void applyItemWiseTaxAction(ActionEvent actionEvent) {
        if (app_mode != "V") {
            OperationBinding ob = executeOperation("newOrOldtaxItem");
            ob.execute();
            // String flag = ob.getResult().toString();
        }
        // _log.info("Flag for taxRule-->" + flag);
        /* if ("Y".equalsIgnoreCase(flag)) {
            executeOperation("CreateInsert3").execute();
             if (ob.getErrors().isEmpty()) {
                OperationBinding createParam = executeOperation("serialNumGen");
                createParam.getParamsMap().put("voName", "SlsSoTr2");
                createParam.execute();
            }
        } */
        showPopup(itemTaxPopUpBind, true);
        //To refresh Item table after changes made in Tax
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailsTabBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmFormLayoutBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
        // AdfFacesContext.getCurrentInstance().addPartialTarget(tottaxAmntBind);
        // AdfFacesContext.getCurrentInstance().addPartialTarget(orderTotCostBind);
    }

    /**Add Other Charges Link
     * @param actionEvent
     */
    public void addOcLink(ActionEvent actionEvent) {
        showPopup(ocPopUpBind, true);
    }

    /**Add Discount to Order
     * @param actionEvent
     */
    public void addDiscOrderAction(ActionEvent actionEvent) {
        showPopup(discOrderPopUpBind, true);
    }

    /**Method to delete item*/
    public void deleteItemLink(ActionEvent actionEvent) {
        String flag = "Y";

        OperationBinding ob = executeOperation("checkItemDelete");
        ob.execute();
        if (ob.getResult() != null) {
            flag = ob.getResult().toString();
        }
        if (flag.equalsIgnoreCase("L")) {
            /* StringBuilder ordlotMsg =
                new StringBuilder("<html><body><p><b>Item is issued from Lot, Can not delete</b></p>");*/
            StringBuilder ordlotMsg =
                new StringBuilder("<html><body><p><b>" + resolvEl("#{bundle['MSG.874']}").toString() + "</b></p>");

            ordlotMsg.append("</body></html>");
            FacesMessage msg = new FacesMessage(ordlotMsg.toString());
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else if (flag.equalsIgnoreCase("T")) {
            //StringBuilder ordtaxMsg =
            //new StringBuilder("<html><body><p><b>TAX is applied on this Item, Can not delete</b></p>");
            StringBuilder ordtaxMsg =
                new StringBuilder("<html><body><p><b>" + resolvEl("#{bundle['MSG.876']}").toString() + "</b></p>");
            // ordtaxMsg.append("<ul><li>First Remove TAX </b></li>");
            ordtaxMsg.append("<ul><li>" + resolvEl("#{bundle['MSG.877']}").toString() + " </b></li>");
            // ordtaxMsg.append("<li>Then try again</b></li></ul>");
            ordtaxMsg.append("<li>" + resolvEl("#{bundle['MSG.878']}").toString() + "</b></li></ul>");

            ordtaxMsg.append("</body></html>");
            FacesMessage msg = new FacesMessage(ordtaxMsg.toString());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            ADFBeanUtils.getOperationBinding("removeDiscBasisForItem").execute();
            executeOperation("deleteCostCenterItem").execute();
            //commented on 7th july for unknown ppr error
            itmQtyBind.processUpdates(FacesContext.getCurrentInstance());
            // AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
            ADFBeanUtils.getOperationBinding("setDefaultItmIdIfItmIdIsNull").execute();
            //executeOperation("Delete2").execute();
            executeOperation("Execute").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailsTabBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmFormLayoutBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
        }
    }

    /**Method to delete Terms & Conditions*/
    public void deleteTnCAction(ActionEvent actionEvent) {
        executeOperation("Delete3").execute();
        executeOperation("Execute3").execute();
    }


    /**Re-Apply Tax on Item
     * @param actionEvent
     */
    public void reApplyItmTaxAction(ActionEvent actionEvent) {
        if (exmptFlgItmBind.getValue() != null) {
            String exmpTax = exmptFlgItmBind.getValue().toString();
            // System.out.println("Exmpt flg Itme is--" + exmpTax);
        }
        if (taxRuleIdItmBind.getValue() != null) {
            Integer ruleId = Integer.parseInt(taxRuleIdItmBind.getValue().toString());
            _log.info("Tax Rule id is-->" + ruleId);
            OperationBinding taxOrd = executeOperation("procTaxItem");
            taxOrd.getParamsMap().put("ruleId", ruleId);
            taxOrd.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailsTabBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmFormLayoutBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);

        }
    }

    /**Method to Re-Apply TAX on Order
     * @param actionEvent
     */
    public void reApplyOrdTaxAction(ActionEvent actionEvent) {
        if (taxRuleIdOrderBind.getValue() != null) {
            Integer ruleId = Integer.parseInt(taxRuleIdOrderBind.getValue().toString());
            _log.info("Tax Rule id ord is-->" + ruleId);
            OperationBinding taxOrd = executeOperation("procTaxOrder");
            taxOrd.getParamsMap().put("ruleId", ruleId);
            taxOrd.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
        }
    }

    /**Edit SalesOrder Action
     * @param actionEvent
     */
    public void editOrderAction(ActionEvent actionEvent) {
        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));

        Integer pending = 0;

        //Check Pending
        OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
        chkUsr.getParamsMap().put("SlocId", sloc_Id);
        chkUsr.getParamsMap().put("CldId", cld_Id);
        chkUsr.getParamsMap().put("OrgId", org_Id);
        chkUsr.getParamsMap().put("DocNo", 21503);
        chkUsr.execute();

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
        }
        _log.info("Current User-" + usr_Id + "and Order Pending At-" + pending);
        if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(usr_Id) == 0) {

            this.setApp_mode("E");

        } else {

            OperationBinding uNm = executeOperation("getUserName");
            uNm.getParamsMap().put("uid", pending);
            uNm.execute();
            if (uNm.getResult() != null) {

                // String msg2 =
                //  "<html><body> <p>This Sales Order is pending at other user<b><i> [ " + uNm.getResult().toString() +
                //   " ] </i></b>for approval, You can not edit this.</p></body></html>";
                String msg2 =
                    "<html><body> <p>" + resolvEl("#{bundle['MSG.824']}").toString() + "<b><i> [ " +
                    uNm.getResult().toString() + " ] </i></b>" + resolvEl("#{bundle['MSG.879']}").toString() +
                    "</p></body></html>";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            }

        }


    }

    /**Method to remove item wise scheme*/
    public void removeSchemeAction(ActionEvent actionEvent) {
        //executeOperation("removeScheme").execute(); //desabled on 19-june, for scheme issue
        OperationBinding binding = this.getBindings().getOperationBinding("getAndSetDiscountForItmFromPolicy");
        binding.execute();
        Object o = binding.execute();
        // System.out.println(" discount after scheme removal *************** == " + o);
        //   discValBind.setValue(o);
        // AdfFacesContext.getCurrentInstance().addPartialTarget(discValBind);
        /* Commented on 15-June-15, 3:20, for scheme issues.*/

    }

    /***Method to Save Sales Order as template*/
    public void saveAsTemplateAction(ActionEvent actionEvent) {
        showPopup(templateDescPopUpBind, true);
    }

    /***Method to delete Other Charges Lines*/
    public void deleteOtherChargesAction(ActionEvent actionEvent) {
        executeOperation("Delete4").execute();
        executeOperation("Execute4").execute();
    }


    public void selectAllSugstAction(ActionEvent actionEvent) {
        OperationBinding selectAll = executeOperation("selectAllCheckBoxSugst");
        selectAll.getParamsMap().put("val", true);
        selectAll.execute();
    }

    public void deSelectAllSgstAction(ActionEvent actionEvent) {
        OperationBinding selectAll = executeOperation("selectAllCheckBoxSugst");
        selectAll.getParamsMap().put("val", false);
        selectAll.execute();
    }

    /**Method to Save and Forward.
     * @return
     */
    public String saveAndForwardAction() {

        Boolean proc = false;
        Integer ordType = 1;
        if (ordTypeBind.getValue() != null) {
            ordType = (Integer) ordTypeBind.getValue();
            // System.out.println(ordType + " Type of Order =============");
        }
        String retVal = null;
        /* if (!isExiceValid()) {

        } else  */if (!advanceConditionMet()) {

        } else if (ordType.compareTo(312) == 0 && !getPoMode()) {
            //System.out.println(" insided save and forward for Purchase Order");
            String msg2 = "<html><body> <p>" + "Please Generate Purchase Order !" + "</p></body></html>";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        } else {
            String saveFlg = "N";
            if (ordTypeBind.getValue().toString().equalsIgnoreCase("481")) {
                saveFlg = callSave();
            } else {
                //ADFBeanUtils.findOperation("setRushOrderStatus").execute();
                saveFlg = saveButtonAction();

            }
            if (saveFlg.equalsIgnoreCase("Y")) {
                if (ordType.compareTo(311) == 0 || ordType.compareTo(418) == 0) {
                    //  System.out.println("inside to check rate contract deleiver auto+++++++++++++++++++++");
                    _log.fine("Delivery schedule generated.");
                } else {

                    //  System.out.println("inside to check rate contract deleiver auto==============================");
                    OperationBinding atDlv = ADFBeanUtils.findOperation("generateAutoDlvSchdl");
                    atDlv.execute();
                    Object chk = atDlv.getResult();
                    if (chk != null) {
                        proc = (Boolean) chk;
                    }
                    if (proc) {
                        saveFlg = "Y";
                    } else {

                    }
                }
            }

            if (saveFlg.equalsIgnoreCase("Y")) {
                //Method call to generate auto schedule, in case user doesn't make any schedule
                // System.out.println("outside to check rate contract deleiver auto---------------------");

                //Method call to generate auto Payment schedule, in case user doesn't make any schedule
                executeOperation("generateAutoPaymentSchdl").execute();

                _log.fine("Payment schedule generated.");
                //Method call to insert data in Sales Order Calculation table
                //executeOperation("orderCalculation").execute();
                //_log.fine("Order Calculations done.");
                // System.out.println("Save flag ! " + saveFlg);
                OperationBinding binding = this.getBindings().getOperationBinding("Commit");
                binding.execute();
                executeOperation("executeDlvVws").execute();
                if (!binding.getErrors().isEmpty()) {
                    FacesMessage message2 =
                        new FacesMessage("There have been an error in saving record. Please try again !");
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                }

                OperationBinding ob = ADFBeanUtils.getOperationBinding("getOrdrType");
                ob.execute();
                Object o = ob.getResult();
                Integer ordrTyp = (o == null ? -1 : (Integer) o);
                String vFlag;
                if (!ordrTyp.equals(481)) {
                    OperationBinding validateFlag = executeOperation("checkValidateSaveandForward");
                    validateFlag.execute();
                    vFlag = validateFlag.getResult().toString();
                } else {
                    vFlag = "Y";
                }
                if (vFlag != null) {
                    String flag = vFlag;
                    if (flag.equalsIgnoreCase("Y")) {
                        Integer pending = 0;
                        //Check Pending
                        OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
                        chkUsr.getParamsMap().put("SlocId", EbizParams.GLBL_APP_SERV_LOC());
                        chkUsr.getParamsMap().put("CldId", EbizParams.GLBL_APP_CLD_ID());
                        chkUsr.getParamsMap().put("OrgId", EbizParams.GLBL_APP_USR_ORG());
                        chkUsr.getParamsMap().put("DocNo", 21503);
                        chkUsr.execute();

                        if (chkUsr.getResult() != null) {
                            pending = Integer.parseInt(chkUsr.getResult().toString());
                        }
                        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                        WfnoOp.getParamsMap().put("SlocId", EbizParams.GLBL_APP_SERV_LOC());
                        WfnoOp.getParamsMap().put("CldId", EbizParams.GLBL_APP_CLD_ID());
                        WfnoOp.getParamsMap().put("OrgId", EbizParams.GLBL_APP_USR_ORG());
                        WfnoOp.getParamsMap().put("DocNo", 21503);
                        WfnoOp.execute();
                        if (WfnoOp.getResult() != null) {
                            WfNum = WfnoOp.getResult().toString();
                        }
                        OperationBinding usrLevelOp = getBindings().getOperationBinding("checkForWf");
                        usrLevelOp.getParamsMap().put("SlocId", EbizParams.GLBL_APP_SERV_LOC());
                        usrLevelOp.getParamsMap().put("CldId", EbizParams.GLBL_APP_CLD_ID());
                        usrLevelOp.getParamsMap().put("OrgId", EbizParams.GLBL_APP_USR_ORG());
                        usrLevelOp.getParamsMap().put("UsrId", EbizParams.GLBL_APP_USR());
                        usrLevelOp.getParamsMap().put("WfNo", WfNum);
                        usrLevelOp.getParamsMap().put("DocNo", 21503);
                        usrLevelOp.execute();
                        if (usrLevelOp.getResult() != null) {
                            level = Integer.parseInt(usrLevelOp.getResult().toString());
                        }

                        if (level.equals(-1)) {
                            /* String msg2 = "Either Workflow or Current User is not defined for the current Document";
                            FacesMessage message2 = new FacesMessage(msg2);
                            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message2); */
                        } else if (pending.compareTo(new Integer(-1)) == 0 ||
                                   pending.compareTo(EbizParams.GLBL_APP_USR()) == 0) {
                            retVal = "goToWf";
                        } else {
                            _log.info("Not in workflow--");
                            retVal = null;
                        }
                    } else if (flag.equalsIgnoreCase("D")) {
                        //  String msg2 =
                        // "<html><body> <p>Delivery Schedule is not completed for this Sales Order, Can not move</p></body></html>";
                        String msg2 =
                            "<html><body> <p>" + resolvEl("#{bundle['MSG.880']}").toString() + "</p></body></html>";

                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message2);
                    } else {
                        // String msg2 =
                        // "<html><body> <p>Payment Schedule is not completed for this Sales Order, Can not move</p></body></html>";
                        String msg2 =
                            "<html><body> <p>" + resolvEl("#{bundle['MSG.882']}").toString() + "</p></body></html>";
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message2);
                    }
                }
            }
        }

        _log.info("Return val : " + retVal);
        return retVal;
    }

    /**Method to Set FacetName- Lot
     * @param actionEvent
     */
    public void viewLotAction(ActionEvent actionEvent) {
        this.setFacetName("Lot");
        executeOperation("Execute6").execute();
    }

    /**Method to Set FacetName- Bin
     * @param actionEvent
     */
    public void viewBinAction(ActionEvent actionEvent) {
        this.setFacetName("Bin");
        executeOperation("Execute7").execute();
    }

    /**Method to Set FacetName- Serials
     * @param actionEvent
     */
    public void viewSerialsAction(ActionEvent actionEvent) {
        this.setFacetName("SrNo");
        executeOperation("Execute8").execute();
    }


    /**To Open PopUp to Select Lot for Item(Issue)
     * @param actionEvent
     */
    public void selectLotFrItmAction(ActionEvent actionEvent) {
        OperationBinding ob = ADFBeanUtils.findOperation("setBindValuesLot");
        ob.getParamsMap().put("itmId", actionEvent.getComponent().getAttributes().get("ItemId"));
        ob.execute();
        executeOperation("filterLotWhWise").execute();

        showPopup(selectLotPopUpBind, true);
    }

    /**To Open PopUp to Select Lot-Bin for Item(Issue)
     * @param actionEvent
     */
    public void selectLotBinFrItmAction(ActionEvent actionEvent) {
        OperationBinding ob = ADFBeanUtils.findOperation("setBindValuesBin");
        ob.getParamsMap().put("itmId", actionEvent.getComponent().getAttributes().get("ItemId"));
        ob.execute();
        executeOperation("filterBinWhWise").execute();
        showPopup(selectLotBinPopUpBind, true);
    }

    /**To Open PopUp to Select Lot-Bin-SrNo for Item(Issue)
     * @param actionEvent
     */
    public void selectSrNoFrItmAction(ActionEvent actionEvent) {
        OperationBinding ob = ADFBeanUtils.findOperation("setBindValuesSr");
        ob.getParamsMap().put("itmId", actionEvent.getComponent().getAttributes().get("ItemId"));
        ob.execute();
        executeOperation("filterSrNoAsPerItem").execute();
        //executeOperation("filterSrNoAsPerItem").execute();
        showPopup(selectSrNoPopUpBind, true);
    }

    /***Method to expand all tree table nodes*/


    private void expandTreeTable() {

        if (this.treeTable != null) {
            disclosedTreeRowKeySet = new RowKeySetImpl();
            CollectionModel model = (CollectionModel) treeTable.getValue();
            JUCtrlHierBinding treeBinding = (JUCtrlHierBinding) model.getWrappedData();
            JUCtrlHierNodeBinding rootNode = treeBinding.getRootNodeBinding();
            disclosedTreeRowKeySet = treeTable.getDisclosedRowKeys();
            if (disclosedTreeRowKeySet == null) {
                disclosedTreeRowKeySet = new RowKeySetImpl();
            }
            List<JUCtrlHierNodeBinding> firstLevelChildren = rootNode.getChildren();
            for (JUCtrlHierNodeBinding node : firstLevelChildren) {
                ArrayList list = new ArrayList();
                list.add(node.getRowKey());
                disclosedTreeRowKeySet.add(list);
                expandTreeChildrenNode(treeTable, node, list);
            }
            treeTable.setDisclosedRowKeys(disclosedTreeRowKeySet);
        }
    }

    private void expandTreeChildrenNode(RichTreeTable rt, JUCtrlHierNodeBinding node, List<Key> parentRowKey) {
        ArrayList children = node.getChildren();
        List<Key> rowKey;
        if (children != null) {
            for (int i = 0; i < children.size(); i++) {
                rowKey = new ArrayList<Key>();
                rowKey.addAll(parentRowKey);
                rowKey.add(((JUCtrlHierNodeBinding) children.get(i)).getRowKey());
                disclosedTreeRowKeySet.add(rowKey);
                if (((JUCtrlHierNodeBinding) (children.get(i))).getChildren() == null)
                    continue;
                expandTreeChildrenNode(rt, (JUCtrlHierNodeBinding) (node.getChildren().get(i)), rowKey);
            }
        }
    }


    /**Method auto Issue Item in  case of Rush & Cash-Sale Order
     * @param actionEvent
     */
    public void autoIssueItemAction(ActionEvent actionEvent) {
        executeOperation("issueAutoItem").execute();

    }

    /**Method to amend Sales Order
     * @param actionEvent
     */
    public void amendOrderAction(ActionEvent actionEvent) {
        Integer pending = 0;
        if (true) {

            OperationBinding res = ADFBeanUtils.findOperation("chkPaymentAgaisntIR");
            res.execute();
            if (res.getResult() != null) {
                if ("Y".equalsIgnoreCase(res.getResult().toString())) {
                    ADFBeanUtils.showPopup(amendOrdrConfrmPopUpBind, true);
                }
            }

            ADFBeanUtils.getOperationBinding("changesSoStatusNdModeOnAmendMent").execute();
            this.setApp_mode("E");
            this.amdFlg = "Y";
            OperationBinding ob = executeOperation("getCurrentAmdNo");
            ob.execute();
            // System.out.println("Current Amendment no is : " + ob.getResult());
            if (ob.getResult() != null) {
                amd_No = Integer.parseInt(ob.getResult().toString());
            }

            _log.info("Amendment Method called");
            OperationBinding setAmd = executeOperation("setAmndmntDtl");
            setAmd.getParamsMap().put("amdNo", amd_No + 1);
            setAmd.execute();
        } else {

            OperationBinding uNm = executeOperation("getUserName");
            uNm.getParamsMap().put("uid", pending);
            uNm.execute();
            if (uNm.getResult() != null) {

                /*  String msg2 =
                    "<html><body> <p>This Sales Order is pending at other user<b><i> [ " + uNm.getResult().toString() +
                    " ] </i></b>for approval, You can not amend this.</p></body></html>";*/
                String msg2 =
                    "<html><body> <p>" + resolvEl("#{bundle['MSG.824']}") + "<b><i> [ " + uNm.getResult().toString() +
                    " ] </i></b>" + resolvEl("#{bundle['MSG.825']}") + "</p></body></html>";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            }
        }
    }

    /**************
 * Validators defined for Sales Order Add/Edit/View pages
 *
 * ******************/

    /**
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void pmtDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        //System.out.println("Inside bean validator");
        if (object != null) {
            Timestamp date = (Timestamp) object;
            OperationBinding ob = executeOperation("paymentDateValidation");
            ob.getParamsMap().put("paydt", date);
            ob.execute();
            if (ob.getResult() != null) {
                String flg = (String) ob.getResult();
                _log.info("Value of Flag is-->" + flg);
                if ("Y".equalsIgnoreCase(flg)) {
                    String msg2 = " Duplicate payment Date for same mode is not allowed!";
                    // throw new ValidatorException(new FacesMessage("Duplicate Payment Date"));
                    //throw new ValidatorException(new FacesMessage(resolvEl("#{bundle['addOtherCharges4']}").toString()));
                    throw new ValidatorException(new FacesMessage(msg2));
                }
            }
        }
    }

    /**Discount Validator for Order
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void discSoAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        OperationBinding binding = this.getBindings().getOperationBinding("getSalesOrderType");
        binding.execute();
        binding.getResult();
        Number n = (Number) object;
        Number zero = new Number(0);
        Number hund = new Number(100);
        Number val = (Number) object;
        String discTyp = discTypSobind.getValue().toString();
        if (binding.getResult() != null) {
            if ((Integer) binding.getResult() != 311) {
                if (discTyp.equalsIgnoreCase("P")) {
                    if (val.compareTo(hund) != -1) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvEl("#{bundle['MSG.455']}"), null));

                    } else if (val.compareTo(zero) == -1) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvEl("#{bundle['MSG.456']}"), null));
                    }
                } else if ("A".equalsIgnoreCase(discTyp)) {
                    OperationBinding checkVal = executeOperation("checkOrdAmtDisc");
                    checkVal.getParamsMap().put("discVal", val);
                    checkVal.execute();
                    if (checkVal.getResult() != null) {
                        String flag = checkVal.getResult().toString();
                        _log.info("Flaf from Am-" + flag);
                        if ("Y".equalsIgnoreCase(flag)) {
                            //  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            //        "Discount must be less than Order total cost",null));

                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          resolvEl("#{bundle['MSG.883']}"), null));
                        }
                    }
                }
            } else if (n.compareTo(zero) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.305']}"), null));
            }
        }
        if (n.compareTo(zero) == -1) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.305']}"),
                                                          null));
        }
        if (discTyp.equalsIgnoreCase("P")) {
            if (val.compareTo(hund) != -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Discount Percentage must be less than hundred.", null));
            }
            if (val.compareTo(zero) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.456']}"), null));
            }
        }

    }

    /**
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void paymntAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number pmtAmt = (Number) object;
            OperationBinding pmtVal = executeOperation("paymntAmtValidator");
            pmtVal.getParamsMap().put("pmtAmt", pmtAmt);
            pmtVal.execute();
            _log.info("Payment Amt Vali flg---" + pmtVal.getResult());
            if ("Y".equalsIgnoreCase(pmtVal.getResult().toString())) {
                // String msg2 = "Payment Amount Exceeding remainig Order Amount";
                String msg2 = resolvEl("#{bundle['MSG.884']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                // message2.setDetail("Enter a valid amount");
                message2.setDetail(resolvEl("#{bundle['MSG.886']}").toString());
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
        }
    }

    /**Discount Validator for Item
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void itmDiscValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (validationsEnabled) {
            if (object != null) {
                //  System.out.println(" ================================================ Begain discount validator");
                OperationBinding binding = this.getBindings().getOperationBinding("getSalesOrderType");
                binding.execute();
                binding.getResult();
                Number n = (Number) object;
                Number zero = new Number(0);
                Number hundrd = new Number(100);
                String discType = "";
                if (discTypItmBind.getValue() != null) {
                    discType = discTypItmBind.getValue().toString();
                }
                if (binding.getResult() != null) {
                    if ((Integer) binding.getResult() != 311) {
                        OperationBinding operationBinding =
                            this.getBindings().getOperationBinding("getTotPriceQtyAmount");
                        operationBinding.execute();

                        if (n.compareTo(zero) == 1) {
                            Number total = (Number) operationBinding.getResult();
                            if (discType.equals("A") &&
                                (n.compareTo(total) == 1 ||
                                 n.compareTo(total) ==
      0)) {
                                //System.out.println("Discount validation failed,");
                                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                              resolvEl("#{bundle['MSG.262']}") + " " +
                                                                              total, null));
                            }

                            if (discType.equals("P") && (n.compareTo(hundrd) == 1 || n.compareTo(hundrd) == 0)) {
                                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                              resolvEl("#{bundle['MSG.303']}"), null));
                            }

                        } else if (n.compareTo(zero) == -1) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          resolvEl("#{bundle['MSG.305']}"), null));
                        }
                    } else if (n.compareTo(zero) == -1) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvEl("#{bundle['MSG.305']}"), null));
                    }

                }
                if (n.compareTo(zero) == -1) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvEl("#{bundle['MSG.305']}"), null));
                }
                if (discType.equals("P") && (n.compareTo(hundrd) == 1 || n.compareTo(hundrd) == 0)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvEl("#{bundle['MSG.303']}"), null));
                }

                //   System.out.println(" ================================================ End discount validator");

                /**Commenting on 18-jun-2015, since on scheme item discount validation is not required

                OperationBinding operationBinding = this.getBindings().getOperationBinding("ItemExistInSchmOrNot");
                operationBinding.execute();
                Object res = operationBinding.getResult();
                if (res != null && (Boolean) res && n.compareTo(zero) == 1) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Item already exist in Scheme table. You cannot give any discount for this item",
                                                                  null));
                }
                */
            }
        }

    }

    /**Method to check invalid precision*/
    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    /**Item Quantity Validator
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void itmQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Integer orderType = Integer.parseInt((ordTypeBind.getValue().toString()));
        if (object != null && itmPriceBind.getValue() != null && orderType != 311 &&
            currConvRateBind.getValue() != null) {
            Number price = (Number) itmPriceBind.getValue();
            if (!ADFBeanUtils.isPrecisionValid((Number) object)) {
                System.out.println("In Me");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.57']}"), null));

            } else if (price.compareTo(0) == 1) {
                Number convRate = (Number) currConvRateBind.getValue();
                Number qty = (Number) object;
                Number tot = price.multiply(qty).multiply(convRate);
                tot = (Number) tot.round(Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}").toString()));
                _log.info("Conv : " + convRate + "Total is : " + tot);

                Boolean totFlag = isPrecisionValid(26, 6, tot);
                if (totFlag.equals(false)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvEl("#{bundle['MSG.405']}"), null));
                }

                if (qty.compareTo(0) == 0 ||
                    qty.compareTo(0) ==
                                         -1) {
                    // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Quantity",
                    //      "Quantity must be greater than zero(0)"));

                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvEl("#{bundle['MSG.336']}"),
                                                                  resolvEl("#{bundle['MSG.892']}")));

                } else {
                    OperationBinding binding = ADFBeanUtils.getOperationBinding("getUomConvFactorForCurrItm");
                    if (binding != null) {
                        binding.execute();
                        Object o = binding.getResult();
                        Number o1 = (o == null ? new Number(-1) : (Number) o);
                        if (o1.compareTo(new Number(-1)) == 0) {
                            throw new ValidatorException(new FacesMessage("UOM Converison factor is not defined for current Item",
                                                                          "Please define proper UOM conversion for the current Item !"));
                        } else if (o1.compareTo(new Number(-2)) == 0) {
                            throw new ValidatorException(new FacesMessage("UOM is not defined for current Item",
                                                                          "Please define proper UOM for the current Item !"));
                        }
                    }
                }
            }
        }
    }

    /**Item Name Validator for Duplicate Record
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void itmNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        RichInputComboboxListOfValues comp = (RichInputComboboxListOfValues) uIComponent;
        if (!comp.isValid()) {
            //String msg2 = resolvEl("#{bundle['MSG.427']}");
            FacesMessage message2 = new FacesMessage("Invalid Item", "Please select a valid Item !");
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        } else if (object != null) {
            _log.info("Value of Item is-->" + object);
            OperationBinding ob = executeOperation("itmIdValidator");
            ob.getParamsMap().put("itmName", object.toString());
            ob.execute();
            if (ob.getResult() != null) {
                String flg = ob.getResult().toString();
                if ("Y".equalsIgnoreCase(flg)) {
                    String msg2 = resolvEl("#{bundle['MSG.427']}");
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                } else if ("F".equalsIgnoreCase(flg)) {
                    throw new ValidatorException(new FacesMessage("Selected Item is already a free Item !",
                                                                  "Selected Item is a free Item in one of the Scheme selected for Item in Sales Order. You cannot select a Free Item in Sale Order !"));
                }
            }
        }
    }


    /**Schedule Quantity Validator for Delivery Schedule
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void schdlQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (ordTypeBind.getValue().equals(311)) {

        } else {
            Number qty = new Number(0);
            if (object != null) {
                qty = (Number) object;
            }
            Number blnqty = (Number) blncQtyBind.getValue();
            // System.out.println("Value from binding is-->" + blnqty + "and currect scdl qty is---->" + qty);
            Number zero = new Number(0);

            if (qty.compareTo(zero) == 0) {
                String msg2 = resolvEl("#{bundle['MSG.337']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            } else if (blnqty.compareTo(qty) == -1) {
                String msg2 = resolvEl("#{bundle['MSG.417']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            } else if (qty.compareTo(0) == -1) {

                // FacesMessage message2 = new FacesMessage("Invalid Quantity", "Can not be negative");
                FacesMessage message2 =
                    new FacesMessage(resolvEl("#{bundle['MSG.336']}"), resolvEl("#{bundle['MSG.894']}"));
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
        }
    }

    /**Other Charges Amount Validator
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void ocAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number ocamt = new Number(0);

        if (object != null) {
            ocamt = (Number) object;
            Boolean totFlag = isPrecisionValid(26, 6, ocamt);
            if (totFlag.equals((Object) false)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision", null));
            } else if (ocamt.compareTo(0) ==
                       -1) {
                // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                //   "Amount must be greater thab zero(0)", null));

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.896']}"), null));
            }

        }
    }

    /**Validator for Order Date (FY Check)
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void orderDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /* if (object != null) {
            _log.info("Date Value in Validator---" + object);
            String flg = "N";
            Timestamp orderDate = (Timestamp) object;
            OperationBinding orddt = executeOperation("orderDtValidate");
            orddt.getParamsMap().put("ordDate", orderDate);
            orddt.execute();
            if (orddt.getResult() != null) {
                flg = orddt.getResult().toString();
            }
            if (flg.equalsIgnoreCase("Y")) {
                FacesMessage errMsg = new FacesMessage(resolvEl("#{bundle['MSG.521']}").toString());
                errMsg.setDetail(resolvEl("#{bundle['MSG.519']}").toString());
                throw new ValidatorException(errMsg);
            }
        } */



    }


    /**Validator for Item Price
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void itmPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && itmQtyBind.getValue() != null && currConvRateBind.getValue() != null) {
            Number price = (Number) object;

            Number convRate = (Number) currConvRateBind.getValue();
            Number qty = (Number) itmQtyBind.getValue();
            Number tot = price.multiply(qty).multiply(convRate);
            tot = (Number) tot.round(EbizParams.GLBL_AMT_DIGIT());

            Boolean totFlag = isPrecisionValid(26, 6, tot);
            if (totFlag.equals(false)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.405']}"), null));
            }

            if (price.compareTo(0) == 0 ||
                price.compareTo(0) ==
                                 -1) {
                //   throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Price",
                //          "Price must be greater than zero(0)"));

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.334']}").toString(),
                                                              resolvEl("#{bundle['MSG.898']}").toString()));
            }
        }

    }


    /***Method to check manadatory field of header*/
    public String checkMandateField() {
        String retVal = "N";
        OperationBinding binding = ADFBeanUtils.getOperationBinding("validateMandatoryFieldsBeforeAddingItems");
        binding.execute();
        Object object = binding.getResult();
        Integer i = (object == null ? -1 : (Integer) object);
        /* 0 : All enteries are valid.
             * 1 : Sales Order Date is not Defined
             * 2 : Sales Order Type is not Defined
             * 3 : Customer is not defined
             * 4 : Currency is not defined
             * 5 : Currency Conversion factor is zero or less than zero.
             * 6 : Shipment Address in not selected.
             * 7 : Quotation is not selected.
             * 8 : Template No not selected.
             * 9 : Previous Sales order to Copy from is not selected.
             * 10 : Warehouse is not selected.
             * 11 : Price Policy not selected.
             * 12 : Validity date is not selected for Open Contract and Rate Contract*/
        if (i.equals(0)) {
            retVal = "Y";
        } else if (i.equals(1)) {
            ADFModelUtils.showFormattedFacesMessage("Sales Order Date is not Defined !",
                                                    "Please define Date for current Sales Order.",
                                                    FacesMessage.SEVERITY_WARN);
        } else if (i.equals(2)) {
            //  FacesMessage reqMsg = new FacesMessage("Order Type is Manadatory");
            FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.921']}"));
            // reqMsg.setDetail("You must enter a value");
            reqMsg.setDetail(resolvEl("#{bundle['MSG.117']}"));
            reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(ordTypeBind.getClientId(), reqMsg);
        } else if (i.equals(3)) {
            // FacesMessage reqMsg = new FacesMessage("Customer Name is Manadatory");
            FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.919']}"));
            // reqMsg.setDetail("You must enter a value");
            reqMsg.setDetail(resolvEl("#{bundle['MSG.117']}"));
            reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(eoNmTransBind.getClientId(), reqMsg);
        } else if (i.equals(4)) {
            //  FacesMessage reqMsg = new FacesMessage("Specific Currency is Manadatory");
            FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.918']}"));
            // reqMsg.setDetail("You must enter a value");
            reqMsg.setDetail(resolvEl("#{bundle['MSG.117']}"));
            reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(currIdSpBind.getClientId(), reqMsg);
        } else if (i.equals(5)) {
            ADFModelUtils.showFormattedFacesMessage("Conversion Rate is invalid !",
                                                    "Conversion Rate should be greater than zero. <br />Please select the proper Currency with proper Conversion Rate.",
                                                    FacesMessage.SEVERITY_WARN);
        } else if (i.equals(6)) {
            ADFModelUtils.showFormattedFacesMessage("Address have not been selected !",
                                                    "Please select a Shipping Address for Current Sales Order.<br /><br/> By default the Default Shipping Address of Customer is Selected in Sales Order. Seems the default Shipping Address for the Current Customer is not defined in Customer Profile.<br/><br>It is advised to define a Default Shipping Address in Customer Profile.",
                                                    FacesMessage.SEVERITY_WARN);
        } else if (i.equals(7)) {
            ADFModelUtils.showFormattedFacesMessage("Sales Quotation has not been selected !",
                                                    "Sales Order Basis is Quotation.<br /><br/> So, please select the Quotation so that Sales Order can be made on its basis.",
                                                    FacesMessage.SEVERITY_WARN);
        } else if (i.equals(8)) {
            ADFModelUtils.showFormattedFacesMessage("Sales Order Template has not been selected !",
                                                    "Sales Order Basis is Template.<br /><br/> So, please select the Template so that Sales Order can be made on its basis.",
                                                    FacesMessage.SEVERITY_WARN);
        } else if (i.equals(9)) {
            ADFModelUtils.showFormattedFacesMessage("Pervious Sales Order has not been selected !",
                                                    "Sales Order Basis is Pervious Sales Order.<br /><br/> So, please select the Pervious Sales Order so that Sales Order can be made on its basis.",
                                                    FacesMessage.SEVERITY_WARN);
        } else if (i.equals(10)) {
            // FacesMessage reqMsg = new FacesMessage("Warehouse is Mandatory");
            FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.909']}"));
            // reqMsg.setDetail("You must enter a value, As you have selected Cash-Sale or Rush Order");
            reqMsg.setDetail(resolvEl("#{bundle['MSG.911']}"));
            reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(whIdSoBind.getClientId(), reqMsg);
        } else if (i.equals(11)) {
            ADFModelUtils.showFormattedFacesMessage("Price Policy has not been selected !",
                                                    "Customer Price Policy is not selected for the Current Customer.<br /><br/> The Current organisation uses Customer Price Policy, Customer Price Policy Selection is Mandatory.",
                                                    FacesMessage.SEVERITY_WARN);
        } else if (i.equals(12)) {
            // FacesMessage reqMsg = new FacesMessage("Validity Date is Mandatory");
            FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.912']}"));
            //reqMsg.setDetail("You must enter a value, As you have selected Rate-Contract Order");
            reqMsg.setDetail(resolvEl("#{bundle['MSG.913']}"));
            reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(validUpToDtBind.getClientId(), reqMsg);

        } else {
            ADFModelUtils.showFormattedFacesMessage("There have been some problem while Validating Sales Order Details !",
                                                    "Please try again.<br /><br/> If the problem persists please contact Ess!",
                                                    FacesMessage.SEVERITY_WARN);
        }


        /*  if (ordTypeBind.getValue() != null) {
            if (getEoNmTransBind().getValue() != null) {
                if (currIdSpBind.getValue() != null) {
                    if (ordDateBind.getValue() != null) {
                        if (soBasisBind.getValue() != null) {
                            if ((Integer.parseInt(ordTypeBind.getValue().toString()) == 310) ||
                                (Integer.parseInt(ordTypeBind.getValue().toString()) != 310 &&
                                 dlvAddsBind.getValue() != null && !("".equals(dlvAddsBind.getValue())))) {
                                System.out.println("value of address in valid.....");
                                _log.info("Address valid.");
                                if (soBasisBind.getValue().equals(391)) {
                                    retVal = "Y";
                                }
                                Object quotId = null;
                                OperationBinding binding = this.getBindings().getOperationBinding("isQuotIdNull");
                                binding.execute();
                                if (binding.getResult() != null) {
                                    quotId = binding.getResult();
                                }
                                if (soBasisBind.getValue().equals(390) && !(Boolean) quotId) {
                                    retVal = "Y";
                                } else {
                                    //FacesMessage reqMsg = new FacesMessage("Quotation is Manadatory Field");
                                    FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.899']}").toString());
                                    // reqMsg.setDetail("You must enter a value, As you have selected Quotation in SO Basis");
                                    reqMsg.setDetail(resolvEl("#{bundle['MSG.901']}"));
                                    reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext.getCurrentInstance().addMessage(quotDispIdBind.getClientId(), reqMsg);
                                }
                                if (soBasisBind.getValue().equals(388) && templateIdBind.getValue() != null) {
                                    retVal = "Y";
                                } else {
                                    //FacesMessage reqMsg = new FacesMessage("Template is Manadatory Field");
                                    FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.903']}"));
                                    // reqMsg.setDetail("You must enter a value, As you have selected Template in SO Basis");
                                    reqMsg.setDetail(resolvEl("#{bundle['MSG.904']}"));
                                    reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext.getCurrentInstance().addMessage(templateIdBind.getClientId(), reqMsg);
                                }
                                if (soBasisBind.getValue().equals(389) && refSoDocIdBind.getValue() != null) {
                                    retVal = "Y";
                                } else {
                                    //FacesMessage reqMsg = new FacesMessage("Previous Order is Manadatory Field");
                                    FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.906']}"));
                                    //reqMsg.setDetail("You must enter a value, As you have selected Previous Order in SO Basis");
                                    reqMsg.setDetail(resolvEl("#{bundle['MSG.908']}"));
                                    reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext.getCurrentInstance().addMessage(refSoDocIdBind.getClientId(), reqMsg);
                                }
                                System.out.println("Warehouse-" + whIdSoBind.getValue());
                                if (resolvEl("#{pageFlowScope.GLBL_ORG_WH_CHK}") != null) {
                                    isWhUsed = resolvEl("#{pageFlowScope.GLBL_ORG_WH_CHK}");
                                }
                                if ("N".equalsIgnoreCase(isWhUsed)) {
                                    retVal = "Y";
                                } else {
                                    if (whIdSoBind.getValue() != null) {
                                        _log.info("Inside if");
                                        retVal = "Y";
                                    } else {
                                        _log.info("Inside else Order Type is-" + ordTypeBind.getValue());
                                        if (Integer.parseInt(ordTypeBind.getValue().toString()) == 309 ||
                                            Integer.parseInt(ordTypeBind.getValue().toString()) == 310) {
                                            // FacesMessage reqMsg = new FacesMessage("Warehouse is Mandatory");
                                            FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.909']}"));
                                            // reqMsg.setDetail("You must enter a value, As you have selected Cash-Sale or Rush Order");
                                            reqMsg.setDetail(resolvEl("#{bundle['MSG.911']}"));
                                            reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                            FacesContext.getCurrentInstance().addMessage(whIdSoBind.getClientId(),
                                                                                         reqMsg);
                                            retVal = "N";
                                        } else {
                                            retVal = "Y";
                                        }
                                    }
                                }
                                System.out.println("Valid Up to is-" + validUpToDtBind.getValue());
                                if (validUpToDtBind.getValue() != null) {

                                } else if (Integer.parseInt(ordTypeBind.getValue().toString()) == 311) {
                                    // FacesMessage reqMsg = new FacesMessage("Validity Date is Mandatory");
                                    FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.912']}"));
                                    //reqMsg.setDetail("You must enter a value, As you have selected Rate-Contract Order");
                                    reqMsg.setDetail(resolvEl("#{bundle['MSG.913']}"));
                                    reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext.getCurrentInstance().addMessage(validUpToDtBind.getClientId(), reqMsg);
                                    retVal = "N";
                                }

                            } else {
                                FacesMessage reqMsg = new FacesMessage("Delivery Address is required.");
                                //reqMsg.setDetail("You must enter a value, As you have selected Previous Order in SO Basis");
                                reqMsg.setDetail("Please select a Delivery Address !");
                                reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext.getCurrentInstance().addMessage(null, reqMsg);
                            }
                        } else {
                            // FacesMessage reqMsg = new FacesMessage("SO Basis is Manadatory");
                            FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.914']}"));
                            // reqMsg.setDetail("You must enter a value");
                            reqMsg.setDetail(resolvEl("#{bundle['MSG.117']}"));
                            reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext.getCurrentInstance().addMessage(soBasisBind.getClientId(), reqMsg);
                        }

                    } else {
                        //   FacesMessage reqMsg = new FacesMessage("Order Date is Manadatory");
                        FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.917']}"));
                        // reqMsg.setDetail("You must enter a value");
                        reqMsg.setDetail(resolvEl("#{bundle['MSG.117']}"));
                        reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(ordDateBind.getClientId(), reqMsg);
                    }
                } else {
                    //  FacesMessage reqMsg = new FacesMessage("Specific Currency is Manadatory");
                    FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.918']}"));
                    // reqMsg.setDetail("You must enter a value");
                    reqMsg.setDetail(resolvEl("#{bundle['MSG.117']}"));
                    reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(currIdSpBind.getClientId(), reqMsg);
                }

            } else {
                // FacesMessage reqMsg = new FacesMessage("Customer Name is Manadatory");
                FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.919']}"));
                // reqMsg.setDetail("You must enter a value");
                reqMsg.setDetail(resolvEl("#{bundle['MSG.117']}"));
                reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(eoNmTransBind.getClientId(), reqMsg);

            }
        } else {
            //  FacesMessage reqMsg = new FacesMessage("Order Type is Manadatory");
            FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.921']}"));
            // reqMsg.setDetail("You must enter a value");
            reqMsg.setDetail(resolvEl("#{bundle['MSG.117']}"));
            reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(ordTypeBind.getClientId(), reqMsg);
        } */

        return retVal;
    }

    /**Tolerance Quantity Validator
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void tlrncQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number qty = new Number(0);
            Number tlrncQty = (Number) object;
            String tlrncTyp = "A";
            tlrncQtyTypBind.processUpdates(FacesContext.getCurrentInstance());
            if (tlrncQtyTypBind.getValue() != null) {
                tlrncTyp = tlrncQtyTypBind.getValue().toString();
                _log.info("Tolerance type is :" + tlrncTyp);
            }
            if (itmQtyBind.getValue() != null) {
                qty = (Number) itmQtyBind.getValue();
            }
            OperationBinding binding = this.getBindings().getOperationBinding("getSalesOrderType");
            binding.execute();
            if (((Integer) binding.getResult()).equals(311)) {
                tlrncQty = new Number(0);
                _log.info("Tolerance Quantity is when 311 A :" + tlrncQty);
            }
            if ((((Integer) binding.getResult()).equals(311)) && ("P".equalsIgnoreCase(tlrncTyp))) {
                tlrncQty = (Number) object;
                _log.info("Tolerance Quantity is is when 311 P:" + tlrncQty);
            }

            if ("A".equalsIgnoreCase(tlrncTyp) &&
                (tlrncQty.compareTo(qty) == 1 ||
                 tlrncQty.compareTo(0) ==
                                   -1)) {
                // throw new ValidatorException(new FacesMessage("Invalid Tolerance Quantity",
                //            "Must be less than Item Quantity and a positive value"));

                throw new ValidatorException(new FacesMessage(resolvEl("#{bundle['MSG.922']}"),
                                                              resolvEl("#{bundle['MSG.923']}")));

            } else if ("P".equalsIgnoreCase(tlrncTyp) && tlrncQty.compareTo(100) == 1) {

                // throw new ValidatorException(new FacesMessage("Invalid Tolerance Quantity (Percent)",
                //            "Must be less than 100"));
                throw new ValidatorException(new FacesMessage(resolvEl("#{bundle['MSG.924']}"),
                                                              resolvEl("#{bundle['MSG.925']}")));

            } else if (tlrncQty.compareTo(0) ==
                       -1) {
                // throw new ValidatorException(new FacesMessage("Invalid Tolerance Quantity",
                //                "Must be a positive value"));

                throw new ValidatorException(new FacesMessage(resolvEl("#{bundle['MSG.922']}"),
                                                              resolvEl("#{bundle['MSG.926']}")));
            }
        }
    }

    /**Validate Tolerance Days (Must be positive)
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void tlrncDaysValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer tlrncDays = (Integer) object;
            if (tlrncDays <
                0) {
                //  throw new ValidatorException(new FacesMessage("Invalid Tolerance Days", "Must be a positive value"));
                throw new ValidatorException(new FacesMessage(resolvEl("#{bundle['MSG.927']}"),
                                                              resolvEl("#{bundle['MSG.926']}")));
            }
        }
    }

    /**Method to validate freight charge
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void freightChargeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String typ = "A";
            if (frieghtVChargesBind.getValue() != null) {
                //System.out.print("        ___"+frieghtVChargesBind.getValue());
                typ = (String) frieghtVChargesBind.getValue();
            }
            Number amt = (Number) object;
            if (amt.compareTo(0) ==
                -1) {
                //  throw new ValidatorException(new FacesMessage("Invalid Value", "Charges must be positive"));
                throw new ValidatorException(new FacesMessage(resolvEl("#{bundle['MSG.253']}"),
                                                              resolvEl("#{bundle['MSG.928']}")));
            } else if (!isPrecisionValid(26, 6, amt)) {
                throw new ValidatorException(new FacesMessage("Invalid Precision."));
            } else if (!typ.equalsIgnoreCase("A")) {
                if (amt.compareTo(new Number(100)) == 1) {
                    throw new ValidatorException(new FacesMessage("Invalid Percentage."));
                }
            }
        }
    }

    /**Method to validate customer name in case of cash -sale order
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void custNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String msg2 = "";
        if (object != null) {
            String custNm = object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = custNm.toCharArray();
            for (char c : xx) {
                if (c == '(') {
                    openB = openB + 1;
                } else if (c == ')') {
                    closeB = closeB + 1;
                }

                if (closeB > openB) {
                    closeFg = true; //closed brackets will not be more than open brackets at any given time.
                }
            }


            //if openB=0 then no. of closing and opening brackets equal || opening bracket must always come before closing brackets
            //closing brackets must not come before first occurrence of openning bracket
            if (openB != closeB || closeFg == true || (custNm.lastIndexOf("(") > custNm.lastIndexOf(")")) ||
                (custNm.indexOf(")") < custNm.indexOf("("))) {
                // msg2 = "Brackets not closed properly.";
                msg2 = resolvEl("#{bundle['MSG.7']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (custNm.contains("()")) {
                //  msg2 = "Empty Brackets are not allowed.";
                msg2 = resolvEl("#{bundle['MSG.170']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (custNm.contains("(.") || custNm.contains("(-") || custNm.contains("-)")) {
                //  msg2 = "Invalid Customer name.Check content inside brackets.";
                msg2 = resolvEl("#{bundle['MSG.930']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (custNm.startsWith(" ") || custNm.endsWith(" ")) {
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                // throw new ValidatorException(new FacesMessage("Invalid Customer name",
                //              "Can not start and ends with space"));
                throw new ValidatorException(new FacesMessage(resolvEl("#{bundle['MSG.933']}"),
                                                              resolvEl("#{bundle['MSG.934']}")));

            }

            if (custNm.contains("  ")) {
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                // throw new ValidatorException(new FacesMessage("Invalid Customer name",
                //  "Can not contain more than One Space b/w 2 characters"));
                throw new ValidatorException(new FacesMessage(resolvEl("#{bundle['MSG.933']}"),
                                                              resolvEl("#{bundle['MSG.936']}")));
            }

            openB = 0;
            closeB = 0;
            closeFg = false;


            //check for valid language name.Allowed- brackets,dots,hyphen
            String expression = "^(?:(?>[A-Za-z \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))?)*$";
            CharSequence inputStr = custNm;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            // String error = "Invalid Customer Name";
            String error = resolvEl("#{bundle['MSG.933']}");

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }

        }
    }

    /**Method To Validate Delivery Date ,it must not be greater than Validity Date
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void deliveryDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && ordTypeBind.getValue().equals(311)) {
            _log.info("Inside Validator-");
            if (validUpToDtBind.getValue() != null) {
                // Timestamp validDt = (Timestamp) validUpToDtBind.getValue();
                Timestamp validDt = StaticValue.getTruncatedDt((Timestamp) validUpToDtBind.getValue());
                Timestamp dlvDt = (Timestamp) object;
                _log.info("Dlv Dt is-" + dlvDt + "vallid up to-" + validDt);
                //System.out.println("Dlv Dt is-" + dlvDt + "vallid up to-" + validDt);
                if (dlvDt.compareTo(validDt) == 1) {
                    // throw new ValidatorException(new FacesMessage("Delivery Date must be less than Validity Date"));
                    throw new ValidatorException(new FacesMessage(resolvEl("#{bundle['MSG.938']}")));
                }
            }
        }
    }

    /**
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void qtyPoPageValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number qty = (Number) object;
            if (qty.compareTo(0) <=
                0) {
                //  throw new ValidatorException(new FacesMessage("Invalid Quantity","Must be greater than zero"));
                throw new ValidatorException(new FacesMessage(resolvEl("#{bundle['MSG.336']}"),
                                                              resolvEl("#{bundle['MSG.514']}")));
            }
        }
    }

    /**Method to validate Issue Quantity from Lot
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void issueQtyLotValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number issueQty = (Number) object;
            Number lotQty = new Number(0);
            if (totStkLotBind.getValue() != null) {
                lotQty = (Number) totStkLotBind.getValue();
                _log.info("Issue qty-" + issueQty + "lot qty-" + lotQty);
                if (issueQty.compareTo(lotQty) ==
                    1) {
                    //throw new ValidatorException(new FacesMessage("Invalid Quantity","Issue Quantity can not be more than available quantity"));
                    throw new ValidatorException(new FacesMessage(resolvEl("#{bundle['MSG.336']}"),
                                                                  resolvEl("#{bundle['MSG.1066']}")));
                }
            }
            if (issueQty.compareTo(0) ==
                -1) {
                //throw new ValidatorException(new FacesMessage("Invalid Quantity","Issue Quantity can not be negative"));
                throw new ValidatorException(new FacesMessage(resolvEl("#{bundle['MSG.336']}"),
                                                              resolvEl("#{bundle['MSG.1067']}")));
            }
        }
    }

    /**
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void issueQtyLotBinValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number issueQty = (Number) object;
            Number lotQty = new Number(0);
            if (totStkLotBinBind.getValue() != null) {
                lotQty = (Number) totStkLotBinBind.getValue();
                _log.info("Issue qty-" + issueQty + "lot qty-" + lotQty);
                if (issueQty.compareTo(lotQty) ==
                    1) {
                    //throw new ValidatorException(new FacesMessage("Invalid Quantity","Issue Quantity can not be more than available quantity"));
                    throw new ValidatorException(new FacesMessage(resolvEl("#{bundle['MSG.336']}"),
                                                                  resolvEl("#{bundle['MSG.1066']}")));
                }
            }
            if (issueQty.compareTo(0) ==
                -1) {
                // throw new ValidatorException(new FacesMessage("Invalid Quantity","Issue Quantity can not be negative"));
                throw new ValidatorException(new FacesMessage(resolvEl("#{bundle['MSG.336']}"),
                                                              resolvEl("#{bundle['MSG.1067']}")));
            }
        }
    }


    /**
     * @param disclosureEvent
     */
    public void deliverySchdlDisclosureListner(DisclosureEvent disclosureEvent) {
        /* if (checkMandateField().equalsIgnoreCase("Y")) {
            DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("SlsSo1Iterator");
            DCBindingContainer dcBindingContainer = (DCBindingContainer)getBindings();
            _log.info("Transaction Status-" + dcBindingContainer.getDataControl().isTransactionModified());
            String tfMode = resolvEl("#{pageFlowScope.PARAM_ORD_MODE}");
            if (tfMode.equalsIgnoreCase("V") && app_mode.equalsIgnoreCase("V")) {

            } else if (dcBindingContainer.getDataControl().isTransactionModified()) {
                OperationBinding ob = executeOperation("generateDispDocNo");
                ob.execute();
                executeOperation("dlvrySchdlDiscList").execute();
            }

        } */
        //Resetting delivery schedule view for every tab discloser action
        ADFBeanUtils.findOperation("refreshSchdlDiscList").execute();
    }

    /***
     * Value Changes Listeners defined for components
     *
     * **/
    public void selectCheckDlvSchdVCE(ValueChangeEvent vce) {
        //  System.out.println("Inside VCE");
        _log.info(vce.getNewValue().toString());
        if (vce.getNewValue() != null) {
            if ("true".equalsIgnoreCase(vce.getNewValue().toString())) {
                _log.info("Balance qty-->" + blncQtyBind.getValue());
                schdlQuantTrans.setValue(blncQtyBind.getValue());
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(schdlQuantTrans);

    }

    /**Apply Order Wise TAX
     * @param vce
     */
    public void taxRuleOrderVCE(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            Integer ruleId = Integer.parseInt(vce.getNewValue().toString());
            _log.info("Tax Rule id is-->" + ruleId);
            OperationBinding taxOrd = executeOperation("procTaxOrder");
            taxOrd.getParamsMap().put("ruleId", ruleId);
            taxOrd.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
        }
    }

    /**
     * @param vce
     */
    public void exemptFlagVCE(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            String exmpTax = vce.getNewValue().toString();
            //  System.out.println("Exmpt flg is--" + exmpTax);
        }
        if (taxRuleIdOrderBind.getValue() != null) {
            Integer ruleId = Integer.parseInt(taxRuleIdOrderBind.getValue().toString());
            _log.info("Tax Rule id is-->" + ruleId);
            OperationBinding taxOrd = executeOperation("procTaxOrder");
            taxOrd.getParamsMap().put("ruleId", ruleId);
            taxOrd.execute();
        }
    }

    /**Tax Rule VCE to apply Tax Rule on Item
     * @param vce
     */
    public void taxRuleItemVCE(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            Integer ruleId = Integer.parseInt(vce.getNewValue().toString());
            _log.info("Tax Rule id is-->" + ruleId);
            OperationBinding taxOrd = executeOperation("procTaxItem");
            taxOrd.getParamsMap().put("ruleId", ruleId);
            taxOrd.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tottaxAmntBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(orderTotCostBind);
        }
    }

    /**Apply Tax Check Box VCE for Item to remove and Apply TAX
     * @param vce
     */
    public void applyTaxItmChkVCE(ValueChangeEvent vce) {
        _log.info("Apply Tax Flag is-->" + vce.getNewValue());
        /* if (vce.getNewValue() != null) {
            String applyTaxFlg = vce.getNewValue().toString();
            _log.info("Apply Tax Flag is-->" + applyTaxFlg);
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            DCIteratorBinding shpMntIter = (DCIteratorBinding) getBindings().get("SlsSoItm1Iterator");
            Row curRow = shpMntIter.getCurrentRow();
            Key key = curRow.getKey();
            if ("false".equalsIgnoreCase(applyTaxFlg)) {
                executeOperation("removeItmTax").execute();
            }
            if (key != null) {
                shpMntIter.setCurrentRowWithKey(key.toStringFormat(true));
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
        } */
    }

    /**VCE to apply Scheme on Item
     * @param vce Mehtod not in use so commented. 15-June-2015, 3:15
     */
    /* public void schmIdVCE(ValueChangeEvent vce) {

        System.out.println("In Scheme VCE Id is-----> " + vce.getNewValue());
        if (vce.getNewValue() != null) {
            String schmId = vce.getNewValue().toString();
            System.out.println("Schm Id is------> " + schmId);
            OperationBinding ob = executeOperation("insertIntoSchm");
            ob.getParamsMap().put("schmId", schmId);
            ob.execute();
            discValBind.setValue(ob.getResult());
        } else {
            executeOperation("removeScheme").execute();
            OperationBinding binding = this.getBindings().getOperationBinding("getAndSetDiscountForItmFromPolicy");
            binding.execute();
            Object o = binding.execute();
            System.out.println(" discount after scheme removal *************** == " + o);
            discValBind.setValue(o);
            AdfFacesContext.getCurrentInstance().addPartialTarget(discValBind);
        }
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
    }
*/

    /**Quotation Lov Value Change Listener to populate SO from Quotation*/
    public void quotationIdVCE(ValueChangeEvent vce) {
        _log.info("Inside Quotation VCE");
        if (vce.getNewValue() != null) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(vce.getComponent());

    }

    /**Method to Populate order from Previous Order*/
    public void previousOrderVCE(ValueChangeEvent vce) {
        _log.info("Inside Pre Order VCE");
        if (vce.getNewValue() != null) {
            String dispId = vce.getNewValue().toString();
            _log.info("Previous Order Display id-->" + dispId);
            OperationBinding ob = executeOperation("populatePrevOrdToSo");
            ob.getParamsMap().put("refDocId", dispId);
            ob.execute();
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(soBasisBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ordTypeBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tottaxAmntBind);
    }

    /**Method to Populate order from Template*/
    public void templateIdVCE(ValueChangeEvent vce) {
        _log.info("Inside template VCE");
        if (vce.getNewValue() != null) {
            String dispId = vce.getNewValue().toString();
            _log.info("Template Order Display id-->" + dispId);
            OperationBinding ob = executeOperation("populateTemplateToSo");
            ob.getParamsMap().put("templId", dispId);
            ob.execute();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(soBasisBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ordTypeBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tottaxAmntBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tottaxAmntBind);
    }


    /**Value change to set price for item- Suggested Order
     * @param vce
     */
    public void eoIdSugstVCE(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {

            Integer suppId = Integer.parseInt(vce.getNewValue().toString());
            _log.info("Eo Id Index --" + suppId);
            OperationBinding latestPrice = executeOperation("getLatestItmPrice");
            latestPrice.getParamsMap().put("eoId", suppId);
            latestPrice.execute();
            if (latestPrice.getResult() != null) {
                Number price = (Number) latestPrice.getResult();
                if (price.compareTo(-2) == 0) {
                    // showFacesMessage("Item or Supplier not found, try again", "E", false, "F");
                    showFacesMessage(resolvEl("#{bundle['MSG.942']}"), "E", true, "F");
                } else {
                    _log.info("Rate in bean is-->" + price);
                    itmPriceSgstBind.setValue(price);
                }
            }
        }
    }


    /**Method to calculate price for Item - Customer Wise
     * @param vce
     */

    /**Method to calculate price for Item - Customer Wise
     * @param vce
     */
    public void itmIdForRateVCE(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {
            String itmId = vce.getNewValue().toString();
            _log.info("Item Id is-->" + itmId);
            OperationBinding ob = executeOperation("getLatestItmPriceSo");
            ob.getParamsMap().put("itmId", itmId);
            ob.execute();
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            oldPriceDesc = null;
            _log.info("Item price is : " + ob.getResult());
            if (ob.getResult() != null && ((Number) ob.getResult()).compareTo(new Number(-3)) != 0) {
                itmPriceBind.setValue(ob.getResult());
                OperationBinding binding = this.getBindings().getOperationBinding("getAndSetDiscountForItmFromPolicy");
                binding.execute();
                //Object o = binding.execute(); /* Commented on 15-June-15, 3:20, for scheme issues.*/
                //discValBind.setValue(o);
                ADFBeanUtils.getOperationBinding("calcAndSetAscblVal").execute();
                //AdfFacesContext.getCurrentInstance().addPartialTarget(discValBind); /* Commented on 15-June-15, 3:20, for scheme issues.*/
                vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
                AdfFacesContext.getCurrentInstance().addPartialTarget(itmPriceBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itmIdBind); //itmFormLayoutBind
                AdfFacesContext.getCurrentInstance().addPartialTarget(itmFormLayoutBind);
                FacesMessage msg = new FacesMessage("Please enter Item Quantity !", "");
                msg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(itmQtyBind.getClientId(), msg);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itemTablePanelCollBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itemTablePanelBoxBind);

            } else {
                FacesMessage msg =
                    new FacesMessage("Unable to fetch Item Price according to Customer Price policy!",
                                     "Kindly check Customer Price policy !");
                msg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(vce.getComponent().getClientId(), msg);
            }
        }
    }


    /**Value Change Listener to set default warehouse in case of cash-sale and rush order
     * @param vce
     */
    public void orderTypeVCE(ValueChangeEvent vce) {
        if (resolvElO("#{pageFlowScope.GLBL_ORG_WH_CHK}") != null) {
            isWhUsed = resolvElO("#{pageFlowScope.GLBL_ORG_WH_CHK}").toString();
        }
        if (vce.getNewValue() != null) {

            Integer ordType = Integer.parseInt(vce.getNewValue().toString());

            if (ordType.equals(309) || ordType.equals(310) || ordType.equals(892) || ordType.equals(897)) {
                if (resolvElO("#{pageFlowScope.PARAM_USR_WH_ID}") != null && "Y".equalsIgnoreCase(isWhUsed)) {
                    String defWh = resolvElO("#{pageFlowScope.PARAM_USR_WH_ID}").toString();
                    _log.info("Default Warehouse is-" + defWh);
                    whIdSoBind.setValue(defWh);
                }
            }
            if (ordType.equals(897) || ordType.equals(892) || ordType.equals(310)) {
                //  System.out.println("when order type is cash sale order ");
                AdfFacesContext.getCurrentInstance().addPartialTarget(paymentModeTabBVar);
            }
            if (ordType.equals(481)) {
                soBasisBind.setValue(391);
                AdfFacesContext.getCurrentInstance().addPartialTarget(soBasisBind);
                taxFlgRBPgBind.setValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(taxFlgRBPgBind);

            } else {
                contratctTypePgBind.setValue(null);
                contractValuePgBind.setValue(null);
                soBasisBind.setValue(391);
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(contratctTypePgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(contractValuePgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soBasisBind);

        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(cashSaledlvAddBind);
    }

    /**Value Change Listener to set pending amount value
     * @param vce
     */
    public void payDateVCE(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            _log.info("Amt to set-" + pendPayAmtTrans.getValue());
            payAmtTransBind.setValue(pendPayAmtTrans.getValue());
            AdfFacesContext.getCurrentInstance().addPartialTarget(payAmtTransBind);

        }
    }

    /**Dialog Listeners defined for popUps**/

    public void ocPopUpDialogListener(DialogEvent dialogEvent) {
        String flg = "N";
        OperationBinding ob = executeOperation("ocAmtValidator");
        ob.getParamsMap().put("ocAmt", new Number(0));
        ob.execute();
        if (ob.getResult() != null) {
            flg = ob.getResult().toString();
        }
        if ("Y".equalsIgnoreCase(flg.toString())) {
            // FacesMessage msg = new FacesMessage("Specific Amount leads negative value");
            FacesMessage msg = new FacesMessage(resolvEl("#{bundle['MSG.943']}"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        executeOperation("Execute4").execute();
    }

    /**Dialog Listenere For Template Description Popup*/
    public void templateDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            if (templateNameBind.getValue() != null) {
                String tmplDesc = templateNameBind.getValue().toString();
                _log.info("Template Description is-->" + tmplDesc);
                OperationBinding Savetmpl = executeOperation("saveAsTemp");
                Savetmpl.getParamsMap().put("tmplDesc", tmplDesc);
                Savetmpl.execute();
                executeOperation("Commit").execute();
                if (Savetmpl.getResult() != null && Savetmpl.getResult().equals("Y")) {
                    // showFacesMessage("Template Saved Successfully", "I", false, "F");
                    showFacesMessage("MSG.944", "I", true, "F");
                }
            }
        }
    }


    /**To Issue Item from LOT
     * @param dialogEvent
     */
    public void selectItmLotDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            DCIteratorBinding parentIter = (DCIteratorBinding) getBindings().get("SlsSoItm1Iterator");
            Key parentKey = parentIter.getCurrentRow().getKey();
            _log.info("Key before lot-" + parentKey);


            String errMsg = null;
            String itmId = null;
            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = executeOperation("getTotalItemIssueQtyLot");
            if (lotItemIdBindVar.getValue() != null) {
                itmId = lotItemIdBindVar.getValue().toString();
            }

            issueQty.getParamsMap().put("itmId", itmId);
            issueQty.execute();
            if (issueQty.getResult() != null) {
                issQty = (Number) issueQty.getResult();
                _log.info("pickqty bind-" + itmQtyIssueLblBind.getValue());
                if (itmQtyIssueLblBind.getValue() != null) {
                    pickQty = (Number) itmQtyIssueLblBind.getValue();
                }
                _log.info("IssueQty-" + issQty + "PickedQty-" + pickQty);
                if (issQty.compareTo(0) == 0) {
                    // errMsg = "Please select quantity to issue";
                    errMsg = resolvEl("#{bundle['MSG.941']}");
                    FacesMessage msg = new FacesMessage(errMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else if (issQty.compareTo(pickQty) != 0) {
                    //  errMsg = "Issued Quantity is not equal to Ordered Quantity";
                    errMsg = resolvEl("#{bundle['MSG.947']}");
                    FacesMessage msg = new FacesMessage(errMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    executeOperation("insertIntoSoItmLot").execute();
                    executeOperation("Execute6").execute();
                    executeOperation("Execute").execute();
                    if (parentKey != null) {
                        _log.info("Key before lot-" + parentKey);
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }

                }
            }
        }
    }


    /**To Issue Item from Bin, if organization is using bin
     * @param dialogEvent
     */
    public void selectItmLotBinDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            DCIteratorBinding parentIter = (DCIteratorBinding) getBindings().get("SlsSoItm1Iterator");
            Key parentKey = parentIter.getCurrentRow().getKey();
            _log.info("Key before Bin-" + parentKey);

            String errMsg = null;
            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = executeOperation("getTotalIssueQtyBin");
            issueQty.execute();
            if (issueQty.getResult() != null) {
                issQty = (Number) issueQty.getResult();
                _log.info("Orderqty bind-" + itmQtyIssueLblBind.getValue());
                if (itmQtyIssueLblBind.getValue() != null) {
                    pickQty = (Number) itmQtyIssueLblBind.getValue();
                }
                _log.info("IssueQty-" + issQty + "PickedQty-" + pickQty);
                if (issQty.compareTo(0) == 0) {
                    // errMsg = "Please select quantity to issue";
                    errMsg = resolvEl("#{bundle['MSG.941']}");
                    FacesMessage msg = new FacesMessage(errMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else if (issQty.compareTo(pickQty) != 0) {
                    // errMsg = "Issued Quantity is not equal to Ordered Quantity";
                    errMsg = resolvEl("#{bundle['MSG.947']}");
                    FacesMessage msg = new FacesMessage(errMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    executeOperation("insertIntoSoItmBin").execute(); //insertIntoSoItmLot
                    executeOperation("Execute6").execute();
                    executeOperation("Execute7").execute();
                    executeOperation("Execute").execute();
                    if (parentKey != null) {
                        _log.info("Key before lot-" + parentKey);
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }

                }
            }
        }
    }


    /**DialogListener to insert data in Lot Bin and Sr Tables of SLS$SO$ITM
     * @param dialogEvent
     */
    public void selectItmSrNoDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            DCIteratorBinding parentIter = (DCIteratorBinding) getBindings().get("SlsSoItm1Iterator");
            Key parentKey = parentIter.getCurrentRow().getKey();
            _log.info("Key before Sr-" + parentKey);

            String errMsg = null;
            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = executeOperation("getTotalIssueQtySr");
            issueQty.execute();
            if (issueQty.getResult() != null) {
                issQty = (Number) issueQty.getResult();
                /*    _log.info("Orderqty bind-" + itmQtyIssueLblBind.getValue());
                if (itmQtyIssueLblBind.getValue() != null) {
                    pickQty = (Number)itmQtyIssueLblBind.getValue();
                } */
                _log.info("Orderqty bind-" + itmIssueSrBind.getValue());
                if (itmIssueSrBind.getValue() != null) {
                    pickQty = (Number) itmIssueSrBind.getValue();
                }
                _log.info("IssueQty-" + issQty + "OrderQty-" + pickQty);
                if (issQty.compareTo(0) == 0) {
                    // errMsg = "Please select quantity to issue";
                    errMsg = resolvEl("#{bundle['MSG.941']}");
                    FacesMessage msg = new FacesMessage(errMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else if (issQty.compareTo(pickQty) != 0) {
                    // errMsg = "Issued Quantity is not equal to Ordered Quantity";
                    errMsg = resolvEl("#{bundle['MSG.947']}");
                    FacesMessage msg = new FacesMessage(errMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    executeOperation("insertIntoPickItmSr").execute();
                    executeOperation("Execute6").execute();
                    executeOperation("Execute7").execute();
                    executeOperation("Execute8").execute();
                    executeOperation("Execute1").execute();
                    executeOperation("Execute").execute();
                    if (parentKey != null) {
                        _log.info("Key before lot-" + parentKey);
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                }
            }
        }

    }

    /**
     * @param popupCanceledEvent
     */
    public void ordTaxPopUpCancelListener(PopupCanceledEvent popupCanceledEvent) {
        _log.info("In Cancel listener Order tax");
        executeOperation("removeOrdTax").execute();
    }

    /**
     * @param popupCanceledEvent
     */
    public void itmTaxPopUpCancelListener(PopupCanceledEvent popupCanceledEvent) {
        _log.info("In Cancel listenr Itm Tax");
        executeOperation("removeItmTax").execute();
    }

    /**To be used in T&C Drag Drop
     * @param dropEvent
     * @return
     */
    public DnDAction tncDropListener(DropEvent dropEvent) {
        // System.out.println("Inside Drop Listener");
        DCBindingContainer bc = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcib = bc.findIteratorBinding("LovTnc1Iterator");
        Row currentRow = dcib.getCurrentRow();
        _log.info("Current row is-->" + currentRow.getAttribute("TncDesc"));
        OperationBinding ob = executeOperation("addTnCAction");
        ob.getParamsMap().put("curRow", currentRow);
        ob.execute();
        return DnDAction.COPY;
    }

    public void dragDropEndListener(DropEvent dropEvent) {

        _log.info("Inside end listener--");
    }


    /***************************************Getter-Setter defined for application**********************************************************/

    public void setBlncQtyBind(RichInputText blncQtyBind) {
        this.blncQtyBind = blncQtyBind;
    }

    public RichInputText getBlncQtyBind() {
        return blncQtyBind;
    }


    /**
     * @param selectAllLinkBind
     */
    public void setSelectAllLinkBind(RichCommandLink selectAllLinkBind) {
        this.selectAllLinkBind = selectAllLinkBind;
    }

    /**
     * @return
     */
    public RichCommandLink getSelectAllLinkBind() {
        return selectAllLinkBind;
    }


    public void setOrderTaxPopUpBind(RichPopup orderTaxPopUpBind) {
        this.orderTaxPopUpBind = orderTaxPopUpBind;
    }

    public RichPopup getOrderTaxPopUpBind() {
        return orderTaxPopUpBind;
    }


    public void setTaxRuleIdOrderBind(RichSelectOneChoice taxRuleIdOrderBind) {
        this.taxRuleIdOrderBind = taxRuleIdOrderBind;
    }

    public RichSelectOneChoice getTaxRuleIdOrderBind() {
        return taxRuleIdOrderBind;
    }

    public void setOcPopUpBind(RichPopup ocPopUpBind) {
        this.ocPopUpBind = ocPopUpBind;
    }

    public RichPopup getOcPopUpBind() {
        return ocPopUpBind;
    }


    public void setItemTaxPopUpBind(RichPopup itemTaxPopUpBind) {
        this.itemTaxPopUpBind = itemTaxPopUpBind;
    }

    public RichPopup getItemTaxPopUpBind() {
        return itemTaxPopUpBind;
    }


    public void setApp_mode(String app_mode) {
        this.app_mode = app_mode;
    }

    public String getApp_mode() {
        return app_mode;
    }

    public void setDiscOrderPopUpBind(RichPopup discOrderPopUpBind) {
        this.discOrderPopUpBind = discOrderPopUpBind;
    }

    public RichPopup getDiscOrderPopUpBind() {
        return discOrderPopUpBind;
    }


    public void setDiscTypSobind(RichSelectOneRadio discTypSobind) {
        this.discTypSobind = discTypSobind;
    }

    public RichSelectOneRadio getDiscTypSobind() {
        return discTypSobind;
    }


    public void setItmPriceBind(RichInputText itmPriceBind) {
        this.itmPriceBind = itmPriceBind;
    }

    public RichInputText getItmPriceBind() {
        return itmPriceBind;
    }

    public void setItmQtyBind(RichInputText itmQtyBind) {
        this.itmQtyBind = itmQtyBind;
    }

    public RichInputText getItmQtyBind() {
        return itmQtyBind;
    }

    public void setDiscTypItmBind(RichSelectOneRadio discTypItmBind) {
        this.discTypItmBind = discTypItmBind;
    }

    public RichSelectOneRadio getDiscTypItmBind() {
        return discTypItmBind;
    }


    public void setSchdlQuantTrans(RichInputText schdlQuantTrans) {
        this.schdlQuantTrans = schdlQuantTrans;
    }

    /**
     * @return
     */
    public RichInputText getSchdlQuantTrans() {
        return schdlQuantTrans;
    }


    public void setCheckTaxYN(String checkTaxYN) {
        this.checkTaxYN = checkTaxYN;
    }

    public String getCheckTaxYN() {
        String flag = "N";
        OperationBinding checkOrdTax = executeOperation("checkOrderTaxAmt");
        checkOrdTax.execute();
        if (checkOrdTax.getResult() != null) {
            flag = checkOrdTax.getResult().toString();

        }
        return flag;
    }


    public void setOrdTypeBind(RichSelectOneChoice ordTypeBind) {
        this.ordTypeBind = ordTypeBind;
    }

    public RichSelectOneChoice getOrdTypeBind() {
        return ordTypeBind;
    }


    public void setEoNmTransBind(RichInputListOfValues eoNmTransBind) {
        this.eoNmTransBind = eoNmTransBind;
    }

    public RichInputListOfValues getEoNmTransBind() {
        /* StringBuffer clientId = new StringBuffer(currConvRateBind.getClientId());
        clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
        clientId = clientId.append("eoNmTransId");
        RichInputListOfValues c = new RichInputListOfValues();
        c.setId("eoNmTransId");

        System.out.println("Component Id : "+c+" Value : "+c.getValue());
        return c;
         */
        return eoNmTransBind;
    }


    public void setTaxRuleIdItmBind(RichSelectOneChoice taxRuleIdItmBind) {
        this.taxRuleIdItmBind = taxRuleIdItmBind;
    }

    public RichSelectOneChoice getTaxRuleIdItmBind() {
        return taxRuleIdItmBind;
    }

    public void setExmptFlgItmBind(RichSelectBooleanCheckbox exmptFlgItmBind) {
        this.exmptFlgItmBind = exmptFlgItmBind;
    }

    public RichSelectBooleanCheckbox getExmptFlgItmBind() {
        return exmptFlgItmBind;
    }


    public void setCurrIdSpBind(RichInputListOfValues currIdSpBind) {
        this.currIdSpBind = currIdSpBind;
    }

    public RichInputListOfValues getCurrIdSpBind() {
        return currIdSpBind;
    }

    /**
     * @param ordDateBind
     */
    public void setOrdDateBind(RichInputDate ordDateBind) {
        this.ordDateBind = ordDateBind;
    }

    public RichInputDate getOrdDateBind() {
        return ordDateBind;
    }


    public void setAmtSpOcBind(RichInputText amtSpOcBind) {
        this.amtSpOcBind = amtSpOcBind;
    }

    public RichInputText getAmtSpOcBind() {
        return amtSpOcBind;
    }


    public void setItmPriceSgstBind(RichInputText itmPriceSgstBind) {
        this.itmPriceSgstBind = itmPriceSgstBind;
    }

    public RichInputText getItmPriceSgstBind() {
        return itmPriceSgstBind;
    }

    public void setGeneratedPoPopUpBind(RichPopup generatedPoPopUpBind) {
        this.generatedPoPopUpBind = generatedPoPopUpBind;
    }

    public RichPopup getGeneratedPoPopUpBind() {
        return generatedPoPopUpBind;
    }

    public void setTreeOrTab(boolean treeOrTab) {
        this.treeOrTab = treeOrTab;
    }

    public boolean isTreeOrTab() {
        return treeOrTab;
    }

    public void setTreeTable(RichTreeTable treeTable) {
        this.treeTable = treeTable;
    }

    public RichTreeTable getTreeTable() {
        return treeTable;
    }


    public void setOrdStatusBind(RichSelectOneChoice ordStatusBind) {
        this.ordStatusBind = ordStatusBind;
    }

    public RichSelectOneChoice getOrdStatusBind() {
        return ordStatusBind;
    }

    public void setBackOrdStatusBind(RichSelectOneChoice backOrdStatusBind) {
        this.backOrdStatusBind = backOrdStatusBind;
    }

    public RichSelectOneChoice getBackOrdStatusBind() {
        return backOrdStatusBind;
    }

    public void setConsignOrdStatusBind(RichSelectOneChoice consignOrdStatusBind) {
        this.consignOrdStatusBind = consignOrdStatusBind;
    }

    public RichSelectOneChoice getConsignOrdStatusBind() {
        return consignOrdStatusBind;
    }

    public void setCurrConvRateBind(RichInputText currConvRateBind) {
        this.currConvRateBind = currConvRateBind;
    }

    public RichInputText getCurrConvRateBind() {
        return currConvRateBind;
    }


    public void setItemTableBind(RichTable itemTableBind) {
        this.itemTableBind = itemTableBind;
    }

    public RichTable getItemTableBind() {
        return itemTableBind;
    }

    public void setSoBasisBind(RichSelectOneChoice soBasisBind) {
        this.soBasisBind = soBasisBind;
    }

    public RichSelectOneChoice getSoBasisBind() {
        return soBasisBind;
    }

    public void setQuotIdBind(RichSelectOneChoice quotIdBind) {
        this.quotIdBind = quotIdBind;
    }

    public RichSelectOneChoice getQuotIdBind() {
        return quotIdBind;
    }

    public void setTemplateIdBind(RichInputListOfValues templateIdBind) {
        this.templateIdBind = templateIdBind;
    }

    public RichInputListOfValues getTemplateIdBind() {
        return templateIdBind;
    }

    public void setRefSoDocIdBind(RichInputListOfValues refSoDocIdBind) {
        this.refSoDocIdBind = refSoDocIdBind;
    }

    public RichInputListOfValues getRefSoDocIdBind() {
        return refSoDocIdBind;
    }

    public void setTemplateNameBind(RichInputText templateNameBind) {
        this.templateNameBind = templateNameBind;
    }

    public RichInputText getTemplateNameBind() {
        return templateNameBind;
    }


    public void setTemplateDescPopUpBind(RichPopup templateDescPopUpBind) {
        this.templateDescPopUpBind = templateDescPopUpBind;
    }

    public RichPopup getTemplateDescPopUpBind() {
        return templateDescPopUpBind;
    }

    public void setPriceCompareSoPopupBind(RichPopup priceCompareSoPopupBind) {
        this.priceCompareSoPopupBind = priceCompareSoPopupBind;
    }

    public RichPopup getPriceCompareSoPopupBind() {
        return priceCompareSoPopupBind;
    }


    public void setTreeTabPanelCollBind(RichPanelCollection treeTabPanelCollBind) {
        this.treeTabPanelCollBind = treeTabPanelCollBind;
    }

    public RichPanelCollection getTreeTabPanelCollBind() {
        return treeTabPanelCollBind;
    }

    public void pmtSchdlDisclosureListener(DisclosureEvent disclosureEvent) {
        // Add event code here...
    }

    public void setPanelTabbedBind(RichPanelTabbed panelTabbedBind) {
        this.panelTabbedBind = panelTabbedBind;
    }

    public RichPanelTabbed getPanelTabbedBind() {
        return panelTabbedBind;
    }

    public void setItemDetailsTabBind(RichShowDetailItem itemDetailsTabBind) {
        this.itemDetailsTabBind = itemDetailsTabBind;
    }

    public RichShowDetailItem getItemDetailsTabBind() {
        return itemDetailsTabBind;
    }

    public void setSoItmPoTableBind(RichTable soItmPoTableBind) {
        this.soItmPoTableBind = soItmPoTableBind;
    }

    public RichTable getSoItmPoTableBind() {
        return soItmPoTableBind;
    }


    public void setFacetName(String facetName) {
        this.facetName = facetName;
    }

    public String getFacetName() {
        return facetName;
    }

    public void setSchmFacetName(String schmFacetName) {
        this.schmFacetName = schmFacetName;
    }

    public String getSchmFacetName() {
        return schmFacetName;
    }

    public void setSelectLotPopUpBind(RichPopup selectLotPopUpBind) {
        this.selectLotPopUpBind = selectLotPopUpBind;
    }

    public RichPopup getSelectLotPopUpBind() {
        return selectLotPopUpBind;
    }


    public void setSelectLotBinPopUpBind(RichPopup selectLotBinPopUpBind) {
        this.selectLotBinPopUpBind = selectLotBinPopUpBind;
    }

    public RichPopup getSelectLotBinPopUpBind() {
        return selectLotBinPopUpBind;
    }


    public void setSelectSrNoPopUpBind(RichPopup selectSrNoPopUpBind) {
        this.selectSrNoPopUpBind = selectSrNoPopUpBind;
    }

    public RichPopup getSelectSrNoPopUpBind() {
        return selectSrNoPopUpBind;
    }

    public void setOcTableBind(RichTable ocTableBind) {
        this.ocTableBind = ocTableBind;
    }

    public RichTable getOcTableBind() {
        return ocTableBind;
    }

    /**
     * @param disclosureEvent
     */
    public void itemDetailsDisclosureListener(DisclosureEvent disclosureEvent) {
        executeOperation("itmDisclosureList").execute();
    }

    public void setChkDlcSchdlItm(boolean chkDlcSchdlItm) {
        this.chkDlcSchdlItm = chkDlcSchdlItm;
    }

    public boolean isChkDlcSchdlItm() {
        if (chkDlcSchdlItm == null) {
            OperationBinding flg = executeOperation("checkDlvSchdlFrItm");
            flg.execute();
            if (flg.getResult() != null) {
                chkDlcSchdlItm = (Boolean) flg.getResult();
            }
        }
        return chkDlcSchdlItm;
    }

    /*     public void setItmQtyIssueBind(RichOutputText itmQtyIssueBind) {
        this.itmQtyIssueBind = itmQtyIssueBind;
    }

    public RichOutputText getItmQtyIssueBind() {
        return itmQtyIssueBind;
    } */

    public void setWhIdSoBind(RichSelectOneChoice whIdSoBind) {
        this.whIdSoBind = whIdSoBind;
    }

    public RichSelectOneChoice getWhIdSoBind() {
        return whIdSoBind;
    }

    public void setCancelOrderBind(RichSelectBooleanCheckbox cancelOrderBind) {
        this.cancelOrderBind = cancelOrderBind;
    }

    public RichSelectBooleanCheckbox getCancelOrderBind() {
        return cancelOrderBind;
    }


    public void setTlrncQtyTypBind(RichSelectOneChoice tlrncQtyTypBind) {
        this.tlrncQtyTypBind = tlrncQtyTypBind;
    }

    public RichSelectOneChoice getTlrncQtyTypBind() {
        return tlrncQtyTypBind;
    }


    /**Method to set base quantity
     * @param vce
     */
    public void itmQtyFrBaseVCE(ValueChangeEvent vce) {
        //validationsEnabled = false;
        //System.out.println("____________________ disabling validations");
        if (vce.getNewValue() != null) {
            Number qty = (Number) vce.getNewValue();
            OperationBinding ob = executeOperation("setQtyBs");
            ob.getParamsMap().put("itmQty", qty);
            ob.execute();
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            OperationBinding sch = ADFBeanUtils.findOperation("setDefaultSchemPerQty");
            sch.execute();
            //  System.out.println(sch.getResult() + "  This is the scheme");
            if (sch.getResult() != null) {
                //discValBind.setValue(0);  /* Commented on 15-June-15, 3:20, for scheme issues.*/
                String schmId = (String) sch.getResult();
                Object currDiscVal = null;
                ob = executeOperation("insertIntoSchm");
                ob.getParamsMap().put("schmId", schmId);
                ob.execute();
                currDiscVal = ob.getResult();
                if (ob.getResult() == null) {
                    OperationBinding big = this.getBindings().getOperationBinding("getAndSetDiscountForItmFromPolicy");
                    big.execute();

                    currDiscVal = big.getResult();
                    //   System.out.println("discount from policy for scheme ================================ to be set" +
                    //                    currDiscVal);
                }
                //  System.out.println(schmId + " final discount for scheme================================ to be set");
                discValBind.setValue(currDiscVal); /* Commented on 15-June-15, 3:20, for scheme issues.*/
                //  System.out.println(schmId + "================================ to be set");
                queueRefreshAction(); /** Queue Event to refresh the Item detail table */
                AdfFacesContext.getCurrentInstance().addPartialTarget(schemeIdPgBind);
                //executeOperation("Execute").execute();
                // AdfFacesContext.getCurrentInstance().addPartialTarget(discValBind); /* Commented on 15-June-15, 3:20, for scheme issues.*/
            } else {
                //this.getBindings().getOperationBinding("getAndSetDiscountForItmFromPolicy").execute();
                //discValBind.setValue(0);  /* Commented on 15-June-15, 3:20, for scheme issues.*/
                OperationBinding binding = this.getBindings().getOperationBinding("getAndSetDiscountForItmFromPolicy");
                binding.execute();
                Object o = binding.getResult();
                discValBind.setValue(o);
                AdfFacesContext.getCurrentInstance().addPartialTarget(discValBind); /* Commented on 15-June-15, 3:20, for scheme issues.*/

                //  OperationBinding operationBinding = this.getBindings().getOperationBinding("ItemExistInSchmOrNot");
                //  operationBinding.execute();
                //   Object res = operationBinding.getResult();
                //  if (res != null && (Boolean) res) {
                //     discValBind.setValue(new Number(0));

                ////  } else {


                // AdfFacesContext.getCurrentInstance().addPartialTarget(discValBind);
                //    }
            }
            ADFBeanUtils.getOperationBinding("calcAndSetAscblVal").execute();
            ADFBeanUtils.getOperationBinding("insertDiscountForItem").execute();

            //   if (getEnableCostCenter()) {
            //        ADFBeanUtils.findOperation("generateCostCenter").execute();
            //   }


            //System.out.println("____________________ setting new discount");
        }
        // validationsEnabled = true;
        //System.out.println("____________________ enabling validations");
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabbedBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(viewSchmLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmFormLayoutBind);

        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailsTabBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTablePanelBoxBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTablePanelCollBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());


    }

    /**
     * @param vce
     */
    public void cancelOrderVCE(ValueChangeEvent vce) {
        if (vce.getNewValue() != null && vce.getNewValue().equals(true)) {
            Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            Integer usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));

            Integer pending = 0;

            //Check Pending
            OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
            chkUsr.getParamsMap().put("SlocId", sloc_Id);
            chkUsr.getParamsMap().put("CldId", cld_Id);
            chkUsr.getParamsMap().put("OrgId", org_Id);
            chkUsr.getParamsMap().put("DocNo", 21503);
            chkUsr.execute();

            if (chkUsr.getResult() != null) {
                pending = (Integer) chkUsr.getResult();
            }
            _log.info("Current User-" + usr_Id + "and Order Pending At-" + pending);
            if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(usr_Id) == 0) {
                OperationBinding chk = ADFBeanUtils.getOperationBinding("cancelOrderReq");
                chk.execute();
                String tres = chk.getResult() == null ? "N" : chk.getResult().toString();
                if (tres.equalsIgnoreCase("Y")) {
                    ADFBeanUtils.showPopup(cancelOrderPopBinding, true);
                } else {
                    this.canc_Flg = "Y";
                    AdfFacesContext.getCurrentInstance().addPartialTarget(toolBarBinding);
                }
            } else {
                OperationBinding uNm = executeOperation("getUserName");
                uNm.getParamsMap().put("uid", pending);
                uNm.execute();
                cancelOrderBind.setValue("N");
                this.canc_Flg = "N";
                if (uNm.getResult() != null) {
                    /*  String msg2 =
                          "<html><body> <p>This Sales Order is pending at other user<b><i> [ " + uNm.getResult().toString() +
                          " ] </i></b>for approval, You can not amend this.</p></body></html>";*/



                    String msg2 =
                        "<html><body> <p>" + resolvEl("#{bundle['MSG.824']}") + "<b><i> [ " +
                        uNm.getResult().toString() + " ] </i></b>" + resolvEl("#{bundle['MSG.825']}") +
                        "</p></body></html>";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message2);
                }
            }
        } else {
            this.canc_Flg = "N";
        }
    }


    public void cancelOrderDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("Yes")) {
            // System.out.println("Yes +++++++++++++++++++++++++++++");
            this.canc_Flg = "Y";
            AdfFacesContext.getCurrentInstance().addPartialTarget(toolBarBinding);
        } else {
            cancelOrderBind.setValue(false);
            cancelOrderBind.setSelected(false);
            this.canc_Flg = "N";
            AdfFacesContext.getCurrentInstance().addPartialTarget(toolBarBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cancelOrderBind);
            // System.out.println("No +++++++++++++++++++++++++++++");
        }
    }

    /**
     * @param vce
     */
    public void shortyCloseOrderVCE(ValueChangeEvent vce) {
        if (vce.getNewValue() != null && vce.getNewValue().equals(true)) {
            Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            Integer usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));

            Integer pending = 0;

            //Check Pending
            OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
            chkUsr.getParamsMap().put("SlocId", sloc_Id);
            chkUsr.getParamsMap().put("CldId", cld_Id);
            chkUsr.getParamsMap().put("OrgId", org_Id);
            chkUsr.getParamsMap().put("DocNo", 21503);
            chkUsr.execute();

            if (chkUsr.getResult() != null) {
                pending = Integer.parseInt(chkUsr.getResult().toString());
            }
            _log.info("Current User-" + usr_Id + "and Order Pending At-" + pending);
            if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(usr_Id) == 0) {
                this.canc_Flg = "Y";
                vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
                AdfFacesContext.getCurrentInstance().addPartialTarget(toolBarBinding);
            } else {
                OperationBinding uNm = executeOperation("getUserName");
                uNm.getParamsMap().put("uid", pending);
                uNm.execute();
                shortCloseOrdrBind.setValue("N");
                if (uNm.getResult() != null) {
                    /*  String msg2 =
                          "<html><body> <p>This Sales Order is pending at other user<b><i> [ " + uNm.getResult().toString() +
                          " ] </i></b>for approval, You can not amend this.</p></body></html>";*/



                    String msg2 =
                        "<html><body> <p>" + resolvEl("#{bundle['MSG.824']}") + "<b><i> [ " +
                        uNm.getResult().toString() + " ] </i></b>" + resolvEl("#{bundle['MSG.825']}") +
                        "</p></body></html>";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message2);
                }


            }
        }
    }

    public void setCanc_Flg(String canc_Flg) {
        this.canc_Flg = canc_Flg;
    }

    public String getCanc_Flg() {
        return canc_Flg;
    }


    public void setPendPayAmtTrans(RichInputText pendPayAmtTrans) {
        this.pendPayAmtTrans = pendPayAmtTrans;
    }

    public RichInputText getPendPayAmtTrans() {
        return pendPayAmtTrans;
    }

    public void setPayAmtTransBind(RichInputText payAmtTransBind) {
        this.payAmtTransBind = payAmtTransBind;
    }

    public RichInputText getPayAmtTransBind() {
        return payAmtTransBind;
    }

    public void setValidUpToDtBind(RichInputDate validUpToDtBind) {
        this.validUpToDtBind = validUpToDtBind;
    }

    public RichInputDate getValidUpToDtBind() {
        return validUpToDtBind;
    }


    public void setTotStkLotBind(RichInputText totStkLotBind) {
        this.totStkLotBind = totStkLotBind;
    }

    public RichInputText getTotStkLotBind() {
        return totStkLotBind;
    }


    public void setTotStkLotBinBind(RichInputText totStkLotBinBind) {
        this.totStkLotBinBind = totStkLotBinBind;
    }

    public RichInputText getTotStkLotBinBind() {
        return totStkLotBinBind;
    }

    /**
     * Warehouse id value change event
     * @param valueChangeEvent
     */
    public void whChngeVCL(ValueChangeEvent valueChangeEvent) {
        //setWhIdInDlvSchVw;
        if (valueChangeEvent.getNewValue() != null) {
            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
            OperationBinding bi = ADFBeanUtils.findOperation("resetProjectId");
            bi.getParamsMap().put("areTyp", "D");
            bi.execute();
            //System.out.println("VCL +"+valueChangeEvent.getNewValue());
            OperationBinding binding = this.getBindings().getOperationBinding("setWhIdInDlvSchVw");
            binding.getParamsMap().put("WhId", new StringBuffer(valueChangeEvent.getNewValue().toString()));
            binding.execute();
            //this.getBindings().getOperationBinding("Execute9").execute();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(deliveryScheduleTable);

    }

    public void setTrfPopupBind(RichPopup trfPopupBind) {
        this.trfPopupBind = trfPopupBind;
    }

    public RichPopup getTrfPopupBind() {
        return trfPopupBind;
    }

    /**
     * Method to add trfDetails
     * @param actionEvent
     */
    public void addTrfDetailsACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("CreateInsert4").execute();
    }

    /**
     *
     * @param actionEvent
     */
    public void deleteTrfDetailACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("Delete6").execute();
    }

    public void WhIdDestVCL(ValueChangeEvent valueChangeEvent) {
        // setAvailableQuantityOnWarehouseSelection
        if (valueChangeEvent.getNewValue() !=
            null) {
            // System.out.println("WhIdDest : " + valueChangeEvent.getNewValue());
            OperationBinding binding =
                this.getBindings().getOperationBinding("setAvailableQuantityOnWarehouseSelection");
            binding.getParamsMap().put("whId", new StringBuffer(valueChangeEvent.getNewValue().toString()));
            binding.execute();
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
                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.827']}").toString() + "!",
                                     resolvElDCMsg("#{bundle['MSG.828']}").toString() +
                                     "!"); //Invalid number! && Cannot enter negative value
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }

        }
    }

    public Object resolvElDCMsg(String data) {
        return ADFModelUtils.resolvEl(data);
    }

    /**
     * Item quantity TRF Validator
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void TransferItemQuantityVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        this.negativeNumberVAL(facesContext, uIComponent, object);
        if (object != null) {
            int i = ((Number) object).compareTo((Object) new Number(0));
            if (i == 0) {
                FacesMessage message =
                    new FacesMessage(resolvEl("#{bundle['MSG.336']}"),
                                     resolvEl("#{bundle['MSG.892']}")); //Invalid number! && Cannot enter negative value
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }

    /**
     * Method to set Transfer order status to generate
     * @param actionEvent
     */
    public void generateTransferOrderACTION(ActionEvent actionEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("setTransferOrderFlag");
        binding.getParamsMap().put("flag", new StringBuffer("Y"));
        binding.execute();
    }

    /**
     * Method to set Transfer order status to not-generate
     * @param actionEvent
     */
    public void cancelTransferOrderACTION(ActionEvent actionEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("setTransferOrderFlag");
        binding.getParamsMap().put("flag", new StringBuffer("N"));
        binding.execute();
    }

    /**
     *Src WhID Validator
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void orgIdsrcVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...isOrgIdSrcVALID
        if (object != null) {
            if (!object.toString().equals("")) {
                //  System.out.println("Object :" + object);
                OperationBinding binding = this.getBindings().getOperationBinding("isOrgIdSrcVALID");
                binding.getParamsMap().put("whIdSrc", object.toString());
                binding.execute();
                //   System.out.println("result : " + binding.getResult());
                if (binding.getResult().equals((Object) false)) {
                    FacesMessage message =
                        new FacesMessage("Duplicate Destination Warehouse found.",
                                         "Duplicate Destination Warehouse is not allowed !"); //Invalid number! && Cannot enter negative value
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }
            }
        }
    }

    public void setDeliveryScheduleTable(RichTable deliveryScheduleTable) {
        this.deliveryScheduleTable = deliveryScheduleTable;
    }

    public RichTable getDeliveryScheduleTable() {
        return deliveryScheduleTable;
    }

    /**
     * ActionEvent to validate iff transfer order lines are valid or not
     * @param actionEvent
     */
    public void okButtonTrfPopupACTION(ActionEvent actionEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("isTransferOrderValid");
        binding.execute();
        // System.out.println("Result : " + binding.getResult());
        if (binding.getResult().equals((Object) false)) {
            FacesMessage msg =
                new FacesMessage("Transter order cannot be left ungenerated ! Either generate tranfer order for Transfer Order Line or Delete it. ");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            trfPopupBind.hide();
        }

    }

    public void destinationOrgVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (!valueChangeEvent.getNewValue().toString().equals("")) {
                OperationBinding binding = this.getBindings().getOperationBinding("refreshDestWareHouse");
                binding.getParamsMap().put("orgIdDest", new StringBuffer(valueChangeEvent.getNewValue().toString()));
                binding.execute();
            }
        }

    }

    /**
     * DialogEvent to validate iff transfer order lines are valid or not
     * @param dialogEvent
     */
    public void okDialogListnerOfTrfACTION(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding binding = this.getBindings().getOperationBinding("isTransferOrderValid");
            binding.execute();
            //  System.out.println("Result : " + binding.getResult());
            if (binding.getResult().equals((Object) false)) {
                FacesMessage msg =
                    new FacesMessage("Transter order cannot be left ungenerated ! Either generate tranfer order for Transfer Order Line or Delete it. ");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }

    }

    public void setTottaxAmntBind(RichInputText tottaxAmntBind) {
        this.tottaxAmntBind = tottaxAmntBind;
    }

    public RichInputText getTottaxAmntBind() {
        return tottaxAmntBind;
    }

    public boolean getPoMode() {

        if (PoMode == null) {
            OperationBinding binding = this.getBindings().getOperationBinding("doPoExist");

            if (binding != null) {
                binding.execute();
                Object object = binding.getResult();
                PoMode = (Boolean) object;
            }
        }

        //_log.info("Result is : "+object);
        return PoMode;
    }

    public void setPoMode(Boolean PoMode) {
        this.PoMode = PoMode;
    }

    public void populateDeliveryScheduleACTION(ActionEvent actionEvent) {
        if (checkMandateField().equalsIgnoreCase("Y")) {
            DCIteratorBinding parentIter = (DCIteratorBinding) getBindings().get("SlsSo1Iterator");
            DCBindingContainer dcBindingContainer = (DCBindingContainer) getBindings();
            _log.info("Transaction Status-" + dcBindingContainer.getDataControl().isTransactionModified());
            String tfMode = resolvEl("#{pageFlowScope.PARAM_ORD_MODE}");
            executeOperation("dlvrySchdlDiscList").execute();
            //if (tfMode.equalsIgnoreCase("V") && app_mode.equalsIgnoreCase("V")) {

            //  } else if (dcBindingContainer.getDataControl().isTransactionModified()) {
            /* OperationBinding ob = generateDispDocNo("generateDispDocNo");
                ob.execute(); */
            //      executeOperation("dlvrySchdlDiscList").execute();
            //   }
            executeOperation("Execute9").execute();
        }
        chkDlcSchdlItm = null;
    }

    public void setWfNum(String WfNum) {
        this.WfNum = WfNum;
    }

    public String getWfNum() {
        return WfNum;
    }

    public void setFrieghtVChargesBind(RichSelectOneRadio frieghtVChargesBind) {
        this.frieghtVChargesBind = frieghtVChargesBind;
    }

    public RichSelectOneRadio getFrieghtVChargesBind() {
        return frieghtVChargesBind;
    }

    public void setFrieghtBind(RichInputText frieghtBind) {
        this.frieghtBind = frieghtBind;
    }

    public RichInputText getFrieghtBind() {
        return frieghtBind;
    }

    public void EoNmTransVCL(ValueChangeEvent vce) {
        RichInputListOfValues eoNmBind = (RichInputListOfValues) vce.getComponent();
        if (vce != null && vce.getNewValue().toString().length() > 0 && eoNmBind.isValid()) {
            OperationBinding ob = executeOperation("chkEntityType");
            ob.getParamsMap().put("eoName", vce.getNewValue().toString());
            ob.execute();
            if (ob.getResult() != null && (Integer) ob.getResult() != 68) {
                this.adds_mode = "C"; // To enable Add new Address Button
                AdfFacesContext.getCurrentInstance().addPartialTarget(addNewAddressPgBind);
                showPopup(prospectToCustPopUpBind, true);
            } else {
                this.adds_mode = "V"; // To disable Add new Address Button
                AdfFacesContext.getCurrentInstance().addPartialTarget(addNewAddressPgBind);
            }
        } else {
            eoNmBind.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(eoNmBind);

            OperationBinding binding = this.getBindings().getOperationBinding("setSlsSoToNull");
            binding.execute();

            FacesMessage msg = new FacesMessage("", "Please enter valid Name.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(quotDispIdBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pricePolicyPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(eoCatgBinding);

    }

    public void taxTypeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setShortCloseOrdrBind(RichSelectBooleanCheckbox shortCloseOrdrBind) {
        this.shortCloseOrdrBind = shortCloseOrdrBind;
    }

    public RichSelectBooleanCheckbox getShortCloseOrdrBind() {
        return shortCloseOrdrBind;
    }

    public void setDlvAddsBind(RichInputText dlvAddsBind) {
        this.dlvAddsBind = dlvAddsBind;
    }

    public RichInputText getDlvAddsBind() {
        return dlvAddsBind;
    }

    public void SoBasisVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equals("390")) {
                FacesMessage message = new FacesMessage("", "Please Select Customer Name and Shipment Address.");
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(soBasisBind.getClientId(), message);

            }
        }
    }

    public void calculateFreightCharge(DialogEvent dialogEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(orderTotCostBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(frieghtBind);
        dialogEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(frieghtBind);
    }

    public void setOrderTotCostBind(RichInputText orderTotCostBind) {
        this.orderTotCostBind = orderTotCostBind;
    }

    public RichInputText getOrderTotCostBind() {
        return orderTotCostBind;
    }

    public void setCashSaledlvAddBind(RichInputText cashSaledlvAddBind) {
        this.cashSaledlvAddBind = cashSaledlvAddBind;
    }

    public RichInputText getCashSaledlvAddBind() {
        return cashSaledlvAddBind;
    }

    public void setContratctTypePgBind(RichSelectOneChoice contratctTypePgBind) {
        this.contratctTypePgBind = contratctTypePgBind;
    }

    public RichSelectOneChoice getContratctTypePgBind() {
        return contratctTypePgBind;
    }

    public void setContractValuePgBind(RichInputText contractValuePgBind) {
        this.contractValuePgBind = contractValuePgBind;
    }

    public RichInputText getContractValuePgBind() {
        return contractValuePgBind;
    }

    public void setTaxFlgRBPgBind(RichSelectOneRadio taxFlgRBPgBind) {
        this.taxFlgRBPgBind = taxFlgRBPgBind;
    }

    public RichSelectOneRadio getTaxFlgRBPgBind() {
        return taxFlgRBPgBind;
    }

    public void ContractBasisVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().length() > 0) {
                contractValuePgBind.setValue(new Number(0));
                AdfFacesContext.getCurrentInstance().addPartialTarget(contractValuePgBind);
            } else {
                contractValuePgBind.setValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(contractValuePgBind);
            }
        }
    }

    public StringBuffer getPolicyApplied() {
        StringBuffer i = new StringBuffer("N");
        OperationBinding binding = this.getBindings().getOperationBinding("checkPolicyApplied");
        if (binding != null) {
            binding.execute();
            Object object = binding.getResult();
            if (object != null) {
                i = (StringBuffer) object;
            }
        }
        return i;
    }

    public StringBuffer getShowItmAttribute() {
        if (showItmAttribute == null) {
            StringBuffer i = new StringBuffer("N");
            OperationBinding binding = this.getBindings().getOperationBinding("checkShowAtt");
            if (binding != null) {
                binding.execute();
                Object object = binding.getResult();
                if (object != null) {
                    i = (StringBuffer) object;
                }
                showItmAttribute = i;
            }
        }
        return showItmAttribute;
    }

    public void ContractValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            oracle.jbo.domain.Number qty = (oracle.jbo.domain.Number) object;

            if (qty.compareTo(0) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.553']}").toString(), null));
            }

            if (qty.compareTo(0) == 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.87']}").toString(), null));

            }


            if (!isPrecisionValid(26, 6, qty)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.57']}").toString(),
                                                              null)); //Invalid Precision(26,6)
            }


        }

    }

    public void setPricePolicyPgBind(RichSelectOneChoice pricePolicyPgBind) {
        this.pricePolicyPgBind = pricePolicyPgBind;
    }

    public RichSelectOneChoice getPricePolicyPgBind() {
        return pricePolicyPgBind;
    }

    public void setProspectToCustPopUpBind(RichPopup prospectToCustPopUpBind) {
        this.prospectToCustPopUpBind = prospectToCustPopUpBind;
    }

    public RichPopup getProspectToCustPopUpBind() {
        return prospectToCustPopUpBind;
    }

    public void ProsToCustDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding binding = this.getBindings().getOperationBinding("chngProsToCust");
            binding.execute();
        }
    }

    public void ProsToCustCL(PopupCanceledEvent popupCanceledEvent) {
        this.adds_mode = "V";
        AdfFacesContext.getCurrentInstance().addPartialTarget(addNewAddressPgBind);

        OperationBinding binding = this.getBindings().getOperationBinding("setSlsSoToNull");
        binding.execute();

        eoNmTransBind.setValue(null);
        /* StringBuffer clientId = new StringBuffer(currConvRateBind.getClientId());
        clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
        clientId = clientId.append("eoNmTransId");
         */ //UIComponent
        AdfFacesContext.getCurrentInstance().addPartialTarget(eoNmTransBind);

        currIdSpBind.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(currIdSpBind);


    }

    public void setAdds_mode(String adds_mode) {
        this.adds_mode = adds_mode;
    }

    public String getAdds_mode() {
        return adds_mode;
    }

    public void setAddNewAddressPgBind(RichCommandImageLink addNewAddressPgBind) {
        this.addNewAddressPgBind = addNewAddressPgBind;
    }

    public RichCommandImageLink getAddNewAddressPgBind() {
        return addNewAddressPgBind;
    }

    public void setItmQtyIssueLblBind(RichOutputLabel itmQtyIssueLblBind) {
        this.itmQtyIssueLblBind = itmQtyIssueLblBind;
    }

    public RichOutputLabel getItmQtyIssueLblBind() {
        return itmQtyIssueLblBind;
    }

    public void setIsSchemeApplied(Boolean isSchemeApplied) {
        this.isSchemeApplied = isSchemeApplied;
    }

    public Boolean getIsSchemeApplied() {
        if (isSchemeApplied == null) {
            OperationBinding binding = this.getBindings().getOperationBinding("checkSchemeAppliedOrNot");
            if (binding != null) {
                binding.execute();
                if (binding.getResult() != null) {
                    isSchemeApplied = (Boolean) binding.getResult();
                }
            }
        }
        return isSchemeApplied;
    }

    public void setQuotDispIdBind(RichInputListOfValues quotDispIdBind) {
        this.quotDispIdBind = quotDispIdBind;
    }

    public RichInputListOfValues getQuotDispIdBind() {
        return quotDispIdBind;
    }

    /**
     * Action event to add line in Paymnt
     * @param actionEvent
     */
    public void addPaymntACTION(ActionEvent actionEvent) {
        /*  if(pmntAmtBind.getValue() != null){
           Number amt = (Number) pmntAmtBind.getValue();
           if(amt.compareTo(new Number(0)) == 1){ */
        OperationBinding binding = this.getBindings().getOperationBinding("addPaymnt");
        binding.execute();
        /* }else{
               FacesMessage message = new FacesMessage("", "Amount Should be greater than Zero.");
               message.setSeverity(FacesMessage.SEVERITY_INFO);
               FacesContext fc = FacesContext.getCurrentInstance();
               fc.addMessage(pmntAmtBind.getClientId(), message);
           }
        }else{
               FacesMessage message = new FacesMessage("", "Amount Should be greater than Zero.");
               message.setSeverity(FacesMessage.SEVERITY_INFO);
               FacesContext fc = FacesContext.getCurrentInstance();
               fc.addMessage(pmntAmtBind.getClientId(), message);
           }
         */
    }

    /**
     * Negative Number and Precision Validator for Paymnt Amts
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void paymntAmtValid(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            this.negativeNumberVAL(facesContext, uIComponent, object);
            Number qty = (Number) object;
            if (!isPrecisionValid(26, 6, qty)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.57']}").toString(),
                                                              null)); //Invalid Precision(26,6)
            } else {
                Number n = new Number(0);
                n = (Number) object;
                OperationBinding binding = this.getBindings().getOperationBinding("isPaymntAmtValid");
                binding.getParamsMap().put("amt", (Number) object);
                binding.execute();
                if (binding.getResult() != null) {
                    if (binding.getResult().equals((Object) false)) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Please enter valid Payment Amount !", null));
                    }
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Please enter valid Payment Amount !", null));
                }
            }
        }


    }

    public void deletePaymntDtlACTION(ActionEvent actionEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("delPaymntDtl");
        binding.execute();
    }

    public void paymntCurrVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            OperationBinding binding = this.getBindings().getOperationBinding("fetchandSetRemainingPaymntAmt");
            binding.getParamsMap().put("CurrId", (Integer) valueChangeEvent.getNewValue());
            binding.execute();
        }
    }

    public void setPmntAmtBind(RichInputText pmntAmtBind) {
        this.pmntAmtBind = pmntAmtBind;
    }

    public RichInputText getPmntAmtBind() {
        return pmntAmtBind;
    }

    public void paymodeVal(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() == null) {
            pmntAmtBind.setValue(new Number(0));
        } else if (valueChangeEvent.getNewValue().toString().equals("")) {
            pmntAmtBind.setValue(new Number(0));
        }
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setPaymentModeTabBVar(RichShowDetailItem paymentModeTabBVar) {
        this.paymentModeTabBVar = paymentModeTabBVar;
    }

    public RichShowDetailItem getPaymentModeTabBVar() {
        return paymentModeTabBVar;
    }

    public void setUseRefOrg(Boolean useRefOrg) {
        this.useRefOrg = useRefOrg;
    }

    public Boolean getUseRefOrg() {
        if (useRefOrg == null) {
            StringBuffer i = new StringBuffer("N");
            OperationBinding binding = this.getBindings().getOperationBinding("checkRefOrgApplied");
            if (binding != null) {
                binding.execute();
                Object object = binding.getResult();
                if (object != null) {
                    i = (StringBuffer) object;
                }
                if (i.toString().equals("Y")) {
                    useRefOrg = true;
                } else {
                    useRefOrg = false;
                }
            }

        }
        return useRefOrg;
    }

    public void setItmFormLayoutBind(RichPanelFormLayout itmFormLayoutBind) {
        this.itmFormLayoutBind = itmFormLayoutBind;
    }

    public RichPanelFormLayout getItmFormLayoutBind() {
        return itmFormLayoutBind;
    }

    public void setEnableOutstandingDtl(StringBuffer enableOutstandingDtl) {
        this.enableOutstandingDtl = enableOutstandingDtl;
    }

    public StringBuffer getEnableOutstandingDtl() {
        if (enableOutstandingDtl == null) {
            StringBuffer i = new StringBuffer("N");
            OperationBinding binding = this.getBindings().getOperationBinding("checkforProfileValues");
            binding.getParamsMap().put("colName", new StringBuffer("SHOW_CUST_OUT_DTL"));
            if (binding != null) {
                binding.execute();
                Object object = binding.getResult();
                if (object != null) {
                    i = (StringBuffer) object;
                }
                if (i.toString().equals("Y")) {
                    enableOutstandingDtl = new StringBuffer("Y");
                } else {
                    enableOutstandingDtl = new StringBuffer("N");
                }
            }
        }
        return enableOutstandingDtl;
    }

    public void pricePolicyVCL(ValueChangeEvent valueChangeEvent) {
        Object newValue = valueChangeEvent.getNewValue();
        if (newValue != null || !"".equals(newValue)) {
            OperationBinding policy = this.getBindings().getOperationBinding("insertPolicyEntry");
            policy.getParamsMap().put("policyId", new StringBuffer(newValue.toString()));
            policy.execute();
            Object result = policy.getResult();
            if (result.equals((Boolean) false)) {
                FacesMessage message = new FacesMessage("", "Inconsistant Data Found !");
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(valueChangeEvent.getComponent().getClientId(), message);
            }
        }
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(pricePolicyPgBind);
        //  System.out.println("Here in");

    }

    public void schemeVCL(ValueChangeEvent vce) {
        //schmIdVCE(valueChangeEvent);
        DCIteratorBinding parentIter = (DCIteratorBinding) getBindings().get("SlsSoItm1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();
        // System.out.println(" scheme value change Event  ---->>  " + vce.getNewValue());
        if (vce.getNewValue() != null) {

            //Commenting on 18 jun 2015, since duplicate validation on scheme item is not required.
            OperationBinding binding = ADFBeanUtils.getOperationBinding("isSchemeValid");
            binding.getParamsMap().put("schDocId", vce.getNewValue());
            binding.execute();
            Object object1 = binding.getResult();
            Boolean b = (object1 == null ? false : (Boolean) object1);
            if (b) {

                String schmId = vce.getNewValue().toString();
                //  System.out.println("Schm Id is----->" + schmId);

                StringBuffer i = new StringBuffer("N");
                OperationBinding ob = this.getBindings().getOperationBinding("checkforProfileValues");
                ob.getParamsMap().put("colName", new StringBuffer("USE_DISC_PLC"));
                if (ob != null) {
                    ob.execute();
                    Object object = ob.getResult();
                    if (object != null) {
                        i = (StringBuffer) object;
                    }
                    OperationBinding schBinding = ADFBeanUtils.getOperationBinding("getSchmTyp");
                    schBinding.getParamsMap().put("schmDocId", vce.getNewValue());
                    schBinding.execute();
                    Object object_2 = schBinding.getResult();
                    StringBuilder schmTyp = (object_2 == null ? new StringBuilder("") : (StringBuilder) object_2);
                    if (i.toString().equals("Y") && "A".equals(schmTyp.toString())) {
                        //System.out.println("Before show popup");
                        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
                        glblSchmId = (String) vce.getNewValue();
                        //  System.out.println("GlBlSchm Id : " + glblSchmId);
                        //Show PopUp which will ask whether to apply/Replace Price Policy with Scheme or not
                        showPopup(schemePopUpPgBind, true);

                    } else {
                        if (vce.getOldValue() != null) {
                            //      System.out.println(" Calling remove Scheme");
                            OperationBinding schRem = executeOperation("removeScheme");
                            schRem.getParamsMap().put("itmId", null);
                            schRem.getParamsMap().put("schmId", vce.getOldValue());
                            schRem.execute();
                            if (parentKey != null) {
                                //  System.out.println("Key before lot----->> " + parentKey);
                                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                            }
                        }
                        //schemeIdPgBind.setValue(null);
                        //AdfFacesContext.getCurrentInstance().addPartialTarget(schemeIdPgBind);

                        ob = executeOperation("insertIntoSchm");
                        ob.getParamsMap().put("schmId", vce.getNewValue());
                        ob.execute();
                        /* Commented on 15-June-15, 3:20, for scheme issues.*/
                        if (ob.getResult() != null) {

                        } else {
                            OperationBinding big =
                                this.getBindings().getOperationBinding("getAndSetDiscountForItmFromPolicy");
                            big.execute();
                            discValBind.setValue(big.getResult());

                        }
                        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
                        ADFBeanUtils.getOperationBinding("calcAndSetAscblVal").execute();

                        executeOperation("Execute12").execute();
                        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
                        if (parentKey != null) {
                            //  System.out.println("Key before lot----->> " + parentKey);
                            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                        }

                    }
                }
                if (vce.getNewValue() != vce.getOldValue()) {
                    if (parentKey != null) {
                        System.out.println("Key before lot----->> " + parentKey);
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                    this.schemeIdPgBind.setValue(vce.getNewValue());

                    AdfFacesContext.getCurrentInstance().addPartialTarget(schemeIdPgBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.itemTableBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(discTypItmBind);

                    AdfFacesContext.getCurrentInstance().addPartialTarget(this.itemTableBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(appliedSchemePgBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(appliedSchmRushOrdPgBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(viewSchmLinkPgBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itmFormLayoutBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);

                    AdfFacesContext.getCurrentInstance().addPartialTarget(discValBind); /* Commented on 15-June-15, 3:20, for scheme issues.*/
                    queueRefreshAction(); /** Queue Event to refresh the Item detail table */
                }

            } else {

                ADFModelUtils.showFacesMessage("This scheme is invalid for Current Sales Order !",
                                               "The Scheme item that exist with this scheme is already added to Sales Order. So you cannot select this scheme.",
                                               FacesMessage.SEVERITY_FATAL, vce.getComponent().getClientId());
                this.schemeIdPgBind.setValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(schemeIdPgBind);
            }


        } else {
            if (parentKey != null) {
                //  System.out.println("Key before lot----->> " + parentKey);
                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
            }
            glblSchmId = null;
            //  System.out.println(" Removing Scheme ------>>><<< ------  ");

            OperationBinding schRem = executeOperation("removeScheme");
            schRem.getParamsMap().put("itmId", null);
            schRem.getParamsMap().put("schmId", vce.getOldValue());
            schRem.execute();

            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            OperationBinding binding = this.getBindings().getOperationBinding("getAndSetDiscountForItmFromPolicy");
            binding.execute();
            discValBind.setValue(binding.getResult());
            ADFBeanUtils.getOperationBinding("calcAndSetAscblVal").execute();

            executeOperation("Execute12").execute();

            if (vce.getNewValue() != vce.getOldValue()) {
                if (parentKey != null) {
                    //   System.out.println("Key before lot----->> " + parentKey);
                    parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }
                this.schemeIdPgBind.setValue(vce.getNewValue());

                AdfFacesContext.getCurrentInstance().addPartialTarget(schemeIdPgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.itemTableBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(discTypItmBind);

                AdfFacesContext.getCurrentInstance().addPartialTarget(this.itemTableBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(appliedSchemePgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(appliedSchmRushOrdPgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(viewSchmLinkPgBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itmFormLayoutBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);

                AdfFacesContext.getCurrentInstance().addPartialTarget(discValBind); /* Commented on 15-June-15, 3:20, for scheme issues.*/

                queueRefreshAction(); /** Queue Event to refresh the Item detail table */
            }

        }


    }

    public void itmIdTransChnageAction(ValueChangeEvent vce) {
        // Add event code here...
        if (vce.getNewValue() != null) {
            String itmId = vce.getNewValue().toString();
            _log.info("Item Id is-->" + itmId);
            OperationBinding ob = executeOperation("getLatestItmPriceSobyItmId");
            ob.getParamsMap().put("ItmId", itmId);
            ob.execute();
            oldPriceDesc = null;
            _log.info("Item price is : " + ob.getResult());
            if (ob.getResult() != null && ((Number) ob.getResult()).compareTo(new Number(-3)) != 0) {
                itmPriceBind.setValue(ob.getResult());
                vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
                //this.getBindings().getOperationBinding("getAndSetDiscountForItmFromPolicy").execute();
                OperationBinding binding = this.getBindings().getOperationBinding("getAndSetDiscountForItmFromPolicy");
                binding.execute();
                Object o = binding.execute();
                //discValBind.setValue(o);
                ADFBeanUtils.getOperationBinding("calcAndSetAscblVal").execute();
                //AdfFacesContext.getCurrentInstance().addPartialTarget(discValBind);  /* Commented on 15-June-15, 3:20, for scheme issues.*/
                AdfFacesContext.getCurrentInstance().addPartialTarget(itmPriceBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itmIdBind); //itmFormLayoutBind
                AdfFacesContext.getCurrentInstance().addPartialTarget(itmFormLayoutBind);
                FacesMessage msg = new FacesMessage("Please enter Item Quantity !", "");
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(itmQtyBind.getClientId(), msg);
            } else {
                FacesMessage msg =
                    new FacesMessage("Unable to fetch Item Price according to Customer Price policy!",
                                     "Kindly check Customer Price policy !");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(vce.getComponent().getClientId(), msg);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
    }

    public void itmIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here... ValidateItmId
        /* if (object != null) {
            _log.info("Value of Item is-->" + object);
            OperationBinding ob = executeOperation("ValidateItmId");
            ob.getParamsMap().put("itmId", object.toString());
            ob.execute();
            if (ob.getResult() != null) {
                String flg = ob.getResult().toString();
                if ("Y".equalsIgnoreCase(flg)) {
                    String msg2 = resolvEl("#{bundle['MSG.427']}");
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                }
            }
        } */
        RichInputListOfValues comp = (RichInputListOfValues) uIComponent;
        if (!comp.isValid()) {
            //String msg2 = resolvEl("#{bundle['MSG.427']}");
            FacesMessage message2 = new FacesMessage("Invalid Item", "Please select a valid Item !");
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        } else if (object != null) {
            OperationBinding binding = getBindings().getOperationBinding("ValidateItmId");
            binding.getParamsMap().put("itmId", object.toString());
            Object execute = binding.execute();
            if (execute == null || execute.toString().equalsIgnoreCase("Y")) {
                FacesMessage msg = new FacesMessage("Item Already Exist");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            } else if (execute == null || execute.toString().equalsIgnoreCase("F")) {
                throw new ValidatorException(new FacesMessage("Selected Item is already a free Item !",
                                                              "Selected Item is a free Item in one of the Scheme selected for Item in Sales Order. You cannot select a Free Item in Sale Order !"));
            }
        }


    }

    public void setItmIssueSrBind(RichOutputLabel itmIssueSrBind) {
        this.itmIssueSrBind = itmIssueSrBind;
    }

    public RichOutputLabel getItmIssueSrBind() {
        return itmIssueSrBind;
    }

    public void setItmIdBind(RichInputListOfValues itmIdBind) {
        this.itmIdBind = itmIdBind;
    }

    public RichInputListOfValues getItmIdBind() {
        return itmIdBind;
    }

    public void fileDownloadAction(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        RichInputText bind = this.getFilePathBind();
        if (bind != null) {
            // System.out.println("come ----- 1");
            String path = bind.getValue().toString();


            //  System.out.println("path is:  " + path);
            try {
                FileInputStream in = new FileInputStream(path);
                //    System.out.println("Available bytes are:  " + in.available());
                if (in.available() <= 0) {
                    //      System.out.println("came in unavailable!");
                    FacesMessage msg = new FacesMessage("There is no data in the File !!");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return;
                }
                byte b[] = new byte[in.available()];
                int i = in.read(b);
                outputStream.write(b);

                outputStream.flush();
                outputStream.close();
                in.close();
                facesContext.getCurrentInstance().responseComplete();
            } catch (FileNotFoundException e) {
                //   System.out.println("File not found");
                FacesMessage msg = new FacesMessage("File Not Found in the Directory!!");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (IOException e) {
                //    System.out.println("IO Exception occur------");
                FacesMessage msg = new FacesMessage("The File is corrupted!!");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    public void setFilePathBind(RichInputText filePathBind) {
        this.filePathBind = filePathBind;
    }

    public RichInputText getFilePathBind() {
        return filePathBind;
    }

    /**
     * Method to execute Attach and Remarks Table on Tab Disclosure.
     * @param disclosureEvent
     */
    public void attachAndRemarksDiscloserList(DisclosureEvent disclosureEvent) {
        getBindings().getOperationBinding("Execute10").execute();
        getBindings().getOperationBinding("Execute11").execute();
    }

    public boolean callWorkFlowFunctions() {
        Boolean b = false;

        OperationBinding wfChk = this.getBindings().getOperationBinding("isWorkFlowAndUserValid");
        wfChk.execute();
        Object res = wfChk.getResult();
        // System.out.println("res" + res);
        if (res.equals((Object) false)) {

        } else if (res.equals((Object) true)) {
            Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            Integer usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
            /***************************Functions to implement WorkFlow in Sales Order*****************************************************************/
            //Get WorkFlow No
            OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
            WfnoOp.getParamsMap().put("SlocId", sloc_Id);
            WfnoOp.getParamsMap().put("CldId", cld_Id);
            WfnoOp.getParamsMap().put("OrgId", org_Id);
            WfnoOp.getParamsMap().put("DocNo", 21503);
            WfnoOp.execute();
            if (WfnoOp.getResult() != null) {
                WfNum = WfnoOp.getResult().toString();
            }
            //Get User Level
            OperationBinding usrLevelO = getBindings().getOperationBinding("getUsrLvl");
            usrLevelO.getParamsMap().put("SlocId", sloc_Id);
            usrLevelO.getParamsMap().put("CldId", cld_Id);
            usrLevelO.getParamsMap().put("OrgId", org_Id);
            usrLevelO.getParamsMap().put("UsrId", usr_Id);
            usrLevelO.getParamsMap().put("WfNo", WfNum);
            usrLevelO.getParamsMap().put("DocNo", 21503);
            usrLevelO.execute();
            if (usrLevelO.getResult() != null) {
                level = Integer.parseInt(usrLevelO.getResult().toString());
            }

            // Insert line in SLS$SO$WF$TXN
            OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
            insTxnOp.getParamsMap().put("SlocId", sloc_Id);
            insTxnOp.getParamsMap().put("CldId", cld_Id);
            insTxnOp.getParamsMap().put("OrgId", org_Id);
            insTxnOp.getParamsMap().put("DocNo", 21503);
            insTxnOp.getParamsMap().put("WfNo", WfNum);
            insTxnOp.getParamsMap().put("usr_idFrm", usr_Id);
            insTxnOp.getParamsMap().put("usr_idTo", usr_Id);
            insTxnOp.getParamsMap().put("levelFrm", level);
            insTxnOp.getParamsMap().put("levelTo", level);
            insTxnOp.getParamsMap().put("action", action);
            insTxnOp.getParamsMap().put("remark", remark);
            insTxnOp.getParamsMap().put("amount", 0);
            insTxnOp.execute();
            b = true;
            if (insTxnOp.getResult() != null) {
                res = Integer.parseInt(insTxnOp.getResult().toString());
            }
        }
        return b;
    }


    public void setShowItmDesc(StringBuffer showItmDesc) {
        this.showItmDesc = showItmDesc;
    }

    public StringBuffer getShowItmDesc() {
        //System.out.println("ShowItmDesc: "+showItmDesc);
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

    public void ItmPriceVCL(ValueChangeEvent vce) {
        //validationsEnabled = false;
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        //this.getBindings().getOperationBinding("getAndSetDiscountForItmFromPolicy").execute();
        OperationBinding binding = this.getBindings().getOperationBinding("getAndSetDiscountForItmFromPolicy");
        binding.execute();

        /*OperationBinding operationBinding = this.getBindings().getOperationBinding("ItemExistInSchmOrNot");
        operationBinding.execute();
        Object res = operationBinding.getResult();
         Commented on 15-June-15, 3:20, for scheme issues.*/
        /* if (res != null && (Boolean) res) {
            discValBind.setValue(new Number(0));
        } else {
            Object o = binding.execute();
            discValBind.setValue(o);
        }
*/
        ADFBeanUtils.getOperationBinding("calcAndSetAscblVal").execute();
        ADFBeanUtils.getOperationBinding("insertDiscountForItem").execute();
        //AdfFacesContext.getCurrentInstance().addPartialTarget(discValBind);  /* Commented on 15-June-15, 3:20, for scheme issues.*/
        _log.info("Item Price VCL Ran");
        //validationsEnabled = true;
    }

    public void exportToExcelACTION(ActionEvent actionEvent) {
        /* OperationBinding binding = this.getBindings().getOperationBinding("getItemRows");
        binding.execute();
        Row[] r = (Row[])binding.getResult();
         */


        this.getBindings().getOperationBinding("exportToExcel").execute();
        // System.out.println("In the bean");
    }

    public void setExportLink(String exportLink) {
        this.exportLink = exportLink;
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
        //  System.out.println("Export link :" + exportLink);
        return exportLink;
    }

    public List getSuggestions(String input) {

        // create a new list to hold matching items
        List<SelectItem> items = new ArrayList<SelectItem>();
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getSuggestedItemDesc");
        binding.getParamsMap().put("itmStr", input);
        binding.execute();
        Object object = binding.getResult();
        // System.out.println("Item Query Executed");
        if (object != null) {
            for (String item : (ArrayList<String>) object) {
                items.add(new SelectItem(item));
            }
        }
        // loop through the suggestions list
        /* for (SelectItem item : suggestions) {
            // check if each item contains the input string
            // convert both to upper case to make a case insensitive comparison
            // you could also do startsWith() or other String comparison methods
            // here
            if (item.getLabel().toUpperCase().contains(input.toUpperCase())) {
                // if the item contains our input string, add it to our matching
                // items list
                items.add(item);
            }
        } */

        // return the matching items
        return items;
    }

    public String ImportExcelAction() {
        // Add event code here...
        //  System.out.println("Hello java");
        OperationBinding operationBinding = this.getBindings().getOperationBinding("checkIfCoaExistsForSelectedEo");
        operationBinding.execute();
        if (operationBinding.getResult().equals((Object) true)) {
            OperationBinding binding = this.getBindings().getOperationBinding("doPoExist");
            binding.execute();
            Object object = binding.getResult();
            _log.info("Result is : " + object);
            if (object.equals((Object) false)) {
                if (checkMandateFieldFoeExcel().equalsIgnoreCase("Y")) {
                    this.app_mode = "A";
                    return "upload_excel";
                }
            } else {
                FacesMessage errMsg =
                    new FacesMessage("Purchase Order have already been created. You cannot add another Item.");
                errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, errMsg);
            }
        } else {
            FacesMessage errMsg =
                new FacesMessage("The selected Entity is Prospect. Please Convert it to Customer before adding items !");
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, errMsg);
        }

        return null;
    }

    public String checkMandateFieldFoeExcel() {
        String retVal = "N";
        if (ordTypeBind.getValue() != null) {
            if (eoNmTransBind.getValue() != null) {
                if (currIdSpBind.getValue() != null) {
                    if (ordDateBind.getValue() != null) {
                        if (soBasisBind.getValue() != null) {
                            if ((Integer.parseInt(ordTypeBind.getValue().toString()) == 310) ||
                                (Integer.parseInt(ordTypeBind.getValue().toString()) != 310 &&
                                 dlvAddsBind.getValue() != null && !("".equals(dlvAddsBind.getValue())))) {
                                //     System.out.println("value of address in valid.....");
                                _log.info("Address valid.");
                                if (soBasisBind.getValue().equals(391)) {
                                    retVal = "Y";
                                }
                                Object quotId = null;
                                OperationBinding binding = this.getBindings().getOperationBinding("isQuotIdNull");
                                binding.execute();
                                if (binding.getResult() != null) {
                                    quotId = binding.getResult();
                                }
                                //   System.out.println("so type is: " + soBasisBind.getValue() + "\t" + quotId);
                                if (soBasisBind.getValue().equals(390) && !(Boolean) quotId) {
                                    retVal = "Y";
                                }
                                if (soBasisBind.getValue().equals(388) && templateIdBind.getValue() != null) {
                                    retVal = "Y";
                                }
                                if (soBasisBind.getValue().equals(389) && refSoDocIdBind.getValue() != null) {
                                    retVal = "Y";
                                }
                                //  System.out.println("Warehouse-" + whIdSoBind.getValue());
                                if (resolvEl("#{pageFlowScope.GLBL_ORG_WH_CHK}") != null) {
                                    isWhUsed = resolvEl("#{pageFlowScope.GLBL_ORG_WH_CHK}");
                                }
                                if ("N".equalsIgnoreCase(isWhUsed)) {
                                    retVal = "Y";
                                } else {
                                    if (whIdSoBind.getValue() != null) {
                                        _log.info("Inside if");
                                        retVal = "Y";
                                    } else {
                                        _log.info("Inside else Order Type is-" + ordTypeBind.getValue());
                                        if (Integer.parseInt(ordTypeBind.getValue().toString()) == 309 ||
                                            Integer.parseInt(ordTypeBind.getValue().toString()) == 310) {
                                            // FacesMessage reqMsg = new FacesMessage("Warehouse is Mandatory");
                                            FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.909']}"));
                                            // reqMsg.setDetail("You must enter a value, As you have selected Cash-Sale or Rush Order");
                                            reqMsg.setDetail(resolvEl("#{bundle['MSG.911']}"));
                                            reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                            FacesContext.getCurrentInstance().addMessage(whIdSoBind.getClientId(),
                                                                                         reqMsg);
                                            retVal = "N";
                                        } else {
                                            retVal = "Y";
                                        }
                                    }
                                }
                                //  System.out.println("Valid Up to is-" + validUpToDtBind.getValue());
                                if (validUpToDtBind.getValue() != null) {

                                } else if (Integer.parseInt(ordTypeBind.getValue().toString()) == 311) {
                                    // FacesMessage reqMsg = new FacesMessage("Validity Date is Mandatory");
                                    FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.912']}"));
                                    //reqMsg.setDetail("You must enter a value, As you have selected Rate-Contract Order");
                                    reqMsg.setDetail(resolvEl("#{bundle['MSG.913']}"));
                                    reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext.getCurrentInstance().addMessage(validUpToDtBind.getClientId(), reqMsg);
                                    retVal = "N";
                                }

                            } else {
                                FacesMessage reqMsg = new FacesMessage("Delivery Address is required.");
                                //reqMsg.setDetail("You must enter a value, As you have selected Previous Order in SO Basis");
                                reqMsg.setDetail("Please select a Delivery Address !");
                                reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext.getCurrentInstance().addMessage(null, reqMsg);
                            }
                        } else {
                            // FacesMessage reqMsg = new FacesMessage("SO Basis is Manadatory");
                            FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.914']}"));
                            // reqMsg.setDetail("You must enter a value");
                            reqMsg.setDetail(resolvEl("#{bundle['MSG.117']}"));
                            reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext.getCurrentInstance().addMessage(soBasisBind.getClientId(), reqMsg);
                        }

                    } else {
                        //   FacesMessage reqMsg = new FacesMessage("Order Date is Manadatory");
                        FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.917']}"));
                        // reqMsg.setDetail("You must enter a value");
                        reqMsg.setDetail(resolvEl("#{bundle['MSG.117']}"));
                        reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(ordDateBind.getClientId(), reqMsg);
                    }
                } else {
                    //  FacesMessage reqMsg = new FacesMessage("Specific Currency is Manadatory");
                    FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.918']}"));
                    // reqMsg.setDetail("You must enter a value");
                    reqMsg.setDetail(resolvEl("#{bundle['MSG.117']}"));
                    reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(currIdSpBind.getClientId(), reqMsg);
                }

            } else {
                // FacesMessage reqMsg = new FacesMessage("Customer Name is Manadatory");
                FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.919']}"));
                // reqMsg.setDetail("You must enter a value");
                reqMsg.setDetail(resolvEl("#{bundle['MSG.117']}"));
                reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(eoNmTransBind.getClientId(), reqMsg);

            }
        } else {
            //  FacesMessage reqMsg = new FacesMessage("Order Type is Manadatory");
            FacesMessage reqMsg = new FacesMessage(resolvEl("#{bundle['MSG.921']}"));
            // reqMsg.setDetail("You must enter a value");
            reqMsg.setDetail(resolvEl("#{bundle['MSG.117']}"));
            reqMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(ordTypeBind.getClientId(), reqMsg);
        }

        return retVal;
    }

    public void discValVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        //  System.out.println(" @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@22  Indside discount value Change Listner ");

        ADFBeanUtils.getOperationBinding("calcAndSetAscblVal").execute();
    }

    public void discValTypVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        ADFBeanUtils.getOperationBinding("calcAndSetAscblVal").execute();
    }

    public void setSchemeItemPopUpPgBind(RichPopup schemeItemPopUpPgBind) {
        this.schemeItemPopUpPgBind = schemeItemPopUpPgBind;
    }

    public RichPopup getSchemeItemPopUpPgBind() {
        return schemeItemPopUpPgBind;
    }

    public void setAppliedSchemePgBind(RichLink appliedSchemePgBind) {
        this.appliedSchemePgBind = appliedSchemePgBind;
    }

    public RichLink getAppliedSchemePgBind() {
        return appliedSchemePgBind;
    }

    public void ChangeItemNameVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            AdfFacesContext.getCurrentInstance().addPartialTarget(schemeDataTablePgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(schemeNameTablePgBind);
        }
    }

    public void setSchemeDataTablePgBind(RichTable schemeDataTablePgBind) {
        this.schemeDataTablePgBind = schemeDataTablePgBind;
    }

    public RichTable getSchemeDataTablePgBind() {
        return schemeDataTablePgBind;
    }

    public void setSchemeNameTablePgBind(RichOutputText schemeNameTablePgBind) {
        this.schemeNameTablePgBind = schemeNameTablePgBind;
    }

    public RichOutputText getSchemeNameTablePgBind() {
        return schemeNameTablePgBind;
    }

    public void FreeAutoIssueItemAL(ActionEvent actionEvent) {
        executeOperation("IssueAutoItemFree").execute();
        // System.out.println("====================== Free auto issue Event");
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotFreeSchemItemBinding);
    }

    public void SelectSrNoFrItmFreeAL(ActionEvent actionEvent) {
        executeOperation("FilterSrNoAsPerFreeItem").execute();
        // System.out.println("====================== Free Sr no issue Event");
        showPopup(selectSrNoFreePopUpBind, true);
    }

    public void SelectFreeItmSrNoDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            DCIteratorBinding parentIter = (DCIteratorBinding) getBindings().get("SlsSoItmSch1Iterator");
            Key parentKey = parentIter.getCurrentRow().getKey();
            _log.info("Key before Sr-" + parentKey);

            String errMsg = null;
            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = executeOperation("getTotalIssueQtySrFree");
            issueQty.execute();
            if (issueQty.getResult() != null) {
                issQty = (Number) issueQty.getResult();
                /*    _log.info("Orderqty bind-" + itmQtyIssueLblBind.getValue());
                if (itmQtyIssueLblBind.getValue() != null) {
                    pickQty = (Number)itmQtyIssueLblBind.getValue();
                } */
                _log.info("Orderqty bind-" + itmIssueSrFreeBind.getValue());
                if (itmIssueSrFreeBind.getValue() != null) {
                    pickQty = (Number) itmIssueSrFreeBind.getValue();
                }
                _log.info("IssueQty-" + issQty + "OrderQty-" + pickQty);
                if (issQty.compareTo(0) == 0) {
                    // errMsg = "Please select quantity to issue";
                    errMsg = resolvEl("#{bundle['MSG.941']}");
                    FacesMessage msg = new FacesMessage(errMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else if (issQty.compareTo(pickQty) != 0) {
                    // errMsg = "Issued Quantity is not equal to Ordered Quantity";
                    errMsg = resolvEl("#{bundle['MSG.947']}");
                    FacesMessage msg = new FacesMessage(errMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    executeOperation("InsertIntoPickItmSrForFree").execute();
                    executeOperation("Execute1").execute();
                    executeOperation("Execute2").execute();
                    executeOperation("Execute3").execute();
                    executeOperation("Execute").execute();
                    if (parentKey != null) {
                        _log.info("Key before lot-" + parentKey);
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(srNoFreeSchemItemBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotFreeSchemItemBinding);
    }

    public void setSelectSrNoFreePopUpBind(RichPopup selectSrNoFreePopUpBind) {
        this.selectSrNoFreePopUpBind = selectSrNoFreePopUpBind;
    }

    public RichPopup getSelectSrNoFreePopUpBind() {
        return selectSrNoFreePopUpBind;
    }

    public void setItmIssueSrFreeBind(RichOutputFormatted itmIssueSrFreeBind) {
        this.itmIssueSrFreeBind = itmIssueSrFreeBind;
    }

    public RichOutputFormatted getItmIssueSrFreeBind() {
        return itmIssueSrFreeBind;
    }

    public void SelectLotFrItmFreeAL(ActionEvent actionEvent) {
        executeOperation("FilterLotWhWiseFree").execute();
        //  System.out.println("====================== Free Lot issue Event");

        showPopup(selectFreeLotPopUpBind, true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotFreeSchemItemBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(binFreeSchemItemBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(srNoFreeSchemItemBinding);
    }

    public void setItmQtyIssueFreeBind(RichOutputFormatted itmQtyIssueFreeBind) {
        this.itmQtyIssueFreeBind = itmQtyIssueFreeBind;
    }

    public RichOutputFormatted getItmQtyIssueFreeBind() {
        return itmQtyIssueFreeBind;
    }

    public void setTotFreeStkLotBind(RichOutputText totFreeStkLotBind) {
        this.totFreeStkLotBind = totFreeStkLotBind;
    }

    public RichOutputText getTotFreeStkLotBind() {
        return totFreeStkLotBind;
    }

    public void IssueFreeQtyLotValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number issueQty = (Number) object;
            Number lotQty = new Number(0);
            if (totFreeStkLotBind.getValue() != null) {
                lotQty = (Number) totFreeStkLotBind.getValue();
                _log.info("Issue qty-" + issueQty + "lot qty-" + lotQty);
                if (issueQty.compareTo(lotQty) ==
                    1) {
                    //throw new ValidatorException(new FacesMessage("Invalid Quantity","Issue Quantity can not be more than available quantity"));
                    throw new ValidatorException(new FacesMessage(resolvEl("#{bundle['MSG.336']}"),
                                                                  resolvEl("#{bundle['MSG.1066']}")));
                }
            }
            if (issueQty.compareTo(0) ==
                -1) {
                //throw new ValidatorException(new FacesMessage("Invalid Quantity","Issue Quantity can not be negative"));
                throw new ValidatorException(new FacesMessage(resolvEl("#{bundle['MSG.336']}"),
                                                              resolvEl("#{bundle['MSG.1067']}")));
            }
        }
    }

    public void SelectFreeItmLotDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            DCIteratorBinding parentIter = (DCIteratorBinding) getBindings().get("SlsSoItmSch1Iterator");
            Key parentKey = parentIter.getCurrentRow().getKey();
            _log.info("Key before lot-" + parentKey);

            String errMsg = null;
            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = executeOperation("getTotalIssueQtyFreeLot");
            issueQty.execute();
            if (issueQty.getResult() != null) {
                issQty = (Number) issueQty.getResult();
                _log.info("pickqty bind-" + itmQtyIssueFreeBind.getValue());
                if (itmQtyIssueFreeBind.getValue() != null) {
                    pickQty = (Number) itmQtyIssueFreeBind.getValue();
                }
                _log.info("IssueQty-" + issQty + "PickedQty-" + pickQty);
                if (issQty.compareTo(0) == 0) {
                    // errMsg = "Please select quantity to issue";
                    errMsg = resolvEl("#{bundle['MSG.941']}");
                    FacesMessage msg = new FacesMessage(errMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else if (issQty.compareTo(pickQty) != 0) {
                    //  errMsg = "Issued Quantity is not equal to Ordered Quantity";
                    errMsg = resolvEl("#{bundle['MSG.947']}");
                    FacesMessage msg = new FacesMessage(errMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    executeOperation("InsertIntoSoItmLotFree").execute();
                    executeOperation("Execute1").execute();
                    executeOperation("Execute").execute();
                    if (parentKey != null) {
                        _log.info("Key before lot-" + parentKey);
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotFreeSchemItemBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(binFreeSchemItemBinding);
    }

    public void setSelectFreeLotPopUpBind(RichPopup selectFreeLotPopUpBind) {
        this.selectFreeLotPopUpBind = selectFreeLotPopUpBind;
    }

    public RichPopup getSelectFreeLotPopUpBind() {
        return selectFreeLotPopUpBind;
    }

    public void SelectLotBinFrItmFreeAL(ActionEvent actionEvent) {
        OperationBinding ob = ADFBeanUtils.findOperation("FilterFreeBinWhWise");
        ob.getParamsMap().put("itmId", actionEvent.getComponent().getAttributes().get("itmId"));
        ob.execute();
        //  System.out.println("====================== Free Bin issue Event");
        showPopup(selectLotBinFreePopUpBind, true);
    }

    public void setItmQtyIssueFreeLblBind(RichOutputFormatted itmQtyIssueFreeLblBind) {
        this.itmQtyIssueFreeLblBind = itmQtyIssueFreeLblBind;
    }

    public RichOutputFormatted getItmQtyIssueFreeLblBind() {
        return itmQtyIssueFreeLblBind;
    }

    public void setTotStkLotBinFreeBind(RichOutputText totStkLotBinFreeBind) {
        this.totStkLotBinFreeBind = totStkLotBinFreeBind;
    }

    public RichOutputText getTotStkLotBinFreeBind() {
        return totStkLotBinFreeBind;
    }

    public void IssueQtyLotBinFreeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number issueQty = (Number) object;
            BigDecimal lotQty = new BigDecimal(0);
            BigDecimal zero = new BigDecimal(0);
            if (totStkLotBinFreeBind.getValue() != null) {
                lotQty = (BigDecimal) totStkLotBinFreeBind.getValue();
                _log.info("Issue qty-" + issueQty + "lot qty-" + lotQty);
                if (issueQty.compareTo(lotQty) ==
                    1) {
                    //throw new ValidatorException(new FacesMessage("Invalid Quantity","Issue Quantity can not be more than available quantity"));
                    throw new ValidatorException(new FacesMessage(resolvEl("#{bundle['MSG.336']}"),
                                                                  resolvEl("#{bundle['MSG.1066']}")));
                }
            }
            if (issueQty.compareTo(zero) ==
                -1) {
                // throw new ValidatorException(new FacesMessage("Invalid Quantity","Issue Quantity can not be negative"));
                throw new ValidatorException(new FacesMessage(resolvEl("#{bundle['MSG.336']}"),
                                                              resolvEl("#{bundle['MSG.1067']}")));
            }
        }

    }

    public void setSelectLotBinFreePopUpBind(RichPopup selectLotBinFreePopUpBind) {
        this.selectLotBinFreePopUpBind = selectLotBinFreePopUpBind;
    }

    public RichPopup getSelectLotBinFreePopUpBind() {
        return selectLotBinFreePopUpBind;
    }

    public void SelectItmLotBinFreeDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            DCIteratorBinding parentIter = (DCIteratorBinding) getBindings().get("SlsSoItmSch1Iterator");
            Key parentKey = parentIter.getCurrentRow().getKey();
            _log.info("Key before Bin-" + parentKey);

            String errMsg = null;
            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = executeOperation("getTotalIssueQtyBinFree");
            issueQty.execute();
            if (issueQty.getResult() != null) {
                issQty = (Number) issueQty.getResult();
                _log.info("Orderqty bind-" + itmQtyIssueFreeLblBind.getValue());
                if (itmQtyIssueFreeLblBind.getValue() != null) {
                    pickQty = (Number) itmQtyIssueFreeLblBind.getValue();
                }
                _log.info("IssueQty-" + issQty + "PickedQty-" + pickQty);
                if (issQty.compareTo(0) == 0) {
                    // errMsg = "Please select quantity to issue";
                    errMsg = resolvEl("#{bundle['MSG.941']}");
                    FacesMessage msg = new FacesMessage(errMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else if (issQty.compareTo(pickQty) != 0) {
                    // errMsg = "Issued Quantity is not equal to Ordered Quantity";
                    errMsg = resolvEl("#{bundle['MSG.947']}");
                    FacesMessage msg = new FacesMessage(errMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    executeOperation("InsertIntoSoItmBinFree").execute();
                    executeOperation("Execute1").execute();
                    executeOperation("Execute2").execute();
                    executeOperation("Execute").execute();
                    if (parentKey != null) {
                        _log.info("Key before lot-" + parentKey);
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(srNoFreeSchemItemBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotFreeSchemItemBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(binFreeSchemItemBinding);
    }

    public void ViewFreeLotAL(ActionEvent actionEvent) {
        this.setSchmFacetName("Lot");
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem) actionEvent.getComponent();
        rcnv.setVisited(true);
        executeOperation("Execute1").execute();
    }

    public void ViewFreeBinAction(ActionEvent actionEvent) {
        this.setSchmFacetName("Bin");
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem) actionEvent.getComponent();
        rcnv.setVisited(true);
        executeOperation("Execute2").execute();
    }

    public void ViewFreeSerialsAL(ActionEvent actionEvent) {
        this.setSchmFacetName("SrNo");
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem) actionEvent.getComponent();
        rcnv.setVisited(true);
        executeOperation("Execute3").execute();
    }

    public void setAppliedSchmRushOrdPgBind(RichLink appliedSchmRushOrdPgBind) {
        this.appliedSchmRushOrdPgBind = appliedSchmRushOrdPgBind;
    }

    public RichLink getAppliedSchmRushOrdPgBind() {
        return appliedSchmRushOrdPgBind;
    }

    public void setViewSchmLinkPgBind(RichLink viewSchmLinkPgBind) {
        this.viewSchmLinkPgBind = viewSchmLinkPgBind;
    }

    public RichLink getViewSchmLinkPgBind() {
        return viewSchmLinkPgBind;
    }

    public void deliverySchdlAL(ActionEvent actionEvent) {
        executeOperation("generateAutoDlvSchdl").execute();
        //executeOperation("generateAutoPaymentSchdl").execute();
    }

    public void setSchemePopUpPgBind(RichPopup schemePopUpPgBind) {
        this.schemePopUpPgBind = schemePopUpPgBind;
    }

    public RichPopup getSchemePopUpPgBind() {
        return schemePopUpPgBind;
    }

    //Pop up for scheme discount
    public void SchemePolicyPopUpDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("Yes")) {
            //System.out.println("yes button is pressed");

            //System.out.println("schid is "+glblSchmId);
            if (glblSchmId != null) {
                //discValBind.setValue(StaticValue.NUMBER_ZERO);
                //executeOperation("removeScheme").execute();
                OperationBinding ob = executeOperation("insertIntoSchm");
                ob.getParamsMap().put("schmId", glblSchmId);
                ob.execute();
                //  discValBind.setValue(ob.getResult());  /* Commented on 15-June-15, 3:20, for scheme issues.*/
                // System.out.println("___________________________");
                ob = executeOperation("ItemPresentInSoItemOrNot");
                ob.getParamsMap().put("showErrorMessaage", true);
                ob.getParamsMap().put("changeDiscountValue", true);
                ob.execute();
                //AdfFacesContext.getCurrentInstance().addPartialTarget(this.itemTableBind);
                //ADFBeanUtils.getOperationBinding("calcAndSetAscblVal").execute();
            } else {
                FacesMessage msg = new FacesMessage("Unable to Insert Scheme Details.");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            glblSchmId = null;
            //System.out.println("No Button is pressed");
            schemePopUpPgBind.hide();
            dialogEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
            AdfFacesContext.getCurrentInstance().addPartialTarget(schemeIdPgBind);

            OperationBinding schRem = executeOperation("removeScheme");
            schRem.getParamsMap().put("itmId", null);
            schRem.getParamsMap().put("schmId", null);
            schRem.execute();
            schemeIdPgBind.setValue(null);
            OperationBinding binding = this.getBindings().getOperationBinding("getAndSetDiscountForItmFromPolicy");
            binding.execute();
            /* Commented on 15-June-15, 3:20, for scheme issues.*/
            /* Object o = binding.getResult();
            System.out.println(" discount after scheme removal *************** == " + o);
            discValBind.setValue(o);
            AdfFacesContext.getCurrentInstance().addPartialTarget(discValBind);
*/
            ADFBeanUtils.getOperationBinding("calcAndSetAscblVal").execute();
        }

        //  System.out.println("before refreshing data");
        executeOperation("Execute12").execute();
        //executeOperation("Execute").execute(); /* Commented on 15-June-15, 3:20, for scheme issues.*/
        queueRefreshAction(); /** Queue Event to refresh the Item detail table */
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmFormLayoutBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(discTypItmBind);
        // AdfFacesContext.getCurrentInstance().addPartialTarget(discValBind);  /* Commented on 15-June-15, 3:20, for scheme issues.*/
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(appliedSchemePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(appliedSchmRushOrdPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(viewSchmLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(schemeIdPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(orderTotCostBind);

    }

    public void setSchemeIdPgBind(RichSelectOneChoice schemeIdPgBind) {
        this.schemeIdPgBind = schemeIdPgBind;
    }

    public RichSelectOneChoice getSchemeIdPgBind() {
        return schemeIdPgBind;
    }

    public void setToolBarBinding(RichToolbar toolBarBinding) {
        this.toolBarBinding = toolBarBinding;
    }

    public RichToolbar getToolBarBinding() {
        return toolBarBinding;
    }

    public void getOldItemPricePopACTION(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("fetchOldPrice").execute();
        //new ActionEvent(histLinkBind).queue();
        //showPopup(oldPricePopUpBind, true);

        _log.info("History View Executed.");
    }

    public void setOldPricePopUpBind(RichPopup oldPricePopUpBind) {
        this.oldPricePopUpBind = oldPricePopUpBind;
    }

    public RichPopup getOldPricePopUpBind() {
        return oldPricePopUpBind;
    }

    public void setHistLinkBind(RichLink histLinkBind) {
        this.histLinkBind = histLinkBind;
    }

    public RichLink getHistLinkBind() {
        return histLinkBind;
    }
    private StringBuilder oldPriceDesc = null;

    public StringBuilder getOldPriceDesc() {
        if (oldPriceDesc == null) {
            ADFBeanUtils.getOperationBinding("fetchOldPrice").execute();
            oldPriceDesc = new StringBuilder("Old Item Price");
        }
        return oldPriceDesc;
    }

    /**
     *
     *Method to set the price based on converstion factor
     * @param actionEvent
     */
    public void selectOldPriceACTION(ActionEvent actionEvent) {
        Object newPriceO = actionEvent.getComponent().getAttributes().get("newPrice");
        if (newPriceO != null) {

            OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("setNewItmPrice");
            operationBinding.getParamsMap().put("price", newPriceO);
            operationBinding.execute();

            itmPriceBind.setValue(operationBinding.getResult());

            actionEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
            //this.getBindings().getOperationBinding("getAndSetDiscountForItmFromPolicy").execute();
            OperationBinding binding = this.getBindings().getOperationBinding("getAndSetDiscountForItmFromPolicy");
            binding.execute();
            Object o = binding.getResult();
            //discValBind.setValue((o == null ? StaticValue.NUMBER_ZERO : (Number) o));  /* Commented on 15-June-15, 3:20, for scheme issues.*/
            ADFBeanUtils.getOperationBinding("calcAndSetAscblVal").execute();

            AdfFacesContext.getCurrentInstance().addPartialTarget(itmPriceBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmIdBind); //itmFormLayoutBind
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmFormLayoutBind);
            //  AdfFacesContext.getCurrentInstance().addPartialTarget(discValBind);  /* Commented on 15-June-15, 3:20, for scheme issues.*/
            //AdfFacesContext.getCurrentInstance().addPartialTarget(discValBind);


            oldPricePopUpBind.hide();
        }
    }

    public Integer getEoID() {
        Integer i = -1;
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getCustomerId");
        if (binding != null) {
            binding.execute();
            Object h = binding.getResult();
            i = (h == null ? -1 : (Integer) h);
        }
        return i;
    }

    public void setEoNmBindVarToNull() {
        eoNmTransBind = null;
    }

    public void setPoModeToNull() {
        PoMode = null;
    }

    public void setItemNmInTabBind(RichOutputText itemNmInTabBind) {
        this.itemNmInTabBind = itemNmInTabBind;
    }

    public RichOutputText getItemNmInTabBind() {
        return itemNmInTabBind;
    }

    public void removeTaxACTION(ActionEvent actionEvent) {
        executeOperation("removeOrdTax").execute();
        orderTaxPopUpBind.hide();
        // ADFBeanUtils.findIterator("SlsSoItm1Iterator").executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailsTabBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmFormLayoutBind);
        //AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
    }

    public void schNmVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (itmPriceBind.getValue() != null && itmQtyBind.getValue() != null) {
                Number price = (Number) itmPriceBind.getValue();
                Number qty = (Number) itmQtyBind.getValue();
                if (price.compareTo(0) > 0 && qty.compareTo(0) > 0) {

                    // Commenting on 18 jun 2015, since validation on scheme item is not required.
                    /**
                    OperationBinding binding = ADFBeanUtils.getOperationBinding("isSchemeValid");
                    binding.getParamsMap().put("schDocId", object);
                    binding.execute();
                    Object object1 = binding.getResult();
                    Boolean b = (object1 == null ? false : (Boolean) object1);
                    if (!b) {
                        throw new ValidatorException(new FacesMessage("This scheme is invalid for Current Sales Order !",
                                                                      "The Scheme item that exist with this scheme is already added to Sales Order. So you cannot select this scheme"));
                    }*/


                } else {
                    throw new ValidatorException(new FacesMessage("This scheme Can not be applied on Sales Order !",
                                                                  "Either Item Quantity Or Item Price is ZERO ! Please Provide the Correct Details."));
                }
            }
        }
    }

    /**
     * Method to add Item Trf
     * @param actionEvent
     *
     *
     * -1 : Some Error Occured
     * 1 : Item is not Selected.
     * 2 : Duplicate Item is Selectheted.
     * 3 : Source Organisation is not Selected.
     * 4 : Warehouse Id Scource is not Selected.
     * 5 : Destination Organisation is not Selected.
     * 6 : Warehouse Id Destination is not Selected.
     * 7 : Source and Destination Warehouse Id are same.
     * 8 : Transfer Quantity should be greater than zero.
     * 9 : UOM is not defined for this Item.
     * 10 : UOM Conversion factor with the Base UOM is not Defined.
     */
    public void addTrfACTION(ActionEvent ace) {
        OperationBinding trfBinding = ADFBeanUtils.getOperationBinding("addItmToTrf");
        trfBinding.execute();
        Object o = trfBinding.getResult();
        Integer i = (o == null ? -1 : (Integer) o);
        if (i.equals(-1)) {
            ADFModelUtils.showFormattedFacesMessage("Transfer Order was not created due to some Error !",
                                                    "Please try again. </br> If the problem Persists, please contact ESS !",
                                                    FacesMessage.SEVERITY_ERROR);
        } else if (i.equals(1)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("itmDescTransId");
            ADFModelUtils.showFacesMessage("Item for Transfer Order have not been selected !", "Please select an Item.",
                                           FacesMessage.SEVERITY_ERROR, clientId.toString());
        } else if (i.equals(2)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("itmDescTransId");
            ADFModelUtils.showFacesMessage("Duplicate Item for Transfer Order have been selected ! Selected Item have already been selected for generating Transfer Order.",
                                           "Please select another Item.", FacesMessage.SEVERITY_ERROR,
                                           clientId.toString());
        } else if (i.equals(3)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("orgSrcDescTransId");
            ADFModelUtils.showFacesMessage("Source Organisation have not been selected !",
                                           "Please select source Organisation.", FacesMessage.SEVERITY_ERROR,
                                           clientId.toString());
        } else if (i.equals(4)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("whSrcDescTransId");
            ADFModelUtils.showFacesMessage("Source Warehouse have not been selected !",
                                           "Please select source Warehouse.", FacesMessage.SEVERITY_ERROR,
                                           clientId.toString());
        } else if (i.equals(5)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("orgDestDescTransId");
            ADFModelUtils.showFacesMessage("Destination Organisation have not been selected !",
                                           "Please select Destination Organisation.", FacesMessage.SEVERITY_ERROR,
                                           clientId.toString());
        } else if (i.equals(6)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("whDestDescTransId");
            ADFModelUtils.showFacesMessage("Destination Warehouse have not been selected !",
                                           "Please select Destination Warehouse.", FacesMessage.SEVERITY_ERROR,
                                           clientId.toString());
        } else if (i.equals(7)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("whDestDescTransId");
            ADFModelUtils.showFacesMessage("Source and Destination Warehouse cannot be same !",
                                           "Please select Different Source and Destination Warehouse.",
                                           FacesMessage.SEVERITY_ERROR, clientId.toString());
        } else if (i.equals(8)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("it142");
            ADFModelUtils.showFacesMessage("Transfer Quantity should be greater than Zero !",
                                           "Please select a valid Transfer Quantity.", FacesMessage.SEVERITY_ERROR,
                                           clientId.toString());
        } else if (i.equals(9)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("itmDescTransId");
            ADFModelUtils.showFacesMessage("UOM is not defined for this Item !", "Please define UOM for this Item.",
                                           FacesMessage.SEVERITY_ERROR, clientId.toString());
        } else if (i.equals(10)) {
            StringBuffer clientId = new StringBuffer(ace.getComponent().getClientId());
            clientId = new StringBuffer(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            clientId = clientId.append("itmDescTransId");
            ADFModelUtils.showFacesMessage("UOM Conversion factor with Base Unit is not defined for this Item !",
                                           "Please define UOM Conversion factor for this Item.",
                                           FacesMessage.SEVERITY_ERROR, clientId.toString());
        }
    }


    public void trfQtyVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (!ADFBeanUtils.isPrecisionValid((Number) object)) {
                throw new ValidatorException(new FacesMessage("The Precision is Invalid !",
                                                              "Please enter Valid Precision."));
            }
            if (ADFBeanUtils.isNumberNegative((Number) object)) {
                throw new ValidatorException(new FacesMessage("The Amount is Nagative !",
                                                              "Please enter Valid Amount."));
            }
        }
    }

    /**
     * Validator for External DocNo and External DocDt
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void extrnalDocNoVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding binding = ADFBeanUtils.getOperationBinding("isDocNoValid");
            binding.getParamsMap().put("docNoO", object.toString());
            binding.getParamsMap().put("docDtO", null);
            binding.execute();
            Object o = binding.getResult();
            Boolean b = (o == null ? false : (Boolean) o);
            // System.out.println(b + " result value in validator");
            if (!b) {
                throw new ValidatorException(new FacesMessage("The Document No. is duplicate for the given External Document Date.",
                                                              "Please enter a valid document No. and document Date."));
                /*
                throw new ValidatorException(new FacesMessage("The Document No. is duplicate.",
                                                              "Please enter a valid document No. since this Document No. Exist"));
            */
            }

        }
    }

    public void setItemTablePanelCollBinding(RichPanelCollection itemTablePanelCollBinding) {
        this.itemTablePanelCollBinding = itemTablePanelCollBinding;
    }

    public RichPanelCollection getItemTablePanelCollBinding() {
        return itemTablePanelCollBinding;
    }

    public void setItemTablePanelBoxBind(RichPanelBox itemTablePanelBoxBind) {
        this.itemTablePanelBoxBind = itemTablePanelBoxBind;
    }

    public RichPanelBox getItemTablePanelBoxBind() {
        return itemTablePanelBoxBind;
    }

    /**
     * Method to Release stock after Approval of Rush Order and Cash Sales Order
     * @param actionEvent
     */
    public void releaseACE(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("isStockAvailableForItems");
        binding.execute();
        Object object = binding.getResult();
        Integer i = (object == null ? -1 : (Integer) object);
        if (i == 0) {
            setEditLotMode(new StringBuffer("R"));
        } else if (i == -1) {
            ADFModelUtils.showFormattedFacesMessage("There have been an error while checking Stock for Items in Current Order",
                                                    "Please try again.", FacesMessage.SEVERITY_ERROR);
        }
    }

    //Method to save sales order after all item has been released in case of rush or direct order.
    public void saveLotACE(ActionEvent actionEvent) {

        // executeOperation("Commit").execute();
        if (!isExiceValid()) { //check for exice organization

        } else if (callValidationForLot()) {
            //  System.out.println("All lot released " + callValidationForLot());
            OperationBinding ob = ADFBeanUtils.findOperation("updateLotStatus");
            ob.execute();
            Boolean chk = (Boolean) ob.getResult();
            // System.out.println(chk + " Value of retruend funcio");
            if (chk) {
                //System.out.println("there in side");
                saveButtonAction();
                setEditLotMode(new StringBuffer("L"));
                setReleaseMode(new StringBuffer("Y"));
            }
        }
    }

    public void setPaymentScheduleTable(RichTable paymentScheduleTable) {
        this.paymentScheduleTable = paymentScheduleTable;
    }

    public RichTable getPaymentScheduleTable() {
        return paymentScheduleTable;
    }

    public void vehicleNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {


    }
    private Timestamp todayDate = StaticValue.getTruncatedCurrDt();

    public void setTodayDate(Timestamp todayDate) {
        this.todayDate = todayDate;
    }

    public Timestamp getTodayDate() {
        return todayDate;
    }

    public void setSchemeItemTableBinding(RichPanelCollection schemeItemTableBinding) {
        this.schemeItemTableBinding = schemeItemTableBinding;
    }

    public RichPanelCollection getSchemeItemTableBinding() {
        return schemeItemTableBinding;
    }

    public void setLotFreeSchemItemBinding(RichTable lotFreeSchemItemBinding) {
        this.lotFreeSchemItemBinding = lotFreeSchemItemBinding;
    }

    public RichTable getLotFreeSchemItemBinding() {
        return lotFreeSchemItemBinding;
    }

    public void setBinFreeSchemItemBinding(RichTable binFreeSchemItemBinding) {
        this.binFreeSchemItemBinding = binFreeSchemItemBinding;
    }

    public RichTable getBinFreeSchemItemBinding() {
        return binFreeSchemItemBinding;
    }

    public void setSrNoFreeSchemItemBinding(RichTable srNoFreeSchemItemBinding) {
        this.srNoFreeSchemItemBinding = srNoFreeSchemItemBinding;
    }

    public RichTable getSrNoFreeSchemItemBinding() {
        return srNoFreeSchemItemBinding;
    }

    /**
     *Action that go to cost center page for Header details
     * @return
     */
    public String headCostCenterAction() {

        OperationBinding binding = executeOperation("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            return "headCc";
        } else {
            return null;
        }
    }


    /**
     *Action that go to cost center page for Line details
     * @return
     */
    public String costCenterAction() {
        OperationBinding binding = executeOperation("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            ADFBeanUtils.findOperation("updateCostCenterAmt").execute();
            return "costCenter";
        } else {

            return null;
        }
    }
    //To Delete all the delivery schedule
    public void deleteAllShipmentACE(ActionEvent actionEvent) {

        ADFBeanUtils.findOperation("deleteAllDeliverySchedule").execute();
        chkDlcSchdlItm = null;
        // System.out.println(" **************************************** ");
    }


    /**
     *Action event to create suggested order
     * @return
     */
    public String createSuggestedOrderAction() {

        OperationBinding checkAppTax = executeOperation("checkTaxApplOrNot");
        checkAppTax.execute();
        String appliedOrNot = checkAppTax.getResult().toString();
        _log.info("Applied or Not flag-->" + appliedOrNot);

        if ("N".equalsIgnoreCase(appliedOrNot)) {
            String curDocID = "N";
            OperationBinding docIdCur = executeOperation("getcurDocID");
            docIdCur.execute();
            if (docIdCur.getResult() != null) {
                curDocID = docIdCur.getResult().toString();
            }
            OperationBinding dispNo = executeOperation("generateDispDocNo");
            dispNo.execute();
            OperationBinding ob = executeOperation("populateSoToSoItmPo");
            // ob.getParamsMap().put("docId", curDocID);
            ob.execute();
            return "createPo";
        }
        return null;
        //MethodCall2
    }


    /***Method to group selected item in MM$SO$ITM$PO*/
    public void groupSugstdItmAction(ActionEvent actionEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("isItemsForSuggestedOrderSelected");
        binding.execute();
        Object object = binding.getResult();
        if (object.equals((Object) true)) {
            showPopup(priceCompareSoPopupBind, true);
        }
    }

    /**
     *dialoge listner for grouped selected
     * @param dialogEvent
     */
    public void priceCompareDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("Yes")) {
            executeOperation("groupSelectedItem").execute();
            this.treeOrTab = true;
            DCIteratorBinding parentIter = (DCIteratorBinding) getBindings().get("MMGrpHdrTemp1Iterator");
            parentIter.refresh(0);
            //expandTreeTable();
            AdfFacesContext.getCurrentInstance().addPartialTarget(treeTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(treeTabPanelCollBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soItmPoTableBind);
        }
    }

    /**
     *Generate Purchase order for grouped Item
     * @param actionEvent
     */
    public void generateDraftPoAction(ActionEvent actionEvent) {
        executeOperation("genDrftPurOrder").execute();
        setPoRemoveStae(false);
        this.treeOrTab = false;
        //setPoMode(true);
        // showPopup(generatedPoPopUpBind, true);
    }

    /**
     * Action to  go back to sales page from purchase order page
     * @return
     */
    public String saveSugggOrder() {
        if (getPoRemoveStae()) {
            executeOperation("rollbackSuggestedOrder").execute();
        }

        // executeOperation("executeViewObjects").execute();
        return "backToSo";
    }

    /**
     * @param actionEvent
     */
    public void exitAction(ActionEvent actionEvent) {
        executeOperation("Rollback").execute();
        this.canc_Flg = "N";
    }

    public void setLotItmIdBindVar(RichOutputText lotItmIdBindVar) {
        this.lotItmIdBindVar = lotItmIdBindVar;
    }

    public RichOutputText getLotItmIdBindVar() {
        return lotItmIdBindVar;
    }

    public void setLotItemIdBindVar(RichOutputText lotItemIdBindVar) {
        this.lotItemIdBindVar = lotItemIdBindVar;
    }

    public RichOutputText getLotItemIdBindVar() {
        return lotItemIdBindVar;
    }

    /** Method Action Event to refresh the Item detail table  for auto populating the scheme Item*/
    public void refreshAmountACE(ActionEvent actionEvent) {
        // Add event code here...
        DCIteratorBinding parentIter = (DCIteratorBinding) getBindings().get("SlsSoItm1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();
        ADFBeanUtils.getOperationBinding("calcAndSetAscblVal").execute();
        // executeOperation("itmDisclosureList").execute();
        ADFBeanUtils.findIterator("SlsSoItm1Iterator").executeQuery();

        if (parentKey != null) {
            //  System.out.println("Key before lot----->> " + parentKey);
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        }
    }

    /** Queue Event to refresh the Item detail table */
    public void queueRefreshAction() {

        ActionEvent ae = new ActionEvent(refreshItmDtlBinding);
        ae.queue();
    }

    public void setRefreshItmDtlBinding(RichLink refreshItmDtlBinding) {
        this.refreshItmDtlBinding = refreshItmDtlBinding;
    }

    public RichLink getRefreshItmDtlBinding() {
        return refreshItmDtlBinding;
    }

    /**
     * Discloser Listner for Sales Order Tracking
     * @param disclosureEvent
     */
    public void orderTrackingDiscList(DisclosureEvent disclosureEvent) {
        ADFBeanUtils.getOperationBinding("methodToSetVarsToPickTracking").execute();
    }

    public void setDiscValBind(RichInputText discValBind) {
        this.discValBind = discValBind;
    }

    public RichInputText getDiscValBind() {
        return discValBind;
    }

    //Method to show pop up for stock details and to set refresh condition
    public void showStocPopupContexListner(ContextInfoEvent contextInfoEvent) {
        setRefStockDetail(true);
        ADFBeanUtils.showPopup(stockDetailPopUpBind, true);
    }

    public void setStockDetailPopUpBind(RichPopup stockDetailPopUpBind) {
        this.stockDetailPopUpBind = stockDetailPopUpBind;
    }

    public RichPopup getStockDetailPopUpBind() {
        return stockDetailPopUpBind;
    }

    public void otherChargesTabDL(DisclosureEvent disclosureEvent) {
        ADFBeanUtils.findOperation("filterOtherCharges").execute();
    }

    public void whIdHeaderVCL(ValueChangeEvent valueChangeEvent) {
        System.out.println("in value change even----------------------------");
        if (valueChangeEvent.getNewValue() != null &&
            valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            OperationBinding bi = ADFBeanUtils.findOperation("resetProjectId");
            bi.getParamsMap().put("areTyp", "H");
            bi.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(projectIdBinding);
        }
    }

    public void setProjectIdBinding(RichSelectOneChoice projectIdBinding) {
        this.projectIdBinding = projectIdBinding;
    }

    public RichSelectOneChoice getProjectIdBinding() {
        return projectIdBinding;
    }

    public void itemBasisVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }


    public void setCancelOrderPopBinding(RichPopup cancelOrderPopBinding) {
        this.cancelOrderPopBinding = cancelOrderPopBinding;
    }

    public RichPopup getCancelOrderPopBinding() {
        return cancelOrderPopBinding;
    }

    public void removeItemWiseTax(ActionEvent acre) {
        DCIteratorBinding shpMntIter = (DCIteratorBinding) getBindings().get("SlsSoItm1Iterator");
        Row curRow = shpMntIter.getCurrentRow();
        Key key = curRow.getKey();
        executeOperation("removeItmTax").execute();
        if (key != null) {
            shpMntIter.setCurrentRowWithKey(key.toStringFormat(true));
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
        itemTaxPopUpBind.hide();

    }

    public void orderTaxExmptVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent != null) {
            exmptFlgOrdBind.processUpdates(FacesContext.getCurrentInstance());
            if (taxRuleIdOrderBind.getValue() != null) {
                Integer ruleId = Integer.parseInt(taxRuleIdOrderBind.getValue().toString());
                _log.info("Tax Rule id ord is-->" + ruleId);
                OperationBinding taxOrd = executeOperation("procTaxOrder");
                taxOrd.getParamsMap().put("ruleId", ruleId);
                taxOrd.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
            }
        }
    }

    public void itmWiseTaxExmptVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent != null) {
            exmptFlgItmBind.processUpdates(FacesContext.getCurrentInstance());
            if (taxRuleIdItmBind.getValue() != null) {
                Integer ruleId = Integer.parseInt(taxRuleIdItmBind.getValue().toString());
                _log.info("Tax Rule id is-->" + ruleId);
                OperationBinding taxOrd = executeOperation("procTaxItem");
                taxOrd.getParamsMap().put("ruleId", ruleId);
                taxOrd.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailsTabBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itmFormLayoutBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
            }
        }
    }

    public void setExmptFlgOrdBind(RichSelectBooleanCheckbox exmptFlgOrdBind) {
        this.exmptFlgOrdBind = exmptFlgOrdBind;
    }

    public RichSelectBooleanCheckbox getExmptFlgOrdBind() {
        return exmptFlgOrdBind;
    }

    public void itemWisTaxPopupCancelListner(PopupCanceledEvent popupCanceledEvent) {
        if (taxRuleIdItmBind.getValue() == null) {
            DCIteratorBinding shpMntIter = (DCIteratorBinding) getBindings().get("SlsSoItm1Iterator");
            Row curRow = shpMntIter.getCurrentRow();
            Key key = curRow.getKey();
            executeOperation("removeItmTax").execute();
            if (key != null) {
                shpMntIter.setCurrentRowWithKey(key.toStringFormat(true));
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
            itemTaxPopUpBind.hide();
        }
    }

    public void itemWiseTaxDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("Ok")) {
            ADFBeanUtils.findOperation("resetItemWiseTax").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailsTabBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmFormLayoutBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTaxLinkBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
        }
    }

    public void setItmTaxLinkBinding(RichLink itmTaxLinkBinding) {
        this.itmTaxLinkBinding = itmTaxLinkBinding;
    }

    public RichLink getItmTaxLinkBinding() {
        return itmTaxLinkBinding;
    }

    /**
     * Taxable amount set to zero on change of Asbl amount
     * @param valueChangeEvent
     */
    public void asblAmtVCL(ValueChangeEvent valueChangeEvent) {
        ADFBeanUtils.getOperationBinding("resetTaxableAmountOnAsblAmtChange").execute();
    }

    public void insertFlexFieldDataACE(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("createFlexiField").execute();
    }

    public void setOthrChargTabBinding(RichShowDetailItem othrChargTabBinding) {
        this.othrChargTabBinding = othrChargTabBinding;
    }

    public RichShowDetailItem getOthrChargTabBinding() {
        return othrChargTabBinding;
    }

    public void setPaymentSchduleFormBinding(RichPanelFormLayout paymentSchduleFormBinding) {
        this.paymentSchduleFormBinding = paymentSchduleFormBinding;
    }

    public RichPanelFormLayout getPaymentSchduleFormBinding() {
        return paymentSchduleFormBinding;
    }

    public void updateOrderACE(ActionEvent actionEvent) {
        OperationBinding checkPolicy = executeOperation("checkPolicyApplied");

        checkPolicy.execute();
        String res = checkPolicy.getResult().toString();
        if (res.equalsIgnoreCase("N")) {

            OperationBinding ob = executeOperation("populateQuotToSo");

            ob.execute();
        } else if (res != null) {
            OperationBinding obprice = executeOperation("getEoPricePlcMethod");
            obprice.execute();
            String eoPriceFlagVar = obprice.getResult().toString();
            if (eoPriceFlagVar.equalsIgnoreCase("N")) {

                OperationBinding ob = executeOperation("populateQuotToSo");

                ob.execute();

            } else {
                ADFModelUtils.showFormattedFacesMessage("The Selected Customer must be applied with a Price Policy ...",
                                                        "Please Select the price Policy for the Selected Customer Before Populating the Quoation..!",
                                                        FacesMessage.SEVERITY_ERROR);
            }

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(soBasisBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ordTypeBind);
        if (itmFormLayoutBind != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmFormLayoutBind);
        }
        if (itemTableBind != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemTableBind);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(othrChargTabBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(schemeIdPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxFlgRBPgBind);
    }

    public void updateDiscountVCL(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void discBasisContexInfList(ContextInfoEvent contextInfoEvent) {

        System.out.println(" *********************** Refreshing the discount basis view");
        ADFBeanUtils.findOperation("refreshDiscountBasis").execute();
        ADFBeanUtils.findIterator("ViewDiscSeqVO1Iterator").executeQuery();
        ADFBeanUtils.showPopup(discountBasisPopUpBind, true);
        // AdfFacesContext.getCurrentInstance().addPartialTarget(discBasisTableBinding);
    }

    public void setDiscBasisTableBinding(RichTable discBasisTableBinding) {
        this.discBasisTableBinding = discBasisTableBinding;
    }

    public RichTable getDiscBasisTableBinding() {
        return discBasisTableBinding;
    }

    public void setDiscountBasisPopUpBind(RichPopup discountBasisPopUpBind) {
        this.discountBasisPopUpBind = discountBasisPopUpBind;
    }

    public RichPopup getDiscountBasisPopUpBind() {
        return discountBasisPopUpBind;
    }

    public void setAmendOrdrConfrmPopUpBind(RichPopup amendOrdrConfrmPopUpBind) {
        this.amendOrdrConfrmPopUpBind = amendOrdrConfrmPopUpBind;
    }

    public RichPopup getAmendOrdrConfrmPopUpBind() {
        return amendOrdrConfrmPopUpBind;
    }

    public void eoCatgVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        OperationBinding binding = this.getBindings().getOperationBinding("setSlsSoToNull");
        binding.execute();
    }

    public void setEoCatgBinding(RichSelectOneChoice eoCatgBinding) {
        this.eoCatgBinding = eoCatgBinding;
    }

    public RichSelectOneChoice getEoCatgBinding() {
        return eoCatgBinding;
    }

    public void dlvSchdlQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...remainingQty
        if (object != null) {
            Number curQty = (Number) object;
            Object balQtyO = uIComponent.getAttributes().get("remainingQty");
            Number balqty = balQtyO == null ? new Number(0) : (Number) balQtyO;
            System.out.println(curQty + "<<<<< current qty");
            System.out.println(balqty + "<<<<< Balance qty");
            if (curQty.compareTo(balqty) == 1) {
                String msg = " Delivery Quantity must not be more than Balance Quantity ";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }

    }
}
