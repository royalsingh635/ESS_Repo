
package mmmatclsftn.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.bean.StaticValue;

import adf.utils.model.ADFModelUtils;

import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Calendar;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import mmmatclsftn.model.services.MmMatClsftnAMImpl;

import oracle.adf.model.BindingContainer;
import oracle.adf.model.BindingContext;
import oracle.adf.model.OperationBinding;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adfdt.model.binding.AdfModelConfigurationException;

import oracle.jbo.ViewObject;

import oracle.jbo.domain.Date;

import oracle.sql.DATE;
import oracle.sql.TIMESTAMP;

import org.apache.myfaces.trinidad.context.RequestContext;

public class MmMatClsftnTypeBean {
//    String mode="V";
    private RichInputListOfValues transGrpNameBind;
    private RichInputListOfValues transItemName;
    private RichSelectOneChoice transClsName;

    public MmMatClsftnTypeBean() {
    }
    String newVal="O";
    public void matSelTypeMethod(ValueChangeEvent valueChangeEvent) {
//        String s=valueChangeEvent.toString();
        
        if(valueChangeEvent.getNewValue().toString().equals("M"))
        {
            newVal="M";
//            oracle.binding.OperationBinding ob = ADFBeanUtils.findOperation("insertItems");
//            ob.execute();     
        }
        else if(valueChangeEvent.getNewValue().toString().equals("O")) {
            newVal="O";
//            System.out.println("its organisation");
//            oracle.binding.OperationBinding ob = ADFBeanUtils.findOperation("invoiceAna");
//            ob.execute();
//            if(ob.getResult()!=null) {
//                System.out.println("Result returned is" +ob.getResult());
//            }   
            
        }
        else {
            newVal="G";
//            System.out.println("its group");
//            oracle.binding.OperationBinding ob = ADFBeanUtils.findOperation("groupAnalysis");
//            ob.execute();
//            if(ob.getResult()!=null) {
//                System.out.println("Result returned is" +ob.getResult());
//            }    
        }
        
       
    }

    public void invAnalysisMethod(ActionEvent actionEvent) {
        oracle.binding.OperationBinding ob = ADFBeanUtils.findOperation("invAnalysis");
        System.out.println("OUTCOME@@@@@@@@@@@");
        ob.execute();
        System.out.println("final outcome is" +ob.getResult().toString());
        if(ob.getResult()!=null){
              System.out.println("invanalyis executed@@@@@@@@@@@@");
          }
    }

    public void createMethod(ActionEvent actionEvent) {
         // Add event code here...
         
        
    }

    public void editMethod(ActionEvent actionEvent) {
//        oracle.binding.OperationBinding ob = ADFBeanUtils.findOperation("edition");
//        ob.execute();   
        RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "E");

//        mode="E";
        // Add event code here...
    }

    public void saveMethod(ActionEvent actionEvent) {
        oracle.binding.OperationBinding ob1 = ADFBeanUtils.findOperation("analysisIdGen");
        System.out.println("analysis generation method in bean");
        ob1.execute();
        String result=ob1.getResult().toString();
        if(result!=null){
            oracle.binding.OperationBinding opr = ADFBeanUtils.findOperation("anaHistory");
            opr.execute();
            System.out.println("result from histort function is"+opr.getResult());
            if(opr.getResult()!=null){
                 System.out.println("history fun called succesfully");
                System.out.println("result------------------------");
                System.out.println("result got is" +ob1.getResult());
                oracle.binding.OperationBinding ob3 = ADFBeanUtils.findOperation("Commit");
//                String s=ADFModelUtils.resolvRsrc("MSG.");
//                    String s="Records Saved Sucesfully!!";
                    String s=ADFModelUtils.resolvRsrc("MSG.2889");
                    System.out.println("committed!!!!");
                                FacesMessage msg=new FacesMessage(s);
                                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                                FacesContext fctx=FacesContext.getCurrentInstance();
                                fctx.addMessage(null, msg);
                                ob3.execute();
//                     RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
                    
                
                
                
                 }
            }
        else {
            System.out.println("result not retrieved as analysis id");
        }
    }

    public void setMode(String mode) {
//        this.mode = mode;
    }

    public String getMode() {
//        return mode;
        return null;
    }

    public void cancelMethod(ActionEvent actionEvent) {
//        System.out.println("cancel method in bean");
//        oracle.binding.OperationBinding ob = ADFBeanUtils.findOperation("Rollback");
//        ob.execute();   
        // Add event code here...
    }

    public void createNew() {
//        System.out.println("create new method");
//        oracle.binding.OperationBinding ob = ADFBeanUtils.findOperation("setTransClsId");
//        ob.execute(); 
//    RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "E");
//        return "ret";
    }

    public void toDateValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
//        // Add event code here...
//        System.out.println("*****validateGradeEndDate*********");
//            if (object != null && object.toString().length() > 0) {
//                java.sql.Date strtDt = null;
//                java.sql.Date endDt = null;
//                Date validStrtDtBinding=null;
//        if (validStrtDtBinding.getValue() != null && validStrtDtBinding.getValue().toString().length() > 0) {
//                  try {
//                      strtDt = ((TIMESTAMP) validStrtDtBinding.getValue()).dateValue();
//                      System.out.println("start date::::" + strtDt);
//                      endDt = ((Timestamp) object).dateValue();
//                      System.out.println("end date ::::::::" + endDt);
//                  } catch (SQLException e) {
//                      System.out.println(e.getStackTrace());
//                  }
//                  if (strtDt.compareTo(endDt) > 0) {
//                      if (strtDt.toString().equals(endDt.toString())) {
//                      } else {
//                          showFacesMessage("To Date can not be before Valid From Date.", "E", false, "V");
//                      }
//                  }
//
//    }
//}
    }

    public void setNewVal(String newVal) {
        this.newVal = newVal;
    }

    public String getNewVal() {
        return newVal;
    }

//   

    public void classTypeChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
//        if(valueChangeEvent.getNewValue().toString().equals("SDE")||valueChangeEvent.getNewValue().toString().equals("VED"))){
//            String s="SDE and VED classification type are not valid for group Selection Criteria!!";
//            FacesMessage msg=new FacesMessage(s);
//            msg.setSeverity(FacesMessage.SEVERITY_FATAL);
//            FacesContext fctx=FacesContext.getCurrentInstance();
//            fctx.addMessage(null, msg);
//            }
           
            
        }

    public void groupValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
//        if(object!=null){
//            String status=object.toString();
//            MmMatClsftnAMImpl am=this.getMmMatClsftnAMImpl`
//        }
        // Add event code here...

    }
//    Timestamp from_date="";
    public void fromDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
//        if(object!=null){
//            
//            
//        }
        // Add event code here...

    }
    Timestamp currentDate= new Timestamp(System.currentTimeMillis());
    public void newDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
//        if(object!=null){
//            System.out.println("currentDate is"+currentDate);
//            Timestamp newDate=(Timestamp)object;
//                System.out.println("new date for validation is"+newDate);
//            if(currentDate.toString()!=null)
//            {
//                        if (newDate.toString().compareTo(currentDate.toString()) == 1) {
//                            
//                        String s="To Date can not be smaller then from Date!!";
//                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,s,null));
//                        }
//                    }
//                
//            }
        }
   
    

    public void classificationValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if(object==null) {
            String s=ADFModelUtils.resolvRsrc("MSG.2890");
//            String s="Please Select Classification Type";
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,s,null));
            
        }
    }

    public oracle.jbo.domain.Date currentDate(ActionEvent actionEvent) {
//      oracle.jbo.domain.Date cdate=new oracle.jbo.domain.Date(new java.sql.Date(new java.util.Date().getTime()));  
//    return cdate; 
        return null;
    }

    public oracle.jbo.domain.Timestamp getCurrDate() {
//        RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
//        cal.set(Calendar.MINUTE, 59);
//        cal.set(Calendar.SECOND, 59); 
//        cal.set(Calendar.MILLISECOND, 999);
//        return new oracle.jbo.domain.Timestamp(cal.getTime());
        return StaticValue.getCurrDtWidTimestamp();
//        return null;
    }

    public oracle.jbo.domain.Timestamp getFromDate() {
//       
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -6);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59); 
        cal.set(Calendar.MILLISECOND, 999);
        return new oracle.jbo.domain.Timestamp(cal.getTime());
//        return null;
    }

    public void deleteRowMethod(ActionEvent actionEvent) {
        // Add event code here...
        oracle.binding.OperationBinding ob1 = ADFBeanUtils.findOperation("deleteRow");
        ob1.execute();
    }

    public void setTransGrpNameBind(RichInputListOfValues transGrpNameBind) {
        this.transGrpNameBind = transGrpNameBind;
    }

    public RichInputListOfValues getTransGrpNameBind() {
        return transGrpNameBind;
    }

    public void setTransItemName(RichInputListOfValues transItemName) {
        this.transItemName = transItemName;
    }

    public RichInputListOfValues getTransItemName() {
        return transItemName;
    }

    public void setTransClsName(RichSelectOneChoice transClsName) {
        this.transClsName = transClsName;
    }

    public RichSelectOneChoice getTransClsName() {
        return transClsName;
    }
}


