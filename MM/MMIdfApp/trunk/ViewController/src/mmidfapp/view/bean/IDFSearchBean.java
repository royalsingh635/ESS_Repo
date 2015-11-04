package mmidfapp.view.bean;

import java.util.List;

import javax.faces.component.UIComponent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class IDFSearchBean {
    private RichPanelGroupLayout panelGrpBind;

    public IDFSearchBean() {
    }
    
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String SearchAction() {
        // Add event code here...
        
        OperationBinding srchsvc = getBindings().getOperationBinding("searchIDF");
        srchsvc.execute();
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

    public String ResetAction() {
        // Add event code here...
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(fctx, panelGrpBind);
        OperationBinding rstObind = getBindings().getOperationBinding("resetIDF");
        rstObind.execute();
        return null;
    }

    public void setPanelGrpBind(RichPanelGroupLayout panelGrpBind) {
        this.panelGrpBind = panelGrpBind;
    }

    public RichPanelGroupLayout getPanelGrpBind() {
        return panelGrpBind;
    }
    
    
}
