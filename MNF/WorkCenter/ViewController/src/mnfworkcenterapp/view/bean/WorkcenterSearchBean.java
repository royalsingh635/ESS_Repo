package mnfworkcenterapp.view.bean;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.adf.view.rich.component.rich.input.RichSelectItem;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class WorkcenterSearchBean {

    private String active = "B";
    private RichInputListOfValues createdbyBind;
    private RichInputDate fromDateBind;
    private RichInputDate toDateBind;
    private RichInputListOfValues workCenterCodeBind;
    private RichSelectOneRadio activeBind1;
    private RichSelectItem searchActiveBind;
    private RichSelectItem searchInActiveBind;
    private RichSelectItem searchActiveInactiveBind;
    private RichInputListOfValues searchDepartmentBind;

    public WorkcenterSearchBean() {
    }

    public BindingContainer getBinding() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     * Method to Search WorkCenters
     */
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void onSearch(ActionEvent actionEvent) {
        OperationBinding binding = getBinding().getOperationBinding("search");
        if(this.getActiveBind1().getValue()!=null){
            binding.getParamsMap().put("active",((active == null) ? null : active));
        }
        binding.execute();
    }

    /**
     * Method to reset Search Results
     */
    public void onReset(ActionEvent actionEvent) {
        UIComponent myForm = actionEvent.getComponent();
        oracle.adf.view.rich.util.ResetUtils.reset(myForm);
        getBinding().getOperationBinding("mainSearchReset").execute();
        createdbyBind.setValue("");
        workCenterCodeBind.setValue("");


    }

    public void setCreatedbyBind(RichInputListOfValues createdbyBind) {
        this.createdbyBind = createdbyBind;
    }

    public RichInputListOfValues getCreatedbyBind() {
        return createdbyBind;
    }

    public void setFromDateBind(RichInputDate fromDateBind) {
        this.fromDateBind = fromDateBind;
    }

    public RichInputDate getFromDateBind() {
        return fromDateBind;
    }

    public void setToDateBind(RichInputDate toDateBind) {
        this.toDateBind = toDateBind;
    }

    public RichInputDate getToDateBind() {
        return toDateBind;
    }

    public void setWorkCenterCodeBind(RichInputListOfValues workCenterCodeBind) {
        this.workCenterCodeBind = workCenterCodeBind;
    }

    public RichInputListOfValues getWorkCenterCodeBind() {
        return workCenterCodeBind;
    }

    /**
     * Method to Set Active/Inactive/Both Parameters of WorkCenter
     */
    public void onActiveVCL(ValueChangeEvent valueChangeEvent) {        
        if (activeBind1.getValue().toString().equalsIgnoreCase("Y"))
            active = "Y";
        else if (activeBind1.getValue().toString().equalsIgnoreCase("N"))
            active = "N";
        else
            active = "B";
    }

    public void setActiveBind1(RichSelectOneRadio activeBind1) {
        this.activeBind1 = activeBind1;
    }

    public RichSelectOneRadio getActiveBind1() {
        return activeBind1;
    }

    public void setSearchActiveBind(RichSelectItem searchActiveBind) {
        this.searchActiveBind = searchActiveBind;
    }

    public RichSelectItem getSearchActiveBind() {
        return searchActiveBind;
    }

    public void setSearchInActiveBind(RichSelectItem searchInActiveBind) {
        this.searchInActiveBind = searchInActiveBind;
    }

    public RichSelectItem getSearchInActiveBind() {
        return searchInActiveBind;
    }

    public void setSearchActiveInactiveBind(RichSelectItem searchActiveInactiveBind) {
        this.searchActiveInactiveBind = searchActiveInactiveBind;
    }

    public RichSelectItem getSearchActiveInactiveBind() {
        return searchActiveInactiveBind;
    }

    public void setSearchDepartmentBind(RichInputListOfValues searchDepartmentBind) {
        this.searchDepartmentBind = searchDepartmentBind;
    }

    public RichInputListOfValues getSearchDepartmentBind() {
        return searchDepartmentBind;
    }
}
