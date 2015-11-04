package mmkitproduction.view.beans;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class SearchBean {
    private RichInputText optItmBind;
    private RichInputListOfValues itmDescBind;
    private RichInputText whBind;
    private RichInputListOfValues kitProdNo;
    private RichSelectOneChoice kitActionBind;
    private RichSelectOneChoice whBind1;
    private RichInputListOfValues getehtrans;

    public SearchBean() {
    }

    public void searchActionListner(ActionEvent actionEvent) {
        // Add event code here...
        String WhId="";
        String itmId="";
        String KitProdNo="";
        String kitActionType="";
        if(getOptItmBind().getValue()!=null && getOptItmBind().getValue()!=""){
           itmId= getOptItmBind().getValue().toString(); 
        }
        if(getWhBind().getValue()!=null && getWhBind().getValue()!="")
        {
          WhId= getWhBind().getValue().toString();
        }
        if(getKitProdNo().getValue()!=null) {
            KitProdNo=getKitProdNo().getValue().toString();
        }
        if(getKitActionBind().getValue()!=null) {
            kitActionType=getKitActionBind().getValue().toString();
        }
          OperationBinding obind=getBindings().getOperationBinding("SearchCriteria");
          obind.getParamsMap().put("ItmId",itmId);
          obind.getParamsMap().put("KitProdNo",KitProdNo);
          obind.getParamsMap().put("WhId",WhId);
          obind.getParamsMap().put("KitActionType", kitActionType);
          obind.execute(); 
    }
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void setOptItmBind(RichInputText optItmBind) {
        this.optItmBind = optItmBind;
    }

    public RichInputText getOptItmBind() {
        return optItmBind;
    }

    public void setItmDescBind(RichInputListOfValues itmDescBind) {
        this.itmDescBind = itmDescBind;
    }

    public RichInputListOfValues getItmDescBind() {
        return itmDescBind;
    }

    public void setWhBind(RichInputText whBind) {
        this.whBind = whBind;
    }

    public RichInputText getWhBind() {
        return whBind;
    }

    public void setKitProdNo(RichInputListOfValues kitProdNo) {
        this.kitProdNo = kitProdNo;
    }

    public RichInputListOfValues getKitProdNo() {
        return kitProdNo;
    }

    public void resetActionListner(ActionEvent actionEvent) {
        // Add event code here...
        getKitProdNo().setValue(null);
        //getWhBind().setValue(null);
        getItmDescBind().setValue(null);
        getehtrans.setValue(null);
        getKitActionBind().setValue(null);
        OperationBinding obind=getBindings().getOperationBinding("ResetAction");
        obind.execute();
        searchActionListner(actionEvent);
    }

    public void setKitActionBind(RichSelectOneChoice kitActionBind) {
        this.kitActionBind = kitActionBind;
    }

    public RichSelectOneChoice getKitActionBind() {
        return kitActionBind;
    }

    public void setWhBind1(RichSelectOneChoice whBind1) {
        this.whBind1 = whBind1;
    }

    public RichSelectOneChoice getWhBind1() {
        return whBind1;
    }

    public void setGetehtrans(RichInputListOfValues getehtrans) {
        this.getehtrans = getehtrans;
    }

    public RichInputListOfValues getGetehtrans() {
        return getehtrans;
    }
}
