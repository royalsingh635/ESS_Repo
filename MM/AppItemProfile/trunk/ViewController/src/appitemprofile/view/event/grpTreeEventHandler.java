package appitemprofile.view.event;


import appitemprofile.view.bean.ItemProfile;

import appitemprofile.view.bean.ItemSearchBean;

import java.util.HashMap;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.binding.DCBindingContainerCurrencyChangeEvent;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.AttributeContext;
import oracle.binding.ManagedDataControl;
import oracle.binding.OperationBinding;
import oracle.binding.RowContext;
import oracle.binding.TransactionalDataControl;
import oracle.binding.UpdateableDataControl;

import org.apache.myfaces.trinidad.event.SelectionEvent;


public class grpTreeEventHandler implements TransactionalDataControl, UpdateableDataControl, ManagedDataControl {
    public grpTreeEventHandler() {
        super();
    }


    public void handleEventStringPayload(Object customPayLoad) {

        SelectionEvent event = (SelectionEvent)customPayLoad;
        UIComponent component = (UIComponent)event.getSource();
        String grpid = (String)component.getAttributes().get("grpId");
        FacesContext fctx = FacesContext.getCurrentInstance();
        ELContext elctx = fctx.getELContext();
        ExpressionFactory exprFactory = fctx.getApplication().getExpressionFactory();
        System.out.println("......payload" + grpid);
        ValueExpression ve = exprFactory.createValueExpression(elctx, "#{ItemPrfBean}", Object.class);
        ItemProfile pageBean = (ItemProfile)ve.getValue(elctx);
        pageBean.setGrpvalue(grpid);
    }
    public void grpLovForSearch(Object customPayLoad) {

        SelectionEvent event = (SelectionEvent)customPayLoad;
        UIComponent component = (UIComponent)event.getSource();
        String grpid = (String)component.getAttributes().get("grpId");
        FacesContext fctx = FacesContext.getCurrentInstance();
        ELContext elctx = fctx.getELContext();
        ExpressionFactory exprFactory = fctx.getApplication().getExpressionFactory();
        System.out.println("......payload" + grpid);
        ValueExpression ve = exprFactory.createValueExpression(elctx, "#{ItemSearchBean}", Object.class);
        ItemSearchBean pageBean = (ItemSearchBean)ve.getValue(elctx);
        pageBean.setGroupName(grpid);
    }

    public String getName() {
        return null;
    }

    public void release() {
    }

    public Object getDataProvider() {
        return null;
    }

    public boolean invokeOperation(Map p0, OperationBinding p1) {
        return false;
    }

    public boolean isTransactionDirty() {
        return false;
    }

    public void rollbackTransaction() {
    }

    public void commitTransaction() {
    }

    public boolean setAttributeValue(AttributeContext p0, Object p1) {
        return false;
    }

    public Object createRowData(RowContext p0) {
        return null;
    }

    public Object registerDataProvider(RowContext p0) {
        return null;
    }

    public boolean removeRowData(RowContext p0) {
        return false;
    }

    public void validate() {
    }

    public void beginRequest(HashMap p0) {
    }

    public void endRequest(HashMap p0) {
    }

    public boolean resetState() {
        return false;
    }
}
