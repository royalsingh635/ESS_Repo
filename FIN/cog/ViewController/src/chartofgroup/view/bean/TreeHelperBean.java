package chartofgroup.view.bean;


import adf.utils.bean.ADFBeanUtils;
import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

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
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.model.servlet.ADFBindingFilter;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTree;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;
import oracle.jbo.uicli.binding.JUIteratorBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class TreeHelperBean {

    private String CogId = null;
    private String GroupType;
    private Integer serverlocationId;
    private String mode = "V";
    private RichTree treetablebind;
    private RichPopup deletepop;
    private RichTreeTable cogTree;
    private RichSelectBooleanCheckbox cogBalanceSheetPopup;
    private RichSelectBooleanCheckbox cogPnLPopup;
    private RichSelectBooleanCheckbox cogBalenceSheet;
    private RichSelectBooleanCheckbox cogProfitLoss;
    private RichSelectBooleanCheckbox cogTransfer;
    private RichSelectBooleanCheckbox cogTransferBalancePopup;
    private RichSelectBooleanCheckbox selectCheckBox;
    private HashSet<String> cogIdVal = new HashSet<String>();
    private RichPopup msgPop;
    private RichOutputText treeCogName;
    private RichPopup transferPopForBS;
    private RichPopup transferPopupForPnL;
    private RichSelectOneChoice grpTyp;
    private RichSelectOneChoice popupcoggrptypBind;
    private Integer onloadVal;
    private RichInputText cogNmFormBind;
    private RichInputText cogLegCodeFormBind;
    private RichInputText cogAliasFormBind;
    private RichSelectOneChoice parentbinding;
    private RichSelectOneChoice createUnderBind;
    String CogName = "";
    private RichInputText cognmpopupBind;
    private RichSelectBooleanCheckbox inheritBind;
    private String L0mode = "V";
    private String chekCogLoAllow;
    private RichLink cogRottAddImageBind;
    private String searchString = "";
    private String searchType = "START";
    private String searchAttributes = "CogNm";
    private RichTree tree1 = null;
    ADFLogger logger = ADFLogger.createADFLogger(this.getClass());
    private RichSelectOneChoice cogBalTypeBinding;
    private RichPanelGroupLayout btnGroupBind;
    private RichPanelGroupLayout cogFormGroupBind;
    private RichPanelFormLayout chkBoxFormBind;
    private RichInputText cogBeanSearchBind;
    private RichPanelFormLayout searchPanelFormBind;

    public void setChekCogLoAllow(String chekCogLoAllow) {
        this.chekCogLoAllow = chekCogLoAllow;
    }

    public String getChekCogLoAllow() {
        BindingContainer bindings = getBindings();
        OperationBinding ob = bindings.getOperationBinding("checkForCOGL0Allow");
        ob.execute();
        if (ob.getResult() != null)
            return ob.getResult().toString();
        else
            return chekCogLoAllow;
    }

    public void setL0mode(String L0mode) {
        this.L0mode = L0mode;
    }

    public String getL0mode() {
        return L0mode;
    }

    public TreeHelperBean() {
    }

    public void onIndexSelection(ActionEvent actionEvent) {
        RichCommandLink linkPressed = (RichCommandLink) actionEvent.getSource();
        Key jboRowKey = (Key) linkPressed.getAttributes().get("jboRowKey");

        ChartOfGroupAMImpl am = (ChartOfGroupAMImpl) resolvElDC("ChartOfGroupAMDataControl");
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


    public void DeleteDialogListener(DialogEvent dialogEvent) {
        ADFBeanUtils.callBindingsMethod("delteCogAction", null, null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cogTree);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cogRottAddImageBind);
        mode = "V";
    }


    private static int VARCHAR = Types.VARCHAR;

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        Object ret = null;
        CallableStatement st = null;
        try {
            ChartOfGroupAMImpl am = (ChartOfGroupAMImpl) resolvElDC("ChartOfGroupAMDataControl");
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

    private ChartOfGroupAMImpl getAm() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data.ChartOfGroupAMDataControl.dataProvider}", Object.class);
        return (ChartOfGroupAMImpl) valueExp.getValue(elContext);
    }

    public void onTreeNodeDelete(ActionEvent actionEvent) {
        String returnVal = ADFBeanUtils.callBindingsMethod("checkCogEditable", null, null).toString();
        if (returnVal.equals("Y")) {
            Object obj = ADFBeanUtils.callBindingsMethod("checkForChildNode", null, null);
            if (obj != null) {
                if (obj.toString().equalsIgnoreCase("Y")) {
                    String msg2 =
                        resolvEl("#{bundle['MSG.211']}").toString(); //Child Record Find First Delete Child Record
                    ADFModelUtils.showFacesMessage(msg2, msg2, FacesMessage.SEVERITY_ERROR, null);
                } else {
                    ADFBeanUtils.showPopup(deletepop, true);
                }
            }
        } else {
            String msg2 =
                resolvEl("#{bundle['MSG.212']}").toString(); //Group cannot be deleted. As reference found in transaction or Group is Reserved.
            ADFModelUtils.showFacesMessage(msg2, msg2, FacesMessage.SEVERITY_ERROR, null);
        }
        mode = "V";
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
        ADFBeanUtils.callBindingsMethod("Rollback", null, null);
        mode = "V";
        AdfFacesContext.getCurrentInstance().addPartialTarget(cogNmFormBind);
    }

    private void requeryForGrpLOV() {
        ChartOfGroupAMImpl am = (ChartOfGroupAMImpl) resolvElDC("ChartOfGroupAMDataControl");
        ViewObjectImpl cogVo = am.getFinCogNewVO();
        if (cogVo.getCurrentRow() != null) {
            FinCogNewVORowImpl cogCurrrw = (FinCogNewVORowImpl) cogVo.getCurrentRow();

            cogCurrrw.getLOVGroupTyp1().executeQuery();
        }
    }

    public void Edit(ActionEvent actionEvent) {
        System.out.println("COGbean Edit ACL");
        Object result = ADFBeanUtils.callBindingsMethod("checkCogEditable", null, null);
        if (result.toString().equalsIgnoreCase("Y")) {
            mode = "E";
            CogName = "A";
            L0mode = "V";
        } else {
            // String message = "Group already used in transaction OR Group is Reserved. Edit not allowed.";
            String message = (String) resolvEl("#{bundle['MSG.214']}");
            ADFModelUtils.showFacesMessage(message, null, FacesMessage.SEVERITY_ERROR, null);
        }
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


    public void Search() {
        JUCtrlHierBinding treeBinding = null;
        if (cogTree == null) {
            this.findTreeInView();
            if (cogTree == null) {
                log("The tree component could not be found in the view. Please check for naming containers. Search function cancelled");
                return;
            }
        }
        CollectionModel model = (CollectionModel) cogTree.getValue();
        treeBinding = (JUCtrlHierBinding) model.getWrappedData();
        JUCtrlHierNodeBinding root = treeBinding.getRootNodeBinding();
        List topNode = (List) cogTree.getFocusRowKey();
        if (topNode != null) {
            root = treeBinding.findNodeByKeyPath(topNode);
        }
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
        System.out.println("in search cog methodddd");
        System.out.println("cogg namee iss==" + CogNm);
        ChartOfGroupAMImpl am = (ChartOfGroupAMImpl) resolvElDC("ChartOfGroupAMDataControl");
        ViewObject vo = am.findViewObject("FinCogNewVO");
        FinCogForTreeVOImpl vSearch = am.getFinCogForTree();
        Integer SlocId = Integer.parseInt(resolvElDCBindVar("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String CldId = (resolvElDCBindVar("#{pageFlowScope.GLBL_APP_CLD_ID}"));
        String OrgId = (resolvElDCBindVar("#{pageFlowScope.GLBL_HO_ORG_ID}"));


        vo.setWhereClause("upper(COG_NM) like upper('" + CogNm + "%') and COG_CLD_ID='" + CldId + "' and COG_SLOC_ID=" +
                          SlocId + " and COG_ORG_ID='" + OrgId + "'");
        vo.executeQuery();

        if (vo.getRowCount() != 0) {

            Row rw = vo.getCurrentRow();
            System.out.println("cogg idd of current roww iss==" + rw.getAttribute("CogId"));
            if (rw != null) {
                System.out.println("00000000000000000000");
                vSearch.setcogParentIdBindVar(rw.getAttribute("CogId").toString());
                vSearch.setSlocIdBindVar(SlocId);
                vSearch.setCldIdBindVar(CldId);
                vSearch.setOrgIdBindVar(OrgId);
                vSearch.executeQuery();
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("setcogParentIdBindVar");
                operationBinding.execute();
                if (searchString != "") {
                }
                cogIdVal.clear();
            }
        } else {
            System.out.println("---0000000000000-----");
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
                    compareString = (String) attribute;
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
            List keyPath = (List) iter.next();
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
                cogTree = (RichTreeTable) uiComponent;
            }
        });
    }

    public void selectViewmode(SelectionEvent selectionEvent) {
        // Add event code here...
        RichTreeTable treeTable = this.getCogTree();
        RowKeySet rks = treeTable.getSelectedRowKeys();
        Iterator rksIterator = rks.iterator();
        if (rksIterator.hasNext()) {
            List key = (List) rksIterator.next();
            JUCtrlHierBinding treeBinding = null;
            treeBinding = (JUCtrlHierBinding) ((CollectionModel) treeTable.getValue()).getWrappedData();
            JUCtrlHierNodeBinding nodeBinding = nodeBinding = treeBinding.findNodeByKeyPath(key);
            DCIteratorBinding _treeIteratorBinding = null;
            _treeIteratorBinding = treeBinding.getDCIteratorBinding();
            Key rowKey = nodeBinding.getRowKey();
            JUIteratorBinding iterator = nodeBinding.getIteratorBinding();
            iterator.setCurrentRowWithKey(rowKey.toStringFormat(true));
            this.setServerlocationId((Integer) iterator.getCurrentRow().getAttribute("CogSlocId"));
            this.setCogId((String) iterator.getCurrentRow().getAttribute("CogId"));
            this.setGroupType((String) iterator.getCurrentRow().getAttribute("CogGrpType"));
            String CldId = (String) iterator.getCurrentRow().getAttribute("CogCldId");
            String CogOrgId = (String) iterator.getCurrentRow().getAttribute("CogOrgId");
            BindingContainer binding = getBindings();
            ChartOfGroupAMImpl am = (ChartOfGroupAMImpl) resolvElDC("ChartOfGroupAMDataControl");
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
            cogIdVal.clear();
        }
    }


    public void saveCog(ActionEvent actionEvent) {
        String balSheet = cogBalenceSheet.getValue().toString();
        String pnl = cogProfitLoss.getValue().toString();
        String transferAcc = cogTransfer.getValue().toString();
        String message = "";
        if (balSheet.equalsIgnoreCase("false") && pnl.equalsIgnoreCase("false")) {
            message =
                resolvEl("#{bundle['MSG.116']}").toString(); //You haven't selected (Balance Sheet Item and Profit Loss). Please select one among Balance Sheet Item and Profit Loss.
            ADFModelUtils.showFacesMessage(message, message, FacesMessage.SEVERITY_ERROR, null);
        }

        else if ((grpTyp.getValue().toString().equalsIgnoreCase("A") ||
                  grpTyp.getValue().toString().equalsIgnoreCase("L")) && balSheet.equalsIgnoreCase("false")) {
            message =
                resolvEl("#{bundle['MSG.2650']}").toString(); //Select Balance sheet in case of Assets and liability";
            ADFModelUtils.showFacesMessage(message, message, FacesMessage.SEVERITY_ERROR, null);
        }

        else if ((grpTyp.getValue().toString().equalsIgnoreCase("E") ||
                  grpTyp.getValue().toString().equalsIgnoreCase("I")) && pnl.equalsIgnoreCase("false")) {
            message = resolvEl("#{bundle['MSG.2651']}").toString(); //Profilt and Loss in case of Expense and Income
            ADFModelUtils.showFacesMessage(message, message, FacesMessage.SEVERITY_ERROR, null);
        }

        else {
            if (balSheet.equalsIgnoreCase("true") && transferAcc.equalsIgnoreCase("false"))
                ADFBeanUtils.showPopup(transferPopForBS, true);
            else if (pnl.equalsIgnoreCase("true") && transferAcc.equalsIgnoreCase("true"))
                ADFBeanUtils.showPopup(transferPopupForPnL, true);
            else {
                if (mode.toString().equalsIgnoreCase("A"))
                    ADFBeanUtils.callBindingsMethod("getAndSetCogId", null, null);
                ADFBeanUtils.callBindingsMethod("Commit", null, null);
                ADFBeanUtils.showPopup(msgPop, true);
                mode = "V";
                AdfFacesContext.getCurrentInstance().addPartialTarget(cogTree);
                AdfFacesContext.getCurrentInstance().addPartialTarget(cogNmFormBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(cogFormGroupBind);

            }
        }
    }

    public void reset() {
        ChartOfGroupAMImpl am = (ChartOfGroupAMImpl) resolvElDC("ChartOfGroupAMDataControl");
        ViewObjectImpl v = am.getFinCog1();
        v.setWhereClause(null);
        v.executeQuery();
        setSearchString("");
        Search();
        searchCog(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cogTree);
    }

    public void resetTree(ActionEvent actionEvent) {
        reset();
        AdfFacesContext.getCurrentInstance().addPartialTarget(cogTree);
    }

    public String createCog() {
        mode = "A";
        L0mode = "V";
        ADFBeanUtils.callBindingsMethod("getFinCogLOV1", null, null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(parentbinding);
        ADFBeanUtils.callBindingsMethod("CreateInsert1", null, null);
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

    public void msgDialogListner(DialogEvent dialogEvent) {
        System.out.println("valueee of cog namess iss==" + cogNmFormBind.getValue());
        searchCog(cogNmFormBind.getValue().toString());
        searchOnSave();
        AdfFacesContext.getCurrentInstance().addPartialTarget(cogBalTypeBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(grpTyp);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cogNmFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cogTree);
        AdfFacesContext.getCurrentInstance().addPartialTarget(btnGroupBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cogFormGroupBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(chkBoxFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cogBeanSearchBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchPanelFormBind);
        msgPop.hide();
    }


    public void delPopUpCancelListner(PopupCanceledEvent popupCanceledEvent) {
        ADFBeanUtils.callBindingsMethod("Rollback", null, null);
        mode = "V";
        AdfFacesContext.getCurrentInstance().addPartialTarget(cogTree);
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
        ChartOfGroupAMImpl am = (ChartOfGroupAMImpl) resolvElDC("ChartOfGroupAMDataControl");
        ViewObjectImpl v = am.getFinCogForTree();


        RowSetIterator itr = v.createRowSetIterator(null);
        while (itr.hasNext()) {
            Row rw = itr.next();

            rw.setAttribute("TransCheckBox", Boolean.TRUE);
            AdfFacesContext.getCurrentInstance().addPartialTarget(selectCheckBox);
            cogIdVal.add(rw.getAttribute("CogId").toString());
        }

        itr.closeRowSetIterator();
        return null;
    }

    public String DeselectAllAction() {
        ChartOfGroupAMImpl am = (ChartOfGroupAMImpl) resolvElDC("ChartOfGroupAMDataControl");
        ViewObjectImpl v = am.getFinCogForTree();


        RowSetIterator itr = v.createRowSetIterator(null);
        while (itr.hasNext()) {
            Row rw = itr.next();
            rw.setAttribute("TransCheckBox", Boolean.FALSE);
            AdfFacesContext.getCurrentInstance().addPartialTarget(selectCheckBox);
            cogIdVal.remove(rw.getAttribute("CogId").toString());
        }

        itr.closeRowSetIterator();

        return null;
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

    public void cogBsItemChangeListner(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            getCogTransfer().setValue(vce.getNewValue().toString());
        }
    }


    public void parentCogChangeListner(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (vce.getNewValue() != null) {
            ADFBeanUtils.callBindingsMethod("cogParentvalueChngAction", new Object[] { vce.getNewValue().toString() }, new Object[] {
                                            "parentId" });
            AdfFacesContext.getCurrentInstance().addPartialTarget(grpTyp);
        }
    }

    public void setTransferPopForBS(RichPopup transferPopForBS) {
        this.transferPopForBS = transferPopForBS;
    }

    public RichPopup getTransferPopForBS() {
        return transferPopForBS;
    }

    public void transferPopForBSDialogListner(DialogEvent de) {
        if (de.getOutcome().name().equals("yes")) {
            ADFBeanUtils.callBindingsMethod("getAndSetCogId", null, null);
            ADFBeanUtils.callBindingsMethod("Commit", null, null);
            ADFBeanUtils.showPopup(msgPop, true);
            mode = "V";
            AdfFacesContext.getCurrentInstance().addPartialTarget(cogTree);
        }
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
            ADFBeanUtils.callBindingsMethod("getAndSetCogId", null, null);
            ADFBeanUtils.callBindingsMethod("Commit", null, null);
            ADFBeanUtils.showPopup(msgPop, true);
            mode = "V";
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

        return (Integer) ret;
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

    public void setParentbinding(RichSelectOneChoice parentbinding) {
        this.parentbinding = parentbinding;
    }

    public RichSelectOneChoice getParentbinding() {
        return parentbinding;
    }

    public void parentidValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (object.toString().length() > 18) {
                String msg =
                    resolvEl("#{bundle['MSG.2652']}").toString(); //Maximum Number Of Level Exceeds. You can not proceed to create new COG
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

            }
        }
    }

    public void activeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (object.toString().equalsIgnoreCase("false")) {
                ChartOfGroupAMImpl am = (ChartOfGroupAMImpl) resolvElDC("ChartOfGroupAMDataControl");

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
                                Object result = callStoredFunction(VARCHAR, "fn_cog_allow_deactivation(?,?,?,?)", new Object[] {
                                                                   CldId, SlocId, HoOrgId, CogId
                                });
                                System.out.println("result =  " + result + " CldId = " + CldId + " SlocId = " + SlocId +
                                                   " HoOrgId = " + HoOrgId + " CogId = " + CogId);
                                if (result != null) {
                                    if (result.toString().equalsIgnoreCase("N")) {
                                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                                      resolvEl("#{bundle['MSG.2653']}").toString(),
                                                                                      null));
                                        //   ou can't set the corresponding COG to Inactive. It is in use in a COA.           }
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

    public void aliasNameValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            String expression = "^(?:(?>[A-Za-z0-9 \\(\\)]+)()(\\%|\\@|\\_|\\.|\\-|\\$(?!\\.|\\-|$))?)*$";
            String message = "";
            String DbobDesc = object.toString();
            CharSequence inputStr = DbobDesc;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            char at = DbobDesc.charAt(0);
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = DbobDesc.toCharArray();
            Object duplicogAliasResult = ADFBeanUtils.callBindingsMethod("checkDulicateCogAlias", new Object[] {
                                                                         object.toString().toUpperCase() }, new Object[] {
                                                                         "currCogAlias" });
            if (duplicogAliasResult.toString().equalsIgnoreCase("Y")) {
                message = resolvEl("#{bundle['MSG.2654']}").toString(); //COG Alias already exists.
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            } else {
                if (at == ' ') {
                    message =
                        resolvEl("#{bundle['MSG.2655']}").toString(); //Space is not allowed as first character in name.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                } else if ((DbobDesc.length() - 1) == DbobDesc.lastIndexOf(' ')) {
                    message =
                        resolvEl("#{bundle['MSG.2656']}").toString(); //Space is not allowed as last character in name.";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                } else {
                    for (char c : xx) {
                        if (c == '(') {
                            openB = openB + 1;
                        } else if (c == ')') {
                            closeB = closeB + 1;
                        }
                        if (closeB > openB) {
                            closeFg = true;
                        }
                    }
                    if (openB != closeB || closeFg == true || (DbobDesc.lastIndexOf("(") > DbobDesc.lastIndexOf(")")) ||
                        (DbobDesc.indexOf(")") < DbobDesc.indexOf("("))) {
                        message = resolvEl("#{bundle['MSG.7']}").toString(); //Brackets not closed properly.
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                    }
                    if (DbobDesc.contains("()")) {
                        message = resolvEl("#{bundle['MSG.49']}").toString(); //Empty Brackets are not allowed
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                    }
                    if (DbobDesc.contains("(.") || DbobDesc.contains("(-") || DbobDesc.contains("-)")) {
                        message =
                            resolvEl("#{bundle['MSG.162']}").toString(); //Invalid Tax name. Check content inside brackets.
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                    }

                    if (matcher.matches()) {
                    } else {
                        message =
                            resolvEl("#{bundle['MSG.221']}").toString(); //Special character and Lowercase are not allowed
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
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
                        message = resolvEl("#{bundle['MSG.222']}").toString(); //First character cannot be numeric
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                    }
                }
            }
        }
    }

    public void cogNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            System.out.println("valuee of object iss===" + object.toString().toUpperCase());
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            String DbobDesc = object.toString();
            String expression = "^(?:(?>[A-Za-z0-9 \\(\\)]+)()(\\%|\\@|\\_|\\.|\\-|\\$(?!\\.|\\-|$))?)*$";
            CharSequence inputStr = DbobDesc;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            char[] xx = DbobDesc.toCharArray();
            String message = "";
            Object result = ADFBeanUtils.callBindingsMethod("CheckDulicateCogName", new Object[] {
                                                            object.toString().toUpperCase() }, new Object[] {
                                                            "cogName" });
            if (result.toString().equalsIgnoreCase("Y")) {
                message = resolvEl("#{bundle['MSG.2657']}").toString(); //COG name already exists.
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            } else {

                if (DbobDesc.length() > 0) {
                    char at = DbobDesc.charAt(0);
                    if (at == ' ') {
                        message =
                            resolvEl("#{bundle['MSG.2655']}").toString(); //Space is not allowed as first character in name.
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                    } else if ((DbobDesc.length() - 1) == DbobDesc.lastIndexOf(' ')) {
                        System.out.println("Spcae at last");
                        message =
                            resolvEl("#{bundle['MSG.2656']}").toString(); //Space is not allowed as last character in name.
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                    }
                }
                for (char c : xx) {
                    if (c == '(') {
                        openB = openB + 1;
                    } else if (c == ')') {
                        closeB = closeB + 1;
                    }

                    if (closeB > openB) {
                        closeFg = true;
                    }
                }

                if (openB != closeB || closeFg == true || (DbobDesc.lastIndexOf("(") > DbobDesc.lastIndexOf(")")) ||
                    (DbobDesc.indexOf(")") < DbobDesc.indexOf("("))) {
                    message = resolvEl("#{bundle['MSG.7']}").toString(); //Brackets not closed properly.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                }
                if (DbobDesc.contains("()")) {
                    message = resolvEl("#{bundle['MSG.49']}").toString(); //Empty Brackets are not allowed
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                }
                if (DbobDesc.contains("(.") || DbobDesc.contains("(-") || DbobDesc.contains("-)")) {
                    message =
                        resolvEl("#{bundle['MSG.162']}").toString(); //Invalid Tax name. Check content inside brackets.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                }

                if (matcher.matches()) {
                } else {
                    message =
                        resolvEl("#{bundle['MSG.221']}").toString(); //Special character and Lowercase are not allowed
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
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
                    message = resolvEl("#{bundle['MSG.222']}").toString(); //First character cannot be numeric
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                }

            }
        }
    }

    public void createCOGAtZeroLevelAL(ActionEvent actionEvent) {
        mode = "A";
        L0mode = "L";
        ADFBeanUtils.callBindingsMethod("CreateInsert1", null, null);
    }

    public void setCogRottAddImageBind(RichLink cogRottAddImageBind) {
        this.cogRottAddImageBind = cogRottAddImageBind;
    }

    public RichLink getCogRottAddImageBind() {
        return cogRottAddImageBind;
    }

    public void cogLegcyCodeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            Object duplicogLegcyResult = ADFBeanUtils.callBindingsMethod("checkDulicateCoglegcyCode", new Object[] {
                                                                         object.toString().toUpperCase() }, new Object[] {
                                                                         "currCoglegcyCode" });
            if (duplicogLegcyResult.toString().equalsIgnoreCase("Y")) {
                String message = resolvEl("#{bundle['MSG.2658']}").toString(); //COG legacy Code already exists
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            }
        }

    }

    public void setCogBalTypeBinding(RichSelectOneChoice cogBalTypeBinding) {
        this.cogBalTypeBinding = cogBalTypeBinding;
    }

    public RichSelectOneChoice getCogBalTypeBinding() {
        return cogBalTypeBinding;
    }

    public void setBtnGroupBind(RichPanelGroupLayout btnGroupBind) {
        this.btnGroupBind = btnGroupBind;
    }

    public RichPanelGroupLayout getBtnGroupBind() {
        return btnGroupBind;
    }

    public void setCogFormGroupBind(RichPanelGroupLayout cogFormGroupBind) {
        this.cogFormGroupBind = cogFormGroupBind;
    }

    public RichPanelGroupLayout getCogFormGroupBind() {
        return cogFormGroupBind;
    }

    public void setChkBoxFormBind(RichPanelFormLayout chkBoxFormBind) {
        this.chkBoxFormBind = chkBoxFormBind;
    }

    public RichPanelFormLayout getChkBoxFormBind() {
        return chkBoxFormBind;
    }

    public void searchOnSave() {
        System.out.println("in search methid");
        JUCtrlHierBinding treeBinding = null;
        if (cogTree == null) {
            this.findTreeInView();
            if (cogTree == null) {
                log("The tree component could not be found in the view. Please check for naming containers. Search function cancelled");
                return;
            }
        }
        CollectionModel model = (CollectionModel) cogTree.getValue();
        treeBinding = (JUCtrlHierBinding) model.getWrappedData();
        JUCtrlHierNodeBinding root = treeBinding.getRootNodeBinding();
        List topNode = (List) cogTree.getFocusRowKey();
        if (topNode != null) {
            root = treeBinding.findNodeByKeyPath(topNode);
        }
        RowKeySet resultRowKeySet =
            searchTreeNode(root, searchAttributes, searchType, cogNmFormBind.getValue().toString().toUpperCase());
        RowKeySet disclosedRowKeySet = buildDiscloseRowKeySet(treeBinding, resultRowKeySet);
        cogTree.setSelectedRowKeys(resultRowKeySet);
        cogTree.setDisclosedRowKeys(disclosedRowKeySet);
        System.out.println("11111111111111111111111111");
        AdfFacesContext.getCurrentInstance().addPartialTarget(cogTree);
    }

    public void setCogBeanSearchBind(RichInputText cogBeanSearchBind) {
        this.cogBeanSearchBind = cogBeanSearchBind;
    }

    public RichInputText getCogBeanSearchBind() {
        return cogBeanSearchBind;
    }

    public void serialNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (((Integer)object).equals(new Integer(0))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Serial no. can not be equal to Zero(0)!", null));
            } else {
                OperationBinding op = ADFBeanUtils.getOperationBinding("checkDuplicateSerialNo");
                op.getParamsMap().put("serialNo", object);
                op.execute();
                if (op.getResult() != null && op.getResult().toString().equals("Y")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Serial no. already exists !", null));

                }
            }
        }
    }

    public void setSearchPanelFormBind(RichPanelFormLayout searchPanelFormBind) {
        this.searchPanelFormBind = searchPanelFormBind;
    }

    public RichPanelFormLayout getSearchPanelFormBind() {
        return searchPanelFormBind;
    }
}

