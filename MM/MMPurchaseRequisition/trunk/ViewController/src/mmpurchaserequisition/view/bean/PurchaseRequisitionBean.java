package mmpurchaserequisition.view.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.math.BigDecimal;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
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

import mmpurchaserequisition.model.service.MMPurchaseRequisitionAMImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

import oracle.jbo.domain.Timestamp;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.UploadedFile;

public class PurchaseRequisitionBean {

    private boolean flag = true;
    private String txnId = null;
    private boolean readny = true;
    private boolean disablebtn = true;
    private boolean createbtn = false;
    private boolean disableEditBtn = true;
    private RichSelectOneChoice srcDoc;
    private RichSelectOneChoice reqArea;
    private RichSelectOneChoice prType;
    private RichInputDate prDate;
    private RichSelectOneChoice prStatus;
    private String saveUpdateMessage;
    private Integer count = -1;
    private RichSelectBooleanCheckbox itemCancel;
    private Integer prevStatus = -1;
    private boolean itemCancelVisbl = true;
    private boolean cancelItems = true;
    private RichTable tblBind;
    private Integer fyid = 0;
    private RichSelectBooleanCheckbox cancelChkBox;
    public static ADFLogger adfLog = (ADFLogger) ADFLogger.createADFLogger(PurchaseRequisitionBean.class);
    private RichInputText bindprQuantity;
    // Integer zero = new Integer(0);
    private RichInputListOfValues bindItmNm;
    private boolean flag1 = false;
    private RichInputDate bindreqdate;
    private RichInputListOfValues itemname;
    private RichInputText quantity;
    private RichInputText itmBsAmtbind;
    private RichInputText itmAmtBsCrnt;
    private RichInputText bdgFlgBinding;
    private RichInputText transTotBindSum;
    private RichTable flexTabBind;
    private List<UploadedFile> UploadedFile;
    private RichTable imgTableBind;
    private RichInputText fileBindName;
    private Boolean costCenterApplicable = false;
    private RichInputText itmAmtBind;
    private RichInputText priceEditFlgBind;

    public void setCostCenterApplicable(Boolean costCenterApplicable) {
        this.costCenterApplicable = costCenterApplicable;
    }

    public Boolean getCostCenterApplicable() {
        OperationBinding ccp = this.getBindings().getOperationBinding("chkCcApplicableOrNot");
        ccp.execute();
        if (ccp.getResult() != null) {
            costCenterApplicable = (Boolean) ccp.getResult();
            adfLog.info("cc ap return-------" + costCenterApplicable);
        }

        return costCenterApplicable;
    }

    public void setUploadedFile(List<UploadedFile> UploadedFile) {
        this.UploadedFile = UploadedFile;
    }

    public List<UploadedFile> getUploadedFile() {
        return UploadedFile;
    }

    public PurchaseRequisitionBean() {

        count = (Integer) getBindings().getOperationBinding("on_load_page").execute();
        OperationBinding op = getBindings().getOperationBinding("getCurrentPrTnxIdTF");
        op.execute();
        String str = (String) op.getResult();
        if (str != null) {
            setTxnId(str);
            disablebtn = true;
            createbtn = false;
            flag = true;
            readny = true;
            disableEditBtn = false;
            itemCancelVisbl = true;
        } else {
            setTxnId(null);
            disablebtn = false;
            flag = false;
            readny = false;
            disableEditBtn = true;
            saveUpdateMessage = evaluateEL("#{bundle['MSG.91']}").toString();
            createbtn = true;
            itemCancelVisbl = false;
        }


    }

    public String saveAction() {
        // Add event code here...
        adfLog.info("budget flag value is ::::: " + bdgFlgBinding.getValue());
        if (bdgFlgBinding.getValue() != null && bdgFlgBinding.getValue().toString().length() > 0) {
            if ("Y".equalsIgnoreCase(bdgFlgBinding.getValue().toString())) {
                OperationBinding validbind = getBindings().getOperationBinding("valiadateBdgAmt");
                validbind.execute();
                adfLog.info("current error is " + validbind.getErrors() + " " + validbind.getResult());
                if (validbind.getErrors().isEmpty()) {
                    String result = validbind.getResult().toString();
                    if ("N".equalsIgnoreCase(result)) {
                        return null;
                    }

                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(transTotBindSum);


        OperationBinding validFlexi = getBindings().getOperationBinding("validateFlexi");
        validFlexi.execute();
        if (validFlexi.getResult() != null) {
            ArrayList<String> al = (ArrayList) validFlexi.getResult();
            StringBuilder str = new StringBuilder("<html><body>");
            //Some field mandatory in Other Details tab
            str.append("<b>");
            str.append(evaluateEL("#{bundle['MSG.2446']}").toString());
            str.append("</b>");
            str.append("<ul>");
            //str.append("<html><body>");
            for (Object obj : al) {
                str.append("<b> <li>  " + obj + "  </li></b>");
            }
            str.append("</ul>");
            if (al.size() > 0) {
                str.append("</body></html>");
                FacesMessage message = new FacesMessage(str.toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                return null;
            }
            //
            adfLog.info("----bean valid field-- " + al.size() + " " + validFlexi.getResult());
            /*  if(al.size()>0){

              String msg = al.toString().substring(1, al.toString().length()-1);
              FacesContext.getCurrentInstance().addMessage(null,
                                                           new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                                                          //Some fields are mandatory in Other Details tab
                                                                          evaluateEL("#{bundle['MSG.2446']}").toString()));

              return null;
          } */

        }

        if (reqArea.getValue() == null || reqArea.getValue().toString().length() <= 0) {
            FacesMessage msg = new FacesMessage(evaluateEL("#{bundle['MSG.573']}").toString());
            msg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(reqArea.getClientId(fctx), msg);
        } else if (prType.getValue() == null || prType.getValue().toString().length() <= 0) {
            FacesMessage msg = new FacesMessage(evaluateEL("#{bundle['MSG.576']}").toString());
            msg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(prType.getClientId(fctx), msg);
        }
        //        else if (srcDoc.getValue() == null || srcDoc.getValue().toString().length() <= 0) {
        //            FacesMessage msg = new FacesMessage(evaluateEL("#{bundle['MSG.580']}").toString());
        //            msg.setSeverity(FacesMessage.SEVERITY_FATAL);
        //            FacesContext fctx = FacesContext.getCurrentInstance();
        //            fctx.addMessage(srcDoc.getClientId(fctx), msg);
        //        }
        else if (prDate.getValue() == null || prDate.getValue().toString().length() <= 0) {
            FacesMessage msg = new FacesMessage(evaluateEL("#{bundle['MSG.578']}").toString());
            msg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(prDate.getClientId(fctx), msg);
        } else if (prStatus.getValue() == null || prStatus.getValue().toString().length() <= 0) {
            FacesMessage msg = new FacesMessage(evaluateEL("#{bundle['MSG.579']}").toString());
            msg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(prStatus.getClientId(fctx), msg);
        }

        else if (prDate.getValue() != null || prDate.getValue().toString().length() > 0) {

            OperationBinding op = getBindings().getOperationBinding("getFYid");
            op.execute();
            Integer fyId = (Integer) op.getResult();


            if (fyId != null && fyId < 0) {
                FacesMessage msg = new FacesMessage(evaluateEL("#{bundle['MSG.575']}").toString());
                msg.setSeverity(FacesMessage.SEVERITY_FATAL);
                FacesContext fctx = FacesContext.getCurrentInstance();
                fctx.addMessage(prDate.getClientId(fctx), msg);
            }
            //            else {
            //                commit();
            //            }


            else {
                adfLog.info("Item Value::" + itemname.getValue() + "quantity::" + quantity.getValue());
                if (itemname.getValue() != null && itemname.getValue() != "") {
                    if (quantity.getValue() != null && quantity.getValue() != "") {
                        OperationBinding a1 = getBindings().getOperationBinding("ItemNameDuplicate");
                        a1.execute();
                        adfLog.info("reuslt:" + a1.getResult());
                        if (a1.getResult() != null && a1.getResult().equals("Y")) {
                            System.out.println("in the else block");
                            Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                            String p_org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
                            String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
                            int p_userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
                            MMPurchaseRequisitionAMImpl am =
                                (MMPurchaseRequisitionAMImpl) resolvElDC("MMPurchaseRequisitionAMDataControl");
                            adfLog.info("Value" + am.getMmPr2().getCurrentRow().getAttribute("AuthStat"));
                            String auth = (String) am.getMmPr2().getCurrentRow().getAttribute("AuthStat");
                            Integer prstat = (Integer) am.getMmPr2().getCurrentRow().getAttribute("PrStat");
                            adfLog.info("value of auth::" + auth);
                            if (auth.equals("N") || prstat.equals(291)) { //  18 aug 15 // for enable save and forward after approval.

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
                                WfnoOp.getParamsMap().put("DocNo", 18514);
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
                                    usrLevelOp.getParamsMap().put("DocNo", 18514);
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
                                        insTxnOp.getParamsMap().put("DocNo", 18514);
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
                                        //method to save data from temp cost table to pr$cost$center
                                        //     OperationBinding costCenterSave = getBindings().getOperationBinding("sendDataFromTempCcToPRCc");
                                        //   costCenterSave.execute();


                                        OperationBinding saveOp = getBindings().getOperationBinding("Commit");
                                        saveOp.execute();
                                        disablebtn = true;
                                        RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
                                        //setMode("V");
                                        showFacesMessage(" Record Saved Successfully", "I", false, "F", null);

                                    } else {
                                        //Something went wrong(User Level in Workflow).Please Contact to ESS!

                                        showFacesMessage(evaluateEL("#{bundle['MSG.1875']}").toString(), "E", false,
                                                         "F", null);

                                    }
                                } else {
                                    //Workflow is not created for Purchase Requisition Slip.
                                    showFacesMessage(evaluateEL("#{bundle['MSG.2447']}").toString(), "E", false, "F",
                                                     null);

                                }
                            }
                        } else {
                            //Duplicate Item Name Found
                            showFacesMessage(evaluateEL("#{bundle['MSG.763']}").toString(), "E", false, "F",
                                             itemname.getClientId());
                        }
                    } else {
                        //Quantity is required.
                        showFacesMessage(evaluateEL("#{bundle['MSG.1706']}").toString(), "E", false, "F",
                                         quantity.getClientId());
                    }
                } else {
                    //Item name is required.
                    showFacesMessage(evaluateEL("#{bundle['MSG.430']}").toString(), "E", false, "F",
                                     itemname.getClientId());
                }

            }
        }
        return null;
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


    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /*  public String createAction() {
        // Add event code here...
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        flag=false;
        disableEditBtn=true;
        createbtn=true;
        readOnly=false;
        disablebtn=false;
        return null;
    }
*/
    public String cancelAction() {
        // Add event code here...

        return null;
    }

    public void getSrcDocItems(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        AdfFacesContext.getCurrentInstance().addPartialTarget(tblBind);
        OperationBinding op = getBindings().getOperationBinding("setPrItems");
        op.getParamsMap().put("txnId", valueChangeEvent.getNewValue());
        op.execute();

    }

    public void checkValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        OperationBinding op = getBindings().getOperationBinding("checkPendingQty");
        op.getParamsMap().put("qty", new BigDecimal((object.toString())));
        op.execute();

        Boolean x = (Boolean) op.getResult();
        if (!x) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          evaluateEL("#{bundle['MSG.577']}").toString(), null));
            //Quantity Should not greater than PR Quantity.
        }

    }

    public void updatePendingQty(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue() != null) {
            OperationBinding op = getBindings().getOperationBinding("updatePendingQty");
            op.getParamsMap().put("quantity", new BigDecimal(valueChangeEvent.getNewValue().toString()));
            op.execute();

        }

    }


    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void cancelAction(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        flag = true;
    }


    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setDisablebtn(boolean disablebtn) {
        this.disablebtn = disablebtn;
    }

    public boolean isDisablebtn() {
        return disablebtn;
    }

    public void editAction(ActionEvent actionEvent) {
        // Add event code here...
        //  srcDoc.processUpdates(FacesContext.getCurrentInstance());
        //  AdfFacesContext.getCurrentInstance().addPartialTarget(srcDoc);
        //        OperationBinding op = getBindings().getOperationBinding("getPRStatus");
        //        op.execute();
        //        Integer prStatus = (Integer) op.getResult();
        //        setPrevStatus(prStatus);
        //
        //        String OrgId = evaluateEL("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        //        Integer SlocId = Integer.parseInt(evaluateEL("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        //        String CldId = evaluateEL("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        //        String currUser = evaluateEL("#{pageFlowScope.GLBL_APP_USR}").toString();
        //
        //        OperationBinding chkUsr = getBindings().getOperationBinding("getModifiedUser");
        //        chkUsr.execute();
        //        Object pendUser = chkUsr.getResult();
        //        if (pendUser != null) {
        //            if (!currUser.equals(pendUser.toString())) {
        //                String msg2 = evaluateEL("#{bundle['MSG.612']}").toString();
        //                FacesMessage message2 = new FacesMessage(msg2);
        //                message2.setSeverity(FacesMessage.SEVERITY_INFO);
        //                FacesContext fc = FacesContext.getCurrentInstance();
        //                fc.addMessage(null, message2);
        //                return;
        //            }
        //
        //        }
        //
        //
        //        srcDoc.processUpdates(FacesContext.getCurrentInstance());
        //        AdfFacesContext.getCurrentInstance().addPartialTarget(srcDoc);
        //        saveUpdateMessage = evaluateEL("#{bundle['MSG.92']}").toString();

        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        int userId = 0;
        OperationBinding check = getBindings().getOperationBinding("pendingCheck");
        check.getParamsMap().put("SlocId", sloc_Id);
        check.getParamsMap().put("CldId", cld_Id);
        check.getParamsMap().put("OrgId", org_Id);
        check.getParamsMap().put("DocNo", 18514);
        check.execute();

        if (check.getErrors().isEmpty()) {
            userId = Integer.parseInt(check.getResult().toString());
            System.out.println("userId function " + userId);
        }
        if (usr_Id == userId || userId == -1) {
            System.out.println("user_ id" + userId);
            RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "A");
            //setMode("A");
        } else {
            String name = "Anonymous";
            OperationBinding getusrname = getBindings().getOperationBinding("getModifiedUser");
            getusrname.getParamsMap().put("usrId", userId);
            getusrname.execute();
            if (getusrname.getErrors().size() == 0 && getusrname.getResult() != null)
                name = (String) getusrname.getResult();
            String msg =
                "This Purchase Requisition Slip is Pending at [" + name +
                "]. You can't Edit this Purchase Requisition Slip.";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        //AdfFacesContext.getCurrentInstance().addPartialTarget(panelGrpBind);
        //return null;


        disablebtn = false;
        readny = false;
        disableEditBtn = true;
        // flag = true;
        cancelItems = false;
        // RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "A");

    }


    public void setCreatebtn(boolean createbtn) {
        this.createbtn = createbtn;
    }

    public boolean isCreatebtn() {
        return createbtn;
    }


    public void setDisableEditBtn(boolean disableEditBtn) {
        this.disableEditBtn = disableEditBtn;
    }

    public boolean isDisableEditBtn() {
        return disableEditBtn;
    }

    public void setSrcDoc(RichSelectOneChoice srcDoc) {
        this.srcDoc = srcDoc;
    }

    public RichSelectOneChoice getSrcDoc() {
        return srcDoc;
    }

    public void updateSrcDoc(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        // srcDoc.updateModel(FacesContext.getCurrentInstance());
        srcDoc.processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(srcDoc);
    }

    private boolean srcDocDisable = true;

    public void setSrcDocDisable(boolean srcDocDisable) {
        this.srcDocDisable = srcDocDisable;
    }

    public boolean isSrcDocDisable() {
        return srcDocDisable;
    }

    public void wareHouseChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        srcDocDisable = false;
    }

    public void setReqArea(RichSelectOneChoice reqArea) {
        this.reqArea = reqArea;
    }

    public RichSelectOneChoice getReqArea() {
        return reqArea;
    }

    public void setPrType(RichSelectOneChoice prType) {
        this.prType = prType;
    }

    public RichSelectOneChoice getPrType() {
        return prType;
    }

    public void setPrDate(RichInputDate prDate) {
        this.prDate = prDate;
    }

    public RichInputDate getPrDate() {
        return prDate;
    }

    public void setPrStatus(RichSelectOneChoice prStatus) {
        this.prStatus = prStatus;
    }

    public RichSelectOneChoice getPrStatus() {
        return prStatus;
    }

    public void commit() {
        OperationBinding op = getBindings().getOperationBinding("Commit");
        op.execute();

        if (op.getErrors().isEmpty()) {
            createbtn = false;
            readny = true;
            disableEditBtn = false;
            flag = true;
            disablebtn = true;
            itemCancelVisbl = true;
            cancelItems = true;
            FacesMessage msg = new FacesMessage(saveUpdateMessage);
            msg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, msg);
        }
    }

    public static Object evaluateEL(String el) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);
        return exp.getValue(elContext);
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void createActionListener(ActionEvent actionEvent) {
        // Add event code here...
        srcDoc.processUpdates(FacesContext.getCurrentInstance());

        AdfFacesContext.getCurrentInstance().addPartialTarget(srcDoc);
        flag = false;
        disableEditBtn = true;
        createbtn = true;
        readny = false;
        disablebtn = false;
        saveUpdateMessage = evaluateEL("#{bundle['MSG.91']}").toString();
        itemCancelVisbl = false;
        cancelItems = true;
        //        RequestContext context = RequestContext.getCurrentInstance();
        //        context.getPageFlowScope().put("", "C");
    }

    public void checkUncheckListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...


        Boolean checked = (Boolean) valueChangeEvent.getNewValue();

        if (checked) {
            OperationBinding op = getBindings().getOperationBinding("checkUncheckAll");
            op.getParamsMap().put("tnxId", txnId);
            op.getParamsMap().put("b", true);
            op.getParamsMap().put("prStatus", 342);
            op.execute();
            itemCancel.setDisabled(true);

        }
        if (!checked) {
            OperationBinding op = getBindings().getOperationBinding("checkUncheckAll");
            op.getParamsMap().put("tnxId", txnId);
            op.getParamsMap().put("b", false);
            op.getParamsMap().put("prStatus", getPrevStatus());
            op.execute();
            itemCancel.setDisabled(false);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(prStatus);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemCancel);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblBind);

    }

    public void setItemCancel(RichSelectBooleanCheckbox itemCancel) {
        this.itemCancel = itemCancel;
    }

    public RichSelectBooleanCheckbox getItemCancel() {
        return itemCancel;
    }

    public void setPrevStatus(Integer prevStatus) {
        this.prevStatus = prevStatus;
    }

    public Integer getPrevStatus() {
        return prevStatus;
    }

    public void setItemCancelVisbl(boolean itemCancelVisbl) {
        this.itemCancelVisbl = itemCancelVisbl;
    }

    public boolean isItemCancelVisbl() {
        return itemCancelVisbl;
    }

    public void setCancelItems(boolean cancelItems) {
        this.cancelItems = cancelItems;
    }

    public boolean isCancelItems() {
        return cancelItems;
    }

    public void setTblBind(RichTable tblBind) {
        this.tblBind = tblBind;
    }

    public RichTable getTblBind() {
        return tblBind;
    }

    public void setReadny(boolean readny) {
        this.readny = readny;
    }

    public boolean isReadny() {
        return readny;
    }

    public void checkBoxListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean checked = (Boolean) valueChangeEvent.getNewValue();
        if (checked) {
            OperationBinding op = getBindings().getOperationBinding("isAllCheckBoxMark");
            op.execute();
            Boolean flag = (Boolean) op.getResult();
            if (flag) {
                op = getBindings().getOperationBinding("setStatus");
                op.getParamsMap().put("prStatus", 342);
                op.execute();
                cancelChkBox.setValue(true);
            } else {
                cancelChkBox.setValue(false);
            }
        } else {
            OperationBinding op = getBindings().getOperationBinding("setStatus");
            op.getParamsMap().put("prStatus", prevStatus);
            op.execute();
            cancelChkBox.setValue(false);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(cancelChkBox);
    }

    public void setCancelChkBox(RichSelectBooleanCheckbox cancelChkBox) {
        this.cancelChkBox = cancelChkBox;
    }

    public RichSelectBooleanCheckbox getCancelChkBox() {
        return cancelChkBox;
    }


    public void addItemActionListener(ActionEvent actionEvent) {
        // Add event code here..
        // adfLog.info("Value" + reqArea.getValue() + " " + bindreqdate.getValue() + " " + prType.getValue());
        if (reqArea.getValue() != null && reqArea.getValue() != null) {
            if (bindreqdate.getValue() != null && bindreqdate.getValue() != "") {
                if (prType.getValue() != null && prType.getValue() != "") {
                    MMPurchaseRequisitionAMImpl am =
                        (MMPurchaseRequisitionAMImpl) resolvElDC("MMPurchaseRequisitionAMDataControl");
                    Integer count = (Integer) am.getMmPrItm2().getRowCountInRange();
                    OperationBinding a1 = null;
                    if (count > 0) {
                        a1 = getBindings().getOperationBinding("ItemNameDuplicate");
                        a1.execute();
                        adfLog.info("result:" + a1.getResult());
                        if (a1.getResult() != null && a1.getResult().equals("N")) {
                            showFacesMessage("Duplicate Item Exists.", "E", false, "F", itemname.getClientId());
                            return;
                        }
                    }
                    Integer tblCont = tblBind.getRowCount();
                    adfLog.info("tblCont:" + tblCont);
                    if (tblCont.compareTo(new Integer(0)) == 0 && flexTabBind.getRowCount() == 0) {
                        //System.out.println("call getFlexiFildMethod from Amimpl");
                        OperationBinding op1 = getBindings().getOperationBinding("getFlexiFieldFilter");
                        op1.execute();
                    }


                    //var1 = "D";
                    OperationBinding op = getBindings().getOperationBinding("CreateInsert1");
                    op.execute();
                    // flag1 = true;
                } else {
                    //Pr type is required.
                    showFacesMessage(evaluateEL("#{bundle['MSG.576']}").toString(), "E", false, "F",
                                     prType.getClientId());
                }
            } else {
                //Requirement Date is required
                showFacesMessage(evaluateEL("#{bundle['MSG.2448']}").toString(), "E", false, "F",
                                 bindreqdate.getClientId());
            }
        } else {
            //Requirement Area is required
            showFacesMessage(evaluateEL("#{bundle['MSG.2449']}").toString(), "E", false, "F", reqArea.getClientId());
        }


        //}


        //        if (bindItmNm.getValue() != null && bindItmNm.getValue() != "") {
        //            if (bindprQuantity.getValue() != null && bindprQuantity.getValue() != "") {
        //                OperationBinding op = getBindings().getOperationBinding("ItemNameDuplicate");
        //                op.execute();
        //                adfLog.info("result:" + op.getResult());
        //                if (op.getResult() != null && op.getResult().equals("Y")) {
        //                    OperationBinding op1 = getBindings().getOperationBinding("addMmPrItem");
        //                    op1.execute();
        //                    bindItmNm.setValue("");
        //                    bindprQuantity.setValue("");
        //                } else {
        //                    showFacesMessage("Duplicate Item Exists.", "E", false, "F", null);
        //                }
        //
        //            } else {
        //                showFacesMessage("Quantity is required.", "E", false, "F", bindprQuantity.getClientId());
        //            }
        //        } else {
        //            showFacesMessage("Item name is required.", "E", false, "F", bindItmNm.getClientId());
        //        }
        // }


    }

    public void prQuantityValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        Number objectNew = (Number) object;
        Number zero = new Number(0);

        if (objectNew == null) {
            //Enter the Quantity
            showFacesMessage(evaluateEL("#{bundle['MSG.2450']}").toString(), "I", false, "V", null);

        } else if (objectNew.compareTo(zero) == 0 || objectNew.compareTo(zero) == -1) {
            //Quantity should be greater than zero.
            showFacesMessage(evaluateEL("#{bundle['MSG.474']}").toString(), "E", false, "V", null);

        }

    }

    public void setBindprQuantity(RichInputText bindprQuantity) {
        this.bindprQuantity = bindprQuantity;
    }

    public RichInputText getBindprQuantity() {
        return bindprQuantity;
    }

    public void setBindItmNm(RichInputListOfValues bindItmNm) {
        this.bindItmNm = bindItmNm;
    }

    public RichInputListOfValues getBindItmNm() {
        return bindItmNm;
    }

    public String saveAndForward() {
        // Add event code here...
        //commented for a while
        adfLog.info("budget flag value is ::::: " + bdgFlgBinding.getValue());
        if (bdgFlgBinding.getValue() != null && bdgFlgBinding.getValue().toString().length() > 0) {
            if ("Y".equalsIgnoreCase(bdgFlgBinding.getValue().toString())) {
                OperationBinding validbind = getBindings().getOperationBinding("valiadateBdgAmt");
                validbind.execute();
                adfLog.info("current error is " + validbind.getErrors() + " " + validbind.getResult());
                if (validbind.getErrors().isEmpty()) {
                    String result = validbind.getResult().toString();
                    if ("N".equalsIgnoreCase(result)) {
                        return null;
                    }

                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(transTotBindSum);

        OperationBinding validFlexi = getBindings().getOperationBinding("validateFlexi");
        validFlexi.execute();
        if (validFlexi.getResult() != null) {

            ArrayList<String> al = (ArrayList) validFlexi.getResult();
            //
            StringBuilder str = new StringBuilder("<html><body>");
            //Some field mandatory in Other Details tab
            str.append("<b>");
            str.append(evaluateEL("#{bundle['MSG.2446']}").toString());
            str.append("</b>");
            str.append("<ul>");
            //str.append("<html><body>");
            for (Object obj : al) {
                str.append("<b> <li>  " + obj + "  </li></b>");
            }
            str.append("</ul>");
            if (al.size() > 0) {

                str.append("</body></html>");
                FacesMessage message = new FacesMessage(str.toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                return null;
            }
            //
            adfLog.info("----bean valid field-- " + al.size() + " " + validFlexi.getResult());
            /*  if(al.size()>0){

                String msg = al.toString().substring(1, al.toString().length()-1);
                FacesContext.getCurrentInstance().addMessage(null,
                                                             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                                                            //Some fields are mandatory in Other Details tab
                                                                            evaluateEL("#{bundle['MSG.2446']}").toString()));

                return null;
            } */

        }

        if (itemname.getValue() != null && itemname.getValue() != "") {
            if (quantity.getValue() != null && quantity.getValue() != "") {

                OperationBinding a1 = getBindings().getOperationBinding("ItemNameDuplicate");
                a1.execute();
                adfLog.info("reuslt:" + a1.getResult());
                if (a1.getResult() != null && a1.getResult().equals("Y")) {
                    Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                    String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
                    String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
                    int usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
                    int userId = 0;
                    OperationBinding check = getBindings().getOperationBinding("pendingCheck");
                    check.getParamsMap().put("SlocId", sloc_Id);
                    check.getParamsMap().put("CldId", cld_Id);
                    check.getParamsMap().put("OrgId", org_Id);
                    check.getParamsMap().put("DocNo", 18514);
                    check.execute();

                    if (check.getErrors().isEmpty()) {
                        userId = Integer.parseInt(check.getResult().toString());
                        // System.out.println("userId function " + userId);
                    }

                    //        OperationBinding resettax = getBindings().getOperationBinding("taxablecheck");
                    //        resettax.execute();
                    //        String istxble = null;
                    //        if (resettax.getErrors().isEmpty()) {
                    //            istxble = resettax.getResult().toString();
                    //            adfLog.info("txble check result " + istxble);
                    //        }
                    //        if ("Y".equalsIgnoreCase(istxble)) {
                    //            OperationBinding chkAmt = getBindings().getOperationBinding("chkPmtAmt");
                    //            chkAmt.execute();
                    //            String chkPmt = null;
                    //            if (chkAmt.getResult() != null)
                    //                chkPmt = chkAmt.getResult().toString();
                    //            if ("Y".equalsIgnoreCase(chkPmt)) {
                    if (usr_Id == userId || userId == -1) {
                        //            OperationBinding geteo = getBindings().getOperationBinding("genPrNo");
                        //            geteo.execute();
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
                        WfnoOp.getParamsMap().put("DocNo", 18514);
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
                            usrLevelOp.getParamsMap().put("DocNo", 18514);
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
                                insTxnOp.getParamsMap().put("DocNo", 18514);
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
                                chkUsr.getParamsMap().put("DocNo", 18514);
                                chkUsr.execute();

                                if (chkUsr.getResult() != null) {
                                    pending = Integer.parseInt(chkUsr.getResult().toString());
                                    System.out.println("pending check" + pending);
                                }
                                if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(usr_Id) == 0) {
                                    adfLog.info("iiiiiiiiii");
                                    OperationBinding saveOp = getBindings().getOperationBinding("Commit");
                                    saveOp.execute();

                                    //showFacesMessage("Record Saved Successfully", "I", false, "F", null);
                                    RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
                                    //  setMode("V");
                                    //AdfFacesContext.getCurrentInstance().addPartialTarget(panelGrpBind);
                                    //no pending
                                    /*  to check attached file is required.
                                     * Y--required
                                     * N-not required.
                                     * */
                                    RequestContext.getCurrentInstance().getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                    OperationBinding attchChkOp = getBindings().getOperationBinding("chkAttchRqd");
                                    attchChkOp.execute();
                                    if (attchChkOp.getResult() != null &&
                                        attchChkOp.getResult().toString().equalsIgnoreCase("Y")) {
                                        //Please upload file from Document Upload tab, then you can proceed.
                                        String msg = evaluateEL("#{bundle['MSG.2451']}").toString();
                                        showFacesMessage(msg, "I", false, "F", null);
                                        adfLog.info("inside req attch true---------------");
                                        return null;
                                    } else
                                        return "towf";
                                }
                                /*  else {//pending
                                                         return null;
                                                         } */
                            } else {
                                //Something went wrong(User Level in Workflow).Please Contact to ESS!
                                showFacesMessage(evaluateEL("#{bundle['MSG.1875']}").toString(), "E", false, "F", null);
                                return null;
                            }
                        } else {
                            //Workflow not Created for Purchase Requisition Slip
                            showFacesMessage(evaluateEL("#{bundle['MSG.2452']}").toString(), "E", false, "F", null);
                            return null;
                        }

                    }
                } else {
                    //Duplicate Item Name Exists.
                    showFacesMessage(evaluateEL("#{bundle['MSG.763']}").toString(), "E", false, "F",
                                     itemname.getClientId());
                }
            } else {
                //Quantity is required.
                showFacesMessage(evaluateEL("#{bundle['MSG.1706']}").toString(), "E", false, "F",
                                 quantity.getClientId());
            }

        } else {
            //Item name is required.
            showFacesMessage(evaluateEL("#{bundle['MSG.430']}").toString(), "E", false, "F", itemname.getClientId());
        }
        return null;
    }

    public void deletePrItemActionListener(ActionEvent actionEvent) {
        // Add event code here...
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("deleteCostCenterItem").execute();
        OperationBinding op = getBindings().getOperationBinding("Delete");
        op.execute();
    }

    public void setBindreqdate(RichInputDate bindreqdate) {
        this.bindreqdate = bindreqdate;
    }

    public RichInputDate getBindreqdate() {
        return bindreqdate;
    }

    public void setItemname(RichInputListOfValues itemname) {
        this.itemname = itemname;
    }

    public RichInputListOfValues getItemname() {
        return itemname;
    }

    public void setQuantity(RichInputText quantity) {
        this.quantity = quantity;
    }

    public RichInputText getQuantity() {
        return quantity;
    }

    public void transItmValueChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            /*  OperationBinding obind = getBindings().getOperationBinding("latestPriceUpdate");
            obind.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmBsAmtbind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmAmtBsCrnt); */
            AdfFacesContext.getCurrentInstance().addPartialTarget(tblBind);
        }
    }

    public void setItmBsAmtbind(RichInputText itmBsAmtbind) {
        this.itmBsAmtbind = itmBsAmtbind;
    }

    public RichInputText getItmBsAmtbind() {
        return itmBsAmtbind;
    }

    public void setItmAmtBsCrnt(RichInputText itmAmtBsCrnt) {
        this.itmAmtBsCrnt = itmAmtBsCrnt;
    }

    public RichInputText getItmAmtBsCrnt() {
        return itmAmtBsCrnt;
    }

    public String cancelPRSaction() {
        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        int userId = 0;
        AdfFacesContext.getCurrentInstance().addPartialTarget(transTotBindSum);
        OperationBinding check = getBindings().getOperationBinding("pendingCheck");
        check.getParamsMap().put("SlocId", sloc_Id);
        check.getParamsMap().put("CldId", cld_Id);
        check.getParamsMap().put("OrgId", org_Id);
        check.getParamsMap().put("DocNo", 18514);
        check.execute();

        if (check.getErrors().isEmpty()) {
            userId = Integer.parseInt(check.getResult().toString());
            System.out.println("userId function " + userId);
        }
        if (usr_Id == userId || userId == -1) {
            System.out.println("user_ id" + userId);
            return "cancelpr";
            // RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "A");
            //setMode("A");
        } else {
            String name = "Anonymous";
            OperationBinding getusrname = getBindings().getOperationBinding("getModifiedUser");
            getusrname.getParamsMap().put("usrId", userId);
            getusrname.execute();
            if (getusrname.getErrors().size() == 0 && getusrname.getResult() != null)
                name = (String) getusrname.getResult();
            //                "This Purchase Requisition Slip is Pending at [" + name + "]. You can't Edit this Purchase Requisition Slip."
            String msg =
                evaluateEL("#{bundle['MSG.2453']}").toString() + name + evaluateEL("#{bundle['MSG.2454']}").toString();
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        return null;
    }

    public void setBdgFlgBinding(RichInputText bdgFlgBinding) {
        this.bdgFlgBinding = bdgFlgBinding;
    }

    public RichInputText getBdgFlgBinding() {
        return bdgFlgBinding;
    }

    public void setTransTotBindSum(RichInputText transTotBindSum) {
        this.transTotBindSum = transTotBindSum;
    }

    public RichInputText getTransTotBindSum() {
        return transTotBindSum;
    }

    public void tmPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && this.getPriceEditFlgBind().getValue() != null &&
            this.getPriceEditFlgBind().getValue().toString().equals("Y")) {
            Number objectNew = (Number) object;
            Number zero = new Number(0);

            if (objectNew.compareTo(zero) == 0 || objectNew.compareTo(zero) == -1) {
                showFacesMessage(evaluateEL("#{bundle['MSG.1048']}").toString(), "E", false, "V",
                                 null); //Price should be greater than zero.

            }
        }
    }

    public void setFlexTabBind(RichTable flexTabBind) {
        this.flexTabBind = flexTabBind;
    }

    public RichTable getFlexTabBind() {
        return flexTabBind;
    }

    public void attachFileAction(ActionEvent actionEvent) {
        // Add event code here...
        try {
            uploadFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String uploadFiles() throws Exception {
        System.out.println("---------1");
        String path = "";
        String extn = "";
        List<UploadedFile> files = this.getUploadedFile();
        InputStream in = null;
        FileOutputStream out = null;
        if (files != null) {
            System.out.println("---------2");
            for (int i = 0; i < files.size(); i++) {
                try {

                    System.out.println("---------------------3");
                    //get file extension
                    extn = files.get(i).getFilename().substring(files.get(i).getFilename().lastIndexOf("."));

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
                    UploadedFile = null;
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
                    UploadedFile = null;
                }
            }
        }
        System.out.println("--------------------5");
        //remove the files to prepare for new uploads
        setUploadedFile(null);
        OperationBinding obExecute = getBindings().getOperationBinding("Execute");
        obExecute.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(imgTableBind);
        //ADFUtils.findOperation("Execute5").execute();
        return "null";
    }

    public void downloadFileListener(FacesContext facesContext, OutputStream outputStream) {
        //Read file from particular path, path bind is binding of table field that contains path
        //File filed = new File(pathPgBind.getValue().toString());
        // String path = JSFUtils.resolveExpressionAsString("#{row.AttchFlPath}");
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

    public void setImgTableBind(RichTable imgTableBind) {
        this.imgTableBind = imgTableBind;
    }

    public RichTable getImgTableBind() {
        return imgTableBind;
    }

    public void setFileBindName(RichInputText fileBindName) {
        this.fileBindName = fileBindName;
    }

    public RichInputText getFileBindName() {
        return fileBindName;
    }

    public void attachdeleteAction(ActionEvent actionEvent) {
        OperationBinding obExecute1 = getBindings().getOperationBinding("Delete1");
        obExecute1.execute();
        getBindings().getOperationBinding("Commit").execute();
        OperationBinding obExecute = getBindings().getOperationBinding("Execute");
        obExecute.execute();
    }

    public String costCenterHeaderAction() {
        OperationBinding ccFinalize = getBindings().getOperationBinding("ccFinalizedCheck");
        ccFinalize.execute();
        if (ccFinalize.getResult() != null) {
            if (ccFinalize.getResult().toString().equalsIgnoreCase("N")) {
                //Profit Centre Profile is not setup properly.
                showFacesMessage(evaluateEL("#{bundle['MSG.2455']}").toString(), "I", false, "F", null);
                return null;
            }
        }

        OperationBinding binding = getBindings().getOperationBinding("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            return "headCc";
        } else {
            return null;
        }
    }

    public String costCenterItemAction() {
        //System.out.println(" greater than----"+itmAmtBind.getValue());

        OperationBinding ccFinalize = getBindings().getOperationBinding("ccFinalizedCheck");
        ccFinalize.execute();
        if (ccFinalize.getResult() != null) {
            if (ccFinalize.getResult().toString().equalsIgnoreCase("N")) {
                //Profit Center Profile is not setup properly.
                showFacesMessage(evaluateEL("#{bundle['MSG.2455']}").toString(), "I", false, "F", null);
                return null;
            }
        }
        if (itmAmtBind.getValue() != null && ((Number) itmAmtBind.getValue()).compareTo(new Number(0)) == 0) {
            //Amount must be greater than zero
            showFacesMessage(evaluateEL("#{bundle['MSG.382']}").toString(), "E", false, "F", null);
            return null;
        }
        OperationBinding binding = getBindings().getOperationBinding("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("updateCostCenterAmt").execute();
            return "costCenter";
        } else {
            return null;
        }
    }


    public void setItmAmtBind(RichInputText itmAmtBind) {
        this.itmAmtBind = itmAmtBind;
    }

    public RichInputText getItmAmtBind() {
        return itmAmtBind;
    }
    //get Top WF usr level
    public String getTopWFUsrLevel() {
        OperationBinding op = getBindings().getOperationBinding("getTopWFUsr");
        op.execute();
        String retn = "N";
        if (op.getResult() != null) {
            retn = op.getResult().toString();
            adfLog.info("---return ---" + retn);
            return retn;
        } else
            return retn;
    }

    public void setPriceEditFlgBind(RichInputText priceEditFlgBind) {
        this.priceEditFlgBind = priceEditFlgBind;
    }

    public RichInputText getPriceEditFlgBind() {
        return priceEditFlgBind;
    }

    public void reqdDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && prDate.getValue() != null) {
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date reqDt = dateFormat.parse(object.toString());
                Date prDt = dateFormat.parse(prDate.getValue().toString());
                adfLog.info("---------date----" + reqDt + " prdt " + prDt);
                if (reqDt.compareTo(prDt) == -1) {
                    String msg = evaluateEL("#{bundle['MSG.2555']}").toString();
                    showFacesMessage("Date must be on or after " + prDate.getValue().toString().substring(0, 10), "E",
                                     false, "V", null); //Date must be on or after
                }


            } catch (ParseException e) {
            }
        }

    }
    
    public String getWfHistryView(){
        OperationBinding wfFlowView = getBindings().getOperationBinding("viewWFData");
        wfFlowView.execute();
        if(wfFlowView.getResult()!=null){
            return wfFlowView.getResult().toString();
        }else{
            return "Document Not In WF"; 
        }
    }
}
