package misreportapp.view.bean;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import javax.faces.event.ValueChangeEvent;

import misreportapp.model.module.MISReportAMImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.view.faces.bi.event.ClickEvent;

import oracle.binding.OperationBinding;

import oracle.dss.dataView.ComponentHandle;
import oracle.dss.dataView.DataComponentHandle;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;

public class MisBean {

    MISReportAMImpl am;

    public MisBean() {
    }

    public MISReportAMImpl getAm() {

        am = (MISReportAMImpl)resolvElDC("MISReportAMDataControl");
        return am;
    }

    public void clickListCurrRatio(ClickEvent clickEvent) {

        ComponentHandle handle = clickEvent.getComponentHandle();
        if (handle instanceof DataComponentHandle) {
            DataComponentHandle dhandle = (DataComponentHandle)handle;

            Key key = (Key)dhandle.getValue(DataComponentHandle.ROW_KEY);
            MISReportAMImpl am = (MISReportAMImpl)resolvElDC("MISReportAMDataControl");
            ViewObject v = am.getCurrRatio1();
            Row row = v.getRow(key);
            //  Integer sloc_id = Integer.parseInt(row.getAttribute("GlSlocId").toString());
            String org_id = row.getAttribute("GlOrgId").toString();
            String cld_id = row.getAttribute("GlCldId").toString();
            Integer cat_id = Integer.parseInt(row.getAttribute("CatId").toString());

            ViewObjectImpl e = am.getCoaCatWise1();
            e.setWhereClause(" org_id = '" + org_id + "' and cld_id = '" + cld_id + "' and cat_id = " + cat_id);
            e.executeQuery();

            go();
        }

    }

    public String go() {
        return "coa";
    }

    public static Object resolveElExp(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object msg = valueExp.getValue(elContext);
        return msg;
    }

    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    public void clickOnGraphEquity(ClickEvent clickEvent) {
        System.out.println("----graph");
        ComponentHandle handle = clickEvent.getComponentHandle();
        if (handle instanceof DataComponentHandle) {
            DataComponentHandle dhandle = (DataComponentHandle)handle;
            System.out.println("Data value: " + dhandle.getValue(DataComponentHandle.UNFORMATTED_VALUE).toString());
            System.out.println(dhandle.getValue(DataComponentHandle.ROW_KEY) + "------row key");
            Key key = (Key)dhandle.getValue(DataComponentHandle.ROW_KEY);
            MISReportAMImpl am = (MISReportAMImpl)resolvElDC("MISReportAMDataControl");
            ViewObject v = am.getDebtEquity1();
            Row row = v.getRow(key);
            //  Integer sloc_id = Integer.parseInt(row.getAttribute("GlSlocId").toString());
            String org_id = row.getAttribute("GlOrgId").toString();
            String cld_id = row.getAttribute("GlCldId").toString();
            Integer cat_id = Integer.parseInt(row.getAttribute("CatId").toString());

            ViewObjectImpl e = am.getCoaCatWise1();
            e.setWhereClause(" org_id = '" + org_id + "' and cld_id = '" + cld_id + "' and cat_id = " + cat_id);
            e.executeQuery();

            go();
        }
    }

    public String detailButton() {
        MISReportAMImpl am = (MISReportAMImpl)resolvElDC("MISReportAMDataControl");
        ViewObjectImpl v = am.getCatCoaBalNew1();
        Row row = v.getCurrentRow();
        System.out.println(row.getAttribute("CoaNm").toString() + "----------detail coa name");

        return null;
    }

    public String currentAssetLink() {
        //  Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer cat_id = 187;
        MISReportAMImpl am = (MISReportAMImpl)resolvElDC("MISReportAMDataControl");
        ViewObjectImpl e = am.getCoaCatWise1();
        e.setWhereClause(" org_id = '" + org_id + "' and cld_id = '" + cld_id + "' and cat_id = " + cat_id);
        e.executeQuery();
        return "coa";
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

    public String currentLiabilityLink() {
        //   Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer cat_id = 188;
        MISReportAMImpl am = (MISReportAMImpl)resolvElDC("MISReportAMDataControl");
        ViewObjectImpl e = am.getCoaCatWise1();
        e.setWhereClause(" org_id = '" + org_id + "' and cld_id = '" + cld_id + "' and cat_id = " + cat_id);
        e.executeQuery();
        return "coa";
    }

    public String stockInHandLInk() {
        //  Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer cat_id = 189;
        MISReportAMImpl am = (MISReportAMImpl)resolvElDC("MISReportAMDataControl");
        ViewObjectImpl e = am.getCoaCatWise1();
        e.setWhereClause(" org_id = '" + org_id + "' and cld_id = '" + cld_id + "' and cat_id = " + cat_id);
        e.executeQuery();
        return "coa";
    }

    public String salesLink() {
        //    Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer cat_id = 188;
        MISReportAMImpl am = (MISReportAMImpl)resolvElDC("MISReportAMDataControl");
        ViewObjectImpl e = am.getCoaCatWise1();
        e.setWhereClause(" org_id = '" + org_id + "' and cld_id = '" + cld_id + "' and cat_id = " + cat_id);
        e.executeQuery();
        return "coa";
    }

    public String capitalAccountLink() {
        // Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer cat_id = 199;
        MISReportAMImpl am = (MISReportAMImpl)resolvElDC("MISReportAMDataControl");
        ViewObjectImpl e = am.getCoaCatWise1();
        e.setWhereClause(" org_id = '" + org_id + "' and cld_id = '" + cld_id + "' and cat_id = " + cat_id);
        e.executeQuery();
        return "coa";
    }

    public String debtLink() {
        //  Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer cat_id = 197;
        MISReportAMImpl am = (MISReportAMImpl)resolvElDC("MISReportAMDataControl");
        ViewObjectImpl e = am.getCoaCatWise1();
        e.setWhereClause(" org_id = '" + org_id + "' and cld_id = '" + cld_id + "' and cat_id = " + cat_id);
        e.executeQuery();
        return "coa";
    }

    public String equityLink() {
        //  Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer cat_id = 198;
        MISReportAMImpl am = (MISReportAMImpl)resolvElDC("MISReportAMDataControl");
        ViewObjectImpl e = am.getCoaCatWise1();
        e.setWhereClause(" org_id = '" + org_id + "' and cld_id = '" + cld_id + "' and cat_id = " + cat_id);
        e.executeQuery();
        return "coa";
    }

    public void FyChangeListener(ValueChangeEvent valueChangeEvent) {
        Integer selectedfyId = null;
        System.out.println("inside FyChangeListener");
        if (valueChangeEvent != null) {
           
            selectedfyId = Integer.parseInt(valueChangeEvent.getNewValue().toString());
            System.out.println("selectedfyId" + selectedfyId);
            
            OperationBinding opCallFunc =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setBindVar");
            opCallFunc.getParamsMap().put("fyId", selectedfyId);
            opCallFunc.execute();
        }
    }

    public BindingContext getBindings() {

        return BindingContext.getCurrent();
    }
}

