package appOpportunity.model.views.client;

import appOpportunity.model.views.common.SearchOppVO;

import oracle.jbo.client.remote.ViewUsageImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Feb 04 16:36:12 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class SearchOppVOClient extends ViewUsageImpl implements SearchOppVO {
    /**
     * This is the default constructor (do not remove).
     */
    public SearchOppVOClient() {
    }


    public void setBindVar() {
        Object _ret = getApplicationModuleProxy().riInvokeExportedMethod(this, "setBindVar", null, null);
        return;
    }

    public void setBindVarToNull() {
        Object _ret = getApplicationModuleProxy().riInvokeExportedMethod(this, "setBindVarToNull", null, null);
        return;
    }
}
