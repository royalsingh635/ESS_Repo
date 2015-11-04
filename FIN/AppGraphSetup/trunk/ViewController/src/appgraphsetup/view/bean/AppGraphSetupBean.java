package appgraphsetup.view.bean;

import appgraphsetup.model.module.AppGraphSetupAMImpl;

import appgraphsetup.model.view.AppGrphTrndVORowImpl;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.ViewObject;


public class AppGraphSetupBean {
    private RichSelectOneChoice graphId;

    public AppGraphSetupBean() {
    }
    private Boolean readOnly = true;
    private String Mode = null;
    private Boolean create = false;
    private Boolean edit = false;
    private Boolean save = true;
    private Boolean cancel = true;

    public String CreateGraph() {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        if (operationBinding.getErrors().isEmpty()) {

            Mode = "A";
            readOnly = false;
            create=true;
            edit =true;
            save=false;
            cancel=false;
        }
        return null;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public String SaveAction() {
        if(graphId.getValue()==""){
            String msg = "Graph Name is required.";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        else{
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        if (Mode == "E") {

            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
         
            DCIteratorBinding Iter = (DCIteratorBinding)bindings.get("AppGrphIterator");
            Key parentKey = Iter.getCurrentRow().getKey();

            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");
            operationBinding1.execute();
            
            
            Iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
            
            if(operationBinding.getErrors().isEmpty()){
                String msg = "Record saved successfully.";
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_INFO);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                readOnly = true;
                create = false;
                edit = false;
                save = true;
                cancel = true;
                Mode = "S";
            }
            
        } else if (Mode == "A") {
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();

            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");
            operationBinding1.execute();
            
            DCIteratorBinding Iter = (DCIteratorBinding)bindings.get("AppGrphIterator");
            Iter.executeQuery();
            
            if(operationBinding.getErrors().isEmpty()){
                String msg = "Record saved successfully.";
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_INFO);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                readOnly = true;
                create = false;
                edit = false;
                save = true;
                cancel = true;
                Mode = "S";
            }
        }
        }
        return null;
    }

    public String CancelAction() {
        if (Mode == "E") {
            
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding Iter = (DCIteratorBinding)bindings.get("AppGrphIterator");
        Key parentKey = Iter.getCurrentRow().getKey();
       
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        Iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        readOnly = true;
       
        create = false;
        edit = false;
        save = true;
        cancel = true;
        Mode = "C";
        }
        else if (Mode == "A") {
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();
            OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
            operationBindingexe.execute();
            DCIteratorBinding Iter = (DCIteratorBinding)bindings.get("AppGrphIterator");
            Iter.executeQuery();
            readOnly = true;
            
            create = false;
            edit = false;
            save = true;
            cancel = true;
            Mode = "C";
        }
        return null;
    }

    public String EditAction() {
        readOnly = false;
        Mode = "E";
        create=true;
        edit =true;
        save=false;
        cancel=false;
        
        return null;
    }

    public String CreateTrendAction() {
        // Add event code here...
        return "Create";
    }

    public String EditTrendAction() {
        String RetVal=null;
        AppGraphSetupAMImpl am = (AppGraphSetupAMImpl)resolvElDC("AppGraphSetupAMDataControl");
        ViewObject v1 = am.getAppGrphTrnd();
        AppGrphTrndVORowImpl currRow=(AppGrphTrndVORowImpl)v1.getCurrentRow();
        String a=currRow.toTemp();
        if(a.equals("Y")){
            RetVal="Edit";
        }
        else{
            RetVal=null;
            String msg = "Something went wrong.Please contact ESS.";
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
             
        }
        return RetVal;
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
    public String DeleteTrendAction() {
        // Add event code here...
        return null;
    }

    public void setCreate(Boolean create) {
        this.create = create;
    }

    public Boolean getCreate() {
        return create;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }

    public Boolean getEdit() {
        return edit;
    }

    public void setSave(Boolean save) {
        this.save = save;
    }

    public Boolean getSave() {
        return save;
    }

    public void setCancel(Boolean cancel) {
        this.cancel = cancel;
    }

    public Boolean getCancel() {
        return cancel;
    }

    public void setGraphId(RichSelectOneChoice graphId) {
        this.graphId = graphId;
    }

    public RichSelectOneChoice getGraphId() {
        return graphId;
    }
}
