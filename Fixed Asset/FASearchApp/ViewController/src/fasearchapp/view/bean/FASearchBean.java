package fasearchapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

public class FASearchBean {
    public FASearchBean() {
    }
    private String mode = "V";
    private String depExecId = null;
    String regdocId = null;

    public void setDepExecId(String depExecId) {
        this.depExecId = depExecId;
    }

    public String getDepExecId() {
        return depExecId;
    }


    public void setRegdocId(String regdocId) {
        this.regdocId = regdocId;
    }

    public String getRegdocId() {
        return regdocId;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void searchAction(ActionEvent actionEvent) {
        ADFBeanUtils.callBindingsMethod("faSearch", null, null);
    }

    public void resetAction(ActionEvent actionEvent) {
        ADFBeanUtils.callBindingsMethod("faReset", null, null);
    }

    public String searchViewAction() {
        mode = "V";
        ADFBeanUtils.callBindingsMethod("getExeId", null, null);
        return "goToProcess";
    }

    public String DepProcessAction() {
        Object ob = ADFBeanUtils.callBindingsMethod("chkForPendingProcess", null, null);
        if (ob != null) {
            if (ob.toString().equalsIgnoreCase("N")) {
                mode = "A";
                depExecId = null;
                return "goToProcess";
            } else {
                ADFModelUtils.showFacesMessage("Voucher is already pending for approval. First post the voucher for next depreciation process .",
                                               "Voucher is already pending for approval. First post the voucher for next depreciation process .",
                                               FacesMessage.SEVERITY_ERROR, null);
                return "Y";
            }
        } else
            return "Y";
    }

    public String viewRegisterAction() {
        mode = "V";
        Object ob = ADFBeanUtils.callBindingsMethod("getRegDocId", null, null);
        if (ob != null)
            regdocId = ob.toString();
        return "goToRegister";
    }

    public String addFARegAction() {
        mode = "A";
        regdocId = null;
        return "goToRegister";
    }

    public String processViewAction() {
        mode = "V";
        Object ob = ADFBeanUtils.callBindingsMethod("getExeId", null, null);
        if (ob != null)
            depExecId = ob.toString();
        return "goToProcess";
    }
}
