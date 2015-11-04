package appWorkflow.view;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import appWorkflow.view.WorkflowBean;
import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class AddPageBean {
    private RichPopup pop;
    private RichPopup poplvl;
    private RichPopup poplvlusr;

    public AddPageBean() {
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
        if (dialogEvent.getOutcome().name().equals("ok")) {


            BindingContainer bindings3 = getBindings();
            OperationBinding operationBinding3 = bindings3.getOperationBinding("Commit");
            operationBinding3.execute();
            BindingContainer bindings4 = getBindings();
            OperationBinding operationBinding4 = bindings4.getOperationBinding("Execute");
            operationBinding4.execute();
            
            WorkflowBean e =new WorkflowBean();
           String a= e.getCancelButtonAdd();
           String b=e.getSaveButtonAdd();
           System.out.println("aa========"+a+"-----------b========"+b);
            e.setCancelButtonAdd("true");
            e.setSaveButtonAdd("true");
            String c= e.getCancelButtonAdd();
            String d=e.getSaveButtonAdd();
            System.out.println("c========"+c+"-----------d========"+d);

        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");

            operationBinding.execute();
            operationBinding1.execute();
        }
    }

    public void CancelListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");

        operationBinding.execute();
        operationBinding1.execute();
    }

    public void setPop(RichPopup pop) {
        this.pop = pop;
    }

    public RichPopup getPop() {
        return pop;
    }

    public void Create(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        showPopup(pop, true);
    }


//---------------WF$lvl---------------
    
    
    public void DialogLvlListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            BindingContainer bindings3 = getBindings();
            OperationBinding operationBinding3 = bindings3.getOperationBinding("Commit");
            operationBinding3.execute();
            BindingContainer bindings4 = getBindings();
            OperationBinding operationBinding4 = bindings4.getOperationBinding("Execute1");
            operationBinding4.execute();
          
        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute1");

            operationBinding.execute();
            operationBinding1.execute();
        }
    }

    public void setPoplvl(RichPopup poplvl) {
        this.poplvl = poplvl;
    }

    public RichPopup getPoplvl() {
        return poplvl;
    }

    public void CancelLvlListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute1");

        operationBinding.execute();
        operationBinding1.execute();
    }

    public void CreateLvl(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
        operationBinding.execute();
        showPopup(poplvl, true);
    }
    
    
   //------------------------WF$LVL$USR-------------------------


    public void CreateLvlUser(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert2");
        operationBinding.execute();
        showPopup(poplvlusr, true);
    }

    public void setPoplvlusr(RichPopup poplvlusr) {
        this.poplvlusr = poplvlusr;
    }

    public RichPopup getPoplvlusr() {
        return poplvlusr;
    }

    public void CancelLvlUsrListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute2");

        operationBinding.execute();
        operationBinding1.execute();
    }

    public void DialogLvlUsrListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            BindingContainer bindings3 = getBindings();
            OperationBinding operationBinding3 = bindings3.getOperationBinding("Commit");
            operationBinding3.execute();
            BindingContainer bindings4 = getBindings();
            OperationBinding operationBinding4 = bindings4.getOperationBinding("Execute2");
            operationBinding4.execute();
            
            

        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute2");

            operationBinding.execute();
            operationBinding1.execute();
        }
    }
}
