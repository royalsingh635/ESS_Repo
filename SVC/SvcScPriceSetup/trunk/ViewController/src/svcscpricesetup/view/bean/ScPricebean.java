package svcscpricesetup.view.bean;


import adf.utils.model.ADFModelUtils;

import java.util.List;

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
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class ScPricebean {
    private RichPanelGroupLayout locPnlGrpBind;
    private RichPanelGroupLayout itmPnlPrpBind;
    private RichPanelGroupLayout eoPnlGrpBind;
    private RichSelectOneRadio priceTypeBind;
    private RichPopup pricePopUpBind;

    private RichSelectOneRadio priceTypeItmBind1;
    private RichSelectBooleanCheckbox entityWiseCBBind;
    private RichSelectOneRadio entityTypeBind;
    private RichInputListOfValues customerLovBind;
    private RichOutputText priceValTableBind;


    public ScPricebean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void addPriceSetup(ActionEvent actionEvent) {
        OperationBinding obind = getBindings().getOperationBinding("CreateInsert");
        obind.execute();
    }

    public void editPriceAction(ActionEvent actionEvent) {

        RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "E");

    }

    public void saveActionListener(ActionEvent actionEvent) {
        //priceNoGenerate
        String action = "I";
        String remark = "A";
        String WfNum = null;
        Integer level = -1;
        Integer res = -1;
        //write code here for calling WF
        Integer usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        //get wf no
        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
        WfnoOp.getParamsMap().put("sloc_id", sloc_Id);
        WfnoOp.getParamsMap().put("cld_id", cld_Id);
        WfnoOp.getParamsMap().put("org_id", org_Id);
        WfnoOp.getParamsMap().put("doc_no", 23011);
        WfnoOp.execute();
        if (WfnoOp.getErrors().size() == 0) {
            if (WfnoOp.getResult() != null) {
                WfNum = WfnoOp.getResult().toString();
            }
            System.out.println("Wf No=" + WfNum);
        } else
            System.out.println("Error getting wfno=" + WfnoOp.getErrors());
        if (WfNum != null && !"0".equalsIgnoreCase(WfNum)) {
            //get user level
            OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
            usrLevelOp.getParamsMap().put("SlocId", sloc_Id);
            usrLevelOp.getParamsMap().put("CldId", cld_Id);
            usrLevelOp.getParamsMap().put("OrgId", org_Id);
            usrLevelOp.getParamsMap().put("usr_id", usr_Id);
            usrLevelOp.getParamsMap().put("WfNum", WfNum);
            usrLevelOp.getParamsMap().put("RfqDocId", 23011);
            usrLevelOp.execute();
            if (usrLevelOp.getErrors().size() == 0) {
                if (usrLevelOp.getResult() != null) {
                    level = Integer.parseInt(usrLevelOp.getResult().toString());

                }
                System.out.println("Level=" + level);
            } else
                System.out.println("Error getting usrlevel=" + usrLevelOp.getErrors());
            if (level >= 0) {
                //insert into txn
                OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                insTxnOp.getParamsMap().put("sloc_id", sloc_Id);
                insTxnOp.getParamsMap().put("cld_id", cld_Id);
                insTxnOp.getParamsMap().put("pOrgId", org_Id);
                insTxnOp.getParamsMap().put("RFQ_DOC_NO", 23011);
                insTxnOp.getParamsMap().put("WfNum", WfNum);
                //TXN PASSED BY GETTING IT IN AMIMPL...
                insTxnOp.getParamsMap().put("usr_idFrm", usr_Id);
                insTxnOp.getParamsMap().put("usr_idTo", usr_Id);
                insTxnOp.getParamsMap().put("levelFrm", level);
                insTxnOp.getParamsMap().put("levelTo", level);
                insTxnOp.getParamsMap().put("action", action);
                insTxnOp.getParamsMap().put("remark", remark);
                insTxnOp.getParamsMap().put("amount", 0);
                insTxnOp.execute();
                if (insTxnOp.getErrors().size() == 0) {
                    if (insTxnOp.getResult() != null) {
                        res = Integer.parseInt(insTxnOp.getResult().toString());
                        System.out.println("Inserted into txn-> Res=" + res);
                    }
                } else
                    System.out.println("error ins into insIntoTxn=" + insTxnOp.getErrors());
                //  System.out.println("Id is="+actionEvent.getComponent().getClientId());
                OperationBinding obind1 = getBindings().getOperationBinding("priceNoGenerate");
                obind1.execute();
                OperationBinding obind = getBindings().getOperationBinding("Commit");
                obind.execute();

                RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
                //String msg="Saved Successfully".
                String msg = ADFModelUtils.resolvRsrc("MSG.227");
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);

            } else {
                //String msg="Something went wrong(Getting user level in Workflow).Please contact to ESS!"
                String msg = ADFModelUtils.resolvRsrc("MSG.2469");
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        } else {
            //String msg="Workflow not Define for MRS."
            String msg = ADFModelUtils.resolvRsrc("MSG.2470");
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }

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

    public void saveandFwdActionListener(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void cancelActionListner(ActionEvent actionEvent) {
        OperationBinding obind = getBindings().getOperationBinding("Rollback");
        obind.execute(); // Add event code here...
    }

    public void InsertAllData(ActionEvent actionEvent) {
        System.out.println(" insertAlldate value " + entityWiseCBBind.getValue() + "  " + entityTypeBind.getValue());
        if ((entityWiseCBBind.getValue() != null && entityTypeBind.getValue() != null) &&
            (entityWiseCBBind.getValue().toString().length() > 0 &&
             entityTypeBind.getValue().toString().length() > 0)) {
            ///    System.out.println(" insertAlldate after value "+entityWiseCBBind.getValue()+"  "+entityTypeBind.getValue()+"--  ");
            if (entityWiseCBBind.getValue().toString().equalsIgnoreCase("true") &&
                entityTypeBind.getValue().toString().equalsIgnoreCase("E")) {
                // System.out.println("sop indie -----");
                if (customerLovBind.getValue() == null) {
                    //               System.out.println(" inside false-------------"+customerLovBind.getValidator().toString());

                    //  showFacesMessage("Customer Name is required.", "E", false, "F", customerLovBind.getClientId());
                    //  return null;
                    FacesMessage message = new FacesMessage("Customer Name is required.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(customerLovBind.getClientId(), message);
                    //  return null;
                } else {
                    //       System.out.println(" inside true-------------");

                    OperationBinding obind = getBindings().getOperationBinding("insertIntoItmPrice");
                    obind.execute();
                    //        RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "E");

                }


            } else {
                //System.out.println(" else pat inside true-------------");

                OperationBinding obind = getBindings().getOperationBinding("insertIntoItmPrice");
                obind.execute();
                //  RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "E");
            }
        }
        /* else{
        */
        //        OperationBinding obind = getBindings().getOperationBinding("insertIntoItmPrice");
        //        obind.execute();

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

    public String saveAndFwdAction() {

        String action = "I";
        String remark = "A";
        String WfNum = null;
        Integer level = -1;
        Integer res = -1;
        //write code here for calling WF
        Integer usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        //get wf no
        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
        WfnoOp.getParamsMap().put("sloc_id", sloc_Id);
        WfnoOp.getParamsMap().put("cld_id", cld_Id);
        WfnoOp.getParamsMap().put("org_id", org_Id);
        WfnoOp.getParamsMap().put("doc_no", 23011);
        WfnoOp.execute();
        if (WfnoOp.getErrors().size() == 0) {
            if (WfnoOp.getResult() != null) {
                WfNum = WfnoOp.getResult().toString();
            }
            System.out.println("Wf No=" + WfNum);
        } else
            System.out.println("Error getting wfno=" + WfnoOp.getErrors());
        if (WfNum != null && !"0".equalsIgnoreCase(WfNum)) {
            //get user level
            OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
            usrLevelOp.getParamsMap().put("SlocId", sloc_Id);
            usrLevelOp.getParamsMap().put("CldId", cld_Id);
            usrLevelOp.getParamsMap().put("OrgId", org_Id);
            usrLevelOp.getParamsMap().put("usr_id", usr_Id);
            usrLevelOp.getParamsMap().put("WfNum", WfNum);
            usrLevelOp.getParamsMap().put("RfqDocId", 23011);
            usrLevelOp.execute();
            if (usrLevelOp.getErrors().size() == 0) {
                if (usrLevelOp.getResult() != null) {
                    level = Integer.parseInt(usrLevelOp.getResult().toString());

                }
                System.out.println("Level=" + level);
            } else
                System.out.println("Error getting usrlevel=" + usrLevelOp.getErrors());
            if (level >= 0) {
                //insert into txn
                OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                insTxnOp.getParamsMap().put("sloc_id", sloc_Id);
                insTxnOp.getParamsMap().put("cld_id", cld_Id);
                insTxnOp.getParamsMap().put("pOrgId", org_Id);
                insTxnOp.getParamsMap().put("RFQ_DOC_NO", 23011);
                insTxnOp.getParamsMap().put("WfNum", WfNum);
                //TXN PASSED BY GETTING IT IN AMIMPL...
                insTxnOp.getParamsMap().put("usr_idFrm", usr_Id);
                insTxnOp.getParamsMap().put("usr_idTo", usr_Id);
                insTxnOp.getParamsMap().put("levelFrm", level);
                insTxnOp.getParamsMap().put("levelTo", level);
                insTxnOp.getParamsMap().put("action", action);
                insTxnOp.getParamsMap().put("remark", remark);
                insTxnOp.getParamsMap().put("amount", 0);
                insTxnOp.execute();
                if (insTxnOp.getErrors().size() == 0) {
                    if (insTxnOp.getResult() != null) {
                        res = Integer.parseInt(insTxnOp.getResult().toString());
                        System.out.println("Inserted into txn-> Res=" + res);
                    }
                } else
                    System.out.println("error ins into insIntoTxn=" + insTxnOp.getErrors());
                OperationBinding obind1 = getBindings().getOperationBinding("priceNoGenerate");
                obind1.execute();
                //  System.out.println("Id is="+actionEvent.getComponent().getClientId());
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
                RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");

                return "wf";
            } else {
                //String msg=""Something went wrong(Getting user level in Workflow).Please contact to ESS!""
                String msg = ADFModelUtils.resolvRsrc("MSG.2469");
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                return null;
            }
        } else {
            //String msg="Workflow not Define for Service Price Setup."
            String msg = ADFModelUtils.resolvRsrc("MSG.2471");
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return null;
        }

    }

    public void resetValueInputItems(AdfFacesContext adfFacesContext, UIComponent component) {
        List<UIComponent> items = component.getChildren();
        for (UIComponent item : items) {

            resetValueInputItems(adfFacesContext, item);

            if (item instanceof RichInputText) {
                RichInputText input = (RichInputText) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputDate) {
                RichInputDate input = (RichInputDate) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichSelectOneChoice) {
                RichSelectOneChoice input = (RichSelectOneChoice) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputListOfValues) {
                RichInputListOfValues input = (RichInputListOfValues) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            }

        }
    }

    public void locFlgVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0) {
            Boolean val = (Boolean) valueChangeEvent.getNewValue();
            if (val) {

            } else {
                OperationBinding obind = getBindings().getOperationBinding("resetValue");
                obind.execute();
                resetValueInputItems(AdfFacesContext.getCurrentInstance(), locPnlGrpBind);
            }
        }
    }

    public void ItmFlgVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0) {
            Boolean val = (Boolean) valueChangeEvent.getNewValue();
            if (val) {

            } else {
                OperationBinding obind = getBindings().getOperationBinding("resetValue");
                obind.execute();

                resetValueInputItems(AdfFacesContext.getCurrentInstance(), itmPnlPrpBind);
            }
        }
    }

    public void EoFlgVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0) {
            Boolean val = (Boolean) valueChangeEvent.getNewValue();
            if (val) {

            } else {
                OperationBinding obind = getBindings().getOperationBinding("resetValue");
                obind.execute();

                resetValueInputItems(AdfFacesContext.getCurrentInstance(), eoPnlGrpBind);
            }
        }
    }

    public void setLocPnlGrpBind(RichPanelGroupLayout locPnlGrpBind) {
        this.locPnlGrpBind = locPnlGrpBind;
    }

    public RichPanelGroupLayout getLocPnlGrpBind() {
        return locPnlGrpBind;
    }

    public void setItmPnlPrpBind(RichPanelGroupLayout itmPnlPrpBind) {
        this.itmPnlPrpBind = itmPnlPrpBind;
    }

    public RichPanelGroupLayout getItmPnlPrpBind() {
        return itmPnlPrpBind;
    }

    public void setEoPnlGrpBind(RichPanelGroupLayout eoPnlGrpBind) {
        this.eoPnlGrpBind = eoPnlGrpBind;
    }

    public RichPanelGroupLayout getEoPnlGrpBind() {
        return eoPnlGrpBind;
    }

    public void setPriceTypeBind(RichSelectOneRadio priceTypeBind) {
        this.priceTypeBind = priceTypeBind;
    }

    public RichSelectOneRadio getPriceTypeBind() {
        return priceTypeBind;
    }

    public void priceValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        Number value = (Number) object;
        System.out.println("Value" + value);
        System.out.println("radio value :::: " + this.priceTypeBind.getValue());
        if (object != null) {
            if (this.priceTypeBind.getValue().equals("P")) {
                System.out.println("Price Type" + priceTypeBind.getValue());
                if (value.compareTo(new Number(0)) <= 0 || value.compareTo(new Number(100)) > 0) {
                    System.out.println("value" + value);
                    //String msg="Value must be between 1 to 100"
                    String msg = ADFModelUtils.resolvRsrc("MSG.2472");
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            } else {
                System.out.println("Price Type" + priceTypeBind.getValue());
                if (value.compareTo(new Number(0)) <= 0) {
                    System.out.println("value" + value);
                    //String msg="Value must be greater than 0."
                    String msg = ADFModelUtils.resolvRsrc("MSG.2473");
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }

    }

    public void editPriceAL(ActionEvent actionEvent) {
        // Add event code here...
        showPopup(pricePopUpBind, true);
        String Mode = "E";


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

    public void setPricePopUpBind(RichPopup pricePopUpBind) {
        this.pricePopUpBind = pricePopUpBind;
    }

    public RichPopup getPricePopUpBind() {
        return pricePopUpBind;
    }


    public void priceValItmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        Number value = (Number) object;
        System.out.println("Value" + value);
        System.out.println("radio value :::: " + this.getPriceTypeItmBind1().getValue());
        if (object != null) {
            if (this.getPriceTypeItmBind1().getValue().equals("P")) {
                System.out.println("Price Type" + getPriceTypeItmBind1().getValue());
                if (value.compareTo(new Number(0)) <= 0 || value.compareTo(new Number(100)) > 0) {
                    System.out.println("value" + value);
                    //String msg="Value must be between 1 to 100"
                    String msg = ADFModelUtils.resolvRsrc("MSG.2472");
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }
    }

    public void setPriceTypeItmBind1(RichSelectOneRadio priceTypeItmBind1) {
        this.priceTypeItmBind1 = priceTypeItmBind1;
    }

    public RichSelectOneRadio getPriceTypeItmBind1() {
        return priceTypeItmBind1;
    }

    public void setEntityWiseCBBind(RichSelectBooleanCheckbox entityWiseCBBind) {
        this.entityWiseCBBind = entityWiseCBBind;
    }

    public RichSelectBooleanCheckbox getEntityWiseCBBind() {
        return entityWiseCBBind;
    }

    public void setEntityTypeBind(RichSelectOneRadio entityTypeBind) {
        this.entityTypeBind = entityTypeBind;
    }

    public RichSelectOneRadio getEntityTypeBind() {
        return entityTypeBind;
    }

    public void setCustomerLovBind(RichInputListOfValues customerLovBind) {
        this.customerLovBind = customerLovBind;
    }

    public RichInputListOfValues getCustomerLovBind() {
        return customerLovBind;
    }

    public void priceValuePopupVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent != null) {
            //      AdfFacesContext.getCurrentInstance().addPartialTarget(priceValTableBind);

        }
    }

    public void setPriceValTableBind(RichOutputText priceValTableBind) {
        this.priceValTableBind = priceValTableBind;
    }

    public RichOutputText getPriceValTableBind() {
        return priceValTableBind;
    }
}
