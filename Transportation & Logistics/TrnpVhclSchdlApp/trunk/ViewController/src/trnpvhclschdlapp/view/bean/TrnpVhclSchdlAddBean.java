package trnpvhclschdlapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import javax.faces.application.FacesMessage;

import oracle.binding.OperationBinding;

import trnpvhclschdlapp.model.services.TrnpVhclSchdlAMImpl;

public class TrnpVhclSchdlAddBean {
    String mode = "V";


    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public TrnpVhclSchdlAddBean() {
    }


    public String editScheduleACTION() {
        mode = "A";
        return null;
    }

    public String addRecordsAction() {
        OperationBinding createOp = ADFBeanUtils.getOperationBinding("CreateInsert");
        createOp.execute();
        mode = "A";
        return null;
    }

    public String saveScheduleACTION() {
        OperationBinding createOp = ADFBeanUtils.getOperationBinding("Commit");
        createOp.execute();
        mode = "V";
        ADFModelUtils.showFormattedFacesMessage(" ",ADFModelUtils.resolvRsrc("MSG.91"), FacesMessage.SEVERITY_INFO);
        return null;
    }

    public String cancelScheduleACTION() {
        OperationBinding createOp = ADFBeanUtils.getOperationBinding("Rollback");
        createOp.execute();
        mode = "V";
        return null;
    }

    public String deleteScheduleACTION() {
        OperationBinding delOp = ADFBeanUtils.getOperationBinding("Delete");
        delOp.execute();
        TrnpVhclSchdlAMImpl am =
            (TrnpVhclSchdlAMImpl) ADFModelUtils.resolvEl("#{data.TrnpVhclSchdlAMDataControl.dataProvider}");
        am.getDBTransaction().postChanges();
        am.getLovAvailableVhclNo().executeQuery();
        am.getSlsTrnpLr().executeQuery();
        return null;
    }
}
