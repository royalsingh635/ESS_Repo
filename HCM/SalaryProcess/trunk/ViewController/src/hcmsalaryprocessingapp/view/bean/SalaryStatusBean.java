package hcmsalaryprocessingapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.context.RequestContext;

public class SalaryStatusBean {
    public SalaryStatusBean() {
    }
    private String mode = "V";

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
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

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void editSalStatusAL(ActionEvent actionEvent) {
        //check for pending
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        Integer pending = null;

        OperationBinding chkUsr = ADFBeanUtils.getOperationBinding("pendingCheck");
        chkUsr.getParamsMap().put("SlocId", sloc_id);
        chkUsr.getParamsMap().put("CldId", cld_id);
        chkUsr.getParamsMap().put("OrgId", pOrgId);
        chkUsr.getParamsMap().put("DocNo", 29502);
        chkUsr.execute();

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
            System.out.println("Pending at=" + pending);
            if (pending.compareTo(-1) != 0 && pending.compareTo(usr_id) != 0) {
                OperationBinding gtUsrNm = ADFBeanUtils.getOperationBinding("getUsrName");
                gtUsrNm.getParamsMap().put("usrId", pending);
                gtUsrNm.execute();
                StringBuffer usrName = new StringBuffer("[Anonymous]");
                if (gtUsrNm.getResult() != null)
                    usrName = new StringBuffer("[").append(gtUsrNm.getResult().toString()).append("]");
                //String msg = "Salary is pending at other user [" + usrName + "] for approval, You cannot Edit this.";
                String msg =
                    resolvElDCMsg("#{bundle['MSG.1493']}").toString() + usrName +
                    resolvElDCMsg("#{bundle['MSG.1501']}").toString();
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                this.mode = "E";
            }
        }
    }

    public void saveSalStatusAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Commit").execute();
        //FacesMessage message = new FacesMessage("Status updated successfully.");
        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1483']}").toString());
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message);
        this.mode = "V";


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
        WfnoOp.getParamsMap().put("doc_no", 29502);
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
            usrLevelOp.getParamsMap().put("DocNo", 29502);
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
                OperationBinding insTxnOp = ADFBeanUtils.getOperationBinding("insIntoTxn");
                insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                insTxnOp.getParamsMap().put("cld_id", cld_id);
                insTxnOp.getParamsMap().put("pOrgId", pOrgId);
                insTxnOp.getParamsMap().put("DOC_NO", 29502);
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


    public String saveAndFwdAL() {
        ADFBeanUtils.getOperationBinding("Commit").execute();
        this.mode = "V";
        OperationBinding coaOp = ADFBeanUtils.getOperationBinding("chkGLCodeLnkValidation");
        coaOp.execute();
        if (coaOp.getErrors().isEmpty() && coaOp.getResult() != null) {
            Integer rslt = (Integer) coaOp.getResult();
            if (rslt.compareTo(new Integer(0)) == 0) {
                String mssg = "";
                //check for pending
                String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
                Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
                Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
                Integer pending = null;

                OperationBinding chkUsr = ADFBeanUtils.getOperationBinding("pendingCheck");
                chkUsr.getParamsMap().put("SlocId", sloc_id);
                chkUsr.getParamsMap().put("CldId", cld_id);
                chkUsr.getParamsMap().put("OrgId", pOrgId);
                chkUsr.getParamsMap().put("DocNo", 29502);
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
                    WfnoOp.getParamsMap().put("doc_no", 29502);
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
                        usrLevelOp.getParamsMap().put("DocNo", 29502);
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
                            insTxnOp.getParamsMap().put("DOC_NO", 29502);
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
            } else {
                StringBuilder msg = new StringBuilder("<html><body>");
                msg.append("<p style='color:navy'><b>Can not proceed to approve the salary!</b></p>");
                msg.append("<p style='color:green'><b>Chart of account has not been linked in the any/all of following.</b></p>");
                msg.append("<p style='color:Red'><b>1-Salary Component Setup</b></p>");
                msg.append("<p style='color:Red'><b>2-Deduction Setup</b></p>");
                msg.append("<p style='color:Red'><b>3-Group Profile Setup</b></p>");
                msg.append("<p style='color:Red'><b>4-Leave Detail Setup(In Case Of Encashment of Leave, COA is mandatory.)</b></p>");

                msg.append("</body></html>");
                FacesMessage message = new FacesMessage(msg.toString());
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);

            }
            return null;
        }
        return null;
    }

}
