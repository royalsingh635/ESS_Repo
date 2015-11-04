package itemprofile.view.bean;

import itemprofile.model.services.AppModuleImpl;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Iterator;

import java.util.List;

import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectItem;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;

import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.server.ViewObjectImpl;

import oracle.ods.virtualization.engine.Entry;

public class ItemProfileBean {
    private RichSelectOneRadio stockableradio;
    private RichSelectOneRadio serviceRadioBind;
    private RichSelectOneRadio salesItmRadioBind;
    private RichSelectOneRadio purItmRadioBind;
    private RichSelectOneRadio wiPRadioBind;
    private RichSelectOneRadio taxExRadioBind;
    private RichSelectOneRadio topFileTypeTransBind;
    private RichSelectOneRadio midFileTypeTransBind;
    private RichSelectOneRadio botFileTypeTransBind;
    private Boolean chkimg = false;
    private String LOVClientId;
    private Boolean Unapproved;
    private RichSelectOneRadio filetypetransPgBind;
    private RichSelectOneRadio maxstkBind;
    private RichSelectOneRadio minStockBind;
    private RichSelectOneRadio safeStkBind;
    private RichSelectOneRadio reorderLvlBind;
    private RichSelectBooleanCheckbox downloadReptBind;


    private Boolean DownloadReptLink = false;
    private Boolean DownloadRept = false;
    private RichSelectBooleanCheckbox downloadedReptBind;
    private Boolean ListOfItemsLink = false;
    private Boolean GrpWiseListOfItemsLink = false;
    private Boolean SuppWiseListOfItemsLink = false;
    private Boolean PrintBarcodeLink = false;
    private Boolean ListOfAlternateItemsLink = false;
    private Boolean ListOfSPItemsLink = false;
    private Boolean AttWiseListOfItemsLink = false;
    private Boolean ListOfItemsPriceLink = false;
    private Boolean ListOfWarehouseLink = false;
    private Boolean ListOfBinsLink = false;
    private Boolean BinsCapacityLink = false;
    private Boolean ListOfKitLink = false;
    private Boolean ItemGroupChartLink = false;
    private Boolean ListOfItemsGroupLink = false;
    private Boolean ListOfUnitsLink = false;
    private Boolean ListOfUnitsConvLink = false;
    private Boolean ListOfSuppLink = false;
    private Boolean ListOfParaLink = false;
    private Boolean MMLink = false;
    private RichSelectBooleanCheckbox listOfItemsCBBind;
    private RichSelectBooleanCheckbox grpListOfItemsCBBind;
    private RichSelectBooleanCheckbox suppWiseListOfItemsCBBind;
    private RichSelectBooleanCheckbox barCodeCBBind;
    private RichSelectBooleanCheckbox listOfAltItemsCBBind;
    private RichSelectBooleanCheckbox listOfItemsSPCBBind;
    private RichSelectBooleanCheckbox attListOfItemsCBBind;
    private RichSelectBooleanCheckbox listOfItemsPriceCBBind;
    private RichSelectBooleanCheckbox listOfWarehouseCBBind;
    private RichSelectBooleanCheckbox listOfBinsCBBind;
    private RichSelectBooleanCheckbox binsCapacityCBBind;
    private RichSelectBooleanCheckbox listOfKitCBBind;
    private RichSelectBooleanCheckbox itemGroupChartCBBind;
    private RichSelectBooleanCheckbox listOfItemsGrpCBBind;
    private RichSelectBooleanCheckbox listOfUnitsCBBind;
    private RichSelectBooleanCheckbox listOfUnitsConvCBBind;
    private RichSelectBooleanCheckbox listOfParamCBBind;
    private RichSelectBooleanCheckbox listOfSuppCBBind;
    private RichSelectBooleanCheckbox imgCBBind;
    private RichInputListOfValues rptNameLOVBind;
    private RichSelectBooleanCheckbox orgNameBackBind;
    private RichSelectOneRadio bgColorBind;
    private RichSelectOneRadio headColorBind;
    private RichSelectItem backColorDBind;
    private RichSelectItem headColorDBind;
    private RichInputComboboxListOfValues attrValNmPageBind;
    private RichInputText attrIdPagBinding;
    List<String> b = new ArrayList<String>();
    String attrIdMulti = null;
    String attrId1 = "";
    String attrId2 = "";
    String attrId3 = "";
    String attrId4 = "";
    String attrId5 = "";
    private boolean suppEvalLink = false;
    private RichSelectBooleanCheckbox suppPrcLvlCBBind;
    private RichSelectBooleanCheckbox suppPrcLvlItemCBVCL;
    private RichSelectBooleanCheckbox suppPrcLvlItmBind;
    private RichSelectBooleanCheckbox suppDetAddsCVBind;
    private RichSelectBooleanCheckbox suppPrcPlcyCBBind;
    private RichSelectBooleanCheckbox suppPrcPlcyHistCBBind;
    private RichSelectBooleanCheckbox pricePlcyPrfBind;
    private RichSelectBooleanCheckbox pricePlcyWsListCBBind;
    private RichSelectBooleanCheckbox loItemChaptrCBBind;
    private RichSelectBooleanCheckbox excsChaptrListItmCBBind;
    private RichSelectBooleanCheckbox reqAreaCBBind;
    private boolean suppDetAddsLink = false;
    private boolean suppPricePlcyLink = false;
    private boolean suppPricePlcyHistLink = false;
    private boolean suppPriceLevelLink = false;
    private boolean suppPriceLevelItemLink = false;

    private boolean pricePlcyPrfLink = false;
    private boolean pricePlcyListLink = false;
    private boolean LOIChaptrLink = false;
    private boolean ExciseChapterLink = false;
    private boolean ReqAreaLink = false;
    private RichSelectBooleanCheckbox qcItemCBBind;
    private RichSelectBooleanCheckbox qcGroupCBBind;
    private boolean QCGroupLink = false;
    private boolean QCItemLink = false;

    public void setQCGroupLink(boolean QCGroupLink) {
        this.QCGroupLink = QCGroupLink;
    }

    public boolean isQCGroupLink() {
        return QCGroupLink;
    }

    public void setQCItemLink(boolean QCItemLink) {
        this.QCItemLink = QCItemLink;
    }

    public boolean isQCItemLink() {
        return QCItemLink;
    }

    public void setSuppDetAddsLink(boolean suppDetAddsLink) {
        this.suppDetAddsLink = suppDetAddsLink;
    }

    public boolean isSuppDetAddsLink() {
        return suppDetAddsLink;
    }

    public void setSuppPricePlcyLink(boolean suppPricePlcyLink) {
        this.suppPricePlcyLink = suppPricePlcyLink;
    }

    public boolean isSuppPricePlcyLink() {
        return suppPricePlcyLink;
    }

    public void setSuppPricePlcyHistLink(boolean suppPricePlcyHistLink) {
        this.suppPricePlcyHistLink = suppPricePlcyHistLink;
    }

    public boolean isSuppPricePlcyHistLink() {
        return suppPricePlcyHistLink;
    }

    public void setSuppPriceLevelLink(boolean suppPriceLevelLink) {
        this.suppPriceLevelLink = suppPriceLevelLink;
    }

    public boolean isSuppPriceLevelLink() {
        return suppPriceLevelLink;
    }

    public void setSuppPriceLevelItemLink(boolean suppPriceLevelItemLink) {
        this.suppPriceLevelItemLink = suppPriceLevelItemLink;
    }

    public boolean isSuppPriceLevelItemLink() {
        return suppPriceLevelItemLink;
    }

    public void setPricePlcyPrfLink(boolean pricePlcyPrfLink) {
        this.pricePlcyPrfLink = pricePlcyPrfLink;
    }

    public boolean isPricePlcyPrfLink() {
        return pricePlcyPrfLink;
    }

    public void setPricePlcyListLink(boolean pricePlcyListLink) {
        this.pricePlcyListLink = pricePlcyListLink;
    }

    public boolean isPricePlcyListLink() {
        return pricePlcyListLink;
    }

    public void setLOIChaptrLink(boolean LOIChaptrLink) {
        this.LOIChaptrLink = LOIChaptrLink;
    }

    public boolean isLOIChaptrLink() {
        return LOIChaptrLink;
    }

    public void setExciseChapterLink(boolean ExciseChapterLink) {
        this.ExciseChapterLink = ExciseChapterLink;
    }

    public boolean isExciseChapterLink() {
        return ExciseChapterLink;
    }

    public void setReqAreaLink(boolean ReqAreaLink) {
        this.ReqAreaLink = ReqAreaLink;
    }

    public boolean isReqAreaLink() {
        return ReqAreaLink;
    }

    public void setSuppEvalLink(boolean suppEvalLink) {
        this.suppEvalLink = suppEvalLink;
    }

    public boolean isSuppEvalLink() {
        return suppEvalLink;
    }
    private RichSelectBooleanCheckbox suppEvalCBBinding;
    private RichLink suppEvalLinkBinding;

    public void setAttrId1(String attrId1) {
        this.attrId1 = attrId1;
    }

    public String getAttrId1() {
        return attrId1;
    }

    public void setAttrId2(String attrId2) {
        this.attrId2 = attrId2;
    }

    public String getAttrId2() {
        return attrId2;
    }

    public void setAttrId3(String attrId3) {
        this.attrId3 = attrId3;
    }

    public String getAttrId3() {
        return attrId3;
    }

    public void setAttrId4(String attrId4) {
        this.attrId4 = attrId4;
    }

    public String getAttrId4() {
        return attrId4;
    }

    public void setAttrId5(String attrId5) {
        this.attrId5 = attrId5;
    }

    public String getAttrId5() {
        return attrId5;
    }

    public void setChkimg(Boolean chkimg) {
        this.chkimg = chkimg;
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    public Boolean getChkimg() {
        return chkimg;
    }

    public void setServItm(String ServItm) {
        this.ServItm = ServItm;
    }

    public String getServItm() {
        return ServItm;
    }

    public void setSalesItm(String SalesItm) {
        this.SalesItm = SalesItm;
    }

    public String getSalesItm() {
        return SalesItm;
    }

    public void setPurItm(String PurItm) {
        this.PurItm = PurItm;
    }

    public String getPurItm() {
        return PurItm;
    }

    public void setWipItm(String WipItm) {
        this.WipItm = WipItm;
    }

    public String getWipItm() {
        return WipItm;
    }

    public void setCapGood(String CapGood) {
        this.CapGood = CapGood;
    }

    public String getCapGood() {
        return CapGood;
    }

    public void setCashPurItm(String CashPurItm) {
        this.CashPurItm = CashPurItm;
    }

    public String getCashPurItm() {
        return CashPurItm;
    }

    public void setCons(String Cons) {
        this.Cons = Cons;
    }

    public String getCons() {
        return Cons;
    }
    private RichSelectOneRadio capGdRadioVCL;
    private RichSelectOneRadio capGdRadioBind;
    private RichSelectOneRadio cashPurItmRadioBind;
    private RichSelectOneRadio consumRadioBind;
    private RichSelectOneRadio actvRadioBind;
    private RichSelectBooleanCheckbox unappCBBind;
    private RichSelectOneRadio blackListRadioBind;
    private RichSelectOneRadio onHoldRadioBind;


    public void setStockableradio(RichSelectOneRadio stockableradio) {
        this.stockableradio = stockableradio;
    }

    public RichSelectOneRadio getStockableradio() {
        return stockableradio;
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
    private RichSelectBooleanCheckbox groupwiseListofItemsCB;
    private RichSelectBooleanCheckbox supplierwiseListofItemsCB;
    private RichSelectBooleanCheckbox barcodeCB;
    private RichSelectBooleanCheckbox listofAlternateItemsCB;
    private RichSelectBooleanCheckbox groupwiseListofAlternateItemsCB;

    public ItemProfileBean() {
    }
    String stockableFlg = null;
    String consummableFlg = null;
    String srvcItmFlg = null;
    String slsItmFlg = null;
    String purItmFlg = null;
    String wipItmFlg = null;
    String capGoodFlg = null;
    String cashPurItmFlg = null;

    public void setStockableFlg(String stockableFlg) {
        this.stockableFlg = stockableFlg;
    }

    public String getStockableFlg() {
        return stockableFlg;
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
    String actvFlg = null;
    String taxExFlg = null;
    String maxStk = null;
    String minStk = null;
    String safteyLevel = null;
    String reOrderLevel = null;
    String blackListed = null;
    String onHold = null;
    String ServItm = null;
    String SalesItm = null;
    String PurItm = null;
    String WipItm = null;

    String CapGood = null;
    String CashPurItm = null;
    String Cons = null;


    public void ItmNmPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void ItmGrpNmPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void ItmLegCodePgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void ItmTechPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void ItmImgPgVCL(ValueChangeEvent vce) {
        if (imgCBBind.isSelected()) {
            chkimg = true;
        }
    }

    public void StkPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void MaxStkPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void MinStkPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void SafeStkPgVCL(ValueChangeEvent valueChangeEvent) {

    }

    public void ReorderPgVCL(ValueChangeEvent valueChangeEvent) {

    }

    public void ServItmPgVCL(ValueChangeEvent valueChangeEvent) {

    }

    public void SaleItmPgVCL(ValueChangeEvent valueChangeEvent) {

    }

    public void PurItmPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void WIPItmPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void TaxExPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void CapGdPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void CashPurItmPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void ConsumPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void ActvPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void UOMBasicPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void UOMPurVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void UOMSalePgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void UOMClssNmPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void PrcBsPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void PricBSToPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void PrcPurPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void PricePurToPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void PrcSalesPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void PriceSlsToPgVCl(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void OrgDescPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void WhDescPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void BlkListPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void OnHoldPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void ParamSetPgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }


    public void GenerateAction(ActionEvent actionEvent) {
         if (maxstkBind.getValue().toString().equals("Exceeds"))
            maxStk = "E";
        else if (maxstkBind.getValue().toString().equals("Not Exceeds"))
            maxStk = "N";
        else if (maxstkBind.getValue().toString().equals("Do Not Check"))
            maxStk = "D";

        if (minStockBind.getValue().toString().equals("Exceeds"))
            minStk = "E";
        else if (minStockBind.getValue().toString().equals("Not Exceeds"))
            minStk = "N";
        else if (minStockBind.getValue().toString().equals("Do Not Check"))
            minStk = "D";

        if (safeStkBind.getValue().toString().equals("Exceeds"))
            safteyLevel = "E";
        else if (safeStkBind.getValue().toString().equals("Not Exceeds"))
            safteyLevel = "N";
        else if (safeStkBind.getValue().toString().equals("Do Not Check"))
            safteyLevel = "D";

        if (reorderLvlBind.getValue().toString().equals("Exceeds"))
            reOrderLevel = "E";
        else if (reorderLvlBind.getValue().toString().equals("Not Exceeds"))
            reOrderLevel = "N";
        else if (reorderLvlBind.getValue().toString().equals("Do Not Check"))
            reOrderLevel = "D";


        if (stockableradio.getValue().toString().equals("No"))
            stockableFlg = "N";
        else if (stockableradio.getValue().toString().equals("Yes"))
            stockableFlg = "Y";
        else
            stockableFlg = null;

        if (serviceRadioBind.getValue().toString().equals("No"))
            ServItm = "N";
        else if (serviceRadioBind.getValue().toString().equals("Yes"))
            ServItm = "Y";
        else
            ServItm = null;

        if (salesItmRadioBind.getValue().toString().equals("No"))
            SalesItm = "N";
        else if (salesItmRadioBind.getValue().toString().equals("Yes"))
            SalesItm = "Y";
        else
            SalesItm = null;

        if (purItmRadioBind.getValue().toString().equals("No"))
            PurItm = "N";
        else if (purItmRadioBind.getValue().toString().equals("Yes"))
            PurItm = "Y";
        else
            PurItm = null;

        if (wiPRadioBind.getValue().toString().equals("No"))
            WipItm = "N";
        else if (wiPRadioBind.getValue().toString().equals("Yes"))
            WipItm = "Y";
        else
            WipItm = null;

        if (taxExRadioBind.getValue().toString().equals("No"))
            taxExFlg = "N";
        else if (taxExRadioBind.getValue().toString().equals("Yes"))
            taxExFlg = "Y";
        else
            taxExFlg = null;

        if (capGdRadioBind.getValue().toString().equals("No"))
            CapGood = "N";
        else if (capGdRadioBind.getValue().toString().equals("Yes"))
            CapGood = "Y";
        else
            CapGood = null;

        if (cashPurItmRadioBind.getValue().toString().equals("No"))
            CashPurItm = "N";
        else if (cashPurItmRadioBind.getValue().toString().equals("Yes"))
            CashPurItm = "Y";
        else
            CashPurItm = null;

        if (consumRadioBind.getValue().toString().equals("No"))
            Cons = "N";
        else if (consumRadioBind.getValue().toString().equals("Yes"))
            Cons = "Y";
        else
            Cons = null;


        if ((actvRadioBind.getValue().equals("Yes")) && (unappCBBind.getValue().equals(true)))
            actvFlg = "U";
        else if ((actvRadioBind.getValue().equals("No")) && (unappCBBind.getValue().equals(true)))
            actvFlg = "U";
        else if (actvRadioBind.getValue().equals("No"))
            actvFlg = "N";
        else if (actvRadioBind.getValue().equals("Yes"))
            actvFlg = "Y";
        else if (unappCBBind.getValue() != null) {
            if (unappCBBind.getValue().equals(true)) {
                actvFlg = "U";
            } else {
                actvFlg = null;
            }
        } else
            actvFlg = null;

        if (blackListRadioBind.getValue().equals("No"))
            blackListed = "N";
        else if (blackListRadioBind.getValue().equals("Yes"))
            blackListed = "Y";
        else
            blackListed = null;

        if (onHoldRadioBind.getValue().equals("No"))
            onHold = "N";
        else if (onHoldRadioBind.getValue().equals("Yes"))
            onHold = "Y";
        else
            onHold = null;

        if (downloadedReptBind.isSelected()) {
            setDownloadReptLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(downloadedReptBind);
        }
        if (listOfItemsCBBind.isSelected()) {
            setListOfItemsLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(listOfItemsCBBind);
        }
        if (grpListOfItemsCBBind.isSelected()) {
            setGrpWiseListOfItemsLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(grpListOfItemsCBBind);
        }
        if (suppWiseListOfItemsCBBind.isSelected()) {
            setSuppWiseListOfItemsLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(suppWiseListOfItemsCBBind);
        }
        if (barCodeCBBind.isSelected()) {
            setPrintBarcodeLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(barCodeCBBind);
        }
        if (listOfAltItemsCBBind.isSelected()) {
            setListOfAlternateItemsLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(listOfAltItemsCBBind);
        }
        if (listOfItemsSPCBBind.isSelected()) {
            setListOfSPItemsLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(listOfItemsSPCBBind);
        }
        if (attListOfItemsCBBind.isSelected()) {
            setAttWiseListOfItemsLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(attListOfItemsCBBind);
        }
        if (listOfItemsPriceCBBind.isSelected()) {
            setListOfItemsPriceLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(listOfItemsPriceCBBind);
        }
        if (listOfWarehouseCBBind.isSelected()) {
            setListOfWarehouseLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(listOfWarehouseCBBind);
        }
        if (listOfBinsCBBind.isSelected()) {
            setListOfBinsLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(listOfBinsCBBind);
        }
        if (binsCapacityCBBind.isSelected()) {
            setBinsCapacityLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(binsCapacityCBBind);
        }
        if (listOfKitCBBind.isSelected()) {
            setListOfKitLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(listOfKitCBBind);
        }
        if (itemGroupChartCBBind.isSelected()) {
            setItemGroupChartLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemGroupChartCBBind);
        }
        if (listOfItemsGrpCBBind.isSelected()) {
            setListOfItemsGroupLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(listOfItemsGrpCBBind);
        }
        if (listOfUnitsCBBind.isSelected()) {
            setListOfUnitsLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(listOfUnitsCBBind);
        }
        if (listOfUnitsConvCBBind.isSelected()) {
            setListOfUnitsConvLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(listOfUnitsConvCBBind);
        }

        if (listOfParamCBBind.isSelected()) {
            setListOfParaLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(listOfParamCBBind);

        }

        if (listOfSuppCBBind.isSelected()) {
            ListOfSuppLink = true;
            // AdfFacesContext.getCurrentInstance().addPartialTarget(listOfSuppCBBind);
        }

        if (suppEvalCBBinding.isSelected()) {
            suppEvalLink = true;
        }

        //Store Attribute Key
        attrIdMulti = newmap.values().toString();
        attrIdMulti = attrIdMulti.substring(1, attrIdMulti.length() - 1);
        String[] attrSplit = attrIdMulti.trim().split("\\s*,\\s*");
        System.out.println("length   " + attrSplit.length);
        if (attrSplit.length > 0) {
            for (int i = 0; i < attrSplit.length; i++) {
                if (i == 0) {
                    attrId1 = attrSplit[i];
                    System.out.println("attrId1--" + attrId1);
                }
                if (i == 1) {
                    attrId2 = attrSplit[i];
                    System.out.println("attrId2--" + attrId2);

                }
                if (i == 2) {
                    attrId3 = attrSplit[i];
                    System.out.println("attrId3--" + attrId3);
                }
                if (i == 3) {
                    attrId4 = attrSplit[i];
                    System.out.println("attrId4--" + attrId4);
                }
                if (i == 4) {
                    attrId5 = attrSplit[i];
                    System.out.println("attrId5--" + attrId5);
                }
            }
        }

        if (suppDetAddsCVBind.isSelected()) {
            suppDetAddsLink = true;
        } else
            suppDetAddsLink = false;
        if (suppPrcLvlCBBind.isSelected()) {
            suppPriceLevelLink = true;
        } else
            suppPriceLevelLink = false;
        if (suppPrcLvlItmBind.isSelected()) {
            suppPriceLevelItemLink = true;
        } else
            suppPriceLevelItemLink = false;
        if (suppPrcPlcyCBBind.isSelected()) {
            suppPricePlcyLink = true;
        } else
            suppPricePlcyLink = true;
        if (suppPrcPlcyHistCBBind.isSelected()) {
            suppPricePlcyHistLink = true;
        } else
            suppPricePlcyHistLink = false;
        if (pricePlcyPrfBind.isSelected()) {
            pricePlcyPrfLink = true;
        } else
            pricePlcyPrfLink = false;
        if (pricePlcyWsListCBBind.isSelected()) {
            pricePlcyListLink = true;
        } else
            pricePlcyListLink = false;
        if (loItemChaptrCBBind.isSelected()) {
            LOIChaptrLink = true;
        } else
            LOIChaptrLink = false;
        if (excsChaptrListItmCBBind.isSelected()) {
            ExciseChapterLink = true;
        } else
            ExciseChapterLink = false;
        if (reqAreaCBBind.isSelected()) {
            ReqAreaLink = true;
        } else
            ReqAreaLink = false;
        if(qcGroupCBBind.isSelected()){
            QCGroupLink = true;
        }else QCGroupLink = false;
        if(qcItemCBBind.isSelected()){
            QCItemLink = true;
        }else QCItemLink = false;

    }

    public void setDownloadReptLink(Boolean DownloadReptLink) {
        this.DownloadReptLink = DownloadReptLink;
    }

    public Boolean getDownloadReptLink() {
        return DownloadReptLink;
    }

    public void setDownloadRept(Boolean DownloadRept) {
        this.DownloadRept = DownloadRept;
    }

    public Boolean getDownloadRept() {
        return DownloadRept;
    }

    public void setListOfItemsLink(Boolean ListOfItemsLink) {
        this.ListOfItemsLink = ListOfItemsLink;
    }

    public Boolean getListOfItemsLink() {
        return ListOfItemsLink;
    }

    public void setGrpWiseListOfItemsLink(Boolean GrpWiseListOfItemsLink) {
        this.GrpWiseListOfItemsLink = GrpWiseListOfItemsLink;
    }

    public Boolean getGrpWiseListOfItemsLink() {
        return GrpWiseListOfItemsLink;
    }

    public void setSuppWiseListOfItemsLink(Boolean SuppWiseListOfItemsLink) {
        this.SuppWiseListOfItemsLink = SuppWiseListOfItemsLink;
    }

    public Boolean getSuppWiseListOfItemsLink() {
        return SuppWiseListOfItemsLink;
    }

    public void setPrintBarcodeLink(Boolean PrintBarcodeLink) {
        this.PrintBarcodeLink = PrintBarcodeLink;
    }

    public Boolean getPrintBarcodeLink() {
        return PrintBarcodeLink;
    }

    public void setListOfAlternateItemsLink(Boolean ListOfAlternateItemsLink) {
        this.ListOfAlternateItemsLink = ListOfAlternateItemsLink;
    }

    public Boolean getListOfAlternateItemsLink() {
        return ListOfAlternateItemsLink;
    }

    public void setListOfSPItemsLink(Boolean ListOfSPItemsLink) {
        this.ListOfSPItemsLink = ListOfSPItemsLink;
    }

    public Boolean getListOfSPItemsLink() {
        return ListOfSPItemsLink;
    }

    public void setAttWiseListOfItemsLink(Boolean AttWiseListOfItemsLink) {
        this.AttWiseListOfItemsLink = AttWiseListOfItemsLink;
    }

    public Boolean getAttWiseListOfItemsLink() {
        return AttWiseListOfItemsLink;
    }

    public void setListOfItemsPriceLink(Boolean ListOfItemsPriceLink) {
        this.ListOfItemsPriceLink = ListOfItemsPriceLink;
    }

    public Boolean getListOfItemsPriceLink() {
        return ListOfItemsPriceLink;
    }

    public void setListOfWarehouseLink(Boolean ListOfWarehouseLink) {
        this.ListOfWarehouseLink = ListOfWarehouseLink;
    }

    public Boolean getListOfWarehouseLink() {
        return ListOfWarehouseLink;
    }

    public void setListOfBinsLink(Boolean ListOfBinsLink) {
        this.ListOfBinsLink = ListOfBinsLink;
    }

    public Boolean getListOfBinsLink() {
        return ListOfBinsLink;
    }

    public void setBinsCapacityLink(Boolean BinsCapacityLink) {
        this.BinsCapacityLink = BinsCapacityLink;
    }

    public Boolean getBinsCapacityLink() {
        return BinsCapacityLink;
    }

    public void setListOfKitLink(Boolean ListOfKitLink) {
        this.ListOfKitLink = ListOfKitLink;
    }

    public Boolean getListOfKitLink() {
        return ListOfKitLink;
    }

    public void setItemGroupChartLink(Boolean ItemGroupChartLink) {
        this.ItemGroupChartLink = ItemGroupChartLink;
    }

    public Boolean getItemGroupChartLink() {
        return ItemGroupChartLink;
    }

    public void setListOfItemsGroupLink(Boolean ListOfItemsGroupLink) {
        this.ListOfItemsGroupLink = ListOfItemsGroupLink;
    }

    public Boolean getListOfItemsGroupLink() {
        return ListOfItemsGroupLink;
    }

    public void setListOfUnitsLink(Boolean ListOfUnitsLink) {
        this.ListOfUnitsLink = ListOfUnitsLink;
    }

    public Boolean getListOfUnitsLink() {
        return ListOfUnitsLink;
    }

    public void setListOfUnitsConvLink(Boolean ListOfUnitsConvLink) {
        this.ListOfUnitsConvLink = ListOfUnitsConvLink;
    }

    public Boolean getListOfUnitsConvLink() {
        return ListOfUnitsConvLink;
    }

    public void setListOfSuppLink(Boolean ListOfSuppLink) {
        this.ListOfSuppLink = ListOfSuppLink;
    }

    public Boolean getListOfSuppLink() {
        return ListOfSuppLink;
    }

    public void setListOfParaLink(Boolean ListOfParaLink) {
        this.ListOfParaLink = ListOfParaLink;
    }

    public Boolean getListOfParaLink() {
        return ListOfParaLink;
    }

    public void setMMLink(Boolean MMLink) {
        this.MMLink = MMLink;
    }

    public Boolean getMMLink() {
        return MMLink;
    }

    public void ServItmVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setServiceRadioBind(RichSelectOneRadio serviceRadioBind) {
        this.serviceRadioBind = serviceRadioBind;
    }

    public RichSelectOneRadio getServiceRadioBind() {
        return serviceRadioBind;
    }

    public void SalesItmVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setSalesItmRadioBind(RichSelectOneRadio salesItmRadioBind) {
        this.salesItmRadioBind = salesItmRadioBind;
    }

    public RichSelectOneRadio getSalesItmRadioBind() {
        return salesItmRadioBind;
    }

    public void PurItmRadioBind(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setPurItmRadioBind(RichSelectOneRadio purItmRadioBind) {
        this.purItmRadioBind = purItmRadioBind;
    }

    public RichSelectOneRadio getPurItmRadioBind() {
        return purItmRadioBind;
    }

    public void PurItmRadioVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void WIPRadioVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setWiPRadioBind(RichSelectOneRadio wiPRadioBind) {
        this.wiPRadioBind = wiPRadioBind;
    }

    public RichSelectOneRadio getWiPRadioBind() {
        return wiPRadioBind;
    }

    public void TaxExRadioVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setTaxExRadioBind(RichSelectOneRadio taxExRadioBind) {
        this.taxExRadioBind = taxExRadioBind;
    }

    public RichSelectOneRadio getTaxExRadioBind() {
        return taxExRadioBind;
    }

    public void CapGdRadioVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setCapGdRadioVCL(RichSelectOneRadio capGdRadioVCL) {
        this.capGdRadioVCL = capGdRadioVCL;
    }

    public RichSelectOneRadio getCapGdRadioVCL() {
        return capGdRadioVCL;
    }

    public void setCapGdRadioBind(RichSelectOneRadio capGdRadioBind) {
        this.capGdRadioBind = capGdRadioBind;
    }

    public RichSelectOneRadio getCapGdRadioBind() {
        return capGdRadioBind;
    }

    public void CashPurItmRadioVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setCashPurItmRadioBind(RichSelectOneRadio cashPurItmRadioBind) {
        this.cashPurItmRadioBind = cashPurItmRadioBind;
    }

    public RichSelectOneRadio getCashPurItmRadioBind() {
        return cashPurItmRadioBind;
    }

    public void ConsumRadioVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setConsumRadioBind(RichSelectOneRadio consumRadioBind) {
        this.consumRadioBind = consumRadioBind;
    }

    public RichSelectOneRadio getConsumRadioBind() {
        return consumRadioBind;
    }

    public void ActvRadioVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setActvRadioBind(RichSelectOneRadio actvRadioBind) {
        this.actvRadioBind = actvRadioBind;
    }

    public RichSelectOneRadio getActvRadioBind() {
        return actvRadioBind;
    }

    public void setUnappCBBind(RichSelectBooleanCheckbox unappCBBind) {
        this.unappCBBind = unappCBBind;
    }

    public RichSelectBooleanCheckbox getUnappCBBind() {
        return unappCBBind;
    }

    public void setBlackListRadioBind(RichSelectOneRadio blackListRadioBind) {
        this.blackListRadioBind = blackListRadioBind;
    }

    public RichSelectOneRadio getBlackListRadioBind() {
        return blackListRadioBind;
    }

    public void setOnHoldRadioBind(RichSelectOneRadio onHoldRadioBind) {
        this.onHoldRadioBind = onHoldRadioBind;
    }

    public RichSelectOneRadio getOnHoldRadioBind() {
        return onHoldRadioBind;
    }

    public void FileTypeRadioTrans(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void topFileTypeTrans(ValueChangeEvent vce) {

    }


    public void setTopFileTypeTransBind(RichSelectOneRadio topFileTypeTransBind) {
        this.topFileTypeTransBind = topFileTypeTransBind;
    }

    public RichSelectOneRadio getTopFileTypeTransBind() {
        return topFileTypeTransBind;
    }

    public void midFileTypeTrans(ValueChangeEvent vce) {

    }


    public void setMidFileTypeTransBind(RichSelectOneRadio midFileTypeTransBind) {
        this.midFileTypeTransBind = midFileTypeTransBind;
    }

    public RichSelectOneRadio getMidFileTypeTransBind() {
        return midFileTypeTransBind;
    }

    public void botFileTypeTrans(ValueChangeEvent vce) {

    }

    public void setBotFileTypeTransBind(RichSelectOneRadio botFileTypeTransBind) {
        this.botFileTypeTransBind = botFileTypeTransBind;
    }

    public RichSelectOneRadio getBotFileTypeTransBind() {
        return botFileTypeTransBind;
    }


    public void setFiletypetransPgBind(RichSelectOneRadio filetypetransPgBind) {
        this.filetypetransPgBind = filetypetransPgBind;
    }

    public RichSelectOneRadio getFiletypetransPgBind() {
        return filetypetransPgBind;
    }

    public void setMaxstkBind(RichSelectOneRadio maxstkBind) {
        this.maxstkBind = maxstkBind;
    }

    public RichSelectOneRadio getMaxstkBind() {
        return maxstkBind;
    }

    public void setMinStockBind(RichSelectOneRadio minStockBind) {
        this.minStockBind = minStockBind;
    }

    public RichSelectOneRadio getMinStockBind() {
        return minStockBind;
    }

    public void setSafeStkBind(RichSelectOneRadio safeStkBind) {
        this.safeStkBind = safeStkBind;
    }

    public RichSelectOneRadio getSafeStkBind() {
        return safeStkBind;
    }

    public void setReorderLvlBind(RichSelectOneRadio reorderLvlBind) {
        this.reorderLvlBind = reorderLvlBind;
    }

    public RichSelectOneRadio getReorderLvlBind() {
        return reorderLvlBind;
    }

    public void setDownloadReptBind(RichSelectBooleanCheckbox downloadReptBind) {
        this.downloadReptBind = downloadReptBind;
    }

    public RichSelectBooleanCheckbox getDownloadReptBind() {
        return downloadReptBind;
    }

    public void downloadReptVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setDownloadedReptBind(RichSelectBooleanCheckbox downloadedReptBind) {
        this.downloadedReptBind = downloadedReptBind;
    }

    public RichSelectBooleanCheckbox getDownloadedReptBind() {
        return downloadedReptBind;
    }

    public void setListOfItemsCBBind(RichSelectBooleanCheckbox listOfItemsCBBind) {
        this.listOfItemsCBBind = listOfItemsCBBind;
    }

    public RichSelectBooleanCheckbox getListOfItemsCBBind() {
        return listOfItemsCBBind;
    }

    public void setGrpListOfItemsCBBind(RichSelectBooleanCheckbox grpListOfItemsCBBind) {
        this.grpListOfItemsCBBind = grpListOfItemsCBBind;
    }

    public RichSelectBooleanCheckbox getGrpListOfItemsCBBind() {
        return grpListOfItemsCBBind;
    }

    public void setSuppWiseListOfItemsCBBind(RichSelectBooleanCheckbox suppWiseListOfItemsCBBind) {
        this.suppWiseListOfItemsCBBind = suppWiseListOfItemsCBBind;
    }

    public RichSelectBooleanCheckbox getSuppWiseListOfItemsCBBind() {
        return suppWiseListOfItemsCBBind;
    }

    public void setBarCodeCBBind(RichSelectBooleanCheckbox barCodeCBBind) {
        this.barCodeCBBind = barCodeCBBind;
    }

    public RichSelectBooleanCheckbox getBarCodeCBBind() {
        return barCodeCBBind;
    }

    public void setListOfAltItemsCBBind(RichSelectBooleanCheckbox listOfAltItemsCBBind) {
        this.listOfAltItemsCBBind = listOfAltItemsCBBind;
    }

    public RichSelectBooleanCheckbox getListOfAltItemsCBBind() {
        return listOfAltItemsCBBind;
    }

    public void setListOfItemsSPCBBind(RichSelectBooleanCheckbox listOfItemsSPCBBind) {
        this.listOfItemsSPCBBind = listOfItemsSPCBBind;
    }

    public RichSelectBooleanCheckbox getListOfItemsSPCBBind() {
        return listOfItemsSPCBBind;
    }

    public void setAttListOfItemsCBBind(RichSelectBooleanCheckbox attListOfItemsCBBind) {
        this.attListOfItemsCBBind = attListOfItemsCBBind;
    }

    public RichSelectBooleanCheckbox getAttListOfItemsCBBind() {
        return attListOfItemsCBBind;
    }

    public void setListOfItemsPriceCBBind(RichSelectBooleanCheckbox listOfItemsPriceCBBind) {
        this.listOfItemsPriceCBBind = listOfItemsPriceCBBind;
    }

    public RichSelectBooleanCheckbox getListOfItemsPriceCBBind() {
        return listOfItemsPriceCBBind;
    }

    public void setListOfWarehouseCBBind(RichSelectBooleanCheckbox listOfWarehouseCBBind) {
        this.listOfWarehouseCBBind = listOfWarehouseCBBind;
    }

    public RichSelectBooleanCheckbox getListOfWarehouseCBBind() {
        return listOfWarehouseCBBind;
    }

    public void setListOfBinsCBBind(RichSelectBooleanCheckbox listOfBinsCBBind) {
        this.listOfBinsCBBind = listOfBinsCBBind;
    }

    public RichSelectBooleanCheckbox getListOfBinsCBBind() {
        return listOfBinsCBBind;
    }

    public void setBinsCapacityCBBind(RichSelectBooleanCheckbox binsCapacityCBBind) {
        this.binsCapacityCBBind = binsCapacityCBBind;
    }

    public RichSelectBooleanCheckbox getBinsCapacityCBBind() {
        return binsCapacityCBBind;
    }

    public void setListOfKitCBBind(RichSelectBooleanCheckbox listOfKitCBBind) {
        this.listOfKitCBBind = listOfKitCBBind;
    }

    public RichSelectBooleanCheckbox getListOfKitCBBind() {
        return listOfKitCBBind;
    }

    public void setItemGroupChartCBBind(RichSelectBooleanCheckbox itemGroupChartCBBind) {
        this.itemGroupChartCBBind = itemGroupChartCBBind;
    }

    public RichSelectBooleanCheckbox getItemGroupChartCBBind() {
        return itemGroupChartCBBind;
    }

    public void setListOfItemsGrpCBBind(RichSelectBooleanCheckbox listOfItemsGrpCBBind) {
        this.listOfItemsGrpCBBind = listOfItemsGrpCBBind;
    }

    public RichSelectBooleanCheckbox getListOfItemsGrpCBBind() {
        return listOfItemsGrpCBBind;
    }

    public void setListOfUnitsCBBind(RichSelectBooleanCheckbox listOfUnitsCBBind) {
        this.listOfUnitsCBBind = listOfUnitsCBBind;
    }

    public RichSelectBooleanCheckbox getListOfUnitsCBBind() {
        return listOfUnitsCBBind;
    }

    public void setListOfUnitsConvCBBind(RichSelectBooleanCheckbox listOfUnitsConvCBBind) {
        this.listOfUnitsConvCBBind = listOfUnitsConvCBBind;
    }

    public RichSelectBooleanCheckbox getListOfUnitsConvCBBind() {
        return listOfUnitsConvCBBind;
    }

    public void ListOfSuppCBBind(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void ListOfParaCBBind(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setListOfParamCBBind(RichSelectBooleanCheckbox listOfParamCBBind) {
        this.listOfParamCBBind = listOfParamCBBind;
    }

    public RichSelectBooleanCheckbox getListOfParamCBBind() {
        return listOfParamCBBind;
    }

    public void ListOfItemsVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(listOfItemsCBBind);
    }

    public void setListOfSuppCBBind(RichSelectBooleanCheckbox listOfSuppCBBind) {
        this.listOfSuppCBBind = listOfSuppCBBind;
    }

    public RichSelectBooleanCheckbox getListOfSuppCBBind() {
        return listOfSuppCBBind;
    }

    public void setUnapproved(Boolean Unapproved) {
        this.Unapproved = Unapproved;
    }

    public Boolean getUnapproved() {
        return Unapproved;
    }

    public void UnappVCL(ValueChangeEvent vce) {
        if (unappCBBind.isSelected()) {
            Unapproved = true;
            setSuppWiseListOfItemsLink(false);
            setPrintBarcodeLink(false);
            setListOfAlternateItemsLink(false);
            setListOfSPItemsLink(false);
            setAttWiseListOfItemsLink(false);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(suppWiseListOfItemsCBBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(barCodeCBBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listOfAltItemsCBBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listOfItemsSPCBBind);

        AdfFacesContext.getCurrentInstance().addPartialTarget(attListOfItemsCBBind);
    }

    public void setImgCBBind(RichSelectBooleanCheckbox imgCBBind) {
        this.imgCBBind = imgCBBind;
    }

    public RichSelectBooleanCheckbox getImgCBBind() {
        return imgCBBind;
    }


    public void DownloadedReptVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() == true) {

            if (rptNameLOVBind.getValue() == null) {
                LOVClientId = rptNameLOVBind.getClientId();
                //rptNameLOVBind.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(rptNameLOVBind);
                setDownloadReptLink(false); // To Disable GoLink


                FacesMessage message = new FacesMessage("Please Select the Report");

                message.setSeverity(FacesMessage.SEVERITY_ERROR);

                FacesContext.getCurrentInstance().addMessage(LOVClientId, message);

            }

        }
    }

    public void setRptNameLOVBind(RichInputListOfValues rptNameLOVBind) {
        this.rptNameLOVBind = rptNameLOVBind;
    }

    public RichInputListOfValues getRptNameLOVBind() {
        return rptNameLOVBind;
    }

    public static BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void ResetAction(ActionEvent actionEvent) {
        System.out.println("before get bindings");
        OperationBinding ob = getBindings().getOperationBinding("ResetAction");
        System.out.println("after bindings get");
        ob.execute();
        System.out.println("after bindings execute");
        stockableradio.setValue("Both");
        AdfFacesContext.getCurrentInstance().addPartialTarget(stockableradio);
        maxstkBind.setValue("Do Not Check");
        AdfFacesContext.getCurrentInstance().addPartialTarget(maxstkBind);
        minStockBind.setValue("Do Not Check");
        AdfFacesContext.getCurrentInstance().addPartialTarget(minStockBind);
        safeStkBind.setValue("Do Not Check");
        AdfFacesContext.getCurrentInstance().addPartialTarget(safeStkBind);
        reorderLvlBind.setValue("Do Not Check");
        AdfFacesContext.getCurrentInstance().addPartialTarget(reorderLvlBind);
        serviceRadioBind.setValue("Both");
        AdfFacesContext.getCurrentInstance().addPartialTarget(serviceRadioBind);
        salesItmRadioBind.setValue("Both");
        AdfFacesContext.getCurrentInstance().addPartialTarget(salesItmRadioBind);
        purItmRadioBind.setValue("Both");
        AdfFacesContext.getCurrentInstance().addPartialTarget(purItmRadioBind);
        wiPRadioBind.setValue("Both");
        AdfFacesContext.getCurrentInstance().addPartialTarget(wiPRadioBind);
        taxExRadioBind.setValue("Both");
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxExRadioBind);
        capGdRadioBind.setValue("Both");
        AdfFacesContext.getCurrentInstance().addPartialTarget(capGdRadioBind);
        cashPurItmRadioBind.setValue("Both");
        AdfFacesContext.getCurrentInstance().addPartialTarget(cashPurItmRadioBind); /*  */
        consumRadioBind.setValue("Both");
        AdfFacesContext.getCurrentInstance().addPartialTarget(consumRadioBind);
        actvRadioBind.setValue("Both");
        AdfFacesContext.getCurrentInstance().addPartialTarget(actvRadioBind);
        unappCBBind.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(unappCBBind);
        blackListRadioBind.setValue("Both");
        AdfFacesContext.getCurrentInstance().addPartialTarget(blackListRadioBind);
        onHoldRadioBind.setValue("Both");
        AdfFacesContext.getCurrentInstance().addPartialTarget(onHoldRadioBind);
        orgNameBackBind.setValue(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(orgNameBackBind);
        //bgColorBind.setValue("D");
        // AdfFacesContext.getCurrentInstance().addPartialTarget(bgColorBind);
        // headColorBind.setValue("D");
        //AdfFacesContext.getCurrentInstance().addPartialTarget(headColorBind);
        //backColorDBind.setValue(true);
        // headColorDBind.setValue(true);
        System.out.println("after reset action");
    }

    public void setOrgNameBackBind(RichSelectBooleanCheckbox orgNameBackBind) {
        this.orgNameBackBind = orgNameBackBind;
    }

    public RichSelectBooleanCheckbox getOrgNameBackBind() {
        return orgNameBackBind;
    }

    public void setBgColorBind(RichSelectOneRadio bgColorBind) {
        this.bgColorBind = bgColorBind;
    }

    public RichSelectOneRadio getBgColorBind() {
        return bgColorBind;
    }

    public void setHeadColorBind(RichSelectOneRadio headColorBind) {
        this.headColorBind = headColorBind;
    }

    public RichSelectOneRadio getHeadColorBind() {
        return headColorBind;
    }

    public void setBackColorDBind(RichSelectItem backColorDBind) {
        this.backColorDBind = backColorDBind;
    }

    public RichSelectItem getBackColorDBind() {
        return backColorDBind;
    }

    public void setHeadColorDBind(RichSelectItem headColorDBind) {
        this.headColorDBind = headColorDBind;
    }

    public RichSelectItem getHeadColorDBind() {
        return headColorDBind;
    }

    public void setAttrValNmPageBind(RichInputComboboxListOfValues attrValNmPageBind) {
        this.attrValNmPageBind = attrValNmPageBind;
    }

    public RichInputComboboxListOfValues getAttrValNmPageBind() {
        return attrValNmPageBind;
    }

    public void setB(List<String> b) {
        this.b = b;
    }

    public List<String> getB() {
        return b;
    }

    public void setNewmap(HashMap newmap) {
        this.newmap = newmap;
    }

    public HashMap getNewmap() {
        return newmap;
    }

    public void setNewsize(Integer newsize) {
        this.newsize = newsize;
    }

    public Integer getNewsize() {
        return newsize;
    }

    public void setAttrIdPagBinding(RichInputText attrIdPagBinding) {
        this.attrIdPagBinding = attrIdPagBinding;
    }

    public RichInputText getAttrIdPagBinding() {
        return attrIdPagBinding;
    }
    HashMap newmap = new HashMap();
    public Integer newsize = 0;

    public void AddAttrAction(ActionEvent actionEvent) {
        if (attrValNmPageBind.getValue() != null && attrIdPagBinding.getValue() != null) {
            System.out.println("Attribute id--" + attrValNmPageBind.getValue().toString());
            System.out.println("data before add" + attrValNmPageBind.getValue().toString());
            b.add(attrValNmPageBind.getValue().toString());
            newmap.put(attrValNmPageBind.getValue().toString(), attrIdPagBinding.getValue().toString());
            this.newsize = b.size();
            System.out.println("Array List" + b.size());
            System.out.println("Map size" + newmap.size());
            this.attrValNmPageBind.setValue(null);
            OperationBinding ob =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("addpartialattributeNamAm");
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(attrValNmPageBind);
            //Store Attribute Key
            /*        attrIdMulti = newmap.values().toString();
            attrIdMulti = attrIdMulti.substring(1, attrIdMulti.length() - 1);
            String[] attrSplit= attrIdMulti.trim().split("\\s*,\\s*");
            System.out.println("length   "+attrSplit.length);
            if(attrSplit.length>=0){
                for(int i = 0; i<attrSplit.length;i++){
                    if(i==0){
                        attrId1 = attrSplit[i];
                        System.out.println("attrId1--"+attrId1);
                    }
                    if(i==1){
                        attrId2 = attrSplit[i];
                        System.out.println("attrId2--"+attrId2);
                    }
                    if(i==2){
                        attrId3 = attrSplit[i];
                        System.out.println("attrId3--"+attrId3);
                    }
                    if(i==3){
                        attrId4 = attrSplit[i];
                        System.out.println("attrId4--"+attrId4);
                    }
                    if(i==4){
                        attrId5 = attrSplit[i];
                        System.out.println("attrId5--"+attrId5);
                    }
                }
            }
      */
        }
    }

    public void removeAttriAction(ActionEvent actionEvent) {
        RichLink rl = (RichLink) actionEvent.getSource();
        System.out.println("Atribute Name to delete1=" + rl.getAttributes().get("newAttr"));
        String grpName = (String) rl.getAttributes().get("newAttr");
        System.out.println("Attribute Name to delete=" + grpName);

        System.out.println(" b remove :" + b.remove(grpName));
        System.out.println("newmap remove : " + newmap.remove(grpName));
        this.setAttrId1("");
        System.out.println("attrId1--" + attrId1);
        this.setAttrId2("");
        System.out.println("AttrId2--" + attrId2);
        this.setAttrId3("");
        System.out.println("AttrId3--" + attrId3);
        this.setAttrId4("");
        System.out.println("AttrId4--" + attrId4);
        this.setAttrId5("");
        System.out.println("AttrId5--" + attrId5);
        System.out.println(newmap.size());
        newsize = b.size();
    }

    public void AttrNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String st = (String) object;
            if (b.size() != 0) {
                for (int i = 0; i < b.size(); i++) {
                    if (st.equals(b.get(i).toString())) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Duplicate Value not allowed", null));
                    }
                }
            }
        }
    }

    public void setSuppEvalCBBinding(RichSelectBooleanCheckbox suppEvalCBBinding) {
        this.suppEvalCBBinding = suppEvalCBBinding;
    }

    public RichSelectBooleanCheckbox getSuppEvalCBBinding() {
        return suppEvalCBBinding;
    }

    public void setSuppEvalLinkBinding(RichLink suppEvalLinkBinding) {
        this.suppEvalLinkBinding = suppEvalLinkBinding;
    }

    public RichLink getSuppEvalLinkBinding() {
        return suppEvalLinkBinding;
    }

    public void setSuppPrcLvlCBBind(RichSelectBooleanCheckbox suppPrcLvlCBBind) {
        this.suppPrcLvlCBBind = suppPrcLvlCBBind;
    }

    public RichSelectBooleanCheckbox getSuppPrcLvlCBBind() {
        return suppPrcLvlCBBind;
    }

    public void suppPrcLvlCBVCL(ValueChangeEvent valueChangeEvent) {
        suppPriceLevelLink = false;

    }

    public void setSuppPrcLvlItemCBVCL(RichSelectBooleanCheckbox suppPrcLvlItemCBVCL) {
        this.suppPrcLvlItemCBVCL = suppPrcLvlItemCBVCL;
    }

    public RichSelectBooleanCheckbox getSuppPrcLvlItemCBVCL() {
        return suppPrcLvlItemCBVCL;
    }

    public void setSuppPrcLvlItmBind(RichSelectBooleanCheckbox suppPrcLvlItmBind) {
        this.suppPrcLvlItmBind = suppPrcLvlItmBind;
    }

    public RichSelectBooleanCheckbox getSuppPrcLvlItmBind() {
        return suppPrcLvlItmBind;
    }

    public void suppPrcLvlItmCBVCL(ValueChangeEvent valueChangeEvent) {
        suppPriceLevelItemLink = false;
    }

    public void setSuppDetAddsCVBind(RichSelectBooleanCheckbox suppDetAddsCVBind) {
        this.suppDetAddsCVBind = suppDetAddsCVBind;
    }

    public RichSelectBooleanCheckbox getSuppDetAddsCVBind() {
        return suppDetAddsCVBind;
    }

    public void suppDetAddsCBVCL(ValueChangeEvent valueChangeEvent) {
        suppDetAddsLink = false;
    }

    public void suppPrcPlcyCBVCL(ValueChangeEvent valueChangeEvent) {
        suppPricePlcyLink = false;
    }

    public void setSuppPrcPlcyCBBind(RichSelectBooleanCheckbox suppPrcPlcyCBBind) {
        this.suppPrcPlcyCBBind = suppPrcPlcyCBBind;
    }

    public RichSelectBooleanCheckbox getSuppPrcPlcyCBBind() {
        return suppPrcPlcyCBBind;
    }

    public void suppPrcPlcyHistCBVCL(ValueChangeEvent valueChangeEvent) {
        suppPricePlcyHistLink = false;
    }

    public void setSuppPrcPlcyHistCBBind(RichSelectBooleanCheckbox suppPrcPlcyHistCBBind) {
        this.suppPrcPlcyHistCBBind = suppPrcPlcyHistCBBind;
    }

    public RichSelectBooleanCheckbox getSuppPrcPlcyHistCBBind() {
        return suppPrcPlcyHistCBBind;
    }


    public void pricePlcyPrfCBVCL(ValueChangeEvent valueChangeEvent) {
        pricePlcyPrfLink = false;
    }

    public void setPricePlcyPrfBind(RichSelectBooleanCheckbox pricePlcyPrfBind) {
        this.pricePlcyPrfBind = pricePlcyPrfBind;
    }

    public RichSelectBooleanCheckbox getPricePlcyPrfBind() {
        return pricePlcyPrfBind;
    }

    public void setPricePlcyWsListCBBind(RichSelectBooleanCheckbox pricePlcyWsListCBBind) {
        this.pricePlcyWsListCBBind = pricePlcyWsListCBBind;
    }

    public RichSelectBooleanCheckbox getPricePlcyWsListCBBind() {
        return pricePlcyWsListCBBind;
    }

    public void pricePlcyWsListCBVCL(ValueChangeEvent valueChangeEvent) {
        pricePlcyListLink = false;
    }

    public void LOItemChaptrCBVCL(ValueChangeEvent valueChangeEvent) {
        LOIChaptrLink = false;
    }

    public void setLoItemChaptrCBBind(RichSelectBooleanCheckbox loItemChaptrCBBind) {
        this.loItemChaptrCBBind = loItemChaptrCBBind;
    }

    public RichSelectBooleanCheckbox getLoItemChaptrCBBind() {
        return loItemChaptrCBBind;
    }

    public void ExcsChptrListItmCBVCL(ValueChangeEvent valueChangeEvent) {
        ExciseChapterLink = false;
    }

    public void setExcsChaptrListItmCBBind(RichSelectBooleanCheckbox excsChaptrListItmCBBind) {
        this.excsChaptrListItmCBBind = excsChaptrListItmCBBind;
    }

    public RichSelectBooleanCheckbox getExcsChaptrListItmCBBind() {
        return excsChaptrListItmCBBind;
    }

    public void ReqAreaCBVCL(ValueChangeEvent valueChangeEvent) {
        ReqAreaLink = false;
    }

    public void setReqAreaCBBind(RichSelectBooleanCheckbox reqAreaCBBind) {
        this.reqAreaCBBind = reqAreaCBBind;
    }

    public RichSelectBooleanCheckbox getReqAreaCBBind() {
        return reqAreaCBBind;
    }

    /*   public void setReqAreaLovBinding(RichSelectOneChoice reqAreaLovBinding) {
        this.reqAreaLovBinding = reqAreaLovBinding;
    }

    public RichSelectOneChoice getReqAreaLovBinding() {
        return reqAreaLovBinding;
    } */
    public void QCItemCBVCL(ValueChangeEvent valueChangeEvent) {
       this.setQCGroupLink(false);
    }

    public void setQcItemCBBind(RichSelectBooleanCheckbox qcItemCBBind) {
        this.qcItemCBBind = qcItemCBBind;
    }

    public RichSelectBooleanCheckbox getQcItemCBBind() {
        return qcItemCBBind;
    }

    public void QCGroupCBVCL(ValueChangeEvent valueChangeEvent) {
      this.setQCGroupLink(false);
    }

    public void setQcGroupCBBind(RichSelectBooleanCheckbox qcGroupCBBind) {
        this.qcGroupCBBind = qcGroupCBBind;
    }

    public RichSelectBooleanCheckbox getQcGroupCBBind() {
        return qcGroupCBBind;
    }
}
