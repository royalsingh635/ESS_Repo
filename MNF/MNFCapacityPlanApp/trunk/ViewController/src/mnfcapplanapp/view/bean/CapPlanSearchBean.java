package mnfcapplanapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.context.RequestContext;

public class CapPlanSearchBean {

    public CapPlanSearchBean() {
    }

    /*----------------------Method for get Binding------------------*/
    OperationBinding ob = null;

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /** Action Listner For Search In Planning for Home Page...
     * */
    public void capPlanSearchAL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("searchCapPlan_Func").execute();
    }

    /** Action Listner For Reset In Planning for Home Page...
     * */
    public void capPlanResetAL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("resetCapPlan_Func").execute();
    }

    /** Action Event for filter search and get f: attribute value
     * */
    public void planViewModeACL(ActionEvent actionEvent) {
        UIComponent componentVal = actionEvent.getComponent();
        oracle.adf.view.rich.util.ResetUtils.reset(componentVal);
        Object objectVal = componentVal.getAttributes().get("PlanDocId");
        String stringVal = objectVal.toString();
        setGLBL_CAP_PLN_TXN_ID(stringVal);
        ob = ADFBeanUtils.findOperation("filterMnfCapPln_Func");
        ob.getParamsMap().put("CapPlnDocId", stringVal);
        ob.execute();
    }

    /**Function for set Capacity Plan Doc Txn Id in Pageflowscope in view Mode
     * */
    public void setGLBL_CAP_PLN_TXN_ID(String txnId) {
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("GLBL_CAP_PLN_TXN_ID", txnId);
    }
}
