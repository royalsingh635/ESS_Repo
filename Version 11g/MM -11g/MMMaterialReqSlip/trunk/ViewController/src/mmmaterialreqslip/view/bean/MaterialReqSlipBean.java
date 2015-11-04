package mmmaterialreqslip.view.bean;

import java.math.BigDecimal;

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
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;

import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class MaterialReqSlipBean {
    private Integer rqmt = null;
    private String whid = null;
    private Integer passedtowf = null;
    private String message = null;
    private RichPopup checkPopUp;
    private RichInputListOfValues itemId;
    private Boolean crs;
    private Boolean sas;
    private Boolean eds;
    private Boolean cas;
    private Boolean adi;
    private Boolean sai;
    private Boolean cai;
    private Boolean bts;
    private String mode;
    private String txn = null;
    private RichSelectOneChoice rqmtBind;
    private RichSelectOneChoice whBind;
    private RichInputDate dateBind;
    private String ResVisMode = "V";

    //  private RichInputText reqQuantityBind;
    private String itemid = null;
    private Integer status = null;
    private RichInputListOfValues itmNameBind;
    private String txnidforwf = null;
    private Integer saved = 0;
    private Integer pendatload = null; //for 1 it is pending else for 0 it is not.
    private Integer on_load =
        (Integer)BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("on_load_page").execute();
    private RichInputText authQtyBinding;
    private RichInputText resQtyBind;
    private RichColumn authQtyColumn;
    private RichColumn resQtyColumn;
    private RichSelectBooleanCheckbox cancelMRSBinding;
    private RichSelectBooleanCheckbox cancelitem;
    private RichInputDate reqdateBind;
    private Timestamp todaydate = new Timestamp(System.currentTimeMillis());
    private RichColumn prRaisedColumn;
    private RichCommandButton prRaiseBtnBind;
    private Boolean AuthSt = false;
    private RichInputListOfValues itemdescbind;
    private RichInputText transReqBind;
    private RichInputText reqQtyBinding;
    private RichColumn cancleColumn;
    private RichPopup valueChangePop;
    private RichTable itmTableBinding;
    private RichTable stkDetailBind;

    public MaterialReqSlipBean() {
        txn = (resolvEl("#{pageFlowScope.Source_Comp}").toString());
        if (txn.equals("create")) {
            mode = "cs";
            crs = true;
            eds = true;
            sas = false;
            cas = false;
            adi = false;
        } else if (txn.equals("view")) {
            mode = "vw";
            crs = false;
            eds = false;
            sas = true;
            cas = false;
            adi = true;

        }

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

    public void checkButtonPopup(ActionEvent actionEvent) {

        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setBindvarForAvailStk").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(stkDetailBind);
        showPopup(checkPopUp, true);
        
    }

    public void setCheckPopUp(RichPopup checkPopUp) {
        this.checkPopUp = checkPopUp;
    }

    public RichPopup getCheckPopUp() {
        return checkPopUp;
    }

    public void setItemId(RichInputListOfValues itemId) {
        this.itemId = itemId;
    }

    public RichInputListOfValues getItemId() {
        return itemId;
    }

    /**
     * @param actionEvent
     */
    public void createSlipActionListenr(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert1").execute();
        //   BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert").execute();
        crs = true;
        eds = true;
        sas = false;
        cas = false;
        adi = false;
        mode = "cs";

    }

    public void addItemActionListener(ActionEvent actionEvent) {
        OperationBinding chkReserv = getBindings().getOperationBinding("checkReservForMrs");
        chkReserv.execute();
        String Result = chkReserv.getResult().toString();
        System.out.println("Result is=" + Result);
        if (Result.length() > 0) {
            if (Result.equalsIgnoreCase("Y")) {
                System.out.println("results in bean is ===" + Result);
                ResVisMode = "E";
            } else {
                ResVisMode = "V";
            }
        }
        Integer fyid =
            (Integer)BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("generateFyId").execute();
        if (fyid <= 0) {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.519']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {
            if (itemdescbind.getValue() != null && !(itemdescbind.getValue().toString().equals(""))) {
                //check for duplicate
                OperationBinding op = getBindings().getOperationBinding("chkDuplicate");
                op.getParamsMap().put("itm", itemdescbind.getValue().toString());
                op.execute();
                Boolean dupli = false;
                if (op.getErrors().size() == 0)
                    dupli = (Boolean)op.getResult();
                else
                    System.out.println("error=" + op.getErrors());
                System.out.println("is duplicate?=" + dupli);
                if (dupli.booleanValue() == true) {
                    FacesMessage message = new FacesMessage("Item already Exist.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(itemdescbind.getClientId(), message);
                } else {
                    if (transReqBind.getValue() != null) {
                        if (new BigDecimal(transReqBind.getValue().toString()).compareTo(new BigDecimal(0)) > 0) {
                            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert").execute();
                            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setTxnIdforItem").execute();
                            crs = true;
                            eds = true;
                            sas = false;
                            cas = false;
                            adi = false;
                            mode = "ai";
                            itemdescbind.setValue(null);
                            itemdescbind.setValue("");
                            itemdescbind.resetValue();
                            transReqBind.setValue(null);
                            transReqBind.setValue("");
                            transReqBind.resetValue();
                            AdfFacesContext.getCurrentInstance().addPartialTarget(itemdescbind);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(transReqBind);

                        } else {
                            FacesMessage message = new FacesMessage("Required Quantity must be greater than Zero.");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(transReqBind.getClientId(), message);
                        }
                    }

                    else {
                        //rqmt null
                        FacesMessage message = new FacesMessage("Quantity Required.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(transReqBind.getClientId(), message);
                    }
                }
            } else {
                //itm null
                FacesMessage message = new FacesMessage("Item Required.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(itemdescbind.getClientId(), message);
            }


        }


    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void saveSlipActionList(ActionEvent actionEvent) {

        if (rqmtBind.getValue() != null) {
            if (whBind.getValue() != null) {
                if (dateBind.getValue() != null) {
                    if (dateBind.getValue().toString().length() > 0) {
                        Integer fyid =
                            (Integer)BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("generateFyId").execute();
                        if (fyid > 0) {
                            Integer rqfyid = 1;
                            if (reqdateBind.getValue() != null)
                                rqfyid =
                                        (Integer)BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("generateFyIdforReqDate").execute();
                            if (rqfyid > 0) {
                                String mrs =
                                    (String)BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("generateMrsNo").execute();

                                if (mrs == null) {
                                    FacesMessage msg = new FacesMessage("Can not Save MRS,contact to ESS");
                                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext ctx = FacesContext.getCurrentInstance();
                                    ctx.addMessage(null, msg);
                                } else {
                                    OperationBinding chkcncl = getBindings().getOperationBinding("checkCancel");
                                    chkcncl.execute();
                                    Boolean result = false;
                                    if (chkcncl.getErrors().size() == 0)
                                        result = (Boolean)chkcncl.getResult();
                                    if (result == true) {
                                        OperationBinding setst = getBindings().getOperationBinding("setMrsStat");
                                        setst.getParamsMap().put("stat", 341);
                                        setst.execute();
                                    }
                                    crs = false;
                                    eds = false;
                                    sas = true;
                                    cas = false;
                                    adi = true;
                                    mode = "ss";
                                    //  String wf_id = "W040";
                                    String action = "I";
                                    String remark = "A";
                                    String WfNum = null;
                                    Integer level = -1;
                                    Integer res = -1;
                                    //write code here for calling WF
                                    Integer usr_Id =
                                        Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
                                    String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
                                    Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                                    String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
                                    //get wf no
                                    OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                                    WfnoOp.getParamsMap().put("sloc_id", sloc_Id);
                                    WfnoOp.getParamsMap().put("cld_id", cld_Id);
                                    WfnoOp.getParamsMap().put("org_id", org_Id);
                                    WfnoOp.getParamsMap().put("doc_no", 18513);
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
                                        usrLevelOp.getParamsMap().put("RfqDocId", 18513);
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
                                            OperationBinding insTxnOp =
                                                getBindings().getOperationBinding("insIntoTxn");
                                            insTxnOp.getParamsMap().put("sloc_id", sloc_Id);
                                            insTxnOp.getParamsMap().put("cld_id", cld_Id);
                                            insTxnOp.getParamsMap().put("pOrgId", org_Id);
                                            insTxnOp.getParamsMap().put("RFQ_DOC_NO", 18513);
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
                                                System.out.println("error ins into insIntoTxn=" +
                                                                   insTxnOp.getErrors());
                                            //  System.out.println("Id is="+actionEvent.getComponent().getClientId());
                                            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
                                            OperationBinding updtStkCncl =
                                                getBindings().getOperationBinding("updtStkAftrCncl");
                                            updtStkCncl.execute();
                                            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
                                            if (actionEvent.getComponent().getClientId().toString().equals("r1:1:cil3")) {
                                                FacesMessage message = new FacesMessage("Saved Successfully.");
                                                message.setSeverity(FacesMessage.SEVERITY_INFO);
                                                FacesContext fc = FacesContext.getCurrentInstance();
                                                fc.addMessage(null, message);
                                            }
                                            saved = 1;
                                        } else {
                                            FacesMessage message =
                                                new FacesMessage("Something went wrong(Getting user level in Workflow).Please contact to ESS!");
                                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                            FacesContext fc = FacesContext.getCurrentInstance();
                                            fc.addMessage(null, message);
                                        }
                                    } else {
                                        FacesMessage message = new FacesMessage("Workflow not Define for MRS.");
                                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message);
                                    }

                                }
                            } else {
                                FacesMessage message =
                                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.519']}").toString());
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(dateBind.getClientId(), message);

                            }
                        } else {
                            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.519']}").toString());
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(dateBind.getClientId(), message);
                        }
                    } else {
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.539']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(dateBind.getClientId(), message);
                    }
                } else {
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.539']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(dateBind.getClientId(), message);
                }
            } else {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.411']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(whBind.getClientId(), message);
            }
        } else {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.573']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(rqmtBind.getClientId(), message);
        }


    }

    public void cancelSlipActionList(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();
        mode = "vw";
    }

    public void addItemSaveList(ActionEvent actionEvent) {
    }

    public void addItemCancelist(ActionEvent actionEvent) {

    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void itemIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

    }


    public void editSlipActionListnr(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer pending = null;
        OperationBinding chkUsr = getBindings().getOperationBinding("pendingRfqCheck");
        chkUsr.getParamsMap().put("SlocId", sloc_id);
        chkUsr.getParamsMap().put("CldId", cld_id);
        chkUsr.getParamsMap().put("OrgId", pOrgId);
        chkUsr.getParamsMap().put("RfqDocNo", 18513);
        chkUsr.execute();

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
        }
        System.out.println("Pending=" + pending);
        if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(usr_id) == 0) {
            OperationBinding CheckAuth = getBindings().getOperationBinding("CheckIsAuth");
            CheckAuth.execute();
            String isauth = "N";
            if (CheckAuth.getResult() != null)
                isauth = CheckAuth.getResult().toString();
            System.out.println("is auth=" + isauth);
            if (isauth.equals("Y")) {
                OperationBinding chkUsrvalid = getBindings().getOperationBinding("ChkUseValid");
                chkUsrvalid.execute();
                String chk = "N";
                if (chkUsrvalid.getErrors().size() == 0 && chkUsrvalid.getResult() != null) {
                    chk = (String)chkUsrvalid.getResult();
                }
                if (chk.equals("Y")) {
                    mode = "eas";
                } else {
                    String msg2 = "This Slip is Approved by other user , You can not Edit this.";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message2);
                }
            } else {
                mode = "es";
                pendatload = 0;
                passedtowf = pending;
                //call a method to save status of mrs
                OperationBinding op = bindings.getOperationBinding("mrsstatus");
                status = Integer.parseInt(op.execute().toString());
                crs = true;
                eds = true;
                sas = false;
                cas = false;
                adi = false;
            }
        } else {
            String username = null;
            OperationBinding opusr = getBindings().getOperationBinding("usrName");
            opusr.getParamsMap().put("usrId", pending);
            opusr.execute();
            username = (String)opusr.getResult();
            String msg2 = "This Slip is pending at [" + username + "] for approval, You can not Edit this.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        }
    }

    public void setCrs(Boolean crs) {
        this.crs = crs;
    }

    public Boolean getCrs() {
        return crs;
    }

    public void setSas(Boolean sas) {
        this.sas = sas;
    }

    public Boolean getSas() {
        return sas;
    }

    public void setEds(Boolean eds) {
        this.eds = eds;
    }

    public Boolean getEds() {
        return eds;
    }

    public void setCas(Boolean cas) {
        this.cas = cas;
    }

    public Boolean getCas() {
        return cas;
    }

    public void setAdi(Boolean adi) {
        this.adi = adi;
    }

    public Boolean getAdi() {
        return adi;
    }

    public void setSai(Boolean sai) {
        this.sai = sai;
    }

    public Boolean getSai() {
        return sai;
    }

    public void setCai(Boolean cai) {
        this.cai = cai;
    }

    public Boolean getCai() {
        return cai;
    }

    public void setBts(Boolean bts) {
        this.bts = bts;
    }

    public Boolean getBts() {
        return bts;
    }

    public void setRqmtBind(RichSelectOneChoice rqmtBind) {
        this.rqmtBind = rqmtBind;
    }

    public RichSelectOneChoice getRqmtBind() {
        return rqmtBind;
    }

    public void setWhBind(RichSelectOneChoice whBind) {
        this.whBind = whBind;
    }

    public RichSelectOneChoice getWhBind() {
        return whBind;
    }

    public void setDateBind(RichInputDate dateBind) {
        this.dateBind = dateBind;
    }

    public RichInputDate getDateBind() {
        return dateBind;
    }

    /*  public void setReqQuantityBind(RichInputText reqQuantityBind) {
        this.reqQuantityBind = reqQuantityBind;
    }

    public RichInputText getReqQuantityBind() {
        return reqQuantityBind;
    } */

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItmNameBind(RichInputListOfValues itmNameBind) {
        this.itmNameBind = itmNameBind;
    }

    public RichInputListOfValues getItmNameBind() {
        return itmNameBind;
    }

    public void itmNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

    }

    public void delItem(ActionEvent actionEvent) {
        OperationBinding CheckAuth = getBindings().getOperationBinding("CheckIsAuth");
        CheckAuth.execute();
        String isauth = CheckAuth.getResult().toString();
        if (isauth.equals("Y")) {
            String msg2 = "This MRS is Approved, You can not Delete Item from this Slip.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        } else {
            System.out.println("Deleting item");
            OperationBinding del = getBindings().getOperationBinding("deleteItm");
            del.execute();
            if (del.getErrors().size() == 0) {
                /*   System.out.println("commiting");
                    OperationBinding com = getBindings().getOperationBinding("Commit");
                    com.execute();
                    if(com.getErrors().size()==0)
                        System.out.println("commited successfully");
                    else
                        System.out.println("Error on commit="+com.getErrors());  */
            } else
                System.out.println("error on delete=" + del.getErrors());
        }
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setOn_load(Integer on_load) {
        this.on_load = on_load;
    }

    public Integer getOn_load() {
        return on_load;
    }

    public void itmIdChangeListener(ValueChangeEvent valueChangeEvent) {

    }

    public void cancelMRSValueChange(ValueChangeEvent valueChangeEvent) {
        sas = false;
        System.out.println("Cancel mrs");
        BindingContainer bindings = getBindings();
        OperationBinding op1 = bindings.getOperationBinding("mrsstatus");
        op1.execute();
        System.out.println("Status=" + status);
        status = (Integer)(op1.getResult());
        if (status == 341) {
            FacesMessage message = new FacesMessage("Cancelled MRS can not be changed");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(dateBind.getClientId(), message);
        } else {
            String st = valueChangeEvent.getNewValue().toString();
            OperationBinding op = bindings.getOperationBinding("setItemCancel");
            op.getParamsMap().put("st", st);
            op.getParamsMap().put("oldst", status);
            op.execute();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(cancleColumn);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cancelitem);
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
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


    public String saveAndFwd() {
        OperationBinding opb = getBindings().getOperationBinding("gettxnvalueforwf");
        opb.execute();
        if (opb.getErrors().size() == 0)
            txnidforwf = (String)opb.getResult();
        else
            System.out.println("error in getting txn=" + opb.getErrors());
        System.out.println("Txn in fwd=" + txnidforwf);

        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer pending = null;
        OperationBinding chkUsr = getBindings().getOperationBinding("pendingRfqCheck");
        chkUsr.getParamsMap().put("SlocId", sloc_id);
        chkUsr.getParamsMap().put("CldId", cld_id);
        chkUsr.getParamsMap().put("OrgId", pOrgId);
        chkUsr.getParamsMap().put("RfqDocNo", 18513);
        chkUsr.execute();

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
        }
        if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(usr_id) == 0) {
            if (saved == 1) {

                saved = 0;
                return "toWF";
            } else
                return null;
        } else {
            String username = null;
            OperationBinding opusr = getBindings().getOperationBinding("usrName");
            opusr.getParamsMap().put("usrId", pending);
            opusr.execute();
            username = (String)opusr.getResult();
            String msg2 = "This Slip is pending at [" + username + "] for approval, You can not forward this.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            return null;
        }


    }

    public void setTxnidforwf(String txnidforwf) {
        this.txnidforwf = txnidforwf;
    }

    public String getTxnidforwf() {
        return txnidforwf;
    }

    public void setPendatload(Integer pendatload) {
        this.pendatload = pendatload;
    }

    public Integer getPendatload() {
        return pendatload;
    }

    public void setPassedtowf(Integer passedtowf) {
        this.passedtowf = passedtowf;
    }

    public Integer getPassedtowf() {
        return passedtowf;
    }

    public void setAuthQtyBinding(RichInputText authQtyBinding) {
        this.authQtyBinding = authQtyBinding;
    }

    public RichInputText getAuthQtyBinding() {
        return authQtyBinding;
    }

    public void setResQtyBind(RichInputText resQtyBind) {
        this.resQtyBind = resQtyBind;
    }

    public RichInputText getResQtyBind() {
        return resQtyBind;
    }

    public void setAuthQtyColumn(RichColumn authQtyColumn) {
        this.authQtyColumn = authQtyColumn;
    }

    public RichColumn getAuthQtyColumn() {
        return authQtyColumn;
    }

    public void setResQtyColumn(RichColumn resQtyColumn) {
        this.resQtyColumn = resQtyColumn;
    }

    public RichColumn getResQtyColumn() {
        return resQtyColumn;
    }

    public void setCancelMRSBinding(RichSelectBooleanCheckbox cancelMRSBinding) {
        this.cancelMRSBinding = cancelMRSBinding;
    }

    public RichSelectBooleanCheckbox getCancelMRSBinding() {
        return cancelMRSBinding;
    }

    public void setCancelitem(RichSelectBooleanCheckbox cancelitem) {
        this.cancelitem = cancelitem;
    }

    public RichSelectBooleanCheckbox getCancelitem() {
        return cancelitem;
    }

    public void authqtychangelist(ValueChangeEvent valueChangeEvent) {
        //setQtyAccToAuthQty
        /*  System.out.println("VCL of Auth Qty");
        if(valueChangeEvent.getNewValue()!=null)
        {
        BigDecimal qty=new BigDecimal(valueChangeEvent.getNewValue().toString());


                OperationBinding op=getBindings().getOperationBinding("setQtyAccToAuthQty");
                op.getParamsMap().put("qty",qty);
                op.execute();

        }
        else
        {
               throw new ValidatorException(new
               FacesMessage(FacesMessage.SEVERITY_ERROR,"Please Enter Quantity.",null));

           } */
        BigDecimal qty = BigDecimal.ZERO;
        if (valueChangeEvent.getNewValue() != null)
            qty = (BigDecimal)valueChangeEvent.getNewValue();
        OperationBinding op = getBindings().getOperationBinding("SetStkResQty");
        op.getParamsMap().put("qty", qty);
        op.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(resQtyBind);
    }


    public void resQtyChangeListener(ValueChangeEvent valueChangeEvent) {
        /*  System.out.println("VCL of Resv Qty");
        if(valueChangeEvent.getNewValue()!=null)
        {
        }
        else
        {
               throw new ValidatorException(new
               FacesMessage(FacesMessage.SEVERITY_ERROR,"Please Enter Quantity.",null));

           } */
    }

    public void setReqdateBind(RichInputDate reqdateBind) {
        this.reqdateBind = reqdateBind;
    }

    public RichInputDate getReqdateBind() {
        return reqdateBind;
    }

    public void setTodaydate(Timestamp todaydate) {
        this.todaydate = todaydate;
    }

    public Timestamp getTodaydate() {
        return todaydate;
    }

    public void authQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        reqQtyBinding.processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(reqQtyBinding);
        System.out.println("Auth Qty Validator");
        if (object != null) {
            BigDecimal Auth = new BigDecimal(object.toString());
            if (Auth.compareTo(BigDecimal.ZERO) >= 0) {

                Boolean is = isPrecisionValid(26, 6, Auth);
                if (is.toString().equalsIgnoreCase("true")) {
                    OperationBinding chk = getBindings().getOperationBinding("checkAuthQty");
                    chk.getParamsMap().put("Auth", Auth);
                    chk.execute();
                    Integer value = Integer.parseInt(chk.getResult().toString());
                    if (value <= 0) {

                    } else {
                        if (value == 1) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          "Authorised Quantity is more than Required Quantity of this item.",
                                                                          null));
                        } else if (value == 2) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          "Authorised Quantity can not be entered without Required Quantity",
                                                                          null));
                        }

                    }
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision(26,6).", null));
                }


            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Quantity can not be less than Zero.", null));
            }
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Quantity Required", null));
        }
    }

    public void ResQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        reqQtyBinding.processUpdates(FacesContext.getCurrentInstance());
        authQtyBinding.processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(reqQtyBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(authQtyBinding);
        System.out.println("Res Qty Validator");
        if (object != null) {

            BigDecimal Res = new BigDecimal(object.toString());
            if (Res.compareTo(BigDecimal.ZERO) >= 0) {

                Boolean is = isPrecisionValid(26, 6, Res);
                if (is.toString().equalsIgnoreCase("true")) {
                    OperationBinding chk = getBindings().getOperationBinding("checkResQty");
                    chk.getParamsMap().put("Res", Res);
                    chk.execute();
                    Integer value = Integer.parseInt(chk.getResult().toString());
                    if (value <= 0) {

                    } else {
                        if (value == 1) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          "Reserved Quantity must be less than to Authorised Quantity and Available Stock.",
                                                                          null));
                        } else if (value == 2) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          "Reserve Quantity can not entered without Authorised Quantity",
                                                                          null));
                        }
                    }
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision(26,6).", null));
                }


            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Quantity can not be less than Zero.", null));
            }
        }
    }

    public void raisePRActionListener(ActionEvent actionEvent) {
        OperationBinding chkUsr = getBindings().getOperationBinding("ChkUseValid");
        chkUsr.execute();
        String chk = "Y";
        if (chkUsr.getErrors().size() == 0 && chkUsr.getResult() != null) {
            chk = (String)chkUsr.getResult();
        }
        if (chk.equals("Y")) {

            OperationBinding chkPr = getBindings().getOperationBinding("checkPr");
            chkPr.execute();
            if (chkPr.getErrors().size() == 0)
                if (chkPr.getResult() != null) {
                    System.out.println("PR is already raised");
                    String msg2 = "PR already raised for this MRS with PR No.: " + chkPr.getResult();
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message2);
                } else {
                    OperationBinding rp = getBindings().getOperationBinding("raisePr");
                    rp.execute();
                    BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
                    if (rp.getResult() != null) {

                        String msg2 = "PR Raised Successfully.\n PR No is : " + rp.getResult();
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message2);
                    }
                }
            mode = "ss";
        } else {
            String msg2 = "You can not Raise PR.";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        }

    }

    public void setPrRaisedColumn(RichColumn prRaisedColumn) {
        this.prRaisedColumn = prRaisedColumn;
    }

    public RichColumn getPrRaisedColumn() {
        return prRaisedColumn;
    }

    public void setPrRaiseBtnBind(RichCommandButton prRaiseBtnBind) {
        this.prRaiseBtnBind = prRaiseBtnBind;
    }

    public RichCommandButton getPrRaiseBtnBind() {
        return prRaiseBtnBind;
    }

    public void prRaiseValueChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue().toString().equals("true"))
            if (authQtyBinding.getValue() != null) {
            } else {
                String msg2 = "Authorised Quantity is Required";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(authQtyBinding.getClientId(), message2);
            }
    }

    public void setAuthSt(Boolean AuthSt) {
        this.AuthSt = AuthSt;
    }

    public Boolean getAuthSt() {
        return AuthSt;
    }

    public void cancelItemVCL(ValueChangeEvent valueChangeEvent) {
        Integer status1 = null;
        sas = false;
        OperationBinding st = getBindings().getOperationBinding("mrsstatus");
        st.execute();
        if (st.getErrors().size() == 0) {
            status1 = (Integer)st.getResult();
        }
        if (valueChangeEvent.getNewValue().toString().equals("true")) {
            //check for all items in current docid
            OperationBinding ca = getBindings().getOperationBinding("checkAllItemCancel");
            ca.execute();
            if (ca.getErrors().size() == 0) {
                Boolean bool = (Boolean)ca.getResult();
                if (bool == true) {
                    cancelMRSBinding.setValue(true);
                } else {
                    //set status as before.and uncheck cancel
                    /*    OperationBinding setst = getBindings().getOperationBinding("setMrsStat");

                               setst.getParamsMap().put("stat",status1);
                               setst.execute();  */
                    cancelMRSBinding.setValue(false);
                }
            }
        } else {
            OperationBinding setst = getBindings().getOperationBinding("setMrsStat");

            setst.getParamsMap().put("stat", status1);
            setst.execute();
            cancelMRSBinding.setValue(false);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(cancelMRSBinding);
    }

    public void itmQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("Required Qty Validator ");
        if (object != null) {
            BigDecimal qty = new BigDecimal(object.toString());
            if (qty.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Quantity must be greater than Zero.", null));
            } else {
                Boolean is = isPrecisionValid(26, 6, qty);
                if (is.toString().equalsIgnoreCase("true")) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision(26,6).", null));
                }
            }

        } else
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please Enter Quantity.",
                                                          null));
    }

    public void itmIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
    }


    public void transItemDescVCE(ValueChangeEvent valueChangeEvent) {
        //code to set itm id in itmvo
        OperationBinding op = getBindings().getOperationBinding("setItemId");
        op.getParamsMap().put("itmname", valueChangeEvent.getNewValue());
        op.execute();
    }

    public void setItemdescbind(RichInputListOfValues itemdescbind) {
        this.itemdescbind = itemdescbind;
    }

    public RichInputListOfValues getItemdescbind() {
        return itemdescbind;
    }

    public void setTransReqBind(RichInputText transReqBind) {
        this.transReqBind = transReqBind;
    }

    public RichInputText getTransReqBind() {
        return transReqBind;
    }


    public void reqQtyVCL(ValueChangeEvent valueChangeEvent) {
        System.out.println("VCL of Required Qty");
        if (valueChangeEvent.getNewValue() != null) {
            BigDecimal qty = new BigDecimal(valueChangeEvent.getNewValue().toString());
            if (qty.compareTo(BigDecimal.ZERO) <= 0) {
                FacesMessage message2 = new FacesMessage("Quantity must be greater than Zero.");
                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(reqQtyBinding.getClientId(), message2);
            } else {
                OperationBinding op = getBindings().getOperationBinding("setQtyAccToReqQty");
                op.getParamsMap().put("qty", qty);
                op.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(authQtyBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(resQtyBind);

            }
        } else {
            FacesMessage message2 = new FacesMessage("Please Enter Quantity.");
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(reqQtyBinding.getClientId(), message2);
        }
    }

    public void setReqQtyBinding(RichInputText reqQtyBinding) {
        this.reqQtyBinding = reqQtyBinding;
    }

    public RichInputText getReqQtyBinding() {
        return reqQtyBinding;
    }

    public void setCancleColumn(RichColumn cancleColumn) {
        this.cancleColumn = cancleColumn;
    }

    public RichColumn getCancleColumn() {
        return cancleColumn;
    }

    public void whChangeLis(ValueChangeEvent valueChangeEvent) {
        OperationBinding op = getBindings().getOperationBinding("countItems");
        op.execute();
        Integer len = 0;
        if (op.getErrors().size() == 0)
            len = (Integer)op.getResult();
        if (len > 0) {
            whid = (String)valueChangeEvent.getOldValue();
            message = "Warehouse";
            showPopup(valueChangePop, true);

        }
    }

    public void changePopupDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().equals(Outcome.ok)) {
            OperationBinding op = getBindings().getOperationBinding("deleteItemonWhChange");
            op.execute();
            message = null;
            whid = null;
            rqmt = null;
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBinding);
        }
    }

    public void setValueChangePop(RichPopup valueChangePop) {
        this.valueChangePop = valueChangePop;
    }

    public RichPopup getValueChangePop() {
        return valueChangePop;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void ReqAreaVCL(ValueChangeEvent valueChangeEvent) {

        OperationBinding op = getBindings().getOperationBinding("countItems");
        op.execute();
        Integer len = 0;
        if (op.getErrors().size() == 0)
            len = (Integer)op.getResult();
        if (len > 0) {
            rqmt = (Integer)valueChangeEvent.getOldValue();
            message = "Requirement Area";
            showPopup(valueChangePop, true);
        }
    }

    public void popupcancelList(PopupCanceledEvent popupCanceledEvent) {
        if (message.equals("Warehouse")) {
            whBind.processUpdates(FacesContext.getCurrentInstance());
            System.out.println(whid);
            whBind.resetValue();
            whBind.setValue(whid);
            OperationBinding op = getBindings().getOperationBinding("SetWh");
            op.getParamsMap().put("Wh", whid);
            op.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(whBind);
        } else if (message.equals("Requirement Area")) {
            rqmtBind.processUpdates(FacesContext.getCurrentInstance());
            System.out.println(rqmt);
            rqmtBind.resetValue();
            rqmtBind.setValue(rqmt);
            OperationBinding op = getBindings().getOperationBinding("SetRQA");
            op.getParamsMap().put("rqa", rqmt);
            op.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(rqmtBind);
        }
        message = null;
        whid = null;
        rqmt = null;
        OperationBinding opr = getBindings().getOperationBinding("ExecuteVo");
        opr.execute();
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, BigDecimal total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();

        vc.setPrecision(precision);


        vc.setScale(scale);

        return vc.validateValue(total);
    }

    public void transReqQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            BigDecimal obj = (BigDecimal)object;
            if (obj.compareTo(BigDecimal.ZERO) > 0) {
                Boolean is = isPrecisionValid(26, 6, obj);
                if (is.toString().equalsIgnoreCase("true")) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision(26,6).", null));
                }
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Quantity must be greater than Zero.", null));
            }
        }
    }

    public void RequiredDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding op = getBindings().getOperationBinding("chkValidReqDate");
            op.getParamsMap().put("reqDt", object);
            op.execute();
            String chk = "Y";
            if (op.getErrors().size() == 0 && op.getResult() != null)
                chk = (String)op.getResult();
            if (chk.equals("N"))
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Required Date must be On or After MRS Date.", null));
        }
    }

    public void setItmTableBinding(RichTable itmTableBinding) {
        this.itmTableBinding = itmTableBinding;
    }

    public RichTable getItmTableBinding() {
        return itmTableBinding;
    }

    public void setResVisMode(String ResVisMode) {
        this.ResVisMode = ResVisMode;
    }

    public String getResVisMode() {
        return ResVisMode;
    }

    public void setStkDetailBind(RichTable stkDetailBind) {
        this.stkDetailBind = stkDetailBind;
    }

    public RichTable getStkDetailBind() {
        return stkDetailBind;
    }
}
