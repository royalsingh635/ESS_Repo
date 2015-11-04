package MMProfileSetup.view;

import MMProfileSetup.model.services.MMProfileSetupAMImpl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

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
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelStretchLayout;
import oracle.adf.view.rich.component.rich.layout.RichToolbar;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;

import oracle.jbo.domain.Number;
import oracle.jbo.server.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class MMProfileSetupBean {
    private String mode="D";
    private String flg="false";
    private RichPopup addPrfPopUp;
    private RichSelectOneChoice chkSelectedOrgid;
    private String flag="true";
    private String flag1="false";
    private RichPopup freezePopup;
    private RichSelectBooleanCheckbox freezeBind;
    private RichToolbar toolbarBind;
    private RichPopup delelePopup;
    String freezsmode="P";
    private RichSelectBooleanCheckbox binBindind;
    private RichSelectBooleanCheckbox whbind;
    private RichPanelGroupLayout panelgroupbind;
    private RichPanelStretchLayout panelstretchBinding;
    private String CldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
    private Integer SlocId=Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
    private String OrgId=resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
    private String HoOrgId=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
    private String UsrId=resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString();

    public MMProfileSetupBean() {
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

    public void setAddPrfPopUp(RichPopup addPrfPopUp) {
        this.addPrfPopUp = addPrfPopUp;
    }

    public RichPopup getAddPrfPopUp() {
        return addPrfPopUp;
    }
    
    public void addPrfActionListener(ActionEvent actionEvent) {   
        flag = "false";
        flag1 = "Y";
        this.mode="A";
        freezsmode="S";
        OperationBinding op1 = getBindings().getOperationBinding("getHoName");
        op1.execute();
        String HoName=op1.getResult().toString();
         OperationBinding op = getBindings().getOperationBinding("checkHoProfile");
        op.execute();
      Integer result=(Integer)op.getResult();
        System.out.println("result from the function is===="+result);
        if(result==-2) {
            FacesMessage message = new FacesMessage("Profile For "+""+HoName+""+" is not Freezed. So first freeze profile for ["+HoName+"]");   
                  message.setSeverity(FacesMessage.SEVERITY_ERROR);   
                  FacesContext fc = FacesContext.getCurrentInstance();   
                  fc.addMessage(null, message); 

        }
        if(result==-1) {
            FacesMessage message = new FacesMessage("Please First Create The Profile For ["+HoName+"]");   
                  message.setSeverity(FacesMessage.SEVERITY_ERROR);   
                  FacesContext fc = FacesContext.getCurrentInstance();   
                  fc.addMessage(null, message); 

        }
        //showPopup(addPrfPopUp, true);
       
    }

    public void addPrfPopupCancelListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
        
    }

    public void addPrfPopupDialogListene(DialogEvent dialogEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
        
    }
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setChkSelectedOrgid(RichSelectOneChoice chkSelectedOrgid) {
        this.chkSelectedOrgid = chkSelectedOrgid;
    }

    public RichSelectOneChoice getChkSelectedOrgid() {
        return chkSelectedOrgid;
    }

    public void editPrfActionListene(ActionEvent actionEvent) {
        // Add event code here...
        flag="false";
        flag1="Y";
        freezsmode="S";
        this.mode="E";
        //showPopup(addPrfPopUp, true);
    }

    public void orgValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    /*     OperationBinding op = getBindings().getOperationBinding("curOrgid");
        op.execute();
        Object org=op.getResult(); */
    freezsmode="P";
       // flag1="true";
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void editSavePrfActionListene(ActionEvent actionEvent) {
       // if(getFreezeBind().getValue()!=null && getFreezeBind().getValue()!=""){
        System.out.println("value of get freeze bind isss ==="+getFreezeBind().getValue());
            if(getFreezeBind().getValue().toString().equalsIgnoreCase("true")){
                showPopup(freezePopup,true);
               
            }
            else{
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding =bindings.getOperationBinding("Commit");
                operationBinding.execute();
                OperationBinding operationBinding1 =bindings.getOperationBinding("Execute");
                operationBinding1.execute();

               // BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
               // BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
                flag = "true";
                flag1 = "false";
                freezsmode="P";
                this.mode="D";
                //String msg2 = "Record Saved Successfully.";
                String msg2 = resolvElDCMsg("#{bundle['MSG.91']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null,message2); 
            }
        //}
        //showPopup(freezePopup,true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(toolbarBind);
     }

    public void cancelPrfActionListene(ActionEvent actionEvent) {
       // OperationBinding obind=getBindings().getOperationBinding("Rollback");
        //obind.execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
        flag = "true";
        flag1 = "false";
        freezsmode="P";
        this.mode="D";
    }

    public void deletePrfActionListener(ActionEvent actionEvent) {
        showPopup(delelePopup, true);
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getFlag1() {
        return flag1;
    }
    
    public Object resolvElDC(String data) {
       FacesContext fc = FacesContext.getCurrentInstance();
       Application app = fc.getApplication();
       ExpressionFactory elFactory = app.getExpressionFactory();
       ELContext elContext = fc.getELContext();
       ValueExpression valueExp =
       elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
       return valueExp.getValue(elContext);
      }
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {

        CallableStatement st = null;
        try {
            // 1. Create a JDBC CallabledStatement
            MMProfileSetupAMImpl am =(MMProfileSetupAMImpl)resolvElDC("MMProfileSetupAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            // 2. Register the first bind variable for the return value
            st.registerOutParameter(1, sqlReturnType);

            if (bindVars != null) {
                // 3. Loop over values for the bind variables passed in, if any
                for (int z = 0; z < bindVars.length; z++) {
                    // 4. Set the value of user-supplied bind vars in the stmt
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            // 5. Set the value of user-supplied bind vars in the stmt
            st.executeUpdate();
            // 6. Return the value of the first bind variable
         
            return st.getObject(1);
        } catch (SQLException e) {
            throw new JboException(e.getMessage());
        } finally {
            if (st != null) {
                try {
                    // 7. Close the statement
                    st.close();
                } catch (SQLException e) {
                    throw new JboException(e.getMessage());
                }
            }
        }
    }

    public void freezeDialogListener(DialogEvent dialogEvent) {
        if(dialogEvent.getOutcome().name().equalsIgnoreCase("yes")){
            //OperationBinding obind=getBindings().getOperationBinding("Commit");
            //obind.execute();
        if(freezeBind.getValue().equals(true)&&whbind.getValue().equals(false))
        {
        System.out.println("OrgId:"+OrgId+"CldId:"+CldId+"HoOrg:"+HoOrgId+"SlocId:"+SlocId+"UsrId:"+UsrId);
                   Integer  result=(Integer)(callStoredFunction(Types.INTEGER, "APP.FN_GEN_AUTO_WH (?,?,?,?,?)", new Object[] {CldId,SlocId,HoOrgId,OrgId,UsrId}));
                   System.out.println("Result :"+result);
        }
                   BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
          BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
            
            flag1 = "true";
           // String msg2 = "Record Save And Freeze Successfully.";
           String msg2 = resolvElDCMsg("#{bundle['MSG.1224']}").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null,message2);  
            freezsmode="Y";
            this.mode="D";
            flag = "true";
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelstretchBinding);
               }else{
          
        }
     
    }

    public void setFreezePopup(RichPopup freezePopup) {
        this.freezePopup = freezePopup;
    }

    public RichPopup getFreezePopup() {
        return freezePopup;
    }

    public void freezeCancelListener(PopupCanceledEvent popupCanceledEvent) {
    }

    public void freezeValueChangeListener(ValueChangeEvent vce) {
      
        AdfFacesContext.getCurrentInstance().addPartialTarget(toolbarBind);
    } 

    public void setFreezeBind(RichSelectBooleanCheckbox freezeBind) {
        this.freezeBind = freezeBind;
    }

    public RichSelectBooleanCheckbox getFreezeBind() {
        return freezeBind;
    }

    public void setToolbarBind(RichToolbar toolbarBind) {
        this.toolbarBind = toolbarBind;
    }

    public RichToolbar getToolbarBind() {
        return toolbarBind;
    }
    
  

    public void deleteDialogListener(DialogEvent dialogEvent) {
        if(dialogEvent.getOutcome().name().equalsIgnoreCase("yes")){
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("delAction").execute();
             BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
            
            flag = "true";
            freezsmode="P";
            AdfFacesContext.getCurrentInstance().addPartialTarget(toolbarBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelgroupbind);
        }else{
            
        }
    }

    public void setDelelePopup(RichPopup delelePopup) {
        this.delelePopup = delelePopup;
    }

    public RichPopup getDelelePopup() {
        return delelePopup;
    }

    public void setFreezsmode(String freezsmode) {
        this.freezsmode = freezsmode;
    }

    public String getFreezsmode() {
        return freezsmode;
    }
    String freezeChk="";
    /*    public String getFreezeChk(){
        String freez = "N";
        if(freezeBind.getValue()!=null){
            if(freezeBind.getValue().equals(true)){
                freez = "Y";
            }else{
                freez = "N";
            }
         
        }
        return freez;
    } */
    public void setFreezeChk(String freezeChk) {
        this.freezeChk = freezeChk;
    }

    public String getFreezeChk() {
        String freez = "N";
        if(freezsmode=="P"){
            if(freezeBind.getValue()!=null){
                if(freezeBind.getValue().equals(true)){
                    freez = "Y";
                }else{
                    freez = "N";
                }
            }
        }
        return freez;
    }

    public void setBinBindind(RichSelectBooleanCheckbox binBindind) {
        this.binBindind = binBindind;
    }

    public RichSelectBooleanCheckbox getBinBindind() {
        return binBindind;
    }

    public void whValueChangeListner(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(valueChangeEvent.getNewValue()!=null){
            System.out.println("before false "+valueChangeEvent.getNewValue());
            if(valueChangeEvent.getNewValue().toString().equalsIgnoreCase("false")){
                System.out.println(valueChangeEvent.getNewValue());
        if(binBindind.getValue()!=null && binBindind.getValue()!=""){
            binBindind.setValue(false);
        }
        }
    }
    }
    
    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
      return vc.validateValue(total);
    }
    
    public Object resolvElDCMsg(String data) {
           FacesContext fc = FacesContext.getCurrentInstance();
           Application app = fc.getApplication();
           ExpressionFactory elFactory = app.getExpressionFactory();
           ELContext elContext = fc.getELContext();
           ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
           return valueExp.getValue(elContext);
       } 

    public void mmProfileValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        
        if(object != null)
        {
         oracle.jbo.domain.Number number = (oracle.jbo.domain.Number)object;
         
         if(number.compareTo(new oracle.jbo.domain.Number(0)) <= 0)
          {throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvElDCMsg("#{bundle['MSG.474']}").toString(), null));}
         
         if(!isPrecisionValid(26, 6, number))
             {throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvElDCMsg("#{bundle['MSG.57']}").toString(), null)); }
         
        }
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }

    public String getFlg() {
        return flg;
    }

    public void setWhbind(RichSelectBooleanCheckbox whbind) {
        this.whbind = whbind;
    }

    public RichSelectBooleanCheckbox getWhbind() {
        return whbind;
    }

    public void binCheckVCL(ValueChangeEvent vce) {
        if(vce.getNewValue()!=null) {
            if(vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if(whbind.getValue().toString().equalsIgnoreCase("false")) {
                     whbind.setValue(true);
                }
            }
        }
         }

    public void setPanelgroupbind(RichPanelGroupLayout panelgroupbind) {
        this.panelgroupbind = panelgroupbind;
    }

    public RichPanelGroupLayout getPanelgroupbind() {
        return panelgroupbind;
    }

    public void setPanelstretchBinding(RichPanelStretchLayout panelstretchBinding) {
        this.panelstretchBinding = panelstretchBinding;
    }

    public RichPanelStretchLayout getPanelstretchBinding() {
        return panelstretchBinding;
    }
}
