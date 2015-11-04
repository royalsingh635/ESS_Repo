package mmPortTrackingApp.view.bean;

import javax.faces.event.ActionEvent;

import mmPortTrackingApp.view.Utils.ADFUtils;

import oracle.binding.OperationBinding;

public class portTrackingSearchBean {
    
    OperationBinding ob = null;
    
    public portTrackingSearchBean() {
    }

    public void searchACE(ActionEvent actionEvent) {
        ob =  ADFUtils.findOperation("searchPortTracking");
        ob.execute();
    }

    public void resetACE(ActionEvent actionEvent) {
        ob =  ADFUtils.findOperation("resetPortTracking");
        ob.execute();
    }
}
