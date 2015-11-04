package trnpfuelbillapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Timestamp;


public class TrnpFuelBillAddBean {
    private String mode = "V";
    private static ADFLogger _log = ADFLogger.createADFLogger(TrnpFuelBillAddBean.class);

    private RichInputListOfValues bindStationName;
    private RichInputListOfValues bindCurrency;
    private RichPanelLabelAndMessage bindInvoiceDateRange;
    private RichInputDate bindInvcStrtDt;
    private RichInputDate bindInvcEndDt;

    public TrnpFuelBillAddBean() {
        if (ADFModelUtils.resolvEl("#{pageFlowScope.MODE_PARAM}") != null)
            mode = (String) ADFModelUtils.resolvEl("#{pageFlowScope.MODE_PARAM}");
    }


    public String addFuelBillACTION() {
        OperationBinding createOp = ADFBeanUtils.getOperationBinding("CreateInsert");
        createOp.execute();
        mode = "A";
        return null;
    }

    public String editFuelBillACTION() {
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
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("#{bundle['MSG.1163']}"),
                                                    ADFModelUtils.resolvRsrc("#{bundle['MSG.1294']}")+" [ " + usrNm + "] ",
                                                    FacesMessage.SEVERITY_INFO);
            /* ADFModelUtils.showFormattedFacesMessage("Edit Not Allowed.",
                                                    "This document is Pending at [ " + usrNm + "].",
                                                    FacesMessage.SEVERITY_INFO); */
        }
        return null;
    }

    public String saveFuelBillACTION() {
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
            if (WfNum != null) {
                OperationBinding usrLevelOp = ADFBeanUtils.findOperation("currUsrLvl");
                usrLevelOp.getParamsMap().put("WfNum", WfNum);
                usrLevelOp.execute();
                level = -1;
                if (usrLevelOp.getResult() != null) {
                    if (usrLevelOp.getResult() != null)
                        level = Integer.parseInt(usrLevelOp.getResult().toString());
                }
                if (level == -1) {
                    ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.2011"),
                                                            " ", FacesMessage.SEVERITY_ERROR);
                    /*  ADFModelUtils.showFormattedFacesMessage("Something went wrong while getting Workflow Level of this user.Contact to ESS.",
                                                            " ", FacesMessage.SEVERITY_ERROR); */
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
                    } else {
                        _log.info("error during insert to WF");
                    }
                    _log.info(level + "level--res" + res);
                }
            } else {
                FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1486"));
                //FacesMessage message = new FacesMessage("Workflow not Defined for this Document.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1486"), " ",
                                                        FacesMessage.SEVERITY_ERROR);
                /* ADFModelUtils.showFormattedFacesMessage("Workflow not Defined for this Document.", " ",
                                                        FacesMessage.SEVERITY_ERROR); */

            }

        }
        return null;
    }

    public String cancelButtonACTION() {
        OperationBinding createOp = ADFBeanUtils.getOperationBinding("Rollback");
        createOp.execute();
        mode = "V";
        return "back";
    }

    public String saveAndFwdFuelBillACTION() {
        Boolean valid = this.callToSaveApplication();
        if (valid) {
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
            if (WfNum != null) {
                OperationBinding usrLevelOp = ADFBeanUtils.findOperation("currUsrLvl");
                usrLevelOp.getParamsMap().put("WfNum", WfNum);
                usrLevelOp.execute();
                level = -1;
                if (usrLevelOp.getResult() != null) {
                    if (usrLevelOp.getResult() != null)
                        level = Integer.parseInt(usrLevelOp.getResult().toString());
                }
                if (level == -1) {
                    ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.2011"),
                                                            " ", FacesMessage.SEVERITY_ERROR);
                    /*  ADFModelUtils.showFormattedFacesMessage("Something went wrong while getting Workflow Level of this user.Contact to ESS.",
                                                            " ", FacesMessage.SEVERITY_ERROR); */
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
                                                        FacesMessage.SEVERITY_ERROR);
                /* ADFModelUtils.showFormattedFacesMessage("Workflow not Defined for this Document.", " ",
                                                        FacesMessage.SEVERITY_ERROR); */

            }

        }
        //Workflow end

        return null;
    }

    public String populateBillDetailACTION() {

        System.out.println("in populate bill detail action");

        if (bindStationName.getValue() == null || bindStationName.getValue().toString().length() == 0) {

            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), " ", FacesMessage.SEVERITY_ERROR,
                                           bindStationName.getClientId());
            /* ADFModelUtils.showFacesMessage("please select Station name", " ", FacesMessage.SEVERITY_ERROR,
                                           bindStationName.getClientId()); */
            return null;
        }

        if (bindStationName.getValue() != null) {
            OperationBinding obChkCoa = ADFBeanUtils.findOperation("chkCoaExist");
            obChkCoa.execute();
            if (obChkCoa.getResult() != null && obChkCoa.getResult().toString().equals("Y")) {
            } else {
                ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2227"),
                                               ADFModelUtils.resolvRsrc("MSG.2228")+"\n "+ADFModelUtils.resolvRsrc("MSG.2227"),
                                               FacesMessage.SEVERITY_ERROR, bindStationName.getClientId());
                /* ADFModelUtils.showFacesMessage("Please Create COA.",
                                               "COA for this Station name Does not Exist. Please Create COA For thIs Supplier Station.",
                                               FacesMessage.SEVERITY_ERROR, bindStationName.getClientId()); */
                return null;
            }
        }

        if (bindInvcStrtDt.getValue() == null || bindInvcStrtDt.getValue().toString().length() == 0) {
            
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), " ", FacesMessage.SEVERITY_ERROR,
                                           bindInvoiceDateRange.getClientId());
            /* ADFModelUtils.showFacesMessage("Please Select Invoice Date Range", " ", FacesMessage.SEVERITY_ERROR,
                                           bindInvoiceDateRange.getClientId()); */
            return null;

        }
        if (bindInvcEndDt.getValue() == null || bindInvcEndDt.getValue().toString().length() == 0) {

            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), " ", FacesMessage.SEVERITY_ERROR,
                                           bindInvoiceDateRange.getClientId());
            /* ADFModelUtils.showFacesMessage("Please Select Invoice Date Range", " ", FacesMessage.SEVERITY_ERROR,
                                           bindInvoiceDateRange.getClientId()); */
            return null;

        }

        if (bindCurrency.getValue() == null || bindCurrency.getValue().toString().length() == 0) {

            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), " ", FacesMessage.SEVERITY_ERROR,
                                           bindCurrency.getClientId());
            /* ADFModelUtils.showFacesMessage("please select currency name", " ", FacesMessage.SEVERITY_ERROR,
                                           bindCurrency.getClientId()); */
            return null;
        }


        //  OperationBinding opvalidateDt = ADFBeanUtils.getOperationBinding("DuplicateRecordValidation");
        //  opvalidateDt.execute();

        //  Boolean result = (Boolean) opvalidateDt.getResult();
        Boolean result = false;
        if (result != null) {
            if (result) {
                ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1202"), " ", FacesMessage.SEVERITY_ERROR);
                //ADFModelUtils.showFormattedFacesMessage("Record is already inserted", " ", FacesMessage.SEVERITY_ERROR);
                return null;

            }
        }

        OperationBinding opAddDtl = ADFBeanUtils.getOperationBinding("populateFuelDtl");
        opAddDtl.execute();
        if (opAddDtl.getResult() != null && opAddDtl.getResult().toString().equals("N"))
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.2226"), " ",
                                                    FacesMessage.SEVERITY_INFO);
            /* ADFModelUtils.showFormattedFacesMessage("No Fuel Bill is Pending from this Supplier.", " ",
                                                    FacesMessage.SEVERITY_INFO); */


        return null;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public Boolean callToSaveApplication() {
        _log.info("Save Method");
        OperationBinding reqNoOp = ADFBeanUtils.getOperationBinding("setRequestNo");
        reqNoOp.execute();
        if (reqNoOp.getErrors().size() == 0 && reqNoOp.getResult() != null) {
            OperationBinding saveOp = ADFBeanUtils.getOperationBinding("Commit");
            saveOp.execute();
            mode = "V";
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.91"), " ", FacesMessage.SEVERITY_INFO);
            //ADFModelUtils.showFormattedFacesMessage("Fuel Bill Saved Successfully.", " ", FacesMessage.SEVERITY_INFO);
            return true;
        } else {
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.91"), " ", FacesMessage.SEVERITY_INFO);
            //ADFModelUtils.showFormattedFacesMessage("Something went wrong.", " ", FacesMessage.SEVERITY_INFO);
            return false;
        }

    }

    public void setBindStationName(RichInputListOfValues bindStationName) {
        this.bindStationName = bindStationName;
    }

    public RichInputListOfValues getBindStationName() {
        return bindStationName;
    }


    public void setBindCurrency(RichInputListOfValues bindCurrency) {
        this.bindCurrency = bindCurrency;
    }

    public RichInputListOfValues getBindCurrency() {
        return bindCurrency;
    }

    public void setBindInvoiceDateRange(RichPanelLabelAndMessage bindInvoiceDateRange) {
        this.bindInvoiceDateRange = bindInvoiceDateRange;
    }

    public RichPanelLabelAndMessage getBindInvoiceDateRange() {
        return bindInvoiceDateRange;
    }

    public void setBindInvcStrtDt(RichInputDate bindInvcStrtDt) {
        this.bindInvcStrtDt = bindInvcStrtDt;
    }

    public RichInputDate getBindInvcStrtDt() {
        return bindInvcStrtDt;
    }

    public void setBindInvcEndDt(RichInputDate bindInvcEndDt) {
        this.bindInvcEndDt = bindInvcEndDt;
    }

    public RichInputDate getBindInvcEndDt() {
        return bindInvcEndDt;
    }

    public void invcEndDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0 && bindInvcStrtDt.getValue() != null) {
            java.sql.Date invcEndDt = null;
            java.sql.Date invcStrtDt = null;
            try {
                invcEndDt = ((Timestamp) object).dateValue();
                invcStrtDt = ((Timestamp) bindInvcStrtDt.getValue()).dateValue();
                if (invcStrtDt.compareTo(invcEndDt) > 0) {
                    if (invcStrtDt.toString().equals(invcEndDt.toString())) {
                        //ok
                    } else {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      ADFModelUtils.resolvRsrc("MSG.521"), null));
                        /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Invalid Date Range.", null)); */
                    }
                }
            } catch (SQLException e) {
                System.out.println("Exception in cast");
            }
        }

    }
}

