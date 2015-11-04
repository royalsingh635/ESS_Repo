package GlblPrfApp.view.bean;

import GlblPrfApp.model.module.GlblPrfAppAMImpl;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelStretchLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.binding.BindingContainer;

import oracle.jbo.Row;

public class GlobalPrfBean {
    private RichPopup popBindVar;
    private RichPopup editPrfSecBindVar;
    private RichPanelFormLayout glblPrfPopbindvar;
    private RichPopup glblPrfPopUp;
    private RichPanelFormLayout panelLayoutFrm;
    private boolean flag=true;
    private boolean flag1=true;
    private boolean disable=true;
    private boolean disable1=true;
    Integer count=-1;

    public GlobalPrfBean() {
        
        count=(Integer)getBindings().getOperationBinding("on_load_page").execute();
    }

    public String editAction() {
      
        showPopup(popBindVar,true);
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
               ExtendedRenderKitService erks = Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
               erks.addScript(context, script.toString());
             }
           }
         } catch (Exception e) {
           throw new RuntimeException(e);
         }
       }

    public void setPopBindVar(RichPopup popBindVar) {
        this.popBindVar = popBindVar;
    }

    public RichPopup getPopBindVar() {
        return popBindVar;
    }

    public void PopupCloseListener(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
        GlblPrfAppAMImpl am =(GlblPrfAppAMImpl)resolvElDC("GlblPrfAppAMDataControl");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("Rollback");
        operationBinding.execute();
    }

    public void dialogListener(DialogEvent dialogEvent) {
        // Add event code here...
        
        if(dialogEvent.getOutcome().name().equals("ok"))
        {
            GlblPrfAppAMImpl am =(GlblPrfAppAMImpl)resolvElDC("GlblPrfAppAMDataControl");
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =bindings.getOperationBinding("Commit");
            operationBinding.execute(); 
            
            FacesMessage msg=new FacesMessage("Successfully Updated.");
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fctx=FacesContext.getCurrentInstance();
            fctx.addMessage(null, msg);
        }
    }
    
    public Object resolvElDC(String data) 
    {
    FacesContext fc = FacesContext.getCurrentInstance();
    Application app = fc.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = fc.getELContext();
    ValueExpression valueExp = elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
    return valueExp.getValue(elContext);
    }
    
    public BindingContainer getBindings() 
    {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String editProfileSecAction() {
        // Add event code here...
        showPopup(editPrfSecBindVar,true);
        return null;
    }

    public void dialogListenerSecPrf(DialogEvent dialogEvent) {
        // Add event code here...
        dialogListener(dialogEvent);
    }

    public void PopupListenerPrfSec(PopupCanceledEvent popupCanceledEvent) {
        PopupCloseListener(popupCanceledEvent);
    }

    public void setEditPrfSecBindVar(RichPopup editPrfSecBindVar) {
        this.editPrfSecBindVar = editPrfSecBindVar;
    }

    public RichPopup getEditPrfSecBindVar() {
        return editPrfSecBindVar;
    }

    public String glblPrfAction() {
        // Add event code here...
        showPopup(glblPrfPopUp,true);
        return null;
    }

    public void setGlblPrfPopbindvar(RichPanelFormLayout glblPrfPopbindvar) {
        this.glblPrfPopbindvar = glblPrfPopbindvar;
    }

    public RichPanelFormLayout getGlblPrfPopbindvar() {
        return glblPrfPopbindvar;
    }

    public void setGlblPrfPopUp(RichPopup glblPrfPopUp) {
        this.glblPrfPopUp = glblPrfPopUp;
    }

    public RichPopup getGlblPrfPopUp() {
        return glblPrfPopUp;
    }

    public void prfDialogListenerAction(DialogEvent dialogEvent) {
        dialogListener(dialogEvent);
    }

    public void PrfPopCancelListener(PopupCanceledEvent popupCanceledEvent) {
        PopupCloseListener(popupCanceledEvent);

    }

    public void setPanelLayoutFrm(RichPanelFormLayout panelLayoutFrm) {
        this.panelLayoutFrm = panelLayoutFrm;
    }

    public RichPanelFormLayout getPanelLayoutFrm() {
        return panelLayoutFrm;
    }


    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public String editPrfAction() {
        flag=false;
        disable=false;
        return null;
    }

    public String cancelAction() {
        rollBack();
        flag=true;
        disable=true;
        return null;
    }

    public String saveAction() {
        GlblPrfAppAMImpl am =(GlblPrfAppAMImpl)resolvElDC("GlblPrfAppAMDataControl");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("Commit");
        operationBinding.execute(); 
        
        FacesMessage msg=new FacesMessage("Record has been Updated Successfully.");
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext fctx=FacesContext.getCurrentInstance();
        fctx.addMessage(null, msg);
        return null;
    }
    
    public void rollBack()
    {
       GlblPrfAppAMImpl am =(GlblPrfAppAMImpl)resolvElDC("GlblPrfAppAMDataControl");
       BindingContainer bindings = getBindings();
       OperationBinding operationBinding =bindings.getOperationBinding("Rollback");
       operationBinding.execute();
   }


    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public String cancelPrfSecAction() {
        // Add event code here...
        rollBack();
        flag1=true;
        disable1=true;
        return null;
    }

    public String editPrfSecAction() {
        flag1=false;
        disable1=false;
        return null;
    }

    public String savePrfSecAction() {
        saveAction();
        flag1=true;
        disable1=true;
        return null;
    }

    public String savePrfAction() {
        // Add event code here...
        saveAction();
        flag=true;
        disable=true;
        return null;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public boolean isDisable() {
        return disable;
    }


    public void setDisable1(boolean disable1) {
        this.disable1 = disable1;
    }

    public boolean isDisable1() {
        return disable1;
    }


    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }
}
