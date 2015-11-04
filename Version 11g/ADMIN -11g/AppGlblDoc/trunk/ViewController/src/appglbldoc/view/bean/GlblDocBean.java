 package appglbldoc.view.bean;

import com.sun.corba.se.spi.orb.Operation;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

//import oracle.adfinternal.view.faces.context.AdfFacesContextImpl;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class GlblDocBean {
    private RichPopup popupBindingOpmode;
    private RichPopup popupBindingType;
    private RichPopup popupBindingTypeReset;
    private RichOutputText globalDocTypIdBinding;
    private RichSelectOneChoice globalDocTypeBindingReset;
    private RichInputText globalDocTypeNameBinding;
    private String Mode="V";
    private RichTable tableBinding;
    private RichSelectBooleanCheckbox hasDocTypeBinding;
    private RichOutputText glblDocIdBinding;
    private RichTable tableOpmodeBinding;
    private RichTable tableTypeBinding;
    private RichTable tableTypeResetBinding;

    public GlblDocBean (){
        
    }
  
   Integer count =Integer.parseInt(getBindings().getOperationBinding("on_load_page").execute().toString());
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    private void showPopup(RichPopup pop, boolean visible) {
        System.out.println("in show popup method");
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null) {
                System.out.println("context"+context+"pop"+pop);
                String popupId = pop.getClientId(context);
                if (popupId != null) {
                    System.out.println("popupid"+popupId);
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible) {
                    System.out.println("visible");
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                    } else {
                        System.out.println("not visible");
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
    public void addButton(ActionEvent actionEvent) {
      
    
      
         Mode="C";
        tableBinding.setRowSelection("none");
       BindingContainer bindings = getBindings();
        OperationBinding operBinding = bindings.getOperationBinding("CreateInsert");
        operBinding.execute(); 
 
    }

    public void editButton(ActionEvent actionEvent) {
       Mode="C";
      tableBinding.setRowSelection("none");
            }

    public void saveButton(ActionEvent actionEvent) {
        Mode="V";
        tableBinding.setRowSelection("single");
        BindingContainer bindings = getBindings();
         OperationBinding operBinding = bindings.getOperationBinding("Commit");
         operBinding.execute();
         BindingContainer bind=getBindings();
         OperationBinding operBind=bind.getOperationBinding("page_Refresh");
         operBind.execute();
      
    }

    public void cancelButton(ActionEvent actionEvent) {
        Mode="V";
        tableBinding.setRowSelection("single");
        BindingContainer bindings = getBindings();
         OperationBinding operBinding = bindings.getOperationBinding("Rollback");
         operBinding.execute();
    }

 

    public void addOpModeButton(ActionEvent actionEvent) {
        System.out.println("in addOpModeButton Action");
        BindingContainer bindings = getBindings();
        OperationBinding oprbinding = bindings.getOperationBinding("CreateInsert1");
        oprbinding.execute();
        System.out.println("showpopup"+popupBindingOpmode);
        showPopup(popupBindingOpmode, true);
        System.out.println("after show popup");
          }

    public void opModeEditButton(ActionEvent actionEvent) {
       
        showPopup(popupBindingOpmode, true);
    }

   

    public void addTypeButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding oprbinding = bindings.getOperationBinding("CreateInsert2");
        oprbinding.execute();
        showPopup(popupBindingType, true);
        
    }

    public void editTypeButton(ActionEvent actionEvent) {
       
        showPopup(popupBindingType, true);
    }

    public void addResetButton(ActionEvent actionEvent) {
       BindingContainer bindings = getBindings();
       OperationBinding operbinding = bindings.getOperationBinding("CreateInsert3");
       operbinding.execute();
       globalDocTypeBindingReset.setValue(globalDocTypIdBinding.getValue());
       showPopup(popupBindingTypeReset, true);
 
    }

    public void editResetButton(ActionEvent actionEvent) {
        showPopup(popupBindingTypeReset, true);
     
    }

  

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setPopupBindingOpmode(RichPopup popupBindingOpmode) {
        this.popupBindingOpmode = popupBindingOpmode;
    }

    public RichPopup getPopupBindingOpmode() {
        return popupBindingOpmode;
    }

    public void opmodeCancelListner(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
              OperationBinding operationBinding =bindings.getOperationBinding("Rollback");
              operationBinding.execute();
      AdfFacesContext.getCurrentInstance().addPartialTarget(tableOpmodeBinding);
    }

    public void opmodeDialogListner(DialogEvent dialogEvent) {
        if(dialogEvent.getOutcome().name().equals("ok")){
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("Commit");
        operationBinding.execute();
            if (operationBinding.getErrors().isEmpty()) {
                String message1 = resolvElDCMsg("#{bundle['MSG.91']}").toString();
                FacesMessage message = new FacesMessage(message1);
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
                String message1 = resolvElDCMsg("#{bundle['MSG.660']}").toString();
                FacesMessage message = new FacesMessage(message1);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        }
        else
        {        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("Rollback");
        operationBinding.execute();
            }
        AdfFacesContext.getCurrentInstance().addPartialTarget(tableOpmodeBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tableTypeBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tableTypeResetBinding);

    }
    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void setPopupBindingType(RichPopup popupBindingType) {
        this.popupBindingType = popupBindingType;
    }

    public RichPopup getPopupBindingType() {
        return popupBindingType;
    }

    public void setPopupBindingTypeReset(RichPopup popupBindingTypeReset) {
        this.popupBindingTypeReset = popupBindingTypeReset;
    }

    public RichPopup getPopupBindingTypeReset() {
        return popupBindingTypeReset;
    }

    public void setGlobalDocTypIdBinding(RichOutputText globalDocTypIdBinding) {
        this.globalDocTypIdBinding = globalDocTypIdBinding;
    }

    public RichOutputText getGlobalDocTypIdBinding() {
        return globalDocTypIdBinding;
    }

    public void setGlobalDocTypeBindingReset(RichSelectOneChoice globalDocTypeBindingReset) {
        this.globalDocTypeBindingReset = globalDocTypeBindingReset;
    }

    public RichSelectOneChoice getGlobalDocTypeBindingReset() {
        return globalDocTypeBindingReset;
    }

    public void hasDocTypeVCL(ValueChangeEvent valueChangeEvent) {
       System.out.println("valueChangeEvent"+valueChangeEvent.getNewValue().toString());
       if(valueChangeEvent.getNewValue().toString().equals("true")) {
           BindingContainer bind=getBindings();
           OperationBinding operBind=bind.getOperationBinding("page_Refresh");
           operBind.execute();
          
       }
       if(valueChangeEvent.getNewValue().toString().equals("false")) {
           // will be discussed later.........
           FacesMessage message = new FacesMessage("Has Doc Type cannot be changed not has Doc Type... Contact ESS!");
           message.setSeverity(FacesMessage.SEVERITY_ERROR);
           FacesContext fc = FacesContext.getCurrentInstance();
           fc.addMessage(null, message);
           hasDocTypeBinding.setValue("true");
           AdfFacesContext.getCurrentInstance().addPartialTarget(valueChangeEvent.getComponent());
       }
    }

    public void setGlobalDocTypeNameBinding(RichInputText globalDocTypeNameBinding) {
        this.globalDocTypeNameBinding = globalDocTypeNameBinding;
    }

    public RichInputText getGlobalDocTypeNameBinding() {
        return globalDocTypeNameBinding;
    }

    public void setMode(String Mode) {
        this.Mode = Mode;
    }

    public String getMode() {
        return Mode;
    }

    public void setTableBinding(RichTable tableBinding) {
        this.tableBinding = tableBinding;
    }

    public RichTable getTableBinding() {
        return tableBinding;
    }

    public void hasDocTypeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
/*        System.out.println("in validator"+object.toString());
       if(object.toString().equals("true")) {
           BindingContainer bind=getBindings();
           OperationBinding operBind=bind.getOperationBinding("page_Refresh");
           operBind.execute();
       }
       if(object.toString().eq) */
    }

    public void setHasDocTypeBinding(RichSelectBooleanCheckbox hasDocTypeBinding) {
        this.hasDocTypeBinding = hasDocTypeBinding;
    }

    public RichSelectBooleanCheckbox getHasDocTypeBinding() {
        return hasDocTypeBinding;
    }

    public void glblDocUsrtypeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
      System.out.println("in glblDocuserValidator in bean"+object.toString());
        BindingContainer bind=getBindings();
        System.out.println("bindings"+bind);
        OperationBinding operBind=bind.getOperationBinding("opModeValidator");
        operBind.getParamsMap().put("glbldocid", glblDocIdBinding.getValue());
        operBind.getParamsMap().put("usertype", object.toString());
        operBind.execute();
        int i=(Integer)operBind.getResult();
        System.out.println("value of i"+i);
        if(i>0) {
            FacesMessage message = new FacesMessage("Data already exists!!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            
        }
    }

    public void setGlblDocIdBinding(RichOutputText glblDocIdBinding) {
        this.glblDocIdBinding = glblDocIdBinding;
    }

    public RichOutputText getGlblDocIdBinding() {
        return glblDocIdBinding;
    }

    public void setTableOpmodeBinding(RichTable tableOpmodeBinding) {
        this.tableOpmodeBinding = tableOpmodeBinding;
    }

    public RichTable getTableOpmodeBinding() {
        return tableOpmodeBinding;
    }

    public void setTableTypeBinding(RichTable tableTypeBinding) {
        this.tableTypeBinding = tableTypeBinding;
    }

    public RichTable getTableTypeBinding() {
        return tableTypeBinding;
    }

    public void setTableTypeResetBinding(RichTable tableTypeResetBinding) {
        this.tableTypeResetBinding = tableTypeResetBinding;
    }

    public RichTable getTableTypeResetBinding() {
        return tableTypeResetBinding;
    }
}
