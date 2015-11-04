package userdocmapping.view.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class UserDocMapBean {
    
    private String mode="V";
    private RichSelectOneChoice docTypeBinding;
    private RichInputComboboxListOfValues usrNmBinding;

    public UserDocMapBean() {
    }


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    public void addUsertoDoc(ActionEvent actionEvent) {
      OperationBinding op=getBindings().getOperationBinding("CreateInsert");
      op.execute();
      mode="A";
    }

    public void editDocMapAL(ActionEvent actionEvent) {
       mode="A";
    }

    public void saveDocMapAL(ActionEvent actionEvent) {
        if(docTypeBinding.getValue()!=null)
        {        
        OperationBinding chk=getBindings().getOperationBinding("CheckUsrName");
        chk.execute();
        if(chk.getResult()!=null)
        {  
        if(chk.getResult().toString().equals("Y")){
        OperationBinding op=getBindings().getOperationBinding("Commit");
        op.execute();
        mode="V";
            }
        else
            {
             
                    FacesMessage Message = new FacesMessage("Please Select User."); 
                    Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(usrNmBinding.getClientId(), Message);   
                }
        }else
        {
                FacesMessage Message = new FacesMessage("Something went wrong."); 
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, Message);
            
            }
        }
        else
        {
                FacesMessage Message = new FacesMessage("Please Select Document Type."); 
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(docTypeBinding.getClientId(), Message);  
            }
    }

    public void cancelAL(ActionEvent actionEvent) {
        OperationBinding op=getBindings().getOperationBinding("Rollback");
        op.execute();
        mode="V";
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void searchAL(ActionEvent actionEvent) {
        OperationBinding op=getBindings().getOperationBinding("SearchMethod");
        op.execute();
    }

    public void resetAL(ActionEvent actionEvent) {
        OperationBinding op=getBindings().getOperationBinding("ResetMethod");
        op.execute();
    }

    public void setDocTypeBinding(RichSelectOneChoice docTypeBinding) {
        this.docTypeBinding = docTypeBinding;
    }

    public RichSelectOneChoice getDocTypeBinding() {
        return docTypeBinding;
    }

    public void setUsrNmBinding(RichInputComboboxListOfValues usrNmBinding) {
        this.usrNmBinding = usrNmBinding;
    }

    public RichInputComboboxListOfValues getUsrNmBinding() {
        return usrNmBinding;
    }
}
