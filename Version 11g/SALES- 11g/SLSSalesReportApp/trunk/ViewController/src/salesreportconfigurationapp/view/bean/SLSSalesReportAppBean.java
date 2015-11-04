package salesreportconfigurationapp.view.bean;

import java.sql.SQLException;

import java.util.Date;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.jbo.domain.Timestamp;
import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichGoLink;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
 

import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.event.DisclosureEvent;

import salesreportconfigurationapp.model.services.SaleReportAMImpl;


public class SLSSalesReportAppBean {
    /**
     * Following variables are use for enabling or disabling the links present on page
     */
    private Boolean custwiseDetailLink = false;
    private Boolean custwiseSummaryLink = false;
    private Boolean salesmanDetailLink = false;
    private Boolean salesmanSummaryLink = false;
    private Boolean productgroupwiseDetailLink = false;
    private Boolean productgroupSummaryLink = false;
    private Boolean itmDetailLink = false;
    private Boolean itmSummaryLink = false;
    private Boolean otherRegisterLink = false;
    private Boolean statusLink = false;

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
    private Boolean salesmanDetail = false;
    private Boolean salesmanSummary = false;
    private Boolean productgroupwiseDetail = false;
    private Boolean productgroupSummary = false;
    private Boolean itmDetail = false;
    private Boolean itmSummary = false;
    private Boolean otherRegister = false;
    private Boolean status = false;
    private Timestamp fromdate;
    private Timestamp todate;
    private String startStringDate;
    private String endStringDate;
    private Boolean custwiseDetailAnalysis = false;
    private Boolean itmwiseSummaryAnalysis = false;
    private Boolean slsmanDetailAnalysis = false;
    private Boolean grpWiseAnalysis = false;
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
    private RichGoLink customerwiseanalysisLink;
    private RichGoLink groupwiseAnalysisCB;
    private RichGoLink salesmanAnaysisLink;
    private RichGoLink itemWiseAnalysisLink;
    private RichSelectBooleanCheckbox custwiseSummaryCBBind;
    private RichSelectBooleanCheckbox itemWiseSummaryAnalysisCBBind;
    private RichSelectBooleanCheckbox grpWiseAnalysisCBBind;
    private RichSelectBooleanCheckbox slSManAnalysisCBBind;
    private RichSelectOneChoice ordernoLOVBind;
    private RichSelectOneChoice oppStatusBind;
    private RichSelectOneChoice oppNoBind;
    private RichSelectOneChoice quotBind;
    private RichSelectOneChoice statusQuotBind;
    private RichSelectOneChoice orderStatusBind;
    private RichSelectOneChoice pickNoBind;
    private RichSelectOneChoice shipNoBind;
    private RichSelectOneChoice invoiveNoBind;
    private RichSelectOneChoice rmaNoBind;
    private RichShowDetailItem opportunityTabBind;


    public SLSSalesReportAppBean() {
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
        if (startDate.getValue() == null || startDate.getValue().toString().length() <= 0) {
            dateClientId = startDate.getClientId();
            return true;
        } else if (endDate.getValue() == null || endDate.getValue().toString().length() <= 0) {
            dateClientId = endDate.getClientId();
            return true;
        }

        else {
            return false;
        }

    }

    /**
     *Generate Button Action
     * @param actionEvent
     */

    public void GenerateButtonAL(ActionEvent actionEvent) {

        if (startDate.getValue() != null) {
            System.out.println("start date if");
            fromdate =
                    (Timestamp)startDate.getValue(); // Converting Timestamp to Date Format and passing as a parameter to Reports through El
        } else {
            System.out.println("Tab is in getTabLovFlag" + tab);
            OperationBinding ob = getBindings().getOperationBinding("getTabLovFlag");
            ob.getParamsMap().put("tabValue", tab);
            ob.execute();

            String res = ob.getResult().toString();
            if (res.equalsIgnoreCase("Y")) {
                System.out.println("Tab is in  getDocDateValue" + tab);
                OperationBinding obj = getBindings().getOperationBinding("getDocDateValue");
                obj.getParamsMap().put("tabValue", tab);
                obj.execute();
                 fromdate  = (Timestamp) obj.getResult();
                 
            } else {
                fromdate = null;
            }
        }
        if (endDate.getValue() != null) {
            System.out.println("end date if");
            todate =
                    (Timestamp)endDate.getValue(); // Converting Timestamp to Date Format and passing as a parameter to Reports through El

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
                todate= (Timestamp)obj.getResult();
                 
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
        } catch (SQLException e) {
        }


        if (custwiseDetail) {
            selectedTabLink = tab;
            setCustwiseDetailLink(true);
        } else {
            selectedTabLink = tab;
            setCustwiseDetailLink(false);
        }


        if (custwiseSummary) {
            selectedTabLink = tab;
            setCustwiseSummaryLink(true);
        } else {
            selectedTabLink = tab;
            setCustwiseSummaryLink(false);
        }


        if (salesmanDetail) {
            selectedTabLink = tab;
            setSalesmanDetailLink(true);
        } else {
            selectedTabLink = tab;
            setSalesmanDetailLink(false);
        }


        if (salesmanSummary) {
            selectedTabLink = tab;
            setSalesmanSummaryLink(true);
        } else {
            selectedTabLink = tab;
            setSalesmanSummaryLink(false);
        }


        if (productgroupwiseDetail) {
            selectedTabLink = tab;
            setProductgroupwiseDetailLink(true);
        } else {
            selectedTabLink = tab;
            setProductgroupwiseDetailLink(false);
        }

        if (productgroupSummary) {
            selectedTabLink = tab;
            setProductgroupSummaryLink(true);
        } else {
            selectedTabLink = tab;
            setProductgroupSummaryLink(false);
        }


        if (itmDetail) {
            selectedTabLink = tab;
            setItmDetailLink(true);
        } else {
            selectedTabLink = tab;
            setItmDetailLink(false);
        }


        if (itmSummary) {
            selectedTabLink = tab;
            setItmSummaryLink(true);
        } else {
            selectedTabLink = tab;
            setItmSummaryLink(false);
        }


        if (otherRegister) {
            selectedTabLink = tab;
            setOtherRegisterLink(true);
        } else {
            selectedTabLink = tab;
            setOtherRegisterLink(false);
        }

        if (status) {
            selectedTabLink = tab;
            setStatusLink(true);
        } else {
            selectedTabLink = tab;
            setStatusLink(false);
        }
        if (custwiseDetailAnalysis) {
            selectedTabLink = tab;
            customerwiseanalysisLink.setVisible(true);
        } else {
            selectedTabLink = tab;
            customerwiseanalysisLink.setVisible(false);
        }
        if (itmwiseSummaryAnalysis) {
            selectedTabLink = tab;
            itemWiseAnalysisLink.setVisible(true);
        } else {
            selectedTabLink = tab;
            itemWiseAnalysisLink.setVisible(false);
        }
        if (slsmanDetailAnalysis) {
            selectedTabLink = tab;
            salesmanAnaysisLink.setVisible(true);
        } else {
            selectedTabLink = tab;
            salesmanAnaysisLink.setVisible(false);
        }
        if (grpWiseAnalysis) {
            selectedTabLink = tab;
            groupwiseAnalysisCB.setVisible(true);
        } else {
            selectedTabLink = tab;
            groupwiseAnalysisCB.setVisible(false);
        }
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

    /**
     * All Tabs Disclosure Event was present
     *
     */

    public void OpportunityTabDL(DisclosureEvent dE) {
        if (dE.isExpanded()) {

            tab = new StringBuffer("Opp");
            SaleReportAMImpl am = (SaleReportAMImpl)resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("OpportunityNo", null);
            oppNoBind.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppNoBind);
        } else {
            selectedTabLink = null;
            SaleReportAMImpl am = (SaleReportAMImpl)resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("OpportunityNo", null);
            System.out.println("oppbind value-->" + oppNoBind.getValue());
            custDetailCBOppPgBind.setValue(false);
            custSummCBOppPgBind.setValue(false);
            salesManDetailCBOppPgBind.setValue(false);
            salesManSummCBOppPgBind.setValue(false);
            productGrpDetailCBOppPgBind.setValue(false);
            productGrpSummCBOppPgBind.setValue(false);
            itemDetailCBOppPgBind.setValue(false);
            itemSummCBOppPgBind.setValue(false);
            opportunityReportCBPgBind.setValue(false);

            AdfFacesContext.getCurrentInstance().addPartialTarget(oppNoBind);
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
        if (dE.isExpanded()) {


            tab = new StringBuffer("Quo");
            SaleReportAMImpl am = (SaleReportAMImpl)resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("QuotationNo", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotBind);
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
            SaleReportAMImpl am = (SaleReportAMImpl)resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("QuotationNo", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotBind);
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
        if (dE.isExpanded()) {

            tab = new StringBuffer("SO");
            SaleReportAMImpl am = (SaleReportAMImpl)resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("OrderNoTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(ordernoLOVBind);
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
            SaleReportAMImpl am = (SaleReportAMImpl)resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("OrderNoTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(ordernoLOVBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailWiseCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummaryCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManDetailCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManSummaryCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGroupDetailCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGroupSummaryCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummaryCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesOrderRegisterCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(ordernoLOVBind);
        }

    }

    public void PickListTabDL(DisclosureEvent dE) {
        if (dE.isExpanded()) {
            tab = new StringBuffer("PL");

            SaleReportAMImpl am = (SaleReportAMImpl)resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("PickIdTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickNoBind);
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
            SaleReportAMImpl am = (SaleReportAMImpl)resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("PickIdTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickNoBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBPLPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBPLPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpCBPLPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productSummCBPLPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBPLPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBPLPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickListRegisterCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pickListStatusCBPgBind);
        }

    }

    public void ShippmentTabDL(DisclosureEvent dE) {
        if (dE.isExpanded()) {
            tab = new StringBuffer("Ship");

            SaleReportAMImpl am = (SaleReportAMImpl)resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("ShipNoTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipNoBind);

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

            SaleReportAMImpl am = (SaleReportAMImpl)resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("ShipNoTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipNoBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBShipPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBShipPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpDetailCBShipPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBShipPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBShipPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBShipPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipmentRegisterCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(shipmentStatusCBPgBind);
        }

    }

    public void InvoiceTabDL(DisclosureEvent dE) {
        if (dE.isExpanded()) {
            tab = new StringBuffer("Inv");
            SaleReportAMImpl am = (SaleReportAMImpl)resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("InvoiceIdTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invoiveNoBind);

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
            SaleReportAMImpl am = (SaleReportAMImpl)resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("InvoiceIdTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invoiveNoBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custDetailCBInvPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(custSummCBInvPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManDetailCBInvPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(salesManSummCBInvPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpDetailCBInvPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(productGrpSummCBInvPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemDetailCBInvPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemSummCBInvPgBind);
        }
    }

    public void RmaTabDL(DisclosureEvent dE) {
        if (dE.isExpanded()) {
            tab = new StringBuffer("Rma");

            SaleReportAMImpl am = (SaleReportAMImpl)resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("RmaIdTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmaNoBind);
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
            SaleReportAMImpl am = (SaleReportAMImpl)resolvElDC("SaleReportAMDataControl"); // get AM
            ViewObject vo = am.getTempVariables();
            vo.getCurrentRow().setAttribute("RmaIdTrans", null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rmaNoBind);
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
            System.out.println("order Before if value-->" + ordernoLOVBind.getValue().toString().length());
            if (ordernoLOVBind.getValue() != null && ordernoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + ordernoLOVBind.getValue());
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
            System.out.println("order Before if value-->" + ordernoLOVBind.getValue().toString().length());
            if (ordernoLOVBind.getValue() != null && ordernoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + ordernoLOVBind.getValue());
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
            System.out.println("order Before if value-->" + ordernoLOVBind.getValue().toString().length());
            if (ordernoLOVBind.getValue() != null && ordernoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + ordernoLOVBind.getValue());
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
            System.out.println("order Before if value-->" + ordernoLOVBind.getValue().toString().length());
            if (ordernoLOVBind.getValue() != null && ordernoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + ordernoLOVBind.getValue());
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
            System.out.println("order Before if value-->" + ordernoLOVBind.getValue().toString().length());
            if (ordernoLOVBind.getValue() != null && ordernoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + ordernoLOVBind.getValue());
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
            System.out.println("order Before if value-->" + ordernoLOVBind.getValue().toString().length());
            if (ordernoLOVBind.getValue() != null && ordernoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + ordernoLOVBind.getValue());
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
            System.out.println("order Before if value-->" + ordernoLOVBind.getValue().toString().length());
            if (ordernoLOVBind.getValue() != null && ordernoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + ordernoLOVBind.getValue());
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
            System.out.println("order Before if value-->" + ordernoLOVBind.getValue().toString().length());
            if (ordernoLOVBind.getValue() != null && ordernoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + ordernoLOVBind.getValue());
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
            System.out.println("oppBind Before if value-->" + oppNoBind.getValue().toString().length());
            if (oppNoBind.getValue() != null && oppNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In oppno bind value-->" + oppNoBind.getValue());
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
            System.out.println("oppBind Before if value-->" + oppNoBind.getValue().toString().length());
            if (oppNoBind.getValue() != null && oppNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In oppno bind value-->" + oppNoBind.getValue());
                    custwiseSummary = true; // To enable GoLInk

                } else {
                    System.out.println("in else of oppno");
                    selectedTabLink = null;
                    custSummCBOppPgBind.setValue(false);
                    custwiseSummary = false; // To Disable Go Link
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

                    }

                } else {
                    selectedTabLink = null;
                    custwiseSummary = false; // To Disable Go Link
                }
            }
        }
    }

    public void ProductGrpDetailOppCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());
            System.out.println("oppBind Before if value-->" + oppNoBind.getValue().toString().length());
            if (oppNoBind.getValue() != null && oppNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In oppno bind value-->" + oppNoBind.getValue());
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
            System.out.println("oppBind Before if value-->" + oppNoBind.getValue().toString().length());
            if (oppNoBind.getValue() != null && oppNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In oppno bind value-->" + oppNoBind.getValue());
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
            System.out.println("oppBind Before if value-->" + oppNoBind.getValue().toString().length());
            if (oppNoBind.getValue() != null && oppNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In oppno bind value-->" + oppNoBind.getValue());
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
            System.out.println("oppBind Before if value-->" + oppNoBind.getValue().toString().length());
            if (oppNoBind.getValue() != null && oppNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In oppno bind value-->" + oppNoBind.getValue());
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
            System.out.println("oppBind Before if value-->" + oppNoBind.getValue().toString().length());
            if (oppNoBind.getValue() != null && oppNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In oppno bind value-->" + oppNoBind.getValue());
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
            System.out.println("oppBind Before if value-->" + oppNoBind.getValue().toString().length());
            if (oppNoBind.getValue() != null && oppNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In oppno bind value-->" + oppNoBind.getValue());
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
            System.out.println("quotBind Before if value-->" + quotBind.getValue().toString().length());
            if (quotBind.getValue() != null && quotBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In quotBind bind value-->" + quotBind.getValue());
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
            System.out.println("quotBind Before if value-->" + quotBind.getValue().toString().length());
            if (quotBind.getValue() != null && quotBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In quotBind bind value-->" + quotBind.getValue());
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
            System.out.println("quotBind Before if value-->" + quotBind.getValue().toString().length());
            if (quotBind.getValue() != null && quotBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In quotBind bind value-->" + quotBind.getValue());
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
            System.out.println("quotBind Before if value-->" + quotBind.getValue().toString().length());
            if (quotBind.getValue() != null && quotBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In quotBind bind value-->" + quotBind.getValue());
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
            System.out.println("quotBind Before if value-->" + quotBind.getValue().toString().length());
            if (quotBind.getValue() != null && quotBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In quotBind bind value-->" + quotBind.getValue());
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
            System.out.println("quotBind Before if value-->" + quotBind.getValue().toString().length());
            if (quotBind.getValue() != null && quotBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In quotBind bind value-->" + quotBind.getValue());
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
            System.out.println("quotBind Before if value-->" + quotBind.getValue().toString().length());
            if (quotBind.getValue() != null && quotBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In quotBind bind value-->" + quotBind.getValue());
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
            System.out.println("quotBind Before if value-->" + quotBind.getValue().toString().length());
            if (quotBind.getValue() != null && quotBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In quotBind bind value-->" + quotBind.getValue());
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
            System.out.println("pick Before if value-->" + pickNoBind.getValue().toString().length());
            if (pickNoBind.getValue() != null && pickNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + pickNoBind.getValue());
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
            System.out.println("pick Before if value-->" + pickNoBind.getValue().toString().length());
            if (pickNoBind.getValue() != null && pickNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + pickNoBind.getValue());
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
            System.out.println("pick Before if value-->" + pickNoBind.getValue().toString().length());
            if (pickNoBind.getValue() != null && pickNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + pickNoBind.getValue());
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
            System.out.println("pick Before if value-->" + pickNoBind.getValue().toString().length());
            if (pickNoBind.getValue() != null && pickNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + pickNoBind.getValue());
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
            System.out.println("pick Before if value-->" + pickNoBind.getValue().toString().length());
            if (pickNoBind.getValue() != null && pickNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + pickNoBind.getValue());
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
            System.out.println("pick Before if value-->" + pickNoBind.getValue().toString().length());
            if (pickNoBind.getValue() != null && pickNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + pickNoBind.getValue());
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
            System.out.println("ship Before if value-->" + shipNoBind.getValue().toString().length());
            if (shipNoBind.getValue() != null && shipNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + shipNoBind.getValue());
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
            System.out.println("ship Before if value-->" + shipNoBind.getValue().toString().length());
            if (shipNoBind.getValue() != null && shipNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + shipNoBind.getValue());
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
            System.out.println("ship Before if value-->" + shipNoBind.getValue().toString().length());
            if (shipNoBind.getValue() != null && shipNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + shipNoBind.getValue());
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
            System.out.println("ship Before if value-->" + shipNoBind.getValue().toString().length());
            if (shipNoBind.getValue() != null && shipNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + shipNoBind.getValue());
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
            System.out.println("ship Before if value-->" + shipNoBind.getValue().toString().length());
            if (shipNoBind.getValue() != null && shipNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + shipNoBind.getValue());
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
            System.out.println("ship Before if value-->" + shipNoBind.getValue().toString().length());
            if (shipNoBind.getValue() != null && shipNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + shipNoBind.getValue());
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
            System.out.println("invoice Before if value-->" + invoiveNoBind.getValue().toString().length());
            if (invoiveNoBind.getValue() != null && invoiveNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In invoice bind value-->" + invoiveNoBind.getValue());
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
            System.out.println("ship Before if value-->" + invoiveNoBind.getValue().toString().length());
            if (invoiveNoBind.getValue() != null && invoiveNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In invoice bind value-->" + invoiveNoBind.getValue());
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
            System.out.println("RMA Before if value-->" + invoiveNoBind.getValue().toString().length());
            if (invoiveNoBind.getValue() != null && invoiveNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In rma bind value-->" + invoiveNoBind.getValue());
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
            System.out.println("invoice Before if value-->" + invoiveNoBind.getValue().toString().length());
            if (invoiveNoBind.getValue() != null && invoiveNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In invoice bind value-->" + invoiveNoBind.getValue());
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
            System.out.println("invoice Before if value-->" + invoiveNoBind.getValue().toString().length());
            if (invoiveNoBind.getValue() != null && invoiveNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In invoice bind value-->" + invoiveNoBind.getValue());
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
            System.out.println("invoice Before if value-->" + invoiveNoBind.getValue().toString().length());
            if (invoiveNoBind.getValue() != null && invoiveNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In invoice bind value-->" + invoiveNoBind.getValue());
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
            System.out.println("invoice Before if value-->" + invoiveNoBind.getValue().toString().length());
            if (invoiveNoBind.getValue() != null && invoiveNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In invoice bind value-->" + invoiveNoBind.getValue());
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
            System.out.println("invoice Before if value-->" + invoiveNoBind.getValue().toString().length());
            if (invoiveNoBind.getValue() != null && invoiveNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In invoice bind value-->" + invoiveNoBind.getValue());
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
            System.out.println("rma Before if value-->" + rmaNoBind.getValue().toString().length());
            if (rmaNoBind.getValue() != null && rmaNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In rmaNoBind bind value-->" + rmaNoBind.getValue());
                    custwiseDetail = true; // To enable GoLInk

                } else {
                    System.out.println("in else of rmaNoBind");
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
            System.out.println("RMA Before if value-->" + rmaNoBind.getValue().toString().length());
            if (rmaNoBind.getValue() != null && rmaNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In rma bind value-->" + rmaNoBind.getValue());
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
            System.out.println("RMA Before if value-->" + rmaNoBind.getValue().toString().length());
            if (rmaNoBind.getValue() != null && rmaNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In invoice bind value-->" + rmaNoBind.getValue());
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
            System.out.println("RMA Before if value-->" + rmaNoBind.getValue().toString().length());
            if (rmaNoBind.getValue() != null && rmaNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In rma bind value-->" + rmaNoBind.getValue());
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
            System.out.println("RMA Before if value-->" + rmaNoBind.getValue().toString().length());
            if (rmaNoBind.getValue() != null && rmaNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In rma bind value-->" + rmaNoBind.getValue());
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
            System.out.println("RMA Before if value-->" + rmaNoBind.getValue().toString().length());
            if (rmaNoBind.getValue() != null && rmaNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In rma bind value-->" + rmaNoBind.getValue());
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
            System.out.println("quotBind Before if value-->" + quotBind.getValue().toString().length());
            if (quotBind.getValue() != null && quotBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In quotBind bind value-->" + quotBind.getValue());
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
            System.out.println("order Before if value-->" + ordernoLOVBind.getValue().toString().length());
            if (ordernoLOVBind.getValue() != null && ordernoLOVBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In order bind value-->" + ordernoLOVBind.getValue());
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
            System.out.println("oppBind Before if value-->" + oppNoBind.getValue().toString().length());
            if (oppNoBind.getValue() != null && oppNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In oppno bind value-->" + oppNoBind.getValue());
                    otherRegister = true; // To enable GoLInk

                } else {
                    System.out.println("in else of oppno");
                    selectedTabLink = null;
                    opportunityReportCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(opportunityReportCBPgBind);
                    otherRegister = false; // To Disable GoLink
                }

            } else {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                    if (checkBoxValidation()) {
                        opportunityReportCBPgBind.setValue(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(opportunityReportCBPgBind);
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

    public void PickListRegisterVCL(ValueChangeEvent vce) {

        if (vce.getNewValue() != null) {
            //System.out.println("Vce value is " + vce.getNewValue().toString());
            System.out.println("pick Before if value-->" + pickNoBind.getValue().toString().length());
            if (pickNoBind.getValue() != null && pickNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + pickNoBind.getValue());
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
            System.out.println("pick Before if value-->" + pickNoBind.getValue().toString().length());
            if (pickNoBind.getValue() != null && pickNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + pickNoBind.getValue());
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
            System.out.println("RMA Before if value-->" + rmaNoBind.getValue().toString().length());
            if (rmaNoBind.getValue() != null && rmaNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In rma bind value-->" + rmaNoBind.getValue());
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
            System.out.println("RMA Before if value-->" + rmaNoBind.getValue().toString().length());
            if (rmaNoBind.getValue() != null && rmaNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In rma bind value-->" + rmaNoBind.getValue());
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
            System.out.println("ship Before if value-->" + shipNoBind.getValue().toString().length());
            if (shipNoBind.getValue() != null && shipNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + shipNoBind.getValue());
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
            System.out.println("ship Before if value-->" + shipNoBind.getValue().toString().length());
            if (shipNoBind.getValue() != null && shipNoBind.getValue().toString().length() > 0) {
                if (vce.getNewValue().toString().equalsIgnoreCase("true")) {

                    System.out.println("In pick bind value-->" + shipNoBind.getValue());
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

    public void setCustomerwiseanalysisLink(RichGoLink customerwiseanalysisLink) {
        this.customerwiseanalysisLink = customerwiseanalysisLink;
    }

    public RichGoLink getCustomerwiseanalysisLink() {
        return customerwiseanalysisLink;
    }

    public void setGroupwiseAnalysisCB(RichGoLink groupwiseAnalysisCB) {
        this.groupwiseAnalysisCB = groupwiseAnalysisCB;
    }

    public RichGoLink getGroupwiseAnalysisCB() {
        return groupwiseAnalysisCB;
    }

    public void setSalesmanAnaysisLink(RichGoLink salesmanAnaysisLink) {
        this.salesmanAnaysisLink = salesmanAnaysisLink;
    }

    public RichGoLink getSalesmanAnaysisLink() {
        return salesmanAnaysisLink;
    }

    public void setItemWiseAnalysisLink(RichGoLink itemWiseAnalysisLink) {
        this.itemWiseAnalysisLink = itemWiseAnalysisLink;
    }

    public RichGoLink getItemWiseAnalysisLink() {
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

    public void setOrdernoLOVBind(RichSelectOneChoice ordernoLOVBind) {
        this.ordernoLOVBind = ordernoLOVBind;
    }

    public RichSelectOneChoice getOrdernoLOVBind() {
        return ordernoLOVBind;
    }

    public void setOppStatusBind(RichSelectOneChoice oppStatusBind) {
        this.oppStatusBind = oppStatusBind;
    }

    public RichSelectOneChoice getOppStatusBind() {
        return oppStatusBind;
    }

    public void setOppNoBind(RichSelectOneChoice oppNoBind) {
        this.oppNoBind = oppNoBind;
    }

    public RichSelectOneChoice getOppNoBind() {
        return oppNoBind;
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
            AdfFacesContext.getCurrentInstance().addPartialTarget(oppNoBind);
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
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotBind);
        }
    }

    public void setQuotBind(RichSelectOneChoice quotBind) {
        this.quotBind = quotBind;
    }

    public RichSelectOneChoice getQuotBind() {
        return quotBind;
    }

    public void setStatusQuotBind(RichSelectOneChoice statusQuotBind) {
        this.statusQuotBind = statusQuotBind;
    }

    public RichSelectOneChoice getStatusQuotBind() {
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
            AdfFacesContext.getCurrentInstance().addPartialTarget(ordernoLOVBind);
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

        }
    }

    public void setPickNoBind(RichSelectOneChoice pickNoBind) {
        this.pickNoBind = pickNoBind;
    }

    public RichSelectOneChoice getPickNoBind() {
        return pickNoBind;
    }

    public void setShipNoBind(RichSelectOneChoice shipNoBind) {
        this.shipNoBind = shipNoBind;
    }

    public RichSelectOneChoice getShipNoBind() {
        return shipNoBind;
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

        }
    }

    public void setInvoiveNoBind(RichSelectOneChoice invoiveNoBind) {
        this.invoiveNoBind = invoiveNoBind;
    }

    public RichSelectOneChoice getInvoiveNoBind() {
        return invoiveNoBind;
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

    public void setRmaNoBind(RichSelectOneChoice rmaNoBind) {
        this.rmaNoBind = rmaNoBind;
    }

    public RichSelectOneChoice getRmaNoBind() {
        return rmaNoBind;
    }

    public static BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void resetAL(ActionEvent actionEvent) {
        OperationBinding ob = getBindings().getOperationBinding("resetAction");
        ob.execute();
    }

    public void setOpportunityTabBind(RichShowDetailItem opportunityTabBind) {
        this.opportunityTabBind = opportunityTabBind;
    }

    public RichShowDetailItem getOpportunityTabBind() {
        return opportunityTabBind;
    }
}
