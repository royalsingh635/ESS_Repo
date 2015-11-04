package slsrmaapp.view.bean;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Generated;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.nav.RichCommandNavigationItem;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.server.JboPrecisionScaleValidator;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import slsrmaapp.model.services.SlsRmaAppAMImpl;


public class SlsRmaAddEditBean {
    private RichInputText rejectQuantBVar;
    private RichPopup addLotPopUpBind;
    private RichPopup addBinPopUpBind;
    private RichPopup addSrNoPopUpBind;
    private RichSelectOneChoice transShipIdBVar;
    private RichInputText transShipDateBVar;
    private boolean _disableHearder = true;
    private boolean _disableChild = true;
    private boolean disableQntFiled = true;
    private String facetName = "Lot";
    private String TxnId = "";
    // private boolean disableCust = true;
    private boolean disableShiment = true;
    private String mode = "V";
    private RichCommandImageLink saveLinkBVar;
    private RichCommandImageLink _saveNFrdLinkBVar;
    private RichCommandImageLink _backLinkBVar;
    private RichCommandImageLink _adLinkBVar;
    private RichCommandImageLink _editLinkBVar;
    private RichCommandImageLink _deleteLinkBVar;
    private RichCommandImageLink _saveLinkBVar;
    private RichCommandImageLink _cancelLinkBVar;
    private RichCommandImageLink addShipLinkBVar;
    private RichPopup selectShpItmPopUpBind;
    private RichInputText shpmntQuantPopBind;
    private RichCommandImageLink addItmlinkSecond;
    private RichCommandImageLink lotBVar;
    private RichCommandImageLink binBVar;
    private RichCommandImageLink srBVar;
    private RichTable itmTblBVar;
    private RichPanelFormLayout itmFrmBVar;
    private RichSelectOneChoice rmatypeBVar;
    private RichInputDate rmaDtBVar;
    private RichInputListOfValues cstNmBVar;
    private RichSelectOneChoice whBVar;
    private RichInputText rsnBVar;
    private RichSelectOneChoice binIdBind;
    private String lotNoChk = "N";
    private RichInputText lotRcptQuantBind;
    private RichInputText binRcptQuantBind;
    private RichInputListOfValues trnsShipmentIdBVar;
    private RichInputText shipDocIdBVar;
    private RichCommandImageLink addShipDocIdBVar;
    private RichTable lotTblBVar;
    private RichSelectOneChoice geDocIdBVar;
    private RichPopup newLotPopUpBVar;
    private RichInputText rcptQntBVar;
    private RichInputText lotOkQntBVar;
    private RichInputText okQntBar;
    private RichInputText rwkQntBVar;
    private String custreadMode = "Y";
    private RichInputText shipQntbVar;
    private RichInputText receiptQntbVarOnPopUp;
    private String addShipLink = "E";
    private RichSelectBooleanCheckbox taxRvrsBVar;
    private RichInputText rmrkBVar;
    private RichPopup lotBinSrDelPopUp;
    private RichCommandImageLink srLinkBVar;
    String WfNum = "0";
    Integer amount = 0;

    public SlsRmaAddEditBean() {
        _disableChild = true;
        // System.out.println("bean constructor");
    }


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /***********************Generic Methods to be used in Whole Application ***************/

    /**
     * Method to open a popup using binding.
     */
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


    /**
     * Generic Method to execute operation
     * */
    public OperationBinding executeOperation(String operation) {
        OperationBinding createParam = getBindings().getOperationBinding(operation);
        return createParam;

    }


    BindingContainer bindings = getBindings();


    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = null;
        if (valueExp.getValue(elContext) != null)
            msg = valueExp.getValue(elContext).toString();
        else
            msg = null;
        return msg;
    }

    public String rma_SaveActionListener() {

        OperationBinding RMAValidationPass = executeOperation("checkRmaValidation");
        RMAValidationPass.execute();
        Boolean pass= (Boolean)RMAValidationPass.getResult();
        System.out.println("Pass is"+pass);
        String flag = "Y";
        Number totalQnt = new Number(0);
         if (rmatypeBVar.getValue().equals(401)) {
            System.out.println("in 401 if");
            OperationBinding itmQntValid = executeOperation("onSaveClickValidItmQnt");
            itmQntValid.execute();
            System.out.println("rst  " + itmQntValid.getResult());
            if (itmQntValid.getResult() != null) {
                String rst = (String)itmQntValid.getResult();
                System.out.println("rst  " + rst);
                if ("Y".equalsIgnoreCase(rst)) {
                    System.out.println("if true");
                    // FacesMessage msg = new FacesMessage("Item Is Not Received with full Quantity in Item Table...");
                    FacesMessage msg = new FacesMessage(resolvEl("#{bundle['MSG.1143']}"));
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(null, msg);
                    flag = "N";
                    System.out.println("====");
                } else {
                    flag = "Y";
                    System.out.println("---+++");
                }
            }    

        } else if(!rmatypeBVar.getValue().equals(401)) {
             if(!pass){
                        flag = "N";
                        FacesMessage message =
                            new FacesMessage("<html><body>Lot/Bin Is not Assigned for some of the items Reciept</body></html>");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    }
             else{
                 
                     System.out.println("When Other than 401");

                     OperationBinding itmQntValid = executeOperation("isAllGateEntryitmAdjusted");
                     itmQntValid.execute();
                     System.out.println("rst isAllGateEntryitmAdjusted " + itmQntValid.getResult());

                     if (itmQntValid.getResult() != null) {
                         ArrayList<String> rtnList =
                             (ArrayList<String>)itmQntValid.getResult(); //ArrayList<String> itmArr = (ArrayList<String>)valArr.getResult();
                         if (rtnList.size() != 0) {
                             //FacesMessage msg = new FacesMessage(resolvEl("#{bundle['MSG.1143']}"));
                             // StringBuilder saveMsg =
                             //     new StringBuilder("<html><body><b><p style='color:red'>Following Gate Entry Items are not received with full quantity In Item Table ,Can't Move</p></b>");

                             StringBuilder saveMsg =
                                 new StringBuilder("<html><body><b><p style='color:red'>" + resolvEl("#{bundle['MSG.1144']}") +
                                                   "</p></b>");
                             saveMsg.append("<ul>");
                             for (String i : rtnList) {
                                 if ("".equals(i)) {
                                     System.out.println("do nothing " + i);
                                 } else {
                                     System.out.println("set 111" + i);
                                     saveMsg.append("<li> <b>" + i + "</li>");
                                 }
                             }
                             saveMsg.append("</ul><br>");
                             // saveMsg.append("<b>What to Do:");" + resolvEl("#{bundle['MSG.871']}"
                             saveMsg.append("<b>" + resolvEl("#{bundle['MSG.871']}"));
                             saveMsg.append("<ul style='color:darkgreen'><li>" + resolvEl("#{bundle['MSG.1145']}") +
                                            "</li></ul></b>");
                             saveMsg.append("</body></html>");
                             FacesMessage msg = new FacesMessage(saveMsg.toString());
                             msg.setSeverity(FacesMessage.SEVERITY_WARN);
                             FacesContext.getCurrentInstance().addMessage(null, msg);
                             disableQntFiled = false;
                             flag = "N";
                         } else {

                             OperationBinding okRejRwkQntValid = executeOperation("checkItmRcvQntToOkRejRekQnt");
                             okRejRwkQntValid.execute();
                             System.out.println("rst checkItmRcvQntToOkRejRekQnt " + okRejRwkQntValid.getResult());
                             if (okRejRwkQntValid.getResult() != null) {
                                 ArrayList<String> valtToShow =
                                     (ArrayList<String>)okRejRwkQntValid.getResult(); //ArrayList<String> itmArr = (ArrayList<String>)valArr.getResult();rtnList |
                                 if (valtToShow.size() != 0) {
                                     //FacesMessage msg = new FacesMessage(resolvEl("#{bundle['MSG.1143']}"));
                                     StringBuilder saveMsg =
                                         new StringBuilder("<html><body><b><p style='color:red'>There is a mismatch in ok ,rejected and reworkable quantity of following item.</p></b>");

                                     /*  StringBuilder saveMsg =
                                         new StringBuilder("<html><body><b><p style='color:red'>" + resolvEl("#{bundle['MSG.1144']}") +"</p></b>"); */
                                     saveMsg.append("<ul>");
                                     for (String i : valtToShow) {
                                         System.out.println("set 11 in bean ==" + i);
                                         saveMsg.append("<li> <b>" + i + "</li>");
                                     }
                                     saveMsg.append("</ul><br>");
                                     saveMsg.append("<b>What to Do:");
                                     // saveMsg.append("<b>"+ resolvEl("#{bundle['MSG.871']}"));
                                     saveMsg.append("<ul style='color:darkgreen'><li>Enter appropriate Ok ,Rejected and Reworkable Quantity of above mentioned Items</li></b>");
                                     saveMsg.append("</body></html>");
                                     FacesMessage msg = new FacesMessage(saveMsg.toString());
                                     msg.setSeverity(FacesMessage.SEVERITY_WARN);
                                     FacesContext.getCurrentInstance().addMessage(null, msg);
                                     disableQntFiled = false;
                                     flag = "N";
                                 } else {

                                     OperationBinding valArr = executeOperation("checkItemRcvdValidation");
                                     valArr.execute();
                                     if (valArr.getResult() != null) {
                                         ArrayList<String> itmArr = (ArrayList<String>)valArr.getResult();
                                         System.out.println("itmitmArr.size( " + itmArr.size());
                                         if (itmArr.size() != 0) {
                                             // StringBuilder saveMsg =
                                             //   new StringBuilder("<html><body><b><p style='color:red'>Following Items are not received with full quantity,Can't Move</p></b>");
                                             StringBuilder saveMsg =
                                                 new StringBuilder("<html><body><b><p style='color:red'>" + resolvEl("#{bundle['MSG.1155']}") +
                                                                   "</p></b>");
                                             saveMsg.append("<ul>");
                                             for (String a : itmArr) {
                                                 saveMsg.append("<li> <b>" + a + "</b></li>");
                                             }
                                             saveMsg.append("</ul><br>");
                                             // saveMsg.append("<b>What to Do:");
                                             saveMsg.append("<b>" + resolvEl("#{bundle['MSG.871']}"));
                                             //  saveMsg.append("<ul style='color:darkgreen'><li>Receive all Items with full quantity from LOT</li><li>Delete Un-Issued Items</li></ul></b>");
                                             saveMsg.append("<ul style='color:darkgreen'><li>" +
                                                            resolvEl("#{bundle['MSG.1070']}") + "</li><li>" +
                                                            resolvEl("#{bundle['MSG.873']}") + "</li></ul></b>");

                                             saveMsg.append("</body></html>");
                                             FacesMessage msg = new FacesMessage(saveMsg.toString());

                                             msg.setSeverity(FacesMessage.SEVERITY_WARN);
                                             FacesContext.getCurrentInstance().addMessage(null, msg);
                                             flag = "N";
                                         } else {
                                             System.out.println("in els when itmarr is null");
                                             flag = "Y";
                                         }
                                     }
                                 }
                             }

                         }
                     }
                 }
           
        }   else  if(!pass){
                        flag = "N";
                        FacesMessage message =
                            new FacesMessage("<html><body>Lot/Bin Is not Assigned for some of the items Reciept</body></html>");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    }


        if (flag.equalsIgnoreCase("Y")) {

            String action = "I";
            String remark = "A";
            // String WfNum = "0";
            // Integer amount = 0;
            BigDecimal level = new BigDecimal(3);
            Integer res = -1;

            String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
            Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
            String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            Integer UsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));


            /*****************************Function to get RMA WORK FLOW NUMBER*************************************************/
            OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
            WfnoOp.getParamsMap().put("SlocId", SlocId);
            WfnoOp.getParamsMap().put("CldId", CldId);
            WfnoOp.getParamsMap().put("OrgId", OrgId);
            WfnoOp.getParamsMap().put("DocNo", 21510);
            WfnoOp.execute();
            System.out.println("WfNum " + WfnoOp.getResult());
            if (WfnoOp.getResult() != null) {
                WfNum = WfnoOp.getResult().toString();


                /*****************************FUNCTION TO ET USER LEVEL*************************************************/
                OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                usrLevelOp.getParamsMap().put("SlocId", SlocId);
                usrLevelOp.getParamsMap().put("CldId", CldId);
                usrLevelOp.getParamsMap().put("OrgId", OrgId);
                usrLevelOp.getParamsMap().put("UsrId", UsrId);
                usrLevelOp.getParamsMap().put("WfNo", WfNum);
                usrLevelOp.getParamsMap().put("DocNo", 21510);
                usrLevelOp.execute();
                System.out.println("level----" + usrLevelOp.getResult());
                if (usrLevelOp.getResult() != null) {
                    level = (BigDecimal)usrLevelOp.getResult();

                    /*****************************FUNCTIION TO INSERT RMA DOCUMENT IN RM AWORKFLOW TABLE*************************************************/

                    OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                    insTxnOp.getParamsMap().put("SlocId", SlocId);
                    insTxnOp.getParamsMap().put("CldId", CldId);
                    insTxnOp.getParamsMap().put("OrgId", OrgId);
                    insTxnOp.getParamsMap().put("DocNo", 21510);
                    insTxnOp.getParamsMap().put("WfNo", WfNum);
                    insTxnOp.getParamsMap().put("usr_idFrm", UsrId);
                    insTxnOp.getParamsMap().put("usr_idTo", UsrId);
                    insTxnOp.getParamsMap().put("levelFrm", level);
                    insTxnOp.getParamsMap().put("levelTo", level);
                    insTxnOp.getParamsMap().put("action", action);
                    insTxnOp.getParamsMap().put("remark", remark);
                    insTxnOp.getParamsMap().put("amount", 0);
                    insTxnOp.execute();
                    System.out.println("res----" + insTxnOp.getResult());
                    if (insTxnOp.getResult() != null) {
                        res = Integer.parseInt(insTxnOp.getResult().toString());
                        /*****************************FUNCTION TO GET RMA WORK FLOW TXN DOC ID(THIS IS FOR PURPOSE TO INSERT TAXN ID AS A PARAMETER IN WORK FLOW TASK FLOW)*************************************************/
                        OperationBinding gettxn = getBindings().getOperationBinding("getTxnId");
                        gettxn.execute();

                        if (gettxn.getResult() != null) {
                            TxnId = gettxn.getResult().toString();
                        }


                        OperationBinding settxn = getBindings().getOperationBinding("settxnidlast");
                        settxn.getParamsMap().put("txn", TxnId);
                        settxn.execute();
                        /**************************--Generating RMS DISPLAY No.--***************************************/
                        OperationBinding genDispDocId = bindings.getOperationBinding("generateDispDocNo");
                        genDispDocId.execute();

                        if (genDispDocId.getResult() != null) {
                            String dispId = genDispDocId.getResult().toString();
                            System.out.println("Dis Id " + dispId);
                            // FacesMessage msg =
                            //   new FacesMessage(resolvElDCMsg("RMA No. " + dispId.substring(2) + " Saved Successfully").toString());
                            /*      FacesMessage msg =
                                new FacesMessage(resolvEl("#{bundle['MSG.1071']}")  + dispId.substring(2)  +
                                                 (resolvEl("#{bundle['MSG.347']}")).toString()); */
                            FacesMessage msg =
                                new FacesMessage("<html><body>" + resolvEl("#{bundle['MSG.1071']}") + " <b style='color:darkGreen'> " +
                                                 dispId.substring(2) + " </b>" + (resolvEl("#{bundle['MSG.347']}")) +
                                                 "</body></html>");
                            msg.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext ctx = FacesContext.getCurrentInstance();
                            ctx.addMessage(null, msg);
                            saveMode = "S";
                        }
                        this.setMode("V");
                        addShipLink = "E";
                        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
                        pageFlowScope.put("CHECK_PAGE_MODE", "V");
                        _disableChild = true;
                        disableQntFiled = true;
                        _saveLinkBVar.setDisabled(true);
                        _saveNFrdLinkBVar.setDisabled(true);
                        addShipDocIdBVar.setDisabled(true);
                        lotBVar.setDisabled(true);
                        binBVar.setDisabled(true);
                        srLinkBVar.setDisabled(true);
                        srBVar.setDisabled(true);
                        trnsShipmentIdBVar.setDisabled(true);
                        transShipIdBVar.setDisabled(true);
                        addShipLinkBVar.setDisabled(true);
                        // taxRvrsBVar.setDisabled(true);
                        rmrkBVar.setDisabled(true);


                        OperationBinding commit = bindings.getOperationBinding("Commit");
                        commit.execute();
                        /*  OperationBinding _Finalcommit = bindings.getOperationBinding("Commit");
                        _Finalcommit.execute(); */

                        System.out.println("_Finalcommit.getErrors() " + commit.getErrors());
                        return flag;
                    } else {
                        System.out.println("thwere is an error in callijng insIntoTxn function");
                        /*    String msg2 = resolvEl("There is an error while calling insIntoTxn function");
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message2); */
                    }

                } else {
                    System.out.println("user level is not defined for this user");
                    /*  String msg2 = resolvEl("Level Is Not Defined For This User");
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message2); */

                }
            } else {
                System.out.println("work flow has not defined for this user");
                /*     String msg2 = resolvEl("Work Flow Is Not Defined For This User");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2); */
            }


        }
        return flag;
    }

    private String saveMode = "Y";

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);

    }

    public void rma_cancelActionListener(ActionEvent actionEvent) {
        OperationBinding rollback = bindings.getOperationBinding("Rollback");
        rollback.execute();
    }

    public void editRma_ActionListener(ActionEvent actionEvent) {
        Integer pending = null;
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer UsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());

        OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
        chkUsr.getParamsMap().put("SlocId", SlocId);
        chkUsr.getParamsMap().put("CldId", CldId);
        chkUsr.getParamsMap().put("OrgId", OrgId);
        chkUsr.getParamsMap().put("DocNo", 21510);
        chkUsr.execute();

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
            System.out.println("pending  " + pending);
        }
        if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(UsrId) == 0) {
            this.setMode("E");
            addShipLink = "E";
            _disableChild = false;
            disableQntFiled = false;
            _saveLinkBVar.setDisabled(false);
            _saveNFrdLinkBVar.setDisabled(false);
            lotBVar.setDisabled(false);
            binBVar.setDisabled(false);
            srLinkBVar.setDisabled(false);
            srBVar.setDisabled(false);
            if (!(rmatypeBVar.getValue().equals(401))) {
                System.out.println("when other then 401");
                disableQntFiled = false;
                /*  rcptQntBVar.setDisabled(false);
                okQntBar.setDisabled(false);
                rejectQuantBVar.setDisabled(false);
                rwkQntBVar.setDisabled(false); */
            }
        } else {
            //
            OperationBinding prndingUsrNm = getBindings().getOperationBinding("getUsrNm");
            prndingUsrNm.getParamsMap().put("usrId", pending);
            prndingUsrNm.execute();
            String nm = (String)prndingUsrNm.getResult();
            System.out.println(" nm " + nm);

            //  String msg2 = resolvEl("#{bundle['MSG.1054']}");
            String msg2 = "This Document is pending at [" + nm + "] for approval, You can not forward this.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        }

    }

    public void createRma_ActionListener(ActionEvent actionEvent) {
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("CHECK_PAGE_MODE", "C");
        _disableHearder = false;
        _disableChild = true;
        disableShiment = true;
        custreadMode = "x";
        _saveLinkBVar.setDisabled(true);
        _saveNFrdLinkBVar.setDisabled(true);
        this.setMode("E");
        lotBVar.setDisabled(true);
        binBVar.setDisabled(true);
        srLinkBVar.setDisabled(true);
        srBVar.setDisabled(true);
        transShipIdBVar.setValue("");
        transShipDateBVar.setValue("");
        transShipIdBVar.setDisabled(false);
        addShipLinkBVar.setDisabled(false);
    }

    public void setRejectQuantBVar(RichInputText rejectQuantBVar) {
        this.rejectQuantBVar = rejectQuantBVar;
    }

    public RichInputText getRejectQuantBVar() {
        return rejectQuantBVar;
    }


    /************************Action Listener Defined For Page***********************/


    /**Method to set Value of switcher facet
     * @param actionEvent
     */
    public void viewLotAction(ActionEvent actionEvent) {
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem)actionEvent.getComponent();
        rcnv.setVisited(true);
        facetName = "Lot";
    }

    /**
     * @param actionEvent
     */
    public void viewBinAction(ActionEvent actionEvent) {
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem)actionEvent.getComponent();
        rcnv.setVisited(true);
        facetName = "Bin";
    }

    /**
     * @param actionEvent
     */
    public void viewSerialsAction(ActionEvent actionEvent) {
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem)actionEvent.getComponent();
        rcnv.setVisited(true);
        facetName = "SrNo";
    }

    /**Method to create LotWise Bin
     * @param actionEvent
     */
    private String binMode = "";

    public void addItemToBinAction(ActionEvent actionEvent) {
        System.out.println("Enter in BIN popUp");
        binMode = "A";
        executeOperation("CreateInsert1").execute();
        executeOperation("addItmToLotBin").execute();

        // executeOperation("executeShipWiseLotVo").execute();
        showPopup(addBinPopUpBind, true);
    }

    /**Method to add Item LotWise
     * @param actionEvent
     */

    private String LotMode = "";

    public void addItemToLotAction(ActionEvent actionEvent) {
        System.out.println("Enter in Lot popUp");
        LotMode = "A";
        /*  OperationBinding ob = getBindings().getOperationBinding("CreateInsert");
        ob.execute();
        OperationBinding genLot = getBindings().getOperationBinding("isLotAutoGen");
        genLot.execute();
        if (genLot.getResult() != null) {
            lotNoChk = genLot.getResult().toString();
        }*/
        System.out.println("Set 1");
        //call function to comapare received
        OperationBinding oppr = getBindings().getOperationBinding("compareItmWiseReceiveQtyToGeQty");
        oppr.execute();
        System.out.println("Return Result " + oppr.getResult());
        if (oppr.getResult() != null) {
            String S = (String)oppr.getResult();
            if ("MORE".equalsIgnoreCase(S)) {
                System.out.println("Quantity exceeds ");
                /*  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ""Received Quantity of this item exceeds to its Gate Entry Quantity" ",
                                                              null)); */
                FacesMessage msg = new FacesMessage(resolvEl("#{bundle['MSG.1146']}"));
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(rcptQntBVar.getClientId(), msg);


            } /* else if ("LESS".equalsIgnoreCase(S)) {
                FacesMessage msg = new FacesMessage("Received Quantity of this item is less than Gate Entry Quantity");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(rcptQntBVar.getClientId(), msg);

            } */ else {
                OperationBinding validQnt = getBindings().getOperationBinding("validRcptQnt");
                validQnt.execute();
                System.out.println(" validQnt.getResult() " + validQnt.getResult());
                if (validQnt.getResult() != null) {
                    String rst = validQnt.getResult().toString();
                    //System.out.println("returned rresult " + rst);
                    if ("OK".equalsIgnoreCase(rst)) {
                        System.out.println("nt trow message");
                        //_disableChild = true;
                        // disableQntFiled = true;
                        OperationBinding returnLotQnt = getBindings().getOperationBinding("returnLotQnt");
                        returnLotQnt.execute();
                        showPopup(newLotPopUpBVar, true);

                    } else {
                        // System.out.println("trow message");
                        //FacesMessage msg =
                        new FacesMessage("Please Enter appropriate OK,Rejected and Reworkable Quantity for this receive quantity of Item"); //
                        FacesMessage msg = new FacesMessage(resolvEl("#{bundle['MSG.1147']}"));
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(rcptQntBVar.getClientId(), msg);
                    }
                }
            }
        }


        else {
            FacesMessage msg = new FacesMessage("Item Row Is Null");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        // System.out.println("Lot No chk in bean is-" + lotNoChk);
        // showPopup(addLotPopUpBind, true);


    }

    /**Method to allocate serial number to Item
     * @param actionEvent
     */

    private String srMode = "";

    public void addItmSrNoAction(ActionEvent actionEvent) {
        OperationBinding ob = executeOperation("serialiazedItemRcptCheck");
        ob.execute();
        if (ob.getResult() != null) {
            if (ob.getResult().toString().equalsIgnoreCase("Y")) {
                // System.out.println("Enter in Sr popUp");
                srMode = "A";
                executeOperation("CreateInsert2").execute();
                executeOperation("addItemToSerialNo").execute();
                showPopup(addSrNoPopUpBind, true);
            } else {
                // FacesMessage msg=new FacesMessage("Can not Recieve more than Item Quanity in LOT");
                FacesMessage msg = new FacesMessage(resolvEl("#{bundle['MSG.1073']}"));
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }
    /****************Dialog Listener Used in Page******************************/

    /**Method to add Item To Lot
     * @param dialogEvent
     */
    public void addItmToLotDialogListener(DialogEvent dialogEvent) {
        /* if (dialogEvent.getOutcome().name().equals("ok")) {
            if (lotRcptQuantBind.getValue() != null) {
                executeOperation("addLotDetails").execute();
                executeOperation("Execute").execute();
                OperationBinding executeLotVo = getBindings().getOperationBinding("executeSlsRmaItemLotVo");
                executeLotVo.execute();
                  DCIteratorBinding iter = (DCIteratorBinding)getBindings().get("SlsRmaItmLot1Iterator");
                iter.executeQuery();
            } else {
                // FacesMessage msg=new FacesMessage("Quantity is required,Must Enter a value");
                FacesMessage msg = new FacesMessage(resolvEl("#{bundle['MSG.1074']}"));
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }

     AdfFacesContext.getCurrentInstance().addPartialTarget(lotTblBVar);*/
    }

    /**Method to add Item to Bin
     * @param dialogEvent
     */
    public void addItmToBinDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            if (binRcptQuantBind.getValue() != null) {
                executeOperation("setbinQtyBsVal").execute();
                executeOperation("Execute1").execute();
            } else {
                // FacesMessage msg=new FacesMessage("Quantity is required,Must Enter a value");
                FacesMessage msg = new FacesMessage(resolvEl("#{bundle['MSG.1074']}"));
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    /**Method to allocate SrNo to Items
     * @param dialogEvent
     */
    public void addItmToSrNoDialogListener(DialogEvent dialogEvent) {
        executeOperation("Execute2").execute();
        OperationBinding executeSrVo = getBindings().getOperationBinding("executeSlsRmaItemSrVo");
        executeSrVo.execute();
    }


    public void setAddLotPopUpBind(RichPopup addLotPopUpBind) {
        this.addLotPopUpBind = addLotPopUpBind;
    }

    public RichPopup getAddLotPopUpBind() {
        return addLotPopUpBind;
    }


    public void setFacetName(String facetName) {
        this.facetName = facetName;
    }

    public String getFacetName() {
        return facetName;
    }


    public void setAddBinPopUpBind(RichPopup addBinPopUpBind) {
        this.addBinPopUpBind = addBinPopUpBind;
    }

    public RichPopup getAddBinPopUpBind() {
        return addBinPopUpBind;
    }


    public void setAddSrNoPopUpBind(RichPopup addSrNoPopUpBind) {
        this.addSrNoPopUpBind = addSrNoPopUpBind;
    }

    public RichPopup getAddSrNoPopUpBind() {
        return addSrNoPopUpBind;
    }


    public void lotRjectQuantValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        /*   if (object != null) {
            oracle.jbo.domain.Number bd = (oracle.jbo.domain.Number)object;
            OperationBinding op = getBindings().getOperationBinding("isLotQtyValid");
            op.getParamsMap().put("obj", bd);
            op.execute();
            Boolean b = (Boolean)op.getResult();
            if (b) {
                // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Quantity Exceeds...",
                //               null));
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.1075']}"), null));
            } //evaluateEL("#{bundle['MSG.609']}").toString()
            else if (bd.compareTo(0) < 1) {
                //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Quantity",
                //                               "Must be greater than zero"));

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.336']}"),
                                                              "Must be greater than zero"));
            }
        } */
    }

    /**
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void binRejctQntValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            oracle.jbo.domain.Number d = (oracle.jbo.domain.Number)object;
            OperationBinding oop = getBindings().getOperationBinding("isBinQntValid");
            oop.getParamsMap().put("_obj", d);
            oop.execute();
            Boolean b = (Boolean)oop.getResult();
            if (b) {
                //  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Quantity Exceeds...",
                //                                         null));
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.1075']}"), null));

            }
            if (binIdBind.getValue() != null) {
                String binId = binIdBind.getValue().toString();
                OperationBinding binchk = getBindings().getOperationBinding("checkBinValidator");
                binchk.getParamsMap().put("BinId", binId);
                binchk.getParamsMap().put("qty", d);
                binchk.execute();
                if (binchk.getResult() != null) {
                    String flg = binchk.getResult().toString();
                    //System.out.println("Flag From Bin Check --" + flg);
                    if ("Y".equalsIgnoreCase(flg)) {
                        //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Quantity",
                        //                                              "Quantity Exceeds total bin capacity"));
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      resolvEl("#{bundle['MSG.336']}"),
                                                                      resolvEl("#{bundle['MSG.1076']}")));
                    }
                }
            }
        }
    }

    public void geId_ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(transShipIdBVar);

        if (valueChangeEvent.getNewValue() != null) {

            OperationBinding addMethod = bindings.getOperationBinding("fnsToInsertInItemTable");
            addMethod.getParamsMap().put("geId", (String)valueChangeEvent.getNewValue());
            addMethod.execute();

            DCIteratorBinding itr = (DCIteratorBinding)getBindings().get("TempVo1Iterator");
            itr.executeQuery();
            OperationBinding refrehLov = bindings.getOperationBinding("refreshLovSwitcher");
            refrehLov.execute();

            //_disable = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(transShipIdBVar);

        }

    }


    public void insInShipNItm_ActionListener(ActionEvent actionEvent) {
        // Add event code here...
        this.setMode("E");
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("CHECK_PAGE_MODE", "V");
        _disableHearder = true;
        // _disableChild = false;
        disableQntFiled = false;
        _saveLinkBVar.setDisabled(false);
        _saveNFrdLinkBVar.setDisabled(false);
        lotBVar.setDisabled(false);
        binBVar.setDisabled(false);
        srLinkBVar.setDisabled(false);
        srBVar.setDisabled(false);
        OperationBinding callFns = bindings.getOperationBinding("instoTnsInShipNItm");
        callFns.execute();
        if (callFns.getResult() != null) {
            BigDecimal rst = (BigDecimal)callFns.getResult();
            System.out.println("result  " + rst);
            if (rst.compareTo(new BigDecimal(1)) == 0) {
                System.out.println("when succcessfully inserted data in item table");
                if (trnsShipmentIdBVar.getValue() != null || transShipDateBVar.getValue() != null) {
                    System.out.println("--------------setting ship lov to blank------------------------11111");
                    trnsShipmentIdBVar.setValue("");
                    transShipDateBVar.setValue("");
                }
            } else if (rst.compareTo(new BigDecimal(1)) == -1) {
                /*  FacesMessage message =
                    new FacesMessage("This Item Is Not Shipped in this Shipment.Please Select Appropriate Shipment."); */
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1148']}"));
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                FacesMessage message = new FacesMessage("Function did not call properly");
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        }
    }


    public void transShipId_ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        /*   System.out.println("Enter In VLC Of Trans SHIP ID");
      //  String value = (String)valueChangeEvent.getNewValue();
String shipDocIdId = (String)shipDocIdBVar.getValue();
        //_disableAll = true;
        // _disable = true;
        _saveLinkBVar.setDisabled(true);
        _saveNFrdLinkBVar.setDisabled(true);
        System.out.println("=----++" + shipDocIdId);
        if (shipDocIdId != null) {
            addShipLinkBVar.setDisabled(false);
            //  addItmlinkSecond.setDisabled(false);
        }
        if (valueChangeEvent.getNewValue() != null) {

            BindingContainer bindings = getBindings();
            OperationBinding checkDuplicateShip = bindings.getOperationBinding("isDuplicateship");
            checkDuplicateShip.getParamsMap().put("_ShipId", shipDocIdId);
            checkDuplicateShip.execute();
            Boolean b = (Boolean)checkDuplicateShip.getResult();
            System.out.println("Dupl flag is" + b);
            if (b) {

               // FacesMessage message = new FacesMessage("This Shipment Is Already Exist");
               FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1077']}"));
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(transShipIdBVar.getClientId(), message);

            } else {
                OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert3");
                operationBinding.execute();
                OperationBinding insinshipTable = bindings.getOperationBinding("setDataInShipmntTbl");
                insinshipTable.getParamsMap().put("ShipId", new StringBuffer((String)shipDocIdId));

                insinshipTable.execute();
            }

        }
 */

    }


    public void setTransShipIdBVar(RichSelectOneChoice transShipIdBVar) {
        this.transShipIdBVar = transShipIdBVar;
    }

    public RichSelectOneChoice getTransShipIdBVar() {
        return transShipIdBVar;
    }

    public void setTransShipDateBVar(RichInputText transShipDateBVar) {
        this.transShipDateBVar = transShipDateBVar;
    }

    public RichInputText getTransShipDateBVar() {
        return transShipDateBVar;
    }

    /**
     * Programmatic invocation of a method that an EL evaluates to.
     * The method must not take any parameters.
     *
     * @param el EL of the method to invoke
     * @return Object that the method returns
     */
    public static Object invokeEL(String el) {
        return invokeEL(el, new Class[0], new Object[0]);
    }

    /**
     * Programmatic invocation of a method that an EL evaluates to.
     *
     * @param el EL of the method to invoke
     * @param paramTypes Array of Class defining the types of the parameters
     * @param params Array of Object defining the values of the parametrs
     * @return Object that the method returns
     */
    public static Object invokeEL(String el, Class[] paramTypes, Object[] params) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        MethodExpression exp = expressionFactory.createMethodExpression(elContext, el, Object.class, paramTypes);

        return exp.invoke(elContext, params);
    }

    public void itmTableSelectionListener(SelectionEvent selectionEvent) {
        this.invokeEL("#{bindings.SlsRmaItmTemp2.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class },
                      new Object[] { selectionEvent });
        executeOperation("executeRowSetLov").execute();

    }

    /*   public void setDisable(boolean _disable) {
        this._disable = _disable;
    }

    public boolean isDisable() {
        return _disable;
    } */

    /*
    public void setDisableAll(boolean _disableAll) {
        this._disableAll = _disableAll;
    }

    public boolean isDisableAll() {
        return _disableAll;
    } */

    /**
     * Programmatic invocation of a method that an EL evaluates to.
     * The method must not take any parameters.
     *
     * @param el EL of the method to invoke
     * @return Object that the method returns
     */
    public static Object invokeELShip(String el) {
        return invokeEL(el, new Class[0], new Object[0]);
    }

    /**
     * Programmatic invocation of a method that an EL evaluates to.
     *
     * @param el EL of the method to invoke
     * @param paramTypes Array of Class defining the types of the parameters
     * @param params Array of Object defining the values of the parametrs
     * @return Object that the method returns
     */
    public static Object invokeELShip(String el, Class[] paramTypes, Object[] params) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        MethodExpression exp = expressionFactory.createMethodExpression(elContext, el, Object.class, paramTypes);

        return exp.invoke(elContext, params);
    }


    public void testShipId_VLC(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }


    public String rmaSavenFrd_Action() {
        //String flg = "Y";
        // mode = "V";
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer UsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());

        // if (!("S".equalsIgnoreCase(saveMode))) {
        System.out.println("Enter if save link is not clicked");
        // String flg = rma_SaveActionListener();
        // }

        String flag = "Y";
        OperationBinding rmaValidations = executeOperation("checkRmaValidation");
        rmaValidations.execute();
        Boolean pass=(Boolean)rmaValidations.getResult();
       if (rmatypeBVar.getValue().equals(401)) {
            System.out.println("in 401 if");

            OperationBinding itmQntValid = executeOperation("onSaveClickValidItmQnt");
            itmQntValid.execute();
            System.out.println("rst  " + itmQntValid.getResult());
            if (itmQntValid.getResult() != null) {
                String rst = (String)itmQntValid.getResult();
                System.out.println("rst  " + rst);
                if ("Y".equalsIgnoreCase(rst)) {
                    System.out.println("if rue");
                    // FacesMessage msg = new FacesMessage("Item Is Not Received with full Quantity in Item Table...");
                    FacesMessage msg = new FacesMessage(resolvEl("#{bundle['MSG.1143']}"));
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(null, msg);
                    flag = "N";
                    System.out.println("====");
                } else {
                    flag = "Y";
                    System.out.println("---+++");
                }
            }

        } 
          else if(!rmatypeBVar.getValue().equals(401)) {
                     if(!pass){
                                   flag = "N";
                                   FacesMessage message =
                                       new FacesMessage("<html><body>Lot/Bin Is not Assigned for some of the items Reciept</body></html>");
                                   message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                   FacesContext.getCurrentInstance().addMessage(null, message);
                               }
                     else{
                         
                             System.out.println("When Other than 401");

                             OperationBinding itmQntValid = executeOperation("isAllGateEntryitmAdjusted");
                             itmQntValid.execute();
                             System.out.println("rst isAllGateEntryitmAdjusted " + itmQntValid.getResult());

                             if (itmQntValid.getResult() != null) {
                                 ArrayList<String> rtnList =
                                     (ArrayList<String>)itmQntValid.getResult(); //ArrayList<String> itmArr = (ArrayList<String>)valArr.getResult();
                                 if (rtnList.size() != 0) {
                                     //FacesMessage msg = new FacesMessage(resolvEl("#{bundle['MSG.1143']}"));
                                     // StringBuilder saveMsg =
                                     //     new StringBuilder("<html><body><b><p style='color:red'>Following Gate Entry Items are not received with full quantity In Item Table ,Can't Move</p></b>");

                                     StringBuilder saveMsg =
                                         new StringBuilder("<html><body><b><p style='color:red'>" + resolvEl("#{bundle['MSG.1144']}") +
                                                           "</p></b>");
                                     saveMsg.append("<ul>");
                                     for (String i : rtnList) {
                                         if ("".equals(i)) {
                                             System.out.println("do nothing " + i);
                                         } else {
                                             System.out.println("set 111" + i);
                                             saveMsg.append("<li> <b>" + i + "</li>");
                                         }
                                     }
                                     saveMsg.append("</ul><br>");
                                     // saveMsg.append("<b>What to Do:");" + resolvEl("#{bundle['MSG.871']}"
                                     saveMsg.append("<b>" + resolvEl("#{bundle['MSG.871']}"));
                                     saveMsg.append("<ul style='color:darkgreen'><li>" + resolvEl("#{bundle['MSG.1145']}") +
                                                    "</li></ul></b>");
                                     saveMsg.append("</body></html>");
                                     FacesMessage msg = new FacesMessage(saveMsg.toString());
                                     msg.setSeverity(FacesMessage.SEVERITY_WARN);
                                     FacesContext.getCurrentInstance().addMessage(null, msg);
                                     disableQntFiled = false;
                                     flag = "N";
                                 } else {

                                     OperationBinding okRejRwkQntValid = executeOperation("checkItmRcvQntToOkRejRekQnt");
                                     okRejRwkQntValid.execute();
                                     System.out.println("rst checkItmRcvQntToOkRejRekQnt " + okRejRwkQntValid.getResult());
                                     if (okRejRwkQntValid.getResult() != null) {
                                         ArrayList<String> valtToShow =
                                             (ArrayList<String>)okRejRwkQntValid.getResult(); //ArrayList<String> itmArr = (ArrayList<String>)valArr.getResult();rtnList |
                                         if (valtToShow.size() != 0) {
                                             //FacesMessage msg = new FacesMessage(resolvEl("#{bundle['MSG.1143']}"));
                                             StringBuilder saveMsg =
                                                 new StringBuilder("<html><body><b><p style='color:red'>There is a mismatch in ok ,rejected and reworkable quantity of following item.</p></b>");

                                             /*  StringBuilder saveMsg =
                                                 new StringBuilder("<html><body><b><p style='color:red'>" + resolvEl("#{bundle['MSG.1144']}") +"</p></b>"); */
                                             saveMsg.append("<ul>");
                                             for (String i : valtToShow) {
                                                 System.out.println("set 111" + i);
                                                 saveMsg.append("<li> <b>" + i + "</li>");
                                             }
                                             saveMsg.append("</ul><br>");
                                             saveMsg.append("<b>What to Do:");
                                             // saveMsg.append("<b>"+ resolvEl("#{bundle['MSG.871']}"));
                                             saveMsg.append("<ul style='color:darkgreen'><li>Enter appropriate Ok ,Rejected and Reworkable Quantity of above mentioned Items</li><li></b>");
                                             saveMsg.append("</body></html>");
                                             FacesMessage msg = new FacesMessage(saveMsg.toString());
                                             msg.setSeverity(FacesMessage.SEVERITY_WARN);
                                             FacesContext.getCurrentInstance().addMessage(null, msg);
                                             disableQntFiled = false;
                                             flag = "N";
                                         } else  {

                                             OperationBinding valArr = executeOperation("checkItemRcvdValidation");
                                             valArr.execute();
                                             if (valArr.getResult() != null) {
                                                 ArrayList<String> itmArr = (ArrayList<String>)valArr.getResult();
                                                 System.out.println("itmitmArr.size( " + itmArr.size());
                                                 if (itmArr.size() != 0) {
                                                     // StringBuilder saveMsg =
                                                     //   new StringBuilder("<html><body><b><p style='color:red'>Following Items are not received with full quantity,Can't Move</p></b>");
                                                     StringBuilder saveMsg =
                                                         new StringBuilder("<html><body><b><p style='color:red'>" + resolvEl("#{bundle['MSG.1068']}") +
                                                                           "</p></b>");
                                                     saveMsg.append("<ul>");
                                                     for (String a : itmArr) {
                                                         saveMsg.append(" <b>" + a + "</b>");
                                                     }
                                                     saveMsg.append("</ul><br>");
                                                     // saveMsg.append("<b>What to Do:");
                                                     saveMsg.append("<b>" + resolvEl("#{bundle['MSG.871']}"));
                                                     //  saveMsg.append("<ul style='color:darkgreen'><li>Receive all Items with full quantity from LOT</li><li>Delete Un-Issued Items</li></ul></b>");
                                                     saveMsg.append("<ul style='color:darkgreen'><li>" +
                                                                    resolvEl("#{bundle['MSG.1070']}") + "</li><li>" +
                                                                    resolvEl("#{bundle['MSG.873']}") + "</li></ul></b>");

                                                     saveMsg.append("</body></html>");
                                                     FacesMessage msg = new FacesMessage(saveMsg.toString());

                                                     msg.setSeverity(FacesMessage.SEVERITY_WARN);
                                                     FacesContext.getCurrentInstance().addMessage(null, msg);
                                                     flag = "N";
                                                 } else {
                                                     System.out.println("in els when itmarr is null");
                                                     flag = "Y";
                                                 }
                                             }
                                         }
                                     }

                                 }
                             }
                         }
           
        } else if(!pass){
            flag="N";
                FacesMessage message =
                    new FacesMessage("<html><body>Lot/Bin Is not Assigned for some of the items Reciept</body></html>");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        
        if (flag.equalsIgnoreCase("Y")) {


            String action = "I";
            String remark = "A";
            //  String WfNum = "0";
            BigDecimal level = new BigDecimal(3);
            Integer res = -1;
            //  Integer amount = 0;

            /*****************************Function to get RMA WORK FLOW NUMBER*************************************************/
            OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
            WfnoOp.getParamsMap().put("SlocId", SlocId);
            WfnoOp.getParamsMap().put("CldId", CldId);
            WfnoOp.getParamsMap().put("OrgId", OrgId);
            WfnoOp.getParamsMap().put("DocNo", 21510);
            WfnoOp.execute();
            System.out.println("WfNum " + WfnoOp.getResult());
            if (WfnoOp.getResult() != null) {
                WfNum = WfnoOp.getResult().toString();

                /*****************************FUNCTION TO ET USER LEVEL*************************************************/
                OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                usrLevelOp.getParamsMap().put("SlocId", SlocId);
                usrLevelOp.getParamsMap().put("CldId", CldId);
                usrLevelOp.getParamsMap().put("OrgId", OrgId);
                usrLevelOp.getParamsMap().put("UsrId", UsrId);
                usrLevelOp.getParamsMap().put("WfNo", WfNum);
                usrLevelOp.getParamsMap().put("DocNo", 21510);
                usrLevelOp.execute();
                System.out.println("level----" + usrLevelOp.getResult());
                if (usrLevelOp.getResult() != null) {
                    level = (BigDecimal)usrLevelOp.getResult();
                    /*****************************FUNCTION TO INSERT DATA IN WORK FLOW TXN TABLE*************************************************/
                    OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                    insTxnOp.getParamsMap().put("SlocId", SlocId);
                    insTxnOp.getParamsMap().put("CldId", CldId);
                    insTxnOp.getParamsMap().put("OrgId", OrgId);
                    insTxnOp.getParamsMap().put("DocNo", 21510);
                    insTxnOp.getParamsMap().put("WfNo", WfNum);
                    insTxnOp.getParamsMap().put("usr_idFrm", UsrId);
                    insTxnOp.getParamsMap().put("usr_idTo", UsrId);
                    insTxnOp.getParamsMap().put("levelFrm", level);
                    insTxnOp.getParamsMap().put("levelTo", level);
                    insTxnOp.getParamsMap().put("action", action);
                    insTxnOp.getParamsMap().put("remark", remark);
                    insTxnOp.getParamsMap().put("amount", 0);
                    insTxnOp.execute();
                    System.out.println("res----" + insTxnOp.getResult());
                    if (insTxnOp.getResult() != null) {
                        res = Integer.parseInt(insTxnOp.getResult().toString());
                        OperationBinding gettxn = getBindings().getOperationBinding("getTxnId");
                        gettxn.execute();

                        if (gettxn.getResult() != null) {
                            TxnId = gettxn.getResult().toString();
                        }


                        OperationBinding settxn = getBindings().getOperationBinding("settxnidlast");
                        settxn.getParamsMap().put("txn", TxnId);
                        settxn.execute();


                        if (flag.equalsIgnoreCase("Y")) {
                            //Check Pending
                            Integer pending = null;

                            /*****************************FUNCTION TO CHEK WHETHER CURRENT RMA DOCUMENT IS PEENDDING AT OTHER USER OR NOT *************************************************/
                            OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
                            chkUsr.getParamsMap().put("SlocId", SlocId);
                            chkUsr.getParamsMap().put("CldId", CldId);
                            chkUsr.getParamsMap().put("OrgId", OrgId);
                            chkUsr.getParamsMap().put("DocNo", 21510);
                            chkUsr.execute();

                            if (chkUsr.getResult() != null) {
                                pending = Integer.parseInt(chkUsr.getResult().toString());

                            }
                            if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(UsrId) == 0) {
                                this.setMode("V");
                                addShipLink = "E";
                                Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
                                pageFlowScope.put("CHECK_PAGE_MODE", "V");
                                _disableChild = true;
                                disableQntFiled = true;
                                _saveLinkBVar.setDisabled(true);
                                _saveNFrdLinkBVar.setDisabled(false);
                                addShipDocIdBVar.setDisabled(true);
                                lotBVar.setDisabled(true);
                                binBVar.setDisabled(true);
                                srLinkBVar.setDisabled(true);
                                srBVar.setDisabled(true);
                                trnsShipmentIdBVar.setDisabled(true);
                                transShipIdBVar.setDisabled(true);
                                addShipLinkBVar.setDisabled(true);
                                _saveLinkBVar.setDisabled(true);
                                _saveNFrdLinkBVar.setDisabled(true);
                                //System.out.println("forwarding rma");
                                /**************************-- CALLING FUNCTION TO GENERATE RMS DISPLAY No.--***************************************/
                                OperationBinding genDispDocId = bindings.getOperationBinding("generateDispDocNo");
                                genDispDocId.execute();
                                System.out.println("");
                                if (genDispDocId.getResult() != null) {
                                    String dispId = genDispDocId.getResult().toString();
                                    // FacesMessage msg =
                                    //   new FacesMessage(resolvElDCMsg("RMA No. " + dispId.substring(2) + " Saved Successfully").toString());
                                    if ("S".equalsIgnoreCase(saveMode)) {
                                        System.out.println("Enter if save link is  clicked");
                                    } else {
                                        System.out.println("Enter if save link is not clicked");
                                        FacesMessage msg =
                                            new FacesMessage("<html><body>" + resolvEl("#{bundle['MSG.1071']}") +
                                                             " <b style='color:darkGreen'> " + dispId.substring(2) +
                                                             " </b>" + (resolvEl("#{bundle['MSG.347']}")) +
                                                             "</body></html>");
                                        msg.setSeverity(FacesMessage.SEVERITY_INFO);
                                        FacesContext ctx = FacesContext.getCurrentInstance();
                                        ctx.addMessage(null, msg);
                                    }
                                }
                                OperationBinding _Finalcommit = bindings.getOperationBinding("Commit");
                                _Finalcommit.execute();

                                System.out.println("_Finalcommit.getErrors() " + _Finalcommit.getErrors());
                                System.out.println("Enter if save link is not clicked");
                                return "gotoWf";
                            } else {
                                //  String msg2 = "This Slip is pending at other user for approval, You can not forward this.";
                                String msg2 = resolvEl("#{bundle['MSG.1054']}");
                                FacesMessage message2 = new FacesMessage(msg2);
                                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message2);
                                // System.out.println("------in ram frd");
                                return null;

                            }
                        } else {
                            System.out.println("in rma fed else null return");
                            return null;
                        }
                    } else {
                        System.out.println("there is an errror while callinbg insIntoTxn function");
                        /*  String msg2 = resolvEl("There is an error while calling insIntoTxn function");
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message2); */
                    }

                } else {
                    System.out.println("User level is not defined for thos user");
                    /*  String msg2 = resolvEl("Level Is Not Defined For This User");
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message2); */
                }

            } else {
                System.out.println("Work flow is not defined for this user");
                /*   String msg2 = resolvEl("Work Flow IS Not Defined For this User");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2); */
            }
        }
        System.out.println("Enter if save link is not clicked");

        /*  if (flag.equalsIgnoreCase("Y")) {
            //Check Pending
            Integer pending = null;
            OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
            chkUsr.getParamsMap().put("SlocId", SlocId);
            chkUsr.getParamsMap().put("CldId", CldId);
            chkUsr.getParamsMap().put("OrgId", OrgId);
            chkUsr.getParamsMap().put("DocNo", 21510);
            chkUsr.execute();

            if (chkUsr.getResult() != null) {
                pending = Integer.parseInt(chkUsr.getResult().toString());

            }
            if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(UsrId) == 0) {
                _saveLinkBVar.setDisabled(true);
                _saveNFrdLinkBVar.setDisabled(true);
                //System.out.println("forwarding rma");
                return "gotoWf";
            } else {
                //  String msg2 = "This Slip is pending at other user for approval, You can not forward this.";
                String msg2 = resolvEl("#{bundle['MSG.1054']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                // System.out.println("------in ram frd");
                return null;

            }
        } else {
            System.out.println("in rma fed else null return");
            return null;
        } */
        return null;
    }

    public void setTxnId(String TxnId) {
        this.TxnId = TxnId;
    }

    public String getTxnId() {
        return TxnId;
    }

    public String returnToSearch_Action() {

        return "return";
    }


    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setSaveLinkBVar(RichCommandImageLink saveLinkBVar) {
        this.saveLinkBVar = saveLinkBVar;
    }

    public RichCommandImageLink getSaveLinkBVar() {
        return saveLinkBVar;
    }

    public void set_saveNFrdLinkBVar(RichCommandImageLink _saveNFrdLinkBVar) {
        this._saveNFrdLinkBVar = _saveNFrdLinkBVar;
    }

    public RichCommandImageLink get_saveNFrdLinkBVar() {
        return _saveNFrdLinkBVar;
    }

    public void set_backLinkBVar(RichCommandImageLink _backLinkBVar) {
        this._backLinkBVar = _backLinkBVar;
    }

    public RichCommandImageLink get_backLinkBVar() {
        return _backLinkBVar;
    }

    public void set_adLinkBVar(RichCommandImageLink _adLinkBVar) {
        this._adLinkBVar = _adLinkBVar;
    }

    public RichCommandImageLink get_adLinkBVar() {
        return _adLinkBVar;
    }

    public void set_editLinkBVar(RichCommandImageLink _editLinkBVar) {
        this._editLinkBVar = _editLinkBVar;
    }

    public RichCommandImageLink get_editLinkBVar() {
        return _editLinkBVar;
    }

    public void set_deleteLinkBVar(RichCommandImageLink _deleteLinkBVar) {
        this._deleteLinkBVar = _deleteLinkBVar;
    }

    public RichCommandImageLink get_deleteLinkBVar() {
        return _deleteLinkBVar;
    }

    public void set_saveLinkBVar(RichCommandImageLink _saveLinkBVar) {
        this._saveLinkBVar = _saveLinkBVar;
    }

    public RichCommandImageLink get_saveLinkBVar() {
        return _saveLinkBVar;
    }

    public void set_cancelLinkBVar(RichCommandImageLink _cancelLinkBVar) {
        this._cancelLinkBVar = _cancelLinkBVar;
    }

    public RichCommandImageLink get_cancelLinkBVar() {
        return _cancelLinkBVar;
    }

    public void lotPopupCancelListener(PopupCanceledEvent popupCanceledEvent) {
        if (LotMode.equalsIgnoreCase("A")) {

            OperationBinding Dlot = getBindings().getOperationBinding("delteLotRow");
            Dlot.execute();
            OperationBinding executelot = getBindings().getOperationBinding("executeLotVo");
            executelot.execute();
        }

    }

    public void binPopupCancelListener(PopupCanceledEvent popupCanceledEvent) {

        if (binMode.equalsIgnoreCase("A")) {
            OperationBinding deleteBin = getBindings().getOperationBinding("delteBinRow");
            deleteBin.execute();
            OperationBinding executebin = getBindings().getOperationBinding("execteBinVo");
            executebin.execute();
        }

    }

    public void serialPopupCancelListener(PopupCanceledEvent popupCanceledEvent) {
        if (srMode.equalsIgnoreCase("A")) {

            OperationBinding DSr = getBindings().getOperationBinding("delteSrRow");
            DSr.execute();
            OperationBinding executeSr = getBindings().getOperationBinding("executeSrVo");
            executeSr.execute();

        }

    }

    public void setAddShipLinkBVar(RichCommandImageLink addShipLinkBVar) {
        this.addShipLinkBVar = addShipLinkBVar;
    }

    public RichCommandImageLink getAddShipLinkBVar() {
        return addShipLinkBVar;
    }

    public void delete_rmaActionListener(ActionEvent actionEvent) {
        this.setMode("C");
        /*  OperationBinding deleteRma = getBindings().getOperationBinding("deleteRma");
        deleteRma.execute();
        OperationBinding commitafterdelete = bindings.getOperationBinding("Commit");
        commitafterdelete.execute(); */
    }

    public void selectItemFrShpmntAction(ActionEvent actionEvent) {
        /*  System.out.println("In Buutton Action");
        showPopup(selectShpItmPopUpBind, true);
        System.out.println("PoUp Opened"); */
    }

    public void selectRejectedQuantDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            System.out.println("in  popup");
            OperationBinding ob = getBindings().getOperationBinding("insertItemToRmaFrmShpmnt");
            ob.execute();
            _saveLinkBVar.setDisabled(false);
            _saveNFrdLinkBVar.setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(_saveLinkBVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(_saveNFrdLinkBVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTblBVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmFrmBVar);

            // AdfFacesContext.getCurrentInstance().addPartialTarget(itmformBVar1);
        }
    }

    public void setSelectShpItmPopUpBind(RichPopup selectShpItmPopUpBind) {
        this.selectShpItmPopUpBind = selectShpItmPopUpBind;
    }

    public RichPopup getSelectShpItmPopUpBind() {
        return selectShpItmPopUpBind;
    }

    public void setShpmntQuantPopBind(RichInputText shpmntQuantPopBind) {
        this.shpmntQuantPopBind = shpmntQuantPopBind;
    }

    public RichInputText getShpmntQuantPopBind() {
        return shpmntQuantPopBind;
    }

    /**To validate Rejected Quantity When Gate Entry is not in use
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void rejectedQuantPopValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // System.out.println("Inside Validator");
        if (object != null) {
            Number rejectQty = (Number)object;
            Number shpmntQty = new Number(0);
            if (shpmntQuantPopBind.getValue() != null) {
                shpmntQty = (Number)shpmntQuantPopBind.getValue();
                System.out.println("Shipment Quant -" + shpmntQty + "and Rejected aty-" + rejectQty);
                boolean b = isPrecisionValid(26, 6, rejectQty);
                // System.out.println("b " + b);
                if (b) {
                    if (rejectQty.compareTo(0) < 0) {
                        // throw new ValidatorException(new FacesMessage("Rejected Quantity must be a positive value"));
                        throw new ValidatorException(new FacesMessage("Rejected Quantity can not negative"));
                    } else if (rejectQty.compareTo(shpmntQty) == 1) {
                        // throw new ValidatorException(new FacesMessage("Rejected Quantity can not be greater that Shipment quantity"));
                        throw new ValidatorException(new FacesMessage(resolvEl("#{bundle['MSG.1079']}")));
                    }
                } else {
                    // System.out.println("-------------");
                    throw new ValidatorException(new FacesMessage("invalid Precision!!"));
                }
            }


        }
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void setAddItmlinkSecond(RichCommandImageLink addItmlinkSecond) {
        this.addItmlinkSecond = addItmlinkSecond;
    }

    public RichCommandImageLink getAddItmlinkSecond() {
        return addItmlinkSecond;
    }


    /*****Function to enable save nd save frd link when gat entry field is not visible******/
    public void AddItemDetailLinkAction(ActionEvent actionEvent) {
        this.setMode("E");
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("CHECK_PAGE_MODE", "V");
        // System.out.println("Enableing buttons.");

        // _disableChild = false;
        disableQntFiled = false;

        lotBVar.setDisabled(false);
        binBVar.setDisabled(false);
        srBVar.setDisabled(false);
        srLinkBVar.setDisabled(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lotBVar);

        AdfFacesContext.getCurrentInstance().addPartialTarget(srLinkBVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(binBVar);

        AdfFacesContext.getCurrentInstance().addPartialTarget(srBVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rmaDtBVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rmatypeBVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cstNmBVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(whBVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rsnBVar);
        if (trnsShipmentIdBVar.getValue() != null || transShipDateBVar.getValue() != null) {
            trnsShipmentIdBVar.setValue(null);
            transShipDateBVar.setValue(null);
        }
        executeOperation("executeRowSetLov").execute();
        showPopup(selectShpItmPopUpBind, true);

    }

    public void rmaTypeIdVLC(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(transShipIdBVar);
        DCIteratorBinding itr = (DCIteratorBinding)getBindings().get("TempVo1Iterator");
        itr.executeQuery();
    }

    public void setLotBVar(RichCommandImageLink lotBVar) {
        this.lotBVar = lotBVar;
    }

    public RichCommandImageLink getLotBVar() {
        return lotBVar;
    }

    public void setBinBVar(RichCommandImageLink binBVar) {
        this.binBVar = binBVar;
    }

    public RichCommandImageLink getBinBVar() {
        return binBVar;
    }

    public void setSrBVar(RichCommandImageLink srBVar) {
        this.srBVar = srBVar;
    }

    public RichCommandImageLink getSrBVar() {
        return srBVar;
    }

    public void setDisableHearder(boolean _disableHearder) {
        this._disableHearder = _disableHearder;
    }

    public boolean isDisableHearder() {
        return _disableHearder;
    }

    public void setDisableChild(boolean _disableChild) {
        this._disableChild = _disableChild;
    }

    public boolean isDisableChild() {
        return _disableChild;
    }


    public void setItmTblBVar(RichTable itmTblBVar) {
        this.itmTblBVar = itmTblBVar;
    }

    public RichTable getItmTblBVar() {
        return itmTblBVar;
    }

    public void setItmFrmBVar(RichPanelFormLayout itmFrmBVar) {
        this.itmFrmBVar = itmFrmBVar;
    }

    public RichPanelFormLayout getItmFrmBVar() {
        return itmFrmBVar;
    }

    /**
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void wareHouseValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // System.out.println("Ware house validator");
        // System.out.println(object);
        if (object == null) {
            //System.out.println(object);
            throw new ValidatorException(new FacesMessage("Select Ware House"));

        }
    }

    public void setRmatypeBVar(RichSelectOneChoice rmatypeBVar) {
        this.rmatypeBVar = rmatypeBVar;
    }

    public RichSelectOneChoice getRmatypeBVar() {
        return rmatypeBVar;
    }

    public void setRmaDtBVar(RichInputDate rmaDtBVar) {
        this.rmaDtBVar = rmaDtBVar;
    }

    public RichInputDate getRmaDtBVar() {
        return rmaDtBVar;
    }

    public void setCstNmBVar(RichInputListOfValues cstNmBVar) {
        this.cstNmBVar = cstNmBVar;
    }

    public RichInputListOfValues getCstNmBVar() {
        return cstNmBVar;
    }

    public void setWhBVar(RichSelectOneChoice whBVar) {
        this.whBVar = whBVar;
    }

    public RichSelectOneChoice getWhBVar() {
        return whBVar;
    }

    public void setRsnBVar(RichInputText rsnBVar) {
        this.rsnBVar = rsnBVar;
    }

    public RichInputText getRsnBVar() {
        return rsnBVar;
    }

    public void setBinMode(String binMode) {
        this.binMode = binMode;
    }

    public String getBinMode() {
        return binMode;
    }

    public void setLotMode(String LotMode) {
        this.LotMode = LotMode;
    }

    public String getLotMode() {
        return LotMode;
    }

    public void setSrMode(String srMode) {
        this.srMode = srMode;
    }

    public String getSrMode() {
        return srMode;
    }

    /**Validator for LOT No.
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void lotNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        // System.out.println("lot no validatoer");
        /*  if (object != null) {
            String lotNo = (String)object;
            System.out.println(lotNo);
            OperationBinding lotNoVald = getBindings().getOperationBinding("LotNoValidator");
            lotNoVald.getParamsMap().put("lot", lotNo);
            lotNoVald.execute();
            if (lotNoVald.getResult() != null) {
                ArrayList<String> rst = (ArrayList<String>)lotNoVald.getResult();
                if ("Y".equalsIgnoreCase(rst.get(0))) {
                    // throw new ValidatorException(new FacesMessage("Lot is already exist in Stock for this item."));
                    throw new ValidatorException(new FacesMessage(resolvElDCMsg("#{bundle['MSG.1080']}").toString()));
                } else if ("NP".equalsIgnoreCase(rst.get(0))) {
                    //throw new ValidatorException(new FacesMessage("Profile is not created for this Organisation, can not move"));
                    throw new ValidatorException(new FacesMessage(resolvElDCMsg("#{bundle['MSG.1081']}").toString()));
                } else if ("NS".equalsIgnoreCase(rst.get(0))) {
                    //  FacesMessage fMsg = new FacesMessage("Item can not be received in this LOT");
                    FacesMessage fMsg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1082']}").toString());
                    // StringBuilder msg =
                    //    new StringBuilder("<html><body>Enter previously used Lot No- <b style='color:red'>" +
                    //                    rst.get(1) + "</b></body></html>");
                    StringBuilder msg =
                        new StringBuilder("<html><body>" + resolvElDCMsg("#{bundle['MSG.1082']}").toString() +
                                          "<b style='color:red'>" + rst.get(1) + "</b></body></html>");
                    fMsg.setDetail(msg.toString());
                    throw new ValidatorException(fMsg);
                }

            }
        } */
    }


    public void setBinIdBind(RichSelectOneChoice binIdBind) {
        this.binIdBind = binIdBind;
    }

    public RichSelectOneChoice getBinIdBind() {
        return binIdBind;
    }

    public void setLotNoChk(String lotNoChk) {
        this.lotNoChk = lotNoChk;
    }

    public String getLotNoChk() {
        return lotNoChk;
    }

    public void setLotRcptQuantBind(RichInputText lotRcptQuantBind) {
        this.lotRcptQuantBind = lotRcptQuantBind;
    }

    public RichInputText getLotRcptQuantBind() {
        return lotRcptQuantBind;
    }

    public void setBinRcptQuantBind(RichInputText binRcptQuantBind) {
        this.binRcptQuantBind = binRcptQuantBind;
    }

    public RichInputText getBinRcptQuantBind() {
        return binRcptQuantBind;
    }

    public void wareHouseVCE(ValueChangeEvent vce) {
        System.out.println("sop11" + vce.getNewValue());
        disableShiment = false;
        trnsShipmentIdBVar.setDisabled(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(trnsShipmentIdBVar);

        if (vce.getNewValue() != null) {
            System.out.println("sop" + vce.getNewValue());
            executeOperation("filterShpmntCustWise").execute();
        }
    }


    public void custVCL(ValueChangeEvent valueChangeEvent) {
        whBVar.setValue(null);
        transShipIdBVar.setValue(null);
        disableShiment = true;
        /*   if (valueChangeEvent.getNewValue() != null) {
            executeOperation("filterShpmntCustWise").execute();
        }
         transShipIdBVar.processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(transShipIdBVar);
        shipDocIdBVar.processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(shipDocIdBVar);
        */

    }

    public void setTrnsShipmentIdBVar(RichInputListOfValues trnsShipmentIdBVar) {
        this.trnsShipmentIdBVar = trnsShipmentIdBVar;
    }

    public RichInputListOfValues getTrnsShipmentIdBVar() {
        return trnsShipmentIdBVar;
    }

    public void setShipDocIdBVar(RichInputText shipDocIdBVar) {
        this.shipDocIdBVar = shipDocIdBVar;
    }

    public RichInputText getShipDocIdBVar() {
        return shipDocIdBVar;
    }

    public void shipmentVCL(ValueChangeEvent valueChangeEvent) {
        // System.out.println("Enter In VLC Of Trans SHIP ID");
        AdfFacesContext.getCurrentInstance().addPartialTarget(cstNmBVar);
        custreadMode = "R";
        addShipDocIdBVar.setDisabled(false);
    }

    public void addShipmtFns() {
        String shipDocIdId = (String)shipDocIdBVar.getValue();
        // System.out.println("=shipment doc id ++" + shipDocIdId); //GLBL_ORG_GE_CHK
        OperationBinding chkShipDays = bindings.getOperationBinding("chekShipDays");
        chkShipDays.execute();
        System.out.println("chkShipDays== " + chkShipDays.getResult());
        String rtnRst = (String)chkShipDays.getResult();
        if ("Y".equalsIgnoreCase(rtnRst)) {
            if (shipDocIdId != null) {
                addShipLinkBVar.setDisabled(false);
                BindingContainer bindings = getBindings();
                OperationBinding checkDuplicateShip = bindings.getOperationBinding("isDuplicateship");
                checkDuplicateShip.getParamsMap().put("_ShipId", shipDocIdId);
                checkDuplicateShip.execute();
                Boolean b = (Boolean)checkDuplicateShip.getResult();
                // System.out.println("Dupl flag is" + b);
                if (b) {

                    // FacesMessage message = new FacesMessage("This Shipment Is Already Exist");
                    FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1077']}"));
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(transShipIdBVar.getClientId(), message);

                } else {
                    Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
                    pageFlowScope.put("CHECK_PAGE_MODE", "V");
                    OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert3");
                    operationBinding.execute();
                    OperationBinding insinshipTable = bindings.getOperationBinding("setDataInShipmntTbl");
                    insinshipTable.getParamsMap().put("ShipId", shipDocIdId);
                    insinshipTable.execute();

                }
            } else {
                FacesMessage message = new FacesMessage("Select Shipment.");
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(trnsShipmentIdBVar.getClientId(), message);
            }

        } else {
            System.out.println("shipment days has been checked and found expired");
        }

    }

    public void addShipmentAction(ActionEvent actionEvent) {
        // System.out.println("Action Of Trans SHIP ID");
        String shipDocIdId = (String)shipDocIdBVar.getValue();
        // System.out.println("=shipment doc id ++" + shipDocIdId); //GLBL_ORG_GE_CHK
        String glblGeChk = resolvEl("#{pageFlowScope.GLBL_ORG_GE_CHK}").toString();
        Integer rmaypId = Integer.parseInt(rmatypeBVar.getValue().toString());

        System.out.println("rmatypeBVar.getValue()!= 401 " + rmatypeBVar.getValue() + " rmaypId " + rmaypId);
        System.out.println(glblGeChk);
        if (glblGeChk.equalsIgnoreCase("Y") && rmaypId != 401) {
            System.out.println("====sasasas====" + geDocIdBVar.getValue().toString().length() + "whBVar.getValue() " +
                               whBVar.getValue());
            if (geDocIdBVar.getValue() == null || geDocIdBVar.getValue().toString().length() == 0 ||
                whBVar.getValue() == null || whBVar.getValue().toString().length() == 0) {
                System.out.println("=-=-=-=-==-");
                FacesMessage message = new FacesMessage("Select Value");
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                if (geDocIdBVar.getValue() == null || geDocIdBVar.getValue().toString().length() == 0) {

                    System.out.println("  when gate entry is  nulll");
                    fc.addMessage(geDocIdBVar.getClientId(), message);
                }
                if (whBVar.getValue() == null || whBVar.getValue().toString().length() == 0) {
                    System.out.println("when wh is null");
                    fc.addMessage(whBVar.getClientId(), message);
                }
            } else {
                System.out.println("eelse whhen gate eenrtry is enable;");
                addShipLink = "Z";
                _disableHearder = true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(whBVar);
                addShipmtFns();

            }
        } else {
            if (whBVar.getValue() == null) {
                System.out.println("when wh is null and no gate entry");
                FacesMessage message = new FacesMessage("Select Value");
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(whBVar.getClientId(), message);
            } else {
                addShipLink = "Z";
                // System.out.println("eelse whhen gate eenrtry is disable;");
                _disableHearder = true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(whBVar);
                addShipmtFns();
            }

        }
    }


    public void setAddShipDocIdBVar(RichCommandImageLink addShipDocIdBVar) {
        this.addShipDocIdBVar = addShipDocIdBVar;
    }

    public RichCommandImageLink getAddShipDocIdBVar() {
        return addShipDocIdBVar;
    }

    public void setLotTblBVar(RichTable lotTblBVar) {
        this.lotTblBVar = lotTblBVar;
    }

    public RichTable getLotTblBVar() {
        return lotTblBVar;
    }

    public void setDisableShiment(boolean disableShiment) {
        this.disableShiment = disableShiment;
    }

    public boolean isDisableShiment() {
        return disableShiment;
    }

    public void setGeDocIdBVar(RichSelectOneChoice geDocIdBVar) {
        this.geDocIdBVar = geDocIdBVar;
    }

    public RichSelectOneChoice getGeDocIdBVar() {
        return geDocIdBVar;
    }

    public void setNewLotPopUpBVar(RichPopup newLotPopUpBVar) {
        this.newLotPopUpBVar = newLotPopUpBVar;
    }

    public RichPopup getNewLotPopUpBVar() {
        return newLotPopUpBVar;
    }

    public void shipWiseLotDiolugeListener(DialogEvent dialogEvent) {
        // System.out.println("In shipwise lot pop up");
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("ok")) {
            /* OperationBinding setDT = getBindings().getOperationBinding("setDocDtInSlsRmaItmLot1");
            setDT.execute(); */
            
            Number enterOkQnt = (Number)okQntBar.getValue();
            Number enterrejQnt = (Number)rejectQuantBVar.getValue();
            Number enterRwkQnt = (Number)rwkQntBVar.getValue();

            /*  System.out.println("entered quantity in item table of om qnt " + enterOkQnt + " rejQnt " + enterrejQnt +
                               " rwkQnt " + enterRwkQnt); */
            OperationBinding validQntInLot = getBindings().getOperationBinding("validLotQnty");
            validQntInLot.execute();
            if (validQntInLot.getResult() != null) {
                String rst = validQntInLot.getResult().toString();
                //System.out.println("return retsfsadf  --" + rst);
                if ("OK".equalsIgnoreCase(rst)) {
                    OperationBinding chkOkRejRwkQnt = getBindings().getOperationBinding("checkOkRejRwkQnt");
                    chkOkRejRwkQnt.execute();
                    if (chkOkRejRwkQnt.getResult() != null) {
                        ArrayList<Number> rtnVal = (ArrayList<Number>)chkOkRejRwkQnt.getResult();
                        Number rejQnt = rtnVal.get(0);
                        Number okQnt = rtnVal.get(1);
                        Number rwkont = rtnVal.get(2);
                        // System.out.println("okQnt in bean " + okQnt + " rejQnt " + rejQnt + "rwkQnt " + rwkont);
                        if (okQnt.compareTo(enterOkQnt) == -1 || okQnt.compareTo(enterOkQnt) == 1) {
                            //   System.out.println("In Ok IF");
                            FacesMessage message = new FacesMessage("You have Entered Wrong Ok Quantity");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        } else if (rejQnt.compareTo(enterrejQnt) == -1 || rejQnt.compareTo(enterrejQnt) == 1) {
                            //  System.out.println("In Rej If");
                            FacesMessage message = new FacesMessage("You have Entered Wrong Rejected Quantity .");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        } else if (rwkont.compareTo(enterRwkQnt) == -1 || rwkont.compareTo(enterRwkQnt) == 1) {
                            // System.out.println("In rwk if");
                            FacesMessage message = new FacesMessage("You have Entered Wrong Reworkable Quantity ..");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);

                        } else {
                            // System.out.println(" in side ok");
                            disableQntFiled = false;
                            executeOperation("addItmInLot").execute();
                            executeOperation("Execute").execute();
                            OperationBinding executeLotVo =
                                getBindings().getOperationBinding("executeSlsRmaItemLotVo");
                            executeLotVo.execute();
                            AdfFacesContext.getCurrentInstance().addPartialTarget(lotTblBVar);
                        }
                    }

                } else {
                    FacesMessage message = new FacesMessage("Item is not received with full quantity in lot table.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            } else {
                FacesMessage message = new FacesMessage("there is no data in lot table.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptQntBVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(okQntBar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rejectQuantBVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rwkQntBVar);
    }

    public void setRcptQntBVar(RichInputText rcptQntBVar) {
        this.rcptQntBVar = rcptQntBVar;
    }

    public RichInputText getRcptQntBVar() {
        return rcptQntBVar;
    }

    public void setLotOkQntBVar(RichInputText lotOkQntBVar) {
        this.lotOkQntBVar = lotOkQntBVar;
    }

    public RichInputText getLotOkQntBVar() {
        return lotOkQntBVar;
    }

    public void setOkQntBar(RichInputText okQntBar) {
        this.okQntBar = okQntBar;
    }

    public RichInputText getOkQntBar() {
        return okQntBar;
    }

    public void setRwkQntBVar(RichInputText rwkQntBVar) {
        this.rwkQntBVar = rwkQntBVar;
    }

    public RichInputText getRwkQntBVar() {
        return rwkQntBVar;
    }

    public void rejtQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
            if (object != null) {
            Number n = (Number)object;
            Number okQnt = new Number(0);
            okQnt = (Number)okQntBar.getValue();
            Number rwkqty = new Number(0);
            rwkqty = (Number)rwkQntBVar.getValue();
            Number rcptQnt = new Number(0);
            rcptQnt = (Number)rcptQntBVar.getValue();

            if (n.compareTo(Number.zero()) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Quantity can not either negative or less then zero",
                                                              null));
            }  else {
                boolean b = isPrecisionValid(26, 6, n);
                if (!b) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision !!", null));
                }
            }

        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enter Quantity.", null));

        }

    }

    public void okQntValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number n = (Number)object;
            Number rejectqty = new Number(0);
            rejectqty = (Number)rejectQuantBVar.getValue();
            Number rwkqty = new Number(0);
            rwkqty = (Number)rwkQntBVar.getValue();
            Number rcptQnt = new Number(0);
            rcptQnt = (Number)rcptQntBVar.getValue();
            if (n.compareTo(Number.zero()) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Quantity can not either negative or less then zero",
                                                              null));
            } /*  else if ((rcptQnt.compareTo(n) == -1 ||
                        (rcptQnt.compareTo(n.add(rejectqty).add(rwkqty)) == -1))) { //if((rcptQnt.compareTo(obj)==-1||(rcptQnt.compareTo(obj.add(rejectqty).add(rwkqty))==-1 )) )

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "You Have Entered Wrong Ok Quantity", null));
             }*/ else {
                boolean b = isPrecisionValid(26, 6, n);
                if (!b) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision !!", null));
                }
            }

        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enter Quantity.", null));

        }

    }

    public void rwkQntValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        //System.out.println("reworkable in item table " + object);
        if (object != null) {
            Number n = (Number)object;
            Number rejectqty = new Number(0);
            rejectqty = (Number)rejectQuantBVar.getValue();
            Number okQnt = new Number(0);
            okQnt = (Number)okQntBar.getValue();
            Number rcptQnt = new Number(0);
            rcptQnt = (Number)rcptQntBVar.getValue();
            if (n.compareTo(Number.zero()) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Quantity can not either negative or less then zero",
                                                              null));
            } /* else if ((rcptQnt.compareTo(n) == -1 ||
                        (rcptQnt.compareTo(n.add(rejectqty).add(okQnt)) == -1))) { //if((rcptQnt.compareTo(obj)==-1||(rcptQnt.compareTo(obj.add(rejectqty).add(rwkqty))==-1 )) )

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "You Have Entered Wrong Reworkable Quantity", null));
            } */ else {
                boolean b = isPrecisionValid(26, 6, n);
                if (!b) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision !!", null));
                }
            }

        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enter Quantity.", null));

        }
    }

    public void okQntValdtorOfPopUp(FacesContext facesContext, UIComponent uIComponent, Object object) {
        //System.out.println(" okQntValidator on popup value " + object);
        
        if (object != null) {
            Number n = (Number)object;

            if (n.compareTo(Number.zero()) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Quantity can not either negative or less then zero",
                                                              null));
            } else {
                boolean b = isPrecisionValid(26, 6, n);
                if (!b) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision!!", null));
                }
            }

        }
    }

    public void rejQntValdtorOfPopUp(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // System.out.println(" rejected quant on popup value " + object);
        if (object != null) {
            Number n = (Number)object;

            if (n.compareTo(Number.zero()) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Quantity can not either negative or less then zero",
                                                              null));
            } else {
                boolean b = isPrecisionValid(26, 6, n);
                if (!b) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision !!", null));
                }
            }
        }
    }

    public void rwkQntValdtorOfPopUp(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // System.out.println(" reworkable quant validator value on popup " + object);
        if (object != null) {
            Number n = (Number)object;

            if (n.compareTo(Number.zero()) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Quantity can not either negative or less then zero",
                                                              null));
            } else {
                boolean b = isPrecisionValid(26, 6, n);
                if (!b) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision !!", null));
                }
            }

        }
    }


    public void setCustreadMode(String custreadMode) {
        this.custreadMode = custreadMode;
    }

    public String getCustreadMode() {
        return custreadMode;
    }

    public void setDisableQntFiled(boolean disableQntFiled) {
        this.disableQntFiled = disableQntFiled;
    }

    public boolean isDisableQntFiled() {
        return disableQntFiled;
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

    SlsRmaAppAMImpl slsam = (SlsRmaAppAMImpl)resolvElDC("SlsRmaAppAMDataControl");


    public void rcptQntPopupVCL(ValueChangeEvent valueChangeEvent) {

        System.out.println("value of recpr qty in VCL is ==" + valueChangeEvent.getNewValue());
        ViewObjectImpl vo = slsam.getSlsRmaItem2();
        ViewObject shpItmVw = slsam.getshpmntItmViewVO1();
        shpItmVw.getCurrentRow().getAttribute("ItmId");
        // System.out.println("ItemId in Valu chnge listener ="+ shpItmVw.getCurrentRow().getAttribute("ItmId"));
        Row row[] = vo.getFilteredRows("ItmId", shpItmVw.getCurrentRow().getAttribute("ItmId"));
        // System.out.println("filtered rows in VCl="+row.length);
        Number val = (Number)valueChangeEvent.getNewValue();
        // System.out.println("value in balue change listener is="+val);
        if (val != null) {
            if (row.length > 0) {
                if (val.compareTo(Number.zero()) != 0) {
                    for (Integer i = 0; i < row.length; i++) {
                        System.out.println("inside validator VCLLL");
                        row[i].setAttribute("OkQty", new Number(0));
                        row[i].setAttribute("RejQty", new Number(0));
                        row[i].setAttribute("RwkQty", new Number(0));
                    }
                }
            }
        } else {
            FacesMessage message = new FacesMessage("Enter Received Quantity");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(receiptQntbVarOnPopUp.getClientId(), message);
        }


    }

    public void receiptQntValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("obect " + object);
        if (object != null) {

            Number rptQty = (Number)object;
            boolean b = isPrecisionValid(26, 6, rptQty);
            Number shipQnt = (Number)shipQntbVar.getValue();
            System.out.println("Recepipt quantity " + rptQty + " shipment Qntity for that item is " + shipQnt +
                               " precision is " + b);
            System.out.println("Condition is rptQty.compareTo(shipQnt)" + rptQty.compareTo(shipQnt));
            if (rptQty.compareTo(Number.zero()) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Quantity can not  either zero  or negative", null));

            } else if (!b) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision !!",
                                                              null));

            } else if (rptQty.compareTo(shipQnt) == 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Received Quantity  of this item Exceeds to its Shipment Quantity",
                                                              null));

            } else {
                System.out.println("else");
                /*  System.out.println("Set 1");
                OperationBinding oppr = getBindings().getOperationBinding("compareItmWiseReceiveQtyToGeQty");
                oppr.getParamsMap().put("rcptQnty", rptQty);
                oppr.execute();
                System.out.println("Return Result "+oppr.getResult());
                if(oppr.getResult()!= null){
                    String S = (String)oppr.getResult();
                    if("MORE".equalsIgnoreCase(S)){
                        System.out.println("Quantity exceeds ");
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Received Quantity of this item exceeds Gate Entry Quantity ",
                                                                      null));

                    }
                } */
            }
        }
    }

    public void setShipQntbVar(RichInputText shipQntbVar) {
        this.shipQntbVar = shipQntbVar;
    }

    public RichInputText getShipQntbVar() {
        return shipQntbVar;
    }

    public void setReceiptQntbVarOnPopUp(RichInputText receiptQntbVarOnPopUp) {
        this.receiptQntbVarOnPopUp = receiptQntbVarOnPopUp;
    }

    public RichInputText getReceiptQntbVarOnPopUp() {
        return receiptQntbVarOnPopUp;
    }


    String rcptTxt = "A";

    public void rcptQntVCL(ValueChangeEvent valueChangeEvent) {
        /* oldRcptQnt = (Number)valueChangeEvent.getOldValue();
        System.out.println("old receilpt quanity =" + oldRcptQnt); */
        if (valueChangeEvent.getNewValue() != null || valueChangeEvent.getNewValue().toString().length() >= 0) {

            DCIteratorBinding iter = (DCIteratorBinding)getBindings().get("SlsRmaItmLot1Iterator");
            System.out.println("iter.getEstimatedRowCount()" + iter.getEstimatedRowCount());
            if (iter.getEstimatedRowCount() > 0) {
                OperationBinding oppr = getBindings().getOperationBinding("getOldRcptOkRwkRejQnt");
                oppr.execute();

                showPopup(lotBinSrDelPopUp, true);
                rcptTxt = "B";
            }
        }
    }

    public void setAddShipLink(String addShipLink) {
        this.addShipLink = addShipLink;
    }

    public String getAddShipLink() {
        return addShipLink;
    }

    public void setTaxRvrsBVar(RichSelectBooleanCheckbox taxRvrsBVar) {
        this.taxRvrsBVar = taxRvrsBVar;
    }

    public RichSelectBooleanCheckbox getTaxRvrsBVar() {
        return taxRvrsBVar;
    }

    public void setRmrkBVar(RichInputText rmrkBVar) {
        this.rmrkBVar = rmrkBVar;
    }

    public RichInputText getRmrkBVar() {
        return rmrkBVar;
    }

    // Number oldOkQnt = (Number)okQntBar.getValue();

    public void okVCL(ValueChangeEvent valueChangeEvent) { //
        //   Number oldOkQnt = (Number)valueChangeEvent.getOldValue();
        ///  System.out.println("valueChangeEvent.getOldValue() of ok qnt " + oldOkQnt);
        DCIteratorBinding iter = (DCIteratorBinding)getBindings().get("SlsRmaItmLot1Iterator");
        System.out.println("iter.getEstimatedRowCount()" + iter.getEstimatedRowCount());
        if (iter.getEstimatedRowCount() > 0) {
            System.out.println("inside");
            OperationBinding oppr = getBindings().getOperationBinding("getOldRcptOkRwkRejQnt");
            oppr.execute();
            showPopup(lotBinSrDelPopUp, true);
        }

    }
    // Number oldrejQnt = (Number)rejectQuantBVar.getValue();

    public void rejVCL(ValueChangeEvent valueChangeEvent) {
        //  oldrejQnt = (Number)valueChangeEvent.getOldValue();
        ///   System.out.println("old rejected value is=" + oldrejQnt);
        DCIteratorBinding iter = (DCIteratorBinding)getBindings().get("SlsRmaItmLot1Iterator");
        System.out.println("iter.getEstimatedRowCount()" + iter.getEstimatedRowCount());
        if (iter.getEstimatedRowCount() > 0) {
            System.out.println("inside");
            OperationBinding oppr = getBindings().getOperationBinding("getOldRcptOkRwkRejQnt");
            oppr.execute();
            showPopup(lotBinSrDelPopUp, true);
        }
    }
    // Number oldRwkQnt = (Number)rwkQntBVar.getValue();

    public void rwkVCL(ValueChangeEvent valueChangeEvent) {
        //  oldRwkQnt = (Number)valueChangeEvent.getOldValue();
        // System.out.println("oldRwkQnt= " + oldRwkQnt);
        System.out.println("set 3");
        DCIteratorBinding iter = (DCIteratorBinding)getBindings().get("SlsRmaItmLot1Iterator");
        System.out.println("iter.getEstimatedRowCount()" + iter.getEstimatedRowCount());
        if (iter.getEstimatedRowCount() > 0) {
            System.out.println("inside");
            OperationBinding oppr = getBindings().getOperationBinding("getOldRcptOkRwkRejQnt");
            oppr.execute();
            showPopup(lotBinSrDelPopUp, true);
        }
    }

    public void okToDeleteLOtBinSrData(ActionEvent actionEvent) {
        if ("B".equalsIgnoreCase(rcptTxt)) {
            Number okQnt = (Number)okQntBar.getValue();
            Number rejQnt = (Number)rejectQuantBVar.getValue();
            Number rwkQnt = (Number)rwkQntBVar.getValue();
            if (okQnt != null || rejQnt != null || rwkQnt != null) {
                System.out.println("when ok rej andd rwk is not null");
                OperationBinding oppr = getBindings().getOperationBinding("setOkRejRwkToZero");
                oppr.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(okQntBar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(rejectQuantBVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(rwkQntBVar);
            }
            rcptTxt = "A";
        }
        executeOperation("deleteLotBinSrData").execute();
        DCIteratorBinding iter = (DCIteratorBinding)getBindings().get("SlsRmaItmLot1Iterator");
        iter.executeQuery();
        lotBinSrDelPopUp.hide();
    }

    public void cancelToDelLotBinSrData(ActionEvent actionEvent) {
        /*  System.out.println("set ing old ok and " + oldOkQnt + "  receipt qnt " + oldRcptQnt + " old rwk qnt  " +
                           oldRwkQnt + " " + oldrejQnt);
        rcptQntBVar.setValue(oldRcptQnt);
        okQntBar.setValue(oldOkQnt);
        rwkQntBVar.setValue(oldOkQnt);
        rejectQuantBVar.setValue(oldrejQnt); */
        OperationBinding setOldRecptQnt = getBindings().getOperationBinding("setOldRecptQnt");
        setOldRecptQnt.execute();

        OperationBinding setOldOkQnt = getBindings().getOperationBinding("setOldOkQnt");
        setOldOkQnt.execute();
        OperationBinding setOldRejQnt = getBindings().getOperationBinding("setOldRejQnt");
        setOldRejQnt.execute();
        OperationBinding setOldRwkQnt = getBindings().getOperationBinding("setOldRwkQnt");
        setOldRwkQnt.execute();
        /*   rcptQntBVar.setValue((Number)setOldRecptQnt.getResult());
        okQntBar.setValue((Number)setOldOkQnt.getResult());
        rwkQntBVar.setValue((Number)setOldRwkQnt.getResult());
        rejectQuantBVar.setValue((Number)setOldRejQnt.getResult()); */
        lotBinSrDelPopUp.hide();
    }

    public void setLotBinSrDelPopUp(RichPopup lotBinSrDelPopUp) {
        this.lotBinSrDelPopUp = lotBinSrDelPopUp;
    }

    public RichPopup getLotBinSrDelPopUp() {
        return lotBinSrDelPopUp;
    }

    public void setSrLinkBVar(RichCommandImageLink srLinkBVar) {
        this.srLinkBVar = srLinkBVar;
    }

    public RichCommandImageLink getSrLinkBVar() {
        return srLinkBVar;
    }

    public void setWfNum(String WfNum) {
        this.WfNum = WfNum;
    }

    public String getWfNum() {
        return WfNum;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }
    private Boolean useTaxRvrsal = null;

    public void setUseTaxRvrsal(Boolean useTaxRvrsal) {
        this.useTaxRvrsal = useTaxRvrsal;
    }

    public Boolean getUseTaxRvrsal() {
        if (useTaxRvrsal == null) {
            StringBuffer i = new StringBuffer("N");
            OperationBinding binding = this.getBindings().getOperationBinding("checkTaxRvrlApplied");
            if (binding != null) {
                binding.execute();
                Object object = binding.getResult();
                if (object != null) {
                    i = (StringBuffer)object;
                }
                if (i.toString().equals("Y")) {
                    useTaxRvrsal = true;
                } else {
                    useTaxRvrsal = false;
                }
            }

        }

        return useTaxRvrsal;
    }
}
