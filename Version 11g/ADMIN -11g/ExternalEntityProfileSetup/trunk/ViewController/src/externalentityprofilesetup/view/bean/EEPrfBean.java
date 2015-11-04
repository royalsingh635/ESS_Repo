package externalentityprofilesetup.view.bean;

import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputNumberSpinbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class EEPrfBean {
    private RichPanelFormLayout searchForm;
    private RichPopup createPopUp;
    private RichSelectOneChoice eeTypeName_bind;
    private String flag = "false";
    private String flag1 = "false";
    private RichInputNumberSpinbox eeTypeLegCodeLen_bind;
    private RichInputNumberSpinbox eeTypeAliasLen_bind;
    private RichPopup deletePopUp;
    private RichSelectOneChoice eeTypeEnfPin_bind;
    private RichPopup createGrpPopUp;
    //private RichInputComboboxListOfValues eeTypeId_Bind;

    public EEPrfBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void searchSupplierActionListener(ActionEvent actionEvent) {

        OperationBinding op1 = getBindings().getOperationBinding("searchSupplier");
        op1.execute();

    }

    public String resetAction() {
        // Add event code here...
        AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
        resetValues(ctx, searchForm);
        return null;
    }

    public void setSearchForm(RichPanelFormLayout searchForm) {
        this.searchForm = searchForm;
    }

    public RichPanelFormLayout getSearchForm() {
        return searchForm;
    }

    public void resetValues(AdfFacesContext ctx, UIComponent component) {
        List<UIComponent> items = component.getChildren();
        for (UIComponent item : items) {
            //  resetValues(ctx,component);

            if (item instanceof RichInputDate) {
                RichInputDate itm = (RichInputDate)item;
                if (!itm.isDisabled()) {
                    itm.resetValue();
                    itm.setValue("");
                    ctx.addPartialTarget(itm);
                }
            }

            if (item instanceof RichInputListOfValues) {
                RichInputListOfValues itm = (RichInputListOfValues)item;
                if (!itm.isDisabled()) {
                    itm.resetValue();
                    itm.setValue("");
                    ctx.addPartialTarget(itm);
                }
            }

            if (item instanceof RichSelectOneChoice) {
                RichSelectOneChoice itm = (RichSelectOneChoice)item;
                if (!itm.isDisabled()) {
                    itm.resetValue();
                    itm.setValue("");
                    ctx.addPartialTarget(itm);
                }
            }

        }
    }

    public void createActionListener(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert").execute();
        showPopup(createPopUp, true);

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

    public void setCreatePopUp(RichPopup createPopUp) {
        this.createPopUp = createPopUp;
    }

    public RichPopup getCreatePopUp() {
        return createPopUp;
    }

    public void createPopUpCancelListener(PopupCanceledEvent popupCanceledEvent) {
        flag1 = "false";
        flag = "false";
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
    }

    public void addPopUpDialogListener(DialogEvent dialogEvent) {
        if (DialogEvent.Outcome.ok == dialogEvent.getOutcome()) {
            flag = "false";
            flag1 = "false";

            // if(eeTypeEnfPin_bind.getValue()==null || eeTypeLegCodeLen_bind.getValue()==null || eeTypeAliasLen_bind.getValue()==null){

            if (eeTypeEnfPin_bind.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.208']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(eeTypeEnfPin_bind.getClientId(), message);
            } else if (eeTypeLegCodeLen_bind.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.208']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(eeTypeLegCodeLen_bind.getClientId(), message);
            } else if (eeTypeAliasLen_bind.getValue() == null) {
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.208']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(eeTypeAliasLen_bind.getClientId(), message);
            }


            else {

                 OperationBinding op = getBindings().getOperationBinding("setLen");
                op.getParamsMap().put("LegCodeLen", eeTypeLegCodeLen_bind.getValue());
                op.getParamsMap().put("AliasLen", eeTypeAliasLen_bind.getValue());
                op.execute(); 
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();


            }

            //             AppRolesAMImpl am = (AppRolesAMImpl)resolvElDC("AppRolesAMDataControl");
            //             ViewObject vo = am.getAppSecUsrRole1();
            //            String rnm = usrRoleName.getValue().toString();
            //
            //            Row[] rnme = vo.getFilteredRows("UsrRoleNm", rnm);
            //

            //            if(rnme.length>1){
            //
            //                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.254']}"));
            //                message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //                FacesContext fc = FacesContext.getCurrentInstance();
            //                fc.addMessage(usrRoleName.getClientId(), message);
            //            }
            //            else{
            //                DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("AppSecUsrRole1Iterator");
            //                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
            //                Key parentKey = parentIter.getCurrentRow().getKey();
            //                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
            //                vo.executeQuery();
            //                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
            //
            //                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.227']}"));
            //                message.setSeverity(FacesMessage.SEVERITY_INFO);
            //                FacesContext fc = FacesContext.getCurrentInstance();
            //                fc.addMessage(null, message);
            //
            //            }

        }
    }


    public void EeTypeIdValueChangeListener(ValueChangeEvent valueChangeEvent) {

        //
        //       OperationBinding op = getBindings().getOperationBinding("setEetype_Id_bhav");
        //       op.getParamsMap().put("eeType", valueChangeEvent.getNewValue());
        //       op.execute();
        OperationBinding op = getBindings().getOperationBinding("chkDuplicatePrf");
        op.getParamsMap().put("eeTyp", valueChangeEvent.getNewValue());
        op.execute();
    }

    public void setEeTypeName_bind(RichSelectOneChoice eeTypeName_bind) {
        this.eeTypeName_bind = eeTypeName_bind;
    }

    public RichSelectOneChoice getEeTypeName_bind() {
        return eeTypeName_bind;
    }

    public void deleteExternalEntityPrfActionListener(ActionEvent actionEvent) {
        showPopup(deletePopUp, true);
        //        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete").execute();
        //        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
        //        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();

    }

    public void editActionListener(ActionEvent actionEvent) {
        OperationBinding op = getBindings().getOperationBinding("getEeBhavVal");
        op.execute();
        String Eebehav = (String)op.getResult();

        if (Eebehav.equals("B")) {
            flag1 = "true";

        }

        showPopup(createPopUp, true);
        flag = "true";

    }

    public void EeTypeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        OperationBinding op = getBindings().getOperationBinding("chkDuplicatePrf");
        op.getParamsMap().put("eeTyp", object.toString());
        op.execute();
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getFlag1() {
        return flag1;
    }

    public void EeTypeLegCodeLenValueChangeListener(ValueChangeEvent valueChangeEvent) {

        OperationBinding op = getBindings().getOperationBinding("chkEeTypeLegCodeLen");
        op.getParamsMap().put("eeTypLegCdLen", valueChangeEvent.getNewValue());
        op.execute();
    }

    public void setEeTypeLegCodeLen_bind(RichInputNumberSpinbox eeTypeLegCodeLen_bind) {
        this.eeTypeLegCodeLen_bind = eeTypeLegCodeLen_bind;
    }

    public RichInputNumberSpinbox getEeTypeLegCodeLen_bind() {
        return eeTypeLegCodeLen_bind;
    }

    public void setEeTypeAliasLen_bind(RichInputNumberSpinbox eeTypeAliasLen_bind) {
        this.eeTypeAliasLen_bind = eeTypeAliasLen_bind;
    }

    public RichInputNumberSpinbox getEeTypeAliasLen_bind() {
        return eeTypeAliasLen_bind;
    }

    public void setDeletePopUp(RichPopup deletePopUp) {
        this.deletePopUp = deletePopUp;
    }

    public RichPopup getDeletePopUp() {
        return deletePopUp;
    }

    public void DeleteDialogListener(DialogEvent dialogEvent) {
        if (DialogEvent.Outcome.ok == dialogEvent.getOutcome()) {


            System.out.println("in set 1");

            OperationBinding insTxnOp = getBindings().getOperationBinding("isChildRecordExist");
            insTxnOp.execute();
            if (insTxnOp.getResult() != null) {
                String rst = (String)insTxnOp.getResult();
                System.out.println("return result in bean---"+rst);
                if ("Y".equalsIgnoreCase(rst)) {
                    System.out.println("set 1");
                    FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.10']}"));
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else if ("E".equalsIgnoreCase(rst)) {
                    System.out.println("set 2");
                    FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.945']}"));
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else {
                    System.out.println("set 11212");
                    BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete").execute();
                    BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
                    BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
                    FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.528']}"));
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            }
        }
        deletePopUp.hide();
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

    public void setEeTypeEnfPin_bind(RichSelectOneChoice eeTypeEnfPin_bind) {
        this.eeTypeEnfPin_bind = eeTypeEnfPin_bind;
    }

    public RichSelectOneChoice getEeTypeEnfPin_bind() {
        return eeTypeEnfPin_bind;
    }

    public void setCreateGrpPopUp(RichPopup createGrpPopUp) {
        this.createGrpPopUp = createGrpPopUp;
    }

    public RichPopup getCreateGrpPopUp() {
        return createGrpPopUp;
    }

    public void createGrpActionListener(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert1").execute();
        showPopup(createGrpPopUp, true);

    }

    public void editGrpActionListener(ActionEvent actionEvent) {
        showPopup(createGrpPopUp, true);

    }

    public void createGrpPopUpCancelListener(PopupCanceledEvent popupCanceledEvent) {

        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
    }

    public void addGrpPopUpDialogListener(DialogEvent dialogEvent) {
        if (DialogEvent.Outcome.ok == dialogEvent.getOutcome()) {

            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
        }
    }
}
