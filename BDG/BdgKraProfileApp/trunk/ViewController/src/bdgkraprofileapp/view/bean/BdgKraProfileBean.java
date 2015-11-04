package bdgkraprofileapp.view.bean;

import bdgkraprofileapp.view.utils.ADFUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.domain.Timestamp;

public class BdgKraProfileBean {
    private String mode = "V";
    private RichInputDate inactiveDtPgBind;
    private RichInputText inactiveReasonPgBind;

    public BdgKraProfileBean() {
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }


    /**Method to get Binding Container*/
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void AddKraButtonAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("ResetKraDef").execute();
        ADFUtils.findOperation("SearchKraDef").execute();

        ADFUtils.findOperation("Execute").execute();
        mode = "A";
        ADFUtils.findOperation("CreateInsert").execute();
    }

    public void EditKraButtonAL(ActionEvent actionEvent) {
        mode = "E";
        // Add event code here...
    }

    public void SaveKraButtonAL(ActionEvent actionEvent) {

        ADFUtils.findOperation("GenerateKraValue").execute();

        DCIteratorBinding parentIter = (DCIteratorBinding) getBindings().get("BdgKraPrfVO1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey();

        mode = "V";
        ADFUtils.findOperation("Commit").execute();
        ADFUtils.findOperation("Execute").execute();

        if (parentKey != null) {
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        }

    }

    public void CancelKraButtonAL(ActionEvent actionEvent) {
        mode = "V";
        ADFUtils.findOperation("Rollback").execute();
    }

    public void SearchKraButtonAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("SearchKraDef").execute();
    }

    public void ResetKraButtonAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("ResetKraDef").execute();
    }

    public void setInactiveDtPgBind(RichInputDate inactiveDtPgBind) {
        this.inactiveDtPgBind = inactiveDtPgBind;
    }

    public RichInputDate getInactiveDtPgBind() {
        return inactiveDtPgBind;
    }

    public void setInactiveReasonPgBind(RichInputText inactiveReasonPgBind) {
        this.inactiveReasonPgBind = inactiveReasonPgBind;
    }

    public RichInputText getInactiveReasonPgBind() {
        return inactiveReasonPgBind;
    }

    public void ActiveCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("false")) {
                inactiveDtPgBind.setValue(new Timestamp(System.currentTimeMillis()));
            } else {
                inactiveDtPgBind.setValue(null);
                inactiveReasonPgBind.setValue(null);
            }
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            AdfFacesContext.getCurrentInstance().addPartialTarget(inactiveDtPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(inactiveReasonPgBind);
        }
    }

    public void KraDescValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            //String expression = "(([a-zA-Z0-9]+)(((([\\s\\+])?([a-zA-Z0-9]+))+)?([\\+])?)*)+"; //String with space in middle and end which accepts + also as special character
            //String expression = "([a-zA-Z0-9]+)(([ ])([a-zA-Z0-9]+))*([ ])*"; //String with space in middle and end

            String expression = "([a-zA-Z0-9]+)(([ ])([a-zA-Z0-9]+))*"; //String with space in middle

            CharSequence inputStr = object.toString();
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Special Character Not Allowed.", null));
            }

            OperationBinding ob = ADFUtils.findOperation("ChkKraDescriptionDuplicate");
            ob.getParamsMap().put("name", object);
            ob.execute();
            if ((Boolean) ob.getResult()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Kra Description.",
                                                              null));
            }
        }
    }
}
