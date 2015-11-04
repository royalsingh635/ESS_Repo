package mmstockswapapp.view.beans;

import java.util.List;

import javax.faces.component.UIComponent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class MmStockSwapSearchBean {
    private RichPanelFormLayout panelGrpBind;

    public MmStockSwapSearchBean() {
    }
      
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public String searchAction() {
        // Add event code here...
        OperationBinding srchsvc = getBindings().getOperationBinding("searchStockSwap");
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

    public String resetAction() {
        // Add event code here...
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(fctx, panelGrpBind);
        OperationBinding rstObind = getBindings().getOperationBinding("resetStockSwap");
         rstObind.execute();
        return null;
       // return null;
    }

    public void setPanelGrpBind(RichPanelFormLayout panelGrpBind) {
        this.panelGrpBind = panelGrpBind;
    }

    public RichPanelFormLayout getPanelGrpBind() {
        return panelGrpBind;
    }
}
