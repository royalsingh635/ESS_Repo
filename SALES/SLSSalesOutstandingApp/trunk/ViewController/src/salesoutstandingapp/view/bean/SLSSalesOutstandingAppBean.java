package salesoutstandingapp.view.bean;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;

public class SLSSalesOutstandingAppBean {
    public SLSSalesOutstandingAppBean() {
    }
     /**
      *  method to get Bindings
      */
     public BindingContainer getBindings() {
     return BindingContext.getCurrent().getCurrentBindingsEntry();
     }
    /**  
     * MethodAction to perform search on the COA
     * */
    public void searchCoaACTION(ActionEvent actionEvent) {
       this.getBindings().getOperationBinding("setCoaNm").execute();
    }
}
