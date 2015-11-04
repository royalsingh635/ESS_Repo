package mmpurchasereturn.view.bean;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.ReturnEvent;

public class PurchaseReturnSearchBean {
    public PurchaseReturnSearchBean() {
    }

    public String AddPurchaseReturnButtonAction() {
        return "goToCreate";
    }
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void searchButtonAL(ActionEvent actionEvent) {
        OperationBinding search=getBindings().getOperationBinding("SearchPurchaseReturn");
        search.execute();
    }

    public void resetButtonAL(ActionEvent actionEvent) {
        OperationBinding reset=getBindings().getOperationBinding("ResetSearch");
        reset.execute();
    }

}
