package appservloc.model.module.client;

import appservloc.model.module.common.AppServrLocAM;

import oracle.jbo.client.remote.ApplicationModuleImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Aug 06 19:30:47 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppServrLocAMClient extends ApplicationModuleImpl implements AppServrLocAM {
    /**
     * This is the default constructor (do not remove).
     */
    public AppServrLocAMClient() {
    }

    public Integer on_load_page() {
        Object _ret = this.riInvokeExportedMethod(this,"on_load_page",null,null);
        return (Integer)_ret;
    }
}
