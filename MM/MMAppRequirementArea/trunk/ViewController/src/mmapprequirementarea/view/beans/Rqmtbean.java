package mmapprequirementarea.view.beans;

import adf.utils.model.ADFModelUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class Rqmtbean {
    private RichDialog showpop1;
    private RichPopup pop1;
    String mode="Y";
    private RichTable tableBinding;
    private RichInputText rqmtNmBind;

    public Rqmtbean() {
    }

    String cldid=resolvEl1("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
    String orgid=resolvEl1("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
    Integer slocid=Integer.parseInt(resolvEl1("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
    Integer userid = Integer.parseInt(resolvEl1("#{pageFlowScope.GLBL_APP_USR}").toString());
    String hoOrgid=resolvEl1("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
    
        public String resolvEl1(String data) {
            FacesContext fc = FacesContext.getCurrentInstance();
            Application app = fc.getApplication();
            ExpressionFactory elFactory = app.getExpressionFactory();
            ELContext elContext = fc.getELContext();
            ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
            String Message = valueExp.getValue(elContext).toString();
            return Message;
        }
    public void addAction(ActionEvent actionEvent) {
        // Add event code here...
        mode="N";
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
       
    }

    public void editActionListen(ActionEvent actionEvent) {
      
        String flag="";
        OperationBinding operationBinding =getBindings().getOperationBinding("checkisDeletable");
        operationBinding.execute();
        if(operationBinding.getErrors().isEmpty()){
             
              flag=operationBinding.getResult().toString();
        }
        if(flag.equalsIgnoreCase("Y")){
            mode="N";
            //showPopup(showpop1, true);
           // setMode("Y");
        }
        else{
//            Requirement Area Can't Editable because it is Already in used
            String s=ADFModelUtils.resolvRsrc("MSG.1756");
            FacesMessage message = new FacesMessage(s);   
            message.setSeverity(FacesMessage.SEVERITY_ERROR);   
            FacesContext fc = FacesContext.getCurrentInstance();   
            fc.addMessage(null, message);  
        }
    }
    
 

    public void saveActionListen(ActionEvent actionEvent) {
        if(hoOrgid!=null){
           
        OperationBinding OBinding =getBindings().getOperationBinding("setRqmtId");
        OBinding.getParamsMap().put("ho_orgId",hoOrgid);
        OBinding.execute();
        
        if(rqmtNmBind.getValue()!=null)
        {
         OperationBinding obind = getBindings().getOperationBinding("testRqmtvalidator");
            obind.getParamsMap().put("rqmtName", rqmtNmBind.getValue().toString());
            obind.execute();
            String flag=null;
                  if(obind.getErrors().isEmpty()){
                      flag=obind.getResult().toString();
                      //System.out.println(" if flag"+flag);
                       if(flag.equalsIgnoreCase("Y")){
                        // System.out.println(" else flag"+flag);
//                        Duplicate Requirement Name Exists
                                       String s=ADFModelUtils.resolvRsrc("MSG.1750");
//            FacesMessage message = new FacesMessage(s);  
//                          String msg=resolvElDCMsg("#{bundle['']}").toString();
                          throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, s, null));
                      }
                  }
        }
         if(OBinding.getErrors().isEmpty()){
           OperationBinding operationBinding =getBindings().getOperationBinding("Commit");
           operationBinding.execute();
              if(operationBinding.getErrors().isEmpty()){
                  mode="Y";
//                  Record Save successfully
                  String s=ADFModelUtils.resolvRsrc("MSG.1599");
                  FacesMessage message = new FacesMessage(s); 
//                  FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1599']}").toString());   
                  message.setSeverity(FacesMessage.SEVERITY_INFO);   
                  FacesContext fc = FacesContext.getCurrentInstance();   
                  fc.addMessage(null, message); 
              }
              else{
//                  Record Not Save
                  String s=ADFModelUtils.resolvRsrc("MSG.1752");
                  FacesMessage message = new FacesMessage(s);
//                  FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1752']}").toString());   
                  message.setSeverity(FacesMessage.SEVERITY_ERROR);   
                  FacesContext fc = FacesContext.getCurrentInstance();   
                  fc.addMessage(null, message); 
              }
             
          }
        }
    }

    public void deleteActionListen(ActionEvent actionEvent) {
        
        
        
        String flag="";
        OperationBinding operationBinding =getBindings().getOperationBinding("checkisDeletable");
        operationBinding.execute();
        if(operationBinding.getErrors().isEmpty()){
             
              flag=operationBinding.getResult().toString();
        }
        else{
           // System.out.println("operationBinding.getErrors() "+operationBinding.getErrors());
        }
        
        if(flag.equalsIgnoreCase("Y")){
            showPopup(pop1,true);      
        }
        else{//Deletable
//        Requirement Area can not be deleted as it is already in used
             String s=ADFModelUtils.resolvRsrc("MSG.1753");
            FacesMessage message = new FacesMessage(s);
//            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1753']}").toString());   
            message.setSeverity(FacesMessage.SEVERITY_ERROR);   
            FacesContext fc = FacesContext.getCurrentInstance();   
            fc.addMessage(null, message);  
        }
      /*   OperationBinding operationBinding =getBindings().getOperationBinding("Delete");
        operationBinding.execute();
        if(operationBinding.getErrors().isEmpty()){
            OperationBinding operationBinding1 =getBindings().getOperationBinding("Commit");
            operationBinding1.execute();
        }
         */
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
    public void cancelActionListen(ActionEvent actionEvent) {
        // Add event code here...
       
        OperationBinding operationBinding =getBindings().getOperationBinding("Rollback");
        operationBinding.execute();
        mode="Y";
    }
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void pop1dialogListner(DialogEvent dialogEvent) {
        if(dialogEvent.getOutcome().name().equalsIgnoreCase("yes")){
                  getBindings().getOperationBinding("Delete").execute();
                  OperationBinding binding = getBindings().getOperationBinding("Commit");
                  binding.execute();
                 
                  if(binding.getErrors().isEmpty()){
                      AdfFacesContext.getCurrentInstance().addPartialTarget(tableBinding);
                      //OperationBinding binding1 = getBindings().getOperationBinding("Rollback");
                     // binding1.execute();
//                     Requirement Area can not be deleted as it is already in used
                       String s=ADFModelUtils.resolvRsrc("MSG.1754");
                       FacesMessage message = new FacesMessage(s);
//                      String message1=resolvElDCMsg("#{bundle['MSG.1754']}").toString();
//                      FacesMessage message = new FacesMessage(message1);   
                          message.setSeverity(FacesMessage.SEVERITY_INFO);   
                          FacesContext fc = FacesContext.getCurrentInstance();   
                          fc.addMessage(null, message);  
                  }else{
//                      Record Not Deleted
                    String s=ADFModelUtils.resolvRsrc("MSG.1755");
                       FacesMessage message = new FacesMessage(s);
//                      String message1=resolvElDCMsg("#{bundle['MSG.1755']}").toString();
//                      FacesMessage message = new FacesMessage(message1);   
                          message.setSeverity(FacesMessage.SEVERITY_ERROR);   
                          FacesContext fc = FacesContext.getCurrentInstance();   
                          fc.addMessage(null, message);
                  }
              }
        if(dialogEvent.getOutcome().name().equalsIgnoreCase("no")){
                  
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

    public void setShowpop1(RichDialog showpop1) {
        this.showpop1 = showpop1;
    }

    public RichDialog getShowpop1() {
        return showpop1;
    }

    public void setPop1(RichPopup pop1) {
        this.pop1 = pop1;
    }

    public RichPopup getPop1() {
        return pop1;
    }

    public void rqmtNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
            if(object!=null){
                 String expression = "([a-zA-Z0-9]+)(([ ])([a-zA-Z0-9]+))*";
                                    CharSequence inputStr = object.toString();
                                    Pattern pattern = Pattern.compile(expression);
                                    Matcher matcher = pattern.matcher(inputStr);
//                 Special Character Not Allowed
                                    String s=ADFModelUtils.resolvRsrc("MSG.1056");
//                                    String error=resolvElDCMsg("#{bundle['MSG.1056']}").toString();

                 // System.out.println("-------------- "+object.toString());
                if (matcher.matches()) {
                 OperationBinding obind = getBindings().getOperationBinding("testRqmtvalidator");
                       obind.getParamsMap().put("rqmtName", object.toString());
                       obind.execute();
                       String flag=null;
                       if(obind.getErrors().isEmpty()){
                           flag=obind.getResult().toString();
                           //System.out.println(" if flag"+flag);
                            if(flag.equalsIgnoreCase("Y")){
                             // System.out.println(" else flag"+flag);
//                             Name Already Exists
                                String s1=ADFModelUtils.resolvRsrc("MSG.1751");
//                               String msg=resolvElDCMsg("#{bundle['MSG.1751']}").toString();
                               throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, s1, null));
                           }
                       }
                       System.out.println("flag value is "+flag);
                      
                     //  System.out.println("outsided flag"+flag); 
                }
                 else {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, s, null));
                      }
             }
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setTableBinding(RichTable tableBinding) {
        this.tableBinding = tableBinding;
    }

    public RichTable getTableBinding() {
        return tableBinding;
    }

    public void setRqmtNmBind(RichInputText rqmtNmBind) {
        this.rqmtNmBind = rqmtNmBind;
    }

    public RichInputText getRqmtNmBind() {
        return rqmtNmBind;
    }
}
