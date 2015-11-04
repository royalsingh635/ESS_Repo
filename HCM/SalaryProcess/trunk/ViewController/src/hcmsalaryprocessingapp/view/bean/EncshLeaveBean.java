package hcmsalaryprocessingapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

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

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.OperationBinding;

public class EncshLeaveBean {
    private RichInputListOfValues empNm;
    private String mode = "V";
    private RichInputText encshLeave;
    private RichInputDate encashDt;
    private RichSelectOneChoice levTyp;
    private RichInputText ernLeav;
    private String childMode = "D";
    private RichInputText totalAmount;

    public void setChildMode(String childMode) {
        this.childMode = childMode;
    }

    public String getChildMode() {
        return childMode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public EncshLeaveBean() {
    }

    public void searchEmpEncashLeave(ActionEvent actionEvent) {
        OperationBinding op = ADFBeanUtils.getOperationBinding("searchEmpEncashLeaveAction");
        op.execute();
    }

    public void resetEmpEncshLeave(ActionEvent actionEvent) {
        OperationBinding op = ADFBeanUtils.getOperationBinding("resetEmpEncashLeaveAction");
        op.execute();
    }

    public void addAction(ActionEvent actionEvent) {
        this.mode = "E";
        OperationBinding op = ADFBeanUtils.getOperationBinding("CreateInsert");
        op.execute();
    }

    public void deleteEncashLeaveAction(ActionEvent actionEvent) {
        this.mode = "E";
        OperationBinding op = ADFBeanUtils.getOperationBinding("Delete");
        op.execute();
        setChildMode("Del");
        try {
            OperationBinding dele = ADFBeanUtils.getOperationBinding("insertTotAmt");
            dele.getParamsMap().put("val", new oracle.jbo.domain.Number(totalAmount.getValue().toString()));
            dele.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void cancelAction(ActionEvent actionEvent) {
        this.mode = "V";
        OperationBinding op = ADFBeanUtils.getOperationBinding("Rollback");
        op.execute();
    }

    public void saveAction(ActionEvent actionEvent) {
        if (chkEncashValidation()) {
            this.mode = "V";
            String message = ADFModelUtils.resolvRsrc("MSG.1907");                 //Record Saved Successfully
            showMessage(message, null);
            OperationBinding op = ADFBeanUtils.getOperationBinding("Commit");
            op.execute();
            String action = "I";
            String remark = "A";
            String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());

            OperationBinding WfnoOp = ADFBeanUtils.getOperationBinding("getWfNo");
            WfnoOp.getParamsMap().put("sloc_id", sloc_id);
            WfnoOp.getParamsMap().put("cld_id", cld_id);
            WfnoOp.getParamsMap().put("org_id", pOrgId);
            WfnoOp.getParamsMap().put("doc_no", 29506);
            WfnoOp.execute();

            String WfNum = null;
            Integer level = 0;
            Integer res = -1;

            if (WfnoOp.getResult() != null) {
                WfNum = WfnoOp.getResult().toString();
            }
            if (WfNum != null) {
                OperationBinding usrLevelOp = ADFBeanUtils.getOperationBinding("getUsrLvl");
                usrLevelOp.getParamsMap().put("SlocId", sloc_id);
                usrLevelOp.getParamsMap().put("CldId", cld_id);
                usrLevelOp.getParamsMap().put("OrgId", pOrgId);
                usrLevelOp.getParamsMap().put("usr_id", usr_id);
                usrLevelOp.getParamsMap().put("WfNum", WfNum);
                usrLevelOp.getParamsMap().put("DocNo", 29506);
                usrLevelOp.execute();
                level = -1;
                if (usrLevelOp.getResult() != null) {
                    if (usrLevelOp.getResult() != null)
                        level = Integer.parseInt(usrLevelOp.getResult().toString());
                }
                if (level == -1) {
                    FacesMessage msg =
                        new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2011"));  //Something went wrong while getting Workflow Level of this user.Contact to ESS.
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext f = FacesContext.getCurrentInstance();
                    f.addMessage(null, msg);
                } else {
                    OperationBinding insTxnOp = ADFBeanUtils.getOperationBinding("insIntoTxnEncash");
                    insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                    insTxnOp.getParamsMap().put("cld_id", cld_id);
                    insTxnOp.getParamsMap().put("pOrgId", pOrgId);
                    insTxnOp.getParamsMap().put("DOC_NO", 29506);
                    insTxnOp.getParamsMap().put("WfNum", WfNum);
                    insTxnOp.getParamsMap().put("poDocId", null);
                    insTxnOp.getParamsMap().put("usr_idFrm", usr_id);
                    insTxnOp.getParamsMap().put("usr_idTo", usr_id);
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
                        OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("Commit");
                        operationBinding.execute();

                    } else {
                        System.out.println("error during insert to WF");
                    }
                    System.out.println(level + "level--res" + res);
                }
            } else {
                //FacesMessage message1 = new FacesMessage("Workflow not Defined for this Document.");
                FacesMessage message1 = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1486']}").toString());
                message1.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc1 = FacesContext.getCurrentInstance();
                fc1.addMessage(null, message1);
            }
        }
    }

    public boolean chkEncashValidation() {
        String message = "";
        boolean result = true;
        if (empNm.getValue() == null || empNm.getValue() == "") {
            String cid = empNm.getClientId();
            message = ADFModelUtils.resolvRsrc("MSG.2737");   //Please select employee name.
            showErrorMessage(message, cid);
            return false;
        }
        if (chkPreviousDocPendingStatus()) {
        } else
            return false;


        if (encashDt.getValue() == null || encashDt.getValue() == "") {
            String cid = encashDt.getClientId();
            message = ADFModelUtils.resolvRsrc("MSG.2738");                //Please Enter Date.
            showErrorMessage(message, cid);
            return false;
        }
        DCIteratorBinding emmpLvEncshDCIt = ADFBeanUtils.findIterator("HcmEmpLeaveEncshDtl1Iterator");
        if (emmpLvEncshDCIt.getEstimatedRowCount() > 0) {
            return chkEncashLeavAllValidation();
        } else {
            message = ADFModelUtils.resolvRsrc("MSG.2739");       //Please add encash leave.
            showMessage(message, null);
            return false;
        }

    }

    public boolean chkPreviousDocPendingStatus() {
        OperationBinding op = ADFBeanUtils.getOperationBinding("chkPrevPendingDocStratus");
        op.execute();
        if (op.getErrors().isEmpty() && op.getResult() != null) {
            if (op.getResult().toString().equals("Y")) {

                String message = ADFModelUtils.resolvRsrc("MSG.2740");     //Already Pending document found: can't create new Documnet.
                showMessage(message, null);
                return false;
            }
        }
        return true;
    }

    public boolean chkEncashLeavAllValidation() {
        String message = "";
        if (levTyp.getValue() == null || levTyp.getValue() == "") {
            String cid = levTyp.getClientId();
            message = ADFModelUtils.resolvRsrc("MSG.2741");              //Please Select Leave Type.
            showErrorMessage(message, cid);
            return false;
        }
        if (encshLeave.getValue() == null || encshLeave.getValue() == "") {
            String cid = encshLeave.getClientId();
            message = ADFModelUtils.resolvRsrc("MSG.2742");             //Please Enter Encash Leave.
            showErrorMessage(message, cid);
            return false;
        }
        if (Integer.parseInt(ernLeav.getValue().toString()) == 0) {
            String cid = ernLeav.getClientId();
            message = ADFModelUtils.resolvRsrc("MSG.2743");        //Leave Balance must be greater than 0 to Encash
            showErrorMessage(message, null);
            return false;
        }

        int levVal = Integer.parseInt(encshLeave.getValue().toString());
        if (levVal <= 0 || levVal > Integer.parseInt(ernLeav.getValue().toString())) {
            if (levVal == 0) {
                message = ADFModelUtils.resolvRsrc("MSG.2744");           //Leave encash should be greater than zero
            } else {
                message =
                    ADFModelUtils.resolvRsrc("MSG.2745") + ADFModelUtils.resolvRsrc("MSG.2746")+ ADFModelUtils.resolvRsrc("MSG.2748") +          //Leave encash should be less than   //Employee Balance  //(
                    Integer.parseInt(ernLeav.getValue().toString()) + ADFModelUtils.resolvRsrc("MSG.2749");          //)
            }
            String cid = encshLeave.getClientId();
            showErrorMessage(message, cid);
            return false;
        }

        return true;
    }

    public String showMessage(String message, String cid) {
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(cid, fm);
        return null;
    }

    public String showErrorMessage(String message, String cid) {
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(cid, fm);
        return null;
    }

    public void setEmpNm(RichInputListOfValues empNm) {
        this.empNm = empNm;
    }

    public RichInputListOfValues getEmpNm() {
        return empNm;
    }

    public void selectEmpVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (valueChangeEvent.getNewValue() != null) {
            OperationBinding op = ADFBeanUtils.getOperationBinding("chkPrevPendingDocStratus");
            op.execute();
            if (op.getErrors().isEmpty() && op.getResult() != null) {
                if (op.getResult().toString().equals("Y")) {
                    String msg = "MSG.1386";
                    showFacesMessage(msg, "E", false, "V");
                    String message = ADFModelUtils.resolvRsrc("MSG.2740");   //Already Pending document found: can't create new Documnet.
                    String cid = encshLeave.getClientId();
                    showErrorMessage(message, cid);
                }
            }
        }
    }

    public void editAction(ActionEvent actionEvent) {
        this.mode = "E";
    }

    public void setEncshLeave(RichInputText encshLeave) {
        this.encshLeave = encshLeave;
    }

    public RichInputText getEncshLeave() {
        return encshLeave;
    }

    public void setEncashDt(RichInputDate encashDt) {
        this.encashDt = encashDt;
    }

    public RichInputDate getEncashDt() {
        return encashDt;
    }

    public void childCreateAction(ActionEvent actionEvent) {
        OperationBinding op = ADFBeanUtils.getOperationBinding("CreateInsert1");
        op.execute();
        setChildMode("E");
    }

    public void calTotSalaryVCL(ValueChangeEvent valueChangeEvent) {
        OperationBinding op = ADFBeanUtils.getOperationBinding("totSalaryAmt");
        System.out.println(valueChangeEvent.getNewValue());
        System.out.println(valueChangeEvent.getOldValue());
        op.getParamsMap().put("days",
                              valueChangeEvent.getNewValue() != null ? valueChangeEvent.getNewValue().toString() :
                              valueChangeEvent.getOldValue());
        op.execute();
    }

    public void setLevTyp(RichSelectOneChoice levTyp) {
        this.levTyp = levTyp;
    }

    public RichSelectOneChoice getLevTyp() {
        return levTyp;
    }

    public void setErnLeav(RichInputText ernLeav) {
        this.ernLeav = ernLeav;
    }

    public RichInputText getErnLeav() {
        return ernLeav;
    }

    public String saveAndFwdAction() {
        if (chkEncashValidation()) {
            this.mode = "V";
            //String message = "Record Saved Successfully";
            //showMessage(message, null);
            ADFBeanUtils.getOperationBinding("Commit").execute();
            String mssg = "";
            //check for pending
            String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
            Integer pending = null;

            OperationBinding chkUsr = ADFBeanUtils.getOperationBinding("pendingCheckEncash");
            chkUsr.getParamsMap().put("SlocId", sloc_id);
            chkUsr.getParamsMap().put("CldId", cld_id);
            chkUsr.getParamsMap().put("OrgId", pOrgId);
            chkUsr.getParamsMap().put("DocNo", 29506);
            chkUsr.execute();

            if (chkUsr.getResult() != null) {
                pending = Integer.parseInt(chkUsr.getResult().toString());
            }
            System.out.println("Pending at=" + pending);
            if (pending.compareTo(usr_id) != 0 && pending.compareTo(-1) != 0) {
                OperationBinding gtUsrNm = ADFBeanUtils.getOperationBinding("getUsrName");
                gtUsrNm.getParamsMap().put("usrId", pending);
                gtUsrNm.execute();
                StringBuffer usrName = new StringBuffer("Anonymous");
                if (gtUsrNm.getResult() != null)
                    usrName = new StringBuffer("[").append(usrName).append("]");
                // String msg2 =
                //"Salary is pending at other user " + usrName + " for approval, You cannot Forward/Approve this.";
                String msg2 =
                    resolvElDCMsg("#{bundle['MSG.1493']}").toString() + usrName +
                    resolvElDCMsg("#{bundle['MSG.1496']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            } else {

                String action = "I";
                String remark = "A";

                OperationBinding WfnoOp = ADFBeanUtils.getOperationBinding("getWfNo");
                WfnoOp.getParamsMap().put("sloc_id", sloc_id);
                WfnoOp.getParamsMap().put("cld_id", cld_id);
                WfnoOp.getParamsMap().put("org_id", pOrgId);
                WfnoOp.getParamsMap().put("doc_no", 29506);
                WfnoOp.execute();

                String WfNum = null;
                Integer level = 0;
                Integer res = -1;

                if (WfnoOp.getResult() != null) {
                    WfNum = WfnoOp.getResult().toString();
                }
                if (WfNum != null) {
                    OperationBinding usrLevelOp = ADFBeanUtils.getOperationBinding("getUsrLvl");
                    usrLevelOp.getParamsMap().put("SlocId", sloc_id);
                    usrLevelOp.getParamsMap().put("CldId", cld_id);
                    usrLevelOp.getParamsMap().put("OrgId", pOrgId);
                    usrLevelOp.getParamsMap().put("usr_id", usr_id);
                    usrLevelOp.getParamsMap().put("WfNum", WfNum);
                    usrLevelOp.getParamsMap().put("DocNo", 29506);
                    usrLevelOp.execute();
                    level = -1;
                    System.out.println("user level at encash---->" + usrLevelOp.getResult());
                    if (usrLevelOp.getResult() != null) {
                        if (usrLevelOp.getResult() != null)
                            level = Integer.parseInt(usrLevelOp.getResult().toString());
                    }
                    if (level == -1) {
                        // FacesMessage message =
                        //new FacesMessage("Something went wrong while getting Workflow Level of this user.Contact to ESS.");
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1506']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else {
                        System.out.println("going to insert into wf");
                        OperationBinding insTxnOp = ADFBeanUtils.getOperationBinding("insIntoTxnEncash");
                        insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                        insTxnOp.getParamsMap().put("cld_id", cld_id);
                        insTxnOp.getParamsMap().put("pOrgId", pOrgId);
                        insTxnOp.getParamsMap().put("DOC_NO", 29506);
                        insTxnOp.getParamsMap().put("WfNum", WfNum);
                        insTxnOp.getParamsMap().put("poDocId", null);
                        insTxnOp.getParamsMap().put("usr_idFrm", usr_id);
                        insTxnOp.getParamsMap().put("usr_idTo", usr_id);
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
                            OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("Commit");
                            operationBinding.execute();
                            return "goToWf";

                        } else {
                            System.out.println("error during insert to WF");
                        }
                        System.out.println(level + "level--res" + res);
                    }
                } else {
                    //FacesMessage message = new FacesMessage("Workflow not Defined for this Document.");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1486']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            }
            return null;
        }
        return null;
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }

    public void levEnchValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String message = "";
        if (object == null || object == "") {
            String cid = encshLeave.getClientId();
            message = ADFModelUtils.resolvRsrc("MSG.2742");            //Please Enter Encash Leave.
            showMessage(message, cid);
        }

        oracle.jbo.domain.Number levVal = new oracle.jbo.domain.Number(0);
        levVal = (oracle.jbo.domain.Number) object;
        //int levVal = Integer.parseInt(object.toString());
        //        if (levVal <= 0) {
        //            String cid = ernLeav.getClientId();
        //            message = "Leave Balance must be grater than 0.";
        //            showMessage(message, null);
        //        }
        oracle.jbo.domain.Number ernLeavCnt = new oracle.jbo.domain.Number(0);
        if (ernLeav.getValue() != null)
            ernLeavCnt = (oracle.jbo.domain.Number) ernLeav.getValue();
        if (levVal.compareTo(0) <= 0 || levVal.compareTo(ernLeavCnt) > 0) {
            if (levVal.compareTo(0) <= 0) {
                message = ADFModelUtils.resolvRsrc("MSG.2744");      //Leave encash should be greater than zero
            } else {
                message =
                    ADFModelUtils.resolvRsrc("MSG.2745") + ADFModelUtils.resolvRsrc("MSG.2746") + ADFModelUtils.resolvRsrc("MSG.2748") +         //Leave encash should be less than       //Employee Balance  //(
                    Integer.parseInt(ernLeav.getValue().toString()) + ADFModelUtils.resolvRsrc("MSG.2749");             //)
            }
            String cid = encshLeave.getClientId();
            showErrorMessage(message, cid);
        }
    }

    public void empVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (valueChangeEvent.getNewValue() != null) {

        }

    }

    public void editEncashLeavesActionListner(ActionEvent actionEvent) {
        setChildMode("E");
    }

    public void okAllValidationActionListners(ActionEvent actionEvent) {
        if (chkEncashLeavAllValidation()) {
            setChildMode("D");
            try {
                OperationBinding op = ADFBeanUtils.getOperationBinding("insertTotAmt");
                op.getParamsMap().put("val", new oracle.jbo.domain.Number(totalAmount.getValue().toString()));
                op.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void setTotalAmount(RichInputText totalAmount) {
        this.totalAmount = totalAmount;
    }

    public RichInputText getTotalAmount() {
        return totalAmount;
    }

    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg) {
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true) {
            String msg = (String) resolvEl("#{bundle['" + mesg + "']}");
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
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else if (typFlg.equals("V")) {
            throw new ValidatorException(message);
        }
    }

    public void leaveTypeVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            encshLeave.setValue(new oracle.jbo.domain.Number(0));
        }
    }
}
