package hcminjuryform.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class AddInjuryBean {
    public String mode = "E";
    public String hospital = "A";
    public String insurance = "A";
    public String informToKin = "A";
    public String policeCase = "A";
    public String labourCase = "A";
    public String compensation = "A";
    public String checkBoxMode = "V";
    private RichSelectOneChoice injuryTypeBind;
    private RichInputText compensationAmtBind;
    private RichInputText hospitalAddBind;
    private RichInputText hospitalNmBind;
    private RichInputText relativePhnBind;
    private RichInputText relativeNmBind;
    private RichInputText policeStationBind;
    private RichInputText insuaranceAmtBind;
    private RichInputText labourCourtAddBind;
    private RichInputText firNoBind;
    private RichInputText casefileNoBind;
    private RichSelectBooleanCheckbox compensationBind;
    private RichSelectBooleanCheckbox policeBind;
    private RichSelectBooleanCheckbox labourBind;
    private RichSelectBooleanCheckbox informBind;
    private RichSelectBooleanCheckbox insuranceBind;
    private RichSelectBooleanCheckbox hospitalBind;
    private RichButton addInjuryBind;

    public AddInjuryBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void addInquiryDetails(ActionEvent actionEvent) {
        System.out.println("In VVVVV");
        checkBoxMode = "E";
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getHospital() {
        return hospital;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInformToKin(String informToKin) {
        this.informToKin = informToKin;
    }

    public String getInformToKin() {
        return informToKin;
    }

    public void setPoliceCase(String policeCase) {
        this.policeCase = policeCase;
    }

    public String getPoliceCase() {
        return policeCase;
    }

    public void setLabourCase(String labourCase) {
        this.labourCase = labourCase;
    }

    public String getLabourCase() {
        return labourCase;
    }

    public void setCompensation(String compensation) {
        this.compensation = compensation;
    }

    public String getCompensation() {
        return compensation;
    }

    public void setCheckBoxMode(String checkBoxMode) {
        this.checkBoxMode = checkBoxMode;
    }

    public String getCheckBoxMode() {
        return checkBoxMode;
    }

    public void addInjuryAL(ActionEvent actionEvent) {
        mode = "A";
        OperationBinding _searchInjury = getBindings().getOperationBinding("CreateInsert");
        ADFBeanUtils.callBindingsMethod("refreshDual", null, null);
        _searchInjury.execute();
    }

    public void editInjuryAL(ActionEvent actionEvent) {
        mode = "A";
        System.out.println("compensationBind ::: "+compensationBind.getValue());
        if(compensationBind.getValue().equals(true)){
            System.out.println("compensationBind.getValue() inside"+compensationBind.getValue());
            compensation="V";
            AdfFacesContext.getCurrentInstance().addPartialTarget(compensationAmtBind);
        }
         if(hospitalBind.getValue().equals(true)){
            hospital="V";
            AdfFacesContext.getCurrentInstance().addPartialTarget(hospitalAddBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(hospitalNmBind);
            
        }
         if(policeBind.getValue().equals(true)){
            policeCase="V";
            AdfFacesContext.getCurrentInstance().addPartialTarget(policeStationBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(firNoBind);
        }
       if(insuranceBind.getValue().equals(true)){
            insurance="V";
            AdfFacesContext.getCurrentInstance().addPartialTarget(insuaranceAmtBind);
        }
         if(informBind.getValue().equals(true)){
            informToKin="V";
            AdfFacesContext.getCurrentInstance().addPartialTarget(relativeNmBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(relativePhnBind);
            
        }
         if(labourBind.getValue().equals(true)){
            labourCase = "V";
            labourCourtAddBind.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(labourCourtAddBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(casefileNoBind);
        }
    }

    public void saveInjuryACL(ActionEvent actionEvent) {
        mode = "E";
        System.out.println("SYSYEM SAVE");
        if (injuryTypeBind.getValue().equals(127)) {
            OperationBinding _insertInquiry = getBindings().getOperationBinding("insertIntoInquiryDtl");
            _insertInquiry.execute();
            Boolean object = (Boolean) _insertInquiry.getResult();
            if (object) {
                OperationBinding _searchInjury = getBindings().getOperationBinding("Commit");
                _searchInjury.execute();
                ADFModelUtils.showFormattedFacesMessage("Record Have been saved Successfully!!!", " ", FacesMessage.SEVERITY_INFO);
            }
        } else {
            OperationBinding _searchInjury = getBindings().getOperationBinding("Commit");
            _searchInjury.execute();
            ADFModelUtils.showFormattedFacesMessage("Record Have been saved Successfully!!!", " ", FacesMessage.SEVERITY_INFO);
        }
       
    }

    public void cancelACL(ActionEvent actionEvent) {
        mode = "E";
        checkBoxMode = "E";
        OperationBinding _searchInjury = getBindings().getOperationBinding("Rollback");
        _searchInjury.execute();
    }

    public void insertIntoInquiryACL(ActionEvent actionEvent) {
        OperationBinding _searchInjury = getBindings().getOperationBinding("insertIntoTheInquiry");
        _searchInjury.execute();
    }

    public void compensationVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            String flag = vce.getNewValue().toString();
            System.out.println("value of compensation" + flag);
            if (flag.equals("true")) {
                compensation = "V";
                AdfFacesContext.getCurrentInstance().addPartialTarget(compensationAmtBind);
                vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            } else {
                compensation = "A";
                compensationAmtBind.setValue(0);
                AdfFacesContext.getCurrentInstance().addPartialTarget(compensationAmtBind);
                vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            }

        }


    }

    public void hospitalVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            String flag = vce.getNewValue().toString();
            System.out.println("value of compensation" + flag);
            if (flag.equals("true")) {
                hospital = "V";

                AdfFacesContext.getCurrentInstance().addPartialTarget(hospitalAddBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(hospitalNmBind);
                vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            } else {
                hospital = "A";
                hospitalAddBind.setValue(" ");
                hospitalNmBind.setValue(" ");
                AdfFacesContext.getCurrentInstance().addPartialTarget(hospitalAddBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(hospitalNmBind);
                vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            }

        }

    }

    public void informToKinVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            String flag = vce.getNewValue().toString();
            System.out.println("value of compensation" + flag);
            if (flag.equals("true")) {
                informToKin = "V";
                AdfFacesContext.getCurrentInstance().addPartialTarget(relativeNmBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(relativePhnBind);
                vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            } else {
                informToKin = "A";
                relativeNmBind.setValue(" ");
                relativePhnBind.setValue(" ");
                AdfFacesContext.getCurrentInstance().addPartialTarget(relativeNmBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(relativePhnBind);
                vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            }
        }

    }

    public void insuranceVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            String flag = vce.getNewValue().toString();
            System.out.println("value of compensation" + flag);
            if (flag.equals("true")) {
                insurance = "V";
                AdfFacesContext.getCurrentInstance().addPartialTarget(insuaranceAmtBind);
                // AdfFacesContext.getCurrentInstance().addPartialTarget(relativePhnBind);
                vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            } else {
                insurance = "A";
                insuaranceAmtBind.setValue(0);
                AdfFacesContext.getCurrentInstance().addPartialTarget(insuaranceAmtBind);
                // AdfFacesContext.getCurrentInstance().addPartialTarget(relativePhnBind);
                vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            }
        }

    }

    public void labourOfficerVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            String flag = vce.getNewValue().toString();
            System.out.println("value of compensation" + flag);
            if (flag.equals("true")) {
                labourCase = "V";
                labourCourtAddBind.setVisible(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(labourCourtAddBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(casefileNoBind);
                vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            } else {
                labourCase = "A";
                labourCourtAddBind.setVisible(false);
                labourCourtAddBind.setValue(" ");
                casefileNoBind.setValue(" ");
                AdfFacesContext.getCurrentInstance().addPartialTarget(labourCourtAddBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(casefileNoBind);
                vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            }
        }

    }

    public void policeCaseVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            String flag = vce.getNewValue().toString();
            System.out.println("value of compensation" + flag);
            if (flag.equals("true")) {
                policeCase = "V";
                AdfFacesContext.getCurrentInstance().addPartialTarget(policeStationBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(firNoBind);
                vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            } else {
                policeCase = "A";
                policeStationBind.setValue(" ");
                firNoBind.setValue(" ");
                AdfFacesContext.getCurrentInstance().addPartialTarget(policeStationBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(firNoBind);
                vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            }
        }

    }

    public void setInjuryTypeBind(RichSelectOneChoice injuryTypeBind) {
        this.injuryTypeBind = injuryTypeBind;
    }

    public RichSelectOneChoice getInjuryTypeBind() {
        return injuryTypeBind;
    }

    public String modifyMode() {
        mode = "A";
        return "modifyMode";
    }

    public void setCompensationAmtBind(RichInputText compensationAmtBind) {
        this.compensationAmtBind = compensationAmtBind;
    }

    public RichInputText getCompensationAmtBind() {
        return compensationAmtBind;
    }

    public void setHospitalAddBind(RichInputText hospitalAddBind) {
        this.hospitalAddBind = hospitalAddBind;
    }

    public RichInputText getHospitalAddBind() {
        return hospitalAddBind;
    }

    public void setHospitalNmBind(RichInputText hospitalNmBind) {
        this.hospitalNmBind = hospitalNmBind;
    }

    public RichInputText getHospitalNmBind() {
        return hospitalNmBind;
    }

    public void setRelativePhnBind(RichInputText relativePhnBind) {
        this.relativePhnBind = relativePhnBind;
    }

    public RichInputText getRelativePhnBind() {
        return relativePhnBind;
    }

    public void setRelativeNmBind(RichInputText relativeNmBind) {
        this.relativeNmBind = relativeNmBind;
    }

    public RichInputText getRelativeNmBind() {
        return relativeNmBind;
    }

    public void setPoliceStationBind(RichInputText policeStationBind) {
        this.policeStationBind = policeStationBind;
    }

    public RichInputText getPoliceStationBind() {
        return policeStationBind;
    }

    public void setInsuaranceAmtBind(RichInputText insuaranceAmtBind) {
        this.insuaranceAmtBind = insuaranceAmtBind;
    }

    public RichInputText getInsuaranceAmtBind() {
        return insuaranceAmtBind;
    }

    public void setLabourCourtAddBind(RichInputText labourCourtAddBind) {
        this.labourCourtAddBind = labourCourtAddBind;
    }

    public RichInputText getLabourCourtAddBind() {
        return labourCourtAddBind;
    }

    public void setFirNoBind(RichInputText firNoBind) {
        this.firNoBind = firNoBind;
    }

    public RichInputText getFirNoBind() {
        return firNoBind;
    }

    public void setCasefileNoBind(RichInputText casefileNoBind) {
        this.casefileNoBind = casefileNoBind;
    }

    public RichInputText getCasefileNoBind() {
        return casefileNoBind;
    }

    public void phoneNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String msg2 = "";
        if (object != null) {
            String phnNo = object.toString();
            if (phnNo.length() > 10) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, " ",
                                                              "Can not contain more than ten digits!!"));
            }
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

    public void setCompensationBind(RichSelectBooleanCheckbox compensationBind) {
        this.compensationBind = compensationBind;
    }

    public RichSelectBooleanCheckbox getCompensationBind() {
        return compensationBind;
    }

    public void setPoliceBind(RichSelectBooleanCheckbox policeBind) {
        this.policeBind = policeBind;
    }

    public RichSelectBooleanCheckbox getPoliceBind() {
        return policeBind;
    }

    public void setLabourBind(RichSelectBooleanCheckbox labourBind) {
        this.labourBind = labourBind;
    }

    public RichSelectBooleanCheckbox getLabourBind() {
        return labourBind;
    }

    public void setInformBind(RichSelectBooleanCheckbox informBind) {
        this.informBind = informBind;
    }

    public RichSelectBooleanCheckbox getInformBind() {
        return informBind;
    }

    public void setInsuranceBind(RichSelectBooleanCheckbox insuranceBind) {
        this.insuranceBind = insuranceBind;
    }

    public RichSelectBooleanCheckbox getInsuranceBind() {
        return insuranceBind;
    }

    public void setHospitalBind(RichSelectBooleanCheckbox hospitalBind) {
        this.hospitalBind = hospitalBind;
    }

    public RichSelectBooleanCheckbox getHospitalBind() {
        return hospitalBind;
    }

    public void InjuryTYpeVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(addInjuryBind);
    }

    public void setAddInjuryBind(RichButton addInjuryBind) {
        this.addInjuryBind = addInjuryBind;
    }

    public RichButton getAddInjuryBind() {
        return addInjuryBind;
    }

    public void injuryTimeValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().trim().length() > 0) {
                   String name = object.toString();
                   String expression ="([01]?[0-9]|2[0-3]):[0-5][0-9]";
                   CharSequence inputStr = name;
                   Pattern pattern = Pattern.compile(expression);
                   Matcher matcher = pattern.matcher(inputStr);
                   String msg = "Time is not in Proper Format";
                   if (matcher.matches()) {
                       }
                               
                       else {
                       throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                   }

    }
    }
}
