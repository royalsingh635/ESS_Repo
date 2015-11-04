package materialreturnnote.view.bean;

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
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;

/**
 * Material Return Note Managed Bean.
 * Contains all the business logic for Material Return functionalities.
 * @author BL
 * Dated -30/11/2013
 * */

public class MRNAddEditBean {
    private RichInputText okQtyBind;
    private RichInputText rejQtyBind;
    private RichInputText rwrkQtyBind;
    private RichInputText scrapQtyBind;
    private RichInputText totIssQtyBind;
    Number zero = new Number(0);
    private RichInputDate mrnDateBind;
    private String mode = modeGet();
    private RichInputText srTransBind;

    public MRNAddEditBean() {
    }
    private static ADFLogger adflog = ADFLogger.createADFLogger(MRNAddEditBean.class);

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
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

    public void AddItemAction(ActionEvent actionEvent) {
        OperationBinding addItmOp = getBindings().getOperationBinding("addItmFormMrNTable");
        addItmOp.execute();
       
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


    public String createMrn() {
        setMode("A");
        return "createMrn";
    }

    public void editAction(ActionEvent actionEvent) {
        Integer sloc_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        int userId = 0;
        OperationBinding check = getBindings().getOperationBinding("pendingCheck");
        check.getParamsMap().put("SlocId", sloc_Id);
        check.getParamsMap().put("CldId", cld_Id);
        check.getParamsMap().put("OrgId", org_Id);
        check.getParamsMap().put("DocNo", 18528);
        check.execute();

        if (check.getErrors().isEmpty()) {
            userId = Integer.parseInt(check.getResult().toString());
            System.out.println("userId function " + userId);
        }
        if (usr_Id == userId || userId == -1) {
            System.out.println("user_ id" + userId);
            setMode("A");
        } else {
            //String name = "Anonymous";
            String name = resolvElDCMsg("#{bundle['MSG.2402']}").toString();

            OperationBinding getusrname = getBindings().getOperationBinding("getUsrNm");
            getusrname.getParamsMap().put("usrId", userId);
            getusrname.execute();
            if (getusrname.getErrors().size() == 0 && getusrname.getResult() != null)
                name = (String) getusrname.getResult();
            //String msg = "This MRN is Pending at [";
            String msg1 = resolvElDCMsg("#{bundle['MSG.2489']}").toString();
            //String msg2 = "]. You can't Edit this MRN.";
            String msg2 = resolvElDCMsg("#{bundle['MSG.2590']}").toString();
            String msg = msg1 + name + msg2;
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }

    }

    public void cancelAction(ActionEvent actionEvent) {
        setMode("V");
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public String cancel() {
        OperationBinding rollbk = getBindings().getOperationBinding("Rollback");
        rollbk.execute();
        setMode(" ");
        return "back";
    }

    public void addItmStkAction(ActionEvent actionEvent) {
        // OperationBinding srFilter = getBindings().getOperationBinding("filterSrNo");
        // srFilter.execute();
        OperationBinding itmStkOp = getBindings().getOperationBinding("Createwithparameters");
        itmStkOp.execute();
        
        if(srTransBind.getValue() != null && srTransBind.getValue().toString().equalsIgnoreCase("Y")){
            adflog.info("-------------geting sr flg bind-- "+srTransBind.getValue());
            
            OperationBinding setRtnQty = this.getBindings().getOperationBinding("settingRtnQty");
            setRtnQty.getParamsMap().put("srFlg", srTransBind.getValue().toString());
            setRtnQty.execute();
        }

    }

    public void saveAction(ActionEvent actionEvent) {
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String p_org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        int p_userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String tableNm = "MM$MRN"; //saveFwdActn1
        // for FyId
        if (mrnDateBind.getValue() != null) {
            Integer fyNo = 0;
            OperationBinding fyIdOp = getBindings().getOperationBinding("getFYid");
            fyIdOp.getParamsMap().put("CldId", p_cldId);
            fyIdOp.getParamsMap().put("OrgId", p_org_id);
            fyIdOp.getParamsMap().put("geDate", (Timestamp) mrnDateBind.getValue());
            fyIdOp.getParamsMap().put("Mode", "A");
            fyIdOp.execute();
            if (fyIdOp.getResult() != null) {
                fyNo = Integer.parseInt(fyIdOp.getResult().toString());
            }
            // adflog.info("Fy id in bean ---------"+fyNo);
            OperationBinding mrnNoOp = getBindings().getOperationBinding("generateMRNNo");
            mrnNoOp.getParamsMap().put("SlocId", p_sloc_id);
            mrnNoOp.getParamsMap().put("CldId", p_cldId);
            mrnNoOp.getParamsMap().put("OrgId", p_org_id);
            mrnNoOp.getParamsMap().put("TableName", tableNm);
            mrnNoOp.getParamsMap().put("fyId", fyNo);
            mrnNoOp.execute();
            String ids = null;
            if (mrnNoOp.getResult() != null) {
                ids = mrnNoOp.getResult().toString();
                //  adflog.info("new generated issue id "+ids);
            }

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
            WfnoOp.getParamsMap().put("DocNo", 18528);
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
                usrLevelOp.getParamsMap().put("DocNo", 18528);
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
                    insTxnOp.getParamsMap().put("DocNo", 18528);
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
                    OperationBinding locSr = getBindings().getOperationBinding("Execute");
                    locSr.execute();
                    setMode("V");
                    //String msg = "MRN No ";
                    String msg = resolvElDCMsg("#{bundle['MSG.725']}").toString();
                    //String msg1= "Saved Successfully.";
                    String msg1 = resolvElDCMsg("#{bundle['MSG.227']}").toString();
                    showFacesMessage(msg + ids + msg1, "I", false, "F", null);
                } else {
                    //String msg1= "Something went wrong(User Level in Workflow).Please Contact to ESS!";
                    String msg1 = resolvElDCMsg("#{bundle['MSG.1875']}").toString();
                    ;
                    showFacesMessage(msg1, "E", false, "F", null);

                }
            } else {
                //String msg1= "Workflow is not created for MRN.";
                String msg1 = resolvElDCMsg("#{bundle['MSG.2591']}").toString();
                ;
                showFacesMessage(msg1, "E", false, "F", null);

            }
        }
    }

    public void itmNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String flagVal = "N";
            String itmName = (String) object;
            adflog.info("arg0  " + itmName.length());
            if (itmName.length() > 0) {
                adflog.info("arg0  " + itmName);
                OperationBinding itmDupOp = getBindings().getOperationBinding("isItmNameDuplicate");
                itmDupOp.getParamsMap().put("value", itmName);
                itmDupOp.execute();
                adflog.info("flagVal   " + itmDupOp.getResult());
                if (itmDupOp.getResult() != null) {
                    flagVal = itmDupOp.getResult().toString();
                }
                if ("Y".equalsIgnoreCase(flagVal)) {
                    adflog.info(" inside validator   :::::  ");
                    //String msg1= "Duplicate Item Name Found";
                    String msg1 = resolvElDCMsg("#{bundle['MSG.2481']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
                }
            }
        }
    }

    public void srNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {

            String flagVal = "N";
            String itmName = (String) object;
            adflog.info("arg03323  :" + itmName.length());
            if (itmName.length() > 0) {
                if ("0".equalsIgnoreCase(itmName)) {
                    adflog.info("arg03323  id zero:");
                } else {
                    OperationBinding srDupOp = getBindings().getOperationBinding("isSrNoDuplicate");
                    srDupOp.getParamsMap().put("value", itmName);
                    srDupOp.execute();
                    adflog.info("SR no return " + srDupOp.getResult());
                    if (srDupOp.getResult() != null) {
                        flagVal = srDupOp.getResult().toString();
                    }
                    if ("Y".equalsIgnoreCase(flagVal)) {
                        //String msg1= "Duplicate Sr. No. Found";
                        String msg1 = resolvElDCMsg("#{bundle['MSG.2592']}").toString();
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
                    }
                }
            }
        }
    }

    public void okQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number value = (Number) object;
        if (value != null && getTotIssQtyBind().getValue() != null) {
            Number totQty = (Number) getTotIssQtyBind().getValue();
            Number totInspctnQty =
                ((Number) getRejQtyBind().getValue()).add((Number) getRwrkQtyBind().getValue()).add(value).add((Number) getScrapQtyBind().getValue());
            adflog.info("totQty---" + totQty + " totInspctnQty----------- " + totInspctnQty + "-- value -------" +
                        value);
            if (value.compareTo(zero) == -1) {
                //String msg1= "Quantity must be positive value.";
                String msg1 = resolvElDCMsg("#{bundle['MSG.767']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
            } else if (totInspctnQty.compareTo(totQty) == 1) {
                //String msg1= "Quantity exceeded Total Issue Quantity.";
                String msg1 = resolvElDCMsg("#{bundle['MSG.768']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
            }
        }
    }

    public void rejQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number value = (Number) object;
        if (value != null && getTotIssQtyBind().getValue() != null) {
            Number totQty = (Number) getTotIssQtyBind().getValue();
            Number totInspctnQty =
                ((Number) getOkQtyBind().getValue()).add((Number) getRwrkQtyBind().getValue()).add(value).add((Number) getScrapQtyBind().getValue());
            adflog.info("totQty---" + totQty + " totInspctnQty----------- " + totInspctnQty + "-- value -------" +
                        value);
            if (value.compareTo(zero) == -1) {
                //String msg1= "Quantity must be positive value.";
                String msg1 = resolvElDCMsg("#{bundle['MSG.767']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
            } else if (totInspctnQty.compareTo(totQty) == 1) {
                //String msg1= "Quantity exceeded Total Issue Quantity.";
                String msg1 = resolvElDCMsg("#{bundle['MSG.768']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
            }
        }
    }

    public void rwrkQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number value = (Number) object;
        if (value != null && getTotIssQtyBind().getValue() != null) {
            Number totQty = (Number) getTotIssQtyBind().getValue();
            Number totInspctnQty =
                ((Number) getRejQtyBind().getValue()).add((Number) getOkQtyBind().getValue()).add(value).add((Number) getScrapQtyBind().getValue());
            adflog.info("totQty---" + totQty + " totInspctnQty----------- " + totInspctnQty + "-- value -------" +
                        value);
            if (value.compareTo(zero) == -1) {
                //String msg1= "Quantity must be positive value.";
                String msg1 = resolvElDCMsg("#{bundle['MSG.767']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
            } else if (totInspctnQty.compareTo(totQty) == 1) {
                //String msg1= "Quantity exceeded Total Issue Quantity.";
                String msg1 = resolvElDCMsg("#{bundle['MSG.768']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
            }
        }
    }

    public void scrapQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number value = (Number) object;
        if (value != null && getTotIssQtyBind().getValue() != null) {
            Number totQty = (Number) getTotIssQtyBind().getValue();
            Number totInspctnQty =
                ((Number) getRejQtyBind().getValue()).add((Number) getRwrkQtyBind().getValue()).add(value).add((Number) getOkQtyBind().getValue());
            adflog.info("totQty---" + totQty + " totInspctnQty----------- " + totInspctnQty + "-- value -------" +
                        value);
            if (value.compareTo(zero) == -1) {
                //String msg1= "Quantity must be positive value.";
                String msg1 = resolvElDCMsg("#{bundle['MSG.767']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
            } else if (totInspctnQty.compareTo(totQty) == 1) {
                //String msg1= "Quantity exceeded Total Issue Quantity.";
                String msg1 = resolvElDCMsg("#{bundle['MSG.768']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
            }
        }
    }

    public void setOkQtyBind(RichInputText okQtyBind) {
        this.okQtyBind = okQtyBind;
    }

    public RichInputText getOkQtyBind() {
        return okQtyBind;
    }

    public void setRejQtyBind(RichInputText rejQtyBind) {
        this.rejQtyBind = rejQtyBind;
    }

    public RichInputText getRejQtyBind() {
        return rejQtyBind;
    }

    public void setRwrkQtyBind(RichInputText rwrkQtyBind) {
        this.rwrkQtyBind = rwrkQtyBind;
    }

    public RichInputText getRwrkQtyBind() {
        return rwrkQtyBind;
    }

    public void setScrapQtyBind(RichInputText scrapQtyBind) {
        this.scrapQtyBind = scrapQtyBind;
    }

    public RichInputText getScrapQtyBind() {
        return scrapQtyBind;
    }

    public void setTotIssQtyBind(RichInputText totIssQtyBind) {
        this.totIssQtyBind = totIssQtyBind;
    }

    public RichInputText getTotIssQtyBind() {
        return totIssQtyBind;
    }

    public void lotNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String flagVal = "N";
            String lotNo = (String) object;
            adflog.info(" lot " + lotNo.length());
            if (lotNo.length() > 0) {
                OperationBinding srDupOp = getBindings().getOperationBinding("isLotNoDuplicate");
                srDupOp.getParamsMap().put("value", lotNo);
                srDupOp.execute();
                if (srDupOp.getResult() != null) {
                    flagVal = srDupOp.getResult().toString();
                }
                if ("Y".equalsIgnoreCase(flagVal)) {
                    //String msg1= "Duplicate Lot No Found";
                    String msg1 = resolvElDCMsg("#{bundle['MSG.775']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
                }
            }
        }
    }

    public void setMrnDateBind(RichInputDate mrnDateBind) {
        this.mrnDateBind = mrnDateBind;
    }

    public RichInputDate getMrnDateBind() {
        return mrnDateBind;
    }

    public String modeGet() {
        return resolvEl("#{pageFlowScope.Add_Edit_Mode}").toString();
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


    public void mrnItmStkDelActionListen(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding srDupOp = getBindings().getOperationBinding("mrnItmStkRmv");
        srDupOp.execute();

    }

    public void setSrTransBind(RichInputText srTransBind) {
        this.srTransBind = srTransBind;
    }

    public RichInputText getSrTransBind() {
        return srTransBind;
    }

    public void issueNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            adflog.info("isss no " + object.toString().length());
            OperationBinding issueDup = getBindings().getOperationBinding("issueDupValid");
            issueDup.getParamsMap().put("issueno", object);
            issueDup.execute();
            String flag = "N";
            if (issueDup.getErrors().isEmpty()) {
                flag = issueDup.getResult().toString();
                System.out.println("issue no result" + flag);
            }
            if (flag.equalsIgnoreCase("Y")) {
                System.out.println("inside validator" + flag);
                //String msg1= "Duplicate Issue No. Found";
                String msg1 = resolvElDCMsg("#{bundle['MSG.778']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
            } else {

            }

        }
    }

    public String saveFwdActn1() {
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
        check.getParamsMap().put("DocNo", 18528);
        check.execute();
        adflog.info(" Step ::::::::::::::1");
        if (check.getErrors().isEmpty()) {
            userId = Integer.parseInt(check.getResult().toString());
            System.out.println("userId function " + userId);
        }
        if (usr_Id == userId || userId == -1) {
            System.out.println("user_ id" + userId);
            //  setMode("A");
            String tableNm = "MM$MRN";
            String ids = null;
            Integer fyNo = 0;
            adflog.info(" Step ::::::::::::::2");
            if (mrnDateBind.getValue() != null) {
                adflog.info(" Step ::::::::::::::3");
                OperationBinding fyIdOp = getBindings().getOperationBinding("getFYid");
                fyIdOp.getParamsMap().put("CldId", cld_Id);
                fyIdOp.getParamsMap().put("OrgId", org_Id);
                fyIdOp.getParamsMap().put("geDate", (Timestamp) mrnDateBind.getValue());
                fyIdOp.getParamsMap().put("Mode", "A");
                fyIdOp.execute();
                if (fyIdOp.getResult() != null) {
                    fyNo = Integer.parseInt(fyIdOp.getResult().toString());
                }
                //   adflog.info("Fy id in bean ---------"+fyNo);
                OperationBinding mrnNoOp = getBindings().getOperationBinding("generateMRNNo");
                mrnNoOp.getParamsMap().put("SlocId", sloc_Id);
                mrnNoOp.getParamsMap().put("CldId", cld_Id);
                mrnNoOp.getParamsMap().put("OrgId", org_Id);
                mrnNoOp.getParamsMap().put("TableName", tableNm);
                mrnNoOp.getParamsMap().put("fyId", fyNo);
                mrnNoOp.execute();

                if (mrnNoOp.getResult() != null) {
                    ids = mrnNoOp.getResult().toString();
                    //   adflog.info("new generated issue id "+ids);
                }

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
                WfnoOp.getParamsMap().put("DocNo", 18528);
                WfnoOp.execute();
                if (WfnoOp.getResult() != null) {
                    if (WfnoOp.getResult() != null)
                        WfNum = WfnoOp.getResult().toString();
                    System.out.println("WfnoOp : " + WfNum);
                }

                if (WfNum != null && !"0".equalsIgnoreCase(WfNum)) {

                    adflog.info(" Step ::::::::::::::4");
                    //get user level
                    OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                    usrLevelOp.getParamsMap().put("SlocId", sloc_Id);
                    usrLevelOp.getParamsMap().put("CldId", cld_Id);
                    usrLevelOp.getParamsMap().put("OrgId", org_Id);
                    usrLevelOp.getParamsMap().put("UsrId", usr_Id);
                    usrLevelOp.getParamsMap().put("WfNo", WfNum);
                    usrLevelOp.getParamsMap().put("DocNo", 18528);
                    usrLevelOp.execute();
                    level = -1;
                    if (usrLevelOp.getResult() != null) {
                        level = Integer.parseInt(usrLevelOp.getResult().toString());
                        System.out.println("user level " + level);
                    }

                    if (level >= 0) {
                        adflog.info(" Step ::::::::::::::5");
                        //insert into txn
                        OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                        insTxnOp.getParamsMap().put("SlocId", sloc_Id);
                        insTxnOp.getParamsMap().put("CldId", cld_Id);
                        insTxnOp.getParamsMap().put("OrgId", org_Id);
                        insTxnOp.getParamsMap().put("DocNo", 18528);
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
                        chkUsr.getParamsMap().put("DocNo", 18528);
                        chkUsr.execute();

                        if (chkUsr.getResult() != null) {
                            pending = Integer.parseInt(chkUsr.getResult().toString());
                            System.out.println("pending check" + pending);
                        }
                        if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(usr_Id) == 0) {
                            OperationBinding saveOp = getBindings().getOperationBinding("Commit");
                            saveOp.execute();
                            setMode("V");
                            adflog.info(" Step ::::::::::::::6");
                            //showFacesMessage("MRN No "+ids+" Save Successfully", "I", false, "F", null);
                            //no pending
                            return "towf";
                        } else { //pending
                            adflog.info(" Step ::::::::::::::7");
                            return null;
                        }
                    } else {
                        //String msg1= "Something went wrong(User Level in Workflow).Please Contact to ESS!";
                        String msg1 = resolvElDCMsg("#{bundle['MSG.1875']}").toString();
                        showFacesMessage(msg1, "E", false, "F", null);
                        return null;
                    }
                } else {
                    //String msg1= "Workflow not Created for MRN";
                    String msg1 = resolvElDCMsg("#{bundle['MSG.2593']}").toString();
                    showFacesMessage(msg1, "E", false, "F", null);
                    return null;
                }
            } else { //no getdate bind
                return null;
            }
        } else {
            //String name = "Anonymous";
            String name = resolvElDCMsg("#{bundle['MSG.2402']}").toString();
            OperationBinding getusrname = getBindings().getOperationBinding("getUsrNm");
            getusrname.getParamsMap().put("usrId", userId);
            getusrname.execute();
            if (getusrname.getErrors().size() == 0 && getusrname.getResult() != null)
                name = (String) getusrname.getResult();
            //String msg1 = "This MRN is Pending at [";
            String msg1 = resolvElDCMsg("#{bundle['MSG.2489']}").toString();
            //String msg2 = "]. You can't Edit this MRN.";
            String msg2 = resolvElDCMsg("#{bundle['MSG.2590']}").toString();
            String msg = msg1 + name + msg2;


            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return null;
        }
        // return null;
    }

    public void lotQtyValuechangeListen(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        OperationBinding srDupOp = getBindings().getOperationBinding("setTrasTotStkValue");
        srDupOp.execute();

    }
}
