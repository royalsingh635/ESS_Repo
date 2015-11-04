package appcurr.bean;

import java.io.Serializable;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class AppCurrBean implements Serializable {
    private String form_mode = "V";
    private String button_mode = "V";
    private RichPopup delPopupBind;
    private Integer count = -1;
    

    public AppCurrBean() {
        count=(Integer)getBindings().getOperationBinding("on_load_page").execute();
        System.out.println(">>>>>>>>>>>>>>count value ="+count);
    }
    
    public BindingContainer getBindings() {
            return BindingContext.getCurrent().getCurrentBindingsEntry();
        }
    
    public void addCurrACT(ActionEvent actionEvent) {
        form_mode = "A";
        button_mode = "A";
        OperationBinding binding = this.getBindings().getOperationBinding("CreateInsert");
        binding.execute();
    }

    public void editCurrACT(ActionEvent actionEvent) {
        form_mode = "E";
        button_mode = "E";
    }
    
    public Object resolvElDCMsg(String data) {
    FacesContext fc = FacesContext.getCurrentInstance();
    Application app = fc.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = fc.getELContext();
    ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
    return valueExp.getValue(elContext);
    }

    public void saveCurrACT(ActionEvent actionEvent) {
        
        // To setMaxId in CurrIdOperationBinding binding = this.getBindings().getOperationBinding("Commit");
        // binding.execute();
      // this.getBindings().getOperationBinding("getSetMaxCurrId").execute();
        OperationBinding binding1 = this.getBindings().getOperationBinding("getSetMaxCurrId");
        binding1.execute();
        // To commit
        OperationBinding binding = this.getBindings().getOperationBinding("Commit");
        binding.execute();
        System.out.println("errorsss issss"+binding.getErrors());
        if(binding.getErrors().isEmpty()){
            if(form_mode.equals("A")){
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.613']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }else if(form_mode.equals("E")){
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.614']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
            
        }
        form_mode = "V";
        button_mode = "V";
    }

    public void cancelCurrACT(ActionEvent actionEvent) {
       
        
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Execute").execute();
        form_mode = "V";
        button_mode = "V";
    }

    public void deleteCurrACT(ActionEvent actionEvent) {
        showPopup(delPopupBind, true);
    }

    public void setForm_mode(String form_mode) {
        this.form_mode = form_mode;
    }

    public String getForm_mode() {
        return form_mode;
    }

    public void setButton_mode(String button_mode) {
        this.button_mode = button_mode;
    }

    public String getButton_mode() {
        return button_mode;
    }

    public void deleteCurrDialogList(DialogEvent dialogEvent) {
        this.getBindings().getOperationBinding("Delete").execute();
        OperationBinding binding = this.getBindings().getOperationBinding("Commit");
        binding.execute();
        
        if(binding.getErrors().isEmpty()){
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.615']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            
        }
    }

    public void setDelPopupBind(RichPopup delPopupBind) {
        this.delPopupBind = delPopupBind;
    }

    public RichPopup getDelPopupBind() {
        return delPopupBind;
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

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }
}
