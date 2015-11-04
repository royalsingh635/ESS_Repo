package trnpprofileapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.output.RichOutputText;

public class AddEditBean {
    
    String mode="V";
    private RichOutputText bindFreezePrf;

    public AddEditBean() {
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
    
    public void addAL(ActionEvent actionEvent) {  
        
        ADFBeanUtils.getOperationBinding("CreateInsert").execute();
      //  ADFBeanUtils.getOperationBinding("executeCritriea").execute();
        
        this.setMode("A");
        
    }
    public void editAL(ActionEvent actionEvent) {

        this.setMode("A");
    }

    public void saveAL(ActionEvent actionEvent) {
     ADFBeanUtils.getOperationBinding("Commit").execute();
    this.setMode("V");
        
    }

    public void cancelAL(ActionEvent actionEvent) {  
        ADFBeanUtils.getOperationBinding("Rollback").execute();
        
        this.setMode("V");
    }

    public void setBindFreezePrf(RichOutputText bindFreezePrf) {
        this.bindFreezePrf = bindFreezePrf;
    }

    public RichOutputText getBindFreezePrf() {
        return bindFreezePrf;
    }
}
