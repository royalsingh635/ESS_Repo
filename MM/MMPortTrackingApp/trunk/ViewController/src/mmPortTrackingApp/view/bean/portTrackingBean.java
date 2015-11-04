package mmPortTrackingApp.view.bean;

import adf.utils.model.ADFModelUtils;

import javax.faces.event.ActionEvent;

import mmPortTrackingApp.view.Utils.ADFUtils;
import mmPortTrackingApp.view.Utils.JSFUtils;

import oracle.binding.OperationBinding;

public class portTrackingBean {
    
    OperationBinding ob = null;
    private StringBuffer mode = new StringBuffer("V");

    public void setMode(StringBuffer mode) {
        this.mode = mode;
    }

    public StringBuffer getMode() {
        return mode;
    }

    public portTrackingBean() {
    }

    public void editPortTrackingACE(ActionEvent actionEvent) {
        setMode( new StringBuffer("E"));
    }

    public void savePortTrackingACE(ActionEvent actionEvent) {
        
        ob =  ADFUtils.findOperation("Commit");
        ob.execute();
//        Record has been Saved Successfully
        String s=ADFModelUtils.resolvRsrc("MSG.2427");
        JSFUtils.addFacesInformationMessage(s);
        setMode( new StringBuffer("V"));
    }

    public void cancelACE(ActionEvent actionEvent) {
        ob =  ADFUtils.findOperation("Rollback");
        ob.execute();
        setMode( new StringBuffer("V"));
    }
}
