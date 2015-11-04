package svcwarrantyprfapp.view.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
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

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adfinternal.view.faces.context.AdfFacesContextImpl;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.context.RequestContext;

public class Svcwarrantyprfbean {
    private RichInputListOfValues pagebindwarrantyname;
    private RichInputText pagebindname;
    private RichInputText pagebindperiod;
    private String mode="C";
    private String newmode="V";
    private RichPanelFormLayout pagebindwrrntyform;
    private RichTable pagebinditemtable;
    private RichSelectBooleanCheckbox activeCheckBind;
    private RichInputText inactvResnBind;

    public void setNewmode(String newmode) {
        this.newmode = newmode;
    }

    public String getNewmode() {
        
        return newmode;
    }
    public String pass=" ";
    private RichSelectBooleanCheckbox fullreplacebinding;
    private RichInputDate pagebindinactivedate;
    private RichInputListOfValues pagebindItemname;
    
    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public Svcwarrantyprfbean() 
    {
    }

   

    public void setPagebindwarrantyname(RichInputListOfValues pagebindwarrantyname) {
        this.pagebindwarrantyname = pagebindwarrantyname;
    }

    public RichInputListOfValues getPagebindwarrantyname() {
        return pagebindwarrantyname;
    }
    public void searchwarranty(ActionEvent actionEvent) 
    {
        OperationBinding ob=BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("searchwaranty");
        ob.getParamsMap().put("warantynam",pagebindwarrantyname.getValue() );
        ob.execute();
        System.out.println(ob.getErrors());
    }
    public void resetwarranty(ActionEvent actionEvent) 
    {
        OperationBinding ob=BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("resetwarrantyname");
        ob.execute();
        System.out.println(ob.getErrors());
    }

    public void cancelwarranty(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding ob=BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback");
        ob.execute();
//        RequestContext context = RequestContext.getCurrentInstance();
//                context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
        this.newmode="V";
        this.mode="C";
      
    }

    public void addwarranty(ActionEvent actionEvent) {
        // Add event code here...
        String hoOrgId = (resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString());
        String orgId=(resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString());
       
      
//        RequestContext context = RequestContext.getCurrentInstance();
//        context.getPageFlowScope().put("ADD_EDIT_MODE", "A");
        this.newmode="A";
        OperationBinding ob=BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert");
        ob.execute();
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getWarrantyId")
                       .execute();  
        
      
      
           
      
    }

    public void deletewarranty(ActionEvent actionEvent) {
        // Add event code here...
//        RequestContext context = RequestContext.getCurrentInstance();
//                context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
        OperationBinding ob=BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete");
        ob.execute();
       this.mode = "P";
    }

    public void savewarranty(ActionEvent actionEvent)
    {
        // Add event code here...
        DCIteratorBinding difrSumm = (DCIteratorBinding) getBindings().get("SvcWtyPrf1Iterator");
        System.out.println("No. of Rows=="+difrSumm.getEstimatedRowCount());
        String msg="";
        if(difrSumm.getEstimatedRowCount() != 0) 
        {
            if(pagebindname.getValue()==null || pagebindperiod.getValue()==null ) 
            { 

                       
                       if((pagebindname.getValue())==null)
                       {
                         System.out.println("1.............."+pagebindname.getValue());
                         FacesMessage message = new FacesMessage("Field is Mandatory");
                         message.setSeverity(FacesMessage.SEVERITY_ERROR);
                         FacesContext fc = FacesContext.getCurrentInstance();
                         fc.addMessage(pagebindname.getClientId(), message);   
                       }
                       
                       if((pagebindperiod.getValue())==null )
                       {
                           
                         System.out.println("taxname"+pagebindperiod.getValue());
                         FacesMessage message = new FacesMessage("Field is Mandatory");
                         message.setSeverity(FacesMessage.SEVERITY_ERROR);
                         FacesContext fc = FacesContext.getCurrentInstance();
                         fc.addMessage(pagebindperiod.getClientId(), message);   
                       }
                       
                      
                       
            } 
            else
            {
///             System.out.println("----------per dys--"+ difrSumm.getCurrentRow().getAttribute("PrdDays").toString());
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit")
                           .execute();
                

                msg = "MSG.91";
                showFacesMessage(msg, "I", true, "F");
//                RequestContext context = RequestContext.getCurrentInstance();
//                context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                this.newmode="V";
                this.mode="C";
            }
          }
          
    else
        {
                   
                      BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit")
                                 .execute();
            

                      msg = "MSG.91";
                      showFacesMessage(msg, "I", true, "F");
//                      RequestContext context = RequestContext.getCurrentInstance();
//                     context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
            this.newmode="V";
            this.mode="C";
        }

    }

    public void editwarranty(ActionEvent actionEvent) {
        // Add event code here...
//        RequestContext context = RequestContext.getCurrentInstance();
//                context.getPageFlowScope().put("ADD_EDIT_MODE", "A");
        this.newmode="A";
       
    }

    public void setPagebindname(RichInputText pagebindname) {
        this.pagebindname = pagebindname;
    }

    public RichInputText getPagebindname() {
        return pagebindname;
    }

    public void setPagebindperiod(RichInputText pagebindperiod) {
        this.pagebindperiod = pagebindperiod;
    }

    public RichInputText getPagebindperiod() {
        return pagebindperiod;
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
    
    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }

    public void replacementwayvaluechange(ValueChangeEvent valueChangeEvent) 
    {
        if(valueChangeEvent.getNewValue() != null && ((valueChangeEvent.getNewValue()).toString()).equals("R"))
        {
            fullreplacebinding.setValue("N");
        }
    }

    public void setFullreplacebinding(RichSelectBooleanCheckbox fullreplacebinding) {
        this.fullreplacebinding = fullreplacebinding;
    }

    public RichSelectBooleanCheckbox getFullreplacebinding() {
        return fullreplacebinding;
    }
    
    
    public Object resolvElDCMsg(String data) {
               FacesContext fc = FacesContext.getCurrentInstance();
               Application app = fc.getApplication();
               ExpressionFactory elFactory = app.getExpressionFactory();
               ELContext elContext = fc.getELContext();
               ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
               return valueExp.getValue(elContext);
           } 

    public void validatename(FacesContext facesContext, UIComponent uIComponent, Object object) 
    {
        if(object!=null && object.toString().length()>0)
        { 
            String st=(String)object;
            if(st.trim().length() == 0) 
            {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvElDCMsg("#{bundle['MSG.1610']}").toString(),null)); 
            }
            
            
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =bindings.getOperationBinding("warrantyNamevalidate");
            operationBinding.getParamsMap().put("type", st.trim());
            operationBinding.execute();
            pass=(String)operationBinding.getResult();
            if(pass.equals("Y")) 
            {
                
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvElDCMsg("#{bundle['MSG.1611']}").toString(),null));
            } 
            
          
            

        
        
        }

    }
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void validateperiod(FacesContext facesContext, UIComponent uIComponent, Object object) 
    {
        if(object!=null)
        {
            Boolean flag= isPrecisionValid(26, 6, ((oracle.jbo.domain.Number)object));
            if(flag) {}
            else{
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvElDCMsg("#{bundle['MSG.57']}").toString(),null)); 
            }
        }
    }
    
    public Boolean isPrecisionValid(Integer precision,Integer scale,oracle.jbo.domain.Number total){
    JboPrecisionScaleValidator vc=new JboPrecisionScaleValidator();
    vc.setPrecision(precision);
    vc.setScale(scale);
    return vc.validateValue(total);
    }

    public void setPagebindinactivedate(RichInputDate pagebindinactivedate) {
        this.pagebindinactivedate = pagebindinactivedate;
    }

    public RichInputDate getPagebindinactivedate() {
        return pagebindinactivedate;
    }

    public void activevaluechange(ValueChangeEvent valueChangeEvent)
    {  
       // valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(pagebindinactivedate);
        AdfFacesContext.getCurrentInstance().addPartialTarget(inactvResnBind);
        
        System.out.println("active value"+valueChangeEvent.getNewValue().toString());
       
        if(valueChangeEvent.getNewValue() != null ) 
        {
            if(((valueChangeEvent.getNewValue()).toString()).equals("false"))
            {
                
                java.sql.Date sysdate=null;
                Long sys=System.currentTimeMillis();
                pagebindinactivedate.setValue(new oracle.jbo.domain.Timestamp(System.currentTimeMillis()));

            }else{
                pagebindinactivedate.setValue(null);
                inactvResnBind.setValue(null);
            }
          
            AdfFacesContext.getCurrentInstance().addPartialTarget(pagebindinactivedate);
            AdfFacesContext.getCurrentInstance().addPartialTarget(inactvResnBind);
        }
    }

    public void deleteItem(ActionEvent actionEvent) {
       
        OperationBinding ob=BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete1");
        ob.execute();
    }

    public void addItem(ActionEvent actionEvent) 
    {
        if(pagebindItemname.getValue() != null)
        {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("addwarrantyItem");
        operationBinding.getParamsMap().put("type", pagebindItemname.getValue());
        operationBinding.execute();
            if(operationBinding.getErrors().size()==0 ) 
            {
                System.out.println("reached");
                
               
                AdfFacesContext.getCurrentInstance().addPartialTarget(pagebindItemname);
                AdfFacesContext.getCurrentInstance().addPartialTarget(pagebinditemtable);
                
                }
            else{
                System.out.println(operationBinding.getErrors());
            }
        }
    }

    public void setPagebindItemname(RichInputListOfValues pagebindItemname) {
        this.pagebindItemname = pagebindItemname;
    }

    public RichInputListOfValues getPagebindItemname() {
        return pagebindItemname;
    }

    public void validateItemname(FacesContext facesContext, UIComponent uIComponent, Object object) 
    {
        if(object != null)
               {
                  String st=(String)object;
                
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding =bindings.getOperationBinding("itemNamevalidate");
                operationBinding.getParamsMap().put("type", st);
                operationBinding.execute();
                pass=(String)operationBinding.getResult();
                if(pass.equals("Y")) 
                {
                    
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvElDCMsg("#{bundle['MSG.1612']}").toString(),null));
                } 

                }
                
               

    }

    public void setPagebindwrrntyform(RichPanelFormLayout pagebindwrrntyform) {
        this.pagebindwrrntyform = pagebindwrrntyform;
    }

    public RichPanelFormLayout getPagebindwrrntyform() {
        return pagebindwrrntyform;
    }

    public void setPagebinditemtable(RichTable pagebinditemtable) {
        this.pagebinditemtable = pagebinditemtable;
    }

    public RichTable getPagebinditemtable() {
        return pagebinditemtable;
    }

    public void setActiveCheckBind(RichSelectBooleanCheckbox activeCheckBind) {
        this.activeCheckBind = activeCheckBind;
    }

    public RichSelectBooleanCheckbox getActiveCheckBind() {
        return activeCheckBind;
    }

    public void setInactvResnBind(RichInputText inactvResnBind) {
        this.inactvResnBind = inactvResnBind;
    }

    public RichInputText getInactvResnBind() {
        return inactvResnBind;
    }
}
