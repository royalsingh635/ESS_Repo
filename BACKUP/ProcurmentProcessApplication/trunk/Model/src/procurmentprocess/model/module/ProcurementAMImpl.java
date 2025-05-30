package procurmentprocess.model.module;


import java.sql.SQLException;

import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.el.ValueExpression;


import oracle.adf.share.ADFContext;

import oracle.jbo.Row;

import oracle.jbo.domain.Timestamp;

import procurmentprocess.model.module.common.ProcurementAM;
import procurmentprocess.model.view.DrftPOVOImpl;
import procurmentprocess.model.view.DualVOImpl;


import procurmentprocess.model.view.QuotationSrchVOImpl;
import procurmentprocess.model.view.RFQSrchVOImpl;

import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewLinkImpl;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Jul 23 10:13:54 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ProcurementAMImpl extends ApplicationModuleImpl implements ProcurementAM {
    /**
     * This is the default constructor (do not remove).
     */
    public ProcurementAMImpl() {
    }
    String OrgId=resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
    /**
     * Container's getter for DrftPO1.
     * @return DrftPO1
     */
    public DrftPOVOImpl getDrftPO1() {
        return (DrftPOVOImpl)findViewObject("DrftPO1");
    }

    /**
     * Container's getter for Dual1.
     * @return Dual1
     */
    public ViewObjectImpl getDual1() {
        return (ViewObjectImpl)findViewObject("Dual1");
    }

    /**
     * Container's getter for PoMode1.
     * @return PoMode1
     */
    public ViewObjectImpl getPoMode1() {
        return (ViewObjectImpl)findViewObject("PoMode1");
    }

    /**
     * Container's getter for Supplier1.
     * @return Supplier1
     */
    public ViewObjectImpl getSupplier1() {
        return (ViewObjectImpl)findViewObject("Supplier1");
    }

    public void blank_data() {
        ViewObjectImpl v = getDrftPO1();
        v.setWhereClause("1=2");
        v.executeQuery();
    }

    /**
     * Container's getter for RFQtrns1.
     * @return RFQtrns1
     */
    public ViewObjectImpl getRFQtrns1() {
        return (ViewObjectImpl)findViewObject("RFQtrns1");
    }

    /**
     * Container's getter for RFQStatusLOV1.
     * @return RFQStatusLOV1
     */
    public ViewObjectImpl getRFQStatusLOV1() {
        return (ViewObjectImpl)findViewObject("RFQStatusLOV1");
    }

    /**
     * Container's getter for RFQSrch1.
     * @return RFQSrch1
     */
    public RFQSrchVOImpl getRFQSrch1() {
        return (RFQSrchVOImpl)findViewObject("RFQSrch1");
    }

    /**
     * Container's getter for RFQSupplier1.
     * @return RFQSupplier1
     */
    public ViewObjectImpl getRFQSupplier1() {
        return (ViewObjectImpl)findViewObject("RFQSupplier1");
    }

    /**
     * Container's getter for RFQSrch2.
     * @return RFQSrch2
     */
    public RFQSrchVOImpl getRFQSrch2() {
        return (RFQSrchVOImpl)findViewObject("RFQSrch2");
    }

    /**
     * Container's getter for RFQSupplier2.
     * @return RFQSupplier2
     */
    public ViewObjectImpl getRFQSupplier2() {
        return (ViewObjectImpl)findViewObject("RFQSupplier2");
    }

    /**
     * Container's getter for RFQEOVL.
     * @return RFQEOVL
     */
    public ViewLinkImpl getRFQEOVL() {
        return (ViewLinkImpl)findViewLink("RFQEOVL");
    }

    /**
     * Container's getter for RFQSupplier3.
     * @return RFQSupplier3
     */
    public ViewObjectImpl getRFQSupplier3() {
        return (ViewObjectImpl)findViewObject("RFQSupplier3");
    }

    /**
     * Container's getter for RFQEOVL1.
     * @return RFQEOVL1
     */
    public ViewLinkImpl getRFQEOVL1() {
        return (ViewLinkImpl)findViewLink("RFQEOVL1");
    }

    /**
     * Container's getter for QuotationSrch1.
     * @return QuotationSrch1
     */
    public QuotationSrchVOImpl getQuotationSrch1() {
        return (QuotationSrchVOImpl)findViewObject("QuotationSrch1");
    }

    /**
     * Container's getter for QuotTrns1.
     * @return QuotTrns1
     */
    public ViewObjectImpl getQuotTrns1() {
        return (ViewObjectImpl)findViewObject("QuotTrns1");
    }

    /**
     * Container's getter for QuotStatus1.
     * @return QuotStatus1
     */
    public ViewObjectImpl getQuotStatus1() {
        return (ViewObjectImpl)findViewObject("QuotStatus1");
    }

    /**
     * Container's getter for QuotationAnalysisSearch1.
     * @return QuotationAnalysisSearch1
     */
    public ViewObjectImpl getQuotationAnalysisSearch1() {
        return (ViewObjectImpl)findViewObject("QuotationAnalysisSearch1");
    }

    /**
     * Container's getter for MMQuotationAnalysisvw1.
     * @return MMQuotationAnalysisvw1
     */
    public ViewObjectImpl getMMQuotationAnalysisvw1() {
        return (ViewObjectImpl)findViewObject("MMQuotationAnalysisvw1");
    }

    // SEARCH FOR QUOTATION ANALYSIS-----------------

    public void searchQuotationAnalysis() {
        ViewObjectImpl anavo = this.getMMQuotationAnalysisvw1();
        ViewObjectImpl srch = this.getQuotationAnalysisSearch1();
        Row currentRow = srch.getCurrentRow();
      
        anavo.setNamedWhereClauseParam("BINDORGID", currentRow.getAttribute("OrgId"));
        anavo.setNamedWhereClauseParam("BINDSLOCID", currentRow.getAttribute("SlocId"));
        anavo.setNamedWhereClauseParam("BINDCLDID", currentRow.getAttribute("CldId"));
        System.out.println("QuotAnalysis........orgId....."+currentRow.getAttribute("OrgId"));
        if (currentRow.getAttribute("QuotNoDocId") != null) {
            anavo.setNamedWhereClauseParam("BindQuotDocId", currentRow.getAttribute("QuotNoDocId").toString());
        } else {
            anavo.setNamedWhereClauseParam("BindQuotDocId", null);
        }
        if (currentRow.getAttribute("RfqNoDocId") != null) {
            anavo.setNamedWhereClauseParam("BindRfqDocId", currentRow.getAttribute("RfqNoDocId").toString());
        } else {
            anavo.setNamedWhereClauseParam("BindRfqDocId", null);
        }
        if (currentRow.getAttribute("SupplierId") != null) {
            anavo.setNamedWhereClauseParam("BindSupplierId",
                                           Integer.parseInt(currentRow.getAttribute("SupplierId").toString()));
        } else {
            anavo.setNamedWhereClauseParam("BindSupplierId", null);
        }
        if (currentRow.getAttribute("EvalId") != null) {
            anavo.setNamedWhereClauseParam("BindEvalId", currentRow.getAttribute("EvalId").toString());
        } else {
            anavo.setNamedWhereClauseParam("BindEvalId", null);
        }
        if (currentRow.getAttribute("EvalFrmDate") != null) {
            anavo.setNamedWhereClauseParam("BindFrmDate", currentRow.getAttribute("EvalFrmDate"));
        } else {
            anavo.setNamedWhereClauseParam("BindFrmDate", null);
        }
        if (currentRow.getAttribute("EvalToDate") != null) {
            anavo.setNamedWhereClauseParam("BindToDate", currentRow.getAttribute("EvalToDate"));
        } else {
            anavo.setNamedWhereClauseParam("BindToDate", null);
        }
        anavo.executeQuery();

    }
    // Reset For Quotation Analysis--------

    public void resetQuotationAnalysis() {
        ViewObjectImpl srch = this.getQuotationAnalysisSearch1();
        Row currentRow = srch.getCurrentRow();
        currentRow.setAttribute("EvalToDate", null);
        currentRow.setAttribute("EvalFrmDate", null);
        currentRow.setAttribute("EvalId", null);
        currentRow.setAttribute("SupplierId", null);
        currentRow.setAttribute("RfqNoDocId", null);
        currentRow.setAttribute("QuotNoDocId", null);
        currentRow.setAttribute("OrgId", OrgId);
        srch.executeQuery();
        this.getMMQuotationAnalysisvw1().setNamedWhereClauseParam("BindQuotDocId", -1);
        this.getMMQuotationAnalysisvw1().executeQuery();
    }


    // SEARCH FOR CashPurchaseOrder---------------------

    public void searchCashPurchaseOrder() {
        
        ViewObjectImpl srch = this.getCpoSearch1();
        Row currentRow = srch.getCurrentRow();
        ViewObjectImpl cpo = this.getCashPurchaseOrder1();
        cpo.setNamedWhereClauseParam("BINDORGID", currentRow.getAttribute("OrgId"));;
        System.out.println("cpo...........orgId......."+OrgId+"...."+currentRow.getAttribute("OrgId"));
        if (currentRow.getAttribute("CpoDocId") != null) {
            cpo.setNamedWhereClauseParam("BINDDOCID", currentRow.getAttribute("CpoDocId").toString());
        } else {
            cpo.setNamedWhereClauseParam("BINDDOCID", null);
        }
        if (currentRow.getAttribute("FrmAmt") != null) {
            cpo.setNamedWhereClauseParam("BINDFRMAMT", currentRow.getAttribute("FrmAmt"));
        } else {
            cpo.setNamedWhereClauseParam("BINDFRMAMT", null);
        }
        if (currentRow.getAttribute("ToAmt") != null) {
            cpo.setNamedWhereClauseParam("BINDTOAMT", currentRow.getAttribute("ToAmt"));
        } else {
            cpo.setNamedWhereClauseParam("BINDTOAMT", null);
        }
        if (currentRow.getAttribute("CpoFrmDate") != null) {
            System.out.println("frm date--"+currentRow.getAttribute("CpoFrmDate"));
            cpo.setNamedWhereClauseParam("BINDFROMDATE", currentRow.getAttribute("CpoFrmDate"));
        } else {
            cpo.setNamedWhereClauseParam("BINDFROMDATE", null);
        }
        if (currentRow.getAttribute("CpoToDate") != null) {
            System.out.println("todate--"+currentRow.getAttribute("CpoToDate"));
            cpo.setNamedWhereClauseParam("BINDTODATE", currentRow.getAttribute("CpoToDate"));
        } else {
            cpo.setNamedWhereClauseParam("BINDTODATE", null);
        }
        if (currentRow.getAttribute("ItemId") != null) {
            cpo.setNamedWhereClauseParam("BINDITMID", currentRow.getAttribute("ItemId"));
        } else {
            cpo.setNamedWhereClauseParam("BINDITMID", null);
        }
        if (currentRow.getAttribute("CoaId") != null) {
            cpo.setNamedWhereClauseParam("BINDCOAID", currentRow.getAttribute("CoaId"));
        } else {
            cpo.setNamedWhereClauseParam("BINDCOAID", null);
        }
        cpo.executeQuery();
    }
    
    
    // Reset FOR CashPurchaseOrder---------------------
    
    public void resetCashpurchaseOrder(){
        ViewObjectImpl srch = this.getCpoSearch1();
        Row currentRow = srch.getCurrentRow();
        currentRow.setAttribute("OrgId", OrgId);
        currentRow.setAttribute("CoaId", null);
        currentRow.setAttribute("ItemId", null);
        currentRow.setAttribute("CpoToDate", null);
        currentRow.setAttribute("CpoFrmDate", null);
        currentRow.setAttribute("ToAmt", null);
        currentRow.setAttribute("FrmAmt", null);
        currentRow.setAttribute("CpoDocId", null);
        srch.executeQuery();
        
        this.getCashPurchaseOrder1().setNamedWhereClauseParam("BINDDOCID", -1);
        this.getCashPurchaseOrder1().executeQuery();
    }
    
    public String resolvEl(String data) {
           ADFContext adfCtx = ADFContext.getCurrent();
          ELContext elContext = adfCtx.getELContext();
          ValueExpression valueExp = adfCtx.getExpressionFactory().createValueExpression(elContext, data, Object.class);
          Object Message = valueExp.getValue(elContext).toString();
          return Message.toString();
      }
   
    
    // SEARCH FOR SuggestedOrder---------------------
    public void searchSuggestedOreder(){
       
       System.out.println("orgid................"+OrgId);
        ViewObjectImpl so = this.getSuggestedOrder1();
        ViewObjectImpl srch = this.getSuggestedOrderSearch1();
        Row currentRow = srch.getCurrentRow();
        System.out.println("orgid................"+ currentRow.getAttribute("OrgId"));
        so.setNamedWhereClauseParam("BINDORGID", currentRow.getAttribute("OrgId"));
        so.setNamedWhereClauseParam("BINDSODOCID", currentRow.getAttribute("SoDocId"));
        so.setNamedWhereClauseParam("BINDFROMDATE",currentRow.getAttribute("SoFrmDate"));
        so.setNamedWhereClauseParam("BINDTODATE", currentRow.getAttribute("SoToDate"));
        so.setNamedWhereClauseParam("BINDEOID", currentRow.getAttribute("SupplierId"));
        so.setNamedWhereClauseParam("BINDORDERPODOCID", currentRow.getAttribute("OrderPoNoDocId"));
        so.setNamedWhereClauseParam("BINDITEMID", currentRow.getAttribute("ItemId"));
        so.executeQuery();
    }
    // Reset For SuggestdOrder----
    public void resetSuggestedOrder(){
        ViewObjectImpl srch = this.getSuggestedOrderSearch1();
        Row currentRow = srch.getCurrentRow();
        currentRow.setAttribute("OrgId", OrgId);
        currentRow.setAttribute("SoDocId", null);
        currentRow.setAttribute("SoFrmDate", null);
        currentRow.setAttribute("SoToDate", null);
        currentRow.setAttribute("SupplierId", null);
        currentRow.setAttribute("OrderPoNoDocId", null);
        currentRow.setAttribute("ItemId", null);
        srch.executeQuery();
        this.getSuggestedOrder1().setNamedWhereClauseParam("BINDSODOCID", -1);
        this.getSuggestedOrder1().executeQuery();
    }

    /**
     * Container's getter for CashPurchaseOrder1.
     * @return CashPurchaseOrder1
     */
    public ViewObjectImpl getCashPurchaseOrder1() {
        return (ViewObjectImpl)findViewObject("CashPurchaseOrder1");
    }

    /**
     * Container's getter for CpoSearch1.
     * @return CpoSearch1
     */
    public ViewObjectImpl getCpoSearch1() {
        return (ViewObjectImpl)findViewObject("CpoSearch1");
    }

    /**
     * Container's getter for SuggestedOrderSearch1.
     * @return SuggestedOrderSearch1
     */
    public ViewObjectImpl getSuggestedOrderSearch1() {
        return (ViewObjectImpl)findViewObject("SuggestedOrderSearch1");
    }

    /**
     * Container's getter for SuggestedOrder1.
     * @return SuggestedOrder1
     */
    public ViewObjectImpl getSuggestedOrder1() {
        return (ViewObjectImpl)findViewObject("SuggestedOrder1");
    }
 

    /**
     * Container's getter for LOVCpoId1.
     * @return LOVCpoId1
     */
    public ViewObjectImpl getLOVCpoId1() {
        return (ViewObjectImpl)findViewObject("LOVCpoId1");
    }
}
