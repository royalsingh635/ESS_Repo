package view.bean;

import java.sql.SQLException;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

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

import model.module.PdcAppAMImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;

public class pdcBean {
    private RichInputText bankIdBind;
    private RichInputText coaIdBind;
    private RichInputListOfValues bankNmBind;
    private RichInputListOfValues coaNmBind;
    private RichInputText dispidBind;
    private RichInputText insNumBind;
    private RichInputDate insStrtDtBind;
    private RichInputDate insEndDtBind;
    private RichInputText fromAmtBind;
    private RichInputText toAmtBind;
    private RichInputText vouDtFrmBind;
    private RichInputText vouDtToBind;
    private RichInputDate fromVoudtBind;
    private RichInputDate toVouDtBind;
    private RichSelectBooleanCheckbox chkBoxBind;
    private RichTable pdcTabBind;
    private RichSelectBooleanCheckbox chekforPostBind;

    public pdcBean() {
    }
    oracle.jbo.domain.Number insamtsum = new oracle.jbo.domain.Number(0);

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void pdcSearchAction(ActionEvent actionEvent) {
        chkBoxBind.setValue(false);
        Integer bnkid = null;
        Integer coaid = null;
        String dispid = null;
        String insno = null;
        Date stdt = null;
        Date enddt = null;
        Integer amtfrom = null;
        Integer amtto = null;
        Date voustrtDt = null;
        Date vouendDt = null;
        if (bankIdBind.getValue() != null)
            bnkid = Integer.parseInt(bankIdBind.getValue().toString());
        if (coaIdBind.getValue() != null)
            coaid = Integer.parseInt(coaIdBind.getValue().toString());
        if (dispidBind.getValue() != null)
            dispid = dispidBind.getValue().toString();
        if (insNumBind.getValue() != null)
            insno = insNumBind.getValue().toString();
        if (insStrtDtBind.getValue() != null)
            stdt = (Date)insStrtDtBind.getValue();
        if (insEndDtBind.getValue() != null)
            enddt = (Date)insEndDtBind.getValue();
        if (fromAmtBind.getValue() != null)
            amtfrom = Integer.parseInt(fromAmtBind.getValue().toString());
        if (toAmtBind.getValue() != null)
            amtto = Integer.parseInt(toAmtBind.getValue().toString());
        if (fromVoudtBind.getValue() != null)
            voustrtDt = (Date)fromVoudtBind.getValue();
        if (toVouDtBind.getValue() != null)
            vouendDt = (Date)toVouDtBind.getValue();
        BindingContainer bindings = getBindings();
        OperationBinding op = bindings.getOperationBinding("pdcSearch");
        op.getParamsMap().put("bank_id", bnkid);
        op.getParamsMap().put("coa_id", coaid);
        op.getParamsMap().put("disp_id", dispid);
        op.getParamsMap().put("InsNo", insno);
        op.getParamsMap().put("Strt_dt", stdt);
        op.getParamsMap().put("End_dt", enddt);
        op.getParamsMap().put("amt_frm", amtfrom);
        op.getParamsMap().put("amt_to", amtto);
        op.getParamsMap().put("Voudt_frm", voustrtDt);
        op.getParamsMap().put("Voudt_to", vouendDt);
        op.execute();
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

    public void setBankIdBind(RichInputText bankIdBind) {
        this.bankIdBind = bankIdBind;
    }

    public RichInputText getBankIdBind() {
        return bankIdBind;
    }

    public void setCoaIdBind(RichInputText coaIdBind) {
        this.coaIdBind = coaIdBind;
    }

    public RichInputText getCoaIdBind() {
        return coaIdBind;
    }

    public void pdcResetAction(ActionEvent actionEvent) {
        chkBoxBind.setValue(false);
        BindingContainer bindings = getBindings();
        OperationBinding op = bindings.getOperationBinding("setBindVar");
        op.execute();
    }

    public void setBankNmBind(RichInputListOfValues bankNmBind) {
        this.bankNmBind = bankNmBind;
    }

    public RichInputListOfValues getBankNmBind() {
        return bankNmBind;
    }

    public void setCoaNmBind(RichInputListOfValues coaNmBind) {
        this.coaNmBind = coaNmBind;
    }

    public RichInputListOfValues getCoaNmBind() {
        return coaNmBind;
    }

    public void setDispidBind(RichInputText dispidBind) {
        this.dispidBind = dispidBind;
    }

    public RichInputText getDispidBind() {
        return dispidBind;
    }

    public void setInsNumBind(RichInputText insNumBind) {
        this.insNumBind = insNumBind;
    }

    public RichInputText getInsNumBind() {
        return insNumBind;
    }

    public void setInsStrtDtBind(RichInputDate insStrtDtBind) {
        this.insStrtDtBind = insStrtDtBind;
    }

    public RichInputDate getInsStrtDtBind() {
        return insStrtDtBind;
    }

    public void setInsEndDtBind(RichInputDate insEndDtBind) {
        this.insEndDtBind = insEndDtBind;
    }

    public RichInputDate getInsEndDtBind() {
        return insEndDtBind;
    }

    public void setFromAmtBind(RichInputText fromAmtBind) {
        this.fromAmtBind = fromAmtBind;
    }

    public RichInputText getFromAmtBind() {
        return fromAmtBind;
    }

    public void setToAmtBind(RichInputText toAmtBind) {
        this.toAmtBind = toAmtBind;
    }

    public RichInputText getToAmtBind() {
        return toAmtBind;
    }

    public void setFromVoudtBind(RichInputDate fromVoudtBind) {
        this.fromVoudtBind = fromVoudtBind;
    }

    public RichInputDate getFromVoudtBind() {
        return fromVoudtBind;
    }

    public void setToVouDtBind(RichInputDate toVouDtBind) {
        this.toVouDtBind = toVouDtBind;
    }

    public RichInputDate getToVouDtBind() {
        return toVouDtBind;
    }

    public void setInsamtsum(Number insamtsum) {
        this.insamtsum = insamtsum;
    }

    public Number getInsamtsum() {
        BindingContainer bindings = getBindings();
        OperationBinding op = bindings.getOperationBinding("insSumAmt");
        op.execute();
        Number sum = (Number)op.getResult();
        return sum;
    }

    public void setChkBoxBind(RichSelectBooleanCheckbox chkBoxBind) {
        this.chkBoxBind = chkBoxBind;
    }

    public RichSelectBooleanCheckbox getChkBoxBind() {
        return chkBoxBind;
    }
    private Integer count=0;
    private String shdec="N";
    public void postChkBoxVCL(ValueChangeEvent vce) {
        count=0;
        Date date = new Date();
        boolean isSelected = ((Boolean)vce.getNewValue()).booleanValue();
        DCBindingContainer dcb = (DCBindingContainer)evaluateEL("#{bindings}");
        DCIteratorBinding dciter1 = dcb.findIteratorBinding("pdc1Iterator");
        ViewObject vo = dciter1.getViewObject();
        RowSetIterator dciter = vo.createRowSetIterator(null);
        int i = 0;
        Row row = null;
        vo.reset();
        while (dciter.hasNext()) {
              if (i == 0)
                row = dciter.first();
            else
                row = dciter.next();
            if (isSelected) {
                shdec="Y";
                Date insdt = (Date)row.getAttribute("TvouInstrmntDt");
              if (date.getCurrentDate().compareTo(insdt) == 1 || date.getCurrentDate().compareTo(insdt) == 0)
                {    
                    count=count+1;
                    row.setAttribute("transPostChk", "Y");
                }
                        } 
            else
            {
                shdec="N";
                row.setAttribute("transPostChk", "N");
            }
                        i++;
        }
        System.out.println("total selected rows  is=="+count);
        if(count==0&&isSelected) {
            System.out.println("In VCL");
            chkBoxBind.setValue(false);
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1316']}").toString()); //There is no Instrument for PDC clearing !
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(chkBoxBind.getClientId(), message);
            //fc.addMessage(null, message);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdcTabBind);
    }

    private static Object evaluateEL(String el) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);
        return exp.getValue(elContext);
    }

    public void VoucherPostActionListener(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding op = bindings.getOperationBinding("postVoucherToGL");
        op.execute();
        op = bindings.getOperationBinding("Commit");
        op.execute();
        chkBoxBind.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdcTabBind);
    }

    public void setPdcTabBind(RichTable pdcTabBind) {
        this.pdcTabBind = pdcTabBind;
    }

    public RichTable getPdcTabBind() {
        return pdcTabBind;
    }

    public void setChekforPostBind(RichSelectBooleanCheckbox chekforPostBind) {
        this.chekforPostBind = chekforPostBind;
    }

    public RichSelectBooleanCheckbox getChekforPostBind() {
        return chekforPostBind;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }
    public Object resolvElDCMsg(String data) {
           FacesContext fc = FacesContext.getCurrentInstance();
           Application app = fc.getApplication();
           ExpressionFactory elFactory = app.getExpressionFactory();
           ELContext elContext = fc.getELContext();
           ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
           return valueExp.getValue(elContext);
       }

    public void setShdec(String shdec) {
        this.shdec = shdec;
    }

    public String getShdec() {
        return shdec;
    }

    public void postChkVCL(ValueChangeEvent valueChangeEvent) {
        UIComponent uIComponent=valueChangeEvent.getComponent();
        PdcAppAMImpl am = (PdcAppAMImpl)resolvElDC("PdcAppAMDataControl");
        ViewObjectImpl vo = am.getpdc1();
        Date date = new Date();
        //Date  dt=(Date)vo.getCurrentRow().getAttribute("TvouInstrmntDt");
        Date dt = (Date)uIComponent.getAttributes().get("date");
       // System.out.println("date in table is==" + dt);
      //  System.out.println("dateeeee issssss-========" + date.getCurrentDate());
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().equals(true)) {
                  if (date.getCurrentDate().compareTo(dt) == -1) {
                    chekforPostBind.setValue(false);
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1317']}").toString()); //"Instrument date is not valid for PDC clearing,It can not be selected !"
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(chekforPostBind.getClientId(), message);
                    
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(chekforPostBind);
            }
        
        }
          }
}
