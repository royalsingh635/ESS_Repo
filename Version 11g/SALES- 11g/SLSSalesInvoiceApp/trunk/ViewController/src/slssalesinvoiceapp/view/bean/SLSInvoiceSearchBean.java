package slssalesinvoiceapp.view.bean;

import java.sql.SQLException;

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

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;


public class SLSInvoiceSearchBean {
    public SLSInvoiceSearchBean() {
    }
    public Object resolvElDCMsg(String data) {
           FacesContext fc = FacesContext.getCurrentInstance();
           Application app = fc.getApplication();
           ExpressionFactory elFactory = app.getExpressionFactory();
           ELContext elContext = fc.getELContext();
           ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
           return valueExp.getValue(elContext);
       } 

    public void searchACTION(ActionEvent actionEvent) {
        getBindings().getOperationBinding("search").execute();
    }

    public void resetAction(ActionEvent actionEvent) {
        getBindings().getOperationBinding("reset").execute();
    }
    
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    /**
     * Validator to check negative value of fields
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void negativeNumberVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object != null){
            
            int i = ((Number) object).compareTo((Object)new Number(0));
            if(i == -1){
               // FacesMessage message = new FacesMessage("Invalid number!","Cannot enter negative value!");   
               FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1002']}").toString(),resolvElDCMsg("#{bundle['MSG.1003']}").toString());   
                message.setSeverity(FacesMessage.SEVERITY_ERROR); 
                throw new ValidatorException(message);
            }
                
            }
        }
    /**
     * Validator to validate fromShipMentQuantity field
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void fromNoOfItemsVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        this.negativeNumberVAL(facesContext, uIComponent, object);
        System.out.println("Object :"+object);
        if(object != null){
            OperationBinding binding = this.getBindings().getOperationBinding("isFromNoOfShipmentValid");
            Number v = new Number(0);
            try {
                v = new Number(object.toString());
            } catch (SQLException e) {
            }
            binding.getParamsMap().put("val", v);
            binding.execute();
            System.out.println("Return :"+binding.getResult());
            if(binding.getResult().equals((Object)false)){
               // FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"From Shipment should be less than or equal to To Shipment Quantity.",null);    
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,resolvElDCMsg("#{bundle['MSG.1005']}").toString(),null);    
                throw new ValidatorException(message);
            }
        }
        
    }
    /**
     * To Number of items validator
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void toNoOfItemsVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // isFromNoOfShipmentValid1
        this.negativeNumberVAL(facesContext, uIComponent, object);
        System.out.println("Object :"+object);
        if(object != null){
            OperationBinding binding = this.getBindings().getOperationBinding("isToNoOfShipmentValid");
            Number v = new Number(0);
            try {
                v = new Number(object.toString());
            } catch (SQLException e) {
            }
            binding.getParamsMap().put("val", v);
            binding.execute();
            System.out.println("Return :"+binding.getResult());
            if(binding.getResult().equals((Object)false)){
              //  FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"To Shipment should be greater than or equal to From Shipment Quantity.",null);    
              FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,resolvElDCMsg("#{bundle['MSG.1007']}").toString(),null);    
                throw new ValidatorException(message);
            }
        }
    }

    public void toSalesAmountVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        //isToSIAmountValid
        this.negativeNumberVAL(facesContext, uIComponent, object);
        System.out.println("Object :"+object);
        if(object != null){
            OperationBinding binding = this.getBindings().getOperationBinding("isToSIAmountValid");
            Number v = new Number(0);
            try {
                v = new Number(object.toString());
            } catch (SQLException e) {
            }
            binding.getParamsMap().put("val", v);
            binding.execute();
            System.out.println("Return :"+binding.getResult());
            if(binding.getResult().equals((Object)false)){
               // FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"To Shipment Amount should be greater than or equal to From Shipment Amount.",null);    
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,resolvElDCMsg("#{bundle['MSG.1009']}").toString(),null);    
                throw new ValidatorException(message);
            }
        }
    }
    /**
     * From SIAmount
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void fromSalesAmountVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // isFromSIAmountValid
        this.negativeNumberVAL(facesContext, uIComponent, object);
        System.out.println("Object :"+object);
        if(object != null){
            OperationBinding binding = this.getBindings().getOperationBinding("isFromSIAmountValid");
            Number v = new Number(0);
            try {
                v = new Number(object.toString());
            } catch (SQLException e) {
            }
            binding.getParamsMap().put("val", v);
            binding.execute();
            System.out.println("Return :"+binding.getResult());
            if(binding.getResult().equals((Object)false)){
               // FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"From Shipment Amount should be less than or equal to To Shipment Amount.",null);    
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,resolvElDCMsg("#{bundle['MSG.1011']}").toString(),null);    
                throw new ValidatorException(message);
            }
        }
    }

    public String createSalesInvoiceACTION() {
        OperationBinding binding = this.getBindings().getOperationBinding("isFinancialYearValid");
        binding.execute();
        Object object = binding.getResult();
        if(object.equals((Object)true)){
            return "A";
        }
        return null;
    }
}
