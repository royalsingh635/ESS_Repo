package objectRl1App.view.bean;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.validator.ValidatorException;

import objectRl1App.model.module.objectRl1AppAMImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class ObjectLR1Bean {
    private RichPopup createPopup;
    private  String diplayVal="Gourav";
    private static String flag=null;
    private Integer count=-1;
    public ObjectLR1Bean() {
        count=(Integer)getBindings().getOperationBinding("on_load_page").execute();
    }
    
    
    public String AddAction() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        showPopup(createPopup,true);
        flag="A";
        return null;
    }

    public String EditAction() {
        showPopup(createPopup,true);
        flag="E";
        return null;
    }
    
    public void createPopupCancelListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();

        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
        createOpBAddress.execute();
        flag="C";
    }
    public void showPopup(RichPopup popup, boolean visible) {
           try {
               FacesContext context = FacesContext.getCurrentInstance();
               if (context != null && popup != null) {
                   String popupId = popup.getClientId(context);
                   if (popupId != null) {
                       StringBuilder script = new StringBuilder();
                       script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                       if (visible) {
                           script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");

                       } else {
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
    public BindingContainer getBindings() {
            return BindingContext.getCurrent().getCurrentBindingsEntry();
        }
    public void createDialogLIstener(DialogEvent dialogEvent) {
                if (dialogEvent.getOutcome().name().equals("ok")) {
                            BindingContainer bindings = getBindings();
                            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                            operationBinding.execute();

                            BindingContainer binding = getBindings();
                            OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
                            createOpBAddress.execute();
                            flag="S";

                        } else if (dialogEvent.getOutcome().name().equals("no")) {

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
    public void DefaultValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Boolean val=(Boolean)object;
        
        String value="";
        
        if(val==true){
             value="Y";
        }else if(val==false){
            value="N";           
        }
        
        
             
        if(val==true){  
        objectRl1AppAMImpl am=(objectRl1AppAMImpl)resolvElDC("objectRl1AppAMDataControl");
        
        ViewObject v2=am.getAppObjRL1();
        int count=0;
        String x="";
            Row[] rowI=v2.getAllRowsInRange();
            for(Row r:rowI){
                try{
                 x=r.getAttribute("ObjDef").toString();
                }
                catch(NullPointerException npe){
                  
                    x="";
                }
              
                if(value.equalsIgnoreCase(x)){
                   count=count+1;
                }
                
            }
        
        
        
            if(flag.equals("A")){
            if(count>0){
                String msg2="Only One Default value allowed";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
                }
            }
            else if(flag.equals("E")){
                if(count>1){
                    String msg2="Only One Default value allowed";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                    }
            }
            
        }
        
    }
    public void setCreatePopup(RichPopup createPopup) {
        this.createPopup = createPopup;
    }

    public RichPopup getCreatePopup() {
        return createPopup;
    }

    public void setDiplayVal(String diplayVal) {
        this.diplayVal = diplayVal;
    }

    public String getDiplayVal() {
        return diplayVal;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }
}
