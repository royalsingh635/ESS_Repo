package svcinvoiceapp.view.bean;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class SVCInvoiceSearchBean {
    public SVCInvoiceSearchBean() {
    }
    
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void searchInvoiceAction(ActionEvent actionEvent) {
        OperationBinding operationbinding = getBindings().getOperationBinding("searchInvoice");
        System.out.println("operationbinding:::" + operationbinding);
        operationbinding.execute();
    }

    public void resetInvoiceBean(ActionEvent actionEvent) {
        OperationBinding operationbinding = getBindings().getOperationBinding("resetInvoice");
        System.out.println("operationbinding:::" + operationbinding);
        operationbinding.execute();
    }
}
