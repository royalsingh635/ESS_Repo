package appschemeprofile.view.bean;

import appschemeprofile.view.utils.ADFUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.domain.Timestamp;

public class SearchPageBean {
    private RichTable searchTablePgBind;
    private RichInputDate fromDatePgBind;

    public SearchPageBean() {
    }

    public void SearchButtonAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("SearchData").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTablePgBind);
    }

    public void ResetButtonAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("ResetData").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTablePgBind);
    }

    public void setSearchTablePgBind(RichTable searchTablePgBind) {
        this.searchTablePgBind = searchTablePgBind;
    }

    public RichTable getSearchTablePgBind() {
        return searchTablePgBind;
    }

    public void setFromDatePgBind(RichInputDate fromDatePgBind) {
        this.fromDatePgBind = fromDatePgBind;
    }

    public RichInputDate getFromDatePgBind() {
        return fromDatePgBind;
    }

    public void ToDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (fromDatePgBind.getValue() != null) {
                // System.out.println("From Date is "+fromDatePgBind.getValue());
                // System.out.println("To date is "+object);

                Timestamp t1 = (Timestamp)fromDatePgBind.getValue();
                Timestamp t2 = (Timestamp)object;

                //System.out.println("T2 Compare to T1 "+t2.compareTo(t1));
                if (t2.compareTo(t1) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "To Date Should be greater than From Date.", null));
                }
            }
        }
    }
}
