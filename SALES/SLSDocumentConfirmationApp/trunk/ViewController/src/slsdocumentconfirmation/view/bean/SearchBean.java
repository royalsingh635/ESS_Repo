package slsdocumentconfirmation.view.bean;

import adf.utils.bean.ADFBeanUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class SearchBean {
    private String mode="A";

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }


    public SearchBean() {
    }

   

    /*   public void searchNameAction(ActionEvent actionEvent) {
       // BindingContainer bc = BindingContext.getCurrent().getCurrentBindingsEntry();
      //  OperationBinding op = bc.getOperationBinding("searchInTable");
     //        op.getParamsMap().put("val2", usrValue2Binding.getValue());
     //        op.getParamsMap().put("val1", usrValueBinding.getValue());
       // op.execute();
    } */


    public void resetAction(ActionEvent actionEvent) {
        
       ADFBeanUtils.getOperationBinding("reset").execute();
       
    }

    public void searchAction(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("search").execute();
       
    }
    
    /**
     *This method use for Call CreateDocConfTF
     * @return
     */
    public String addAction() {
       this.mode="A";
        return "goToCreatePage";
    }
    
    /**
     *This method use for Call CreateDocConfTF
     * @return
     */
    public String viewAction() {
        this.mode="V";
       
        return "goToCreatePage";
    }


    public void fielterDocConfACE(ActionEvent actionEvent) {
        OperationBinding o = ADFBeanUtils.getOperationBinding("viewSelectedDocumentId");
        o.getParamsMap().put("docId", actionEvent.getComponent().getAttributes().get("docId"));
        o.execute();
    }
}
