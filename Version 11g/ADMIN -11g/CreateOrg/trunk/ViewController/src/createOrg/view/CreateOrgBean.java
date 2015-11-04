package createOrg.view;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class CreateOrgBean {

    private static String disableValue = "true";

    static boolean disable = true;
    static boolean disable_new = false;

    public CreateOrgBean() {
    }


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setDisableValue(String disableValue) {
        this.disableValue = disableValue;
    }

    public String getDisableValue() {
        return disableValue;
    }


    public void newListener(ActionEvent actionEvent) {
        this.setDisableValue("false");
        disable = false;
        disable_new=true;
    }

    public void saveListener(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        BindingContainer bindings1 = getBindings();
        OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute");
        operationBinding1.execute();
        this.setDisableValue("true");
        disable = true;
        disable_new=false;
    }

   /* public void cancelListener(ActionEvent actionEvent) {
        //this.setDisableValue("true");
        BindingContainer bindings2 = getBindings();
        OperationBinding operationBinding2 = bindings2.getOperationBinding("Rollback");
        operationBinding2.execute();
        BindingContainer bindings1 = getBindings();
        OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute");
        operationBinding1.execute();
        this.setDisableValue("true");
        disable = true;
        disable_new=false;
        disable = true;
    }*/

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable_new(boolean disable_new) {
        this.disable_new = disable_new;
    }

    public boolean isDisable_new() {
        return disable_new;
    }

    public String cancel() {
        //this.setDisableValue("true");
        BindingContainer bindings2 = getBindings();
        OperationBinding operationBinding2 = bindings2.getOperationBinding("Rollback");
        operationBinding2.execute();
        BindingContainer bindings1 = getBindings();
        OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute");
        operationBinding1.execute();
        this.setDisableValue("true");
        disable = true;
        disable_new=false;
        disable = true;
        return "CreateNew";
    }
}
