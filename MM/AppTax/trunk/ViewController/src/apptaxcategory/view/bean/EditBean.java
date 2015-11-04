package apptaxcategory.view.bean;

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

import oracle.adf.model.BindingContext;

import oracle.binding.OperationBinding;

public class EditBean {
    public EditBean() {
    }
    String mode = "V";

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }


    public void editMethod(ActionEvent actionEvent) {
        // Add event code here...
        // BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("edit").execute();
        mode = "C";


    }

    public void addMethod(ActionEvent actionEvent) {
        // Add event code here
        //doBeforeCommit

        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert").execute();
        mode = "C";
    }

    public void save(ActionEvent actionEvent) {
        // Add event code h"ere...
        System.out.println("save function call");
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("doBeforeCommit").execute();

        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
        mode = "V";
    }

    public void cancel(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("in cancel mode");
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();
        mode = "V";
    }


    public void categoryNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        //  System.out.println("---------inside Validator-----");
        if (object != null) {
            //  System.out.println("--------not null--------------");
            OperationBinding op = ADFBeanUtils.getOperationBinding("categoryNmCheck");
            op.getParamsMap().put("name", object.toString());
            op.execute();
            System.out.println("--------------result-- " + op.getResult() + " " + object);
            if (op.getResult() != null && op.getResult().toString().equals("Y")) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Record Found.",
                                                              null));

            }

        }


    }
}
