package objectNrApp.model.module.client;

import objectNrApp.model.module.common.ObjectNrAM;

import oracle.jbo.client.remote.ApplicationModuleImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sun Aug 11 19:08:07 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ObjectNrAMClient extends ApplicationModuleImpl implements ObjectNrAM {
    /**
     * This is the default constructor (do not remove).
     */
    public ObjectNrAMClient() {
    }

    public Integer on_load_page() {
        Object _ret = this.riInvokeExportedMethod(this,"on_load_page",null,null);
        return (Integer)_ret;
    }
}
