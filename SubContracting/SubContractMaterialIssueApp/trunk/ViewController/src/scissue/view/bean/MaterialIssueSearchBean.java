package scissue.view.bean;

import adf.utils.bean.ADFBeanUtils;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.context.RequestContext;


public class MaterialIssueSearchBean {
    public MaterialIssueSearchBean() {
    }


    /**
     * Getting Material Issue Doc Id from F:Attribute
     * */
    public void MaterialIssueViewModeACL(ActionEvent actionEvent) {
        UIComponent componentVal = actionEvent.getComponent();
        oracle.adf.view.rich.util.ResetUtils.reset(componentVal);
        Object objectVal = componentVal.getAttributes().get("IssueDocId");
        String scoDocId = objectVal.toString();
        set_GLBL_ISSUE_TXN_ID(scoDocId);
        OperationBinding binding = ADFBeanUtils.findOperation("filterMmScIsssue_Func");
        binding.getParamsMap().put("ScIssueDocId", scoDocId);
        binding.execute();
    }

    /**
     * Function for set Material Issue Doc Txn Id in Pageflowscope in view Mode
     * */
    public void set_GLBL_ISSUE_TXN_ID(String txnId) {
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("GLBL_ISSUE_TXN_ID", txnId);
    }
}
