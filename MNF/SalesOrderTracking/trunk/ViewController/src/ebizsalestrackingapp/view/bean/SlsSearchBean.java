package ebizsalestrackingapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import java.util.Map;

import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;

import oracle.binding.OperationBinding;

public class SlsSearchBean {
    public SlsSearchBean() {
    }

    public void slsToTrackACE(ActionEvent actionEvent) {

        Map pageFlowScope = ADFContext.getCurrent().getPageFlowScope();
        pageFlowScope.put("SLS_DOC_ID", actionEvent.getComponent().getAttributes().get("slsDocId"));
        pageFlowScope.put("SLS_ITM_ID", actionEvent.getComponent().getAttributes().get("prdId"));
        pageFlowScope.put("SLS_DLV_DT", actionEvent.getComponent().getAttributes().get("dlvDt"));
        
        OperationBinding ob = ADFBeanUtils.findOperation("getCurrentOrderStage");   
        ob.getParamsMap().put("soDocId", actionEvent.getComponent().getAttributes().get("slsDocId"));
        ob.execute();
        if(ob.getResult()!=null) {
            Integer stage = (Integer) ob.getResult();
            pageFlowScope.put("SLS_STAGE", stage);
        }
   
        
       OperationBinding ob2 = ADFBeanUtils.findOperation("oppQuotTracking");   
       ob2.getParamsMap().put("soDocId", actionEvent.getComponent().getAttributes().get("slsDocId"));
       System.out.println("soDocId is ========================="+ actionEvent.getComponent().getAttributes().get("slsDocId"));
       ob2.execute();
       if(ob2.getResult()!=null) {   
           pageFlowScope.put("SLS_SO_SOURCE",  (Integer)ob2.getResult());
           System.out.println(" value is **********" + ob2.getResult());
       }
        
    }
}
