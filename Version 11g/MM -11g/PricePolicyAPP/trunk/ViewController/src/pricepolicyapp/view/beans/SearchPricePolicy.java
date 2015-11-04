package pricepolicyapp.view.beans;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class SearchPricePolicy {
    private RichPanelGroupLayout searchPanelR;
    private RichTable tableBind;

    public SearchPricePolicy() {
    }
        public BindingContainer getBindings(){
          return  BindingContext.getCurrent().getCurrentBindingsEntry();
        }
    public void search(ActionEvent actionEvent) {
        OperationBinding searchBind = getBindings().getOperationBinding("searchPolicy");
        searchBind.execute();
    }

    public String reset() {
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(fctx, searchPanelR);
        /*  OperationBinding opReset = getBindings().getOperationBinding("resetSearch");
        opReset.execute(); */
        
        OperationBinding searchBind = getBindings().getOperationBinding("resetPolicy");
        searchBind.execute();
      //  AdfFacesContext.getCurrentInstance().addPartialTarget(tableBind);
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

    public void setSearchPanelR(RichPanelGroupLayout searchPanelR) {
        this.searchPanelR = searchPanelR;
    }

    public RichPanelGroupLayout getSearchPanelR() {
        return searchPanelR;
    }

    public void setTableBind(RichTable tableBind) {
        this.tableBind = tableBind;
    }

    public RichTable getTableBind() {
        return tableBind;
    }
}

