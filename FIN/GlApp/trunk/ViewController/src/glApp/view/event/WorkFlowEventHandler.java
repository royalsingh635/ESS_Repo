package glApp.view.event;

import glApp.view.bean.GlAddBean;

import java.util.HashMap;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.binding.AttributeContext;
import oracle.binding.DataControl;
import oracle.binding.ManagedDataControl;
import oracle.binding.OperationBinding;
import oracle.binding.RowContext;
import oracle.binding.TransactionalDataControl;
import oracle.binding.UpdateableDataControl;

public class WorkFlowEventHandler implements TransactionalDataControl, UpdateableDataControl, ManagedDataControl {
    public WorkFlowEventHandler() {
        super();
    }

    public void getCommentValue(String customPayLoad) {


        System.out.println("Comment ..."+customPayLoad);
        FacesContext fctx = FacesContext.getCurrentInstance();
        ELContext elctx = fctx.getELContext();
        ExpressionFactory exprFactory = fctx.getApplication().getExpressionFactory();

        ValueExpression ve = exprFactory.createValueExpression(elctx, "#{GlAddBean}", Object.class);
        GlAddBean pageBean = (GlAddBean)ve.getValue(elctx);
        pageBean.setWfComment(customPayLoad.toString());


    }


    public void getEventValue(Object payload) {

        ValueChangeEvent valueChangeEvent = (ValueChangeEvent)payload;
        UIComponent component = (UIComponent)valueChangeEvent.getSource();
        String action = (String)component.getAttributes().get("action");
        Integer userId = (Integer)component.getAttributes().get("userid");
        Integer nextLvl = (Integer)component.getAttributes().get("userlvl");
        Integer wfId = (Integer)component.getAttributes().get("wfId");
        String wfFlag = component.getAttributes().get("wfFlag").toString();
        FacesContext fctx = FacesContext.getCurrentInstance();
        ELContext elctx = fctx.getELContext();
        ExpressionFactory exprFactory = fctx.getApplication().getExpressionFactory();

        ValueExpression ve = exprFactory.createValueExpression(elctx, "#{GlAddBean}", Object.class);
        GlAddBean pageBean = (GlAddBean)ve.getValue(elctx);
        System.out.println(action + userId + nextLvl);
        pageBean.setWfMode(action);
        pageBean.setWfNextUser(userId);
        pageBean.setWfNextLvl(nextLvl);
        pageBean.setWfId(wfId);
        pageBean.setWfFlag(wfFlag);

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
