package appUsrProfileSec.view.bean;

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


    static {
        count = (Integer)getBinding().getOperationBinding("on_load_page").execute();
        System.out.println(count);
    }

    public UserProfileSecurity() {
    }

    public void setUserProfileSearchString(RichInputText userProfileSearchString) {
        this.userProfileSearchString = userProfileSearchString;
    }

    public RichInputText getUserProfileSearchString() {
        return userProfileSearchString;
    }


    /**
     *
     **/
    public void profileSearchActionListener(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding createOB = getBinding().getOperationBinding("searchUserProfileDescription");
        if (userProfileSearchString.getValue() != null) {
            createOB.getParamsMap().put("searchString", userProfileSearchString.getValue());
            createOB.execute();
        }
    }


    /**
     *
     **/
    public void resetSearchStringActionListener(ActionEvent actionEvent) {
        // Add event code here...
        userProfileSearchString.setValue("");
        OperationBinding createOB = getBinding().getOperationBinding("resetUserProfileDescription");

        createOB.execute();

    }


    /**
     *
     **/
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
            Integer usrid = Integer.parseInt(object.toString());
            if (usrid < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than Zero", null));
            }

        }
    }

    public void pwdMAxLength(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer pwd = Integer.parseInt(object.toString());
            if (pwd < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than Zero", null));
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
}
