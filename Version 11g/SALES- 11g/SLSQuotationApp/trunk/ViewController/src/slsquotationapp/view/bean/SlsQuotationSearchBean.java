package slsquotationapp.view.bean;

import java.sql.SQLException;

import java.util.Map;

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
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputNumberSlider;
import oracle.adf.view.rich.component.rich.input.RichInputRangeSlider;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.model.NumberRange;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;

public class SlsQuotationSearchBean {
    private String formMode = "V";
    private RichInputText searchQuotNoBind;
    private RichSelectOneChoice searchEOTypeBind;
    private RichInputListOfValues searchEONmBind;
    private RichSelectOneChoice searchStatusTypeBind;
    private RichSelectOneChoice searchPaymentStatusBind;
    private RichInputDate searchStrtDtBind;
    private RichInputDate searchEndDtBind;
    private RichSelectOneChoice statusSearchBind;
    private RichSelectOneChoice currIDBind;

    public SlsQuotationSearchBean() {
    }
    
    /**
     * ActionEvent to perform searchAction
     * @param actionEvent
     */
    public void searchACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("searchQuotationViewCriteria").execute();
        
    }
    /**
     * ActionEvent to perform resetAction
     * @param actionEvent
     */
    public void resetACTION(ActionEvent actionEvent) {
       
        this.getBindings().getOperationBinding("resetQuotationViewCriteria").execute();
    }

    public String createQuotationACTION() {
        this.formMode = "C";
        return "C";
    }

    public String viewQuotationACTION() {
        this.formMode = "V";
        return "V";
    }

    public String deleteQuotationACTION() {
        this.formMode = "D";
        return "D";
    }

    /**
     *  method to get Bindings
     */
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
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.827']}").toString(),resolvElDCMsg("#{bundle['MSG.828']}").toString()+"!");  //"Invalid number!"&& "Cannot enter negative value!"
                message.setSeverity(FacesMessage.SEVERITY_ERROR); 
                throw new ValidatorException(message);
            }
                
            }
        }
    
    
    public void setSearchQuotNoBind(RichInputText searchQuotNoBind) {
        this.searchQuotNoBind = searchQuotNoBind;
    }

    public RichInputText getSearchQuotNoBind() {
        return searchQuotNoBind;
    }

    public void setSearchEOTypeBind(RichSelectOneChoice searchEOTypeBind) {
        this.searchEOTypeBind = searchEOTypeBind;
    }

    public RichSelectOneChoice getSearchEOTypeBind() {
        return searchEOTypeBind;
    }

    public void setSearchEONmBind(RichInputListOfValues searchEONmBind) {
        this.searchEONmBind = searchEONmBind;
    }

    public RichInputListOfValues getSearchEONmBind() {
        return searchEONmBind;
    }

    public void setSearchStatusTypeBind(RichSelectOneChoice searchStatusTypeBind) {
        this.searchStatusTypeBind = searchStatusTypeBind;
    }

    public RichSelectOneChoice getSearchStatusTypeBind() {
        return searchStatusTypeBind;
    }

    public void setSearchPaymentStatusBind(RichSelectOneChoice searchPaymentStatusBind) {
        this.searchPaymentStatusBind = searchPaymentStatusBind;
    }

    public RichSelectOneChoice getSearchPaymentStatusBind() {
        return searchPaymentStatusBind;
    }

    public void setSearchStrtDtBind(RichInputDate searchStrtDtBind) {
        this.searchStrtDtBind = searchStrtDtBind;
    }

    public RichInputDate getSearchStrtDtBind() {
        return searchStrtDtBind;
    }

    public void setSearchEndDtBind(RichInputDate searchEndDtBind) {
        this.searchEndDtBind = searchEndDtBind;
    }

    public RichInputDate getSearchEndDtBind() {
        return searchEndDtBind;
    }
    
    /**
     * From item
     * @param facesContext
     * @param uIComponent
     * @param object
     */

    public void ItemRangeFromVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        //isSearchFromNoOfItemValid
        this.negativeNumberVAL(facesContext, uIComponent, object);
     //  System.out.println("Object :"+object);
        if(object != null){
            OperationBinding binding = this.getBindings().getOperationBinding("isSearchFromNoOfItemValid");
            Number v = new Number(0);
            try {
                v = new Number(object.toString());
            } catch (SQLException e) {
            }
            binding.getParamsMap().put("val", v);
            binding.execute();
          //  System.out.println("Return :"+binding.getResult());
            if(binding.getResult().equals((Object)false)){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,resolvElDCMsg("#{bundle['MSG.829']}").toString(),null);  //"From item should be less than or equal to TO Item."  
                throw new ValidatorException(message);
            }
        }
        
        
    }
    /**
     * To Item
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void ItemRangeToVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        this.negativeNumberVAL(facesContext, uIComponent, object);
       // System.out.println("Object :"+object);
        if(object != null){
            OperationBinding binding = this.getBindings().getOperationBinding("isSearchToNoOfItemValid");
            Number v = new Number(0);
            try {
                v = new Number(object.toString());
            } catch (SQLException e) {
            }
            binding.getParamsMap().put("val", v);
            binding.execute();
          //  System.out.println("Return :"+binding.getResult());
            if(binding.getResult().equals((Object)false)){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,resolvElDCMsg("#{bundle['MSG.830']}").toString(),null); //"To item should be greater than or equal to  from Item."   
                throw new ValidatorException(message);
            }
        }
    }

    /**
     * Validator to validate the from quantity range
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void QuantityRangeFromVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        this.negativeNumberVAL(facesContext, uIComponent, object);
       // System.out.println("Object :"+object);
        if(object != null){
            OperationBinding binding = this.getBindings().getOperationBinding("isSearchFromQuantityValid");
            Number v = new Number(0);
            try {
                v = new Number(object.toString());
            } catch (SQLException e) {
            }
            binding.getParamsMap().put("val", v);
            binding.execute();
          //  System.out.println("Return :"+binding.getResult());
            if(binding.getResult().equals((Object)false)){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,resolvElDCMsg("#{bundle['MSG.831']}").toString(),null);  //"From Amount should be less than or equal to To amount."  
                throw new ValidatorException(message);
            }
        }
        
        
    }
    /**
     * Validator to validate the To quantity range
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void QuantityRangeToVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        this.negativeNumberVAL(facesContext, uIComponent, object);
       // System.out.println("Object :"+object);
        if(object != null){
            OperationBinding binding = this.getBindings().getOperationBinding("isSearchToQuantityValid");
            Number v = new Number(0);
            try {
                v = new Number(object.toString());
            } catch (SQLException e) {
            }
            binding.getParamsMap().put("val", v);
            binding.execute();
           // System.out.println("Return :"+binding.getResult());
            if(binding.getResult().equals((Object)false)){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,resolvElDCMsg("#{bundle['MSG.832']}").toString(),null);  //"To Amount should be greater than or equal to From amount."  
                throw new ValidatorException(message);
            }
        }
        
    }
    /**
     * Validator to validate TotalAmountFrom
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void TotalAmountFromVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        
        this.negativeNumberVAL(facesContext, uIComponent, object);
        //System.out.println("Object :"+object);
        if(object != null){
            OperationBinding binding = this.getBindings().getOperationBinding("isSearchFromTotalAmountValid");
            Number v = new Number(0);
            try {
                v = new Number(object.toString());
            } catch (SQLException e) {
            }
            binding.getParamsMap().put("val", v);
            binding.execute();
           // System.out.println("Return :"+binding.getResult());
            if(binding.getResult().equals((Object)false)){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,resolvElDCMsg("#{bundle['MSG.831']}").toString(),null); //"From Amount should be less than or equal to To amount."   
                //message.setSeverity(FacesMessage.SEVERITY_INFO);
                throw new ValidatorException(message);
            }
        }
    }
    /**
     * Validator to validate ToTotalAmountFrom
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    
    public void TotalAmountToVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
            
            this.negativeNumberVAL(facesContext, uIComponent, object);
            //System.out.println("Object :"+object);
            if(object != null){
                OperationBinding binding = this.getBindings().getOperationBinding("isSearchToTotalAmountValid");
                Number v = new Number(0);
                try {
                    v = new Number(object.toString());
                } catch (SQLException e) {
                }
                binding.getParamsMap().put("val", v);
                binding.execute();
               // System.out.println("Return :"+binding.getResult());
                if(binding.getResult().equals((Object)false)){
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,resolvElDCMsg("#{bundle['MSG.832']}").toString(),null);  //"To Amount should be Greater than or equal to From amount."  
                    //message.setSeverity(FacesMessage.SEVERITY_INFO);
                    throw new ValidatorException(message);
                }
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

    public void setStatusSearchBind(RichSelectOneChoice statusSearchBind) {
        this.statusSearchBind = statusSearchBind;
    }

    public RichSelectOneChoice getStatusSearchBind() {
        return statusSearchBind;
    }

    public void setCurrIDBind(RichSelectOneChoice currIDBind) {
        this.currIDBind = currIDBind;
    }

    public RichSelectOneChoice getCurrIDBind() {
        return currIDBind;
    }
}
