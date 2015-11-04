package mmgatepass.view.bean;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class SearchGatePassBean {
    public SearchGatePassBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void createButtonAL(ActionEvent actionEvent) {
    }

    public void searchAL(ActionEvent actionEvent) {
        OperationBinding search=getBindings().getOperationBinding("SearchGP");
        search.execute();
    }

    public void resetAL(ActionEvent actionEvent) {
        OperationBinding reset=getBindings().getOperationBinding("ResetSearch");
        reset.execute();
    }
}
