package mnfroutecard.view.bean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.validator.ValidatorException;

import mnfroutecardapp.view.utils.ADFUtils;

import mnfroutecardapp.view.utils.JSFUtils;

import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.DomainValidationException;
import oracle.jbo.domain.Timestamp;

public class SearchMnfRouteCardBean {
    private RichInputDate fromDatePgBind;

    public SearchMnfRouteCardBean() {
    }

    public void setFromDatePgBind(RichInputDate fromDatePgBind) {
        this.fromDatePgBind = fromDatePgBind;
    }

    public RichInputDate getFromDatePgBind() {
        return fromDatePgBind;
    }

    public void ToDateValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (fromDatePgBind.getValue() != null) {
               // System.out.println("From Date is "+fromDatePgBind.getValue());
               // System.out.println("To date is "+object);
                
                Timestamp t1 = (Timestamp) fromDatePgBind.getValue();
                Timestamp t2 = (Timestamp) object;
                
                //System.out.println("T2 Compare to T1 "+t2.compareTo(t1));
                if(t2.compareTo(t1) < 0){
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"To Date Should be greater than From Date.",null));
                }
                
            }
        }
    }

    public String createRouteCardAC() {
        OperationBinding ob = ADFUtils.findOperation("chkgetYearFyId");
        ob.execute();
        oracle.jbo.domain.Number chkUsr = (oracle.jbo.domain.Number)ob.getResult();
        //if (!(ob.getResult()== -1)) {
        if (!(chkUsr.compareTo(-1)==0)) {
            return "add";
        } else {
            JSFUtils.addFacesErrorMessage("Financial Year is not Defined  !");
        }
        return null;
    }
}
