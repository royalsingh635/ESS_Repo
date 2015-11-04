package slspicpackshipapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.bean.StaticValue;
import adf.utils.model.ADFModelUtils;

import java.util.ArrayList;
import java.util.List;

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
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichMessage;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


/**
 * PickPack&Ship Managed Bean.
 * Contains all the business logic
 * */

public class PickPackShipBean {
    private String temp = null;
    private RichPopup usrDefindSrNoPopupBinding;
    private RichShowDetailItem packTabBind;

    public String getTemp() {
        if (temp == null) {
            OperationBinding binding = ADFBeanUtils.getOperationBinding("checkProfileValues");
            if (binding != null) {
                binding.getParamsMap().put("columnNm", "TEMP_COL");
                binding.execute();
                Object object = binding.getResult();
                if (object != null) {
                    temp = object.toString();
                }
            }
        }
        return temp;
    }

    private RowKeySet disclosedTreeRowKeySet = new RowKeySetImpl();
    private static ADFLogger _log = ADFLogger.createADFLogger(PickPackShipBean.class);
    private ArrayList rowList = new ArrayList();
    private RichSelectOneChoice currIdSpBinding;
    private RichTable srForItemTableBinding;
    private RichOutputText dualSrNoBinding;
    private RichOutputText arrivalPntPgBind;
    private RichOutputText deptPointPgBind;
    private RichPopup createNewRoutePopup;
    private RichPopup selectLotPopUpBind;
    private RichPopup selectBinPopUpBind;
    private RichPopup selectSrNoPopUpBind;
    private RichPopup selectPackItemPopupBind;
    private RichInputText itmIdSearchBind;
    private RichInputListOfValues packNoSearchBind;
    private RichTable packItmTableBind;
    private RichInputText pickedQtyBind;
    private RichOutputText pendQtyTreetabBind;
    private RichInputText selectQuantTransTreeTabBind;
    private RichInputText eoIdSearchShipBind;
    private RichSelectOneChoice dlvModeShipSearchBind;
    private RichSelectOneChoice whIdSearchShipBind;

    private String facetNm = "Lot";
    private RichSelectOneChoice dlvModeSrchMainBind;
    private RichInputDate pickDtSrchMainBind;
    private RichInputDate pickDtFrmShipBind;
    private RichInputDate pickDtToShipBind;

    private String pick_mode = "V";
    private String pack_mode = "V";
    private String faceNmPick = "Lot";
    public String e_Mode = "A";
    private RichPopup selectSRNoPopBindForPckMtrl;
    private RichLink savePckBind;
    private RichTable bindLotPckMtrl;
    private RichTable bindBinPckMtrl;
    private RichTable bindSrPckMtrl;
    private RichSelectBooleanCheckbox freezePrfBind;
    private RichCommandImageLink pckEditBind;
    private RichCommandImageLink pckAddBind;
    private RichSelectOneChoice whIdBinding;
    private RichInputText availQtyBindVar;
    private RichPopup freezePopup;
    private RichPopup cancelPopupbind;
    private String save_allocate_mode = "A";
    private RichSelectBooleanCheckbox cancelPackBind;
    private RichLink addPckMtrlBind;
    private RichLink cancelPckMtrlBind;
    private RichLink allocateLotBindVar;
    private RichLink autoIssueBindVar;
    private RichLink allocateBinBindVar;
    private RichLink allocateSerialBindVar;
    private String packFreezeMode = "N";
    private Boolean autoIssuMode = null;

    public Boolean getAutoIssuMode() {

        return autoIssuMode;
    }

    public String getPackFreezeMode() {
        return packFreezeMode;
    }

    public void setSave_allocate_mode(String save_allocate_mode) {
        this.save_allocate_mode = save_allocate_mode;
    }

    public String getSave_allocate_mode() {
        return save_allocate_mode;
    }

    public void setE_Mode(String e_Mode) {
        this.e_Mode = e_Mode;
    }

    public String getE_Mode() {
        return e_Mode;
    }
    //private RichOutputText pickQtyIssueBind;
    private RichSelectOneRadio goToDestBind;
    Integer selectChkShiCount = 0;
    private RichTable genShimntTabBind;
    private String destGo = "P";
    private RichOutputText itmAvlblQtyBind;
    private RichSelectBooleanCheckbox cancelPickFlgBind;
    private RichSelectBooleanCheckbox cancelShipFlgBind;
    private RichInputDate pickDateBind;
    private RichSelectOneChoice wareHousePickBind;
    private RichTable lotTabBind;
    private RichTable lotBinTabBind;
    private RichTable lotBinSrTabBind;
    private RichInputListOfValues dlvAddsIdBind;
    private RichInputText dispShipIdBinding;
    private RichSelectOneChoice uidBinding;
    private RichInputDate uidCreateDtBinding;
    private RichInputDate shipDtBinding;
    private RichSelectOneChoice whIdShipBinding;
    private RichPanelLabelAndMessage docIdShipBinding;
    private RichOutputText shipIdDocBinding;
    private RichInputListOfValues deliveryAddressShipmntSearchBind;
    private RichInputListOfValues pickIdBind;
    private RichInputListOfValues pickIdTrans;
    private RichMessage alertMessageBind;
    private RichPopup alertPopupBind;
    private RichDialog alertDialogBind;
    private RichPopup alertPopupStopBind;
    private RichPopup alertPopupNotifctionBind;
    private RichInputText packNoBind;
    private RichPopup transporterPopupBind;
    private RichInputText itmTotalStkQtyBVar;
    private RichTable bindItmTbl;
    private RichInputText dlvAddsBVar;
    private RichInputText binIssuQntbVar;
    private RichPopup delConfirmPopUp;
    private Boolean isPickSelect = true;
    private RichCommandImageLink saveShipLinkBVar;
    private RichCommandImageLink updateButtonBind;
    private RichSelectBooleanCheckbox cancelPickListFlag;
    private RichCommandImageLink saveButtonBind;
    private RichCommandImageLink editButtonBind;
    private RichCommandImageLink addButtonBind;
    private RichTable soItmTableBind;
    private RichPanelGroupLayout popResetPglBind;
    private RichPopup selectpopUpBindForPCkMtrl;
    private RichTable lotTablebindForPckMtrl;
    private RichPopup selectBinPopUpBindForPckMtrl;
    public String allocate_mode = "V";
    private String pickReservMode = "N";

    private Boolean isPackUsed = null;

    public Boolean getIsPackUsed() {

        if (isPackUsed == null) {
            OperationBinding b = ADFBeanUtils.getOperationBinding("isPackUsed");
            if (b != null) {
                b.execute();
                Object o = b.getResult();
                isPackUsed = (o == null ? false : (Boolean) o);
                //   System.out.println("*********************************  " + isPackUsed);
            }
        }
        return isPackUsed;
    }
    private Boolean enableCostCenter = null;

    //Setting values to check cost center aplicable
    public Boolean getEnableCostCenter() {
        //isCostCenterApplicable
        if (enableCostCenter == null) {
            OperationBinding b = ADFBeanUtils.getOperationBinding("isCostCenterApplicable");
            if (b != null) {
                b.execute();
                Object o = b.getResult();
                enableCostCenter = (o == null ? false : (Boolean) o);
                //   System.out.println("*********************************  " + enableCostCenter);
            }
        }
        return enableCostCenter;
    }

    public String getPickReservMode() {
        return pickReservMode;
    }
    private String wfIdForPick = null;

    public String getWfIdForPick() {
        return wfIdForPick;
    }

    public void setAllocate_mode(String allocate_mode) {
        this.allocate_mode = allocate_mode;
    }

    public String getAllocate_mode() {
        return allocate_mode;
    }
    private Boolean isExistTradingBranch = null;

    public void setIsExistTradingBranch(Boolean isExistTradingBranch) {
        this.isExistTradingBranch = isExistTradingBranch;
    }

    public Boolean getIsExistTradingBranch() {
        if (isExistTradingBranch == null) {
            OperationBinding binding = ADFBeanUtils.getOperationBinding("isBranchExiseTrading");
            binding.execute();
            Object object = binding.getResult();
            if (object != null) {
                isExistTradingBranch = (Boolean) object;
            }
        }
        return isExistTradingBranch;
    }

    private Boolean isSoSearchOnPick = null;

    public Boolean getIsSoSearchOnPick() {
        if (isSoSearchOnPick == null) {
            OperationBinding ob = ADFBeanUtils.findOperation("isSoSeachApplicable");
            ob.execute();
            if (ob.getResult() != null) {
                isSoSearchOnPick = (Boolean) ob.getResult();
            }
        }
        return isSoSearchOnPick;
    }

    public PickPackShipBean() {
    }


    /**
     * Method to resolve expression.
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
     * Method to resolve expression-returns Object.
     */
    public Object resolvElO(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext);
        return Message;
    }
    /***********************Generic Methods to be used in Whole Application ***************/

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

    public void addSoRowToLList(RowIterator ri, Key selectedNodeKey) {
        Row[] rows = ri.findByKey(selectedNodeKey, 1);
        if (rows != null) {
            for (Row row : rows) {
                reset_link = "D";
                OperationBinding ob = executeOperation("populateItemToPickList");
                ob.getParamsMap().put("curRow", row);
                ob.execute();
                // executeOperation("postChange").execute();  //14-july-2014 for reflecting the available quantity of item.
                // AdfFacesContext.getCurrentInstance().addPartialTarget(soTreeTableBind);
            }
        }

    }

    /**
     * this function is used at the time of removal of lot bin sr data by Rohit Chaturvedi
     */

    public String removeRowToLList(RowIterator ri, Key selectedNodeKey) {
        String rtnval = "Y";
        Row[] rows = ri.findByKey(selectedNodeKey, 1);
        // System.out.println("rows " + rows.length);
        if (rows != null) {
            // for (Row row : rows) {
            OperationBinding obb = executeOperation("removeLotbinSrData");
            obb.getParamsMap().put("currRow", rows);
            obb.execute();
            // System.out.println("ob.getResult() " + obb.getResult());
            if (obb.getResult() != null) {
                rtnval = (String) obb.getResult();
            }
            //}
        }
        //  System.out.println("NOTOK " + rtnval);
        return rtnval;
    }

    /*************** * *  *  * *  *Action Listeners defined for for page * * * ************/


    public void newPickListAction(ActionEvent actionEvent) {
        executeOperation("Createwithparameters").execute();
        OperationBinding filterTree = executeOperation("filterOrderData");
        filterTree.getParamsMap().put("pickDate", StaticValue.getCurrDtWidTimestamp());
        filterTree.execute();
        this.pick_mode = "A";
        this.header = "Z";
        isExistTradingBranch = null;

    }

    /**Method To Edit PickList
     * @param actionEvent
     */
    public void editPickListAction(ActionEvent actionEvent) {
        System.out.println("inside edit");
        OperationBinding b = ADFBeanUtils.getOperationBinding("isUserAllowedToEditPick");
        b.execute();
        Object o = b.getResult();
        Integer j = (o == null ? -1 : (Integer) o);

        executeOperation("executePickItmVo").execute();
        OperationBinding oFlg = executeOperation("shipmntCrtdOrNt");
        oFlg.execute();

        if (j == -1) {

        } else if (oFlg.getResult() != null && (oFlg.getResult().toString()).equalsIgnoreCase("N")) {
            System.out.println("indide edit");

            String eoNm = null;
            String wareh = null;
            Timestamp pickDt = null;
            OperationBinding bindingEo = this.getBindings().getOperationBinding("getPickEoNm");
            bindingEo.execute();
            if (bindingEo.getResult() != null) {
                eoNm = bindingEo.getResult().toString();
            }
            if (pickDateBind.getValue() != null) {
                pickDt = (Timestamp) pickDateBind.getValue();
            }
            if (wareHousePickBind.getValue() != null) {
                wareh = wareHousePickBind.getValue().toString();
            }
            _log.info("EoNm is-" + eoNm + " wareHou-" + wareh + "pick Date--" + pickDt);
            /* OperationBinding ob = executeOperation("filterTreeCustomerWise");
            ob.getParamsMap().put("eo_nm", eoNm);
            ob.execute();

            OperationBinding filterTree1 = executeOperation("filterTreeWarehouse");
            filterTree1.getParamsMap().put("whId", wareh);
            filterTree1.execute(); */

            /* OperationBinding binding = ADFBeanUtils.getOperationBinding("callRelsFunctionOnEdit");
            binding.execute();
            Object object = binding.getResult();
            Integer i = (object == null ? -1 : (Integer) object);
            if (i.equals(-1)) {
                ADFModelUtils.showFormattedFacesMessage("There have been some error during Releasing Stock !",
                                                        "There have been some error during Releasing Stock.<br/> Please try again!</br></br> If the problem persists, please contact ESS!",
                                                        FacesMessage.SEVERITY_ERROR);
            } else */
            {
                this.pick_mode = "Z";
                this.header = "Y";
                OperationBinding filterTree = executeOperation("filterOrderData");
                filterTree.getParamsMap().put("pickDate", pickDt);
                filterTree.execute();

                ADFBeanUtils.getOperationBinding("setSoDataOnEdit").execute();
            }
        } else {
            ADFModelUtils.showFormattedFacesMessage("Shipment have been generated for the current Pick !",
                                                    "Current Pick cannot be edited as Shipment have been generated for the currenct Pick",
                                                    FacesMessage.SEVERITY_INFO);
            //FacesMessage errMsg = new FacesMessage("Shipment is done for this picklist, can not edit");
            FacesMessage errMsg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.910"));

            errMsg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, errMsg);
        }

    }


    /**Method to populate Item in PickList
     * @param actionEvent
     */
    public void addItemToPickListAction(ActionEvent actionEvent) {
        if (dlvAddsIdBind.getValue() != null) {
            System.out.println("selectQuantTransTreeTabBind value  " + selectQuantTransTreeTabBind.getValue());
            if (selectQuantTransTreeTabBind.getValue() != null) {
                Number chkPendQtyMin = new Number(0);
                Number chkPendQtyMax = new Number(0);
                Number itmAvlQty = new Number(0);
                Number tlrncQty = new Number(0);
                Number hund = new Number(100);

                Number schdlQuant = (oracle.jbo.domain.Number) selectQuantTransTreeTabBind.getValue();
                Number pendQty = (oracle.jbo.domain.Number) pendQtyTreetabBind.getValue();

                if (itmAvlblQtyBind.getValue() != null) {
                    itmAvlQty = (Number) itmAvlblQtyBind.getValue();
                }
                Number qtyToChk = new Number(0);
                Row[] rows = null; //getSelectedNodeRowIterator().findByKey(getSelectedNodeRowKey(), 1);
                System.out.println("Total length of rows are:  " + rows.length + "------------");
                if (rows != null) {
                    for (Row row : rows) {
                        System.out.println("TlrncVal-" + row.getAttribute("TlrncQtyVal") + "and type-" +
                                           row.getAttribute("TlrncQtyType"));
                        if (row.getAttribute("TlrncQtyVal") != null) {
                            tlrncQty = (Number) row.getAttribute("TlrncQtyVal");
                        }
                        if (row.getAttribute("TlrncQtyType") != null &&
                            "A".equalsIgnoreCase(row.getAttribute("TlrncQtyType").toString())) {
                            System.out.println("tlrncQty " + tlrncQty + " pendQty " + pendQty);
                            chkPendQtyMin = pendQty.subtract(tlrncQty);
                            System.out.println(" chkPendQtyMin" + chkPendQtyMin);
                            chkPendQtyMax = pendQty.add(tlrncQty);
                        } else if (row.getAttribute("TlrncQtyType") != null &&
                                   "P".equalsIgnoreCase(row.getAttribute("TlrncQtyType").toString())) {

                            chkPendQtyMin = pendQty.subtract((pendQty.multiply(tlrncQty)).divide(hund));
                            chkPendQtyMax = pendQty.add((pendQty.multiply(tlrncQty)).divide(hund));
                        } else {
                            chkPendQtyMax = pendQty;
                            chkPendQtyMin = pendQty;
                        }
                    }
                }
                if (chkPendQtyMax.compareTo(itmAvlQty) == 1) {
                    qtyToChk = itmAvlQty;
                } else {
                    qtyToChk = chkPendQtyMax;
                }
                _log.info("MinQty aftr tlrnc-" + chkPendQtyMin + "max qty is-" + qtyToChk + "and schdl quant is-" +
                          schdlQuant);

                if (schdlQuant.compareTo(0) == -1 || schdlQuant.compareTo(qtyToChk) == 1 ||
                    schdlQuant.compareTo(0) == 0) {
                    FacesMessage errMsg =
                        /* new FacesMessage("Invalid Quantity", "Must be greater than 0 and less than " + qtyToChk +
                                     " (Pending Quantity)"); */
                        new FacesMessage(ADFModelUtils.resolvRsrc("MSG.336"),
                                         ADFModelUtils.resolvRsrc("MSG.915") + qtyToChk +
                                         ADFModelUtils.resolvRsrc("MSG.1707"));
                    errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(selectQuantTransTreeTabBind.getClientId(), errMsg);

                } else {
                    Row[] roow = null; //getSelectedNodeRowIterator().findByKey(getSelectedNodeRowKey(), 1);
                    System.out.println(" Length of roow : " + roow.length + "-----");
                    String i = "N";
                    if (roow.length > 0) {
                        System.out.println("Roow Count for lot data exist:  " + roow.length + " ----");
                        //  getBindings().getOperationBinding("Execute3").execute();
                        for (Row r1 : roow) {
                            //System.out.println("row  in for loop " + r1);
                            OperationBinding lotRowCount = executeOperation("isLotDataExist");
                            lotRowCount.getParamsMap().put("r", r1);
                            lotRowCount.execute();
                            i = (String) lotRowCount.getResult();
                            System.out.println("i " + i);

                        }
                    }
                    if ("Y".equalsIgnoreCase(i)) {
                        System.out.println("set if");
                        showPopup(delConfirmPopUp, true);
                    } else {
                        System.out.println("set else ");

                        addSoRowToLList(null, null); //(getSelectedNodeRowIterator(), getSelectedNodeRowKey());
                        selectQuantTransTreeTabBind.setValue(0);
                        //#{row.TransItmPickOrder == 305 or row.TransItmPickOrder == 306}
                        //ADFBeanUtils.getOperationBinding("autoIssueItmOnAdd").execute();
                    }
                }
            } else {
                //FacesMessage errMsg = new FacesMessage("Invalid Quantity");
                FacesMessage errMsg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.336"));
                errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                //errMsg.setDetail("Must Enter a Value");
                errMsg.setDetail(ADFModelUtils.resolvRsrc("MSG.117"));
                FacesContext.getCurrentInstance().addMessage(selectQuantTransTreeTabBind.getClientId(), errMsg);
            }
        } else {
            FacesMessage addsMsg = new FacesMessage("You must enter a value", "Address is required");
            addsMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(dlvAddsIdBind.getClientId(), addsMsg);
        }
    }


    /**Method to select lot for item (If Organisation is not using bin and Items are not serialised)
     * @param actionEvent
     */
    public void selectLotFrItemAction(ActionEvent actionEvent) {
        showPopup(selectLotPopUpBind, true);
    }

    /**Methood To Save PickList
     * @param actionEvent
     */
    public void saveAction(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("areTablesToBeUpdatedLocked");
        binding.execute();
        Object object = binding.getResult();
        Boolean b = (object == null ? true : (Boolean) object);
        if (!b) {
            OperationBinding ob = ADFBeanUtils.getOperationBinding("functionsCallOnPickSave");
            ob.getParamsMap().put("flag", "M");
            ob.execute();
            Object o = ob.getResult();
            if (o != null && o.toString().equals("V")) {
                pick_mode = "V";
            }
        }
    }

    /**Method to select lot and Bin for item (If Organisation is using bin and Items are not serialised)
     * @param actionEvent
     */
    public void selectBinFrItemAction(ActionEvent actionEvent) {
        showPopup(selectBinPopUpBind, true);
    }

    /**Method to select lot, Bin and SrNofor item (If Organisation is using/Not using bin and Items are  serialised)
     * @param actionEvent
     */
    public void selectSrNoFrItemAction(ActionEvent actionEvent) {
        executeOperation("filterPickSrNoTable").execute();
        showPopup(selectSrNoPopUpBind, true);
    }

    /**Create new Packing
     * @param actionEvent
     */
    public void newPackAction(ActionEvent actionEvent) {
        OperationBinding chk = ADFBeanUtils.findOperation("isShipPresntForPick");
        chk.execute();
        Boolean res = chk.getResult() == null ? false : (Boolean) chk.getResult();
        if (res) {
            ADFModelUtils.showFormattedFacesMessage("Packing not Allowed",
                                                    "Shipment has been already created for the present Pick, Packing cannot be done for the Pick. ",
                                                    FacesMessage.SEVERITY_INFO);
        } else {

            OperationBinding crt = executeOperation("CreateInsert");
            crt.execute();

            System.out.println("crt.getErrors()" + crt.getErrors());
            if (crt.getErrors().isEmpty()) {
                //this.getBindings().getOperationBinding("refreshPickLov").execute();
                this.pack_mode = "A";
            }
        }
    }

    /**Method to Edit Packing Details
     * @param actionEvent
     */
    public void editPackAction(ActionEvent actionEvent) {
        this.pack_mode = "E";
    }

    /**Save Pack Details
     * @param actionEvent
     */
    public void savePackAction(ActionEvent actionEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("getPackItmRowCount");
        binding.execute();
        if (((Integer) binding.getResult()) > 0) {
            OperationBinding dispId = executeOperation("generateDispDocNo");
            dispId.getParamsMap().put("flag", "pack");
            dispId.execute();
            if (dispId.getResult() != null) {
                String diplsyId = dispId.getResult().toString();
                if (diplsyId.startsWith("P")) {
                } else {
                    diplsyId = diplsyId.substring(2);
                }

                StringBuilder saveMsg =
                    // new StringBuilder("<html><body><p><b>Packing No " + diplsyId + " Saved Successfully</b></p>");
                    new StringBuilder("<html><body><p>" + ADFModelUtils.resolvRsrc("MSG.2610") + " <b>" + diplsyId +
                                      "</b> " + ADFModelUtils.resolvRsrc("MSG.347") + "</p>");

                saveMsg.append("</body></html>");
                FacesMessage msg = new FacesMessage(saveMsg.toString());
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                executeOperation("Commit").execute();
                executeOperation("Execute").execute();
            }
            this.pack_mode = "V";
            executeOperation("Commit").execute();
            executeOperation("Execute").execute();
        } else {
            FacesMessage msg =
                new FacesMessage("Cannnot save as there is no item in Pack.Please add ateast an item to save.");
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }

    }

    /**Method to populate items in pack list from pick list available items
     * First clears selected indices of Shuttle
     * @param actionEvent
     */
    public void addItemToPackAction(ActionEvent actionEvent) {
        OperationBinding ob = ADFBeanUtils.findOperation("validatedpackSetup");
        ob.execute();
        Boolean chk = ob.getResult() == null ? false : (Boolean) ob.getResult();
        if (chk) {
            JUCtrlListBinding listBinding = (JUCtrlListBinding) getBindings().get("viewPickPackItm1");
            listBinding.clearSelectedIndices();
            showPopup(selectPackItemPopupBind, true);
        }
    }

    /**Search in SLS$PACK using item and pack no
     * @param actionEvent
     */
    public void searchPackAction(ActionEvent actionEvent) {
        String itmId = null;
        String packNo = null;

        if (itmIdSearchBind.getValue() != null) {
            itmId = itmIdSearchBind.getValue().toString();
        }
        if (packNoBind.getValue() != null) {
            packNo = packNoBind.getValue().toString();
        }
        _log.info("ItmId-->" + itmId + "packNo--" + packNo);
        Object elO = resolvElO("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String org_id = "";
        if (elO != null) {
            System.out.println("org id is not null");
            org_id = elO.toString();
        }
        OperationBinding Vobject = executeOperation("getPackObject");
        Vobject.execute();
        if (Vobject.getResult() != null) {
            ViewObject slsPack = (ViewObject) Vobject.getResult();

            if (itmId == null && packNo == null) {
                System.out.println("Come --------- null null");
                slsPack.setWhereClause(null);
            } else if (itmId != null && packNo == null) {
                System.out.println("Come --------- notnull null");
                slsPack.setWhereClause("DOC_ID IN (SELECT DOC_ID FROM SLS$PACK$ITM WHERE ITM_ID='" + itmId + "')");
            } else if (packNo != null && itmId == null) {
                System.out.println("Come --------- null notnull");
                String comp_id = org_id + packNo;
                System.out.println("main id is:  --" + comp_id + "--");
                slsPack.setWhereClause("PACK_ID = '" + comp_id + "'");
            } else {
                String comp_id = org_id + packNo;
                System.out.println("Come --------- notnull notnull");
                slsPack.setWhereClause("DOC_ID IN (SELECT DOC_ID FROM SLS$PACK$ITM WHERE ITM_ID='" + itmId +
                                       "') OR PACK_ID = '" + comp_id + "'");
            }
            /*                slsPack.setWhereClause("DOC_ID IN (SELECT DOC_ID FROM SLS$PACK$ITM WHERE ITM_ID='" + itmId +
                                       "' OR PACK_NO='" + packNo + "')");*/

            /* slsPack.setWhereClause("DOC_ID IN (SELECT DOC_ID FROM SLS$PACK$ITM WHERE ITM_ID=NVL(ITM_ID,'" + itmId +
                                               "') OR PACK_NO=NVL(PACK_NO,'" + packNo + "'))"); */
            slsPack.executeQuery();
        }
    }

    /**Search in SLS$PACK using item and pack no
     * @param actionEvent
     */
    public void resetPackAction(ActionEvent actionEvent) {
        packNoBind.setValue(null);
        OperationBinding Vobject = executeOperation("getPackObject");
        Vobject.execute();
        if (Vobject.getResult() != null) {
            ViewObject slsPack = (ViewObject) Vobject.getResult();
            slsPack.setWhereClause(null);
            slsPack.executeQuery();

        }
        OperationBinding Sobject = executeOperation("getSearchObject");
        Sobject.execute();
        if (Sobject.getResult() != null) {
            itmIdSearchBind.setValue(null);
            packNoSearchBind.setValue(null);
            ViewObject searchVo = (ViewObject) Sobject.getResult();
            searchVo.executeQuery();
            System.out.println("Excuted");
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmIdSearchBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(packNoSearchBind);
    }

    /**Method to delete Item From PickList
     * @param actionEvent
     */

    public void deletePickListItemAction(ActionEvent actionEvent) {
        OperationBinding deleteOb = executeOperation("deleteItmFrmPickList");
        deleteOb.execute();
        if (deleteOb.getResult() != null) {
            String flag = deleteOb.getResult().toString();

            if ("P".equalsIgnoreCase(flag)) {
                //FacesMessage errMsg = new FacesMessage("Items of picklists are packed, Can not delete");
                FacesMessage errMsg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.929"));

                errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, errMsg);
            } /* else if ("L".equalsIgnoreCase(flag)) {
                //FacesMessage errMsg = new FacesMessage("This Item is issued from LOT, Can not delete");
                FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.931']}").toString());

                errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, errMsg);
            }  */ else if ("N".equalsIgnoreCase(flag)) {
                System.out.println("---------------!   Came in Else iF!__!_------------");
                executeOperation("Delete").execute();
                executeOperation("Execute3").execute();
                executeOperation("Execute1").execute();
                executeOperation("postChange").execute();

            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(soItmTableBind);

    }


    /**Method to Search in Shipment View
     * @param actionEvent
     */
    public void searchShipmentAction(ActionEvent actionEvent) {


    }

    /**Method to Reset Shipment View after Search
     * @param actionEvent
     */
    public void resetShipmentAction(ActionEvent actionEvent) {
        OperationBinding resetSearch = executeOperation("resetShipment");
        resetSearch.execute();
        selectChkShiCount = 0;
    }

    /**Method set FacetName to Lot
     * @param actionEvent
     */
    public void viewLotAction(ActionEvent actionEvent) {
        this.setFacetNm("Lot");

    }

    /**Method set FacetName to Bin
     * @param actionEvent
     */
    public void viewBinAction(ActionEvent actionEvent) {
        this.setFacetNm("Bin");

    }

    /**Method set FacetName to SrNo
     * @param actionEvent
     */
    public void viewSerialsAction(ActionEvent actionEvent) {
        this.setFacetNm("SrNo");
    }

    /**Method to generate Shipment from PickList
     * @param actionEvent
     */
    public void generateShipmentAction(ActionEvent actionEvent) {
        OperationBinding validateGen = executeOperation("validateGenShipment");
        validateGen.execute();
        String vouId = "";
        if (validateGen.getResult() != null) {

            AdfFacesContext.getCurrentInstance().addPartialTarget(arrivalPntPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(deptPointPgBind);

            if (validateGen.getResult().toString().equalsIgnoreCase("Y")) {
                OperationBinding chkBind = executeOperation("chkMandTrnpFldsEnterOrNot");
                chkBind.execute();
                if (chkBind.getResult() != null && (Boolean) chkBind.getResult()) {
                    OperationBinding ob = executeOperation("generateShipment");
                    ob.execute();
                    if (ob.getResult() != null) {
                        String flag = ob.getResult().toString();
                        _log.info("Flag from Ship--" + flag);
                        if ("N".equalsIgnoreCase(flag)) {
                            /*  FacesMessage errMsg =
                            new FacesMessage("Please Select atleast one Picklist to generate Shipment"); */
                            FacesMessage errMsg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.932"));
                            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext.getCurrentInstance().addMessage(null, errMsg);
                        } else if ("E".equalsIgnoreCase(flag)) {
                            FacesMessage message =
                                new FacesMessage("There have been an error in Generating Shipment ! Contact Ess !");
                            message.setSeverity(FacesMessage.SEVERITY_FATAL);
                            FacesContext.getCurrentInstance().addMessage(null, message);
                        } else {
                            /* FacesMessage saveMsg =
                            new FacesMessage("Shipment" + flag.substring(2) + "generated successfully."); */
                            executeOperation("Commit").execute();
                            OperationBinding binding =
                                this.getBindings().getOperationBinding("callFunctionToGetGeneratedVouId");
                            binding.execute();
                            if (binding.getResult() != null) {
                                if (!binding.getResult().equals("0")) {
                                    vouId = " Voucher No. <b>" + binding.getResult() + "</b> is generated. ";
                                }
                            }
                            FacesMessage saveMsg =
                                new FacesMessage("<html><body>" + ADFModelUtils.resolvRsrc("MSG.2701") + "<b> " + flag +
                                                 " </b> " + ADFModelUtils.resolvRsrc("MSG.319") + " " + vouId +
                                                 "</body></html>");
                            saveMsg.setSeverity(FacesMessage.SEVERITY_INFO);

                            FacesContext.getCurrentInstance().addMessage(null, saveMsg);
                            //                        this.getTransporterPopupBind().hide();
                            selectChkShiCount = 0;
                            _log.info("Saved");
                            System.out.println("Message is:  " + saveMsg);

                            //Space for method to send mail
                        }
                    }
                } else {
                    FacesMessage message = new FacesMessage("Please select mandatory fields of transporter.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
        }
    }

    /**Method to return on Search Page
     * @return
     */
    public String cancelBackToSearchAction() {
        this.header = "Y";
        this.pick_mode = "V";
        this.reset_link = "Y";
        this.pickReservMode = "N";
        executeOperation("Rollback").execute();
        executeOperation("Execute").execute();

        return "backToSearch";
    }

    /**Method to Check whether system is using PACK or not, if not then redirect to Shipment
     * @return
     */
    public String packOrShipAction() {

        OperationBinding ob = executeOperation("setPackVsblParam");
        ob.execute();
        if (ob.getResult() != null) {
            String flag = ob.getResult().toString();
            _log.info("Use Pack Flag from Am--" + flag);
            RequestContext.getCurrentInstance().getPageFlowScope().put("PARAM_PACK_VSBL_CHCK", flag);
            if (resolvElO("#{pageFlowScope.PARAM_PACK_VSBL_CHCK}") != null) {
                flag = resolvElO("#{pageFlowScope.PARAM_PACK_VSBL_CHCK}").toString();
            }

            _log.info("Flag from --" + flag);
            if (flag.equalsIgnoreCase("Y")) {
                return "goToPack";
            } else {
                return "directShipment";
            }
        } else {
            return null;
        }
    }

    /**Method to Search in Landing Page
     * @param actionEvent
     */
    public void searchAction(ActionEvent actionEvent) {
        //OperationBinding searchVo = executeOperation("getmainSearchView");

        OperationBinding searchVo = executeOperation("searchMainSearch");
        searchVo.execute();
    }

    /**Method to reset data in Landing page
     * @param actionEvent
     */
    public void resetAction(ActionEvent actionEvent) {
        OperationBinding reset = executeOperation("resetMainSearch");
        reset.execute();
    }


    /**Method to create New PickList from Search Page
     * @return
     */
    public String newPickListSrchAction() {
        OperationBinding filterTree = executeOperation("filterOrderData");
        filterTree.getParamsMap().put("pickDate", new Timestamp(11112011));
        filterTree.execute();
        System.out.println("Filtered");
        OperationBinding createPick = executeOperation("Createwithparameters");
        createPick.execute();
        if (createPick.getErrors().isEmpty()) {
            this.pick_mode = "A";
            this.header = "Z";
            RequestContext.getCurrentInstance().getPageFlowScope().put("NAV_PARAM", "P");
            return "goToPickList";

        } else {
            /* StringBuilder msg =
                new StringBuilder("<html><body><b><p style='color:red'>Something went wrong while creating Pick-List</p></b>"); */
            StringBuilder msg =
                new StringBuilder("<html><body><b><p style='color:red'>" + ADFModelUtils.resolvRsrc("MSG.935") +
                                  "</p></b>");

            //msg.append("<ul><li>Try Again</li><li>Or Contact ESS:  +91-120-4212931-39</li></ul></body></html>");
            msg.append("<ul><li>" + ADFModelUtils.resolvRsrc("MSG.937") + "</li><li>" +
                       ADFModelUtils.resolvRsrc("MSG.939") + "</li></ul></body></html>");

            FacesMessage errMsg = new FacesMessage(msg.toString());
            FacesContext.getCurrentInstance().addMessage(null, errMsg);
            return null;
        }
    }

    /**Method to Auto Issue Items
     * @param actionEvent
     */
    public void autoIssueItemAction(ActionEvent actionEvent) {
        executeOperation("issueAutoItem").execute();
    }

    /**Set FacetName to Lot in PickList page
     * @param actionEvent
     */
    public void viewLotPickAction(ActionEvent actionEvent) {
        // executeOperation("executePickItmVo").execute();
        this.setFaceNmPick("Lot");
    }

    /**Set FacetName to Bin in PickList page
     * @param actionEvent
     */
    public void viewBinPickAction(ActionEvent actionEvent) {
        // executeOperation("executePickItmVo").execute();
        this.setFaceNmPick("Bin");
    }

    /**Set FacetName to Serail in PickList page
     * @param actionEvent
     */
    public void viewSerialsPickAction(ActionEvent actionEvent) {
        //  executeOperation("executePickItmVo").execute();
        this.setFaceNmPick("SrNo");
    }

    /**Method to check whether PACK is used in Application or not
     * And redirects control to PickList page from Shipment page
     * @return
     */
    public String backFrmShpmntAction() {
        ADFBeanUtils.findOperation("refreshAllVoOncancellation").execute();
        OperationBinding resetSearch = executeOperation("resetShipment");
        resetSearch.execute();
        selectChkShiCount = 0;
        String flag = "N";
        if (resolvElO("#{pageFlowScope.PARAM_PACK_VSBL_CHCK}") != null) {
            flag = resolvElO("#{pageFlowScope.PARAM_PACK_VSBL_CHCK}").toString();
        }

        _log.info("Flag from --" + flag);
        if (flag.equalsIgnoreCase("Y") && destGo.equalsIgnoreCase("P")) {
            return "backToPack";
        } else if (flag.equalsIgnoreCase("Y") && destGo.equalsIgnoreCase("S")) {
            return "shpMntToSearch";
        } else if (flag.equalsIgnoreCase("N") && destGo.equalsIgnoreCase("P")) {
            return "backToPick";
        } else if (flag.equalsIgnoreCase("N") && destGo.equalsIgnoreCase("S")) {
            return "shpMntToSearch";
        } else {
            return null;
        }

    }

    /**Update Shipment Status to cancel
     * @param actionEvent
     */
    public void updateShipmentAction(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("cancelShipmnt");
        binding.execute();
        Object o = binding.getResult();
    }

    /**method to cancel pack
     * @param actionEvent
     */
    public void cancelPackAction(ActionEvent actionEvent) {
        executeOperation("Rollback").execute();
        executeOperation("Execute").execute();
        executeOperation("Execute1").execute();
        executeOperation("Execute2").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(packItmTableBind);
        packTabBind.setDisclosed(true);
        this.pack_mode = "V";
    }

    /*******************Value Change Listeners defined for page********************/

    public void pickDateVCE(ValueChangeEvent vce) {
        _log.info("Refresh Table");
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
    }

    /**Value Change Listener to filter TreeTable as per Item Selected
     * @param vce
     */
    public void ItemNmSearchVCE(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding ob = executeOperation("searchTreeasPerItm");
            ob.getParamsMap().put("itmDesc", vce.getNewValue().toString());
            ob.execute();
            try {
                //expandTreeTable();
            } catch (NullPointerException npe) {
                //FacesMessage errMsg = new FacesMessage("No Sales Order to display ,Can not expand ");
                FacesMessage errMsg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.905"));

                errMsg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, errMsg);
            }
        }
    }


    /**Method to Filter treeTable customer wise
     * @param vce
     */
    public void customerIdVCE(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            ADFBeanUtils.findOperation("refreshPickCurr").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(currIdSpBinding);

        }
    }

    /**Filter Table as per Selected Customer (First Time)
     * @param vce
     */
    public void selectPickToGenShipVCE(ValueChangeEvent vce) {
        _log.info("New Value-" + vce.getNewValue());
        if (vce.getNewValue() != null && vce.getNewValue().equals(true)) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            if (selectChkShiCount == 0) {
                ADFBeanUtils.getOperationBinding("filterShipGenVw").execute();
            }
            selectChkShiCount = selectChkShiCount + 1;
            DCIteratorBinding shpMntIter = (DCIteratorBinding) getBindings().get("viewSlsShipmntGen1Iterator");
            Row curRow = shpMntIter.getCurrentRow();
            curRow.setAttribute("selectPickListCbTrans", "Y");

            AdfFacesContext.getCurrentInstance().addPartialTarget(genShimntTabBind);
        } else {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            selectChkShiCount = selectChkShiCount - 1;
            if (selectChkShiCount == 0) {
                ADFBeanUtils.getOperationBinding("filterShipGenVw").execute();
            }
            DCIteratorBinding shpMntIter = (DCIteratorBinding) getBindings().get("viewSlsShipmntGen1Iterator");
            Row curRow = shpMntIter.getCurrentRow();
            curRow.setAttribute("selectPickListCbTrans", "N");

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(saveShipLinkBVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(genShimntTabBind);


    }

    /**Method to filter tree Warehouse wise
     * @param vce
     */
    public void warehousePickVCE(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
        }

        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
    }


    /*********************Dialog Listener for Defined for page********************/

    /**Dialog Listener To Add Item Lot Wise
     * Method to select lot for item (If Organisation is not using bin and Items are not serialised)
     * @param dialogEvent
     */
    public void selectItmLotDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            String errMsg = null;
            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = executeOperation("getTotalIssuQtyLot");
            issueQty.execute();
            OperationBinding binding = ADFBeanUtils.getOperationBinding("isLotQtyValid");
            binding.execute();
            Object object = binding.getResult();
            _log.info("isLotValid Returned : " + object);
            if ("Y".equals(object.toString())) {
                if (issueQty.getResult() != null) {
                    issQty = (Number) issueQty.getResult();
                    OperationBinding pickQtyBinding = ADFBeanUtils.getOperationBinding("getCurrentPickQty");
                    pickQtyBinding.execute();
                    Object o = pickQtyBinding.getResult();
                    pickQty = (o == null ? StaticValue.NUMBER_ZERO : (Number) o);

                    _log.info("IssueQty-" + issQty + "PickedQty-" + pickQty);
                    if (issQty.compareTo(0) == 0) {
                        /*  errMsg = "Please select quantity to issue"; */
                        errMsg = ADFModelUtils.resolvRsrc("MSG.941");
                        FacesMessage msg = new FacesMessage(errMsg);
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else if (issQty.compareTo(pickQty) != 0) {
                        /* errMsg = "Issued Quantity is not equal to Picked Quantity"; */
                        errMsg = ADFModelUtils.resolvRsrc("MSG.949");
                        FacesMessage msg = new FacesMessage(errMsg);
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        executeOperation("insertIntoPickItmLot").execute();
                        AdfFacesContext.getCurrentInstance().addPartialTarget(lotTabBind);
                    }
                }
            } else {
                ADFModelUtils.showFormattedFacesMessage("Invalid Lot Issue Quantity !", object.toString(),
                                                        FacesMessage.SEVERITY_ERROR);
            }


        }
    }

    /**Dialog Listener To Select Bin and Lot for Item
     * Method to select lot for item (If Organisation is not using bin and Items are not serialised)
     * @param dialogEvent
     */
    public void selectItmBinDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            String errMsg = null;
            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = executeOperation("getTotalIssueQtyBin");
            issueQty.execute();
            OperationBinding binIssueQty = executeOperation("isBinQtyValid");
            binIssueQty.execute();
            Object res = binIssueQty.getResult();
            StringBuilder h = (res == null ? new StringBuilder("") : (StringBuilder) res);
            _log.info("BinIssue check returned : " + h);
            if (!"Y".equals(h.toString())) {
                ADFModelUtils.showFormattedFacesMessage("Invalid Lot Issue Quantity !", h.toString(),
                                                        FacesMessage.SEVERITY_ERROR);
            } else if (issueQty.getResult() != null) {

                issQty = (Number) issueQty.getResult();
                //_log.info("pickqty bind-" + pickQtyIssueBind.getValue());

                OperationBinding pickQtyBinding = ADFBeanUtils.getOperationBinding("getCurrentPickQty");
                pickQtyBinding.execute();
                Object object = pickQtyBinding.getResult();
                pickQty = (object == null ? StaticValue.NUMBER_ZERO : (Number) object);

                _log.info("IssueQty-" + issQty + "PickedQty-" + pickQty);
                if (issQty.compareTo(0) == 0) {
                    //errMsg = "Please select quantity to issue";
                    errMsg = ADFModelUtils.resolvRsrc("MSG.941");

                    FacesMessage msg = new FacesMessage(errMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else if (issQty.compareTo(pickQty) != 0) {
                    //errMsg = "Issued Quantity is not equal to Picked Quantity";
                    errMsg = ADFModelUtils.resolvRsrc("MSG.949");
                    FacesMessage msg = new FacesMessage(errMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    executeOperation("insertIntoPickItmBin").execute();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(lotTabBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTabBind);

                }
            }
            /*  System.out.println("resetting value");
            binIssuQntbVar.setValue(null);
            binIssuQntbVar.resetValue();
            AdfFacesContext.getCurrentInstance().addPartialTarget(binIssuQntbVar); */
        }

    }

    /**Dialog Listener To Select Bin,Lot and SrNo for Item
     * Method to select lot and bin for item (If Organisation is using bin and Items not serialised)
     * @param dialogEvent
     */
    public void selectItmSrNoDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            String errMsg = null;
            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = executeOperation("getTotalIssueQtySr");
            issueQty.execute();
            if (issueQty.getResult() != null) {
                issQty = (Number) issueQty.getResult();
                OperationBinding pickQtyBinding = ADFBeanUtils.getOperationBinding("getCurrentPickQty");
                pickQtyBinding.execute();
                Object object = pickQtyBinding.getResult();
                pickQty = (object == null ? StaticValue.NUMBER_ZERO : (Number) object);

                _log.info("IssueQty-" + issQty + "PickedQty-" + pickQty);
                if (issQty.compareTo(0) == 0) {
                    //errMsg = "Please select quantity to issue";
                    errMsg = ADFModelUtils.resolvRsrc("MSG.941");

                    FacesMessage msg = new FacesMessage(errMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else if (issQty.compareTo(pickQty) != 0) {
                    //errMsg = "Issued Quantity is not equal to Picked Quantity";
                    errMsg = "Issued Quantity is not equal to Picked Quantity";

                    FacesMessage msg = new FacesMessage(errMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    executeOperation("insertIntoPickItmSr").execute();

                    AdfFacesContext.getCurrentInstance().addPartialTarget(lotTabBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTabBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinSrTabBind);
                }
            }
        }
    }

    /**Method to populate items in pack$itm table
     * @param dialogEvent
     */
    public void addItemToPackDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            Key packItmKey = null;
            OperationBinding binding = executeOperation("insertItmInPackItm");
            binding.execute();
            Key object = (Key) binding.getResult();

            if (object != null) {
                packItmKey = (Key) object;
            }
            // System.out.println("Pack Item Key in bean: " + packItmKey);
            DCIteratorBinding packItr = (DCIteratorBinding) getBindings().get("SlsPack1Iterator");
            //  Key p = par.getCurrentRow().getKey();
            ViewObject viewObject = packItr.getViewObject();
            //  System.out.println(" viewObject " + viewObject);

            Row packCurrRow = packItr.getViewObject().getCurrentRow();
            Key packKey = packCurrRow.getKey();
            Row rr = viewObject.getRow(packKey);
            //System.out.println("rr " + rr);
            //System.out.println("pack item currenct row" + packCurrRow);
            DCIteratorBinding packItmItr = (DCIteratorBinding) getBindings().get("SlsPackItm1Iterator");
            executeOperation("Execute").execute();
            executeOperation("Execute1").execute();

            // System.out.println("stop execuute");
            // System.out.println("packItmItr Iterater count : " + packItmItr.getEstimatedRowCount() + " : " +
            //                    packItr.getEstimatedRowCount());
            //  System.out.println("packItr===" + packItr + "viewObject.getRowCount() " + viewObject.getRowCount());
            viewObject.setCurrentRowAtRangeIndex(((viewObject.getRowCount()) - 1));
            AdfFacesContext.getCurrentInstance().addPartialTarget(packItmTableBind);
        }
    }


    /**********************Validators Defined for Page***********************/

    /**Validator to check packed quantity
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void packItemWeightValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number wght = (oracle.jbo.domain.Number) object;
            if (wght.compareTo(0) == 1 || wght.compareTo(0) == 0) {
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Weight",
                                                               "Can not be negative")); */
                if (isPrecisionValid(26, 6, wght)) {

                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  ADFModelUtils.resolvRsrc("MSG.57"),
                                                                  ADFModelUtils.resolvRsrc("MSG.894")));
                }

            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.965"),
                                                              ADFModelUtils.resolvRsrc("MSG.894")));
            }
        }
    }


    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();

        vc.setPrecision(precision);


        vc.setScale(scale);

        return vc.validateValue(total);
    }

    /**Validator to check weight
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void packedQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number pickedQty = new oracle.jbo.domain.Number(0);
            oracle.jbo.domain.Number packedQty = new oracle.jbo.domain.Number(0);
            packedQty = (oracle.jbo.domain.Number) object;
            if (pickedQtyBind.getValue() != null) {
                pickedQty = (oracle.jbo.domain.Number) pickedQtyBind.getValue();
            }
            if (packedQty.compareTo(0) == 1 || packedQty.compareTo(0) == 0) {

                if (isPrecisionValid(26, 6, packedQty)) {

                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  ADFModelUtils.resolvRsrc("MSG.57"),
                                                                  ADFModelUtils.resolvRsrc("MSG.514")));
                }

            } else if (packedQty.compareTo(pickedQty) == 1) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.950"),
                                                              ADFModelUtils.resolvRsrc("MSG.953")));
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.950"),
                                                              ADFModelUtils.resolvRsrc("MSG.514")));
            }
        }
    }

    /**Validator to check Schedule Quantity in tree table
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void selectQuantTransValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number chkPendQtyMin = new Number(0);
            Number chkPendQtyMax = new Number(0);
            Number tlrncQty = new Number(0);
            Number hund = new Number(100);
            Number schdlQuant = (oracle.jbo.domain.Number) object;
            Number pendQty = (oracle.jbo.domain.Number) pendQtyTreetabBind.getValue();

            Row[] rows = null; //getSelectedNodeRowIterator().findByKey(getSelectedNodeRowKey(), 1);
            if (rows != null) {
                for (Row row : rows) {
                    _log.info("TlrncVal-" + row.getAttribute("TlrncQtyVal") + "and type-" +
                              row.getAttribute("TlrncQtyType"));
                    if (row.getAttribute("TlrncQtyVal") != null) {
                        tlrncQty = (Number) row.getAttribute("TlrncQtyVal");
                    }
                    if (row.getAttribute("TlrncQtyType") != null &&
                        "A".equalsIgnoreCase(row.getAttribute("TlrncQtyType").toString())) {
                        chkPendQtyMin = pendQty.subtract(tlrncQty);
                        chkPendQtyMax = pendQty.add(tlrncQty);
                    } else if (row.getAttribute("TlrncQtyType") != null &&
                               "P".equalsIgnoreCase(row.getAttribute("TlrncQtyType").toString())) {
                        chkPendQtyMin = pendQty.subtract((pendQty.multiply(tlrncQty)).divide(hund));
                        chkPendQtyMax = pendQty.add((pendQty.multiply(tlrncQty)).divide(hund));
                    }
                }
            }
            _log.info("MinQty aftr tlrnc-" + chkPendQtyMin + "max qty is-" + chkPendQtyMax);
            if (schdlQuant.compareTo(chkPendQtyMin) == -1 || schdlQuant.compareTo(chkPendQtyMax) == 1) {
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Quantity",
                                                              "Must be greater than " + chkPendQtyMin +
                                                              " and less than " + chkPendQtyMax +
                                                              " Pending Quantity")); */
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.336"),
                                                              ADFModelUtils.resolvRsrc("MSG.955") + chkPendQtyMin +
                                                              ADFModelUtils.resolvRsrc("MSG.957") + chkPendQtyMax +
                                                              ADFModelUtils.resolvRsrc("MSG.723")));


            }

        }
    }


    public void setSelectLotPopUpBind(RichPopup selectLotPopUpBind) {
        this.selectLotPopUpBind = selectLotPopUpBind;
    }

    public RichPopup getSelectLotPopUpBind() {
        return selectLotPopUpBind;
    }


    public void setSelectBinPopUpBind(RichPopup selectBinPopUpBind) {
        this.selectBinPopUpBind = selectBinPopUpBind;
    }

    public RichPopup getSelectBinPopUpBind() {
        return selectBinPopUpBind;
    }


    public void setSelectSrNoPopUpBind(RichPopup selectSrNoPopUpBind) {
        this.selectSrNoPopUpBind = selectSrNoPopUpBind;
    }

    public RichPopup getSelectSrNoPopUpBind() {
        return selectSrNoPopUpBind;
    }

    public void setSelectPackItemPopupBind(RichPopup selectPackItemPopupBind) {
        this.selectPackItemPopupBind = selectPackItemPopupBind;
    }

    public RichPopup getSelectPackItemPopupBind() {
        return selectPackItemPopupBind;
    }


    public void setItmIdSearchBind(RichInputText itmIdSearchBind) {
        this.itmIdSearchBind = itmIdSearchBind;
    }

    public RichInputText getItmIdSearchBind() {
        return itmIdSearchBind;
    }

    public void setPackNoSearchBind(RichInputListOfValues packNoSearchBind) {
        this.packNoSearchBind = packNoSearchBind;
    }

    public RichInputListOfValues getPackNoSearchBind() {
        return packNoSearchBind;
    }

    public void setPackItmTableBind(RichTable packItmTableBind) {
        this.packItmTableBind = packItmTableBind;
    }

    public RichTable getPackItmTableBind() {
        return packItmTableBind;
    }


    public void setPickedQtyBind(RichInputText pickedQtyBind) {
        this.pickedQtyBind = pickedQtyBind;
    }

    public RichInputText getPickedQtyBind() {
        return pickedQtyBind;
    }


    public void setPendQtyTreetabBind(RichOutputText pendQtyTreetabBind) {
        this.pendQtyTreetabBind = pendQtyTreetabBind;
    }

    public RichOutputText getPendQtyTreetabBind() {
        return pendQtyTreetabBind;
    }


    public void setSelectQuantTransTreeTabBind(RichInputText selectQuantTransTreeTabBind) {
        this.selectQuantTransTreeTabBind = selectQuantTransTreeTabBind;
    }

    public RichInputText getSelectQuantTransTreeTabBind() {
        return selectQuantTransTreeTabBind;
    }


    public void setEoIdSearchShipBind(RichInputText eoIdSearchShipBind) {
        this.eoIdSearchShipBind = eoIdSearchShipBind;
    }

    public RichInputText getEoIdSearchShipBind() {
        return eoIdSearchShipBind;
    }

    public void setDlvModeShipSearchBind(RichSelectOneChoice dlvModeShipSearchBind) {
        this.dlvModeShipSearchBind = dlvModeShipSearchBind;
    }

    public RichSelectOneChoice getDlvModeShipSearchBind() {
        return dlvModeShipSearchBind;
    }

    public void setWhIdSearchShipBind(RichSelectOneChoice whIdSearchShipBind) {
        this.whIdSearchShipBind = whIdSearchShipBind;
    }

    public RichSelectOneChoice getWhIdSearchShipBind() {
        return whIdSearchShipBind;
    }


    public void setFacetNm(String facetNm) {
        this.facetNm = facetNm;
    }

    public String getFacetNm() {
        return facetNm;
    }


    public void setDlvModeSrchMainBind(RichSelectOneChoice dlvModeSrchMainBind) {
        this.dlvModeSrchMainBind = dlvModeSrchMainBind;
    }

    public RichSelectOneChoice getDlvModeSrchMainBind() {
        return dlvModeSrchMainBind;
    }

    public void setPickDtSrchMainBind(RichInputDate pickDtSrchMainBind) {
        this.pickDtSrchMainBind = pickDtSrchMainBind;
    }

    public RichInputDate getPickDtSrchMainBind() {
        return pickDtSrchMainBind;
    }

    public void setPickDtFrmShipBind(RichInputDate pickDtFrmShipBind) {
        this.pickDtFrmShipBind = pickDtFrmShipBind;
    }

    public RichInputDate getPickDtFrmShipBind() {
        return pickDtFrmShipBind;
    }

    public void setPickDtToShipBind(RichInputDate pickDtToShipBind) {
        this.pickDtToShipBind = pickDtToShipBind;
    }

    public RichInputDate getPickDtToShipBind() {
        return pickDtToShipBind;
    }

    public void setPick_mode(String pick_mode) {
        this.pick_mode = pick_mode;
    }

    public String getPick_mode() {
        return pick_mode;
    }

    public void setPack_mode(String pack_mode) {
        this.pack_mode = pack_mode;
    }

    public String getPack_mode() {
        return pack_mode;
    }


    public void setFaceNmPick(String faceNmPick) {
        this.faceNmPick = faceNmPick;
    }

    public String getFaceNmPick() {
        return faceNmPick;
    }

    public void setGoToDestBind(RichSelectOneRadio goToDestBind) {
        this.goToDestBind = goToDestBind;
    }

    public RichSelectOneRadio getGoToDestBind() {
        return goToDestBind;
    }


    public void setGenShimntTabBind(RichTable genShimntTabBind) {
        this.genShimntTabBind = genShimntTabBind;
    }

    public RichTable getGenShimntTabBind() {
        return genShimntTabBind;
    }


    public void setItmAvlblQtyBind(RichOutputText itmAvlblQtyBind) {
        this.itmAvlblQtyBind = itmAvlblQtyBind;
    }

    public RichOutputText getItmAvlblQtyBind() {
        return itmAvlblQtyBind;
    }

    public void setCancelPickFlgBind(RichSelectBooleanCheckbox cancelPickFlgBind) {
        this.cancelPickFlgBind = cancelPickFlgBind;
    }

    public RichSelectBooleanCheckbox getCancelPickFlgBind() {
        return cancelPickFlgBind;
    }


    public void setCancelShipFlgBind(RichSelectBooleanCheckbox cancelShipFlgBind) {
        this.cancelShipFlgBind = cancelShipFlgBind;
    }

    public RichSelectBooleanCheckbox getCancelShipFlgBind() {
        return cancelShipFlgBind;
    }

    public void setPickDateBind(RichInputDate pickDateBind) {
        this.pickDateBind = pickDateBind;
    }

    public RichInputDate getPickDateBind() {
        return pickDateBind;
    }

    public void setWareHousePickBind(RichSelectOneChoice wareHousePickBind) {
        this.wareHousePickBind = wareHousePickBind;
    }

    public RichSelectOneChoice getWareHousePickBind() {
        return wareHousePickBind;
    }

    public void setLotTabBind(RichTable lotTabBind) {
        this.lotTabBind = lotTabBind;
    }

    public RichTable getLotTabBind() {
        return lotTabBind;
    }

    public void setLotBinTabBind(RichTable lotBinTabBind) {
        this.lotBinTabBind = lotBinTabBind;
    }

    public RichTable getLotBinTabBind() {
        return lotBinTabBind;
    }

    public void setLotBinSrTabBind(RichTable lotBinSrTabBind) {
        this.lotBinSrTabBind = lotBinSrTabBind;
    }

    public RichTable getLotBinSrTabBind() {
        return lotBinSrTabBind;
    }

    public void setDlvAddsIdBind(RichInputListOfValues dlvAddsIdBind) {
        this.dlvAddsIdBind = dlvAddsIdBind;
    }

    public RichInputListOfValues getDlvAddsIdBind() {
        return dlvAddsIdBind;
    }

    public void setDispShipIdBinding(RichInputText dispShipIdBinding) {
        this.dispShipIdBinding = dispShipIdBinding;
    }

    public RichInputText getDispShipIdBinding() {
        return dispShipIdBinding;
    }

    public void setUidBinding(RichSelectOneChoice uidBinding) {
        this.uidBinding = uidBinding;
    }

    public RichSelectOneChoice getUidBinding() {
        return uidBinding;
    }

    public void setUidCreateDtBinding(RichInputDate uidCreateDtBinding) {
        this.uidCreateDtBinding = uidCreateDtBinding;
    }

    public RichInputDate getUidCreateDtBinding() {
        return uidCreateDtBinding;
    }

    public void setShipDtBinding(RichInputDate shipDtBinding) {
        this.shipDtBinding = shipDtBinding;
    }

    public RichInputDate getShipDtBinding() {
        return shipDtBinding;
    }

    public void setWhIdShipBinding(RichSelectOneChoice whIdShipBinding) {
        this.whIdShipBinding = whIdShipBinding;
    }

    public RichSelectOneChoice getWhIdShipBinding() {
        return whIdShipBinding;
    }

    public void setDocIdShipBinding(RichPanelLabelAndMessage docIdShipBinding) {
        this.docIdShipBinding = docIdShipBinding;
    }

    public RichPanelLabelAndMessage getDocIdShipBinding() {
        return docIdShipBinding;
    }

    public void setShipIdDocBinding(RichOutputText shipIdDocBinding) {
        this.shipIdDocBinding = shipIdDocBinding;
    }

    public RichOutputText getShipIdDocBinding() {
        return shipIdDocBinding;
    }

    public void searchSoACTION(ActionEvent actionEvent) {
        OperationBinding binding = executeOperation("arePickEntriesValid");
        binding.execute();
        Object object = binding.getResult();
        Boolean b = (object == null ? false : (Boolean) object);
        if (b) {
            header = "N";
        }
    }

    public void setDeliveryAddressShipmntSearchBind(RichInputListOfValues deliveryAddressShipmntSearchBind) {
        this.deliveryAddressShipmntSearchBind = deliveryAddressShipmntSearchBind;
    }

    public RichInputListOfValues getDeliveryAddressShipmntSearchBind() {
        return deliveryAddressShipmntSearchBind;
    }

    public void setPickIdBind(RichInputListOfValues pickIdBind) {
        this.pickIdBind = pickIdBind;
    }

    public RichInputListOfValues getPickIdBind() {
        return pickIdBind;
    }

    public void setPickIdTrans(RichInputListOfValues pickIdTrans) {
        this.pickIdTrans = pickIdTrans;
    }

    public RichInputListOfValues getPickIdTrans() {
        return pickIdTrans;
    }

    public void saveWarnACTION(ActionEvent actionevent) {
        OperationBinding re = ADFBeanUtils.getOperationBinding("chkSoAdvance");
        re.execute();
        Boolean res = (Boolean) re.getResult();
        if (res) {
            Object object = cancelPickListFlag.getValue();
            System.out.println("Val of cancel flag:  " + object);
            if (object != null && (Boolean) object.equals(true)) {
                OperationBinding ob = ADFBeanUtils.getOperationBinding("functionsCallOnPickSave");
                ob.getParamsMap().put("flag", "C");
                ob.execute();
                Object o = ob.getResult();
                if (o != null && o.toString().equals("V")) {
                    pick_mode = "V";
                }
                return;
            }
            System.out.println("Start");
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindItmTbl);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTabBind);
            System.out.println("save set 1");
            OperationBinding binding = this.getBindings().getOperationBinding("getAlertFlag");
            binding.execute();
            String val = binding.getResult().toString();
            System.out.println("Result : " + val);
            alertPopupBind.hide();
            if (val.equalsIgnoreCase("W")) {
                this.showPopup(alertPopupBind, true);
            } else if (val.equalsIgnoreCase("S")) {
                this.showPopup(alertPopupStopBind, true);
            } else if (val.equalsIgnoreCase("N")) {
                this.showPopup(alertPopupNotifctionBind, true);
            } else {
                System.out.println("save set 2");
                saveAction(new ActionEvent(new RichCommandButton()));
            }
        } else {

        }
        System.out.println("save set 3");
    }

    public void setAlertMessageBind(RichMessage alertMessageBind) {
        this.alertMessageBind = alertMessageBind;
    }

    public RichMessage getAlertMessageBind() {
        return alertMessageBind;
    }

    public void setAlertPopupBind(RichPopup alertPopupBind) {
        this.alertPopupBind = alertPopupBind;
    }

    public RichPopup getAlertPopupBind() {
        return alertPopupBind;
    }

    public void setAlertPopupStopBind(RichPopup alertPopupStopBind) {
        this.alertPopupStopBind = alertPopupStopBind;
    }

    public RichPopup getAlertPopupStopBind() {
        return alertPopupStopBind;
    }

    public void setAlertPopupNotifctionBind(RichPopup alertPopupNotifctionBind) {
        this.alertPopupNotifctionBind = alertPopupNotifctionBind;
    }

    public RichPopup getAlertPopupNotifctionBind() {
        return alertPopupNotifctionBind;
    }

    public void alertWarningDialogList(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("Yes")) {
            System.out.println("save set 4");
            saveAction(new ActionEvent(new RichCommandButton()));
        }
    }

    public void alertInfoDialogList(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("Yes")) {
            System.out.println("save set 5");
            saveAction(new ActionEvent(new RichCommandButton()));
        }
    }

    public void issueQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            //Object object_2 = uIComponent.getAttributes().get("totStk");
            //System.out.println("Itm Stock : "+object_2);
            Number itmStkQnt = (Number) itmTotalStkQtyBVar.getValue();
            System.out.println(" item stock quantity " + itmStkQnt);
            Number issueQtyVlC = (Number) object;
            _log.info("issueQtyVlC" + issueQtyVlC);
            /******************************************/
            //addded by rohit to check whether issue quantity from lot or bin must be less then or equal to stock quantity.
            if (issueQtyVlC.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Quantity can not be negative", null));

            } else if (issueQtyVlC.compareTo(itmStkQnt) == 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Issue Quantity must be less then or equal to Stock Quantity",
                                                              null));


            }
            /********************************/
            if (isPrecisionValid(26, 6, issueQtyVlC)) {

            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.57"),
                                                              ADFModelUtils.resolvRsrc("MSG.894")));
            }
        }
    }


    public void setPackNoBind(RichInputText packNoBind) {
        this.packNoBind = packNoBind;
    }

    public RichInputText getPackNoBind() {
        return packNoBind;
    }

    public void packQuantityValid(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Object o = uIComponent.getAttributes().get("pickQty");
            Number packQty = (Number) object;
            Number pickQty = (o == null ? StaticValue.NUMBER_ZERO : (Number) o);
            System.out.println("Pick Quantity : " + pickQty + " Pack Qty : " + packQty);
            if (packQty.compareTo(StaticValue.NUMBER_ZERO) <= 0) {
                throw new ValidatorException(new FacesMessage("Invalid Quantity !",
                                                              "Pack Quantity should be greater than zero."));
            } else if (packQty.compareTo(pickQty) > 0) {
                throw new ValidatorException(new FacesMessage("Invalid Quantity !",
                                                              "Pack Quantity should be less than or equal to Pick Quantity."));
            }
        }
    }

    public void setTransporterPopupBind(RichPopup transporterPopupBind) {
        this.transporterPopupBind = transporterPopupBind;
    }

    public RichPopup getTransporterPopupBind() {
        return transporterPopupBind;
    }

    public void setItmTotalStkQtyBVar(RichInputText itmTotalStkQtyBVar) {
        this.itmTotalStkQtyBVar = itmTotalStkQtyBVar;
    }

    public RichInputText getItmTotalStkQtyBVar() {
        return itmTotalStkQtyBVar;
    }

    public void setBindItmTbl(RichTable bindItmTbl) {
        this.bindItmTbl = bindItmTbl;
    }

    public RichTable getBindItmTbl() {
        return bindItmTbl;
    }

    public void setDlvAddsBVar(RichInputText dlvAddsBVar) {
        this.dlvAddsBVar = dlvAddsBVar;
    }

    public RichInputText getDlvAddsBVar() {
        return dlvAddsBVar;
    }

    public void setBinIssuQntbVar(RichInputText binIssuQntbVar) {
        this.binIssuQntbVar = binIssuQntbVar;
    }

    public RichInputText getBinIssuQntbVar() {
        return binIssuQntbVar;
    }

    String reset_link = "Y";
    String header = "Y";

    public void resetPopulatedDataAction(ActionEvent actionEvent) {
        System.out.println("aet 1");

        Timestamp dt = StaticValue.getCurrDtWidTimestamp();
        if (pickDateBind.getValue() != null) {
            dt = (Timestamp) pickDateBind.getValue();
        }
        OperationBinding filterTree = executeOperation("resetOrderData");
        filterTree.getParamsMap().put("pickDate", dt);
        filterTree.execute();
        header = "Z";
    }

    public void setReset_link(String reset_link) {
        this.reset_link = reset_link;
    }

    public String getReset_link() {
        return reset_link;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public void setDelConfirmPopUp(RichPopup delConfirmPopUp) {
        this.delConfirmPopUp = delConfirmPopUp;
    }

    public RichPopup getDelConfirmPopUp() {
        return delConfirmPopUp;
    }

    public void okToDelActionListener(ActionEvent actionEvent) {
        /* OperationBinding obb = executeOperation("delLotbinSrData");
        obb.execute();
        executeOperation("Execute3").execute();
        OperationBinding ob = executeOperation("rePopulateItemToPickList");
        ob.execute(); */
        ADFBeanUtils.getOperationBinding("addSelectedItemsToPick").execute();
        delConfirmPopUp.hide();
        //System.out.println("NOTOK " + rtnval);
    }

    public void cancelActionListener(ActionEvent actionEvent) {
        delConfirmPopUp.hide();
    }


    public void setIsPickSelect(Boolean isPickSelect) {
        this.isPickSelect = isPickSelect;
    }

    public Boolean getIsPickSelect() {
        //System.out.println("enter in gatter");
        if (isPickSelect != null) {
            //System.out.println("set 1");
            OperationBinding ob = executeOperation("isPickSelected");
            ob.execute();
            if (ob != null) {
                Boolean b = (Boolean) ob.getResult();
                //System.out.println("b :" + b);
                if (b != null) {
                    isPickSelect = b;
                }
            }
        }

        //System.out.println("isPickSelect  in bean " + isPickSelect);
        return isPickSelect;
    }

    public void setSaveShipLinkBVar(RichCommandImageLink saveShipLinkBVar) {
        this.saveShipLinkBVar = saveShipLinkBVar;
    }

    public RichCommandImageLink getSaveShipLinkBVar() {
        return saveShipLinkBVar;
    }

    public void setUpdateButtonBind(RichCommandImageLink updateButtonBind) {
        this.updateButtonBind = updateButtonBind;
    }

    public RichCommandImageLink getUpdateButtonBind() {
        return updateButtonBind;
    }

    public void cancelChangeAction(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            AdfFacesContext.getCurrentInstance().addPartialTarget(updateButtonBind);
        }

    }

    public void setCancelPickListFlag(RichSelectBooleanCheckbox cancelPickListFlag) {
        this.cancelPickListFlag = cancelPickListFlag;
    }

    public RichSelectBooleanCheckbox getCancelPickListFlag() {
        return cancelPickListFlag;
    }

    public void cancelAction(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(saveButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(editButtonBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(addButtonBind);
    }

    public void setSaveButtonBind(RichCommandImageLink saveButtonBind) {
        this.saveButtonBind = saveButtonBind;
    }

    public RichCommandImageLink getSaveButtonBind() {
        return saveButtonBind;
    }

    public void setEditButtonBind(RichCommandImageLink editButtonBind) {
        this.editButtonBind = editButtonBind;
    }

    public RichCommandImageLink getEditButtonBind() {
        return editButtonBind;
    }

    public void setAddButtonBind(RichCommandImageLink addButtonBind) {
        this.addButtonBind = addButtonBind;
    }

    public RichCommandImageLink getAddButtonBind() {
        return addButtonBind;
    }

    /**
     * Method to select all the filtered Sales Order
     * @param actionEvent
     */
    public void selectAllSoACTION(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("selectAllPickSo").execute();
    }

    /**
     * Method to de-select all the filtered Sales Order
     * @param actionEvent
     */
    public void deSelectAllSoACTION(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("deSelectAllPickSo").execute();
    }

    /**
     * Method to select all the filtered Sales Order Items
     * @param actionEvent
     */
    public void selectAllSoItmACTION(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("selectAllPickSoItms").execute();
    }

    /**
     * Method to de-select all the filtered Sales Order Items
     * @param actionEvent
     */
    public void deSelectAllSoItmACTION(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("deSelectAllPickSoItms").execute();
    }

    /**
     * Method to filter Sales Order Items for selected SalesOrder
     * @param actionEvent
     */
    public void filterSoPickItemsForSelectedSoACTION(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("filterSoPickItems").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(soItmTableBind);
    }

    /**
     * Validator to validate Scheduled Quantity
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void schdlQtyVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Object selectedO = uIComponent.getAttributes().get("selected");
            StringBuilder selected =
                (selectedO == null ? new StringBuilder("N") : new StringBuilder(selectedO.toString()));
            System.out.println("Selected : " + selected);
            if ("Y".equals(selected.toString())) {
                Object availableQtyO = uIComponent.getAttributes().get("availQty");
                Object pendingQtyO = uIComponent.getAttributes().get("pendingQty");
                Object tolQtyO = uIComponent.getAttributes().get("tolQuant");


                Number availableQty = (availableQtyO == null ? StaticValue.NUMBER_ZERO : (Number) availableQtyO);
                Number pendingQty = (pendingQtyO == null ? StaticValue.NUMBER_ZERO : (Number) pendingQtyO);
                Number tolTotQty = (tolQtyO == null ? StaticValue.NUMBER_ZERO : (Number) tolQtyO);
                Number schdlQty = (Number) object;
                pendingQty = pendingQty.add(tolTotQty);
                System.out.println("totQtyO : " + tolTotQty + " " + tolQtyO);
                System.out.println("availableQty : " + availableQty);
                System.out.println("pendingQty : " + pendingQty);
                System.out.println("schdlQty : " + schdlQty);
                if (ADFBeanUtils.isNumberNegative(schdlQty)) {
                    throw new ValidatorException(new FacesMessage("Scheduled Quantity with Tolerance cannot be less than or equal to Zero!",
                                                                  "Please select a Valid Scheduled Quantity!"));
                } else if (schdlQty.compareTo(pendingQty) == 1) {
                    throw new ValidatorException(new FacesMessage("Scheduled Quantity cannot be greater than Pending Quantity!",
                                                                  "Please select a Valid Scheduled Quantity!"));
                } else if (schdlQty.compareTo(availableQty) == 1) {
                    throw new ValidatorException(new FacesMessage("Scheduled Quantity cannot be greater than Available Quantity!",
                                                                  "Please select a Valid Scheduled Quantity!"));
                }
            }
        }
    }

    private ArrayList<StringBuilder> issuedItmsList;

    public ArrayList<StringBuilder> getIssuedItmsList() {
        return issuedItmsList;
    }

    /**
     * Method to addPick to SlsPickItem
     * @param ace
     */
    public void addSelectedItemsToPick(ActionEvent ace) {
        //checkIfLotDataExistForAnyOfSelectedRows
        OperationBinding binding = ADFBeanUtils.getOperationBinding("checkIfLotDataExistForAnyOfSelectedRows");
        binding.execute();
        Object r = binding.getResult();
        if (r == null) {

        } else {
            ArrayList<StringBuilder> j = (ArrayList<StringBuilder>) r;
            if (j.size() > 0) {
                issuedItmsList = j;
                showPopup(delConfirmPopUp, true);
            } else {
                ADFBeanUtils.getOperationBinding("addSelectedItemsToPick").execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(popResetPglBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(soItmTableBind);
            }
        }
    }


    public void setSoItmTableBind(RichTable soItmTableBind) {
        this.soItmTableBind = soItmTableBind;
    }

    public RichTable getSoItmTableBind() {
        return soItmTableBind;
    }

    public void setPopResetPglBind(RichPanelGroupLayout popResetPglBind) {
        this.popResetPglBind = popResetPglBind;
    }

    public RichPanelGroupLayout getPopResetPglBind() {
        return popResetPglBind;
    }

    public void AddPckMtrlActionListner(ActionEvent actionEvent) {
        /**
     * 1 : Wharehouse is not Selected.
     * 2 : Item is not Selected.
     * 3 : Item Uom is not Selected.
     * 4 : Item Uom Bs is not Selected.
     * 5 : PickQuantity is less than or Equal to zero
     * 6 : PickQuantity is greater than Available Quantity.
         */
        OperationBinding binding = ADFBeanUtils.getOperationBinding("addPackMtlItm");
        binding.execute();
        Object o = binding.getResult();
        Integer i = (o == null ? -1 : (Integer) o);
        if (i == 1) {
            ADFModelUtils.showFacesMessage("Warehouse is not Selected.", "Please select required Values.",
                                           FacesMessage.SEVERITY_ERROR,
                                           ADFBeanUtils.getComponentClientIdFromId(actionEvent.getComponent(), "soc6"));
        } else if (i == 2) {
            ADFModelUtils.showFacesMessage("Item is not Selected.", "Please select required Values.",
                                           FacesMessage.SEVERITY_ERROR,
                                           ADFBeanUtils.getComponentClientIdFromId(actionEvent.getComponent(),
                                                                                   "itmDescTransId"));
        } else if (i == 3) {
            ADFModelUtils.showFacesMessage("Item UOM is not Selected.", "Please select required Values.",
                                           FacesMessage.SEVERITY_ERROR,
                                           ADFBeanUtils.getComponentClientIdFromId(actionEvent.getComponent(),
                                                                                   "itmDescTransId"));
        } else if (i == 4) {
            ADFModelUtils.showFacesMessage("Item UOM Basic is not Selected.", "Please select required Values.",
                                           FacesMessage.SEVERITY_ERROR,
                                           ADFBeanUtils.getComponentClientIdFromId(actionEvent.getComponent(),
                                                                                   "itmDescTransId"));
        } else if (i == 5) {
            ADFModelUtils.showFacesMessage("Pack Quantity is not Valid.", "Pack Quantity should be greater than zero..",
                                           FacesMessage.SEVERITY_ERROR,
                                           ADFBeanUtils.getComponentClientIdFromId(actionEvent.getComponent(), "it38"));
        } else if (i == 6) {
            ADFModelUtils.showFacesMessage("Pack Quantity is not Valid.",
                                           "Pack Quantity should be less than or equal to Available Quantity.",
                                           FacesMessage.SEVERITY_ERROR,
                                           ADFBeanUtils.getComponentClientIdFromId(actionEvent.getComponent(), "it38"));
        } else if (i == 8) {
            ADFModelUtils.showFacesMessage("Packing material already added.",
                                           "Please add another Packing material, as this material is already added.",
                                           FacesMessage.SEVERITY_ERROR,
                                           ADFBeanUtils.getComponentClientIdFromId(actionEvent.getComponent(),
                                                                                   "itmDescTransId"));
        }


    }

    public void savePckMtrlActionListner(ActionEvent actionEvent) {

        ADFBeanUtils.getOperationBinding("chkDuplicateItemforPck").execute();
        ADFBeanUtils.getOperationBinding("insertIntoPck").execute();
        e_Mode = "V";
        AdfFacesContext.getCurrentInstance().addPartialTarget(freezePrfBind);
    }

    public void AllocateLotToPckMtrl(ActionEvent actionEvent) {
        showPopup(selectpopUpBindForPCkMtrl, true);

    }

    public void AutoIssueToPckMtrl(ActionEvent actionEvent) {
        executeOperation("issueAutoItemInPckMtrl").execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(bindLotPckMtrl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindBinPckMtrl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindSrPckMtrl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(savePckBind);
    }

    public void AllocateBinToPckMtrl(ActionEvent actionEvent) {
        showPopup(selectBinPopUpBindForPckMtrl, true);
    }

    public void AllocateSrToPckMtrl(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void setSelectpopUpBindForPCkMtrl(RichPopup selectpopUpBindForPCkMtrl) {
        this.selectpopUpBindForPCkMtrl = selectpopUpBindForPCkMtrl;
    }

    public RichPopup getSelectpopUpBindForPCkMtrl() {
        return selectpopUpBindForPCkMtrl;
    }

    public void SelectItemLotDialogListenerForPckMtrl(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            String errMsg = null;
            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = executeOperation("getTotalIssuQtyLot");
            issueQty.execute();
            OperationBinding binding = ADFBeanUtils.getOperationBinding("isLotQtyValidForPckMtrl");
            binding.execute();
            Object object = binding.getResult();
            _log.info("isLotValid Returned : " + object);
            if ("Y".equals(object.toString())) {
                if (issueQty.getResult() != null) {
                    issQty = (Number) issueQty.getResult();
                    OperationBinding pickQtyBinding = ADFBeanUtils.getOperationBinding("getCurrentPickQtyForPckMtrl");
                    pickQtyBinding.execute();
                    Object o = pickQtyBinding.getResult();
                    pickQty = (o == null ? StaticValue.NUMBER_ZERO : (Number) o);

                    _log.info("IssueQty-" + issQty + "PickedQty-" + pickQty);
                    if (issQty.compareTo(0) == 0) {
                        /*  errMsg = "Please select quantity to issue"; */
                        errMsg = ADFModelUtils.resolvRsrc("MSG.914");
                        FacesMessage msg = new FacesMessage(errMsg);
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(savePckBind);
                    } else if (issQty.compareTo(pickQty) != 0) {
                        /* errMsg = "Issued Quantity is not equal to Picked Quantity"; */
                        errMsg = ADFModelUtils.resolvRsrc("MSG.949");
                        FacesMessage msg = new FacesMessage(errMsg);
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(savePckBind);
                    } else {
                        executeOperation("insertIntoPickItmLotForPckMtrl").execute();
                        AdfFacesContext.getCurrentInstance().addPartialTarget(bindLotPckMtrl);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(bindBinPckMtrl);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(bindSrPckMtrl);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(savePckBind);
                    }
                }
            } else {
                ADFModelUtils.showFormattedFacesMessage("Invalid Lot Issue Quantity !", object.toString(),
                                                        FacesMessage.SEVERITY_ERROR);
            }


        }

    }

    public void setLotTablebindForPckMtrl(RichTable lotTablebindForPckMtrl) {
        this.lotTablebindForPckMtrl = lotTablebindForPckMtrl;
    }

    public RichTable getLotTablebindForPckMtrl() {
        return lotTablebindForPckMtrl;
    }

    public void setSelectBinPopUpBindForPckMtrl(RichPopup selectBinPopUpBindForPckMtrl) {
        this.selectBinPopUpBindForPckMtrl = selectBinPopUpBindForPckMtrl;
    }

    public RichPopup getSelectBinPopUpBindForPckMtrl() {
        return selectBinPopUpBindForPckMtrl;
    }

    public void selectItmBinForPckMtrlDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            String errMsg = null;
            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = executeOperation("getTotalIssueQtyBin");
            issueQty.execute();
            OperationBinding binIssueQty = executeOperation("isBinQtyValidForPckMtrl");
            binIssueQty.execute();
            Object res = binIssueQty.getResult();
            StringBuilder h = (res == null ? new StringBuilder("") : (StringBuilder) res);
            _log.info("BinIssue check returned : " + h);
            if (!"Y".equals(h.toString())) {
                ADFModelUtils.showFormattedFacesMessage("Invalid Lot Issue Quantity !", h.toString(),
                                                        FacesMessage.SEVERITY_ERROR);
            } else if (issueQty.getResult() != null) {

                issQty = (Number) issueQty.getResult();
                //_log.info("pickqty bind-" + pickQtyIssueBind.getValue());

                OperationBinding pickQtyBinding = ADFBeanUtils.getOperationBinding("getCurrentPickQtyForPckMtrl");
                pickQtyBinding.execute();
                Object object = pickQtyBinding.getResult();
                pickQty = (object == null ? StaticValue.NUMBER_ZERO : (Number) object);

                _log.info("IssueQty-" + issQty + "PickedQty-" + pickQty);
                if (issQty.compareTo(0) == 0) {
                    //errMsg = "Please select quantity to issue";
                    errMsg = ADFModelUtils.resolvRsrc("MSG.941");

                    FacesMessage msg = new FacesMessage(errMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(savePckBind);
                } else if (issQty.compareTo(pickQty) != 0) {
                    //errMsg = "Issued Quantity is not equal to Picked Quantity";
                    errMsg = ADFModelUtils.resolvRsrc("MSG.949");
                    FacesMessage msg = new FacesMessage(errMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    executeOperation("insertIntoPickItmBinForPckMtrl").execute();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(bindLotPckMtrl);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(bindBinPckMtrl);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(bindSrPckMtrl);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(savePckBind);
                }
            }
            /*  System.out.println("resetting value");
                    binIssuQntbVar.setValue(null);
                    binIssuQntbVar.resetValue();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(binIssuQntbVar); */
        }
    }

    public void AddSerialForPckMtrlActionListener(ActionEvent actionEvent) {
        // Add event code here...

        executeOperation("filterPickSrNoTableForPckMtrl").execute();
        showPopup(selectSRNoPopBindForPckMtrl, true);
    }

    public void selectItmSrNoForPckMtrlDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            String errMsg = null;
            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = executeOperation("getTotalIssueQtySr");
            issueQty.execute();
            if (issueQty.getResult() != null) {
                issQty = (Number) issueQty.getResult();
                OperationBinding pickQtyBinding = ADFBeanUtils.getOperationBinding("getCurrentPickQtyForPckMtrl");
                pickQtyBinding.execute();
                Object object = pickQtyBinding.getResult();
                pickQty = (object == null ? StaticValue.NUMBER_ZERO : (Number) object);

                _log.info("IssueQty-" + issQty + "PickedQty-" + pickQty);
                if (issQty.compareTo(0) == 0) {
                    //errMsg = "Please select quantity to issue";
                    errMsg = ADFModelUtils.resolvRsrc("MSG.941");

                    FacesMessage msg = new FacesMessage(errMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else if (issQty.compareTo(pickQty) != 0) {
                    //errMsg = "Issued Quantity is not equal to Picked Quantity";
                    errMsg = "Issued Quantity is not equal to Picked Quantity";

                    FacesMessage msg = new FacesMessage(errMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    executeOperation("insertIntoPickItmSrForPckMtrl").execute();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(bindLotPckMtrl);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(bindBinPckMtrl);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(bindSrPckMtrl);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(savePckBind);
                }
            }
        }
    }

    public void cancelActionListenerForPckMtrl(ActionEvent actionEvent) {
        // Add event code here...

        OperationBinding pickQtyBinding = ADFBeanUtils.getOperationBinding("Rollback1");
        pickQtyBinding.execute();
        packFreezeMode = "N";
    }

    public void setSelectSRNoPopBindForPckMtrl(RichPopup selectSRNoPopBindForPckMtrl) {
        this.selectSRNoPopBindForPckMtrl = selectSRNoPopBindForPckMtrl;
    }

    public RichPopup getSelectSRNoPopBindForPckMtrl() {
        return selectSRNoPopBindForPckMtrl;
    }

    public void AllocateAdd(ActionEvent actionEvent) {

        OperationBinding binding = ADFBeanUtils.getOperationBinding("savePackingMtrlIssuDtls");
        binding.execute();
        Object o = binding.getResult();
        Integer i = (o == null ? -1 : (Integer) o);
        if (i == 0) {
            pack_mode = "V";
            packFreezeMode = "N";
        }
        System.out.println("HI----->");
        /* if (packFreezeMode.equalsIgnoreCase("N")) {
            System.out.println("INside N");
            OperationBinding allocateBind = ADFBeanUtils.getOperationBinding("Commit");
            allocateBind.execute();
            ADFModelUtils.showFormattedFacesMessage("Your Item's Details Have Been Saved Successfully.", "",
                                                    FacesMessage.SEVERITY_INFO);

        } else if (packFreezeMode.equalsIgnoreCase("Y")) {
            System.out.println("INside Y");
            OperationBinding allocateBindvar = ADFBeanUtils.getOperationBinding("validateLotBinSRForEachPck");
            allocateBindvar.execute();
            if (allocateBindvar.getResult() != null) {
                String resValid = allocateBindvar.getResult().toString();
                if (resValid.equalsIgnoreCase("Y")) {
                    OperationBinding freezeBind = ADFBeanUtils.getOperationBinding("freezeProfile");
                    freezeBind.execute();
                    Object object = freezeBind.getResult();
                    if (object != null && object.toString().equalsIgnoreCase("Y")) {
                        packFreezeMode = "N";
                        cancelPackBind.setVisible(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(cancelPackBind);
                        ADFBeanUtils.getOperationBinding("allowFreezePrf").execute();
                        ADFModelUtils.showFormattedFacesMessage("Your Packaging Material Profile has been Saved Successfully.",
                                                                "Packaging Profile is Freezed. ",
                                                                FacesMessage.SEVERITY_INFO);
                    } else {
                        ADFModelUtils.showFormattedFacesMessage("Your Profile has not been saved Successfully.", "",
                                                                FacesMessage.SEVERITY_ERROR);
                    }
                } else {
                    ADFModelUtils.showFormattedFacesMessage("Please Allocate the Lot/Bin/Serial for all the Packaging Material Items.",
                                                            "Some Items have not been issued properly.",
                                                            FacesMessage.SEVERITY_ERROR);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(allocateBinBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(allocateLotBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(autoIssueBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(allocateSerialBindVar);
            }
        } */
    }
    private String freeze_profile = "V";

    public void setFreeze_profile(String freeze_profile) {
        this.freeze_profile = freeze_profile;
    }

    public String getFreeze_profile() {
        return freeze_profile;
    }

    public void freezePackProfileValueChangeListener(ValueChangeEvent vce) {
        // Add event code here...luec
        if (vce.getNewValue() != null) {
            System.out.println("freezePackProfileValueChangeListener :::::::::::::  ");
            System.out.println("vce.getNewValue() :::::::::::::::" + vce.getNewValue());
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            if (vce.getNewValue().equals(true)) {
                freeze_profile = "A";
                /*  OperationBinding allocateBindvar = ADFBeanUtils.getOperationBinding("validateLotBinSRForEachPck");

                allocateBindvar.execute();
                if (allocateBindvar.getResult() != null) {
                    String resValid = allocateBindvar.getResult().toString();
                    if (resValid.equalsIgnoreCase("Y")) {
                        showPopup(freezePopup, true);
                    } */

                /*   else if (resValid.equalsIgnoreCase("N")) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "There Must be Some Lot,Bin or Serial which are not issued Properly..",
                                                                      "Please Select the required Lot,Bin or serial"));

                    }
                } */


            }
        }

    }

    public void duplicateItmIdValidatorVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        OperationBinding allocateBind = ADFBeanUtils.getOperationBinding("chkDuplicateItemforPck");

        allocateBind.execute();
    }

    public void itmDuplicateVCL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            System.out.println("Object Value in Item Duplicate Validator--" + object.toString());
            OperationBinding allocateBind = ADFBeanUtils.getOperationBinding("chkDuplicateItemforPck");
            allocateBind.getParamsMap().put("itmIdBind", object.toString());
            allocateBind.execute();
            String object_2 = allocateBind.getResult().toString();
            if (object_2.equalsIgnoreCase("Y")) {
                System.out.println("Inside flag...");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Item Id Exists..",
                                                              "Please Select Another Item Name.."));
            }
        }
    }

    public void setSavePckBind(RichLink savePckBind) {
        this.savePckBind = savePckBind;
    }

    public RichLink getSavePckBind() {
        return savePckBind;
    }

    public void setBindLotPckMtrl(RichTable bindLotPckMtrl) {
        this.bindLotPckMtrl = bindLotPckMtrl;
    }

    public RichTable getBindLotPckMtrl() {
        return bindLotPckMtrl;
    }

    public void setBindBinPckMtrl(RichTable bindBinPckMtrl) {
        this.bindBinPckMtrl = bindBinPckMtrl;
    }

    public RichTable getBindBinPckMtrl() {
        return bindBinPckMtrl;
    }

    public void setBindSrPckMtrl(RichTable bindSrPckMtrl) {
        this.bindSrPckMtrl = bindSrPckMtrl;
    }

    public RichTable getBindSrPckMtrl() {
        return bindSrPckMtrl;
    }

    public void setFreezePrfBind(RichSelectBooleanCheckbox freezePrfBind) {
        this.freezePrfBind = freezePrfBind;
    }

    public RichSelectBooleanCheckbox getFreezePrfBind() {
        return freezePrfBind;
    }

    public void setPckEditBind(RichCommandImageLink pckEditBind) {
        this.pckEditBind = pckEditBind;
    }

    public RichCommandImageLink getPckEditBind() {
        return pckEditBind;
    }

    public void setPckAddBind(RichCommandImageLink pckAddBind) {
        this.pckAddBind = pckAddBind;
    }

    public RichCommandImageLink getPckAddBind() {
        return pckAddBind;
    }

    public void itemDescVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding allocateBind1 = ADFBeanUtils.getOperationBinding("setAvlStk");
            allocateBind1.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(availQtyBindVar);
            OperationBinding allocateBind = ADFBeanUtils.getOperationBinding("executeLovItem");
            allocateBind.execute();
        }

    }

    public void availQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            Object availStkO = uIComponent.getAttributes().get("availStk");
            Number availStk = (availStkO == null ? StaticValue.NUMBER_ZERO : (Number) availStkO);
            Number pickQty = (Number) object;
            if (pickQty.compareTo(StaticValue.NUMBER_ZERO) > 0) {
                if (pickQty.compareTo(availStk) > 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Item Available Quantity is less than Picked Quantity.",
                                                                  "Please enter valid quantity."));
                }
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Item Available Quantity Must Be Greater Than Zero.",
                                                              "Please enter valid quantity."));
            }
        }
    }

    public void setWhIdBinding(RichSelectOneChoice whIdBinding) {
        this.whIdBinding = whIdBinding;
    }

    public RichSelectOneChoice getWhIdBinding() {
        return whIdBinding;
    }

    public void validateFreezePrfValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("Object value for Freeze Prf--" + object);
        if (object != null && object.equals(true)) {
            OperationBinding allocateBindvar = ADFBeanUtils.getOperationBinding("validateLotBinSRForEachPck");

            allocateBindvar.execute();
            if (allocateBindvar.getResult() != null) {
                String resValid = allocateBindvar.getResult().toString();
                if (resValid.equalsIgnoreCase("Y")) {

                } else if (resValid.equalsIgnoreCase("N")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "There Must be Some Lot,Bin or Serial which are not issued Properly..",
                                                                  "Please Select the required Lot,Bin or serial"));

                }
            }
        }
    }

    public void cancelPckPrfVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            if (vce.getNewValue().equals(true)) {
                System.out.println("INside cancel profile VCL :::::::::" + vce.getNewValue());
                showPopup(cancelPopupbind, true);
            }
        }
    }

    /**
     * Action to be called on SaveAndForwardAction
     * @return
     */
    public String saveAndForwardAction() {
        OperationBinding rej = ADFBeanUtils.getOperationBinding("chkSoAdvance");
        rej.execute();
        Boolean res = (Boolean) rej.getResult();
        if (res) {
            OperationBinding aa = ADFBeanUtils.getOperationBinding("areTablesToBeUpdatedLocked");
            aa.execute();
            Object q = aa.getResult();
            Boolean b = (q == null ? true : (Boolean) q);
            if (!b) {
                OperationBinding www = ADFBeanUtils.getOperationBinding("functionsCallOnPickSave");
                www.getParamsMap().put("flag", "M");
                www.execute();
                Object o = www.getResult();
                if (o != null && o.toString().equals("V")) {
                    pick_mode = "V";
                    OperationBinding binding = ADFBeanUtils.getOperationBinding("getWorkFlowIdForPick");
                    binding.execute();
                    wfIdForPick = binding.getResult().toString();
                    OperationBinding ob = ADFBeanUtils.getOperationBinding("callWfFunctions");
                    ob.execute();
                    Object object = ob.getResult();
                    if (object != null && "Y".equals(object.toString())) {
                        pick_mode = "V";
                        return "goToWf";
                    } else {
                        return null;
                    }
                }
            }
        }
        return null;

    }

    public void savePickAftrResACTION(ActionEvent actionEvent) {
        StringBuilder saveMsg;
        OperationBinding u = ADFBeanUtils.getOperationBinding("isCheckForExiseValid");
        u.execute();
        System.out.println("u.getResult() : " + u.getResult());
        Object excChk = (u.getResult() == null ? false : u.getResult());
        if (!(Boolean) excChk) {

        } else {
            DCIteratorBinding parentIter = (DCIteratorBinding) getBindings().get("SlsPick1Iterator");
            Key parentKey = parentIter.getCurrentRow().getKey();
            _log.info("Parent Key before commit-" + parentKey);
            OperationBinding valArr = executeOperation("checkItemIssueValidation");
            valArr.execute();
            System.out.println("valArr " + valArr.getResult());
            if (valArr.getResult() != null) {
                ArrayList<String> itmArr = (ArrayList<String>) valArr.getResult();
                System.out.println("itmArr  =" + itmArr.size());
                if (itmArr.size() != 0) {
                    /* saveMsg =
                                        new StringBuilder("<html><body><b><p style='color:red'>Following Items are not issued with full quantity,Can't Save</p></b>"); */
                    saveMsg =
                        new StringBuilder("<html><body><b><p style='color:red'>" + ADFModelUtils.resolvRsrc("MSG.920") +
                                          "</p></b>");
                    saveMsg.append("<ul>");
                    for (String a : itmArr) {
                        saveMsg.append("<li> <b>" + a + "</b></li>");
                    }
                    saveMsg.append("</ul><br>");
                    //saveMsg.append("<b>What to Do:");
                    saveMsg.append("<b>" + ADFModelUtils.resolvRsrc("MSG.871") + "");
                    //saveMsg.append("<ul style='color:darkgreen'><li>Isuue all Items with full quantity from LOT</li><li>Delete Un-Issued Items</li></ul></b>");
                    saveMsg.append("<ul style='color:darkgreen'><li>" + ADFModelUtils.resolvRsrc("MSG.872") +
                                   "</li><li>" + ADFModelUtils.resolvRsrc("MSG.873") + "</li></ul></b>");
                    saveMsg.append("</body></html>");
                    FacesMessage msg = new FacesMessage(saveMsg.toString());
                    msg.setSeverity(FacesMessage.SEVERITY_WARN);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {

                    OperationBinding ob = ADFBeanUtils.getOperationBinding("functionsCallOnPickSave");
                    ob.getParamsMap().put("flag", "S");
                    ob.execute();
                    Object o = ob.getResult();
                    if (o != null && o.toString().equals("V")) {
                        pick_mode = "V";
                        pickReservMode = "N";
                    }
                }
            }
        }
    }

    public void reservStockPickACTION(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.findOperation("isAutoIssueItemExist");
        binding.execute();
        Object on = binding.getResult();
        System.out.println(on + " Result on ");
        autoIssuMode = (on == null ? false : (Boolean) on);
        pickReservMode = "Y";
    }

    public void autoIssueItemsAfterAddACTION(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("autoIssueAddedItems");
        binding.execute();
    }

    public void setAvailQtyBindVar(RichInputText availQtyBindVar) {
        this.availQtyBindVar = availQtyBindVar;
    }

    public RichInputText getAvailQtyBindVar() {
        return availQtyBindVar;
    }

    public void deletePcKMtrlAction(ActionEvent actionEvent) {
        executeOperation("Delete").execute();

    }

    public void freezeDialogListner(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding allocateBind = ADFBeanUtils.getOperationBinding("freezeProfile");
            allocateBind.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(freezePrfBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pckEditBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pckAddBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cancelPackBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(addPckMtrlBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cancelPckMtrlBind);
        }

    }


    public void setFreezePopup(RichPopup freezePopup) {
        this.freezePopup = freezePopup;
    }

    public RichPopup getFreezePopup() {
        return freezePopup;
    }

    public void cancelPrfDIalogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding allocateBindvar = ADFBeanUtils.getOperationBinding("cancelPckPrf1");
            allocateBindvar.execute();
            cancelPackBind.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cancelPackBind);
        } else {
            cancelPackBind.setVisible(true);
            cancelPackBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cancelPackBind);
        }
    }

    public void setCancelPopupbind(RichPopup cancelPopupbind) {
        this.cancelPopupbind = cancelPopupbind;
    }

    public RichPopup getCancelPopupbind() {
        return cancelPopupbind;
    }

    public void freezepopupCancel(PopupCanceledEvent popupCanceledEvent) {

        freeze_profile = "V";
        freezePrfBind.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(freezePrfBind);

    }

    public void setCancelPackBind(RichSelectBooleanCheckbox cancelPackBind) {
        this.cancelPackBind = cancelPackBind;
    }

    public RichSelectBooleanCheckbox getCancelPackBind() {
        return cancelPackBind;
    }

    public void setAddPckMtrlBind(RichLink addPckMtrlBind) {
        this.addPckMtrlBind = addPckMtrlBind;
    }

    public RichLink getAddPckMtrlBind() {
        return addPckMtrlBind;
    }

    public void setCancelPckMtrlBind(RichLink cancelPckMtrlBind) {
        this.cancelPckMtrlBind = cancelPckMtrlBind;
    }

    public RichLink getCancelPckMtrlBind() {
        return cancelPckMtrlBind;
    }

    public void setAllocateLotBindVar(RichLink allocateLotBindVar) {
        this.allocateLotBindVar = allocateLotBindVar;
    }

    public RichLink getAllocateLotBindVar() {
        return allocateLotBindVar;
    }

    public void setAutoIssueBindVar(RichLink autoIssueBindVar) {
        this.autoIssueBindVar = autoIssueBindVar;
    }

    public RichLink getAutoIssueBindVar() {
        return autoIssueBindVar;
    }

    public void setAllocateBinBindVar(RichLink allocateBinBindVar) {
        this.allocateBinBindVar = allocateBinBindVar;
    }

    public RichLink getAllocateBinBindVar() {
        return allocateBinBindVar;
    }

    public void setAllocateSerialBindVar(RichLink allocateSerialBindVar) {
        this.allocateSerialBindVar = allocateSerialBindVar;
    }

    public RichLink getAllocateSerialBindVar() {
        return allocateSerialBindVar;
    }

    public void AllocateLotBinACTION(ActionEvent actionEvent) {
        packFreezeMode = "Y";
        AdfFacesContext.getCurrentInstance().addPartialTarget(savePckBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(allocateBinBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(allocateLotBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(autoIssueBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(allocateSerialBindVar);
    }

    public void cancelPrfListener(PopupCanceledEvent popupCanceledEvent) {

        cancelPackBind.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cancelPackBind);
    }

    public void totalNoOfPacketsValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number pcktsNo = (oracle.jbo.domain.Number) object;
            if (pcktsNo.compareTo(0) == 1 || pcktsNo.compareTo(0) == 0) {
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Weight",
                                                               "Can not be negative")); */
                if (isPrecisionValid(26, 6, pcktsNo)) {

                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Quantity !!",
                                                                  ADFModelUtils.resolvRsrc("MSG.894")));
                }

            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Quantity !!",
                                                              ADFModelUtils.resolvRsrc("MSG.894")));
            }
        }
    }

    /**Method to navigate from Search page to PickList page
     * @return
     */
    public String goToPickListAction() {

        if ((goToDestBind.getValue() != null && goToDestBind.getValue().equals("P"))) {
            executeOperation("goToPickListAction").execute();
            destGo = "P";
            RequestContext.getCurrentInstance().getPageFlowScope().put("NAV_PARAM", "P");
            return "goToPickList";
        } else {
            destGo = "S";
            RequestContext.getCurrentInstance().getPageFlowScope().put("NAV_PARAM", "S");
            return "goToShipDirect";
        }
    }

    public String goStraightToShipmentAction() {
        destGo = "S";
        RequestContext.getCurrentInstance().getPageFlowScope().put("NAV_PARAM", "S");
        return "goToShipDirect";
    }

    public void pickFilterACE(ActionEvent actionEvent) {
        String pickId = actionEvent.getComponent().getAttributes().get("pickDocId").toString();
        if ((goToDestBind.getValue() != null && goToDestBind.getValue().equals("S"))) {

            OperationBinding ob = ADFBeanUtils.getOperationBinding("filterSelectedShipment");
            ob.getParamsMap().put("pickDocId", pickId);
            ob.getParamsMap().put("shipDocId", null);
            ob.execute();

        }
        System.out.println(pickId + " ===== Selected pick doc id");
        RequestContext.getCurrentInstance().getPageFlowScope().put("PICK_DOC_ID", pickId);
    }

    public void shipFilterACE(ActionEvent actionEvent) {
        String pickId = actionEvent.getComponent().getAttributes().get("pickDocId").toString();
        OperationBinding ob = ADFBeanUtils.getOperationBinding("filterSelectedShipment");
        ob.getParamsMap().put("pickDocId", pickId);
        ob.getParamsMap().put("shipDocId", actionEvent.getComponent().getAttributes().get("shipDocId"));
        ob.execute();
        System.out.println(pickId + " ===== Selected pick doc id");
        RequestContext.getCurrentInstance().getPageFlowScope().put("PICK_DOC_ID", pickId);
    }


    //Action to go to cost center detail service page
    /*     public String costCenterAction() {
        OperationBinding binding = executeOperation("chkCcApplicableOrNot");
        binding.execute();
        setItemLotAmt(itemLotAmt);
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            return "costCenter";
        } else {
            return null;
        }
    } */


    public void setCurrIdSpBinding(RichSelectOneChoice currIdSpBinding) {
        this.currIdSpBinding = currIdSpBinding;
    }

    public RichSelectOneChoice getCurrIdSpBinding() {
        return currIdSpBinding;
    }

    /**
     * Method to pick Header Details from Sales Order
     * @param actionEvent
     */
    public void fetchPickHeaderDtlsACTION(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("fetchPickHeaderDetailsFromSo");
        binding.execute();
        Object o = binding.getResult();
        Integer i = (o == null ? -1 : (Integer) o);
        if (i == 1) {
            ADFModelUtils.showFacesMessage("Source Document Id have not been selected !",
                                           "Please select a valid Source Document Id.", FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(actionEvent.getComponent(),
                                                                      "soIdForSelectionTransId"));
        }
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


    //Method to search the serialized item to select the item

    private String searchValue = "";
    private Key key;

    public void setKey(Key key) {
        this.key = key;
    }

    public Key getKey() {
        return key;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void searchSrNoACE(ActionEvent actionEvent) {

        if (dualSrNoBinding.getValue() != null) {
            String itmSr = dualSrNoBinding.getValue().toString();
            System.out.println("item serial no ---->>>> " + itmSr);

            setSearchValue(itmSr);
            DCIteratorBinding it = ADFBeanUtils.findIterator("ViewMmStkSummSr1Iterator");
            RowSetIterator rsi = it.getRowSetIterator();
            RowKeySet oldSelection = srForItemTableBinding.getSelectedRowKeys();

            if (rsi.first() != null) {
                //System.out.println("Inside of the loop");
                Row r = rsi.first();
                while (rsi.hasNext() && getKey() == null && (!matchFoundInput(r, oldSelection))) {
                    r = rsi.next();
                }

            }
        }

    }

    private boolean matchFoundInput(Row r, RowKeySet oldSelection) {
        setKey(null);
        //System.out.println("inside of the match function");
        ArrayList lst = new ArrayList(1);
        RowKeySetImpl newSelection = new RowKeySetImpl();
        Key key = null;
        String rowValue = r.getAttribute("SrNo").toString();
        if (rowValue.toString().contains(searchValue)) {
            // System.out.println("now setting key to " + key);
            key = r.getKey();
            lst.add(key);
            srForItemTableBinding.setActiveRowKey(lst);
            newSelection.add(lst);
            makeCurrentInput(srForItemTableBinding, newSelection, oldSelection);
            return true;
        } else {

        }
        return false;

    }

    private void makeCurrentInput(RichTable outputItemTableBind, RowKeySet newCurrentRow, RowKeySet oldCurrentRow) {
        //To make a row current, we need to create a SelectionEvent which
        //expects the following arguments: component, unselected_keys,
        //selected_keys. In our example, we don't have unselected keys and
        //therefore create an empty RowSet for this
        SelectionEvent selectionEvent = new SelectionEvent(oldCurrentRow, newCurrentRow, outputItemTableBind);
        selectionEvent.queue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(outputItemTableBind);
    }

    public void setSrForItemTableBinding(RichTable srForItemTableBinding) {
        this.srForItemTableBinding = srForItemTableBinding;
    }

    public RichTable getSrForItemTableBinding() {
        return srForItemTableBinding;
    }

    public void setDualSrNoBinding(RichOutputText dualSrNoBinding) {
        this.dualSrNoBinding = dualSrNoBinding;
    }

    public RichOutputText getDualSrNoBinding() {
        return dualSrNoBinding;
    }

    public void setArrivalPntPgBind(RichOutputText arrivalPntPgBind) {
        this.arrivalPntPgBind = arrivalPntPgBind;
    }

    public RichOutputText getArrivalPntPgBind() {
        return arrivalPntPgBind;
    }

    public void setDeptPointPgBind(RichOutputText deptPointPgBind) {
        this.deptPointPgBind = deptPointPgBind;
    }

    public RichOutputText getDeptPointPgBind() {
        return deptPointPgBind;
    }

    public void createRouteInTransportDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            _log.info("In OK Outcome");
            OperationBinding binding = ADFBeanUtils.getOperationBinding("createRouteInTransport");
            binding.execute();
        }
    }

    public void setCreateNewRoutePopup(RichPopup createNewRoutePopup) {
        this.createNewRoutePopup = createNewRoutePopup;
    }

    public RichPopup getCreateNewRoutePopup() {
        return createNewRoutePopup;
    }

    public String createNewRouteAction() {
        ADFBeanUtils.showPopup(createNewRoutePopup, true);
        return null;
    }

    public void enterUsrDefindSrNoACE(ActionEvent actionEvent) {
        ADFBeanUtils.showPopup(usrDefindSrNoPopupBinding, true);
    }

    public void addUsrDefindSerialACE(ActionEvent actionEvent) {
        OperationBinding ao = ADFBeanUtils.findOperation("addUserDefinedSrForItem");
        ao.execute();
        if (ao.getResult() != null) {
            if ("N".equalsIgnoreCase(ao.getResult().toString())) {
                ADFModelUtils.showFacesMessage("Serial Value is Required", "Pleaes Enter Serial Value !",
                                               FacesMessage.SEVERITY_ERROR,
                                               getComponentCliendIdFromId(actionEvent.getComponent(), "it4"));
            }
        }
    }

    public void setUsrDefindSrNoPopupBinding(RichPopup usrDefindSrNoPopupBinding) {
        this.usrDefindSrNoPopupBinding = usrDefindSrNoPopupBinding;
    }

    public RichPopup getUsrDefindSrNoPopupBinding() {
        return usrDefindSrNoPopupBinding;
    }
    private Boolean allowUsrDefindSrNote = null;

    public Boolean getAllowUsrDefindSrNote() {
        if (allowUsrDefindSrNote == null) {
            OperationBinding o = ADFBeanUtils.findOperation("chkMMPrf");
            o.execute();
            if (o.getResult() != null)
                allowUsrDefindSrNote = (Boolean) o.getResult();
        }
        return allowUsrDefindSrNote;
    }

    public void removeusrDefinSrACE(ActionEvent actionEvent) {
        OperationBinding ao = ADFBeanUtils.findOperation("removeUsrDefindSerial");
        ao.getParamsMap().put("sr_No", actionEvent.getComponent().getAttributes().get("srNo"));
        ao.execute();
    }

    public void netWeightValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            oracle.jbo.domain.Number wght = (oracle.jbo.domain.Number) object;

            if (wght.compareTo(0) == 1 || wght.compareTo(0) == 0) {
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Weight",
                                                               "Can not be negative")); */
                if (isPrecisionValid(26, 6, wght)) {

                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  ADFModelUtils.resolvRsrc("MSG.57"),
                                                                  ADFModelUtils.resolvRsrc("MSG.894")));
                }

            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.965"),
                                                              ADFModelUtils.resolvRsrc("MSG.894")));
            }
        }


    }

    public void setPackTabBind(RichShowDetailItem packTabBind) {
        this.packTabBind = packTabBind;
    }

    public RichShowDetailItem getPackTabBind() {
        return packTabBind;
    }

    public void packItemValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...



    }
}
