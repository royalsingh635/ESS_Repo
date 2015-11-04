package finopclstkdtlapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;


public class FinOpClStkDtlBean {
    String mode = "V";
    private RichSelectOneChoice stkTypeBinding;
    private Boolean enableCostCenter = null;
    Integer glblDocId = 38501;
    Integer glblDocType = 0;
    Number zero = new Number(0);
    private RichInputDate stkDtBinding;
    private RichInputText closingStkBinding;
    private RichInputText openingStkBinding;

    public void setZero(Number zero) {
        this.zero = zero;
    }

    public Number getZero() {
        return zero;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public FinOpClStkDtlBean() {
    }

    public String addStkDtlACTION() {
        OperationBinding createOp = ADFBeanUtils.getOperationBinding("CreateInsert");
        createOp.execute();
        mode = "A";
        return null;
    }

    public String editStkDtlACTION() {
        if (!(isEditAllowedForTheDate(null))) {
            mode = "A";
        } else {
            ADFModelUtils.showFacesMessage("Edit Not Allowed for the Record(Period has been Closed).", " ",
                                           FacesMessage.SEVERITY_ERROR, null);
        }

        return null;
    }

    public String saveStkDtlACTION() {
        if (callToSaveApplication()) {
            OperationBinding saveCC = ADFBeanUtils.getOperationBinding("sendDataFromTempCcToPmsSiteRcdCc");
            saveCC.execute();
            OperationBinding saveOp = ADFBeanUtils.getOperationBinding("Commit");
            saveOp.execute();
            ADFModelUtils.showFacesMessage("Record Saved Successfully.", " ", FacesMessage.SEVERITY_INFO, null);
        }
        return null;
    }

    public String cancleStkDtlACTION() {
        OperationBinding createOp = ADFBeanUtils.getOperationBinding("Rollback");
        createOp.execute();
        mode = "V";
        return null;
    }

    public void stkDtVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(stkTypeBinding);
    }

    public void stkTypeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding valid = ADFBeanUtils.getOperationBinding("chkDuplicateValues");
            valid.getParamsMap().put("type", object);
            valid.execute();

            if (valid.getResult() != null && valid.getResult().toString().equals("N")) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, "Duplicate Data Exist.",
                                                              null));

            }
        }
    }

    public void openingStockValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number amt = (Number) (object);
            if (!ADFBeanUtils.isPrecisionValid(26, 6, amt))
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Precision (26,6)",
                                                              null));

            if (amt.compareTo(new Number(0)) < 0)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                              "Amount can not be less than Zero.", null));
        }
    }

    public void closingStockValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number amt = (Number) (object);
            if (!ADFBeanUtils.isPrecisionValid(26, 6, amt))
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Precision (26,6)",
                                                              null));

            if (amt.compareTo(new Number(0)) < 0)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                              "Amount can not be less than Zero.", null));
        }
    }

    public void setStkTypeBinding(RichSelectOneChoice stkTypeBinding) {
        this.stkTypeBinding = stkTypeBinding;
    }

    public RichSelectOneChoice getStkTypeBinding() {
        return stkTypeBinding;
    }


    //Setting values to check cost center aplicable
    public Boolean getEnableCostCenter() {
        //isCostCenterApplicable
        if (enableCostCenter == null) {
            OperationBinding b = ADFBeanUtils.getOperationBinding("isCostCenterApplicable");
            if (b != null) {
                b.execute();
                Object o = b.getResult();
                enableCostCenter = (o == null ? false : (Boolean) o);
            }
        }
        return enableCostCenter;
    }

    //Action to go to cost center detail service page
    public String costCenterAction() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            return "costCenter";
        } else {
            return null;
        }
    }

    public void stockDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date stkDt = null;
            java.sql.Date sysDt = null;
            try {
                stkDt = ((Timestamp) object).dateValue();
                sysDt = (new Timestamp(System.currentTimeMillis())).dateValue();
                if (stkDt.compareTo(sysDt) > 0) {
                    if (sysDt.toString().equals(stkDt.toString())) {
                        //ok
                    } else {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Value can not be a Future Date.", null));
                    }
                }
            } catch (SQLException e) {
                System.out.println("Exception in cast");
            }

            //Validate if Period is closed or not
            if (isEditAllowedForTheDate(stkDt)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Entry Not Allowed on this Date (Period has been Closed).",
                                                              null));
            }
        }
    }

    public Boolean isEditAllowedForTheDate(java.sql.Date date) {
        System.out.println("Inside validation check " + date);
        OperationBinding binding = ADFBeanUtils.getOperationBinding("chkIfTxnAllowd");
        binding.getParamsMap().put("txnDate", date);
        binding.execute();
        System.out.println("Result =" + binding.getResult());
        if (binding.getResult() != null && binding.getResult().toString().equals("Y")) {
            return true;
        }
        return false;
    }

    public Boolean callToSaveApplication() {
        if (stkDtBinding.getValue() != null && stkDtBinding.getValue().toString().length() > 0) {
        } else {
            ADFModelUtils.showFacesMessage("Please select Date.", " ", FacesMessage.SEVERITY_WARN,
                                           stkDtBinding.getClientId());
            return false;
        }
        if (stkTypeBinding.getValue() != null && stkTypeBinding.getValue().toString().length() > 0) {
        } else {
            ADFModelUtils.showFacesMessage("Please select Type.", " ", FacesMessage.SEVERITY_WARN,
                                           stkTypeBinding.getClientId());
            return false;
        }
        /*  if (openingStkBinding.getValue() != null && openingStkBinding.getValue().toString().length() > 0) {
        } else {
            ADFModelUtils.showFacesMessage("Please Enter Value.", " ", FacesMessage.SEVERITY_WARN,
                                           openingStkBinding.getClientId());
            return false;
        } */
        if (closingStkBinding.getValue() != null && closingStkBinding.getValue().toString().length() > 0) {
        } else {
            ADFModelUtils.showFacesMessage("Please Enter Value.", " ", FacesMessage.SEVERITY_WARN,
                                           closingStkBinding.getClientId());
            return false;
        }

        OperationBinding saveOp = ADFBeanUtils.getOperationBinding("Commit");
        saveOp.execute();
        mode = "V";
        return true;
    }

    public void setStkDtBinding(RichInputDate stkDtBinding) {
        this.stkDtBinding = stkDtBinding;
    }

    public RichInputDate getStkDtBinding() {
        return stkDtBinding;
    }

    public void setClosingStkBinding(RichInputText closingStkBinding) {
        this.closingStkBinding = closingStkBinding;
    }

    public RichInputText getClosingStkBinding() {
        return closingStkBinding;
    }

    public void setOpeningStkBinding(RichInputText openingStkBinding) {
        this.openingStkBinding = openingStkBinding;
    }

    public RichInputText getOpeningStkBinding() {
        return openingStkBinding;
    }

    public String deleteStkDtlACTION() {
        OperationBinding createOp = ADFBeanUtils.getOperationBinding("Delete");
        createOp.execute();
        OperationBinding saveOp = ADFBeanUtils.getOperationBinding("Commit");
        saveOp.execute();
        mode = "V";
        ADFModelUtils.showFacesMessage("Record has been Removed.", " ", FacesMessage.SEVERITY_INFO, null);

        return null;
    }
}

