package falandingapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import javax.faces.application.FacesMessage;

public class FALandingBean {
    public FALandingBean() {
    }

    public String unpostedLink() {
        Object res = ADFBeanUtils.callBindingsMethod("unPostedDetail", null, null);
       System.out.println("res = "+res);
        if(res!=null){
            
            if(res.toString().equalsIgnoreCase("Y")){
                return "un";
            }else{
                //return res.toString();
                ADFModelUtils.showFacesMessage(res.toString(), res.toString(), FacesMessage.SEVERITY_ERROR , null);
            }
    
    }
    return null;
}
}