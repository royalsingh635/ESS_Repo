package searchGlbChp.view.bean;

import java.util.Iterator;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class GlobalSearchBean {
    private RichTreeTable treeTable;
    private String bindParentId;
    private String bindId;
    private String bindName;
    private String bindDes;
    private String bindHscode;
    private RichInputText parentIdBind;
    private RichInputText idBind;
    private RichInputText nameBind;
    private RichInputText descBind;
    private RichInputText hsBind;
    private RichInputListOfValues searchChapId;
    private RichOutputText binChapId;
    private RichCommandImageLink bindEditButton;
    private RichCommandImageLink bindCancelButton;
    private RichCommandImageLink bindSaveButton;
    private RichInputListOfValues searchChapName;
    private String searchType = "EQUAL";
    private String searchAttributes = "ChpNm";
    private String searchString = "";
    private String mode ="V";

    
    ADFLogger logger = ADFLogger.createADFLogger(this.getClass());

    public GlobalSearchBean() {
    }
    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void editGlobalChapter(ActionEvent actionEvent) {
        System.out.println("reached here");
        bindCancelButton.setDisabled(false);
        System.out.println("reached here1");
        bindSaveButton.setDisabled(false);
        System.out.println("reached here2");
        bindEditButton.setDisabled(true);
        nameBind.setDisabled(false);
        descBind.setDisabled(false);
        hsBind.setDisabled(false);
        setMode("A");
    }

    public void saveGlobalChapter(ActionEvent actionEvent) {
        System.out.println("reached here");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();

        bindCancelButton.setDisabled(true);
        bindSaveButton.setDisabled(true);
        bindEditButton.setDisabled(false);
        nameBind.setDisabled(true);
        descBind.setDisabled(true);
        hsBind.setDisabled(true);
        setMode("V");
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void cancelGlobalChapter(ActionEvent actionEvent) {
        bindCancelButton.setDisabled(true);
        bindSaveButton.setDisabled(true);
        bindEditButton.setDisabled(false);
        nameBind.setDisabled(true);
        descBind.setDisabled(true);
        hsBind.setDisabled(true);
        setMode("V");
    }

    public void getNodeValue(SelectionEvent selectionEvent) {
        RichTreeTable tree = this.getTreeTable();
        RowKeySet row = tree.getSelectedRowKeys();
        Iterator itr = row.iterator();
        if (itr.hasNext()) {
            List key = (List)itr.next();
            System.out.println("list is>>>>>>>>>>>>" + key);


            JUCtrlHierBinding treeBinding = null;
            CollectionModel collectionModel = (CollectionModel)tree.getValue();
            treeBinding = (JUCtrlHierBinding)collectionModel.getWrappedData();
            JUCtrlHierNodeBinding nodeBinding = null;
            nodeBinding = treeBinding.findNodeByKeyPath(key);
            Row rw = nodeBinding.getRow();
            String rowType = rw.getStructureDef().getDefName();
            System.out.println("rowtype is" + rowType);
            if (rowType.equalsIgnoreCase("GlobalChapterParentVO")) {
                System.out.println("ChpDesc is" + rw.getAttribute("ChpDesc"));
                System.out.println("ChpId is" + rw.getAttribute("ChpId"));
                System.out.println("ChpIdParent is" + rw.getAttribute("ChpIdParent"));
                System.out.println("ChpNm is" + rw.getAttribute("ChpNm"));
                System.out.println("HsCodeis" + rw.getAttribute("HsCode"));

                String chapterId = (String)rw.getAttribute("ChpId");
                System.out.println("id is in parent>>>>>" + chapterId);


                /* String hscode=(String)rw.getAttribute("HsCode");
                    getParentIdBind().setSubmittedValue((String)rw.getAttribute("ChpIdParent"));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(parentIdBind);
                    getIdBind().setSubmittedValue((String)rw.getAttribute("ChpId"));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(idBind);
                    getNameBind().setSubmittedValue((String)rw.getAttribute("ChpNm"));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(nameBind);
                    getDescBind().setSubmittedValue((String)rw.getAttribute("ChpDesc"));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(descBind);
                    getHsBind().setSubmittedValue((String)rw.getAttribute("HsCode"));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(hsBind);*/

                OperationBinding binding =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("searchByCntryNameVo");
                binding.getParamsMap().put("chapId", chapterId);
                binding.execute();
                chapterId=null;
            } else if (rowType.equalsIgnoreCase("GlobalChapterVO")) {
                String chapterId = (String)rw.getAttribute("ChpId");
                System.out.println("id is>>>>>" + chapterId);
                OperationBinding binding =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("searchByCntryNameVo");
                binding.getParamsMap().put("chapId", chapterId);
                binding.execute();
            }
        }


    }


    public void searhByChapterId(ActionEvent actionEvent) {
        
        String chapid=null;
        if (binChapId.getValue()!=null) {
            //Search();
           chapid = binChapId.getValue().toString();
        }
        System.out.println("chapid is  " + chapid);

        OperationBinding binding =
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("searchCurrentROw");
        binding.getParamsMap().put("chapId", chapid);
        binding.execute();

    }

    private void findTreeInView() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        UIViewRoot root = fctx.getViewRoot();
        //hard coding tree component Id with its surrounding naming container ID
        //PanelCollection
        System.out.println("find tree table binding");
        root.invokeOnComponent(fctx, "tt1", new ContextCallback() {
                public void invokeContextCallback(FacesContext facesContext, UIComponent uiComponent) {
                    treeTable = (RichTreeTable)uiComponent;
                }
            });
    }

    public void Search() {
        JUCtrlHierBinding treeBinding = null;
        System.out.println("in search method");
        //get handle to tree if it does not exist. If tree component cannot be
        //found in view, exit this function
        if (treeTable == null) {
            this.findTreeInView();
            if (treeTable == null) {
                //tree not found
                System.out.println("tree not find");
                return;

            }
        }
        //Get the JUCtrlHierbinding reference from the PageDef
        CollectionModel model = (CollectionModel)treeTable.getValue();
        treeBinding = (JUCtrlHierBinding)model.getWrappedData();

        //Read the attributes to search in from the SelectManyChoice component
        //  String searchAttributeArray[] =             (String[])searchAttributes.toArray(new String[searchAttributes.size()]);

        //Define a node to search in. In this example, the root node is used
        JUCtrlHierNodeBinding root = treeBinding.getRootNodeBinding();

        //However, if the user used the "Show as Top" context menu option to
        //shorten the tree display, then we only search starting from this top
        //mode

        List topNode = (List)treeTable.getFocusRowKey();
        // System.out.println("node size is>>>"+topNode.size());
        if (topNode != null) {
            //make top node the root node for the search
            root = treeBinding.findNodeByKeyPath(topNode);
            System.out.println("node is not null>>>");
        }
        System.out.println("node is  null>>>");
        //Select the tree items that match the search criteria and expand the
        //tree to display them
        System.out.println("searchChapName.getValue() = "+searchChapName.getValue());
        RowKeySet resultRowKeySet = null;
        if (searchChapName.getValue() == null) {
            resultRowKeySet = searchTreeNode(root, searchAttributes, searchType, null);
        } else
            resultRowKeySet = searchTreeNode(root, searchAttributes, searchType, searchChapName.getValue().toString());
        RowKeySet disclosedRowKeySet = buildDiscloseRowKeySet(treeBinding, resultRowKeySet);
        treeTable.setSelectedRowKeys(resultRowKeySet);
        treeTable.setDisclosedRowKeys(disclosedRowKeySet);

        AdfFacesContext.getCurrentInstance().addPartialTarget(treeTable);
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
        System.out.println("searchAttributes" + searchAttributes + "searchType" + searchType + "searchString" +
                           searchString);
        System.out.println("in searchTreeNode method");
        RowKeySetImpl rowKeys = new RowKeySetImpl();
        //set default search
        String _searchType = searchType == null ? "START" : searchType.length() > 0 ? searchType : "START";

        //Sanity checks
        if (node == null) {
            //log("Node passed as NULL");
            return rowKeys;
        }
        if (searchAttributes == null) {
            // log(node.getName() + ": search attribute is NULL or has a ZERO length");
            return rowKeys;
        }
        if (searchString == null || searchString.length() < 1) {
            // log(node.getName() + ": Search string cannot be NULL or EMPTY");
            return rowKeys;
        }

        Row nodeRow = node.getRow();
        System.out.println("node row is" + nodeRow);
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
            System.out.println("search type is>>" + _searchType);
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

    private void log(String message) {
        logger.log(ADFLogger.WARNING, message);
    }

    public void resetChapter(ActionEvent actionEvent) {
        searchChapName.setValue("");
    }

    public void setTreeTable(RichTreeTable treeTable) {
        this.treeTable = treeTable;
    }

    public RichTreeTable getTreeTable() {
        return treeTable;
    }


    public void setBindParentId(String bindParentId) {
        this.bindParentId = bindParentId;
    }

    public String getBindParentId() {
        return bindParentId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindName(String bindName) {
        this.bindName = bindName;
    }

    public String getBindName() {
        return bindName;
    }

    public void setBindDes(String bindDes) {
        this.bindDes = bindDes;
    }

    public String getBindDes() {
        return bindDes;
    }

    public void setBindHscode(String bindHscode) {
        this.bindHscode = bindHscode;
    }

    public String getBindHscode() {
        return bindHscode;
    }

    public void setParentIdBind(RichInputText parentIdBind) {
        this.parentIdBind = parentIdBind;
    }

    public RichInputText getParentIdBind() {
        return parentIdBind;
    }

    public void setIdBind(RichInputText idBind) {
        this.idBind = idBind;
    }

    public RichInputText getIdBind() {
        return idBind;
    }

    public void setNameBind(RichInputText nameBind) {
        this.nameBind = nameBind;
    }

    public RichInputText getNameBind() {
        return nameBind;
    }

    public void setDescBind(RichInputText descBind) {
        this.descBind = descBind;
    }

    public RichInputText getDescBind() {
        return descBind;
    }

    public void setHsBind(RichInputText hsBind) {
        this.hsBind = hsBind;
    }

    public RichInputText getHsBind() {
        return hsBind;
    }


    public void setSearchChapId(RichInputListOfValues searchChapId) {
        this.searchChapId = searchChapId;
    }

    public RichInputListOfValues getSearchChapId() {
        return searchChapId;
    }

    public void setBinChapId(RichOutputText binChapId) {
        this.binChapId = binChapId;
    }

    public RichOutputText getBinChapId() {
        return binChapId;
    }


    public void setBindEditButton(RichCommandImageLink bindEditButton) {
        this.bindEditButton = bindEditButton;
    }

    public RichCommandImageLink getBindEditButton() {
        return bindEditButton;
    }

    public void setBindCancelButton(RichCommandImageLink bindCancelButton) {
        this.bindCancelButton = bindCancelButton;
    }

    public RichCommandImageLink getBindCancelButton() {
        return bindCancelButton;
    }

    public void setBindSaveButton(RichCommandImageLink bindSaveButton) {
        this.bindSaveButton = bindSaveButton;
    }

    public RichCommandImageLink getBindSaveButton() {
        return bindSaveButton;
    }


    public void setSearchChapName(RichInputListOfValues searchChapName) {
        this.searchChapName = searchChapName;
    }

    public RichInputListOfValues getSearchChapName() {
        return searchChapName;
    }

}
