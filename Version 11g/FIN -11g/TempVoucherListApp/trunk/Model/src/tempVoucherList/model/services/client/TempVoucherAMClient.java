package tempVoucherList.model.services.client;

import java.math.BigDecimal;

import java.util.List;

import javax.faces.model.SelectItem;

import oracle.jbo.client.remote.ApplicationModuleImpl;

import oracle.jbo.domain.Date;

import tempVoucherList.model.services.common.TempVoucherAM;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Jan 07 15:48:06 IST 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class TempVoucherAMClient extends ApplicationModuleImpl implements TempVoucherAM {
    /**
     * This is the default constructor (do not remove).
     */
    public TempVoucherAMClient() {
    }


    public String validateChqNo(Integer p_sloc_id, Integer p_bnk_id, Integer p_chq_buk_id, String p_chq_no) {
        Object _ret =
            this.riInvokeExportedMethod(this,"validateChqNo",new String [] {"java.lang.Integer","java.lang.Integer","java.lang.Integer","java.lang.String"},new Object[] {p_sloc_id, p_bnk_id, p_chq_buk_id, p_chq_no});
        return (String)_ret;
    }


    public String validateChqNo(Integer p_sloc_id, Integer p_bnk_id, Integer p_chq_buk_id, Integer p_chq_no) {
        Object _ret =
            this.riInvokeExportedMethod(this,"validateChqNo",new String [] {"java.lang.Integer","java.lang.Integer","java.lang.Integer","java.lang.Integer"},new Object[] {p_sloc_id, p_bnk_id, p_chq_buk_id, p_chq_no});
        return (String)_ret;
    }


    public void drCrNoteAdj() {
        Object _ret = this.riInvokeExportedMethod(this,"drCrNoteAdj",null,null);
        return;
    }


  /*   public void validateTempVouForGl() {
        Object _ret = this.riInvokeExportedMethod(this,"validateTempVouForGl",null,null);
        return;
    } */


    public Integer on_load_page() {
        Object _ret = this.riInvokeExportedMethod(this,"on_load_page",null,null);
        return (Integer)_ret;
    }


    public List<SelectItem> DisplayIdOfAdjDtl() {
        Object _ret = this.riInvokeExportedMethod(this,"DisplayIdOfAdjDtl",null,null);
        return (List<SelectItem>)_ret;
    }


    /*  public List DisplayIdOfAdjDtl() {
        Object _ret = this.riInvokeExportedMethod(this,"DisplayIdOfAdjDtl",null,null);
        return (List)_ret;
    } */


    public String CheckDuplicateTemplateName(String CldId, Integer SlocId, String HoOrgId, String TemplateName,
                                             Integer UsrId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"CheckDuplicateTemplateName",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.String","java.lang.Integer"},new Object[] {CldId, SlocId, HoOrgId, TemplateName, UsrId});
        return (String)_ret;
    }

    public void adjustAdvanceService(String p_cld_id, Integer p_slc_id, String p_ho_id, String p_org_id,
                                     Integer p_coa_id) {
        Object _ret =
            this.riInvokeExportedMethod(this,"adjustAdvanceService",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.String","java.lang.Integer"},new Object[] {p_cld_id, p_slc_id, p_ho_id, p_org_id, p_coa_id});
        return;
    }

    public oracle.jbo.domain.Number adjustAmount() {
        Object _ret = this.riInvokeExportedMethod(this,"adjustAmount",null,null);
        return (oracle.jbo.domain.Number)_ret;
    }

    public void adjustTvouAdjAllRows(String p_action_typ) {
        Object _ret = this.riInvokeExportedMethod(this,"adjustTvouAdjAllRows",new String [] {"java.lang.String"},new Object[] {p_action_typ});
        return;
    }

    public Integer assignBnkInstrmtAmt(oracle.jbo.domain.Number bnkInstrmntAmt, oracle.jbo.domain.Number oldCurrRate,
                                       oracle.jbo.domain.Number newCurrRate) {
        Object _ret =
            this.riInvokeExportedMethod(this,"assignBnkInstrmtAmt",new String [] {"oracle.jbo.domain.Number","oracle.jbo.domain.Number","oracle.jbo.domain.Number"},new Object[] {bnkInstrmntAmt, oldCurrRate, newCurrRate});
        return (Integer)_ret;
    }

    public void billDetail() {
        Object _ret = this.riInvokeExportedMethod(this,"billDetail",null,null);
        return;
    }

    public String callWfFunctions(String cldId, Integer slcId, String orgId, Integer usrId, String tvouId,
                                  Integer docId, Integer docTypId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"callWfFunctions",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.Integer","java.lang.String","java.lang.Integer","java.lang.Integer"},new Object[] {cldId, slcId, orgId, usrId, tvouId, docId, docTypId});
        return (String)_ret;
    }

    public void checkAllAdvances() {
        Object _ret = this.riInvokeExportedMethod(this,"checkAllAdvances",null,null);
        return;
    }

    public String checkBillNumberUniqueness(String CldId, Integer SlocId, String HoOrgId, String OrgId, Integer CoaId,
                                            Date vDate, String BillNo, String TvouId, String Mode) {
        Object _ret =
            this.riInvokeExportedMethod(this,"checkBillNumberUniqueness",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.String","java.lang.Integer","oracle.jbo.domain.Date","java.lang.String","java.lang.String","java.lang.String"},new Object[] {CldId, SlocId, HoOrgId, OrgId, CoaId, vDate, BillNo, TvouId, Mode});
        return (String)_ret;
    }

    public String checkExpenseBillNumberDuplicate(String CldId, Integer SlocId, String HoOrgId, String OrgId,
                                                  Integer CoaId, Date vDate, String BillNo, String TvouId,
                                                  String Mode) {
        Object _ret =
            this.riInvokeExportedMethod(this,"checkExpenseBillNumberDuplicate",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.String","java.lang.Integer","oracle.jbo.domain.Date","java.lang.String","java.lang.String","java.lang.String"},new Object[] {CldId, SlocId, HoOrgId, OrgId, CoaId, vDate, BillNo, TvouId, Mode});
        return (String)_ret;
    }

    public String checkInstrumentAddedOrNot() {
        Object _ret = this.riInvokeExportedMethod(this,"checkInstrumentAddedOrNot",null,null);
        return (String)_ret;
    }

    public BigDecimal checkOsAmt(String vouId, String amt) {
        Object _ret =
            this.riInvokeExportedMethod(this,"checkOsAmt",new String [] {"java.lang.String","java.lang.String"},new Object[] {vouId, amt});
        return (BigDecimal)_ret;
    }

    public String chqNoFillPolicy() {
        Object _ret = this.riInvokeExportedMethod(this,"chqNoFillPolicy",null,null);
        return (String)_ret;
    }

    public oracle.jbo.domain.Number[] compareInstrumentAmt() {
        Object _ret = this.riInvokeExportedMethod(this,"compareInstrumentAmt",null,null);
        return (oracle.jbo.domain.Number[])_ret;
    }

    public void deSelectAllAdvance() {
        Object _ret = this.riInvokeExportedMethod(this,"deSelectAllAdvance",null,null);
        return;
    }

    public void deSelectAllInvoices() {
        Object _ret = this.riInvokeExportedMethod(this,"deSelectAllInvoices",null,null);
        return;
    }

    public void deleteFromDetail(String displayId) {
        Object _ret = this.riInvokeExportedMethod(this,"deleteFromDetail",new String [] {"java.lang.String"},new Object[] {displayId});
        return;
    }

    public void exceptioHandeler() {
        Object _ret = this.riInvokeExportedMethod(this,"exceptioHandeler",null,null);
        return;
    }

    public void exchangeFluct(String VouId, Date VouDt) {
        Object _ret =
            this.riInvokeExportedMethod(this,"exchangeFluct",new String [] {"java.lang.String","oracle.jbo.domain.Date"},new Object[] {VouId, VouDt});
        return;
    }

    public void fetchAdjustmentData(String p_cld_id, Integer p_slc_id, String p_ho_id, String p_org_id,
                                    Integer p_coa_id, Integer p_eo_mst_id, String p_arap_flg, String p_amt_typ,
                                    String p_filter_Val, Date p_bill_Frm_Dt, Date p_bill_To_Dt, Integer p_bill_Days) {
        Object _ret =
            this.riInvokeExportedMethod(this,"fetchAdjustmentData",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.String","java.lang.Integer","java.lang.Integer","java.lang.String","java.lang.String","java.lang.String","oracle.jbo.domain.Date","oracle.jbo.domain.Date","java.lang.Integer"},new Object[] {p_cld_id, p_slc_id, p_ho_id, p_org_id, p_coa_id, p_eo_mst_id, p_arap_flg, p_amt_typ, p_filter_Val, p_bill_Frm_Dt, p_bill_To_Dt, p_bill_Days});
        return;
    }

    public void fetchAdjustmentDataForManual(String p_cld_id, Integer p_slc_id, String p_ho_id, String p_org_id,
                                             Integer p_coa_id, Integer p_eo_mst_id, String p_arap_flg,
                                             String p_amt_typ, String p_filter_Val, Date p_bill_Frm_Dt,
                                             Date p_bill_To_Dt, Integer p_bill_Days) {
        Object _ret =
            this.riInvokeExportedMethod(this,"fetchAdjustmentDataForManual",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.String","java.lang.Integer","java.lang.Integer","java.lang.String","java.lang.String","java.lang.String","oracle.jbo.domain.Date","oracle.jbo.domain.Date","java.lang.Integer"},new Object[] {p_cld_id, p_slc_id, p_ho_id, p_org_id, p_coa_id, p_eo_mst_id, p_arap_flg, p_amt_typ, p_filter_Val, p_bill_Frm_Dt, p_bill_To_Dt, p_bill_Days});
        return;
    }

    public void filterArapShuttle(String CldId, Integer SlocId, String HoOrgId, String OrgId, Integer EoMstId,
                                  Integer CoaId, String AmtTyp, String ArApFlg, Date fromDate, Date ToDate,
                                  String FilterVal, Integer days, String Ext1, String Ext2, Integer Ext3,
                                  Integer Ext4) {
        Object _ret =
            this.riInvokeExportedMethod(this,"filterArapShuttle",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.String","java.lang.Integer","java.lang.Integer","java.lang.String","java.lang.String","oracle.jbo.domain.Date","oracle.jbo.domain.Date","java.lang.String","java.lang.Integer","java.lang.String","java.lang.String","java.lang.Integer","java.lang.Integer"},new Object[] {CldId, SlocId, HoOrgId, OrgId, EoMstId, CoaId, AmtTyp, ArApFlg, fromDate, ToDate, FilterVal, days, Ext1, Ext2, Ext3, Ext4});
        return;
    }

    public void filterPostedAdvVoucherDetail(String CldId, Integer SlocId, String HoOrgId, Integer CoaId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"filterPostedAdvVoucherDetail",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.Integer"},new Object[] {CldId, SlocId, HoOrgId, CoaId});
        return;
    }

    public void filterPostedVoucherDetail(String CldId, Integer SlocId, String HoOrgId, Integer CoaId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"filterPostedVoucherDetail",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.Integer"},new Object[] {CldId, SlocId, HoOrgId, CoaId});
        return;
    }

    public void filterUnpostedAdvVoucherDetail(String CldId, Integer SlocId, String HoOrgId, Integer CoaId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"filterUnpostedAdvVoucherDetail",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.Integer"},new Object[] {CldId, SlocId, HoOrgId, CoaId});
        return;
    }

    public void filterUnpostedVoucherDetail(String CldId, Integer SlocId, String HoOrgId, Integer CoaId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"filterUnpostedVoucherDetail",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.Integer"},new Object[] {CldId, SlocId, HoOrgId, CoaId});
        return;
    }

    public void generateDocNo() {
        Object _ret = this.riInvokeExportedMethod(this,"generateDocNo",null,null);
        return;
    }

    public Integer getDocUsrFromWF(String cldId, Integer slcId, String orgId, Integer usrId, String tvouId,
                                   Integer docId, Integer docTypId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"getDocUsrFromWF",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.Integer","java.lang.String","java.lang.Integer","java.lang.Integer"},new Object[] {cldId, slcId, orgId, usrId, tvouId, docId, docTypId});
        return (Integer)_ret;
    }

    public void getFromTemplate(String tmplVouId) {
        Object _ret = this.riInvokeExportedMethod(this,"getFromTemplate",new String [] {"java.lang.String"},new Object[] {tmplVouId});
        return;
    }

    public String getGlDispNo() {
        Object _ret = this.riInvokeExportedMethod(this,"getGlDispNo",null,null);
        return (String)_ret;
    }

    public Date getGlVouDt() {
        Object _ret = this.riInvokeExportedMethod(this,"getGlVouDt",null,null);
        return (Date)_ret;
    }

    public Integer getGlVouTyp() {
        Object _ret = this.riInvokeExportedMethod(this,"getGlVouTyp",null,null);
        return (Integer)_ret;
    }

    public String getLastChqNo(Integer p_sloc_id, Integer p_bnk_id, String p_search_mode, Integer p_chq_buk_id) {
        Object _ret =
            this.riInvokeExportedMethod(this,"getLastChqNo",new String [] {"java.lang.Integer","java.lang.Integer","java.lang.String","java.lang.Integer"},new Object[] {p_sloc_id, p_bnk_id, p_search_mode, p_chq_buk_id});
        return (String)_ret;
    }

    public String getMultiOrgFlag() {
        Object _ret = this.riInvokeExportedMethod(this,"getMultiOrgFlag",null,null);
        return (String)_ret;
    }

    public BigDecimal getTaxBA(String tvouId, String cldId, String hoOrgId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"getTaxBA",new String [] {"java.lang.String","java.lang.String","java.lang.String"},new Object[] {tvouId, cldId, hoOrgId});
        return (BigDecimal)_ret;
    }

    public BigDecimal getTdsBA(String tvouId, String cldId, String hoOrgId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"getTdsBA",new String [] {"java.lang.String","java.lang.String","java.lang.String"},new Object[] {tvouId, cldId, hoOrgId});
        return (BigDecimal)_ret;
    }

    public Integer getTvouLnSn(String VouId) {
        Object _ret = this.riInvokeExportedMethod(this,"getTvouLnSn",new String [] {"java.lang.String"},new Object[] {VouId});
        return (Integer)_ret;
    }

    public String getVoucherStatus(Integer slocId, String orgId, Integer docId, Integer docTypEntId, Integer docTypeId,
                                   String docTxnId, Date docTxnDt, Integer docUsrId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"getVoucherStatus",new String [] {"java.lang.Integer","java.lang.String","java.lang.Integer","java.lang.Integer","java.lang.Integer","java.lang.String","oracle.jbo.domain.Date","java.lang.Integer"},new Object[] {slocId, orgId, docId, docTypEntId, docTypeId, docTxnId, docTxnDt, docUsrId});
        return (String)_ret;
    }

    public String getWfId(String cldId, Integer slcId, String orgId, Integer docId, Integer docTypId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"getWfId",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.Integer","java.lang.Integer"},new Object[] {cldId, slcId, orgId, docId, docTypId});
        return (String)_ret;
    }

    public void importTvouAdj() {
        Object _ret = this.riInvokeExportedMethod(this,"importTvouAdj",null,null);
        return;
    }

    public void importTvouAdjDtl() {
        Object _ret = this.riInvokeExportedMethod(this,"importTvouAdjDtl",null,null);
        return;
    }

    public void importTvouLn() {
        Object _ret = this.riInvokeExportedMethod(this,"importTvouLn",null,null);
        return;
    }

    public void insTvouLineTax(String PARAM_CLD_ID, Integer PARAM_SLOC_ID, Integer PARAM_INST_ID,
                               String PARAM_HO_ORG_ID, String PARAM_ORG_ID, String PARAM_TVOU_ID, Date PARAM_TVOU_DT,
                               Integer PARAM_CURR_ID_SP, Integer PARAM_UID, Date PARAM_USR_DT) {
        Object _ret =
            this.riInvokeExportedMethod(this,"insTvouLineTax",new String [] {"java.lang.String","java.lang.Integer","java.lang.Integer","java.lang.String","java.lang.String","java.lang.String","oracle.jbo.domain.Date","java.lang.Integer","java.lang.Integer","oracle.jbo.domain.Date"},new Object[] {PARAM_CLD_ID, PARAM_SLOC_ID, PARAM_INST_ID, PARAM_HO_ORG_ID, PARAM_ORG_ID, PARAM_TVOU_ID, PARAM_TVOU_DT, PARAM_CURR_ID_SP, PARAM_UID, PARAM_USR_DT});
        return;
    }

    public void insTvouLineTds(String PARAM_CLD_ID, Integer PARAM_SLOC_ID, Integer PARAM_INST_ID,
                               String PARAM_HO_ORG_ID, String PARAM_ORG_ID, String PARAM_TVOU_ID, Date PARAM_TVOU_DT,
                               Integer PARAM_CURR_ID_SP, Integer PARAM_UID, Date PARAM_USR_DT) {
        Object _ret =
            this.riInvokeExportedMethod(this,"insTvouLineTds",new String [] {"java.lang.String","java.lang.Integer","java.lang.Integer","java.lang.String","java.lang.String","java.lang.String","oracle.jbo.domain.Date","java.lang.Integer","java.lang.Integer","oracle.jbo.domain.Date"},new Object[] {PARAM_CLD_ID, PARAM_SLOC_ID, PARAM_INST_ID, PARAM_HO_ORG_ID, PARAM_ORG_ID, PARAM_TVOU_ID, PARAM_TVOU_DT, PARAM_CURR_ID_SP, PARAM_UID, PARAM_USR_DT});
        return;
    }

    public void insertIntoTvouAdjAutoDtl(String query, String p_cld_id, Integer p_slc_id, String p_ho_id,
                                         String p_org_id, Integer p_coa_id, Integer p_eo_mst_id, String p_arap_flg,
                                         String p_amt_typ, String p_filter_Val, Date p_bill_Frm_Dt, Date p_bill_To_Dt,
                                         Integer p_bill_Days) {
        Object _ret =
            this.riInvokeExportedMethod(this,"insertIntoTvouAdjAutoDtl",new String [] {"java.lang.String","java.lang.String","java.lang.Integer","java.lang.String","java.lang.String","java.lang.Integer","java.lang.Integer","java.lang.String","java.lang.String","java.lang.String","oracle.jbo.domain.Date","oracle.jbo.domain.Date","java.lang.Integer"},new Object[] {query, p_cld_id, p_slc_id, p_ho_id, p_org_id, p_coa_id, p_eo_mst_id, p_arap_flg, p_amt_typ, p_filter_Val, p_bill_Frm_Dt, p_bill_To_Dt, p_bill_Days});
        return;
    }

    public String instrumentTypeCheck() {
        Object _ret = this.riInvokeExportedMethod(this,"instrumentTypeCheck",null,null);
        return (String)_ret;
    }

    public Integer on_load_page(String cld_id, Integer sloc_id, String org_id, Integer usr_id) {
        Object _ret =
            this.riInvokeExportedMethod(this,"on_load_page",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.Integer"},new Object[] {cld_id, sloc_id, org_id, usr_id});
        return (Integer)_ret;
    }

    public String procTaxForHdr(String ruleId) {
        Object _ret = this.riInvokeExportedMethod(this,"procTaxForHdr",new String [] {"java.lang.String"},new Object[] {ruleId});
        return (String)_ret;
    }

    public String procTdsForHdr(String ruleid) {
        Object _ret = this.riInvokeExportedMethod(this,"procTdsForHdr",new String [] {"java.lang.String"},new Object[] {ruleid});
        return (String)_ret;
    }

    public String procTdsForHdrBill(String ruleid) {
        Object _ret = this.riInvokeExportedMethod(this,"procTdsForHdrBill",new String [] {"java.lang.String"},new Object[] {ruleid});
        return (String)_ret;
    }

    public void refreshAdjustedAmount() {
        Object _ret = this.riInvokeExportedMethod(this,"refreshAdjustedAmount",null,null);
        return;
    }

    public void refreshDetailAdjustedAmountValue() {
        Object _ret = this.riInvokeExportedMethod(this,"refreshDetailAdjustedAmountValue",null,null);
        return;
    }

    public void removeAutoAdjustment() {
        Object _ret = this.riInvokeExportedMethod(this,"removeAutoAdjustment",null,null);
        return;
    }

    public void resetBalTransactionAmount() {
        Object _ret = this.riInvokeExportedMethod(this,"resetBalTransactionAmount",null,null);
        return;
    }

    public void saveAsGL() {
        Object _ret = this.riInvokeExportedMethod(this,"saveAsGL",null,null);
        return;
    }

    public void saveAsTemplate(String vouId, String name) {
        Object _ret =
            this.riInvokeExportedMethod(this,"saveAsTemplate",new String [] {"java.lang.String","java.lang.String"},new Object[] {vouId, name});
        return;
    }

    public void selectAllAdvance() {
        Object _ret = this.riInvokeExportedMethod(this,"selectAllAdvance",null,null);
        return;
    }

    public void selectAllAdvanceInAdjDtl() {
        Object _ret = this.riInvokeExportedMethod(this,"selectAllAdvanceInAdjDtl",null,null);
        return;
    }

    public void selectAllInvoices() {
        Object _ret = this.riInvokeExportedMethod(this,"selectAllInvoices",null,null);
        return;
    }

    public void setAdjustmentSummaryCol() {
        Object _ret = this.riInvokeExportedMethod(this,"setAdjustmentSummaryCol",null,null);
        return;
    }

    public void setAutoAdjustGlobalParameters(String p_cld_id, Integer p_slc_id, String p_ho_id, String p_org_id,
                                              Integer p_coa_id, Integer Eo_mst_id, String Entity_wise) {
        Object _ret =
            this.riInvokeExportedMethod(this,"setAutoAdjustGlobalParameters",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.String","java.lang.Integer","java.lang.Integer","java.lang.String"},new Object[] {p_cld_id, p_slc_id, p_ho_id, p_org_id, p_coa_id, Eo_mst_id, Entity_wise});
        return;
    }

    public void setCheckFlagOnArap() {
        Object _ret = this.riInvokeExportedMethod(this,"setCheckFlagOnArap",null,null);
        return;
    }

    public void setGlobalParameters(String p_cld_id, String p_org_id, String p_ho_id, Integer p_slc_id) {
        Object _ret =
            this.riInvokeExportedMethod(this,"setGlobalParameters",new String [] {"java.lang.String","java.lang.String","java.lang.String","java.lang.Integer"},new Object[] {p_cld_id, p_org_id, p_ho_id, p_slc_id});
        return;
    }

    public String setTvouAdjAmtThs(oracle.jbo.domain.Number inputAmt) {
        Object _ret =
            this.riInvokeExportedMethod(this,"setTvouAdjAmtThs",new String [] {"oracle.jbo.domain.Number"},new Object[] {inputAmt});
        return (String)_ret;
    }

    public void setTvouHdr(String tvouId) {
        Object _ret = this.riInvokeExportedMethod(this,"setTvouHdr",new String [] {"java.lang.String"},new Object[] {tvouId});
        return;
    }

    public void setTvouHdrBindParameter(String tvouId) {
        Object _ret = this.riInvokeExportedMethod(this,"setTvouHdrBindParameter",new String [] {"java.lang.String"},new Object[] {tvouId});
        return;
    }

    public void setTvouHdrParameter() {
        Object _ret = this.riInvokeExportedMethod(this,"setTvouHdrParameter",null,null);
        return;
    }

    public void setTvouTemplateHeader(String TvouId, String TemplateId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"setTvouTemplateHeader",new String [] {"java.lang.String","java.lang.String"},new Object[] {TvouId, TemplateId});
        return;
    }

    public void taxConsolidation() {
        Object _ret = this.riInvokeExportedMethod(this,"taxConsolidation",null,null);
        return;
    }

    public void taxConsolidationExpense() {
        Object _ret = this.riInvokeExportedMethod(this,"taxConsolidationExpense",null,null);
        return;
    }

    public void tdsConsolidation() {
        Object _ret = this.riInvokeExportedMethod(this,"tdsConsolidation",null,null);
        return;
    }

    public void tdsConsolidationExpense() {
        Object _ret = this.riInvokeExportedMethod(this,"tdsConsolidationExpense",null,null);
        return;
    }

    public void thisAdjAmount(oracle.jbo.domain.Number amount) {
        Object _ret = this.riInvokeExportedMethod(this,"thisAdjAmount",new String [] {"oracle.jbo.domain.Number"},new Object[] {amount});
        return;
    }

    public void tvouLineOp() {
        Object _ret = this.riInvokeExportedMethod(this,"tvouLineOp",null,null);
        return;
    }

    public void tvouOtherCharges() {
        Object _ret = this.riInvokeExportedMethod(this,"tvouOtherCharges",null,null);
        return;
    }

    public void tvoudr() {
        Object _ret = this.riInvokeExportedMethod(this,"tvoudr",null,null);
        return;
    }

    public void unCheckAllAdvances() {
        Object _ret = this.riInvokeExportedMethod(this,"unCheckAllAdvances",null,null);
        return;
    }

    public String updateChqNo(Integer p_sloc_id, Integer p_bnk_id, Integer p_chq_buk_id,
                              oracle.jbo.domain.Number p_chq_no, String p_org_id, Integer p_doc_id, String p_vou_id,
                              Date p_vou_dt, Integer p_usr_id, Integer p_chq_bk_stat, Date p_chq_bk_stat_dt,
                              Date p_mod_dt, String p_post_flg) {
        Object _ret =
            this.riInvokeExportedMethod(this,"updateChqNo",new String [] {"java.lang.Integer","java.lang.Integer","java.lang.Integer","oracle.jbo.domain.Number","java.lang.String","java.lang.Integer","java.lang.String","oracle.jbo.domain.Date","java.lang.Integer","java.lang.Integer","oracle.jbo.domain.Date","oracle.jbo.domain.Date","java.lang.String"},new Object[] {p_sloc_id, p_bnk_id, p_chq_buk_id, p_chq_no, p_org_id, p_doc_id, p_vou_id, p_vou_dt, p_usr_id, p_chq_bk_stat, p_chq_bk_stat_dt, p_mod_dt, p_post_flg});
        return (String)_ret;
    }

    public void updateTvouNarration(String VouId) {
        Object _ret = this.riInvokeExportedMethod(this,"updateTvouNarration",new String [] {"java.lang.String"},new Object[] {VouId});
        return;
    }

    public String validateChqNo(Integer p_sloc_id, Integer p_bnk_id, Integer p_chq_buk_id,
                                oracle.jbo.domain.Number p_chq_no) {
        Object _ret =
            this.riInvokeExportedMethod(this,"validateChqNo",new String [] {"java.lang.Integer","java.lang.Integer","java.lang.Integer","oracle.jbo.domain.Number"},new Object[] {p_sloc_id, p_bnk_id, p_chq_buk_id, p_chq_no});
        return (String)_ret;
    }

    public void validateTempVou() {
        Object _ret = this.riInvokeExportedMethod(this,"validateTempVou",null,null);
        return;
    }

    public Integer validateTempVouForGl() {
        Object _ret = this.riInvokeExportedMethod(this,"validateTempVouForGl",null,null);
        return (Integer)_ret;
    }

    public void voucherDeleteWdtId(String cld_id, Integer sloc_id, String ho_org, String org_id, Integer usr_id,
                                   String mode_id) {
        Object _ret =
            this.riInvokeExportedMethod(this,"voucherDeleteWdtId",new String [] {"java.lang.String","java.lang.Integer","java.lang.String","java.lang.String","java.lang.Integer","java.lang.String"},new Object[] {cld_id, sloc_id, ho_org, org_id, usr_id, mode_id});
        return;
    }
}
