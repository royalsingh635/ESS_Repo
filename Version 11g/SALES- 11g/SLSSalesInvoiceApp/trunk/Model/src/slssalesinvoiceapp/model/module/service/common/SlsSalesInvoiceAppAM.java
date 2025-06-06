package slssalesinvoiceapp.model.module.service.common;

import java.math.BigDecimal;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Row;
import oracle.jbo.domain.Timestamp;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Sep 03 17:31:56 IST 2013
// ---------------------------------------------------------------------
public interface SlsSalesInvoiceAppAM extends ApplicationModule {
    Integer getMaxShipSrNo();

    void setBaseAmount(BigDecimal Rate);

    Integer invoiceWiseTaxValue();

    void insertIntoSlsInvTrLineForInvoicewise(Long p_tax_rule_id, Integer p_user_id, BigDecimal p_taxable_amount,
                                              BigDecimal P_conv_fctr);

    void setCurr_OC();

    void insertIntoTnc(Row CurrRow_Tnc);

    String shipmentNameValidation(String ShipmentName);

    String checkForReapplyTax(BigDecimal TaxableAmount);

    void donePaySchdlAction(Integer p_user_id, BigDecimal totAmt);

    String paymentDateValidation(Timestamp paydt);

    void DiscountValidation(BigDecimal DiscVal);

    void removeTaxInvoiceWise();

    String getShipmentId(String ShipmentDispId);

    String setInvShpItm(Integer P_USR, String P_ship_ID);

    void insertIntoSlsInvTrLineForItemwise(Long p_tax_rule_id, Integer p_user_id, BigDecimal p_taxable_amount,
                                           BigDecimal P_conv_fctr);

    void removeTaxItemWise();

    void checkTrforItem(Integer UserId);

    void executeVoForItemwiseTax();

    void deleteShpDtl();

    void setTotalInvAmt();

    void setTaxRuleFlg(String ShpId);

    void setTaxAmount();


    void reset();

    void search();

    void setBindVariablesInSearchView();

    void setSelectDocIdOnViewPage();

    void refreshCustomerLOV(Integer InvType);

    void exceptionHandler();

    void insertAdjustment();

    void setCoaIdFromEo(StringBuffer EoNm);

    StringBuffer getCurrentDocId();

    void setSelectDocIdOnCommit(StringBuffer DocId);

    void InvoiceCalcu(StringBuffer DocId);

    Integer slsInvoicePendingAt();

    StringBuffer getWfIdAttachedWithTheDoc();

    Integer getUsrLvl(StringBuffer WfId);

    Integer insIntoTxn(StringBuffer WfId, Integer level);

    void functionCallOnApprovalFromWorkFlow();

    boolean DisableAdjTab();


    void setTaxToZero();

    boolean isTaxAppliedCorrectly();

    boolean isFYOpenForCurrentDate(Timestamp dt);

    void advanceAdjCheck();

    boolean isPaymntSchduleValid();

    void CancelSalesInvoice();

    boolean isUserEligibleToCancelSalesInvoice();

    void insertItmOnBasisOfIntimation();

    boolean doOtherChargeCoaAlreadyExist(Integer CoaId);

    Integer isDiscountValueValid(BigDecimal val);


    boolean isFromNoOfShipmentValid(oracle.jbo.domain.Number val);

    boolean isToNoOfShipmentValid(oracle.jbo.domain.Number val);

    boolean isFromSIAmountValid(oracle.jbo.domain.Number val);

    boolean isToSIAmountValid(oracle.jbo.domain.Number val);

   // boolean getSlsInvdtlsCount();

    boolean isSlsInvdtlsCountValid();

    boolean isOtherChargesAmountValid(BigDecimal val);

    void AutoGeneratePaymentSchedule();

    Integer adjAmtVal(BigDecimal val);

    void populateSupplimentryInvoice();

    Integer chkInvOcEnteredOrNot();

    Integer getCurConvType();

    void setTaxRuleInTransient();

    void setRemainingPayment();

    boolean isCoaIdNull();

    void searchVoForSuppInvoice();

    void deselectAllInvoices();

    void selectAllInvoices();

    boolean saveAndPoplulateDistinctItm();

    boolean applyNewPriceForSuppInv(StringBuffer oper, StringBuffer typ, BigDecimal value);

    void resetSuppliTabs();

    Boolean isWorkFlowAndUserValid();

    void updateShipmentOnSave();

    void filterTNC();

    Boolean areAllShipmntAmtValid();

    Boolean isFinancialYearValid();
}
