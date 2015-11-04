package fagrpapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import fagrpapp.model.module.FAGrpAMImpl;

import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;

public class FAGrpBean {
    private String mode = "V";
    private RichPanelFormLayout searchFormBinding;
    private String law = (String) ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_IT_LAW}");
    private RichInputDate effDtBinding;
    private RichInputDate ruleEffDtBinding;

    public void setLaw(String law) {
        this.law = law;
    }

    public String getLaw() {
        return law;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public FAGrpBean() {
    }

    public String createGrpAction() {

        ADFBeanUtils.callBindingsMethod("CreateInsert", null, null);
        mode = "A";
        return null;
    }

    public String editGrpAction() {
        // Add event code here...
        mode = "E";
        return null;
    }

    public String saveAction() {
        // Add event code here...
        Object res = ADFBeanUtils.callBindingsMethod("allRequiredValid", null, null);
        if (res.equals("N")) {
            //String msg1="All fields are mandatory.";
            String msg2 = "Please enter all the required details.";

            String msg1 = resolvElDCMsg("#{bundle['MSG.1906']}").toString();
            //String msg2 = resolvElDCMsg("#{bundle['MSG.4300']}").toString();


            ADFModelUtils.showFacesMessage(msg1, msg2, FacesMessage.SEVERITY_ERROR, null);
        } else {
            ADFBeanUtils.callBindingsMethod("replicateDataToOrg", null, null);

            ADFBeanUtils.callBindingsMethod("Commit", null, null);
            //String msg1="Record Saved Successfuly";
            //String msg2="Record Saved Successfuly";
            String msg1 = resolvElDCMsg("#{bundle['MSG.1907']}").toString();
            String msg2 = resolvElDCMsg("#{bundle['MSG.1907']}").toString();

            ADFModelUtils.showFacesMessage(msg1, msg2, FacesMessage.SEVERITY_INFO, null);

            mode = "V";
        }
        return null;
    }

    public String cancelAction() {
        mode = "V";
        ADFBeanUtils.callBindingsMethod("Rollback", null, null);

        return null;
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public String deleteAction() {
        // Add event code here...

        //String msg1="Record Deleted Successful";
        //String msg2="Group Deleted Successfuly.";
        String msg1 = resolvElDCMsg("#{bundle['MSG.528']}").toString();
        String msg2 = resolvElDCMsg("#{bundle['MSG.1908']}").toString();
        ADFBeanUtils.callBindingsMethod("Delete", null, null);
        ADFModelUtils.showFacesMessage(msg1, msg2, FacesMessage.SEVERITY_INFO, null);
        mode = "V";
        return null;
    }

    public String createGrpLineAction() {
        Object res = ADFBeanUtils.callBindingsMethod("allRequiredValid", null, null);
        if (res.equals("N")) {
            //String msg1="All fields are mandatory.";
            String msg2 = "Please enter all the required details.";

            String msg1 = resolvElDCMsg("#{bundle['MSG.1906']}").toString();
            //  String msg2 = resolvElDCMsg("#{bundle['MSG.4300']}").toString();
            ADFModelUtils.showFacesMessage(msg1, msg2, FacesMessage.SEVERITY_ERROR, null);
        } else {
            FAGrpAMImpl am = (FAGrpAMImpl) ADFBeanUtils.getApplicationModuleForDataControl("FAGrpAMDataControl");
            am.getTransaction().postChanges();
            ADFBeanUtils.callBindingsMethod("CreateInsert1", null, null);
            ruleEffDtBinding.setValue(effDtBinding.getValue());
            ViewObjectImpl prf = am.getAppFaGrpPrf1();
            Row currentRow = prf.getCurrentRow();
            Object EffDate = currentRow.getAttribute("EffDate");
            System.out.println("----darer =" + EffDate);
            Row row = am.getAppFaGrpRule1().getCurrentRow();
            row.setAttribute("FaGrpRuleEffDate", EffDate);
        }
        return null;
    }

    public String searchAction() {
        ADFBeanUtils.callBindingsMethod("searchAction", null, null);
        return null;
    }

    public String resetAction() {
        ADFBeanUtils.callBindingsMethod("resetAction", null, null);
        resetValueInputItems(AdfFacesContext.getCurrentInstance(), this.searchFormBinding);
        return null;
    }


    private void resetValueInputItems(AdfFacesContext adfFacesContext, UIComponent component) {
        List<UIComponent> items = component.getChildren();
        for (UIComponent item : items) {

            resetValueInputItems(adfFacesContext, item);

            if (item instanceof RichInputText) {
                RichInputText input = (RichInputText) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputDate) {
                RichInputDate input = (RichInputDate) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichSelectOneChoice) {
                RichSelectOneChoice input = (RichSelectOneChoice) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputListOfValues) {
                RichInputListOfValues input = (RichInputListOfValues) item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            }

        }
    }

    public void setSearchFormBinding(RichPanelFormLayout searchFormBinding) {
        this.searchFormBinding = searchFormBinding;
    }

    public RichPanelFormLayout getSearchFormBinding() {
        return searchFormBinding;
    }

    public void groupNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...  groupnameValid
        Object res = ADFBeanUtils.callBindingsMethod("groupnameValid", new Object[] { object }, new Object[] {
                                                     "Name" });
        if (res != null) {
            if (res.equals("Y")) {
                //String msg1="Duplicate Record Exist!";
                String msg1 = resolvElDCMsg("#{bundle['MSG.1909']}").toString();

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
            }
        }
    }

    public void shiftValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            Integer sh = (Integer) object;
            if (sh < 0 || sh > 3) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Not a Valid Range. It must be between 1 to 3!", null));
            } else {
                Object res = ADFBeanUtils.callBindingsMethod("shiftDuplicate", new Object[] { object }, new Object[] {
                                                             "shift" });
                System.out.println("res = " + res + "----");
                if (res != null) {
                    if (res.equals("Y")) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Duplicate Shift Exist!", null));
                    }
                }
            }
        }
    }

    public void ITDepPercentage(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            //Integer per = Integer.parseInt(object.toString());
            Number per = (Number) object;
            Boolean b = ADFBeanUtils.isPrecisionValid(9, 6, per);
            if (!b) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision/Scale",
                                                              null));

            } else if (per.compareTo(0) <= 0 || per.compareTo(100) > 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Percentage Value",
                                                              null));

            }

        }
    }

    public void CODepPercentage(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            Number per = (Number) object;
            Boolean b = ADFBeanUtils.isPrecisionValid(9, 6, per);
            if (!b) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision/Scale",
                                                              null));

            } else if (per.compareTo(0) <= 0 || per.compareTo(100) > 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Percentage Value",
                                                              null));

            }
        }
    }


    public void coLawValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Object res = ADFBeanUtils.callBindingsMethod("coLawDuplicate", new Object[] { object }, new Object[] { "law" });
        if (res != null) {
            if (res.equals("Y")) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Record Exist!",
                                                              null));
            }
        }
    }

    public void setEffDtBinding(RichInputDate effDtBinding) {
        this.effDtBinding = effDtBinding;
    }

    public RichInputDate getEffDtBinding() {
        return effDtBinding;
    }

    public void setRuleEffDtBinding(RichInputDate ruleEffDtBinding) {
        this.ruleEffDtBinding = ruleEffDtBinding;
    }

    public RichInputDate getRuleEffDtBinding() {
        return ruleEffDtBinding;
    }

    public void ruleEffDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null && effDtBinding.getValue() != null) {
            if (object.toString().compareTo(effDtBinding.getValue().toString()) < 0) {
                //String msg1= "Rule Effective Date must be less than or equal to Profile Effective Date!";
                String msg1 = resolvElDCMsg("#{bundle['MSG.1911']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));

            }
        }
    }

    public void finalizeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null && effDtBinding.getValue() != null) {
            if (object.toString().equalsIgnoreCase("true")) {
                Date dt = (Date) new Date().getCurrentDate();
                System.out.println("date =" + dt + "effDtBinding = " + effDtBinding.getValue());
                if (dt.toString().compareTo(effDtBinding.getValue().toString()) < 0) {
                    //String msg1="Cannot Finalize before effective Date!";
                    String msg1 = resolvElDCMsg("#{bundle['MSG.1912']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));

                }
            }
        }
    }

    public void RefreshMethod(ActionEvent actionEvent) {
        // Add event code here...
        Object refresh = ADFBeanUtils.callBindingsMethod("refreshItem", null, null);
        String refreshmsg = "Items updated successfully";
        ADFModelUtils.showFacesMessage(refreshmsg, refreshmsg, FacesMessage.SEVERITY_INFO, null);


    }
}
