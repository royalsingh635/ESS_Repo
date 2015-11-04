package svcinvoiceapp.view.bean;

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
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;

import svcinvoiceapp.model.services.SVCInvoiceAMImpl;

public class SVCInvoiceAddEditBean {

    private static String mode = "V";
    private static ADFLogger _log = (ADFLogger) ADFLogger.createADFLogger(SVCInvoiceAMImpl.class);
    private RichInputText currConvFctrBind;
    public String docTxnId = null;
    public String wfId = null;
    private RichInputText totalAmountBinding;
    private RichInputText payAmountBinding;
    private RichLink submitAmountBinding;
    private RichSelectOneRadio disctypeBinding;
    private RichInputText taxableAmount;
    private RichInputText avrAlPmtAmtBind;
    private RichInputDate payDateBind;
    private RichSelectOneChoice invcTypeBind;
    private RichSelectOneChoice transDtBind;
    private RichInputText discAmtValueChange;
    private RichInputText discAmtSpBind;
    private RichInputText focTtlAmtBind;

    public void setDocTxnId(String docTxnId) {
        this.docTxnId = docTxnId;
    }

    public String getDocTxnId() {
        return docTxnId;
    }

    public void setWfId(String wfId) {
        this.wfId = wfId;
    }

    public String getWfId() {
        return wfId;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }


    public SVCInvoiceAddEditBean() {
        //  RequestContext.getCurrentInstance().getPageFlowScope().put("Add_Edit_Mode", "E");
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     * For resolving the El
     */
    public Object resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);

    }


    @SuppressWarnings("unchecked")
    public void refreshInvoiceCalc(ActionEvent actionEvent) {

        Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        Integer userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        OperationBinding operationbinding = getBindings().getOperationBinding("refreshInvoiceCalc");
        //  System.out.println("refreshInvoiceCalc:operationbinding::"+operationbinding);
        operationbinding.getParamsMap().put("SlocId", slocId);
        operationbinding.getParamsMap().put("CldId", cldId);
        operationbinding.getParamsMap().put("HoOrgId", hoOrgId);
        operationbinding.getParamsMap().put("OrgId", orgId);
        //System.out.println("passed value successfully");
        operationbinding.execute();
    }


    public void addInvDtlAction(ActionEvent actionEvent) {

        /* OperationBinding createTaxamt = getBindings().getOperationBinding("CreateInsert1");
        createTaxamt.execute(); */
        //invcTypeBind
        Integer invcType = new Integer(0);
        if (invcTypeBind.getValue() != null && invcTypeBind.getValue().toString().length() > 0) {
            invcType = Integer.parseInt(invcTypeBind.getValue().toString());
        }

        if (invcType.compareTo(95) == 0) {
            if (transDtBind.getValue() != null && transDtBind.getValue().toString().length() > 0) {

                OperationBinding obind = getBindings().getOperationBinding("duplicatePmtDateChk");
                obind.execute();
                String flag = obind.getResult().toString();
                if ("N".equalsIgnoreCase(flag)) {
                    FacesMessage message2 = new FacesMessage("Record already exist for this date");
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(transDtBind.getClientId(), message2);
                    return;
                }

            } else {
                FacesMessage message2 = new FacesMessage("Payment date is required");
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(transDtBind.getClientId(), message2);
                return;
            }
        }
        OperationBinding operationbinding;
        operationbinding = getBindings().getOperationBinding("addInvcDtl");
        operationbinding.execute();

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

    public void saveInvoice(ActionEvent actionEvent) {
        OperationBinding genInvoiceNo = getBindings().getOperationBinding("genInvioceNo");
        // System.out.println("operationbinding:::" + genInvoiceNo);
        genInvoiceNo.execute();

        OperationBinding saveAction = getBindings().getOperationBinding("saveAction");
        saveAction.execute();
        OperationBinding operationbinding = getBindings().getOperationBinding("Commit");
        //System.out.println("operationbinding:::" + operationbinding);
        operationbinding.execute();
        callWf();
        operationbinding.execute();
        RequestContext.getCurrentInstance().getPageFlowScope().put("Add_Edit_Mode", "V");
    }

    public void invoiceTaxCalculation(ActionEvent actionEvent) {
        // System.out.println("Welcome in taxcalculation");
        OperationBinding istaxApply = getBindings().getOperationBinding("isTaxApplicable");
        istaxApply.execute();
        String exmtdflag = null;
        if (istaxApply.getErrors().isEmpty()) {
            exmtdflag = istaxApply.getResult().toString();
        }
        _log.info("tax exempted flag value is " + exmtdflag);
        if ("Y".equalsIgnoreCase(exmtdflag)) {
            FacesMessage message2 =
                new FacesMessage("Tax is not Applicable on this item (either tax rule not define or this item is tax exempted)");
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            return;
        } else {
            OperationBinding operationbinding = getBindings().getOperationBinding("invoiceTaxCalculation");
            operationbinding.execute();
        }
    }

    //  @SuppressWarnings("unchecked")
    public void calculationDiscAmtVL(ValueChangeEvent vl) {
        Number discAmt = new Number(0);
        discAmt = (Number) vl.getNewValue();
        // System.out.println("discAmt::12:"+discAmt);
        OperationBinding operationbinding = getBindings().getOperationBinding("calculationDiscAmtValue");
        operationbinding.getParamsMap().put("discAmt", discAmt);
        operationbinding.execute();
    }

    public void discountTypeVLC(ValueChangeEvent vl) {
        // System.out.println("Welcome in discountTypeVLC");
        SVCInvoiceAMImpl am = (SVCInvoiceAMImpl) resolvElDC("SVCInvoiceAMDataControl");
        am.getSvcInvcItm().getCurrentRow().setAttribute("DiscVal", new Number(0));
        am.getSvcInvcItm().getCurrentRow().setAttribute("DiscAmtSp", new Number(0));
    }

    public void taxAftrDiscFlgVL(ValueChangeEvent valueChangeEvent) {
        //System.out.println("welcome in taxAftrDiscFlgVL");
        SVCInvoiceAMImpl am = (SVCInvoiceAMImpl) resolvElDC("SVCInvoiceAMDataControl");
        String checkValue = valueChangeEvent.getNewValue().toString();
        Number itmQnty = (Number) am.getSvcInvcItm().getCurrentRow().getAttribute("ItmQty");
        Number itmprc = (Number) am.getSvcInvcItm().getCurrentRow().getAttribute("ItmPrice");
        //Number discValue=(Number)am.getSvcInvcItm().getCurrentRow().getAttribute("DiscVal");
        Number discAmt = (Number) am.getSvcInvcItm().getCurrentRow().getAttribute("DiscAmtSp");
        Number taxAmt = (Number) am.getSvcInvcItm().getCurrentRow().getAttribute("TaxAmtSp");
        Number taxableAmt = itmQnty.multiply(itmprc);
        Number newTotalAmt = new Number(0);
        //Number discAmt=new Number(0);
        if (checkValue.equalsIgnoreCase("false")) {
            am.getSvcInvcItm().getCurrentRow().setAttribute("TaxableAmtSp", taxableAmt);

        } else if (checkValue.equalsIgnoreCase("true")) {
            taxableAmt = (Number) taxableAmt.minus(discAmt);
            // System.out.println("taxableAmt:in else:"+taxableAmt);
            am.getSvcInvcItm().getCurrentRow().setAttribute("TaxableAmtSp", taxableAmt);
        }

        newTotalAmt = (Number) ((itmQnty.multiply(itmprc).minus(discAmt)));

        am.getSvcInvcItm().getCurrentRow().setAttribute("transTotalAmount", newTotalAmt);
        am.getSvcInvcItm().getCurrentRow().setAttribute("ItmAmtSp", newTotalAmt);
        am.getSvcInvcItm().getCurrentRow().setAttribute("TaxAmtSp", new Number(0));

    }

    public void schedulePaymntAmt(ActionEvent actionEvent) {
        if (payDateBind.getValue() != null && payDateBind.getValue().toString().length() > 0) {

        } else {
            FacesMessage message2 = new FacesMessage("Payment date can't be empty");
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(payDateBind.getClientId(), message2);
            return;
        }

        if (payAmountBinding.getValue() != null && payAmountBinding.getValue().toString().length() > 0) {

        } else {
            FacesMessage message2 = new FacesMessage("Payment Amount can't be empty");
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(payDateBind.getClientId(), message2);
            return;
        }

        if (payAmountBinding.getValue() != null) {
            Number amt = (Number) payAmountBinding.getValue();
            if (amt.compareTo(0) > 0) {
                OperationBinding dupDate = getBindings().getOperationBinding("duplicateDateChk");
                dupDate.execute();
                _log.info("result and error is " + dupDate.getErrors() + "  " + dupDate.getResult());
                String flg = dupDate.getResult().toString();
                if (flg.equalsIgnoreCase("N")) {
                    FacesMessage message2 = new FacesMessage("Record Already Exists for this date");
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(payDateBind.getClientId(), message2);
                    return;
                }

                OperationBinding operationbinding = getBindings().getOperationBinding("schedulePaymntAmt");
                operationbinding.execute();
                //payAmountBinding
                AdfFacesContext.getCurrentInstance().addPartialTarget(payAmountBinding);
               // payAmountBinding.setValue(new Number(0));
            } else {
                ADFUtil.showFacesMsg(resolvElDCMsg("#{bundle['MSG.1641']}").toString(),
                                     resolvElDCMsg("#{bundle['MSG.489']}").toString(), FacesMessage.SEVERITY_ERROR,
                                     null);
            }

        }
    }
    //  @SuppressWarnings("unchecked")
    public void eoNmValueChngeListener(ValueChangeEvent valueChangeEvent) {
        String paramHoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        // System.out.println("paramHoOrgId:::"+paramHoOrgId);
        if (valueChangeEvent.getNewValue() != null) {
            // paramHoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            OperationBinding geteo = getBindings().getOperationBinding("getCoaForEo");
            geteo.getParamsMap().put("hoOrgId", paramHoOrgId);
            geteo.getParamsMap().put("eoName", valueChangeEvent.getNewValue().toString());
            geteo.execute();


            Object coa = geteo.getResult();
            // System.out.println("coa:1::"+coa);
            if (coa != null) {
                Integer coaid = Integer.parseInt(coa.toString());
                // System.out.println("coaid::::2:::"+coaid);
                if (coaid > 0) {
                    _log.info("Set COA" + coaid);
                    OperationBinding setCoa = getBindings().getOperationBinding("setcoaIdForInvc");
                    setCoa.getParamsMap().put("coa", coaid);
                    setCoa.execute();

                } else if (coaid == -2) {
                    ADFUtil.showFacesMsg(resolvElDCMsg("#{bundle['MSG.1642']}").toString(),
                                         resolvElDCMsg("#{bundle['MSG.1643']}").toString(), FacesMessage.SEVERITY_WARN,
                                         null);
                } else if (coaid == -3) {
                    ADFUtil.showFacesMsg(resolvElDCMsg("#{bundle['MSG.1642']}").toString(),
                                         resolvElDCMsg("#{bundle['MSG.1644']}").toString(), FacesMessage.SEVERITY_WARN,
                                         null);
                } else if (coaid == 0) {
                    //System.out.println("walkin customer");





                } else {
                    //  System.out.println("error");
                    ADFUtil.showFacesMsg(resolvElDCMsg("#{bundle['MSG.1642']}").toString(),
                                         resolvElDCMsg("#{bundle['MSG.897']}").toString(), FacesMessage.SEVERITY_ERROR,
                                         null);

                }
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(currConvFctrBind);
    }

    public void setCurrConvFctrBind(RichInputText currConvFctrBind) {
        this.currConvFctrBind = currConvFctrBind;
    }

    public RichInputText getCurrConvFctrBind() {
        return currConvFctrBind;
    }

    public void rowSelection(SelectionEvent selectionEvent) {
        ADFUtil.invokeEL("#{bindings.SvcInvcItm.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class }, new Object[] {
                         selectionEvent });
        Row selectedRow = (Row) ADFUtil.evaluateEL("#{bindings.SvcInvcItmIterator.currentRow}");
    }

    public void selectionDocNoSrc(SelectionEvent selectionEvent) {
        ADFUtil.invokeEL("#{bindings.SvcInvcSrc.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class }, new Object[] {
                         selectionEvent });
        Row selectedRow = (Row) ADFUtil.evaluateEL("#{bindings.SvcInvcSrcIterator.currentRow}");
    }

    public void addItmDetail(ActionEvent actionEvent) {
        // Add event code here...
    }

    public String saveAndForwardAction() {
        OperationBinding validate = getBindings().getOperationBinding("validatePayment");
        validate.execute();
        Object object = validate.getResult();
        if (object != null) {
            Integer res = (Integer) object;
            if (res.equals(0)) {
                //saveInvoice(null);
                OperationBinding genInvoiceNo = getBindings().getOperationBinding("genInvioceNo");
                //  System.out.println("operationbinding:::" + genInvoiceNo);
                genInvoiceNo.execute();
                OperationBinding operationbinding = getBindings().getOperationBinding("Commit");
                //  System.out.println("operationbinding:::" + operationbinding);
                operationbinding.execute();
                callWf();
                operationbinding.execute();
                OperationBinding binding = getBindings().getOperationBinding("getCurrentDocId");
                binding.execute();
                docTxnId = (String) binding.getResult();
                OperationBinding binding1 = getBindings().getOperationBinding("getWfId");
                binding1.execute();
                wfId = (String) binding1.getResult();
                RequestContext.getCurrentInstance().getPageFlowScope().put("Add_Edit_Mode", "V");
                // System.out.println("docTxnId = "+docTxnId+" wfId = "+wfId);
                /*  OperationBinding operationbinding = getBindings().getOperationBinding("Commit");
                operationbinding.execute() */;
                return "wf";
            } else {
                ADFUtil.showFacesMsg(resolvElDCMsg("#{bundle['MSG.1645']}").toString(), null,
                                     FacesMessage.SEVERITY_ERROR, null);
            }
        }
        return null;
    }

    public String editInvoice() {
        Integer usrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        //
        OperationBinding operationbinding = getBindings().getOperationBinding("getDocUsrFromWF");
        operationbinding.execute();
        Object object = operationbinding.getResult();
        if (object != null) {
            Integer pending = (Integer) object;
            if (pending != usrId) {
                //System.out.println("error");
                ADFUtil.showFacesMsg(resolvElDCMsg("#{bundle['MSG.1646']}").toString(),
                                     resolvElDCMsg("#{bundle['MSG.612']}").toString(), FacesMessage.SEVERITY_ERROR,
                                     null);
            } else {
                RequestContext.getCurrentInstance().getPageFlowScope().put("Add_Edit_Mode", "E");
            }
        }
        return null;
    }

    public boolean callWf() {
        //callWfFunctions
        OperationBinding operationbinding = getBindings().getOperationBinding("callWfFunctions");
        operationbinding.execute();
        return true;
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }


    public void discValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null && object.toString().length()>0) {
            Number discVal = (Number) object;
            if (discVal.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1292']}").toString(), null));

            } else if (!ADFUtil.isPrecisionValid(26, 6, discVal)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1637']}").toString(), null));

            } else if (disctypeBinding.getValue() != null && disctypeBinding.getValue().toString().length()>0) {
                if (disctypeBinding.getValue().toString().equalsIgnoreCase("P")) {
                    if (discVal.compareTo(100) >= 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvElDCMsg("#{bundle['MSG.1638']}").toString(),
                                                                      null));

                    }
                } else if (disctypeBinding.getValue().toString().equalsIgnoreCase("A")) {
                   // if (discVal.compareTo(taxableAmount.getValue()) > 0) {
                    
                    OperationBinding obind=getBindings().getOperationBinding("discAmtValidator");
                    obind.getParamsMap().put("discValue", discVal);
                    obind.execute();
                    String rslt=(String)obind.getResult();
                    
                    if("N".equalsIgnoreCase(rslt))
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvElDCMsg("#{bundle['MSG.1639']}").toString(),
                                                                      null));

                  //  }
                }
            }
        }
    }

    public void totalAmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number discVal = (Number) object;
            if (discVal.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1292']}").toString(), null));

            } else if (!ADFUtil.isPrecisionValid(26, 6, discVal)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1637']}").toString(), null));

            }
        }
    }

    public void spAmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number payAmt = (Number) object;
            if (payAmt.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1292']}").toString(), null));

            } else if (!ADFUtil.isPrecisionValid(26, 6, payAmt)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1637']}").toString(), null));

            } else { //validAmount
                OperationBinding operationbinding = getBindings().getOperationBinding("validAmount");
                operationbinding.getParamsMap().put("payAmt", payAmt);
                operationbinding.execute();
                if (operationbinding.getResult() != null) {
                    if (operationbinding.getResult().toString().equalsIgnoreCase("N")) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvElDCMsg("#{bundle['MSG.1640']}").toString(),
                                                                      null));
                    }
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(submitAmountBinding);
    }

    public void eoNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /*  String paramHoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        System.out.println("paramHoOrgId:::"+paramHoOrgId);
        if(object!=null){
        // paramHoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        OperationBinding geteo = getBindings().getOperationBinding("getCoaForEo");
        geteo.getParamsMap().put("hoOrgId",paramHoOrgId);
        geteo.getParamsMap().put("eoName",object.toString());
        geteo.execute();


        Object coa=geteo.getResult();
        System.out.println("coa:1::"+coa);
            if(coa!=null){
                Integer coaid=Integer.parseInt(coa.toString());
                System.out.println("coaid::::2:::"+coaid);
                if(coaid!=0){
                    _log.info("Set COA"+coaid);
                    OperationBinding setCoa = getBindings().getOperationBinding("setcoaIdForInvc");
                    setCoa.getParamsMap().put("coa",coaid);
                    setCoa.execute();

                }else if(coaid==0){
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Coa Does Not Exist For this Customer! Enter Other Customer",null));
                }
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(currConvFctrBind);
    } */
    }

    public void setTotalAmountBinding(RichInputText totalAmountBinding) {
        this.totalAmountBinding = totalAmountBinding;
    }

    public RichInputText getTotalAmountBinding() {
        return totalAmountBinding;
    }

    public void setPayAmountBinding(RichInputText payAmountBinding) {
        this.payAmountBinding = payAmountBinding;
    }

    public RichInputText getPayAmountBinding() {
        return payAmountBinding;
    }

    public String addINvoiceAction() {
        RequestContext.getCurrentInstance().getPageFlowScope().put("Add_Edit_Mode", "A");
        return "create";
    }

    public void deletePaymentScheduleAction(ActionEvent actionEvent) {

        Object slNo = actionEvent.getComponent().getAttributes().get("slNoAttr");
        if (slNo != null) {
            OperationBinding validate = getBindings().getOperationBinding("deletePaymentSchedule");
            validate.getParamsMap().put("slNo", slNo);
            validate.execute();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(payAmountBinding);
    }

    public void setSubmitAmountBinding(RichLink submitAmountBinding) {
        this.submitAmountBinding = submitAmountBinding;
    }

    public RichLink getSubmitAmountBinding() {
        return submitAmountBinding;
    }

    public void setDisctypeBinding(RichSelectOneRadio disctypeBinding) {
        this.disctypeBinding = disctypeBinding;
    }

    public RichSelectOneRadio getDisctypeBinding() {
        return disctypeBinding;
    }

    public void setTaxableAmount(RichInputText taxableAmount) {
        this.taxableAmount = taxableAmount;
    }

    public RichInputText getTaxableAmount() {
        return taxableAmount;
    }

    public void pmtAmtDiscloser(DisclosureEvent disclosureEvent) {
        // Add event code here...//setPaymentAmount
        OperationBinding validate = getBindings().getOperationBinding("setPaymentAmount");
        validate.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(avrAlPmtAmtBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(focTtlAmtBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(payAmountBinding);
        //avrAlPmtAmtBind//payAmountBinding
    }

    public void setAvrAlPmtAmtBind(RichInputText avrAlPmtAmtBind) {
        this.avrAlPmtAmtBind = avrAlPmtAmtBind;
    }

    public RichInputText getAvrAlPmtAmtBind() {
        return avrAlPmtAmtBind;
    }

    public void setPayDateBind(RichInputDate payDateBind) {
        this.payDateBind = payDateBind;
    }

    public RichInputDate getPayDateBind() {
        return payDateBind;
    }

    public void setInvcTypeBind(RichSelectOneChoice invcTypeBind) {
        this.invcTypeBind = invcTypeBind;
    }

    public RichSelectOneChoice getInvcTypeBind() {
        return invcTypeBind;
    }

    public void setTransDtBind(RichSelectOneChoice transDtBind) {
        this.transDtBind = transDtBind;
    }

    public RichSelectOneChoice getTransDtBind() {
        return transDtBind;
    }

    public void discValueChange(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(discAmtSpBind);
    }

    public void setDiscAmtValueChange(RichInputText discAmtValueChange) {
        this.discAmtValueChange = discAmtValueChange;
    }

    public RichInputText getDiscAmtValueChange() {
        return discAmtValueChange;
    }

    public void setDiscAmtSpBind(RichInputText discAmtSpBind) {
        this.discAmtSpBind = discAmtSpBind;
    }

    public RichInputText getDiscAmtSpBind() {
        return discAmtSpBind;
    }

    public void setFocTtlAmtBind(RichInputText focTtlAmtBind) {
        this.focTtlAmtBind = focTtlAmtBind;
    }

    public RichInputText getFocTtlAmtBind() {
        return focTtlAmtBind;
    }
}
