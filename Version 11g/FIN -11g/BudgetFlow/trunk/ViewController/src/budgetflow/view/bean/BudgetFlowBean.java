package budgetflow.view.bean;

import java.util.List;

import javax.faces.component.UIComponent;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

public class BudgetFlowBean {
    private RichPanelFormLayout searchForm;
    private RichSelectOneChoice fyId;
    private RichSelectOneChoice monId;

    public BudgetFlowBean() {
    }

    public String resetBdgtFlwAction() {
        setFyId(null);
        setMonId(null);
        resetValueInputItems(AdfFacesContext.getCurrentInstance(), searchForm);
        return "reset";
    }

    private void resetValueInputItems(AdfFacesContext adfFacesContext, UIComponent component) {
        /** Get list of all components from search panel */
        List<UIComponent> items = component.getChildren();
        for (UIComponent item : items) {

            resetValueInputItems(adfFacesContext, item);
            /** Check if Input text , reset its value*/
            if (item instanceof RichInputText) {
                RichInputText input = (RichInputText)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
                /** Check if RichInputDate , reset its value*/
            } else if (item instanceof RichInputDate) {
                RichInputDate input = (RichInputDate)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }

                /* Check if SelectOneChoice , reset its value*/
            } else if (item instanceof RichSelectOneChoice) {
                RichSelectOneChoice input = (RichSelectOneChoice)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }

            }
        }
    }


    public void setSearchForm(RichPanelFormLayout searchForm) {
        this.searchForm = searchForm;
    }

    public RichPanelFormLayout getSearchForm() {
        return searchForm;
    }

    public void setFyId(RichSelectOneChoice fyId) {
        this.fyId = fyId;
    }

    public RichSelectOneChoice getFyId() {
        return fyId;
    }

    public void setMonId(RichSelectOneChoice monId) {
        this.monId = monId;
    }

    public RichSelectOneChoice getMonId() {
        return monId;
    }
}
