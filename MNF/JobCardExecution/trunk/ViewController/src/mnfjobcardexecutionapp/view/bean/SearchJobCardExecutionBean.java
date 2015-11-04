package mnfjobcardexecutionapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

import oracle.jbo.domain.Number;
import oracle.binding.OperationBinding;

public class SearchJobCardExecutionBean {
    OperationBinding ob = null;
    public SearchJobCardExecutionBean() {
    }

    public void searchActionListener(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("searchActionListener").execute();
    }

    public void resetActionListener(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("resetActionListener").execute();
    }

    public String createAction() {
        ob = ADFBeanUtils.findOperation("checkYearFyId");
                ob.execute();
                Number chkUsr = (Number)ob.getResult();
                if (!(chkUsr.compareTo(-1)==0)) {
                    return "create";
                }else{
                    StringBuilder message = new StringBuilder();
                    message.append("Financial Year is not Defined !");
                    ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(), FacesMessage.SEVERITY_INFO);
                }
                return null;
    }
}
