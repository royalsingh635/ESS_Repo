package slspicpackshipapp.model.views.client;

import oracle.jbo.client.remote.ViewUsageImpl;

import slspicpackshipapp.model.views.common.ViewMmStkSummBinVO;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Nov 15 15:50:05 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ViewMmStkSummBinVOClient extends ViewUsageImpl implements ViewMmStkSummBinVO {
    /**
     * This is the default constructor (do not remove).
     */
    public ViewMmStkSummBinVOClient() {
    }

    public oracle.jbo.domain.Number getTotalIssueQtyBin() {
        Object _ret = getApplicationModuleProxy().riInvokeExportedMethod(this,"getTotalIssueQtyBin",null,null);
        return (oracle.jbo.domain.Number)_ret;
    }
}
