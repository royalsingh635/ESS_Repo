package adf.utils.workflow;

public interface WFApprove {
    /**
     * apply logic for work flow return according to your application logic
     * or call your custom after workflow logic or method in it.
     * @param wfReturn
     * @param txn
     */
    public void afterWorkFlowFrmLnk(String wfReturn,String txn);
    
    /**
     * Custom logic for get application module.
     * @return
     */
    public  WFApprove getAppAM();
    
    /**
     * Custom logic for release application module.
     * @param am
     */
    public void releaseAppAM(WFApprove am);
}
