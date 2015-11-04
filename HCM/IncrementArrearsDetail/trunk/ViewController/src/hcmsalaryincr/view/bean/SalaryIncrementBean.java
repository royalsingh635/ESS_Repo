package hcmsalaryincr.view.bean;

import adf.utils.bean.ADFBeanUtils;


import java.math.BigDecimal;

import java.sql.SQLException;


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

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class SalaryIncrementBean
{
    private RichSelectOneChoice salId;
    private RichSelectOneChoice incrrmntTyp;
    private RichInputText incrmtAmnt;
    private RichSelectOneChoice refSlryCmpnent;
    private RichSelectOneChoice grpId;
    private RichInputDate incrmntDate;
    private RichInputDate applicblDate;
    private RichPopup addReffComponent;
    private RichSelectOneChoice salIdFrSumm;
    private RichSelectOneChoice incrmntTypeFrSumm;
    private RichInputText incrmntAmntFrSumm;
    private RichInputListOfValues empNM;
    private RichPopup showPrevComp;
    private RichPopup showPrevHiistCompfrmReff;
    private RichSelectBooleanCheckbox decrFlag;
    private RichInputText rcvryPrd;
    private RichInputText recvryFldChk;
    private static ADFLogger _logger = ADFLogger.createADFLogger(SalaryIncrementBean.class);
    private RichSelectOneChoice incrCriteraBinding;
    private RichSelectOneChoice grpSaliDBinding;
    private RichInputText grpIncrmntAmntBinding;
    private RichSelectOneChoice grpIncrmntTypeBinding;
    private String copyMode = "GroupComp";
    private RichSelectOneChoice dummyIncrTypeFrCpyBinding;
    private RichInputText dummyIncrAmtFrGrpBinding;
    private RichInputText grpSalAmtBinding;
    private RichPopup dojFilterPopPupBinding;
    private RichPopup groupIncrempinding;
    private RichPopup empGrpIdBinding;
    private RichSelectOneRadio subCriteriaBinding;

    public void setCopyMode(String copyMode)
    {
        this.copyMode = copyMode;
    }

    public String getCopyMode()
    {
        return copyMode;
    }

    public SalaryIncrementBean()
    {
    }
    public String localMode = "view";
    public String localModefrReff = "reffview";
    public boolean modeFrDecrFlag = false;


    public void setModeFrDecrFlag(boolean modeFrDecrFlag)
    {
        this.modeFrDecrFlag = modeFrDecrFlag;
    }

    public boolean isModeFrDecrFlag()
    {
        if (getDecrFlagVisisbleOrNot().equals("true"))
            return true;
        else
            return false;
    }

    public void setLocalModefrReff(String localModefrReff)
    {
        this.localModefrReff = localModefrReff;
    }

    public String getLocalModefrReff()
    {
        return localModefrReff;
    }

    public void setLocalMode(String localMode)
    {
        this.localMode = localMode;
    }

    public String getLocalMode()
    {
        return localMode;
    }

    public void addIncrement(ActionEvent actionEvent)
    {
        RequestContext context = RequestContext.getCurrentInstance();
        ADFBeanUtils.getOperationBinding("CreateInsert").execute();
        OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("generateDocId");
        operationBinding.execute();
        if (operationBinding.getErrors().isEmpty())
        {
            context.getPageFlowScope().put("ADD_EDIT_MODE", "S");
            OperationBinding opechk = ADFBeanUtils.getOperationBinding("SetPrevSlryCompInDummyVo");
            opechk.getParamsMap().put("type", "new");
            opechk.execute();
        }
        else
        {

            System.out.println("doc id not generaated");
        }

    }

    public void cancelIncrement(ActionEvent actionEvent)
    {
        OperationBinding ob = ADFBeanUtils.getOperationBinding("chkIncrId");
        ob.execute();
        if (ob.getErrors().isEmpty())
        {
            String chkIncrIdstatus = (String) ob.getResult();
            if (chkIncrIdstatus != "exist")
            {
                OperationBinding opechk = ADFBeanUtils.getOperationBinding("SetPrevSlryCompInDummyVo");
                opechk.getParamsMap().put("type", "cancel");
                opechk.execute();
            }
            else
            {
                System.out.println("already in progress increment");
            }

        }
        ADFBeanUtils.getOperationBinding("Rollback").execute();
        RequestContext context = RequestContext.getCurrentInstance();
        context.getPageFlowScope().put("ADD_EDIT_MODE", "D");

    }

    public void saveIncrment(ActionEvent actionEvent)
    {
        boolean status = chkSveIncrmtValidation();
        if (status)
        {
            OperationBinding opPrevIncrbin = ADFBeanUtils.getOperationBinding("getPrevIncrStatus");
            opPrevIncrbin.execute();
            if (opPrevIncrbin.getErrors().size() == 0 && opPrevIncrbin.getResult().toString().equals("Y"))
            {
                if (incrCriteraBinding.getValue() != null)
                {
                    String incCritearVal = (String) incrCriteraBinding.getValue();
                    if (incCritearVal.equals("G"))
                    {
                        saveIncrForGroup();
                    }
                    if (incCritearVal.equals("E"))
                    {
                        saveIncrForSingleEmployee();
                    }
                }
            }
            else
            {
                if (opPrevIncrbin.getErrors().isEmpty())
                {
                    String message = "MSG.1423";
                    showFacesMessage(message, "I", true, "F");
                    status = false;
                }
            }
        }


    }

    public void saveIncrForGroup()
    {
        String mssg = "";
        DCIteratorBinding difrSumm = ADFBeanUtils.findIterator("HCMIncrmntSummIterator");
        boolean status;
        if (difrSumm.getEstimatedRowCount() > 0)
        {
            status = chkValidFrNwSlryCmponentFrSummFrGrp();
        }
        else
        {
            String message = resolvElDCMsg("#{bundle['MSG.1422']}").toString();
            showMessage(message, null);
            status = false;
        }
        if (status == true)
        {
            String isOk = "Y";
            RequestContext context = RequestContext.getCurrentInstance();
            OperationBinding ob = ADFBeanUtils.getOperationBinding("chkIncrId");
            ob.execute();
            if (ob.getErrors().isEmpty())
            {
                String chkIncrIdstatus = (String) ob.getResult();
                if (chkIncrIdstatus != "exist")
                {
                    OperationBinding ob2 = ADFBeanUtils.getOperationBinding("generateIncrId");
                    ob2.execute();
                    if (ob2.getErrors().isEmpty())
                    {
                        isOk = "Y";
                    }
                    else
                    {
                        isOk = "N";
                    }
                }
                else
                {
                    isOk = "Y";
                }
            }
            else
            {
                isOk = "N";
            }

            if (isOk.equals("Y"))
            {
                OperationBinding ob3 = ADFBeanUtils.getOperationBinding("Commit");
                ob3.execute();
                OperationBinding opchk = ADFBeanUtils.getOperationBinding("ExecuteVoSummndReff");
                opchk.execute();
                mssg = "MSG.91";
                showFacesMessage(mssg, "I", true, "F");
                context.getPageFlowScope().put("ADD_EDIT_MODE", "D");
                setLocalMode("view");

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
                WfnoOp.getParamsMap().put("doc_no", 28511);
                WfnoOp.execute();

                String WfNum = null;
                Integer level = 0;
                Integer res = -1;

                if (WfnoOp.getResult() != null)
                {
                    WfNum = WfnoOp.getResult().toString();
                }
                if (WfNum != null)
                {
                    OperationBinding usrLevelOp = ADFBeanUtils.getOperationBinding("getUsrLvl");
                    usrLevelOp.getParamsMap().put("SlocId", sloc_id);
                    usrLevelOp.getParamsMap().put("CldId", cld_id);
                    usrLevelOp.getParamsMap().put("OrgId", pOrgId);
                    usrLevelOp.getParamsMap().put("usr_id", usr_id);
                    usrLevelOp.getParamsMap().put("WfNum", WfNum);
                    usrLevelOp.getParamsMap().put("DocNo", 28511);
                    usrLevelOp.execute();
                    level = -1;
                    if (usrLevelOp.getResult() != null)
                    {
                        if (usrLevelOp.getResult() != null)
                            level = Integer.parseInt(usrLevelOp.getResult().toString());
                    }
                    if (level == -1)
                    {
                        FacesMessage message =
                            new FacesMessage("Something went wrong while getting Workflow Level of this user.Contact to ESS.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                    else
                    {
                        OperationBinding insTxnOp = ADFBeanUtils.getOperationBinding("insIntoTxn");
                        insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                        insTxnOp.getParamsMap().put("cld_id", cld_id);
                        insTxnOp.getParamsMap().put("pOrgId", pOrgId);
                        insTxnOp.getParamsMap().put("DOC_NO", 28511);
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

                        if (insTxnOp.getResult() != null)
                        {
                            res = Integer.parseInt(insTxnOp.getResult().toString());
                        }
                        if (res == 1)
                        {
                            OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("Commit");
                            operationBinding.execute();
                            OperationBinding opchknew = ADFBeanUtils.getOperationBinding("ExecuteVoSummndReff");
                            opchknew.execute();

                        }
                        else
                        {
                            System.out.println("error during insert to WF");
                        }
                        System.out.println(level + "level--res" + res);
                    }
                }
                else
                {
                    FacesMessage message = new FacesMessage("Workflow not Defined for this Document.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }

            }
            else
            {
                mssg = "Some error occurs!!";
                showFacesMessage(mssg, "E", false, "F");
                context.getPageFlowScope().put("ADD_EDIT_MODE", "S");
            }
        }
    }

    public void saveIncrForSingleEmployee()
    {
        String mssg = "";
        DCIteratorBinding difrSumm = ADFBeanUtils.findIterator("HCMIncrmntSummIterator");
        boolean status;
        if (difrSumm.getEstimatedRowCount() > 0)
        {
            status = chkValidFrNwSlryCmponentFrSumm();
        }
        else
        {
            String message = resolvElDCMsg("#{bundle['MSG.1422']}").toString();
            showMessage(message, null);
            status = false;
        }
        if (status == true)
        {
            OperationBinding opbin = ADFBeanUtils.getOperationBinding("getStatusIsIncrOrDecr");
            opbin.execute();
            if (opbin.getErrors().isEmpty() && opbin.getResult() != "")
            {
                if (opbin.getResult().toString().equals("N"))
                {
                    if (recvryFldChk.getValue().equals("Y"))
                        if (rcvryPrd.getValue() == null)
                        {
                            String cid = rcvryPrd.getClientId();
                            showMessage("Please enter period for the payment of arrear amount.", cid);
                            status = false;
                        }
                }
                else if (opbin.getResult().toString().equals("Y"))
                {
                    if (recvryFldChk.getValue().equals("Y"))
                        if (rcvryPrd.getValue() == null)
                        {
                            String cid = rcvryPrd.getClientId();
                            showMessage("Please enter period for the recovery of arrear amount.", cid);
                            status = false;
                        }

                }
            }
        }
        if (status == true)
        {
            String isOk = "Y";
            RequestContext context = RequestContext.getCurrentInstance();
            OperationBinding ob = ADFBeanUtils.getOperationBinding("chkIncrId");
            ob.execute();
            if (ob.getErrors().isEmpty())
            {
                String chkIncrIdstatus = (String) ob.getResult();
                if (chkIncrIdstatus != "exist")
                {
                    OperationBinding ob2 = ADFBeanUtils.getOperationBinding("generateIncrId");
                    ob2.execute();
                    if (ob2.getErrors().isEmpty())
                    {
                        isOk = "Y";
                    }
                    else
                    {
                        isOk = "N";
                    }
                }
                else
                {
                    isOk = "Y";
                }
            }
            else
            {
                isOk = "N";
            }

            if (isOk.equals("Y"))
            {
                OperationBinding ob3 = ADFBeanUtils.getOperationBinding("Commit");
                ob3.execute();
                OperationBinding opchk = ADFBeanUtils.getOperationBinding("ExecuteVoSummndReff");
                opchk.execute();
                mssg = "MSG.91";
                showFacesMessage(mssg, "I", true, "F");
                context.getPageFlowScope().put("ADD_EDIT_MODE", "D");
                setLocalMode("view");

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
                WfnoOp.getParamsMap().put("doc_no", 28511);
                WfnoOp.execute();

                String WfNum = null;
                Integer level = 0;
                Integer res = -1;

                if (WfnoOp.getResult() != null)
                {
                    WfNum = WfnoOp.getResult().toString();
                }
                if (WfNum != null)
                {
                    OperationBinding usrLevelOp = ADFBeanUtils.getOperationBinding("getUsrLvl");
                    usrLevelOp.getParamsMap().put("SlocId", sloc_id);
                    usrLevelOp.getParamsMap().put("CldId", cld_id);
                    usrLevelOp.getParamsMap().put("OrgId", pOrgId);
                    usrLevelOp.getParamsMap().put("usr_id", usr_id);
                    usrLevelOp.getParamsMap().put("WfNum", WfNum);
                    usrLevelOp.getParamsMap().put("DocNo", 28511);
                    usrLevelOp.execute();
                    level = -1;
                    if (usrLevelOp.getResult() != null)
                    {
                        if (usrLevelOp.getResult() != null)
                            level = Integer.parseInt(usrLevelOp.getResult().toString());
                    }
                    if (level == -1)
                    {
                        FacesMessage message =
                            new FacesMessage("Something went wrong while getting Workflow Level of this user.Contact to ESS.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                    else
                    {
                        OperationBinding insTxnOp = ADFBeanUtils.getOperationBinding("insIntoTxn");
                        insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                        insTxnOp.getParamsMap().put("cld_id", cld_id);
                        insTxnOp.getParamsMap().put("pOrgId", pOrgId);
                        insTxnOp.getParamsMap().put("DOC_NO", 28511);
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

                        if (insTxnOp.getResult() != null)
                        {
                            res = Integer.parseInt(insTxnOp.getResult().toString());
                        }
                        if (res == 1)
                        {
                            OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("Commit");
                            operationBinding.execute();
                            OperationBinding opchknew = ADFBeanUtils.getOperationBinding("ExecuteVoSummndReff");
                            opchknew.execute();

                        }
                        else
                        {
                            System.out.println("error during insert to WF");
                        }
                        System.out.println(level + "level--res" + res);
                    }
                }
                else
                {
                    FacesMessage message = new FacesMessage("Workflow not Defined for this Document.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }

            }
            else
            {
                mssg = "Some error occurs!!";
                showFacesMessage(mssg, "E", false, "F");
                context.getPageFlowScope().put("ADD_EDIT_MODE", "S");
            }
        }
    }

    public boolean chkSveIncrmtValidation()
    {
        String message = "";
        boolean result = true;
        if (incrCriteraBinding.getValue() == null || incrCriteraBinding.getValue() == "")
        {
            String cid = incrCriteraBinding.getClientId();
            message = "Please select criteria for increment.";
            showMessage(message, cid);
            result = false;
        }
        if (incrCriteraBinding.getValue().equals("G"))
        {
            if (grpId.getValue() == null || grpId.getValue() == "")
            {
                String cid = grpId.getClientId();
                message = resolvElDCMsg("#{bundle['MSG.1424']}").toString();
                showMessage(message, cid);
                result = false;
            }
            if (result == true)
            if (subCriteriaBinding.getValue() == null || subCriteriaBinding.getValue() == "")
            {
                String cid = subCriteriaBinding.getClientId();
                message = "Please select sub criteria";
                showMessage(message, cid);
                result = false;
            }
            if (result == true)
            if (subCriteriaBinding.getValue().toString().equals("S"))
            {
                DCIteratorBinding di = ADFBeanUtils.findIterator("HcmIncrEmpVO1Iterator");
                if (di.getEstimatedRowCount() > 0)
                {
                   
                }
                else
                {
                    String cid = subCriteriaBinding.getClientId();
                    message = "Please select employee for increment or change increment criteria";
                    showMessage(message, cid);
                    result = false;
                }
            }
            
        }
        if (incrCriteraBinding.getValue().equals("E"))
        {
            if (grpId.getValue() == null || grpId.getValue() == "")
            {
                String cid = grpId.getClientId();
                message = resolvElDCMsg("#{bundle['MSG.1424']}").toString();
                showMessage(message, cid);
                result = false;
            }
            if (result == true)
                if (empNM.getValue() == null || empNM.getValue() == "")
                {
                    String cid = empNM.getClientId();
                    message = resolvElDCMsg("#{bundle['MSG.1425']}").toString();
                    showMessage(message, cid);
                    result = false;
                }
        }
        if (result == true)
            if (applicblDate.getValue() == null || applicblDate.getValue() == "")
            {
                String cid = applicblDate.getClientId();
                message = resolvElDCMsg("#{bundle['MSG.1427']}").toString();
                showMessage(message, cid);
                result = false;
            }
        if (result == true)
            if (incrmntDate.getValue() == null || incrmntDate.getValue() == "")
            {
                String cid = incrmntDate.getClientId();
                message = resolvElDCMsg("#{bundle['MSG.1426']}").toString();
                showMessage(message, cid);
                result = false;
            }

        return result;
    }

    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg)
    {
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true)
        {
            String msg = (String) resolvEl("#{bundle['" + mesg + "']}");
            message = new FacesMessage(msg);
        }
        if (sev.equalsIgnoreCase("E"))
        {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        }
        else if (sev.equalsIgnoreCase("W"))
        {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
        }
        else if (sev.equalsIgnoreCase("I"))
        {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        else
        {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        if (typFlg.equals("F"))
        {
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        else if (typFlg.equals("V"))
        {
            throw new ValidatorException(message);
        }
    }

    public String resolvEl(String data)
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }


    public void addNewSlryCmpnent(ActionEvent actionEvent)
    {
        DCIteratorBinding di = ADFBeanUtils.findIterator("HCMIncrementRefIterator");
        boolean result = true;
        if (di.getEstimatedRowCount() > 0)
        {
            result = chkValidFrNwSlryCmponent();
        }

        if (result == true)
        {
            System.out.println("inside====" + result);
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert1").execute();
            setLocalModefrReff("reffadd");
        }


    }

    public boolean chkValidFrNwSlryCmponent()
    {
        String message = "";
        boolean result = true;
        System.out.println("table values-----" + salId.getValue() + "===" + incrmtAmnt.getValue());
        if (salId.getValue() == null || salId.getValue() == "")
        {
            String cid = salId.getClientId();
            message = resolvElDCMsg("#{bundle['MSG.1428']}").toString();
            showMessage(message, cid);
            result = false;
        }
        if (result == true)
            if (incrmtAmnt.getValue() == null || incrmtAmnt.getValue() == "")
            {

                String cid = incrmtAmnt.getClientId();
                message = resolvElDCMsg("#{bundle['MSG.1429']}").toString();
                showMessage(message, cid);
                result = false;
            }
        if (result == true)
            if (refSlryCmpnent.getValue() == null || refSlryCmpnent.getValue() == "")
            {
                String cid = refSlryCmpnent.getClientId();
                message = resolvElDCMsg("#{bundle['MSG.1430']}").toString();
                showMessage(message, cid);
                result = false;
            }

        return result;
    }

    public boolean chkValidFrNwSlryCmponentFrSumm()
    {
        String message = "";
        boolean result = true;
        if (salIdFrSumm.getValue() == null || salIdFrSumm.getValue() == "")
        {
            String cid = salIdFrSumm.getClientId();
            message = resolvElDCMsg("#{bundle['MSG.1428']}").toString();
            showMessage(message, cid);
            result = false;
        }
        if (result == true)
            if (incrmntTypeFrSumm.getValue() == null || incrmntTypeFrSumm.getValue() == "")
            {
                String cid = incrmntTypeFrSumm.getClientId();
                message = resolvElDCMsg("#{bundle['MSG.1431']}").toString();
                showMessage(message, cid);
                result = false;
            }
        if (result == true)
            if (incrmntTypeFrSumm.getValue().toString().equals("P"))
            {
                String incCritearVal = (String) incrCriteraBinding.getValue();
                if (incCritearVal.equals("E"))
                {
                    DCIteratorBinding di = ADFBeanUtils.findIterator("HCMIncrementRefIterator");
                    if (di.getEstimatedRowCount() > 0)
                    {
                        result = true;
                    }
                    else
                    {
                        message = "Please add either reference salary component or change increment type as amount.";
                        showFacesMessage(message, "E", false, "F");
                        result = false;
                    }
                }

            }

        if (result == true)
            if (incrmntAmntFrSumm.getValue() == null || incrmntAmntFrSumm.getValue() == "")
            {
                String cid = incrmntAmntFrSumm.getClientId();
                message = resolvElDCMsg("#{bundle['MSG.1432']}").toString();
                showMessage(message, cid);
                result = false;
            }
        if (result == true)
            if (decrFlag.getValue().equals(true))
                if (incrmntTypeFrSumm.getValue().toString().equals("A") &&
                    incrmntAmntFrSumm.getValue().toString().equals("0"))
                {
                    String cid = incrmntAmntFrSumm.getClientId();
                    message = "For decrement there should be some amount. ";
                    showMessage(message, cid);
                    return false;
                }

        return result;
    }

    public boolean chkValidFrNwSlryCmponentFrSummFrGrp()
    {
        String message = "";
        if (grpSaliDBinding.getValue() == null || grpSaliDBinding.getValue() == "")
        {
            String cid = grpSaliDBinding.getClientId();
            message = resolvElDCMsg("#{bundle['MSG.1428']}").toString();
            showMessage(message, cid);
            return false;
        }
        if (grpIncrmntTypeBinding.getValue() == null || grpIncrmntTypeBinding.getValue() == "")
        {
            String cid = grpIncrmntTypeBinding.getClientId();
            message = resolvElDCMsg("#{bundle['MSG.1431']}").toString();
            showMessage(message, cid);
            return false;
        }
        if (grpIncrmntAmntBinding.getValue() == null || grpIncrmntAmntBinding.getValue() == "")
        {
            String cid = grpIncrmntAmntBinding.getClientId();
            message = resolvElDCMsg("#{bundle['MSG.1432']}").toString();
            showMessage(message, cid);
            return false;
        }
        return true;
    }

    public String showMessage(String message, String cid)
    {

        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(cid, fm);
        return null;
    }

    public void editAllFields(ActionEvent actionEvent)
    {
        String msg = "";
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        Integer pending = null;
        String incCritearVal = (String) incrCriteraBinding.getValue();
        boolean EmpWrkStatus = false;
        if (incCritearVal.equals("E"))
        {
            OperationBinding chkempwrkstatus = ADFBeanUtils.getOperationBinding("chkEmpWrkStatus");
            chkempwrkstatus.execute();
            if (chkempwrkstatus.getErrors().isEmpty() && chkempwrkstatus.getResult() != "")
            {
                String status = (String) chkempwrkstatus.getResult();
                if (status.equals("31") || status.equals("33"))
                {
                    msg =
                        "Can not edit increment of this employee: Working status in employee profile are RESIGNED ,ON HOLD or SUSPENDED ";
                    showFacesMessage(msg, "I", false, "F");
                    EmpWrkStatus = false;
                }
                else
                {
                    EmpWrkStatus = true;
                }
            }
        }
        else if (incCritearVal.equals("G"))
        {
            EmpWrkStatus = true;
        }
        if (EmpWrkStatus)
        {
            OperationBinding chkUsr = ADFBeanUtils.getOperationBinding("pendingCheck");
            chkUsr.getParamsMap().put("SlocId", sloc_id);
            chkUsr.getParamsMap().put("CldId", cld_id);
            chkUsr.getParamsMap().put("OrgId", pOrgId);
            chkUsr.getParamsMap().put("DocNo", 28511);
            chkUsr.execute();

            if (chkUsr.getResult() != null)
            {
                pending = Integer.parseInt(chkUsr.getResult().toString());
            }
            System.out.println("Pending at=" + pending);
            if (pending.compareTo(-1) != 0 && pending.compareTo(usr_id) != 0)
            {
                OperationBinding gtUsrNm = ADFBeanUtils.getOperationBinding("getUsrName");
                gtUsrNm.getParamsMap().put("usrId", pending);
                gtUsrNm.execute();
                StringBuffer usrName = new StringBuffer("[Anonymous]");
                if (gtUsrNm.getResult() != null)
                    usrName = new StringBuffer("[").append(gtUsrNm.getResult()).append("]");
                String msg2 = "Increment is pending at other user " + usrName + " for approval, You cannot Edit this.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
            }
            else
            {
                RequestContext context = RequestContext.getCurrentInstance();
                context.getPageFlowScope().put("ADD_EDIT_MODE", "S");
            }
        }


    }

    public void showPreviousSlryCmpnent(ActionEvent actionEvent)
    {
        OperationBinding ob = ADFBeanUtils.getOperationBinding("getPreviousSlryCmponent");
        ob.execute();
    }

    public void setSalId(RichSelectOneChoice salId)
    {
        this.salId = salId;
    }

    public RichSelectOneChoice getSalId()
    {
        return salId;
    }

    public void setIncrrmntTyp(RichSelectOneChoice incrrmntTyp)
    {
        this.incrrmntTyp = incrrmntTyp;
    }

    public RichSelectOneChoice getIncrrmntTyp()
    {
        return incrrmntTyp;
    }

    public void setIncrmtAmnt(RichInputText incrmtAmnt)
    {
        this.incrmtAmnt = incrmtAmnt;
    }

    public RichInputText getIncrmtAmnt()
    {
        return incrmtAmnt;
    }

    public void setRefSlryCmpnent(RichSelectOneChoice refSlryCmpnent)
    {
        this.refSlryCmpnent = refSlryCmpnent;
    }

    public RichSelectOneChoice getRefSlryCmpnent()
    {
        return refSlryCmpnent;
    }

    public void setGrpId(RichSelectOneChoice grpId)
    {
        this.grpId = grpId;
    }

    public RichSelectOneChoice getGrpId()
    {
        return grpId;
    }

    public void setIncrmntDate(RichInputDate incrmntDate)
    {
        this.incrmntDate = incrmntDate;
    }

    public RichInputDate getIncrmntDate()
    {
        return incrmntDate;
    }

    public void setApplicblDate(RichInputDate applicblDate)
    {
        this.applicblDate = applicblDate;
    }

    public RichInputDate getApplicblDate()
    {
        return applicblDate;
    }

    public void goBackToSrchPg(ActionEvent actionEvent)
    {

    }

    private void showPopup(RichPopup pop, boolean visible)
    {
        try
        {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null)
            {
                String popupId = pop.getClientId(context);
                if (popupId != null)
                {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible)
                    {
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                    }
                    else
                    {
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setAddReffComponent(RichPopup addReffComponent)
    {
        this.addReffComponent = addReffComponent;
    }

    public RichPopup getAddReffComponent()
    {
        return addReffComponent;
    }

    public void saveMultiReffComponent(ActionEvent actionEvent)
    {

        showPopup(addReffComponent, true);


    }

    public void addSlryrComponentInSumm(ActionEvent actionEvent)
    {
        DCIteratorBinding di = ADFBeanUtils.findIterator("HCMIncrmntSummIterator");
        boolean result = true;
        if (di.getEstimatedRowCount() > 0)
        {
            result = chkValidFrNwSlryCmponentFrSumm();
            System.out.println("result====" + result);
        }

        if (result == true)
        {
            System.out.println("inside====" + result);
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert2").execute();
            setLocalMode("add");
        }


    }

    public void setSalIdFrSumm(RichSelectOneChoice salIdFrSumm)
    {
        this.salIdFrSumm = salIdFrSumm;
    }

    public RichSelectOneChoice getSalIdFrSumm()
    {
        return salIdFrSumm;
    }

    public void setIncrmntTypeFrSumm(RichSelectOneChoice incrmntTypeFrSumm)
    {
        this.incrmntTypeFrSumm = incrmntTypeFrSumm;
    }

    public RichSelectOneChoice getIncrmntTypeFrSumm()
    {
        return incrmntTypeFrSumm;
    }

    public void setIncrmntAmntFrSumm(RichInputText incrmntAmntFrSumm)
    {
        this.incrmntAmntFrSumm = incrmntAmntFrSumm;
    }

    public RichInputText getIncrmntAmntFrSumm()
    {
        return incrmntAmntFrSumm;
    }

    public void chkAllFieldsfrEmpty(ActionEvent actionEvent)
    {
        DCIteratorBinding di = (DCIteratorBinding) ADFBeanUtils.findIterator("HCMIncrementRefIterator");
        boolean result = true;
        if (di.getEstimatedRowCount() > 0)
        {
            result = chkValidFrNwSlryCmponent();
            if (result == true)
            {
                OperationBinding ob = ADFBeanUtils.getOperationBinding("calIncrmntAmnt");
                ob.execute();
                if (ob.getErrors().isEmpty())
                {
                    addReffComponent.hide();
                    setLocalModefrReff("reffview");
                }
                else
                {
                    System.out.println("Error on salary calculation=" + ob.getErrors());
                }

            }
        }
        else
        {
            OperationBinding ob = ADFBeanUtils.getOperationBinding("calIncrmntAmnt");

            ob.execute();
            addReffComponent.hide();
        }

    }

    public void cnclIncrmComponenttFrmSumm(ActionEvent actionEvent)
    {
        String msg = "";
        OperationBinding opChk = ADFBeanUtils.getOperationBinding("delFromReffChild");
        opChk.execute();
        if (opChk.getErrors().size() > 0 || opChk.getResult() == "")
        {
            System.out.println("Error on delete child from reff=" + opChk.getErrors());
        }
        else
        {
            if (opChk.getResult().toString().equals("true"))
            {
                msg = "MSG.1433";
                showFacesMessage(msg, "E", true, "F");
            }
            if (opChk.getResult().toString().equals("false"))
            {
                ADFBeanUtils.getOperationBinding("Delete").execute();
                setLocalMode("view");
            }

        }

    }

    public void cnclIncrmComponenttFrmReff(ActionEvent actionEvent)
    {

        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete1").execute();
        setLocalModefrReff("reffview");

    }


    public void chkDuplicateSlryCmponent(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDuplCmponentStatus");
            opChk.getParamsMap().put("salId", object);
            opChk.execute();
            if (opChk.getErrors().size() > 0 || opChk.getResult() == null)
                System.out.println("Error on check duplicate=" + opChk.getErrors());
            else
            {
                if (opChk.getResult().toString().equals("Y"))
                    showFacesMessage("MSG.1397", "E", true, "V");
            }

        }
    }

    public void calculateNewSalary(ValueChangeEvent valueChangeEvent)
    {

        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0)
        {
            BigDecimal amount = new BigDecimal(valueChangeEvent.getNewValue().toString());
            DCIteratorBinding parentIter = (DCIteratorBinding) ADFBeanUtils.findIterator("HCMIncrmntSummIterator");
            Key parentKey = parentIter.getCurrentRow().getKey();
            OperationBinding opChk = ADFBeanUtils.getOperationBinding("calculateNewSlryAmnt");
            opChk.getParamsMap().put("amount", amount);
            opChk.execute();
            if (opChk.getErrors().size() > 0)
                System.out.println("Error on calculation total increment amount =" + opChk.getErrors());

            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        }
    }

    public void chkValidateIncrmntPerc(FacesContext facesContext, UIComponent uIComponent, Object object)
    {

        if (object != null && object.toString().length() > 0)
        {
            Boolean flag = isPrecisionValid(26, 6, ((Number) object));
            if (flag == false)
            {
                String msg = "MSG.1386";
                showFacesMessage(msg, "E", true, "V");
            }
            else
            {
                BigDecimal frCmpreZro = BigDecimal.ZERO;
                BigDecimal value = new BigDecimal(object.toString());
                BigDecimal cmpreWdHund = new BigDecimal(100);
                int newvalue = value.compareTo(frCmpreZro);
                int newvalueFrHund = value.compareTo(cmpreWdHund);
                if (newvalue == -1)
                {
                    String msg = "MSG.1386";
                    showFacesMessage(msg, "E", true, "V");
                }
                else if (newvalue == 0)
                {
                    String msg = "MSG.1386";
                    showFacesMessage(msg, "E", true, "V");
                }
                //                else if (newvalueFrHund == 0 || newvalueFrHund == +1) {
                //                    String msg =  "MSG.1386";
                //                    showFacesMessage(msg, "E", true, "V");
                //                }
            }

        }

    }

    public void chkValidateIncrmntAmnt(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            String msg = "";
            if (incrmntTypeFrSumm.getValue() == null || incrmntTypeFrSumm.getValue() == "")
            {
                msg = "MSG.1431";
                showFacesMessage(msg, "E", true, "V");
            }
            else
            {
                Boolean flag = isPrecisionValid(26, 6, ((Number) object));
                if (flag == false)
                {
                    msg = "MSG.1434";
                    showFacesMessage(msg, "E", true, "V");
                }
                else
                {
                    BigDecimal frCmpreZro = BigDecimal.ZERO;
                    BigDecimal value = new BigDecimal(object.toString());
                    int newvalue = value.compareTo(frCmpreZro);
                    if (newvalue == -1)
                    {
                        msg = "MSG.1434";
                        showFacesMessage(msg, "E", true, "V");
                    }
                    else
                    {
                        BigDecimal amount = new BigDecimal(object.toString());
                        OperationBinding opChknew = ADFBeanUtils.getOperationBinding("chkPrevSalaryAmount");
                        opChknew.getParamsMap().put("amount", amount);
                        opChknew.execute();
                        if (opChknew.getResult() != null && opChknew.getErrors().isEmpty())
                        {
                            if (opChknew.getResult().toString().equals("N"))
                            {
                                msg = "Decrement amount can't be same or more than previous salary amount.";
                                showFacesMessage(msg, "E", false, "V");
                            }
                            //                            else if (opChknew.getResult().toString().equals("X"))
                            //                            {
                            //                                msg = "Decrement can't be apply over new salary component.";
                            //                                showFacesMessage(msg, "E", false, "V");
                            //                            }
                        }
                    }
                }
            }
        }
    }

    public void grpIncrmntAmntValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            String msg = "";
            if (grpIncrmntTypeBinding.getValue() == null || grpIncrmntTypeBinding.getValue() == "")
            {
                msg = "MSG.1431";
                showFacesMessage(msg, "E", true, "V");
            }
            else
            {
                Boolean flag = isPrecisionValid(26, 6, ((Number) object));
                if (flag == false)
                {
                    msg = "MSG.1434";
                    showFacesMessage(msg, "E", true, "V");
                }
                else
                {
                    BigDecimal frCmpreZro = BigDecimal.ZERO;
                    BigDecimal value = new BigDecimal(object.toString());
                    int newvalue = value.compareTo(frCmpreZro);
                    if (newvalue == -1)
                    {
                        msg = "MSG.1434";
                        showFacesMessage(msg, "E", true, "V");
                    }
                }
            }
        }
    }

    public void setAllIncrtDataIntoSummTble(ActionEvent actionEvent)
    {

        OperationBinding ob = ADFBeanUtils.getOperationBinding("setAllIncrmntDataIntoTble");
        ob.execute();

    }

    public String getPercOrAmountLable()
    {
        if (grpIncrmntTypeBinding.getValue() != null)
        {
            String type = (String) grpIncrmntTypeBinding.getValue();
            if (type.equals("A"))
            {
                return "Amount";
            }
            else if (type.equals("P"))
            {
                return "";
            }
        }
        return "Amount";
    }

    public String getPercOrAmountLableFrCopy()
    {
        if (dummyIncrTypeFrCpyBinding.getValue() != null)
        {
            String type = (String) dummyIncrTypeFrCpyBinding.getValue();
            if (type.equals("A"))
            {
                return "Amount";
            }
            else if (type.equals("P"))
            {
                return "";
            }
        }
        return "Amount";
    }


    public void setEmpNM(RichInputListOfValues empNM)
    {
        this.empNM = empNM;
    }

    public RichInputListOfValues getEmpNM()
    {
        return empNM;
    }

    public void calOnChngIncrmntTyp(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0)
        {
            String IncrmntTyp = valueChangeEvent.getNewValue().toString();
            OperationBinding opChktwo = ADFBeanUtils.getOperationBinding("getSlryAmntAftrChngIncrmntTyp");
            opChktwo.getParamsMap().put("IncrmntTyp", IncrmntTyp);
            opChktwo.execute();
            if (opChktwo.getErrors().size() > 0)
                System.out.println("Error on increment type change =" + opChktwo.getErrors());
        }

    }

    public String saveAndFwdAction()
    {
        boolean status = chkSveIncrmtValidation();
        if (status == true)
        {
            String incCritearVal = (String) incrCriteraBinding.getValue();
            OperationBinding opbin = ADFBeanUtils.getOperationBinding("getPrevIncrStatus");
            opbin.execute();
            if (opbin.getErrors().size() == 0 && opbin.getResult().toString().equals("Y"))
            {
                DCIteratorBinding difrSumm = ADFBeanUtils.findIterator("HCMIncrmntSummIterator");
                if (difrSumm.getEstimatedRowCount() > 0)
                {
                    if (incCritearVal.equals("E"))
                    {
                        status = chkValidFrNwSlryCmponentFrSumm();
                    }
                    if (incCritearVal.equals("G"))
                    {
                        status = chkValidFrNwSlryCmponentFrSummFrGrp();
                    }
                }
                else
                {
                    String message = resolvElDCMsg("#{bundle['MSG.1435']}").toString();
                    showMessage(message, null);
                    status = false;
                }
            }
            else
            {
                if (opbin.getErrors().isEmpty())
                {
                    String message = "MSG.1423";
                    showFacesMessage(message, "", true, "F");
                    status = false;
                }
            }
            if (status == true)
            {
                if (incCritearVal.equals("E"))
                {
                    OperationBinding opbin1 = ADFBeanUtils.getOperationBinding("getStatusIsIncrOrDecr");
                    opbin1.execute();
                    if (opbin1.getErrors().isEmpty() && opbin1.getResult() != "")
                    {
                        if (opbin1.getResult().toString().equals("N"))
                        {
                            if (recvryFldChk.getValue().equals("Y"))
                                if (rcvryPrd.getValue() == null)
                                {
                                    String cid = rcvryPrd.getClientId();
                                    showMessage("Please enter pay period.", cid);
                                    status = false;
                                }
                        }
                        else if (opbin1.getResult().toString().equals("Y"))
                        {
                            if (recvryFldChk.getValue().equals("Y"))
                                if (rcvryPrd.getValue() == null)
                                {
                                    String cid = rcvryPrd.getClientId();
                                    showMessage("Please enter recovery period.", cid);
                                    status = false;
                                }

                        }
                    }
                }
            }

            if (status == true)
            {
                String mssg = "";
                String isOk = "Y";
                RequestContext context = RequestContext.getCurrentInstance();
                OperationBinding ob = ADFBeanUtils.getOperationBinding("chkIncrId");
                ob.execute();
                if (ob.getErrors().isEmpty())
                {
                    String chkIncrIdstatus = (String) ob.getResult();
                    System.out.println("chkIncrIdstatus-----------------" + chkIncrIdstatus);
                    if (chkIncrIdstatus != "exist")
                    {
                        OperationBinding ob2 = ADFBeanUtils.getOperationBinding("generateIncrId");
                        ob2.execute();
                        if (ob2.getErrors().isEmpty())
                        {
                            isOk = "Y";
                        }
                        else
                        {
                            isOk = "N";
                        }
                    }
                    else
                    {
                        isOk = "Y";
                    }
                }
                else
                {
                    isOk = "N";
                }

                if (isOk.equals("Y"))
                {
                    OperationBinding ob3 =
                        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit");
                    ob3.execute();
                    OperationBinding opchk = ADFBeanUtils.getOperationBinding("ExecuteVoSummndReff");
                    opchk.execute();
                    context.getPageFlowScope().put("ADD_EDIT_MODE", "D");


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
                    chkUsr.getParamsMap().put("DocNo", 28511);
                    chkUsr.execute();

                    if (chkUsr.getResult() != null)
                    {
                        pending = Integer.parseInt(chkUsr.getResult().toString());
                    }
                    System.out.println("Pending at=" + pending);
                    if (pending.compareTo(usr_id) != 0 && pending.compareTo(-1) != 0)
                    {
                        OperationBinding gtUsrNm = ADFBeanUtils.getOperationBinding("getUsrName");
                        gtUsrNm.getParamsMap().put("usrId", pending);
                        gtUsrNm.execute();
                        StringBuffer usrName = new StringBuffer("Anonymous");
                        if (gtUsrNm.getResult() != null)
                            usrName = new StringBuffer("[").append(usrName).append("]");
                        String msg2 =
                            "Increment is pending at other user " + usrName +
                            " for approval, You cannot Forward/Approve this.";
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message2);
                    }
                    else
                    {

                        String action = "I";
                        String remark = "A";

                        OperationBinding WfnoOp = ADFBeanUtils.getOperationBinding("getWfNo");
                        WfnoOp.getParamsMap().put("sloc_id", sloc_id);
                        WfnoOp.getParamsMap().put("cld_id", cld_id);
                        WfnoOp.getParamsMap().put("org_id", pOrgId);
                        WfnoOp.getParamsMap().put("doc_no", 28511);
                        WfnoOp.execute();

                        String WfNum = null;
                        Integer level = 0;
                        Integer res = -1;

                        if (WfnoOp.getResult() != null)
                        {
                            WfNum = WfnoOp.getResult().toString();
                        }
                        if (WfNum != null)
                        {
                            OperationBinding usrLevelOp = ADFBeanUtils.getOperationBinding("getUsrLvl");
                            usrLevelOp.getParamsMap().put("SlocId", sloc_id);
                            usrLevelOp.getParamsMap().put("CldId", cld_id);
                            usrLevelOp.getParamsMap().put("OrgId", pOrgId);
                            usrLevelOp.getParamsMap().put("usr_id", usr_id);
                            usrLevelOp.getParamsMap().put("WfNum", WfNum);
                            usrLevelOp.getParamsMap().put("DocNo", 28511);
                            usrLevelOp.execute();
                            level = -1;
                            if (usrLevelOp.getResult() != null)
                            {
                                if (usrLevelOp.getResult() != null)
                                    level = Integer.parseInt(usrLevelOp.getResult().toString());
                            }
                            if (level == -1)
                            {
                                FacesMessage message =
                                    new FacesMessage("Something went wrong while getting Workflow Level of this user.Contact to ESS.");
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            }
                            else
                            {
                                OperationBinding insTxnOp = ADFBeanUtils.getOperationBinding("insIntoTxn");
                                insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                                insTxnOp.getParamsMap().put("cld_id", cld_id);
                                insTxnOp.getParamsMap().put("pOrgId", pOrgId);
                                insTxnOp.getParamsMap().put("DOC_NO", 28511);
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

                                if (insTxnOp.getResult() != null)
                                {
                                    res = Integer.parseInt(insTxnOp.getResult().toString());
                                }
                                if (res == 1)
                                {
                                    OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("Commit");
                                    operationBinding.execute();
                                    OperationBinding opchk1 = ADFBeanUtils.getOperationBinding("ExecuteVoSummndReff");
                                    opchk1.execute();
                                    return "goToWf";

                                }
                                else
                                {
                                    System.out.println("error during insert to WF");
                                }
                                System.out.println(level + "level--res" + res);
                            }
                        }
                        else
                        {
                            FacesMessage message = new FacesMessage("Workflow not Defined for this Document.");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }
                    }
                }
                else
                {
                    mssg = "Some error occurs!!";
                    showFacesMessage(mssg, "E", false, "F");
                    context.getPageFlowScope().put("ADD_EDIT_MODE", "S");
                }
                return null;

            }
        }
        return null;
    }

    public void closePop(ActionEvent actionEvent)
    {
        DCIteratorBinding di = ADFBeanUtils.findIterator("HCMIncrementRefIterator");
        boolean result = true;
        if (di.getEstimatedRowCount() > 0)
        {
            result = chkValidFrNwSlryCmponent();
            System.out.println("result during close====" + result);
        }
        if (result == true)
        {
            addReffComponent.hide();
            setLocalModefrReff("reffview");
        }
    }

    public void chkDuplicateReffSalComponent(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            if (object.equals(salId.getValue()))
            {
                showFacesMessage("Salary component and referred salary component both can not be same.", "E", false,
                                 "V");
            }
            else
            {
                OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDuplReffCmponentStatus");
                opChk.getParamsMap().put("RefSalId", object); //salId
                opChk.getParamsMap().put("SalId", salId.getValue());
                opChk.execute();
                if (opChk.getErrors().size() > 0 || opChk.getResult() == null)
                    System.out.println("Error on check duplicate=" + opChk.getErrors());
                else
                {
                    if (opChk.getResult().toString().equals("Y"))
                        showFacesMessage("MSG.1436", "E", true, "V");
                }
            }
        }

    }


    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total)
    {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public String filtrArrearDetail()
    {
        String result = null;
        OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("FiltrArrearWithCurrentEmp");
        operationBinding.getParamsMap().put("empGrp", grpId.getValue());
        operationBinding.getParamsMap().put("empNM", empNM.getValue());
        operationBinding.getParamsMap().put("incrmntDt", incrmntDate.getValue());
        operationBinding.getParamsMap().put("applicblDT", applicblDate.getValue());
        operationBinding.execute();
        if (operationBinding.getErrors().isEmpty())
        {
            System.out.println("current employee is " + empNM.getValue());
            result = "viewArrDetail";
            return result;
        }
        return result;
    }

    public void refreshEmployee(ValueChangeEvent valueChangeEvent)
    {
        if (incrCriteraBinding.getValue() != null)
            if (incrCriteraBinding.getValue().toString().equals("E"))
            {
                valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
                AdfFacesContext.getCurrentInstance().addPartialTarget(empNM);
            }
            else if (incrCriteraBinding.getValue().toString().equals("G"))
            {
                
                valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
                AdfFacesContext.getCurrentInstance().addPartialTarget(incrCriteraBinding);
                OperationBinding opechk = ADFBeanUtils.getOperationBinding("SetSlryCompInGrpDummyVo");
                opechk.getParamsMap().put("type", "employee");
                opechk.execute();
            }

    }

    public void refreshEmployeeNm(ValueChangeEvent valueChangeEvent)
    {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(empNM);
        OperationBinding opechk = ADFBeanUtils.getOperationBinding("SetPrevSlryCompInDummyVo");
        opechk.getParamsMap().put("type", "employee");
        opechk.execute();
    }

    public void incrDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {

        if (object != null && object.toString().length() > 0)
        {
            String incCritearVal = (String) incrCriteraBinding.getValue();
            if (incrCriteraBinding.getValue() != null)
            {
                java.sql.Date incrDt = null;
                try
                {
                    incrDt = ((Timestamp) object).dateValue();
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                }
                boolean rsltfrSydte = chkIncrDtWithSysDt(incrDt, "IncrDt");
                if (rsltfrSydte == true)
                {
                    boolean rsltfrApplidte = chkIncrDtWithApplDt(incrDt);
                    if (rsltfrApplidte == true)
                    {
                        if (incCritearVal.equals("G"))
                        {

                            String lastIncrDateFrGroup = getLastIncrDtFrGrpIncr(incrDt);
                            if (lastIncrDateFrGroup.equals("N"))
                            {
                                String message =
                                    "Please select different month: This group already got increment in this month. ";
                                showFacesMessage(message, "E", false, "V");
                            }
                            else
                            {

                            }

                        }
                        if (incCritearVal.equals("E"))
                        {


                            String lstSlrProcRslt = chkLastSlrProcDate(incrDt);
                            if (lstSlrProcRslt.equals("Y"))
                            {
                                String chkdoj = chkDoj(incrDt);
                                if (chkdoj.equals("Y"))
                                {
                                    String prevIncrRslt = chkPrevIncrRslt(incrDt);
                                    if (prevIncrRslt.equals("Y"))
                                    {
                                    }
                                    else
                                    {
                                        String message = "MSG.1437";
                                        showFacesMessage(message, "E", true, "V");
                                    }
                                }
                                else if (chkdoj.equals("M"))
                                {
                                    String result = "";
                                    String doj = getJoiningDt();
                                    String message =
                                        "Increment date can not be in the same month of employee Joining Date:Employe Joining Date is ";
                                    result = message + doj;
                                    showFacesMessage(result, "E", false, "V");
                                }
                                else
                                {
                                    String result = "";
                                    String doj = getJoiningDt();
                                    String message =
                                        "Increment Date Can not be before the Employee Joining Date:Employe Joining Date is ";
                                    result = message + doj;
                                    showFacesMessage(result, "E", false, "V");
                                }
                            }
                            else
                            {
                                String lstSlryProcDt = getLastSlryProcDt();
                                String message =
                                    "Last salary processing date was " + lstSlryProcDt +
                                    ":Please enter increment date after this date ";
                                showFacesMessage(message, "E", false, "V");
                            }
                        }
                    }
                }


            }
        }


    }

    public boolean chkIncrDtWithSysDt(java.sql.Date incrDt, String type)
    {
        java.sql.Date sysDt = null;
        try
        {
            sysDt = (new Timestamp(System.currentTimeMillis())).dateValue();
            if (incrDt.compareTo(sysDt) > 0)
            {
                if (incrDt.toString().equals(sysDt.toString()))
                {
                    return true;
                }
                else
                {
                    if (type.equals("IncrDt"))
                    {
                        String msg = "Increment date should be less than current date.";
                        showFacesMessage(msg, "E", false, "V");
                        return false;
                    }
                    else if (type.equals("ApplDt"))
                    {
                        String msg = "Applicable date should be less than current date.";
                        showFacesMessage(msg, "E", false, "V");
                        return false;
                    }
                }
            }
            else
            {
                return true;
            }

        }
        catch (SQLException e) {
        }
        return true;
    }

    public boolean chkIncrDtWithApplDt(java.sql.Date incrDt)
    {
        java.sql.Date applDt = null;
        try
        {
            if (applicblDate.getValue() != null)
            {
                applDt = ((Timestamp) applicblDate.getValue()).dateValue();
                if (incrDt.compareTo(applDt) < 0)
                {
                    if (incrDt.toString().equals(applDt.toString()))
                    {
                        return true;
                    }
                    else
                    {
                        String msg = "Increment date should be greater than applicable date.";
                        showFacesMessage(msg, "E", false, "V");
                        return false;
                    }
                }
                else
                {
                    return true;
                }
            }


        }
        catch (SQLException e) {
        }
        return true;
    }


    public String chkLastSlrProcDate(java.sql.Date incrDt)
    {
        OperationBinding opechk = ADFBeanUtils.getOperationBinding("chkLastSalryProc");
        opechk.getParamsMap().put("incrDt", incrDt);
        opechk.execute();
        if (opechk.getErrors().size() == 0)
        {
            return opechk.getResult().toString();
        }
        return "";
    }

    public String chkDoj(java.sql.Date incrDt)
    {
        OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("chkJoiningDate");
        operationBinding.getParamsMap().put("incrDt", incrDt);
        operationBinding.execute();
        if (operationBinding.getErrors().size() == 0)
        {
            return operationBinding.getResult().toString();
        }
        return "";
    }

    public String chkPrevIncrRslt(java.sql.Date incrDt)
    {
        OperationBinding ob = ADFBeanUtils.getOperationBinding("getPreviousIncrmntReslt");
        ob.getParamsMap().put("incrDt", incrDt);
        ob.execute();
        if (ob.getErrors().size() == 0)
        {
            return ob.getResult().toString();
        }
        return "";
    }

    public String getJoiningDt()
    {
        OperationBinding ob = ADFBeanUtils.getOperationBinding("getJoiningDate");
        ob.execute();
        if (ob.getErrors().isEmpty())
        {
            return (String) ob.getResult();
        }
        return "";
    }

    public String getLastSlryProcDt()
    {
        OperationBinding ob = ADFBeanUtils.getOperationBinding("getLastSalaryProcessDate");
        ob.execute();
        if (ob.getErrors().isEmpty())
        {
            return (String) ob.getResult();
        }
        return "";
    }

    public String getLastIncrApliDt(java.sql.Date incrDt)
    {
        OperationBinding obchk = ADFBeanUtils.getOperationBinding("chkLastIncrAppldate");
        obchk.getParamsMap().put("applDt", incrDt);
        obchk.execute();
        if (obchk.getErrors().size() == 0)
        {
            return obchk.getResult().toString();
        }
        return "";
    }

    public String getLastIncrDtFrGrpIncr(java.sql.Date incrDt)
    {
        OperationBinding obchk = ADFBeanUtils.getOperationBinding("chkLastIncrdateForGrpIncr");
        obchk.getParamsMap().put("grpincrDt", incrDt);
        obchk.execute();
        if (obchk.getErrors().size() == 0)
        {
            return obchk.getResult().toString();
        }
        return "";
    }

    public String getDecrFlagVisisbleOrNot()
    {
        return "true";
    }

    public void appliDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {

            if (incrCriteraBinding.getValue() != null)
            {
                String incCritearVal = (String) incrCriteraBinding.getValue();
                java.sql.Date incrDt = null;
                try
                {
                    incrDt = ((Timestamp) object).dateValue();
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                }
                if (incCritearVal.equals("G"))
                {
                    boolean rsltfrSydte = chkIncrDtWithSysDt(incrDt, "ApplDt");
                    if (rsltfrSydte == true)
                    {

                    }
                }
                if (incCritearVal.equals("E"))
                {
                    boolean rsltfrSydte = chkIncrDtWithSysDt(incrDt, "ApplDt");
                    if (rsltfrSydte == true)
                    {
                        String chkDoj = chkDoj(incrDt);
                        if (chkDoj.equals("Y"))
                        {
                            String chkLstApplDt = getLastIncrApliDt(incrDt);
                            if (chkLstApplDt.equals("N"))
                            {
                                String message =
                                    "Please select different date:" +
                                    "This date already used as applicable date in previous increment. ";
                                showFacesMessage(message, "E", false, "V");
                            }
                        }
                        else if (chkDoj.equals("M"))
                        {
                            String result = "";
                            String doj = getJoiningDt();
                            String message =
                                "Increment date can not be in the same month of employee Joining Date:" +
                                "Employe Joining Date is ";
                            result = message + doj;
                            showFacesMessage(result, "E", false, "V");
                        }
                        else
                        {
                            String result = "";
                            String doj = getJoiningDt();
                            String message =
                                "Increment Applicable Can not be before the Employee Joining Date:" +
                                "Employye Joining Date is ";
                            result = message + doj;
                            showFacesMessage(result, "E", false, "V");

                        }
                    }
                }
            }
        }


    }

    public String cnclAction()
    {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();
        RequestContext context = RequestContext.getCurrentInstance();
        context.getPageFlowScope().put("ADD_EDIT_MODE", "D");
        OperationBinding ob = ADFBeanUtils.getOperationBinding("goBckToSrchPg");
        ob.execute();
        OperationBinding opechk = ADFBeanUtils.getOperationBinding("SetPrevSlryCompInDummyVo");
        opechk.getParamsMap().put("type", "cancel");
        opechk.execute();
        return "goBack";
    }

    public void chkIsPercentAvlbl(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            {
                String IncrmntTyp = object.toString();
                OperationBinding opChkone = ADFBeanUtils.getOperationBinding("getStatusIsAmntFieldAvlbl");
                opChkone.getParamsMap().put("IncrmntTyp", IncrmntTyp);
                opChkone.execute();
                if (opChkone.getErrors().size() > 0)
                {
                    System.out.println("Error on increment type change =" + opChkone.getErrors());
                }
                else
                {
                    if (opChkone.getResult().toString().equals("true"))
                    {
                    }
                    if (opChkone.getResult().toString().equals("false"))
                    {
                        String msg = "MSG.1439";
                        showFacesMessage(msg, "E", true, "V");
                    }
                }
            }

        }
    }

    public Object resolvElDCMsg(String data)
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void showPrevRefCommponent(ActionEvent actionEvent)
    {
        showPopup(showPrevComp, true);
    }

    public void showPrevRefHistCommponent(ActionEvent actionEvent)
    {
        showPopup(showPrevHiistCompfrmReff, true);
    }

    public void setShowPrevComp(RichPopup showPrevComp)
    {
        this.showPrevComp = showPrevComp;
    }

    public RichPopup getShowPrevComp()
    {
        return showPrevComp;
    }

    public void editNewComp(ActionEvent actionEvent)
    {
        setLocalMode("add");
    }

    public void closeNewComp(ActionEvent actionEvent)
    {
        DCIteratorBinding di = ADFBeanUtils.findIterator("HCMIncrmntSummIterator");
        boolean result = true;
        if (di.getEstimatedRowCount() > 0)
        {
            result = chkValidFrNwSlryCmponentFrSumm();
            System.out.println("result====" + result);
        }
        if (result == true)
        {
            if (incrmntAmntFrSumm.getValue() != null)
            {
                OperationBinding opChk = ADFBeanUtils.getOperationBinding("calculateNewSlryAmnt");
                BigDecimal amount = new BigDecimal(incrmntAmntFrSumm.getValue().toString());
                opChk.getParamsMap().put("amount", amount);
                opChk.execute();
            }
            setLocalMode("view");
        }

    }

    public void setShowPrevHiistCompfrmReff(RichPopup showPrevHiistCompfrmReff)
    {
        this.showPrevHiistCompfrmReff = showPrevHiistCompfrmReff;
    }

    public RichPopup getShowPrevHiistCompfrmReff()
    {
        return showPrevHiistCompfrmReff;
    }

    public void setDecrFlag(RichSelectBooleanCheckbox decrFlag)
    {
        this.decrFlag = decrFlag;
    }

    public RichSelectBooleanCheckbox getDecrFlag()
    {
        return decrFlag;
    }

    public void decrFlagVC(ValueChangeEvent valueChangeEvent)
    {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(incrmntAmntFrSumm);
    }

    public void decrFlagValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            String msg = "";
            String decFlag = object.toString();
            if (decFlag.equals("true"))
            {
                OperationBinding opChknew = ADFBeanUtils.getOperationBinding("chkNewSalaryComponent");
                opChknew.execute();
                if (opChknew.getResult() != null && opChknew.getErrors().isEmpty())
                {

                    if (opChknew.getResult().toString().equals("N"))
                    {
                        msg = "Decrement can't be apply over new salary component.";
                        showFacesMessage(msg, "E", false, "V");
                    }
                }
            }

        }
    }

    public void setRcvryPrd(RichInputText rcvryPrd)
    {
        this.rcvryPrd = rcvryPrd;
    }

    public RichInputText getRcvryPrd()
    {
        return rcvryPrd;
    }

    public void setRecvryFldChk(RichInputText recvryFldChk)
    {
        this.recvryFldChk = recvryFldChk;
    }

    public RichInputText getRecvryFldChk()
    {
        return recvryFldChk;
    }

    public Object resolvElDC(String data)
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    public void setIncrCriteraBinding(RichSelectOneChoice incrCriteraBinding)
    {
        this.incrCriteraBinding = incrCriteraBinding;
    }

    public RichSelectOneChoice getIncrCriteraBinding()
    {
        return incrCriteraBinding;
    }

    public boolean getIncrDtDisableCon()
    {
        boolean result = false;
        if (incrCriteraBinding.getValue() != null)
        {
            if (incrCriteraBinding.getValue().equals("E"))
            {
                if (grpId.getValue() != null)
                    if (!grpId.getValue().toString().equals(""))
                    {
                        if (empNM.getValue() != null)
                            if (!empNM.getValue().toString().equals(""))
                            {
                                result = true;
                            }
                    }
            }
            else if (incrCriteraBinding.getValue().equals("G"))
            {
                if (grpId.getValue() != null)
                    if (!grpId.getValue().toString().equals(""))
                    {
                        result = true;
                    }
            }
        }
        return result;
    }

    public void setGrpSaliDBinding(RichSelectOneChoice grpSaliDBinding)
    {
        this.grpSaliDBinding = grpSaliDBinding;
    }

    public RichSelectOneChoice getGrpSaliDBinding()
    {
        return grpSaliDBinding;
    }

    public void setGrpIncrmntAmntBinding(RichInputText grpIncrmntAmntBinding)
    {
        this.grpIncrmntAmntBinding = grpIncrmntAmntBinding;
    }

    public RichInputText getGrpIncrmntAmntBinding()
    {
        return grpIncrmntAmntBinding;
    }

    public void setGrpIncrmntTypeBinding(RichSelectOneChoice grpIncrmntTypeBinding)
    {
        this.grpIncrmntTypeBinding = grpIncrmntTypeBinding;
    }

    public RichSelectOneChoice getGrpIncrmntTypeBinding()
    {
        return grpIncrmntTypeBinding;
    }

    public void addSlryCmponentFrGrp(ActionEvent actionEvent)
    {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert2").execute();
        setLocalMode("add");
        grpIncrmntTypeBinding.setValue("A");
    }

    public void closeNewCompFrGrp(ActionEvent actionEvent)
    {
        boolean result = true;
        String copyMode = getCopyMode();
        if (copyMode != null && copyMode.equals("GroupComp"))
        {
            DCIteratorBinding di = ADFBeanUtils.findIterator("HCMIncrmntSummIterator");
            if (di.getEstimatedRowCount() > 0)
            {
                result = chkValidFrNwSlryCmponentFrSummFrGrp();
            }
            if (result == true)
            {
                grpSalAmtBinding.setValue(grpIncrmntAmntBinding.getValue());
                setLocalMode("view");
            }
        }
        else if (getCopyMode().equals("CopyAllComp"))
        {
            OperationBinding opCopyAll = ADFBeanUtils.getOperationBinding("copySameIncrementInAll");
            opCopyAll.execute();
            if (opCopyAll.getResult() != null && opCopyAll.getErrors().isEmpty())
            {

                if (opCopyAll.getResult().toString().equals("Y"))
                {
                    String msg = "Increment has been copied successfully in all component.";
                    showFacesMessage(msg, "I", false, "F");
                }
                if (opCopyAll.getResult().toString().equals("N"))
                {
                    String msg = "copied unsuccessful: Please fill both increment type and increment amount.";
                    showFacesMessage(msg, "E", false, "F");
                }
            }
            dummyIncrTypeFrCpyBinding.setValue("");
            dummyIncrAmtFrGrpBinding.setValue("");
            setCopyMode("GroupComp");
            setLocalMode("view");

        }


    }

    public void openCopyToAllDummy(ActionEvent actionEvent)
    {
        setCopyMode("CopyAllComp");
        setLocalMode("add");

    }

    public void incrCriteraVCL(ValueChangeEvent valueChangeEvent)
    {
        grpId.setValue("");
    }

    public void setDummyIncrTypeFrCpyBinding(RichSelectOneChoice dummyIncrTypeFrCpyBinding)
    {
        this.dummyIncrTypeFrCpyBinding = dummyIncrTypeFrCpyBinding;
    }

    public RichSelectOneChoice getDummyIncrTypeFrCpyBinding()
    {
        return dummyIncrTypeFrCpyBinding;
    }

    public void setDummyIncrAmtFrGrpBinding(RichInputText dummyIncrAmtFrGrpBinding)
    {
        this.dummyIncrAmtFrGrpBinding = dummyIncrAmtFrGrpBinding;
    }

    public RichInputText getDummyIncrAmtFrGrpBinding()
    {
        return dummyIncrAmtFrGrpBinding;
    }

    public void setGrpSalAmtBinding(RichInputText grpSalAmtBinding)
    {
        this.grpSalAmtBinding = grpSalAmtBinding;
    }

    public RichInputText getGrpSalAmtBinding()
    {
        return grpSalAmtBinding;
    }

    public String moveToGrpComponentPageAction()
    {
        // Add event code here...
        return "addOrDeleteComponent";
    }

    public void setDojFilterPopPupBinding(RichPopup dojFilterPopPupBinding)
    {
        this.dojFilterPopPupBinding = dojFilterPopPupBinding;
    }

    public RichPopup getDojFilterPopPupBinding()
    {
        return dojFilterPopPupBinding;
    }

    public void openDOJFilePopPupActtionListner(ActionEvent actionEvent)
    {
        showPopup(dojFilterPopPupBinding, true);
    }

    public void setGroupIncrempinding(RichPopup groupIncrempinding) {
        this.groupIncrempinding = groupIncrempinding;
    }

    public RichPopup getGroupIncrempinding() {
        return groupIncrempinding;
    }

    public String SalaryincrEmpAc() {
        showPopup(empGrpIdBinding, true);
        return null;
    }

    public void setEmpGrpIdBinding(RichPopup empGrpIdBinding) {
        this.empGrpIdBinding = empGrpIdBinding;
    }

    public RichPopup getEmpGrpIdBinding() {
        return empGrpIdBinding;
    }

    public void selectEmpValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
                    Object bindingsMethod = ADFBeanUtils.callBindingsMethod("validateEmplId", new Object[] { object }, new Object[] {
                                                                            "Employee" });
                    if (bindingsMethod != null) {
                        if (!bindingsMethod.toString().equalsIgnoreCase("Y")) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          bindingsMethod.toString(), null));

                        }
                    }
        }

    }

    public void setSubCriteriaBinding(RichSelectOneRadio subCriteriaBinding)
    {
        this.subCriteriaBinding = subCriteriaBinding;
    }

    public RichSelectOneRadio getSubCriteriaBinding()
    {
        return subCriteriaBinding;
    }
}
