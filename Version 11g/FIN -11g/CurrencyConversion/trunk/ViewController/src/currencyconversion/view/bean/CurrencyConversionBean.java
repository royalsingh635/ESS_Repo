package currencyconversion.view.bean;

import currencyconversion.model.module.CurrencyConversionAMImpl;

import java.util.ArrayList;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class CurrencyConversionBean {
    private RichPopup deletePopup;
    private RichTable currConvTableBind;
    String currFromPage = "";
    String currToFromPage = "";
    private RichPopup jumpToRowPopUp;
    private Integer count =-1;
    private RichSelectOneChoice frmCurrBindVar;
    private RichSelectOneChoice toCurrBindVar;
    private RichInputDate effDateVar;

    public CurrencyConversionBean() {
        
          count =(Integer)getBindings().getOperationBinding("on_load_page").execute();
    }

    public void Save(ActionEvent actionEvent) {
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Commit");
        createOpBAddress.execute();
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void cancel(ActionEvent actionEvent) {
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
        createOpBAddress.execute();
    }

    public void delete(ActionEvent actionEvent) {


        showPopup(deletePopup, true);
    }

    public void setDeletePopup(RichPopup deletePopup) {
        this.deletePopup = deletePopup;
    }

    public RichPopup getDeletePopup() {
        return deletePopup;
    }

    private void showPopup(RichPopup popup, boolean visible) {
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

    public void deleteDialogListener(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("yes")) {
            BindingContainer bindings = getBindings();

            OperationBinding createOpBAddress1 = bindings.getOperationBinding("Delete");
            createOpBAddress1.execute();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();

            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
            createOpBAddress.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(currConvTableBind);

        } else if (dialogEvent.getOutcome().name().equals("no")) {

            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
            createOpBAddress.execute();

            BindingContainer bindings = getBindings();
            OperationBinding OpBAddress = bindings.getOperationBinding("Execute");
            OpBAddress.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(currConvTableBind);
        }

    }

    public void setCurrConvTableBind(RichTable currConvTableBind) {
        this.currConvTableBind = currConvTableBind;
    }

    public RichTable getCurrConvTableBind() {
        return currConvTableBind;
    }

    public void resetTableFilterListener(ActionEvent actionEvent) {
        FilterableQueryDescriptor queryDescriptor = (FilterableQueryDescriptor)getCurrConvTableBind().getFilterModel();
        if (queryDescriptor != null && queryDescriptor.getFilterCriteria() != null) {
            queryDescriptor.getFilterCriteria().clear();
            getCurrConvTableBind().queueEvent(new QueryEvent(getCurrConvTableBind(), queryDescriptor));
            
            AdfFacesContext.getCurrentInstance().addPartialTarget(currConvTableBind);
        }
    }

    public void setCurrFromPage(String currFromPage) {
        this.currFromPage = currFromPage;
    }

    public String getCurrFromPage() {
        return currFromPage;
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

    public void searchButton(ActionEvent actionEvent) {
        CurrencyConversionAMImpl am = (CurrencyConversionAMImpl)resolvElDC("CurrencyConversionAMDataControl");
        ViewObject empVw = am.getAppCurrConv1();
        empVw.setRangeSize(empVw.getRowCount());
        Row row[] = empVw.getAllRowsInRange();
        for (Row r : row) {
            String currFrom = (String)r.getAttribute("CcCurrDesc");
            String currTo = (String)r.getAttribute("CcCurrTxnDesc");
            /*   System.out.println("Currency from---->"+currFrom);
            System.out.println("Currency to---->"+currTo);
            System.out.println("And Curr from page is----->"+currFromPage);
            System.out.println("And Curr to page is----->"+currToFromPage);  */
            if (currFrom.startsWith(currFromPage.toUpperCase()) && (currTo.startsWith(currToFromPage.toUpperCase()))) {
                System.out.println("both matched");
                Key rowKey = r.getKey();
                //make the searched row the current
                CollectionModel collectionModel = (CollectionModel)currConvTableBind.getValue();
                //the table model - CollectionModel - wraps the ADF tre binding for this table
                JUCtrlHierBinding tableBinding = (JUCtrlHierBinding)collectionModel.getWrappedData();
                //get the iterator for the tree binding
                DCIteratorBinding iteratorBinding = tableBinding.getIteratorBinding();
                iteratorBinding.setCurrentRowWithKey(rowKey.toStringFormat(true));
                //create a new table rowKey (the RichTable row key is different from JBO Key
                ArrayList tableRowKey = new ArrayList();
                tableRowKey.add(rowKey);
                RowKeySetImpl rks = new RowKeySetImpl();
                rks.add(tableRowKey);
                currConvTableBind.setSelectedRowKeys(rks);
                //close the search dialog

            }
        }
        jumpToRowPopUp.hide();
        closePopup("p1");
        //refresh table
        AdfFacesContext.getCurrentInstance().addPartialTarget(currConvTableBind);

    }

    private void closePopup(String popup) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        //create the JaavScript expressions
        StringBuffer scriptBuffer = new StringBuffer();
        scriptBuffer.append("var popup = AdfPage.PAGE.findComponentByAbsoluteId('");
        scriptBuffer.append(popup + "');");
        scriptBuffer.append("if(popup.isPopupVisible()==true){");
        scriptBuffer.append("popup.hide();}");
        String script = scriptBuffer.toString();
        //execute the script on the client
        ExtendedRenderKitService extendedRenderKitService =
            Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
        extendedRenderKitService.addScript(fctx, script);
    }

    public void setJumpToRowPopUp(RichPopup jumpToRowPopUp) {
        this.jumpToRowPopUp = jumpToRowPopUp;
    }

    public RichPopup getJumpToRowPopUp() {
        return jumpToRowPopUp;
    }

    public void showPopUpButton(ActionEvent actionEvent) {
        showPopup(jumpToRowPopUp, true);
    }

    public void setCurrToFromPage(String currToFromPage) {
        this.currToFromPage = currToFromPage;
    }

    public String getCurrToFromPage() {
        return currToFromPage;
    }


    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void searchAction(ActionEvent actionEvent) {
        // Add event code here...
        
        BindingContainer binding = getBindings();
        OperationBinding srchOpr = binding.getOperationBinding("searchAction");
        srchOpr.execute();
    }

    public void resetAction(ActionEvent actionEvent) {
        // Add event code here...
        
        frmCurrBindVar.setValue("");
        toCurrBindVar.setValue("");
        effDateVar.setValue("");
        BindingContainer binding = getBindings();
        OperationBinding resetOpr = binding.getOperationBinding("resetAction");
        resetOpr.execute();
    }

    public void setFrmCurrBindVar(RichSelectOneChoice frmCurrBindVar) {
        this.frmCurrBindVar = frmCurrBindVar;
    }

    public RichSelectOneChoice getFrmCurrBindVar() {
        return frmCurrBindVar;
    }

    public void setToCurrBindVar(RichSelectOneChoice toCurrBindVar) {
        this.toCurrBindVar = toCurrBindVar;
    }

    public RichSelectOneChoice getToCurrBindVar() {
        return toCurrBindVar;
    }

    public void setEffDateVar(RichInputDate effDateVar) {
        this.effDateVar = effDateVar;
    }

    public RichInputDate getEffDateVar() {
        return effDateVar;
    }
}
