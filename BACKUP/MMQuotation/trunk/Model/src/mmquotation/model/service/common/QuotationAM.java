package mmquotation.model.service.common;

import oracle.jbo.ApplicationModule;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Apr 01 14:28:13 IST 2014
// ---------------------------------------------------------------------
public interface QuotationAM extends ApplicationModule {
    void searchQuery(Integer SlocId, String OrgId, String DocId);

    void setBindVarToEoId();

    String CheckSaved();

   // void filterItems();

    String getTxnId();

    Integer getUsrLvl(Integer SlocId, String CldId, String OrgId, Integer UsrId, String WfNo, String DocNo);

    String getWfNo(Integer SlocId, String CldId, String OrgId, Integer DocNo);

    Integer insIntoTxn(Integer SlocId, String CldId, String OrgId, Integer DocNo, String WfNo, Integer usr_idFrm,
                       Integer usr_idTo, Integer levelTo, Integer levelFrm, String action, String remark,
                       oracle.jbo.domain.Number amount);

    Integer pendingCheck(Integer SlocId, String CldId, String OrgId, Integer DocNo);

    void resetQuery();

    String resolvEl(String data);

    void searchAction();

    void tranlateQuot();
}
