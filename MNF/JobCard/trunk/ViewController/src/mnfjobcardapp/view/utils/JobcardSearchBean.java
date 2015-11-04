package mnfjobcardapp.view.utils;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Date;

import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.context.RequestContext;

public class JobcardSearchBean {
    private RichOutputText jcIdBind;
    private RichOutputText jcStatBind;
    private RichOutputText shiftIdBind;
    private RichInputDate fromDtBind;
    private RichInputDate toDtBind;
    private RichOutputText outputItemBindVar;

    public JobcardSearchBean() {
    }

    /*---------------------Getter and Setters--------------------*/
    public void setJcIdBind(RichOutputText jcIdBind) {
        this.jcIdBind = jcIdBind;
    }

    public RichOutputText getJcIdBind() {
        return jcIdBind;
    }

    public void setJcStatBind(RichOutputText jcStatBind) {
        this.jcStatBind = jcStatBind;
    }

    public RichOutputText getJcStatBind() {
        return jcStatBind;
    }

    public void setShiftIdBind(RichOutputText shiftIdBind) {
        this.shiftIdBind = shiftIdBind;
    }

    public RichOutputText getShiftIdBind() {
        return shiftIdBind;
    }

    public void setFromDtBind(RichInputDate fromDtBind) {
        this.fromDtBind = fromDtBind;
    }

    public RichInputDate getFromDtBind() {
        return fromDtBind;
    }

    public void setToDtBind(RichInputDate toDtBind) {
        this.toDtBind = toDtBind;
    }

    public RichInputDate getToDtBind() {
        return toDtBind;
    }

    public void setOutputItemBindVar(RichOutputText outputItemBindVar) {
        this.outputItemBindVar = outputItemBindVar;
    }

    public RichOutputText getOutputItemBindVar() {
        return outputItemBindVar;
    }
    /*----------------------Method for get Binding------------------*/
    OperationBinding ob = null;

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /*--------------------Search Job Card-------------------------*/
    public void searchFunc(ActionEvent actionEvent) {
        ob = ADFBeanUtils.findOperation("SearchJobCard");
        ob.getParamsMap().put("JcId", (jcIdBind.getValue() == null ? null : jcIdBind.getValue().toString()));
        ob.getParamsMap().put("Stat",
                              (jcStatBind.getValue() == null ? null :
                               Integer.parseInt(jcStatBind.getValue().toString())));
        ob.getParamsMap().put("ShiftId", (shiftIdBind.getValue() == null ? null : shiftIdBind.getValue().toString()));
        ob.getParamsMap().put("fromDate", (fromDtBind.getValue() == null ? null : fromDtBind.getValue()));
        ob.getParamsMap().put("toDate", (toDtBind.getValue() == null ? null : toDtBind.getValue()));
        ob.getParamsMap().put("itemOutput",
                              (outputItemBindVar.getValue() == null ? null : outputItemBindVar.getValue().toString()));
        ob.execute();
    }

    /*------------------Reset Jobcard-----------------------------*/
    public void resetFunc(ActionEvent actionEvent) {
        ob = ADFBeanUtils.findOperation("ResetJobCard");
        ob.execute();
    }


    /*----------------------Create Job Card-------------------------*/
    public void createJobCard(ActionEvent actionEvent) {
        ob = ADFBeanUtils.findOperation("checkYearFyId");
        ob.execute();
        Number chkUsr = (Number) ob.getResult();
        if (!(chkUsr.compareTo(-1) == 0)) {
            Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
            Date currntDt = CurrentDtFunc();
            pageFlowScope.put("GLBL_CURRENT_DATE", currntDt);

            String TxnId = DocTxnIdFunc();
            pageFlowScope.put("GLBL_DOC_TXN_ID", TxnId);

            Integer fyId = FyIdFunc();
            pageFlowScope.put("GLBL_FY_ID", fyId);

            String JcSno = JobcardSNO();
            pageFlowScope.put("GLBL_JC_SR_ID", JcSno);
        } else {
            //            StringBuilder message = new StringBuilder();
            //            message.append("Financial Year is not Defined !");
            //            ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(), FacesMessage.SEVERITY_INFO);
        }
    }

    /*----------------------Function to get DocTxnId---------------------------*/
    public String DocTxnIdFunc() {
        ob = ADFBeanUtils.findOperation("getDocTxnId");
        ob.execute();
        String val = (String) ob.getResult();
        return val;
    }

    /*-----------------------Function to get Job card S. No---------------------*/
    public String JobcardSNO() {
        ob = ADFBeanUtils.findOperation("getJobCardSNO");
        ob.execute();
        String val1 = (String) ob.getResult();
        return val1;
    }

    /*-----------------------Current Date--------------------------------*/
    public Date CurrentDtFunc() {
        Date convertedDate = new Date(Date.getCurrentDate());
        return convertedDate;
    }

    /*---------------------Function to get FY_ID---------------------------*/
    public Integer FyIdFunc() {
        ob = ADFBeanUtils.findOperation("getFyId");
        ob.execute();
        Integer val2 = (Integer) ob.getResult();
        return val2;
    }

    /*--------------------get doc id by af: attribute-------------------*/
    public void onViewACTION(ActionEvent actionEvent) {
        UIComponent viewbtn = actionEvent.getComponent();
        oracle.adf.view.rich.util.ResetUtils.reset(viewbtn);
        Object object = viewbtn.getAttributes().get("getJcDocId");
        String txn_id = (String) object.toString();
        Date currntDt1 = CurrentDtFunc();
        //   System.out.println("value of attribute :" + object.toString());
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("GLBL_DOC_TXN_ID", txn_id);
        pageFlowScope.put("GLBL_CURRENT_DATE", currntDt1);
    }

    public String createJcAction() {
        ob = ADFBeanUtils.findOperation("checkYearFyId");
        ob.execute();
        Number chkUsr = (Number) ob.getResult();
        if (!(chkUsr.compareTo(-1) == 0)) {
            return "CreateJC";
        } else {
            StringBuilder message = new StringBuilder();
            message.append("Financial Year is not Defined !");
            ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(), FacesMessage.SEVERITY_INFO);
        }
        return null;
    }
}
