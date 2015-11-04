package hcmdepartsetupapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import java.math.BigDecimal;

import java.sql.SQLException;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import javax.faces.context.FacesContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Timestamp;

import org.apache.myfaces.trinidad.context.RequestContext;

public class DepartSetupBean {
    // private RichInputText pagebinddepname;
    public String mode = "V";
    public String orgmode = "V";
    public String replicatelinkmode = "C";
    private RichInputDate bindingValidStartDate;
    private RichInputDate bindingEndDate;
    private RichInputDate bindingOrgEndDate;
    private RichInputDate bindingOrgStartDate;
    public String pass = " ";
    public String newpass = "";
    private RichInputListOfValues pagebindLovDepname;
    private RichInputText pagebindDepartNam;
    String hoOrgId = (resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString());
    String orgId = (resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString());
    private RichTable orgtablebind;
    private RichInputText pagebindorgid;
    private RichPopup reqAreaPopBinding;
    private RichSelectBooleanCheckbox productionChkBinding;
    private RichSelectBooleanCheckbox serviceChkBinding;
    private RichInputText reqAreaNmBinding;
    private RichShowDetailItem reqAreaTabBinding;
    private RichShowDetailItem orgDeptTabBinding;


    private RichLink saveBtnBinding;
    private RichSelectBooleanCheckbox reqFlgBinding;


    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public DepartSetupBean() {
    }

    public void searchDepartName(ActionEvent actionEvent) {
        System.out.println("searchDepartName");
        OperationBinding ob = getNewOperationBinding("searchdepnameAM");
        ob.getParamsMap().put("depnam", pagebindLovDepname.getValue());
        ob.execute();
        System.out.println(ob.getErrors());

    }

    public void resetDepartName(ActionEvent actionEvent) {
        System.out.println("resetDepartName");
        pagebindLovDepname.setValue("");
        OperationBinding ob = getNewOperationBinding("resetdepartNameAM");
        ob.getParamsMap().put("res", pagebindLovDepname.getValue());
        ob.execute();
        System.out.println(ob.getErrors());

    }

    public void addDepartName(ActionEvent actionEvent) {
        System.out.println("addDepartName");
        this.mode = "E";
        this.replicatelinkmode = "C";
        if (hoOrgId.equals(orgId)) {
            getNewOperationBinding("CreateInsert").execute();
            getNewOperationBinding("getDepartId").execute();
            // getNewOperationBinding("refreshParentLov").execute();
        } else {
            FacesMessage message = new FacesMessage("Departments can be created by the Head Organization Only.");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    public void cancelDepartName(ActionEvent actionEvent) {
        System.out.println("cancelDepartName");
        this.mode = "V";
        this.replicatelinkmode = "C";
        getNewOperationBinding("Rollback").execute();
    }

    public void saveDepartName(ActionEvent actionEvent) {
        System.out.println("saveDepartName");
        String msg = "";
        DCIteratorBinding difrSumm = ADFBeanUtils.findIterator("OrgDept1Iterator");
        DCIteratorBinding diffAppDept = ADFBeanUtils.findIterator("AppDept1Iterator");
        boolean status = false;
        if (diffAppDept.getEstimatedRowCount() > 0) {
            status = chkSveDeptValidation();
            if (status == true) {
                if (difrSumm.getEstimatedRowCount() > 0) {
                    status = chkValidFrOrgDept();
                }
            }
        } else {
            status = true;
        }
        if (status == true) {
            getNewOperationBinding("Commit").execute();
            getNewOperationBinding("Execute").execute();
            if (diffAppDept.getEstimatedRowCount() > 0) {
                getNewOperationBinding("refreshParentLov").execute();
            }
            msg = "MSG.91";
            showFacesMessage(msg, "I", true, "F");
            this.mode = "V";
            this.replicatelinkmode = "C";
        }
    }

    public void setReplicatelinkmode(String replicatelinkmode) {
        this.replicatelinkmode = replicatelinkmode;
    }

    public String getReplicatelinkmode() {
        return replicatelinkmode;
    }

    public void chkDuplicateDepartName(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("chkDuplicateDepartName");
        if (object != null && object.toString().length() > 0) {
            String pt = "([a-zA-Z0-9_]+)(([ ])([a-zA-Z0-9_]+))*";
            Pattern ptt = null;
            ptt = ptt.compile(pt);
            Matcher matcher = ptt.matcher(object.toString().trim().toString());
            if (!matcher.matches()) {
                String msg = "Not a valid name.Special character/spaces not allowed!";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
            String st = (String) object;
            OperationBinding opbind = getNewOperationBinding("departNamevalidate");
            opbind.getParamsMap().put("type", st.trim());
            opbind.execute();
            pass = (String) opbind.getResult();
            if (pass.equals("Y")) {
                String message = resolvElDCMsg("#{bundle['MSG.1561']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            }
        }
    }


    public void editDepartName(ActionEvent actionEvent) {
        this.mode = "E";
        this.replicatelinkmode = "C";
    }

    public boolean chkValidFrOrgDept() {
        System.out.println("chkValidFrOrgDept");
        String message = "";
        boolean result = true;
        DCIteratorBinding itr = ADFBeanUtils.findIterator("OrgDept1Iterator");
        if (itr.getCurrentRow().getAttribute("OrgId") == null || itr.getCurrentRow().getAttribute("OrgId") == "") {
            message = resolvElDCMsg("#{bundle['MSG.1562']}").toString();
            showMessage(message, null);
            result = false;
        }
        if (result == true)
            if (bindingOrgStartDate.getValue() == null || bindingOrgStartDate.getValue() == "") {
                String cid = bindingOrgStartDate.getClientId();
                message = resolvElDCMsg("#{bundle['MSG.1563']}").toString();
                showMessage(message, cid);
                result = false;
            }
        if (result == true)
            System.out.println("reqFlgBinding.getValue()" + reqFlgBinding.getValue());
        if (reqFlgBinding.getValue() != null)
            if (reqFlgBinding.getValue().equals(true)) {
                DCIteratorBinding dirqmt = ADFBeanUtils.findIterator("RequirementAreaVO1Iterator");
                if (dirqmt.getEstimatedRowCount() > 0) {
                    result = true;
                } else {
                    String cid = reqFlgBinding.getClientId();
                    message = "Please create requirement area or uncheck requirement area  checkbox.";
                    showMessage(message, cid);
                    result = false;
                }

            }
        return result;
    }

    public boolean chkSveDeptValidation() {
        System.out.println("chkSveDeptValidation");
        String message = "";
        boolean result = true;
        if (pagebindDepartNam.getValue() == null || pagebindDepartNam.getValue() == "") {
            String cid = pagebindDepartNam.getClientId();
            message = resolvElDCMsg("#{bundle['MSG.1564']}").toString();
            showMessage(message, cid);
            result = false;
        }
        if (result == true)
            if (bindingValidStartDate.getValue() == null || bindingValidStartDate.getValue() == "") {
                String cid = bindingValidStartDate.getClientId();
                message = resolvElDCMsg("#{bundle['MSG.1366']}").toString();
                showMessage(message, cid);
                result = false;
            }
        return result;
    }


    public void addOrganization(ActionEvent actionEvent) {
        System.out.println("addOrganization");
        if (hoOrgId.equals(orgId)) {
            this.replicatelinkmode = "D";
            DCIteratorBinding di = ADFBeanUtils.findIterator("OrgDept1Iterator");
            boolean result = true;
            if (di.getEstimatedRowCount() > 0) {
                result = chkValidFrOrgDept();
            }
            if (result == true) {
                getNewOperationBinding("CreateInsert1").execute();
                OperationBinding ob = getNewOperationBinding("setDate");
                ob.execute();
            }
        } else {
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1565']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    public void setOrgmode(String orgmode) {
        this.orgmode = orgmode;
    }

    public String getOrgmode() {
        return orgmode;
    }

    public void validateDepartEndDate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("validateDepartEndDate");
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (bindingValidStartDate.getValue() != null && bindingValidStartDate.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) bindingValidStartDate.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1566", "E", true, "V");
                    }
                }
            }
        }

    }

    public void setBindingValidStartDate(RichInputDate bindingValidStartDate) {
        this.bindingValidStartDate = bindingValidStartDate;
    }

    public RichInputDate getBindingValidStartDate() {
        return bindingValidStartDate;
    }

    public void setBindingEndDate(RichInputDate bindingEndDate) {
        this.bindingEndDate = bindingEndDate;
    }

    public RichInputDate getBindingEndDate() {
        return bindingEndDate;
    }

    public void setBindingOrgEndDate(RichInputDate bindingOrgEndDate) {
        this.bindingOrgEndDate = bindingOrgEndDate;
    }

    public RichInputDate getBindingOrgEndDate() {
        return bindingOrgEndDate;
    }

    public void validateOrgEndDate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("validateOrgEndDate");
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (bindingOrgStartDate.getValue() != null && bindingOrgStartDate.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) bindingOrgStartDate.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1567", "E", true, "V");
                    }
                }
            }

            if (bindingEndDate.getValue() != null && bindingEndDate.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) bindingEndDate.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) < 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1568", "E", true, "V");
                    }
                }
            }

        }
    }

    public void setBindingOrgStartDate(RichInputDate bindingOrgStartDate) {
        this.bindingOrgStartDate = bindingOrgStartDate;
    }

    public RichInputDate getBindingOrgStartDate() {
        return bindingOrgStartDate;
    }

    public void valuechangeDepartName(ValueChangeEvent valueChangeEvent) {
        System.out.println("valuechangeDepartName");
        OperationBinding ob = getNewOperationBinding("searchdepnameAM");
        ob.getParamsMap().put("depnam", pagebindLovDepname.getValue());
        ob.execute();
        System.out.println(ob.getErrors());
    }

    public void orgStrtDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("orgStrtDtValidator");
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (bindingValidStartDate.getValue() != null && bindingValidStartDate.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) bindingValidStartDate.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1569", "E", true, "V");
                    }
                }
            }


            if (bindingEndDate.getValue() != null && bindingEndDate.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) bindingEndDate.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) < 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1570", "E", true, "V");
                    }
                }
            }
        }

    }

    public void setPagebindLovDepname(RichInputListOfValues pagebindLovDepname) {
        this.pagebindLovDepname = pagebindLovDepname;
    }

    public RichInputListOfValues getPagebindLovDepname() {
        return pagebindLovDepname;
    }

    public void setPagebindDepartNam(RichInputText pagebindDepartNam) {
        this.pagebindDepartNam = pagebindDepartNam;
    }

    public RichInputText getPagebindDepartNam() {
        return pagebindDepartNam;
    }

    // public void setPagebindOrgNam(RichInputListOfValues pagebindOrgNam) {
    //    this.pagebindOrgNam = pagebindOrgNam;
    //}

    /* public RichInputListOfValues getPagebindOrgNam() {
        return pagebindOrgNam;
    }*/

    public void checkduplicateorgname(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("checkduplicateorgname");
        String st = (String) object;
        System.out.println(st);
        OperationBinding ob = getNewOperationBinding("orgduplicatenam");
        ob.getParamsMap().put("type", st);
        ob.execute();
        pass = (String) ob.getResult();
        if (pass.equals("Y")) {
            String message = resolvElDCMsg("#{bundle['MSG.1571']}").toString();
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
    }

    public void deleteorganization(ActionEvent actionEvent) {
        getNewOperationBinding("Delete").execute();
        this.replicatelinkmode = "C";
    }


    public void setOrgtablebind(RichTable orgtablebind) {
        this.orgtablebind = orgtablebind;
    }

    public RichTable getOrgtablebind() {
        return orgtablebind;
    }

    /* public void valuechangeorgname(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(orgtablebind);
        }
    }*/

    public void setPagebindorgid(RichInputText pagebindorgid) {
        this.pagebindorgid = pagebindorgid;
    }

    public RichInputText getPagebindorgid() {
        return pagebindorgid;
    }

    public void chkduplicateorgname(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("chkduplicateorgname");
        if (object != null && object.toString().length() > 0) {
            System.out.println("1..orgname=" + object);
            OperationBinding ob = getNewOperationBinding("chkDuplicateOrgID");
            ob.getParamsMap().put("orgDesc", object);
            ob.execute();
            if (ob.getErrors().size() > 0 || ob.getResult() == null)
                System.out.println("Error on check duplicate=" + ob.getErrors());
            else {
                if (ob.getResult().toString().equals("Y"))
                    showFacesMessage("MSG.1411", "E", true, "V");
            }

        }

    }

    public void validparent(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("validparent");
        if (object != null && object.toString().length() > 0) {
            String result = "";
            String message = "";
            String st = (String) object;
            OperationBinding ob = getNewOperationBinding("parentdepart");
            ob.getParamsMap().put("deptid", st);
            //putParam(ob, "deptid", st);
            ob.execute();
            pass = (String) ob.getResult();
            if (pass.equals("Y")) {
                message = resolvElDCMsg("#{bundle['MSG.1572']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            }
            ob = getNewOperationBinding("expireparent");
            ob.getParamsMap().put("deptid", st);
            //putParam(ob, "deptid", st);
            ob.execute();
            newpass = (String) ob.getResult();
            if (newpass.equals("Y")) {
                message = resolvElDCMsg("#{bundle['MSG.1573']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            }
            ob = getNewOperationBinding("chkIsChildValid");
            ob.getParamsMap().put("parDeptId", st);
            // putParam(ob, "parDeptId", st);
            ob.execute();
            if (ob.getErrors().isEmpty()) {
                if (ob.getResult().equals("N")) {
                    message = resolvElDCMsg("#{bundle['MSG.1795']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                }
            }


        }
    }

    public void replicateall(ActionEvent actionEvent) {
        System.out.println("replicateall");
        getNewOperationBinding("addallorg").execute();
    }

    public void valuechangeparent(ValueChangeEvent valueChangeEvent) {
        System.out.println("valuechangeparent");
        String message = "";
        String st = valueChangeEvent.getNewValue().toString();
        OperationBinding op = getNewOperationBinding("expireparent");
        op.getParamsMap().put("deptid", st);
        //putParam(op, "deptid", st);
        op.execute();
        pass = (String) op.getResult();
        if (pass.equals("Y")) {
            message = resolvElDCMsg("#{bundle['MSG.1573']}").toString();
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
    }

    public void deletDept(ActionEvent actionEvent) {
        String message = "";
        OperationBinding ob = getNewOperationBinding("chkForChildDept");
        ob.execute();
        if (ob.getErrors().isEmpty()) {
            if (ob.getResult().equals("N")) {
                message = "MSG.1794";
                showFacesMessage(message, "I", true, "F");
            } else {
                getNewOperationBinding("Delete1").execute();
            }
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


    public OperationBinding getNewOperationBinding(String methodName) {
        OperationBinding opBind = ADFBeanUtils.getOperationBinding(methodName);
        return opBind;
    }

    public String showMessage(String message, String cid) {
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(cid, fm);
        return null;
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

    public void linkToReqAreaAL(ActionEvent actionEvent) {

        showPopup(reqAreaPopBinding, true);


    }

    public void setReqAreaPopBinding(RichPopup reqAreaPopBinding) {
        this.reqAreaPopBinding = reqAreaPopBinding;
    }

    public RichPopup getReqAreaPopBinding() {
        return reqAreaPopBinding;
    }

    public void delReqmtAreaAL(ActionEvent actionEvent) {
        OperationBinding del = ADFBeanUtils.getOperationBinding("chkReqAreaForDeletion");
        del.execute();
        if (del.getErrors().isEmpty() && del.getResult() != null) {
            BigDecimal delRslt = (BigDecimal) del.getResult();
            if (delRslt.compareTo(new BigDecimal(1)) == 0) {
                //     getBindings().getOperationBinding("Commit").execute();
                ADFBeanUtils.getOperationBinding("refreshAftDeletion").execute();
                FacesMessage message = new FacesMessage("Requirement area deleted successfully.");
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);

            } else if (delRslt.compareTo(new BigDecimal(-1)) == 0) {
                FacesMessage message =
                    new FacesMessage("Requirement area can't be deleted due to its further linking.");
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }

            else {
                FacesMessage message = new FacesMessage("Somthing went wrong in deleting: " + delRslt.toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        } else {
            FacesMessage message =
                new FacesMessage("Somthing went wrong in deleting function returned null:  " + del.getResult());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    public void tempIssueVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != "") {
            System.out.println("tempissue new val----" + valueChangeEvent.getNewValue());
            if (valueChangeEvent.getNewValue().equals(true)) {
                productionChkBinding.setValue(false);
                serviceChkBinding.setValue(false);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(productionChkBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(serviceChkBinding);
    }

    public void setProductionChkBinding(RichSelectBooleanCheckbox productionChkBinding) {
        this.productionChkBinding = productionChkBinding;
    }

    public RichSelectBooleanCheckbox getProductionChkBinding() {
        return productionChkBinding;
    }

    public void setServiceChkBinding(RichSelectBooleanCheckbox serviceChkBinding) {
        this.serviceChkBinding = serviceChkBinding;
    }

    public RichSelectBooleanCheckbox getServiceChkBinding() {
        return serviceChkBinding;
    }

    public void createReqAreaAL(ActionEvent actionEvent) {
        if (reqAreaNmBinding.getValue() != null && reqAreaNmBinding.getValue() != "") {
            OperationBinding op = ADFBeanUtils.getOperationBinding("insertIntoRqmtArea");
            op.execute();
            if (op.getErrors().isEmpty() && op.getResult() != null) {
                BigDecimal obj = (BigDecimal) op.getResult();

                if (obj.compareTo(new BigDecimal(1)) == 0) {
                    System.out.println("result is 1 hide pop and show msg");
                    reqAreaPopBinding.hide();
                    //getBindings().getOperationBinding("Commit").execute();
                    ADFBeanUtils.getOperationBinding("refreshAftDeletion").execute();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(reqAreaPopBinding);
                    FacesMessage message = new FacesMessage("Requirement area created successfully.");
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                    //     this.mode = "V";
                } else {
                    op.getErrors();
                }
            }
        } else {
            FacesMessage message =
                new FacesMessage("Either Click on Get Requirement Area Or enter requirement area name!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(reqAreaNmBinding.getClientId(), message);
        }
    }

    public void setReqAreaNmBinding(RichInputText reqAreaNmBinding) {
        this.reqAreaNmBinding = reqAreaNmBinding;
    }

    public RichInputText getReqAreaNmBinding() {
        return reqAreaNmBinding;
    }

    public void reqAreaPopCancelListener(PopupCanceledEvent popupCanceledEvent) {
        System.out.println("at popUp Cancel listener---");
        ADFBeanUtils.getOperationBinding("resetAtPopCancel").execute();
    }

    public void setReqAreaTabBinding(RichShowDetailItem reqAreaTabBinding) {
        this.reqAreaTabBinding = reqAreaTabBinding;
    }

    public RichShowDetailItem getReqAreaTabBinding() {
        return reqAreaTabBinding;
    }

    public void reqAreaFlagVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(saveBtnBinding);

    }

    public void setOrgDeptTabBinding(RichShowDetailItem orgDeptTabBinding) {
        this.orgDeptTabBinding = orgDeptTabBinding;
    }

    public RichShowDetailItem getOrgDeptTabBinding() {
        return orgDeptTabBinding;
    }

    public void setSaveBtnBinding(RichLink saveBtnBinding) {
        this.saveBtnBinding = saveBtnBinding;
    }

    public RichLink getSaveBtnBinding() {
        return saveBtnBinding;
    }

    public void setReqFlgBinding(RichSelectBooleanCheckbox reqFlgBinding) {
        this.reqFlgBinding = reqFlgBinding;
    }

    public RichSelectBooleanCheckbox getReqFlgBinding() {
        return reqFlgBinding;
    }

    public void getReqAreaNmAL(ActionEvent actionEvent) {
        OperationBinding dept = ADFBeanUtils.getOperationBinding("setDeptNm");
        dept.execute();
        if (dept.getErrors().isEmpty() && dept.getResult() != null) {
            reqAreaNmBinding.setValue(dept.getResult());
            AdfFacesContext.getCurrentInstance().addPartialTarget(reqAreaNmBinding);
        }
    }

    public void departLegacyCodeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Object bindingMethod = ADFBeanUtils.callBindingsMethod("CheckLegacyCodeDuplicate", new Object[] { object }, new Object[] {
                                                                   "LegacyCodeId" });
            System.out.println("object=="+object);
            if (!bindingMethod.toString().equalsIgnoreCase("Y")) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, bindingMethod.toString(),
                                                              null));
            }
        }

    }
}
