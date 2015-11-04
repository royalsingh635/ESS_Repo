package fadepreciationapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class FADepreciationBean {
    private RichSelectOneChoice transLawTypBind;
    private String mode = "V";
    private RichSelectOneChoice transFyIdBind;
    private RichSelectOneChoice transPrdBind;
    private RichInputDate transFyStrtBind;
    private RichInputDate transFyEndBind;
    private RichPopup itemHistoryPopUpBind;
    private RichPopup popUpP2Binding;

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public FADepreciationBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    String workFlowId = null;
    String execId = null;

    public void setExecId(String execId) {
        this.execId = execId;
    }

    public String getExecId() {
        if (ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_APP_MOD}").toString().equalsIgnoreCase("V")) {
            return ADFModelUtils.resolvEl("#{pageFlowScope.PARAM_APP_EXEID}").toString();
        } else {
            return execId;
        }
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    public String getWorkFlowId() {
        return workFlowId;
    }

    public String saveAndForwrdAction() {
        Integer currUser = EbizParams.GLBL_APP_USR();
        Integer pendingUsr = 0;

        Integer userLevel = 0;
        BindingContainer binding = getBindings();
        Object ob = ADFBeanUtils.callBindingsMethod("getCurrentpendingUser", new Object[] { 20002, execId, 0 }, new Object[] {
                                                    "docId", "txnDocId", "docTypId"
        });
        if (ob != null)
            pendingUsr = Integer.parseInt(ob.toString());
        if (currUser == pendingUsr || pendingUsr == -1) {
            ob = ADFBeanUtils.callBindingsMethod("getWFId", new Object[] { 20002, 0 }, new Object[] {
                                                 "docId", "docTypId" });
            if (ob != null) {
                workFlowId = ob.toString();
                System.out.println("workfloww idd is==" + workFlowId);
            }
        }
        if (workFlowId != null && !("0".toString().equalsIgnoreCase("workFlowId"))) {
            ob = ADFBeanUtils.callBindingsMethod("getUserLevel", new Object[] { workFlowId, 20002, 0 }, new Object[] {
                                                 "wfid", "docId", "docTypId"
            });
            if (ob != null) {
                userLevel = Integer.parseInt(ob.toString());
            }
            if (userLevel != null) {
                ADFBeanUtils.callBindingsMethod("insertInWFTxn", new Object[] {
                                                20002, 0, workFlowId, execId, userLevel }, new Object[] {
                                                "docId", "docTypId", "wfId", "depExecId", "level"
                });
            }
        }
        else if(workFlowId == null){
                ADFModelUtils.showFacesMessage("Work Flow not defined.", "Work Flow not defined.", FacesMessage.SEVERITY_ERROR,null);
                
            }
        System.out.println("Under Construction !!");

        return "forward";
    }

    public void depreciationProcessAL(ActionEvent actionEvent) {
        if (transLawTypBind.getValue().toString().equalsIgnoreCase("CO") &&
            transPrdBind.getValue().toString().equalsIgnoreCase("")) {
            ADFModelUtils.showFacesMessage("select period value.", "select period value.", FacesMessage.SEVERITY_ERROR,
                                           transPrdBind.getClientId());
        } else {
            Object ob = ADFBeanUtils.callBindingsMethod("chkStrtDtEndDt", null, null);
            if (ob != null) {
                if (ob.toString().equalsIgnoreCase("Y")) {
                    ob = ADFBeanUtils.callBindingsMethod("processDepreciation", null, null);
                    if (ob != null) {
                        if (ob.toString().equalsIgnoreCase("E")) {
                            ADFModelUtils.showFacesMessage("There is no item to process for this month.",
                                                           "There is no item to process for this month.",
                                                           FacesMessage.SEVERITY_ERROR, null);
                        } else {
                            execId = ob.toString();
                            ADFBeanUtils.callBindingsMethod("setBindValiables", null, null);
                            System.out.println("InvoiceCountMethodAM is statrted");
                            Object Invoice = ADFBeanUtils.callBindingsMethod("InvoiceCountMethodAM", null, null);
                            System.out.println("InvoiceCountMethodAM is end");

                            if (Invoice == "Y") {
                                ADFBeanUtils.callBindingsMethod("Commit", null, null);
                                if (transLawTypBind.getValue() != null) {
                                    if (transLawTypBind.getValue().toString().equalsIgnoreCase("CO"))
                                        mode = "CO";
                                    else if (transLawTypBind.getValue().toString().equalsIgnoreCase("IT"))
                                        mode = "IT";
                                    else
                                        mode = "V";
                                }
                            } else {
                                showPopup(popUpP2Binding, true);


                            }

                        }
                    }
                } else if (ob.toString().equalsIgnoreCase("N")) {
                    ADFModelUtils.showFacesMessage("Depreciation is not allowed for the selected date.",
                                                   "Depreciation is not allowed for the selected date.",
                                                   FacesMessage.SEVERITY_ERROR, null);
                } else {
                    ADFModelUtils.showFacesMessage("Please Regiester items before processing.",
                                                   "Please Regiester items before processing.",
                                                   FacesMessage.SEVERITY_ERROR, null);
                }
            }
        }
    }

    public void setTransLawTypBind(RichSelectOneChoice transLawTypBind) {
        this.transLawTypBind = transLawTypBind;
    }

    public RichSelectOneChoice getTransLawTypBind() {
        return transLawTypBind;
    }

    public void lawTypVCL(ValueChangeEvent valueChangeEvent) {
        ADFBeanUtils.callBindingsMethod("setAttVal", null, null);
    }

    public void setTransFyIdBind(RichSelectOneChoice transFyIdBind) {
        this.transFyIdBind = transFyIdBind;
    }

    public RichSelectOneChoice getTransFyIdBind() {
        return transFyIdBind;
    }

    public void setTransPrdBind(RichSelectOneChoice transPrdBind) {
        this.transPrdBind = transPrdBind;
    }

    public RichSelectOneChoice getTransPrdBind() {
        return transPrdBind;
    }

    public void setTransFyStrtBind(RichInputDate transFyStrtBind) {
        this.transFyStrtBind = transFyStrtBind;
    }

    public RichInputDate getTransFyStrtBind() {
        return transFyStrtBind;
    }

    public void setTransFyEndBind(RichInputDate transFyEndBind) {
        this.transFyEndBind = transFyEndBind;
    }

    public RichInputDate getTransFyEndBind() {
        return transFyEndBind;
    }

    public String setModForSerchTF() {
        if (ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_APP_MOD}").toString().equalsIgnoreCase("V") &&
            ADFModelUtils.resolvEl("#{pageFlowScope.PARAM_LAW_TYP_SRCH}").toString().equalsIgnoreCase("CO"))
            mode = "CO";
        else if (ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_APP_MOD}").toString().equalsIgnoreCase("V") &&
                 ADFModelUtils.resolvEl("#{pageFlowScope.PARAM_LAW_TYP_SRCH}").toString().equalsIgnoreCase("IT"))
            mode = "IT";
        return "abc";
    }


    public void viewItmHistoryAL(ActionEvent actionEvent) {
        ADFBeanUtils.showPopup(itemHistoryPopUpBind, true);
    }

    public void setItemHistoryPopUpBind(RichPopup itemHistoryPopUpBind) {
        this.itemHistoryPopUpBind = itemHistoryPopUpBind;
    }

    public RichPopup getItemHistoryPopUpBind() {
        return itemHistoryPopUpBind;
    }

    public String viewCOItmHistory() {
        ADFBeanUtils.callBindingsMethod("setGlblParamforItemHostory", null, null);
        return "viewHistory";
    }

    public String viewItItmHistory() {
        ADFBeanUtils.callBindingsMethod("setGlblParamforItemHostory", null, null);
        return "viewHistory";
    }

    public void faDeleteDepProcessActionListener(ActionEvent actionEvent) {
        ADFBeanUtils.callBindingsMethod("depProcessDellete", null, null);
        mode = "V";
    }

    public void PopupactionListener(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().toString().equalsIgnoreCase("ok")) {
            ADFBeanUtils.callBindingsMethod("Commit", null, null);
            if (transLawTypBind.getValue() != null) {
                if (transLawTypBind.getValue().toString().equalsIgnoreCase("CO"))
                    mode = "CO";
                else if (transLawTypBind.getValue().toString().equalsIgnoreCase("IT"))
                    mode = "IT";
                else
                    mode = "V";
            }
        }

    }

    public void PopupCancelListener(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
        ADFBeanUtils.callBindingsMethod("Rollback", null, null);
    }

    public void setPopUpP2Binding(RichPopup popUpP2Binding) {
        this.popUpP2Binding = popUpP2Binding;
    }

    public RichPopup getPopUpP2Binding() {
        return popUpP2Binding;
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
}
