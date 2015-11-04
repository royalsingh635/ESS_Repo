package applicationGlobalPackage.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCControlBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class AppGlblPkg {

    private RichPopup savepopup;
    private RichPopup deletepopup;
    private RichPopup addpopup;
    private RichPopup editpopup;
    private RichPopup addModPopUp;
    private RichPopup addSmodPopup;
    private RichPopup deleteModPopUp;
    private RichPopup deleteSmodPopUp;


    public AppGlblPkg() {
    }

    public void createDetail(ActionEvent actionEvent) {
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        OperationBinding createOpBAddress = bindings.getOperationBinding("CreateInsert2");
        createOpBAddress.execute();
    }

    public void createInsert(ActionEvent actionEvent) {

        System.out.println("hello");
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        OperationBinding createOpBAddress = bindings.getOperationBinding("CreateInsert");
        createOpBAddress.execute();
        showPopup(addpopup, true);

    }

    public void delete(ActionEvent actionEvent) {


        BindingContainer bindings = getBindings();
        DCControlBinding cb = (DCControlBinding)bindings.get("AppGlblPkgMod");

        String error = resolvEl("#{bundle['APP.ChildRecordExsits']}");
        ViewObject vo = cb.getViewObject();

        Integer count = vo.getRowCount();
        if (count > 0) {
            FacesContext fctx = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(error);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            fctx.addMessage(null, message);
        } else {
            BindingContainer binding = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Delete");
            operationBinding.execute();
            showPopup(deletepopup, true);


        }
    }

    public void edit(ActionEvent actionEvent) {

        showPopup(editpopup, true);


    }

    public void exit(ActionEvent actionEvent) {

        String amDef = "externalentitymaster.model.service";
        String config = "ExternalEntityMasterAMLocal";

        //ExternalEntityMasterAMImpl am =
        //(ExternalEntityMasterAMImpl)Configuration.createRootApplicationModule("externalentitymaster.model.module",                                                                                  config);
        //  System.out.println(am.getUcode());
        //   this.setServerLocId(am.getUCode());
        //  super.create(attributeList);
    }

    public void help(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void save(ActionEvent actionEvent) {
        showPopup(savepopup, true);


        /* BindingContext bindingctx = BindingContext.getCurrent();
                BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
                OperationBinding createOpBAddress = bindings.getOperationBinding("Commit");
                createOpBAddress.execute();  */


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

    public void view(ActionEvent actionEvent) {

        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
        createOpBAddress.execute();
    }

    public void cancel(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding createOpBAddress = bindings.getOperationBinding("Rollback");
        createOpBAddress.execute();


    }


    public void saveDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();


        } else if (dialogEvent.getOutcome().name().equals("cancel")) {

        }

        // Add event code here...
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setSavepopup(RichPopup savepopup) {
        this.savepopup = savepopup;
    }

    public RichPopup getSavepopup() {
        return savepopup;
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

    protected void refreshPage() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String refreshpage = fc.getViewRoot().getViewId();
        ViewHandler ViewH = fc.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fc, refreshpage);
        UIV.setViewId(refreshpage);
        fc.setViewRoot(UIV);
    }

    public void setDeletepopup(RichPopup deletepopup) {
        this.deletepopup = deletepopup;
    }

    public RichPopup getDeletepopup() {
        return deletepopup;
    }

    public void deleteDialogListener(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("yes")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();

            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
            createOpBAddress.execute();
            refreshPage();
            System.out.println("save");

        } else if (dialogEvent.getOutcome().name().equals("no")) {

            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
            createOpBAddress.execute();

            BindingContainer bindings = getBindings();
            OperationBinding OpBAddress = bindings.getOperationBinding("Execute");
            OpBAddress.execute();
        }


    }
    
    public void deleteDialogListenerMod(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("yes")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();

            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Execute1");
            createOpBAddress.execute();
            refreshPage();
            System.out.println("save");

        } else if (dialogEvent.getOutcome().name().equals("no")) {

            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
            createOpBAddress.execute();

            BindingContainer bindings = getBindings();
            OperationBinding OpBAddress = bindings.getOperationBinding("Execute1");
            OpBAddress.execute();
        }
    }
    
    public void deleteDialogListenerSMod(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("yes")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();

            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Execute2");
            createOpBAddress.execute();
            refreshPage();
            System.out.println("save");

        } else if (dialogEvent.getOutcome().name().equals("no")) {

            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
            createOpBAddress.execute();

            BindingContainer bindings = getBindings();
            OperationBinding OpBAddress = bindings.getOperationBinding("Execute2");
            OpBAddress.execute();
        }


    }



    public void addDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Commit");
            createOpBAddress.execute();
            BindingContainer bindings = getBindings();
            OperationBinding OpBAddress = bindings.getOperationBinding("Execute");
            OpBAddress.execute();


        } else if (dialogEvent.getOutcome().name().equals("cancel")) {


        }

    }

    public void addDialogListenerMod(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Commit");
            createOpBAddress.execute();
            BindingContainer bindings = getBindings();
            OperationBinding OpBAddress = bindings.getOperationBinding("Execute1");
            OpBAddress.execute();


        } else if (dialogEvent.getOutcome().name().equals("cancel")) {


        }

    }

    public void addDialogListenerSMod(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Commit");
            createOpBAddress.execute();
            BindingContainer bindings = getBindings();
            OperationBinding OpBAddress = bindings.getOperationBinding("Execute2");
            OpBAddress.execute();


        } else if (dialogEvent.getOutcome().name().equals("cancel")) {


        }
    }

   

    public void addCanceledListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
        createOpBAddress.execute();
        BindingContainer bindings = getBindings();
        OperationBinding OpBAddress = bindings.getOperationBinding("Execute");
        OpBAddress.execute();

    }
    public void CanceledListenerMod(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
        createOpBAddress.execute();
        BindingContainer bindings = getBindings();
        OperationBinding OpBAddress = bindings.getOperationBinding("Execute1");
        OpBAddress.execute();

    }
    public void CanceledListenerSmod(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
        createOpBAddress.execute();
        BindingContainer bindings = getBindings();
        OperationBinding OpBAddress = bindings.getOperationBinding("Execute2");
        OpBAddress.execute();

    }
   

    public void validate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String value = (String)object;
        if ((value == null) || value.length() == 0) {
            return;
        }

        String expression = "[A-Z 0-9_)(\\Q\\/:-,\\E]*";
        CharSequence inputStr = value;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);

        String expression1 = "[^a-z]*";
        CharSequence inputStr1 = value;
        Pattern pattern1 = Pattern.compile(expression1);
        Matcher matcher1 = pattern1.matcher(inputStr1);

        String expression2 = "[^0-9][^a-z]*";
        CharSequence inputStr2 = value;
        Pattern pattern2 = Pattern.compile(expression2);
        Matcher matcher2 = pattern2.matcher(inputStr2);


        String lowerCaseError = resolvEl("#{bundle['APP.LowerCaseCharacters']}");
        String SpecialCharError = resolvEl("#{bundle['APP.SpecialCharacters']}");
        String NumericError = resolvEl("#{bundle['APP.NumericCharacters']}");
        if (!matcher1.matches()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, lowerCaseError, null));
        } else {
            if (!matcher2.matches()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, NumericError, null));
            } else {
                if (!matcher.matches()) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, SpecialCharError,
                                                                  null));
                }

            }
        }
    }
    public void pageAddMod(ActionEvent actionEvent) {

        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        OperationBinding createOpBAddress = bindings.getOperationBinding("CreateInsert1");
        createOpBAddress.execute();
        showPopup(addModPopUp, true);


    }

    public void pageAddSmod(ActionEvent actionEvent) {
        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        OperationBinding createOpBAddress = bindings.getOperationBinding("CreateInsert2");
        createOpBAddress.execute();
        showPopup(addSmodPopup, true);
    }

    public void deleteModule(ActionEvent actionEvent) {


        BindingContainer bindings = getBindings();
        DCControlBinding cb = (DCControlBinding)bindings.get("AppGlblPkgSmod");

        String error = resolvEl("#{bundle['APP.ChildRecordExsits']}");
        ViewObject vo = cb.getViewObject();

        Integer count = vo.getRowCount();
        if (count > 0) {
            FacesContext fctx = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(error);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            fctx.addMessage(null, message);
        } else {
            BindingContainer binding = getBindings();
            OperationBinding operationBinding = binding.getOperationBinding("Delete1");
            operationBinding.execute();
            showPopup(deleteModPopUp, true);

        }
    }

    public void deleteSubmodules(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete2");
        operationBinding.execute();
showPopup(deleteSmodPopUp, true);
    }


    public void setAddModPopUp(RichPopup addModPopUp) {
        this.addModPopUp = addModPopUp;
    }

    public RichPopup getAddModPopUp() {
        return addModPopUp;
    }

    public void editMod(ActionEvent actionEvent) {
        showPopup(addModPopUp, true);
    }

    public void setAddSmodPopup(RichPopup addSmodPopup) {
        this.addSmodPopup = addSmodPopup;
    }

    public RichPopup getAddSmodPopup() {
        return addSmodPopup;
    }
    public void setAddpopup(RichPopup addpopup) {
        this.addpopup = addpopup;
    }

    public RichPopup getAddpopup() {
        return addpopup;
    }
    public void setEditpopup(RichPopup editpopup) {
        this.editpopup = editpopup;
    }

    public RichPopup getEditpopup() {
        return editpopup;
    }

    public void setDeleteModPopUp(RichPopup deleteModPopUp) {
        this.deleteModPopUp = deleteModPopUp;
    }

    public RichPopup getDeleteModPopUp() {
        return deleteModPopUp;
    }

    public void editSmod(ActionEvent actionEvent) {
        showPopup(addSmodPopup, true);
    }

    public void setDeleteSmodPopUp(RichPopup deleteSmodPopUp) {
        this.deleteSmodPopUp = deleteSmodPopUp;
    }

    public RichPopup getDeleteSmodPopUp() {
        return deleteSmodPopUp;
    }
}
