package appUsrProfileSec.view.bean;

import appUsrProfileSec.model.module.AppSecUsrPrfAMImpl;

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

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;

public class UserProfileSecurity {
    private String Mode = "V";
    private RichInputText userProfileSearchString;
    private Boolean isRO = true;

    private static Integer count;
    private Boolean isRenderPage;
    private RichTable tableBinding;
    private RichSelectBooleanCheckbox activebinding;
    private RichSelectBooleanCheckbox resvBinding;
    private RichInputText usrIdMinLenthBinding;
    private RichInputText pwdMinLengthBinding;
    private RichInputText usrIdMaxLengthBinding;
    private RichInputText pwdMaxLengthBinding;
    private RichInputText profileNmBinding;
    
    
    // varible to store value returned by function FN_SECURTIY_USR_MIN_MAX_ALLOW created and used  BY MS
    private Integer userMinLength;
    private Integer userMaxLength;
    private Integer pwdMinLength;
    private Integer pwdMaxLength;
    private Integer specialCharLB;
    private Integer specialCharUB;
    private String specialCherAllowed;
    private RichInputText capsCount;
    private RichInputText spclCount;
    private RichInputText alertDays;
    private RichInputText changeDays;


    static {
        count = (Integer)getBinding().getOperationBinding("on_load_page").execute();
        System.out.println(count);
    }

    public UserProfileSecurity() {
        try{
          
            this.callStoredFunction(Types.VARCHAR, "FN_SECURTIY_USR_MIN_MAX_ALLOW(?,?,?,?,?,?,?,?)", new Object[]{ 
                                                                                                   null    });
        
           System.out.println("min val"+this.getUserMinLength() + "MAx Length "+this.getUserMaxLength() +"Pwd Max Length "+this.getPwdMaxLength() +"pwd Min Length "+this.getPwdMinLength() + "Special charAlowd :"+this.specialCherAllowed);
           this.setPwdMinLength(8);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    
    }

    public void setUserProfileSearchString(RichInputText userProfileSearchString) {
        this.userProfileSearchString = userProfileSearchString;
    }

    public RichInputText getUserProfileSearchString() {
        return userProfileSearchString;
    }

    public void profileSearchActionListener(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding createOB = getBinding().getOperationBinding("searchUserProfileDescription");
        if (userProfileSearchString.getValue() != null) {
            createOB.getParamsMap().put("searchString", userProfileSearchString.getValue());
            createOB.execute();
        }
    }

    public void resetSearchStringActionListener(ActionEvent actionEvent) {
        // Add event code here...
        userProfileSearchString.setValue("");
        OperationBinding createOB = getBinding().getOperationBinding("resetUserProfileDescription");

        createOB.execute();

    }

    public static BindingContainer getBinding() {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        return bindings;
    }

    public String editAction() {
        // Add event code here...
        this.setIsRO(false);
        this.setMode("E");
        tableBinding.setRowSelection("none");
        System.out.println("EDIT btn CLICKED = MODE = " + Mode);
        return null;
    }

    public void setIsRO(Boolean isRO) {
        this.isRO = isRO;
    }

    public Boolean getIsRO() {
        return isRO;
    }


    public String addAction() {

        // Add event code here...
        this.setIsRO(false);
        this.setMode("A");
        tableBinding.setRowSelection("none");

        System.out.println("ADD btn clicked = MODE =" + Mode);
        BindingContainer bc = getBinding();
        OperationBinding binding = bc.getOperationBinding("CreateInsert");
        binding.execute();
        return null;
    }

    public void setMode(String Mode) {
        this.Mode = Mode;
    }

    public String getMode() {
        return Mode;
    }

    public String cancelAction() {
        // Add event code here...
        System.out.println("CANCEL BTN CLICKED = MODE = " + Mode);
        if (this.Mode.equalsIgnoreCase("E") || this.Mode.equalsIgnoreCase("A")) {
            this.setMode("V");
            this.setIsRO(true);

            System.out.println(Mode);
            System.out.println(isRO);
            tableBinding.setRowSelection("single");

            BindingContainer bc = getBinding();
            OperationBinding binding = bc.getOperationBinding("Rollback");
            binding.execute();
            return null;
        } else {
            this.setMode("V");
            this.setIsRO(false);
            tableBinding.setRowSelection("single");

            return null;
        }


    }

    public String saveAction() {
        // Add event code here...
        System.out.println("SAVE BTN CLICKED = MODE " + Mode);
        int count = 0;
        if (this.Mode.equalsIgnoreCase("E") || this.Mode.equalsIgnoreCase("A")) {


            if (usrIdMaxLengthBinding.getValue() != null && usrIdMinLenthBinding.getValue() != null) {
                System.out.println("usrIdMaxLengthBinding.getValue() = " + usrIdMaxLengthBinding.getValue());
                System.out.println("usrIdMinLenthBinding.getValue() = " + usrIdMinLenthBinding.getValue());
                if (Integer.parseInt(usrIdMaxLengthBinding.getValue().toString()) <
                    Integer.parseInt(usrIdMinLenthBinding.getValue().toString())) {
                    count++;
                    FacesMessage message =
                        new FacesMessage("UserId Maximum Length should be greater than Minimum length");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            }
            if (pwdMaxLengthBinding.getValue() != null && pwdMinLengthBinding.getValue() != null) {
                System.out.println("pwdMaxLengthBinding.getValue() = " + pwdMaxLengthBinding.getValue());
                System.out.println("pwdMinLengthBinding.getValue() = " + pwdMinLengthBinding.getValue());
                if (Integer.parseInt(pwdMaxLengthBinding.getValue().toString()) <
                    Integer.parseInt(pwdMinLengthBinding.getValue().toString())) {
                    count++;
                    FacesMessage message =
                        new FacesMessage("Password Maximum Length should be greater than Minimum length");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            }
            if (resvBinding.getValue() != null && activebinding.getValue() != null) {
                System.out.println("resvBinding.getValue().toString() = " + resvBinding.getValue().toString() +
                                   "activebinding.getValue().toString() = " + activebinding.getValue().toString());

                if (resvBinding.getValue().toString().equalsIgnoreCase("true") &&
                    activebinding.getValue().toString().equalsIgnoreCase("false")) {
                    //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Reserved User can not be made In Active !! ", null));
                    count++;
                    FacesMessage message = new FacesMessage("Reserved User can not be made In Active !!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }

            }
            
            if(this.getSpclCount()!=null && this.getCapsCount() !=null ){

                Integer sum=Integer.parseInt(this.getSpclCount().getValue().toString())+Integer.parseInt(this.getCapsCount().getValue().toString());
                Integer pwdMin=Integer.parseInt(this.getPwdMinLengthBinding().getValue().toString());
                Integer pwdMax=Integer.parseInt(this.getPwdMaxLengthBinding().getValue().toString());
                  if(pwdMax.compareTo(sum+1)<0){
                    count++;
                    FacesMessage message = new FacesMessage("Sum of No of Caps charater in password and no of special character in password should be one less than man password length ");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }else{
                    
                }
            }
           if(this.getAlertDays()!=null && this.getChangeDays()!=null) 
            if(this.getAlertDays().getValue()!=null && this.getChangeDays().getValue()!=null){
            if(Integer.parseInt(this.getAlertDays().getValue().toString())>Integer.parseInt(this.getChangeDays().getValue().toString())){
                count++;
                FacesMessage message = new FacesMessage("Number of Alert Days cant be more than number of Change Days");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
            }
            if (count == 0) {
                this.setIsRO(true);
                this.setMode("V");
                System.out.println(Mode);
                tableBinding.setRowSelection("single");
                BindingContainer bc = getBinding();
                OperationBinding binding = bc.getOperationBinding("Commit");
                binding.execute();
                if (binding.getErrors().isEmpty()) {
                    FacesMessage message = new FacesMessage("Record Saved Successfuly");
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);

                } else {
                    FacesMessage message = new FacesMessage("Error in Creating Records. Please Contact ESS!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);

                }
                // return "save";
            }

            //return "null";
        } else {
            this.setIsRO(false);
            tableBinding.setRowSelection("single");

            //return null;
        }
        return null;
    }

    public static void setCount(Integer count) {
        UserProfileSecurity.count = count;
    }

    public static Integer getCount() {
        return count;
    }

    public void setIsRenderPage(Boolean isRenderPage) {
        this.isRenderPage = isRenderPage;
    }

    public Boolean getIsRenderPage() {
        if (count == 1) {
            this.setIsRenderPage(false);
        } else {
            this.setIsRenderPage(true);
        }
        return isRenderPage;
    }

    public void setTableBinding(RichTable tableBinding) {
        this.tableBinding = tableBinding;
    }

    public RichTable getTableBinding() {
        return tableBinding;
    }

    public void setActivebinding(RichSelectBooleanCheckbox activebinding) {
        this.activebinding = activebinding;
    }

    public RichSelectBooleanCheckbox getActivebinding() {
        return activebinding;
    }

    public void setResvBinding(RichSelectBooleanCheckbox resvBinding) {
        this.resvBinding = resvBinding;
    }

    public RichSelectBooleanCheckbox getResvBinding() {
        return resvBinding;
    }

    public void UsrIdMaxLength(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer usrId = Integer.parseInt(object.toString());
            if(this.getUserMaxLength()!=null){
                if(this.getUserMaxLength().compareTo(usrId)<0){
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Value should not be greater than " +this.getUserMaxLength(),null));
                }
                if(this.getUsrIdMinLenthBinding().getValue()!=null){
                    if(usrId.compareTo(Integer.parseInt(this.getUsrIdMinLenthBinding().getValue().toString()))<0){
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Maximum legth cant be less than minimum length" ,null));
                    }
                }
            }
        }
    }
    
    public void userPrfIdMinLengthVal(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object != null){
            Integer usrId=Integer.parseInt(object.toString());
            if(this.getUserMinLength()!=null){
                
                if(this.getUserMinLength().compareTo(usrId)>0){
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Value should not be less than " +this.getUserMinLength(),null));
                }
        }
          if(this.getUsrIdMaxLengthBinding().getValue()!=null){
              if(usrId.compareTo(Integer.parseInt(this.getUsrIdMaxLengthBinding().getValue().toString()))>0){
                  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Maximum legth cant be less than minimum length" ,null));
              }
          }
      }
    } 

    public void pwdMAxLength(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer pwd = Integer.parseInt(object.toString());
            if(this.getPwdMaxLength()!=null){
                if(this.getPwdMaxLength().compareTo(pwd)<0){
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Value should not be greater than " + this.getPwdMaxLength(),null));
                }
            }
            if(this.getPwdMinLengthBinding().getValue()!=null){
                if(pwd.compareTo(Integer.parseInt(this.getPwdMinLengthBinding().getValue().toString()))<0){
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Maximum legth cant be less than minimum length" ,null));
                    
                }
            }

        }
    }
    
    public void pwdMinLengthVal(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer pwd = Integer.parseInt(object.toString());
            if(this.getPwdMinLength()!=null){
                if(this.getPwdMinLength().compareTo(pwd)>0){
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Value should not be less than " + this.getPwdMinLength(),null));
                }
            }
            
            if(this.getPwdMaxLengthBinding().getValue()!=null){
                if(pwd.compareTo(Integer.parseInt(this.getPwdMaxLengthBinding().getValue().toString()))>0){
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Maximum legth cant be less than minimum length" ,null));
                    
                }
            }

        }   
    }

    public void AlertForRenewalValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer alert = Integer.parseInt(object.toString());
            if (alert < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than Zero", null));
            }
        }
    }

    public void pwdRenewalDays(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer pwdRnwl = Integer.parseInt(object.toString());
            if (pwdRnwl < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than Zero", null));
            }
        }
    }
    

    public void setUsrIdMinLenthBinding(RichInputText usrIdMinLenthBinding) {
        this.usrIdMinLenthBinding = usrIdMinLenthBinding;
    }

    public RichInputText getUsrIdMinLenthBinding() {
        return usrIdMinLenthBinding;
    }

    public void setPwdMinLengthBinding(RichInputText pwdMinLengthBinding) {
        this.pwdMinLengthBinding = pwdMinLengthBinding;
    }

    public RichInputText getPwdMinLengthBinding() {
        return pwdMinLengthBinding;
    }

    public void setUsrIdMaxLengthBinding(RichInputText usrIdMaxLengthBinding) {
        this.usrIdMaxLengthBinding = usrIdMaxLengthBinding;
    }

    public RichInputText getUsrIdMaxLengthBinding() {
        return usrIdMaxLengthBinding;
    }

    public void setPwdMaxLengthBinding(RichInputText pwdMaxLengthBinding) {
        this.pwdMaxLengthBinding = pwdMaxLengthBinding;
    }

    public RichInputText getPwdMaxLengthBinding() {
        return pwdMaxLengthBinding;
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void nameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("object = " + object);
        if (object != null) {
            String nm = object.toString();
            if (nm.startsWith(" ") || nm.endsWith(" ")) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Space not allowed at start and end!", null));
            } else if (nm.contains("  ")) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Two continous spaces not allowed!", null));
            }

            else {
                
                        BindingContainer bc = getBinding();
                        OperationBinding binding = bc.getOperationBinding("nameValidator");
                        binding.getParamsMap().put("name", object.toString().toUpperCase());
                        binding.execute();
                        Integer result = (Integer)binding.getResult();
                        System.out.println("result in validator-->>>" + result);
                        if (result == 1) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          "Duplicate Record Exist", null));
                        }
                        else{
                                String expression = "^(?:(?>[A-Za-z0-9 \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))*(\\.|\\&(?!\\.|\\&|$))*(\\.|\\:(?!\\.|\\:|$))*" +
                                    "(\\.|\\/(?!\\.|\\/|$))*(\\.|\\;(?!\\.|\\;|$))*(\\.|\\\\(?!\\.|\\\\|$))*(\\.|\\,(?!\\.|\\,|$))?)*$";
                                            CharSequence inputStr = nm;
                                Pattern pattern = Pattern.compile(expression);
                                Matcher matcher = pattern.matcher(inputStr);
                                String error = resolvElDCMsg("#{bundle['MSG.1185']}").toString();
    
                                if (matcher.matches()) {
                                } else {
                                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error,
                                                                                  resolvElDCMsg("name can contain only these special character:/,\\,-,&,:,;").toString()));
                                }
                        }
            }
        }

    }


    public String deleteAction() {
        System.out.println("in delete action");
        OperationBinding op = getBinding().getOperationBinding("checkDeletablePrf");
        op.execute();
        if (op.getErrors().isEmpty() && op.getResult() != null)
        {
            String reslt = op.getResult().toString();
            if (reslt.equalsIgnoreCase("Y")) 
            {
                BindingContainer bc = getBinding();
                OperationBinding binding = bc.getOperationBinding("Delete");
                binding.execute();
                OperationBinding operationBinding = bc.getOperationBinding("Commit");
                operationBinding.execute();

                FacesMessage message = new FacesMessage("Record Deleted Successfully!");
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                
            }
            else if(reslt.equalsIgnoreCase("N"))
            {
                FacesMessage message = new FacesMessage("Child record exists, can not delete this profile!");
                message.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        } 
        else {
            op.getErrors();
        }
        return null;
    }

    public void setProfileNmBinding(RichInputText profileNmBinding) {
        this.profileNmBinding = profileNmBinding;
    }

    public RichInputText getProfileNmBinding() {
        return profileNmBinding;
    }
    
    
    
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
              CallableStatement st = null;
              try {
                  AppSecUsrPrfAMImpl am = (AppSecUsrPrfAMImpl)resolvElDC("AppSecUsrPrfAMDataControl");
                  st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
                  st.registerOutParameter(1,sqlReturnType);
                  
                  if (bindVars != null) {
                      for (int z = 0; z < bindVars.length; z++) {
                          st.setObject(z + 2, bindVars[z]);
                      }
                  }
               
                  st.registerOutParameter(3,Types.NUMERIC);
                  st.registerOutParameter(4,Types.NUMERIC);
                  st.registerOutParameter(5,Types.NUMERIC);
                  st.registerOutParameter(6,Types.NUMERIC);
                  st.registerOutParameter(7,Types.NUMERIC);
                  st.registerOutParameter(8,Types.NUMERIC);
                  st.registerOutParameter(9,Types.VARCHAR);
                  st.executeUpdate();
                  this.setUserMinLength(((Number)st.getObject(3)).intValue());
                  this.setUserMaxLength(((Number)st.getObject(4)).intValue());
                  this.setPwdMinLength(((Number)st.getObject(5)).intValue());
                  this.setPwdMaxLength(((Number)st.getObject(6)).intValue());
                  this.setSpecialCharLB(((Number)st.getObject(7)).intValue());
                  this.setSpecialCharUB(((Number)st.getObject(8)).intValue());
                  this.setSpecialCherAllowed((String)st.getObject(9));
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
    
     public Object resolvElDC(String data) {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    Application app = fc.getApplication();
                    ExpressionFactory elFactory = app.getExpressionFactory();
                    ELContext elContext = fc.getELContext();
                    ValueExpression valueExp =
                    elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
            return valueExp.getValue(elContext);
            }

    public void setUserMinLength(Integer userMinLength) {
        this.userMinLength = userMinLength;
    }

    public Integer getUserMinLength() {
        return userMinLength;
    }

    public void setUserMaxLength(Integer userMaxLength) {
        this.userMaxLength = userMaxLength;
    }

    public Integer getUserMaxLength() {
        return userMaxLength;
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

    public void setSpecialCharLB(Integer specialCharLB) {
        this.specialCharLB = specialCharLB;
    }

    public Integer getSpecialCharLB() {
        return specialCharLB;
    }

    public void setSpecialCharUB(Integer specialCharUB) {
        this.specialCharUB = specialCharUB;
    }

    public Integer getSpecialCharUB() {
        return specialCharUB;
    }

    public void setSpecialCherAllowed(String specialCherAllowed) {
        this.specialCherAllowed = specialCherAllowed;
    }

    public String getSpecialCherAllowed() {
        return specialCherAllowed;
    }

    public void setCapsCount(RichInputText capsCount) {
        this.capsCount = capsCount;
    }

    public RichInputText getCapsCount() {
        return capsCount;
    }

    public void setSpclCount(RichInputText spclCount) {
        this.spclCount = spclCount;
    }

    public RichInputText getSpclCount() {
        return spclCount;
    }

    public void capsCountVal(FacesContext facesContext, UIComponent uIComponent, Object object) {

    }

    public void specialCharCountVal(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(this.getSpecialCherAllowed().equalsIgnoreCase("Y")&&this.getSpecialCharLB()!=null&&this.getSpecialCharUB()!=null){
            Integer spl=Integer.parseInt(this.getSpclCount().getValue().toString());
            if(this.getSpecialCharLB().compareTo(spl)>0&&this.getSpecialCharUB().compareTo(spl)<0){
                
            }else{
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Special character count should be greater than "+this.getSpecialCharLB() +" and less than "+this.getSpecialCharUB(),null));
            }
        }
    }

    public void setAlertDays(RichInputText alertDays) {
        this.alertDays = alertDays;
    }

    public RichInputText getAlertDays() {
        return alertDays;
    }

    public void setChangeDays(RichInputText changeDays) {
        this.changeDays = changeDays;
    }

    public RichInputText getChangeDays() {
        return changeDays;
    }
}
