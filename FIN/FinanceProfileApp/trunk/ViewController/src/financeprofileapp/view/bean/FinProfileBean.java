package financeprofileapp.view.bean;

import javax.el.ELContext;
import javax.el.ValueExpression;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.input.RichInputNumberSpinbox;

import oracle.binding.OperationBinding;

public class FinProfileBean {
    private RichInputNumberSpinbox maxCogCodeLen_bind;
    private RichInputNumberSpinbox cogLvlLimit_bind;
    private RichInputNumberSpinbox cogLvl0LpadLen_bind;
    private RichInputNumberSpinbox cogLvlLpadLen_bind;
    public Integer count= 1;

    public FinProfileBean() {
    }
    public String editButton="false";
    //public String Button="true";
    
    public void editActionListener(ActionEvent actionEvent) {
        editButton = "true";
    }


    public void setEditButton(String editButton) {
        this.editButton = editButton;
    }

    public String getEditButton() {
        return editButton;
    }

    public void cancelActionListener(ActionEvent actionEvent) {
        editButton = "false";
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
    }
    
    public String resolvEl(String data) {
            ADFContext adfCtx = ADFContext.getCurrent();
                    ELContext elContext = adfCtx.getELContext();
                    ValueExpression valueExp = adfCtx.getExpressionFactory().createValueExpression(elContext, data, Object.class);
                    Object Message = valueExp.getValue(elContext).toString();
                    return Message.toString();
        }

    public void saveActionListener(ActionEvent actionEvent) {
       
        
        count = ((Integer)cogLvlLpadLen_bind.getValue()+(((Integer)cogLvlLimit_bind.getValue()-1)*(Integer)cogLvl0LpadLen_bind.getValue()));
        
        //BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();     
        if( (Integer)maxCogCodeLen_bind.getValue()>=1 && 
            (Integer)maxCogCodeLen_bind.getValue()<=20 && 
            (Integer)maxCogCodeLen_bind.getValue()>=count  ){
            OperationBinding op=BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit");

      
                op.execute();
                
                if(op.getErrors().size()>0){
                     String msg="Chart of Group Already Created based on the policy Defined, Profile Cannot be Modified Now!!";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Profile is in use",msg));
                }else{
                    
                    System.out.println("After Commit");
                    editButton="false";
                }
         } else{
           
           FacesMessage message = new FacesMessage(resolvEl("#{bundle['LBL.2216']}"),count.toString());   
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(maxCogCodeLen_bind.getClientId(), message);
        }
        
    }

    public void setMaxCogCodeLen_bind(RichInputNumberSpinbox maxCogCodeLen_bind) {
        this.maxCogCodeLen_bind = maxCogCodeLen_bind;
    }

    public RichInputNumberSpinbox getMaxCogCodeLen_bind() {
        return maxCogCodeLen_bind;
    }

    public void createActionListener(ActionEvent actionEvent) {
        editButton = "true";
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert").execute();
    }

    public void setCogLvlLimit_bind(RichInputNumberSpinbox cogLvlLimit_bind) {
        this.cogLvlLimit_bind = cogLvlLimit_bind;
    }

    public RichInputNumberSpinbox getCogLvlLimit_bind() {
        return cogLvlLimit_bind;
    }

    public void setCogLvl0LpadLen_bind(RichInputNumberSpinbox cogLvl0LpadLen_bind) {
        this.cogLvl0LpadLen_bind = cogLvl0LpadLen_bind;
    }

    public RichInputNumberSpinbox getCogLvl0LpadLen_bind() {
        return cogLvl0LpadLen_bind;
    }

    public void setCogLvlLpadLen_bind(RichInputNumberSpinbox cogLvlLpadLen_bind) {
        this.cogLvlLpadLen_bind = cogLvlLpadLen_bind;
    }

    public RichInputNumberSpinbox getCogLvlLpadLen_bind() {
        return cogLvlLpadLen_bind;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }
}
