package defineworkflowapp.view.bean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewObjectImpl;

public class DefineWfBean {
    private RichInputListOfValues wfNameBinding;
    private RichInputListOfValues docNameBinding;
    private RichInputListOfValues docTypeNameBinding;
    private RichInputListOfValues usrNameBinding;
    private String mode="V";
    private RichInputListOfValues docNmBinding;
    private RichInputListOfValues docTypeNmBinding;
    private RichSelectOneChoice usrLevelBinding;
    private RichInputComboboxListOfValues usrLvlNameBinding;
    private RichInputText maxLvlBinding;
    private RichInputText wfNmBinding;
    private RichInputDate frmDtBinding;
    private Date curr=new Date();
    private RichSelectBooleanCheckbox autoskipBinding;
    private RichSelectBooleanCheckbox skipchkBinding;
    private RichInputText authdaysBinding;
    private RichInputText warndaysBinding;

    public DefineWfBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void createWorkflowAL(ActionEvent actionEvent) {
        OperationBinding createOp=getBindings().getOperationBinding("Createwithparameters");
        createOp.execute();
        mode="A";
    }

    public void populateLevelsAL(ActionEvent actionEvent) {
        if(maxLvlBinding.getValue()!=null){
        if(wfNmBinding.getValue()!=null && wfNmBinding.getValue().toString().length()>0){
        if(frmDtBinding.getValue()!=null){
       OperationBinding populateOp=getBindings().getOperationBinding("populateLevels");
       populateOp.execute();
        }
        else
        {
                FacesMessage Message = new FacesMessage("Please Select Valid Date."); 
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(wfNmBinding.getClientId(), Message);
            }
        }
        else
        {
                FacesMessage Message = new FacesMessage("Please Enter WorkFlow Name."); 
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(wfNmBinding.getClientId(), Message);
            }
        }
        else
        {
                FacesMessage Message = new FacesMessage("Please Enter Maximum Levels for WorkFlow."); 
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(maxLvlBinding.getClientId(), Message);
            }
    }

    public void SaveWfAL(ActionEvent actionEvent) {
      /*   OperationBinding updateWfIdOp=getBindings().getOperationBinding("updateWfId");
        updateWfIdOp.execute(); */
       OperationBinding saveOp=getBindings().getOperationBinding("Commit");
       saveOp.execute();
       mode="V";
        FacesMessage Message = new FacesMessage("Record Saved Successfully!"); 
        Message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, Message);
    }

    public void searchWfAL(ActionEvent actionEvent) {
       OperationBinding searchOp=getBindings().getOperationBinding("searchWorkflow");
        searchOp.execute();
    }

    public void resetSearchAL(ActionEvent actionEvent) {
      wfNameBinding.setValue(null);
      docNameBinding.setValue(null);
      docTypeNameBinding.setValue(null);
      usrNameBinding.setValue(null);
        OperationBinding resetOp=getBindings().getOperationBinding("resetBindVar");
         resetOp.execute();
    }

    public void setWfNameBinding(RichInputListOfValues wfNameBinding) {
        this.wfNameBinding = wfNameBinding;
    }

    public RichInputListOfValues getWfNameBinding() {
        return wfNameBinding;
    }

    public void setDocNameBinding(RichInputListOfValues docNameBinding) {
        this.docNameBinding = docNameBinding;
    }

    public RichInputListOfValues getDocNameBinding() {
        return docNameBinding;
    }

    public void setDocTypeNameBinding(RichInputListOfValues docTypeNameBinding) {
        this.docTypeNameBinding = docTypeNameBinding;
    }

    public RichInputListOfValues getDocTypeNameBinding() {
        return docTypeNameBinding;
    }

    public void setUsrNameBinding(RichInputListOfValues usrNameBinding) {
        this.usrNameBinding = usrNameBinding;
    }

    public RichInputListOfValues getUsrNameBinding() {
        return usrNameBinding;
    }

    public void cancelWf(ActionEvent actionEvent) {
       OperationBinding cancelOp=getBindings().getOperationBinding("Rollback");
       cancelOp.execute();
       mode="V";
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void editWfAL(ActionEvent actionEvent) {
      mode="A";
    }

    public void addDocAL(ActionEvent actionEvent) {
        OperationBinding opDoc=getBindings().getOperationBinding("validateDoc");
        opDoc.execute();
        if(opDoc.getResult()!=null && opDoc.getResult().toString().equals("Y"))
        {
        OperationBinding op=getBindings().getOperationBinding("validateDocType");
        op.execute();
        if(op.getResult()!=null && op.getResult().toString().equals("Y"))
        {
       OperationBinding addDocOp=getBindings().getOperationBinding("addDocument");
       addDocOp.execute();
        docNmBinding.setValue(null);
        docTypeNmBinding.setValue(null);
            }
        else if(op.getResult()!=null && op.getResult().toString().equals("D"))
        {
            FacesMessage Message = new FacesMessage("Duplicate Combination for Document and Document Type."); 
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, Message);
        }
        else
        {
                FacesMessage Message = new FacesMessage("Please Select Valid Document Type."); 
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, Message);
            }
        }
                else
                {
                FacesMessage Message = new FacesMessage("Please Select Valid Document."); 
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, Message);
            }
    }

    public void setDocNmBinding(RichInputListOfValues docNmBinding) {
        this.docNmBinding = docNmBinding;
    }

    public RichInputListOfValues getDocNmBinding() {
        return docNmBinding;
    }

    public void setDocTypeNmBinding(RichInputListOfValues docTypeNmBinding) {
        this.docTypeNmBinding = docTypeNmBinding;
    }

    public RichInputListOfValues getDocTypeNmBinding() {
        return docTypeNmBinding;
    }

    public void addUsrAL(ActionEvent actionEvent) {
        OperationBinding opvalidlvl=getBindings().getOperationBinding("validateLevel");
            opvalidlvl.execute();
            if(opvalidlvl.getResult()!=null && opvalidlvl.getResult().toString().equals("Y")){
        OperationBinding opvalid=getBindings().getOperationBinding("validateUsr");
        opvalid.execute();
        if(opvalid.getResult()!=null && opvalid.getResult().toString().equals("Y")){
        OperationBinding addDocOp=getBindings().getOperationBinding("addUsrToDocument");
        addDocOp.execute();
        usrLevelBinding.setValue(null);
        usrLvlNameBinding.setValue(null);
        }
        else if(opvalid.getResult()!=null && opvalid.getResult().toString().equals("D")){
                FacesMessage Message = new FacesMessage("Selected User already has been assigned to selected Document."); 
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, Message); 
      
            
            }
        else
        {
                FacesMessage Message = new FacesMessage("Please Select Valid User."); 
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, Message); 
            }
            }
            else
            {
                    FacesMessage Message = new FacesMessage("Please Select Valid Level."); 
                    Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, Message); 
                }
        
    }

    public void setUsrLevelBinding(RichSelectOneChoice usrLevelBinding) {
        this.usrLevelBinding = usrLevelBinding;
    }

    public RichSelectOneChoice getUsrLevelBinding() {
        return usrLevelBinding;
    }

    public void setUsrLvlNameBinding(RichInputComboboxListOfValues usrLvlNameBinding) {
        this.usrLvlNameBinding = usrLvlNameBinding;
    }

    public RichInputComboboxListOfValues getUsrLvlNameBinding() {
        return usrLvlNameBinding;
    }

    public void setMaxLvlBinding(RichInputText maxLvlBinding) {
        this.maxLvlBinding = maxLvlBinding;
    }

    public RichInputText getMaxLvlBinding() {
        return maxLvlBinding;
    }

    public void setWfNmBinding(RichInputText wfNmBinding) {
        this.wfNmBinding = wfNmBinding;
    }

    public RichInputText getWfNmBinding() {
        return wfNmBinding;
    }

    public void setFrmDtBinding(RichInputDate frmDtBinding) {
        this.frmDtBinding = frmDtBinding;
    }

    public RichInputDate getFrmDtBinding() {
        return frmDtBinding;
    }

    public void setCurr(Date curr) {
        this.curr = curr;
    }

    public Date getCurr() {
        return curr;
    }

    public void validateWarnDays(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object!=null)
        {
            if((new Integer(object.toString())).compareTo(new Integer(0))<=0)
            {
               throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Warning Days must be greater than Zero.",null));

            }
            else
            {
           // authdaysBinding.processUpdates(facesContext.getCurrentInstance());
            OperationBinding opValid=getBindings().getOperationBinding("validateWarnDays");
            opValid.getParamsMap().put("warnDays", object);
            opValid.execute();
            if(opValid.getResult()!=null)
                if(opValid.getResult().equals("Y"))
                {}
            else if(opValid.getResult().equals("N"))
                {
                   throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Warning Days must be less than or equal to Authorised Days.",null));
                    }
            }
            }
    }

    public void authDysValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object!=null)
        {
            if((new Integer(object.toString())).compareTo(new Integer(0))<=0)
            {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Authorise Days must be greater than Zero.",null));

            }
            else
            {
             //   warndaysBinding.processUpdates(facesContext.getCurrentInstance());
            OperationBinding opValid=getBindings().getOperationBinding("validateAuthDays");
            opValid.getParamsMap().put("authDays", object);
            opValid.execute();
            if(opValid.getResult()!=null)
                if(opValid.getResult().equals("Y"))
                {}
            else
                {
                  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Auth days must be greater than warning days",null));
                    }
            }
            }
    }

    public void skipValueChangeListner(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(valueChangeEvent.getNewValue()!=null){
         
          String skipvalue=valueChangeEvent.getNewValue().toString();
          System.out.println("value change listner value is "+skipvalue);
          if(skipvalue.equalsIgnoreCase("false")){
              OperationBinding opValid=getBindings().getOperationBinding("autoSkipSet");
              opValid.execute();
              autoskipBinding.setDisabled(true);
              AdfFacesContext.getCurrentInstance().addPartialTarget(autoskipBinding);
              
          }else if(skipvalue.equalsIgnoreCase("true")){
              autoskipBinding.setDisabled(false);
              AdfFacesContext.getCurrentInstance().addPartialTarget(autoskipBinding);
              
          }
        }
    }

    public void setAutoskipBinding(RichSelectBooleanCheckbox autoskipBinding) {
        this.autoskipBinding = autoskipBinding;
    }

    public RichSelectBooleanCheckbox getAutoskipBinding() {
        return autoskipBinding;
    }

    public void autoskipValueChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
      /*   if (valueChangeEvent.getNewValue() != null) {
            String autoskip = valueChangeEvent.getNewValue().toString();
            if (autoskip.equalsIgnoreCase("true")) {
                System.out.println("skip chk value is "+skipchkBinding.getValue() );
                if (skipchkBinding.getValue() != null && skipchkBinding.getValue() != "") {
                    String skipchk = skipchkBinding.getValue().toString();
                    if (skipchk.equalsIgnoreCase("false")) {
                        OperationBinding opValid = getBindings().getOperationBinding("autoSkipSet");
                        opValid.execute();
                        autoskipBinding.setDisabled(true);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(autoskipBinding);
                    }
                }else{
                    autoskipBinding.setDisabled(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(autoskipBinding);
                }
            } 

        }*/


    }

    public void setSkipchkBinding(RichSelectBooleanCheckbox skipchkBinding) {
        this.skipchkBinding = skipchkBinding;
    }

    public RichSelectBooleanCheckbox getSkipchkBinding() {
        return skipchkBinding;
    }

    public void setAuthdaysBinding(RichInputText authdaysBinding) {
        this.authdaysBinding = authdaysBinding;
    }

    public RichInputText getAuthdaysBinding() {
        return authdaysBinding;
    }

    public void setWarndaysBinding(RichInputText warndaysBinding) {
        this.warndaysBinding = warndaysBinding;
    }

    public RichInputText getWarndaysBinding() {
        return warndaysBinding;
    }

    public void deleteDocAL(ActionEvent actionEvent) {
       OperationBinding op=getBindings().getOperationBinding("Delete");
       op.execute();
    }

    public void wfNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if(object!=null){
            OperationBinding op=getBindings().getOperationBinding("wfNameValid");
            op.getParamsMap().put("Name", object.toString());
            op.execute();
            
            if(op.getResult()!=null){
                String flag=op.getResult().toString();
            System.out.println("now result is "+flag);
                if(flag.equalsIgnoreCase("N")){
                    String errorMsg="Duplicate Workflow Name Found";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,errorMsg,null));
                }
            }
        }
    }
}
