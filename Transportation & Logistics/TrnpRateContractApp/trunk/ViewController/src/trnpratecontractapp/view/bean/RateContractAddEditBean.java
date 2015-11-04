package trnpratecontractapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;


public class RateContractAddEditBean {
    private String mode = "V";
    private static ADFLogger _log = ADFLogger.createADFLogger(RateContractAddEditBean.class);
    private RichInputListOfValues custNmBinding;
    private RichInputDate effectvDtBinding;
    private RichInputText rateBinding;
    private RichInputListOfValues unitBinding;
    private RichSelectOneRadio rateBasisBinding;
    private RichSelectOneChoice vehicleTypeIdBind;
    private RichInputListOfValues currBinding;
    private RichTable itmTableBinding;
    private RichInputListOfValues rateUnitBinding;
    private RichInputText minDistRateBinding;

    //comment
    public RateContractAddEditBean() {
    }

    public String addRateContractAction() {
        OperationBinding createOp = ADFBeanUtils.getOperationBinding("CreateInsert");
        createOp.execute();
        OperationBinding genTxnIdOp = ADFBeanUtils.getOperationBinding("genTxnId");
        genTxnIdOp.execute();
        mode = "A";
        return null;
    }

    public String editRateContractAction() {
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
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1163"),
                                                    ADFModelUtils.resolvRsrc("MSG.1294")+" [ " + usrNm + "] "+ADFModelUtils.resolvRsrc("MSG.879"),
                                                    FacesMessage.SEVERITY_INFO);
        }
        return null;
    }

    public String saveAction() {
        Boolean valid = this.callToSaveApplication();
        if (valid) {
            OperationBinding createOp = ADFBeanUtils.getOperationBinding("Commit");
            createOp.execute();
            mode = "V";
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.91"), " ",
                                                    FacesMessage.SEVERITY_INFO);
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
                    ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1506"),
                                                            " ", FacesMessage.SEVERITY_ERROR);
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
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1486"), " ",
                                                        FacesMessage.SEVERITY_ERROR);

            }

        }
        return null;
    }

    public Boolean callToSaveApplication() {
        _log.info("Validate Fields");
        if (custNmBinding.getValue() != null && custNmBinding.getValue().toString().length() > 0) {
            System.out.println("CUSTNAME BIND IS" + custNmBinding.getValue());
            if (vehicleTypeIdBind.getValue() != null && vehicleTypeIdBind.getValue().toString().length() > 0) {
                System.out.println("zzzzzzzz" + vehicleTypeIdBind.getValue());
                if (effectvDtBinding.getValue() != null && effectvDtBinding.getValue().toString().length() > 0) {
                    System.out.println("xxxxxxx" + effectvDtBinding.getValue());
                    if (rateBasisBinding.getValue().toString().equals("F") ?
                        (rateBinding.getValue() != null && rateBinding.getValue().toString().length() > 0) ? true :
                        false : true) {
                        if (rateBasisBinding.getValue().toString().equals("F") ?
                            (rateUnitBinding.getValue() != null && rateUnitBinding.getValue().toString().length() > 0) ?
                            true : false : true) {
                            if (currBinding.getValue() != null && currBinding.getValue().toString().length() > 0) {
                                return true;
                            } else {
                                ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), " ",
                                                               FacesMessage.SEVERITY_ERROR, currBinding.getClientId());
                                return false;
                            }
                        } else {
                            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), " ", FacesMessage.SEVERITY_ERROR,
                                                           rateUnitBinding.getClientId());
                            return false;
                        }
                    } else {
                        ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), " ", FacesMessage.SEVERITY_ERROR,
                                                       rateBinding.getClientId());
                        return false;
                    }
                } else {
                    ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), " ", FacesMessage.SEVERITY_ERROR,
                                                   effectvDtBinding.getClientId());
                    return false;
                }
            } else {
                ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), " ", FacesMessage.SEVERITY_ERROR,
                                               vehicleTypeIdBind.getClientId());
                return false;
            }
        } else {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), " ", FacesMessage.SEVERITY_ERROR,
                                           custNmBinding.getClientId());
            return false;
        }
    }

    public String cancelAction() {
        OperationBinding createOp = ADFBeanUtils.getOperationBinding("Rollback");
        createOp.execute();
        mode = "V";
        return null;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public String addProductAction() {
        if (this.callToSaveApplication()) {
            OperationBinding createOp = ADFBeanUtils.getOperationBinding("CreateInsert1");
            createOp.execute();
        }
        return null;
    }

    public String deleteProductAction() {
        System.out.println("in delete method");
        OperationBinding createOp = ADFBeanUtils.getOperationBinding("Delete");
        createOp.execute();
        return null;
    }

    public String amendContractAction() {
        OperationBinding amendOp = ADFBeanUtils.getOperationBinding("amendRateContract");
        amendOp.execute();
        mode = "A";
        return null;
    }

    public String saveAndFwdAction() {
        Boolean valid = this.callToSaveApplication();
        if (valid) {
            OperationBinding createOp = ADFBeanUtils.getOperationBinding("Commit");
            createOp.execute();
            mode = "V";
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
                    ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1506"),
                                                            " ", FacesMessage.SEVERITY_ERROR);
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

            }

        }
        //Workflow end

        return null;
    }

    public void productNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            OperationBinding chkItmOp = ADFBeanUtils.findOperation("chkDuplicateItmNm");
            chkItmOp.getParamsMap().put("itmNm", object);
            chkItmOp.execute();
            if (chkItmOp.getErrors().size() == 0 && chkItmOp.getResult() != null) {
                if (chkItmOp.getResult().toString().equals("Y")) {

                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  ADFModelUtils.resolvRsrc("MSG.1202"), null));
                    //  ADFModelUtils.showFacesMessage("Rate Contract for this Customer Already exist.", " ", FacesMessage.SEVERITY_ERROR, clientId);

                }
            }
        }


    }

    public void effectiveDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            OperationBinding opChkdt = ADFBeanUtils.findOperation("validateEffectiveDt");
            java.sql.Date effDt = null;
            try {
                effDt = ((Timestamp) object).dateValue();
            } catch (SQLException e) {
                System.out.println("Exception in cast");
            }
            opChkdt.getParamsMap().put("effctvDt", effDt);
            opChkdt.execute();
            if (opChkdt.getErrors().size() == 0 && opChkdt.getResult() != null) {
                if (opChkdt.getResult().toString().equals("Y")) {

                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  ADFModelUtils.resolvRsrc("MSG.521"), null));
                    //  ADFModelUtils.showFacesMessage("Rate Contract for this Customer Already exist.", " ", FacesMessage.SEVERITY_ERROR, clientId);

                }
            }


        }
    }

    public void customerNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0 && vehicleTypeIdBind.getValue() != null) {
            System.out.println("Inside validator in cust bean");
            OperationBinding chkCustOp = ADFBeanUtils.findOperation("chkDuplicateCustomerNm");
            chkCustOp.getParamsMap().put("custNm", object);
            chkCustOp.getParamsMap().put("vehId", vehicleTypeIdBind.getValue());
            chkCustOp.execute();
            if (chkCustOp.getErrors().size() == 0 && chkCustOp.getResult() != null) {
                if (chkCustOp.getResult().toString().equals("Y")) {

                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  ADFModelUtils.resolvRsrc("MSG.1988"),
                                                                  null));
                    //  ADFModelUtils.showFacesMessage("Rate Contract for this Customer Already exist.", " ", FacesMessage.SEVERITY_ERROR, clientId);

                }
            }
        }
    }


    public void vehicleValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null && custNmBinding.getValue() != null) {
            System.out.println("Inside validator in vhcl bean");
            OperationBinding op = ADFBeanUtils.findOperation("chkDuplicateCustomerNm");
            op.getParamsMap().put("vehId", object);
            op.getParamsMap().put("custNm",
                                  custNmBinding.getValue() == null ? " " : custNmBinding.getValue().toString());
            op.execute();
            if (op.getErrors().size() == 0 && op.getResult() != null) {
                if (op.getResult().toString().equals("Y")) {

                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  ADFModelUtils.resolvRsrc("MSG.1988"),
                                                                  null));

                }
            }

        }
    }


    public void resetSearchAL(ActionEvent actionEvent) {
        OperationBinding resetOp = ADFBeanUtils.findOperation("resetSearchFields");
        resetOp.execute();
    }


    public void setCustNmBinding(RichInputListOfValues custNmBinding) {
        this.custNmBinding = custNmBinding;
    }

    public RichInputListOfValues getCustNmBinding() {
        return custNmBinding;
    }

    public void setEffectvDtBinding(RichInputDate effectvDtBinding) {
        this.effectvDtBinding = effectvDtBinding;
    }

    public RichInputDate getEffectvDtBinding() {
        return effectvDtBinding;
    }

    public void setRateBinding(RichInputText rateBinding) {
        this.rateBinding = rateBinding;
    }

    public RichInputText getRateBinding() {
        return rateBinding;
    }

    public void setUnitBinding(RichInputListOfValues unitBinding) {
        this.unitBinding = unitBinding;
    }

    public RichInputListOfValues getUnitBinding() {
        return unitBinding;
    }

    public void setRateBasisBinding(RichSelectOneRadio rateBasisBinding) {
        this.rateBasisBinding = rateBasisBinding;
    }

    public RichSelectOneRadio getRateBasisBinding() {
        return rateBasisBinding;
    }

    public void addOtherChargesACL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("CreateInsertInOc").execute();
        ADFBeanUtils.findOperation("addOtherCharges").execute();
        /* UIComponent componentVal = actionEvent.getComponent();
    oracle.adf.view.rich.util.ResetUtils.reset(componentVal);
    Object objectVal = componentVal.getAttributes().get("oc_id");
    String oc_id = objectVal.toString();
    System.out.println("--------------------------------"+oc_id);
    if(oc_id != null){
        if(chkValidateRows(oc_id)){
        ADFBeanUtils.findOperation("CreateInsertInOc").execute();
        ADFBeanUtils.findOperation("addOtherCharges").execute();
        }
        else {
            FacesMessage Message = new FacesMessage("Row already added");
            Message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, Message);
        }
    } */
    }

    public Boolean chkValidateRows(String OcId) {
        System.out.println("__" + OcId);
        OperationBinding binding = ADFBeanUtils.findOperation("validateRows");
        binding.getParamsMap().put("ocId", OcId);
        binding.execute();
        Integer val = (Integer) binding.getResult();
        System.out.println("----------------" + val);
        if (val == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void delete(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("Deleting mode-------0-------");
        OperationBinding binding = ADFBeanUtils.findOperation("deleteMethod");

        binding.execute();

    }


    public void setVehicleTypeIdBind(RichSelectOneChoice vehicleTypeIdBind) {
        this.vehicleTypeIdBind = vehicleTypeIdBind;
    }

    public RichSelectOneChoice getVehicleTypeIdBind() {
        return vehicleTypeIdBind;
    }

    public void currencyValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        /*   String currency = (String) object;
        if (currency != null || currency.length() > 0) {
            System.out.println("currency validation method");
            return;
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Currency field can not be null.", null));

        }  */



    }

    public void setCurrBinding(RichInputListOfValues currBinding) {
        this.currBinding = currBinding;
    }

    public RichInputListOfValues getCurrBinding() {
        return currBinding;
    }

    public void itmNameVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBinding);
    }


    public void setItmTableBinding(RichTable itmTableBinding) {
        this.itmTableBinding = itmTableBinding;
    }

    public RichTable getItmTableBinding() {
        return itmTableBinding;
    }

    public void setRateUnitBinding(RichInputListOfValues rateUnitBinding) {
        this.rateUnitBinding = rateUnitBinding;
    }

    public RichInputListOfValues getRateUnitBinding() {
        return rateUnitBinding;
    }

    public void contractForVCL(ValueChangeEvent valueChangeEvent) {
        custNmBinding.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(custNmBinding);
    }

    public void flatRateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        
        System.out.println("Rate Basis value ::" +rateBasisBinding.getValue());
        
        if ((object != null) && (rateBasisBinding.getValue() == "F")) {
            Number rate = (Number) (object);
            if (rate.compareTo(new Number(0)) <= 0)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1001"), null));

            if (!ADFBeanUtils.isPrecisionValid(26, 6, rate))
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));


        }

    }

    public void productRateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number rate = (Number) (object);
            if (rate.compareTo(new Number(0)) <= 0)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1001"), null));

            if (!ADFBeanUtils.isPrecisionValid(26, 6, rate))
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));


        }

    }

    public void perDayRateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number rate = (Number) (object);
            if (rate.compareTo(new Number(0)) < 0)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1001"), null));

            if (!ADFBeanUtils.isPrecisionValid(26, 6, rate))
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));


        }
    }

    public void minDistRateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number rate = (Number) (object);
            if (rate.compareTo(new Number(0)) < 0)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1001"), null));

            if (!ADFBeanUtils.isPrecisionValid(26, 6, rate))
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));

        }


    }

    public void minDistValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number rate = (Number) (object);
            if (rate.compareTo(new Number(0)) < 0)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.198"), null));

            if (!ADFBeanUtils.isPrecisionValid(26, 6, rate))
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));

            Number distRate = new Number(0);
            if (minDistRateBinding.getValue() != null) {
                distRate = (Number) minDistRateBinding.getValue();
            }
            if (distRate.compareTo(new Number(0)) == 0 && rate.compareTo(new Number(0)) > 0)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.198"), null));

        }
    }

    public void rateBasisVCE(ValueChangeEvent valueChangeEvent) {
        _log.info("Executing VCE with value=" + valueChangeEvent.getNewValue());
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void vhclTypeVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(custNmBinding);
    }
    
    public void setMinDistRateBinding(RichInputText minDistRateBinding) {
        this.minDistRateBinding = minDistRateBinding;
    }

    public RichInputText getMinDistRateBinding() {
        return minDistRateBinding;
    }

}
