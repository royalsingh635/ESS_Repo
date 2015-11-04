package slsordrsplittingapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.binding.OperationBinding;

public class SLSOrdrSplittingAddEditBean {
    private String mode = "V";

    public String getMode() {
        return mode;
    }

    public SLSOrdrSplittingAddEditBean() {
    }

    public void editAE(ActionEvent actionEvent) {
        mode = "E";
    }

    public void saveAE(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("saveSplitData");
        binding.execute();
        Object object = binding.getResult();
        Integer o = (object == null ? -1 : (Integer) object);
        if (o.equals(0)) {
            mode = "V";
        }
    }

    public String cancelAE() {
        ADFBeanUtils.findOperation("resetSearchonBack").execute();
        ADFBeanUtils.getOperationBinding("Rollback").execute();
        mode = "V";
        return "goBack";
    }

    /**
     * Method to add Item to add
     * @param actionEvent
     * 0 : Added Successfully
     * 1 : Customer not Selected
     * 2 : Item not selected
     * 3 : Item Quantity is not valid
     * 4 : Item RowAlready Exist
     * 5 : Item Quantity is greater than Sales Order Item Quantity.
     */
    public void addItemForSlpitAE(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("addItem");
        binding.execute();
        Object object = binding.getResult();
        Integer i = (object == null ? -1 : (Integer) object);
        if (i == 1) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2142"),ADFModelUtils.resolvRsrc("MSG.2142"),
                                           FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(actionEvent.getComponent(), "transEoNmId"));    //Customer Name have not been selected !", "Please select Customer Name. 
        } else if (i == 2) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2146"),ADFModelUtils.resolvRsrc("MSG.586"),
                                           FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(actionEvent.getComponent(), "transItmNmId"));   // Item have not been selected !", "Please select Item.
        } else if (i == 3) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2147"), ADFModelUtils.resolvRsrc("MSG.2148"),
                                           FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(actionEvent.getComponent(), "it32"));             //Item Quantity is Invalid !", "Please select a Quantity Greater than zero.
        } else if (i == 4) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2149"),
                                           ADFModelUtils.resolvRsrc("MSG.2150"),                          //Duplicate Customer and Item for this Order !",
                                                                                                                         // "Combination of Customer and Item Already exists.
                                           FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(actionEvent.getComponent(), "transEoNmId"));
        } else if (i == 5) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2155"),                       //Item Quantity is greater than Sales Item Quantity !
                                           ADFModelUtils.resolvRsrc("MSG.2157"),   //Item Quantity cannot be Greater than total Order Quantity for that Item
                                           FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(actionEvent.getComponent(), "it32"));
        } else if (i == -1) {
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2152"),                            //There have been some Error While adding Item !
                                           ADFModelUtils.resolvRsrc("MSG.2153"),                   //Please try again! If the problem persists, Contact ESS.
                                           FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(actionEvent.getComponent(), "shipDispIdTransId"));
        } else if(i==6){
            ADFModelUtils.showFacesMessage(ADFModelUtils.resolvRsrc("MSG.2288"),                            //There have been some Error While adding Item !
                                           ADFModelUtils.resolvRsrc("MSG.2104"),                   //Please try again! If the problem persists, Contact ESS.
                                           FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(actionEvent.getComponent(), "it32"));
        }
    }

    /**
     * Method to fetch Component client id
     * @param comp
     * @param id
     * @return
     */
    public String getComponentCliendIdFromId(UIComponent comp, String id) {
        if (comp != null) {
            StringBuilder clientId = new StringBuilder(comp.getClientId());
            clientId = new StringBuilder(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            return clientId.append(id).toString();
        } else {
            return null;
        }
    }

    /**
     * Method to remove a spllitted Item
     * @param actionEvent
     */
    public void deleteSliptItemAE(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("Delete").execute();
    }

    public void splitOrderACE(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("splitORder").execute();
    }

    public void backACE(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("resetSearchonBack").execute();
    }
}
