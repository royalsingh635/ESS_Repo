package accountsrecievable.view.beans;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;


import oracle.adf.view.faces.bi.component.graph.UIGraph;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class accountsRecievable {
    private UIGraph arGraphPgBind;
    private RichTable arTablePgBind;

    public accountsRecievable() {
    }

    public String viewDetailAction() {
        OperationBinding op = getBindings().getOperationBinding("curCoaId");
        op.execute();
        Integer coaId = (Integer)op.getResult();
        System.out.println(coaId);

        String glbl_org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String glbl_cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer glbl_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));

        OperationBinding op1 = getBindings().getOperationBinding("getItemForCoaWiseDetail");
        op1.getParamsMap().put("cldId", glbl_cld_id);
        op1.getParamsMap().put("slocId", glbl_sloc_id);
        op1.getParamsMap().put("orgId", glbl_org_id);
        op1.getParamsMap().put("coaId", coaId);
        op1.execute();
        return "detail";
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

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void DaysTypeVCL(ValueChangeEvent vce) {
        if (vce != null) {
            if (vce.getNewValue() != null) {
                OperationBinding op = getBindings().getOperationBinding("getFilteredRowsOnVCL");
                op.getParamsMap().put("type", vce.getNewValue().toString());
                op.execute();
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(arGraphPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(arTablePgBind);
        }
    }

    public void setArGraphPgBind(UIGraph arGraphPgBind) {
        this.arGraphPgBind = arGraphPgBind;
    }

    public UIGraph getArGraphPgBind() {
        return arGraphPgBind;
    }

    public void setArTablePgBind(RichTable arTablePgBind) {
        this.arTablePgBind = arTablePgBind;
    }

    public RichTable getArTablePgBind() {
        return arTablePgBind;
    }
}
