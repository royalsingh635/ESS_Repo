package mmappgrpdef.view.bean;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
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

import javax.swing.text.View;

import mmappgrpdef.model.module.AppGrpdefAMImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;
import oracle.jbo.uicli.binding.JUEventBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.event.SelectionListener;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class AppGrpDefBean 
{
    private static ADFLogger adf_log=(ADFLogger)ADFLogger.createADFLogger(AppGrpDefBean.class);
    private RichPopup addPopup;
    private RichTreeTable groupTree;
    private static int NUMBER = Types.INTEGER;
    private static int VARCHAR = Types.VARCHAR;
    private String mode = "V";
    private RichPanelFormLayout dialogPanelFormBind;
    private RichPanelFormLayout pageFormBind;
    private RichPopup menuPopup;
    private RichInputText inactiveRsnBind;
    private Integer count=-1;
    private RichSelectBooleanCheckbox slsFlg;
    private RichSelectBooleanCheckbox capitalFlg;
    private RichSelectBooleanCheckbox purFlg;
    private RichSelectBooleanCheckbox wipFlg;
    private RichSelectBooleanCheckbox cashPurFlg;
    private RichSelectBooleanCheckbox stockableFlg;
    private RichSelectBooleanCheckbox srvcFlg;
    private RichPanelFormLayout checkBoxPanel;
    private RichSelectOneChoice pickOrderBind;
    private RichOutputText coaNmBindVar;
    private RichInputText bindGrpNmVar;
    private RichInputText bindShortCodeVar;
    int nowRows;
    private RichInputListOfValues coaNmBind;
    private RichSelectOneChoice valuationBind;

    public AppGrpDefBean() {
    }

    public void OnSelection(SelectionEvent selectionEvent) {
        RichTreeTable treeTable = this.getGroupTree();
        RowKeySet rks = treeTable.getSelectedRowKeys();
        String grpId = null;
        if(rks != null){
        Iterator keys = rks.iterator();
        
        while (keys.hasNext()) {
            List key = (List)keys.next();
            treeTable.setRowKey(key);
            JUCtrlHierNodeBinding node = (JUCtrlHierNodeBinding)treeTable.getRowData();
            Row rw = node.getRow();
            String voName = node.getViewObject().getName();
            grpId = rw.getAttribute("GrpId").toString();
        }}
        
        UIComponent component = selectionEvent.getComponent();
        if (resolvEl("#{pageFlowScope.PARAM_TF_CALLED}").toString().equals("Y")) {
            Map<String, Object> attributes = component.getAttributes();
            attributes.put("grpId", grpId);
            BindingContainer bindingContainer = BindingContext.getCurrent().getCurrentBindingsEntry();
            JUEventBinding eventBinding = (JUEventBinding)bindingContainer.get("publisherEventBinding");
            SelectionListener selectListener = (SelectionListener)eventBinding.getListener();
            selectListener.processSelection(selectionEvent);
        } else {
            AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
            ViewObjectImpl v = am.getFormGrp1();
            v.setWhereClause(null);
            v.executeQuery();
            v.setWhereClause("Grp_Id = '" + grpId + "'");
            v.executeQuery();
            //am.getLovGroupIdParent().executeQuery();
            
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(coaNmBindVar);
    }

    public void setGroupTree(RichTreeTable groupTree) {
        this.groupTree = groupTree;
    }

    public RichTreeTable getGroupTree() {
        return groupTree;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    public void addButton(ActionEvent actionEvent) {
        try{
      /*   mode = "A";
        AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
        ViewObjectImpl v = am.getFormGrp1();
        Row row = v.getCurrentRow();
        String grp = "0";
        if (row.getAttribute("GrpId") != null) {
            grp = row.getAttribute("GrpId").toString();
        }
        
        Integer pusrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        Integer pslocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String hoOrgId=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String cldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        AdfFacesContext.getCurrentInstance().addPartialTarget(pageFormBind);
        am.getLovGroupIdParent().executeQuery();
        v.setWhereClause(null);
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
       
        ViewObjectImpl e = am.getFormGrp1();
        Row  row1 = e.getCurrentRow();
        row1.setAttribute("GrpIdParent", grp);
        row1.setAttribute("UsrIdCreate", pusrId);
        row1.setAttribute("SlocId", pslocId);
        row1.setAttribute("HoOrgId", hoOrgId);
        row1.setAttribute("CldId", cldId);
        row1.setAttribute("GrpNm", null);
        row1.setAttribute("GrpShortCode", null);
      menuPopup.hide(); */
      mode = "A";
      AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
      // ViewObjectImpl vgrp = am.getAppGrp1();
      //System.out.println("Current row of appgrp="+vgrp.getCurrentRow());
      ViewObjectImpl v = am.getFormGrp1();
      Row row = v.getCurrentRow();
      String grp = "0";
      if (row.getAttribute("GrpId") != null) {
          grp = row.getAttribute("GrpId").toString();
      }
      System.out.println("Parent GroupId="+grp);
      Integer pusrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
      Integer pslocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
      String hoOrgId=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
      String cldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
      String orgId=resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
      AdfFacesContext.getCurrentInstance().addPartialTarget(pageFormBind);
      //am.getLovGroupIdParent().executeQuery();
      //v.setWhereClause(null);
      BindingContainer bindings = getBindings();
      OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
      operationBinding.execute();
      //ViewObjectImpl e = am.getFormGrp1();
      Row  row1 = v.getCurrentRow();
      row1.setAttribute("GrpIdParent", grp);
      System.out.println("Parent set="+row1.getAttribute("GrpIdParent"));
      row1.setAttribute("UsrIdCreate", pusrId);
      row1.setAttribute("SlocId", pslocId);
      row1.setAttribute("HoOrgId", hoOrgId);
      row1.setAttribute("CldId", cldId);
      row1.setAttribute("GrpNm", null);
      row1.setAttribute("GrpShortCode", null);
      String gpid=null;
      if(row1.getAttribute("GrpId")!=null)
      gpid = (String)row1.getAttribute("GrpId");
      else{
      String table = "APP$GRP";
      gpid = callStoredFunction(Types.VARCHAR, "APP.FN_APP_GEN_ID(?,?,?,?,?,?)", new Object[] { pslocId,cldId,hoOrgId,orgId,table,grp }).toString();
      } 
      row1.setAttribute("GrpId", gpid);
      OperationBinding op = bindings.getOperationBinding("setValueAsParent");
      op.getParamsMap().put("CldId",cldId);
      op.getParamsMap().put("SlocId",pslocId);
      op.getParamsMap().put("HoOrgId",hoOrgId);
      op.getParamsMap().put("GrpId",row1.getAttribute("GrpId"));
      op.getParamsMap().put("GrpIdParent",grp);
      op.execute();
      Integer ret=-1;
      ret =
      (Integer)callStoredFunction(Types.INTEGER, "APP.FN_INS_APP_ITM_GRP_COA(?,?,?,?,?,?,?)", new Object[] { hoOrgId,cldId,pslocId,orgId,gpid,grp,pusrId});
      System.out.println("Return="+ret);
      am.getAppItmGrpCoa1().executeQuery();
      am.getAppItmCoaLink1().executeQuery();
        menuPopup.hide();
        }catch(NullPointerException exception){
            FacesMessage message2 = new FacesMessage("Maine Hu Don");
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, message2);
        }
    }


    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = "N";
        if(valueExp != null)
        {msg = valueExp.getValue(elContext).toString();}
        return msg;
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

    public void setAddPopup(RichPopup addPopup) {
        this.addPopup = addPopup;
    }

    public RichPopup getAddPopup() {
        return addPopup;
    }

    public void addPopupCancelListener(PopupCanceledEvent popupCanceledEvent) {
        if (mode == "A") {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();

            AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
            ViewObjectImpl v = am.getFormGrp1();
            v.executeQuery();
            
            

        }
        AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
        ViewObjectImpl v = am.getAppGrp1();
        v.setWhereClause(null);
        v.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(groupTree);
    }

    public void addDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
            Integer pslocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
            String hoOrgId=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            String cldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            String orgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
            if (mode == "A") {

                ViewObjectImpl v = am.getFormGrp1();
                Row row = v.getCurrentRow();
                String currGrpId = row.getAttribute("GrpIdParent").toString();
                String table = "APP$GRP";
                String grpId =
                    callStoredFunction(VARCHAR, "APP.FN_APP_GEN_ID(?,?,?,?,?,?)", new Object[] { pslocId,cldId,hoOrgId,orgid,table,currGrpId }).toString();
                    //System.out.println("add new grp Id ---- > "+grpId);
                row.setAttribute("GrpId", grpId);

            }

            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();

            ViewObjectImpl e = am.getAppGrp1();
            e.executeQuery();

            ViewObjectImpl f = am.getAppGrp2();
            f.executeQuery();
            
            AdfFacesContext.getCurrentInstance().addPartialTarget(groupTree);
            Search();
        }
    }


    public void editButton(ActionEvent actionEvent) {
  
  /*       String orgId=resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("getCurrentOrgId");
        operationBinding.execute();
        String currOrgId = (String)operationBinding.getResult();
          
        if(!orgId.equals(currOrgId))
        {    mode = "V";
                     operationBinding = bindings.getOperationBinding("getOrganisationName");
                     operationBinding.getParamsMap().put("orgId", resolvEl("#{bindings.OrgId.inputValue}").toString());
                     operationBinding.execute();
                     Object obj = operationBinding.getResult();
               
                if(obj != null)
                {
                    FacesMessage message2 = new FacesMessage("The Group was created from a different Organisation ,Please Login to "+obj+ " Organisation to Edit.");
                    message2.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext.getCurrentInstance().addMessage(null, message2);
                }
             
                else{
                     FacesMessage message2 = new FacesMessage("The Group was created from a different Organisation ,Please Login to Organisation to Edit.");
                     message2.setSeverity(FacesMessage.SEVERITY_INFO);
                     FacesContext.getCurrentInstance().addMessage(null, message2);
                    }
         }
       else
        {} */
        
        mode = "E";
       /*  showPopup(addPopup, true); */
        menuPopup.hide();
        
    }


    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
        try {
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();
            System.out.println(st.getObject(1));
            return st.getObject(1);

        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void searchButton(ActionEvent actionEvent) {
        Search();
        AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
        ViewObjectImpl v = am.getFormGrp1();
        v.setWhereClause(null);
        v.executeQuery();
        v.setWhereClause("Upper(Grp_Nm) like '%" + searchString.toUpperCase() + "%'");
        v.executeQuery();
       // System.out.println("COUNT--"+v.getRowCount()+"--"+v.getAllRowsInRange().length);
        if(v.getAllRowsInRange().length==0){
            FacesMessage message2 = new FacesMessage(resolvEl("#{bundle['MSG.274']}").toString());
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, message2);
        }
    }

    private void log(String message) {
        logger.log(ADFLogger.WARNING, message);
    }

    private void findTreeInView() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        UIViewRoot root = fctx.getViewRoot();
        //hard coding tree component Id with its surrounding naming container ID
        //PanelCollection
        root.invokeOnComponent(fctx, "tt1", new ContextCallback() {
                public void invokeContextCallback(FacesContext facesContext, UIComponent uiComponent) {
                    groupTree = (RichTreeTable)uiComponent;
                }
            });
    }

    private String searchString = "";
    private String searchType = "CONTAIN";
    private String searchAttributes = "GrpNm";
    ADFLogger logger = ADFLogger.createADFLogger(this.getClass());


    public void findRowInTable(String searchAtt,String searchTyp,String searchStr) {
        JUCtrlHierBinding treeBinding = null;

        //get handle to tree if it does not exist. If tree component cannot be
        //found in view, exit this function
        if (groupTree == null) {
            this.findTreeInView();
            if (groupTree == null) {
                //tree not found
                log("The tree component could not be found in the view. Please check for naming containers. Search function cancelled");
                return;
            }
        }
        //Get the JUCtrlHierbinding reference from the PageDef
        CollectionModel model = (CollectionModel)groupTree.getValue();
        treeBinding = (JUCtrlHierBinding)model.getWrappedData();

        //Read the attributes to search in from the SelectManyChoice component
        //  String searchAttributeArray[] =             (String[])searchAttributes.toArray(new String[searchAttributes.size()]);

        //Define a node to search in. In this example, the root node is used
        JUCtrlHierNodeBinding root = treeBinding.getRootNodeBinding();

        //However, if the user used the "Show as Top" context menu option to
        //shorten the tree display, then we only search starting from this top
        //mode

        List topNode = (List)groupTree.getFocusRowKey();
        if (topNode != null) {
            //make top node the root node for the search
            root = treeBinding.findNodeByKeyPath(topNode);
        }

        //Select the tree items that match the search criteria and expand the
        //tree to display them
        RowKeySet resultRowKeySet = searchTreeNode(root, searchAtt, searchTyp, searchStr);
        RowKeySet disclosedRowKeySet = buildDiscloseRowKeySet(treeBinding, resultRowKeySet);
        groupTree.setSelectedRowKeys(resultRowKeySet);
        groupTree.setDisclosedRowKeys(disclosedRowKeySet);
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(groupTree);
    }

    public void Search() {
        JUCtrlHierBinding treeBinding = null;

        //get handle to tree if it does not exist. If tree component cannot be
        //found in view, exit this function
        if (groupTree == null) {
            this.findTreeInView();
            if (groupTree == null) {
                //tree not found
                log("The tree component could not be found in the view. Please check for naming containers. Search function cancelled");
                return;
            }
        }
        //Get the JUCtrlHierbinding reference from the PageDef
        CollectionModel model = (CollectionModel)groupTree.getValue();
        treeBinding = (JUCtrlHierBinding)model.getWrappedData();

        //Read the attributes to search in from the SelectManyChoice component
        //  String searchAttributeArray[] =             (String[])searchAttributes.toArray(new String[searchAttributes.size()]);

        //Define a node to search in. In this example, the root node is used
        JUCtrlHierNodeBinding root = treeBinding.getRootNodeBinding();

        //However, if the user used the "Show as Top" context menu option to
        //shorten the tree display, then we only search starting from this top
        //mode

        List topNode = (List)groupTree.getFocusRowKey();
        if (topNode != null) {
            //make top node the root node for the search
            root = treeBinding.findNodeByKeyPath(topNode);
        }

        //Select the tree items that match the search criteria and expand the
        //tree to display them
        RowKeySet resultRowKeySet = searchTreeNode(root, searchAttributes, searchType, searchString);
        RowKeySet disclosedRowKeySet = buildDiscloseRowKeySet(treeBinding, resultRowKeySet);
        groupTree.setSelectedRowKeys(resultRowKeySet);
        groupTree.setDisclosedRowKeys(disclosedRowKeySet);
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(groupTree);
    }


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
            }else if (_searchType.equalsIgnoreCase("EQUAL") &&
                       compareString.toUpperCase().equalsIgnoreCase(searchString.toUpperCase())) {
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

    public void onIndexSelection(ActionEvent actionEvent) {
      /*   RichCommandLink linkPressed = (RichCommandLink)actionEvent.getSource();
        Key jboRowKey = (Key)linkPressed.getAttributes().get("jboRowKey");
        AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
        ViewObjectImpl v = am.getAppGrp1();
        v.setWhereClause("grp_nm like '" + linkPressed.getText() + "%'");
        v.executeQuery();
        ViewObjectImpl e = am.getFormGrp1();
        e.setWhereClause("grp_nm like '" + linkPressed.getText() + "%'");
        e.executeQuery();

        */ AdfFacesContext.getCurrentInstance().addPartialTarget(groupTree);
       
    }

   
   

    public void addNewmasterButton(ActionEvent actionEvent) {
  //      try{
/* 
        String grp = "0";
        mode = "A";

        Integer pusrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        Integer pslocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String hoOrgId=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String cldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        

     
        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();

        AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
        ViewObjectImpl e = am.getFormGrp1();
        Row  row1 = e.getCurrentRow();
        row1.setAttribute("GrpIdParent", grp);
        row1.setAttribute("UsrIdCreate", pusrId);
        row1.setAttribute("SlocId", pslocId);
        row1.setAttribute("HoOrgId", hoOrgId);
        row1.setAttribute("CldId", cldId);
        row1.setAttribute("GrpNm", null);
        row1.setAttribute("GrpShortCode", null);
        menuPopup.hide(); */
        
              
        mode = "A";

        Integer pusrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        Integer pslocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String hoOrgId=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String cldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();

        AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
        ViewObjectImpl e = am.getFormGrp1();
        Row  row1 = e.getCurrentRow();
        String gpid=null;
        if(row1.getAttribute("GrpId")!=null)
        gpid = (String)row1.getAttribute("GrpId");
        else{
        String table = "APP$GRP";
        gpid = callStoredFunction(Types.VARCHAR, "APP.FN_APP_GEN_ID(?,?,?,?,?,?)", new Object[] { pslocId,cldId,hoOrgId,orgId,table,null }).toString();
        } 
        System.out.println("group id is "+gpid);
        row1.setAttribute("GrpId", gpid);
        row1.setAttribute("GrpIdParent", "0");
        row1.setAttribute("UsrIdCreate", pusrId);
        row1.setAttribute("SlocId", pslocId);
        row1.setAttribute("HoOrgId", hoOrgId);
        row1.setAttribute("CldId", cldId);
        row1.setAttribute("GrpNm", null);
        row1.setAttribute("GrpShortCode", null);
        
       Integer ret=-1;
       ret =
       (Integer)callStoredFunction(Types.INTEGER, "APP.FN_INS_APP_ITM_GRP_COA(?,?,?,?,?,?,?)", new Object[] { hoOrgId,cldId,pslocId,orgId,gpid,"0",pusrId});
       am.getAppItmGrpCoa1().executeQuery();
        menuPopup.hide();
        
            OperationBinding createCoa = bindings.getOperationBinding("addCoa");
            createCoa.execute();
System.out.println("ret  ------------ "+ret);
        
        
       /*  }catch(NullPointerException ex){
            FacesMessage message2 = new FacesMessage("I Dot't");
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, message2);
        } */

    }

    public void resetButton(ActionEvent actionEvent) {
        AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
        ViewObjectImpl v = am.getAppGrp1();
        v.setWhereClause(null);
        v.executeQuery();
    }

   

    public void setDialogPanelFormBind(RichPanelFormLayout dialogPanelFormBind) {
        this.dialogPanelFormBind = dialogPanelFormBind;
    }

    public RichPanelFormLayout getDialogPanelFormBind() {
        return dialogPanelFormBind;
    }

    public String addChildOnMenu() {
        try{
        mode = "A";
        AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
        ViewObjectImpl v = am.getFormGrp1();
        Row row = v.getCurrentRow();
        String grp = "0";
        if (row.getAttribute("GrpId") != null) {
            grp = row.getAttribute("GrpId").toString();
        }
        Integer pusrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        Integer pslocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String hoOrgId=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String cldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        

        showPopup(addPopup, true);
        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        
       // System.out.println("addChildMenu : "+grp);

        ViewObjectImpl e = am.getFormGrp1();
        Row  row1 = e.getCurrentRow();
        row1.setAttribute("GrpIdParent", grp);
        row1.setAttribute("UsrIdCreate", pusrId);
        row1.setAttribute("SlocId", pslocId);
        row1.setAttribute("HoOrgId", hoOrgId);
        row1.setAttribute("CldId", cldId);
        
        row1.setAttribute("GrpNm", null);
        row1.setAttribute("GrpShortCode", null);
        }catch(NullPointerException es){
            FacesMessage message2 = new FacesMessage("Edit.");
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, message2);
        }
        return null;
    }

    public void setPageFormBind(RichPanelFormLayout pageFormBind) {
        this.pageFormBind = pageFormBind;
    }

    public RichPanelFormLayout getPageFormBind() {
        return pageFormBind;
    }

    public void cancelButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
        am.getAppItmCoaLink1().executeQuery();
        am.getAppGrp1().executeQuery();
        mode = "V";
    }

    public void saveButton(ActionEvent actionEvent) {
        Integer objVal=Integer.parseInt(valuationBind.getValue().toString());;
        System.out.println("Valuation:"+objVal);
        Integer  objPick=Integer.parseInt(pickOrderBind.getValue().toString());
        //  String pickClientId=pickOrderBind.getClientId();
        System.out.println("pick:"+objPick);
        
            
            if(objVal==303&& objPick !=305) {
                System.out.println("in the LIFO Block..");
                System.out.println("pick Client Id:"+pickOrderBind.getClientId());
            FacesMessage message = new FacesMessage(" If Valuation method is LIFO type then Pick Order must also be LIFO type. ");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(pickOrderBind.getClientId(), message);
            return;
            }
            if(objVal==304 && objPick !=306) {
                System.out.println("in the FIFO Block....");
                System.out.println("pick Client Id:"+pickOrderBind.getClientId());
                FacesMessage message = new FacesMessage(" If Valuation method is FIFO type then Pick Order must also be FIFO type.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(pickOrderBind.getClientId(), message);
                return;
            }
            
            
            
    
        AdfFacesContext.getCurrentInstance().addPartialTarget(coaNmBind);
        System.out.println("save action called");
     //   try{
        System.out.println("Saving Grp");
        AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
        BindingContainer bindings = getBindings();
        Integer pslocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String hoOrgId=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String cldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String orgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        
        String grpId=null;
        
        /*    
        if(bindGrpNmVar.getValue() != null)
        {
        OperationBinding op = bindings.getOperationBinding("isGroupNameValid");
        op.getParamsMap().put("grpNm",bindGrpNmVar.getValue().toString());
        op.execute();
        Boolean obj = (Boolean)op.getResult();
          if(obj)
          {
            FacesMessage message2 = new FacesMessage("Two or more Group Siblings should not have same Name.");
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message2);
            return;
          }
        }
        
        
        if(bindShortCodeVar.getValue() != null)
        {
            System.out.println("Calling...");
        OperationBinding op = bindings.getOperationBinding("isShortDescValid");
        op.execute();
        Boolean obj = (Boolean)op.getResult();
        if(obj)
          {
            FacesMessage message2 = new FacesMessage("Two or more Group Short Code should not be Same.");
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message2);
            return;
          }
        } */
        
        if (mode.equals("A")) {
            System.out.println("Add mode");
            ViewObjectImpl v = am.getFormGrp1();
            Row row = v.getCurrentRow();
           // String currGrpId = row.getAttribute("GrpIdParent").toString();
          //  System.out.println("currGrpId 0--------------save0------"+currGrpId);
            //String table = "APP$GRP";
            //grpId = callStoredFunction(VARCHAR, "APP.FN_APP_GEN_ID(?,?,?,?,?,?)", new Object[] { pslocId,cldId,hoOrgId,orgid,table,currGrpId }).toString();
            //System.out.println("new grp id --->  "+grpId);
           // row.setAttribute("GrpId", grpId);
           // setCoa(grpId);
           //SET_COA();
        }
        
        if(mode.equals("E"))
        {
            //setModifyCoa();
           // SET_COA();
         }
        
        DCIteratorBinding parentIter =  (DCIteratorBinding)bindings.get("AppGrp1Iterator");
        DCIteratorBinding formIter = (DCIteratorBinding)bindings.get("FormGrp1Iterator");
        Key parentKey = null;
        Key formKey =null;
        String formGrpId = null;
        String formGrpNm = null;
        if(parentIter.getCurrentRow() != null){
        parentKey = parentIter.getCurrentRow().getKey();
        System.out.println("My-------------"+parentKey);
        }
     //   try{
            ViewObjectImpl v = am.getFormGrp1();
            Row row = v.getCurrentRow();
            formKey =formIter.getCurrentRow().getKey();
            System.out.println("My Form-----------"+formKey);
            formGrpId = row.getAttribute("GrpId").toString();
            formGrpNm= row.getAttribute("GrpNm").toString();
            
            System.out.println("form goupt id is ----------"+formGrpId);
       /*  }catch(NullPointerException npe){
            System.out.println("form goupt eRROR IS ="+npe);
        } */
        System.out.println("formGrpId--------"+formGrpId);
        Row[] fr=am.getAppItmCoaLink1().getAllRowsInRange();
        System.out.println(fr.length);

        
        if(fr.length>0)
        {
                HashSet<String> h=new HashSet<String>();
            for(Row r:fr)
            {
                if(r.getAttribute("CoaNm")!=null && r.getAttribute("CoaNm")!=""){
                System.out.println("Hello----------------------Hi");}
                else
                {
//                        FacesMessage message2 = new FacesMessage("Null Not Allowed "+r.getAttribute("AttNm").toString());
//                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
//                        FacesContext.getCurrentInstance().addMessage(null, message2);
                    h.add(r.getAttribute("AttNm").toString());
                    System.out.println("Size of HashSet for null Values "+h.size());
                    }
                }
                if(h.size()>0){
                     System.out.println("HashSet="+h);
                     
                    StringBuilder msg =
                        new StringBuilder("<html><body><p>Please select COA for : <b>" + h +
                                          "</b> </p>");//
                    msg.append("</body></html>");  
                    String show=msg.toString();
                FacesMessage message2 = new FacesMessage(show);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message2);
            }
            else
                {
                        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                        operationBinding.execute();
                    System.out.println("Error on commit="+operationBinding.getErrors());
                        BindingContainer bindings1 = getBindings();
                        OperationBinding operationBinding1 = bindings1.getOperationBinding("Rollback");
                        operationBinding1.execute();
                        BindingContainer bindings2 = getBindings();
                        OperationBinding operationBinding2 = bindings2.getOperationBinding("Execute");
                        operationBinding2.execute();
                        OperationBinding operationBinding3 = bindings2.getOperationBinding("Execute1");
                        operationBinding3.execute();
                        
                        ViewObjectImpl e = am.getAppGrp1();
                        e.executeQuery();
                           
                        ViewObjectImpl f = am.getAppGrp2();
                        f.executeQuery();
                        am.getFormGrp1().executeQuery();
                        am.getLovGroupIdParent().executeQuery();
                        //  System.out.println("parentKey--------"+parentKey+"formKey-------"+formKey);
                        if (parentKey != null && formKey != null) {
                          //   parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                            /*  if(formIter.getRowSetIterator().getRow(formKey) != null){ 
                                 System.out.println(" inside form key");
                             formIter.setCurrentRowWithKey(formKey.toStringFormat(true)); */
                             System.out.println("----------------------formGrpId"+formGrpId);
                           // ViewObjectImpl v = am.getFormGrp1();
                            v.setWhereClause(null);
                            v.executeQuery();
                            v.setWhereClause("Grp_Id = '" + formGrpId + "' AND CLD_ID= '"+cldId+"' AND HO_ORG_ID='"+hoOrgId+"' AND SLOC_ID ="+pslocId);
                            System.out.println("Expr="+v.getQuery());
                            v.executeQuery();
                             //}
                            System.out.println("finding row");
                            findRowInTable("GrpNm", "EQUAL", formGrpNm);
                            System.out.println("roe finding complete");
                         }
                        mode = "V";
                    }
        }
        else
        {
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();
                System.out.println("Error on commit here="+operationBinding.getErrors());
                BindingContainer bindings1 = getBindings();
                OperationBinding operationBinding1 = bindings1.getOperationBinding("Rollback");
                operationBinding1.execute();
                BindingContainer bindings2 = getBindings();
                OperationBinding operationBinding2 = bindings2.getOperationBinding("Execute");
                operationBinding2.execute();
                OperationBinding operationBinding3 = bindings2.getOperationBinding("Execute1");
                operationBinding3.execute();
                
                ViewObjectImpl e = am.getAppGrp1();
                e.executeQuery();

                ViewObjectImpl f = am.getAppGrp2();
                f.executeQuery();
                am.getFormGrp1().executeQuery();
                am.getLovGroupIdParent().executeQuery();
                //  System.out.println("parentKey--------"+parentKey+"formKey-------"+formKey);
                if (parentKey != null && formKey != null) {
                  //   parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    /*  if(formIter.getRowSetIterator().getRow(formKey) != null){ 
                         System.out.println(" inside form key");
                     formIter.setCurrentRowWithKey(formKey.toStringFormat(true)); */
                     System.out.println("----------------------formGrpId"+formGrpId);
                   // ViewObjectImpl v = am.getFormGrp1();
                    v.setWhereClause(null);
                    v.executeQuery();
                    v.setWhereClause("Grp_Id = '" + formGrpId + "' AND CLD_ID= '"+cldId+"' AND HO_ORG_ID='"+hoOrgId+"' AND SLOC_ID ="+pslocId);
                    v.executeQuery();
                     //}
                    findRowInTable("GrpNm", "EQUAL", formGrpNm);
                 }
                mode = "V";
            }
      /*   }catch(NullPointerException rd){
            FacesMessage message2 = new FacesMessage("Lhfycs Lsjgcghs");
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, message2);
        } */
       
    
        
        }

    public void setMenuPopup(RichPopup menuPopup) {
        this.menuPopup = menuPopup;
    }

    public RichPopup getMenuPopup() {
        return menuPopup;
    }
    
    public void actvValueChangeListener(ValueChangeEvent vce) {
        AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
        if (vce.getNewValue() != null) {
            String oldval = vce.getOldValue().toString();
     
            String newVal = vce.getNewValue().toString();
    
            ViewObjectImpl v1 = am.getFormGrp1();
            Row row = v1.getCurrentRow();
            if (newVal.equalsIgnoreCase("true")) {
                row.setAttribute("InactvDt", null);
                row.setAttribute("InactvResn", null);
                inactiveRsnBind.setValue("");
                AdfFacesContext.getCurrentInstance().addPartialTarget(inactiveRsnBind);
            } else if (newVal.equalsIgnoreCase("false")) {
                Date dt = (Date)Date.getCurrentDate();
                row.setAttribute("InactvDt", dt);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(inactiveRsnBind);
        }
    }

    public void grpNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object!=null){
      OperationBinding ob=getBindings().getOperationBinding("nameValidator");
          ob.getParamsMap().put("Pname", object.toString());
          ob.execute();
          String flag=null;
          if(ob.getErrors().isEmpty())
          {
              flag=ob.getResult().toString();
          }
          adf_log.info("flag value is "+flag);
              if(flag.equalsIgnoreCase("N"))
              {
                        throw new ValidatorException(new FacesMessage("Two or more Group Siblings should not have same Name."));
              } 
      
                   String quotId=object.toString();
                      int openB=0;
                      int closeB=0;
                      boolean closeFg=false;
                      
                      char[] xx=quotId.toCharArray();
                      
                      for(char c:xx){
                      
                          if(c=='('){
                              
                              openB=openB+1;
                              
                          }else if(c==')'){
                              
                              closeB=closeB+1;
                              
                          }
                      
                      if(closeB>openB){
                          closeFg=true; /**1.no. of closed brackets will not be more than open brackets at any given time.*/
                      }
                      }
                      
                      /**2.if openB=0 then no. of closing and opening brackets equal ||
                      * 3.opening bracket must always come before closing brackets
                      * 4.closing brackets must not come before first occurrence of openning bracket
                      */
                      if(openB!=closeB ||closeFg==true||(quotId.lastIndexOf("(")>quotId.lastIndexOf(")")) ||(quotId.indexOf(")")<quotId.indexOf("(")) ){
                          String msg2=resolvEl("#{bundle['MSG.7']}").toString();
                          FacesMessage message2 = new FacesMessage(msg2);
                          message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                          throw new ValidatorException(message2);
                      }
                      
                      /**5.check for empty brackets
                       */
                      if(quotId.contains("()")){
                              String msg2=resolvEl("#{bundle['MSG.170']}").toString();
                              FacesMessage message2 = new FacesMessage(msg2);
                              message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                              throw new ValidatorException(message2);
                      }
                      /**check for to dot not comes together
                       */
                          if(quotId.contains("..")){
                                  String msg2=resolvEl("#{bundle['MSG.276']}").toString();
                                  FacesMessage message2 = new FacesMessage(msg2);
                                  message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                  throw new ValidatorException(message2);
                          } 
                      /**check for to dot not comes together
                       */
                          if(quotId.contains("--")){
                                  String msg2=resolvEl("#{bundle['MSG.277']}").toString();
                                  FacesMessage message2 = new FacesMessage(msg2);
                                  message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                  throw new ValidatorException(message2);
                          } 
                      /**  check for wrong combo
                       */
                          if(quotId.contains("(.")||quotId.contains("(-")||quotId.contains("-)")){
                              String msg2=resolvEl("#{bundle['MSG.59']}").toString();
                              FacesMessage message2 = new FacesMessage(msg2);
                              message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                              throw new ValidatorException(message2);
                          }
                          openB=0;
                          closeB=0;
                          closeFg=false; 
        
        
                          /**check for valid cornm .. Starts with character. can have dots .No two dots can be adjacent.
                           * 
                           */
                                       
                  //check for valid country name.. Starts with character. can have dots .No two dots can be adjacent.
                  String expression = "^(?:(?>[A-Za-z0-9 )]+)(?:\\_|\\-|\\(|\\.|\\)|\\:|\\#|\\/|\\\\(?!\\_|\\-|\\:|\\#|\\/|\\\\|$))?)*$";
                          CharSequence inputStr = quotId;
                          Pattern pattern = Pattern.compile(expression);
                          Matcher matcher = pattern.matcher(inputStr);
                          String error=resolvEl("#{bundle['MSG.82']}").toString();
                          
                          if (matcher.matches()) {
                          }else {
                                         throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
                          }
                 
                  }
        
       System.out.println("Object .. "+object);
        OperationBinding op = getBindings().getOperationBinding("isGroupNameValid");
        op.getParamsMap().put("grpNm",object.toString());
        op.execute();
        if(op.getErrors().size()>0)
            System.out.println("error on chk="+op.getErrors());
        Boolean obj = (Boolean)op.getResult();
        
        System.out.println("Result : "+obj);
        
        if(obj)
        {
          throw new ValidatorException(new FacesMessage("Two or more Group Siblings should not have same Name."));
          } 
        System.out.println("Valid 7 checked");

    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setInactiveRsnBind(RichInputText inactiveRsnBind) {
        this.inactiveRsnBind = inactiveRsnBind;
    }

    public RichInputText getInactiveRsnBind() {
        return inactiveRsnBind;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
         count = (Integer)getBindings().getOperationBinding("on_load_page").execute();
        return count;
    }

    public String addCOAAction() {
        try{
        // Add event code here...
        AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
        ViewObjectImpl vo = am.getAppItmCoaLink1();
        ViewObjectImpl GrpVo = am.getFormGrp1();
        Row grpRow = GrpVo.getCurrentRow();
        ViewObjectImpl itmCoaVo = am.getAppItmGrpCoa1();
       
        RowSetIterator set = vo.createRowSetIterator(null);
        while(set.hasNext())
        { 
            
           // System.out.println("count...");
                
            Row row = set.next();
            Row coa = itmCoaVo.createRow();
            coa.setAttribute("CoaFor",row.getAttribute("AttId"));
            coa.setAttribute("HoOrgId",grpRow.getAttribute("HoOrgId"));
            coa.setAttribute("OrgId",grpRow.getAttribute("OrgId"));
            coa.setAttribute("SlocId",grpRow.getAttribute("SlocId"));
            coa.setAttribute("CldId",grpRow.getAttribute("CldId"));
           // itmCoaVo.insertRow(coa);
        }
        set.closeRowSetIterator();
        }catch(NullPointerException rsrx){
            FacesMessage message2 = new FacesMessage("Coa");
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, message2);
        }
        return null;
    }
    
    // This method not in use.
    private void setCoa(String grpId)
    {
        try{
        AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
        ViewObjectImpl vo = am.getAppItmCoaLink1();
        ViewObjectImpl GrpVo = am.getFormGrp1();
        Row grpRow = GrpVo.getCurrentRow();
        ViewObjectImpl itmCoaVo = am.getAppItmGrpCoa1();
        String userId = resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString();
        RowSetIterator set = vo.createRowSetIterator(null);
        while(set.hasNext())
        {
            Row row = set.next();
            Row coa = itmCoaVo.createRow();
            coa.setAttribute("CoaFor",row.getAttribute("AttId"));
            coa.setAttribute("HoOrgId",grpRow.getAttribute("HoOrgId"));
            coa.setAttribute("OrgId",grpRow.getAttribute("OrgId"));
            coa.setAttribute("SlocId",grpRow.getAttribute("SlocId"));
            coa.setAttribute("CldId",grpRow.getAttribute("CldId"));
            coa.setAttribute("GrpId",grpId);
            coa.setAttribute("CoaId",row.getAttribute("CoaId"));
            coa.setAttribute("UsrIdCreate",Integer.parseInt(userId));
            coa.setAttribute("UsrIdCreateDt",new Timestamp(System.currentTimeMillis()));
            itmCoaVo.insertRow(coa);
        }
        set.closeRowSetIterator();
        }catch(NullPointerException sgfg){
            FacesMessage message2 = new FacesMessage("The Group was");
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, message2);
        }
    }
    
    // This method not in use.
    private void setModifyCoa()
    {
        try{
        AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
        ViewObjectImpl vo = am.getAppItmCoaLink1();
        ViewObjectImpl GrpVo = am.getFormGrp1();
        Row grpRow = GrpVo.getCurrentRow();
        ViewObjectImpl itmCoaVo = am.getAppItmGrpCoa1();
        String userId = resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString();
        
        RowSetIterator set = vo.createRowSetIterator(null);
        while(set.hasNext())
        {
            Row row = set.next();
            RowQualifier rq = new RowQualifier(itmCoaVo);
            rq.setWhereClause("CldId = '"+grpRow.getAttribute("CldId")+"' AND SlocId = "+grpRow.getAttribute("SlocId")+" AND HoOrgId = '"+grpRow.getAttribute("HoOrgId")+"' AND OrgId = '"+grpRow.getAttribute("OrgId")+"' AND CoaFor = "+row.getAttribute("AttId")+" AND GrpId = '"+grpRow.getAttribute("GrpId")+"' ");
            Row rows[] = itmCoaVo.getFilteredRows(rq);
         // System.out.println("rows : "+rows.length);
           if(rows !=null && rows.length == 1)
            {  // System.out.println(row.getAttribute("CoaId"));
                rows[0].setAttribute("CoaId", row.getAttribute("CoaId"));
                rows[0].setAttribute("UsrIdMod",Integer.parseInt(userId));
                rows[0].setAttribute("UsrIdModDt",new Timestamp(System.currentTimeMillis()));
            }
            else if(rows !=null && rows.length == 0)
            {
                Row coa = itmCoaVo.createRow();
                coa.setAttribute("CoaFor",row.getAttribute("AttId"));
                coa.setAttribute("HoOrgId",grpRow.getAttribute("HoOrgId"));
                coa.setAttribute("OrgId",grpRow.getAttribute("OrgId"));
                coa.setAttribute("SlocId",grpRow.getAttribute("SlocId"));
                coa.setAttribute("CldId",grpRow.getAttribute("CldId"));
                coa.setAttribute("GrpId",grpRow.getAttribute("GrpId"));
                coa.setAttribute("CoaId",row.getAttribute("CoaId"));
                coa.setAttribute("UsrIdCreate",Integer.parseInt(userId));
                coa.setAttribute("UsrIdCreateDt",new Timestamp(System.currentTimeMillis()));
                itmCoaVo.insertRow(coa);
            }
        }
        set.closeRowSetIterator();
        }catch(NullPointerException tydy){
            FacesMessage message2 = new FacesMessage("different Organisation");
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, message2);
        }
    }

    public void SlsValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue().equals(true)) {
            capitalFlg.setValue(false);       
        }
        else
        {  capitalFlg.setValue(true);    
            }
        AdfFacesContext.getCurrentInstance().addPartialTarget(capitalFlg);
    }

    public void setSlsFlg(RichSelectBooleanCheckbox slsFlg) {
        this.slsFlg = slsFlg;
    }

    public RichSelectBooleanCheckbox getSlsFlg() {
        return slsFlg;
    }

    public void setCapitalFlg(RichSelectBooleanCheckbox capitalFlg) {
        this.capitalFlg = capitalFlg;
    }

    public RichSelectBooleanCheckbox getCapitalFlg() {
        return capitalFlg;
    }

    public void setPurFlg(RichSelectBooleanCheckbox purFlg) {
        this.purFlg = purFlg;
    }

    public RichSelectBooleanCheckbox getPurFlg() {
        return purFlg;
    }

    public void purValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue().equals(true)) {
            capitalFlg.setValue(false);       
        }
        else
        {  capitalFlg.setValue(true);    
            }
        AdfFacesContext.getCurrentInstance().addPartialTarget(capitalFlg);
    }

    public void setWipFlg(RichSelectBooleanCheckbox wipFlg) {
        this.wipFlg = wipFlg;
    }

    public RichSelectBooleanCheckbox getWipFlg() {
        return wipFlg;
    }

    public void wipValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue().equals(true)) {
            capitalFlg.setValue(false);
        }
        else
        {  capitalFlg.setValue(true);    
            }
        AdfFacesContext.getCurrentInstance().addPartialTarget(capitalFlg);
    }

    public void setCashPurFlg(RichSelectBooleanCheckbox cashPurFlg) {
        this.cashPurFlg = cashPurFlg;
    }

    public RichSelectBooleanCheckbox getCashPurFlg() {
        return cashPurFlg;
    }

    public void cashPurFlgValueListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue().equals(true)) {
            capitalFlg.setValue(false);
        }
    }

    public void capitalFlgValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue().equals(true)) {
            slsFlg.setValue(false);
            purFlg.setValue(false);
            wipFlg.setValue(false);
            cashPurFlg.setValue(false);
       }
        AdfFacesContext.getCurrentInstance().addPartialTarget(slsFlg);
        AdfFacesContext.getCurrentInstance().addPartialTarget(purFlg);
        AdfFacesContext.getCurrentInstance().addPartialTarget(wipFlg);
        AdfFacesContext.getCurrentInstance().addPartialTarget(capitalFlg);
    }

    public void setStockableFlg(RichSelectBooleanCheckbox stockableFlg) {
        this.stockableFlg = stockableFlg;
    }

    public RichSelectBooleanCheckbox getStockableFlg() {
        return stockableFlg;
    }

    public void stockableChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue().equals(true)) {
            srvcFlg.setValue(false);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(srvcFlg);
    }

    public void setSrvcFlg(RichSelectBooleanCheckbox srvcFlg) {
        this.srvcFlg = srvcFlg;
    }

    public RichSelectBooleanCheckbox getSrvcFlg() {
        return srvcFlg;
    }

    public void srvcChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue().equals(true)) {
            stockableFlg.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(stockableFlg);
        }
    }
    
    
    // This method not in use.
    private void setDefaultValueToCheckBoxes()
    {
        List<UIComponent> checkBoxes = checkBoxPanel.getChildren();
        for(UIComponent ui:checkBoxes)
        {
           if(ui instanceof RichSelectBooleanCheckbox)
           {
               RichSelectBooleanCheckbox UIC = (RichSelectBooleanCheckbox)ui;
             if(!UIC.isDisabled())
             {
                  Object ob = UIC.getValue();
                   if(ob == null)
                   { UIC.setValue("N");}
                }
           }
        }
    }

    public void setCheckBoxPanel(RichPanelFormLayout checkBoxPanel) {
        this.checkBoxPanel = checkBoxPanel;
    }

    public RichPanelFormLayout getCheckBoxPanel() {
        return checkBoxPanel;
    }
    
    // This method not in use.
    private void SET_COA()
    {
        try{
        AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
        
        ViewObjectImpl GrpVo = am.getFormGrp1();
        ViewObjectImpl linkvo = am.getAppItmCoaLink1();
        ViewObjectImpl itmCoaVo = am.getAppItmGrpCoa1();
       
        Row grpRow = GrpVo.getCurrentRow();
        String userId = resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString();
        
        RowSetIterator set = linkvo.createRowSetIterator(null);
        while(set.hasNext())
        {
            Row row = set.next();
            RowQualifier rq = new RowQualifier(itmCoaVo);
            rq.setWhereClause("CldId = '"+grpRow.getAttribute("CldId")+"' AND SlocId = "+grpRow.getAttribute("SlocId")+" AND HoOrgId = '"+grpRow.getAttribute("HoOrgId")+"' AND OrgId = '"+grpRow.getAttribute("OrgId")+"' AND CoaFor = "+row.getAttribute("AttId")+" AND GrpId = '"+grpRow.getAttribute("GrpId")+"' ");
            Row rows[] = itmCoaVo.getFilteredRows(rq);
         
         //Update COA
           if(rows !=null && rows.length == 1)
            {  
                rows[0].setAttribute("CoaId", row.getAttribute("CoaId"));
                rows[0].setAttribute("UsrIdMod",Integer.parseInt(userId));
                rows[0].setAttribute("UsrIdModDt",new Timestamp(System.currentTimeMillis()));
            }
            
            //Insert New Row
            else if(rows !=null && rows.length == 0)
           {
               Row coa = itmCoaVo.createRow();
               coa.setAttribute("CoaFor",row.getAttribute("AttId"));
               coa.setAttribute("HoOrgId",grpRow.getAttribute("HoOrgId"));
               coa.setAttribute("OrgId",grpRow.getAttribute("OrgId"));
               coa.setAttribute("SlocId",grpRow.getAttribute("SlocId"));
               coa.setAttribute("CldId",grpRow.getAttribute("CldId"));
               coa.setAttribute("GrpId",grpRow.getAttribute("GrpId"));
               coa.setAttribute("CoaId",row.getAttribute("CoaId"));
               coa.setAttribute("UsrIdCreate",Integer.parseInt(userId));
               coa.setAttribute("UsrIdCreateDt",new Timestamp(System.currentTimeMillis()));
               itmCoaVo.insertRow(coa);
           }
            //Remove Row
         else if(rows !=null && rows.length == 1 && (row.getAttribute("CoaId") == null || row.getAttribute("CoaId").toString().isEmpty()))
             { rows[0].remove(); }
            
          
        }
        set.closeRowSetIterator();
        }catch(NullPointerException hfjhj){
            FacesMessage message2 = new FacesMessage("Please Login");
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, message2);
        }
    }

    public void CoaNmValueChangeListener(ValueChangeEvent valueChangeEvent) {
        try{
        if(valueChangeEvent.getNewValue()!=null){
         // Add event code here...
        AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
        ViewObjectImpl GrpVo = am.getFormGrp1();
        ViewObjectImpl linkvo = am.getAppItmCoaLink1();
        ViewObjectImpl itmCoaVo = am.getAppItmGrpCoa1();
        
       // System.out.println(linkvo.getCurrentRow().getAttribute("AttId") +"  --->");
       // System.out.println(valueChangeEvent.getNewValue() +"  valueChangeEvent.getNewValue()");
       // System.out.println(valueChangeEvent.getOldValue() +"  valueChangeEvent.getOldValue()");
        
        Row grpRow = GrpVo.getCurrentRow();
        String userId = resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString();
        
        String newValue = (String)valueChangeEvent.getNewValue();
        Object oldValue = valueChangeEvent.getOldValue();
        System.out.println("My New Value Is "+newValue.length());
        System.out.println("My Old Value Is "+oldValue);
        if(newValue.length() == 0 && oldValue != null)
        {  // System.out.println("remove");
            RowQualifier rq = new RowQualifier(itmCoaVo);
            rq.setWhereClause("CldId = '"+grpRow.getAttribute("CldId")+"' AND SlocId = "+grpRow.getAttribute("SlocId")+" AND HoOrgId = '"+grpRow.getAttribute("HoOrgId")+"' AND CoaFor = "+linkvo.getCurrentRow().getAttribute("AttId")+" AND GrpId = '"+grpRow.getAttribute("GrpId")+"' ");
            Row rows[] = itmCoaVo.getFilteredRows(rq);
            System.out.println(rows.length);
            if(rows != null && rows.length >=1)
            { rows[0].remove();
              
              nowRows=rows.length;
              System.out.println(nowRows);
              //rows[0].setAttribute("CoaId", null);
              }
            
        }
        
        else if(newValue.length() > 0 && oldValue == null)
        {  
            System.out.println("Maine yahaa hu");
            Row coa = itmCoaVo.createRow();
            System.out.println(linkvo.getCurrentRow().getAttribute("AttId"));
            coa.setAttribute("CoaFor",linkvo.getCurrentRow().getAttribute("AttId"));
            System.out.println(grpRow.getAttribute("HoOrgId"));
            coa.setAttribute("HoOrgId",grpRow.getAttribute("HoOrgId"));
            System.out.println(grpRow.getAttribute("OrgId"));
            coa.setAttribute("OrgId",grpRow.getAttribute("OrgId"));
            System.out.println(grpRow.getAttribute("SlocId"));
            coa.setAttribute("SlocId",grpRow.getAttribute("SlocId"));
            System.out.println(grpRow.getAttribute("CldId"));
            coa.setAttribute("CldId",grpRow.getAttribute("CldId"));
            System.out.println(grpRow.getAttribute("GrpId"));
            coa.setAttribute("GrpId",grpRow.getAttribute("GrpId"));
            System.out.println(getCoaId(newValue));
            coa.setAttribute("CoaId",getCoaId(newValue));
            System.out.println(Integer.parseInt(userId));
            coa.setAttribute("UsrIdCreate",Integer.parseInt(userId));
            System.out.println(new Timestamp(System.currentTimeMillis()));
            coa.setAttribute("UsrIdCreateDt",new Timestamp(System.currentTimeMillis()));
            itmCoaVo.insertRow(coa);
         }
        
        else if(newValue.length() > 0 && oldValue != null)
        {   // System.out.println("Update..");
            RowQualifier rq = new RowQualifier(itmCoaVo);
            rq.setWhereClause("CldId = '"+grpRow.getAttribute("CldId")+"' AND SlocId = "+grpRow.getAttribute("SlocId")+" AND HoOrgId = '"+grpRow.getAttribute("HoOrgId")+"' AND CoaFor = "+linkvo.getCurrentRow().getAttribute("AttId")+" AND GrpId = '"+grpRow.getAttribute("GrpId")+"' ");
            Row rows[] = itmCoaVo.getFilteredRows(rq);
            if(rows != null && rows.length >=1)
            {     
                  rows[0].setAttribute("CoaId",getCoaId(newValue));
                  rows[0].setAttribute("UsrIdMod",Integer.parseInt(userId));
                  rows[0].setAttribute("UsrIdModDt",new Timestamp(System.currentTimeMillis()));

              }
        }
        }else{
            FacesMessage message2 = new FacesMessage("to Edit.");
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, message2);
        }
        }catch(NullPointerException exe){
                FacesMessage message2 = new FacesMessage("Organisation to Edit.");
                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(null, message2);
            }
   
         
    }
    
    
    private Integer getCoaId(String CoaNm)
    {
        AppGrpdefAMImpl am = (AppGrpdefAMImpl)resolvElDC("AppGrpdefAMDataControl");
        ViewObjectImpl coaVo = am.getCoaVo();
        Row rws[]=coaVo.getFilteredRows("CoaNm",CoaNm);
         
        if(rws != null && rws.length >=1)
        {  
            return (Integer)rws[0].getAttribute("CoaId");
        }
        return 0;
    }

    public void costMthValueChngListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
     //Object obj=valueChangeEvent.getNewValue();
     //Integer obj1=305;
     //System.out.println("Entered value is:"+obj);
      //pickOrderBind.setValue("LIFO");
    }

    public void setPickOrderBind(RichSelectOneChoice pickOrderBind) {
        this.pickOrderBind = pickOrderBind;
    }

    public RichSelectOneChoice getPickOrderBind() {
        return pickOrderBind;
    }

    public void setCoaNmBindVar(RichOutputText coaNmBindVar) {
        this.coaNmBindVar = coaNmBindVar;
    }

    public RichOutputText getCoaNmBindVar() {
        return coaNmBindVar;
    }

    public void setBindGrpNmVar(RichInputText bindGrpNmVar) {
        this.bindGrpNmVar = bindGrpNmVar;
    }

    public RichInputText getBindGrpNmVar() {
        return bindGrpNmVar;
    }

    public void setBindShortCodeVar(RichInputText bindShortCodeVar) {
        this.bindShortCodeVar = bindShortCodeVar;
        }

    public RichInputText getBindShortCodeVar() {
        return bindShortCodeVar;
    }

    public void ShortDescValidator(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        
    }

    public void shtDescValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if(object != null)
        {
           
            BindingContainer bindings = getBindings();     
        OperationBinding op = bindings.getOperationBinding("isShortDescCorrect");
        op.getParamsMap().put("shortCode", object.toString().trim());
        op.execute();
        Object obj = op.getResult();
        System.out.println("Object : "+obj);
        if(obj != null)
        {
        Boolean b = (Boolean)obj;
            
        if(b){
        String msg2=resolvEl("#{bundle['MSG.170']}").toString();
        FacesMessage message2 = new FacesMessage("Two or more Group Short Code should not be Same.");
        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message2);
        }
        }
        }
    }

    public void setCoaNmBind(RichInputListOfValues coaNmBind) {
        this.coaNmBind = coaNmBind;
    }

    public RichInputListOfValues getCoaNmBind() {
        return coaNmBind;
    }


    public void setValuationBind(RichSelectOneChoice valuationBind) {
        this.valuationBind = valuationBind;
    }

    public RichSelectOneChoice getValuationBind() {
        return valuationBind;
    }
//    public void ValutionValidation(){
//        System.out.println("In the ValuationValidation method");
//        Integer objVal=Integer.parseInt(valuationBind.getValue().toString());;
//        System.out.println("Valuation:"+objVal);
//        Integer  objPick=Integer.parseInt(pickOrderBind.getValue().toString());
//      //  String pickClientId=pickOrderBind.getClientId();
//        System.out.println("pick:"+objPick);
//        if(objVal!=null && objPick!=null) {
//            if(objVal==303&& objPick !=305) {
////                FacesMessage message = new FacesMessage(" If Valuation is LIFO Type then Pick Order Must Also be LIFO ");   
////                     message.setSeverity(FacesMessage.SEVERITY_ERROR);   
////                     FacesContext fc = FacesContext.getCurrentInstance();   
////                     fc.addMessage(pickOrderBind.getClientId(), message); 
//                 throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"If Valuation is LIFO Type then Pick Order Must Also be LIFO",null));
//                     //return "true";
//            }
//            if(objVal==304 && objPick !=306) {
////               
//                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"If Valuation is FIFO Type then Pick Order Must Also be FIFO",null));
//                   //return "true";       
//            }
//           
//        }
//        //return "false";
//    }

//    public void ValuationValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
//         //Add event code here...aa
//        System.out.println("in the  Valuation Validation...");
//        Integer val=Integer.parseInt(object.toString());
//        Integer pick=Integer.parseInt(pickOrderBind.getValue().toString());
//        if(val!=null&& pick!=null )
//        {
//        if(val==303&& pick !=305) {
//            
//            String msg2=" If Valuation is LIFO Type then Pick Order Must Also be LIFO ";//resolvEl("#{bundle['MSG.170']}").toString();
//            FacesMessage message2 = new FacesMessage(" If Valuation is LIFO Type then Pick Order Must Also be LIFO ");
//            message2.setSeverity(FacesMessage.SEVERITY_INFO);
//            throw new ValidatorException(message2);
//        /* FacesMessage message = new FacesMessage(" If Valuation is LIFO Type then Pick Order Must Also be LIFO ");
//        message.setSeverity(FacesMessage.SEVERITY_INFO);
//        FacesContext fc = FacesContext.getCurrentInstance();
//        fc.addMessage(valuationBind.getClientId(), message); */
//       // return;
//        }
//        if(val==304 && pick !=306) {
//            String msg2=" If Valuation is FIFO Type then Pick Order Must Also be FIFO ";//resolvEl("#{bundle['MSG.170']}").toString();
//            FacesMessage message2 = new FacesMessage(" If Valuation is FIFO Type then Pick Order Must Also be FIFO ");
//            message2.setSeverity(FacesMessage.SEVERITY_INFO);
//            throw new ValidatorException(message2);
///*             FacesMessage message = new FacesMessage(" If Valuation is FIFO Type then Pick Order Must Also be FIFO ");
//            message.setSeverity(FacesMessage.SEVERITY_INFO);
//            FacesContext fc = FacesContext.getCurrentInstance();
//            fc.addMessage(valuationBind.getClientId(), message);
//            return; */
//        }
//    }
//   }
}
