package mmpurchaserequisition.view.bean;

import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;


import oracle.jbo.domain.Date;


public class SearchPurReqBean {
    
    private String PrTxnId=null;
    private RichInputText prNo;
    private RichSelectOneChoice prType;
    private RichSelectOneChoice srcDoc;
    private RichInputDate frmDate;
    private RichInputDate toDate;
    private Integer count=-1;
    private Date date = (Date)Date.getCurrentDate();
    private RichSelectOneChoice reqIdPgBind;
    private RichSelectOneChoice prStatPgBind;

    public SearchPurReqBean() {
       
 
      }

    public String searchAction() {
        // Add event code here...
        
        OperationBinding op=  getBindings().getOperationBinding("search");
         op.execute();
        return null;
    }
    
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void callPurchaseRequisition(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding op=  getBindings().getOperationBinding("getCurrentPrTnxId");
         op.execute();
         String str=(String)op.getResult();
         if(str!=null)
         {setPrTxnId(str);}
       
    }


    public void setPrTxnId(String PrTxnId) {
        this.PrTxnId = PrTxnId;
    }

    public String getPrTxnId() {
        return PrTxnId;
    }

    public void resetAction(ActionEvent actionEvent) {
        // Add event code here...
        prNo.setValue("");
        prType.setValue("");
        srcDoc.setValue("");
        frmDate.setValue("");
        toDate.setValue("");
        reqIdPgBind.setValue("");
        prStatPgBind.setValue("");
        OperationBinding op=  getBindings().getOperationBinding("resetAction");
         op.execute();
        
    }

    public void setPrNo(RichInputText prNo) {
        this.prNo = prNo;
    }

    public RichInputText getPrNo() {
        return prNo;
    }

    public void setPrType(RichSelectOneChoice prType) {
        this.prType = prType;
    }

    public RichSelectOneChoice getPrType() {
        return prType;
    }

    public void setSrcDoc(RichSelectOneChoice srcDoc) {
        this.srcDoc = srcDoc;
    }

    public RichSelectOneChoice getSrcDoc() {
        return srcDoc;
    }

    public void setFrmDate(RichInputDate frmDate) {
        this.frmDate = frmDate;
    }

    public RichInputDate getFrmDate() {
        return frmDate;
    }

    public void setToDate(RichInputDate toDate) {
        this.toDate = toDate;
    }

    public RichInputDate getToDate() {
        return toDate;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        count=(Integer)getBindings().getOperationBinding("on_load_page").execute();
        return count;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDisablePrSrc(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        
        Object obj = valueChangeEvent.getNewValue();
        
        if(obj != null)
        {
            
          String selectedValue = String.valueOf(obj);
          if(!selectedValue.equals("287"))
          {srcDoc.setDisabled(true);}
          
          if(selectedValue.equals("287"))
          {srcDoc.setDisabled(false);}
       }
        
    }

    public void setReqIdPgBind(RichSelectOneChoice reqIdPgBind) {
        this.reqIdPgBind = reqIdPgBind;
    }

    public RichSelectOneChoice getReqIdPgBind() {
        return reqIdPgBind;
    }

    public void setPrStatPgBind(RichSelectOneChoice prStatPgBind) {
        this.prStatPgBind = prStatPgBind;
    }

    public RichSelectOneChoice getPrStatPgBind() {
        return prStatPgBind;
    }
}
