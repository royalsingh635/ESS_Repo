package mmquotation.model.service.client;

import mmquotation.model.service.common.QuotationAM;

import oracle.jbo.client.remote.ApplicationModuleImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Apr 01 14:28:14 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class QuotationAMClient extends ApplicationModuleImpl implements QuotationAM {
    /**
     * This is the default constructor (do not remove).
     */
    public QuotationAMClient() {
    }


    public String CheckSaved() {
        Object _ret = this.riInvokeExportedMethod(this,"CheckSaved",null,null);
        return (String)_ret;
    }

    public void filterItems() {
        Object _ret = this.riInvokeExportedMethod(this,"filterItems",null,null);
        return;
    }

    public String getTxnId() {
        Object _ret = this.riInvokeExportedMethod(this,"getTxnId",null,null);
        return (String)_ret;
    }

    public Integer getUsrLvl(Integer SlocId, String CldId, String OrgId, Integer UsrId, String WfNo, String DocNo) {
        Object _ret =
            this.riInvokeExportedMethod(this,"getUsrLvl",new String [] {"java.lang.Integer","java.lang.String","java.lang.String","java.lang.Integer","java.lang.String","java.lang.String"},new Object[] {SlocId, CldId, OrgId, UsrId, WfNo, DocNo});
        return (Integer)_ret;
    }

    public String getWfNo(Integer SlocId, String CldId, String OrgId, Integer DocNo) {
        Object _ret =
            this.riInvokeExportedMethod(this,"getWfNo",new String [] {"java.lang.Integer","java.lang.String","java.lang.String","java.lang.Integer"},new Object[] {SlocId, CldId, OrgId, DocNo});
        return (String)_ret;
    }

    public Integer insIntoTxn(Integer SlocId, String CldId, String OrgId, Integer DocNo, String WfNo,
                              Integer usr_idFrm, Integer usr_idTo, Integer levelTo, Integer levelFrm, String action,
                              String remark, oracle.jbo.domain.Number amount) {
        Object _ret =
            this.riInvokeExportedMethod(this,"insIntoTxn",new String [] {"java.lang.Integer","java.lang.String","java.lang.String","java.lang.Integer","java.lang.String","java.lang.Integer","java.lang.Integer","java.lang.Integer","java.lang.Integer","java.lang.String","java.lang.String","oracle.jbo.domain.Number"},new Object[] {SlocId, CldId, OrgId, DocNo, WfNo, usr_idFrm, usr_idTo, levelTo, levelFrm, action, remark, amount});
        return (Integer)_ret;
    }

    public Integer pendingCheck(Integer SlocId, String CldId, String OrgId, Integer DocNo) {
        Object _ret =
            this.riInvokeExportedMethod(this,"pendingCheck",new String [] {"java.lang.Integer","java.lang.String","java.lang.String","java.lang.Integer"},new Object[] {SlocId, CldId, OrgId, DocNo});
        return (Integer)_ret;
    }

    public void resetQuery() {
        Object _ret = this.riInvokeExportedMethod(this,"resetQuery",null,null);
        return;
    }

    public String resolvEl(String data) {
        Object _ret = this.riInvokeExportedMethod(this,"resolvEl",new String [] {"java.lang.String"},new Object[] {data});
        return (String)_ret;
    }

    public void searchAction() {
        Object _ret = this.riInvokeExportedMethod(this,"searchAction",null,null);
        return;
    }

    public void searchQuery(Integer SlocId, String OrgId, String DocId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"searchQuery",new String [] {"java.lang.Integer","java.lang.String","java.lang.String"},new Object[] {SlocId, OrgId, DocId});
        return;
    }

    public void setBindVarToEoId() {
        Object _ret = this.riInvokeExportedMethod(this,"setBindVarToEoId",null,null);
        return;
    }

    public void tranlateQuot() {
        Object _ret = this.riInvokeExportedMethod(this,"tranlateQuot",null,null);
        return;
    }
}
