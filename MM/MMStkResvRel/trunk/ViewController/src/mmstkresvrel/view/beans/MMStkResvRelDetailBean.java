package mmstkresvrel.view.beans;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import oracle.jbo.domain.Number;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class MMStkResvRelDetailBean {
    private RichPopup popUpUpdateReserve;
    public String mode = "V";

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg) {
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true) {
            String msg = resolvEl("#{bundle['" + mesg + "']}");
            message = new FacesMessage(msg);
        }
        if (sev.equalsIgnoreCase("E")) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        } else if (sev.equalsIgnoreCase("W")) {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
        } else if (sev.equalsIgnoreCase("I")) {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        } else {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        if (typFlg.equals("F")) {
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else if (typFlg.equals("V")) {
            throw new ValidatorException(message);
        }
    }


    public String getMode() {
        return mode;
    }

    public MMStkResvRelDetailBean() {
    }


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void popUpUpdateResv(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void popupUpdateRelease(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void setPopUpUpdateReserve(RichPopup popUpUpdateReserve) {
        this.popUpUpdateReserve = popUpUpdateReserve;
    }

    public RichPopup getPopUpUpdateReserve() {
        return popUpUpdateReserve;
    }

    public void editDetail(ActionEvent actionEvent) {
        // Add event code here...
        this.setMode("E");

    }

    public void saveDetail(ActionEvent actionEvent) {
        // Add event code here...

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding1 = bindings.getOperationBinding("doResvUpdt");
        operationBinding1.execute();
                BindingContainer bindings1 = getBindings();
                OperationBinding operationBinding = bindings1.getOperationBinding("Commit");
                operationBinding.execute();
        showFacesMessage("Record Saved Successfully.", "I", false, "F");
        System.out.println("Commit done");
        this.setMode("S");
    }

    public void cancelDetail(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        this.setMode("C");
    }

    public void modQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...

        if (object != null) {
            Number obj = (Number) object;
            if (obj.compareTo(new Number(0)) == 1) {
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("qtyValidator");
                operationBinding.getParamsMap().put("modVal", obj);
                operationBinding.execute();
                if (operationBinding.getResult() != null &&
                    "Y".equalsIgnoreCase(operationBinding.getResult().toString())) {
                    // Invalid quantity
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Quantity",
                                                                  null));

                }
            } else {
                if (obj.compareTo(new Number(0)) ==
                    -1) {
                    // value must be positive

                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Quantity Must be positive", null));
                }
            }
        }

    }
}
