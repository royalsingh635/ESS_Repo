package slsordrsplittingapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import javax.faces.event.ActionEvent;

public class SLSOrdrSplittingSearchBean {
    private String docId;

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocId() {
        return docId;
    }

    public SLSOrdrSplittingSearchBean() {
    }

    public void searchACTION(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("search").execute();
    }

    public void resetACTION(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("reset").execute();
    }

    public void goToSoAE(ActionEvent actionEvent) {
        Object docId = actionEvent.getComponent().getAttributes().get("docId");
        if(docId != null){
            this.docId = docId.toString();
        }
        
    }

    public String goToViewPageACTION() {
        return docId == null ? null : "go";
    }
}
