package bdgfinancebudgetapp.view.bean;

import adf.utils.model.ADFModelUtils;

import bdgfinancebudgetapp.view.utils.ADFUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;

import org.apache.myfaces.trinidad.context.RequestContext;

public class SearchBdgFinBean {
    private RichInputDate endDatePgBind;
    private RichInputDate startDatePgBind;
    private RichTable searchTablePgBind;
    private RichOutputText amtNotationDescPgBind;

    public SearchBdgFinBean() {
    }

    public void searchButtonAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("searchDataAccordingly").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTablePgBind);
    }

    public void resetButtonAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("resetDataAccordingly").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTablePgBind);

    }

    public void startDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (endDatePgBind.getValue() != null) {
                // System.out.println("From Date is "+fromDatePgBind.getValue());
                // System.out.println("To date is "+object);

                Timestamp t1 = (Timestamp) endDatePgBind.getValue();
                Timestamp t2 = (Timestamp) object;

                //System.out.println("T2 Compare to T1 "+t2.compareTo(t1));
                if (t1.compareTo(t2) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  ADFModelUtils.resolvRsrc("MSG.2061"), null));
                    /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "From Date Should be on or before To Date.", null)); */
                }
            }
        }
    }

    public void endDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (startDatePgBind.getValue() != null) {
                // System.out.println("From Date is "+fromDatePgBind.getValue());
                // System.out.println("To date is "+object);

                Timestamp t1 = (Timestamp) startDatePgBind.getValue();
                Timestamp t2 = (Timestamp) object;

                //System.out.println("T2 Compare to T1 "+t2.compareTo(t1));
                if (t2.compareTo(t1) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  ADFModelUtils.resolvRsrc("MSG.1052"), null));
                    /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "To Date Should be greater than From Date.", null)); */
                }
            }
        }
    }

    public void setEndDatePgBind(RichInputDate endDatePgBind) {
        this.endDatePgBind = endDatePgBind;
    }

    public RichInputDate getEndDatePgBind() {
        return endDatePgBind;
    }

    public void setStartDatePgBind(RichInputDate startDatePgBind) {
        this.startDatePgBind = startDatePgBind;
    }

    public RichInputDate getStartDatePgBind() {
        return startDatePgBind;
    }

    public void setSearchTablePgBind(RichTable searchTablePgBind) {
        this.searchTablePgBind = searchTablePgBind;
    }

    public RichTable getSearchTablePgBind() {
        return searchTablePgBind;
    }

    public void amountNotationVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());

            RequestContext.getCurrentInstance().getPageFlowScope().put("P_AMT_NOTATION", vce.getNewValue());

            Number val = (Number) vce.getNewValue();
            if (val.compareTo(1) > 0) {
                RequestContext.getCurrentInstance().getPageFlowScope().put("P_NOTATION_DISP", "Y");
            } else {
                RequestContext.getCurrentInstance().getPageFlowScope().put("P_NOTATION_DISP", "N");
            }
            RequestContext.getCurrentInstance().getPageFlowScope().put("DISP_AMT_NOTATION",
                                                                       amtNotationDescPgBind.getValue());
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTablePgBind);

    }

    public void setAmtNotationDescPgBind(RichOutputText amtNotationDescPgBind) {
        this.amtNotationDescPgBind = amtNotationDescPgBind;
    }

    public RichOutputText getAmtNotationDescPgBind() {
        return amtNotationDescPgBind;
    }
}
