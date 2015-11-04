package candidateprofilesetup.view.beans;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import java.math.BigDecimal;

import java.util.HashMap;

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

import javax.faces.validator.ValidatorException;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.OperationBinding;

import oracle.javatools.parser.java.v2.internal.compiler.Obj;

import oracle.jbo.domain.Number;
import oracle.jbo.rules.JboPrecisionScaleValidator;

public class CandidateProfileBean {
    private RichInputText candidateNameBinding;
    private RichSelectOneChoice candGenderBinding;
    private RichSelectOneChoice advertSourceNm;
    private RichInputDate advertDateBinding;
    private RichInputListOfValues deprtmentNmBinding;
    private RichInputListOfValues desigNmBinding;
    private RichInputListOfValues locNmBinding;
    private RichSelectOneChoice interviewStatusBinding;
    private RichInputText totalExpBinding;
    private RichInputText relatedExpBinding;
    private RichInputText currentSalBinding;
    private RichInputText expectedSalBinding;
    private RichInputText leavReasonBinding;
    private RichInputText noticePerdBinding;
    private RichSelectOneChoice nationalityBinding;
    private RichInputText interviewTimesBinding;
    private RichInputText candCurrentAddBinding;
    private RichInputText candPermAddBinding;
    private RichInputDate candDobBinding;
    private RichInputText candEmailBinding;
    private RichSelectOneChoice candReligionBinding;
    private RichSelectOneChoice candBloodGrpBinding;
    private RichSelectOneChoice candMaritalStatusBinding;
    private RichInputText candPhone1Binding;
    private RichInputText candPhone2Bindng;
    private RichInputText candPanNoBinding;
    private RichInputText companyNameBinding;
    private RichSelectOneChoice candidateTypeBinding;

    public CandidateProfileBean() {
    }
    private String candMode = "D";
    private String candPersMode = "D";
    private String candExpMode = "D";
    private String candQualiMode = "D";
    private String candSkillMode = "D";
    private String candLangMode = "D";

    public void setCandMode(String candMode) {
        this.candMode = candMode;
    }

    public String getCandMode() {
        return candMode;
    }

    public void setCandPersMode(String candPersMode) {
        this.candPersMode = candPersMode;
    }

    public String getCandPersMode() {
        return candPersMode;
    }

    public void setCandExpMode(String candExpMode) {
        this.candExpMode = candExpMode;
    }

    public String getCandExpMode() {
        return candExpMode;
    }

    public void setCandQualiMode(String candQualiMode) {
        this.candQualiMode = candQualiMode;
    }

    public String getCandQualiMode() {
        return candQualiMode;
    }

    public void setCandSkillMode(String candSkillMode) {
        this.candSkillMode = candSkillMode;
    }

    public String getCandSkillMode() {
        return candSkillMode;
    }

    public void setCandLangMode(String candLangMode) {
        this.candLangMode = candLangMode;
    }

    public String getCandLangMode() {
        return candLangMode;
    }

    public void searchCandidateAL(ActionEvent actionEvent) {
        OperationBinding ob = ADFBeanUtils.getOperationBinding("searchCandidate");
        ob.execute();
    }

    public void resetCandidateAL(ActionEvent actionEvent) {
        OperationBinding ob = ADFBeanUtils.getOperationBinding("resetCandidate");
        ob.execute();
    }

    public void saveCandidateAL(ActionEvent actionEvent) {
        boolean chk = chkCandiValidation();
        String msg5="Record saved successfully!";
        if (chk) {
            OperationBinding opchk = ADFBeanUtils.getOperationBinding("Commit");
            opchk.execute();
            ADFModelUtils.showFacesMessage(msg5, msg5, FacesMessage.SEVERITY_INFO, null);
            setCandMode("D");
        }
    }

    public void addCandidateDetailAL(ActionEvent actionEvent) {
        OperationBinding opchk = ADFBeanUtils.getOperationBinding("CreateInsert");
        opchk.execute();
        OperationBinding opSetDocIdchk = ADFBeanUtils.getOperationBinding("setDocId");
        opSetDocIdchk.execute();
        setCandMode("E");

    }

    public void addCandidateOtherAllDetailAL(ActionEvent actionEvent) {
        System.out.println("In add Link Id Is  = " + actionEvent.getComponent().getId());
        String id = actionEvent.getComponent().getId();
        if (id.equals("addExperianceDetail")) {
            addExperianceDetail();
        }
        if (id.equals("addQualificationDetail")) {
            addQualificationDetail();
        }
        if (id.equals("addSkillDetail")) {
            addSkillDetail();
        }
        if (id.equals("addLanguageDetail")) {
            addLanguageDetail();
        }

    }


    public void deleteCandidateDetailAL(ActionEvent actionEvent) {
        System.out.println("Link Id Is  = " + actionEvent.getComponent().getId());
        String id = actionEvent.getComponent().getId();
        if (id.equals("deleteExperianceDetail")) {
            deleteExperianceDetail();
        }
        if (id.equals("deleteQualificationDetail")) {
            deleteQualificationDetail();
        }
        if (id.equals("deleteSkillDetail")) {
            deleteSkillDetail();
        }
        if (id.equals("deleteLanguageDetail")) {
            deleteLanguageDetail();
        }

    }

    public void addExperianceDetail() {
        OperationBinding opchk = ADFBeanUtils.getOperationBinding("CreateInsert1");
        opchk.execute();
        setCandExpMode("E");
    }

    public void addLanguageDetail() {
        OperationBinding opchk = ADFBeanUtils.getOperationBinding("CreateInsert5");
        opchk.execute();
        setCandLangMode("E");
    }

    public void addQualificationDetail() {
        OperationBinding opchk = ADFBeanUtils.getOperationBinding("CreateInsert3");
        opchk.execute();
        setCandQualiMode("E");
    }

    public void addSkillDetail() {
        OperationBinding opchk = ADFBeanUtils.getOperationBinding("CreateInsert4");
        opchk.execute();
        setCandSkillMode("E");
    }


    public void deleteExperianceDetail() {
        OperationBinding opchk = ADFBeanUtils.getOperationBinding("Delete");
        opchk.execute();
        setCandExpMode("D");
    }

    public void deleteLanguageDetail() {
        OperationBinding opchk = ADFBeanUtils.getOperationBinding("Delete1");
        opchk.execute();
        setCandLangMode("D");
    }

    public void deleteQualificationDetail() {
        OperationBinding opchk = ADFBeanUtils.getOperationBinding("Delete2");
        opchk.execute();
        setCandQualiMode("D");
    }

    public void deleteSkillDetail() {
        OperationBinding opchk = ADFBeanUtils.getOperationBinding("Delete3");
        opchk.execute();
        setCandSkillMode("D");
    }

    public void editCandidateDetailsAL(ActionEvent actionEvent) {
        System.out.println("Link Id Is  = " + actionEvent.getComponent().getId());
        String id = actionEvent.getComponent().getId();
        if (id.equals("editAllDetail")) {
            setCandMode("E");
        }
        if (id.equals("editPersonalDetail")) {
            setCandPersMode("E");
        }
        if (id.equals("editExperianceDetail")) {
            setCandExpMode("E");
        }
        if (id.equals("editQualificationDetail")) {
            setCandQualiMode("E");
        }
        if (id.equals("editSkillsDetail")) {
            setCandSkillMode("E");
        }
        if (id.equals("editLanguageDetail")) {
            setCandLangMode("E");
        }
    }

    public void okCandidateDetailsAL(ActionEvent actionEvent) {
        System.out.println("Link Id Is  = " + actionEvent.getComponent().getId());
        String id = actionEvent.getComponent().getId();

        if (id.equals("okAllDetail")) {
            setCandMode("D");
        }
        if (id.equals("okPersonalDetail")) {
            setCandPersMode("D");
        }
        if (id.equals("okExperianceDetail")) {
            setCandExpMode("D");
        }
        if (id.equals("okQualificationDetail")) {
            setCandQualiMode("D");
        }
        if (id.equals("okSkillDetail")) {
            setCandSkillMode("D");
        }
        if (id.equals("okLanguageDetail")) {
            setCandLangMode("D");
        }

    }

    public void cancelCandidateDetailsAL(ActionEvent actionEvent) {
        OperationBinding opchk = ADFBeanUtils.getOperationBinding("Rollback");
        opchk.execute();
        setCandMode("D");
    }

    public void setCandidateNameBinding(RichInputText candidateNameBinding) {
        this.candidateNameBinding = candidateNameBinding;
    }

    public RichInputText getCandidateNameBinding() {
        return candidateNameBinding;
    }

    public void setCandGenderBinding(RichSelectOneChoice candGenderBinding) {
        this.candGenderBinding = candGenderBinding;
    }

    public RichSelectOneChoice getCandGenderBinding() {
        return candGenderBinding;
    }

    public void setAdvertSourceNm(RichSelectOneChoice advertSourceNm) {
        this.advertSourceNm = advertSourceNm;
    }

    public RichSelectOneChoice getAdvertSourceNm() {
        return advertSourceNm;
    }

    public void setAdvertDateBinding(RichInputDate advertDateBinding) {
        this.advertDateBinding = advertDateBinding;
    }

    public RichInputDate getAdvertDateBinding() {
        return advertDateBinding;
    }

    public void setDeprtmentNmBinding(RichInputListOfValues deprtmentNmBinding) {
        this.deprtmentNmBinding = deprtmentNmBinding;
    }

    public RichInputListOfValues getDeprtmentNmBinding() {
        return deprtmentNmBinding;
    }

    public void setDesigNmBinding(RichInputListOfValues desigNmBinding) {
        this.desigNmBinding = desigNmBinding;
    }

    public RichInputListOfValues getDesigNmBinding() {
        return desigNmBinding;
    }

    public void setLocNmBinding(RichInputListOfValues locNmBinding) {
        this.locNmBinding = locNmBinding;
    }

    public RichInputListOfValues getLocNmBinding() {
        return locNmBinding;
    }

    public void setInterviewStatusBinding(RichSelectOneChoice interviewStatusBinding) {
        this.interviewStatusBinding = interviewStatusBinding;
    }

    public RichSelectOneChoice getInterviewStatusBinding() {
        return interviewStatusBinding;
    }

    public void setTotalExpBinding(RichInputText totalExpBinding) {
        this.totalExpBinding = totalExpBinding;
    }

    public RichInputText getTotalExpBinding() {
        return totalExpBinding;
    }

    public void setRelatedExpBinding(RichInputText relatedExpBinding) {
        this.relatedExpBinding = relatedExpBinding;
    }

    public RichInputText getRelatedExpBinding() {
        return relatedExpBinding;
    }

    public void setCurrentSalBinding(RichInputText currentSalBinding) {
        this.currentSalBinding = currentSalBinding;
    }

    public RichInputText getCurrentSalBinding() {
        return currentSalBinding;
    }

    public void setExpectedSalBinding(RichInputText expectedSalBinding) {
        this.expectedSalBinding = expectedSalBinding;
    }

    public RichInputText getExpectedSalBinding() {
        return expectedSalBinding;
    }

    public void setLeavReasonBinding(RichInputText leavReasonBinding) {
        this.leavReasonBinding = leavReasonBinding;
    }

    public RichInputText getLeavReasonBinding() {
        return leavReasonBinding;
    }

    public void setNoticePerdBinding(RichInputText noticePerdBinding) {
        this.noticePerdBinding = noticePerdBinding;
    }

    public RichInputText getNoticePerdBinding() {
        return noticePerdBinding;
    }

    public void setNationalityBinding(RichSelectOneChoice nationalityBinding) {
        this.nationalityBinding = nationalityBinding;
    }

    public RichSelectOneChoice getNationalityBinding() {
        return nationalityBinding;
    }

    public void setInterviewTimesBinding(RichInputText interviewTimesBinding) {
        this.interviewTimesBinding = interviewTimesBinding;
    }

    public RichInputText getInterviewTimesBinding() {
        return interviewTimesBinding;
    }

    public void setCandCurrentAddBinding(RichInputText candCurrentAddBinding) {
        this.candCurrentAddBinding = candCurrentAddBinding;
    }

    public RichInputText getCandCurrentAddBinding() {
        return candCurrentAddBinding;
    }

    public void setCandPermAddBinding(RichInputText candPermAddBinding) {
        this.candPermAddBinding = candPermAddBinding;
    }

    public RichInputText getCandPermAddBinding() {
        return candPermAddBinding;
    }

    public void setCandDobBinding(RichInputDate candDobBinding) {
        this.candDobBinding = candDobBinding;
    }

    public RichInputDate getCandDobBinding() {
        return candDobBinding;
    }

    public void setCandEmailBinding(RichInputText candEmailBinding) {
        this.candEmailBinding = candEmailBinding;
    }

    public RichInputText getCandEmailBinding() {
        return candEmailBinding;
    }

    public void setCandReligionBinding(RichSelectOneChoice candReligionBinding) {
        this.candReligionBinding = candReligionBinding;
    }

    public RichSelectOneChoice getCandReligionBinding() {
        return candReligionBinding;
    }

    public void setCandBloodGrpBinding(RichSelectOneChoice candBloodGrpBinding) {
        this.candBloodGrpBinding = candBloodGrpBinding;
    }

    public RichSelectOneChoice getCandBloodGrpBinding() {
        return candBloodGrpBinding;
    }

    public void setCandMaritalStatusBinding(RichSelectOneChoice candMaritalStatusBinding) {
        this.candMaritalStatusBinding = candMaritalStatusBinding;
    }

    public RichSelectOneChoice getCandMaritalStatusBinding() {
        return candMaritalStatusBinding;
    }

    public void setCandPhone1Binding(RichInputText candPhone1Binding) {
        this.candPhone1Binding = candPhone1Binding;
    }

    public RichInputText getCandPhone1Binding() {
        return candPhone1Binding;
    }

    public void setCandPhone2Bindng(RichInputText candPhone2Bindng) {
        this.candPhone2Bindng = candPhone2Bindng;
    }

    public RichInputText getCandPhone2Bindng() {
        return candPhone2Bindng;
    }

    public void setCandPanNoBinding(RichInputText candPanNoBinding) {
        this.candPanNoBinding = candPanNoBinding;
    }

    public RichInputText getCandPanNoBinding() {
        return candPanNoBinding;
    }

    public boolean chkCandiValidation() {
        String message = "";
        boolean result = true;
        if (candidateNameBinding.getValue() == null || candidateNameBinding.getValue() == "") {
            String cid = candidateNameBinding.getClientId();
            message = "Please enter candidate name.";
            showMessage(message, cid);
            result = false;
        }
        if (candGenderBinding.getValue() == null || candGenderBinding.getValue() == "") {
            String cid = candGenderBinding.getClientId();
            message = "Please select gender.";
            showMessage(message, cid);
            result = false;
        }
        if (advertSourceNm.getValue() == null || advertSourceNm.getValue() == "") {
            String cid = advertSourceNm.getClientId();
            message = "Please select advertisement source.";
            showMessage(message, cid);
            result = false;
        }
        if (advertDateBinding.getValue() == null || advertDateBinding.getValue() == "") {
            String cid = advertDateBinding.getClientId();
            message = "Please enter advertisement date.";
            showMessage(message, cid);
            result = false;
        }
        if (deprtmentNmBinding.getValue() == null || deprtmentNmBinding.getValue() == "") {
            String cid = deprtmentNmBinding.getClientId();
            message = "Please select department name.";
            showMessage(message, cid);
            result = false;
        }
        if (desigNmBinding.getValue() == null || desigNmBinding.getValue() == "") {
            String cid = desigNmBinding.getClientId();
            message = "Please select designation name.";
            showMessage(message, cid);
            result = false;
        }
        if (locNmBinding.getValue() == null || locNmBinding.getValue() == "") {
            String cid = locNmBinding.getClientId();
            message = "Please select location name.";
            showMessage(message, cid);
            result = false;
        }
        if (interviewStatusBinding.getValue() == null || interviewStatusBinding.getValue() == "") {
            String cid = interviewStatusBinding.getClientId();
            message = "Please select interview status.";
            showMessage(message, cid);
            result = false;
        }
        if (interviewStatusBinding.getValue() == null || interviewStatusBinding.getValue() == "") {
            String cid = interviewStatusBinding.getClientId();
            message = "Please select interview status.";
            showMessage(message, cid);
            result = false;
        }
        if (totalExpBinding.getValue() == null || totalExpBinding.getValue() == "") {
            String cid = totalExpBinding.getClientId();
            message = "Please enter total experiance.";
            showMessage(message, cid);
            result = false;
        } else {
            BigDecimal CmpreZro = BigDecimal.ZERO;
            BigDecimal value = new BigDecimal(totalExpBinding.getValue().toString());
            int newvalue = value.compareTo(CmpreZro);


            if (newvalue <= 0) {

                String msg1 = "total experiance  can not be zero or less than zero";
                ADFModelUtils.showFacesMessage(msg1, msg1, FacesMessage.SEVERITY_ERROR, totalExpBinding.getClientId());
                return false;
            }

        }
        if (relatedExpBinding.getValue() == null || relatedExpBinding.getValue() == "") {
            String cid = relatedExpBinding.getClientId();
            message = "Please enter related experiance.";
            showMessage(message, cid);
            result = false;
        } else {
            BigDecimal ComZero = BigDecimal.ZERO;
            BigDecimal Value = new BigDecimal(relatedExpBinding.getValue().toString());
            int newValue = Value.compareTo(ComZero);
            if (newValue <= 0) {
                String Msg3 = "related experiance.can not be zero or less than";
                ADFModelUtils.showFacesMessage(Msg3, Msg3, FacesMessage.SEVERITY_ERROR,
                                               relatedExpBinding.getClientId());
                result = false;
            }
        }
        if (currentSalBinding.getValue() == null || currentSalBinding.getValue() == "") {
            String cid = currentSalBinding.getClientId();
            message = "Please enter Current Salary.";
            showMessage(message, cid);
            result = false;
        } else {
            BigDecimal CmpreZro = BigDecimal.ZERO;
            BigDecimal value = new BigDecimal(currentSalBinding.getValue().toString());
            int newvalue = value.compareTo(CmpreZro);


            if (newvalue <= 0) {

                String msg1 = "Current salary can not be zero or less than zero";
                ADFModelUtils.showFacesMessage(msg1, msg1, FacesMessage.SEVERITY_ERROR,
                                               currentSalBinding.getClientId());
                return false;
            }

        }
        if (expectedSalBinding.getValue() == null || expectedSalBinding.getValue() == "") {
            String cid = expectedSalBinding.getClientId();
            message = "Please enter expected salary.";
            showMessage(message, cid);
            result = false;
        } else {
            BigDecimal CopZero = BigDecimal.ZERO;
            BigDecimal expectedSalary = new BigDecimal(expectedSalBinding.getValue().toString());
            int newval = expectedSalary.compareTo(CopZero);
            if (newval <= 0) {
                String msg4 = "expected salary.can not be zero or less than";
                ADFModelUtils.showFacesMessage(msg4, msg4, FacesMessage.SEVERITY_ERROR,
                                               relatedExpBinding.getClientId());
                result = false;
            }

        }
        return result;
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
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

    public void chkAllDuplicateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {


        if (object != null) {
            System.out.println("Link id for validator Is  = " + uIComponent.getId());
            String textIdforValidation = uIComponent.getId();

            String amImplMthdName = "";
            String chkRsult = "";
            OperationBinding opChk = null;
            if (textIdforValidation != null) {
                if (textIdforValidation.equals("cndtLanguage")) {
                    String cndtLangNm = object.toString();
                    opChk = ADFBeanUtils.getOperationBinding("chkDupliaceLanguage");
                    opChk.getParamsMap().put("cndtLangNm", cndtLangNm);
                    opChk.execute();
                }
                if (textIdforValidation.equals("cndtSkillName")) {
                    String cndtSkillNm = object.toString();
                    opChk = ADFBeanUtils.getOperationBinding("chkDupliaceSkills");
                    opChk.getParamsMap().put("cndtSkillNm", cndtSkillNm);
                    opChk.execute();
                }
                if (textIdforValidation.equals("cndtCourceName")) {
                    System.out.println("cndtCourceNamebean");
                    String cndtCourceId = object.toString();
                    opChk = ADFBeanUtils.getOperationBinding("chkDupliaceCource");
                    opChk.getParamsMap().put("cndtCourceId", cndtCourceId);
                    opChk.execute();

                    System.out.println("111cndtCourceId" + cndtCourceId);
                }

                if (textIdforValidation.equals("cndtCompName")) {
                    System.out.println("cndtCompName");
                    String cndtCompName = object.toString();
                    opChk = ADFBeanUtils.getOperationBinding("chkDupliaceCompanyName");
                    opChk.getParamsMap().put("cndtCompName", cndtCompName);
                    System.out.println("cndtCompName" + cndtCompName);
                    opChk.execute();
                }


            }
            if (opChk.getErrors().size() == 0 && opChk.getResult() != null) {
                chkRsult = (String) opChk.getResult();
                if (chkRsult.equals("Y")) {
                    showFacesMessage("Duplicate entry!!", "E", false, "V");
                }
            } else {
                System.out.println("Error during duplicate check =" + opChk.getErrors());
            }


        }

    }

    public void setCompanyNameBinding(RichInputText companyNameBinding) {
        this.companyNameBinding = companyNameBinding;
    }

    public RichInputText getCompanyNameBinding() {
        return companyNameBinding;
    }


    public void emailValidationCondidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null && object.toString().trim().length() > 0) {
            String name = object.toString();
            String expression =
                "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-_.]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            // String expression = "^([a-zA-Z0-9_-.]+)@([a-zA-Z0-9_-.]+).([a-zA-Z]{2,5})$";
            CharSequence inputStr = name;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String msg = "Email is not in Proper Format";
            if (matcher.matches()) {
                System.out.println("Inside duplicate employee MAIL id validator");
                if (object != null && object.toString().length() > 0) {
                    OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkDupliMailId");
                    opChk.getParamsMap().put("mailId", object);
                    opChk.execute();
                    if (opChk.getErrors().size() > 0)
                        System.out.println("Error on check duplicate=" + opChk.getErrors());
                    else {
                        if (opChk.getResult() == null || opChk.getResult().toString().equals("Y"))
                            showFacesMessage("MSG.1481", "E", true, "V");
                    }

                }

            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }


    }

    public void phoneValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String msg2 = "";
        if (object != null && object.toString().length() > 0) {
            String phnNo = object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = phnNo.toCharArray();
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
            if (openB != closeB || closeFg == true || (phnNo.lastIndexOf("(") > phnNo.lastIndexOf(")")) ||
                (phnNo.indexOf(")") < phnNo.indexOf("("))) {
                msg2 = "Brackets not closed properly.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (phnNo.contains("()")) {
                msg2 = "Empty Brackets are not allowed.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (phnNo.contains("(.") || phnNo.contains("(-") || phnNo.contains("-)")) {
                msg2 = "Invalid Phone Number.Check content inside brackets.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            openB = 0;
            closeB = 0;
            closeFg = false;
            //check for valid language name.Allowed- brackets,dots,hyphen
            String expression = "([0-9\\-\\+\\(\\)]+)";
            CharSequence inputStr = phnNo;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = "Invalid Phone Number";
            System.out.println("Index of plus is--->" + phnNo.lastIndexOf("+"));
            System.out.println("Bracket index--->" + phnNo.charAt(0));
            if (matcher.matches()) {
                if (phnNo.contains("++") || phnNo.contains("--")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  "Can not contain two hyphen(--) or plus(++)"));
                } else if (phnNo.lastIndexOf("+") > 1) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  "Plus sign should be in proper place"));
                } else if (phnNo.lastIndexOf("+") == 1 && phnNo.charAt(0) != '(') {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  "Plus sign should be in proper place"));
                } else if (phnNo.startsWith(" ") || phnNo.endsWith(" ")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                  "Space Not allowed at start and end"));
                }
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                              "Only numeric character,+,() and - allowed"));
            }
        }


    }

   
    public void setCandidateTypeBinding(RichSelectOneChoice candidateTypeBinding) {
        this.candidateTypeBinding = candidateTypeBinding;
    }

    public RichSelectOneChoice getCandidateTypeBinding() {
        return candidateTypeBinding;
    }

}
