package slspicpackshipapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.nav.RichCommandNavigationItem;
import oracle.adf.view.rich.component.rich.output.RichMessage;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


/**
 * PickPack&Ship Managed Bean.
 * Contains all the business logic
 * @author Ashish Kumar and edited by Vikas Kukreti,Aman junaid,Rohit Chaturvedi
 * Dated -01/10/2013
 * */
public class PickPackShipBean {
    private RichTreeTable soTreeTableBind;
    private RowKeySet disclosedTreeRowKeySet = new RowKeySetImpl();
    private static ADFLogger _log = ADFLogger.createADFLogger(PickPackShipBean.class);
    private ArrayList rowList = new ArrayList();
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
    private RichInputText eoIdSrchMainBind;
    private RichInputText shpmntIdSrchBind;
    private RichInputText pickIdSrchMainBind;
    private RichInputText soIdSrchMainBind;
    private RichSelectOneChoice dlvModeSrchMainBind;
    private RichInputDate pickDtSrchMainBind;
    private RichInputDate pickDtFrmShipBind;
    private RichInputDate pickDtToShipBind;

    private String pick_mode = "V";
    private String pack_mode = "V";
    private String faceNmPick = "Lot";

    private RichOutputText pickQtyIssueBind;
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
    private RichSelectOneChoice eoIdShipBinding;
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

    /***Method to expand all tree table nodes*/
    private void expandTreeTable() {
        if (this.soTreeTableBind != null) {
            disclosedTreeRowKeySet = new RowKeySetImpl();
            CollectionModel model = (CollectionModel)soTreeTableBind.getValue();
            JUCtrlHierBinding treeBinding = (JUCtrlHierBinding)model.getWrappedData();
            JUCtrlHierNodeBinding rootNode = treeBinding.getRootNodeBinding();
            disclosedTreeRowKeySet = soTreeTableBind.getDisclosedRowKeys();
            if (disclosedTreeRowKeySet == null) {
                disclosedTreeRowKeySet = new RowKeySetImpl();
            }
            List<JUCtrlHierNodeBinding> firstLevelChildren = rootNode.getChildren();
            for (JUCtrlHierNodeBinding node : firstLevelChildren) {
                ArrayList list = new ArrayList();
                list.add(node.getRowKey());
                disclosedTreeRowKeySet.add(list);
                expandTreeChildrenNode(soTreeTableBind, node, list);
            }
            soTreeTableBind.setDisclosedRowKeys(disclosedTreeRowKeySet);
        }
    }

    private void expandTreeChildrenNode(RichTreeTable rt, JUCtrlHierNodeBinding node, List<Key> parentRowKey) {
        ArrayList children = node.getChildren();
        List<Key> rowKey;
        if (children != null) {
            for (int i = 0; i < children.size(); i++) {
                rowKey = new ArrayList<Key>();
                rowKey.addAll(parentRowKey);
                rowKey.add(((JUCtrlHierNodeBinding)children.get(i)).getRowKey());
                disclosedTreeRowKeySet.add(rowKey);
                if (((JUCtrlHierNodeBinding)(children.get(i))).getChildren() == null)
                    continue;
                expandTreeChildrenNode(rt, (JUCtrlHierNodeBinding)(node.getChildren().get(i)), rowKey);
            }
        }
    }

    /**
     * @return
     */
    public RowIterator getSelectedNodeRowIterator() {
        if (getSoTreeTableBind() != null && getSoTreeTableBind().getSelectedRowKeys() != null) {
            for (Object opaqueFacesKey : getSoTreeTableBind().getSelectedRowKeys()) {
                getSoTreeTableBind().setRowKey(opaqueFacesKey);
                // System.out.println("set getSelectedNodeRowIterator" +
                // ((JUCtrlHierNodeBinding)getSoTreeTableBind().getRowData()).getRowIterator());
                return ((JUCtrlHierNodeBinding)getSoTreeTableBind().getRowData()).getRowIterator();
            }
        }
        // System.out.println("return null");
        return null;
    }

    /**
     * @return
     */
    public Key getSelectedNodeRowKey() {
        if (getSoTreeTableBind() != null && getSoTreeTableBind().getSelectedRowKeys() != null) {
            for (Object opaqueFacesKey : getSoTreeTableBind().getSelectedRowKeys()) {
                getSoTreeTableBind().setRowKey(opaqueFacesKey);
                //  System.out.println("  getSelectedNodeRowKey " +
                //      ((JUCtrlHierNodeBinding)getSoTreeTableBind().getRowData()).getRowKey());
                return ((JUCtrlHierNodeBinding)getSoTreeTableBind().getRowData()).getRowKey();
            }
        }
        // System.out.println(" getSelectedNodeRowKey rerutn null");
        return null;
    }


    /**
     * @param ri
     * @param selectedNodeKey
     */


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
                AdfFacesContext.getCurrentInstance().addPartialTarget(soTreeTableBind);
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
                rtnval = (String)obb.getResult();
            }
            //}
        }
        //  System.out.println("NOTOK " + rtnval);
        return rtnval;
    }

    /*************** * *  *  * *  *Action Listeners defined for for page * * * ************/

    /**Method to Create new PickList
     * @param actionEvent
     */

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void newPickListAction(ActionEvent actionEvent) {
        executeOperation("Createwithparameters").execute();
        OperationBinding filterTree = executeOperation("filterOrderData");
        filterTree.getParamsMap().put("pickDate", new Timestamp(System.currentTimeMillis()));
        filterTree.execute();

        /*   try {
            expandTreeTable();
        } catch (NullPointerException npe) {
            //FacesMessage errMsg = new FacesMessage("No Sales Order to display ,Can not expand ");
            FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.905']}").toString());

            errMsg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, errMsg);
        } */
        AdfFacesContext.getCurrentInstance().addPartialTarget(soTreeTableBind);
        this.pick_mode = "A";
        this.header = "Z";

    }

    /**Method To Edit PickList
     * @param actionEvent
     */
    public void editPickListAction(ActionEvent actionEvent) {
        System.out.println("inside edit");
        executeOperation("executePickItmVo").execute();
        OperationBinding oFlg = executeOperation("shipmntCrtdOrNt");
        oFlg.execute();
        if (oFlg.getResult() != null && (oFlg.getResult().toString()).equalsIgnoreCase("N")) {
            System.out.println("indide edit");
            this.pick_mode = "Z";
            this.header = "Y";
            String eoNm = null;
            String wareh = null;
            Timestamp pickDt = null;
            OperationBinding bindingEo = this.getBindings().getOperationBinding("getPickEoNm");
            bindingEo.execute();
            if (bindingEo.getResult() != null) {
                eoNm = bindingEo.getResult().toString();
            }
            if (pickDateBind.getValue() != null) {
                pickDt = (Timestamp)pickDateBind.getValue();
            }
            if (wareHousePickBind.getValue() != null) {
                wareh = wareHousePickBind.getValue().toString();
            }
            _log.info("EoNm is-" + eoNm + " wareHou-" + wareh + "pick Date--" + pickDt);
            OperationBinding ob = executeOperation("filterTreeCustomerWise");
            ob.getParamsMap().put("eo_nm", eoNm);
            ob.execute();


            OperationBinding filterTree1 = executeOperation("filterTreeWarehouse");
            filterTree1.getParamsMap().put("whId", wareh);
            filterTree1.execute();

            OperationBinding filterTree = executeOperation("filterOrderData");
            filterTree.getParamsMap().put("pickDate", pickDt);
            filterTree.execute();

            try {
                expandTreeTable();
            } catch (NullPointerException npe) {
                //FacesMessage errMsg = new FacesMessage("No Sales Order to display ,Can not expand ");
                FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.905']}").toString());

                errMsg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, errMsg);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(soTreeTableBind);
        } else {
            //FacesMessage errMsg = new FacesMessage("Shipment is done for this picklist, can not edit");
            FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.910']}").toString());

            errMsg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, errMsg);
        }

    }
    /*  Number schdlQuant = new Number(0);
    Number pendQty = new Number(0);
    Number itmAvlQty = new Number(0); */


    /**Method to populate Item in PickList
     * @param actionEvent
     */
    public void addItemToPickListAction(ActionEvent actionEvent) {
        if (dlvAddsIdBind.getValue() != null) {
            System.out.println("selectQuantTransTreeTabBind value  " + selectQuantTransTreeTabBind.getValue());
            if (selectQuantTransTreeTabBind.getValue() != null) {
                //
                //  System.out.println("selectQuantTransTreeTabBind value  " + selectQuantTransTreeTabBind.getValue());
                /* schdlQuant = (oracle.jbo.domain.Number)selectQuantTransTreeTabBind.getValue();
                pendQty = (oracle.jbo.domain.Number)pendQtyTreetabBind.getValue();
                if (itmAvlblQtyBind.getValue() != null) {
                    itmAvlQty = (Number)itmAvlblQtyBind.getValue();
                } */
                //   System.out.println(" schdlQuant= "+schdlQuant+" pendQty= "+pendQty+" itmAvlQty "+itmAvlQty);
                Number chkPendQtyMin = new Number(0);
                Number chkPendQtyMax = new Number(0);
                Number itmAvlQty = new Number(0);
                Number tlrncQty = new Number(0);
                Number hund = new Number(100);

                Number schdlQuant = (oracle.jbo.domain.Number)selectQuantTransTreeTabBind.getValue();
                Number pendQty = (oracle.jbo.domain.Number)pendQtyTreetabBind.getValue();

                if (itmAvlblQtyBind.getValue() != null) {
                    itmAvlQty = (Number)itmAvlblQtyBind.getValue();
                }
                Number qtyToChk = new Number(0);

                Row[] rows = getSelectedNodeRowIterator().findByKey(getSelectedNodeRowKey(), 1);
                System.out.println("Total length of rows are:  " + rows.length + "------------");
                if (rows != null) {
                    for (Row row : rows) {
                        System.out.println("TlrncVal-" + row.getAttribute("TlrncQtyVal") + "and type-" +
                                           row.getAttribute("TlrncQtyType"));
                        if (row.getAttribute("TlrncQtyVal") != null) {
                            tlrncQty = (Number)row.getAttribute("TlrncQtyVal");
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
                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.336']}").toString(),
                                         resolvElDCMsg("#{bundle['MSG.915']}").toString() + qtyToChk +
                                         resolvElDCMsg("#{bundle['MSG.916']}").toString());
                    errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(selectQuantTransTreeTabBind.getClientId(), errMsg);

                } else {
                    Row[] roow = getSelectedNodeRowIterator().findByKey(getSelectedNodeRowKey(), 1);
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
                            i = (String)lotRowCount.getResult();
                            System.out.println("i " + i);

                        }
                    }
                    if ("Y".equalsIgnoreCase(i)) {
                        System.out.println("set if");
                        showPopup(delConfirmPopUp, true);

                    } else {
                        System.out.println("set else ");
                        addSoRowToLList(getSelectedNodeRowIterator(), getSelectedNodeRowKey());
                        selectQuantTransTreeTabBind.setValue(0);
                    }
                }
            } else {
                //FacesMessage errMsg = new FacesMessage("Invalid Quantity");
                FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.336']}").toString());
                errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                //errMsg.setDetail("Must Enter a Value");
                errMsg.setDetail(resolvElDCMsg("#{bundle['MSG.117']}").toString());

                FacesContext.getCurrentInstance().addMessage(selectQuantTransTreeTabBind.getClientId(), errMsg);
            }
        } else {
            FacesMessage addsMsg = new FacesMessage("You must enter a value", "Address is required");
            addsMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(dlvAddsIdBind.getClientId(), addsMsg);
        }
    }

    /**Method to Expand all tree Node
     * @param actionEvent
     */
    public void expandTreeTableAction(ActionEvent actionEvent) {
        try {
            expandTreeTable();
        } catch (NullPointerException npe) {
            //FacesMessage errMsg = new FacesMessage("No Sales Order to display ,Can not expand ");
            FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.905']}").toString());
            errMsg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, errMsg);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(soTreeTableBind);
    }

    /**Collapse TreeTable
     * @param actionEvent
     */
    public void collapseTreeTableAction(ActionEvent actionEvent) {
        soTreeTableBind.getDisclosedRowKeys().clear();
        AdfFacesContext.getCurrentInstance().addPartialTarget(soTreeTableBind);
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
        System.out.println("save set 7");
        System.out.println("save set 8");
        StringBuilder saveMsg;
        System.out.println("save set 9");
        String docidPick = "N";
        System.out.println("save set 10");
        DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("SlsPick1Iterator");
        System.out.println("save set 11");
        Key parentKey = parentIter.getCurrentRow().getKey();
        _log.info("Parent Key before commit-" + parentKey);
        /*  System.out.println("inside execute item method");
        OperationBinding opp1=executeOperation("Execute2");
        opp1.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindItmTbl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotBinTabBind); */
        OperationBinding valArr = executeOperation("checkItemIssueValidation");
        valArr.execute();
        System.out.println("valArr " + valArr.getResult());
        if (valArr.getResult() != null) {
            ArrayList<String> itmArr = (ArrayList<String>)valArr.getResult();
            System.out.println("itmArr  =" + itmArr.size());
            if (itmArr.size() != 0) {
                /* saveMsg =
                        new StringBuilder("<html><body><b><p style='color:red'>Following Items are not issued with full quantity,Can't Save</p></b>"); */
                saveMsg =
                        new StringBuilder("<html><body><b><p style='color:red'>" + resolvElDCMsg("#{bundle['MSG.920']}").toString() +
                                          "</p></b>");

                saveMsg.append("<ul>");
                for (String a : itmArr) {
                    saveMsg.append("<li> <b>" + a + "</b></li>");
                }
                saveMsg.append("</ul><br>");
                //saveMsg.append("<b>What to Do:");
                saveMsg.append("<b>" + resolvElDCMsg("#{bundle['MSG.871']}").toString() + "");
                //saveMsg.append("<ul style='color:darkgreen'><li>Isuue all Items with full quantity from LOT</li><li>Delete Un-Issued Items</li></ul></b>");
                saveMsg.append("<ul style='color:darkgreen'><li>" + resolvElDCMsg("#{bundle['MSG.872']}").toString() +
                               "</li><li>" + resolvElDCMsg("#{bundle['MSG.873']}").toString() + "</li></ul></b>");
                saveMsg.append("</body></html>");
                FacesMessage msg = new FacesMessage(saveMsg.toString());
                msg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                System.out.println("inside else when " + itmArr.size());
                // executeOperation("Commit").execute();
                OperationBinding dispId = executeOperation("generateDispDocNo");
                dispId.getParamsMap().put("flag", "pick");
                dispId.execute();
                executeOperation("Commit").execute();
                if (dispId.getResult() != null) {
                    String diplsyId = dispId.getResult().toString();
                    if (diplsyId.startsWith("P")) {
                    } else {
                        diplsyId = diplsyId.substring(2);
                    }

                    /* saveMsg =
                            new StringBuilder("<html><body><p>PickList <b>" + diplsyId + "</b> Saved Successfully</p>"); */
                    saveMsg =
                            new StringBuilder("<html><body><p>" + resolvElDCMsg("#{bundle['LBL.2575']}").toString() + " <b>" +
                                              diplsyId + "</b> " + resolvElDCMsg("#{bundle['MSG.347']}").toString() +
                                              "</p>");
                    saveMsg.append("</body></html>");
                    FacesMessage msg = new FacesMessage(saveMsg.toString());
                    msg.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

                if (cancelPickFlgBind.getValue() != null) {
                    String cancelOrd = cancelPickFlgBind.getValue().toString();
                    _log.info("Cancel Pick Flag-" + cancelOrd);
                    if (cancelOrd.equalsIgnoreCase("true")) {
                        OperationBinding cancOrd = executeOperation("updatePickStausCancel");
                        cancOrd.getParamsMap().put("canc_Flag", cancelOrd);
                        cancOrd.execute();
                    }
                }
                _log.info("Call Reserve Stock");
                executeOperation("reserveStock").execute();
                //executeOperation("UpdateOrdStat").execute();
                OperationBinding binding = getBindings().getOperationBinding("UpdateOrdStat");
                binding.getParamsMap().put("flag", "S");
                binding.execute();
                OperationBinding docId = executeOperation("getCurrentdocId");
                docId.execute();
                if (docId.getResult() != null) {
                    docidPick = docId.getResult().toString();
                }
                _log.info("Pick DocId--" + docidPick);
                executeOperation("Commit").execute();
                executeOperation("Execute").execute();
                this.pick_mode = "V";
                // _log.info("Parent Key after commit-" + parentKey);
                executeOperation("resetTreeTableFilter").execute();
                OperationBinding setRow = executeOperation("setOncurRow");
                setRow.getParamsMap().put("pickId", docidPick);
                setRow.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(soTreeTableBind);
                // parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

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
        OperationBinding crt = executeOperation("CreateInsert");
        crt.execute();

        System.out.println("crt.getErrors()" + crt.getErrors());
        if (crt.getErrors().isEmpty()) {
            //this.getBindings().getOperationBinding("refreshPickLov").execute();
            this.pack_mode = "A";
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
        if (((Integer)binding.getResult()) > 0) {
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
                    new StringBuilder("<html><body><p>" + resolvElDCMsg("#{bundle['LBL.2610']}").toString() + " <b>" +
                                      diplsyId + "</b> " + resolvElDCMsg("#{bundle['MSG.347']}").toString() + "</p>");

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
        JUCtrlListBinding listBinding = (JUCtrlListBinding)getBindings().get("viewPickPackItm1");
        listBinding.clearSelectedIndices();
        showPopup(selectPackItemPopupBind, true);
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
            ViewObject slsPack = (ViewObject)Vobject.getResult();

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
            ViewObject slsPack = (ViewObject)Vobject.getResult();
            slsPack.setWhereClause(null);
            slsPack.executeQuery();

        }
        OperationBinding Sobject = executeOperation("getSearchObject");
        Sobject.execute();
        if (Sobject.getResult() != null) {
            itmIdSearchBind.setValue(null);
            packNoSearchBind.setValue(null);
            ViewObject searchVo = (ViewObject)Sobject.getResult();
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
                FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.929']}").toString());

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
                AdfFacesContext.getCurrentInstance().addPartialTarget(soTreeTableBind);
            }
        }
    }

    /**Method To reset treeTable Filter
     * @param actionEvent
     */
    public void resetTreeTableAction(ActionEvent actionEvent) {
        executeOperation("resetTreeTableFilter").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(soTreeTableBind);
    }

    /**Method to Search in Shipment View
     * @param actionEvent
     */
    public void searchShipmentAction(ActionEvent actionEvent) {
        OperationBinding sipmntVo = executeOperation("getShipmntSearchView");
        sipmntVo.execute();
        if (sipmntVo.getResult() != null) {
            ViewObject searchShip1 = (ViewObject)sipmntVo.getResult();
            _log.info("Pick Date From is--" + pickDtFrmShipBind.getValue() + "And To is-" +
                      pickDtToShipBind.getValue());
            searchShip1.setNamedWhereClauseParam("pickDtFrm", pickDtFrmShipBind.getValue());
            searchShip1.setNamedWhereClauseParam("eoIdBind", eoIdSearchShipBind.getValue());
            searchShip1.setNamedWhereClauseParam("pickDtTo", pickDtToShipBind.getValue());
            searchShip1.setNamedWhereClauseParam("whIdBind", whIdSearchShipBind.getValue());
            searchShip1.setNamedWhereClauseParam("ShipDocIdBind", null);
            searchShip1.setNamedWhereClauseParam("PickDocId", null);
            _log.info("Address : " + this.deliveryAddressShipmntSearchBind.getValue() + "Pick Id is :" +
                      this.pickIdTrans.getValue());
            searchShip1.setNamedWhereClauseParam("DlvAddsBind", this.deliveryAddressShipmntSearchBind.getValue());
            searchShip1.setNamedWhereClauseParam("PickDispIdBind", this.pickIdTrans.getValue());
            searchShip1.executeQuery();
        }

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
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem)actionEvent.getComponent();
        rcnv.setVisited(true);
    }

    /**Method set FacetName to Bin
     * @param actionEvent
     */
    public void viewBinAction(ActionEvent actionEvent) {
        this.setFacetNm("Bin");
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem)actionEvent.getComponent();
        rcnv.setVisited(true);
    }

    /**Method set FacetName to SrNo
     * @param actionEvent
     */
    public void viewSerialsAction(ActionEvent actionEvent) {
        this.setFacetNm("SrNo");
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem)actionEvent.getComponent();
        rcnv.setVisited(true);
    }

    /**Method to generate Shipment from PickList
     * @param actionEvent
     */
    public void generateShipmentAction(ActionEvent actionEvent) {
        OperationBinding validateGen = executeOperation("validateGenShipment");
        validateGen.execute();
        String vouId = "";
        if (validateGen.getResult() != null) {
            if (validateGen.getResult().toString().equalsIgnoreCase("Y")) {

                OperationBinding ob = executeOperation("generateShipment");
                ob.execute();
                if (ob.getResult() != null) {
                    String flag = ob.getResult().toString();
                    _log.info("Flag from Ship--" + flag);
                    if ("N".equalsIgnoreCase(flag)) {
                        /*  FacesMessage errMsg =
                            new FacesMessage("Please Select atleast one Picklist to generate Shipment"); */
                        FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.932']}").toString());
                        errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, errMsg);
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
                            new FacesMessage("<html><body>" + resolvElDCMsg("#{bundle['LBL.2701']}").toString() +
                                             "<b> " + flag.substring(2) + " </b> " +
                                             resolvElDCMsg("#{bundle['MSG.319']}").toString() + " " + vouId +
                                             "</body></html>");
                        saveMsg.setSeverity(FacesMessage.SEVERITY_INFO);

                        FacesContext.getCurrentInstance().addMessage(null, saveMsg);
                        //                        this.getTransporterPopupBind().hide();
                        selectChkShiCount = 0;
                        _log.info("Saved");
                        System.out.println("Message is:  " + saveMsg);
                    }
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
        executeOperation("Rollback").execute();
        executeOperation("Execute").execute();
        executeOperation("resetTreeTableFilter").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(soTreeTableBind);
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
        /* if (searchVo.getResult() != null) {
            _log.info("ViewObject is--" + searchVo + "Pick Date is-" + pickDtSrchMainBind.getValue());
            ViewObject srchVo = (ViewObject)searchVo.getResult();
            srchVo.setNamedWhereClauseParam("eoIdBind", eoIdSrchMainBind.getValue());
            srchVo.setNamedWhereClauseParam("soDocIdBind", soIdSrchMainBind.getValue());
            srchVo.setNamedWhereClauseParam("pickIdBind", pickIdSrchMainBind.getValue());
            srchVo.setNamedWhereClauseParam("shpmntIdBind", shpmntIdSrchBind.getValue());
            srchVo.setNamedWhereClauseParam("dlvModeBind", dlvModeSrchMainBind.getValue());
            srchVo.setNamedWhereClauseParam("pickDateBind", pickDtSrchMainBind.getValue());
            srchVo.executeQuery();
        } */
    }

    /**Method to reset data in Landing page
     * @param actionEvent
     */
    public void resetAction(ActionEvent actionEvent) {
        OperationBinding reset = executeOperation("resetMainSearch");
        reset.execute();
    }

    /**Method to navigate from Search page to PickList page
     * @return
     */
    public String goToPickListAction() {
        _log.info("Destination--" + goToDestBind.getValue());
        //OperationBinding binding = this.getBindings().getOperationBinding("doShipmentExist");
        // binding.execute();
        // _log.info("Shipment exists : "+binding.getResult());
        //|| binding.getResult().equals((Object)false))
        if ((goToDestBind.getValue() != null && goToDestBind.getValue().equals("P"))) {
            executeOperation("goToPickListAction").execute();
            destGo = "P";
            return "goToPickList";
        } else {
            destGo = "S";
            return "goToShipDirect";
        }
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
            return "goToPickList";

        } else {
            /* StringBuilder msg =
                new StringBuilder("<html><body><b><p style='color:red'>Something went wrong while creating Pick-List</p></b>"); */
            StringBuilder msg =
                new StringBuilder("<html><body><b><p style='color:red'>" + resolvElDCMsg("#{bundle['MSG.935']}").toString() +
                                  "</p></b>");

            //msg.append("<ul><li>Try Again</li><li>Or Contact ESS:  +91-120-4212931-39</li></ul></body></html>");
            msg.append("<ul><li>" + resolvElDCMsg("#{bundle['MSG.937']}").toString() + "</li><li>" +
                       resolvElDCMsg("#{bundle['MSG.939']}").toString() + "</li></ul></body></html>");

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
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem)actionEvent.getComponent();
        rcnv.setVisited(true);
    }

    /**Set FacetName to Bin in PickList page
     * @param actionEvent
     */
    public void viewBinPickAction(ActionEvent actionEvent) {
        // executeOperation("executePickItmVo").execute();
        this.setFaceNmPick("Bin");
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem)actionEvent.getComponent();
        rcnv.setVisited(true);
    }

    /**Set FacetName to Serail in PickList page
     * @param actionEvent
     */
    public void viewSerialsPickAction(ActionEvent actionEvent) {
        //  executeOperation("executePickItmVo").execute();
        this.setFaceNmPick("SrNo");
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem)actionEvent.getComponent();
        rcnv.setVisited(true);
    }

    /**Method to check whether PACK is used in Application or not
     * And redirects control to PickList page from Shipment page
     * @return
     */
    public String backFrmShpmntAction() {
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
        if (cancelShipFlgBind.getValue() != null) {
            String cancelOrd = cancelShipFlgBind.getValue().toString();
            _log.info("Cancel order Flag-" + cancelOrd);
            if (cancelOrd.equalsIgnoreCase("true")) {
                String whId = null;
                String shipId = null;
                String docId = null;
                Integer eoId = 0;
                Date ShipDt = null;
                Date usrIdCreateDt = null;
                Timestamp uIdCreateDtN = null;
                Timestamp shipDtN = null;
                if (dispShipIdBinding.getValue() != null) {
                    shipId = dispShipIdBinding.getValue().toString();
                }

                if (shipIdDocBinding.getValue() != null) {
                    docId = shipIdDocBinding.getValue().toString();
                }

                if (shipDtBinding.getValue() != null) {
                    shipDtN = (Timestamp)shipDtBinding.getValue();
                }

                if (uidCreateDtBinding.getValue() != null) {
                    uIdCreateDtN = (Timestamp)uidCreateDtBinding.getValue();
                }

                try {
                    ShipDt = shipDtN.dateValue();
                    usrIdCreateDt = uIdCreateDtN.dateValue();
                } catch (SQLException sqle) {
                    // TODO: Add catch code
                    sqle.printStackTrace();
                }
                if (eoIdShipBinding.getValue() != null) {
                    eoId = Integer.parseInt(eoIdShipBinding.getValue().toString());
                }
                if (whIdShipBinding.getValue() != null) {
                    whId = whIdShipBinding.getValue().toString();
                }
                OperationBinding cancOrd = executeOperation("updateShipmentStausCancel");
                cancOrd.getParamsMap().put("canc_Flag", cancelOrd);
                cancOrd.getParamsMap().put("eoId", eoId);
                cancOrd.getParamsMap().put("usrIdCreateDt", usrIdCreateDt);
                cancOrd.getParamsMap().put("shipId", shipId);
                cancOrd.getParamsMap().put("ShipDt", ShipDt);
                cancOrd.getParamsMap().put("whId", whId);
                cancOrd.getParamsMap().put("docId", docId);
                cancOrd.execute();
            }
        }
        executeOperation("Commit").execute();
        executeOperation("refreshAllVoOncancellation").execute();
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
        this.pack_mode = "V";
    }

    /*******************Value Change Listeners defined for page********************/

    public void pickDateVCE(ValueChangeEvent vce) {
        /* if (vce.getNewValue() != null) {
            Timestamp pickDt = (Timestamp)vce.getNewValue();
            _log.info("Pick Date is--" + pickDt);
            OperationBinding filterTree = executeOperation("filterOrderData");
            filterTree.getParamsMap().put("pickDate", pickDt);
            filterTree.execute();
            try {
                expandTreeTable();
            } catch (NullPointerException npe) {
                //FacesMessage errMsg = new FacesMessage("No Sales Order to display ,Can not expand ");
                FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.905']}").toString());

                errMsg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, errMsg);
            }
        } */
        _log.info("Refresh Table");
        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        adfFacesContext.addPartialTarget(soTreeTableBind);
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
                expandTreeTable();
            } catch (NullPointerException npe) {
                //FacesMessage errMsg = new FacesMessage("No Sales Order to display ,Can not expand ");
                FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.905']}").toString());

                errMsg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, errMsg);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(soTreeTableBind);
        }
    }


    /**Method to Filter treeTable customer wise
     * @param vce
     */
    public void customerIdVCE(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            /* String eoNm = (vce.getNewValue().toString());
            _log.info("EoId in Bean--" + eoNm);
            OperationBinding ob = executeOperation("filterTreeCustomerWise");
            ob.getParamsMap().put("eo_nm", eoNm);
            ob.execute(); */
            AdfFacesContext.getCurrentInstance().addPartialTarget(soTreeTableBind);

        }
    }

    /**Filter Table as per Selected Customer (First Time)
     * @param vce
     */
    public void selectPickToGenShipVCE(ValueChangeEvent vce) {
        _log.info("New Value-" + vce.getNewValue());
        if (vce.getNewValue() != null && vce.getNewValue().equals(true)) {
            DCIteratorBinding shpMntIter = (DCIteratorBinding)getBindings().get("viewSlsShipmntGen1Iterator");
            Row curRow = shpMntIter.getCurrentRow();
            ViewObject slsShipGen = shpMntIter.getViewObject();
            if (curRow != null && curRow.getAttribute("EoId") != null && selectChkShiCount == 0) {
                Integer eoId = Integer.parseInt(curRow.getAttribute("EoId").toString());
                _log.info("EoId is--" + eoId);
                slsShipGen.setNamedWhereClauseParam("eoIdBind", eoId);
                _log.info("Del address : " + curRow.getAttribute("DlvAddsId"));
                slsShipGen.setNamedWhereClauseParam("DlvAddsBind", curRow.getAttribute("DlvAddsId"));
                slsShipGen.executeQuery();
                shpMntIter.executeQuery();
            }
            selectChkShiCount++;

            AdfFacesContext.getCurrentInstance().addPartialTarget(genShimntTabBind);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(saveShipLinkBVar);

    }

    /**Method to filter tree Warehouse wise
     * @param vce
     */
    public void warehousePickVCE(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            /*  String wareh = vce.getNewValue().toString();
            _log.info("Warehouse is--" + wareh);
            OperationBinding filterTree = executeOperation("filterTreeWarehouse");
            filterTree.getParamsMap().put("whId", wareh);
            filterTree.execute();
            try {
                expandTreeTable();
            } catch (NullPointerException npe) {
               // FacesMessage errMsg = new FacesMessage("No Sales Order to display ,Can not expand ");
               FacesMessage errMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.905']}").toString());

                errMsg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, errMsg);
            } */
        }

        AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
        adfFacesContext.addPartialTarget(soTreeTableBind);
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
            _log.info("isLotValid Returned : "+object);
            if("Y".equals(object.toString())){  
                if (issueQty.getResult() != null) {
                    issQty = (Number)issueQty.getResult();
                    _log.info("pickqty bind-" + pickQtyIssueBind.getValue());
                    if (pickQtyIssueBind.getValue() != null) {
                        pickQty = (Number)pickQtyIssueBind.getValue();
                    }
                    _log.info("IssueQty-" + issQty + "PickedQty-" + pickQty);
                    if (issQty.compareTo(0) == 0) {
                        /*  errMsg = "Please select quantity to issue"; */
                        errMsg = resolvElDCMsg("#{bundle['MSG.941']}").toString();
                        FacesMessage msg = new FacesMessage(errMsg);
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else if (issQty.compareTo(pickQty) != 0) {
                        /* errMsg = "Issued Quantity is not equal to Picked Quantity"; */
                        errMsg = resolvElDCMsg("#{bundle['MSG.949']}").toString();
                        FacesMessage msg = new FacesMessage(errMsg);
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        executeOperation("insertIntoPickItmLot").execute();
                        AdfFacesContext.getCurrentInstance().addPartialTarget(lotTabBind);
                    }
                }
            }else{
                ADFModelUtils.showFormattedFacesMessage("Invalid Lot Issue Quantity !", object.toString(), FacesMessage.SEVERITY_ERROR);
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
            StringBuilder h = (res == null ?  new StringBuilder("") : (StringBuilder)res);
            _log.info("BinIssue check returned : "+h);
            if(!"Y".equals(h.toString())){
                ADFModelUtils.showFormattedFacesMessage("Invalid Lot Issue Quantity !", h.toString(), FacesMessage.SEVERITY_ERROR);
            }else  if (issueQty.getResult() != null) {

                issQty = (Number)issueQty.getResult();
                _log.info("pickqty bind-" + pickQtyIssueBind.getValue());
                if (pickQtyIssueBind.getValue() != null) {
                    pickQty = (Number)pickQtyIssueBind.getValue();
                }
                _log.info("IssueQty-" + issQty + "PickedQty-" + pickQty);
                if (issQty.compareTo(0) == 0) {
                    //errMsg = "Please select quantity to issue";
                    errMsg = resolvElDCMsg("#{bundle['MSG.941']}").toString();

                    FacesMessage msg = new FacesMessage(errMsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else if (issQty.compareTo(pickQty) != 0) {
                    //errMsg = "Issued Quantity is not equal to Picked Quantity";
                    errMsg = resolvElDCMsg("#{bundle['MSG.949']}").toString();
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
                issQty = (Number)issueQty.getResult();
                _log.info("pickqty bind-" + pickQtyIssueBind.getValue());
                if (pickQtyIssueBind.getValue() != null) {
                    pickQty = (Number)pickQtyIssueBind.getValue();
                }
                _log.info("IssueQty-" + issQty + "PickedQty-" + pickQty);
                if (issQty.compareTo(0) == 0) {
                    //errMsg = "Please select quantity to issue";
                    errMsg = resolvElDCMsg("#{bundle['MSG.941']}").toString();

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
            Key object = (Key)binding.getResult();

            if (object != null) {
                packItmKey = (Key)object;
            }
            System.out.println("Pack Item Key in bean: " + packItmKey);
            DCIteratorBinding packItr = (DCIteratorBinding)getBindings().get("SlsPack1Iterator");
            //  Key p = par.getCurrentRow().getKey();
            ViewObject viewObject = packItr.getViewObject();
            System.out.println(" viewObject " + viewObject);

            Row packCurrRow = packItr.getViewObject().getCurrentRow();
            Key packKey = packCurrRow.getKey();
            Row rr = viewObject.getRow(packKey);
            System.out.println("rr " + rr);
            System.out.println("pack item currenct row" + packCurrRow);

            // System.out.println("Pack Key in bean before execeute: " + p);
            //  par.executeQuery();
            DCIteratorBinding packItmItr = (DCIteratorBinding)getBindings().get("SlsPackItm1Iterator");
            /*   ViewObject packItmVw = packItmItr.getViewObject();
            System.out.println(" viewObject "+packItmVw);
            Key packCurrItmKey = packItmItr.getViewObject().getCurrentRow().getKey();
            Row packItmCurrRow=packItmItr.getViewObject().getCurrentRow();
            System.out.println("pack item currenct row"+packCurrRow); */
            // parentIter.executeQuery();

            // executeOperation("Commit").execute();
            executeOperation("Execute").execute();
            executeOperation("Execute1").execute();

            System.out.println("stop execuute");
            System.out.println("packItmItr Iterater count : " + packItmItr.getEstimatedRowCount() + " : " +
                               packItr.getEstimatedRowCount());
            System.out.println("packItr===" + packItr + "viewObject.getRowCount() " + viewObject.getRowCount());
            viewObject.setCurrentRowAtRangeIndex(((viewObject.getRowCount()) - 1));
            /*  if (packItmKey != null) {
                try {
                    System.out.println("set 1------------");
                     packItr.setCurrentRowWithKey(packKey.toStringFormat(true));
                      packItmItr.setCurrentRowWithKey(packItmKey.toStringFormat(true));

                    System.out.println("------------");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error in setting current row.");
                }
            } */
            // executeOperation("Execute").execute();
            // executeOperation("Execute1").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(packItmTableBind);

            /*  System.out.println("cuurent row attribute are:  ");
            for (int i = 0; i < packCurrRow.getAttributeCount(); i++) {
                System.out.println(packCurrRow.getAttribute(i));
            } */
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
            oracle.jbo.domain.Number wght = (oracle.jbo.domain.Number)object;
            if (wght.compareTo(0) == 1 || wght.compareTo(0) == 0) {
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Weight",
                                                               "Can not be negative")); */
                if (isPrecisionValid(26, 6, wght)) {

                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvElDCMsg("#{bundle['MSG.57']}").toString(),
                                                                  resolvElDCMsg("#{bundle['MSG.894']}").toString()));
                }

            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.965']}").toString(),
                                                              resolvElDCMsg("#{bundle['MSG.894']}").toString()));
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
            packedQty = (oracle.jbo.domain.Number)object;
            if (pickedQtyBind.getValue() != null) {
                pickedQty = (oracle.jbo.domain.Number)pickedQtyBind.getValue();
            }
            if (packedQty.compareTo(0) == 1 || packedQty.compareTo(0) == 0) {

                if (isPrecisionValid(26, 6, packedQty)) {

                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvElDCMsg("#{bundle['MSG.57']}").toString(),
                                                                  resolvElDCMsg("#{bundle['MSG.514']}").toString()));
                }

            } else if (packedQty.compareTo(pickedQty) == 1) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.950']}").toString(),
                                                              resolvElDCMsg("#{bundle['MSG.953']}").toString()));
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.950']}").toString(),
                                                              resolvElDCMsg("#{bundle['MSG.514']}").toString()));
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
            Number schdlQuant = (oracle.jbo.domain.Number)object;
            Number pendQty = (oracle.jbo.domain.Number)pendQtyTreetabBind.getValue();

            Row[] rows = getSelectedNodeRowIterator().findByKey(getSelectedNodeRowKey(), 1);
            if (rows != null) {
                for (Row row : rows) {
                    _log.info("TlrncVal-" + row.getAttribute("TlrncQtyVal") + "and type-" +
                              row.getAttribute("TlrncQtyType"));
                    if (row.getAttribute("TlrncQtyVal") != null) {
                        tlrncQty = (Number)row.getAttribute("TlrncQtyVal");
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
                                                              resolvElDCMsg("#{bundle['MSG.336']}").toString(),
                                                              resolvElDCMsg("#{bundle['MSG.955']}").toString() +
                                                              chkPendQtyMin +
                                                              resolvElDCMsg("#{bundle['MSG.957']}").toString() +
                                                              chkPendQtyMax +
                                                              resolvElDCMsg("#{bundle['LBL.723']}").toString()));


            }

        }
    }


    public void setSoTreeTableBind(RichTreeTable soTreeTableBind) {
        this.soTreeTableBind = soTreeTableBind;
    }

    public RichTreeTable getSoTreeTableBind() {
        return soTreeTableBind;
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


    public void setEoIdSrchMainBind(RichInputText eoIdSrchMainBind) {
        this.eoIdSrchMainBind = eoIdSrchMainBind;
    }

    public RichInputText getEoIdSrchMainBind() {
        return eoIdSrchMainBind;
    }

    public void setShpmntIdSrchBind(RichInputText shpmntIdSrchBind) {
        this.shpmntIdSrchBind = shpmntIdSrchBind;
    }

    public RichInputText getShpmntIdSrchBind() {
        return shpmntIdSrchBind;
    }

    public void setPickIdSrchMainBind(RichInputText pickIdSrchMainBind) {
        this.pickIdSrchMainBind = pickIdSrchMainBind;
    }

    public RichInputText getPickIdSrchMainBind() {
        return pickIdSrchMainBind;
    }

    public void setSoIdSrchMainBind(RichInputText soIdSrchMainBind) {
        this.soIdSrchMainBind = soIdSrchMainBind;
    }

    public RichInputText getSoIdSrchMainBind() {
        return soIdSrchMainBind;
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


    public void setPickQtyIssueBind(RichOutputText pickQtyIssueBind) {
        this.pickQtyIssueBind = pickQtyIssueBind;
    }

    public RichOutputText getPickQtyIssueBind() {
        return pickQtyIssueBind;
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

    public void setEoIdShipBinding(RichSelectOneChoice eoIdShipBinding) {
        this.eoIdShipBinding = eoIdShipBinding;
    }

    public RichSelectOneChoice getEoIdShipBinding() {
        return eoIdShipBinding;
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

        Timestamp dt = new Timestamp(System.currentTimeMillis());
        OperationBinding chkForCoa = executeOperation("isCoaIdIsNull");
        chkForCoa.execute();

        System.out.println("chkForCoartn value " + chkForCoa.getResult());

        String rtnVal = (String)chkForCoa.getResult();
        if (pickDateBind.getValue() != null) {
            dt = (Timestamp)pickDateBind.getValue();
        }

        System.out.println("delivery address valu on search click==" + dlvAddsBVar.getValue());

        if (dlvAddsBVar.getValue() != null && "N".equalsIgnoreCase(rtnVal)) {
            header = "N";
            System.out.println("aet 1");
            OperationBinding filterTree = executeOperation("filterOrderData");
            filterTree.getParamsMap().put("pickDate", dt);
            filterTree.execute();
        } else if ("Y".equalsIgnoreCase(rtnVal)) {
            System.out.println("in bean when coa is null");
            FacesMessage msg = new FacesMessage("Coa does not exist for this customer .Please Create COA");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            System.out.println("in bean when add is not tehrhe");
            FacesMessage msg = new FacesMessage("Select Delivery Address");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(dlvAddsIdBind.getClientId(), msg);
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
        Object object = cancelPickListFlag.getValue();
        System.out.println("Val of cancel flag:  " + object);
        if (object != null && (Boolean)object.equals(true)) {
            String string = object.toString();
            System.out.println("Flag value is: on save  " + string);
            Object execute = getBindings().getOperationBinding("cancelPickList").execute();

            if (execute != null && execute.toString().equalsIgnoreCase("y")) {
                FacesMessage errMsg = new FacesMessage("PickList is cancelled successfully.");
                errMsg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(null, errMsg);
            } else {
                FacesMessage errMsg = new FacesMessage("error in cancellation PickList ");
                errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, errMsg);
                return;
            }
            OperationBinding binding = getBindings().getOperationBinding("UpdateOrdStat");
            binding.getParamsMap().put("flag", "C");
            binding.execute();
            getBindings().getOperationBinding("Commit").execute();
            getBindings().getOperationBinding("Execute").execute();
            getBindings().getOperationBinding("Execute1").execute();
            return;
        }
        System.out.println("Start");
        /* OperationBinding opp = executeOperation("setCurrPickItmRowWithKey");
        opp.execute();
          OperationBinding opp = executeOperation("Execute2");
        opp.execute();  */
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
            Number itmStkQnt = (Number)itmTotalStkQtyBVar.getValue();
            System.out.println(" item stock quantity " + itmStkQnt);
            Number issueQtyVlC = (Number)object;
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
                                                              resolvElDCMsg("#{bundle['MSG.57']}").toString(),
                                                              resolvElDCMsg("#{bundle['MSG.894']}").toString()));
            }
        }
    }

    public String showShimpmentDetailACTION() {
        System.out.println("setValuesForShipmentPage  in bean");
        this.getBindings().getOperationBinding("setValuesForShipmentPage").execute();
        return "viewShipment";
    }

    public void setPackNoBind(RichInputText packNoBind) {
        this.packNoBind = packNoBind;
    }

    public RichInputText getPackNoBind() {
        return packNoBind;
    }

    public void packQuantityValid(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /* if (object != null) {
            OperationBinding binding = this.getBindings().getOperationBinding("isPackQuantityValid");
            binding.getParamsMap().put("val", (Number)object);
            binding.execute();
            Object object_2 = binding.getResult();
            _log.info("Returned " + object_2);
            if (object_2.equals((Object)false)) {
                throw new ValidatorException(new FacesMessage("Invalid Quantity !"));
            }
        } */
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

        Timestamp dt = new Timestamp(System.currentTimeMillis());
        if (pickDateBind.getValue() != null) {
            dt = (Timestamp)pickDateBind.getValue();
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

        OperationBinding obb = executeOperation("delLotbinSrData");
        obb.execute();
        executeOperation("Execute3").execute();
        OperationBinding ob = executeOperation("rePopulateItemToPickList");
        ob.execute();
        selectQuantTransTreeTabBind.setValue(0);
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
                Boolean b = (Boolean)ob.getResult();
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

    public void cancelChangeAction(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(updateButtonBind);
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
}
