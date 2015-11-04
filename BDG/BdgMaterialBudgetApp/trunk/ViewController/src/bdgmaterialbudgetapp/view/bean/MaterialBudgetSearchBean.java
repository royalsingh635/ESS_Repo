package bdgmaterialbudgetapp.view.bean;

import bdgmaterialbudgetapp.view.utils.ADFUtils;

import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;

import org.apache.myfaces.trinidad.context.RequestContext;

public class MaterialBudgetSearchBean {
    private RichInputText bdgIdBind;
    private RichSelectOneChoice bdgFyIdBinding;
    private RichInputDate bdgStDtBinding;
    private RichInputDate bdgEndDtBinding;
    private RichSelectOneChoice orgBinding;
    private RichInputListOfValues empNmBinding;
    private RichSelectOneChoice budgetStatBinding;
    private RichTable searchTablePgBind;
    private RichOutputText amtNotationDescPgBind;

    public MaterialBudgetSearchBean() {
    }


    public String resetSearchAction() {
        bdgIdBind.setValue(null);
        bdgFyIdBinding.setValue(null);
        bdgStDtBinding.setValue(null);
        bdgEndDtBinding.setValue(null);
        getOrgBinding().setValue(null);
        empNmBinding.setValue(null);
        bdgStDtBinding.setValue(null);
        OperationBinding op = ADFUtils.findOperation("resetSearchMethod");
        op.execute();
        return null;
    }

    public void setBdgIdBind(RichInputText bdgIdBind) {
        this.bdgIdBind = bdgIdBind;
    }

    public RichInputText getBdgIdBind() {
        return bdgIdBind;
    }

    public void setBdgFyIdBinding(RichSelectOneChoice bdgFyIdBinding) {
        this.bdgFyIdBinding = bdgFyIdBinding;
    }

    public RichSelectOneChoice getBdgFyIdBinding() {
        return bdgFyIdBinding;
    }

    public void setBdgStDtBinding(RichInputDate bdgStDtBinding) {
        this.bdgStDtBinding = bdgStDtBinding;
    }

    public RichInputDate getBdgStDtBinding() {
        return bdgStDtBinding;
    }

    public void setBdgEndDtBinding(RichInputDate bdgEndDtBinding) {
        this.bdgEndDtBinding = bdgEndDtBinding;
    }

    public RichInputDate getBdgEndDtBinding() {
        return bdgEndDtBinding;
    }

    public void bdgEndDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (bdgStDtBinding.getValue() != null && bdgStDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) bdgStDtBinding.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        throw new ValidatorException(new FacesMessage("End Date can not be Before Start Date."));
                    }
                }
            }
        }
    }


    public void setOrgBinding(RichSelectOneChoice orgBinding) {
        this.orgBinding = orgBinding;
    }

    public RichSelectOneChoice getOrgBinding() {
        return orgBinding;
    }

    public void setEmpNmBinding(RichInputListOfValues empNmBinding) {
        this.empNmBinding = empNmBinding;
    }

    public RichInputListOfValues getEmpNmBinding() {
        return empNmBinding;
    }

    public void setBudgetStatBinding(RichSelectOneChoice budgetStatBinding) {
        this.budgetStatBinding = budgetStatBinding;
    }

    public RichSelectOneChoice getBudgetStatBinding() {
        return budgetStatBinding;
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

    public void setSearchTablePgBind(RichTable searchTablePgBind) {
        this.searchTablePgBind = searchTablePgBind;
    }

    public RichTable getSearchTablePgBind() {
        return searchTablePgBind;
    }

    public void setAmtNotationDescPgBind(RichOutputText amtNotationDescPgBind) {
        this.amtNotationDescPgBind = amtNotationDescPgBind;
    }

    public RichOutputText getAmtNotationDescPgBind() {
        return amtNotationDescPgBind;
    }
}
