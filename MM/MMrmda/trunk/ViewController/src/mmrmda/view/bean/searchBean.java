package mmrmda.view.bean;

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
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class searchBean {
    private RichPanelFormLayout searchPanelR;
    private RichTable tableBind;
    private RichInputDate rcptDtPgBind;

    public searchBean() {
    }

    public String searchAction() {
        // Add event code here...
        
        ADFLogger logger = ADFLogger.createADFLogger(searchBean.class);
        
        OperationBinding operationBinding =getBindings().getOperationBinding("searchAction");
        operationBinding.getParamsMap().put("VCName", "MmRmdaVOCriteria");
        operationBinding.getParamsMap().put("VOName", "MmRmda1");
        operationBinding.execute();
        
     
        
        return null;
    }
    public String resetAction() {
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(fctx, searchPanelR);
        //rcptDtPgBind.setValue(null);
        OperationBinding operationBinding =getBindings().getOperationBinding("resetIssueAction");
        operationBinding.getParamsMap().put("VCName", "MmRmdaVOCriteria");
        operationBinding.getParamsMap().put("VOName", "MmRmda1");
        operationBinding.execute();
       
      /*   OperationBinding searchBind = getBindings().getOperationBinding("resetIssueAction");
        searchBind.execute(); */
       AdfFacesContext.getCurrentInstance().addPartialTarget(tableBind);
        //return "reset";
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

    public BindingContainer getBindings()
    {return BindingContext.getCurrent().getCurrentBindingsEntry();}

    public void setSearchPanelR(RichPanelFormLayout searchPanelR) {
        this.searchPanelR = searchPanelR;
    }

    public RichPanelFormLayout getSearchPanelR() {
        return searchPanelR;
    }

    public void setTableBind(RichTable tableBind) {
        this.tableBind = tableBind;
    }

    public RichTable getTableBind() {
        return tableBind;
    }

    public void setRcptDtPgBind(RichInputDate rcptDtPgBind) {
        this.rcptDtPgBind = rcptDtPgBind;
    }

    public RichInputDate getRcptDtPgBind() {
        return rcptDtPgBind;
    }
}
