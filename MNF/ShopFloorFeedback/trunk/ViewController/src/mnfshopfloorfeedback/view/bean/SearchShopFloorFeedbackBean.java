package mnfshopfloorfeedback.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.validator.ValidatorException;

import mnfshopfloorfeedback.view.Utils.ADFUtils;

import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.binding.OperationBinding;

import java.util.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;

public class SearchShopFloorFeedbackBean {
    OperationBinding ob = null;
    private RichInputListOfValues feedbackIdBind;
    private RichInputComboboxListOfValues fdbkBasisBind;
    private RichInputListOfValues jcBind;
    private RichInputListOfValues rcBind;
    private RichInputListOfValues createdByBind;
    private RichInputDate fromDateBind;
    private RichInputDate toDateBind;
    private RichSelectOneChoice fdbkBaseBind;
    private RichOutputText transSearchCreatedByIdBind;
    private RichOutputText transSearchDocIdBind;

    public SearchShopFloorFeedbackBean() {
    }

    /**
     * Method to reset the Search Results
     * **/
    public void ResetBtnActionListener(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("setResetFdbkSearchParams");
        ob.execute();
    }

    /**
     * Method to get Search Results
     * **/
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void SearchBtnActionListener(ActionEvent actionEvent) {
        actionEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        ob = ADFUtils.findOperation("setFdbkSearchParams");
        ob.getParamsMap().put("createdto",
                              this.toDateBind.getValue() == null ? null :
                              (Timestamp)this.toDateBind.getValue());
        ob.getParamsMap().put("id",
                              this.transSearchDocIdBind.getValue() == null ? null :
                              this.transSearchDocIdBind.getValue().toString());
        ob.getParamsMap().put("createdBy",
                              this.transSearchCreatedByIdBind.getValue() == null ? null :
                              Integer.parseInt(this.transSearchCreatedByIdBind.getValue().toString()));
        ob.getParamsMap().put("basis",
                              this.fdbkBaseBind.getValue() == null ? null :
                              Integer.parseInt(this.fdbkBaseBind.getValue().toString()));
        ob.getParamsMap().put("jcid",this.jcBind.getValue() == null ? null :
                              this.jcBind.getValue().toString());
        ob.getParamsMap().put("rcid",
                              this.rcBind.getValue() == null ? null :
                              this.rcBind.getValue().toString());
        ob.getParamsMap().put("createdOn", this.fromDateBind.getValue() == null ? null : (Timestamp)this.fromDateBind.getValue());
        ob.execute();
    }

    public void setFeedbackIdBind(RichInputListOfValues feedbackIdBind) {
        this.feedbackIdBind = feedbackIdBind;
    }

    public RichInputListOfValues getFeedbackIdBind() {
        return feedbackIdBind;
    }

    public void setFdbkBasisBind(RichInputComboboxListOfValues fdbkBasisBind) {
        this.fdbkBasisBind = fdbkBasisBind;
    }

    public RichInputComboboxListOfValues getFdbkBasisBind() {
        return fdbkBasisBind;
    }

    public void setJcBind(RichInputListOfValues jcBind) {
        this.jcBind = jcBind;
    }

    public RichInputListOfValues getJcBind() {
        return jcBind;
    }

    public void setRcBind(RichInputListOfValues rcBind) {
        this.rcBind = rcBind;
    }

    public RichInputListOfValues getRcBind() {
        return rcBind;
    }

    public void setCreatedByBind(RichInputListOfValues createdByBind) {
        this.createdByBind = createdByBind;
    }

    public RichInputListOfValues getCreatedByBind() {
        return createdByBind;
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

    public void setFdbkBaseBind(RichSelectOneChoice fdbkBaseBind) {
        this.fdbkBaseBind = fdbkBaseBind;
    }

    public RichSelectOneChoice getFdbkBaseBind() {
        return fdbkBaseBind;
    }

    @SuppressWarnings("oracle.jdeveloper.java.semantic-warning")
    public void createToValidator(FacesContext facesContext, UIComponent uIComponent,
                                  Object object) throws ParseException {
    }

    public void setTransSearchCreatedByIdBind(RichOutputText transSearchCreatedByIdBind) {
        this.transSearchCreatedByIdBind = transSearchCreatedByIdBind;
    }

    public RichOutputText getTransSearchCreatedByIdBind() {
        return transSearchCreatedByIdBind;
    }

    public void setTransSearchDocIdBind(RichOutputText transSearchDocIdBind) {
        this.transSearchDocIdBind = transSearchDocIdBind;
    }

    public RichOutputText getTransSearchDocIdBind() {
        return transSearchDocIdBind;
    }

    public String createAction() {
        OperationBinding b1 =ADFBeanUtils.findOperation("checkYearFyId");
        b1.execute();
        System.out.println("result::"+b1.getResult());
        if(b1.getResult()!=null){
                Number chkUsr = (Number)b1.getResult();
                System.out.println("value of chkUsr:::"+chkUsr+"   "+b1.getErrors());
                if (!(chkUsr.compareTo(-1)==0)) {
                    return "create";
                }else{
                    StringBuilder message = new StringBuilder();
                    message.append("Financial Year is not Defined !");
                    ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(), FacesMessage.SEVERITY_INFO);
                }
        }
                return null;
    }
}
