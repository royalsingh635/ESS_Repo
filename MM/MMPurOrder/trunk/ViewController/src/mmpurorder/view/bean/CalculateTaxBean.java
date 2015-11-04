package mmpurorder.view.bean;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import oracle.jbo.domain.Number;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.MethodExpression;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import javax.faces.event.PhaseId;

import mmpurorder.model.service.PurOrderAMImpl;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.fragment.RichRegion;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.adf.view.rich.event.RegionNavigationEvent;

import oracle.jbo.JboException;

import org.apache.myfaces.trinidad.context.RequestContext;
/** This class comprises all the methods for PO tax related functionalities on the Add/Edit Pages.
 * Is deprecated
 *@author SM 
 * Dated-10 Dec 2012
 * 
 * * */
public class CalculateTaxBean {

    private String taskFlowId = "/WEB-INF/AppTrCalcTF.xml#AppTrCalcTF";
    private String emptyTaskflowId = "";
    private String currentTaskFlowId = taskFlowId;
    private RichPopup taxPopUp;
    private RichRegion region;

    public CalculateTaxBean() {

    }

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

  /*   public void taxPopUpCancelListener(PopupCanceledEvent popupCanceledEvent) {
         System.out.println("cancel Listener");       
        currentTaskFlowId = emptyTaskflowId;
    } */
    
    /* Not in use
     * */
    public void doneButtonForward(){            
    }
    
    public void showPopUp(PopupFetchEvent popupFetchEvent) {
        currentTaskFlowId = taskFlowId;
    }

  /*   public void taxNavigationListener(RegionNavigationEvent regionNavigationEvent) {
        System.out.println("taxNavigation1");
        String newViewId = regionNavigationEvent.getNewViewId();
        
        if (newViewId == null) {
            currentTaskFlowId = emptyTaskflowId;
           taxPopUp.hide();
        }

    } */

    public PurOrderAMImpl getAm() {
        return (PurOrderAMImpl)resolvElDC("PurOrderAMDataControl");
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
       /*  System.out.println("cancel popup");
        currentTaskFlowId = emptyTaskflowId;
        FacesContext fc = FacesContext.getCurrentInstance();
        ExpressionFactory ef = null;
        ef = fc.getApplication().getExpressionFactory();
        ELContext ec = fc.getELContext();
        MethodExpression me = null;
        me = ef.createMethodExpression(ec, "#{centralBean.getExit}", String.class, new Class[] { });
        region.queueActionEventInRegion(me, null, null, false, 0, 0, PhaseId.INVOKE_APPLICATION); */
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

    public String importItmFrmExcel() {
        // Add event code here...
        return null;
    }
}
