package svcservicecontract.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adfmailinterface.view.bean.utill.MailSender;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
//import adfmailinterface.view.bean.utill.MailSender;

public class CstmrBean {
    private List<UploadedFile> uploadedFile;
    private RichShowDetailItem itmTabBind;
    private RichShowDetailItem asgnTabBind;
    private RichShowDetailItem focTabBid;
    private RichInputText fileBindName;
    private RichInputText prtyNmBind;
    private RichPopup notWorkingPopup;
    private RichInputListOfValues locIdBind;
    private RichPanelGroupLayout locGrpbind;
    private RichInputListOfValues cstmrAddsId;
    private RichInputListOfValues itmAddsIdBind;
    private RichPanelGroupLayout itmLocPgrpBind;
    private RichInputDate cstmrDtBind;
    private RichInputDate cstmrEndBind;
    private RichInputDate itmStrtDtBind;
    private RichInputDate itmEndDtBind;
    private RichInputListOfValues eoNmBind;
    private RichSelectOneRadio discTypeBind;
    private RichTable pmtTablBind;
    private RichPopup lovAddsPopupBind;
    private RichSelectBooleanCheckbox slctDslctBinding;
    private RichLink mailBtnBind;
    private RichButton cstmrAprvdBtnBind;
    private RichInputText itmSrBinding;
    private RichTable serialTableBind;
    private RichInputText serialisedItmBind;
    //  private RichShowDetailItem siteSurvayBind;
    public void setUploadedFile(List<UploadedFile> uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<UploadedFile> getUploadedFile() {
        return uploadedFile;
    }
    private RichShowDetailItem quotTabBind;
    private RichShowDetailItem shdlTabBind;
    private RichShowDetailItem pmtTabBind;
    private RichShowDetailItem survayTabBind;
    private RichInputText taxableItmAmtSpBind;
    private RichPopup taxPopBind;
    private RichInputDate paymentDatebind;
    private RichInputText payAmtBind;
    private RichInputListOfValues itmNmBind;
    private RichSelectOneChoice pmtModeBind;
    private RichInputText totAmtBind;
    private RichSelectOneChoice rqmtAreaBind;
    private RichInputListOfValues empNmBind;
    private RichPanelGroupLayout totalPnlGrpBinding;
    private RichInputDate shdlDateBinding;
    private RichTable focItmTblbind;
    private RichInputText itmQtyBind;
    private RichInputText itmPriceBind;
    private RichTable svcSchdlTabBind;
    private RichSelectOneChoice docStatBinding;
    private RichInputDate prdFrmDateBind;
    private RichInputDate prdToDtBind;
    private RichInputText scNoBind;
    private String notWorkingItem;


    public CstmrBean() {
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

    ADFLogger adfLog = (ADFLogger) ADFLogger.createADFLogger(CstmrBean.class);

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

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void cstmrValueChange(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent.getNewValue() != null) {
            String eoNm = valueChangeEvent.getNewValue().toString();
            OperationBinding geteo = getBindings().getOperationBinding("getCoaForEo");
            geteo.getParamsMap().put("entityName", eoNm);
            geteo.execute();
        }
    }

    public void AddItmActionListner(ActionEvent actionEvent) {
        // Add event code here...
        adfLog.info("add item action listener call");
        OperationBinding itmchk = getBindings().getOperationBinding("isItmExists");
        itmchk.execute();
        String flag = null;
        if (itmchk.getErrors().isEmpty()) {
            flag = itmchk.getResult().toString();
        } else {
            adfLog.info("error found is " + itmchk.getErrors());
        }
//        if ("Y".equalsIgnoreCase(flag)) {
            OperationBinding gnrtScNo = getBindings().getOperationBinding("crtItmSvc");
            gnrtScNo.execute();
            getItmNmBind().setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getItmNmBind());
            itmTabBind.setDisclosed(true);
            OperationBinding institmloc = getBindings().getOperationBinding("isrtIntoItmLocation");
            institmloc.execute();
//        } else if ("N".equalsIgnoreCase(flag)) {
//            String msg = "Item Already Exists";
//            /**commented bbecoz if we add another item location in this than message, no need to display as per the discussion with -Sukanta sir*/
            //   showFacesMessage(msg, "E", false, "F", getItmNmBind().getClientId());
            // showFacesMessage("Service Contract  Save Successfully", "I", false, "F", null);
//        }
    }

    public void AddactionListner(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void editActionListner(ActionEvent actionEvent) {
        // Add event code here...
        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        int userId = 0;
        OperationBinding check = getBindings().getOperationBinding("pendingCheck");
        check.getParamsMap().put("SlocId", sloc_Id);
        check.getParamsMap().put("CldId", cld_Id);
        check.getParamsMap().put("OrgId", org_Id);
        check.getParamsMap().put("DocNo", 23001);
        check.execute();

        if (check.getErrors().isEmpty()) {
            userId = Integer.parseInt(check.getResult().toString());
            System.out.println("userId function " + userId);
        }
        if (usr_Id == userId || userId == -1) {
            System.out.println("user_ id" + userId);
            RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "C");
        } else {
            String name = "Anonymous";
            OperationBinding getusrname = getBindings().getOperationBinding("getUsrNm");
            getusrname.getParamsMap().put("usrId", userId);
            getusrname.execute();
            if (getusrname.getErrors().size() == 0 && getusrname.getResult() != null)
                name = (String) getusrname.getResult();
            String msg = "This Service Contract is Pending at [" + name + "]. You can't Edit this Service Contract.";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }


    }

    public void saveActionListner(ActionEvent actionEvent) {
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String p_org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int p_userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        //  String tableNm = "SVC$SC";//saveFwdActn1
        
          if(serialisedItmBind.getValue()!=null&& serialisedItmBind.getValue().toString().length()>0){
            adfLog.info("---------1111value of serial flag---------"+serialisedItmBind.getValue().toString());
            if(serialisedItmBind.getValue().toString().equalsIgnoreCase("Y")){
                adfLog.info("---------2222value of serial flag---------"+serialisedItmBind.getValue().toString());
                OperationBinding srQty = getBindings().getOperationBinding("isSerialNoValid");//"checkQtyBfrSave"
                srQty.getParamsMap().put("SrNo", "");
                srQty.execute();
                if(srQty.getResult()!=null){
                    adfLog.info("----check before save---------------inside bean--------"+srQty.getResult().toString()+" ---SR flag--- "+serialisedItmBind.getValue().toString());
                    if(srQty.getResult().toString().equalsIgnoreCase("Y")||srQty.getResult().toString().equalsIgnoreCase("O") ){
                        showFacesMessage("Enter Require Serial No.", "E", false, "F", null);
                        return;
                    }
                    adfLog.info("----check before save---------------inside bean--------"+srQty.getResult().toString());
                }
            }
        }
        
        OperationBinding resettax = getBindings().getOperationBinding("taxablecheck");
        resettax.execute();
        String istxble = null;
        if (resettax.getErrors().isEmpty()) {
            istxble = resettax.getResult().toString();
            adfLog.info("txble check result " + istxble);
        }
        if ("Y".equalsIgnoreCase(istxble)) {
            OperationBinding chkAmt = getBindings().getOperationBinding("chkPmtAmt");
            chkAmt.execute();
            String chkPmt = null;
            if (chkAmt.getResult() != null)
                chkPmt = chkAmt.getResult().toString();
            if ("Y".equalsIgnoreCase(chkPmt)) {
                OperationBinding geteo = getBindings().getOperationBinding("generateScNo");
                geteo.execute();

                String action = "I";
                String remark = "A";
                String WfNum = null;
                Integer level = 0;
                Integer res = -1;
                //int amount=0;
                OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                WfnoOp.getParamsMap().put("SlocId", p_sloc_id);
                WfnoOp.getParamsMap().put("CldId", p_cldId);
                WfnoOp.getParamsMap().put("OrgId", p_org_id);
                WfnoOp.getParamsMap().put("DocNo", 23001);
                WfnoOp.execute();
                if (WfnoOp.getResult() != null) {
                    if (WfnoOp.getResult() != null)
                        WfNum = WfnoOp.getResult().toString();
                    System.out.println("WfNum is : " + WfNum);
                }

                if (WfNum != null) {
                    //get user level
                    OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                    usrLevelOp.getParamsMap().put("SlocId", p_sloc_id);
                    usrLevelOp.getParamsMap().put("CldId", p_cldId);
                    usrLevelOp.getParamsMap().put("OrgId", p_org_id);
                    usrLevelOp.getParamsMap().put("UsrId", p_userId);
                    usrLevelOp.getParamsMap().put("WfNo", WfNum);
                    usrLevelOp.getParamsMap().put("DocNo", 23001);
                    usrLevelOp.execute();
                    level = -1;
                    if (usrLevelOp.getResult() != null) {
                        level = Integer.parseInt(usrLevelOp.getResult().toString());
                        System.out.println("user level" + level);
                    }

                    if (level >= 0) { //insert into txn
                        OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                        insTxnOp.getParamsMap().put("SlocId", p_sloc_id);
                        insTxnOp.getParamsMap().put("CldId", p_cldId);
                        insTxnOp.getParamsMap().put("OrgId", p_org_id);
                        insTxnOp.getParamsMap().put("DocNo", 23001);
                        insTxnOp.getParamsMap().put("WfNo", WfNum);
                        insTxnOp.getParamsMap().put("usr_idFrm", p_userId);
                        insTxnOp.getParamsMap().put("usr_idTo", p_userId);
                        insTxnOp.getParamsMap().put("levelFrm", level);
                        insTxnOp.getParamsMap().put("levelTo", level);
                        insTxnOp.getParamsMap().put("action", action);
                        insTxnOp.getParamsMap().put("remark", remark);
                        insTxnOp.getParamsMap().put("amount", 0);
                        insTxnOp.execute();

                        if (insTxnOp.getResult() != null) {
                            res = Integer.parseInt(insTxnOp.getResult().toString());
                            System.out.println("txn save output" + res);
                        }

                        OperationBinding saveOp = getBindings().getOperationBinding("Commit");
                        saveOp.execute();
                        RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
                        //setMode("V");
                        showFacesMessage("Service Contract  Save Successfully", "I", false, "F", null);
                        //  showFacesMessage("Service Contract No "+scno+" Save Successfully", "I", false, "F", null);
                    } else {
                        showFacesMessage("Something went wrong(User Level in Workflow).Please Contact to ESS!", "E",
                                         false, "F", null);

                    }
                } else {
                    showFacesMessage("Workflow is not created for Service Contract.", "E", false, "F", null);

                }


            } else {
                String msg =
                    "Amount Mismatch.(Total Payment Schedule Amount Should be Equal to Total Service Contract Amount)";

                showFacesMessage(msg, "I", false, "F", null);
            }
        }

    }

    public String savefwdAction() {
        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        int userId = 0;
        
        //check serial qty
        if(serialisedItmBind.getValue()!=null&& serialisedItmBind.getValue().toString().length()>0){
            adfLog.info("---------1111value of serial flag---------"+serialisedItmBind.getValue().toString());
            if(serialisedItmBind.getValue().toString().equalsIgnoreCase("Y")){
                adfLog.info("---------2222value of serial flag---------"+serialisedItmBind.getValue().toString());
                OperationBinding srQty = getBindings().getOperationBinding("isSerialNoValid");//"checkQtyBfrSave"
                srQty.getParamsMap().put("SrNo", "");
                srQty.execute();
                if(srQty.getResult()!=null){
                    adfLog.info("----check before save---------------inside bean--------"+srQty.getResult().toString()+" ---SR flag--- "+serialisedItmBind.getValue().toString());
                    if(srQty.getResult().toString().equalsIgnoreCase("Y")||srQty.getResult().toString().equalsIgnoreCase("O") ){
                        showFacesMessage("Enter Require Serial No.", "E", false, "F", null);
                        return null;
                    }
                    adfLog.info("----check before save---------------inside bean--------"+srQty.getResult().toString());
                }
            }
        }
        
        
        OperationBinding check = getBindings().getOperationBinding("pendingCheck");
        check.getParamsMap().put("SlocId", sloc_Id);
        check.getParamsMap().put("CldId", cld_Id);
        check.getParamsMap().put("OrgId", org_Id);
        check.getParamsMap().put("DocNo", 23001);
        check.execute();

        if (check.getErrors().isEmpty()) {
            userId = Integer.parseInt(check.getResult().toString());
            System.out.println("userId function " + userId);
        }
        if (usr_Id == userId || userId == -1) {
            OperationBinding resettax = getBindings().getOperationBinding("taxablecheck");
            resettax.execute();
            String istxble = null;
            if (resettax.getErrors().isEmpty()) {
                istxble = resettax.getResult().toString();
                adfLog.info("txble check result " + istxble);
            }
            if ("Y".equalsIgnoreCase(istxble)) {
                OperationBinding chkAmt = getBindings().getOperationBinding("chkPmtAmt");
                chkAmt.execute();
                String chkPmt = null;
                if (chkAmt.getResult() != null)
                    chkPmt = chkAmt.getResult().toString();
                if ("Y".equalsIgnoreCase(chkPmt)) {
                    OperationBinding notWorking = getBindings().getOperationBinding("itemNotWorking");
                    notWorking.execute();
                    // showPopup(notWorkingPopup, true);
                    adfLog.info(notWorkingItem + " -------array list and error item not working " +
                                notWorking.getErrors() + " result is " + notWorking.getResult());

                    if ("N".equalsIgnoreCase(notWorking.getResult().toString())) {

                    } else {
                        adfLog.info("set arraylist value is to not working if before");
                        notWorkingItem = notWorking.getResult().toString();
                        //  adfLog.info("set arraylist value is to not working if after");
                        // setNotWorkingItem(notWorking.getResult().toString());
                        adfLog.info("set arraylist value is before show popup");
                        showPopup(notWorkingPopup, true);
                        adfLog.info(notWorkingItem + "set arraylist value is after show popup");
                        return null;

                    }


                    adfLog.info(notWorkingItem + " -------array list and error item not working " +
                                notWorking.getErrors());

                    OperationBinding geteo = getBindings().getOperationBinding("generateScNo");
                    geteo.execute();
                    String action = "I";
                    String remark = "A";
                    String WfNum = null;
                    Integer level = 0;
                    Integer res = -1;
                    Integer pending = 0;
                    OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                    WfnoOp.getParamsMap().put("SlocId", sloc_Id);
                    WfnoOp.getParamsMap().put("CldId", cld_Id);
                    WfnoOp.getParamsMap().put("OrgId", org_Id);
                    WfnoOp.getParamsMap().put("DocNo", 23001);
                    WfnoOp.execute();
                    if (WfnoOp.getResult() != null) {
                        if (WfnoOp.getResult() != null)
                            WfNum = WfnoOp.getResult().toString();
                        System.out.println("WfnoOp : " + WfNum);
                    }

                    if (WfNum != null && !"0".equalsIgnoreCase(WfNum)) {
                        //get user level
                        OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                        usrLevelOp.getParamsMap().put("SlocId", sloc_Id);
                        usrLevelOp.getParamsMap().put("CldId", cld_Id);
                        usrLevelOp.getParamsMap().put("OrgId", org_Id);
                        usrLevelOp.getParamsMap().put("UsrId", usr_Id);
                        usrLevelOp.getParamsMap().put("WfNo", WfNum);
                        usrLevelOp.getParamsMap().put("DocNo", 23001);
                        usrLevelOp.execute();
                        level = -1;
                        if (usrLevelOp.getResult() != null) {
                            level = Integer.parseInt(usrLevelOp.getResult().toString());
                            System.out.println("user level " + level);
                        }

                        if (level >= 0) {
                            //insert into txn
                            OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                            insTxnOp.getParamsMap().put("SlocId", sloc_Id);
                            insTxnOp.getParamsMap().put("CldId", cld_Id);
                            insTxnOp.getParamsMap().put("OrgId", org_Id);
                            insTxnOp.getParamsMap().put("DocNo", 23001);
                            insTxnOp.getParamsMap().put("WfNo", WfNum);
                            insTxnOp.getParamsMap().put("usr_idFrm", usr_Id);
                            insTxnOp.getParamsMap().put("usr_idTo", usr_Id);
                            insTxnOp.getParamsMap().put("levelFrm", level);
                            insTxnOp.getParamsMap().put("levelTo", level);
                            insTxnOp.getParamsMap().put("action", action);
                            insTxnOp.getParamsMap().put("remark", remark);
                            insTxnOp.getParamsMap().put("amount", 0);
                            insTxnOp.execute();

                            //     adflog.info("now the errors in txn is"+insTxnOp.getErrors());
                            if (insTxnOp.getResult() != null) {
                                res = Integer.parseInt(insTxnOp.getResult().toString());
                                System.out.println("ins ito txn" + res);
                            }


                            //Check Pending
                            OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
                            chkUsr.getParamsMap().put("SlocId", sloc_Id);
                            chkUsr.getParamsMap().put("CldId", cld_Id);
                            chkUsr.getParamsMap().put("OrgId", org_Id);
                            chkUsr.getParamsMap().put("DocNo", 23001);
                            chkUsr.execute();

                            if (chkUsr.getResult() != null) {
                                pending = Integer.parseInt(chkUsr.getResult().toString());
                                System.out.println("pending check" + pending);
                            }
                            if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(usr_Id) == 0) {
                                OperationBinding saveOp = getBindings().getOperationBinding("Commit");
                                saveOp.execute();
                                RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
                                //showFacesMessage("MRN No "+ids+" Save Successfully", "I", false, "F", null);
                                //no pending
                                return "towf";
                            }
                            /*  else {//pending
                                                         return null;
                                                         } */
                        } else {
                            showFacesMessage("Something went wrong(User Level in Workflow).Please Contact to ESS!", "E",
                                             false, "F", null);
                            return null;
                        }
                    } else {
                        showFacesMessage("Workflow not Created for Service Contract", "E", false, "F", null);
                        return null;
                    }

                } else {
                    String msg =
                        "Amount Mismatch.(Total Payment Schedule Amount Should be Equal to Total Service Contract Amount)";

                    showFacesMessage(msg, "I", false, "F", null);
                    return null;
                }
            }
        } else {
            String name = "Anonymous";
            OperationBinding getusrname = getBindings().getOperationBinding("getUsrNm");
            getusrname.getParamsMap().put("usrId", userId);
            getusrname.execute();
            if (getusrname.getErrors().size() == 0 && getusrname.getResult() != null)
                name = (String) getusrname.getResult();
            String msg = "This Service Contract is Pending at [" + name + "]. You can't forward this Service Contract.";


            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return null;
        }
        return null;
    }

    public void saveAndFwdActionListener(ActionEvent actionEvent) {
        // Add event code here...

    }

    public void setItmTabBind(RichShowDetailItem itmTabBind) {
        this.itmTabBind = itmTabBind;
    }

    public RichShowDetailItem getItmTabBind() {
        return itmTabBind;
    }

    public void setAsgnTabBind(RichShowDetailItem asgnTabBind) {
        this.asgnTabBind = asgnTabBind;
    }

    public RichShowDetailItem getAsgnTabBind() {
        return asgnTabBind;
    }

    public void setFocTabBid(RichShowDetailItem focTabBid) {
        this.focTabBid = focTabBid;
    }

    public RichShowDetailItem getFocTabBid() {
        return focTabBid;
    }

    /* public void setSiteSurvayBind(RichShowDetailItem siteSurvayBind) {
        this.siteSurvayBind = siteSurvayBind;
    }

    public RichShowDetailItem getSiteSurvayBind() {
        return siteSurvayBind;
    } */

    public void setQuotTabBind(RichShowDetailItem quotTabBind) {
        this.quotTabBind = quotTabBind;
    }

    public RichShowDetailItem getQuotTabBind() {
        return quotTabBind;
    }

    public void setShdlTabBind(RichShowDetailItem shdlTabBind) {
        this.shdlTabBind = shdlTabBind;
    }

    public RichShowDetailItem getShdlTabBind() {
        return shdlTabBind;
    }

    public void setPmtTabBind(RichShowDetailItem pmtTabBind) {
        this.pmtTabBind = pmtTabBind;
    }

    public RichShowDetailItem getPmtTabBind() {
        return pmtTabBind;
    }

    public void setSurvayTabBind(RichShowDetailItem survayTabBind) {
        this.survayTabBind = survayTabBind;
    }

    public RichShowDetailItem getSurvayTabBind() {
        return survayTabBind;
    }

    public void ItmSaveNextAction(ActionEvent actionEvent) {
        // Add event code here...


    }

    public void FocSaveNextAction(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void AsgnSaveNextAction(ActionEvent actionEvent) {


        //OperationBinding geteo = getBindings().getOperationBinding("CreateInsert2");
        //geteo.execute();
    }

    public void survSaveNextAction(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void QuotSaveNextAction(ActionEvent actionEvent) {
        // Add event code here...
        // OperationBinding obind = getBindings().getOperationBinding("postchange");
        //obind.execute();

    }

    public void SiteSaveNextAction(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void AddAssgnMent(ActionEvent actionEvent) {
        if (getRqmtAreaBind().getValue() == null || getRqmtAreaBind().getValue() == "") {
            showFacesMessage("Requirement Area is Required", "E", false, "F", getRqmtAreaBind().getClientId());
        } else if (getEmpNmBind().getValue() == null || getEmpNmBind().getValue() == "") {
            showFacesMessage("Service Executive is Required", "E", false, "F", getEmpNmBind().getClientId());
        } else {
            OperationBinding obindDplct = getBindings().getOperationBinding("duplicateAsnChk");
            obindDplct.execute();
            String flag = null;

            if (obindDplct.getErrors().isEmpty()) {
                flag = obindDplct.getResult().toString();
            } else {
                adfLog.info("and dplct errors " + obindDplct.getErrors());
            }
            adfLog.info("dplct check value is " + flag);
            if ("Y".equalsIgnoreCase(flag)) {
                OperationBinding geteo = getBindings().getOperationBinding("createAsn");
                geteo.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(getRqmtAreaBind());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getEmpNmBind());
            } else if ("N".equalsIgnoreCase(flag)) {
                showFacesMessage("Duplicate Record Found", "E", false, "F", null);
            }
        }
    }

    public void addFocAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding geteo = getBindings().getOperationBinding("CreateInsert2");
        geteo.execute();
    }

    public void AddSurveyAction(ActionEvent actionEvent) {
        OperationBinding duplicate = getBindings().getOperationBinding("crtShedule");
        duplicate.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(shdlDateBinding);
        /*     OperationBinding geteo = getBindings().getOperationBinding("crtShedule");
      geteo.execute(); */

    }

    public void taxRuleVCL(ValueChangeEvent valueChangeEvent) {
        OperationBinding geteo = getBindings().getOperationBinding("addTaxRule");
        geteo.execute();
    }

    private void showPopup(RichPopup popup, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && popup != null) {
                String popupId = popup.getClientId(context);
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


    public void setTaxableItmAmtSpBind(RichInputText taxableItmAmtSpBind) {
        this.taxableItmAmtSpBind = taxableItmAmtSpBind;
    }

    public RichInputText getTaxableItmAmtSpBind() {
        return taxableItmAmtSpBind;
    }

    public void setTaxPopBind(RichPopup taxPopBind) {
        this.taxPopBind = taxPopBind;
    }

    public RichPopup getTaxPopBind() {
        return taxPopBind;
    }

    public void applytaxCheck(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding istaxApply = getBindings().getOperationBinding("isTaxApplicable");
        istaxApply.execute();
        String exmtdflag = null;
        if (istaxApply.getErrors().isEmpty()) {
            exmtdflag = istaxApply.getResult().toString();
        }
        adfLog.info("tax exempted flag value is " + exmtdflag);
        if ("Y".equalsIgnoreCase(exmtdflag)) {
            FacesMessage message2 =
                new FacesMessage("Tax is not Applicable on this item (either tax rule not define or this item is tax exempted)");
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        } else {
            // OperationBinding chkTaxOp = getBindings().getOperationBinding("checkTaxPresent");
            // chkTaxOp.execute();
            //  if("Y".equalsIgnoreCase(chkTaxOp.getResult().toString())){

            OperationBinding opr = getBindings().getOperationBinding("getTrRuleId");
            opr.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(totAmtBind);
            //  Integer reply = (Integer)opr.getResult();
            //  showPopup(itmTaxPopUp, true);

            // }else{
            //  OperationBinding crTaxOp = getBindings().getOperationBinding("CreateWithParams");
            //  crTaxOp.execute();
            //taxPopBind
        }
        // Add event code here...


    }

    public void addPmtAmtAction(ActionEvent actionEvent) {
        if (paymentDatebind.getValue() == null || paymentDatebind.getValue() == "") {

            // String msg2 = "Please specify Payment Date.";
            String msg2 = resolvEl("Payment date can't be empty").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message2);

        } else if (payAmtBind.getValue() == null || payAmtBind.getValue() == "") {

            // String msg2 = "Please specify Payment amount.";
            String msg2 = resolvEl("Payment Amount can't be Empty}").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message2);
        } else {

            OperationBinding dplctDt = getBindings().getOperationBinding("dplctDtchk");
            dplctDt.execute();
            String flag = null;

            if (dplctDt.getErrors().isEmpty())
                flag = dplctDt.getResult().toString();
            adfLog.info("dplct date return --------- " + flag);
            if ("Y".equalsIgnoreCase(flag)) {
                OperationBinding opr = getBindings().getOperationBinding("insrtPmtshdl");
                opr.execute();
            } else {
                showFacesMessage("Record Already Exists for this date", "E", false, "F", paymentDatebind.getClientId());
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(paymentDatebind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(payAmtBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pmtModeBind);
    }

    public void setPaymentDatebind(RichInputDate paymentDatebind) {
        this.paymentDatebind = paymentDatebind;
    }

    public RichInputDate getPaymentDatebind() {
        return paymentDatebind;
    }

    public void setPayAmtBind(RichInputText payAmtBind) {
        this.payAmtBind = payAmtBind;
    }

    public RichInputText getPayAmtBind() {
        return payAmtBind;
    }

    public void DisctypeValueChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue() != null) {

            OperationBinding opr = getBindings().getOperationBinding("resetDiscAmt");
            opr.execute();
        }

    }

    public void DiscAmtValueChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {

        }
    }

    public void reapplyTaxdialogListener(DialogEvent dialogEvent) {
        // Add event code here...
    }

    public void setItmNmBind(RichInputListOfValues itmNmBind) {
        this.itmNmBind = itmNmBind;
    }

    public RichInputListOfValues getItmNmBind() {
        return itmNmBind;
    }

    public void setPmtModeBind(RichSelectOneChoice pmtModeBind) {
        this.pmtModeBind = pmtModeBind;
    }

    public RichSelectOneChoice getPmtModeBind() {
        return pmtModeBind;
    }


    public void cstmrAprvlAction(ActionEvent actionEvent) {
        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        int userId = 0;
        OperationBinding check = getBindings().getOperationBinding("pendingCheck");
        check.getParamsMap().put("SlocId", sloc_Id);
        check.getParamsMap().put("CldId", cld_Id);
        check.getParamsMap().put("OrgId", org_Id);
        check.getParamsMap().put("DocNo", 23001);
        check.execute();

        if (check.getErrors().isEmpty()) {
            userId = Integer.parseInt(check.getResult().toString());
            // System.out.println("userId function "+userId);
        }
        if (usr_Id == userId || userId == -1) {
            // System.out.println("user_ id" +userId);
            /**temporary commit files */
            OperationBinding opr = getBindings().getOperationBinding("updateSvcStatus");
            opr.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(docStatBinding);
            RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
            try {
                OperationBinding fileObind = getBindings().getOperationBinding("generateFile");
                fileObind.execute();
                File pd = null;

                if (fileObind.getErrors().isEmpty()) {
                    pd = (File) fileObind.getResult();
                } else {
                    adfLog.info("error of files has been found is " + fileObind.getErrors());
                }
                adfLog.info("file value is " + pd);
                //getMailDetail
                OperationBinding mailDetail = getBindings().getOperationBinding("getMailDetail");
                mailDetail.execute();
                Map maildtl = new HashMap<>();

                if (mailDetail.getErrors().isEmpty()) {
                    maildtl = (Map) mailDetail.getResult();

                    ArrayList<String> are = (ArrayList) maildtl.get("To");
                    String ar[] = new String[are.size()];
                    ar = are.toArray(ar);
                    for (String r1 : ar)
                        adfLog.info("array size  " + are.size() + "array is " + ar + " value is " + r1);
                    MailSender sender =
                        new MailSender(maildtl.get("Server").toString(), maildtl.get("Security").toString(),
                                       Integer.parseInt(maildtl.get("Port").toString()), ar, null, null,
                                       "Customer Approve", "Record has been approved by customer", pd);

                    sender.send(maildtl.get("From").toString(), maildtl.get("Password").toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            /**above code is commented for release*/


            // To send Auto Mail
            //You can get congiration value from a procedure  PR_ALRT_GET_MAIL_CFG ---- "Domain Host", "Security", port,"sender Email Id", "sender Password"

            /*             OperationBinding op = this.getBindings().getOperationBinding("getToCustomer");
            op.execute();
            ArrayList<String> to = (ArrayList<String>) op.getResult();
            this.setTo(to);
            this.setSubject("Service Contract");
            this.setMessage("Service Contract has been approved");
             ServiceInterface bean = (ServiceInterface) resolveExpression("#{pageFlowScope.ServiceInterface}");
            //System.out.println("bean "+bean);
            bean.showPop(actionEvent);*/
            /*   ServiceInterface bean = (ServiceInterface) resolveExpression("#{pageFlowScope.ServiceInterface}");
          adfLog.info("bean "+bean);
          bean.showPop(actionEvent); */

        } else {
            String name = "Anonymous";
            OperationBinding getusrname = getBindings().getOperationBinding("getUsrNm");
            getusrname.getParamsMap().put("usrId", userId);
            getusrname.execute();
            if (getusrname.getErrors().size() == 0 && getusrname.getResult() != null)
                name = (String) getusrname.getResult();
            String msg = "This Service Contract is Pending at [" + name + "]. You can't Edit this Service Contract.";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }

    }


    public void setTo(ArrayList<String> to) {
        this.to = to;
    }

    public ArrayList<String> getTo() {
        return to;
    }

    public void setCc(ArrayList<String> cc) {
        this.cc = cc;
    }

    public ArrayList<String> getCc() {
        return cc;
    }

    public void setBcc(ArrayList<String> bcc) {
        this.bcc = bcc;
    }

    public ArrayList<String> getBcc() {
        return bcc;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    private ArrayList<String> to = new ArrayList<String>();
    private ArrayList<String> cc = new ArrayList<String>();
    private ArrayList<String> bcc = new ArrayList<String>();
    private String subject = null;
    private String message = null;


    public void ApprvdbyCstmrAction(ActionEvent actionEvent) {
        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        int userId = 0;
        OperationBinding check = getBindings().getOperationBinding("pendingCheck");
        check.getParamsMap().put("SlocId", sloc_Id);
        check.getParamsMap().put("CldId", cld_Id);
        check.getParamsMap().put("OrgId", org_Id);
        check.getParamsMap().put("DocNo", 23001);
        check.execute();

        if (check.getErrors().isEmpty()) {
            userId = Integer.parseInt(check.getResult().toString());
            System.out.println("userId function " + userId);
        }
        if (usr_Id == userId || userId == -1) {
            System.out.println("user_ id" + userId);
            OperationBinding opr = getBindings().getOperationBinding("scNoValuegen");
            opr.execute();
            //  docStatBinding
            RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");


            // To send Auto Mail
            // You can get congiration value from a procedure  PR_ALRT_GET_MAIL_CFG ---- "Domain Host", "Security", port,"sender Email Id", "sender Password"
            //           MailSender sender=new MailSender("Domain Host", "Security", port,new String[]
            //                                                   {"alok.kumar@essindia.com"},null,null,"Customer Approve","Record has been approved by customer",null);
            //
            //           sender.send("sender Email Id", "sender Password");

            AdfFacesContext.getCurrentInstance().addPartialTarget(docStatBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(scNoBind);

            //RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "C");
        } else {
            String name = "Anonymous";
            OperationBinding getusrname = getBindings().getOperationBinding("getUsrNm");
            getusrname.getParamsMap().put("usrId", userId);
            getusrname.execute();
            if (getusrname.getErrors().size() == 0 && getusrname.getResult() != null)
                name = (String) getusrname.getResult();
            String msg = "This Service Contract is Pending at [" + name + "]. You can't Edit this Service Contract.";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }

    }

    public void pmtschdlDeleteAction(ActionEvent actionEvent) {
        // Add event code here...
        //  ADFBeanUtils.findOperation("Delete").execute();
        //   /** becoz of adf util use*/
        OperationBinding opr = getBindings().getOperationBinding("Delete");
        opr.execute();
    }

    public void setTotAmtBind(RichInputText totAmtBind) {
        this.totAmtBind = totAmtBind;
    }

    public RichInputText getTotAmtBind() {
        return totAmtBind;
    }

    public void deleteAsnmntAction(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("Delete1").execute();
        /**becoz of adf util in used*/
        // OperationBinding opr = getBindings().getOperationBinding("Delete1");
        //opr.execute();
    }

    public void setRqmtAreaBind(RichSelectOneChoice rqmtAreaBind) {
        this.rqmtAreaBind = rqmtAreaBind;
    }

    public RichSelectOneChoice getRqmtAreaBind() {
        return rqmtAreaBind;
    }

    public void setEmpNmBind(RichInputListOfValues empNmBind) {
        this.empNmBind = empNmBind;
    }

    public RichInputListOfValues getEmpNmBind() {
        return empNmBind;
    }

    public void renewalActionListner(ActionEvent actionEvent) {

        adfLog.info("customer name is " + eoNmBind.getValue());
        if (eoNmBind.getValue() != null) {

            OperationBinding opr = getBindings().getOperationBinding("insertFrRenewal");
            opr.execute();
            adfLog.info("insert for renewal errors  " + opr.getErrors());
            if ("Y".equalsIgnoreCase(opr.getResult().toString())) {
                totalPnlGrpBinding.setVisible(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(totalPnlGrpBinding);
            }
        } else {
            showFacesMessage("Customer is Required", "E", false, "F", eoNmBind.getClientId());

        }

    }

    public void setTotalPnlGrpBinding(RichPanelGroupLayout totalPnlGrpBinding) {
        this.totalPnlGrpBinding = totalPnlGrpBinding;
    }

    public RichPanelGroupLayout getTotalPnlGrpBinding() {
        return totalPnlGrpBinding;
    }

    public void autoSvcSchedule(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding opr = getBindings().getOperationBinding("autoSrvcSchedule");
        opr.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(shdlDateBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(svcSchdlTabBind); //svcSchdlTabBind
    }

    public void setShdlDateBinding(RichInputDate shdlDateBinding) {
        this.shdlDateBinding = shdlDateBinding;
    }

    public RichInputDate getShdlDateBinding() {
        return shdlDateBinding;
    }

    public void ctcinitValuchangeListner(ValueChangeEvent valueChangeEvent) {
        //    totalPnlGrpBinding
        adfLog.info("value change event value is" + valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue() != null) {
            Integer i = (Integer) valueChangeEvent.getNewValue();
            if (i.compareTo(20) == 0 || i.compareTo(92) == 0 || i.compareTo(19) == 0) {
                totalPnlGrpBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(totalPnlGrpBinding);
            } else if (i.compareTo(18) == 0) {
                totalPnlGrpBinding.setVisible(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(totalPnlGrpBinding);
            }
        }
    }

    public void autoPaymentActionListner(ActionEvent actionEvent) {
        //paymentDatebind

        if (paymentDatebind.getValue() != null && paymentDatebind.getValue().toString().length() > 0) {
            OperationBinding opr = getBindings().getOperationBinding("autoPaymentschedule");
            opr.execute();

            AdfFacesContext.getCurrentInstance().addPartialTarget(pmtTablBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(paymentDatebind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(payAmtBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pmtModeBind);


        }
    }

    public void ItmQtyvalidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number value = (Number) object;

            if (value.compareTo(new Number(0)) == -1) {
                showFacesMessage("Value must be Greater than 0", "E", false, "V", null);
            }
        }

    }

    public void ItmTabPricevalidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number value = (Number) object;

            if (value.compareTo(new Number(0)) == -1) {
                showFacesMessage("Value must be Greater than 0", "E", false, "V", null);
            }
        }

    }

    public void SitesvyQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number value = (Number) object;

            if (value.compareTo(new Number(0)) == -1) {
                showFacesMessage("Value must be Greater than 0", "E", false, "V", null);
            }
        }

    }

    public void QuotnQtyValid(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number value = (Number) object;

            if (value.compareTo(new Number(0)) == -1) {
                showFacesMessage("Value must be Greater than 0", "E", false, "V", null);
            }
        }

    }

    public void QuotQtyValid(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number value = (Number) object;

            if (value.compareTo(new Number(0)) == -1) {
                showFacesMessage("Value must be Greater than 0", "E", false, "V", null);
            }
        }

    }

    public void discAmtValid(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number value = (Number) object;

            if (value.compareTo(new Number(0)) == -1) {
                showFacesMessage("Value must be Greater than 0", "E", false, "V", null);
            }

            OperationBinding opr = getBindings().getOperationBinding("discAmtValidator");
            opr.getParamsMap().put("discAmt", value);
            opr.execute();

            String temp = null;
            if (opr.getErrors().isEmpty()) {
                temp = opr.getResult().toString();
            } else {
                adfLog.info("disc amount errors is " + opr.getErrors());
            }

            if (temp.equalsIgnoreCase("N")) {
                showFacesMessage("Discount Amount must be less than Total Amount", "E", false, "V", null);
            } else if (temp.equalsIgnoreCase("P")) {
                showFacesMessage("Discount Amount must be less than 100", "E", false, "V", null);

            }
            adfLog.info("discAmount return value is " + temp);

        }


    }

    public void focQtyvalidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number value = (Number) object;

            if (value.compareTo(new Number(0)) == -1) {
                showFacesMessage("Value must be Greater than 0", "E", false, "V", null);
            }
        }

    }

    public void pmtAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number value = (Number) object;

            if (value.compareTo(new Number(0)) == -1) {
                showFacesMessage("Value must be Greater than 0", "E", false, "V", null);
            }
        }

    }

    public void itemFocValuechangeListner(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(focItmTblbind);
    }

    public void itemQtyValuechange(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(focItmTblbind);
    }

    public void setFocItmTblbind(RichTable focItmTblbind) {
        this.focItmTblbind = focItmTblbind;
    }

    public RichTable getFocItmTblbind() {
        return focItmTblbind;
    }

    public void setItmQtyBind(RichInputText itmQtyBind) {
        this.itmQtyBind = itmQtyBind;
    }

    public RichInputText getItmQtyBind() {
        return itmQtyBind;
    }

    public void setItmPriceBind(RichInputText itmPriceBind) {
        this.itmPriceBind = itmPriceBind;
    }

    public RichInputText getItmPriceBind() {
        return itmPriceBind;
    }

    public void setSvcSchdlTabBind(RichTable svcSchdlTabBind) {
        this.svcSchdlTabBind = svcSchdlTabBind;
    }

    public RichTable getSvcSchdlTabBind() {
        return svcSchdlTabBind;
    }

    public void setDocStatBinding(RichSelectOneChoice docStatBinding) {
        this.docStatBinding = docStatBinding;
    }

    public RichSelectOneChoice getDocStatBinding() {
        return docStatBinding;
    }

    public void pmtDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        adfLog.info("object value is " + object + "prdfrm value is " + prdFrmDateBind.getValue());
        if (object != null && prdFrmDateBind.getValue() != null && prdFrmDateBind.getValue().toString().length() > 0) {
            Timestamp currDt = (Timestamp) object;

            Timestamp Todt = (Timestamp) prdFrmDateBind.getValue();
            String dateValid = dateValidator(Todt, currDt);
            adfLog.info("date vlidator is " + dateValid);
            java.sql.Date viewDt = null;
            try {
                viewDt = Todt.dateValue();
            } catch (Exception e) {
                adfLog.info("exception found in conversion" + e);
            }
            if ("Y".equalsIgnoreCase(dateValid)) {

            } else if ("N".equalsIgnoreCase(dateValid)) {
                showFacesMessage("Date must be on or After " + viewDt, "E", false, "V", null);
            }

        }


    }

    public void crtDateBind(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...




    }

    public String dateValidator(Timestamp curr, Timestamp Todt) {
        java.sql.Date strtDt = null;
        java.sql.Date endDt = null;
        try {
            strtDt = curr.dateValue();
            endDt = Todt.dateValue();

        } catch (SQLException e) {
            System.out.println("Error in cast date" + e);
            return "N";
        }

        if (strtDt.compareTo(endDt) > 0) {
            if (strtDt.toString().equals(endDt.toString())) {
                //ok
            } else {
                return "N";
            }
        }


        return null;
    }

    public void setPrdFrmDateBind(RichInputDate prdFrmDateBind) {
        this.prdFrmDateBind = prdFrmDateBind;
    }

    public RichInputDate getPrdFrmDateBind() {
        return prdFrmDateBind;
    }

    public void serviceDateValid(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && prdFrmDateBind.getValue() != null && prdFrmDateBind.getValue().toString().length() > 0) {
            Timestamp currDt = (Timestamp) object;
            Timestamp prdfrm = (Timestamp) prdFrmDateBind.getValue();

            String chkDt = dateValidator(prdfrm, currDt);
            java.sql.Date viewDt = null;
            try {
                viewDt = prdfrm.dateValue();
            } catch (Exception e) {
                adfLog.info("exception found in conversion" + e);
            }
            if ("Y".equalsIgnoreCase(chkDt)) {

            } else if ("N".equalsIgnoreCase(chkDt)) {
                showFacesMessage("Date must be on or After " + viewDt, "E", false, "V", null);
            }

            if (prdToDtBind.getValue() != null && prdToDtBind.getValue().toString().length() > 0) {
                Timestamp prdDt = (Timestamp) prdToDtBind.getValue();

                String datevalid = dateValidator(currDt, prdDt);
                java.sql.Date toDtview = null;
                try {
                    toDtview = prdDt.dateValue();
                } catch (Exception e) {
                    adfLog.info("exception found in conversion" + e);
                }
                if ("Y".equalsIgnoreCase(datevalid)) {

                } else if ("N".equalsIgnoreCase(datevalid)) {
                    showFacesMessage("Date must be on or Before " + toDtview, "E", false, "V", null);
                }

            }


        }

    }

    public void setPrdToDtBind(RichInputDate prdToDtBind) {
        this.prdToDtBind = prdToDtBind;
    }

    public RichInputDate getPrdToDtBind() {
        return prdToDtBind;
    }

    public void prdToValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && prdFrmDateBind.getValue() != null && prdFrmDateBind.getValue().toString().length() > 0) {
            Timestamp currDt = (Timestamp) object;

            if (prdToDtBind.getValue() != null && prdToDtBind.getValue().toString().length() > 0) {
                Timestamp prdDt = (Timestamp) prdToDtBind.getValue();

                String datevalid = dateValidator(currDt, prdDt);
                java.sql.Date toDtview = null;
                try {
                    toDtview = prdDt.dateValue();
                } catch (Exception e) {
                    adfLog.info("exception found in conversion" + e);
                }
                if ("Y".equalsIgnoreCase(datevalid)) {

                } else if ("N".equalsIgnoreCase(datevalid)) {
                    showFacesMessage("Date must be on or Before " + toDtview, "E", false, "V", null);
                }

            }
        }

    }

    public void prdFrm1Validator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null && prdFrmDateBind.getValue() != null && prdFrmDateBind.getValue().toString().length() > 0) {
            Timestamp currDt = (Timestamp) object;

            Timestamp Todt = (Timestamp) prdFrmDateBind.getValue();
            String dateValid = dateValidator(Todt, currDt);
            adfLog.info("date vlidator is " + dateValid);
            java.sql.Date viewDt = null;
            try {
                viewDt = Todt.dateValue();
            } catch (Exception e) {
                adfLog.info("exception found in conversion" + e);
            }
            if ("Y".equalsIgnoreCase(dateValid)) {

            } else if ("N".equalsIgnoreCase(dateValid)) {
                showFacesMessage("Date must be on or After " + viewDt, "E", false, "V", null);
            }

        }
    }

    public void setScNoBind(RichInputText scNoBind) {
        this.scNoBind = scNoBind;
    }

    public RichInputText getScNoBind() {
        return scNoBind;
    }

    public String addAttachAction(ActionEvent actionEvent) throws Exception {
        // Add event code here...
        String path = "";
        String extn = "";
        List<UploadedFile> files = this.getUploadedFile();
        InputStream in = null;
        FileOutputStream out = null;
        if (files != null) {
            for (int i = 0; i < files.size(); i++) {
                try {


                    //get file extension
                    extn = files.get(i).getFilename().substring(files.get(i).getFilename().lastIndexOf("."));
                    adfLog.info("Content Type:" + files.get(i).getContentType());
                    adfLog.info("entension--->>" + extn);
                    //Add files to the Table
                    OperationBinding op = getBindings().getOperationBinding("createAttchmntRow");
                    //  op = ADFUtils.findOperation("createAttchmntRow");
                    op.getParamsMap().put("fileNm", files.get(i).getFilename());
                    op.getParamsMap().put("contentTyp", files.get(i).getContentType());
                    op.getParamsMap().put("extn", extn);
                    op.execute();

                    if (op.getResult() != null) {
                        path = op.getResult().toString();
                    }
                    System.out.println("extn " + extn);
                    //write files in the file system.

                    in = files.get(i).getInputStream();
                    System.out.println(files.get(i).getInputStream());

                    System.out.println("write file at " + path + extn);
                    out = new FileOutputStream(path + extn);
                    byte[] buffer = new byte[8192];
                    int bytesRead = 0;

                    while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                        out.write(buffer, 0, bytesRead);
                        // out.flush();
                    }
                    out.flush();

                    //call commit after checking all validations
                    OperationBinding obCommit = getBindings().getOperationBinding("Commit");
                    obCommit.execute();
                    ///   ADFUtils.findOperation("Commit").execute();
                } catch (IOException ioe) {
                    // TODO: Add catch code
                    ioe.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                }
            }
        }
        //remove the files to prepare for new uploads
        setUploadedFile(null);
        OperationBinding obExecute = getBindings().getOperationBinding("Execute");
        obExecute.execute();
        //ADFUtils.findOperation("Execute5").execute();
        return "null";
    }

    public void setFileBindName(RichInputText fileBindName) {
        this.fileBindName = fileBindName;
    }

    public RichInputText getFileBindName() {
        return fileBindName;
    }

    public void DownloadfileAction(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        RichInputText bind = this.getFileBindName();
        if (bind != null) {
            System.out.println("come ----- 1");
            String path = bind.getValue().toString();

            System.out.println("path is:  " + path);
            File file = new File(path);
            FileInputStream fis = null;
            byte[] b;
            try {
                fis = new FileInputStream(file);
                int n;
                while ((n = fis.available()) > 0) {
                    b = new byte[n];
                    int result = fis.read(b);
                    outputStream.write(b, 0, b.length);
                    if (result == -1)
                        break;
                }
                outputStream.flush();
            } catch (IOException e) {
                //    JSFUtils.addFacesErrorMessage("Problem in downloading a file");
                e.printStackTrace();
            } finally {
                try {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void deleteAction(ActionEvent actionEvent) {
        // Add event code here...
        String path = null;

        Object pathObj = actionEvent.getComponent().getAttributes().get("pathWithName");
        if (pathObj != null) {
            path = actionEvent.getComponent().getAttributes().get("pathWithName").toString();

            System.out.println("File path in AMimpl" + path);
            File f = new File(path);
            try {
                boolean success = f.delete();
                if (success) {
                    System.out.println("File Deleted");
                    OperationBinding obDelete = getBindings().getOperationBinding("Delete2");
                    obDelete.execute();
                    OperationBinding obCommit = getBindings().getOperationBinding("Commit");
                    obCommit.execute();
                    //                    OperationBinding obExecute = getBindings().getOperationBinding("Execute");
                    //                    obExecute.execute();
                    /*  ADFUtils.findOperation("Delete").execute();
                    ADFUtils.findOperation("Commit").execute();
                    ADFUtils.findOperation("Execute5").execute(); */
                }
            } catch (Exception x) {
                System.err.format("%s: no such" + " file or directory%n", path);
            }
        }
    }

    public void ownValueChangeListen(ValueChangeEvent valueChangeEvent) {
        adfLog.info("own supply change value change " + valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue() != null) {
            Boolean own = (Boolean) valueChangeEvent.getNewValue();

            if (own) {
                prtyNmBind.setValue(null);
                prtyNmBind.setDisabled(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(prtyNmBind);
            } else {
                prtyNmBind.setDisabled(false);
            }
        }
    }

    public void setPrtyNmBind(RichInputText prtyNmBind) {
        this.prtyNmBind = prtyNmBind;
    }

    public RichInputText getPrtyNmBind() {
        return prtyNmBind;
    }

    public void instldtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && prdFrmDateBind.getValue() != null && prdFrmDateBind.getValue().toString().length() > 0) {
            Timestamp currDt = (Timestamp) object;

            Timestamp Todt = (Timestamp) prdFrmDateBind.getValue();
            String dateValid = dateValidator(currDt, Todt);
            adfLog.info("date vlidator is " + dateValid);
            java.sql.Date viewDt = null;
            try {
                viewDt = Todt.dateValue();
            } catch (Exception e) {
                adfLog.info("exception found in conversion" + e);
            }
            if ("Y".equalsIgnoreCase(dateValid)) {

            } else if ("N".equalsIgnoreCase(dateValid)) {
                showFacesMessage("Date must be on or before " + viewDt, "E", false, "V", null);
            }

        }


    }

    public void deleteScheduleAction(ActionEvent actionEvent) {
        OperationBinding obind = getBindings().getOperationBinding("Delete3");
        obind.execute();
    }

    public String prcdyesWf() {
        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        int userId = 0;
        OperationBinding check = getBindings().getOperationBinding("pendingCheck");
        check.getParamsMap().put("SlocId", sloc_Id);
        check.getParamsMap().put("CldId", cld_Id);
        check.getParamsMap().put("OrgId", org_Id);
        check.getParamsMap().put("DocNo", 23001);
        check.execute();

        if (check.getErrors().isEmpty()) {
            userId = Integer.parseInt(check.getResult().toString());
            System.out.println("userId function " + userId);
        }
        if (usr_Id == userId || userId == -1) {
            OperationBinding resettax = getBindings().getOperationBinding("taxablecheck");
            resettax.execute();
            String istxble = null;
            if (resettax.getErrors().isEmpty()) {
                istxble = resettax.getResult().toString();
                adfLog.info("txble check result " + istxble);
            }
            if ("Y".equalsIgnoreCase(istxble)) {
                OperationBinding chkAmt = getBindings().getOperationBinding("chkPmtAmt");
                chkAmt.execute();
                String chkPmt = null;
                if (chkAmt.getResult() != null)
                    chkPmt = chkAmt.getResult().toString();
                if ("Y".equalsIgnoreCase(chkPmt)) {

                    OperationBinding geteo = getBindings().getOperationBinding("generateScNo");
                    geteo.execute();
                    String action = "I";
                    String remark = "A";
                    String WfNum = null;
                    Integer level = 0;
                    Integer res = -1;
                    Integer pending = 0;
                    OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                    WfnoOp.getParamsMap().put("SlocId", sloc_Id);
                    WfnoOp.getParamsMap().put("CldId", cld_Id);
                    WfnoOp.getParamsMap().put("OrgId", org_Id);
                    WfnoOp.getParamsMap().put("DocNo", 23001);
                    WfnoOp.execute();
                    if (WfnoOp.getResult() != null) {
                        if (WfnoOp.getResult() != null)
                            WfNum = WfnoOp.getResult().toString();
                        System.out.println("WfnoOp : " + WfNum);
                    }

                    if (WfNum != null && !"0".equalsIgnoreCase(WfNum)) {
                        //get user level
                        OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                        usrLevelOp.getParamsMap().put("SlocId", sloc_Id);
                        usrLevelOp.getParamsMap().put("CldId", cld_Id);
                        usrLevelOp.getParamsMap().put("OrgId", org_Id);
                        usrLevelOp.getParamsMap().put("UsrId", usr_Id);
                        usrLevelOp.getParamsMap().put("WfNo", WfNum);
                        usrLevelOp.getParamsMap().put("DocNo", 23001);
                        usrLevelOp.execute();
                        level = -1;
                        if (usrLevelOp.getResult() != null) {
                            level = Integer.parseInt(usrLevelOp.getResult().toString());
                            System.out.println("user level " + level);
                        }

                        if (level >= 0) {
                            //insert into txn
                            OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                            insTxnOp.getParamsMap().put("SlocId", sloc_Id);
                            insTxnOp.getParamsMap().put("CldId", cld_Id);
                            insTxnOp.getParamsMap().put("OrgId", org_Id);
                            insTxnOp.getParamsMap().put("DocNo", 23001);
                            insTxnOp.getParamsMap().put("WfNo", WfNum);
                            insTxnOp.getParamsMap().put("usr_idFrm", usr_Id);
                            insTxnOp.getParamsMap().put("usr_idTo", usr_Id);
                            insTxnOp.getParamsMap().put("levelFrm", level);
                            insTxnOp.getParamsMap().put("levelTo", level);
                            insTxnOp.getParamsMap().put("action", action);
                            insTxnOp.getParamsMap().put("remark", remark);
                            insTxnOp.getParamsMap().put("amount", 0);
                            insTxnOp.execute();

                            //     adflog.info("now the errors in txn is"+insTxnOp.getErrors());
                            if (insTxnOp.getResult() != null) {
                                res = Integer.parseInt(insTxnOp.getResult().toString());
                                System.out.println("ins ito txn" + res);
                            }


                            //Check Pending
                            OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
                            chkUsr.getParamsMap().put("SlocId", sloc_Id);
                            chkUsr.getParamsMap().put("CldId", cld_Id);
                            chkUsr.getParamsMap().put("OrgId", org_Id);
                            chkUsr.getParamsMap().put("DocNo", 23001);
                            chkUsr.execute();

                            if (chkUsr.getResult() != null) {
                                pending = Integer.parseInt(chkUsr.getResult().toString());
                                System.out.println("pending check" + pending);
                            }
                            if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(usr_Id) == 0) {
                                OperationBinding saveOp = getBindings().getOperationBinding("Commit");
                                saveOp.execute();
                                RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
                                notWorkingPopup.hide();
                                //showFacesMessage("MRN No "+ids+" Save Successfully", "I", false, "F", null);
                                //no pending
                                return "towf";
                            }
                            /*  else {//pending
                                                         return null;
                                                         } */
                        } else {
                            showFacesMessage("Something went wrong(User Level in Workflow).Please Contact to ESS!", "E",
                                             false, "F", null);

                            return null;
                        }
                    } else {
                        showFacesMessage("Workflow not Created for Service Contract", "E", false, "F", null);

                        return null;
                    }

                } else {
                    String msg =
                        "Amount Mismatch.(Total Payment Schedule Amount Should be Equal to Total Service Contract Amount)";

                    showFacesMessage(msg, "I", false, "F", null);
                    return null;
                }
            }
        } else {
            String name = "Anonymous";
            OperationBinding getusrname = getBindings().getOperationBinding("getUsrNm");
            getusrname.getParamsMap().put("usrId", userId);
            getusrname.execute();
            if (getusrname.getErrors().size() == 0 && getusrname.getResult() != null)
                name = (String) getusrname.getResult();
            String msg = "This Service Contract is Pending at [" + name + "]. You can't forward this Service Contract.";


            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return null;
        }
        return null;


    }

    public void prcdNoActionListener(ActionEvent actionEvent) {
        notWorkingPopup.hide();

        // Add event code here...
    }

    public void setNotWorkingPopup(RichPopup notWorkingPopup) {
        this.notWorkingPopup = notWorkingPopup;
    }

    public RichPopup getNotWorkingPopup() {
        return notWorkingPopup;
    }

    public String getNotWorkingItem() {
        return notWorkingItem;
    }

    public void focItmNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding obind = getBindings().getOperationBinding("focItmNameValid");
            obind.getParamsMap().put("name", object);
            obind.execute();
            String flag = null;
            if (obind.getErrors().isEmpty()) {
                flag = obind.getResult().toString();
                adfLog.info("item id return flag is " + flag);
                if ("N".equalsIgnoreCase(flag)) {
                    showFacesMessage("Duplicate item name found", "E", false, "V", null);

                } else {

                }
            }
            adfLog.info(" errors found in this type " + obind.getErrors());
        }
    }

    public void asgnAlexecutive(ActionEvent actionEvent) {
        OperationBinding obind = getBindings().getOperationBinding("asgnmentToAllExec");
        obind.execute();
    }

    public void instlDtAll(ActionEvent actionEvent) {
        OperationBinding obind = getBindings().getOperationBinding("installDateToAll");
        obind.execute();
    }

    public void addCstmrLoc(ActionEvent actionEvent) {
        if (locIdBind.getValue() != null && locIdBind.getValue().toString().length() > 0) {
        } else {
            showFacesMessage("Address is Required ", "E", false, "F", locIdBind.getClientId());
            return;
        }
        if (cstmrDtBind.getValue() != null && cstmrDtBind.getValue().toString().length() > 0) {
        } else {
            showFacesMessage("Start date is Required ", "E", false, "F", cstmrDtBind.getClientId());
            return;
        }
        if (cstmrEndBind.getValue() != null && cstmrEndBind.getValue().toString().length() > 0) {
        } else {
            showFacesMessage("End date is Required ", "E", false, "F", cstmrEndBind.getClientId());
            return;
        }


        OperationBinding dplct = ADFBeanUtils.getOperationBinding("dplctLocation");
        dplct.execute();
        String flag = null;
        if (dplct.getErrors().isEmpty()) {
            flag = dplct.getResult().toString();
        }
        if ("Y".equalsIgnoreCase(flag)) {
            OperationBinding obind = ADFBeanUtils.getOperationBinding("insrtLocation");
            obind.execute();
            AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
            SrchScBean oc = new SrchScBean();
            oc.resetValueInputItems(fctx, locGrpbind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cstmrEndBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cstmrDtBind);

        } else if ("N".equalsIgnoreCase(flag)) {
            showFacesMessage("Record Already Exists", "W", false, "F", null);
            //showFacesMessage("Workflow not Created for Service Contract", "E", false, "F", null);
            return;
        }
        //cstmrDtBind
    }

    public void setLocIdBind(RichInputListOfValues locIdBind) {
        this.locIdBind = locIdBind;
    }

    public RichInputListOfValues getLocIdBind() {
        return locIdBind;
    }

    public void setLocGrpbind(RichPanelGroupLayout locGrpbind) {
        this.locGrpbind = locGrpbind;
    }

    public RichPanelGroupLayout getLocGrpbind() {
        return locGrpbind;
    }

    public void insrtItemLocationAction(ActionEvent actionEvent) {
        if (cstmrAddsId.getValue() != null && cstmrAddsId.getValue().toString().length() > 0) {

        } else {
            showFacesMessage("Customer Location is Required ", "E", false, "F", cstmrAddsId.getClientId());
            return;
        }
        if (getItmAddsIdBind().getValue() != null && getItmAddsIdBind().getValue().toString().length() > 0) {
        } else {
            showFacesMessage("Item Location is Required ", "E", false, "F", getItmAddsIdBind().getClientId());
            return;
        }
        if (getItmStrtDtBind().getValue() != null && getItmStrtDtBind().getValue().toString().length() > 0) {
        } else {
            showFacesMessage("Start Date is Required ", "E", false, "F", getItmStrtDtBind().getClientId());
            return;
        }
        if (getItmEndDtBind().getValue() != null && getItmEndDtBind().getValue().toString().length() > 0) {
        } else {
            showFacesMessage("End Date is Required ", "E", false, "F", getItmEndDtBind().getClientId());
            return;
        }

        OperationBinding binding = ADFBeanUtils.findOperation("duplicateItmAddsCheck");
        binding.execute();
        String flag = null;
        if (binding.getErrors().isEmpty()) {
            flag = binding.getResult().toString();
        }
        adfLog.info("duplicate item check " + flag + "total errors are " + binding.getErrors());
        if ("Y".equalsIgnoreCase(flag)) {
            ADFBeanUtils.findOperation("insrtItemLocation").execute();
            //    AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
            //  SrchScBean oc = new SrchScBean();
            //oc.resetValueInputItems(fctx, itmLocPgrpBind);

            AdfFacesContext.getCurrentInstance().addPartialTarget(getItmStrtDtBind());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getItmEndDtBind());

        } else if ("N".equalsIgnoreCase(flag)) {
            showFacesMessage("Record Already Exists", "W", false, "F", null);
            return;
            //showFacesMessage("Workflow not Created for Service Contract", "E", false, "F", null);
        }
    }

    public void dltCstmrAction(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("Delete4").execute();
    }

    public void dltItmlocationAction(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("Delete5").execute();
    }

    public void setCstmrAddsId(RichInputListOfValues cstmrAddsId) {
        this.cstmrAddsId = cstmrAddsId;
    }

    public RichInputListOfValues getCstmrAddsId() {
        return cstmrAddsId;
    }

    public void setItmAddsIdBind(RichInputListOfValues itmAddsIdBind) {
        this.itmAddsIdBind = itmAddsIdBind;
    }

    public RichInputListOfValues getItmAddsIdBind() {
        return itmAddsIdBind;
    }

    public void setItmLocPgrpBind(RichPanelGroupLayout itmLocPgrpBind) {
        this.itmLocPgrpBind = itmLocPgrpBind;
    }

    public RichPanelGroupLayout getItmLocPgrpBind() {
        return itmLocPgrpBind;
    }

    public void deleteFocItem(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("Delete6").execute();

    }

    public void setCstmrDtBind(RichInputDate cstmrDtBind) {
        this.cstmrDtBind = cstmrDtBind;
    }

    public RichInputDate getCstmrDtBind() {
        return cstmrDtBind;
    }

    public void setCstmrEndBind(RichInputDate cstmrEndBind) {
        this.cstmrEndBind = cstmrEndBind;
    }

    public RichInputDate getCstmrEndBind() {
        return cstmrEndBind;
    }

    public void setItmStrtDtBind(RichInputDate itmStrtDtBind) {
        this.itmStrtDtBind = itmStrtDtBind;
    }

    public RichInputDate getItmStrtDtBind() {
        return itmStrtDtBind;
    }

    public void setItmEndDtBind(RichInputDate itmEndDtBind) {
        this.itmEndDtBind = itmEndDtBind;
    }

    public RichInputDate getItmEndDtBind() {
        return itmEndDtBind;
    }


    public Object resolveExpression(String expression) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, expression, Object.class);
        return valueExp.getValue(elContext);
    }

    public void deleteItmActionListner(ActionEvent actionEvent) {

        /**this cascading delete option  not delete asgnment vo record
         * because of cascading delete create
         * opration not perform on different instance of assignment vo
         * */
        ADFBeanUtils.findOperation("deleteRecord").execute(); //asgn delete record
        ADFBeanUtils.findOperation("cscdTrDelete").execute();
        ADFBeanUtils.findOperation("Execute2").execute();
        ADFBeanUtils.findOperation("CscdDelete").execute();
        ADFBeanUtils.findOperation("Execute1").execute();
        itmTabBind.setDisclosed(true);

        ADFBeanUtils.findOperation("deleteAllPmtSchedule").execute();

    }

    public void setEoNmBind(RichInputListOfValues eoNmBind) {
        this.eoNmBind = eoNmBind;
    }

    public RichInputListOfValues getEoNmBind() {
        return eoNmBind;
    }

    public void itmQtyValueChange(ValueChangeEvent valueChangeEvent) {
        //ADFBeanUtils.findOperation("setpendingQty").execute();
        OperationBinding obind = getBindings().getOperationBinding("setpendingQty");
        obind.getParamsMap().put("qty", valueChangeEvent.getNewValue());
        obind.execute();
    }

    public void discountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number n = (Number) object;
            Number price = new Number(0);
            Number qty = new Number(0);
            if (itmPriceBind.getValue() != null)
                price = (Number) itmPriceBind.getValue();

            if (itmQtyBind.getValue() != null)
                qty = (Number) itmQtyBind.getValue();

            Number totol = price.multiply(qty);
            Number zero = new Number(0);
            Number hundrd = new Number(100);
            if (discTypeBind.getValue().equals("A") && (n.compareTo(totol) == 1 || n.compareTo(totol) == 0)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.262']}").toString() + "  " +
                                                              totol, null));
            } else if (n.compareTo(zero) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.305']}").toString(), null));
            }
            if (discTypeBind.getValue().equals("P") && (n.compareTo(hundrd) == 1 || n.compareTo(hundrd) == 0)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.303']}").toString(), null));
            }

            if (totol.compareTo(zero) == 0 && n.compareTo(totol) == 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.262']}").toString() + "  " +
                                                              totol, null));
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


    public void setDiscTypeBind(RichSelectOneRadio discTypeBind) {
        this.discTypeBind = discTypeBind;
    }

    public RichSelectOneRadio getDiscTypeBind() {
        return discTypeBind;
    }

    public void setPmtTablBind(RichTable pmtTablBind) {
        this.pmtTablBind = pmtTablBind;
    }

    public RichTable getPmtTablBind() {
        return pmtTablBind;
    }


    public void selectAllAddsValueChangeListner(ValueChangeEvent valueChangeEvent) {
        adfLog.info("value Change event for select all value is " + valueChangeEvent.getNewValue());
        /*  if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0) {
            Boolean value = (Boolean)valueChangeEvent.getNewValue();
            if (value) {
                OperationBinding obind = getBindings().getOperationBinding("selectAllAdds");
                obind.getParamsMap().put("check", "Y");
                obind.execute();
            }else{
                OperationBinding obind = getBindings().getOperationBinding("selectAllAdds");
                obind.getParamsMap().put("check", "N");
                obind.execute();
            }
        } */

    }

    public void showCustomerAdds(ActionEvent actionEvent) {
        OperationBinding obind = getBindings().getOperationBinding("applyAddsFileter");
        obind.execute();
        showPopup(lovAddsPopupBind, true);
    }

    public void setLovAddsPopupBind(RichPopup lovAddsPopupBind) {
        this.lovAddsPopupBind = lovAddsPopupBind;
    }

    public RichPopup getLovAddsPopupBind() {
        return lovAddsPopupBind;
    }

    public void AddDialogListener(DialogEvent dialogEvent) {
        adfLog.info("insert in to service adds");
        if (dialogEvent.getOutcome().name().equals("ok")) {
            adfLog.info("insert in to service adds now ok receive");
            OperationBinding obind = getBindings().getOperationBinding("insertIntoSvcLoc");
            obind.execute();
            //  OperationBinding obind1 = getBindings().getOperationBinding("eoAddsExecute");
            //obind1.execute();
            //eoAddsExecute
        }
    }

    public void addsPopupFetchListner(PopupFetchEvent popupFetchEvent) {
        OperationBinding obind = getBindings().getOperationBinding("applyAddsFileter");
        obind.execute();
        //setSlctDslctBinding(null);
        // AdfFacesContext.getCurrentInstance().addPartialTarget(slctDslctBinding);

        /*    OperationBinding obind1 = getBindings().getOperationBinding("eoAddsExecute");
             obind1.execute();
      */
    }

    public void setSlctDslctBinding(RichSelectBooleanCheckbox slctDslctBinding) {
        this.slctDslctBinding = slctDslctBinding;
    }

    public RichSelectBooleanCheckbox getSlctDslctBinding() {
        return slctDslctBinding;
    }

    public void selectAllCustomer(ActionEvent actionEvent) {
        OperationBinding obind = getBindings().getOperationBinding("selectAllCstmrAdds");
        obind.getParamsMap().put("check", "Y");
        obind.execute();
        /*         OperationBinding obind1 = getBindings().getOperationBinding("eoAddsExecute");
             obind1.execute(); */

    }

    public void deselectAllCstmrAction(ActionEvent actionEvent) {
        OperationBinding obind = getBindings().getOperationBinding("selectAllCstmrAdds");
        obind.getParamsMap().put("check", "N");
        obind.execute();

    }

    public void popAddLocationCstmr(ActionEvent actionEvent) {
        OperationBinding obind = getBindings().getOperationBinding("selectAllAdds");
        obind.getParamsMap().put("check", "Y");
        obind.execute();
        /*      OperationBinding obind1 = getBindings().getOperationBinding("eoAddsExecute");
        obind1.execute(); */

    }

    public void deselectAllpopAction(ActionEvent actionEvent) {
        OperationBinding obind = getBindings().getOperationBinding("selectAllAdds");
        obind.getParamsMap().put("check", "N");
        obind.execute();

        /*
        OperationBinding obind1 = getBindings().getOperationBinding("eoAddsExecute");
        obind1.execute(); */

    }

    public void scTypeVCL(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null && valueChangeEvent.getNewValue().toString().length()>0){
            AdfFacesContext.getCurrentInstance().addPartialTarget(survayTabBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotTabBind); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(shdlTabBind); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(pmtTabBind); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(mailBtnBind); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(cstmrAprvdBtnBind); //asgnTabBind
            AdfFacesContext.getCurrentInstance().addPartialTarget(asgnTabBind);
            
        }
    }

    public void setMailBtnBind(RichLink mailBtnBind) {
        this.mailBtnBind = mailBtnBind;
    }

    public RichLink getMailBtnBind() {
        return mailBtnBind;
    }

    public void setCstmrAprvdBtnBind(RichButton cstmrAprvdBtnBind) {
        this.cstmrAprvdBtnBind = cstmrAprvdBtnBind;
    }

    public RichButton getCstmrAprvdBtnBind() {
        return cstmrAprvdBtnBind;
    }

    public void calcValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...

    }

    public void scCalcTypeVcl(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setItmSrBinding(RichInputText itmSrBinding) {
        this.itmSrBinding = itmSrBinding;
    }

    public RichInputText getItmSrBinding() {
        return itmSrBinding;
    }

    public void deleteSrNoAction(ActionEvent actionEvent) {
       ADFBeanUtils.getOperationBinding("Delete7").execute();
       adfLog.info("---deleted-------");
        AdfFacesContext.getCurrentInstance().addPartialTarget(serialTableBind);

    }

    public void addItmSrNoAction(ActionEvent actionEvent) {
      
     if(itmSrBinding.getValue()!=null){
              String str=itmSrBinding.getValue().toString();
              
              String trimStr=str.trim();
              if(trimStr.length()>0){
                     OperationBinding opchkDup=getBindings().getOperationBinding("isSerialNoValid");
                     opchkDup.getParamsMap().put("SrNo",str);
                     opchkDup.execute();
                     adfLog.info("-----------------"+opchkDup+"---no---"+str);
                     if(opchkDup.getResult()!=null){
                         adfLog.info("-----------------"+opchkDup.getResult()+"---no---"+str);
                     if(opchkDup.getResult().toString().equals("Y")){
                         OperationBinding op=getBindings().getOperationBinding("insertItmSrNo");
                         op.getParamsMap().put("srNo",str);
                         op.execute();
                         itmSrBinding.setValue("");
                     }else if(opchkDup.getResult().toString().equals("D")){
                        // showFacesMessage("Serial No. [ "+srNoBind.getValue().toString()+" ] already exists.", "E", false, "F");
                         String a1=resolvEl("#{bundle['LBL.909']}");
                         String b1= resolvEl("#{bundle['MSG.765']}");
                         String c1=itmSrBinding.getValue().toString();
                        showFacesMessage(a1+" "+c1+" "+b1, "E", false, "F", null);
                     }else if(opchkDup.getResult().toString().equals("I")){
                        // showFacesMessage("All Serial no.'s are assigned for this item.", "E", false, "F");
                        showFacesMessage("MSG.769", "E", true, "F",null);
                     }else if(opchkDup.getResult().toString().equals("O")){
                        // showFacesMessage("Serial no.'s cannot be assigned for this item as Receipt quantity is zero.", "E", false, "F");
                        showFacesMessage("Serial no.'s cannot be assigned for this item as quantity is zero.", "E", true, "F",null);
                     }
                     AdfFacesContext.getCurrentInstance().addPartialTarget(itmSrBinding);
                     }else{
                         adfLog.info("---------------geting op nullll");
                     }
              }else{
                  //showFacesMessage("Invalid Serial No.", "E", false, "F");
                  showFacesMessage("MSG.771", "E", true, "F",null);
              }
             }else{
                // showFacesMessage("Enter Serial Number", "E", false, "F");
                showFacesMessage("MSG.772", "E", true, "F",null);
             }
     AdfFacesContext.getCurrentInstance().addPartialTarget(serialTableBind);
    }

    public void setSerialTableBind(RichTable serialTableBind) {
        this.serialTableBind = serialTableBind;
    }

    public RichTable getSerialTableBind() {
        return serialTableBind;
    }

    public void setSerialisedItmBind(RichInputText serialisedItmBind) {
        this.serialisedItmBind = serialisedItmBind;
    }

    public RichInputText getSerialisedItmBind() {
        return serialisedItmBind;
    }
}
