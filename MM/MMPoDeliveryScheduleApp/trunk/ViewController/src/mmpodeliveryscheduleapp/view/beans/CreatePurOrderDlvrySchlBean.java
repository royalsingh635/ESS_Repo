package mmpodeliveryscheduleapp.view.beans;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;


public class CreatePurOrderDlvrySchlBean {
    private String mode = modeGet();
    private RichInputText balanceQtyBind;

    public CreatePurOrderDlvrySchlBean() {
    }

    public String modeGet() {
        return (String) ADFModelUtils.resolvEl("#{pageFlowScope.Add_Edit_Mode}");
    }

    public String cancelAction() {
        ADFBeanUtils.getOperationBinding("Rollback").execute();
        setMode(" ");
        return "backToSearch";
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        if (mode.equals("")) {
            return modeGet();
        } else {
            return mode;
        }
    }

    public void editAction(ActionEvent actionEvent) {
        setMode("A");
    }

    public void schdlAmend(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println(" amend ::: ");
        setMode("A");
    }

    public void saveAction(ActionEvent actionEvent) {
        // Add event code here...
        ADFBeanUtils.getOperationBinding("Commit").execute();
        setMode("V");
    }

    public void addScheduleAction(ActionEvent actionEvent) {
        // Add event code here...

        //OperationBinding ob =   ADFBeanUtils.getOperationBinding("chkDlvryDateAndWhSelected");
        OperationBinding ob = ADFBeanUtils.findOperation("chkDlvryDateAndWhSelected");
        ob.execute();
        Object ret = ob.getResult();
        Integer retVal = (ret == null ? 0 : (Integer) ret);
        if (retVal.compareTo(new Integer(1)) == 0) {
            //String msg1="Date null";
            // String msg2= "Please Select Date ";
            String msg1=ADFModelUtils.resolvRsrc("MSG.1916");
            String msg2=ADFModelUtils.resolvRsrc("MSG.1917");
//            String msg1 = resolvElDCMsg("#{bundle['MSG.1916']}").toString();
//            String msg2 = resolvElDCMsg("#{bundle['MSG.1917']}").toString();

            ADFModelUtils.showFacesMessage(msg1, msg2, FacesMessage.SEVERITY_WARN, null);
            return;
        }
        if (retVal.compareTo(new Integer(2)) == 0) {

            //String msg1 = "Warehouse null";
            // String msg2 = "Please Select Warehouse.";
            String msg1=ADFModelUtils.resolvRsrc("MSG.1914");
            String msg2=ADFModelUtils.resolvRsrc("MSG.1915");
//            String msg1 = resolvElDCMsg("#{bundle['MSG.1914']}").toString();
//            String msg2 = resolvElDCMsg("#{bundle['MSG.1915']}").toString();
            ADFModelUtils.showFacesMessage(msg1, msg2, FacesMessage.SEVERITY_WARN, null);
            return;
        }
        ADFBeanUtils.findOperation("addDlvSchedule").execute();


    }


    public void deleteDlvAction(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("deleteDlvSch").execute();
    }

    public void selectAllDlvrSchlQty(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("selectAllDlvQty").execute();
    }


    public void setBalanceQtyBind(RichInputText balanceQtyBind) {
        this.balanceQtyBind = balanceQtyBind;
    }

    public RichInputText getBalanceQtyBind() {
        return balanceQtyBind;
    }

    public void schdlQtyValidatior(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (((Number) object).compareTo((Number) balanceQtyBind.getValue()) == 1) {
                //  String msg1= "Quantity must be less than or equals to Balance Quantity."
                String msg1=ADFModelUtils.resolvRsrc("MSG.1918");
//                String msg1 = resolvElDCMsg("#{bundle['MSG.1918']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg1, null));
            }
        }

    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }


}
