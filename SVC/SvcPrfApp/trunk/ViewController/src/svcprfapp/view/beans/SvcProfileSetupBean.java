package svcprfapp.view.beans;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichToolbar;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class SvcProfileSetupBean {
    private RichSelectBooleanCheckbox freezeBind;
    private RichPopup freezePopup;
    private String mode="V";
    private RichSelectBooleanCheckbox custBind;
    private RichSelectBooleanCheckbox locBind;
    private RichSelectBooleanCheckbox defectBind;
    private RichPopup deletePopUp;
    private RichToolbar toolbarBind;
    private RichPanelGroupLayout panelGroupbind;

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public SvcProfileSetupBean() {
    }
    
    
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    public String addPrfAction() {
        // Add event code here...
        
    
        OperationBinding op1 = getBindings().getOperationBinding("getHoName");
                op1.execute();
                String HoName=op1.getResult().toString();
                System.out.println("value returned  from HoName::"+op1.getResult());
                 OperationBinding op = getBindings().getOperationBinding("checkHoProfile");
                op.execute();
              Integer result=(Integer)op.getResult();
                System.out.println("result from the function is===="+result);
                if(result==-2) {
                    //String message = "Profile for";
                    String msg = resolvElDCMsg("#{bundle['MSG.2017']}").toString();
                    //String message = " is not Freezed. So first freeze profile for [";
                    String msg1 = resolvElDCMsg("#{bundle['MSG.2421']}").toString();
                    
                    //String message = "]";
                    String msg2 = resolvElDCMsg("#{bundle['MSG.2022']}").toString();
                    
                    FacesMessage message = new FacesMessage(msg+""+HoName+""+msg1+HoName+msg2);   
                          message.setSeverity(FacesMessage.SEVERITY_ERROR);   
                          FacesContext fc = FacesContext.getCurrentInstance();   
                          fc.addMessage(null, message); 

                }
                if(result==-1) {
                    //String message = "Please First Create The Profile For [";
                    String msg = resolvElDCMsg("#{bundle['MSG.2025']}").toString();
                    //String message = "]";
                    String msg2 = resolvElDCMsg("#{bundle['MSG.2022']}").toString();
                    FacesMessage message = new FacesMessage(msg+HoName+msg2);   
                          message.setSeverity(FacesMessage.SEVERITY_ERROR);   
                          FacesContext fc = FacesContext.getCurrentInstance();   
                          fc.addMessage(null, message); 

                }
                this.setMode("A");
        return null;
    }

    public String savePrfAction() {
        // Add event code here...
        
        String result=TatValidate();
        if(result.equalsIgnoreCase("N"))
        {
            //String message = "Select One of the Category  in Turn Around Time";
            String msg = resolvElDCMsg("#{bundle['MSG.2422']}").toString();
            String msg2 = msg;
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null,message2);  
            
        }
        if(result.equalsIgnoreCase("Y")){
        // if(getFreezeBind().getValue()!=null && getFreezeBind().getValue()!=""){
         System.out.println("value of get freeze bind isss ==="+getFreezeBind().getValue());
             if(getFreezeBind().getValue().toString().equalsIgnoreCase("true")){
                 showPopup(freezePopup,true);
                
             }
             else{
                 BindingContainer bindings = getBindings();
                 OperationBinding operationBinding =bindings.getOperationBinding("Commit");
                 operationBinding.execute();
                 /*  OperationBinding operationBinding1 =bindings.getOperationBinding("Execute");
                 operationBinding1.execute(); */

                // BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
                // BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
                 //flag = "true";
                // flag1 = "false";
                //freezsmode="P";
                 //this.mode="D";
              //   this.mode1="D";
              //String message = "Record Saved Successfully.";
                String msg = resolvElDCMsg("#{bundle['MSG.91']}").toString();
                 String msg2 = msg;
                 //String msg2 = resolvElDCMsg("#{bundle['MSG.91']}").toString();
                 FacesMessage message2 = new FacesMessage(msg2);
                 message2.setSeverity(FacesMessage.SEVERITY_INFO);
                 FacesContext fc = FacesContext.getCurrentInstance();
                 fc.addMessage(null,message2); 
                 this.setMode("V");
             }
    }
         //}
         //showPopup(freezePopup,true);
         //AdfFacesContext.getCurrentInstance().addPartialTarget(toolbarBind);
        return null;
    }
    
    
    
    private void showPopup(RichPopup pop, boolean visible) {  
        try {  
          FacesContext context = FacesContext.getCurrentInstance();  
          if (context != null && pop != null) {  
            String popupId = pop.getClientId(context);  
            if (popupId != null) {  
              StringBuilder script = new StringBuilder();  
              script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");  
              if (visible) {  
                script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");  
              } else {  
                script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");  
              }  
              ExtendedRenderKitService erks =  
                Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);  
              erks.addScript(context, script.toString());  
            }  
          }  
        } catch (Exception e) {  
          throw new RuntimeException(e);  
        }  
      }
    
    
    public Object resolvElDCMsg(String data) {
           FacesContext fc = FacesContext.getCurrentInstance();
           Application app = fc.getApplication();
           ExpressionFactory elFactory = app.getExpressionFactory();
           ELContext elContext = fc.getELContext();
           ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
           return valueExp.getValue(elContext);
       }

    public void setFreezeBind(RichSelectBooleanCheckbox freezeBind) {
        this.freezeBind = freezeBind;
    }

    public RichSelectBooleanCheckbox getFreezeBind() {
        return freezeBind;
    }

    public void setFreezePopup(RichPopup freezePopup) {
        this.freezePopup = freezePopup;
    }

    public RichPopup getFreezePopup() {
        return freezePopup;
    }

    public void FreezePopup(DialogEvent dialogEvent) {
        // Add event code here...
        

        if(dialogEvent.getOutcome().name().equalsIgnoreCase("yes")){
           // BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("delAction").execute();
             BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
            OperationBinding operationBinding1 =BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute");
            operationBinding1.execute();
            this.setMode("V");
            //flag = "true";
            //freezsmode="P";
            AdfFacesContext.getCurrentInstance().addPartialTarget(toolbarBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelGroupbind);
        }else{
            
        }
    }

    public String CancelPrfAction() {
        // Add event code here...
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
        this.setMode("V");
        return null;
    }

    public void editPrfAction(ActionEvent actionEvent) {
        // Add event code here...
        this.setMode("A");
    }

    public void setCustBind(RichSelectBooleanCheckbox custBind) {
        this.custBind = custBind;
    }

    public RichSelectBooleanCheckbox getCustBind() {
        return custBind;
    }

    public void setLocBind(RichSelectBooleanCheckbox locBind) {
        this.locBind = locBind;
    }

    public RichSelectBooleanCheckbox getLocBind() {
        return locBind;
    }

    public void setDefectBind(RichSelectBooleanCheckbox defectBind) {
        this.defectBind = defectBind;
    }

    public RichSelectBooleanCheckbox getDefectBind() {
        return defectBind;
    }
    
    
 public String TatValidate() {
     String var="Y";
     if(getCustBind().getValue().toString().equalsIgnoreCase("false")) { 
             if(getLocBind().getValue().toString().equalsIgnoreCase("false")) {
                 if(getDefectBind().getValue().toString().equalsIgnoreCase("false")){
                     var="N";
                 }
             }
         }
        return var; 
     }

    public String deletePrfAction() {
        // Add event code here...

        showPopup(deletePopUp, true);
        return null;
    }

    public void setDeletePopUp(RichPopup deletePopUp) {
        this.deletePopUp = deletePopUp;
    }

    public RichPopup getDeletePopUp() {
        return deletePopUp;
    }

    public void deleteDialogListener(DialogEvent dialogEvent) {
        // Add event code here...
        

        if(dialogEvent.getOutcome().name().equalsIgnoreCase("yes")){
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("delAction").execute();
             BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
            
//        flag = "true";
//            freezsmode="P";
            //AdfFacesContext.getCurrentInstance().addPartialTarget(toolbarBind);
           // AdfFacesContext.getCurrentInstance().addPartialTarget(panelgroupbind);
        }else{
            
        }
    }

    public void setToolbarBind(RichToolbar toolbarBind) {
        this.toolbarBind = toolbarBind;
    }

    public RichToolbar getToolbarBind() {
        return toolbarBind;
    }

    public void setPanelGroupbind(RichPanelGroupLayout panelGroupbind) {
        this.panelGroupbind = panelGroupbind;
    }

    public RichPanelGroupLayout getPanelGroupbind() {
        return panelGroupbind;
    }
    
}
