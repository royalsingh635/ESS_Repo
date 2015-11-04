package appglbllang.view.bean;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class AppGlblLangBean {
   
   
    private RichInputText commentBind;
    private RichInputText langBind;
    private String Mode="V";
    private RichCommandButton editBinding;
    private RichTable tableBind;
    private RichPopup popupBind;
    private RichCommandImageLink editBind;
    private String deleteMode="Not Show"; //if deleteMode="V" then delete option is enabled.

    public AppGlblLangBean() {
    }



    public void setCommentBind(RichInputText commentBind) {
        this.commentBind = commentBind;
    }

    public RichInputText getCommentBind() {
        return commentBind;
    }

    public String searchAction() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("searchCriteria");
        System.out.println("in search");
        operationBinding.getParamsMap().put("language",langBind.getValue());
        operationBinding.getParamsMap().put("comment", commentBind.getValue());
        operationBinding.execute();
        return null;
    }
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String resetAction() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("searchCriteria");
        System.out.println("in search");
       operationBinding.execute();
       langBind.setValue("");
       commentBind.setValue("");
        return null;
    }

    public void setLangBind(RichInputText langBind) {
        this.langBind = langBind;
    }

    public RichInputText getLangBind() {
        return langBind;
    }

    public String createLang() {
        Mode="C";
        tableBind.setRowSelection("none");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        return null;
    }

    public String editlang() {
       Mode="C";
        tableBind.setRowSelection("none");
       AdfFacesContext.getCurrentInstance().addPartialTarget(editBind);
        return null;
    }

    public String deleteLang() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete");
        operationBinding.execute();
        return null;
    }

    public String saveLang() {
        Mode="V";
        tableBind.setRowSelection("single");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        return null;
    }

    public String cancelLang() {
        Mode="V";
        tableBind.setRowSelection("single");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        return null;
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

    public void setMode(String Mode) {
        this.Mode = Mode;
    }

    public String getMode() {
        return Mode;
    }

    public void setEditBinding(RichCommandButton editBinding) {
        this.editBinding = editBinding;
    }

    public RichCommandButton getEditBinding() {
        return editBinding;
    }

    public void setTableBind(RichTable tableBind) {
        this.tableBind = tableBind;
    }

    public RichTable getTableBind() {
        return tableBind;
    }

    public void setPopupBind(RichPopup popupBind) {
        this.popupBind = popupBind;
    }

    public RichPopup getPopupBind() {
        return popupBind;
    }

    public void popUpDL(DialogEvent dialogEvent) {
        // Add event code here...
    }

    public void setEditBind(RichCommandImageLink editBind) {
        this.editBind = editBind;
    }

    public RichCommandImageLink getEditBind() {
        return editBind;
    }

    public void setDeleteMode(String deleteMode) {
        this.deleteMode = deleteMode;
    }

    public String getDeleteMode() {
        return deleteMode;
    }

    public void deleteModeAction(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
    }
}
