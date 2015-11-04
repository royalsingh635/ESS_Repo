package pricepolicyapp.view.beans;

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
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.context.RequestContext;

public class PriceBean {
    public PriceBean() {
    }
    String cldid=resolvEl1("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
    String orgid=resolvEl1("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
    Integer slocid=Integer.parseInt(resolvEl1("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
    Integer userid = Integer.parseInt(resolvEl1("#{pageFlowScope.GLBL_APP_USR}").toString());
    String hoOrgid=resolvEl1("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
    String glbl_mode=resolvEl1("#{pageFlowScope.mode}").toString();
    
    String edit_mode="C";
    public String resolvEl1(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void addActionListner(ActionEvent actionEvent) {
        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        if(operationBinding.getErrors().isEmpty()){
            RequestContext context = RequestContext.getCurrentInstance();
            context.getPageFlowScope().put("mode","C");
            edit_mode="C";
            
        }
    }

    public void editActionListner(ActionEvent actionEvent) {
        // Add event code here...
      
            RequestContext context = RequestContext.getCurrentInstance();
            context.getPageFlowScope().put("mode","C");
            edit_mode="E";

    }

    public void saveActionListener(ActionEvent actionEvent) {
        // Add event code here...
        
       /*  if(resolvEl1("#{pageFlowScope.PARAM_EO_ID}")!=null){
            if(glbl_mode.equalsIgnoreCase("C") && edit_mode.equalsIgnoreCase("C")){
        Integer eoId=Integer.parseInt(resolvEl1("#{pageFlowScope.PARAM_EO_ID}").toString());
        if(eoId!=null && eoId!=-1){
            OperationBinding ObindSupplier =getBindings().getOperationBinding("insertSupplier");
            ObindSupplier.getParamsMap().put("supplierId",eoId );
            ObindSupplier.execute();
        }
            }
        } */
        OperationBinding operationBinding =getBindings().getOperationBinding("Commit");
        operationBinding.execute();
        if(operationBinding.getErrors().isEmpty()){
            FacesMessage message = new FacesMessage("Record Saved Successfully!");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            RequestContext context = RequestContext.getCurrentInstance();
            context.getPageFlowScope().put("mode","S");
            
        }
    }

    public void cancelActionListener(ActionEvent actionEvent) {
        OperationBinding operationBinding =getBindings().getOperationBinding("Rollback");
        operationBinding.execute();
        if(operationBinding.getErrors().isEmpty()){
            RequestContext context = RequestContext.getCurrentInstance();
            context.getPageFlowScope().put("mode","S");
            
        }
    }

    public void NameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String currName="";
        if(object!=null){
            currName=object.toString();
        }
                        String expression = "([a-zA-Z0-9]+)(([ ])([a-zA-Z0-9]+))*";
                        CharSequence inputStr = object.toString();
                        Pattern pattern = Pattern.compile(expression);
                        Matcher matcher = pattern.matcher(inputStr);
                        String error=resolvEl("Special Character Not Allowed").toString();

                        if (matcher.matches()) {
                    
                        OperationBinding operationBinding =getBindings().getOperationBinding("priceNameValid");
                        operationBinding.getParamsMap().put("Name", currName);
                        operationBinding.execute();
                        if(operationBinding.getErrors().isEmpty()){
                            String temp=operationBinding.getResult().toString();
                            if("Y".equalsIgnoreCase(temp)){
                                
                            }else if("N".equalsIgnoreCase(temp)){
                                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Policy Name Already Exists", null));  
                            }
                        }
                        }
                        else {
                                       throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
                        }
        

    }
    public String resolvEl(String data)
     {
             FacesContext fc = FacesContext.getCurrentInstance();
             Application app = fc.getApplication();
             ExpressionFactory elFactory = app.getExpressionFactory();
             ELContext elContext = fc.getELContext();
             ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
             String Message = valueExp.getValue(elContext).toString();
             return Message;
         }

    public void actvValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(valueChangeEvent.getNewValue()!=null){
            if("false".equalsIgnoreCase(valueChangeEvent.getNewValue().toString())){
                OperationBinding operationBinding =getBindings().getOperationBinding("setinacivedt");
                operationBinding.execute();   
            }
        }
    }

    public void SupplierAddActionListener(ActionEvent actionEvent) {
        // Add event code here...
        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("addSupplier");
        operationBinding.execute();
    }

    public void SupplierSaveAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding operationBinding =getBindings().getOperationBinding("Commit");
        operationBinding.execute();
        if(operationBinding.getErrors().isEmpty()){
            FacesMessage message = new FacesMessage("Supplier Saved Successfully!");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            RequestContext context = RequestContext.getCurrentInstance();
            context.getPageFlowScope().put("mode","S");
            
        }
    }

    public void setEdit_mode(String edit_mode) {
        this.edit_mode = edit_mode;
    }

    public String getEdit_mode() {
        return edit_mode;
    }

    public void supplierDelActionListen(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding operationBinding =getBindings().getOperationBinding("delSupplier");
        operationBinding.execute();
        
    }

    public void suppNameValid(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        //EoNm
        if(object!=null){
            OperationBinding operationBinding =getBindings().getOperationBinding("supplierNameValid");
            operationBinding.getParamsMap().put("suppName", object.toString());
            operationBinding.execute();
            if(operationBinding.getErrors().isEmpty()){
                String temp=operationBinding.getResult().toString();
               // System.out.println("supplier out put "+temp);
                String error="Supplier Name Already Exists";
                if("Y".equalsIgnoreCase(temp)){
                    
                }else if("N".equalsIgnoreCase(temp)){
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
                }
            }
        }
    }
}
