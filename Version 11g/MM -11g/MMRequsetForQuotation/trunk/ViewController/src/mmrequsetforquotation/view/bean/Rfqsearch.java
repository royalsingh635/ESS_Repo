package mmrequsetforquotation.view.bean;


import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Date;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class Rfqsearch {
    private RichSelectOneChoice orgIdBinding;
    private RichInputText dlvryDayBinding;
    private RichInputDate rfqDtBinding;
    private RichInputText rfqSearchBinding;
    private RichSelectOneChoice rfqStsBinding;
    private RichInputText rfqNoBinding;
    private RichPopup popDeleteBind;
    private RichTable rfq1TableBinding;

    public Rfqsearch() {
    }

    public void setOrgIdBinding(RichSelectOneChoice orgIdBinding) {
        this.orgIdBinding = orgIdBinding;
    }

    public RichSelectOneChoice getOrgIdBinding() {
        return orgIdBinding;
    }

    public void setDlvryDayBinding(RichInputText dlvryDayBinding) {
        this.dlvryDayBinding = dlvryDayBinding;
    }

    public RichInputText getDlvryDayBinding() {
        return dlvryDayBinding;
    }

    public void setRfqDtBinding(RichInputDate rfqDtBinding) {
        this.rfqDtBinding = rfqDtBinding;
    }

    public RichInputDate getRfqDtBinding() {
        return rfqDtBinding;
    }

    public void setRfqSearchBinding(RichInputText rfqSearchBinding) {
        this.rfqSearchBinding = rfqSearchBinding;
    }

    public RichInputText getRfqSearchBinding() {
        return rfqSearchBinding;
    }

    public void setRfqStsBinding(RichSelectOneChoice rfqStsBinding) {
        this.rfqStsBinding = rfqStsBinding;
    }

    public RichSelectOneChoice getRfqStsBinding() {
        return rfqStsBinding;
    }

    public void searchActionListner(ActionEvent actionEvent) {
        // Add event code here...
        String orgId=null;
        Integer delvrydays=null;
        Date rfqdate=null;
        String rfqno=null;
        Integer rfqsts=null;
        if(getOrgIdBinding().getValue()!=null && getOrgIdBinding().getValue()!=""){
           orgId= getOrgIdBinding().getValue().toString(); 
        }
        if(getDlvryDayBinding().getValue()!=null && getDlvryDayBinding().getValue()!="")
        {
          delvrydays= Integer.parseInt(getDlvryDayBinding().getValue().toString());
        }
        if(getRfqDtBinding().getValue()!=null && getRfqDtBinding().getValue()!=null) {
            rfqdate=(Date)getRfqDtBinding().getValue();
        }
        if(getRfqNoBinding().getValue()!=null && getRfqNoBinding().getValue()!="") {
            rfqno=getRfqNoBinding().getValue().toString();
        }
        if(getRfqStsBinding().getValue()!=null && getRfqStsBinding().getValue()!="") {
            System.out.println("rfq status id is: "+getRfqStsBinding().getValue());
           rfqsts= (Integer)getRfqStsBinding().getValue();
           System.out.println(rfqsts);
        }
        
        OperationBinding obind=getBindings().getOperationBinding("SearchCriteria");
        obind.getParamsMap().put("orgId",orgId);
        obind.getParamsMap().put("delvrydays",delvrydays);
        obind.getParamsMap().put("rfqdate",rfqdate);
        obind.getParamsMap().put("rfqno", rfqno);
        obind.getParamsMap().put("rfqsts", rfqsts);
        obind.execute(); 
    }

    public void ResetActionListner(ActionEvent actionEvent) {
        // Add event code here...
        getOrgIdBinding().setValue(null);
        getDlvryDayBinding().setValue(null);
        getRfqDtBinding().setValue(null);
        getRfqStsBinding().setValue(null);
        getRfqNoBinding().setValue(null);
        
        OperationBinding obind=getBindings().getOperationBinding("ResetAction");
        obind.execute();
        //searchActionListner(actionEvent);
    }
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setRfqNoBinding(RichInputText rfqNoBinding) {
        this.rfqNoBinding = rfqNoBinding;
    }

    public RichInputText getRfqNoBinding() {
        return rfqNoBinding;
    }

    public void DeleteAction(ActionEvent actionEvent) {
        // Add event code here...
        showPopup(popDeleteBind, true);
    }

    public void PopDeletedialogListner(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().name().equalsIgnoreCase("yes")){
          //  updatePrStatusondelete  
          BindingContainer ob1 = getBindings();
          OperationBinding ob12 = ob1.getOperationBinding("updatePrStatusondelete");
          ob12.execute();
          if(ob12.getErrors().isEmpty()){
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
          }
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Execute");
            operationBinding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(rfq1TableBinding);
          //Execute
        }
    }
    private void showPopup(RichPopup pop, boolean visible)
    {
      try 
      {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null && pop != null) 
        {
            String popupId = pop.getClientId(context);
            if (popupId != null) 
            {
                StringBuilder script = new StringBuilder();
                script.append("var popup =AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                if (visible){
                   script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                }  
                else{
                    script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                }
            ExtendedRenderKitService erks =Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
            erks.addScript(context, script.toString());
        }
      }
    } 
      catch (Exception e) 
      {
           throw new RuntimeException(e);
      }
    }

    public void setPopDeleteBind(RichPopup popDeleteBind) {
        this.popDeleteBind = popDeleteBind;
    }

    public RichPopup getPopDeleteBind() {
        return popDeleteBind;
    }

    public void setRfq1TableBinding(RichTable rfq1TableBinding) {
        this.rfq1TableBinding = rfq1TableBinding;
    }

    public RichTable getRfq1TableBinding() {
        return rfq1TableBinding;
    }
}
