package svccalltracing.view.bean;

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


public class CallTraceBean {
    private RichPanelGroupLayout searchGrpBind;
    public String mode="V";
    public String emode="V";

    public void setEmode(String emode) {
        this.emode = emode;
    }

    public String getEmode() {
        return emode;
    }


    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public CallTraceBean() {
    }
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void AddActionListener(ActionEvent actionEvent) {
        OperationBinding obind=getBindings().getOperationBinding("CreateInsert");
        obind.execute();
        if(obind.getErrors().isEmpty()){
        mode="C";
            emode="C";

        }
    }

    public void editActionListener(ActionEvent actionEvent) {
        //if(obind.getErrors().isEmpty()){
        mode="C";
        emode="V";
        //}
    }

    public void saveActionListner(ActionEvent actionEvent) {
        //System.out.println("sequence no save function ");
        if(emode.equalsIgnoreCase("C")){
        OperationBinding srno=getBindings().getOperationBinding("setSrNo");
        srno.execute();
        }
        OperationBinding obind=getBindings().getOperationBinding("Commit");
        obind.execute();
        if(obind.getErrors().isEmpty()){
        mode="V";
        }
         
    }

    public void cancelActionListener(ActionEvent actionEvent) {
        OperationBinding obind=getBindings().getOperationBinding("Rollback");
        obind.execute();
        if(obind.getErrors().isEmpty()){
        mode="V";
        }
    }

    public void searchActionListener(ActionEvent actionEvent) {
        OperationBinding obind=getBindings().getOperationBinding("searchAction");
        obind.execute();
    }

    public void resetActionListener(ActionEvent actionEvent) {
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(fctx, searchGrpBind);
        OperationBinding obind=getBindings().getOperationBinding("resetAction");
        obind.execute();
    }

    public void setSearchGrpBind(RichPanelGroupLayout searchGrpBind) {
        this.searchGrpBind = searchGrpBind;
    }

    public RichPanelGroupLayout getSearchGrpBind() {
        return searchGrpBind;
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
}
