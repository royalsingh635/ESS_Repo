package MMMtlMis.view.bean;

//import MMMtlMis.model.service.MMMtlMisAMImpl;

import adf.utils.bean.ADFBeanUtils;

import java.math.BigDecimal;

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
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.SpringboardChangeEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class MisMtlBean {
    public static ADFLogger adfLog = (ADFLogger) ADFLogger.createADFLogger(MisMtlBean.class);
    private UIXSwitcher bindAdfSwitcher;
    private boolean serialized = false;
    private boolean lnkdisable = false;
    private boolean binVisble = true;
    private RichInputText bindOpeningQty;
    private RichInputText bindClosingQty;
    private RichInputDate bindFromDate;
    private RichInputDate bindToDate;
    private RichSelectOneChoice bindWarehouseId;

    private boolean columnVisible = true;
    private boolean disableItm = true;

    private boolean disableOrgWh = false;

    private RichTreeTable bindAdfTree;
    private RichSelectBooleanCheckbox includeItemBind;
    private UIXSwitcher bindSwitcher;
    private RichInputDate createFromDate;
    private RichInputDate createToDate;
    private RichInputDate modifyFromDate;
    private RichInputDate modifyToDate;
    private RichSelectOneChoice bindingOrgId;
    private RichSelectOneChoice bindingWarehouseId;
    private RichInputListOfValues bindingSupplierId;
    private RichInputListOfValues bindingItmGrpId;
    private RichInputListOfValues bindingItmId;
    private RichSelectBooleanCheckbox bindingSafetyQty;
    private RichSelectBooleanCheckbox bindingReorderLvl;
    private RichSelectBooleanCheckbox bindingSaleItm;
    private RichSelectBooleanCheckbox bindingPurchaseItm;
    private RichSelectBooleanCheckbox bindingWIPItm;
    private RichSelectOneChoice bindingOrgId_1;
    private RichSelectOneChoice bindingWhId_1;
    private RichInputListOfValues bindingItmId_1;
    private RichCommandImageLink bindMnthJan;
    private RichCommandImageLink bindQuatQ1;
    private String mode = "V";


    private String setQ1 = "NONE";
    private String setQ2 = "NONE";
    private String setQ3 = "NONE";
    private String setQ4 = "NONE";

    private RichCommandImageLink bindQuatQ2;
    private RichCommandImageLink bindQuatQ3;
    private RichCommandImageLink bindQuatQ4;
    private RichCommandImageLink bindMnthFeb;
    private RichCommandImageLink bindMnthMar;
    private RichCommandImageLink bindMnthApr;
    private RichCommandImageLink bindMnthMay;
    private RichCommandImageLink bindMnthJun;
    private RichCommandImageLink bindMnthJul;
    private RichCommandImageLink bindMnthAug;
    private RichCommandImageLink bindMnthSep;
    private RichCommandImageLink bindMnthOct;
    private RichCommandImageLink bindMnthNov;
    private RichCommandImageLink bindMnthDec;

    private String selectedMnth = "NONE";
    private String selectedParam = "NONE";
    private UIXSwitcher productAgeswitcher;
    private RichPanelGroupLayout panelGrpBind;
    private RichSelectOneChoice bindOrg;
    private RichSelectOneChoice bindWh;
    private RichInputListOfValues bindItm;
    private RichPopup addtempPopup;
    private RichPopup saveTemplatePopUP;
    private RichInputListOfValues bindtemplItm;
    private RichInputText templtBind;
    private RichInputText bindTempNm;
    private RichSelectOneChoice bindRangetype;
    private RichSelectBooleanCheckbox bindcheck;
    private String var;
    private RichTable prdctTabBind;
    private RichInputText tempNmBind;
    private RichSelectOneChoice bktRangeTypeBind;
    private RichSelectOneChoice whNmTempBind;
    private String springBoardDispMode = "grid";
    private String discloseTab = null;

    public void setDiscloseTab(String discloseTab) {
        this.discloseTab = discloseTab;
    }

    public String getDiscloseTab() {
        return discloseTab;
    }

    public void setSpringBoardDispMode(String springBoardDispMode) {
        this.springBoardDispMode = springBoardDispMode;
    }

    public String getSpringBoardDispMode() {
        return springBoardDispMode;
    }

    public MisMtlBean() {
    }


    public void setBindAdfSwitcher(UIXSwitcher bindAdfSwitcher) {
        this.bindAdfSwitcher = bindAdfSwitcher;
    }

    public UIXSwitcher getBindAdfSwitcher() {
        return bindAdfSwitcher;
    }

    public void StkItmLotAction(ActionEvent actionEvent) {
        // Add event code here...
        String binAvail = (String) evaluateEL("#{pageFlowScope.GLBL_ORG_BIN_CHK}");
        OperationBinding op = getBindings().getOperationBinding("executeStkItmLotVo");
        op.execute();
        op = getBindings().getOperationBinding("isItmSerialized");
        op.execute();
        serialized = (Boolean) op.getResult();
        if (serialized) {
            if (binAvail.equals("Y")) {
                lnkdisable = false;
                bindAdfSwitcher.setFacetName("ItemLotFacet");
            } else {
                lnkdisable = false;
                bindAdfSwitcher.setFacetName("ItemLotSrFacet");
            }
        }

        else {
            if (binAvail.equals("Y")) {
                bindAdfSwitcher.setFacetName("ItemLotFacet");
                lnkdisable = false;
            } else {
                lnkdisable = true;
                bindAdfSwitcher.setFacetName("ItemLotFacet");
            }

        }
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void backToStkItm(ActionEvent actionEvent) {
        // Add event code here...
        bindAdfSwitcher.setFacetName("ItemFacet");
    }

    public void setSerialized(boolean serialized) {
        this.serialized = serialized;
    }

    public boolean isSerialized() {
        return serialized;
    }

    public void backToItemLot(ActionEvent actionEvent) {
        // Add event code here...
        bindAdfSwitcher.setFacetName("ItemLotFacet");
    }

    public void StkItmBinAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("isLotItmSerialized");
        op.execute();
        serialized = (Boolean) op.getResult();
        op = getBindings().getOperationBinding("executeStkItmBinVo");
        op.execute();
        bindAdfSwitcher.setFacetName("ItemBinFacet");

    }

    public void backToStkBin(ActionEvent actionEvent) {
        // Add event code here...
        if (binVisble) {
            bindAdfSwitcher.setFacetName("ItemBinFacet");
        } else {
            bindAdfSwitcher.setFacetName("ItemLotSrFacet");
        }
    }

    public void StkItmSrAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("executeStkItmSrVo");
        op.execute();
        binVisble = true;
        bindAdfSwitcher.setFacetName("ItemSrFacet");
    }

    public void setLnkdisable(boolean lnkdisable) {
        this.lnkdisable = lnkdisable;
    }

    public boolean isLnkdisable() {
        return lnkdisable;
    }

    public static String evaluateEL(String el) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);
        String retVal = exp.getValue(elContext).toString();
        return retVal;
    }

    private static Object evaluateEL1(String el) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);
        return exp.getValue(elContext);
    }

    public void setBinVisble(boolean binVisble) {
        this.binVisble = binVisble;
    }

    public boolean isBinVisble() {
        return binVisble;
    }

    public void executeStkItmLotSrVo(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("executeStkItmLotSrVo");
        op.execute();
        binVisble = false;
        bindAdfSwitcher.setFacetName("ItemSrFacet");

    }

    public void callStockPoAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("executePoVo");
        op.getParamsMap().put("voName", "PoVw1");
        op.getParamsMap().put("voCriteria", "PoVwVOCriteria");
        op.execute();
        bindAdfSwitcher.setFacetName("ItemPoFacet");
    }

    public void searchAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("executeStkItmVo");
        op.execute();
        bindAdfSwitcher.setFacetName("ItemFacet");

        if (bindWarehouseId.getValue() == null || bindWarehouseId.getValue().toString().length() <= 0) {
            columnVisible = true;
        }

        else {
            columnVisible = false;
        }
    }

    public void callStockResvAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("executeResvVo");
        op.getParamsMap().put("voName", "ResvVw1");
        op.getParamsMap().put("voCriteria", "ResvVwVOCriteria");
        op.execute();
        bindAdfSwitcher.setFacetName("ItemResvFacet");
    }

    public void callComRcptAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("executeResvVo");
        op.getParamsMap().put("voName", "ComRcpt1");
        // op.getParamsMap().put("voName","ComRcpt2");
        op.getParamsMap().put("voCriteria", "ComRcptVOCriteria");
        op.execute();
        bindAdfSwitcher.setFacetName("ItemComRcptFacet");
    }

    public void callComIssuAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("executeResvVo");
        op.getParamsMap().put("voName", "ComIssu1");
        op.getParamsMap().put("voCriteria", "ComIssuVOCriteria");
        op.execute();
        bindAdfSwitcher.setFacetName("ItemComIssuFacet");
    }

    public String searchItmMovAction() {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("executeItmMovVo");
        op.getParamsMap().put("voName", "ItmMov1");
        op.getParamsMap().put("voCriteria", "ItmMovVOCriteria");
        op.execute();

        op = getBindings().getOperationBinding("getStockValue");
        op.getParamsMap().put("date", bindFromDate.getValue());
        op.execute();
        BigDecimal stkVal = (BigDecimal) op.getResult();
        bindOpeningQty.setValue(stkVal);

        op = getBindings().getOperationBinding("getStockValue");
        op.getParamsMap().put("date", bindToDate.getValue());
        op.execute();
        stkVal = (BigDecimal) op.getResult();
        bindClosingQty.setValue(stkVal);


        return null;
    }

    public void setBindOpeningQty(RichInputText bindOpeningQty) {
        this.bindOpeningQty = bindOpeningQty;
    }

    public RichInputText getBindOpeningQty() {
        return bindOpeningQty;
    }

    public void setBindClosingQty(RichInputText bindClosingQty) {
        this.bindClosingQty = bindClosingQty;
    }

    public RichInputText getBindClosingQty() {
        return bindClosingQty;
    }

    public void setBindFromDate(RichInputDate bindFromDate) {
        this.bindFromDate = bindFromDate;
    }

    public RichInputDate getBindFromDate() {
        return bindFromDate;
    }

    public void setBindToDate(RichInputDate bindToDate) {
        this.bindToDate = bindToDate;
    }

    public RichInputDate getBindToDate() {
        return bindToDate;
    }

    public void setBindWarehouseId(RichSelectOneChoice bindWarehouseId) {
        this.bindWarehouseId = bindWarehouseId;
    }

    public RichSelectOneChoice getBindWarehouseId() {
        return bindWarehouseId;
    }

    public void setColumnVisible(boolean columnVisible) {
        this.columnVisible = columnVisible;
    }

    public boolean isColumnVisible() {
        return columnVisible;
    }

    public void setBindAdfTree(RichTreeTable bindAdfTree) {
        this.bindAdfTree = bindAdfTree;
    }

    public RichTreeTable getBindAdfTree() {
        return bindAdfTree;
    }

    /**Method to get Iterator*/
    public RowIterator getSelectedNodeIterator() {
        if (getBindAdfTree() != null && getBindAdfTree().getSelectedRowKeys() != null) {
            for (Object rKey : getBindAdfTree().getSelectedRowKeys()) {
                getBindAdfTree().setRowKey(rKey);
                return ((JUCtrlHierNodeBinding) getBindAdfTree().getRowData()).getRowIterator();
            }
        }
        return null;
    }

    /**Method to get Node Key*/
    public Key getSelectedNodeKey() {
        if (getBindAdfTree() != null && getBindAdfTree().getSelectedRowKeys() != null) {
            for (Object rKey : getBindAdfTree().getSelectedRowKeys()) {
                getBindAdfTree().setRowKey(rKey);
                return ((JUCtrlHierNodeBinding) getBindAdfTree().getRowData()).getRowKey();
            }
        }
        return null;
    }


    public void showCurRowDetail(RowIterator ri, Key selectedNodeKey) {
        Row[] rows = ri.findByKey(selectedNodeKey, 1);
        FacesMessage msg = new FacesMessage(rows[0].getAttribute("FirstName") + "'s data available in this row");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }


    public void searchItmAction(ActionEvent actionEvent) {
        // Add event code here...
        Object obj = includeItemBind.getValue();
        System.out.println(" before if and else bindingWarehouseId.getValue()" + bindingWarehouseId.getValue());
        // System.out.println("Obj : "+obj);
        // if(bindingWarehouseId.getValue()!=null && bindingWarehouseId.getValue()!=""){
        System.out.println("if part bindingWarehouseId.getValue()" + bindingWarehouseId.getValue());
        OperationBinding op = getBindings().getOperationBinding("searchMainAction");
        op.getParamsMap().put("voName", "");
        op.getParamsMap().put("voCriteria", "");
        op.getParamsMap().put("includeParam", (Boolean) obj);
        op.getParamsMap().put("bindFlg", selectedParam);
        op.execute();
        bindSwitcher.setFacetName("MainFacet");


        //        }else{
        //            System.out.println("else part bindingWarehouseId.getValue()"+bindingWarehouseId.getValue());
        //            FacesMessage message = new FacesMessage("Warehouse is Required");
        //            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        //            FacesContext fc = FacesContext.getCurrentInstance();
        //            fc.addMessage(bindingWarehouseId.getClientId(), message);
        //        }

    }

    public void setDisableAction(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean b = (Boolean) valueChangeEvent.getNewValue();
        if (b) {
            disableItm = false;
        } else {
            disableItm = true;
        }
    }

    public void setDisableItm(boolean disableItm) {
        this.disableItm = disableItm;
    }

    public boolean isDisableItm() {
        return disableItm;
    }

    public void setIncludeItemBind(RichSelectBooleanCheckbox includeItemBind) {
        this.includeItemBind = includeItemBind;
    }

    public RichSelectBooleanCheckbox getIncludeItemBind() {
        return includeItemBind;
    }

    public void CallReceiptVw(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("executeReceiptVo_1");
        op.getParamsMap().put("voName", "Receipt1");
        // op.getParamsMap().put("voCriteria","RcptVOCriteria");
        op.execute();
        bindSwitcher.setFacetName("ReceiptFacet");

    }

    public void setBindSwitcher(UIXSwitcher bindSwitcher) {
        this.bindSwitcher = bindSwitcher;
    }

    public UIXSwitcher getBindSwitcher() {
        return bindSwitcher;
    }


    public void StkItmLotAction_1(ActionEvent actionEvent) {
        // Add event code here...
        String binAvail = (String) evaluateEL("#{pageFlowScope.GLBL_ORG_BIN_CHK}");
        OperationBinding op = getBindings().getOperationBinding("executeStkItmLotVo_1");
        op.execute();
        Boolean b = (Boolean) op.getResult();
        disableOrgWh = !b;
        op = getBindings().getOperationBinding("isItmSerialized_1");
        op.execute();
        serialized = (Boolean) op.getResult();
        if (serialized) {
            if (binAvail.equals("Y")) {
                lnkdisable = false;
                bindSwitcher.setFacetName("ItemLotFacet");
            } else {
                lnkdisable = false;
                bindSwitcher.setFacetName("ItemLotSrFacet");
            }
        }

        else {
            if (binAvail.equals("Y")) {
                bindSwitcher.setFacetName("ItemLotFacet");
                lnkdisable = false;
            } else {
                lnkdisable = true;
                bindSwitcher.setFacetName("ItemLotFacet");
            }

        }

    }

    public void StkItmBinAction_1(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("isLotItmSerialized_1");
        op.execute();
        serialized = (Boolean) op.getResult();
        op = getBindings().getOperationBinding("executeStkItmBinVo_1");
        op.execute();
        bindSwitcher.setFacetName("ItemBinFacet");

    }

    public void StkItmSrAction_1(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("executeStkItmSrVo_1");
        op.execute();
        binVisble = true;
        bindSwitcher.setFacetName("ItemSrFacet");
    }


    public void backToItemLot_1(ActionEvent actionEvent) {
        // Add event code here...
        bindSwitcher.setFacetName("ItemLotFacet");
    }

    public void backToStkItm_1(ActionEvent actionEvent) {
        // Add event code here...
        bindSwitcher.setFacetName("MainFacet");
    }

    public void backToStkBin_1(ActionEvent actionEvent) {
        // Add event code here...
        if (binVisble) {
            bindSwitcher.setFacetName("ItemBinFacet");
        } else {
            bindSwitcher.setFacetName("ItemLotSrFacet");
        }
    }

    public void executeStkItmLotSrVo_1(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("executeStkItmLotSrVo_1");
        op.execute();
        binVisble = false;
        bindSwitcher.setFacetName("ItemSrFacet");

    }

    public void setDisableOrgWh(boolean disableOrgWh) {
        this.disableOrgWh = disableOrgWh;
    }

    public boolean isDisableOrgWh() {
        return disableOrgWh;
    }

    public void CallSupplierVw(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("executeSupplier");
        op.getParamsMap().put("voName", "SupplierVW1");
        op.execute();
        bindSwitcher.setFacetName("SupplierFacet");

    }

    public void backToReceipt(ActionEvent actionEvent) {
        // Add event code here...
        bindSwitcher.setFacetName("ReceiptFacet");
    }

    public void resetAction(ActionEvent actionEvent) {

        modifyFromDate.setValue("");
        modifyToDate.setValue("");

        //bindingOrgId.setValue("");
        bindingWarehouseId.setValue("");
        bindingSupplierId.setValue("");

        bindingItmGrpId.setValue("");
        bindingItmId.setValue("");

        bindingWIPItm.setValue("");
        bindingPurchaseItm.setValue("");
        bindingSaleItm.setValue("");
        bindingSafetyQty.setValue("");
        bindingReorderLvl.setValue("");
        includeItemBind.setValue("");
        //
        //        selectedMnth="NONE";
        //        selectedParam="";
        //        setQ1="NONE";
        //        setQ2="NONE";
        //        setQ3="NONE";
        //        setQ4="NONE";
        OperationBinding op = getBindings().getOperationBinding("resetAction");
        op.execute();
        //        op=  getBindings().getOperationBinding("searchMainAction");
        //        op.getParamsMap().put("voName","");
        //        op.getParamsMap().put("voCriteria","");
        //        op.getParamsMap().put("includeParam",Boolean.TRUE);
        //        op.execute();
        bindSwitcher.setFacetName("MainFacet");

        // Add event code here...
    }

    public void setCreateFromDate(RichInputDate createFromDate) {
        this.createFromDate = createFromDate;
    }

    public RichInputDate getCreateFromDate() {
        return createFromDate;
    }

    public void setCreateToDate(RichInputDate createToDate) {
        this.createToDate = createToDate;
    }

    public RichInputDate getCreateToDate() {
        return createToDate;
    }

    public void setModifyFromDate(RichInputDate modifyFromDate) {
        this.modifyFromDate = modifyFromDate;
    }

    public RichInputDate getModifyFromDate() {
        return modifyFromDate;
    }

    public void setModifyToDate(RichInputDate modifyToDate) {
        this.modifyToDate = modifyToDate;
    }

    public RichInputDate getModifyToDate() {
        return modifyToDate;
    }

    public void setBindingOrgId(RichSelectOneChoice bindingOrgId) {
        this.bindingOrgId = bindingOrgId;
    }

    public RichSelectOneChoice getBindingOrgId() {
        return bindingOrgId;
    }

    public void setBindingWarehouseId(RichSelectOneChoice bindingWarehouseId) {
        this.bindingWarehouseId = bindingWarehouseId;
    }

    public RichSelectOneChoice getBindingWarehouseId() {
        return bindingWarehouseId;
    }

    public void setBindingSupplierId(RichInputListOfValues bindingSupplierId) {
        this.bindingSupplierId = bindingSupplierId;
    }

    public RichInputListOfValues getBindingSupplierId() {
        return bindingSupplierId;
    }

    public void setBindingItmGrpId(RichInputListOfValues bindingItmGrpId) {
        this.bindingItmGrpId = bindingItmGrpId;
    }

    public RichInputListOfValues getBindingItmGrpId() {
        return bindingItmGrpId;
    }

    public void setBindingItmId(RichInputListOfValues bindingItmId) {
        this.bindingItmId = bindingItmId;
    }

    public RichInputListOfValues getBindingItmId() {
        return bindingItmId;
    }

    public void setBindingSafetyQty(RichSelectBooleanCheckbox bindingSafetyQty) {
        this.bindingSafetyQty = bindingSafetyQty;
    }

    public RichSelectBooleanCheckbox getBindingSafetyQty() {
        return bindingSafetyQty;
    }

    public void setBindingReorderLvl(RichSelectBooleanCheckbox bindingReorderLvl) {
        this.bindingReorderLvl = bindingReorderLvl;
    }

    public RichSelectBooleanCheckbox getBindingReorderLvl() {
        return bindingReorderLvl;
    }

    public void setBindingSaleItm(RichSelectBooleanCheckbox bindingSaleItm) {
        this.bindingSaleItm = bindingSaleItm;
    }

    public RichSelectBooleanCheckbox getBindingSaleItm() {
        return bindingSaleItm;
    }

    public void setBindingPurchaseItm(RichSelectBooleanCheckbox bindingPurchaseItm) {
        this.bindingPurchaseItm = bindingPurchaseItm;
    }

    public RichSelectBooleanCheckbox getBindingPurchaseItm() {
        return bindingPurchaseItm;
    }

    public void setBindingWIPItm(RichSelectBooleanCheckbox bindingWIPItm) {
        this.bindingWIPItm = bindingWIPItm;
    }

    public RichSelectBooleanCheckbox getBindingWIPItm() {
        return bindingWIPItm;
    }

    public void resetAction_1(ActionEvent actionEvent) {
        // Add event code here...
        //  bindingOrgId_1.setValue("");
        bindingWhId_1.setValue("");
        bindingItmId_1.setValue("");
        bindFromDate.setValue("");
        bindToDate.setValue("");
        OperationBinding op = getBindings().getOperationBinding("resetAction_1");
        op.execute();
        op = getBindings().getOperationBinding("executeItmMovVo");
        op.getParamsMap().put("voName", "ItmMov1");
        op.getParamsMap().put("voCriteria", "ItmMovVOCriteria");
        op.execute();
    }

    public void setBindingOrgId_1(RichSelectOneChoice bindingOrgId_1) {
        this.bindingOrgId_1 = bindingOrgId_1;
    }

    public RichSelectOneChoice getBindingOrgId_1() {
        return bindingOrgId_1;
    }

    public void setBindingWhId_1(RichSelectOneChoice bindingWhId_1) {
        this.bindingWhId_1 = bindingWhId_1;
    }

    public RichSelectOneChoice getBindingWhId_1() {
        return bindingWhId_1;
    }

    public void setBindingItmId_1(RichInputListOfValues bindingItmId_1) {
        this.bindingItmId_1 = bindingItmId_1;
    }

    public RichInputListOfValues getBindingItmId_1() {
        return bindingItmId_1;
    }

    public void setBindMnthJan(RichCommandImageLink bindMnthJan) {
        this.bindMnthJan = bindMnthJan;
    }

    public RichCommandImageLink getBindMnthJan() {
        return bindMnthJan;
    }

    public void setBindQuatQ1(RichCommandImageLink bindQuatQ1) {
        this.bindQuatQ1 = bindQuatQ1;
    }

    public RichCommandImageLink getBindQuatQ1() {
        return bindQuatQ1;
    }

    public String callQuat1() {
        // Add event code here...
        setQ1 = "ACTV";
        setQ2 = "NONE";
        setQ3 = "NONE";
        setQ4 = "NONE";
        selectedMnth = "NONE";
        selectedParam = "Q1";
        return null;
    }

    public void setSetQ1(String setQ1) {
        this.setQ1 = setQ1;
    }

    public String getSetQ1() {
        return setQ1;
    }

    public void setBindQuatQ2(RichCommandImageLink bindQuatQ2) {
        this.bindQuatQ2 = bindQuatQ2;
    }

    public RichCommandImageLink getBindQuatQ2() {
        return bindQuatQ2;
    }

    public String callQuat2() {
        // Add event code here...
        setQ2 = "ACTV";
        setQ1 = "NONE";
        setQ3 = "NONE";
        setQ4 = "NONE";
        selectedMnth = "NONE";
        selectedParam = "Q2";
        return null;
    }

    public void setSetQ2(String setQ2) {
        this.setQ2 = setQ2;
    }

    public String getSetQ2() {
        return setQ2;
    }

    public void setBindQuatQ3(RichCommandImageLink bindQuatQ3) {
        this.bindQuatQ3 = bindQuatQ3;
    }

    public RichCommandImageLink getBindQuatQ3() {
        return bindQuatQ3;
    }

    public String callQuat3() {
        // Add event code here...
        setQ2 = "NONE";
        setQ1 = "NONE";
        setQ3 = "ACTV";
        setQ4 = "NONE";
        selectedMnth = "NONE";
        selectedParam = "Q3";
        return null;
    }

    public void setSetQ3(String setQ3) {
        this.setQ3 = setQ3;
    }

    public String getSetQ3() {
        return setQ3;
    }

    public void setSetQ4(String setQ4) {
        this.setQ4 = setQ4;
    }

    public String getSetQ4() {
        return setQ4;
    }

    public void setBindQuatQ4(RichCommandImageLink bindQuatQ4) {
        this.bindQuatQ4 = bindQuatQ4;
    }

    public RichCommandImageLink getBindQuatQ4() {
        return bindQuatQ4;
    }

    public String callQuat4() {
        // Add event code here...
        setQ2 = "NONE";
        setQ1 = "NONE";
        setQ3 = "NONE";
        setQ4 = "ACTV";
        selectedMnth = "NONE";
        selectedParam = "Q4";
        return null;
    }

    public void setBindMnthFeb(RichCommandImageLink bindMnthFeb) {
        this.bindMnthFeb = bindMnthFeb;
    }

    public RichCommandImageLink getBindMnthFeb() {
        return bindMnthFeb;
    }

    public void setBindMnthMar(RichCommandImageLink bindMnthMar) {
        this.bindMnthMar = bindMnthMar;
    }

    public RichCommandImageLink getBindMnthMar() {
        return bindMnthMar;
    }

    public void setBindMnthApr(RichCommandImageLink bindMnthApr) {
        this.bindMnthApr = bindMnthApr;
    }

    public RichCommandImageLink getBindMnthApr() {
        return bindMnthApr;
    }

    public void setBindMnthMay(RichCommandImageLink bindMnthMay) {
        this.bindMnthMay = bindMnthMay;
    }

    public RichCommandImageLink getBindMnthMay() {
        return bindMnthMay;
    }

    public void setBindMnthJun(RichCommandImageLink bindMnthJun) {
        this.bindMnthJun = bindMnthJun;
    }

    public RichCommandImageLink getBindMnthJun() {
        return bindMnthJun;
    }

    public void setBindMnthJul(RichCommandImageLink bindMnthJul) {
        this.bindMnthJul = bindMnthJul;
    }

    public RichCommandImageLink getBindMnthJul() {
        return bindMnthJul;
    }

    public void setBindMnthAug(RichCommandImageLink bindMnthAug) {
        this.bindMnthAug = bindMnthAug;
    }

    public RichCommandImageLink getBindMnthAug() {
        return bindMnthAug;
    }

    public void setBindMnthSep(RichCommandImageLink bindMnthSep) {
        this.bindMnthSep = bindMnthSep;
    }

    public RichCommandImageLink getBindMnthSep() {
        return bindMnthSep;
    }

    public void setBindMnthOct(RichCommandImageLink bindMnthOct) {
        this.bindMnthOct = bindMnthOct;
    }

    public RichCommandImageLink getBindMnthOct() {
        return bindMnthOct;
    }

    public void setBindMnthNov(RichCommandImageLink bindMnthNov) {
        this.bindMnthNov = bindMnthNov;
    }

    public RichCommandImageLink getBindMnthNov() {
        return bindMnthNov;
    }

    public void setBindMnthDec(RichCommandImageLink bindMnthDec) {
        this.bindMnthDec = bindMnthDec;
    }

    public RichCommandImageLink getBindMnthDec() {
        return bindMnthDec;
    }

    public void setSelectedMnth(String selectedMnth) {
        this.selectedMnth = selectedMnth;
    }

    public String getSelectedMnth() {
        return selectedMnth;
    }

    /**
     * @param actionEvent
     */
    public void callMonthJan(ActionEvent actionEvent) {

        // Add event code here...
        selectedMnth = "JAN";
        selectedParam = selectedMnth;
        setQ2 = "NONE";
        setQ1 = "NONE";
        setQ3 = "NONE";
        setQ4 = "NONE";
    }

    public String callMonthFeb() {
        // Add event code here...
        selectedMnth = "FEB";
        selectedParam = selectedMnth;
        setQ2 = "NONE";
        setQ1 = "NONE";
        setQ3 = "NONE";
        setQ4 = "NONE";
        return null;
    }

    public void callMonthMar(ActionEvent actionEvent) {
        // Add event code here...
        selectedMnth = "MAR";
        selectedParam = selectedMnth;
        setQ2 = "NONE";
        setQ1 = "NONE";
        setQ3 = "NONE";
        setQ4 = "NONE";
    }

    public void callMonthApr(ActionEvent actionEvent) {
        // Add event code here...
        selectedMnth = "APR";
        selectedParam = selectedMnth;
        setQ2 = "NONE";
        setQ1 = "NONE";
        setQ3 = "NONE";
        setQ4 = "NONE";
    }

    public String callMonthMay() {
        // Add event code here...
        selectedMnth = "MAY";
        selectedParam = selectedMnth;
        setQ2 = "NONE";
        setQ1 = "NONE";
        setQ3 = "NONE";
        setQ4 = "NONE";
        return null;
    }

    public String callMonthJun() {
        // Add event code here...
        selectedMnth = "JUN";
        selectedParam = selectedMnth;
        setQ2 = "NONE";
        setQ1 = "NONE";
        setQ3 = "NONE";
        setQ4 = "NONE";
        return null;
    }

    public String callMonthJul() {
        // Add event code here...
        selectedMnth = "JUL";
        selectedParam = selectedMnth;
        setQ2 = "NONE";
        setQ1 = "NONE";
        setQ3 = "NONE";
        setQ4 = "NONE";
        return null;
    }

    public String callMonthAug() {
        // Add event code here...
        selectedMnth = "AUG";
        selectedParam = selectedMnth;
        setQ2 = "NONE";
        setQ1 = "NONE";
        setQ3 = "NONE";
        setQ4 = "NONE";
        return null;
    }

    public String callMonthSep() {
        // Add event code here...
        selectedMnth = "SEP";
        selectedParam = selectedMnth;
        setQ2 = "NONE";
        setQ1 = "NONE";
        setQ3 = "NONE";
        setQ4 = "NONE";
        return null;
    }

    public String callMonthOct() {
        // Add event code here...
        selectedMnth = "OCT";
        selectedParam = selectedMnth;
        setQ2 = "NONE";
        setQ1 = "NONE";
        setQ3 = "NONE";
        setQ4 = "NONE";
        return null;
    }

    public String callMonthNov() {
        // Add event code here...
        selectedMnth = "NOV";
        selectedParam = selectedMnth;
        setQ2 = "NONE";
        setQ1 = "NONE";
        setQ3 = "NONE";
        setQ4 = "NONE";
        return null;
    }

    public String callMonthDec() {
        // Add event code here...
        selectedMnth = "DEC";
        selectedParam = selectedMnth;
        setQ2 = "NONE";
        setQ1 = "NONE";
        setQ3 = "NONE";
        setQ4 = "NONE";
        return null;
    }

    public void setSelectedParam(String selectedParam) {
        this.selectedParam = selectedParam;
    }

    public String getSelectedParam() {
        return selectedParam;
    }

    public void ReceiptDetailActionListener(ActionEvent actionEvent) {
        // Add event code here...

        OperationBinding op = getBindings().getOperationBinding("executeReceiptDetail");
        op.execute();
        bindSwitcher.setFacetName("ReceiptDetailFacet");

    }

    public void backToReceiptfrmDtl(ActionEvent actionEvent) {
        // Add event code here...
        bindSwitcher.setFacetName("ReceiptFacet");
    }

    public void materialIssueActionListen(ActionEvent actionEvent) {
        // Add event code here...

        //pending because vo query is mismatch
        OperationBinding op = getBindings().getOperationBinding("Isssuedetail");
        op.execute();
        bindSwitcher.setFacetName("issueDetailFacet");
    }


    public void issuedetailback(ActionEvent actionEvent) {
        // Add event code here...
        bindSwitcher.setFacetName("MainFacet");
    }

    public void issueRecordAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("MmissueReord");
        op.execute();
        bindSwitcher.setFacetName("MmIssueRecord");
    }

    public void issueRecordBack(ActionEvent actionEvent) {
        // Add event code here...
        bindSwitcher.setFacetName("issueDetailFacet");
    }

    public void ReservedDetailBack(ActionEvent actionEvent) {
        // Add event code here...
        bindSwitcher.setFacetName("MainFacet");
    }

    public void ReservedAction(ActionEvent actionEvent) {
        // Add event code here...

        OperationBinding op = getBindings().getOperationBinding("MmreservedDetail");
        op.execute();
        bindSwitcher.setFacetName("ReservedDetailFacet");
    }

    /**
     *      Method to show validation message(I,E,W)
     *      mesg:Message to display
     *      sev:Severity(I,E,W)
     *      chk:true=if resource bundle is used
     *      typFlg: 'F' for FM , 'V' for VE
     *      clientId : client id for UI component
     * */
    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg, String clientId) {
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
        if (typFlg.equals("F")) {
            FacesContext.getCurrentInstance().addMessage(clientId, message);
        } else if (typFlg.equals("V")) {
            throw new ValidatorException(message);
        }
    }

    public void PrdctAgeningSearchAction(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("Value of template :-------- :" + templtBind.getValue());
        if (bindOrg.getValue() != null && bindOrg.getValue() != "") {
            ADFBeanUtils.getOperationBinding("searchProuductAge").execute();
            productAgeswitcher.setFacetName("productAgeItems");
        } else {
            //System.out.println("in the else block ");
            showFacesMessage("Organisation is required", "E", false, "F", bindOrg.getClientId());
            return;
        }
    }

    public void updateRangeAction(ActionEvent actionEvent) {
        // Add event code here...

        OperationBinding op = getBindings().getOperationBinding("updateRange");
        op.execute();
    }


    private void resetValueInputItems(AdfFacesContext adfFacesContext, UIComponent component) {
        List<UIComponent> items = component.getChildren();
        for (UIComponent item : items) {

            resetValueInputItems(adfFacesContext, item);

            if (item instanceof RichInputText) {
                RichInputText input = (RichInputText) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputDate) {
                RichInputDate input = (RichInputDate) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichSelectOneChoice) {
                RichSelectOneChoice input = (RichSelectOneChoice) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputListOfValues) {
                RichInputListOfValues input = (RichInputListOfValues) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            }

        }
    }

    public void resetProductage(ActionEvent actionEvent) {
        // Add event code here...
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(fctx, panelGrpBind);
        bindOrg.setValue("");
        bindWh.setValue("");
        bindItm.setValue("");


    }

    public void setProductAgeswitcher(UIXSwitcher productAgeswitcher) {
        this.productAgeswitcher = productAgeswitcher;
    }

    public UIXSwitcher getProductAgeswitcher() {
        return productAgeswitcher;
    }

    public void productAgeTotStkAL(ActionEvent actionEvent) {
        // Add event code here...executeProductAgeLot
        System.out.println(" in the productAge");
        Object itmid = actionEvent.getComponent().getAttributes().get("paramItmId");
        System.out.println("Value of Itm Id::" + itmid);
        OperationBinding op = getBindings().getOperationBinding("executeProductAgeLot");
        op.getParamsMap().put("itmid", itmid);
        op.execute();
        productAgeswitcher.setFacetName("productAgeLot");

    }

    public void backProductAgeLotAL(ActionEvent actionEvent) {
        // Add event code here...
        productAgeswitcher.setFacetName("productAgeItems");
    }

    public void setPanelGrpBind(RichPanelGroupLayout panelGrpBind) {
        this.panelGrpBind = panelGrpBind;
    }

    public RichPanelGroupLayout getPanelGrpBind() {
        return panelGrpBind;
    }

    public void setBindOrg(RichSelectOneChoice bindOrg) {
        this.bindOrg = bindOrg;
    }

    public RichSelectOneChoice getBindOrg() {
        return bindOrg;
    }

    public void setBindWh(RichSelectOneChoice bindWh) {
        this.bindWh = bindWh;
    }

    public RichSelectOneChoice getBindWh() {
        return bindWh;
    }

    public void setBindItm(RichInputListOfValues bindItm) {
        this.bindItm = bindItm;
    }

    public RichInputListOfValues getBindItm() {
        return bindItm;
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    public void createTemplateItem(ActionEvent actionEvent) {
        // Add event code here...
        if ((bindtemplItm.getValue() != null) && (bindtemplItm.getValue() != "")) {
            OperationBinding op = this.getBindings().getOperationBinding("checkDuplicateTemp");
            Object result = op.execute();
            adfLog.info("-----------result----- " + result);
            if (result != null && result.toString().equalsIgnoreCase("Y")) {
                showFacesMessage("Duplicate Record Found.", "E", false, "F", null);
                return;
            }
            ADFBeanUtils.getOperationBinding("templateItem").execute();
        } else
            adfLog.info("in the else loop.");
        showFacesMessage("Item Name is required.", "E", false, "F", bindtemplItm.getClientId());
    }

    public void addTemplate(ActionEvent actionEvent) {
        // Add event code here...
        // ADFBeanUtils.findOperation("Rollback").execute();
        ADFBeanUtils.findOperation("CreateInsert").execute();
        showPopup(addtempPopup, true);
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

    public void setAddtempPopup(RichPopup addtempPopup) {
        this.addtempPopup = addtempPopup;
    }

    public RichPopup getAddtempPopup() {
        return addtempPopup;
    }

    public void addTemplateDialogue(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().equals("ok")) {
            // System.out.println("in the ok loop");
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            setMode("V");
            showFacesMessage("Template Saved Successfully", "I", false, "F", null);
        }
        if (dialogEvent.getOutcome().name().equals("cancel")) {
            // System.out.println("in the cancel loop");
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();
            setMode("V");
        }
    }

    public void addTempItem(ActionEvent actionEvent) {
        // Add event code here...
        if (tempNmBind.getValue() != null && tempNmBind.getValue().toString().length() > 0) {
            if (bktRangeTypeBind.getValue() != null && bktRangeTypeBind.getValue().toString().length() > 0) {
                if (whNmTempBind.getValue() != null && whNmTempBind.getValue().toString().length() > 0) {
                    setMode("A");
                } else
                    showFacesMessage("Warehouse is required.", "E", false, "F", null);
            } else
                showFacesMessage("Range Type is required.", "E", false, "F", null);
        } else
            showFacesMessage("Template Name is required.", "E", false, "F", null);

    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void saveTemplateDialog(DialogEvent dialogEvent) {
        // Add event code here...
        adfLog.info("iondnfdfd");
        if (dialogEvent.getOutcome().name().equals("ok")) {
            adfLog.info(" in the ok  if block");

            BindingContainer bindings = getBindings();
            OperationBinding binding = bindings.getOperationBinding("filterProductAgeMain");
            binding.execute();
            adfLog.info("Value of result:::" + binding.getResult());
            if (binding.getResult() != null && binding.getResult().equals("Y")) {
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();
                showFacesMessage("Template Saved Successfully", "I", false, "F", null);
            }
            if (binding.getResult() != null && binding.getResult().equals("N")) {
                showFacesMessage("Select at leastOneItem", "E", false, "F", null);
            }

            if (binding.getResult() != null && binding.getResult().equals("B")) {
                showFacesMessage("Data Range is required", "I", false, "F", bindRangetype.getClientId());
            }
            if (binding.getResult() != null && binding.getResult().equals("T")) {
                showFacesMessage("Template Name is required", "I", false, "F", bindTempNm.getClientId());
            }

            //setMode("V");
        }
        if (dialogEvent.getOutcome().name().equals("cancel")) {
            //System.out.println("in the cancel loop");
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();
            setMode("V");
        }

    }

    public void saveaAsTemplateACL(ActionEvent actionEvent) {
        // Add event code here...filterProductAgeMain
        ADFBeanUtils.findOperation("CreateInsert").execute();
        showPopup(saveTemplatePopUP, true);


    }

    public void setSaveTemplatePopUP(RichPopup saveTemplatePopUP) {
        this.saveTemplatePopUP = saveTemplatePopUP;
    }

    public RichPopup getSaveTemplatePopUP() {
        return saveTemplatePopUP;
    }

    public List getTemplateName(String string) {
        // Add event code here...
        List<SelectItem> items = new ArrayList<SelectItem>();
        OperationBinding binding = getBindings().getOperationBinding("getSuggestedItemDesc");
        binding.getParamsMap().put("itmStr", string);
        binding.execute();
        Object object = binding.getResult();
        if (object != null) {
            for (String item : (ArrayList<String>) object) {
                items.add(new SelectItem(item));
            }
        }
        return items;

    }

    public void deleteTemplateItem(ActionEvent actionEvent) {
        // Add event code here...
        getBindings().getOperationBinding("Delete").execute();
        // getBindings().getOperationBinding("Commit").execute();

    }

    public void setBindtemplItm(RichInputListOfValues bindtemplItm) {
        this.bindtemplItm = bindtemplItm;
    }

    public RichInputListOfValues getBindtemplItm() {
        return bindtemplItm;
    }

    public void setTempltBind(RichInputText templtBind) {
        this.templtBind = templtBind;
    }

    public RichInputText getTempltBind() {
        return templtBind;
    }

    public void setBindTempNm(RichInputText bindTempNm) {
        this.bindTempNm = bindTempNm;
    }

    public RichInputText getBindTempNm() {
        return bindTempNm;
    }

    public void setBindRangetype(RichSelectOneChoice bindRangetype) {
        this.bindRangetype = bindRangetype;
    }

    public RichSelectOneChoice getBindRangetype() {
        return bindRangetype;
    }

    public void selectAllItemActionListener(ActionEvent actionEvent) {
        // Add event code here...
        getBindings().getOperationBinding("checkAllItem").execute();
    }

    public void unSelectAllItemActionListener(ActionEvent actionEvent) {
        // Add event code here...
        getBindings().getOperationBinding("uncheckAllItem").execute();
    }

    public void selectItemChangeListener(ValueChangeEvent vce) {
        // Add event code here...
        System.out.println("value of check::" + vce.getNewValue());
        adfLog.info("value of check::" + vce.getNewValue());
        //        if (valueChangeEvent.getNewValue().toString().equals("true")) {
        //            getBindings().getOperationBinding("checkAllItem").execute();
        //        } else {
        //            getBindings().getOperationBinding("uncheckAllItem").execute();
        //        }


        boolean isSelected = ((Boolean) vce.getNewValue()).booleanValue();
        DCBindingContainer dcb = (DCBindingContainer) evaluateEL1("#{bindings}");
        DCIteratorBinding dciter1 = dcb.findIteratorBinding("ProductAgeMain1Iterator");
        ViewObject vo = dciter1.getViewObject();
        RowSetIterator dciter = vo.createRowSetIterator(null);
        int i = 0;
        Row row = null;
        vo.reset();
        while (dciter.hasNext()) {
            if (i == 0)
                row = dciter.first();
            else
                row = dciter.next();
            if (isSelected) {
                row.setAttribute("TransCheck", "Y");

            } else {

                row.setAttribute("TransCheck", "N");
            }
            i++;
        }


        AdfFacesContext.getCurrentInstance().addPartialTarget(prdctTabBind);

    }

    public void setBindcheck(RichSelectBooleanCheckbox bindcheck) {
        this.bindcheck = bindcheck;
    }

    public RichSelectBooleanCheckbox getBindcheck() {
        return bindcheck;
    }

    //    public String evaluateEL(String el) {
    //        FacesContext facesContext = FacesContext.getCurrentInstance();
    //        ELContext elContext = facesContext.getELContext();
    //        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
    //        ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);
    //        String retVal = exp.getValue(elContext).toString();
    //        return retVal;
    //    }

    public void setPrdctTabBind(RichTable prdctTabBind) {
        this.prdctTabBind = prdctTabBind;
    }

    public RichTable getPrdctTabBind() {
        return prdctTabBind;
    }

    public void springboardCL(SpringboardChangeEvent springboardChangeEvent) {
        // Add event code here...
    }

    public void stockMoveDCL(DisclosureEvent disclosureEvent) {
        ADFBeanUtils.getOperationBinding("Execute1").execute();
        OperationBinding op = getBindings().getOperationBinding("executeDefaultItmMovVo");
        op.getParamsMap().put("voName", "ItmMov1");
        op.getParamsMap().put("voCriteria", "ItmMovVOCriteria");
        op.execute();
    }

    public void inventoryDCL(DisclosureEvent disclosureEvent) {
        ADFBeanUtils.getOperationBinding("Execute").execute();
    }

    public void stockAgeingDCL(DisclosureEvent disclosureEvent) {
        ADFBeanUtils.getOperationBinding("ExecutePrdAg").execute();
    }

    public void setTempNmBind(RichInputText tempNmBind) {
        this.tempNmBind = tempNmBind;
    }

    public RichInputText getTempNmBind() {
        return tempNmBind;
    }

    public void setBktRangeTypeBind(RichSelectOneChoice bktRangeTypeBind) {
        this.bktRangeTypeBind = bktRangeTypeBind;
    }

    public RichSelectOneChoice getBktRangeTypeBind() {
        return bktRangeTypeBind;
    }

    public void setWhNmTempBind(RichSelectOneChoice whNmTempBind) {
        this.whNmTempBind = whNmTempBind;
    }

    public RichSelectOneChoice getWhNmTempBind() {
        return whNmTempBind;
    }

    public void setDefaultTab() {
        springBoardDispMode = "strip";
        discloseTab = "SA";
        //    ADFBeanUtils.getOperationBinding("ExecutePrdAg").execute();
    }
}

