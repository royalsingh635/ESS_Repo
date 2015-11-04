package mmreceiptmtl.view.beans;

import adf.utils.bean.StaticValue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.ActionEvent;
import javax.faces.event.MethodExpressionActionListener;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import mmreceiptmtl.model.services.MMReceiptMtlAMImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputNumberSpinbox;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.component.rich.nav.RichCommandNavigationItem;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.JboPrecisionScaleValidator;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
//import java.util.Date;


/**
 * Material Receipt Managed Bean.
 * Contains all the business logic for Receipt functionalities.
 * @author SM
 * @Modified by- NG
 * Dated -03/09/2013
 * */

public class MtlReceiptBean implements Serializable {
    private RichSelectOneChoice srcDocType;
    private RichInputListOfValues docNoPageVar;
    private RichTable rcptItmTable;
    private RichInputDate rcptDate;
    private RichSelectOneChoice whIdPage;
    private RichInputText rcptNo;
    private String selectFacet = "Lot";
    private RichPopup lotAddPopUp;
    private RichCommandNavigationItem allocateLotNav;
    private RichPopup binAddPopup;
    private String LotMode = "A";
    private String BinMode = "A";
    private String SrMode = "A";
    private RichPanelGroupLayout groupMutiBind;
    private Integer uiCount = 0;
    private RichPopup srAddPopUp;
    private RichInputText srLotBind;
    private RichSelectOneChoice srBinBind;
    private RichInputText rcptQtyBind;
    private RichTable lotTableBind;
    private RichTable binTableBind;
    private RichTable srTableBind;
    private RichInputText lotNoBinBind;
    private RichSelectOneChoice binNoBinBind;
    private RichInputText srNoBind;
    private static int NUMBER = Types.NUMERIC;

    private static Integer SOURCE_DOC_TYPE_PO = 266;
    private static Integer SOURCE_DOC_TYPE_IMPORT_PO = 819;
    private static Integer SOURCE_DOC_TYPE_JITR = 938;
    private static Integer SOURCE_DOC_TYPE_TO = 270;
    private static Integer SOURCE_DOC_TYPE_CPO = 459;
    private static Integer SOURCE_DOC_TYPE_WOUT_PO = 370;
    private static Integer SOURCE_DOC_TYPE_MRN = 399;
    private static Integer SOURCE_DOC_TYPE_GPR = 825;
    private static Integer SOURCE_DOC_TYPE_FGR = 267;
    private static Integer SOURCE_DOC_TYPE_PRODCT = 770;
    private static Integer RCPT_TYPE_OB = 396;
    private static Integer RCPT_TYPE_SFLOR = 335;
    private static Integer RCPT_TYPE_TO_WITHIN = 337;
    private static Integer RCPT_TYPE_TO_OTHER = 338;

    //  private static String RCPT_WF_ID="W046";
    private static Integer RCPT_DOC_NO = 18515;
    private Number finalRcptQty = new Number(0);
    private Number oldFinalRcptQty = new Number(0);
    private static Integer RCPT_STATUS_REWORK = 381;
    private static Integer RCPT_STATUS_QCPEND = 380;
    private static Integer RCPT_STATUS_STOCKUP = 383;
    private static Integer RCPT_STATUS_QCDONE = 422;
    private static Integer RCPT_STATUS_PEND = 379;
    //private Boolean disable=false;
    private static ADFLogger _log = ADFLogger.createADFLogger(MtlReceiptBean.class);

    private RichTable rcptSrcTable;
    private RichInputListOfValues itmNameBindVar;
    private RichSelectOneChoice uomBindVar;
    private RichInputText itmQty;
    private String showAddItm = "Y";
    private RichPopup editConfPopUp;
    private RichPanelGroupLayout groupAddDoc;
    private RichInputNumberSpinbox schdlNoBind;
    private RichSelectOneChoice itmIdRcpt;
    private RichInputDate docDate;
    private String disItmForm = "N";
    private String chkLotAss = "N";
    private RichPanelFormLayout itmForm;
    private RichSelectOneChoice whIdsrcPage;
    private RichSelectOneChoice rcptStgVar;
    private RichOutputText checkQcReqd;
    private RichOutputText checkItmLot;
    private RichPopup qualityCheckPopUp;
    private RichSelectOneChoice rcptSrcType;
    private RichInputText finalQty;
    private String msgpop;
    private RichPopup confDlvQtyChkPopUp;
    private RichOutputText chkReworkable;
    private RichInputText supplierPage;
    private RichTable srNoTableBind;
    private RichSelectOneChoice rcptStat;
    private RichInputText authStat;
    private RichInputComboboxListOfValues itmSerialNo;
    private RichInputText transSerialSrOp;
    private RichPopup rmdaPopUp;
    private RichInputText rmdaNo;
    private RichInputText checkRejected;
    private RichSelectOneChoice reqmtAreaPage;
    private RichInputText uomConvFctr;
    private Boolean isUomBase;
    private RichInputText lotidBindPage;
    private RichInputComboboxListOfValues geNoListBinding;
    private RichCommandImageLink updtPONObuttonBind;
    private RichPopup qtyChangePopup;
    private RichInputText fianlRcptQtyBinding;
    private RichInputText docIdSrcDispBinding;
    private RichShowDetailItem tab1binding;
    private RichShowDetailItem tab2Binding;
    private RichColumn docIdSrcColumnVar;
    private RichSelectOneChoice orgIdSrcBinding;
    private RichInputListOfValues eoIdSrcBinding;
    private RichCommandLink rmdaNoBinding;
    private RichInputText rejQtybind;
    private RichInputText itmLndPriceBind;
    private String callLotBinDltPop =
        "N"; // N for call delete button and Y for call final rcpt Qty value change listener
    private String deleteButtonActionPop = "N";
    private RichPopup deleteItmPopup;
    private RichInputText fileBindName;
    private RichInputText binQtyBsBind;
    private RichPopup applyTaxOnItem;
    private RichPopup resetTaxPopup; // N for Lot bin not Assign and Y for Lot bin Assign.
    //  private RichInputComboboxListOfValues geNoBind;
    private Integer taxRule = null;
    private Integer taxRuleitem = null;
    private Number taxableAmount = new Number(0);
    private Number itmtaxamt1 = new Number(0);
    private String taxExmpt = "N";
    private String taxFlg = "N";
    private String taxruleflg = "I";
    Number zero = new Number(0);
    Number one = new Number(1);
    private String taxPopulate = "N";
    private RichInputText transOcAmtBind;
    private RichSelectOneChoice ocDiscriptbind;
    private RichTable calculationTblBind;
    private RichTable taxTrLineBind;
    private RichInputText binQtyRejBsBind;
    private RichInputText binQtyRwkBsBind;
    private RichPopup barCodePopBinding;
    private RichInputListOfValues itmIdOnPopBinding;
    private RichInputText srNoOnPopBinding;
    private RichInputText delvQtyOnPopBinding;
    private RichInputText rejQtyOnPopBinding;
    private RichInputText reWrkQtyOnPopBinding;
    private RichInputText rcptQtyOnPopBinding;
    private RichInputText srLzItmChkOnPopBinding;
    private RichInputText itmChkOnPopBinding;
    private RichTable ocTableBind;
    private RichInputText uomCnvrFactBind;
    private RichInputDate mfgDateBind;
    private RichPopup binCnfrnPopup;
    private RichInputText srNoForNonSrItm;

    public MtlReceiptBean() {
    }

    /**
     *  -----------------------------------------Setters and Getters --------------------------------------------------
     * */

    /**
     *Global round digit for Amount
     * @return
     */

    public Integer getGlblRoundAmtDigit() {
        if (resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}") != null) {
            return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}"));
        } else {
            return 6;
        }
    }

    /**
     *Global round digit for Quantity
     * @return
     */

    public Integer getGlblRoundQtyDigit() {
        if (resolvEl("#{pageFlowScope.GLBL_QTY_DIGIT}") != null) {
            return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_QTY_DIGIT}"));
        } else {
            return 6;
        }

    }

    /**
     *Global round digit for currency rate
     * @return
     */

    public Integer getGlblRoundCurrRateDigit() {
        if (resolvEl("#{pageFlowScope.GLBL_CURR_DIGIT}") != null) {
            return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_CURR_DIGIT}"));
        } else {
            return 6;
        }

    }

    public void setLotMode(String LotMode) {
        this.LotMode = LotMode;
    }

    public String getLotMode() {
        return LotMode;
    }

    public void setBinMode(String BinMode) {
        this.BinMode = BinMode;
    }

    public String getBinMode() {
        return BinMode;
    }

    public void setRcptDate(RichInputDate rcptDate) {
        this.rcptDate = rcptDate;
    }

    public RichInputDate getRcptDate() {
        return rcptDate;
    }

    public void setWhIdPage(RichSelectOneChoice whIdPage) {
        this.whIdPage = whIdPage;
    }

    public RichSelectOneChoice getWhIdPage() {
        return whIdPage;
    }

    public void setRcptNo(RichInputText rcptNo) {
        this.rcptNo = rcptNo;
    }

    public RichInputText getRcptNo() {
        return rcptNo;
    }

    public void setRcptQtyBind(RichInputText rcptQtyBind) {
        this.rcptQtyBind = rcptQtyBind;
    }

    public RichInputText getRcptQtyBind() {
        return rcptQtyBind;
    }

    public void setSrcDocType(RichSelectOneChoice srcDocType) {
        this.srcDocType = srcDocType;
    }

    public RichSelectOneChoice getSrcDocType() {
        return srcDocType;
    }

    public void setDocNoPageVar(RichInputListOfValues docNoPageVar) {
        this.docNoPageVar = docNoPageVar;
    }

    public RichInputListOfValues getDocNoPageVar() {
        return docNoPageVar;
    }

    public void setRcptItmTable(RichTable rcptItmTable) {
        this.rcptItmTable = rcptItmTable;
    }

    public RichTable getRcptItmTable() {
        return rcptItmTable;
    }

    public void setSupplierPage(RichInputText supplierPage) {
        this.supplierPage = supplierPage;
    }

    public RichInputText getSupplierPage() {
        return supplierPage;
    }

    public void setMsgpop(String msgpop) {
        this.msgpop = msgpop;
    }

    public String getMsgpop() {
        return msgpop;
    }

    public void setSrNoBind(RichInputText srNoBind) {
        this.srNoBind = srNoBind;
    }

    public RichInputText getSrNoBind() {
        return srNoBind;
    }

    public void setReqmtAreaPage(RichSelectOneChoice reqmtAreaPage) {
        this.reqmtAreaPage = reqmtAreaPage;
    }

    public RichSelectOneChoice getReqmtAreaPage() {
        return reqmtAreaPage;
    }

    public void setGroupMutiBind(RichPanelGroupLayout groupMutiBind) {
        this.groupMutiBind = groupMutiBind;
    }

    public RichPanelGroupLayout getGroupMutiBind() {
        return groupMutiBind;
    }

    public void setAuthStat(RichInputText authStat) {
        this.authStat = authStat;
    }

    public RichInputText getAuthStat() {
        return authStat;
    }

    public void setSrAddPopUp(RichPopup srAddPopUp) {
        this.srAddPopUp = srAddPopUp;
    }

    public RichPopup getSrAddPopUp() {
        return srAddPopUp;
    }

    public void setConfDlvQtyChkPopUp(RichPopup confDlvQtyChkPopUp) {
        this.confDlvQtyChkPopUp = confDlvQtyChkPopUp;
    }

    public RichPopup getConfDlvQtyChkPopUp() {
        return confDlvQtyChkPopUp;
    }

    public void setRmdaNo(RichInputText rmdaNo) {
        this.rmdaNo = rmdaNo;
    }

    public RichInputText getRmdaNo() {
        return rmdaNo;
    }

    public void setRcptSrcTable(RichTable rcptSrcTable) {
        this.rcptSrcTable = rcptSrcTable;
    }

    public RichTable getRcptSrcTable() {
        return rcptSrcTable;
    }

    public void setItmNameBindVar(RichInputListOfValues itmNameBindVar) {
        this.itmNameBindVar = itmNameBindVar;
    }

    public RichInputListOfValues getItmNameBindVar() {
        return itmNameBindVar;
    }

    public void setUomBindVar(RichSelectOneChoice uomBindVar) {
        this.uomBindVar = uomBindVar;
    }

    public RichSelectOneChoice getUomBindVar() {
        return uomBindVar;
    }

    public void setChkReworkable(RichOutputText chkReworkable) {
        this.chkReworkable = chkReworkable;
    }

    public void setLotAddPopUp(RichPopup lotAddPopUp) {
        this.lotAddPopUp = lotAddPopUp;
    }

    public RichPopup getLotAddPopUp() {
        return lotAddPopUp;
    }

    public RichOutputText getChkReworkable() {
        return chkReworkable;
    }

    public void setItmQty(RichInputText itmQty) {
        this.itmQty = itmQty;
    }

    public RichInputText getItmQty() {
        return itmQty;
    }

    public void setShowAddItm(String showAddItm) {
        this.showAddItm = showAddItm;
    }

    public String getShowAddItm() {
        return showAddItm;
    }

    public void setRcptSrcType(RichSelectOneChoice rcptSrcType) {
        this.rcptSrcType = rcptSrcType;
    }

    public RichSelectOneChoice getRcptSrcType() {
        return rcptSrcType;
    }

    public void setFinalQty(RichInputText finalQty) {
        this.finalQty = finalQty;
    }

    public RichInputText getFinalQty() {
        return finalQty;
    }

    public void setLotTableBind(RichTable lotTableBind) {
        this.lotTableBind = lotTableBind;
    }

    public RichTable getLotTableBind() {
        return lotTableBind;
    }

    public void setBinTableBind(RichTable binTableBind) {
        this.binTableBind = binTableBind;
    }

    public RichTable getBinTableBind() {
        return binTableBind;
    }

    public void setSrTableBind(RichTable srTableBind) {
        this.srTableBind = srTableBind;
    }

    public RichTable getSrTableBind() {
        return srTableBind;
    }

    public void setEditConfPopUp(RichPopup editConfPopUp) {
        this.editConfPopUp = editConfPopUp;
    }

    public RichPopup getEditConfPopUp() {
        return editConfPopUp;
    }

    public void setGroupAddDoc(RichPanelGroupLayout groupAddDoc) {
        this.groupAddDoc = groupAddDoc;
    }

    public RichPanelGroupLayout getGroupAddDoc() {
        return groupAddDoc;
    }

    public void setSchdlNoBind(RichInputNumberSpinbox schdlNoBind) {
        this.schdlNoBind = schdlNoBind;
    }

    public RichInputNumberSpinbox getSchdlNoBind() {
        return schdlNoBind;
    }

    public void setItmIdRcpt(RichSelectOneChoice itmIdRcpt) {
        this.itmIdRcpt = itmIdRcpt;
    }

    public RichSelectOneChoice getItmIdRcpt() {
        return itmIdRcpt;
    }

    public void setDocDate(RichInputDate docDate) {
        this.docDate = docDate;
    }

    public void setRcptStat(RichSelectOneChoice rcptStat) {
        this.rcptStat = rcptStat;
    }

    public RichSelectOneChoice getRcptStat() {
        return rcptStat;
    }

    public RichInputDate getDocDate() {
        return docDate;
    }

    public void setDisItmForm(String disItmForm) {
        this.disItmForm = disItmForm;
    }

    public String getDisItmForm() {
        return disItmForm;
    }

    public void setItmForm(RichPanelFormLayout itmForm) {
        this.itmForm = itmForm;
    }

    public RichPanelFormLayout getItmForm() {
        return itmForm;
    }

    public void setSrMode(String SrMode) {
        this.SrMode = SrMode;
    }

    public String getSrMode() {
        return SrMode;
    }

    public void setSrLotBind(RichInputText srLotBind) {
        this.srLotBind = srLotBind;
    }

    public RichInputText getSrLotBind() {
        return srLotBind;
    }

    public void setSrBinBind(RichSelectOneChoice srBinBind) {
        this.srBinBind = srBinBind;
    }

    public RichSelectOneChoice getSrBinBind() {
        return srBinBind;
    }

    public void setWhIdsrcPage(RichSelectOneChoice whIdsrcPage) {
        this.whIdsrcPage = whIdsrcPage;
    }

    public RichSelectOneChoice getWhIdsrcPage() {
        return whIdsrcPage;
    }

    public void setRcptStgVar(RichSelectOneChoice rcptStgVar) {
        this.rcptStgVar = rcptStgVar;
    }

    public RichSelectOneChoice getRcptStgVar() {
        return rcptStgVar;
    }

    public void setCheckQcReqd(RichOutputText checkQcReqd) {
        this.checkQcReqd = checkQcReqd;
    }

    public RichOutputText getCheckQcReqd() {
        return checkQcReqd;
    }

    public void setCheckItmLot(RichOutputText checkItmLot) {
        this.checkItmLot = checkItmLot;
    }

    public RichOutputText getCheckItmLot() {
        return checkItmLot;
    }

    public void setQualityCheckPopUp(RichPopup qualityCheckPopUp) {
        this.qualityCheckPopUp = qualityCheckPopUp;
    }

    public RichPopup getQualityCheckPopUp() {
        return qualityCheckPopUp;
    }

    public void setLotNoBinBind(RichInputText lotNoBinBind) {
        this.lotNoBinBind = lotNoBinBind;
    }

    public RichInputText getLotNoBinBind() {
        return lotNoBinBind;
    }

    public void setBinNoBinBind(RichSelectOneChoice binNoBinBind) {
        this.binNoBinBind = binNoBinBind;
    }

    public RichSelectOneChoice getBinNoBinBind() {
        return binNoBinBind;
    }


    public void setBinAddPopup(RichPopup binAddPopup) {
        this.binAddPopup = binAddPopup;
    }

    public RichPopup getBinAddPopup() {
        return binAddPopup;
    }

    public void setSrNoTableBind(RichTable srNoTableBind) {
        this.srNoTableBind = srNoTableBind;
    }

    public RichTable getSrNoTableBind() {
        return srNoTableBind;
    }

    public void setSelectFacet(String selectFacet) {
        this.selectFacet = selectFacet;
    }

    public String getSelectFacet() {
        return selectFacet;
    }

    public void setAllocateLotNav(RichCommandNavigationItem allocateLotNav) {
        this.allocateLotNav = allocateLotNav;
    }

    public RichCommandNavigationItem getAllocateLotNav() {
        return allocateLotNav;
    }

    public void setRmdaPopUp(RichPopup rmdaPopUp) {
        this.rmdaPopUp = rmdaPopUp;
    }

    public RichPopup getRmdaPopUp() {
        return rmdaPopUp;
    }

    public void setItmSerialNo(RichInputComboboxListOfValues itmSerialNo) {
        this.itmSerialNo = itmSerialNo;
    }

    public RichInputComboboxListOfValues getItmSerialNo() {
        return itmSerialNo;
    }

    public void setTransSerialSrOp(RichInputText transSerialSrOp) {
        this.transSerialSrOp = transSerialSrOp;
    }

    public RichInputText getTransSerialSrOp() {
        return transSerialSrOp;
    }

    public void setCheckRejected(RichInputText checkRejected) {
        this.checkRejected = checkRejected;
    }

    public RichInputText getCheckRejected() {
        return checkRejected;
    }

    public void setUomConvFctr(RichInputText uomConvFctr) {
        this.uomConvFctr = uomConvFctr;
    }

    public RichInputText getUomConvFctr() {
        return uomConvFctr;
    }

    public void setIsUomBase(Boolean isUomBase) {
        this.isUomBase = isUomBase;
    }

    public Boolean getIsUomBase() {

        Object fctr = this.getUomConvFctr().getValue();
        if (fctr != null) {
            if (((Number) fctr).compareTo(new Number(1)) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     *  -------------------------------------- Helper Methods--------------------------------------------------
     * */

    private void showPopup(RichPopup popup, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && popup != null) {
                String popupId = popup.getClientId(context);
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

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
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

    /**
     *      Method to show validation message(I,E,W)
     *      mesg:Message to display
     *      sev:Severity(I,E,W)
     *      chk:true=if resource bundle is used
     *      typFlg: 'F' for FM , 'V' for VE
     * */
    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg) {
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true) {
            String msg = resolvEl("#{bundle['" + mesg + "']}");
            message = new FacesMessage(msg);
        }
        if (sev.equalsIgnoreCase("E")) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        } else if (sev.equalsIgnoreCase("W")) {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
        } else if (sev.equalsIgnoreCase("I")) {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        } else {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        if (typFlg.equals("F")) {
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else if (typFlg.equals("V")) {
            throw new ValidatorException(message);
        }
    }

    public String addDocumentAction() {
        Integer paramUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String paramHoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        if (rcptSrcType.getValue() != null) {
            if (Integer.parseInt(rcptSrcType.getValue().toString()) == RCPT_TYPE_OB) {

                if (itmNameBindVar.getValue() != null && itmNameBindVar.getValue().toString().length() > 0) {
                    if (uomBindVar.getValue() != null) {
                        if (itmQty.getValue() != null) {
                            _log.info("qty=" + itmQty.getValue());
                            Number qty = (Number) itmQty.getValue();

                            if (qty.compareTo(new Number(0)) == 1) {
                                OperationBinding chkItmDup = getBindings().getOperationBinding("checkItmDuplicate");
                                chkItmDup.getParamsMap().put("itmNm", itmNameBindVar.getValue().toString());
                                chkItmDup.getParamsMap().put("uomId", uomBindVar.getValue().toString());
                                chkItmDup.getParamsMap().put("paramOrgId", paramOrgId);
                                chkItmDup.getParamsMap().put("paramCldId", paramCldId);

                                chkItmDup.execute();
                                String retChk = chkItmDup.getResult().toString();
                                _log.info("Return =" + retChk);
                                if (retChk.equals("N")) {
                                    _log.info(" retChk  :::::  1  ");
                                    if (!retChk.equals("D")) {
                                        _log.info(" retChk  :::::  2  ");
                                        //add item to the receipt
                                        if (rcptSrcTable.getRowCount() == 0) {
                                            //Generate temp Doc for WIthout PO
                                            _log.info(" retChk  :::::  3  ");
                                            OperationBinding genDocIdOp =
                                                getBindings().getOperationBinding("generateTempDocId");
                                            genDocIdOp.getParamsMap().put("UsrId", paramUsrId);
                                            genDocIdOp.getParamsMap().put("CldId", paramCldId);
                                            genDocIdOp.getParamsMap().put("SlocId", paramSlocId);
                                            genDocIdOp.getParamsMap().put("OrgId", paramOrgId);
                                            genDocIdOp.getParamsMap().put("WhId", whIdPage.getValue().toString());
                                            genDocIdOp.getParamsMap().put("DocTypeSrc", RCPT_TYPE_OB);
                                            genDocIdOp.execute();
                                        }
                                        OperationBinding addItmOp =
                                            getBindings().getOperationBinding("addItemToRcptForOb");
                                        addItmOp.getParamsMap().put("ItmName", itmNameBindVar.getValue().toString());
                                        addItmOp.getParamsMap().put("ItmUom", uomBindVar.getValue().toString());
                                        addItmOp.getParamsMap().put("ItmQty", (Number) itmQty.getValue());
                                        addItmOp.execute();

                                        if (addItmOp.getErrors().isEmpty()) {
                                            _log.info("Item added");
                                            OperationBinding opbind =
                                                getBindings().getOperationBinding("setTransValueToNull");
                                            opbind.execute();
                                            itmNameBindVar.setValue("");
                                            uomBindVar.setValue("");
                                            uomBindVar.resetValue();
                                            itmQty.setValue("");
                                            AdfFacesContext.getCurrentInstance().addPartialTarget(itmNameBindVar);
                                            AdfFacesContext.getCurrentInstance().addPartialTarget(uomBindVar);
                                            AdfFacesContext.getCurrentInstance().addPartialTarget(itmQty);
                                        }
                                    } else {
                                        _log.info(" else  Item already added.");
                                        //showFacesMessage("Item already added.", "E", false, "F");
                                        showFacesMessage("MSG.726", "E", true, "F");
                                    }
                                } else {
                                    _log.info(" else  Opening Balance for is already added for the financial year..");
                                    String msg =
                                        "Opening Balance for  " + itmNameBindVar.getValue().toString() +
                                        " is already added for the financial year.";
                                    showFacesMessage(msg, "E", false, "F");
                                    // Key->(MSG.727)->>Opening Balance for
                                    // Key->(MSG.728)->>is already added for the financial year.

                                }
                            } else {
                                //showFacesMessage("Quantity must be greater than 0.", "E", false, "F");
                                showFacesMessage("MSG.337", "E", true, "F");

                                _log.info("Quantity must be greater than 0");
                            }
                        } else {
                            String msg = "Item Quantity must be entered.";
                            //showFacesMessage(msg, "E", false, "F");
                            //Key->MSG.729->Item Quantity must be entered.
                            showFacesMessage("MSG.729", "E", true, "F");
                            _log.info("Quantity Null");
                        }
                    } else {
                        _log.info("UOm Null");
                    }
                } else {
                    showFacesMessage("Please select Item.", "E", false, "F");
                    _log.info("Itm Null");
                }
            } else {
                if (srcDocType.getValue() != null && !srcDocType.getValue().equals("")) {
                    if (Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_WOUT_PO) {
                        String retV = "N";
                        OperationBinding chkRcptWOPO = getBindings().getOperationBinding("chkRcptWOTPO");
                        chkRcptWOPO.execute();
                        if (chkRcptWOPO.getResult() != null) {
                            retV = chkRcptWOPO.getResult().toString();
                        }
                        _log.info("retV     :::: " + retV);
                        if ("N".equalsIgnoreCase(retV)) {
                            showFacesMessage("Current Organisation not allow Receipt Without PO.", "E", false, "F");
                        } else if ("Y".equalsIgnoreCase(retV)) {

                            if (supplierPage.getValue() != null) {
                                if (itmNameBindVar.getValue() != null) {
                                    if (uomBindVar.getValue() != null) {
                                        if (itmQty.getValue() != null) {
                                            if (rcptSrcTable.getRowCount() ==
                                                0) {
                                                //Generate temp Doc for WIthout PO
                                                OperationBinding genDocIdOp =
                      getBindings().getOperationBinding("generateTempDocId");
                                                genDocIdOp.getParamsMap().put("UsrId", paramUsrId);
                                                genDocIdOp.getParamsMap().put("CldId", paramCldId);
                                                genDocIdOp.getParamsMap().put("SlocId", paramSlocId);
                                                genDocIdOp.getParamsMap().put("OrgId", paramOrgId);
                                                genDocIdOp.getParamsMap().put("WhId", whIdPage.getValue().toString());
                                                genDocIdOp.getParamsMap().put("DocTypeSrc", SOURCE_DOC_TYPE_WOUT_PO);
                                                genDocIdOp.execute();
                                            }
                                            if (itmNameBindVar.getValue() != null) {
                                                if (uomBindVar.getValue() != null) {
                                                    if (itmQty.getValue() != null) {
                                                        _log.info("qty=" + itmQty.getValue());
                                                        Number qty = (Number) itmQty.getValue();

                                                        if (qty.compareTo(new Number(0)) == 1) {
                                                            OperationBinding chkItmDup =
                                                                getBindings().getOperationBinding("checkItmDuplicate");
                                                            chkItmDup.getParamsMap().put("itmNm",
                                                                                         itmNameBindVar.getValue().toString());
                                                            chkItmDup.getParamsMap().put("uomId",
                                                                                         uomBindVar.getValue().toString());
                                                            chkItmDup.getParamsMap().put("paramOrgId", paramOrgId);
                                                            chkItmDup.getParamsMap().put("paramCldId", paramCldId);

                                                            chkItmDup.execute();

                                                            if (chkItmDup.getResult().toString().equals("N")) {
                                                                //add item to the receipt
                                                                OperationBinding addItmOp =
                                                                    getBindings().getOperationBinding("addItemToRcpt");
                                                                addItmOp.getParamsMap().put("ItmName",
                                                                                            itmNameBindVar.getValue().toString());
                                                                addItmOp.getParamsMap().put("ItmUom",
                                                                                            uomBindVar.getValue().toString());
                                                                addItmOp.getParamsMap().put("ItmQty",
                                                                                            (Number) itmQty.getValue());
                                                                addItmOp.execute();

                                                                if (addItmOp.getErrors().isEmpty()) {
                                                                    OperationBinding opbind =
                                                                        getBindings().getOperationBinding("setTransValueToNull");
                                                                    opbind.execute();
                                                                    itmNameBindVar.setValue("");
                                                                    uomBindVar.setValue("");
                                                                    uomBindVar.resetValue();
                                                                    itmQty.setValue("");
                                                                    AdfFacesContext.getCurrentInstance().addPartialTarget(itmNameBindVar);
                                                                    AdfFacesContext.getCurrentInstance().addPartialTarget(uomBindVar);
                                                                    AdfFacesContext.getCurrentInstance().addPartialTarget(itmQty);
                                                                    _log.info("Item added");
                                                                }
                                                            } else {
                                                                //showFacesMessage("Item already added.", "E", false, "F");
                                                                showFacesMessage("MSG.726", "E", true, "F");
                                                            }
                                                        } else {
                                                            //showFacesMessage("Quantity must be greater than zero", "E", false, "F");
                                                            showFacesMessage("MSG.730", "E", true, "F");
                                                            _log.info("Quantity must be greater than 0");
                                                        }
                                                    } else {
                                                        //showFacesMessage("Quantity must be entered", "E", false, "F");
                                                        showFacesMessage("MSG.657", "E", true, "F");
                                                        _log.info("Quantity Null");
                                                    }

                                                } else {
                                                    //showFacesMessage("Please select the Unit.", "E", false, "F");
                                                    showFacesMessage("MSG.731", "E", true, "F");
                                                    _log.info("UOm Null");
                                                }
                                            } else {
                                                //showFacesMessage("Please select the item.", "E", false, "F");
                                                showFacesMessage("MSG.659", "E", true, "F");
                                                _log.info("Itm Null");
                                            }
                                        } else {
                                            //showFacesMessage("Quantity must be entered", "E", false, "F");
                                            showFacesMessage("MSG.657", "E", true, "F");
                                            _log.info("Quantity Null");
                                        }
                                    } else {
                                        //showFacesMessage("Please select the Unit.", "E", false, "F");
                                        showFacesMessage("MSG.731", "E", true, "F");
                                        _log.info("UOm Null");
                                    }
                                } else {
                                    //showFacesMessage("Please select the item.", "E", false, "F");
                                    showFacesMessage("MSG.659", "E", true, "F");
                                    _log.info("Itm Null");
                                }
                            } else {
                                //showFacesMessage("Please select the Supplier.", "E", false, "F");
                                showFacesMessage("MSG.732", "E", true, "F");
                            }
                        }
                    } else if (Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_PO ||
                               Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_IMPORT_PO) {
                        // For Other source Types
                        if (supplierPage.getValue() != null) {
                            if (docNoPageVar.getValue() != null && docNoPageVar.getValue().toString().length() > 0) {
                                _log.info("ADD DOC");
                                //code to check warehouse compatiblity of PO

                                OperationBinding chkSuppValid =
                                    getBindings().getOperationBinding("chkSupplierInvcCopyReceipt");
                                chkSuppValid.execute();
                                _log.info(" valide resup " + chkSuppValid.getResult());
                                if (chkSuppValid.getResult() != null &&
                                    ((Integer) chkSuppValid.getResult()).compareTo(new Integer(1)) == 0) {

                                } else {
                                    showFacesMessage("Supplier document not received .Can not proceed.", "W", false,
                                                     "F");
                                    return null;
                                }

                                if (Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_IMPORT_PO) {
                                    _log.info("Inside IPO ::::: ");

                                    OperationBinding ipoChk =
                                        getBindings().getOperationBinding("checkForIPODocumentValide");
                                    ipoChk.getParamsMap().put("CldId", paramCldId);
                                    ipoChk.getParamsMap().put("SlocId", paramSlocId);
                                    ipoChk.getParamsMap().put("OrgId", paramOrgId);
                                    ipoChk.getParamsMap().put("SrcDocNo", docNoPageVar.getValue());
                                    ipoChk.execute();
                                    _log.info("Inside IPO check result ::::: " + ipoChk.getResult());
                                    if (ipoChk.getResult() != null &&
                                        "Y".equalsIgnoreCase(ipoChk.getResult().toString())) {
                                    } else {
                                        showFacesMessage("Import PO not valid for following reason" +
                                                         ipoChk.getResult(), "W", false, "F");
                                        return null;
                                    }
                                }

                                OperationBinding chkWh = getBindings().getOperationBinding("isPoWarehouseCompatible");
                                chkWh.execute();
                                System.out.println("chkWh opbinding return=" + chkWh.getResult());
                                if (chkWh.getResult() != null && "Y".equalsIgnoreCase(chkWh.getResult().toString())) {
                                    OperationBinding chksupp =
                                        getBindings().getOperationBinding("isPoSupplierCompatible");
                                    chksupp.execute();
                                    if ("Y".equalsIgnoreCase(chksupp.getResult().toString())) {
                                        //Check for duplicate Docs
                                        OperationBinding chkDup =
                                            getBindings().getOperationBinding("checkForDuplicateDocNo");
                                        chkDup.getParamsMap().put("CldId", paramCldId);
                                        chkDup.getParamsMap().put("SlocId", paramSlocId);
                                        chkDup.getParamsMap().put("OrgId", paramOrgId);
                                        chkDup.getParamsMap().put("SrcType",
                                                                  Integer.parseInt(srcDocType.getValue().toString()));
                                        chkDup.execute();
                                        Object ob = chkDup.getResult();
                                        _log.info("ChkDupli=" + ob);
                                        String res = "N";
                                        if (ob != null) {
                                            res = chkDup.getResult().toString();
                                        }

                                        if (res.equals("N")) {

                                            int itmRCount = 1;
                                            // Check for all item service and consumable
                                            OperationBinding chkCDtlr =
                                                getBindings().getOperationBinding("checkServiceConsumbleItm");
                                            chkCDtlr.execute();
                                            if (chkCDtlr.getResult() != null) {
                                                itmRCount = Integer.parseInt(chkCDtlr.getResult().toString());
                                            }

                                            if (itmRCount == 1) {

                                                //Check if tolerance days has to be checked.Y/N
                                                OperationBinding chkTolD =
                                                    getBindings().getOperationBinding("chkToleranceDays");
                                                chkTolD.getParamsMap().put("CldId", paramCldId);
                                                chkTolD.getParamsMap().put("SlocId", paramSlocId);
                                                chkTolD.getParamsMap().put("orgId", paramOrgId);
                                                chkTolD.execute();
                                                String ret = "0";
                                                if (chkTolD.getResult().toString().equals("Y")) {
                                                    _log.info("--Tolerance to be checked as per MM$PRF--");
                                                    OperationBinding chkTolrDay =
                                                        getBindings().getOperationBinding("validateToleranceDays");
                                                    chkTolrDay.getParamsMap().put("CldId", paramCldId);
                                                    chkTolrDay.getParamsMap().put("SlocId", paramSlocId);
                                                    chkTolrDay.getParamsMap().put("OrgId", paramOrgId);
                                                    chkTolrDay.getParamsMap().put("p_po_no",
                                                                                  docNoPageVar.getValue().toString());
                                                    chkTolrDay.getParamsMap().put("p_rcpt_date",
                                                                                  (Timestamp) rcptDate.getValue());
                                                    chkTolrDay.execute();

                                                    ret = chkTolrDay.getResult().toString();
                                                    _log.info("Is PO acceptable as per tolerance : " + ret);
                                                } else if (chkTolD.getResult().toString().equals("N")) {
                                                    OperationBinding chkDtlr =
                                                        getBindings().getOperationBinding("chkDaysTolerance");
                                                    chkDtlr.execute();
                                                    if (!(chkDtlr.getResult() != null))
                                                        ret = "-1";
                                                    else if (chkDtlr.getResult() != null &&
                                                             chkDtlr.getResult().toString().equals("Y"))
                                                        ret = "1";
                                                    else if (chkDtlr.getResult() != null &&
                                                             chkDtlr.getResult().toString().equals("N"))
                                                        ret = "-2";
                                                    else
                                                        ret = "-1";
                                                } else {
                                                    ret = "-1";
                                                }

                                                if (ret.equals("1")) {

                                                    OperationBinding chkCurCmp =
                                                        getBindings().getOperationBinding("checkCurrCompatibility");
                                                    chkCurCmp.getParamsMap().put("OrgId", paramOrgId);
                                                    chkCurCmp.getParamsMap().put("CldId", paramCldId);
                                                    chkCurCmp.getParamsMap().put("SlocId", paramSlocId);
                                                    chkCurCmp.getParamsMap().put("PoDocId",
                                                                                 docNoPageVar.getValue().toString());
                                                    chkCurCmp.execute();
                                                    if ("Y".equalsIgnoreCase(chkCurCmp.getResult().toString())) {

                                                        OperationBinding addDoc =
                                                            getBindings().getOperationBinding("addDocItems");
                                                        addDoc.getParamsMap().put("CldId", paramCldId);
                                                        addDoc.getParamsMap().put("SlocId", paramSlocId);
                                                        addDoc.getParamsMap().put("OrgId", paramOrgId);
                                                        addDoc.getParamsMap().put("srcType",
                                                                                  srcDocType.getValue().toString());
                                                        addDoc.execute();

                                                        OperationBinding execItm =
                                                            getBindings().getOperationBinding("Execute");
                                                        execItm.execute();

                                                        // Add Currency First time when Purchase order document add and Org Not use Gate Entry.

                                                        _log.info(" inside get select row count   ");

                                                        OperationBinding rowCountBind =
                                                            getBindings().getOperationBinding("getRcptSrcCount");
                                                        rowCountBind.execute();
                                                        if (rowCountBind.getResult() != null) {
                                                            _log.info(" inside get select row count value    ::::::  " +
                                                                      rowCountBind.getResult());
                                                            Integer count =
                                                                Integer.parseInt(rowCountBind.getResult().toString());
                                                            if (count.compareTo(new Integer(1)) == 0) {
                                                                OperationBinding opcurr =
                                                                    getBindings().getOperationBinding("setCurrIdSpPoToMtlRcpt");
                                                                opcurr.getParamsMap().put("value", SOURCE_DOC_TYPE_PO);
                                                                opcurr.execute();
                                                            }

                                                        }


                                                    } else {
                                                        //showFacesMessage("Purchase Orders having the same currency can only be added.", "E", false, "F");
                                                        showFacesMessage("MSG.733", "E", true, "F");
                                                    }
                                                } else if (ret.equals("-2")) {
                                                    showFacesMessage("Cannot accept Document as it is before or after tolerance days",
                                                                     "E", false, "F");
                                                    // showFacesMessage("MSG.734", "E", true, "F");
                                                } else if (ret.equals("-1")) {
                                                    showFacesMessage("Something went Wrong,Please Contact to ESS (Tolerance Days)",
                                                                     "E", false, "F");
                                                    //showFacesMessage("MSG.734", "E", true, "F");
                                                }
                                            } else {
                                                showFacesMessage("No stockable item available in the selected document.",
                                                                 "E", false, "F");
                                                //  showFacesMessage("MSG.735", "E", true, "F");
                                            }
                                        } else { //
                                            //showFacesMessage("Duplicate Document No.", "E", false, "F");
                                            showFacesMessage("MSG.735", "E", true, "F");
                                        } //
                                        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptItmTable);
                                    } else {
                                        showFacesMessage("PO is not Relate to this Supplier", "E", false, "F");
                                    }
                                } else {
                                    showFacesMessage("PO is not Relate to this Warehouse", "E", false, "F");
                                }
                            } else {
                                //showFacesMessage("Select/Enter the Document No.", "E", false, "F");
                                showFacesMessage("MSG.736", "E", true, "F");
                            }
                        } else {
                            //showFacesMessage("Please select the Supplier.", "E", false, "F");
                            showFacesMessage("MSG.732", "E", true, "F");
                        }
                    }
                    
                    
                    else if (Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_JITR) {
                                            // For Other source Types
                                            if (supplierPage.getValue() != null) {
                                                if (docNoPageVar.getValue() != null && docNoPageVar.getValue().toString().length() > 0) {
                                                    _log.info("ADD DOC");
                                                    //code to check warehouse compatiblity of PO

                                                    OperationBinding chkSuppValid =
                                                        getBindings().getOperationBinding("chkSupplierInvcCopyReceipt");
                                                    chkSuppValid.execute();
                                                    _log.info(" valide resup " + chkSuppValid.getResult());
                                                    if (chkSuppValid.getResult() != null &&
                                                        ((Integer) chkSuppValid.getResult()).compareTo(new Integer(1)) == 0) {

                                                    } else {
                                                        showFacesMessage("Supplier document not received .Can not proceed.", "W", false,
                                                                         "F");
                                                        return null;
                                                    }
                                                    
                                                    
                                                   OperationBinding chkDup =  getBindings().getOperationBinding("checkForDuplicateDocNo");
                                                    chkDup.getParamsMap().put("CldId", paramCldId);
                                                    chkDup.getParamsMap().put("SlocId", paramSlocId);
                                                    chkDup.getParamsMap().put("OrgId", paramOrgId);
                                                    chkDup.getParamsMap().put("SrcType",
                                                                          Integer.parseInt(srcDocType.getValue().toString()));
                                                    chkDup.execute();
                                                    Object ob = chkDup.getResult();
                                                    _log.info("ChkDupli=" + ob);
                                                    String res = "N";
                                                    if (ob != null) {
                                                    res = chkDup.getResult().toString();
                                                    }

                                                    if (res.equals("N")) {
                                                        
                                                                OperationBinding addDoc =
                                                                    getBindings().getOperationBinding("populateRcptItmfromForJITR");
                                                                addDoc.getParamsMap().put("CldId", paramCldId);
                                                                addDoc.getParamsMap().put("SlocId", paramSlocId);
                                                                addDoc.getParamsMap().put("OrgId", paramOrgId);
                                                                addDoc.execute();
                                                                if (addDoc.getResult() != null &&
                                                                    ((Integer) addDoc.getResult()).compareTo(new Integer(1)) == 0) {
                                                                    // Add Currency First time when Purchase order document add and Org Not use Gate Entry.

                                                                    _log.info(" inside get select row count   ");

                                                                    OperationBinding rowCountBind =
                                                                        getBindings().getOperationBinding("getRcptSrcCount");
                                                                    rowCountBind.execute();
                                                                    if (rowCountBind.getResult() != null) {
                                                                        _log.info(" inside get select row count value    ::::::  " +
                                                                                  rowCountBind.getResult());
                                                                        Integer count =
                                                                            Integer.parseInt(rowCountBind.getResult().toString());
                                                                        if (count.compareTo(new Integer(1)) == 0) {
                                                                            OperationBinding opcurr =
                                                                                getBindings().getOperationBinding("setCurrIdSpPoToMtlRcpt");
                                                                            opcurr.getParamsMap().put("value", SOURCE_DOC_TYPE_JITR);
                                                                            opcurr.execute();
                                                                        }

                                                                    }          
                                                                    
                                                                    
                                                                    
                                                                    
                                                                }else{
                                                                    showFacesMessage("Something wrong ", "E", false, "F");
                                                                    return null;
                                                                }
                                                        

                                                    
                                                            } else { //
                                                                //showFacesMessage("Duplicate Document No.", "E", false, "F");
                                                                showFacesMessage("MSG.735", "E", true, "F");
                                                            } //
                                                            AdfFacesContext.getCurrentInstance().addPartialTarget(rcptItmTable);
                                                } else {
                                                    //showFacesMessage("Select/Enter the Document No.", "E", false, "F");
                                                    showFacesMessage("MSG.736", "E", true, "F");
                                                }
                                            } else {
                                                //showFacesMessage("Please select the Supplier.", "E", false, "F");
                                                showFacesMessage("MSG.732", "E", true, "F");
                                            }
                                        }
                                        
                    
                    
                    else if (Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_TO) {
                        if (whIdsrcPage.getValue() != null) {
                            System.out.println("DocNo=" + docNoPageVar.getValue());
                            if (docNoPageVar.getValue() != null && docNoPageVar.getValue().toString().length() > 0) {
                                OperationBinding chkDup = getBindings().getOperationBinding("checkForDuplicateDocNo");
                                chkDup.getParamsMap().put("CldId", paramCldId);
                                chkDup.getParamsMap().put("SlocId", paramSlocId);
                                chkDup.getParamsMap().put("OrgId", paramOrgId);
                                chkDup.getParamsMap().put("SrcType", SOURCE_DOC_TYPE_TO);
                                chkDup.execute();
                                if (chkDup.getResult().toString().equals("N")) {

                                    //Link all items to Organisation.
                                    OperationBinding linkitm =
                                        getBindings().getOperationBinding("LinkItemtoOrganisation");
                                    linkitm.getParamsMap().put("CldId", paramCldId);
                                    linkitm.getParamsMap().put("SlocId", paramSlocId);
                                    linkitm.getParamsMap().put("OrgId", paramOrgId);
                                    linkitm.getParamsMap().put("HoOrgId", paramHoOrgId);
                                    linkitm.getParamsMap().put("UsrId", paramUsrId);
                                    linkitm.execute();

                                    OperationBinding chkItmExist = getBindings().getOperationBinding("chkItmTrfQty");
                                    chkItmExist.execute();
                                    if (chkItmExist.getErrors().size() == 0 && chkItmExist.getResult() != null) {
                                        if (chkItmExist.getResult().toString().equals("Y")) {
                                            OperationBinding chkCurrConv = getBindings().getOperationBinding("chkCurrencyConversion");
                                            chkCurrConv.getParamsMap().put("CldId", paramCldId);
                                            chkCurrConv.getParamsMap().put("SlocId", paramSlocId);
                                            chkCurrConv.getParamsMap().put("OrgId", paramOrgId);
                                            chkCurrConv.execute();
                                            _log.info(" xysmnnnn  "+chkCurrConv.getResult());
                                            if(chkCurrConv.getResult()!=null && ((Integer)chkCurrConv.getResult()).compareTo(new Integer(0))==0){
                                                
                                            _log.info(" gfgf  "+chkCurrConv.getResult());
                                                showFacesMessage("Currency conversion rate not define between both organization. Can not add document.",
                                                                 "E", false, "F");
                                                return null;
                                            }
                                                                                        
                                            OperationBinding addDoc = getBindings().getOperationBinding("addDocItems");
                                            addDoc.getParamsMap().put("CldId", paramCldId);
                                            addDoc.getParamsMap().put("SlocId", paramSlocId);
                                            addDoc.getParamsMap().put("OrgId", paramOrgId);
                                            addDoc.getParamsMap().put("srcType", SOURCE_DOC_TYPE_TO);
                                            addDoc.execute();
                                        } else {
                                            showFacesMessage("No Pending Item in this Transfer Order which have been Issued.",
                                                             "E", false, "F");
                                        }
                                    } else {
                                        showFacesMessage("Something went Wrong. Please Contact to ESS!", "E", false,
                                                         "F");
                                    }
                                } else if (chkDup.getResult().toString().equals("Y")) {
                                    //showFacesMessage("Duplicate Document No.", "E", false, "F");
                                    showFacesMessage("MSG.735", "E", true, "F");
                                } else if (chkDup.getResult().toString().equals("O")) {
                                    //showFacesMessage("Select/Enter the Transfer Order No.", "E", false, "F");
                                    showFacesMessage("MSG.737", "E", true, "F");
                                }
                            } else {
                                //showFacesMessage("Select/Enter the Transfer Order No.", "E", false, "F");
                                showFacesMessage("MSG.737", "E", true, "F");
                            }
                        } else {
                            // showFacesMessage("Please Select the Source Warehouse.", "E", false, "F");
                            showFacesMessage("MSG.738", "E", true, "F");
                        }
                    } else if (Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_MRN) {
                        if (reqmtAreaPage.getValue() != null && reqmtAreaPage.getValue() != "") {
                            if (docNoPageVar.getValue() != null && docNoPageVar.getValue().toString().length() > 0) {
                                OperationBinding chkDup = getBindings().getOperationBinding("checkForDuplicateDocNo");
                                chkDup.getParamsMap().put("CldId", paramCldId);
                                chkDup.getParamsMap().put("SlocId", paramSlocId);
                                chkDup.getParamsMap().put("OrgId", paramOrgId);
                                chkDup.getParamsMap().put("SrcType", SOURCE_DOC_TYPE_MRN);
                                chkDup.execute();
                                if (chkDup.getResult().toString().equals("N")) {
                                    OperationBinding addDoc = getBindings().getOperationBinding("addDocItems");
                                    addDoc.getParamsMap().put("CldId", paramCldId);
                                    addDoc.getParamsMap().put("SlocId", paramSlocId);
                                    addDoc.getParamsMap().put("OrgId", paramOrgId);
                                    addDoc.getParamsMap().put("srcType", SOURCE_DOC_TYPE_MRN);
                                    addDoc.execute();

                                } else if (chkDup.getResult().toString().equals("Y")) {
                                    //showFacesMessage("Duplicate Document No.", "E", false, "F");
                                    showFacesMessage("MSG.735", "E", true, "F");
                                } else if (chkDup.getResult().toString().equals("O")) {
                                    //showFacesMessage("Select/Enter the MRN No.", "E", false, "F");
                                    showFacesMessage("MSG.739", "E", true, "F");
                                }

                            } else {
                                //showFacesMessage("Select the MRN No.", "E", false, "F");
                                showFacesMessage("MSG.740", "E", true, "F");
                            }
                        } else {
                            //showFacesMessage("Select the Requirement Area.", "E", false, "F");
                            showFacesMessage("MSG.741", "E", true, "F");
                        }
                    } else if (Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_GPR) {
                        _log.info("SOURCE_DOC_TYPE_GPR 1");
                        if (supplierPage.getValue() != null) {
                            if (docNoPageVar.getValue() != null && docNoPageVar.getValue().toString().length() > 0) {
                                //                                OperationBinding chkDup = getBindings().getOperationBinding("checkForDuplicateDocNo");
                                //                                chkDup.getParamsMap().put("CldId", paramCldId);
                                //                                chkDup.getParamsMap().put("SlocId", paramSlocId);
                                //                                chkDup.getParamsMap().put("OrgId", paramOrgId);
                                //                                chkDup.getParamsMap().put("SrcType", SOURCE_DOC_TYPE_GPR);
                                //                                chkDup.execute();
                                //                                String ret = "N";
                                //                                if (ret.equals("N")) {
                                _log.info("SOURCE_DOC_TYPE_GPR 2");
                                OperationBinding addDoc = getBindings().getOperationBinding("addDocItems");
                                addDoc.getParamsMap().put("CldId", paramCldId);
                                addDoc.getParamsMap().put("SlocId", paramSlocId);
                                addDoc.getParamsMap().put("OrgId", paramOrgId);
                                addDoc.getParamsMap().put("srcType", SOURCE_DOC_TYPE_GPR);
                                addDoc.execute();

                                //                                } else if (chkDup.getResult().toString().equals("Y")) {
                                //                                    //showFacesMessage("Duplicate Document No.", "E", false, "F");
                                //                                    showFacesMessage("MSG.735", "E", true, "F");
                                //
                                //                                }
                            } else {
                                //showFacesMessage("Select/Enter the Document No.", "E", false, "F");
                                showFacesMessage("MSG.736", "E", true, "F");
                            }
                        } else {
                            //showFacesMessage("Please select the Supplier.", "E", false, "F");
                            showFacesMessage("Please select the Customer.", "E", false, "F");
                        }
                    }

                    else if (Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_FGR) {
                        if (reqmtAreaPage.getValue() != null &&
                            reqmtAreaPage.getValue().toString().trim().length() != 0) {
                            if (itmNameBindVar.getValue() != null) {
                                if (uomBindVar.getValue() != null) {
                                    if (itmQty.getValue() != null) {
                                        if (itmLndPriceBind.getValue() != null) {

                                            _log.info("qty=" + itmQty.getValue());
                                            _log.info("qty=" + itmLndPriceBind.getValue());
                                            Number qty = (Number) itmQty.getValue();
                                            Number price = (Number) itmLndPriceBind.getValue();

                                            if (qty.compareTo(new Number(0)) == 1) {
                                                if (price.compareTo(new Number(0)) == 1) {

                                                    if (rcptSrcTable.getRowCount() ==
                                                        0) {
                                                        //Generate temp Doc for WIthout PO
                                                        OperationBinding genDocIdOp =
                              getBindings().getOperationBinding("generateTempDocId");
                                                        genDocIdOp.getParamsMap().put("UsrId", paramUsrId);
                                                        genDocIdOp.getParamsMap().put("CldId", paramCldId);
                                                        genDocIdOp.getParamsMap().put("SlocId", paramSlocId);
                                                        genDocIdOp.getParamsMap().put("OrgId", paramOrgId);
                                                        genDocIdOp.getParamsMap().put("WhId",
                                                                                      whIdPage.getValue().toString());
                                                        genDocIdOp.getParamsMap().put("DocTypeSrc",
                                                                                      SOURCE_DOC_TYPE_FGR);
                                                        genDocIdOp.execute();
                                                    }
                                                    OperationBinding chkDupli =
                                                        getBindings().getOperationBinding("chkDupliItm");
                                                    chkDupli.getParamsMap().put("ItmName",
                                                                                itmNameBindVar.getValue().toString());
                                                    chkDupli.execute();
                                                    String chk = "N";
                                                    if (chkDupli.getErrors().size() == 0 &&
                                                        chkDupli.getResult() != null)
                                                        chk = (String) chkDupli.getResult();
                                                    if (chk.equals("N")) {
                                                        //OperationBinding addItmOp = getBindings().getOperationBinding("addItemToRcptForOb");addItemToRcptForFGR
                                                        OperationBinding addItmOp =
                                                            getBindings().getOperationBinding("addItemToRcptForFGR");
                                                        addItmOp.getParamsMap().put("ItmName",
                                                                                    itmNameBindVar.getValue().toString());
                                                        addItmOp.getParamsMap().put("ItmUom",
                                                                                    uomBindVar.getValue().toString());
                                                        addItmOp.getParamsMap().put("ItmQty",
                                                                                    (Number) itmQty.getValue());
                                                        addItmOp.getParamsMap().put("ItmPrice",
                                                                                    (Number) itmLndPriceBind.getValue());
                                                        addItmOp.execute();

                                                        if (addItmOp.getErrors().isEmpty()) {
                                                            _log.info("Item added");
                                                            OperationBinding opbind =
                                                                getBindings().getOperationBinding("setTransValueToNull");
                                                            opbind.execute();
                                                            itmNameBindVar.setValue("");
                                                            uomBindVar.setValue("");
                                                            uomBindVar.resetValue();
                                                            itmQty.setValue("");
                                                            itmLndPriceBind.setValue("");
                                                            AdfFacesContext.getCurrentInstance().addPartialTarget(itmNameBindVar);
                                                            AdfFacesContext.getCurrentInstance().addPartialTarget(uomBindVar);
                                                            AdfFacesContext.getCurrentInstance().addPartialTarget(itmQty);
                                                            AdfFacesContext.getCurrentInstance().addPartialTarget(itmLndPriceBind);
                                                        }

                                                    } else {
                                                        showFacesMessage("MSG.726", "E", true, "F");
                                                    }
                                                } else {
                                                    //showFacesMessage("Item Price must be greater than 0.", "E", false, "F");
                                                    showFacesMessage("Item Price must be greater than 0.", "E", false,
                                                                     "F");
                                                }
                                            } else {
                                                //showFacesMessage("Item Quantity must be greater than 0.", "E", false, "F");
                                                showFacesMessage("MSG.742", "E", true, "F");
                                            }

                                        } else {
                                            //showFacesMessage("Item Price must be entered.", "E", false, "F");
                                            showFacesMessage("Item Price must be entered.", "E", false, "F");
                                        }
                                    } else {
                                        //showFacesMessage("Item Quantity must be entered.", "E", false, "F");
                                        showFacesMessage("MSG.729", "E", true, "F");
                                    }
                                } else {
                                    //  showFacesMessage("Select the Unit.", "E", false, "F");
                                    showFacesMessage("MSG.731", "E", true, "F");
                                }
                            } else {
                                //showFacesMessage("Select the Item.", "E", false, "F");
                                showFacesMessage("MSG.659", "E", true, "F");
                            }
                        } else {
                            //showFacesMessage("Select the Requirement Area.", "E", false, "F");
                            showFacesMessage("MSG.741", "E", true, "F");
                        }
                    } else if (Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_CPO) //CPO
                    {
                        //Code to add Item from CPO
                        if (docNoPageVar.getValue() != null && docNoPageVar.getValue().toString().length() > 0) {

                            //Check for duplicate Docs
                            OperationBinding chkDup = getBindings().getOperationBinding("checkForDuplicateDocNo");
                            chkDup.getParamsMap().put("CldId", paramCldId);
                            chkDup.getParamsMap().put("SlocId", paramSlocId);
                            chkDup.getParamsMap().put("OrgId", paramOrgId);
                            chkDup.getParamsMap().put("SrcType", Integer.parseInt(srcDocType.getValue().toString()));
                            chkDup.execute();
                            Object ob = chkDup.getResult();
                            _log.info("ChkDupli=" + ob);
                            String res = "N";
                            if (ob != null) {
                                res = chkDup.getResult().toString();
                            }
                            if (res.equals("N")) {


                                // Check for currency campatibility for same CPO

                                OperationBinding chkWh = getBindings().getOperationBinding("isPoWarehouseCompatible");
                                chkWh.execute();
                                System.out.println("chkWh opbinding return=" + chkWh.getResult());
                                if (chkWh.getResult() != null && "Y".equalsIgnoreCase(chkWh.getResult().toString())) {

                                    OperationBinding chkCurCmpCPO =
                                        getBindings().getOperationBinding("checkCurrCompatibilityForCPO");
                                    chkCurCmpCPO.getParamsMap().put("OrgId", paramOrgId);
                                    chkCurCmpCPO.getParamsMap().put("CldId", paramCldId);
                                    chkCurCmpCPO.getParamsMap().put("SlocId", paramSlocId);
                                    chkCurCmpCPO.getParamsMap().put("PoDocId", docNoPageVar.getValue().toString());
                                    chkCurCmpCPO.execute();
                                    if ("Y".equalsIgnoreCase(chkCurCmpCPO.getResult().toString())) {
                                        OperationBinding addDoc = getBindings().getOperationBinding("addDocItems");
                                        addDoc.getParamsMap().put("CldId", paramCldId);
                                        addDoc.getParamsMap().put("SlocId", paramSlocId);
                                        addDoc.getParamsMap().put("OrgId", paramOrgId);
                                        addDoc.getParamsMap().put("srcType", srcDocType.getValue().toString());
                                        addDoc.execute();

                                        OperationBinding execItm = getBindings().getOperationBinding("Execute");

                                        execItm.execute();

                                        _log.info(" inside get CPO select row count   ");

                                        OperationBinding rowCountBind1 =
                                            getBindings().getOperationBinding("getRcptSrcCount");
                                        rowCountBind1.execute();
                                        if (rowCountBind1.getResult() != null) {
                                            _log.info(" inside get selectCPO  row count value    ::::::  " +
                                                      rowCountBind1.getResult());
                                            Integer count = Integer.parseInt(rowCountBind1.getResult().toString());
                                            if (count.compareTo(new Integer(1)) == 0) {
                                                OperationBinding opcurr =
                                                    getBindings().getOperationBinding("setCurrIdSpPoToMtlRcpt");
                                                opcurr.getParamsMap().put("value", SOURCE_DOC_TYPE_CPO);
                                                opcurr.execute();
                                            }

                                        }
                                    } else {
                                        //showFacesMessage("Cash Purchase Orders having the same currency can only be added.", "E", false, "F");
                                        showFacesMessage("MSG.1298", "E", true, "F");
                                    }
                                } else {

                                    showFacesMessage("Cash Purchase order is not Relate to this Warehouse", "E", false,
                                                     "F");
                                }
                            } else {
                                //showFacesMessage("Duplicate Document No.", "E", false, "F");
                                showFacesMessage("MSG.735", "E", true, "F");
                            }
                            AdfFacesContext.getCurrentInstance().addPartialTarget(rcptItmTable);

                        } else {
                            //showFacesMessage("Select/Enter the Document No.", "E", false, "F");
                            showFacesMessage("MSG.736", "E", true, "F");
                        }

                    }
                } else {
                    //showFacesMessage("Select the Document Type.", "E", false, "F");
                    showFacesMessage("MSG.743", "E", true, "F");
                }
            }
        } else {
            // showFacesMessage("Select the Receipt Type.", "E", false, "F");
            showFacesMessage("MSG.744", "E", true, "F");
        }

        return null;
    }


    /**
     *  Method called on save action.
     *   1.check if any item doesn't have received quantity.If yes, show message for removal.!!
     *   2.check if all items have received/rejected quantity.If no then show message for deletion.!!
     *   3.check if serial no's are entered for all the serialized items(in case QC reqd), if no, then show message!!
     *   4.check whether all the non serialized items are allocated LOT/BIN!!
     *   5.check in case received after QC then LOT/BIN allocated?!!
     *   6.check if any reworkable qty is present for any item in any Document.If yes, Update the receipt stat to PENDING FOR REWORK(381)!!
     *   7.check if all items are received(no pending reworkable qty) , if no, then proceed for QC(receipt stat=380)!!
     *   8.if after qc done, finally call for Workflow. If user != final wf user then rcpt stat updated to 382(Forwarded for approval)!!
     *   9.After stock updated (Final WF user approved), rcpt stat is changed to 383. And can be consumed by Purchase Invoice.    !!
     * */
    public String saveRcptAction() {
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer paramUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String tfMode = resolvEl("#{pageFlowScope.ADD_EDIT_MODE}");
        String paramBinChk = resolvEl("#{pageFlowScope.GLBL_ORG_BIN_CHK}");

        Integer fyNo = 0;
        if (rcptDate.getValue() != null) {

            if (rcptSrcType.getValue() != null) {

                OperationBinding isDocPr = getBindings().getOperationBinding("isDocumentPresent");
                isDocPr.execute();

                if ("Y".equals(isDocPr.getResult().toString())) {

                    if (Integer.parseInt(rcptSrcType.getValue().toString()) == RCPT_TYPE_OB ||
                        Integer.parseInt(srcDocType.getValue().toString()) == (SOURCE_DOC_TYPE_FGR) ||
                        Integer.parseInt(srcDocType.getValue().toString()) == (SOURCE_DOC_TYPE_MRN) ||
                        Integer.parseInt(srcDocType.getValue().toString()) == (SOURCE_DOC_TYPE_GPR)) { //Opening Balance or FGR
                        OperationBinding rFy = getBindings().getOperationBinding("getFYid");
                        rFy.getParamsMap().put("CldId", paramCldId);
                        rFy.getParamsMap().put("OrgId", paramOrgId);
                        rFy.getParamsMap().put("geDate", (Timestamp) rcptDate.getValue());
                        rFy.getParamsMap().put("Mode", "V");
                        rFy.execute();
                        Object fy = rFy.getResult();

                        fyNo = Integer.parseInt(fy.toString());

                        OperationBinding rcptNoOp = getBindings().getOperationBinding("getRcptNo");
                        rcptNoOp.getParamsMap().put("SlocId", paramSlocId);
                        rcptNoOp.getParamsMap().put("CldId", paramCldId);
                        rcptNoOp.getParamsMap().put("OrgId", paramOrgId);
                        rcptNoOp.getParamsMap().put("WhId", getWhIdPage().getValue().toString());
                        rcptNoOp.getParamsMap().put("fyId", fyNo);
                        rcptNoOp.execute();

                        Object rno = rcptNoOp.getResult();
                        _log.info("--GeNo--" + rno);

                        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptNo);


                        OperationBinding exec = getBindings().getOperationBinding("Commit");
                        exec.execute();
                        _log.info("commit error=" + exec.getErrors());
                        if (exec.getErrors().isEmpty()) {
                            String retval = callWfMethods(paramSlocId, paramCldId, paramOrgId, paramUsrId, "S");
                            if ("Y".equals(retval)) {

                                srNoBind.setValue("");
                                srNoBind.resetValue();
                                AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                                // exec.execute(); commented on 23-may-2014
                                RequestContext context = RequestContext.getCurrentInstance();
                                context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                // showFacesMessage("Receipt "+rno+" Saved Succesfully", "I", false, "F");
                                String a = resolvEl("#{bundle['MSG.745']}");
                                String b = resolvEl("#{bundle['MSG.227']}");
                                showFacesMessage(a + rno + b, "I", false, "F");
                            } else {
                                _log.info("Error in saving WF related data");
                            }


                        } else {
                            _log.info("Error during commit");
                        }
                    } else { //For all other rcpt Types
                        if (srcDocType.getValue() != null) {

                            Integer scDocType = Integer.parseInt(srcDocType.getValue().toString());

                            if (scDocType.equals(SOURCE_DOC_TYPE_PO) || scDocType.equals(SOURCE_DOC_TYPE_WOUT_PO) || scDocType.equals(SOURCE_DOC_TYPE_IMPORT_PO) || scDocType.equals(SOURCE_DOC_TYPE_JITR)) { // IMPORt PO
                                OperationBinding rFy = getBindings().getOperationBinding("getFYid");
                                rFy.getParamsMap().put("CldId", paramCldId);
                                rFy.getParamsMap().put("OrgId", paramOrgId);
                                rFy.getParamsMap().put("geDate", (Timestamp) rcptDate.getValue());
                                rFy.getParamsMap().put("Mode", "V");
                                rFy.execute();

                                Object fy = rFy.getResult();

                                fyNo = Integer.parseInt(fy.toString());

                                OperationBinding chkRcvd = getBindings().getOperationBinding("checkForRcvdQtyPresent");
                                chkRcvd.getParamsMap().put("CldId", paramCldId);
                                chkRcvd.getParamsMap().put("slocId", paramSlocId);
                                chkRcvd.getParamsMap().put("OrgId", paramOrgId);
                                chkRcvd.getParamsMap().put("whId", getWhIdPage().getValue().toString());
                                chkRcvd.execute();


                                HashSet h = (HashSet) chkRcvd.getResult();
                                if (!h.isEmpty()) {
                                    _log.info("HashSet not empty" + h);

                                    StringBuilder msg =
                                        new StringBuilder("<html><body><p>Some items do not have received/rejected/reworkable quantity for the following Documents : <b>" +
                                                          h + "</b> </p>"); //
                                    msg.append("<ul> <li>Click YES to delete.</li>");
                                    msg.append("<li>Click NO to continue.</li></ul>");
                                    msg.append("</body></html>");


                                    msgpop = msg.toString();
                                    /*    FacesMessage fm=new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg);
                            FacesContext.getCurrentInstance().addMessage(null, fm); */
                                    showPopup(confDlvQtyChkPopUp, true);
                                    _log.info("After msg popup");

                                } else { //if received/rej/rwk qty present
                                    String txnvalid = "Y";


                                    if ("N".equals(authStat.getValue().toString())) {

                                        //check if all the serial nos are entered
                                        //if yes,
                                        OperationBinding chkSerOp =
                                            getBindings().getOperationBinding("checkSerialEnteredForSave");
                                        chkSerOp.getParamsMap().put("CldId", paramCldId);
                                        chkSerOp.getParamsMap().put("SlocId", paramSlocId);
                                        chkSerOp.getParamsMap().put("OrgId", paramOrgId);
                                        chkSerOp.execute();
                                        _log.info("---txninvalid---" + txnvalid);
                                        if ("N".equals(chkSerOp.getResult().toString())) {

                                            String chkReworkableN = "N";
                                            OperationBinding chkRwkable =
                                                getBindings().getOperationBinding("chkRWkableQtyPresent");
                                            chkRwkable.execute();
                                            if (chkRwkable.getResult() != null) {
                                                _log.info("chkReworkableN1  :::::::: ");
                                                chkReworkableN = chkRwkable.getResult().toString();
                                            }

                                            _log.info("chkReworkableN1   :  " + chkReworkableN);
                                            //  if("Y".equals(chkReworkable.getValue().toString())){//if reworkable qty present
                                            if ("Y".equalsIgnoreCase(chkReworkableN)) {
                                                OperationBinding setStatRcptOp =
                                                    getBindings().getOperationBinding("setRcptStatus");
                                                setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_REWORK);
                                                setStatRcptOp.execute();
                                            } else {

                                                // if(Integer.parseInt(rcptStat.getValue().toString())==RCPT_STATUS_REWORK && "N".equals(chkReworkable.getValue().toString())){
                                                if (Integer.parseInt(rcptStat.getValue().toString()) ==
                                                    RCPT_STATUS_REWORK && "N".equalsIgnoreCase(chkReworkableN)) {
                                                    OperationBinding setStatRcptOp =
                                                        getBindings().getOperationBinding("setRcptStatus");
                                                    setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_PEND);
                                                    setStatRcptOp.execute();
                                                }

                                                if (scDocType.equals(SOURCE_DOC_TYPE_PO) ||
                                                    scDocType.equals(SOURCE_DOC_TYPE_IMPORT_PO) ||
                                                    scDocType.equals(SOURCE_DOC_TYPE_JITR)) {

                                                    //validation if all values entered.
                                                    _log.info("QC REQD--" + checkQcReqd.getValue().toString());
                                                    if ("Y".equalsIgnoreCase(checkQcReqd.getValue().toString())) {
                                                        OperationBinding chkRcvItm =
                                                            getBindings().getOperationBinding("chkIsAnyItmForQc");
                                                        chkRcvItm.execute();
                                                        String chkQc = "Y";
                                                        if (chkRcvItm.getErrors().size() == 0 &&
                                                            chkRcvItm.getResult() != null)
                                                            chkQc = (String) chkRcvItm.getResult();
                                                        if (chkQc.equals("Y")) {
                                                            OperationBinding setStatRcptOp =
                                                                getBindings().getOperationBinding("setRcptStatus");
                                                            setStatRcptOp.getParamsMap().put("stat",
                                                                                             RCPT_STATUS_QCPEND);
                                                            setStatRcptOp.execute();
                                                        }
                                                    } else {
                                                        _log.info("QC NOT REQD");
                                                        /*   OperationBinding setStatRcptOp = getBindings().getOperationBinding("setRcptStatus");
                                                 setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_PEND);
                                                 setStatRcptOp.execute();
                                     } */

                                                    }
                                                } else {
                                                    OperationBinding setStatRcptOp =
                                                        getBindings().getOperationBinding("setRcptStatus");
                                                    setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_PEND);
                                                    setStatRcptOp.execute();
                                                }

                                            } //end else
                                        } else {

                                            // code for partioal sr no
                                            _log.info(" all serial no not enter properly");
                                            OperationBinding setStatRcptOp =
                                                getBindings().getOperationBinding("setRcptStatus");
                                            setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_PEND);
                                            setStatRcptOp.execute();
                                            // txnvalid = "N";
                                        }
                                    } else if ("Y".equalsIgnoreCase(authStat.getValue().toString())) {

                                        //   txnvalid="N";
                                        //check if lot allotted

                                        OperationBinding chkFinalRcptQty =
                                            getBindings().getOperationBinding("checkAnyFinalRcptQtyPresent");
                                        chkFinalRcptQty.getParamsMap().put("CldId", paramCldId);
                                        chkFinalRcptQty.getParamsMap().put("SlocId", paramSlocId);
                                        chkFinalRcptQty.getParamsMap().put("OrgId", paramOrgId);
                                        chkFinalRcptQty.execute();
                                        if ("N".equalsIgnoreCase(chkFinalRcptQty.getResult().toString())) {

                                        } else {

                                            OperationBinding chkLot =
                                                getBindings().getOperationBinding("checkLotSelected");
                                            chkLot.getParamsMap().put("CldId", paramCldId);
                                            chkLot.getParamsMap().put("SlocId", paramSlocId);
                                            chkLot.getParamsMap().put("OrgId", paramOrgId);
                                            chkLot.execute();
                                            System.out.println("Error on execute checklotselect=" + chkLot.getErrors());
                                            System.out.println("Result on lot=" + chkLot.getResult());
                                            // String Lotchk ="N";
                                            if ("N".equalsIgnoreCase(chkLot.getResult().toString())) {
                                                //  if ("N".equalsIgnoreCase(Lotchk)){
                                                OperationBinding chkBin =
                                                    getBindings().getOperationBinding("checkBinSelected");
                                                chkBin.getParamsMap().put("CldId", paramCldId);
                                                chkBin.getParamsMap().put("SlocId", paramSlocId);
                                                chkBin.getParamsMap().put("OrgId", paramOrgId);
                                                chkBin.getParamsMap().put("Binchk", paramBinChk);
                                                chkBin.execute();

                                                if ("N".equalsIgnoreCase(chkBin.getResult().toString())) {
                                                    OperationBinding chkSr =
                                                        getBindings().getOperationBinding("checkSerialSelected");
                                                    chkSr.getParamsMap().put("CldId", paramCldId);
                                                    chkSr.getParamsMap().put("SlocId", paramSlocId);
                                                    chkSr.getParamsMap().put("OrgId", paramOrgId);
                                                    chkSr.getParamsMap().put("Binchk", paramBinChk);
                                                    chkSr.execute();
                                                    if ("N".equalsIgnoreCase(chkSr.getResult().toString())) {
                                                        //check if all entered
                                                        OperationBinding exec =
                                                            getBindings().getOperationBinding("Commit");
                                                        exec.execute();
                                                        if (exec.getErrors().size() == 0) {
                                                            OperationBinding execUpl =
                                                                getBindings().getOperationBinding("updateLndCost");
                                                            execUpl.getParamsMap().put("cldId", paramCldId);
                                                            execUpl.getParamsMap().put("SlocId", paramSlocId);
                                                            execUpl.getParamsMap().put("OrgId", paramOrgId);
                                                            execUpl.execute();

                                                            /**
                                       * for populate rcpt calculation value
                                       */

                                                            OperationBinding popCalc =
                                                                getBindings().getOperationBinding("populateCalculations");
                                                            popCalc.getParamsMap().put("p_cld_id", paramCldId);
                                                            popCalc.getParamsMap().put("p_sloc_id", paramSlocId);
                                                            popCalc.getParamsMap().put("p_org_id", paramOrgId);
                                                            popCalc.getParamsMap().put("p_usr_id", paramUsrId);
                                                            popCalc.execute();
                                                        }
                                                        //exec.execute();
                                                        OperationBinding execUp =
                                                            getBindings().getOperationBinding("updateStockForOthers");
                                                        execUp.getParamsMap().put("cldId", paramCldId);
                                                        execUp.getParamsMap().put("SlocId", paramSlocId);
                                                        execUp.getParamsMap().put("OrgId", paramOrgId);
                                                        execUp.execute();
                                                        Object o = execUp.getResult();
                                                        _log.info("Stock update called :" + o);

                                                        if (execUp.getErrors().isEmpty() && o != null) {

                                                            //if(scDocType.equals(SOURCE_DOC_TYPE_PO))
                                                            // {

                                                            OperationBinding setBalandTmpQty =
                                                                getBindings().getOperationBinding("setBalandTmpQty");
                                                            setBalandTmpQty.execute();

                                                            //    }
                                                            OperationBinding chkProv =
                                                                getBindings().getOperationBinding("doCrtProvVourOnRcpt");
                                                            chkProv.execute();
                                                            if (chkProv.getResult() != null &&
                                                                chkProv.getResult().equals("Y")) {
                                                                OperationBinding genProvVou =
                                                                    getBindings().getOperationBinding("insGlProvVou");
                                                                genProvVou.execute();
                                                                Object retu = null;
                                                                if (genProvVou.getErrors().size() == 0 &&
                                                                    genProvVou.getResult() != null)
                                                                    retu = genProvVou.getResult();
                                                            }
                                                            OperationBinding setStatRcptOp =
                                                                getBindings().getOperationBinding("setRcptStatus");
                                                            setStatRcptOp.getParamsMap().put("stat",
                                                                                             RCPT_STATUS_STOCKUP);
                                                            setStatRcptOp.execute();

                                                        } else {
                                                            _log.info("Stock update failed!!");
                                                        }
                                                    } else {
                                                        txnvalid = "N";
                                                    }
                                                } else {
                                                    txnvalid = "N";
                                                }
                                            } else {
                                                txnvalid = "N";
                                            }
                                        }
                                    }
                                    //either receipt is approved or not approved
                                    if ("Y".equalsIgnoreCase(txnvalid)) {
                                        if (tfMode.equals("A")) {
                                            OperationBinding rcptNoOp = getBindings().getOperationBinding("getRcptNo");
                                            rcptNoOp.getParamsMap().put("SlocId", paramSlocId);
                                            rcptNoOp.getParamsMap().put("CldId", paramCldId);
                                            rcptNoOp.getParamsMap().put("OrgId", paramOrgId);
                                            rcptNoOp.getParamsMap().put("WhId", getWhIdPage().getValue().toString());
                                            rcptNoOp.getParamsMap().put("fyId", fyNo);
                                            rcptNoOp.execute();
                                            Object rno = rcptNoOp.getResult();
                                            _log.info("--GeNo--" + rno);

                                        }

                                        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptNo);

                                        OperationBinding exec = getBindings().getOperationBinding("Commit");
                                        exec.execute();

                                        if (exec.getErrors().isEmpty()) {
                                            if ("N".equalsIgnoreCase(authStat.getValue().toString())) {
                                                String retval =
                                                    callWfMethods(paramSlocId, paramCldId, paramOrgId, paramUsrId, "S");
                                                _log.info("WF methods call complete:" + retval);
                                            }
                                            if (scDocType.equals(SOURCE_DOC_TYPE_PO) ||
                                                scDocType.equals(SOURCE_DOC_TYPE_IMPORT_PO)) {
                                                _log.info("RMDA NO=" + rmdaNo.getValue());
                                                _log.info("CheckRejected=" + checkRejected.getValue());

                                                /*  OperationBinding execUpl = getBindings().getOperationBinding("updateLndCost");
                                         execUpl.getParamsMap().put("cldId", paramCldId);
                                         execUpl.getParamsMap().put("SlocId", paramSlocId);
                                         execUpl.getParamsMap().put("OrgId", paramOrgId);
                                         execUpl.execute();
                                          */
                                                if (scDocType.equals(SOURCE_DOC_TYPE_PO)) {

                                                    OperationBinding opcurr =
                                                        getBindings().getOperationBinding("setCurrIdSpPoToMtlRcpt");
                                                    opcurr.getParamsMap().put("value", SOURCE_DOC_TYPE_PO);
                                                    opcurr.execute();
                                                } else if(scDocType.equals(SOURCE_DOC_TYPE_IMPORT_PO)){
                                                    OperationBinding opcurr =
                                                        getBindings().getOperationBinding("setCurrIdSpPoToMtlRcpt");
                                                    opcurr.getParamsMap().put("value", SOURCE_DOC_TYPE_IMPORT_PO);
                                                    opcurr.execute();
                                                }else{
                                                    OperationBinding opcurr =
                                                        getBindings().getOperationBinding("setCurrIdSpPoToMtlRcpt");
                                                    opcurr.getParamsMap().put("value", SOURCE_DOC_TYPE_JITR);
                                                    opcurr.execute(); 
                                                }

                                                srNoBind.setValue("");
                                                srNoBind.resetValue();
                                                AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                                                RequestContext context = RequestContext.getCurrentInstance();
                                                context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                                // enableHeader=false;
                                                // showFacesMessage("Receipt Saved Succesfully", "I", false, "F");
                                                showFacesMessage("MSG.746", "I", true, "F");

                                            } else {
                                                srNoBind.setValue("");
                                                srNoBind.resetValue();
                                                AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                                                RequestContext context = RequestContext.getCurrentInstance();
                                                context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                                // enableHeader=false;
                                                //showFacesMessage("Receipt Saved Succesfully", "I", false, "F");
                                                showFacesMessage("MSG.746", "I", true, "F");
                                            }
                                        }


                                    } else {
                                        // save in case user clicks save and not enters all quantities/serials  //chekc this ..
                                        _log.info("Save Only -- All serials not entered. ");
                                    } //endIf---TxnValid
                                } //end for all Rcpt Types

                            } else if (scDocType.equals(SOURCE_DOC_TYPE_PRODCT)) { // for production    SOURCE_DOC_TYPE_PRODCT
                                OperationBinding rFy = getBindings().getOperationBinding("getFYid");
                                rFy.getParamsMap().put("CldId", paramCldId);
                                rFy.getParamsMap().put("OrgId", paramOrgId);
                                rFy.getParamsMap().put("geDate", (Timestamp) rcptDate.getValue());
                                rFy.getParamsMap().put("Mode", "V");
                                rFy.execute();

                                Object fy = rFy.getResult();

                                fyNo = Integer.parseInt(fy.toString());

                                OperationBinding chkRcvd = getBindings().getOperationBinding("checkForRcvdQtyPresent");
                                chkRcvd.getParamsMap().put("CldId", paramCldId);
                                chkRcvd.getParamsMap().put("slocId", paramSlocId);
                                chkRcvd.getParamsMap().put("OrgId", paramOrgId);
                                chkRcvd.getParamsMap().put("whId", getWhIdPage().getValue().toString());
                                chkRcvd.execute();


                                HashSet h = (HashSet) chkRcvd.getResult();
                                if (!h.isEmpty()) {
                                    _log.info("HashSet not empty" + h);

                                    StringBuilder msg =
                                        new StringBuilder("<html><body><p>Some items do not have received/rejected/reworkable quantity for the following Documents : <b>" +
                                                          h + "</b> </p>"); //
                                    msg.append("<ul> <li>Click YES to delete.</li>");
                                    msg.append("<li>Click NO to continue.</li></ul>");
                                    msg.append("</body></html>");


                                    msgpop = msg.toString();
                                    /*    FacesMessage fm=new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg);
                                FacesContext.getCurrentInstance().addMessage(null, fm); */
                                    showPopup(confDlvQtyChkPopUp, true);
                                    _log.info("After msg popup");

                                } else { //if received/rej/rwk qty present
                                    String txnvalid = "Y";


                                    if ("N".equals(authStat.getValue().toString())) {

                                        //check if all the serial nos are entered
                                        //if yes,
                                        OperationBinding chkSerOp =
                                            getBindings().getOperationBinding("checkSerialEnteredForSave");
                                        chkSerOp.getParamsMap().put("CldId", paramCldId);
                                        chkSerOp.getParamsMap().put("SlocId", paramSlocId);
                                        chkSerOp.getParamsMap().put("OrgId", paramOrgId);
                                        chkSerOp.execute();
                                        _log.info("---txninvalid---" + txnvalid);
                                        if ("N".equals(chkSerOp.getResult().toString())) {

                                            String chkReworkableN = "N";
                                            OperationBinding chkRwkable =
                                                getBindings().getOperationBinding("chkRWkableQtyPresent");
                                            chkRwkable.execute();
                                            if (chkRwkable.getResult() != null) {
                                                _log.info("chkReworkableN1  :::::::: ");
                                                chkReworkableN = chkRwkable.getResult().toString();
                                            }

                                            _log.info("chkReworkableN1   :  " + chkReworkableN);
                                            //  if("Y".equals(chkReworkable.getValue().toString())){//if reworkable qty present
                                            if ("Y".equalsIgnoreCase(chkReworkableN)) {
                                                OperationBinding setStatRcptOp =
                                                    getBindings().getOperationBinding("setRcptStatus");
                                                setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_REWORK);
                                                setStatRcptOp.execute();
                                            } else {

                                                // if(Integer.parseInt(rcptStat.getValue().toString())==RCPT_STATUS_REWORK && "N".equals(chkReworkable.getValue().toString())){
                                                if (Integer.parseInt(rcptStat.getValue().toString()) ==
                                                    RCPT_STATUS_REWORK && "N".equalsIgnoreCase(chkReworkableN)) {
                                                    OperationBinding setStatRcptOp =
                                                        getBindings().getOperationBinding("setRcptStatus");
                                                    setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_PEND);
                                                    setStatRcptOp.execute();
                                                }

                                                if (scDocType.equals(SOURCE_DOC_TYPE_PRODCT)) { ///   SOURCE_DOC_TYPE_PO

                                                    //validation if all values entered.
                                                    _log.info("QC REQD--" + checkQcReqd.getValue().toString());
                                                    if ("Y".equalsIgnoreCase(checkQcReqd.getValue().toString())) {
                                                        OperationBinding chkRcvItm =
                                                            getBindings().getOperationBinding("chkIsAnyItmForQc");
                                                        chkRcvItm.execute();
                                                        String chkQc = "Y";
                                                        if (chkRcvItm.getErrors().size() == 0 &&
                                                            chkRcvItm.getResult() != null)
                                                            chkQc = (String) chkRcvItm.getResult();
                                                        if (chkQc.equals("Y")) {
                                                            OperationBinding setStatRcptOp =
                                                                getBindings().getOperationBinding("setRcptStatus");
                                                            setStatRcptOp.getParamsMap().put("stat",
                                                                                             RCPT_STATUS_QCPEND);
                                                            setStatRcptOp.execute();
                                                        }
                                                    } else {
                                                        _log.info("QC NOT REQD");
                                                        /*   OperationBinding setStatRcptOp = getBindings().getOperationBinding("setRcptStatus");
                                                     setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_PEND);
                                                     setStatRcptOp.execute();
                                         } */

                                                    }
                                                } else {
                                                    OperationBinding setStatRcptOp =
                                                        getBindings().getOperationBinding("setRcptStatus");
                                                    setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_PEND);
                                                    setStatRcptOp.execute();
                                                }

                                            } //end else
                                        } else {
                                            OperationBinding setStatRcptOp =
                                                getBindings().getOperationBinding("setRcptStatus");
                                            setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_PEND);
                                            setStatRcptOp.execute();
                                            // txnvalid = "N";
                                        }
                                    } else if ("Y".equalsIgnoreCase(authStat.getValue().toString())) {

                                        //   txnvalid="N";
                                        //check if lot allotted
                                        OperationBinding chkLot = getBindings().getOperationBinding("checkLotSelected");
                                        chkLot.getParamsMap().put("CldId", paramCldId);
                                        chkLot.getParamsMap().put("SlocId", paramSlocId);
                                        chkLot.getParamsMap().put("OrgId", paramOrgId);
                                        chkLot.execute();
                                        System.out.println("Error on execute checklotselect=" + chkLot.getErrors());
                                        System.out.println("Result on lot=" + chkLot.getResult());
                                        if ("N".equalsIgnoreCase(chkLot.getResult().toString())) {

                                            OperationBinding chkBin =
                                                getBindings().getOperationBinding("checkBinSelected");
                                            chkBin.getParamsMap().put("CldId", paramCldId);
                                            chkBin.getParamsMap().put("SlocId", paramSlocId);
                                            chkBin.getParamsMap().put("OrgId", paramOrgId);
                                            chkBin.getParamsMap().put("Binchk", paramBinChk);
                                            chkBin.execute();
                                            if ("N".equalsIgnoreCase(chkBin.getResult().toString())) {
                                                OperationBinding chkSr =
                                                    getBindings().getOperationBinding("checkSerialSelected");
                                                chkSr.getParamsMap().put("CldId", paramCldId);
                                                chkSr.getParamsMap().put("SlocId", paramSlocId);
                                                chkSr.getParamsMap().put("OrgId", paramOrgId);
                                                chkSr.getParamsMap().put("Binchk", paramBinChk);
                                                chkSr.execute();
                                                if ("N".equalsIgnoreCase(chkSr.getResult().toString())) {
                                                    //check if all entered
                                                    OperationBinding exec = getBindings().getOperationBinding("Commit");
                                                    exec.execute();
                                                    if (exec.getErrors().size() == 0) {
                                                        OperationBinding execUpl =
                                                            getBindings().getOperationBinding("updateLndCost");
                                                        execUpl.getParamsMap().put("cldId", paramCldId);
                                                        execUpl.getParamsMap().put("SlocId", paramSlocId);
                                                        execUpl.getParamsMap().put("OrgId", paramOrgId);
                                                        execUpl.execute();

                                                        /**
                                           * for populate rcpt calculation value
                                           */

                                                        /*
                                            OperationBinding popCalc = getBindings().getOperationBinding("populateCalculations");
                                            popCalc.getParamsMap().put("p_cld_id",paramCldId);
                                            popCalc.getParamsMap().put("p_sloc_id",paramSlocId);
                                            popCalc.getParamsMap().put("p_org_id",paramOrgId);
                                            popCalc.getParamsMap().put("p_usr_id",paramUsrId);
                                            popCalc.execute();*/
                                                    }
                                                    //exec.execute();
                                                    OperationBinding execUp =
                                                        getBindings().getOperationBinding("updateStockForOthers");
                                                    execUp.getParamsMap().put("cldId", paramCldId);
                                                    execUp.getParamsMap().put("SlocId", paramSlocId);
                                                    execUp.getParamsMap().put("OrgId", paramOrgId);
                                                    execUp.execute();
                                                    Object o = execUp.getResult();
                                                    _log.info("Stock update called :" + o);

                                                    if (execUp.getErrors().isEmpty() && o != null) {

                                                        //if(scDocType.equals(SOURCE_DOC_TYPE_PO))
                                                        // {

                                                        // OperationBinding setBalandTmpQty = getBindings().getOperationBinding("setBalandTmpQty");
                                                        // setBalandTmpQty.execute();

                                                        //    }
//                                                        OperationBinding chkProv =
//                                                            getBindings().getOperationBinding("doCrtProvVourOnRcpt");
//                                                        chkProv.execute();
//                                                        if (chkProv.getResult() != null &&
//                                                            chkProv.getResult().equals("Y")) {
//                                                            OperationBinding genProvVou =
//                                                                getBindings().getOperationBinding("insGlProvVou");
//                                                            genProvVou.execute();
//                                                            Object retu = null;
//                                                            if (genProvVou.getErrors().size() == 0 &&
//                                                                genProvVou.getResult() != null)
//                                                                retu = genProvVou.getResult();
//                                                        }
                                                        OperationBinding setStatRcptOp =
                                                            getBindings().getOperationBinding("setRcptStatus");
                                                        setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_STOCKUP);
                                                        setStatRcptOp.execute();

                                                    } else {
                                                        _log.info("Stock update failed!!");
                                                    }
                                                } else {
                                                    txnvalid = "N";
                                                }
                                            } else {
                                                txnvalid = "N";
                                            }
                                        } else {
                                            txnvalid = "N";
                                        }
                                    }

                                    //either receipt is approved or not approved
                                    if ("Y".equalsIgnoreCase(txnvalid)) {
                                        if (tfMode.equals("A")) {
                                            OperationBinding rcptNoOp = getBindings().getOperationBinding("getRcptNo");
                                            rcptNoOp.getParamsMap().put("SlocId", paramSlocId);
                                            rcptNoOp.getParamsMap().put("CldId", paramCldId);
                                            rcptNoOp.getParamsMap().put("OrgId", paramOrgId);
                                            rcptNoOp.getParamsMap().put("WhId", getWhIdPage().getValue().toString());
                                            rcptNoOp.getParamsMap().put("fyId", fyNo);
                                            rcptNoOp.execute();
                                            Object rno = rcptNoOp.getResult();
                                            _log.info("--GeNo--" + rno);

                                        }

                                        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptNo);

                                        OperationBinding exec = getBindings().getOperationBinding("Commit");
                                        exec.execute();

                                        if (exec.getErrors().isEmpty()) {
                                            if ("N".equalsIgnoreCase(authStat.getValue().toString())) {
                                                String retval =
                                                    callWfMethods(paramSlocId, paramCldId, paramOrgId, paramUsrId, "S");
                                                _log.info("WF methods call complete:" + retval);
                                            }
                                            if (scDocType.equals(SOURCE_DOC_TYPE_PRODCT)) {
                                                _log.info("RMDA NO=" + rmdaNo.getValue());
                                                _log.info("CheckRejected=" + checkRejected.getValue());

                                                /*  OperationBinding execUpl = getBindings().getOperationBinding("updateLndCost");
                                             execUpl.getParamsMap().put("cldId", paramCldId);
                                             execUpl.getParamsMap().put("SlocId", paramSlocId);
                                             execUpl.getParamsMap().put("OrgId", paramOrgId);
                                             execUpl.execute();
                                              */


                                                /*  OperationBinding opcurr=getBindings().getOperationBinding("setCurrIdSpPoToMtlRcpt");
                                            opcurr.getParamsMap().put("value", SOURCE_DOC_TYPE_PO);
                                            opcurr.execute(); */


                                                srNoBind.setValue("");
                                                srNoBind.resetValue();
                                                AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                                                RequestContext context = RequestContext.getCurrentInstance();
                                                context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                                // enableHeader=false;
                                                // showFacesMessage("Receipt Saved Succesfully", "I", false, "F");
                                                showFacesMessage("MSG.746", "I", true, "F");

                                            } else {
                                                srNoBind.setValue("");
                                                srNoBind.resetValue();
                                                AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                                                RequestContext context = RequestContext.getCurrentInstance();
                                                context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                                // enableHeader=false;
                                                //showFacesMessage("Receipt Saved Succesfully", "I", false, "F");
                                                showFacesMessage("MSG.746", "I", true, "F");
                                            }
                                        }


                                    } else {
                                        // save in case user clicks save and not enters all quantities/serials  //chekc this ..
                                        _log.info("Save Only -- All serials not entered. ");
                                    } //endIf---TxnValid
                                } //end for all Rcpt Types    for production
                            } else if (scDocType.equals(SOURCE_DOC_TYPE_TO)) {
                                OperationBinding rFy = getBindings().getOperationBinding("getFYid");
                                rFy.getParamsMap().put("CldId", paramCldId);
                                rFy.getParamsMap().put("OrgId", paramOrgId);
                                rFy.getParamsMap().put("geDate", (Timestamp) rcptDate.getValue());
                                rFy.getParamsMap().put("Mode", "V");
                                rFy.execute();

                                Object fy = rFy.getResult();

                                fyNo = Integer.parseInt(fy.toString());

                                OperationBinding chkRcvd = getBindings().getOperationBinding("checkForRcvdQtyPresent");
                                chkRcvd.getParamsMap().put("CldId", paramCldId);
                                chkRcvd.getParamsMap().put("slocId", paramSlocId);
                                chkRcvd.getParamsMap().put("OrgId", paramOrgId);
                                chkRcvd.getParamsMap().put("whId", getWhIdPage().getValue().toString());
                                chkRcvd.execute();


                                HashSet h = (HashSet) chkRcvd.getResult();
                                if (!h.isEmpty()) {
                                    _log.info("TO HashSet not empty" + h);
                                    //  Key->MSG.747->Some items do not have received/rejected/reworkable quantity for the following Documents
                                    StringBuilder msg =
                                        new StringBuilder("<html><body><p>Some items do not have received/rejected/reworkable quantity for the following Documents : <b>" +
                                                          h + "</b> </p>");
                                    // key-MSG.706->Click YES to delete.
                                    msg.append("<ul> <li>Click YES to delete.</li>");
                                    //    key-MSG.707->Click NO to continue.
                                    msg.append("<li>Click NO to continue.</li></ul>");
                                    msg.append("</body></html>");

                                    msgpop = msg.toString();
                                    /*    FacesMessage fm=new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg);
                                               FacesContext.getCurrentInstance().addMessage(null, fm); */
                                    showPopup(confDlvQtyChkPopUp, true);
                                    _log.info("After msg popup");
                                } else {

                                    //check if all enetered
                                    if (tfMode.equals("A")) {
                                        OperationBinding rcptNoOp = getBindings().getOperationBinding("getRcptNo");
                                        rcptNoOp.getParamsMap().put("SlocId", paramSlocId);
                                        rcptNoOp.getParamsMap().put("CldId", paramCldId);
                                        rcptNoOp.getParamsMap().put("OrgId", paramOrgId);
                                        rcptNoOp.getParamsMap().put("WhId", getWhIdPage().getValue().toString());
                                        rcptNoOp.getParamsMap().put("fyId", fyNo);
                                        rcptNoOp.execute();
                                        Object rno = rcptNoOp.getResult();
                                        _log.info("--RCPTNO TO--" + rno);

                                    }

                                    AdfFacesContext.getCurrentInstance().addPartialTarget(rcptNo);
                                    OperationBinding exec = getBindings().getOperationBinding("Commit");
                                    exec.execute();

                                    if (exec.getErrors().isEmpty()) {
                                        if ("N".equalsIgnoreCase(authStat.getValue().toString())) {
                                            String retval =
                                                callWfMethods(paramSlocId, paramCldId, paramOrgId, paramUsrId, "S");
                                            _log.info("WF methods call complete:" + retval);
                                        }

                                        // exec.execute();  commented on 23-may-2014
                                        if ("Y".equalsIgnoreCase(authStat.getValue().toString())) {

                                            OperationBinding chkLot =
                                                getBindings().getOperationBinding("checkLotSelected");
                                            chkLot.getParamsMap().put("CldId", paramCldId);
                                            chkLot.getParamsMap().put("SlocId", paramSlocId);
                                            chkLot.getParamsMap().put("OrgId", paramOrgId);
                                            chkLot.execute();
                                            if ("N".equalsIgnoreCase(chkLot.getResult().toString())) {

                                                OperationBinding chkBin =
                                                    getBindings().getOperationBinding("checkBinSelected");
                                                chkBin.getParamsMap().put("CldId", paramCldId);
                                                chkBin.getParamsMap().put("SlocId", paramSlocId);
                                                chkBin.getParamsMap().put("OrgId", paramOrgId);
                                                chkBin.getParamsMap().put("Binchk", paramBinChk);
                                                chkBin.execute();
                                                if ("N".equalsIgnoreCase(chkBin.getResult().toString())) {
                                                    OperationBinding chkSr =
                                                        getBindings().getOperationBinding("checkSerialSelected");
                                                    chkSr.getParamsMap().put("CldId", paramCldId);
                                                    chkSr.getParamsMap().put("SlocId", paramSlocId);
                                                    chkSr.getParamsMap().put("OrgId", paramOrgId);
                                                    chkSr.getParamsMap().put("Binchk", paramBinChk);
                                                    chkSr.execute();
                                                    if ("N".equalsIgnoreCase(chkSr.getResult().toString())) {
                                                        OperationBinding execUpl =
                                                            getBindings().getOperationBinding("updateLndCost");
                                                        execUpl.getParamsMap().put("cldId", paramCldId);
                                                        execUpl.getParamsMap().put("SlocId", paramSlocId);
                                                        execUpl.getParamsMap().put("OrgId", paramOrgId);
                                                        execUpl.execute();
                                                        exec.execute();
                                                        OperationBinding execUp =
                                                            getBindings().getOperationBinding("updateStockForOthers");
                                                        execUp.getParamsMap().put("cldId", paramCldId);
                                                        execUp.getParamsMap().put("SlocId", paramSlocId);
                                                        execUp.getParamsMap().put("OrgId", paramOrgId);
                                                        execUp.execute();
                                                        Object o = execUp.getResult();
                                                        _log.info("Stock update called :" + o);

                                                        if (execUp.getErrors().isEmpty() &&
                                                            o !=
                            null) {
                                                            //   exec.execute();  //commit
                                                            if (((Integer) o).compareTo(0) ==
                                                                1) { //stock update successful

                                                                OperationBinding setStatRcptOp =
                            getBindings().getOperationBinding("setRcptStatus");
                                                                setStatRcptOp.getParamsMap().put("stat",
                                                                                                 RCPT_STATUS_STOCKUP);
                                                                setStatRcptOp.execute();

                                                                exec.execute(); //commit
                                                                execUpl.execute();
                                                                exec.execute();
                                                                srNoBind.setValue("");
                                                                srNoBind.resetValue();
                                                                AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                                                                RequestContext context =
                                                                    RequestContext.getCurrentInstance();
                                                                context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                                                // enableHeader=false;
                                                                //showFacesMessage("Receipt Saved Succesfully and Stock Updated.", "I", false, "F");
                                                                showFacesMessage("MSG.748", "I", true, "F");
                                                            } else {
                                                                _log.info("Stock update failed!!");
                                                            }
                                                        } else {
                                                            _log.info("Stock update failed!!");
                                                        }
                                                    } else {
                                                        _log.info("All Serials are not Entered!!");
                                                    }
                                                } else {
                                                    _log.info("All Bins are not Entered!!");
                                                }
                                            } else {
                                                _log.info("All Lots are not Entered!!");
                                            }
                                        } else if (exec.getErrors().isEmpty()) {
                                            srNoBind.setValue("");
                                            srNoBind.resetValue();
                                            AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                                            RequestContext context = RequestContext.getCurrentInstance();
                                            context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                            // enableHeader=false;
                                            // showFacesMessage("Receipt Saved Succesfully", "I", false, "F");
                                            showFacesMessage("MSG.746", "I", true, "F");
                                        } else {
                                            _log.info("Error during commit");
                                        }

                                        /*   srNoBind.setValue("");
                           srNoBind.resetValue();
                           AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                           RequestContext context = RequestContext.getCurrentInstance();
                           context.getPageFlowScope().put("ADD_EDIT_MODE","V");
                           // enableHeader=false;
                           showFacesMessage("Receipt Saved Succesfully", "I", false, "F");   */
                                    }
                                }
                            }
                            //                   else if(scDocType.equals(SOURCE_DOC_TYPE_MRN)){
                            //
                            //                       OperationBinding rFy = getBindings().getOperationBinding("getFYid");
                            //                            rFy.getParamsMap().put("CldId", paramCldId);
                            //                            rFy.getParamsMap().put("OrgId", paramOrgId);
                            //                            rFy.getParamsMap().put("geDate", (Timestamp)rcptDate.getValue());
                            //                            rFy.getParamsMap().put("Mode", "V");
                            //                            rFy.execute();
                            //
                            //                            Object fy=rFy.getResult();
                            //
                            //                       fyNo=Integer.parseInt(fy.toString());
                            //
                            //                       OperationBinding chkRcvd = getBindings().getOperationBinding("checkForRcvdQtyPresent");
                            //                          chkRcvd.getParamsMap().put("CldId", paramCldId);
                            //                          chkRcvd.getParamsMap().put("slocId", paramSlocId);
                            //                          chkRcvd.getParamsMap().put("OrgId", paramOrgId);
                            //                          chkRcvd.getParamsMap().put("whId", getWhIdPage().getValue().toString());
                            //                          chkRcvd.execute();
                            //
                            //
                            //                       HashSet h=(HashSet)chkRcvd.getResult();
                            //                       if(!h.isEmpty()){
                            //                       _log.info("TO HashSet not empty"+h);
                            //                           //Key->MSG.705->>Some items do not have received quantity for the following Documents
                            //                       StringBuilder msg =new StringBuilder("<html><body><p>Some items do not have received quantity for the following Documents : <b>" + h +
                            //                                                                      "</b> </p>");
                            //                           // key-MSG.706->Click YES to delete.
                            //                           msg.append("<ul> <li>Click YES to delete.</li>");
                            //                           // key-MSG.707->Click NO to continue.
                            //                           msg.append("<li>Click NO to continue.</li></ul>");
                            //                           msg.append("</body></html>");
                            //
                            //                          msgpop=msg.toString();
                            //                                              /*    FacesMessage fm=new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg);
                            //                                               FacesContext.getCurrentInstance().addMessage(null, fm); */
                            //                          showPopup(confDlvQtyChkPopUp, true);
                            //                          _log.info("After msg popup");
                            //                       }else{
                            //                           //check if all enetered
                            //                           if(tfMode.equals("A")){
                            //                             OperationBinding rcptNoOp = getBindings().getOperationBinding("getRcptNo");
                            //                                rcptNoOp.getParamsMap().put("SlocId", paramSlocId);
                            //                                rcptNoOp.getParamsMap().put("CldId", paramCldId);
                            //                                rcptNoOp.getParamsMap().put("OrgId", paramOrgId);
                            //                                rcptNoOp.getParamsMap().put("WhId", getWhIdPage().getValue().toString());
                            //                                rcptNoOp.getParamsMap().put("fyId", fyNo);
                            //                                rcptNoOp.execute();
                            //                              Object rno=rcptNoOp.getResult();
                            //                              _log.info("--RCPTNO TO--"+rno);
                            //
                            //                           }
                            //
                            //                           AdfFacesContext.getCurrentInstance().addPartialTarget(rcptNo);
                            //                           OperationBinding exec = getBindings().getOperationBinding("Commit");
                            //                           exec.execute();
                            //                           if(exec.getErrors().isEmpty()){
                            //                             if("N".equalsIgnoreCase(authStat.getValue().toString())){
                            //                                String retval=callWfMethods(paramSlocId, paramCldId, paramOrgId, paramUsrId, "S");
                            //                                _log.info("WF methods call complete:"+retval);
                            //                             }
                            //                              // exec.execute(); commented on 23-may-2014
                            //
                            //                               RequestContext context = RequestContext.getCurrentInstance();
                            //                               context.getPageFlowScope().put("ADD_EDIT_MODE","V");
                            //                               // enableHeader=false;
                            //                              // showFacesMessage("Receipt Saved Succesfully", "I", false, "F");
                            //                              showFacesMessage("MSG.746", "I", true, "F");
                            //                           }else{
                            //                               _log.info("Commit fail");
                            //                           }
                            //
                            //                       }
                            //                   }
                            else if (scDocType.equals(SOURCE_DOC_TYPE_CPO)) {
                                OperationBinding rFy = getBindings().getOperationBinding("getFYid");
                                rFy.getParamsMap().put("CldId", paramCldId);
                                rFy.getParamsMap().put("OrgId", paramOrgId);
                                rFy.getParamsMap().put("geDate", (Timestamp) rcptDate.getValue());
                                rFy.getParamsMap().put("Mode", "V");
                                rFy.execute();

                                Object fy = rFy.getResult();

                                fyNo = Integer.parseInt(fy.toString());

                                OperationBinding chkRcvd = getBindings().getOperationBinding("checkForRcvdQtyPresent");
                                chkRcvd.getParamsMap().put("CldId", paramCldId);
                                chkRcvd.getParamsMap().put("slocId", paramSlocId);
                                chkRcvd.getParamsMap().put("OrgId", paramOrgId);
                                chkRcvd.getParamsMap().put("whId", getWhIdPage().getValue().toString());
                                chkRcvd.execute();


                                HashSet h = (HashSet) chkRcvd.getResult();
                                if (!h.isEmpty()) {
                                    _log.info("HashSet not empty" + h);

                                    StringBuilder msg =
                                        new StringBuilder("<html><body><p>Some items do not have received/rejected/reworkable quantity for the following Documents : <b>" +
                                                          h + "</b> </p>"); //
                                    msg.append("<ul> <li>Click YES to delete.</li>");
                                    msg.append("<li>Click NO to continue.</li></ul>");
                                    msg.append("</body></html>");


                                    msgpop = msg.toString();
                                    /*    FacesMessage fm=new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg);
                             FacesContext.getCurrentInstance().addMessage(null, fm); */
                                    showPopup(confDlvQtyChkPopUp, true);
                                    _log.info("After msg popup");

                                } else { //if received/rej/rwk qty present
                                    String txnvalid = "Y";


                                    if ("N".equals(authStat.getValue().toString())) {

                                        //check if all the serial nos are entered
                                        //if yes,
                                        OperationBinding chkSerOp =
                                            getBindings().getOperationBinding("checkSerialEnteredForSave");
                                        chkSerOp.getParamsMap().put("CldId", paramCldId);
                                        chkSerOp.getParamsMap().put("SlocId", paramSlocId);
                                        chkSerOp.getParamsMap().put("OrgId", paramOrgId);
                                        chkSerOp.execute();
                                        if (chkSerOp.getErrors().size() > 0)
                                            _log.info("Error=" + chkSerOp.getErrors());
                                        _log.info("---txninvalid---" + txnvalid);
                                        if ("N".equals(chkSerOp.getResult().toString())) {
                                            String chkReworkableNe = "N";
                                            OperationBinding chkRwkable =
                                                getBindings().getOperationBinding("chkRWkableQtyPresent");
                                            chkRwkable.execute();
                                            if (chkRwkable.getResult() != null) {
                                                _log.info("chkReworkableNe2 :::::");
                                                chkReworkableNe = chkRwkable.getResult().toString();
                                            }

                                            _log.info("chkReworkableNe2   :  " + chkReworkableNe);

                                            //if("Y".equals(chkReworkable.getValue().toString())){//if reworkable qty present
                                            if ("Y".equalsIgnoreCase("chkReworkableNe")) {
                                                OperationBinding setStatRcptOp =
                                                    getBindings().getOperationBinding("setRcptStatus");
                                                setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_REWORK);
                                                setStatRcptOp.execute();
                                            } else {

                                                // if(Integer.parseInt(rcptStat.getValue().toString())==RCPT_STATUS_REWORK && "N".equals(chkReworkable.getValue().toString())){
                                                if (Integer.parseInt(rcptStat.getValue().toString()) ==
                                                    RCPT_STATUS_REWORK && "N".equalsIgnoreCase("chkReworkableNe")) {
                                                    OperationBinding setStatRcptOp =
                                                        getBindings().getOperationBinding("setRcptStatus");
                                                    setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_PEND);
                                                    setStatRcptOp.execute();
                                                }

                                                //validation if all values entered.
                                                _log.info("QC REQD--" + checkQcReqd.getValue().toString());
                                                if ("Y".equalsIgnoreCase(checkQcReqd.getValue().toString())) {
                                                    OperationBinding chkRcvItm =
                                                        getBindings().getOperationBinding("chkIsAnyItmForQc");
                                                    chkRcvItm.execute();
                                                    String chkQc = "Y";
                                                    if (chkRcvItm.getErrors().size() == 0 &&
                                                        chkRcvItm.getResult() != null)
                                                        chkQc = (String) chkRcvItm.getResult();
                                                    if (chkQc.equals("Y")) {
                                                        OperationBinding setStatRcptOp =
                                                            getBindings().getOperationBinding("setRcptStatus");
                                                        setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_QCPEND);
                                                        setStatRcptOp.execute();
                                                    }
                                                } else {
                                                    _log.info("QC NOT REQD");
                                                }

                                            } //end else
                                        } else {

                                            OperationBinding setStatRcptOp =
                                                getBindings().getOperationBinding("setRcptStatus");
                                            setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_PEND);
                                            setStatRcptOp.execute();
                                            // txnvalid = "N";
                                        }
                                    } else if ("Y".equalsIgnoreCase(authStat.getValue().toString())) {

                                        //   txnvalid="N";
                                        //check if lot allotted
                                        OperationBinding chkLot = getBindings().getOperationBinding("checkLotSelected");
                                        chkLot.getParamsMap().put("CldId", paramCldId);
                                        chkLot.getParamsMap().put("SlocId", paramSlocId);
                                        chkLot.getParamsMap().put("OrgId", paramOrgId);
                                        chkLot.execute();

                                        if ("N".equalsIgnoreCase(chkLot.getResult().toString())) {

                                            OperationBinding chkBin =
                                                getBindings().getOperationBinding("checkBinSelected");
                                            chkBin.getParamsMap().put("CldId", paramCldId);
                                            chkBin.getParamsMap().put("SlocId", paramSlocId);
                                            chkBin.getParamsMap().put("OrgId", paramOrgId);
                                            chkBin.getParamsMap().put("Binchk", paramBinChk);
                                            chkBin.execute();
                                            if ("N".equalsIgnoreCase(chkBin.getResult().toString())) {
                                                OperationBinding chkSr =
                                                    getBindings().getOperationBinding("checkSerialSelected");
                                                chkSr.getParamsMap().put("CldId", paramCldId);
                                                chkSr.getParamsMap().put("SlocId", paramSlocId);
                                                chkSr.getParamsMap().put("OrgId", paramOrgId);
                                                chkSr.getParamsMap().put("Binchk", paramBinChk);
                                                chkSr.execute();
                                                if ("N".equalsIgnoreCase(chkSr.getResult().toString())) {
                                                    //check if all entered
                                                    OperationBinding exec = getBindings().getOperationBinding("Commit");
                                                    exec.execute();
                                                    OperationBinding execUpl =
                                                        getBindings().getOperationBinding("updateLndCost");
                                                    execUpl.getParamsMap().put("cldId", paramCldId);
                                                    execUpl.getParamsMap().put("SlocId", paramSlocId);
                                                    execUpl.getParamsMap().put("OrgId", paramOrgId);
                                                    execUpl.execute();
                                                    exec.execute();
                                                    OperationBinding execUp =
                                                        getBindings().getOperationBinding("updateStockForOthers");
                                                    execUp.getParamsMap().put("cldId", paramCldId);
                                                    execUp.getParamsMap().put("SlocId", paramSlocId);
                                                    execUp.getParamsMap().put("OrgId", paramOrgId);
                                                    execUp.execute();
                                                    Object o = execUp.getResult();
                                                    _log.info("Stock update called :" + o);

                                                    if (execUp.getErrors().isEmpty() && o != null) {


                                                        OperationBinding setStatRcptOp =
                                                            getBindings().getOperationBinding("setRcptStatus");
                                                        setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_STOCKUP);
                                                        setStatRcptOp.execute();

                                                    } else {
                                                        _log.info("Stock update failed!!");
                                                    }
                                                } else {
                                                    txnvalid = "N";
                                                }
                                            } else {
                                                txnvalid = "N";
                                            }
                                        } else {
                                            txnvalid = "N";
                                        }
                                    }


                                    if ("Y".equalsIgnoreCase(txnvalid)) {
                                        if (tfMode.equals("A")) {
                                            OperationBinding rcptNoOp = getBindings().getOperationBinding("getRcptNo");
                                            rcptNoOp.getParamsMap().put("SlocId", paramSlocId);
                                            rcptNoOp.getParamsMap().put("CldId", paramCldId);
                                            rcptNoOp.getParamsMap().put("OrgId", paramOrgId);
                                            rcptNoOp.getParamsMap().put("WhId", getWhIdPage().getValue().toString());
                                            rcptNoOp.getParamsMap().put("fyId", fyNo);
                                            rcptNoOp.execute();
                                            Object rno = rcptNoOp.getResult();
                                            _log.info("--GeNo--" + rno);

                                        }

                                        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptNo);

                                        OperationBinding exec = getBindings().getOperationBinding("Commit");
                                        exec.execute();
                                        if (exec.getErrors().isEmpty()) {
                                            if ("N".equalsIgnoreCase(authStat.getValue().toString())) {
                                                String retval =
                                                    callWfMethods(paramSlocId, paramCldId, paramOrgId, paramUsrId, "S");
                                                _log.info("WF methods call complete:" + retval);
                                            }
                                            if (scDocType.equals(SOURCE_DOC_TYPE_CPO)) {

                                                //  exec.execute();  commented on 23-may-2014
                                                OperationBinding opcurr =
                                                    getBindings().getOperationBinding("setCurrIdSpPoToMtlRcpt");
                                                opcurr.getParamsMap().put("value", SOURCE_DOC_TYPE_CPO);
                                                opcurr.execute();
                                                srNoBind.setValue("");
                                                srNoBind.resetValue();
                                                AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                                                RequestContext context = RequestContext.getCurrentInstance();
                                                context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                                // enableHeader=false;
                                                // showFacesMessage("Receipt Saved Succesfully", "I", false, "F");
                                                showFacesMessage("MSG.746", "I", true, "F");

                                            } /* else{
                                          srNoBind.setValue("");
                                          srNoBind.resetValue();
                                          AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                                          RequestContext context = RequestContext.getCurrentInstance();
                                          context.getPageFlowScope().put("ADD_EDIT_MODE","V");
                                          // enableHeader=false;
                                          //showFacesMessage("Receipt Saved Succesfully", "I", false, "F");
                                          showFacesMessage("MSG.746", "I", true, "F");
                                        } */
                                        }


                                    } else {
                                        // save in case user clicks save and not enters all quantities/serials  //chekc this ..
                                        _log.info("Save Only -- All serials not entered. ");
                                        /**
                               * Code commented By Nitesh Garg.
                               * If Lot or Bin or Sr is not given for any Item then Do not Commit.
                               */


                                    } //endIf---TxnValid
                                } //end for all Rcpt Types

                            }

                            else {
                                _log.info("Document Out of scope.");
                            }
                        } else {
                            //showFacesMessage("Please Select the Document Type for the Receipt.", "E", false, "F");
                            showFacesMessage("MSG.749", "E", true, "F");
                        }
                    }
                } else {
                    //showFacesMessage("Documents have not been added for this Receipt.Cannot Save.", "E", false, "F");
                    showFacesMessage("MSG.750", "E", true, "F");
                }

            } else {
                // showFacesMessage("Please select the Receipt Type.", "E", false, "F");
                showFacesMessage("MSG.751", "E", true, "F");
            }

        } else {
            _log.info("Enter Rcpt Date");
            // showFacesMessage("Receipt Date is required.", "E", false, "F");
            showFacesMessage("MSG.752", "E", true, "F");

        }
        return null;
    }


    public String saveForwardAction() {
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer paramUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String paramHoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String tfMode = resolvEl("#{pageFlowScope.ADD_EDIT_MODE}");
        String paramWhChk = resolvEl("#{pageFlowScope.GLBL_ORG_WH_CHK}");
        String paramGeChk = resolvEl("#{pageFlowScope.GLBL_ORG_GE_CHK}");

        Integer fyNo = 0;
        if (rcptDate.getValue() != null) {

            if (rcptSrcType.getValue() != null) {

                OperationBinding isDocPr = getBindings().getOperationBinding("isDocumentPresent");
                isDocPr.execute();

                if ("Y".equals(isDocPr.getResult().toString())) {

                    if (Integer.parseInt(rcptSrcType.getValue().toString()) == RCPT_TYPE_OB ||
                        Integer.parseInt(srcDocType.getValue().toString()) == (SOURCE_DOC_TYPE_FGR) ||
                        Integer.parseInt(srcDocType.getValue().toString()) == (SOURCE_DOC_TYPE_MRN) ||
                        Integer.parseInt(srcDocType.getValue().toString()) == (SOURCE_DOC_TYPE_GPR)) { //Opening Balance
                        OperationBinding rFy = getBindings().getOperationBinding("getFYid");
                        rFy.getParamsMap().put("CldId", paramCldId);
                        rFy.getParamsMap().put("OrgId", paramOrgId);
                        rFy.getParamsMap().put("geDate", (Timestamp) rcptDate.getValue());
                        rFy.getParamsMap().put("Mode", "V");
                        rFy.execute();

                        Object fy = rFy.getResult();

                        fyNo = Integer.parseInt(fy.toString());

                        /**
                     * Stock Detail is entered or not, for all Items.? in Both Cases.? @Nitesh Garg
                     */
                        String txnvalid = "Y";
                        //check if lot allotted

                        /**
                         * Check for current document if any item for this document currently available in stock or not
                         * IF Y :  Item Already in stock can not add Opening balance
                         * IF N : Item Not in Stock
                         */

                        if (Integer.parseInt(rcptSrcType.getValue().toString()) == RCPT_TYPE_OB) {

                            OperationBinding chkItmForStock =
                                getBindings().getOperationBinding("checkItmInStockOrNotForOB");
                            chkItmForStock.getParamsMap().put("cldId", paramCldId);
                            chkItmForStock.getParamsMap().put("slocId", paramSlocId);
                            chkItmForStock.getParamsMap().put("orgId", paramOrgId);
                            chkItmForStock.execute();

                            if (chkItmForStock.getResult() != null &&
                                "Y".equalsIgnoreCase(chkItmForStock.getResult().toString())) {
                                showFacesMessage("Opening balance for some of the items are already available. Can not proceed.",
                                                 "E", false, "F");
                                return null;
                            }

                        }


                        /**
                         * Check for current document if any item for this document Stock adjustment account define or not
                         * IF 1 :  All ok all item has been define
                         * IF 0 : NO date found in item profile table
                         * IF -1 : not define stock adjustment account
                         */


                        if (Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_FGR) {

                            OperationBinding chkItmForStock =
                                getBindings().getOperationBinding("checkItmInAdjAccPresents");
                            chkItmForStock.getParamsMap().put("cldId", paramCldId);
                            chkItmForStock.getParamsMap().put("slocId", paramSlocId);
                            chkItmForStock.getParamsMap().put("orgId", paramOrgId);
                            chkItmForStock.execute();
                            _log.info(" chkItmForStock  " + chkItmForStock.getResult());
                            if (chkItmForStock.getResult() != null &&
                                ((Integer) chkItmForStock.getResult()).compareTo(new Integer(0)) == 0) {
                                showFacesMessage("Stock Adjustment A/c is not defined for all the items selected. Can not proceed.",
                                                 "E", false, "F");
                                return null;
                            }

                        }

                        OperationBinding chkLot = getBindings().getOperationBinding("checkLotSelected");
                        chkLot.getParamsMap().put("CldId", paramCldId);
                        chkLot.getParamsMap().put("SlocId", paramSlocId);
                        chkLot.getParamsMap().put("OrgId", paramOrgId);
                        chkLot.execute();
                        String lotchkR = "N";
                        if (chkLot.getResult() != null) {
                            lotchkR = chkLot.getResult().toString();
                        }

                        String paramBinChk = resolvEl("#{pageFlowScope.GLBL_ORG_BIN_CHK}");
                        if ("N".equalsIgnoreCase(lotchkR)) {

                            OperationBinding chkBin = getBindings().getOperationBinding("checkBinSelected");
                            chkBin.getParamsMap().put("CldId", paramCldId);
                            chkBin.getParamsMap().put("SlocId", paramSlocId);
                            chkBin.getParamsMap().put("OrgId", paramOrgId);
                            chkBin.getParamsMap().put("Binchk", paramBinChk);
                            chkBin.execute();

                            if ("N".equalsIgnoreCase(chkBin.getResult().toString())) {
                                OperationBinding chkSr = getBindings().getOperationBinding("checkSerialSelected");
                                chkSr.getParamsMap().put("CldId", paramCldId);
                                chkSr.getParamsMap().put("SlocId", paramSlocId);
                                chkSr.getParamsMap().put("OrgId", paramOrgId);
                                chkSr.getParamsMap().put("Binchk", paramBinChk);
                                chkSr.execute();
                                String srChkR = "N";
                                if (chkSr.getResult() != null) {
                                    srChkR = chkSr.getResult().toString();
                                }
                                if ("N".equalsIgnoreCase(srChkR)) {
                                    //check if all entered
                                    _log.info("Stock is given for all items");

                                } else {
                                    txnvalid = "N";
                                }
                            } else {
                                txnvalid = "N";
                            }
                        } else {
                            txnvalid = "N";
                        }


                        /**
                     * Stock is Updated for all Items or not.? in Both Cases OB & FGR.? @Nitesh Garg(Close)
                     */
                        if (txnvalid.equals("Y")) {
                            OperationBinding rcptNoOp = getBindings().getOperationBinding("getRcptNo");
                            rcptNoOp.getParamsMap().put("SlocId", paramSlocId);
                            rcptNoOp.getParamsMap().put("CldId", paramCldId);
                            rcptNoOp.getParamsMap().put("OrgId", paramOrgId);
                            rcptNoOp.getParamsMap().put("WhId", getWhIdPage().getValue().toString());
                            rcptNoOp.getParamsMap().put("fyId", fyNo);
                            rcptNoOp.execute();

                            Object rno = rcptNoOp.getResult();
                            _log.info("--GeNo--" + rno);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(rcptNo);
                            OperationBinding exec = getBindings().getOperationBinding("Commit");
                            exec.execute();

                            OperationBinding execUpl = getBindings().getOperationBinding("updateLndCost");
                            execUpl.getParamsMap().put("cldId", paramCldId);
                            execUpl.getParamsMap().put("SlocId", paramSlocId);
                            execUpl.getParamsMap().put("OrgId", paramOrgId);
                            execUpl.execute();
                            // exec.execute();  commented on 23-may-2014

                            String retval = callWfMethods(paramSlocId, paramCldId, paramOrgId, paramUsrId, "SF");

                            if ("Y".equals(retval)) {
                                srNoBind.setValue("");
                                srNoBind.resetValue();
                                AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                                // exec.execute();
                                RequestContext context = RequestContext.getCurrentInstance();
                                context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                return "goTOWf";
                                //showFacesMessage("Receipt "+rno+" Saved Succesfully", "I", false, "F");
                            } else {
                                _log.info("Error in saving WF related data");
                            }
                        } else {
                            _log.info("Stock Update Problem,Can not Forward");
                            // showFacesMessage("To Forward,Please Update PO No. for this Receipt.", "E", false, "F");
                        }

                    } else { //For all other rcpt Types
                        if (srcDocType.getValue() != null) {

                            Integer scDocType = Integer.parseInt(srcDocType.getValue().toString());

                            if (scDocType.equals(SOURCE_DOC_TYPE_PO) || scDocType.equals(SOURCE_DOC_TYPE_WOUT_PO) || scDocType.equals(SOURCE_DOC_TYPE_IMPORT_PO) || scDocType.equals(SOURCE_DOC_TYPE_JITR)) { // for Import PO 17/04/2015
                                //Check if any PO Added or not in both Types@nitesh.
                                OperationBinding chkpo = getBindings().getOperationBinding("checkForPOPresent");
                                chkpo.execute();
                                String ispoexist = "N";
                                if (chkpo.getErrors().size() == 0 && chkpo.getResult() != null)
                                    ispoexist = (String) chkpo.getResult();
                                if (ispoexist.equals("N") && !(scDocType.equals(SOURCE_DOC_TYPE_JITR))) {
                                    showFacesMessage("Not a Valid PO,Please Update PO No.", "E", false, "F");
                                } else {
                                    OperationBinding rFy = getBindings().getOperationBinding("getFYid");
                                    rFy.getParamsMap().put("CldId", paramCldId);
                                    rFy.getParamsMap().put("OrgId", paramOrgId);
                                    rFy.getParamsMap().put("geDate", (Timestamp) rcptDate.getValue());
                                    rFy.getParamsMap().put("Mode", "V");
                                    rFy.execute();

                                    Object fy = rFy.getResult();

                                    fyNo = Integer.parseInt(fy.toString());

                                    OperationBinding chkRcvd =
                                        getBindings().getOperationBinding("checkForRcvdQtyPresent");
                                    chkRcvd.getParamsMap().put("CldId", paramCldId);
                                    chkRcvd.getParamsMap().put("slocId", paramSlocId);
                                    chkRcvd.getParamsMap().put("OrgId", paramOrgId);
                                    chkRcvd.getParamsMap().put("whId", getWhIdPage().getValue().toString());
                                    chkRcvd.execute();


                                    HashSet h = (HashSet) chkRcvd.getResult();
                                    if (!h.isEmpty()) {
                                        _log.info("HashSet not empty" + h);
                                        //Key->MSG.747-Some items do not have received/rejected/reworkable quantity for the following Documents
                                        StringBuilder msg =
                                            new StringBuilder("<html><body><p>Some items do not have received/rejected/reworkable quantity for the following Documents : <b>" +
                                                              h + "</b> </p>");
                                        //key->MSG.706->>Click YES to delete.
                                        msg.append("<ul> <li>Click YES to delete.</li>");
                                        //key->MSG.707->>Click NO to continue.
                                        msg.append("<li>Click NO to continue.</li></ul>");
                                        msg.append("</body></html>");


                                        msgpop = msg.toString();
                                        /*    FacesMessage fm=new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg);
                            FacesContext.getCurrentInstance().addMessage(null, fm); */
                                        showPopup(confDlvQtyChkPopUp, true);
                                        _log.info("After msg popup");

                                    } else { //if received/rej/rwk qty present
                                        String txnvalid = "Y";

                                        if ("N".equals(authStat.getValue().toString())) {

                                            //check if all the serial nos are entered
                                            //if yes,
                                            OperationBinding chkSerOp =
                                                getBindings().getOperationBinding("checkSerialEntered");
                                            chkSerOp.getParamsMap().put("CldId", paramCldId);
                                            chkSerOp.getParamsMap().put("SlocId", paramSlocId);
                                            chkSerOp.getParamsMap().put("OrgId", paramOrgId);
                                            chkSerOp.execute();
                                            _log.info("---txninvalid---" + txnvalid);
                                            if ("N".equals(chkSerOp.getResult().toString())) {
                                                String chkReworkableNe = "N";
                                                OperationBinding chkRwkable =
                                                    getBindings().getOperationBinding("chkRWkableQtyPresent");
                                                chkRwkable.execute();
                                                if (chkRwkable.getResult() != null) {
                                                    chkReworkableNe = chkRwkable.getResult().toString();
                                                }

                                                _log.info("chkReworkableNe   :  " + chkReworkableNe);

                                                // if("Y".equals(chkReworkable.getValue().toString())){//if reworkable qty present
                                                if ("Y".equalsIgnoreCase(chkReworkableNe)) {
                                                    OperationBinding setStatRcptOp =
                                                        getBindings().getOperationBinding("setRcptStatus");
                                                    setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_REWORK);
                                                    setStatRcptOp.execute();
                                                    return null;
                                                } else {

                                                    if (scDocType.equals(SOURCE_DOC_TYPE_PO) ||
                                                        scDocType.equals(SOURCE_DOC_TYPE_IMPORT_PO) || scDocType.equals(SOURCE_DOC_TYPE_JITR)) {

                                                        //validation if all values entered.
                                                        _log.info("QC REQD--" + checkQcReqd.getValue().toString());

                                                        if ("Y".equalsIgnoreCase(checkQcReqd.getValue().toString())) {
                                                            OperationBinding chkRcvItm =
                                                                getBindings().getOperationBinding("chkIsAnyItmForQc");
                                                            chkRcvItm.execute();
                                                            String chkQc = "Y";
                                                            if (chkRcvItm.getErrors().size() == 0 &&
                                                                chkRcvItm.getResult() != null)
                                                                chkQc = (String) chkRcvItm.getResult();
                                                            if (chkQc.equals("Y")) {
                                                                OperationBinding setStatRcptOp =
                                                                    getBindings().getOperationBinding("setRcptStatus");
                                                                setStatRcptOp.getParamsMap().put("stat",
                                                                                                 RCPT_STATUS_QCPEND);
                                                                setStatRcptOp.execute();
                                                            } else {

                                                            }
                                                        } else {
                                                            _log.info("QC NOT REQD");
                                                            /*   OperationBinding setStatRcptOp = getBindings().getOperationBinding("setRcptStatus");
                                                 setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_PEND);
                                                 setStatRcptOp.execute();
                                     } */

                                                        }
                                                    }

                                                } //end else
                                            } else {
                                                txnvalid = "N";
                                            }
                                        } else if ("Y".equalsIgnoreCase(authStat.getValue().toString())) {
                                            //will never enter here in case of snf as button will be disabled

                                            OperationBinding exec = getBindings().getOperationBinding("Commit");
                                            exec.execute();
                                            OperationBinding execUpl =
                                                getBindings().getOperationBinding("updateLndCost");
                                            execUpl.getParamsMap().put("cldId", paramCldId);
                                            execUpl.getParamsMap().put("SlocId", paramSlocId);
                                            execUpl.getParamsMap().put("OrgId", paramOrgId);
                                            execUpl.execute();
                                            exec.execute();
                                            OperationBinding execUp = getBindings().getOperationBinding("updateStock");
                                            execUp.getParamsMap().put("cldId", paramCldId);
                                            execUp.getParamsMap().put("SlocId", paramSlocId);
                                            execUp.getParamsMap().put("OrgId", paramOrgId);
                                            execUp.execute();
                                            Object o = execUp.getResult();
                                            _log.info("Stock update called :" + o);

                                            if (execUp.getErrors().isEmpty()) {
                                                //   exec.execute();  //commit
                                                OperationBinding setStatRcptOp =
                                                    getBindings().getOperationBinding("setRcptStatus");
                                                setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_STOCKUP);
                                                setStatRcptOp.execute();

                                            } else {
                                                _log.info("Stock update failed!!");
                                            }
                                        }


                                        if ("Y".equalsIgnoreCase(txnvalid)) {
                                            if (tfMode.equals("A")) {
                                                OperationBinding rcptNoOp =
                                                    getBindings().getOperationBinding("getRcptNo");
                                                rcptNoOp.getParamsMap().put("SlocId", paramSlocId);
                                                rcptNoOp.getParamsMap().put("CldId", paramCldId);
                                                rcptNoOp.getParamsMap().put("OrgId", paramOrgId);
                                                rcptNoOp.getParamsMap().put("WhId",
                                                                            getWhIdPage().getValue().toString());
                                                rcptNoOp.getParamsMap().put("fyId", fyNo);
                                                rcptNoOp.execute();
                                                Object rno = rcptNoOp.getResult();
                                                _log.info("--GeNo--" + rno);

                                            }

                                            AdfFacesContext.getCurrentInstance().addPartialTarget(rcptNo);
                                            //                                            if (scDocType.equals(SOURCE_DOC_TYPE_PO)) {
                                            //                                                _log.info("--updateLndPriceForPO--");
                                            //                                                OperationBinding updateLndPriceForPO =
                                            //                                                    getBindings().getOperationBinding("updateLndPriceForPO");
                                            //                                                updateLndPriceForPO.execute();
                                            //                                            }

                                            OperationBinding popCalc =
                                                getBindings().getOperationBinding("populateCalculations");
                                            popCalc.getParamsMap().put("p_cld_id", paramCldId);
                                            popCalc.getParamsMap().put("p_sloc_id", paramSlocId);
                                            popCalc.getParamsMap().put("p_org_id", paramOrgId);
                                            popCalc.getParamsMap().put("p_usr_id", paramUsrId);
                                            popCalc.execute();

                                            OperationBinding exec = getBindings().getOperationBinding("Commit");
                                            exec.execute();
                                            if (exec.getErrors().isEmpty()) {
                                                if ("N".equalsIgnoreCase(authStat.getValue().toString())) {
                                                    String retval =
                                                        callWfMethods(paramSlocId, paramCldId, paramOrgId, paramUsrId,
                                                                      "SF");
                                                    _log.info("WF methods call complete:" + retval);
                                                    /**
                                                * for call rcpt cal function.
                                                */
                                                    // String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
                                                    // String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
                                                    // Integer paramSlocId= Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                                                    // Integer paramUsrId= Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
                                                    // Object obj = invcTypeBindVar.getValue();


                                                }
                                                if (scDocType.equals(SOURCE_DOC_TYPE_PO)) {

                                                    _log.info("Set currency:::::::::::::::::::::::::: ");
                                                    OperationBinding opcurr =
                                                        getBindings().getOperationBinding("setCurrIdSpPoToMtlRcpt");
                                                    opcurr.getParamsMap().put("value", SOURCE_DOC_TYPE_PO);
                                                    opcurr.execute();

                                                    // exec.execute();  commented on 23-may-2014

                                                }
                                                if (scDocType.equals(SOURCE_DOC_TYPE_IMPORT_PO)) {

                                                    _log.info("Set currency:::::::::::::::::::::::::: ");
                                                    OperationBinding opcurr =
                                                        getBindings().getOperationBinding("setCurrIdSpPoToMtlRcpt");
                                                    opcurr.getParamsMap().put("value", SOURCE_DOC_TYPE_IMPORT_PO);
                                                    opcurr.execute();

                                                    // exec.execute();  commented on 23-may-2014

                                                }
                                                
                                                if (scDocType.equals(SOURCE_DOC_TYPE_JITR)) {

                                                    _log.info("Set currency:::::::::::::::::::::::::: ");
                                                    OperationBinding opcurr =
                                                        getBindings().getOperationBinding("setCurrIdSpPoToMtlRcpt");
                                                    opcurr.getParamsMap().put("value", SOURCE_DOC_TYPE_JITR);
                                                    opcurr.execute();

                                                    // exec.execute();  commented on 23-may-2014

                                                }
                                                
                                                OperationBinding chkTax =
                                                    getBindings().getOperationBinding("checkForTaxAmountMisMatch");
                                                chkTax.getParamsMap().put("CldId", paramCldId);
                                                chkTax.getParamsMap().put("slocId", paramSlocId);
                                                chkTax.getParamsMap().put("OrgId", paramOrgId);
                                                chkTax.getParamsMap().put("whId", getWhIdPage().getValue().toString());
                                                chkTax.execute();


                                                HashSet h1 = (HashSet) chkTax.getResult();
                                                if (!h1.isEmpty()) {
                                                    _log.info("HashSet not empty" + h1);
                                                    //Key->MSG.747-Some items do not have received/rejected/reworkable quantity for the following Documents
                                                    StringBuilder msg =
                                                        new StringBuilder("<html><body><p>The receipt could not be forwarded as tax amount for the following items are not applied properly. Reapply tax for the items and forwarded again : <b>" +
                                                                          h1 + "</b> </p>");
                                                    msg.append("</body></html>");


                                                    msgpop = msg.toString();

                                                }


                                                srNoBind.setValue("");
                                                srNoBind.resetValue();
                                                AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                                                RequestContext context = RequestContext.getCurrentInstance();
                                                context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                                //Check Receipt Status is QC Pendng or not.if QC Pending then do not forward.
                                                OperationBinding chkStat =
                                                    getBindings().getOperationBinding("CheckRcptStatus");
                                                chkStat.execute();
                                                OperationBinding chkAllPrice =
                                                    getBindings().getOperationBinding("chkAllPurPricePresent");
                                                chkAllPrice.execute();
                                                if (chkStat.getResult() != null) {
                                                    Integer stat = (Integer) chkStat.getResult();
                                                    if (stat.equals(RCPT_STATUS_QCPEND)) {
                                                        showFacesMessage("QC Pending! Can not Forward Receipt.", "I",
                                                                         false, "F");
                                                        return null;
                                                    } else if ("Y".equalsIgnoreCase(chkAllPrice.getResult().toString())) { // add for check
                                                        showFacesMessage("Some item price zero . Can not Forward Receipt.",
                                                                         "I", false, "F");
                                                        return null;
                                                        // }
                                                    } else if (!h1.isEmpty() && scDocType.equals(SOURCE_DOC_TYPE_PO)) { // add for check
                                                        _log.info(" inside tax validatore");
                                                        showFacesMessage(msgpop, "I", false, "F");
                                                        return null;
                                                    }

                                                    else {
                                                        if (scDocType.equals(SOURCE_DOC_TYPE_PO)) {
                                                            _log.info("--updateLndPriceForPO--");
                                                            OperationBinding updateLndPriceForPO =
                                                                getBindings().getOperationBinding("updateLndPriceForPO");
                                                            updateLndPriceForPO.execute();
                                                        }
                                                        return "goTOWf";
                                                    }
                                                } else
                                                    showFacesMessage("Error while Forwarding.Contact to ESS!", "E",
                                                                     false, "F");
                                                return null;
                                                // return "goTOWf";
                                                // enableHeader=false;
                                                //   showFacesMessage("Receipt Saved Succesfully", "I", false, "F");
                                            }


                                        } //endIf---TxnValid
                                    } //end for all Rcpt Types
                                }
                            } else if (scDocType.equals(SOURCE_DOC_TYPE_PRODCT)) { ///   SOURCE_DOC_TYPE_PRODCT

                                OperationBinding rFy = getBindings().getOperationBinding("getFYid");
                                rFy.getParamsMap().put("CldId", paramCldId);
                                rFy.getParamsMap().put("OrgId", paramOrgId);
                                rFy.getParamsMap().put("geDate", (Timestamp) rcptDate.getValue());
                                rFy.getParamsMap().put("Mode", "V");
                                rFy.execute();

                                Object fy = rFy.getResult();

                                fyNo = Integer.parseInt(fy.toString());

                                OperationBinding chkRcvd = getBindings().getOperationBinding("checkForRcvdQtyPresent");
                                chkRcvd.getParamsMap().put("CldId", paramCldId);
                                chkRcvd.getParamsMap().put("slocId", paramSlocId);
                                chkRcvd.getParamsMap().put("OrgId", paramOrgId);
                                chkRcvd.getParamsMap().put("whId", getWhIdPage().getValue().toString());
                                chkRcvd.execute();


                                HashSet h = (HashSet) chkRcvd.getResult();
                                if (!h.isEmpty()) {
                                    _log.info("HashSet not empty" + h);
                                    //Key->MSG.747-Some items do not have received/rejected/reworkable quantity for the following Documents
                                    StringBuilder msg =
                                        new StringBuilder("<html><body><p>Some items do not have received/rejected/reworkable quantity for the following Documents : <b>" +
                                                          h + "</b> </p>");
                                    //key->MSG.706->>Click YES to delete.
                                    msg.append("<ul> <li>Click YES to delete.</li>");
                                    //key->MSG.707->>Click NO to continue.
                                    msg.append("<li>Click NO to continue.</li></ul>");
                                    msg.append("</body></html>");


                                    msgpop = msg.toString();
                                    /*    FacesMessage fm=new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg);
                                FacesContext.getCurrentInstance().addMessage(null, fm); */
                                    showPopup(confDlvQtyChkPopUp, true);
                                    _log.info("After msg popup");

                                } else { //if received/rej/rwk qty present
                                    String txnvalid = "Y";

                                    if ("N".equals(authStat.getValue().toString())) {

                                        //check if all the serial nos are entered
                                        //if yes,
                                        OperationBinding chkSerOp =
                                            getBindings().getOperationBinding("checkSerialEntered");
                                        chkSerOp.getParamsMap().put("CldId", paramCldId);
                                        chkSerOp.getParamsMap().put("SlocId", paramSlocId);
                                        chkSerOp.getParamsMap().put("OrgId", paramOrgId);
                                        chkSerOp.execute();
                                        _log.info("---txninvalid---" + txnvalid);
                                        if ("N".equals(chkSerOp.getResult().toString())) {
                                            String chkReworkableNe = "N";
                                            OperationBinding chkRwkable =
                                                getBindings().getOperationBinding("chkRWkableQtyPresent");
                                            chkRwkable.execute();
                                            if (chkRwkable.getResult() != null) {
                                                chkReworkableNe = chkRwkable.getResult().toString();
                                            }

                                            _log.info("chkReworkableNe   :  " + chkReworkableNe);

                                            // if("Y".equals(chkReworkable.getValue().toString())){//if reworkable qty present
                                            if ("Y".equalsIgnoreCase(chkReworkableNe)) {
                                                OperationBinding setStatRcptOp =
                                                    getBindings().getOperationBinding("setRcptStatus");
                                                setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_REWORK);
                                                setStatRcptOp.execute();
                                                return null;
                                            } else {

                                                if (scDocType.equals(SOURCE_DOC_TYPE_PRODCT)) {

                                                    //validation if all values entered.
                                                    _log.info("QC REQD--" + checkQcReqd.getValue().toString());

                                                    if ("Y".equalsIgnoreCase(checkQcReqd.getValue().toString())) {
                                                        OperationBinding chkRcvItm =
                                                            getBindings().getOperationBinding("chkIsAnyItmForQc");
                                                        chkRcvItm.execute();
                                                        String chkQc = "Y";
                                                        if (chkRcvItm.getErrors().size() == 0 &&
                                                            chkRcvItm.getResult() != null)
                                                            chkQc = (String) chkRcvItm.getResult();
                                                        if (chkQc.equals("Y")) {
                                                            OperationBinding setStatRcptOp =
                                                                getBindings().getOperationBinding("setRcptStatus");
                                                            setStatRcptOp.getParamsMap().put("stat",
                                                                                             RCPT_STATUS_QCPEND);
                                                            setStatRcptOp.execute();
                                                        } else {

                                                        }
                                                    } else {
                                                        _log.info("QC NOT REQD");
                                                        /*   OperationBinding setStatRcptOp = getBindings().getOperationBinding("setRcptStatus");
                                                     setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_PEND);
                                                     setStatRcptOp.execute();
                                         } */

                                                    }
                                                }

                                            } //end else
                                        } else {
                                            txnvalid = "N";
                                        }
                                    } else if ("Y".equalsIgnoreCase(authStat.getValue().toString())) {
                                        //will never enter here in case of snf as button will be disabled

                                        OperationBinding exec = getBindings().getOperationBinding("Commit");
                                        exec.execute();
                                        OperationBinding execUpl = getBindings().getOperationBinding("updateLndCost");
                                        execUpl.getParamsMap().put("cldId", paramCldId);
                                        execUpl.getParamsMap().put("SlocId", paramSlocId);
                                        execUpl.getParamsMap().put("OrgId", paramOrgId);
                                        execUpl.execute();
                                        exec.execute();
                                        OperationBinding execUp = getBindings().getOperationBinding("updateStock");
                                        execUp.getParamsMap().put("cldId", paramCldId);
                                        execUp.getParamsMap().put("SlocId", paramSlocId);
                                        execUp.getParamsMap().put("OrgId", paramOrgId);
                                        execUp.execute();
                                        Object o = execUp.getResult();
                                        _log.info("Stock update called :" + o);

                                        if (execUp.getErrors().isEmpty()) {
                                            //   exec.execute();  //commit
                                            OperationBinding setStatRcptOp =
                                                getBindings().getOperationBinding("setRcptStatus");
                                            setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_STOCKUP);
                                            setStatRcptOp.execute();

                                        } else {
                                            _log.info("Stock update failed!!");
                                        }
                                    }


                                    if ("Y".equalsIgnoreCase(txnvalid)) {
                                        if (tfMode.equals("A")) {
                                            OperationBinding rcptNoOp = getBindings().getOperationBinding("getRcptNo");
                                            rcptNoOp.getParamsMap().put("SlocId", paramSlocId);
                                            rcptNoOp.getParamsMap().put("CldId", paramCldId);
                                            rcptNoOp.getParamsMap().put("OrgId", paramOrgId);
                                            rcptNoOp.getParamsMap().put("WhId", getWhIdPage().getValue().toString());
                                            rcptNoOp.getParamsMap().put("fyId", fyNo);
                                            rcptNoOp.execute();
                                            Object rno = rcptNoOp.getResult();
                                            _log.info("--GeNo--" + rno);

                                        }

                                        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptNo);

                                        OperationBinding exec = getBindings().getOperationBinding("Commit");
                                        exec.execute();
                                        if (exec.getErrors().isEmpty()) {
                                            if ("N".equalsIgnoreCase(authStat.getValue().toString())) {
                                                String retval =
                                                    callWfMethods(paramSlocId, paramCldId, paramOrgId, paramUsrId,
                                                                  "SF");
                                                _log.info("WF methods call complete:" + retval);
                                                /**
                                                    * for call rcpt cal function.
                                                    */
                                                // String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
                                                // String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
                                                // Integer paramSlocId= Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                                                // Integer paramUsrId= Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
                                                // Object obj = invcTypeBindVar.getValue();

                                                /*  OperationBinding popCalc = getBindings().getOperationBinding("populateCalculations");
                                                   popCalc.getParamsMap().put("p_cld_id",paramCldId);
                                                   popCalc.getParamsMap().put("p_sloc_id",paramSlocId);
                                                   popCalc.getParamsMap().put("p_org_id",paramOrgId);
                                                   popCalc.getParamsMap().put("p_usr_id",paramUsrId);
                                                   popCalc.execute(); */

                                            }
                                            /*  if(scDocType.equals(SOURCE_DOC_TYPE_PO)){

                                              _log.info("Set currency:::::::::::::::::::::::::: ");
                                              OperationBinding opcurr=getBindings().getOperationBinding("setCurrIdSpPoToMtlRcpt");
                                              opcurr.getParamsMap().put("value", SOURCE_DOC_TYPE_PO);
                                              opcurr.execute();

                                                // exec.execute();  commented on 23-may-2014

                                          }  */
                                            srNoBind.setValue("");
                                            srNoBind.resetValue();
                                            AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                                            RequestContext context = RequestContext.getCurrentInstance();
                                            context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                            //Check Receipt Status is QC Pendng or not.if QC Pending then do not forward.
                                            OperationBinding chkStat =
                                                getBindings().getOperationBinding("CheckRcptStatus");
                                            chkStat.execute();
                                            OperationBinding chkAllPrice =
                                                getBindings().getOperationBinding("chkAllPurPricePresent");
                                            chkAllPrice.execute();
                                            if (chkStat.getResult() != null) {
                                                Integer stat = (Integer) chkStat.getResult();
                                                if (stat.equals(RCPT_STATUS_QCPEND)) {
                                                    showFacesMessage("QC Pending! Can not Forward Receipt.", "I", false,
                                                                     "F");
                                                    return null;
                                                } else if ("Y".equalsIgnoreCase(chkAllPrice.getResult().toString())) { // add for check
                                                    showFacesMessage("Some item price zero . Can not Forward Receipt.",
                                                                     "I", false, "F");
                                                    return null;
                                                } else
                                                    return "goTOWf";
                                            } else
                                                showFacesMessage("Error while Forwarding.Contact to ESS!", "E", false,
                                                                 "F");
                                            return null;
                                            // return "goTOWf";
                                            // enableHeader=false;
                                            //   showFacesMessage("Receipt Saved Succesfully", "I", false, "F");
                                        }


                                    } //endIf---TxnValid
                                } //end for all Rcpt Types


                            } else if (scDocType.equals(SOURCE_DOC_TYPE_TO)) {

                                OperationBinding rFy = getBindings().getOperationBinding("getFYid");
                                rFy.getParamsMap().put("CldId", paramCldId);
                                rFy.getParamsMap().put("OrgId", paramOrgId);
                                rFy.getParamsMap().put("geDate", (Timestamp) rcptDate.getValue());
                                rFy.getParamsMap().put("Mode", "V");
                                rFy.execute();

                                Object fy = rFy.getResult();

                                fyNo = Integer.parseInt(fy.toString());

                                OperationBinding chkRcvd = getBindings().getOperationBinding("checkForRcvdQtyPresent");
                                chkRcvd.getParamsMap().put("CldId", paramCldId);
                                chkRcvd.getParamsMap().put("slocId", paramSlocId);
                                chkRcvd.getParamsMap().put("OrgId", paramOrgId);
                                chkRcvd.getParamsMap().put("whId", getWhIdPage().getValue().toString());
                                chkRcvd.execute();


                                HashSet h = (HashSet) chkRcvd.getResult();
                                if (!h.isEmpty()) {
                                    _log.info("TO HashSet not empty" + h);
                                    //Key->MSG.747-Some items do not have received/rejected/reworkable quantity for the following Documents
                                    StringBuilder msg =
                                        new StringBuilder("<html><body><p>Some items do not have received/rejected/reworkable quantity for the following Documents : <b>" +
                                                          h + "</b> </p>");
                                    //key->MSG.706->>Click YES to delete.
                                    msg.append("<ul> <li>Click YES to delete.</li>");
                                    //key->MSG.707->>Click NO to continue.
                                    msg.append("<li>Click NO to continue.</li></ul>");
                                    msg.append("</body></html>");

                                    msgpop = msg.toString();
                                    /*    FacesMessage fm=new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg);
                                               FacesContext.getCurrentInstance().addMessage(null, fm); */
                                    showPopup(confDlvQtyChkPopUp, true);
                                    _log.info("After msg popup");
                                } else {
                                    if (tfMode.equals("A")) {
                                        OperationBinding rcptNoOp = getBindings().getOperationBinding("getRcptNo");
                                        rcptNoOp.getParamsMap().put("SlocId", paramSlocId);
                                        rcptNoOp.getParamsMap().put("CldId", paramCldId);
                                        rcptNoOp.getParamsMap().put("OrgId", paramOrgId);
                                        rcptNoOp.getParamsMap().put("WhId", getWhIdPage().getValue().toString());
                                        rcptNoOp.getParamsMap().put("fyId", fyNo);
                                        rcptNoOp.execute();
                                        Object rno = rcptNoOp.getResult();
                                        _log.info("--RCPTNO TO--" + rno);

                                    }

                                    AdfFacesContext.getCurrentInstance().addPartialTarget(rcptNo);

                                    OperationBinding exec = getBindings().getOperationBinding("Commit");
                                    exec.execute();
                                    if (exec.getErrors().isEmpty()) {
                                        if ("N".equalsIgnoreCase(authStat.getValue().toString())) {

                                            String retval =
                                                callWfMethods(paramSlocId, paramCldId, paramOrgId, paramUsrId, "SF");
                                            _log.info("WF methods call complete:" + retval);
                                        }
                                        // exec.execute(); commented on 23-may-2014
                                        srNoBind.setValue("");
                                        srNoBind.resetValue();
                                        AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                                        RequestContext context = RequestContext.getCurrentInstance();
                                        context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                        // enableHeader=false;
                                        //showFacesMessage("Receipt Saved Succesfully", "I", false, "F");
                                        return "goTOWf";
                                    }
                                }
                            }
                            //                   else if(scDocType.equals(SOURCE_DOC_TYPE_MRN)){
                            //                       OperationBinding rFy = getBindings().getOperationBinding("getFYid");
                            //                            rFy.getParamsMap().put("CldId", paramCldId);
                            //                            rFy.getParamsMap().put("OrgId", paramOrgId);
                            //                            rFy.getParamsMap().put("geDate", (Timestamp)rcptDate.getValue());
                            //                            rFy.getParamsMap().put("Mode", "V");
                            //                            rFy.execute();
                            //
                            //                            Object fy=rFy.getResult();
                            //
                            //                       fyNo=Integer.parseInt(fy.toString());
                            //
                            //                       OperationBinding chkRcvd = getBindings().getOperationBinding("checkForRcvdQtyPresent");
                            //                          chkRcvd.getParamsMap().put("CldId", paramCldId);
                            //                          chkRcvd.getParamsMap().put("slocId", paramSlocId);
                            //                          chkRcvd.getParamsMap().put("OrgId", paramOrgId);
                            //                          chkRcvd.getParamsMap().put("whId", getWhIdPage().getValue().toString());
                            //                          chkRcvd.execute();
                            //
                            //
                            //                       HashSet h=(HashSet)chkRcvd.getResult();
                            //                       if(!h.isEmpty()){
                            //                       _log.info("TO HashSet not empty"+h);
                            //               //key->MSG.705->Some items do not have received quantity for the following Documents
                            //                       StringBuilder msg =new StringBuilder("<html><body><p>Some items do not have received quantity for the following Documents : <b>" + h +
                            //                                                                      "</b> </p>");
                            //                           //key->MSG.706->>Click YES to delete.
                            //                           msg.append("<ul> <li>Click YES to delete.</li>");
                            //                           //key->MSG.707->>Click NO to continue.
                            //                           msg.append("<li>Click NO to continue.</li></ul>");
                            //                           msg.append("</body></html>");
                            //
                            //                          msgpop=msg.toString();
                            //                                              /*    FacesMessage fm=new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg);
                            //                                               FacesContext.getCurrentInstance().addMessage(null, fm); */
                            //                          showPopup(confDlvQtyChkPopUp, true);
                            //                          _log.info("After msg popup");
                            //                       }else{
                            //                           //check if all enetered
                            //                           if(tfMode.equals("A")){
                            //                             OperationBinding rcptNoOp = getBindings().getOperationBinding("getRcptNo");
                            //                                rcptNoOp.getParamsMap().put("SlocId", paramSlocId);
                            //                                rcptNoOp.getParamsMap().put("CldId", paramCldId);
                            //                                rcptNoOp.getParamsMap().put("OrgId", paramOrgId);
                            //                                rcptNoOp.getParamsMap().put("WhId", getWhIdPage().getValue().toString());
                            //                                rcptNoOp.getParamsMap().put("fyId", fyNo);
                            //                                rcptNoOp.execute();
                            //                              Object rno=rcptNoOp.getResult();
                            //                              _log.info("--RCPTNO TO--"+rno);
                            //
                            //                           }
                            //
                            //                           AdfFacesContext.getCurrentInstance().addPartialTarget(rcptNo);
                            //                           OperationBinding exec = getBindings().getOperationBinding("Commit");
                            //                           exec.execute();
                            //                           if(exec.getErrors().isEmpty()){
                            //                             if("N".equalsIgnoreCase(authStat.getValue().toString())){
                            //                                String retval=callWfMethods(paramSlocId, paramCldId, paramOrgId, paramUsrId,"SF");
                            //                                _log.info("WF methods call complete:"+retval);
                            //                             }
                            //                             //  exec.execute();  commented on 23-may-2014
                            //
                            //                               RequestContext context = RequestContext.getCurrentInstance();
                            //                               context.getPageFlowScope().put("ADD_EDIT_MODE","V");
                            //                               return "goTOWf";
                            //                           }else{
                            //                               _log.info("Commit fail");
                            //                           }
                            //
                            //                       }
                            //                   }
                            else if (scDocType.equals(SOURCE_DOC_TYPE_CPO)) {

                                OperationBinding rFy = getBindings().getOperationBinding("getFYid");
                                rFy.getParamsMap().put("CldId", paramCldId);
                                rFy.getParamsMap().put("OrgId", paramOrgId);
                                rFy.getParamsMap().put("geDate", (Timestamp) rcptDate.getValue());
                                rFy.getParamsMap().put("Mode", "V");
                                rFy.execute();

                                Object fy = rFy.getResult();

                                fyNo = Integer.parseInt(fy.toString());

                                OperationBinding chkRcvd = getBindings().getOperationBinding("checkForRcvdQtyPresent");
                                chkRcvd.getParamsMap().put("CldId", paramCldId);
                                chkRcvd.getParamsMap().put("slocId", paramSlocId);
                                chkRcvd.getParamsMap().put("OrgId", paramOrgId);
                                chkRcvd.getParamsMap().put("whId", getWhIdPage().getValue().toString());
                                chkRcvd.execute();


                                HashSet h = (HashSet) chkRcvd.getResult();
                                if (!h.isEmpty()) {
                                    _log.info("HashSet not empty" + h);
                                    //Key->MSG.747-Some items do not have received/rejected/reworkable quantity for the following Documents
                                    StringBuilder msg =
                                        new StringBuilder("<html><body><p>Some items do not have received/rejected/reworkable quantity for the following Documents : <b>" +
                                                          h + "</b> </p>");
                                    //key->MSG.706->>Click YES to delete.
                                    msg.append("<ul> <li>Click YES to delete.</li>");
                                    //key->MSG.707->>Click NO to continue.
                                    msg.append("<li>Click NO to continue.</li></ul>");
                                    msg.append("</body></html>");


                                    msgpop = msg.toString();

                                    showPopup(confDlvQtyChkPopUp, true);
                                    _log.info("After msg popup");

                                } else { //if received/rej/rwk qty present
                                    String txnvalid = "Y";

                                    if ("N".equals(authStat.getValue().toString())) {
                                        _log.info("Not approved");
                                        //check if all the serial nos are entered
                                        //if yes,
                                        OperationBinding chkSerOp =
                                            getBindings().getOperationBinding("checkSerialEntered");
                                        chkSerOp.getParamsMap().put("CldId", paramCldId);
                                        chkSerOp.getParamsMap().put("SlocId", paramSlocId);
                                        chkSerOp.getParamsMap().put("OrgId", paramOrgId);
                                        chkSerOp.execute();
                                        _log.info("---txninvalid---" + txnvalid);
                                        if ("N".equals(chkSerOp.getResult().toString())) {
                                            String chkReworkableN = "N";
                                            OperationBinding chkRwkable =
                                                getBindings().getOperationBinding("chkRWkableQtyPresent");
                                            chkRwkable.execute();
                                            if (chkRwkable.getResult() != null) {
                                                chkReworkableN = chkRwkable.getResult().toString();
                                            }

                                            _log.info("chkReworkableN   :  " + chkReworkableN);

                                            // if("Y".equals(chkReworkable.getValue().toString())){//if reworkable qty present
                                            if ("Y".equalsIgnoreCase(chkReworkableN)) {
                                                _log.info(chkReworkableN + "    ChkReworkable is=" +
                                                          chkReworkable.getValue());
                                                OperationBinding setStatRcptOp =
                                                    getBindings().getOperationBinding("setRcptStatus");
                                                setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_REWORK);
                                                setStatRcptOp.execute();
                                                return null;
                                            } else {

                                                if (scDocType.equals(SOURCE_DOC_TYPE_CPO)) {

                                                    //validation if all values entered.
                                                    _log.info("QC REQD--" + checkQcReqd.getValue().toString());

                                                    if ("Y".equalsIgnoreCase(checkQcReqd.getValue().toString())) {
                                                        OperationBinding chkRcvItm =
                                                            getBindings().getOperationBinding("chkIsAnyItmForQc");
                                                        chkRcvItm.execute();
                                                        String chkQc = "Y";
                                                        if (chkRcvItm.getErrors().size() == 0 &&
                                                            chkRcvItm.getResult() != null)
                                                            chkQc = (String) chkRcvItm.getResult();
                                                        if (chkQc.equals("Y")) {
                                                            OperationBinding setStatRcptOp =
                                                                getBindings().getOperationBinding("setRcptStatus");
                                                            setStatRcptOp.getParamsMap().put("stat",
                                                                                             RCPT_STATUS_QCPEND);
                                                            setStatRcptOp.execute();
                                                        } else {

                                                        }
                                                    } else {
                                                        _log.info("QC NOT REQD");
                                                    }
                                                }

                                            } //end else
                                        } else {
                                            txnvalid = "N";
                                        }
                                    } else if ("Y".equalsIgnoreCase(authStat.getValue().toString())) {
                                        //will never enter here in case of snf as button will be disabled

                                        OperationBinding exec = getBindings().getOperationBinding("Commit");
                                        exec.execute();
                                        OperationBinding execUpl = getBindings().getOperationBinding("updateLndCost");
                                        execUpl.getParamsMap().put("cldId", paramCldId);
                                        execUpl.getParamsMap().put("SlocId", paramSlocId);
                                        execUpl.getParamsMap().put("OrgId", paramOrgId);
                                        execUpl.execute();
                                        exec.execute();
                                        OperationBinding execUp = getBindings().getOperationBinding("updateStock");
                                        execUp.getParamsMap().put("cldId", paramCldId);
                                        execUp.getParamsMap().put("SlocId", paramSlocId);
                                        execUp.getParamsMap().put("OrgId", paramOrgId);
                                        execUp.execute();
                                        Object o = execUp.getResult();
                                        _log.info("Stock update called :" + o);

                                        if (execUp.getErrors().isEmpty()) {
                                            //   exec.execute();  //commit
                                            OperationBinding setStatRcptOp =
                                                getBindings().getOperationBinding("setRcptStatus");
                                            setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_STOCKUP);
                                            setStatRcptOp.execute();

                                        } else {
                                            _log.info("Stock update failed!!");
                                        }
                                    }


                                    if ("Y".equalsIgnoreCase(txnvalid)) {
                                        if (tfMode.equals("A")) {
                                            OperationBinding rcptNoOp = getBindings().getOperationBinding("getRcptNo");
                                            rcptNoOp.getParamsMap().put("SlocId", paramSlocId);
                                            rcptNoOp.getParamsMap().put("CldId", paramCldId);
                                            rcptNoOp.getParamsMap().put("OrgId", paramOrgId);
                                            rcptNoOp.getParamsMap().put("WhId", getWhIdPage().getValue().toString());
                                            rcptNoOp.getParamsMap().put("fyId", fyNo);
                                            rcptNoOp.execute();
                                            Object rno = rcptNoOp.getResult();
                                            _log.info("--GeNo--" + rno);

                                        }

                                        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptNo);

                                        OperationBinding exec = getBindings().getOperationBinding("Commit");
                                        exec.execute();
                                        if (exec.getErrors().isEmpty()) {
                                            if ("N".equalsIgnoreCase(authStat.getValue().toString())) {
                                                String retval =
                                                    callWfMethods(paramSlocId, paramCldId, paramOrgId, paramUsrId,
                                                                  "SF");
                                                _log.info("WF methods call complete:" + retval);
                                                
                                                if("N".equalsIgnoreCase(retval)){
                                                    srNoBind.setValue("");
                                                    srNoBind.resetValue();
                                                    AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                                                    RequestContext context = RequestContext.getCurrentInstance();
                                                    context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                                    return null;
                                                }
                                                
                                            }
                                            if (scDocType.equals(SOURCE_DOC_TYPE_CPO)) {
                                                //  exec.execute();  commented on 23-may-2014
                                                OperationBinding opcurr =
                                                    getBindings().getOperationBinding("setCurrIdSpPoToMtlRcpt");
                                                opcurr.getParamsMap().put("value", SOURCE_DOC_TYPE_CPO);
                                                opcurr.execute();

                                            }
                                            srNoBind.setValue("");
                                            srNoBind.resetValue();
                                            AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                                            RequestContext context = RequestContext.getCurrentInstance();
                                            context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                            //Check Receipt Status is QC Pendng or not.if QC Pending then do not forward.
                                            OperationBinding chkStat =
                                                getBindings().getOperationBinding("CheckRcptStatus");
                                            chkStat.execute();
                                            if (chkStat.getResult() != null) {
                                                Integer stat = (Integer) chkStat.getResult();
                                                if (stat.equals(RCPT_STATUS_QCPEND)) {
                                                    showFacesMessage("QC Pending! Can not Forward Receipt.", "I", false,
                                                                     "F");
                                                    return null;
                                                } else
                                                    return "goTOWf";
                                            } else
                                                showFacesMessage("Error while Forwarding.Contact to ESS!", "E", false,
                                                                 "F");
                                            return null;
                                        }


                                    } //endIf---TxnValid
                                } //end for all Rcpt Types


                            } else {
                                _log.info("Document Out of scope.");
                            }
                        } else {
                            // showFacesMessage("Please Select the Document Type for the Receipt.", "E", false, "F");
                            showFacesMessage("MSG.749", "E", true, "F");
                        }
                    }
                } else {
                    // showFacesMessage("Documents have not been added for this Receipt.Cannot Save.", "E", false, "F");
                    showFacesMessage("MSG.750", "E", true, "F");
                }

            } else {
                // showFacesMessage("Please select the Receipt Type.", "E", false, "F");
                showFacesMessage("MSG.751", "E", true, "F");
            }

        } else {
            _log.info("Enter Rcpt Date");
            //showFacesMessage("Receipt Date is required.", "E", false, "F");
            showFacesMessage("MSG.752", "E", true, "F");

        }


        return null;
    }

    public void geDocVCE(ValueChangeEvent valueChangeEvent) {
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));

        if (valueChangeEvent.getNewValue() != null) {

            OperationBinding op = getBindings().getOperationBinding("isGEDupli");
            op.getParamsMap().put("geno", valueChangeEvent.getNewValue());
            op.execute();
            if (op.getErrors().size() == 0 || op.getResult() != null) {
                if (op.getResult().toString().equals("Y")) {
                    //throw new ValidatorException(new FacesMessage("Duplicate Gate Entry!"));
                    showFacesMessage("Duplicate Gate Entry!", "E", false, "F");
                } else if (op.getResult().toString().equals("N")) {
                    _log.info(" valide resup not duplicate " + valueChangeEvent.getNewValue());
                    OperationBinding opSuppChk = getBindings().getOperationBinding("chkinvcCopyFrmGe");
                    opSuppChk.getParamsMap().put("geNo", valueChangeEvent.getNewValue());
                    opSuppChk.execute();
                    _log.info(" valide resup " + opSuppChk.getResult());
                    if (opSuppChk.getResult() != null &&
                        ((Integer) (opSuppChk.getResult())).compareTo(new Integer(0)) == 0) {
                        showFacesMessage("Supplier document not received .Can not proceed.", "E", false, "F");
                    } else {


                        OperationBinding poplGe = getBindings().getOperationBinding("populateRcptFromGe");
                        poplGe.getParamsMap().put("OrgId", paramOrgId);
                        poplGe.getParamsMap().put("CldId", paramCldId);
                        poplGe.getParamsMap().put("SlocId", paramSlocId);
                        poplGe.getParamsMap().put("Geno", valueChangeEvent.getNewValue().toString());
                        poplGe.execute();
                        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptSrcType);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(srcDocType);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptSrcTable);
                        //   AdfFacesContext.getCurrentInstance().addPartialTarget(docNoPageVar);

                        OperationBinding execItm = getBindings().getOperationBinding("Execute");
                        execItm.execute();
                        _log.info("func ret val--" + poplGe.getResult().toString());
                    }
                }
            }
            /*   OperationBinding poplGe = getBindings().getOperationBinding("populateRcptFromGe");
                   poplGe.getParamsMap().put("OrgId", paramOrgId);
                   poplGe.getParamsMap().put("CldId", paramCldId);
                   poplGe.getParamsMap().put("SlocId", paramSlocId);
                   poplGe.getParamsMap().put("Geno",valueChangeEvent.getNewValue().toString());
                   poplGe.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(rcptSrcType);
            OperationBinding execItm = getBindings().getOperationBinding("Execute");
            execItm.execute();
                   _log.info("func ret val--"+poplGe.getResult().toString()); */
        }

    }

    public void addItemAction(ActionEvent actionEvent) {
        // Add event code here... For adding items manually for without PO
    }

    public void editRcptAction(ActionEvent actionEvent) {
        Integer paramUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String paramHoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        //        _log.info("(Integer)srcDocType.getValue()"+(Integer)srcDocType.getValue());


        OperationBinding chkWhAccess = getBindings().getOperationBinding("checkWarehouseAccessOrNotByUser");
        chkWhAccess.execute();

        if (chkWhAccess.getResult() != null) {
            Integer allowToEdit = Integer.parseInt(chkWhAccess.getResult().toString());
            if (allowToEdit.compareTo(new Integer(1)) == 0) {
                String whNm = null;
                OperationBinding chkWhNm = getBindings().getOperationBinding("whNmView");
                chkWhNm.execute();
                if (chkWhNm.getResult() != null) {
                    whNm = chkWhNm.getResult().toString();
                }
                showFacesMessage("You do not have access to ' " + whNm + " '. Access to be given in Security Module.",
                                 "W", false, "F");
                return;
            }
        }

        String ret = checkPendingUsr(paramSlocId, paramCldId, paramOrgId, RCPT_DOC_NO, paramUsrId);
        _log.info("ret:" + ret);

        if ("Y".equalsIgnoreCase(ret)) {
            if (rcptStat.getValue() != null) {
                if (Integer.parseInt(rcptStat.getValue().toString()) == RCPT_STATUS_STOCKUP ||
                    Integer.parseInt(rcptStat.getValue().toString()) == 384) {
                    // showFacesMessage("Stock is updated for this Receipt. Cannot edit this Receipt.", "E", false, "F");
                    showFacesMessage("MSG.753", "E", true, "F");
                } else if (Integer.parseInt(rcptStat.getValue().toString()) == RCPT_STATUS_QCPEND) {
                    //   showFacesMessage("This receipt is sent for QC Process. Cannot edit this Receipt.", "E", false, "F");
                    showFacesMessage("MSG.754", "E", true, "F");
                } /* else if("N".equalsIgnoreCase(authStat.getValue().toString()) && Integer.parseInt(rcptStat.getValue().toString())==RCPT_STATUS_QCDONE){   // disable for costing calculation 21/11/2014 BL
              //  showFacesMessage("This receipt is pending for approval after QC Process. Cannot edit this Receipt.", "E", false, "F");
              showFacesMessage("MSG.755", "E", true, "F");
            } */ else {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.getPageFlowScope().put("ADD_EDIT_MODE", "E");
                    if ((Integer.parseInt(srcDocType.getValue().toString()) == (SOURCE_DOC_TYPE_WOUT_PO))) {
                        showPopup(editConfPopUp, true);
                    }
                }
            }
        }

    }

    public void allocateLotAction(ActionEvent actionEvent) {

        allocateLotNav.setVisited(true);
        selectFacet = "Lot";
    }


    public void allocateBinAction(ActionEvent actionEvent) {
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem) actionEvent.getComponent();
        rcnv.setVisited(true);
        selectFacet = "Bin";
    }

    public void selectSrAction(ActionEvent actionEvent) {
        RichCommandNavigationItem rcnv = (RichCommandNavigationItem) actionEvent.getComponent();
        rcnv.setVisited(true);
        selectFacet = "Serial";
    }


    public void addLotAction(ActionEvent actionEvent) {
        //Check for Lot Quantity is Complete given or not.'
        String chk = "Y";
        OperationBinding chkQty = getBindings().getOperationBinding("ChkLotQuantity");
        chkQty.execute();
        if (chkQty.getErrors().size() == 0 && chkQty.getResult() != null)
            chk = (String) chkQty.getResult();
        if (chk.equals("Y")) {
            //Quantity is already full
            _log.info("Lot for This Item has been added already.");
            showFacesMessage("Lot for This Item has been added already.", "E", false, "V");
        } else {
            //Check if any Quantity received or not, If all quantity has been rejected then no need to add Lot for the item.
            OperationBinding chkR = getBindings().getOperationBinding("chkRcvdQty");
            chkR.execute();
            String avl = "Y";
            if (chkR.getErrors().size() == 0) {
                if (chkR.getResult() != null)
                    avl = (String) chkR.getResult();
            }
            _log.info("Checked Received Qty Avl=" + avl);
            if (avl.equals("Y")) {
                //Now Create Row for Lot
                String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");

                OperationBinding op = getBindings().getOperationBinding("CreateInsert");
                op.execute();

                OperationBinding oplot = getBindings().getOperationBinding("setLotDetails");
                oplot.getParamsMap().put("OrgId", paramOrgId);
                oplot.execute();


                LotMode = "A";

                showPopup(lotAddPopUp, true);

                //added by Nitesh Garg to disable LotNo
                OperationBinding chkforLotAss = getBindings().getOperationBinding("ChkLotIsAssignedOrNot");
                chkforLotAss.execute();
                if (chkforLotAss.getErrors().size() == 0) {
                    chkLotAss = (String) chkforLotAss.getResult();
                }
            } else {
                //Show message for not adding lot due to unavailablity of Received Qty

                showFacesMessage("Can not create Lot for the Item which has not been received.", "E", false, "F");
            }
        }
        // AdfFacesContext.getCurrentInstance().addPartialTarget(lotidBindPage);
    }

    public String editLotAction() {
        showPopup(lotAddPopUp, true);
        return null;
    }

    public void lotPopUpCancelListener(PopupCanceledEvent popupCanceledEvent) {
        if (LotMode.equals("A")) {
            OperationBinding op = getBindings().getOperationBinding("deleteLot");
            op.execute();
            OperationBinding opEx = getBindings().getOperationBinding("Execute2");
            opEx.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBind);
            /*    lotidBindPage.setDisabled(false);//by Nitesh Garg */
            chkLotAss = "N";
        }
    }


    public void addBinAction(ActionEvent actionEvent) {
        OperationBinding op = getBindings().getOperationBinding("CreateInsert1");
        op.execute();
        BinMode = "A";
        OperationBinding opB = getBindings().getOperationBinding("setItmLotIdCurrBin");
        opB.execute();
        showPopup(binAddPopup, true);
    }

    public void editBinAction(ActionEvent actionEvent) {
        BinMode = "E";
        OperationBinding opB = getBindings().getOperationBinding("getCurrBinIdAndValue");
        opB.execute();
        showPopup(binAddPopup, true);
    }

    public void binPopUpCancelListener(PopupCanceledEvent popupCanceledEvent) {
        if (BinMode.equals("A")) {
            OperationBinding op = getBindings().getOperationBinding("deleteBin");
            op.execute();
            OperationBinding opEx = getBindings().getOperationBinding("Execute1");
            opEx.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(binTableBind);
        }
        if (BinMode.equals("E")) {
            OperationBinding opB = getBindings().getOperationBinding("setCurrBinIdAndValue");
            opB.execute();
            if (opB.getResult() != null) {
                Number qty = (Number) opB.getResult();
                _log.info("qty    :::: " + qty);
                if (qty.compareTo(new Number(0)) == 1) {
                    OperationBinding op = getBindings().getOperationBinding("setValBinQtyBs");
                    op.getParamsMap().put("binQty", qty);
                    op.execute();
                }
            }

        }

        BinMode = "E";
    }

    public void lotAddDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            /*  MMReceiptMtlAMImpl am=  (MMReceiptMtlAMImpl)resolvElDC("MMReceiptMtlAMDataControl");
          am.getDBTransaction().postChanges(); */
            AdfFacesContext.getCurrentInstance().addPartialTarget(checkItmLot);
            OperationBinding op = getBindings().getOperationBinding("Execute2");
            op.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(lotTableBind);
        }
        /*     lotidBindPage.setDisabled(false);//by Nitesh Garg */
        chkLotAss = "N";
    }


    /**
     *  Method used to add multiple input text components dynamically at Runtime.
     *  Runtime assignment of action and validators also provided.
     * */
    public void addSerialMultipleAction(ActionEvent actionEvent) {

        System.out.println("--->" + getGroupMutiBind().getChildCount());
        if (getGroupMutiBind().getChildCount() == 0) {

        } else {
            srLotBind.setDisabled(true);
            srBinBind.setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(srLotBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(srBinBind);
        }
        if (srLotBind.getValue() != null && srBinBind.getValue() != null) {
            OperationBinding op = getBindings().getOperationBinding("getBinQtyForSr");
            op.getParamsMap().put("LotId", srLotBind.getValue().toString());
            op.getParamsMap().put("BinId", srBinBind.getValue().toString());
            op.execute();
            Number binqty = (Number) op.getResult();
            //  Number groupcnt=getGroupMutiBind().getChildCount();

            RichInputText ui = new RichInputText();
            uiCount = uiCount + 1;
            ui.setId("crit" + uiCount);
            ui.setLabel("Enter Serial No");
            ui.setContentStyle("width:130px;");
            ui.setAutoSubmit(true);
            MethodBinding mb =
                FacesContext.getCurrentInstance().getApplication().createMethodBinding("#{pageFlowScope.MtlReceiptBean.srNoValidator}", new Class[] {
                                                                                       FacesContext.class,
                                                                                       UIComponent.class, Object.class
            });
            ui.setValidator(mb);
            addComponent(getGroupMutiBind(), ui);

            System.out.println("--->" + getGroupMutiBind().getChildCount());

            //Create Method Expression
            FacesContext fctx = FacesContext.getCurrentInstance();
            Application application = fctx.getApplication();
            ExpressionFactory elFactory = application.getExpressionFactory();
            ELContext elContext = fctx.getELContext();
            MethodExpression methodExp = null;
            methodExp =
                elFactory.createMethodExpression(elContext, "#{pageFlowScope.MtlReceiptBean.deleteSrLinkAction}",
                                                 Object.class, new Class[] { ActionEvent.class });
            MethodExpressionActionListener al = null;
            al = new MethodExpressionActionListener(methodExp);

            RichCommandImageLink uiLnk = new RichCommandImageLink();
            uiLnk.setId("ccil" + uiCount);
            uiLnk.setText("[Delete]");
            uiLnk.setInlineStyle("font-weight:bold;");

            uiLnk.setIcon("#{resource['images:delete_ico_en.png']}");
            uiLnk.addActionListener(al);
            uiLnk.setImmediate(true);
            addComponent(getGroupMutiBind(), uiLnk);
            AdfFacesContext.getCurrentInstance().addPartialTarget(groupMutiBind);
        } else {
            //showFacesMessage("Lot and Bin must be selected", "E", false, "F");
            showFacesMessage("MSG.756", "E", true, "F");
        }
    }

    /**
     * Method to delete the Text item(SR No) added dynamically.
     * Called on click of delete Link
     * */
    public void deleteSrLinkAction(ActionEvent actionevent) {
        System.out.println("In action for delete" + actionevent.getComponent().getId());
        String txtid = actionevent.getComponent().getId().replace("ccil", "crit");

        UIComponent ui = groupMutiBind.findComponent(txtid);

        if (ui != null) {
            _log.info("Deleted");
            this.delComponent(groupMutiBind, ui);
            this.delComponent(groupMutiBind, actionevent.getComponent());
            AdfFacesContext.getCurrentInstance().addPartialTarget(groupMutiBind);
        }
    }

    public void addComponent(UIComponent parentUIComponent, UIComponent childUIComponent) {
        parentUIComponent.getChildren().add(childUIComponent);
        AdfFacesContext.getCurrentInstance().addPartialTarget(parentUIComponent);
    }

    public void delComponent(UIComponent parentUIComponent, UIComponent childUIComponent) {
        parentUIComponent.getChildren().remove(childUIComponent);
        AdfFacesContext.getCurrentInstance().addPartialTarget(parentUIComponent);
    }

    public void addRcptSerialAction(ActionEvent actionEvent) {
        /*     OperationBinding op=getBindings().getOperationBinding("CreateInsert2");
        op.execute();
        OperationBinding opb=getBindings().getOperationBinding("setItmLotBinCurrSr");
        opb.execute();

      showPopup(srAddPopUp, true); */
    }

    public void srAddDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            List<UIComponent> uiList = getGroupMutiBind().getChildren();

            ArrayList al = new ArrayList();
            for (UIComponent ui : uiList) {
                if (ui.getId().startsWith("crit")) {
                    RichInputText uir = (RichInputText) ui;

                    if (uir.getValue() != null) {
                        al.add(uir.getValue().toString());
                    }
                }
            }

            if (al.size() > 0) {
                OperationBinding op = getBindings().getOperationBinding("addSerials");
                op.getParamsMap().put("al", al);
                op.execute();

                OperationBinding exop = getBindings().getOperationBinding("Execute3");
                exop.execute();
            } else if (al.isEmpty()) {
                _log.info("Empty al");
                //  showFacesMessage("Add Serial No.", "E", false, "F");
                showFacesMessage("MSG.758", "E", true, "F");
            }
            _log.info("ArrayList :" + al);
            uiList.clear();
            //   srLotBind.setDisabled(false);
            //    srBinBind.setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(srLotBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(srBinBind);

        }
    }

    public void srPopUpCancelListener(PopupCanceledEvent popupCanceledEvent) {

        if (SrMode.equals("A")) {
            List<UIComponent> uiList = getGroupMutiBind().getChildren();

            /*   for(UIComponent ui:uiList){

            delComponent(getGroupMutiBind(), ui);
        } */
            uiList.clear();
            OperationBinding op = getBindings().getOperationBinding("deleteSr");
            op.execute();

            /*  Iterator itr=uiList.iterator();

        while(itr.hasNext()){
            Object obj=itr.next();
            if(uiList.contains(obj)){
            RichInputText ui=(RichInputText)obj;
            delComponent(getGroupMutiBind(), ui);
            }
        }  */
            //  srLotBind.setDisabled(false);
            //  srBinBind.setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(srLotBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(srBinBind);
        }
    }

    public void binAddDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            /*   MMReceiptMtlAMImpl am=  (MMReceiptMtlAMImpl)resolvElDC("MMReceiptMtlAMDataControl");
            am.getDBTransaction().postChanges(); */
            OperationBinding op = getBindings().getOperationBinding("Execute1");
            op.execute();
        }
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


    public void lotQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number zero = new Number(0);
            Number getQty = (Number) object;
            if (getQty.compareTo(zero) == 1) {
                Number rcptQty = (Number) rcptQtyBind.getValue();

                Number fQty = (Number) finalQty.getValue();
                OperationBinding op = getBindings().getOperationBinding("isLotQtyValid");

                if (Integer.parseInt(rcptSrcType.getValue().toString()) == RCPT_TYPE_OB ||
                    Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_FGR) {
                    op.getParamsMap().put("rcptQty", fQty);
                } else {
                    op.getParamsMap().put("rcptQty", rcptQty);
                }
                op.getParamsMap().put("obj", (Number) object);
                op.execute();
                if (op.getResult().equals("Y")) {
                    // showFacesMessage("Invalid Lot Quantity.", "E", false, "V");
                    showFacesMessage("MSG.759", "E", true, "V");
                }
            } else {
                // showFacesMessage("Quantity must be greater than zero.", "E", false, "V");
                showFacesMessage("MSG.730", "E", true, "V");
            }

        }

    }


    public void srNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Boolean stat = false;
            List<UIComponent> uiList = getGroupMutiBind().getChildren();
            for (UIComponent ui : uiList) {
                if (ui.getId().startsWith("crit")) {
                    if (ui.getId().equalsIgnoreCase(uIComponent.getId())) {

                    } else {
                        RichInputText uir = (RichInputText) ui;
                        if (uir.getValue() != null) {
                            String sr = uir.getValue().toString();
                            if (sr.equalsIgnoreCase(object.toString())) {
                                stat = true;
                            }
                        }
                    }

                }
            }

            if (stat == true) {
                // showFacesMessage("Duplicate Sr present !! ", "E", false, "V");
                showFacesMessage("MSG.760", "E", true, "V");
            }
            System.out.println("UIComp in sr -- Validator" + uIComponent.getId() + "--Value--" + object.toString());
        }
    }

    public void lotNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            OperationBinding op = getBindings().getOperationBinding("isLotNoValid");
            op.getParamsMap().put("lotNo", object.toString());
            op.execute();

            if (op.getResult().equals("Y")) {
                // showFacesMessage("Duplicate Lot No.", "E", false, "V");
                showFacesMessage("MSG.761", "E", true, "V");
            }
        }
    }


    public void binNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String ret = "N";
            OperationBinding op = getBindings().getOperationBinding("isBinValid");
            op.getParamsMap().put("Binid", object.toString());
            op.execute();
            if (op.getResult() != null) {
                ret = op.getResult().toString();
            }
            _log.info("op   vvv  " + op.getResult());
            if ("Y".equalsIgnoreCase(ret)) {
                //  showFacesMessage("Bin Name already allocated.", "E", false, "V");
                showFacesMessage("MSG.762", "E", true, "V");
            }
        }
    }


    public void binQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (lotNoBinBind.getValue() != null || binNoBinBind.getValue() != null) {
            _log.info("--" + lotNoBinBind.getValue() + "==" + binNoBinBind.getValue());
            _log.info("Inside bin validator :::::");
            if (binQtyBsBind.getValue() == null) {
                _log.info("Inside bin validator quantity null:::::");
            }
            if (object != null) {
                Number qty = (Number) object;
                if (qty.compareTo(new Number(0)) == 1) {
                    OperationBinding op = getBindings().getOperationBinding("isBinQtyValid");
                    op.getParamsMap().put("lotNo", lotNoBinBind.getValue().toString());
                    op.getParamsMap().put("binId", binNoBinBind.getValue().toString());
                    op.getParamsMap().put("obj", qty);
                    op.execute();

                    if (op.getResult().equals("Y")) {
                        // showFacesMessage("Invalid Bin Quantity.", "E", false, "V");
                        showFacesMessage("MSG.764", "E", true, "V");
                    }
                } else {
                    // showFacesMessage("Quantity must be greater than zero.", "E", false, "V");
                    showFacesMessage("MSG.730", "E", true, "V");
                }

                OperationBinding opBin = getBindings().getOperationBinding("checkBinBalanceQty");
                opBin.getParamsMap().put("binId", binNoBinBind.getValue().toString());
                opBin.execute();
                if (opBin.getResult() != null) {
                    Number balQty = (Number) opBin.getResult();
                    _log.info(" balance qty " + balQty);
                    if (balQty.compareTo(new Number(-3)) == 0) {
                        _log.info("Capacity not define for this bin " + balQty);
                    } else {
                        if (qty.compareTo(balQty) == 1) {
                            _log.info("Capacity not vaditot define for this bin " + balQty);
                            showFacesMessage("Quantity exceeded for define bin capacity for selected item.", "E", false,
                                             "V");
                        }
                    }
                }


            } else {
                showFacesMessage("Bin Quantity is Rquired", "E", false, "V");
            }
        }
    }


    public void lotQtyVCE(ValueChangeEvent valueChangeEvent) {
        /*  OperationBinding op=getBindings().getOperationBinding("setValLotQtyBs");
        op.getParamsMap().put("lotQty",(Number)valueChangeEvent.getNewValue());
        op.execute(); */
    }

    public void binQtyVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            //checkBinCapacityBalanceQty
            //        OperationBinding opChk = getBindings().getOperationBinding("checkBinCapacityBalanceQty");
            //        opChk.getParamsMap().put("binQty", (Number) valueChangeEvent.getNewValue());
            //        opChk.execute();
            OperationBinding op = getBindings().getOperationBinding("setValBinQtyBs");
            op.getParamsMap().put("binQty", (Number) valueChangeEvent.getNewValue());
            op.execute();
        }
    }

    public void binRejectQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if ((rcptSrcType.getValue() != null &&
             Integer.parseInt(rcptSrcType.getValue().toString()) == RCPT_TYPE_SFLOR) ||
            (srcDocType.getValue() != null &&
             Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_IMPORT_PO)) {
            if (lotNoBinBind.getValue() != null || binNoBinBind.getValue() != null) {
                _log.info("--" + lotNoBinBind.getValue() + "==" + binNoBinBind.getValue());
                _log.info("Inside bin validator :::::");
                if (binQtyRejBsBind.getValue() == null) {
                    _log.info("Inside bin validator quantity null:::::");
                }
                if (object != null) {
                    Number qty = (Number) object;
                    if (qty.compareTo(new Number(0)) >= 0) {
                        OperationBinding op = getBindings().getOperationBinding("isBinQtyRejRwkValid");
                        op.getParamsMap().put("lotNo", lotNoBinBind.getValue().toString());
                        op.getParamsMap().put("binId", binNoBinBind.getValue().toString());
                        op.getParamsMap().put("type", "R");
                        op.getParamsMap().put("obj", qty);
                        op.execute();

                        if (op.getResult().equals("Y")) {
                            // showFacesMessage("Invalid Bin Quantity.", "E", false, "V");
                            showFacesMessage("MSG.764", "E", true, "V");
                        }
                    } else {
                        // showFacesMessage("Quantity must be greater than zero.", "E", false, "V");
                        showFacesMessage("Quantity must be greater than or equals zero.", "E", true, "V");
                    }
                } else {
                    showFacesMessage("Bin Quantity is Rquired", "E", false, "V");
                }
            }
        }
    }

    public void binRejectQtyVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            OperationBinding op = getBindings().getOperationBinding("setValBinQtyRejRwkBs");
            op.getParamsMap().put("binQty", (Number) valueChangeEvent.getNewValue());
            op.getParamsMap().put("type", "R");
            op.execute();
        }
    }

    public void binRwrkQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (rcptSrcType.getValue() != null && Integer.parseInt(rcptSrcType.getValue().toString()) == RCPT_TYPE_SFLOR) {
            if (lotNoBinBind.getValue() != null || binNoBinBind.getValue() != null) {
                _log.info("--" + lotNoBinBind.getValue() + "==" + binNoBinBind.getValue());
                _log.info("Inside bin validator :::::");
                if (binQtyBsBind.getValue() == null) {
                    _log.info("Inside bin validator quantity null:::::");
                }
                if (object != null) {
                    Number qty = (Number) object;
                    if (qty.compareTo(new Number(0)) >= 0) {
                        OperationBinding op = getBindings().getOperationBinding("isBinQtyRejRwkValid");
                        op.getParamsMap().put("lotNo", lotNoBinBind.getValue().toString());
                        op.getParamsMap().put("binId", binNoBinBind.getValue().toString());
                        op.getParamsMap().put("obj", qty);
                        op.getParamsMap().put("type", "W");
                        op.execute();

                        if (op.getResult().equals("Y")) {
                            // showFacesMessage("Invalid Bin Quantity.", "E", false, "V");
                            showFacesMessage("MSG.764", "E", true, "V");
                        }
                    } else {
                        // showFacesMessage("Quantity must be greater than zero.", "E", false, "V");
                        showFacesMessage("Quantity must be greater than or equals zero.", "E", true, "V");
                    }
                } else {
                    showFacesMessage("Bin Quantity is Rquired", "E", false, "V");
                }
            }
        }
    }

    public void binRwrkQtyVCL(ValueChangeEvent valueChangeEvent) {
        OperationBinding op = getBindings().getOperationBinding("setValBinQtyRejRwkBs");
        op.getParamsMap().put("binQty", (Number) valueChangeEvent.getNewValue());
        op.getParamsMap().put("type", "W");
        op.execute();
    }


    public void addSerialNoForNonSrItm(ActionEvent actionEvent) {
        // Add event code here...
        if (srNoForNonSrItm.getValue() != null) {
            String str = srNoForNonSrItm.getValue().toString();

            String trimStr = str.trim();
            if (trimStr.length() > 0) {
                OperationBinding op = getBindings().getOperationBinding("addManualSerialForNoneSrItm");
                op.getParamsMap().put("SrNo", srNoForNonSrItm.getValue().toString());
                op.execute();
                srNoBind.setValue("");

            } else {
                //showFacesMessage("Invalid Serial No.", "E", false, "F");
                showFacesMessage("MSG.771", "E", true, "F");
            }
        } else {
            // showFacesMessage("Enter Serial Number", "E", false, "F");
            showFacesMessage("MSG.772", "E", true, "F");
        }
    }

    public void addSerialManualAction(ActionEvent actionEvent) {
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));

        if (srNoBind.getValue() != null) {
            String str = srNoBind.getValue().toString();

            String trimStr = str.trim();
            if (trimStr.length() > 0) {
                OperationBinding opchkDup = getBindings().getOperationBinding("isSerialNoValid");
                opchkDup.getParamsMap().put("SrNo", str);
                opchkDup.getParamsMap().put("OrgId", paramOrgId);
                opchkDup.getParamsMap().put("slocId", paramSlocId);
                opchkDup.getParamsMap().put("CldId", paramCldId);
                opchkDup.execute();

                if (opchkDup.getResult().toString().equals("Y")) {
                    OperationBinding op = getBindings().getOperationBinding("addManualSerial");
                    op.getParamsMap().put("SrNo", srNoBind.getValue().toString());
                    op.execute();
                    srNoBind.setValue("");
                } else if (opchkDup.getResult().toString().equals("N")) {
                    // showFacesMessage("Serial No. [ "+srNoBind.getValue().toString()+" ] already exists.", "E", false, "F");
                    String a1 = resolvEl("#{bundle['LBL.909']}");
                    String b1 = resolvEl("#{bundle['MSG.765']}");
                    String c1 = srNoBind.getValue().toString();
                    showFacesMessage(a1 + " " + c1 + " " + b1, "E", false, "F");
                } else if (opchkDup.getResult().toString().equals("I")) {
                    // showFacesMessage("All Serial no.'s are assigned for this item.", "E", false, "F");
                    showFacesMessage("MSG.769", "E", true, "F");
                } else if (opchkDup.getResult().toString().equals("O")) {
                    // showFacesMessage("Serial no.'s cannot be assigned for this item as Receipt quantity is zero.", "E", false, "F");
                    showFacesMessage("MSG.770", "E", true, "F");
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
            } else {
                //showFacesMessage("Invalid Serial No.", "E", false, "F");
                showFacesMessage("MSG.771", "E", true, "F");
            }
        } else {
            // showFacesMessage("Enter Serial Number", "E", false, "F");
            showFacesMessage("MSG.772", "E", true, "F");
        }
    }

    /**
     *  Method to update the DocId of src and Itm tables if PO has been created for the received items
     *  1.Validate if the PO No. selected contains all the items in the receipt(Without PO) and their tolerance levels
     *  2.If passes the validation, update the PO No.
     * */
    public void updateDocNoAction(ActionEvent actionEvent) {
        /*    OperationBinding op=getBindings().getOperationBinding("updateSrcDocNo");
        op.getParamsMap().put("srNo",srNoBind.getValue().toString());
        op.execute(); */
        Integer paramUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String paramHoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String paramGechk = resolvEl("#{pageFlowScope.GLBL_ORG_GE_CHK}");

        if (docNoPageVar.getValue() != null) {
            Integer schdlNo = 1;
            if (schdlNoBind.getValue() != null) {
                schdlNo = Integer.parseInt(schdlNoBind.getValue().toString());
            }

            OperationBinding op = getBindings().getOperationBinding("validateWoutPoRcptDoc");
            op.getParamsMap().put("CldId", paramCldId);
            op.getParamsMap().put("SlocId", paramSlocId);
            op.getParamsMap().put("OrgId", paramOrgId);
            op.getParamsMap().put("poDocId", docNoPageVar.getValue().toString());
            op.getParamsMap().put("p_dlv_sch_no", schdlNo);
            if (paramGechk.equals("Y")) {
                op.getParamsMap().put("p_rcpt_flg", "G");
            } else {
                op.getParamsMap().put("p_rcpt_flg", "S");
            }

            op.execute();
            String ret = op.getResult().toString();
            if (ret.equals("1")) {
                OperationBinding opSrc = getBindings().getOperationBinding("updateSrcDocNo");
                opSrc.getParamsMap().put("DocId", docNoPageVar.getValue().toString());
                if (schdlNoBind.getValue() == null) {
                    opSrc.getParamsMap().put("dlvNo", new Integer(1));
                } else {
                    opSrc.getParamsMap().put("dlvNo", schdlNo);
                }
                opSrc.execute();
                if ("Y".equals(opSrc.getResult().toString())) {
                    // disable=true;
                    //showFacesMessage("PO No. succesfully updated.","I", false, "F");
                    showFacesMessage("MSG.773", "I", true, "F");
                    OperationBinding opbind = getBindings().getOperationBinding("setTransValueToNull");
                    opbind.execute();
                    docNoPageVar.setValue("");
                    docDate.setValue("");
                    docNoPageVar.setValue(null);
                    docDate.setValue(null);
                    disItmForm = "N";
                    // schdlNoBind.setValue("");schdlNoBind.setValue(null);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(schdlNoBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(docNoPageVar);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(docDate);


                }
            } else if (ret.equals("-4")) {
                // showFacesMessage("Items mismatch between receipt and delivery schedule","E", false, "F");
                showFacesMessage("MSG.774", "E", true, "F");
            } else if (ret.equals("-7")) {
                // showFacesMessage("Cannot accept before or after maximum tolerance days","E",false,"F");
                showFacesMessage("MSG.776", "E", true, "F");
            } else if (ret.equals("-9")) {
                // showFacesMessage("Cannot accept quantity more than tolerance quantity","E",false,"F");
                showFacesMessage("MSG.777", "E", true, "F");
            } else if (ret.equals("-1")) {
                // showFacesMessage("Invalid PO Number.Updation Failed.","E",false,"F");
                showFacesMessage("MSG.779", "E", true, "F");
            }
        } else {
            // showFacesMessage("Please select the Purchase Order No.", "E", false, "F");
            showFacesMessage("MSG.780", "E", true, "F");
        }

    }

    public void editConfirmDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
            showAddItm = "N";
            disItmForm = "Y";
            AdfFacesContext.getCurrentInstance().addPartialTarget(docNoPageVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(schdlNoBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(updtPONObuttonBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(groupAddDoc);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmForm);
        } else {
            showAddItm = "Y";
            disItmForm = "N";
            AdfFacesContext.getCurrentInstance().addPartialTarget(docNoPageVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(schdlNoBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(updtPONObuttonBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(groupAddDoc);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmForm);
        }
    }

    public void docNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...































































































































    }


    public void rcptQtyTolrValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Integer paramUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String paramHoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        if (object != null) {

            Number zero = new Number(0);

            if (((Number) object).compareTo(zero) == -1) {
                //showFacesMessage("Received Quantity cannot be less than zero.", "E", false, "V");
                showFacesMessage("MSG.669", "E", true, "V");
            } else {

                Boolean flag = isPrecisionValid(26, 6, ((Number) object));
                if (flag.equals(false))
                    showFacesMessage("MSG.57", "E", true, "V"); //"Invalid pricision (26,6)."
                else if (Integer.parseInt(rcptSrcType.getValue().toString()) == RCPT_TYPE_OB) {

                } else if (Integer.parseInt(srcDocType.getValue().toString()) == (SOURCE_DOC_TYPE_PO)) {

                    String paramGeChk = resolvEl("#{pageFlowScope.GLBL_ORG_GE_CHK}");
                    if (paramGeChk.equalsIgnoreCase("Y")) {
                        OperationBinding opge = getBindings().getOperationBinding("validateRcptQtyForGeRcvQty");
                        opge.getParamsMap().put("qty", object);
                        opge.execute();
                        if (opge.getResult() != null) {
                            if (opge.getResult().toString().equals("N"))
                                showFacesMessage("Sum of Rejected,Rework,Receipt quantity can't be greater than Quantity Received at Gate Entry.",
                                                 "E", false, "V");

                        }
                    } else {
                        OperationBinding opc = getBindings().getOperationBinding("chkToleranceQty");
                        opc.getParamsMap().put("CldId", paramCldId);
                        opc.getParamsMap().put("SlocId", paramSlocId);
                        opc.getParamsMap().put("orgId", paramOrgId);
                        opc.execute();
                        _log.info("Check Tolerance is=" + opc.getResult());
                        if (opc.getResult().toString().equals("Y")) {
                            OperationBinding op = getBindings().getOperationBinding("validateToleranceQty");
                            op.getParamsMap().put("CldId", paramCldId);
                            op.getParamsMap().put("SlocId", paramSlocId);
                            op.getParamsMap().put("OrgId", paramOrgId);
                            op.getParamsMap().put("p_itm_id", null);
                            op.getParamsMap().put("rcpt_qty", (Number) object);
                            op.execute();

                            String res = op.getResult().toString();
                            _log.info("Res in tolr=" + res);
                            if (res.equals("-2")) {
                                // showFacesMessage("Cannot accept more than tolerance quantity", "E", false, "V");
                                showFacesMessage("MSG.781", "E", true, "V");
                            }
                        } else if (opc.getResult().toString().equals("N")) {
                            OperationBinding opchkPoQty = getBindings().getOperationBinding("validatePoRcptQty");
                            opchkPoQty.getParamsMap().put("rcptQty", object);
                            opchkPoQty.execute();
                            if (opchkPoQty.getResult() != null && opchkPoQty.getResult().toString().equals("Y")) {
                                _log.info("Quantity valid for PO");
                            } else {
                                showFacesMessage("Cannot accept more than Pending quantity", "E", false, "V");
                            }
                        }
                    }
                } else if (Integer.parseInt(srcDocType.getValue().toString()) == (SOURCE_DOC_TYPE_CPO)) {
                    /* OperationBinding opchkPoQty=getBindings().getOperationBinding("validatePoRcptQty");
                                  opchkPoQty.getParamsMap().put("rcptQty", object);
                              opchkPoQty.execute();
                              if(opchkPoQty.getResult()!=null && opchkPoQty.getResult().toString().equals("Y"))
                              {
                                  _log.info("Quantity valid for PO");
                                  }
                              else
                              {
                              showFacesMessage("Cannot accept more than Pending quantity", "E", false, "V");
                                  } */
                    if (srcDocType.getValue().equals(SOURCE_DOC_TYPE_CPO)) {
                        OperationBinding op = getBindings().getOperationBinding("validateRcptQty");
                        op.getParamsMap().put("qty", object);
                        op.execute();
                        if (op.getResult() != null) {
                            if (op.getResult().toString().equals("N"))
                                showFacesMessage("Sum of Rejected,Rework,Receipt quantity can't be greater than Pending Quantity.",
                                                 "E", false, "V");
                        }
                    }
                }
            }
        }
    }

    public void rcptQtyVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            OperationBinding opch = getBindings().getOperationBinding("setRcptQtyBase");
            opch.getParamsMap().put("rcptQty", (Number) valueChangeEvent.getNewValue());
            opch.execute();

            if (Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_PO ||
                Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_IMPORT_PO) {
                if (valueChangeEvent.getNewValue() != null) {
                    String paramGeChk = resolvEl("#{pageFlowScope.GLBL_ORG_GE_CHK}");
                    if (paramGeChk.equalsIgnoreCase("N")) {
                        OperationBinding op = getBindings().getOperationBinding("updateTmpRcptQtyForRcpt");
                        op.getParamsMap().put("oldRcptQty", valueChangeEvent.getOldValue());
                        op.getParamsMap().put("newRcptQty", valueChangeEvent.getNewValue());
                        op.execute();
                    } else {
                        OperationBinding oprcv = getBindings().getOperationBinding("updateRejQtyFromRcv");
                        oprcv.getParamsMap().put("oldRcvQty", valueChangeEvent.getOldValue());
                        oprcv.getParamsMap().put("newRcvQty", valueChangeEvent.getNewValue());
                        oprcv.execute();
                    }
                }

            }
        }
    }

    public void RejQtyVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            OperationBinding opc = getBindings().getOperationBinding("setRejQtyBase");
            opc.getParamsMap().put("rejQty", (Number) valueChangeEvent.getNewValue());
            opc.execute();

            if (valueChangeEvent.getNewValue() != null) {
                String paramGeChk = resolvEl("#{pageFlowScope.GLBL_ORG_GE_CHK}");
                if (paramGeChk.equalsIgnoreCase("N")) {
                    if (Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_PO) {
                        OperationBinding op = getBindings().getOperationBinding("updateTmpRcptQtyFromRej");
                        op.getParamsMap().put("oldRejQty", valueChangeEvent.getOldValue());
                        op.getParamsMap().put("newRejQty", valueChangeEvent.getNewValue());
                        op.execute();
                    } else if (Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_IMPORT_PO) {
                        OperationBinding oprcv = getBindings().getOperationBinding("updateRcvQtyFromRej");
                        oprcv.getParamsMap().put("oldRejQty", valueChangeEvent.getOldValue());
                        oprcv.getParamsMap().put("newRejQty", valueChangeEvent.getNewValue());
                        oprcv.execute();
                    }
                    else if (Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_JITR) {
                         OperationBinding oprcv = getBindings().getOperationBinding("updateRcvQtyFromRej");
                         oprcv.getParamsMap().put("oldRejQty", valueChangeEvent.getOldValue());
                         oprcv.getParamsMap().put("newRejQty", valueChangeEvent.getNewValue());
                         oprcv.execute();
                    }
                } else {
                    OperationBinding oprcv = getBindings().getOperationBinding("updateRcvQtyFromRej");
                    oprcv.getParamsMap().put("oldRejQty", valueChangeEvent.getOldValue());
                    oprcv.getParamsMap().put("newRejQty", valueChangeEvent.getNewValue());
                    oprcv.execute();
                }
            }

        }
    }

    public void rwkQtyVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0) {
            OperationBinding opc = getBindings().getOperationBinding("setRwkQtyBase");
            opc.getParamsMap().put("rwkQty", (Number) valueChangeEvent.getNewValue());
            opc.execute();

            if (valueChangeEvent.getNewValue() != null) {
                String paramGeChk = resolvEl("#{pageFlowScope.GLBL_ORG_GE_CHK}");
                if (paramGeChk.equalsIgnoreCase("N")) {
                    if (Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_PO) {
                        OperationBinding op = getBindings().getOperationBinding("updateTmpRcptQtyForRwk");
                        op.getParamsMap().put("oldRwkQty", valueChangeEvent.getOldValue());
                        op.getParamsMap().put("newRwkQty", valueChangeEvent.getNewValue());
                        op.execute();
                    } else if (Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_IMPORT_PO) {
                        OperationBinding oprcv = getBindings().getOperationBinding("updateRcvQtyFromRwk");
                        oprcv.getParamsMap().put("oldRwkQty", valueChangeEvent.getOldValue());
                        oprcv.getParamsMap().put("newRwkQty", valueChangeEvent.getNewValue());
                        oprcv.execute();
                    }else if (Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_JITR) {
                        OperationBinding oprcv = getBindings().getOperationBinding("updateRcvQtyFromRwk");
                        oprcv.getParamsMap().put("oldRwkQty", valueChangeEvent.getOldValue());
                        oprcv.getParamsMap().put("newRwkQty", valueChangeEvent.getNewValue());
                        oprcv.execute();
                    }

                } else {
                    OperationBinding oprcv = getBindings().getOperationBinding("updateRcvQtyFromRwk");
                    oprcv.getParamsMap().put("oldRwkQty", valueChangeEvent.getOldValue());
                    oprcv.getParamsMap().put("newRwkQty", valueChangeEvent.getNewValue());
                    oprcv.execute();
                }

            }
        }
    }

    public void rcptSrcTypeVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().trim().length() > 0) {
            Integer vce = Integer.parseInt(valueChangeEvent.getNewValue().toString());
            //   if(vce==337 || vce==338){ //wh within org
            OperationBinding opc = getBindings().getOperationBinding("resetOrgWh");
            opc.getParamsMap().put("rcptType", vce);
            opc.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(whIdsrcPage);
            /*       }else{

            } */

            if (vce == 334 || vce == 332) {
                geNoListBinding.setRequired(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(geNoListBinding);
            }
            /*   if(vce==337)
                {
                        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
                        orgIdSrcBinding.setValue(paramOrgId);
                        OperationBinding op=getBindings().getOperationBinding("SetOrgSrc");
                        op.getParamsMap().put("org", paramOrgId);
                        op.execute();
                    } */
        }
        OperationBinding opbind = getBindings().getOperationBinding("setTransValueToNull");
        opbind.execute();
        eoIdSrcBinding.setValue("");
        docNoPageVar.setValue("");
        docDate.setValue("");
        itmNameBindVar.setValue("");
        uomBindVar.setValue("");
        uomBindVar.resetValue();
        itmQty.setValue("");
        //  schdlNoBind.setValue("");schdlNoBind.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(schdlNoBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmNameBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(uomBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmQty);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docNoPageVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docDate);
        AdfFacesContext.getCurrentInstance().addPartialTarget(eoIdSrcBinding);
    }


    public void trSerialNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));

        OperationBinding opchkDup = getBindings().getOperationBinding("isSerialNoValidSr");
        opchkDup.getParamsMap().put("SrNo", object.toString());
        opchkDup.getParamsMap().put("OrgId", paramOrgId);
        opchkDup.getParamsMap().put("slocId", paramSlocId);
        opchkDup.getParamsMap().put("CldId", paramCldId);
        opchkDup.execute();

        if (opchkDup.getResult().toString().equals("N")) {
            //showFacesMessage("Serial No. [ "+object.toString()+" ] already exists.", "E", false, "V");
            String a1 = "LBL.909";
            String b1 = "MSG.765";
            String c1 = object.toString();
            showFacesMessage("a1 " + c1 + " b1", "E", true, "V");
        }
    }

    public void trSerialNoVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            OperationBinding opc = getBindings().getOperationBinding("setTrSerialNo");
            opc.getParamsMap().put("serial", valueChangeEvent.getNewValue().toString());
            opc.execute();
        }
    }


    public void finalRcptQtyVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            //Check if FGR
            Integer scDocType = Integer.parseInt(srcDocType.getValue().toString());
            Integer rcptSrc = Integer.parseInt(rcptSrcType.getValue().toString());
            if (scDocType.equals(SOURCE_DOC_TYPE_FGR) || rcptSrc.equals(RCPT_TYPE_OB)) {
                //Check if Lot assign
                OperationBinding opchk = getBindings().getOperationBinding("chkLotAssignOrNot");
                opchk.execute();
                if (opchk.getResult() != null)
                    if (opchk.getResult().toString().equals("Y")) {
                        callLotBinDltPop = "Y";
                        finalRcptQty = (Number) valueChangeEvent.getNewValue();
                        oldFinalRcptQty = (Number) valueChangeEvent.getOldValue();
                        showPopup(qtyChangePopup, true);
                    } else {
                        OperationBinding opc = getBindings().getOperationBinding("setFinalRcptQtyBase");
                        opc.getParamsMap().put("frcptQty", (Number) valueChangeEvent.getNewValue());
                        opc.execute();
                    }
            }


        }
    }


    public void delNoteQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number noteqty = (Number) object;
            OperationBinding chkQty = getBindings().getOperationBinding("checkDlvNoteQty");
            chkQty.getParamsMap().put("noteQty", noteqty);
            chkQty.execute();

            String ret = chkQty.getResult().toString();
            if (ret.equalsIgnoreCase("Y")) {
                //  throw new ValidatorException(new FacesMessage("Delivery Note quantity cannot be greater than Pending Quantity."));
                throw new ValidatorException(new FacesMessage("MSG.782"));
            }
        }
    }


    public void deleteConfDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {

            String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            Integer paramUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
            String paramHoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));

            OperationBinding delRwOp = getBindings().getOperationBinding("deleteItemsNonRecvd");
            delRwOp.getParamsMap().put("CldId", paramCldId);
            delRwOp.getParamsMap().put("slocId", paramSlocId);
            delRwOp.getParamsMap().put("OrgId", paramOrgId);
            delRwOp.getParamsMap().put("whId", getWhIdPage().getValue().toString());
            delRwOp.execute();

            AdfFacesContext.getCurrentInstance().addPartialTarget(whIdPage);
            AdfFacesContext.getCurrentInstance().addPartialTarget(docIdSrcDispBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tab1binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(docIdSrcColumnVar);

        } else if (dialogEvent.getOutcome().name().equals("No")) {
            _log.info("--NO--");
        }
    }

    public String addReceiptAction() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.getPageFlowScope().put("ADD_EDIT_MODE", "A");
        tab1binding.setDisclosed(true);
        tab2Binding.setDisclosed(false);
        return "addReceipt";
    }

    public String checkPendingUsr(Integer sloc_id, String cld_id, String pOrgId, Integer RcptDocNo, Integer usr_id) {
        Integer pending = 0;
        String retVal = "Y";
        OperationBinding chkUsr = getBindings().getOperationBinding("pendingUsrCheck");
        chkUsr.getParamsMap().put("SlocId", sloc_id);
        chkUsr.getParamsMap().put("CldId", cld_id);
        chkUsr.getParamsMap().put("OrgId", pOrgId);
        chkUsr.getParamsMap().put("RcptDocNo", RcptDocNo);
        chkUsr.execute();

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
        }

        _log.info("PENDING_USR:" + pending + "---CURRENT_USR:" + usr_id);

        OperationBinding getcrUsr = getBindings().getOperationBinding("getUserIdCreated");
        getcrUsr.execute();

        Integer rcptUsrId = (Integer) getcrUsr.getResult(); //User who created his receipt

        if ("N".equals(authStat.getValue().toString())) {
            if (pending.compareTo(usr_id) != 0 && pending.compareTo(new Integer(-1)) != 0) {

                OperationBinding getUsr = getBindings().getOperationBinding("getUserName");
                getUsr.getParamsMap().put("usrId", pending);
                getUsr.execute();
                String ms1 = resolvEl("#{bundle['MSG.783']}");
                String ms2 = resolvEl("#{bundle['MSG.784']}");
                //  String msg2 = "This Receipt is pending at other user "+getUsr.getResult().toString()+" for approval, You cannot Forward/Approve this.";
                String msg2 = ms1 + getUsr.getResult().toString() + ms2;
                _log.info("msg2" + msg2);
                // showFacesMessage(msg2, "E", false, "F");
                showFacesMessage(msg2, "E", false, "F");
                retVal = "N";
            }
        } else {

            if (rcptUsrId.compareTo(usr_id) == 0 || pending.compareTo(usr_id) == 0) {
                retVal = "Y";
            } else {
                OperationBinding getUsr = getBindings().getOperationBinding("getUserName");
                getUsr.getParamsMap().put("usrId", rcptUsrId);
                getUsr.execute();
                String ms1 = resolvEl("#{bundle['MSG.785']}");
                String ms2 = resolvEl("#{bundle['MSG.786']}");
                //String msg2 = "This Receipt can be edited by user "+getUsr.getResult().toString()+" for Stock Updation only.";
                //String msg2 = "MSG.785 "+getUsr.getResult().toString()+" MSG.786";
                String msg2 = ms1 + getUsr.getResult().toString() + ms2;
                _log.info("msg2" + msg2);
                // showFacesMessage(msg2, "E", false, "F");
                showFacesMessage(msg2, "E", false, "F");
                retVal = "N";
            }
        }
        return retVal;

    }


    public String callWfMethods(Integer sloc_id, String cld_id, String org_Id, Integer usr_Id, String callFrom) {

        /**
           *  Insert entry into WF items..
         * */
        _log.info("accessing wf");
        //String wf_id = RCPT_WF_ID;
        String action = "I";
        String remark = "A";

        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
        WfnoOp.getParamsMap().put("sloc_id", sloc_id);
        WfnoOp.getParamsMap().put("cld_id", cld_id);
        WfnoOp.getParamsMap().put("org_id", org_Id);
        WfnoOp.getParamsMap().put("doc_no", RCPT_DOC_NO);
        WfnoOp.execute();
        String WfNum = null;


        if (WfnoOp.getResult() != null) {
            WfNum = WfnoOp.getResult().toString();
        }
        _log.info("get Workflow id=" + WfNum);
        _log.info(" ::::::::::::::   " + (!"0".equalsIgnoreCase(WfNum)));
        if (WfNum != null) {
            if ("SF".equalsIgnoreCase(callFrom) && (!"0".equalsIgnoreCase(WfNum)) || "S".equalsIgnoreCase(callFrom)) {
                Integer level = -1;
                OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                usrLevelOp.getParamsMap().put("SlocId", sloc_id);
                usrLevelOp.getParamsMap().put("CldId", cld_id);
                usrLevelOp.getParamsMap().put("OrgId", org_Id);
                usrLevelOp.getParamsMap().put("usr_id", usr_Id);
                usrLevelOp.getParamsMap().put("WfNum", WfNum);
                usrLevelOp.getParamsMap().put("rcptDocId", RCPT_DOC_NO);
                usrLevelOp.execute();

                if (usrLevelOp.getResult() != null) {
                    level = Integer.parseInt(usrLevelOp.getResult().toString());
                }
                _log.info("GET Level of user=" + level);
                if (level > -1) {
                    // String WfNum=getWf_no(sloc_id,cld_id,OrgId,PO_DOC_NO);
                    /* Integer level =Integer.parseInt(callStoredFunction(NUMBER, "APP.PKG_APP_WF.GET_USR_LEVEL(?,?,?,?,?,?)", new Object[] { sloc_id,
                                                                                                                   cld_id,
                                                                                                                   OrgId,
                                                                                                                   usr_id,
                                                                                                                   WfNum,
                                                                                                                   PO_DOC_NO }).toString());


        */System.out.println("inserting into txn" + sloc_id + "-SAS---" + cld_id + "---" + org_Id + "--WFNUM--" +
                             WfNum + "----" + "------" + "-----" + usr_Id + "--" + usr_Id + "---" + level + "---" +
                             action + "---" + remark + "---");

                    Integer res = -1;


                    OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                    insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                    insTxnOp.getParamsMap().put("cld_id", cld_id);
                    insTxnOp.getParamsMap().put("pOrgId", org_Id);
                    insTxnOp.getParamsMap().put("rcpt_doc_no", RCPT_DOC_NO);
                    insTxnOp.getParamsMap().put("WfNum", WfNum);
                    insTxnOp.getParamsMap().put("usr_idFrm", usr_Id);
                    insTxnOp.getParamsMap().put("usr_idTo", usr_Id);
                    insTxnOp.getParamsMap().put("levelFrm", level);
                    insTxnOp.getParamsMap().put("levelTo", level);
                    insTxnOp.getParamsMap().put("action", action);
                    insTxnOp.getParamsMap().put("remark", remark);
                    insTxnOp.getParamsMap().put("amount", new Number(0));
                    insTxnOp.getParamsMap().put("post", "S");
                    insTxnOp.execute();

                    if (insTxnOp.getResult() != null) {
                        res = Integer.parseInt(insTxnOp.getResult().toString());
                    }
                    _log.info("Res after insert into txn=" + res);
                    if (res == 1) {

                        OperationBinding operationBinding = getBindings().getOperationBinding("Commit");
                        operationBinding.execute();
                        return "Y";
                    } else {
                        _log.info("error during insert to WF");
                        return "N";

                    }
                } else {
                    showFacesMessage("Something went wrong(User Level in Workflow).Please Contact to ESS!", "E", false,
                                     "F");
                    return "N";
                }
            } else {
                showFacesMessage("Workflow not Define for Current Organisation.", "E", false, "F");
                return "N";
            }

        } else {
            return "N";
        }
    }


    public void rejQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number zero = new Number(0);
            Number value = (Number) object;
            if (((Number) object).compareTo(zero) == -1) {
                // showFacesMessage("Rejected Quantity cannot be less than zero.", "E", false, "V");
                showFacesMessage("MSG.787", "E", true, "V");
            }
            Boolean flag = isPrecisionValid(26, 6, value);
            if (flag.equals(false)) {
                showFacesMessage("MSG.57", "E", true, "V"); //"Invalid pricision (26,6)."
            }

            String paramGeChk = resolvEl("#{pageFlowScope.GLBL_ORG_GE_CHK}");
            if (paramGeChk.equalsIgnoreCase("Y")) {
                OperationBinding opge = getBindings().getOperationBinding("validateRejQtyForGeRcvQty");
                opge.getParamsMap().put("qty", object);
                opge.execute();
                if (opge.getResult() != null) {
                    if (opge.getResult().toString().equals("N"))
                        showFacesMessage("Sum of Rejected,Rework,Receipt quantity can't be greater than Quantity Received at Gate Entry.",
                                         "E", false, "V");

                }
            }

            if (srcDocType.getValue().equals(SOURCE_DOC_TYPE_CPO)) {
                OperationBinding op = getBindings().getOperationBinding("validateRejQty");
                op.getParamsMap().put("qty", object);
                op.execute();
                if (op.getResult() != null) {
                    if (op.getResult().toString().equals("N"))
                        showFacesMessage("Sum of Rejected,Rework,Receipt quantity can't be greater than Pending Quantity.",
                                         "E", false, "V");
                }
            }

        }
    }

    public void rwkQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number zero = new Number(0);
            Number value = (Number) object;
            if (((Number) object).compareTo(zero) == -1) {
                //  showFacesMessage("Reworkable Quantity cannot be less than zero.", "E", false, "V");
                showFacesMessage("MSG.788", "E", true, "V");
            }
            Boolean flag = isPrecisionValid(26, 6, value);
            if (flag.equals(false)) {
                showFacesMessage("MSG.57", "E", true, "V"); //"Invalid pricision (26,6)."
            }

            String paramGeChk = resolvEl("#{pageFlowScope.GLBL_ORG_GE_CHK}");
            if (paramGeChk.equalsIgnoreCase("Y")) {
                OperationBinding opge = getBindings().getOperationBinding("validateRwkQtyForGeRcvQty");
                opge.getParamsMap().put("qty", object);
                opge.execute();
                if (opge.getResult() != null) {
                    if (opge.getResult().toString().equals("N"))
                        showFacesMessage("Sum of Rejected,Rework,Receipt quantity can't be greater than Quantity Received at Gate Entry.",
                                         "E", false, "V");

                }
            }


            if (srcDocType.getValue().equals(SOURCE_DOC_TYPE_CPO)) {
                OperationBinding op = getBindings().getOperationBinding("validateRwkQty");
                op.getParamsMap().put("qty", object);
                op.execute();
                if (op.getResult() != null) {
                    if (op.getResult().toString().equals("N"))
                        showFacesMessage("Sum of Rejected,Rework,Receipt quantity can't be greater than Pending Quantity.",
                                         "E", false, "V");
                }
            }
        }
    }

    public void deliveryNoteQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number zero = new Number(0);
            Number value = (Number) object;
            if (((Number) object).compareTo(zero) == -1) {
                // showFacesMessage("Delivery Note Quantity cannot be less than zero.", "E", false, "V");
                showFacesMessage("MSG.789", "E", true, "V");
            }
            Boolean flag = isPrecisionValid(26, 6, value);
            if (flag.equals(false)) {
                showFacesMessage("MSG.57", "E", true, "V"); //"Invalid pricision (26,6)."
            }
        }
    }

    public void finalRcptQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number zero = new Number(0);
            Number value = (Number) object;
            if (((Number) object).compareTo(zero) <= 0) {
                showFacesMessage("Final Receipt Quantity should be greater than zero.", "E", false, "V");
                // showFacesMessage("MSG.790", "E", true, "V");
            }
            Boolean flag = isPrecisionValid(26, 6, value);
            if (flag.equals(false)) {
                showFacesMessage("MSG.57", "E", true, "V"); //"Invalid pricision (26,6)."
            }
        } else
            showFacesMessage("Quantity Required.", "E", false, "V");
    }

    public void invcDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");

            Timestamp ts = (Timestamp) object;
            OperationBinding tsOp = getBindings().getOperationBinding("isFYOpenForCurrentDate");
            tsOp.getParamsMap().put("cldId", paramCldId);
            tsOp.getParamsMap().put("orgId", paramOrgId);
            tsOp.getParamsMap().put("dt", ts);
            tsOp.execute();

            if ("false".equalsIgnoreCase(tsOp.getResult().toString())) {
                //  showFacesMessage("Invalid Invoice Date. Invoice Date must be present in an open financial year.", "E", false, "V");
                showFacesMessage("MSG.671", "E", true, "V");
            }

            if (rcptDate.getValue() != null) {
                _log.info(ts + "      " + rcptDate.getValue());
                if (ts.toString().compareTo(rcptDate.getValue().toString()) == 1) {
                    showFacesMessage("Date must be before or on " + rcptDate.getValue(), "E", false, "V");
                }
            }

        }
    }

    public void deliveryDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");

            Timestamp ts = (Timestamp) object;
            OperationBinding tsOp = getBindings().getOperationBinding("isFYOpenForCurrentDate");
            tsOp.getParamsMap().put("cldId", paramCldId);
            tsOp.getParamsMap().put("orgId", paramOrgId);
            tsOp.getParamsMap().put("dt", ts);
            tsOp.execute();

            if ("false".equalsIgnoreCase(tsOp.getResult().toString())) {
                // showFacesMessage("Invalid Delivery Date. Delivery Date must be present in an open financial year.", "E", false, "V");
                showFacesMessage("MSG.672", "E", true, "V");
            }

            if (rcptDate.getValue() != null) {
                _log.info(ts + "      " + rcptDate.getValue());
                if (ts.toString().compareTo(rcptDate.getValue().toString()) == 1) {
                    showFacesMessage("Date must be before or on " + rcptDate.getValue(), "E", false, "V");
                }
            }

        }
    }

    public void billDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");

            Timestamp ts = (Timestamp) object;

            OperationBinding tsOp = getBindings().getOperationBinding("isFYOpenForCurrentDate");
            tsOp.getParamsMap().put("cldId", paramCldId);
            tsOp.getParamsMap().put("orgId", paramOrgId);
            tsOp.getParamsMap().put("dt", ts);
            tsOp.execute();

            if ("false".equalsIgnoreCase(tsOp.getResult().toString())) {
                // showFacesMessage("Invalid Transporter Bill Date. Transporter Bill Date must be present in an open financial year.", "E", false, "V");
                showFacesMessage("MSG.673", "E", true, "V");
            }

            if (rcptDate.getValue() != null) {
                _log.info(ts + "      " + rcptDate.getValue());
                if (ts.toString().compareTo(rcptDate.getValue().toString()) == 1) {
                    showFacesMessage("Date must be before or on " + rcptDate.getValue(), "E", false, "V");
                }
            }

        }
    }

    public void lotPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number zero = new Number(0);
            Number value = (Number) object;
            if (((Number) object).compareTo(zero) == -1) {
                // showFacesMessage("Lot Price cannot be less than zero.", "E", false, "V");
                showFacesMessage("MSG.791", "E", true, "V");
            }
            Boolean flag = isPrecisionValid(26, 6, value);
            if (flag.equals(false)) {
                showFacesMessage("MSG.57", "E", true, "V"); //"Invalid pricision (26,6)."
            }
        }
    }

    public void deleteSrNoAction(ActionEvent actionEvent) {
        OperationBinding delOp = getBindings().getOperationBinding("Delete");
        delOp.execute();
        OperationBinding exeOp = getBindings().getOperationBinding("Execute4");
        exeOp.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(srNoTableBind);
    }


    public void addSerialNoAction(ActionEvent actionEvent) {
        if (Integer.parseInt(rcptSrcType.getValue().toString()) != RCPT_TYPE_OB &&
            Integer.parseInt(srcDocType.getValue().toString()) != SOURCE_DOC_TYPE_FGR) {
            _log.info("itmSerialNo.getValue()" + itmSerialNo.getValue());
            if (itmSerialNo.getValue() != null && itmSerialNo.getValue() != "" &&
                !itmSerialNo.getValue().toString().equalsIgnoreCase("0")) {
                //Code for check Valid Quantity for SR
                String valid = "N";
                String paramBinChk = resolvEl("#{pageFlowScope.GLBL_ORG_BIN_CHK}");
                //code for check if Quantity does not exceeds Bin or Lot Quantity
                OperationBinding chkLBQty = getBindings().getOperationBinding("chkLotOrBinQuantity");
                chkLBQty.getParamsMap().put("Binchk", paramBinChk);
                chkLBQty.execute();
                if (chkLBQty.getErrors().size() == 0 && chkLBQty.getResult() != null) {
                    valid = (String) chkLBQty.getResult();
                }

                if (valid.equals("Y")) {
                    //code for check duplicate implement by Nitesh Garg
                    OperationBinding chkSrDup = getBindings().getOperationBinding("chkSerialDuplicate");
                    chkSrDup.getParamsMap().put("SrNo", itmSerialNo.getValue().toString());
                    chkSrDup.execute();
                    String dupli = "N";
                    if (chkSrDup.getErrors().size() == 0 && chkSrDup.getResult() != null)
                        dupli = (String) chkSrDup.getResult();
                    if (dupli.equals("N")) {
                        OperationBinding op = getBindings().getOperationBinding("CreateInsert2");
                        op.execute();
                        OperationBinding opb = getBindings().getOperationBinding("setItmLotBinCurrSr");
                        opb.execute();
                        itmSerialNo.resetValue();
                        AdfFacesContext.getCurrentInstance().addPartialTarget(itmSerialNo);
                    } else {
                        //  showFacesMessage("duplicate serial no.", "E", false, "F");
                        showFacesMessage("MSG.812", "E", true, "F");
                        _log.info("Duplicate Serial No.");

                    }
                } else {
                    showFacesMessage("No. of Serial No. Exceeds the Quantity.", "E", false, "F");
                }
            } else {
                //  showFacesMessage("Please enter/select the Serial No.", "E", false, "F");
                showFacesMessage("MSG.792", "E", true, "F");
            }
        } else {
            if (transSerialSrOp.getValue() != null && transSerialSrOp.getValue() != "" &&
                !transSerialSrOp.getValue().toString().equalsIgnoreCase("0")) {
                String str = transSerialSrOp.getValue().toString();

                String trimStr = str.trim();
                if (trimStr.length() > 0) {
                    String valid = "N";
                    String paramBinChk = resolvEl("#{pageFlowScope.GLBL_ORG_BIN_CHK}");
                    //code for check if Quantity does not exceeds Bin or Lot Quantity
                    OperationBinding chkLBQty = getBindings().getOperationBinding("chkLotOrBinQuantity");
                    chkLBQty.getParamsMap().put("Binchk", paramBinChk);
                    chkLBQty.execute();

                    if (chkLBQty.getErrors().size() == 0 && chkLBQty.getResult() != null) {
                        valid = (String) chkLBQty.getResult();
                    }

                    if (valid.equals("Y")) {
                        //code for check duplicate implement by Nitesh Garg
                        OperationBinding chkSrDup = getBindings().getOperationBinding("chkSerialDuplicate");
                        chkSrDup.getParamsMap().put("SrNo", itmSerialNo.getValue().toString());
                        chkSrDup.execute();
                        String dupli = "N";
                        if (chkSrDup.getErrors().size() == 0 && chkSrDup.getResult() != null)
                            dupli = (String) chkSrDup.getResult();
                        if (dupli.equals("N")) {
                            OperationBinding op = getBindings().getOperationBinding("CreateInsert2");
                            op.execute();
                            OperationBinding opb = getBindings().getOperationBinding("setItmLotBinCurrSr");
                            opb.execute();
                            transSerialSrOp.resetValue();
                            AdfFacesContext.getCurrentInstance().addPartialTarget(transSerialSrOp);
                        } else {
                            //  showFacesMessage("duplicate serial no.", "E", false, "F");
                            showFacesMessage("MSG.812", "E", true, "F");
                            _log.info("Duplicate Serial No.");
                        }
                    } else {
                        showFacesMessage("No. of Serial No. Exceeds the Quantity.", "E", false, "F");
                    }
                } else {
                    //  showFacesMessage("Invalid Serial No.", "E", false, "F");
                    showFacesMessage("MSG.771", "E", true, "F");
                }
            } else {
                // showFacesMessage("Please enter/select the Serial No.", "E", false, "F");
                showFacesMessage("MSG.792", "E", true, "F");
            }
        }
    }

    public String genRmda(String cldid, String orgid, Integer slocId) {

        OperationBinding genrmdaOp = getBindings().getOperationBinding("generateRmda");
        genrmdaOp.getParamsMap().put("OrgId", orgid);
        genrmdaOp.getParamsMap().put("slocId", slocId);
        genrmdaOp.getParamsMap().put("CldId", cldid);
        genrmdaOp.execute();
        String ret = "N";
        if ("1".equals(genrmdaOp.getResult().toString())) {
            ret = "Y";
        }
        return ret;
    }


    public void viewRmdaNoAction(ActionEvent actionEvent) {
        /* String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId= Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));

         */
        /*      OperationBinding rmdaOp=getBindings().getOperationBinding("isRmdaChanged");
        rmdaOp.execute();



        if(rmdaNo.getValue()==null || "Y".equals(rmdaOp.getResult().toString())){
        _log.info("Generate Rmda");
            String ret=genRmda(paramCldId, paramOrgId, paramSlocId);
            if("Y".equals(ret)){
                OperationBinding exeOp=getBindings().getOperationBinding("Execute5");
                exeOp.execute();
                OperationBinding execOp=getBindings().getOperationBinding("Execute6");
                execOp.execute();

                showPopup(rmdaPopUp, true);
            }
        }else{
    */showPopup(rmdaPopUp, true);
        //  }
    }


    public void deleteItmSerialAction(ActionEvent actionEvent) {
        OperationBinding delOp = getBindings().getOperationBinding("Delete1");
        delOp.execute();
        OperationBinding exOp = getBindings().getOperationBinding("Execute3");
        exOp.execute();

    }

    public void deleteItemAdditionalSerialAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding delOp = getBindings().getOperationBinding("Delete5");
        delOp.execute();
        OperationBinding exOp = getBindings().getOperationBinding("Execute8");
        exOp.execute();
    }

    public void receivedQtyBsVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            OperationBinding opc = getBindings().getOperationBinding("setRcptQtySpecific");
            opc.getParamsMap().put("rcptQtyBs", (Number) valueChangeEvent.getNewValue());
            opc.execute();

            OperationBinding callBs = getBindings().getOperationBinding("callrcptQtyVCE");
            callBs.getParamsMap().put("oldQtyBs", valueChangeEvent.getOldValue());
            callBs.getParamsMap().put("newQtyBs", valueChangeEvent.getNewValue());
            callBs.execute();

        }
    }

    public void reworkableQtyBsVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            OperationBinding opc = getBindings().getOperationBinding("setRwkQtySpecific");
            opc.getParamsMap().put("rwkQtyBs", (Number) valueChangeEvent.getNewValue());
            opc.execute();
            OperationBinding callBs = getBindings().getOperationBinding("callrwkQtyVCE");
            callBs.getParamsMap().put("oldQtyBs", valueChangeEvent.getOldValue());
            callBs.getParamsMap().put("newQtyBs", valueChangeEvent.getNewValue());
            callBs.execute();
        }
    }

    public void rejectedQtyBsVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            OperationBinding opc = getBindings().getOperationBinding("setRejQtySpecific");
            opc.getParamsMap().put("rejQtyBs", (Number) valueChangeEvent.getNewValue());
            opc.execute();
            OperationBinding callBs = getBindings().getOperationBinding("callrejQtyVCE");
            callBs.getParamsMap().put("oldQtyBs", valueChangeEvent.getOldValue());
            callBs.getParamsMap().put("newQtyBs", valueChangeEvent.getNewValue());
            callBs.execute();
        }
    }


    public void setLotidBindPage(RichInputText lotidBindPage) {
        this.lotidBindPage = lotidBindPage;
    }

    public RichInputText getLotidBindPage() {
        return lotidBindPage;
    }

    /*   public void setGeNoBind(RichInputComboboxListOfValues geNoBind) {
        this.geNoBind = geNoBind;
    }

    public RichInputComboboxListOfValues getGeNoBind() {
        return geNoBind;
    } */


    /**
     *@ Nitesh Garg
     * 1> To Validate, If current FyId is > 1 then can not use Opening Balance to Add
     * */
    public void RcptSrcTypeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String paramGeChk = resolvEl("#{pageFlowScope.GLBL_ORG_GE_CHK}");
        if (paramGeChk.equalsIgnoreCase("Y"))
            geNoListBinding.processUpdates(facesContext.getCurrentInstance());
        if (object != null) {
            Integer type = (Integer) object;
            if (type == 396) {
                OperationBinding chk = getBindings().getOperationBinding("ChkUseOpeningBalance");
                chk.getParamsMap().put("SrcType", object);
                chk.execute();
                String status = null;
                if (chk.getErrors().size() == 0) {
                    if (chk.getResult() != null) {
                        status = (String) chk.getResult();
                        if (status.equals("N")) {
                            throw new ValidatorException(new FacesMessage("Opening Balance Can not be Selected for this Financial Year."));
                        }
                    } else
                        throw new ValidatorException(new FacesMessage("Something went Wrong, contact ESS!"));

                }
            }


        }
    }

    public void setChkLotAss(String chkLotAss) {
        this.chkLotAss = chkLotAss;
    }

    public String getChkLotAss() {
        return chkLotAss;
    }

    public void setGeNoListBinding(RichInputComboboxListOfValues geNoListBinding) {
        this.geNoListBinding = geNoListBinding;
    }

    public RichInputComboboxListOfValues getGeNoListBinding() {
        return geNoListBinding;
    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void transItmQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number zero = new Number(0);
            Number value = (Number) object;
            if (((Number) object).compareTo(zero) == -1 || value.compareTo(zero) == 0) {
                showFacesMessage("Quantity must be greater than zero.", "E", false, "V");
                //  showFacesMessage("MSG.767", "E", true, "V");
            }

            Boolean flag = isPrecisionValid(26, 6, value);
            if (flag.equals(false)) {
                showFacesMessage("MSG.57", "E", true, "V"); //"Invalid pricision (26,6)."
            }
        }
    }

    public void GEValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        OperationBinding op = getBindings().getOperationBinding("isGEDupli");
        op.getParamsMap().put("geno", object);
        op.execute();
        if (op.getErrors().size() == 0 || op.getResult() != null) {
            if (op.getResult().toString().equals("Y"))
                throw new ValidatorException(new FacesMessage("Duplicate Gate Entry!"));
        }

    }

    public void setUpdtPONObuttonBind(RichCommandImageLink updtPONObuttonBind) {
        this.updtPONObuttonBind = updtPONObuttonBind;
    }

    public RichCommandImageLink getUpdtPONObuttonBind() {
        return updtPONObuttonBind;
    }

    public void setQtyChangePopup(RichPopup qtyChangePopup) {
        this.qtyChangePopup = qtyChangePopup;
    }

    public RichPopup getQtyChangePopup() {
        return qtyChangePopup;
    }

    public void qtyChangePopupCL(PopupCanceledEvent popupCanceledEvent) {
        finalRcptQty = new Number(0);
        fianlRcptQtyBinding.processUpdates(FacesContext.getCurrentInstance());
        fianlRcptQtyBinding.resetValue();
        fianlRcptQtyBinding.setValue(oldFinalRcptQty);
        OperationBinding opc = getBindings().getOperationBinding("setFinalRcptQtyBase");
        opc.getParamsMap().put("frcptQty", oldFinalRcptQty);
        opc.execute();
        oldFinalRcptQty = new Number(0);
    }

    public void qtyChangePopupDL(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
            //Delete Lot Bin Sr
            OperationBinding opdel = getBindings().getOperationBinding("deleteLotBinSr");
            opdel.execute();
            if ("Y".equalsIgnoreCase(callLotBinDltPop)) {
                _log.info("call from value change listener ::::::");
                OperationBinding opc = getBindings().getOperationBinding("setFinalRcptQtyBase");
                opc.getParamsMap().put("frcptQty", finalRcptQty);
                opc.execute();
                finalRcptQty = new Number(0);
            }
            if ("Y".equalsIgnoreCase(deleteButtonActionPop)) {
                OperationBinding op = getBindings().getOperationBinding("Delete2");
                op.execute();
            }
            _log.info(" YES call delete lot been pop call  ::::::");
        } else {
            if ("Y".equalsIgnoreCase(callLotBinDltPop)) {
                _log.info("call from value change listener ::::::");
                finalRcptQty = new Number(0);
                fianlRcptQtyBinding.processUpdates(FacesContext.getCurrentInstance());
                fianlRcptQtyBinding.resetValue();
                fianlRcptQtyBinding.setValue(oldFinalRcptQty);
                OperationBinding opc = getBindings().getOperationBinding("setFinalRcptQtyBase");
                opc.getParamsMap().put("frcptQty", oldFinalRcptQty);
                opc.execute();
                oldFinalRcptQty = new Number(0);
            }
            if ("Y".equalsIgnoreCase(deleteButtonActionPop)) {
                deleteButtonActionPop = "C";
            }

        }

        _log.info(" NO call delete lot been pop call  ::::::");
        qtyChangePopup.hide();
    }

    public void setDeleteItmPopup(RichPopup deleteItmPopup) {
        this.deleteItmPopup = deleteItmPopup;
    }

    public RichPopup getDeleteItmPopup() {
        return deleteItmPopup;
    }


    public void setFianlRcptQtyBinding(RichInputText fianlRcptQtyBinding) {
        this.fianlRcptQtyBinding = fianlRcptQtyBinding;
    }

    public RichInputText getFianlRcptQtyBinding() {
        return fianlRcptQtyBinding;
    }

    public void EoIdVCE(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        schdlNoBind.setValue("");
        schdlNoBind.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(schdlNoBind);
        OperationBinding opbind = getBindings().getOperationBinding("setTransValueToNull");
        opbind.execute();
        docNoPageVar.setValue("");
        docDate.setValue("");
        itmNameBindVar.setValue("");
        uomBindVar.setValue("");
        uomBindVar.resetValue();
        uomBindVar.setValue(null);
        itmQty.setValue("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmNameBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(uomBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmQty);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docNoPageVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docDate);

    }

    public void WhIdVCE(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        OperationBinding opbind = getBindings().getOperationBinding("setTransValueToNull");
        opbind.execute();
        docNoPageVar.setValue("");
        schdlNoBind.setValue("");
        schdlNoBind.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(schdlNoBind);
        docDate.setValue("");
        itmNameBindVar.setValue("");
        uomBindVar.setValue("");
        uomBindVar.resetValue();
        itmQty.setValue("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmNameBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(uomBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmQty);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docNoPageVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docDate);

    }

    public void setDocIdSrcDispBinding(RichInputText docIdSrcDispBinding) {
        this.docIdSrcDispBinding = docIdSrcDispBinding;
    }

    public RichInputText getDocIdSrcDispBinding() {
        return docIdSrcDispBinding;
    }

    public void setTab1binding(RichShowDetailItem tab1binding) {
        this.tab1binding = tab1binding;
    }

    public RichShowDetailItem getTab1binding() {
        return tab1binding;
    }

    public void setTab2Binding(RichShowDetailItem tab2Binding) {
        this.tab2Binding = tab2Binding;
    }

    public RichShowDetailItem getTab2Binding() {
        return tab2Binding;
    }

    public void srcDocTypeVCE(ValueChangeEvent valueChangeEvent) {
        OperationBinding opbind = getBindings().getOperationBinding("setTransValueToNull");
        opbind.execute();
        docNoPageVar.setValue("");
        schdlNoBind.setValue("");
        schdlNoBind.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(schdlNoBind);
        docDate.setValue("");
        itmNameBindVar.setValue("");
        uomBindVar.setValue("");
        uomBindVar.resetValue();
        itmQty.setValue("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmNameBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(uomBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmQty);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docNoPageVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docDate);

    }

    public void srcOrgVCE(ValueChangeEvent valueChangeEvent) {
        OperationBinding opbind = getBindings().getOperationBinding("setTransValueToNull");
        opbind.execute();
        docNoPageVar.setValue("");
        docDate.setValue("");
        itmNameBindVar.setValue("");
        uomBindVar.setValue("");
        uomBindVar.resetValue();
        itmQty.setValue("");
        whIdsrcPage.setValue(null);
        whIdsrcPage.setValue("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(whIdsrcPage);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmNameBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(uomBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmQty);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docNoPageVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docDate);

    }

    public void setDocIdSrcColumnVar(RichColumn docIdSrcColumnVar) {
        this.docIdSrcColumnVar = docIdSrcColumnVar;
    }

    public RichColumn getDocIdSrcColumnVar() {
        return docIdSrcColumnVar;
    }

    public void setOrgIdSrcBinding(RichSelectOneChoice orgIdSrcBinding) {
        this.orgIdSrcBinding = orgIdSrcBinding;
    }

    public RichSelectOneChoice getOrgIdSrcBinding() {
        return orgIdSrcBinding;
    }

    public void TransSrcDocNoVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (docDate.getValue() != null) {
            } else
                docDate.setValue(new Timestamp(System.currentTimeMillis()));
        } else
            docDate.setValue(null);
    }

    /*  public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public Boolean getDisable() {
        return disable;
    } */

    public void setEoIdSrcBinding(RichInputListOfValues eoIdSrcBinding) {
        this.eoIdSrcBinding = eoIdSrcBinding;
    }

    public RichInputListOfValues getEoIdSrcBinding() {
        return eoIdSrcBinding;
    }

    public void setRmdaNoBinding(RichCommandLink rmdaNoBinding) {
        this.rmdaNoBinding = rmdaNoBinding;
    }

    public RichCommandLink getRmdaNoBinding() {
        return rmdaNoBinding;
    }

    public String DeleteItmHaveNoQuantity() {
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer paramUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String paramHoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));

        OperationBinding delRwOp = getBindings().getOperationBinding("deleteItemsNonRecvd");
        delRwOp.getParamsMap().put("CldId", paramCldId);
        delRwOp.getParamsMap().put("slocId", paramSlocId);
        delRwOp.getParamsMap().put("OrgId", paramOrgId);
        delRwOp.getParamsMap().put("whId", getWhIdPage().getValue().toString());
        delRwOp.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(whIdPage);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docIdSrcDispBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tab1binding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docIdSrcColumnVar);
        confDlvQtyChkPopUp.hide();
        OperationBinding chk = getBindings().getOperationBinding("CheckIfAnyDocumentPresent");
        chk.execute();
        if (chk.getResult() != null) {
            if (chk.getResult().toString().equals("N")) {
                OperationBinding oproll = getBindings().getOperationBinding("Rollback");
                oproll.execute();
                return "exitRcpt";
            }
        }
        return null;
    }

    public String DeleteConfirnNoAL() {
        confDlvQtyChkPopUp.hide();
        return null;
    }

    public void showInlinePopuponMouseHover(ClientEvent clientEvent) {
        // Add event code here...
    }

    public void setRejQtybind(RichInputText rejQtybind) {
        this.rejQtybind = rejQtybind;
    }

    public RichInputText getRejQtybind() {
        return rejQtybind;
    }

    public void srcDocTypeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer type = (Integer) object;

            if (type.compareTo(SOURCE_DOC_TYPE_WOUT_PO) == 0) {
                String retV = "N";
                OperationBinding chkRcptWOPO = getBindings().getOperationBinding("chkRcptWOTPO");
                chkRcptWOPO.execute();
                if (chkRcptWOPO.getResult() != null) {
                    retV = chkRcptWOPO.getResult().toString();
                }
                _log.info("retV     :::: " + retV);
                if ("N".equalsIgnoreCase(retV)) {
                    showFacesMessage("Current Organisation not allow Receipt Without PO.", "E", false, "V");
                }
            }
        }
    }

    public void ItmLotPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number zero = new Number(0);
            Number getPrice = (Number) object;
            _log.info(" price  not null :::::::");
            if (srcDocType.getValue() != null && srcDocType.getValue() != "") {
                if (Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_FGR) {
                    if (getPrice.compareTo(zero) == 0 || zero.compareTo(getPrice) == 1) {
                        showFacesMessage("Price must be greater than zero.", "E", false, "V");
                    }
                    Boolean flag = isPrecisionValid(26, 6, getPrice);
                    if (flag.equals(false)) {
                        showFacesMessage("MSG.57", "E", true, "V"); //"Invalid pricision (26,6)."
                    }


                }
            }

        }
    }


    public void setItmLndPriceBind(RichInputText itmLndPriceBind) {
        this.itmLndPriceBind = itmLndPriceBind;
    }

    public RichInputText getItmLndPriceBind() {
        return itmLndPriceBind;
    }

    public void trnsItmPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number zero = new Number(0);
            Number getPrice = (Number) object;
            _log.info(" price  not null :::::::");
            if (srcDocType.getValue() != null && srcDocType.getValue() != "") {
                if (Integer.parseInt(srcDocType.getValue().toString()) == SOURCE_DOC_TYPE_FGR) {
                    if (getPrice.compareTo(zero) == 0 || zero.compareTo(getPrice) == 1) {
                        showFacesMessage("Price must be greater than zero.", "E", false, "V");
                    }
                    Boolean flag = isPrecisionValid(26, 6, getPrice);
                    if (flag.equals(false)) {
                        showFacesMessage("MSG.57", "E", true, "V"); //"Invalid pricision (26,6)."
                    }


                }

            }
        }
    }

    public String rcptItmDelete() {
        _log.info("Item delete");
        OperationBinding opchk = getBindings().getOperationBinding("chkLotAssignOrNot");
        opchk.execute();
        if (opchk.getResult() != null)
            if (opchk.getResult().toString().equals("Y")) {
                callLotBinDltPop = "N";
                deleteButtonActionPop = "Y";
                showPopup(qtyChangePopup, true);
            } else {
                showPopup(deleteItmPopup, true);
            }


        _log.info("Item added");
        OperationBinding opbind = getBindings().getOperationBinding("setTransValueToNull");
        opbind.execute();
        itmNameBindVar.setValue("");
        uomBindVar.setValue("");
        uomBindVar.resetValue();
        itmQty.setValue("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmNameBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(uomBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmQty);
        return null;
    }

    public void deleteItemDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {

            OperationBinding op = getBindings().getOperationBinding("Delete2");
            op.execute();

            _log.info(" YES call delete :::::::::");
        } else {

        }
    }

    /**
     * Methode for check any Reworkable Quantity  present or not for current Receipt.
     * @return "Y" if Reworkable Quantity  present and
     *         "N" when Reworkable Quantity not present
     */
    public String getRwrkTrue() {
        String chkReworkableN = "N";
        OperationBinding chkRwkable = getBindings().getOperationBinding("chkRWkableQtyPresent");
        chkRwkable.execute();
        if (chkRwkable.getResult() != null) {
            chkReworkableN = chkRwkable.getResult().toString();
        }
        return chkReworkableN;
    }


    public void fileDownloadAction(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        RichInputText bind = this.getFileBindName();
        if (bind != null) {
            System.out.println("come ----- 1");
            String path = bind.getValue().toString();

            System.out.println("path is:  " + path);
            try {
                FileInputStream in = new FileInputStream(path);
                System.out.println("Available bytes are:  " + in.available());
                if (in.available() <= 0) {
                    System.out.println("came in unavailable!");
                    FacesMessage msg = new FacesMessage("There is no data in the File !!");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return;
                }
                byte b[] = new byte[in.available()];
                int i = in.read(b);
                outputStream.write(b);

                outputStream.flush();
                outputStream.close();
                in.close();
                facesContext.getCurrentInstance().responseComplete();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
                FacesMessage msg = new FacesMessage("File Not Found in the Directory!!");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (IOException e) {
                System.out.println("IO Exception occur------");
                FacesMessage msg = new FacesMessage("The File is corrupted!!");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }


    public void setFileBindName(RichInputText fileBindName) {
        this.fileBindName = fileBindName;
    }

    public RichInputText getFileBindName() {
        return fileBindName;
    }

    public void setBinQtyBsBind(RichInputText binQtyBsBind) {
        this.binQtyBsBind = binQtyBsBind;
    }

    public RichInputText getBinQtyBsBind() {
        return binQtyBsBind;
    }

    public String releaseRcptForCostingAction() {
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer paramUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String paramHoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String tfMode = resolvEl("#{pageFlowScope.ADD_EDIT_MODE}");
        String paramWhChk = resolvEl("#{pageFlowScope.GLBL_ORG_WH_CHK}");
        String paramGeChk = resolvEl("#{pageFlowScope.GLBL_ORG_GE_CHK}");

        Integer fyNo = 0;
        if (rcptDate.getValue() != null) {

            if (rcptSrcType.getValue() != null) {

                OperationBinding isDocPr = getBindings().getOperationBinding("isDocumentPresent");
                isDocPr.execute();

                if ("Y".equals(isDocPr.getResult().toString())) {

                    if (Integer.parseInt(rcptSrcType.getValue().toString()) == RCPT_TYPE_OB ||
                        Integer.parseInt(srcDocType.getValue().toString()) == (SOURCE_DOC_TYPE_FGR)) { //Opening Balance
                        OperationBinding rFy = getBindings().getOperationBinding("getFYid");
                        rFy.getParamsMap().put("CldId", paramCldId);
                        rFy.getParamsMap().put("OrgId", paramOrgId);
                        rFy.getParamsMap().put("geDate", (Timestamp) rcptDate.getValue());
                        rFy.getParamsMap().put("Mode", "V");
                        rFy.execute();

                        Object fy = rFy.getResult();

                        fyNo = Integer.parseInt(fy.toString());

                        /**
                     * Stock Detail is entered or not, for all Items.? in Both Cases.? @Nitesh Garg
                     */
                        String txnvalid = "Y";
                        //check if lot allotted
                        OperationBinding chkLot = getBindings().getOperationBinding("checkLotSelected");
                        chkLot.getParamsMap().put("CldId", paramCldId);
                        chkLot.getParamsMap().put("SlocId", paramSlocId);
                        chkLot.getParamsMap().put("OrgId", paramOrgId);
                        chkLot.execute();
                        String lotchkR = "N";
                        if (chkLot.getResult() != null) {
                            lotchkR = chkLot.getResult().toString();
                        }

                        String paramBinChk = resolvEl("#{pageFlowScope.GLBL_ORG_BIN_CHK}");
                        if ("N".equalsIgnoreCase(lotchkR)) {

                            OperationBinding chkBin = getBindings().getOperationBinding("checkBinSelected");
                            chkBin.getParamsMap().put("CldId", paramCldId);
                            chkBin.getParamsMap().put("SlocId", paramSlocId);
                            chkBin.getParamsMap().put("OrgId", paramOrgId);
                            chkBin.getParamsMap().put("Binchk", paramBinChk);
                            chkBin.execute();

                            if ("N".equalsIgnoreCase(chkBin.getResult().toString())) {
                                OperationBinding chkSr = getBindings().getOperationBinding("checkSerialSelected");
                                chkSr.getParamsMap().put("CldId", paramCldId);
                                chkSr.getParamsMap().put("SlocId", paramSlocId);
                                chkSr.getParamsMap().put("OrgId", paramOrgId);
                                chkSr.getParamsMap().put("Binchk", paramBinChk);
                                chkSr.execute();
                                String srChkR = "N";
                                if (chkSr.getResult() != null) {
                                    srChkR = chkSr.getResult().toString();
                                }
                                if ("N".equalsIgnoreCase(srChkR)) {
                                    //check if all entered
                                    _log.info("Stock is given for all items");

                                } else {
                                    txnvalid = "N";
                                }
                            } else {
                                txnvalid = "N";
                            }
                        } else {
                            txnvalid = "N";
                        }


                        /**
                     * Stock is Updated for all Items or not.? in Both Cases OB & FGR.? @Nitesh Garg(Close)
                     */
                        if (txnvalid.equals("Y")) {
                            OperationBinding rcptNoOp = getBindings().getOperationBinding("getRcptNo");
                            rcptNoOp.getParamsMap().put("SlocId", paramSlocId);
                            rcptNoOp.getParamsMap().put("CldId", paramCldId);
                            rcptNoOp.getParamsMap().put("OrgId", paramOrgId);
                            rcptNoOp.getParamsMap().put("WhId", getWhIdPage().getValue().toString());
                            rcptNoOp.getParamsMap().put("fyId", fyNo);
                            rcptNoOp.execute();

                            Object rno = rcptNoOp.getResult();
                            _log.info("--GeNo--" + rno);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(rcptNo);
                            OperationBinding exec = getBindings().getOperationBinding("Commit");
                            exec.execute();

                            OperationBinding execUpl = getBindings().getOperationBinding("updateLndCost");
                            execUpl.getParamsMap().put("cldId", paramCldId);
                            execUpl.getParamsMap().put("SlocId", paramSlocId);
                            execUpl.getParamsMap().put("OrgId", paramOrgId);
                            execUpl.execute();
                            // exec.execute();  commented on 23-may-2014

                            String retval = callWfMethods(paramSlocId, paramCldId, paramOrgId, paramUsrId, "SF");

                            if ("Y".equals(retval)) {
                                srNoBind.setValue("");
                                srNoBind.resetValue();
                                AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                                // exec.execute();
                                RequestContext context = RequestContext.getCurrentInstance();
                                context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                return "goTOWf";
                                //showFacesMessage("Receipt "+rno+" Saved Succesfully", "I", false, "F");
                            } else {
                                _log.info("Error in saving WF related data");
                            }
                        } else {
                            _log.info("Stock Update Problem,Can not Forward");
                            // showFacesMessage("To Forward,Please Update PO No. for this Receipt.", "E", false, "F");
                        }

                    } else { //For all other rcpt Types
                        if (srcDocType.getValue() != null) {

                            Integer scDocType = Integer.parseInt(srcDocType.getValue().toString());

                            if (scDocType.equals(SOURCE_DOC_TYPE_PO) || scDocType.equals(SOURCE_DOC_TYPE_WOUT_PO)) {
                                //Check if any PO Added or not in both Types@nitesh.
                                OperationBinding chkpo = getBindings().getOperationBinding("checkForPOPresent");
                                chkpo.execute();
                                String ispoexist = "N";
                                if (chkpo.getErrors().size() == 0 && chkpo.getResult() != null)
                                    ispoexist = (String) chkpo.getResult();
                                if (ispoexist.equals("N")) {
                                    showFacesMessage("Not a Valid PO,Please Update PO No.", "E", false, "F");
                                } else {
                                    OperationBinding rFy = getBindings().getOperationBinding("getFYid");
                                    rFy.getParamsMap().put("CldId", paramCldId);
                                    rFy.getParamsMap().put("OrgId", paramOrgId);
                                    rFy.getParamsMap().put("geDate", (Timestamp) rcptDate.getValue());
                                    rFy.getParamsMap().put("Mode", "V");
                                    rFy.execute();

                                    Object fy = rFy.getResult();

                                    fyNo = Integer.parseInt(fy.toString());

                                    OperationBinding chkRcvd =
                                        getBindings().getOperationBinding("checkForRcvdQtyPresent");
                                    chkRcvd.getParamsMap().put("CldId", paramCldId);
                                    chkRcvd.getParamsMap().put("slocId", paramSlocId);
                                    chkRcvd.getParamsMap().put("OrgId", paramOrgId);
                                    chkRcvd.getParamsMap().put("whId", getWhIdPage().getValue().toString());
                                    chkRcvd.execute();


                                    HashSet h = (HashSet) chkRcvd.getResult();
                                    if (!h.isEmpty()) {
                                        _log.info("HashSet not empty" + h);
                                        //Key->MSG.747-Some items do not have received/rejected/reworkable quantity for the following Documents
                                        StringBuilder msg =
                                            new StringBuilder("<html><body><p>Some items do not have received/rejected/reworkable quantity for the following Documents : <b>" +
                                                              h + "</b> </p>");
                                        //key->MSG.706->>Click YES to delete.
                                        msg.append("<ul> <li>Click YES to delete.</li>");
                                        //key->MSG.707->>Click NO to continue.
                                        msg.append("<li>Click NO to continue.</li></ul>");
                                        msg.append("</body></html>");


                                        msgpop = msg.toString();
                                        /*    FacesMessage fm=new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg);
                            FacesContext.getCurrentInstance().addMessage(null, fm); */
                                        showPopup(confDlvQtyChkPopUp, true);
                                        _log.info("After msg popup");

                                    } else { //if received/rej/rwk qty present
                                        String txnvalid = "Y";

                                        if ("N".equals(authStat.getValue().toString())) {

                                            //check if all the serial nos are entered
                                            //if yes,
                                            OperationBinding chkSerOp =
                                                getBindings().getOperationBinding("checkSerialEntered");
                                            chkSerOp.getParamsMap().put("CldId", paramCldId);
                                            chkSerOp.getParamsMap().put("SlocId", paramSlocId);
                                            chkSerOp.getParamsMap().put("OrgId", paramOrgId);
                                            chkSerOp.execute();
                                            _log.info("---txninvalid---" + txnvalid);
                                            if ("N".equals(chkSerOp.getResult().toString())) {
                                                String chkReworkableNe = "N";
                                                OperationBinding chkRwkable =
                                                    getBindings().getOperationBinding("chkRWkableQtyPresent");
                                                chkRwkable.execute();
                                                if (chkRwkable.getResult() != null) {
                                                    chkReworkableNe = chkRwkable.getResult().toString();
                                                }

                                                _log.info("chkReworkableNe   :  " + chkReworkableNe);

                                                // if("Y".equals(chkReworkable.getValue().toString())){//if reworkable qty present
                                                if ("Y".equalsIgnoreCase(chkReworkableNe)) {
                                                    OperationBinding setStatRcptOp =
                                                        getBindings().getOperationBinding("setRcptStatus");
                                                    setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_REWORK);
                                                    setStatRcptOp.execute();
                                                    return null;
                                                } else {

                                                    if (scDocType.equals(SOURCE_DOC_TYPE_PO)) {

                                                        //validation if all values entered.
                                                        _log.info("QC REQD--" + checkQcReqd.getValue().toString());

                                                        if ("Y".equalsIgnoreCase(checkQcReqd.getValue().toString())) {
                                                            OperationBinding chkRcvItm =
                                                                getBindings().getOperationBinding("chkIsAnyItmForQc");
                                                            chkRcvItm.execute();
                                                            String chkQc = "Y";
                                                            if (chkRcvItm.getErrors().size() == 0 &&
                                                                chkRcvItm.getResult() != null)
                                                                chkQc = (String) chkRcvItm.getResult();
                                                            if (chkQc.equals("Y")) {
                                                                OperationBinding setStatRcptOp =
                                                                    getBindings().getOperationBinding("setRcptStatus");
                                                                setStatRcptOp.getParamsMap().put("stat",
                                                                                                 RCPT_STATUS_QCPEND);
                                                                setStatRcptOp.execute();
                                                            } else {

                                                            }
                                                        } else {
                                                            _log.info("QC NOT REQD");
                                                            /*   OperationBinding setStatRcptOp = getBindings().getOperationBinding("setRcptStatus");
                                                 setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_PEND);
                                                 setStatRcptOp.execute();
                                     } */

                                                        }
                                                    }

                                                } //end else
                                            } else {
                                                txnvalid = "N";
                                            }
                                        } else if ("Y".equalsIgnoreCase(authStat.getValue().toString())) {
                                            //will never enter here in case of snf as button will be disabled

                                            /*  OperationBinding exec = getBindings().getOperationBinding("Commit");      // Commented for costing applied 13/10/2014
                                exec.execute();
                                OperationBinding execUpl = getBindings().getOperationBinding("updateLndCost");
                                                                        execUpl.getParamsMap().put("cldId", paramCldId);
                                                                        execUpl.getParamsMap().put("SlocId", paramSlocId);
                                                                        execUpl.getParamsMap().put("OrgId", paramOrgId);
                                                                        execUpl.execute();
                                exec.execute();
                                         OperationBinding execUp = getBindings().getOperationBinding("updateStock");
                                         execUp.getParamsMap().put("cldId", paramCldId);
                                         execUp.getParamsMap().put("SlocId", paramSlocId);
                                         execUp.getParamsMap().put("OrgId", paramOrgId);
                                         execUp.execute();
                                         Object o=execUp.getResult();
                                         _log.info("Stock update called :"+o);

                                          if(execUp.getErrors().isEmpty()){
                                              //   exec.execute();  //commit
                                              OperationBinding setStatRcptOp = getBindings().getOperationBinding("setRcptStatus");
                                                        setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_STOCKUP);
                                                        setStatRcptOp.execute();

                                          }else{
                                                 _log.info("Stock update failed!!");
                                          }  */ // Commented for costing applied 13/10/2014
                                        }


                                        if ("Y".equalsIgnoreCase(txnvalid)) {
                                            if (tfMode.equals("A")) {
                                                OperationBinding rcptNoOp =
                                                    getBindings().getOperationBinding("getRcptNo");
                                                rcptNoOp.getParamsMap().put("SlocId", paramSlocId);
                                                rcptNoOp.getParamsMap().put("CldId", paramCldId);
                                                rcptNoOp.getParamsMap().put("OrgId", paramOrgId);
                                                rcptNoOp.getParamsMap().put("WhId",
                                                                            getWhIdPage().getValue().toString());
                                                rcptNoOp.getParamsMap().put("fyId", fyNo);
                                                rcptNoOp.execute();
                                                Object rno = rcptNoOp.getResult();
                                                _log.info("--GeNo--" + rno);

                                            }

                                            AdfFacesContext.getCurrentInstance().addPartialTarget(rcptNo);

                                            OperationBinding exec = getBindings().getOperationBinding("Commit");
                                            exec.execute();
                                            if (exec.getErrors().isEmpty()) {
                                                if ("N".equalsIgnoreCase(authStat.getValue().toString())) {
                                                    //  String retval=callWfMethods(paramSlocId, paramCldId, paramOrgId, paramUsrId, "SF");\\  Commented for costing applied 13/10/2014
                                                    String retval =
                                                        callWfMethods(paramSlocId, paramCldId, paramOrgId, paramUsrId,
                                                                      "S");
                                                    _log.info("WF methods call complete:" + retval);
                                                }
                                                if (scDocType.equals(SOURCE_DOC_TYPE_PO)) {

                                                    _log.info("Set currency:::::::::::::::::::::::::: ");
                                                    OperationBinding opcurr =
                                                        getBindings().getOperationBinding("setCurrIdSpPoToMtlRcpt");
                                                    opcurr.getParamsMap().put("value", SOURCE_DOC_TYPE_PO);
                                                    opcurr.execute();

                                                    // exec.execute();  commented on 23-may-2014

                                                }
                                                srNoBind.setValue("");
                                                srNoBind.resetValue();
                                                AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                                                RequestContext context = RequestContext.getCurrentInstance();
                                                context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                                //Check Receipt Status is QC Pendng or not.if QC Pending then do not forward.
                                                OperationBinding chkStat =
                                                    getBindings().getOperationBinding("CheckRcptStatus");
                                                chkStat.execute();
                                                if (chkStat.getResult() != null) {
                                                    Integer stat = (Integer) chkStat.getResult();
                                                    if (stat.equals(RCPT_STATUS_QCPEND)) {
                                                        showFacesMessage("QC Pending! Can not Forward Receipt.", "I",
                                                                         false, "F");
                                                        return null;
                                                    } else
                                                        return null; // return "goTOWf"; // Commented for costing applied 13/10/2014
                                                } else
                                                    showFacesMessage("Error while Forwarding.Contact to ESS!", "E",
                                                                     false, "F");
                                                return null;
                                                // return "goTOWf";
                                                // enableHeader=false;
                                                //   showFacesMessage("Receipt Saved Succesfully", "I", false, "F");
                                            }


                                        } //endIf---TxnValid
                                    } //end for all Rcpt Types
                                }
                            } else if (scDocType.equals(SOURCE_DOC_TYPE_TO)) {

                                OperationBinding rFy = getBindings().getOperationBinding("getFYid");
                                rFy.getParamsMap().put("CldId", paramCldId);
                                rFy.getParamsMap().put("OrgId", paramOrgId);
                                rFy.getParamsMap().put("geDate", (Timestamp) rcptDate.getValue());
                                rFy.getParamsMap().put("Mode", "V");
                                rFy.execute();

                                Object fy = rFy.getResult();

                                fyNo = Integer.parseInt(fy.toString());

                                OperationBinding chkRcvd = getBindings().getOperationBinding("checkForRcvdQtyPresent");
                                chkRcvd.getParamsMap().put("CldId", paramCldId);
                                chkRcvd.getParamsMap().put("slocId", paramSlocId);
                                chkRcvd.getParamsMap().put("OrgId", paramOrgId);
                                chkRcvd.getParamsMap().put("whId", getWhIdPage().getValue().toString());
                                chkRcvd.execute();


                                HashSet h = (HashSet) chkRcvd.getResult();
                                if (!h.isEmpty()) {
                                    _log.info("TO HashSet not empty" + h);
                                    //Key->MSG.747-Some items do not have received/rejected/reworkable quantity for the following Documents
                                    StringBuilder msg =
                                        new StringBuilder("<html><body><p>Some items do not have received/rejected/reworkable quantity for the following Documents : <b>" +
                                                          h + "</b> </p>");
                                    //key->MSG.706->>Click YES to delete.
                                    msg.append("<ul> <li>Click YES to delete.</li>");
                                    //key->MSG.707->>Click NO to continue.
                                    msg.append("<li>Click NO to continue.</li></ul>");
                                    msg.append("</body></html>");

                                    msgpop = msg.toString();
                                    /*    FacesMessage fm=new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg);
                                               FacesContext.getCurrentInstance().addMessage(null, fm); */
                                    showPopup(confDlvQtyChkPopUp, true);
                                    _log.info("After msg popup");
                                } else {
                                    if (tfMode.equals("A")) {
                                        OperationBinding rcptNoOp = getBindings().getOperationBinding("getRcptNo");
                                        rcptNoOp.getParamsMap().put("SlocId", paramSlocId);
                                        rcptNoOp.getParamsMap().put("CldId", paramCldId);
                                        rcptNoOp.getParamsMap().put("OrgId", paramOrgId);
                                        rcptNoOp.getParamsMap().put("WhId", getWhIdPage().getValue().toString());
                                        rcptNoOp.getParamsMap().put("fyId", fyNo);
                                        rcptNoOp.execute();
                                        Object rno = rcptNoOp.getResult();
                                        _log.info("--RCPTNO TO--" + rno);

                                    }

                                    AdfFacesContext.getCurrentInstance().addPartialTarget(rcptNo);

                                    OperationBinding exec = getBindings().getOperationBinding("Commit");
                                    exec.execute();
                                    if (exec.getErrors().isEmpty()) {
                                        if ("N".equalsIgnoreCase(authStat.getValue().toString())) {

                                            // String retval=callWfMethods(paramSlocId, paramCldId, paramOrgId, paramUsrId,"SF");  // Commented for costing applied 13/10/2014
                                            String retval =
                                                callWfMethods(paramSlocId, paramCldId, paramOrgId, paramUsrId, "S");
                                            _log.info("WF methods call complete:" + retval);
                                        }
                                        // exec.execute(); commented on 23-may-2014
                                        srNoBind.setValue("");
                                        srNoBind.resetValue();
                                        AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                                        RequestContext context = RequestContext.getCurrentInstance();
                                        context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                        // enableHeader=false;
                                        //showFacesMessage("Receipt Saved Succesfully", "I", false, "F");
                                        // return "goTOWf";  //Commented for costing applied 13/10/2014
                                        return null;
                                    }
                                }

                            } else if (scDocType.equals(SOURCE_DOC_TYPE_MRN)) {
                                OperationBinding rFy = getBindings().getOperationBinding("getFYid");
                                rFy.getParamsMap().put("CldId", paramCldId);
                                rFy.getParamsMap().put("OrgId", paramOrgId);
                                rFy.getParamsMap().put("geDate", (Timestamp) rcptDate.getValue());
                                rFy.getParamsMap().put("Mode", "V");
                                rFy.execute();

                                Object fy = rFy.getResult();

                                fyNo = Integer.parseInt(fy.toString());

                                OperationBinding chkRcvd = getBindings().getOperationBinding("checkForRcvdQtyPresent");
                                chkRcvd.getParamsMap().put("CldId", paramCldId);
                                chkRcvd.getParamsMap().put("slocId", paramSlocId);
                                chkRcvd.getParamsMap().put("OrgId", paramOrgId);
                                chkRcvd.getParamsMap().put("whId", getWhIdPage().getValue().toString());
                                chkRcvd.execute();


                                HashSet h = (HashSet) chkRcvd.getResult();
                                if (!h.isEmpty()) {
                                    _log.info("TO HashSet not empty" + h);
                                    //key->MSG.705->Some items do not have received quantity for the following Documents
                                    StringBuilder msg =
                                        new StringBuilder("<html><body><p>Some items do not have received quantity for the following Documents : <b>" +
                                                          h + "</b> </p>");
                                    //key->MSG.706->>Click YES to delete.
                                    msg.append("<ul> <li>Click YES to delete.</li>");
                                    //key->MSG.707->>Click NO to continue.
                                    msg.append("<li>Click NO to continue.</li></ul>");
                                    msg.append("</body></html>");

                                    msgpop = msg.toString();
                                    /*    FacesMessage fm=new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg);
                                               FacesContext.getCurrentInstance().addMessage(null, fm); */
                                    showPopup(confDlvQtyChkPopUp, true);
                                    _log.info("After msg popup");
                                } else {
                                    //check if all enetered
                                    if (tfMode.equals("A")) {
                                        OperationBinding rcptNoOp = getBindings().getOperationBinding("getRcptNo");
                                        rcptNoOp.getParamsMap().put("SlocId", paramSlocId);
                                        rcptNoOp.getParamsMap().put("CldId", paramCldId);
                                        rcptNoOp.getParamsMap().put("OrgId", paramOrgId);
                                        rcptNoOp.getParamsMap().put("WhId", getWhIdPage().getValue().toString());
                                        rcptNoOp.getParamsMap().put("fyId", fyNo);
                                        rcptNoOp.execute();
                                        Object rno = rcptNoOp.getResult();
                                        _log.info("--RCPTNO TO--" + rno);

                                    }

                                    AdfFacesContext.getCurrentInstance().addPartialTarget(rcptNo);
                                    OperationBinding exec = getBindings().getOperationBinding("Commit");
                                    exec.execute();
                                    if (exec.getErrors().isEmpty()) {
                                        if ("N".equalsIgnoreCase(authStat.getValue().toString())) {
                                            //   String retval=callWfMethods(paramSlocId, paramCldId, paramOrgId, paramUsrId,"SF"); // Commented for costing applied 13/10/2014
                                            String retval =
                                                callWfMethods(paramSlocId, paramCldId, paramOrgId, paramUsrId, "S");
                                            _log.info("WF methods call complete:" + retval);
                                        }
                                        //  exec.execute();  commented on 23-may-2014

                                        RequestContext context = RequestContext.getCurrentInstance();
                                        context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                        // return "goTOWf";  // Commented for costing applied 13/10/2014
                                        return null;
                                    } else {
                                        _log.info("Commit fail");
                                    }

                                }
                            } else if (scDocType.equals(SOURCE_DOC_TYPE_CPO)) {

                                OperationBinding rFy = getBindings().getOperationBinding("getFYid");
                                rFy.getParamsMap().put("CldId", paramCldId);
                                rFy.getParamsMap().put("OrgId", paramOrgId);
                                rFy.getParamsMap().put("geDate", (Timestamp) rcptDate.getValue());
                                rFy.getParamsMap().put("Mode", "V");
                                rFy.execute();

                                Object fy = rFy.getResult();

                                fyNo = Integer.parseInt(fy.toString());

                                OperationBinding chkRcvd = getBindings().getOperationBinding("checkForRcvdQtyPresent");
                                chkRcvd.getParamsMap().put("CldId", paramCldId);
                                chkRcvd.getParamsMap().put("slocId", paramSlocId);
                                chkRcvd.getParamsMap().put("OrgId", paramOrgId);
                                chkRcvd.getParamsMap().put("whId", getWhIdPage().getValue().toString());
                                chkRcvd.execute();


                                HashSet h = (HashSet) chkRcvd.getResult();
                                if (!h.isEmpty()) {
                                    _log.info("HashSet not empty" + h);
                                    //Key->MSG.747-Some items do not have received/rejected/reworkable quantity for the following Documents
                                    StringBuilder msg =
                                        new StringBuilder("<html><body><p>Some items do not have received/rejected/reworkable quantity for the following Documents : <b>" +
                                                          h + "</b> </p>");
                                    //key->MSG.706->>Click YES to delete.
                                    msg.append("<ul> <li>Click YES to delete.</li>");
                                    //key->MSG.707->>Click NO to continue.
                                    msg.append("<li>Click NO to continue.</li></ul>");
                                    msg.append("</body></html>");


                                    msgpop = msg.toString();

                                    showPopup(confDlvQtyChkPopUp, true);
                                    _log.info("After msg popup");

                                } else { //if received/rej/rwk qty present
                                    String txnvalid = "Y";

                                    if ("N".equals(authStat.getValue().toString())) {
                                        _log.info("Not approved");
                                        //check if all the serial nos are entered
                                        //if yes,
                                        OperationBinding chkSerOp =
                                            getBindings().getOperationBinding("checkSerialEntered");
                                        chkSerOp.getParamsMap().put("CldId", paramCldId);
                                        chkSerOp.getParamsMap().put("SlocId", paramSlocId);
                                        chkSerOp.getParamsMap().put("OrgId", paramOrgId);
                                        chkSerOp.execute();
                                        _log.info("---txninvalid---" + txnvalid);
                                        if ("N".equals(chkSerOp.getResult().toString())) {
                                            String chkReworkableN = "N";
                                            OperationBinding chkRwkable =
                                                getBindings().getOperationBinding("chkRWkableQtyPresent");
                                            chkRwkable.execute();
                                            if (chkRwkable.getResult() != null) {
                                                chkReworkableN = chkRwkable.getResult().toString();
                                            }

                                            _log.info("chkReworkableN   :  " + chkReworkableN);

                                            // if("Y".equals(chkReworkable.getValue().toString())){//if reworkable qty present
                                            if ("Y".equalsIgnoreCase(chkReworkableN)) {
                                                _log.info(chkReworkableN + "    ChkReworkable is=" +
                                                          chkReworkable.getValue());
                                                OperationBinding setStatRcptOp =
                                                    getBindings().getOperationBinding("setRcptStatus");
                                                setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_REWORK);
                                                setStatRcptOp.execute();
                                                return null;
                                            } else {

                                                if (scDocType.equals(SOURCE_DOC_TYPE_CPO)) {

                                                    //validation if all values entered.
                                                    _log.info("QC REQD--" + checkQcReqd.getValue().toString());

                                                    if ("Y".equalsIgnoreCase(checkQcReqd.getValue().toString())) {
                                                        OperationBinding chkRcvItm =
                                                            getBindings().getOperationBinding("chkIsAnyItmForQc");
                                                        chkRcvItm.execute();
                                                        String chkQc = "Y";
                                                        if (chkRcvItm.getErrors().size() == 0 &&
                                                            chkRcvItm.getResult() != null)
                                                            chkQc = (String) chkRcvItm.getResult();
                                                        if (chkQc.equals("Y")) {
                                                            OperationBinding setStatRcptOp =
                                                                getBindings().getOperationBinding("setRcptStatus");
                                                            setStatRcptOp.getParamsMap().put("stat",
                                                                                             RCPT_STATUS_QCPEND);
                                                            setStatRcptOp.execute();
                                                        } else {

                                                        }
                                                    } else {
                                                        _log.info("QC NOT REQD");
                                                    }
                                                }

                                            } //end else
                                        } else {
                                            txnvalid = "N";
                                        }
                                    } else if ("Y".equalsIgnoreCase(authStat.getValue().toString())) {
                                        //will never enter here in case of snf as button will be disabled

                                        /*   OperationBinding exec = getBindings().getOperationBinding("Commit");    // Commented for costing applied 13/10/2014
                                exec.execute();
                                OperationBinding execUpl = getBindings().getOperationBinding("updateLndCost");
                                                             execUpl.getParamsMap().put("cldId", paramCldId);
                                                             execUpl.getParamsMap().put("SlocId", paramSlocId);
                                                             execUpl.getParamsMap().put("OrgId", paramOrgId);
                                                             execUpl.execute();
                                exec.execute();
                                         OperationBinding execUp = getBindings().getOperationBinding("updateStock");
                                         execUp.getParamsMap().put("cldId", paramCldId);
                                         execUp.getParamsMap().put("SlocId", paramSlocId);
                                         execUp.getParamsMap().put("OrgId", paramOrgId);
                                         execUp.execute();
                                         Object o=execUp.getResult();
                                         _log.info("Stock update called :"+o);

                                          if(execUp.getErrors().isEmpty()){
                                              //   exec.execute();  //commit
                                              OperationBinding setStatRcptOp = getBindings().getOperationBinding("setRcptStatus");
                                                        setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_STOCKUP);
                                                        setStatRcptOp.execute();

                                          }else{
                                                 _log.info("Stock update failed!!");
                                          }  */ // Commented for costing applied 13/10/2014
                                    }


                                    if ("Y".equalsIgnoreCase(txnvalid)) {
                                        if (tfMode.equals("A")) {
                                            OperationBinding rcptNoOp = getBindings().getOperationBinding("getRcptNo");
                                            rcptNoOp.getParamsMap().put("SlocId", paramSlocId);
                                            rcptNoOp.getParamsMap().put("CldId", paramCldId);
                                            rcptNoOp.getParamsMap().put("OrgId", paramOrgId);
                                            rcptNoOp.getParamsMap().put("WhId", getWhIdPage().getValue().toString());
                                            rcptNoOp.getParamsMap().put("fyId", fyNo);
                                            rcptNoOp.execute();
                                            Object rno = rcptNoOp.getResult();
                                            _log.info("--GeNo--" + rno);

                                        }

                                        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptNo);

                                        OperationBinding exec = getBindings().getOperationBinding("Commit");
                                        exec.execute();
                                        if (exec.getErrors().isEmpty()) {
                                            if ("N".equalsIgnoreCase(authStat.getValue().toString())) {
                                                //   String retval=callWfMethods(paramSlocId, paramCldId, paramOrgId, paramUsrId, "SF"); // Commented for costing applied 13/10/2014
                                                String retval =
                                                    callWfMethods(paramSlocId, paramCldId, paramOrgId, paramUsrId, "S");
                                                _log.info("WF methods call complete:" + retval);
                                            }
                                            if (scDocType.equals(SOURCE_DOC_TYPE_CPO)) {
                                                //  exec.execute();  commented on 23-may-2014
                                                OperationBinding opcurr =
                                                    getBindings().getOperationBinding("setCurrIdSpPoToMtlRcpt");
                                                opcurr.getParamsMap().put("value", SOURCE_DOC_TYPE_CPO);
                                                opcurr.execute();

                                            }
                                            srNoBind.setValue("");
                                            srNoBind.resetValue();
                                            AdfFacesContext.getCurrentInstance().addPartialTarget(srNoBind);
                                            RequestContext context = RequestContext.getCurrentInstance();
                                            context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                            //Check Receipt Status is QC Pendng or not.if QC Pending then do not forward.
                                            OperationBinding chkStat =
                                                getBindings().getOperationBinding("CheckRcptStatus");
                                            chkStat.execute();
                                            if (chkStat.getResult() != null) {
                                                Integer stat = (Integer) chkStat.getResult();
                                                if (stat.equals(RCPT_STATUS_QCPEND)) {
                                                    showFacesMessage("QC Pending! Can not Forward Receipt.", "I", false,
                                                                     "F");
                                                    return null;
                                                } else
                                                    return null; // return "goTOWf";  // Commented for costing applied 13/10/2014
                                            } else
                                                showFacesMessage("Error while Forwarding.Contact to ESS!", "E", false,
                                                                 "F");
                                            return null;
                                        }


                                    } //endIf---TxnValid
                                } //end for all Rcpt Types


                            } else {
                                _log.info("Document Out of scope.");
                            }
                        } else {
                            // showFacesMessage("Please Select the Document Type for the Receipt.", "E", false, "F");
                            showFacesMessage("MSG.749", "E", true, "F");
                        }
                    }
                } else {
                    // showFacesMessage("Documents have not been added for this Receipt.Cannot Save.", "E", false, "F");
                    showFacesMessage("MSG.750", "E", true, "F");
                }

            } else {
                // showFacesMessage("Please select the Receipt Type.", "E", false, "F");
                showFacesMessage("MSG.751", "E", true, "F");
            }

        } else {
            _log.info("Enter Rcpt Date");
            //showFacesMessage("Receipt Date is required.", "E", false, "F");
            showFacesMessage("MSG.752", "E", true, "F");

        }


        return null;
    }

    public void addOtherChargesAction(ActionEvent actionEvent) {
        OperationBinding rcptOcCoa = getBindings().getOperationBinding("chkOcCoaId");
        rcptOcCoa.execute();
        if (rcptOcCoa.getResult() != null) {
            Integer chkCoa = Integer.parseInt(rcptOcCoa.getResult().toString());
            if (chkCoa == 1) {
                showFacesMessage("Coa Id is Required.", "E", false, "F");
                return;
            }
        }
        if (ocDiscriptbind.getValue() == null) {
            showFacesMessage("Other charges Description is Required.", "E", false, "F");
            return;
        }
        if (transOcAmtBind.getValue() == null) {
            showFacesMessage("Amount is Required.", "E", false, "F");
            return;
        } else {
            Number ocAmt = (Number) transOcAmtBind.getValue();
            if (ocAmt.compareTo(new Number(0)) <= 0) {
                showFacesMessage("Amount must be greater than zero.", "E", false, "F");
                return;
            }
        }
        OperationBinding dupCoa = getBindings().getOperationBinding("checkOcDuplicate");
        dupCoa.execute();
        if (dupCoa.getResult() != null) {
            Integer chkCoa = Integer.parseInt(dupCoa.getResult().toString());
            if (chkCoa == 1) {
                showFacesMessage("Duplicate COA Found.", "E", false, "F");
                return;
            }
        }
        OperationBinding rcptAddoc = getBindings().getOperationBinding("addOcDetails");
        rcptAddoc.execute();

    }

    public String taxItemAddAction() {

        return null;
    }

    public void taxResetOnItem(ActionEvent actionEvent) {
        showPopup(resetTaxPopup, true);
    }

    public void setApplyTaxOnItem(RichPopup applyTaxOnItem) {
        this.applyTaxOnItem = applyTaxOnItem;
    }

    public RichPopup getApplyTaxOnItem() {
        return applyTaxOnItem;
    }

    public void setResetTaxPopup(RichPopup resetTaxPopup) {
        this.resetTaxPopup = resetTaxPopup;
    }

    public RichPopup getResetTaxPopup() {
        return resetTaxPopup;
    }

    public void TaxItemOnAddAction(ActionEvent actionEvent) {
        Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        String p_org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Number taxAbleAmt = new Number(0);
        Number purPrice = new Number(0);
        String itmId = null;
        String itmuom = null;
        RichCommandImageLink ob = (RichCommandImageLink) actionEvent.getSource();
        ob.getAttributes().get("taxableAmtSp");
        ob.getAttributes().get("purPrice");
        ob.getAttributes().get("itmId");
        ob.getAttributes().get("itmUom");
        taxAbleAmt = (Number) ob.getAttributes().get("taxableAmtSp");
        purPrice = (Number) ob.getAttributes().get("purPrice");
        if (ob.getAttributes().get("itmId") != null) {
            itmId = ob.getAttributes().get("itmId").toString();
        }
        if (ob.getAttributes().get("itmUom") != null) {
            itmuom = ob.getAttributes().get("itmUom").toString();
        }
        _log.info("taxAbleAmt :::  " + taxAbleAmt + "pur price  ::::  " + purPrice + " item id  " + itmId);
        if (itmId != null) {

        } else {
            showFacesMessage("Item Id null . ", "W", false, "F");
            return;
        }
        if (itmuom != null) {

        } else {
            showFacesMessage("Item unit null . ", "W", false, "F");
            return;
        }
        _log.info("taxAbleAmt :::  " + taxAbleAmt + "pur price  ::::  " + purPrice);
        if (purPrice.compareTo(new Number(0)) == 0) {
            showFacesMessage("Can't apply  tax . ", "W", false, "F");
            return;
        }
        _log.info("taxAbleAmt :::  " + taxAbleAmt);
        _log.info("add Tax for item ::::::::::::::: ");

        ViewObjectImpl po = getAm().getMmMtlRcpt();
        ViewObjectImpl voSrc = getAm().getMmMtlRcptSrc();
        Row poRow = po.getCurrentRow();
        Row rcptSrc = voSrc.getCurrentRow();
        ViewObject poVo = getAm().getMmMtlRcptItm();
        Row itmCurr = poVo.getCurrentRow();
        if (itmCurr != null) {
            /* if (itmCurr.getAttribute("ItmPrice") != null && itmCurr.getAttribute("ItmPrice") != new Number(0) &&
                 itmCurr.getAttribute("OrdQty") != null && itmCurr.getAttribute("OrdQty") != new Number(0) &&
                 ((Number)itmPriceBind.getValue()).compareTo(zero) == 1 &&
                 ((Number)itmQtyBind.getValue()).compareTo(zero) == 1) { */



            String flg = "N";

            getAm().getLovItmForDisp().setNamedWhereClauseParam("slocIdBind", p_sloc_id);
            getAm().getLovItmForDisp().setNamedWhereClauseParam("cldIdBind", p_cldId);
            getAm().getLovItmForDisp().setNamedWhereClauseParam("hoOrgIdBind", p_hoOrgId);
            getAm().getLovItmForDisp().setNamedWhereClauseParam("orgIdBind", p_org_id);
            getAm().getLovItmForDisp().setNamedWhereClauseParam("itmIdBind", itmId);
            getAm().getLovItmForDisp().setNamedWhereClauseParam("itmNameBind", null);
            getAm().getLovItmForDisp().executeQuery();

            Row[] itms = getAm().getLovItmForDisp().getFilteredRows("ItmId", itmId);
            _log.info("lenth   " + itms.length);
            if (itms.length > 0) {
                if (itms[0].getAttribute("TaxExmptFlg") != null) {
                    flg = itms[0].getAttribute("TaxExmptFlg").toString();
                }
            }


            // String flg = (String)itmCurr.getAttribute("TransTaxExmptFlg");

            if (flg.equals("N")) {
                ViewObject vo = getAm().getMmMtlRcpt();
                String docId = (vo.getCurrentRow().getAttribute("DocId")).toString();
                String docIdSrc = rcptSrc.getAttribute("DocIdSrc").toString();
                String whId = rcptSrc.getAttribute("WhId").toString();
                Integer dlvSchdlNo = Integer.parseInt(rcptSrc.getAttribute("DlvSchdlNo").toString());
                ViewObjectImpl trVo = getAm().getMmMtlRcptTrVO();
                RowQualifier rqtr = new RowQualifier(trVo);
                //String itmId = itmCurr.getAttribute("ItmId").toString();
                // String itmuom = itmCurr.getAttribute("ItmUom").toString();
                rqtr.setWhereClause("DocId='" + docId + "' and WhId ='" + whId + "' and DocIdSrc = '" + docIdSrc +
                                    "' and DlvSchdlNo = " + dlvSchdlNo + " AND ItmId='" + itmId + "' and ItmUom='" +
                                    itmuom + "'");
                //System.out.println("DocId=" + docId + " and  ItmId=" + itmId + "' and ItmUom='" + itmuom + "'");
                Row[] r = trVo.getFilteredRows(rqtr);
                //System.out.println("No. of rows in tr of above ids=" + r.length);
                _log.info(rqtr.getExprStr() + "    No. of rows in tr of above ids=" + r.length);
                //String tflg = poVo.getCurrentRow().getAttribute("TransTaxChangedFlg").toString();
                String tflg = "N";

                if (r.length > 0) {
                    itmtaxamt1 = (Number) (r[0].getAttribute("TaxableAmtSp"));
                    taxRuleitem = (Integer) r[0].getAttribute("TaxRuleId");
                    if (r[0].getAttribute("TaxExmptFlg") != null) { // 12341 change for tax exmpt flg null when rate contranct thourch quotation.19-09-14
                        _log.info("::::::   " + r[0].getAttribute("TaxExmptFlg"));
                        taxExmpt = r[0].getAttribute("TaxExmptFlg").toString();
                    } else {
                        _log.info("tax TaxExmptFlg nulll    ::::::::");
                    }
                    if (r[0].getAttribute("TaxRuleFlg") != null) {
                        _log.info("::::::   " + r[0].getAttribute("TaxRuleFlg"));
                        taxruleflg = r[0].getAttribute("TaxRuleFlg").toString();
                    } else {
                        _log.info("tax TaxRuleFlg nulll    ::::::::");
                    }
                }


                if (r.length > 0 && tflg.equalsIgnoreCase("N")) {
                    showPopup(applyTaxOnItem, true);
                } else {
                    tflg = "Y";
                    //remove tax if r.length>0 and tflg is "Y"
                    if (r.length > 0 && tflg.equalsIgnoreCase("Y")) {
                        //  ViewObjectImpl voline = getAm().getMmDrftPoTrLines1();
                        ViewObjectImpl voline = getAm().getMmMtlRcptTrLinesVO();

                        RowSetIterator rql = voline.createRowSetIterator(null);
                        while (rql.hasNext()) {
                            Row frline = rql.next();
                            if (frline.getAttribute("DocId").toString().equalsIgnoreCase(docId) &&
                                frline.getAttribute("ItmId").toString().equalsIgnoreCase(itmId)) {
                                //       System.out.println("Removing TRline="+frline.getKey());



                                //    frline.remove();
                            }
                        }
                        rql.closeRowSetIterator();
                        voline.executeQuery();


                        RowSetIterator rstr = trVo.createRowSetIterator(null);
                        while (rstr.hasNext()) {
                            Row rtr = rstr.next();
                            Integer dlvNo = Integer.parseInt(rtr.getAttribute("DlvSchdlNo").toString());
                            if (rtr.getAttribute("DocId").toString().equalsIgnoreCase(docId) &&
                                rtr.getAttribute("DocIdSrc").toString().equalsIgnoreCase(docIdSrc) &&
                                rtr.getAttribute("WhId").toString().equalsIgnoreCase(whId) &&
                                dlvNo.compareTo(dlvSchdlNo) == 0 &&
                                rtr.getAttribute("ItmId").toString().equalsIgnoreCase(itmId) &&
                                rtr.getAttribute("ItmUom").toString().equalsIgnoreCase(itmuom)) {
                                _log.info("Removing TR=" + rtr.getKey());
                                rtr.remove();
                            }
                        }
                        rstr.closeRowSetIterator();
                        trVo.executeQuery();

                    }

                    OperationBinding usrLevelOp = getBindings().getOperationBinding("Createwithparameters");
                    usrLevelOp.getParamsMap().put("SlocId", p_sloc_id);
                    usrLevelOp.getParamsMap().put("CldId", p_cldId);
                    usrLevelOp.getParamsMap().put("OrgId", p_org_id);
                    usrLevelOp.getParamsMap().put("UsrIdCreate", p_user_id);
                    usrLevelOp.getParamsMap().put("DocId", docId);
                    usrLevelOp.getParamsMap().put("WhId", whId);
                    usrLevelOp.getParamsMap().put("DocIdSrc", docIdSrc);
                    usrLevelOp.getParamsMap().put("DlvSchdlNo", dlvSchdlNo);
                    usrLevelOp.getParamsMap().put("ItmId", itmId);
                    usrLevelOp.getParamsMap().put("ItmUom", itmuom);
                    usrLevelOp.getParamsMap().put("TaxRuleFlg", "I");
                    usrLevelOp.getParamsMap().put("TaxableAmtSp", taxAbleAmt);
                    usrLevelOp.execute();
                    _log.info("call tax  popup");
                    showPopup(applyTaxOnItem, true);
                }
            } else {
                FacesMessage message = new FacesMessage("Tax is not Applicable on this Item");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }

            /*  } else {
                 FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.408']}"));
                 message.setSeverity(FacesMessage.SEVERITY_INFO);
                 FacesContext fc = FacesContext.getCurrentInstance();
                 fc.addMessage(null, message);
             } */
        }


    }

    public void applyDefualtTaxAction(ActionEvent actionEvent) {
        Number taxAbleAmt = new Number(0);
        String itmId = null;
        String uom = null;
        RichCommandImageLink ob = (RichCommandImageLink) actionEvent.getSource();
        ob.getAttributes().get("taxableAmtSpDefault");
        ob.getAttributes().get("itmUnit");
        ob.getAttributes().get("itmIdDefaultTax");
        taxAbleAmt = (Number) ob.getAttributes().get("taxableAmtSpDefault");
        //  purPrice = (Number)ob.getAttributes().get("purPrice"); */
        if (ob.getAttributes().get("itmIdDefaultTax") != null) {
            itmId = ob.getAttributes().get("itmIdDefaultTax").toString();
        }
        // _log.info("taxAbleAmt :::  "+taxAbleAmt + "pur price  ::::  "+purPrice+" item id  "+itmId);
        _log.info("itm id :::  " + itmId + "taxAbleAmt :::  " + taxAbleAmt);
        if (itmId != null) {

        } else {
            showFacesMessage("Item Id null . ", "W", false, "F");
            return;
        }
        if (ob.getAttributes().get("itmUnit") != null) {
            uom = ob.getAttributes().get("itmUnit").toString();
        } else {
            showFacesMessage("Item unit null . ", "W", false, "F");
            return;
        }

        String retFlg = "Y";
        OperationBinding applyDirctTx = getBindings().getOperationBinding("applyDirectTax");
        applyDirctTx.getParamsMap().put("itmId", itmId);
        applyDirctTx.getParamsMap().put("taxabltAmt", taxAbleAmt);
        applyDirctTx.getParamsMap().put("uom", uom);
        applyDirctTx.execute();
        if (applyDirctTx.getResult() != null) {
            retFlg = applyDirctTx.getResult().toString();
        }

        if ("Y".equalsIgnoreCase(retFlg)) {
            showFacesMessage("Default tax apply successfully . ", "I", false, "F");
            return;
        } else if ("N".equalsIgnoreCase(retFlg)) {
            showFacesMessage("Tax is not applicable on this Item . ", "W", false, "F");

            return;
        } else if ("R".equalsIgnoreCase(retFlg)) {
            showFacesMessage("No Tax Rule define for current supplier.", "W", false, "F");
            return;
        } else if ("E".equalsIgnoreCase(retFlg)) {
            showFacesMessage("Item or supplier may be not enterd.", "W", false, "F");
            return;
        }


    }

    public void reApplyTaxAction(ActionEvent actionEvent) {
        Number taxAbleAmt = new Number(0);
        String itmId = null;
        String uom = null;
        //RichCommandImageLink ob = (RichCommandImageLink) actionEvent.getSource();
        RichLink ob = (RichLink) actionEvent.getSource();
        ob.getAttributes().get("taxableAmtSpReapply");
        ob.getAttributes().get("itmUnitReapply");
        ob.getAttributes().get("itmIdReapplyTax");
        taxAbleAmt = (Number) ob.getAttributes().get("taxableAmtSpReapply");
        //  purPrice = (Number)ob.getAttributes().get("purPrice"); */
        if (ob.getAttributes().get("itmIdReapplyTax") != null) {
            itmId = ob.getAttributes().get("itmIdReapplyTax").toString();
        }
        // _log.info("taxAbleAmt :::  "+taxAbleAmt + "pur price  ::::  "+purPrice+" item id  "+itmId);
        _log.info("itm id :::  " + itmId + "taxAbleAmt :::  " + taxAbleAmt);
        if (itmId != null) {

        } else {
            showFacesMessage("Item Id null . ", "W", false, "F");
            return;
        }
        if (ob.getAttributes().get("itmUnitReapply") != null) {
            uom = ob.getAttributes().get("itmUnitReapply").toString();
        } else {
            showFacesMessage("Item unit null . ", "W", false, "F");
            return;
        }

        OperationBinding applyDirctTx = getBindings().getOperationBinding("reApplytaxOnItem");
        applyDirctTx.getParamsMap().put("itmIdTax", itmId);
        applyDirctTx.getParamsMap().put("taxAmountValue", taxAbleAmt);
        applyDirctTx.getParamsMap().put("itmUomTax", uom);
        applyDirctTx.execute();

    }

    public void applyPoTaxAction(ActionEvent actionEvent) {
        Number taxAbleAmt = new Number(0);
        String itmId = null;
        RichCommandImageLink ob = (RichCommandImageLink) actionEvent.getSource();
        ob.getAttributes().get("taxableAmtSpPO");
        ob.getAttributes().get("purPrice");
        ob.getAttributes().get("itmIdPOTax");
        taxAbleAmt = (Number) ob.getAttributes().get("taxableAmtSpPO");
        // purPrice = (Number)ob.getAttributes().get("purPrice"); */
        if (ob.getAttributes().get("itmIdPOTax") != null) {
            itmId = ob.getAttributes().get("itmIdPOTax").toString();
        }
        //_log.info("taxAbleAmt :::  "+taxAbleAmt + "pur price  ::::  "+purPrice+" item id  "+itmId);
        _log.info("Itm Id  :::: " + itmId + "   taxAbleAmt :::  " + taxAbleAmt);
        if (itmId != null) {

        } else {
            showFacesMessage("Item Id null . ", "W", false, "F");
            return;
        }
    }

    public void populateTaxAndOcData(ActionEvent actionEvent) {
        // Add event code here...
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer paramUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String tfMode = resolvEl("#{pageFlowScope.ADD_EDIT_MODE}");
        String paramBinChk = resolvEl("#{pageFlowScope.GLBL_ORG_BIN_CHK}");


        /**
         * validation for reworkable quantity
         */

        String chkReworkableN = "N";
        OperationBinding chkRwkable = getBindings().getOperationBinding("chkRWkableQtyPresent");
        chkRwkable.execute();
        if (chkRwkable.getResult() != null) {
            _log.info("chkReworkableN1  :::::::: ");
            chkReworkableN = chkRwkable.getResult().toString();
        }

        _log.info("chkReworkableN1   :  " + chkReworkableN);
        //  if("Y".equals(chkReworkable.getValue().toString())){//if reworkable qty present
        if ("Y".equalsIgnoreCase(chkReworkableN)) {
            showFacesMessage("Some item have reworkable quantity can't apply tax ", "W", false, "F");
            return;
        }


        /**
         * validation for check QC presents
         */

        /*  OperationBinding chkRcvItm=getBindings().getOperationBinding("chkIsAnyItmForQc");
              chkRcvItm.execute();
              String chkQc="Y";
              if(chkRcvItm.getErrors().size()==0 && chkRcvItm.getResult()!=null)
               chkQc = (String)chkRcvItm.getResult();
              if(chkQc.equals("Y")){
               showFacesMessage("Some Item have been Qc Required .Can't apply Tax", "W", false, "F");
                return ;
              }     */

        _log.info("QC REQD--" + checkQcReqd.getValue().toString());
        if ("Y".equalsIgnoreCase(checkQcReqd.getValue().toString())) {
            OperationBinding chkRcvItm = getBindings().getOperationBinding("chkIsAnyItmForQc");
            chkRcvItm.execute();
            String chkQc = "Y";
            if (chkRcvItm.getErrors().size() == 0 && chkRcvItm.getResult() != null)
                chkQc = (String) chkRcvItm.getResult();
            if (chkQc.equals("Y")) {
                showFacesMessage("Some Item have been Qc Required .Can't apply Tax", "W", false, "F");
                return;
                /*  OperationBinding setStatRcptOp = getBindings().getOperationBinding("setRcptStatus");
                        setStatRcptOp.getParamsMap().put("stat", RCPT_STATUS_QCPEND);
                        setStatRcptOp.execute(); */
            }
        }


        /**
         * validation for receipt quantity not presents
         *
         */

        OperationBinding chkRcvd = getBindings().getOperationBinding("checkForRcvdQtyPresent");
        chkRcvd.getParamsMap().put("CldId", paramCldId);
        chkRcvd.getParamsMap().put("slocId", paramSlocId);
        chkRcvd.getParamsMap().put("OrgId", paramOrgId);
        chkRcvd.getParamsMap().put("whId", getWhIdPage().getValue().toString());
        chkRcvd.execute();


        HashSet h = (HashSet) chkRcvd.getResult();
        if (!h.isEmpty()) {
            _log.info("HashSet not empty" + h);

            StringBuilder msg =
                new StringBuilder("<html><body><p>Some items do not have received/rejected/reworkable quantity for the following Documents : <b>" +
                                  h + "</b> </p>"); //
            msg.append("<ul> <li>Click YES to delete.</li>");
            msg.append("<li>Click NO to continue.</li></ul>");
            msg.append("</body></html>");


            msgpop = msg.toString();
            /*    FacesMessage fm=new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg);
           FacesContext.getCurrentInstance().addMessage(null, fm); */
            showPopup(confDlvQtyChkPopUp, true);
            _log.info("After msg popup");
            return;

        }
        OperationBinding populateData = getBindings().getOperationBinding("populateDateTaxAndOc");
        populateData.execute();
        _log.info(" populate date ::::::::: ");
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcptSrcTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(calculationTblBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ocTableBind);

        setTaxPopulate("Y");


    }

    public MMReceiptMtlAMImpl getAm() {
        return (MMReceiptMtlAMImpl) resolvElDC("MMReceiptMtlAMDataControl");

    }

    public void taxRuleVCE(ValueChangeEvent vce) {
        /* if (vce.getNewValue() != null) {
            Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC1}"));
            Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR1}"));
            String p_org_id = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG1}");
            String p_hoOrgId=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            String p_cldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            String taxid1 = vce.getNewValue().toString();


            Integer taxid=null;
            ViewObjectImpl votax = getAm().getLovTaxRuleIdForDispVO();
            votax.setNamedWhereClauseParam("BindSlocId", p_sloc_id);
            votax.setNamedWhereClauseParam("BindCldId", p_cldId);
             votax.setNamedWhereClauseParam("BindHoOrgId",p_hoOrgId );
            votax.setNamedWhereClauseParam("BindTaxRuleDesc", taxid1);
            votax.executeQuery();
            Row[] xx=votax.getFilteredRows("TaxRuleDesc", taxid1);
            _log.info(" row lenth  "+xx.length);
            if(xx.length>0){
                taxid = Integer.parseInt(xx[0].getAttribute("TaxRuleId").toString());
            }
            _log.info("tax rul id");

            if(taxid!=null){
            Row trcurr = getAm().getMmMtlRcptTrVO().getCurrentRow();

            trcurr.setAttribute("CldId", p_cldId);
            trcurr.setAttribute("SlocId", p_sloc_id);
            trcurr.setAttribute("OrgId", p_org_id);
            trcurr.setAttribute("DocId", getAm().getMmMtlRcptSrc().getCurrentRow().getAttribute("DocId"));
            trcurr.setAttribute("WhId", getAm().getMmMtlRcptSrc().getCurrentRow().getAttribute("WhId"));
            trcurr.setAttribute("DocIdSrc", getAm().getMmMtlRcptSrc().getCurrentRow().getAttribute("DocIdSrc"));
            trcurr.setAttribute("DlvSchdlNo", getAm().getMmMtlRcptSrc().getCurrentRow().getAttribute("DlvSchdlNo"));
            trcurr.setAttribute("ItmId", getAm().getMmMtlRcptSrc().getCurrentRow().getAttribute("ItmId"));
            trcurr.setAttribute("ItmUom", getAm().getMmMtlRcptSrc().getCurrentRow().getAttribute("ItmUom"));
            System.out.println("Setting attribute TaxRuleId on VCL="+taxid);
            trcurr.setAttribute("TaxRuleId", taxid);
            String p_doc_id = trcurr.getAttribute("DocId").toString();
            String p_po_item_id = null;
            String p_po_item_uom = null;
            Number p_curr_fctr = new Number(1);
            String taxexm="N";
            if (trcurr.getAttribute("ItmId") != null) {
                p_po_item_id = trcurr.getAttribute("ItmId").toString();
            }
            if(trcurr.getAttribute("TaxExmptFlg") != null)
            {
                taxexm=trcurr.getAttribute("TaxExmptFlg").toString();
                }
            if (trcurr.getAttribute("ItmUom") != null) {
                p_po_item_uom = trcurr.getAttribute("ItmUom").toString();
            }
            Number p_taxable_amount = (Number)trcurr.getAttribute("TaxableAmt");
            trcurr.setAttribute("TaxRuleFlg","I");
            if (getAm().getMmQuot1().getCurrentRow().getAttribute("CurrConvFctr") != null) {
                p_curr_fctr = (Number)getAm().getMmQuot1().getCurrentRow().getAttribute("CurrConvFctr");
            }
            adfLog.info("Tax rule flg=I");

            BigDecimal ret =
                 (BigDecimal)callStoredFunction(NUMBER, "MM.PKG_MM_QUOT.ins_quot_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]{p_sloc_id, p_cldId , p_hoOrgId , p_org_id,p_doc_id,p_po_item_id,p_po_item_uom,taxid,p_user_id,p_taxable_amount,"I",p_curr_fctr,taxexm});

           Number retVal=new Number(0);
             try {
                 retVal = new Number(ret);
             } catch (SQLException e) {
                 adfLog.info("Error while convert BigDecimal to number");
             }
             String flg="N";
            Number zero=new Number(0);
            adfLog.info("Key of TR1 1545="+this.getAm().getMmQuotTr1().getCurrentRow().getKey());

            getAm().getMmQuotTrLines1().executeQuery();
             adfLog.info("Key of TR1 1548="+this.getAm().getMmQuotTr1().getCurrentRow().getKey());

            getAm().getMmQuotTrLines2().executeQuery();
             adfLog.info("Key of TR1 1551="+this.getAm().getMmQuotTr1().getCurrentRow().getKey());

            if(trcurr.getAttribute("TaxAmt")!=null)
           flg=trcurr.getAttribute("TaxAmt").toString();
            if(flg.equals("Y"))
            {
                trcurr.setAttribute("TaxAmt",zero);
                getAm().getMmQuot1().getCurrentRow().setAttribute("QuotTaxAmt_Trans", zero);
            }
            else
            {
            trcurr.setAttribute("TaxAmt", retVal);
                getAm().getMmQuotItm().getCurrentRow().setAttribute("TaxAmtItm_Trans", retVal);
            }

        adfLog.info("Key of TR1 1566="+this.getAm().getMmQuotTr1().getCurrentRow().getKey());

         AdfFacesContext.getCurrentInstance().addPartialTarget(taxTrLineBind);
          adfLog.info("Key of TR1 1569="+this.getAm().getMmQuotTr1().getCurrentRow().getKey());
         trcurr.setAttribute("TaxRuleId", taxid);
            adfLog.info("Key of TR1 1571="+this.getAm().getMmQuotTr1().getCurrentRow().getKey());

        }
        } */

        if (vce.getNewValue() != null) {
            // Integer taxid = Integer.parseInt(vce.getNewValue().toString());

            //  Row trcurr = getAm().getMmDrftPoTr1().getCurrentRow();
            Row trcurr = getAm().getMmMtlRcptTrVO().getCurrentRow();
            Row itmcurr = getAm().getMmMtlRcptItm().getCurrentRow();
            Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
            String p_org_id = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String p_hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            String p_cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            String taxid1 = vce.getNewValue().toString();
            Integer taxid = null;
            ViewObjectImpl votax = getAm().getLovTaxRuleIdForNmVO();
            votax.setNamedWhereClauseParam("BindSlocId", p_sloc_id);
            votax.setNamedWhereClauseParam("BindCldId", p_cldId);
            votax.setNamedWhereClauseParam("BindHoOrgId", p_hoOrgId);
            votax.setNamedWhereClauseParam("BindTaxRuleDesc", taxid1);
            votax.executeQuery();
            Row[] xx = votax.getFilteredRows("TaxRuleDesc", taxid1);
            _log.info(" row lenth  " + xx.length);
            if (xx.length > 0) {
                taxid = Integer.parseInt(xx[0].getAttribute("TaxRuleId").toString());
            }
            _log.info("tax rul id");

            if (taxid != null) {
                String p_doc_id = trcurr.getAttribute("DocId").toString();
                String p_docIdSrc = trcurr.getAttribute("DocIdSrc").toString();
                String p_WhId = trcurr.getAttribute("WhId").toString();
                String dlvSchdlNo = trcurr.getAttribute("DlvSchdlNo").toString();
                // String p_docIdSrc = trcurr.getAttribute("DocIdSrc").toString();
                String p_item_id = null;
                String p_item_uom = null;
                Number p_curr_fctr = new Number(1);
                String taxexm = "N";
                if (trcurr.getAttribute("ItmId") != null) {
                    p_item_id = trcurr.getAttribute("ItmId").toString();
                }
                if (trcurr.getAttribute("TaxExmptFlg") != null) {
                    taxexm = trcurr.getAttribute("TaxExmptFlg").toString();
                }
                if (trcurr.getAttribute("ItmUom") != null) {
                    p_item_uom = trcurr.getAttribute("ItmUom").toString();
                }
                Number p_taxable_amount = (Number) itmcurr.getAttribute("TaxableAmtSp");
                // Number p_taxable_amount = (Number) trcurr.getAttribute("TaxableAmtSp");
                trcurr.setAttribute("TaxRuleFlg", "I");
                if (getAm().getMmMtlRcpt().getCurrentRow().getAttribute("CurrConvFctr") != null) {
                    p_curr_fctr =
                        (Number) ((Number) getAm().getMmMtlRcpt().getCurrentRow().getAttribute("CurrConvFctr")).round(getGlblRoundCurrRateDigit());
                }
                _log.info("Tax rule flg=I");
                Number taxableAmtOrg = (Number) itmcurr.getAttribute("TaxableAmtSpOrig");

                /* BigDecimal ret =
                (BigDecimal)callStoredFunction(NUMBER, "MM.PKG_MM_PO.ins_drft_po_tr_lines(?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                               new Object[] { p_sloc_id, p_cldId, p_hoOrgId, p_org_id, p_doc_id,
                                                              p_po_item_id, p_po_item_uom, taxid, p_user_id,
                                                              p_taxable_amount, "I", p_curr_fctr, taxexm }); */

                BigDecimal ret =
                    (BigDecimal) (callStoredFunction(Types.NUMERIC,
                                                     "MM.INS_RCPT_TR_LINES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                                     p_sloc_id, p_cldId, p_hoOrgId, p_org_id, p_WhId, p_doc_id,
                                                     p_docIdSrc, dlvSchdlNo, p_item_id, p_item_uom, taxid,
                                                     taxableAmtOrg, p_taxable_amount, "I", p_curr_fctr, taxexm, "N"
                }));


                Number retVal = zero;
                if (ret != null) {
                    try {
                        retVal = new Number(ret);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                _log.info(retVal + "     retVal  " + ret);

                BigDecimal retRec =
                    (BigDecimal) (callStoredFunction(Types.NUMERIC, "MM.MM_GET_REC_TAX_RCPT(?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                                     p_cldId, p_sloc_id, p_hoOrgId, p_org_id, p_WhId, p_doc_id,
                                                     p_docIdSrc, dlvSchdlNo, p_item_id, p_item_uom, taxexm
                }));

                Number retRecN = new Number(0);
                if (retRec != null) {
                    try {
                        retRecN = new Number(retRec);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                _log.info(retRec + "retRecN   " + retRecN);
                getAm().getMmMtlRcptItm().getCurrentRow().setAttribute("RecTaxAmtSp",
                                                                       retRecN.round(getGlblRoundAmtDigit()));
                getAm().getMmMtlRcptItm().getCurrentRow().setAttribute("RecTaxAmtBs",
                                                                       (((Number) retRecN.round(getGlblRoundAmtDigit())).multiply(p_curr_fctr)).round(getGlblRoundAmtDigit()));
                // Number retVal = new Number(ret);
                String flg = "N";
                Number zero = new Number(0);
                // getAm().getMmDrftPoTrLines1().executeQuery();
                getAm().getMmMtlRcptTrLinesVO().executeQuery();
                if (trcurr.getAttribute("TaxAmt") != null)
                    flg = trcurr.getAttribute("TaxAmt").toString();
                if (flg.equals("Y")) {
                    trcurr.setAttribute("TaxAmt", zero);
                    // getAm().getMmDrftPo().getCurrentRow().setAttribute("TransTotalTax", zero);
                } else {
                    trcurr.setAttribute("TaxAmt", retVal.round(getGlblRoundAmtDigit()));
                    trcurr.setAttribute("TaxAmtBs",
                                        (((Number) retVal.round(getGlblRoundAmtDigit())).multiply(p_curr_fctr)).round(getGlblRoundAmtDigit()));
                    //   if (p_tax_rule_flg.equalsIgnoreCase("I")) {
                    getAm().getMmMtlRcptItm().getCurrentRow().setAttribute("TotTaxAmtSp",
                                                                           retVal.round(getGlblRoundAmtDigit()));
                    getAm().getMmMtlRcptItm().getCurrentRow().setAttribute("TotTaxAmtBs",
                                                                           (((Number) retVal.round(getGlblRoundAmtDigit())).multiply(p_curr_fctr)).round(getGlblRoundAmtDigit()));
                    /*  } else if (p_tax_rule_flg.equalsIgnoreCase("P")) {
                getAm().getMmDrftPo().getCurrentRow().setAttribute("TransTotalTax", retVal);
            } */

                }
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(taxTrLineBind);

        }
    }

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {

            st = getAm().getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                    // System.out.println(bindVars[z] + "z");
                }
            }
            st.executeUpdate();

            return st.getObject(1);
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void taxCalcDialogListener(DialogEvent dialogEvent) {
        getAm().getDBTransaction().postChanges();
    }

    public void taxPopUpCancelListener(PopupCanceledEvent popupCanceledEvent) throws SQLException {


    }

    public void setTaxPopulate(String taxPopulate) {
        this.taxPopulate = taxPopulate;
    }

    public String getTaxPopulate() {
        return taxPopulate;
    }

    public void deleteOcAction(ActionEvent actionEvent) {
        OperationBinding populateData = getBindings().getOperationBinding("Delete3");
        populateData.execute();
    }

    public void setTransOcAmtBind(RichInputText transOcAmtBind) {
        this.transOcAmtBind = transOcAmtBind;
    }

    public RichInputText getTransOcAmtBind() {
        return transOcAmtBind;
    }

    public void setOcDiscriptbind(RichSelectOneChoice ocDiscriptbind) {
        this.ocDiscriptbind = ocDiscriptbind;
    }

    public RichSelectOneChoice getOcDiscriptbind() {
        return ocDiscriptbind;
    }

    public void taxAbleAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number ob = (Number) object;
            if (zero.compareTo(ob) == 1) {
                showFacesMessage("Taxable Amount can't be negative.", "E", false, "V");
            }
        }
    }

    public void populateRcptCalc(ActionEvent actionEvent) {
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        Integer paramUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        // Object obj = invcTypeBindVar.getValue();

        OperationBinding popCalc = getBindings().getOperationBinding("populateCalculations");
        popCalc.getParamsMap().put("p_cld_id", paramCldId);
        popCalc.getParamsMap().put("p_sloc_id", paramSlocId);
        popCalc.getParamsMap().put("p_org_id", paramOrgId);
        popCalc.getParamsMap().put("p_usr_id", paramUsrId);
        popCalc.execute();
    }

    public void taxAmtVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            _log.info("Inside value change listenerrr");
            String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            Integer paramUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
            String paramHoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            Number taxAmt = (Number) vce.getNewValue();
            ViewObjectImpl rcptVo = getAm().getMmMtlRcpt();
            ViewObjectImpl rcptItmVo = getAm().getMmMtlRcptItm();
            ViewObjectImpl rcptTrVo = getAm().getMmMtlRcptTrVO1();
            Row rrItm = rcptItmVo.getCurrentRow();
            Row currRcpt = rcptVo.getCurrentRow();
            String docId = rrItm.getAttribute("DocId").toString();
            String whId = rrItm.getAttribute("WhId").toString();
            String docIdSrc = rrItm.getAttribute("DocIdSrc").toString();
            Integer dlvSchdlNo = Integer.parseInt(rrItm.getAttribute("DlvSchdlNo").toString());
            String itmId = rrItm.getAttribute("ItmId").toString();
            String itmUom = rrItm.getAttribute("ItmUom").toString();
            Number currencyFctr =
                (Number) ((Number) currRcpt.getAttribute("CurrConvFctr")).round(getGlblRoundCurrRateDigit());
            Number taxableAmtOrg = (Number) rrItm.getAttribute("TaxableAmtSpOrig");
            String taxRuleFlg = "I";
            String taxExpt = "N";
            _log.info("docId  : " + docId + " whId " + whId + " docIdSrc " + docIdSrc + " dlvSchdlNo " + dlvSchdlNo +
                      " itmId " + itmId + " itmUom " + itmUom);
            //(((Number)retVal.round(getGlblRoundAmtDigit())).multiply(p_curr_fctr)).round(getGlblRoundAmtDigit())

            //rrItm.setAttribute("TaxableAmtBs", taxAmt.multiply(currencyFctr));
            rrItm.setAttribute("TaxableAmtBs",
                               (((Number) taxAmt.round(getGlblRoundAmtDigit())).multiply(currencyFctr)).round(getGlblRoundAmtDigit()));
            RowQualifier rqTr = new RowQualifier(rcptTrVo);
            rqTr.setWhereClause("CldId ='" + paramCldId + "' and SlocId =" + paramSlocId + " and OrgId ='" +
                                paramOrgId + "' and DocId ='" + docId + "' and WhId ='" + whId + "' and DocIdSrc ='" +
                                docIdSrc + "' and DlvSchdlNo =" + dlvSchdlNo + " and ItmId ='" + itmId +
                                "' and ItmUom ='" + itmUom + "' ");
            Row[] rr = rcptTrVo.getFilteredRows(rqTr);
            _log.info(rqTr.getExprStr() + "  trmmmmmmm  " + rr.length);
            if (rr.length > 0) {
                if (rr[0].getAttribute("TaxRuleId") != null) {
                    Integer taxRuleId = Integer.parseInt(rr[0].getAttribute("TaxRuleId").toString());
                    if (taxRuleId != null) {
                        _log.info(" taxRuleId : " + taxRuleId);
                        BigDecimal ret =
                            (BigDecimal) (callStoredFunction(Types.NUMERIC,
                                                             "MM.INS_RCPT_TR_LINES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                                             paramSlocId, paramCldId, paramHoOrgId, paramOrgId, whId,
                                                             docId, docIdSrc, dlvSchdlNo, itmId, itmUom, taxRuleId,
                                                             taxableAmtOrg, taxAmt, taxRuleFlg, currencyFctr, taxExpt,
                                                             "Y"
                        }));


                        Number retVal = new Number(0);
                        if (ret != null) {
                            try {
                                retVal = new Number(ret);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        _log.info(" retVal : " + retVal);
                        //(((Number)retVal.round(getGlblRoundAmtDigit())).multiply(p_curr_fctr)).round(getGlblRoundAmtDigit())
                        //                        rrItm.setAttribute("TotTaxAmtSp", retVal);
                        //                        rrItm.setAttribute("TotTaxAmtBs", retVal.multiply(currencyFctr));

                        rrItm.setAttribute("TotTaxAmtSp", retVal.round(getGlblRoundAmtDigit()));
                        rrItm.setAttribute("TotTaxAmtBs",
                                           (((Number) retVal.round(getGlblRoundAmtDigit())).multiply(currencyFctr)).round(getGlblRoundAmtDigit()));

                        BigDecimal retRec =
                            (BigDecimal) (callStoredFunction(Types.NUMERIC,
                                                             "MM.MM_GET_REC_TAX_RCPT(?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                                             paramCldId, paramSlocId, paramHoOrgId, paramOrgId, whId,
                                                             docId, docIdSrc, dlvSchdlNo, itmId, itmUom, taxExpt
                        }));

                        Number retRecN = new Number(0);
                        if (retRec != null) {
                            try {
                                retRecN = new Number(retRec);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        //(((Number)retVal.round(getGlblRoundAmtDigit())).multiply(p_curr_fctr)).round(getGlblRoundAmtDigit())
                        _log.info(retRec + " retRecN : " + retRecN);
                        //                        rrItm.setAttribute("RecTaxAmtSp", retRecN);
                        //                        rrItm.setAttribute("RecTaxAmtBs", retRecN.multiply(currencyFctr));

                        rrItm.setAttribute("RecTaxAmtSp", retRecN.round(getGlblRoundAmtDigit()));
                        rrItm.setAttribute("RecTaxAmtBs",
                                           (((Number) retRecN.round(getGlblRoundAmtDigit())).multiply(currencyFctr)).round(getGlblRoundAmtDigit()));


                        getAm().getMmMtlRcptTrVO().executeQuery();
                        getAm().getMmMtlRcptTrLinesVO().executeQuery();
                        AdfFacesContext.getCurrentInstance().addPartialTarget(calculationTblBind);

                    }
                }
            }
        }
    }

    public void setCalculationTblBind(RichTable calculationTblBind) {
        this.calculationTblBind = calculationTblBind;
    }

    public RichTable getCalculationTblBind() {
        return calculationTblBind;
    }

    public void setTaxTrLineBind(RichTable taxTrLineBind) {
        this.taxTrLineBind = taxTrLineBind;
    }

    public RichTable getTaxTrLineBind() {
        return taxTrLineBind;
    }

    public void reqrmentAreaVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            OperationBinding opbind = getBindings().getOperationBinding("setTransValueToNull");
            opbind.execute();
            //  eoIdSrcBinding.setValue("");
            docNoPageVar.setValue("");
            docDate.setValue("");
            itmNameBindVar.setValue("");
            uomBindVar.setValue("");
            uomBindVar.resetValue();
            itmQty.setValue("");
            //  schdlNoBind.setValue("");schdlNoBind.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(schdlNoBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmNameBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(uomBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmQty);
            AdfFacesContext.getCurrentInstance().addPartialTarget(docNoPageVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(docDate);
            // AdfFacesContext.getCurrentInstance().addPartialTarget(eoIdSrcBinding);
        }
    }


    public void setBinQtyRejBsBind(RichInputText binQtyRejBsBind) {
        this.binQtyRejBsBind = binQtyRejBsBind;
    }

    public RichInputText getBinQtyRejBsBind() {
        return binQtyRejBsBind;
    }

    public void setBinQtyRwkBsBind(RichInputText binQtyRwkBsBind) {
        this.binQtyRwkBsBind = binQtyRwkBsBind;
    }

    public RichInputText getBinQtyRwkBsBind() {
        return binQtyRwkBsBind;
    }

    public void setBarCodePopBinding(RichPopup barCodePopBinding) {
        this.barCodePopBinding = barCodePopBinding;
    }

    public RichPopup getBarCodePopBinding() {
        return barCodePopBinding;
    }

    public void addItmUsingBarCodeAL(ActionEvent actionEvent) {
        showPopup(barCodePopBinding, true);
    }

    public void addItemToBarCodeTblAL(ActionEvent actionEvent) {
        if (itmIdOnPopBinding.getValue() != null && itmIdOnPopBinding.getValue().toString().length() > 0) {

            if ((srLzItmChkOnPopBinding.getValue() != null &&
                 srLzItmChkOnPopBinding.getValue().toString().equalsIgnoreCase("N") &&
                 srNoOnPopBinding.getValue() == null) ||
                (srLzItmChkOnPopBinding.getValue() != null &&
                 srLzItmChkOnPopBinding.getValue().toString().equalsIgnoreCase("Y") &&
                 srNoOnPopBinding.getValue() != null)) {
                if ((itmChkOnPopBinding.getValue() != null &&
                     itmChkOnPopBinding.getValue().toString().equalsIgnoreCase("N") &&
                     delvQtyOnPopBinding.getValue() != null) ||
                    (itmChkOnPopBinding.getValue() != null &&
                     itmChkOnPopBinding.getValue().toString().equalsIgnoreCase("Y") &&
                     delvQtyOnPopBinding.getValue() == null)) {
                    if (rcptQtyOnPopBinding.getValue() != null &&
                        rcptQtyOnPopBinding.getValue().toString().length() > 0) {
                        if (rejQtyOnPopBinding.getValue() != null &&
                            rejQtyOnPopBinding.getValue().toString().length() > 0) {
                            if (reWrkQtyOnPopBinding.getValue() != null &&
                                reWrkQtyOnPopBinding.getValue().toString().length() > 0) {

                                OperationBinding binding = getBindings().getOperationBinding("chkItmIdInPo");
                                binding.getParamsMap().put("ItmId", itmIdOnPopBinding.getValue());
                                binding.execute();
                                if (binding.getErrors().isEmpty() && binding.getResult() != null) {
                                    String rslt = binding.getResult().toString();
                                    if (rslt.equalsIgnoreCase("N")) {
                                        OperationBinding op = getBindings().getOperationBinding("updateItmQty");
                                        op.getParamsMap().put("ItmId", itmIdOnPopBinding.getValue());
                                        op.execute();
                                        if (op.getResult() != null && op.getResult().toString().equals("N")) {
                                            getBindings().getOperationBinding("insertIntoBarCodeTbl").execute();

                                        }

                                    } else if (rslt.equalsIgnoreCase("Y")) {
                                        FacesMessage message = new FacesMessage("This Item not found in current PO!!");
                                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(itmIdOnPopBinding.getClientId(), message);
                                    }
                                }
                            } else {
                                FacesMessage message = new FacesMessage("Reworkable Quantity Can not be left blank !!");
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(reWrkQtyOnPopBinding.getClientId(), message);

                            }
                        } else {
                            FacesMessage message = new FacesMessage("Rejected Quantity Can not be left blank !!");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(rejQtyOnPopBinding.getClientId(), message);
                        }
                    } else {
                        FacesMessage message = new FacesMessage("Receipt Quantity Can not be left blank !!");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(rcptQtyOnPopBinding.getClientId(), message);

                    }
                } else {
                    FacesMessage message = new FacesMessage("Delivered Quantity Can not be left blank !!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(delvQtyOnPopBinding.getClientId(), message);

                }
            } else {
                FacesMessage message = new FacesMessage("Serial No. Can not be left blank for serialized item !!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(srNoOnPopBinding.getClientId(), message);

            }

        } else {
            FacesMessage message = new FacesMessage("Please Enter an Item Id !!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(itmIdOnPopBinding.getClientId(), message);
        }
    }

    public void setItmIdOnPopBinding(RichInputListOfValues itmIdOnPopBinding) {
        this.itmIdOnPopBinding = itmIdOnPopBinding;
    }

    public RichInputListOfValues getItmIdOnPopBinding() {
        return itmIdOnPopBinding;
    }

    public void setSrNoOnPopBinding(RichInputText srNoOnPopBinding) {
        this.srNoOnPopBinding = srNoOnPopBinding;
    }

    public RichInputText getSrNoOnPopBinding() {
        return srNoOnPopBinding;
    }

    public void setDelvQtyOnPopBinding(RichInputText delvQtyOnPopBinding) {
        this.delvQtyOnPopBinding = delvQtyOnPopBinding;
    }

    public RichInputText getDelvQtyOnPopBinding() {
        return delvQtyOnPopBinding;
    }

    public void setRejQtyOnPopBinding(RichInputText rejQtyOnPopBinding) {
        this.rejQtyOnPopBinding = rejQtyOnPopBinding;
    }

    public RichInputText getRejQtyOnPopBinding() {
        return rejQtyOnPopBinding;
    }

    public void setReWrkQtyOnPopBinding(RichInputText reWrkQtyOnPopBinding) {
        this.reWrkQtyOnPopBinding = reWrkQtyOnPopBinding;
    }

    public RichInputText getReWrkQtyOnPopBinding() {
        return reWrkQtyOnPopBinding;
    }

    public void setRcptQtyOnPopBinding(RichInputText rcptQtyOnPopBinding) {
        this.rcptQtyOnPopBinding = rcptQtyOnPopBinding;
    }

    public RichInputText getRcptQtyOnPopBinding() {
        return rcptQtyOnPopBinding;
    }

    public void setSrLzItmChkOnPopBinding(RichInputText srLzItmChkOnPopBinding) {
        this.srLzItmChkOnPopBinding = srLzItmChkOnPopBinding;
    }

    public RichInputText getSrLzItmChkOnPopBinding() {
        return srLzItmChkOnPopBinding;
    }

    public void setItmChkOnPopBinding(RichInputText itmChkOnPopBinding) {
        this.itmChkOnPopBinding = itmChkOnPopBinding;
    }

    public RichInputText getItmChkOnPopBinding() {
        return itmChkOnPopBinding;
    }

    public void itmIdOnPopVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0) {
            OperationBinding op = getBindings().getOperationBinding("setItmUom");
            op.getParamsMap().put("itmId", valueChangeEvent.getNewValue());
            op.execute();
        }
    }

    public void barCodePopDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("OK")) {
            System.out.println("inside OK --");
            getBindings().getOperationBinding("updateRcptItmQty").execute();
        }
    }

    public void deleteBarCodeItmAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("Delete4").execute();
        getBindings().getOperationBinding("Execute7").execute();
    }

    public void setOcTableBind(RichTable ocTableBind) {
        this.ocTableBind = ocTableBind;
    }

    public RichTable getOcTableBind() {
        return ocTableBind;
    }

    public void addAllSerialNoAction(ActionEvent actionEvent) {
        OperationBinding opc = getBindings().getOperationBinding("insertAllSerialNo");
        opc.execute();
    }

    public void setUomCnvrFactBind(RichInputText uomCnvrFactBind) {
        this.uomCnvrFactBind = uomCnvrFactBind;
    }

    public RichInputText getUomCnvrFactBind() {
        return uomCnvrFactBind;
    }

    public String getuomFactorCnvr() {
        String retVal = "Y";
        if (uomCnvrFactBind.getValue() != null) {
            if (one.compareTo((Number) (uomCnvrFactBind.getValue())) == 0) {
                retVal = "N";
            }
        }
        return retVal;
    }

    public void MfgDateVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            try {
                _log.info("vce.getNewValue(): " + vce.getNewValue());
                OperationBinding opc = getBindings().getOperationBinding("setItmLotExpiryDate");
                opc.getParamsMap().put("dt", (oracle.jbo.domain.Timestamp) vce.getNewValue());
                opc.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Timestamp getCurrDate() {

        if (rcptDate.getValue() != null) {
            try {
                _log.info("trunk" + StaticValue.getTruncatedDt((Timestamp) rcptDate.getValue()));

                java.sql.Date stDt = null;
                try {
                    stDt = StaticValue.getTruncatedDt((Timestamp) rcptDate.getValue()).dateValue();
                } catch (SQLException e) {
                }
                Calendar calStDate = Calendar.getInstance();
                calStDate.setTime(stDt);

                calStDate.add(Calendar.DATE, 1);
                _log.info(" add date " + StaticValue.getDtWithTimestamp(new Timestamp(calStDate.getTime())));

                //_log.info("trunk" + StaticValue.addOrSubDaysWithTimestamp((Timestamp) rcptDate.getValue()));


                _log.info("rcptDate.getValue()  " + rcptDate.getValue());
                Timestamp dt1 = (Timestamp) rcptDate.getValue();

                _log.info(" date value" + dt1.dateValue());
                _log.info(" date value1" + new oracle.jbo.domain.Timestamp(new Date(dt1.dateValue())));
                return StaticValue.getTruncatedDt((Timestamp) rcptDate.getValue());
                // return StaticValue.getDtWithTimestamp(new Timestamp(calStDate.getTime()));
            } catch (Exception e) {

                e.printStackTrace();
                return (Timestamp) rcptDate.getValue();
            }
        } else {
            return new Timestamp(System.currentTimeMillis());
            //return new oracle.jbo.domain.Timestamp();
        }
    }


    public void mfgDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Timestamp mfgDt = (Timestamp) object;
            if (rcptDate.getValue() != null) {
                _log.info(mfgDt + "      " + rcptDate.getValue());
                if (mfgDt.toString().compareTo(rcptDate.getValue().toString()) == 1) {
                    showFacesMessage("Date must be before or on " + rcptDate.getValue(), "E", false, "V");
                }
            }
        }


    }

    public void expiryDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Timestamp expDt = (Timestamp) object;
            _log.info(expDt + "    ----  ");
            if (mfgDateBind.getValue() != null) {
                _log.info(expDt + "  ----    " + mfgDateBind.getValue());
                if (mfgDateBind.getValue().toString().compareTo(expDt.toString()) == 1) {
                    showFacesMessage("Date must be on or after " + rcptDate.getValue(), "E", false, "V");
                }
            }
        }


    }

    public void setMfgDateBind(RichInputDate mfgDateBind) {
        this.mfgDateBind = mfgDateBind;
    }

    public RichInputDate getMfgDateBind() {
        return mfgDateBind;
    }

    public void generateAllLotNoAction(ActionEvent actionEvent) {
        // Add event code here...
        _log.info("generateAllLotNoAction :");
        OperationBinding opc = getBindings().getOperationBinding("addLotForAllItem");
        opc.execute();

    }

    public void binVCL(ValueChangeEvent vce) {
        // Add event code here...
        if (vce.getNewValue() != null) {
            //checkBinCapacityBalanceQty
            Number retVal = new Number(0);
            OperationBinding opChk = getBindings().getOperationBinding("checkBinCapacityBalanceQty");
            opChk.getParamsMap().put("binId", vce.getNewValue());
            opChk.execute();
            if (opChk.getResult() != null) {
                retVal = (Number) opChk.getResult();
            }
            if (retVal.compareTo(new Number(-1)) == 0) {
                _log.info("Error ocurree");

            } else if (retVal.compareTo(new Number(-2)) == 0) {
                showPopup(binCnfrnPopup, true);
                _log.info("other item ocurree");
            } else if (retVal.compareTo(new Number(-3)) == 0) {
                _log.info("capacity not define item ocurree");
            } else if (retVal.compareTo(new Number(-4)) == 0) {
                _log.info("No itm for bin not define item ocurree");
            }
        }
    }

    public void binCnfrnYesAction(ActionEvent actionEvent) {
        // Add event code here...
        binCnfrnPopup.hide();
    }

    public void binCnfrnNoAction(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding opChk = getBindings().getOperationBinding("setCurrentBinNull");
        opChk.execute();
        binNoBinBind.setValue(null);
        binCnfrnPopup.hide();
    }

    public void setBinCnfrnPopup(RichPopup binCnfrnPopup) {
        this.binCnfrnPopup = binCnfrnPopup;
    }

    public RichPopup getBinCnfrnPopup() {
        return binCnfrnPopup;
    }


    public void setSrNoForNonSrItm(RichInputText srNoForNonSrItm) {
        this.srNoForNonSrItm = srNoForNonSrItm;
    }

    public RichInputText getSrNoForNonSrItm() {
        return srNoForNonSrItm;
    }

    public String getInvcCopyReq() {
        String ret = "N";
        OperationBinding opChk = getBindings().getOperationBinding("chkInvoiceCopyRequired");
        opChk.execute();
        if (opChk.getResult() != null) {
            ret = opChk.getResult().toString();
        }
        return ret;
    }


    public void fileDescriptionTabDisclosureListener(DisclosureEvent disclosureEvent) {
        // Add event code here...
        
        OperationBinding opChk = getBindings().getOperationBinding("setBindVarFrRmrkView");
        opChk.execute();
    }
    
    public String getLotAutoGen(){
        
        OperationBinding opChk = getBindings().getOperationBinding("chkLotAutoGen");
        opChk.execute();
        if(opChk.getResult()!=null){
            if("Y".equalsIgnoreCase(opChk.getResult().toString())){
                return "Y";
            }else{
            return "N";
            }
        }else{
        return "Y";
        }
    }
    
    public String getWfHistryView(){
        OperationBinding wfFlowView = getBindings().getOperationBinding("viewWFData");
        wfFlowView.execute();
        if(wfFlowView.getResult()!=null){
            return wfFlowView.getResult().toString();
        }else{
            return "Document Not In WF"; 
        }
    }
}

