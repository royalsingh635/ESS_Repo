package appLanguageSetup.view.bean;

import appLanguageSetup.model.module.LanguageSetupAMImpl;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.layout.RichToolbar;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class LanguageSetupBean {
    private RichPopup lang_pop;
    private static boolean desable=true;
    private static boolean create_desable=false;
    private static boolean edit_desable=false;
    private static boolean save_desable = true;
    private static boolean delete_desable = false;
    private static boolean cancel_desable=true;
    private static boolean tansDesc_visible = true;
    private String mode = "V";
    private RichPopup delete_pop;
    private RichToolbar toolbar;
    private Integer count = -1;
    public LanguageSetupBean() {
        count = (Integer)getBindings().getOperationBinding("on_load_page").execute();
         System.out.println(count + " == LSET");   
    }

    public void create(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
                   operationBinding.execute();
                   
        mode = "C";
        desable = false;
        create_desable = true;
        edit_desable = true;
        save_desable = false;
        delete_desable = true;
        cancel_desable = false;
        tansDesc_visible = false;
        //showPopup(lang_pop, true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(toolbar);
    }

    public void edit(ActionEvent actionEvent) {
        //showPopup(lang_pop, true);
        desable = false;
        create_desable = true;
        edit_desable = true;
        save_desable = false;
        delete_desable = true;
        cancel_desable = false;
       tansDesc_visible = true;
        AdfFacesContext.getCurrentInstance().addPartialTarget(toolbar);
        mode = "E";
    }
    public void save(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");
        operationBinding.execute();   
        operationBinding1.execute();
        if(operationBinding.getErrors().isEmpty()){
                        FacesMessage message = new FacesMessage("Record Saved Successfully!");
                        message.setSeverity(FacesMessage.SEVERITY_INFO);

                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
        desable = true;
        create_desable=false;
        edit_desable=false;
        save_desable = true;
        delete_desable = false;
        cancel_desable=true;
        tansDesc_visible = true;
        mode = "V";
        AdfFacesContext.getCurrentInstance().addPartialTarget(toolbar);
    }

    public void delete(ActionEvent actionEvent) {
        mode = "D";
        LanguageSetupAMImpl am =(LanguageSetupAMImpl)resolvElDC("LanguageSetupAMDataControl");
        ViewObject vo=am.getAppCuntryLang1();
    Integer count=vo.getRowCount();
    System.out.println("Count is------?"+count);
    if(count >0){
        FacesMessage message = new FacesMessage("Can not delete! Child Record Found");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message);
    }
    else{

        showPopup(delete_pop, true);
        create_desable=false;
        edit_desable=false;
        save_desable = true;
        delete_desable = false;
        cancel_desable=true;
        tansDesc_visible = true;
        AdfFacesContext.getCurrentInstance().addPartialTarget(toolbar);
    }
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
    public void cancel(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        mode = "V";
        desable = true;
        create_desable=false;
        edit_desable=false;
        save_desable = true;
        delete_desable = false;
        cancel_desable=true;
        tansDesc_visible = true;
        AdfFacesContext.getCurrentInstance().addPartialTarget(toolbar);
    }
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
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
    public void DeleteDialogListener(DialogEvent dialogEvent) {

    if (dialogEvent.getOutcome().name().equals("yes")) {
    BindingContainer bindings = getBindings();
    OperationBinding operationBinding = bindings.getOperationBinding("Delete");
    operationBinding.execute();
        BindingContainer bindingsa = getBindings();
        OperationBinding operationBindings = bindingsa.getOperationBinding("Commit");
        operationBindings.execute();
    BindingContainer binding = getBindings();
    OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
    createOpBAddress.execute();
    } else if (dialogEvent.getOutcome().name().equals("no")) {
       
        

    }
    }
   /* public void DialogListener(DialogEvent dialogEvent) {
       
        if (dialogEvent.getOutcome().name().equals("ok")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");
            operationBinding.execute();   
            operationBinding1.execute();
            if(operationBinding.getErrors().isEmpty()){
                            FacesMessage message = new FacesMessage("Record Saved Successfully!");
                            message.setSeverity(FacesMessage.SEVERITY_INFO);

                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }
            
        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");
           
            operationBinding.execute();
            operationBinding1.execute();
        }
    
    }*/
    public void CancelListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");
        
        operationBinding.execute();
        operationBinding1.execute();
      
    } 

    public void setLang_pop(RichPopup lang_pop) {
        this.lang_pop = lang_pop;
    }

    public RichPopup getLang_pop() {
        return lang_pop;
    }

    public void setDesable(boolean desable) {
        this.desable = desable;
    }

    public boolean isDesable() {
        return desable;
    }


    public void setCreate_desable(boolean create_desable) {
        this.create_desable = create_desable;
    }

    public boolean isCreate_desable() {
        return create_desable;
    }

    public void setEdit_desable(boolean edit_desable) {
        this.edit_desable = edit_desable;
    }

    public boolean isEdit_desable() {
        return edit_desable;
    }

    public void setSave_desable(boolean save_desable) {
        this.save_desable = save_desable;
    }

    public boolean isSave_desable() {
        return save_desable;
    }

    public void setDelete_desable(boolean delete_desable) {
        this.delete_desable = delete_desable;
    }

    public boolean isDelete_desable() {
        return delete_desable;
    }

    public void setCancel_desable(boolean cancel_desable) {
        this.cancel_desable = cancel_desable;
    }

    public boolean isCancel_desable() {
        return cancel_desable;
    }

    public void setDelete_pop(RichPopup delete_pop) {
        this.delete_pop = delete_pop;
    }

    public RichPopup getDelete_pop() {
        return delete_pop;
    }

     public void setTansDesc_visible(boolean tansDesc_visible) {
        this.tansDesc_visible = tansDesc_visible;
    }

    public boolean isTansDesc_visible() {
        return tansDesc_visible;
    }

    public void setToolbar(RichToolbar toolbar) {
        this.toolbar = toolbar;
    }

    public RichToolbar getToolbar() {
        return toolbar;
    }


    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}
