package mmmatclsftn.view.bean;

import adf.utils.bean.ADFBeanUtils;

import javax.faces.event.ActionEvent;

public class MatSearchBean {
    public MatSearchBean() {
    }

    public void searchMethod(ActionEvent actionEvent) {
        // Add event code here...
        oracle.binding.OperationBinding ob1 = ADFBeanUtils.findOperation("searchAnalysis");
        ob1.execute(); 
    }

    public void resetMethod(ActionEvent actionEvent) {
        // Add event code here...
        oracle.binding.OperationBinding ob1 = ADFBeanUtils.findOperation("reset");
        ob1.execute(); 
    }

    public void anaIdSearch(ActionEvent actionEvent) {
        
        // Add event code here...
//        oracle.binding.OperationBinding ob1 = ADFBeanUtils.findOperation("anaIdFilter");
//        ob1.execute();
    }
}
