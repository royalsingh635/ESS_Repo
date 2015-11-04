package mmmatclsftn.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.myfaces.trinidad.context.RequestContext;


public class ClsftnSetUpBean {
    public ClsftnSetUpBean() {
    }

    public void createMethod(ActionEvent actionEvent) {
        // Add event code here...
        RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "E");
        oracle.binding.OperationBinding ob = ADFBeanUtils.findOperation("valueValidation");
        ob.execute();
        if(ob.getResult()==1){
            
        
        oracle.binding.OperationBinding ob1 = ADFBeanUtils.findOperation("CreateInsert");
        ob1.execute();
        }
        else {
            String s=ADFModelUtils.resolvRsrc("MSG.2886");
//            String s="Total of three values should be 100!!";
                        FacesMessage msg=new FacesMessage(s);
                        msg.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext fctx=FacesContext.getCurrentInstance();
                        fctx.addMessage(null, msg);
        }
    }
    
    public void saveMethod() {
        // Add event code here...
        oracle.binding.OperationBinding ob = ADFBeanUtils.findOperation("amountValidationMethod");
        ob.execute();
        System.out.println("amount validation method bean");
        if(ob.getResult()!=null) {
            oracle.binding.OperationBinding ob2 = ADFBeanUtils.findOperation("validationOfAmount");
            ob2.execute();
            System.out.println("validation of amount in bean");
            if(ob2.getResult()!=null){
                Integer total=Integer.parseInt(ob2.getResult().toString());
                if(total==100) {
                    System.out.println("^^^^^^^^^^^^^^^total percentage returned is" +total);
                    if(ob.getResult()!=null)
                    {
                        Integer res= Integer.parseInt(ob.getResult().toString());
                        System.out.println("value returned from bean validation is" +res);
                        if(res==1){
                            oracle.binding.OperationBinding ob1 = ADFBeanUtils.findOperation("Commit");
                            ob1.execute();
                        }
                        else {
                            String s=ADFModelUtils.resolvRsrc("MSG.2887");
//                            String s="Please enter the values correctly";
                                        FacesMessage msg=new FacesMessage(s);
                                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                        FacesContext fctx=FacesContext.getCurrentInstance();
                                        fctx.addMessage(null, msg);
                            
                        }
                    
                    
                    }
                    
                    
                    }
                    else
                    {   
                                        String s=ADFModelUtils.resolvRsrc("MSG.2888");
//                                        String s="Total percent should be equal to 100!!!";
                                        FacesMessage msg=new FacesMessage(s);
                                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                                        FacesContext fctx=FacesContext.getCurrentInstance();
                                        fctx.addMessage(null, msg);
                        }
                }
        }
                }
                
        
       

    public void editMethod(ActionEvent actionEvent) {
        // Add event code here...
        RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "E");
    }
}
