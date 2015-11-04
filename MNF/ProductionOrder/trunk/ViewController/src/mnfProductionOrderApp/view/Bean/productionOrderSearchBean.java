package mnfProductionOrderApp.view.Bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import java.sql.SQLException;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import mnfProductionOrderApp.view.Utils.ADFUtils;

import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Timestamp;

public class productionOrderSearchBean {
    
    OperationBinding ob = null;
    private RichInputDate pdoCreateToBind;
    private RichInputDate pdoCreateFromBind;
 

    public productionOrderSearchBean() {
    }

  

    public void setPdoCreateToBind(RichInputDate pdoCreateToBind) {
        this.pdoCreateToBind = pdoCreateToBind;
    }

    public RichInputDate getPdoCreateToBind() {
        return pdoCreateToBind;
    }

    public void setPdoCreateFromBind(RichInputDate pdoCreateFromBind) {
        this.pdoCreateFromBind = pdoCreateFromBind;
    }

    public RichInputDate getPdoCreateFromBind() {
        return pdoCreateFromBind;
    }

    public String createPdoAC() {
        ob = ADFBeanUtils.findOperation("chkgetYearFyId");
        ob.execute();
        oracle.jbo.domain.Number chkUsr = (oracle.jbo.domain.Number)ob.getResult();
        if (!(chkUsr.compareTo(-1)==0)) {
     // if (!(ob.getResult()==-1)) {
            return "createProdOrd";
        }else{
           StringBuilder message = new StringBuilder();
            message.append("Financial Year is not Defined !");
            ADFModelUtils.showFormattedFacesMessage("Production Order", message.toString(), FacesMessage.SEVERITY_INFO);
        }
        return null;
        //return "createProdOrd";
    }
}
