package slssalesinvoicetrend.view.bean;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class SlsSalesInvoiceTrendBean {
    private RichSelectOneChoice fyBind;
    private RichSelectOneChoice rangeSelectionBind;
    private String docTxnIdForView;

    public void setDocTxnIdForView(String docTxnIdForView) {
        this.docTxnIdForView = docTxnIdForView;
    }

    public String getDocTxnIdForView() {
        return docTxnIdForView;
    }

    public SlsSalesInvoiceTrendBean() {
    }

    /**
     *  method to get Bindings
     */
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    /**
     * Fy value change listener
     * @param valueChangeEvent
     */
    public void fyVCL(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue() != null){
            OperationBinding binding = this.getBindings().getOperationBinding("setFy");
            binding.getParamsMap().put("fy", valueChangeEvent.getNewValue());
            binding.execute();
        }
        
    }
    /**
     * Action event to serach on the basis of financial year
     * @param actionEvent
     */
    public void searchFYACTION(ActionEvent actionEvent) {
        if(this.fyBind.getValue() != null){
            OperationBinding binding = this.getBindings().getOperationBinding("setFy");
            binding.getParamsMap().put("fy", this.fyBind.getValue());
            binding.execute();
        }
    }

    public void setFyBind(RichSelectOneChoice fyBind) {
        this.fyBind = fyBind;
    }

    public RichSelectOneChoice getFyBind() {
        return fyBind;
    }

    public void setRangeSelectionBind(RichSelectOneChoice rangeSelectionBind) {
        this.rangeSelectionBind = rangeSelectionBind;
    }

    public RichSelectOneChoice getRangeSelectionBind() {
        return rangeSelectionBind;
    }

    /**
     * Value change listener to set range in coaGraph
     * @param vce
     */
    public void rangeSelectionBindVCL(ValueChangeEvent vce) {
        if(vce.getNewValue() != null){
            OperationBinding binding = this.getBindings().getOperationBinding("setValuesInGraphVo");
            binding.getParamsMap().put("num", Integer.parseInt(vce.getNewValue().toString()));
            binding.execute();
        }
    }
/**
     *Actioon performs navigation from monthwise to coa wise
     * @return
     */
    public String viewCoaFromMonthWiseAmountACTION() {
        OperationBinding binding = this.getBindings().getOperationBinding("setValuesInGraphVo");
        binding.getParamsMap().put("num", 5);
        binding.execute();
        return "view";
    }
    /**
     * Method to get and set the selecetd DocId for Viewing Invoice.
     * @param actionEvent
     */
    public void invDocIdACTION(ActionEvent actionEvent) {
        Object object = actionEvent.getComponent().getAttributes().get("docId");
        if(object != null){
            docTxnIdForView = object.toString();
        }
    }
}
