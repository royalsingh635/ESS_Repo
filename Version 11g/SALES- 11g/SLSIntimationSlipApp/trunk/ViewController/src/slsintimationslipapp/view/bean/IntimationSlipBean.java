package slsintimationslipapp.view.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

import slsintimationslipapp.model.views.SlsIntmSlipVOImpl;


public class IntimationSlipBean {
    private String mode = "V";
    private RichInputDate docDtBinding;
    private RichInputText eoIdBinding;
    private RichInputText soldQntyBinding;
    private RichInputText shipmentQtyBinding;
    private RichTreeTable treeTableBinding;
    private RichInputText docIdNBinding;
    private RichInputDate shipmentDtBinding;
    private RichInputComboboxListOfValues intimantionNoBinding;
    private RichInputComboboxListOfValues shipIdBinding;
    private RichInputComboboxListOfValues consigneeBinding;
    private RichInputComboboxListOfValues itemIdBeanBinding;
    private RichInputText consigneeIdBinding;
    private RichInputText itemSearchBinding;
    private RichInputText shipmentSearchBinding;
    private boolean isSoldDisable = true;


    private static ADFLogger logAdf = ADFLogger.createADFLogger(IntimationSlipBean.class);

    private RowKeySet disclosedTreeRowKeySet = new RowKeySetImpl();

    private String cldIdVar = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
    private Integer slocIdVar = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
    private String orgIdVar = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
    //private String hoOrgIdVar=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
    private Integer usrIdVar = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
    private RichSelectOneChoice shipmentIdtreeBinding;
    private RichInputText docTxnIdBinding;
    private String docIdbeanBind;

    Map<Row, String> checkItmMstr = new HashMap<Row, String>();
    Row row = null;
    String flgItm = null;
    private RichTable intmTabBind;
    private RichInputText balItmQtyBinding;
    private RichInputText balQtyTransBind;
    private String docHexId=null;
    private RichSelectOneChoice intmInvModeBinding;
    private RichSelectOneChoice statusBinding;
    private RichSelectOneChoice modeIntmBinding;
    private RichInputComboboxListOfValues eoIdNbinding;



    @PostConstruct 
    public void runCretria(){
        System.out.println("PostConstruct ----runCretria ");

        OperationBinding operationBinding = null;
        //if (operationBinding) {
            operationBinding = getBindings().getOperationBinding("getfilterCreteria");
            operationBinding.execute();
       // }
    }
    
    
    
    public IntimationSlipBean() {
    }


    public static BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    /**
     *
     * @param data
     * @return
     */
    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }


    /**
     *
     * @param data
     * @return
     */
    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    /**
     *
     * @param data
     * @return
     */
    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }


    public String createIntimationSlipAction() {
        // Add event code here...
    getBindings().getOperationBinding("setBindVarToNull").execute();
        setMode("A");
        setIsSoldDisable(false);
        OperationBinding operationBinding = getBindings().getOperationBinding("Createwithparameters");
        operationBinding.execute();
        logAdf.info("mode : " + mode);
        return null;
    }


      public String editIntimationSlipAction() {
        // Add event code here...
        this.setMode("E");     
        return null;
    } 
    
    
    public void saveFunction() {
        {
            OperationBinding operationBinding = null;
            if(docTxnIdBinding.getValue()!=null){
                docHexId=docTxnIdBinding.getValue().toString();//SlsIntmSlipVOCriteria1
                logAdf.info("docHexId" + docHexId);
            }
            
            if (mode == "A" || true) {
                logAdf.info("mode :" + mode);
                operationBinding = getBindings().getOperationBinding("Commit");
                operationBinding.execute();
                operationBinding = getBindings().getOperationBinding("getDeleteRowsSldQty");
                logAdf.info("getDeleteRowswSldQty---" + operationBinding);
                operationBinding.execute();
                
                this.saveToWorkFlow();
                operationBinding = getBindings().getOperationBinding("isIsAllItmZero");
                operationBinding.execute();
                Boolean isVal = (Boolean)operationBinding.getResult();
                if (isVal) {
                    operationBinding = getBindings().getOperationBinding("Delete");
                    operationBinding.execute();
                }

                operationBinding = getBindings().getOperationBinding("Commit");
                operationBinding.execute();
                operationBinding = getBindings().getOperationBinding("Execute");
                operationBinding.execute();
                operationBinding = getBindings().getOperationBinding("Execute1");
                operationBinding.execute();
                operationBinding = getBindings().getOperationBinding("Execute2");
                operationBinding.execute();
                
            }
            setMode("V");
            operationBinding = getBindings().getOperationBinding("getRowAftrInsert");
            operationBinding.getParamsMap().put("docId", docHexId);
            operationBinding.execute();
        }
    }

    public String saveAction() {
        // Add event code here...
        
       /*  if(docTxnIdBinding.getValue()!=null){
            docHexId=docTxnIdBinding.getValue().toString();//SlsIntmSlipVOCriteria1
            logAdf.info("docHexId" + docHexId);
        }
        operationBinding = getBindings().getOperationBinding("resIntimationSlip");
        operationBinding.getParamsMap().put("docTxnIdRes", docHexId);
        operationBinding.execute(); */
        OperationBinding operationBinding = null;
        if (mode == "A" || true) {
            logAdf.info("mode :" + mode);
            operationBinding = getBindings().getOperationBinding("Commit");
            operationBinding.execute();
            /* operationBinding = getBindings().getOperationBinding("getDeleteRowsSldQty");
            logAdf.info("getDeleteRowswSldQty---" + operationBinding);
            operationBinding.execute(); */
            this.saveFunction();
        }
        logAdf.info("Saved");
        String msg = resolvElDCMsg("#{bundle['MSG.818']}").toString();//Record Saved Successfully!
        FacesMessage message = new FacesMessage(msg);
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message);
        
        
        return null;
    }


    public String cancelAction() {
        // Add event code here...
        
        Key parentKey = null;
        DCIteratorBinding parentIter = null;
        if (mode.equalsIgnoreCase("A")) {
        getBindings().getOperationBinding("deleteIntimationOnCancel").execute();
            //AdfFacesContext.getCurrentInstance().addPartialTarget(eoidTransBindVar);
            OperationBinding operationBinding = getBindings().getOperationBinding("Rollback");
            operationBinding.execute();
            /* logAdf.info("After Rollback");
            operationBinding = getBindings().getOperationBinding("getIntmSlipVoImplAM");
            operationBinding.execute();
            ViewObject object1 = (ViewObject)operationBinding.getResult();
            object1.executeQuery();
            operationBinding = getBindings().getOperationBinding("getIntmSlipItmVoImplAM");
            operationBinding.execute();
            ViewObject object2 = (ViewObject)operationBinding.getResult();
            object2.executeQuery();
      */       operationBinding = getBindings().getOperationBinding("Execute");
            operationBinding.execute();
            operationBinding = getBindings().getOperationBinding("Execute1");
            operationBinding.execute();
            operationBinding = getBindings().getOperationBinding("Execute2");
            operationBinding.execute();

        } else {
            parentIter = (DCIteratorBinding)getBindings().get("SlsIntmSlipIterator");
            if (parentIter != null) {
                parentKey = parentIter.getCurrentRow().getKey();
                logAdf.info("Parentkey " + parentKey);
                BindingContainer binding = getBindings();
                System.out.println("binding " + binding);
                logAdf.info("canceling");
                OperationBinding operationBinding = getBindings().getOperationBinding("Rollback");
                operationBinding.execute();
                operationBinding = getBindings().getOperationBinding("Execute2");
                operationBinding.execute();
                try {
                    if (parentIter != null && parentKey != null) {
                        parentIter.executeQuery();
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                } catch (Exception e) {
                    // TODO: Add catch code

                    e.printStackTrace();
                }
            }
        }
        setMode("V");
        return null;

    }

    public String insertIntoItemAction() {

        // Add event code here...
     
        Timestamp docDate = null;
        Integer eoId = null;
        //Key parentKey = null;
        //DCIteratorBinding parentIter = null;
        setMode("A");
        if (mode.equalsIgnoreCase("A")) {
            logAdf.info("mode : " + mode);
            String docIdN = docIdNBinding.getValue().toString();
            logAdf.info(" DocIdN : " + docIdN);
            docDate = (Timestamp)docDtBinding.getValue();
            eoId=Integer.parseInt(eoIdBinding.getValue().toString());

            BindingContainer binding = getBindings();
            logAdf.info("binding " + binding);
            OperationBinding operationBinding = getBindings().getOperationBinding("getAutoRowsIntmnSlipItm");
            operationBinding.getParamsMap().put("eoIdAM", eoId);
            operationBinding.getParamsMap().put("docDtAM", docDate);
            operationBinding.getParamsMap().put("docIdAM", docIdN);
            if ((mode == "A") || (mode == "E")) {
                logAdf.info("Saving");
                logAdf.info("eoId :=" + eoId + " docDate:= " + docDate);
                operationBinding.execute();
                Object autoinsertRows = operationBinding.getResult();
                logAdf.info("autoinsertRows" + autoinsertRows);
                if ((autoinsertRows != null)) {
                    System.out.println("returned by function dispDocId" + operationBinding.execute());

                    operationBinding = getBindings().getOperationBinding("Commit");
                    operationBinding.execute();
                    //operationBinding = getBindings().getOperationBinding("getRowsIntmSlip");
                    //System.out.println(docIdN);
                    //operationBinding.getParamsMap().put("docIdN", docIdN);
                    //operationBinding.execute();
                    operationBinding = getBindings().getOperationBinding("Execute");
                    operationBinding.execute();
                    operationBinding = getBindings().getOperationBinding("Execute1");
                    operationBinding.execute();
                    operationBinding = getBindings().getOperationBinding("Commit");
                    operationBinding.execute();
                    operationBinding = getBindings().getOperationBinding("Rollback");
                    operationBinding.execute();
                    operationBinding = getBindings().getOperationBinding("getRowAftrInsert");
                    operationBinding.getParamsMap().put("docId", docIdN);

                    operationBinding.execute();
                    logAdf.info(" DocIdN after Commit : " + docIdN);
                    logAdf.info("canceling");
                    setMode("A");
                }
            }
        }
        setMode("A");
        return null;
    }

    public String searchAction() {
        // Add event code here...
        
        logAdf.info(" in Search Action-----------------------------");
        //getBindings().getOperationBinding("setBindVarToNull").execute();
        System.out.println(itemIdBeanBinding.getValue());
        System.out.println(itemSearchBinding.getValue());
        OperationBinding operationBinding=null;
        if (mode!="A"||mode!="E") {
            operationBinding = getBindings().getOperationBinding("getRowAftrInsert");
            operationBinding.getParamsMap().put("docId", "R");
            operationBinding.execute();
        }
        String intmId = null;
        Timestamp shipDt = null;
        Integer consID = null;
        String shipId = null;
        String itemId = null;
        /* 
        Key parentKey=null;
        DCIteratorBinding parentIter=null;
        if (true) {
                   parentIter = (DCIteratorBinding)getBindings().get("SlsIntmSlipIterator");
                   parentKey = parentIter.getCurrentRow().getKey();
                   logAdf.info("Parentkey " + parentKey);
                   BindingContainer binding = getBindings();
                   System.out.println("binding " + binding);
        }  
        */
        operationBinding = getBindings().getOperationBinding("getIntmSlipVoImplAM");
        operationBinding.execute();
        SlsIntmSlipVOImpl object1 = (SlsIntmSlipVOImpl)operationBinding.getResult();
        operationBinding = getBindings().getOperationBinding("getIntmSlipItmVoImplAM");
        operationBinding.execute();
        ViewObject object2 = (ViewObject)operationBinding.getResult();
        if (intimantionNoBinding.getValue() != null && intimantionNoBinding.getValue() != "") {
            intmId = "01"+intimantionNoBinding.getValue().toString();
            logAdf.info("Search row count after entering Intimation No : " + intmId + " Row Count :"+object1.getRowCount());
        }
        if (shipmentDtBinding.getValue() != null && shipmentDtBinding.getValue() != "") {
            shipDt = (Timestamp)shipmentDtBinding.getValue();
            logAdf.info("Search row count after entering Shipment date :" + shipDt +" Row Count :"+ object1.getRowCount());
        }
        logAdf.info("consId : " + consigneeIdBinding.getValue());
        if (consigneeIdBinding.getValue() != null &&
            (consigneeBinding.getValue() != null || consigneeBinding.getValue() != "")) {
            consID = Integer.parseInt(consigneeIdBinding.getValue().toString());
            logAdf.info("Search row count after entering Consignee Name : " + consID +" Row Count :"+ object1.getRowCount());
        }
        if (shipmentSearchBinding.getValue() != null && shipmentSearchBinding.getValue() != "") {
            shipId = shipmentSearchBinding.getValue().toString();
            logAdf.info("Search row count after entering Consignee Name : " + shipId + "  rowcount : " +
                        object2.getRowCount());
        }
        if ((itemIdBeanBinding.getValue() != null || itemIdBeanBinding.getValue() != "") &&
            itemSearchBinding.getValue() != null) {
            itemId = itemSearchBinding.getValue().toString();
            logAdf.info("Item Id is " + itemId);
        }
        object1.setNamedWhereClauseParam("dispDocIdBindvar", intmId);
        object1.setNamedWhereClauseParam("docDtBindVar", shipDt);
        logAdf.info("consId before search : " + consID);
        object1.setNamedWhereClauseParam("eoIdBindVar", consID);
        object2.setNamedWhereClauseParam("shipDocIdBindVar", shipId);

        if (consigneeBinding.getValue() != null || intimantionNoBinding.getValue() != null ||
            shipmentDtBinding.getValue() != null) {
            logAdf.info("Execute Intimation slip VO");

            object1.executeQuery();
            logAdf.info("row count after searching  : " + object1.getRowCount());
        }
        if (shipmentSearchBinding.getValue() != null) {
            logAdf.info("Search row count after entering Shipmemnt No : " + shipId +" Row Count :"+ object2.getRowCount());
            object2.executeQuery();
        }
        logAdf.info("item id binding" + itemIdBeanBinding.getValue());
        if ((itemIdBeanBinding.getValue() != null || itemIdBeanBinding.getValue() != "") ||
            itemSearchBinding.getValue() != null) {
            OperationBinding operationBindingGc = getBindings().getOperationBinding("setItemIdAmVar");
            operationBindingGc.getParamsMap().put("itemIdAmVar", itemId);
            operationBindingGc.execute();
            logAdf.info(" Search Item :" + itemId + operationBinding.getResult());
        }
        
        if (treeTableBinding.getRowCount() > 0) {
            expandTreeTable();
        }
        return null;
    }


    public String resetAction() {
        logAdf.info("reset Action");
        //getBindings().getOperationBinding("setBindVar").execute();
        OperationBinding operationBinding = getBindings().getOperationBinding("getIntmSlipVoImplAM");
        operationBinding.execute();
        ViewObject object1 = (ViewObject)operationBinding.getResult();
        operationBinding = getBindings().getOperationBinding("getIntmSlipItmVoImplAM");
        operationBinding.execute();
        ViewObject object2 = (ViewObject)operationBinding.getResult();
        object1.setNamedWhereClauseParam("dispDocIdBindvar", null);
        object1.setNamedWhereClauseParam("docDtBindVar", null);
        object1.setNamedWhereClauseParam("eoIdBindVar", null);
        object1.executeQuery();
        System.out.println(object2.getRowCount());
        object2.setNamedWhereClauseParam("shipDocIdBindVar", null);
        object2.executeQuery();
        System.out.println(itemIdBeanBinding.getValue());
        operationBinding = getBindings().getOperationBinding("getItemIdBindingBean");
        operationBinding.getParamsMap().put("itmIdbean", null);
        operationBinding.execute();
        operationBinding = getBindings().getOperationBinding("getResetFields");
        operationBinding.execute();
        collapseTreeTable();
        return "reset";
    }

    public void collapseTreeTable() {
        treeTableBinding.getDisclosedRowKeys().clear();
        AdfFacesContext.getCurrentInstance().addPartialTarget(treeTableBinding);
    }

    public void quantityValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        logAdf.info("facescontext" + facesContext);
        logAdf.info("UIComponent" + uIComponent);
        logAdf.info("Validator" + uIComponent);
        if ((object != null) && (balItmQtyBinding.getValue() != null)) {
            Number zero = new Number(0);
            Number soldQty = (Number)object;
            Number balQty = (Number)balItmQtyBinding.getValue();
            logAdf.info("balQty : " + balQty);

            //Number zero = new Number(0);
            if (balQty != zero) {
                if (soldQty.compareTo(zero) == -1) {
                    String msg = resolvElDCMsg("#{bundle['MSG.1006']}").toString();//Value of Sold quantity cannot be less than zero
                    FacesMessage facesMsg = new FacesMessage(msg);
                    throw new ValidatorException(facesMsg);
                    //new FacesMessage(facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR),msg,null)
                    //facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    //facesContext.addMessage(soldQntyBinding.getClientId(), facesMsg);
                }

                if ((soldQty.compareTo(balQty) == 1)) {
                    String msg =resolvElDCMsg("#{bundle['MSG.1008']}").toString();//Sold quantity must be less than or equal to Balance quantity
                    FacesMessage facesMsg = new FacesMessage(msg);
                    facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(facesMsg);
                    //facesContext.addMessage(soldQntyBinding.getClientId(), facesMsg);
                }

            }

        } else {
            String msg = resolvElDCMsg("#{bundle['MSG.959']}").toString();//Please Enter the value
            FacesMessage facesMsg = new FacesMessage(msg);
            facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMsg);
            //facesContext.addMessage(soldQntyBinding.getClientId(), facesMsg);
        }
    }

    public RowIterator getSelectedNodeIterator() {
        if (getTreeTableBinding() != null && getTreeTableBinding().getSelectedRowKeys() != null) {
            for (Object rKey : getTreeTableBinding().getSelectedRowKeys()) {
                getTreeTableBinding().setRowKey(rKey);
                logAdf.info("Row Iterator Binding" +
                            ((JUCtrlHierNodeBinding)getTreeTableBinding().getRowData()).getRowIterator());
                return ((JUCtrlHierNodeBinding)getTreeTableBinding().getRowData()).getRowIterator();
            }
        }
        return null;
    }

    /**Method to get Node Key*/
    public Key getSelectedNodeKey() {
        if (getTreeTableBinding() != null && getTreeTableBinding().getSelectedRowKeys() != null) {
            for (Object rKey : getTreeTableBinding().getSelectedRowKeys()) {
                getTreeTableBinding().setRowKey(rKey);
                logAdf.info("RowKey Binding" +
                            ((JUCtrlHierNodeBinding)getTreeTableBinding().getRowData()).getRowKey());
                return ((JUCtrlHierNodeBinding)getTreeTableBinding().getRowData()).getRowKey();
            }
        }
        return null;
    }

    public String addToItemAction() {
        logAdf.info("AddToItemAction");
        // Add event code here...getItmCurrentRow
        OperationBinding operationBinding = getBindings().getOperationBinding("getItmCurrentRow");
        operationBinding.execute();
        Row rwNewRow = (Row)operationBinding.getResult();
        checkItmMstr.put(rwNewRow, flgItm);
        for (Map.Entry<Row, String> entry : checkItmMstr.entrySet()) {
            Row key = entry.getKey();
            String value = entry.getValue().toString();
            logAdf.info("Key = " + key + ", Value = " + value);
        }

        return null;
    }

    public void saveToWorkFlow() {
        String wfNo = "W050";
        Integer level = 0;
        String action = "I";
        String remark = "A";
        Integer res = -1;
        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
        WfnoOp.getParamsMap().put("SlocId", slocIdVar);
        WfnoOp.getParamsMap().put("CldId", cldIdVar);
        WfnoOp.getParamsMap().put("OrgId", orgIdVar);
        WfnoOp.getParamsMap().put("DocNo", 21509);
        WfnoOp.execute();
        if (WfnoOp.getResult() != null) {
            wfNo = WfnoOp.getResult().toString();
        }

        OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
        usrLevelOp.getParamsMap().put("SlocId", slocIdVar);
        usrLevelOp.getParamsMap().put("CldId", cldIdVar);
        usrLevelOp.getParamsMap().put("OrgId", orgIdVar);
        usrLevelOp.getParamsMap().put("UsrId", usrIdVar);
        usrLevelOp.getParamsMap().put("WfNo", wfNo);
        usrLevelOp.getParamsMap().put("DocNo", 21509);
        usrLevelOp.execute();
        if (usrLevelOp.getResult() != null) {
            level = Integer.parseInt(usrLevelOp.getResult().toString());
        }

        OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
        insTxnOp.getParamsMap().put("SlocId", slocIdVar);
        insTxnOp.getParamsMap().put("CldId", cldIdVar);
        insTxnOp.getParamsMap().put("OrgId", orgIdVar);
        insTxnOp.getParamsMap().put("DocNo", 21509);
        insTxnOp.getParamsMap().put("WfNo", wfNo);
        insTxnOp.getParamsMap().put("usr_idFrm", usrIdVar);
        insTxnOp.getParamsMap().put("usr_idTo", usrIdVar);
        insTxnOp.getParamsMap().put("levelFrm", level);
        insTxnOp.getParamsMap().put("levelTo", level);
        insTxnOp.getParamsMap().put("action", action);
        insTxnOp.getParamsMap().put("remark", remark);
        insTxnOp.getParamsMap().put("amount", 0);
        insTxnOp.execute();

        if (insTxnOp.getResult() != null) {
            res = Integer.parseInt(insTxnOp.getResult().toString());
        }
    }

    private void expandTreeTable() {
        if (this.treeTableBinding != null) {
            disclosedTreeRowKeySet = new RowKeySetImpl();
            CollectionModel model = (CollectionModel)treeTableBinding.getValue();
            JUCtrlHierBinding treeBinding = (JUCtrlHierBinding)model.getWrappedData();
            JUCtrlHierNodeBinding rootNode = treeBinding.getRootNodeBinding();
            disclosedTreeRowKeySet = treeTableBinding.getDisclosedRowKeys();
            if (disclosedTreeRowKeySet == null) {
                disclosedTreeRowKeySet = new RowKeySetImpl();
            }
            List<JUCtrlHierNodeBinding> firstLevelChildren = rootNode.getChildren();
            for (JUCtrlHierNodeBinding node : firstLevelChildren) {
                ArrayList list = new ArrayList();
                list.add(node.getRowKey());
                disclosedTreeRowKeySet.add(list);
                expandTreeChildrenNode(treeTableBinding, node, list);
            }
            treeTableBinding.setDisclosedRowKeys(disclosedTreeRowKeySet);
        }
    }

    /**Method to expand childs*/
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


    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    /*  public void setEoIdTransBinding(RichInputComboboxListOfValues eoIdTransBinding) {
        this.eoIdTransBinding = eoIdTransBinding;
    }

    public RichInputComboboxListOfValues getEoIdTransBinding() {
        return eoIdTransBinding;
    } */

    public void setDocDtBinding(RichInputDate docDtBinding) {
        this.docDtBinding = docDtBinding;
    }

    public RichInputDate getDocDtBinding() {
        return docDtBinding;
    }

    public void setEoIdBinding(RichInputText eoIdBinding) {
        this.eoIdBinding = eoIdBinding;
    }

    public RichInputText getEoIdBinding() {
        return eoIdBinding;
    }

    public void setSoldQntyBinding(RichInputText soldQntyBinding) {
        this.soldQntyBinding = soldQntyBinding;
    }

    public RichInputText getSoldQntyBinding() {
        return soldQntyBinding;
    }

    public void setShipmentQtyBinding(RichInputText shipmentQtyBinding) {
        this.shipmentQtyBinding = shipmentQtyBinding;
    }

    public RichInputText getShipmentQtyBinding() {
        return shipmentQtyBinding;
    }


    public void setTreeTableBinding(RichTreeTable treeTableBinding) {
        this.treeTableBinding = treeTableBinding;
    }

    public RichTreeTable getTreeTableBinding() {
        return treeTableBinding;
    }


    public void setDocIdNBinding(RichInputText docIdNBinding) {
        this.docIdNBinding = docIdNBinding;
    }

    public RichInputText getDocIdNBinding() {
        return docIdNBinding;
    }


    public void setShipmentDtBinding(RichInputDate shipmentDtBinding) {
        this.shipmentDtBinding = shipmentDtBinding;
    }

    public RichInputDate getShipmentDtBinding() {
        return shipmentDtBinding;
    }


    public void setIntimantionNoBinding(RichInputComboboxListOfValues intimantionNoBinding) {
        this.intimantionNoBinding = intimantionNoBinding;
    }

    public RichInputComboboxListOfValues getIntimantionNoBinding() {
        return intimantionNoBinding;
    }


    public void setShipIdBinding(RichInputComboboxListOfValues shipIdBinding) {
        this.shipIdBinding = shipIdBinding;
    }

    public RichInputComboboxListOfValues getShipIdBinding() {
        return shipIdBinding;
    }

    public void setConsigneeBinding(RichInputComboboxListOfValues consigneeBinding) {
        this.consigneeBinding = consigneeBinding;
    }

    public RichInputComboboxListOfValues getConsigneeBinding() {
        return consigneeBinding;
    }

    public void setItemIdBeanBinding(RichInputComboboxListOfValues itemIdBeanBinding) {
        this.itemIdBeanBinding = itemIdBeanBinding;
    }

    public RichInputComboboxListOfValues getItemIdBeanBinding() {
        return itemIdBeanBinding;
    }

    public void setConsigneeIdBinding(RichInputText consigneeIdBinding) {
        this.consigneeIdBinding = consigneeIdBinding;
    }

    public RichInputText getConsigneeIdBinding() {
        return consigneeIdBinding;
    }

    public void setItemSearchBinding(RichInputText itemSearchBinding) {
        this.itemSearchBinding = itemSearchBinding;
    }

    public RichInputText getItemSearchBinding() {
        return itemSearchBinding;
    }

    public void setShipmentSearchBinding(RichInputText shipmentSearchBinding) {
        this.shipmentSearchBinding = shipmentSearchBinding;
    }

    public RichInputText getShipmentSearchBinding() {
        return shipmentSearchBinding;
    }


    /* String docIdN=docIdNBinding.getValue().toString();
    OperationBinding operationBinding = getBindings().getOperationBinding("getAutoRowsIntmnSlipItm");
    if ((mode == "A") || (mode == "E")) {
        System.out.println("Saving");
        Timestamp docDate = (Timestamp)docDtBinding.getValue();
        Integer eoId = (Integer)eoIdBinding.getValue();
        System.out.println("eoId :=" + eoId + " docDate:= " + docDate);
        operationBinding.execute();
        Object autoinsertRows = operationBinding.getResult();
        System.out.println(autoinsertRows);
        if ((autoinsertRows != null)) {
            System.out.println("returned by function dispDocId" + operationBinding.execute());
            operationBinding = getBindings().getOperationBinding("Commit");
            operationBinding.execute();
            operationBinding = getBindings().getOperationBinding("getRowsIntmSlip");
            System.out.println(docIdN);
            operationBinding.getParamsMap().put("docIdN", docIdN);
            operationBinding.execute();
            operationBinding = getBindings().getOperationBinding("Execute");
            operationBinding.execute();
            operationBinding = getBindings().getOperationBinding("Execute1");
            operationBinding.execute();
            operationBinding = getBindings().getOperationBinding("Commit");
            operationBinding.execute();
            System.out.println("Saved");
        }
    }else{
        operationBinding = getBindings().getOperationBinding("getDeleteRowsSldQty");
        operationBinding.execute();
        operationBinding = getBindings().getOperationBinding("Commit");
        operationBinding.execute();
        operationBinding = getBindings().getOperationBinding("getRowsIntmSlip");
        System.out.println(docIdN);
        operationBinding.getParamsMap().put("docIdN", docIdN);
        operationBinding.execute();
        operationBinding = getBindings().getOperationBinding("Commit");
        operationBinding.execute();
        operationBinding = getBindings().getOperationBinding("Execute");
        operationBinding.execute();
        operationBinding = getBindings().getOperationBinding("Execute1");
        operationBinding.execute();
        System.out.println("Saved");
        String msg = "Record Saved Successfully!";
        FacesMessage message = new FacesMessage(msg);
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message);
    }
    setMode("V"); */

    /*     public void setEoidTransBindVar(RichInputComboboxListOfValues eoidTransBindVar) {
        this.eoidTransBindVar = eoidTransBindVar;
    }

    public RichInputComboboxListOfValues getEoidTransBindVar() {
        return eoidTransBindVar;
    } */

    public String saveAndForwardAction() {
        // Add event code here...
        
        OperationBinding operationBinding = null;
        logAdf.info("mode :" + mode);
        operationBinding = getBindings().getOperationBinding("Commit");
        operationBinding.execute();
        operationBinding = getBindings().getOperationBinding("getDeleteRowsSldQty");
        logAdf.info("getDeleteRowswSldQty---" + operationBinding);
        operationBinding.execute();
        operationBinding = getBindings().getOperationBinding("updateBalQtyShip");
        logAdf.info("updateBalQtyShip---" + operationBinding);
        operationBinding.getParamsMap().put("CldId", cldIdVar);
        operationBinding.getParamsMap().put("OrgId", orgIdVar);
        operationBinding.getParamsMap().put("SlocId", slocIdVar);
        logAdf.info("docTxnIdBinding : " + docTxnIdBinding.getValue().toString() + " docIdN : " +
                    docIdNBinding.getValue().toString());
        operationBinding.getParamsMap().put("DocId", docTxnIdBinding.getValue().toString());
        operationBinding.execute();
        Number balQty = (Number)operationBinding.getResult();
        logAdf.info("balQty : " + balQty);
        this.saveFunction();
        this.saveToWorkFlow();
        Integer pending = null;
        OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
        chkUsr.getParamsMap().put("SlocId", slocIdVar);
        chkUsr.getParamsMap().put("CldId", cldIdVar);
        chkUsr.getParamsMap().put("OrgId", orgIdVar);
        chkUsr.getParamsMap().put("DocNo", 21509);
        chkUsr.execute();
        setMode("V");
        if (chkUsr.getResult() != null) {
            setMode("V");
            pending = Integer.parseInt(chkUsr.getResult().toString());
            //       System.out.println("Pending"+pending);
        }
        if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(usrIdVar) == 0) {
            RequestContext rc = RequestContext.getCurrentInstance();
            rc.getPageFlowScope().put("Doc_txn_id", docTxnIdBinding.getValue().toString());
            getBindings().getOperationBinding("Commit").execute();
            setMode("V");
            //setIsSoldDisable(true);
            return "goToWfTF";
        } else {
            //setIsSoldDisable(true);
            String msg2 = resolvElDCMsg("#{bundle['MSG.1054']}").toString();//This Slip is pending at other user for approval, You can not forward this.
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            return null;
        }


    }

    public void setShipmentIdtreeBinding(RichSelectOneChoice shipmentIdtreeBinding) {
        this.shipmentIdtreeBinding = shipmentIdtreeBinding;
    }

    public RichSelectOneChoice getShipmentIdtreeBinding() {
        return shipmentIdtreeBinding;
    }

    public void setDocTxnIdBinding(RichInputText docTxnIdBinding) {
        this.docTxnIdBinding = docTxnIdBinding;
    }

    public RichInputText getDocTxnIdBinding() {
        return docTxnIdBinding;
    }

    public void setDocIdbeanBind(String docIdbeanBind) {
        this.docIdbeanBind = docIdbeanBind;
    }

    public String getDocIdbeanBind() {
        if (docTxnIdBinding.getValue() != null) {
            return docTxnIdBinding.getValue().toString();
        } else {
            return docIdbeanBind;
        }
    }

    public void setIntmTabBind(RichTable intmTabBind) {
        this.intmTabBind = intmTabBind;
    }

    public RichTable getIntmTabBind() {
        return intmTabBind;
    }

    public void soldQtyValueChngListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...


        if (valueChangeEvent.getNewValue() != null) {
            RowIterator ri = this.getSelectedNodeIterator();
            Key selectedNodeKey = this.getSelectedNodeKey();
            Row[] rows = ri.findByKey(selectedNodeKey, 1);
            Number sldItmQty = new Number(0);
            Number oldbalQty = new Number(0);
            if (balItmQtyBinding.getValue() != null) {
                //showCurRowDetail(getSelectedNodeIterator(), getSelectedNodeKey());
                oldbalQty = (Number)balItmQtyBinding.getValue();
                sldItmQty = (Number)valueChangeEvent.getNewValue();
                //sldItmQty=(Number)rows[0].getAttribute("SoldQty");
                Number newbalItmQty = oldbalQty.subtract(sldItmQty);
                logAdf.info("sldItmQty : " + sldItmQty);
                logAdf.info("newbalItmQty : " + newbalItmQty);
                logAdf.info("oldbalQty : " + oldbalQty);
                rows[0].setAttribute("BalQtyTrans", newbalItmQty);
                OperationBinding opB = getBindings().getOperationBinding("setQtyBs");
                opB.getParamsMap().put("row",rows[0]);
                logAdf.info("rows[0]" + rows[0]);
                opB.getParamsMap().put("itmQty",sldItmQty);
                logAdf.info("sldItmQty" + sldItmQty);
                opB.execute();
            }
        }
    }

    public void setBalItmQtyBinding(RichInputText balItmQtyBinding) {
        this.balItmQtyBinding = balItmQtyBinding;
    }

    public RichInputText getBalItmQtyBinding() {
        return balItmQtyBinding;
    }

    public void setBalQtyTransBind(RichInputText balQtyTransBind) {
        this.balQtyTransBind = balQtyTransBind;
    }

    public RichInputText getBalQtyTransBind() {
        return balQtyTransBind;
    }

    public void consigneeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
    }

    public void setIntmInvModeBinding(RichSelectOneChoice intmInvModeBinding) {
        this.intmInvModeBinding = intmInvModeBinding;
    }

    public RichSelectOneChoice getIntmInvModeBinding() {
        return intmInvModeBinding;
    }

    public void setStatusBinding(RichSelectOneChoice statusBinding) {
        this.statusBinding = statusBinding;
    }

    public RichSelectOneChoice getStatusBinding() {
        return statusBinding;
    }

    public void setModeIntmBinding(RichSelectOneChoice modeIntmBinding) {
        this.modeIntmBinding = modeIntmBinding;
    }

    public RichSelectOneChoice getModeIntmBinding() {
        return modeIntmBinding;
    }

    public void setIsSoldDisable(boolean isSoldDisable) {
        this.isSoldDisable = isSoldDisable;
    }

    public boolean isIsSoldDisable() {
        return isSoldDisable;
    }

    public void setEoIdNbinding(RichInputComboboxListOfValues eoIdNbinding) {
        this.eoIdNbinding = eoIdNbinding;
    }

    public RichInputComboboxListOfValues getEoIdNbinding() {
        return eoIdNbinding;
    }
}
