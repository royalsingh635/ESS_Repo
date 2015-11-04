package tempVoucherList.view.bean;

import adf.utils.bean.ADFBeanUtils;

import java.util.ArrayList;

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
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;


public class TvouAutoAdjustBean {

    private RichInputText tvouAdjAmtAdjThsBind;
    private RichInputText sumTrxAmtBind;

    private RichInputText advanceAssignedBinding;
    private RichInputText transactionAmountBinding;
    private Number remBal;
    private RichInputText remainingBalanceBinding;
    private Number zero = new Number(0);
    private RichOutputText tvouAdjAmtOTBind;
    private String disableAdjustment = "Y";
    private String searchValue;
    private Key key;
    private RichTable invTable;
    private Number bnkInstrmntAmt;
    private RichInputDate toDateBinding;
    private RichInputDate frmDateBinding;
    private RichCommandButton refreshButtonBinding;
    private RichInputText tvouCcCurrBinding;
    private RichInputText tvouCurrCctrxBinding;
    private RichInputText remTrancationAmtBinding;
    private RichSelectOneChoice tvouCurrIdTrxBinding;
    private RichSelectOneChoice tvouAdjCurrIdSpBinding;
    //private RichCommandButton backButtonBinding;

    public TvouAutoAdjustBean() {
    }

    public void InvoiceTrxAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // check for negative and null values

        if (object == null) {
            String msg = (String) resolvEl("#{bundle['MSG.1976']}");
            String msg1 = (String) resolvEl("#{bundle['MSG.1977']}");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg1));
        }
        Number trxAmt = (Number) object;

        if (trxAmt.compareTo(0) == -1) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.489']}").toString(),
                                                          resolvElDCMsg("#{bundle['MSG.490']}").toString()));
        }
        /** CHECK WHETHER CURRENCY IS SAME OR NOT IF SAME THEN ONLY AMOUNT WITHOUT CONVERSION WILL BE VALIDATE*/
        Number outstandingAmt = (Number) tvouAdjAmtOTBind.getValue();
        Number advance = (Number) advanceAssignedBinding.getValue();

        Number add = outstandingAmt.subtract(advance);
        Number addTrx = new Number(0);
        System.out.println("tvouCurrIdTrxBinding.getValue() = " + tvouCurrIdTrxBinding.getValue());
        System.out.println("tvouAdjCurrIdSpBinding.getValue() = " + tvouAdjCurrIdSpBinding.getValue());
        System.out.println("tvouCurrIdTrxBinding.getValue().equals(tvouAdjCurrIdSpBinding.getValue()) = " +
                           (tvouCurrIdTrxBinding.getValue().equals(tvouAdjCurrIdSpBinding.getValue())));
        ViewObject line = ADFUtil.getAM().findViewObject("TvouAdjAutoVO");
        Row currentRow = line.getCurrentRow();
        Object Adjsp = currentRow.getAttribute("TvouAdjCurrIdSp");
        System.out.println("Adjsp = " + Adjsp);
        System.out.println("!tvouCurrIdTrxBinding.getValue().equals(Adjsp) = " +
                           (!tvouCurrIdTrxBinding.getValue().equals(Adjsp)));

        if (tvouCurrIdTrxBinding.getValue().equals(Adjsp)) {
            addTrx = add;

        } else {
            addTrx =
                roundOffAmt((add.multiply((Number) tvouCcCurrBinding.getValue())).divide((Number) tvouCurrCctrxBinding.getValue()));
        }
        if (trxAmt.compareTo(addTrx) == 1) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.489']}").toString() + " " +
                                                          addTrx,
                                                          resolvElDCMsg("#{bundle['MSG.429']}").toString() + " " +
                                                          addTrx));

        }

    }

    public void InvoiceTrxAmtVCE(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());

        if (valueChangeEvent.getNewValue() != null) {
            /** tHIS AMOUNT WILL BE UPDATE ACCORDING TO THE CURRENCY*/

            ViewObject line = ADFUtil.getAM().findViewObject("TvouAdjAutoVO");
            Row currentRow = line.getCurrentRow();
            Object Adjsp = currentRow.getAttribute("TvouAdjCurrIdSp");
            System.out.println("Adjsp = " + Adjsp);
            System.out.println("!tvouCurrIdTrxBinding.getValue().equals(Adjsp) = " +
                               (!tvouCurrIdTrxBinding.getValue().equals(Adjsp)));

            if (tvouCurrIdTrxBinding.getValue().equals(Adjsp)) {
                this.tvouAdjAmtAdjThsBind.setValue((Number) valueChangeEvent.getNewValue());
            } else
                this.tvouAdjAmtAdjThsBind.setValue(roundOffAmt(((Number) valueChangeEvent.getNewValue()).divide((Number) tvouCcCurrBinding.getValue()).multiply((Number) tvouCurrCctrxBinding.getValue())));


            AdfFacesContext.getCurrentInstance().addPartialTarget(sumTrxAmtBind);

            AdfFacesContext.getCurrentInstance().addPartialTarget(tvouAdjAmtAdjThsBind);

            AdfFacesContext.getCurrentInstance().addPartialTarget(remainingBalanceBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(remTrancationAmtBinding);

        }
    }

    public void setTvouAdjAmtAdjThsBind(RichInputText tvouAdjAmtAdjThsBind) {
        this.tvouAdjAmtAdjThsBind = tvouAdjAmtAdjThsBind;
    }

    public RichInputText getTvouAdjAmtAdjThsBind() {
        return tvouAdjAmtAdjThsBind;
    }

    /**Method to resolve String from XML file @06-07-2013*/

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void setSumTrxAmtBind(RichInputText sumTrxAmtBind) {
        this.sumTrxAmtBind = sumTrxAmtBind;
    }

    public RichInputText getSumTrxAmtBind() {
        return sumTrxAmtBind;
    }

    public void setAdvanceAssignedBinding(RichInputText advanceAssignedBinding) {
        this.advanceAssignedBinding = advanceAssignedBinding;
    }

    public RichInputText getAdvanceAssignedBinding() {
        return advanceAssignedBinding;
    }

    /**
     * Update the trx and this field on VCE of the invoice row check box
     * **/
    public void checkBoxAdjustVCE(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        // System.out.println("valueChangeEvent." + valueChangeEvent.getNewValue());

        if (valueChangeEvent != null && (Boolean) valueChangeEvent.getNewValue() == false) {
            System.out.println("inside if");
            this.tvouAdjAmtAdjThsBind.setValue(new Number(0));
            this.transactionAmountBinding.setValue(new Number(0));
        }
        //set the disable flag adjustment button on value change event of check box
        setDisableAdjustment("Y");
        refreshButtonBinding.setDisabled(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(refreshButtonBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(transactionAmountBinding);
    }

    public void checkBoxAdvVCE(ValueChangeEvent valueChangeEvent) {
        //set the disable flag adjustment button on value change event of check box
        setDisableAdjustment("Y");
        refreshButtonBinding.setDisabled(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(refreshButtonBinding);
        /*  backButtonBinding.setDisabled(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(backButtonBinding); */

    }

    public void setTransactionAmountBinding(RichInputText transactionAmountBinding) {
        this.transactionAmountBinding = transactionAmountBinding;
    }

    public RichInputText getTransactionAmountBinding() {
        return transactionAmountBinding;
    }

    public void setRemBal(Number remBal) {
        this.remBal = remBal;
    }

    public Number getRemBal() {
        return remBal;
    }

    public void setRemainingBalanceBinding(RichInputText remainingBalanceBinding) {
        this.remainingBalanceBinding = remainingBalanceBinding;
    }

    public RichInputText getRemainingBalanceBinding() {
        return remainingBalanceBinding;
    }

    public void setZero(Number zero) {
        this.zero = zero;
    }

    public Number getZero() {
        return zero;
    }

    public Number getBalanceAmt() {


        Number invoiceAmt = new Number();
        Number advAmt = new Number();

        // check null values in expression
        if (resolveElExp("#{bindings.SumAdjustableAmtTrans.inputValue}") != null &&
            resolveElExp("#{bindings.SumAdjustableAdvAmtTrans.inputValue}") != null) {

            invoiceAmt = (Number) resolveElExp("#{bindings.SumAdjustableAmtTrans.inputValue}");
            advAmt = (Number) resolveElExp("#{bindings.SumAdjustableAdvAmtTrans.inputValue}");

            return new Number(invoiceAmt.sub(advAmt));

        } else {

            return new Number(0);
        }
    }

    /**
     * @param data
     * @return
     * function to get string value of a EL expression written like "#{somevalue}".
     */
    public static Object resolveElExp(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object msg = valueExp.getValue(elContext);
        return msg;
    }

    public void setTvouAdjAmtOTBind(RichOutputText tvouAdjAmtOTBind) {
        this.tvouAdjAmtOTBind = tvouAdjAmtOTBind;
    }

    public RichOutputText getTvouAdjAmtOTBind() {
        return tvouAdjAmtOTBind;
    }


    public void setDisableAdjustment(String disableAdjustment) {
        this.disableAdjustment = disableAdjustment;
    }

    public String getDisableAdjustment() {
        return disableAdjustment;
    }


    public void refreshButtonAction(ActionEvent actionEvent) {
        //update the summary columns
        OperationBinding createOpBAddress = getBindings().getOperationBinding("setAdjustmentSummaryCol");
        createOpBAddress.execute();
        //enable the adjustment button flag after each refresh
        setDisableAdjustment("N");
        refreshButtonBinding.setDisabled(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(refreshButtonBinding);
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String selectAllInvoiceAction() {

        setDisableAdjustment("Y");
        /*  refreshButtonBinding.setDisabled(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(refreshButtonBinding); */
        return "selectAllInvoice";
    }

    public String deSelectAllInvoiceAction() {
        setDisableAdjustment("Y");
        /* refreshButtonBinding.setDisabled(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(refreshButtonBinding); */
        return "deSelectAllInvoice";
    }

    public String selectAllAdvAction() {
        setDisableAdjustment("Y");
        refreshButtonBinding.setDisabled(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(refreshButtonBinding);
        /*  backButtonBinding.setDisabled(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(backButtonBinding); */
        return "selectAllAdv";
    }

    public String deSelectAllAdvAction() {
        setDisableAdjustment("Y");
        refreshButtonBinding.setDisabled(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(refreshButtonBinding);
        /*   backButtonBinding.setDisabled(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(backButtonBinding); */
        return "deSelectAllAdv";
    }

    /**
     * Method action called to search the user entered bill.
     *
     * */

    public void gotoPressed(ActionEvent actionEvent) {
        //intiate the start if searchvalue is not null
        if (getSearchValue() != null) {

            DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding it = (DCIteratorBinding) dcBindings.get("TvouAdjAutoVOIterator");
            RowSetIterator rsi = it.getRowSetIterator();
            RowKeySet oldSelection = invTable.getSelectedRowKeys();

            //iterate through each row untill match is found
            if (rsi.first() != null) {
                Row r = rsi.first();
                while (rsi.hasNext() && getKey() == null && (!matchFound(r, oldSelection))) {
                    r = rsi.next();
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(invTable);
            }
        }
    }

    public Number roundOffAmt(Number amt) {

        Integer amt_digit = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}").toString());
        if (amt != null) {
            if (amt_digit != null) {

                return new Number(amt.round(amt_digit));

            } else {

                return new Number(amt.round(2));

            }
        } else {
            return new Number(0);
        }
    }

    public Object resolvEl(String data) {

        ADFContext adfCtx = ADFContext.getCurrent();
        ELContext elContext = adfCtx.getELContext();
        ValueExpression valueExp = adfCtx.getExpressionFactory().createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext).toString();

        return Message;
    }

    /**
     * Helper method to search the row with given bill no.
     * **/
    private boolean matchFound(Row r, RowKeySet oldSelection) {

        setKey(null);
        ArrayList lst = new ArrayList(1);
        RowKeySetImpl newSelection = new RowKeySetImpl();
        Key key = null;

        if (r.getAttribute("TvouAdjExtDocNo") != null) {
            String rowValue = (String) r.getAttribute("TvouAdjExtDocNo");
            if (rowValue.toString().contains(searchValue)) {
                System.out.println("now setting key to " + key);
                key = r.getKey();
                lst.add(key);
                invTable.setActiveRowKey(lst);
                newSelection.add(lst);
                makeCurrent(invTable, newSelection, oldSelection);
                return true;
            }
        }
        return false;
    }

    private void makeCurrent(RichTable invTable, RowKeySet newCurrentRow, RowKeySet oldCurrentRow) {
        //To make a row current, we need to create a SelectionEvent which
        //expects the following arguments: component, unselected_keys,
        //selected_keys. In our example, we don't have unselected keys and
        //therefore create an empty RowSet for this
        SelectionEvent selectionEvent = new SelectionEvent(oldCurrentRow, newCurrentRow, invTable);
        selectionEvent.queue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(invTable);
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Key getKey() {
        return key;
    }

    public void setInvTable(RichTable invTable) {
        this.invTable = invTable;
    }

    public RichTable getInvTable() {
        return invTable;
    }

    public void setBnkInstrmntAmt(Number bnkInstrmntAmt) {
        this.bnkInstrmntAmt = bnkInstrmntAmt;
    }

    public Number getBnkInstrmntAmt() {
        return bnkInstrmntAmt;
    }

    public void setToDateBinding(RichInputDate toDateBinding) {
        this.toDateBinding = toDateBinding;
    }

    public RichInputDate getToDateBinding() {
        return toDateBinding;
    }

    public void setFrmDateBinding(RichInputDate frmDateBinding) {
        this.frmDateBinding = frmDateBinding;
    }

    public RichInputDate getFrmDateBinding() {
        return frmDateBinding;
    }

    public String fetchAdjDataAction() {

        if ("C".equalsIgnoreCase(resolveElExp("#{pageFlowScope.TVOU_LN_COA_BEHAV}").toString())) {

            if (frmDateBinding.getValue() == null) {
                String msg = (String) resolvEl("#{bundle['MSG.468']}");
                showFacesMsg(msg, null, FacesMessage.SEVERITY_ERROR, frmDateBinding);
                return null;
            }

            if (toDateBinding.getValue() == null) {
                String msg = (String) resolvEl("#{bundle['MSG.469']}");
                showFacesMsg(msg, null, FacesMessage.SEVERITY_ERROR, toDateBinding);
                return null;
            }

        }
        refreshButtonBinding.setDisabled(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(refreshButtonBinding);
        setDisableAdjustment("Y");
        return "getAdjData";
    }

    /**
     *  Generalized method used to display the facesmessages.
     * **/

    public void showFacesMsg(String msgHdr, String msgDtl, javax.faces.application.FacesMessage.Severity msgSeverity,
                             UIComponent componentBinding) {

        FacesMessage msg = new FacesMessage(msgHdr);
        msg.setDetail(msgDtl);
        msg.setSeverity(msgSeverity);
        //FacesContext.getCurrentInstance().addMessage(componentBinding, msg);
        FacesContext.getCurrentInstance().addMessage(componentBinding.getClientId(), msg);
    }

    public void setRefreshButtonBinding(RichCommandButton refreshButtonBinding) {
        this.refreshButtonBinding = refreshButtonBinding;
    }

    public RichCommandButton getRefreshButtonBinding() {
        return refreshButtonBinding;
    }

    /*  public void setBackButtonBinding(RichCommandButton backButtonBinding) {
        this.backButtonBinding = backButtonBinding;
    }

    public RichCommandButton getBackButtonBinding() {
        return backButtonBinding;
    } */


    public String adjustAction() {
        // Add event code here...
        /*   backButtonBinding.setDisabled(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(backButtonBinding); */
        return "autoAdjust";
    }

    public void setTvouCcCurrBinding(RichInputText tvouCcCurrBinding) {
        this.tvouCcCurrBinding = tvouCcCurrBinding;
    }

    public RichInputText getTvouCcCurrBinding() {
        return tvouCcCurrBinding;
    }

    public void setTvouCurrCctrxBinding(RichInputText tvouCurrCctrxBinding) {
        this.tvouCurrCctrxBinding = tvouCurrCctrxBinding;
    }

    public RichInputText getTvouCurrCctrxBinding() {
        return tvouCurrCctrxBinding;
    }
    Number baseAmount = new Number(0);

    public void setBaseAmount(Number baseAmount) {
        this.baseAmount = baseAmount;
    }

    public Number getBaseAmount() {
        return baseAmount;
    }

    public String backAction() {
        ViewObject line = ADFUtil.getAM().findViewObject("TvouLinesLnk");
        if (line != null) {
            Row currentRow = line.getCurrentRow();
            if (currentRow != null) {
                Object cc = currentRow.getAttribute("TvouCc");
                if (cc != null) {
                    Number trxAmt = (Number) sumTrxAmtBind.getValue();
                    baseAmount = trxAmt.multiply((Number) cc);
                    // ADFBeanUtils.callBindingsMethod("updateCostCenterAmt", null, null);
                    ADFBeanUtils.callBindingsMethod("updateCostCenterAmt", new Object[] { sumTrxAmtBind.getValue() }, new Object[] {
                                                    "amountSp" });
                    baseAmount = (Number) ADFBeanUtils.callBindingsMethod("getTotalbaseInvoiceAmount", null, null);

                    //  baseAmount=trxAmt.multiply((Number)cc);
                    // ADFBeanUtils.callBindingsMethod("updateCostCenterAmt", null, null);
                    ADFBeanUtils.callBindingsMethod("updateCostCenterAmt", new Object[] { sumTrxAmtBind.getValue() }, new Object[] {
                                                    "amountSp" });

                }
            }
        }
        return "back";
    }

    public void setRemTrancationAmtBinding(RichInputText remTrancationAmtBinding) {
        this.remTrancationAmtBinding = remTrancationAmtBinding;
    }

    public RichInputText getRemTrancationAmtBinding() {
        return remTrancationAmtBinding;
    }

    public void projectVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        if (valueChangeEvent.getNewValue() != null) {
            ADFBeanUtils.callBindingsMethod("setProject", new Object[] { valueChangeEvent.getNewValue() }, new Object[] {
                                            "mode" });
        }
    }

    public void setTvouCurrIdTrxBinding(RichSelectOneChoice tvouCurrIdTrxBinding) {
        this.tvouCurrIdTrxBinding = tvouCurrIdTrxBinding;
    }

    public RichSelectOneChoice getTvouCurrIdTrxBinding() {
        return tvouCurrIdTrxBinding;
    }

    public void setTvouAdjCurrIdSpBinding(RichSelectOneChoice tvouAdjCurrIdSpBinding) {
        this.tvouAdjCurrIdSpBinding = tvouAdjCurrIdSpBinding;
    }

    public RichSelectOneChoice getTvouAdjCurrIdSpBinding() {
        return tvouAdjCurrIdSpBinding;
    }
}
