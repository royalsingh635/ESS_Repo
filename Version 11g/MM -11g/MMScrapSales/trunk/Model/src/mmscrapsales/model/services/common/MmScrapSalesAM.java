package mmscrapsales.model.services.common;

import oracle.jbo.ApplicationModule;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Jul 04 15:34:24 IST 2014
// ---------------------------------------------------------------------
public interface MmScrapSalesAM extends ApplicationModule {
    void setTxnIdForCurrentRow(Integer UsrId, String CldId, Integer SlocId, String OrgId);

    String addScrpItmRcrd();


    String filterLotorSr();

    String itmQtyBaseValid(oracle.jbo.domain.Number itmBsQty);

    String validQuantityCheck();

    String insrtScrpStk();

    String setTransQuantity();

    String setsrQuantity();

    String checkSaveRecord();

    String DeleteSrno();

    String Deletelotrecord();

    String genscrapId();

    String inserSrStock();

    String resetSrch();

    String search();

    String filterDocId(String DocId, String cldId, String orgId, Integer slocId);

    String selectAllItem();

    String autoStkAllotment();

    String postchanges();

    String itmValidChk();

    Integer getUsrLvl(Integer SlocId, String CldId, String OrgId, Integer UsrId, String WfNo, String DocNo);

    String getWfNo(Integer SlocId, String CldId, String OrgId, Integer DocNo);

    Integer insIntoTxn(Integer SlocId, String CldId, String OrgId, Integer DocNo, String WfNo, Integer usr_idFrm,
                       Integer usr_idTo, Integer levelTo, Integer levelFrm, String action, String remark,
                       oracle.jbo.domain.Number amount);

    Integer pendingCheck(Integer SlocId, String CldId, String OrgId, Integer DocNo);

    String setOutCome(String whRtnvalue);

    String getUsrNm(Integer usrId);

    String deleteItmWithRecord();

    void filteredDataAtpageload();

    void ResetFieldValues();

    void RefreshCheckbox();
}
