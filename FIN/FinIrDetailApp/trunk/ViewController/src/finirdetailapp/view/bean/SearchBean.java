package finirdetailapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import javax.faces.event.ActionEvent;

public class SearchBean {
    public SearchBean() {
    }

    public void searchAction(ActionEvent actionEvent) {
        // Add event code here...
        ADFBeanUtils.getOperationBinding("filterSearchView").execute();
    }

    public void resetAction(ActionEvent actionEvent) {
        // Add event code here...
        ADFBeanUtils.getOperationBinding("resetSearchView").execute();
    }
}
