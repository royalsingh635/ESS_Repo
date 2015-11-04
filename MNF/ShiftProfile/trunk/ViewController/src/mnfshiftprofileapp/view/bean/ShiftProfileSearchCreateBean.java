package mnfshiftprofileapp.view.bean;

import java.math.BigDecimal;

import oracle.jbo.domain.Timestamp;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.binding.OperationBinding;

import mnfshiftprofileapp.view.Utils.ADFUtils;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adfinternal.view.faces.context.AdfFacesContextImpl;

import oracle.jbo.Row;

import java.util.Date;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;

import mnfshiftprofileapp.model.services.MnfShiftProfileAppAMImpl;

import mnfshiftprofileapp.view.Utils.JSFUtils;

import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.format.DefaultDateFormatter;
import oracle.jbo.rules.JboPrecisionScaleValidator;

public class ShiftProfileSearchCreateBean {
    private static ADFLogger adfLog = (ADFLogger) ADFLogger.createADFLogger(ShiftProfileSearchCreateBean.class);
    private RichInputText formShiftNameBind;
    private RichInputText formLegacyCodeBind;
    private RichInputText formRemarkBind;
    private RichInputDate formValidStartDateBind;
    private RichInputDate formValidEndDateBind;
    OperationBinding ob = null;
    String shiftMode = "V";
    Boolean orgLOV = false;
    private RichOutputText startTimeBindbySpinBox;
    private RichInputText formStartTimeBind;
    private RichInputText formEndTimeBiind;
    private RichInputText formBreakTimeBind;
    private RichInputListOfValues searchShiftNameBindVar;
    private static final String TIME24HOURS_PATTERN = "([0-1][0-9]|2[0-3]):[0-5][0-9]";
    private Pattern pattern;
    private Matcher stMatcher;
    private Matcher endMatcher;
    private RichInputText shiftTimeBind;
    private RichSelectBooleanCheckbox bindReplicateOrg;
    private RichPanelFormLayout searchFormBinding;
    private RichLink replicateLinkBinding;
    private RichInputDate orgValidStartDateBind;
    private RichInputDate orgEndDateBind;
    private RichInputText formAvailableTimeBind;
    private RichInputText newShiftIdBind;

    public void setOrgLOV(Boolean orgLOV) {
        this.orgLOV = orgLOV;
    }

    public Boolean getOrgLOV() {
        return orgLOV;
    }

    public void setShiftMode(String shiftMode) {
        this.shiftMode = shiftMode;
    }

    public String getShiftMode() {
        return shiftMode;
    }

    public ShiftProfileSearchCreateBean() {
        pattern = Pattern.compile(TIME24HOURS_PATTERN);
    }

    public void setFormShiftNameBind(RichInputText formShiftNameBind) {
        this.formShiftNameBind = formShiftNameBind;
    }

    public RichInputText getFormShiftNameBind() {
        return formShiftNameBind;
    }

    public void setFormLegacyCodeBind(RichInputText formLegacyCodeBind) {
        this.formLegacyCodeBind = formLegacyCodeBind;
    }

    public RichInputText getFormLegacyCodeBind() {
        return formLegacyCodeBind;
    }

    public void setFormRemarkBind(RichInputText formRemarkBind) {
        this.formRemarkBind = formRemarkBind;
    }

    public RichInputText getFormRemarkBind() {
        return formRemarkBind;
    }

    public void setFormValidStartDateBind(RichInputDate formValidStartDateBind) {
        this.formValidStartDateBind = formValidStartDateBind;
    }

    public RichInputDate getFormValidStartDateBind() {
        return formValidStartDateBind;
    }

    public void setFormValidEndDateBind(RichInputDate formValidEndDateBind) {
        this.formValidEndDateBind = formValidEndDateBind;
    }

    public RichInputDate getFormValidEndDateBind() {
        return formValidEndDateBind;
    }

    public void hourValueChangeListener(ValueChangeEvent vce) {
        String st = vce.getNewValue().toString();
    }

    public void minValueChangeListener(ValueChangeEvent vce) {
        String st = vce.getNewValue().toString();
    }

    public void setStartTimeBindbySpinBox(RichOutputText startTimeBindbySpinBox) {
        this.startTimeBindbySpinBox = startTimeBindbySpinBox;
    }

    public RichOutputText getStartTimeBindbySpinBox() {
        return startTimeBindbySpinBox;
    }

    public void setFormStartTimeBind(RichInputText formStartTimeBind) {
        this.formStartTimeBind = formStartTimeBind;
    }

    public RichInputText getFormStartTimeBind() {
        return formStartTimeBind;
    }

    public void setFormEndTimeBiind(RichInputText formEndTimeBiind) {
        this.formEndTimeBiind = formEndTimeBiind;
    }

    public RichInputText getFormEndTimeBiind() {
        return formEndTimeBiind;
    }

    public void setFormBreakTimeBind(RichInputText formBreakTimeBind) {
        this.formBreakTimeBind = formBreakTimeBind;
    }

    public RichInputText getFormBreakTimeBind() {
        return formBreakTimeBind;
    }

    public void setSearchShiftNameBindVar(RichInputListOfValues searchShiftNameBindVar) {
        this.searchShiftNameBindVar = searchShiftNameBindVar;
    }

    public RichInputListOfValues getSearchShiftNameBindVar() {
        return searchShiftNameBindVar;
    }

    /**
     * Function to validate Break Time Precision and Positive number
     * **/
    public void breakTimeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number zero = new oracle.jbo.domain.Number(0);
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) (object);
            Boolean isValid = isPrecisionValid(5, 2, value);
            adfLog.info("Function return isValid::"+isValid);
            if (isValid == false) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1062']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
            if (value != null && (value.compareTo(zero) != 1) && (value.compareTo(zero) != 0)) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.962']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }

    /**
     * Function to validate Precision for number fields
     * **/
    public Boolean isPrecisionValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);

    }

    /**
     * Function to validate Start Time format as hh:mm
     * **/
    public void startTimeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String stTime = (String) object.toString();
            stMatcher = pattern.matcher(stTime);
            if (!stMatcher.matches()) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1344']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }

    }

    public void endTimeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void endTimeChangeListener(ValueChangeEvent vce) {
    }

    /**
     * Function to calculate End Time on Start Time Value Change
     * **/
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void stTimeValueChangeListener(ValueChangeEvent vce) {
        ob = ADFUtils.findOperation("timeFormatChange");
        ob.getParamsMap().put("st", (String) this.getFormStartTimeBind().getValue());
        ob.getParamsMap().put("ed", (oracle.jbo.domain.Number) this.getShiftTimeBind().getValue());
        ob.getParamsMap().put("br", (oracle.jbo.domain.Number) this.getFormBreakTimeBind().getValue());
        ob.execute();
        if (ob.getResult() != null) {
            String v = (String) ob.getResult(); //Success, BreakTimeFail, AvailableTimeFail
            if (v.equalsIgnoreCase("success")) {
            } else if (v.equalsIgnoreCase("AvailableTimeFail")) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1345']}");
                ADFUtils.showFacesMsg(msg, msg, FacesMessage.SEVERITY_ERROR, this.getFormStartTimeBind().getClientId());
            }
        }
    }

    /**
     * Function to calculate Available time / End Time on Break Time Value Change
     * **/
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void breakTimeValueChangeListener(ValueChangeEvent vce) {
        ob = ADFUtils.findOperation("timeFormatChange");
        ob.getParamsMap().put("st", (String) this.getFormStartTimeBind().getValue());
        ob.getParamsMap().put("ed", (oracle.jbo.domain.Number) this.getShiftTimeBind().getValue());
        ob.getParamsMap().put("br", (oracle.jbo.domain.Number) this.getFormBreakTimeBind().getValue());
        ob.execute();
        if (ob.getResult() != null) {
            String v = (String) ob.getResult();
            if (v.equalsIgnoreCase("success")) {
            } else if (v.equalsIgnoreCase("BreakTimeFail")) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1346']}");
                ADFUtils.showFacesMsg(msg, msg, FacesMessage.SEVERITY_ERROR, this.getFormBreakTimeBind().getClientId());
            } else if (v.equalsIgnoreCase("AvailableTimeFail")) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1347']}");
                ADFUtils.showFacesMsg(msg, msg, FacesMessage.SEVERITY_ERROR, this.getFormBreakTimeBind().getClientId());
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(formAvailableTimeBind);
    }

    /**
     * Function to check Organization Duplicacy
     * **/
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void organizationValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("organisationValidator invoked"+object);
        if (object != null) {
            ob = ADFUtils.findOperation("chkDuplicate");
            ob.getParamsMap().put("tab", "OrgDesc");
            ob.getParamsMap().put("val", object.toString());
            ob.execute();
            System.out.println("ob result::"+ob.getResult().toString());
            if (ob.getResult() != null && ob.getResult().toString().equalsIgnoreCase("true")) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1348']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                // }
            }
        } else {
            String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.951']}");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        }
    }

    /**
     * Function to validate Duplicacy
     * **/
    private boolean duplicateValue(String iter, String attrsNm, Object val) {

        DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
        Integer countVal = 0;

        for (Row r : dcIter.getAllRowsInRange()) {
            if (r.getAttribute(attrsNm) != null && val.equals(r.getAttribute(attrsNm))) {
                countVal = countVal + 1;
            }
        }
        Row currentRow = dcIter.getCurrentRow();

        if (currentRow.getAttribute(attrsNm) != null && val.equals(currentRow.getAttribute(attrsNm))) {
            countVal = countVal - 1;
        }
        return countVal == 1 ? true : false;
    }

    /**
     * Function to Search Shifts
     * **/
    public void searchBtnActionListener(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("setShiftSearchParam");
        ob.execute();
    }

    /**
     * Function to reset Search Results
     * **/
    public void resetBtnActionListener(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("resetSearch");
        ob.execute();
    }

    /**
     * Function to Add new Shift
     * **/
    public void addShiftActionListener(ActionEvent actionEvent) {
        setShiftMode("C");
        ob = ADFUtils.findOperation("CreateWithParams");
        ob.execute();
    }

    /**
     * Function to edit any existing Shift
     * **/
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void editShiftActionListener(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("isPrfInUse");
        ob.getParamsMap().put("shiftId", this.getNewShiftIdBind().getValue() != null ? this.getNewShiftIdBind().getValue().toString() : "");
        ob.execute();
        Object result = ob.getResult();
        if (result != null) {
            String flag = result.toString();
            if(flag.equalsIgnoreCase("Y")){
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1398']}");
                ADFUtils.showFacesMsg(msg, msg, FacesMessage.SEVERITY_ERROR, null);
            }
            else {
                setShiftMode("E");
            }
        }
    }

    /**
     * Function to save Shift
     * **/
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void saveBtnActionListener(ActionEvent actionEvent) {

        if (this.getFormShiftNameBind().getValue() != null && this.getFormValidStartDateBind().getValue() != null &&
            this.getFormStartTimeBind().getValue() != null && this.getShiftTimeBind().getValue() != null) {
            adfLog.info("Value of shiftmode:::"+getShiftMode());

                ob = ADFUtils.findOperation("timeFormatChange");
                ob.getParamsMap().put("st", (String) this.getFormStartTimeBind().getValue());
                ob.getParamsMap().put("ed", (oracle.jbo.domain.Number) this.getShiftTimeBind().getValue());
                ob.getParamsMap().put("br", (oracle.jbo.domain.Number) this.getFormBreakTimeBind().getValue());
                ob.execute();
                if (ob.getResult() != null) {
                    String v = (String) ob.getResult();
                    if (v.equalsIgnoreCase("success")) {
                //                    ob = ADFUtils.findOperation("beforeSave");
                //                    ob.execute();
                        ob = ADFUtils.findOperation("Commit");
                        ob.execute();
                        setShiftMode("V");
                        String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1262']}");
                        ADFUtils.showFacesMsg(msg, msg, FacesMessage.SEVERITY_INFO, null);
                    } else if (v.equalsIgnoreCase("BreakTimeFail")) {
                        String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1346']}");
                        ADFUtils.showFacesMsg(msg, msg, FacesMessage.SEVERITY_ERROR, this.getFormBreakTimeBind().getClientId());
                    } else if (v.equalsIgnoreCase("AvailableTimeFail")) {
                        String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1347']}");
                        ADFUtils.showFacesMsg(msg, msg, FacesMessage.SEVERITY_ERROR, this.getFormBreakTimeBind().getClientId());
                    }
                }
                }      else {
            String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1349']}");
            ADFUtils.showFacesMsg(msg, msg, FacesMessage.SEVERITY_ERROR, null);
        }

           
    }

    /**
     * Function to cancel the Action
     * **/
    public void cancelBtnActionListener(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("Rollback");
        ob.execute();
        setShiftMode("V");
    }

    /**
     * Function to Add row in Organization VO
     * **/
    public void addShiftOrganizationActionListener(ActionEvent actionEvent) {
        setShiftMode("A");
        setOrgLOV(true);
        ob = ADFUtils.findOperation("CreateWithParamsShiftOrg");
        ob.execute();
    }

    public void setShiftTimeBind(RichInputText shiftTimeBind) {
        this.shiftTimeBind = shiftTimeBind;
    }

    public RichInputText getShiftTimeBind() {
        return shiftTimeBind;
    }

    /**
     * Function to validate Shift Time in positive manner
     * **/
    public void shiftTimeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number zero = new oracle.jbo.domain.Number(0);
            oracle.jbo.domain.Number value = (oracle.jbo.domain.Number) (object);
            Boolean isValid = isPrecisionValid(5, 1, value);
            if (isValid == false) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1062']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
            if (value != null && (value.compareTo(zero) != 1) && (value.compareTo(zero) != 0)) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.962']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }

    /**
     * Function to calculate End Time & Available Time on Shift Time Value Change
     * **/
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void shiftTimeValueChangeListener(ValueChangeEvent vce) {
        ob = ADFUtils.findOperation("timeFormatChange");
        ob.getParamsMap().put("st", (String) this.getFormStartTimeBind().getValue());
        ob.getParamsMap().put("ed", (oracle.jbo.domain.Number) this.getShiftTimeBind().getValue());
        ob.getParamsMap().put("br", (oracle.jbo.domain.Number) this.getFormBreakTimeBind().getValue());
        ob.execute();
        if (ob.getResult() != null) {
            String v = (String) ob.getResult(); //Success, BreakTimeFail, AvailableTimeFail
            if (v != null && v.equalsIgnoreCase("success")) {
            } else if (v != null && v.equalsIgnoreCase("AvailableTimeFail")) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1347']}");
                ADFUtils.showFacesMsg(msg, msg, FacesMessage.SEVERITY_ERROR, this.getFormBreakTimeBind().getClientId());
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(formAvailableTimeBind);
    }

    public void setBindReplicateOrg(RichSelectBooleanCheckbox bindReplicateOrg) {
        this.bindReplicateOrg = bindReplicateOrg;
    }

    public RichSelectBooleanCheckbox getBindReplicateOrg() {
        return bindReplicateOrg;
    }

    public void setSearchFormBinding(RichPanelFormLayout searchFormBinding) {
        this.searchFormBinding = searchFormBinding;
    }

    public RichPanelFormLayout getSearchFormBinding() {
        return searchFormBinding;
    }

    public void shiftNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (object.toString().contains("  ")) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1350']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              msg, null));
            }
             OperationBinding ob1 = ADFUtils.findOperation("duplicateShiftName");
            ob1.execute(); 
            if(ob1.getResult().toString().equals("Y"))
            {
            String msg = "Duplicate Shift Name Exists";
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          msg, null));
        }
    }
    }

    public void legacyCodeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (object.toString().contains("  ")) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1350']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              msg, null));
            }
        }
    }

    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public void replicateAllValueChangeListener(ValueChangeEvent valueChangeEvent) {
    }

    public void replicateActionListener(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("enableReservedMode");
        ob.execute();
        ob = ADFUtils.findOperation("excuteFnMnfPrfOrgReplication");
        ob.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(replicateLinkBinding);
    }

    public void setReplicateLinkBinding(RichLink replicateLinkBinding) {
        this.replicateLinkBinding = replicateLinkBinding;
    }

    public RichLink getReplicateLinkBinding() {
        return replicateLinkBinding;
    }

    public void endDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Date d1 = null;
            Date d2 = null;
            Timestamp t1 = null;
            Timestamp t2 = null;
            try {
                t1 = new Timestamp(formValidStartDateBind.getValue().toString());
                t2 = (Timestamp) object;

                d1 = t1.dateValue();
                d2 = t2.dateValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (t1 != null && t2 != null && d1 != null && d2 != null && t2.compareTo(t1) < 0 &&
                !d1.toString().equals(d2.toString())) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1338']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              msg,
                                                              this.formValidEndDateBind.getClientId()));
            }
        }
    }

    public void orgEndDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Date d1 = null;
            Date d2 = null;
            Timestamp t1 = null;
            Timestamp t2 = null;
            try {
                t1 = new Timestamp(orgValidStartDateBind.getValue().toString());
                t2 = (Timestamp) object;

                d1 = t1.dateValue();
                d2 = t2.dateValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (t1 != null && t2 != null && d1 != null && d2 != null && t2.compareTo(t1) < 0 &&
                !d1.toString().equals(d2.toString())) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1338']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              msg, null));
            }
        }
    }

    public void setOrgValidStartDateBind(RichInputDate orgValidStartDateBind) {
        this.orgValidStartDateBind = orgValidStartDateBind;
    }

    public RichInputDate getOrgValidStartDateBind() {
        return orgValidStartDateBind;
    }

    public void setOrgEndDateBind(RichInputDate orgEndDateBind) {
        this.orgEndDateBind = orgEndDateBind;
    }

    public RichInputDate getOrgEndDateBind() {
        return orgEndDateBind;
    }

    public void setFormAvailableTimeBind(RichInputText formAvailableTimeBind) {
        this.formAvailableTimeBind = formAvailableTimeBind;
    }

    public RichInputText getFormAvailableTimeBind() {
        return formAvailableTimeBind;
    }

    public void orgShiftStartDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Date d1 = null;
            Date d2 = null;
            Timestamp t1 = null;
            Timestamp t2 = null;
            try {
                t1 = new Timestamp(formValidStartDateBind.getValue().toString());
                t2 = (Timestamp) object;

                d1 = t1.dateValue();
                d2 = t2.dateValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (t1 != null && t2 != null && d1 != null && d2 != null && t2.compareTo(t1) < 0 &&
                !d1.toString().equals(d2.toString())) {
                String msg = JSFUtils.resolveExpressionAsString("#{bundle['MSG.1351']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              msg, null));
            }
        }
    }

    public void setNewShiftIdBind(RichInputText newShiftIdBind) {
        this.newShiftIdBind = newShiftIdBind;
    }

    public RichInputText getNewShiftIdBind() {
        return newShiftIdBind;
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
    
    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

}
