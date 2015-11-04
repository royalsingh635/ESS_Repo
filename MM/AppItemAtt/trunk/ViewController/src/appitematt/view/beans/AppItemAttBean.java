package appitematt.view.beans;

import adf.utils.model.ADFModelUtils;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.ADFmMessageBundle;
import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class AppItemAttBean {
    BindingContext bindingctx = BindingContext.getCurrent();
    BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
    private RichSelectBooleanCheckbox bindSqlCheck;
    private RichSelectBooleanCheckbox bindGrpCheck;
    private String mode="V";
    private String valMode="V";
    private String rowSel = "single";
    private RichInputText bindAttTypeVar;
    private String childCntrl = "EN";
    private String parentCntrl = "EN";
    private RichPopup popUpBindVar;
    private RichInputText attPrefixBind;
    private RichTable itmAttTypeTableBind;
    private RichPanelGroupLayout panelSearch;
    private RichPopup attTypeDeletePopupBind;

    public AppItemAttBean() {
    }

    public BindingContainer getBindings(){
      return  BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public String createNewAttTypeAction() {
        // Add event code here...
        OperationBinding createOpBAddress = bindings.getOperationBinding("CreateInsert");
        createOpBAddress.execute();
        setMode("A");
        setChildCntrl("DS");   //Disable child control
        itmAttTypeTableBind.setRowSelection("none");
        return null;
    }


    public void saveAttTypeAction(ActionEvent actionEvent) {
        // Add event code here...
        Object obj=bindAttTypeVar.getValue();
        if(obj != null)
        {
            OperationBinding createOpBAddress = bindings.getOperationBinding("isAttTypeExist");
            createOpBAddress.getParamsMap().put("attType", bindAttTypeVar.getValue());
            createOpBAddress.execute();
            
            Boolean b = (Boolean)createOpBAddress.getResult();
            if(b)
            {
//                Attribute Type Already Exist
                String s=ADFModelUtils.resolvRsrc("MSG.2026");
                FacesMessage message = new FacesMessage(s);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                return;
            }
            String retV="N";
            OperationBinding isAttPrefix = bindings.getOperationBinding("isAttPrefixReq");
            isAttPrefix.execute();
            if(isAttPrefix.getResult()!=null){
                retV=isAttPrefix.getResult().toString();
            }
            if("Y".equalsIgnoreCase(retV)){
//                Attribute Prefix Is Required
                String s=ADFModelUtils.resolvRsrc("MSG.2027");
                FacesMessage message = new FacesMessage(s);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(attPrefixBind.getClientId(), message);
                return;
            }
            
            
            bindings.getOperationBinding("doCommit").execute();
            bindings.getOperationBinding("Commit").execute();
            setMode("V");
            setChildCntrl("EN");
            itmAttTypeTableBind.setRowSelection("single");
        }
    }

    public void resetAction(ActionEvent actionEvent) {
        // Add event code here...
        bindings.getOperationBinding("Rollback").execute();
        setMode("V");
        setChildCntrl("EN");   //Enable Child Control
    itmAttTypeTableBind.setRowSelection("single");
    }

    public String createAttValAction() {
        // Add event code here...
        OperationBinding createOpBAddress = bindings.getOperationBinding("CreateInsert1");
        createOpBAddress.execute();
        setValMode("A");
        setParentCntrl("DS");
        itmAttTypeTableBind.setRowSelection("none");
        return "setPrefix";
    }

    public String saveAttValAction() {
        // Add event code here...
        bindings.getOperationBinding("doBeforeAttValCommit").execute();
        bindings.getOperationBinding("Commit").execute();
        setValMode("V");
        setParentCntrl("EN");
        itmAttTypeTableBind.setRowSelection("single");
        return null;
    }

    public void setBindSqlCheck(RichSelectBooleanCheckbox bindSqlCheck) {
        this.bindSqlCheck = bindSqlCheck;
    }

    public RichSelectBooleanCheckbox getBindSqlCheck() {
        return bindSqlCheck;
    }

    public void setBindGrpCheck(RichSelectBooleanCheckbox bindGrpCheck) {
        this.bindGrpCheck = bindGrpCheck;
    }

    public RichSelectBooleanCheckbox getBindGrpCheck() {
        return bindGrpCheck;
    }

    public void SequenceVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(valueChangeEvent.getNewValue().equals(false))
         {bindGrpCheck.setValue(false);}
    }

    public void GroupVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        
       if(bindSqlCheck.getValue().equals(false))
       {bindGrpCheck.setValue(false);
        }

    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public String editAttTypeAction() {
        // Add event code here...
        
       /*  OperationBinding opr = bindings.getOperationBinding("getAttValRowCount");
        opr.execute();
        Integer count = (Integer)opr.getResult();
        if(count >0)
        {return null;} */
        
        setMode("E");
        setChildCntrl("DS");
        itmAttTypeTableBind.setRowSelection("none");
        return null;
    }

    public String editAttValueAction() {
        // Add event code here...
        setValMode("E");
        setRowSel("none");
        setParentCntrl("DS");
        itmAttTypeTableBind.setRowSelection("none");
        return null;
    }

    public void setValMode(String valMode) {
        this.valMode = valMode;
    }

    public String getValMode() {
        return valMode;
    }

    public void resetAttValAction(ActionEvent actionEvent) {
        // Add event code here...
        bindings.getOperationBinding("Rollback").execute();
        setValMode("V");
        setRowSel("single");
        itmAttTypeTableBind.setRowSelection("single");
        setParentCntrl("EN");
    }
    
    public void delete()
    {
        OperationBinding opr = bindings.getOperationBinding("checkAttUseInItm");
        opr.execute();
        Integer count = (Integer)opr.getResult();
        if(count > 0)
        {
//            Can't delete it,Attribute Value used in Item Profile."
            String s=ADFModelUtils.resolvRsrc("MSG.2029");
            FacesMessage message = new FacesMessage(s);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        else
        {
            bindings.getOperationBinding("Delete").execute(); 
            //bindings.getOperationBinding("Execute").execute(); 
        }
    }

    public void deleteAttributeValue(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding opr = bindings.getOperationBinding("checkAttUseInItm");
        opr.execute();
        Integer count = (Integer)opr.getResult();
        if(count > 0)
        {
//            Can't delete it,Attribute Value used in Item Profile."
            String s=ADFModelUtils.resolvRsrc("MSG.2029");
            FacesMessage message = new FacesMessage(s);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        else
        {
              showPopup(popUpBindVar,true);
        }
        }

    public void setRowSel(String rowSel) {
        this.rowSel = rowSel;
    }

    public String getRowSel() {
        return rowSel;
    }

    public void setBindAttTypeVar(RichInputText bindAttTypeVar) {
        this.bindAttTypeVar = bindAttTypeVar;
    }

    public RichInputText getBindAttTypeVar() {
        return bindAttTypeVar;
    }

    public void setChildCntrl(String childCntrl) {
        this.childCntrl = childCntrl;
    }

    public String getChildCntrl() {
        return childCntrl;
    }

    public void setParentCntrl(String parentCntrl) {
        this.parentCntrl = parentCntrl;
    }

    public String getParentCntrl() {
        return parentCntrl;
    }

    public void dialogListenerAction(DialogEvent dialogEvent) {
        // Add event code here...
        
        String str = dialogEvent.getOutcome().name();
       // System.out.println("Str : "+str);
        if(str.equals("yes"))
        {    //delete();
             bindings.getOperationBinding("Delete").execute(); 
             bindings.getOperationBinding("Commit").execute();
         }
        
    }

    public void setPopUpBindVar(RichPopup popUpBindVar) {
        this.popUpBindVar = popUpBindVar;
    }

    public RichPopup getPopUpBindVar() {
        return popUpBindVar;
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
        ExtendedRenderKitService erks = Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
        erks.addScript(context, script.toString());
        }
        }
    } catch (Exception e) {
    throw new RuntimeException(e);
    }
    }

    public void setAttPrefixBind(RichInputText attPrefixBind) {
        this.attPrefixBind = attPrefixBind;
    }

    public RichInputText getAttPrefixBind() {
        return attPrefixBind;
    }

    public void setItmAttTypeTableBind(RichTable itmAttTypeTableBind) {
        this.itmAttTypeTableBind = itmAttTypeTableBind;
    }

    public RichTable getItmAttTypeTableBind() {
        return itmAttTypeTableBind;
    }

    public String searchAction() {
        OperationBinding opReset = getBindings().getOperationBinding("searchAtt");
        opReset.execute();
        return null;
    }

    public String resetAction() {
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(fctx, panelSearch);
        OperationBinding opReset = getBindings().getOperationBinding("resetSearch");
        opReset.execute();
        return null;
    }
    private void resetValueInputItems(AdfFacesContext adfFacesContext, UIComponent component) {
        List<UIComponent> items = component.getChildren();
        for (UIComponent item : items) {

            resetValueInputItems(adfFacesContext, item);

            if (item instanceof RichInputText) {
                RichInputText input = (RichInputText)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputDate) {
                RichInputDate input = (RichInputDate)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichSelectOneChoice) {
                RichSelectOneChoice input = (RichSelectOneChoice)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputListOfValues) {
                RichInputListOfValues input = (RichInputListOfValues)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            }

        }
    }

    public void setPanelSearch(RichPanelGroupLayout panelSearch) {
        this.panelSearch = panelSearch;
    }

    public RichPanelGroupLayout getPanelSearch() {
        return panelSearch;
    }

    public String deleteAttributeTypeAction() {
       showPopup(attTypeDeletePopupBind, true);
        return null;
    }

    public void deleteAttTypeDialogListener(DialogEvent dialogEvent) {
        if(dialogEvent.getOutcome().name().equals("yes")){
            bindings.getOperationBinding("Delete1").execute(); 
            bindings.getOperationBinding("Commit").execute();
        }
    }

    public void setAttTypeDeletePopupBind(RichPopup attTypeDeletePopupBind) {
        this.attTypeDeletePopupBind = attTypeDeletePopupBind;
    }

    public RichPopup getAttTypeDeletePopupBind() {
        return attTypeDeletePopupBind;
    }

    public void attValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object!=null){
            String value = object.toString();
            OperationBinding opReset = getBindings().getOperationBinding("isDuplicateValue");
            opReset.getParamsMap().put("value", value);
            opReset.execute();
            String retVal ="N";
            if(opReset.getResult()!=null){
                retVal=opReset.getResult().toString();
            }
            if("Y".equalsIgnoreCase(retVal)){
//                Duplicate Value Found.
                String s=ADFModelUtils.resolvRsrc("MSG.1156");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              s, null));
            }
        }
    }

    public void attCodeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object!=null){
            String code = object.toString();
            OperationBinding opReset = getBindings().getOperationBinding("isDuplicateCode");
            opReset.getParamsMap().put("code", code);
            opReset.execute();
            String retVal ="N";
            if(opReset.getResult()!=null){
                retVal=opReset.getResult().toString();
            }
            if("Y".equalsIgnoreCase(retVal)){
//                "Duplicate Code Found."
                String s=ADFModelUtils.resolvRsrc("MSG.2030");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,s, null));
            }
        }
    }
}
