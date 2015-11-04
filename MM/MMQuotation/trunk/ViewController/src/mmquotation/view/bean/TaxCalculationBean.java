package mmquotation.view.bean;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import mmquotation.model.service.QuotationAMImpl;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.fragment.RichRegion;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;
import oracle.adf.view.rich.event.RegionNavigationEvent;

import oracle.jbo.JboException;
import oracle.jbo.domain.Number;

public class TaxCalculationBean {
    private String taskFlowId = "/WEB-INF/AppTrCalcTF.xml#AppTrCalcTF";
    private String emptyTaskflowId = "";
    private String currentTaskFlowId = taskFlowId;
    private RichPopup taxPopUp;
    private RichRegion region;

    public TaxCalculationBean() {
    }

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }
    public void showPopUp(PopupFetchEvent popupFetchEvent) {
        currentTaskFlowId = taskFlowId;
    }
    public void taxPopUpCancelListener(PopupCanceledEvent popupCanceledEvent) {
         System.out.println("cancel Listener");
       
        currentTaskFlowId = emptyTaskflowId;
    }
    public void taxNavigationListener(RegionNavigationEvent regionNavigationEvent) {
        System.out.println("taxNavigation1");
        String newViewId = regionNavigationEvent.getNewViewId();
        
         
        if (newViewId == null) {
            currentTaskFlowId = emptyTaskflowId;
           taxPopUp.hide();
        }

    }
    private static int NUMBER = Types.NUMERIC;
    private static int VARCHAR = Types.VARCHAR;
    private Number P_TAX_AMT = null;
    protected Object callStoredProcedure(String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            // 1. Create a JDBC CallableStatement
            st = getAm().getDBTransaction().createCallableStatement("begin " + stmt + ";end;", 0);
            st.setObject( 1, bindVars[0]);
            st.setObject( 2, bindVars[1]);
            st.setObject( 3, bindVars[2]);
            st.setObject( 4, bindVars[3]);
            st.registerOutParameter(5, NUMBER);
            st.setObject( 6, bindVars[4]);
            st.setObject( 7, bindVars[5]);
            st.executeUpdate();
            return st.getObject(5);
        } catch (SQLException e) {
            int end = e.getMessage().indexOf("\n");
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {

                }
            }
        }
    }

    public QuotationAMImpl getAm() {
        return (QuotationAMImpl)resolvElDC("QuotationAMDataControl");
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

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }


    public void cancelPopup(PopupCanceledEvent pce) {
        System.out.println("cancel popup");
        currentTaskFlowId = emptyTaskflowId;
        FacesContext fc = FacesContext.getCurrentInstance();
        ExpressionFactory ef = null;
        ef = fc.getApplication().getExpressionFactory();
        ELContext ec = fc.getELContext();
        MethodExpression me = null;
        me = ef.createMethodExpression(ec, "#{centralBean.getExit}", String.class, new Class[] { });
        region.queueActionEventInRegion(me, null, null, false, 0, 0, PhaseId.INVOKE_APPLICATION);
    }

    public void setTaxPopUp(RichPopup taxPopUp) {
        this.taxPopUp = taxPopUp;
    }

    public RichPopup getTaxPopUp() {
        return taxPopUp;
    }

    public void setRegion(RichRegion region) {
        this.region = region;
    }

    public RichRegion getRegion() {
        return region;
    }
}
