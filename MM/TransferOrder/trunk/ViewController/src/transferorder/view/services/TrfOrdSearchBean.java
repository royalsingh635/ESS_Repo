package transferorder.view.services;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class TrfOrdSearchBean {
    private RichInputListOfValues trfNoBindVal;
    private RichSelectOneChoice trfSrcTypeBindVal;
    private RichInputDate fromDtBindVal;
    private RichInputDate toDtBindVal;
    private RichPanelFormLayout searchForm;

    public TrfOrdSearchBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    public void searchTrfOrdActionListener(ActionEvent actionEvent) {
        System.out.println("in search page bean ");
       // System.out.println(trfNoBindVal);
        System.out.println("trfNoBindVal = "+trfNoBindVal.getValue()+" trfSrcTypeBindVal = "+trfSrcTypeBindVal.getValue()+" fromDtBindVal = "+fromDtBindVal+" toDtBindVal = "+toDtBindVal);
        OperationBinding op1= getBindings().getOperationBinding("searchResultTrfOrd");
        op1.execute();
    }

    public void setTrfNoBindVal(RichInputListOfValues trfNoBindVal) {
        this.trfNoBindVal = trfNoBindVal;
    }

    public RichInputListOfValues getTrfNoBindVal() {
        return trfNoBindVal;
    }

    public void setTrfSrcTypeBindVal(RichSelectOneChoice trfSrcTypeBindVal) {
        this.trfSrcTypeBindVal = trfSrcTypeBindVal;
    }

    public RichSelectOneChoice getTrfSrcTypeBindVal() {
        return trfSrcTypeBindVal;
    }

    public void setFromDtBindVal(RichInputDate fromDtBindVal) {
        this.fromDtBindVal = fromDtBindVal;
    }

    public RichInputDate getFromDtBindVal() {
        return fromDtBindVal;
    }

    public void setToDtBindVal(RichInputDate toDtBindVal) {
        this.toDtBindVal = toDtBindVal;
    }

    public RichInputDate getToDtBindVal() {
        return toDtBindVal;
    }

    public void setSearchForm(RichPanelFormLayout searchForm) {
        this.searchForm = searchForm;
    }

    public RichPanelFormLayout getSearchForm() {
        return searchForm;
    }

    public String resetAction() {
        // Add event code here...
        AdfFacesContext ctx = AdfFacesContext.getCurrentInstance();
        resetValues(ctx,searchForm);   
        OperationBinding op1= getBindings().getOperationBinding("resetAction");
        op1.execute();
        return null;
    }
    
    
    public void resetValues(AdfFacesContext ctx,UIComponent component)
    {
        List<UIComponent> items = component.getChildren();
        for(UIComponent item:items)
        {
          //  resetValues(ctx,component);
            
        if(item instanceof RichInputDate)
        {
            RichInputDate itm = (RichInputDate)item;   
          if(!itm.isDisabled())
          {
              itm.resetValue();
              itm.setValue("");
               ctx.addPartialTarget(itm);
           }
        }
            
        if(item instanceof RichInputListOfValues)
        {
            RichInputListOfValues itm = (RichInputListOfValues)item;   
            if(!itm.isDisabled())
            {
              itm.resetValue();
              itm.setValue("");
                ctx.addPartialTarget(itm);
            }
        }
            
            if(item instanceof RichSelectOneChoice)
            {
                RichSelectOneChoice itm = (RichSelectOneChoice)item;   
                if(!itm.isDisabled())
                {
                  itm.resetValue();
                  itm.setValue("");
                    ctx.addPartialTarget(itm);
                }
            }
        
        }
    }
    
    public List getSuggestions(String input) {
     
        // create a new list to hold matching items
        List<SelectItem> items = new ArrayList<SelectItem>();
        OperationBinding binding = getBindings().getOperationBinding("getSuggestedItemDesc");
        binding.getParamsMap().put("itmStr", input);
        binding.execute();
        Object object = binding.getResult();
        System.out.println("Item Query Executed");
        if (object != null) {
            for (String item : (ArrayList<String>)object) {
                items.add(new SelectItem(item));
            }
        }
        return items;
    }
}
