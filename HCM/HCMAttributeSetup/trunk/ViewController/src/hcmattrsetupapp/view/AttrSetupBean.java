package hcmattrsetupapp.view;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import hcmattrsetupapp.model.services.HcmAttrSetupAMImpl;

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

import oracle.adf.view.rich.component.rich.input.RichInputText;


import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.RichPopup;

public class AttrSetupBean {
    private String mode = "E";
    private RichInputText attrtype;
    private RichInputText attrname;
    private String pass = "";
    private RichPopup dedLinkPopBinding;
    private RichSelectOneChoice dedBinding;

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public AttrSetupBean() {
    }

    public void editAttr(ActionEvent actionEvent) {
        mode = "D";
    }


    public void saveattr(ActionEvent actionEvent) {
        attrtype.getValue();
        OperationBinding ob = ADFBeanUtils.getOperationBinding("saveattram");
        ob.getParamsMap().put("type", attrtype.getValue());
        ob.getParamsMap().put("name", attrname.getValue());
        ob.execute();
        mode = "E";
    }

    public void cancelattr(ActionEvent actionEvent) {
        mode = "E";
        ADFBeanUtils.getOperationBinding("Rollback").execute();
    }

    public void setAttrtype(RichInputText attrtype) {
        this.attrtype = attrtype;
    }

    public RichInputText getAttrtype() {
        return attrtype;
    }

    public void setAttrname(RichInputText attrname) {
        this.attrname = attrname;
    }

    public RichInputText getAttrname() {
        return attrname;
    }

    public void valattrtype(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String st = (String) object;
        OperationBinding operationBinding = ADFBeanUtils.getOperationBinding("attrtypevalidate");
        operationBinding.getParamsMap().put("type", st);
        operationBinding.execute();
        pass = (String) operationBinding.getResult();
        if (pass.equals("Y")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          (ADFModelUtils.resolvRsrc("MSG.2770")), null));        //duplicate not allowed
        }

    }


    public void valattrname(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String nt = (String) object;

        OperationBinding ob = ADFBeanUtils.getOperationBinding("namevalam");
        System.out.println(ob);
        ob.getParamsMap().put("name", nt);
        ob.execute();
        System.out.println("Error=" + ob.getErrors());
        pass = (String) ob.getResult();
        System.out.println(pass);
        if (pass.equals("Y")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          (ADFModelUtils.resolvRsrc("MSG.2770")), null));  //duplicate not allowed
        }


    }

    private void showPopup(RichPopup pop, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null) {
                String popupId = pop.getClientId(context);
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

    public void linkDedToOrgAL(ActionEvent actionEvent) {
        showPopup(dedLinkPopBinding, true);
    }

    public void setDedLinkPopBinding(RichPopup dedLinkPopBinding) {
        this.dedLinkPopBinding = dedLinkPopBinding;
    }

    public RichPopup getDedLinkPopBinding() {
        return dedLinkPopBinding;
    }

    public void linkToAttLnkAL(ActionEvent actionEvent) {
        if (dedBinding.getValue() != null && dedBinding.getValue().toString().length() > 0) {
            ADFBeanUtils.getOperationBinding("insertIntoAttLnk").execute();
        } else {
            FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2768"));   //Please select an attribute!
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(dedBinding.getClientId(), message);
        }
    }

    public void linkToAttLnkDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().toString().equalsIgnoreCase("ok")) {

            ADFBeanUtils.getOperationBinding("Commit").execute();

            FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.818"));    //Record Saved Successfully!
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    public void duplicateDeductionValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding binding = ADFBeanUtils.getOperationBinding("chkDuplicateDeduction");
            binding.getParamsMap().put("newAtt", object);
            binding.execute();
            if (binding.getErrors().isEmpty() && binding.getResult() != null) {
                String rslt = binding.getResult().toString();
                if (rslt.equalsIgnoreCase("Y")) {
                    String msg = ADFModelUtils.resolvRsrc("MSG.2769");                //Duplicate attribute not allowed!
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }
    }

    public void setDedBinding(RichSelectOneChoice dedBinding) {
        this.dedBinding = dedBinding;
    }

    public RichSelectOneChoice getDedBinding() {
        return dedBinding;
    }

    public void linkPopCancelListener(PopupCanceledEvent popupCanceledEvent) {
        System.out.println("inside cancel pop");
        ADFBeanUtils.getOperationBinding("refreshVO").execute();
        ADFBeanUtils.getOperationBinding("Rollback").execute();
    }
}


