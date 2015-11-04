package finfdwithdrawlapp.view.beans;

import adf.utils.bean.ADFBeanUtils;

import javax.faces.event.ActionEvent;

public class FinFDWithdrawlSearchBean {
    private String mode = "V";
    private String docTxnId = null;

    public void setDocTxnId(String docTxnId) {
        this.docTxnId = docTxnId;
    }

    public String getDocTxnId() {
        return docTxnId;
    }


    public FinFDWithdrawlSearchBean() {
    }
    
    /**
     * Method to perform Search
     * @param actionEvent
     */
    public void searchACTION(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("search").execute();
    }
    /**
     * Method to perform Reset
     * @param actionEvent
     */
    public void resetACTION(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("reset").execute();
    }
    /**
     * Method to create new document
     * @param actionEvent
     */
    public String createACTION() {
        mode = "A";
        return "goToAddEditPage";
    }
    
    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
    /**
     * Method to set doc txn id for viewPurpose
     * @param actionEvent
     */
    public void viewACTION(ActionEvent actionEvent) {
        Object object = actionEvent.getComponent().getAttributes().get("docId");
        if(object!= null){
            docTxnId = (String)object; 
        } 
        mode = "V";
    }
}
