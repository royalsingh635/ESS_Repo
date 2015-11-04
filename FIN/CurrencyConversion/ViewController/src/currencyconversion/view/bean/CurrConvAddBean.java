package currencyconversion.view.bean;


import currencyconversion.model.module.CurrencyConversionAMImpl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Date;

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
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.domain.Number;


public class CurrConvAddBean {
    private RichInputText buyBase;
    private RichInputText sellBase;
    private RichInputText buySpecific;
    private RichInputText sellSpecific;
    private RichInputDate datefrom;
    private RichInputDate dateto;
    private Integer count = -1;
    public byte countCurr = 0;
    private int CurrId = -1;
    private int CurrIdTxn = -2;
    private Date currDate = new Date();
    private static final int VARCHAR = Types.VARCHAR;

    public CurrConvAddBean() {
        count = (Integer) getBindings().getOperationBinding("on_load_page").execute();
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

    public void appbuyingValueChange(ValueChangeEvent valueChangeEvent) {

        Integer decimalDigit = 4;
        if (resolvEl("#{pageFlowScope.GLBL_CURR_DIGIT}") != null)
            decimalDigit = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_CURR_DIGIT}").toString());

        Number n = (Number) valueChangeEvent.getNewValue();
        Number m = new Number(1);
        Number o = new Number(0);

        if (n.equals(o)) {
            buySpecific.setValue(o);
        } else {
            buySpecific.setValue(m.divide(n).round(decimalDigit));
        }
        Number sb = ((Number) this.sellBase.getValue());
        if ((sb.compareTo(0) > 0) && (sb.compareTo(n) > 0)) {
            FacesMessage message =
                new FacesMessage("Buying base currency should not be less than selling base currency ");
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

    public void setBuyBase(RichInputText buyBase) {
        this.buyBase = buyBase;
    }

    public RichInputText getBuyBase() {
        return buyBase;
    }

    public void setSellBase(RichInputText sellBase) {
        this.sellBase = sellBase;
    }

    public RichInputText getSellBase() {
        return sellBase;
    }

    public void setBuySpecific(RichInputText buySpecific) {
        this.buySpecific = buySpecific;
    }

    public RichInputText getBuySpecific() {
        return buySpecific;
    }

    public void setSellSpecific(RichInputText sellSpecific) {
        this.sellSpecific = sellSpecific;
    }

    public RichInputText getSellSpecific() {

        return sellSpecific;
    }


    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    public void baseSellValueChange(ValueChangeEvent valueChangeEvent) {
        Integer decimalDigit = 4;
        if (resolvEl("#{pageFlowScope.GLBL_CURR_DIGIT}") != null)
            decimalDigit = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_CURR_DIGIT}").toString());

        Number n = (Number) valueChangeEvent.getNewValue();
        Number m = new Number(1);
        Number o = new Number(0);

        if (n.equals(o)) {
            sellSpecific.setValue(o);
        } else {
            sellSpecific.setValue(m.divide(n).round(decimalDigit));
        }
        if ((((((Number) this.buyBase.getValue()).compareTo(o)) > 0) &&
             ((((Number) this.buyBase.getValue()).compareTo(n)) < 0))) {
            FacesMessage message =
                new FacesMessage("Buying base currency should not be less than Selling base currency");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }

        /*  if ((((((Number) this.buyBase.getValue()).compareTo(o)) > 0) &&
             ((((Number) this.buyBase.getValue()).compareTo(n)) > 0))) {
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
            buyBase.setValue(o);
        } else {
            buyBase.setValue(m.divide(n).round(decimalDigit));
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
            sellBase.setValue(o);
        } else {
            sellBase.setValue(m.divide(n).round(decimalDigit));
        }

    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String OkAction() {

        if (datefrom.getValue() == null) {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.539']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return null;
        }

        else {

            Number a = new Number(0);
            Number x = (Number) buyBase.getValue();
            Number y = (Number) sellBase.getValue();
            Number b = (Number) x.minus(a);
            Number c = (Number) y.minus(a);
            if (b.sign() == 1 && c.sign() == 1) {

                String retVal = null;
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();
                if (operationBinding.getErrors().isEmpty()) {
                    retVal = "BackWithCommit";
                } else {
                    retVal = null;
                }
                this.CurrId = -1;
                this.CurrIdTxn = -2;

                return retVal;
            } else {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.540']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);

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

    public void buyBaseValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number baseBuy = new Number(0);
        if (object != null)
            baseBuy = (Number) object;

        if (baseBuy.compareTo(0) == -1) {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.541']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

    public void sellBaseValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number baseBuy = new Number(0);
        if (object != null)
            baseBuy = (Number) object;

        if (baseBuy.compareTo(0) == -1) {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.541']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
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


    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void listenCurrChange(ValueChangeEvent valueChangeEvent) throws SQLException {
        }



    public void listenTxn(ValueChangeEvent valueChangeEvent) throws SQLException {
        this.CurrIdTxn = (Integer) valueChangeEvent.getNewValue();
        System.out.println(this.CurrIdTxn);
        System.out.println(this.CurrId);

        if (this.CurrId == this.CurrIdTxn) {
            (this.buyBase).setValue(new Number(1.00));
            (this.sellBase).setValue(new Number(1.00));

            this.buySpecific.setValue(new Number(1.00));
            this.sellSpecific.setValue(new Number(1.00));
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.buySpecific);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.sellSpecific);

            // System.out.println("In txn change");

            AdfFacesContext.getCurrentInstance().addPartialTarget(this.buyBase);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.buyBase);
        } else {
            (this.buyBase).setValue(new Number(0.00));
            (this.sellBase).setValue(new Number(0.00));


            this.buySpecific.setValue(new Number(0.00));
            this.sellSpecific.setValue(new Number(0.00));
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.buySpecific);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.sellSpecific);

            AdfFacesContext.getCurrentInstance().addPartialTarget(this.buyBase);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.buyBase);
        }

    }

    public void backListen(ActionEvent actionEvent) {
        System.out.println("in back");
        this.CurrId = -1;
        this.CurrIdTxn = -2;
    }

    public void setCurrId(int CurrId) {
        this.CurrId = CurrId;
    }

    public int getCurrId() {
        return CurrId;
    }

    public void setCurrIdTxn(int CurrIdTxn) {
        this.CurrIdTxn = CurrIdTxn;
    }

    public int getCurrIdTxn() {
        return CurrIdTxn;
    }


    public void setCurrDate(Date currDate) {
        this.currDate = currDate;
    }

    public Date getCurrDate() {
        return currDate;
    }


    public void listenChange(ValueChangeEvent valueChangeEvent) {
        //        oracle.jbo.domain.Date  d=(oracle.jbo.domain.Date ) valueChangeEvent.getNewValue();
        //
        //
        //        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        //        String org_id =  resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        //        String dtFlg =(String)callStoredFunction2(VARCHAR, "APP.PKG_APP.GET_FY_STAT_FOR_TXN(?,?,?)", new Object[] { sloc_id,
        //                                                                                                          org_id,
        //                                                                                                          d });
        //
        //
        //        if (dtFlg.equalsIgnoreCase("Y")) {
        //
        //            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.521']}").toString());
        //            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        //
        //            FacesContext fc = FacesContext.getCurrentInstance();
        //            fc.addMessage(null, message);
        //        }datefrom

    }

    protected Object callStoredFunction2(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        CurrencyConversionAMImpl am = (CurrencyConversionAMImpl) resolvElDC("CurrencyConversionAMDataControl");
        try {
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();
            System.out.println(st.getObject(1));
            return st.getObject(1);

        } catch (SQLException e) {
            e.printStackTrace();
            int end = e.getMessage().indexOf("\n");

            String message = e.getMessage().substring(11, end);

            FacesMessage msg = new FacesMessage(message);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    throw new JboException(e);
                }
            }
        }
    }

    public void ccEffDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("insert validator-----");
        if (object != null) {
            System.out.println("date valueee is====" + object);
            String ccDt = null;
            ccDt = object.toString();
            System.out.println("currncey cov date is====" + ccDt);
            oracle.jbo.domain.Date dt = new oracle.jbo.domain.Date();
            String sysdt = dt.getCurrentDate().dateValue().toString();
            System.out.println("current system date is=" + sysdt);
            if (ccDt.compareTo(sysdt) == 1) {
                FacesMessage errMsg = new FacesMessage("Invalid Date !");
                errMsg.setDetail("Date should be less than or equal to current date");
                throw new ValidatorException(errMsg);

            }

        }
    }
}
