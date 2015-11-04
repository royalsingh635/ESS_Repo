package currencyconversion.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import currencyconversion.model.module.CurrencyConversionAMImpl;

import java.util.ArrayList;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichToolbar;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
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
    private Integer count = -1;
    private RichSelectOneChoice frmCurrBindVar;
    private RichSelectOneChoice toCurrBindVar;
    private RichInputDate effDateVar;
    private String mode = "V";
    private String currmode = "V";
    private RichInputText baseCurrBuyBind;
    private RichInputText baseCurrSellBind;
    private RichSelectOneChoice buyingCurrBind;
    private String currConvRpAllow = "Y";
    private String multiCurrConvAllow;
    private RichPopup replicatePopUpBinding;
    private RichToolbar buttonBarBinding;
    private RichPanelGroupLayout panelGroupBind;

    public void setMultiCurrConvAllow(String multiCurrConvAllow) {
        this.multiCurrConvAllow = multiCurrConvAllow;
    }

    public String getMultiCurrConvAllow() {
        Object obj = ADFBeanUtils.callBindingsMethod("getValueFromAppProfile", new Object[] { "mul" }, new Object[] {
                                                     "mode" });
        if (obj != null)
            return obj.toString();
        else
            return multiCurrConvAllow;
    }

    public void setCurrConvRpAllow(String currConvRpAllow) {
        this.currConvRpAllow = currConvRpAllow;
    }

    public String getCurrConvRpAllow() {
        Object obj = ADFBeanUtils.callBindingsMethod("getValueFromAppProfile", new Object[] { "rep" }, new Object[] {
                                                     "mode" });
        if (obj != null) {
            return obj.toString();
        } else
            return currConvRpAllow;
    }

    public void setCurrmode(String currmode) {
        this.currmode = currmode;
    }

    public String getCurrmode() {
        return currmode;
    }

    public CurrencyConversionBean() {
        count = (Integer) getBindings().getOperationBinding("on_load_page").execute();
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
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

    /*  public void resetTableFilterListener(ActionEvent actionEvent) {
        FilterableQueryDescriptor queryDescriptor = (FilterableQueryDescriptor) getCurrConvTableBind().getFilterModel();
        if (queryDescriptor != null && queryDescriptor.getFilterCriteria() != null) {
            queryDescriptor.getFilterCriteria().clear();
            getCurrConvTableBind().queueEvent(new QueryEvent(getCurrConvTableBind(), queryDescriptor));

            AdfFacesContext.getCurrentInstance().addPartialTarget(currConvTableBind);
        }
    } */

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
        CurrencyConversionAMImpl am = (CurrencyConversionAMImpl) resolvElDC("CurrencyConversionAMDataControl");
        ViewObject empVw = am.getAppCurrConv1();
        empVw.setRangeSize(empVw.getRowCount());
        Row row[] = empVw.getAllRowsInRange();
        for (Row r : row) {
            String currFrom = (String) r.getAttribute("CcCurrDesc");
            String currTo = (String) r.getAttribute("CcCurrTxnDesc");
            if (currFrom.startsWith(currFromPage.toUpperCase()) && (currTo.startsWith(currToFromPage.toUpperCase()))) {
                Key rowKey = r.getKey();
                //make the searched row the current
                CollectionModel collectionModel = (CollectionModel) currConvTableBind.getValue();
                //the table model - CollectionModel - wraps the ADF tre binding for this table
                JUCtrlHierBinding tableBinding = (JUCtrlHierBinding) collectionModel.getWrappedData();
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
        ADFBeanUtils.callBindingsMethod("searchAction", null, null);
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

    public String addCurrencyConAction() {
        mode = "A";
        currmode = "A";
        ADFBeanUtils.callBindingsMethod("CreateInsert", null, null);
        ADFBeanUtils.callBindingsMethod("setCurrIdTxn", null, null);
        return null;
    }

    public void setBaseCurrBuyBind(RichInputText baseCurrBuyBind) {
        this.baseCurrBuyBind = baseCurrBuyBind;
    }

    public RichInputText getBaseCurrBuyBind() {
        return baseCurrBuyBind;
    }

    public void setBaseCurrSellBind(RichInputText baseCurrSellBind) {
        this.baseCurrSellBind = baseCurrSellBind;
    }

    public RichInputText getBaseCurrSellBind() {
        return baseCurrSellBind;
    }

    public void CurrConSaveActionListener(ActionEvent actionEvent) {
        Number buyCurrRate = new Number(0);
        Number sellCurrRate = new Number(0);
        String message = "";
        if (baseCurrSellBind.getValue() != null || baseCurrSellBind.getValue() != "")
            sellCurrRate = (Number) baseCurrSellBind.getValue();
        if (baseCurrBuyBind.getValue() != null || baseCurrBuyBind.getValue() != "")
            buyCurrRate = (Number) baseCurrBuyBind.getValue();
        if (buyingCurrBind.getValue() == null || buyingCurrBind.getValue() == "") {
            message = "Currency is required .";
            ADFModelUtils.showFacesMessage(message, message, FacesMessage.SEVERITY_ERROR, buyingCurrBind.getClientId());
        } else if (buyCurrRate.compareTo(new Number(0)) == 0 || buyCurrRate.compareTo(new Number(0)) < 0) {
            message = "Value should be greater than 0. ";
            ADFModelUtils.showFacesMessage(message, message, FacesMessage.SEVERITY_ERROR,
                                           baseCurrBuyBind.getClientId());
        } else if (sellCurrRate.compareTo(new Number(0)) == 0 || sellCurrRate.compareTo(new Number(0)) < 0) {
            message = "Value should be greater than 0. ";
            ADFModelUtils.showFacesMessage(message, message, FacesMessage.SEVERITY_ERROR,
                                           baseCurrSellBind.getClientId());
        } else if (sellCurrRate.compareTo(buyCurrRate) > 0) {
            message = "Buying rate should be greater than Selling rate";
            ADFModelUtils.showFacesMessage(message, message, FacesMessage.SEVERITY_ERROR,
                                           baseCurrBuyBind.getClientId());
        } else {
            Object result = ADFBeanUtils.callBindingsMethod("checkDplicateBaseAndBuyingCurr", null, null);
           if (result.toString().equalsIgnoreCase("N")) {
                message = "Only one Entry is allowed for the selected Currency";
                ADFModelUtils.showFacesMessage(message, message, FacesMessage.SEVERITY_ERROR,
                                               buyingCurrBind.getClientId());

            } else {
                if (getCurrConvRpAllow().toString().equalsIgnoreCase("Y")&& mode.toString().equalsIgnoreCase("A")) {
                    showPopup(replicatePopUpBinding, true);
                } else {
                    message="Record updated successfully !";
                    ADFBeanUtils.callBindingsMethod("Commit", null, null);
                    ADFModelUtils.showFacesMessage(message, message, FacesMessage.SEVERITY_INFO,
                                                   null);
                    mode = "V";
                    currmode = "V";
                }
            }
        }
    }

    public void baseBuyingCurrValueChangeListener(ValueChangeEvent vce) {
        if (vce.getNewValue() != null)
            ADFBeanUtils.callBindingsMethod("setSpecificBuyCurr", new Object[] { vce.getNewValue() }, new Object[] {
                                            "baseBuyCurr" });

    }

    public void baseSellCurrVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null)
            ADFBeanUtils.callBindingsMethod("setSpecificSellCurr", new Object[] { vce.getNewValue() }, new Object[] {
                                            "baseSellCurr" });
    }

    public void currIdTxnVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            Object result = ADFBeanUtils.callBindingsMethod("setBuyOrCellCurrVal", new Object[] { vce.getNewValue() }, new Object[] {
                                                            "currIdTxn" });
            if (result.toString().equalsIgnoreCase("Y"))
                currmode = "V";
            else
                currmode = "A";

        }
    }

    public void currConDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String ccDt = object.toString().substring(0, 10);
            oracle.jbo.domain.Date dt = new oracle.jbo.domain.Date();
            String sysdt = dt.getCurrentDate().dateValue().toString();
            if (ccDt.compareTo(sysdt) > 0) {
                FacesMessage errMsg = new FacesMessage("Invalid Date !");
                errMsg.setDetail("Date should be less than or equal to current date");
                throw new ValidatorException(errMsg);
            } else {
                Object result = ADFBeanUtils.callBindingsMethod("checkForDupliCCDate", new Object[] { object }, new Object[] {
                                                                "currEffDt" });
                if (result.toString().equalsIgnoreCase("Y"))
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "record for this date already exists", null));
                else if (result.toString().equalsIgnoreCase("E"))
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Given Conversion Date is equal to or Less than Latest Conversion Date Entry for the Given Currency",
                                                                  null));
            }

        }
    }

    public void currConEditActionListener(ActionEvent actionEvent) {
        Object result = ADFBeanUtils.callBindingsMethod("checkEditAllow", null, null);
        if (result.toString().equalsIgnoreCase("Y")) {
            mode = "E";
            currmode = "E";
        } else {
            String msg =
                "Conversion Date  Less than Latest Conversion Date Entry for the selected Currency.So you can not edit.";
            ADFModelUtils.showFacesMessage(msg, msg, FacesMessage.SEVERITY_INFO, null);
        }
    }

    public void currConCancelActionListener(ActionEvent actionEvent) {
        ADFBeanUtils.callBindingsMethod("Rollback", null, null);
        mode = "V";
        currmode = "V";
    }

    public void setBuyingCurrBind(RichSelectOneChoice buyingCurrBind) {
        this.buyingCurrBind = buyingCurrBind;
    }

    public RichSelectOneChoice getBuyingCurrBind() {
        return buyingCurrBind;
    }

    public void replicateaAndSaveActionListener(ActionEvent actionEvent) {
        ADFBeanUtils.callBindingsMethod("replicateToAllOrganization", null, null);
        ADFBeanUtils.callBindingsMethod("Commit", null, null);
        mode = "V";
        currmode = "V";
        replicatePopUpBinding.hide();
        AdfFacesContext.getCurrentInstance().addPartialTarget(buttonBarBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelGroupBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(currConvTableBind);
    }

    public void dontReplicateAndSaveActionListener(ActionEvent actionEvent) {
        ADFBeanUtils.callBindingsMethod("Commit", null, null);
        mode = "V";
        currmode = "V";
        replicatePopUpBinding.hide();
        AdfFacesContext.getCurrentInstance().addPartialTarget(buttonBarBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelGroupBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(currConvTableBind);
    }


    public void setReplicatePopUpBinding(RichPopup replicatePopUpBinding) {
        this.replicatePopUpBinding = replicatePopUpBinding;
    }

    public RichPopup getReplicatePopUpBinding() {
        return replicatePopUpBinding;
    }

    public void setButtonBarBinding(RichToolbar buttonBarBinding) {
        this.buttonBarBinding = buttonBarBinding;
    }

    public RichToolbar getButtonBarBinding() {
        return buttonBarBinding;
    }

    public void setPanelGroupBind(RichPanelGroupLayout panelGroupBind) {
        this.panelGroupBind = panelGroupBind;
    }

    public RichPanelGroupLayout getPanelGroupBind() {
        return panelGroupBind;
    }
}
