package tempVoucherList.model.views.client;

import oracle.jbo.client.remote.ViewUsageImpl;

import tempVoucherList.model.views.common.LovDistCoaVO;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Jan 19 12:34:38 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LovDistCoaVOClient extends ViewUsageImpl implements LovDistCoaVO {
    /**
     * This is the default constructor (do not remove).
     */
    public LovDistCoaVOClient() {
    }

    public void setBindCoaNm(String value) {
        Object _ret =
            getApplicationModuleProxy().riInvokeExportedMethod(this,"setBindCoaNm",new String [] {"java.lang.String"},new Object[] {value});
        return;
    }
}
