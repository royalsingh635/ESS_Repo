package misReportConfig.model.views.client;

import misReportConfig.model.views.common.FinRptGrpVO;

import oracle.jbo.client.remote.ViewUsageImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Nov 30 12:22:03 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class FinRptGrpVOClient extends ViewUsageImpl implements FinRptGrpVO {
    /**
     * This is the default constructor (do not remove).
     */
    public FinRptGrpVOClient() {
    }

    public void setBindGrpId(String value) {
        Object _ret =
            getApplicationModuleProxy().riInvokeExportedMethod(this,"setBindGrpId",new String [] {"java.lang.String"},new Object[] {value});
        return;
    }
}
