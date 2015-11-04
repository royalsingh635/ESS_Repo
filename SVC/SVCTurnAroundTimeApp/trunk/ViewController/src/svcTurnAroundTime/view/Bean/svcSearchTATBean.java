package svcTurnAroundTime.view.Bean;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import svcTurnAroundTime.view.Utils.ADFUtils;

public class svcSearchTATBean {
    
    OperationBinding ob = null;
    private RichInputListOfValues labelNmBind;
    private RichInputListOfValues locationNmBind;
    private RichInputListOfValues transEntityNmBind;
    private RichInputListOfValues dfectNmBind;
    private RichPanelGroupLayout searchLocation;
    private RichPanelGroupLayout searchEntityBind;
    private RichPanelGroupLayout searchDefectBind;

    public svcSearchTATBean() {
    }

    public void searchEntityACE(ActionEvent actionEvent) {
        // searchEntity
        ob = ADFUtils.findOperation("searchEntity");
        ob.execute();
    }

    public void resetEntityACE(ActionEvent actionEvent) {
        // resetEntity
        ob = ADFUtils.findOperation("resetEntity");
        ob.execute();
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
               resetValueInputItems(fctx, searchEntityBind); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(transEntityNmBind);
        
    }

    public void searchLocationACE(ActionEvent actionEvent) {
        // searchLocation
        ob = ADFUtils.findOperation("searchLocation");
        ob.execute();
    }

    public void resetLocationACE(ActionEvent actionEvent) {
        //resetLocation
        
        
        ob = ADFUtils.findOperation("resetLocation");
        ob.execute();
        
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
               resetValueInputItems(fctx, searchLocation);
        AdfFacesContext.getCurrentInstance().addPartialTarget(labelNmBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(locationNmBind);                                                                 
    }

    public void searchDefects(ActionEvent actionEvent) {
        // searchDefects
        ob = ADFUtils.findOperation("searchDefects");
        ob.execute();
    }

    public void resetDefects(ActionEvent actionEvent) {
        // resetDefects
        ob = ADFUtils.findOperation("resetDefects");
        ob.execute();
        
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
               resetValueInputItems(fctx, searchDefectBind); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(dfectNmBind);
    }

    public void setLabelNmBind(RichInputListOfValues labelNmBind) {
        this.labelNmBind = labelNmBind;
    }

    public RichInputListOfValues getLabelNmBind() {
        return labelNmBind;
    }

    public void setLocationNmBind(RichInputListOfValues locationNmBind) {
        this.locationNmBind = locationNmBind;
    }

    public RichInputListOfValues getLocationNmBind() {
        return locationNmBind;
    }

    public void setTransEntityNmBind(RichInputListOfValues transEntityNmBind) {
        this.transEntityNmBind = transEntityNmBind;
    }

    public RichInputListOfValues getTransEntityNmBind() {
        return transEntityNmBind;
    }

    public void setDfectNmBind(RichInputListOfValues dfectNmBind) {
        this.dfectNmBind = dfectNmBind;
    }

    public RichInputListOfValues getDfectNmBind() {
        return dfectNmBind;
    }

    public void setSearchLocation(RichPanelGroupLayout searchLocation) {
        this.searchLocation = searchLocation;
    }

    public RichPanelGroupLayout getSearchLocation() {
        return searchLocation;
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

    public void setSearchEntityBind(RichPanelGroupLayout searchEntityBind) {
        this.searchEntityBind = searchEntityBind;
    }

    public RichPanelGroupLayout getSearchEntityBind() {
        return searchEntityBind;
    }

    public void setSearchDefectBind(RichPanelGroupLayout searchDefectBind) {
        this.searchDefectBind = searchDefectBind;
    }

    public RichPanelGroupLayout getSearchDefectBind() {
        return searchDefectBind;
    }

    /*     public void LocationIdVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    } */
}
