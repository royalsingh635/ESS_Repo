package schemepolicyapp.view.bean;

import java.sql.SQLException;


import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;

import oracle.binding.BindingContainer;

import java.util.Date;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.server.JboPrecisionScaleValidator;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;

public class PlcyAddEditBean {
    private String formMode = "V";
    private Timestamp currDate = new Timestamp(System.currentTimeMillis());
    private RichInputText eoIdBind;
    private RichInputText execIdBind;
    private RichSelectOneChoice catgIdBind;
    private RichInputDate validFromBind;
    private RichInputText minAmtBind;
    private RichInputText minQtyBind;
    private RichInputText schemeIdBind;
    private RichSelectOneChoice schemePlcBasisBind;
    private RichInputText grpIdBind;
    private RichInputText itmIdBind;
    private RichSelectBooleanCheckbox replicateAllCheckbind;
    private RichPanelFormLayout detailFormbind;
    private RichPanelFormLayout headerFormBind;
    private RichInputText schemeNameBind;
    private RichPanelFormLayout middleFormBind;

    public void setCurrDate(oracle.jbo.domain.Timestamp currDate) {
        this.currDate = currDate;
    }

    public oracle.jbo.domain.Timestamp getCurrDate() {
        Timestamp t = new Timestamp(System.currentTimeMillis());
        try {
            // Date d = new Date(t.dateValue().toString());
            t = new Timestamp(t.dateValue() + " 00:00:00");
            System.out.println("Value of Date-->" + t);
        } catch (SQLException e) {
        }
        currDate = t;
        return currDate;

        //       return currDate;
    }

    public void setFormMode(String formMode) {
        this.formMode = formMode;
    }

    public String getFormMode() {
        return formMode;
    }

    public PlcyAddEditBean() {
    }

    public void editAction(ActionEvent actionEvent) {
        // Add event code here...
        setFormMode("E");
    }

    public BindingContainer getBinding() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void saveAction(ActionEvent actionEvent) {
        // Add event code here...
       
            setFormMode("V");
       

    }

    public void AddAction(ActionEvent actionEvent) {
        // Add event code here...
        getBinding().getOperationBinding("executeDistinctPolicyVo").execute();
        getBinding().getOperationBinding("CreateInsert").execute();
        setFormMode("A");
    }

    public void cancelAction(ActionEvent actionEvent) {
        // Add event code here...
        Object val = this.resolvEl("#{pageFlowScope.PARAM_PLCY_MODE}");
        getBinding().getOperationBinding("Rollback").execute();

        setFormMode("V");
    }

    public Object resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void categorySelectionListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(headerFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(middleFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(detailFormbind);
        //  getBinding().getOperationBinding("setCustToNull").execute();
    }

    public boolean validateAllFiledsOnPage() {
        Object object_2 = eoIdBind.getValue();
        Object object = execIdBind.getValue();
        Object object_3 = catgIdBind.getValue();
        System.out.println("value are: " + object_2 + "\t" + object + "\t" + object_3);
        if (eoIdBind.getValue() == null && execIdBind.getValue() == null &&
            (catgIdBind.getValue() == null || catgIdBind.getValue().toString().equalsIgnoreCase(""))) {
            FacesMessage msg = new FacesMessage("Please select Atleast one among Category customer and Executive !!");
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return true;
        }
        return false;
    }

    public void setEoIdBind(RichInputText eoIdBind) {
        this.eoIdBind = eoIdBind;
    }

    public RichInputText getEoIdBind() {
        return eoIdBind;
    }

    public void setExecIdBind(RichInputText execIdBind) {
        this.execIdBind = execIdBind;
    }

    public RichInputText getExecIdBind() {
        return execIdBind;
    }

    public void setCatgIdBind(RichSelectOneChoice catgIdBind) {
        this.catgIdBind = catgIdBind;
    }

    public RichSelectOneChoice getCatgIdBind() {
        return catgIdBind;
    }

    public void setValidFromBind(RichInputDate validFromBind) {
        this.validFromBind = validFromBind;
    }

    public RichInputDate getValidFromBind() {
        return validFromBind;
    }

    public boolean checkAllValidations() {
        if (schemePlcBasisBind != null && schemePlcBasisBind.getValue().equals(720) &&
            (catgIdBind.getValue() == null || catgIdBind.getValue().equals(0))) {
            System.out.println("Catg value is null");
            FacesMessage msg = new FacesMessage("Category is empty !!", "Please Select Category Id.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(catgIdBind.getClientId(), msg);
            return false;
        }
        /* if (validFromBind.getValue() == null) {
            System.out.println("Date is null...");
            FacesMessage msg = new FacesMessage("Please Select Valid From Date.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(validFromBind.getClientId(), msg);
            return false;
        } */
        if (schemeIdBind.getValue() == null || schemeIdBind.getValue().toString().equals("")) {
            System.out.println("Date is null...");
            FacesMessage msg = new FacesMessage("Sheme can not be Empty", "Please Select Scheme Id !!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;
        }
        /*  if (grpIdBind.getValue() == null && itmIdBind.getValue() == null) {
            FacesMessage msg = new FacesMessage("Please Select either Group Name or Item Name !!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;
        } */
        return true;
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }


    public void maxQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            Number val = (Number) object;
            boolean flag = isPrecisionValid(26, 6, (Number) object);
            System.out.println("Flg val is: " + flag);
            if (!flag) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid value",
                                                              "Please enter the correct value !!"));
            } else if (val.compareTo(Number.zero()) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid value",
                                                              "Value must be greater than Zero !!"));
            }

            if (minQtyBind.getValue() != null) {
                System.out.println("Came .." + (val.compareTo((Number) minQtyBind.getValue()) < 0));
                if (val.compareTo((Number) minQtyBind.getValue()) < 0) {
                    System.out.println("done...");
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid value",
                                                                  "Quantity must be greater than Minimum Quantity !!"));
                }
            }
        }
    }

    public void minQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            Number val = (Number) object;
            boolean flag = isPrecisionValid(26, 6, (Number) object);
            System.out.println("Flg val is: " + flag);
            if (!flag) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid value",
                                                              "Please enter the correct value !!"));
            } else if (val.compareTo(Number.zero()) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid value",
                                                              "Value must be greater than Zero !!"));
            }
        }
    }

    public void maxAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            Number val = (Number) object;
            boolean flag = isPrecisionValid(26, 6, (Number) object);
            System.out.println("Flg val is: " + flag);
            if (!flag) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid value",
                                                              "Please enter the correct value !!"));
            } else if (val.compareTo(Number.zero()) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid value",
                                                              "Value must be greater than Zero !!"));
            }

            if (minAmtBind.getValue() != null) {
                if (val.compareTo((Number) minAmtBind.getValue()) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid value",
                                                                  "Amount must be greater than Minimum Amount !!"));
                }
            }
        }
    }

    public void minAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            Number val = (Number) object;
            boolean flag = isPrecisionValid(26, 6, (Number) object);
            System.out.println("Flg val is: " + flag);
            if (!flag) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid value",
                                                              "Please enter the correct value !!"));
            } else if (val.compareTo(Number.zero()) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid value",
                                                              "Value must be greater than Zero !!"));
            }
        }
    }

    public void setMinAmtBind(RichInputText minAmtBind) {
        this.minAmtBind = minAmtBind;
    }

    public RichInputText getMinAmtBind() {
        return minAmtBind;
    }

    public void setMinQtyBind(RichInputText minQtyBind) {
        this.minQtyBind = minQtyBind;
    }

    public RichInputText getMinQtyBind() {
        return minQtyBind;
    }


    public void setSchemeIdBind(RichInputText schemeIdBind) {
        this.schemeIdBind = schemeIdBind;
    }

    public RichInputText getSchemeIdBind() {
        return schemeIdBind;
    }

    public void setSchemePlcBasisBind(RichSelectOneChoice schemePlcBasisBind) {
        this.schemePlcBasisBind = schemePlcBasisBind;
    }

    public RichSelectOneChoice getSchemePlcBasisBind() {
        return schemePlcBasisBind;
    }

    public void setGrpIdBind(RichInputText grpIdBind) {
        this.grpIdBind = grpIdBind;
    }

    public RichInputText getGrpIdBind() {
        return grpIdBind;
    }

    public void setItmIdBind(RichInputText itmIdBind) {
        this.itmIdBind = itmIdBind;
    }

    public RichInputText getItmIdBind() {
        return itmIdBind;
    }

    public void addplcAction() {
        // Add event code here...
        //  getBinding().getOperationBinding("CreateInsert").execute();
        setFormMode("A");
    }

    public void validTovalidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here..
        if (object != null) {
            oracle.jbo.domain.Date d = (oracle.jbo.domain.Date) object;
            if (validFromBind.getValue() != null) {
                oracle.jbo.domain.Date fromDate = (oracle.jbo.domain.Date) validFromBind.getValue();
                //  System.out.println("From date is: "+fromDate);
                // System.out.println("to date is: "+d);
                // System.out.println("compare is: "+d.compareTo(fromDate));
                if (d.compareTo(fromDate) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Date",
                                                                  "Date must be greater than or equal to From Date !!"));
                }
            }
        }
    }

    public void validFromValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        oracle.jbo.domain.Date d = (oracle.jbo.domain.Date) object;
        try {
            //oracle.jbo.domain.Date currDate = new oracle.jbo.domain.Date(System.currentTimeMillis());


            System.out.println("Curr date is: " + currDate);
            System.out.println("Valid from date is: " + d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void schemeBasisValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        /*  if (object != null) {
            OperationBinding binding = this.getBinding().getOperationBinding("chkDuplicateplcyBasis");
            binding.getParamsMap().put("plcId", Integer.parseInt(object.toString()));
            binding.execute();

            System.out.println(binding.getResult());
            if (binding.getResult() != null && binding.getResult().equals(true)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Value !!", ""));
            }
        } */
    }

    public void duplicateCategroyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        /*  if (object != null) {
            OperationBinding binding = this.getBinding().getOperationBinding("chkDuplicateCatgId");
            binding.getParamsMap().put("catgId", Integer.parseInt(object.toString()));
            binding.execute();

            System.out.println(binding.getResult());
            if (binding.getResult() != null && binding.getResult().equals(true)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Category !!",
                                                              ""));
            }
        } */
    }

    public void setReplicateAllCheckbind(RichSelectBooleanCheckbox replicateAllCheckbind) {
        this.replicateAllCheckbind = replicateAllCheckbind;
    }

    public RichSelectBooleanCheckbox getReplicateAllCheckbind() {
        return replicateAllCheckbind;
    }

    public void schemeidValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            OperationBinding binding = this.getBinding().getOperationBinding("chkDuplicateSchmId");
            binding.getParamsMap().put("schmId", object.toString());
            binding.getParamsMap().put("mode", getFormMode());
            binding.execute();

            System.out.println(binding.getResult());
            if (binding.getResult() != null && binding.getResult().equals(true)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Value !!", ""));
            }
        }
    }

    public void execIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            OperationBinding binding = this.getBinding().getOperationBinding("chkDuplicateExecId");
            binding.getParamsMap().put("eoNm", object.toString());
            binding.execute();

            System.out.println(binding.getResult());
            if (binding.getResult() != null && binding.getResult().equals(true)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Value !!", ""));
            }
        }
    }

    public void custNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        /*  if (object != null) {
            OperationBinding binding = this.getBinding().getOperationBinding("chkDuplicateCustId");
            binding.getParamsMap().put("eoNm", object.toString());
            binding.execute();

            System.out.println(binding.getResult());
            if (binding.getResult() != null && binding.getResult().equals(true)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Value !!", ""));
            }
        } */
    }

    public void setDetailFormbind(RichPanelFormLayout detailFormbind) {
        this.detailFormbind = detailFormbind;
    }

    public RichPanelFormLayout getDetailFormbind() {
        return detailFormbind;
    }


    public void setHeaderFormBind(RichPanelFormLayout headerFormBind) {
        this.headerFormBind = headerFormBind;
    }

    public RichPanelFormLayout getHeaderFormBind() {
        return headerFormBind;
    }

    public void schemeBasisChangeAction(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(headerFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(middleFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(detailFormbind);
    }

    public void groupChangeAction(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(headerFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(middleFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(detailFormbind);
    }

    public void custValChangeAction(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(headerFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(middleFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(detailFormbind);
    }

    public void execChangeAction(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(headerFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(middleFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(detailFormbind);
    }

    public void itemChaneAction(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(headerFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(middleFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(detailFormbind);
    }

    public void schemeIdChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(schemeNameBind);
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(detailFormbind);
    }

    public void setSchemeNameBind(RichInputText schemeNameBind) {
        this.schemeNameBind = schemeNameBind;
    }

    public RichInputText getSchemeNameBind() {
        return schemeNameBind;
    }

    public void setMiddleFormBind(RichPanelFormLayout middleFormBind) {
        this.middleFormBind = middleFormBind;
    }

    public RichPanelFormLayout getMiddleFormBind() {
        return middleFormBind;
    }

    public String cancelMoveAction() {
        // Add event code here...
        
        getBinding().getOperationBinding("Rollback").execute();
        setFormMode("V");
        Object val = this.resolvEl("#{pageFlowScope.PARAM_PLCY_MODE}");
        if (val != null && val.toString().equalsIgnoreCase("A")) {
            return "retrunPage";
        }

        return null;
    }

    public void AddSchemePolicy(ActionEvent actionEvent) {
        
        boolean val = this.checkAllValidations();
        DCBindingContainer dcbind = (DCBindingContainer) getBinding();
        boolean b = dcbind.getDataControl().isTransactionModified();
        System.out.println("modified : " + b);
        System.out.println("value of validation: " + val);
        if (val == true) {
            if (b == true) {
                
                System.out.println("Value of replicate:  " + replicateAllCheckbind.getValue());
                if (replicateAllCheckbind.getValue() != null &&
                    (replicateAllCheckbind.getValue().toString().equalsIgnoreCase("Y") ||
                     replicateAllCheckbind.getValue().equals(true))) {

                   

                    if (schemePlcBasisBind.getValue() != null && schemePlcBasisBind.getValue().equals(720)) {

                        Object result = getBinding().getOperationBinding("checkIfCustomerExist").execute();
                        if (result != null && result.equals(false)) {
                            FacesMessage msg =
                                new FacesMessage("There is no customer in this category can not save !!");
                            msg.setSeverity(FacesMessage.SEVERITY_WARN);
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                            return;
                        }

                        OperationBinding binding1 = getBinding().getOperationBinding("addEntryForCategory");
                        binding1.getParamsMap().put("mode", getFormMode());
                        binding1.getParamsMap().put("replFlg", "Y");
                        binding1.execute();

                        OperationBinding execute = getBinding().getOperationBinding("setCurrentDataToVO");
                        execute.getParamsMap().put("basis", 720);
                        execute.execute();
                    }
                    OperationBinding binding = getBinding().getOperationBinding("replicateDataInAllOrg");
                    binding.getParamsMap().put("mode", getFormMode());
                    binding.execute();
                    if (!(schemePlcBasisBind.getValue() != null && schemePlcBasisBind.getValue().equals(720))) {
                        OperationBinding execute = getBinding().getOperationBinding("setCurrentDataToVO");
                        execute.getParamsMap().put("basis",schemePlcBasisBind.getValue());
                        execute.execute();
                    }
                } else if (schemePlcBasisBind.getValue() != null && schemePlcBasisBind.getValue().equals(720)) {

                    Object result = getBinding().getOperationBinding("checkIfCustomerExist").execute();
                    if (result != null && result.equals(false)) {
                        FacesMessage msg = new FacesMessage("There is no customer in this category can not save !!");
                        msg.setSeverity(FacesMessage.SEVERITY_WARN);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        return;
                    }

                    OperationBinding binding = getBinding().getOperationBinding("addEntryForCategory");
                    binding.getParamsMap().put("mode", getFormMode());
                    binding.getParamsMap().put("replFlg", "N");
                    binding.execute();

                    OperationBinding execute = getBinding().getOperationBinding("setCurrentDataToVO");
                    execute.getParamsMap().put("basis", 720);
                    execute.execute();
                } else {
                    OperationBinding execute = getBinding().getOperationBinding("setCurrentDataToVO");
                    execute.getParamsMap().put("basis", schemePlcBasisBind.getValue());
                    execute.execute();
                }

                getBinding().getOperationBinding("Commit").execute();
                getBinding().getOperationBinding("Execute").execute();
                getBinding().getOperationBinding("Execute1").execute();
                getBinding().getOperationBinding("Execute2").execute();
            }
        }
    }

    public void AttachPolicyActionListener(ActionEvent actionEvent) {
        OperationBinding execute = getBinding().getOperationBinding("addPolicy");
       
        execute.execute();
    }
}
