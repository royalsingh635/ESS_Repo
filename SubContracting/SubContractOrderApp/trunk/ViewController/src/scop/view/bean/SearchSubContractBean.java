package scop.view.bean;

import adf.utils.bean.ADFBeanUtils;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import org.apache.myfaces.trinidad.context.RequestContext;

public class SearchSubContractBean {
    public SearchSubContractBean() {
    }

    /*----------------------------Search Form---------------------------*/
    public void searchACL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("searchSco_Func").execute();
    }

    /*-----------------------------Reset Form--------------------------*/
    public void resetACL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("resetSco_Func").execute();
    }

    /*------------------------Subcontracting order in view mode ACL-------------------------*/
    public void ScoViewModeACL(ActionEvent actionEvent) {
        UIComponent componentVal = actionEvent.getComponent();
        oracle.adf.view.rich.util.ResetUtils.reset(componentVal);
        Object objectVal = componentVal.getAttributes().get("ScoId");
        String scoDocId = objectVal.toString();
        setGLBL_SCO_TXN_ID(scoDocId);
        ADFBeanUtils.findOperation("filterMmSco_Func").execute();
    }

    /**Function for set Capacity Plan Doc Txn Id in Pageflowscope in view Mode
     * */
    public void setGLBL_SCO_TXN_ID(String txnId) {
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("GLBL_SCO_TXN_ID", txnId);
    }
}
