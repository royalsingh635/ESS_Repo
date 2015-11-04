package trnploadingrequestapp.view.bean;

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
import oracle.adf.model.OperationBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;


public class TrnpLoadRequestAddBean {

    private String mode = "V";
    private static ADFLogger _log = ADFLogger.createADFLogger(TrnpLoadRequestAddBean.class);
    private RichInputListOfValues bindCustomerName;

    private RichInputListOfValues bindSourceDoc;
    private RichSelectOneRadio requestTypeBinding;
    private RichInputListOfValues bindTrnpName;
    private RichInputListOfValues bindRouteName;
    private RichSelectOneChoice bindVehicleType;
    private RichSelectOneChoice bindRequestBasis;
    private RichInputListOfValues bindCurrency;
    private RichInputText bindCurrRate;
    private RichSelectBooleanCheckbox bindMultiple;
    private RichInputListOfValues bindCustName;
    private RichPopup productDtlPopupBind;
    private RichInputText wghtFldBind;
    private RichOutputText itmWghtBind;

    public TrnpLoadRequestAddBean() {
        if (ADFModelUtils.resolvEl("#{pageFlowScope.MODE_PARAM}") != null)
            mode = (String) ADFModelUtils.resolvEl("#{pageFlowScope.MODE_PARAM}");

    }


    public void addRequestAL(ActionEvent actionEvent) {
        System.out.println("in add Action listner");
        OperationBinding createOp = (OperationBinding) ADFBeanUtils.getOperationBinding("CreateInsert");
        createOp.execute();
        mode = "A";
    }

    public void editRequestAL(ActionEvent actionEvent) {
        OperationBinding pendingAtOp = (OperationBinding) ADFBeanUtils.getOperationBinding("chkPendingUsr");
        pendingAtOp.execute();
        Integer pendingAt = (Integer) pendingAtOp.getResult();
        if (pendingAt.equals(-1) || pendingAt.equals(EbizParams.GLBL_APP_USR()))
            mode = "A";
        else {
            OperationBinding usrNmOp = (OperationBinding) ADFBeanUtils.getOperationBinding("getUsrNm");
            usrNmOp.getParamsMap().put("usrId", pendingAt);
            pendingAtOp.execute();
            String usrNm = "Anonymous.";
            if (usrNmOp.getResult() != null)
                usrNm = (String) usrNmOp.getResult();
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1163"),
                                                    ADFModelUtils.resolvRsrc("MSG.1294") + " [ " + usrNm + "] " +
                                                    ADFModelUtils.resolvRsrc("MSG.879"), FacesMessage.SEVERITY_INFO);
        }
    }


    public String cancelRequestAction() {
        OperationBinding createOp = (OperationBinding) ADFBeanUtils.getOperationBinding("Rollback");
        createOp.execute();
        mode = "V";
        return "back";
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public Boolean callToSaveApplication() {
        _log.info("Save Method");
        /*OperationBinding reqNoOp = (OperationBinding) ADFBeanUtils.getOperationBinding("setRequestNo");
           reqNoOp.execute();
        if (reqNoOp.getErrors().size() == 0 && reqNoOp.getResult() != null) {
            OperationBinding saveOp = (OperationBinding) ADFBeanUtils.getOperationBinding("Commit");
            saveOp.execute();
            mode = "V";
            ADFModelUtils.showFormattedFacesMessage("Loading Request Saved Successfully.", " ",
                                                    FacesMessage.SEVERITY_INFO);
            return true;
        } else {
            ADFModelUtils.showFormattedFacesMessage("Something went wrong.", " ", FacesMessage.SEVERITY_ERROR);
            return false;
        } */

        OperationBinding saveOp = (OperationBinding) ADFBeanUtils.getOperationBinding("Commit");
        saveOp.execute();

        return true;
    }

    public String addRouteDtlAL() {

        if (requestTypeBinding.getValue() != null) {

            System.out.println("Value of requestTypeBinding.getValue() :" + requestTypeBinding.getValue());

            if (requestTypeBinding.getValue().toString().equalsIgnoreCase("T")) {

                if (bindTrnpName.getValue() == null || bindTrnpName.getValue().toString().length() == 0) {
                    ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), " ",
                                                   FacesMessage.SEVERITY_ERROR, bindTrnpName.getClientId());
                    return null;
                }
            }
        }

        if (bindRouteName.getValue() == null || bindRouteName.getValue().toString().length() == 0) {
            System.out.println("bindRouteName.getValue !!");
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), " ", FacesMessage.SEVERITY_ERROR,
                                           bindRouteName.getClientId());
            return null;
        }

        if (bindVehicleType.getValue() == null || bindVehicleType.getValue().toString().length() == 0) {

            System.out.println("bindVehicleType.getValue !!");
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), " ", FacesMessage.SEVERITY_ERROR,
                                           bindVehicleType.getClientId());
            return null;
        }

        if (bindRequestBasis.getValue() == null || bindRequestBasis.getValue().toString().length() == 0) {
            System.out.println("bindRequestBasis.getValue :: " + bindRequestBasis.getValue());
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), " ", FacesMessage.SEVERITY_ERROR,
                                           bindRequestBasis.getClientId());
            return null;
        }

        if (bindCurrency.getValue() == null || bindCurrency.getValue().toString().length() == 0) {
            System.out.println("bindCurrency.getValue !!");
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), " ", FacesMessage.SEVERITY_ERROR,
                                           bindCurrency.getClientId());
            return null;
        }

        _log.info("CheckBox Value=" + bindMultiple.getValue());
        if (!bindRequestBasis.getValue().toString().equalsIgnoreCase("39")) {
            if (bindMultiple.getValue() == null || bindMultiple.getValue().toString().equals("false") ||
                bindMultiple.getValue().toString().equals("N")) {
                System.out.println("bindMultiple = N AND bindRequestBasis != 39 :: " +
                                   bindRequestBasis.getValue().toString().equalsIgnoreCase("39"));
                if (bindCustName.getValue() == null || bindCustName.getValue().toString().length() == 0) {
                    System.out.println("bindCustName.getValue !!");
                    ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), " ",
                                                   FacesMessage.SEVERITY_ERROR, bindCustName.getClientId());
                    return null;
                }

            }
        }

        System.out.println("addRouteDtlAL::: is called");
        OperationBinding opAddRouteDtl = (OperationBinding) ADFBeanUtils.getOperationBinding("populateRouteDtl");
        opAddRouteDtl.execute();
        /*   OperationBinding opAddRouteOc = (OperationBinding) ADFBeanUtils.getOperationBinding("populateRouteOc");
        opAddRouteOc.execute(); */

        return null;
    }

    public void addCustomerName(ActionEvent actionEvent) {
        if (bindCustomerName.getValue() != null && bindCustomerName.getValue().toString().length() > 0) {
            OperationBinding validateCust = (OperationBinding) ADFBeanUtils.getOperationBinding("validateCustomer");
            validateCust.execute();

            String res = (String) validateCust.getResult();
            System.out.println("result is:::::::" + res);

            if (res.equalsIgnoreCase("N")) {

                FacesMessage Message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.375"));
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(bindCustomerName.getClientId(), Message);
            }

            else {
                OperationBinding addCust = (OperationBinding) ADFBeanUtils.getOperationBinding("CreateInsertCustomer");
                addCust.execute();
                OperationBinding setCust = (OperationBinding) ADFBeanUtils.getOperationBinding("setValueInCustomer");
                setCust.execute();
                bindCustomerName.setValue(null);
            }
        } else {
            FacesMessage Message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.508"));
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(bindCustomerName.getClientId(), Message);
        }
    }

    public void deleteCustomerAL(ActionEvent actionEvent) {
        OperationBinding delCust = (OperationBinding) ADFBeanUtils.getOperationBinding("deleteCustomer");
        delCust.execute();
    }

    public void departureDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /*  if (object != null && object.toString().length() > 0) {
            java.sql.Date depDt = null;
            try {
                depDt = ((Timestamp) object).dateValue();
            } catch (SQLException e) {
                System.out.println("Exception in cast");
            }

            OperationBinding opChkdt = ADFBeanUtils.findOperation("validateEffectiveDt");
            opChkdt.getParamsMap().put("effctvDt", effDt);
            opChkdt.execute();
            if (opChkdt.getErrors().size() == 0 && opChkdt.getResult() != null) {
                if (opChkdt.getResult().toString().equals("Y")) {

                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Effective Date.", null));
                    //  ADFModelUtils.showFacesMessage("Rate Contract for this Customer Already exist.", " ", FacesMessage.SEVERITY_ERROR, clientId);

                }
            }
        } */



    }


    public void addSourceDocumentAL(ActionEvent actionEvent) {
        if (bindSourceDoc.getValue() != null && bindSourceDoc.getValue().toString().length() > 0) {
            System.out.println("in add action listner");

            OperationBinding validateDocSrc = (OperationBinding) ADFBeanUtils.getOperationBinding("ValidateDocSrc");
            validateDocSrc.execute();

            String res = validateDocSrc.getResult().toString();

            System.out.println("res is:::::" + res);

            if (res.equalsIgnoreCase("N")) {

                FacesMessage Message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.375"));
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(bindSourceDoc.getClientId(), Message);
            }

            else {
                OperationBinding addDocSrc = (OperationBinding) ADFBeanUtils.getOperationBinding("CreateInsertDocSrc");
                addDocSrc.execute();
                OperationBinding addDocItmDtl =
                    (OperationBinding) ADFBeanUtils.getOperationBinding("populateItemFromSourceDoc");
                addDocItmDtl.execute();
                bindSourceDoc.setValue(null);
            }
        } else {
            FacesMessage Message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.508"));
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(bindSourceDoc.getClientId(), Message);
        }
    }


    public void deleteDocSrcAL(ActionEvent actionEvent) {
        OperationBinding delDoc = (OperationBinding) ADFBeanUtils.getOperationBinding("deleteDocumentSrc");
        delDoc.execute();
    }


    public String saveAction() {
        OperationBinding ob =
            (OperationBinding) BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("MandatoryDate");
        ob.execute();
        Object object = ob.getResult();
        String string = object.toString();
        System.out.println("string is:::" + string);
        if (string.equalsIgnoreCase("N")) {


            FacesMessage Message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1991"));
            Message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, Message);
            return null;
        }

        else {

            Boolean valid = this.callToSaveApplication();

            if (valid) {

                ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.91"), " ",
                                                        FacesMessage.SEVERITY_INFO);

                //Call for WF
                String action = "I";
                String remark = "A";

                OperationBinding WfnoOp = (OperationBinding) ADFBeanUtils.findOperation("getWfNo");
                WfnoOp.execute();

                String WfNum = null;
                Integer level = 0;
                Integer res = -1;

                if (WfnoOp.getResult() != null) {
                    WfNum = WfnoOp.getResult().toString();
                }
                if (WfNum != null) {
                    OperationBinding usrLevelOp = (OperationBinding) ADFBeanUtils.findOperation("currUsrLvl");
                    usrLevelOp.getParamsMap().put("WfNum", WfNum);
                    usrLevelOp.execute();
                    level = -1;
                    if (usrLevelOp.getResult() != null) {
                        if (usrLevelOp.getResult() != null)
                            level = Integer.parseInt(usrLevelOp.getResult().toString());
                    }

                    System.out.println("Level :: " + level);

                    if (level == -1) {
                        ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1506"), " ",
                                                                FacesMessage.SEVERITY_ERROR);
                    } else {
                        OperationBinding insTxnOp = (OperationBinding) ADFBeanUtils.findOperation("insWfTxn");
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
                            OperationBinding operationBinding = (OperationBinding) ADFBeanUtils.findOperation("Commit");
                            operationBinding.execute();
                            mode = "V";

                        } else {
                            _log.info("error during insert to WF");
                        }
                        _log.info(level + "level--res" + res);
                    }
                } else {
                    FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1486"));
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                    ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1486"), " ",
                                                            FacesMessage.SEVERITY_ERROR);

                }

            }
        }

        return null;


    }


    public String saveAndFwdAction() {

        OperationBinding ob =
            (OperationBinding) BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("MandatoryDate");
        ob.execute();

        Object object = ob.getResult();

        String string = object.toString();

        System.out.println("string is:::" + string);

        if (string.equalsIgnoreCase("N")) {


            FacesMessage Message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1991"));
            Message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, Message);
            return null;
        }

        else {

            Boolean valid = this.callToSaveApplication();

            if (valid) {

                mode = "V";

                OperationBinding prodDtlOp = (OperationBinding) ADFBeanUtils.findOperation("chkProdDtlRow");
                prodDtlOp.execute();

                Boolean prodDtlRowCount = (Boolean) prodDtlOp.getResult();
                System.out.println("Returned Value =" + prodDtlRowCount);
                if (prodDtlRowCount != null && !prodDtlRowCount) {
                    //show msg

                    System.out.println("Inside true condition :: ");
                    /* ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1992"),
                                                            " ", FacesMessage.SEVERITY_WARN);  */



                } else {
                    //Workflow Start
                    String action = "I";
                    String remark = "A";

                    OperationBinding WfnoOp = (OperationBinding) ADFBeanUtils.findOperation("getWfNo");
                    WfnoOp.execute();

                    String WfNum = null;
                    Integer level = 0;
                    Integer res = -1;

                    if (WfnoOp.getResult() != null) {
                        WfNum = WfnoOp.getResult().toString();
                    }
                    if (WfNum != null) {
                        OperationBinding usrLevelOp = (OperationBinding) ADFBeanUtils.findOperation("currUsrLvl");
                        usrLevelOp.getParamsMap().put("WfNum", WfNum);
                        usrLevelOp.execute();
                        level = -1;
                        if (usrLevelOp.getResult() != null) {
                            if (usrLevelOp.getResult() != null)
                                level = Integer.parseInt(usrLevelOp.getResult().toString());
                        }
                        if (level == -1) {
                            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1506"), " ",
                                                                    FacesMessage.SEVERITY_ERROR);
                        } else {
                            OperationBinding insTxnOp = (OperationBinding) ADFBeanUtils.findOperation("insWfTxn");
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
                                OperationBinding operationBinding =
                                    (OperationBinding) ADFBeanUtils.findOperation("Commit");
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

                    }

                }
            }
        }
        //Workflow end
        System.out.println("-----------------------------------------------------------------------------");
        return null;
    }

    public void addOCAL(ActionEvent actionEvent) {
        OperationBinding addOc = (OperationBinding) ADFBeanUtils.findOperation("CreateInsertOC");
        addOc.execute();
        OperationBinding populateOc = (OperationBinding) ADFBeanUtils.findOperation("addOcFromVW");
        populateOc.execute();
    }

    public void deleteOCAL(ActionEvent actionEvent) {
        OperationBinding delOc = (OperationBinding) ADFBeanUtils.findOperation("DeleteOC");
        delOc.execute();
    }

    public void setBindCustomerName(RichInputListOfValues bindCustomerName) {
        this.bindCustomerName = bindCustomerName;
    }

    public RichInputListOfValues getBindCustomerName() {
        return bindCustomerName;
    }


    public void setBindSourceDoc(RichInputListOfValues bindSourceDoc) {
        this.bindSourceDoc = bindSourceDoc;
    }

    public RichInputListOfValues getBindSourceDoc() {
        return bindSourceDoc;
    }

    public void arrivalDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        /*  if (object != null) {

            oracle.jbo.domain.Timestamp t = null;

            try {
                t = (Timestamp) object.t = ((Timestamp) object).dateValue();
            } catch (Exception e) {
                System.out.println("Exception in cast");
            }

        } */


    }

    public void amountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("in amount validator");
        Number Amt = new Number(0);
        Number amount = (Number) object;
        if (amount == null) {
            return;
        }
        if (amount.floatValue() <= Amt.floatValue()) {
            String error = ADFModelUtils.resolvRsrc("MSG.1063");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
        }

        if (!ADFBeanUtils.isPrecisionValid(26, 6, amount)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          ADFModelUtils.resolvRsrc("MSG.1107"), null));

        }
    }


    public void itmWeightValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number wt = (Number) object;
            if (wt.compareTo(new Number(0)) <= 0) {
                String error = ADFModelUtils.resolvRsrc("MSG.1063");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }

            if (!ADFBeanUtils.isPrecisionValid(26, 6, wt)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1107"), null));

            }
        }
    }

    public void setRequestTypeBinding(RichSelectOneRadio requestTypeBinding) {
        this.requestTypeBinding = requestTypeBinding;
    }

    public RichSelectOneRadio getRequestTypeBinding() {
        return requestTypeBinding;
    }

    public void setBindTrnpName(RichInputListOfValues bindTrnpName) {
        this.bindTrnpName = bindTrnpName;
    }

    public RichInputListOfValues getBindTrnpName() {
        return bindTrnpName;
    }

    public void setBindRouteName(RichInputListOfValues bindRouteName) {
        this.bindRouteName = bindRouteName;
    }

    public RichInputListOfValues getBindRouteName() {
        return bindRouteName;
    }

    public void setBindVehicleType(RichSelectOneChoice bindVehicleType) {
        this.bindVehicleType = bindVehicleType;
    }

    public RichSelectOneChoice getBindVehicleType() {
        return bindVehicleType;
    }

    public void setBindRequestBasis(RichSelectOneChoice bindRequestBasis) {
        this.bindRequestBasis = bindRequestBasis;
    }

    public RichSelectOneChoice getBindRequestBasis() {
        return bindRequestBasis;
    }

    public void setBindCurrency(RichInputListOfValues bindCurrency) {
        this.bindCurrency = bindCurrency;
    }

    public RichInputListOfValues getBindCurrency() {
        return bindCurrency;
    }

    public void setBindCurrRate(RichInputText bindCurrRate) {
        this.bindCurrRate = bindCurrRate;
    }

    public RichInputText getBindCurrRate() {
        return bindCurrRate;
    }

    public void setBindMultiple(RichSelectBooleanCheckbox bindMultiple) {
        this.bindMultiple = bindMultiple;
    }

    public RichSelectBooleanCheckbox getBindMultiple() {
        return bindMultiple;
    }

    public void setBindCustName(RichInputListOfValues bindCustName) {
        this.bindCustName = bindCustName;
    }

    public RichInputListOfValues getBindCustName() {
        return bindCustName;
    }

    public void changeArrivalDateVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {

            Object date = vce.getNewValue();
            try {
                Date arrivalDate = ((Timestamp) date).dateValue();
                System.out.println("ArrivalDate in bean VCL ::" + vce.getNewValue());
                OperationBinding ob = (OperationBinding) ADFBeanUtils.findOperation("changeArrivalDate");
                ob.getParamsMap().put("arrvlDate", arrivalDate);
                ob.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void custRateCntrctValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (this.bindRequestBasis.getValue() != null && this.bindRequestBasis.getValue().toString().length() > 0) {
            System.out.println("Value of bindRequestBasis in Rate Contract validator :: " +
                               bindRequestBasis.getValue());

            if (bindRequestBasis.getValue().toString().equalsIgnoreCase("39")) {
            } else {
                if ((object != null) && (object.toString().length() > 0)) {
                    String custName = (String) object;
                    OperationBinding op = (OperationBinding) ADFBeanUtils.findOperation("custRateCntrctValidator");
                    op.getParamsMap().put("custName", custName);
                    op.execute();
                    Boolean result = (Boolean) op.getResult();

                    if (result != null) {
                        if (!result)
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          ADFModelUtils.resolvRsrc("MSG.1993"), null));
                    }

                }
            }
        } else {
            System.out.println("Please select Request Basis !! ");
        }
    }

    public void lrBasisVCL(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("39")) {
                this.bindMultiple.setValue(false);
            }
        }

    }

    public void updatePopupAL(ActionEvent actionEvent) {

        System.out.println("Inside update Popup AL !!");

        OperationBinding ob = (OperationBinding) ADFBeanUtils.findOperation("chkAllItemsWeight");
        ob.execute();
        Boolean result = (Boolean) ob.getResult();

        System.out.println("ob.getResult() = " + result);

        if (result != null) {
            System.out.println("Inside result not null condition..");

            if (result || mode.equalsIgnoreCase("V")) {
                System.out.println("Popup hide called !!");
                productDtlPopupBind.hide();
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(wghtFldBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmWghtBind);

    }

    public void setProductDtlPopupBind(RichPopup productDtlPopupBind) {
        this.productDtlPopupBind = productDtlPopupBind;
    }

    public RichPopup getProductDtlPopupBind() {
        return productDtlPopupBind;
    }

    public void setWghtFldBind(RichInputText wghtFldBind) {
        this.wghtFldBind = wghtFldBind;
    }

    public RichInputText getWghtFldBind() {
        return wghtFldBind;
    }

    public void setItmWghtBind(RichOutputText itmWghtBind) {
        this.itmWghtBind = itmWghtBind;
    }

    public RichOutputText getItmWghtBind() {
        return itmWghtBind;
    }

    public void ocAmtVCL(ValueChangeEvent valueChangeEvent) {
        Number amtSp = new Number(0);
        if (valueChangeEvent.getNewValue() != null) {
            amtSp = (Number) valueChangeEvent.getNewValue();
            Number oldVal = new Number(0);
            if (valueChangeEvent.getOldValue() != null) {
                oldVal = (Number) valueChangeEvent.getOldValue();
            }
            int roundDigit = 0;
            if (ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}").toString() != null)
                roundDigit = Integer.parseInt(ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}").toString());
            if (amtSp.compareTo(oldVal.round(roundDigit)) == 0) {
            } else {
                OperationBinding ob = (OperationBinding) ADFBeanUtils.findOperation("updtOcBsAmt");
                ob.getParamsMap().put("amtSp", amtSp);
                ob.execute();
            }
        }
        /*
        Number rate = new Number(0);
        if (this.getCurrRate() != null)
            rate = (Number) this.getCurrRate().round(6);
        Number bs = (Number) rate.multiply(value.round(6));
        this.setAmtBs(new Number(bs.round(6)));
         */
    }
}
