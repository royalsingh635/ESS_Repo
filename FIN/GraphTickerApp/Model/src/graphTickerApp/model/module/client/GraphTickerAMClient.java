package graphTickerApp.model.module.client;

import graphTickerApp.model.module.common.GraphTickerAM;

import oracle.jbo.client.remote.ApplicationModuleImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Oct 22 18:43:16 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class GraphTickerAMClient extends ApplicationModuleImpl implements GraphTickerAM {
    /**
     * This is the default constructor (do not remove).
     */
    public GraphTickerAMClient() {
    }

    public Integer on_load_page() {
        Object _ret = this.riInvokeExportedMethod(this,"on_load_page",null,null);
        return (Integer)_ret;
    }

    public void setBindVarValue(Integer SlocId, Integer UserId, String Org, String CldId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"setBindVarValue",new String [] {"java.lang.Integer","java.lang.Integer","java.lang.String","java.lang.String"},new Object[] {SlocId, UserId, Org, CldId});
        return;
    }


    public void bankInflowOoutflow() {
        Object _ret = this.riInvokeExportedMethod(this,"bankInflowOoutflow",null,null);
        return;
    }

    public void cashInflowOoutflow() {
        Object _ret = this.riInvokeExportedMethod(this,"cashInflowOoutflow",null,null);
        return;
    }

    public void refreshSupplier() {
        Object _ret = this.riInvokeExportedMethod(this,"refreshSupplier",null,null);
        return;
    }

    public void setTkrDataBind() {
        Object _ret = this.riInvokeExportedMethod(this,"setTkrDataBind",null,null);
        return;
    }

    public void topCustomer() {
        Object _ret = this.riInvokeExportedMethod(this,"topCustomer",null,null);
        return;
    }
}
