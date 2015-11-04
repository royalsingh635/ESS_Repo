package bdgeoprofileapp.view.bean;

import adf.utils.model.ADFModelUtils;

import bdgeoprofileapp.view.utils.ADFUtils;
import bdgeoprofileapp.view.utils.JSFUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlValueBindingRef;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class SearchBdgEoProfileBean {
    private RichInputDate startDatePgBind;
    private RichInputDate endDatePgBind;
    private RichTable searchTablePgBind;
    private RichInputText budgetIdPgBind;
    private RichInputText employeeNamePgBind;
    private RichSelectOneChoice financialYearPgBind;
    private RichSelectOneChoice orgIdPgBind;
    private RichInputText docIdPgBind;
    private RichPopup remarksPopUpPgBind;
    private RichOutputText amtNotationDescPgBind;

    public SearchBdgEoProfileBean() {
    }

    public void startDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (endDatePgBind.getValue() != null) {
                // System.out.println("From Date is "+fromDatePgBind.getValue());
                // System.out.println("To date is "+object);

                Timestamp t1 = (Timestamp) endDatePgBind.getValue();
                Timestamp t2 = (Timestamp) object;

                //System.out.println("T2 Compare to T1 "+t2.compareTo(t1));
                if (t1.compareTo(t2) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  ADFModelUtils.resolvRsrc("MSG.2061"), null));
                    // MSG.2061  From Date Should be on or before To Date.
                }
            }
        }
    }

    public void endDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (startDatePgBind.getValue() != null) {
                // System.out.println("From Date is "+fromDatePgBind.getValue());
                // System.out.println("To date is "+object);

                Timestamp t1 = (Timestamp) startDatePgBind.getValue();
                Timestamp t2 = (Timestamp) object;

                //System.out.println("T2 Compare to T1 "+t2.compareTo(t1));
                if (t2.compareTo(t1) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  ADFModelUtils.resolvRsrc("MSG.1521"), null));
                    // MSG.1521 To Date Should be greater than From Date.
                }
            }
        }
    }

    public void setStartDatePgBind(RichInputDate startDatePgBind) {
        this.startDatePgBind = startDatePgBind;
    }

    public RichInputDate getStartDatePgBind() {
        return startDatePgBind;
    }

    public void setEndDatePgBind(RichInputDate endDatePgBind) {
        this.endDatePgBind = endDatePgBind;
    }

    public RichInputDate getEndDatePgBind() {
        return endDatePgBind;
    }

    public void searchButtonAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("searchDataAccordingly").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTablePgBind);
    }

    public void resetButtonAL(ActionEvent actionEvent) {
        budgetIdPgBind.setValue(null);
        employeeNamePgBind.setValue(null);
        financialYearPgBind.setValue(null);
        startDatePgBind.setValue(null);
        endDatePgBind.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(budgetIdPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(employeeNamePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(financialYearPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(startDatePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(endDatePgBind);

        ADFUtils.findOperation("resetDataAccordingly").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTablePgBind);
    }

    public void setSearchTablePgBind(RichTable searchTablePgBind) {
        this.searchTablePgBind = searchTablePgBind;
    }

    public RichTable getSearchTablePgBind() {
        return searchTablePgBind;
    }

    public void setBudgetIdPgBind(RichInputText budgetIdPgBind) {
        this.budgetIdPgBind = budgetIdPgBind;
    }

    public RichInputText getBudgetIdPgBind() {
        return budgetIdPgBind;
    }

    public void setEmployeeNamePgBind(RichInputText employeeNamePgBind) {
        this.employeeNamePgBind = employeeNamePgBind;
    }

    public RichInputText getEmployeeNamePgBind() {
        return employeeNamePgBind;
    }

    public void setFinancialYearPgBind(RichSelectOneChoice financialYearPgBind) {
        this.financialYearPgBind = financialYearPgBind;
    }

    public RichSelectOneChoice getFinancialYearPgBind() {
        return financialYearPgBind;
    }

    public List budgetIdAutoSuggestion(String string) {
        //get access to the binding context and binding container at runtime
        BindingContext bctx = BindingContext.getCurrent();
        BindingContainer bindings = bctx.getCurrentBindingsEntry();
        //set the bind variable value that is used to filter the View Object
        //query of the suggest list. The View Object instance has a View Criteria assigned

        Integer usrId = Integer.parseInt(JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_USR}").toString());
        String hoOrgId = (String) JSFUtils.resolveExpression("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cldId = (String) JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer slocId = Integer.parseInt(JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());

        /*  System.out.println("Values are " + usrId + " " + cldId + " " + hoOrgId + " " + orgIdPgBind.getValue() + " " +
                           slocId + " " + string); */

        System.out.println("String entered is =" + string);
        OperationBinding setVariable = (OperationBinding) bindings.get("setBindCldId");
        setVariable.getParamsMap().put("value", cldId);
        setVariable.execute();

        setVariable = (OperationBinding) bindings.get("setBindSlocId");
        setVariable.getParamsMap().put("value", slocId);
        setVariable.execute();

        setVariable = (OperationBinding) bindings.get("setBindHoOrgId");
        setVariable.getParamsMap().put("value", hoOrgId);
        setVariable.execute();

        setVariable = (OperationBinding) bindings.get("setBindOrgId");
        setVariable.getParamsMap().put("value", orgIdPgBind.getValue());
        setVariable.execute();

        setVariable = (OperationBinding) bindings.get("setBindUserId");
        setVariable.getParamsMap().put("value", usrId);
        setVariable.execute();

        setVariable = (OperationBinding) bindings.get("setBindBdgId");
        setVariable.getParamsMap().put("value", string);
        setVariable.execute();

        setVariable = (OperationBinding) bindings.get("setBindEoId");
        setVariable.getParamsMap().put("value", null);
        setVariable.execute();

        //the data in the suggest list is queried by a tree binding.
        JUCtrlHierBinding hierBinding = (JUCtrlHierBinding) bindings.get("LOVBdgIdAutoSuggestionVO1");
        //re- query the list based on the new bind variable values
        hierBinding.executeQuery();
        //The rangeSet,the list of queries entries, is of type JUCtrlValueBndingRef.
        List<JUCtrlValueBindingRef> displayDataList = hierBinding.getRangeSet();
        System.out.println("No .of values in displayDataList=" + displayDataList.size());
        ArrayList<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (JUCtrlValueBindingRef displayData : displayDataList) {
            Row rw = displayData.getRow();
            //populate the SelectItem list
            selectItems.add(new SelectItem((String) rw.getAttribute("BudgetId"), (String) rw.getAttribute("BudgetId")));
        }
        return selectItems;
    }

    public List empNameAutoSuggestion(String string) {
        //get access to the binding context and binding container at runtime
        BindingContext bctx = BindingContext.getCurrent();
        BindingContainer bindings = bctx.getCurrentBindingsEntry();
        //set the bind variable value that is used to filter the View Object
        //query of the suggest list. The View Object instance has a View Criteria assigned
        Integer usrId = Integer.parseInt(JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_USR}").toString());
        String hoOrgId = (String) JSFUtils.resolveExpression("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cldId = (String) JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer slocId = Integer.parseInt(JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());

        System.out.println("String entered is =" + string);
        OperationBinding setVariable = (OperationBinding) bindings.get("setBindCldId");
        setVariable.getParamsMap().put("value", cldId);
        setVariable.execute();

        setVariable = (OperationBinding) bindings.get("setBindSlocId");
        setVariable.getParamsMap().put("value", slocId);
        setVariable.execute();

        setVariable = (OperationBinding) bindings.get("setBindHoOrgId");
        setVariable.getParamsMap().put("value", hoOrgId);
        setVariable.execute();

        setVariable = (OperationBinding) bindings.get("setBindOrgId");
        setVariable.getParamsMap().put("value", orgIdPgBind.getValue());
        setVariable.execute();

        setVariable = (OperationBinding) bindings.get("setBindUserId");
        setVariable.getParamsMap().put("value", usrId);
        setVariable.execute();

        setVariable = (OperationBinding) bindings.get("setBindEoId");
        setVariable.getParamsMap().put("value", string);
        setVariable.execute();

        setVariable = (OperationBinding) bindings.get("setBindBdgId");
        setVariable.getParamsMap().put("value", null);
        setVariable.execute();

        //the data in the suggest list is queried by a tree binding.
        JUCtrlHierBinding hierBinding = (JUCtrlHierBinding) bindings.get("LOVBdgIdAutoSuggestionVO1");
        //re- query the list based on the new bind variable values
        hierBinding.executeQuery();
        //The rangeSet,the list of queries entries, is of type JUCtrlValueBndingRef.
        List<JUCtrlValueBindingRef> displayDataList = hierBinding.getRangeSet();
        System.out.println("No .of values in displayDataList=" + displayDataList.size());
        HashSet hs = new HashSet();
        //ArrayList<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (JUCtrlValueBindingRef displayData : displayDataList) {
            Row rw = displayData.getRow();
            //populate the SelectItem list
            //selectItems.add(new SelectItem((String) rw.getAttribute("EoNm"), (String) rw.getAttribute("EoNm")));
            hs.add(new SelectItem((String) rw.getAttribute("EoNm"), (String) rw.getAttribute("EoNm")));
        }

        //return selectItems;
        return new ArrayList<SelectItem>(hs);
    }

    public void setOrgIdPgBind(RichSelectOneChoice orgIdPgBind) {
        this.orgIdPgBind = orgIdPgBind;
    }

    public RichSelectOneChoice getOrgIdPgBind() {
        return orgIdPgBind;
    }

    public void remarksHistoryPopUpFL(PopupFetchEvent popupFetchEvent) {
        OperationBinding ob = ADFUtils.findOperation("showRemarksHistory");
        ob.getParamsMap().put("docId", docIdPgBind.getValue());
        ob.execute();
    }

    public void setDocIdPgBind(RichInputText docIdPgBind) {
        this.docIdPgBind = docIdPgBind;
    }

    public RichInputText getDocIdPgBind() {
        return docIdPgBind;
    }

    public void showRemarksButtonAL(ActionEvent actionEvent) {
        showPopup(remarksPopUpPgBind, true);
    }

    public void setRemarksPopUpPgBind(RichPopup remarksPopUpPgBind) {
        this.remarksPopUpPgBind = remarksPopUpPgBind;
    }

    public RichPopup getRemarksPopUpPgBind() {
        return remarksPopUpPgBind;
    }

    private void showPopup(RichPopup popup, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && popup != null) {
                String popupId = popup.getClientId(context);
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

    public void employeeHierarchyDL(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
            ADFUtils.findOperation("setBindVarForEmpHierarchyView").execute();
        }
    }

    public void amountNotationVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());

            RequestContext.getCurrentInstance().getPageFlowScope().put("P_AMT_NOTATION", vce.getNewValue());

            Number val = (Number) vce.getNewValue();
            if (val.compareTo(1) > 0) {
                RequestContext.getCurrentInstance().getPageFlowScope().put("P_NOTATION_DISP", "Y");
            } else {
                RequestContext.getCurrentInstance().getPageFlowScope().put("P_NOTATION_DISP", "N");
            }
            RequestContext.getCurrentInstance().getPageFlowScope().put("DISP_AMT_NOTATION",
                                                                       amtNotationDescPgBind.getValue());
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchTablePgBind);

    }

    public void setAmtNotationDescPgBind(RichOutputText amtNotationDescPgBind) {
        this.amtNotationDescPgBind = amtNotationDescPgBind;
    }

    public RichOutputText getAmtNotationDescPgBind() {
        return amtNotationDescPgBind;
    }

    public void orgNameVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            ADFUtils.findOperation("setEmployeeCodeToGlobalParam").execute();
        }
    }

    public String addButtonAction() {
        if (JSFUtils.resolveExpression("#{pageFlowScope.P_EMP_CODE}") != null) {
            return "add";
        } else {
            JSFUtils.addFacesErrorMessage(ADFModelUtils.resolvRsrc("MSG.2051")); // MSG.2051 You are not a valid employee

            return null;
        }
    }

    public String viewDtlAction() {
        if (JSFUtils.resolveExpression("#{pageFlowScope.P_EMP_CODE}") != null) {
            return "view";
        } else {
            JSFUtils.addFacesErrorMessage(ADFModelUtils.resolvRsrc("MSG.2051")); // MSG.2051 You are not a valid employee

            return null;
        }
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = "N";
        if (valueExp != null) {
            msg = valueExp.getValue(elContext).toString();
        }
        return msg;
    }
}
