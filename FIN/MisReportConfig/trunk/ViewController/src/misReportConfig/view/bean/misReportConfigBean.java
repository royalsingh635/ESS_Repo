package misReportConfig.view.bean;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;

import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.el.MethodExpression;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import oracle.jbo.uicli.binding.JUCtrlValueBindingRef;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.model.TreeModel;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class misReportConfigBean {

    private RichPopup rptConfigPopup;
    private RichPopup rptConfigCoaPopup;
    private RichTreeTable treeTabBind;
    private String hdrDesc =resolvElDCMsg("#{bundle['MSG.651']}").toString();
    //    "Report Configurator form is used to plan and organize management information system. Through this page we can configure entities involved in MIS Dashboard Page.";
    private boolean saveDisabled;
    private RichTable numTableBinding;
    private RichTable configTableBinding;
    private RichTable configCoaTableBinding;
    private RichPanelFormLayout searchForm;
    private RowKeySet disclosedTreeRowKeySet = new RowKeySetImpl();
    private RichInputText searchField;

    public misReportConfigBean() {
    }

    /**Tree Table Binding in managed bean*/


    public void setTreeTabBind(RichTreeTable treeTabBind) {
        this.treeTabBind = treeTabBind;
    }

    public RichTreeTable getTreeTabBind() {
        return treeTabBind;
    }

    public Object resolvElDCMsg(String data) {
    FacesContext fc = FacesContext.getCurrentInstance();
    Application app = fc.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = fc.getELContext();
    ValueExpression valueExp = elFactory.createValueExpression(elContext, data,
    Object.class);
    return valueExp.getValue(elContext);
    }
   
  

    public void treeTableSelectionListener(SelectionEvent selectionEvent) {

        String adfSelectionListener = "#{bindings.FinRptGrpVO.treeModel.makeCurrent}";

        FacesContext fctx = FacesContext.getCurrentInstance();
        Application application = fctx.getApplication();
        ELContext elCtx = fctx.getELContext();
        ExpressionFactory exprFactory = application.getExpressionFactory();
        MethodExpression me = null;
        me =
 exprFactory.createMethodExpression(elCtx, adfSelectionListener, Object.class, new Class[] { SelectionEvent.class });
        me.invoke(elCtx, new Object[] { selectionEvent });

        RichTreeTable tree = (RichTreeTable)selectionEvent.getSource();
        TreeModel model = (TreeModel)tree.getValue();
        //get selected nodes
        RowKeySet rowKeySet = selectionEvent.getAddedSet();
        Iterator rksIterator = rowKeySet.iterator();
        while (rksIterator.hasNext()) {
            List key = (List)rksIterator.next();
            JUCtrlHierBinding treeBinding = null;
            treeBinding = (JUCtrlHierBinding)((CollectionModel)tree.getValue()).getWrappedData();
            JUCtrlHierNodeBinding nodeBinding = treeBinding.findNodeByKeyPath(key);
            Row rw = nodeBinding.getRow();
            System.out.println("row: " + rw.getAttribute("RptGrpId")); // You can get any attribute
            String message=null;
            if (rw.getAttribute("RptGrpId") != null) {
                if(rw.getAttribute("RptMsgId").toString()!=null)
                {
                String reportMsgId=rw.getAttribute("RptMsgId").toString();
                 message = resolvElDCMsg("#{bundle['"+reportMsgId+"']}").toString();
                }
                else
                {
                message=rw.getAttribute("RptGrpDesc").toString();
                }
               
                System.out.println("message = "+message);
                setHdrDesc(message);
                String var = rw.getAttribute("RptGrpId").toString();
                if (var.length() <= 1) {
                    var = null;
                }

                OperationBinding opExe1 = getBindings().getOperationBinding("setFinRptGrpDtlParameter");
                opExe1.getParamsMap().put("p_grp_id", var);
                opExe1.execute();

                OperationBinding opExe2 = getBindings().getOperationBinding("setFinRptGrpDtl1Parameter");
                opExe2.getParamsMap().put("p_grp_id", var);
                opExe2.execute();
                
                OperationBinding opExe3 = getBindings().getOperationBinding("setFinRptGrpFormulaDtlParameter");
                opExe3.getParamsMap().put("p_grp_id", var);
                opExe3.execute();

                AdfFacesContext.getCurrentInstance().addPartialTarget(numTableBinding);
                //AdfFacesContext.getCurrentInstance().addPartialTarget(denTableBinding);
                //AdfFacesContext.getCurrentInstance().addPartialTarget(forTableBinding);


            }

        }


    }
    /***Method to expand all tree table nodes*/
        private void expandTreeTable() {
            if (this.treeTabBind != null) {
                disclosedTreeRowKeySet = new RowKeySetImpl();
                CollectionModel model = (CollectionModel)treeTabBind.getValue();
                JUCtrlHierBinding treeBinding = (JUCtrlHierBinding)model.getWrappedData();
                JUCtrlHierNodeBinding rootNode = treeBinding.getRootNodeBinding();
                disclosedTreeRowKeySet = treeTabBind.getDisclosedRowKeys();
                if (disclosedTreeRowKeySet == null) {
                    disclosedTreeRowKeySet = new RowKeySetImpl();
                }
                List<JUCtrlHierNodeBinding> firstLevelChildren = rootNode.getChildren();
                for (JUCtrlHierNodeBinding node : firstLevelChildren) {
                    ArrayList list = new ArrayList();
                    list.add(node.getRowKey());
                    disclosedTreeRowKeySet.add(list);
                    expandTreeChildrenNode(treeTabBind, node, list);
                }
                treeTabBind.setDisclosedRowKeys(disclosedTreeRowKeySet);
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

    public void onDialogCancel(ClientEvent clientEvent) {
        System.out.println("inside dialog cancel bean action");
        rptConfigPopup.hide();
        rptConfigCoaPopup.hide();

        OperationBinding opRllbck = getBindings().getOperationBinding("Rollback");
        opRllbck.execute();
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setHdrDesc(String hdrDesc) {
        this.hdrDesc = hdrDesc;
    }

    public String getHdrDesc() {
        return hdrDesc;
    }

    public void setRptConfigPopup(RichPopup rptConfigPopup) {
        this.rptConfigPopup = rptConfigPopup;
    }

    public RichPopup getRptConfigPopup() {
        return rptConfigPopup;
    }

    public void setRptConfigCoaPopup(RichPopup rptConfigCoaPopup) {
        this.rptConfigCoaPopup = rptConfigCoaPopup;
    }

    public RichPopup getRptConfigCoaPopup() {
        return rptConfigCoaPopup;
    }

    public void rptConfigAddAction(ActionEvent actionEvent) {
        showPopup(rptConfigPopup, true);

        OperationBinding opCreate = getBindings().getOperationBinding("Createwithparameters");
        opCreate.execute();
    }

    public void rptConfigCoaAddAction(ActionEvent actionEvent) {
        showPopup(rptConfigCoaPopup, true);
        OperationBinding opCreate = getBindings().getOperationBinding("Createwithparameters1");
        opCreate.execute();

    }


    public void setSaveDisabled(boolean saveDisabled) {
        this.saveDisabled = saveDisabled;
    }

    public boolean isSaveDisabled() {
        return saveDisabled;
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

    public void setNumTableBinding(RichTable numTableBinding) {
        this.numTableBinding = numTableBinding;
    }

    public RichTable getNumTableBinding() {
        return numTableBinding;
    }


    public void rptConfigDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().toString().equals("ok")) {

            OperationBinding opCreate = getBindings().getOperationBinding("Commit");
            opCreate.execute();
        } else if (dialogEvent.getOutcome().toString().equals("cancel")) {
            OperationBinding opCreate = getBindings().getOperationBinding("Rollback");
            opCreate.execute();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(configTableBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(configCoaTableBinding);
    }


    public void rptCnfgCoaDL(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().toString().equals("ok")) {

            OperationBinding opCreate = getBindings().getOperationBinding("Commit");
            opCreate.execute();
        } else if (dialogEvent.getOutcome().toString().equals("cancel")) {
            OperationBinding opCreate = getBindings().getOperationBinding("Rollback");
            opCreate.execute();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(configTableBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(configCoaTableBinding);

    }


    public String editRptConfigAction() {
        showPopup(rptConfigPopup, true);
        return null;
    }

    public String editRptCnfgCoaAction() {
        showPopup(rptConfigCoaPopup, true);
        return null;
    }

    public void popupCancelListner(PopupCanceledEvent popupCanceledEvent) {
        OperationBinding opCreate = getBindings().getOperationBinding("Rollback");
        opCreate.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(configTableBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(configCoaTableBinding);
    }

    public void setConfigTableBinding(RichTable configTableBinding) {
        this.configTableBinding = configTableBinding;
    }

    public RichTable getConfigTableBinding() {
        return configTableBinding;
    }

    public void setConfigCoaTableBinding(RichTable configCoaTableBinding) {
        this.configCoaTableBinding = configCoaTableBinding;
    }

    public RichTable getConfigCoaTableBinding() {
        return configCoaTableBinding;
    }

    public List onSuggest(String string) {

        BindingContext bctx = BindingContext.getCurrent();
        BindingContainer bindings = bctx.getCurrentBindingsEntry();

        System.out.println("string " + string);
        //set the bind variable value that is used to filter the View Object
        //query of the suggest list. The View Object instance has a View
        //Criteria assigned

          OperationBinding setVariable = (OperationBinding)bindings.get("setSearchCriteria");
        setVariable.getParamsMap().put("value", string);
        setVariable.execute(); 

        //the data in the suggest list is queried by a tree binding.

        JUCtrlHierBinding hierBinding = (JUCtrlHierBinding)bindings.get("FinRptGrpSearch");
        //System.out.println("hierBinding " + hierBinding);
        //re-query the list based on the new bind variable values
        hierBinding.executeQuery();

        //The rangeSet, the list of queries entries, is of type
        //JUCtrlValueBndingRef.
        List<JUCtrlValueBindingRef> displayDataList = hierBinding.getRangeSet();
        System.out.println("" + hierBinding.getRangeSize());
        ArrayList<SelectItem> selectItems = new ArrayList<SelectItem>();

        for (JUCtrlValueBindingRef displayData : displayDataList) {
            Row rw = displayData.getRow();
            selectItems.add(new SelectItem((String)rw.getAttribute("RptGrpNm"), (String)rw.getAttribute("RptGrpNm")));
        }

        return selectItems;
    }

    private void resetValueInputItems(AdfFacesContext adfFacesContext, UIComponent component) {
        /** Get list of all components from search panel */
        List<UIComponent> items = component.getChildren();
        for (UIComponent item : items) {

            resetValueInputItems(adfFacesContext, item);
            /** Check if Input text , reset its value*/
            if (item instanceof RichInputText) {
                RichInputText input = (RichInputText)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
                /** Check if RichInputDate , reset its value*/
            } else if (item instanceof RichInputDate) {
                RichInputDate input = (RichInputDate)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }

                /* Check if SelectOneChoice , reset its value*/
            } else if (item instanceof RichSelectOneChoice) {
                RichSelectOneChoice input = (RichSelectOneChoice)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }

            }
        }
    }
    
    public String searchTreeAction() {
        // Add event code here...
        BindingContext bctx = BindingContext.getCurrent();
        BindingContainer bindings = bctx.getCurrentBindingsEntry();
        
        OperationBinding setCriteria = (OperationBinding)bindings.get("setFinRptGrpCriteria");
        setCriteria.getParamsMap().put("p_grp_nm", searchField.getValue());
        setCriteria.execute();        
        
        expandTreeTable();
       
       AdfFacesContext.getCurrentInstance().addPartialTarget(treeTabBind);
        return null;
    }

    public String resetTreeAction() {
        resetValueInputItems(AdfFacesContext.getCurrentInstance(), searchForm);
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchField);
        AdfFacesContext.getCurrentInstance().addPartialTarget(treeTabBind);
        return "reset";
    }

    public void setSearchForm(RichPanelFormLayout searchForm) {
        this.searchForm = searchForm;
    }

    public RichPanelFormLayout getSearchForm() {
        return searchForm;
    }

    public void setSearchField(RichInputText searchField) {
        this.searchField = searchField;
    }

    public RichInputText getSearchField() {
        return searchField;
    }

    public void delCogFrmCatAction(ActionEvent actionEvent) {
        
        OperationBinding opDel = getBindings().getOperationBinding("delCurrRowForVO") ;
        opDel.getParamsMap().put("p_vo_nm", "FinRptConfigVO");
        opDel.execute();
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(configTableBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(configCoaTableBinding);
    }

    public void delCoaFrmCatCogAction(ActionEvent actionEvent) {
        
        OperationBinding opDel = getBindings().getOperationBinding("delCurrRowForVO") ;
        opDel.getParamsMap().put("p_vo_nm", "FinRptConfigCoaVO");
        opDel.execute();
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(configTableBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(configCoaTableBinding);
    }
}
