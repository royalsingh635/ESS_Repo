package hmcdesgstrct.view.bean;

import adf.utils.bean.ADFBeanUtils;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import java.util.HashMap;

import java.util.Iterator;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.JboException;
import oracle.jbo.ValidationException;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;

import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class DesignationBean {
    private RichInputListOfValues dept_nm;
    private RichInputText deptID;
    private RichInputDate validFromDate;
    private RichInputDate validEndDate;
    private RichInputDate orgValidFromDate;
    private RichInputDate orgValidEndDate;
    private RichSelectOneChoice empDesignation;
    private RichSelectOneChoice empRprtngDesignation;
    private RichInputText noOfPos;
    private RichSelectOneChoice empOrgniztion;
    private RichInputText orgDesignLvl;
    private RichInputText orgNoOfPos;
    private RichSelectOneChoice empDeptNM;

    RequestContext context = RequestContext.getCurrentInstance();
    HashMap<String, Object> map = new HashMap<String, Object>();
    OperationBinding ob = null;
    private RichPopup reffAmountPopUpBinding;
    private RichSelectBooleanCheckbox refrenceChkBinding;

    public DesignationBean() {
    }

    public void searchDepartmnts(ActionEvent actionEvent) {
        if (deptID.getValue() != null) {
            ob = ADFBeanUtils.getOperationBinding("searchDepartment");
            ob.getParamsMap().put("depttId", deptID.getValue());
            ob.execute();
        }

    }


    public void setDept_nm(RichInputListOfValues dept_nm) {
        this.dept_nm = dept_nm;
    }

    public RichInputListOfValues getDept_nm() {
        return dept_nm;
    }

    public void setDeptID(RichInputText deptID) {
        this.deptID = deptID;
    }

    public RichInputText getDeptID() {
        return deptID;
    }


    public void resetDeptField(ActionEvent actionEvent) {

        dept_nm.setValue("");
        ob = ADFBeanUtils.getOperationBinding("resetDepartment");
        ob.execute();
    }

    public void addDesignation(ActionEvent actionEvent) {
        DCIteratorBinding di = getDCIterarorBinding("DesignationIterator");
        boolean result = true;
        if (di.getEstimatedRowCount() > 0)
            result = chkvalidation();
        if (result == true) {
            ADFBeanUtils.getOperationBinding("CreateInsert").execute();
            ob = ADFBeanUtils.getOperationBinding("generateDocId");
            ob.execute();
        }
        context.getPageFlowScope().put("ADD_EDIT_MODE", "S");
        ADFBeanUtils.getOperationBinding("executeCurrentRowLov").execute();

    }

    public void addReffAmountAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("CreateInsert2").execute();
    }

    public void delReffAmountAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete2").execute();
    }

    public void cancelDesignation(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Rollback").execute();
        context.getPageFlowScope().put("ADD_EDIT_MODE", "D");
    }


    public void saveDesignation(ActionEvent actionEvent) {
        String mssg = "";
        boolean result = true;
        DCIteratorBinding difrOrg = getDCIterarorBinding("DesignationIterator");
        if (difrOrg.getEstimatedRowCount() > 0) {
            result = chkvalidation();

        } else {
            result = false;
            ADFBeanUtils.getOperationBinding("Commit").execute();
            mssg = "MSG.91";
            showFacesMessage(mssg, "I", true, "F");
            context.getPageFlowScope().put("ADD_EDIT_MODE", "D");
        }

        if (result == true) {
            DCIteratorBinding di = getDCIterarorBinding("OrgDesignation1Iterator");
            if (di.getEstimatedRowCount() > 0) {
                result = chkOrgDesigValidation();
            }
        }

        if (result == true) {
            ob = ADFBeanUtils.getOperationBinding("generateStructId");
            ob.execute();
            if (ob.getErrors().isEmpty()) {
                ob = ADFBeanUtils.getOperationBinding("Commit");
                ob.execute();
                mssg = "MSG.91";
                showFacesMessage(mssg, "I", true, "F");
                context.getPageFlowScope().put("ADD_EDIT_MODE", "D");
            } else {
                mssg = "MSG.1361";
                showFacesMessage(mssg, "E", true, "F");
                context.getPageFlowScope().put("ADD_EDIT_MODE", "S");
            }
        }


    }

    public void addOrganization(ActionEvent actionEvent) {
        boolean resultdesg = true;
        resultdesg = chkvalidation();
        if (resultdesg == true) {
            DCIteratorBinding di = getDCIterarorBinding("OrgDesignation1Iterator");
            boolean result = true;
            if (di.getEstimatedRowCount() > 0)
                result = chkOrgDesigValidation();
            if (result == true) {
                ADFBeanUtils.getOperationBinding("CreateInsert1").execute();
                ADFBeanUtils.getOperationBinding("chkDesigEndDt").execute();
            }
        }

    }

    public void editAllFields(ActionEvent actionEvent) {
        context.getPageFlowScope().put("ADD_EDIT_MODE", "S");
        ADFBeanUtils.getOperationBinding("executeCurrentRowLov").execute();
    }


    public void validUptoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (validFromDate.getValue() != null && validFromDate.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) validFromDate.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {

                    } else {
                        showFacesMessage("MSG.1380", "E", true, "V");
                    }
                }
            }
        }

    }


    public void validOrgUptoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (orgValidFromDate.getValue() != null && orgValidFromDate.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) orgValidFromDate.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {

                    }

                    else {
                        showFacesMessage("MSG.1380", "E", true, "V");
                    }
                }
            }
            if (validEndDate.getValue() != null && validEndDate.getValue().toString().length() > 0) {

                java.sql.Date newstrtDt = null;
                java.sql.Date newendDt = null;
                try {
                    newstrtDt = ((Timestamp) object).dateValue();
                    newendDt = ((Timestamp) validEndDate.getValue()).dateValue();

                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (newstrtDt.compareTo(newendDt) > 0) {
                    if (newstrtDt.toString().equals(newendDt.toString())) {

                    } else {
                        showFacesMessage("MSG.1406", "E", true, "V");
                    }
                }

            }
        }

    }

    public void validOrgFrmDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (validFromDate.getValue() != null && validFromDate.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) validFromDate.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1407", "E", true, "V");
                    }
                }
            }
            if (validEndDate.getValue() != null && validEndDate.getValue().toString().length() > 0) {
                java.sql.Date newstrtDt = null;
                java.sql.Date newendDt = null;
                try {
                    newstrtDt = ((Timestamp) object).dateValue();
                    newendDt = ((Timestamp) validEndDate.getValue()).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (newstrtDt.compareTo(newendDt) > 0) {
                    if (newstrtDt.toString().equals(newendDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1406", "E", true, "V");
                    }
                }

            }
        }

    }

    public void setValidFromDate(RichInputDate validFromDate) {
        this.validFromDate = validFromDate;
    }

    public RichInputDate getValidFromDate() {
        return validFromDate;
    }

    public void setValidEndDate(RichInputDate validEndDate) {
        this.validEndDate = validEndDate;
    }

    public RichInputDate getValidEndDate() {
        return validEndDate;
    }

    public void setOrgValidFromDate(RichInputDate orgValidFromDate) {
        this.orgValidFromDate = orgValidFromDate;
    }

    public RichInputDate getOrgValidFromDate() {
        return orgValidFromDate;
    }

    public void setOrgValidEndDate(RichInputDate orgValidEndDate) {
        this.orgValidEndDate = orgValidEndDate;
    }

    public RichInputDate getOrgValidEndDate() {
        return orgValidEndDate;
    }

    public void chkDuplicateOrgId(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String message = "";
        if (object != null && object.toString().length() > 0) {

            ob = ADFBeanUtils.getOperationBinding("chkDesigAvalInOrg");
            map.put("orgID", object);
            map.put("DesgId", empDesignation.getValue());
            map.put("reprtId", empRprtngDesignation.getValue());
            map.put("DeptId", empDeptNM.getValue());
            putparam(ob, map);
            ob.execute();
            if (ob.getErrors().size() > 0 || ob.getResult() == null) {
                System.out.println("Error on check departtment,deisgnattion,reporting designation in organization=" +
                                   ob.getErrors());
            }

            else {
                if (ob.getResult().toString().equals("0")) {
                    message = "MSG.1408";
                    showFacesMessage(message, "E", true, "V");
                }
                if (ob.getResult().toString().equals("1")) {
                    message = "MSG.1409";
                    showFacesMessage(message, "E", true, "V");
                }
                if (ob.getResult().toString().equals("2")) {
                    message = "MSG.1410";
                    showFacesMessage(message, "E", true, "V");
                }
                if (ob.getResult().toString().equals("Y")) {
                    ob = ADFBeanUtils.getOperationBinding("chkDuplicateOrgID");
                    ob.getParamsMap().put("orgID", object);
                    ob.execute();
                    if (ob.getErrors().size() > 0 || ob.getResult() == null)
                        System.out.println("Error on check duplicate=" + ob.getErrors());
                    else {
                        if (ob.getResult().toString().equals("Y"))
                            showFacesMessage("MSG.1411", "E", true, "V");
                    }
                }
            }


        }

    }

    public void chkDuplicateDesigStruct(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            if (empDesignation.getValue() != null && empDesignation.getValue().toString().length() > 0) {
                ob = ADFBeanUtils.getOperationBinding("chkDuplicateDeptStructre");
                map.put("DesgId", object);
                map.put("reprtId", empRprtngDesignation.getValue());
                map.put("DeptId", empDeptNM.getValue());
                putparam(ob, map);
                ob.execute();
                if (ob.getErrors().size() > 0 || ob.getResult() == null)
                    System.out.println("Error on check duplicate=" + ob.getErrors());
                else {
                    if (ob.getResult().toString().equals("Y"))
                        showFacesMessage("MSG.1413", "E", true, "V");
                }
            }
        }

    }

    public void chkDuplicateDesigStructValueChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0) {
            if (empDesignation.getValue() != null && empDesignation.getValue().toString().length() > 0) {
                ob = ADFBeanUtils.getOperationBinding("chkDuplicateDeptStructre");
                map.put("DesgId", empDesignation.getValue());
                map.put("reprtId", empRprtngDesignation.getValue());
                map.put("DeptId", empDeptNM.getValue());
                putparam(ob, map);
                ob.execute();
                if (ob.getErrors().size() > 0 || ob.getResult() == null)
                    System.out.println("Error on check duplicate=" + ob.getErrors());
                else {
                    if (ob.getResult().toString().equals("Y"))
                        showFacesMessage("MSG.1413", "E", true, "V");
                }
            }
        }
    }

    public void chkValidationFrNoPostion(FacesContext facesContext, UIComponent uIComponent,
                                         Object object) throws SQLException {
        if (object != null && object.toString().length() > 0) {
            Boolean flag = ADFBeanUtils.isPrecisionValid(10, 0, new Number(object));
            if (flag == false) {
                String msg = "MSG.1414";
                showFacesMessage(msg, "E", true, "V");
            } else {
                if (object.equals(0)) {
                    String msg = "MSG.1414";
                    showFacesMessage(msg, "E", true, "V");
                } else if ((Integer) object <= 0) {
                    String msg = "MSG.1414";
                    showFacesMessage(msg, "E", true, "V");

                }
            }


        }
    }

    public void chkValidateOrgNoPosition(FacesContext facesContext, UIComponent uIComponent,
                                         Object object) throws SQLException {
        if (object != null && object.toString().length() > 0) {
            String msg = "";
            Boolean flag = ADFBeanUtils.isPrecisionValid(10, 0, new Number(object));
            if (flag == false) {
                msg = "MSG.1414";
                showFacesMessage(msg, "E", true, "V");
            } else {
                if (object.equals(0)) {
                    msg = "MSG.1414";
                    showFacesMessage(msg, "E", true, "V");
                } else if ((Integer) object <= 0) {
                    msg = "MSG.1414";
                    showFacesMessage(msg, "E", true, "V");

                } else {
                    ob = ADFBeanUtils.getOperationBinding("chkNoOfPositn");
                    ob.getParamsMap().put("noOfPos", (Integer) object);
                    ob.execute();
                    if (ob.getErrors().isEmpty()) {
                        if (ob.getResult() != null && ob.getResult().toString().equals("Y")) {
                            msg =
                                "No of positions can be incresed only:" +
                                "In case of resigned employee no of positions can be decreasee for particular designation structure.";
                            showFacesMessage(msg, "E", false, "V");
                        }
                    }
                }
            }


        }

    }

    public void departValueChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            ob = ADFBeanUtils.getOperationBinding("searchDepartmentNM");
            map.put("deptNM", "valueChangeEvent.getNewValue()");
            ob.execute();
        }
    }

    public void setEmpDesignation(RichSelectOneChoice empDesignation) {
        this.empDesignation = empDesignation;
    }

    public RichSelectOneChoice getEmpDesignation() {
        return empDesignation;
    }

    public void setEmpRprtngDesignation(RichSelectOneChoice empRprtngDesignation) {
        this.empRprtngDesignation = empRprtngDesignation;
    }

    public RichSelectOneChoice getEmpRprtngDesignation() {
        return empRprtngDesignation;
    }

    public void setNoOfPos(RichInputText noOfPos) {
        this.noOfPos = noOfPos;
    }

    public RichInputText getNoOfPos() {
        return noOfPos;
    }

    public boolean chkOrgDesigValidation() {
        String message = "";
        boolean result = true;
        if (empOrgniztion.getValue() == null || empOrgniztion.getValue() == "") {
            String cid = empOrgniztion.getClientId();
            message = resolvElDCMsg("#{bundle['MSG.1118']}").toString();
            showMessage(message, cid);
            return false;
        }
        if (orgDesignLvl.getValue() == null || orgDesignLvl.getValue() == "") {
            String cid = orgDesignLvl.getClientId();
            message = resolvElDCMsg("#{bundle['MSG.1416']}").toString();
            showMessage(message, cid);
            return false;
        }
        if (orgValidFromDate.getValue() == null || orgValidFromDate.getValue() == "") {
            String cid = orgValidFromDate.getClientId();
            message = resolvElDCMsg("#{bundle['MSG.1366']}").toString();
            showMessage(message, cid);
            return false;
        }
        if (orgNoOfPos.getValue() == null || orgNoOfPos.getValue() == "") {
            String cid = orgNoOfPos.getClientId();
            message = resolvElDCMsg("#{bundle['MSG.1415']}").toString();
            showMessage(message, cid);
            return false;
        }


        return result;
    }

    private void showPopup(RichPopup pop, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null) {
                String popupId = pop.getClientId(context);
                if (popupId != null) {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup =AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
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

    public void openReffAmountPopuPdAL(ActionEvent actionEvent) {
        showPopup(reffAmountPopUpBinding, true);
    }

    public void closeReffAmountPopuP(ActionEvent actionEvent) {
        reffAmountPopUpBinding.hide();
    }

    public boolean chkvalidation() {
        String message = "";
        boolean result = true;
        if (empDesignation.getValue() == null || empDesignation.getValue() == "") {
            String cid = empDesignation.getClientId();
            message = resolvElDCMsg("#{bundle['MSG.1418']}").toString();
            showMessage(message, cid);
            return false;
        }
        if (result == true)
            result = chkReptgDesig();
        if (result == true)
            if (validFromDate.getValue() == null || validFromDate.getValue() == "") {
                String cid = validFromDate.getClientId();
                message = resolvElDCMsg("#{bundle['MSG.1366']}").toString();
                showMessage(message, cid);
                return false;
            }
        if (result == true) {
            if (refrenceChkBinding.getValue() != null && refrenceChkBinding.getValue() != "") {
                String refChkVal = refrenceChkBinding.getValue().toString();
                if (refChkVal.equals("true")) {
                    DCIteratorBinding di = getDCIterarorBinding("HcmDesgReffDtl1Iterator");
                    if (di.getEstimatedRowCount() > 0) {
                        return true;
                    } else {
                        String cid = refrenceChkBinding.getClientId();
                        message = "please enter reference amount or uncheck this.";
                        showMessage(message, cid);
                        return false;
                    }

                }
            }

        }
        return result;
    }


    public void setEmpOrgniztion(RichSelectOneChoice empOrgniztion) {
        this.empOrgniztion = empOrgniztion;
    }

    public RichSelectOneChoice getEmpOrgniztion() {
        return empOrgniztion;
    }

    public void setOrgDesignLvl(RichInputText orgDesignLvl) {
        this.orgDesignLvl = orgDesignLvl;
    }

    public RichInputText getOrgDesignLvl() {
        return orgDesignLvl;
    }

    public void setOrgNoOfPos(RichInputText orgNoOfPos) {
        this.orgNoOfPos = orgNoOfPos;
    }

    public RichInputText getOrgNoOfPos() {
        return orgNoOfPos;
    }


    public void chkValidateDsgnatnLvl(FacesContext facesContext, UIComponent uIComponent,
                                      Object object) throws SQLException {
        if (object != null && object.toString().length() > 0) {

            Boolean flag = ADFBeanUtils.isPrecisionValid(5, 0, new Number(object));
            if (flag == false) {
                String msg = "MSG.1419";
                showFacesMessage(msg, "E", true, "V");
            } else {
                if (object.equals(0)) {
                    String msg = "MSG.1419";
                    showFacesMessage(msg, "E", true, "V");
                } else if ((Integer) object <= 0) {
                    String msg = "MSG.1419";
                    showFacesMessage(msg, "E", true, "V");

                }
            }


        }

    }

    public void deleteDesigStruct(ActionEvent actionEvent) {

        String msg = "";
        ob = ADFBeanUtils.getOperationBinding("getStructLinkWithOrgReslt");
        ob.execute();
        if (ob.getErrors().size() > 0 || ob.getResult() == "") {
            System.out.println("Error on delete structure from hcm desig profile=" + ob.getErrors());
        } else {
            if (ob.getResult().toString().equals("true")) {
                msg = "MSG.1420";
                showFacesMessage(msg, "E", true, "F");
            }
            if (ob.getResult().toString().equals("false")) {
                ADFBeanUtils.getOperationBinding("Delete1").execute();
            }

        }

    }

    public void deleteOrgDesig(ActionEvent actionEvent) {
        String msg = "";
        ob = ADFBeanUtils.getOperationBinding("getDesigUsageInEmpPrf");
        ob.execute();
        if (ob.getErrors().size() > 0 || ob.getResult() == "") {
            System.out.println("Error on delete structure from hcm desig profile=" + ob.getErrors());
        } else {
            if (ob.getResult().toString().equals("true")) {
                msg =
                    "Designation structure can't be delete:In this organization designation structure is being used in employee profile.";
                showFacesMessage(msg, "E", false, "F");
            }
            if (ob.getResult().toString().equals("false")) {
                ADFBeanUtils.getOperationBinding("Delete").execute();
            }

        }


    }

    public void setEmpDeptNM(RichSelectOneChoice empDeptNM) {
        this.empDeptNM = empDeptNM;
    }

    public RichSelectOneChoice getEmpDeptNM() {
        return empDeptNM;
    }

    public void refrshDesignation(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0) {
            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
            AdfFacesContext.getCurrentInstance().addPartialTarget(empDesignation);
        }
    }

    public void setNoOfPos(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0) {
            String[] numberofpos = valueChangeEvent.getNewValue().toString().split(".");
            if (numberofpos.length > 0) {
                noOfPos.setValue(numberofpos[0]);
            }

        }
    }

    public void setdeisgLevl(ValueChangeEvent valueChangeEvent) {
        String[] desiglevl = valueChangeEvent.getNewValue().toString().split(".");
        if (desiglevl.length > 0) {
            orgDesignLvl.setValue(desiglevl[0]);
        }

    }

    public void refrshallDates(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0) {
            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
            AdfFacesContext.getCurrentInstance().addPartialTarget(orgValidFromDate);
            AdfFacesContext.getCurrentInstance().addPartialTarget(orgValidEndDate);
        }
    }

    public void structStrtDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            ob = ADFBeanUtils.getOperationBinding("chkStructValidStrtdt");
            map.put("strtDt", object);
            putparam(ob, map);
            ob.execute();
            if (ob.getErrors().size() == 0 && ob.getResult().toString().equals("Y")) {

            } else {
                showFacesMessage("MSG.1421", "E", true, "V");
            }
        }

    }

    public boolean chkReptgDesig() {
        String message = "";
        if (empDesignation.getValue() != null && empDesignation.toString().length() > 0) {
            ob = ADFBeanUtils.getOperationBinding("chkIsBlnkReptgAvl");
            ob.execute();
            if (ob.getErrors().isEmpty()) {
                if (ob.getResult() != null && ob.getResult().toString().equals("Y")) {
                    String cid = empRprtngDesignation.getClientId();
                    message = resolvElDCMsg("#{bundle['MSG.2585']}").toString();
                    // message =
                    //   "Please select reporting designation: Only one designation is allowed with no reporting designation.";
                    showMessage(message, cid);
                    return false;
                } else if (ob.getResult() != null && ob.getResult().toString().equals("Z")) {
                    String cid = empRprtngDesignation.getClientId();
                    message = resolvElDCMsg("#{bundle['MSG.2586']}").toString();
                    // message = "Designation and reporting designation both can not be same.";
                    showMessage(message, cid);
                    return false;
                } else if (ob.getResult() != null && ob.getResult().toString().equals("X")) {
                    String cid = empRprtngDesignation.getClientId();
                    ob = ADFBeanUtils.getOperationBinding("getDeptName");
                    ob.execute();
                    if (ob.getErrors().isEmpty()) {
                        String deptnm = ob.getResult().toString();
                        message = resolvElDCMsg("#{bundle['MSG.2587']}").toString() + deptnm + " department.";
                        // message =
                        //   "Can not create this designation structure  :Reporting designation already used with this desigantion in " +
                        // deptnm + " department.";
                        showMessage(message, cid);
                        return false;
                    }

                }
            }
        }
        return true;
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


    @SuppressWarnings("unchecked")
    public void putparam(OperationBinding ob, HashMap map) {
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            ob.getParamsMap().put(pairs.getKey(), pairs.getValue());
            it.remove();
        }
    }

    public String showMessage(String message, String cid) {
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(cid, fm);
        return null;
    }

    public DCIteratorBinding getDCIterarorBinding(String itteratorName) {
        DCIteratorBinding dcIterator = ADFBeanUtils.findIterator(itteratorName);
        return dcIterator;
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


    public void legacyCodeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            Object bindingsMethod = ADFBeanUtils.callBindingsMethod("CheckLegacyCodeValidator", new Object[] { object }, new Object[] {
                                                                    "LegacyCode1" });
            if (bindingsMethod != null) {
                if (!bindingsMethod.toString().equalsIgnoreCase("Y")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  bindingsMethod.toString(), null));

                }
            }
        }
    }


    public void setReffAmountPopUpBinding(RichPopup reffAmountPopUpBinding) {
        this.reffAmountPopUpBinding = reffAmountPopUpBinding;
    }

    public RichPopup getReffAmountPopUpBinding() {
        return reffAmountPopUpBinding;
    }

    public void setRefrenceChkBinding(RichSelectBooleanCheckbox refrenceChkBinding) {
        this.refrenceChkBinding = refrenceChkBinding;
    }

    public RichSelectBooleanCheckbox getRefrenceChkBinding() {
        return refrenceChkBinding;
    }

    public void workexperiancevalidater(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer per = (Integer) object;
            if (per.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Work experience",
                                                              null));

            } else {
                if (object != null) {
                    Object bindingsMethod = ADFBeanUtils.callBindingsMethod("checkValidaterMinWorkExper", new Object[] {
                                                                            object }, new Object[] { "workexpr" });
                    if (bindingsMethod != null) {
                        if (!bindingsMethod.toString().equalsIgnoreCase("Y")) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          bindingsMethod.toString(), null));

                        }
                    }
                }

            }


        }
    }

    public void refralAmtvalidartor(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number per = (Number) object;
            if (per.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Amount",
                                                              null));

            } else {
                if (object != null) {
                    Object bindingsMethod = ADFBeanUtils.callBindingsMethod("checkValidaterRefamt", new Object[] {
                                                                            object }, new Object[] { "Refamtount" });
                    if (bindingsMethod != null) {
                        if (!bindingsMethod.toString().equalsIgnoreCase("Y")) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          bindingsMethod.toString(), null));

                        }
                    }
                }

            }


        }

    }
}
