package pmssiterecordapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import java.sql.Date;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;

//comment added by nitesh garg
public class PmsSiteRecordAddBean {
    private String mode = "V";
    private static ADFLogger _log = ADFLogger.createADFLogger(PmsSiteRecordAddBean.class);
    Integer srDestNo = null;
    private RichInputListOfValues expReqNoBinding;
    private RichPopup taxPopupBinding;
    public Number zero = new Number(0);
    private RichPopup tdsPopupBinding;
    private Boolean enableCostCenter = null;
    private RichSelectOneChoice projectBinding;
    private RichInputListOfValues empNmBinding;
    private RichInputDate rcdDtBinding;
    private RichInputListOfValues coaNmBinding;
    private RichInputListOfValues currencyNmBinding;
    private RichInputListOfValues expCoaNmBinding;
    private RichInputListOfValues billCoaNmBinding;
    private RichInputDate dueDtBinding;
    private RichInputText billAmtSpBinding;
    private RichInputText billInvcNoBinding;
    private RichInputDate billInvcDtBinding;
    private java.sql.Date sysdate = null;

    public void setSysdate(Date sysdate) {
        this.sysdate = sysdate;
    }

    public Date getSysdate() {
        try {
            return (new Timestamp(System.currentTimeMillis())).dateValue();
        } catch (SQLException e) {
            _log.info("Error in Cast to Date");
        }
        return sysdate;
    }

    //Setting values to check cost center aplicable
    public Boolean getEnableCostCenter() {
        //isCostCenterApplicable
        if (enableCostCenter == null) {
            OperationBinding b = ADFBeanUtils.getOperationBinding("isCostCenterApplicable");
            if (b != null) {
                b.execute();
                Object o = b.getResult();
                enableCostCenter = (o == null ? false : (Boolean) o);
            }
        }
        return enableCostCenter;
    }

    public void setZero(Number zero) {
        this.zero = zero;
    }

    public Number getZero() {
        return zero;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public PmsSiteRecordAddBean() {
        if (ADFModelUtils.resolvEl("#{pageFlowScope.P_MODE}") != null)
            mode = (String) ADFModelUtils.resolvEl("#{pageFlowScope.P_MODE}");
    }


    public String addReportACTION() {
        OperationBinding createOp = ADFBeanUtils.getOperationBinding("CreateInsert");
        createOp.execute();
        mode = "A";
        return null;
    }

    public String editReportACTION() {
        OperationBinding pendingAtOp = ADFBeanUtils.getOperationBinding("chkPendingUsr");
        pendingAtOp.execute();
        Integer pendingAt = (Integer) pendingAtOp.getResult();
        if (pendingAt.equals(-1) || pendingAt.equals(EbizParams.GLBL_APP_USR()))
            mode = "A";
        else {
            OperationBinding usrNmOp = ADFBeanUtils.getOperationBinding("getUsrNm");
            usrNmOp.getParamsMap().put("usrId", pendingAt);
            usrNmOp.execute();
            String usrNm = "Anonymous.";
            if (usrNmOp.getResult() != null)
                usrNm = (String) usrNmOp.getResult();
            /*  ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("#{bundle['MSG.1163']}"),
                                                    ADFModelUtils.resolvRsrc("#{bundle['MSG.1294']}") + " [ " + usrNm +
                                                    "]. ", FacesMessage.SEVERITY_INFO);*/
            ADFModelUtils.showFormattedFacesMessage(" ", ADFModelUtils.resolvRsrc("MSG.1294") + usrNm + "].",
                                                    FacesMessage.SEVERITY_INFO);
        }
        return null;
    }

    public String saveReportACTION() {
        Boolean valid = this.callToSaveApplication();
        if (valid) {
            //Call for WF
            String action = "I";
            String remark = "A";

            OperationBinding WfnoOp = ADFBeanUtils.findOperation("getWfNo");
            WfnoOp.execute();

            String WfNum = null;
            Integer level = 0;
            Integer res = -1;

            if (WfnoOp.getResult() != null) {
                WfNum = WfnoOp.getResult().toString();
            }
            _log.info("on Save Wf No=" + WfNum);
            if (WfNum != null) {

                OperationBinding usrLevelOp = ADFBeanUtils.findOperation("currUsrLvl");
                usrLevelOp.getParamsMap().put("wfNo", WfNum);
                usrLevelOp.execute();
                level = -1;
                if (usrLevelOp.getResult() != null) {
                    if (usrLevelOp.getResult() != null)
                        level = Integer.parseInt(usrLevelOp.getResult().toString());
                }
                if (level == -1) {
                    ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1875"), " ",
                                                            FacesMessage.SEVERITY_WARN);
                } else {
                    OperationBinding insTxnOp = ADFBeanUtils.findOperation("insWfTxn");
                    insTxnOp.getParamsMap().put("wfId", WfNum);
                    insTxnOp.getParamsMap().put("lvlFrm", level);
                    insTxnOp.getParamsMap().put("lvlTo", level);
                    insTxnOp.getParamsMap().put("action", action);
                    insTxnOp.getParamsMap().put("remark", remark);
                    insTxnOp.getParamsMap().put("amount", 0);
                    insTxnOp.execute();

                    if (insTxnOp.getResult() != null) {
                        res = Integer.parseInt(insTxnOp.getResult().toString());
                    }
                    if (res == 1) {
                        OperationBinding operationBinding = ADFBeanUtils.findOperation("Commit");
                        operationBinding.execute();
                        ADFModelUtils.showFormattedFacesMessage(" ", ADFModelUtils.resolvRsrc("MSG.91"),
                                                                FacesMessage.SEVERITY_INFO);
                    } else {
                        _log.info("error during insert to WF");
                    }
                    _log.info(level + "level--res" + res);
                }
            } else {
                ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1486"), " ",
                                                        FacesMessage.SEVERITY_WARN);

            }

        }
        return null;
    }

    public String cancelReportACTION() {
        OperationBinding createOp = ADFBeanUtils.getOperationBinding("Rollback");
        createOp.execute();
        mode = "V";
        return "back";
    }

    public String saveAndFwdACTION() {
        if (this.chkBeforeSaveAndForward()) {
            //Workflow Start
            String action = "I";
            String remark = "A";

            OperationBinding WfnoOp = ADFBeanUtils.findOperation("getWfNo");
            WfnoOp.execute();

            String WfNum = null;
            Integer level = 0;
            Integer res = -1;

            if (WfnoOp.getResult() != null) {
                WfNum = WfnoOp.getResult().toString();
            }
            _log.info("on Fwd Wf No=" + WfNum);
            if (WfNum != null) {

                OperationBinding usrLevelOp = ADFBeanUtils.findOperation("currUsrLvl");
                usrLevelOp.getParamsMap().put("wfNo", WfNum);
                usrLevelOp.execute();
                level = -1;
                if (usrLevelOp.getResult() != null) {
                    if (usrLevelOp.getResult() != null)
                        level = Integer.parseInt(usrLevelOp.getResult().toString());
                }
                if (level == -1) {

                    ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1875"), " ",
                                                            FacesMessage.SEVERITY_ERROR);
                } else {
                    OperationBinding insTxnOp = ADFBeanUtils.findOperation("insWfTxn");
                    insTxnOp.getParamsMap().put("wfId", WfNum);
                    insTxnOp.getParamsMap().put("lvlFrm", level);
                    insTxnOp.getParamsMap().put("lvlTo", level);
                    insTxnOp.getParamsMap().put("action", action);
                    insTxnOp.getParamsMap().put("remark", remark);
                    insTxnOp.getParamsMap().put("amount", 0);
                    insTxnOp.execute();

                    if (insTxnOp.getResult() != null) {
                        res = Integer.parseInt(insTxnOp.getResult().toString());
                    }
                    _log.info("Workflow insert return=" + res);
                    if (res != null && res == 1) {
                        OperationBinding operationBinding = ADFBeanUtils.findOperation("Commit");
                        operationBinding.execute();
                        return "workFlow";
                    } else {
                        _log.info("error during insert to WF");
                    }
                    _log.info(level + "level--res" + res);
                }
            } else {
                ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1486"), " ",
                                                        FacesMessage.SEVERITY_WARN);
            }

        }
        //Workflow end

        return null;
    }

    public Boolean callToSaveApplication() {
        _log.info("Save Method");

        OperationBinding rcdIdOp = ADFBeanUtils.getOperationBinding("genSiteRcdId");
        rcdIdOp.execute();
        if (rcdIdOp.getErrors().size() == 0 && rcdIdOp.getResult() != null) {
            OperationBinding ccAmt = ADFBeanUtils.getOperationBinding("sendDataFromTempCcToPmsSiteRcdCc");
            ccAmt.execute();
            OperationBinding saveOp = ADFBeanUtils.getOperationBinding("Commit");
            saveOp.execute();
            mode = "V";
            return true;
        } else {
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1478"), " ",
                                                    FacesMessage.SEVERITY_INFO);
            return false;
        }

    }

    public String populateFinReqExpACTION() {
        if (chkRequiredHeadField()) {
            if (expReqNoBinding.getValue() != null && expReqNoBinding.getValue().toString().length() > 0) {
            } else
                return null;
            OperationBinding popReqExpOp = ADFBeanUtils.getOperationBinding("populateFinReqExpenseDtl");
            popReqExpOp.execute();
            _log.info("populateFinReqExpenseDtl Return=" + popReqExpOp.getResult());
            if (popReqExpOp.getResult() != null && popReqExpOp.getResult().toString().equals("-1")) {
                ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2633"), " ", FacesMessage.SEVERITY_WARN,
                                               expReqNoBinding.getClientId());
            } else {
                expReqNoBinding.setValue(null);
            }
        }
        return null;
    }

    public String addNewExpCoaACTION() {
        if (chkRequiredHeadField()) {

            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("PmsSiteRcdExpDtlIterator");
            RowSetIterator rsi = dciter.getRowSetIterator();
            if (rsi.getCurrentRow() != null &&
                (expCoaNmBinding.getValue() == null || expCoaNmBinding.getValue().toString().length() == 0)) {
                ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1684"), " ", FacesMessage.SEVERITY_WARN,
                                               expCoaNmBinding.getClientId());
                return null;
            }
            Row lastRow = rsi.last();
            int lastRowIndex = rsi.getRangeIndexOf(lastRow);
            Row newRow = rsi.createRow();
            newRow.setNewRowState(Row.STATUS_INITIALIZED);
            rsi.insertRowAtRangeIndex(lastRowIndex + 1, newRow);
            rsi.setCurrentRow(newRow);
            OperationBinding defValOp = ADFBeanUtils.getOperationBinding("setDefaultExpValues");
            defValOp.execute();
        }
        return null;
    }

    public void addExpBillAL(ActionEvent actionEvent) {

        srDestNo = (Integer) actionEvent.getComponent().getAttributes().get("srNoExp");
        _log.info("Filtering Detail for No=" + srDestNo);
        OperationBinding filterBill = ADFBeanUtils.getOperationBinding("filterBillDtl");
        filterBill.getParamsMap().put("SrDestNo", srDestNo);
        filterBill.execute();
    }

    public String goBackToRecordDtlACTION() {
        if (mode.equals("A")) {
            OperationBinding replOp = ADFBeanUtils.getOperationBinding("replicateBilledAmtToExpAmt");
            replOp.execute();
        }
        return "goToRecord";
    }

    public String addNewBillACTION() {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("PmsSiteRcdBillDtlIterator");
        RowSetIterator rsi = dciter.getRowSetIterator();
        Row lastRow = rsi.last();
        int lastRowIndex = rsi.getRangeIndexOf(lastRow);
        Row newRow = rsi.createRow();
        newRow.setNewRowState(Row.STATUS_INITIALIZED);
        rsi.insertRowAtRangeIndex(lastRowIndex + 1, newRow);
        rsi.setCurrentRow(newRow);
        OperationBinding defOp = ADFBeanUtils.getOperationBinding("setDefaultBillValues");
        defOp.getParamsMap().put("srDestNo", srDestNo);
        defOp.execute();
        return null;
    }

    public void setExpReqNoBinding(RichInputListOfValues expReqNoBinding) {
        this.expReqNoBinding = expReqNoBinding;
    }

    public RichInputListOfValues getExpReqNoBinding() {
        return expReqNoBinding;
    }

    public void setTaxPopupBinding(RichPopup taxPopupBinding) {
        this.taxPopupBinding = taxPopupBinding;
    }

    public RichPopup getTaxPopupBinding() {
        return taxPopupBinding;
    }

    public String addTaxRuleACTION() {
        ADFBeanUtils.showPopup(taxPopupBinding, true);
        if (mode.equals("A")) {
            OperationBinding addTaxOp = ADFBeanUtils.getOperationBinding("createNewTaxRule");
            addTaxOp.execute();
        }
        return null;
    }

    public void taxRuleVCE(ValueChangeEvent valueChangeEvent) {
        OperationBinding addTaxLineOp = ADFBeanUtils.getOperationBinding("populateTaxRuleLines");
        addTaxLineOp.getParamsMap().put("taxRuleId", valueChangeEvent.getNewValue());
        addTaxLineOp.execute();
    }

    public void setTdsPopupBinding(RichPopup tdsPopupBinding) {
        this.tdsPopupBinding = tdsPopupBinding;
    }

    public RichPopup getTdsPopupBinding() {
        return tdsPopupBinding;
    }

    public String addTDSRuleACTION() {
        if (mode.equals("A")) {
            OperationBinding addTdsOp = ADFBeanUtils.getOperationBinding("createNewTDSRule");
            addTdsOp.execute();
        }
        ADFBeanUtils.showPopup(tdsPopupBinding, true);
        return null;
    }

    public void tdsRuleVCE(ValueChangeEvent valueChangeEvent) {
        OperationBinding addTaxLineOp = ADFBeanUtils.getOperationBinding("populateTdsRuleLines");
        addTaxLineOp.getParamsMap().put("tdsRuleId", valueChangeEvent.getNewValue());
        addTaxLineOp.execute();
    }

    //Action to go to cost center detail service page
    public String costCenterAction() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            return "costCenter";
        } else {
            return null;
        }
    }

    public String deleteRcdDtlLine() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("deleteCostCenterItem");
        binding.execute();
        OperationBinding delIr = ADFBeanUtils.getOperationBinding("deleteExpenseDtl");
        delIr.execute();
        /* OperationBinding delOp = ADFBeanUtils.getOperationBinding("Delete1");
        delOp.execute(); */
        return null;
    }

    public Boolean chkRequiredHeadField() {
        if (projectBinding.getValue() == null || projectBinding.getValue().toString().length() == 0) {
            ADFModelUtils.showFacesMessage("Please select Project.", " ", FacesMessage.SEVERITY_WARN,
                                           projectBinding.getClientId());
            return false;
        }
        /*   if (empNmBinding.getValue() == null || empNmBinding.getValue().toString().length() == 0) {
            ADFModelUtils.showFacesMessage("Please select Employee.", " ", FacesMessage.SEVERITY_WARN,
                                           empNmBinding.getClientId());
            return false;
        } */
        if (rcdDtBinding.getValue() == null || rcdDtBinding.getValue().toString().length() == 0) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2490"), " ", FacesMessage.SEVERITY_WARN,
                                           rcdDtBinding.getClientId());
            return false;
        }
        if (coaNmBinding.getValue() == null || coaNmBinding.getValue().toString().length() == 0) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.1684"), " ", FacesMessage.SEVERITY_WARN,
                                           coaNmBinding.getClientId());
            return false;
        }
        if (currencyNmBinding.getValue() == null || currencyNmBinding.getValue().toString().length() == 0) {
            ADFModelUtils.showFacesMessage("Please select Currency.", " ", FacesMessage.SEVERITY_WARN,
                                           currencyNmBinding.getClientId());
            return false;
        }
        return true;
    }

    public void setProjectBinding(RichSelectOneChoice projectBinding) {
        this.projectBinding = projectBinding;
    }

    public RichSelectOneChoice getProjectBinding() {
        return projectBinding;
    }

    public void setEmpNmBinding(RichInputListOfValues empNmBinding) {
        this.empNmBinding = empNmBinding;
    }

    public RichInputListOfValues getEmpNmBinding() {
        return empNmBinding;
    }

    public void setRcdDtBinding(RichInputDate rcdDtBinding) {
        this.rcdDtBinding = rcdDtBinding;
    }

    public RichInputDate getRcdDtBinding() {
        return rcdDtBinding;
    }

    public void setCoaNmBinding(RichInputListOfValues coaNmBinding) {
        this.coaNmBinding = coaNmBinding;
    }

    public RichInputListOfValues getCoaNmBinding() {
        return coaNmBinding;
    }

    public void setCurrencyNmBinding(RichInputListOfValues currencyNmBinding) {
        this.currencyNmBinding = currencyNmBinding;
    }

    public RichInputListOfValues getCurrencyNmBinding() {
        return currencyNmBinding;
    }

    public void expAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (expCoaNmBinding.getValue() != null && expCoaNmBinding.getValue().toString().length() > 0) {
            if (object != null) {
                Number rate = (Number) (object);
                if (!ADFBeanUtils.isPrecisionValid(26, 6, rate))
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                                  ADFModelUtils.resolvRsrc("MSG.57"), null));

                if (rate.compareTo(new Number(0)) < 0)
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                                  ADFModelUtils.resolvRsrc("MSG.1665"), null));

            } else
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                              ADFModelUtils.resolvRsrc("MSG.977"), null));

        }

    }

    public void setExpCoaNmBinding(RichInputListOfValues expCoaNmBinding) {
        this.expCoaNmBinding = expCoaNmBinding;
    }

    public RichInputListOfValues getExpCoaNmBinding() {
        return expCoaNmBinding;
    }

    public void setBillCoaNmBinding(RichInputListOfValues billCoaNmBinding) {
        this.billCoaNmBinding = billCoaNmBinding;
    }

    public RichInputListOfValues getBillCoaNmBinding() {
        return billCoaNmBinding;
    }

    public void setDueDtBinding(RichInputDate dueDtBinding) {
        this.dueDtBinding = dueDtBinding;
    }

    public RichInputDate getDueDtBinding() {
        return dueDtBinding;
    }

    public void setBillAmtSpBinding(RichInputText billAmtSpBinding) {
        this.billAmtSpBinding = billAmtSpBinding;
    }

    public RichInputText getBillAmtSpBinding() {
        return billAmtSpBinding;
    }

    public void setBillInvcNoBinding(RichInputText billInvcNoBinding) {
        this.billInvcNoBinding = billInvcNoBinding;
    }

    public RichInputText getBillInvcNoBinding() {
        return billInvcNoBinding;
    }

    public void setBillInvcDtBinding(RichInputDate billInvcDtBinding) {
        this.billInvcDtBinding = billInvcDtBinding;
    }

    public RichInputDate getBillInvcDtBinding() {
        return billInvcDtBinding;
    }

    public String resetTaxACTION() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("resetTrAndTrLine");
        binding.execute();
        if (binding.getResult() != null && binding.getResult().toString().equals("Y"))
            ADFModelUtils.showFacesMessage(" ", ADFModelUtils.resolvRsrc("MSG.2643"), FacesMessage.SEVERITY_INFO, null);

        return null;
    }

    public String resetTDSACTION() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("resetTDSRuleAndTDSLine");
        binding.execute();
        if (binding.getResult() != null && binding.getResult().toString().equals("Y"))
            ADFModelUtils.showFacesMessage(" ", ADFModelUtils.resolvRsrc("MSG.2644"), FacesMessage.SEVERITY_INFO, null);
        return null;
    }

    public void billAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number rate = (Number) (object);
            if (!ADFBeanUtils.isPrecisionValid(26, 6, rate))
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                              ADFModelUtils.resolvRsrc("MSG.57"), null));

            if (rate.compareTo(new Number(0)) < 0)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                              ADFModelUtils.resolvRsrc("MSG.1665"), null));

        } /* else
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                          ADFModelUtils.resolvRsrc("MSG.977"), null)); */

    }

    public void coaNmVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(currencyNmBinding);
    }

    public String removeTaxACTION() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("resetTrAndTrLine");
        binding.execute();
        taxPopupBinding.hide();
        return null;
    }

    public String removeTDSACTION() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("resetTDSRuleAndTDSLine");
        binding.execute();
        tdsPopupBinding.hide();
        return null;
    }

    public void tdsCheckBoxVCL(ValueChangeEvent valueChangeEvent) {
        _log.info("New Value =" + valueChangeEvent.getNewValue());
        OperationBinding binding = ADFBeanUtils.getOperationBinding("updateTDSCheckInSameAccounts");
        binding.getParamsMap().put("chkVal",
                                   valueChangeEvent.getNewValue() != null &&
                                   valueChangeEvent.getNewValue().toString().equals("true") ? "Y" : "N");
        binding.execute();
    }

    public void billInvoiceNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding chkDupli = ADFBeanUtils.getOperationBinding("chkDuplicateInvoiceNo");
            chkDupli.getParamsMap().put("chkVal", object);
            chkDupli.execute();
            if (chkDupli.getResult() != null && chkDupli.getResult().toString().equals("Y")) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                              ADFModelUtils.resolvRsrc("MSG.2645"), null));
            }
        }
    }

    public void taxCheckBoxVCE(ValueChangeEvent valueChangeEvent) {
        _log.info("New Value =" + valueChangeEvent.getNewValue());
        OperationBinding binding = ADFBeanUtils.getOperationBinding("updateTAXCheckInSameAccounts");
        binding.getParamsMap().put("chkVal",
                                   valueChangeEvent.getNewValue() != null &&
                                   valueChangeEvent.getNewValue().toString().equals("true") ? "Y" : "N");
        binding.execute();
    }

    public void invcDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            OperationBinding binding = ADFBeanUtils.getOperationBinding("chkInvoiceDateValidate");
            java.sql.Date invcDt = null;
            try {
                invcDt = ((Timestamp) object).dateValue();
            } catch (SQLException e) {
                _log.info("Error in Date Cast");
            }
            _log.info("Binding is =" + binding);
            binding.getParamsMap().put("invcDate", invcDt);
            binding.execute();
            if (binding.getResult() != null && binding.getResult().toString().equals("Y")) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.2646"), null));
            }
        }
    }

    public void rcdDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) throws SQLException {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date rcdDt = null;
            java.sql.Date sysDt = null;
            try {
                rcdDt = ((Timestamp) object).dateValue();
                sysDt = (new Timestamp(System.currentTimeMillis())).dateValue();
                if (rcdDt.compareTo(sysDt) > 0) {
                    if (sysDt.toString().equals(rcdDt.toString())) {
                        //ok
                    } else {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      ADFModelUtils.resolvRsrc("MSG.521"), null));
                    }
                }
            } catch (SQLException e) {
                System.out.println("Exception in cast");
            }
        }


    }

    public void billRequiredCBVCL(ValueChangeEvent valueChangeEvent) {
        _log.info("New Value =" + valueChangeEvent.getNewValue());
        OperationBinding binding = ADFBeanUtils.getOperationBinding("updateBillRequiredInSameAccounts");
        binding.getParamsMap().put("chkVal",
                                   valueChangeEvent.getNewValue() != null &&
                                   valueChangeEvent.getNewValue().toString().equals("true") ? "Y" : "N");
        binding.execute();
    }

    public Boolean chkBeforeSaveAndForward() {
        if (this.callToSaveApplication()) {
        } else {
            return false;
        }
        //check if all bill has been added or not
        OperationBinding chkBillDtl = ADFBeanUtils.getOperationBinding("chkAllBillAddedForExpenses");
        chkBillDtl.execute();
        if (chkBillDtl.getResult() != null && chkBillDtl.getResult().toString().equals("Y")) {
        } else {
            ADFModelUtils.showFormattedFacesMessage(" ", ADFModelUtils.resolvRsrc("MSG.2647"),
                                                    FacesMessage.SEVERITY_INFO);
            return false;
        }

        //check if expense amount is entered for all or not
        OperationBinding chkExpAmt = ADFBeanUtils.getOperationBinding("checkExpenseAmtEnteredOrNot");
        chkExpAmt.execute();
        if (chkExpAmt.getResult() != null && chkExpAmt.getResult().toString().equals("Y")) {
        } else {
            ADFModelUtils.showFormattedFacesMessage("Expense Amount is not Valid.",
                                                    " Please Enter Expense Amount for all Expenses.",
                                                    FacesMessage.SEVERITY_INFO);
            return false;
        }

        //check if Taxable Amount is same or not
        OperationBinding chkTaxTdsAmtOp = ADFBeanUtils.getOperationBinding("checkTaxTdsAmtSameOrNot");
        chkTaxTdsAmtOp.execute();
        if (chkTaxTdsAmtOp.getResult() == null) {
            ADFModelUtils.showFormattedFacesMessage("Something went wrong.", " ", FacesMessage.SEVERITY_INFO);
            return false;
        }
        if (chkTaxTdsAmtOp.getResult().equals("tax")) {
            ADFModelUtils.showFormattedFacesMessage(" ", ADFModelUtils.resolvRsrc("MSG.513"),
                                                    FacesMessage.SEVERITY_INFO);
            return false;
        }
        if (chkTaxTdsAmtOp.getResult().equals("tds")) {
            ADFModelUtils.showFormattedFacesMessage(" ", ADFModelUtils.resolvRsrc("MSG.346"),
                                                    FacesMessage.SEVERITY_INFO);
            return false;
        }

        if (this.enableCostCenter) {
            //Check if Cost Centre is applied or not
            OperationBinding chkCC = ADFBeanUtils.getOperationBinding("checkCostCentreAddedOrNot");
            chkCC.execute();
            if (chkCC.getResult() != null && chkCC.getResult().equals("Y")) {
                ADFModelUtils.showFormattedFacesMessage(" ", "Please Select Cost Center for All Required Expenses.",
                                                        FacesMessage.SEVERITY_INFO);
                return false;
            }
            if (chkCC.getResult() != null && chkCC.getResult().equals("A")) {
                ADFModelUtils.showFormattedFacesMessage(" ", "Amount mismatch between Expenses and Cost Center Amount.",
                                                        FacesMessage.SEVERITY_INFO);
                return false;
            }
        }

        //check if COA Amount is sufficient or not
        OperationBinding chkCoaBal = ADFBeanUtils.getOperationBinding("checkCOABalanceIsAvailable");
        chkCoaBal.execute();
        if (chkCoaBal.getResult() != null && chkCoaBal.getResult().toString().equals("Y")) {

        } else {
            ADFModelUtils.showFormattedFacesMessage("Unable to Forward", "Insufficient Balance for this Cash Account.",
                                                    FacesMessage.SEVERITY_INFO);
            return false;
        }

        return true;
    }


    public void coaBalPopFetchListener(PopupFetchEvent popupFetchEvent) {
        System.out.println("Popup Fetch Listener");
        OperationBinding binding = ADFBeanUtils.getOperationBinding("showCoaBalance");
        binding.execute();
    }
}
