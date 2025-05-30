package transferorder.model.services.common;

import java.util.ArrayList;

import oracle.jbo.ApplicationModule;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Sep 11 17:54:31 IST 2013
// ---------------------------------------------------------------------
public interface TransferOrderAM extends ApplicationModule {
    void setOrgValue();

    void setFyId();

    void setTrfNo();

    void setInvReq();

    void getSrcDocDtl(String MRSno, String docId);
    
    void setOrdQtyBs(Object ordQty);


    void searchResultTrfOrd();

    void getItemForMmTrfOrd(String cldId, String orgId, Integer slocId);

    void delChildRec(String docId);

    Integer getUsrLvl(Integer SlocId, String CldId, String OrgId, Integer UsrId, String WfNo, String DocNo);

    String getWfNo(Integer SlocId, String CldId, String OrgId, Integer DocNo);


    Integer pendingCheck(Integer SlocId, String CldId, String OrgId, Integer DocNo);

    String getDocTxnId();

    void updateStatus();


   // void setItmUom(String itmUom);

    void setItmUomBsDefVal(String docId);

    void setCriteriaForItems();

    Boolean CheckDuplicate();

    void setItmUomBs();

    void setTransItmId(String itmname);

    void resetItemAndQty();

    void setItmUom(String itmUom);

    void chkDestWhandSet();

    void chkSrcWhandSet();

    void setDestOrgandWh();

    void setOneSidOrgValue();

    void setSrcOrgandWh();

    void resetTrfType();

    void deleteitem();

    Boolean checkAllItemCancel();

    Integer checkAuthQty(String Auth);

    Boolean checkCancel();

    Integer checkResQty(String Res);

    void setItemCancel(String st, Integer oldst);

    Integer trfStatus();

    void setTrfStat(Integer stat);

    Integer insIntoTxn(Integer SlocId, String CldId, String OrgId, Integer DocNo, String WfNo, Integer usr_idFrm,
                       Integer usr_idTo, Integer levelTo, Integer levelFrm, String action, String remark,
                       oracle.jbo.domain.Number amount);

    void resetAction();

    void trfOrdCriteria();

    void updtStkAftrCncl();

    void ChangeinAuthQty();

    void ChangeinOrdQty(oracle.jbo.domain.Number qty);

    void setDestWhandSrcOrgwh();

    void setSrcWhandDestOrgwh();

    void ExecuteItmVo();

    String chkUserAuth();

    String trfOrdCriteria1(String orgId, String cldId, Integer slocId, String DocId);

    String whSetWithTrfType();

    String setDestWhTrfType();

    String stockFilter();

    ArrayList<String> getSuggestedItemDesc(String itmStr);

    void setDfltProjectId();

    String raiseTransferOrder();

    String getTopWFUsr();

    String checkRemarksField();

    String viewWFData();
}
