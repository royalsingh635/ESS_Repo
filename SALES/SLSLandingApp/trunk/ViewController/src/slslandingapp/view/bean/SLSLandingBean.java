package slslandingapp.view.bean;


import adf.utils.ebiz.EbizParams;

import alertupdateservice.view.bean.AlertUpdateServiceBean;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.jbo.domain.Number;
import javax.faces.event.ValueChangeEvent;
import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputNumberSlider;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import slslandingapp.ds.TickerDispDS;
import slslandingapp.model.ds.TickerRowDS;
import slslandingapp.view.list.MonthsList;

public class SLSLandingBean {;
    private RichInputNumberSlider ProductsNum = new RichInputNumberSlider();
    
    
    private RichInputNumberSlider CustNum = new RichInputNumberSlider();
    private StringBuffer desc = new StringBuffer("");
    private ArrayList<MonthsList> monthsList = new ArrayList<MonthsList>(); 
    /************** Varibles to be used for tickers******************/
    private Number grossProfitCurrentAmt = new Number(0);
    private Number grossProfitPreviousAmt = new Number(0);
    private Number rejectionCurrentAmt = new Number(0);
    private Number rejectionPreviousAmt = new Number(0);
    private Number totalSalesCurrentAmt = new Number(0);
    private Number totalSalesPreviousAmt = new Number(0);
    private Number totalOrderBookedCurrentAmt = new Number(0);
    private Number totalOrderBookedPreviousAmt = new Number(0);
    private Number oppportunityClosedCurrentAmt = new Number(0);
    private Number oppportunityClosedPreviousAmt = new Number(0);
    RichPanelGroupLayout panelGroup;
   
    private ArrayList<TickerRowDS> tickerList = new ArrayList<TickerRowDS>();
    private ArrayList<TickerRowDS> selectedList = new ArrayList<TickerRowDS>();
    private StringBuffer currencyDesc = new StringBuffer("");
    /************ Variables to used to pass In the Applications *******/
    private StringBuffer docIdForSo = new StringBuffer("");
    private StringBuffer docIdForQuot = new StringBuffer("");
    private StringBuffer docIdForInv = new StringBuffer("");
    private StringBuffer docIdForRMA = new StringBuffer("");
    private RichPopup settingsPopup;
    private RichPanelGroupLayout panelGroupAlert;


    public SLSLandingBean() {
            // To get the total list of selected list of tickers
            OperationBinding bind = this.getBindings().getOperationBinding("getSelectedTickerList");
            if(bind != null){
                bind.execute();
                if(bind.getResult() != null){
                   selectedList = (ArrayList<TickerRowDS>)bind.getResult();
                }    
            }
    }
    /**
     * Method to getBindings
     * @return
     */
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    /**
     * Discloser Listner to be called When the user click on the tab to refresh the VO in the tab
     * @param disclosureEvent
     */
    public void top5ProductsTabDiscloserList(DisclosureEvent disclosureEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProducts");
        binding.getParamsMap().put("val", 5);
        binding.execute();
        ProductsNum.setValue(5);
        
        //executeTopNCustomers
    }
    
    /**
     * Discloser Listner to be called When the user click on the tab to refresh the VO in the tab
     * @param disclosureEvent
     */
    public void top5CustomsersTabDiscloserList(DisclosureEvent disclosureEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("executeTopNCustomers");
        binding.getParamsMap().put("val", 5);
        binding.execute();
        CustNum.setValue(5);
    }
    /**
     * Discloser Listner to be called When the user click on the tab to refresh the VO in the tab
     * @param disclosureEvent
     */
    public void ProductGroupWiseTabDiscloserList(DisclosureEvent disclosureEvent) {
        populateMonths();
        Integer[] i = new Integer[12];
        Calendar cal = Calendar.getInstance();  
        Integer year = cal.get(cal.YEAR);  
        Integer month = cal.get(cal.MONTH)+1;
        for( MonthsList m : getMonthsList()){
            if(m.getmonId() == month){
                    m.setEnabled(new StringBuffer("Y"));    
            }
        }
        i[0] = month;
        OperationBinding binding = this.getBindings().getOperationBinding("setCurrentYearInTempVo");
        binding.getParamsMap().put("year", year.toString());
        binding.execute();
        executeProductGroupWiseSalesVsProfit(new StringBuffer(year.toString()),i);
        
    }
    public void executeProductGroupWiseSalesVsProfit(StringBuffer year, Integer[] month){
        OperationBinding binding = this.getBindings().getOperationBinding("executeProductGroupWiseSalesAndProfit");
        binding.getParamsMap().put("year", year);
        binding.getParamsMap().put("month", month);
        binding.execute();
    }
    public void setProductsNum(RichInputNumberSlider ProductsNum) {
        this.ProductsNum = ProductsNum;
    }

    public RichInputNumberSlider getProductsNum() {
        return ProductsNum;
    }
    /**
     * VCL to execute vo on Changeing the total number of Products
     * @param valueChangeEvent
     */
    public void NoOfProductsVCL(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent != null){
            BigDecimal count = (BigDecimal)valueChangeEvent.getNewValue();
            int i = count.intValue();
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProducts");
            binding.getParamsMap().put("val", i);
            binding.execute();
        }
    }
    /**
     * VCL to execute vo on Changeing the total number of Customers
     * @param valueChangeEvent
     */
    public void NoOfCustomerVCL(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent != null){
            BigDecimal count = (BigDecimal)valueChangeEvent.getNewValue();
            int i = count.intValue();
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNCustomers");
            binding.getParamsMap().put("val", i);
            binding.execute();
        }
    }

    /**
     * Execute ProfitVsSalesVo in click on Tab
     * @param disclosureEvent
     */
    public void ProfitVsSalesDiscloserList(DisclosureEvent disclosureEvent) {
        this.getBindings().getOperationBinding("executeProfitVsSales").execute();
    }

    public void setCustNum(RichInputNumberSlider CustNum) {
        this.CustNum = CustNum;
    }

    public RichInputNumberSlider getCustNum() {
        return CustNum;
    }
    /**
     * Dialog Listner to Execute OpportunityAnalysisVo on Tab Discloser.
     * @param disclosureEvent
     */
    public void oppportunityAnalysisDiscloserList(DisclosureEvent disclosureEvent) {
        this.getBindings().getOperationBinding("executeOpportunityAnalysis").execute();
    }
    /**
     * Method to get Order Count Pending for my Approval
     * @return
     */
    public Number getOrderPendingForMyApprovalCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 21503);
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
        binding.getParamsMap().put("DocTypeId", 21502);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{
            return new Number(0);    
        }   
    }
    
    /**
     * Method to get Invoice Count Pending for my Approval
     * @return
     */
    public Number getInvoicePendingForMyApprovalCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 21504);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{
            return new Number(0);    
        }   
    }
    
    /**
     * Method to get RMA Count Pending for my Approval
     * @return
     */
    public Number getRMAPendingForMyApprovalCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 21510);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{
            return new Number(0);    
        }   
    }
    
     /**
      * Method to get Order Count Pending for Others Approval
      * @return
      */
    public Number getOrderPendingForAtOthersCount(){
         OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
         binding.getParamsMap().put("DocTypeId", 21503);
         binding.getParamsMap().put("CountType", new StringBuffer("O"));
         binding.execute();
         if(binding.getResult() != null){
             return (Number)binding.getResult();
         }else{
             return new Number(0);    
         }   
     }
     
    /**
     * Method to get Quotation Count Pending for Others Approval
     * @return
     */
    public Number getQuotationPendingAtOthersCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 21502);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{
            return new Number(0);    
        }   
    }
    
    /**
     * Method to get Invoice Count Pending for Others Approval
     * @return
     */
    public Number getInvoicePendingAtOthersCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 21504);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{
            return new Number(0);    
        }   
    }
    
    /**
     * Method to get RMA Count Pending for Others Approval
     * @return
     */
    public Number getRMAPendingAtOthersCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 21510);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{
            return new Number(0);    
        }   
    }
    
    /**
     * Method to get Quotation Count Pending
     * @return
     */
    public Number getMyQuotationPendingCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 21502);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{
            return new Number(0);    
        }   
    }
    
    /**
     * Method to get Invoice Count Pending 
     * @return
     */
    public Number getMyInvoicePendingCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 21504);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{
            return new Number(0);    
        }   
    }
    
    /**
     * Method to get RMA Count Pending
     * @return
     */
    public Number getMyRMAPendingCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 21510);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{
            return new Number(0);    
        }   
    }
    
    /**
     * Method to get Order Count Pending for my Approval
     * @return
     */
    public Number getMyOrderPendingCount(){
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 21503);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{
            return new Number(0);    
        }   
    }
/********************************************* For Quotation *********************************************************************/
    /**
     * ACTION to QuotationPendingForMyApproval
     * @return
     */
    public String quotationForMyApprovalACTION() {
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setParametersInWFQuotationVo");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        return "wfQuotation";
    }
    /**
     * ACTION to QuotationPendingOnOthersMyApproval
     * @return
     */
    public String quotationPendingWithOthersACTION() {
        desc = new StringBuffer("Pending at others");
        OperationBinding binding = this.getBindings().getOperationBinding("setParametersInWFQuotationVo");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        return "wfQuotation";
    }
    /**
     * ACTION to QuotationUnposted
     * @return
     */
    public String quotationUnpostedACTION() {
        desc = new StringBuffer("Unposted");
        OperationBinding binding = this.getBindings().getOperationBinding("setParametersInWFQuotationVo");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        return "wfQuotation";
    }
    
/******************************************* Sales Order **********************************************************/
        /**
     * ACTION to OrderForMyApproval
     * @return
     */
    public String orderForMyApprovalACTION() {
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setParametersInWFSoVo");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        return "wfSo";
    }
    /**
     * ACTION to OrderPendingOnOthersMyApproval
     * @return
     */
    public String orderPendingWithOthersACTION() {
        desc = new StringBuffer("Pending at others");
        OperationBinding binding = this.getBindings().getOperationBinding("setParametersInWFSoVo");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        return "wfSo";
    }
    /**
     * ACTION to OrderUnposted
     * @return
     */
    public String orderUnpostedACTION() {
        desc = new StringBuffer("Unposted");
        OperationBinding binding = this.getBindings().getOperationBinding("setParametersInWFSoVo");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        return "wfSo";
    }
    
/******************************************* Sales Invoice **********************************************************/
        /**
     * ACTION to InvoiceForMyApproval
     * @return
     */
    public String invoiceForMyApprovalACTION() {
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setParametersInWFInvVo");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        return "wfInv";
    }
    /**
     * ACTION to InvoicePendingOnOthersMyApproval
     * @return
     */
    public String invoicePendingWithOthersACTION() {
        desc = new StringBuffer("Pending at others");
        OperationBinding binding = this.getBindings().getOperationBinding("setParametersInWFInvVo");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        return "wfInv";
    }
    /**
     * ACTION to InvoiceUnposted
     * @return
     */
    public String invoiceUnpostedACTION() {
        desc = new StringBuffer("Unposted");
        OperationBinding binding = this.getBindings().getOperationBinding("setParametersInWFInvVo");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        return "wfInv";
    }

    /******************************************* Sales RMA **********************************************************/
            /**
         * ACTION to RmaForMyApproval
         * @return
         */
        public String rmaForMyApprovalACTION() {
            desc = new StringBuffer("Pending for My approval");
            OperationBinding binding = this.getBindings().getOperationBinding("setParametersInWFRMAVo");
            binding.getParamsMap().put("type", new StringBuffer("M"));
            binding.execute();
            return "wfRma";
        }
        /**
         * ACTION to InvoicePendingOnOthersMyApproval
         * @return
         */
        public String rmaPendingWithOthersACTION() {
            desc = new StringBuffer("Pending at others");
            OperationBinding binding = this.getBindings().getOperationBinding("setParametersInWFRMAVo");
            binding.getParamsMap().put("type", new StringBuffer("O"));
            binding.execute();
            return "wfRma";
        }
        /**
         * ACTION to rmaUnposted
         * @return
         */
        public String rmsUnpostedACTION() {
            desc = new StringBuffer("Unposted");
            OperationBinding binding = this.getBindings().getOperationBinding("setParametersInWFRMAVo");
            binding.getParamsMap().put("type", new StringBuffer("U"));
            binding.execute();
            return "wfRma";
        }


    public void setDesc(StringBuffer desc) {
        this.desc = desc;
    }

    public StringBuffer getDesc() {
        return desc;
    }

    public void setDocIdForSo(StringBuffer docIdForSo) {
        this.docIdForSo = docIdForSo;
    }

    public StringBuffer getDocIdForSo() {
        return docIdForSo;
    }

    public void setDocIdForQuot(StringBuffer docIdForQuot) {
        this.docIdForQuot = docIdForQuot;
    }

    public StringBuffer getDocIdForQuot() {
        return docIdForQuot;
    }

    public void setDocIdForInv(StringBuffer docIdForInv) {
        this.docIdForInv = docIdForInv;
    }

    public StringBuffer getDocIdForInv() {
        return docIdForInv;
    }

    public void setDocIdForRMA(StringBuffer docIdForRMA) {
        this.docIdForRMA = docIdForRMA;
    }

    public StringBuffer getDocIdForRMA() {
        return docIdForRMA;
    }
    /**
     * Action to go to SO Application
     * @return
     */
    public String viewSalesOrderACTION() {
        this.docIdForSo = new StringBuffer("");
        OperationBinding binding = this.getBindings().getOperationBinding("getCurrentSoId");
        binding.execute();
        if(binding.getResult() != null && !binding.getResult().toString().equals("")){
            this.docIdForSo = new StringBuffer(binding.getResult().toString());    
        }
        if(this.docIdForSo.toString().equals("")){
            return null;
        }else{
            return "goToSalesOrder";
        }
    }
    /**
     * Action to go to SI Application
     * @return
     */
    public String viewSalesInvoiceACTION(){
        this.docIdForInv = new StringBuffer("");
        OperationBinding binding = this.getBindings().getOperationBinding("getCurrentSIId");
        binding.execute();
        if(binding.getResult() != null && !binding.getResult().toString().equals("")){
            this.docIdForInv = new StringBuffer(binding.getResult().toString());    
        }
        if(this.docIdForInv.toString().equals("")){
            return null;
        }else{
            return "goToSalesInvoice";
        }
    }
    
    /**
     * Action to go to Quotation Application
     * @return
     */
    public String viewSalesQuotACTION(){
        this.docIdForQuot = new StringBuffer("");
        OperationBinding binding = this.getBindings().getOperationBinding("getCurrentSQId");
        binding.execute();
        if(binding.getResult() != null && !binding.getResult().toString().equals("")){
            this.docIdForQuot = new StringBuffer(binding.getResult().toString());    
        }
        if(this.docIdForQuot.toString().equals("")){
            return null;
        }else{
            return "goToQuotation";
        }
    }
    
    /**
     * Action to go to RMA Application
     * @return
     */
    public String viewSalesRMAACTION(){
        this.docIdForQuot = new StringBuffer("");
        OperationBinding binding = this.getBindings().getOperationBinding("getCurrentRMAId");
        binding.execute();
        if(binding.getResult() != null && !binding.getResult().toString().equals("")){
            this.docIdForRMA = new StringBuffer(binding.getResult().toString());    
        }
        if(this.docIdForRMA.toString().equals("")){
            return null;
        }else{
            return "goToSalesRMA";
        }
    }
    
    public void populateMonths(){
        monthsList.add(new MonthsList(new StringBuffer("JAN"),1,new StringBuffer("N")));
        monthsList.add(new MonthsList(new StringBuffer("FEB"),2,new StringBuffer("N")));
        monthsList.add(new MonthsList(new StringBuffer("MAR"),3,new StringBuffer("N")));
        monthsList.add(new MonthsList(new StringBuffer("APR"),4,new StringBuffer("N")));
        monthsList.add(new MonthsList(new StringBuffer("MAY"),5,new StringBuffer("N")));
        monthsList.add(new MonthsList(new StringBuffer("JUN"),6,new StringBuffer("N")));
        monthsList.add(new MonthsList(new StringBuffer("JUL"),7,new StringBuffer("N")));
        monthsList.add(new MonthsList(new StringBuffer("AUG"),8,new StringBuffer("N")));
        monthsList.add(new MonthsList(new StringBuffer("SEP"),9,new StringBuffer("N")));
        monthsList.add(new MonthsList(new StringBuffer("OCT"),10,new StringBuffer("N")));
        monthsList.add(new MonthsList(new StringBuffer("NOV"),11,new StringBuffer("N")));
        monthsList.add(new MonthsList(new StringBuffer("DEC"),12,new StringBuffer("N")));
    }

    public ArrayList<MonthsList> getMonthsList() {
        return monthsList;
    }

    public void selectedMonthACTION(ActionEvent actionEvent) {
        StringBuffer months = new StringBuffer("");
        RichCommandLink ob = (RichCommandLink)actionEvent.getSource();
        ob.getAttributes().get("enabled");
        System.out.println("Source is : "+ob.getText()+" "+ob.getAttributes().get("enabled"));
        ArrayList<MonthsList> arrayList = this.getMonthsList();
        //months.append("(");
        Integer[] i = new Integer[13];
        Integer count = 0;
        for( MonthsList m : getMonthsList()){
            if(m.getMon().toString().equals(ob.getText())){
                if(m.getEnabled().toString().equals("N")){
                    m.setEnabled(new StringBuffer("Y"));    
                }else if(m.getEnabled().toString().equals("Y")){
                    m.setEnabled(new StringBuffer("N"));
                }
            }
            if(m.getEnabled().toString().equals("Y")){
                i[count] = m.getmonId();
                count = count + 1;
            }else{
                i[count] = null;
                count = count + 1;
            }
        }
        executeProductGroupWiseSalesVsProfit(new StringBuffer(""), i);
    }

    /**
     * Value change listner to execute the Product groupwise sales Vs Profit Vo
     * @param valueChangeEvent
     */
    public void ProductGroupwiseYearVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            Integer count = 0;
            Integer[] i = new Integer[13];
            ArrayList<MonthsList> arrayList = this.getMonthsList();
            for( MonthsList m : getMonthsList()){
                if(m.getEnabled().toString().equals("Y")){
                    i[count] = m.getmonId();
                    count = count + 1;
                }else{
                    i[count] = null;
                    count = count + 1;
                }
            }
          executeProductGroupWiseSalesVsProfit(new StringBuffer(valueChangeEvent.getNewValue().toString()), i);
            
        }

    }
    /**
     * Getter to get Gross Profit Current Amt
     * @return
     */
    public Number getGrossProfitCurrentAmt() {
            StringBuffer s = new StringBuffer("");
            OperationBinding binding = this.getBindings().getOperationBinding("getTickerAmount");
            binding.getParamsMap().put("tickerId", 464);
            binding.execute();
            if(binding.getResult() != null){
                s = (StringBuffer)binding.getResult();
                try {
                    if(!s.toString().equals("")){
                    //System.out.println(" grossProfitPreviousAmt"+ s.substring(s.indexOf(":")+1));
                    //System.out.println(" grossProfitCurrentAmt"+ s.substring(0,s.indexOf(":")));
                    grossProfitPreviousAmt = new Number(s.substring(s.indexOf(":")+1));
                    grossProfitPreviousAmt = (Number)grossProfitPreviousAmt.div(new Number(1000000));
                    grossProfitCurrentAmt = new Number(s.substring(0,s.indexOf(":")));
                    grossProfitCurrentAmt = (Number)grossProfitCurrentAmt.div(new Number(1000000));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return grossProfitCurrentAmt;
        
    }

    public Number getGrossProfitPreviousAmt() {
        return grossProfitPreviousAmt;
    }

    public StringBuffer getCurrencyDesc() {
        OperationBinding bind = this.getBindings().getOperationBinding("getCurrIdBs");
        if(bind != null){
           currencyDesc = (StringBuffer)bind.execute();
        }
        return currencyDesc;
    }

    /**
     * Getter to get Rejection Current Amt
     * @return
     */
    
    public Number getRejectionCurrentAmt() {
        StringBuffer s = new StringBuffer("");
        OperationBinding binding = this.getBindings().getOperationBinding("getTickerAmount");
        binding.getParamsMap().put("tickerId", 466);
        binding.execute();
        if(binding.getResult() != null){
            s = (StringBuffer)binding.getResult();
            try {
                if(!s.toString().equals("")){
                //System.out.println(" grossProfitPreviousAmt"+ s.substring(s.indexOf(":")+1));
                //System.out.println(" grossProfitCurrentAmt"+ s.substring(0,s.indexOf(":")));
                rejectionPreviousAmt = new Number(s.substring(s.indexOf(":")+1));
                rejectionPreviousAmt = (Number)rejectionPreviousAmt.div(new Number(1000000));
                rejectionCurrentAmt = new Number(s.substring(0,s.indexOf(":")));
                rejectionCurrentAmt = (Number)rejectionCurrentAmt.div(new Number(1000000));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rejectionCurrentAmt;
    }

    public Number getRejectionPreviousAmt() {
        return rejectionPreviousAmt;
    }
    
    /**
     * Getter to get Total Sales Current Amt
     * @return
     */
    
    public Number getTotalSalesCurrentAmt() {
        StringBuffer s = new StringBuffer("");
        OperationBinding binding = this.getBindings().getOperationBinding("getTickerAmount");
        binding.getParamsMap().put("tickerId", 468);
        binding.execute();
        if(binding.getResult() != null){
            s = (StringBuffer)binding.getResult();
            try {
                if(!s.toString().equals("")){
                    //System.out.println(" grossProfitPreviousAmt"+ s.substring(s.indexOf(":")+1));
                    //System.out.println(" grossProfitCurrentAmt"+ s.substring(0,s.indexOf(":")));
                    totalSalesPreviousAmt = new Number(s.substring(s.indexOf(":")+1));
                    totalSalesPreviousAmt = (Number)totalSalesPreviousAmt.div(new Number(1000000));
                    totalSalesCurrentAmt = new Number(s.substring(0,s.indexOf(":")));
                    totalSalesCurrentAmt = (Number)totalSalesCurrentAmt.div(new Number(1000000));
                    
                       
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return totalSalesCurrentAmt;
    }

    public Number getTotalSalesPreviousAmt() {
        return totalSalesPreviousAmt;
    }
    /**
     * Method to get the Total Order Booked Amount
     * @return
     */
    public Number getTotalOrderBookedCurrentAmt() {
        StringBuffer s = new StringBuffer("");
        OperationBinding binding = this.getBindings().getOperationBinding("getTickerAmount");
        binding.getParamsMap().put("tickerId", 467);
        binding.execute();
        if(binding.getResult() != null){
            s = (StringBuffer)binding.getResult();
            try {
                if(!s.toString().equals("")){
                    //System.out.println(" grossProfitPreviousAmt"+ s.substring(s.indexOf(":")+1));
                    //System.out.println(" grossProfitCurrentAmt"+ s.substring(0,s.indexOf(":")));
                    totalOrderBookedPreviousAmt = new Number(s.substring(s.indexOf(":")+1));
                    totalOrderBookedPreviousAmt = (Number)totalOrderBookedPreviousAmt.div(new Number(1000000));
                    totalOrderBookedCurrentAmt = new Number(s.substring(0,s.indexOf(":")));
                    totalOrderBookedCurrentAmt = (Number)totalOrderBookedCurrentAmt.div(new Number(1000000));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return totalOrderBookedCurrentAmt;
    }

    /**
     * Method to get the Total Order Booked Amount
     * @return
     */
    public Number getTotalOrderBookedPreviousAmt() {
        return totalOrderBookedPreviousAmt;
    }
    
    public Number getOppportunityClosedCurrentAmt() {
        StringBuffer s = new StringBuffer("");
        OperationBinding binding = this.getBindings().getOperationBinding("getTickerAmount");
        binding.getParamsMap().put("tickerId", 465);
        binding.execute();
        if(binding.getResult() != null){
            s = (StringBuffer)binding.getResult();
            try {
                if(!s.toString().equals("")){
                    //System.out.println(" grossProfitPreviousAmt"+ s.substring(s.indexOf(":")+1));
                    //System.out.println(" grossProfitCurrentAmt"+ s.substring(0,s.indexOf(":")));
                    oppportunityClosedPreviousAmt = new Number(s.substring(s.indexOf(":")+1));
                    oppportunityClosedPreviousAmt = (Number)oppportunityClosedPreviousAmt.div(new Number(1000000));
                    oppportunityClosedCurrentAmt = new Number(s.substring(0,s.indexOf(":")));
                    oppportunityClosedCurrentAmt = (Number)oppportunityClosedCurrentAmt.div(new Number(1000000));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return oppportunityClosedCurrentAmt;
    }

    public Number getOppportunityClosedPreviousAmt() {
        return oppportunityClosedPreviousAmt;
    }
    /**
     * Method to open Settings popup
     * @param actionEvent
     */
     public void settingPopupACTION(ActionEvent actionEvent) {
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
        /* for(TickerRowDS t : tickerList){
            
             for(TickerRowDS r : selectedList){
                if(r.getTickerId().equals(t.getTickerId())){
                    r.setTickerDesc(t.getTickerDesc());
                    break;
                }
            }  
        }
         */ // To remove the selecetd tickers from the total list of tickers
        /* for(TickerRowDS t : selectedList){
            for(TickerRowDS r : tickerList){
                if(r.getTickerId().equals(t.getTickerId())){
                    tickerList.remove(r);
                    break;
                } 
            } 
        } */ 
        System.out.println("In PopupList :");
        for(TickerRowDS r : selectedList){
            System.out.println(r.getSeqNo()+ " : "+r.getTickerDesc()+" : "+r.getTickerId());
        }
        showPopup(this.settingsPopup,true);
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

    public void setSettingsPopup(RichPopup settingsPopup) {
        this.settingsPopup = settingsPopup;
    }

    public RichPopup getSettingsPopup() {
        return settingsPopup;
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
    /**
     * Method to add a selected row from ticker setup page.
     * @param actionEvent
     */
    public void addTickerACTION(ActionEvent actionEvent) {
        String ticker = "0";
        RichCommandImageLink ob = (RichCommandImageLink)actionEvent.getSource();
        ob.getAttributes().get("tickerId");
        System.out.println("Source is : " + ob.getText() + " " + ob.getAttributes().get("tickerId"));
        if (ob.getAttributes().get("tickerId") != null) {
            if(this.selectedList.size() < 4){
                Integer seq = 0;
                for(TickerRowDS r : this.selectedList){
                    if(seq < r.getSeqNo()){
                        seq = r.getSeqNo();
                    }
                }
                seq = seq+1;
                ticker = ob.getAttributes().get("tickerId").toString();
                for(TickerRowDS r : this.tickerList){
                    if(ticker.equals(r.getTickerId())){
                        System.out.println("Seq : "+seq + "Ticker ID : "+ticker+" Desc : "+r.getTickerDesc());
                        TickerRowDS t = new TickerRowDS();
                        t.setTickerId(ticker);
                        t.setSeqNo(seq);
                        t.setTickerDesc(r.getTickerDesc());
                        t.setCurr(r.getCurr());
                        t.setCurrentAmt(r.getCurrentAmt());
                        t.setPreviousAmt(r.getPreviousAmt());
                        selectedList.add(t);
                        tickerList.remove(r);
                        break;
                    }
                }
            
            }else{
               FacesMessage message =
                   new FacesMessage("Cannot select more that Four Tickers !");
               message.setSeverity(FacesMessage.SEVERITY_ERROR);
               FacesContext.getCurrentInstance().addMessage(null, message);
            }
            
        }

    }
    /**
     * Method to delete a selected row from ticker setup page.
     * @param actionEvent
     */
    public void deleteTickerACTION(ActionEvent actionEvent) {
        String ticker = "0";
        RichCommandImageLink ob = (RichCommandImageLink)actionEvent.getSource();
        ob.getAttributes().get("tickerId");
        System.out.println("Source is : " + ob.getText() + " " + ob.getAttributes().get("tickerId"));
        if (ob.getAttributes().get("tickerId") != null) {
                Integer seq = 0;
                for(TickerRowDS r : this.tickerList){
                    if(seq < r.getSeqNo()){
                        seq = r.getSeqNo();
                    }
                }
                seq = seq+1;
                ticker = ob.getAttributes().get("tickerId").toString();
                for(TickerRowDS r : this.selectedList){
                    if(ticker.equals(r.getTickerId())){
                        System.out.println("Seq : "+seq + "Ticker ID : "+ticker+" Desc : "+r.getTickerDesc());
                        TickerRowDS t = new TickerRowDS();
                        t.setTickerId(ticker);
                        t.setSeqNo(seq);
                        t.setTickerDesc(r.getTickerDesc());
                        t.setCurr(r.getCurr());
                        t.setCurrentAmt(r.getCurrentAmt());
                        t.setPreviousAmt(r.getPreviousAmt());
                        
                        tickerList.add(t);
                        selectedList.remove(r);
                        break;
                    }
                }
                seq = 1;
                for(TickerRowDS r : this.selectedList){
                        r.setSeqNo(seq); 
                        seq = seq +1;
                }
            
        }

    }

    /**
     * Move the selected ticker up
     * @param actionEvent
     */
    public void moveSelectedUpACTION(ActionEvent actionEvent) {
        Integer sequenceNo = 0;
        RichCommandImageLink ob = (RichCommandImageLink)actionEvent.getSource();
        ob.getAttributes().get("sequenceNo");
        System.out.println("Source is : " + ob.getText() + " " + ob.getAttributes().get("sequenceNo"));
        sequenceNo = (Integer)ob.getAttributes().get("sequenceNo");
        
        
        if(sequenceNo != 1){
            TickerRowDS selup = this.selectedList.get(sequenceNo - 2);
            TickerRowDS selcur = this.selectedList.get(sequenceNo - 1);
            selup.setSeqNo(sequenceNo);
            selcur.setSeqNo(sequenceNo - 1);
            this.selectedList.set(sequenceNo - 2, selcur); 
            this.selectedList.set(sequenceNo - 1, selup);
        }
        
       /*  if(sequenceNo != 1){
            for(TickerRowDS r : this.selectedList){
                if( r.getSequenceNo() == (sequenceNo - 1) ){
                    selup = r; 
                }
                if( r.getSequenceNo() == sequenceNo ){
                    selcur = r; 
                } 
            }
        } */
    }
    /**
     * Move the selected ticker down
     * @param actionEvent
     */
    public void moveSelectedDownACTION(ActionEvent actionEvent) {
        Integer sequenceNo = 0;
        RichCommandImageLink ob = (RichCommandImageLink)actionEvent.getSource();
        ob.getAttributes().get("sequenceNo");
        System.out.println("Source is : " + ob.getText() + " " + ob.getAttributes().get("sequenceNo"));
        sequenceNo = (Integer)ob.getAttributes().get("sequenceNo");
        
        
        if(sequenceNo != 3){
            TickerRowDS selcur = this.selectedList.get(sequenceNo - 1);
            TickerRowDS seldn = this.selectedList.get(sequenceNo);
            seldn.setSeqNo(sequenceNo);
            selcur.setSeqNo(sequenceNo + 1);
            this.selectedList.set(sequenceNo, selcur); 
            this.selectedList.set(sequenceNo - 1, seldn);
        }
        
    }
    /**
     * PopPup Cancel Listner
     * @param popupCanceledEvent
     */
    public void settingPopupCancelLIST(PopupCanceledEvent popupCanceledEvent) {
        
    }
    /**
     * Save ticker settings
     * @param dialogEvent
     */
    public void saveSettingsPopUpLIST(DialogEvent dialogEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("saveUserSettings");
        binding.execute();
        for(TickerRowDS r : getSelectedList()){
            OperationBinding bind = this.getBindings().getOperationBinding("insertRecord");
            bind.getParamsMap().put("seqNo",r.getSeqNo());
            bind.getParamsMap().put("tickeId",r.getTickerId());
            bind.execute();      
        }
        this.getBindings().getOperationBinding("Commit").execute();
    }
    
    public ArrayList<TickerDispDS> getTickerSequence(){
        ArrayList<TickerDispDS> list = new ArrayList<TickerDispDS>();
        
        list.clear();
        for(TickerRowDS r : selectedList){
            TickerDispDS t = new TickerDispDS();
            t.setSeqNo(r.getSeqNo());
            t.setTickerId(r.getTickerId());
            t.setTickerDesc(r.getTickerDesc());
            t.setArrow(r.getArrow());
            t.setCurrentAmt(r.getCurrentAmt());
            t.setPreviousAmt(r.getPreviousAmt());
            list.add(t);
        }
        return list;
        
    }
    
    public Integer getTickerCount(){
        Integer i = 0;
        if(selectedList != null){
            i = selectedList.size();   
        }
        return i;
    }
    // Code For Alert and Update Service 
        private AlertUpdateServiceBean bean=new  AlertUpdateServiceBean();
    RichPanelFormLayout panelForm;
        private int i=0;
        public String getText(){
            if(i==0){
             this.printDetail();
              i++;  
            } 
            return "Y";
        }
        
        public void printDetail() {
        UIOutput o1=bean.getAlert("300", "275",EbizParams.GLBL_APP_CLD_ID(),EbizParams.GLBL_APP_SERV_LOC(),EbizParams.GLBL_HO_ORG_ID(),EbizParams.GLBL_APP_USR_ORG(),EbizParams.GLBL_APP_USR(),"00306");
        this.panelForm.getChildren().add(o1);
        System.out.println("addind Refresh");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.panelForm);
        AdfFacesContext.getCurrentInstance().addPartialTarget(o1);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelGroupAlert());
        }
        
        public void getUpdate(ClientEvent clientEvent) {
            System.out.println("I am in bean");
            bean.getUpdate(this.getPanelForm(),EbizParams.GLBL_APP_CLD_ID(),EbizParams.GLBL_APP_SERV_LOC(),EbizParams.GLBL_HO_ORG_ID(),EbizParams.GLBL_APP_USR_ORG(),EbizParams.GLBL_APP_USR(),"00306");
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelForm());
           
        }

        public void setPanelForm(RichPanelFormLayout panelForm) {
            this.panelForm = panelForm;
        }

        public RichPanelFormLayout getPanelForm() {
            return panelForm;
        }

        /*   public void setPanelGroup(RichPanelGroupLayout panelGroup) {
            this.panelGroup = panelGroup;
        }

        public RichPanelGroupLayout getPanelGroup() {
            return panelGroup;
        } */


    public void setPanelGroupAlert(RichPanelGroupLayout panelGroupAlert) {
        this.panelGroupAlert = panelGroupAlert;
    }

    public RichPanelGroupLayout getPanelGroupAlert() {
        return panelGroupAlert;
    }
}
