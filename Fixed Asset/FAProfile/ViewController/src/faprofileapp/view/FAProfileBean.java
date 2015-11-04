package faprofileapp.view;

//import java.util.Date;
import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Date;


public class FAProfileBean {
    private String mode = "V";
    private String finalize;
    private RichInputText round_validater;
    private RichInputDate effectiveDate;
    private RichPanelBox companyLawBinding;
    private RichPanelBox itLawBinding;
    private RichTable prflTblBinding;
    private RichPopup deletePopupBinding;
    private RichSelectBooleanCheckbox coNegDepBind;

    public void setFinalize(String finalize) {
        this.finalize = finalize;
    }

    public String getFinalize() {
        return finalize;
    }
    private RichSelectOneChoice applicableLaw;

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public FAProfileBean() {
    }

    public String addActionMethod() {
        this.mode = "D";
        ADFBeanUtils.callBindingsMethod("CreateInsert", null, null);
        return null;
    }

    public String cancelButtonMethod() {
        this.mode = "V";
        ADFBeanUtils.callBindingsMethod("Rollback", null, null);
        return null;
    }

    public String deleteButtonMethod() {
        ADFBeanUtils.callBindingsMethod("Delete", null, null);
        ADFBeanUtils.callBindingsMethod("Commit", null, null);
        return null;
    }

    public String saveButtonMethod() {
        Object bindingsMethod = ADFBeanUtils.callBindingsMethod("saveValidations", null, null);


        if (bindingsMethod != null) {
            if (bindingsMethod.toString().equalsIgnoreCase("Y")) {
                ADFBeanUtils.callBindingsMethod("onFinalize", null, null);
                ADFBeanUtils.callBindingsMethod("Commit", null, null); /*  */
                AdfFacesContext.getCurrentInstance().addPartialTarget(prflTblBinding);
                this.mode = "V";

                String message = resolvElDCMsg("#{bundle['MSG.818']}").toString();
                FacesMessage Message = new FacesMessage(message);
                Message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, Message);
            } else {
                ADFModelUtils.showFacesMessage("Please Enter all the required details!",
                                               "Please Enter all the required details!", FacesMessage.SEVERITY_ERROR,
                                               null);
            }
        }

        return null;
    }

    public void setApplicableLaw(RichSelectOneChoice applicableLaw) {
        this.applicableLaw = applicableLaw;
    }

    public RichSelectOneChoice getApplicableLaw() {


        return applicableLaw;
    }

    public String editButtonMethod() {
        this.mode = "D";
        return null;
    }

    public void setRound_validater(RichInputText round_validater) {
        this.round_validater = round_validater;
    }

    public RichInputText getRound_validater() {
        return round_validater;
    }

    public void Round_Validater(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Integer value = (Integer) object;
        if (object != null) {
            if (value < 2 || value > 6)

            {
                System.out.println(value);

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "value should be b/w 2 to 6",
                                                              null));


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


    public void ValidatorEffectiveDate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("object=" + object);
        //String  dts=(String)object;
        Date dt = new Date();
        String s = dt.getCurrentDate().dateValue().toString();
        System.out.println("current date is=" + dt.getCurrentDate().dateValue());
        if (object.toString().compareTo(s) < 0) {
            System.out.println("Effective Date should not less than from system date ");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Effective Date should not be less than current date", null));
        }

    }

    public void setEffectiveDate(RichInputDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public RichInputDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setCompanyLawBinding(RichPanelBox companyLawBinding) {
        this.companyLawBinding = companyLawBinding;
    }

    public RichPanelBox getCompanyLawBinding() {
        return companyLawBinding;
    }

    public void setItLawBinding(RichPanelBox itLawBinding) {
        this.itLawBinding = itLawBinding;
    }

    public RichPanelBox getItLawBinding() {
        return itLawBinding;
    }

    public void lawVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(companyLawBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itLawBinding);

    }

    public void DeprecitionITCVl(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(applicableLaw);

    }

    public void DeprecitionCoCVl(ValueChangeEvent valueChangeEvent) {
        coNegDepBind.setValue(null);
        coNegDepBind.setValue("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(coNegDepBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(applicableLaw);
    }

    public String deleteAction() {

        ADFBeanUtils.showPopup(deletePopupBinding, true);
        return null;
    }

    public void setPrflTblBinding(RichTable prflTblBinding) {
        this.prflTblBinding = prflTblBinding;
    }

    public RichTable getPrflTblBinding() {
        return prflTblBinding;
    }

    public void deleteDL(DialogEvent dialogEvent) {
        System.out.println("dialogEvent.getOutcome().name().toString().equalsIgnoreCase(\"ok\") =" +
                           dialogEvent.getOutcome().name().toString().equalsIgnoreCase("ok"));
        System.out.println("dialogEvent.getOutcome().name() = " + dialogEvent.getOutcome().name());
        if (dialogEvent.getOutcome().name().toString().equalsIgnoreCase("yes")) {

            Object res = ADFBeanUtils.callBindingsMethod("deleteAction", null, null);
            if (res != null) {
                if (res.toString().equalsIgnoreCase("Y")) {
                    AdfFacesContext.getCurrentInstance().addPartialTarget(prflTblBinding);
                    ADFModelUtils.showFacesMessage("Record Deleted Successfuly", "Record Deleted Successfuly!",
                                                   FacesMessage.SEVERITY_INFO, null);

                }
            }
        }
    }

    public void setDeletePopupBinding(RichPopup deletePopupBinding) {
        this.deletePopupBinding = deletePopupBinding;
    }

    public RichPopup getDeletePopupBinding() {
        return deletePopupBinding;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String searchACtion() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("filterCntryTable");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(prflTblBinding);

            return null;
        }
        return null;
    }

    public String reset() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("resetCntryTable");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(prflTblBinding);

            return null;
        }
        return null;
    }

    public void setCoNegDepBind(RichSelectBooleanCheckbox coNegDepBind) {
        this.coNegDepBind = coNegDepBind;
    }

    public RichSelectBooleanCheckbox getCoNegDepBind() {
        return coNegDepBind;
    }
}
