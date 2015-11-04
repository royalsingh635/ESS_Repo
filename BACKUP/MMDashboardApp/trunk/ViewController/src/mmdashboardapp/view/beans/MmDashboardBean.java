package mmdashboardapp.view.beans;

import java.math.BigDecimal;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import mmdashboardapp.model.ds.TickerRowDS;

import mmdashboardapp.view.ds.TickerDispDS;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputNumberSlider;

import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class MmDashboardBean {
    private RichInputNumberSlider suppNum = new RichInputNumberSlider();
    private RichInputNumberSlider productNum = new RichInputNumberSlider();
    private RichInputNumberSlider productGrpNum = new RichInputNumberSlider();
    private StringBuffer desc = new StringBuffer("");
    private StringBuffer docIdForQuot = new StringBuffer("");
    private String facetValue = "mrs";
    private String facetValuePo = "po";
    private String facetValueRfqPage = "rfq";
    private UIXSwitcher switcherBind;
    private Number grossCurrentTotalStock = new Number(0);
    private Number grossPreviousTotalStock = new Number(0);
    private Number grossCurrentTotalInvoiceValue = new Number(0);
    private Number grossPreviousTotalInvoiceValue = new Number(0);
    private Number grossCurrentTotalPurchase = new Number(0);
    private Number grossPreviousTotalPurchase = new Number(0);
    private RichPopup tkrSettingsPopup;
    Number million = new Number(1000000);
    Number thousand = new Number(1000);
    Number zero = new Number(0);
    private String firstTkrCurrAmtNotation = "";
    private String firstTkrPrvsAmtNotation = "";
    private String secondTkrCurrAmtNotation = "";
    private String secondTkrPrvsAmtNotation = "";
    private String thirdTkrCurrAmtNotation = "";
    private String thirdTkrPrvsAmtNotation = "";
    private ArrayList<TickerRowDS> tickerList = new ArrayList<TickerRowDS>();
    private ArrayList<TickerRowDS> selectedList = new ArrayList<TickerRowDS>();
    private UIXSwitcher switcherBindRfqPage;

    public MmDashboardBean() {
    } 
    
    /**
     * Method to getBindings
     * @return
     */
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    /** ###################################  Get Count Of Pending Various Documents for my Approval    ##########################
    
    /**
     * Method to get Quotation Count Pending for my Approval
     * @return
     */
    public Number getRFQPendingForMyApprovalCount(){
         OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18502);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
        }   
    }
    
    /**
     * Method to get Quotation Count Pending for my Approval
     * @return
     */
    public Number getQuotationPendingForMyApprovalCount(){
     OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18503);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{  
            return new Number(0);    
      }   
    }
    
    /**
     * Method to get Quatation Analysis Count Pending for my Approval
     * @return
     */
    public Number getQuatAnaPendingForMyApprovalCount(){
     OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18510);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
       }   
    }
    
    /**
     * Method to get Purchase Order Count Pending for my Approval
     * @return
     */
    public Number getPOPendingForMyApprovalCount(){
       OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18504);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
        }   
    }
    
    /**
     * Method to get Material Receipt Count Pending for my Approval
     * @return
     */
    public Number getReceiptPendingForMyApprovalCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18515);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
       }   
    }
    /**
     * Method to get MRS Count Pending for my Approval
     * @return
     */
    public Number getMRSForMyApprovalCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18513);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{
            return new Number(0);    
        }   
    }
    /**
     * Method to get Transfer Order Count Pending for my Approval
     * @return
     */
    
    public Number getTOForMyApprovalCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18519);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{
            return new Number(0);    
       }   
    }
    
    /**
     * Method to get Stack Take Count Pending for my Approval
     * @return
     */
    public Number getStockTakePendingForMyApprovalCount(){
       OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18518);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
        }   
    }
    
    /**
     * Method to get Stock Adjustment Count Pending for my Approval
     * @return
     */
    public Number getStockAdjstPendingForMyApprovalCount(){
         OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18517);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
       }   
    }
    
    
    /**
     * Method to get Purchase Return Count Pending for my Approval
     * @return
     */
    
    public Number getPRForMyApprovalCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18529);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{
            return new Number(0);    
       }   
    }
    
    /**
     * Method to get Material Return Note Count Pending for my Approval
     * @return
     */
    public Number getMRNPendingForMyApprovalCount(){
       /* OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18518);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
        }    */
        return new Number(0);    
    }
    
    /**
     * Method to get Purchase Invoice Count Pending for my Approval
     * @return
     */
    public Number getInvoicePendingForMyApprovalCount(){
       /*   OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18521);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
       }    */
        return new Number(0);    
    }
    
    /** ###################################  Get Count Of Pending Various Documents for Other User Approval    ##########################  
    
    /**
     * Method to get RFQ Count Pending for other Approval
     * @return
     */
    public Number getRFQPendingForOtherApprovalCount(){
          OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18502);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
        }   
    }
    
    /**
     * Method to get Quotation Count Pending for Other Approval
     * @return
     */
    public Number getQuotationPendingForOtherApprovalCount(){
      OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18503);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{
            return new Number(0);    
      }   
    }
    
    /**
     * Method to get Quatation Analysis Count Pending for Other Approval
     * @return
     */
    public Number getQuatAnaPendingForOtherApprovalCount(){
       OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18510);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
       }   
    }
    
    /**
     * Method to get Purchase Order Count Pending for Other Approval
     * @return
     */
    public Number getPOPendingForOtherApprovalCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18504);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
        }   
    }
    
    /**
     * Method to get Material Receipt Count Pending for Other Approval
     * @return
     */
    public Number getReceiptPendingForOtherApprovalCount(){
         OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18515);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
        }   
    }
    /**
     * Method to get MRS Count Pending for Other Approval
     * @return
     */
    public Number getMRSForOtherApprovalCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18513);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
        }   
    }
    /**
     * Method to get Transfer Order Count Pending for Other Approval
     * @return
     */
    
    public Number getTOForOtherApprovalCount(){
       OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18519);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
       }   
    }
    
    /**
     * Method to get Stack Take Count Pending for Other Approval
     * @return
     */
    public Number getStockTakePendingForOtherApprovalCount(){
         OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18518);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
        }   
    }
    
    /**
     * Method to get Stock Adjustment Count Pending for Other Approval
     * @return
     */
    public Number getStockAdjstPendingForOtherApprovalCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18517);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
        }   
    }
    
    
    /**
     * Method to get Purchase Return Count Pending for Other Approval
     * @return
     */
    
    public Number getPRForOtherApprovalCount(){
       OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18529);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
       }   
    }
    
    /**
     * Method to get Material Return Note Count Pending for Other Approval
     * @return
     */
    public Number getMRNPendingForOtherApprovalCount(){
        /*  OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18528);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
        } */   
        return new Number(0); 
    }
    
    /**
     * Method to get purchase Invoice Count Pending for Other Approval
     * @return
     */
    public Number getInvoicePendingForOtherApprovalCount(){
       /*  OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18521);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
        }    */
       return new Number(0);    
    }
    
    /** ###################################  Get Count Of  Various Unpostedn Documents for all User Approval    ##########################  
    
    /**
     * Method to get RFQ Count Pending for UnpostedDocuments
     * @return
     */
    public Number getRFQUnpostedDocumentsCount(){
         OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18502);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
     }   
    }
    
    /**
     * Method to get Quotation Count Pending for UnpostedDocuments
     * @return
     */
    public Number getQuotationUnpostedDocumentsCount(){
       OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18503);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{
            return new Number(0);    
       }   
    }
    
    /**
     * Method to get Quatation Analysis Count Pending for UnpostedDocuments
     * @return
     */
    public Number getQuatAnaUnpostedDocumentsCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18510);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
        }   
    }
    
    /**
     * Method to get Purchase Order Count Pending for UnpostedDocuments
     * @return
     */
    public Number getPOUnpostedDocumentsCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18504);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
        }   
    }
    
    /**
     * Method to get Material Receipt Count Pending for UnpostedDocuments
     * @return
     */
    public Number getReceiptUnpostedDocumentsCount(){
         OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18515);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
        }   
    }
    /**
     * Method to get MRS Count Pending for UnpostedDocuments
     * @return
     */
    public Number getMRSUnpostedDocumentsCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18513);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
        }   
    }
    /**
     * Method to get Transfer Order Count Pending for UnpostedDocuments
     * @return
     */
    
    public Number getTOUnpostedDocumentsCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18519);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
       }   
    }
    
    /**
     * Method to get Stack Take Count Pending for UnpostedDocuments
     * @return
     */
    public Number getStockTakeUnpostedDocumentsCount(){
         OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18518);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
        }   
    }
    
    /**
     * Method to get Stock Adjustment Count Pending for UnpostedDocuments
     * @return
     */
    public Number getStockAdjstUnpostedDocumentsCount(){
         OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18517);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
        }   
    }
    
    /**
     * Method to get Purchase Return Count Pending for UnpostedDocuments
     * @return
     */
    
    public Number getPRUnpostedDocumentsCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18529);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
       }   
    }
    
    /**
     * Method to get Material Return Note Count Pending for UnpostedDocuments
     * @return
     */
    public Number getMRNUnpostedDocumentsCount(){
        /*  OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18528);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
        }    */
        return new Number(0); 
    }
    
    /**
     * Method to get Purchase Invoice Count Pending for UnpostedDocuments
     * @return
     */
    public Number getInvoiceUnpostedDocumentsCount(){
       /*   OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18521);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{ 
            return new Number(0);    
        }    */
       return new Number(0); 
    }
    

    public void top5SuppliersTabDiscloserList(DisclosureEvent disclosureEvent) {
         OperationBinding binding = this.getBindings().getOperationBinding("executeTopSuppliersRating");
                binding.getParamsMap().put("val", 5);
                binding.execute();
                suppNum.setValue(5);
        
    }
    
    public void top5ProductsTabDiscloserList(DisclosureEvent disclosureEvent) {
         OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProductVO");
                binding.getParamsMap().put("val", 5);
                binding.execute();
                productNum.setValue(5);
        
    }


    public void top5ProductGrpsTabDiscloserList(DisclosureEvent disclosureEvent) {
         OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProductGrpVO");
                binding.getParamsMap().put("val", 5);
                binding.execute();
                productGrpNum.setValue(5);
        
    }


    public void setSuppNum(RichInputNumberSlider suppNum) {
        this.suppNum = suppNum;
    }

    public RichInputNumberSlider getSuppNum() {
        return suppNum;
    }

    public void noOfSupplierSliderVCL(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent != null){
            BigDecimal count = (BigDecimal)valueChangeEvent.getNewValue();
            int i = count.intValue();
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopSuppliersRating");
            binding.getParamsMap().put("val", i);
            binding.execute();
        }
    }
    public void noOfProductSliderVCL(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent != null){
            BigDecimal count = (BigDecimal)valueChangeEvent.getNewValue();
            int i = count.intValue();
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProductVO");
            binding.getParamsMap().put("val", i);
            binding.execute();
        }
    }
    public void noOfProductGrpSliderVCL(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent != null){
            BigDecimal count = (BigDecimal)valueChangeEvent.getNewValue();
            int i = count.intValue();
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProductGrpVO");
            binding.getParamsMap().put("val", i);
            binding.execute();
        }
    }
    public String quotationForMyApprovalAction() {
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFQuotationView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        return "wfQuotationView";
        
    }
    public String quotationForOtherApprovalAction() {
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFQuotationView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        return "wfQuotationView";
        
    }
    public String quotationUnpostedAction() {
        desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFQuotationView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        return "wfQuotationView";
        
    }

    public void setDesc(StringBuffer desc) {
        this.desc = desc;
    }

    public StringBuffer getDesc() {
        return desc;
    }

    public void setDocIdForQuot(StringBuffer docIdForQuot) {
        this.docIdForQuot = docIdForQuot;
    }

    public StringBuffer getDocIdForQuot() {
        return docIdForQuot;
    }

    public String viewQuotationAction() {
        this.docIdForQuot = new StringBuffer("");
        OperationBinding binding = this.getBindings().getOperationBinding("getCurrDocIdfoQuot");
        binding.execute();
        if(binding.getResult() != null && !binding.getResult().toString().equals("")){
            this.docIdForQuot = new StringBuffer(binding.getResult().toString());    
        }
        if(this.docIdForQuot.toString().equals("")){
            return null;
        }else{
            return "viewQuotationPage";
        }
    }
    
    public String rfqForMyApprovalAction() {
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFRfqView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValueRfqPage("rfq");
        facetValueRfqPage = "rfq";
        return "wfRfqView";
    }

    public String rfqForOtherApprovalAction() {
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFRfqView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValueRfqPage("rfq");
        facetValueRfqPage = "rfq";
        return "wfRfqView";
    }

    public String rfqForMyUnpostedAction() {
        desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFRfqView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValueRfqPage("rfq");
        facetValueRfqPage = "rfq";
        return "wfRfqView";
    }

    public String poForMyApprovalAction() {
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFPurOrdView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValuePo("po");
        facetValuePo="po";
        return "wfPurOrdView";
    }

    public String poForOtherApprovalAction() {
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFPurOrdView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValuePo("po");
        facetValuePo="po";
        return "wfPurOrdView";
    }

    public String poForMyUnpostedAction() {
        desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFPurOrdView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValuePo("po");
        facetValuePo="po";
        return "wfPurOrdView";
    }

    public String quotAnaForMyApprovalAction() {
        // Add event code here...
        return null;
    }

    public String quotAnaForOtherApprovalAction() {
        // Add event code here...
        return null;
    }

    public String quotAnaForMyUnpostedAction() {
        // Add event code here...
        return null;
    }

    public String viewPurOrdAction() {
        return "viewPurOrdPage";
    }

    public String trfOrdForMyApprovalAction() {
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFTrfOrdView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValue("trfOrd");
        facetValue="trfOrd";
        return "viewTrfOrdView";
    }

    public String trfOrdForOtherApprovalAction() {
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFTrfOrdView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValue("trfOrd");
        facetValue="trfOrd";
        return "viewTrfOrdView";
    }

    public String trfOrdForMyUnpostedAction() {
        desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFTrfOrdView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValue("trfOrd");
        facetValue="trfOrd";
        return "viewTrfOrdView";
    }

    public String mrsForMyApprovalAction() {
       desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFMRSView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValue("mrs");
        facetValue="mrs";
        return "viewTrfOrdView"; 
       
    }

    public String mrsForOtherApprovalAction() {
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFMRSView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValue("mrs");
        facetValue="mrs";
        return "viewTrfOrdView"; 
    }

    public String mrsForMyUnpostedAction() {
         desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFMRSView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValue("mrs");
        facetValue="mrs";
        return "viewTrfOrdView"; 
    }

  
    public String rcptForMyApprovalAction() {
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFRcptView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValuePo("rcpt");
        facetValuePo="rcpt";
        return "wfPurOrdView";
    }

    public String rcptForOtherApprovalAction() {
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFRcptView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValuePo("rcpt");
        facetValuePo="rcpt";
        return "wfPurOrdView";
    }

    public String rcptForUnpostedAction() {
        desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFRcptView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValuePo("rcpt");
        facetValuePo="rcpt";
        return "wfPurOrdView";
    }
    
    public String stockTakeForMyApprovalAction() {
       desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFStockTakeView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValueRfqPage("stockTake");
        facetValueRfqPage = "stockTake";
        return "wfRfqView"; 
       
    }

    public String stockTakeForOtherApprovalAction() {
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFStockTakeView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValueRfqPage("stockTake");
        facetValueRfqPage = "stockTake";
        return "wfRfqView"; 
    }

    public String stockTakeForMyUnpostedAction() {
         desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFStockTakeView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValueRfqPage("stockTake");
        facetValueRfqPage = "stockTake";
        return "wfRfqView"; 
    }
    
    public String stockAdjForMyApprovalAction() {
       desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFStockAdjView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValueRfqPage("stockAdj");
        facetValueRfqPage = "stockAdj";
        return "wfRfqView"; 
       
    }

    public String stockAdjForOtherApprovalAction() {
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFStockAdjView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValueRfqPage("stockAdj");
        facetValueRfqPage = "stockAdj";
        return "wfRfqView"; 
    }

    public String stockAdjForMyUnpostedAction() {
         desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFStockAdjView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValueRfqPage("stockAdj");
        facetValueRfqPage = "stockAdj";
        return "wfRfqView"; 
    }

    public void setFacetValuePo(String facetValuePo) {
        this.facetValuePo = facetValuePo;
    }

    public String getFacetValuePo() {
        return facetValuePo;
    }
    
    public void setSwitcherBind(UIXSwitcher switcherBind) {
        this.switcherBind = switcherBind;
    }

    public UIXSwitcher getSwitcherBind() {
        return switcherBind;
    }

    public void setFacetValue(String facetValue) {
        this.facetValue = facetValue;
    }

    public String getFacetValue() {
        return facetValue;
    }


    public String tkrQuery() {
        OperationBinding tkrQuery = this.getBindings().getOperationBinding("executeTkrQuery");
       tkrQuery.getParamsMap().put("tkrId", "TKR001");
        tkrQuery.execute();
        return null;
    }
    public Number getGrossCurrentTotalStock(){
        OperationBinding tkridVal = this.getBindings().getOperationBinding("getTkrIdValue");
        tkridVal.getParamsMap().put("tkrPos", 1);
        tkridVal.execute();
        String tkrId = null;
        if(tkridVal.getResult()!=null){
            tkrId = tkridVal.getResult().toString();
        }
        if(tkrId!=null){
        OperationBinding tkrQuery = this.getBindings().getOperationBinding("executeTkrQuery");
        tkrQuery.getParamsMap().put("tkrId", tkrId);
        tkrQuery.execute();
        if(tkrQuery.getResult()!=null){
            ArrayList<Number> queryVal = new ArrayList<Number>();
            queryVal  =(ArrayList<Number>)tkrQuery.getResult();
            if(queryVal.size()>0){
            grossCurrentTotalStock = queryVal.get(0);
                if(grossCurrentTotalStock!=null){
                 if(grossCurrentTotalStock.compareTo(thousand) < 0){
                        this.setFirstTkrCurrAmtNotation(" ");
                   }else if(grossCurrentTotalStock.compareTo(thousand) > 0 && grossCurrentTotalStock.compareTo(million) <0 ){
                        this.setFirstTkrCurrAmtNotation("K");
                            grossCurrentTotalStock =(Number)grossCurrentTotalStock.divide(new Number(1000));
                    }else if(grossCurrentTotalStock.compareTo(million) > 0){
                        this.setFirstTkrCurrAmtNotation("M");
                            grossCurrentTotalStock =(Number)grossCurrentTotalStock.divide(new Number(1000000));
                    }
                }else{
                    grossCurrentTotalStock = zero;
                }
            grossPreviousTotalStock = queryVal.get(1);
                if(grossPreviousTotalStock!=null){
                 if(grossPreviousTotalStock.compareTo(thousand) < 0){
                        this.setFirstTkrPrvsAmtNotation(" ");
                   }else if(grossPreviousTotalStock.compareTo(thousand) > 0 && grossPreviousTotalStock.compareTo(million) <0 ){
                        this.setFirstTkrPrvsAmtNotation("K");
                            grossPreviousTotalStock =(Number)grossPreviousTotalStock.divide(new Number(1000));
                    }else if(grossPreviousTotalStock.compareTo(million) > 0){
                        this.setFirstTkrPrvsAmtNotation("M");
                            grossPreviousTotalStock =(Number)grossPreviousTotalStock.divide(new Number(1000000));
                    }
                }else{
                    grossPreviousTotalStock = zero;
                }
            }
           
        }
        }else{
            grossCurrentTotalStock = zero;
            grossPreviousTotalStock = zero;
        }
     return grossCurrentTotalStock;
    }
    
   public Number getGrossPreviousTotalStock(){
        return grossPreviousTotalStock;
    }
   
    public Number getGrossCurrentTotalInvoiceValue(){
        OperationBinding tkridVal = this.getBindings().getOperationBinding("getTkrIdValue");
        tkridVal.getParamsMap().put("tkrPos", 2);
        tkridVal.execute();
        String tkrId = null;
        if(tkridVal.getResult()!=null){
            tkrId = tkridVal.getResult().toString();
        }
        if(tkrId!=null){
    
        OperationBinding tkrQuery = this.getBindings().getOperationBinding("executeTkrQuery");
        tkrQuery.getParamsMap().put("tkrId",tkrId);
        tkrQuery.execute();
        if(tkrQuery.getResult()!=null){
            ArrayList<Number> queryVal = new ArrayList<Number>();
            queryVal  =(ArrayList<Number>)tkrQuery.getResult();

            if(queryVal.size()>0){
            grossCurrentTotalInvoiceValue = queryVal.get(0);
                if(grossCurrentTotalInvoiceValue!=null){
                 if(grossCurrentTotalInvoiceValue.compareTo(thousand) < 0){
                        this.setSecondTkrCurrAmtNotation(" ");
                   }else if(grossCurrentTotalInvoiceValue.compareTo(thousand) > 0 && grossCurrentTotalInvoiceValue.compareTo(million) <0 ){
                        this.setSecondTkrCurrAmtNotation("K");
                            grossCurrentTotalInvoiceValue =(Number)grossCurrentTotalInvoiceValue.divide(new Number(1000));
                    }else if(grossCurrentTotalInvoiceValue.compareTo(million) > 0){
                        this.setSecondTkrCurrAmtNotation("M");
                            grossCurrentTotalInvoiceValue =(Number)grossCurrentTotalInvoiceValue.divide(new Number(1000000));
                    }
                }else{
                    grossCurrentTotalInvoiceValue = zero;
                }
            grossPreviousTotalInvoiceValue = queryVal.get(1);
                if(grossPreviousTotalInvoiceValue!=null){
                 if(grossPreviousTotalInvoiceValue.compareTo(thousand) < 0){
                        this.setSecondTkrPrvsAmtNotation(" ");
                   }else if(grossPreviousTotalInvoiceValue.compareTo(thousand) > 0 && grossPreviousTotalInvoiceValue.compareTo(million) <0 ){
                        this.setSecondTkrPrvsAmtNotation("K");
                            grossPreviousTotalInvoiceValue =(Number)grossPreviousTotalInvoiceValue.divide(new Number(1000));
                    }else if(grossPreviousTotalInvoiceValue.compareTo(million) > 0){
                        this.setSecondTkrPrvsAmtNotation("M");
                            grossPreviousTotalInvoiceValue =(Number)grossPreviousTotalInvoiceValue.divide(new Number(1000000));
                    }
                }else{
                    grossPreviousTotalInvoiceValue = zero;
                }
         
            }
        }
        }else{
            grossCurrentTotalInvoiceValue = zero;
            grossPreviousTotalInvoiceValue = zero;
            
        }
     return grossCurrentTotalInvoiceValue;
    }
    
    public Number getGrossPreviousTotalInvoiceValue(){
        return grossPreviousTotalInvoiceValue;
    }
    
    public Number getGrossCurrentTotalPurchase(){
        OperationBinding tkridVal = this.getBindings().getOperationBinding("getTkrIdValue");
        tkridVal.getParamsMap().put("tkrPos", 3);
        tkridVal.execute();
        String tkrId = null;
        if(tkridVal.getResult()!=null){
            tkrId = tkridVal.getResult().toString();
        }
        if(tkrId!=null){
        OperationBinding tkrQuery = this.getBindings().getOperationBinding("executeTkrQuery");
        tkrQuery.getParamsMap().put("tkrId", tkrId);
        tkrQuery.execute();
        if(tkrQuery.getResult()!=null){
            ArrayList<Number> queryVal = new ArrayList<Number>();
            queryVal  =(ArrayList<Number>)tkrQuery.getResult();
            if(queryVal.size()>0){
            grossCurrentTotalPurchase = queryVal.get(0);
            if(grossCurrentTotalPurchase!=null){
             if(grossCurrentTotalPurchase.compareTo(thousand) < 0){
                    this.setThirdTkrCurrAmtNotation(" ");
               }else if(grossCurrentTotalPurchase.compareTo(thousand) > 0 && grossCurrentTotalPurchase.compareTo(million) <0 ){
                    this.setThirdTkrCurrAmtNotation("K");
                        grossCurrentTotalPurchase =(Number)grossCurrentTotalPurchase.divide(new Number(1000));
                }else if(grossCurrentTotalPurchase.compareTo(million) > 0){
                    this.setThirdTkrCurrAmtNotation("M");
                        grossCurrentTotalPurchase =(Number)grossCurrentTotalPurchase.divide(new Number(1000000));
                }
            }else{
                grossCurrentTotalPurchase = zero;
            }
           grossPreviousTotalPurchase = queryVal.get(1);
                if(grossPreviousTotalPurchase!=null){
                 if(grossPreviousTotalPurchase.compareTo(thousand) < 0){
                       this.setThirdTkrPrvsAmtNotation(" ");
                   }else if(grossPreviousTotalPurchase.compareTo(thousand) > 0 && grossPreviousTotalPurchase.compareTo(million) <0 ){
                        this.setThirdTkrPrvsAmtNotation("K");
                            grossPreviousTotalPurchase =(Number)grossPreviousTotalPurchase.divide(new Number(1000));
                    }else if(grossPreviousTotalPurchase.compareTo(million) > 0){
                        this.setThirdTkrPrvsAmtNotation("M");
                            grossPreviousTotalPurchase =(Number)grossPreviousTotalPurchase.divide(new Number(1000000));
                    }
                }else{
                    grossPreviousTotalPurchase = zero;
                }
            }
        }
        }else{
            grossCurrentTotalPurchase = zero;
            grossPreviousTotalPurchase = zero;
        }
     return grossCurrentTotalPurchase;
    }
    
    public Number getGrossPreviousTotalPurchase(){
        return grossPreviousTotalPurchase;
    }

    public void setTkrSettingsPopup(RichPopup tkrSettingsPopup) {
        this.tkrSettingsPopup = tkrSettingsPopup;
    }

    public RichPopup getTkrSettingsPopup() {
        return tkrSettingsPopup;
    }
    /**
     *  ActionEvent to show popup
     */
    private void showPopup(RichPopup pop, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null) {
                String popupId = pop.getClientId(context);
                if (popupId != null) {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible) {
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                    } else {
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void tkrSettingPopupAction(ActionEvent actionEvent) {
        // To get the total list of tickers
        OperationBinding binding = this.getBindings().getOperationBinding("getTickerList");
        if(binding != null){
            binding.execute();
            if(binding.getResult() != null){
            
               tickerList = (ArrayList<TickerRowDS>)binding.getResult();

            }    
        }
        // To get the total list of selected list of tickers
      OperationBinding bind = this.getBindings().getOperationBinding("getSelectedTickerList");
        if(bind != null){
            bind.execute();
            if(bind.getResult() != null){
                selectedList.clear();
                selectedList = (ArrayList<TickerRowDS>)bind.getResult();
            }    
        }
        
        // To set ticker description in select tickeres.
 /*       for(TickerRowDS t : tickerList){
            for(TickerRowDS r : selectedList){
                if(r.getTickerId().equals(t.getTickerId())){
                    r.setTickerDesc(t.getTickerDesc());
                    break;
                }
            } 
        }*/
         // To remove the selecetd tickers from the total list of tickers
        for(TickerRowDS t : selectedList){
            for(TickerRowDS r : tickerList){
                if(r.getTickerId().equals(t.getTickerId())){
                    tickerList.remove(r);
                    break;
                } 
            } 
        } 
      
        for(TickerRowDS r : selectedList){
        } 
       showPopup(tkrSettingsPopup, true);
    }
    
    
    /**
     * Move the selected ticker up
     * @param actionEvent
     */
    public void moveSelectedUpAction(ActionEvent actionEvent) {
        Integer sequenceNo = 0;
        RichCommandImageLink ob = (RichCommandImageLink)actionEvent.getSource();
        ob.getAttributes().get("sequenceNo");
        sequenceNo = (Integer)ob.getAttributes().get("sequenceNo");
        
        
        if(sequenceNo != 1){
            TickerRowDS selup = this.selectedList.get(sequenceNo - 2);
            TickerRowDS selcur = this.selectedList.get(sequenceNo - 1);
            selup.setSequenceNo(sequenceNo);
            selcur.setSequenceNo(sequenceNo - 1);
            this.selectedList.set(sequenceNo - 2, selcur); 
            this.selectedList.set(sequenceNo - 1, selup);
        }
        
    }
    /**
     * Move the selected ticker down
     * @param actionEvent
     */
    public void moveSelectedDownAction(ActionEvent actionEvent) {
        Integer sequenceNo = 0;
        RichCommandImageLink ob = (RichCommandImageLink)actionEvent.getSource();
        ob.getAttributes().get("sequenceNo");
        sequenceNo = (Integer)ob.getAttributes().get("sequenceNo");
        
        
        if(sequenceNo != 3){
            TickerRowDS selcur = this.selectedList.get(sequenceNo - 1);
            TickerRowDS seldn = this.selectedList.get(sequenceNo);
            seldn.setSequenceNo(sequenceNo);
            selcur.setSequenceNo(sequenceNo + 1);
            this.selectedList.set(sequenceNo, selcur); 
            this.selectedList.set(sequenceNo - 1, seldn);
        }
        
    }
    
    public String getTickerFirstLableName(){
        OperationBinding tkrName = this.getBindings().getOperationBinding("getTkrLableName");
        tkrName.getParamsMap().put("tkrPos", 1);
        tkrName.execute();
        String name = "No Ticker Selected";
        if(tkrName.getResult()!=null){
            name = tkrName.getResult().toString();
        }
        return name;
    }
    public String getTickerSecondLableName(){
        OperationBinding tkrName = this.getBindings().getOperationBinding("getTkrLableName");
        tkrName.getParamsMap().put("tkrPos", 2);
        tkrName.execute();
        String name = "No Ticker Selected";
        if(tkrName.getResult()!=null){
            name = tkrName.getResult().toString();
        }
        return name;
    }
    public String getTickerThirdLableName(){
        OperationBinding tkrName = this.getBindings().getOperationBinding("getTkrLableName");
        tkrName.getParamsMap().put("tkrPos", 3);
        tkrName.execute();
        String name = "No Ticker Selected";
        if(tkrName.getResult()!=null){
            name = tkrName.getResult().toString();
        }
     
        return name;
    }

    /**
     * Method to add a selected row from ticker setup page.
     * @param actionEvent
     */
    public void addTickerAction(ActionEvent actionEvent) {
        //Integer ticker = 0;
        String ticker = null;
        RichCommandImageLink ob = (RichCommandImageLink)actionEvent.getSource();
        ob.getAttributes().get("tickerId");
      
        if (ob.getAttributes().get("tickerId") != null) {

            if(this.selectedList.size() < 3){
                Integer seq = 0;
                for(TickerRowDS r : this.selectedList){
                    if(seq < r.getSequenceNo()){
                        seq = r.getSequenceNo();
                    }
                }
                seq = seq+1;
                ticker = (String)ob.getAttributes().get("tickerId");
                for(TickerRowDS r : this.tickerList){
                    if(ticker.equalsIgnoreCase(r.getTickerId().toString())){
                        selectedList.add(new TickerRowDS(ticker,seq,r.getTickerDesc()));
                        tickerList.remove(r);
                        break;
                    }
                }
            
            }else{
               FacesMessage message =
                   new FacesMessage("Cannot select more that THREE Tickers !");
               message.setSeverity(FacesMessage.SEVERITY_ERROR);
               FacesContext.getCurrentInstance().addMessage(null, message);
            }
            
        }

    }

    /**
     * Method to delete a selected row from ticker setup page.
     * @param actionEvent
     */
    public void deleteTickerAction(ActionEvent actionEvent) {
       // Integer ticker = 0;
        String ticker =null;
        RichCommandImageLink ob = (RichCommandImageLink)actionEvent.getSource();
        ob.getAttributes().get("tickerId");
       
        if (ob.getAttributes().get("tickerId") != null) {
                Integer seq = 0;
                for(TickerRowDS r : this.tickerList){
                    if(seq < r.getSequenceNo()){
                        seq = r.getSequenceNo();
                    }
                }
                seq = seq+1;
                ticker = (String)ob.getAttributes().get("tickerId");
                for(TickerRowDS r : this.selectedList){
                    if(ticker.equalsIgnoreCase(r.getTickerId().toString())){
                
                        tickerList.add(new TickerRowDS(ticker,seq,r.getTickerDesc()));
                        selectedList.remove(r);
                        break;
                    }
                }
                seq = 1;
                for(TickerRowDS r : this.selectedList){
                        r.setSequenceNo(seq); 
                        seq = seq +1;
                }
            
        }

    }
    public void tickerSettingDialogListener(DialogEvent dialogEvent) {
        OperationBinding unsetPos = this.getBindings().getOperationBinding("unsetAllTkrPosForUsr");
         unsetPos.execute();      
        for(TickerRowDS r : getSelectedList()){
            OperationBinding updateTkr = this.getBindings().getOperationBinding("updateTickerSettingForUsr");
            updateTkr.getParamsMap().put("seqNo",r.getSequenceNo());
            updateTkr.getParamsMap().put("tickeId",r.getTickerId());
            updateTkr.execute();      
        }
        this.getBindings().getOperationBinding("Commit").execute();
        // Add event code here...  updateTickerSettingForUsr
        
        /* OperationBinding updateTkr = this.getBindings().getOperationBinding("updateTickerSettingForUsr");
        updateTkr.execute(); */
    }



    public void setTickerList(ArrayList<TickerRowDS> tickerList) {
        this.tickerList = tickerList;
    }

    public ArrayList<TickerRowDS> getTickerList() {
        return tickerList;
    }

    public void setSelectedList(ArrayList<TickerRowDS> selectedList) {
        this.selectedList = selectedList;
    }

    public ArrayList<TickerRowDS> getSelectedList() {
        return selectedList;
    }

    public void setThirdTkrCurrAmtNotation(String thirdTkrCurrAmtNotation) {
        this.thirdTkrCurrAmtNotation = thirdTkrCurrAmtNotation;
    }

    public String getThirdTkrCurrAmtNotation() {
        return thirdTkrCurrAmtNotation;
    }

    public void setThirdTkrPrvsAmtNotation(String thirdTkrPrvsAmtNotation) {
        this.thirdTkrPrvsAmtNotation = thirdTkrPrvsAmtNotation;
    }

    public String getThirdTkrPrvsAmtNotation() {
        return thirdTkrPrvsAmtNotation;
    }

    public void setFirstTkrCurrAmtNotation(String firstTkrCurrAmtNotation) {
        this.firstTkrCurrAmtNotation = firstTkrCurrAmtNotation;
    }

    public String getFirstTkrCurrAmtNotation() {
        return firstTkrCurrAmtNotation;
    }

    public void setFirstTkrPrvsAmtNotation(String firstTkrPrvsAmtNotation) {
        this.firstTkrPrvsAmtNotation = firstTkrPrvsAmtNotation;
    }

    public String getFirstTkrPrvsAmtNotation() {
        return firstTkrPrvsAmtNotation;
    }

    public void setSecondTkrCurrAmtNotation(String secondTkrCurrAmtNotation) {
        this.secondTkrCurrAmtNotation = secondTkrCurrAmtNotation;
    }

    public String getSecondTkrCurrAmtNotation() {
        return secondTkrCurrAmtNotation;
    }

    public void setSecondTkrPrvsAmtNotation(String secondTkrPrvsAmtNotation) {
        this.secondTkrPrvsAmtNotation = secondTkrPrvsAmtNotation;
    }

    public String getSecondTkrPrvsAmtNotation() {
        return secondTkrPrvsAmtNotation;
    }

    public void setSwitcherBindRfqPage(UIXSwitcher switcherBindRfqPage) {
        this.switcherBindRfqPage = switcherBindRfqPage;
    }

    public UIXSwitcher getSwitcherBindRfqPage() {
        return switcherBindRfqPage;
    }

    public void setFacetValueRfqPage(String facetValueRfqPage) {
        this.facetValueRfqPage = facetValueRfqPage;
    }

    public String getFacetValueRfqPage() {
        return facetValueRfqPage;
    }


    public void setProductNum(RichInputNumberSlider productNum) {
        this.productNum = productNum;
    }

    public RichInputNumberSlider getProductNum() {
        return productNum;
    }

    public void setProductGrpNum(RichInputNumberSlider productGrpNum) {
        this.productGrpNum = productGrpNum;
    }

    public RichInputNumberSlider getProductGrpNum() {
        return productGrpNum;
    }
}
