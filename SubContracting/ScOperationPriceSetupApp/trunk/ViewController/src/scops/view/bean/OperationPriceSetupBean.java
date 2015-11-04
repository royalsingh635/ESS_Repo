package scops.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.context.RequestContext;

public class OperationPriceSetupBean {

    public OperationPriceSetupBean() {
    }

    /**
     * For Process Update in Model
     * */
    public void EoTypeVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
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
     * Create Insert On Add Link Listner
     * */
    public void OnAddCreateInsertACL(ActionEvent ace) {
        RequestContext.getCurrentInstance().getPageFlowScope().put("PAGE_MODE", "C");
        ADFBeanUtils.findOperation("CreateInsertInScEoItm").execute();
    }

    /**
     * Edit Link Listner
     * */
    public void OnEditACL(ActionEvent actionEvent) {
        RequestContext.getCurrentInstance().getPageFlowScope().put("PAGE_MODE", "C");
    }

    /**
     * Save Link Listner
     * */
    public void OnSaveACL(ActionEvent actionEvent) {
        Integer i = HeaderMandatoryFields(actionEvent.getComponent());
        if (i == 0) {
            ADFBeanUtils.findOperation("Commit").execute();
            RequestContext.getCurrentInstance().getPageFlowScope().put("PAGE_MODE", "V");
            StringBuilder message = new StringBuilder();
            message.append("<P>Saved Successfully.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Operation Price Setup", message.toString(),
                                                    FacesMessage.SEVERITY_INFO);
        }
    }

    /**
     * Method to validate Header before saving
     * @return
     */
    public Integer HeaderMandatoryFields(UIComponent ui) {
        OperationBinding binding = ADFBeanUtils.findOperation("ValidateHeaderPart");
        binding.execute();
        Object object = binding.getResult();
        Integer i = object == null ? -1 : (Integer) object;
        if (i.equals(1)) {
            ADFModelUtils.showFacesMessage("Operation not selected !", "Please select Operation.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "transOpNmId"));
        }
        if (i.equals(2)) {
            ADFModelUtils.showFacesMessage("Item not selected !", "Please select Item.", FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(ui, "transItemNmId"));
        } else if (i.equals(3)) {
            ADFModelUtils.showFacesMessage("Currency not selected !", "Please select Currency.",
                                           FacesMessage.SEVERITY_ERROR,
                                           getComponentCliendIdFromId(ui, "transSpCurrNmId"));
        } else if (i.equals(4)) {
            ADFModelUtils.showFacesMessage("Unit of Measurement not selected !", "Please select Unit of Measurement.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "soc8"));
        } else if (i.equals(5)) {
            ADFModelUtils.showFacesMessage("Item Price not Entered !", "Please Enter Item Price.",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "it11"));
        } else if (i.equals(6)) {
            ADFModelUtils.showFacesMessage("Item Price not Entered !", "Item Price can't be Zero(0)",
                                           FacesMessage.SEVERITY_ERROR, getComponentCliendIdFromId(ui, "it11"));
        }
        return i;
    }

    /**
     * On Calcel Link Listner
     * */
    public void OnCancelACL(ActionEvent actionEvent) {
        DCIteratorBinding parentIter =
            ADFBeanUtils.getDCBindingContainer().findIteratorBinding("CustomerSupplierViewVo1Iterator");
        Key parentKey = null;
        int index = 0;
        if (parentIter.getCurrentRow() != null) {
            parentKey = parentIter.getCurrentRow().getKey();
            index = parentIter.getCurrentRowIndexInRange();
        }
        System.out.println("Index  " + index + "==KEY--" + parentKey);
        ADFBeanUtils.findOperation("Rollback").execute();
        RequestContext.getCurrentInstance().getPageFlowScope().put("PAGE_MODE", "V");
        if (parentKey != null) {
            parentIter.setCurrentRowIndexInRange(index);
        }
    }

    /**
     * Code for Precision Check
     */
    public Boolean isPrecisionValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    /**
     * Item Price Validator
     * */
    public void ItemPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            if (val.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Negative numbers are not allowed.", null));
            }
        }
    }

    /**
     * Tolerance Days Validator
     * */
    public void ToleranceDaysValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            if (val.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Negative numbers are not allowed.", null));
            }
        }
    }

    /**
     * Lead Days Validator
     * */
    public void LeadDaysValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            if (val.compareTo(0) < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Negative numbers are not allowed.", null));
            }
        }
    }
}
