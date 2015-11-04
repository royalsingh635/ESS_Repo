package scinvoiceapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.context.RequestContext;

public class AddSCInvoice {
    private RichInputListOfValues docNoBind;
    private RichInputListOfValues entityBind;
    private RichInputListOfValues currency;
    private RichPopup bindTaxAll;
    private RichPopup bindTdsAll;
    private RichSelectOneChoice project;

    public AddSCInvoice() {
        super();
        Map paramMap = RequestContext.getCurrentInstance().getPageFlowScope();
        this.mode=(String)paramMap.get("GLBL_MODE");
    }
    
    private RichPopup bindOcPop;
    private RichInputListOfValues docBind;
    private RichPopup bindTaxPop;
    private RichPopup bindTdsPop;
    private String mode="C";
    private String wfId="W000";

    public void setWfId(String wfId) {
        this.wfId = wfId;
    }

    public String getWfId() {
        return wfId;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setBindOcPop(RichPopup bindOcPop) {
        this.bindOcPop = bindOcPop;
    }

    public RichPopup getBindOcPop() {
        return bindOcPop;
    }

    public void addDocument(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("createDocument").execute();
        this.setMode("C");
    }

    public void editDocument(ActionEvent actionEvent) {
        this.setMode("C");

    }
    
    public void showErrorMessage(String clienId,String header,String message){
        FacesContext.getCurrentInstance().addMessage(clienId, new FacesMessage(FacesMessage.SEVERITY_ERROR,header==null?"Error":header,message));
    }

    public void saveDocument(ActionEvent actionEvent) {
        
        // validate Record
        
      String val= this.validateHeader();
       
       if(val.equals("N"))
            return;
        
        // WorkFLow related work
        OperationBinding op=ADFBeanUtils.findOperation("getWfId");
        op.execute();
        Object wf=op.getResult();
        if(wf==null){
            ADFModelUtils.showFormattedFacesMessage("Set Up Error", "Work FLow is not defined for this document", FacesMessage.SEVERITY_ERROR);
           return ; 
        }
        this.setWfId((String)wf);
        
        op=ADFBeanUtils.findOperation("getCurUser");
        op.execute();
        
        Integer result=(Integer)op.getResult();
        
        if(result== -1){
            ADFBeanUtils.findOperation("insertToWF").execute(); 
        }
        
        
        OperationBinding docDisp=ADFBeanUtils.findOperation("updateDocDisp");
        docDisp.execute(); 
        
        
        //sendDataFromTempCcToInvcCc
           ADFBeanUtils.findOperation("sendDataFromTempCcToInvcCc").execute(); 
        
        // Commit the data
        
        ADFBeanUtils.findOperation("Commit").execute();
        
        this.setMode("V");
    }

    public void cancel(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("Rollback").execute();
        this.setMode("V");
    }

    public String saveAndForward() {
        // validate Form
    
            String val=this.validateSF();
            
            if(val.equals("N"))
                return null;
        
        // Work FLow related task
        OperationBinding op=ADFBeanUtils.findOperation("getWfId");
        op.execute();
        Object wf=op.getResult();
        if(wf==null){
            ADFModelUtils.showFormattedFacesMessage("Set Up Error", "Work FLow is not defined for this document", FacesMessage.SEVERITY_ERROR);
           return null; 
        }
        this.setWfId((String)wf);
        
        op=ADFBeanUtils.findOperation("getCurUser");
        op.execute();
        
        Integer result=(Integer)op.getResult();
        
        if(result== -1){
            ADFBeanUtils.findOperation("insertToWF").execute(); 
        }
        
        OperationBinding docDisp=ADFBeanUtils.findOperation("updateDocDisp");
        docDisp.execute(); 
        
        // Isert To CC table
        ADFBeanUtils.findOperation("sendDataFromTempCcToInvcCc").execute(); 
        
        // Save Data 
        
        ADFBeanUtils.findOperation("Commit").execute();
        this.setMode("V");
        
        return "WF";
    }

    public void showOtherCharges(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("filterOC").execute();
        ADFBeanUtils.showPopup(this.getBindOcPop(), true);
    }

    public void addDocDetail(ActionEvent actionEvent) {
        String result=this.validateAdd();
        if(result.equals("Y")){ 
          ADFBeanUtils.findOperation("addDocDetail").execute();
        } 
    }

    public void setDocBind(RichInputListOfValues docBind) {
        this.docBind = docBind;
    }

    public RichInputListOfValues getDocBind() {
        return docBind;
    }

    public void listenCurrChange(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        ADFBeanUtils.findOperation("refreshOC").execute();
    }

    public void listenAmtChange(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        ADFBeanUtils.findOperation("refreshOC").execute();
    }

    public void setBindTaxPop(RichPopup bindTaxPop) {
        this.bindTaxPop = bindTaxPop;
    }

    public RichPopup getBindTaxPop() {
        return bindTaxPop;
    }

    public void taxRuleVCL(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null){
            OperationBinding op= ADFBeanUtils.findOperation("applyTax");
            op.getParamsMap().put("taxRuleId", valueChangeEvent.getNewValue());
            op.execute();
        }
    }

    public void applyTaxListener(ActionEvent actionEvent) {
        OperationBinding op=ADFBeanUtils.findOperation("applyDefaultTax");
        op.getParamsMap().put("flag", "I");
        op.execute();
        ADFBeanUtils.showPopup(this.getBindTaxPop(), true);
    }

    public void applyTDS(ActionEvent actionEvent) {
        ADFBeanUtils.showPopup(this.getBindTdsPop(), true);
    }

    public void tdsRuleChange(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null){
            OperationBinding op= ADFBeanUtils.findOperation("applyTDS");
            op.getParamsMap().put("tdsRuleId", valueChangeEvent.getNewValue());
            op.execute();
        }
        
    }

    public void setBindTdsPop(RichPopup bindTdsPop) {
        this.bindTdsPop = bindTdsPop;
    }

    public RichPopup getBindTdsPop() {
        return bindTdsPop;
    }
    
    
    public String validateHeader(){
        String result="Y";
        
        Object entityName=this.getEntityBind().getValue();
        
        if(entityName==null || entityName.toString().trim().length()==0){
            this.showErrorMessage(this.getEntityBind().getClientId(), null, "Please make a selection to Save");
            return "N";
        }
        
        if(this.getCurrency().getValue()==null || this.getCurrency().getValue().toString().trim().length()==0){
            this.showErrorMessage(this.getCurrency().getClientId(), null, "Please make a selection to Save");
            return "N";
        }
        
        
        if(this.getProject()!=null&&this.getProject().isVisible()){
            if(this.getProject().getValue()==null || this.getProject().getValue().toString().trim().length()==0){
                this.showErrorMessage(this.getProject().getClientId(), null, "Please make a selection to Save");
                return "N";
            }
        }
        
        
        return result;
    }
    
    public String validateAdd(){
       
        String result=this.validateHeader();
        
        if(result.equals("N"))
            return "N";
        
        Object docNo=this.getDocNoBind().getValue();
        
        if(docNo==null||docNo.toString().trim().length()==0){
            
            this.showErrorMessage(this.getDocNoBind().getClientId(), null, "Please make a selection to add");
            
            return "N";
            
        }
        
        return result;
    }
    
    
    public String validateSF(){
        String result="Y";
        
        String ret=this.validateHeader();
        
        if(ret.equalsIgnoreCase("N"))
            return "N";
        
        
        OperationBinding op=ADFBeanUtils.findOperation("validatePayment");  
        op.execute();
        
        if(op.getErrors().size()>0){
            this.showErrorMessage(null, "Error while validating Payment", op.getErrors().get(0).toString());
            return "N";
        }
        
        ret=op.getResult().toString();
        
        if(ret.equalsIgnoreCase("N"))
            return "N";
        
        return result;
    }
    
    

    public void setDocNoBind(RichInputListOfValues docNoBind) {
        this.docNoBind = docNoBind;
    }

    public RichInputListOfValues getDocNoBind() {
        return docNoBind;
    }


    public void setEntityBind(RichInputListOfValues entityBind) {
        this.entityBind = entityBind;
    }

    public RichInputListOfValues getEntityBind() {
        return entityBind;
    }

    public void applyTaxAll(ValueChangeEvent valueChangeEvent) {
        OperationBinding op=ADFBeanUtils.findOperation("applyTaxAll");
        op.getParamsMap().put("taxRuleId", valueChangeEvent.getNewValue());
        op.execute();
    }

    public void tdsAllChange(ValueChangeEvent valueChangeEvent) {
        OperationBinding op=ADFBeanUtils.findOperation("applyTDSAll");
        op.getParamsMap().put("tdsRuleId", valueChangeEvent.getNewValue());
        op.execute();
    }

    public void changeTypeVCL(ValueChangeEvent valueChangeEvent) {
        
    }

    public void setCurrency(RichInputListOfValues currency) {
        this.currency = currency;
    }

    public RichInputListOfValues getCurrency() {
        return currency;
    }
    
    public void hidePop(ActionEvent action){
        if(this.getBindTaxPop().isVisible()){
            ADFBeanUtils.showPopup(this.getBindTaxPop(), false); 
        }
        if(this.getBindTdsPop().isVisible()){
            ADFBeanUtils.showPopup(this.getBindTdsPop(), false); 
        }
        if(this.getBindTaxAll().isVisible()){
            ADFBeanUtils.showPopup(this.getBindTaxAll(), false); 
        }
        if(this.getBindTdsAll().isVisible()){
            ADFBeanUtils.showPopup(this.getBindTdsAll(), false); 
        }
    }

    public void setBindTaxAll(RichPopup bindTaxAll) {
        this.bindTaxAll = bindTaxAll;
    }

    public RichPopup getBindTaxAll() {
        return bindTaxAll;
    }

    public void setBindTdsAll(RichPopup bindTdsAll) {
        this.bindTdsAll = bindTdsAll;
    }

    public RichPopup getBindTdsAll() {
        return bindTdsAll;
    }

    public void applyTaxAllAL(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding op=ADFBeanUtils.findOperation("applyDefaultTax");
        op.getParamsMap().put("flag", "A");
        op.execute();
        ADFBeanUtils.showPopup(this.getBindTaxAll(), true);
    }
    
    
    public String headCostCenterAction() {
         OperationBinding binding = ADFBeanUtils.findOperation("chkCcApplicableOrNot");
         binding.execute();
         if (binding.getResult() != null && (Boolean) binding.getResult()) {
             return "headCc";
         } else {
             return null;
         }
     }
    
    public String costCenterAction() {
        OperationBinding binding = ADFBeanUtils.findOperation("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
        return "costCenter";
        } else {
        return null;
    }
    }

    public void addTnc(ActionEvent actionEvent) {
       ADFBeanUtils.findOperation("addTnc").execute();
    }
    
    public Boolean getIsCcValid(){
        OperationBinding binding = ADFBeanUtils.findOperation("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
        return true;
        } else {
        return false;
        }
    }

    public void setProject(RichSelectOneChoice project) {
        this.project = project;
    }

    public RichSelectOneChoice getProject() {
        return project;
    }
}
