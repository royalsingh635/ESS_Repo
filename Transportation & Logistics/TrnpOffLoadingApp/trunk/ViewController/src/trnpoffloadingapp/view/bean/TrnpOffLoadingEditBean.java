package trnpoffloadingapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;

import org.apache.myfaces.trinidad.context.RequestContext;


public class TrnpOffLoadingEditBean {
    String mode = "V";
    private static ADFLogger _log = ADFLogger.createADFLogger(TrnpOffLoadingEditBean.class);
    private RichSelectOneChoice offloadingBasisBinding;
    private RichInputListOfValues entityNmBinding;
    private RichInputListOfValues currencyNmBinding;
    private RichInputListOfValues loadingOrderBinding;
    private RichInputListOfValues transporterNmBinding;
    private RichInputText shipQtyBinding;
    private RichInputListOfValues docSrcBinding;
    private RichSelectOneRadio reqTypeBinding;
    private RichInputText strtReadBinding;


    public TrnpOffLoadingEditBean() {
        if (ADFModelUtils.resolvEl("#{pageFlowScope.P_MODE}") != null)
            mode = (String) ADFModelUtils.resolvEl("#{pageFlowScope.P_MODE}");
    }

    public void setMode(String mode) {
        RequestContext.getCurrentInstance().getPageFlowScope().put("P_MODE", "mode");
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public String offLoadAddACTION() {
        OperationBinding createOp = ADFBeanUtils.getOperationBinding("CreateInsertOffload");
        createOp.execute();
        mode = "A";
        return null;
    }


    public String offLoadEditACTION() {
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
                                                    ADFModelUtils.resolvRsrc("MSG.1294")+" [ " + usrNm + "]. ",
                                                    FacesMessage.SEVERITY_INFO);
            /* ADFModelUtils.showFormattedFacesMessage("Edit Not Allowed.",
                                                    "This Rate Contract is Pending at [ " + usrNm + "].",
                                                    FacesMessage.SEVERITY_INFO); */
        }
        return null;
    }

    public String offLoadSaveACTION() {
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
                    ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1506"),
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
                        ADFModelUtils.showFormattedFacesMessage(" ",ADFModelUtils.resolvRsrc("MSG.614"),
                                                                FacesMessage.SEVERITY_INFO);
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
                /*  ADFModelUtils.showFormattedFacesMessage("Workflow not Defined for this Document.", " ",
                                                        FacesMessage.SEVERITY_ERROR); */

            }

        }
        return null;
    }

    public String offLoadCancelACTION() {
        OperationBinding saveOp = ADFBeanUtils.getOperationBinding("Rollback");
        saveOp.execute();
        mode = "V";
        return "back";
    }

    public String offLoadSaveFwdACTION() {
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
                if (level == -1) 
                {
                    ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1506"),
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
                /*  ADFModelUtils.showFormattedFacesMessage("Workflow not Defined for this Document.", " ",
                                                        FacesMessage.SEVERITY_ERROR); */

            }

        }
        //Workflow end

        return null;
    }

    public Boolean callToSaveApplication() {
        _log.info("Save Method");
        OperationBinding olNoOp = ADFBeanUtils.getOperationBinding("setOffLoadingNo");
        olNoOp.execute();
        if (olNoOp.getErrors().size() == 0 && olNoOp.getResult() != null) {
            OperationBinding saveOp = ADFBeanUtils.getOperationBinding("Commit");
            saveOp.execute();
            mode = "V";
            return true;
        } else {
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1486"), " ", FacesMessage.SEVERITY_INFO);
            //ADFModelUtils.showFormattedFacesMessage("Something went wrong.", " ", FacesMessage.SEVERITY_INFO);
            return false;
        }

    }

    public String populateRouteACTION() {
        if (this.chkNullValuesOnPage()) {
            OperationBinding popRouteOp = ADFBeanUtils.getOperationBinding("populateRouteDtl");
            popRouteOp.execute();
        }
        return null;
    }

    public String addSourceDocumentACTION() {
        if (docSrcBinding != null) {
            OperationBinding chkDupli = ADFBeanUtils.getOperationBinding("chkDuplicateDocument");
            chkDupli.execute();
            if (chkDupli.getResult() != null && (Boolean) chkDupli.getResult()) {
                OperationBinding addSrcDocOp = ADFBeanUtils.getOperationBinding("addSourceDocumentDtl");
                addSrcDocOp.execute();
            } else {
                ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.735"), " ", FacesMessage.SEVERITY_ERROR,
                                               docSrcBinding.getClientId());
                /* ADFModelUtils.showFacesMessage("Duplicate Document Exist.", " ", FacesMessage.SEVERITY_ERROR,
                                               docSrcBinding.getClientId()); */
            }
        } else {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.808"), " ", FacesMessage.SEVERITY_ERROR,
                                           docSrcBinding.getClientId());
            /* ADFModelUtils.showFacesMessage("Please Select Source Document No.", " ", FacesMessage.SEVERITY_ERROR,
                                           docSrcBinding.getClientId()); */
        }
        return null;
    }

    public void addOCAL(ActionEvent actionEvent) {
        OperationBinding addOc = ADFBeanUtils.findOperation("CreateInsertOC");
        addOc.execute();
        OperationBinding populateOc = ADFBeanUtils.findOperation("addOcFromVW");
        populateOc.execute();
    }

    public void deleteOCAL(ActionEvent actionEvent) {
        OperationBinding delOc = ADFBeanUtils.findOperation("DeleteOC");
        delOc.execute();
    }


    public String deleteDocSrcACTION() {
        OperationBinding delsrc = ADFBeanUtils.findOperation("deleteDocSrc");
        delsrc.execute();
        return null;
    }

    public void setOffloadingBasisBinding(RichSelectOneChoice offloadingBasisBinding) {
        this.offloadingBasisBinding = offloadingBasisBinding;
    }

    public RichSelectOneChoice getOffloadingBasisBinding() {
        return offloadingBasisBinding;
    }

    public void setEntityNmBinding(RichInputListOfValues entityNmBinding) {
        this.entityNmBinding = entityNmBinding;
    }

    public RichInputListOfValues getEntityNmBinding() {
        return entityNmBinding;
    }

    public void setCurrencyNmBinding(RichInputListOfValues currencyNmBinding) {
        this.currencyNmBinding = currencyNmBinding;
    }

    public RichInputListOfValues getCurrencyNmBinding() {
        return currencyNmBinding;
    }

    public void setLoadingOrderBinding(RichInputListOfValues loadingOrderBinding) {
        this.loadingOrderBinding = loadingOrderBinding;
    }

    public RichInputListOfValues getLoadingOrderBinding() {
        return loadingOrderBinding;
    }

    public void setTransporterNmBinding(RichInputListOfValues transporterNmBinding) {
        this.transporterNmBinding = transporterNmBinding;
    }

    public RichInputListOfValues getTransporterNmBinding() {
        return transporterNmBinding;
    }


    public Boolean chkNullValuesOnPage() {
        if (offloadingBasisBinding.getValue() != null && offloadingBasisBinding.getValue().toString().length() > 0) {
            if(offloadingBasisBinding.getValue().toString().equalsIgnoreCase("39"))
            {}
            else
            {
                if (entityNmBinding.getValue() != null && entityNmBinding.getValue().toString().length() > 0) {
                    if (currencyNmBinding.getValue() != null && currencyNmBinding.getValue().toString().length() > 0) {
                        if (loadingOrderBinding.getValue() != null &&
                            loadingOrderBinding.getValue().toString().length() > 0) {
                            if (reqTypeBinding.getValue().toString().equals("I") ||
                                (reqTypeBinding.getValue().toString().equals("T") &&
                                 transporterNmBinding.getValue() != null &&
                                 transporterNmBinding.getValue().toString().length() > 0)) {
                            } else {
                                ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), " ",
                                                               FacesMessage.SEVERITY_ERROR,
                                                               transporterNmBinding.getClientId());
                                /*  ADFModelUtils.showFacesMessage("Please select Transporter.", " ",
                                                               FacesMessage.SEVERITY_ERROR,
                                                               transporterNmBinding.getClientId()); */
                                return false;
                            }
                        } else {
                            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), " ", FacesMessage.SEVERITY_ERROR,
                                                           loadingOrderBinding.getClientId());
                            /* ADFModelUtils.showFacesMessage("Please select Loading Order.", " ", FacesMessage.SEVERITY_ERROR,
                                                           loadingOrderBinding.getClientId()); */
                            return false;
                        }
                    } else {
                        ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), " ", FacesMessage.SEVERITY_ERROR,
                                                       currencyNmBinding.getClientId());
                        /* ADFModelUtils.showFacesMessage("Please select Currency.", " ", FacesMessage.SEVERITY_ERROR,
                                                       currencyNmBinding.getClientId()); */
                        return false;
                    }
                } else {
                    ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), " ", FacesMessage.SEVERITY_ERROR,
                                                   entityNmBinding.getClientId());
                    /* ADFModelUtils.showFacesMessage("Please select Name.", " ", FacesMessage.SEVERITY_ERROR,
                                                   entityNmBinding.getClientId()); */
                    return false;
                }    
            }
        } else {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.508"), " ", FacesMessage.SEVERITY_ERROR,
                                           offloadingBasisBinding.getClientId());
            /* ADFModelUtils.showFacesMessage("Please select Offloading Basis.", " ", FacesMessage.SEVERITY_ERROR,
                                           offloadingBasisBinding.getClientId()); */
            return false;
        }
        return true;
    }

    public void loadingOrderValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            OperationBinding chkDupli = ADFBeanUtils.findOperation("chkDupliOrder");
            chkDupli.getParamsMap().put("loIdParam", object.toString());
            chkDupli.execute();
            if (chkDupli.getResult() != null && chkDupli.getResult().toString().equals("N")) {
            } 
            else
            {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                              ADFModelUtils.resolvRsrc("MSG.4613")+ chkDupli.getResult() +
                                                              ADFModelUtils.resolvRsrc("MSG.2082"),
                                                              null));
                /*  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                              "Offloading No." + chkDupli.getResult() +
                                                              " with same Loading Order is already Pending for same Customer.",
                                                               null)); */
            }
        }
    }

    public void rateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (val.compareTo(new Number(0)) > 0) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1001"),
                                                              null));
                /*  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Rate should be > 0.",
                                                              null)); */
            }
            if (!ADFBeanUtils.isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision (26,6).",
                                                              null)); */

            }

        }
    }

    public void actualValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (val.compareTo(new Number(0)) > 0) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.198"), null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Actual Value should be > 0.", null)); */
            }

            if (!ADFBeanUtils.isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision (26,6).",
                                                              null)); */

            }
        }
    }

    public void strtReadingValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer val = (Integer) object;
            if (val.compareTo(new Integer(0)) >= 0) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                             ADFModelUtils.resolvRsrc("MSG.198"), null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Start Reading should be >= 0.", null)); */
            }

        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          ADFModelUtils.resolvRsrc("MSG.508"), null));
            /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Please Enter Start Reading Value.", null)); */
        }
    }

    public void endReadingValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer val = (Integer) object;
            if (val.compareTo(new Integer(0)) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.198"),
                                                              null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "End Reading should be > 0.",
                                                              null)); */
            }
            _log.info("Start Reading =" + strtReadBinding);
            _log.info("Start Reading Value=" + strtReadBinding.getValue());
            if (strtReadBinding.getValue() != null && strtReadBinding.getValue().toString().length() > 0) {
                System.out.println("Inside Validator");
                Integer strtRead = (Integer) strtReadBinding.getValue();
                if (strtRead != null) {
                    System.out.println("start reading is not null");
                    if (val.compareTo(strtRead) <= 0) {
                        System.out.println("Value is less than start reading");
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      ADFModelUtils.resolvRsrc("MSG.2106"),
                                                                      null));
                        /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "End Reading should be Greater Than Start Reading.",
                                                                      null)); */

                    }
                } else
                    System.out.println("start reading is null");
            }
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          ADFModelUtils.resolvRsrc("MSG.508"), null));
            /*  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Please Enter End Reading Value.", null)); */
        }
    }

    public void damagedQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (val.compareTo(new Number(0)) >= 0) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.198"), null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Damaged Quantity should be >= 0.", null)); */
            }
            if (!ADFBeanUtils.isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision (26,6).",
                                                              null)); */

            }
            if (shipQtyBinding.getValue() != null) {
                oracle.jbo.domain.Number shipQty = (oracle.jbo.domain.Number) shipQtyBinding.getValue();
                if (shipQty.compareTo(val) < 0)
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  ADFModelUtils.resolvRsrc("MSG.2126"),
                                                                  null));
                    /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Damaged Quantity can not be greater than Shipment Quantity.",
                                                                  null)); */
            }

        }


    }

    public void itmActualValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (val.compareTo(new Number(0)) > 0) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.198"), null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Actual Value should be > 0.", null)); */
            }
            if (!ADFBeanUtils.isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision (26,6).",
                                                              null)); */

            }

        }


    }

    public void itmRateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (val.compareTo(new Number(0)) > 0) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,ADFModelUtils.resolvRsrc("MSG.198"),
                                                              null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Rate should be > 0.",
                                                              null)); */
            }
            if (!ADFBeanUtils.isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision (26,6).",
                                                              null)); */

            }

        }


    }


    public void amtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {

            Number rate = (Number) (object);
            if (rate.compareTo(new Number(0)) <= 0)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.198"), null));

            if (!ADFBeanUtils.isPrecisionValid(26, 6, rate))
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));


        }


    }

    public void setShipQtyBinding(RichInputText shipQtyBinding) {
        this.shipQtyBinding = shipQtyBinding;
    }

    public RichInputText getShipQtyBinding() {
        return shipQtyBinding;
    }

    public void setDocSrcBinding(RichInputListOfValues docSrcBinding) {
        this.docSrcBinding = docSrcBinding;
    }

    public RichInputListOfValues getDocSrcBinding() {
        return docSrcBinding;
    }

    public void setReqTypeBinding(RichSelectOneRadio reqTypeBinding) {
        this.reqTypeBinding = reqTypeBinding;
    }

    public RichSelectOneRadio getReqTypeBinding() {
        return reqTypeBinding;
    }

    public void setStrtReadBinding(RichInputText strtReadBinding) {
        this.strtReadBinding = strtReadBinding;
    }

    public RichInputText getStrtReadBinding() {
        return strtReadBinding;
    }


    public void changeArrivalDateVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {

            Object date = vce.getNewValue();

            Timestamp arrivalDate = ((Timestamp) date);
            System.out.println("ArrivalDate in bean VCL ::" + vce.getNewValue());
            OperationBinding ob = ADFBeanUtils.findOperation("changeArrivalDate");
            ob.getParamsMap().put("arrvlDate", arrivalDate);
            ob.execute();
        }
    }

}

