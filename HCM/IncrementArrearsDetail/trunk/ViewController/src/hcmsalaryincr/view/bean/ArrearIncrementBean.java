package hcmsalaryincr.view.bean;

import adf.utils.bean.ADFBeanUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class ArrearIncrementBean {
    private RichSelectOneChoice empGrp;
    private RichInputListOfValues empNm;
    private RichInputDate dtFrom;
    private RichInputDate dtUpTo;
    private RichInputDate incrmntDtNew;
    private RichInputDate applicbleDtNew;
    private RichSelectOneChoice bindingWrkStat;
    private RichSelectOneChoice incrCriteriaBinding;

    public ArrearIncrementBean() {
    }

    public void srchArrDetails(ActionEvent actionEvent) 
    {
        if (empGrp.getValue() != null || dtFrom.getValue() != null || empNm.getValue() != null ||
            dtUpTo.getValue() != null) {
            System.out.println("empGrp============" + empGrp.getValue());
            System.out.println("incrmntDt============" + dtFrom.getValue());
            System.out.println("applicblDT============" + dtUpTo.getValue());
            //BindingContainer bindings = getBindings();
            OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("searchArrDetails");
            operationBinding.getParamsMap().put("empGrp",
                                                (empGrp.getValue() == null) ? null : (this.empGrp.getValue()));
            operationBinding.getParamsMap().put("incrmntDt",
                                                (dtFrom.getValue() == null) ? null : (this.dtFrom.getValue()));
            operationBinding.getParamsMap().put("applicblDT",
                                                (dtUpTo.getValue() == null) ? null : (this.dtUpTo.getValue()));
            operationBinding.getParamsMap().put("incrmntDtnew",
                                                (incrmntDtNew.getValue() == null) ? null : (this.incrmntDtNew.getValue()));
            operationBinding.getParamsMap().put("applicblDTnew",
                                                (applicbleDtNew.getValue() == null) ? null : (this.applicbleDtNew.getValue()));
            if(operationBinding.getErrors().isEmpty()) 
            {
                System.out.println(empNm);
                operationBinding.execute();  
            }
            
        }
    }
    public BindingContainer getBindings() 
    {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void resetAllFields(ActionEvent actionEvent) {
        applicbleDtNew.setValue("");  
        incrmntDtNew.setValue("");
        empGrp.setValue(""); 
        dtFrom.setValue("");  
        dtUpTo.setValue("");  
        bindingWrkStat.setValue("");
        incrCriteriaBinding.setValue("");
        OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("resetArrDetailsAllFields");
        operationBinding.execute();

    }

    public void setEmpGrp(RichSelectOneChoice empGrp) {
        this.empGrp = empGrp;
    }

    public RichSelectOneChoice getEmpGrp() {
        return empGrp;
    }

    public void setEmpNm(RichInputListOfValues empNm) {
        this.empNm = empNm;
    }

    public RichInputListOfValues getEmpNm() {
        return empNm;
    }

    public void setDtFrom(RichInputDate dtFrom) {
        this.dtFrom = dtFrom;
    }

    public RichInputDate getDtFrom() {
        return dtFrom;
    }

    public void setDtUpTo(RichInputDate dtUpTo) {
        this.dtUpTo = dtUpTo;
    }

    public RichInputDate getDtUpTo() {
        return dtUpTo;
    }

    public void setIncrmntDtNew(RichInputDate incrmntDtNew) {
        this.incrmntDtNew = incrmntDtNew;
    }

    public RichInputDate getIncrmntDtNew() {
        return incrmntDtNew;
    }

    public void setApplicbleDtNew(RichInputDate applicbleDtNew) {
        this.applicbleDtNew = applicbleDtNew;
    }

    public RichInputDate getApplicbleDtNew() {
        return applicbleDtNew;
    }

    public void setBindingWrkStat(RichSelectOneChoice bindingWrkStat)
    {
        this.bindingWrkStat = bindingWrkStat;
    }

    public RichSelectOneChoice getBindingWrkStat()
    {
        return bindingWrkStat;
    }

    public void setIncrCriteriaBinding(RichSelectOneChoice incrCriteriaBinding)
    {
        this.incrCriteriaBinding = incrCriteriaBinding;
    }

    public RichSelectOneChoice getIncrCriteriaBinding()
    {
        return incrCriteriaBinding;
    }
}
