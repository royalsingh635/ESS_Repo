package MMMtlMis.view.bean;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.jbo.ViewObject;

public class HierarchyBean {
    private String var;
    String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
    String slocIdStr = resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}");
    Integer slocId = Integer.parseInt(slocIdStr);
    String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
    String hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
    private RichOutputText bindItmDesc;
    private RichOutputText bindItmId;

    public void setVar(String var) {
        this.var = var;
    }

    public String getVar() {
        return var;
    }

    public HierarchyBean() {
    }

    public void hierarchySearchAction(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("Value passed to search =" + var);

        DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding empBdgDtl = (DCIteratorBinding) dcBindings.get("HierarchyItmSearch1Iterator");
        ViewObject vo = empBdgDtl.getRowSetIterator().getRowSet().getViewObject();
        vo.setNamedWhereClauseParam("BindCldId", cldId);
        vo.setNamedWhereClauseParam("BindSlocId", slocId);
        vo.setNamedWhereClauseParam("BindHoOrgId", hoOrgId);
        vo.setNamedWhereClauseParam("BindOrgId", orgId);
        //vo.setNamedWhereClauseParam("BindEmpCode", JsfUtils.resolveExpression("#{pageFlowScope.P_EMP_CODE}"));
        vo.setNamedWhereClauseParam("BindItmId", var);
        vo.executeQuery();
        //System.out.println("Length in code=" + vo.getFilteredRows("CldId", "0000").length);


    }

    public void hierarchyresultListener(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("listener called");

        /* //HeadEmpDetailVO1Iterator
            DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding empBdgDtl = (DCIteratorBinding) dcBindings.get("EmpBudgetDetailForSearchVO4Iterator");

            //String empNm = (String) empBdgDtl.getCurrentRow().getAttribute("EmpNm");
            Integer empId = (Integer) empBdgDtl.getCurrentRow().getAttribute("EmpCode"); */

        System.out.println("Selected Employee=" + bindItmId.getValue());
        if (bindItmId.getValue() != null) {
            DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding headEmpItr = (DCIteratorBinding) dcBindings.get("HierarchyItmDetails1Iterator");
            ViewObject vo = headEmpItr.getRowSetIterator().getRowSet().getViewObject();
            vo.setNamedWhereClauseParam("BindCldId", cldId);
            vo.setNamedWhereClauseParam("BindSlocId", slocId);
            vo.setNamedWhereClauseParam("BindHoOrgId", hoOrgId);
            vo.setNamedWhereClauseParam("BindOrgId", orgId);
            //vo.setNamedWhereClauseParam("BindEmpCode", JsfUtils.resolveExpression("#{pageFlowScope.P_EMP_CODE}"));
            vo.setNamedWhereClauseParam("BindItmId", bindItmId.getValue());
            vo.executeQuery();
        }

    }

    public void setBindItmDesc(RichOutputText bindItmDesc) {
        this.bindItmDesc = bindItmDesc;
    }

    public RichOutputText getBindItmDesc() {
        return bindItmDesc;
    }

    public void setBindItmId(RichOutputText bindItmId) {
        this.bindItmId = bindItmId;
    }

    public RichOutputText getBindItmId() {
        return bindItmId;
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

}
