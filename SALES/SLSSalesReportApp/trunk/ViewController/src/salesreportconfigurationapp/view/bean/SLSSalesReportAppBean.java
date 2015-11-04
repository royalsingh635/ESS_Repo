package salesreportconfigurationapp.view.bean;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ViewObject;
import oracle.jbo.domain.Timestamp;

import org.apache.myfaces.trinidad.event.DisclosureEvent;

import salesreportconfigurationapp.model.services.SaleReportAMImpl;


public class SLSSalesReportAppBean {
    private RichLink taxLinkBind;
    private RichSelectBooleanCheckbox taxCBBind;

    public void setInstallLink(Boolean InstallLink) {
        this.InstallLink = InstallLink;
    }

    public Boolean getInstallLink() {
        return InstallLink;
    }

    public void setInstall(Boolean Install) {
        this.Install = Install;
    }

    public Boolean getInstall() {
        return Install;
    }
    private RichShowDetailItem dsTabBind;
    private RichLink packCustSummaryLink;
    private RichLink packCustDetailLink;
    private RichLink packPrdGrpSummLink;
    private RichLink packCustPrdGrpDetail;
    private RichLink packOtherRegisterLink;
    private RichLink packSummaryLink;
    private RichLink packItemSummLink;
    private RichLink packItemDetailLink;
    private RichSelectBooleanCheckbox packCustSummCBBind;
    private RichSelectBooleanCheckbox packCustDetailCBBind;
    private RichSelectBooleanCheckbox packPrdGrpSummCBBind;
    private RichSelectBooleanCheckbox packPrdGrpDetailCBBind;
    private RichSelectBooleanCheckbox packRegisterCBBind;
    private RichSelectBooleanCheckbox packSummaryCBBind;
    private RichSelectBooleanCheckbox packItemSummaryCBBind;
    private RichSelectBooleanCheckbox packItemDetailCBBind;
    private RichShowDetailItem packTabBind;
    private RichSelectBooleanCheckbox invRegCBBind;
    private RichLink invRegCBLink;
    private RichSelectBooleanCheckbox custPriceCBBind;
    private RichLink custPriceLinkBind;
    private RichSelectBooleanCheckbox pickBarDetCBBind;
    private RichInputListOfValues oppNoLOVBind;
    private RichInputListOfValues quotNoLOVBind;
    private RichInputListOfValues orderLOVBind;
    private RichInputListOfValues pickNoLOVBind;
    private RichInputListOfValues packNoLOVBind;
    private RichInputListOfValues shipNoLOVBind;
    private RichInputListOfValues invoiceNoLOVBind;
    private RichInputListOfValues rmaNoLOVBinding;
    private RichInputListOfValues rmaNoLOVBind;
    private RichLink invRegSummCBBind;
    private RichSelectBooleanCheckbox invRegSummCB;
    private RichLink pickDetLinkBind;
    private RichSelectBooleanCheckbox suppRegInvCBBind;
    private RichLink intmRegLinkBind;
    private RichSelectBooleanCheckbox intmRegisterCBBind;
    private RichLink suppRegLinkBind;
    private RichInputComboboxListOfValues intmNoLOVBind;
    private RichSelectBooleanCheckbox custWiseGrpCBBind;
    private RichLink custGrpLinkBind;
    private RichInputListOfValues toInvTransBind;
    private RichInputListOfValues fromInvLOVBind;
    private RichSelectBooleanCheckbox ordAmtCBBind;
    private RichLink ordAmdLinkBind;
    private RichSelectBooleanCheckbox invVehicleSummCBBind;
    private RichLink invvehLinkBind;
    private RichSelectBooleanCheckbox installationCBBind;
    private RichLink installationLinkBind;

    public void setWhSummLink(Boolean whSummLink) {
        this.whSummLink = whSummLink;
    }

    public Boolean getWhSummLink() {
        return whSummLink;
    }

    public void setWhDetLink(Boolean whDetLink) {
        this.whDetLink = whDetLink;
    }

    public Boolean getWhDetLink() {
        return whDetLink;
    }

    /**
     * Following variables are use for enabling or disabling the links present on page
     */
    private Boolean custwiseDetailLink = false;
    private Boolean custwiseSummaryLink = false;
    private Boolean custPriceLink = false;
    private Boolean salesmanDetailLink = false;
    private Boolean salesmanSummaryLink = false;
    private Boolean productgroupwiseDetailLink = false;
    private Boolean productgroupSummaryLink = false;
    private Boolean itmDetailLink = false;
    private Boolean itmSummaryLink = false;
    private Boolean otherRegisterLink = false;
    private Boolean pickBarLink = false;
    private Boolean otherRegSummLink = false;
    private Boolean otherPendingLink = false;
    private Boolean OrderTrackingLink = false;
    private Boolean whSummLink = false;
    private Boolean whDetLink = false;
    private Boolean suppRegLink = false;
    private Boolean intmRegLink = false;
    private Boolean InstallLink = false;
    private Boolean BarLink = false;
    private Boolean CustGrpLink = false;
    private Boolean OrderAmdLink = false;
    private Boolean InvVehLink = false;
    private Boolean TaxLink = false;
    private RichSelectBooleanCheckbox marginBind;
    private RichSelectBooleanCheckbox downloadedReptBind;
    private RichLink downloadReportLink;
    private RichSelectBooleanCheckbox salesOrderPendingCB;
    private RichSelectBooleanCheckbox salesOrderPendingCBBind;
    private RichLink orderPendingLinkBind;
    private RichInputListOfValues attrTypePgBind;
    private RichInputComboboxListOfValues attrValPgBind;
    private RichInputText attrIdPgBind;
    private RichInputListOfValues orderNoCBBind;
    private RichSelectBooleanCheckbox dsCustSummCBBind;
    private RichSelectBooleanCheckbox dsCustDetCBBind;
    private RichSelectBooleanCheckbox dsPrdGrpSummCBBind;
    private RichSelectBooleanCheckbox dsPrdGrpDetCBBind;
    private RichSelectBooleanCheckbox dsWhSummCBBind;
    private RichSelectBooleanCheckbox dsWhDetCBBind;
    private RichSelectBooleanCheckbox dsItmSummCBBind;
    private RichSelectBooleanCheckbox dsItmDetCBBind;
    private RichSelectOneChoice dsOrderNoLOVBind;
    private RichSelectOneChoice dsWhLOVBind;
    private RichLink dsCustSummLink;
    private RichLink dsCustDetLink;
    private RichLink dsPrdGrpSummLink;
    private RichLink dsPrdGrpDetLink;
    private RichLink dsWhSummLink;
    private RichLink dsWhDetLink;
    private RichLink dsItmSummLink;
    private RichLink dsItmDetLink;
    List<String> list = null;

    public void setAt(String at) {
        this.at = at;
    }

    public String getAt() {
        return at;
    }
    String attribute1 = "test";
    String attribute2 = "test";
    String attribute3 = "test";
    String attribute4 = "test";
    String attribute5 = "test";
    String at = "";
    private RichPanelGroupLayout iteratorPGLBind;

    public void setAttribute1(String attribute1) {
        this.attribute1 = "test";
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = "test";
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = "test";
    }

    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = "test";
    }

    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = "test";
    }

    public String getAttribute5() {
        return attribute5;
    }


    public void setOrderTracking(Boolean OrderTracking) {
        this.OrderTracking = OrderTracking;
    }

    public Boolean getOrderTracking() {
        return OrderTracking;
    }

    public void setDownloadReportLink(Boolean DownloadReportLink) {
        this.DownloadReportLink = DownloadReportLink;
    }

    public Boolean getDownloadReportLink1() {
        return DownloadReportLink;
    }
    private Boolean statusLink = false;
    private Boolean DCRegisterLink = false;
    private Boolean DCProductiveSalonLink = false;
    private Boolean DownloadReportLink = false;

    /*  /**
     * For Bottom tab Links


    private Boolean btcustwiseDetailLink = false;
    private Boolean btcustwiseSummaryLink = false;
    private Boolean btsalesmanDetailLink = false;
    private Boolean btsalesmanSummaryLink = false;
    private Boolean btproductgroupwiseDetailLink = false;
    private Boolean btproductgroupSummaryLink = false;
    private Boolean btitmDetailLink = false;
    private Boolean btitmSummaryLink = false; */


    /**
     * Following variables are used for checking condition for checkBox present on page
     */
    private Boolean custwiseDetail = false;
    private Boolean custwiseSummary = false;
    private Boolean custPrice = false;
    private Boolean salesmanDetail = false;
    private Boolean salesmanSummary = false;
    private Boolean productgroupwiseDetail = false;
    private Boolean productgroupSummary = false;
    private Boolean itmDetail = false;
    private Boolean itmSummary = false;
    private Boolean otherRegister = false;
    private Boolean pickBar = false;
    private Boolean otherRegSumm = false;
    private Boolean otherPending = false;
    private Boolean whSumm = false;
    private Boolean whDet = false;
    private Boolean OrderTracking = false;
    private Boolean status = false;
    private Boolean suppReg = false;
    private boolean intmReg = false;
    private Boolean Bar = false;
    private Boolean CustGrp = false;
    private Boolean OrderAmd = false;
    private Boolean InvVeh = false;
    private Boolean Tax = false;
    private Boolean Install = false;
    private String check;
    private Timestamp fromdate;
    private Timestamp todate;
    private String startStringDate;
    private String endStringDate;
    private Boolean custwiseDetailAnalysis = false;
    private Boolean itmwiseSummaryAnalysis = false;
    private Boolean slsmanDetailAnalysis = false;
    private Boolean grpWiseAnalysis = false;
    private Boolean linkb = false;
    private Boolean DCRegister = false;
    private Boolean DownloadReport = false;
    private Boolean DCProductiveSalon = false;
    private Boolean DownloadedReport = false;
    private StringBuffer tab =
        new StringBuffer("Opp"); /*// To Know Which Tab is Selected and that value is stored in this variable */
    private String dateClientId; //To Store Client Id Of Date
    private StringBuffer selectedTabLink; // To enable link of particular tab only


    /*  /**
     * For Bottom tab variables are been created

    private Boolean btcustwiseDetail = false;
    private Boolean btcustwiseSummary = false;
    private Boolean btsalesmanDetail = false;
    private Boolean btsalesmanSummary = false;
    private Boolean btproductgroupwiseDetail = false;
    private Boolean btproductgroupSummary = false;
    private Boolean btitmDetail = false;
    private Boolean btitmSummary = false;


    private StringBuffer bttab =
        new StringBuffer("Prft"); // To Know Which Tab is Selected present at bottom and that value is stored in this variable
    private StringBuffer btselectedTabLink; // To enable link of particular tab only which is present at bottom */

    private RichInputDate startDate;
    private RichInputDate endDate;
    private RichSelectBooleanCheckbox custDetailWiseCBPgBind;
    private RichSelectBooleanCheckbox custSummaryCBPgBind;
    private RichSelectBooleanCheckbox productGroupDetailCBPgBind;
    private RichSelectBooleanCheckbox productGroupSummaryCBPgBind;
    private RichSelectBooleanCheckbox salesManDetailCBPgBind;
    private RichSelectBooleanCheckbox salesManSummaryCBPgBind;
    private RichSelectBooleanCheckbox itemDetailCBPgBind;
    private RichSelectBooleanCheckbox itemSummaryCBPgBind;
    private RichSelectOneRadio topRadioGroupPgBind;
    private RichSelectOneRadio middleRadioGroupPgBind;
    private RichSelectOneRadio bottomRadioGroupPgBind;
    private RichSelectOneRadio fileTypeTransPgBind;
    private RichSelectBooleanCheckbox custDetailCBOppPgBind;
    private RichSelectBooleanCheckbox custSummCBOppPgBind;
    private RichSelectBooleanCheckbox productGrpDetailCBOppPgBind;
    private RichSelectBooleanCheckbox productGrpSummCBOppPgBind;
    private RichSelectBooleanCheckbox salesManDetailCBOppPgBind;
    private RichSelectBooleanCheckbox salesManSummCBOppPgBind;
    private RichSelectBooleanCheckbox itemDetailCBOppPgBind;
    private RichSelectBooleanCheckbox itemSummCBOppPgBind;
    private RichSelectBooleanCheckbox custDetailCBQuoPgBind;
    private RichSelectBooleanCheckbox custSummCBQuoPgBind;
    private RichSelectBooleanCheckbox productGrpDetailCBQuoPgBind;
    private RichSelectBooleanCheckbox productGrpSummCBQuoPgBind;
    private RichSelectBooleanCheckbox salesManDetailCBQuoPgBind;
    private RichSelectBooleanCheckbox salesManSummCBQuoPgBind;
    private RichSelectBooleanCheckbox itemDetailCBQuoPgBind;
    private RichSelectBooleanCheckbox itemSummCBQuoPgBind;
    private RichSelectBooleanCheckbox custDetailCBPLPgBind;
    private RichSelectBooleanCheckbox custSummCBPLPgBind;
    private RichSelectBooleanCheckbox productGrpCBPLPgBind;
    private RichSelectBooleanCheckbox productSummCBPLPgBind;
    private RichSelectBooleanCheckbox itemDetailCBPLPgBind;
    private RichSelectBooleanCheckbox itemSummCBPLPgBind;
    private RichSelectBooleanCheckbox custDetailCBShipPgBind;
    private RichSelectBooleanCheckbox custSummCBShipPgBind;
    private RichSelectBooleanCheckbox productGrpDetailCBShipPgBind;
    private RichSelectBooleanCheckbox productGrpSummCBShipPgBind;
    private RichSelectBooleanCheckbox itemDetailCBShipPgBind;
    private RichSelectBooleanCheckbox itemSummCBShipPgBind;
    private RichSelectBooleanCheckbox custDetailCBInvPgBind;
    private RichSelectBooleanCheckbox custSummCBInvPgBind;
    private RichSelectBooleanCheckbox productGrpDetailCBInvPgBind;
    private RichSelectBooleanCheckbox productGrpSummCBInvPgBind;
    private RichSelectBooleanCheckbox salesManDetailCBInvPgBind;
    private RichSelectBooleanCheckbox salesManSummCBInvPgBind;
    private RichSelectBooleanCheckbox itemDetailCBInvPgBind;
    private RichSelectBooleanCheckbox itemSummCBInvPgBind;
    private RichSelectBooleanCheckbox custDetailCBRmaPgBind;
    private RichSelectBooleanCheckbox custSummCBRmaPgBind;
    private RichSelectBooleanCheckbox productGrpCBRmaPgBind;
    private RichSelectBooleanCheckbox productGrpSummCBRmaPgBind;
    private RichSelectBooleanCheckbox itemDetailCBRmaPgBind;
    private RichSelectBooleanCheckbox itemSummCBRmaPgBind;
    private RichSelectBooleanCheckbox quotationRegisterPgCB;
    private RichSelectBooleanCheckbox salesOrderRegisterCBPgBind;
    private RichSelectBooleanCheckbox opportunityReportCBPgBind;
    private RichSelectBooleanCheckbox pickListRegisterCBPgBind;
    private RichSelectBooleanCheckbox pickListStatusCBPgBind;
    private RichSelectBooleanCheckbox salesManSummCBPgBind;
    private RichSelectBooleanCheckbox rmaRegisterCBPgBind;
    private RichSelectBooleanCheckbox rmaStatusCBPgBind;
    private RichSelectBooleanCheckbox custWiseSummaryCBPrftPgBind;
    private RichSelectBooleanCheckbox custDetailCBPrftPgBind;
    private RichSelectBooleanCheckbox salesManSummaryCBPrftPgBind;
    private RichSelectBooleanCheckbox salesManDetailCBPrftPgBind;
    private RichSelectBooleanCheckbox productGroupSummaryCBPrftPgBind;
    private RichSelectBooleanCheckbox productGroupDetailCBPrftPgBind;
    private RichSelectBooleanCheckbox itemSummaryCBPrftPgBind;
    private RichSelectBooleanCheckbox itemDetailCBPrftPgBind;
    private RichSelectBooleanCheckbox shipmentStatusCBPgBind;
    private RichSelectBooleanCheckbox shipmentRegisterCBPgBind;
    private RichLink customerwiseanalysisLink;
    private RichLink groupwiseAnalysisCB;
    private RichLink salesmanAnaysisLink;
    private RichLink itemWiseAnalysisLink;
    private RichSelectBooleanCheckbox custwiseSummaryCBBind;
    private RichSelectBooleanCheckbox itemWiseSummaryAnalysisCBBind;
    private RichSelectBooleanCheckbox grpWiseAnalysisCBBind;
    private RichSelectBooleanCheckbox slSManAnalysisCBBind;

    private RichSelectOneChoice oppStatusBind;

    private RichSelectOneChoice statusQuotBind;
    private RichSelectOneChoice orderStatusBind;

    private RichShowDetailItem opportunityTabBind;
    //private RichSelectOneChoice dcStageBind;
    private RichSelectOneChoice dcDocIdBind;
    private RichSelectBooleanCheckbox dcRegisterCBBind;
    private RichLink dcRegisterLink;
    private RichSelectBooleanCheckbox dcProductiveSalonCBBind;
    private RichShowDetailItem qoutTabBind;
    private RichShowDetailItem soTabBind;
    private RichShowDetailItem pickTabBind;
    private RichShowDetailItem shipTabBind;
    private RichShowDetailItem invTabBind;
    private RichShowDetailItem rmaTabBind;
    private RichShowDetailItem prftTabBind;
    private RichShowDetailItem analysisTabBind;
    private RichShowDetailItem dcTabBind;
    private RichLink oppCustSumLink;
    private RichLink oppCustDetailLink;
    private RichLink oppPgSummLink;
    private RichLink oppPgDetailLink;
    private RichLink oppSESummLink;
    private RichLink oppSEDetailLink;
    private RichLink oppPRSummLink;
    private RichLink oppPRDetailLink;
    private RichLink oppRegisterLink;
    private RichLink statusBindLink;
    private RichLink quotCustSumLink;
    private RichLink quotCustDetailLink;
    private RichLink quotPGSummLink;
    private RichLink qoutPGDetailLink;
    private RichLink quotPRSummLink;
    private RichLink quotPRdetailLink;
    private RichLink quotPRRegisterLink;
    private RichLink quotSESummLink;
    private RichLink quotSEDetailLink;
    private RichLink soCustSummLink;
    private RichLink soCustDetailLink;
    private RichLink soPGSummLink;
    private RichLink soPGDetailLink;
    private RichLink soSESummLink;
    private RichLink soSEDetailLink;
    private RichLink soPRSummLink;
    private RichLink soPRDetailLink;
    private RichLink soRegisterLink;
    private RichLink pickCustSummLink;
    private RichLink pickCustDetailLink;
    private RichLink pickPGSummLink;
    private RichLink pickPGDetailLink;
    private RichLink pickRegisterLink;
    private RichLink pickStatusLink;
    private RichLink pickPRSummLink;
    private RichLink pickPRDetailLink;
    private RichLink shipCustSummLink;
    private RichLink shipCustDetailLink;
    private RichLink shipPGSummLink;
    private RichLink shipPgDetailLink;
    private RichLink shipRegisterLink;
    private RichLink shipStatusLink;
    private RichLink shipPRSummLink;
    private RichLink shipPRDetailLink;
    private RichLink invCustSummLink;
    private RichLink invCustDetailLink;
    private RichLink invPgSummLink;
    private RichLink invPGDetailLink;
    private RichLink invSESummLink;
    private RichLink invPRSummLink;
    private RichLink invPRDetailLink;
    private RichLink rmACustSummLink;
    private RichLink rmACustDetailLink;
    private RichLink rmAPGSummLink;
    private RichLink rmAPGDetailLink;
    private RichLink rmARegisterLink;
    private RichLink rmAStatusLink;
    private RichLink rmAPRSummLink;
    private RichLink rmAPRDetailLink;
    private RichLink prftCustSummLink;
    private RichLink prftCustDetailLink;
    private RichLink prftPGSummLink;
    private RichLink prftPGDetailLink;
    private RichLink prftSESummLink;
    private RichLink prftSEDetailLink;
    private RichLink prftPRSummLink;
    private RichLink prftPRDetailLink;
    private RichLink dcPrdSalonLink;
    private RichLink trackingLink;
    private RichSelectBooleanCheckbox orderTrackingCBPgBind;
    ArrayList<String> b = new ArrayList<String>();
    HashMap newmap = new HashMap();

    /*  public void setAl(ArrayList<String> al) {
        this.al = al;
    } */

    /* public ArrayList<String> getAl() {
        return al;
    } */
    public Integer size = 0;
    public Integer newsize = 0;
    ArrayList<String> al = null;

    public void setB(ArrayList<String> b) {
        this.b = b;
    }

    public ArrayList<String> getB() {
        return b;
    }

    public SLSSalesReportAppBean() {

    }

    public void call() {
        OperationBinding ob = getBindings().getOperationBinding("dateSet");
        ob.execute();
    }

    /**
     * Code for Resolve El
     */
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
     *      clientId : client id for UI component
     * */
    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg, String clientId) {

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
            FacesContext.getCurrentInstance().addMessage(clientId, message);
        } else if (typFlg.equals("V")) {
            throw new ValidatorException(message);
        }
    }


    /**
     *When use click on Check Box first it will check whether user entered Date or not....????
     *
     */
    public boolean checkBoxValidation() {
        //System.out.println("start date------>>>" + startDate.getValue());
        // System.out.println("End date------>>>" + endDate.getValue());
        return false;

    }

    /**
     *Generate Button Action
     * @param actionEvent
     */
    protected void refreshPage() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        String refreshpage = fctx.getViewRoot().getViewId();
        ViewHandler ViewH = fctx.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fctx, refreshpage);
        UIV.setViewId(refreshpage);
        fctx.setViewRoot(UIV);
    }

    public void GenerateButtonAL(ActionEvent actionEvent) {
        if (startDate.getValue() != null) {
            System.out.println("start date if");
            fromdate =
                (Timestamp) startDate.getValue(); // Converting Timestamp to Date Format and passing as a parameter to Reports through El
        } else {
            System.out.println("Tab is in getTabLovFlag" + tab);
            OperationBinding ob = getBindings().getOperationBinding("getTabLovFlag");
            System.out.println("after getbind");
            ob.getParamsMap().put("tabValue", tab);
            System.out.println("after get params");
            ob.execute();
            System.out.println("after execute");
            String res = null;
            if (ob.getResult() != null) {
                res = ob.getResult().toString();
            }
            if (res.equalsIgnoreCase("Y")) {
                System.out.println("Tab is in  getDocDateValue" + tab);
                OperationBinding obj = getBindings().getOperationBinding("getDocDateValue");
                obj.getParamsMap().put("tabValue", tab);
                obj.execute();
                fromdate = (Timestamp) obj.getResult();

            } else {
                fromdate = null;
            }
        }
        if (endDate.getValue() != null) {
            System.out.println("end date if");
            todate =
                (Timestamp) endDate.getValue(); // Converting Timestamp to Date Format and passing as a parameter to Reports through El

        } else {
            System.out.println("Tab is in getTabLovFlag" + tab);
            OperationBinding ob = getBindings().getOperationBinding("getTabLovFlag");
            ob.getParamsMap().put("tabValue", tab);
            ob.execute();

            String res = ob.getResult().toString();
            if (res.equalsIgnoreCase("Y")) {
                System.out.println("Tab is in getDocDateValue " + tab);
                OperationBinding obj = getBindings().getOperationBinding("getDocDateValue");
                obj.getParamsMap().put("tabValue", tab);
                obj.execute();
                todate = (Timestamp) obj.getResult();

            } else {
                todate = null;
            }

        }
        try {
            if (fromdate != null && todate != null) {
                startStringDate =
                    fromdate.dateValue().toString(); // Converting Timestamp to Date Format and passing as a parameter to Reports through El
                endStringDate =
                    todate.dateValue().toString(); // Converting Timestamp to Date Format and passing as a parameter to Reports through El
            } else {
                startStringDate = null;
                endStringDate = null;
            }
            if (marginBind.getValue() != null) {
                if (marginBind.getValue().equals(true)) {
                    check = "Y";
                } else {
                    check = "N";
                }
            }
        } catch (SQLException e) {
        }


        if (custwiseDetail) {
            selectedTabLink = tab;
            setCustwiseDetailLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppCustDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotCustDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soCustDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsCustDetLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickCustDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packCustDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipCustDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invCustDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmACustDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(prftCustDetailLink);
        } else {
            selectedTabLink = tab;
            setCustwiseDetailLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppCustDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotCustDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soCustDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsCustDetLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickCustDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packCustDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipCustDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invCustDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmACustDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(prftCustDetailLink);
        }


        if (custwiseSummary) {
            selectedTabLink = tab;
            setCustwiseSummaryLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppCustSumLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotCustSumLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soCustSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsCustSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickCustSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packCustSummaryLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipCustSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invCustSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmACustSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(prftCustSummLink);
        } else {
            selectedTabLink = tab;
            setCustwiseSummaryLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppCustSumLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotCustSumLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soCustSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsCustSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickCustSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packCustSummaryLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipCustSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invCustSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmACustSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(prftCustSummLink);
        }


        if (salesmanDetail) {
            selectedTabLink = tab;
            setSalesmanDetailLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppSEDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotSEDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soSEDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(statusBindLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(prftSEDetailLink);

        } else {
            selectedTabLink = tab;
            setSalesmanDetailLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppSEDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotSEDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soSEDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(statusBindLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(prftSEDetailLink);
        }


        if (salesmanSummary) {
            selectedTabLink = tab;
            setSalesmanSummaryLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppSESummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotSESummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soSESummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invSESummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(prftSESummLink);
        } else {
            selectedTabLink = tab;
            setSalesmanSummaryLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppSESummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotSESummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soSESummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invSESummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(prftSESummLink);
        }


        if (productgroupwiseDetail) {
            selectedTabLink = tab;
            setProductgroupwiseDetailLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppPgDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(qoutPGDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soPGDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsPrdGrpDetLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickPGDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packCustPrdGrpDetail);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipPgDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invPGDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmAPGDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(prftPGDetailLink);
        } else {
            selectedTabLink = tab;
            setProductgroupwiseDetailLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppPgDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(qoutPGDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soPGDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsPrdGrpDetLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickPGDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packCustPrdGrpDetail);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipPgDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invPGDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmAPGDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(prftPGDetailLink);
        }

        if (productgroupSummary) {
            selectedTabLink = tab;
            setProductgroupSummaryLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppPgSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotPGSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soPGSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsPrdGrpSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickPGSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packPrdGrpSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipPGSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invPgSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmAPGSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(prftPGSummLink);
        } else {
            selectedTabLink = tab;
            setProductgroupSummaryLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppPgSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotPGSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soPGSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsPrdGrpSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickPGSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packPrdGrpSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipPGSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invPgSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmAPGSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(prftPGSummLink);
        }


        if (itmDetail) {
            selectedTabLink = tab;
            setItmDetailLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppPRDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotPRdetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soPRDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsItmDetLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickPRDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packItemDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickPGSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipPRDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invPRDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmAPRDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(prftPRDetailLink);
        } else {
            selectedTabLink = tab;
            setItmDetailLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppPRDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotPRdetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soPRDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsItmDetLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickPRDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packItemDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipPRDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invPRDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmAPRDetailLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(prftPRDetailLink);
        }


        if (itmSummary) {
            selectedTabLink = tab;
            setItmSummaryLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppPRSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotPRSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soPRSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsItmSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickPRSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packItemSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invPRSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipPRSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmAPRSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(prftPRSummLink);
        } else {
            selectedTabLink = tab;
            setItmSummaryLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppPRSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotPRSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soPRSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsItmSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickPRSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packItemSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipPRSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invPRSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmAPRSummLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(prftPRSummLink);
        }

        if (pickBar) {
            selectedTabLink = tab;
            System.out.println("In Other true");
            setPickBarLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickDetLinkBind);
        } else {
            selectedTabLink = tab;
            System.out.println("In Other false");
            setPickBarLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickDetLinkBind);
        }
        if (CustGrp) {
            selectedTabLink = tab;
            System.out.println("In custbar true");
            setCustGrpLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custWiseGrpCBBind);
        } else {
            selectedTabLink = tab;
            System.out.println("In Other false");
            setCustGrpLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custWiseGrpCBBind);
        }
        if (otherRegister) {
            selectedTabLink = tab;
            System.out.println("In Other true");
            setOtherRegisterLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppRegisterLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotPRRegisterLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soRegisterLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickRegisterLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packOtherRegisterLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipRegisterLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invRegCBLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmARegisterLink);
        } else {
            selectedTabLink = tab;
            System.out.println("In Other false");
            setOtherRegisterLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppRegisterLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotPRRegisterLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soRegisterLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickRegisterLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packOtherRegisterLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipRegisterLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invRegCBLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmARegisterLink);

        }

        if (otherRegSumm) {
            selectedTabLink = tab;
            System.out.println("In Other true");
            setOtherRegSummLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invRegSummCBBind);
        } else {
            selectedTabLink = tab;
            System.out.println("In Other false");
            setOtherRegSummLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invRegSummCBBind);
        }
        if (otherPending) {
            selectedTabLink = tab;
            System.out.println("In Pending true");
            setOtherPendingLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(orderPendingLinkBind);

        } else {
            selectedTabLink = tab;
            System.out.println("In Pending false");
            setOtherPendingLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(orderPendingLinkBind);

        }

        if (OrderTracking) {
            selectedTabLink = tab;
            System.out.println("In tracking true");
            setOrderTrackingLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(trackingLink);

        } else {
            selectedTabLink = tab;
            System.out.println("In tracking false");
            setOrderTrackingLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(trackingLink);
        }

        if (OrderAmd) {
            selectedTabLink = tab;
            System.out.println("In tracking true");
            setOrderAmdLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(ordAmdLinkBind);

        } else {
            selectedTabLink = tab;
            System.out.println("In tracking false");
            setOrderAmdLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(ordAmdLinkBind);
        }

        if (InvVeh) {
            selectedTabLink = tab;
            System.out.println("In tracking true");
            setInvVehLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invvehLinkBind);

        } else {
            selectedTabLink = tab;
            System.out.println("In tracking false");
            setInvVehLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invvehLinkBind);
        }
        
        if (Tax) {
            selectedTabLink = tab;
            System.out.println("In tracking true");
            setTaxLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(taxLinkBind);

        } else {
            selectedTabLink = tab;
            System.out.println("In tracking false");
            setTaxLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(taxLinkBind);
        }

        if (custPrice) {
            selectedTabLink = tab;
            System.out.println("in custprice true");
            setCustPriceLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custPriceLinkBind);
        } else {
            selectedTabLink = tab;
            System.out.println("in custprice false");
            setCustPriceLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custPriceLinkBind);
        }

        if (status) {
            selectedTabLink = tab;
            setStatusLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickStatusLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packSummaryLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipStatusLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmAStatusLink);
        } else {
            selectedTabLink = tab;
            setStatusLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickStatusLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packSummaryLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipStatusLink);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmAStatusLink);
        }
        if (custwiseDetailAnalysis) {
            selectedTabLink = tab;
            customerwiseanalysisLink.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(customerwiseanalysisLink);
        } else {
            selectedTabLink = tab;
            customerwiseanalysisLink.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(customerwiseanalysisLink);
        }
        if (intmReg) {
            selectedTabLink = tab;
            intmRegLinkBind.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(intmRegLinkBind);
        } else {
            selectedTabLink = tab;
            intmRegLinkBind.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(intmRegLinkBind);
        }
        if (Install) {
            selectedTabLink = tab;
            installationLinkBind.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(installationLinkBind);
        } else {
            selectedTabLink = tab;
            installationLinkBind.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(installationLinkBind);
        }
        if (suppReg) {
            selectedTabLink = tab;
            suppRegLinkBind.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(suppRegLinkBind);
        } else {
            selectedTabLink = tab;
            suppRegLinkBind.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(suppRegLinkBind);
        }
        if (itmwiseSummaryAnalysis) {
            selectedTabLink = tab;
            itemWiseAnalysisLink.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemWiseAnalysisLink);
        } else {
            selectedTabLink = tab;
            itemWiseAnalysisLink.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemWiseAnalysisLink);
        }
        if (slsmanDetailAnalysis) {
            selectedTabLink = tab;
            salesmanAnaysisLink.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesmanAnaysisLink);
        } else {
            selectedTabLink = tab;
            salesmanAnaysisLink.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesmanAnaysisLink);
        }
        if (grpWiseAnalysis) {
            selectedTabLink = tab;
            groupwiseAnalysisCB.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseAnalysisCB);

        } else {
            selectedTabLink = tab;
            groupwiseAnalysisCB.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(groupwiseAnalysisCB);
        }
        if (DCRegister) {
            selectedTabLink = tab;
            setDCRegisterLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dcRegisterLink);
        } else {
            selectedTabLink = tab;
            setDCRegisterLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dcRegisterLink);
        }
        if (DCProductiveSalon) {
            selectedTabLink = tab;
            setDCProductiveSalonLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dcPrdSalonLink);
        } else {
            selectedTabLink = tab;
            setDCProductiveSalonLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dcPrdSalonLink);
        }
        if (DownloadedReport) {
            selectedTabLink = tab;
            setDownloadReportLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(downloadReportLink);
        } else {
            selectedTabLink = tab;
            setDownloadReportLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(downloadReportLink);
        }
        if (whSumm) {
            selectedTabLink = tab;
            setWhSummLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsWhSummLink);
        } else {
            selectedTabLink = tab;
            setWhSummLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsWhSummLink);
        }
        if (whDet) {
            selectedTabLink = tab;
            setWhDetLink(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsWhDetLink);
        } else {
            selectedTabLink = tab;
            setWhDetLink(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsWhDetLink);
        }
        if (downloadedReptBind.isSelected()) {
            linkb = true;
        } else {
            linkb = true;
        }
        if (tab.equals("Opp")) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(opportunityTabBind);
        } else if (tab.equals("Quo")) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(qoutTabBind);
        } else if (tab.equals("SO")) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(soTabBind);
        } else if (tab.equals("DS")) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsTabBind);
        } else if (tab.equals("PL")) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickTabBind);
        } else if (tab.equals("PK")) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(packTabBind);
        } else if (tab.equals("Ship")) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipTabBind);
        } else if (tab.equals("Inv")) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(invTabBind);
        } else if (tab.equals("Rma")) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmaTabBind);
        } else if (tab.equals("Prft")) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(prftTabBind);
        } else if (tab.equals("Analysis")) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(analysisTabBind);
        } else if (tab.equals("DC")) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(dcTabBind);
        }

        list = new ArrayList<String>(newmap.values());
        for (String s : list) {
            System.out.println(s);
        }

        System.out.println("list value in " + list + "newmap value in generate" + newmap.values());

        // System.out.println("s" + s + " length " + s.length());
        // at = s.substring(1, s.length() - 1);


        //        System.out.println("at value" + at + "length" + at.length());
        //        String att[] = at.trim().split("\\s*,\\s*");
        //        System.out.println("attribute values are");
        //        al.add(at);
        //al.add(b.toString());
        //al = new ArrayList(Arrays.asList(b.toString().substring(1, b.toString().length()-1).split("\\\\s*,\\\\s*")));
        //         System.out.println("al"+al);

        //        System.out.println("length" + att.length);

        // System.out.println("array list value is "+al);
        //        if (att.length > 1) {
        //            for (int i = 0; i < att.length; i++) {
        //                if (i == 0) {
        //                    attribute1 = att[i];
        //                    System.out.println("attrId1--" + attribute1);
        //                }
        //                if (i == 1) {
        //                    attribute2 = att[i];
        //                    System.out.println("attrId2--" + attribute2);
        //
        //                }
        //                if (i == 2) {
        //                    attribute3 = att[i];
        //                    System.out.println("attrId3--" + attribute3);
        //                }
        //                if (i == 3) {
        //                    attribute4 = att[i];
        //                    System.out.println("attrId4--" + attribute4);
        //                }
        //                if (i == 4) {
        //                    attribute5 = att[i];
        //                    System.out.println("attrId5--" + attribute5);
        //                }


        //        System.out.println("att values after loop attr");
        //        System.out.println(attribute1);
        //        System.out.println(attribute2);
        //        System.out.println(attribute3);
        //        System.out.println(attribute4);
        //        System.out.println(attribute5);

        /* private Boolean itmwiseSummaryAnalysis = false;
        private Boolean slsmanDetailAnalysis = false;
        private Boolean grpWiseAnalysis = false; */

        /* /**
         * For Bottom tab to enable or disable links

        if (btcustwiseDetail) {
            btselectedTabLink = bttab;
            setBtcustwiseDetailLink(true);
        } else {
            btselectedTabLink = bttab;
            setBtcustwiseDetailLink(false);
        }


        if (btcustwiseSummary) {
            btselectedTabLink = bttab;
            setBtcustwiseSummaryLink(true);
        } else {
            btselectedTabLink = bttab;
            setBtcustwiseSummaryLink(false);
        }


        if (btsalesmanDetail) {
            btselectedTabLink = bttab;
            setBtsalesmanDetailLink(true);
        } else {
            btselectedTabLink = bttab;
            setBtsalesmanDetailLink(false);
        }


        if (btsalesmanSummary) {
            btselectedTabLink = bttab;
            setBtsalesmanSummaryLink(true);
        } else {
            btselectedTabLink = bttab;
            setBtsalesmanSummaryLink(false);
        }


        if (btproductgroupwiseDetail) {
            btselectedTabLink = bttab;
            setBtproductgroupwiseDetailLink(true);
        } else {
            btselectedTabLink = bttab;
            setBtproductgroupwiseDetailLink(false);
        }

        if (btproductgroupSummary) {
            btselectedTabLink = bttab;
            setBtproductgroupSummaryLink(true);
        } else {
            btselectedTabLink = bttab;
            setBtproductgroupSummaryLink(false);
        }


        if (btitmDetail) {
            btselectedTabLink = bttab;
            setBtitmDetailLink(true);
        } else {
            btselectedTabLink = bttab;
            setBtitmDetailLink(false);
        }


        if (btitmSummary) {
            btselectedTabLink = bttab;
            setBtitmSummaryLink(true);
        } else {
            btselectedTabLink = bttab;
            setBtitmSummaryLink(false);
        } */
    }

    public void setTaxLink(Boolean TaxLink) {
        this.TaxLink = TaxLink;
    }

    public Boolean getTaxLink() {
        return TaxLink;
    }

    public void setTax(Boolean Tax) {
        this.Tax = Tax;
    }

    public Boolean getTax() {
        return Tax;
    }

    public void setInvVehLink(Boolean InvVehLink) {
        this.InvVehLink = InvVehLink;
    }

    public Boolean getInvVehLink() {
        return InvVehLink;
    }

    public void setInvVeh(Boolean InvVeh) {
        this.InvVeh = InvVeh;
    }

    public Boolean getInvVeh() {
        return InvVeh;
    }

    public void setOrderAmdLink(Boolean OrderAmdLink) {
        this.OrderAmdLink = OrderAmdLink;
    }

    public Boolean getOrderAmdLink() {
        return OrderAmdLink;
    }

    public void setOrderAmd(Boolean OrderAmd) {
        this.OrderAmd = OrderAmd;
    }

    public Boolean getOrderAmd() {
        return OrderAmd;
    }

    public void setBarLink(Boolean BarLink) {
        this.BarLink = BarLink;
    }

    public Boolean getBarLink() {
        return BarLink;
    }

    public void setCustGrpLink(Boolean CustGrpLink) {
        this.CustGrpLink = CustGrpLink;
    }

    public Boolean getCustGrpLink() {
        return CustGrpLink;
    }

    public void setBar(Boolean Bar) {
        this.Bar = Bar;
    }

    public Boolean getBar() {
        return Bar;
    }

    public void setCustGrp(Boolean CustGrp) {
        this.CustGrp = CustGrp;
    }

    public Boolean getCustGrp() {
        return CustGrp;
    }

    public void setPickBarLink(Boolean pickBarLink) {
        this.pickBarLink = pickBarLink;
    }

    public Boolean getPickBarLink() {
        return pickBarLink;
    }

    public void setPickBar(Boolean pickBar) {
        this.pickBar = pickBar;
    }

    public Boolean getPickBar() {
        return pickBar;
    }

    public void setPackCustSummaryLink(RichLink packCustSummaryLink) {
        this.packCustSummaryLink = packCustSummaryLink;
    }

    public RichLink getPackCustSummaryLink() {
        return packCustSummaryLink;
    }

    public void setCustPriceLink(Boolean custPriceLink) {
        this.custPriceLink = custPriceLink;
    }

    public Boolean getCustPriceLink() {
        return custPriceLink;
    }

    public void setCustPrice(Boolean custPrice) {
        this.custPrice = custPrice;
    }

    public Boolean getCustPrice() {
        return custPrice;
    }

    public void setNewmap(HashMap newmap) {
        this.newmap = newmap;
    }

    public HashMap getNewmap() {
        return newmap;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }

    public void setNewsize(Integer newsize) {
        this.newsize = newsize;
    }

    public Integer getNewsize() {
        return newsize;
    }

    public void setAl(ArrayList<String> al) {
        this.al = al;
    }

    public ArrayList<String> getAl() {
        return al;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    public void setWhSumm(Boolean whSumm) {
        this.whSumm = whSumm;
    }

    public Boolean getWhSumm() {
        return whSumm;
    }

    public void setWhDet(Boolean whDet) {
        this.whDet = whDet;
    }

    public Boolean getWhDet() {
        return whDet;
    }

    public void setOtherPendingLink(Boolean otherPendingLink) {
        this.otherPendingLink = otherPendingLink;
    }

    public Boolean getOtherPendingLink() {
        return otherPendingLink;
    }

    public void setCustwiseDetail(Boolean custwiseDetail) {
        this.custwiseDetail = custwiseDetail;
    }

    public Boolean getCustwiseDetail() {
        return custwiseDetail;
    }

    public void setCustwiseSummary(Boolean custwiseSummary) {
        this.custwiseSummary = custwiseSummary;
    }

    public Boolean getCustwiseSummary() {
        return custwiseSummary;
    }

    public void setSalesmanDetail(Boolean salesmanDetail) {
        this.salesmanDetail = salesmanDetail;
    }

    public Boolean getSalesmanDetail() {
        return salesmanDetail;
    }

    public void setSalesmanSummary(Boolean salesmanSummary) {
        this.salesmanSummary = salesmanSummary;
    }

    public Boolean getSalesmanSummary() {
        return salesmanSummary;
    }

    public void setProductgroupwiseDetail(Boolean productgroupwiseDetail) {
        this.productgroupwiseDetail = productgroupwiseDetail;
    }

    public Boolean getProductgroupwiseDetail() {
        return productgroupwiseDetail;
    }

    public void setProductgroupSummary(Boolean productgroupSummary) {
        this.productgroupSummary = productgroupSummary;
    }

    public Boolean getProductgroupSummary() {
        return productgroupSummary;
    }

    public void setItmDetail(Boolean itmDetail) {
        this.itmDetail = itmDetail;
    }

    public Boolean getItmDetail() {
        return itmDetail;
    }

    public void setItmSummary(Boolean itmSummary) {
        this.itmSummary = itmSummary;
    }

    public Boolean getItmSummary() {
        return itmSummary;
    }

    public void setOtherPending(Boolean otherPending) {
        this.otherPending = otherPending;
    }

    public Boolean getOtherPending() {
        return otherPending;
    }

    public void setFromdate(Timestamp fromdate) {
        this.fromdate = fromdate;
    }

    public Timestamp getFromdate() {
        return fromdate;
    }

    public void setTodate(Timestamp todate) {
        this.todate = todate;
    }

    public Timestamp getTodate() {
        return todate;
    }

    public void setCustwiseDetailAnalysis(Boolean custwiseDetailAnalysis) {
        this.custwiseDetailAnalysis = custwiseDetailAnalysis;
    }

    public Boolean getCustwiseDetailAnalysis() {
        return custwiseDetailAnalysis;
    }

    public void setItmwiseSummaryAnalysis(Boolean itmwiseSummaryAnalysis) {
        this.itmwiseSummaryAnalysis = itmwiseSummaryAnalysis;
    }

    public Boolean getItmwiseSummaryAnalysis() {
        return itmwiseSummaryAnalysis;
    }

    public void setSlsmanDetailAnalysis(Boolean slsmanDetailAnalysis) {
        this.slsmanDetailAnalysis = slsmanDetailAnalysis;
    }

    public Boolean getSlsmanDetailAnalysis() {
        return slsmanDetailAnalysis;
    }

    public void setGrpWiseAnalysis(Boolean grpWiseAnalysis) {
        this.grpWiseAnalysis = grpWiseAnalysis;
    }

    public Boolean getGrpWiseAnalysis() {
        return grpWiseAnalysis;
    }

    public void setDCRegister(Boolean DCRegister) {
        this.DCRegister = DCRegister;
    }

    public Boolean getDCRegister() {
        return DCRegister;
    }

    public void setDCProductiveSalon(Boolean DCProductiveSalon) {
        this.DCProductiveSalon = DCProductiveSalon;
    }

    public Boolean getDCProductiveSalon() {
        return DCProductiveSalon;
    }

    public void setDateClientId(String dateClientId) {
        this.dateClientId = dateClientId;
    }

    public String getDateClientId() {
        return dateClientId;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getCheck() {
        return check;
    }

    public void setLinkb(Boolean linkb) {
        this.linkb = linkb;
    }

    public Boolean getLinkb() {
        return linkb;
    }

    public void setDownloadReport(Boolean DownloadReport) {
        this.DownloadReport = DownloadReport;
    }

    public Boolean getDownloadReport() {
        return DownloadReport;
    }

    public void setDownloadedReport(Boolean DownloadedReport) {
        this.DownloadedReport = DownloadedReport;
    }

    public Boolean getDownloadedReport() {
        return DownloadedReport;
    }

    public void setOrderTrackingLink(Boolean OrderTrackingLink) {
        this.OrderTrackingLink = OrderTrackingLink;
    }

    public Boolean getOrderTrackingLink() {
        return OrderTrackingLink;
    }

    /**
     * All Tabs Disclosure Event was present
     *
     */

    public void OpportunityTabDL(DisclosureEvent dE) {
        call();
        if (dE.isExpanded()) {
            tab = new StringBuffer("Opp");
            SaleReportAMImpl am = (SaleReportAMImpl) resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("OpportunityNo", null);
            oppNoLOVBind.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppNoLOVBind);
        } else {

            selectedTabLink = null;
            SaleReportAMImpl am = (SaleReportAMImpl) resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("OpportunityNo", null);
            if (oppNoLOVBind.getValue() != null) {
                System.out.println("oppbind value-->" + oppNoLOVBind.getValue());
            }
            custDetailCBOppPgBind.setValue(false);
            custSummCBOppPgBind.setValue(false);
            salesManDetailCBOppPgBind.setValue(false);
            salesManSummCBOppPgBind.setValue(false);
            productGrpDetailCBOppPgBind.setValue(false);
            productGrpSummCBOppPgBind.setValue(false);
            itemDetailCBOppPgBind.setValue(false);
            itemSummCBOppPgBind.setValue(false);
            opportunityReportCBPgBind.setValue(false);

            AdfFacesContext.getCurrentInstance().addPartialTarget(oppNoLOVBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBOppPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBOppPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManDetailCBOppPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManSummCBOppPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpDetailCBOppPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBOppPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBOppPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBOppPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(opportunityReportCBPgBind);
        }
    }

    public void QuotationTabDL(DisclosureEvent dE) {
        call();
        if (dE.isExpanded()) {


            tab = new StringBuffer("Quo");
            SaleReportAMImpl am = (SaleReportAMImpl) resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("QuotationNo", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotNoLOVBind);
        } else {
            selectedTabLink = null;

            custDetailCBQuoPgBind.setValue(false);
            custSummCBQuoPgBind.setValue(false);
            salesManDetailCBQuoPgBind.setValue(false);
            salesManSummCBQuoPgBind.setValue(false);
            productGrpDetailCBQuoPgBind.setValue(false);
            productGrpSummCBQuoPgBind.setValue(false);
            itemDetailCBQuoPgBind.setValue(false);
            itemSummCBQuoPgBind.setValue(false);
            quotationRegisterPgCB.setValue(false);
            SaleReportAMImpl am = (SaleReportAMImpl) resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("QuotationNo", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotNoLOVBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBQuoPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBQuoPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManDetailCBQuoPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManSummCBQuoPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpDetailCBQuoPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBQuoPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBQuoPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBQuoPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotationRegisterPgCB);
        }

    }

    public void SalesOrderTabDL(DisclosureEvent dE) {
        call();
        if (dE.isExpanded()) {

            tab = new StringBuffer("SO");
            SaleReportAMImpl am = (SaleReportAMImpl) resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("OrderNoTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(orderLOVBind);
        } else {
            selectedTabLink = null;

            custDetailWiseCBPgBind.setValue(false);
            custSummaryCBPgBind.setValue(false);
            salesManDetailCBPgBind.setValue(false);
            salesManSummaryCBPgBind.setValue(false);
            productGroupDetailCBPgBind.setValue(false);
            productGroupSummaryCBPgBind.setValue(false);
            itemDetailCBPgBind.setValue(false);
            itemSummaryCBPgBind.setValue(false);
            salesOrderRegisterCBPgBind.setValue(false);
            salesOrderPendingCBBind.setValue(false);
            SaleReportAMImpl am = (SaleReportAMImpl) resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("OrderNoTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(orderLOVBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailWiseCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummaryCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManDetailCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManSummaryCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGroupDetailCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGroupSummaryCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummaryCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesOrderRegisterCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesOrderPendingCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(orderLOVBind);
        }

    }

    public void PickListTabDL(DisclosureEvent dE) {
        call();
        if (dE.isExpanded()) {
            tab = new StringBuffer("PL");

            SaleReportAMImpl am = (SaleReportAMImpl) resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("PickIdTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickNoLOVBind);
        } else {
            selectedTabLink = null;

            custDetailCBPLPgBind.setValue(false);
            custSummCBPLPgBind.setValue(false);
            productGrpCBPLPgBind.setValue(false);
            productSummCBPLPgBind.setValue(false);
            itemDetailCBPLPgBind.setValue(false);
            itemSummCBPLPgBind.setValue(false);
            pickListRegisterCBPgBind.setValue(false);
            pickListStatusCBPgBind.setValue(false);
            pickBarDetCBBind.setValue(false);
            SaleReportAMImpl am = (SaleReportAMImpl) resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("PickIdTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickNoLOVBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBPLPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBPLPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpCBPLPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productSummCBPLPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBPLPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBPLPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickListRegisterCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickListStatusCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickBarDetCBBind);
        }

    }

    public void ShippmentTabDL(DisclosureEvent dE) {
        call();
        if (dE.isExpanded()) {
            tab = new StringBuffer("Ship");

            SaleReportAMImpl am = (SaleReportAMImpl) resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("ShipNoTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipNoLOVBind);

        } else {
            selectedTabLink = null;

            custDetailCBShipPgBind.setValue(false);
            custSummCBShipPgBind.setValue(false);
            productGrpDetailCBShipPgBind.setValue(false);
            productGrpSummCBShipPgBind.setValue(false);
            itemDetailCBShipPgBind.setValue(false);
            itemSummCBShipPgBind.setValue(false);
            shipmentRegisterCBPgBind.setValue(false);
            shipmentStatusCBPgBind.setValue(false);
            intmRegisterCBBind.setValue(false);
            installationCBBind.setValue(false);

            SaleReportAMImpl am = (SaleReportAMImpl) resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("ShipNoTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipNoLOVBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBShipPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBShipPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpDetailCBShipPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBShipPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBShipPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBShipPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipmentRegisterCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipmentStatusCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(intmRegisterCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(installationCBBind);
        }

    }

    public void InvoiceTabDL(DisclosureEvent dE) {
        call();
        if (dE.isExpanded()) {
            tab = new StringBuffer("Inv");
            SaleReportAMImpl am = (SaleReportAMImpl) resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("InvoiceIdTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invoiceNoLOVBind);

        } else {
            selectedTabLink = null;

            custDetailCBInvPgBind.setValue(false);
            custSummCBInvPgBind.setValue(false);
            salesManDetailCBInvPgBind.setValue(false);
            salesManSummCBInvPgBind.setValue(false);
            productGrpDetailCBInvPgBind.setValue(false);
            productGrpSummCBInvPgBind.setValue(false);
            itemDetailCBInvPgBind.setValue(false);
            itemSummCBInvPgBind.setValue(false);
            invRegCBBind.setValue(false);
            invRegSummCB.setValue(false);
            suppRegInvCBBind.setValue(false);
            custWiseGrpCBBind.setValue(false);
            invVehicleSummCBBind.setValue(false);
            taxCBBind.setValue(false);
            SaleReportAMImpl am = (SaleReportAMImpl) resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("InvoiceIdTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invoiceNoLOVBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBInvPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBInvPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManDetailCBInvPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManSummCBInvPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpDetailCBInvPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBInvPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBInvPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBInvPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invRegCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invRegSummCB);
            AdfFacesContext.getCurrentInstance().addPartialTarget(suppRegInvCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custWiseGrpCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invVehicleSummCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(taxCBBind);
        }
    }

    public void RmaTabDL(DisclosureEvent dE) {
        call();
        if (dE.isExpanded()) {
            tab = new StringBuffer("Rma");

            SaleReportAMImpl am = (SaleReportAMImpl) resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("RmaIdTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmaNoLOVBind);
        } else {
            selectedTabLink = null;

            custDetailCBRmaPgBind.setValue(false);
            custSummCBRmaPgBind.setValue(false);
            productGrpCBRmaPgBind.setValue(false);
            productGrpSummCBRmaPgBind.setValue(false);
            itemDetailCBRmaPgBind.setValue(false);
            itemSummCBRmaPgBind.setValue(false);
            rmaRegisterCBPgBind.setValue(false);
            rmaStatusCBPgBind.setValue(false);
            SaleReportAMImpl am = (SaleReportAMImpl) resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("RmaIdTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmaNoLOVBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBRmaPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBRmaPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpCBRmaPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBRmaPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBRmaPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBRmaPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmaRegisterCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmaStatusCBPgBind);
        }
    }

    public void ProfitabilityTabDL(DisclosureEvent dE) {
        call();
        if (dE.isExpanded()) {
            tab = new StringBuffer("Prft");
        } else {
            selectedTabLink = null;

            custDetailCBPrftPgBind.setValue(false);
            custWiseSummaryCBPrftPgBind.setValue(false);
            productGroupDetailCBPrftPgBind.setValue(false);
            productGroupSummaryCBPrftPgBind.setValue(false);
            itemDetailCBPrftPgBind.setValue(false);
            itemSummaryCBPrftPgBind.setValue(false);
            salesManDetailCBPrftPgBind.setValue(false);
            salesManSummaryCBPrftPgBind.setValue(false);

            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBPrftPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custWiseSummaryCBPrftPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGroupDetailCBPrftPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGroupSummaryCBPrftPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBPrftPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummaryCBPrftPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManDetailCBPrftPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManSummaryCBPrftPgBind);
        }
    }

    public void setStartDate(RichInputDate startDate) {
        this.startDate = startDate;
    }

    public RichInputDate getStartDate() {
        return startDate;
    }

    public void setEndDate(RichInputDate endDate) {
        this.endDate = endDate;
    }

    public RichInputDate getEndDate() {
        return endDate;
    }

    public void setCustDetailWiseCBPgBind(RichSelectBooleanCheckbox custDetailWiseCBPgBind) {
        this.custDetailWiseCBPgBind = custDetailWiseCBPgBind;
    }

    public RichSelectBooleanCheckbox getCustDetailWiseCBPgBind() {
        return custDetailWiseCBPgBind;
    }

    public void setCustSummaryCBPgBind(RichSelectBooleanCheckbox custSummaryCBPgBind) {
        this.custSummaryCBPgBind = custSummaryCBPgBind;
    }

    public RichSelectBooleanCheckbox getCustSummaryCBPgBind() {
        return custSummaryCBPgBind;
    }

    public void setCustwiseDetailLink(Boolean custwiseDetailLink) {
        this.custwiseDetailLink = custwiseDetailLink;
    }

    public Boolean getCustwiseDetailLink() {
        return custwiseDetailLink;
    }

    public void setCustwiseSummaryLink(Boolean custwiseSummaryLink) {
        this.custwiseSummaryLink = custwiseSummaryLink;
    }

    public Boolean getCustwiseSummaryLink() {
        return custwiseSummaryLink;
    }

    public void setSalesmanDetailLink(Boolean salesmanDetailLink) {
        this.salesmanDetailLink = salesmanDetailLink;
    }

    public Boolean getSalesmanDetailLink() {
        return salesmanDetailLink;
    }

    public void setSalesmanSummaryLink(Boolean salesmanSummaryLink) {
        this.salesmanSummaryLink = salesmanSummaryLink;
    }

    public Boolean getSalesmanSummaryLink() {
        return salesmanSummaryLink;
    }

    public void setProductgroupwiseDetailLink(Boolean productgroupwiseDetailLink) {
        this.productgroupwiseDetailLink = productgroupwiseDetailLink;
    }

    public Boolean getProductgroupwiseDetailLink() {
        return productgroupwiseDetailLink;
    }

    public void setProductgroupSummaryLink(Boolean productgroupSummaryLink) {
        this.productgroupSummaryLink = productgroupSummaryLink;
    }

    public Boolean getProductgroupSummaryLink() {
        return productgroupSummaryLink;
    }

    public void setItmDetailLink(Boolean itmDetailLink) {
        this.itmDetailLink = itmDetailLink;
    }

    public Boolean getItmDetailLink() {
        return itmDetailLink;
    }

    public void setItmSummaryLink(Boolean itmSummaryLink) {
        this.itmSummaryLink = itmSummaryLink;
    }

    public Boolean getItmSummaryLink() {
        return itmSummaryLink;
    }

    public void setTab(StringBuffer tab) {
        this.tab = tab;
    }

    public StringBuffer getTab() {
        return tab;
    }

    public void setProductGroupDetailCBPgBind(RichSelectBooleanCheckbox productGroupDetailCBPgBind) {
        this.productGroupDetailCBPgBind = productGroupDetailCBPgBind;
    }

    public RichSelectBooleanCheckbox getProductGroupDetailCBPgBind() {
        return productGroupDetailCBPgBind;
    }

    public void setProductGroupSummaryCBPgBind(RichSelectBooleanCheckbox productGroupSummaryCBPgBind) {
        this.productGroupSummaryCBPgBind = productGroupSummaryCBPgBind;
    }

    public RichSelectBooleanCheckbox getProductGroupSummaryCBPgBind() {
        return productGroupSummaryCBPgBind;
    }

    public void setSalesManDetailCBPgBind(RichSelectBooleanCheckbox salesManDetailCBPgBind) {
        this.salesManDetailCBPgBind = salesManDetailCBPgBind;
    }

    public RichSelectBooleanCheckbox getSalesManDetailCBPgBind() {
        return salesManDetailCBPgBind;
    }

    public void setSalesManSummaryCBPgBind(RichSelectBooleanCheckbox salesManSummaryCBPgBind) {
        this.salesManSummaryCBPgBind = salesManSummaryCBPgBind;
    }

    public RichSelectBooleanCheckbox getSalesManSummaryCBPgBind() {
        return salesManSummaryCBPgBind;
    }

    public void setItemDetailCBPgBind(RichSelectBooleanCheckbox itemDetailCBPgBind) {
        this.itemDetailCBPgBind = itemDetailCBPgBind;
    }

    public RichSelectBooleanCheckbox getItemDetailCBPgBind() {
        return itemDetailCBPgBind;
    }

    public void setItemSummaryCBPgBind(RichSelectBooleanCheckbox itemSummaryCBPgBind) {
        this.itemSummaryCBPgBind = itemSummaryCBPgBind;
    }

    public RichSelectBooleanCheckbox getItemSummaryCBPgBind() {
        return itemSummaryCBPgBind;
    }

    public void setStartStringDate(String startStringDate) {
        this.startStringDate = startStringDate;
    }

    public String getStartStringDate() {
        return startStringDate;
    }

    public void setEndStringDate(String endStringDate) {
        this.endStringDate = endStringDate;
    }

    public String getEndStringDate() {
        return endStringDate;
    }

    public void setTopRadioGroupPgBind(RichSelectOneRadio topRadioGroupPgBind) {
        this.topRadioGroupPgBind = topRadioGroupPgBind;
    }

    public RichSelectOneRadio getTopRadioGroupPgBind() {
        return topRadioGroupPgBind;
    }

    public void setMiddleRadioGroupPgBind(RichSelectOneRadio middleRadioGroupPgBind) {
        this.middleRadioGroupPgBind = middleRadioGroupPgBind;
    }

    public RichSelectOneRadio getMiddleRadioGroupPgBind() {
        return middleRadioGroupPgBind;
    }

    public void setBottomRadioGroupPgBind(RichSelectOneRadio bottomRadioGroupPgBind) {
        this.bottomRadioGroupPgBind = bottomRadioGroupPgBind;
    }

    public RichSelectOneRadio getBottomRadioGroupPgBind() {
        return bottomRadioGroupPgBind;
    }

    public void setFileTypeTransPgBind(RichSelectOneRadio fileTypeTransPgBind) {
        this.fileTypeTransPgBind = fileTypeTransPgBind;
    }

    public RichSelectOneRadio getFileTypeTransPgBind() {
        return fileTypeTransPgBind;
    }

    public void setCustDetailCBOppPgBind(RichSelectBooleanCheckbox custDetailCBOppPgBind) {
        this.custDetailCBOppPgBind = custDetailCBOppPgBind;
    }

    public RichSelectBooleanCheckbox getCustDetailCBOppPgBind() {
        return custDetailCBOppPgBind;
    }

    public void setCustSummCBOppPgBind(RichSelectBooleanCheckbox custSummCBOppPgBind) {
        this.custSummCBOppPgBind = custSummCBOppPgBind;
    }

    public RichSelectBooleanCheckbox getCustSummCBOppPgBind() {
        return custSummCBOppPgBind;
    }

    public void setProductGrpDetailCBOppPgBind(RichSelectBooleanCheckbox productGrpDetailCBOppPgBind) {
        this.productGrpDetailCBOppPgBind = productGrpDetailCBOppPgBind;
    }

    public RichSelectBooleanCheckbox getProductGrpDetailCBOppPgBind() {
        return productGrpDetailCBOppPgBind;
    }

    public void setProductGrpSummCBOppPgBind(RichSelectBooleanCheckbox productGrpSummCBOppPgBind) {
        this.productGrpSummCBOppPgBind = productGrpSummCBOppPgBind;
    }

    public RichSelectBooleanCheckbox getProductGrpSummCBOppPgBind() {
        return productGrpSummCBOppPgBind;
    }

    public void setSalesManDetailCBOppPgBind(RichSelectBooleanCheckbox salesManDetailCBOppPgBind) {
        this.salesManDetailCBOppPgBind = salesManDetailCBOppPgBind;
    }

    public RichSelectBooleanCheckbox getSalesManDetailCBOppPgBind() {
        return salesManDetailCBOppPgBind;
    }

    public void setSalesManSummCBOppPgBind(RichSelectBooleanCheckbox salesManSummCBOppPgBind) {
        this.salesManSummCBOppPgBind = salesManSummCBOppPgBind;
    }

    public RichSelectBooleanCheckbox getSalesManSummCBOppPgBind() {
        return salesManSummCBOppPgBind;
    }

    public void setItemDetailCBOppPgBind(RichSelectBooleanCheckbox itemDetailCBOppPgBind) {
        this.itemDetailCBOppPgBind = itemDetailCBOppPgBind;
    }

    public RichSelectBooleanCheckbox getItemDetailCBOppPgBind() {
        return itemDetailCBOppPgBind;
    }

    public void setItemSummCBOppPgBind(RichSelectBooleanCheckbox itemSummCBOppPgBind) {
        this.itemSummCBOppPgBind = itemSummCBOppPgBind;
    }

    public RichSelectBooleanCheckbox getItemSummCBOppPgBind() {
        return itemSummCBOppPgBind;
    }

    public void setCustDetailCBQuoPgBind(RichSelectBooleanCheckbox custDetailCBQuoPgBind) {
        this.custDetailCBQuoPgBind = custDetailCBQuoPgBind;
    }

    public RichSelectBooleanCheckbox getCustDetailCBQuoPgBind() {
        return custDetailCBQuoPgBind;
    }

    public void setCustSummCBQuoPgBind(RichSelectBooleanCheckbox custSummCBQuoPgBind) {
        this.custSummCBQuoPgBind = custSummCBQuoPgBind;
    }

    public RichSelectBooleanCheckbox getCustSummCBQuoPgBind() {
        return custSummCBQuoPgBind;
    }

    public void setProductGrpDetailCBQuoPgBind(RichSelectBooleanCheckbox productGrpDetailCBQuoPgBind) {
        this.productGrpDetailCBQuoPgBind = productGrpDetailCBQuoPgBind;
    }

    public RichSelectBooleanCheckbox getProductGrpDetailCBQuoPgBind() {
        return productGrpDetailCBQuoPgBind;
    }

    public void setProductGrpSummCBQuoPgBind(RichSelectBooleanCheckbox productGrpSummCBQuoPgBind) {
        this.productGrpSummCBQuoPgBind = productGrpSummCBQuoPgBind;
    }

    public RichSelectBooleanCheckbox getProductGrpSummCBQuoPgBind() {
        return productGrpSummCBQuoPgBind;
    }

    public void setSalesManDetailCBQuoPgBind(RichSelectBooleanCheckbox salesManDetailCBQuoPgBind) {
        this.salesManDetailCBQuoPgBind = salesManDetailCBQuoPgBind;
    }

    public RichSelectBooleanCheckbox getSalesManDetailCBQuoPgBind() {
        return salesManDetailCBQuoPgBind;
    }

    public void setSalesManSummCBQuoPgBind(RichSelectBooleanCheckbox salesManSummCBQuoPgBind) {
        this.salesManSummCBQuoPgBind = salesManSummCBQuoPgBind;
    }

    public RichSelectBooleanCheckbox getSalesManSummCBQuoPgBind() {
        return salesManSummCBQuoPgBind;
    }

    public void setItemDetailCBQuoPgBind(RichSelectBooleanCheckbox itemDetailCBQuoPgBind) {
        this.itemDetailCBQuoPgBind = itemDetailCBQuoPgBind;
    }

    public RichSelectBooleanCheckbox getItemDetailCBQuoPgBind() {
        return itemDetailCBQuoPgBind;
    }

    public void setItemSummCBQuoPgBind(RichSelectBooleanCheckbox itemSummCBQuoPgBind) {
        this.itemSummCBQuoPgBind = itemSummCBQuoPgBind;
    }

    public RichSelectBooleanCheckbox getItemSummCBQuoPgBind() {
        return itemSummCBQuoPgBind;
    }

    public void setCustDetailCBPLPgBind(RichSelectBooleanCheckbox custDetailCBPLPgBind) {
        this.custDetailCBPLPgBind = custDetailCBPLPgBind;
    }

    public RichSelectBooleanCheckbox getCustDetailCBPLPgBind() {
        return custDetailCBPLPgBind;
    }

    public void setCustSummCBPLPgBind(RichSelectBooleanCheckbox custSummCBPLPgBind) {
        this.custSummCBPLPgBind = custSummCBPLPgBind;
    }

    public RichSelectBooleanCheckbox getCustSummCBPLPgBind() {
        return custSummCBPLPgBind;
    }

    public void setProductGrpCBPLPgBind(RichSelectBooleanCheckbox productGrpCBPLPgBind) {
        this.productGrpCBPLPgBind = productGrpCBPLPgBind;
    }

    public RichSelectBooleanCheckbox getProductGrpCBPLPgBind() {
        return productGrpCBPLPgBind;
    }

    public void setProductSummCBPLPgBind(RichSelectBooleanCheckbox productSummCBPLPgBind) {
        this.productSummCBPLPgBind = productSummCBPLPgBind;
    }

    public RichSelectBooleanCheckbox getProductSummCBPLPgBind() {
        return productSummCBPLPgBind;
    }

    public void setItemDetailCBPLPgBind(RichSelectBooleanCheckbox itemDetailCBPLPgBind) {
        this.itemDetailCBPLPgBind = itemDetailCBPLPgBind;
    }

    public RichSelectBooleanCheckbox getItemDetailCBPLPgBind() {
        return itemDetailCBPLPgBind;
    }

    public void setItemSummCBPLPgBind(RichSelectBooleanCheckbox itemSummCBPLPgBind) {
        this.itemSummCBPLPgBind = itemSummCBPLPgBind;
    }

    public RichSelectBooleanCheckbox getItemSummCBPLPgBind() {
        return itemSummCBPLPgBind;
    }

    public void setCustDetailCBShipPgBind(RichSelectBooleanCheckbox custDetailCBShipPgBind) {
        this.custDetailCBShipPgBind = custDetailCBShipPgBind;
    }

    public RichSelectBooleanCheckbox getCustDetailCBShipPgBind() {
        return custDetailCBShipPgBind;
    }

    public void setCustSummCBShipPgBind(RichSelectBooleanCheckbox custSummCBShipPgBind) {
        this.custSummCBShipPgBind = custSummCBShipPgBind;
    }

    public RichSelectBooleanCheckbox getCustSummCBShipPgBind() {
        return custSummCBShipPgBind;
    }

    public void setProductGrpDetailCBShipPgBind(RichSelectBooleanCheckbox productGrpDetailCBShipPgBind) {
        this.productGrpDetailCBShipPgBind = productGrpDetailCBShipPgBind;
    }

    public RichSelectBooleanCheckbox getProductGrpDetailCBShipPgBind() {
        return productGrpDetailCBShipPgBind;
    }

    public void setProductGrpSummCBShipPgBind(RichSelectBooleanCheckbox productGrpSummCBShipPgBind) {
        this.productGrpSummCBShipPgBind = productGrpSummCBShipPgBind;
    }

    public RichSelectBooleanCheckbox getProductGrpSummCBShipPgBind() {
        return productGrpSummCBShipPgBind;
    }

    public void setItemDetailCBShipPgBind(RichSelectBooleanCheckbox itemDetailCBShipPgBind) {
        this.itemDetailCBShipPgBind = itemDetailCBShipPgBind;
    }

    public RichSelectBooleanCheckbox getItemDetailCBShipPgBind() {
        return itemDetailCBShipPgBind;
    }

    public void setItemSummCBShipPgBind(RichSelectBooleanCheckbox itemSummCBShipPgBind) {
        this.itemSummCBShipPgBind = itemSummCBShipPgBind;
    }

    public RichSelectBooleanCheckbox getItemSummCBShipPgBind() {
        return itemSummCBShipPgBind;
    }

    public void setSalesManSummCBPgBind(RichSelectBooleanCheckbox salesManSummCBPgBind) {
        this.salesManSummCBPgBind = salesManSummCBPgBind;
    }

    public RichSelectBooleanCheckbox getSalesManSummCBPgBind() {
        return salesManSummCBPgBind;
    }

    public void setCustDetailCBInvPgBind(RichSelectBooleanCheckbox custDetailCBInvPgBind) {
        this.custDetailCBInvPgBind = custDetailCBInvPgBind;
    }

    public RichSelectBooleanCheckbox getCustDetailCBInvPgBind() {
        return custDetailCBInvPgBind;
    }

    public void setCustSummCBInvPgBind(RichSelectBooleanCheckbox custSummCBInvPgBind) {
        this.custSummCBInvPgBind = custSummCBInvPgBind;
    }

    public RichSelectBooleanCheckbox getCustSummCBInvPgBind() {
        return custSummCBInvPgBind;
    }

    public void setProductGrpDetailCBInvPgBind(RichSelectBooleanCheckbox productGrpDetailCBInvPgBind) {
        this.productGrpDetailCBInvPgBind = productGrpDetailCBInvPgBind;
    }

    public RichSelectBooleanCheckbox getProductGrpDetailCBInvPgBind() {
        return productGrpDetailCBInvPgBind;
    }

    public void setProductGrpSummCBInvPgBind(RichSelectBooleanCheckbox productGrpSummCBInvPgBind) {
        this.productGrpSummCBInvPgBind = productGrpSummCBInvPgBind;
    }

    public RichSelectBooleanCheckbox getProductGrpSummCBInvPgBind() {
        return productGrpSummCBInvPgBind;
    }

    public void setSalesManDetailCBInvPgBind(RichSelectBooleanCheckbox salesManDetailCBInvPgBind) {
        this.salesManDetailCBInvPgBind = salesManDetailCBInvPgBind;
    }

    public RichSelectBooleanCheckbox getSalesManDetailCBInvPgBind() {
        return salesManDetailCBInvPgBind;
    }

    public void setSalesManSummCBInvPgBind(RichSelectBooleanCheckbox salesManSummCBInvPgBind) {
        this.salesManSummCBInvPgBind = salesManSummCBInvPgBind;
    }

    public RichSelectBooleanCheckbox getSalesManSummCBInvPgBind() {
        return salesManSummCBInvPgBind;
    }

    public void setItemDetailCBInvPgBind(RichSelectBooleanCheckbox itemDetailCBInvPgBind) {
        this.itemDetailCBInvPgBind = itemDetailCBInvPgBind;
    }

    public RichSelectBooleanCheckbox getItemDetailCBInvPgBind() {
        return itemDetailCBInvPgBind;
    }

    public void setItemSummCBInvPgBind(RichSelectBooleanCheckbox itemSummCBInvPgBind) {
        this.itemSummCBInvPgBind = itemSummCBInvPgBind;
    }

    public RichSelectBooleanCheckbox getItemSummCBInvPgBind() {
        return itemSummCBInvPgBind;
    }

    public void setCustDetailCBRmaPgBind(RichSelectBooleanCheckbox custDetailCBRmaPgBind) {
        this.custDetailCBRmaPgBind = custDetailCBRmaPgBind;
    }

    public RichSelectBooleanCheckbox getCustDetailCBRmaPgBind() {
        return custDetailCBRmaPgBind;
    }

    public void setCustSummCBRmaPgBind(RichSelectBooleanCheckbox custSummCBRmaPgBind) {
        this.custSummCBRmaPgBind = custSummCBRmaPgBind;
    }

    public RichSelectBooleanCheckbox getCustSummCBRmaPgBind() {
        return custSummCBRmaPgBind;
    }

    public void setProductGrpCBRmaPgBind(RichSelectBooleanCheckbox productGrpCBRmaPgBind) {
        this.productGrpCBRmaPgBind = productGrpCBRmaPgBind;
    }

    public RichSelectBooleanCheckbox getProductGrpCBRmaPgBind() {
        return productGrpCBRmaPgBind;
    }

    public void setProductGrpSummCBRmaPgBind(RichSelectBooleanCheckbox productGrpSummCBRmaPgBind) {
        this.productGrpSummCBRmaPgBind = productGrpSummCBRmaPgBind;
    }

    public RichSelectBooleanCheckbox getProductGrpSummCBRmaPgBind() {
        return productGrpSummCBRmaPgBind;
    }

    public void setItemDetailCBRmaPgBind(RichSelectBooleanCheckbox itemDetailCBRmaPgBind) {
        this.itemDetailCBRmaPgBind = itemDetailCBRmaPgBind;
    }

    public RichSelectBooleanCheckbox getItemDetailCBRmaPgBind() {
        return itemDetailCBRmaPgBind;
    }

    public void setItemSummCBRmaPgBind(RichSelectBooleanCheckbox itemSummCBRmaPgBind) {
        this.itemSummCBRmaPgBind = itemSummCBRmaPgBind;
    }

    public RichSelectBooleanCheckbox getItemSummCBRmaPgBind() {
        return itemSummCBRmaPgBind;
    }

    public void setSelectedTabLink(StringBuffer selectedTabLink) {
        this.selectedTabLink = selectedTabLink;
    }

    public StringBuffer getSelectedTabLink() {
        return selectedTabLink;
    }

    public void setQuotationRegisterPgCB(RichSelectBooleanCheckbox quotationRegisterPgCB) {
        this.quotationRegisterPgCB = quotationRegisterPgCB;
    }

    public RichSelectBooleanCheckbox getQuotationRegisterPgCB() {
        return quotationRegisterPgCB;
    }

    public void setOtherRegister(Boolean otherRegister) {
        this.otherRegister = otherRegister;
    }

    public void setOtherRegSummLink(Boolean otherRegSummLink) {
        this.otherRegSummLink = otherRegSummLink;
    }

    public Boolean getOtherRegSummLink() {
        return otherRegSummLink;
    }

    public void setOtherRegSumm(Boolean otherRegSumm) {
        this.otherRegSumm = otherRegSumm;
    }

    public Boolean getOtherRegSumm() {
        return otherRegSumm;
    }

    public Boolean getOtherRegister() {
        return otherRegister;
    }

    public void setOtherRegisterLink(Boolean otherRegisterLink) {
        this.otherRegisterLink = otherRegisterLink;
    }

    public Boolean getOtherRegisterLink() {
        return otherRegisterLink;
    }

    public void setSalesOrderRegisterCBPgBind(RichSelectBooleanCheckbox salesOrderRegisterCBPgBind) {
        this.salesOrderRegisterCBPgBind = salesOrderRegisterCBPgBind;
    }

    public RichSelectBooleanCheckbox getSalesOrderRegisterCBPgBind() {
        return salesOrderRegisterCBPgBind;
    }

    public void setPickListRegisterCBPgBind(RichSelectBooleanCheckbox pickListRegisterCBPgBind) {
        this.pickListRegisterCBPgBind = pickListRegisterCBPgBind;
    }

    public RichSelectBooleanCheckbox getPickListRegisterCBPgBind() {
        return pickListRegisterCBPgBind;
    }

    public void setOpportunityReportCBPgBind(RichSelectBooleanCheckbox opportunityReportCBPgBind) {
        this.opportunityReportCBPgBind = opportunityReportCBPgBind;
    }

    public RichSelectBooleanCheckbox getOpportunityReportCBPgBind() {
        return opportunityReportCBPgBind;
    }

    public void setPickListStatusCBPgBind(RichSelectBooleanCheckbox pickListStatusCBPgBind) {
        this.pickListStatusCBPgBind = pickListStatusCBPgBind;
    }

    public RichSelectBooleanCheckbox getPickListStatusCBPgBind() {
        return pickListStatusCBPgBind;
    }

    public void setStatusLink(Boolean statusLink) {
        this.statusLink = statusLink;
    }

    public Boolean getStatusLink() {
        return statusLink;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setRmaRegisterCBPgBind(RichSelectBooleanCheckbox rmaRegisterCBPgBind) {
        this.rmaRegisterCBPgBind = rmaRegisterCBPgBind;
    }

    public RichSelectBooleanCheckbox getRmaRegisterCBPgBind() {
        return rmaRegisterCBPgBind;
    }

    public void setRmaStatusCBPgBind(RichSelectBooleanCheckbox rmaStatusCBPgBind) {
        this.rmaStatusCBPgBind = rmaStatusCBPgBind;
    }

    public RichSelectBooleanCheckbox getRmaStatusCBPgBind() {
        return rmaStatusCBPgBind;
    }

    public void setCustWiseSummaryCBPrftPgBind(RichSelectBooleanCheckbox custWiseSummaryCBPrftPgBind) {
        this.custWiseSummaryCBPrftPgBind = custWiseSummaryCBPrftPgBind;
    }

    public RichSelectBooleanCheckbox getCustWiseSummaryCBPrftPgBind() {
        return custWiseSummaryCBPrftPgBind;
    }

    public void setCustDetailCBPrftPgBind(RichSelectBooleanCheckbox custDetailCBPrftPgBind) {
        this.custDetailCBPrftPgBind = custDetailCBPrftPgBind;
    }

    public RichSelectBooleanCheckbox getCustDetailCBPrftPgBind() {
        return custDetailCBPrftPgBind;
    }

    public void setSalesManSummaryCBPrftPgBind(RichSelectBooleanCheckbox salesManSummaryCBPrftPgBind) {
        this.salesManSummaryCBPrftPgBind = salesManSummaryCBPrftPgBind;
    }

    public RichSelectBooleanCheckbox getSalesManSummaryCBPrftPgBind() {
        return salesManSummaryCBPrftPgBind;
    }

    public void setSalesManDetailCBPrftPgBind(RichSelectBooleanCheckbox salesManDetailCBPrftPgBind) {
        this.salesManDetailCBPrftPgBind = salesManDetailCBPrftPgBind;
    }

    public RichSelectBooleanCheckbox getSalesManDetailCBPrftPgBind() {
        return salesManDetailCBPrftPgBind;
    }

    public void setProductGroupSummaryCBPrftPgBind(RichSelectBooleanCheckbox productGroupSummaryCBPrftPgBind) {
        this.productGroupSummaryCBPrftPgBind = productGroupSummaryCBPrftPgBind;
    }

    public RichSelectBooleanCheckbox getProductGroupSummaryCBPrftPgBind() {
        return productGroupSummaryCBPrftPgBind;
    }

    public void setProductGroupDetailCBPrftPgBind(RichSelectBooleanCheckbox productGroupDetailCBPrftPgBind) {
        this.productGroupDetailCBPrftPgBind = productGroupDetailCBPrftPgBind;
    }

    public RichSelectBooleanCheckbox getProductGroupDetailCBPrftPgBind() {
        return productGroupDetailCBPrftPgBind;
    }

    public void setItemSummaryCBPrftPgBind(RichSelectBooleanCheckbox itemSummaryCBPrftPgBind) {
        this.itemSummaryCBPrftPgBind = itemSummaryCBPrftPgBind;
    }

    public RichSelectBooleanCheckbox getItemSummaryCBPrftPgBind() {
        return itemSummaryCBPrftPgBind;
    }

    public void setItemDetailCBPrftPgBind(RichSelectBooleanCheckbox itemDetailCBPrftPgBind) {
        this.itemDetailCBPrftPgBind = itemDetailCBPrftPgBind;
    }

    public RichSelectBooleanCheckbox getItemDetailCBPrftPgBind() {
        return itemDetailCBPrftPgBind;
    }

    public void setShipmentStatusCBPgBind(RichSelectBooleanCheckbox shipmentStatusCBPgBind) {
        this.shipmentStatusCBPgBind = shipmentStatusCBPgBind;
    }

    public RichSelectBooleanCheckbox getShipmentStatusCBPgBind() {
        return shipmentStatusCBPgBind;
    }

    public void setShipmentRegisterCBPgBind(RichSelectBooleanCheckbox shipmentRegisterCBPgBind) {
        this.shipmentRegisterCBPgBind = shipmentRegisterCBPgBind;
    }

    public RichSelectBooleanCheckbox getShipmentRegisterCBPgBind() {
        return shipmentRegisterCBPgBind;
    }


    /*  public void setBtcustwiseDetailLink(Boolean btcustwiseDetailLink) {
        this.btcustwiseDetailLink = btcustwiseDetailLink;
    }

    public Boolean getBtcustwiseDetailLink() {
        return btcustwiseDetailLink;
    }

    public void setBtcustwiseSummaryLink(Boolean btcustwiseSummaryLink) {
        this.btcustwiseSummaryLink = btcustwiseSummaryLink;
    }

    public Boolean getBtcustwiseSummaryLink() {
        return btcustwiseSummaryLink;
    }

    public void setBtsalesmanDetailLink(Boolean btsalesmanDetailLink) {
        this.btsalesmanDetailLink = btsalesmanDetailLink;
    }

    public Boolean getBtsalesmanDetailLink() {
        return btsalesmanDetailLink;
    }

    public void setBtsalesmanSummaryLink(Boolean btsalesmanSummaryLink) {
        this.btsalesmanSummaryLink = btsalesmanSummaryLink;
    }

    public Boolean getBtsalesmanSummaryLink() {
        return btsalesmanSummaryLink;
    }

    public void setBtproductgroupwiseDetailLink(Boolean btproductgroupwiseDetailLink) {
        this.btproductgroupwiseDetailLink = btproductgroupwiseDetailLink;
    }

    public Boolean getBtproductgroupwiseDetailLink() {
        return btproductgroupwiseDetailLink;
    }

    public void setBtproductgroupSummaryLink(Boolean btproductgroupSummaryLink) {
        this.btproductgroupSummaryLink = btproductgroupSummaryLink;
    }

    public Boolean getBtproductgroupSummaryLink() {
        return btproductgroupSummaryLink;
    }

    public void setBtitmDetailLink(Boolean btitmDetailLink) {
        this.btitmDetailLink = btitmDetailLink;
    }

    public Boolean getBtitmDetailLink() {
        return btitmDetailLink;
    }

    public void setBtitmSummaryLink(Boolean btitmSummaryLink) {
        this.btitmSummaryLink = btitmSummaryLink;
    }

    public Boolean getBtitmSummaryLink() {
        return btitmSummaryLink;
    }

    public void setBtcustwiseDetail(Boolean btcustwiseDetail) {
        this.btcustwiseDetail = btcustwiseDetail;
    }

    public Boolean getBtcustwiseDetail() {
        return btcustwiseDetail;
    }

    public void setBtcustwiseSummary(Boolean btcustwiseSummary) {
        this.btcustwiseSummary = btcustwiseSummary;
    }

    public Boolean getBtcustwiseSummary() {
        return btcustwiseSummary;
    }

    public void setBtsalesmanDetail(Boolean btsalesmanDetail) {
        this.btsalesmanDetail = btsalesmanDetail;
    }

    public Boolean getBtsalesmanDetail() {
        return btsalesmanDetail;
    }

    public void setBtsalesmanSummary(Boolean btsalesmanSummary) {
        this.btsalesmanSummary = btsalesmanSummary;
    }

    public Boolean getBtsalesmanSummary() {
        return btsalesmanSummary;
    }

    public void setBtproductgroupwiseDetail(Boolean btproductgroupwiseDetail) {
        this.btproductgroupwiseDetail = btproductgroupwiseDetail;
    }

    public Boolean getBtproductgroupwiseDetail() {
        return btproductgroupwiseDetail;
    }

    public void setBtproductgroupSummary(Boolean btproductgroupSummary) {
        this.btproductgroupSummary = btproductgroupSummary;
    }

    public Boolean getBtproductgroupSummary() {
        return btproductgroupSummary;
    }

    public void setBtitmDetail(Boolean btitmDetail) {
        this.btitmDetail = btitmDetail;
    }

    public Boolean getBtitmDetail() {
        return btitmDetail;
    }

    public void setBtitmSummary(Boolean btitmSummary) {
        this.btitmSummary = btitmSummary;
    }

    public Boolean getBtitmSummary() {
        return btitmSummary;
    }


    public void setBttab(StringBuffer bttab) {
        this.bttab = bttab;
    }

    public StringBuffer getBttab() {
        return bttab;
    }

    public void setBtselectedTabLink(StringBuffer btselectedTabLink) {
        this.btselectedTabLink = btselectedTabLink;
    }

    public StringBuffer getBtselectedTabLink() {
        return btselectedTabLink;
    } */

    /**
     *CheckBox Value change Listener where we can set values of radio button
     *
     */

    public void TopRadioGroupVCL(ValueChangeEvent vce) {
        if (vce != null) {
            if (vce.getNewValue() != null) {
                middleRadioGroupPgBind.setValue(null);
                bottomRadioGroupPgBind.setValue(null);

                fileTypeTransPgBind.setValue(vce.getNewValue());
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(middleRadioGroupPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bottomRadioGroupPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(fileTypeTransPgBind);
        }
    }

    public void MiddleRadioGroupVCL(ValueChangeEvent vce) {
        if (vce != null) {
            if (vce.getNewValue() != null) {
                topRadioGroupPgBind.setValue(null);
                bottomRadioGroupPgBind.setValue(null);

                fileTypeTransPgBind.setValue(vce.getNewValue());
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(topRadioGroupPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bottomRadioGroupPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(fileTypeTransPgBind);
        }
    }

    public void BottomRadioGroupVCL(ValueChangeEvent vce) {
        if (vce != null) {
            if (vce.getNewValue() != null) {
                topRadioGroupPgBind.setValue(null);
                middleRadioGroupPgBind.setValue(null);

                fileTypeTransPgBind.setValue(vce.getNewValue());
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(topRadioGroupPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(middleRadioGroupPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(fileTypeTransPgBind);
        }
    }

    /**
     * Code for Check Box Value Change Listener
     *
     */

    public void CustDetailSOVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (orderLOVBind.getValue() != null && orderLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + orderLOVBind.getValue());
                    custwiseDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    custDetailWiseCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailWiseCBPgBind);
                    custwiseDetail = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            custDetailWiseCBPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailWiseCBPgBind);
                            custwiseDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            custwiseDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        custwiseDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void CustSummarySOVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());
            //
            if (orderLOVBind.getValue() != null && orderLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + orderLOVBind.getValue());
                    custwiseSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    custSummaryCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(custSummaryCBPgBind);
                    custwiseSummary = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            custSummaryCBPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummaryCBPgBind);
                            custwiseSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            custwiseSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        custwiseSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ProductGroupDetailSOVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (orderLOVBind.getValue() != null && orderLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + orderLOVBind.getValue());
                    productgroupwiseDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    productGroupDetailCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(productGroupDetailCBPgBind);
                    productgroupwiseDetail = false; // To Disable GoLink
                }

            }

            else {

                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            productGroupDetailCBPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(productGroupDetailCBPgBind);
                            productgroupwiseDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            productgroupwiseDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        productgroupwiseDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ProductGroupSummarySOVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (orderLOVBind.getValue() != null && orderLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + orderLOVBind.getValue());
                    productgroupSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    productGroupSummaryCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(productGroupSummaryCBPgBind);
                    productgroupSummary = false; // To Disable GoLink

                }

            }

            else {
                if (vce.getNewValue() != null) {
                    //System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            productGroupSummaryCBPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(productGroupSummaryCBPgBind);
                            productgroupSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            productgroupSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        productgroupSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void SalesManDetailSOVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (orderLOVBind.getValue() != null && orderLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + orderLOVBind.getValue());
                    salesmanDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    salesManDetailCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(salesManDetailCBPgBind);
                    salesmanDetail = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            salesManDetailCBPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManDetailCBPgBind);
                            salesmanDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            salesmanDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        salesmanDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void SalesManSummarySOVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (orderLOVBind.getValue() != null && orderLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + orderLOVBind.getValue());
                    salesmanSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    salesManSummaryCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(salesManSummaryCBPgBind);
                    salesmanSummary = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            salesManSummaryCBPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManSummaryCBPgBind);
                            salesmanSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            salesmanSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        salesmanSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ItemDetailSOVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (orderLOVBind.getValue() != null && orderLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + orderLOVBind.getValue());
                    itmDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    itemDetailCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBPgBind);
                    itmDetail = false; // To Disable GoLink

                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            itemDetailCBPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBPgBind);
                            itmDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            itmDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        itmDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ItemSummarySOVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (orderLOVBind.getValue() != null && orderLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + orderLOVBind.getValue());
                    itmSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    itemSummaryCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummaryCBPgBind);
                    itmSummary = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            itemSummaryCBPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummaryCBPgBind);
                            itmSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            itmSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        itmSummary = false; // To Disable Go Link
                    }
                }
            }

        }
    }

    public void CustDetailOppCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {

            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (oppNoLOVBind.getValue() != null && oppNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In oppno bind value-->" + oppNoLOVBind.getValue());
                    custwiseDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of oppno");
                    selectedTabLink = null;
                    custDetailCBOppPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBOppPgBind);
                    custwiseDetail = false; // To Disable GoLink
                }

            } else {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                    if (checkBoxValidation()) {
                        custDetailCBOppPgBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBOppPgBind);
                        custwiseDetail = false; // To Disable GoLink

                        showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                    } else {
                        custwiseDetail = true; // To enable GoLInk

                    }
                } else {
                    selectedTabLink = null;
                    custwiseDetail = false; // To Disable Go Link
                }
            }
        }
    }

    public void CustSummOppCBVCL(ValueChangeEvent vce) {
        /*   SaleReportAMImpl am = (SaleReportAMImpl )resolvElDC("eSaleReportAMDataControl"); // get AM
       ViewObject vo=am.getTempVariables();               // get View Object */

        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (oppNoLOVBind.getValue() != null && oppNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In oppno bind value-->" + oppNoLOVBind.getValue());
                    custwiseSummary = true; // To enable GoLInk
                    AdfFacesContext.getCurrentInstance().addPartialTarget(oppCustSumLink);

                } else {
                    System.out.println("in else of oppno");
                    selectedTabLink = null;
                    custSummCBOppPgBind.setValue(false);
                    custwiseSummary = false; // To Disable Go Link
                    AdfFacesContext.getCurrentInstance().addPartialTarget(oppCustSumLink);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBOppPgBind);
                }

            } else {
                System.out.println("in else ");
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                    if (checkBoxValidation()) {
                        custSummCBOppPgBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBOppPgBind);
                        custwiseSummary = false; // To Disable GoLink

                        showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                    } else {
                        custwiseSummary = true; // To enable GoLInk
                        AdfFacesContext.getCurrentInstance().addPartialTarget(oppCustSumLink);
                    }

                } else {
                    selectedTabLink = null;
                    custwiseSummary = false; // To Disable Go Link
                    AdfFacesContext.getCurrentInstance().addPartialTarget(oppCustSumLink);
                }
            }
        }
    }

    public void ProductGrpDetailOppCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (oppNoLOVBind.getValue() != null && oppNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In oppno bind value-->" + oppNoLOVBind.getValue());
                    productgroupwiseDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of oppno");
                    selectedTabLink = null;
                    productGrpDetailCBOppPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpDetailCBOppPgBind);
                    productgroupwiseDetail = false; // To Disable GoLink
                }

            } else {

                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                    if (checkBoxValidation()) {
                        productGrpDetailCBOppPgBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpDetailCBOppPgBind);
                        productgroupwiseDetail = false; // To Disable GoLink

                        showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                    } else {
                        productgroupwiseDetail = true; // To enable GoLInk

                    }
                } else {
                    selectedTabLink = null;
                    productgroupwiseDetail = false; // To Disable Go Link
                }
            }
        }
    }

    public void ProductGrpSummOppCBVCL(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (oppNoLOVBind.getValue() != null && oppNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In oppno bind value-->" + oppNoLOVBind.getValue());
                    productgroupSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of oppno");
                    selectedTabLink = null;
                    productGrpSummCBOppPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBOppPgBind);
                    productgroupSummary = false; // To Disable GoLink
                }

            } else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            productGrpSummCBOppPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBOppPgBind);
                            productgroupSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            productgroupSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        productgroupSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void SalesManDetailOppCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());


            if (oppNoLOVBind.getValue() != null && oppNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In oppno bind value-->" + oppNoLOVBind.getValue());
                    salesmanDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of oppno");
                    selectedTabLink = null;
                    salesManDetailCBOppPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(salesManDetailCBOppPgBind);
                    salesmanDetail = false; // To Disable GoLink
                }

            } else {


                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                    if (checkBoxValidation()) {
                        salesManDetailCBOppPgBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(salesManDetailCBOppPgBind);
                        salesmanDetail = false; // To Disable GoLink

                        showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                    } else {
                        salesmanDetail = true; // To enable GoLInk

                    }
                } else {
                    selectedTabLink = null;
                    salesmanDetail = false; // To Disable Go Link
                }
            }
        }
    }

    public void SalesManSummOppCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (oppNoLOVBind.getValue() != null && oppNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In oppno bind value-->" + oppNoLOVBind.getValue());
                    salesmanSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of oppno");
                    salesManSummCBOppPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(salesManSummCBOppPgBind);
                    salesmanSummary = false; // To Disable GoLink
                }

            }

            else {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                    if (checkBoxValidation()) {
                        salesManSummCBOppPgBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(salesManSummCBOppPgBind);
                        salesmanSummary = false; // To Disable GoLink

                        showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                    } else {
                        salesmanSummary = true; // To enable GoLInk

                    }
                } else {
                    selectedTabLink = null;
                    salesmanSummary = false; // To Disable Go Link
                }
            }
        }
    }

    public void ItemDetailOppCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (oppNoLOVBind.getValue() != null && oppNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In oppno bind value-->" + oppNoLOVBind.getValue());
                    itmDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of oppno");
                    selectedTabLink = null;
                    itemDetailCBOppPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBOppPgBind);
                    itmDetail = false; // To Disable GoLink
                }

            } else {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                    if (checkBoxValidation()) {
                        itemDetailCBOppPgBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBOppPgBind);
                        itmDetail = false; // To Disable GoLink

                        showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                    } else {
                        itmDetail = true; // To enable GoLInk

                    }
                } else {
                    selectedTabLink = null;
                    itmDetail = false; // To Disable Go Link
                }
            }
        }
    }

    public void ItemSummOppCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (oppNoLOVBind.getValue() != null && oppNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In oppno bind value-->" + oppNoLOVBind.getValue());
                    itmSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of oppno");
                    selectedTabLink = null;
                    itemSummCBOppPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBOppPgBind);
                    itmSummary = false; // To Disable GoLink
                }

            } else {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                    if (checkBoxValidation()) {
                        itemSummCBOppPgBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBOppPgBind);
                        itmSummary = false; // To Disable GoLink

                        showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                    } else {
                        itmSummary = true; // To enable GoLInk

                    }
                } else {
                    selectedTabLink = null;
                    itmSummary = false; // To Disable Go Link
                }
            }
        }
    }

    public void CustDetailQuoCBVCL(ValueChangeEvent vce) {


        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (quotNoLOVBind.getValue() != null && quotNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In quotNoLOVBind bind value-->" + quotNoLOVBind.getValue());
                    custwiseDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of oppno");
                    selectedTabLink = null;
                    custDetailCBQuoPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBQuoPgBind);
                    custwiseDetail = false;
                }

            }

            else {


                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                    if (checkBoxValidation()) {
                        custDetailCBQuoPgBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBQuoPgBind);
                        custwiseDetail = false; // To Disable GoLink

                        showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                    } else {
                        custwiseDetail = true; // To enable GoLInk

                    }
                } else {
                    selectedTabLink = null;
                    custwiseDetail = false; // To Disable Go Link
                }
            }
        }
    }

    public void CustSummQuoCBVCL(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (quotNoLOVBind.getValue() != null && quotNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In quotNoLOVBind bind value-->" + quotNoLOVBind.getValue());
                    custwiseSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of oppno");
                    selectedTabLink = null;
                    custSummCBQuoPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBQuoPgBind);
                    custwiseSummary = false;
                }

            }

            else {


                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                    if (checkBoxValidation()) {
                        custSummCBQuoPgBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBQuoPgBind);
                        custwiseSummary = false; // To Disable GoLink

                        showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                    } else {
                        custwiseSummary = true; // To enable GoLInk

                    }
                } else {
                    selectedTabLink = null;
                    custwiseSummary = false; // To Disable Go Link
                }
            }
        }
    }

    public void ProductGrpDetailQuoCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (quotNoLOVBind.getValue() != null && quotNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In quotNoLOVBind bind value-->" + quotNoLOVBind.getValue());
                    productgroupwiseDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of oppno");
                    selectedTabLink = null;
                    productGrpDetailCBQuoPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpDetailCBQuoPgBind);
                    productgroupwiseDetail = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            productGrpDetailCBQuoPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpDetailCBQuoPgBind);
                            productgroupwiseDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            productgroupwiseDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        productgroupwiseDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ProductGroSummQuoCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (quotNoLOVBind.getValue() != null && quotNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In quotNoLOVBind bind value-->" + quotNoLOVBind.getValue());
                    productgroupSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of oppno");
                    selectedTabLink = null;
                    productGrpSummCBQuoPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBQuoPgBind);
                    productgroupSummary = false;
                }

            }

            else {
                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            productGrpSummCBQuoPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBQuoPgBind);
                            productgroupSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            productgroupSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        productgroupSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void SalesManDetailQuoCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (quotNoLOVBind.getValue() != null && quotNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In quotNoLOVBind bind value-->" + quotNoLOVBind.getValue());
                    salesmanDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of oppno");
                    selectedTabLink = null;
                    salesManDetailCBQuoPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(salesManDetailCBQuoPgBind);
                    salesmanDetail = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            salesManDetailCBQuoPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManDetailCBQuoPgBind);
                            salesmanDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            salesmanDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        salesmanDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void SalesManSummQuoCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (quotNoLOVBind.getValue() != null && quotNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In quotNoLOVBind bind value-->" + quotNoLOVBind.getValue());
                    salesmanSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of oppno");
                    selectedTabLink = null;
                    salesManSummCBQuoPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(salesManSummCBQuoPgBind);
                    salesmanSummary = false; // To Disable GoLink
                }

            }

            else {

                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            salesManSummCBQuoPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManSummCBQuoPgBind);
                            salesmanSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            salesmanSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        salesmanSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ItemDetailQuoCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (quotNoLOVBind.getValue() != null && quotNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In quotNoLOVBind bind value-->" + quotNoLOVBind.getValue());
                    itmDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of oppno");
                    selectedTabLink = null;
                    itemDetailCBQuoPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBQuoPgBind);
                    itmDetail = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            itemDetailCBQuoPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBQuoPgBind);
                            itmDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            itmDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        itmDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ItemSummQuoCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (quotNoLOVBind.getValue() != null && quotNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In quotNoLOVBind bind value-->" + quotNoLOVBind.getValue());
                    itmSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of oppno");
                    selectedTabLink = null;
                    itemSummCBQuoPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBQuoPgBind);
                    itmSummary = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            itemSummCBQuoPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBQuoPgBind);
                            itmSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            itmSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        itmSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void CustDetailPLCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (pickNoLOVBind.getValue() != null && pickNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + pickNoLOVBind.getValue());
                    custwiseDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    custDetailCBPLPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBPLPgBind);
                    custwiseDetail = false;
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            custDetailCBPLPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBPLPgBind);
                            custwiseDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            custwiseDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        custwiseDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void CustSummPLCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (pickNoLOVBind.getValue() != null && pickNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + pickNoLOVBind.getValue());
                    custwiseSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    custSummCBPLPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBPLPgBind);
                    custwiseSummary = false; // To Disable GoLink
                }

            }

            else {
                if (vce.getNewValue() != null) {
                    //System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            custSummCBPLPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBPLPgBind);
                            custwiseSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            custwiseSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        custwiseSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ProductGrpDetailPLCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (pickNoLOVBind.getValue() != null && pickNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + pickNoLOVBind.getValue());
                    productgroupwiseDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    productGrpCBPLPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpCBPLPgBind);
                    productgroupwiseDetail = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            productGrpCBPLPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpCBPLPgBind);
                            productgroupwiseDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            productgroupwiseDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        productgroupwiseDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ProductGrpSummPLCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (pickNoLOVBind.getValue() != null && pickNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + pickNoLOVBind.getValue());
                    productgroupSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    productSummCBPLPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(productSummCBPLPgBind);
                    productgroupSummary = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            productSummCBPLPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(productSummCBPLPgBind);
                            productgroupSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            productgroupSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        productgroupSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ItemDetailPLCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (pickNoLOVBind.getValue() != null && pickNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + pickNoLOVBind.getValue());
                    itmDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    itemDetailCBPLPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBPLPgBind);
                    itmDetail = false; // To Disable GoLink

                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            itemDetailCBPLPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBPLPgBind);
                            itmDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            itmDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        itmDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ItemSummPLCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (pickNoLOVBind.getValue() != null && pickNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + pickNoLOVBind.getValue());
                    itmSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    itemSummCBPLPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBPLPgBind);
                    itmSummary = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            itemSummCBPLPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBPLPgBind);
                            itmSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            itmSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        itmSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void CustDetailShipCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (shipNoLOVBind.getValue() != null && shipNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + shipNoLOVBind.getValue());
                    custwiseDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of pick");
                    selectedTabLink = null;
                    custDetailCBShipPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBShipPgBind);
                    custwiseDetail = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            custDetailCBShipPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBShipPgBind);
                            custwiseDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            custwiseDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        custwiseDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void CustSummShipCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (shipNoLOVBind.getValue() != null && shipNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + shipNoLOVBind.getValue());
                    custwiseSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of pick");
                    selectedTabLink = null;
                    custSummCBShipPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBShipPgBind);
                    custwiseSummary = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            custSummCBShipPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBShipPgBind);
                            custwiseSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            custwiseSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        custwiseSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ProductGrpDetailShipCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (shipNoLOVBind.getValue() != null && shipNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + shipNoLOVBind.getValue());
                    productgroupwiseDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    productGrpDetailCBShipPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpDetailCBShipPgBind);
                    productgroupwiseDetail = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            productGrpDetailCBShipPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpDetailCBShipPgBind);
                            productgroupwiseDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            productgroupwiseDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        productgroupwiseDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ProductGrpSummShipCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (shipNoLOVBind.getValue() != null && shipNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + shipNoLOVBind.getValue());
                    productgroupSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    productGrpSummCBShipPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBShipPgBind);
                    productgroupSummary = false; // To Disable GoLink
                }

            }

            else {
                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            productGrpSummCBShipPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBShipPgBind);
                            productgroupSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            productgroupSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        productgroupSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ItemDetailShipCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (shipNoLOVBind.getValue() != null && shipNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + shipNoLOVBind.getValue());
                    itmDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    itemDetailCBShipPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBShipPgBind);
                    itmDetail = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            itemDetailCBShipPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBShipPgBind);
                            itmDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            itmDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        itmDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ItemSummShipCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (shipNoLOVBind.getValue() != null && shipNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + shipNoLOVBind.getValue());
                    itmSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    itemSummCBShipPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBShipPgBind);
                    itmSummary = false; // To Disable GoLink

                }

            }

            else {

                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            itemSummCBShipPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBShipPgBind);
                            itmSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            itmSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        itmSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void CustDetailInvCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (invoiceNoLOVBind.getValue() != null && invoiceNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In invoice bind value-->" + invoiceNoLOVBind.getValue());
                    custwiseDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of invoice");
                    selectedTabLink = null;
                    custDetailCBInvPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBInvPgBind);
                    custwiseDetail = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            custDetailCBInvPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBInvPgBind);
                            custwiseDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            custwiseDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        custwiseDetail = false; // To Disable Go Link
                    }
                }
            }

        }
    }

    public void CustSummInvCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (invoiceNoLOVBind.getValue() != null && invoiceNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In invoice bind value-->" + invoiceNoLOVBind.getValue());
                    custwiseSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of invoice");
                    selectedTabLink = null;
                    custSummCBInvPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBInvPgBind);
                    custwiseSummary = false; // To Disable GoLink
                }

            }

            else {
                if (vce.getNewValue() != null) {
                    //System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            custSummCBInvPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBInvPgBind);
                            custwiseSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            custwiseSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        custwiseSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ProductGrpDetailInvCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (invoiceNoLOVBind.getValue() != null && invoiceNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In rma bind value-->" + invoiceNoLOVBind.getValue());
                    productgroupwiseDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of rma");
                    selectedTabLink = null;
                    productGrpDetailCBInvPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpDetailCBInvPgBind);
                    productgroupwiseDetail = false; // To Disable GoLink
                }

            }

            else {

                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            productGrpDetailCBInvPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpDetailCBInvPgBind);
                            productgroupwiseDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            productgroupwiseDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        productgroupwiseDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ProductGrpSummInvCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (invoiceNoLOVBind.getValue() != null && invoiceNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In invoice bind value-->" + invoiceNoLOVBind.getValue());
                    productgroupSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of invoice");
                    selectedTabLink = null;
                    productGrpSummCBInvPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBInvPgBind);
                    productgroupSummary = false; // To Disable GoLink

                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            productGrpSummCBInvPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBInvPgBind);
                            productgroupSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            productgroupSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        productgroupSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void SalesManDetailInvCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (invoiceNoLOVBind.getValue() != null && invoiceNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In invoice bind value-->" + invoiceNoLOVBind.getValue());
                    salesmanDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of invoice");
                    selectedTabLink = null;
                    salesManDetailCBInvPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(salesManDetailCBInvPgBind);
                    salesmanDetail = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            salesManDetailCBInvPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManDetailCBInvPgBind);
                            salesmanDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            salesmanDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        salesmanDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void SalesManSummInvCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (invoiceNoLOVBind.getValue() != null && invoiceNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In invoice bind value-->" + invoiceNoLOVBind.getValue());
                    salesmanSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of invoice");
                    selectedTabLink = null;
                    salesManSummCBInvPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(salesManSummCBInvPgBind);
                    salesmanSummary = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            salesManSummCBInvPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManSummCBInvPgBind);
                            salesmanSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            salesmanSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        salesmanSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ItemDetailInvCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (invoiceNoLOVBind.getValue() != null && invoiceNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In invoice bind value-->" + invoiceNoLOVBind.getValue());
                    itmDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of invoice");
                    selectedTabLink = null;
                    itemDetailCBInvPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBInvPgBind);
                    itmDetail = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            itemDetailCBInvPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBInvPgBind);
                            itmDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            itmDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        itmDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ItemSummInvCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (invoiceNoLOVBind.getValue() != null && invoiceNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In invoice bind value-->" + invoiceNoLOVBind.getValue());
                    itmSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of invoice");
                    selectedTabLink = null;
                    itemSummCBInvPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBInvPgBind);
                    itmSummary = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            itemSummCBInvPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBInvPgBind);
                            itmSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            itmSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        itmSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void CustDetailRmaCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (rmaNoLOVBind.getValue() != null && rmaNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In rmaNoLOVBind bind value-->" + rmaNoLOVBind.getValue());
                    custwiseDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of rmaNoLOVBind");
                    selectedTabLink = null;
                    custDetailCBRmaPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBRmaPgBind);
                    custwiseDetail = false; // To Disable GoLink
                }

            }

            else {

                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            custDetailCBRmaPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBRmaPgBind);
                            custwiseDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            custwiseDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        custwiseDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void CustSummRmaCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (rmaNoLOVBind.getValue() != null && rmaNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In rma bind value-->" + rmaNoLOVBind.getValue());
                    custwiseSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of rma");
                    selectedTabLink = null;
                    custSummCBRmaPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBRmaPgBind);
                    custwiseSummary = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            custSummCBRmaPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBRmaPgBind);
                            custwiseSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            custwiseSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        custwiseSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ProductGrpDetailRmaCBVCL(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (rmaNoLOVBind.getValue() != null && rmaNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In invoice bind value-->" + rmaNoLOVBind.getValue());
                    productgroupwiseDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of invoice");
                    selectedTabLink = null;
                    productGrpCBRmaPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpCBRmaPgBind);
                    productgroupwiseDetail = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            productGrpCBRmaPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpCBRmaPgBind);
                            productgroupwiseDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            productgroupwiseDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        productgroupwiseDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ProductGrpSummRmaCBVCL(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (rmaNoLOVBind.getValue() != null && rmaNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In rma bind value-->" + rmaNoLOVBind.getValue());
                    productgroupSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of rma");
                    selectedTabLink = null;
                    productGrpSummCBRmaPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBRmaPgBind);
                    productgroupSummary = false; // To Disable GoLink

                }

            }

            else {
                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            productGrpSummCBRmaPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBRmaPgBind);
                            productgroupSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            productgroupSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        productgroupSummary = false; // To Disable Go Link
                    }
                }
            }

        }
    }

    public void ItemDetailRmaCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (rmaNoLOVBind.getValue() != null && rmaNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In rma bind value-->" + rmaNoLOVBind.getValue());
                    itmDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of rma");
                    selectedTabLink = null;
                    itemDetailCBRmaPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBRmaPgBind);
                    itmDetail = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            itemDetailCBRmaPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBRmaPgBind);
                            itmDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            itmDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        itmDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ItemSummRmaCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (rmaNoLOVBind.getValue() != null && rmaNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In rma bind value-->" + rmaNoLOVBind.getValue());
                    itmSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of rma");
                    selectedTabLink = null;
                    itemSummCBRmaPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBRmaPgBind);
                    itmSummary = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            itemSummCBRmaPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBRmaPgBind);
                            itmSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            itmSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        itmSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void QuotationRegisterVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (quotNoLOVBind.getValue() != null && quotNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In quotNoLOVBind bind value-->" + quotNoLOVBind.getValue());
                    otherRegister = true; // To enable GoLInk

                } else {
                    System.out.println("in else of oppno");
                    selectedTabLink = null;
                    quotationRegisterPgCB.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(quotationRegisterPgCB);
                    otherRegister = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            quotationRegisterPgCB.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(quotationRegisterPgCB);
                            otherRegister = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            otherRegister = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        otherRegister = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void SalesOrderRegisterVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (orderLOVBind.getValue() != null && orderLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + orderLOVBind.getValue());
                    otherRegister = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    salesOrderRegisterCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(salesOrderRegisterCBPgBind);
                    otherRegister = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            salesOrderRegisterCBPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(salesOrderRegisterCBPgBind);
                            otherRegister = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            otherRegister = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        otherRegister = false; // To Disable Go Link
                    }
                }
            }

        }
    }

    public void OpportunityReportVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (oppNoLOVBind.getValue() != null && oppNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In oppno bind value-->" + oppNoLOVBind.getValue());
                    otherRegister = true; // To enable GoLInk
                    AdfFacesContext.getCurrentInstance().addPartialTarget(oppRegisterLink);

                } else {
                    System.out.println("in else of oppno");
                    selectedTabLink = null;
                    opportunityReportCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(opportunityReportCBPgBind);
                    otherRegister = false; // To Disable GoLink
                    AdfFacesContext.getCurrentInstance().addPartialTarget(oppRegisterLink);
                }

            } else {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                    if (checkBoxValidation()) {
                        opportunityReportCBPgBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(opportunityReportCBPgBind);
                        otherRegister = false; // To Disable GoLink
                        AdfFacesContext.getCurrentInstance().addPartialTarget(oppRegisterLink);
                        showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                    } else {
                        otherRegister = true; // To enable GoLInk
                        AdfFacesContext.getCurrentInstance().addPartialTarget(oppRegisterLink);
                    }
                } else {
                    selectedTabLink = null;
                    otherRegister = false; // To Disable Go Link
                    AdfFacesContext.getCurrentInstance().addPartialTarget(oppRegisterLink);
                }
            }
        }

    }

    public void PickListRegisterVCL(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (pickNoLOVBind.getValue() != null && pickNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + pickNoLOVBind.getValue());
                    otherRegister = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    pickListRegisterCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(pickListRegisterCBPgBind);
                    otherRegister = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            pickListRegisterCBPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(pickListRegisterCBPgBind);
                            otherRegister = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            otherRegister = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        otherRegister = false; // To Disable Go Link
                    }
                }
            }

        }
    }

    public void PickListStatusVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (pickNoLOVBind.getValue() != null && pickNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + pickNoLOVBind.getValue());
                    status = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    pickListStatusCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(pickListStatusCBPgBind);
                    status = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            pickListStatusCBPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(pickListStatusCBPgBind);
                            status = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            status = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        status = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void RmaRegisterVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (rmaNoLOVBind.getValue() != null && rmaNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In rma bind value-->" + rmaNoLOVBind.getValue());
                    otherRegister = true; // To enable GoLInk

                } else {
                    System.out.println("in else of rma");
                    selectedTabLink = null;
                    rmaRegisterCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(rmaRegisterCBPgBind);
                    otherRegister = false; // To Disable GoLink

                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            rmaRegisterCBPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(rmaRegisterCBPgBind);
                            otherRegister = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            otherRegister = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        otherRegister = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void RmaStatusVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (rmaNoLOVBind.getValue() != null && rmaNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In rma bind value-->" + rmaNoLOVBind.getValue());
                    status = true; // To enable GoLInk

                } else {
                    System.out.println("in else of rma");
                    selectedTabLink = null;
                    rmaStatusCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(rmaStatusCBPgBind);
                    status = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            rmaStatusCBPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(rmaStatusCBPgBind);
                            status = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            status = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        status = false; // To Disable Go Link
                    }
                }
            }

        }
    }

    public void CustomerWiseSummaryPrftCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //   System.out.println("Vce value is " + vce.getNewValue().toString());
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (checkBoxValidation()) {
                    custWiseSummaryCBPrftPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(custWiseSummaryCBPrftPgBind);
                    custwiseSummary = false; // To Disable GoLink

                    showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                } else {
                    custwiseSummary = true; // To enable GoLInk

                }
            } else {
                selectedTabLink = null;
                custwiseSummary = false; // To Disable Go Link
            }
        }
    }

    public void CustomerWiseDetailPrftCBVCL(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {
            //   System.out.println("Vce value is " + vce.getNewValue().toString());
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (checkBoxValidation()) {
                    custDetailCBPrftPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBPrftPgBind);
                    custwiseDetail = false; // To Disable GoLink

                    showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                } else {
                    custwiseDetail = true; // To enable GoLInk

                }
            } else {
                selectedTabLink = null;
                custwiseDetail = false; // To Disable Go Link
            }
        }
    }

    public void SalesManWiseSummaryPrftCBVCL(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {
            //   System.out.println("Vce value is " + vce.getNewValue().toString());
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (checkBoxValidation()) {
                    salesManSummaryCBPrftPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(salesManSummaryCBPrftPgBind);
                    salesmanSummary = false; // To Disable GoLink

                    showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                } else {
                    salesmanSummary = true; // To enable GoLInk

                }
            } else {
                selectedTabLink = null;
                salesmanSummary = false; // To Disable Go Link
            }
        }
    }

    public void SalesManWiseDetailPrftCBVCL(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {
            //   System.out.println("Vce value is " + vce.getNewValue().toString());
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (checkBoxValidation()) {
                    salesManDetailCBPrftPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(salesManDetailCBPrftPgBind);
                    salesmanDetail = false; // To Disable GoLink

                    showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                } else {
                    salesmanDetail = true; // To enable GoLInk

                }
            } else {
                selectedTabLink = null;
                salesmanDetail = false; // To Disable Go Link
            }
        }
    }

    public void ProductGroupWiseSummaryPrftCBVCL(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {
            //   System.out.println("Vce value is " + vce.getNewValue().toString());
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (checkBoxValidation()) {
                    productGroupSummaryCBPrftPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(productGroupSummaryCBPrftPgBind);
                    productgroupSummary = false; // To Disable GoLink

                    showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                } else {
                    productgroupSummary = true; // To enable GoLInk

                }
            } else {
                selectedTabLink = null;
                productgroupSummary = false; // To Disable Go Link
            }
        }
    }

    public void ProductGroupWiseDetailPrftCBVCL(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {
            //   System.out.println("Vce value is " + vce.getNewValue().toString());
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (checkBoxValidation()) {
                    productGroupDetailCBPrftPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(productGroupDetailCBPrftPgBind);
                    productgroupwiseDetail = false; // To Disable GoLink

                    showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                } else {
                    productgroupwiseDetail = true; // To enable GoLInk

                }
            } else {
                selectedTabLink = null;
                productgroupwiseDetail = false; // To Disable Go Link
            }
        }
    }

    public void ItemWiseSummaryPrftCBVCL(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {
            //   System.out.println("Vce value is " + vce.getNewValue().toString());
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (checkBoxValidation()) {
                    itemSummaryCBPrftPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummaryCBPrftPgBind);
                    itmSummary = false; // To Disable GoLink

                    showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                } else {
                    itmSummary = true; // To enable GoLInk

                }
            } else {
                selectedTabLink = null;
                itmSummary = false; // To Disable Go Link
            }
        }
    }

    public void ItemWiseDetailPrftCBVCL(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {
            //   System.out.println("Vce value is " + vce.getNewValue().toString());
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (checkBoxValidation()) {
                    itemDetailCBPrftPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBPrftPgBind);
                    itmDetail = false; // To Disable GoLink

                    showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                } else {
                    itmDetail = true; // To enable GoLInk

                }
            } else {
                selectedTabLink = null;
                itmDetail = false; // To Disable Go Link
            }
        }
    }

    public void ShipmentRegisterVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (shipNoLOVBind.getValue() != null && shipNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + shipNoLOVBind.getValue());
                    otherRegister = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    shipmentRegisterCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(shipmentRegisterCBPgBind);
                    otherRegister = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            shipmentRegisterCBPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(shipmentRegisterCBPgBind);
                            otherRegister = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            otherRegister = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        otherRegister = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void ShipmentStatusVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (shipNoLOVBind.getValue() != null && shipNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + shipNoLOVBind.getValue());
                    status = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    shipmentStatusCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(shipmentStatusCBPgBind);
                    status = false; // To Disable GoLink
                }

            }

            else {

                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            shipmentStatusCBPgBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(shipmentStatusCBPgBind);
                            status = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            status = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        status = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void CustomerwiseCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //   System.out.println("Vce value is " + vce.getNewValue().toString());
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (checkBoxValidation()) {
                    custwiseSummaryCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(custwiseSummaryCBBind);
                    custwiseDetailAnalysis = false; // To Disable GoLink

                    showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                } else {
                    custwiseDetailAnalysis = true; // To enable GoLInk

                }
            } else {
                selectedTabLink = null;
                custwiseDetailAnalysis = false; // To Disable Go Link
            }
        }
    }

    public void ItemWiseanalysisCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //   System.out.println("Vce value is " + vce.getNewValue().toString());
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (checkBoxValidation()) {
                    itemWiseSummaryAnalysisCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itemWiseSummaryAnalysisCBBind);
                    itmwiseSummaryAnalysis = false; // To Disable GoLink

                    showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                } else {
                    itmwiseSummaryAnalysis = true; // To enable GoLInk

                }
            } else {
                selectedTabLink = null;
                itmwiseSummaryAnalysis = false; // To Disable Go Link
            }
        }
    }

    public void GroupWiseanalysisCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //   System.out.println("Vce value is " + vce.getNewValue().toString());
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (checkBoxValidation()) {
                    grpWiseAnalysisCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(grpWiseAnalysisCBBind);
                    grpWiseAnalysis = false; // To Disable GoLink

                    showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                } else {
                    grpWiseAnalysis = true; // To enable GoLInk

                }
            } else {
                selectedTabLink = null;
                grpWiseAnalysis = false; // To Disable Go Link
            }
        }
    }

    public void SalesmanWiseAnalysisCBVCL(ValueChangeEvent vce) {
        // Add event code here...
        if (vce.getNewValue() != null) {
            //   System.out.println("Vce value is " + vce.getNewValue().toString());
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (checkBoxValidation()) {
                    slSManAnalysisCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(slSManAnalysisCBBind);
                    slsmanDetailAnalysis = false; // To Disable GoLink

                    showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                } else {
                    slsmanDetailAnalysis = true; // To enable GoLInk

                }
            } else {
                selectedTabLink = null;
                slsmanDetailAnalysis = false; // To Disable Go Link
            }
        }
    }

    public void setCustomerwiseanalysisLink(RichLink customerwiseanalysisLink) {
        this.customerwiseanalysisLink = customerwiseanalysisLink;
    }

    public RichLink getCustomerwiseanalysisLink() {
        return customerwiseanalysisLink;
    }

    public void setGroupwiseAnalysisCB(RichLink groupwiseAnalysisCB) {
        this.groupwiseAnalysisCB = groupwiseAnalysisCB;
    }

    public RichLink getGroupwiseAnalysisCB() {
        return groupwiseAnalysisCB;
    }

    public void setSalesmanAnaysisLink(RichLink salesmanAnaysisLink) {
        this.salesmanAnaysisLink = salesmanAnaysisLink;
    }

    public RichLink getSalesmanAnaysisLink() {
        return salesmanAnaysisLink;
    }

    public void setItemWiseAnalysisLink(RichLink itemWiseAnalysisLink) {
        this.itemWiseAnalysisLink = itemWiseAnalysisLink;
    }

    public RichLink getItemWiseAnalysisLink() {
        return itemWiseAnalysisLink;
    }

    public void SLSAnalysisTabDL(DisclosureEvent dE) {
        if (dE.isExpanded()) {


            tab = new StringBuffer("Analysis");
        } else {
            selectedTabLink = null;

            slSManAnalysisCBBind.setValue(false);
            grpWiseAnalysisCBBind.setValue(false);
            itemWiseSummaryAnalysisCBBind.setValue(false);
            custwiseSummaryCBBind.setValue(false);
            itemWiseAnalysisLink.setVisible(false);
            salesmanAnaysisLink.setVisible(false);
            groupwiseAnalysisCB.setVisible(false);
            customerwiseanalysisLink.setVisible(false);

            AdfFacesContext.getCurrentInstance().addPartialTarget(slSManAnalysisCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(grpWiseAnalysisCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemWiseSummaryAnalysisCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custwiseSummaryCBBind);

        }
    }

    public void setCustwiseSummaryCBBind(RichSelectBooleanCheckbox custwiseSummaryCBBind) {
        this.custwiseSummaryCBBind = custwiseSummaryCBBind;
    }

    public RichSelectBooleanCheckbox getCustwiseSummaryCBBind() {
        return custwiseSummaryCBBind;
    }

    public void setItemWiseSummaryAnalysisCBBind(RichSelectBooleanCheckbox itemWiseSummaryAnalysisCBBind) {
        this.itemWiseSummaryAnalysisCBBind = itemWiseSummaryAnalysisCBBind;
    }

    public RichSelectBooleanCheckbox getItemWiseSummaryAnalysisCBBind() {
        return itemWiseSummaryAnalysisCBBind;
    }

    public void setGrpWiseAnalysisCBBind(RichSelectBooleanCheckbox grpWiseAnalysisCBBind) {
        this.grpWiseAnalysisCBBind = grpWiseAnalysisCBBind;
    }

    public RichSelectBooleanCheckbox getGrpWiseAnalysisCBBind() {
        return grpWiseAnalysisCBBind;
    }

    public void setSlSManAnalysisCBBind(RichSelectBooleanCheckbox slSManAnalysisCBBind) {
        this.slSManAnalysisCBBind = slSManAnalysisCBBind;
    }

    public RichSelectBooleanCheckbox getSlSManAnalysisCBBind() {
        return slSManAnalysisCBBind;
    }


    public void setOppStatusBind(RichSelectOneChoice oppStatusBind) {
        this.oppStatusBind = oppStatusBind;
    }

    public RichSelectOneChoice getOppStatusBind() {
        return oppStatusBind;
    }


    public void oppNoVL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            startDate.setValue(null);
            startDate.resetValue();
            AdfFacesContext.getCurrentInstance().addPartialTarget(startDate);
            endDate.setValue(null);
            endDate.resetValue();
            AdfFacesContext.getCurrentInstance().addPartialTarget(endDate);
            OperationBinding ob = getBindings().getOperationBinding("resetAction");
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppStatusBind);
            selectedTabLink = null;
            custSummCBOppPgBind.setValue(false);
            custwiseSummary = false; // To Disable Go Link
            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBOppPgBind);
            custDetailCBOppPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBOppPgBind);
            custwiseDetail = false;
            salesManSummCBOppPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManSummCBOppPgBind);
            salesmanSummary = false; // To Disable GoLink
            salesManDetailCBOppPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManDetailCBOppPgBind);
            salesmanDetail = false; // To Disable GoLink
            productGrpSummCBOppPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBOppPgBind);
            productgroupSummary = false; // To Disable GoLink
            productGrpDetailCBOppPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpDetailCBOppPgBind);
            productgroupwiseDetail = false; // To Disable GoLink
            itemSummCBOppPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBOppPgBind);
            itmSummary = false; // To Disable GoLink
            itemDetailCBOppPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBOppPgBind);
            itmDetail = false; // To Disable GoLink
            opportunityReportCBPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(opportunityReportCBPgBind);
            otherRegister = false; // To Disable GoLink

        }

    }

    public void oppNoStatusVL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppNoLOVBind);
        }

    }

    public void quotationNoBind(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            startDate.setValue(null);
            endDate.setValue(null);
            OperationBinding ob = getBindings().getOperationBinding("resetAction");
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(statusQuotBind);
            custSummCBQuoPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBQuoPgBind);
            custwiseSummary = false;

            custDetailCBQuoPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBQuoPgBind);
            custwiseDetail = false;

            productGrpSummCBQuoPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBQuoPgBind);
            productgroupSummary = false;

            productGrpDetailCBQuoPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpDetailCBQuoPgBind);
            productgroupwiseDetail = false;

            salesManSummCBQuoPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManSummCBQuoPgBind);
            salesmanSummary = false; // To Disable GoLink

            salesManDetailCBQuoPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManDetailCBQuoPgBind);
            salesmanDetail = false; // To Disable GoLink

            itemSummCBQuoPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBQuoPgBind);
            itmSummary = false; // To Disable GoLink

            itemDetailCBQuoPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBQuoPgBind);
            itmDetail = false; // To Disable GoLink
            quotationRegisterPgCB.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotationRegisterPgCB);
            otherRegister = false; // To Disable GoLink
        }
    }

    public void statusQuotVL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotNoLOVBind);
        }
    }


    public void setstatusQuotBind(RichSelectOneChoice statusQuotBind) {
        this.statusQuotBind = statusQuotBind;
    }

    public RichSelectOneChoice getstatusQuotBind() {
        return statusQuotBind;
    }

    public void orderNoVL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            startDate.setValue(null);
            endDate.setValue(null);
            OperationBinding ob = getBindings().getOperationBinding("resetAction");
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(orderStatusBind);


            custSummaryCBPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummaryCBPgBind);
            custwiseSummary = false; // To Disable GoLink


            custDetailWiseCBPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailWiseCBPgBind);
            custwiseDetail = false; // To Disable GoLink

            productGroupSummaryCBPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGroupSummaryCBPgBind);
            productgroupSummary = false; // To Disable GoLink

            productGroupDetailCBPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGroupDetailCBPgBind);
            productgroupwiseDetail = false; // To Disable GoLink

            salesManSummaryCBPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManSummaryCBPgBind);
            salesmanSummary = false; // To Disable GoLink


            salesManDetailCBPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManDetailCBPgBind);
            salesmanDetail = false; // To Disable GoLink

            itemSummaryCBPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummaryCBPgBind);
            itmSummary = false; // To Disable GoLink

            itemDetailCBPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBPgBind);
            itmDetail = false; // To Disable GoLink

            salesOrderRegisterCBPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesOrderRegisterCBPgBind);
            otherRegister = false; // To Disable GoLink


        }
    }

    public void orderStatusVL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(orderLOVBind);
        }
    }

    public void setOrderStatusBind(RichSelectOneChoice orderStatusBind) {
        this.orderStatusBind = orderStatusBind;
    }

    public RichSelectOneChoice getOrderStatusBind() {
        return orderStatusBind;
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

    public void picklistVL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            startDate.setValue(null);
            endDate.setValue(null);
            OperationBinding ob = getBindings().getOperationBinding("resetAction");
            ob.execute();

            custSummCBPLPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBPLPgBind);
            custwiseSummary = false; // To Disable GoLink


            custDetailCBPLPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBPLPgBind);
            custwiseDetail = false;

            productSummCBPLPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productSummCBPLPgBind);
            productgroupSummary = false; // To Disable GoLink


            productGrpCBPLPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpCBPLPgBind);
            productgroupwiseDetail = false; // To Disable GoLink

            pickListRegisterCBPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickListRegisterCBPgBind);
            otherRegister = false; // To Disable GoLink

            pickListStatusCBPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickListStatusCBPgBind);
            status = false; // To Disable GoLink


            itemSummCBPLPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBPLPgBind);
            itmSummary = false; // To Disable GoLink

            itemDetailCBPLPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBPLPgBind);
            itmDetail = false; // To Disable GoLink

            pickBarDetCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickBarDetCBBind);
            pickBar = false; // To Disable GoLink

        }
    }


    public void shipNoVL(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {
            startDate.setValue(null);
            endDate.setValue(null);
            OperationBinding ob = getBindings().getOperationBinding("resetAction");
            ob.execute();
            custSummCBShipPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBShipPgBind);
            custwiseSummary = false; // To Disable GoLink
            productGrpSummCBShipPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBShipPgBind);
            productgroupSummary = false; // To Disable GoLink
            custDetailCBShipPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBShipPgBind);
            custwiseDetail = false; // To Disable GoLink
            productGrpDetailCBShipPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpDetailCBShipPgBind);
            productgroupwiseDetail = false; // To Disable GoLink

            shipmentRegisterCBPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipmentRegisterCBPgBind);
            otherRegister = false; // To Disable GoLink

            shipmentStatusCBPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipmentStatusCBPgBind);
            status = false; // To Disable GoLink

            itemSummCBShipPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBShipPgBind);
            itmSummary = false; // To Disable GoLink

            itemDetailCBShipPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBShipPgBind);
            itmDetail = false; // To Disable GoLink
        }
    }

    public void InvoiceNoVL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {

            startDate.setValue(null);
            endDate.setValue(null);
            OperationBinding ob = getBindings().getOperationBinding("resetAction");
            ob.execute();
            custSummCBInvPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBInvPgBind);
            custwiseSummary = false; // To Disable GoLink

            custDetailCBInvPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBInvPgBind);
            custwiseDetail = false; // To Disable GoLink

            productGrpSummCBInvPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBInvPgBind);
            productgroupSummary = false; // To Disable GoLink

            productGrpDetailCBInvPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpDetailCBInvPgBind);
            productgroupwiseDetail = false; // To Disable GoLink
            salesManSummCBInvPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManSummCBInvPgBind);
            salesmanSummary = false; // To Disable GoLink
            salesManDetailCBInvPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManDetailCBInvPgBind);
            salesmanDetail = false; // To Disable GoLink
            itemSummCBInvPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBInvPgBind);
            itmSummary = false; // To Disable GoLink
            itemDetailCBInvPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBInvPgBind);
            itmDetail = false; // To Disable GoLink
            invRegCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invRegCBBind);
            otherRegister = false;
            invRegSummCB.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invRegSummCB);
            otherRegSumm = false;
            invVehicleSummCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invVehicleSummCBBind);
            InvVeh = false;
            taxCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(taxCBBind);
            Tax = false;

        }
    }


    public void rmaNoVL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            startDate.setValue(null);
            endDate.setValue(null);
            OperationBinding ob = getBindings().getOperationBinding("resetAction");
            ob.execute();
            custSummCBRmaPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBRmaPgBind);
            custwiseSummary = false; // To Disable GoLink
            custDetailCBRmaPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBRmaPgBind);
            custwiseDetail = false; // To Disable GoLink
            productGrpSummCBRmaPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBRmaPgBind);
            productgroupSummary = false; // To Disable GoLink
            productGrpCBRmaPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpCBRmaPgBind);
            productgroupwiseDetail = false; // To Disable GoLink
            rmaStatusCBPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmaStatusCBPgBind);
            status = false; // To Disable GoLink
            rmaRegisterCBPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmaRegisterCBPgBind);
            otherRegister = false; // To Disable GoLink
            itemSummCBRmaPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBRmaPgBind);
            itmSummary = false; // To Disable GoLink
            itemDetailCBRmaPgBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBRmaPgBind);
            itmDetail = false; // To Disable GoLink
        }
    }

    public static BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void resetAL(ActionEvent actionEvent) {
        //        if (iteratorPGLBind.isVisible() == true) {
        //            iteratorPGLBind.setVisible(false);
        //            AdfFacesContext.getCurrentInstance().addPartialTarget(iteratorPGLBind);
        //        }
        OperationBinding ob = getBindings().getOperationBinding("resetAction");
        ob.execute();
    }

    public void setOpportunityTabBind(RichShowDetailItem opportunityTabBind) {
        this.opportunityTabBind = opportunityTabBind;
    }

    public RichShowDetailItem getOpportunityTabBind() {
        return opportunityTabBind;
    }

    public void DocNoVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            startDate.setValue(null);
            startDate.resetValue();
            AdfFacesContext.getCurrentInstance().addPartialTarget(startDate);
            endDate.setValue(null);
            endDate.resetValue();
            AdfFacesContext.getCurrentInstance().addPartialTarget(endDate);
            OperationBinding ob = getBindings().getOperationBinding("resetAction");
            ob.execute();

            // AdfFacesContext.getCurrentInstance().addPartialTarget(dcStageBind);
            selectedTabLink = null;

            dcRegisterCBBind.setValue(false);
            DCRegister = false; // To Disable Go Link
            AdfFacesContext.getCurrentInstance().addPartialTarget(dcProductiveSalonCBBind);
            dcProductiveSalonCBBind.setValue(false);
            DCProductiveSalon = false; // To Disable Go Link
            AdfFacesContext.getCurrentInstance().addPartialTarget(dcProductiveSalonCBBind);


        }
    }


    public void setDcDocIdBind(RichSelectOneChoice dcDocIdBind) {
        this.dcDocIdBind = dcDocIdBind;
    }

    public RichSelectOneChoice getDcDocIdBind() {
        return dcDocIdBind;
    }

    public void DCStageNameVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(dcDocIdBind);
        }
    }

    public void DCTabDL(DisclosureEvent dce) {
        if (dce.isExpanded()) {

            tab = new StringBuffer("DC");
            SaleReportAMImpl am = (SaleReportAMImpl) resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("DCDocIdTrans", null);
            dcDocIdBind.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dcDocIdBind);
        } else {
            selectedTabLink = null;
            SaleReportAMImpl am = (SaleReportAMImpl) resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("DCDocIdTrans", null);
            System.out.println("DCDocIdbind value-->" + dcDocIdBind.getValue());
            dcRegisterCBBind.setValue(false);
            dcProductiveSalonCBBind.setValue(false);


            AdfFacesContext.getCurrentInstance().addPartialTarget(dcDocIdBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dcRegisterCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dcProductiveSalonCBBind);

        }
    }

    public void setDcRegisterCBBind(RichSelectBooleanCheckbox dcRegisterCBBind) {
        this.dcRegisterCBBind = dcRegisterCBBind;
    }

    public RichSelectBooleanCheckbox getDcRegisterCBBind() {
        return dcRegisterCBBind;
    }

    public void DCRegisterCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (dcDocIdBind.getValue() != null && dcDocIdBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In DCDocId bind value-->" + dcDocIdBind.getValue());
                    DCRegister = true; // To enable GoLInk

                } else {
                    System.out.println("in else of DCDOcID");
                    selectedTabLink = null;
                    dcRegisterCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(dcRegisterCBBind);
                    DCRegister = false; // To Disable GoLink
                }

            } else {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                    if (checkBoxValidation()) {
                        dcRegisterCBBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(dcRegisterCBBind);
                        DCRegister = false; // To Disable GoLink

                        showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                    } else {
                        DCRegister = true; // To enable GoLInk

                    }
                } else {
                    selectedTabLink = null;
                    DCRegister = false; // To Disable Go Link
                }
            }
        }
    }


    public void setDcRegisterLink(RichLink dcRegisterLink) {
        this.dcRegisterLink = dcRegisterLink;
    }

    public RichLink getDcRegisterLink() {
        return dcRegisterLink;
    }

    public void setDCRegisterLink(Boolean DCRegisterLink) {
        this.DCRegisterLink = DCRegisterLink;
    }

    public Boolean getDCRegisterLink() {
        return DCRegisterLink;
    }

    public void setDCProductiveSalonLink(Boolean DCProductiveSalonLink) {
        this.DCProductiveSalonLink = DCProductiveSalonLink;
    }

    public Boolean getDCProductiveSalonLink() {
        return DCProductiveSalonLink;
    }

    public void DCProductiveSalonCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (dcDocIdBind.getValue() != null && dcDocIdBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In DCDocId bind value-->" + dcDocIdBind.getValue());
                    DCProductiveSalon = true; // To enable GoLInk

                } else {
                    System.out.println("in else of DCDOcID");
                    selectedTabLink = null;
                    dcProductiveSalonCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(dcProductiveSalonCBBind);
                    DCProductiveSalon = false; // To Disable GoLink
                }

            } else {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                    if (checkBoxValidation()) {
                        dcProductiveSalonCBBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(dcProductiveSalonCBBind);
                        DCProductiveSalon = false; // To Disable GoLink

                        showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                    } else {
                        DCProductiveSalon = true; // To enable GoLInk

                    }
                } else {
                    selectedTabLink = null;
                    DCProductiveSalon = false; // To Disable Go Link
                }
            }
        }
    }

    public void setDcProductiveSalonCBBind(RichSelectBooleanCheckbox dcProductiveSalonCBBind) {
        this.dcProductiveSalonCBBind = dcProductiveSalonCBBind;
    }

    public RichSelectBooleanCheckbox getDcProductiveSalonCBBind() {
        return dcProductiveSalonCBBind;
    }

    public void DCLabelVCL(ValueChangeEvent valueChangeEvent) {
        //        dcStageBind.setValue(null);
        //        AdfFacesContext.getCurrentInstance().addPartialTarget(dcStageBind);
        OperationBinding ob = getBindings().getOperationBinding("setLabel");
        ob.execute();
    }

    public void setQoutTabBind(RichShowDetailItem qoutTabBind) {
        this.qoutTabBind = qoutTabBind;
    }

    public RichShowDetailItem getQoutTabBind() {
        return qoutTabBind;
    }

    public void setSoTabBind(RichShowDetailItem soTabBind) {
        this.soTabBind = soTabBind;
    }

    public RichShowDetailItem getSoTabBind() {
        return soTabBind;
    }

    public void setPickTabBind(RichShowDetailItem pickTabBind) {
        this.pickTabBind = pickTabBind;
    }

    public RichShowDetailItem getPickTabBind() {
        return pickTabBind;
    }

    public void setShipTabBind(RichShowDetailItem shipTabBind) {
        this.shipTabBind = shipTabBind;
    }

    public RichShowDetailItem getShipTabBind() {
        return shipTabBind;
    }

    public void setInvTabBind(RichShowDetailItem invTabBind) {
        this.invTabBind = invTabBind;
    }

    public RichShowDetailItem getInvTabBind() {
        return invTabBind;
    }

    public void setRmaTabBind(RichShowDetailItem rmaTabBind) {
        this.rmaTabBind = rmaTabBind;
    }

    public RichShowDetailItem getRmaTabBind() {
        return rmaTabBind;
    }

    public void setPrftTabBind(RichShowDetailItem prftTabBind) {
        this.prftTabBind = prftTabBind;
    }

    public RichShowDetailItem getPrftTabBind() {
        return prftTabBind;
    }

    public void setAnalysisTabBind(RichShowDetailItem analysisTabBind) {
        this.analysisTabBind = analysisTabBind;
    }

    public RichShowDetailItem getAnalysisTabBind() {
        return analysisTabBind;
    }

    public void setDcTabBind(RichShowDetailItem dcTabBind) {
        this.dcTabBind = dcTabBind;
    }

    public RichShowDetailItem getDcTabBind() {
        return dcTabBind;
    }

    public void setOppCustSumLink(RichLink oppCustSumLink) {
        this.oppCustSumLink = oppCustSumLink;
    }

    public RichLink getOppCustSumLink() {
        return oppCustSumLink;
    }

    public void setOppCustDetailLink(RichLink oppCustDetailLink) {
        this.oppCustDetailLink = oppCustDetailLink;
    }

    public RichLink getOppCustDetailLink() {
        return oppCustDetailLink;
    }

    public void setOppPgSummLink(RichLink oppPgSummLink) {
        this.oppPgSummLink = oppPgSummLink;
    }

    public RichLink getOppPgSummLink() {
        return oppPgSummLink;
    }

    public void setOppPgDetailLink(RichLink oppPgDetailLink) {
        this.oppPgDetailLink = oppPgDetailLink;
    }

    public RichLink getOppPgDetailLink() {
        return oppPgDetailLink;
    }

    public void setOppSESummLink(RichLink oppSESummLink) {
        this.oppSESummLink = oppSESummLink;
    }

    public RichLink getOppSESummLink() {
        return oppSESummLink;
    }

    public void setOppSEDetailLink(RichLink oppSEDetailLink) {
        this.oppSEDetailLink = oppSEDetailLink;
    }

    public RichLink getOppSEDetailLink() {
        return oppSEDetailLink;
    }

    public void setOppPRSummLink(RichLink oppPRSummLink) {
        this.oppPRSummLink = oppPRSummLink;
    }

    public RichLink getOppPRSummLink() {
        return oppPRSummLink;
    }

    public void setOppPRDetailLink(RichLink oppPRDetailLink) {
        this.oppPRDetailLink = oppPRDetailLink;
    }

    public RichLink getOppPRDetailLink() {
        return oppPRDetailLink;
    }

    public void setOppRegisterLink(RichLink oppRegisterLink) {
        this.oppRegisterLink = oppRegisterLink;
    }

    public RichLink getOppRegisterLink() {
        return oppRegisterLink;
    }

    public void setStatusBindLink(RichLink statusBindLink) {
        this.statusBindLink = statusBindLink;
    }

    public RichLink getStatusBindLink() {
        return statusBindLink;
    }

    public void setQuotCustSumLink(RichLink quotCustSumLink) {
        this.quotCustSumLink = quotCustSumLink;
    }

    public RichLink getQuotCustSumLink() {
        return quotCustSumLink;
    }

    public void setQuotCustDetailLink(RichLink quotCustDetailLink) {
        this.quotCustDetailLink = quotCustDetailLink;
    }

    public RichLink getQuotCustDetailLink() {
        return quotCustDetailLink;
    }

    public void setQuotPGSummLink(RichLink quotPGSummLink) {
        this.quotPGSummLink = quotPGSummLink;
    }

    public RichLink getQuotPGSummLink() {
        return quotPGSummLink;
    }

    public void setQoutPGDetailLink(RichLink qoutPGDetailLink) {
        this.qoutPGDetailLink = qoutPGDetailLink;
    }

    public RichLink getQoutPGDetailLink() {
        return qoutPGDetailLink;
    }

    public void setQuotPRSummLink(RichLink quotPRSummLink) {
        this.quotPRSummLink = quotPRSummLink;
    }

    public RichLink getQuotPRSummLink() {
        return quotPRSummLink;
    }

    public void setQuotPRdetailLink(RichLink quotPRdetailLink) {
        this.quotPRdetailLink = quotPRdetailLink;
    }

    public RichLink getQuotPRdetailLink() {
        return quotPRdetailLink;
    }

    public void setQuotPRRegisterLink(RichLink quotPRRegisterLink) {
        this.quotPRRegisterLink = quotPRRegisterLink;
    }

    public RichLink getQuotPRRegisterLink() {
        return quotPRRegisterLink;
    }

    public void setQuotSESummLink(RichLink quotSESummLink) {
        this.quotSESummLink = quotSESummLink;
    }

    public RichLink getQuotSESummLink() {
        return quotSESummLink;
    }

    public void setQuotSEDetailLink(RichLink quotSEDetailLink) {
        this.quotSEDetailLink = quotSEDetailLink;
    }

    public RichLink getQuotSEDetailLink() {
        return quotSEDetailLink;
    }

    public void setSoCustSummLink(RichLink soCustSummLink) {
        this.soCustSummLink = soCustSummLink;
    }

    public RichLink getSoCustSummLink() {
        return soCustSummLink;
    }

    public void setSoCustDetailLink(RichLink soCustDetailLink) {
        this.soCustDetailLink = soCustDetailLink;
    }

    public RichLink getSoCustDetailLink() {
        return soCustDetailLink;
    }

    public void setSoPGSummLink(RichLink soPGSummLink) {
        this.soPGSummLink = soPGSummLink;
    }

    public RichLink getSoPGSummLink() {
        return soPGSummLink;
    }

    public void setSoPGDetailLink(RichLink soPGDetailLink) {
        this.soPGDetailLink = soPGDetailLink;
    }

    public RichLink getSoPGDetailLink() {
        return soPGDetailLink;
    }

    public void setSoSESummLink(RichLink soSESummLink) {
        this.soSESummLink = soSESummLink;
    }

    public RichLink getSoSESummLink() {
        return soSESummLink;
    }

    public void setSoSEDetailLink(RichLink soSEDetailLink) {
        this.soSEDetailLink = soSEDetailLink;
    }

    public RichLink getSoSEDetailLink() {
        return soSEDetailLink;
    }

    public void setSoPRSummLink(RichLink soPRSummLink) {
        this.soPRSummLink = soPRSummLink;
    }

    public RichLink getSoPRSummLink() {
        return soPRSummLink;
    }

    public void setSoPRDetailLink(RichLink soPRDetailLink) {
        this.soPRDetailLink = soPRDetailLink;
    }

    public RichLink getSoPRDetailLink() {
        return soPRDetailLink;
    }

    public void setSoRegisterLink(RichLink soRegisterLink) {
        this.soRegisterLink = soRegisterLink;
    }

    public RichLink getSoRegisterLink() {
        return soRegisterLink;
    }

    public void setPickCustSummLink(RichLink pickCustSummLink) {
        this.pickCustSummLink = pickCustSummLink;
    }

    public RichLink getPickCustSummLink() {
        return pickCustSummLink;
    }

    public void setPickCustDetailLink(RichLink pickCustDetailLink) {
        this.pickCustDetailLink = pickCustDetailLink;
    }

    public RichLink getPickCustDetailLink() {
        return pickCustDetailLink;
    }

    public void setPickPGSummLink(RichLink pickPGSummLink) {
        this.pickPGSummLink = pickPGSummLink;
    }

    public RichLink getPickPGSummLink() {
        return pickPGSummLink;
    }

    public void setPickPGDetailLink(RichLink pickPGDetailLink) {
        this.pickPGDetailLink = pickPGDetailLink;
    }

    public RichLink getPickPGDetailLink() {
        return pickPGDetailLink;
    }

    public void setPickRegisterLink(RichLink pickRegisterLink) {
        this.pickRegisterLink = pickRegisterLink;
    }

    public RichLink getPickRegisterLink() {
        return pickRegisterLink;
    }

    public void setPickStatusLink(RichLink pickStatusLink) {
        this.pickStatusLink = pickStatusLink;
    }

    public RichLink getPickStatusLink() {
        return pickStatusLink;
    }

    public void setPickPRSummLink(RichLink pickPRSummLink) {
        this.pickPRSummLink = pickPRSummLink;
    }

    public RichLink getPickPRSummLink() {
        return pickPRSummLink;
    }

    public void setPickPRDetailLink(RichLink pickPRDetailLink) {
        this.pickPRDetailLink = pickPRDetailLink;
    }

    public RichLink getPickPRDetailLink() {
        return pickPRDetailLink;
    }

    public void setShipCustSummLink(RichLink shipCustSummLink) {
        this.shipCustSummLink = shipCustSummLink;
    }

    public RichLink getShipCustSummLink() {
        return shipCustSummLink;
    }

    public void setShipCustDetailLink(RichLink shipCustDetailLink) {
        this.shipCustDetailLink = shipCustDetailLink;
    }

    public RichLink getShipCustDetailLink() {
        return shipCustDetailLink;
    }

    public void setShipPGSummLink(RichLink shipPGSummLink) {
        this.shipPGSummLink = shipPGSummLink;
    }

    public RichLink getShipPGSummLink() {
        return shipPGSummLink;
    }

    public void setShipPgDetailLink(RichLink shipPgDetailLink) {
        this.shipPgDetailLink = shipPgDetailLink;
    }

    public RichLink getShipPgDetailLink() {
        return shipPgDetailLink;
    }

    public void setShipRegisterLink(RichLink shipRegisterLink) {
        this.shipRegisterLink = shipRegisterLink;
    }

    public RichLink getShipRegisterLink() {
        return shipRegisterLink;
    }

    public void setShipStatusLink(RichLink shipStatusLink) {
        this.shipStatusLink = shipStatusLink;
    }

    public RichLink getShipStatusLink() {
        return shipStatusLink;
    }

    public void setShipPRSummLink(RichLink shipPRSummLink) {
        this.shipPRSummLink = shipPRSummLink;
    }

    public RichLink getShipPRSummLink() {
        return shipPRSummLink;
    }

    public void setShipPRDetailLink(RichLink shipPRDetailLink) {
        this.shipPRDetailLink = shipPRDetailLink;
    }

    public RichLink getShipPRDetailLink() {
        return shipPRDetailLink;
    }

    public void setInvCustSummLink(RichLink invCustSummLink) {
        this.invCustSummLink = invCustSummLink;
    }

    public RichLink getInvCustSummLink() {
        return invCustSummLink;
    }

    public void setInvCustDetailLink(RichLink invCustDetailLink) {
        this.invCustDetailLink = invCustDetailLink;
    }

    public RichLink getInvCustDetailLink() {
        return invCustDetailLink;
    }

    public void setInvPgSummLink(RichLink invPgSummLink) {
        this.invPgSummLink = invPgSummLink;
    }

    public RichLink getInvPgSummLink() {
        return invPgSummLink;
    }

    public void setInvPGDetailLink(RichLink invPGDetailLink) {
        this.invPGDetailLink = invPGDetailLink;
    }

    public RichLink getInvPGDetailLink() {
        return invPGDetailLink;
    }

    public void setInvSESummLink(RichLink invSESummLink) {
        this.invSESummLink = invSESummLink;
    }

    public RichLink getInvSESummLink() {
        return invSESummLink;
    }

    public void setInvPRSummLink(RichLink invPRSummLink) {
        this.invPRSummLink = invPRSummLink;
    }

    public RichLink getInvPRSummLink() {
        return invPRSummLink;
    }

    public void setInvPRDetailLink(RichLink invPRDetailLink) {
        this.invPRDetailLink = invPRDetailLink;
    }

    public RichLink getInvPRDetailLink() {
        return invPRDetailLink;
    }

    public void setRmACustSummLink(RichLink rmACustSummLink) {
        this.rmACustSummLink = rmACustSummLink;
    }

    public RichLink getRmACustSummLink() {
        return rmACustSummLink;
    }

    public void setRmACustDetailLink(RichLink rmACustDetailLink) {
        this.rmACustDetailLink = rmACustDetailLink;
    }

    public RichLink getRmACustDetailLink() {
        return rmACustDetailLink;
    }

    public void setRmAPGSummLink(RichLink rmAPGSummLink) {
        this.rmAPGSummLink = rmAPGSummLink;
    }

    public RichLink getRmAPGSummLink() {
        return rmAPGSummLink;
    }

    public void setRmAPGDetailLink(RichLink rmAPGDetailLink) {
        this.rmAPGDetailLink = rmAPGDetailLink;
    }

    public RichLink getRmAPGDetailLink() {
        return rmAPGDetailLink;
    }

    public void setRmARegisterLink(RichLink rmARegisterLink) {
        this.rmARegisterLink = rmARegisterLink;
    }

    public RichLink getRmARegisterLink() {
        return rmARegisterLink;
    }

    public void setRmAStatusLink(RichLink rmAStatusLink) {
        this.rmAStatusLink = rmAStatusLink;
    }

    public RichLink getRmAStatusLink() {
        return rmAStatusLink;
    }

    public void setRmAPRSummLink(RichLink rmAPRSummLink) {
        this.rmAPRSummLink = rmAPRSummLink;
    }

    public RichLink getRmAPRSummLink() {
        return rmAPRSummLink;
    }

    public void setRmAPRDetailLink(RichLink rmAPRDetailLink) {
        this.rmAPRDetailLink = rmAPRDetailLink;
    }

    public RichLink getRmAPRDetailLink() {
        return rmAPRDetailLink;
    }

    public void setPrftCustSummLink(RichLink prftCustSummLink) {
        this.prftCustSummLink = prftCustSummLink;
    }

    public RichLink getPrftCustSummLink() {
        return prftCustSummLink;
    }

    public void setPrftCustDetailLink(RichLink prftCustDetailLink) {
        this.prftCustDetailLink = prftCustDetailLink;
    }

    public RichLink getPrftCustDetailLink() {
        return prftCustDetailLink;
    }

    public void setPrftPGSummLink(RichLink prftPGSummLink) {
        this.prftPGSummLink = prftPGSummLink;
    }

    public RichLink getPrftPGSummLink() {
        return prftPGSummLink;
    }

    public void setPrftPGDetailLink(RichLink prftPGDetailLink) {
        this.prftPGDetailLink = prftPGDetailLink;
    }

    public RichLink getPrftPGDetailLink() {
        return prftPGDetailLink;
    }

    public void setPrftSESummLink(RichLink prftSESummLink) {
        this.prftSESummLink = prftSESummLink;
    }

    public RichLink getPrftSESummLink() {
        return prftSESummLink;
    }

    public void setPrftSEDetailLink(RichLink prftSEDetailLink) {
        this.prftSEDetailLink = prftSEDetailLink;
    }

    public RichLink getPrftSEDetailLink() {
        return prftSEDetailLink;
    }

    public void setPrftPRSummLink(RichLink prftPRSummLink) {
        this.prftPRSummLink = prftPRSummLink;
    }

    public RichLink getPrftPRSummLink() {
        return prftPRSummLink;
    }

    public void setPrftPRDetailLink(RichLink prftPRDetailLink) {
        this.prftPRDetailLink = prftPRDetailLink;
    }

    public RichLink getPrftPRDetailLink() {
        return prftPRDetailLink;
    }

    public void setDcPrdSalonLink(RichLink dcPrdSalonLink) {
        this.dcPrdSalonLink = dcPrdSalonLink;
    }

    public RichLink getDcPrdSalonLink() {
        return dcPrdSalonLink;
    }

    public void setTrackingLink(RichLink trackingLink) {
        this.trackingLink = trackingLink;
    }

    public RichLink getTrackingLink() {
        return trackingLink;
    }

    public void SalesOrderTrackingVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (orderLOVBind.getValue() != null && orderLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In DCDocId bind value-->" + orderLOVBind.getValue());
                    OrderTracking = true; // To enable GoLInk

                } else {
                    System.out.println("in else of DCDOcID");
                    selectedTabLink = null;
                    orderTrackingCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(orderTrackingCBPgBind);
                    OrderTracking = false; // To Disable GoLink
                }

            } else {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                    if (checkBoxValidation()) {
                        orderTrackingCBPgBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(orderTrackingCBPgBind);
                        OrderTracking = false; // To Disable GoLink

                        showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                    } else {
                        OrderTracking = true; // To enable GoLInk

                    }
                } else {
                    selectedTabLink = null;
                    OrderTracking = false; // To Disable Go Link
                }
            }
        }
    }


    public void setOrderTrackingCBPgBind(RichSelectBooleanCheckbox orderTrackingCBPgBind) {
        this.orderTrackingCBPgBind = orderTrackingCBPgBind;
    }

    public RichSelectBooleanCheckbox getOrderTrackingCBPgBind() {
        return orderTrackingCBPgBind;
    }

    public void setMarginBind(RichSelectBooleanCheckbox marginBind) {
        this.marginBind = marginBind;
    }

    public RichSelectBooleanCheckbox getMarginBind() {
        return marginBind;
    }

    public void DownloadedReportVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //   System.out.println("Vce value is " + vce.getNewValue().toString());
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (checkBoxValidation()) {
                    downloadedReptBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(downloadedReptBind);
                    DownloadReport = false; // To Disable GoLink

                    showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                } else {
                    DownloadReport = true; // To enable GoLInk

                }
            } else {
                selectedTabLink = null;
                DownloadReport = false; // To Disable Go Link
            }
        }
    }

    public void setDownloadedReptBind(RichSelectBooleanCheckbox downloadedReptBind) {
        this.downloadedReptBind = downloadedReptBind;
    }

    public RichSelectBooleanCheckbox getDownloadedReptBind() {
        return downloadedReptBind;
    }

    public void setDownloadReportLink(RichLink downloadReportLink) {
        this.downloadReportLink = downloadReportLink;
    }

    public RichLink getDownloadReportLink() {
        return downloadReportLink;
    }

    public void setSalesOrderPendingCB(RichSelectBooleanCheckbox salesOrderPendingCB) {
        this.salesOrderPendingCB = salesOrderPendingCB;
    }

    public RichSelectBooleanCheckbox getSalesOrderPendingCB() {
        return salesOrderPendingCB;
    }

    public void setSalesOrderPendingCBBind(RichSelectBooleanCheckbox salesOrderPendingCBBind) {
        this.salesOrderPendingCBBind = salesOrderPendingCBBind;
    }

    public RichSelectBooleanCheckbox getSalesOrderPendingCBBind() {
        return salesOrderPendingCBBind;
    }

    public void setOrderPendingLinkBind(RichLink orderPendingLinkBind) {
        this.orderPendingLinkBind = orderPendingLinkBind;
    }

    public RichLink getOrderPendingLinkBind() {
        return orderPendingLinkBind;
    }

    public void pendingOrderCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());
            //
            if (orderLOVBind.getValue() != null && orderLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order pending bind value-->" + orderLOVBind.getValue());
                    otherPending = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    salesOrderPendingCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(salesOrderPendingCBBind);
                    otherPending = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            salesOrderPendingCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(salesOrderPendingCBBind);
                            otherPending = false; // To Disable GoLink
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            otherPending = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        otherPending = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void deleteAttrName(ActionEvent actionEvent) {
        RichLink ob = (RichLink) actionEvent.getSource();
        System.out.println("Atribute Name to delete1=" + ob.getAttributes().get("newgrp"));
        String grpName = (String) ob.getAttributes().get("newgrp");
        System.out.println("Attribute Name to delete=" + grpName);

        this.b.remove(grpName);
        this.newmap.remove(grpName);
        System.out.println(newmap);
        System.out.println(newmap.size());
        if (newmap.size() == 0) {
            if (iteratorPGLBind.isVisible() == true) {
                iteratorPGLBind.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(iteratorPGLBind);
            }
        }
        this.setAttribute1("test");
        this.setAttribute2("test");
        this.setAttribute3("test");
        this.setAttribute4("test");
        this.setAttribute5("test");
        this.newsize = b.size();
    }

    public void addAttrAction(ActionEvent actionEvent) {
        if (attrValPgBind.getValue() != null && attrIdPgBind.getValue() != null) {
            if (iteratorPGLBind.isVisible() == false) {
                iteratorPGLBind.setVisible(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(iteratorPGLBind);
            }

            System.out.println("GROUP NAME" + attrValPgBind.getValue().toString());
            b.add(attrValPgBind.getValue().toString());
            newmap.put(attrValPgBind.getValue().toString(), attrIdPgBind.getValue().toString());
            this.newsize = b.size();
            System.out.println("Array List" + b.size());
            System.out.println("Map size" + newmap.size());
            this.attrValPgBind.setValue(null);
            OperationBinding ob =
                BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("addpartialattributeNamAm");
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(attrValPgBind);


        }


    }

    public void setAttrTypePgBind(RichInputListOfValues attrTypePgBind) {
        this.attrTypePgBind = attrTypePgBind;
    }

    public RichInputListOfValues getAttrTypePgBind() {
        return attrTypePgBind;
    }

    public void setAttrValPgBind(RichInputComboboxListOfValues attrValPgBind) {
        this.attrValPgBind = attrValPgBind;
    }

    public RichInputComboboxListOfValues getAttrValPgBind() {
        return attrValPgBind;
    }

    public void setAttrIdPgBind(RichInputText attrIdPgBind) {
        this.attrIdPgBind = attrIdPgBind;
    }

    public RichInputText getAttrIdPgBind() {
        return attrIdPgBind;
    }

    public void setIteratorPGLBind(RichPanelGroupLayout iteratorPGLBind) {
        this.iteratorPGLBind = iteratorPGLBind;
    }

    public RichPanelGroupLayout getIteratorPGLBind() {
        return iteratorPGLBind;
    }

    public void OrderNoCBBind(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void OrderNoDSCBVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setOrderNoCBBind(RichInputListOfValues orderNoCBBind) {
        this.orderNoCBBind = orderNoCBBind;
    }

    public RichInputListOfValues getOrderNoCBBind() {
        return orderNoCBBind;
    }

    public void setDsCustSummCBBind(RichSelectBooleanCheckbox dsCustSummCBBind) {
        this.dsCustSummCBBind = dsCustSummCBBind;
    }

    public RichSelectBooleanCheckbox getDsCustSummCBBind() {
        return dsCustSummCBBind;
    }

    public void dsCustSummCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());
            //
            if (dsOrderNoLOVBind.getValue() != null && dsOrderNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + dsOrderNoLOVBind.getValue());
                    custwiseSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    dsCustSummCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(dsCustSummCBBind);
                    custwiseSummary = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            dsCustSummCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dsCustSummCBBind);
                            custwiseSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            custwiseSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        custwiseSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void setDsCustDetCBBind(RichSelectBooleanCheckbox dsCustDetCBBind) {
        this.dsCustDetCBBind = dsCustDetCBBind;
    }

    public RichSelectBooleanCheckbox getDsCustDetCBBind() {
        return dsCustDetCBBind;
    }

    public void dsCustDetCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (dsOrderNoLOVBind.getValue() != null && dsOrderNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + dsOrderNoLOVBind.getValue());
                    custwiseDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    dsCustDetCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(dsCustDetCBBind);
                    custwiseDetail = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            dsCustDetCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dsCustDetCBBind);
                            custwiseDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            custwiseDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        custwiseDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void dsPrdGrpSummCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (dsOrderNoLOVBind.getValue() != null && dsOrderNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + dsOrderNoLOVBind.getValue());
                    productgroupSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    dsPrdGrpSummCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(dsPrdGrpSummCBBind);
                    productgroupSummary = false; // To Disable GoLink

                }

            }

            else {
                if (vce.getNewValue() != null) {
                    //System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            dsPrdGrpSummCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dsPrdGrpSummCBBind);
                            productgroupSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            productgroupSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        productgroupSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void setDsPrdGrpSummCBBind(RichSelectBooleanCheckbox dsPrdGrpSummCBBind) {
        this.dsPrdGrpSummCBBind = dsPrdGrpSummCBBind;
    }

    public RichSelectBooleanCheckbox getDsPrdGrpSummCBBind() {
        return dsPrdGrpSummCBBind;
    }

    public void dsPrdGrpDetCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (dsOrderNoLOVBind.getValue() != null && dsOrderNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + dsOrderNoLOVBind.getValue());
                    productgroupwiseDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    dsPrdGrpDetCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(dsPrdGrpDetCBBind);
                    productgroupwiseDetail = false; // To Disable GoLink
                }

            }

            else {

                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            dsPrdGrpDetCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dsPrdGrpDetCBBind);
                            productgroupwiseDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            productgroupwiseDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        productgroupwiseDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void setDsPrdGrpDetCBBind(RichSelectBooleanCheckbox dsPrdGrpDetCBBind) {
        this.dsPrdGrpDetCBBind = dsPrdGrpDetCBBind;
    }

    public RichSelectBooleanCheckbox getDsPrdGrpDetCBBind() {
        return dsPrdGrpDetCBBind;
    }

    public void dsWhSummCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (dsOrderNoLOVBind.getValue() != null && dsOrderNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + dsOrderNoLOVBind.getValue());
                    whSumm = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    dsWhSummCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(dsWhSummCBBind);
                    whSumm = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            dsWhSummCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dsWhSummCBBind);
                            whSumm = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            whSumm = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        whSumm = false; // To Disable Go Link
                    }
                }
            }

        }

    }

    public void setDsWhSummCBBind(RichSelectBooleanCheckbox dsWhSummCBBind) {
        this.dsWhSummCBBind = dsWhSummCBBind;
    }

    public RichSelectBooleanCheckbox getDsWhSummCBBind() {
        return dsWhSummCBBind;
    }

    public void dsWhDetCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (dsOrderNoLOVBind.getValue() != null && dsOrderNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + dsOrderNoLOVBind.getValue());
                    whDet = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    dsWhDetCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(dsWhDetCBBind);
                    whDet = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            dsWhDetCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dsWhDetCBBind);
                            whDet = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            whDet = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        whDet = false; // To Disable Go Link
                    }
                }
            }

        }
    }

    public void setDsWhDetCBBind(RichSelectBooleanCheckbox dsWhDetCBBind) {
        this.dsWhDetCBBind = dsWhDetCBBind;
    }

    public RichSelectBooleanCheckbox getDsWhDetCBBind() {
        return dsWhDetCBBind;
    }

    public void dsItmSummCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (dsOrderNoLOVBind.getValue() != null && dsOrderNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + dsOrderNoLOVBind.getValue());
                    itmSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    dsItmSummCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(dsItmSummCBBind);
                    itmSummary = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            dsItmSummCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dsItmSummCBBind);
                            itmSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            itmSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        itmSummary = false; // To Disable Go Link
                    }
                }
            }

        }

    }

    public void setDsItmSummCBBind(RichSelectBooleanCheckbox dsItmSummCBBind) {
        this.dsItmSummCBBind = dsItmSummCBBind;
    }

    public RichSelectBooleanCheckbox getDsItmSummCBBind() {
        return dsItmSummCBBind;
    }

    public void dsItmDetCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (dsOrderNoLOVBind.getValue() != null && dsOrderNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + dsOrderNoLOVBind.getValue());
                    itmDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    dsItmDetCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(dsItmDetCBBind);
                    itmDetail = false; // To Disable GoLink

                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            dsItmDetCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dsItmDetCBBind);
                            itmDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            itmDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        itmDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void setDsItmDetCBBind(RichSelectBooleanCheckbox dsItmDetCBBind) {
        this.dsItmDetCBBind = dsItmDetCBBind;
    }

    public RichSelectBooleanCheckbox getDsItmDetCBBind() {
        return dsItmDetCBBind;
    }

    public void DeliveryScheduleDL(DisclosureEvent dE) {
        if (dE.isExpanded()) {

            tab = new StringBuffer("DS");
            SaleReportAMImpl am = (SaleReportAMImpl) resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("OrderNoTrans", null);
            vo.getCurrentRow().setAttribute("WhIdTrans", null);
            vo.getCurrentRow().setAttribute("WhNmTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsOrderNoLOVBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsWhLOVBind);
        } else {
            selectedTabLink = null;

            dsCustDetCBBind.setValue(false);
            dsCustSummCBBind.setValue(false);
            dsWhDetCBBind.setValue(false);
            dsWhSummCBBind.setValue(false);
            dsPrdGrpDetCBBind.setValue(false);
            dsPrdGrpSummCBBind.setValue(false);
            dsItmDetCBBind.setValue(false);
            dsItmSummCBBind.setValue(false);
            SaleReportAMImpl am = (SaleReportAMImpl) resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("OrderNoTrans", null);
            vo.getCurrentRow().setAttribute("WhIdTrans", null);
            vo.getCurrentRow().setAttribute("WhNmTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsOrderNoLOVBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsWhLOVBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsCustDetCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsCustSummCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsWhSummCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsWhDetCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsPrdGrpDetCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsPrdGrpSummCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsItmDetCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsItmSummCBBind);

        }
    }

    public void setdsOrderNoLOVBind(RichSelectOneChoice dsOrderNoLOVBind) {
        this.dsOrderNoLOVBind = dsOrderNoLOVBind;
    }

    public RichSelectOneChoice getdsOrderNoLOVBind() {
        return dsOrderNoLOVBind;
    }

    public void dsOrderNoLOVVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            startDate.setValue(null);
            endDate.setValue(null);
            OperationBinding ob = getBindings().getOperationBinding("resetAction");
            ob.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsWhLOVBind);


            dsCustSummCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsCustSummCBBind);
            custwiseSummary = false; // To Disable GoLink


            dsCustDetCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsCustDetCBBind);
            custwiseDetail = false; // To Disable GoLink

            dsPrdGrpSummCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsPrdGrpSummCBBind);
            productgroupSummary = false; // To Disable GoLink

            dsPrdGrpDetCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsPrdGrpDetCBBind);
            productgroupwiseDetail = false; // To Disable GoLink

            dsWhSummCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsWhSummCBBind);
            whSumm = false; // To Disable GoLink


            dsWhDetCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsWhDetCBBind);
            whDet = false; // To Disable GoLink

            dsItmSummCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsItmSummCBBind);
            itmSummary = false; // To Disable GoLink

            dsItmDetCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dsItmSummCBBind);
            itmDetail = false; // To Disable GoLink


        }
    }

    public void setDsWhLOVBind(RichSelectOneChoice dsWhLOVBind) {
        this.dsWhLOVBind = dsWhLOVBind;
    }

    public RichSelectOneChoice getDsWhLOVBind() {
        return dsWhLOVBind;
    }

    public void dsWhLOVVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setDsCustSummLink(RichLink dsCustSummLink) {
        this.dsCustSummLink = dsCustSummLink;
    }

    public RichLink getDsCustSummLink() {
        return dsCustSummLink;
    }

    public void setDsCustDetLink(RichLink dsCustDetLink) {
        this.dsCustDetLink = dsCustDetLink;
    }

    public RichLink getDsCustDetLink() {
        return dsCustDetLink;
    }

    public void setDsPrdGrpSummLink(RichLink dsPrdGrpSummLink) {
        this.dsPrdGrpSummLink = dsPrdGrpSummLink;
    }

    public RichLink getDsPrdGrpSummLink() {
        return dsPrdGrpSummLink;
    }

    public void setDsPrdGrpDetLink(RichLink dsPrdGrpDetLink) {
        this.dsPrdGrpDetLink = dsPrdGrpDetLink;
    }

    public RichLink getDsPrdGrpDetLink() {
        return dsPrdGrpDetLink;
    }

    public void setDsWhSummLink(RichLink dsWhSummLink) {
        this.dsWhSummLink = dsWhSummLink;
    }

    public RichLink getDsWhSummLink() {
        return dsWhSummLink;
    }

    public void setDsWhDetLink(RichLink dsWhDetLink) {
        this.dsWhDetLink = dsWhDetLink;
    }

    public RichLink getDsWhDetLink() {
        return dsWhDetLink;
    }

    public void setDsItmSummLink(RichLink dsItmSummLink) {
        this.dsItmSummLink = dsItmSummLink;
    }

    public RichLink getDsItmSummLink() {
        return dsItmSummLink;
    }

    public void setDsItmDetLink(RichLink dsItmDetLink) {
        this.dsItmDetLink = dsItmDetLink;
    }

    public RichLink getDsItmDetLink() {
        return dsItmDetLink;
    }

    public void setDsTabBind(RichShowDetailItem dsTabBind) {
        this.dsTabBind = dsTabBind;
    }

    public RichShowDetailItem getDsTabBind() {
        return dsTabBind;
    }


    public void setPackCustDetailLink(RichLink packCustDetailLink) {
        this.packCustDetailLink = packCustDetailLink;
    }

    public RichLink getPackCustDetailLink() {
        return packCustDetailLink;
    }

    public void setPackPrdGrpSummLink(RichLink packPrdGrpSummLink) {
        this.packPrdGrpSummLink = packPrdGrpSummLink;
    }

    public RichLink getPackPrdGrpSummLink() {
        return packPrdGrpSummLink;
    }

    public void setPackCustPrdGrpDetail(RichLink packCustPrdGrpDetail) {
        this.packCustPrdGrpDetail = packCustPrdGrpDetail;
    }

    public RichLink getPackCustPrdGrpDetail() {
        return packCustPrdGrpDetail;
    }

    public void setPackOtherRegisterLink(RichLink packOtherRegisterLink) {
        this.packOtherRegisterLink = packOtherRegisterLink;
    }

    public RichLink getPackOtherRegisterLink() {
        return packOtherRegisterLink;
    }

    public void setPackSummaryLink(RichLink packSummaryLink) {
        this.packSummaryLink = packSummaryLink;
    }

    public RichLink getPackSummaryLink() {
        return packSummaryLink;
    }

    public void setPackItemSummLink(RichLink packItemSummLink) {
        this.packItemSummLink = packItemSummLink;
    }

    public RichLink getPackItemSummLink() {
        return packItemSummLink;
    }

    public void setPackItemDetailLink(RichLink packItemDetailLink) {
        this.packItemDetailLink = packItemDetailLink;
    }

    public RichLink getPackItemDetailLink() {
        return packItemDetailLink;
    }

    public void PackTabDL(DisclosureEvent dE) {
        if (dE.isExpanded()) {
            tab = new StringBuffer("PK");

            SaleReportAMImpl am = (SaleReportAMImpl) resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("PackNoLOVTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packNoLOVBind);
        } else {
            selectedTabLink = null;

            packCustDetailCBBind.setValue(false);
            packCustSummCBBind.setValue(false);
            packPrdGrpDetailCBBind.setValue(false);
            packPrdGrpSummCBBind.setValue(false);
            packItemDetailCBBind.setValue(false);
            packItemSummaryCBBind.setValue(false);
            packRegisterCBBind.setValue(false);
            packSummaryCBBind.setValue(false);
            SaleReportAMImpl am = (SaleReportAMImpl) resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("PackNoLOVTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packNoLOVBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packCustDetailCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packCustSummCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packPrdGrpDetailCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packPrdGrpSummCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packItemDetailCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packItemSummaryCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packRegisterCBBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packSummaryCBBind);
        }

    }

    public void setPackCustSummCBBind(RichSelectBooleanCheckbox packCustSummCBBind) {
        this.packCustSummCBBind = packCustSummCBBind;
    }

    public RichSelectBooleanCheckbox getPackCustSummCBBind() {
        return packCustSummCBBind;
    }

    public void packCustSummCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());
            //
            if (packNoLOVBind.getValue() != null && packNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + packNoLOVBind.getValue());
                    custwiseSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    packCustSummCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(packCustSummCBBind);
                    custwiseSummary = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            packCustSummCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(packCustSummCBBind);
                            custwiseSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            custwiseSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        custwiseSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void setPackCustDetailCBBind(RichSelectBooleanCheckbox packCustDetailCBBind) {
        this.packCustDetailCBBind = packCustDetailCBBind;
    }

    public RichSelectBooleanCheckbox getPackCustDetailCBBind() {
        return packCustDetailCBBind;
    }

    public void packCustDetailCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (packNoLOVBind.getValue() != null && packNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + packNoLOVBind.getValue());
                    custwiseDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    packCustDetailCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(packCustDetailCBBind);
                    custwiseDetail = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            packCustDetailCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(packCustDetailCBBind);
                            custwiseDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            custwiseDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        custwiseDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void setPackPrdGrpSummCBBind(RichSelectBooleanCheckbox packPrdGrpSummCBBind) {
        this.packPrdGrpSummCBBind = packPrdGrpSummCBBind;
    }

    public RichSelectBooleanCheckbox getPackPrdGrpSummCBBind() {
        return packPrdGrpSummCBBind;
    }

    public void packPrdGrpSummCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (packNoLOVBind.getValue() != null && packNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + packNoLOVBind.getValue());
                    productgroupSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    packPrdGrpSummCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(packPrdGrpSummCBBind);
                    productgroupSummary = false; // To Disable GoLink

                }

            }

            else {
                if (vce.getNewValue() != null) {
                    //System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            packPrdGrpSummCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(packPrdGrpSummCBBind);
                            productgroupSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            productgroupSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        productgroupSummary = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void setPackPrdGrpDetailCBBind(RichSelectBooleanCheckbox packPrdGrpDetailCBBind) {
        this.packPrdGrpDetailCBBind = packPrdGrpDetailCBBind;
    }

    public RichSelectBooleanCheckbox getPackPrdGrpDetailCBBind() {
        return packPrdGrpDetailCBBind;
    }

    public void packPrdGrpDetailCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (packNoLOVBind.getValue() != null && packNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + packNoLOVBind.getValue());
                    productgroupwiseDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    packPrdGrpDetailCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(packPrdGrpDetailCBBind);
                    productgroupwiseDetail = false; // To Disable GoLink
                }

            }

            else {

                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            packPrdGrpDetailCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(packPrdGrpDetailCBBind);
                            productgroupwiseDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            productgroupwiseDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        productgroupwiseDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void setPackRegisterCBBind(RichSelectBooleanCheckbox packRegisterCBBind) {
        this.packRegisterCBBind = packRegisterCBBind;
    }

    public RichSelectBooleanCheckbox getPackRegisterCBBind() {
        return packRegisterCBBind;
    }

    public void packRegisterCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (packNoLOVBind.getValue() != null && packNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + packNoLOVBind.getValue());
                    otherRegister = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    packRegisterCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(packRegisterCBBind);
                    otherRegister = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            packRegisterCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(packRegisterCBBind);
                            otherRegister = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            otherRegister = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        otherRegister = false; // To Disable Go Link
                    }
                }
            }

        }
    }

    public void setPackSummaryCBBind(RichSelectBooleanCheckbox packSummaryCBBind) {
        this.packSummaryCBBind = packSummaryCBBind;
    }

    public RichSelectBooleanCheckbox getPackSummaryCBBind() {
        return packSummaryCBBind;
    }

    public void packSummaryCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (packNoLOVBind.getValue() != null && packNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + packNoLOVBind.getValue());
                    status = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    packSummaryCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(packSummaryCBBind);
                    status = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            packSummaryCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(packSummaryCBBind);
                            status = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            status = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        status = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void setPackItemSummaryCBBind(RichSelectBooleanCheckbox packItemSummaryCBBind) {
        this.packItemSummaryCBBind = packItemSummaryCBBind;
    }

    public RichSelectBooleanCheckbox getPackItemSummaryCBBind() {
        return packItemSummaryCBBind;
    }

    public void packItemSummaryCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (packNoLOVBind.getValue() != null && packNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + packNoLOVBind.getValue());
                    itmSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    packItemSummaryCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(packItemSummaryCBBind);
                    itmSummary = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            packItemSummaryCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(packItemSummaryCBBind);
                            itmSummary = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            itmSummary = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        itmSummary = false; // To Disable Go Link
                    }
                }
            }

        }
    }

    public void setPackItemDetailCBBind(RichSelectBooleanCheckbox packItemDetailCBBind) {
        this.packItemDetailCBBind = packItemDetailCBBind;
    }

    public RichSelectBooleanCheckbox getPackItemDetailCBBind() {
        return packItemDetailCBBind;
    }

    public void packItemDetailCBBind(ValueChangeEvent vce) {

    }

    public void packItemDetailCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (packNoLOVBind.getValue() != null && packNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + packNoLOVBind.getValue());
                    itmDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    packItemDetailCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(packItemDetailCBBind);
                    itmDetail = false; // To Disable GoLink

                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            packItemDetailCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(packItemDetailCBBind);
                            itmDetail = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            itmDetail = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        itmDetail = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void setPackTabBind(RichShowDetailItem packTabBind) {
        this.packTabBind = packTabBind;
    }

    public RichShowDetailItem getPackTabBind() {
        return packTabBind;
    }

    public void setpackNoLOVBind(RichInputListOfValues packNoLOVBind) {
        this.packNoLOVBind = packNoLOVBind;
    }

    public RichInputListOfValues getpackNoLOVBind() {
        return packNoLOVBind;
    }

    public void PackNoVL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            startDate.setValue(null);
            endDate.setValue(null);
            OperationBinding ob = getBindings().getOperationBinding("resetAction");
            ob.execute();

            packCustSummCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packCustSummCBBind);
            custwiseSummary = false; // To Disable GoLink


            packCustDetailCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packCustDetailCBBind);
            custwiseDetail = false;

            packPrdGrpSummCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packPrdGrpSummCBBind);
            productgroupSummary = false; // To Disable GoLink


            packPrdGrpDetailCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packPrdGrpSummCBBind);
            productgroupwiseDetail = false; // To Disable GoLink

            packRegisterCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packRegisterCBBind);
            otherRegister = false; // To Disable GoLink

            packSummaryCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packSummaryCBBind);
            status = false; // To Disable GoLink


            packItemSummaryCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packItemSummaryCBBind);
            itmSummary = false; // To Disable GoLink

            packItemDetailCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(packItemDetailCBBind);
            itmDetail = false; // To Disable GoLink

        }
    }

    public void setInvRegCBBind(RichSelectBooleanCheckbox invRegCBBind) {
        this.invRegCBBind = invRegCBBind;
    }

    public RichSelectBooleanCheckbox getInvRegCBBind() {
        return invRegCBBind;
    }

    public void invRegCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (invoiceNoLOVBind.getValue() != null && invoiceNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + invoiceNoLOVBind.getValue());
                    otherRegister = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    invRegCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(invRegCBBind);
                    otherRegister = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            invRegCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(invRegCBBind);
                            otherRegister = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            otherRegister = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        otherRegister = false; // To Disable Go Link
                    }
                }
            }

        }
    }

    public void setInvRegCBLink(RichLink invRegCBLink) {
        this.invRegCBLink = invRegCBLink;
    }

    public RichLink getInvRegCBLink() {
        return invRegCBLink;
    }

    public void setCustPriceCBBind(RichSelectBooleanCheckbox custPriceCBBind) {
        this.custPriceCBBind = custPriceCBBind;
    }

    public RichSelectBooleanCheckbox getCustPriceCBBind() {
        return custPriceCBBind;
    }

    public void custPriceSetupVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            System.out.println("in else ");
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (checkBoxValidation()) {
                    custPriceCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(custPriceCBBind);
                    custwiseSummary = false; // To Disable GoLink

                    showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                } else {
                    custwiseSummary = true; // To enable GoLInk
                    AdfFacesContext.getCurrentInstance().addPartialTarget(custPriceCBBind);
                }

            } else {
                selectedTabLink = null;
                custwiseSummary = false; // To Disable Go Link
                AdfFacesContext.getCurrentInstance().addPartialTarget(custPriceCBBind);
            }

        }
    }

    public void setCustPriceLinkBind(RichLink custPriceLinkBind) {
        this.custPriceLinkBind = custPriceLinkBind;
    }

    public RichLink getCustPriceLinkBind() {
        return custPriceLinkBind;
    }

    public void setPickBarDetCBBind(RichSelectBooleanCheckbox pickBarDetCBBind) {
        this.pickBarDetCBBind = pickBarDetCBBind;
    }

    public RichSelectBooleanCheckbox getPickBarDetCBBind() {
        return pickBarDetCBBind;
    }

    public void setOppNoLOVBind(RichInputListOfValues oppNoLOVBind) {
        this.oppNoLOVBind = oppNoLOVBind;
    }

    public RichInputListOfValues getOppNoLOVBind() {
        return oppNoLOVBind;
    }

    public void setQuotNoLOVBind(RichInputListOfValues quotNoLOVBind) {
        this.quotNoLOVBind = quotNoLOVBind;
    }

    public RichInputListOfValues getQuotNoLOVBind() {
        return quotNoLOVBind;
    }

    public void setOrderLOVBind(RichInputListOfValues orderLOVBind) {
        this.orderLOVBind = orderLOVBind;
    }

    public RichInputListOfValues getOrderLOVBind() {
        return orderLOVBind;
    }

    public void setPickNoLOVBind(RichInputListOfValues pickNoLOVBind) {
        this.pickNoLOVBind = pickNoLOVBind;
    }

    public RichInputListOfValues getPickNoLOVBind() {
        return pickNoLOVBind;
    }

    public void setPackNoLOVBind(RichInputListOfValues packNoLOVBind) {
        this.packNoLOVBind = packNoLOVBind;
    }

    public RichInputListOfValues getPackNoLOVBind() {
        return packNoLOVBind;
    }

    public void setShipNoLOVBind(RichInputListOfValues shipNoLOVBind) {
        this.shipNoLOVBind = shipNoLOVBind;
    }

    public RichInputListOfValues getShipNoLOVBind() {
        return shipNoLOVBind;
    }

    public void setInvoiceNoLOVBind(RichInputListOfValues invoiceNoLOVBind) {
        this.invoiceNoLOVBind = invoiceNoLOVBind;
    }

    public RichInputListOfValues getInvoiceNoLOVBind() {
        return invoiceNoLOVBind;
    }

    public void setRmaNoLOVBinding(RichInputListOfValues rmaNoLOVBinding) {
        this.rmaNoLOVBinding = rmaNoLOVBinding;
    }

    public RichInputListOfValues getRmaNoLOVBinding() {
        return rmaNoLOVBinding;
    }

    public void setRmaNoLOVBind(RichInputListOfValues rmaNoLOVBind) {
        this.rmaNoLOVBind = rmaNoLOVBind;
    }

    public RichInputListOfValues getRmaNoLOVBind() {
        return rmaNoLOVBind;
    }

    public void setInvRegSummCBBind(RichLink invRegSummCBBind) {
        this.invRegSummCBBind = invRegSummCBBind;
    }

    public RichLink getInvRegSummCBBind() {
        return invRegSummCBBind;
    }

    public void setInvRegSummCB(RichSelectBooleanCheckbox invRegSummCB) {
        this.invRegSummCB = invRegSummCB;
    }

    public RichSelectBooleanCheckbox getInvRegSummCB() {
        return invRegSummCB;
    }

    public void invRegSummCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (invoiceNoLOVBind.getValue() != null && invoiceNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + invoiceNoLOVBind.getValue());
                    otherRegSumm = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    invRegSummCB.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(invRegSummCB);
                    otherRegSumm = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            invRegSummCB.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(invRegSummCB);
                            otherRegSumm = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            otherRegSumm = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        otherRegSumm = false; // To Disable Go Link
                    }
                }
            }

        }
    }

    public void setPickDetLinkBind(RichLink pickDetLinkBind) {
        this.pickDetLinkBind = pickDetLinkBind;
    }

    public RichLink getPickDetLinkBind() {
        return pickDetLinkBind;
    }

    public void pickDetBarCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (pickNoLOVBind.getValue() != null && pickNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + pickNoLOVBind.getValue());
                    pickBar = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    pickBarDetCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(pickBarDetCBBind);
                    pickBar = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //   System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            pickBarDetCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(pickBarDetCBBind);
                            pickBar = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            pickBar = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        pickBar = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void setSuppRegInvCBBind(RichSelectBooleanCheckbox suppRegInvCBBind) {
        this.suppRegInvCBBind = suppRegInvCBBind;
    }

    public RichSelectBooleanCheckbox getSuppRegInvCBBind() {
        return suppRegInvCBBind;
    }

    public void suppRegInvCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (invoiceNoLOVBind.getValue() != null && invoiceNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In invoice bind value-->" + invoiceNoLOVBind.getValue());
                    suppReg = true; // To enable GoLInk

                } else {
                    System.out.println("in else of invoice");
                    selectedTabLink = null;
                    suppRegInvCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(suppRegInvCBBind);
                    suppReg = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            suppRegInvCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(suppRegInvCBBind);
                            suppReg = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            suppReg = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        suppReg = false; // To Disable Go Link
                    }
                }
            }

        }
    }


    public void setIntmRegLinkBind(RichLink intmRegLinkBind) {
        this.intmRegLinkBind = intmRegLinkBind;
    }

    public RichLink getIntmRegLinkBind() {
        return intmRegLinkBind;
    }

    public void setIntmRegisterCBBind(RichSelectBooleanCheckbox intmRegisterCBBind) {
        this.intmRegisterCBBind = intmRegisterCBBind;
    }

    public RichSelectBooleanCheckbox getIntmRegisterCBBind() {
        return intmRegisterCBBind;
    }

    public void intmRegisterCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (shipNoLOVBind.getValue() != null && shipNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + shipNoLOVBind.getValue());
                    intmReg = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    intmRegisterCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(intmRegisterCBBind);
                    intmReg = false; // To Disable GoLink
                }

            }

            else {
                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            intmRegisterCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(intmRegisterCBBind);
                            intmReg = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            intmReg = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        intmReg = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void setSuppRegLinkBind(RichLink suppRegLinkBind) {
        this.suppRegLinkBind = suppRegLinkBind;
    }

    public RichLink getSuppRegLinkBind() {
        return suppRegLinkBind;
    }

    public void setSuppRegLink(Boolean suppRegLink) {
        this.suppRegLink = suppRegLink;
    }

    public Boolean getSuppRegLink() {
        return suppRegLink;
    }

    public void setIntmRegLink(Boolean intmRegLink) {
        this.intmRegLink = intmRegLink;
    }

    public Boolean getIntmRegLink() {
        return intmRegLink;
    }

    public void setSuppReg(Boolean suppReg) {
        this.suppReg = suppReg;
    }

    public Boolean getSuppReg() {
        return suppReg;
    }

    public void setIntmReg(boolean intmReg) {
        this.intmReg = intmReg;
    }

    public boolean isIntmReg() {
        return intmReg;
    }

    public void setIntmNoLOVBind(RichInputComboboxListOfValues intmNoLOVBind) {
        this.intmNoLOVBind = intmNoLOVBind;
    }

    public RichInputComboboxListOfValues getIntmNoLOVBind() {
        return intmNoLOVBind;
    }

    public void intmNoLOVVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setCustWiseGrpCBBind(RichSelectBooleanCheckbox custWiseGrpCBBind) {
        this.custWiseGrpCBBind = custWiseGrpCBBind;
    }

    public RichSelectBooleanCheckbox getCustWiseGrpCBBind() {
        return custWiseGrpCBBind;
    }

    public void custWiseGrpCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (invoiceNoLOVBind.getValue() != null && invoiceNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In invoice bind value-->" + invoiceNoLOVBind.getValue());
                    CustGrp = true; // To enable GoLInk

                } else {
                    System.out.println("in else of invoice");
                    selectedTabLink = null;
                    custWiseGrpCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(custWiseGrpCBBind);
                    CustGrp = false; // To Disable GoLink
                }

            }

            else {


                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            custWiseGrpCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(custWiseGrpCBBind);
                            CustGrp = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            CustGrp = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        CustGrp = false; // To Disable Go Link
                    }
                }
            }

        }
    }

    public void setCustGrpLinkBind(RichLink custGrpLinkBind) {
        this.custGrpLinkBind = custGrpLinkBind;
    }

    public RichLink getCustGrpLinkBind() {
        return custGrpLinkBind;
    }

    public void setToInvTransBind(RichInputListOfValues toInvTransBind) {
        this.toInvTransBind = toInvTransBind;
    }

    public RichInputListOfValues getToInvTransBind() {
        return toInvTransBind;
    }

    public void setFromInvLOVBind(RichInputListOfValues fromInvLOVBind) {
        this.fromInvLOVBind = fromInvLOVBind;
    }

    public RichInputListOfValues getFromInvLOVBind() {
        return fromInvLOVBind;
    }

    public void FromInvLOVVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            startDate.setValue(null);
            endDate.setValue(null);
            toInvTransBind.setValue(vce.getNewValue());
        }
    }

    public void setOrdAmtCBBind(RichSelectBooleanCheckbox ordAmtCBBind) {
        this.ordAmtCBBind = ordAmtCBBind;
    }

    public RichSelectBooleanCheckbox getOrdAmtCBBind() {
        return ordAmtCBBind;
    }

    public void OrdAmdCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (orderLOVBind.getValue() != null && orderLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In DCDocId bind value-->" + orderLOVBind.getValue());
                    OrderAmd = true; // To enable GoLInk

                } else {
                    System.out.println("in else of DCDOcID");
                    selectedTabLink = null;
                    ordAmtCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(ordAmtCBBind);
                    OrderAmd = false; // To Disable GoLink
                }

            } else {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                    if (checkBoxValidation()) {
                        ordAmtCBBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(ordAmtCBBind);
                        OrderAmd = false; // To Disable GoLink

                        showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                    } else {
                        OrderAmd = true; // To enable GoLInk

                    }
                } else {
                    selectedTabLink = null;
                    OrderAmd = false; // To Disable Go Link
                }
            }
        }
    }

    public void setOrdAmdLinkBind(RichLink ordAmdLinkBind) {
        this.ordAmdLinkBind = ordAmdLinkBind;
    }

    public RichLink getOrdAmdLinkBind() {
        return ordAmdLinkBind;
    }

    public void setInvVehicleSummCBBind(RichSelectBooleanCheckbox invVehicleSummCBBind) {
        this.invVehicleSummCBBind = invVehicleSummCBBind;
    }

    public RichSelectBooleanCheckbox getInvVehicleSummCBBind() {
        return invVehicleSummCBBind;
    }

    public void invVehSummVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (invoiceNoLOVBind.getValue() != null && invoiceNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In invoice bind value-->" + invoiceNoLOVBind.getValue());
                    InvVeh = true; // To enable GoLInk

                } else {
                    System.out.println("in else of invoice");
                    selectedTabLink = null;
                    invVehicleSummCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(invVehicleSummCBBind);
                    InvVeh = false; // To Disable GoLink
                }

            }

            else {
                if (vce.getNewValue() != null) {
                    //System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            invVehicleSummCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(invVehicleSummCBBind);
                            InvVeh = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            InvVeh = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        InvVeh = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void setInvvehLinkBind(RichLink invvehLinkBind) {
        this.invvehLinkBind = invvehLinkBind;
    }

    public RichLink getInvvehLinkBind() {
        return invvehLinkBind;
    }

    public void setInstallationCBBind(RichSelectBooleanCheckbox installationCBBind) {
        this.installationCBBind = installationCBBind;
    }

    public RichSelectBooleanCheckbox getInstallationCBBind() {
        return installationCBBind;
    }

    public void installationCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (shipNoLOVBind.getValue() != null && shipNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + shipNoLOVBind.getValue());
                    Install = true; // To enable GoLInk

                } else {
                    System.out.println("in else of order");
                    selectedTabLink = null;
                    installationCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(installationCBBind);
                    Install = false; // To Disable GoLink
                }

            }

            else {
                if (vce.getNewValue() != null) {
                    //  System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            installationCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(installationCBBind);
                            Install = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            Install = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        Install = false; // To Disable Go Link
                    }
                }
            }
        }
    }

    public void setInstallationLinkBind(RichLink installationLinkBind) {
        this.installationLinkBind = installationLinkBind;
    }

    public RichLink getInstallationLinkBind() {
        return installationLinkBind;
    }

    public void setTaxLinkBind(RichLink taxLinkBind) {
        this.taxLinkBind = taxLinkBind;
    }

    public RichLink getTaxLinkBind() {
        return taxLinkBind;
    }

    public void setTaxCBBind(RichSelectBooleanCheckbox taxCBBind) {
        this.taxCBBind = taxCBBind;
    }

    public RichSelectBooleanCheckbox getTaxCBBind() {
        return taxCBBind;
    }

    public void taxCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());

            if (invoiceNoLOVBind.getValue() != null && invoiceNoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In invoice bind value-->" + invoiceNoLOVBind.getValue());
                    Tax = true; // To enable GoLInk

                } else {
                    System.out.println("in else of invoice");
                    selectedTabLink = null;
                    taxCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(taxCBBind);
                    Tax = false; // To Disable GoLink
                }

            }

            else {
                if (vce.getNewValue() != null) {
                    //System.out.println("Vce value is " + vce.getNewValue().toString());
                    if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            taxCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(taxCBBind);
                            Tax = false; // To Disable GoLink

                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            Tax = true; // To enable GoLInk

                        }
                    } else {
                        selectedTabLink = null;
                        Tax = false; // To Disable Go Link
                    }
                }
            }
        }
    }
}
