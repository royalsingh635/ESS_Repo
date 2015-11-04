package appItemImage.view.bean;

import java.io.File;
import java.io.Serializable;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.context.RequestContext;

public class ItemImageBean implements Serializable {
   
    private RichOutputText imageIdBindVar;
    private String ImageIdVal=null;
    private String DefVal=null;
    private RichOutputText defaultValue;
    private RichOutputText pathBindVar;
    private String path=null;
    private String createButton="false";
    private String saveButton ="true";

    public ItemImageBean() {
    }

    public String ItemAction() {
       
        ImageIdVal= imageIdBindVar.getValue().toString();
        DefVal=defaultValue.getValue().toString();
        RequestContext.getCurrentInstance().getPageFlowScope().put("IMAGE_ID", ImageIdVal);
        RequestContext.getCurrentInstance().getPageFlowScope().put("DEFAULT_VALUE", DefVal);
        
        
        
        
        return "Edit";
    }
    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }
    public void setImageIdBindVar(RichOutputText imageIdBindVar) {
        this.imageIdBindVar = imageIdBindVar;
    }

    public RichOutputText getImageIdBindVar() {
        return imageIdBindVar;
    }

    public void setImageIdVal(String ImageIdVal) {
        this.ImageIdVal = ImageIdVal;
    }

    public String getImageIdVal() {
        return ImageIdVal;
    }

    public String CreateAction() {
        RequestContext.getCurrentInstance().getPageFlowScope().put("DEFAULT_VALUE", "A"); 
        return "Create";
    }

    public String DeleteAction() {
        
        ImageIdVal= imageIdBindVar.getValue().toString();
        path=pathBindVar.getValue().toString();
        RequestContext.getCurrentInstance().getPageFlowScope().put("DELETE_IMG_ID", ImageIdVal);
        createButton="true";
        saveButton="false";
        return "Delete";
    }

    public String SaveAction() {
        OperationBinding createOB = executeBinding("Commit");
        createOB.execute();
        if(createOB.getErrors().isEmpty()){
            createButton="false";
            saveButton="true";
            File deleteFile = new File(path); 
            // check if the file is present or not
            if( deleteFile.exists() ){
                
                Boolean val=deleteFile.delete() ;
               
               
                
            }
        }
        
        return "Refresh";
    }

    public String CancelAction() {
        OperationBinding createOB = executeBinding("Rollback");
        createOB.execute();
        createButton="false";
        saveButton="true";
        return "Refresh";
    }
    
    public OperationBinding executeBinding(String var) {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding(var);
        return operationBinding;
    }

    public void setDefaultValue(RichOutputText defaultValue) {
        this.defaultValue = defaultValue;
    }

    public RichOutputText getDefaultValue() {
        return defaultValue;
    }

    

    public void setPathBindVar(RichOutputText pathBindVar) {
        this.pathBindVar = pathBindVar;
    }

    public RichOutputText getPathBindVar() {
        return pathBindVar;
    }

    public void setCreateButton(String createButton) {
        this.createButton = createButton;
    }

    public String getCreateButton() {
        return createButton;
    }

    public void setSaveButton(String saveButton) {
        this.saveButton = saveButton;
    }

    public String getSaveButton() {
        return saveButton;
    }
}
