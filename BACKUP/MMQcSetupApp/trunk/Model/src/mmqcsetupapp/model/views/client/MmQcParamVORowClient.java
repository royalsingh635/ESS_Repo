package mmqcsetupapp.model.views.client;

import mmqcsetupapp.model.views.common.MmQcParamVORow;

import oracle.jbo.client.remote.RowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Aug 09 12:19:51 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MmQcParamVORowClient extends RowImpl implements MmQcParamVORow {
    /**
     * This is the default constructor (do not remove).
     */
    public MmQcParamVORowClient() {
    }

    public void isActive(String value) {
        Object _ret =
            getApplicationModuleProxy().riInvokeExportedMethod(this,"isActive",new String [] {"java.lang.String"},new Object[] {value});
        return;
    }
}
