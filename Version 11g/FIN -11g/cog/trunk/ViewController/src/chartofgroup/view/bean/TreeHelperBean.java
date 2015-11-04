package chartofgroup.view.bean;


import chartofgroup.model.service.ChartOfGroupAMImpl;
import chartofgroup.model.view.FinCogNewVORowImpl;

import chartofgroup.model.view.views.FinCogForTreeVOImpl;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCControlBinding;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTree;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.client.Configuration;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectCategoryManagerImpl;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;
import oracle.jbo.uicli.binding.JUIteratorBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class TreeHelperBean {

    private String CogId = null;
    private String GroupType;
    private Integer serverlocationId;
    private String mode = "";
    private RichTree treetablebind;
    private RichPopup deletepop;
    private RichTreeTable cogTree;
    private RichPopup addPopup;
    private RichSelectBooleanCheckbox cogBalanceSheetPopup;
    private RichSelectBooleanCheckbox cogPnLPopup;
    private RichSelectBooleanCheckbox cogBalenceSheet;
    private RichSelectBooleanCheckbox cogProfitLoss;
    private RichSelectBooleanCheckbox cogTransfer;
    private RichSelectBooleanCheckbox cogTransferBalancePopup;
    private RichSelectBooleanCheckbox selectCheckBox;
    private HashSet<String> cogIdVal = new HashSet<String>();
    private RichPopup msgPop;
    private RichPanelCollection panelCollectionTree;
    private RichTreeTable cogTreeBinding;
    private RichOutputText treeCogName;
    private RichSelectOneChoice moveParentId;
    private RichPopup transferPopForBS;
    private RichPopup transferPopupForPnL;
    private String changedBy = "";
    private RichSelectOneChoice grpTyp;
    private RichSelectOneChoice popupcoggrptypBind;
    private Integer onloadVal;
    private RichInputText cogNmFormBind;
    private RichInputText cogLegCodeFormBind;
    private RichInputText cogAliasFormBind;
    private String editFlag = "false";
    private RichSelectOneChoice parentbinding;
    private RichSelectOneChoice createUnderBind;
    String CogName="";
    private RichInputText cognmpopupBind;
    private RichSelectBooleanCheckbox inheritBind;

    public TreeHelperBean() {
    }

    public void onIndexSelection(ActionEvent actionEvent) {
        RichCommandLink linkPressed = (RichCommandLink)actionEvent.getSource();
        Key jboRowKey = (Key)linkPressed.getAttributes().get("jboRowKey");

        ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvElDC("ChartOfGroupAMDataControl");
        ViewObjectImpl v = am.getFinCog1();
        v.setWhereClause("Cog_Nm like '" + linkPressed.getText() + "%'");

        v.executeQuery();


        AdfFacesContext.getCurrentInstance().addPartialTarget(cogTree);
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

    public void DeleteDialogListener(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("yes")) {
            BindingContainer bindings = getBindings();
            DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("FinCog1Iterator1");

            Key parentKey = getAm().getFinCog1().first().getKey();

            // Key parentKey = parentIter.getCurrentRow().getKey();

            RichTreeTable tree = this.getCogTree();
            //get the list of selected row keys
            RowKeySet rks = tree.getSelectedRowKeys();
            //access the iterator to loop over selected nodes
            Iterator rksIterator = rks.iterator();
            //The CollectionModel represents the tree model and is
            //accessed from the tree "value" property
            CollectionModel model = (CollectionModel)tree.getValue();
            //The CollectionModel is a wrapper for the ADF tree binding
            //class, which is JUCtrlHierBinding
            JUCtrlHierBinding treeBinding = (JUCtrlHierBinding)model.getWrappedData();

            //loop over the selected nodes and delete the rows they
            //represent
            if (rksIterator.hasNext() == false) {
                // System.out.println("value of iterator is ====false");
                ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvElDC("ChartOfGroupAMDataControl");
                ViewObjectImpl vo = am.getFinCogNewVO();
                ViewObjectImpl v = am.getFinCog1();
                vo.getCurrentRow().remove();
                vo.executeQuery();
                v.executeQuery();
            }
            while (rksIterator.hasNext()) {
                //System.out.println("value of iterator is====true");
                List nodeKey = (List)rksIterator.next();
                //find the ADF node binding using the node key
                JUCtrlHierNodeBinding node = treeBinding.findNodeByKeyPath(nodeKey);
                //delete the row.
                Row rw = node.getRow();
                rw.remove();

            }


            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();

            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
            createOpBAddress.execute();
            BindingContainer binding1 = getBindings();
            OperationBinding createOpBAddress1 = binding1.getOperationBinding("Execute1");
            createOpBAddress1.execute();

            if (parentIter.getRowSetIterator().getRow(parentKey) !=
                null) { //check condition else gives exception in add mode.
                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

                parentIter.refresh(0);
            }
            getAm().getFinCog2().setWhereClause(null);
            getAm().getFinCog2().executeQuery();
            OperationBinding createOpBAddress2 = binding.getOperationBinding("Rollback");
            createOpBAddress2.execute();
            OperationBinding createOpBAddress3 = binding.getOperationBinding("Execute");
            createOpBAddress3.execute();
            OperationBinding createOpBAddress4 = binding1.getOperationBinding("Execute1");
            createOpBAddress4.execute();


            treeBinding.setCurrentRowAtIndex(1);

            this.setMode("");
            AdfFacesContext.getCurrentInstance().addPartialTarget(cogTree);

        } else if (dialogEvent.getOutcome().name().equals("no")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();

            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
            createOpBAddress.execute();
            BindingContainer binding1 = getBindings();
            OperationBinding createOpBAddress1 = binding1.getOperationBinding("Execute1");
            createOpBAddress1.execute();
            this.setMode("");
            AdfFacesContext.getCurrentInstance().addPartialTarget(cogTree);
        }
    }


    public void SaveDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            changedBy = "POPUP";
            String cogTran = cogTransferBalancePopup.getValue().toString();
            String a = cogBalanceSheetPopup.getValue().toString();
            String pnl = cogPnLPopup.getValue().toString();
            //System.out.println();
            /* if(chekactivemode=="N")
                  {
                    if((Boolean)cogBalanceSheetPopup.getValue()==false && (Boolean)cogPnLPopup.getValue()==false) {
                 // System.out.println("832489346379681297901274209670912");
                      activeMode="T"  ;
                  }
                  else if((Boolean)cogBalanceSheetPopup.getValue()==true && (Boolean)cogPnLPopup.getValue()==true) {
                      activeMode="T"  ;
                  }
                  else
                  {
                  activeMode="F";
                  }
                  } */

            if (a.equalsIgnoreCase("true") && pnl.equalsIgnoreCase("true")) {

                String msg1 = resolvEl1("#{bundle['MSG.115']}");
                FacesMessage message = new FacesMessage(msg1);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                //  throw new ValidatorException(message);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else if (a.equalsIgnoreCase("false") && pnl.equalsIgnoreCase("false")) {

                String msg2 = resolvEl1("#{bundle['MSG.115']}");
                FacesMessage message = new FacesMessage(msg2);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                //  throw new ValidatorException(message);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else if ((popupcoggrptypBind.getValue().toString().equalsIgnoreCase("A") ||
                        popupcoggrptypBind.getValue().toString().equalsIgnoreCase("L")) &&
                       (Boolean)cogPnLPopup.getValue() == true) {
                System.out.println("condition get trueeee");
                FacesMessage message = new FacesMessage("Select Balance sheet in case of Assets and liability");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else if ((popupcoggrptypBind.getValue().toString().equalsIgnoreCase("E") ||
                        popupcoggrptypBind.getValue().toString().equalsIgnoreCase("I")) &&
                       (Boolean)cogBalanceSheetPopup.getValue() == true) {
                FacesMessage message = new FacesMessage("Select Profilt and Loss in case of Expense and Income");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                if (a.equalsIgnoreCase("true") && cogTran.equalsIgnoreCase("false")) {
                    showPopup(transferPopForBS, true);
                } else if (pnl.equalsIgnoreCase("true") && cogTran.equalsIgnoreCase("true")) {
                    showPopup(transferPopupForPnL, true);
                } else {

                    BindingContainer bindings = getBindings();
                    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                    operationBinding.execute();


                    OperationBinding createOpBAddress = bindings.getOperationBinding("Execute");
                    createOpBAddress.execute();


                    OperationBinding createOpBAddress1 = bindings.getOperationBinding("Execute1");
                    createOpBAddress1.execute();

                    OperationBinding createOpBAddressExe = bindings.getOperationBinding("Execute2");
                    createOpBAddressExe.execute();
                    if (operationBinding.getErrors().isEmpty()) {
                        requeryForGrpLOV();
                        showPopup(msgPop, true);

                        this.setMode("");
                        panelCollectionTree.setVisible(false);
                    }
                    activeMode = "F";
                }
            }


        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(cogTree);
    }

    public String resolvEl1(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }


    private static int VARCHAR = Types.VARCHAR;

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        Object ret = null;
        CallableStatement st = null;
        try {
            ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvElDC("ChartOfGroupAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();
            ret = st.getObject(1);
        } catch (SQLException e) {
            if (e.getMessage().length() < 11) {

                String msg = e.getMessage();
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                int end = e.getMessage().indexOf("\n");
                String msg = e.getMessage().substring(11, end);
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                //  throw new ValidatorException(message);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    if (e.getMessage().length() < 11) {

                        String msg = e.getMessage();
                        FacesMessage message = new FacesMessage(msg);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else {
                        int end = e.getMessage().indexOf("\n");
                        String msg = e.getMessage().substring(11, end);
                        FacesMessage message = new FacesMessage(msg);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        //  throw new ValidatorException(message);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                }
            }
        }
        return ret;
    }
    private String failureMessage = "";
    private String successMessage = "";

    protected String callStoredProc_move(String stmt, Object[] bindVars) {
        failureMessage = "";
        successMessage = "";
        CallableStatement st = null;
        String retval = "";
        try {
            /** 1. Create a JDBC CallabledStatement */
            ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvElDC("ChartOfGroupAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin " + stmt + ";end;", 0);
            /** 2. Register the first bind variable for the return value. and last 2 variable for output variable of function. */


            st.setObject(1, bindVars[0]);
            st.setObject(2, bindVars[1]);
            st.setObject(3, bindVars[2]);
            st.setObject(4, bindVars[3]);
            st.setObject(5, bindVars[4]);
            st.setObject(6, bindVars[5]);

            st.registerOutParameter(7, VARCHAR);

            st.registerOutParameter(8, VARCHAR);

            st.registerOutParameter(9, VARCHAR);

            /** 5. Set the value of user-supplied bind vars in the stmt */

            st.executeUpdate();

            try {

                retval = st.getObject(9).toString();


                if (st.getObject(7) == null) {
                    failureMessage = "";
                } else {
                    failureMessage = st.getObject(7).toString();
                    failureMessage = failureMessage.substring(0, failureMessage.length() - 1);
                    // System.out.println(failureMessage);
                }


                if (st.getObject(8) == null) {
                    successMessage = "";
                } else {
                    successMessage = st.getObject(8).toString();
                }


            } catch (NullPointerException e) {


                String msg = e.getMessage();
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);


            }

        } catch (SQLException e) {
            // System.out.println(e.getMessage());
            if (e.getMessage().length() < 11) {

                String msg = e.getMessage();
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                int end = e.getMessage().indexOf("\n");
                String msg = e.getMessage().substring(11, end);
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                //  throw new ValidatorException(message);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        } finally {
            if (st != null) {
                try {
                    /** 7. Close the statement */
                    st.close();
                } catch (SQLException e) {
                    if (e.getMessage().length() < 11) {
                        int end = e.getMessage().indexOf("\n");
                        String msg = e.getMessage();
                        FacesMessage message = new FacesMessage(msg);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else {
                        int end = e.getMessage().indexOf("\n");
                        String msg = e.getMessage().substring(11, end);
                        FacesMessage message = new FacesMessage(msg);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        //  throw new ValidatorException(message);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }

                }
            }
        }

        return retval;
    }

    protected String callStoredProc_delete(String stmt, Object[] bindVars) {
        failureMessage = "";
        successMessage = "";
        CallableStatement st = null;
        String retval = "";
        try {
            /** 1. Create a JDBC CallabledStatement */
            ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvElDC("ChartOfGroupAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin " + stmt + ";end;", 0);
            /** 2. Register the first bind variable for the return value. and last 2 variable for output variable of function. */


            st.setObject(1, bindVars[0]);
            st.setObject(2, bindVars[1]);
            st.setObject(3, bindVars[2]);
            st.setObject(4, bindVars[3]);

            st.registerOutParameter(5, VARCHAR);

            st.registerOutParameter(6, VARCHAR);

            st.registerOutParameter(7, VARCHAR);

            /** 5. Set the value of user-supplied bind vars in the stmt */

            st.executeUpdate();
            //System.out.println(4);
            try {

                retval = st.getObject(7).toString();


                if (st.getObject(5) == null) {
                    failureMessage = "";
                } else {
                    failureMessage = st.getObject(5).toString();
                    failureMessage = failureMessage.substring(0, failureMessage.length() - 1);
                    // System.out.println(failureMessage);
                }


                if (st.getObject(6) == null) {
                    successMessage = "";
                } else {
                    successMessage = st.getObject(6).toString();
                }


            } catch (NullPointerException e) {
                //System.out.println(e);


                String msg = e.getMessage();
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);

            }

        } catch (SQLException e) {
            if (e.getMessage().length() < 11) {

                String msg = e.getMessage();
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                int end = e.getMessage().indexOf("\n");
                String msg = e.getMessage().substring(11, end);
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        } finally {
            if (st != null) {
                try {
                    /** 7. Close the statement */
                    st.close();
                } catch (SQLException e) {
                    if (e.getMessage().length() < 11) {
                        int end = e.getMessage().indexOf("\n");
                        String msg = e.getMessage();
                        FacesMessage message = new FacesMessage(msg);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else {
                        int end = e.getMessage().indexOf("\n");
                        String msg = e.getMessage().substring(11, end);
                        FacesMessage message = new FacesMessage(msg);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                }
            }
        }

        return retval;
    }


    private ChartOfGroupAMImpl getAm() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data.ChartOfGroupAMDataControl.dataProvider}", Object.class);
        return (ChartOfGroupAMImpl)valueExp.getValue(elContext);
    }

    public void onTreeNodeDelete(ActionEvent actionEvent) {
        ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvElDC("ChartOfGroupAMDataControl");
        activeMode = "F";
        ViewObject v1 = getAm().getFinCogNewVO();
        Row curRow = v1.getCurrentRow();
        String CogId = curRow.getAttribute("CogId").toString();
        String SlocId = curRow.getAttribute("CogSlocId").toString();
        String CldId = curRow.getAttribute("CogCldId").toString();
        String orgid = curRow.getAttribute("CogOrgId").toString();

        String returnVal =
            callStoredFunction(VARCHAR, "FIN.CKECK_COG_DELETE(?,?,?,?)", new Object[] { CogId, SlocId, CldId,
                                                                                        orgid }).toString();

        System.out.println("value of returnval after pressing delte button is=:" + returnVal);
        if (returnVal.equals("Y")) {
            String num = null;
            //access the tree from the JSF component reference created
            //using the af:tree "binding" property. The "binding" property
            //creates a pair of set/get methods to access the RichTree instance
            RichTreeTable tree = this.getCogTree();

            //get the list of selected row keys
            RowKeySet rks = tree.getSelectedRowKeys();

            //access the iterator to loop over selected nodes
            Iterator rksIterator = rks.iterator();
            //The CollectionModel represents the tree model and is
            //accessed from the tree "value" property
            CollectionModel model = (CollectionModel)tree.getValue();
            //The CollectionModel is a wrapper for the ADF tree binding
            //class, which is JUCtrlHierBinding
            JUCtrlHierBinding treeBinding = (JUCtrlHierBinding)model.getWrappedData();


            //loop over the selected nodes and delete the rows they
            //represent
            //ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvElDC("ChartOfGroupAMDataControl");
            //ViewObjectImpl v1 = am.getFinCogNewVO();
            System.out.println("value of iter.hasnext" + rksIterator.hasNext());
            if (rksIterator.hasNext() == false) {
                //ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvElDC("ChartOfGroupAMDataControl");
                checknode();
            }


            while (rksIterator.hasNext()) {
                List nodeKey = (List)rksIterator.next();
                //find the ADF node binding using the node key
                JUCtrlHierNodeBinding node = treeBinding.findNodeByKeyPath(nodeKey);
                //delete the row.
                if (node.getRow() != null) {
                    System.out.println("value of node in rows is " + node.getRow());
                    Row rw = node.getRow();
                    String cogIdVal = (String)rw.getAttribute("CogId");


                    //ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvElDC("ChartOfGroupAMDataControl");
                    ViewObjectImpl v = am.getFinCog1();

                    // Row currentRow = v.getCurrentRow();
                    //String cogIdVal= (String)currentRow.getAttribute("CogId");

                    PreparedStatement st =
                        am.getDBTransaction().createPreparedStatement("select count(*) as val from fin$cog where COG_ID_PARENT=" +
                                                                      cogIdVal, 0);
                    try {
                        ResultSet rs = st.executeQuery();
                        while (rs.next()) {
                            num = rs.getString("val");

                        }

                    } catch (Exception e) {
                        String msg2 = e.getMessage();
                        FacesMessage message = new FacesMessage(msg2);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        //  throw new ValidatorException(message);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }

                    v.executeQuery();

                    int numb = Integer.parseInt(num);

                    if (numb == 0) {
                        //  rw.remove();
                        showPopup(deletepop, true);
                        panelCollectionTree.setVisible(false);
                    } else {
                        String msg2 = resolvEl1("#{bundle['MSG.211']}");
                        FacesMessage message = new FacesMessage(msg2);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        //  throw new ValidatorException(message);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);

                    }
                } else {
                    checknode();
                }
            }

            //only refresh the tree if tree nodes have been selected
            if (rks.size() > 0) {
                AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
                adfFacesContext.addPartialTarget(tree);
            }
        } else {
            String msg2 = resolvEl1("#{bundle['MSG.212']}");
            FacesMessage message = new FacesMessage(msg2);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //  throw new ValidatorException(message);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        this.setMode("");
        //Configuration.releaseRootApplicationModule(getAm(), true);
    }


    public String onCreatecog(String CogId, Integer serverlocationId, String GrpType) {


        return null;
    }


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    public void setCogId(String CogId) {
        this.CogId = CogId;
    }

    public String getCogId() {
        return CogId;
    }

    public void Cancel(ActionEvent actionEvent) {
        activeMode = "F";
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        OperationBinding createOpBAddress = bindings.getOperationBinding("Rollback");
        createOpBAddress.execute();


        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress1 = binding.getOperationBinding("Execute");
        createOpBAddress1.execute();


        BindingContainer bindingexe = getBindings();
        OperationBinding createOpBAddressexe = bindingexe.getOperationBinding("Execute1");
        createOpBAddressexe.execute();

        OperationBinding createOpBAddressExe = bindings.getOperationBinding("Execute2");
        createOpBAddressExe.execute();
        panelCollectionTree.setVisible(false);
        requeryForGrpLOV();
        this.setMode("");


    }

    private void requeryForGrpLOV() {
        ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvElDC("ChartOfGroupAMDataControl");
        ViewObjectImpl cogVo = am.getFinCogNewVO();
        if (cogVo.getCurrentRow() != null) {
            FinCogNewVORowImpl cogCurrrw = (FinCogNewVORowImpl)cogVo.getCurrentRow();

            cogCurrrw.getLOVGroupTyp1().executeQuery();
        }
    }

    public void Add(ActionEvent actionEvent) {
        CogName="";
        activeMode = "T";
        chekactivemode = "N";
        Integer SlocId = null;
        String CogparentId = "";
        String GroupType = "";
        String CogActv = "";
        String CogBsItem = "";
        String CogCfItem = "";
        String CogGrpBalType = "";
        String CogInheritProp = "";
        String CogPnlItem = "";

        String CogSubldgr = "";
        String CogTrfBal = "";
        String inheritVal = "N";

        RichTreeTable treeTable = this.getCogTree();

        RowKeySet rks = treeTable.getSelectedRowKeys();
        Iterator rksIterator = rks.iterator();

        if (rksIterator.hasNext()) {
            List key = (List)rksIterator.next();
            JUCtrlHierBinding treeBinding = null;
            treeBinding = (JUCtrlHierBinding)((CollectionModel)treeTable.getValue()).getWrappedData();
            JUCtrlHierNodeBinding nodeBinding = nodeBinding = treeBinding.findNodeByKeyPath(key);
            DCIteratorBinding _treeIteratorBinding = null;
            _treeIteratorBinding = treeBinding.getDCIteratorBinding();

            Key rowKey = nodeBinding.getRowKey();
            JUIteratorBinding iterator = nodeBinding.getIteratorBinding();
            iterator.setCurrentRowWithKey(rowKey.toStringFormat(true));


            CogparentId = ((String)iterator.getCurrentRow().getAttribute("CogId"));
            //Check for valid level
            if (CogparentId.length() >= 19) {
                String msg2 = resolvEl1("#{bundle['MSG.213']}");
                FacesMessage message = new FacesMessage(msg2);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                //  throw new ValidatorException(message);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                //System.out.println("Level is invalid!");
            } else {

                //System.out.println("CogparentId :"+CogparentId);
                SlocId = (Integer)iterator.getCurrentRow().getAttribute("CogSlocId");
                String GT = (String)iterator.getCurrentRow().getAttribute("CogGrpType");

                if (GT.equals("L")) {
                    GroupType = "2";
                } else if (GT.equals("A")) {
                    GroupType = "1";
                } else if (GT.equals("E")) {
                    GroupType = "4";
                } else if (GT.equals("I")) {
                    GroupType = "3";
                } else {
                    GroupType = GT;
                }
                //System.out.println("Group type selected is : "+GroupType);


                //GroupType = ((String)iterator.getCurrentRow().getAttribute("CogGrpType"));
                // System.out.println(GroupType + "-----GroupType");
                if (iterator.getCurrentRow().getAttribute("CogInheritProp") != null) {
                    inheritVal = iterator.getCurrentRow().getAttribute("CogInheritProp").toString();
                }
                // System.out.println(inheritVal + "------inheritVal");

                if (inheritVal.equals("Y")) {

                    if (iterator.getCurrentRow().getAttribute("CogActv") != null) {
                        CogActv = (iterator.getCurrentRow().getAttribute("CogActv").toString());
                    }

                    if (iterator.getCurrentRow().getAttribute("CogBsItem") != null) {
                        CogBsItem = (iterator.getCurrentRow().getAttribute("CogBsItem").toString());
                    }

                    if (iterator.getCurrentRow().getAttribute("CogCfItem") != null) {
                        CogCfItem = (iterator.getCurrentRow().getAttribute("CogCfItem").toString());
                    }

                    if (iterator.getCurrentRow().getAttribute("CogGrpBalType") != null) {
                        CogGrpBalType = (iterator.getCurrentRow().getAttribute("CogGrpBalType").toString());
                    }


                    if (iterator.getCurrentRow().getAttribute("CogInheritProp") != null) {
                        CogInheritProp = (iterator.getCurrentRow().getAttribute("CogInheritProp").toString());
                    }

                    if (iterator.getCurrentRow().getAttribute("CogPnlItem") != null) {
                        CogPnlItem = (iterator.getCurrentRow().getAttribute("CogPnlItem").toString());
                    }


                    if (iterator.getCurrentRow().getAttribute("CogSubldgr") != null) {
                        CogSubldgr = (iterator.getCurrentRow().getAttribute("CogSubldgr").toString());
                    }

                    if (iterator.getCurrentRow().getAttribute("CogTrfBal") != null) {
                        CogTrfBal = (iterator.getCurrentRow().getAttribute("CogTrfBal").toString());
                    }
                    //                if (iterator.getCurrentRow().getAttribute("CogTrfBal") != null) {
                    //                    CogTrfBal = (iterator.getCurrentRow().getAttribute("CogTrfBal").toString());
                    //                }
                    //
                }


            }


        }

        if (CogparentId.length() >= 19) {

            // System.out.println("Level is invalid!");
        } else {

            if (inheritVal.equals("Y")) {
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Createwithparameters1");
                operationBinding.execute();


                BindingContainer binding = getBindings();
                DCControlBinding cb = (DCControlBinding)binding.get("FinCog11");
                ViewObject vo = cb.getViewObject();
                Row currentRow = vo.getCurrentRow();

                currentRow.setAttribute("CogIdParent", CogparentId);
                currentRow.setAttribute("CogSlocId", SlocId);
                currentRow.setAttribute("CogGrpType", GroupType);
                currentRow.setAttribute("CogActv", CogActv);
                currentRow.setAttribute("CogBsItem", CogBsItem);
                currentRow.setAttribute("CogCfItem", CogCfItem);
                currentRow.setAttribute("CogGrpBalType", CogGrpBalType);
                currentRow.setAttribute("CogInheritProp", CogInheritProp);
                currentRow.setAttribute("CogPnlItem", CogPnlItem);

                currentRow.setAttribute("CogSubldgr", CogSubldgr);
                currentRow.setAttribute("CogTrfBal", CogTrfBal);
                panelCollectionTree.setVisible(false);
                showPopup(addPopup, true);
                //            try {
                //                this.popupcoggrptypBind.setValue(GroupType);
                //            } catch (Exception e) {
                //               System.out.println("Error in setting group type.");
                //               // e.printStackTrace();
                //            }
            }


            else {
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Createwithparameters1");
                operationBinding.execute();


                BindingContainer binding = getBindings();
                DCControlBinding cb = (DCControlBinding)binding.get("FinCog11");
                ViewObject vo = cb.getViewObject();
                Row currentRow = vo.getCurrentRow();

                currentRow.setAttribute("CogIdParent", CogparentId);
                currentRow.setAttribute("CogSlocId", SlocId);
                currentRow.setAttribute("CogGrpType", GroupType);
                panelCollectionTree.setVisible(false);
                showPopup(addPopup, true);
            }


        }


    }


    public void Delete(ActionEvent actionEvent) {
        showPopup(deletepop, true);
        panelCollectionTree.setVisible(false);
    }
    public String chekactivemode = "N";

    public void Edit(ActionEvent actionEvent) {
        ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvElDC("ChartOfGroupAMDataControl");
        chekactivemode = "Y";
        activeMode = "F";
        editFlag = "true";
         CogName="A";
         Integer child=null;
         String cogid=null;
        ViewObject v1 = getAm().getFinCogNewVO();
        ViewObject v2=getAm().getFinCog1();
        Row curRow = v1.getCurrentRow();
        String CogId = curRow.getAttribute("CogId").toString();
        String SlocId = curRow.getAttribute("CogSlocId").toString();
        String CldId = curRow.getAttribute("CogCldId").toString();
        String orgid = curRow.getAttribute("CogOrgId").toString();

        String returnVal =
            callStoredFunction(VARCHAR, "FIN.CKECK_COG_DELETE(?,?,?,?)", new Object[] { CogId, SlocId, CldId,
                                                                                        orgid }).toString();


        if (returnVal.equals("Y")) {             
           
            requeryForGrpLOV();
            this.setMode("E");
            panelCollectionTree.setVisible(false);
            if( v1.getCurrentRow().getAttribute("CogId")!=null)
            cogid= v1.getCurrentRow().getAttribute("CogId").toString() ; 
             ViewObjectImpl vo = am.getCountChildVO1();
            vo.setNamedWhereClauseParam("BindCogId", cogid);
            vo.setNamedWhereClauseParam("BindHoOrgId",  resolvEl1("#{pageFlowScope.GLBL_HO_ORG_ID}").toString());
            vo.setNamedWhereClauseParam("BindSlocId", Integer.parseInt(resolvEl1("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString()));
            vo.setNamedWhereClauseParam("BindCldId", resolvEl1("#{pageFlowScope.GLBL_APP_CLD_ID}").toString());
            vo.executeQuery();
             Row rw[]=vo.getAllRowsInRange();
            System.out.println("lengthhhh iss="+rw.length);
            if( rw.length>0) {
              if(rw[0].getAttribute("Value")!=null)
            child=Integer.parseInt(rw[0].getAttribute("Value").toString());
              } 
            if(child==0) {
               System.out.println("no child found"); 
            }
            else {
                System.out.println("child exists");
                this.setMode("D"); 
            }
        }
        else {                       
            String msg2 = resolvEl1("#{bundle['MSG.214']}");
            FacesMessage message = new FacesMessage(msg2);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //  throw new ValidatorException(message);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }

        }   
       


    public Object resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }


    public void setTreetablebind(RichTree treetablebind) {
        this.treetablebind = treetablebind;
    }

    public RichTree getTreetablebind() {
        return treetablebind;
    }

    public void setDeletepop(RichPopup deletepop) {
        this.deletepop = deletepop;
    }

    public RichPopup getDeletepop() {
        return deletepop;
    }


    public void setCogTree(RichTreeTable cogTree) {
        this.cogTree = cogTree;
    }

    public RichTreeTable getCogTree() {
        return cogTree;
    }

    public void setAddPopup(RichPopup addPopup) {
        this.addPopup = addPopup;
    }

    public RichPopup getAddPopup() {
        return addPopup;
    }

    public void setServerlocationId(Integer serverlocationId) {
        this.serverlocationId = serverlocationId;
    }

    public Integer getServerlocationId() {
        return serverlocationId;
    }

    public void setGroupType(String GroupType) {
        this.GroupType = GroupType;
    }

    public String getGroupType() {
        return GroupType;
    }


    private String searchString = "";
    private String searchType = "START";
    private String searchAttributes = "CogNm";
    private RichTree tree1 = null;
    ADFLogger logger = ADFLogger.createADFLogger(this.getClass());

    public void Search() {
        JUCtrlHierBinding treeBinding = null;

        //get handle to tree if it does not exist. If tree component cannot be
        //found in view, exit this function
        if (cogTree == null) {
            this.findTreeInView();
            if (cogTree == null) {
                //tree not found
                log("The tree component could not be found in the view. Please check for naming containers. Search function cancelled");
                return;
            }
        }
        //Get the JUCtrlHierbinding reference from the PageDef
        CollectionModel model = (CollectionModel)cogTree.getValue();
        treeBinding = (JUCtrlHierBinding)model.getWrappedData();

        //Read the attributes to search in from the SelectManyChoice component
        //  String searchAttributeArray[] =             (String[])searchAttributes.toArray(new String[searchAttributes.size()]);

        //Define a node to search in. In this example, the root node is used
        JUCtrlHierNodeBinding root = treeBinding.getRootNodeBinding();

        //However, if the user used the "Show as Top" context menu option to
        //shorten the tree display, then we only search starting from this top
        //mode

        List topNode = (List)cogTree.getFocusRowKey();
        if (topNode != null) {
            //make top node the root node for the search
            root = treeBinding.findNodeByKeyPath(topNode);
        }

        //Select the tree items that match the search criteria and expand the
        //tree to display them
        RowKeySet resultRowKeySet = searchTreeNode(root, searchAttributes, searchType, searchString.toUpperCase());

        RowKeySet disclosedRowKeySet = buildDiscloseRowKeySet(treeBinding, resultRowKeySet);
        cogTree.setSelectedRowKeys(resultRowKeySet);
        cogTree.setDisclosedRowKeys(disclosedRowKeySet);

        AdfFacesContext.getCurrentInstance().addPartialTarget(cogTree);
    }

    public void onSearch(ActionEvent actionEvent) {
        searchCog(searchString);
        Search();
    }

    private void searchCog(String CogNm) {

        ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvEl("ChartOfGroupAMDataControl");
        ViewObject vo = am.findViewObject("FinCogNewVO");
        FinCogForTreeVOImpl vSearch = am.getFinCogForTree();
        Integer SlocId = Integer.parseInt(resolvElDCBindVar("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String CldId = (resolvElDCBindVar("#{pageFlowScope.GLBL_APP_CLD_ID}"));
        String OrgId = (resolvElDCBindVar("#{pageFlowScope.GLBL_HO_ORG_ID}"));


        vo.setWhereClause("upper(COG_NM) like upper('" + CogNm + "%') and COG_CLD_ID='" + CldId +
                          "' and COG_SLOC_ID=" + SlocId + " and COG_ORG_ID='" + OrgId + "'");
        vo.executeQuery();

        if (vo.getRowCount() != 0) {

            Row rw = vo.getCurrentRow();
            if (rw != null) {

                vSearch.setcogParentIdBindVar(rw.getAttribute("CogId").toString());
                vSearch.setSlocIdBindVar(SlocId);
                vSearch.setCldIdBindVar(CldId);
                vSearch.setOrgIdBindVar(OrgId);
                vSearch.executeQuery();

                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("setcogParentIdBindVar");
                operationBinding.execute();
                if (searchString != "") {
                    panelCollectionTree.setVisible(true);
                }
                cogIdVal.clear();
                AdfFacesContext.getCurrentInstance().addPartialTarget(cogTreeBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(panelCollectionTree);
            }
        } else {


            vo.setWhereClause(null);
            vo.executeQuery();


        }
    }

    /**
     * Method that parses an ADF bound ADF Faces tree component to find search string matches
     * in one of the specified attribute names. Attribute names are ignored if they don't exist
     * in the search node. The method performs a recursiv search and returns a RowKeySet with the
     * row keys of all nodes that contain the search string
     * @param  node The JUCtrlHierNodeBinding instance to search
     * @param  searchAttributes An array of attribute names to search in
     * @param  searchType defines where the search is started within the text. Valid values are
     *         START, CONTAIN, END. If NULL the "CONTAIN" is set as the default
     * @param  searchString  The search condition
     * @return RowKeySet row keys
     */
    private RowKeySet searchTreeNode(JUCtrlHierNodeBinding node, String searchAttributes, String searchType,
                                     String searchString) {
        RowKeySetImpl rowKeys = new RowKeySetImpl();
        //set default search
        String _searchType = searchType == null ? "START" : searchType.length() > 0 ? searchType : "START";

        //Sanity checks
        if (node == null) {
            log("Node passed as NULL");
            return rowKeys;
        }
        if (searchAttributes == null) {
            log(node.getName() + ": search attribute is NULL or has a ZERO length");
            return rowKeys;
        }
        if (searchString == null || searchString.length() < 1) {
            log(node.getName() + ": Search string cannot be NULL or EMPTY");
            return rowKeys;
        }

        Row nodeRow = node.getRow();
        if (nodeRow != null) {

            String compareString = "";
            try {
                Object attribute = nodeRow.getAttribute(searchAttributes);
                if (attribute instanceof String) {
                    compareString = (String)attribute;
                } else {
                    //try the toString method as a simple fallback
                    compareString = attribute.toString();
                }
            } catch (oracle.jbo.JboException attributeNotFound) {
                //node does not have attribute. Exclude from search
            }
            //compare strings case insesitive.
            if (_searchType.equalsIgnoreCase("CONTAIN") &&
                compareString.toUpperCase().indexOf(searchString.toUpperCase()) > -1) {
                //get row key
                rowKeys.add(node.getKeyPath());
            } else if (_searchType.equalsIgnoreCase("START") &&
                       compareString.toUpperCase().startsWith(searchString.toUpperCase())) {
                //get row key
                rowKeys.add(node.getKeyPath());
            } else if (_searchType.equalsIgnoreCase("END") &&
                       compareString.toUpperCase().endsWith(searchString.toUpperCase())) {
                //get row key
                rowKeys.add(node.getKeyPath());
            }

        }

        List<JUCtrlHierNodeBinding> children = node.getChildren();

        if (children != null) {
            for (JUCtrlHierNodeBinding _node : children) {
                //Each child search returns a row key set that must be added to the
                //row key set returned by the overall search
                RowKeySet rks = searchTreeNode(_node, searchAttributes, this.searchType, searchString);
                if (rks != null && rks.size() > 0) {
                    rowKeys.addAll(rks);
                }
            }
        }
        return rowKeys;
    }

    /**
     * Helper method that returns a list of parent node for the RowKeySet passed
     * as the keys argument. The RowKeySet can be used to disclose the folders in
     * which the keys reside. Node that to disclose a full branch, all RowKeySet
     * that are in the path must be defined
     *
     * @param  treeBinding ADF tree binding instance read from the PageDef file
     * @param  keys  RowKeySet containing List entries of oracle.jbo.Key
     * @return RowKeySet of parent keys to disclose
     */
    private RowKeySet buildDiscloseRowKeySet(JUCtrlHierBinding treeBinding, RowKeySet keys) {
        RowKeySetImpl discloseRowKeySet = new RowKeySetImpl();
        Iterator iter = keys.iterator();
        while (iter.hasNext()) {
            List keyPath = (List)iter.next();
            JUCtrlHierNodeBinding node = treeBinding.findNodeByKeyPath(keyPath);
            if (node != null && node.getParent() != null && !node.getParent().getKeyPath().isEmpty()) {
                //store the parent path
                discloseRowKeySet.add(node.getParent().getKeyPath());

                //call method recursively until no parents are found
                RowKeySetImpl parentKeySet = new RowKeySetImpl();
                parentKeySet.add(node.getParent().getKeyPath());
                RowKeySet rks = buildDiscloseRowKeySet(treeBinding, parentKeySet);
                discloseRowKeySet.addAll(rks);
            }
        }

        return discloseRowKeySet;
    }

    private void log(String message) {
        logger.log(ADFLogger.WARNING, message);
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchAttributes(String searchAttributes) {
        this.searchAttributes = searchAttributes;
    }

    public String getSearchAttributes() {
        return searchAttributes;
    }

    //To learn what the code below is doing, please see Sample #58
    //ADF Code Corner
    //http://www.oracle.com/technetwork/developer-tools/adf/learnmore/index-101235.html

    private void findTreeInView() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        UIViewRoot root = fctx.getViewRoot();
        //hard coding tree component Id with its surrounding naming container ID
        //PanelCollection
        root.invokeOnComponent(fctx, "pc1:tt1", new ContextCallback() {
                public void invokeContextCallback(FacesContext facesContext, UIComponent uiComponent) {
                    cogTree = (RichTreeTable)uiComponent;
                }
            });
    }

    public void PopUpCancleListner(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();

        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
        createOpBAddress.execute();

        BindingContainer binding1 = getBindings();
        OperationBinding createOpBAddress1 = binding1.getOperationBinding("Execute1");
        createOpBAddress1.execute();
        requeryForGrpLOV();
        this.setMode("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(cogTree);
    }

    public void selectViewmode(SelectionEvent selectionEvent) {
        // Add event code here...
        RichTreeTable treeTable = this.getCogTree();

        RowKeySet rks = treeTable.getSelectedRowKeys();
        Iterator rksIterator = rks.iterator();
        if (rksIterator.hasNext()) {
            List key = (List)rksIterator.next();
            JUCtrlHierBinding treeBinding = null;
            treeBinding = (JUCtrlHierBinding)((CollectionModel)treeTable.getValue()).getWrappedData();
            JUCtrlHierNodeBinding nodeBinding = nodeBinding = treeBinding.findNodeByKeyPath(key);


            DCIteratorBinding _treeIteratorBinding = null;
            _treeIteratorBinding = treeBinding.getDCIteratorBinding();

            Key rowKey = nodeBinding.getRowKey();
            JUIteratorBinding iterator = nodeBinding.getIteratorBinding();
            iterator.setCurrentRowWithKey(rowKey.toStringFormat(true));
            this.setServerlocationId((Integer)iterator.getCurrentRow().getAttribute("CogSlocId"));
            this.setCogId((String)iterator.getCurrentRow().getAttribute("CogId"));
            this.setGroupType((String)iterator.getCurrentRow().getAttribute("CogGrpType"));

            String CldId = (String)iterator.getCurrentRow().getAttribute("CogCldId");
            String CogOrgId = (String)iterator.getCurrentRow().getAttribute("CogOrgId");

            BindingContainer binding = getBindings();
            ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvEl("ChartOfGroupAMDataControl");
            ViewObject vo = am.findViewObject("FinCogNewVO");
            FinCogForTreeVOImpl vSearch = am.getFinCogForTree();
            vo.setWhereClause("Cog_Id='" + CogId + "' and COG_CLD_ID='" + CldId + "' and COG_SLOC_ID=" +
                              serverlocationId + " and COG_ORG_ID='" + CogOrgId + "'");
            vo.executeQuery();
            requeryForGrpLOV();

            vSearch.setcogParentIdBindVar(CogId);
            vSearch.setSlocIdBindVar(serverlocationId);
            vSearch.setCldIdBindVar(CldId);
            vSearch.setOrgIdBindVar(CogOrgId);
            vSearch.executeQuery();
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("setcogParentIdBindVar");
            operationBinding.execute();
            panelCollectionTree.setVisible(true);
            cogIdVal.clear();
            AdfFacesContext.getCurrentInstance().addPartialTarget(cogTreeBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelCollectionTree);

        }
    }


    public void saveCog(ActionEvent actionEvent) {
         /*  System.out.println("---------"+grpTyp.getValue());
        System.out.println("cogBalenceSheet.getValue()"+cogBalenceSheet.getValue());
        System.out.println("cogProfitLoss.getValue()"+cogProfitLoss.getValue()); */

        /*   if(chekactivemode=="N")
        {
          if((Boolean)cogBalenceSheet.getValue()==false && (Boolean)cogProfitLoss.getValue()==false) {
       // System.out.println("832489346379681297901274209670912");
            activeMode="T"  ;
        }
        else if((Boolean)cogBalenceSheet.getValue()==true && (Boolean)cogProfitLoss.getValue()==true) {
            activeMode="T"  ;
        }
        else
        {
        activeMode="F";
        }
        } */
        changedBy = "FORM";
        String a = cogBalenceSheet.getValue().toString();
        String pnl = cogProfitLoss.getValue().toString();
        String cogTran = cogTransfer.getValue().toString();
         /**
         * Duplicate record check for COG_NM, COG_ALIAS, LEGACY_CODE
         */
        Boolean cogNmCount = false;
        Boolean cogAliasCount = false;
        Boolean cogLegCodeCount = false;
        ChartOfGroupAMImpl am = getAm();
        ViewObjectImpl cogNewVO = am.getFinCog3();
        //Check for duplicate CogNm
        if (this.cogNmFormBind.getValue() != null) {
             Row[] filteredRows = cogNewVO.getFilteredRows("CogNm", this.cogNmFormBind.getValue().toString());
            if (filteredRows.length > 1) {
                cogNmCount = true;
            }
        }
        //Check for duplicate LegCode check
        if (this.cogLegCodeFormBind.getValue() != null) {
             Row[] filteredRows = cogNewVO.getFilteredRows("CogLegCd", this.cogLegCodeFormBind.getValue().toString());
            if (filteredRows.length > 1) {
                cogLegCodeCount = true;
            }
        }
        //Check for duplicate Cog Alias Check
        if (this.cogAliasFormBind.getValue() != null) {
            Row[] filteredRows = cogNewVO.getFilteredRows("CogAlias", this.cogAliasFormBind.getValue().toString());
            if (filteredRows.length > 1) {
                cogAliasCount = true;
            }
        }


       /*  if (cogNmCount && editFlag.equals("false")) {
            //String msg1 = resolvEl1("Duplicate COG Name.");
            FacesMessage message = new FacesMessage("Duplicate COG Name.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(this.cogNmFormBind.getClientId(), message);
        } */ if (cogLegCodeCount) {
                //String msg1 = resolvEl1("Duplicate COG Legacy code.");
            FacesMessage message = new FacesMessage("Duplicate COG Legacy code.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(this.cogLegCodeFormBind.getClientId(), message);
        } else if (cogAliasCount) {
               //String msg1 = resolvEl1("Duplicate COG Alias.");
            FacesMessage message = new FacesMessage("Duplicate COG Alias.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(this.cogAliasFormBind.getClientId(), message);
        }


       /*  else if (a.equalsIgnoreCase("true") && pnl.equalsIgnoreCase("true")) {
           String msg1 = resolvEl1("#{bundle['MSG.115']}");
            FacesMessage message = new FacesMessage(msg1);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //  throw new ValidatorException(message);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } */
             else if (a.equalsIgnoreCase("false") && pnl.equalsIgnoreCase("false")) {
            String msg2 = resolvEl1("#{bundle['MSG.116']}");
            FacesMessage message = new FacesMessage(msg2);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //  throw new ValidatorException(message);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }  else if ((grpTyp.getValue().toString().equalsIgnoreCase("A") ||
                    grpTyp.getValue().toString().equalsIgnoreCase("L"))&&(Boolean)cogBalenceSheet.getValue() == false)  {//&& (Boolean)cogProfitLoss.getValue() == true)
            FacesMessage message = new FacesMessage("Select Balance sheet in case of Assets and liability");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }  else if ((grpTyp.getValue().toString().equalsIgnoreCase("E") ||
                    grpTyp.getValue().toString().equalsIgnoreCase("I"))&& (Boolean)cogProfitLoss.getValue() == false )// (Boolean)cogBalenceSheet.getValue() == true)
                  {          
            FacesMessage message = new FacesMessage("Select Profilt and Loss in case of Expense and Income");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }  else {
                 if (a.equalsIgnoreCase("true") && cogTran.equalsIgnoreCase("false")) {
                showPopup(transferPopForBS, true);
            } else if (pnl.equalsIgnoreCase("true") && cogTran.equalsIgnoreCase("true")) {
                showPopup(transferPopupForPnL, true);
            } else {
                    requeryForGrpLOV();
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();
                editFlag = "false";

                OperationBinding createOpBAddress = bindings.getOperationBinding("Execute");
                createOpBAddress.execute();

                OperationBinding createOpBAddressExe = bindings.getOperationBinding("Execute2");
                createOpBAddressExe.execute();
                if (operationBinding.getErrors().isEmpty()) {
                    showPopup(msgPop, true);
                    this.setMode("");
                    panelCollectionTree.setVisible(false);
                }
                activeMode = "F";
                CogName="";
            }
        }
   }

    public void reset() {
        ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvElDC("ChartOfGroupAMDataControl");
        ViewObjectImpl v = am.getFinCog1();

        v.setWhereClause(null);
        v.executeQuery();
        setSearchString("");
        panelCollectionTree.setVisible(false);
        Search();
        searchCog(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cogTree);
    }

    public void resetTree(ActionEvent actionEvent) {
        reset();
        AdfFacesContext.getCurrentInstance().addPartialTarget(cogTree);
    }

    private String activeMode = "F";

    public String createCog() {
        //    chekactivemode="N";
        activeMode = "T";
        ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvElDC("ChartOfGroupAMDataControl");
        ViewObjectImpl cogVo = am.getFinCogNewVO();
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        OperationBinding createOpBAddress = bindings.getOperationBinding("Createwithparameters");
        createOpBAddress.execute();
        requeryForGrpLOV();

        /*      System.out.println(resolvElDCBindVar("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        System.out.println( resolvElDCBindVar("#{pageFlowScope.GLBL_APP_CLD_ID}"));
        System.out.println(resolvElDCBindVar("#{pageFlowScope.GLBL_APP_USR_ORG}"));
        System.out.println( cogVo.getCurrentRow().getAttribute("CogIdParent"));

        OperationBinding cob = bindings.getOperationBinding("checkCogId");
         cob.getParamsMap().put("Sloc", resolvElDCBindVar("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        cob.getParamsMap().put("CldId", resolvElDCBindVar("#{pageFlowScope.GLBL_APP_CLD_ID}"));
        cob.getParamsMap().put("OrgId", resolvElDCBindVar("#{pageFlowScope.GLBL_APP_USR_ORG}"));
        cob.getParamsMap().put("CogId",cogVo.getCurrentRow().getAttribute("CogIdParent") );
        cob.execute();
        String size=cob.getResult().toString();
        // this.setCogId(size);
        System.out.println("sizeee value==+"+size);
        System.out.println("vaoe of length of sizde"+size.length()); */


        this.setMode("A");

        panelCollectionTree.setVisible(false);

        return null;
    }


    public void cogPnLItemPopupChangeListner(ValueChangeEvent vcepnl) {

    }

    public void cogBsItemPopupChangeListner(ValueChangeEvent vbspnlpp) {

        if (vbspnlpp.getNewValue() != null) {
            String a = vbspnlpp.getNewValue().toString();


            cogTransferBalancePopup.setValue(a);
        }
    }


    public void setMsgPop(RichPopup msgPop) {
        this.msgPop = msgPop;
    }

    public RichPopup getMsgPop() {
        return msgPop;
    }

    /**
     * @param popupCanceledEvent
     */
    public void msgPopCancelListner(PopupCanceledEvent popupCanceledEvent) {
        msgPop.hide();
    }

    public void msgDialogListner(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            msgPop.hide();
        }
    }


    public void delPopUpCancelListner(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        BindingContainer binding = getBindings();
        BindingContainer binding1 = getBindings();
        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("FinCog1Iterator1");
        Key parentKey = parentIter.getCurrentRow().getKey();
        DCIteratorBinding nxtIter = (DCIteratorBinding)bindings.get("FinCogNewVOIterator");
        Key nxtKey = parentIter.getCurrentRow().getKey();

        OperationBinding operationBinding;
        operationBinding = bindings.getOperationBinding("Rollback");
        OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
        OperationBinding createOpBAddress1 = binding1.getOperationBinding("Execute1");


        operationBinding.execute();
        createOpBAddress.execute();
        createOpBAddress1.execute();

        if (parentIter.getRowSetIterator().getRow(parentKey) !=
            null) { //check condition else gives exception in add mode.
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        }
        if (nxtIter.getRowSetIterator().getRow(nxtKey) != null) { //check condition else gives exception in add mode.
            nxtIter.setCurrentRowWithKey(nxtKey.toStringFormat(true));
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(cogTree);
        this.setMode("");
    }


    public void setPanelCollectionTree(RichPanelCollection panelCollectionTree) {
        this.panelCollectionTree = panelCollectionTree;
    }

    public RichPanelCollection getPanelCollectionTree() {
        return panelCollectionTree;
    }


    public void setSelectCheckBox(RichSelectBooleanCheckbox selectCheckBox) {
        this.selectCheckBox = selectCheckBox;
    }

    public RichSelectBooleanCheckbox getSelectCheckBox() {
        if (selectCheckBox == null) {
            RichSelectBooleanCheckbox val = new RichSelectBooleanCheckbox();
            val.setValue(Boolean.FALSE);
            return val;

        }

        return selectCheckBox;
    }

    public String selectAllAction() {
        ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvElDC("ChartOfGroupAMDataControl");
        ViewObjectImpl v = am.getFinCogForTree();


        RowSetIterator itr = v.createRowSetIterator(null);
        while (itr.hasNext()) {
            Row rw = itr.next();

            rw.setAttribute("TransCheckBox", Boolean.TRUE);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cogTreeBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(selectCheckBox);
            cogIdVal.add(rw.getAttribute("CogId").toString());
        }

        itr.closeRowSetIterator();
        return null;
    }

    public String DeselectAllAction() {
        ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvElDC("ChartOfGroupAMDataControl");
        ViewObjectImpl v = am.getFinCogForTree();


        RowSetIterator itr = v.createRowSetIterator(null);
        while (itr.hasNext()) {
            Row rw = itr.next();

            rw.setAttribute("TransCheckBox", Boolean.FALSE);

            AdfFacesContext.getCurrentInstance().addPartialTarget(cogTreeBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(selectCheckBox);
            cogIdVal.remove(rw.getAttribute("CogId").toString());
        }

        itr.closeRowSetIterator();

        return null;
    }


    public void setCogTreeBinding(RichTreeTable cogTreeBinding) {
        this.cogTreeBinding = cogTreeBinding;
    }

    public RichTreeTable getCogTreeBinding() {
        return cogTreeBinding;
    }


    public void checkboxSelectListner(ValueChangeEvent valueChangeEvent) {

        if (Boolean.TRUE.equals(valueChangeEvent.getNewValue())) {

            cogIdVal.add(treeCogName.getValue().toString());
            // System.out.println(cogIdVal);


        } else if (Boolean.FALSE.equals(valueChangeEvent.getNewValue())) {

            cogIdVal.remove(treeCogName.getValue().toString());


        }


    }

    public void setTreeCogName(RichOutputText treeCogName) {
        this.treeCogName = treeCogName;
    }

    public RichOutputText getTreeCogName() {
        return treeCogName;
    }

    public String getValueAction() {
        String val = "";

        Iterator itr = cogIdVal.iterator();

        while (itr.hasNext()) {

            val = val + itr.next().toString() + ",";
        }

        int strcount = val.length();
        val = val.substring(0, strcount - 1);

        return null;
    }

    public void setCogBalanceSheetPopup(RichSelectBooleanCheckbox cogBalanceSheetPopup) {
        this.cogBalanceSheetPopup = cogBalanceSheetPopup;
    }

    public RichSelectBooleanCheckbox getCogBalanceSheetPopup() {
        return cogBalanceSheetPopup;
    }

    public void setCogPnLPopup(RichSelectBooleanCheckbox cogPnLPopup) {
        this.cogPnLPopup = cogPnLPopup;
    }

    public RichSelectBooleanCheckbox getCogPnLPopup() {
        return cogPnLPopup;
    }

    public void setCogBalenceSheet(RichSelectBooleanCheckbox cogBalenceSheet) {
        this.cogBalenceSheet = cogBalenceSheet;
    }

    public RichSelectBooleanCheckbox getCogBalenceSheet() {
        return cogBalenceSheet;
    }

    public void setCogProfitLoss(RichSelectBooleanCheckbox cogProfitLoss) {
        this.cogProfitLoss = cogProfitLoss;
    }

    public RichSelectBooleanCheckbox getCogProfitLoss() {
        return cogProfitLoss;
    }

    public void setCogTransfer(RichSelectBooleanCheckbox cogTransfer) {
        this.cogTransfer = cogTransfer;
    }

    public RichSelectBooleanCheckbox getCogTransfer() {
        return cogTransfer;
    }

    public void setCogTransferBalancePopup(RichSelectBooleanCheckbox cogTransferBalancePopup) {
        this.cogTransferBalancePopup = cogTransferBalancePopup;
    }

    public RichSelectBooleanCheckbox getCogTransferBalancePopup() {
        return cogTransferBalancePopup;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        if (mode.equals("")) {
            return "V";
        } else {
            return mode;
        }

    }

    public String resolvElDCBindVar(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    public String DeleteSelectCOG() {
        if (cogIdVal.size() <= 0) {
            String msg1 = resolvEl1("#{bundle['MSG.216']}");
            FacesMessage message = new FacesMessage(msg1);
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {


            String CogId = "";
            Integer SlocId = Integer.parseInt(resolvElDCBindVar("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String CldId = (resolvElDCBindVar("#{pageFlowScope.GLBL_APP_CLD_ID}"));
            String OrgId = (resolvElDCBindVar("#{pageFlowScope.GLBL_HO_ORG_ID}"));

            Iterator itr = cogIdVal.iterator();
            while (itr.hasNext()) {

                CogId = CogId + itr.next().toString() + "-";


            }
            CogId = CogId.substring(0, CogId.length() - 1);
            // System.out.println(CogId + "------->CogId");

            String retval = DeletedSelectedValue(CogId, SlocId, CldId, OrgId);


            if (retval.equalsIgnoreCase("Y")) {
                String msg1 = resolvEl1("#{bundle['MSG.216']}");
                FacesMessage message = new FacesMessage(msg1);
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();


                OperationBinding createOpBAddress = bindings.getOperationBinding("Execute");
                createOpBAddress.execute();


                OperationBinding createOpBAddress1 = bindings.getOperationBinding("Execute1");
                createOpBAddress1.execute();

                OperationBinding createOpBAddressExe = bindings.getOperationBinding("Execute2");
                createOpBAddressExe.execute();
                panelCollectionTree.setVisible(false);
            } else {
                if (failureMessage.equals("")) {
                    failureMessage = "";
                } else {
                    failureMessage = "Chart of group " + failureMessage + " is not allowed to delete. ";
                }
                if (successMessage.equals("")) {
                    successMessage = "";
                } else {
                    successMessage = "Chart of group " + successMessage + " only is allowed to deleted.";
                }
                String msg1 = failureMessage + successMessage;
                FacesMessage message = new FacesMessage(msg1);
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }


        }
        return null;

    }

    private String DeletedSelectedValue(String CogId, Integer SlocId, String CldId, String OrgId) {

        String val =
            callStoredProc_delete("FIN.PKG_FIN.PROC_cog_grp_md(?,?,?,?,?,?,?)", new Object[] { SlocId, CldId, OrgId,
                                                                                               CogId });
        return val;
    }

    private String MoveSelectedValue(String CogId, Integer SlocId, String CldId, String OrgId, Integer UserId,
                                     String CogParentId) {

        System.out.println("Params :" + SlocId + ":" + CldId + ":" + OrgId + ":" + UserId + ":" + CogId + ":" +
                           CogParentId);
        //        String val =
        //            callStoredProc_move("FIN.PKG_FIN.PROC_cog_grp_md(?,?,?,?,?,?,?,?,?)", new Object[] { SlocId, CldId, OrgId,
        //                                                                                                 UserId, CogId,
        //                                                                                                 CogParentId });
        String val =
            callStoredProc_move("FIN.FN_cog_grp_md(?,?,?,?,?,?,?,?,?)", new Object[] { SlocId, CldId, OrgId, CogId,
                                                                                       CogParentId });

        return val;
    }

    public void cogBsItemChangeListner(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            getCogTransfer().setValue(vce.getNewValue().toString());
        }
    }


    public String MoveSelectedCOGAction() {
        String ParentId = "";

        if (cogIdVal.size() <= 0) {
            String msg1 = resolvEl1("#{bundle['MSG.217']}");
            FacesMessage message = new FacesMessage(msg1);
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {


            if (moveParentId.getValue() == null) {
                String msg1 = resolvEl1("#{bundle['MSG.218']}");
                FacesMessage message = new FacesMessage(msg1);
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {


                ParentId = moveParentId.getValue().toString();
                // System.out.println(moveParentId);

                String CogId = "";
                Integer SlocId = Integer.parseInt(resolvElDCBindVar("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                String CldId = (resolvElDCBindVar("#{pageFlowScope.GLBL_APP_CLD_ID}"));
                String OrgId = (resolvElDCBindVar("#{pageFlowScope.GLBL_HO_ORG_ID}"));
                Integer UserId = Integer.parseInt(resolvElDCBindVar("#{pageFlowScope.GLBL_APP_USR}"));
                // System.out.println(ParentId + "<---------------");
                Iterator itr = cogIdVal.iterator();
                while (itr.hasNext()) {
                    String val = itr.next().toString();
                    if (val.equalsIgnoreCase(ParentId)) {
                        String msg1 = resolvEl1("#{bundle['MSG.219']}");
                        FacesMessage message = new FacesMessage(msg1);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                        return null;
                    } else {
                        CogId = CogId + val + "-";
                    }

                }
                CogId = CogId.substring(0, CogId.length() - 1);
                // System.out.println(CogId + "------->CogId");

                String retval = MoveSelectedValue(CogId, SlocId, CldId, OrgId, UserId, ParentId);


                if (retval.equalsIgnoreCase("Y")) {
                    String msg1 = resolvEl1("#{bundle['MSG.220']}");
                    FacesMessage message = new FacesMessage(msg1);
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                    BindingContainer bindings = getBindings();
                    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                    operationBinding.execute();


                    OperationBinding createOpBAddress = bindings.getOperationBinding("Execute");
                    createOpBAddress.execute();


                    OperationBinding createOpBAddress1 = bindings.getOperationBinding("Execute1");
                    createOpBAddress1.execute();

                    OperationBinding createOpBAddressExe = bindings.getOperationBinding("Execute2");
                    createOpBAddressExe.execute();
                    panelCollectionTree.setVisible(false);
                } else {
                    if (failureMessage.equals("")) {
                        failureMessage = "";
                    } else {
                        failureMessage = "Chart of group " + failureMessage + " is not allowed to move. ";
                    }
                    if (successMessage.equals("")) {
                        successMessage = "";
                    } else {
                        successMessage = "Chart of group " + successMessage + " only is allowed to move.";
                    }
                    String msg1 = failureMessage + successMessage;
                    FacesMessage message = new FacesMessage(msg1);
                    message.setSeverity(FacesMessage.SEVERITY_WARN);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }


            }
        }
        return null;
    }

    public void setMoveParentId(RichSelectOneChoice moveParentId) {
        this.moveParentId = moveParentId;
    }

    public RichSelectOneChoice getMoveParentId() {
        return moveParentId;
    }

    public void NameValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String msg2 = "";
        ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvElDC("ChartOfGroupAMDataControl");
        ViewObjectImpl v = am.getFinCogNewVO();
        ViewObjectImpl vo = am.getFinCog1();
        System.out.println("cognameeee isssss in vaaa=="+CogName);
        String cogname = object.toString();
        if (object != null) {
            System.out.println("CogId=" + v.getCurrentRow().getAttribute("CogId"));
            String cldid = resolvElDCBindVar("#{pageFlowScope.GLBL_APP_CLD_ID}");
            Integer slocid = Integer.parseInt(resolvElDCBindVar("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
            String hoorgid = resolvElDCBindVar("#{pageFlowScope.GLBL_HO_ORG_ID}");
            System.out.println("helllooooooooooo");
            System.out.println("value of cog name which is entered in the field is ==" + object);
            BindingContext bindingctx = BindingContext.getCurrent();
            BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
            OperationBinding cob = bindings.getOperationBinding("CheckDulicateCogName");
            System.out.println(resolvElDCBindVar("#{pageFlowScope.GLBL_APP_CLD_ID}"));
            cob.getParamsMap().put("CldId", cldid);
            cob.getParamsMap().put("Sloc", slocid);
            cob.getParamsMap().put("HoOrgId", hoorgid);
            //cob.getParamsMap().put("cognm", object);
            cob.getParamsMap().put("cognm", cogname);
            cob.getParamsMap().put("cogId", v.getCurrentRow().getAttribute("CogId"));

            cob.execute();
            System.out.println("value of the resut is=" + cob.getResult());
            if (cob.getResult() != null) {
                String Val = cob.getResult().toString();
                System.out.println("value return by the function is=" + Val);
                if (Val.equalsIgnoreCase("N")) {
                    System.out.println("cog name already exists please enter another name");
                    String msg = "this COG is already exist please enter new COG Name.";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

                }
            }
            Row curr=v.getCurrentRow();
            System.out.println("-----"+curr.getAttribute("CogNm"));
            if (v.getCurrentRow().getAttribute("CogId") != null&& CogName!="A") {
                System.out.println("COggggNameeeee is===="+CogName);
                System.out.println("------------->>>>>>>>>>>>>>>>>>>>>>");
                Row[] rw = v.getFilteredRows("CogId", v.getCurrentRow().getAttribute("CogId"));
                System.out.println("length=" + rw.length);
                if (rw.length > 0) {
                    String name = rw[0].getAttribute("CogNm").toString();
                    if (name.equalsIgnoreCase(cogname)) {
                        String msg = "this COG is already exist please enter new COG Name.";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                    }
                } 
            }
            String DbobDesc = object.toString();
            char at = DbobDesc.charAt(0);
            if (at == ' ') {
                System.out.println("Spcae at first");
                FacesMessage message2 = new FacesMessage("Space is not allowed as first character in name.");
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            } else if ((DbobDesc.length() - 1) == DbobDesc.lastIndexOf(' ')) {
                System.out.println("Spcae at last");
                FacesMessage message2 = new FacesMessage("Space is not allowed as last character in name.");
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);

            }


            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = DbobDesc.toCharArray();
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
            if (openB != closeB || closeFg == true || (DbobDesc.lastIndexOf("(") > DbobDesc.lastIndexOf(")")) ||
                (DbobDesc.indexOf(")") < DbobDesc.indexOf("("))) {
                msg2 = resolvEl1("#{bundle['MSG.7']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (DbobDesc.contains("()")) {
                msg2 = resolvEl1("#{bundle['MSG.49']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (DbobDesc.contains("(.") || DbobDesc.contains("(-") || DbobDesc.contains("-)")) {
                msg2 = resolvEl1("#{bundle['MSG.162']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;


            //check for valid language name.Allowed- brackets,dots,hyphen
            String expression = "^(?:(?>[A-Za-z0-9 \\(\\)]+)()(\\%|\\@|\\_|\\.|\\-|\\$(?!\\.|\\-|$))?)*$";
            CharSequence inputStr = DbobDesc;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvEl1("#{bundle['MSG.221']}");

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }
            int count = 0;
            String[] charval = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
            for (int i = 0; i < charval.length; i++) {
                Boolean val = DbobDesc.startsWith(charval[i]);
                if (val.equals(Boolean.TRUE)) {
                    count = count + 1;
                }
            }

            if (count > 0) {
                String numNotAllowMsg = resolvEl1("#{bundle['MSG.222']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, numNotAllowMsg, null));
            }


        }

    }

    public void parentCogChangeListner(ValueChangeEvent vce) {
      if (vce.getNewValue() != null) {
            String CogparentId = vce.getNewValue().toString();
            Integer SlocId = Integer.parseInt(resolvElDCBindVar("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String CldId = (resolvElDCBindVar("#{pageFlowScope.GLBL_APP_CLD_ID}"));
            String OrgId = (resolvElDCBindVar("#{pageFlowScope.GLBL_HO_ORG_ID}"));


            ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvElDC("ChartOfGroupAMDataControl");
            ViewObjectImpl v = am.getFinCogLOV1();
            FinCogNewVORowImpl finCog = (FinCogNewVORowImpl)am.getFinCogNewVO().getCurrentRow();
            RowQualifier rq = new RowQualifier(v);
            rq.setWhereClause("CogCldId='" + CldId + "' AND CogSlocId=" + SlocId + " AND CogOrgId='" + OrgId +
                              "' AND CogId='" + CogparentId + "'");
            Row[] rw = v.getFilteredRows(rq);


            if (rw.length > 0) {
                Row r = rw[0];
                String inheritVal = r.getAttribute("CogInheritProp").toString();

                if (inheritVal.equals("Y")) {

                    if (r.getAttribute("CogActv") != null) {
                        finCog.setCogActv(r.getAttribute("CogActv").toString());
                    }

                    if (r.getAttribute("CogBsItem") != null) {
                        finCog.setCogBsItem(r.getAttribute("CogBsItem").toString());
                    }

                    if (r.getAttribute("CogCfItem") != null) {
                        finCog.setCogCfItem(r.getAttribute("CogCfItem").toString());
                    }

                    if (r.getAttribute("CogGrpBalType") != null) {
                        finCog.setCogGrpBalType(r.getAttribute("CogGrpBalType").toString());
                    }

                    if (r.getAttribute("CogGrpType") != null) {

                        finCog.setCogGrpType(r.getAttribute("CogGrpType").toString());
                        grpTyp.setValue(r.getAttribute("CogGrpType"));
                        AdfFacesContext.getCurrentInstance().addPartialTarget(grpTyp);
                    }

                    if (r.getAttribute("CogInheritProp") != null) {
                        finCog.setCogInheritProp(r.getAttribute("CogInheritProp").toString());
                    }

                    if (r.getAttribute("CogPnlItem") != null) {
                        finCog.setCogPnlItem(r.getAttribute("CogPnlItem").toString());
                    }


                    if (r.getAttribute("CogSubldgr") != null) {
                        finCog.setCogSubldgr(r.getAttribute("CogSubldgr").toString());
                    }

                    if (r.getAttribute("CogTrfBal") != null) {
                        finCog.setCogTrfBal(r.getAttribute("CogTrfBal").toString());
                    }

                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(grpTyp);
    }

    public void setTransferPopForBS(RichPopup transferPopForBS) {
        this.transferPopForBS = transferPopForBS;
    }

    public RichPopup getTransferPopForBS() {
        return transferPopForBS;
    }

    public void transferPopForBSDialogListner(DialogEvent de) {
        if (de.getOutcome().name().equals("yes")) {
            requeryForGrpLOV();
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();


            OperationBinding createOpBAddress = bindings.getOperationBinding("Execute");
            createOpBAddress.execute();

            OperationBinding createOpBAddressExe = bindings.getOperationBinding("Execute2");
            createOpBAddressExe.execute();
            if (changedBy.equals("POPUP")) {
                OperationBinding createOpBAddress1 = bindings.getOperationBinding("Execute1");
                createOpBAddress1.execute();
            }
            if (operationBinding.getErrors().isEmpty()) {
                showPopup(msgPop, true);
                this.setMode("");
                panelCollectionTree.setVisible(false);
            }

        } else if (de.getOutcome().name().equals("no")) {
            activeMode = "T";
            if (changedBy.equals("POPUP")) {
                showPopup(addPopup, true);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(cogTree);
    }

    public void setTransferPopupForPnL(RichPopup transferPopupForPnL) {
        this.transferPopupForPnL = transferPopupForPnL;
    }

    public RichPopup getTransferPopupForPnL() {
        return transferPopupForPnL;
    }

    public void transferPopupForPnLDialogListner(DialogEvent de) {
        if (de.getOutcome().name().equals("yes")) {
            requeryForGrpLOV();
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();


            OperationBinding createOpBAddress = bindings.getOperationBinding("Execute");
            createOpBAddress.execute();

            OperationBinding createOpBAddressExe = bindings.getOperationBinding("Execute2");
            createOpBAddressExe.execute();
            if (changedBy.equals("POPUP")) {
                OperationBinding createOpBAddress1 = bindings.getOperationBinding("Execute1");
                createOpBAddress1.execute();
            }
            if (operationBinding.getErrors().isEmpty()) {
                showPopup(msgPop, true);
                this.setMode("");
                panelCollectionTree.setVisible(false);
            }

        } else if (de.getOutcome().name().equals("no")) {
            if (changedBy.equals("POPUP")) {
                showPopup(addPopup, true);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(cogTree);
    }

    public void setGrpTyp(RichSelectOneChoice grpTyp) {
        this.grpTyp = grpTyp;
    }

    public RichSelectOneChoice getGrpTyp() {
        return grpTyp;
    }


    public void setPopupcoggrptypBind(RichSelectOneChoice popupcoggrptypBind) {
        this.popupcoggrptypBind = popupcoggrptypBind;
    }

    public RichSelectOneChoice getPopupcoggrptypBind() {
        return popupcoggrptypBind;
    }

    public void setOnloadVal(Integer onloadVal) {
        this.onloadVal = onloadVal;
    }

    public Integer getOnloadVal() {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("on_load_page");
        Object ret = operationBinding.execute();

        return (Integer)ret;
    }

    public void setCogNmFormBind(RichInputText cogNmFormBind) {
        this.cogNmFormBind = cogNmFormBind;
    }

    public RichInputText getCogNmFormBind() {
        return cogNmFormBind;
    }

    public void setCogLegCodeFormBind(RichInputText cogLegCodeFormBind) {
        this.cogLegCodeFormBind = cogLegCodeFormBind;
    }

    public RichInputText getCogLegCodeFormBind() {
        return cogLegCodeFormBind;
    }

    public void setCogAliasFormBind(RichInputText cogAliasFormBind) {
        this.cogAliasFormBind = cogAliasFormBind;
    }

    public RichInputText getCogAliasFormBind() {
        return cogAliasFormBind;
    }

    public void setEditFlag(String editFlag) {
        this.editFlag = editFlag;
    }

    public String getEditFlag() {
        return editFlag;
    }


    public void setActiveMode(String activeMode) {
        this.activeMode = activeMode;
    }

    public String getActiveMode() {
        return activeMode;
    }

    public void checknode() {
        ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvElDC("ChartOfGroupAMDataControl");
        ViewObjectImpl vo = am.getFinCogNewVO();
        ViewObjectImpl v = am.getFinCog1();
        System.out.println("curent row value is" + vo.getCurrentRow().getAttribute("CogId"));
        Row row[] = v.getFilteredRows("CogId", vo.getCurrentRow().getAttribute("CogId"));
        System.out.println("filtesrd row number is=" + row.length);
        showPopup(deletepop, true);
        panelCollectionTree.setVisible(false);
    }

    public String callmaxno(Integer Sloc, String CldId, String OrgId, String CogId) {
        return (String)callStoredFunction(Types.VARCHAR, "FIN.PKG_FIN.GET_COG_ID(?,?,?,?)",
                                          new Object[] { Sloc, CldId, OrgId, CogId });
    }

    public void setParentbinding(RichSelectOneChoice parentbinding) {
        this.parentbinding = parentbinding;
    }

    public RichSelectOneChoice getParentbinding() {
        return parentbinding;
    }

    public void parentidValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String len1 = object.toString();
            System.out.println("length of object parent id is==" + len1.length());

            // String len=parentbinding.getValue().toString();
            // System.out.println("length of parent id"+len.length());
            if (len1.length() > 18) {
                String msg = "Maximum Number Of Level Exceeds. You can not proceed to create new COG";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

            }
            /* ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvElDC("ChartOfGroupAMDataControl");
        ViewObjectImpl coglovvo=am.getFinCogLOV1();
        ViewObjectImpl cogVo = am.getFinCogNewVO();
        ViewObjectImpl finCogVo=am.getFinCog1();
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        System.out.println(finCogVo.getCurrentRow().getAttribute("CogSlocId"));
            System.out.println(finCogVo.getCurrentRow().getAttribute("CogCldId"));
        System.out.println(finCogVo.getCurrentRow().getAttribute("CogOrgId"));
            System.out.println( object);

            OperationBinding cob = bindings.getOperationBinding("checkCogId");
             cob.getParamsMap().put("Sloc", finCogVo.getCurrentRow().getAttribute("CogSlocId"));
            cob.getParamsMap().put("CldId", finCogVo.getCurrentRow().getAttribute("CogCldId"));
            cob.getParamsMap().put("OrgId", finCogVo.getCurrentRow().getAttribute("CogOrgId"));
            cob.getParamsMap().put("CogId",object );
            cob.execute();
       if(cob.getErrors().size()>0) {
           System.out.println("Error is="+cob.getErrors());
       }
            System.out.println("value of result in called stored function"+cob.getResult());
            if(cob.getResult()!=null)
            {
            String val=cob.getResult().toString();
            // this.setCogId(size);
            //System.out.println("sizeee value==+"+size);
           // System.out.println("vaoe of length of sizde"+size.length());
            if(val.equals("-1")) {
                String msg="Maximum Number Of Level Exceeds. You can not proceed to create new COG";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,null));

            }
            } */
        }
    }

    public void setChekactivemode(String chekactivemode) {
        this.chekactivemode = chekactivemode;
    }

    public String getChekactivemode() {
        return chekactivemode;
    }
    /*  public void NameValidation1(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String msg2 = "";
        ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvElDC("ChartOfGroupAMDataControl");
        ViewObjectImpl v = am.getFinCogNewVO();
               if (object != null) {
         System.out.println("CogId="+v.getCurrentRow().getAttribute("CogId"));
         String cldid=resolvElDCBindVar("#{pageFlowScope.GLBL_APP_CLD_ID}");
         Integer slocid=Integer.parseInt(resolvElDCBindVar("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
         String hoorgid=resolvElDCBindVar("#{pageFlowScope.GLBL_HO_ORG_ID}");
          System.out.println("helllooooooooooo");
          System.out.println("value of cog name which is entered in the field is =="+object);
            BindingContext bindingctx = BindingContext.getCurrent();
            BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
            OperationBinding cob = bindings.getOperationBinding("CheckDulicateCogName");
            System.out.println(resolvElDCBindVar("#{pageFlowScope.GLBL_APP_CLD_ID}"));
            cob.getParamsMap().put("CldId", cldid);
             cob.getParamsMap().put("Sloc",slocid);
            cob.getParamsMap().put("HoOrgId", hoorgid);
            cob.getParamsMap().put("cognm",object );
            cob.getParamsMap().put("cogId",v.getCurrentRow().getAttribute("CogId") );

            cob.execute();
            System.out.println("value of the resut is="+cob.getResult());
            if(cob.getResult()!=null)
            { String Val=cob.getResult().toString();
            if(Val.equalsIgnoreCase("N")) {
           String msg="this COG is already exist please enter new COG Name.";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,null));

            }
            }
            String DbobDesc = object.toString();
            char at = DbobDesc.charAt(0);
            if(at == ' '){
                System.out.println("Spcae at first");
                FacesMessage message2 = new FacesMessage("Space is not allowed as first character in name.");
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }else if((DbobDesc.length()-1) == DbobDesc.lastIndexOf(' ')){
                System.out.println("Spcae at last");
                FacesMessage message2 = new FacesMessage("Space is not allowed as last character in name.");
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);

            }




            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = DbobDesc.toCharArray();
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
            if (openB != closeB || closeFg == true || (DbobDesc.lastIndexOf("(") > DbobDesc.lastIndexOf(")")) ||
                (DbobDesc.indexOf(")") < DbobDesc.indexOf("("))) {
                msg2 = resolvEl1("#{bundle['MSG.7']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (DbobDesc.contains("()")) {
                msg2 = resolvEl1("#{bundle['MSG.49']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (DbobDesc.contains("(.") || DbobDesc.contains("(-") || DbobDesc.contains("-)")) {
                msg2 = resolvEl1("#{bundle['MSG.162']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;


            //check for valid language name.Allowed- brackets,dots,hyphen
            String expression = "^(?:(?>[A-Za-z0-9 \\(\\)]+)()(\\%|\\@|\\_|\\.|\\-|\\$(?!\\.|\\-|$))?)*$";
            CharSequence inputStr = DbobDesc;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvEl1("#{bundle['MSG.221']}");

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }
            int count = 0;
            String[] charval = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
            for (int i = 0; i < charval.length; i++) {
                Boolean val = DbobDesc.startsWith(charval[i]);
                if (val.equals(Boolean.TRUE)) {
                    count = count + 1;
                }
            }

            if (count > 0) {
                String numNotAllowMsg = resolvEl1("#{bundle['MSG.222']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, numNotAllowMsg, null));
            }


        }

    }  */

    public void popUpCL(PopupCanceledEvent popupCanceledEvent) {
        activeMode = "T";
    }

    public void activeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (object.toString().equalsIgnoreCase("false")) {
                ChartOfGroupAMImpl am = (ChartOfGroupAMImpl)resolvElDC("ChartOfGroupAMDataControl");

                if (am != null) {
                    ViewObjectImpl cog = am.getFinCogNewVO();
                    if (cog != null) {
                        Row currentRow = cog.getCurrentRow();
                        if (currentRow != null) {
                            Object CogId = currentRow.getAttribute("CogId");
                            if (CogId != null) {

                                Integer SlocId =
                                    Integer.parseInt(resolvElDCBindVar("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                                String CldId = (resolvElDCBindVar("#{pageFlowScope.GLBL_APP_CLD_ID}"));
                                String HoOrgId = (resolvElDCBindVar("#{pageFlowScope.GLBL_HO_ORG_ID}"));
                                Object result =
                                    callStoredFunction(VARCHAR, "fn_cog_allow_deactivation(?,?,?,?)", new Object[] { CldId,
                                                                                                                     SlocId,
                                                                                                                     HoOrgId,
                                                                                                                     CogId });
                                System.out.println("result =  " + result + " CldId = " + CldId + " SlocId = " +
                                                   SlocId + " HoOrgId = " + HoOrgId + " CogId = " + CogId);
                                if (result != null) {
                                    if (result.toString().equalsIgnoreCase("N")) {
                                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                                      "You can't Inactive the correponding COG. It is in use in COA. ",
                                                                                      null));
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    public void setCreateUnderBind(RichSelectOneChoice createUnderBind) {
        this.createUnderBind = createUnderBind;
    }

    public RichSelectOneChoice getCreateUnderBind() {
        return createUnderBind;
    }

    public void setCogName(String CogName) {
        this.CogName = CogName;
    }

    public String getCogName() {
        return CogName;
    }

    public void setInheritBind(RichSelectBooleanCheckbox inheritBind) {
        this.inheritBind = inheritBind;
    }

    public RichSelectBooleanCheckbox getInheritBind() {
        return inheritBind;
    }
}

