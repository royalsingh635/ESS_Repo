package appEoAddress.model.service.client;

import appEoAddress.model.service.common.AppEoAddressAM;

import oracle.jbo.client.remote.ApplicationModuleImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Oct 23 10:43:41 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppEoAddressAMClient extends ApplicationModuleImpl implements AppEoAddressAM {
    /**
     * This is the default constructor (do not remove).
     */
    public AppEoAddressAMClient() {
    }


    public String getModeToEntity() {
        Object _ret = this.riInvokeExportedMethod(this,"getModeToEntity",null,null);
        return (String)_ret;
    }

    public Integer on_load_page() {
        Object _ret = this.riInvokeExportedMethod(this,"on_load_page",null,null);
        return (Integer)_ret;
    }

    public void setBindVar(String cldid, Integer slocid, String hoorgid, Integer eoid) {
        Object _ret =
            this.riInvokeExportedMethod(this,"setBindVar",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.Integer"},new Object[] {cldid, slocid, hoorgid, eoid});
        return;
    }

    public void setModeToEntity(String modeToEntity) {
        Object _ret = this.riInvokeExportedMethod(this,"setModeToEntity",new String [] {"java.lang.String"},new Object[] {modeToEntity});
        return;
    }

    public void settransval() {
        Object _ret = this.riInvokeExportedMethod(this,"settransval",null,null);
        return;
    }
}
