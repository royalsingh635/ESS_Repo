package stockprofilesetup.view.Beans;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class StockProfile {
    private RichInputText monthBind;
    private RichPopup showpop;
    private RichSelectBooleanCheckbox prflDefault;
    private RichInputText outputfield;
    // private RichSelectBooleanCheckbox prflDefault1;
    private RichInputListOfValues prfTransBind;
    private RichTable stkTblBinding;


    public StockProfile() {
    }
    String mode = "N";
    String temp = "D";

    public void SaveBtn(ActionEvent actionEvent) {

        String flag = "N";
        OperationBinding bindDflt = getBindings().getOperationBinding("prfdflt");
        bindDflt.execute();
        if (bindDflt.getErrors().isEmpty()) {
            flag = bindDflt.getResult().toString();
        }
        if (flag.equalsIgnoreCase("Y")) {
            mode = "N";
            System.out.println("Flag " + flag);
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            if (operationBinding.getErrors().isEmpty()) {
                FacesMessage message = new FacesMessage("Record Save Successfully!");
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        } else if (flag.equalsIgnoreCase("N")) {
            System.out.println("else Flag " + flag);
            FacesMessage message = new FacesMessage("No Default Profile Selected");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }

    }


    public void EditBtn(ActionEvent actionEvent) {
        String flag = null;

        OperationBinding operationBinding = getBindings().getOperationBinding("isStkDeletable");
        operationBinding.execute();
        if (operationBinding.getErrors().isEmpty()) {
            flag = operationBinding.getResult().toString();
            if ("Y".equalsIgnoreCase(flag)) {
                mode = "Y";
            } else if ("N".equalsIgnoreCase(flag)) {
                //String msg="Profile Can't Editable because it is Already in used";

                FacesMessage message = new FacesMessage("Profile Can't Editable because it is Already in used");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        }
    }


    public void CreateBtn(ActionEvent actionEvent) {
        mode = "Y";
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
    }

    public void CancelBtn(ActionEvent actionEvent) {

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();

        mode = "N";
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void ValueChangeListen(ValueChangeEvent valueChangeEvent) {
        if (!valueChangeEvent.getNewValue().toString().equalsIgnoreCase(null)) {
            Integer value = Integer.parseInt(valueChangeEvent.getNewValue().toString());

            String s = null;
            switch (value) {

            case 357:
                monthBind.setValue((float) 12);
                s = "12 Months";
                outputfield.setValue(s);
                break;

            case 358:
                monthBind.setValue((float) 6);
                s = "6 Months";
                outputfield.setValue(s);
                break;
            case 359:
                monthBind.setValue((float) 3);
                s = "3 Months";
                outputfield.setValue(s);
                break;
            case 360:
                monthBind.setValue((float) 2);
                s = "2 Months";
                outputfield.setValue(s);
                break;
            case 361:
                monthBind.setValue((float) 1);
                s = "1 Month";
                outputfield.setValue(s);
                break;
            case 362:
                monthBind.setValue((float) 0.5);
                s = "15 Days";
                outputfield.setValue(s);
                break;
            case 363:
                monthBind.setValue((float) 0.0);
                s = "0 Days";
                outputfield.setValue(s);
                break;

            }

        }
    }

    public void setMonthBind(RichInputText monthBind) {
        this.monthBind = monthBind;
    }

    public RichInputText getMonthBind() {
        return monthBind;
    }

    public void ProfileDFL(ValueChangeEvent valueChangeEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("prfdflt");
        operationBinding.execute();

    }

    public void DeleteBtn(ActionEvent actionEvent) {
        String flag = null;
        AdfFacesContext.getCurrentInstance().addPartialTarget(prflDefault);
        if (getPrflDefault().getValue() != null && getPrflDefault().getValue() != "") {
            System.out.println("getPrflDefault().getValue() : " + getPrflDefault().getValue());
            if (getPrflDefault().getValue().toString().equalsIgnoreCase("false")) {

                OperationBinding operationBinding = getBindings().getOperationBinding("isStkDeletable");
                operationBinding.execute();
                if (operationBinding.getErrors().isEmpty()) {
                    flag = operationBinding.getResult().toString();
                }
                if ("Y".equalsIgnoreCase(flag)) {
                    showPopup(showpop, true);
                } else if ("N".equalsIgnoreCase(flag)) {
                    FacesMessage message = new FacesMessage("Profile Can't Deletable because it is Already in used");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            } else if (getPrflDefault().getValue().toString().equalsIgnoreCase("true")) {
                FacesMessage message = new FacesMessage("Default profile can't Deletable");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        }

    }

    public void popDialog(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("yes")) {
            getBindings().getOperationBinding("Delete").execute();
            OperationBinding binding = getBindings().getOperationBinding("Commit");
            binding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(stkTblBinding);

            if (binding.getErrors().isEmpty()) {
                String message1 = resolvElDCMsg("Record is deleted Successfully").toString();
                FacesMessage message = new FacesMessage(message1);
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                String message1 = resolvElDCMsg("Enabled To Delete").toString();
                FacesMessage message = new FacesMessage(message1);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        }
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("no")) {

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

    public void setShowpop(RichPopup showpop) {
        this.showpop = showpop;
    }

    public RichPopup getShowpop() {
        return showpop;
    }

    private void showPopup(RichPopup pop, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null) {
                String popupId = pop.getClientId(context);
                if (popupId != null) {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible) {
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                    } else {
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void NameValidation_Normal(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String expression = "([a-zA-Z0-9]+)(([ ])([a-zA-Z0-9]+))*";
        CharSequence inputStr = object.toString();
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        String error = resolvElDCMsg("Special Character Not Allowed").toString();
        if (matcher.matches()) {
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
        }
    }

    public void setPrflDefault(RichSelectBooleanCheckbox prflDefault) {
        this.prflDefault = prflDefault;
    }

    public RichSelectBooleanCheckbox getPrflDefault() {
        return prflDefault;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTemp() {
        return temp;
    }

    public void setOutputfield(RichInputText outputfield) {
        this.outputfield = outputfield;
    }

    public RichInputText getOutputfield() {
        return outputfield;
    }
    /*  public String getCreateEnabled() {
        BindingContainer bc = this.getBindings();
        oracle.adf.model.OperationBinding ob = (oracle.adf.model.OperationBinding)bc.getOperationBinding("getRowStatus");
        return (String)ob.execute();
    }

    public String getCellColor() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExpressionFactory ef = ctx.getApplication().getExpressionFactory();
        ValueExpression ve = ef.createValueExpression(ctx.getELContext(), "#{row}", FacesCtrlHierNodeBinding.class);

        FacesCtrlHierNodeBinding node = (FacesCtrlHierNodeBinding)ve.getValue(ctx.getELContext());
        Row row = node.getRow();

        BindingContainer bc = this.getBindings();
        oracle.adf.model.OperationBinding ob = (oracle.adf.model.OperationBinding)bc.getOperationBinding("getRowStatusColor");
        ob.getParamsMap().put("row", row);
        String status = (String)ob.execute();

        if (status.equals("Modified")) {
            return "background-color:Red;";
        }

        return null;
    }

    private static class FacesCtrlHierNodeBinding {
    } */


    public void searchAction(ActionEvent actionEvent) {
        // Add event code here...
        if (getPrfTransBind().getValue() != null && getPrfTransBind().getValue() != null) {
            OperationBinding operationBinding = getBindings().getOperationBinding("searchCriteria");
            operationBinding.getParamsMap().put("prfName", getPrfTransBind().getValue().toString());
            operationBinding.execute();
        }
    }

    public void resetAction(ActionEvent actionEvent) {
        // Add event code here...

        OperationBinding operationBinding = getBindings().getOperationBinding("resetAction");
        operationBinding.execute();
        prfTransBind.setValue(null);
    }

    public void setPrfTransBind(RichInputListOfValues prfTransBind) {
        this.prfTransBind = prfTransBind;
    }

    public RichInputListOfValues getPrfTransBind() {
        return prfTransBind;
    }

    public void setStkTblBinding(RichTable stkTblBinding) {
        this.stkTblBinding = stkTblBinding;
    }

    public RichTable getStkTblBinding() {
        return stkTblBinding;
    }

    public void AddReamarkAL(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding binding = getBindings().getOperationBinding("CreateInsert1");
        binding.execute();
        mode = "AR";
    }

    public void deleteRemarkAL() {
        // Add event code here...
        OperationBinding binding = getBindings().getOperationBinding("Delete1");
        binding.execute();
        mode = "DR";
        OperationBinding binding1 = getBindings().getOperationBinding("Commit1");
        binding1.execute();

    }

    public void editRemarkAL(ActionEvent actionEvent) {
        // Add event code here...
        mode = "ER";

    }

    public void saveRemarkAL(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding binding = getBindings().getOperationBinding("Commit1");
        binding.execute();
        String message1 = resolvElDCMsg("Remarks saved Successfully").toString();
        FacesMessage message = new FacesMessage(message1);
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext fc = FacesContext.getCurrentInstance();

        mode = "SR";
    }
}
