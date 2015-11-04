package accountspayable.view;


import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;


import oracle.adf.view.faces.bi.component.graph.UIGraph;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;


public class accountsPayable {
    private Integer count = getcount();
    private UIGraph accountsPayablePgBind;
    private RichTable accPayableTablePgBind;

    public accountsPayable() {

    }


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    public void setCount(Integer count) {
        this.count = count;
    }
    private Integer getcount(){
        Object ret=getBindings().getOperationBinding("on_load_page").execute();
        if(ret!=null){
        return (Integer)ret;
        }
        else{
          return 1;
        }
        
    }
    public Integer getCount() {
        
        return count;
    }

    public void FilterTypeVCL(ValueChangeEvent vce) {
        if(vce != null){
            //System.out.println("Radio button value is "+vce.getNewValue());
            
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =bindings.getOperationBinding("getFilteredRowsOnVCL");
            operationBinding.getParamsMap().put("type", vce.getNewValue().toString());
            operationBinding.execute();
            
            AdfFacesContext.getCurrentInstance().addPartialTarget(accPayableTablePgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(accountsPayablePgBind);
        }
    }

    public void setAccountsPayablePgBind(UIGraph accountsPayablePgBind) {
        this.accountsPayablePgBind = accountsPayablePgBind;
    }

    public UIGraph getAccountsPayablePgBind() {
        return accountsPayablePgBind;
    }

    public void setAccPayableTablePgBind(RichTable accPayableTablePgBind) {
        this.accPayableTablePgBind = accPayableTablePgBind;
    }

    public RichTable getAccPayableTablePgBind() {
        return accPayableTablePgBind;
    }
}
