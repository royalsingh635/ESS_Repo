package currencyconversion.view.edit;


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
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;


public class CurrencyConversionEditViewBean {


    private RichInputText baseBuying;
    private RichInputText baseSelling;
    private RichInputText specificBuying;
    private RichInputText specificSelling;

    private RichInputDate datefrom;
    private RichInputDate dateto;
    private Integer count = -1;
    private RichSelectOneChoice currTxn;
    private RichSelectOneChoice currId;
    private Number base1;

    public CurrencyConversionEditViewBean() {
        count = (Integer) getBindings().getOperationBinding("on_load_page").execute();
        try {
            base1 = new Number(1.00);
        } catch (Exception e) {
            System.out.println("Error in creating number object");
        }
    }


    public void setBaseBuying(RichInputText baseBuying) {
        this.baseBuying = baseBuying;
    }

    public RichInputText getBaseBuying() {
        return baseBuying;
    }

    public void setBaseSelling(RichInputText baseSelling) {
        this.baseSelling = baseSelling;
    }

    public RichInputText getBaseSelling() {
        return baseSelling;
    }

    public void setSpecificBuying(RichInputText specificBuying) {
        this.specificBuying = specificBuying;
    }

    public RichInputText getSpecificBuying() {
        return specificBuying;
    }

    public void setSpecificSelling(RichInputText specificSelling) {
        this.specificSelling = specificSelling;
    }

    public RichInputText getSpecificSelling() {
        return specificSelling;
    }

    public Object resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext);
        return Message;
    }

    public void baseBuyValueChange(ValueChangeEvent valueChangeEvent) {
        Integer decimalDigit = 4;
        if (resolvEl("#{pageFlowScope.GLBL_CURR_DIGIT}") != null)
            decimalDigit = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_CURR_DIGIT}").toString());

        Number n = (Number) valueChangeEvent.getNewValue();
        Number m = new Number(1);
        Number o = new Number(0);

        if (n.equals(o)) {
            specificBuying.setValue(o);
        } else {
            specificBuying.setValue(m.divide(n).round(decimalDigit));
        }

        Number sb = ((Number) this.baseSelling.getValue());
        if ((sb.compareTo(0) > 0) && (sb.compareTo(n) > 0)) {
            FacesMessage message =
                new FacesMessage("Buying base currency should not be less than selling base currency");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        /*  if ((sb.compareTo(0) > 0) && (sb.compareTo(n) < 0)) {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['LBL.3151']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } */


    }

    public void baseSellValueChange(ValueChangeEvent valueChangeEvent) {
        Integer decimalDigit = 4;
        if (resolvEl("#{pageFlowScope.GLBL_CURR_DIGIT}") != null)
            decimalDigit = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_CURR_DIGIT}").toString());


        Number n = (Number) valueChangeEvent.getNewValue();
        Number m = new Number(1);
        Number o = new Number(0);
        if (n.equals(o)) {
            specificSelling.setValue(o);
        } else {
            specificSelling.setValue(m.divide(n).round(decimalDigit));
        }
        if ((((((Number) this.baseBuying.getValue()).compareTo(o)) > 0) &&
             ((((Number) this.baseBuying.getValue()).compareTo(n)) < 0))) {
            FacesMessage message =
                new FacesMessage("Buying base currency should not be less than selling base currency");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        /*  if ((((((Number) this.baseBuying.getValue()).compareTo(o)) > 0) &&
             ((((Number) this.baseBuying.getValue()).compareTo(n)) > 0))) {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['LBL.3151']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } */


    }

    public void specificBuy(ValueChangeEvent valueChangeEvent) {
        Integer decimalDigit = 4;
        if (resolvEl("#{pageFlowScope.GLBL_CURR_DIGIT}") != null)
            decimalDigit = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_CURR_DIGIT}").toString());

        Number n = (Number) valueChangeEvent.getNewValue();
        Number m = new Number(1);
        Number o = new Number(0);
        if (n.equals(o)) {
            baseBuying.setValue(o);
        } else {
            baseBuying.setValue(m.divide(n).round(decimalDigit));
        }

    }

    public void specificSell(ValueChangeEvent valueChangeEvent) {
        Integer decimalDigit = 4;
        if (resolvEl("#{pageFlowScope.GLBL_CURR_DIGIT}") != null)
            decimalDigit = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_CURR_DIGIT}").toString());

        Number n = (Number) valueChangeEvent.getNewValue();
        Number m = new Number(1);
        Number o = new Number(0);
        if (n.equals(o)) {
            baseSelling.setValue(o);
        } else {
            baseSelling.setValue(m.divide(n).round(decimalDigit));
        }


    }


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String OkAction() {

        if (datefrom.getValue() == null) {
            FacesMessage message = new FacesMessage("Date From is required.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return null;
        }

        else {


            Number a = new Number(0);
            Number x = (Number) baseBuying.getValue();
            Number y = (Number) baseSelling.getValue();
            Number b = (Number) x.minus(a);
            Number c = (Number) y.minus(a);


            if (b.sign() == 1 && c.sign() == 1) {
                String retVal = null;
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();
                if (operationBinding.getErrors().isEmpty()) {
                    retVal = "commitWithback";
                } else {
                    retVal = null;
                }
                return retVal;
            } else {
                FacesMessage message = new FacesMessage("Buying rate and selling rate should be greater than 0.");
                //  FacesMessage message = new FacesMessage("Buying rate and selling rate should be greater than 0.");
                message.setSeverity(FacesMessage.SEVERITY_INFO);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                return null;
            }


        }
    }


    public void setDatefrom(RichInputDate datefrom) {
        this.datefrom = datefrom;
    }

    public RichInputDate getDatefrom() {
        return datefrom;
    }

    public void setDateto(RichInputDate dateto) {
        this.dateto = dateto;
    }

    public RichInputDate getDateto() {
        return dateto;
    }


    public void baseBuyingValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        Number baseBuy = new Number(0);
        if (object != null)
            baseBuy = (Number) object;

        if (baseBuy.compareTo(0) == -1) {
            FacesMessage message = new FacesMessage("Non Negative value is required.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

    public void baseSellingValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number baseBuy = new Number(0);
        if (object != null)
            baseBuy = (Number) object;

        if (baseBuy.compareTo(0) == -1) {
            FacesMessage message = new FacesMessage("Non Negative value is required.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }


    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCurrTxn(RichSelectOneChoice currTxn) {
        this.currTxn = currTxn;
    }

    public RichSelectOneChoice getCurrTxn() {
        return currTxn;
    }

    public void setCurrId(RichSelectOneChoice currId) {
        this.currId = currId;
    }

    public RichSelectOneChoice getCurrId() {
        return currId;
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void setBase1(Number base1) {
        this.base1 = base1;
    }

    public Number getBase1() {
        return base1;
    }
}
