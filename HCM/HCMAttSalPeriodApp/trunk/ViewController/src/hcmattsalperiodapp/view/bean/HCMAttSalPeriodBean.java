package hcmattsalperiodapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import java.sql.SQLException;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

public class HCMAttSalPeriodBean {
    private String mode = "V";
    private RichInputDate attenFrmDTBinding;
    private RichInputDate slryPrcsPrdFrmDTBinding;
    private RichInputDate attenToDTBinding;
    private RichInputDate slryPrcsPrdToDTBinding;
    private RichInputListOfValues grpNmBinding;
    private RichInputText grpIdTransBinding;

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public HCMAttSalPeriodBean() {
    }

    /**
     * Method to add period
     * @param actionEvent
     */
    public void AddPeriodAL(ActionEvent actionEvent) {
        /*
        * 1 - Attendance Perdiod From is not defined
        * 2 - Attendance Period To is not defined
        * 3 - Salary Period From is not defined
        * 4 - Salary Period To is not defined
        * 5 - Attendance From Period is less than Attendance To Period
        * 6 - Salary Period From Period is less than Salary Period To 
        */
        OperationBinding binding = ADFBeanUtils.getOperationBinding("addSalPeriod");
        binding.execute();
        Object o = binding.getResult();
        Integer i = (o == null ? -1 : (Integer) o);
        System.out.println("Id : "+ADFBeanUtils.getComponentClientIdFromId(actionEvent.getComponent(), "id2"));
        
         /*   if (i.equals(1)) {
                    ADFModelUtils.showFacesMessage("Employee Group Name Not Defined.",
                                                   "Please Select Employee Group name.", FacesMessage.SEVERITY_ERROR,
                                                   ADFBeanUtils.getComponentClientIdFromId(actionEvent.getComponent(), "transgrpNmId"));
        */    if (i.equals(1)) {
            ADFModelUtils.showFacesMessage("Salary Processing from date is not defined.",
                                           "Please select Salary Processing from date.", FacesMessage.SEVERITY_ERROR,
                                           ADFBeanUtils.getComponentClientIdFromId(actionEvent.getComponent(), "id4"));
        } else if (i.equals(2)) {
            ADFModelUtils.showFacesMessage("Salary Processing To date is not defined.",
                                           "Please select Salary Processing To date.", FacesMessage.SEVERITY_ERROR,
                                           ADFBeanUtils.getComponentClientIdFromId(actionEvent.getComponent(), "id3"));
        
        }else if (i.equals(3)) {
            ADFModelUtils.showFacesMessage("Attendance from date is not defined.",
                                           "Please select Attendance from date.", FacesMessage.SEVERITY_ERROR,
                                           ADFBeanUtils.getComponentClientIdFromId(actionEvent.getComponent(), "id2"));   
        }else  if (i.equals(4)) {
            ADFModelUtils.showFacesMessage("Attendance to date is not defined.",
                                           "Please select Attendance to date.", FacesMessage.SEVERITY_ERROR,
                                           ADFBeanUtils.getComponentClientIdFromId(actionEvent.getComponent(), "id1"));
        } else  if (i.equals(6)) {
            ADFModelUtils.showFacesMessage("Attendance from date is not valid.",
                                           "Attendance from date cannot be less than Attendance to date.", FacesMessage.SEVERITY_ERROR,
                                           ADFBeanUtils.getComponentClientIdFromId(actionEvent.getComponent(), "id2"));
        }else if (i.equals(5)) {
            ADFModelUtils.showFacesMessage("Salary Processing from date is not valid.",
                                           "Salary Processing from date cannot be less than Salary Processing to date.", FacesMessage.SEVERITY_ERROR,
                                           ADFBeanUtils.getComponentClientIdFromId(actionEvent.getComponent(), "id4"));
        } 
          

        attenFrmDTBinding.setValue("");
        attenToDTBinding.setValue("");
        slryPrcsPrdFrmDTBinding.setValue("");
        slryPrcsPrdToDTBinding.setValue("");
        grpNmBinding.setValue("");
        
    }

    /**
     * Method to edit period
     * @param actionEvent
     */
    public void editAL(ActionEvent actionEvent) {
        mode = "E";
    }

    /**
     * Method to save all changes
     * @param actionEvent
     */
    public void saveAL(ActionEvent actionEvent) {
        //Integer i=null;
        OperationBinding binding = ADFBeanUtils.getOperationBinding("saveRecord");
        binding.execute();
        OperationBinding binding1 = ADFBeanUtils.getOperationBinding("addSalPeriod");
        binding1.execute();
        Object o = binding.getResult();
        Integer i = (o == null ? -1 : (Integer) o);
        if (o.equals(0)) {
            mode = "V";
        }
        attenFrmDTBinding.setValue("");
        attenToDTBinding.setValue("");
        slryPrcsPrdFrmDTBinding.setValue("");
        slryPrcsPrdToDTBinding.setValue("");
        grpNmBinding.setValue("");
    }

    /**
     * Method to revert all the changes
     * @param actionEvent
     */
    public void cancelAL(ActionEvent actionEvent) {
        // Add event code here...
        mode = "V";

        attenFrmDTBinding.setValue("");
        attenToDTBinding.setValue("");
        slryPrcsPrdFrmDTBinding.setValue("");
        slryPrcsPrdToDTBinding.setValue("");
        grpNmBinding.setValue("");
        
    }

    public void deletePeriodAL(ActionEvent actionEvent) {
         OperationBinding binding = ADFBeanUtils.getOperationBinding("Delete");
         binding.execute();
         OperationBinding commitBinding = ADFBeanUtils.getOperationBinding("Commit");
         commitBinding.execute();

    }


      // Attendance From date Validator.
     public void attenDTValidator(FacesContext facesContext, UIComponent uIComponent, Object object) 
     {
        if(object!=null && object.toString().length()>0)
        {
           OperationBinding binding = ADFBeanUtils.getOperationBinding("chkDuplictAttenFrmDt");
           binding.getParamsMap().put("attenFrmDt", object);
            binding.getParamsMap().put("slyPrcsFrmDt1", slryPrcsPrdFrmDTBinding.getValue());
           
           
          // binding.getParamsMap().put("empgrpId",grpIdTransBinding.getValue());
           
           binding.execute();
           
            if (binding.getErrors().isEmpty() && binding.getResult() != null)
                        {
                          System.out.println("in bean result"+ binding.getResult());
                            String rslt = binding.getResult().toString();
                            if (rslt.equalsIgnoreCase("Z"))
                            {
                                String msg = "Attendance date Should be Equal or Less then from Salary Processing From Date";
                                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                            }
                            else if (rslt.equalsIgnoreCase("Y"))
                            {
                                String msg = "Duplicate Date not allowed!";
                                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                            }
                        }
    
        } 

     }

    public void salaryPrcPrdFrmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object!=null && object.toString().length()>0)
        {
           OperationBinding binding = ADFBeanUtils.getOperationBinding("chkDuplictSlyPrcsFrmDt");
           binding.getParamsMap().put("slyPrcsFrmDt", object);
           // binding.getParamsMap().put("empgrpId",grpIdTransBinding.getValue());
           binding.execute();
            if (binding.getErrors().isEmpty() && binding.getResult() != null)
                        {
                          System.out.println("in bean result"+ binding.getResult());
                            String rslt = binding.getResult().toString();
                            if (rslt.equalsIgnoreCase("Y"))
                            {
                                String msg = "Duplicate Date not allowed!";
                                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                                
                                
                            }
                        }
        
        } 


    }

    public void setAttenFrmDTBinding(RichInputDate attenFrmDTBinding) {
        this.attenFrmDTBinding = attenFrmDTBinding;
    }

    public RichInputDate getAttenFrmDTBinding() {
        return attenFrmDTBinding;
    }

    public void setSlryPrcsPrdFrmDTBinding(RichInputDate slryPrcsPrdFrmDTBinding) {
        this.slryPrcsPrdFrmDTBinding = slryPrcsPrdFrmDTBinding;
    }

    public RichInputDate getSlryPrcsPrdFrmDTBinding() {
        return slryPrcsPrdFrmDTBinding;
    }

    public void attenPrdFrmDtVCL(ValueChangeEvent vce) 
    {
        if ( vce.getNewValue()!=null)
        {
             //Date dateforManual = null;
              oracle.jbo.domain.Timestamp dt = (oracle.jbo.domain.Timestamp) vce.getNewValue();
              System.out.println("from date is-----" + dt);
              oracle.jbo.domain.Date dt2 = null;
                try {   
                             dt2 = new oracle.jbo.domain.Date(dt.dateValue());
                    } 
                catch (SQLException e) 
                       {
                           }
                   //  System.out.println("adding one month---" + dt2.addMonths(1));
                        Date dateBefore = new Date((dt2.addMonths(1).dateValue()).getTime() - 1 * 24 * 3600 * 1000);
                        System.out.println("aft minus one day date is-- " + dateBefore);
                         attenToDTBinding.setValue(new oracle.jbo.domain.Timestamp(dateBefore));
                        AdfFacesContext.getCurrentInstance().addPartialTarget(attenToDTBinding);
            }
        
           UIComponent c = vce.getComponent();  
           //This step actually invokes Update Model phase for this component
           c.processUpdates(FacesContext.getCurrentInstance());
           
           
    }

    public void setAttenToDTBinding(RichInputDate attenToDTBinding) {
        this.attenToDTBinding = attenToDTBinding;
    }

    public RichInputDate getAttenToDTBinding() {
        return attenToDTBinding;
    }

    public void setSlryPrcsPrdToDTBinding(RichInputDate slryPrcsPrdToDTBinding) {
        this.slryPrcsPrdToDTBinding = slryPrcsPrdToDTBinding;
    }

    public RichInputDate getSlryPrcsPrdToDTBinding() {
        return slryPrcsPrdToDTBinding;
    }

    public void salaryPrcsPrdDtVCL(ValueChangeEvent vce) {
               if ( vce.getNewValue()!=null)
               {
                    //Date dateforManual = null;
                     oracle.jbo.domain.Timestamp dt = (oracle.jbo.domain.Timestamp) vce.getNewValue();
                     System.out.println("from date is-----" + dt);
                     oracle.jbo.domain.Date dt2 = null;
                       try {   
                                    dt2 = new oracle.jbo.domain.Date(dt.dateValue());
                           } 
                       catch (SQLException e) 
                              {
                                  }
                          //  System.out.println("adding one month---" + dt2.addMonths(1));
                               Date dateBefore = new Date((dt2.addMonths(1).dateValue()).getTime() - 1 * 24 * 3600 * 1000);
                               System.out.println("aft minus one day date is-- " + dateBefore);
                                slryPrcsPrdToDTBinding.setValue(new oracle.jbo.domain.Timestamp(dateBefore));
                               AdfFacesContext.getCurrentInstance().addPartialTarget(slryPrcsPrdToDTBinding);
                   }
               
               
     
       
           }

    public void setGrpNmBinding(RichInputListOfValues grpNmBinding) {
        this.grpNmBinding = grpNmBinding;
    }

    public RichInputListOfValues getGrpNmBinding() {
        return grpNmBinding;
    }

    public void setGrpIdTransBinding(RichInputText grpIdTransBinding) {
        this.grpIdTransBinding = grpIdTransBinding;
    }

    public RichInputText getGrpIdTransBinding() {
        return grpIdTransBinding;
    }
}
