package mminvoice.model.services.common;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.domain.Timestamp;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Oct 24 15:41:05 IST 2013
// ---------------------------------------------------------------------
public interface MMInvoiceAM extends ApplicationModule {
    void setTxnIdForCurrentRow(Integer UsrId, String CldId, Integer SlocId, String OrgId);

    Integer populateInvoice(String CldId, Integer SlocId, String OrgId, String rcptId, Integer UsrId);

    String getInvNo(Integer SlocId, String CldId, String OrgId);

    void applyTaxRule(String cldId, Integer slocId, String orgId, Integer ruleId, oracle.jbo.domain.Number taxableAmt,
                      String type);

    String checkTaxPresent();

    Key ocCurrentRowKey();

    void setTrExempted(String Flg);

    void applyTdsRule(String cldId, Integer slocId, String orgId, Integer ruleId, oracle.jbo.domain.Number taxableAmt,
                      String type);

    String checkTdsPresent();

    String populateCalculations(String p_cld_id, Integer p_sloc_id, String p_org_id, Integer p_usr_id);

    void setcoaIdForInvc(Integer coa);

    Integer getCoaForEo(String hoOrgId, String eoName);

    void postChng();


    String paymentDateValidation(Timestamp paydt);

    Integer getPaySchdRowCount();

    void setPaymentAmount();

    void deletePaymentSchedule();

    void insertAdjustmentLines();

    String isAdjustmentValidate(oracle.jbo.domain.Number num);

    oracle.jbo.domain.Number setAdjustmentBSAmount(oracle.jbo.domain.Number AdjtSpAmt);


    void deletePaymentSchduleAll();

    void donePaySchdlAction(Integer p_user_id, oracle.jbo.domain.Number totAmt, Integer paymentMode);

    void appendPaymentAmtOnSameDt(oracle.jbo.domain.Number amt, Timestamp date);

    oracle.jbo.domain.Number getTotalPaymentSchdAmount();

    String CheckSaved();

    String getCurrentDocId();

    Integer getFYid(String CldId, String OrgId, Timestamp invDate, String Mode);

    Integer getUsrLvl(Integer SlocId, String CldId, String OrgId, Integer UsrId, String WfNo, String DocNo);

    String getWfNo(Integer SlocId, String CldId, String OrgId, Integer DocNo);

    Integer insIntoTxn(Integer SlocId, String CldId, String OrgId, Integer DocNo, String WfNo, Integer usr_idFrm,
                       Integer usr_idTo, Integer levelTo, Integer levelFrm, String action, String remark,
                       oracle.jbo.domain.Number amount);

    Integer pendingCheck(Integer SlocId, String CldId, String OrgId, Integer DocNo);

    void callFunctionAfterWf();

    void defaultInvcSrch();

    void resetAction();

    void searchAction();

    void setSelectDocIdOnViewPage();

    oracle.jbo.domain.Number getTotalAdjustedAmount();

    boolean isInvcAuth();

    void deleteOtherCharges();

    void deleteTrFromItm();

    void deleteTrFromSrc();

    void deleteTdsFromItm();

    Integer getTrRuleId();

    boolean checkAmount();

    Integer getTdsRuleId();

    String getOrgBsCurrency();

    String getSupplierBsCurrency();

    void doBeforeCommit(Integer SlocId, String CldId, String OrgId);

    oracle.jbo.domain.Number getTotalAmt();

    Integer populateInvoiceByIssue(String CldId, Integer SlocId, String OrgId, String rcptId, Integer UsrId);


    void cleanUp();

    String setCurrencyFactor(String OrgId);

    boolean chkValidation(oracle.jbo.domain.Number amt);

    Integer populateInvoiceByCashPurchase(String CldId, Integer SlocId, String OrgId, String rcptId, Integer UsrId);

    Row getCurrentRow();

    void executePurchaseInvoiceVO();

    void selectedPurchaseInvoiceAction();

    void setDifferencePrice(oracle.jbo.domain.Number newPrice);

    void insertNewPurchaseInvoice();

    void setAmtInSelectedPI(oracle.jbo.domain.Number amount);

    void setPerInSelectedPI(oracle.jbo.domain.Number per);

    boolean checkValidation();

    Boolean checkEoExist();

    void cleanUpOnInvcCreate();

    Boolean checkItemSelectedPI();

    void cleanSelectedInvoiceVo();

    Boolean checkCurrencyExist();

    Boolean checkTaxRuleApplied();

    String getUserName(Integer userId);

    void refreshAllLovs();

    void setDefaultAddress();

    void addCoa();

    Boolean checkDuplicateCoa();

    void executeTransferInvoiceVO();

    void selectedTransferInvoiceAction();

    void cleanSelectedTrfInvoiceVo();

    boolean checkValidation1();

    void setAmtInSelectedPI1(oracle.jbo.domain.Number amount);

    void setPerInSelectedPI1(oracle.jbo.domain.Number per);

    String checkItemSelectedTI();

    String insertNewPurTransferInvc();

    String populateSrvcInvoice();

    String populateConsumabeInvoice();

    String isTaxApplicable();
}
