package mminvoice.view.beans;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class InvoiceSrchBean {
    private RichInputListOfValues invcNoBindVar;
    private RichInputListOfValues eoNmBindVar;
    private RichSelectOneChoice currBindVar;
    private RichSelectOneChoice invcTypeBindVar;
    private RichInputDate fromDateBindVar;
    private RichInputDate toDateBindVar;
    private RichInputText fromSpAmtBindVar;
    private RichInputText toSpAmtBindVar;

    public InvoiceSrchBean() {
    }

    public void searchAction(ActionEvent actionEvent) {
        // Add event code here...
         OperationBinding srchOp = getBindings().getOperationBinding("searchAction");
         srchOp.execute();
    }

    public void resetAction(ActionEvent actionEvent) {
        invcNoBindVar.setValue("");
        eoNmBindVar.setValue("");
        currBindVar.setValue("");
        invcTypeBindVar.setValue("");
        fromDateBindVar.setValue("");
        toDateBindVar.setValue("");
        fromSpAmtBindVar.setValue("");
        toSpAmtBindVar.setValue("");
        OperationBinding resetOp = getBindings().getOperationBinding("resetAction");
         resetOp.execute();
    }
    
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setInvcNoBindVar(RichInputListOfValues invcNoBindVar) {
        this.invcNoBindVar = invcNoBindVar;
    }

    public RichInputListOfValues getInvcNoBindVar() {
        return invcNoBindVar;
    }

    public void setEoNmBindVar(RichInputListOfValues eoNmBindVar) {
        this.eoNmBindVar = eoNmBindVar;
    }

    public RichInputListOfValues getEoNmBindVar() {
        return eoNmBindVar;
    }

    public void setCurrBindVar(RichSelectOneChoice currBindVar) {
        this.currBindVar = currBindVar;
    }

    public RichSelectOneChoice getCurrBindVar() {
        return currBindVar;
    }

    public void setInvcTypeBindVar(RichSelectOneChoice invcTypeBindVar) {
        this.invcTypeBindVar = invcTypeBindVar;
    }

    public RichSelectOneChoice getInvcTypeBindVar() {
        return invcTypeBindVar;
    }

    public void setFromDateBindVar(RichInputDate fromDateBindVar) {
        this.fromDateBindVar = fromDateBindVar;
    }

    public RichInputDate getFromDateBindVar() {
        return fromDateBindVar;
    }

    public void setToDateBindVar(RichInputDate toDateBindVar) {
        this.toDateBindVar = toDateBindVar;
    }

    public RichInputDate getToDateBindVar() {
        return toDateBindVar;
    }

    public void setFromSpAmtBindVar(RichInputText fromSpAmtBindVar) {
        this.fromSpAmtBindVar = fromSpAmtBindVar;
    }

    public RichInputText getFromSpAmtBindVar() {
        return fromSpAmtBindVar;
    }

    public void setToSpAmtBindVar(RichInputText toSpAmtBindVar) {
        this.toSpAmtBindVar = toSpAmtBindVar;
    }

    public RichInputText getToSpAmtBindVar() {
        return toSpAmtBindVar;
    }
}
