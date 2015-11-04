package inventoryreportapp.view.bean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichGoImageLink;
import oracle.adf.view.rich.component.rich.nav.RichGoLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;



import org.apache.myfaces.trinidad.component.UIXSwitcher;

public class InventoryRptBean {
    private UIXSwitcher switcherBinding;
    private RichSelectBooleanCheckbox gateEntryChkBinding;
    private String gateEntryMode="false";
    private String receiptRegisterMode="false";
    private String detailedReceiptRegMode="false";
    private String issueRegIssueNoWise="false";
    private RichSelectOneChoice fileTypBinding;
    private RichSelectOneChoice inventoryTypBinding;
    private RichSelectBooleanCheckbox receiptRegChkBinding;
    private RichGoLink gateEntryRptLinkBinding;
    private RichGoLink receiptRegRptLinkBinding;
    private RichSelectBooleanCheckbox detailedReceiptRegisterChkBinding;
    private RichSelectBooleanCheckbox issueRegIssueNoWiseChkBinding;
    private RichSelectBooleanCheckbox partyWiseChkBinding;
    private String partyWiseReceiptReg="false";
    private RichSelectBooleanCheckbox itemWiseRegChekBinding;
    private String ItemWiseReceiptReg="false";
    private RichSelectBooleanCheckbox mrsCheckBinding;
    private String mrsRpt="false";
    private RichSelectBooleanCheckbox rmdaCheckBinding;
    private String rmdaRpt="false";
    private RichSelectBooleanCheckbox qcProcessChekBinding;
    private String QcProcessRpt="false";
    private RichSelectBooleanCheckbox bindLotWiseVar;
    private RichSelectBooleanCheckbox bindBinWiseVar;
    private RichSelectBooleanCheckbox bindSrWiseVar;
    private RichGoLink stoclLotWiseLink;
    private RichGoLink stockBinWiseLink;
    private RichGoLink stockSrWiseLink;
    private RichGoLink stockRegisterBindVar;
    private RichSelectBooleanCheckbox bindStockRegisterVar;
    private RichSelectBooleanCheckbox bindStockTakeVar;
    private RichGoImageLink bindStockTakeLinkVar;
    private RichSelectBooleanCheckbox bindKitWorkShopVar;
    private RichGoLink bindKitWorkShopLink;
    private RichGoLink bindGatePassLinkVar;
    private RichSelectBooleanCheckbox bindGatePassVar;
    private RichSelectBooleanCheckbox bindPurchaseReturnVar;
    private RichGoLink bindPurchaseReturnLinkVar;
    private RichGoLink bindPurchaseReturnItmVar;
    private RichSelectBooleanCheckbox bindPurRtnItmVar;
    private RichSelectBooleanCheckbox bindPurchaseRequisitionVar;
    private RichGoLink bindPurchaseRequisitionLink;
    private RichSelectBooleanCheckbox bindTransOrdVar;
    private RichGoLink bindTransOrderLink;
    private RichSelectBooleanCheckbox bindCurrStockSummVar;
    private RichSelectBooleanCheckbox bindItmStockLedgerVar;
    private RichSelectBooleanCheckbox bindItmMoveDetailVar;
    private RichGoLink bindCurrStockLink;
    private RichGoLink bindItmStckLedgerLink;
    private RichGoLink bindItmMovDetailLink;
    private RichSelectBooleanCheckbox bindMrnVar;
    private RichGoLink bindMrnLink;
    private RichSelectBooleanCheckbox stockTakeDetailChkBinding;
    private RichGoLink stocktakedetailLinkBinding;
    private RichSelectBooleanCheckbox stockTakingSummaryBinding;
    private RichGoLink stockTakeSummarylinkBinding;
    private RichSelectBooleanCheckbox currStockSummaryCheckBinding;
    private RichGoLink currentStockSummaryLinkBinding;
    private RichSelectBooleanCheckbox prIndentCheckBinding;
    private RichGoLink indentRptLinkBinding;
    private RichSelectBooleanCheckbox consumptionRegChkBinding;
    private RichGoLink consumtionRegLinkBinding;
    private RichSelectBooleanCheckbox purchaseInvoiceCheckBinding;
    private RichGoLink purchaseInvoiceLinkBinding;
    private RichSelectBooleanCheckbox stockTrfInvcChkBinding;
    private RichGoLink stockTransferInvcLinkBinding;
    private RichSelectBooleanCheckbox gatepassBinding;
    private RichSelectBooleanCheckbox gatePassSummaryBinding;
    private RichGoLink gatepasslnkBinding;
    private RichGoLink gatePassSummarylnkBinding;


    public InventoryRptBean() {
    }
    
    public void selectInventoryTypeVCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(switcherBinding);
    }

    public void setSwitcherBinding(UIXSwitcher switcherBinding) {
        this.switcherBinding = switcherBinding;
    }

    public UIXSwitcher getSwitcherBinding() {
        return switcherBinding;
    }
    public BindingContainer getBindings(){
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    // for search in Material Inward
    public void SearchForMaterialInwardAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("searchMaterialInward").execute();
    }
    
    // for Reset in Material Inwards
    public void resetForMaterialInwardAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("resetMaterialInward").execute();
    }

    public void setGateEntryChkBinding(RichSelectBooleanCheckbox gateEntryChkBinding) {
        this.gateEntryChkBinding = gateEntryChkBinding;
    }

    public RichSelectBooleanCheckbox getGateEntryChkBinding() {
        return gateEntryChkBinding;
    }
    
    
    // generate Report Btn Material Inwards-------------------------
    public void genRptBtnForMaterialInwardsAL(ActionEvent actionEvent) {
        if((Boolean)gateEntryChkBinding.getValue()==true){
            this.gateEntryMode="true";
        }
        else{
            this.gateEntryMode="false";
        }
        if((Boolean)receiptRegChkBinding.getValue()==true){
                this.receiptRegisterMode="true";
            }
        else{
            this.receiptRegisterMode="false";
        }
        if((Boolean)detailedReceiptRegisterChkBinding.getValue()==true){
            this.detailedReceiptRegMode="true";
        }
        else{
            this.detailedReceiptRegMode="false";
        }
        if((Boolean)partyWiseChkBinding.getValue().equals(true)){
            this.partyWiseReceiptReg="true";
        }
        else{
            this.partyWiseReceiptReg="false";
        }
        if((Boolean)itemWiseRegChekBinding.getValue().equals(true)){
            this.ItemWiseReceiptReg="true";
        }
        else{
            this.ItemWiseReceiptReg="false";
        }
        }
    

    public void setGateEntryMode(String gateEntryMode) {
        this.gateEntryMode = gateEntryMode;
    }

    public String getGateEntryMode() {
        return gateEntryMode;
    }

    public void setFileTypBinding(RichSelectOneChoice fileTypBinding) {
        this.fileTypBinding = fileTypBinding;
    }

    public RichSelectOneChoice getFileTypBinding() {
        return fileTypBinding;
    }

    public void setInventoryTypBinding(RichSelectOneChoice inventoryTypBinding) {
        this.inventoryTypBinding = inventoryTypBinding;
    }

    public RichSelectOneChoice getInventoryTypBinding() {
        return inventoryTypBinding;
    }

    public void setReceiptRegChkBinding(RichSelectBooleanCheckbox receiptRegChkBinding) {
        this.receiptRegChkBinding = receiptRegChkBinding;
    }

    public RichSelectBooleanCheckbox getReceiptRegChkBinding() {
        return receiptRegChkBinding;
    }

    public void setGateEntryRptLinkBinding(RichGoLink gateEntryRptLinkBinding) {
        this.gateEntryRptLinkBinding = gateEntryRptLinkBinding;
    }

    public RichGoLink getGateEntryRptLinkBinding() {
        return gateEntryRptLinkBinding;
    }

    public void setReceiptRegRptLinkBinding(RichGoLink receiptRegRptLinkBinding) {
        this.receiptRegRptLinkBinding = receiptRegRptLinkBinding;
    }

    public RichGoLink getReceiptRegRptLinkBinding() {
        return receiptRegRptLinkBinding;
    }

    public void setReceiptRegisterMode(String receiptRegisterMode) {
        this.receiptRegisterMode = receiptRegisterMode;
    }

    public String getReceiptRegisterMode() {
        return receiptRegisterMode;
    }

    public void setDetailedReceiptRegisterChkBinding(RichSelectBooleanCheckbox detailedReceiptRegisterChkBinding) {
        this.detailedReceiptRegisterChkBinding = detailedReceiptRegisterChkBinding;
    }

    public RichSelectBooleanCheckbox getDetailedReceiptRegisterChkBinding() {
        return detailedReceiptRegisterChkBinding;
    }

    public void setDetailedReceiptRegMode(String detailedReceiptRegMode) {
        this.detailedReceiptRegMode = detailedReceiptRegMode;
    }

    public String getDetailedReceiptRegMode() {
        return detailedReceiptRegMode;
    }

    public void searchForMaterialOutwardAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("searchMaterailOutwards").execute();
    }

    public void resetMaterialOutwardAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("resetMaterialOutwards").execute();
    }

    public void genRptBtnForMaterialOutwardsAL(ActionEvent actionEvent) {
        if((Boolean)issueRegIssueNoWiseChkBinding.getValue().equals(true)){
            this.issueRegIssueNoWise="true";
        }
        else{
            this.issueRegIssueNoWise="false";
        }
        if(consumptionRegChkBinding.getValue().equals(true)){
            consumtionRegLinkBinding.setVisible(true);
        }
        else{
            consumtionRegLinkBinding.setVisible(false);
        }
    }

    public void setIssueRegIssueNoWise(String issueRegIssueNoWise) {
        this.issueRegIssueNoWise = issueRegIssueNoWise;
    }

    public String getIssueRegIssueNoWise() {
        return issueRegIssueNoWise;
    }

    public void setIssueRegIssueNoWiseChkBinding(RichSelectBooleanCheckbox issueRegIssueNoWiseChkBinding) {
        this.issueRegIssueNoWiseChkBinding = issueRegIssueNoWiseChkBinding;
    }

    public RichSelectBooleanCheckbox getIssueRegIssueNoWiseChkBinding() {
        return issueRegIssueNoWiseChkBinding;
    }

    public void setPartyWiseChkBinding(RichSelectBooleanCheckbox partyWiseChkBinding) {
        this.partyWiseChkBinding = partyWiseChkBinding;
    }

    public RichSelectBooleanCheckbox getPartyWiseChkBinding() {
        return partyWiseChkBinding;
    }

    public void setPartyWiseReceiptReg(String partyWiseReceiptReg) {
        this.partyWiseReceiptReg = partyWiseReceiptReg;
    }

    public String getPartyWiseReceiptReg() {
        return partyWiseReceiptReg;
    }

    public void setItemWiseRegChekBinding(RichSelectBooleanCheckbox itemWiseRegChekBinding) {
        this.itemWiseRegChekBinding = itemWiseRegChekBinding;
    }

    public RichSelectBooleanCheckbox getItemWiseRegChekBinding() {
        return itemWiseRegChekBinding;
    }

    public void setItemWiseReceiptReg(String ItemWiseReceiptReg) {
        this.ItemWiseReceiptReg = ItemWiseReceiptReg;
    }

    public String getItemWiseReceiptReg() {
        return ItemWiseReceiptReg;
    }

    public void resetMrsAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("resetMRS").execute();
    }

    public void genRptBtnMRSAL(ActionEvent actionEvent) {
        if(mrsCheckBinding.getValue().equals(true)){
            mrsRpt="true";
        }
        else{
            mrsRpt="false";
        }
        if(prIndentCheckBinding.getValue()!=null){
            Boolean chek = (Boolean)prIndentCheckBinding.getValue();
            if(chek){
                indentRptLinkBinding.setVisible(true);
            }
            else{
                indentRptLinkBinding.setVisible(false);
            }
        }
    }

    public void setMrsCheckBinding(RichSelectBooleanCheckbox mrsCheckBinding) {
        this.mrsCheckBinding = mrsCheckBinding;
    }

    public RichSelectBooleanCheckbox getMrsCheckBinding() {
        return mrsCheckBinding;
    }

    public void setMrsRpt(String mrsRpt) {
        this.mrsRpt = mrsRpt;
    }

    public String getMrsRpt() {
        return mrsRpt;
    }

    public void resetRmdaParamAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("resetRmda").execute();
    }

    public void setRmdaCheckBinding(RichSelectBooleanCheckbox rmdaCheckBinding) {
        this.rmdaCheckBinding = rmdaCheckBinding;
    }

    public RichSelectBooleanCheckbox getRmdaCheckBinding() {
        return rmdaCheckBinding;
    }

    public void genRptForRmdaAL(ActionEvent actionEvent) {
        if(rmdaCheckBinding.getValue().equals(true)){
            this.rmdaRpt="true";
        }
        else{
            this.rmdaRpt="false";
        }
    }

    public void setRmdaRpt(String rmdaRpt) {
        this.rmdaRpt = rmdaRpt;
    }

    public String getRmdaRpt() {
        return rmdaRpt;
    }

    public void genRptForQcProcessAL(ActionEvent actionEvent) {
        if(qcProcessChekBinding.getValue().equals(true)){
            this.QcProcessRpt="true";
        }
        else{
            this.QcProcessRpt="false";
        }
    }

    public void setQcProcessChekBinding(RichSelectBooleanCheckbox qcProcessChekBinding) {
        this.qcProcessChekBinding = qcProcessChekBinding;
    }

    public RichSelectBooleanCheckbox getQcProcessChekBinding() {
        return qcProcessChekBinding;
    }

    public void setQcProcessRpt(String QcProcessRpt) {
        this.QcProcessRpt = QcProcessRpt;
    }

    public String getQcProcessRpt() {
        return QcProcessRpt;
    }

    public void resetQcProcessAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("resetQcProcess").execute();
    }

    public void setBindLotWiseVar(RichSelectBooleanCheckbox bindLotWiseVar) {
        this.bindLotWiseVar = bindLotWiseVar;
    }

    public RichSelectBooleanCheckbox getBindLotWiseVar() {
        return bindLotWiseVar;
    }

    public void setBindBinWiseVar(RichSelectBooleanCheckbox bindBinWiseVar) {
        this.bindBinWiseVar = bindBinWiseVar;
    }

    public RichSelectBooleanCheckbox getBindBinWiseVar() {
        return bindBinWiseVar;
    }

    public void setBindSrWiseVar(RichSelectBooleanCheckbox bindSrWiseVar) {
        this.bindSrWiseVar = bindSrWiseVar;
    }

    public RichSelectBooleanCheckbox getBindSrWiseVar() {
        return bindSrWiseVar;
    }

    public String generateReportForStockAction() {
        // Add event code here...
        Object lotVal = bindLotWiseVar.getValue();
         if(lotVal != null)
        {
        Boolean islot=(Boolean)lotVal;
        if(islot){stoclLotWiseLink.setVisible(true);}
        else{stoclLotWiseLink.setVisible(false);};
        }
        
        Object binVal = bindBinWiseVar.getValue();
        if(binVal != null)
        {
        Boolean isbin=(Boolean)binVal;
        if(isbin){stockBinWiseLink.setVisible(true);}
        else{stockBinWiseLink.setVisible(false);};
        }
        
        Object srVal = bindSrWiseVar.getValue();
        if(srVal != null)
        {
            Boolean issr=(Boolean)srVal;
        if(issr){stockSrWiseLink.setVisible(true);}
        else{stockSrWiseLink.setVisible(false);};
        }
        
        Object stkReg = bindStockRegisterVar.getValue();
        if(stkReg != null)
        {
            Boolean isStkReg=(Boolean)stkReg;
        if(isStkReg){stockRegisterBindVar.setVisible(true);}
        else{stockRegisterBindVar.setVisible(false);};
        }
        
        
        Object stkcurr = bindCurrStockSummVar.getValue();
        if(stkcurr != null)
        {
            Boolean isStkReg=(Boolean)stkcurr;
        if(isStkReg){bindCurrStockLink.setVisible(true);}
        else{bindCurrStockLink.setVisible(false);};
        }
        
        
        Object stktm = bindItmStockLedgerVar.getValue();
        if(stktm != null)
        {
            Boolean isStkReg=(Boolean)stktm;
        if(isStkReg){bindItmStckLedgerLink.setVisible(true);}
        else{bindItmStckLedgerLink.setVisible(false);};
        }
        
        
        Object stkitmdtl = bindItmMoveDetailVar.getValue();
        if(stkitmdtl != null)
        {
            Boolean isStkReg=(Boolean)stkitmdtl;
        if(isStkReg){bindItmMovDetailLink.setVisible(true);}
        else{bindItmMovDetailLink.setVisible(false);};
        }
        
        if(currStockSummaryCheckBinding.getValue()!=null){
            Boolean chk = (Boolean)currStockSummaryCheckBinding.getValue();
            if(chk){
                currentStockSummaryLinkBinding.setVisible(true);
            }
            else{
                currentStockSummaryLinkBinding.setVisible(false);
            }
        }
        
        
        return null;
    }

    public void setStoclLotWiseLink(RichGoLink stoclLotWiseLink) {
        this.stoclLotWiseLink = stoclLotWiseLink;
    }

    public RichGoLink getStoclLotWiseLink() {
        return stoclLotWiseLink;
    }

    public void setStockBinWiseLink(RichGoLink stockBinWiseLink) {
        this.stockBinWiseLink = stockBinWiseLink;
    }

    public RichGoLink getStockBinWiseLink() {
        return stockBinWiseLink;
    }

    public void setStockSrWiseLink(RichGoLink stockSrWiseLink) {
        this.stockSrWiseLink = stockSrWiseLink;
    }

    public RichGoLink getStockSrWiseLink() {
        return stockSrWiseLink;
    }

    public void setStockRegisterBindVar(RichGoLink stockRegisterBindVar) {
        this.stockRegisterBindVar = stockRegisterBindVar;
    }

    public RichGoLink getStockRegisterBindVar() {
        return stockRegisterBindVar;
    }

    public void setBindStockRegisterVar(RichSelectBooleanCheckbox bindStockRegisterVar) {
        this.bindStockRegisterVar = bindStockRegisterVar;
    }

    public RichSelectBooleanCheckbox getBindStockRegisterVar() {
        return bindStockRegisterVar;
    }

    public String resetStockAction() {
        getBindings().getOperationBinding("resetStock").execute();
        stockRegisterBindVar.setVisible(false);
        stockSrWiseLink.setVisible(false);
        stockBinWiseLink.setVisible(false);
        stoclLotWiseLink.setVisible(false);
        bindLotWiseVar.setValue(false);
        bindBinWiseVar.setValue(false);
        bindSrWiseVar.setValue(false);
        bindStockRegisterVar.setValue(false);
        bindCurrStockSummVar.setValue(false);
        bindCurrStockLink.setVisible(false);
        bindItmStockLedgerVar.setValue(false);
        bindItmStckLedgerLink.setVisible(false);
        bindItmMoveDetailVar.setValue(false);
        bindItmMovDetailLink.setVisible(false);
        return null;
    }

    public void setBindStockTakeVar(RichSelectBooleanCheckbox bindStockTakeVar) {
        this.bindStockTakeVar = bindStockTakeVar;
    }

    public RichSelectBooleanCheckbox getBindStockTakeVar() {
        return bindStockTakeVar;
    }

    public void setBindStockTakeLinkVar(RichGoImageLink bindStockTakeLinkVar) {
        this.bindStockTakeLinkVar = bindStockTakeLinkVar;
    }

    public RichGoImageLink getBindStockTakeLinkVar() {
        return bindStockTakeLinkVar;
    }

    public String generateReportForStockTakeAction() {
        // Add event code here...
        Object stkVal = bindStockTakeVar.getValue();
         if(stkVal != null)
        {
        Boolean isStkTk=(Boolean)stkVal;
        if(isStkTk){bindStockTakeLinkVar.setVisible(true);}
        else{bindStockTakeLinkVar.setVisible(false);};
        }
         if(stockTakeDetailChkBinding.getValue()!=null){
            Boolean dtlchk = (Boolean)stockTakeDetailChkBinding.getValue();
            if(dtlchk){
                stocktakedetailLinkBinding.setVisible(true);
            }
            else{
                stocktakedetailLinkBinding.setVisible(false);
            }
        }
         if(stockTakingSummaryBinding.getValue()!=null){
            Boolean summry = (Boolean)stockTakingSummaryBinding.getValue();
            if(summry){
                stockTakeSummarylinkBinding.setVisible(true);
            }
            else{
                stockTakeSummarylinkBinding.setVisible(false);
            }
        }
        return null;
    }

    public void setBindKitWorkShopVar(RichSelectBooleanCheckbox bindKitWorkShopVar) {
        this.bindKitWorkShopVar = bindKitWorkShopVar;
    }

    public RichSelectBooleanCheckbox getBindKitWorkShopVar() {
        return bindKitWorkShopVar;
    }

    public void setBindKitWorkShopLink(RichGoLink bindKitWorkShopLink) {
        this.bindKitWorkShopLink = bindKitWorkShopLink;
    }

    public RichGoLink getBindKitWorkShopLink() {
        return bindKitWorkShopLink;
    }

    public String generateKitWorkShopAction() {
        // Add event code here...
        // Add event code here...
        Object kitVal = bindKitWorkShopVar.getValue();
         if(kitVal != null)
        {
        Boolean isKit=(Boolean)kitVal;
        if(isKit){bindKitWorkShopLink.setVisible(true);}
        else{bindKitWorkShopLink.setVisible(false);};
        }
        return null;
    }

    public String resetStockTakeAction() {
        // Add event code here...
        getBindings().getOperationBinding("resetStockTake").execute();
        bindStockTakeLinkVar.setVisible(false);
        bindStockTakeVar.setValue(false);
        return null;
    }

    public String resetKitWorkshopAction() {
        // Add event code here...
        getBindings().getOperationBinding("resetKitWorkShop").execute();
        bindKitWorkShopLink.setVisible(false);
        bindKitWorkShopVar.setValue(false);
        return null;
    }

    public void setBindGatePassLinkVar(RichGoLink bindGatePassLinkVar) {
        this.bindGatePassLinkVar = bindGatePassLinkVar;
    }

    public RichGoLink getBindGatePassLinkVar() {
        return bindGatePassLinkVar;
    }

    public void setBindGatePassVar(RichSelectBooleanCheckbox bindGatePassVar) {
        this.bindGatePassVar = bindGatePassVar;
    }

    public RichSelectBooleanCheckbox getBindGatePassVar() {
        return bindGatePassVar;
    }

    public String generateGatePassAction() {
        // Add event code here...
        Object objVal = bindGatePassVar.getValue();
         if(objVal != null)
        {
        Boolean isKit=(Boolean)objVal;
        if(isKit){bindGatePassLinkVar.setVisible(true);}
        else{bindGatePassLinkVar.setVisible(false);};
        }
         if(gatepassBinding.getValue().equals(true)) {
             this.gatepasslnkBinding.setVisible(true);
         }
         else {
             this.gatepasslnkBinding.setVisible(false);
         }
         if(gatePassSummaryBinding.getValue().equals(true)) {
             this.gatePassSummarylnkBinding.setVisible(true);
         }
         else {
             this.gatePassSummarylnkBinding.setVisible(false);
         }
        return null;
    }

    public void setBindPurchaseReturnVar(RichSelectBooleanCheckbox bindPurchaseReturnVar) {
        this.bindPurchaseReturnVar = bindPurchaseReturnVar;
    }

    public RichSelectBooleanCheckbox getBindPurchaseReturnVar() {
        return bindPurchaseReturnVar;
    }

    public void setBindPurchaseReturnLinkVar(RichGoLink bindPurchaseReturnLinkVar) {
        this.bindPurchaseReturnLinkVar = bindPurchaseReturnLinkVar;
    }

    public RichGoLink getBindPurchaseReturnLinkVar() {
        return bindPurchaseReturnLinkVar;
    }

    public String generatePurchaseReturnAction() {
        // Add event code here...
        Object objVal = bindPurchaseReturnVar.getValue();
         if(objVal != null)
        {
        Boolean isPr=(Boolean)objVal;
        if(isPr){bindPurchaseReturnLinkVar.setVisible(true);}
        else{bindPurchaseReturnLinkVar.setVisible(false);};
        }
         
        Object objItmVal = bindPurRtnItmVar.getValue();
         if(objItmVal != null)
        {
        Boolean isPr=(Boolean)objItmVal;
        if(isPr){bindPurchaseReturnItmVar.setVisible(true);}
        else{bindPurchaseReturnItmVar.setVisible(false);};
        } 
        return null;
    }

    public String resetGatePassAction() {
        // Add event code here...
        getBindings().getOperationBinding("resetGatePass").execute();
        bindGatePassLinkVar.setVisible(false);
        bindGatePassVar.setValue(false);
        gatepassBinding.setValue(false);
        gatepasslnkBinding.setVisible(true);
        gatePassSummaryBinding.setValue(false);
        gatePassSummarylnkBinding.setVisible(false);
        return null;
    }

    public String resetPurchaseReturnAction() {
        // Add event code here...
        
        getBindings().getOperationBinding("resetPurchaseReturn").execute();
        bindPurchaseReturnLinkVar.setVisible(false);
        bindPurchaseReturnVar.setValue(false);
        bindPurRtnItmVar.setValue(false);
        bindPurchaseReturnItmVar.setVisible(false);
        return null;
    }

    public void setBindPurchaseReturnItmVar(RichGoLink bindPurchaseReturnItmVar) {
        this.bindPurchaseReturnItmVar = bindPurchaseReturnItmVar;
    }

    public RichGoLink getBindPurchaseReturnItmVar() {
        return bindPurchaseReturnItmVar;
    }

    public void setBindPurRtnItmVar(RichSelectBooleanCheckbox bindPurRtnItmVar) {
        this.bindPurRtnItmVar = bindPurRtnItmVar;
    }

    public RichSelectBooleanCheckbox getBindPurRtnItmVar() {
        return bindPurRtnItmVar;
    }

    public String generateTransferOrderAction() {
        // Add event code here...
        Object objItmVal = bindTransOrdVar.getValue();
         if(objItmVal != null)
        {
        Boolean isPr=(Boolean)objItmVal;
        if(isPr){bindTransOrderLink.setVisible(true);}
        else{bindTransOrderLink.setVisible(false);};
        } 
        return null;
    }

    public String generatePurchaseInvoiceAction() {
        if(purchaseInvoiceCheckBinding.getValue().equals(true)){
            purchaseInvoiceLinkBinding.setVisible(true);
        }
        else{
            purchaseInvoiceLinkBinding.setVisible(false);
        }
        
        if(stockTrfInvcChkBinding.getValue().equals(true)){
            stockTransferInvcLinkBinding.setVisible(true);
        }
        else{
            stockTransferInvcLinkBinding.setVisible(false);
        }
        return null;
    }

    public String generatePurchaseRequisitionAction() {
        // Add event code here...
        Object objVal = bindPurchaseRequisitionVar.getValue();
         if(objVal != null)
        {
        Boolean isPr=(Boolean)objVal;
        if(isPr){bindPurchaseRequisitionLink.setVisible(true);}
        else{bindPurchaseRequisitionLink.setVisible(false);};
        }
     
        return null;
    }

    public String generateMaterialRtnNoteAction() {
        // Add event code here...
        Object objVal = bindMrnVar.getValue();
         if(objVal != null)
        {
        Boolean isPr=(Boolean)objVal;
        if(isPr){bindMrnLink.setVisible(true);}
        else{bindMrnLink.setVisible(false);};
        }
        return null;
    }

    public String resetPurchaseRequisitionAction() {
        // Add event code here...
        getBindings().getOperationBinding("resetPurchaseRequisition").execute();
        bindPurchaseRequisitionLink.setVisible(false);
        bindPurchaseRequisitionVar.setValue(false);
        return null;
    }

    public void setBindPurchaseRequisitionVar(RichSelectBooleanCheckbox bindPurchaseRequisitionVar) {
        this.bindPurchaseRequisitionVar = bindPurchaseRequisitionVar;
    }

    public RichSelectBooleanCheckbox getBindPurchaseRequisitionVar() {
        return bindPurchaseRequisitionVar;
    }

    public void setBindPurchaseRequisitionLink(RichGoLink bindPurchaseRequisitionLink) {
        this.bindPurchaseRequisitionLink = bindPurchaseRequisitionLink;
    }

    public RichGoLink getBindPurchaseRequisitionLink() {
        return bindPurchaseRequisitionLink;
    }

    public String resetTransferOrderAction() {
        // Add event code here...
         getBindings().getOperationBinding("resetTransferOrder").execute();
        bindTransOrdVar.setValue(false);
        bindTransOrderLink.setVisible(false);
        return null;
    }

    public void setBindTransOrdVar(RichSelectBooleanCheckbox bindTransOrdVar) {
        this.bindTransOrdVar = bindTransOrdVar;
    }

    public RichSelectBooleanCheckbox getBindTransOrdVar() {
        return bindTransOrdVar;
    }

    public void setBindTransOrderLink(RichGoLink bindTransOrderLink) {
        this.bindTransOrderLink = bindTransOrderLink;
    }

    public RichGoLink getBindTransOrderLink() {
        return bindTransOrderLink;
    }

    public void setBindCurrStockSummVar(RichSelectBooleanCheckbox bindCurrStockSummVar) {
        this.bindCurrStockSummVar = bindCurrStockSummVar;
    }

    public RichSelectBooleanCheckbox getBindCurrStockSummVar() {
        return bindCurrStockSummVar;
    }

    public void setBindItmStockLedgerVar(RichSelectBooleanCheckbox bindItmStockLedgerVar) {
        this.bindItmStockLedgerVar = bindItmStockLedgerVar;
    }

    public RichSelectBooleanCheckbox getBindItmStockLedgerVar() {
        return bindItmStockLedgerVar;
    }

    public void setBindItmMoveDetailVar(RichSelectBooleanCheckbox bindItmMoveDetailVar) {
        this.bindItmMoveDetailVar = bindItmMoveDetailVar;
    }

    public RichSelectBooleanCheckbox getBindItmMoveDetailVar() {
        return bindItmMoveDetailVar;
    }

    public void setBindCurrStockLink(RichGoLink bindCurrStockLink) {
        this.bindCurrStockLink = bindCurrStockLink;
    }

    public RichGoLink getBindCurrStockLink() {
        return bindCurrStockLink;
    }

    public void setBindItmStckLedgerLink(RichGoLink bindItmStckLedgerLink) {
        this.bindItmStckLedgerLink = bindItmStckLedgerLink;
    }

    public RichGoLink getBindItmStckLedgerLink() {
        return bindItmStckLedgerLink;
    }

    public void setBindItmMovDetailLink(RichGoLink bindItmMovDetailLink) {
        this.bindItmMovDetailLink = bindItmMovDetailLink;
    }

    public RichGoLink getBindItmMovDetailLink() {
        return bindItmMovDetailLink;
    }

    public void setBindMrnVar(RichSelectBooleanCheckbox bindMrnVar) {
        this.bindMrnVar = bindMrnVar;
    }

    public RichSelectBooleanCheckbox getBindMrnVar() {
        return bindMrnVar;
    }

    public void setBindMrnLink(RichGoLink bindMrnLink) {
        this.bindMrnLink = bindMrnLink;
    }

    public RichGoLink getBindMrnLink() {
        return bindMrnLink;
    }

    public String resetMrnAction() {
        // Add event code here...
         getBindings().getOperationBinding("resetMrn").execute();
        bindMrnVar.setValue(false);
        bindMrnLink.setVisible(false);
        return null;
    }

    public void setStockTakeDetailChkBinding(RichSelectBooleanCheckbox stockTakeDetailChkBinding) {
        this.stockTakeDetailChkBinding = stockTakeDetailChkBinding;
    }

    public RichSelectBooleanCheckbox getStockTakeDetailChkBinding() {
        return stockTakeDetailChkBinding;
    }

    public void setStocktakedetailLinkBinding(RichGoLink stocktakedetailLinkBinding) {
        this.stocktakedetailLinkBinding = stocktakedetailLinkBinding;
    }

    public RichGoLink getStocktakedetailLinkBinding() {
        return stocktakedetailLinkBinding;
    }

    public void setStockTakingSummaryBinding(RichSelectBooleanCheckbox stockTakingSummaryBinding) {
        this.stockTakingSummaryBinding = stockTakingSummaryBinding;
    }

    public RichSelectBooleanCheckbox getStockTakingSummaryBinding() {
        return stockTakingSummaryBinding;
    }

    public void setStockTakeSummarylinkBinding(RichGoLink stockTakeSummarylinkBinding) {
        this.stockTakeSummarylinkBinding = stockTakeSummarylinkBinding;
    }

    public RichGoLink getStockTakeSummarylinkBinding() {
        return stockTakeSummarylinkBinding;
    }

    public void setCurrStockSummaryCheckBinding(RichSelectBooleanCheckbox currStockSummaryCheckBinding) {
        this.currStockSummaryCheckBinding = currStockSummaryCheckBinding;
    }

    public RichSelectBooleanCheckbox getCurrStockSummaryCheckBinding() {
        return currStockSummaryCheckBinding;
    }

    public void setCurrentStockSummaryLinkBinding(RichGoLink currentStockSummaryLinkBinding) {
        this.currentStockSummaryLinkBinding = currentStockSummaryLinkBinding;
    }

    public RichGoLink getCurrentStockSummaryLinkBinding() {
        return currentStockSummaryLinkBinding;
    }

    public void setPrIndentCheckBinding(RichSelectBooleanCheckbox prIndentCheckBinding) {
        this.prIndentCheckBinding = prIndentCheckBinding;
    }

    public RichSelectBooleanCheckbox getPrIndentCheckBinding() {
        return prIndentCheckBinding;
    }

    public void setIndentRptLinkBinding(RichGoLink indentRptLinkBinding) {
        this.indentRptLinkBinding = indentRptLinkBinding;
    }

    public RichGoLink getIndentRptLinkBinding() {
        return indentRptLinkBinding;
    }

    public void setConsumptionRegChkBinding(RichSelectBooleanCheckbox consumptionRegChkBinding) {
        this.consumptionRegChkBinding = consumptionRegChkBinding;
    }

    public RichSelectBooleanCheckbox getConsumptionRegChkBinding() {
        return consumptionRegChkBinding;
    }

    public void setConsumtionRegLinkBinding(RichGoLink consumtionRegLinkBinding) {
        this.consumtionRegLinkBinding = consumtionRegLinkBinding;
    }

    public RichGoLink getConsumtionRegLinkBinding() {
        return consumtionRegLinkBinding;
    }

    public void resetPurchaseInvoiceAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("resetPI").execute();
    }

    public void setPurchaseInvoiceCheckBinding(RichSelectBooleanCheckbox purchaseInvoiceCheckBinding) {
        this.purchaseInvoiceCheckBinding = purchaseInvoiceCheckBinding;
    }

    public RichSelectBooleanCheckbox getPurchaseInvoiceCheckBinding() {
        return purchaseInvoiceCheckBinding;
    }

    public void setPurchaseInvoiceLinkBinding(RichGoLink purchaseInvoiceLinkBinding) {
        this.purchaseInvoiceLinkBinding = purchaseInvoiceLinkBinding;
    }

    public RichGoLink getPurchaseInvoiceLinkBinding() {
        return purchaseInvoiceLinkBinding;
    }

    public void setStockTrfInvcChkBinding(RichSelectBooleanCheckbox stockTrfInvcChkBinding) {
        this.stockTrfInvcChkBinding = stockTrfInvcChkBinding;
    }

    public RichSelectBooleanCheckbox getStockTrfInvcChkBinding() {
        return stockTrfInvcChkBinding;
    }

    public void setStockTransferInvcLinkBinding(RichGoLink stockTransferInvcLinkBinding) {
        this.stockTransferInvcLinkBinding = stockTransferInvcLinkBinding;
    }

    public RichGoLink getStockTransferInvcLinkBinding() {
        return stockTransferInvcLinkBinding;
    }

    public void setGatepassBinding(RichSelectBooleanCheckbox gatepassBinding) {
        this.gatepassBinding = gatepassBinding;
    }

    public RichSelectBooleanCheckbox getGatepassBinding() {
        return gatepassBinding;
    }

    public void setGatePassSummaryBinding(RichSelectBooleanCheckbox gatePassSummaryBinding) {
        this.gatePassSummaryBinding = gatePassSummaryBinding;
    }

    public RichSelectBooleanCheckbox getGatePassSummaryBinding() {
        return gatePassSummaryBinding;
    }

    public void setGatepasslnkBinding(RichGoLink gatepasslnkBinding) {
        this.gatepasslnkBinding = gatepasslnkBinding;
    }

    public RichGoLink getGatepasslnkBinding() {
        return gatepasslnkBinding;
    }

    public void setGatePassSummarylnkBinding(RichGoLink gatePassSummarylnkBinding) {
        this.gatePassSummarylnkBinding = gatePassSummarylnkBinding;
    }

    public RichGoLink getGatePassSummarylnkBinding() {
        return gatePassSummarylnkBinding;
    }
}


