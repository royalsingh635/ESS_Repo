package slsprofilesetupapp.view.bean;

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
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;
import oracle.jbo.rules.JboPrecisionScaleValidator;


public class SalesProfileSetupBean {
    private String mode = "V";
    private RichSelectOneChoice organisationBinding;
    private RichSelectOneChoice orgIdBindVar;
    private RichPanelFormLayout dtlForm;
    private RichSelectBooleanCheckbox freezeProfileBinding;
    private RichSelectOneRadio soAdvTypebVar;
    private RichInputText soAdvValBVar;
    private RichInputText daysBVar;
    private RichSelectBooleanCheckbox stopRmacheckbVar;
    private RichSelectBooleanCheckbox chkAdvcPmtOfSoBVar;


    public SalesProfileSetupBean() {
    }

    private static ADFLogger logAdf = ADFLogger.createADFLogger(SalesProfileSetupBean.class);


    public static BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    /**
     *
     * @param data
     * @return
     */
    public String resolvEl(String data) {
        System.out.println(data);
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        System.out.println(msg);

        return msg;
    }


    /**
     *
     * @param data
     * @return
     */
    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    /**
     *
     * @param data
     * @return
     */
    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }


    /*  public String addProfileSetupAction() {

        logAdf.info("mode value : " + mode);
        try {
            if (mode.equalsIgnoreCase("V") &&
                (organisationBinding.getValue() == null && orgIdBindVar.getValue() != null)) {

                setMode("A");
                logAdf.info("ADD Action");
                logAdf.info("mode value : " + mode);
                OperationBinding operationBinding = getBindings().getOperationBinding("Createwithparameters");
                operationBinding.getParamsMap().put("OrgId", orgIdBindVar.getValue());
                operationBinding.getParamsMap().put("HoOrgId", resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}"));
                operationBinding.getParamsMap().put("CldId", resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
                operationBinding.getParamsMap().put("UsrIdCreate", resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
                operationBinding.getParamsMap().put("SlocId", resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                operationBinding.execute();

            }
        } catch (Exception e) {
             TODO: Add catch code
            e.printStackTrace();
        }

        return null;
    }  */


    public String editSalesProfileSetupAction() {
        System.out.println("enetrt edit");
        if (mode.equalsIgnoreCase("V")) {
            System.out.println("set 111");
            setMode("E");
            System.out.println(getMode());
        }
        return null;
    }

    public String cancelSalesProfileSetupAction() {
        // Add event code here...
        if (mode.equalsIgnoreCase("A") || mode.equalsIgnoreCase("E")) {
            OperationBinding operationBinding = getBindings().getOperationBinding("Rollback");
            operationBinding.execute();
            setMode("V");
        }
        return null;
    }

    /* public String saveSalesProfileSetupAction() {
         System.out.println("Save Method");
        if (mode.equalsIgnoreCase("A")||mode.equalsIgnoreCase("E")) {
            OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
            operationBinding.execute();
            logAdf.info("Saved");
            //String msg = "Record Saved Successfully!";
       FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.818']}").toString());//Record Saved Successfully!
            //FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            setMode("V");
        }
        return null;
    }*/

    public String deleteSalesProfileSetupAction() {
        // Add event code here...
        if (mode.equalsIgnoreCase("V")) {
            OperationBinding operationBinding = getBindings().getOperationBinding("Delete");
            operationBinding.execute();
            OperationBinding operationBinding1 = getBindings().getOperationBinding("Commit");
            operationBinding1.execute();
        }
        return null;
    }

    public String profileSetupSearchAction() {
        // Add event code here...
        if (mode.equalsIgnoreCase("S")) {
            if (orgIdBindVar.getValue() != null) {
                logAdf.info("orgId : " + orgIdBindVar.getValue());

                /*operationBinding.getParamsMap().put("orgIdBindVar", orgIdBindVar.getValue());
                operationBinding.execute();
                operationBinding = getBindings().getOperationBinding("Execute");
                operationBinding.execute(); */
                OperationBinding operationBinding = getBindings().getOperationBinding("ExecuteWithParams");
                operationBinding.getParamsMap().put("orgIdBindVar", null);
                operationBinding.execute();

                operationBinding = getBindings().getOperationBinding("Execute");
                operationBinding.execute();

                operationBinding = getBindings().getOperationBinding("getResetSearchByOrg");
                operationBinding.getParamsMap().put("orgIdBindVar", null);
                operationBinding.execute();

                operationBinding = getBindings().getOperationBinding("getSearchByOrg");
                operationBinding.getParamsMap().put("organisation", orgIdBindVar.getValue());
                operationBinding.execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(organisationBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(dtlForm);
            }
        }
        setMode("V");
        return null;
    }


    public String resetProfileSetupAction() {
        // Add event code here...

        if (mode.equalsIgnoreCase("V")) {

            logAdf.info("reset Action");
            OperationBinding operationBinding = getBindings().getOperationBinding("ExecuteWithParams");
            operationBinding.getParamsMap().put("orgIdBindVar", null);
            operationBinding.execute();
            operationBinding = getBindings().getOperationBinding("Execute");
            operationBinding.execute();
            operationBinding = getBindings().getOperationBinding("getResetSearchByOrg");
            operationBinding.getParamsMap().put("orgIdBindVar", null);
            operationBinding.execute();
            //getResetSearchByOrg
            //setOrgIdBindVar(null);
            orgIdBindVar.setValue("0");
        }
        setMode("S");
        return null;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        if (freezeProfileBinding.getValue() == "Y") {
            return "F";
        }
        return mode;
    }

    public void setOrganisationBinding(RichSelectOneChoice organisationBinding) {
        this.organisationBinding = organisationBinding;
    }

    public RichSelectOneChoice getOrganisationBinding() {
        return organisationBinding;
    }


    public void setOrgIdBindVar(RichSelectOneChoice orgIdBindVar) {
        this.orgIdBindVar = orgIdBindVar;
    }

    public RichSelectOneChoice getOrgIdBindVar() {
        return orgIdBindVar;
    }

    public void setDtlForm(RichPanelFormLayout dtlForm) {
        this.dtlForm = dtlForm;
    }

    public RichPanelFormLayout getDtlForm() {
        return dtlForm;
    }


    public void setFreezeProfileBinding(RichSelectBooleanCheckbox freezeProfileBinding) {
        this.freezeProfileBinding = freezeProfileBinding;
    }

    public RichSelectBooleanCheckbox getFreezeProfileBinding() {
        return freezeProfileBinding;
    }

    public void saveActionListener(ActionEvent actionEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("areValidationsValid");
        binding.execute();
        logAdf.info("Validations are ok : "+binding.getResult());
        if(binding.getResult().equals((Object)true)){
            OperationBinding operationBinding = getBindings().getOperationBinding("Commit1");
            operationBinding.execute();
            logAdf.info("Commit sucessfull : "+binding.getResult());
            if(operationBinding.getErrors().isEmpty()){
                String msg = "Sales Profile is saved sucessfully !";
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                setMode("V");
            }else{
                String msg = "There have been an error in saving Sales Profile. Please try again !";
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
            //String msg = resolvElDCMsg("#{bundle['MSG.818']}").toString(); //Record Saved Successfully!
        }/* else{
            String msg = "There have been an error in saving the record. Please try again!";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } */
        /* OperationBinding validations = this.getBindings().getOperationBinding("areValidationsOk");
        validations.execute();
        System.out.println("Save Method returned : "+validations.getResult());
        if(validations.getResult().equals((Object)false)){

        }else  if (mode.equalsIgnoreCase("A") || mode.equalsIgnoreCase("E")) {
            System.out.println("chkAdvcPmtOfSoBVar=" + chkAdvcPmtOfSoBVar.getValue() + "stopRmacheckbVar" +
                               stopRmacheckbVar.getValue());
            Boolean advCheck = (Boolean)chkAdvcPmtOfSoBVar.getValue();
            Boolean stoRma = (Boolean)stopRmacheckbVar.getValue();
            if(advCheck == true){
                if (soAdvValBVar.getValue() == null || soAdvValBVar.getValue().toString().length() < 0) {
                    System.out.println("set 2");
                    FacesMessage message =
                        new FacesMessage("You Have Selected Advance Payment in Sales Order. Please Enter Advance Value");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(soAdvValBVar.getClientId(), message);
                }
            }else if (stoRma == true) {
                System.out.println("set 1" + soAdvValBVar.getValue());
                System.out.println("set 3" + daysBVar.getValue());
                 if (daysBVar.getValue() == null || daysBVar.getValue().toString().length() < 0) {
                    System.out.println("set 4");
                    FacesMessage message = new FacesMessage("You Have Selected Stop Rma .Please Enter Days");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(daysBVar.getClientId(), message);
                } else {
                    System.out.println("set 5");
                    OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                    operationBinding.execute();
                    logAdf.info("Saved");
                    String msg = resolvElDCMsg("#{bundle['MSG.818']}").toString(); //Record Saved Successfully!

                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                    setMode("V");
                }
            } else {
                System.out.println("set 15"); //
                /* OperationBinding setUserId = getBindings().getOperationBinding("setUserIdCreateOnsaveVlick");
                setUserId.execute(); 
                OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                operationBinding.execute();
                logAdf.info("Saved");
                String msg = resolvElDCMsg("#{bundle['MSG.818']}").toString(); //Record Saved Successfully!
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                setMode("V");
            }

        } */
    }

    public void createPrfActionListener(ActionEvent actionEvent) {
        logAdf.info("mode value : " + mode);
        try {
            System.out.println("org  " + organisationBinding.getValue());
            // if (organisationBinding.getValue()!= null) {
            setMode("A");
            logAdf.info("ADD Action");
            logAdf.info("mode value : " + mode);
            OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsert");
            operationBinding.execute();
            

        }
        // }
        catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }

    }
    /**Method to check invalid precision*/
    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void setSoAdvTypebVar(RichSelectOneRadio soAdvTypebVar) {
        this.soAdvTypebVar = soAdvTypebVar;
    }

    public RichSelectOneRadio getSoAdvTypebVar() {
        return soAdvTypebVar;
    }

    public void setSoAdvValBVar(RichInputText soAdvValBVar) {
        this.soAdvValBVar = soAdvValBVar;
    }

    public RichInputText getSoAdvValBVar() {
        return soAdvValBVar;
    }

    public void chkSoAdvVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("VLC chkSoAdvVCL===" + valueChangeEvent.getNewValue());
        Boolean b = (Boolean)valueChangeEvent.getNewValue();
        if (b == false) {
            soAdvTypebVar.setValue("A");

            AdfFacesContext.getCurrentInstance().addPartialTarget(soAdvTypebVar);
            soAdvValBVar.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soAdvValBVar);
        }

    }

    public void stpSaleRtnVCL(ValueChangeEvent valueChangeEvent) {
        System.out.println("VLC stopSalesRtn===" + valueChangeEvent.getNewValue());
        Boolean c = (Boolean)valueChangeEvent.getNewValue();
        if (c == false) {
            daysBVar.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(daysBVar);
        }

    }

    public void setDaysBVar(RichInputText daysBVar) {
        this.daysBVar = daysBVar;
    }

    public RichInputText getDaysBVar() {
        return daysBVar;
    }

    public void soAdvValValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("object  " + object + " type==" + soAdvTypebVar.getValue());
        if (object != null) {
            Number soadvVal = (Number)object;
            String soAdvType = (String)soAdvTypebVar.getValue();
            Boolean totFlag = isPrecisionValid(26, 6, (Number)object);
            if (totFlag.equals(false)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.405']}"), null));
            }else if ("A".equalsIgnoreCase(soAdvType)) {
                if (soadvVal.compareTo(0) < 0) {
                    System.out.println("amout is negative....");
                    FacesMessage message = new FacesMessage("Value Can Not be Negative");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }
            } else if ("P".equalsIgnoreCase(soAdvType)) {
                System.out.println("soadvVal.compareTo(99.99) =" + soadvVal.compareTo(99.99));
                if (soadvVal.compareTo(0) < 0) {
                    System.out.println("amout is negative....");
                    FacesMessage message = new FacesMessage("Value Can Not Be Negative");
                    // FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.994']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                } else if (soadvVal.compareTo(99.99) == 1) {
                    throw new ValidatorException(new FacesMessage("Value Should be Less then 99.99")); //MRP Rate should be less then 99.99%..
                }
            }
        }
    }

    public void daysValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("object " + object);
        if (object != null) {
            Integer days = (Integer)object;
            if (days.compareTo(0) < 0) {
                System.out.println("amout is negative....");
                FacesMessage message =
                    new FacesMessage("Value Can Not be Negative"); //MRP Rate can not be either Zero or negative.
                // FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.994']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }

    public void setStopRmacheckbVar(RichSelectBooleanCheckbox stopRmacheckbVar) {
        this.stopRmacheckbVar = stopRmacheckbVar;
    }

    public RichSelectBooleanCheckbox getStopRmacheckbVar() {
        return stopRmacheckbVar;
    }

    public void setChkAdvcPmtOfSoBVar(RichSelectBooleanCheckbox chkAdvcPmtOfSoBVar) {
        this.chkAdvcPmtOfSoBVar = chkAdvcPmtOfSoBVar;
    }

    public RichSelectBooleanCheckbox getChkAdvcPmtOfSoBVar() {
        return chkAdvcPmtOfSoBVar;
    }
}
