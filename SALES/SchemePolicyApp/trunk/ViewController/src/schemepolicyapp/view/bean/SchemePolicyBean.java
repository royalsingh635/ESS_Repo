package schemepolicyapp.view.bean;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;

public class SchemePolicyBean {
    public SchemePolicyBean() {
        System.out.println("Hello app");
    }

    private BindingContainer getBinding() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void searchAction(ActionEvent actionEvent) {
        // Add event code here...
        getBinding().getOperationBinding("filterSearchView").execute();
    }

    public void resetAction(ActionEvent actionEvent) {
        // Add event code here...
        getBinding().getOperationBinding("resetSearchView").execute();
    }

    public String addAction() {
        // Add event code here...
        return "Add";
    }

    public String goToViewPage() {
        // Add event code here...
        return "view";
    }
}
