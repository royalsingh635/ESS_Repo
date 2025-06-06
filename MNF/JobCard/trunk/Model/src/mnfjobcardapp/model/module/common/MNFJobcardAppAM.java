package mnfjobcardapp.model.module.common;

import java.util.ArrayList;

import oracle.jbo.ApplicationModule;
import oracle.jbo.domain.Date;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Sep 10 13:43:03 IST 2014
// ---------------------------------------------------------------------
public interface MNFJobcardAppAM extends ApplicationModule {
    void setFilterData(String CldId, Integer SlocId, String HoOrgId, String OrgId);


    String getDocTxnId(String cldId, Integer slcId, String orgId, Integer docId, Integer docTypeId, Integer userId);


    Integer getFyId(String cldId, String hoOrgId, Date txnDt);

    String getJobCardSNO(Integer slcId, String cldId, String hoOrgId, Integer docId, String hexDocId, Integer docTypId,
                         String tabNm, Integer fyId, String docSubTypId);


    String chkDuplicateInParamNm(String DocId, String paramNm, String CldId, String HoOrgId, Integer SlocID);

    String chkDuplicateInParamNmDT(String DocId, String paramNm, String CldId, String HoOrgId, Integer SlocID);


    String insertInMnfOpAttch(String CldId, String HoOrgId, String OrgId, Integer Sloc_id, Integer UserId, String DocId,
                              String AttchFileExtn, String AttchFilepath, String AttchExtn, String DispFlNm);

    String callWfFunctions(String cldId, Integer slcId, String orgId, Integer usrId, String txnId, Integer docId,
                           Integer docTypId);

    Integer getDocUsrFromWF(String cldId, Integer slcId, String orgId, Integer usrId, String txnId, Integer docId,
                            Integer docTypId);

    String getWfId(String cldId, Integer slcId, String orgId, Integer docId, Integer docTypId);

    String getUserName(Integer u_Id, Integer slc_id);

    String AutoConsumptionCheck(String CldId, Integer SlocId, String OrgId, String P_Col);

    Integer GenBarcode(String CldId, Integer SlocId, String HoOrgId, String OrgId, String DocId, Integer UserId,
                       Date UserDt, String Mode, Integer BcQty);

    void updateWfStatus(String doc_txn_id, String wf_mode);


    void insertBrcktQty(Integer bcQty);

    String PopulateITM(String P_CLD_ID, Integer P_SLOC_ID, String P_HO_ORG_ID, String P_ORG_ID, String P_DOC_ID,
                       Date P_DOC_DT, Integer P_USR_ID, Date P_USR_DT, String P_MODE, Integer P_OP_SR_NO);

    Integer IssueAutoItem(String CldId, String HoOrgId, String OrgId, Integer SlocId);

    void FilterBinWhWise();

    void FilterLotWhWise();

    void FilterSrNoAsPerItem();

    void InsertIntoPickItmSr(String CldId, String HoOrgId, String OrgId, Integer SlocId);

    void InsertIntoRcItmLot(String CldID, String HoOrgId, String OrgId, Integer SlocId);

    void InsertIntoRcItmBin(String CldID, String HoOrgId, String OrgId, Integer SlocId);

    Integer chkValidateMethod();


    String ReleaseStatus(String cld_id, Integer sloc_id, String Ho_org_id, String Org_id, Integer user_id);

    String AllowJcFuture(String cld_id, Integer sloc_id, String org_id);

    String AllowJcPrevious(String cld_id, Integer sloc_id, String org_id);

    String AllowJcWorkStation(String cld_id, Integer sloc_id, String org_id);


    void opIdExe();

    void ResetJobCard(String CldId, String HoOrgId, String OrgId, Integer SlocId);

    void SearchJobCard(String CldId, String HoOrgId, String OrgId, Integer SlocId, String JcId, Integer Stat,
                       String ShiftId, Date fromDate, Date toDate, String itemOutput);

    void InsertWhAndReqAreaId();


    void AddSerialEntry(String lotId, String serial);

    String GetlotNo();

    String AllowLotIdEditable(String cld_id, Integer sloc_id, String org_id);

    String ChkOutputItmSerialized();

    void UpdateDocumentOpStatus(String cld_id, String ho_org_id, String org_id, Integer sloc_id, String Flag);

    Integer IssueAutoItemFromShopFloor(String CldId, String HoOrgId, String OrgId, Integer SlocId);

    void FilterSrNoAsPerItemSF();

    void InsertIntoPickItmSrSF(String CldId, String HoOrgId, String OrgId, Integer SlocId);

    void FilterLotWhWiseSF();

    void InsertIntoRcItmLotSF(String CldID, String HoOrgId, String OrgId, Integer SlocId);

    void FilterBinWhWiseSF();

    void InsertIntoRcItmBinSF(String CldID, String HoOrgId, String OrgId, Integer SlocId);


    void AddLotEntry(String CldId, String OrgId, String HoOrgId, Integer SlocId, String DocId, String lotId,
                     oracle.jbo.domain.Number lotQty, Integer UserId);

    String CheckforInputItmLot();

    String checkSourceDocIdValidate(String SrcDispId);

    String CheckforOutputItmLot();

    int chkNmDuplicate(String val, String Type);

    void deleteStkLot();

    int chkSrDuplicate(String val);

    int chkNmDuplicate1(String val);

    oracle.jbo.domain.Number checkYearFyId();


    oracle.jbo.domain.Number checkQtyValue();

    oracle.jbo.domain.Number statusUpdate();

    void processItemChange();

    void setopQuntity(String flag);


    ArrayList<String> valAndUpAdhoc();

    String valBfForward();

    String isDuplicate(String name);

    Boolean chkCcApplicableOrNot();

    void generateCostCenterHeaderWise();

    void declareGlblValuesForCC();

    void deleteCostCenterItem();

    void updateCostCenterAmt();

    void updateCostCenter(String docId, Integer jcBasis);

    void updateTempCostTable();

    Boolean isCostCenterAppli();

    Integer[] getDays();

    void insertIntoSubContrator();

    void setDefaultSubContracterName(String OpNo);

    String ChkQcRequired();

    String ChkCalcYieldPerFlg();

    String checkShortCloseForPartialQty();

    String isItemSerialized();

    void setCurrentRow(String itemId, Integer itemType);

    String insertIntoJobCardItemStk();
}

