package mmreceiptmtl.view.beans;

import java.io.Serializable;

import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class RcptSearchBean implements Serializable{
    private RichPanelFormLayout searchForm;
    private RichTable rcptSearchTable;

    public RcptSearchBean() {
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
                if(item.getId().endsWith("soc4")){}else{
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                 }
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
            } else if(item instanceof RichInputComboboxListOfValues){
                RichInputComboboxListOfValues input = (RichInputComboboxListOfValues)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
            }
        }
    }
    
    public String resetSearchAction() {
        resetValueInputItems(AdfFacesContext.getCurrentInstance(), searchForm);
        
       OperationBinding srchOp = getBindings().getOperationBinding("rcptResetSearch");
        srchOp.execute();
        return "reset";
    }

    public String searchRcptAction() {
        // Add event code here...
        OperationBinding srchOp = getBindings().getOperationBinding("rcptSearch");
        srchOp.execute();
      //  OperationBinding srchOp = getBindings().getOperationBinding("ExecuteWithParams");
       /*  srchOp.getParamsMap().put("bindOrgId", );
        srchOp.getParamsMap().put("bindCldId", );
        srchOp.getParamsMap().put("bindSlocId", );
         */
      /*   srchOp.getParamsMap().put("bindWhId", );
        srchOp.getParamsMap().put("bindRcptNo", );
        
        srchOp.getParamsMap().put("bindDtFrm", );
        srchOp.getParamsMap().put("bindDtTo", );
        
        srchOp.getParamsMap().put("bindFyId", );
        srchOp.getParamsMap().put("bindEoNm", );
        srchOp.getParamsMap().put("bindRcptStg", );
        srchOp.getParamsMap().put("bindRcptStat", );
        srchOp.getParamsMap().put("bindDocIdSrc", );
        srchOp.getParamsMap().put("bindRcptDocType", );
        
        srchOp.execute();
         */
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptSearchTable);
        return null;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }
    public void setSearchForm(RichPanelFormLayout searchForm) {
        this.searchForm = searchForm;
    }

    public RichPanelFormLayout getSearchForm() {
        return searchForm;
    }

    public void setRcptSearchTable(RichTable rcptSearchTable) {
        this.rcptSearchTable = rcptSearchTable;
    }

    public RichTable getRcptSearchTable() {
        return rcptSearchTable;
    }
}
