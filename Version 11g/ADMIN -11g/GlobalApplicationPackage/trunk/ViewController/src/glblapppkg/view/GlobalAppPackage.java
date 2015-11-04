package glblapppkg.view;

import glblapppkg.model.module.GlblAppPkgAMImpl;

import glblapppkg.model.view.GlblAppPkgDMLVOImpl;

import java.sql.SQLException;

import java.util.Iterator;

import java.util.List;

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

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.server.ViewObjectImpl;

import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import javax.faces.context.FacesContext;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.RowSet;
import oracle.jbo.VariableValueManager;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class GlobalAppPackage {


    private RichInputText searchText;
    private RichTreeTable treeBind;
    private RichPopup pop;
    private RichPopup delPop;
    private Row row = null;
    private RichInputText idTextBind;
    private RichInputText nameTextBind;

    private Integer count;
    private RichSelectOneChoice parentIdBinding;
    private RichSelectOneChoice addparentIdBinding;
    private String searchString = "";
    private String searchType = "CONTAIN";
    private String searchAttributes = "GlblAppliStructNm";
    ADFLogger logger = ADFLogger.createADFLogger(this.getClass());
    private RichInputText structIdBinding;

    private void log(String message) {
        logger.log(ADFLogger.WARNING, message);
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
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


    public void setSearchText(RichInputText searchText) {
        this.searchText = searchText;
    }

    public RichInputText getSearchText() {
        return searchText;
    }

    public void searchButton(ActionEvent actionEvent) {
        /*   GlblAppPkgAMImpl am = (GlblAppPkgAMImpl)resolvElDC("GlblAppPkgAMDataControl");
        ViewObjectImpl vo = am.getAppGlblAppliStruct1();
        VariableValueManager vm = vo.ensureVariableManager();

        if (searchText.getValue() != null && searchText.getValue().toString().trim().length() > 0) {
            String structname = searchText.getValue().toString().trim();

            vm.setVariableValue("bindStructParentId", structname);
           // vo.setWhereClause("upper(GLBL_APPLI_STRUCT_NM) LIKE '" + structname.toUpperCase() + "%'");

            vo.executeQuery();
            structname = "";
        } else {
            vm.setVariableValue("bindStructParentId", null);

           // vo.setWhereClause("GLBL_APPLI_STRUCT_ID_PARENT = '0'");
            vo.executeQuery();
        } */

        Search();

        GlblAppPkgAMImpl am = (GlblAppPkgAMImpl)resolvElDC("GlblAppPkgAMDataControl");
        am.getAppGlblAppliStruct1().getCurrentRow();

        ViewObjectImpl v = am.getGlblAppPkgDML1();

        v.setWhereClause(null);
        v.executeQuery();

        DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("AppGlblAppliStruct1Iterator");


        oracle.jbo.Key parentKey = parentIter.getCurrentRow().getKey();

        System.out.println("Key" + parentKey);
        v.setWhereClause("GLBL_APPLI_STRUCT_NM like '%" + searchString + "%'");
        v.executeQuery();

        if (v.getAllRowsInRange().length == 0) {
            FacesMessage message2 = new FacesMessage(resolvEl("#{bundle['MSG.274']}").toString());
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
        if (valueExp != null) {
            msg = valueExp.getValue(elContext).toString();
        }
        return msg;
    }

    public void setTreeBind(RichTreeTable treeBind) {
        this.treeBind = treeBind;
    }

    public RichTreeTable getTreeBind() {
        return treeBind;
    }

    public void treeSelectionListener(SelectionEvent selectionEvent) {
        /*  ADFUtil.invokeEL("#{bindings.AppGlblAppliStruct11.treeModel.makeCurrent}",
                         new Class[] { SelectionEvent.class }, new Object[] { selectionEvent });
        RowKeySet rks = treeBind.getSelectedRowKeys();
        Iterator keys = rks.iterator();
        String structid = null;
        while (keys.hasNext()) {
            List key = (List)keys.next();
            treeBind.setRowKey(key);
            JUCtrlHierNodeBinding node = (JUCtrlHierNodeBinding)treeBind.getRowData();
            Row rw = node.getRow();
            */

        RichTreeTable treeTable = this.getTreeBind();
        RowKeySet rks = treeTable.getSelectedRowKeys();
        Iterator keys = rks.iterator();
        String structid = null;
        while (keys.hasNext()) {
            List key = (List)keys.next();
            treeTable.setRowKey(key);
            JUCtrlHierNodeBinding node = (JUCtrlHierNodeBinding)treeTable.getRowData();
            Row rw = node.getRow();
            //  String voName = node.getViewObject().getName();
            //  grpId = rw.getAttribute("GrpId").toString();
            structid = rw.getAttribute("GlblAppliStructId").toString();
        }


        System.out.println("Struct id---" + structid);
        GlblAppPkgAMImpl am = (GlblAppPkgAMImpl)resolvElDC("GlblAppPkgAMDataControl");
        GlblAppPkgDMLVOImpl vo = am.getGlblAppPkgDML1();
        vo.setWhereClause(null);
        vo.executeQuery();
        System.out.println("sel");
        //vo.setGlblAppPkgDMLVOImpl
        vo.setstructIdBind(structid);
        // vo.setWhereClause("GLBL_APPLI_STRUCT_ID='"+structid+"'");
        vo.executeQuery();
        System.out.println(vo.getQuery());
        /*  Row[] R = vo.getFilteredRows("GlblAppliStructId", structid);
            vo.setCurrentRow(R[0]); */
    }

    public void rollback() {
        BindingContainer bindings = getBindings();

        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppGlblAppliStruct1Iterator");


        oracle.jbo.Key parentKey = parentIter.getCurrentRow().getKey();


        OperationBinding createOpBAddress = bindings.getOperationBinding("Rollback");
        createOpBAddress.execute();


        OperationBinding OpBAddress = bindings.getOperationBinding("Execute");
        OpBAddress.execute();
        System.out.println("parentkey");
        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

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

    public void createButtonActionListener(ActionEvent actionEvent) {

        //  vo.executeQuery();
        System.out.println("in create button action listner");
        Object parId = structIdBinding.getValue();
        System.out.println("parent id :  " + parId);
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert").execute();
        System.out.println("after execute");
        GlblAppPkgAMImpl am = (GlblAppPkgAMImpl)resolvElDC("GlblAppPkgAMDataControl");
        ViewObjectImpl vo = am.getGlblAppPkgDML1();
        row = vo.getCurrentRow();
        row.setAttribute("GlblAppliStructIdParent", parId);
        //System.out.println("addparentIdBinding"+addparentIdBinding);
        // addparentIdBinding.setValue(parId);

        System.out.println("after set Value");
        showPopup(pop, true);
    }

    public void setPop(RichPopup pop) {
        this.pop = pop;
    }

    public RichPopup getPop() {
        return pop;
    }

    public void createDialogListener(DialogEvent dialogEvent) {
        GlblAppPkgAMImpl am = (GlblAppPkgAMImpl)resolvElDC("GlblAppPkgAMDataControl");
        ViewObjectImpl vo = am.getGlblAppPkgDML1();
        ViewObjectImpl vo1 = am.getAppGlblAppliStruct1();
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok) {

            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
        }
        vo1.executeQuery();
    }

    public void popCancelListener(PopupCanceledEvent popupCanceledEvent) {

        /*   BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();
        GlblAppPkgAMImpl am = (GlblAppPkgAMImpl)resolvElDC("GlblAppPkgAMDataControl");
        ViewObjectImpl vo = am.getGlblAppPkgDML1();

        Row[] r = vo.getFilteredRows("GlblAppliStructId", row.getAttribute("GlblAppliStructId"));
        // vo.executeQuery();
        vo.setCurrentRow(r[0]); */
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();
    }

    public void delDialogListener(DialogEvent dialogEvent) {
        System.out.println("in dialog listner");
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok) {
            System.out.println("in first if of dialog listner");
            GlblAppPkgAMImpl am = (GlblAppPkgAMImpl)resolvElDC("GlblAppPkgAMDataControl");
            ViewObjectImpl vo = am.getGlblAppPkgDML1();
            ViewObjectImpl vo1 = am.getAppGlblAppliStruct1();
            Row crow = vo.getCurrentRow();
            Row[] r =
                vo.getFilteredRows("GlblAppliStructIdParent", vo.getCurrentRow().getAttribute("GlblAppliStructId"));
            System.out.println("row =" + r);
            if (r.length == 0) {
                System.out.println("delete in if");
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete").execute();


            } else {
                System.out.println("delete in else");
                int i = 0;
                while (i < r.length) {
                    vo.setCurrentRow(r[i]);

                    BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete").execute();
                    i++;
                }
                vo.setCurrentRow(crow);

                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete").execute();
            }
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
            vo.setWhereClause("GLBL_APPLI_STRUCT_ID_PARENT like '%'");
            vo.executeQuery();
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("refresh_page").execute();
            vo1.reset();
            vo1.executeQuery();
            Row[] filteredRows = vo1.getFilteredRows("GlblAppliStructId", "0");
            vo.setWhereClause(null);
            vo.executeQuery();
            vo.setNamedWhereClauseParam("structIdBind", "0");
vo.executeQuery();
            Row allRows[] = vo1.getAllRowsInRange();
            for (Row row : allRows) {
                RowSet childRow = (RowSet)row.getAttribute("ChildAppGlblAppliStruct");
                System.out.println("Row in link is-" + childRow.getRangeSize());
                childRow.executeQuery();
            }

        }
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("refresh_page").execute();

    }

    public void setDelPop(RichPopup delPop) {
        this.delPop = delPop;
    }

    public RichPopup getDelPop() {
        return delPop;
    }

    public void deleteActionListener(ActionEvent actionEvent) {
        showPopup(delPop, true);

    }

    public void editButtonActionListener(ActionEvent actionEvent) {
        GlblAppPkgAMImpl am = (GlblAppPkgAMImpl)resolvElDC("GlblAppPkgAMDataControl");
        ViewObjectImpl vo = am.getGlblAppPkgDML1();
        row = vo.getCurrentRow();
        showPopup(pop, true);
    }

    public void resetActionListener(ActionEvent actionEvent) {
        GlblAppPkgAMImpl am = (GlblAppPkgAMImpl)resolvElDC("GlblAppPkgAMDataControl");
        ViewObjectImpl vo = am.getAppGlblAppliStruct1();

        vo.setWhereClause(null);
        // vo.setWhereClause("GLBL_APPLI_STRUCT_ID = '0'");

        // treeBind.setRowSelection("single");
        /*  vo.setWhereClause("GLBL_APPLI_STRUCT_ID_PARENT = '0'"); */
        vo.executeQuery();
        searchText.setValue("");
        Search();

        // GlblAppPkgAMImpl am = (GlblAppPkgAMImpl)resolvElDC("GlblAppPkgAMDataControl");
        ViewObjectImpl v = am.getGlblAppPkgDML1();

        /*    v.setWhereClause(null);
        v.executeQuery();
        */v.setWhereClause("GLBL_APPLI_STRUCT_NM like '%" + searchString + "%'");
        v.executeQuery();
        Search();
        // ViewObjectImpl v = am.getGlblAppPkgDML1();

        /*    v.setWhereClause(null);
        v.executeQuery();
        */v.setWhereClause("GLBL_APPLI_STRUCT_NM like '%" + searchString + "%'");
        v.executeQuery();
        rollback();
    }

    public void idValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {


        if (object != null && object.toString().length() > 0) {

            GlblAppPkgAMImpl am = (GlblAppPkgAMImpl)resolvElDC("GlblAppPkgAMDataControl");
            ViewObjectImpl vo = am.getGlblAppPkgDML1();


            Row[] r = vo.getFilteredRows("GlblAppliStructId", object.toString());

            if (r.length > 1) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.254']}").toString(), null));
            }
        } else {

            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.508']}").toString(), null));
        }
    }

    public void nameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null && object.toString().length() > 0) {


            GlblAppPkgAMImpl am = (GlblAppPkgAMImpl)resolvElDC("GlblAppPkgAMDataControl");
            ViewObjectImpl vo = am.getGlblAppPkgDML1();
            Row[] r = vo.getFilteredRows("GlblAppliStructNm", object.toString());

            if (r.length > 1) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.254']}").toString(), null));
            }
        } else {

            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.508']}").toString(), null));
        }


    }


    public void setIdTextBind(RichInputText idTextBind) {
        this.idTextBind = idTextBind;
    }

    public RichInputText getIdTextBind() {
        return idTextBind;
    }

    public void setNameTextBind(RichInputText nameTextBind) {
        this.nameTextBind = nameTextBind;
    }

    public RichInputText getNameTextBind() {
        return nameTextBind;
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {

        if (count == null) {
            OperationBinding op = getBindings().getOperationBinding("on_load_page");
            op.execute();
            count = Integer.parseInt(op.getResult().toString());

        }
        return count;
    }

    public void setParentIdBinding(RichSelectOneChoice parentIdBinding) {
        this.parentIdBinding = parentIdBinding;
    }

    public RichSelectOneChoice getParentIdBinding() {
        return parentIdBinding;
    }

    public void setAddparentIdBinding(RichSelectOneChoice addparentIdBinding) {
        this.addparentIdBinding = addparentIdBinding;
    }

    public RichSelectOneChoice getAddparentIdBinding() {
        return addparentIdBinding;
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

    public void findRowInTable(String searchAtt, String searchTyp, String searchStr) {
        JUCtrlHierBinding treeBinding = null;

        //get handle to tree if it does not exist. If tree component cannot be
        //found in view, exit this function
        if (treeBind == null) {
            this.findTreeInView();
            if (treeBind == null) {
                //tree not found
                log("The tree component could not be found in the view. Please check for naming containers. Search function cancelled");
                return;
            }
        }
        //Get the JUCtrlHierbinding reference from the PageDef
        CollectionModel model = (CollectionModel)treeBind.getValue();
        treeBinding = (JUCtrlHierBinding)model.getWrappedData();

        //Read the attributes to search in from the SelectManyChoice component
        //  String searchAttributeArray[] =             (String[])searchAttributes.toArray(new String[searchAttributes.size()]);

        //Define a node to search in. In this example, the root node is used
        JUCtrlHierNodeBinding root = treeBinding.getRootNodeBinding();

        //However, if the user used the "Show as Top" context menu option to
        //shorten the tree display, then we only search starting from this top
        //mode

        List topNode = (List)treeBind.getFocusRowKey();
        if (topNode != null) {
            //make top node the root node for the search
            root = treeBinding.findNodeByKeyPath(topNode);
        }

        //Select the tree items that match the search criteria and expand the
        //tree to display them
        RowKeySet resultRowKeySet = searchTreeNode(root, searchAtt, searchTyp, searchStr);
        RowKeySet disclosedRowKeySet = buildDiscloseRowKeySet(treeBinding, resultRowKeySet);
        treeBind.setSelectedRowKeys(resultRowKeySet);
        treeBind.setDisclosedRowKeys(disclosedRowKeySet);

        AdfFacesContext.getCurrentInstance().addPartialTarget(treeBind);
    }

    private void findTreeInView() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        UIViewRoot root = fctx.getViewRoot();
        //hard coding tree component Id with its surrounding naming container ID
        //PanelCollection
        root.invokeOnComponent(fctx, treeBind.getId(), new ContextCallback() {
                public void invokeContextCallback(FacesContext facesContext, UIComponent uiComponent) {
                    treeBind = (RichTreeTable)uiComponent;
                }
            });
    }

    public void Search() {
        JUCtrlHierBinding treeBinding = null;

        //get handle to tree if it does not exist. If tree component cannot be
        //found in view, exit this function
        if (treeBind == null) {
            this.findTreeInView();
            if (treeBind == null) {
                //tree not found
                log("The tree component could not be found in the view. Please check for naming containers. Search function cancelled");
                return;
            }
        }
        //Get the JUCtrlHierbinding reference from the PageDef
        CollectionModel model = (CollectionModel)treeBind.getValue();
        treeBinding = (JUCtrlHierBinding)model.getWrappedData();

        //Read the attributes to search in from the SelectManyChoice component
        //  String searchAttributeArray[] =             (String[])searchAttributes.toArray(new String[searchAttributes.size()]);

        //Define a node to search in. In this example, the root node is used
        JUCtrlHierNodeBinding root = treeBinding.getRootNodeBinding();

        //However, if the user used the "Show as Top" context menu option to
        //shorten the tree display, then we only search starting from this top
        //mode

        List topNode = (List)treeBind.getFocusRowKey();
        if (topNode != null) {
            //make top node the root node for the search
            root = treeBinding.findNodeByKeyPath(topNode);
        }

        //Select the tree items that match the search criteria and expand the
        //tree to display them
        RowKeySet resultRowKeySet = searchTreeNode(root, searchAttributes, searchType, searchString);
        RowKeySet disclosedRowKeySet = buildDiscloseRowKeySet(treeBinding, resultRowKeySet);
        treeBind.setSelectedRowKeys(resultRowKeySet);
        treeBind.setDisclosedRowKeys(disclosedRowKeySet);

        AdfFacesContext.getCurrentInstance().addPartialTarget(treeBind);
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
            //   System.out.println("COMAPRE STRING:"+compareString);
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
            } else if (_searchType.equalsIgnoreCase("EQUAL") &&
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


    public void setStructIdBinding(RichInputText structIdBinding) {
        this.structIdBinding = structIdBinding;
    }

    public RichInputText getStructIdBinding() {
        return structIdBinding;
    }
}
