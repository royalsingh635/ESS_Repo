package reportconfigapp.view.bean;

import java.io.Serializable;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.server.ViewObjectImpl;

import oracle.jbo.uicli.binding.JUCtrlRangeBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import reportconfigapp.model.module.ReportConFigAMImpl;
import reportconfigapp.model.views.AppReportconfigViewImpl;
import reportconfigapp.model.views.AppReportconfigViewRowImpl;

public class ReportConfigBean implements Serializable {
    private RichPopup addpopup;
    private RichPopup addCogPopup;
    private RichPopup addCoaPopup;
    private RichSelectBooleanCheckbox groupFlag;
    private RichCommandLink selectCoabind;
    private RichCommandButton addMainBinding;
    private RichPopup deleteCogPopup;
    private int toNoOfRows;
    private int pageNumber;
    private int maxPages;
    private static final int RANGE_SIZE = 10;
    private RichTable searchTable;
    private String visb = "true";
    private String flag = "";
    private RichTable cogTable;
    private RichTable coaTable;
    private static Integer count;
    
    /**
     *
     **/
    public static BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    static {
        getBindings().getOperationBinding("on_load_page").execute();
        //count=1;
        count = Integer.parseInt(getBindings().getOperationBinding("on_load_page").getResult().toString());
        System.out.println(count);
        System.out.println("static block");
    }
    
    private Boolean isRenderPage;
    public void setIsRenderPage(Boolean isRenderPage) {
        this.isRenderPage = isRenderPage;
    }

    public Boolean getIsRenderPage() {
        if (count == 1) {
            return false;
        } else {
            return true;
        }
    }


    public ReportConfigBean() {
    }
    private String mode = "";
    /*  private static Integer noOfRows=null;
    private static Integer noOfCogRows=null; */

    /* ReportConFigAMImpl am = (ReportConFigAMImpl)resolvElDC("ReportConFigAMDataControl");
    ViewObjectImpl lovCatVO1=am.getLovCatVO();
    ViewObjectImpl reportconfigView1 = am.getAppReportconfigView1();
    ViewObjectImpl reportconfigView = am.getAppReportconfigView();

    this.getNoOfRows(reportconfigView1.getRowCount());
     noOfCogRows=reportconfigView.getRowCount(); */

    public void editButton(ActionEvent actionEvent) {
        // Add event code here...

        this.setMode("E");
        showPopup(addpopup, true);

    }

    public void showPopup(RichPopup popup, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && popup != null) {
                String popupId = popup.getClientId(context);
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


   /*  public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    } */

    public static Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    public void setAddpopup(RichPopup addpopup) {
        this.addpopup = addpopup;
    }

    public RichPopup getAddpopup() {
        return addpopup;
    }

    /*     public void popupCancelList(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
        createOpBAddress.execute();
    } */

    public void addDialogList(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            if (mode.equalsIgnoreCase("A")) {
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();
                BindingContainer binding = getBindings();
                OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
                createOpBAddress.execute();
            }

        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            BindingContainer bindin = getBindings();
            DCIteratorBinding parentIter = (DCIteratorBinding)bindin.get("LovCatVOIterator");
            Key parentKey = parentIter.getCurrentRow().getKey();

            System.out.println("Key" + parentKey);
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();
            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
            createOpBAddress.execute();

            if (parentIter.getRowSetIterator().getRow(parentKey) !=
                null) { //check condition else gives exception in add mode.
                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
            }

        }
        this.setMode("V");
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


    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public String AddCogButton() {
        // Add event code here...
        ReportConFigAMImpl am = (ReportConFigAMImpl)resolvElDC("ReportConFigAMDataControl");
        ViewObjectImpl v = am.getAppReportconfigView();
        ViewObjectImpl lovCatId = am.getLovCatVO();


        Row row = null;
        Row catRow = null;
        Row newRow = null;

        row = v.getCurrentRow();
        catRow = lovCatId.getCurrentRow();

        if ((catRow != null)) {

            String horg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
            Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
            String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
            Integer attId = (Integer)catRow.getAttribute("AttId");

            if ((row != null)) {


                flag = (String)row.getAttribute("GrpFlg");

            } else {

                flag = "N";

            }

            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
            operationBinding.execute();

            newRow = v.getCurrentRow();


            if (newRow != null) {
                System.out.println(horg_id + "--horg_id-");
                System.out.println(org_id + "--org_id-");
                System.out.println(usr_id + "--usr_id-");
                System.out.println(cld_id + "--cld_id-");
                System.out.println(sloc_id + "--sloc_id-");
                newRow.setAttribute("GrpFlg", flag);
                newRow.setAttribute("CatId", attId);
                newRow.setAttribute("HoOrgId", horg_id);
                newRow.setAttribute("OrgId", org_id);
                newRow.setAttribute("UsrIdCreate", usr_id);
                newRow.setAttribute("CldId", cld_id);
                newRow.setAttribute("SlocId", sloc_id);
                this.setMode("A");
                showPopup(addCogPopup, true);
            }


        }

        return null;

    }

    public void addCogDialogListener(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().equals("ok")) {
            if ((mode.equalsIgnoreCase("E")) || (mode.equalsIgnoreCase("A"))) {
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();
                BindingContainer binding = getBindings();
                OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
                createOpBAddress.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(cogTable);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(cogTable);
            this.setMode("V");
        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            BindingContainer bindin = getBindings();
            DCIteratorBinding parentIter = (DCIteratorBinding)bindin.get("LovCatVOIterator");
            Key parentKey = parentIter.getCurrentRow().getKey();

            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();
            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
            createOpBAddress.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(cogTable);
            if (parentIter.getRowSetIterator().getRow(parentKey) !=
                null) { //check condition else gives exception in add mode.
                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
            }
            this.setMode("V");
            AdfFacesContext.getCurrentInstance().addPartialTarget(cogTable);
        }

    }

    public void setAddCogPopup(RichPopup addCogPopup) {
        this.addCogPopup = addCogPopup;
    }

    public RichPopup getAddCogPopup() {
        return addCogPopup;
    }

    public void addCogPopupCancelListener(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
        BindingContainer bindin = getBindings();
        DCIteratorBinding parentIter = (DCIteratorBinding)bindin.get("LovCatVOIterator");
        Key parentKey = parentIter.getCurrentRow().getKey();

        System.out.println("Key" + parentKey);

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
        createOpBAddress.execute();
        if (parentIter.getRowSetIterator().getRow(parentKey) !=
            null) { //check condition else gives exception in add mode.
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        }
        this.setMode("V");
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(cogTable);
    }

    public String addCoaButton() {
        // Add event code here...
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
        operationBinding.execute();
        String horg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        //Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());

        ReportConFigAMImpl am = (ReportConFigAMImpl)resolvElDC("ReportConFigAMDataControl");
        ViewObjectImpl v = am.getAppReportconfigView();
        Row row = null;

        row = v.getCurrentRow();
        if (row != null) {
            row.setAttribute("HoOrgId", horg_id);
            row.setAttribute("OrgId", org_id);
            //row.setAttribute("UsrIdCreate", usr_id);
            row.setAttribute("CldId", cld_id);
            row.setAttribute("SlocId", sloc_id);
            this.setMode("A");
        }

        return null;
    }

    public void addCoaPopupDialogListener(DialogEvent dialogEvent) {
        // Add event code here...

        if (dialogEvent.getOutcome().name().equals("ok")) {
            if ((mode.equalsIgnoreCase("E")) || (mode.equalsIgnoreCase("A"))) {
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();
                BindingContainer binding = getBindings();
                OperationBinding createOpBAddress = binding.getOperationBinding("Execute1");
                createOpBAddress.execute();
            }

        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();
            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Execute1");
            createOpBAddress.execute();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(coaTable);
        this.setMode("V");
    }

    public void setAddCoaPopup(RichPopup addCoaPopup) {
        this.addCoaPopup = addCoaPopup;
    }

    public RichPopup getAddCoaPopup() {
        return addCoaPopup;
    }

    public void addCoaPopupCancelListener(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
        BindingContainer bindin = getBindings();
        DCIteratorBinding parentIter = (DCIteratorBinding)bindin.get("LovCatVOIterator");
        Key parentKey = parentIter.getCurrentRow().getKey();

        System.out.println("Key" + parentKey);
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        BindingContainer binding = getBindings();
        OperationBinding createOpBinding = binding.getOperationBinding("Execute1");
        createOpBinding.execute();

        if (parentIter.getRowSetIterator().getRow(parentKey) !=
            null) { //check condition else gives exception in add mode.
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(coaTable);
        this.setMode("");
    }

    public String selectCoaLinkAction() {
        // Add event code here...
        showPopup(addCoaPopup, true);
        return null;
    }

    public void TransCatIdValueListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void groupFlagValueChangeListener(ValueChangeEvent vce) {
        // Add event code here...
        ReportConFigAMImpl am = (ReportConFigAMImpl)resolvElDC("ReportConFigAMDataControl");
        ViewObjectImpl reportconfigView = am.getAppReportconfigView();
        System.out.println("length--->" + reportconfigView.getAllRowsInRange().length);
        //Row row1 = reportconfigView.getCurrentRow();
        RowSetIterator rcitr = reportconfigView.createRowSetIterator(null);
        String setFlg = "N";
        Boolean value = (Boolean)vce.getNewValue();
        if (value == true) {
            setFlg = "Y";
        }
        System.out.println("VALUE--" + value);
        while (rcitr.hasNext()) {
            System.out.println("VALUE--" + value + "iterator");

            Row row = rcitr.next();
            AdfFacesContext.getCurrentInstance().addPartialTarget(groupFlag);
            row.setAttribute("GrpFlg", setFlg);

        }
        rcitr.closeRowSetIterator();

        BindingContainer binding = getBindings();
        OperationBinding createOpBinding1 = binding.getOperationBinding("Commit");
        OperationBinding createOpBinding = binding.getOperationBinding("Execute");
        createOpBinding1.execute();
        createOpBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(cogTable);
    }

    public void setGroupFlag(RichSelectBooleanCheckbox groupFlag) {
        this.groupFlag = groupFlag;
    }

    public RichSelectBooleanCheckbox getGroupFlag() {
        return groupFlag;
    }

    public void setSelectCoabind(RichCommandLink selectCoabind) {
        this.selectCoabind = selectCoabind;
    }

    public RichCommandLink getSelectCoabind() {
        return selectCoabind;
    }

    public String deleteButtonAction() {
        // Add event code here...
        showPopup(deleteCogPopup, true);
        return null;
    }

    public void deleteDialogListener(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().equals("yes")) {
            //
            ReportConFigAMImpl am = (ReportConFigAMImpl)resolvElDC("ReportConFigAMDataControl");
            ViewObjectImpl reportconfigCoaVO = am.getReportconfigCoaVO();
            ViewObjectImpl reportconfigView = am.getAppReportconfigView();

            Row rowCag = reportconfigView.getCurrentRow();
            String cogWiseCoa = (String)rowCag.getAttribute("CogId");
            Row[] r = reportconfigCoaVO.getFilteredRows("CogId", cogWiseCoa);
            for (Row r1 : r) {
                r1.remove();
            }
            System.out.println("length--->" + reportconfigCoaVO.getAllRowsInRange().length);


            BindingContainer binding = getBindings();
            OperationBinding createOpBinding1 = binding.getOperationBinding("Delete");
            OperationBinding createOpBinding2 = binding.getOperationBinding("Commit");
            OperationBinding createOpBinding = binding.getOperationBinding("Execute");
            createOpBinding1.execute();
            createOpBinding2.execute();
            createOpBinding.execute();

        }
        if (dialogEvent.getOutcome().name().equals("No")) {
            //
            BindingContainer binding = getBindings();
            OperationBinding createOpBinding1 = binding.getOperationBinding("Rollback");
            OperationBinding createOpBinding = binding.getOperationBinding("Execute");
            createOpBinding1.execute();
            createOpBinding.execute();
        }

        this.setMode("V");
    }

    public void cancelDeleteListener(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
        BindingContainer binding = getBindings();
        OperationBinding createOpBinding1 = binding.getOperationBinding("Rollback");
        OperationBinding createOpBinding = binding.getOperationBinding("Execute");
        createOpBinding1.execute();
        createOpBinding.execute();
        this.setMode("V");
    }


    public void setDeleteCogPopup(RichPopup deleteCogPopup) {
        this.deleteCogPopup = deleteCogPopup;
    }

    public RichPopup getDeleteCogPopup() {
        return deleteCogPopup;
    }

    public String deleteCoaLinkAction() {
        // Add event code here...
        BindingContainer binding;
        binding = getBindings();
        OperationBinding createOpBinding1 = binding.getOperationBinding("Delete1");
        OperationBinding createOpBinding = binding.getOperationBinding("Execute1");
        createOpBinding1.execute();
        createOpBinding.execute();
        return null;
    }

    public String editCoaButtonAction() {
        // Add event code here...
        this.setMode("E");
        return null;
    }

    public String addButtonAction() {
        // Add event code here...
        this.setMode("A");
        return "goToAddCog";
    }

    public String editButtonAction() {
        // Add event code here...

        ReportConFigAMImpl am = (ReportConFigAMImpl)resolvElDC("ReportConFigAMDataControl");
        ViewObjectImpl lovCatVO = am.getLovCatVO();
        ViewObjectImpl reportconfigView = am.getAppReportconfigView1();
        ViewObjectImpl reportconfigMainView = am.getReportConfigMainVO();


        Row rowCat = reportconfigMainView.getCurrentRow();
        //String catId = (String)rowCat.getAttribute("CatId");
        lovCatVO.setWhereClause("ATT_ID =" + Integer.parseInt(rowCat.getAttribute("CatId").toString()));
        lovCatVO.executeQuery();
        BindingContainer bindin = getBindings();

        // DCIteratorBinding parentIter = (DCIteratorBinding)bindin.get("AppReportconfigViewIterator");
        // Key parentKey = parentIter.getCurrentRow().getKey();
        //  Key tosetKey=rowCat.getKey();
        //     System.out.println(parentIter.getAllRowsInRange().length+"Key to set--"+tosetKey);

        // if(parentIter.getRowSetIterator().getRow(tosetKey)!=null){  //check condition else gives exception in add mode.
        //      parentIter.setCurrentRowWithKey(tosetKey.toStringFormat(true));
        //}
        this.setMode("E");
        return "goToAddCog";
    }

    public String editCogButtonAction() {

        showPopup(addCogPopup, true);
        this.setMode("E");
        return null;
    }

    public String backButtonAction() {

        ReportConFigAMImpl am = (ReportConFigAMImpl)resolvElDC("ReportConFigAMDataControl");

        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
        createOpBAddress.execute();
        ViewObjectImpl lovCatVO = am.getLovCatVO();
        lovCatVO.setWhereClause("");
        lovCatVO.executeQuery();
        return "backToMain";
    }

    public void setToNoOfRows(int toNoOfRows) {
        this.toNoOfRows = toNoOfRows;
    }

    public int getToNoOfRows() {
        int y = getSearchView().getIteratorBinding().getNavigatableRowIterator().getRangeStart();
        if (y == -1) {
            y = 0;
        }
        int x = y + (getSearchView().getIteratorBinding().getAllRowsInRange().length);
        return toNoOfRows = x;
    }

    private JUCtrlRangeBinding getSearchView() {
        BindingContext bindingCtx = BindingContext.getCurrent();
        BindingContainer bindings = bindingCtx.getCurrentBindingsEntry();
        return (JUCtrlRangeBinding)bindings.getControlBinding("ReportConfigMainVO");
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageNumber() {
        int val = 0;
        try {
            val = getSearchView().getIteratorBinding().getNavigatableRowIterator().getRangeStart() / RANGE_SIZE + 1;
        } catch (NullPointerException np) {
            val = 1;
        }
        return pageNumber = val;
    }

    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }

    public int getMaxPages() {
        Double x = new Double(getSearchView().getIteratorBinding().getEstimatedRowCount());
        if (getSearchView().getIteratorBinding().getEstimatedRowCount() != 0) {
            Double y = (Double)x / RANGE_SIZE;
            Double u = Math.ceil(y.doubleValue());
            return maxPages = u.intValue();
        } else {
            return 1;
        }
    }

    public String goLastPage() {
        JUCtrlRangeBinding searchView = this.getSearchView();
        long lastPageNum = Math.max(1, (searchView.getIteratorBinding().getEstimatedRowCount() - 1) / RANGE_SIZE + 1);
        if (lastPageNum <= 1)
            return null;
        int prePage = searchView.getIteratorBinding().getNavigatableRowIterator().getRangeStart() / RANGE_SIZE + 1;
        searchView.getIteratorBinding().getNavigatableRowIterator().scrollRange((int)(RANGE_SIZE *
                                                                                      (lastPageNum - prePage)));
        return null;
    }

    public boolean isLastEnabled() {
        JUCtrlRangeBinding searchView = this.getSearchView();
        long lastPageNum = Math.max(1, (searchView.getIteratorBinding().getEstimatedRowCount() - 1) / RANGE_SIZE + 1);
        long currentPage =
            searchView.getIteratorBinding().getNavigatableRowIterator().getRangeStart() / RANGE_SIZE + 1;
        return currentPage < lastPageNum;
    }

    public void rowSelected(SelectionEvent se) {
        JUCtrlRangeBinding searchView = getSearchView();
        searchView.getIteratorBinding().setCurrentRowIndexInRange((Integer)se.getAddedSet().toArray()[0]);
    }


    public void setSearchTable(RichTable searchTable) {
        this.searchTable = searchTable;
    }

    public RichTable getSearchTable() {
        return searchTable;
    }

    public void setVisb(String visb) {
        this.visb = visb;
    }

    public String getVisb() {
        return visb;
    }

    public void setAddMainBinding(RichCommandButton addMainBinding) {
        this.addMainBinding = addMainBinding;
    }

    public RichCommandButton getAddMainBinding() {
        return addMainBinding;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setCogTable(RichTable cogTable) {
        this.cogTable = cogTable;
    }

    public RichTable getCogTable() {
        return cogTable;
    }

    public void setCoaTable(RichTable coaTable) {
        this.coaTable = coaTable;
    }

    public RichTable getCoaTable() {
        return coaTable;
    }
}
