package chartofaccount.view.bean;

import chartofaccount.model.service.ChartOfAccountAMImpl;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class OrgCoa {


    private RichPopup addEditPopup;
    private RichPopup addCoaFypopup;
    private Integer count =(Integer)getBindings().getOperationBinding("on_load_page").execute();
    public OrgCoa() {
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

    
    public BindingContainer getBindings() {
                return BindingContext.getCurrent().getCurrentBindingsEntry();
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

   
   
    public String saveOrgCoa() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
        createOpBAddress.execute();
        
        return null;
    }

    public String cancelOrgCoa() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
        createOpBAddress.execute();
       
        return null;
    }

    public String testForExecute() {
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
        createOpBAddress.execute();
        return null;
    }

    public void setAddEditPopup(RichPopup addEditPopup) {
        this.addEditPopup = addEditPopup;
    }

    public RichPopup getAddEditPopup() {
        return addEditPopup;
    }

    public String back() {
        // Add event code here...
        return null;
    }

    public String createCoa() {
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("CreateInsert");
        createOpBAddress.execute();
        showPopup( addEditPopup,true);
        return null;
    }

    public void addEditDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
                       BindingContainer bindings = getBindings();
                       OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                       operationBinding.execute();

                       BindingContainer binding = getBindings();
                       OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
                       createOpBAddress.execute();


                   } else if (dialogEvent.getOutcome().name().equals("no")) {

                   }
    }

    public void addEditpopupCancelListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
        createOpBAddress.execute();
    }

    public void editButton(ActionEvent actionEvent) {
        showPopup( addEditPopup,true);
    }

    public void setAddCoaFypopup(RichPopup addCoaFypopup) {
        this.addCoaFypopup = addCoaFypopup;
    }

    public RichPopup getAddCoaFypopup() {
        return addCoaFypopup;
    }

    public void coaFyDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
                       BindingContainer bindings = getBindings();
                       OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                       operationBinding.execute();

                       BindingContainer binding = getBindings();
                       OperationBinding createOpBAddress = binding.getOperationBinding("Execute1");
                       createOpBAddress.execute();


                   } else if (dialogEvent.getOutcome().name().equals("no")) {

                   }
    }

    public void coaFyPopupCancelListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Execute1");
        createOpBAddress.execute();
    }

    public void addCoaFY(ActionEvent actionEvent) {
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("CreateInsert1");
        createOpBAddress.execute();
        showPopup( addCoaFypopup,true);
    }

    public void editCoaFyButton(ActionEvent actionEvent) {
        showPopup( addCoaFypopup,true);
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }
}
