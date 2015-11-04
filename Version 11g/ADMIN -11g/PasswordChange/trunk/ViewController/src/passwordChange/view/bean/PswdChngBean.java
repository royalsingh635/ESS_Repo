package passwordChange.view.bean;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

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

import javax.security.auth.Subject;
import javax.security.auth.login.FailedLoginException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputText;



import oracle.binding.BindingContainer;

import oracle.jbo.JboException;

import oracle.jbo.server.ViewObjectImpl;

import passwordChange.model.Module.ChngPswdAMImpl;
import passwordChange.model.View.AppSecUsrVORowImpl;

import passwordChange.view.utill.WeblogicUser;

import weblogic.security.URLCallbackHandler;

import weblogic.security.services.Authentication;

import weblogic.servlet.security.ServletAuthentication;

public class PswdChngBean {
    private RichInputText currPassWord;
    private RichInputText confPassWord;
    private RichInputText currentPasswordBind;
    private RichInputText newPasswordBind;
    private RichInputText confirmPassBind;
    
    ChngPswdAMImpl am = (ChngPswdAMImpl)resolvElDC("ChngPswdAMDataControl");
            String        sb=null;
            
    private Integer pwdMinLength=null;
    private Integer pwdMaxLength=null;
    private Integer splCount=null;
    private Integer cpasCount=null;
            
    
  //  Integer count =(Integer)getBindings().getOperationBinding("on_load_page").execute();
    public PswdChngBean() {
        try{ 
          
           this.callStoredFunctionWithReturn(Types.VARCHAR, "FN_SECURITY_USR_PRF_INFO(?,?,?,?,?,?,?,?,?)", new Object[]{
                                                                                                           am.getUserId(),
                                                                                                           am.getSlocId(),
                                                                                                           am.getCldId()     
                                                                                                           });
           // System.out.println("min length :"+this.getPwdMinLength() +"Max Length :"+this.getPwdMaxLength() + "capsCount "+this.getCpasCount() +"SplCount "+this.getSplCount());
            }catch(Exception e){
            e.printStackTrace();
            }
    }

    public BindingContainer getBindings()
    {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void saveButton(ActionEvent actionEvent) {
        if (currentPasswordBind.getValue() != null && currentPasswordBind.getValue().toString().length() != 0) {
            if (newPasswordBind.getValue() != null && newPasswordBind.getValue().toString().length() != 0) {
                if (confirmPassBind.getValue() != null && confirmPassBind.getValue().toString().length() != 0) {
                            ChngPswdAMImpl am = (ChngPswdAMImpl)resolvElDC("ChngPswdAMDataControl");
                            ViewObjectImpl v1 = am.getAppSecUsr1();
                            AppSecUsrVORowImpl row1 = (AppSecUsrVORowImpl)v1.getCurrentRow();
                          
                            Integer slocid = row1.getSlocId();
                            Integer usrid = row1.getUsrId();
                            String oldpwd=currentPasswordBind.getValue().toString();
        
  //                          call to get user original decrypted password.
                            oldpwd =
                                (String)(callStoredFunction(Types.VARCHAR, "APP.PKG_SEC.FN_GET_USR_DEC_PWD(?,?)", new Object[] { slocid,
                                                                                                                                 usrid }));

                          
                          
                            if (oldpwd.equalsIgnoreCase(currentPasswordBind.getValue().toString())) {
                                if (!(newPasswordBind.getValue().toString().equalsIgnoreCase(confirmPassBind.getValue().toString()))) {
                                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.542']}").toString());
                                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(confirmPassBind.getClientId(fc), message);
        
        
                                } else {
        
                                    String newpass = newPasswordBind.getValue().toString();
                           
        /*
         * Funtion to update paasword fields
         */
                                    String setpwd =
                                        (String)(callStoredFunction(Types.VARCHAR, "APP.PKG_SEC.FN_SET_USR_PWD(?,?,?)",
                                                                    new Object[] { slocid, usrid, newpass }));
        
                                    am.getTransaction().commit();
                                    
                                    try{
                                        
                                        WeblogicUser.createConnection();
                                    }catch(Exception e){
                                        
                                        FacesMessage message = new FacesMessage("There is Problem in creating connection .Retry Later.");
                                        message.setSeverity(FacesMessage.SEVERITY_INFO);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message);
                                    }
                                   
                                      Boolean b= WeblogicUser.resetUserPassword(usrid.toString(), newpass);
                                   
                                   
                                    WeblogicUser.closeConnection();
                                    
                                    
                                    if(b){
                                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.543']}").toString());
                                        message.setSeverity(FacesMessage.SEVERITY_INFO);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message);
                                   
                                    }else{
                                  
                                            FacesMessage message = new FacesMessage("Password cant be changed u have to login with your old password");
                                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                            FacesContext fc = FacesContext.getCurrentInstance();
                                            fc.addMessage(null, message);
                                    }
                                    newPasswordBind.setValue(null);
                                    currentPasswordBind.setValue(null);
                                    confirmPassBind.setValue(null);
        
                                }
                            } else { //Message for current password <> old password
                                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.544']}").toString());
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(currentPasswordBind.getClientId(fc), message);
        
        
                            }
                     
                        
                        } else { //Message for null value of Confirm password
                            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.508']}").toString());
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(confirmPassBind.getClientId(fc), message);
        
                        }
                          
                   
            } else { //Message for New passwrod=null
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.508']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(newPasswordBind.getClientId(fc), message);
            }
        } else { //message for Current password=null
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.508']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(currentPasswordBind.getClientId(fc), message);

        }
    

       
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

    public void validateConfirmPass(FacesContext facesContext, UIComponent uIComponent, Object object) {
        
    }

    public void setCurrPassWord(RichInputText currPassWord) {
        this.currPassWord = currPassWord;
    }

    public RichInputText getCurrPassWord() {
        return currPassWord;
    }

    public void setConfPassWord(RichInputText confPassWord) {
        this.confPassWord = confPassWord;
    }

    public RichInputText getConfPassWord() {
        return confPassWord;
    }

   

    public void setCurrentPasswordBind(RichInputText currentPasswordBind) {
        this.currentPasswordBind = currentPasswordBind;
    }

    public RichInputText getCurrentPasswordBind() {
        return currentPasswordBind;
    }

    public void setNewPasswordBind(RichInputText newPasswordBind) {
        this.newPasswordBind = newPasswordBind;
    }

    public RichInputText getNewPasswordBind() {
        return newPasswordBind;
    }

    public void setConfirmPassBind(RichInputText confirmPassBind) {
        this.confirmPassBind = confirmPassBind;
    }

    public RichInputText getConfirmPassBind() {
        return confirmPassBind;
    }
   
    
  
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
            CallableStatement st = null;
            try {
                ChngPswdAMImpl am = (ChngPswdAMImpl)resolvElDC("ChngPswdAMDataControl");
                st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
                st.registerOutParameter(1,sqlReturnType);
                if (bindVars != null) {
                    for (int z = 0; z < bindVars.length; z++) {
                        st.setObject(z + 2, bindVars[z]);
                    }
                }
                st.executeUpdate();
     
                return st.getObject(1);
            } catch (SQLException e) {
                throw new JboException(e);
            } finally {
                if (st != null) {
                    try {
                        st.close();
                    } catch (SQLException e) { throw new JboException(e);
                    }
                }
            }
        }


   

   
    public Object resolvElDCMsg(String data) {
           FacesContext fc = FacesContext.getCurrentInstance();
           Application app = fc.getApplication();
           ExpressionFactory elFactory = app.getExpressionFactory();
           ELContext elContext = fc.getELContext();
           ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
           return valueExp.getValue(elContext);
       }

    public void validatePassword(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object!=null){
            String password=(String)object;
            
            if(password.length()>7){
            
            
                String expression = ".*\\d+(.)*";
                CharSequence inputStr = password;
                Pattern pattern = Pattern.compile(expression);
                Matcher matcher = pattern.matcher(inputStr);

                // String error = resolvEl("#{bundle['usergroups.UserEmailID.Message']}");
                String error = "Password should have minimum 1 digit ";

                if (matcher.matches()) {
                
                    String s=this.validatePwdString(password);
                    if(s.equals("Y")){
                        
                    }else{
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Password should contain "+this.getSplCount()+" special Character and "+this. getCpasCount()+" charcter in upper case",null));
                    }
                } else {

                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
                }
                
            
            }else{
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "password's length should be more than 7 character", null));
            }
        }
    }
    
    
    protected Object callStoredFunctionWithReturn(int sqlReturnType, String stmt, Object[] bindVars) {
               CallableStatement st = null;
               try {
                   
                   st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
                   st.registerOutParameter(1,sqlReturnType);
                   
                   if (bindVars != null) {
                       for (int z = 0; z < bindVars.length; z++) {
                           st.setObject(z + 2, bindVars[z]);
                       }
                   }
                

                   st.registerOutParameter(5,Types.NUMERIC);
                   st.registerOutParameter(6,Types.NUMERIC);
                   st.registerOutParameter(7,Types.NUMERIC);
                   st.registerOutParameter(8,Types.NUMERIC);
                   st.registerOutParameter(9,Types.NUMERIC);
                   st.registerOutParameter(10, Types.NUMERIC);
                   st.executeUpdate();
                   
                   this.setPwdMinLength(((Number)(st.getObject(7))).intValue());
                   this.setPwdMaxLength(((Number)(st.getObject(8))).intValue());
                   this.setCpasCount(((Number)(st.getObject(9))).intValue());
                   this.setSplCount(((Number)(st.getObject(10))).intValue());

                   return st.getObject(1);
               } catch (SQLException e) {
                   e.printStackTrace();
                   throw new JboException(e);
               } finally {
                   if (st != null) {
                       try {
                           st.close();
                   } catch (SQLException e) {
                       throw new JboException(e);
                       }
                   }
               }
           }
    
    public void validateConfirmPwd(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object !=null){
            String cnfPwd=object.toString();
            
            if(this.newPasswordBind!=null)
            if(cnfPwd.toString().equals(this.newPasswordBind.getValue().toString())){
                String s=this.validatePwdString(cnfPwd);
                if(s.equals("Y")){
                    
                }else{
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Password should contain "+this.getSplCount()+" special Character and "+this. getCpasCount()+" charcter in upper case",null));
                }
            }else{
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Re-entered Password does not match ",null));
            }
        }
         
    }
    
    
    public String validatePwdString(String pwd){
        String result="N";
        String expression=".*";
        if(this.getSplCount()>0){
         expression = "(\\w*[_|^\\w]\\w*){"+ this.getSplCount()+"}";
        }else{
            expression = "\\w*[^_]\\w*";
        }
            
        CharSequence inputStr = pwd;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        System.out.println("Trying to match special charcter ");
        if(matcher.matches()){
        
            System.out.println("matching for speecial character");
            if(this.getCpasCount()>0){
              
                 expression = "(([[a-z0-9]|[_|^\\w]])*[A-Z]([[a-z0-9]|[_|^\\w]])*){"+ this.getCpasCount()+"}";
            }else{
                expression = "^([[a-z0-9]|[_|^\\w]])*)?";
            }
                pattern = Pattern.compile(expression);
                 matcher = pattern.matcher(inputStr);
                 if(matcher.matches()){
                 System.out.println("matched cpas charcter");
                     result="Y";
                 }else{
                     result="N";
                 }
            }
            
       else{
            result="N";
        }
        
       return result;
    }

    public void setPwdMinLength(Integer pwdMinLength) {
        this.pwdMinLength = pwdMinLength;
    }

    public Integer getPwdMinLength() {
        return pwdMinLength;
    }

    public void setPwdMaxLength(Integer pwdMaxLength) {
        this.pwdMaxLength = pwdMaxLength;
    }

    public Integer getPwdMaxLength() {
        return pwdMaxLength;
    }

    public void setSplCount(Integer splCount) {
        this.splCount = splCount;
    }

    public Integer getSplCount() {
        return splCount;
    }

    public void setCpasCount(Integer cpasCount) {
        this.cpasCount = cpasCount;
    }

    public Integer getCpasCount() {
        return cpasCount;
    }


}
