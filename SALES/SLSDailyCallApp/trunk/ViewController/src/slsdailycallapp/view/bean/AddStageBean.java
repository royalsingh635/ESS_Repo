package slsdailycallapp.view.bean;

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

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class AddStageBean {
    private StringBuffer mode = new StringBuffer("V");

    public AddStageBean() {
    }

    public void addACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("CreateInsert").execute();
        mode = new StringBuffer("A");
    }

    public void editACTION(ActionEvent actionEvent) {
        mode = new StringBuffer("E");
    }

    public void saveACTION(ActionEvent actionEvent) {
        OperationBinding chk = this.getBindings().getOperationBinding("isDataValid");
        chk.execute();
        if (chk.getResult().equals((Object)true)) {
            OperationBinding binding = this.getBindings().getOperationBinding("generateAndSetStage");
            binding.execute();
            if (binding.getResult().equals((Object)false)) {
                String mes = ADFModelUtils.resolvRsrc("MSG.1777");
                FacesMessage message =
                    new FacesMessage(mes);//There is some error in Generating Stage Id. Please Try Again !");
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(null, message);

            } else {
                OperationBinding operationBinding = this.getBindings().getOperationBinding("Commit");
                operationBinding.execute();
                if (operationBinding.getErrors().isEmpty()) {
                    String mes = ADFModelUtils.resolvRsrc("MSG.1260");

                    FacesMessage message = new FacesMessage(mes);
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    mode = new StringBuffer("V");
                } else {
                    String mes1 = ADFModelUtils.resolvRsrc("MSG.1261");
                    FacesMessage message = new FacesMessage(mes1);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
        } else {
            String mes = ADFModelUtils.resolvRsrc("MSG.1778");//"Stage name is not entered or Duplicate Stage Name is entered for one or more stages. Please enter valid values for all the stages !";

            FacesMessage message = new FacesMessage(mes);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }


    }

    public void cancelACTION(ActionEvent actionEvent) {
        this.getBindings().getOperationBinding("Rollback").execute();
        mode = new StringBuffer("V");
    }

    public String goBackACTION() {
        // Add event code here...
        return "return";
    }

    public void setMode(StringBuffer mode) {
        this.mode = mode;
    }

    public StringBuffer getMode() {
        return mode;
    }

    /**  method to get Bindings
     */
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    public void stageNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /* DailyCallAMImpl am;
        am = (DailyCallAMImpl)resolvElDC("DailyCallAMDataControl");
        ViewObjectImpl dcStage1 = am.getSlsDcStage1();
        Row[] filteredRowsInRange = dcStage1.getFilteredRows("StageNm", object.toString()); */
        //int i = filteredRowsInRange.length;
        /* if(object != null && !"".equals(object)){
            OperationBinding binding = getBindings().getOperationBinding("checkDuplicateStageName");
            binding.getParamsMap().put("name", object.toString());
            binding.execute();
            Integer object_2 = (Integer)binding.getResult();
            String msg = "Stage with the same name found.Please enter any other name.";
            if (binding == null || object_2 > 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }    
        } */
    }
}
