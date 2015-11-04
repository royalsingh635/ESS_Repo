package hcmfullandfinalapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.ebiz.EbizParams;

import adf.utils.model.ADFModelUtils;

import java.sql.SQLException;

import java.util.Date;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import javax.faces.context.FacesContext;

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

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Timestamp;

import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.context.RequestContext;

public class FullAndFinalBean {
    private RichInputDate relevingDateBinding;
    private RichInputText noticePrdBinding;
    private RichInputDate resignDateBinding;
    private RichOutputText docIdBinding;
    private RichInputDate fnfDateBinding;
    private RichOutputText empDocIdBinding;
    private RichPopup pendingTaskPopBinding;
    private RichInputText pendingTaskChkBinding;
    private RichPopup exitPopBinding;
    private RichInputText exitChkBinding;


    public FullAndFinalBean() {
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

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void addNewFnFAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("CreateInsert").execute();
        ADFBeanUtils.getOperationBinding("insertIntoFNFandgGenrateDocId").execute();
        RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_MODE", "E");


    }

    public void editFnfDetailAL(ActionEvent actionEvent) {
        //check for pending
        String cld_id = EbizParams.GLBL_APP_CLD_ID();
        Integer sloc_id = EbizParams.GLBL_APP_SERV_LOC();
        String pOrgId = EbizParams.GLBL_APP_USR_ORG();
        Integer usr_id = EbizParams.GLBL_APP_USR();
        Integer pending = null;

        OperationBinding chkUsr = ADFBeanUtils.getOperationBinding("pendingCheck");
        chkUsr.getParamsMap().put("SlocId", sloc_id);
        chkUsr.getParamsMap().put("CldId", cld_id);
        chkUsr.getParamsMap().put("OrgId", pOrgId);
        chkUsr.getParamsMap().put("DocNo", 29504);
        chkUsr.execute();

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
            System.out.println("Pending at=" + pending);
            if (pending.compareTo(-1) != 0 && pending.compareTo(usr_id) != 0) {
                OperationBinding gtUsrNm = ADFBeanUtils.getOperationBinding("getUsrName");
                gtUsrNm.getParamsMap().put("usrId", pending);
                gtUsrNm.execute();
                StringBuffer usrName = new StringBuffer("Anonymous");
                if (gtUsrNm.getResult() != null)
                    usrName = new StringBuffer(gtUsrNm.getResult().toString());
                String msg1= ADFModelUtils.resolvRsrc("MSG.2632");      // Document is pending at other user [
                String msg2= ADFModelUtils.resolvRsrc("MSG.1501");   //] for approval, You cannot Edit this.
                String msg = msg1  + usrName + msg2;
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_MODE", "E");
            }
        }

    }

    public void saveFnfDetailAL(ActionEvent actionEvent) {
        boolean chk = validatebeforeProcess(actionEvent.getComponent().getId());
        if (chk) {
            RequestContext.getCurrentInstance().getPageFlowScope().put("PARAM_DOC_TXN_ID", docIdBinding.getValue());
            ADFBeanUtils.getOperationBinding("Commit").execute();
            FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.91"));  //Record saved successfully.
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_MODE", "V");

            String action = "I";
            String remark = "A";
            String cld_id = EbizParams.GLBL_APP_CLD_ID();
            Integer sloc_id = EbizParams.GLBL_APP_SERV_LOC();
            String pOrgId = EbizParams.GLBL_APP_USR_ORG();
            Integer usr_id = EbizParams.GLBL_APP_USR();

            OperationBinding WfnoOp = ADFBeanUtils.getOperationBinding("getWfNo");
            WfnoOp.getParamsMap().put("sloc_id", sloc_id);
            WfnoOp.getParamsMap().put("cld_id", cld_id);
            WfnoOp.getParamsMap().put("org_id", pOrgId);
            WfnoOp.getParamsMap().put("doc_no", 29504);
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
                usrLevelOp.getParamsMap().put("DocNo", 29504);
                usrLevelOp.execute();
                level = -1;
                if (usrLevelOp.getResult() != null) {
                    if (usrLevelOp.getResult() != null)
                        level = Integer.parseInt(usrLevelOp.getResult().toString());
                }
                if (level == -1) {
                    FacesMessage msg =
                        new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2011"));       //Something went wrong while getting Workflow Level of this user.Contact to ESS.
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext f = FacesContext.getCurrentInstance();
                    f.addMessage(null, msg);
                } else {
                    OperationBinding insTxnOp = ADFBeanUtils.getOperationBinding("insIntoTxn");
                    insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                    insTxnOp.getParamsMap().put("cld_id", cld_id);
                    insTxnOp.getParamsMap().put("pOrgId", pOrgId);
                    insTxnOp.getParamsMap().put("DOC_NO", 29504);
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


    public void cancelBtnAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Rollback").execute();
        RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_MODE", "V");

    }

    public void setRelevingDateBinding(RichInputDate relevingDateBinding) {
        this.relevingDateBinding = relevingDateBinding;
    }

    public RichInputDate getRelevingDateBinding() {
        return relevingDateBinding;
    }

    public void setNoticePrdBinding(RichInputText noticePrdBinding) {
        this.noticePrdBinding = noticePrdBinding;
    }

    public RichInputText getNoticePrdBinding() {
        return noticePrdBinding;
    }

    public void setResignDateBinding(RichInputDate resignDateBinding) {
        this.resignDateBinding = resignDateBinding;
    }

    public RichInputDate getResignDateBinding() {
        return resignDateBinding;
    }

    public void noticePeriodValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer not = (Integer) object;
            if (not.compareTo(new Integer(0)) < 0) {
                String msg = ADFModelUtils.resolvRsrc("MSG.1152");        //Negative value not allowed!
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }


    }

    public void noticePeriodVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && resignDateBinding.getValue() != null) {

            Integer notPrd = (Integer) valueChangeEvent.getNewValue();
            if (notPrd.compareTo(new Integer(0)) == 0) {
                relevingDateBinding.setValue(((Timestamp) resignDateBinding.getValue()));
                AdfFacesContext.getCurrentInstance().addPartialTarget(relevingDateBinding);
            } else {
                Timestamp resdt = (Timestamp) resignDateBinding.getValue();
                Date res = null;
                try {
                    res = resdt.dateValue();
                } catch (SQLException e) {
                }
                OperationBinding binding = ADFBeanUtils.getOperationBinding("calcRelvngDate");
                binding.getParamsMap().put("days", notPrd);
                binding.getParamsMap().put("resDt", res);
                binding.execute();
                System.out.println("result to set relvng date---" + binding.getResult());
                relevingDateBinding.setValue((Timestamp) binding.getResult());
            }
        }
    }

    public void processFnFAL(ActionEvent actionEvent) {

        boolean check = validatebeforeProcess(actionEvent.getComponent().getId());
        if (check) {
            OperationBinding binding = ADFBeanUtils.getOperationBinding("processFnf");
            binding.execute();
        }
    }


    public boolean validatebeforeProcess(String id) {
        if (noticePrdBinding.getValue() == null) {
            FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2634"));    //Notice period can not be blank !
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(noticePrdBinding.getClientId(), msg);
            return false;
        }
        if (fnfDateBinding.getValue() == null) {
            FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2635"));        //Full and Final date can not be left blank !
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(fnfDateBinding.getClientId(), msg);
            return false;
        }
        if (!id.equalsIgnoreCase("pendTaskBtn") && !id.equalsIgnoreCase("b2")) {
            if (pendingTaskChkBinding.getValue() != null &&
                pendingTaskChkBinding.getValue().toString().equalsIgnoreCase("Y")) {
                if (getDCIterarorBinding("HcmEmpFnfTask1Iterator").getEstimatedRowCount() == 0) {
                    FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2636"));       //Add pending tasks !
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, msg);
                    return false;
                }

                if (exitChkBinding.getValue() != null && exitChkBinding.getValue().toString().equalsIgnoreCase("Y")) {
                    if (getDCIterarorBinding("HcmEmpFnfExitQues1Iterator").getEstimatedRowCount() == 0) {
                        FacesMessage msg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2637"));    //Follow exit interview!
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext context = FacesContext.getCurrentInstance();
                        context.addMessage(null, msg);
                        return false;

                    }
                }
            }
        }
        return true;
    }

    public DCIteratorBinding getDCIterarorBinding(String itteratorName) {
        DCIteratorBinding dcIterator = ADFBeanUtils.findIterator(itteratorName);
        return dcIterator;
    }

    public String saveAndFwdAction() {
        boolean chk = validatebeforeProcess("saveAndFwd");
        if (chk) {
            ADFBeanUtils.getOperationBinding("Commit").execute();
            RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_MODE", "V");
            RequestContext.getCurrentInstance().getPageFlowScope().put("PARAM_DOC_TXN_ID", docIdBinding.getValue());
            String cld_id = EbizParams.GLBL_APP_CLD_ID();
            Integer sloc_id = EbizParams.GLBL_APP_SERV_LOC();
            String pOrgId = EbizParams.GLBL_APP_USR_ORG();
            Integer usr_id = EbizParams.GLBL_APP_USR();
            Integer pending = null;

            OperationBinding chkUsr = ADFBeanUtils.getOperationBinding("pendingCheck");
            chkUsr.getParamsMap().put("SlocId", sloc_id);
            chkUsr.getParamsMap().put("CldId", cld_id);
            chkUsr.getParamsMap().put("OrgId", pOrgId);
            chkUsr.getParamsMap().put("DocNo", 29504);
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
                WfnoOp.getParamsMap().put("doc_no", 29504);
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
                    usrLevelOp.getParamsMap().put("DocNo", 29504);
                    usrLevelOp.execute();
                    level = -1;
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
                        OperationBinding insTxnOp = ADFBeanUtils.getOperationBinding("insIntoTxn");
                        insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                        insTxnOp.getParamsMap().put("cld_id", cld_id);
                        insTxnOp.getParamsMap().put("pOrgId", pOrgId);
                        insTxnOp.getParamsMap().put("DOC_NO", 29504);
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

    public void setDocIdBinding(RichOutputText docIdBinding) {
        this.docIdBinding = docIdBinding;
    }

    public RichOutputText getDocIdBinding() {
        return docIdBinding;
    }

    public void empNmVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void setFnfDateBinding(RichInputDate fnfDateBinding) {
        this.fnfDateBinding = fnfDateBinding;
    }

    public RichInputDate getFnfDateBinding() {
        return fnfDateBinding;
    }

    public void recoveryAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number obj = (oracle.jbo.domain.Number) object;
            if (obj.compareTo(new Integer(0)) < 0) {
                String msg = ADFModelUtils.resolvRsrc("MSG.1152");           //Negative value not allowed!
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }

    public void otherEarningValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number obj = (oracle.jbo.domain.Number) object;
            if (obj.compareTo(new Integer(0)) < 0) {
                String msg = ADFModelUtils.resolvRsrc("MSG.1152");                 //Negative value not allowed!
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }

    public void empNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && empDocIdBinding.getValue() != null) {
            String empDoc = empDocIdBinding.getValue().toString();
            OperationBinding ob = ADFBeanUtils.getOperationBinding("validateEmpForFnF");
            ob.getParamsMap().put("docIdEmp", empDoc);
            ob.execute();
            if (ob.getErrors().isEmpty() && ob.getResult() != null) {
                String rslt = ob.getResult().toString();
                if (rslt.equalsIgnoreCase("Y")) {
                    String msg = ADFModelUtils.resolvRsrc("MSG.2639");      //Current Employee is not eligible for Full and Final Settlement!
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }
    }

    public void setEmpDocIdBinding(RichOutputText empDocIdBinding) {
        this.empDocIdBinding = empDocIdBinding;
    }

    public RichOutputText getEmpDocIdBinding() {
        return empDocIdBinding;
    }

    public void openPendingTaskPopAL(ActionEvent actionEvent) {
        boolean chk = validatebeforeProcess(actionEvent.getComponent().getId());
        if (chk) {
            showPopup(pendingTaskPopBinding, true);
        }
    }

    public void setPendingTaskPopBinding(RichPopup pendingTaskPopBinding) {
        this.pendingTaskPopBinding = pendingTaskPopBinding;
    }

    public RichPopup getPendingTaskPopBinding() {
        return pendingTaskPopBinding;
    }

    public void duplicateTaskValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String task = object.toString();
            OperationBinding binding = ADFBeanUtils.getOperationBinding("chkDuplicateTask");
            binding.getParamsMap().put("taskId", task);
            binding.execute();
            if (binding.getErrors().isEmpty() && binding.getResult() != null) {
                String rslt = binding.getResult().toString();
                if (rslt.equalsIgnoreCase("Y")) {
                    String msg = ADFModelUtils.resolvRsrc("MSG.2640");                //Duplicate task not allowed!
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }

    }

    public void addTaskAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("CreateInsert1").execute();
    }

    public void deleteTaskAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete").execute();
    }

    public void setPendingTaskChkBinding(RichInputText pendingTaskChkBinding) {
        this.pendingTaskChkBinding = pendingTaskChkBinding;
    }

    public RichInputText getPendingTaskChkBinding() {
        return pendingTaskChkBinding;
    }

    public void okLinkAtPopAL(ActionEvent actionEvent) {
        pendingTaskPopBinding.hide();
    }

    public void openExitInterViewPopAL(ActionEvent actionEvent) {
        boolean chk = validatebeforeProcess(actionEvent.getComponent().getId());
        if (chk) {
            showPopup(exitPopBinding, true);
        }
    }

    public void exitPopOkBtnAL(ActionEvent actionEvent) {
        exitPopBinding.hide();
    }

    public DnDAction quesDropListener(DropEvent dropEvent) {
        ADFBeanUtils.getOperationBinding("chkDuplicateQuesAndInsert").execute();
        return DnDAction.NONE;
    }

    public void setExitPopBinding(RichPopup exitPopBinding) {
        this.exitPopBinding = exitPopBinding;
    }

    public RichPopup getExitPopBinding() {
        return exitPopBinding;
    }

    public void deleteExitQuesAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete1").execute();
    }

    public void setExitChkBinding(RichInputText exitChkBinding) {
        this.exitChkBinding = exitChkBinding;
    }

    public RichInputText getExitChkBinding() {
        return exitChkBinding;
    }
}
