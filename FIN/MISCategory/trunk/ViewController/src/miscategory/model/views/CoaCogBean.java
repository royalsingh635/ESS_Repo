package miscategory.model.views;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

public class CoaCogBean {
    private RichOutputText coaCogBinding;
    private RichOutputText coaBinding;
    private String coacogVal=null;
    private Integer coaVal=null;
    private RichInputText glExistBinding;

    public CoaCogBean() {
    }

    public void setCoaCogBinding(RichOutputText coaCogBinding) {
        this.coaCogBinding = coaCogBinding;
    }

    public RichOutputText getCoaCogBinding() {
        return coaCogBinding;
    }

    public void setCoaBinding(RichOutputText coaBinding) {
        this.coaBinding = coaBinding;
    }

    public RichOutputText getCoaBinding() {
        return coaBinding;
    }

  
    public String gotoCoaCogTF() {
        coacogVal=coaCogBinding.getValue().toString();
        coaVal=(Integer)coaBinding.getValue();
        return "coacog";
    }

    public void setCoacogVal(String coacogVal) {
        this.coacogVal = coacogVal;
    }

    public String getCoacogVal() {
        return coacogVal;
    }

    public void setCoaVal(Integer coaVal) {
        this.coaVal = coaVal;
    }

    public Integer getCoaVal() {
        return coaVal;
    }

    public void setGlExistBinding(RichInputText glExistBinding) {
        this.glExistBinding = glExistBinding;
    }

    public RichInputText getGlExistBinding() {
        return glExistBinding;
    }
}
