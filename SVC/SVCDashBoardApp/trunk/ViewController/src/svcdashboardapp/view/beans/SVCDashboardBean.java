package svcdashboardapp.view.beans;

//import com.sun.org.apache.xpath.internal.operations.Number;

import adf.utils.ebiz.EbizParams;

import alertupdateservice.view.bean.AlertUpdateServiceBean;

import java.math.BigDecimal;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputNumberSlider;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;


import oracle.jbo.domain.Number;
import oracle.adf.share.logging.ADFLogger;


import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;

import oracle.adf.view.rich.component.rich.nav.RichLink;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.render.ClientEvent;

import org.apache.myfaces.trinidad.component.UIXSwitcher;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import svcdashboardapp.model.ds.TickerRowDS;

public class SVCDashboardBean {
    
//    private RichInputNumberSlider suppNum = new RichInputNumberSlider();
//    private RichInputNumberSlider productNum = new RichInputNumberSlider();
//    private RichInputNumberSlider productGrpNum = new RichInputNumberSlider();
    private StringBuffer desc = new StringBuffer("");
    private RichPanelGroupLayout alertPgBind;

    public void setDesc(StringBuffer desc) {
        this.desc = desc;
    }

    public StringBuffer getDesc() {
        return desc;
    }
//    private StringBuffer docIdForQuot = new StringBuffer("");
    private String facetValue = "Service Contract";
   // private String facetValuePo = "po";
    public void setFacetValue(String facetValue) {
        this.facetValue = facetValue;
    }

    public String getFacetValue() {
        return facetValue;
    }
    
    
    
    //private String facetValueRfqPage = "rfq";
    private UIXSwitcher switcherBind;
    private Number grossCurrentTotalStock = new Number(0);
    private Number grossPreviousTotalStock = new Number(0);
    private Number grossCurrentTotalInvoiceValue = new Number(0);
    private Number grossPreviousTotalInvoiceValue = new Number(0);
    private Number grossCurrentTotalPurchase = new Number(0);
    private Number grossPreviousTotalPurchase = new Number(0);
    private RichPopup tkrSettingsPopup;

    public void setTkrSettingsPopup(RichPopup tkrSettingsPopup) {
        this.tkrSettingsPopup = tkrSettingsPopup;
    }

    public RichPopup getTkrSettingsPopup() {
        return tkrSettingsPopup;
    }
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
    private String tickerFirstLableName;
    private String tickerFirstDatarange;
    private String tickerSecondLableName;
    private String tickerSecondDatarange;
    private String tickerThirdLableName;
    private String tickerThirdtDatarange;

    public void setTickerFirstDatarange(String tickerFirstDatarange) {
        this.tickerFirstDatarange = tickerFirstDatarange;
    }

    public String getTickerFirstDatarange() {
        //String bind=null;
        OperationBinding tkrName = this.getBindings().getOperationBinding("getTkrLableName");
        tkrName.getParamsMap().put("tkrPos", 1);
        tkrName.execute();
        String name = "Data Range Not Available";
        String tkr=(String)tkrName.getResult();
        adfLog.info("Value of  Second tkr:::"+tkr);
        if(tkrName.getResult()!=null){
            OperationBinding name1 = this.getBindings().getOperationBinding("getTckrDataRange");
            name1.getParamsMap().put("Tkr", tkr);
            name1.execute();
            if(name1.getResult()!=null)
            {
            name = name1.getResult().toString();
            }
            else
            {
              name = "Data Range Not Available";
            }
        }
        return name;
        //return tickerFirstDatarange;
    }

    public void setTickerSecondDatarange(String tickerSecondDatarange) {
        this.tickerSecondDatarange = tickerSecondDatarange;
    }

    public String getTickerSecondDatarange() {
        OperationBinding tkrName = this.getBindings().getOperationBinding("getTkrLableName");
        tkrName.getParamsMap().put("tkrPos", 2);
        tkrName.execute();
        String name = "Data Range Not Available";
        String tkr=(String)tkrName.getResult();
        adfLog.info("Value of  Second tkr:::"+tkr);
        if(tkrName.getResult()!=null){
            OperationBinding name1 = this.getBindings().getOperationBinding("getTckrDataRange");
            name1.getParamsMap().put("Tkr", tkr);
            name1.execute();
            if(name1.getResult()!=null)
            {
            name = name1.getResult().toString();
            }
            else
            {
              name = "Data Range Not Available";
            }
        }
        return name;
        //return tickerSecondDatarange;
    }

    public void setTickerThirdtDatarange(String tickerThirdtDatarange) {
        this.tickerThirdtDatarange = tickerThirdtDatarange;
    }

    public String getTickerThirdtDatarange() {
        OperationBinding tkrName = this.getBindings().getOperationBinding("getTkrLableName");
        tkrName.getParamsMap().put("tkrPos", 3);
        tkrName.execute();
        String name = "Data Range Not Available";
        String tkr=(String)tkrName.getResult();
     adfLog.info("Value of tkr:::"+tkr);
        if(tkrName.getResult()!=null){
            OperationBinding name1 = this.getBindings().getOperationBinding("getTckrDataRange");
            name1.getParamsMap().put("Tkr", tkr);
            name1.execute();
            if(name1.getResult()!=null)
            {
            name = name1.getResult().toString();
            }
            else
            {
              name = "Data Range Not Available";
            }
        }
        return name;
        //return tickerThirdtDatarange;
    }
    private Number SCPendingForMyApprovalCount;
    private Number WorkOrderPendingForMyApprovalCount;
    private Number SCPendingForOtherApprovalCount;
    private Number WorkOrderPendingForOtherApprovalCount;
    private Number InvoicePendingForOtherApprovalCount;
    private Number SCUnpostedDocumentsCount;
    private Number WorkOrderUnpostedDocumentsCount;
    private Number InvoiceUnpostedDocumentsCount;
    private Number InvoicePendingForMyApprovalCount;
    private static ADFLogger adfLog  = ADFLogger.createADFLogger(SVCDashboardBean.class);

    public void setSCPendingForOtherApprovalCount(Number SCPendingForOtherApprovalCount) {
        this.SCPendingForOtherApprovalCount = SCPendingForOtherApprovalCount;
    }

    public Number getSCPendingForOtherApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
                binding.getParamsMap().put("DocTypeId", 23001);
                binding.getParamsMap().put("CountType", new StringBuffer("O"));
                binding.execute();
                if(binding.getResult() != null){
                    return (Number)binding.getResult();
                }else{ 
                    return new Number(0);    
                }
        //return SCPendingForOtherApprovalCount;
    }

    public void setWorkOrderPendingForOtherApprovalCount(Number WorkOrderPendingForOtherApprovalCount) {
        this.WorkOrderPendingForOtherApprovalCount = WorkOrderPendingForOtherApprovalCount;
    }

    public Number getWorkOrderPendingForOtherApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
                binding.getParamsMap().put("DocTypeId", 23003);
                binding.getParamsMap().put("CountType", new StringBuffer("O"));
                binding.execute();
                if(binding.getResult() != null){
                    return (Number)binding.getResult();
                }else{ 
                    return new Number(0);    
                }
        //return WorkOrderPendingForOtherApprovalCount;
    }

    public void setInvoicePendingForOtherApprovalCount(Number InvoicePendingForOtherApprovalCount) {
        this.InvoicePendingForOtherApprovalCount = InvoicePendingForOtherApprovalCount;
    }

    public Number getInvoicePendingForOtherApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
                binding.getParamsMap().put("DocTypeId", 23008);
                binding.getParamsMap().put("CountType", new StringBuffer("O"));
                binding.execute();
                if(binding.getResult() != null){
                    return (Number)binding.getResult();
                }else{ 
                    return new Number(0);    
                }
        //return InvoicePendingForOtherApprovalCount;
    }

    public void setSCUnpostedDocumentsCount(Number SCUnpostedDocumentsCount) {
        this.SCUnpostedDocumentsCount = SCUnpostedDocumentsCount;
    }

    public Number getSCUnpostedDocumentsCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
                binding.getParamsMap().put("DocTypeId", 23001);
                binding.getParamsMap().put("CountType", new StringBuffer("U"));
                binding.execute();
                if(binding.getResult() != null){
                    return (Number)binding.getResult();
                }else{ 
                    return new Number(0);    
                }
       // return SCUnpostedDocumentsCount;
    }

    public void setWorkOrderUnpostedDocumentsCount(Number WorkOrderUnpostedDocumentsCount) {
        this.WorkOrderUnpostedDocumentsCount = WorkOrderUnpostedDocumentsCount;
    }

    public Number getWorkOrderUnpostedDocumentsCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
                binding.getParamsMap().put("DocTypeId", 23003);
                binding.getParamsMap().put("CountType", new StringBuffer("U"));
                binding.execute();
                if(binding.getResult() != null){
                    return (Number)binding.getResult();
                }else{ 
                    return new Number(0);    
                }
        //return WorkOrderUnpostedDocumentsCount;
    }

    public void setInvoiceUnpostedDocumentsCount(Number InvoiceUnpostedDocumentsCount) {
        this.InvoiceUnpostedDocumentsCount = InvoiceUnpostedDocumentsCount;
    }

    public Number getInvoiceUnpostedDocumentsCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
                binding.getParamsMap().put("DocTypeId", 23008);
                binding.getParamsMap().put("CountType", new StringBuffer("U"));
                binding.execute();
                if(binding.getResult() != null){
                    return (Number)binding.getResult();
                }else{ 
                    return new Number(0);    
                }
        //return InvoiceUnpostedDocumentsCount;
    }

    public void setWorkOrderPendingForMyApprovalCount(Number WorkOrderPendingForMyApprovalCount) {
        this.WorkOrderPendingForMyApprovalCount = WorkOrderPendingForMyApprovalCount;
    }

    public Number getWorkOrderPendingForMyApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
                binding.getParamsMap().put("DocTypeId", 23003);
                binding.getParamsMap().put("CountType", new StringBuffer("M"));
                binding.execute();
                if(binding.getResult() != null){
                    return (Number)binding.getResult();
                }else{ 
                    return new Number(0);    
                }
        //return WorkOrderPendingForMyApprovalCount;
    }

    public void setInvoicePendingForMyApprovalCount(Number InvoicePendingForMyApprovalCount) {
        this.InvoicePendingForMyApprovalCount = InvoicePendingForMyApprovalCount;
    }

    public Number getInvoicePendingForMyApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
                binding.getParamsMap().put("DocTypeId", 23008);
                binding.getParamsMap().put("CountType", new StringBuffer("M"));
                binding.execute();
                if(binding.getResult() != null){
                    return (Number)binding.getResult();
                }else{ 
                    return new Number(0);    
                }
        //return InvoicePendingForMyApprovalCount;
    }
   

    public void setSCPendingForMyApprovalCount(Number SCPendingForMyApprovalCount) {
        this.SCPendingForMyApprovalCount = SCPendingForMyApprovalCount;
    }

    public Number getSCPendingForMyApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
                binding.getParamsMap().put("DocTypeId", 23001);
                binding.getParamsMap().put("CountType", new StringBuffer("M"));
                binding.execute();
                if(binding.getResult() != null){
                    return (Number)binding.getResult();
                }else{ 
                    return new Number(0);    
                }

       // return SCPendingForMyApprovalCount;
    }

   
    public SVCDashboardBean() {
    }

    public void setTickerFirstLableName(String tickerFirstLableName) {
        this.tickerFirstLableName = tickerFirstLableName;
    }
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String getTickerFirstLableName() {
        OperationBinding tkrName = this.getBindings().getOperationBinding("getTkrLableName");
        tkrName.getParamsMap().put("tkrPos", 1);
        tkrName.execute();
        String name = "No Ticker Selected";
        if(tkrName.getResult()!=null){
            name = tkrName.getResult().toString();
        }
        return name;
        //return tickerFirstLableName;
    }

    public void setTickerSecondLableName(String tickerSecondLableName) {
        this.tickerSecondLableName = tickerSecondLableName;
    }

    public String getTickerSecondLableName() {
        OperationBinding tkrName = this.getBindings().getOperationBinding("getTkrLableName");
        tkrName.getParamsMap().put("tkrPos", 2);
        tkrName.execute();
        String name = "No Ticker Selected";
        if(tkrName.getResult()!=null){
            name = tkrName.getResult().toString();
        }
        return name;
        //return tickerSecondLableName;
    }

    public void setTickerThirdLableName(String tickerThirdLableName) {
        this.tickerThirdLableName = tickerThirdLableName;
    }

    public String getTickerThirdLableName() {
        OperationBinding tkrName = this.getBindings().getOperationBinding("getTkrLableName");
        tkrName.getParamsMap().put("tkrPos", 3);
        tkrName.execute();
        String name = "No Ticker Selected";
        if(tkrName.getResult()!=null){
            name = tkrName.getResult().toString();
        }
        
        return name;
        
        
        
       // return tickerThirdLableName;
    }

    public void tkrSettingPopupAction(ActionEvent actionEvent) {
        // Add event code here...
        
        OperationBinding binding = this.getBindings().getOperationBinding("getTickerList");
        if(binding != null){
            binding.execute();
            if(binding.getResult() != null){
            
               tickerList = (ArrayList<TickerRowDS>)binding.getResult();
               adfLog.info("Total ticker available::::"+tickerList.size());

            }    
        }
        // To get the total list of selected list of tickers
        OperationBinding bind = this.getBindings().getOperationBinding("getSelectedTickerList");
        if(bind != null){
            bind.execute();
            if(bind.getResult() != null){
                selectedList.clear();
                selectedList = (ArrayList<TickerRowDS>)bind.getResult();
                adfLog.info("Selected ticker:::"+selectedList.size());
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

    public void setGrossCurrentTotalStock(Number grossCurrentTotalStock) {
        this.grossCurrentTotalStock = grossCurrentTotalStock;
    }

    public Number getGrossCurrentTotalStock() {
        
        adfLog.info(" in the getGrossCurrentTotalStock");
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
              adfLog.info("Size of arraylist:::"+queryVal.size());
            if(queryVal.size()>0){
            grossCurrentTotalStock = queryVal.get(0);
            adfLog.info("Value of grossCurrentTotalStock"+grossCurrentTotalStock);
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
            adfLog.info("Value of grossPreviousTotalStock"+grossPreviousTotalStock);
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
       // return grossCurrentTotalStock;
    }

    public void setGrossPreviousTotalStock(Number grossPreviousTotalStock) {
        this.grossPreviousTotalStock = grossPreviousTotalStock;
    }

    public Number getGrossPreviousTotalStock() {
        return grossPreviousTotalStock;
    }

    public void setGrossCurrentTotalInvoiceValue(Number grossCurrentTotalInvoiceValue) {
        this.grossCurrentTotalInvoiceValue = grossCurrentTotalInvoiceValue;
    }

    public Number getGrossCurrentTotalInvoiceValue() {
        
        
        
        adfLog.info("int the GrossCurrentTotalInvoiceValue");
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
            adfLog.info(" Size  of ArrayList "+queryVal.size());
             if(queryVal.size()>0){
            grossCurrentTotalInvoiceValue = queryVal.get(0);
            adfLog.info("Value of grossCurrentTotalInvoiceValue "+grossCurrentTotalInvoiceValue );
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
            adfLog.info(" Value of grossPreviousTotalInvoiceValue "+grossPreviousTotalInvoiceValue);
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
        //return grossCurrentTotalInvoiceValue;
    }

    public void setGrossPreviousTotalInvoiceValue(Number grossPreviousTotalInvoiceValue) {
        this.grossPreviousTotalInvoiceValue = grossPreviousTotalInvoiceValue;
    }

    public Number getGrossPreviousTotalInvoiceValue() {
        return grossPreviousTotalInvoiceValue;
    }

    public void setGrossCurrentTotalPurchase(Number grossCurrentTotalPurchase) {
        this.grossCurrentTotalPurchase = grossCurrentTotalPurchase;
    }

    public Number getGrossCurrentTotalPurchase() {
        
        
        adfLog.info("in the GrossCurrentTotalPurchase");
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
            adfLog.info("Size of ArrayList"+queryVal.size());
            if(queryVal.size()>0){
            grossCurrentTotalPurchase = queryVal.get(0);
            adfLog.info(" Value of grossCurrentTotalPurchase"+grossCurrentTotalPurchase);
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
           adfLog.info("Value of  grossPreviousTotalPurchase"+grossPreviousTotalPurchase);
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
        //return grossCurrentTotalPurchase;
    }

    public void setGrossPreviousTotalPurchase(Number grossPreviousTotalPurchase) {
        this.grossPreviousTotalPurchase = grossPreviousTotalPurchase;
    }

    public Number getGrossPreviousTotalPurchase() {
        return grossPreviousTotalPurchase;
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

    public void tickerSettingDialogueListener(DialogEvent dialogEvent) {
        // Add event code here...
        
        OperationBinding unsetPos = this.getBindings().getOperationBinding("unsetAllTkrPosForUsr");
         unsetPos.execute();      
        for(TickerRowDS r : getSelectedList()){
            OperationBinding updateTkr = this.getBindings().getOperationBinding("updateTickerSettingForUsr");
            updateTkr.getParamsMap().put("seqNo",r.getSequenceNo());
            updateTkr.getParamsMap().put("tickeId",r.getTickerId());
            updateTkr.execute();      
        }
        this.getBindings().getOperationBinding("Commit").execute();
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


    public void moveSelectedUpAction(ActionEvent actionEvent) {
        // Add event code here...
        Integer sequenceNo = 0;
        RichLink ob = (RichLink)actionEvent.getSource();
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

    public void moveSelectedDownAction(ActionEvent actionEvent) {
        // Add event code here...
        
        Integer sequenceNo = 0;
        RichLink ob = (RichLink)actionEvent.getSource();
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

    public void deleteTickerAction(ActionEvent actionEvent) {
        // Add event code here...
        String ticker =null;
        RichLink ob = (RichLink)actionEvent.getSource();
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

    public void addTickerName(ActionEvent actionEvent) {
        // Add event code here...
        String ticker = null;
        RichLink ob = (RichLink)actionEvent.getSource();
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

    public String scForMyApprovalAction() {
        // Add event code here...
        

                desc = new StringBuffer("Pending for My approval");
                OperationBinding binding = this.getBindings().getOperationBinding("setWFScView");
                binding.getParamsMap().put("type", new StringBuffer("M"));
                binding.execute();
                setFacetValue("Service Contract");
                //facetValueRfqPage = "rfq";
                return "toWfViewPage";
        //return null;
    }

    public String WorkOrderForMyApprovalAction() {
        // Add event code here...
        desc = new StringBuffer("Pending for My approval");
                OperationBinding binding = this.getBindings().getOperationBinding("setWFWorkOrderView");
                binding.getParamsMap().put("type", new StringBuffer("M"));
                binding.execute();
                setFacetValue("Work Order");
                //facetValueRfqPage = "rfq";
                return "toWfViewPage";
        //return null;
    }

    public String InvcForMyApprovalAction() {
        // Add event code here...
        desc = new StringBuffer("Pending for My approval");
                OperationBinding binding = this.getBindings().getOperationBinding("setWFSvcInvcView");
                binding.getParamsMap().put("type", new StringBuffer("M"));
                binding.execute();
                setFacetValue("Service Invoice");
                //facetValueRfqPage = "rfq";
                return "toWfViewPage";
        //return null;
    }

    public String ScForOtherAction() {
        // Add event code here...
        desc = new StringBuffer("Pending With  Others");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFScView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValue("Service Contract");
        //facetValueRfqPage = "rfq";
        return "toWfViewPage";
        //return null;
    }

    public String WorkOrderForOtherAction() {
        // Add event code here...
        desc = new StringBuffer("Pending with Others");
                OperationBinding binding = this.getBindings().getOperationBinding("setWFWorkOrderView");
                binding.getParamsMap().put("type", new StringBuffer("O"));
                binding.execute();
                setFacetValue("Work Order");
                //facetValueRfqPage = "rfq";
                return "toWfViewPage";
        //return null;
    }

    public String InvcForOtherAction() {
        // Add event code here...
        desc = new StringBuffer("Pending with Others ");
                OperationBinding binding = this.getBindings().getOperationBinding("setWFSvcInvcView");
                binding.getParamsMap().put("type", new StringBuffer("O"));
                binding.execute();
                setFacetValue("Service Invoice");
                //facetValueRfqPage = "rfq";
                return "toWfViewPage";
        //return null;
    }

    public String ScMyUnpostedAction() {
        // Add event code here...
        desc = new StringBuffer("My Unposted Documents");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFScView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
       
        setFacetValue("Service Contract");
        facetValue = "Service Contract";
    System.out.println("-------------------------------"+facetValue);
     
        //facetValueRfqPage = "rfq";
        return "toWfViewPage";
        //return null;
    }

    public String WorkOrderMyUnpostedAction() {
        // Add event code here...
        desc = new StringBuffer("My Unposted Documents ");
                OperationBinding binding = this.getBindings().getOperationBinding("setWFWorkOrderView");
                binding.getParamsMap().put("type", new StringBuffer("U"));
                binding.execute();
                setFacetValue("Work Order");
                //facetValueRfqPage = "rfq";
                return "toWfViewPage";
        //return null;
    }

    public String InvcMyUnpostedAction() {
        // Add event code here...
        desc = new StringBuffer("My Unposted Documents");
                OperationBinding binding = this.getBindings().getOperationBinding("setWFSvcInvcView");
                binding.getParamsMap().put("type", new StringBuffer("U"));
                binding.execute();
                setFacetValue("Service Invoice");
                //facetValueRfqPage = "rfq";
                return "toWfViewPage";
        
        //return null;
    }

    public void InvcSliderVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        
        if(valueChangeEvent != null){
            BigDecimal count = (BigDecimal)valueChangeEvent.getNewValue();
            int i = count.intValue();
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNCustInvcVO");
            binding.getParamsMap().put("val", i);
            binding.execute();
        }
    }

    public void AmcSliderVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(valueChangeEvent != null){
            BigDecimal count = (BigDecimal)valueChangeEvent.getNewValue();
            int i = count.intValue();
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNCustAmcVO");
            binding.getParamsMap().put("val", i);
            binding.execute();
        }
    }

    public void TktSliderVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...        
        if(valueChangeEvent != null){
            BigDecimal count = (BigDecimal)valueChangeEvent.getNewValue();
            int i = count.intValue();
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNTktVO");
            binding.getParamsMap().put("val", i);
            binding.execute();
        }
    }

    public void defctSliderVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(valueChangeEvent != null){
            BigDecimal count = (BigDecimal)valueChangeEvent.getNewValue();
            int i = count.intValue();
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNDefectVO");
            binding.getParamsMap().put("val", i);
            binding.execute();
        }
    }
    // Code For Alert and Update Service 
      private AlertUpdateServiceBean bean=new  AlertUpdateServiceBean();
      private RichPanelFormLayout panelForm;
      private int i=0;
      public String getText(){
          if(i==0){
           this.printDetail();
            i++;  
          } 
          return "Y";
      }
      
      public void printDetail() {

       UIOutput o1=bean.getAlert("300", "275",EbizParams.GLBL_APP_CLD_ID(),EbizParams.GLBL_APP_SERV_LOC(),EbizParams.GLBL_HO_ORG_ID(),EbizParams.GLBL_APP_USR_ORG(),EbizParams.GLBL_APP_USR(),"00307");
      this.panelForm.getChildren().add(o1);
      System.out.println("addind Refresh");
      AdfFacesContext.getCurrentInstance().addPartialTarget(this.panelForm);
      AdfFacesContext.getCurrentInstance().addPartialTarget(o1);
      AdfFacesContext.getCurrentInstance().addPartialTarget(this.getAlertPgBind());
      
      
      }
      
      public void getUpdate(ClientEvent clientEvent) {
          System.out.println("I am in bean");
          bean.getUpdate(this.getPanelForm(),EbizParams.GLBL_APP_CLD_ID(),EbizParams.GLBL_APP_SERV_LOC(),EbizParams.GLBL_HO_ORG_ID(),EbizParams.GLBL_APP_USR_ORG(),EbizParams.GLBL_APP_USR(),"00307");
          AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelForm());
         
      }

      public void setPanelForm(RichPanelFormLayout panelForm) {
          this.panelForm = panelForm;
      }

      public RichPanelFormLayout getPanelForm() {
          return panelForm;
      }

//      public void setPanelGroup(RichPanelGroupLayout panelGroup) {
//          this.panelGroup = panelGroup;
//      }
//
//      public RichPanelGroupLayout getPanelGroup() {
//          return panelGroup;
//      }
//
//      public void setPanelForm(RichPanelFormLayout panelForm) {
//          this.panelForm = panelForm;
//      }
//
//      public RichPanelFormLayout getPanelForm() {
//          return panelForm;
//      }
    public void setAlertPgBind(RichPanelGroupLayout alertPgBind) {
        this.alertPgBind = alertPgBind;
    }

    public RichPanelGroupLayout getAlertPgBind() {
        return alertPgBind;
    }
}
