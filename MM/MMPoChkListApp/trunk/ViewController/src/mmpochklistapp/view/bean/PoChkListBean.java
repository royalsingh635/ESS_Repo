package mmpochklistapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Timestamp;

public class PoChkListBean {
    private RichInputDate poDateBinding;
    private RichInputListOfValues poNoBinding;

    public PoChkListBean() {
    }

    public void searchPoChkListAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("search").execute();
    }

    public void resetPoChkListAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("resetSearch").execute();
    }

    public void dueDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (poDateBinding.getValue() != null && poDateBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) poDateBinding.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    // System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
//                        Due date can not be greater than PO Date
                        String msg = ADFModelUtils.resolvRsrc("MSG.2428");
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                    }
                }
            }
        }
    }

    public void clearDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (poDateBinding.getValue() != null && poDateBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) poDateBinding.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    // System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
//                        String msg = "Clear date can not be greater than PO Date!";
                        String msg = ADFModelUtils.resolvRsrc("MSG.2429");
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                    }
                }
            }

        }

    }

    public void setPoDateBinding(RichInputDate poDateBinding) {
        this.poDateBinding = poDateBinding;
    }

    public RichInputDate getPoDateBinding() {
        return poDateBinding;
    }

    public void saveAction(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Commit").execute();
    }
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void AddNewCheckListAction(ActionEvent actionEvent) {
        OperationBinding obind=getBindings().getOperationBinding("addNewCheckList");
        obind.execute();
        System.out.println("obind errors are "+obind.getErrors());
        String flag=obind.getResult().toString();
        if(flag.equalsIgnoreCase("P")){
            String msg = ADFModelUtils.resolvRsrc("MSG.2430");
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(poNoBinding.getClientId(), message);
        }
    }

    public void setPoNoBinding(RichInputListOfValues poNoBinding) {
        this.poNoBinding = poNoBinding;
    }

    public RichInputListOfValues getPoNoBinding() {
        return poNoBinding;
    }
}
