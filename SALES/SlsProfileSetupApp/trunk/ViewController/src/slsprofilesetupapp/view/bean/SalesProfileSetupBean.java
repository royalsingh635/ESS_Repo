package slsprofilesetupapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import java.util.ArrayList;

import java.util.Map;

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
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.util.FacesMessageUtils;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;
import oracle.jbo.rules.JboPrecisionScaleValidator;

import slsprofilesetupapp.model.sequence.DiscPlcItm;


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
    private RichInputListOfValues coANmLovBindVar;
    private RichSelectOneChoice paymentLovBindVar;
    private RichLink attachButtonBindVar;
    private RichLink delAttachbuttonBindVar;
    private ArrayList<DiscPlcItm> list = null;

    public void setList(ArrayList<DiscPlcItm> list) {
        this.list = list;
    }

    public ArrayList<DiscPlcItm> getList() {
        if (list == null) {
            list = new ArrayList<DiscPlcItm>();
            OperationBinding binding = ADFBeanUtils.getOperationBinding("getDiscountPolicySeq");
            if (binding != null) {
                binding.execute();
                Object object = binding.getResult();
                list = (ArrayList<DiscPlcItm>) object;
                if(list.size() == 0){
                    list = null;
                }
            }
        }
        return list;
    }


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
            list=null;
            OperationBinding operationBinding = getBindings().getOperationBinding("Rollback");
            operationBinding.execute();
            setMode("V");
        }
        return null;
    }


    public String deleteSalesProfileSetupAction() {
        // Add event code here...
        if (mode.equalsIgnoreCase("V")) {
            ADFBeanUtils.findOperation("removeDiscPolySeq").execute();
            list = null;
            OperationBinding operationBinding2 = getBindings().getOperationBinding("removeAllfromOrgSLSCoa");
            operationBinding2.execute();
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

    /**
     * Method to Save Sales Profile
     * @param actionEvent
     */
    public void saveActionListener(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("areValidationsValid");
        binding.execute();
        if (binding.getResult().equals((Object) true)) {
            OperationBinding binding_2 = ADFBeanUtils.getOperationBinding("setDiscountPolicyDataOnSave");
            binding_2.getParamsMap().put("list", list);
            binding_2.execute();
            if(binding_2.getErrors().isEmpty()){
                OperationBinding operationBinding = getBindings().getOperationBinding("Commit1");
                operationBinding.execute();
                logAdf.info("Commit sucessfull : " + binding.getResult());
                if (operationBinding.getErrors().isEmpty()) {
                    String msg = ADFModelUtils.resolvRsrc("MSG.2533");    //Sales Profile is saved sucessfully !
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                    setMode("V");
                    list = null;
                } else {
                    String msg = ADFModelUtils.resolvRsrc("MSG.2534");  //There have been an error in saving Sales Profile. Please try again !
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            }
        }
    }

    public void createPrfActionListener(ActionEvent actionEvent) {
        try {
            OperationBinding operationBinding = getBindings().getOperationBinding("createNewSlsPrf");
            operationBinding.execute();
            ADFBeanUtils.getOperationBinding("populateDiscountPlcDataOnAdd").execute();
            setMode("A");
        }catch (Exception e) {
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
        Boolean b = (Boolean) valueChangeEvent.getNewValue();
        if (b == false) {
            soAdvTypebVar.setValue("A");

            AdfFacesContext.getCurrentInstance().addPartialTarget(soAdvTypebVar);
            soAdvValBVar.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soAdvValBVar);
        }

    }

    public void stpSaleRtnVCL(ValueChangeEvent valueChangeEvent) {
        System.out.println("VLC stopSalesRtn===" + valueChangeEvent.getNewValue());
        Boolean c = (Boolean) valueChangeEvent.getNewValue();
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
            Number soadvVal = (Number) object;
            String soAdvType = (String) soAdvTypebVar.getValue();
            Boolean totFlag = isPrecisionValid(26, 6, (Number) object);
            if (totFlag.equals(false)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvEl("#{bundle['MSG.405']}"), null));
            } else if ("A".equalsIgnoreCase(soAdvType)) {
                if (soadvVal.compareTo(0) < 0) {
                    System.out.println("amout is negative....");
                    FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2536"));    //Value Can Not be Negative
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }
            } else if ("P".equalsIgnoreCase(soAdvType)) {
                System.out.println("soadvVal.compareTo(99.99) =" + soadvVal.compareTo(99.99));
                if (soadvVal.compareTo(0) < 0) {
                    System.out.println("amout is negative....");
                    FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2536"));          //Value Can Not Be Negative
                    // FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.994']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                } else if (soadvVal.compareTo(99.99) == 1) {
                    throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1293"))); //MRP Rate should be less then 99.99%..
                }
            }
        }
    }

    public void daysValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("object " + object);
        if (object != null) {
            Integer days = (Integer) object;
            if (days.compareTo(0) < 0) {
                System.out.println("amout is negative....");
                FacesMessage message =
                    new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2536")); //MRP Rate can not be either Zero or negative.
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

    /**
     * Method to fetch Component client id
     * @param comp
     * @param id
     * @return
     */
    public String getComponentCliendIdFromId(UIComponent comp, String id) {
        if (comp != null) {
            StringBuilder clientId = new StringBuilder(comp.getClientId());
            clientId = new StringBuilder(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            return clientId.append(id).toString();
        } else {
            return null;
        }
    }


    /**
     * Method to add COA to profile
     * @return
     * 0 :  Added SuccessFully
     * 1 :  Coa is not Defined
     * 2 :  PymntMode is not Selected
     * 3 :  Duplicate Coa and Payment Combination
     */
    public void attachCoaNPayment(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("addCoaToProfile");
        binding.execute();
        Object o = binding.getResult();
        Integer i = (o == null ? -1 : (Integer) o);
        if (i == 1) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2543"),    //  Coa is not Selected !
                                           ADFModelUtils.resolvRsrc("MSG.2544"),   //Please Select a Coa which will be attached to Payment mode.
                                           FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(actionEvent.getComponent(), "transCoaNmId"));
        } else if (i == 2) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2545"),         //Payment mode is not Selected !
                                           ADFModelUtils.resolvRsrc("MSG.2546"),             // Please Select a Payment mode.
                                           FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(actionEvent.getComponent(), "soc10"));
        } else if (i == 3) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2547"),       //Duplicate Coa and Payment is Selected !
                                           ADFModelUtils.resolvRsrc("MSG.2548"),  //The selected Coa with Payment is already defined. Please select a different Combination of Coa and Payment.
                                           FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(actionEvent.getComponent(), "soc10"));
        } else if (i == -1) {
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.2602"),   //There have been error while adding Coa with Payment.
                                                    ADFModelUtils.resolvRsrc("MSG.2603"), FacesMessage.SEVERITY_ERROR);     //Please contact Ess !
        }
    }


    public void delAttachedCoaWithPayment(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding binding = this.getBindings().getOperationBinding("Delete1");
        binding.execute();
    }

    public void postSoAdvncVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(coANmLovBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(paymentLovBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(attachButtonBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(delAttachbuttonBindVar);
    }

    public void setCoANmLovBindVar(RichInputListOfValues coANmLovBindVar) {
        this.coANmLovBindVar = coANmLovBindVar;
    }

    public RichInputListOfValues getCoANmLovBindVar() {
        return coANmLovBindVar;
    }

    public void setPaymentLovBindVar(RichSelectOneChoice paymentLovBindVar) {
        this.paymentLovBindVar = paymentLovBindVar;
    }

    public RichSelectOneChoice getPaymentLovBindVar() {
        return paymentLovBindVar;
    }

    public void setAttachButtonBindVar(RichLink attachButtonBindVar) {
        this.attachButtonBindVar = attachButtonBindVar;
    }

    public RichLink getAttachButtonBindVar() {
        return attachButtonBindVar;
    }

    public void setDelAttachbuttonBindVar(RichLink delAttachbuttonBindVar) {
        this.delAttachbuttonBindVar = delAttachbuttonBindVar;
    }

    public RichLink getDelAttachbuttonBindVar() {
        return delAttachbuttonBindVar;
    }

    /**
     * Method to move discount policy sequence up
     * @param ae
     */
    public void moveUp(ActionEvent ae) {
        Map<String, Object> attributes = ae.getComponent().getAttributes();
        Object row = attributes.get("row");
        Object i = attributes.get("index");
        Integer index = (Integer) i;
        if (index > 0) {
            System.out.println("Index : " + index + " Row : " + row.toString());
            DiscPlcItm arrayListHelper = list.get(index - 1);
            list.set(index - 1, (DiscPlcItm) row);
            list.set(index, arrayListHelper);
        }
        System.out.println(list.toString());
    }
    /**
     * Method to move discount policy sequence down
     * @param ae
     */
    public void moveDown(ActionEvent ae) {
        Map<String, Object> attributes = ae.getComponent().getAttributes();
        Object row = attributes.get("row");
        Object i = attributes.get("index");
        Integer index = (Integer) i;
        if (index < 4) {
            System.out.println("Index : " + index + " Row : " + row.toString());
            DiscPlcItm arrayListHelper = list.get(index + 1);
            list.set(index + 1, (DiscPlcItm) row);
            list.set(index, arrayListHelper);
        }
        System.out.println(list.toString());
    }

}
