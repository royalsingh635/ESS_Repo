package scprofileapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.context.RequestContext;

public class ScProfileSetupBean {
    public ScProfileSetupBean() {
    }

    /**
     * Action Listner for ADD Button
     * @Return void
     * */
    public void AddLink(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.findOperation("returnRowCount");
        binding.execute();
        Integer i = (Integer) binding.getResult();
        if (i == 0) {
            RequestContext.getCurrentInstance().getPageFlowScope().put("PAGE_MODE", "C");
            ADFBeanUtils.findOperation("CreateInsert").execute();
        } else {
            ADFModelUtils.showFormattedFacesMessage("Subcontracting Profile Setup", "Record Already Exist.".toString(),
                                                    FacesMessage.SEVERITY_INFO);
        }
    }

    /**
     * Action Listner for EDIT Button
     * @Return void
     * */
    public void EditLink(ActionEvent actionEvent) {
        RequestContext.getCurrentInstance().getPageFlowScope().put("PAGE_MODE", "C");
    }

    /**
     * Action Listner for SAVE Button
     * @Return void
     * */
    public void SaveLink(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("Commit").execute();
        RequestContext.getCurrentInstance().getPageFlowScope().put("PAGE_MODE", "V");
        ADFModelUtils.showFormattedFacesMessage("Subcontracting Profile Setup", "Record Saved Successfully.".toString(),
                                                FacesMessage.SEVERITY_INFO);
    }

    /**
     * Action Listner for CANCEL Button
     * @Return void
     * */
    public void CancelLink(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("Rollback").execute();
        RequestContext.getCurrentInstance().getPageFlowScope().put("PAGE_MODE", "V");
    }

    /**
     * Action Listner for Profile Freeze Link
     * @return void
     * */
    public void FreezeProfileACL(ActionEvent acl) {
        if (acl != null) {
            ADFBeanUtils.findOperation("FreezeProfileMethod").execute();
        }
    }
}
