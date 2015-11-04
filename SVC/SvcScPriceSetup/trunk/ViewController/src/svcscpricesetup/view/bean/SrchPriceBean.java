package svcscpricesetup.view.bean;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class SrchPriceBean {
    private RichInputText transLocationSearchBind;

    public SrchPriceBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void searchActionListner(ActionEvent actionEvent) {
        System.out.println("caling serach---");
        OperationBinding obind = getBindings().getOperationBinding("search");
        obind.execute();
        System.out.println(" after caling serach---");
    }

    public void resetActionListner(ActionEvent actionEvent) {
        OperationBinding obind = getBindings().getOperationBinding("reset");
        obind.execute();
    }

    public void setTransLocationSearchBind(RichInputText transLocationSearchBind) {
        this.transLocationSearchBind = transLocationSearchBind;
    }

    public RichInputText getTransLocationSearchBind() {
        return transLocationSearchBind;
    }
}
