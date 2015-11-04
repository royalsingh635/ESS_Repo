package svcservicecontract.view.bean;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
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

public class SrchScBean {
    private RichPanelGroupLayout panelGrpBind;

    public SrchScBean() {
    }
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void srcActionListener(ActionEvent actionEvent) {
        // Add event code here...
       OperationBinding srchsvc = getBindings().getOperationBinding("srchSvcSc");
       srchsvc.execute();
    }
    public void resetValueInputItems(AdfFacesContext adfFacesContext, UIComponent component) {
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
    public void resetActionListener(ActionEvent actionEvent) {
        // Add event code here...
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(fctx, panelGrpBind);
        OperationBinding rstObind = getBindings().getOperationBinding("rstSvcVo");
         rstObind.execute();
    }

    public void setPanelGrpBind(RichPanelGroupLayout panelGrpBind) {
        this.panelGrpBind = panelGrpBind;
    }

    public RichPanelGroupLayout getPanelGrpBind() {
        return panelGrpBind;
    }
}
