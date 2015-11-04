package finfdwithdrawlapp.view.beans;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;
import javax.faces.application.FacesMessage;
import oracle.binding.OperationBinding;
import javax.faces.event.ActionEvent;
public class FinFDWithdrawlAddEditBean {
    private String mode = "V";
    private String fetchMode = "V";
    private String wfId = null;
    
    public FinFDWithdrawlAddEditBean() {
    }

    /**
     * Method to add new Document
     * @param ace
     */
    public void createACTION(ActionEvent ace) {
        ADFBeanUtils.getOperationBinding("createNewDocument").execute();
        setModeToCreate();
    }

    public void setWfId(String wfId) {
        this.wfId = wfId;
    }

    public String getWfId() {
        return wfId;
    }

    public void setModeToCreate() {
        mode = "A";
        fetchMode = "A";
    }

    public void setFetchMode(String fetchMode) {
        this.fetchMode = fetchMode;
    }

    public String getFetchMode() {
        return fetchMode;
    }


    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    /**
     * Method to add new Document
     * @param actionEvent
     */
    public void addACTION(ActionEvent actionEvent) {
        // Add event code here...
    }

    /**
     * Method to edit new Document
     * @param actionEvent
     */
    public void editACTION(ActionEvent actionEvent) {
        mode = "E";
    }

    /**
     * Method to save Document
     * @param actionEvent
     */
    public void saveACTION(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("saveDocument");
        binding.execute();
        Object o = binding.getResult();
        Integer i = (o == null ? -1 : (Integer)o);
        if(i == 1){
            mode = "V";
        }
    }

    /**
     * Method to cancel document
     * @return
     */
    public String cancelACTION() {
        mode = "V";
        ADFBeanUtils.getOperationBinding("Rollback").execute();
        return "goBack";
    }

    /**
     * Method to save and forward a document
     * @return
     */
    public String saveAndForwardACTION() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("saveDocument");
        binding.execute();
        Object o = binding.getResult();
        Integer i = (o == null ? -1 : (Integer)o);
        Integer j = -1;
        if(i == 1){
            mode = "V";
            OperationBinding b = ADFBeanUtils.getOperationBinding("passWorkFlowEntries");
            b.execute();
            Object ob = b.getResult();
            j  = (ob == null ? -1 : (Integer)ob);
            if(i == 1){
                OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("getWorkFlowId");
                operationBinding.execute();
                Object object = operationBinding.getResult();
                wfId = (String)object;
            }
        }
        return (i == 1 ? "goToWf" : null);
        
    }

    /**
     * Method to fetchDatafromfd
     * @param actionEvent
     */
    public void fetchAndPopDataFrmFdACTION(javax.faces.event.ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("populateDtlsFrmFd");
        binding.execute();
        Object o = binding.getResult();
        Integer i = (o == null ? -1 : (Integer) o);
        if (i.equals(1)) {
            ADFModelUtils.showFacesMessage("Please Select a Value !", "Please select WithDrawl Type !",
                                           FacesMessage.SEVERITY_ERROR,
                                           ADFBeanUtils.getComponentClientIdFromId(actionEvent.getComponent(), "sor2"));
        }else if(i.equals(2)){
            ADFModelUtils.showFacesMessage("Please Select a Value !", "Please select Fixed Deposite Id !",
                                           FacesMessage.SEVERITY_ERROR,
                                           ADFBeanUtils.getComponentClientIdFromId(actionEvent.getComponent(), "ilov1"));
        }else if(i.equals(0)){
            fetchMode = "V";
        }

    }


}
