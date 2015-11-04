package mmsoctktakeapp.view.bean;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Timestamp;

public class StockTakeSearchBean {
    private RichInputDate fromDatePgBind;
    private RichInputDate toDatePgBind;
    private RichInputText stkTakeNoPgBind;
    private RichSelectOneChoice prfIdPgBind;

    public StockTakeSearchBean() {
    }
    public BindingContainer getBindings(){
        return BindingContext.getCurrent().getCurrentBindingsEntry();
        }

    public void searchActionListener(ActionEvent actionEvent) {
        OperationBinding binding = getBindings().getOperationBinding("searchStkTake");
        binding.getParamsMap().put("fromdate", fromDatePgBind.getValue());
        binding.getParamsMap().put("todate", toDatePgBind.getValue());
        binding.execute();

    }

    public void resetActionListener(ActionEvent actionEvent) {
        prfIdPgBind.setValue(null);
        stkTakeNoPgBind.setValue(null);
        OperationBinding binding = getBindings().getOperationBinding("resetStkTake");
        binding.execute();
        //searchActionListener(actionEvent);
    }

    public String createnew() {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert").execute(); 
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setDfltPrf").execute();
        return "create";
    }

    public void setFromDatePgBind(RichInputDate fromDatePgBind) {
        this.fromDatePgBind = fromDatePgBind;
    }

    public RichInputDate getFromDatePgBind() {
        return fromDatePgBind;
    }

    public void setToDatePgBind(RichInputDate toDatePgBind) {
        this.toDatePgBind = toDatePgBind;
    }

    public RichInputDate getToDatePgBind() {
        return toDatePgBind;
    }

    public void setStkTakeNoPgBind(RichInputText stkTakeNoPgBind) {
        this.stkTakeNoPgBind = stkTakeNoPgBind;
    }

    public RichInputText getStkTakeNoPgBind() {
        return stkTakeNoPgBind;
    }

    public void setPrfIdPgBind(RichSelectOneChoice prfIdPgBind) {
        this.prfIdPgBind = prfIdPgBind;
    }

    public RichSelectOneChoice getPrfIdPgBind() {
        return prfIdPgBind;
    }
}
