package mmqcprocess.view.beans;

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
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

/**
 * Qc Process Managed Bean.
 * Contains all the business logic for Qc Process functionalities.
 * @author BL
 * Dated -12/08/2013
 * */
public class QcProcessBean {
    // private RichInputText tatalQtyBind;
    private RichInputText doneQcQtyBind;
    private RichInputText inspctnQtyBind;
    private String disableItm;
    //private RichInputText okQtyBind;
    //private RichInputText rejectQtyBind;
    // private RichInputText rewrkQtyBind;
    private String disable = "A";
    private String disableItmField = "V";
    Number zero = new Number(0);
    Number one = new Number(1);
    private RichSelectOneChoice receiptNoBind;
    private RichSelectOneChoice qcStatusBind;
    private RichInputText actualValueBind;
    private String mode = modeGet();
    private RichPopup detailPopup;
    int itmPreviousStatusVal = 0;
    private RichPopup acptPopup;
    private RichPopup rejctPopup;
    private RichSelectOneRadio accptRjctBind;
    private RichInputText okQtyBsBind;
    private RichInputText rejectQtyBsBind;
    private RichInputText rewrkQtyBsBind;
    private RichInputText tatalQtyBsBind;
    private RichInputText uomCnvrFactBind;
    private RichTable paramTableBind;
    //private RichSelectOneChoice srNoBind;
    private RichTable qcCsnTableBind;
    private RichSelectOneChoice qcForDocTypeBind;
    private RichPopup jeCompletePopup;
    private RichInputText transcheckQtybind;
    private RichInputListOfValues srNoBind;
    private RichPopup viewRcptItmDetailsPopup;

    public QcProcessBean() {
    }
    private static ADFLogger loginfo = ADFLogger.createADFLogger(QcProcessBean.class);

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     *      Method to show validation message(I,E,W)
     *      mesg:Message to display
     *      sev:Severity(I,E,W)
     *      chk:true=if resource bundle is used
     *      typFlg: 'F' for FM , 'V' for VE
     *      clientId : client id for UI component
     * */
    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg, String clientId) {
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true) {
            String msg = resolvEl("#{bundle['" + mesg + "']}");
            message = new FacesMessage(msg);
        }
        if (sev.equalsIgnoreCase("E")) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        } else if (sev.equalsIgnoreCase("W")) {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
        } else if (sev.equalsIgnoreCase("I")) {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        } else {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        if (typFlg.equals("F")) {
            FacesContext.getCurrentInstance().addMessage(clientId, message);
        } else if (typFlg.equals("V")) {
            throw new ValidatorException(message);
        }
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void actualValueVCL(ValueChangeEvent valueChangeEvent) {
    }

    public void ActualChkVCL(ValueChangeEvent valueChangeEvent) {
    }

    public String createAction() {
        setDisable("A");
        setDisableItmField("V");
        setMode("A");
        return "create";
    }

    public String createQcProcess() {
        String req = "N";
        String dup = "N";
        OperationBinding opReq = getBindings().getOperationBinding("isRequired");
        opReq.execute();
        if (opReq.getResult() != null) {
            req = opReq.getResult().toString();
        }
        if ("N".equalsIgnoreCase(req)) {
            OperationBinding oprcptDup = getBindings().getOperationBinding("isReceiptNoDuplicate");
            oprcptDup.getParamsMap().put("value", receiptNoBind.getValue().toString());
            oprcptDup.execute();
            if (oprcptDup.getResult() != null) {
                dup = oprcptDup.getResult().toString();
            }
        }
        if ("Y".equalsIgnoreCase(req)) {


            String message = resolvElDCMsg("#{bundle['MSG.681']}").toString();


            FacesMessage msg =

                //new FacesMessage(FacesMessage.SEVERITY_ERROR,"Receipt No Required.", null);
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(receiptNoBind.getClientId(), msg);
            return null;
        } else if ("Y".equalsIgnoreCase(dup)) {
            //"Duplicate Receipt No found.",
            String message = resolvElDCMsg("#{bundle['MSG.695']}").toString();


            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(receiptNoBind.getClientId(), msg);
            return null;
        } else {
            OperationBinding checkitmParm = getBindings().getOperationBinding("checkParamItmExist");
            checkitmParm.execute();
            ArrayList itmList = new ArrayList();
            if (checkitmParm.getResult() != null) {
                itmList = (ArrayList) checkitmParm.getResult();
            }
            loginfo.info("array--------------" + itmList);
            if (itmList.isEmpty()) {
                loginfo.info("all param define--------------" + qcForDocTypeBind.getValue());
                setDisableItm("A");
                setDisable("V");
                setDisableItmField("A");
                setMode("A");
                OperationBinding opStatus = getBindings().getOperationBinding("addQcRsltItm");
                opStatus.getParamsMap().put("type", qcForDocTypeBind.getValue());
                opStatus.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(paramTableBind);
            } else {
                FacesMessage msg =
                    //new FacesMessage(FacesMessage.SEVERITY_ERROR,"Parameter Not defind for Item ", null);resolvEl("#{bundle['MSG.681']}").toString()
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.682']}").toString(), null);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(receiptNoBind.getClientId(), msg);
                msg.setDetail(itmList.toString());

                // msg.setSummary("In This Receipt Item Paramater not defind for listed Item");
                msg.setSummary(resolvEl("#{bundle['MSG.683']}").toString());


                return null;
            }
            return null;
        }
    }

    public String editPendingQC() {
        OperationBinding opStatus = getBindings().getOperationBinding("chkQcEditable");
        opStatus.execute();
        String stats = "N";
        if (opStatus.getResult() != null) {
            stats = opStatus.getResult().toString();
        }
        if ("Y".equalsIgnoreCase(stats)) {
            FacesMessage msg =

                //new FacesMessage(FacesMessage.SEVERITY_ERROR,"Qc Process Already Completed. You Con't edit this", null);
                new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.684']}").toString(), null);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, msg);
        } else {
            setDisableItmField("A");
            setDisableItm("V");
            setMode("A");
        }
        return null;
    }

    public String nextItmAction() {
        setDisableItm("A");
        setDisableItmField("A");
        //inspctnQtyBind.setDisabled(false);
        return "itmCreate";
    }

    public String doneQcItmStatus() {
        loginfo.info("log info");
        if (getTatalQtyBsBind().getValue() != null && getDoneQcQtyBind().getValue() != null) {
            Number qcTotQty = (Number) (getTatalQtyBsBind().getValue());
            Number qcDoneQty = (Number) (getDoneQcQtyBind().getValue());
            Number qcReqQty = (Number) qcTotQty.minus(qcDoneQty);
            String stats = "N";
            OperationBinding opStatus = getBindings().getOperationBinding("isParamStatusTrue");
            opStatus.execute();
            if (opStatus.getResult() != null) {
                stats = opStatus.getResult().toString();
            }
            String srReq = "N";
            OperationBinding srStatus = getBindings().getOperationBinding("isSrNoRequired");
            srStatus.execute();
            if (srStatus.getResult() != null) {
                srReq = srStatus.getResult().toString();
            }
            if (qcReqQty.compareTo(zero) == 0) {
                OperationBinding resetSr = getBindings().getOperationBinding("resetSrNo");
                resetSr.execute();

                FacesMessage msg =

                    //new FacesMessage(FacesMessage.SEVERITY_ERROR,"All Item Quantity checked for particular item.Please select next Item.", null);
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.686']}").toString(), null);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, msg);
            } else if ("Y".equalsIgnoreCase(srReq)) {
                /* OperationBinding resetSr = getBindings().getOperationBinding("resetSrNo");
                    resetSr.execute(); */

                srNoBind.resetValue();
                AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                FacesMessage msg =
                    //new FacesMessage(FacesMessage.SEVERITY_ERROR,"Select Serial no for Qc.", null);
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.687']}").toString(), null);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, msg);
            } else if ("N".equalsIgnoreCase(stats)) {
                OperationBinding insertQcDlt = getBindings().getOperationBinding("addDtlItmFromParam");
                insertQcDlt.execute();
            } else if ("Y".equalsIgnoreCase(stats)) {
                FacesMessage msg =
                    //new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fill the actual value.", null);
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.688']}").toString(), null);


                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, msg);
            }


        }
        setDisableItm("V");
        return null;
    }

    public String saveAction() {
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String p_org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String tableNm = "MM$MTL$QC";
        OperationBinding updateCompleteQc = getBindings().getOperationBinding("checkCompleteQcDone");
        updateCompleteQc.execute();
        String chekUpdate = "N";
        if (updateCompleteQc.getResult() != null) {
            chekUpdate = updateCompleteQc.getResult().toString();
        }

        Integer checkForCheck = Integer.parseInt(qcForDocTypeBind.getValue().toString());

        loginfo.info("checkForCheck   :: " + checkForCheck);
        Integer fyNo = 0;
        OperationBinding fyIdOp = getBindings().getOperationBinding("getFYid");
        fyIdOp.getParamsMap().put("CldId", p_cldId);
        fyIdOp.getParamsMap().put("OrgId", p_org_id);
        //fyIdOp.getParamsMap().put("geDate", (Timestamp)callDateBind.getValue());
        fyIdOp.getParamsMap().put("geDate", new Timestamp(System.currentTimeMillis()));
        fyIdOp.getParamsMap().put("Mode", "A");
        fyIdOp.execute();
        if (fyIdOp.getResult() != null) {
            fyNo = Integer.parseInt(fyIdOp.getResult().toString());
        }
        loginfo.info("Fy id in bean ---------" + fyNo);
        OperationBinding callNoOp = getBindings().getOperationBinding("genQcProcessNo");
        callNoOp.getParamsMap().put("SlocId", p_sloc_id);
        callNoOp.getParamsMap().put("CldId", p_cldId);
        callNoOp.getParamsMap().put("HoOrgId", p_hoOrgId);
        callNoOp.getParamsMap().put("OrgId", p_org_id);
        callNoOp.getParamsMap().put("TableName", tableNm);
        //callNoOp.getParamsMap().put("fyId", fyNo);
        callNoOp.execute();
        String ids = null;
        if (callNoOp.getResult() != null) {
            ids = callNoOp.getResult().toString();
            loginfo.info("new generated issue id " + ids);
        }


        loginfo.info("---- chekUpdate  -----------" + chekUpdate);
        if ("Y".equalsIgnoreCase(chekUpdate)) {
            String chekType = "N";
            //                 OperationBinding type = getBindings().getOperationBinding("QcItemType");
            //                type.execute();
            //                Object result=type.getResult();
            //                loginfo.info("value of result:::::"+result);
            //if(result!=null) {
            // Integer typeid=Integer.parseInt(result.toString());
            //loginfo.info("Type is:::::::"+typeid);
            //if(typeid==280)
            //{
            //loginfo.info(" in the typeidblock");
            OperationBinding checkCompleteQc = getBindings().getOperationBinding("checkQcTypeComplete");
            checkCompleteQc.execute();
            if (checkCompleteQc.getResult() != null) {
                chekType = checkCompleteQc.getResult().toString();
            }
            // }
            //}
            loginfo.info("---- chekType  -----------" + chekType);
            if ("Y".equalsIgnoreCase(chekType)) {

                //                    OperationBinding qcstat = getBindings().getOperationBinding("setQcComplete");
                //                    qcstat.execute();
                if (checkForCheck.compareTo(new Integer(780)) == 0 || checkForCheck.compareTo(new Integer(781)) == 0) {

                    loginfo.info("Inside second save    -------------------------");
                    OperationBinding commitRcptUpdate = getBindings().getOperationBinding("Commit");
                    commitRcptUpdate.execute();
                    setDisableItmField("V");
                    setDisable("V");
                    setMode("V");


                    FacesMessage msg =
                        //new FacesMessage(FacesMessage.SEVERITY_INFO,"Qc Process Complete and Save SuccessFully .", null);
                        new FacesMessage(FacesMessage.SEVERITY_INFO, resolvEl("#{bundle['MSG.689']}").toString(), null);

                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, msg);

                } else if (checkForCheck.compareTo(new Integer(280)) == 0) {
                    OperationBinding updateRcpt = getBindings().getOperationBinding("updateReceiptTable");
                    updateRcpt.execute();
                    if (updateRcpt.getResult() != null) {
                        String retVal = updateRcpt.getResult().toString();
                        loginfo.info("retVal--------" + retVal);
                        if ("Y".equalsIgnoreCase(retVal)) {
                            loginfo.info("Inside second save    -------------------------");
                            OperationBinding commitRcptUpdate = getBindings().getOperationBinding("Commit");
                            commitRcptUpdate.execute();
                            setDisableItmField("V");
                            setDisable("V");
                            setMode("V");
                            FacesMessage msg =
                                //new FacesMessage(FacesMessage.SEVERITY_INFO,"Qc Process Complete and Save SuccessFully .", null);
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                 resolvEl("#{bundle['MSG.689']}").toString(), null);

                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, msg);
                        } else if ("Y".equalsIgnoreCase(retVal)) {
                            FacesMessage msg =
                                //new FacesMessage(FacesMessage.SEVERITY_ERROR,"Some Error Occur Updating Receipt table .", null);
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                 resolvEl("#{bundle['MSG.690']}").toString(), null);

                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, msg);
                        }
                    }
                } else {


                    showPopup(jeCompletePopup, true);

                    /*
                        OperationBinding updateRcpt = getBindings().getOperationBinding("updateReceiptTable");
                        updateRcpt.execute();
                        if(updateRcpt.getResult()!=null){
                            String retVal = updateRcpt.getResult().toString();
                            loginfo.info("retVal--------"+retVal);
                            if("Y".equalsIgnoreCase(retVal)){
                                loginfo.info("Inside second save    -------------------------");
                                OperationBinding commitRcptUpdate= getBindings().getOperationBinding("Commit");
                                commitRcptUpdate.execute();
                                setDisableItmField("V");
                                setDisable("V");
                                setMode("V");
                                FacesMessage msg =
                                //new FacesMessage(FacesMessage.SEVERITY_INFO,"Qc Process Complete and Save SuccessFully .", null);
                                new FacesMessage(FacesMessage.SEVERITY_INFO,resolvEl("#{bundle['MSG.689']}").toString(), null);

                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, msg);
                            }else if("Y".equalsIgnoreCase(retVal)){
                                FacesMessage msg =
                                //new FacesMessage(FacesMessage.SEVERITY_ERROR,"Some Error Occur Updating Receipt table .", null);
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvEl("#{bundle['MSG.690']}").toString(), null);

                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, msg);
                        }
                        } */

                }
            }
        } else if ("N".equalsIgnoreCase(chekUpdate)) {
            OperationBinding commitItm = getBindings().getOperationBinding("Commit");
            commitItm.execute();
            setDisableItmField("V");
            setDisable("V");
            setMode("V");
            FacesMessage msg =
                //new FacesMessage(FacesMessage.SEVERITY_INFO,"Qc Process Incomplete and save SuccessFully .", null);
                new FacesMessage(FacesMessage.SEVERITY_INFO, resolvEl("#{bundle['MSG.691']}").toString(), null);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, msg);
        }


        //inspctnQtyBind.setDisabled(false);
        return null;
    }

    public String cancelAction() {
        OperationBinding cancelItm = getBindings().getOperationBinding("Rollback");
        cancelItm.execute();
        OperationBinding executeItm = getBindings().getOperationBinding("Execute");
        executeItm.execute();
        setDisableItmField("V");
        setMode("");
        return "goToSearch";
    }

    public void inspectionQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number value = (Number) object;
        if (value != null) {
            if (getTatalQtyBsBind().getValue() != null && getDoneQcQtyBind().getValue() != null) {
                Number qcTotQty = (Number) (getTatalQtyBsBind().getValue());
                Number qcDoneQty = (Number) (getDoneQcQtyBind().getValue());
                Number qcReqQty = (Number) qcTotQty.minus(qcDoneQty);
                if (value.compareTo(zero) == -1) {
                    /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Quantity must be positive value.", null));  */
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvElDCMsg("#{bundle['LBL.1270']}").toString(),
                                                                  null));

                }
                if (qcReqQty.compareTo(zero) == 0) {

                } else if (value.compareTo(qcReqQty) ==
                           1) {
                    //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Inspecting Quantity less than or equals to "+qcReqQty, null));
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  resolvEl("#{bundle['MSG.692']}").toString() +
                                                                  qcReqQty, null));
                }
            }
        }
    }
    /* public void setTatalQtyBind(RichInputText tatalQtyBind) {
        this.tatalQtyBind = tatalQtyBind;
    }

    public RichInputText getTatalQtyBind() {
        return tatalQtyBind;
    }  */

    public void setDoneQcQtyBind(RichInputText doneQcQtyBind) {
        this.doneQcQtyBind = doneQcQtyBind;
    }

    public RichInputText getDoneQcQtyBind() {
        return doneQcQtyBind;
    }

    public void setInspctnQtyBind(RichInputText inspctnQtyBind) {
        this.inspctnQtyBind = inspctnQtyBind;
    }

    public RichInputText getInspctnQtyBind() {
        return inspctnQtyBind;
    }

    public void setDisableItm(String disableItm) {
        this.disableItm = disableItm;
    }

    public String getDisableItm() {
        return disableItm;
    }


    public void okQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number value = (Number) object;
        if (value != null && getTatalQtyBsBind().getValue() != null) {
            Number totQty = (Number) getTatalQtyBsBind().getValue();
            Number totInspctnQty =
                ((Number) getRejectQtyBsBind().getValue()).add((Number) getRewrkQtyBsBind().getValue()).add(value);
            if (value.compareTo(zero) == -1) {
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                             "Quantity must be positive value.", null));  */
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['LBL.1270']}").toString(), null));

            } else if (totInspctnQty.compareTo(totQty) == 1) {
                /*    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                             "QC done Quantity exceeded Total QC Quantity. ", null));  */
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.693']}").toString(), null));
            }
        }
    }

    public void rejectQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number value = (Number) object;
        if (value != null && getTatalQtyBsBind().getValue() != null) {
            Number totQty = (Number) getTatalQtyBsBind().getValue(); // tot to totbs
            Number totInspctnQty =
                ((Number) getOkQtyBsBind().getValue()).add((Number) getRewrkQtyBsBind().getValue()).add(value); // ok to okbs , rewk to rewkbs
            if (value.compareTo(zero) == -1) {
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Quantity must be positive value.", null));  */
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['LBL.1270']}").toString(), null));

            } else if (totInspctnQty.compareTo(totQty) == 1) {
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "QC done Quantity exceeded Total QC Quantity. ", null));  */
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.693']}").toString(), null));

            }
        }
    }

    public void reworkQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number value = (Number) object;
        if (value != null && getTatalQtyBsBind().getValue() != null) {
            Number totQty = (Number) getTatalQtyBsBind().getValue();
            Number totInspctnQty =
                ((Number) getRejectQtyBsBind().getValue()).add((Number) getOkQtyBsBind().getValue()).add(value);
            if (value.compareTo(zero) == -1) {
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Quantity must be positive value.", null));  */
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['LBL.1270']}").toString(), null));
            } else if (totInspctnQty.compareTo(totQty) == 1) {
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "QC done Quantity exceeded Total QC Quantity. ", null));  */
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.693']}").toString(), null));
            }
        }
    }

    public String modeGet() {
        return (String) resolvEl("#{pageFlowScope.Add_Edit_Mode}");
    }

    /*  public void setOkQtyBind(RichInputText okQtyBind) {
        this.okQtyBind = okQtyBind;
    }

    public RichInputText getOkQtyBind() {
        return okQtyBind;
    }

    public void setRejectQtyBind(RichInputText rejectQtyBind) {
        this.rejectQtyBind = rejectQtyBind;
    }

    public RichInputText getRejectQtyBind() {
        return rejectQtyBind;
    }

    public void setRewrkQtyBind(RichInputText rewrkQtyBind) {
        this.rewrkQtyBind = rewrkQtyBind;
    }

    public RichInputText getRewrkQtyBind() {
        return rewrkQtyBind;
    }  */

    public void setDisable(String disable) {
        this.disable = disable;
    }

    public String getDisable() {
        return disable;
    }


    public void setReceiptNoBind(RichSelectOneChoice receiptNoBind) {
        this.receiptNoBind = receiptNoBind;
    }

    public RichSelectOneChoice getReceiptNoBind() {
        return receiptNoBind;
    }

    public void setDisableItmField(String disableItmField) {
        this.disableItmField = disableItmField;
    }

    public String getDisableItmField() {
        return disableItmField;
    }

    public void setQcStatusBind(RichSelectOneChoice qcStatusBind) {
        this.qcStatusBind = qcStatusBind;
    }

    public RichSelectOneChoice getQcStatusBind() {
        return qcStatusBind;
    }

    public void setActualValueBind(RichInputText actualValueBind) {
        this.actualValueBind = actualValueBind;
    }

    public RichInputText getActualValueBind() {
        return actualValueBind;
    }


    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        if (mode.equals("")) {
            return modeGet();
        } else {
            return mode;
        }
    }

    public String editDetailAction() {
        OperationBinding previousStatus = getBindings().getOperationBinding("itemPreviousStatus");
        previousStatus.execute();
        if (previousStatus.getResult() != null) {
            itmPreviousStatusVal = Integer.parseInt(previousStatus.getResult().toString());
        }
        loginfo.info("status value before edit  " + itmPreviousStatusVal);
        showPopup(detailPopup, true);
        return null;
    }

    private void showPopup(RichPopup pop, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null) {
                String popupId = pop.getClientId(context);
                if (popupId != null) {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible) {
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                    } else {
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setDetailPopup(RichPopup detailPopup) {
        this.detailPopup = detailPopup;
    }

    public RichPopup getDetailPopup() {
        return detailPopup;
    }


    public void editDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            OperationBinding editOpDlt = getBindings().getOperationBinding("editQcDtl");
            editOpDlt.getParamsMap().put("statusValue", itmPreviousStatusVal);
            editOpDlt.execute();
            OperationBinding commitItm = getBindings().getOperationBinding("Commit");
            commitItm.execute();
        }
    }

    public void popupCancelListener(PopupCanceledEvent popupCanceledEvent) {
    }

    public void QcTypeVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(receiptNoBind);
        //receiptNoBind.processUpdates(FacesContext.getCurrentInstance());
    }

    public void acptRjtAllItmVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if ("A".equalsIgnoreCase(vce.getNewValue().toString())) {
                showPopup(acptPopup, true);
            } else if ("R".equalsIgnoreCase(vce.getNewValue().toString())) {
                showPopup(rejctPopup, true);
            }
        }
    }

    public void setAcptPopup(RichPopup acptPopup) {
        this.acptPopup = acptPopup;
    }

    public RichPopup getAcptPopup() {
        return acptPopup;
    }

    public String geteditlinkDisable() {
        OperationBinding opStatus = getBindings().getOperationBinding("chkQcEditable");
        opStatus.execute();
        String stats = "N";
        if (opStatus.getResult() != null) {
            stats = opStatus.getResult().toString();
        }
        if ("Y".equalsIgnoreCase(stats)) {
            stats = "Y";
        }
        return stats;
    }

    public String getDisbaleDoneEdit() {
        OperationBinding disable = getBindings().getOperationBinding("isParameterEdiable");
        disable.execute();
        if (disable.getResult() != null && "N".equalsIgnoreCase(disable.getResult().toString())) {
            return "N";
        } else {
            return "Y";
        }
    }

    public String getDisbaleAccRejct() {
        OperationBinding accRjct = getBindings().getOperationBinding("isAcceptRejectEnable");
        accRjct.execute();
        if (accRjct.getResult() != null && "N".equalsIgnoreCase(accRjct.getResult().toString())) {
            return "N";
        } else {
            return "Y";
        }

        //        if(doneQcQtyBind.getValue()!=null){
        //            OperationBinding accRjct = getBindings().getOperationBinding("isAcceptRejectEnable");
        //            accRjct.execute();
        //        if(zero.compareTo((Number)doneQcQtyBind.getValue())==0){
        //            return "Y";
        //        }/* else if(((Number)tatalQtyBind.getValue()).compareTo((Number)doneQcQtyBind.getValue())==0) {
        //            return "Y";  //isAcceptRejectEnable
        //        } */else{
        //            return "N";
        //        }
        //        }else{
        //            return null;
        //        }
    }

    public String getDisabaleAllQty() {
        if (doneQcQtyBind.getValue() != null && tatalQtyBsBind.getValue() != null) {
            if (((Number) tatalQtyBsBind.getValue()).compareTo((Number) doneQcQtyBind.getValue()) == 0) {
                return "Y";
            } else {
                return "N";
            }
        } else {
            return null;
        }
    }

    public String getDisableRejctReason() {
        if (rejectQtyBsBind.getValue() != null) {
            if (((Number) rejectQtyBsBind.getValue()).compareTo(zero) > 0) {
                return "N";
            } else {
                return "Y";
            }
        } else {
            return "Y";
        }
    }

    public String getDisableAll() {
        if (doneQcQtyBind.getValue() != null) {
            if (zero.compareTo((Number) doneQcQtyBind.getValue()) == 0) {
                return "Y";
            } else {
                return "N";
            }
        } else {
            return "N";
        }
    }

    public String getReqRejectReason() {
        if (rejectQtyBsBind.getValue() != null) {
            if (((Number) rejectQtyBsBind.getValue()).compareTo(zero) > 0) {
                return "N";
            } else {
                return "Y";
            }
        } else {
            return "Y";
        }
    }

    public void setRejctPopup(RichPopup rejctPopup) {
        this.rejctPopup = rejctPopup;
    }

    public RichPopup getRejctPopup() {
        return rejctPopup;
    }

    public void acceptAllDailogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {

            okQtyBsBind.setValue((Number) tatalQtyBsBind.getValue());
            rejectQtyBsBind.setValue(zero);
            rewrkQtyBsBind.setValue(zero);
            doneQcQtyBind.setValue((Number) tatalQtyBsBind.getValue());
            OperationBinding updateSpValue = getBindings().getOperationBinding("updateSpQtyAccRej");
            updateSpValue.getParamsMap().put("value", "A");
            updateSpValue.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(okQtyBsBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rejectQtyBsBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rewrkQtyBsBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(doneQcQtyBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(accptRjctBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(inspctnQtyBind);
        } else {
            accptRjctBind.setValue("N");
            AdfFacesContext.getCurrentInstance().addPartialTarget(accptRjctBind);
        }
    }

    public void rejectAllDailogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
            okQtyBsBind.setValue(zero);
            rejectQtyBsBind.setValue((Number) tatalQtyBsBind.getValue());
            rewrkQtyBsBind.setValue(zero);
            doneQcQtyBind.setValue((Number) tatalQtyBsBind.getValue());
            OperationBinding updateSpValue = getBindings().getOperationBinding("updateSpQtyAccRej");
            updateSpValue.getParamsMap().put("value", "R");
            updateSpValue.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(okQtyBsBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rejectQtyBsBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rewrkQtyBsBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(doneQcQtyBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(accptRjctBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(inspctnQtyBind);
        } else {
            accptRjctBind.setValue("N");
            AdfFacesContext.getCurrentInstance().addPartialTarget(accptRjctBind);
        }
    }

    public void setAccptRjctBind(RichSelectOneRadio accptRjctBind) {
        this.accptRjctBind = accptRjctBind;
    }

    public RichSelectOneRadio getAccptRjctBind() {
        return accptRjctBind;
    }

    public void srNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (getTatalQtyBsBind().getValue() != null && getDoneQcQtyBind().getValue() != null) {
                Number qcTotQty = (Number) (getTatalQtyBsBind().getValue());
                Number qcDoneQty = (Number) (getDoneQcQtyBind().getValue());
                Number qcReqQty = (Number) qcTotQty.minus(qcDoneQty);
                if (qcReqQty.compareTo(zero) == 0) {
                } else {
                    String srNo = (String) object;
                    String stats = "N";
                    OperationBinding opStatus = getBindings().getOperationBinding("isSrNoDuplicate");
                    opStatus.getParamsMap().put("SrNoValue", srNo);
                    opStatus.execute();
                    if (opStatus.getResult() != null) {
                        stats = opStatus.getResult().toString();
                    }
                    if ("Y".equalsIgnoreCase(stats)) {
                        /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                               "Qc Done For this Sr No.", null));  */
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvEl("#{bundle['MSG.694']}").toString(),
                                                                      null));
                    }
                }
            }
        }
    }

    public void receiptNoValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String docIdSrc = (String) object;
            loginfo.info(" doc id src -- " + docIdSrc);
            OperationBinding receiptNoval = getBindings().getOperationBinding("isReceiptNoDuplicate");
            receiptNoval.getParamsMap().put("value", docIdSrc);
            receiptNoval.execute();
            String valueRet = "N";
            if (receiptNoval.getResult() != null) {
                valueRet = receiptNoval.getResult().toString();
            }
            if ("Y".equalsIgnoreCase(valueRet)) {
                /* throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Duplicate Receipt No found.", null));  */
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.695']}").toString(), null));

            }
        }
    }


    public String getuomFactorCnvr() {
        String retVal = "Y";
        if (uomCnvrFactBind.getValue() != null) {
            if (one.compareTo((Number) (uomCnvrFactBind.getValue())) == 0) {
                retVal = "N";
            }
        }
        return retVal;
    }

    public void setOkQtyBsBind(RichInputText okQtyBsBind) {
        this.okQtyBsBind = okQtyBsBind;
    }

    public RichInputText getOkQtyBsBind() {
        return okQtyBsBind;
    }

    public void setRejectQtyBsBind(RichInputText rejectQtyBsBind) {
        this.rejectQtyBsBind = rejectQtyBsBind;
    }

    public RichInputText getRejectQtyBsBind() {
        return rejectQtyBsBind;
    }

    public void setRewrkQtyBsBind(RichInputText rewrkQtyBsBind) {
        this.rewrkQtyBsBind = rewrkQtyBsBind;
    }

    public RichInputText getRewrkQtyBsBind() {
        return rewrkQtyBsBind;
    }

    public void setTatalQtyBsBind(RichInputText tatalQtyBsBind) {
        this.tatalQtyBsBind = tatalQtyBsBind;
    }

    public RichInputText getTatalQtyBsBind() {
        return tatalQtyBsBind;
    }

    public void setUomCnvrFactBind(RichInputText uomCnvrFactBind) {
        this.uomCnvrFactBind = uomCnvrFactBind;
    }

    public RichInputText getUomCnvrFactBind() {
        return uomCnvrFactBind;
    }

    public void setParamTableBind(RichTable paramTableBind) {
        this.paramTableBind = paramTableBind;
    }

    public RichTable getParamTableBind() {
        return paramTableBind;
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void actualValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number value = (Number) object;
        if (value != null) {
            Boolean flag = isPrecisionValid(26, 6, value);
            if (flag.equals(false)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.405']}").toString(),
                                                              null)); // Invalid Precision scale (26,6)
            }
        }
    }

    public void actualValpopupValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number value = (Number) object;
        if (value != null) {
            Boolean flag = isPrecisionValid(26, 6, value);
            if (flag.equals(false)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.405']}").toString(),
                                                              null)); // Invalid Precision scale (26,6)
            }
        }
    }

    //    public void setSrNoBind(RichSelectOneChoice srNoBind) {
    //        this.srNoBind = srNoBind;
    //    }
    //
    //    public RichSelectOneChoice getSrNoBind() {
    //        return srNoBind;
    //    }

    public void addParameterAndSrNo(ActionEvent actionEvent) {
        // Add event code here...  addCsnAndParameter
        loginfo.info("log info");
        if (getTatalQtyBsBind().getValue() != null && getDoneQcQtyBind().getValue() != null) {
            Number qcTotQty = (Number) (getTatalQtyBsBind().getValue());
            Number qcDoneQty = (Number) (getDoneQcQtyBind().getValue());
            Number qcReqQty = (Number) qcTotQty.minus(qcDoneQty);
            String stats = "N";
            String srReq = "N";
            OperationBinding srStatus = getBindings().getOperationBinding("isSrNoRequired");
            srStatus.execute();
            if (srStatus.getResult() != null) {
                srReq = srStatus.getResult().toString();
            }
            if (qcReqQty.compareTo(zero) == 0) {
                OperationBinding resetSr = getBindings().getOperationBinding("resetSrNo");
                resetSr.execute();

                FacesMessage msg =

                    //new FacesMessage(FacesMessage.SEVERITY_ERROR,"All Item Quantity checked for particular item.Please select next Item.", null);
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.686']}").toString(), null);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, msg);
                return;
            }
            if ("Y".equalsIgnoreCase(srReq)) {
                /* OperationBinding resetSr = getBindings().getOperationBinding("resetSrNo");
                               resetSr.execute(); */

                srNoBind.resetValue();
                AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                FacesMessage msg =
                    //new FacesMessage(FacesMessage.SEVERITY_ERROR,"Select Serial no for Qc.", null);
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.687']}").toString(), null);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, msg);
                return;
            } else if ("X".equalsIgnoreCase(srReq)) {

                showFacesMessage("Total check Quantity must be greater than zero", "W", false, "F", null);
                return;
            }
            Integer tableCount = 0;
            Integer tableR = qcCsnTableBind.getRowCount();
            tableCount = tableR + 1;

            OperationBinding receiptNoval = getBindings().getOperationBinding("addCsnAndParameter");
            receiptNoval.getParamsMap().put("count", tableCount);
            receiptNoval.execute();
        }
    }

    public void setQcCsnTableBind(RichTable qcCsnTableBind) {
        this.qcCsnTableBind = qcCsnTableBind;
    }

    public RichTable getQcCsnTableBind() {
        return qcCsnTableBind;
    }

    public void actualValueDltValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...

        Number value = (Number) object;
        if (value != null) {
            Boolean flag = isPrecisionValid(26, 6, value);
            if (flag.equals(false)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.405']}").toString(),
                                                              null)); // Invalid Precision scale (26,6)
            }
        }

    }

    public void doneQcProcessSingleAction(ActionEvent actionEvent) {

        Integer statsDone = 0;
        OperationBinding opStatusDone = getBindings().getOperationBinding("isProcessCompleteForSingleSr");
        opStatusDone.execute();
        if (opStatusDone.getResult() != null) {
            statsDone = Integer.parseInt(opStatusDone.getResult().toString());
        }
        if (statsDone.compareTo(new Integer(1)) == 0) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Qc Process Complete for select Check Sr No .", null);
            // new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvEl("#{bundle['MSG.688']}").toString(), null);


            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, msg);
            return;
        }

        String stats = "N";
        OperationBinding opStatus = getBindings().getOperationBinding("isParamStatusTrueDlt");
        opStatus.execute();
        if (opStatus.getResult() != null) {
            stats = opStatus.getResult().toString();
        }

        /* String srReq= "N";
         OperationBinding srStatus = getBindings().getOperationBinding("isSrNoRequired");
                     srStatus.execute();
                     if(srStatus.getResult()!=null){
                         srReq=srStatus.getResult().toString();
                     } */

        if ("Y".equalsIgnoreCase(stats)) {
            FacesMessage msg =
                //new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fill the actual value.", null);
                new FacesMessage(FacesMessage.SEVERITY_WARN, resolvEl("#{bundle['MSG.688']}").toString(), null);


            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, msg);
            return;
        }

        OperationBinding doneQcSingle = getBindings().getOperationBinding("doneQcProcessForSingleItm");
        doneQcSingle.execute();
    }

    public void editChkSrNoAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding ediQcSingle = getBindings().getOperationBinding("editChkSrNoAction");
        ediQcSingle.execute();
    }

    public void setQcForDocTypeBind(RichSelectOneChoice qcForDocTypeBind) {
        this.qcForDocTypeBind = qcForDocTypeBind;
    }

    public RichSelectOneChoice getQcForDocTypeBind() {
        return qcForDocTypeBind;
    }

    public void setJeCompletePopup(RichPopup jeCompletePopup) {
        this.jeCompletePopup = jeCompletePopup;
    }

    public RichPopup getJeCompletePopup() {
        return jeCompletePopup;
    }

    public String saveQcForJe() {

        OperationBinding updateRcpt = getBindings().getOperationBinding("updateReceiptTable");
        updateRcpt.execute();
        if (updateRcpt.getResult() != null) {
            String retVal = updateRcpt.getResult().toString();
            loginfo.info("retVal--------" + retVal);
            if ("Y".equalsIgnoreCase(retVal)) {
                loginfo.info("Inside second save    -------------------------");
                OperationBinding commitRcptUpdate = getBindings().getOperationBinding("Commit");
                commitRcptUpdate.execute();
                setDisableItmField("V");
                setDisable("V");
                setMode("V");
                FacesMessage msg =
                    //new FacesMessage(FacesMessage.SEVERITY_INFO,"Qc Process Complete and Save SuccessFully .", null);
                    new FacesMessage(FacesMessage.SEVERITY_INFO, resolvEl("#{bundle['MSG.689']}").toString(), null);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, msg);
                jeCompletePopup.hide();
            } else if ("Y".equalsIgnoreCase(retVal)) {
                FacesMessage msg =
                    //new FacesMessage(FacesMessage.SEVERITY_ERROR,"Some Error Occur Updating Receipt table .", null);
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['MSG.690']}").toString(), null);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, msg);
                jeCompletePopup.hide();
            }
        }
        jeCompletePopup.hide();
        return null;
    }

    public void setTranscheckQtybind(RichInputText transcheckQtybind) {
        this.transcheckQtybind = transcheckQtybind;
    }

    public RichInputText getTranscheckQtybind() {
        return transcheckQtybind;
    }

    public void setSrNoBind(RichInputListOfValues srNoBind) {
        this.srNoBind = srNoBind;
    }

    public RichInputListOfValues getSrNoBind() {
        return srNoBind;
    }

    public void viewRcptItmDetailsAction(ActionEvent actionEvent) {
        OperationBinding updateRcpt = getBindings().getOperationBinding("viewRcptItmDetails");
        updateRcpt.getParamsMap().put("docIdSrc", receiptNoBind.getValue());
        updateRcpt.execute();
        showPopup(viewRcptItmDetailsPopup, true);

    }

    public void setViewRcptItmDetailsPopup(RichPopup viewRcptItmDetailsPopup) {
        this.viewRcptItmDetailsPopup = viewRcptItmDetailsPopup;
    }

    public RichPopup getViewRcptItmDetailsPopup() {
        return viewRcptItmDetailsPopup;
    }
}
