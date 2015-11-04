package finBudget.view.beans;

import finBudget.model.services.FinBudgetAMImpl;

import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;


public class FinBudgetSearchBean {
    private RichSelectOneChoice orgId;
    private RichInputText coaId;
    private RichSelectOneChoice fyId;
    private RichPanelFormLayout searchForm;
    private RichSelectOneChoice mon_id;
    Integer count =(Integer)getBindings().getOperationBinding("on_load_page").execute();
    public FinBudgetSearchBean() {
    }

    public String searchAction() {

        /** Get FinBudgetSearch view object */

        ViewObject v = getAm().getFinBudgetSearchDtl();

        /** Get Global Parameters from task flow */

        String hoOrgId = "01";
        String cldId = "0000";
        Integer slocId = 1;
        String org_Id = "01";

        hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");


        System.out.println("Org_id " + org_Id + " ho_org_id " + hoOrgId + " slocId " + slocId + " cldId " + cldId +
                           " coaId " + coaId.getValue() + " fyId " + fyId.getValue());


        /**Set values of bind variables. these values are from bind  */

        if (orgId.getValue() == null || orgId.getValue() == ""){
            v.setNamedWhereClauseParam("org_id", org_Id);
        }
        else{
            v.setNamedWhereClauseParam("org_id", orgId.getValue());
        }
        v.setNamedWhereClauseParam("sloc_id", slocId);
        v.setNamedWhereClauseParam("ho_org_id", hoOrgId);
        v.setNamedWhereClauseParam("cld_id", cldId);
        if (coaId.getValue() != null)
            if (Integer.parseInt(coaId.getValue().toString()) != 0){
                v.setNamedWhereClauseParam("coa_id", coaId.getValue());
            }
        v.setNamedWhereClauseParam("cog_id", null);
        v.setNamedWhereClauseParam("fy_id", fyId.getValue());
        v.setNamedWhereClauseParam("mon_id", mon_id.getValue());

        /** Execute view object */
        v.executeQuery();

       // OperationBinding createOpBAddress = getBindings().getOperationBinding("ExecuteWithParams");
       // createOpBAddress.execute();

        return null;
    }


    public FinBudgetAMImpl getAm() {

        FinBudgetAMImpl am = (FinBudgetAMImpl)resolvElDC("FinBudgetAMDataControl");
        return am;

    }

    private void resetValueInputItems(AdfFacesContext adfFacesContext, UIComponent component) {
        /** Get list of all components from search panel */
        List<UIComponent> items = component.getChildren();
        for (UIComponent item : items) {

            resetValueInputItems(adfFacesContext, item);
            /** Check if Input text , reset its value*/
            if (item instanceof RichInputText) {
                RichInputText input = (RichInputText)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }

                /** Check if RichInputDate , reset its value*/
            } else if (item instanceof RichInputDate) {
                RichInputDate input = (RichInputDate)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
                /** Check if SelectOneChoice , reset its value*/
            } else if (item instanceof RichSelectOneChoice) {
                RichSelectOneChoice input = (RichSelectOneChoice)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
            }

        }
    }

    public void CoaNmValueChangeListener(ValueChangeEvent valueChangeEvent) {

     /*   ViewObject lovCoaRow = getAm().getLovCoaVO();
        Row[] filterdRw = lovCoaRow.getFilteredRows("CoaNm", valueChangeEvent.getNewValue());
        if (filterdRw.length > 0) {
            coaId.setValue(Integer.parseInt(filterdRw[0].getAttribute("CoaId").toString()));
        }
*/
    }


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
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

    public void setOrgId(RichSelectOneChoice orgId) {
        this.orgId = orgId;
    }

    public RichSelectOneChoice getOrgId() {

        return orgId;
    }

    public void setCoaId(RichInputText coaId) {
        this.coaId = coaId;
    }

    public RichInputText getCoaId() {
        return coaId;
    }

    public void setFyId(RichSelectOneChoice fyId) {
        this.fyId = fyId;
    }

    public RichSelectOneChoice getFyId() {
        return fyId;
    }

    public void setSearchForm(RichPanelFormLayout searchForm) {
        this.searchForm = searchForm;
    }

    public RichPanelFormLayout getSearchForm() {
        return searchForm;
    }


    public String resetActionCall() {
        System.out.println("------------------In the reset action-----------");


        ViewObject v = getAm().getFinBudgetSearchDtl();
        ViewObject view = getAm().getFinBudgetSearch();

        v.setNamedWhereClauseParam("sloc_id", null);
        v.setNamedWhereClauseParam("ho_org_id", null);
        //v.setNamedWhereClauseParam("org_id", null);
        v.setNamedWhereClauseParam("cld_id", null);
        v.setNamedWhereClauseParam("coa_id", null);
        v.setNamedWhereClauseParam("cog_id", null);
        v.setNamedWhereClauseParam("fy_id", null);
        v.setNamedWhereClauseParam("mon_id", null);
        
        v.executeQuery();

        /** Execute view object*/

        view.executeQuery();

        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        coaId.setValue(" ");
        AdfFacesContext.getCurrentInstance().addPartialTarget(coaId);
        resetValueInputItems(fctx, searchForm);
        OperationBinding createOp = getBindings().getOperationBinding("ExecuteWithParams");
        createOp.execute();
        OperationBinding createOpBAddress = getBindings().getOperationBinding("Execute");
        createOpBAddress.execute();

        return "resetAction";
    }

    public void setMon_id(RichSelectOneChoice mon_id) {
        this.mon_id = mon_id;
    }

    public RichSelectOneChoice getMon_id() {
        return mon_id;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }
}
