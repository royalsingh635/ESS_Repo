package slssalespricedetailsapp.view.bean;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.component.UIXSwitcher;

public class SLSSalesPriceDetailsBean {
    
    private StringBuffer facetNameForSwitchingViews = new StringBuffer("graph");
    private UIXSwitcher switcherBind;
    private RichInputListOfValues eoNmBind;

    public SLSSalesPriceDetailsBean() {
    }

    /**
     * ActionEvent to Switch Item Price History ti Table View
     * @param actionEvent
     */
    public void switchToGridViewACTION(ActionEvent actionEvent) {
        this.switcherBind.setFacetName("table");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.switcherBind);
        this.facetNameForSwitchingViews = new StringBuffer("table");
        

    }

    /**
     * ActionEvent to Switch Item Price History to Graph View
     * @param actionEvent
     */
    public void switchToGraphViewACTION(ActionEvent actionEvent) {
        this.switcherBind.setFacetName("graph");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.switcherBind);
        this.facetNameForSwitchingViews = new StringBuffer("graph");
    }

    public void setFacetNameForSwitchingViews(StringBuffer facetNameForSwitchingViews) {
        this.facetNameForSwitchingViews = facetNameForSwitchingViews;
    }

    public StringBuffer getFacetNameForSwitchingViews() {
        return facetNameForSwitchingViews;
    }

    public void setSwitcherBind(UIXSwitcher switcherBind) {
        this.switcherBind = switcherBind;
    }

    public UIXSwitcher getSwitcherBind() {
        return switcherBind;
    }

    /**
     * ActionEvent to search Cutomers
     * @param actionEvent
     */
    public void searchCust(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("searchCustomers").execute();
    }
    
    /**
     * ActionEvent to reset Customers
     * @param actionEvent
     */
    public void resetCust(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("resetCustomers").execute();
    }
    /**
     *  method to get Bindings
     */
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void customerLovVCL(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue() != null){
            System.out.println(":"+valueChangeEvent.getNewValue().toString());
            OperationBinding binding = this.getBindings().getOperationBinding("setEoNmInPriceView");
            binding.getParamsMap().put("eoNm", new StringBuffer(valueChangeEvent.getNewValue().toString()));
            binding.execute();
            
            
        }
    }

    /*
     * Search for cutomers on the main page
     */
    public void searchEoOnMainPageACTION(ActionEvent actionEvent) {
        if(this.eoNmBind.getValue()!= null){
            if(!this.eoNmBind.getValue().toString().equals("")){
                OperationBinding binding = this.getBindings().getOperationBinding("setEoNmInPriceView");
                binding.getParamsMap().put("eoNm", new StringBuffer(this.eoNmBind.getValue().toString()));
                binding.execute();
            }
        }
    }

    public void setEoNmBind(RichInputListOfValues eoNmBind) {
        this.eoNmBind = eoNmBind;
    }

    public RichInputListOfValues getEoNmBind() {
        return eoNmBind;
    }
}
