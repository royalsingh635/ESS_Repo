package slsdiscountpolicyapp.view.bean;

import java.sql.Date;

import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Timestamp;
import oracle.jbo.domain.Number;
import oracle.jbo.rules.JboPrecisionScaleValidator;

public class SLSDiscountPolicyBean {
    private static ADFLogger _log = ADFLogger.createADFLogger(SLSDiscountPolicyBean.class);
    
    private RichInputDate startDtBinding;
    private RichInputDate expDtBinding;
    /**
     * mode value can be
     * A : Add Mode
     * E : Edit Mode
     * V : View Mode
     */
    private StringBuffer mode = new StringBuffer("V");
    private RichSelectOneRadio discTypBinding;
    private RichInputText discValBinding;
    private RichTable discountTableBinding;
    private Boolean rowsUpdated = false;

    public SLSDiscountPolicyBean() {
    }

    public void searchACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("search").execute();
    }

    public void resetACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("reset").execute();
    }

    public void setStartDtBinding(RichInputDate startDtBinding) {
        this.startDtBinding = startDtBinding;
    }

    public RichInputDate getStartDtBinding() {
        return startDtBinding;
    }

    public void setExpDtBinding(RichInputDate expDtBinding) {
        this.expDtBinding = expDtBinding;
    }

    public RichInputDate getExpDtBinding() {
        return expDtBinding;
    }

    public void expDtVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object != null && startDtBinding.getValue() != null){
            Timestamp expDt = (Timestamp)object;
            Timestamp strtDt = (Timestamp)startDtBinding.getValue();
            Date expDate = null;
            Date strtDate = null;
            try {
                expDate = expDt.dateValue();
                strtDate = strtDt.dateValue();
            } catch (SQLException e) {
            }
            _log.info("Start Date : "+strtDate+" End Date : "+expDate);
            if(strtDate.compareTo(expDate) >= 0){
                throw new ValidatorException(new FacesMessage("Invalid Expiry Date !","End Date should be greater than Start Date !"));
            }
        }
    }
    
    public void addACTION(ActionEvent actionEvent) {
        mode = new StringBuffer("A");
        rowsUpdated = false;
        this.getBindings().getOperationBinding("reset").execute();
    }
    public void editACTION(ActionEvent actionEvent) {
        rowsUpdated = false;
        this.getBindings().getOperationBinding("reset").execute();
        mode = new StringBuffer("E");
    }
    public void saveACTION(ActionEvent actionEvent) {
        OperationBinding binding = getBindings().getOperationBinding("Commit");
        binding.execute();
        if(binding.getErrors().isEmpty()){
            mode = new StringBuffer("V");
            rowsUpdated = false;
            this.getBindings().getOperationBinding("resetSearchEditRows").execute();
            StringBuilder saveMsg =
                new StringBuilder("<html><body><b><p style='color:red'>" + "Saved sucessfully !" +
                                  "</p></b>");
            saveMsg.append("<b>Discount entries have been saved sucessfully.");
            saveMsg.append("</body></html>");
            FacesMessage msg = new FacesMessage(saveMsg.toString());
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }else{
            StringBuilder saveMsg =
                new StringBuilder("<html><body><b><p style='color:red'>" + "Error in saving data !" +
                                  "</p></b>");
            saveMsg.append("<b>There have been an error while saving data. Please try again or contact ESS.");
            saveMsg.append("</body></html>");
            FacesMessage msg = new FacesMessage(saveMsg.toString());
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }
    public void cancelACTION(ActionEvent actionEvent) {
        OperationBinding binding = getBindings().getOperationBinding("Rollback");
        binding.execute();
        mode = new StringBuffer("V");
        rowsUpdated = false;
        this.getBindings().getOperationBinding("reset").execute();
    }
    public void updateACTION(ActionEvent actionEvent) {
        if(!ValidateAmount()){
            
        }else if(mode.toString().equals("A")){
            OperationBinding binding = getBindings().getOperationBinding("addDiscountRows");
            binding.execute();
            if(1 == (Integer)binding.getResult()){
                rowsUpdated = true;    
            }
            //mode = new StringBuffer("E");
            _log.info("AddDiscountRowsReturn : "+binding.getResult());    
        }else if(mode.toString().equals("E")){
            OperationBinding binding = getBindings().getOperationBinding("editDiscRows");
            binding.execute();
            if((Boolean)binding.getResult()){
                rowsUpdated = true;    
            }
            
            _log.info("EditDiscountRowsReturn : "+binding.getResult());    
        } 
    }

    public void setMode(StringBuffer mode) {
        this.mode = mode;
    }

    public StringBuffer getMode() {
        return mode;
    }
    
    public static BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setDiscTypBinding(RichSelectOneRadio discTypBinding) {
        this.discTypBinding = discTypBinding;
    }

    public RichSelectOneRadio getDiscTypBinding() {
        return discTypBinding;
    }

    public void setDiscValBinding(RichInputText discValBinding) {
        this.discValBinding = discValBinding;
    }

    public RichInputText getDiscValBinding() {
        return discValBinding;
    }
    
    /**Method to check invalid precision*/
    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }
    
    public Boolean ValidateAmount(){
        Boolean b = true;
        Object discTypO = discTypBinding.getValue();
        Object discValO = discValBinding.getValue();
        StringBuilder discTyp = (discTypO == null ? new StringBuilder("A") : new StringBuilder(discTypO.toString()));
        Number discVal = (discValO == null ? new Number(0) : (Number)discValO);
        
        if(discVal.compareTo(new Number(0)) < 0){
            b = false;
            FacesMessage msg = new FacesMessage("Invalid Discount Value !","Discount value should be greater than 0.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(discValBinding.getClientId(), msg);
        }else if(discTyp.toString().equals("P")){
            if(discVal.compareTo(new Number(100)) >= 0){
                b = false;
                FacesMessage msg = new FacesMessage("Invalid Percentage Value !","Discount value should be greater between 0 to 99.99.");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(discValBinding.getClientId(), msg);
            }  
        }else if(!isPrecisionValid(26, 6, discVal)){
            b = false;
            FacesMessage msg = new FacesMessage("Invalid Precision Value !","Discount value can have maximum Precision of (26,6).");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(discValBinding.getClientId(), msg);
        }
        
        return b;
    }

    public void salesExecVALID(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        this.getBindings().getOperationBinding("searchForEdit").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(discountTableBinding);
    }

    public void customerNmVALID(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        this.getBindings().getOperationBinding("searchForEdit").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(discountTableBinding);
    }

    public void custCatgVALID(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        this.getBindings().getOperationBinding("searchForEdit").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(discountTableBinding);
    }

    public void itmNmVALID(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        this.getBindings().getOperationBinding("searchForEdit").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(discountTableBinding);
    }

    public void itmGrpVALID(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        this.getBindings().getOperationBinding("searchForEdit").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(discountTableBinding);
    }

    public void discountBasisVALID(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        this.getBindings().getOperationBinding("searchForEdit").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(discountTableBinding);
    }

    public void setDiscountTableBinding(RichTable discountTableBinding) {
        this.discountTableBinding = discountTableBinding;
    }

    public RichTable getDiscountTableBinding() {
        return discountTableBinding;
    }

    public void setRowsUpdated(Boolean rowsUpdated) {
        this.rowsUpdated = rowsUpdated;
    }

    public Boolean getRowsUpdated() {
        return rowsUpdated;
    }
}
