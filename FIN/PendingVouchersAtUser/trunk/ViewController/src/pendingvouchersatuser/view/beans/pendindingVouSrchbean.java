package pendingvouchersatuser.view.beans;

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

public class pendindingVouSrchbean {
    private RichInputText vouIdBinding;
    private RichInputDate vouDtBind;
    private RichInputListOfValues usrNmBind;
    private RichInputDate fwdDtBind;
    private RichInputText rmkBind;
    private RichPanelGroupLayout searchPanel;

    public pendindingVouSrchbean() {
    }
    public BindingContainer getBindings() {
       return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void SearchActionListner(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding operationBinding =getBindings().getOperationBinding("searchAction");
        operationBinding.execute();
    }

    public void resetActionListner(ActionEvent actionEvent) {
        // Add event code here...
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(fctx, searchPanel);
        OperationBinding operationBinding = getBindings().getOperationBinding("resetAction");
        operationBinding.execute();
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

    public void setVouIdBinding(RichInputText vouIdBinding) {
        this.vouIdBinding = vouIdBinding;
    }

    public RichInputText getVouIdBinding() {
        return vouIdBinding;
    }

    public void setVouDtBind(RichInputDate vouDtBind) {
        this.vouDtBind = vouDtBind;
    }

    public RichInputDate getVouDtBind() {
        return vouDtBind;
    }

    public void setUsrNmBind(RichInputListOfValues usrNmBind) {
        this.usrNmBind = usrNmBind;
    }

    public RichInputListOfValues getUsrNmBind() {
        return usrNmBind;
    }

    public void setFwdDtBind(RichInputDate fwdDtBind) {
        this.fwdDtBind = fwdDtBind;
    }

    public RichInputDate getFwdDtBind() {
        return fwdDtBind;
    }

    public void setRmkBind(RichInputText rmkBind) {
        this.rmkBind = rmkBind;
    }

    public RichInputText getRmkBind() {
        return rmkBind;
    }

    public void setSearchPanel(RichPanelGroupLayout searchPanel) {
        this.searchPanel = searchPanel;
    }

    public RichPanelGroupLayout getSearchPanel() {
        return searchPanel;
    }
}
