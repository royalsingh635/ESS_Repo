package transferbalances.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import javax.faces.application.FacesMessage;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.nav.RichLink;

import oracle.binding.OperationBinding;

public class TransferBean {

    public TransferBean() {
    }

    public String refreshMethod() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("filterIncomeExpenseVO");
        binding.execute();
        return null;
    }
  

    public String generateVoucher() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("generateVoucher_Transfer");
        binding.execute();
        Object res = binding.getResult();
        if(res!=null){
            if(res.equals("nezero")){
                ADFModelUtils.showFacesMessage("Net Income/Expense and Asset/Liability Amount is not equal", null, FacesMessage.SEVERITY_ERROR, null);
            }else{
                ADFModelUtils.showFacesMessage("Amount Transfered Successfully", null, FacesMessage.SEVERITY_INFO, null);
            }
        }
        return null;
    }

    public void RrAmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if(object!=null){
            oracle.jbo.domain.Number amt=(oracle.jbo.domain.Number)object;
            
            if(ADFBeanUtils.isNumberNegative(amt)){
                ADFModelUtils.showFacesMessage("Negative Value", null, FacesMessage.SEVERITY_ERROR, uIComponent.getClientId());

            }else if(!ADFBeanUtils.isPrecisionValid(amt)){
                ADFModelUtils.showFacesMessage("Invalid Precision/Scale.", null, FacesMessage.SEVERITY_ERROR,  uIComponent.getClientId());

            }
            
        }
    }

 
}
