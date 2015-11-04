package mmgatepass.view.bean;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;
import oracle.jbo.rules.JboPrecisionScaleValidator;

public class RcptAllBean {
    private RichInputText balQtyBind;
    private RichInputListOfValues eoBinding;
    private RichSelectOneChoice whBinding;
    private RichLink svebuttonBind;

    public void setTotBalQty(Number totBalQty) {
        this.totBalQty = totBalQty;
    }

    public Number getTotBalQty() {
        return totBalQty;
    }
    private Number totBalQty = new Number(0);

    public RcptAllBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void saveRcptAllAction(ActionEvent actionEvent) {
        OperationBinding obind = getBindings().getOperationBinding("insertAllRcptGatePass");
        obind.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(eoBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(whBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(svebuttonBind);
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void searchRcptAllGatePass(ActionEvent actionEvent) {
        if (eoBinding.getValue() != null && eoBinding.getValue().toString().length() > 0) {

        } else {
            //  String msg="Entity is required.";
            String msg = resolvElDCMsg("#{bundle['MSG.2846']}").toString();
            FacesMessage Message = new FacesMessage(msg);
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(eoBinding.getClientId(), Message);
            return;
        }

        if (whBinding.getValue() != null && whBinding.getValue().toString().length() > 0) {

        } else {
            //  String msg="Warehouse Name is required";
            String msg = resolvElDCMsg("#{bundle['MSG.2847']}").toString();
            FacesMessage Message = new FacesMessage(msg);
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(whBinding.getClientId(), Message);
            return;
        }


        OperationBinding obind = getBindings().getOperationBinding("srchRcptGp");
        obind.execute();

        OperationBinding totbal = getBindings().getOperationBinding("totBalQtyRcptAll");
        totbal.execute();
    }

    public void setBalQtyBind(RichInputText balQtyBind) {
        this.balQtyBind = balQtyBind;
    }

    public RichInputText getBalQtyBind() {
        return balQtyBind;
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

    public void rcptQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            Number obj = (Number) object;
            Number zero = new Number(0);

            if (obj.compareTo(zero) == 0 || obj.compareTo(zero) == 1) {

            } else {
                //  String msg="Quantity must be greater than zero.";
                String msg = resolvElDCMsg("#{bundle['MSG.730']}").toString();
                showFacesMessage(msg, "E", false, "V");
            }
            if (!isPrecisionValid(26, 6, obj)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.57']}"), null));
            }


            if (balQtyBind.getValue() != null && balQtyBind.getValue().toString().length() > 0) {
                Number blncQty = (Number) balQtyBind.getValue();
                if (obj.compareTo(blncQty) == 1) {
                    //  String msg="Quantity must be less than balance quantity.";
                    String msg = resolvElDCMsg("#{bundle['MSG.2848']}").toString();
                    showFacesMessage(msg, "E", false, "V");

                }

            }
        }
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void setEoBinding(RichInputListOfValues eoBinding) {
        this.eoBinding = eoBinding;
    }

    public RichInputListOfValues getEoBinding() {
        return eoBinding;
    }

    public void setWhBinding(RichSelectOneChoice whBinding) {
        this.whBinding = whBinding;
    }

    public RichSelectOneChoice getWhBinding() {
        return whBinding;
    }

    public void setSvebuttonBind(RichLink svebuttonBind) {
        this.svebuttonBind = svebuttonBind;
    }

    public RichLink getSvebuttonBind() {
        return svebuttonBind;
    }
}
