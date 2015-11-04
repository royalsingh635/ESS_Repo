package materialreturnnote.view.bean;

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

public class SearchMRNBean {
    private RichPanelGroupLayout searchPanel;

    /**
     * Material Return Note Managed Bean.
     * Contains all the business logic for Material Return functionalities.
     * @author BL
     * Dated -30/11/2013
     * */
    
    public SearchMRNBean() {
    }

    public String createAction() {
        // Add event code here...
        return "createMRN";
    }
    
    public BindingContainer getBindings(){
      return  BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public String searchAction() {
        OperationBinding searchBind = getBindings().getOperationBinding("searchMrnAction");
        searchBind.execute();
        return null;
    }
    
    public String resetAction() {
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(fctx, searchPanel);
         OperationBinding opReset = getBindings().getOperationBinding("resetSearch");
        opReset.execute(); 
       
       /*  OperationBinding searchBind = getBindings().getOperationBinding("searchMrnAction");
        searchBind.execute(); */
       // AdfFacesContext.getCurrentInstance().addPartialTarget(tableBind);
        return "reset";
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

    public void setSearchPanel(RichPanelGroupLayout searchPanel) {
        this.searchPanel = searchPanel;
    }

    public RichPanelGroupLayout getSearchPanel() {
        return searchPanel;
    }
}
