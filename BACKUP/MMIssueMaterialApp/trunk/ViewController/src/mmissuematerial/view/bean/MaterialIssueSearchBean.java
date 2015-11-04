package mmissuematerial.view.bean;

import java.util.List;

import javax.faces.component.UIComponent;

import oracle.adf.model.BindingContext;

import oracle.adf.share.logging.ADFLogger;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class MaterialIssueSearchBean {
    private RichPanelFormLayout searchForm;
   private RichPanelGroupLayout searchPanel;
    private RichTable tableBind;
    private RichPanelGroupLayout searchPanelR;

    public MaterialIssueSearchBean() {
    }
    
    private static ADFLogger adflog= ADFLogger.createADFLogger(MaterialIssueSearchBean.class);
    public BindingContainer getBindings(){
      return  BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public String searchAction() {
        OperationBinding searchBind = getBindings().getOperationBinding("searchIssueAction");
        searchBind.execute();
        return null;
    }
    
    public String resetAction() {
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(fctx, searchPanelR);
       /*  OperationBinding opReset = getBindings().getOperationBinding("resetSearch");
        opReset.execute(); */
       
        OperationBinding searchBind = getBindings().getOperationBinding("resetIssueAction");
        searchBind.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tableBind);
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

    public void setSearchForm(RichPanelFormLayout searchForm) {
        this.searchForm = searchForm;
    }

    public RichPanelFormLayout getSearchForm() {
        return searchForm;
    }

   public void setSearchPanel(RichPanelGroupLayout searchPanel) {
        this.searchPanel = searchPanel;
    }

    public RichPanelGroupLayout getSearchPanel() {
        return searchPanel;
    } 

    public void setTableBind(RichTable tableBind) {
        this.tableBind = tableBind;
    }

    public RichTable getTableBind() {
        return tableBind;
    }

    public void setSearchPanelR(RichPanelGroupLayout searchPanelR) {
        this.searchPanelR = searchPanelR;
    }

    public RichPanelGroupLayout getSearchPanelR() {
        return searchPanelR;
    }
}
