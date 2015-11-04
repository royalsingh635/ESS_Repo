package hcmsalaryincr.view.bean;

import adf.utils.bean.ADFBeanUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;


import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import javax.faces.context.FacesContext;


import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;


import javax.faces.application.FacesMessage;

import org.apache.myfaces.trinidad.context.RequestContext;

public class SalaryIncrementSearchBean
{
    private RichSelectOneChoice empGrp;
    private RichInputListOfValues empNM;
    private RichInputDate incrmntDt;
    private RichInputDate applicblDT;
    private RichInputDate incrmntDtNew;
    private RichInputDate applicbleDtNew;
    private RichSelectOneChoice workStatusBinding;
    private RichSelectOneChoice incrCriteriaBinding;

    public SalaryIncrementSearchBean()
    {
    }


    public void searchEmplIncrDetails(ActionEvent actionEvent)
    {
        if (incrCriteriaBinding.getValue() != null || empGrp.getValue() != null || applicblDT.getValue() != null ||
            empNM.getValue() != null || incrmntDt.getValue() != null)
        {

            if (incrCriteriaBinding.getValue() == null || incrCriteriaBinding.getValue()=="")
            {
                String message = "";
                String cid = incrCriteriaBinding.getClientId();
                message = "Please select Increment criteria for search.";
                showMessage(message, cid);

            }
            else
            {
                OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("searchIncrDetails");
                operationBinding.getParamsMap().put("empGrp",
                                                    (empGrp.getValue() == null) ? null : (this.empGrp.getValue()));
                operationBinding.getParamsMap().put("incrmntDt",
                                                    (incrmntDt.getValue() == null) ? null :
                                                    (this.incrmntDt.getValue()));
                operationBinding.getParamsMap().put("applicblDT",
                                                    (applicblDT.getValue() == null) ? null :
                                                    (this.applicblDT.getValue()));
                operationBinding.getParamsMap().put("incrmntDtnew",
                                                    (incrmntDtNew.getValue() == null) ? null :
                                                    (this.incrmntDtNew.getValue()));
                operationBinding.getParamsMap().put("applicblDTnew",
                                                    (applicbleDtNew.getValue() == null) ? null :
                                                    (this.applicbleDtNew.getValue()));
                operationBinding.execute();
            }

        }

    }

    public String showMessage(String message, String cid)
    {

        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(cid, fm);
        return null;
    }

    public void resetFields(ActionEvent actionEvent)
    {

        empGrp.setValue("");
        incrmntDt.setValue("");
        incrmntDtNew.setValue("");
        applicblDT.setValue("");
        applicbleDtNew.setValue("");
        workStatusBinding.setValue("");
        incrCriteriaBinding.setValue("");
        OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("resetAllFields");
        operationBinding.execute();


    }

    public void onViewACTION(ActionEvent actionEvent)
    {

    }

    public BindingContainer getBindings()
    {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setEmpGrp(RichSelectOneChoice empGrp)
    {
        this.empGrp = empGrp;
    }

    public RichSelectOneChoice getEmpGrp()
    {
        return empGrp;
    }

    public void setEmpNM(RichInputListOfValues empNM)
    {
        this.empNM = empNM;
    }

    public RichInputListOfValues getEmpNM()
    {
        return empNM;
    }

    public void setIncrmntDt(RichInputDate incrmntDt)
    {
        this.incrmntDt = incrmntDt;
    }

    public RichInputDate getIncrmntDt()
    {
        return incrmntDt;
    }

    public void setApplicblDT(RichInputDate applicblDT)
    {
        this.applicblDT = applicblDT;
    }

    public RichInputDate getApplicblDT()
    {
        return applicblDT;
    }

    public void fltrBysrchEmplyNm(ValueChangeEvent valueChangeEvent)
    {
        if (empGrp.getValue() != null || applicblDT.getValue() != null || empNM.getValue() != null ||
            incrmntDt.getValue() != null)
        {
            System.out.println("empGrp============" + empGrp.getValue());
            System.out.println("incrmntDt============" + incrmntDt.getValue());
            System.out.println("applicblDT============" + applicblDT.getValue());
            OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("searchIncrDetails");
            operationBinding.getParamsMap().put("empGrp",
                                                (empGrp.getValue() == null) ? null : (this.empGrp.getValue()));
            operationBinding.getParamsMap().put("incrmntDt",
                                                (incrmntDt.getValue() == null) ? null : (this.incrmntDt.getValue()));
            operationBinding.getParamsMap().put("applicblDT",
                                                (applicblDT.getValue() == null) ? null : (this.applicblDT.getValue()));
            operationBinding.execute();
        }

    }

    public String addNewAction()
    {
        RequestContext context = RequestContext.getCurrentInstance();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert").execute();
        OperationBinding operationBinding1 = ADFBeanUtils.getOperationBinding("generateNewDocId");
        operationBinding1.execute();
        if (operationBinding1.getErrors().isEmpty())
        {
            String docId = operationBinding1.getResult().toString();
            context.getPageFlowScope().put("ADD_EDIT_MODE", "S");
            context.getPageFlowScope().put("GLBL_GET_DOC_ID", docId);
            context.getPageFlowScope().put("GLBL_CHK_MODE", "E");
            System.out.println("new generatedd DocId=" + docId);
            //            OperationBinding opchk = bindings.getOperationBinding("filtrOrgHcmEmpPrf");
            //            opchk.execute();
            return "add";
        }
        else
        {
            System.out.println("some error during generation of doc id");
            return null;
        }

    }

    public String viewAction()
    {
        RequestContext context = RequestContext.getCurrentInstance();
        //BindingContainer bindings = getBindings();
        OperationBinding opchk = ADFBeanUtils.getOperationBinding("setDocId");
        opchk.execute();
        if (opchk.getErrors().isEmpty())
            opchk = ADFBeanUtils.getOperationBinding("chkIncrStatus");
        opchk.execute();
        if (opchk.getErrors().isEmpty() && opchk.getResult().toString().equals("47"))
        {
            opchk = ADFBeanUtils.getOperationBinding("chkDataInHistTable");
            opchk.execute();
            if (opchk.getErrors().isEmpty() && opchk.getResult() != null)
            {
                if (opchk.getResult().toString().equals("true"))
                    context.getPageFlowScope().put("GLBL_CHK_MODE", "H");
                else
                {
                    context.getPageFlowScope().put("GLBL_CHK_MODE", "E");
                }
            }

        }
        else
        {
            context.getPageFlowScope().put("GLBL_CHK_MODE", "E");
        }
        context.getPageFlowScope().put("ADD_EDIT_MODE", "D");
        return "view";
    }

    public void setIncrmntDtNew(RichInputDate incrmntDtNew)
    {
        this.incrmntDtNew = incrmntDtNew;
    }

    public RichInputDate getIncrmntDtNew()
    {
        return incrmntDtNew;
    }

    public void setApplicbleDtNew(RichInputDate applicbleDtNew)
    {
        this.applicbleDtNew = applicbleDtNew;
    }

    public RichInputDate getApplicbleDtNew()
    {
        return applicbleDtNew;
    }

    public String searchAllArrear()
    {
        //BindingContainer bindings = getBindings();
        OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("showArrearDetails");
        operationBinding.execute();
        System.out.println(operationBinding.getErrors());
        return "searchArrear";
    }

    public void setWorkStatusBinding(RichSelectOneChoice workStatusBinding)
    {
        this.workStatusBinding = workStatusBinding;
    }

    public RichSelectOneChoice getWorkStatusBinding()
    {
        return workStatusBinding;
    }

    public Object resolvElDC(String data)
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);

    }

    public String resolvEl(String data)
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        return msg;
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
