package mnfoperationApp.view.bean;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import mnfoperationApp.view.utils.ADFUtils;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Date;

import org.apache.myfaces.trinidad.context.RequestContext;

public class OpsearchBean {
    private RichOutputText opTypeId;
    private RichSelectOneRadio opActv;
    private String Active;
    private RichInputDate opfromDT;
    private RichInputDate opToDT;
    private RichOutputText transOpDocId;
    private RichOutputText modeBindVar;
    private RichSelectBooleanCheckbox inHouseBindVar;
    private RichSelectBooleanCheckbox subContractBindVar;

    public OpsearchBean() {
    }

    /*---------------------Getter and Setter of Variables------------------------*/

    public void setOpActv(RichSelectOneRadio opActv) {
        this.opActv = opActv;
    }

    public RichSelectOneRadio getOpActv() {
        return opActv;
    }

    public void setActive(String Active) {
        this.Active = Active;
    }

    public String getActive() {
        return Active;
    }

    public void setOpfromDT(RichInputDate opfromDT) {
        this.opfromDT = opfromDT;
    }

    public RichInputDate getOpfromDT() {
        return opfromDT;
    }

    public void setOpToDT(RichInputDate opToDT) {
        this.opToDT = opToDT;
    }

    public RichInputDate getOpToDT() {
        return opToDT;
    }

    public void setOpTypeId(RichOutputText opTypeId) {
        this.opTypeId = opTypeId;
    }

    public RichOutputText getOpTypeId() {
        return opTypeId;
    }

    public void setTransOpDocId(RichOutputText transOpDocId) {
        this.transOpDocId = transOpDocId;
    }

    public RichOutputText getTransOpDocId() {
        return transOpDocId;
    }

    public void setModeBindVar(RichOutputText modeBindVar) {
        this.modeBindVar = modeBindVar;
    }

    public RichOutputText getModeBindVar() {
        return modeBindVar;
    }

    /*------------------------GetBinding Method----------------------------*/
    OperationBinding ob = null;

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    /*------------------------Create Operation Method----------------------------*/
    public void onCREATE(ActionEvent actionEvent) {
        // Add event code here...
        ADFUtils.findOperation("CreateInsertInOrg").execute();
        String TxnId = DocTxnIdFunc();
        String opSno = OperationSNO();
        Date currntDt = CurrentDtFunc();
        System.out.println("Value of TxnId : " + TxnId);
        System.out.println("Opeartion Sno : " + opSno);
        System.out.println("Currnt date : " + currntDt);
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("GLBL_CURRENT_DT", currntDt);
        pageFlowScope.put("GLBL_GET_TXN_ID", TxnId);
        pageFlowScope.put("GLBL_OPRTN_ID", opSno);
    }

    /*----------------------Function to get DocTxnId---------------------------*/
    public String DocTxnIdFunc() {
        ob = ADFUtils.findOperation("getDocTxnId");
        ob.execute();
        String val = (String) ob.getResult();
        return val;
    }

    /*-----------------------Function to get operation card S. No---------------------*/
    public String OperationSNO() {
        ob = ADFUtils.findOperation("getOperationSNO");
        ob.execute();
        String val1 = (String) ob.getResult();
        return val1;
    }

    /*-----------------------Current Date--------------------------------*/
    public Date CurrentDtFunc() {
        Date convertedDate = new Date(Date.getCurrentDate());
        return convertedDate;
    }


    /*-------------------------------Search Operation---------------------------*/
    public void onSearchACTION(ActionEvent actionEvent) {
        System.out.println("----------------" + this.inHouseBindVar.getValue().toString() + "-----------------" +
                           this.subContractBindVar.getValue().toString());
        if (opActv.getValue().toString().equals("Y")) {
            this.setActive("Y");
        }
        if (opActv.getValue().toString().equals("N")) {
            this.setActive("N");
        }
        if (opActv.getValue().toString().equals("Both")) {
            this.setActive(null);
        }
        ob = ADFUtils.findOperation("onSearchOP");
        ob.getParamsMap().put("opTypeID",
                              (opTypeId.getValue() == null) ? null :
                              Integer.parseInt(this.opTypeId.getValue().toString()));
        ob.getParamsMap().put("opACTV", (this.getActive() == null) ? null : (this.getActive().toString()));
        ob.getParamsMap().put("opID",
                              (transOpDocId.getValue() == null) ? null : (this.transOpDocId.getValue().toString()));
        ob.getParamsMap().put("opFromDT", (opfromDT.getValue() == null) ? null : (this.opfromDT.getValue()));
        ob.getParamsMap().put("opToDT", (opToDT.getValue() == null) ? null : (this.opToDT.getValue()));
        ob.getParamsMap().put("mode",
                              (modeBindVar.getValue() == null) ? null :
                              Integer.parseInt(this.modeBindVar.getValue().toString()));
        ob.getParamsMap().put("TransOpInhouse",
                              (this.inHouseBindVar.getValue().toString().equals("true")) ? "Y" : null);
        ob.getParamsMap().put("TransSubContract",
                              (this.subContractBindVar.getValue().toString().equals("true")) ? "Y" : null);
        ob.execute();
    }

    /*-------------------------Reset Search Form-----------------------------------*/
    public void onResetACTION(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("OnResetOp");
        ob.execute();
        opActv.setValue("Both");
        opfromDT.resetValue();
        opToDT.resetValue();
        opTypeId.setValue(null);
        modeBindVar.setValue(null);
    }

    /*--------------------Method for filter table in View Mode by AF:Attribute--------------------*/
    public void onViewACTION(ActionEvent actionEvent) {
        UIComponent viewbtn = actionEvent.getComponent();
        oracle.adf.view.rich.util.ResetUtils.reset(viewbtn);
        Object object = viewbtn.getAttributes().get("view");
        String txn_id = (String) object.toString();
        Date currntDt1 = CurrentDtFunc();
        System.out.println("value of attribute :" + object.toString());
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("GLBL_GET_TXN_ID", txn_id);
        pageFlowScope.put("GLBL_CURRENT_DT", currntDt1);
    }

    public void setInHouseBindVar(RichSelectBooleanCheckbox inHouseBindVar) {
        this.inHouseBindVar = inHouseBindVar;
    }

    public RichSelectBooleanCheckbox getInHouseBindVar() {
        return inHouseBindVar;
    }

    public void setSubContractBindVar(RichSelectBooleanCheckbox subContractBindVar) {
        this.subContractBindVar = subContractBindVar;
    }

    public RichSelectBooleanCheckbox getSubContractBindVar() {
        return subContractBindVar;
    }
}
