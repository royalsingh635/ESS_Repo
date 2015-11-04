package mmscrapsales.view.bean;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class ScrpSrchBean {
    private RichPanelGroupLayout panelGroupBind;
    private RichInputText scrapNoBind;

    public ScrpSrchBean() {
    }
    public BindingContainer getBindings(){
      return  BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void searchActionListener(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding obind = getBindings().getOperationBinding("search");
        obind.execute();
    }

    public void resetActionListner(ActionEvent actionEvent) {
        // Add event code here...
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(fctx, panelGroupBind);
        
        OperationBinding obind = getBindings().getOperationBinding("resetSrch");
        obind.execute();
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

    public void setPanelGroupBind(RichPanelGroupLayout panelGroupBind) {
        this.panelGroupBind = panelGroupBind;
    }

    public RichPanelGroupLayout getPanelGroupBind() {
        return panelGroupBind;
    }

    public void setScrapNoBind(RichInputText scrapNoBind) {
        this.scrapNoBind = scrapNoBind;
    }

    public RichInputText getScrapNoBind() {
        return scrapNoBind;
    }
}
