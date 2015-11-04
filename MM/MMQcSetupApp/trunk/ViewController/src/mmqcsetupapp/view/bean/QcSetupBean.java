package mmqcsetupapp.view.bean;

import adf.utils.model.ADFModelUtils;

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
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


/**
 * Qc Setup Managed Bean.
 * Contains all the business logic for Qc Setup functionalities.
 * @author BL
 * Dated -06/08/2013
 * */

public class QcSetupBean {
    private RichSelectOneRadio sourceBind;
    private UIXSwitcher switcherBind;
    private String facetValue = "group";
    private RichSelectOneRadio tlrncTypeBind;
    Number zero = new Number(0);
    Number hundrd = new Number(100);
    private RichInputText stdValBind;
    private RichSelectOneRadio tlrncTypeGrpBind;
    private RichInputText stdValGrpBind;
    private String mode = "V";
    private String paramMode = "S";
    private RichPanelFormLayout itmformBind;
    private RichTable itmTableBind;
    private RichPanelFormLayout grpFormBind;
    private RichTable grpTableBind;
    private RichPanelBox itmTabBind;
    private RichPanelBox grpTabBind;
    private String modeForFunction = "";
    private RichSelectOneChoice grpIdBind;
    public static ADFLogger adfLog = ADFLogger.createADFLogger(QcSetupBean.class);
    private RichPopup itmDeletePopup;
    private RichPopup paramDeletePoup;
    private RichTable paramTableBind;
    private String deleteParamDisble;
    private String deleleItmDisble;

    public QcSetupBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public BindingContainer getBindings1() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
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

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void qcSourceVCL(ValueChangeEvent vce) {
        facetValue = vce.getNewValue().toString();
        AdfFacesContext.getCurrentInstance().addPartialTarget(switcherBind);
    }


    public void createParameter(ActionEvent actionEvent) {
        OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsert");
        operationBinding.execute();
        setParamMode("E");
    }

//    Parameter used in other application. You can't Edit.!!
    String s=ADFModelUtils.resolvRsrc("MSG.652");
//    String msge = resolvElDCMsg("#{bundle['MSG.652']}").toString();


    public void editParameter(ActionEvent actionEvent) {
        OperationBinding isEditable = getBindings().getOperationBinding("isParamDeletable");
        isEditable.getParamsMap().put("ChkType", "P");
        isEditable.execute();
        if (isEditable.getResult() != null) {
            String retVal = isEditable.getResult().toString();
            adfLog.info("delete inside itm --------" + retVal);
            if ("Y".equalsIgnoreCase(retVal)) {
                setParamMode("E");
            } else if ("N".equalsIgnoreCase(retVal)) {
                FacesMessage msg =
                    //  new FacesMessage(FacesMessage.SEVERITY_ERROR,"Paramater used in other application. You can't Edit .!!",null);

                    new FacesMessage(FacesMessage.SEVERITY_ERROR, s, null);
                FacesContext fc = FacesContext.getCurrentInstance();

                fc.addMessage(null, msg);
            }

        }

    }

    public void saveparameter(ActionEvent actionEvent) {
        OperationBinding opSave = getBindings().getOperationBinding("Commit");
        opSave.execute();
//        Record Saved Successfully.
        String s=ADFModelUtils.resolvRsrc("MSG.91");
        FacesMessage msg =
            new FacesMessage(FacesMessage.SEVERITY_INFO,s, null);
        FacesContext fc = FacesContext.getCurrentInstance();

        fc.addMessage(null, msg);
        setParamMode("S");
    }

    public void cancelParameter(ActionEvent actionEvent) {
        OperationBinding opCancel = getBindings().getOperationBinding("Rollback");
        opCancel.execute();
        setParamMode("S");
    }

    public void setSourceBind(RichSelectOneRadio sourceBind) {
        this.sourceBind = sourceBind;
    }

    public RichSelectOneRadio getSourceBind() {
        return sourceBind;
    }

    public void setSwitcherBind(UIXSwitcher switcherBind) {
        this.switcherBind = switcherBind;
    }

    public UIXSwitcher getSwitcherBind() {
        return switcherBind;
    }

    public void setFacetValue(String facetValue) {
        this.facetValue = facetValue;
    }

    public String getFacetValue() {
        return facetValue;
    }

    public void activeVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            String newVal = vce.getNewValue().toString();
            OperationBinding opAct = getBindings().getOperationBinding("isActive");
            opAct.getParamsMap().put("value", newVal);
            opAct.execute();
        }
    }

    public String createItmAction() {
        OperationBinding isItmReq = getBindings().getOperationBinding("isItmRequired");
        isItmReq.execute();
        if (isItmReq.getResult() != null) {
            if ("Y".equalsIgnoreCase(isItmReq.getResult().toString())) {
                String s=ADFModelUtils.resolvRsrc("MSG.2239");
//                "Item Name Required."
//                    String s=resolvElDCMsg("#{bundle['MSG.2239']}").toString();
//                Paramater used in other application. You can't delete
                FacesMessage msg =
                    // new FacesMessage(FacesMessage.SEVERITY_ERROR,"Paramater used in other application. You can't delete .!!",null);
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, s, null);
                FacesContext fc = FacesContext.getCurrentInstance();

                fc.addMessage(null, msg);

            } else {
                OperationBinding opItem = getBindings1().getOperationBinding("CreateInsert2");
                opItem.execute();
                setMode("A");
            }
        }
        return null;
    }

    public void editItemAction(ActionEvent actionEvent) {
        OperationBinding isEditable = getBindings().getOperationBinding("isParamDeletable");
        isEditable.getParamsMap().put("ChkType", "I");
        isEditable.execute();
        if (isEditable.getResult() != null) {
            String retVal = isEditable.getResult().toString();
            adfLog.info("delete inside itm --------" + retVal);
            if ("Y".equalsIgnoreCase(retVal)) {
                setMode("A");
            } else if ("N".equalsIgnoreCase(retVal)) {
                FacesMessage msg =
                    //  new FacesMessage(FacesMessage.SEVERITY_ERROR,"Paramater used in other application. You can't Edit .!!",null);

                    new FacesMessage(FacesMessage.SEVERITY_ERROR, s, null);
                FacesContext fc = FacesContext.getCurrentInstance();

                fc.addMessage(null, msg);
            }

        }

    }

    public void saveItemAction(ActionEvent actionEvent) {
        OperationBinding opSave = getBindings1().getOperationBinding("Commit");
        opSave.execute();
//        Record Saved Successfully.
        String s=ADFModelUtils.resolvRsrc("MSG.91");
        FacesMessage msg =
            new FacesMessage(FacesMessage.SEVERITY_INFO, s, null);
        FacesContext fc = FacesContext.getCurrentInstance();

        fc.addMessage(null, msg);
        setMode("V");
    }

    public void cancelItemAction(ActionEvent actionEvent) {
        OperationBinding opCancel = getBindings1().getOperationBinding("Rollback");
        opCancel.execute();
        sourceBind.setValue("item");
        setMode("V");
    }


    public String createGrpAction() {
        OperationBinding isItmReq = getBindings().getOperationBinding("isGrpRequired");
        isItmReq.execute();
//        "Group Required."
        String s=ADFModelUtils.resolvRsrc("MSG.2240");
//        String s=resolvElDCMsg("#{bundle['MSG.2240']}").toString();
        if (isItmReq.getResult() != null) {
            if ("Y".equalsIgnoreCase(isItmReq.getResult().toString())) {
//                "Group Required
                FacesMessage msg =
                    // new FacesMessage(FacesMessage.SEVERITY_ERROR,"Paramater used in other application. You can't delete .!!",null);
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,s , null);
                FacesContext fc = FacesContext.getCurrentInstance();

                fc.addMessage(null, msg);

            } else {
                OperationBinding opItem = getBindings1().getOperationBinding("CreateInsert1");
                opItem.execute();
                setMode("A");
                setModeForFunction("A");
            }
        }
        return null;
    }

    public String editGrpAction() {
        setMode("A");
        setModeForFunction("E");
        return null;
    }

    public String saveGrpAction() {
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String p_org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String p_ho_org_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String grpId = grpIdBind.getValue().toString();
        adfLog.info("grpId   " + grpId + "Mode fuc" + getModeForFunction() + "p_sloc_id   " + p_sloc_id + "p_org_id  " +
                    p_org_id + "p_cldId    " + p_cldId);
        if (grpId != null && getModeForFunction() != null) {
            OperationBinding addItmFrmGrp = getBindings().getOperationBinding("addItmFromGrp");
            addItmFrmGrp.getParamsMap().put("CldId", p_cldId);
            addItmFrmGrp.getParamsMap().put("SlocId", p_sloc_id);
            addItmFrmGrp.getParamsMap().put("HoOrgId", p_ho_org_id);
            addItmFrmGrp.getParamsMap().put("OrgId", p_org_id);
            addItmFrmGrp.getParamsMap().put("GrpId", grpId);
            addItmFrmGrp.getParamsMap().put("modeValue", getModeForFunction());
            addItmFrmGrp.execute();
        }
        OperationBinding opSave = getBindings1().getOperationBinding("Commit");
        opSave.execute();
        String s=ADFModelUtils.resolvRsrc("MSG.91");
        FacesMessage msg =
            new FacesMessage(FacesMessage.SEVERITY_INFO, s, null);
        FacesContext fc = FacesContext.getCurrentInstance();

        fc.addMessage(null, msg);
        setMode("V");
        setModeForFunction(null);
        return null;
    }

    public String cancelGrpAction() {
        OperationBinding opCancel = getBindings1().getOperationBinding("Rollback");
        opCancel.execute();
        setMode("V");
        sourceBind.setValue("group");
        setModeForFunction(null);
        return null;
    }

    public void tlrncLowerBind(ValueChangeEvent vce) {

    }

    public void stdValValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number stdVal = (Number) object;
        /* if(stdVal !=null){
            if(stdVal.compareTo(zero) < 0){
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Standard Value Must be greater than zero", null));
            }
        } */
    }


    public void setTlrncTypeBind(RichSelectOneRadio tlrncTypeBind) {
        this.tlrncTypeBind = tlrncTypeBind;
    }

    public RichSelectOneRadio getTlrncTypeBind() {
        return tlrncTypeBind;
    }

    public void tlrncLowerValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number lwrVal = (Number) object;
        Number stdValNew = (Number) stdValBind.getValue();
        Number stdVal = new Number(0);
        if (stdValNew != null)
            stdVal = (Number) stdValNew.abs();
        else
            stdVal = zero;
        /*  if (stdVal == null) {
            stdVal = zero;
        } */
        adfLog.info("stdValNew >> " + stdValNew + "  stdVal >> " + stdVal);
        if (lwrVal != null && tlrncTypeBind.getValue() != null && stdVal != null) {
            if (lwrVal.compareTo(zero) <
                0) {
//                Value must be greater than or equals to zero!!
                //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                //"Value must be greater than or equals zero", null));
                String s=ADFModelUtils.resolvRsrc("MSG.653");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              s, null));
            }
            if (tlrncTypeBind.getValue().equals("A") && lwrVal.compareTo(stdVal) == 1 &&
                (zero.compareTo(stdVal) > 0 || zero.compareTo(stdVal) < 0)) {
//                value less than or equal to
                String s=ADFModelUtils.resolvRsrc("MSG.654");
                //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Value Less than or equals to "+stdVal, null));
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              s + stdVal,
                                                              null));
            } else if (tlrncTypeBind.getValue().equals("A") && lwrVal.compareTo(stdVal) == 1 &&
                       zero.compareTo(stdVal) ==
                       0) {
//                Value must be equals to
                String s=ADFModelUtils.resolvRsrc("MSG.655");
                // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Value must be equals to "+stdVal, null));
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              s + stdVal,
                                                              null));
            } else if (tlrncTypeBind.getValue().equals("P") &&
                       lwrVal.compareTo(hundrd) ==
                       1) {
//                Invalid Percentage Value.
                String s=ADFModelUtils.resolvRsrc("MSG.303");
                //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Percentage",null));
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              s, null));
            } else if (tlrncTypeBind.getValue().equals("P") &&
                       lwrVal.compareTo(zero) ==
                       -1) {
//                Invalid Percentage Value.
                String s=ADFModelUtils.resolvRsrc("MSG.303");
                //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Percentage",null));
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              s, null));
            } else if (tlrncTypeBind.getValue().equals("P") && zero.compareTo(stdVal) == 0 &&
                       lwrVal.compareTo(zero) ==
                       1) {
//                Value must be equals to
                String s=ADFModelUtils.resolvRsrc("MSG.655");
                //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Value must be equals to "+stdVal, null));
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              s + stdVal,
                                                              null));
            }
        }
    }

    public void tlrncUpperValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number upprVal = (Number) object;
    }

    public void setStdValBind(RichInputText stdValBind) {
        this.stdValBind = stdValBind;
    }

    public RichInputText getStdValBind() {
        return stdValBind;
    }

    public void tlrncLwrGrpValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number lwrVal = (Number) object;
        Number stdValNew = (Number) stdValGrpBind.getValue();
        Number stdVal = new Number(0);
        if (stdValNew != null)
            stdVal = (Number) stdValNew.abs();
        else
            stdVal = zero;
        /* if(stdVal==null){
            stdVal = zero;
        } */
        adfLog.info("stdValNew >> " + stdValNew + "  stdVal >> " + stdVal);
        if (lwrVal != null) {
            if (lwrVal.compareTo(zero) <
                0) {
                //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                // "Value must be greater than or equals zero", null));
                String s=ADFModelUtils.resolvRsrc("MSG.653");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              s, null));
            }
            if (tlrncTypeGrpBind.getValue().equals("A") && lwrVal.compareTo(stdVal) == 1 &&
                (zero.compareTo(stdVal) > 0 || zero.compareTo(stdVal) < 0)) {
                String s=ADFModelUtils.resolvRsrc("MSG.654");
//                Value must be greater than or equals to zero!!
                //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Value Less than or equals to "+stdVal, null));
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              s + stdVal,
                                                              null));
            } else if (tlrncTypeGrpBind.getValue().equals("A") && lwrVal.compareTo(stdVal) == 1 &&
                       zero.compareTo(stdVal) ==
                       0) {
//                Value must be equals to
                String s=ADFModelUtils.resolvRsrc("MSG.655");
                //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Value must be equals to "+stdVal, null));
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              s + stdVal,
                                                              null));
            } else if (tlrncTypeGrpBind.getValue().equals("P") &&
                       lwrVal.compareTo(hundrd) ==
                       1) {
//                Invalid Percentage Value.
                String s=ADFModelUtils.resolvRsrc("MSG.303");
                //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Percentage",null));
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              s, null));

            } else if (tlrncTypeGrpBind.getValue().equals("P") && zero.compareTo(stdVal) == 0 &&
                       lwrVal.compareTo(zero) ==
                       1) {
//                Value must be equals to
                String s=ADFModelUtils.resolvRsrc("MSG.655");
                // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Value must be equals to "+stdVal, null));
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              s + stdVal,
                                                              null));
            }
        }
    }

    public void tlrncUpprGrpValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...

    }

    public void setTlrncTypeGrpBind(RichSelectOneRadio tlrncTypeGrpBind) {
        this.tlrncTypeGrpBind = tlrncTypeGrpBind;
    }

    public RichSelectOneRadio getTlrncTypeGrpBind() {
        return tlrncTypeGrpBind;
    }

    public void setStdValGrpBind(RichInputText stdValGrpBind) {
        this.stdValGrpBind = stdValGrpBind;
    }

    public RichInputText getStdValGrpBind() {
        return stdValGrpBind;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setParamMode(String paramMode) {
        this.paramMode = paramMode;
    }

    public String getParamMode() {
        return paramMode;
    }

    public void setItmformBind(RichPanelFormLayout itmformBind) {
        this.itmformBind = itmformBind;
    }

    public RichPanelFormLayout getItmformBind() {
        return itmformBind;
    }

    public void setItmTableBind(RichTable itmTableBind) {
        this.itmTableBind = itmTableBind;
    }

    public RichTable getItmTableBind() {
        return itmTableBind;
    }

    public void itemVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmformBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTabBind);
    }

    public void grpVCL(ValueChangeEvent valueChangeEvent) {
    }

    public void setGrpFormBind(RichPanelFormLayout grpFormBind) {
        this.grpFormBind = grpFormBind;
    }

    public RichPanelFormLayout getGrpFormBind() {
        return grpFormBind;
    }

    public void setGrpTableBind(RichTable grpTableBind) {
        this.grpTableBind = grpTableBind;
    }

    public RichTable getGrpTableBind() {
        return grpTableBind;
    }

    public void setItmTabBind(RichPanelBox itmTabBind) {
        this.itmTabBind = itmTabBind;
    }

    public RichPanelBox getItmTabBind() {
        return itmTabBind;
    }

    public void setGrpTabBind(RichPanelBox grpTabBind) {
        this.grpTabBind = grpTabBind;
    }

    public RichPanelBox getGrpTabBind() {
        return grpTabBind;
    }

    public void paramNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String msg2 = "";
        if (object != null) {
            String langDescNew = object.toString();
            String langDesc = langDescNew.trim();

            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = langDesc.toCharArray();
            for (char c : xx) {
                if (c == '(') {
                    openB = openB + 1;
                } else if (c == ')') {
                    closeB = closeB + 1;
                }

                if (closeB > openB) {
                    closeFg = true; //closed brackets will not be more than open brackets at any given time.
                }
            }


            //if openB=0 then no. of closing and opening brackets equal || opening bracket must always come before closing brackets
            //closing brackets must not come before first occurrence of openning bracket
            if (openB != closeB || closeFg == true || (langDesc.lastIndexOf("(") > langDesc.lastIndexOf(")")) ||
                (langDesc.indexOf(")") < langDesc.indexOf("("))) {
//                Brackets not closed properly.
                String s=ADFModelUtils.resolvRsrc("MSG.7");
//                msg2 = resolvElDCMsg("#{bundle['MSG.7']}").toString();
                FacesMessage message2 = new FacesMessage(s);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("()")) {
//                Empty Brackets are not allowed.
                String s=ADFModelUtils.resolvRsrc("MSG.170");
//                msg2 = resolvElDCMsg("#{bundle['MSG.170']}").toString();
                FacesMessage message2 = new FacesMessage(s);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("  ")) {
                
//                Invalid Parameter Names.
                String s=ADFModelUtils.resolvRsrc("MSG.288");
//                msg2 = resolvElDCMsg("#{bundle['MSG.288']}").toString();
                FacesMessage message2 = new FacesMessage(s);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("(.") || langDesc.contains("(-") || langDesc.contains("-)")) {
//                Invalid Set name. Check content inside brackets.
                String s=ADFModelUtils.resolvRsrc("MSG.287");
                msg2 = resolvElDCMsg("#{bundle['MSG.287']}").toString();
                FacesMessage message2 = new FacesMessage(s);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;

            //check for valid language name.Allowed- brackets,dots,hyphen
//            Invalid Parameter Names.
            String expression = "^(?:(?>[A-Za-z0-9 \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))?)*$";
            CharSequence inputStr = langDesc;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error =ADFModelUtils.resolvRsrc("MSG.288");
//            String error = resolvElDCMsg("#{bundle['MSG.288']}").toString();

            if (matcher.matches()) {
//                Only characters, brackets, dots(.) and hyphen(-) allowed.
            } else {
                String s=ADFModelUtils.resolvRsrc("MSG.289");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                              s));
            }

            int count = 0;
            OperationBinding isDup = getBindings().getOperationBinding("isDuplicate");
            isDup.getParamsMap().put("value", langDesc);
            isDup.execute();
            if (isDup.getResult() != null) {
                count = Integer.parseInt(isDup.getResult().toString());
            }
            if (count > 0) {
//                Duplicate Record Found.
                String s=ADFModelUtils.resolvRsrc("MSG.46");
//                msg2 = resolvElDCMsg("#{bundle['MSG.46']}").toString();
                FacesMessage message2 = new FacesMessage(s);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

        }

    }

    public void grpNmVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(grpFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(grpTableBind);
    }

    public void itemNmVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmformBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTabBind);
    }

    public void setModeForFunction(String modeForFunction) {
        this.modeForFunction = modeForFunction;
    }

    public String getModeForFunction() {
        return modeForFunction;
    }

    public void setGrpIdBind(RichSelectOneChoice grpIdBind) {
        this.grpIdBind = grpIdBind;
    }

    public RichSelectOneChoice getGrpIdBind() {
        return grpIdBind;
    }

    public void paramIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String paramId = (String) object;
        if (paramId != null) {
            int count = 0;
            OperationBinding isDup = getBindings().getOperationBinding("isParamIdDuplicate");
            isDup.getParamsMap().put("value", paramId);
            isDup.execute();
            if (isDup.getResult() != null) {
                count = Integer.parseInt(isDup.getResult().toString());
            }
            System.out.println("count in itm bean   " + count);
            if (count > 0) {
//                Duplicate Record Found.
                String msg2 = resolvElDCMsg("#{bundle['MSG.46']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
        }
    }

    public String deleteParamAction() {
        OperationBinding isdelete = getBindings().getOperationBinding("isParamDeletable");
        isdelete.getParamsMap().put("ChkType", "P");
        isdelete.execute();
        if (isdelete.getResult() != null) {
            String retVal = isdelete.getResult().toString();
            adfLog.info("delete inside itm --------" + retVal);
            if ("Y".equalsIgnoreCase(retVal)) {
                setDeleteParamDisble("N");
                showPopup(paramDeletePoup, true);
            } else if ("N".equalsIgnoreCase(retVal)) {
//                Parameter used in other application. You can't delete.!!
                String s=ADFModelUtils.resolvRsrc("MSG.656");
                FacesMessage msg =
                    // new FacesMessage(FacesMessage.SEVERITY_ERROR,"Paramater used in other application. You can't delete .!!",null);
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, s,
                                     null);
                FacesContext fc = FacesContext.getCurrentInstance();

                fc.addMessage(null, msg);
            }

        }
        return null;
    }

    public String deleteParamItmAction() {
        sourceBind.setValue("item");
        OperationBinding isdelete = getBindings().getOperationBinding("isParamDeletable");
        isdelete.getParamsMap().put("ChkType", "I");
        isdelete.execute();
        if (isdelete.getResult() != null) {
            String retVal = isdelete.getResult().toString();
            adfLog.info("delete inside itm --------" + retVal);
            if ("Y".equalsIgnoreCase(retVal)) {
                setDeleleItmDisble("N");
                showPopup(itmDeletePopup, true);
            } else if ("N".equalsIgnoreCase(retVal)) {
                String s=ADFModelUtils.resolvRsrc("MSG.656");
                FacesMessage msg =
                    //new FacesMessage(FacesMessage.SEVERITY_ERROR,"Paramater used in other application. You can't delete .!!",null);
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, s,
                                     null);
                FacesContext fc = FacesContext.getCurrentInstance();

                fc.addMessage(null, msg);
            }

        }
        return null;
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

    public void setItmDeletePopup(RichPopup itmDeletePopup) {
        this.itmDeletePopup = itmDeletePopup;
    }

    public RichPopup getItmDeletePopup() {
        return itmDeletePopup;
    }

    public void setParamDeletePoup(RichPopup paramDeletePoup) {
        this.paramDeletePoup = paramDeletePoup;
    }

    public RichPopup getParamDeletePoup() {
        return paramDeletePoup;
    }

    public void paramDeleteDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
            OperationBinding opdelete = getBindings().getOperationBinding("Delete");
            opdelete.execute();
            OperationBinding opSave = getBindings().getOperationBinding("Commit");
            opSave.execute();
            OperationBinding exSave = getBindings().getOperationBinding("Execute");
            exSave.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(paramTableBind);
            setDeleteParamDisble("Y");
        }
        setDeleteParamDisble("Y");
    }

    public void itmParamDeleteDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
            OperationBinding opdelete = getBindings().getOperationBinding("Delete2");
            opdelete.execute();
            OperationBinding opSave = getBindings().getOperationBinding("Commit");
            opSave.execute();
            setDeleleItmDisble("Y");
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
        }
        setDeleleItmDisble("Y");
    }

    public void setParamTableBind(RichTable paramTableBind) {
        this.paramTableBind = paramTableBind;
    }

    public RichTable getParamTableBind() {
        return paramTableBind;
    }

    public void paramIdGrpValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String paramId = (String) object;
        if (paramId != null) {
            int count = 0;
            OperationBinding isDup = getBindings().getOperationBinding("isParamIdGrpDuplicate");
            isDup.getParamsMap().put("value", paramId);
            isDup.execute();
            if (isDup.getResult() != null) {
                count = Integer.parseInt(isDup.getResult().toString());
            }
            System.out.println("count in itm bean   " + count);
            if (count > 0) {
//                Duplicate Record Found.
                String s=ADFModelUtils.resolvRsrc("MSG.46");
//                String msg2 = resolvElDCMsg("#{bundle['MSG.46']}").toString();
                FacesMessage message2 = new FacesMessage(s);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
        }
    }

    public String backToParamPage() {
        facetValue = "group";
        return "backToParam";
    }

    public void setDeleteParamDisble(String deleteParamDisble) {
        this.deleteParamDisble = deleteParamDisble;
    }

    public String getDeleteParamDisble() {
        return deleteParamDisble;
    }

    public void paramDeletePCL(PopupCanceledEvent popupCanceledEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(paramTableBind);
        setDeleteParamDisble("Y");
    }

    public void setDeleleItmDisble(String deleleItmDisble) {
        this.deleleItmDisble = deleleItmDisble;
    }

    public String getDeleleItmDisble() {
        return deleleItmDisble;
    }

    public void paramItmdeletePCL(PopupCanceledEvent popupCanceledEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBind);
        setDeleleItmDisble("Y");
    }
}
