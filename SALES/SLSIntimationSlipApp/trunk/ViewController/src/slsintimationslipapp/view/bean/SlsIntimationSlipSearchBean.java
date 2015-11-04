package slsintimationslipapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import javax.faces.event.ActionEvent;

public class SlsIntimationSlipSearchBean {
    private String mode = "V";
    private String docTxnId;

    public String getMode() {
        return mode;
    }

    public String getDocTxnId() {
        return docTxnId;
    }

    public SlsIntimationSlipSearchBean() {
    }
    /**
     * Method to search Intimation Slip
     * @param actionEvent
     */
    public void searchACTION(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("search").execute();
    }
    /**
     * Method to reset Intimation Slip
     * @param actionEvent
     */
    public void resetACTION(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("reset").execute();
    }


    public String addACTION() {
        //ADFBeanUtils.findOperation("createFlexiField").execute();
        mode = "A";
        return "go";
    }

    public void viewACTION(ActionEvent actionEvent) {
        Object object = actionEvent.getComponent().getAttributes().get("docTxnId");
        if(object != null){
            docTxnId = object.toString();
        }
        mode = "V";
    }
}
