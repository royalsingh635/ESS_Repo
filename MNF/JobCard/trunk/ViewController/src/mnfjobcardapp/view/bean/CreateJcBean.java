package mnfjobcardapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.bean.StaticValue;
import adf.utils.model.ADFModelUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ValueExpression;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import mnfjobcardapp.view.utils.JSFUtils;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandNavigationItem;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.component.rich.output.RichSpacer;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.javatools.parser.java.v2.internal.compiler.Obj;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class CreateJcBean {

    private static ADFLogger adfLog = (ADFLogger) ADFLogger.createADFLogger(CreateJcBean.class);
    private String editClick = null;
    private RichOutputText jcDocIdBind;
    private RichInputText opSrNoBindVar;
    private UploadedFile _File;
    private String fileDisplayname = "";
    private RichInputFile attachDocBindVar;
    private RichInputText attachFileNmBindVar;
    private String wfId = null;
    private String AutoId = null;
    private RichInputText itmbracketNo;
    private String itemFetch = "disable";
    private RichPopup selectSrNoPopUpBind;
    private RichPopup selectLotPopUpBind;
    private RichPopup selectLotBinPopUpBind;
    private String facetName = "Lot";
    private RichOutputText stockAvailablePgBind;
    private RichInputText actualItemQuantityPgBind;
    private RichOutputText itmIssueSrBind;
    private RichOutputText itmQtyIssueLblBind;
    private RichOutputText totStkLotBind;
    private RichOutputText itmQtyIssueBinLblBind;
    private RichOutputText totStkLotBinBind;
    private RichInputListOfValues opIdBindVar;
    private RichInputListOfValues whIdBindVar;
    private RichInputListOfValues shiftIdBindVar;
    private RichInputListOfValues empIdBindVar;
    private RichInputListOfValues reqAreaBindVar;
    private RichInputDate startDtBindVar;
    private RichInputDate endDtBindVar;
    private RichOutputText itemIdPgBind;
    private RichInputListOfValues inputItemBind;
    private RichTable allItemsDispPgBind;
    private Key key;
    private String searchValue;
    private RichInputText jcStatBindvar;
    private RichInputDate machineStartTym;
    private RichInputDate machineEndTym;
    private String WsVal = null;
    private String PrevDate = null;
    private String FutDate = null;
    private RichInputListOfValues srcDocIdBindVar;
    private RichInputText actOutputItmQtyBindVar;
    private RichInputListOfValues wsIdBindVar;
    private RichSelectOneChoice jobCardBasisBindVar;
    private RichInputDate sourceDocDtBind;
    private RichPanelFormLayout outputDetailsFormBind;
    private String releaseMode = "I";
    private String ChkForFetch = "X";
    private RichLink saveBtnAction;
    private RichButton releaseBtnBind;
    private RichPopup addLotPopUp;
    private RichInputText lotIdBindVar;
    private RichInputText lotQtyBindVar;
    private RichInputText lotIdInSerial;
    private RichInputText serialNoBindVar;
    private String lotNo = "";
    private String lotQty = "";
    private String lotId_EN = null;
    private String itmSeriallized = null;
    private RichPanelTabbed panelTabBinding;
    private RichShowDetailItem showDetailsOutputitmStk;
    private RichPanelCollection panelColLotTableBind;
    private RichPanelCollection panelColSerialTableBind;
    private RichButton barcode_Button;
    private RichInputText opoutputItmQty;
    private RichButton populateItmBindVar;
    private RichOutputText shopFloor_stockAvailablePgBind;
    private RichPopup selectSrNoPopUpBind_ShopFloor;
    private RichOutputText itmIssueSrBind_ShopFloor;
    private RichPopup selectLotPopUpBind_ShopFloor;
    private RichOutputText itmQtyIssueLblBind_ShopFloor;
    private RichOutputText totStkLotBind_ShopFloor;
    private RichPopup selectLotBinPopUpBind_ShopFloor;
    private RichOutputText itmQtyIssueBinLblBind_ShopFloor;
    private RichOutputText totStkLotBinBind_ShopFloor;
    private UIXSwitcher switcherLotBindVar;
    private RichShowDetailItem showDetailAllItmTabBind;
    private RichInputText jobCardDispIdBind;
    private RichInputText createdBybindVar;
    private RichInputDate createdOnBindVar;
    private String BarcodeNo = "";
    private String serialNo = "";
    private RichInputText outputItemNmBindVar;
    private RichShowDetailItem showOperationItemTabBindVar;
    private RichSelectOneRadio fg_sfgradioBindVar;
    private RichButton add_editLotBtnBindVar;
    private RichPanelBox lotPanel;
    private RichTable lotTbl;
    private RichTable binTbl;
    private RichTable srcTbl;
    private RichOutputText docIdSrc;
    private RichSelectOneChoice valueTypeBindVar;
    private RichInputText paramValueBindVar;
    private RichSelectBooleanCheckbox isValuePercBinding;
    private RichInputText minTolBindVar;
    private RichInputText maxTolBindVar;
    private RichInputText minValBindVar;
    private RichInputText maxValBindVar;
    private RichTable qcParamTable;
    private int oldVal = 0;
    private RichInputText pendingQty;
    private RichTable inputTabBind;
    private RichSelectOneChoice itemType;
    private RichSelectOneChoice jbTypeBind;
    private RichSpacer addItemTab;
    private RichPanelGroupLayout linkPanelGroup;
    private RichPopup deletePop;
    private RichInputText itemIdBind;
    private RichTable inputItemTable;
    private RichTable outputItemTable;
    private RichPanelGroupLayout linkGroup;
    private String shortCloseFlag;
    private RichButton addSerialbtn;
    private RichPanelFormLayout bindFormLayout;
    private RichPanelGroupLayout bindItemGrpLayout;
    private RichLink deleteBtnBind;

    public void setShortCloseFlag(String shortCloseFlag) {
        this.shortCloseFlag = shortCloseFlag;
    }

    public String getShortCloseFlag() {
        adfLog.info("in the getter shortCloseFlag");
        OperationBinding op=ADFBeanUtils.findOperation("checkShortCloseForPartialQty");
        op.execute();
        return (String)op.getResult();
        
    }
    private Boolean isCCExist = null;
    private RichInputDate jcDate;
    private RichSelectOneChoice isuueType;
    private RichInputText returnQty;
    private RichInputText itmQty;
    private RichInputListOfValues bindSubContractNm;
    private RichInputListOfValues bindCurrencyNm;
    private String qcChkFlg="N";
    private String opYieldFlg="N";
    private RichSelectBooleanCheckbox bindShortClose;

    public void setOpYieldFlg(String opYieldFlg) {
        this.opYieldFlg = opYieldFlg;
    }

    public String getOpYieldFlg() {
        OperationBinding opd = ADFBeanUtils.findOperation("ChkCalcYieldPerFlg");
         opd.execute();
         String result=(String)opd.getResult();
        adfLog.info("value of result in yield::"+result);
        return result;
    }

    public void setQcChkFlg(String qcChkFlg) {
        this.qcChkFlg = qcChkFlg;
    }

    public String getQcChkFlg() {
         OperationBinding ob1 = ADFBeanUtils.findOperation("ChkQcRequired");
          ob1.execute();
          String result=(String)ob1.getResult();
          adfLog.info("value of result in getter::"+result);
        return result;
    }

    public void setIsCCExist(Boolean isCCExist) {
        this.isCCExist = isCCExist;
    }

    public Boolean getIsCCExist() {
        if (this.isCCExist == null) {
            OperationBinding op = this.getBindings().getOperationBinding("isCostCenterAppli");
            op.execute();
            if (op.getErrors().size() == 0) {
                this.isCCExist = (Boolean) op.getResult();
            } else {
                this.isCCExist = false;
            }
        }
        return isCCExist;
    }

    /*-----------------Method for Getter and Setter-----------------*/
    public void setShowOperationItemTabBindVar(RichShowDetailItem showOperationItemTabBindVar) {
        this.showOperationItemTabBindVar = showOperationItemTabBindVar;
    }

    public RichShowDetailItem getShowOperationItemTabBindVar() {
        return showOperationItemTabBindVar;
    }

    public void setFg_sfgradioBindVar(RichSelectOneRadio fg_sfgradioBindVar) {
        this.fg_sfgradioBindVar = fg_sfgradioBindVar;
    }

    public RichSelectOneRadio getFg_sfgradioBindVar() {
        return fg_sfgradioBindVar;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setBarcodeNo(String BarcodeNo) {
        this.BarcodeNo = BarcodeNo;
    }

    public String getBarcodeNo() {
        return BarcodeNo;
    }

    public CreateJcBean() {

    }

    public void setPopulateItmBindVar(RichButton populateItmBindVar) {
        this.populateItmBindVar = populateItmBindVar;
    }

    public RichButton getPopulateItmBindVar() {
        return populateItmBindVar;
    }

    public void setOpoutputItmQty(RichInputText opoutputItmQty) {
        this.opoutputItmQty = opoutputItmQty;
    }

    public RichInputText getOpoutputItmQty() {
        return opoutputItmQty;
    }

    public void setBarcode_Button(RichButton barcode_Button) {
        this.barcode_Button = barcode_Button;
    }

    public RichButton getBarcode_Button() {
        return barcode_Button;
    }

    public void setPanelColLotTableBind(RichPanelCollection panelColLotTableBind) {
        this.panelColLotTableBind = panelColLotTableBind;
    }

    public RichPanelCollection getPanelColLotTableBind() {
        return panelColLotTableBind;
    }

    public void setPanelColSerialTableBind(RichPanelCollection panelColSerialTableBind) {
        this.panelColSerialTableBind = panelColSerialTableBind;
    }

    public RichPanelCollection getPanelColSerialTableBind() {
        return panelColSerialTableBind;
    }

    public void setPanelTabBinding(RichPanelTabbed panelTabBinding) {
        this.panelTabBinding = panelTabBinding;
    }

    public RichPanelTabbed getPanelTabBinding() {
        return panelTabBinding;
    }

    public void setShowDetailsOutputitmStk(RichShowDetailItem showDetailsOutputitmStk) {
        this.showDetailsOutputitmStk = showDetailsOutputitmStk;
    }

    public RichShowDetailItem getShowDetailsOutputitmStk() {
        return showDetailsOutputitmStk;
    }

    public void setItmSeriallized(String itmSeriallized) {
        this.itmSeriallized = itmSeriallized;
    }

    public String getItmSeriallized() {
        ob = ADFBeanUtils.findOperation("ChkOutputItmSerialized");
        ob.execute();
        return ob.getResult().toString();
        // return itmSeriallized;
    }

    public void setLotId_EN(String lotId_EN) {
        this.lotId_EN = lotId_EN;
    }

    public String getLotId_EN() {
        ob = ADFBeanUtils.findOperation("AllowLotIdEditable");
        ob.execute();
        return ob.getResult().toString();
        // return lotId_EN;
    }

    public void setLotQty(String lotQty) {
        this.lotQty = lotQty;
    }

    public String getLotQty() {
        return lotQty;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setAddLotPopUp(RichPopup addLotPopUp) {
        this.addLotPopUp = addLotPopUp;
    }

    public RichPopup getAddLotPopUp() {
        return addLotPopUp;
    }

    public void setChkForFetch(String ChkForFetch) {
        this.ChkForFetch = ChkForFetch;
    }

    public String getChkForFetch() {
        return ChkForFetch;
    }

    public void setReleaseMode(String releaseMode) {
        this.releaseMode = releaseMode;
    }

    public String getReleaseMode() {
        return releaseMode;
    }

    public void setActOutputItmQtyBindVar(RichInputText actOutputItmQtyBindVar) {
        this.actOutputItmQtyBindVar = actOutputItmQtyBindVar;
    }

    public RichInputText getActOutputItmQtyBindVar() {
        return actOutputItmQtyBindVar;
    }

    public void setSrcDocIdBindVar(RichInputListOfValues srcDocIdBindVar) {
        this.srcDocIdBindVar = srcDocIdBindVar;
    }

    public RichInputListOfValues getSrcDocIdBindVar() {
        return srcDocIdBindVar;
    }

    public void setPrevDate(String PrevDate) {
        this.PrevDate = PrevDate;
    }

    public String getPrevDate() {
        ob = ADFBeanUtils.findOperation("AllowJcPrevious"); // Here Checking for Job Card Enable/Disable
        ob.execute();
        return ob.getResult().toString();
        //return PrevDate;
    }

    public void setFutDate(String FutDate) {
        this.FutDate = FutDate;
    }

    public String getFutDate() {
        ob = ADFBeanUtils.findOperation("AllowJcFuture"); // Here Checking for Job Card Enable/Disable
        ob.execute();
        return ob.getResult().toString();
        // return FutDate;
    }

    public void setWsVal(String WsVal) {
        this.WsVal = WsVal;
    }

    public String getWsVal() {
        ob = ADFBeanUtils.findOperation("AllowJcWorkStation"); // Here Checking for Work Station Enable/Disable
        ob.execute();
        return ob.getResult().toString();
        //return WsVal;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Key getKey() {
        return key;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setReqAreaBindVar(RichInputListOfValues reqAreaBindVar) {
        this.reqAreaBindVar = reqAreaBindVar;
    }

    public RichInputListOfValues getReqAreaBindVar() {
        return reqAreaBindVar;
    }

    public void setFacetName(String facetName) {
        this.facetName = facetName;
    }

    public String getFacetName() {
        return facetName;
    }

    public void setItmbracketNo(RichInputText itmbracketNo) {
        this.itmbracketNo = itmbracketNo;
    }

    public RichInputText getItmbracketNo() {
        return itmbracketNo;
    }

    public void setAutoId(String AutoId) {
        this.AutoId = AutoId;
    }

    public String getAutoId() {
        return AutoId;
    }

    public void setWfId(String wfId) {
        this.wfId = wfId;
    }

    public String getWfId() {
        return wfId;
    }

    public void setFile(UploadedFile _File) {
        this._File = _File;
    }

    public UploadedFile getFile() {
        return _File;
    }

    public void setFileDisplayname(String fileDisplayname) {
        this.fileDisplayname = fileDisplayname;
    }

    public String getFileDisplayname() {
        return fileDisplayname;
    }

    public void setAttachDocBindVar(RichInputFile attachDocBindVar) {
        this.attachDocBindVar = attachDocBindVar;
    }

    public RichInputFile getAttachDocBindVar() {
        return attachDocBindVar;
    }


    public void setAttachFileNmBindVar(RichInputText attachFileNmBindVar) {
        this.attachFileNmBindVar = attachFileNmBindVar;
    }

    public RichInputText getAttachFileNmBindVar() {
        return attachFileNmBindVar;
    }

    public void setEditClick(String editClick) {
        this.editClick = editClick;
    }

    public String getEditClick() {
        return editClick;
    }

    public void setJcDocIdBind(RichOutputText jcDocIdBind) {
        this.jcDocIdBind = jcDocIdBind;
    }

    public RichOutputText getJcDocIdBind() {
        return jcDocIdBind;
    }

    public void setOpSrNoBindVar(RichInputText opSrNoBindVar) {
        this.opSrNoBindVar = opSrNoBindVar;
    }

    public RichInputText getOpSrNoBindVar() {
        return opSrNoBindVar;
    }

    public void setStockAvailablePgBind(RichOutputText stockAvailablePgBind) {
        this.stockAvailablePgBind = stockAvailablePgBind;
    }

    public RichOutputText getStockAvailablePgBind() {
        return stockAvailablePgBind;
    }

    public void setActualItemQuantityPgBind(RichInputText actualItemQuantityPgBind) {
        this.actualItemQuantityPgBind = actualItemQuantityPgBind;
    }

    public RichInputText getActualItemQuantityPgBind() {
        return actualItemQuantityPgBind;
    }

    public void setItmIssueSrBind(RichOutputText itmIssueSrBind) {
        this.itmIssueSrBind = itmIssueSrBind;
    }

    public RichOutputText getItmIssueSrBind() {
        return itmIssueSrBind;
    }

    public void setItmQtyIssueLblBind(RichOutputText itmQtyIssueLblBind) {
        this.itmQtyIssueLblBind = itmQtyIssueLblBind;
    }

    public RichOutputText getItmQtyIssueLblBind() {
        return itmQtyIssueLblBind;
    }

    public void setTotStkLotBind(RichOutputText totStkLotBind) {
        this.totStkLotBind = totStkLotBind;
    }

    public RichOutputText getTotStkLotBind() {
        return totStkLotBind;
    }

    public void setItmQtyIssueBinLblBind(RichOutputText itmQtyIssueBinLblBind) {
        this.itmQtyIssueBinLblBind = itmQtyIssueBinLblBind;
    }

    public RichOutputText getItmQtyIssueBinLblBind() {
        return itmQtyIssueBinLblBind;
    }

    public void setTotStkLotBinBind(RichOutputText totStkLotBinBind) {
        this.totStkLotBinBind = totStkLotBinBind;
    }

    public RichOutputText getTotStkLotBinBind() {
        return totStkLotBinBind;
    }

    public void setOpIdBindVar(RichInputListOfValues opIdBindVar) {
        this.opIdBindVar = opIdBindVar;
    }

    public RichInputListOfValues getOpIdBindVar() {
        return opIdBindVar;
    }

    public void setWhIdBindVar(RichInputListOfValues whIdBindVar) {
        this.whIdBindVar = whIdBindVar;
    }

    public RichInputListOfValues getWhIdBindVar() {
        return whIdBindVar;
    }

    public void setShiftIdBindVar(RichInputListOfValues shiftIdBindVar) {
        this.shiftIdBindVar = shiftIdBindVar;
    }

    public RichInputListOfValues getShiftIdBindVar() {
        return shiftIdBindVar;
    }

    public void setEmpIdBindVar(RichInputListOfValues empIdBindVar) {
        this.empIdBindVar = empIdBindVar;
    }

    public RichInputListOfValues getEmpIdBindVar() {
        return empIdBindVar;
    }

    public void setStartDtBindVar(RichInputDate startDtBindVar) {
        this.startDtBindVar = startDtBindVar;
    }

    public RichInputDate getStartDtBindVar() {
        return startDtBindVar;
    }

    public void setEndDtBindVar(RichInputDate endDtBindVar) {
        this.endDtBindVar = endDtBindVar;
    }

    public RichInputDate getEndDtBindVar() {
        return endDtBindVar;
    }

    public void setItemIdPgBind(RichOutputText itemIdPgBind) {
        this.itemIdPgBind = itemIdPgBind;
    }

    public RichOutputText getItemIdPgBind() {
        return itemIdPgBind;
    }

    public void setInputItemBind(RichInputListOfValues inputItemBind) {
        this.inputItemBind = inputItemBind;
    }

    public RichInputListOfValues getInputItemBind() {
        return inputItemBind;
    }

    public void setAllItemsDispPgBind(RichTable allItemsDispPgBind) {
        this.allItemsDispPgBind = allItemsDispPgBind;
    }

    public RichTable getAllItemsDispPgBind() {
        return allItemsDispPgBind;
    }

    public void setJcStatBindvar(RichInputText jcStatBindvar) {
        this.jcStatBindvar = jcStatBindvar;
    }

    public RichInputText getJcStatBindvar() {
        return jcStatBindvar;
    }

    public void setMachineStartTym(RichInputDate machineStartTym) {
        this.machineStartTym = machineStartTym;
    }

    public RichInputDate getMachineStartTym() {
        return machineStartTym;
    }

    public void setMachineEndTym(RichInputDate machineEndTym) {
        this.machineEndTym = machineEndTym;
    }

    public RichInputDate getMachineEndTym() {
        return machineEndTym;
    }

    public void setWsIdBindVar(RichInputListOfValues wsIdBindVar) {
        this.wsIdBindVar = wsIdBindVar;
    }

    public RichInputListOfValues getWsIdBindVar() {
        return wsIdBindVar;
    }

    public void setJobCardBasisBindVar(RichSelectOneChoice jobCardBasisBindVar) {
        this.jobCardBasisBindVar = jobCardBasisBindVar;
    }

    public RichSelectOneChoice getJobCardBasisBindVar() {
        return jobCardBasisBindVar;
    }

    public void setSourceDocDtBind(RichInputDate sourceDocDtBind) {
        this.sourceDocDtBind = sourceDocDtBind;
    }

    public RichInputDate getSourceDocDtBind() {
        return sourceDocDtBind;
    }

    public void setOutputDetailsFormBind(RichPanelFormLayout outputDetailsFormBind) {
        this.outputDetailsFormBind = outputDetailsFormBind;
    }

    public RichPanelFormLayout getOutputDetailsFormBind() {
        return outputDetailsFormBind;
    }

    public void setSaveBtnAction(RichLink saveBtnAction) {
        this.saveBtnAction = saveBtnAction;
    }

    public RichLink getSaveBtnAction() {
        return saveBtnAction;
    }

    public void setReleaseBtnBind(RichButton releaseBtnBind) {
        this.releaseBtnBind = releaseBtnBind;
    }

    public RichButton getReleaseBtnBind() {
        return releaseBtnBind;
    }

    public void setLotIdBindVar(RichInputText lotIdBindVar) {
        this.lotIdBindVar = lotIdBindVar;
    }

    public RichInputText getLotIdBindVar() {
        return lotIdBindVar;
    }

    public void setLotQtyBindVar(RichInputText lotQtyBindVar) {
        this.lotQtyBindVar = lotQtyBindVar;
    }

    public RichInputText getLotQtyBindVar() {
        return lotQtyBindVar;
    }

    public void setLotIdInSerial(RichInputText lotIdInSerial) {
        this.lotIdInSerial = lotIdInSerial;
    }

    public RichInputText getLotIdInSerial() {
        return lotIdInSerial;
    }

    public void setSerialNoBindVar(RichInputText serialNoBindVar) {
        this.serialNoBindVar = serialNoBindVar;
    }

    public RichInputText getSerialNoBindVar() {
        return serialNoBindVar;
    }

    public void setShopFloor_stockAvailablePgBind(RichOutputText shopFloor_stockAvailablePgBind) {
        this.shopFloor_stockAvailablePgBind = shopFloor_stockAvailablePgBind;
    }

    public RichOutputText getShopFloor_stockAvailablePgBind() {
        return shopFloor_stockAvailablePgBind;
    }

    public void setSelectSrNoPopUpBind_ShopFloor(RichPopup selectSrNoPopUpBind_ShopFloor) {
        this.selectSrNoPopUpBind_ShopFloor = selectSrNoPopUpBind_ShopFloor;
    }

    public RichPopup getSelectSrNoPopUpBind_ShopFloor() {
        return selectSrNoPopUpBind_ShopFloor;
    }

    public void setItmIssueSrBind_ShopFloor(RichOutputText itmIssueSrBind_ShopFloor) {
        this.itmIssueSrBind_ShopFloor = itmIssueSrBind_ShopFloor;
    }

    public RichOutputText getItmIssueSrBind_ShopFloor() {
        return itmIssueSrBind_ShopFloor;
    }

    public void setSelectLotPopUpBind_ShopFloor(RichPopup selectLotPopUpBind_ShopFloor) {
        this.selectLotPopUpBind_ShopFloor = selectLotPopUpBind_ShopFloor;
    }

    public RichPopup getSelectLotPopUpBind_ShopFloor() {
        return selectLotPopUpBind_ShopFloor;
    }

    public void setItmQtyIssueLblBind_ShopFloor(RichOutputText itmQtyIssueLblBind_ShopFloor) {
        this.itmQtyIssueLblBind_ShopFloor = itmQtyIssueLblBind_ShopFloor;
    }

    public RichOutputText getItmQtyIssueLblBind_ShopFloor() {
        return itmQtyIssueLblBind_ShopFloor;
    }

    public void setTotStkLotBind_ShopFloor(RichOutputText totStkLotBind_ShopFloor) {
        this.totStkLotBind_ShopFloor = totStkLotBind_ShopFloor;
    }

    public RichOutputText getTotStkLotBind_ShopFloor() {
        return totStkLotBind_ShopFloor;
    }

    public void setItmQtyIssueBinLblBind_ShopFloor(RichOutputText itmQtyIssueBinLblBind_ShopFloor) {
        this.itmQtyIssueBinLblBind_ShopFloor = itmQtyIssueBinLblBind_ShopFloor;
    }

    public RichOutputText getItmQtyIssueBinLblBind_ShopFloor() {
        return itmQtyIssueBinLblBind_ShopFloor;
    }

    public void setTotStkLotBinBind_ShopFloor(RichOutputText totStkLotBinBind_ShopFloor) {
        this.totStkLotBinBind_ShopFloor = totStkLotBinBind_ShopFloor;
    }

    public RichOutputText getTotStkLotBinBind_ShopFloor() {
        return totStkLotBinBind_ShopFloor;
    }

    public void setSelectLotBinPopUpBind_ShopFloor(RichPopup selectLotBinPopUpBind_ShopFloor) {
        this.selectLotBinPopUpBind_ShopFloor = selectLotBinPopUpBind_ShopFloor;
    }

    public RichPopup getSelectLotBinPopUpBind_ShopFloor() {
        return selectLotBinPopUpBind_ShopFloor;
    }

    public void setSwitcherLotBindVar(UIXSwitcher switcherLotBindVar) {
        this.switcherLotBindVar = switcherLotBindVar;
    }

    public UIXSwitcher getSwitcherLotBindVar() {
        return switcherLotBindVar;
    }

    public void setShowDetailAllItmTabBind(RichShowDetailItem showDetailAllItmTabBind) {
        this.showDetailAllItmTabBind = showDetailAllItmTabBind;
    }

    public RichShowDetailItem getShowDetailAllItmTabBind() {
        return showDetailAllItmTabBind;
    }

    public void setJobCardDispIdBind(RichInputText jobCardDispIdBind) {
        this.jobCardDispIdBind = jobCardDispIdBind;
    }

    public RichInputText getJobCardDispIdBind() {
        return jobCardDispIdBind;
    }

    public void setCreatedBybindVar(RichInputText createdBybindVar) {
        this.createdBybindVar = createdBybindVar;
    }

    public RichInputText getCreatedBybindVar() {
        return createdBybindVar;
    }

    public void setCreatedOnBindVar(RichInputDate createdOnBindVar) {
        this.createdOnBindVar = createdOnBindVar;
    }

    public RichInputDate getCreatedOnBindVar() {
        return createdOnBindVar;
    }

    public void setOutputItemNmBindVar(RichInputText outputItemNmBindVar) {
        this.outputItemNmBindVar = outputItemNmBindVar;
    }

    public RichInputText getOutputItemNmBindVar() {
        return outputItemNmBindVar;
    }

    /*----------------------Method for get Binding------------------*/
    OperationBinding ob = null;

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /*-------------------------Common Method For Faces Message------------------------*/
    public void Add_Faces_Message_RichInputListOfValues(RichInputListOfValues S1, String M1) {
        FacesMessage Message = new FacesMessage(M1);
        Message.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.addMessage(S1.getClientId(), Message);
    }

    public void Add_Faces_Message_RichInputText(RichInputText S2, String M2) {
        FacesMessage Message = new FacesMessage(M2);
        Message.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.addMessage(S2.getClientId(), Message);
    }

    /*--------------------------Method for disclosed panel tab--------------------*/
    public void setDisclosedFirstTab(RichShowDetailItem tabBind) {
        RichPanelTabbed richPanelTabbed = getPanelTabBinding();
        for (UIComponent child : richPanelTabbed.getChildren()) {
            RichShowDetailItem sdi = (RichShowDetailItem) child;
            if (sdi.getClientId().equals(tabBind.getClientId())) {
                sdi.setDisclosed(true);
            } else {
                sdi.setDisclosed(false);
            }
        }
    }

    /*---------------------------Validation for Enter Values(Required)------------------------*/
    public boolean requiredFields_Func() {
        System.out.println("Bean ");
        Boolean _retVal = false;
        ob = ADFBeanUtils.findOperation("chkValidateMethod");
        ob.execute();
        System.out.println("Error is " + ob.getErrors());
        Integer _chkVal = (Integer) ob.getResult();
        System.out.println("check Value is " + _chkVal);
        if (_chkVal == 1) {
            Add_Faces_Message_RichInputListOfValues(srcDocIdBindVar, "Please Select Source Document Id.");
        }   else if (_chkVal == 11) {
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mandatory",
                                                                          "Please Enter the Output Item Quantity."));
            }
        else if (_chkVal == 2) {
            Add_Faces_Message_RichInputListOfValues(opIdBindVar, "Please Select Operation Id.");
        } else if (_chkVal == 3) {
            Add_Faces_Message_RichInputListOfValues(wsIdBindVar, "Please Select Work Station.");
        } else if (_chkVal == 4) {
            Add_Faces_Message_RichInputListOfValues(shiftIdBindVar, "Please Select Shift.");
        } else if (_chkVal == 5) {
            Add_Faces_Message_RichInputListOfValues(empIdBindVar, "Please Select Employee.");
        } else if (_chkVal == 6) {
            Add_Faces_Message_RichInputListOfValues(reqAreaBindVar, "Please Select Requirement Area.");
        } else if (_chkVal == 7) {
            Add_Faces_Message_RichInputListOfValues(whIdBindVar, "Please Select Warehouse.");
        } else if (_chkVal == 8) {
            Add_Faces_Message_RichInputText(actOutputItmQtyBindVar, "Please Enter Actual Quantity.");
        } else if (_chkVal == 10) {
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mandatory",
                                                                          "Please Enter a output item."));
        } else if (_chkVal == 9) {

            if (this.getJobCardBasisBindVar().equals("144")||this.getJobCardBasisBindVar().equals("153")) {

                ob = ADFBeanUtils.findOperation("valAndUpAdhoc");
                ob.execute();
                Object obj = ob.getResult();

                if (obj != null) {
                    ArrayList<String> al = (ArrayList<String>) obj;
                    StringBuilder msg = new StringBuilder();

                    if (al.size() == 1) {
                        msg = msg.append(al.get(0));
                    } else {
                        Iterator iter = al.iterator();
                        while (iter.hasNext()) {
                            msg = msg.append("<P>" + iter.next() + "</P><br>");
                        }
                    }

                    ADFModelUtils.showFormattedFacesMessage("Job Card", msg.toString(), FacesMessage.SEVERITY_ERROR);

                } else {
                    _retVal = true;
                }

            } else {
                _retVal = true;
            }
        }
        System.out.println("Return value is " + _retVal);
        return _retVal;
    }

    /*----------------------------Save button Action Listner------------------*/
    public void saveACTION(ActionEvent actionEvent) {
        Integer stat=(Integer)getAttrsVal("MnfJc1Iterator","JcStat");
        Integer mode=(Integer)getAttrsVal("MnfJc1Iterator","JcMode");
        if(stat.equals(new Integer(72))&& mode.equals(new Integer(76))&&bindShortClose.isSelected()) {
           setAttrsVal("MnfJc1Iterator","JcStat",151);
            ADFBeanUtils.findOperation("Commit").execute();
            saveBtnAction.setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveBtnAction);
        }
        else{
        if (requiredFields_Func()) {
            if ((!(this.getJobCardBasisBindVar().getValue().toString().trim().equals("144")||this.getJobCardBasisBindVar().getValue().toString().trim().equals("153"))) &&
                "Y".equals(this.getChkForFetch().toString())) {
                StringBuilder message = new StringBuilder();
                message.append("Please Populate Operation Items Again.");
                ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(), FacesMessage.SEVERITY_INFO);
                setDisclosedFirstTab(showOperationItemTabBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(showOperationItemTabBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
            } else {
                System.out.println("Release Mode is " + this.getReleaseMode());

                if ("R".equals(this.getReleaseMode().toString())) {
                    Integer jcBasis=(Integer)this.getJobCardBasisBindVar().getValue();
                    if(jcBasis==153){
                     ADFBeanUtils.findOperation("insertIntoJobCardItemStk").execute();
                    }
                    
                    String outItm = CheckLotEntry_Func("CheckforOutputItmLot");

                    if (outItm.equals("checked")) {
                        String checkLotEntry = CheckLotEntry_Func("CheckforInputItmLot");
                        System.out.println("Value is " + checkLotEntry);
                        if (checkLotEntry.equalsIgnoreCase("checked")) {
                            ADFBeanUtils.findOperation("InsertWhAndReqAreaId").execute();
                           
                           //This function will insert MM and FIN LINE ENTRY.
                            String rt=onSaveRelease();
                            adfLog.info("value of rt:::"+rt);
                            if(rt.equalsIgnoreCase("true")){
                           
                            ob = ADFBeanUtils.findOperation("statusUpdate");
                            ob.execute();

                            if (this.getIsCCExist()) {
                                ob = ADFBeanUtils.findOperation("updateCostCenter");
                                ob.execute();
                            }
                            
                             adfLog.info("nomal commit1");
                            ADFBeanUtils.findOperation("Commit").execute();
                            showDetailsOutputitmStk.setDisabled(false);
                            }
                        } else {
                            StringBuilder message = new StringBuilder();
                            message.append("<P>Lot Quantity don't match the quantities allotted for the following Documents:</P><BR>");
                            message.append("<P style='font-weight:900'>" + this.jobCardDispIdBind.getValue() + "</P>");
                            message.append("<UL><LI> [ " + checkLotEntry + " ] </LI></UL>");
                            ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(),
                                                                    FacesMessage.SEVERITY_INFO);
                            setDisclosedFirstTab(showDetailAllItmTabBind);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailAllItmTabBind);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
                        }
                    } else {
                        StringBuilder message = new StringBuilder();
                        message.append("<P>Please Fill All the lot Quantity for Output Item:</P><BR>");
                        message.append("<P style='font-weight:900'>" + this.jobCardDispIdBind.getValue() + "</P>");
                        ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(),
                                                                FacesMessage.SEVERITY_INFO);
                    }
                } else {

                    if (ValidateAll()) {

                        if (this.getIsCCExist()) {
                            ob = ADFBeanUtils.findOperation("updateCostCenter");
                            ob.execute();
                        }
                       adfLog.info("nomal commit 2");
                        ob = ADFBeanUtils.findOperation("Commit");
                        Object execute = ob.execute();

                        if (execute == null) {

                            Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
                            pageFlowScope.put("JC_MODE", "V");
                            pageFlowScope.put("JC_PARAM_MODE","D");
                            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getLinkPanelGroup());
                            this.setEditClick(null);
                            StringBuilder message = new StringBuilder();
                            message.append("<P>Job Card Saved Successfully.</P><BR>");
                            message.append("<UL><LI>Job Card Id : " + this.jobCardDispIdBind.getValue() + "</LI>");
                            message.append("<LI>Created By : " + this.createdBybindVar.getValue() + "</LI>");
                            // message.append("<LI>Created Date : "+this.createdOnBindVar.getValue()+"</LI></UL>");
                            ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(),
                                                                    FacesMessage.SEVERITY_INFO);
                        }
                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        //updateDocOpStat_Func("N");
                        ADFBeanUtils.findOperation("InsertWhAndReqAreaId").execute();
                        callFuncWf();
                        adfLog.info("nomal commit 3");
                        ADFBeanUtils.findOperation("Commit").execute();
                        System.out.println("##########################################");
                        ob = ADFBeanUtils.findOperation("statusUpdate");
                        ob.execute();
                        //                        int out = Integer.parseInt(ob.getResult().toString());
                        //                        if (out != -1) {
                        //                            ADFBeanUtils.findOperation("Commit").execute();
                        //                        }
                    }
                }
            }
        }
        }
    }

    /*--------------Method for validate MnfJcOpitm Row Check------------------*/
    public boolean ValidateAll() {
        Boolean retval = false;
        DCIteratorBinding iter = ADFBeanUtils.findIterator("MnfJcOpOutputItmIterator");
        Row[] allRowsInRange = iter.getAllRowsInRange();
        System.out.println("Length :" + allRowsInRange.length);
        if (allRowsInRange.length > 0) {
            retval = true; // Did this if any conditions may come in further situation at the time use this
        } else {
            if (!(this.getJobCardBasisBindVar().getValue().toString().equals("144")||this.getJobCardBasisBindVar().getValue().toString().equals("153"))) {
                StringBuilder message = new StringBuilder();
                message.append("<P>Job card cannot be created with out Single output item.</P><BR>");
                ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(), FacesMessage.SEVERITY_INFO);


            } else {
                OperationBinding ob =
                    BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("valBfForward");
                ob.execute();
                String result = ob.getResult().toString();
                if (result.equals("Y")) {
                    retval = true;
                } else {
                    retval = false;
                    StringBuilder message = new StringBuilder();
                    message.append(result);
                    ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(), FacesMessage.SEVERITY_INFO);

                }
            }
        }
        System.out.println("Validate all : " + retval);
        return retval;
    }
    /*----------------------------Resolve Expression for PafeFlowScope----------------------*/
    public Object resolvEl(String data) {
        ADFContext adfCtx = ADFContext.getCurrent();
        ELContext elContext = adfCtx.getELContext();
        ValueExpression valueExp = adfCtx.getExpressionFactory().createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext);
        return Message;
    }

    /*----------------------------Get pageFlowScope Values----------------------------------*/
    public Integer getSlocId() {
        return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
    }

    public StringBuffer getCldId() {
        return new StringBuffer(resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString());
    }

    public StringBuffer getOrgId() {
        return new StringBuffer(resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString());
    }

    public StringBuffer getHoOrgId() {
        return new StringBuffer(resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString());
    }

    public Integer getUsrId() {
        return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
    }

    /*----------------------On cancel Action-------------------------*/
    public void onCANCEL(ActionEvent actionEvent) {
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("GLBL_DOC_TXN_ID", null);
        pageFlowScope.put("GLBL_JC_SR_ID", null);
    }

    /*---------------------on Edit----------------------------------*/
    public void onEDIT(ActionEvent actionEvent) {
        Integer usr_Id = getUsrId();
        Integer pending = 0;
        ob = ADFBeanUtils.findOperation("getDocUsrFromWF");
        ob.execute();
        Integer chkUsr = (Integer) ob.getResult();
        if (chkUsr != null) {
            pending = chkUsr;
        }
        if (pending.compareTo(usr_Id) == 0) {
            Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
            pageFlowScope.put("JC_MODE","E");
            pageFlowScope.put("JC_PARAM_MODE","E");
            this.setEditClick("E");
            ChkAutoConsumption();
            disableField();
        } else if (pending.compareTo(new Integer(-1)) == 0) {
            StringBuilder Msg22 = new StringBuilder();
            Msg22.append("<p>Job Card has been Approved, You can not edit Job Card</p>");
            ADFModelUtils.showFormattedFacesMessage("Job Card", Msg22.toString(), FacesMessage.SEVERITY_INFO);
        } else {
            ob = ADFBeanUtils.findOperation("getUserName");
            ob.getParamsMap().put("u_Id", pending);
            ob.getParamsMap().put("slc_id", getSlocId());
            ob.execute();
            if (ob.getResult() != null) {
                StringBuilder message1 = new StringBuilder();
                message1.append("<p>This Job Card is pending at other user<b><i> [ " + ob.getResult().toString() +
                                "] </i></b>for approval, You can not edit this.</p>");
                ADFModelUtils.showFormattedFacesMessage("Job Card", message1.toString(), FacesMessage.SEVERITY_INFO);
            }
        }
    }

    /*------------------------Getting Items In Table-------------------------*/
    public void populate_op_items(ActionEvent actionEvent) {
        if (requiredFields_Func()) {
            if ("R".equals(this.getReleaseMode().toString())) {
                ChkAutoConsumption();
                populateOpItems_Func();
                this.setChkForFetch("X");
                this.showDetailAllItmTabBind.setDisabled(false);
                this.showDetailsOutputitmStk.setDisabled(false);
                this.populateItmBindVar.setDisabled(true);
                this.actOutputItmQtyBindVar.setDisabled(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(actOutputItmQtyBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(outputDetailsFormBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(populateItmBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailAllItmTabBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailsOutputitmStk);
                AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
                executeBindings_Func();
            } else {
                this.getShowDetailAllItmTabBind().setDisabled(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailAllItmTabBind);
                ChkAutoConsumption();
                populateOpItems_Func();
                disableField();
                executeBindings_Func();
            }

            // To Update Cost center values for all items.
            if (this.getIsCCExist())
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("updateTempCostTable").execute();
        }
    }

    /*-----------------------------Execute Bindings and Insert Warehouse and Req Area Id Value------------------*/
    /* change by chirag as the Another user change the primary key error comes--*/ 
    public void executeBindings_Func() {
        adfLog.info("inthe executeBindings Function start");
        ADFBeanUtils.findOperation("ExecuteInputItems").execute();
        ADFBeanUtils.findOperation("ExecuteOutputItems").execute();
        ADFBeanUtils.findOperation("ExecuteAllOpItems").execute();
        ADFBeanUtils.findOperation("ExecuteCoProductsByProducts").execute();
        ADFBeanUtils.findOperation("InsertWhAndReqAreaId").execute();
        adfLog.info("in the executeBinding Function end");
    }

    /*------------------------Fetch Operation Item Function------------------------*/
    public void populateOpItems_Func() {
        ob = ADFBeanUtils.findOperation("PopulateITM");
        ob.getParamsMap().put("P_OP_SR_NO", Integer.parseInt(this.opSrNoBindVar.getValue().toString()));
        ob.execute();
    }

    /*------------------Create With Parameter in MNF$JC$PARAM--------------*/
    public void addOverhead(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("CreateWithParamsInOverHead").execute();
    }

    /*-------------------------delete Overhead--------------------*/
    public void deleteOverhead(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("DeleteOverhead").execute();
    }

    /*----------------------------parameter Name Validation-----------------------*/
    public void ParamNmValidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String paramNm = (String) object.toString();
        //ob = ADFBeanUtils.findOperation("chkDuplicateInParamNm");
        ob = ADFBeanUtils.findOperation("chkNmDuplicate");
        ob.getParamsMap().put("val", paramNm);
        ob.getParamsMap().put("Type", "O");
        ob.execute();
        Integer dulicateParam = Integer.parseInt(ob.getResult().toString());
        if (dulicateParam == 1) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Parameter Name already exist.",
                                                          null));
        } else {
            System.out.println("");
        }

    }

    /*-----------------------Create with parameter in mchine Downtime-----------*/
    public void MachineDTCreateWthParam(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("CreateWithParamsMDT").execute();
    }

    /*----------------------Delete Machine Downtime-----------------------*/
    public void deleteMachineDT(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("DeleteMachineDt").execute();
    }

    /*--------------------Duplicate Validation for Machine Downtime--------------------*/
    public void machineDTValidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String paramNm = (String) object.toString();
        //ob = ADFBeanUtils.findOperation("chkDuplicateInParamNmDT");
        ob = ADFBeanUtils.findOperation("chkNmDuplicate");
        ob.getParamsMap().put("val", paramNm);
        ob.getParamsMap().put("Type", "M");
        ob.execute();
        Integer dulicateParam = Integer.parseInt(ob.getResult().toString());
        if (dulicateParam == 1) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Parameter Name already exist.",
                                                          null));
        } else {
            System.out.println("");
        }
    }

    /*---------------------------------Variable Declaration for Upload File-------------------------------------*/
    InputStream inputStream = null;
    StringBuffer fileName = new StringBuffer();
    StringBuffer dirPath = getPath(); //new StringBuffer("D:\\DOCS"); //getPath(); //new StringBuffer("D:\\DOCS");
    ArrayList<UploadDoc> fileList = new ArrayList<UploadDoc>();

    /*-----------------------------GetPath for Upload Document-----------------------------*/
    public StringBuffer getPath() {
        /* StringBuffer path = new StringBuffer((String)callStoredFunction(Types.VARCHAR, " APP.FN_GET_APP_DOC_ATTACH_PATH(?)",
                                                                new Object[] { getSlocId() }));
                System.out.println("path " + path);
                if (path != null) {
                    System.out.println("return path when fucntion called sucessfully");
                    return path;
                } else {
                    System.out.println("return when fucntion not called successfully");
                    return new StringBuffer("D:\\DOCS");
                } */
        return new StringBuffer("D:\\DOCS");
    }


    /*--------------------------Action for file upload------------------------*/
    public void fileUpload(ActionEvent actionEvent) {
        if (fileDisplayname.equals("")) {
            Add_Faces_Message_RichInputText(attachFileNmBindVar, "Please enter Display name.");
        } else if (_File != null) {
            /*  System.out.println("_File value : " + _File);
                   System.out.println("fileDispName : " + fileDisplayname);
                   System.out.println("FILE_NAME :" + _File.getFilename());
                   System.out.println("File Content : " + _File.getContentType());
                   System.out.println("Dir path : " + dirPath + "\\" + fileName);
                   System.out.println("Value of inputstream in Block : " + inputStream); */
            fileList.add(new UploadDoc(_File.getFilename(), _File.getContentType().toString(),
                                       dirPath + "\\" + fileName,
                                       fileDisplayname +
                                       _File.getFilename().substring(_File.getFilename().lastIndexOf(".")), inputStream,
                                       _File.getFilename().substring(_File.getFilename().lastIndexOf("."))));

            for (UploadDoc list : fileList) {
                /*  System.out.println("----------------***************---------------");
                         System.out.println("File Name :" + _File.getFilename());
                         System.out.println("File Extension : " + list.getAttchedFileExtension());
                         System.out.println("File path : " + list.getAttchedFilePath());
                         System.out.println("File Display Name : " + list.getAttchedFileDispName());
                         System.out.println("File ext : " + list.getFileExt());
                         System.out.println("File input Stream : " + list.getFileInputStream()); */
                ob = ADFBeanUtils.findOperation("insertInMnfOpAttch");
                ob.getParamsMap().put("AttchFileExtn", list.getAttchedFileExtension());
                ob.getParamsMap().put("AttchFilepath", list.getAttchedFilePath());
                ob.getParamsMap().put("AttchExtn", list.getFileExt());
                ob.getParamsMap().put("DispFlNm", list.getAttchedFileDispName());
                //   ob.getParamsMap().put("DocId", getTxnId());
                ob.execute();
                String Nm = (String) ob.getResult();
                if (list.getFileInputStream() != null) {
                    try {
                        InputStream in = list.getFileInputStream();
                        FileOutputStream out = new FileOutputStream(list.getAttchedFilePath() + Nm + list.getFileExt());
                        byte[] buffer = new byte[8192];
                        int bytesRead = 0;
                        while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                            out.write(buffer, 0, bytesRead);
                        }
                        in.close();
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            attachFileNmBindVar.setValue("");
            _File.dispose();
            _File = null;
            fileList.clear();
        } else {
            FacesMessage Message = new FacesMessage("Please select a file.");
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(attachDocBindVar.getClientId(), Message);
        }
    }

    /*-------------------------Input File change listner-------------------*/
    public void inputFileListner(ValueChangeEvent vce) throws IOException {
        if (vce.getNewValue() != null) {
            UploadedFile file = (UploadedFile) vce.getNewValue();
            inputStream = file.getInputStream();
        }
    }

    /*--------------------------Document download---------------------------------*/
    public void downloadFile(FacesContext facesContext, OutputStream outputStream) throws IOException {
        //   String path = JSFUtils.resolveExpressionAsString("#{row.AttchFlPath}");
        String path = ADFModelUtils.resolvEl("#{row.AttchFlPath}").toString();
        File file = new File(path);
        FileInputStream fis;
        byte[] b;
        try {
            fis = new FileInputStream(file);
            int n;
            while ((n = fis.available()) > 0) {
                b = new byte[n];
                int result = fis.read(b);
                outputStream.write(b, 0, b.length);
                if (result == -1)
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        outputStream.flush();
    }

    /*--------------------------Delete Attachment--------------------*/
    public void deleteAttachment(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("DeleteAttch").execute();
    }

    /*----------------------Call Function callWfFunction----------------------*/
    public void callFuncWf() {
        callWfId();
        ob = ADFBeanUtils.findOperation("callWfFunctions");
        ob.execute();
    }

    /*---------------------Call callWfId Function------------------------------*/
    public void callWfId() {
        ob = ADFBeanUtils.findOperation("getWfId");
        ob.execute();
        String wId = (String) ob.getResult();
        setWfId(wId);
    }

    /*------------------------------Save and Forward action-----------------------*/
    public String saveAndForwardAction() {
        if (requiredFields_Func() && ValidateAll()) {

            //                int out = Integer.parseInt(ob.getResult().toString());
            //                if(out  != -1){
            //                ADFBeanUtils.findOperation("Commit").execute();
            //                }
            if (this.getIsCCExist()) {
                ob = ADFBeanUtils.findOperation("updateCostCenter");
                ob.execute();
            }
            ob = ADFBeanUtils.findOperation("Commit");
            //                ob = ADFBeanUtils.findOperation("statusUpdate");
            //                ob.execute();
            //                ADFBeanUtils.findOperation("Commit").execute();
            Object _Exe = ob.execute();
            if (_Exe == null) {
                Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
                pageFlowScope.put("JC_MODE", "V");
                pageFlowScope.put("JC_PARAM_MODE", "D");
                this.setEditClick(null);
            }
            //updateDocOpStat_Func("Y");
            ADFBeanUtils.findOperation("InsertWhAndReqAreaId").execute();
            
            DCIteratorBinding jciter = ADFBeanUtils.findIterator("MnfJc1Iterator");
            Integer loctype = (Integer)jciter.getCurrentRow().getAttribute("LocType");
            adfLog.info("value of Loctype:::"+loctype);
            
            if (loctype==4) {
                ob = ADFBeanUtils.findOperation("insertIntoSubContrator");
                ob.execute();
            }
            callFuncWf();
            ADFBeanUtils.findOperation("Commit").execute();
            return "WorkFlowGo";
        }
        return "0";

    }

    /*-----------------Update document Operation status change on Approval---------------------*/
    public void updateDocOpStat_Func(String _val) {
        ob = ADFBeanUtils.findOperation("UpdateDocumentOpStatus");
        ob.getParamsMap().put("Flag", _val);
        ob.execute();
    }

    /*--------------------------Autoconsumption check--------------------*/
    public void ChkAutoConsumption() {
        ob = ADFBeanUtils.findOperation("AutoConsumptionCheck");
        ob.execute();
        String auto = (String) ob.getResult();
        setAutoId(auto);
    }

    /*----------------------Disable Function--------------------*/
    public void disableField() {
        //this.saveBtnAction.setDisabled(true);
        adfLog.info("in the disabled fields function");
        if (this.getJobCardBasisBindVar().getValue() != null ||
            this.getJobCardBasisBindVar().getValue().toString().trim().equals("144")||this.getJobCardBasisBindVar().getValue().toString().trim().equals("153")) {
            this.srcDocIdBindVar.setReadOnly(true);
            //--To be modify by chirag
            this.populateItmBindVar.setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(populateItmBindVar);
        } else {
            this.populateItmBindVar.setDisabled(true);
            this.srcDocIdBindVar.setDisabled(true);
        }
        
        this.getJbTypeBind().setDisabled(true);
        this.opIdBindVar.setDisabled(true);
        this.shiftIdBindVar.setDisabled(true);
        this.empIdBindVar.setDisabled(true);
        this.reqAreaBindVar.setDisabled(true);
        this.whIdBindVar.setDisabled(true);
        this.wsIdBindVar.setDisabled(true);
        this.jobCardBasisBindVar.setDisabled(true);
        this.actOutputItmQtyBindVar.setDisabled(true);
    }
    /*----------------------Action for barcode generation--------------------*/
    public void GenBarcodeAction(ActionEvent actionEvent) {
        if (BarcodeNo.equals("")) {
            Add_Faces_Message_RichInputText(itmbracketNo, "Please Enter Barcode Bracket.");
        } else {
            ob = ADFBeanUtils.findOperation("GenBarcode");
            ob.getParamsMap().put("BcQty", Integer.parseInt(this.itmbracketNo.getValue().toString()));
            ob.execute();
            resetInputText("it10");
            this.barcode_Button.setDisabled(true);
            ADFBeanUtils.findOperation("ExecuteBarcode").execute();
        }
    }

    /*----------------------Function to get DocTxnId---------------------------*/
    public String DocTxnIdFunc() {
        ob = ADFBeanUtils.findOperation("getDocTxnId");
        ob.execute();
        String val = (String) ob.getResult();
        return val;
    }

    /*-----------------------Function to get Job card S. No---------------------*/
    public String JobcardSNO() {
        ob = ADFBeanUtils.findOperation("getJobCardSNO");
        ob.execute();
        String val1 = (String) ob.getResult();
        return val1;
    }

    /*-----------------------Current Date--------------------------------*/
    public Date CurrentDtFunc() {
        Date convertedDate = new Date(Date.getCurrentDate());
        return convertedDate;
    }

    /*---------------------Function to get FY_ID---------------------------*/
    public Integer FyIdFunc() {
        ob = ADFBeanUtils.findOperation("getFyId");
        ob.execute();
        Integer val2 = (Integer) ob.getResult();
        return val2;
    }
    /*--------------------------Add Job Card-----------------------------*/
    public void AddJobcardAction(ActionEvent actionEvent) {
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("JC_MODE", "C");
        pageFlowScope.put("JC_PARAM_MODE", "E");
        Date currntDt = CurrentDtFunc();
        //adfLog.info("value of fyid"+currntDt);
        pageFlowScope.put("GLBL_CURRENT_DATE", currntDt);
        String TxnId = DocTxnIdFunc();
        //adfLog.info("value of fyid 1"+TxnId);
        pageFlowScope.put("GLBL_DOC_TXN_ID", TxnId);
        Integer fyId = FyIdFunc();
        //adfLog.info("value of fyid 2"+fyId);
        pageFlowScope.put("GLBL_FY_ID", fyId);
        String JcSno = JobcardSNO();
        adfLog.info("value of fyid 3"+JcSno);

       // pageFlowScope.put("GLBL_FY_ID", fyId);
        pageFlowScope.put("GLBL_JC_SR_ID", JcSno);
        ADFBeanUtils.findOperation("CreateWithParamsInMnfJc1").execute();
        ADFBeanUtils.findOperation("CreateWithParamsInSRC").execute();
        this.getJobCardBasisBindVar().setDisabled(false);
        this.getSrcDocIdBindVar().setReadOnly(false);
        this.getSrcDocIdBindVar().setDisabled(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.opIdBindVar);
    }

    /*-------------------Action Release---------------*/
    public void ReleaseJobCard(ActionEvent actionEvent) {
       
        Integer jcBasis=(Integer)this.getJobCardBasisBindVar().getValue();
        adfLog.info("in the ReleaseJobCard"+jcBasis);
        if(jcBasis!=153){
        ADFBeanUtils.findOperation("CreateWithParamsInLotStk").execute();
        }
        if(jcBasis==153){
        showDetailsOutputitmStk.setDisabled(true);
        }
        DCIteratorBinding jciter = ADFBeanUtils.findIterator("MnfJc1Iterator");
                   Integer loctype = (Integer)jciter.getCurrentRow().getAttribute("LocType");
                   adfLog.info("value of Loctype from release:::"+loctype);
                   
                   if (loctype==4) {
                       ob = ADFBeanUtils.findOperation("insertIntoSubContrator");
                       ob.execute();
                   }
        this.setChkForFetch("Y");
        this.setReleaseMode("R");
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("JC_PARAM_MODE", "E");
        this.populateItmBindVar.setDisabled(false);
        this.actOutputItmQtyBindVar.setDisabled(false);
        this.releaseBtnBind.setDisabled(true);
        this.saveBtnAction.setDisabled(false);


        if (this.getJobCardBasisBindVar().getValue().toString().equals("144")||this.getJobCardBasisBindVar().getValue().toString().equals("153")) {

        } else {
            this.showDetailAllItmTabBind.setDisabled(true);
            this.showDetailsOutputitmStk.setDisabled(true);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(releaseBtnBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(actOutputItmQtyBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(outputDetailsFormBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(populateItmBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(saveBtnAction);
        AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailAllItmTabBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailsOutputitmStk);
        setDisclosedFirstTab(showOperationItemTabBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(showOperationItemTabBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
    }

    /*-------------------------Release On saveAction------------------*/
    public String onSaveRelease() {
        DCIteratorBinding iters = ADFBeanUtils.findIterator("MnfJcItmStkVOLotIterator");
        Row[] arIRs = iters.getAllRowsInRange();
        Integer Ris = (Integer) arIRs.length;
        if (Ris == 0) {
            StringBuilder m1 = new StringBuilder();
            m1.append("<P>Lot Quantity don't match the quantity of Output Item allotted for the following Document :</P><BR>");
            m1.append("<P style='font-weight:900'>" + this.jobCardDispIdBind.getValue() + "</P>");
            m1.append("<UL><LI> [ " + this.outputItemNmBindVar.getValue() + " ] </LI></UL>");
            ADFModelUtils.showFormattedFacesMessage("Job Card", m1.toString(), FacesMessage.SEVERITY_INFO);
            setDisclosedFirstTab(showDetailsOutputitmStk);
            AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailsOutputitmStk);
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
        } else {
            ob = ADFBeanUtils.findOperation("ChkOutputItmSerialized");
            ob.execute();
            String Schk = (String) ob.getResult();
            if (Schk.equals("Y")) {
                DCIteratorBinding iter = ADFBeanUtils.findIterator("MnfJcItmStkVOSerialIterator");
                Row[] arIR = iter.getAllRowsInRange();
                int val = arIR.length;
                Number Ri = new Number(val);
                Number Qtyi = (Number) ((Number) this.actOutputItmQtyBindVar.getValue()).round(2);
                if (Ri.compareTo(Qtyi) == 0) {
                    ADFBeanUtils.findOperation("deleteStkLot").execute();
                    String result=ReleaseStat();
                    adfLog.info("value of result::"+result);
                    if(result.equals("Y"))
                    return "true";

                } else {
                    StringBuilder m2 = new StringBuilder();
                    m2.append("<p>Output Item is Serialized. Please Enter Serial's for the following Document :</p><BR>");
                    m2.append("<P style='font-weight:900'>" + this.jobCardDispIdBind.getValue() + "</P>");
                    m2.append("<UL><LI> [ " + this.outputItemNmBindVar.getValue() + " ] </LI></UL>");
                    ADFModelUtils.showFormattedFacesMessage("Job Card", m2.toString(), FacesMessage.SEVERITY_INFO);
                    setDisclosedFirstTab(showDetailsOutputitmStk);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailsOutputitmStk);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
                }

            } else if (Schk.equals("N")) {
                String result1=ReleaseStat();
                adfLog.info("value of result1:"+result1);
                if(result1.equals("Y"))
                return "true";
                
            }
        }
        return "false";
    }

    public String ReleaseStat() {
//        //this is to be tested .................
//        ADFBeanUtils.findOperation("Commit").execute();
        ob = ADFBeanUtils.findOperation("ReleaseStatus");
        ob.execute();
        
        String Stat = (String) ob.getResult();
        adfLog.info("value of stat::"+Stat);
        if (Stat.equals("SUCCESS")) {
            StringBuilder m3 = new StringBuilder();
            m3.append("<p style='font-weight:bold;'>Job Card Released.</p><BR>");
            m3.append("<UL><LI>Job Card Id : " + this.jobCardDispIdBind.getValue() + "</LI>");
            m3.append("<LI>Created By : " + this.createdBybindVar.getValue() + "</LI>");
            // m3.append("<LI>Created Date : "+this.createdOnBindVar.getValue()+"</LI></UL>");
            ADFModelUtils.showFormattedFacesMessage("Job Card", m3.toString(), FacesMessage.SEVERITY_INFO);
            this.setReleaseMode("I");
            disableField();
            AdfFacesContext.getCurrentInstance().addPartialTarget(jcStatBindvar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveBtnAction);
            return "Y";
        } else if (Stat.equals("ERROR")) {
            StringBuilder m4 = new StringBuilder();
            m4.append("<p>Unable To Relese Job card..!!</p>");
            ADFModelUtils.showFormattedFacesMessage("Job Card", m4.toString(), FacesMessage.SEVERITY_ERROR);
            return "N";
        }
        return "N";
    }

    /*--------------------------------------Auto-------------------------------------------*/
    public void autoIssueItemAction(ActionEvent actionEvent) {
        Number stock = (Number) stockAvailablePgBind.getValue();
        Number avail = (Number) actualItemQuantityPgBind.getValue();
        if (stock.compareTo(avail) < 0) {
            StringBuilder message = new StringBuilder();
            message.append("<P>Cannot issue the Item because of less Stock or No Stock.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(), FacesMessage.SEVERITY_ERROR);
        } else {
            ADFBeanUtils.findOperation("IssueAutoItem").execute();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(switcherLotBindVar);
    }

    public void SerialNoForItmAL(ActionEvent actionEvent) {
        Number stock = (Number) stockAvailablePgBind.getValue();
        Number avail = (Number) actualItemQuantityPgBind.getValue();
        if (stock.compareTo(avail) < 0) {
            StringBuilder message = new StringBuilder();
            message.append("<P>Cannot issue the Item because of less Stock or No Stock.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(), FacesMessage.SEVERITY_ERROR);
        } else {
            ADFBeanUtils.findOperation("FilterSrNoAsPerItem").execute();
            showPopup(selectSrNoPopUpBind, true);
        }

    }

    public void SelectLotFrItmAction(ActionEvent actionEvent) {
        Number stock = (Number) stockAvailablePgBind.getValue();
        Number avail = (Number) actualItemQuantityPgBind.getValue();
        if (stock.compareTo(avail) < 0) {
            StringBuilder message = new StringBuilder();
            message.append("<P>Cannot issue the Item because of less Stock or No Stock.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(), FacesMessage.SEVERITY_ERROR);
        } else {
            ADFBeanUtils.findOperation("FilterLotWhWise").execute();
            showPopup(selectLotPopUpBind, true);
        }

    }

    public void SelectLotBinFrItmAction(ActionEvent actionEvent) {
        Number stock = (Number) stockAvailablePgBind.getValue();
        Number avail = (Number) actualItemQuantityPgBind.getValue();
        if (stock.compareTo(avail) < 0) {
            StringBuilder message = new StringBuilder();
            message.append("<P>Cannot issue the Item because of less Stock or No Stock.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(), FacesMessage.SEVERITY_ERROR);
        } else {
            ADFBeanUtils.findOperation("FilterBinWhWise").execute();
            showPopup(selectLotBinPopUpBind, true);
        }

    }


    /*----------------------------Pop Up bindings--------------------------------------*/
    public void setSelectSrNoPopUpBind(RichPopup selectSrNoPopUpBind) {
        this.selectSrNoPopUpBind = selectSrNoPopUpBind;
    }

    public RichPopup getSelectSrNoPopUpBind() {
        return selectSrNoPopUpBind;
    }

    public void setSelectLotPopUpBind(RichPopup selectLotPopUpBind) {
        this.selectLotPopUpBind = selectLotPopUpBind;
    }

    public RichPopup getSelectLotPopUpBind() {
        return selectLotPopUpBind;
    }

    public void setSelectLotBinPopUpBind(RichPopup selectLotBinPopUpBind) {
        this.selectLotBinPopUpBind = selectLotBinPopUpBind;
    }

    public RichPopup getSelectLotBinPopUpBind() {
        return selectLotBinPopUpBind;
    }

    /**
     * Method to open a popup using binding.
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


    /*-----------------------Breadcrumps Navigation listners-----------------------*/
    public void ViewLotActionListener(ActionEvent actionEvent) {
        //this.setFacetName("Lot");
        switcherLotBindVar.setFacetName("Lot");
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem) actionEvent.getComponent();
        rcnv.setVisited(true);
        ADFBeanUtils.findOperation("Execute1").execute();
    }

    public void ViewBinActionListener(ActionEvent actionEvent) {
        //this.setFacetName("Bin");
        switcherLotBindVar.setFacetName("Bin");
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem) actionEvent.getComponent();
        rcnv.setVisited(true);
        ADFBeanUtils.findOperation("Execute2").execute();
    }

    public void ViewSerialsActionListener(ActionEvent actionEvent) {
        //this.setFacetName("Sr");
        switcherLotBindVar.setFacetName("Sr");
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem) actionEvent.getComponent();
        rcnv.setVisited(true);
        ADFBeanUtils.findOperation("Execute3").execute();
    }

    public void SelectItmSrNoDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            DCIteratorBinding parentIter = ADFBeanUtils.findIterator("MnfJcOpItmAllIterator");
            Key parentKey = parentIter.getCurrentRow().getKey();
            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = ADFBeanUtils.findOperation("getTotalIssueQtySr");
            issueQty.execute();
            if (issueQty.getResult() != null) {
                issQty = (Number) issueQty.getResult();
                if (itmIssueSrBind.getValue() != null) {
                    pickQty = (Number) itmIssueSrBind.getValue();
                }
                if (issQty.compareTo(0) == 0) {
                    StringBuilder message = new StringBuilder();
                    message.append("<P>Please select quantity to issue.</P><BR>");
                    ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(),
                                                            FacesMessage.SEVERITY_ERROR);
                } else if (issQty.compareTo(pickQty) != 0) {
                    StringBuilder mes = new StringBuilder();
                    mes.append("<P>Issued Quantity is not equal to Ordered Quantity.</P><BR>");
                    ADFModelUtils.showFormattedFacesMessage("Job Card", mes.toString(), FacesMessage.SEVERITY_ERROR);
                } else {
                    ADFBeanUtils.findOperation("InsertIntoPickItmSr").execute();
                    /* ADFBeanUtils.findOperation("Execute1").execute();
                    ADFBeanUtils.findOperation("Execute2").execute();
                    ADFBeanUtils.findOperation("Execute3").execute();
                    ADFBeanUtils.findOperation("Execute4").execute();*/
                    AdfFacesContext.getCurrentInstance().addPartialTarget(lotPanel);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(switcherLotBindVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(lotTbl);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(binTbl);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(srcTbl);
                    if (parentKey != null) {
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                }
            }
        }
    }

    public void selectItmLotDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            DCIteratorBinding parentIter = ADFBeanUtils.findIterator("MnfJcOpItmAllIterator");
            Key parentKey = parentIter.getCurrentRow().getKey();
            //    System.out.println("Key before lot-" + parentKey);
            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = ADFBeanUtils.findOperation("getTotalIssueQtyLot");
            issueQty.execute();
            if (issueQty.getResult() != null) {
                issQty = (Number) issueQty.getResult();
                if (itmQtyIssueLblBind.getValue() != null) {
                    pickQty = (Number) itmQtyIssueLblBind.getValue();
                }
                if (issQty.compareTo(0) == 0) {
                    StringBuilder message = new StringBuilder();
                    message.append("<P>Please select quantity to issue.</P><BR>");
                    ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(),
                                                            FacesMessage.SEVERITY_ERROR);
                } else if (issQty.compareTo(pickQty) != 0) {
                    StringBuilder mes = new StringBuilder();
                    mes.append("<P>Issued Quantity is not equal to Ordered Quantity.</P><BR>");
                    ADFModelUtils.showFormattedFacesMessage("Job Card", mes.toString(), FacesMessage.SEVERITY_ERROR);
                } else {
                    ADFBeanUtils.findOperation("InsertIntoRcItmLot").execute();
                    /* ADFBeanUtils.findOperation("Execute1").execute();
                    ADFBeanUtils.findOperation("Execute2").execute();
                    ADFBeanUtils.findOperation("Execute3").execute();
                    ADFBeanUtils.findOperation("Execute4").execute(); */
                    AdfFacesContext.getCurrentInstance().addPartialTarget(lotPanel);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(switcherLotBindVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(lotTbl);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(binTbl);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(srcTbl);
                    if (parentKey != null) {
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                }
            }
        }
    }

    public void SelectItmLotBinDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            DCIteratorBinding parentIter = ADFBeanUtils.findIterator("MnfJcOpItmAllIterator");
            Key parentKey = parentIter.getCurrentRow().getKey();
            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = ADFBeanUtils.findOperation("getTotalIssueQtyBin");
            issueQty.execute();
            if (issueQty.getResult() != null) {
                issQty = (Number) issueQty.getResult();
                if (itmQtyIssueBinLblBind.getValue() != null) {
                    pickQty = (Number) itmQtyIssueBinLblBind.getValue();
                }
                if (issQty.compareTo(0) == 0) {
                    StringBuilder message = new StringBuilder();
                    message.append("<P>Please select quantity to issue.</P><BR>");
                    ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(),
                                                            FacesMessage.SEVERITY_ERROR);
                } else if (issQty.compareTo(pickQty) != 0) {
                    StringBuilder mes = new StringBuilder();
                    mes.append("<P>Issued Quantity is not equal to Ordered Quantity.</P><BR>");
                    ADFModelUtils.showFormattedFacesMessage("Job Card", mes.toString(), FacesMessage.SEVERITY_ERROR);
                } else {
                    ADFBeanUtils.findOperation("InsertIntoRcItmBin").execute();
                    /* ADFBeanUtils.findOperation("Execute1").execute();
                    ADFBeanUtils.findOperation("Execute2").execute();
                    ADFBeanUtils.findOperation("Execute3").execute();
                    ADFBeanUtils.findOperation("Execute4").execute(); */
                    AdfFacesContext.getCurrentInstance().addPartialTarget(lotPanel);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(switcherLotBindVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(lotTbl);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(binTbl);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(srcTbl);
                    if (parentKey != null) {
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                }
            }
        }
    }


    public void IssueQtyLotValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        try {
            if (object != null) {
                Number issueQty = new Number(object.toString());
                //Number issueQty = (Number) object;
                Number lotQty = new Number(0);
                if (totStkLotBind.getValue() != null) {
                    lotQty = (Number) totStkLotBind.getValue();
                    if (issueQty.compareTo(lotQty) == 1) {
                        throw new ValidatorException(new FacesMessage("Invalid Quantity",
                                                                      "Issue Quantity can not be more than available quantity"));
                    }
                }
                if (issueQty.compareTo(0) == -1) {
                    throw new ValidatorException(new FacesMessage("Invalid Quantity",
                                                                  "Issue Quantity can not be negative"));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void IssueQtyLotBinValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        try {
            if (object != null) {
                Number issueQty = new Number(object.toString());
                //Number issueQty = (Number) object;
                Number lotQty = new Number(0);
                if (totStkLotBinBind.getValue() != null) {
                    lotQty = (Number) totStkLotBinBind.getValue();
                    if (issueQty.compareTo(lotQty) == 1) {
                        throw new ValidatorException(new FacesMessage("Invalid Quantity",
                                                                      "Issue Quantity can not be more than available quantity"));
                    }
                }
                if (issueQty.compareTo(0) == -1) {
                    throw new ValidatorException(new FacesMessage("Invalid Quantity",
                                                                  "Issue Quantity can not be negative"));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    /**
     * Code for Precision Check
     */
    public Boolean isPrecisionValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    /*-------------------------------------All item tab Actiual Quantity Validation-----------------------------------*/
    public void allItemsActQtyValidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        try {
            if (object != null) {
                Number val = new Number(object.toString());
                //oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
                if (!isPrecisionValid(26, 6, val)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision. Please enter valid precision(26, 6).",
                                                                  null));
                }
                if (val.compareTo(0) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Negative numbers are not allowed.", null));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /*----------------------------Co Products Validation for Precision-------------------------------------*/
    public void coProdtsActlQtyValidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        try {
            if (object != null) {
                Number val = new Number(object.toString());
                // oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
                if (!isPrecisionValid(26, 6, val)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision. Please enter valid precision(26, 6).",
                                                                  null));
                }
                if (val.compareTo(0) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Negative numbers are not allowed.", null));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /*---------------------------------Overhead Value Validation---------------------------------*/
    public void overheadValueValidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer val = Integer.parseInt(object.toString());
            if (val < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Negative numbers were not allowed.", null));
            }
        }
    }

    /*-----------------------------Validation of Bracket No.------------------------------------*/
    public void BracktNoValidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer val = Integer.parseInt(object.toString());
            if (val < 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Negative numbers were not allowed.", null));
            }
        }
    }

    /*---------------------------Search Item All Action listner----------------------*/
    public void SearchInputItemAL(ActionEvent actionEvent) {
        if (itemIdPgBind.getValue() != null) {
            String itmName = itemIdPgBind.getValue().toString();
            setSearchValue(itmName);
            DCIteratorBinding it = ADFBeanUtils.findIterator("MnfJcOpItmAllIterator");
            RowSetIterator rsi = it.getRowSetIterator();
            RowKeySet oldSelection = allItemsDispPgBind.getSelectedRowKeys();
            if (rsi.first() != null) {
                Row r = rsi.first();
                while (rsi.hasNext() && getKey() == null && (!matchFoundInput(r, oldSelection))) {
                    r = rsi.next();
                }
            }
        }
    }

    private boolean matchFoundInput(Row r, RowKeySet oldSelection) {
        setKey(null);
        ArrayList lst = new ArrayList(1);
        RowKeySetImpl newSelection = new RowKeySetImpl();
        Key key = null;
        String rowValue = (String) r.getAttribute("ItmId");
        if (rowValue.toString().contains(searchValue)) {
            key = r.getKey();
            lst.add(key);
            allItemsDispPgBind.setActiveRowKey(lst);
            newSelection.add(lst);
            makeCurrentInput(allItemsDispPgBind, newSelection, oldSelection);
            return true;
        }
        return false;
    }

    private void makeCurrentInput(RichTable outputItemTableBind, RowKeySet newCurrentRow, RowKeySet oldCurrentRow) {
        //To make a row current, we need to create a SelectionEvent which
        //expects the following arguments: component, unselected_keys,
        //selected_keys. In our example, we don't have unselected keys and
        //therefore create an empty RowSet for this
        SelectionEvent selectionEvent = new SelectionEvent(oldCurrentRow, newCurrentRow, outputItemTableBind);
        selectionEvent.queue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(outputItemTableBind);
    }

    /*--------------------------------Validate Date Job card---------------------------*/
    public void validateJcDate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        try {
            Timestamp t = (Timestamp) object;
            ob = ADFBeanUtils.findOperation("validDateJc");
            java.sql.Date d = t.dateValue();
            ob.getParamsMap().put("doc_dt", d);
            ob.execute();
            String vl = (String) ob.getResult();
            if (vl.equals("N")) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Job Card can not be created on the specified date.",
                                                              null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void srcDocVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (vce.getNewValue() != null) {
            ADFBeanUtils.findOperation("opIdExe").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(sourceDocDtBind);
            //     AdfFacesContext.getCurrentInstance().addPartialTarget(opIdBindVar);
        }
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    /*-----------------------------Actual Itm Validate-----------------------------*/
    public void ActualOutputItmValidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        try {
            if (object != null) {
                Number val = new Number(object.toString());
                //oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
                ob = ADFBeanUtils.findOperation("ChkOutputItmSerialized");
                ob.execute();
                if ("Y".equals(ob.getResult().toString())) {
                    String s = String.valueOf(val);
                    if (!s.matches("[0-9]")) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Output Item is serialized, Please enter quantity in Integer value.",
                                                                      null));
                    }
                    if (val.compareTo(0) == 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Quantity should be greater than Zero.", null));
                    }
                } else {
                    if (!isPrecisionValid(26, 6, val)) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Invalid Precision. Please enter valid precision(26, 6).",
                                                                      null));
                    }
                    if (val.compareTo(0) < 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Negative numbers are not allowed.", null));
                    }
                    if (val.compareTo(0) == 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Quantity should be greater than Zero.", null));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /*--------------------Opreration Id Value change listner-----------------*/
    public void operationVCL(ValueChangeEvent valueChangeEvent) {
        Object val=valueChangeEvent.getNewValue();
        adfLog.info("val::"+val);
        if(val!=null)
        {
        ob = ADFBeanUtils.findOperation("setDefaultSubContracterName");
        ob.getParamsMap().put("OpNo",val.toString());
        ob.execute();
        }
        this.jobCardBasisBindVar.setDisabled(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(jobCardBasisBindVar);
        this.srcDocIdBindVar.setDisabled(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(srcDocIdBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(outputDetailsFormBind);

    }

    /*------------------Value change listner for Actual output Qty------------------------*/
    public void actualOutputVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            this.setChkForFetch("Y");

            // To update all Input values to table
            if (this.getIsCCExist())
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("updateTempCostTable").execute();
        }
    }

    /*------------------------------Add Lot For Output Item----------------------------*/
    public void Add_Lot_Action(ActionEvent actionEvent) {
        DCIteratorBinding iter = ADFBeanUtils.findIterator("MnfJcItmStkVOLotIterator");
        Row[] arIR = iter.getAllRowsInRange();
        Integer Ri = (Integer) arIR.length;
        if (Ri == 1) {
            ob = ADFBeanUtils.findOperation("GetlotNo");
            ob.execute();
            String lotNo = (String) ob.getResult();
            this.setLotNo(lotNo);
            this.setLotQty(this.actOutputItmQtyBindVar.getValue().toString());
            showPopup(addLotPopUp, true);
        }
    }
    /*-------------------------------Add Lot Dialog Listner----------------------------*/
    public void addLotDialogListner(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            ob = ADFBeanUtils.findOperation("AddLotEntry");
            ob.getParamsMap().put("lotId", this.lotIdBindVar.getValue().toString());
            ob.getParamsMap().put("lotQty", this.lotQtyBindVar.getValue());
            ob.execute();
            //ADFBeanUtils.findOperation("ExecuteLot").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelColLotTableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotIdInSerial);
            this.fg_sfgradioBindVar.setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(fg_sfgradioBindVar);
            this.add_editLotBtnBindVar.setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(add_editLotBtnBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(serialNoBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(addSerialbtn);
            
        }
    }

    /*------------------------------------Add Serial Action Listner--------------------*/
    public void Add_Serial_Action(ActionEvent actionEvent) {
        if (serialNo.equals("")) {
            Add_Faces_Message_RichInputText(serialNoBindVar, "Please Enter Serial Number.");
        } else {
            DCIteratorBinding iter = ADFBeanUtils.findIterator("MnfJcItmStkVOSerialIterator");
            Row[] arIR = iter.getAllRowsInRange();
            int val = arIR.length;
            Number Ri = new Number(val);
            Number Qtyi = (Number) ((Number) this.actOutputItmQtyBindVar.getValue()).round(2);
            if (Ri.compareTo(Qtyi) == 0) {
                StringBuilder message = new StringBuilder();
                message.append("<P>No. of Serial No Exceeds the Quantity.</P><BR>");
                ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(), FacesMessage.SEVERITY_INFO);
            } else {
                ob = ADFBeanUtils.findOperation("AddSerialEntry");
                ob.getParamsMap().put("lotId", this.lotIdInSerial.getValue().toString());
                ob.getParamsMap().put("serial", this.serialNoBindVar.getValue().toString());
                ob.execute();
                //ADFBeanUtils.findOperation("ExecuteSerial").execute();
                AdfFacesContext.getCurrentInstance().addPartialTarget(panelColSerialTableBind);
                //resetInputText("it28");
                serialNoBindVar.setValue("");
                serialNoBindVar.setSubmittedValue(null);
                serialNoBindVar.resetValue();
                setSerialNo("");
                AdfFacesContext.getCurrentInstance().addPartialTarget(serialNoBindVar);
            }
        }
    }

    /*--------------------Reset Input Text Box Method-------------------------*/
    private void resetInputText(String id) {
        RichInputText input = (RichInputText) findComponentInRoot(id);
        input.setSubmittedValue(null);
        input.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(input);
    }

    /**
     * Locate an UIComponent in view root with its component id. Use a recursive way to achieve this.
     * @param id UIComponent id
     * @return UIComponent object
     */
    public static UIComponent findComponentInRoot(String id) {
        UIComponent component = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            UIComponent root = facesContext.getViewRoot();
            component = findComponent(root, id);
        }
        return component;
    }

    /**
     * Locate an UIComponent from its root component.
     * @param base root Component (parent)
     * @param id UIComponent id
     * @return UIComponent object
     */
    public static UIComponent findComponent(UIComponent base, String id) {
        if (id.equals(base.getId()))
            return base;

        UIComponent children = null;
        UIComponent result = null;
        Iterator childrens = base.getFacetsAndChildren();
        while (childrens.hasNext() && (result == null)) {
            children = (UIComponent) childrens.next();
            if (id.equals(children.getId())) {
                result = children;
                break;
            }
            result = findComponent(children, id);
            if (result != null) {
                break;
            }
        }
        return result;
    }
    /*-----------------------------------Total Quantity Validator-----------------------------------*/

    public void TotalOutputItmQty_Validate(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (outputItemNmBindVar.getValue() == null || outputItemNmBindVar.getValue().toString().trim().length() == 0)
            return;

        Number pendingVal = (Number) pendingQty.getValue();
        Number val = null;
        //try{
        if (object != null) {
            val = (Number) object;
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Quantity can not be blank.",
                                                          null));
        }
        //}catch(Exception e){System.out.println(e);}
        //oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
        //ob = ADFBeanUtils.findOperation("ChkOutputItmSerialized");
        //ob.execute();
        /*   if ("Y".equals(ob.getResult().toString())) {
                String s = String.valueOf(val);
                if (!s.matches("[0-9]")) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Output Item is serialized, Please enter quantity in Integer value.",
                                                                  null));
                }
                if (val.compareTo(0) == 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Quantity should be greater than Zero.", null));
                }
            } else { */
        if (!isPrecisionValid(26, 6, val)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Invalid Precision. Please enter valid precision(26, 6).",
                                                          null));
        }
        if (val.compareTo(0) < 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Negative numbers are not allowed.", null));
        }
        if (val.compareTo(0) == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          "Quantity should be greater than Zero.", null));
        }

        if (val.compareTo(pendingVal) > 0) {
            if (!(this.getJobCardBasisBindVar().getValue().toString().equals("144")||this.getJobCardBasisBindVar().getValue().toString().equals("153"))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Quantity can not be greater than Pending Quantity.",
                                                              null));

                }
            }
           OperationBinding ob=ADFBeanUtils.findOperation("isItemSerialized");
               ob.execute();
               adfLog.info("isItemSerialized::"+ob.getResult());
               if(ob.getResult().toString().equalsIgnoreCase("Y")) {
                   if (!isPrecisionValid(20,0, val)) {
                       throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                     "Output Item is serialized you can not enter quantity in decimal.",
                                                                     null));
                   }
               }
        //   }
        // }
    }

    /*-------------------------------Output Itm Qty Value Change Listner---------------------*/
    public void OutputItm_Qty_VCL(ValueChangeEvent valueChangeEvent) {
        //ob = ADFBeanUtils.findOperation("checkQtyValue");
        //ob.execute();
        //Number n = (Number)ob.getResult();
        //System.out.println("Value of N : "+n);
        /* if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) <=n.intValue() ){
            this.actOutputItmQtyBindVar.setValue(valueChangeEvent.getNewValue());
            AdfFacesContext.getCurrentInstance().addPartialTarget(actOutputItmQtyBindVar);
        }else{
                //Add_Faces_Message_RichInputText(opoutputItmQty, "Quantity should be less than or equal to "+n);
                ADFModelUtils.showFormattedFacesMessage("Job Card", "Quantity should be less than or equal to PDO quantity "+n, FacesMessage.SEVERITY_WARN);
            opoutputItmQty.setValue(n);
        } */
        Object obj = valueChangeEvent.getNewValue();
        if (obj == null) {
            //Number pendingVal = (Number)pendingQty.getValue();
            //opoutputItmQty.setValue(pendingVal);
//            Number val=(Number)obj;
//            Number per=(Number)getAttrsVal("MnfJc1Iterator","TransOpYieldPer");
//            Number calcVal=(val.multiply(per)).divide(new Number(100));
//        adfLog.info("CalCulated val::"+calcVal+"Value of per:"+per);
            
            this.actOutputItmQtyBindVar.setValue(valueChangeEvent.getNewValue());
            //this.actOutputItmQtyBindVar.setValue(calcVal);
            AdfFacesContext.getCurrentInstance().addPartialTarget(actOutputItmQtyBindVar);
        }
        if (obj != null) {
            //Number pendingVal = (Number)pendingQty.getValue();
            //opoutputItmQty.setValue(pendingVal);
            Number val=(Number)obj;
            Number per=(Number)getAttrsVal("MnfJc1Iterator","TransOpYieldPer");
            adfLog.info(obj+"value of per::"+per);
            Number calcVal=(val.multiply(per)).divide(new Number(100));
        //adfLog.info("CalCulated val::"+calcVal+"Value of per:"+per);
            
            //this.actOutputItmQtyBindVar.setValue(valueChangeEvent.getNewValue());
            this.actOutputItmQtyBindVar.setValue(calcVal);
            AdfFacesContext.getCurrentInstance().addPartialTarget(actOutputItmQtyBindVar);
        }
    }


    /*------------------------Auto Issue In Item Lot, Bin Serial In Manual(25)---------------*/
    public void autoIssueItem_Manual(ActionEvent actionEvent) {
        Number stock = (Number) shopFloor_stockAvailablePgBind.getValue();
        Number avail = (Number) actualItemQuantityPgBind.getValue();
        if (stock.compareTo(avail) < 0) {
            StringBuilder message = new StringBuilder();
            message.append("<P>Cannot issue the Item because of less Stock or No Stock.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(), FacesMessage.SEVERITY_ERROR);
        } else {
            ADFBeanUtils.findOperation("IssueAutoItemFromShopFloor").execute();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(switcherLotBindVar);
    }

    /*------------------------SerialNo In Item Lot, Bin Serial In Manual(25)---------------*/
    public void SerialNoForItmAL_Manual(ActionEvent actionEvent) {
        Number stock = (Number) shopFloor_stockAvailablePgBind.getValue();
        Number avail = (Number) actualItemQuantityPgBind.getValue();
        if (stock.compareTo(avail) < 0) {
            StringBuilder message = new StringBuilder();
            message.append("<P>Cannot issue the Item because of less Stock or No Stock.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(), FacesMessage.SEVERITY_ERROR);
        } else {
            ADFBeanUtils.findOperation("FilterSrNoAsPerItemSF").execute();
            showPopup(selectSrNoPopUpBind_ShopFloor, true);
        }
    }

    /*-------------------Select Serial Dialog Listner for Shop Floor------------------------*/
    public void SelectItmSrNoDialogListener_ShopFloor(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            DCIteratorBinding parentIter = ADFBeanUtils.findIterator("MnfJcOpItmAllIterator");
            Key parentKey = parentIter.getCurrentRow().getKey();
            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = ADFBeanUtils.findOperation("getTotalIssueQtySrSF");
            issueQty.execute();
            if (issueQty.getResult() != null) {
                issQty = (Number) issueQty.getResult();
                if (itmIssueSrBind_ShopFloor.getValue() != null) {
                    pickQty = (Number) itmIssueSrBind_ShopFloor.getValue();
                }
                if (issQty.compareTo(0) == 0) {
                    StringBuilder m5 = new StringBuilder();
                    m5.append("<P>Please select quantity to issue.</P><BR>");
                    ADFModelUtils.showFormattedFacesMessage("Job Card", m5.toString(), FacesMessage.SEVERITY_ERROR);
                } else if (issQty.compareTo(pickQty) != 0) {
                    StringBuilder m6 = new StringBuilder();
                    m6.append("<P>Issued Quantity is not equal to Ordered Quantity.</P><BR>");
                    ADFModelUtils.showFormattedFacesMessage("Job Card", m6.toString(), FacesMessage.SEVERITY_ERROR);
                } else {
                    ADFBeanUtils.findOperation("InsertIntoPickItmSrSF").execute();
                    /* ADFBeanUtils.findOperation("Execute1").execute();
                    ADFBeanUtils.findOperation("Execute2").execute();
                    ADFBeanUtils.findOperation("Execute3").execute();
                    ADFBeanUtils.findOperation("Execute4").execute(); */
                    AdfFacesContext.getCurrentInstance().addPartialTarget(lotPanel);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(switcherLotBindVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(lotTbl);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(binTbl);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(srcTbl);
                    if (parentKey != null) {
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                }
            }
        }
    }

    /*------------------------Select Lot In Item Lot, Bin Serial In Manual(25)---------------*/
    public void SelectLotFrItmAction_Manual(ActionEvent actionEvent) {
        Number stock = (Number) shopFloor_stockAvailablePgBind.getValue();
        Number avail = (Number) actualItemQuantityPgBind.getValue();
        if (stock.compareTo(avail) < 0) {
            StringBuilder message = new StringBuilder();
            message.append("<P>Cannot issue the Item because of less Stock or No Stock.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(), FacesMessage.SEVERITY_ERROR);
        } else {
            ADFBeanUtils.findOperation("FilterLotWhWiseSF").execute();
            showPopup(selectLotPopUpBind_ShopFloor, true);
        }
    }

    /*----------------------------Lot Dialog Listner for Manual(Shop Floor)-----------------------*/
    public void selectItmLotDialogListener_ShopFloor(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            DCIteratorBinding parentIter = ADFBeanUtils.findIterator("MnfJcOpItmAllIterator");
            Key parentKey = parentIter.getCurrentRow().getKey();
            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = ADFBeanUtils.findOperation("getTotalIssueQtyLotSF");
            issueQty.execute();
            if (issueQty.getResult() != null) {
                issQty = (Number) issueQty.getResult();
                if (itmQtyIssueLblBind_ShopFloor.getValue() != null) {
                    pickQty = (Number) itmQtyIssueLblBind_ShopFloor.getValue();
                }
                if (issQty.compareTo(0) == 0) {
                    StringBuilder m5 = new StringBuilder();
                    m5.append("<P>Please select quantity to issue.</P><BR>");
                    ADFModelUtils.showFormattedFacesMessage("Job Card", m5.toString(), FacesMessage.SEVERITY_ERROR);
                } else if (issQty.compareTo(pickQty) != 0) {
                    StringBuilder m6 = new StringBuilder();
                    m6.append("<P>Issued Quantity is not equal to Ordered Quantity.</P><BR>");
                    ADFModelUtils.showFormattedFacesMessage("Job Card", m6.toString(), FacesMessage.SEVERITY_ERROR);
                } else {
                    System.out.println("Else insert function . . . . ");
                    ADFBeanUtils.findOperation("InsertIntoRcItmLotSF").execute();
                    /* ADFBeanUtils.findOperation("Execute1").execute();
                    ADFBeanUtils.findOperation("Execute2").execute();
                    ADFBeanUtils.findOperation("Execute3").execute();
                    ADFBeanUtils.findOperation("Execute4").execute(); */
                    AdfFacesContext.getCurrentInstance().addPartialTarget(lotPanel);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(switcherLotBindVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(lotTbl);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(binTbl);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(srcTbl);
                    if (parentKey != null) {
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                }
            }
        }
    }

    /*-------------------------Validator for Issue Qty In Shop Floor---------------------------------*/
    public void IssueQtyLot_SF_Validator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            try {
                Number issueQty = new Number(object.toString());
                //Number issueQty = (Number) object;
                Number lotQty = new Number(0);

                if (totStkLotBind_ShopFloor.getValue() != null) {
                    lotQty = (Number) totStkLotBind_ShopFloor.getValue();
                    if (issueQty.compareTo(lotQty) == 1) {
                        throw new ValidatorException(new FacesMessage("Invalid Quantity",
                                                                      "Issue Quantity can not be more than available quantity"));
                    }
                }
                if (issueQty.compareTo(0) == -1) {
                    throw new ValidatorException(new FacesMessage("Invalid Quantity",
                                                                  "Issue Quantity can not be negative"));
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /*------------------------Select Bin In Item Lot, Bin Serial In Manual(25)---------------*/
    public void SelectLotBinFrItmAction_Manual(ActionEvent actionEvent) {
        Number stock = (Number) shopFloor_stockAvailablePgBind.getValue();
        Number avail = (Number) actualItemQuantityPgBind.getValue();
        if (stock.compareTo(avail) < 0) {
            StringBuilder message = new StringBuilder();
            message.append("<P>Cannot issue the Item because of less Stock or No Stock.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(), FacesMessage.SEVERITY_ERROR);
        } else {
            ADFBeanUtils.findOperation("FilterBinWhWiseSF").execute();
            System.out.println("First");
            showPopup(selectLotBinPopUpBind_ShopFloor, true);
            System.out.println("End");
        }

    }

    /*----------------------Dialog Listner for Bin In SHop Floor--------------------------*/
    public void SelectItmLotBinDialogListener_ShopFloor(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            DCIteratorBinding parentIter = ADFBeanUtils.findIterator("MnfJcOpItmAllIterator");
            Key parentKey = parentIter.getCurrentRow().getKey();
            Number issQty = new Number(0);
            Number pickQty = new Number(0);
            OperationBinding issueQty = ADFBeanUtils.findOperation("getTotalIssueQtyBinSF");
            issueQty.execute();
            if (issueQty.getResult() != null) {
                issQty = (Number) issueQty.getResult();
                if (itmQtyIssueBinLblBind_ShopFloor.getValue() != null) {
                    pickQty = (Number) itmQtyIssueBinLblBind_ShopFloor.getValue();
                }
                if (issQty.compareTo(0) == 0) {
                    StringBuilder m5 = new StringBuilder();
                    m5.append("<P>Please select quantity to issue.</P><BR>");
                    ADFModelUtils.showFormattedFacesMessage("Job Card", m5.toString(), FacesMessage.SEVERITY_ERROR);
                } else if (issQty.compareTo(pickQty) != 0) {
                    StringBuilder ms = new StringBuilder();
                    ms.append("<P>Issued Quantity is not equal to Ordered Quantity.</P><BR>");
                    ADFModelUtils.showFormattedFacesMessage("Job Card", ms.toString(), FacesMessage.SEVERITY_ERROR);
                } else {
                    ADFBeanUtils.findOperation("InsertIntoRcItmBinSF").execute();
                    /* ADFBeanUtils.findOperation("Execute1").execute();
                    ADFBeanUtils.findOperation("Execute2").execute();
                    ADFBeanUtils.findOperation("Execute3").execute();
                    ADFBeanUtils.findOperation("Execute4").execute(); */
                    AdfFacesContext.getCurrentInstance().addPartialTarget(lotPanel);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(switcherLotBindVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(lotTbl);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(binTbl);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(srcTbl);
                    if (parentKey != null) {
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                }
            }
        }
    }

    public void IssueQtyLotBin_SF_Validator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number issueQty = (Number) object;
            Number lotQty = new Number(0);
            if (totStkLotBinBind_ShopFloor.getValue() != null) {
                lotQty = (Number) totStkLotBinBind_ShopFloor.getValue();
                if (issueQty.compareTo(lotQty) == 1) {
                    throw new ValidatorException(new FacesMessage("Invalid Quantity",
                                                                  "Issue Quantity can not be more than available quantity"));
                }
            }
            if (issueQty.compareTo(0) == -1) {
                throw new ValidatorException(new FacesMessage("Invalid Quantity",
                                                              "Issue Quantity can not be negative"));
            }
        }
    }

    /* public String CheckLotEntry_Func() {
        ob = ADFBeanUtils.findOperation("CheckforInputItmLot");
        ob.execute();
        Object oj = ob.getResult();
        return oj.toString();
    } */
    public String CheckLotEntry_Func(String type) {
        ob = ADFBeanUtils.findOperation(type);
        ob.execute();
        Object oj = ob.getResult();
        System.out.println("Valid or Not : " + oj.toString());
        return oj.toString();
    }

    /*-------------------------Source doc Id Validator------------------------------*/
    public void sourceDocIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String idSrc = (String) object.toString();
            ob = ADFBeanUtils.findOperation("checkSourceDocIdValidate");
            ob.getParamsMap().put("SrcDispId", idSrc);
            ob.execute();
            if (ob.getResult().toString().equals("Y")) {
                throw new ValidatorException(new FacesMessage("PDO already used in Route Card."));
            }
        } else {
            if(this.jobCardBasisBindVar.getValue().toString().trim().equals("144")){
                System.out.println("It is Job Card ");
            }else{
                    throw new ValidatorException(new FacesMessage("PDO is mandatory."));
            }
        }
    }

    public void setAdd_editLotBtnBindVar(RichButton add_editLotBtnBindVar) {
        this.add_editLotBtnBindVar = add_editLotBtnBindVar;
    }

    public RichButton getAdd_editLotBtnBindVar() {
        return add_editLotBtnBindVar;
    }

    public void setLotPanel(RichPanelBox lotPanel) {
        this.lotPanel = lotPanel;
    }

    public RichPanelBox getLotPanel() {
        return lotPanel;
    }

    public void setLotTbl(RichTable lotTbl) {
        this.lotTbl = lotTbl;
    }

    public RichTable getLotTbl() {
        return lotTbl;
    }

    public void setBinTbl(RichTable binTbl) {
        this.binTbl = binTbl;
    }

    public RichTable getBinTbl() {
        return binTbl;
    }

    public void setSrcTbl(RichTable srcTbl) {
        this.srcTbl = srcTbl;
    }

    public RichTable getSrcTbl() {
        return srcTbl;
    }

    public void chkOutputStkAction(DisclosureEvent disclosureEvent) {
        adfLog.info("chkOutputStkAction is called..");
        if (!resolvEl("#{pageFlowScope.JC_MODE}").toString().equals("V")) {
            String output = CheckLotEntry_Func("CheckforInputItmLot");
            if (!(output.equals("checked"))) {
                StringBuilder message = new StringBuilder();
                message.append("<P>Please Fill All the lot Quantity for :</P><BR>");
                message.append(output);
                ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(), FacesMessage.SEVERITY_INFO);
                setDisclosedFirstTab(showDetailAllItmTabBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(showDetailAllItmTabBind);
                AdfFacesContext.getCurrentInstance().addPartialTarget(panelTabBinding);
            }
        }
    }

    public void serialValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            ob = ADFBeanUtils.findOperation("chkSrDuplicate");
            ob.getParamsMap().put("val", object.toString());
            ob.execute();
            int i = (Integer) ob.getResult();
            System.out.println("Return Value :  " + i);
            if (i == 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Serial Name already exist.",
                                                              null));
            }
        }
    }

    public void addQcParamAction(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("CreateWithParamsQcParam").execute();
    }

    public void deleteQcParamAction(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("DeleteQcParam").execute();
    }

    public void setDocIdSrc(RichOutputText docIdSrc) {
        this.docIdSrc = docIdSrc;
    }

    public RichOutputText getDocIdSrc() {
        return docIdSrc;
    }

    public void paramNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            System.out.println("Inside of  validator 0000 !!" + object);
            /* Object str = getAttrsVal("MnfRcOpQcParamVO1Iterator", "ItmId");
            System.out.println("ITEM ID :" + str);
            if (duplicateValue("MnfRcOpQcParamVO1Iterator","ItmId", str)  ) {
                System.out.println("Inside 1"); */
            System.out.println(duplicateValue("MnfjcOpQcParam1Iterator", "TransParamDesc", object));
            if (duplicateValue("MnfjcOpQcParam1Iterator", "TransParamDesc", object)) {
                System.out.println("Inside of  validator !!");
                JSFUtils.addFacesErrorMessage("Duplicate Parameter is Selected !");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Duplicate Parameter Selected", null));
            }
            //}
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTable);
    }

    /**
     * Method to check duplicate value for rows within an iterator
     *
     * **/
    private boolean duplicateValue(String iter, String attrsNm, Object val) {
        DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);
        ViewObject vo = dcIter.getViewObject();
        RowSetIterator rSetIter = vo.createRowSetIterator(null);
        Row r = null;
        Integer countVal = 0;
        while (rSetIter.hasNext()) {
            r = rSetIter.next();
            // System.out.println("attrsNm "+r.getAttribute(attrsNm));

            if (r.getAttribute(attrsNm) != null && val.equals(r.getAttribute(attrsNm))) {
                countVal = countVal + 1;
            }
        }
        rSetIter.closeRowSetIterator();

        //exclude count from current row
        Row currentRow = dcIter.getCurrentRow();

        if (currentRow.getAttribute(attrsNm) != null && val.equals(currentRow.getAttribute(attrsNm))) {
            countVal = countVal - 1;
        }

        return countVal == 1 ? true : false;

    }

    public void setAttrsVal(String iter, String attrs, Object val) {
        if (iter != null && attrs != null) {
            DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);
            if (dcIter.getCurrentRow() != null)
                dcIter.getCurrentRow().setAttribute(attrs, val);
        }
    }

    public void qcParamVCL(ValueChangeEvent valueChangeEvent) {
        setAttrsVal("MnfjcOpQcParam1Iterator", "TlrncLower", 0);
        setAttrsVal("MnfjcOpQcParam1Iterator", "TlrncUpper", 0);
        setAttrsVal("MnfjcOpQcParam1Iterator", "UpperLimit", 0);
        setAttrsVal("MnfjcOpQcParam1Iterator", "LowerLimit", 0);
        setAttrsVal("MnfjcOpQcParam1Iterator", "StdVal", 0);
        AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTable);
    }

    public void setValueTypeBindVar(RichSelectOneChoice valueTypeBindVar) {
        this.valueTypeBindVar = valueTypeBindVar;
    }

    public RichSelectOneChoice getValueTypeBindVar() {
        return valueTypeBindVar;
    }

    public void standardValueValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            // if(getAttrsVal("MnfjcOpQcParam1Iterator", "ParamId")!= null){
            oracle.jbo.domain.Number stdNum = (oracle.jbo.domain.Number) object;
            if (ADFBeanUtils.isNumberNegative(stdNum)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Negative Value not allowed.", null));
            }
            if (!ADFBeanUtils.isPrecisionValid(stdNum)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision.", null));
            }
        }


    }

    public void stdValueVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            Integer x = (valueTypeBindVar.getValue() != null ? (Integer) valueTypeBindVar.getValue() : null);
            if (x == 274) {

                Number a = new Number(0);
                Number e = new Number(0);
                Number b = new Number(0);
                Number d = new Number(0);

                a = (Number) paramValueBindVar.getValue();
                e = (Number) paramValueBindVar.getValue();
                if (minTolBindVar.getValue() != null) {
                    b = (Number) minTolBindVar.getValue();
                }
                if (maxTolBindVar.getValue() != null) {
                    d = (Number) maxTolBindVar.getValue();
                }
                if (a != null && b != null && d != null) {
                    if (isValuePercBinding.isSelected()) {
                        b = a.multiply(b.divide(new Number(100)));
                        d = a.multiply(d.divide(new Number(100)));
                    }
                    Number f = a.subtract(b);
                    Number g = e.add(d);

                    setAttrsVal("MnfjcOpQcParam1Iterator", "UpperLimit", g);
                    setAttrsVal("MnfjcOpQcParam1Iterator", "LowerLimit", f);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTable);
        } else {
            setAttrsVal("MnfjcOpQcParam1Iterator", "TlrncLower", 0);
            setAttrsVal("MnfjcOpQcParam1Iterator", "TlrncUpper", 0);
            setAttrsVal("MnfjcOpQcParam1Iterator", "UpperLimit", 0);
            setAttrsVal("MnfjcOpQcParam1Iterator", "LowerLimit", 0);
            AdfFacesContext.getCurrentInstance().addPartialTarget(minTolBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(qcParamTable);
        }

    }

    public void setParamValueBindVar(RichInputText paramValueBindVar) {
        this.paramValueBindVar = paramValueBindVar;
    }

    public RichInputText getParamValueBindVar() {
        return paramValueBindVar;
    }

    public void chkPercentAmountVCE(ValueChangeEvent valueChangeEvent) {
        if (paramValueBindVar.getValue() != null) {
            Integer c = (valueTypeBindVar.getValue() != null ? (Integer) valueTypeBindVar.getValue() : null);
            if (c != null && c == 274) {

                Number a = (Number) paramValueBindVar.getValue();
                Number e = (Number) paramValueBindVar.getValue();

                Number b = (Number) minTolBindVar.getValue();
                Number d = (Number) maxTolBindVar.getValue();

                if (a != null && b != null && d != null) {
                    if (isValuePercBinding.isSelected()) {
                        b = a.multiply(b.divide(new Number(100)));
                        d = a.multiply(d.divide(new Number(100)));

                    }
                    Number f = a.subtract(b);
                    Number g = e.add(d);

                    setAttrsVal("MnfjcOpQcParam1Iterator", "UpperLimit", g);
                    setAttrsVal("MnfjcOpQcParam1Iterator", "LowerLimit", f);
                }
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(maxValBindVar);
    }

    public void setIsValuePercBinding(RichSelectBooleanCheckbox isValuePercBinding) {
        this.isValuePercBinding = isValuePercBinding;
    }

    public RichSelectBooleanCheckbox getIsValuePercBinding() {
        return isValuePercBinding;
    }

    public void lowerTolVCE(ValueChangeEvent valueChangeEvent) {
        if (paramValueBindVar.getValue() != null && valueChangeEvent.getNewValue() != null) {

            Integer c = (Integer) valueTypeBindVar.getValue();

            if (c != null && c == 274) {

                Number a = (Number) paramValueBindVar.getValue();
                Number b = (Number) minTolBindVar.getValue();
                if (a != null && b != null) {
                    if (isValuePercBinding.isSelected()) {
                        b = a.multiply(b.divide(new Number(100)));
                        // System.out.println(b + "  dfd");
                    }
                    Number f = a.subtract(b);

                    setAttrsVal("MnfjcOpQcParam1Iterator", "LowerLimit", f);
                }

                AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);

            }

        }
        if (paramValueBindVar.getValue() != null && valueChangeEvent.getNewValue() == null) {
            Number a = (Number) paramValueBindVar.getValue();
            Number f = a.subtract(0);
            setAttrsVal("MnfjcOpQcParam1Iterator", "LowerLimit", f);
            AdfFacesContext.getCurrentInstance().addPartialTarget(minValBindVar);
        }

    }

    public void lowTolerValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number value = (Number) (object);

            if (isValuePercBinding.isSelected()) {
                if (ADFBeanUtils.isPercentValueValid(value) != 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Please provide Percentage Value.", null));
                }
                if (ADFBeanUtils.isNumberNegative(value)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Negative Value not allowed.", null));
                }
            } else {
                if (!ADFBeanUtils.isPrecisionValid(value)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision.",
                                                                  null));
                }
                if (ADFBeanUtils.isNumberNegative(value)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Negative Value not allowed.", null));
                }
            }
        }


    }

    public void setMinTolBindVar(RichInputText minTolBindVar) {
        this.minTolBindVar = minTolBindVar;
    }

    public RichInputText getMinTolBindVar() {
        return minTolBindVar;
    }

    public void uprTolVCE(ValueChangeEvent valueChangeEvent) {
        if (paramValueBindVar.getValue() != null && valueChangeEvent.getNewValue() != null) {
            Integer c = (Integer) valueTypeBindVar.getValue();
            if (c != null && c == 274) {
                Number a = (Number) paramValueBindVar.getValue();
                Number b = (Number) maxTolBindVar.getValue();
                if (a != null && b != null) {
                    if (isValuePercBinding.isSelected()) {
                        b = a.multiply(b.divide(new Number(100)));
                        //System.out.println(b + "  dcf");
                    }
                    Number f = a.add(b);
                    setAttrsVal("MnfjcOpQcParam1Iterator", "UpperLimit", f);
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
            }
        }
        if (paramValueBindVar.getValue() != null && valueChangeEvent.getNewValue() == null) {
            Number a = (Number) paramValueBindVar.getValue();
            Number f = a.add(0);
            setAttrsVal("MnfjcOpQcParam1Iterator", "UpperLimit", f);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maxTolBindVar);
        }

    }

    public void setMaxTolBindVar(RichInputText maxTolBindVar) {
        this.maxTolBindVar = maxTolBindVar;
    }

    public RichInputText getMaxTolBindVar() {
        return maxTolBindVar;
    }

    public void uprTolerValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number value = (Number) (object);

            if (isValuePercBinding.isSelected()) {
                if (ADFBeanUtils.isPercentValueValid(value) != 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Please provide Percentage Value.", null));
                }
                if (ADFBeanUtils.isNumberNegative(value)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Negative Value not allowed.", null));
                }
            } else {
                if (!ADFBeanUtils.isPrecisionValid(value)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision.",
                                                                  null));
                }
                if (ADFBeanUtils.isNumberNegative(value)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Negative Value not allowed.", null));
                }
            }
            Number minV = new Number(0);
            if (minTolBindVar.getValue() != null) {
                minV = (Number) minTolBindVar.getValue();
            }

            if (value.compareTo(minV) == -1) {
                FacesMessage message = new FacesMessage("Upper Tolerance must be greater than Lower Tolerance!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }


    }

    public void setMinValBindVar(RichInputText minValBindVar) {
        this.minValBindVar = minValBindVar;
    }

    public RichInputText getMinValBindVar() {
        return minValBindVar;
    }

    public void setMaxValBindVar(RichInputText maxValBindVar) {
        this.maxValBindVar = maxValBindVar;
    }

    public RichInputText getMaxValBindVar() {
        return maxValBindVar;
    }

    public void setQcParamTable(RichTable qcParamTable) {
        this.qcParamTable = qcParamTable;
    }

    public RichTable getQcParamTable() {
        return qcParamTable;
    }

    public void overheadsDCL(DisclosureEvent disclosureEvent) {
        if (!requiredFields_Func()) {
            setDisclosedFirstTab(showOperationItemTabBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(showOperationItemTabBindVar);
        }
    }

    public void mcnDtDCL(DisclosureEvent disclosureEvent) {
        if (!requiredFields_Func()) {
            setDisclosedFirstTab(showOperationItemTabBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(showOperationItemTabBindVar);
        }
    }

    public void qcPDCL(DisclosureEvent disclosureEvent) {
        if (!requiredFields_Func()) {
            setDisclosedFirstTab(showOperationItemTabBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(showOperationItemTabBindVar);
        }
    }

    /**
     * Method to get value of attribute of current row for an Iterator
     * @param iter for iterator name
     * @param for attribute name
     * **/
    public Object getAttrsVal(String iter, String attrs) {

        if (iter != null && attrs != null) {
            DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);
            if (dcIter.getCurrentRow() != null)
                return dcIter.getCurrentRow().getAttribute(attrs);

        }
        return null;
    }

    public void setPendingQty(RichInputText pendingQty) {
        this.pendingQty = pendingQty;
    }

    public RichInputText getPendingQty() {
        return pendingQty;
    }

    public void jobCardBasisVLC(ValueChangeEvent valueChangeEvent) {
        // valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (valueChangeEvent.getNewValue() != null) {
            srcDocIdBindVar.setValue(null);
            srcDocIdBindVar.setSubmittedValue(null);
            //sourceDocDtBind.setValue(null);
            //sourceDocDtBind.setSubmittedValue(null);
            opIdBindVar.resetValue();
            opIdBindVar.setSubmittedValue(null);
            ADFBeanUtils.findOperation("opIdExe").execute();
            System.out.println("value is " + valueChangeEvent.getNewValue());
            if (valueChangeEvent.getNewValue().equals(new Integer(144))|| valueChangeEvent.getNewValue().equals(new Integer(153))) {

                this.getSrcDocIdBindVar().setReadOnly(true);
                //  this.getSrcDocIdBindVar().setValue("-1");
                this.getOpoutputItmQty().setReadOnly(true);
                this.getAddItemTab().setVisible(true);


                this.getShowOperationItemTabBindVar().setVisible(false);
            } else {
                this.getSrcDocIdBindVar().setReadOnly(false);
                this.getOpoutputItmQty().setReadOnly(false);
                this.getAddItemTab().setVisible(false);
                this.getShowOperationItemTabBindVar().setVisible(true);
            }
        }
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getAddItemTab());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getShowOperationItemTabBindVar());

    }

    public void processItemTypeVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("processItemChange").execute();
        //setopQuntity

        OperationBinding op = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setopQuntity");

        if (vce.getNewValue().toString().equals("67"))
            op.getParamsMap().put("flag", "S");
        else
            op.getParamsMap().put("flag", "R");

        op.execute();

        System.out.println("Error is " + op.getErrors());


        this.refreshItemValues();
        //AdfFacesContext.getCurrentInstance().addPartialTarget(this.getInputTabBind());
        // AdfFacesContext.getCurrentInstance().addPartialTarget(this.get);
    }

    public void setInputTabBind(RichTable inputTabBind) {
        this.inputTabBind = inputTabBind;
    }

    public RichTable getInputTabBind() {
        return inputTabBind;
    }

    public void addJcOpItem(ActionEvent actionEvent) {
        if (this.getOpIdBindVar().getValue() != null &&
            this.getOpIdBindVar().getValue().toString().trim().length() > 0) {

            // This valAndUpAdhoc" is to validate last inserted row
            OperationBinding ob = ADFBeanUtils.findOperation("valAndUpAdhoc");
            ob.execute();
            Object obj = ob.getResult();

            if (obj != null) {
                ArrayList<String> al = (ArrayList<String>) obj;
                StringBuilder msg = new StringBuilder();

                if (al.size() == 1) {
                    msg = msg.append(al.get(0));
                } else {
                    Iterator iter = al.iterator();
                    while (iter.hasNext()) {
                        msg = msg.append("<P>" + iter.next() + "</P><br>");
                    }
                }

                ADFModelUtils.showFormattedFacesMessage("Job Card", msg.toString(), FacesMessage.SEVERITY_ERROR);
            } else {
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateItemAdoc").execute();

                if (this.getIsCCExist())
                    BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("updateCostCenterAmt").execute();

            }


        } else {
            FacesContext.getCurrentInstance().addMessage(this.getOpIdBindVar().getClientId(),
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mandatory",
                                                                          "Please Select a Value"));
        }
    }

    public void validateOutput(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // MnfJcOpOutputItmIterator
        DCIteratorBinding iter = ADFBeanUtils.findIterator("MnfAdhocOutItm1Iterator");
        Long count = iter.getEstimatedRowCount();
        System.out.println("Object is " + object);
        System.out.println("Count is " + count);
        if (object.toString().trim().equals("67") && count.compareTo(new Long(1)) > 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalida",
                                                          "You have already added an Item"));
        }
    }

    public void changeItemVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        OperationBinding op = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setopQuntity");

        if (this.getItemType().getValue() != null && this.getItemType().getValue().toString().trim().equals("67"))
            op.getParamsMap().put("flag", "S");
        else
            op.getParamsMap().put("flag", "R");
        op.execute();

        System.out.println("Error is " + op.getErrors());
        this.refreshItemValues();

    }

    public void changeQtyVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        OperationBinding op = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setopQuntity");
        if (this.getItemType().getValue() != null && this.getItemType().getValue().toString().trim().equals("67"))
            op.getParamsMap().put("flag", "S");
        else
            op.getParamsMap().put("flag", "R");
        op.execute();

        System.out.println("Error is " + op.getErrors());
        this.refreshItemValues();
    }

    public void setItemType(RichSelectOneChoice itemType) {
        this.itemType = itemType;
    }

    public RichSelectOneChoice getItemType() {
        return itemType;
    }

    public void deleteItem(ActionEvent actionEvent) {
        // DeleteItem
        showPopup(this.getDeletePop(), true);

    }

    public void setCurrentItem(ActionEvent actionEvent) {
        // setCurrentRow

        UIComponent comp = actionEvent.getComponent();
        Map map = comp.getAttributes();
        Object obj = map.get("ItemId");
        Object obj1=map.get("ItemType");
        adfLog.info("Item id click " +map.get("ItemId"));
        adfLog.info("Item type click " +map.get("ItemType"));
        if (obj != null && obj.toString().trim().length() > 0&&obj1!=null && obj1.toString().trim().length() > 0) {
            String itemId = obj.toString();
            Integer itemtype=(Integer)obj1;
            OperationBinding op =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setCurrentRow");
            op.getParamsMap().put("itemId", itemId);
            op.getParamsMap().put("itemType",itemtype);
            op.execute();
            if (op.getErrors().size() > 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                                                             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                                                              "Error Occured in AM funcion of setCurrentRow"));
            }
        } else {
            System.out.println("Item id is not present at :" + Thread.currentThread().getStackTrace());
        }
    }

    public void validateItem(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...

        if (object == null || object.toString().trim().length() == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mandatory",
                                                          "Item name is required"));
        } else {
            OperationBinding op =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("isDuplicate");
            op.getParamsMap().put("name", object.toString().trim());
            op.execute();

            if (op.getErrors().size() > 0) {
                System.out.println("Error is " + op.getErrors());
                return;
            }

            String result = op.getResult().toString();

            if (result.equals("Y")) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate",
                                                              "Record already exist"));
            }
        }

    }

    public void setJbTypeBind(RichSelectOneChoice jbTypeBind) {
        this.jbTypeBind = jbTypeBind;
    }

    public RichSelectOneChoice getJbTypeBind() {
        return jbTypeBind;
    }

    public void setAddItemTab(RichSpacer addItemTab) {
        this.addItemTab = addItemTab;
    }

    public RichSpacer getAddItemTab() {
        return addItemTab;
    }

    public void setLinkPanelGroup(RichPanelGroupLayout linkPanelGroup) {
        this.linkPanelGroup = linkPanelGroup;
    }

    public RichPanelGroupLayout getLinkPanelGroup() {
        return linkPanelGroup;
    }

    public void setDeletePop(RichPopup deletePop) {
        this.deletePop = deletePop;
    }

    public RichPopup getDeletePop() {
        return deletePop;
    }

    public void deleteDiaList(DialogEvent dialogEvent) {
        System.out.println("delete dialogue event is invoked");
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("deleteCostCenterItem").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("DeleteItem").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindFormLayout);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindItemGrpLayout);
        AdfFacesContext.getCurrentInstance().addPartialTarget(deleteBtnBind);
        if (this.getIsCCExist())
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("deleteCostCenterItem").execute();
    }

    public void refreshItemValues() {
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.outputItemNmBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.itemIdBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.opoutputItmQty);

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.inputItemTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.outputItemTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getIsuueType());
    }
    

    public void setItemIdBind(RichInputText itemIdBind) {
        this.itemIdBind = itemIdBind;
    }

    public RichInputText getItemIdBind() {
        return itemIdBind;
    }

    public void setInputItemTable(RichTable inputItemTable) {
        this.inputItemTable = inputItemTable;
    }

    public RichTable getInputItemTable() {
        return inputItemTable;
    }

    public void setOutputItemTable(RichTable outputItemTable) {
        this.outputItemTable = outputItemTable;
    }

    public RichTable getOutputItemTable() {
        return outputItemTable;
    }

    public void refreshOnDisclose(DisclosureEvent disclosureEvent) {
        // Add event code here...
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getLinkGroup());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.allItemsDispPgBind);
    }

    public void setLinkGroup(RichPanelGroupLayout linkGroup) {
        this.linkGroup = linkGroup;
    }

    public RichPanelGroupLayout getLinkGroup() {
        return linkGroup;
    }

    public String headCostCenterAction() {
        OperationBinding binding =
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("chkCcApplicableOrNot");
        binding.execute();
        System.out.println("Result is " + binding.getResult());
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            return "headCc";
        } else {
            return null;
        }
    }

    public String addCosCenterLine() {
        OperationBinding binding =
            BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            return "costCenter";
        } else {
            return null;
        }
    }

    public void processChangeInPrice(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        OperationBinding op = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("setopQuntity");

        if (this.getItemType().getValue() != null && this.getItemType().getValue().toString().trim().equals("67"))
            op.getParamsMap().put("flag", "S");
        else
            op.getParamsMap().put("flag", "R");
        op.execute();
    }

  
    
    public Timestamp[] getValid(){
        OperationBinding op = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getDays");
        op.execute();
        Integer[] arry=(Integer[])op.getResult(); 
        
         Timestamp d[]=new Timestamp[2];
         d[0]=StaticValue.getTruncatedDt(new Timestamp(((Timestamp)this.getJcDate().getValue()).getTime()-arry[0]*86400000));
         d[1]=StaticValue.getTruncatedDt(new Timestamp(((Timestamp)this.getJcDate().getValue()).getTime()+arry[1]*86400000));
        
       // System.out.println("Array is "+d[0] +"and is "+d[1]);
        return d;
    }
    
    
    public Timestamp getCurrent() {
        return StaticValue.getTruncatedCurrDt();
    }
    
    


    public void setJcDate(RichInputDate jcDate) {
        this.jcDate = jcDate;
    }

    public RichInputDate getJcDate() {
        return jcDate;
    }

    public void setIsuueType(RichSelectOneChoice isuueType) {
        this.isuueType = isuueType;
    }

    public RichSelectOneChoice getIsuueType() {
        return isuueType;
    }

    public void setReturnQty(RichInputText returnQty) {
        this.returnQty = returnQty;
    }

    public RichInputText getReturnQty() {
        return returnQty;
    }

    public void returnChkChangeListener(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if((Boolean)valueChangeEvent.getNewValue()){
            this.getReturnQty().setVisible(true);
            this.getReturnQty().setValue(0.00);
        }else{
            this.getReturnQty().setVisible(false);
            this.getReturnQty().setValue(0.00);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getReturnQty());
    }

    public void setItmQty(RichInputText itmQty) {
        this.itmQty = itmQty;
    }

    public RichInputText getItmQty() {
        return itmQty;
    }

    public void returnQty(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object!=null&&object.toString().trim().length()!=0){
            if(this.getItmQty().getValue()!=null&&this.getItmQty().getValue().toString().trim().length()!=0){
                if(((oracle.jbo.domain.Number)this.getItmQty().getValue()).compareTo(((oracle.jbo.domain.Number)object))<0){
                  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","You can not return the value more than item quantity"));
                }
            }
        }else{
            
        }

    }


    public void LocationtypeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        DCIteratorBinding jciter = ADFBeanUtils.findIterator("MnfJc1Iterator");
        Integer loctype = (Integer)jciter.getCurrentRow().getAttribute("LocType");
        if(loctype!=null)
        {
        adfLog.info("value of loctype:::"+loctype);
        if(loctype==4) {
            bindCurrencyNm.setValue("");
            bindSubContractNm.setValue("");
            bindCurrencyNm.setRequired(true);
            bindSubContractNm.setRequired(true);
            jciter.getCurrentRow().setAttribute("TransEoName",null);
            jciter.getCurrentRow().setAttribute("TransCurrDescTxn",null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindSubContractNm);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindCurrencyNm);
        }
        else {
            //adfLog.info("in the else part");
             bindCurrencyNm.setRequired(false);
             bindSubContractNm.setRequired(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindSubContractNm);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bindCurrencyNm);
        }
        }
    }

    public void setBindSubContractNm(RichInputListOfValues bindSubContractNm) {
        this.bindSubContractNm = bindSubContractNm;
    }

    public RichInputListOfValues getBindSubContractNm() {
        return bindSubContractNm;
    }

    public void setBindCurrencyNm(RichInputListOfValues bindCurrencyNm) {
        this.bindCurrencyNm = bindCurrencyNm;
    }

    public RichInputListOfValues getBindCurrencyNm() {
        return bindCurrencyNm;
    }

    public void setBindShortClose(RichSelectBooleanCheckbox bindShortClose) {
        this.bindShortClose = bindShortClose;
    }

    public RichSelectBooleanCheckbox getBindShortClose() {
        return bindShortClose;
    }

    public void shortCloseJcVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        adfLog.info("heckShortCloseForPartialQty");
        OperationBinding op=ADFBeanUtils.findOperation("checkShortCloseForPartialQty");
        op.execute();
        if(op.getResult()!=null) {
            if(op.getResult().toString().equals("Y")) {
                saveBtnAction.setDisabled(false);
            
            }
            else {
                StringBuilder message = new StringBuilder();
                message.append("No Job Execution exists on the basis of this Job card.");
                ADFModelUtils.showFormattedFacesMessage("Job Card", message.toString(), FacesMessage.SEVERITY_INFO);
            }
        }
    }

    public void shortCloseFlgVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(bindShortClose.isSelected()){
        adfLog.info("heckShortCloseForPartialQty");
        OperationBinding op=ADFBeanUtils.findOperation("checkShortCloseForPartialQty");
        op.execute();
        if(op.getResult()!=null) {
            if(op.getResult().toString().equals("Y")) {
                saveBtnAction.setDisabled(false);
                releaseBtnBind.setDisabled(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(saveBtnAction);
                AdfFacesContext.getCurrentInstance().addPartialTarget(releaseBtnBind);
            }
        }
        }
        else {
            releaseBtnBind.setDisabled(false);
            saveBtnAction.setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(releaseBtnBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveBtnAction);
        }
    }

    public void setAddSerialbtn(RichButton addSerialbtn) {
        this.addSerialbtn = addSerialbtn;
    }

    public RichButton getAddSerialbtn() {
        return addSerialbtn;
    }

    public void setBindFormLayout(RichPanelFormLayout bindFormLayout) {
        this.bindFormLayout = bindFormLayout;
    }

    public RichPanelFormLayout getBindFormLayout() {
        return bindFormLayout;
    }

    public void setBindItemGrpLayout(RichPanelGroupLayout bindItemGrpLayout) {
        this.bindItemGrpLayout = bindItemGrpLayout;
    }

    public RichPanelGroupLayout getBindItemGrpLayout() {
        return bindItemGrpLayout;
    }

    public void setDeleteBtnBind(RichLink deleteBtnBind) {
        this.deleteBtnBind = deleteBtnBind;
    }

    public RichLink getDeleteBtnBind() {
        return deleteBtnBind;
    }
}
