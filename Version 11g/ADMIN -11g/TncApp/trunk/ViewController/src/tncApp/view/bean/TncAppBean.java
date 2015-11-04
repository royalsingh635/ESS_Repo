package tncApp.view.bean;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class TncAppBean {
    private RichPopup pop;
    private RichPopup deletepop;
    Integer count =Integer.parseInt(getBindings().getOperationBinding("on_load_page").execute().toString());
    private RichPanelFormLayout formBind;

    public TncAppBean() {
    }

    public void add(ActionEvent actionEvent) {
      
       System.out.println(count+"====");
        BindingContainer bindings = getBindings();
                   OperationBinding operationBinding = bindings.getOperationBinding("Createwithparameters");
                   operationBinding.execute();
        
        showPopup(pop, true);
    }

    public void edit(ActionEvent actionEvent) {
        // Add event code here...
        showPopup(pop, true);
    }

    public void delete(ActionEvent actionEvent) {
        // Add event code here...
        showPopup(deletepop, true);
    }

    public void save(ActionEvent actionEvent) {
       /*  // Add event code here...
        BindingContainer bindings = getBindings();
                   OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                   operationBinding.execute();
        BindingContainer bindings1 = getBindings();
                   OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute");
                   operationBinding1.execute(); */
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
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void DialogListener(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().equals("ok")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");
            operationBinding.execute();   
            operationBinding1.execute();
            if(operationBinding.getErrors().isEmpty()){
                String msg=resolvElDCMsg("#{bundle['MSG.818']}").toString();//Record Saved Successfully!
                            FacesMessage message = new FacesMessage(msg);
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
        AdfFacesContext.getCurrentInstance().addPartialTarget(formBind);
    }
    public void CancelListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");
        
        operationBinding.execute();
        operationBinding1.execute();
      
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
    public void setPop(RichPopup pop) {
        this.pop = pop;
    }

    public RichPopup getPop() {
        return pop;
    }

    public void setDeletepop(RichPopup deletepop) {
        this.deletepop = deletepop;
    }

    public RichPopup getDeletepop() {
        return deletepop;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }
    public Object resolvElDCMsg(String data) {
           FacesContext fc = FacesContext.getCurrentInstance();
           Application app = fc.getApplication();
           ExpressionFactory elFactory = app.getExpressionFactory();
           ELContext elContext = fc.getELContext();
           ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
           return valueExp.getValue(elContext);
       }

    public void setFormBind(RichPanelFormLayout formBind) {
        this.formBind = formBind;
    }

    public RichPanelFormLayout getFormBind() {
        return formBind;
    }
}
