package itemreportapp.view.bean;

import itemreportapp.model.module.ItemReportAppAMImpl;

import itemreportapp.model.view.AppGrpVOImpl;
import itemreportapp.model.view.AppItmPrfVOImpl;
import itemreportapp.model.view.AppUomConvStdVOImpl;
import itemreportapp.model.view.TransItmPrfVOImpl;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;


import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;

import oracle.adf.view.rich.component.rich.nav.RichGoLink;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;

public class ItemReportsBean {


    private RichSelectBooleanCheckbox loiCB;
    private RichSelectBooleanCheckbox obCB;
    private RichSelectBooleanCheckbox gwloiCB;
    private RichSelectBooleanCheckbox gwobCB;
    private RichSelectBooleanCheckbox phCB;
    private RichSelectBooleanCheckbox ssitemCB;
    private RichSelectBooleanCheckbox printbarCB;
    private RichSelectBooleanCheckbox logCB;
    private RichSelectBooleanCheckbox consitemCB;
    private RichSelectBooleanCheckbox srvcitmCB;
    private RichSelectBooleanCheckbox taxitemCB;
    private RichSelectBooleanCheckbox taxfreeitemCB;
    private RichSelectBooleanCheckbox salesitemCB;
    private RichSelectBooleanCheckbox puritmCB;
    private RichSelectBooleanCheckbox wipitemsCB;
    private RichSelectBooleanCheckbox tradeitemCB;
    private RichSelectBooleanCheckbox cgCB;
    private RichSelectBooleanCheckbox cashpurCB;
    private RichSelectBooleanCheckbox stckitemCB;
    private RichSelectBooleanCheckbox rmCB;
    private RichInputText pricelimit;
    private RichInputText priceBasicLimitTo;
    private RichInputText pricePurchaseLimitTo;
    private RichInputText priceSalesLimitTo;
    private RichInputText priceBasic;
    private RichInputText pricePurchase;
    private RichInputText priceSales;
    private RichSelectOneChoice fileTypeBind;
    private RichGoLink listofitemsgo;
    private Integer count = -1;

    String stockableFlg = null;
    String consummableFlg = null;
    String srvcItmFlg = null;
    String slsItmFlg = null;
    String purItmFlg = null;
    String wipItmFlg = null;
    String capGoodFlg = null;
    String cashPurItmFlg = null;
    String actvFlg = null;
    String taxExFlg = null;
    String maxStk = null;
    String minStk = null;
    String safteyLevel = null;
    String reOrderLevel = null;
    String blackListed = null;
    String onHold = null;

    private RichSelectOneRadio stockableradio;
    private RichSelectOneRadio sreviceitemradio;
    private RichSelectOneRadio salesitemradio;
    private RichSelectOneRadio purchaseitemradio;
    private RichSelectOneRadio wipitmradio;
    private RichSelectOneRadio taxexamptedradio;
    private RichSelectOneRadio capitalgoodradio;
    private RichSelectOneRadio consumableradio;
    private RichSelectOneRadio cashpurradio;
    private RichSelectOneRadio activeradio;
    private RichSelectOneChoice selectProfile;
    private RichSelectBooleanCheckbox groupwiseListofItemsCB;
    private RichSelectBooleanCheckbox supplierwiseListofItemsCB;
    private RichSelectBooleanCheckbox barcodeCB;
    private RichSelectBooleanCheckbox listofAlternateItemsCB;
    private RichSelectBooleanCheckbox groupwiseListofAlternateItemsCB;
    private RichGoLink groupwiseListofItemsGO;
    private RichGoLink supplierwiseListofItemsGO;
    private RichGoLink barcodeGO;
    private RichGoLink listofAlternateItemsGO;
    private RichGoLink gwListofAltItemsGO;
    private RichSelectBooleanCheckbox listofWarehouseCB;
    private RichSelectBooleanCheckbox gwListofWarehouse;
    private RichSelectBooleanCheckbox lisstofBinsCB;
    private RichSelectBooleanCheckbox gwListofBinsCB;
    private RichGoLink listofWarehouseGO;
    private RichGoLink gwListofWarehouseGO;
    private RichGoLink listofBinsGO;
    private RichGoLink gwListofBinsGO;
    private RichPanelGroupLayout itemsProfileGroupLayout;
    private RichPanelGroupLayout warehouseProfileGroupLayout;
    private RichSelectBooleanCheckbox listofKitItemsCB;
    private RichSelectBooleanCheckbox gwListofKitItemsCB;
    private RichGoLink listofKitItemsGO;
    private RichGoLink gwListofKitItemsGO;
    private RichPanelGroupLayout kitProfileGroup;
    private RichGoLink listofGroupGO;
    private RichSelectBooleanCheckbox listofGroupCB;
    private RichPanelGroupLayout groupProfileGroupLayout;
    private RichSelectOneRadio blackListedRadio;
    private RichSelectOneRadio onHoldRadio;
    private RichGoLink listofSuppliersGO;
    private RichSelectBooleanCheckbox listofSuppliersCB;
    private RichPanelGroupLayout suppliersProfileGroupLayout;
    private RichSelectBooleanCheckbox perfCB;
    private RichGoLink perfGO;
    private RichPanelGroupLayout perfProfileGroupLayout;
    private RichSelectBooleanCheckbox uomCB;
    private RichSelectBooleanCheckbox uomcCB;
    private RichGoLink uomGO;
    private RichGoLink uomcGO;
    private RichPanelGroupLayout uomProfileGroupLayout;
    private RichPanelLabelAndMessage stkblereadonly;
    private RichPanelLabelAndMessage srvcitmreadonly;
    private RichPanelLabelAndMessage slsitmread;
    private RichPanelLabelAndMessage purchasereadonly;
    private RichPanelLabelAndMessage wipreadonly;
    private RichPanelLabelAndMessage textexemptedreadonly;
    private RichPanelLabelAndMessage capitalgdreadonly;
    private RichPanelLabelAndMessage cashPurReadOnly;
    private RichPanelLabelAndMessage consummableReadonly;
    private RichPanelLabelAndMessage actvReadonly;
    private RichPanelLabelAndMessage blackListedReadOnly;
    private RichPanelLabelAndMessage onHoldReadOnly;
    private RichSelectOneChoice itemnamereadonly;
    private RichSelectOneChoice grpNameReadOnly;
    private RichPanelGroupLayout mmProfilePanelGrouplayoutBinding;
    private RichGoLink mmProfileLinkBinding;
    private RichGoLink singleAttributeLinkBinding;
    private RichGoLink attributeWiseLinkBinding;
    private RichSelectBooleanCheckbox singleAttriCheckBinding;
    private RichSelectBooleanCheckbox attributeWiseCheckBinding;
    private RichSelectBooleanCheckbox listOfItemGroupBinding;
    private RichGoLink listofitemgroupLinkBinding;
    private RichSelectBooleanCheckbox unapprovedChkBinding;
    private RichPanelLabelAndMessage unapprovedReadOnly;
    private RichSelectBooleanCheckbox listofitempriceChkBinding;
    private RichGoLink listofitempriceLnkBinding;

    public ItemReportsBean() {
    }

    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }


    //code for generate Report button

    public void generateReportBtn(ActionEvent actionEvent) {
        ItemReportAppAMImpl am = (ItemReportAppAMImpl)resolvElDC("ItemReportAppAMDataControl");
        ViewObjectImpl transvo = am.getTransItmPrf1();

        if ((String)transvo.getCurrentRow().getAttribute("MaxStk") == "Exceeds")
            maxStk = "E";
        else if ((String)transvo.getCurrentRow().getAttribute("MaxStk") == "Not Exceeds")
            maxStk = "N";
        else if ((String)transvo.getCurrentRow().getAttribute("MaxStk") == "Do Not Check")
            maxStk = "D";

        if ((String)transvo.getCurrentRow().getAttribute("MinStk") == "Exceeds")
            minStk = "E";
        else if ((String)transvo.getCurrentRow().getAttribute("MinStk") == "Not Exceeds")
            minStk = "N";
        else if ((String)transvo.getCurrentRow().getAttribute("MinStk") == "Do Not Check")
            minStk = "D";

        if ((String)transvo.getCurrentRow().getAttribute("SafeQty") == "Exceeds")
            safteyLevel = "E";
        else if ((String)transvo.getCurrentRow().getAttribute("SafeQty") == "Not Exceeds")
            safteyLevel = "N";
        else if ((String)transvo.getCurrentRow().getAttribute("SafeQty") == "Do Not Check")
            safteyLevel = "D";

        if ((String)transvo.getCurrentRow().getAttribute("ReorderLvl") == "Exceeds")
            reOrderLevel = "E";
        else if ((String)transvo.getCurrentRow().getAttribute("ReorderLvl") == "Not Exceeds")
            reOrderLevel = "N";
        else if ((String)transvo.getCurrentRow().getAttribute("ReorderLvl") == "Do Not Check")
            reOrderLevel = "D";

        if (stockableradio.getValue().toString().equals("No"))
            stockableFlg = "N";
        else if (stockableradio.getValue().toString().equals("Yes"))
            stockableFlg = "Y";
        else
            stockableFlg = null;

        if (sreviceitemradio.getValue().equals("No"))
            srvcItmFlg = "N";
        else if (sreviceitemradio.getValue().equals("Yes"))
            srvcItmFlg = "Y";
        else
            srvcItmFlg = null;

        if (salesitemradio.getValue().equals("No"))
            slsItmFlg = "N";
        else if (salesitemradio.getValue().equals("Yes"))
            slsItmFlg = "Y";
        else
            slsItmFlg = null;

        if (purchaseitemradio.getValue().equals("No"))
            purItmFlg = "N";
        else if (purchaseitemradio.getValue().equals("Yes"))
            purItmFlg = "Y";
        else
            purItmFlg = null;

        if (wipitmradio.getValue().equals("No"))
            wipItmFlg = "N";
        else if (wipitmradio.getValue().equals("Yes"))
            wipItmFlg = "Y";
        else
            wipItmFlg = null;

        if (capitalgoodradio.getValue().equals("No"))
            capGoodFlg = "N";
        else if (capitalgoodradio.getValue().equals("Yes"))
            capGoodFlg = "Y";
        else
            capGoodFlg = null;

        if (cashpurradio.getValue().equals("No"))
            cashPurItmFlg = "N";
        else if (cashpurradio.getValue().equals("Yes"))
            cashPurItmFlg = "Y";
        else
            cashPurItmFlg = null;

        if (consumableradio.getValue().equals("No"))
            consummableFlg = "N";
        else if (consumableradio.getValue().equals("Yes"))
            consummableFlg = "Y";
        else
            consummableFlg = null;            
              
            if((activeradio.getValue().equals("Yes"))&&(unapprovedChkBinding.getValue().equals(true)))
              actvFlg="U";
            else  if((activeradio.getValue().equals("No"))&&(unapprovedChkBinding.getValue().equals(true)))
             actvFlg="U";
        else  if (activeradio.getValue().equals("No"))
            actvFlg = "N";
        else if (activeradio.getValue().equals("Yes"))
            actvFlg = "Y";
        else if(unapprovedChkBinding.getValue()!=null){
                if(unapprovedChkBinding.getValue().equals(true)){
                          actvFlg="U";
                }else{
                    actvFlg=null;
                  }
            } 
        else  
            actvFlg = null; 
            

        if (taxexamptedradio.getValue().equals("No"))
            taxExFlg = "N";
        else if (taxexamptedradio.getValue().equals("Yes"))
            taxExFlg = "Y";
        else
            taxExFlg = null;

        if (blackListedRadio.getValue().equals("No"))
            blackListed = "N";
        else if (blackListedRadio.getValue().equals("Yes"))
            blackListed = "Y";
        else
            blackListed = null;

        if (onHoldRadio.getValue().equals("No"))
            onHold = "N";
        else if (onHoldRadio.getValue().equals("Yes"))
            onHold = "Y";
        else
            onHold = null;

        //items Profile
        if (loiCB.getValue().toString() == "true")
            listofitemsgo.setVisible(true);
        else
            listofitemsgo.setVisible(false);


        if (groupwiseListofItemsCB.getValue().toString() == "true")
            groupwiseListofItemsGO.setVisible(true);
        else
            groupwiseListofItemsGO.setVisible(false);

        if (supplierwiseListofItemsCB.getValue().toString() == "true") {
                supplierwiseListofItemsGO.setVisible(true);
        }
        else
            supplierwiseListofItemsGO.setVisible(false);

        if (barcodeCB.getValue().toString() == "true"){
            barcodeGO.setVisible(true);
        }
        else
            barcodeGO.setVisible(false);

        if (listofAlternateItemsCB.getValue().toString() == "true"){
            listofAlternateItemsGO.setVisible(true);
        }
        else
            listofAlternateItemsGO.setVisible(false);

        if (groupwiseListofAlternateItemsCB.getValue().toString() == "true"){
            gwListofAltItemsGO.setVisible(true);
        }
        else
            gwListofAltItemsGO.setVisible(false);

        if (singleAttriCheckBinding.getValue().equals(true)) {
            singleAttributeLinkBinding.setVisible(true);
        } else {
            singleAttributeLinkBinding.setVisible(false);
        }

        if (attributeWiseCheckBinding.getValue().equals(true)) {
            attributeWiseLinkBinding.setVisible(true);
        } else {
            attributeWiseLinkBinding.setVisible(false);
        }
        //warehouse profile
        if (listofWarehouseCB.getValue().toString() == "true")
            listofWarehouseGO.setVisible(true);
        else
            listofWarehouseGO.setVisible(false);

        if (gwListofWarehouse.getValue().toString() == "true")
            gwListofWarehouseGO.setVisible(true);
        else
            gwListofWarehouseGO.setVisible(false);

        if (lisstofBinsCB.getValue().toString() == "true")
            listofBinsGO.setVisible(true);
        else
            listofBinsGO.setVisible(false);

        if (gwListofBinsCB.getValue().toString() == "true")
            gwListofBinsGO.setVisible(true);
        else
            gwListofBinsGO.setVisible(false);

        //kit profile
        if (listofKitItemsCB.getValue().toString() == "true")
            listofKitItemsGO.setVisible(true);
        else
            listofKitItemsGO.setVisible(false);

        if (gwListofKitItemsCB.getValue().toString() == "true")
            gwListofKitItemsGO.setVisible(true);
        else
            gwListofKitItemsGO.setVisible(false);

        if (listofGroupCB.getValue().toString() == "true")
            listofGroupGO.setVisible(true);
        else
            listofGroupGO.setVisible(false);

        if (listofSuppliersCB.getValue().toString() == "true")
            listofSuppliersGO.setVisible(true);
        else
            listofSuppliersGO.setVisible(false);

        if (perfCB.getValue().toString() == "true")
            perfGO.setVisible(true);
        else
            perfGO.setVisible(false);

        if (uomCB.getValue().toString() == "true")
            uomGO.setVisible(true);
        else
            uomGO.setVisible(false);

        if (uomcCB.getValue().toString() == "true")
            uomcGO.setVisible(true);
        else
            uomcGO.setVisible(false);
        
        if(listOfItemGroupBinding.getValue().equals(true)){
            listofitemgroupLinkBinding.setVisible(true);
        }
        else{
            listofitemgroupLinkBinding.setVisible(false);
        }
        if(listofitempriceChkBinding.getValue().equals(true)){
            listofitempriceLnkBinding.setVisible(true);
        }else{
            listofitempriceLnkBinding.setVisible(false);
        }

    }


    //Bindings for Report CheckBox

    public void setLoiCB(RichSelectBooleanCheckbox loiCB) {
        this.loiCB = loiCB;
    }

    public RichSelectBooleanCheckbox getLoiCB() {
        return loiCB;
    }

    public void setObCB(RichSelectBooleanCheckbox obCB) {
        this.obCB = obCB;
    }

    public RichSelectBooleanCheckbox getObCB() {
        return obCB;
    }

    public void setGwloiCB(RichSelectBooleanCheckbox gwloiCB) {
        this.gwloiCB = gwloiCB;
    }

    public RichSelectBooleanCheckbox getGwloiCB() {
        return gwloiCB;
    }

    public void setGwobCB(RichSelectBooleanCheckbox gwobCB) {
        this.gwobCB = gwobCB;
    }

    public RichSelectBooleanCheckbox getGwobCB() {
        return gwobCB;
    }

    public void setPhCB(RichSelectBooleanCheckbox phCB) {
        this.phCB = phCB;
    }

    public RichSelectBooleanCheckbox getPhCB() {
        return phCB;
    }

    public void setSsitemCB(RichSelectBooleanCheckbox ssitemCB) {
        this.ssitemCB = ssitemCB;
    }

    public RichSelectBooleanCheckbox getSsitemCB() {
        return ssitemCB;
    }

    public void setPrintbarCB(RichSelectBooleanCheckbox printbarCB) {
        this.printbarCB = printbarCB;
    }

    public RichSelectBooleanCheckbox getPrintbarCB() {
        return printbarCB;
    }

    public void setLogCB(RichSelectBooleanCheckbox logCB) {
        this.logCB = logCB;
    }

    public RichSelectBooleanCheckbox getLogCB() {
        return logCB;
    }

    public void setConsitemCB(RichSelectBooleanCheckbox consitemCB) {
        this.consitemCB = consitemCB;
    }

    public RichSelectBooleanCheckbox getConsitemCB() {
        return consitemCB;
    }

    public void setSrvcitmCB(RichSelectBooleanCheckbox srvcitmCB) {
        this.srvcitmCB = srvcitmCB;
    }

    public RichSelectBooleanCheckbox getSrvcitmCB() {
        return srvcitmCB;
    }

    public void setTaxitemCB(RichSelectBooleanCheckbox taxitemCB) {
        this.taxitemCB = taxitemCB;
    }

    public RichSelectBooleanCheckbox getTaxitemCB() {
        return taxitemCB;
    }

    public void setTaxfreeitemCB(RichSelectBooleanCheckbox taxfreeitemCB) {
        this.taxfreeitemCB = taxfreeitemCB;
    }

    public RichSelectBooleanCheckbox getTaxfreeitemCB() {
        return taxfreeitemCB;
    }

    public void setSalesitemCB(RichSelectBooleanCheckbox salesitemCB) {
        this.salesitemCB = salesitemCB;
    }

    public RichSelectBooleanCheckbox getSalesitemCB() {
        return salesitemCB;
    }

    public void setPuritmCB(RichSelectBooleanCheckbox puritmCB) {
        this.puritmCB = puritmCB;
    }

    public RichSelectBooleanCheckbox getPuritmCB() {
        return puritmCB;
    }

    public void setWipitemsCB(RichSelectBooleanCheckbox wipitemsCB) {
        this.wipitemsCB = wipitemsCB;
    }

    public RichSelectBooleanCheckbox getWipitemsCB() {
        return wipitemsCB;
    }

    public void setTradeitemCB(RichSelectBooleanCheckbox tradeitemCB) {
        this.tradeitemCB = tradeitemCB;
    }

    public RichSelectBooleanCheckbox getTradeitemCB() {
        return tradeitemCB;
    }

    public void setCgCB(RichSelectBooleanCheckbox cgCB) {
        this.cgCB = cgCB;
    }

    public RichSelectBooleanCheckbox getCgCB() {
        return cgCB;
    }

    public void setCashpurCB(RichSelectBooleanCheckbox cashpurCB) {
        this.cashpurCB = cashpurCB;
    }

    public RichSelectBooleanCheckbox getCashpurCB() {
        return cashpurCB;
    }

    public void setStckitemCB(RichSelectBooleanCheckbox stckitemCB) {
        this.stckitemCB = stckitemCB;
    }

    public RichSelectBooleanCheckbox getStckitemCB() {
        return stckitemCB;
    }

    public void setRmCB(RichSelectBooleanCheckbox rmCB) {
        this.rmCB = rmCB;
    }

    public RichSelectBooleanCheckbox getRmCB() {
        return rmCB;
    }


    public void setPricelimit(RichInputText pricelimit) {
        this.pricelimit = pricelimit;
    }

    public RichInputText getPricelimit() {
        return pricelimit;
    }

    public void setPriceBasicLimitTo(RichInputText priceBasicLimitTo) {
        this.priceBasicLimitTo = priceBasicLimitTo;
    }

    public RichInputText getPriceBasicLimitTo() {
        return priceBasicLimitTo;
    }

    public void setPricePurchaseLimitTo(RichInputText pricePurchaseLimitTo) {
        this.pricePurchaseLimitTo = pricePurchaseLimitTo;
    }

    public RichInputText getPricePurchaseLimitTo() {
        return pricePurchaseLimitTo;
    }

    public void setPriceSalesLimitTo(RichInputText priceSalesLimitTo) {
        this.priceSalesLimitTo = priceSalesLimitTo;
    }

    public RichInputText getPriceSalesLimitTo() {
        return priceSalesLimitTo;
    }

    public void setPriceBasic(RichInputText priceBasic) {
        this.priceBasic = priceBasic;
    }

    public RichInputText getPriceBasic() {
        return priceBasic;
    }

    public void setPricePurchase(RichInputText pricePurchase) {
        this.pricePurchase = pricePurchase;
    }

    public RichInputText getPricePurchase() {
        return pricePurchase;
    }

    public void setPriceSales(RichInputText priceSales) {
        this.priceSales = priceSales;
    }

    public RichInputText getPriceSales() {
        return priceSales;
    }


    public void priceBasicToValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

    }

    public void pricePurcToValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

    }

    public void priceSalesToValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

    }

    public void setFileTypeBind(RichSelectOneChoice fileTypeBind) {
        this.fileTypeBind = fileTypeBind;
    }

    public RichSelectOneChoice getFileTypeBind() {
        return fileTypeBind;
    }

    public void priceBasicValid(FacesContext facesContext, UIComponent uIComponent, Object object) {

    }

    public void pricePurchValid(FacesContext facesContext, UIComponent uIComponent, Object object) {

    }

    public void priceSlsValid(FacesContext facesContext, UIComponent uIComponent, Object object) {

    }

    public void priceBasicChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
        } else
            priceBasic.setValue(0);
    }

    public void priceBasicToChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
        } else
            priceBasicLimitTo.setValue(0);
    }

    public void pricePurChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
        } else
            pricePurchase.setValue(0);
    }

    public void pricePurToChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
        } else
            pricePurchaseLimitTo.setValue(0);
    }

    public void priceSlsChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
        } else
            priceSales.setValue(0);
    }

    public void priceSlsToChange(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
        } else
            priceSalesLimitTo.setValue(0);
    }

    public void setListofitemsgo(RichGoLink listofitemsgo) {
        this.listofitemsgo = listofitemsgo;
    }

    public RichGoLink getListofitemsgo() {
        return listofitemsgo;
    }

    public void setStockableFlg(String stockableFlg) {
        this.stockableFlg = stockableFlg;
    }

    public String getStockableFlg() {
        return stockableFlg;
    }

    public void submitAction(ActionEvent actionEvent) {

    }

    public void submit(ActionEvent actionEvent) {

    }

    public void itemIdChangeListener(ValueChangeEvent valueChangeEvent) {


    }

    public void setConsummableFlg(String consummableFlg) {
        this.consummableFlg = consummableFlg;
    }

    public String getConsummableFlg() {
        return consummableFlg;
    }

    public void setSrvcItmFlg(String srvcItmFlg) {
        this.srvcItmFlg = srvcItmFlg;
    }

    public String getSrvcItmFlg() {
        return srvcItmFlg;
    }

    public void setSlsItmFlg(String slsItmFlg) {
        this.slsItmFlg = slsItmFlg;
    }

    public String getSlsItmFlg() {
        return slsItmFlg;
    }

    public void setPurItmFlg(String purItmFlg) {
        this.purItmFlg = purItmFlg;
    }

    public String getPurItmFlg() {
        return purItmFlg;
    }

    public void setWipItmFlg(String wipItmFlg) {
        this.wipItmFlg = wipItmFlg;
    }

    public String getWipItmFlg() {
        return wipItmFlg;
    }

    public void setCapGoodFlg(String capGoodFlg) {
        this.capGoodFlg = capGoodFlg;
    }

    public String getCapGoodFlg() {
        return capGoodFlg;
    }

    public void setCashPurItmFlg(String cashPurItmFlg) {
        this.cashPurItmFlg = cashPurItmFlg;
    }

    public String getCashPurItmFlg() {
        return cashPurItmFlg;
    }

    public void setStockableradio(RichSelectOneRadio stockableradio) {
        this.stockableradio = stockableradio;
    }

    public RichSelectOneRadio getStockableradio() {
        return stockableradio;
    }

    public void setSreviceitemradio(RichSelectOneRadio sreviceitemradio) {
        this.sreviceitemradio = sreviceitemradio;
    }

    public RichSelectOneRadio getSreviceitemradio() {
        return sreviceitemradio;
    }

    public void setSalesitemradio(RichSelectOneRadio salesitemradio) {
        this.salesitemradio = salesitemradio;
    }

    public RichSelectOneRadio getSalesitemradio() {
        return salesitemradio;
    }

    public void setPurchaseitemradio(RichSelectOneRadio purchaseitemradio) {
        this.purchaseitemradio = purchaseitemradio;
    }

    public RichSelectOneRadio getPurchaseitemradio() {
        return purchaseitemradio;
    }

    public void setWipitmradio(RichSelectOneRadio wipitmradio) {
        this.wipitmradio = wipitmradio;
    }

    public RichSelectOneRadio getWipitmradio() {
        return wipitmradio;
    }

    public void setTaxexamptedradio(RichSelectOneRadio taxexamptedradio) {
        this.taxexamptedradio = taxexamptedradio;
    }

    public RichSelectOneRadio getTaxexamptedradio() {
        return taxexamptedradio;
    }


    public void setCapitalgoodradio(RichSelectOneRadio capitalgoodradio) {
        this.capitalgoodradio = capitalgoodradio;
    }

    public RichSelectOneRadio getCapitalgoodradio() {
        return capitalgoodradio;
    }

    public void setConsumableradio(RichSelectOneRadio consumableradio) {
        this.consumableradio = consumableradio;
    }

    public RichSelectOneRadio getConsumableradio() {
        return consumableradio;
    }

    public void setCashpurradio(RichSelectOneRadio cashpurradio) {
        this.cashpurradio = cashpurradio;
    }

    public RichSelectOneRadio getCashpurradio() {
        return cashpurradio;
    }

    public void setActiveradio(RichSelectOneRadio activeradio) {
        this.activeradio = activeradio;
    }

    public RichSelectOneRadio getActiveradio() {
        return activeradio;
    }

    public void setActvFlg(String actvFlg) {
        this.actvFlg = actvFlg;
    }

    public String getActvFlg() {
        return actvFlg;
    }

    public void setTaxExFlg(String taxExFlg) {
        this.taxExFlg = taxExFlg;
    }

    public String getTaxExFlg() {
        return taxExFlg;
    }

    public void setMaxStk(String maxStk) {
        this.maxStk = maxStk;
    }

    public String getMaxStk() {
        return maxStk;
    }

    public void setMinStk(String minStk) {
        this.minStk = minStk;
    }

    public String getMinStk() {
        return minStk;
    }

    public void setSafteyLevel(String safteyLevel) {
        this.safteyLevel = safteyLevel;
    }

    public String getSafteyLevel() {
        return safteyLevel;
    }

    public void setReOrderLevel(String reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
    }

    public String getReOrderLevel() {
        return reOrderLevel;
    }

    public void setSelectProfile(RichSelectOneChoice selectProfile) {
        this.selectProfile = selectProfile;
    }

    public RichSelectOneChoice getSelectProfile() {
        return selectProfile;
    }

    public void setGroupwiseListofItemsCB(RichSelectBooleanCheckbox groupwiseListofItemsCB) {
        this.groupwiseListofItemsCB = groupwiseListofItemsCB;
    }

    public RichSelectBooleanCheckbox getGroupwiseListofItemsCB() {
        return groupwiseListofItemsCB;
    }

    public void setSupplierwiseListofItemsCB(RichSelectBooleanCheckbox supplierwiseListofItemsCB) {
        this.supplierwiseListofItemsCB = supplierwiseListofItemsCB;
    }

    public RichSelectBooleanCheckbox getSupplierwiseListofItemsCB() {
        return supplierwiseListofItemsCB;
    }

    public void setBarcodeCB(RichSelectBooleanCheckbox barcodeCB) {
        this.barcodeCB = barcodeCB;
    }

    public RichSelectBooleanCheckbox getBarcodeCB() {
        return barcodeCB;
    }

    public void setListofAlternateItemsCB(RichSelectBooleanCheckbox listofAlternateItemsCB) {
        this.listofAlternateItemsCB = listofAlternateItemsCB;
    }

    public RichSelectBooleanCheckbox getListofAlternateItemsCB() {
        return listofAlternateItemsCB;
    }

    public void setGroupwiseListofAlternateItemsCB(RichSelectBooleanCheckbox groupwiseListofAlternateItemsCB) {
        this.groupwiseListofAlternateItemsCB = groupwiseListofAlternateItemsCB;
    }

    public RichSelectBooleanCheckbox getGroupwiseListofAlternateItemsCB() {
        return groupwiseListofAlternateItemsCB;
    }

    public void setGroupwiseListofItemsGO(RichGoLink groupwiseListofItemsGO) {
        this.groupwiseListofItemsGO = groupwiseListofItemsGO;
    }

    public RichGoLink getGroupwiseListofItemsGO() {
        return groupwiseListofItemsGO;
    }

    public void setSupplierwiseListofItemsGO(RichGoLink supplierwiseListofItemsGO) {
        this.supplierwiseListofItemsGO = supplierwiseListofItemsGO;
    }

    public RichGoLink getSupplierwiseListofItemsGO() {
        return supplierwiseListofItemsGO;
    }

    public void setBarcodeGO(RichGoLink barcodeGO) {
        this.barcodeGO = barcodeGO;
    }

    public RichGoLink getBarcodeGO() {
        return barcodeGO;
    }

    public void setListofAlternateItemsGO(RichGoLink listofAlternateItemsGO) {
        this.listofAlternateItemsGO = listofAlternateItemsGO;
    }

    public RichGoLink getListofAlternateItemsGO() {
        return listofAlternateItemsGO;
    }

    public void setGwListofAltItemsGO(RichGoLink gwListofAltItemsGO) {
        this.gwListofAltItemsGO = gwListofAltItemsGO;
    }

    public RichGoLink getGwListofAltItemsGO() {
        return gwListofAltItemsGO;
    }

    public void setListofWarehouseCB(RichSelectBooleanCheckbox listofWarehouseCB) {
        this.listofWarehouseCB = listofWarehouseCB;
    }

    public RichSelectBooleanCheckbox getListofWarehouseCB() {
        return listofWarehouseCB;
    }

    public void setGwListofWarehouse(RichSelectBooleanCheckbox gwListofWarehouse) {
        this.gwListofWarehouse = gwListofWarehouse;
    }

    public RichSelectBooleanCheckbox getGwListofWarehouse() {
        return gwListofWarehouse;
    }

    public void setLisstofBinsCB(RichSelectBooleanCheckbox lisstofBinsCB) {
        this.lisstofBinsCB = lisstofBinsCB;
    }

    public RichSelectBooleanCheckbox getLisstofBinsCB() {
        return lisstofBinsCB;
    }

    public void setGwListofBinsCB(RichSelectBooleanCheckbox gwListofBinsCB) {
        this.gwListofBinsCB = gwListofBinsCB;
    }

    public RichSelectBooleanCheckbox getGwListofBinsCB() {
        return gwListofBinsCB;
    }

    public void setListofWarehouseGO(RichGoLink listofWarehouseGO) {
        this.listofWarehouseGO = listofWarehouseGO;
    }

    public RichGoLink getListofWarehouseGO() {
        return listofWarehouseGO;
    }

    public void setGwListofWarehouseGO(RichGoLink gwListofWarehouseGO) {
        this.gwListofWarehouseGO = gwListofWarehouseGO;
    }

    public RichGoLink getGwListofWarehouseGO() {
        return gwListofWarehouseGO;
    }

    public void setListofBinsGO(RichGoLink listofBinsGO) {
        this.listofBinsGO = listofBinsGO;
    }

    public RichGoLink getListofBinsGO() {
        return listofBinsGO;
    }

    public void setGwListofBinsGO(RichGoLink gwListofBinsGO) {
        this.gwListofBinsGO = gwListofBinsGO;
    }

    public RichGoLink getGwListofBinsGO() {
        return gwListofBinsGO;
    }

    public void selectValueChangeListener(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent.getNewValue().equals("Item Profile")) {
            itemsProfileGroupLayout.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemsProfileGroupLayout);
        } else {
            itemsProfileGroupLayout.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemsProfileGroupLayout);
        }

        if (valueChangeEvent.getNewValue().equals("Warehouse Profile")) {
            warehouseProfileGroupLayout.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(warehouseProfileGroupLayout);
        } else {
            warehouseProfileGroupLayout.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(warehouseProfileGroupLayout);
        }

        if (valueChangeEvent.getNewValue().equals("Kit Profile")) {
            kitProfileGroup.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(kitProfileGroup);
        } else {
            kitProfileGroup.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(kitProfileGroup);
        }

        if (valueChangeEvent.getNewValue().equals("Group Profile")) {
            groupProfileGroupLayout.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(groupProfileGroupLayout);
        } else {
            groupProfileGroupLayout.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(groupProfileGroupLayout);
        }

        if (valueChangeEvent.getNewValue().equals("Supplier Profile")) {
            suppliersProfileGroupLayout.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(suppliersProfileGroupLayout);
        } else {
            suppliersProfileGroupLayout.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(suppliersProfileGroupLayout);
        }

        if (valueChangeEvent.getNewValue().equals("Performance Profile")) {
            perfProfileGroupLayout.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(perfProfileGroupLayout);
        } else {
            perfProfileGroupLayout.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(perfProfileGroupLayout);
        }

        if (valueChangeEvent.getNewValue().equals("UOM Profile")) {
            uomProfileGroupLayout.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(uomProfileGroupLayout);
        } else {
            uomProfileGroupLayout.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(uomProfileGroupLayout);
        }

        if (valueChangeEvent.getNewValue().equals("MM Profile")) {
            mmProfilePanelGrouplayoutBinding.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(mmProfilePanelGrouplayoutBinding);
        } else {
            mmProfilePanelGrouplayoutBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(mmProfilePanelGrouplayoutBinding);
        }
    }

    public void setItemsProfileGroupLayout(RichPanelGroupLayout itemsProfileGroupLayout) {
        this.itemsProfileGroupLayout = itemsProfileGroupLayout;
    }

    public RichPanelGroupLayout getItemsProfileGroupLayout() {
        return itemsProfileGroupLayout;
    }

    public void setWarehouseProfileGroupLayout(RichPanelGroupLayout warehouseProfileGroupLayout) {
        this.warehouseProfileGroupLayout = warehouseProfileGroupLayout;
    }

    public RichPanelGroupLayout getWarehouseProfileGroupLayout() {
        return warehouseProfileGroupLayout;
    }

    public void setListofKitItemsCB(RichSelectBooleanCheckbox listofKitItemsCB) {
        this.listofKitItemsCB = listofKitItemsCB;
    }

    public RichSelectBooleanCheckbox getListofKitItemsCB() {
        return listofKitItemsCB;
    }

    public void setGwListofKitItemsCB(RichSelectBooleanCheckbox gwListofKitItemsCB) {
        this.gwListofKitItemsCB = gwListofKitItemsCB;
    }

    public RichSelectBooleanCheckbox getGwListofKitItemsCB() {
        return gwListofKitItemsCB;
    }

    public void setListofKitItemsGO(RichGoLink listofKitItemsGO) {
        this.listofKitItemsGO = listofKitItemsGO;
    }

    public RichGoLink getListofKitItemsGO() {
        return listofKitItemsGO;
    }

    public void setGwListofKitItemsGO(RichGoLink gwListofKitItemsGO) {
        this.gwListofKitItemsGO = gwListofKitItemsGO;
    }

    public RichGoLink getGwListofKitItemsGO() {
        return gwListofKitItemsGO;
    }

    public void setKitProfileGroup(RichPanelGroupLayout kitProfileGroup) {
        this.kitProfileGroup = kitProfileGroup;
    }

    public RichPanelGroupLayout getKitProfileGroup() {
        return kitProfileGroup;
    }

    public void setListofGroupGO(RichGoLink listofGroupGO) {
        this.listofGroupGO = listofGroupGO;
    }

    public RichGoLink getListofGroupGO() {
        return listofGroupGO;
    }

    public void setListofGroupCB(RichSelectBooleanCheckbox listofGroupCB) {
        this.listofGroupCB = listofGroupCB;
    }

    public RichSelectBooleanCheckbox getListofGroupCB() {
        return listofGroupCB;
    }

    public void setGroupProfileGroupLayout(RichPanelGroupLayout groupProfileGroupLayout) {
        this.groupProfileGroupLayout = groupProfileGroupLayout;
    }

    public RichPanelGroupLayout getGroupProfileGroupLayout() {
        return groupProfileGroupLayout;
    }

    public void setBlackListedRadio(RichSelectOneRadio blackListedRadio) {
        this.blackListedRadio = blackListedRadio;
    }

    public RichSelectOneRadio getBlackListedRadio() {
        return blackListedRadio;
    }

    public void setOnHoldRadio(RichSelectOneRadio onHoldRadio) {
        this.onHoldRadio = onHoldRadio;
    }

    public RichSelectOneRadio getOnHoldRadio() {
        return onHoldRadio;
    }

    public void setListofSuppliersGO(RichGoLink listofSuppliersGO) {
        this.listofSuppliersGO = listofSuppliersGO;
    }

    public RichGoLink getListofSuppliersGO() {
        return listofSuppliersGO;
    }

    public void setListofSuppliersCB(RichSelectBooleanCheckbox listofSuppliersCB) {
        this.listofSuppliersCB = listofSuppliersCB;
    }

    public RichSelectBooleanCheckbox getListofSuppliersCB() {
        return listofSuppliersCB;
    }

    public void setBlackListed(String blackListed) {
        this.blackListed = blackListed;
    }

    public String getBlackListed() {
        return blackListed;
    }

    public void setOnHold(String onHold) {
        this.onHold = onHold;
    }

    public String getOnHold() {
        return onHold;
    }

    public void setSuppliersProfileGroupLayout(RichPanelGroupLayout suppliersProfileGroupLayout) {
        this.suppliersProfileGroupLayout = suppliersProfileGroupLayout;
    }

    public RichPanelGroupLayout getSuppliersProfileGroupLayout() {
        return suppliersProfileGroupLayout;
    }

    public void setPerfCB(RichSelectBooleanCheckbox perfCB) {
        this.perfCB = perfCB;
    }

    public RichSelectBooleanCheckbox getPerfCB() {
        return perfCB;
    }

    public void setPerfGO(RichGoLink perfGO) {
        this.perfGO = perfGO;
    }

    public RichGoLink getPerfGO() {
        return perfGO;
    }

    public void setPerfProfileGroupLayout(RichPanelGroupLayout perfProfileGroupLayout) {
        this.perfProfileGroupLayout = perfProfileGroupLayout;
    }

    public RichPanelGroupLayout getPerfProfileGroupLayout() {
        return perfProfileGroupLayout;
    }

    public void setUomCB(RichSelectBooleanCheckbox uomCB) {
        this.uomCB = uomCB;
    }

    public RichSelectBooleanCheckbox getUomCB() {
        return uomCB;
    }

    public void setUomcCB(RichSelectBooleanCheckbox uomcCB) {
        this.uomcCB = uomcCB;
    }

    public RichSelectBooleanCheckbox getUomcCB() {
        return uomcCB;
    }

    public void setUomGO(RichGoLink uomGO) {
        this.uomGO = uomGO;
    }

    public RichGoLink getUomGO() {
        return uomGO;
    }

    public void setUomcGO(RichGoLink uomcGO) {
        this.uomcGO = uomcGO;
    }

    public RichGoLink getUomcGO() {
        return uomcGO;
    }

    public void setUomProfileGroupLayout(RichPanelGroupLayout uomProfileGroupLayout) {
        this.uomProfileGroupLayout = uomProfileGroupLayout;
    }

    public RichPanelGroupLayout getUomProfileGroupLayout() {
        return uomProfileGroupLayout;
    }


    public void setStkblereadonly(RichPanelLabelAndMessage stkblereadonly) {
        this.stkblereadonly = stkblereadonly;
    }

    public RichPanelLabelAndMessage getStkblereadonly() {
        return stkblereadonly;
    }


    public void setSrvcitmreadonly(RichPanelLabelAndMessage srvcitmreadonly) {
        this.srvcitmreadonly = srvcitmreadonly;
    }

    public RichPanelLabelAndMessage getSrvcitmreadonly() {
        return srvcitmreadonly;
    }


    public void setSlsitmread(RichPanelLabelAndMessage slsitmread) {
        this.slsitmread = slsitmread;
    }

    public RichPanelLabelAndMessage getSlsitmread() {
        return slsitmread;
    }


    public void setPurchasereadonly(RichPanelLabelAndMessage purchasereadonly) {
        this.purchasereadonly = purchasereadonly;
    }

    public RichPanelLabelAndMessage getPurchasereadonly() {
        return purchasereadonly;
    }


    public void setWipreadonly(RichPanelLabelAndMessage wipreadonly) {
        this.wipreadonly = wipreadonly;
    }

    public RichPanelLabelAndMessage getWipreadonly() {
        return wipreadonly;
    }


    public void setTextexemptedreadonly(RichPanelLabelAndMessage textexemptedreadonly) {
        this.textexemptedreadonly = textexemptedreadonly;
    }

    public RichPanelLabelAndMessage getTextexemptedreadonly() {
        return textexemptedreadonly;
    }


    public void setCapitalgdreadonly(RichPanelLabelAndMessage capitalgdreadonly) {
        this.capitalgdreadonly = capitalgdreadonly;
    }

    public RichPanelLabelAndMessage getCapitalgdreadonly() {
        return capitalgdreadonly;
    }


    public void setCashPurReadOnly(RichPanelLabelAndMessage cashPurReadOnly) {
        this.cashPurReadOnly = cashPurReadOnly;
    }

    public RichPanelLabelAndMessage getCashPurReadOnly() {
        return cashPurReadOnly;
    }


    public void setConsummableReadonly(RichPanelLabelAndMessage consummableReadonly) {
        this.consummableReadonly = consummableReadonly;
    }

    public RichPanelLabelAndMessage getConsummableReadonly() {
        return consummableReadonly;
    }


    public void setActvReadonly(RichPanelLabelAndMessage actvReadonly) {
        this.actvReadonly = actvReadonly;
    }

    public RichPanelLabelAndMessage getActvReadonly() {
        return actvReadonly;
    }


    public void setBlackListedReadOnly(RichPanelLabelAndMessage blackListedReadOnly) {
        this.blackListedReadOnly = blackListedReadOnly;
    }

    public RichPanelLabelAndMessage getBlackListedReadOnly() {
        return blackListedReadOnly;
    }

    public void setOnHoldReadOnly(RichPanelLabelAndMessage onHoldReadOnly) {
        this.onHoldReadOnly = onHoldReadOnly;
    }

    public RichPanelLabelAndMessage getOnHoldReadOnly() {
        return onHoldReadOnly;
    }

    public void itemNameChangeList(ValueChangeEvent valueChangeEvent) {
                listofitemsgo.setVisible(false);
                groupwiseListofItemsGO.setVisible(false);
                supplierwiseListofItemsGO.setVisible(false);
                barcodeGO.setVisible(false);
                listofAlternateItemsGO.setVisible(false);
                gwListofAltItemsGO.setVisible(false);
                singleAttributeLinkBinding.setVisible(false); 
                attributeWiseLinkBinding.setVisible(false);
                listofitempriceLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
                AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);

    }

    public void groupNameChangelist(ValueChangeEvent valueChangeEvent) {
        listofitemsgo.setVisible(false);
                groupwiseListofItemsGO.setVisible(false);
                supplierwiseListofItemsGO.setVisible(false);
                barcodeGO.setVisible(false);
                listofAlternateItemsGO.setVisible(false);
                gwListofAltItemsGO.setVisible(false);
                singleAttributeLinkBinding.setVisible(false); 
                attributeWiseLinkBinding.setVisible(false);
                listofitempriceLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
                AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
        }

    public void showLegacyChangeList(ValueChangeEvent valueChangeEvent) {
        listofitemsgo.setVisible(false);
                groupwiseListofItemsGO.setVisible(false);
                supplierwiseListofItemsGO.setVisible(false);
                barcodeGO.setVisible(false);
                listofAlternateItemsGO.setVisible(false);
                gwListofAltItemsGO.setVisible(false);
                singleAttributeLinkBinding.setVisible(false); 
                attributeWiseLinkBinding.setVisible(false);
                listofitempriceLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
                AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
    }

    public void showTechChangeListener(ValueChangeEvent valueChangeEvent) {
        listofitemsgo.setVisible(false);
                groupwiseListofItemsGO.setVisible(false);
                supplierwiseListofItemsGO.setVisible(false);
                barcodeGO.setVisible(false);
                listofAlternateItemsGO.setVisible(false);
                gwListofAltItemsGO.setVisible(false);
                singleAttributeLinkBinding.setVisible(false); 
                attributeWiseLinkBinding.setVisible(false);
                listofitempriceLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
                AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
    }

    public void capitalgoodchangelistener(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(capitalgdreadonly);
        listofitemsgo.setVisible(false);
                groupwiseListofItemsGO.setVisible(false);
                supplierwiseListofItemsGO.setVisible(false);
                barcodeGO.setVisible(false);
                listofAlternateItemsGO.setVisible(false);
                gwListofAltItemsGO.setVisible(false);
                singleAttributeLinkBinding.setVisible(false); 
                attributeWiseLinkBinding.setVisible(false);
                listofitempriceLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
                AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
    }

    public void slschange(ValueChangeEvent valueChangeEvent) {
        listofitemsgo.setVisible(false);
                groupwiseListofItemsGO.setVisible(false);
                supplierwiseListofItemsGO.setVisible(false);
                barcodeGO.setVisible(false);
                listofAlternateItemsGO.setVisible(false);
                gwListofAltItemsGO.setVisible(false);
                singleAttributeLinkBinding.setVisible(false); 
                attributeWiseLinkBinding.setVisible(false);
                listofitempriceLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
                AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
    }

    public void wipChangelist(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(wipreadonly);
        listofitemsgo.setVisible(false);
                groupwiseListofItemsGO.setVisible(false);
                supplierwiseListofItemsGO.setVisible(false);
                barcodeGO.setVisible(false);
                listofAlternateItemsGO.setVisible(false);
                gwListofAltItemsGO.setVisible(false);
                singleAttributeLinkBinding.setVisible(false); 
                attributeWiseLinkBinding.setVisible(false);
                listofitempriceLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
                AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
    }


    public void consummablechangelist(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(consummableReadonly);
        listofitemsgo.setVisible(false);
                groupwiseListofItemsGO.setVisible(false);
                supplierwiseListofItemsGO.setVisible(false);
                barcodeGO.setVisible(false);
                listofAlternateItemsGO.setVisible(false);
                gwListofAltItemsGO.setVisible(false);
                singleAttributeLinkBinding.setVisible(false); 
                attributeWiseLinkBinding.setVisible(false);
                listofitempriceLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
                AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
    }

    public void stkbleflgchange(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(stkblereadonly);
        listofitemsgo.setVisible(false);
                groupwiseListofItemsGO.setVisible(false);
                supplierwiseListofItemsGO.setVisible(false);
                barcodeGO.setVisible(false);
                listofAlternateItemsGO.setVisible(false);
                gwListofAltItemsGO.setVisible(false);
                singleAttributeLinkBinding.setVisible(false); 
                attributeWiseLinkBinding.setVisible(false);
                listofitempriceLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
                AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
    }

    public void purchasechangelist(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(purchasereadonly);
        listofitemsgo.setVisible(false);
                groupwiseListofItemsGO.setVisible(false);
                supplierwiseListofItemsGO.setVisible(false);
                barcodeGO.setVisible(false);
                listofAlternateItemsGO.setVisible(false);
                gwListofAltItemsGO.setVisible(false);
                singleAttributeLinkBinding.setVisible(false); 
                attributeWiseLinkBinding.setVisible(false);
                listofitempriceLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
                AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
    }

    public void cashPurChangeList(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(cashPurReadOnly);
        listofitemsgo.setVisible(false);
                groupwiseListofItemsGO.setVisible(false);
                supplierwiseListofItemsGO.setVisible(false);
                barcodeGO.setVisible(false);
                listofAlternateItemsGO.setVisible(false);
                gwListofAltItemsGO.setVisible(false);
                singleAttributeLinkBinding.setVisible(false); 
                attributeWiseLinkBinding.setVisible(false);
                listofitempriceLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
                AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
    }

    public void serviceitmvaluechange(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(srvcitmreadonly);
        listofitemsgo.setVisible(false);
                groupwiseListofItemsGO.setVisible(false);
                supplierwiseListofItemsGO.setVisible(false);
                barcodeGO.setVisible(false);
                listofAlternateItemsGO.setVisible(false);
                gwListofAltItemsGO.setVisible(false);
                singleAttributeLinkBinding.setVisible(false); 
                attributeWiseLinkBinding.setVisible(false);
                listofitempriceLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
                AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
    }

    public void maxStkChangeList(ValueChangeEvent valueChangeEvent) {
        listofitemsgo.setVisible(false);
                groupwiseListofItemsGO.setVisible(false);
                supplierwiseListofItemsGO.setVisible(false);
                barcodeGO.setVisible(false);
                listofAlternateItemsGO.setVisible(false);
                gwListofAltItemsGO.setVisible(false);
                singleAttributeLinkBinding.setVisible(false); 
                attributeWiseLinkBinding.setVisible(false);
                listofitempriceLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
                AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
                // Add event code here...
    }

    public void taxexampchangeListener(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(textexemptedreadonly);
        listofitemsgo.setVisible(false);
                groupwiseListofItemsGO.setVisible(false);
                supplierwiseListofItemsGO.setVisible(false);
                barcodeGO.setVisible(false);
                listofAlternateItemsGO.setVisible(false);
                gwListofAltItemsGO.setVisible(false);
                singleAttributeLinkBinding.setVisible(false); 
                attributeWiseLinkBinding.setVisible(false);
                listofitempriceLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
                AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
    }

    public void minStkChangeList(ValueChangeEvent valueChangeEvent) {
        listofitemsgo.setVisible(false);
                groupwiseListofItemsGO.setVisible(false);
                supplierwiseListofItemsGO.setVisible(false);
                barcodeGO.setVisible(false);
                listofAlternateItemsGO.setVisible(false);
                gwListofAltItemsGO.setVisible(false);
                singleAttributeLinkBinding.setVisible(false); 
                attributeWiseLinkBinding.setVisible(false);
                listofitempriceLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
                AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
                // Add event code here...
    }

    public void safeQtyChangeList(ValueChangeEvent valueChangeEvent) {
        listofitemsgo.setVisible(false);
                groupwiseListofItemsGO.setVisible(false);
                supplierwiseListofItemsGO.setVisible(false);
                barcodeGO.setVisible(false);
                listofAlternateItemsGO.setVisible(false);
                gwListofAltItemsGO.setVisible(false);
                singleAttributeLinkBinding.setVisible(false); 
                attributeWiseLinkBinding.setVisible(false);
                listofitempriceLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
                AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
                // Add event code here...
    }

    public void reorderLvlChangeList(ValueChangeEvent valueChangeEvent) {
        listofitemsgo.setVisible(false);
                groupwiseListofItemsGO.setVisible(false);
                supplierwiseListofItemsGO.setVisible(false);
                barcodeGO.setVisible(false);
                listofAlternateItemsGO.setVisible(false);
                gwListofAltItemsGO.setVisible(false);
                singleAttributeLinkBinding.setVisible(false); 
                attributeWiseLinkBinding.setVisible(false);
                listofitempriceLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
                AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
                // Add event code here...
    }

    public void UOMBasicChangelist(ValueChangeEvent valueChangeEvent) {
        listofitemsgo.setVisible(false);
                groupwiseListofItemsGO.setVisible(false);
                supplierwiseListofItemsGO.setVisible(false);
                barcodeGO.setVisible(false);
                listofAlternateItemsGO.setVisible(false);
                gwListofAltItemsGO.setVisible(false);
                singleAttributeLinkBinding.setVisible(false); 
                attributeWiseLinkBinding.setVisible(false);
                listofitempriceLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
                AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
                // Add event code here...
    }

    public void UOMPurchangeList(ValueChangeEvent valueChangeEvent) {
        listofitemsgo.setVisible(false);
                groupwiseListofItemsGO.setVisible(false);
                supplierwiseListofItemsGO.setVisible(false);
                barcodeGO.setVisible(false);
                listofAlternateItemsGO.setVisible(false);
                gwListofAltItemsGO.setVisible(false);
                singleAttributeLinkBinding.setVisible(false); 
                attributeWiseLinkBinding.setVisible(false);
                listofitempriceLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
                AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
                // Add event code here...
    }

    public void UOMSlsChangeList(ValueChangeEvent valueChangeEvent) {
        listofitemsgo.setVisible(false);
                groupwiseListofItemsGO.setVisible(false);
                supplierwiseListofItemsGO.setVisible(false);
                barcodeGO.setVisible(false);
                listofAlternateItemsGO.setVisible(false);
                gwListofAltItemsGO.setVisible(false);
                singleAttributeLinkBinding.setVisible(false); 
                attributeWiseLinkBinding.setVisible(false);
                listofitempriceLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
                AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
                // Add event code here...
    }

    public void actvChangeList(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(actvReadonly);
        listofitemsgo.setVisible(false);
        groupwiseListofItemsGO.setVisible(false);
        supplierwiseListofItemsGO.setVisible(false);
        barcodeGO.setVisible(false);
        listofAlternateItemsGO.setVisible(false);
        gwListofAltItemsGO.setVisible(false);
        singleAttributeLinkBinding.setVisible(false); 
        attributeWiseLinkBinding.setVisible(false);
        listofitempriceLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
        AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
         AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
    }

    public void fileTypeChangeList(ValueChangeEvent valueChangeEvent) {
        listofitemsgo.setVisible(false);
        groupwiseListofItemsGO.setVisible(false);
        supplierwiseListofItemsGO.setVisible(false);
        barcodeGO.setVisible(false);
        listofAlternateItemsGO.setVisible(false);
        gwListofAltItemsGO.setVisible(false);
        singleAttributeLinkBinding.setVisible(false); 
        attributeWiseLinkBinding.setVisible(false);
        listofitempriceLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
        AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
         AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
        listofWarehouseGO.setVisible(false);
        gwListofWarehouseGO.setVisible(false);
        listofBinsGO.setVisible(false);
        gwListofBinsGO.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofWarehouseGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofWarehouseGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofBinsGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofBinsGO);

        listofKitItemsGO.setVisible(false);
        gwListofKitItemsGO.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofKitItemsGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofKitItemsGO);

        listofGroupGO.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofGroupGO);

        uomGO.setVisible(false);
        uomcGO.setVisible(false);

        listofSuppliersGO.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofSuppliersGO);

        perfGO.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(perfGO);


    }

    public void organisationValueChangeList(ValueChangeEvent valueChangeEvent) {
        listofWarehouseGO.setVisible(false);
        gwListofWarehouseGO.setVisible(false);
        listofBinsGO.setVisible(false);
        gwListofBinsGO.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofWarehouseGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofWarehouseGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofBinsGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofBinsGO);
    }

    public void warehouseValueChangeList(ValueChangeEvent valueChangeEvent) {
        listofWarehouseGO.setVisible(false);
        gwListofWarehouseGO.setVisible(false);
        listofBinsGO.setVisible(false);
        gwListofBinsGO.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofWarehouseGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofWarehouseGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofBinsGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofBinsGO);
    }

    public void KitItemChangeList(ValueChangeEvent valueChangeEvent) {
        listofKitItemsGO.setVisible(false);
        gwListofKitItemsGO.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofKitItemsGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofKitItemsGO);
    }

    public void grpProfileGrpNameChangeList(ValueChangeEvent valueChangeEvent) {
        listofGroupGO.setVisible(false);
        listofitemgroupLinkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemgroupLinkBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofGroupGO);
    }

    public void blackListedChangeList(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(blackListedReadOnly);
        listofSuppliersGO.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofSuppliersGO);
    }

    public void onHoldChangeListener(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(onHoldReadOnly);
        listofSuppliersGO.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofSuppliersGO);
    }

    public void uomClassNmValueChangeList(ValueChangeEvent valueChangeEvent) {
        uomGO.setVisible(false);
        uomcGO.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(uomGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(uomcGO);
    }

    public void paramSetNameChangeList(ValueChangeEvent valueChangeEvent) {
        perfGO.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(perfGO);

    }

    public void setItemnamereadonly(RichSelectOneChoice itemnamereadonly) {
        this.itemnamereadonly = itemnamereadonly;
    }

    public RichSelectOneChoice getItemnamereadonly() {
        return itemnamereadonly;
    }

    public void setGrpNameReadOnly(RichSelectOneChoice grpNameReadOnly) {
        this.grpNameReadOnly = grpNameReadOnly;
    }

    public RichSelectOneChoice getGrpNameReadOnly() {
        return grpNameReadOnly;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        count = (Integer)getBindings().getOperationBinding("on_load_page").execute();
        return count;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setMmProfilePanelGrouplayoutBinding(RichPanelGroupLayout mmProfilePanelGrouplayoutBinding) {
        this.mmProfilePanelGrouplayoutBinding = mmProfilePanelGrouplayoutBinding;
    }

    public RichPanelGroupLayout getMmProfilePanelGrouplayoutBinding() {
        return mmProfilePanelGrouplayoutBinding;
    }

    public void setMmProfileLinkBinding(RichGoLink mmProfileLinkBinding) {
        this.mmProfileLinkBinding = mmProfileLinkBinding;
    }

    public RichGoLink getMmProfileLinkBinding() {
        return mmProfileLinkBinding;
    }

    public void setSingleAttributeLinkBinding(RichGoLink singleAttributeLinkBinding) {
        this.singleAttributeLinkBinding = singleAttributeLinkBinding;
    }

    public RichGoLink getSingleAttributeLinkBinding() {
        return singleAttributeLinkBinding;
    }

    public void setAttributeWiseLinkBinding(RichGoLink attributeWiseLinkBinding) {
        this.attributeWiseLinkBinding = attributeWiseLinkBinding;
    }

    public RichGoLink getAttributeWiseLinkBinding() {
        return attributeWiseLinkBinding;
    }

    public void setSingleAttriCheckBinding(RichSelectBooleanCheckbox singleAttriCheckBinding) {
        this.singleAttriCheckBinding = singleAttriCheckBinding;
    }

    public RichSelectBooleanCheckbox getSingleAttriCheckBinding() {
        return singleAttriCheckBinding;
    }

    public void setAttributeWiseCheckBinding(RichSelectBooleanCheckbox attributeWiseCheckBinding) {
        this.attributeWiseCheckBinding = attributeWiseCheckBinding;
    }

    public RichSelectBooleanCheckbox getAttributeWiseCheckBinding() {
        return attributeWiseCheckBinding;
    }

    public void attributeVCL(ValueChangeEvent valueChangeEvent) {
        listofitemsgo.setVisible(false);
        groupwiseListofItemsGO.setVisible(false);
        supplierwiseListofItemsGO.setVisible(false);
        barcodeGO.setVisible(false);
        listofAlternateItemsGO.setVisible(false);
        gwListofAltItemsGO.setVisible(false);
        singleAttributeLinkBinding.setVisible(false); 
        attributeWiseLinkBinding.setVisible(false);
        listofitempriceLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
        AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
         AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
    }

    public void setListOfItemGroupBinding(RichSelectBooleanCheckbox listOfItemGroupBinding) {
        this.listOfItemGroupBinding = listOfItemGroupBinding;
    }

    public RichSelectBooleanCheckbox getListOfItemGroupBinding() {
        return listOfItemGroupBinding;
    }

    public void setListofitemgroupLinkBinding(RichGoLink listofitemgroupLinkBinding) {
        this.listofitemgroupLinkBinding = listofitemgroupLinkBinding;
    }

    public RichGoLink getListofitemgroupLinkBinding() {
        return listofitemgroupLinkBinding;
    }


    public void unapprovedChngList(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(unapprovedReadOnly);
        listofitemsgo.setVisible(false);
        groupwiseListofItemsGO.setVisible(false);
        supplierwiseListofItemsGO.setVisible(false);
        barcodeGO.setVisible(false);
        listofAlternateItemsGO.setVisible(false);
        gwListofAltItemsGO.setVisible(false);
        singleAttributeLinkBinding.setVisible(false); 
        attributeWiseLinkBinding.setVisible(false);
        listofitempriceLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofitemsgo);
        AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseListofItemsGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(supplierwiseListofItemsGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(barcodeGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofAlternateItemsGO);
        AdfFacesContext.getCurrentInstance().addPartialTarget(gwListofAltItemsGO);
         AdfFacesContext.getCurrentInstance().addPartialTarget(singleAttributeLinkBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(attributeWiseLinkBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listofitempriceLnkBinding);
    }
    public String getActiveFalse(){
           String val ="N";
               if(unapprovedChkBinding.getValue()!=null){
                   if(unapprovedChkBinding.getValue().equals(true)){
                       val="Y";
                   }
               }
           return val;
       }

    public void setUnapprovedChkBinding(RichSelectBooleanCheckbox unapprovedChkBinding) {
        this.unapprovedChkBinding = unapprovedChkBinding;
    }

    public RichSelectBooleanCheckbox getUnapprovedChkBinding() {
        return unapprovedChkBinding;
    }

    public void setUnapprovedReadOnly(RichPanelLabelAndMessage unapprovedReadOnly) {
        this.unapprovedReadOnly = unapprovedReadOnly;
    }

    public RichPanelLabelAndMessage getUnapprovedReadOnly() {
        return unapprovedReadOnly;
    }

    public void setListofitempriceChkBinding(RichSelectBooleanCheckbox listofitempriceChkBinding) {
        this.listofitempriceChkBinding = listofitempriceChkBinding;
    }

    public RichSelectBooleanCheckbox getListofitempriceChkBinding() {
        return listofitempriceChkBinding;
    }

    public void setListofitempriceLnkBinding(RichGoLink listofitempriceLnkBinding) {
        this.listofitempriceLnkBinding = listofitempriceLnkBinding;
    }

    public RichGoLink getListofitempriceLnkBinding() {
        return listofitempriceLnkBinding;
    }
}
