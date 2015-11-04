package slstaxformtrackapp.model.views.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

public class SlsTaxFormTrackBean {
    public SlsTaxFormTrackBean() {
    }

    public void searchInvoiceAL(ActionEvent actionEvent) {

        OperationBinding op = ADFBeanUtils.findOperation("searchInvoice");
        op.execute();
    }

    public void resetSearch(ActionEvent actionEvent) {

        OperationBinding op = ADFBeanUtils.findOperation("reset");
        op.execute();
    }

    public void addReceiveFormAL(ActionEvent actionEvent) {

        OperationBinding op = ADFBeanUtils.findOperation("addReceiveForm");
        op.execute();
    }

    public void amtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Object valAmt = uIComponent.getAttributes().get("invAmt");
        
            Number formAmt = (Number) valAmt;
            Number val =  new Number(0);
            val = (Number) object;
            
            if (!(ADFBeanUtils.isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, (ADFModelUtils.resolvRsrc("MSG.1107")),    //Invalid Precision(26,6).
                                                              null));
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              (ADFModelUtils.resolvRsrc("MSG.198")), null));         //Value should be greater than zero.
            } else {
                
                if (val.compareTo(formAmt) > 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  (ADFModelUtils.resolvRsrc("MSG.2682")), null));     //Total Amount should be less than or equals to Invoice Amount
                }
                else{
                    System.out.println("Inside false :::");
                }
            }
        }
    }
}
