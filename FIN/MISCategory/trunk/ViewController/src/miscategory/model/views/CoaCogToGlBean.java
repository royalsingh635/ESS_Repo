package miscategory.model.views;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class CoaCogToGlBean {
    private RichInputText voucherIdBinding;
    private String VoucherId=null;
    private RichInputText coaNameBinding;

    public CoaCogToGlBean() {
    }

    public String gotoGlTF() {
         VoucherId=voucherIdBinding.getValue().toString();
        return "gl";
    }

    public void setVoucherIdBinding(RichInputText voucherIdBinding) {
        this.voucherIdBinding = voucherIdBinding;
    }

    public RichInputText getVoucherIdBinding() {
        return voucherIdBinding;
    }

    public void setVoucherId(String VoucherId) {
        this.VoucherId = VoucherId;
    }

    public String getVoucherId() {
        return VoucherId;
    }

    public void setCoaNameBinding(RichInputText coaNameBinding) {
        this.coaNameBinding = coaNameBinding;
    }

    public RichInputText getCoaNameBinding() {
        return coaNameBinding;
    }

    public String searchAction() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("searchCriteria");
        operationBinding.getParamsMap().put("coaName", coaNameBinding.getValue().toString());
        operationBinding.execute();
        return null;
    }

    public String resetAction() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("resetCriteria");
        operationBinding.execute();
        coaNameBinding.setValue("");
        return null;
    }
    
   
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
   
}
