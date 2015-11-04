package bdgeoprofileapp.view.bean;

import adf.utils.model.ADFModelUtils;

import bdgeoprofileapp.view.utils.ADFUtils;
import bdgeoprofileapp.view.utils.JSFUtils;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.server.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class BdgHierarchyBean {
    private String mode = (String) JSFUtils.resolveExpression("#{pageFlowScope.P_MODE}");
    private RichPopup empDetailsPopUpPgBind;
    private String searchInputPgBind;
    private RichPopup approveDocPopUpPgBind;
    private RichTable immediateEmpDtlPgBind;
    private RichLink empAuthorizePgBind;
    private RichOutputText empNamePgBind;
    private RichOutputText empIdPgBind;

    public BdgHierarchyBean() {
    }

    protected String getCldIdValue() {
        return (String) JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_CLD_ID}");
    }

    protected Integer getSlocIdValue() {
        return Integer.parseInt(JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
    }

    protected String getHoOrgIdValue() {
        return (String) JSFUtils.resolveExpression("#{pageFlowScope.GLBL_HO_ORG_ID}");
    }

    protected String getOrgIdValue() {
        return (String) JSFUtils.resolveExpression("#{pageFlowScope.GLBL_APP_USR_ORG}");
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setSearchInputPgBind(String searchInputPgBind) {
        this.searchInputPgBind = searchInputPgBind;
    }

    public String getSearchInputPgBind() {
        return searchInputPgBind;
    }

    /**
     *      Method to Apply Precision Validation(P,S,V)
     *      P- Precision
     *      S- Scale
     *      V- Value to validate
     * */
    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void AddEmpDetailsAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("setTargetAmount").execute();
        //ADFUtils.findOperation("Commit").execute();
        empDetailsPopUpPgBind.hide();
        mode = "V";
        RequestContext.getCurrentInstance().getPageFlowScope().put("P_RET_MODE", "V");
        //System.out.println("mode value on ok is " + mode);
    }

    public void cancelEmpDetailsAL(ActionEvent actionEvent) {
        //ADFUtils.findOperation("Rollback").execute();
        empDetailsPopUpPgBind.hide();
        mode = (String) JSFUtils.resolveExpression("#{pageFlowScope.P_MODE}");
        RequestContext.getCurrentInstance().getPageFlowScope().put("P_RET_MODE", mode);
        //System.out.println("Mode valie on cancel is " + mode);
    }

    public void setEmpDetailsPopUpPgBind(RichPopup empDetailsPopUpPgBind) {
        this.empDetailsPopUpPgBind = empDetailsPopUpPgBind;
    }

    public RichPopup getEmpDetailsPopUpPgBind() {
        return empDetailsPopUpPgBind;
    }

    public void targetAmtVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding ob = ADFUtils.findOperation("setCurrentBdgAmt");
            ob.getParamsMap().put("amt", vce.getNewValue());
            ob.execute();
        }
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public String backButtonAction() {
        /*  if (mode == null) {
            RequestContext.getCurrentInstance().getPageFlowScope().put("P_RET_MODE", "V");
        } */
        System.out.println("Mode value is " + mode);
        return "back";
    }

    public void targetAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null)); // MSG.1107  Invalid Precision(26,6).
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.198"),
                                                              null)); //MSG.198 Route cannot be edited.
            }
        }
    }

    public void empTargetAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("Object value is " + object);
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.1107"),
                                                              null)); // MSG.1107  Invalid Precision(26,6).
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              ADFModelUtils.resolvRsrc("MSG.198"),
                                                              null)); //MSG.198 Route cannot be edited.
            }
        }
    }

    public static Object invokeMethod(String expr, Class[] paramTypes, Object[] params) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ELContext elc = fc.getELContext();
        ExpressionFactory ef = fc.getApplication().getExpressionFactory();
        MethodExpression me = ef.createMethodExpression(elc, expr, Object.class, paramTypes);
        return me.invoke(elc, params);
    }

    public static Object invokeMethod(String expr, Class paramType, Object param) {
        return invokeMethod(expr, new Class[] { paramType }, new Object[] { param });
    }

    public void searchButtonAL(ActionEvent actionEvent) {
        System.out.println("Value passed to search =" + searchInputPgBind);

        DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding empBdgDtl = (DCIteratorBinding) dcBindings.get("EmpBudgetDetailForSearchVO4Iterator");
        ViewObject vo = empBdgDtl.getRowSetIterator().getRowSet().getViewObject();
        vo.setNamedWhereClauseParam("BindCldId", this.getCldIdValue());
        vo.setNamedWhereClauseParam("BindSlocId", this.getSlocIdValue());
        vo.setNamedWhereClauseParam("BindHoOrgId", this.getHoOrgIdValue());
        vo.setNamedWhereClauseParam("BindOrgId", this.getOrgIdValue());
        vo.setNamedWhereClauseParam("BindEmpCode", JSFUtils.resolveExpression("#{pageFlowScope.P_EMP_CODE}"));
        vo.setNamedWhereClauseParam("BindEmpName", searchInputPgBind);
        vo.executeQuery();
        //System.out.println("Length in code=" + vo.getFilteredRows("CldId", "0000").length);
    }

    public void searchResultListener(ActionEvent actionEvent) {
        System.out.println("listener called");

        /* //HeadEmpDetailVO1Iterator
        DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding empBdgDtl = (DCIteratorBinding) dcBindings.get("EmpBudgetDetailForSearchVO4Iterator");

        //String empNm = (String) empBdgDtl.getCurrentRow().getAttribute("EmpNm");
        Integer empId = (Integer) empBdgDtl.getCurrentRow().getAttribute("EmpCode"); */

        System.out.println("Selected Employee=" + empIdPgBind.getValue());
        if (empIdPgBind.getValue() != null) {
            DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding headEmpItr = (DCIteratorBinding) dcBindings.get("HeadEmpDetailVO1Iterator");
            ViewObject headVo = headEmpItr.getRowSetIterator().getRowSet().getViewObject();
            headVo.setNamedWhereClauseParam("BindEmpName", null);
            headVo.setNamedWhereClauseParam("BindEmpCode", empIdPgBind.getValue());
            headVo.executeQuery();
        }
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

    public void approveBudgetAL(ActionEvent actionEvent) {
        showPopup(approveDocPopUpPgBind, true);
    }

    public void approveDocumentDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("Yes")) {
            System.out.println("Approve Budget Selected");
            OperationBinding ob = ADFUtils.findOperation("approveRejectBudget");
            ob.getParamsMap().put("statusType", 3);
            ob.execute();
        } else {
            approveDocPopUpPgBind.hide();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(immediateEmpDtlPgBind);
    }

    public void setApproveDocPopUpPgBind(RichPopup approveDocPopUpPgBind) {
        this.approveDocPopUpPgBind = approveDocPopUpPgBind;
    }

    public RichPopup getApproveDocPopUpPgBind() {
        return approveDocPopUpPgBind;
    }

    public void setImmediateEmpDtlPgBind(RichTable immediateEmpDtlPgBind) {
        this.immediateEmpDtlPgBind = immediateEmpDtlPgBind;
    }

    public RichTable getImmediateEmpDtlPgBind() {
        return immediateEmpDtlPgBind;
    }

    public void approveBdgAL(ActionEvent actionEvent) {
        System.out.println("Approve Budget Selected");
        OperationBinding ob = ADFUtils.findOperation("approveRejectBudget");
        ob.getParamsMap().put("statusType", 3);
        ob.execute();

        approveDocPopUpPgBind.hide();

        AdfFacesContext.getCurrentInstance().addPartialTarget(immediateEmpDtlPgBind);
    }

    public void rejectBdgAL(ActionEvent actionEvent) {
        System.out.println("Reject Budget Selected");
        OperationBinding ob = ADFUtils.findOperation("approveRejectBudget");
        ob.getParamsMap().put("statusType", 1);
        ob.execute();

        approveDocPopUpPgBind.hide();

        AdfFacesContext.getCurrentInstance().addPartialTarget(immediateEmpDtlPgBind);
    }

    public void empTargetAmountVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(empAuthorizePgBind);
    }

    public void setEmpAuthorizePgBind(RichLink empAuthorizePgBind) {
        this.empAuthorizePgBind = empAuthorizePgBind;
    }

    public RichLink getEmpAuthorizePgBind() {
        return empAuthorizePgBind;
    }

    public void setEmpNamePgBind(RichOutputText empNamePgBind) {
        this.empNamePgBind = empNamePgBind;
    }

    public RichOutputText getEmpNamePgBind() {
        return empNamePgBind;
    }

    public void setEmpIdPgBind(RichOutputText empIdPgBind) {
        this.empIdPgBind = empIdPgBind;
    }

    public RichOutputText getEmpIdPgBind() {
        return empIdPgBind;
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
