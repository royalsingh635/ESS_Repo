package trnploadinginvoiceapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

public class LoadingInvoiceAddBean {
    private String mode = (String) ADFModelUtils.resolvEl("#{pageFlowScope.P_MODE}");
    private RichInputText amtBsPgBind;
    private RichInputText emptyRatePgBind;
    private RichTable loadingInvDtlTablePgBind;
    private RichTable otherChgsTablePgBind;
    private RichPopup applyTaxPgBind;
    private String cldId = EbizParams.GLBL_APP_CLD_ID();
    private String hoOrgId = EbizParams.GLBL_HO_ORG_ID();
    private String orgId = EbizParams.GLBL_APP_USR_ORG();
    private Integer slocId = EbizParams.GLBL_APP_SERV_LOC();
    private Integer usrId = EbizParams.GLBL_APP_USR();
    private Integer docNo = 24257;
    private Integer docType = 0;
    private RichSelectOneChoice taxRuleIdPgBind;
    private RichInputText totKmPgBind;
    private RichInputText totValuePgBind;
    private RichSelectOneChoice liStatusPgBind;
    private RichPanelBox totalAmtFormPgBind;
    private RichInputText convFctrPgBind;
    private String pass = "N";

    public LoadingInvoiceAddBean() {
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void editButtonAL(ActionEvent actionEvent) {
        if (liStatusPgBind.getValue() != null && Integer.parseInt(liStatusPgBind.getValue().toString()) != 15) {
            //check for pending
            Integer pending = null;

            OperationBinding chkUsr = ADFBeanUtils.findOperation("pendingCheck");
            chkUsr.getParamsMap().put("SlocId", slocId);
            chkUsr.getParamsMap().put("CldId", cldId);
            chkUsr.getParamsMap().put("OrgId", orgId);
            chkUsr.getParamsMap().put("DocNo", docNo);
            chkUsr.execute();

            if (chkUsr.getResult() != null) {
                pending = Integer.parseInt(chkUsr.getResult().toString());
            }
            System.out.println("Pending at=" + pending);
            if (pending.compareTo(-1) != 0 && pending.compareTo(usrId) != 0) {
                OperationBinding gtUsrNm = ADFBeanUtils.findOperation("getUsrName");
                gtUsrNm.getParamsMap().put("usrId", pending);
                gtUsrNm.execute();
                StringBuffer usrName = new StringBuffer("Anonymous");
                if (gtUsrNm.getResult() != null)
                    usrName = new StringBuffer("[").append(gtUsrNm.getResult()).append("]");
                String msg2 = ADFModelUtils.resolvRsrc("MSG.1294")+ " [ " + usrName + "] "+ ADFModelUtils.resolvRsrc("MSG.879");
                    //"Loading Invoice is pending at other user " + usrName + " for approval, You cannot Edit this.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            } else {
                mode = "E";
            }
        } else {

            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.2047"),
                                                    ADFModelUtils.resolvRsrc("MSG.2028"),
                                                    FacesMessage.SEVERITY_ERROR);

            /*  ADFModelUtils.showFormattedFacesMessage("Approved Document",
                                                    "Document has been approved. You cannot edit this document",
                                                    FacesMessage.SEVERITY_ERROR); */
        }
    }

    public void addButtonAL(ActionEvent actionEvent) {
        mode = "C";
        ADFBeanUtils.findOperation("CreateInsert").execute();
    }

    public Boolean callMethodsBeforeSave() {
        Boolean val = true;
        ADFBeanUtils.findOperation("setTotalAmtVals").execute();

        ADFBeanUtils.findOperation("getDfltTaxRuleId").execute();

        ADFBeanUtils.findOperation("processCalcOnUserSel").execute();

        return val;
    }

    public void saveButtonAL(ActionEvent actionEvent) {
        if (callMethodsBeforeSave()) {
            OperationBinding op = ADFBeanUtils.findOperation("Commit");
            op.execute();
            if (op.getErrors().size() == 0) {
                mode = "V";
                //  JSFUtils.addFacesInformationMessage("Record Saved Successfully.");

                //Workflow Start
                String action = "I";
                String remark = "A";

                OperationBinding WfnoOp = ADFBeanUtils.findOperation("getWfNo");
                WfnoOp.getParamsMap().put("sloc_id", slocId);
                WfnoOp.getParamsMap().put("cld_id", cldId);
                WfnoOp.getParamsMap().put("org_id", orgId);
                WfnoOp.getParamsMap().put("doc_no", docNo);
                WfnoOp.execute();

                String wfNum = null;
                Integer level = 0;
                Integer res = -1;

                if (WfnoOp.getResult() != null) {
                    wfNum = WfnoOp.getResult().toString();
                }
                if (wfNum != null) {
                    OperationBinding usrLevelOp = ADFBeanUtils.findOperation("getUsrLvl");
                    usrLevelOp.getParamsMap().put("SlocId", slocId);
                    usrLevelOp.getParamsMap().put("CldId", cldId);
                    usrLevelOp.getParamsMap().put("OrgId", orgId);
                    usrLevelOp.getParamsMap().put("usr_id", usrId);
                    usrLevelOp.getParamsMap().put("WfNum", wfNum);
                    usrLevelOp.getParamsMap().put("DocNo", docNo);
                    usrLevelOp.execute();
                    level = -1;
                    if (usrLevelOp.getResult() != null) {
                        if (usrLevelOp.getResult() != null)
                            level = Integer.parseInt(usrLevelOp.getResult().toString());
                    }
                    if (level == -1) {
                        FacesMessage message =
                            new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2011"));
                            //new FacesMessage("Something went wrong while getting Workflow Level of this user.Contact to ESS.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else {
                        OperationBinding insTxnOp = ADFBeanUtils.findOperation("insIntoTxn");
                        insTxnOp.getParamsMap().put("sloc_id", slocId);
                        insTxnOp.getParamsMap().put("cld_id", cldId);
                        insTxnOp.getParamsMap().put("pOrgId", orgId);
                        insTxnOp.getParamsMap().put("DOC_NO", docNo);
                        insTxnOp.getParamsMap().put("WfNum", wfNum);
                        insTxnOp.getParamsMap().put("poDocId", null);
                        insTxnOp.getParamsMap().put("usr_idFrm", usrId);
                        insTxnOp.getParamsMap().put("usr_idTo", usrId);
                        insTxnOp.getParamsMap().put("levelFrm", level);
                        insTxnOp.getParamsMap().put("levelTo", level);
                        insTxnOp.getParamsMap().put("action", action);
                        insTxnOp.getParamsMap().put("remark", remark);
                        insTxnOp.getParamsMap().put("amount", 0);
                        insTxnOp.getParamsMap().put("post", "S");
                        insTxnOp.execute();

                        if (insTxnOp.getResult() != null) {
                            res = Integer.parseInt(insTxnOp.getResult().toString());
                        }
                        if (res == 1) {
                            OperationBinding operationBinding = ADFBeanUtils.findOperation("Commit");
                            operationBinding.execute();
                        } else {
                            System.out.println("error during insert to WF");
                        }
                        System.out.println(level + "level--res" + res);
                    }
                }
                //Workflow end
                ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.91"), ADFModelUtils.resolvRsrc("MSG.1907"),
                                                        FacesMessage.SEVERITY_INFO);
                /* ADFModelUtils.showFormattedFacesMessage("Saved Successfully", "Record Saved Successfully",
                                                        FacesMessage.SEVERITY_INFO); */
            } else {
                FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1478"));
                //FacesMessage message = new FacesMessage("Something went wrong..");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        }
    }

    public String saveAndFrwdAction() {
        
        if(pass.equalsIgnoreCase("Y"))
        {
            if (callMethodsBeforeSave()) {
                OperationBinding op = ADFBeanUtils.findOperation("Commit");
                op.execute();
                if (op.getErrors().size() == 0) {
                    mode = "V";
                    //  JSFUtils.addFacesInformationMessage("Record Saved Successfully.");

                    //Workflow Start
                    String action = "I";
                    String remark = "A";

                    OperationBinding WfnoOp = ADFBeanUtils.findOperation("getWfNo");
                    WfnoOp.getParamsMap().put("sloc_id", slocId);
                    WfnoOp.getParamsMap().put("cld_id", cldId);
                    WfnoOp.getParamsMap().put("org_id", orgId);
                    WfnoOp.getParamsMap().put("doc_no", docNo);
                    WfnoOp.execute();

                    String wfNum = null;
                    Integer level = 0;
                    Integer res = -1;

                    if (WfnoOp.getResult() != null) {
                        wfNum = WfnoOp.getResult().toString();
                    }
                    if (wfNum != null) {
                        OperationBinding usrLevelOp = ADFBeanUtils.findOperation("getUsrLvl");
                        usrLevelOp.getParamsMap().put("SlocId", slocId);
                        usrLevelOp.getParamsMap().put("CldId", cldId);
                        usrLevelOp.getParamsMap().put("OrgId", orgId);
                        usrLevelOp.getParamsMap().put("usr_id", usrId);
                        usrLevelOp.getParamsMap().put("WfNum", wfNum);
                        usrLevelOp.getParamsMap().put("DocNo", docNo);
                        usrLevelOp.execute();
                        level = -1;
                        if (usrLevelOp.getResult() != null) {
                            if (usrLevelOp.getResult() != null)
                                level = Integer.parseInt(usrLevelOp.getResult().toString());
                        }
                        if (level == -1) {
                            FacesMessage message =
                                new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2011"));
                                //new FacesMessage("Something went wrong while getting Workflow Level of this user.Contact to ESS.");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        } else {
                            OperationBinding insTxnOp = ADFBeanUtils.findOperation("insIntoTxn");
                            insTxnOp.getParamsMap().put("sloc_id", slocId);
                            insTxnOp.getParamsMap().put("cld_id", cldId);
                            insTxnOp.getParamsMap().put("pOrgId", orgId);
                            insTxnOp.getParamsMap().put("DOC_NO", docNo);
                            insTxnOp.getParamsMap().put("WfNum", wfNum);
                            insTxnOp.getParamsMap().put("poDocId", null);
                            insTxnOp.getParamsMap().put("usr_idFrm", usrId);
                            insTxnOp.getParamsMap().put("usr_idTo", usrId);
                            insTxnOp.getParamsMap().put("levelFrm", level);
                            insTxnOp.getParamsMap().put("levelTo", level);
                            insTxnOp.getParamsMap().put("action", action);
                            insTxnOp.getParamsMap().put("remark", remark);
                            insTxnOp.getParamsMap().put("amount", 0);
                            insTxnOp.getParamsMap().put("post", "S");
                            insTxnOp.execute();

                            if (insTxnOp.getResult() != null) {
                                res = Integer.parseInt(insTxnOp.getResult().toString());
                            }
                            if (res == 1) {
                                OperationBinding operationBinding = ADFBeanUtils.findOperation("Commit");
                                operationBinding.execute();
                                return "workFlow";
                            } else {
                                System.out.println("error during insert to WF");
                            }
                            System.out.println(level + "level--res" + res);
                        }
                    } else {
                        FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1486"));
                        //FacesMessage message = new FacesMessage("Workflow not Defined for this Document.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                    //Workflow end

                } else {
                    FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1478"));
                    ///FacesMessage message = new FacesMessage("Something went wrong..");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            }    
        }
        return null;
    }

    public String cancelButtonAction() {
        mode = "V";
        ADFBeanUtils.findOperation("Rollback").execute();
        return "back";
    }

    public void addTripDtlAL(ActionEvent actionEvent) {

        OperationBinding binding = ADFBeanUtils.findOperation("chkCoaOpenedOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            ADFBeanUtils.findOperation("CreateInsert1").execute();
            ADFBeanUtils.findOperation("setDfltLiDtlValues").execute();
        }
    }

    public void deleteTripDtlAL(ActionEvent actionEvent) {
        ////// Remove the row which is round trip
        ADFBeanUtils.findOperation("removeRoundTripRow").execute();

        ADFBeanUtils.findOperation("Delete").execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(loadingInvDtlTablePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(totalAmtFormPgBind);
    }

    public void totalKmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(ADFBeanUtils.isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
                /*  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null)); */
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.198"), null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than zero.", null)); */
            }
        }
    }

    public void amountSpValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(ADFBeanUtils.isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
                /*  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null)); */
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.198"), null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than zero.", null)); */
            }
        }
    }

    public void emptyVehicleValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(ADFBeanUtils.isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
                /*  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null)); */
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.198"), null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than zero.", null)); */
            }
        }
    }

    public void amountSpVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding binding = ADFBeanUtils.findOperation("setBaseValue");
            binding.getParamsMap().put("amtSp", vce.getNewValue());
            binding.execute();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(amtBsPgBind);
    }

    public void setAmtBsPgBind(RichInputText amtBsPgBind) {
        this.amtBsPgBind = amtBsPgBind;
    }

    public RichInputText getAmtBsPgBind() {
        return amtBsPgBind;
    }

    public void roundTripFlgVCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(emptyRatePgBind);
        //System.out.println("Rate Flag is " + vce.getNewValue());
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

            } else if (vce.getNewValue().toString().equalsIgnoreCase("false")) {
                ////// Remove the row which is round trip
                ADFBeanUtils.findOperation("removeRoundTripRow").execute();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(loadingInvDtlTablePgBind);
        }
    }

    public void emptyRateVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding binding = ADFBeanUtils.findOperation("insNewRoundTripRow");
            binding.getParamsMap().put("emptyRate", vce.getNewValue());
            binding.execute();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(loadingInvDtlTablePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(totalAmtFormPgBind);
    }

    public void setEmptyRatePgBind(RichInputText emptyRatePgBind) {
        this.emptyRatePgBind = emptyRatePgBind;
    }

    public RichInputText getEmptyRatePgBind() {
        return emptyRatePgBind;
    }

    public void setLoadingInvDtlTablePgBind(RichTable loadingInvDtlTablePgBind) {
        this.loadingInvDtlTablePgBind = loadingInvDtlTablePgBind;
    }

    public RichTable getLoadingInvDtlTablePgBind() {
        return loadingInvDtlTablePgBind;
    }

    public void otherChgsAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(ADFBeanUtils.isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null));
                /*  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null)); */
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.198"), null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than zero.", null)); */
            }
        }
    }

    public void othersChgsAmtVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(otherChgsTablePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(totalAmtFormPgBind);
    }

    public void setOtherChgsTablePgBind(RichTable otherChgsTablePgBind) {
        this.otherChgsTablePgBind = otherChgsTablePgBind;
    }

    public RichTable getOtherChgsTablePgBind() {
        return otherChgsTablePgBind;
    }

    public void addOCAL(ActionEvent actionEvent) {
        OperationBinding addOc = ADFBeanUtils.findOperation("CreateInsertOC");
        addOc.execute();
        OperationBinding populateOc = ADFBeanUtils.findOperation("addOcFromVW");
        populateOc.execute();
    }

    public void offloadingVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding binding = ADFBeanUtils.findOperation("insOtherChgsBasedOnOffload");
            binding.getParamsMap().put("ocDispId", vce.getNewValue());
            binding.execute();
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(loadingInvDtlTablePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(totKmPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(totValuePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(totalAmtFormPgBind);
    }

    public void setApplyTaxPgBind(RichPopup applyTaxPgBind) {
        this.applyTaxPgBind = applyTaxPgBind;
    }

    public RichPopup getApplyTaxPgBind() {
        return applyTaxPgBind;
    }

    public void applyTaxAL(ActionEvent actionEvent) {
        if (taxRuleIdPgBind.getValue() != null) {
            OperationBinding binding = ADFBeanUtils.findOperation("insertIntoSlsTrnpTrAndTrLines");
            binding.getParamsMap().put("taxRuleId", taxRuleIdPgBind.getValue());
            binding.execute();

            applyTaxPgBind.hide();
        }
    }

    public void cancelTaxAL(ActionEvent actionEvent) {
        applyTaxPgBind.hide();
    }

    public void showTaxSelAL(ActionEvent actionEvent) {
        ADFBeanUtils.showPopup(applyTaxPgBind, true);
    }

    public void setTaxRuleIdPgBind(RichSelectOneChoice taxRuleIdPgBind) {
        this.taxRuleIdPgBind = taxRuleIdPgBind;
    }

    public RichSelectOneChoice getTaxRuleIdPgBind() {
        return taxRuleIdPgBind;
    }

    public void custNameVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding binding = ADFBeanUtils.findOperation("setCoaIdBasedOnNameSel");
            binding.getParamsMap().put("custName", vce.getNewValue());
            binding.execute();
        }
    }

    public void processCalcAL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("getDfltTaxRuleId").execute();

        ADFBeanUtils.findOperation("processCalcOnUserSel").execute();
    }

    public void offloadingNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding ob = ADFBeanUtils.findOperation("chkOffloadingExistOrNot");
            ob.getParamsMap().put("offloading", object);
            ob.execute();

            if (ob.getResult() != null && !(Boolean) ob.getResult()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, ADFModelUtils.resolvRsrc("MSG.2979"), null));
                //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Record.", null));
            }

            OperationBinding binding = ADFBeanUtils.findOperation("chkOffloadingExisOrNot");
            binding.getParamsMap().put("offDispId", object);
            binding.execute();

            if (binding.getResult() != null && binding.getResult().toString().length() > 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.2979") + binding.getResult(),
                                                              null));
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Duplicate Record. Please check " + binding.getResult(),
                                                              null)); */
            }
        }
    }

    public void setTotKmPgBind(RichInputText totKmPgBind) {
        this.totKmPgBind = totKmPgBind;
    }

    public RichInputText getTotKmPgBind() {
        return totKmPgBind;
    }

    public void setTotValuePgBind(RichInputText totValuePgBind) {
        this.totValuePgBind = totValuePgBind;
    }

    public RichInputText getTotValuePgBind() {
        return totValuePgBind;
    }

    public void setLiStatusPgBind(RichSelectOneChoice liStatusPgBind) {
        this.liStatusPgBind = liStatusPgBind;
    }

    public RichSelectOneChoice getLiStatusPgBind() {
        return liStatusPgBind;
    }

    public void setTotalAmtFormPgBind(RichPanelBox totalAmtFormPgBind) {
        this.totalAmtFormPgBind = totalAmtFormPgBind;
    }

    public RichPanelBox getTotalAmtFormPgBind() {
        return totalAmtFormPgBind;
    }

    public void currNameVCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(convFctrPgBind);
    }

    public void setConvFctrPgBind(RichInputText convFctrPgBind) {
        this.convFctrPgBind = convFctrPgBind;
    }

    public RichInputText getConvFctrPgBind() {
        return convFctrPgBind;
    }

    public void saveAndFwdAL(ActionEvent actionEvent) {
        
        RichLink txtAmt = (RichLink) actionEvent.getSource();
        
        Number amt = (Number) txtAmt.getAttributes().get("TotalAmtValue");
        
        if(amt.compareTo(0) > 0)
            pass = "Y";
        else
        {
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.1686"), ADFModelUtils.resolvRsrc("MSG.2078"), FacesMessage.SEVERITY_ERROR);
            //ADFModelUtils.showFormattedFacesMessage("Amount can nto be zero", "Amount should be greater than zero ", FacesMessage.SEVERITY_ERROR);    
            pass = "N";
        }
    }
}
