package mnfproductioncosting.view.beans;

import adf.utils.bean.ADFBeanUtils;

import javax.faces.event.ActionEvent;

public class MNFProductionCostingSearchBean {
    
    public MNFProductionCostingSearchBean() {
    }

    public void searchDocumentIdAL(ActionEvent actionEvent) {
        System.out.println("in search document ID action listner");
        ADFBeanUtils.findOperation("searchDocumentId").execute();
    }

    public void resetDocumentIdAL(ActionEvent actionEvent) {
        // Add event code here...
        
        System.out.println("in reset document ID action listner");
        ADFBeanUtils.findOperation("resetDocumentId").execute();
    }

}
