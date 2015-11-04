package hrjobadapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.bean.StaticValue;
import org.apache.myfaces.trinidad.context.RequestContext;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
/* import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;

import oracle.adf.view.rich.context.AdfFacesContext; */

import oracle.binding.OperationBinding;
import oracle.jbo.rules.JboPrecisionScaleValidator;

public class HRJobAdvtBean {
    private RichPopup salRangePopBinding;
    private RichSelectOneChoice salCompBinding;
    private RichInputText amtFromBinding;
    private RichInputText amtToBinding;
    private RichInputDate validStrtDtBinding;
    private RichInputDate validEndDtBinding;
 //   private RichLink   saveBind;
    private RichSelectOneChoice jobTypeBinding;
 //   private RichSelectOneChoice locationBinding;
    private RichSelectOneChoice departmemtbinding;
 //   private RichSelectOneChoice designationBinding;
    private RichInputText noOfVacancyBinding;
    private RichSelectOneChoice hireTypeBinding;
    private RichInputText advrtisemnetCostBinding;
    private RichInputDate requirementValidtyDate;
    private RichInputDate validFromDateBinding;
    private RichInputListOfValues sourceNameBinding;
    private RichSelectOneChoice soureTypeBinding;
    private RichSelectOneChoice advertisementfrqyBinding;
    private RichInputText degisnationCostBinding;
    private RichInputText manPlanNo;
    private RichInputDate manPlanDt;
    private String mode="V";
 //   private RichLink addSrcButtonBind;
    private RichLink okSrcButtonBind;
    private RichInputListOfValues designationBinding;
    private RichInputListOfValues locationBinding;
    //  private RichLink  deleteSrc;
   // private RichLink okAdvtDtlButtonBind;
//    private RichLink   okDetailButtonBind;

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

   
   public String validateMainField() {
       String result = "Y";
       if (manPlanNo.getValue() == null) {
           result="N";
           String msg = "ManPowerPlanning No can not be blank";
           FacesMessage message = new FacesMessage(msg);
           message.setSeverity(FacesMessage.SEVERITY_ERROR);
           FacesContext context = FacesContext.getCurrentInstance();
           context.addMessage(manPlanNo.getClientId(), message);
           return result ;

       }
       if (manPlanDt.getValue() == null) {
           result="N";
           String msg = "ManPowerPlanning Date can not be blank";
           FacesMessage message = new FacesMessage(msg);
           message.setSeverity(FacesMessage.SEVERITY_ERROR);
           FacesContext context = FacesContext.getCurrentInstance();
           context.addMessage(manPlanDt.getClientId(), message);
           return result ;

       }
       if (hireTypeBinding.getValue() == null) {
           result="N";
           String msg = "HireType can not be blank";
           FacesMessage message = new FacesMessage(msg);
           message.setSeverity(FacesMessage.SEVERITY_ERROR);
           FacesContext context = FacesContext.getCurrentInstance();
           context.addMessage(hireTypeBinding.getClientId(), message);
           return result ;

       }
       if (advrtisemnetCostBinding.getValue() == null) {
           result="N";
           String msg = "advertisement Cost can not be blank";
           FacesMessage message = new FacesMessage(msg);
           message.setSeverity(FacesMessage.SEVERITY_ERROR);
           FacesContext context = FacesContext.getCurrentInstance();
           context.addMessage(advrtisemnetCostBinding.getClientId(), message);
           return result ;

       }
       if (requirementValidtyDate.getValue() == null) {
           result="N";
           String msg = "Requirement Validity Date can not be blank";
           FacesMessage message = new FacesMessage(msg);
           message.setSeverity(FacesMessage.SEVERITY_ERROR);
           FacesContext context = FacesContext.getCurrentInstance();
           context.addMessage(requirementValidtyDate.getClientId(), message);
           return result ;

       }
       if (validFromDateBinding.getValue() == null) {
           result="N";
           String msg = "Valid From Date can not be blank";
           FacesMessage message = new FacesMessage(msg);
           message.setSeverity(FacesMessage.SEVERITY_ERROR);
           FacesContext context = FacesContext.getCurrentInstance();
           context.addMessage(validFromDateBinding.getClientId(), message);
           return result ;

       }
     return result; 
   }


    public HRJobAdvtBean() {
    }
    public String validateField() {
        String result = "Y";
        
        if (departmemtbinding.getValue() == null) {
            result="N";
            String msg = "Department can not be blank";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(departmemtbinding.getClientId(), message);
            return result ;

        }

        if (designationBinding.getValue() == null) {
            result="N";
            String msg = "Designation can not be blank";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(designationBinding.getClientId(), message);
            return result ;

        }
        if (locationBinding.getValue() == null) {
            result="N";
            String msg = "Location can not be blank";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(locationBinding.getClientId(), message);
            return result ;

        }   
        if (jobTypeBinding.getValue() == null) {
            result="N";
            String msg = "Group Detail can not be blank";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(jobTypeBinding.getClientId(), message);
            return result ;

        }

        if (noOfVacancyBinding.getValue() == null) {
            result="N";
            String msg = "Designation can not be blank";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(noOfVacancyBinding.getClientId(), message);
            return result ;

        }
        
        Long count =
            ((DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry()).findIteratorBinding("HcmJobAdvtSal1Iterator").getEstimatedRowCount();
        System.out.println("count.intValue():::::::::"+count.intValue());
        if (count.intValue() == 0) {
            result="N";
            String msg = "Please Add Salary Range";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, message); 
            return result ;
        }   
        
        return result;
    }
    
    
    public String ValidateSourceField()
    {
        System.out.println("in validate source field");
        String result = "Y";
        if (soureTypeBinding.getValue() == null||soureTypeBinding.getValue().toString().trim().length()==0) {
            result="N";
            System.out.println("soureTypeBinding.getValue():::::::"+soureTypeBinding.getValue());
            String msg = "Source Type Field can not be blank";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(soureTypeBinding.getClientId(), message);
            return result;
        }

        if (sourceNameBinding.getValue() == null || sourceNameBinding.getValue().toString().trim().length()==0) {
            result="N";
            System.out.println("sourceNameBinding.getValue()::::::"+sourceNameBinding.getValue());
            String msg = "Source Name Field can not be blank";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(sourceNameBinding.getClientId(), message);
            return result;
        }
        if (advertisementfrqyBinding.getValue() == null || advertisementfrqyBinding.getValue().toString().trim().length()==0) {
            result="N";
            System.out.println("advertisementfrqyBinding.getValue()::::::"+advertisementfrqyBinding.getValue());
            String msg = "Advertisement Frequency can not be blank";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(advertisementfrqyBinding.getClientId(), message);           
            return result;
        }

        if (advrtisemnetCostBinding.getValue() == null || advrtisemnetCostBinding.getValue().toString().trim().length()==0) {
            result="N";
            System.out.println("advrtisemnetCostBinding.getValue()::::::::;"+advrtisemnetCostBinding.getValue());
            String msg = "Advertisement Cost can not be blank";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(advrtisemnetCostBinding.getClientId(), message);
            return result;
        }
        return result;  
    }
 

    public void searchAdvtAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("searchAdvt").execute();
    }

    public void resetAdvtAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("resetAdvt").execute();
    }

    public void addJobAdvtDtlAL(ActionEvent actionEvent) {
        String field = this.validateMainField();
        if(field.equals("Y")){
        System.out.println("in add job advertisment AL:::::::");
        String param = resolvEl("#{pageFlowScope.PAGE_MODE}");
        System.out.println("value of param is ::" + param);

        Long count =
            ((DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry()).findIteratorBinding("HcmJobAdvtDtl1Iterator").getEstimatedRowCount();

        if (count.intValue() > 1) {
            if (param.equals("C")) {
                String result=this.validateField();
                if(result.equalsIgnoreCase("N"))
                return;
            }
        }
        

        OperationBinding ob = ADFBeanUtils.getOperationBinding("chkDuplicateAdvtDtl");
        ob.execute();
        String res = (String) ob.getResult();
        System.out.println("result::" + res);
        if (res != null && res.equals("Y")) {
            System.out.println("in if");
        } else {
            System.out.println("in the lese");
            String msg = "Duplicate Record Exist";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, message);
            return;
        }
       // okAdvtDtlButtonBind.setDisabled(true);  //addAdvtDetailButton
        ADFBeanUtils.getOperationBinding("CreateInsert1").execute();
        changePageMode_Create();
        //  okDetailButtonBind.setDisabled(false);
        //  saveBind.setDisabled(true);

        this.setMode("C");
        }
    }

    public void deleteAdvtDtLAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete").execute();
      //  okAdvtDtlButtonBind.setDisabled(false);
    }
    
    public void addJobAdvtAL(ActionEvent actionEvent) {
        // addSrcButtonBind.setDisabled(true);
        okSrcButtonBind.setDisabled(true);
        changePageMode_Create();
        ADFBeanUtils.getOperationBinding("CreateInsert").execute();

    }

    public void editJobAdvtAL(ActionEvent actionEvent) {
       changePageMode_Create();
       okSrcButtonBind.setDisabled(false);
        // addSrcButtonBind.setDisabled(false);
    //   okAdvtDtlButtonBind.setDisabled(false);
       //  okDetailButtonBind.setDisabled(false);
       // deleteSrc.setDisabled(false);
       //  saveBind.setDisabled(true);
        
    }

    public void saveJobAdvtAL(ActionEvent actionEvent) {
        if (hireTypeBinding.getValue() == null) {
            String msg = "Hire Type  can not be blank";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(hireTypeBinding.getClientId(), message);
            return;

        }

        if (advrtisemnetCostBinding.getValue() == null) {
            String msg = "advertisement Cost can not be blank";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(advrtisemnetCostBinding.getClientId(), message);
            return;

        }

        if (requirementValidtyDate.getValue() == null) {
            String msg = "requirement Validty Date can not be blank";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(requirementValidtyDate.getClientId(), message);
            return;

        }

        if (validFromDateBinding.getValue() == null) {
            String msg = "Valid From Date can not be blank";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(validFromDateBinding.getClientId(), message);
            return;

        }

        if (validEndDtBinding.getValue() == null) {
            String msg = "Valid End Date can not be blank";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(validEndDtBinding.getClientId(), message);
            return;
        }
        
        Long count1 =((DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry()).findIteratorBinding("HcmJobAdvtDtl1Iterator").getEstimatedRowCount();

        System.out.println("count1.intValue():::::"+count1.intValue());
        if (count1.intValue() > 0) 
        {
            String result=this.validateField();
            if(result.equalsIgnoreCase("N")) 
            {
                return ;
            }
        } 
        
        Long count =((DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry()).findIteratorBinding("HcmJobAdvtSrc1Iterator").getEstimatedRowCount();

        System.out.println("count.intValue():::::"+count.intValue());
        if (count.intValue() > 0) 
        {
            String result1=this.ValidateSourceField();
            if(result1.equalsIgnoreCase("N"))
            {
            return ;
             }
        }
        
        changePageMode_View();
        ADFBeanUtils.getOperationBinding("Commit").execute();
 //      saveBind.setDisabled(true);
        
    }

    public void cancelBtnAL(ActionEvent actionEvent) {
        System.out.println("in cancel action listner");
        // addSrcButtonBind.setDisabled(true);
        // deleteSrc.setDisabled(true);
        okSrcButtonBind.setDisabled(true);
   //     okAdvtDtlButtonBind.setDisabled(true);
        //  okDetailButtonBind.setDisabled(true);
        changePageMode_View();
        ADFBeanUtils.getOperationBinding("Rollback").execute();
    }

    public void addSalaryRangeAL(ActionEvent actionEvent) {
        System.out.println("popup called");
        showPopup(salRangePopBinding, true);

        /*   boolean chk = validateFldsOnSalPop();
        if (chk) {
          //  ADFBeanUtils.getOperationBinding("CreateInsert2").execute();
              showPopup(salRangePopBinding, true);
        }*/
    }

    public void deleteSalaryRangeOnPopAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete1").execute();
    }

    public void addSalaryRangeOnPopAL(ActionEvent actionEvent) {
        System.out.println("in add addSalaryRangeOnPop AL:::::: ");
        ADFBeanUtils.getOperationBinding("CreateInsert4").execute();
    }

    public void okBtnOnSalRangePopAL(ActionEvent actionEvent) {
        if (amtFromBinding.getValue() == null) {
            String msg = "Salary Component Start range can not be blank";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(amtFromBinding.getClientId(), message);
            return;
        }

        if (amtToBinding.getValue() == null) {
            String msg = "Salary Component End range can not be blank";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(amtToBinding.getClientId(), message);
            return;
        }
        salRangePopBinding.hide();
        /*  boolean chk = validateFldsOnSalPop();
        if (chk) {
            salRangePopBinding.hide();
        } */

    }

    public void setSalRangePopBinding(RichPopup salRangePopBinding) {
        this.salRangePopBinding = salRangePopBinding;
    }

    public RichPopup getSalRangePopBinding() {
        return salRangePopBinding;
    }

    public void setSalCompBinding(RichSelectOneChoice salCompBinding) {
        this.salCompBinding = salCompBinding;
    }

    public RichSelectOneChoice getSalCompBinding() {
        return salCompBinding;
    }

    public void setAmtFromBinding(RichInputText amtFromBinding) {
        this.amtFromBinding = amtFromBinding;
    }

    public RichInputText getAmtFromBinding() {
        return amtFromBinding;
    }

    public void setAmtToBinding(RichInputText amtToBinding) {
        this.amtToBinding = amtToBinding;
    }

    public RichInputText getAmtToBinding() {
        return amtToBinding;
    }

    public void addSrcAL(ActionEvent actionEvent) {
        System.out.println("in add source advertisment AL :::::::");   
        // deleteSrc.setDisabled(false);
        okSrcButtonBind.setDisabled(false);
        // addSrcButtonBind.setDisabled(true);
        //  saveBind.setDisabled(true);
         this. validateMainField();
            String param = resolvEl("#{pageFlowScope.PAGE_MODE}");
            System.out.println("value of param is ::" + param);
                Long count =
                    ((DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry()).findIteratorBinding("HcmJobAdvtSrc1Iterator").getEstimatedRowCount();

                if (count.intValue() > 0) {
                    if (param.equals("C")) {
                        String result=this.ValidateSourceField();
                        if(result.equalsIgnoreCase("N"))
                        return;
                    }
                }
                OperationBinding ob = ADFBeanUtils.getOperationBinding("chkDuplicateAdvtSourceDetl");
                ob.execute();
                String res = (String) ob.getResult();
                System.out.println("result::" + res);
                if (res != null && res.equals("Y")) {
                    System.out.println("in if");
                  //  //  saveBind.setDisabled(false);
                } else {
                    System.out.println("in the lese");
                    String msg = "Duplicate Record Exist";
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, message);
                    return;
                } 
        ADFBeanUtils.getOperationBinding("CreateInsert3").execute();
        changePageMode_Create();
    }

    public void deleteSrcAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete2").execute();
    }

    public void advtDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("object is ::::: " + object);
        if (object != null) {
            OperationBinding binding = ADFBeanUtils.getOperationBinding("chkDuplicateAdvtDt");
            binding.getParamsMap().put("advtDt", object);
            binding.execute();
            if (binding.getErrors().isEmpty() && binding.getResult() != null) {
                String rslt = binding.getResult().toString();
                System.out.println("result is::::::" + rslt);
                if (rslt.equalsIgnoreCase("Y")) {
                    String msg = "Duplicate advertisement date is not allowed !";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }
    }

    /*    public void validateAdvEndDate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("***** validateAdvEndDate validator*********");
        System.out.println("object is:::::"+object);
            if (object != null && object.toString().length() > 0) {
                java.sql.Date strtDt = null;
                java.sql.Date endDt = null;

                System.out.println("validStrtDtBinding.getValue():::::" +validStrtDtBinding.getValue());


    if (validStrtDtBinding.getValue() != null && validStrtDtBinding.getValue().toString().length() > 0) {
        try {
            strtDt = ((oracle.jbo.domain.Timestamp) validStrtDtBinding.getValue()).dateValue();
            System.out.println("start date is ::::: "+strtDt);
            endDt = ((oracle.jbo.domain.Timestamp) object).dateValue();
            System.out.println("end date is ::::: "+endDt);

        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
        }
        if (strtDt.compareTo(endDt) > 0) {
            if (strtDt.toString().equals(endDt.toString())) {
            }
            else {
                showFacesMessage("To Date can not be before Valid From Date.", "E", false, "V");
            }
        }
    }
    }



} */
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

    public void setValidStrtDtBinding(RichInputDate validStrtDtBinding) {
        this.validStrtDtBinding = validStrtDtBinding;
    }

    public RichInputDate getValidStrtDtBinding() {
        return validStrtDtBinding;
    }

    /*  public void validateAdvStartDate(FacesContext facesContext, UIComponent uIComponent, Object object) {

        System.out.println("***** validateAdvstart Date validator::::::");
            if (object != null && object.toString().length() > 0) {
                java.sql.Date strtDt = null;
                java.sql.Date endDt = null;

                if (validEndDtBinding.getValue() != null && validEndDtBinding.getValue().toString().length() > 0) {
                    try {
                        endDt = ((oracle.jbo.domain.Timestamp) validEndDtBinding.getValue()).dateValue();
                        System.out.println("start date is ::::: "+endDt);
                        strtDt = ((oracle.jbo.domain.Timestamp) object).dateValue();
                        System.out.println("end date is ::::: "+strtDt);

                    } catch (SQLException e) {
                        System.out.println(e.getStackTrace());
                    }
                    if (endDt.compareTo(strtDt) > 0) {
                        if (endDt.toString().equals(strtDt.toString())) {
                        }
                        else {
                            showFacesMessage("From Date can not be after Valid upTo Date.", "E", false, "V");
                        }
                    }
                }
            }

    } */

    public void setValidEndDtBinding(RichInputDate validEndDtBinding) {
        this.validEndDtBinding = validEndDtBinding;
    }

    public RichInputDate getValidEndDtBinding() {
        return validEndDtBinding;
    }

    /**
     * Function to check precision of Number fields
     */
    public Boolean isPrecisionValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }


    public void advertismentCostValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        oracle.jbo.domain.Number amt = StaticValue.NUMBER_ZERO;

        if (object != null) {
            oracle.jbo.domain.Number amount = (oracle.jbo.domain.Number) object;

            if (!isPrecisionValid(26, 6, amount)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            if (amount.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value must be greater then Zero.", null));
            }
            if (amount.compareTo(amt) == -1) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Amount can't be negative",
                                                              null));
            }
        }


    }

    public void designationCostValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        oracle.jbo.domain.Number amt = StaticValue.NUMBER_ZERO;

        if (object != null) {
            oracle.jbo.domain.Number amount = (oracle.jbo.domain.Number) object;

            if (!isPrecisionValid(26, 6, amount)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            if (amount.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value must be greater then Zero.", null));
            }
            if (amount.compareTo(amt) == -1) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Amount can't be negative",
                                                              null));
            }
        }

    }

    public void noOfVacancyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        oracle.jbo.domain.Number amt = StaticValue.NUMBER_ZERO;

        if (object != null) {
            oracle.jbo.domain.Number amount = (oracle.jbo.domain.Number) object;

            if (amount.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value must be greater then Zero.", null));
            }
            if (amount.compareTo(amt) == -1) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "No of Vacancies can't be negative", null));
            }
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

    public void okAdevertisementDetailAL(ActionEvent actionEvent) {
       System.out.println(":::::::::::: okAdevertisementDetailAL ::::::::");
       
       String result=this.validateField();
        //  saveBind.setDisabled(true);
             
    if(result.equalsIgnoreCase("Y"))
    {
        OperationBinding ob = ADFBeanUtils.getOperationBinding("chkDuplicateAdvtDtl");
        ob.execute();
        String res = (String) ob.getResult();
        System.out.println("result::" + res);
        if (res != null && res.equals("Y")) {
            System.out.println("in if");
            //  saveBind.setDisabled(false);
            
            this.setMode("V");
        } else {
            System.out.println("in the lese");
            String msg = "Duplicate Record Exist";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, message);
        }
     }
        
    }
    public void editAdvertismentDetailAL(ActionEvent actionEvent) {
        System.out.println("********* editAdvertismentDetailAL ******");
    }

//    public void set//  saveBind(RichLink //  saveBind) {
//        this.//  saveBind = //  saveBind;
//    }
//
//    public RichLink get//  saveBind() {
//        return //  saveBind;
//    }

    public void editSrcDetailAL(ActionEvent actionEvent) {
        System.out.println("********* edit Source AdvertismentDetailAL ******");
    }

    public void okSourceAdvertisementDetailAL(ActionEvent actionEvent) {

        System.out.println(":::::::::::: okSourceAdvertisementDetailAL ::::::::"); 
        String result=this.ValidateSourceField();
        //  saveBind.setDisabled(false);
        /*  if(result.equalsIgnoreCase("Y"))
        {
            OperationBinding ob = ADFBeanUtils.getOperationBinding("chkDuplicateAdvtSourceDetl");
            ob.execute();
            String res = (String) ob.getResult();
            System.out.println("result::" + res);
            if (res != null && res.equals("Y")) {
                System.out.println("in if");
            } else {
                System.out.println("in the else");
                String msg = "Duplicate Record Exist";
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, message);
            }

        } */
    //    changePageMode_Create();
    }

    public void setJobTypeBinding(RichSelectOneChoice jobTypeBinding) {
        this.jobTypeBinding = jobTypeBinding;
    }

    public RichSelectOneChoice getJobTypeBinding() {
        return jobTypeBinding;
    }

    /*  public void setLocationBinding(RichSelectOneChoice locationBinding) {
        this.locationBinding = locationBinding;
    }

    public RichSelectOneChoice getLocationBinding() {
        return locationBinding;
    } */

    public void setDepartmemtbinding(RichSelectOneChoice departmemtbinding) {
        this.departmemtbinding = departmemtbinding;
    }

    public RichSelectOneChoice getDepartmemtbinding() {
        return departmemtbinding;
    }

    /*  public void setDesignationBinding(RichSelectOneChoice designationBinding) {
        this.designationBinding = designationBinding;
    }

    public RichSelectOneChoice getDesignationBinding() {
        return designationBinding;
    } */

    public void setNoOfVacancyBinding(RichInputText noOfVacancyBinding) {
        this.noOfVacancyBinding = noOfVacancyBinding;
    }

    public RichInputText getNoOfVacancyBinding() {
        return noOfVacancyBinding;
    }


    public void maxAmountRangeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("***** validate Max Amount Range *********");

        if (object != null) {
            if (amtFromBinding.getValue() != null) {
                oracle.jbo.domain.Number max = (oracle.jbo.domain.Number) object;
                oracle.jbo.domain.Number min = (oracle.jbo.domain.Number) amtFromBinding.getValue();
                if (max.compareTo(0) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                                                  "Max Amount Range can not be in negative"));
                }
                if (max.compareTo(min) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                                                                  "Max Amount Range should be greater than Min Amount Range"));
                }
            }

        }


    }

    public void setHireTypeBinding(RichSelectOneChoice hireTypeBinding) {
        this.hireTypeBinding = hireTypeBinding;
    }

    public RichSelectOneChoice getHireTypeBinding() {
        return hireTypeBinding;
    }

    public void setAdvrtisemnetCostBinding(RichInputText advrtisemnetCostBinding) {
        this.advrtisemnetCostBinding = advrtisemnetCostBinding;
    }

    public RichInputText getAdvrtisemnetCostBinding() {
        return advrtisemnetCostBinding;
    }

    public void setRequirementValidtyDate(RichInputDate requirementValidtyDate) {
        this.requirementValidtyDate = requirementValidtyDate;
    }

    public RichInputDate getRequirementValidtyDate() {
        return requirementValidtyDate;
    }

    public void setValidFromDateBinding(RichInputDate validFromDateBinding) {
        this.validFromDateBinding = validFromDateBinding;
    }

    public RichInputDate getValidFromDateBinding() {
        return validFromDateBinding;
    }

    public void setSourceNameBinding(RichInputListOfValues sourceNameBinding) {
        this.sourceNameBinding = sourceNameBinding;
    }

    public RichInputListOfValues getSourceNameBinding() {
        return sourceNameBinding;
    }

    public void setSoureTypeBinding(RichSelectOneChoice soureTypeBinding) {
        this.soureTypeBinding = soureTypeBinding;
    }

    public RichSelectOneChoice getSoureTypeBinding() {
        return soureTypeBinding;
    }

    public void setAdvertisementfrqyBinding(RichSelectOneChoice advertisementfrqyBinding) {
        this.advertisementfrqyBinding = advertisementfrqyBinding;
    }

    public RichSelectOneChoice getAdvertisementfrqyBinding() {
        return advertisementfrqyBinding;
    }

    public void setDegisnationCostBinding(RichInputText degisnationCostBinding) {
        this.degisnationCostBinding = degisnationCostBinding;
    }

    public RichInputText getDegisnationCostBinding() {
        return degisnationCostBinding;
    }

    /**Page Mode Change In Create Mode
     * */
    public void changePageMode_Create() {
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("PAGE_MODE", "C");
    }

    /**Page Mode Change In View Mode
     * */
    public void changePageMode_View() {
        System.out.println("::::::::::in change mode view:::::::::");
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("PAGE_MODE", "V");
    }

    public void validateJobCost(FacesContext facesContext, UIComponent uIComponent, Object object) {
        oracle.jbo.domain.Number amt = StaticValue.NUMBER_ZERO;

        if (object != null) {
            oracle.jbo.domain.Number amount = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, amount)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            if (amount.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value must be greater then Zero.", null));
            }
            if (amount.compareTo(amt) == -1) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "JobCost can't be negative",
                                                              null));
            }
          OperationBinding binding = ADFBeanUtils.getOperationBinding("chkJobCost");
            binding.getParamsMap().put("jobCost", object);
            binding.execute();
            if (binding.getErrors().isEmpty() && binding.getResult() != null) {
                String rslt = binding.getResult().toString();
                System.out.println("result is**:::" + rslt);
                if (rslt.equalsIgnoreCase("Y")) {
                    String msg = "Job Cost can not be greater than Advertisement Cost !";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
            
        }

    }

    public void validatePageNo(FacesContext facesContext, UIComponent uIComponent, Object object) {
        oracle.jbo.domain.Number amt = StaticValue.NUMBER_ZERO;

        if (object == null) {
            oracle.jbo.domain.Number pageno = (oracle.jbo.domain.Number) object;

            if (pageno.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value must be greater then Zero.", null));
            }
            if (pageno.compareTo(amt) == -1) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "PageNo can't be negative",
                                                              null));
            }
        }

    }

    public void setManPlanNo(RichInputText manPlanNo) {
        this.manPlanNo = manPlanNo;
    }

    public RichInputText getManPlanNo() {
        return manPlanNo;
    }

    public void setManPlanDt(RichInputDate manPlanDt) {
        this.manPlanDt = manPlanDt;
    }

    public RichInputDate getManPlanDt() {
        return manPlanDt;
    }

    public void validateSourceName(FacesContext facesContext, UIComponent uIComponent, Object object) {
            System.out.println("object is ::::: " + object);
            if (object != null) {
                OperationBinding binding = ADFBeanUtils.getOperationBinding("chkDuplicateSourceName");
                binding.getParamsMap().put("srcName", object);
                binding.execute();
                if (binding.getErrors().isEmpty() && binding.getResult() != null) {
                    String rslt = binding.getResult().toString();
                    System.out.println("result is::::::" + rslt);
                    if (rslt.equalsIgnoreCase("Y")) {
                        String msg = "Duplicate Source Name date is not allowed !";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                    }
                }
            }
    }

    /*  public void set// addSrcButtonBind(RichLink // addSrcButtonBind) {
        this.// addSrcButtonBind = // addSrcButtonBind;
    }

    public RichLink get// addSrcButtonBind() {
        return // addSrcButtonBind;
    } */

    public void setOkSrcButtonBind(RichLink okSrcButtonBind) {
        this.okSrcButtonBind = okSrcButtonBind;
    }

    public RichLink getOkSrcButtonBind() {
        return okSrcButtonBind;
    }

    public void tabDisloseListner(DisclosureEvent disclosureEvent) {
        String param = resolvEl("#{pageFlowScope.PAGE_MODE}");
        if(param.equals("V")){
            // addSrcButtonBind.setDisabled(true);
            }if(param.equals("C")){
        // addSrcButtonBind.setDisabled(false);
            }
    }

    /*  public void set// deleteSrc(RichLink // deleteSrc) {
        this.// deleteSrc = // deleteSrc;
    }

    public RichLink get// deleteSrc() {
        return // deleteSrc;
    } */
//
//    public void setOkAdvtDtlButtonBind(RichLink okAdvtDtlButtonBind) {
//        this.okAdvtDtlButtonBind = okAdvtDtlButtonBind;
//    }
//
//    public RichLink getOkAdvtDtlButtonBind() {
//        return okAdvtDtlButtonBind;
//    }

//    public void set//  okDetailButtonBind(RichLink //  okDetailButtonBind) {
//        this.//  okDetailButtonBind = //  okDetailButtonBind;
//    }
//
//    public RichLink get//  okDetailButtonBind() {
//        return //  okDetailButtonBind;
//    }
    public void setDesignationBinding(RichInputListOfValues designationBinding) {
        this.designationBinding = designationBinding;
    }

    public RichInputListOfValues getDesignationBinding() {
        return designationBinding;
    }

    public void setLocationBinding(RichInputListOfValues locationBinding) {
        this.locationBinding = locationBinding;
    }

    public RichInputListOfValues getLocationBinding() {
        return locationBinding;
    }

    public void validateSalaryComponent(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("object is ::::: " + object);
        if (object != null) {
            OperationBinding binding = ADFBeanUtils.getOperationBinding("chkDuplicateSalaryComponent");
            binding.getParamsMap().put("salcomp", object);
            binding.execute();
            if (binding.getErrors().isEmpty() && binding.getResult() != null) {
                String rslt = binding.getResult().toString();
                System.out.println("result is::::::" + rslt);
                if (rslt.equalsIgnoreCase("Y")) {
                    String msg = "Duplicate Salary component is not allowed !";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }
    }

    public void validateAmoutStartRange(FacesContext facesContext, UIComponent uIComponent, Object object) {
        oracle.jbo.domain.Number amt = StaticValue.NUMBER_ZERO;
        if (object != null) {
            oracle.jbo.domain.Number startRange = (oracle.jbo.domain.Number) object;

            if (!isPrecisionValid(26, 6, startRange)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            if (startRange.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value must be greater then Zero.", null));
            }
            if (startRange.compareTo(amt) == -1) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Amount can't be negative",
                                                              null));
            }
        }

    }
}
