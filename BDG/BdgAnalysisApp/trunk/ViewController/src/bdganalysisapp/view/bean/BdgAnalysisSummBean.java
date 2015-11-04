package bdganalysisapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelSpringboard;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.domain.Number;
import oracle.jbo.rules.JboPrecisionScaleValidator;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.component.UIXIterator;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;


public class BdgAnalysisSummBean {

    private BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
    private RichInputText opValBinding;
    private RichSelectOneChoice operationBinding;
    private RichSelectOneRadio selCrtBinding;
    public String viewFacetNm =
        (resolvElOther("#{pageFlowScope.P_IS_CALL_FRM_OTH}") != null &&
         resolvElOther("#{pageFlowScope.P_IS_CALL_FRM_OTH}").toString().equals("Y") ? "Detail" : "Summary");
    public String prodDtlFacetNm = "Tree";
    public Integer structId = null;
    public String mode = "V";
    public String structNm = null;
    public Integer level = 1;
    public RowKeySet disclosedTreeRowKeySet = null;
    public List breadCrumbList = new ArrayList();
    private RichSelectOneChoice amtNotBinding;
    private RichSelectOneChoice bdgTypeBinding;
    private RichSelectOneRadio compareBasisBinding;
    private RichInputDate stDtBinding;
    private RichInputDate endDtBinding;
    private RichPanelSpringboard panelSpringBoardBinding;
    private RichShowDetailItem tabInSpringBinding;
    private final Integer precision = 26;
    private final Integer scale = 6;
    private UIXIterator springIteratorBinding;


    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setStructId(Integer structId) {
        this.structId = structId;
    }

    public Integer getStructId() {
        return structId;
    }


    //  private Map breadCrumbMap = new HashMap();

    public void setProdDtlFacetNm(String prodDtlFacetNm) {
        this.prodDtlFacetNm = prodDtlFacetNm;
    }

    public String getProdDtlFacetNm() {
        return prodDtlFacetNm;
    }
    private RichInputText searchStructBinding;
    private RichTreeTable treeTabBind; //Summary , Detail

    public void setViewFacetNm(String viewFacetNm) {
        this.viewFacetNm = viewFacetNm;
    }

    public String getViewFacetNm() {
        return viewFacetNm;
    }


    public BdgAnalysisSummBean() {
        System.out.println("facet Name=" + viewFacetNm);
        if (resolvElOther("#{pageFlowScope.P_IS_CALL_FRM_OTH}") != null &&
            resolvElOther("#{pageFlowScope.P_DOC_STRUCT}") != null &&
            resolvElOther("#{pageFlowScope.P_DOC_STRUCT_VAL}") != null) {
            viewFacetNm = "Detail";
            if (resolvElOther("#{pageFlowScope.P_IS_CALL_FRM_OTH}").toString().equals("Y")) {
                Integer structId = Integer.parseInt(resolvElOther("#{pageFlowScope.P_DOC_STRUCT}").toString());
                String structVal = resolvElOther("#{pageFlowScope.P_DOC_STRUCT_VAL}").toString();
                this.callCommonMethod(structId, structVal);
            } else
                viewFacetNm = "Summary";
        }
    }

    private Object resolvElOther(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    /***Method to expand all tree table nodes*/
    private void expandTreeTable() {
        if (this.treeTabBind != null) {
            disclosedTreeRowKeySet = new RowKeySetImpl();
            CollectionModel model = (CollectionModel) treeTabBind.getValue();
            JUCtrlHierBinding treeBinding = (JUCtrlHierBinding) model.getWrappedData();
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
            AdfFacesContext.getCurrentInstance().addPartialTarget(treeTabBind);
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
                rowKey.add(((JUCtrlHierNodeBinding) children.get(i)).getRowKey());
                disclosedTreeRowKeySet.add(rowKey);
                if (((JUCtrlHierNodeBinding) (children.get(i))).getChildren() == null)
                    continue;
                expandTreeChildrenNode(rt, (JUCtrlHierNodeBinding) (node.getChildren().get(i)), rowKey);
            }
        }
    }


    public String processBtnAction() {
        if (opValBinding.getValue() != null && opValBinding.getValue().toString().length() > 0) {
            OperationBinding op = bindings.getOperationBinding("processForDataAnalysis");
            op.getParamsMap().put("structId", structId);
            op.execute();
            showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2352"), "I", false, "F", null);
            //showFacesMessage("Amount has been Processed.", "I", false, "F", null);
        } else {
            showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), "E", false, "F", opValBinding.getClientId());
            //showFacesMessage("Please Enter a Value.", "E", false, "F", opValBinding.getClientId());
        }
        return null;
    }


    public String saveBtnAction() {
        OperationBinding op = bindings.getOperationBinding("Commit");
        op.execute();
        showFacesMessage(ADFModelUtils.resolvRsrc("MSG.91"), "I", false, "F", null);
        //showFacesMessage("Record saved Successfully.", "I", false, "F", null);
        mode = "V";
        return null;
    }


    /**
     *      Method to show validation message(Info,Err,Warn)
     *      mesg:   Message to display
     *      sev:    Severity(I-Info,E-Error,W-Warning)
     *      chk:    true=if resource bundle is used; false=If Resource Bundle is not used.
     *      typFlg: 'F' for FacesMessage , 'V' for ValidatorException
     *      comp:   Client Id of Component (Optional)
     * */
    private void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg, String comp) {
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true) {
            String msg = (String) resolvEl("#{bundle['" + mesg + "']}");
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
            FacesContext.getCurrentInstance().addMessage(comp, message);
        } else if (typFlg.equals("V")) {
            throw new ValidatorException(message);
        }
    }

    /**
     *      Method to Resolve EL expression (D)
     *      D - "EL Expression"
     * */
    private String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = (String) valueExp.getValue(elContext);
        return Message;
    }

    /**
     *      Method to Apply Precision Validation(P,S,V)
     *      P- Precision
     *      S- Scale
     *      V- Value to validate
     * */
    private Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }


    public void setOpValBinding(RichInputText opValBinding) {
        this.opValBinding = opValBinding;
    }

    public RichInputText getOpValBinding() {
        return opValBinding;
    }

    public void setOperationBinding(RichSelectOneChoice operationBinding) {
        this.operationBinding = operationBinding;
    }

    public RichSelectOneChoice getOperationBinding() {
        return operationBinding;
    }

    public void setSelCrtBinding(RichSelectOneRadio selCrtBinding) {
        this.selCrtBinding = selCrtBinding;
    }

    public RichSelectOneRadio getSelCrtBinding() {
        return selCrtBinding;
    }

    public String backToSummaryBtnAction() {
        viewFacetNm = "Summary";
        return null;
    }


    public void showEoBdgDtlActionListener(ActionEvent actionEvent) {
        System.out.println("Inside action listener " + bdgTypeBinding.getValue());
        System.out.println("Globlal param=" + resolvElOther("#{pageFlowScope.P_ANALYSIS_TYPE}"));

        String type = "1";
        if (resolvElOther("#{pageFlowScope.P_ANALYSIS_TYPE}") != null)
            type = resolvElOther("#{pageFlowScope.P_ANALYSIS_TYPE}").toString();
        else if (bdgTypeBinding.getValue() != null)
            type = bdgTypeBinding.getValue().toString();

        if (type.equals("1")) {
            System.out.println("Inside 1");
            prodDtlFacetNm = "Form";
            RichLink ob = (RichLink) actionEvent.getSource();
            OperationBinding opFilter = bindings.getOperationBinding("filterEoBdgProdDtl");
            opFilter.getParamsMap().put("cldId", ob.getAttributes().get("cldIdAtt"));
            opFilter.getParamsMap().put("slocId", ob.getAttributes().get("slocIdAtt"));
            opFilter.getParamsMap().put("hoOrgId", ob.getAttributes().get("hoOrgAtt"));
            opFilter.getParamsMap().put("orgId", ob.getAttributes().get("orgAtt"));
            opFilter.getParamsMap().put("strtDt", ob.getAttributes().get("strtDtAtt"));
            opFilter.getParamsMap().put("endDt", ob.getAttributes().get("endDtAtt"));
            opFilter.getParamsMap().put("regionId", ob.getAttributes().get("regionAtt"));
            opFilter.getParamsMap().put("dtlEoId", ob.getAttributes().get("dtlEoIdAtt"));
            opFilter.getParamsMap().put("headEoId", ob.getAttributes().get("headEoIdAtt"));
            opFilter.execute();
        } else if (type.equals("2")) {
            System.out.println("Inside 2");
            prodDtlFacetNm = "MaterialForm";
            RichLink ob = (RichLink) actionEvent.getSource();
            OperationBinding opFilter = bindings.getOperationBinding("filterMtlBdgProdDtl");
            opFilter.getParamsMap().put("cldId", ob.getAttributes().get("cldIdAtt"));
            opFilter.getParamsMap().put("slocId", ob.getAttributes().get("slocIdAtt"));
            opFilter.getParamsMap().put("hoOrgId", ob.getAttributes().get("hoOrgAtt"));
            opFilter.getParamsMap().put("orgId", ob.getAttributes().get("orgAtt"));
            opFilter.getParamsMap().put("strtDt", ob.getAttributes().get("strtDtAtt"));
            opFilter.getParamsMap().put("endDt", ob.getAttributes().get("endDtAtt"));
            opFilter.getParamsMap().put("regionId", ob.getAttributes().get("regionAtt"));
            opFilter.getParamsMap().put("dtlEoId", ob.getAttributes().get("dtlEoIdAtt"));
            opFilter.getParamsMap().put("headEoId", ob.getAttributes().get("headEoIdAtt"));
            opFilter.execute();
        } else if (type.equals("3")) {
            System.out.println("Inside 3");
            prodDtlFacetNm = "FinancialForm";
            RichLink ob = (RichLink) actionEvent.getSource();
            OperationBinding opFilter = bindings.getOperationBinding("filterFinBdgProdDtl");
            System.out.println("1value=" + ob.getAttributes().get("cldIdAtt"));
            System.out.println("2value=" + ob.getAttributes().get("slocIdAtt"));
            System.out.println("3value=" + ob.getAttributes().get("hoOrgAtt"));
            System.out.println("4value=" + ob.getAttributes().get("orgAtt"));
            System.out.println("5value=" + ob.getAttributes().get("strtDtAtt"));
            System.out.println("6value=" + ob.getAttributes().get("endDtAtt"));
            System.out.println("7value=" + ob.getAttributes().get("regionAtt"));
            System.out.println("8value=" + ob.getAttributes().get("dtlEoIdAtt"));
            System.out.println("9value=" + ob.getAttributes().get("headEoIdAtt"));
            opFilter.getParamsMap().put("cldId", ob.getAttributes().get("cldIdAtt"));
            opFilter.getParamsMap().put("slocId", ob.getAttributes().get("slocIdAtt"));
            opFilter.getParamsMap().put("hoOrgId", ob.getAttributes().get("hoOrgAtt"));
            opFilter.getParamsMap().put("orgId", ob.getAttributes().get("orgAtt"));
            opFilter.getParamsMap().put("strtDt", ob.getAttributes().get("strtDtAtt"));
            opFilter.getParamsMap().put("endDt", ob.getAttributes().get("endDtAtt"));
            opFilter.getParamsMap().put("regionId", ob.getAttributes().get("regionAtt"));
            opFilter.getParamsMap().put("dtlEoId", ob.getAttributes().get("dtlEoIdAtt"));
            opFilter.getParamsMap().put("headEoId", ob.getAttributes().get("headEoIdAtt"));
            opFilter.execute();
            System.out.println("Method executed");
        } else
            System.out.println("No condition is true");
    }

    public void setSearchStructBinding(RichInputText searchStructBinding) {
        this.searchStructBinding = searchStructBinding;
    }

    public RichInputText getSearchStructBinding() {
        return searchStructBinding;
    }

    public void structValVCL(ValueChangeEvent valueChangeEvent) {
        OperationBinding ob = bindings.getOperationBinding("searchStructValNm");
        ob.getParamsMap().put("structValNm", valueChangeEvent.getNewValue());
        ob.execute();
        //  expandTreeTable();
        AdfFacesContext.getCurrentInstance().addPartialTarget(treeTabBind);
    }

    public void setTreeTabBind(RichTreeTable treeTabBind) {
        this.treeTabBind = treeTabBind;
    }

    public RichTreeTable getTreeTabBind() {
        return treeTabBind;
    }

    public void showTreeActionListener(ActionEvent actionEvent) {

    }


    public void springTabDiscloseListener(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
            RichShowDetailItem ob = (RichShowDetailItem) disclosureEvent.getSource();
            System.out.println("Value of Event fire=" + ob.getId());
            structId = (Integer) ob.getAttributes().get("tabIdAtt");
            structNm = (String) ob.getAttributes().get("tabNmAtt");
            level = (Integer) ob.getAttributes().get("tabLvl");
            OperationBinding exeOnTab = bindings.getOperationBinding("executeVoOnTabDiscloure");
            Integer oldLvl = (Integer) ob.getAttributes().get("oldTabLvlAtt");
            if (level != null && oldLvl != null && level.compareTo(oldLvl) > 0) {
                exeOnTab.getParamsMap().put("structId", ob.getAttributes().get("tabIdAtt"));
                exeOnTab.getParamsMap().put("structValDepd", ob.getAttributes().get("structValAtt"));
                exeOnTab.getParamsMap().put("structValBind", null);

            } else {
                exeOnTab.getParamsMap().put("structId", ob.getAttributes().get("tabIdAtt"));
                exeOnTab.getParamsMap().put("structValDepd", null);
                exeOnTab.getParamsMap().put("structValBind", null);

            }
            exeOnTab.execute();
            addToBreadCrumbList(structId, structNm, null, null, level);
        }
    }

    public void setBreadCrumbList(List breadCrumbList) {
        this.breadCrumbList = breadCrumbList;
    }

    public List getBreadCrumbList() {
        return breadCrumbList;
    }


    public void addToBreadCrumbList(Integer structId, String structNm, String structVal, String structValNm,
                                    Integer level) {
        int index = 0;
        for (index = 0; index < breadCrumbList.size(); index++) {
            HashMap tmpMap = (HashMap) breadCrumbList.get(index);
            Integer stId = (Integer) tmpMap.get("struct");
            if (structVal != null) {
                if (tmpMap.get("structVal") != null) {
                    if (stId.equals(structId) && tmpMap.get("structVal").equals(structVal)) {
                        while (breadCrumbList.size() > (index + 1)) {
                            breadCrumbList.remove(index + 1);
                            System.out.println("1..removed from Index=" + (index + 1) + " and size=" +
                                               breadCrumbList.size());
                        }
                        return;
                    }
                } else if (stId.equals(structId)) {
                    while (breadCrumbList.size() > (index + 1)) {
                        breadCrumbList.remove(index + 1);
                        System.out.println("2..removed from Index=" + (index + 1) + " and size=" +
                                           breadCrumbList.size());
                    }
                    break;
                }
            } else {
                if (stId.equals(structId) && tmpMap.get("structVal") == null) {
                    while (breadCrumbList.size() > (index + 1)) {
                        breadCrumbList.remove(index + 1);
                        System.out.println("3..removed from Index=" + (index + 1) + " and size=" +
                                           breadCrumbList.size());
                    }
                    return;
                }
            }
        }

        HashMap crumbMap = new HashMap();
        crumbMap.put("struct", structId);
        crumbMap.put("structNm", structNm);
        crumbMap.put("structVal", structVal);
        crumbMap.put("structValNm", structValNm);
        crumbMap.put("level", level);
        breadCrumbList.add(crumbMap);
        System.out.println(breadCrumbList + " added to List, size= " + breadCrumbList.size());
    }


    public void searchInTabListener(ActionEvent actionEvent) {
        RichLink ob = (RichLink) actionEvent.getSource();
        String structValId = (String) ob.getAttributes().get("structIdVar");
        String structValNm = (String) ob.getAttributes().get("structValNmVar");
        System.out.println("structValId=" + structValId);
        OperationBinding opFilter = bindings.getOperationBinding("filterOrgSummFromStructVal");
        opFilter.getParamsMap().put("val", structValId);
        opFilter.execute();
        addToBreadCrumbList(structId, structNm, structValId, structValNm, level);
    }

    public void setStructNm(String structNm) {
        this.structNm = structNm;
    }

    public String getStructNm() {
        return structNm;
    }

    public void breadCrumbActionListener(ActionEvent actionEvent) {
        RichLink ob = (RichLink) actionEvent.getSource();
        this.structId = (Integer) ob.getAttributes().get("structAtt");
        OperationBinding exeOnTab = bindings.getOperationBinding("executeVoOnTabDiscloure");
        exeOnTab.getParamsMap().put("structId", ob.getAttributes().get("structAtt"));
        exeOnTab.getParamsMap().put("structVal", ob.getAttributes().get("structValAtt"));
        exeOnTab.getParamsMap().put("structValDepd", null);
        exeOnTab.execute();

        UIXIterator uiXIterator = this.getSpringIteratorBinding();
        System.out.println("Child count=" + uiXIterator.getChildCount());
        for (UIComponent child : uiXIterator.getChildren()) {
            RichShowDetailItem sdi = (RichShowDetailItem) child;
            System.out.println("Value of sdi = " + sdi.getText());
            /*  Integer tabId = (Integer) sdi.getAttributes().get("tabIdAtt");
            System.out.println("TabIdAtt = " + tabId);
            System.out.println("this.structId=" + this.structId);
            if (this.structId.equals(tabId))
                sdi.setDisclosed(true);
            else
                sdi.setDisclosed(false); */
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(panelSpringBoardBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tabInSpringBinding);

        //System.out.println("Selected Index =" + ob.getAttributes().get("indexAtt"));
        Integer index = (Integer) ob.getAttributes().get("indexAtt");
        while (breadCrumbList.size() > (index + 1)) {
            //System.out.println("Removing index=" + (breadCrumbList.size() - 1));
            breadCrumbList.remove(breadCrumbList.size() - 1);
        }
        // refreshPage();

    }


    protected void refreshPage() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        String refreshpage = fctx.getViewRoot().getViewId();
        ViewHandler ViewH = fctx.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fctx, refreshpage);
        UIV.setViewId(refreshpage);
        fctx.setViewRoot(UIV);
    }


    public void editSummaryBtnAL(ActionEvent actionEvent) {
        RichLink ob = (RichLink) actionEvent.getSource();
        Integer structId = (Integer) ob.getAttributes().get("structIdAtt");
        String structVal = (String) ob.getAttributes().get("structValAtt");
        this.callCommonMethod(structId, structVal);
    }


    public void callCommonMethod(Integer structId, String structVal) {
        viewFacetNm = "Detail";
        if (structId.equals(new Integer(2))) {
            String type = "1";
            if (resolvElOther("#{pageFlowScope.P_ANALYSIS_TYPE}") != null)
                type = resolvElOther("#{pageFlowScope.P_ANALYSIS_TYPE}").toString();
            else if (bdgTypeBinding.getValue() != null)
                type = bdgTypeBinding.getValue().toString();
            if (type.equals("1")) {
                prodDtlFacetNm = "Form";
            } else if (type.equals("2")) {
                prodDtlFacetNm = "MaterialForm";
            } else if (type.equals("3")) {
                prodDtlFacetNm = "FinancialForm";
            }


            //Filter Item Detail Table on the Basis of Selected Employee, Date Range, Region and Login User
            OperationBinding op = bindings.getOperationBinding("filterTreeHeader");
            op.getParamsMap().put("structId", structId);
            op.getParamsMap().put("structVal", null);
            op.execute();

            OperationBinding opGetParam = bindings.getOperationBinding("fetchParamAndCallFilterMethod");
            opGetParam.execute();


        } else {
            prodDtlFacetNm = "Tree";
            OperationBinding op = bindings.getOperationBinding("filterTreeHeader");
            op.getParamsMap().put("structId", structId);
            op.getParamsMap().put("structVal", structVal);
            op.execute();
        }
    }


    /*
     * Code to update Amount of Item And Item Distribution
     */
    public void distQtyVCL(ValueChangeEvent valueChangeEvent) {
        System.out.println("dist qty vcl");
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        Number val = new Number(0);
        if (valueChangeEvent.getNewValue() != null)
            val = (Number) valueChangeEvent.getNewValue();
        OperationBinding opUpdtAmt = bindings.getOperationBinding("updtBudgetEoProdPrdAmt");
        opUpdtAmt.getParamsMap().put("value", val);
        opUpdtAmt.getParamsMap().put("key", "Q");
        opUpdtAmt.execute();
    }

    public void distItmAmtVCL(ValueChangeEvent valueChangeEvent) {
        System.out.println("dist amt vcl");
        Number val = new Number(0);
        if (valueChangeEvent.getNewValue() != null)
            val = (Number) valueChangeEvent.getNewValue();
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        OperationBinding opUpdtAmt = bindings.getOperationBinding("updtBudgetEoProdPrdAmt");
        opUpdtAmt.getParamsMap().put("value", val);
        opUpdtAmt.getParamsMap().put("key", "A");
        opUpdtAmt.getParamsMap().put("notation", null);
        opUpdtAmt.execute();
    }

    public void itmAmtVCL(ValueChangeEvent valueChangeEvent) {
        Number val = new Number(0);
        if (valueChangeEvent.getNewValue() != null)
            val = (Number) valueChangeEvent.getNewValue();
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        OperationBinding opUpdtAmt = bindings.getOperationBinding("updtBdgEoProdDtl");
        opUpdtAmt.getParamsMap().put("value", val);
        opUpdtAmt.getParamsMap().put("key", "A");
        opUpdtAmt.getParamsMap().put("notation", null);
        opUpdtAmt.execute();
    }

    public void itmPriceVCL(ValueChangeEvent valueChangeEvent) {
        Number val = new Number(0);
        if (valueChangeEvent.getNewValue() != null)
            val = (Number) valueChangeEvent.getNewValue();
        OperationBinding opUpdtAmt = bindings.getOperationBinding("updtBdgEoProdDtl");
        opUpdtAmt.getParamsMap().put("value", val);
        opUpdtAmt.getParamsMap().put("key", "P");
        opUpdtAmt.execute();

    }

    public void itmQtyVCL(ValueChangeEvent valueChangeEvent) {
        Number val = new Number(0);
        if (valueChangeEvent.getNewValue() != null)
            val = (Number) valueChangeEvent.getNewValue();
        OperationBinding opUpdtAmt = bindings.getOperationBinding("updtBdgEoProdDtl");
        opUpdtAmt.getParamsMap().put("value", val);
        opUpdtAmt.getParamsMap().put("key", "Q");
        opUpdtAmt.execute();
    }

    public void nextDisplayBtnAL(ActionEvent actionEvent) {
        bindings.getOperationBinding("Next").execute();
        updateTreeAndFormOnNavigation((RichLink) actionEvent.getSource());
    }

    public void prvDisplayBtnAL(ActionEvent actionEvent) {
        bindings.getOperationBinding("Previous").execute();
        updateTreeAndFormOnNavigation((RichLink) actionEvent.getSource());

    }

    public void lastDisplayBtnAL(ActionEvent actionEvent) {
        bindings.getOperationBinding("Last").execute();
        updateTreeAndFormOnNavigation((RichLink) actionEvent.getSource());

    }

    public void firstDisplayBtnAL(ActionEvent actionEvent) {
        bindings.getOperationBinding("First").execute();
        updateTreeAndFormOnNavigation((RichLink) actionEvent.getSource());

    }

    private void updateTreeAndFormOnNavigation(RichLink ob) {
        // viewFacetNm = "Detail";

        Integer structId = (Integer) ob.getAttributes().get("structIdAtt");
        String structVal = (String) ob.getAttributes().get("structValAtt");
        if (structId.equals(new Integer(2))) {
            //prodDtlFacetNm = "Form";
            //Filter Item Detail Table on the Basis of Selected Employee, Date Range, Region and Login User
            OperationBinding op = bindings.getOperationBinding("filterTreeHeader");
            op.getParamsMap().put("structId", structId);
            op.getParamsMap().put("structVal", null);
            op.execute();
            OperationBinding opGetParam = bindings.getOperationBinding("fetchParamAndCallFilterMethod");
            opGetParam.execute();
        } else {
            // prodDtlFacetNm = "Tree";
            OperationBinding op = bindings.getOperationBinding("filterTreeHeader");
            op.getParamsMap().put("structId", structId);
            op.getParamsMap().put("structVal", structVal);
            op.execute();
        }
    }

    public void editBudgetAL(ActionEvent actionEvent) {
        OperationBinding chkOp = bindings.getOperationBinding("chkUsrDocFreeze");
        chkOp.execute();
        mode = "A";
        /* if (chkOp.getErrors().size() == 0 && chkOp.getResult() != null && chkOp.getResult().toString().equals("N"))
            mode = "A";
        else {
            showFacesMessage("Please Unfreeze Budget from Sales Budget.", "E", false, "F", null);
        } */
    }

    public void resetSearchAL(ActionEvent actionEvent) {
        bindings.getOperationBinding("resetValues").execute();
        amtNotBinding.setValue(null);
        bdgTypeBinding.setValue(null);
        compareBasisBinding.setValue(null);
        stDtBinding.setValue(null);
        endDtBinding.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(amtNotBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bdgTypeBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(compareBasisBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(stDtBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(endDtBinding);
    }

    public void setAmtNotBinding(RichSelectOneChoice amtNotBinding) {
        this.amtNotBinding = amtNotBinding;
    }

    public RichSelectOneChoice getAmtNotBinding() {
        return amtNotBinding;
    }

    public void setBdgTypeBinding(RichSelectOneChoice bdgTypeBinding) {
        this.bdgTypeBinding = bdgTypeBinding;
    }

    public RichSelectOneChoice getBdgTypeBinding() {
        return bdgTypeBinding;
    }

    public void setCompareBasisBinding(RichSelectOneRadio compareBasisBinding) {
        this.compareBasisBinding = compareBasisBinding;
    }

    public RichSelectOneRadio getCompareBasisBinding() {
        return compareBasisBinding;
    }

    public void setStDtBinding(RichInputDate stDtBinding) {
        this.stDtBinding = stDtBinding;
    }

    public RichInputDate getStDtBinding() {
        return stDtBinding;
    }

    public void setEndDtBinding(RichInputDate endDtBinding) {
        this.endDtBinding = endDtBinding;
    }

    public RichInputDate getEndDtBinding() {
        return endDtBinding;
    }

    public void cancelBtnAL(ActionEvent actionEvent) {
        Object amtNotation = amtNotBinding.getValue();
        Object bdgType = bdgTypeBinding.getValue();
        Object compareBasis = compareBasisBinding.getValue();
        Object stDate = stDtBinding.getValue();
        Object endDate = endDtBinding.getValue();

        bindings.getOperationBinding("Rollback").execute();
        mode = "V";

        amtNotBinding.setValue(amtNotation);
        bdgTypeBinding.setValue(bdgType);
        compareBasisBinding.setValue(compareBasis);
        stDtBinding.setValue(stDate);
        endDtBinding.setValue(endDate);
    }

    public void searchButtonAL(ActionEvent actionEvent) {
        // Add event code here...#{bindings.executeVoOnSearch.execute}

        if (stDtBinding.getValue() != null && stDtBinding.getValue().toString().length() > 0)
            if (endDtBinding.getValue() != null && endDtBinding.getValue().toString().length() > 0)
                bindings.getOperationBinding("executeVoOnSearch").execute();
            else
                showFacesMessage(ADFModelUtils.resolvRsrc("MSG.291"), "E", false, "F", endDtBinding.getClientId());
                //showFacesMessage("Please Select End Date", "E", false, "F", endDtBinding.getClientId());
        else
            showFacesMessage(ADFModelUtils.resolvRsrc("MSG.290"), "E", false, "F", stDtBinding.getClientId());
            //showFacesMessage("Please Select Start Date", "E", false, "F", stDtBinding.getClientId());
    }

    public void setPanelSpringBoardBinding(RichPanelSpringboard panelSpringBoardBinding) {
        this.panelSpringBoardBinding = panelSpringBoardBinding;
    }

    public RichPanelSpringboard getPanelSpringBoardBinding() {
        return panelSpringBoardBinding;
    }

    public void setTabInSpringBinding(RichShowDetailItem tabInSpringBinding) {
        this.tabInSpringBinding = tabInSpringBinding;
    }

    public RichShowDetailItem getTabInSpringBinding() {
        return tabInSpringBinding;
    }

    public void itmQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null)
            try {
                if ((new Number(object)).compareTo(new Number(0)) < 0)
                    showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1288"), "E", false, "V", null);
                    //showFacesMessage("Quantity can not be < 0.", "E", false, "V", null);
                if (!ADFBeanUtils.isPrecisionValid(precision, scale, new Number(object)))
                    showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1107"), "E", false, "V", null);
                    //showFacesMessage("Invalid Precision Scale(26,6).", "E", false, "V", null);
            } catch (SQLException e) {
            }
    }

    public void itmAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null)
            try {
                if ((new Number(object)).compareTo(new Number(0)) < 0)
                    showFacesMessage(ADFModelUtils.resolvRsrc("MSG.290"), "E", false, "V", null);
                    //showFacesMessage("Amount can not be < 0.", "E", false, "V", null);
                if (!ADFBeanUtils.isPrecisionValid(precision, scale, new Number(object)))
                    showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1107"), "E", false, "V", null);
                    //showFacesMessage("Invalid Precision Scale(26,6).", "E", false, "V", null);
            } catch (SQLException e) {
            }
    }

    public void distQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null)
            try {
                if ((new Number(object)).compareTo(new Number(0)) < 0)
                    showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1288"), "E", false, "V", null);
                    //showFacesMessage("Quantity can not be < 0.", "E", false, "V", null);
                if (!ADFBeanUtils.isPrecisionValid(precision, scale, new Number(object)))
                    showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1107"), "E", false, "V", null);
                    //showFacesMessage("Invalid Precision Scale(26,6).", "E", false, "V", null);
            } catch (SQLException e) {
            }
    }

    public void distItmAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null)
            try {
                if ((new Number(object)).compareTo(new Number(0)) < 0)
                    showFacesMessage(ADFModelUtils.resolvRsrc("MSG.290"), "E", false, "V", null);
                    //showFacesMessage("Amount can not be < 0.", "E", false, "V", null);
                if (!ADFBeanUtils.isPrecisionValid(precision, scale, new Number(object)))
                    showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1107"), "E", false, "V", null);
                    //showFacesMessage("Invalid Precision Scale(26,6).", "E", false, "V", null);
            } catch (SQLException e) {
            }
    }

    public void adjustAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            try {
                if ((new Number(object)).compareTo(new Number(0)) < 0)
                    showFacesMessage(ADFModelUtils.resolvRsrc("MSG.290"), "E", false, "V", null);
                    //showFacesMessage("Amount can not be < 0.", "E", false, "V", null);
                if (!ADFBeanUtils.isPrecisionValid(precision, scale, new Number(object)))
                    showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1107"), "E", false, "V", null);
                    //showFacesMessage("Invalid Precision Scale(26,6).", "E", false, "V", null);
            } catch (SQLException e) {
            }
        }
    }

    public void setSpringIteratorBinding(UIXIterator springIteratorBinding) {
        this.springIteratorBinding = springIteratorBinding;
    }

    public UIXIterator getSpringIteratorBinding() {
        return springIteratorBinding;
    }

    public void empHierarchyDrawerDL(DisclosureEvent disclosureEvent) {
        OperationBinding op = bindings.getOperationBinding("filterEmpHierarchyVw");
        op.execute();
    }

    public void itmPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            try {
                if ((new Number(object)).compareTo(new Number(0)) < 0)
                    showFacesMessage(ADFModelUtils.resolvRsrc("MSG.979"), "E", false, "V", null);
                    //showFacesMessage("Price can not be < 0.", "E", false, "V", null);
                if (!ADFBeanUtils.isPrecisionValid(precision, scale, new Number(object)))
                    showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1107"), "E", false, "V", null);
                    //showFacesMessage("Invalid Precision Scale(26,6).", "E", false, "V", null);
            } catch (SQLException e) {
            }
        }
    }

    public void mtlItmPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            try {
                if ((new Number(object)).compareTo(new Number(0)) < 0)
                    showFacesMessage(ADFModelUtils.resolvRsrc("MSG.979"), "E", false, "V", null);
                    //showFacesMessage("Price can not be < 0.", "E", false, "V", null);
                if (!ADFBeanUtils.isPrecisionValid(precision, scale, new Number(object)))
                    showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1107"), "E", false, "V", null);
                    //showFacesMessage("Invalid Precision Scale(26,6).", "E", false, "V", null);
            } catch (SQLException e) {
            }
        }
    }

    public void mtlItmQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            try {
                if ((new Number(object)).compareTo(new Number(0)) < 0)
                    showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1288"), "E", false, "V", null);
                    //showFacesMessage("Quantity can not be < 0.", "E", false, "V", null);
                if (!ADFBeanUtils.isPrecisionValid(precision, scale, new Number(object)))
                    showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1107"), "E", false, "V", null);
                    //showFacesMessage("Invalid Precision Scale(26,6).", "E", false, "V", null);
            } catch (SQLException e) {
            }
        }
    }

    public void mtlItmPriceVCL(ValueChangeEvent valueChangeEvent) {
        Number val = new Number(0);
        if (valueChangeEvent.getNewValue() != null) {
            val = (Number) valueChangeEvent.getNewValue();
            OperationBinding opUpdtAmt = bindings.getOperationBinding("updtBdgEoProdDtl");
            opUpdtAmt.getParamsMap().put("value", val);
            opUpdtAmt.getParamsMap().put("key", "P");
            opUpdtAmt.execute();
        }
    }

    public void mtlItmQtyVCL(ValueChangeEvent valueChangeEvent) {
        Number val = new Number(0);
        if (valueChangeEvent.getNewValue() != null) {
            val = (Number) valueChangeEvent.getNewValue();
            OperationBinding opUpdtAmt = bindings.getOperationBinding("updtBdgEoProdDtl");
            opUpdtAmt.getParamsMap().put("value", val);
            opUpdtAmt.getParamsMap().put("key", "Q");
            opUpdtAmt.execute();
        }
    }

    public void expItemAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            try {
                if ((new Number(object)).compareTo(new Number(0)) < 0)
                    showFacesMessage(ADFModelUtils.resolvRsrc("MSG.290"), "E", false, "V", null);
                    //showFacesMessage("Amount can not be < 0.", "E", false, "V", null);
                if (!ADFBeanUtils.isPrecisionValid(precision, scale, new Number(object)))
                    showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1107"), "E", false, "V", null);
                    //showFacesMessage("Invalid Precision Scale(26,6).", "E", false, "V", null);
            } catch (SQLException e) {
            }
        }
    }

    public void expItemAmtVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            OperationBinding ob = bindings.getOperationBinding("updateMtlExpDtl");
            ob.getParamsMap().put("val", valueChangeEvent.getNewValue());
            ob.execute();
        }
    }

    public String showTreeAction() {
        OperationBinding op = bindings.getOperationBinding("getFacetNameToDisclose");
        op.execute();
        if (op.getErrors().size() == 0 && op.getResult() != null && op.getResult().toString().equals("Tree")) {
            prodDtlFacetNm = "Tree";
        } else {
            if (resolvElOther("#{pageFlowScope.P_IS_CALL_FRM_OTH}") != null)
                if (resolvElOther("#{pageFlowScope.P_IS_CALL_FRM_OTH}").toString().equals("Y"))
                    return "back";
                else
                    viewFacetNm = "Summary";
        }
        return null;
    }
}
