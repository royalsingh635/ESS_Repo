package slssaleseocreditlimit.view.bean;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;

public class SLSSalesEoCreditLimitBean {
    public SLSSalesEoCreditLimitBean() {
    }

    /**
     *  method to get Bindings
     */
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    /**
     * ActionEvent to perform Search EOCreditlimitVo
     * @param actionEvent
     */
    public void searchACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("search").execute();
    }
    
    /**
     * ActionEvent to perform reset EOCreditlimitVo
     * @param actionEvent
     */
    public void resetACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("reset").execute();
    }
}
