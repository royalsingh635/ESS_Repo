package mnfreportapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import oracle.jbo.domain.Timestamp;
import java.sql.Types;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import mnfreportapp.view.utils.ADFUtils;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichMessage;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.JboException;


import org.apache.myfaces.trinidad.event.DisclosureEvent;

public class MNFReportBean {
    private RichSelectBooleanCheckbox prodFcCBBinding;
    private RichLink prodFcLinkBinding;

    public MNFReportBean() {
        try{
            System.out.println("--calling---");
           // ADFUtils.findOperation("setDate").execute();  
            System.out.println("--called---");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    CallableStatement cstmt = null;
    Connection conn=null;
    // Method to call the SQl function
      private RichInputDate startDatePgBind;
    private RichInputDate endDatePgBind;
    private Boolean bomExplosion = false;
    private Boolean bomRevision = false;
    private Boolean pdoPrint = false;
    private Boolean pdoSchldPrint = false;
    private Boolean pdoStatus = false;
    private Boolean pdoSummary = false;
    private Boolean dailyProduction = false;
    private Boolean jcPrint = false;
    private Boolean jcStatus = false;
    private Boolean jcBarcode = false;
    private Boolean jcCoPrdByPrd = false;
    private Boolean jcCostingPrint = false;
    private Boolean jcRejectionSumm = false;
    private Boolean jcScrapSumm = false;
    private Boolean rcPrint = false;
    private Boolean rcBarcode = false;
    private Boolean rcCoPrdByPrd = false;
    private Boolean rcCostingPrint = false;
    private Boolean rcRejectionSumm = false;
    private Boolean rcScrapSumm = false;
    private Boolean stockAtShop = false;
    private Boolean wsReading = false;
    private Boolean mode = false;
    private boolean rcConsumption = false;
     private boolean jcConsumption = false;
     private Boolean rcIssuedQty = false;
     private Boolean jcIssueQty = false;
     private Boolean rcMRS = false;
     private Boolean jcMRS = false;
     private Boolean outSO = false;
     private Boolean SOPDO = false;
     private Boolean planVSactual = false;
     private Boolean prodRej_summ = false;
     private Boolean prodRej_det = false;
     private boolean prodRatio = false;
     private boolean prodMoveSlip = false;
     private boolean machNoPlan = false;
     private boolean machPlan = false;
     private boolean machDowntime  = false;
     private boolean wsDet = false;
     private Boolean shiftList = false;
     private Boolean rcRejectionDet = false;
     private boolean wsUtilization = false;
     private boolean wsItmUtilization = false;
     private boolean planActualRC = false;
    private boolean prodMoveRC = false;
    private boolean downRpt = false;
    private boolean QCTestCert = false;
    private boolean gpp = false;
    private boolean bomHierarchy = false;
    private boolean prodFcLink = false;

    public void setProdFcLink(boolean prodFcLink) {
        this.prodFcLink = prodFcLink;
    }

    public boolean isProdFcLink() {
        return prodFcLink;
    }
    private RichSelectBooleanCheckbox planVsActRCCBBind;
    private RichSelectBooleanCheckbox prodMoveRCCBBind;
    private RichLink planVsActRCLinkBind;
    private RichLink prodMoveRCLinkBind;
    private RichMessage msgInfoPgBind;
    private RichSelectBooleanCheckbox downRptCBPgBind;
    private RichSelectBooleanCheckbox orgNamCBPgBind;
    private RichInputListOfValues rptNmLovPgBind;
    private RichLink dwonRptLinkPgBind;
    private RichLink qcTestCertLinkBind;
    private RichInputListOfValues qcNOLovPgBind;
    private RichSelectBooleanCheckbox qcTestCertCBBind;
    private RichSelectBooleanCheckbox gpPCBPgBinding;
    private RichInputListOfValues gppLovPgBind;
    private RichLink gpPpgLinkBiind;
    private RichOutputText gppDocIdPgBind;
    private RichSelectBooleanCheckbox boMHieraCBBinding;
    private RichLink bomHierarchyLinkBinding;

    public void setPlanActualRCLink(boolean planActualRCLink) {
        this.planActualRCLink = planActualRCLink;
    }

    public boolean isPlanActualRCLink() {
        return planActualRCLink;
    }

    public void setProdMoveRCLink(boolean prodMoveRCLink) {
        this.prodMoveRCLink = prodMoveRCLink;
    }

    public boolean isProdMoveRCLink() {
        return prodMoveRCLink;
    }

    private Boolean bomExplosion_link = false;
    private Boolean bomRevision_link = false;
    private Boolean pdoPrint_link = false;
    private Boolean pdoSchldPrint_link = false;
    private Boolean pdoStatus_link = false;
    private Boolean pdoSummary_link = false;
    private Boolean dailyProduction_link = false;
    private Boolean jcPrint_link = false;
    private Boolean jcStatus_link = false;
    private Boolean jcBarcode_link = false;
    private Boolean jcCoPrdByPrd_link = false;
    private Boolean jcCostingPrint_link = false;
    private Boolean jcRejectionSumm_link = false;
    private Boolean jcScrapSumm_link = false;
    private Boolean rcPrint_link = false;
    private Boolean rcBarcode_link = false;
    private Boolean rcCoPrdByPrd_link = false;
    private Boolean rcCostingPrint_link = false;
    private Boolean rcRejectionSumm_link = false;
    private Boolean rcScrapSumm_link = false;
    private Boolean stockAtShop_link = false;
    private Boolean wsReading_link = false;
    private boolean rcConsumptionLink = false;
    private boolean jcConsumptionLink = false;
    private Boolean rcIssuedQtyLink = false;
    private Boolean jcIssueQtyLink = false;
    private Boolean rcMRSLink = false;
    private Boolean jcMRSLink = false;
    private Boolean outSOLink = false;
    private Boolean SOPDOLink = false;
    private Boolean planVSactualLink = false;
    private Boolean prodRej_summLink = false;
    private Boolean prodRej_detLink = false;
    private boolean prodRatioLink = false;
    private boolean prodMoveSlipLink = false;
    private boolean machNoPlanLink = false;
    private boolean machPlanLink = false;
    private boolean machDowntimeLink  = false;
    private boolean wsDetLink = false;
    private Boolean shiftListLink = false;
    private Boolean rcRejectionDetLink = false;
    private boolean wsUtilizationLink = false;
    private boolean wsItmUtilizationLink = false;
    private boolean planActualRCLink = false;
    private boolean prodMoveRCLink = false;
    private boolean downRptLink = false;
    private boolean QCTestCertLink = false;
    private boolean gppLink = false;
    private boolean bomHierarchyLink = false;

    public void setBomHierarchyLink(boolean bomHierarchyLink) {
        this.bomHierarchyLink = bomHierarchyLink;
    }

    public boolean isBomHierarchyLink() {
        return bomHierarchyLink;
    }

    public void setGppLink(boolean gppLink) {
        this.gppLink = gppLink;
    }

    public boolean isGppLink() {
        return gppLink;
    }

    public void setQCTestCertLink(boolean QCTestCertLink) {
        this.QCTestCertLink = QCTestCertLink;
    }

    public boolean isQCTestCertLink() {
        return QCTestCertLink;
    }

    public void setDownRptLink(boolean downRptLink) {
        this.downRptLink = downRptLink;
    }

    public boolean isDownRptLink() {
        return downRptLink;
    }
    private RichLink wsUtilizationLinkBind;
    private RichLink wsUtilizationItemLinkBind;
    private RichSelectBooleanCheckbox wsUtilItemCBBind;
    private RichSelectBooleanCheckbox wsUtilCBBind;

    public void setWsUtilizationLink(boolean wsUtilizationLink) {
        this.wsUtilizationLink = wsUtilizationLink;
    }

    public boolean isWsUtilizationLink() {
        return wsUtilizationLink;
    }

    public void setWsItmUtilizationLink(boolean wsItmUtilizationLink) {
        this.wsItmUtilizationLink = wsItmUtilizationLink;
    }

    public boolean isWsItmUtilizationLink() {
        return wsItmUtilizationLink;
    }

    public void setRcRejectionDetLink(Boolean rcRejectionDetLink) {
        this.rcRejectionDetLink = rcRejectionDetLink;
    }

    public Boolean getRcRejectionDetLink() {
        return rcRejectionDetLink;
    }
    private RichSelectBooleanCheckbox pdoSOCBBind;
    private RichSelectBooleanCheckbox outSOCBBind;
    private RichSelectBooleanCheckbox palnVCactualCBBind;
    private RichSelectBooleanCheckbox prodMoveSlipCBBind;
    private RichSelectBooleanCheckbox prodRatioCBBind;
    private RichSelectBooleanCheckbox prodRejDetCBBind;
    private RichSelectBooleanCheckbox prodRejSummCBBind;
    private RichLink outSOLinkBind;
    private RichLink planVSactualLinkBind;
    private RichLink prodMoveSlipLinkBind;
    private RichLink prodRatioLinkBind;
    private RichLink prodRejDetLinkBind;
    private RichLink prodRejSummLinkBind;
    private RichLink pdoSOLinkBind;
    private RichSelectBooleanCheckbox jcConsumpCBBind;
    private RichSelectBooleanCheckbox jcIssuedQtyCBBind;
    private RichSelectBooleanCheckbox jcMRSCBBind;
    private RichLink jcIssuedQtyLinkBind;
    private RichLink jcConsumpLinkBind;
    private RichSelectBooleanCheckbox rcConsumpCBBind;
   // private RichSelectBooleanCheckbox issuedQtyCBBind;
    private RichSelectBooleanCheckbox rcMRSCBBind;
    private RichSelectBooleanCheckbox rcIssuedQtyCBBind;
    private RichLink rcMRSLinkBind;
    private RichLink rcIssuedQtyLinkBind;
    private RichLink rcConsumpLinkBind;
    private RichSelectBooleanCheckbox wsDetCBBind;
    private RichSelectBooleanCheckbox wsDowntimeCBBind;
    private RichSelectBooleanCheckbox wsPlanCBBind;
  //  private RichSelectBooleanCheckbox wsNoPlanCBVCL;
    private RichSelectBooleanCheckbox wsNoPlanCBBind;
    private RichSelectBooleanCheckbox listOfShiftCBBind;
    private RichLink wsDetLinkBind;
    private RichLink wsDowntimeLinkBind;
    private RichLink wsPlanLinkBind;
    private RichLink wsNoPlanLinkBind;
    private RichLink listOfShiftLinkBind;
    private RichLink jcMRSLinkBind;
    private RichSelectBooleanCheckbox rcRejectionDetCBBinding;
    private RichLink rcRejectionDetLinkBind;

    public void setRcConsumptionLink(boolean rcConsumptionLink) {
        this.rcConsumptionLink = rcConsumptionLink;
    }

    public boolean isRcConsumptionLink() {
        return rcConsumptionLink;
    }

    public void setJcConsumptionLink(boolean jcConsumptionLink) {
        this.jcConsumptionLink = jcConsumptionLink;
    }

    public boolean isJcConsumptionLink() {
        return jcConsumptionLink;
    }

    public void setRcIssuedQtyLink(Boolean rcIssuedQtyLink) {
        this.rcIssuedQtyLink = rcIssuedQtyLink;
    }

    public Boolean getRcIssuedQtyLink() {
        return rcIssuedQtyLink;
    }

    public void setJcIssueQtyLink(Boolean jcIssueQtyLink) {
        this.jcIssueQtyLink = jcIssueQtyLink;
    }

    public Boolean getJcIssueQtyLink() {
        return jcIssueQtyLink;
    }

    public void setRcMRSLink(Boolean rcMRSLink) {
        this.rcMRSLink = rcMRSLink;
    }

    public Boolean getRcMRSLink() {
        return rcMRSLink;
    }

    public void setJcMRSLink(Boolean jcMRSLink) {
        this.jcMRSLink = jcMRSLink;
    }

    public Boolean getJcMRSLink() {
        return jcMRSLink;
    }

    public void setOutSOLink(Boolean outSOLink) {
        this.outSOLink = outSOLink;
    }

    public Boolean getOutSOLink() {
        return outSOLink;
    }

    public void setSOPDOLink(Boolean SOPDOLink) {
        this.SOPDOLink = SOPDOLink;
    }

    public Boolean getSOPDOLink() {
        return SOPDOLink;
    }

    public void setPlanVSactualLink(Boolean planVSactualLink) {
        this.planVSactualLink = planVSactualLink;
    }

    public Boolean getPlanVSactualLink() {
        return planVSactualLink;
    }

    public void setProdRej_summLink(Boolean prodRej_summLink) {
        this.prodRej_summLink = prodRej_summLink;
    }

    public Boolean getProdRej_summLink() {
        return prodRej_summLink;
    }

    public void setProdRej_detLink(Boolean prodRej_detLink) {
        this.prodRej_detLink = prodRej_detLink;
    }

    public Boolean getProdRej_detLink() {
        return prodRej_detLink;
    }

    public void setProdRatioLink(boolean prodRatioLink) {
        this.prodRatioLink = prodRatioLink;
    }

    public boolean isProdRatioLink() {
        return prodRatioLink;
    }

    public void setProdMoveSlipLink(boolean prodMoveSlipLink) {
        this.prodMoveSlipLink = prodMoveSlipLink;
    }

    public boolean isProdMoveSlipLink() {
        return prodMoveSlipLink;
    }

    public void setMachNoPlanLink(boolean machNoPlanLink) {
        this.machNoPlanLink = machNoPlanLink;
    }

    public boolean isMachNoPlanLink() {
        return machNoPlanLink;
    }

    public void setMachPlanLink(boolean machPlanLink) {
        this.machPlanLink = machPlanLink;
    }

    public boolean isMachPlanLink() {
        return machPlanLink;
    }

    public void setMachDowntimeLink(boolean machDowntimeLink) {
        this.machDowntimeLink = machDowntimeLink;
    }

    public boolean isMachDowntimeLink() {
        return machDowntimeLink;
    }

    public void setWsDetLink(boolean wsDetLink) {
        this.wsDetLink = wsDetLink;
    }

    public boolean isWsDetLink() {
        return wsDetLink;
    }

    public void setShiftListLink(Boolean shiftListLink) {
        this.shiftListLink = shiftListLink;
    }

    public Boolean getShiftListLink() {
        return shiftListLink;
    }
    private RichSelectBooleanCheckbox bomExplosionCBPgBind;
    private RichSelectBooleanCheckbox bomRevisionCBPgBind;
    private RichSelectBooleanCheckbox pdoPrintCBPgBind;
    private RichSelectBooleanCheckbox pdoSchldPrintCBPgBind;
    private RichSelectBooleanCheckbox pdoStatusCBPgBind;
    private RichSelectBooleanCheckbox pdoSummaryCBPgBind;
    private RichSelectBooleanCheckbox dailyProductionCBPgBind;

    private RichSelectBooleanCheckbox rcPrintCBPgBind;
    private RichSelectBooleanCheckbox rcBarcodeCBPgBind;
    private RichSelectBooleanCheckbox rcCoPrdByPrdCBPgBind;
    private RichSelectBooleanCheckbox rcCostingPrintCBPgBind;
    private RichSelectBooleanCheckbox rcRejectionSummCBPgBind;
    private RichSelectBooleanCheckbox rcScrapSummCBPgBind;

    private RichSelectBooleanCheckbox jcPrintCBPgBind;
    private RichSelectBooleanCheckbox jcStatusCBPgBind;
    private RichSelectBooleanCheckbox jcBarcodeCBPgBind;
    private RichSelectBooleanCheckbox jcCoPrdByPrdCBPgBind;
    private RichSelectBooleanCheckbox jcCostingPrintCBPgBind;
    private RichSelectBooleanCheckbox jcRejectionSummCBPgBind;
    private RichSelectBooleanCheckbox jcScrapSummCBPgBind;

    private RichSelectBooleanCheckbox stockAtShopCBPgBind;
    private RichSelectBooleanCheckbox wsReadingCBPgBind;
    private RichLink bomExplosionLinkPgBind;
    private RichLink bomRevisionLinkPgBind;
    private RichLink pdoPrintLinkPgBind;
    private RichLink pdoSchldPrintLinkPgBind;
    private RichLink pdoStatusLinkPgBind;
    private RichLink pdoSummaryLinkPgBind;
    private RichLink dailyProductionLinkPgBind;
    private RichLink jcPrintLinkPgBind;
    private RichLink jcStatusLinkPgBind;
    private RichLink jcBarcodeLinkPgBind;
    private RichLink jcCoPrdByPrdLinkPgBind;
    private RichLink jcCostingPrintLinkPgBind;
    private RichLink jcRejectionSummLinkPgBind;
    private RichLink jcScrapSummLinkPgBind;
    private RichLink rcPrintLinkPgBind;
    private RichLink rcBarcodeLinkPgBind;
    private RichLink rcCoPrdByPrdLinkPgBind;
    private RichLink rcCostingPrintLinkPgBind;
    private RichLink rcRejectionSummLinkPgBind;
    private RichLink rcScrapSummLinkPgBind;
    private RichLink stockAtShopLinkPgBind;
    private RichLink wsReadingLinkPgBind;
    private RichOutputText bomDocIdPgBind;
    private RichOutputText pdoDocIdPgBind;
    private RichOutputText jcDocIdPgBind;
    private RichOutputText rcDocIdPgBind;
    private RichPanelFormLayout searchReportFormPgBind;

    public void setMode(Boolean mode) {
        this.mode = mode;
    }

    public Boolean getMode() {
        return mode;
    }

    public void setStockAtShop_link(Boolean stockAtShop_link) {
        this.stockAtShop_link = stockAtShop_link;
    }

    public Boolean getStockAtShop_link() {
        return stockAtShop_link;
    }

    public void setWsReading_link(Boolean wsReading_link) {
        this.wsReading_link = wsReading_link;
    }

    public Boolean getWsReading_link() {
        return wsReading_link;
    }

    public void setRcPrint_link(Boolean rcPrint_link) {
        this.rcPrint_link = rcPrint_link;
    }

    public Boolean getRcPrint_link() {
        return rcPrint_link;
    }

    public void setRcBarcode_link(Boolean rcBarcode_link) {
        this.rcBarcode_link = rcBarcode_link;
    }

    public Boolean getRcBarcode_link() {
        return rcBarcode_link;
    }

    public void setRcCoPrdByPrd_link(Boolean rcCoPrdByPrd_link) {
        this.rcCoPrdByPrd_link = rcCoPrdByPrd_link;
    }

    public Boolean getRcCoPrdByPrd_link() {
        return rcCoPrdByPrd_link;
    }

    public void setRcCostingPrint_link(Boolean rcCostingPrint_link) {
        this.rcCostingPrint_link = rcCostingPrint_link;
    }

    public Boolean getRcCostingPrint_link() {
        return rcCostingPrint_link;
    }

    public void setRcRejectionSumm_link(Boolean rcRejectionSumm_link) {
        this.rcRejectionSumm_link = rcRejectionSumm_link;
    }

    public Boolean getRcRejectionSumm_link() {
        return rcRejectionSumm_link;
    }

    public void setRcScrapSumm_link(Boolean rcScrapSumm_link) {
        this.rcScrapSumm_link = rcScrapSumm_link;
    }

    public Boolean getRcScrapSumm_link() {
        return rcScrapSumm_link;
    }

    public void setJcPrint_link(Boolean jcPrint_link) {
        this.jcPrint_link = jcPrint_link;
    }

    public Boolean getJcPrint_link() {
        return jcPrint_link;
    }

    public void setJcStatus_link(Boolean jcStatus_link) {
        this.jcStatus_link = jcStatus_link;
    }

    public Boolean getJcStatus_link() {
        return jcStatus_link;
    }

    public void setJcBarcode_link(Boolean jcBarcode_link) {
        this.jcBarcode_link = jcBarcode_link;
    }

    public Boolean getJcBarcode_link() {
        return jcBarcode_link;
    }

    public void setJcCoPrdByPrd_link(Boolean jcCoPrdByPrd_link) {
        this.jcCoPrdByPrd_link = jcCoPrdByPrd_link;
    }

    public Boolean getJcCoPrdByPrd_link() {
        return jcCoPrdByPrd_link;
    }

    public void setJcCostingPrint_link(Boolean jcCostingPrint_link) {
        this.jcCostingPrint_link = jcCostingPrint_link;
    }

    public Boolean getJcCostingPrint_link() {
        return jcCostingPrint_link;
    }

    public void setJcRejectionSumm_link(Boolean jcRejectionSumm_link) {
        this.jcRejectionSumm_link = jcRejectionSumm_link;
    }

    public Boolean getJcRejectionSumm_link() {
        return jcRejectionSumm_link;
    }

    public void setJcScrapSumm_link(Boolean jcScrapSumm_link) {
        this.jcScrapSumm_link = jcScrapSumm_link;
    }

    public Boolean getJcScrapSumm_link() {
        return jcScrapSumm_link;
    }

    public void setPdoPrint_link(Boolean pdoPrint_link) {
        this.pdoPrint_link = pdoPrint_link;
    }

    public Boolean getPdoPrint_link() {
        return pdoPrint_link;
    }

    public void setPdoSchldPrint_link(Boolean pdoSchldPrint_link) {
        this.pdoSchldPrint_link = pdoSchldPrint_link;
    }

    public Boolean getPdoSchldPrint_link() {
        return pdoSchldPrint_link;
    }

    public void setPdoStatus_link(Boolean pdoStatus_link) {
        this.pdoStatus_link = pdoStatus_link;
    }

    public Boolean getPdoStatus_link() {
        return pdoStatus_link;
    }

    public void setPdoSummary_link(Boolean pdoSummary_link) {
        this.pdoSummary_link = pdoSummary_link;
    }

    public Boolean getPdoSummary_link() {
        return pdoSummary_link;
    }

    public void setDailyProduction_link(Boolean dailyProduction_link) {
        this.dailyProduction_link = dailyProduction_link;
    }

    public Boolean getDailyProduction_link() {
        return dailyProduction_link;
    }

    public void setBomExplosion_link(Boolean bomExplosion_link) {
        this.bomExplosion_link = bomExplosion_link;
    }

    public Boolean getBomExplosion_link() {
        return bomExplosion_link;
    }

    public void setBomRevision_link(Boolean bomRevision_link) {
        this.bomRevision_link = bomRevision_link;
    }

    public Boolean getBomRevision_link() {
        return bomRevision_link;
    }

 

    public void setStartDatePgBind(RichInputDate startDatePgBind) {
        this.startDatePgBind = startDatePgBind;
    }

    public RichInputDate getStartDatePgBind() {
        return startDatePgBind;
    }

    public void setEndDatePgBind(RichInputDate endDatePgBind) {
        this.endDatePgBind = endDatePgBind;
    }

    public RichInputDate getEndDatePgBind() {
        return endDatePgBind;
    }


    /**
     *Method which will check whether Date is Selected or Not
     */
    public boolean checkBoxValidation() {
        if (startDatePgBind.getValue() == null || startDatePgBind.getValue().toString().length() <= 0) {
            return true;
        } else if (endDatePgBind.getValue() == null || endDatePgBind.getValue().toString().length() <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Following method were used to kown whether LOV binding selected or not
     * and check whether to select Date or Not Conditionally
     */
    public Boolean chkLovSelectedOrNot(RichOutputText outputBind) {
        Boolean val = false;

        if (outputBind.getValue() != null && outputBind.getValue().toString().length() > 0) {
            val = true;
        }

        return val;
    }

    /**
     * Following method will display error conditionslly
     */
    public void DisplayDateErrorOnDateConditionally() {
        FacesMessage message =
            new FacesMessage("You have Not Entered Start Date Or End Date...Please Enter Required Date");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext fc = FacesContext.getCurrentInstance();
        if (startDatePgBind.getValue() != null && startDatePgBind.getValue().toString().length() != 0) {
        } else {
            fc.addMessage(startDatePgBind.getClientId(), message);
        }
        if (endDatePgBind.getValue() != null && endDatePgBind.getValue().toString().length() != 0) {
        } else {
            fc.addMessage(endDatePgBind.getClientId(), message);
        }
    }

    /**
     *Will Set Date Fields To null or Blank
     */
    public void SetDateFieldsToNull(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().length() > 0) {

                mode = true;

                startDatePgBind.setValue(null);
                endDatePgBind.setValue(null);
            } else {
                mode = false;
            }
            ADFUtils.findOperation("Execute").execute();
        } else {
            mode = false;
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(startDatePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(endDatePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchReportFormPgBind);
    }

    public void BomIdDispVCL(ValueChangeEvent vce) {
        SetDateFieldsToNull(vce);
        if(vce.getNewValue() != null && vce.getNewValue().toString().length() > 0){
            
        }else{
            bomExplosionCBPgBind.setValue(false);
            bomRevisionCBPgBind.setValue(false);
            
            bomExplosion = false;
            bomRevision = false;
            
            AdfFacesContext.getCurrentInstance().addPartialTarget(bomExplosionCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bomRevisionCBPgBind);
        }
    }

    public void BomExplosionVCL(ValueChangeEvent vce) {
        //System.out.println("bom doc id is "+bomDocIdPgBind.getValue());
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(bomDocIdPgBind)) {
                    bomExplosion = true;
                } else {
                    bomExplosion = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    bomExplosionCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(bomExplosionCBPgBind);
                }
            } else {
                bomExplosion = false;
            }
        }
    }

    public void setBomExplosionCBPgBind(RichSelectBooleanCheckbox bomExplosionCBPgBind) {
        this.bomExplosionCBPgBind = bomExplosionCBPgBind;
    }

    public RichSelectBooleanCheckbox getBomExplosionCBPgBind() {
        return bomExplosionCBPgBind;
    }

    public void setBomRevisionCBPgBind(RichSelectBooleanCheckbox bomRevisionCBPgBind) {
        this.bomRevisionCBPgBind = bomRevisionCBPgBind;
    }

    public RichSelectBooleanCheckbox getBomRevisionCBPgBind() {
        return bomRevisionCBPgBind;
    }

    public void BomRevisionVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(bomDocIdPgBind)) {
                    bomRevision = true;
                } else {
                    bomRevision = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    bomRevisionCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(bomRevisionCBPgBind);
                }
            } else {
                bomRevision = false;
            }
        }
    }

    public void PdoDispIdVCL(ValueChangeEvent vce) {
        SetDateFieldsToNull(vce);
        if(vce.getNewValue() != null && vce.getNewValue().toString().length() > 0){
            
        }else{
            pdoPrintCBPgBind.setValue(false);
            pdoSchldPrintCBPgBind.setValue(false);
            pdoStatusCBPgBind.setValue(false);
            pdoSummaryCBPgBind.setValue(false);
            dailyProductionCBPgBind.setValue(false);
            pdoSOCBBind.setValue(false);
            
            pdoPrint = false;
            pdoSchldPrint = false;
            pdoStatus = false;
            pdoSummary = false;
            dailyProduction = false;
            SOPDO = false;
        
            
            AdfFacesContext.getCurrentInstance().addPartialTarget(pdoPrintCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pdoSchldPrintCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pdoStatusCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pdoSummaryCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dailyProductionCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pdoSOCBBind);
        }
    }

    public void setPdoPrintCBPgBind(RichSelectBooleanCheckbox pdoPrintCBPgBind) {
        this.pdoPrintCBPgBind = pdoPrintCBPgBind;
    }

    public RichSelectBooleanCheckbox getPdoPrintCBPgBind() {
        return pdoPrintCBPgBind;
    }

    public void PdoPrintVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(pdoDocIdPgBind)) {
                    pdoPrint = true;
                } else {
                    pdoPrint = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    pdoPrintCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(pdoPrintCBPgBind);
                }
            } else {
                pdoPrint = false;
            }
        }
    }

    public void setPdoSchldPrintCBPgBind(RichSelectBooleanCheckbox pdoSchldPrintCBPgBind) {
        this.pdoSchldPrintCBPgBind = pdoSchldPrintCBPgBind;
    }

    public RichSelectBooleanCheckbox getPdoSchldPrintCBPgBind() {
        return pdoSchldPrintCBPgBind;
    }

    public void PdoSchldPrintVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(pdoDocIdPgBind)) {
                    pdoSchldPrint = true;
                } else {
                    pdoSchldPrint = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    pdoSchldPrintCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(pdoSchldPrintCBPgBind);
                }
            } else {
                pdoSchldPrint = false;
            }
        }
    }

    public void setPdoStatusCBPgBind(RichSelectBooleanCheckbox pdoStatusCBPgBind) {
        this.pdoStatusCBPgBind = pdoStatusCBPgBind;
    }

    public RichSelectBooleanCheckbox getPdoStatusCBPgBind() {
        return pdoStatusCBPgBind;
    }

    public void PdoStatusVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(pdoDocIdPgBind)) {
                    pdoStatus = true;
                } else {
                    pdoStatus = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    pdoStatusCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(pdoStatusCBPgBind);
                }
            } else {
                pdoStatus = false;
            }
        }
    }

    public void setPdoSummaryCBPgBind(RichSelectBooleanCheckbox pdoSummaryCBPgBind) {
        this.pdoSummaryCBPgBind = pdoSummaryCBPgBind;
    }

    public RichSelectBooleanCheckbox getPdoSummaryCBPgBind() {
        return pdoSummaryCBPgBind;
    }

    public void PdoSummaryVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(pdoDocIdPgBind)) {
                    pdoSummary = true;
                } else {
                    pdoSummary = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    pdoSummaryCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(pdoSummaryCBPgBind);
                }
            } else {
                pdoSummary = false;
            }
        }
    }

    public void setDailyProductionCBPgBind(RichSelectBooleanCheckbox dailyProductionCBPgBind) {
        this.dailyProductionCBPgBind = dailyProductionCBPgBind;
    }

    public RichSelectBooleanCheckbox getDailyProductionCBPgBind() {
        return dailyProductionCBPgBind;
    }

    public void DailyProductionVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(pdoDocIdPgBind)) {
                    dailyProduction = true;
                } else {
                    dailyProduction = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    dailyProductionCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(dailyProductionCBPgBind);
                }
            } else {
                dailyProduction = false;
            }
        }
    }

    public void JcDispIdVCL(ValueChangeEvent vce) {
        SetDateFieldsToNull(vce);
        if(vce.getNewValue() != null && vce.getNewValue().toString().length() > 0){
            
        }else{
            jcPrintCBPgBind.setValue(false);
            jcStatusCBPgBind.setValue(false);
            jcBarcodeCBPgBind.setValue(false);
            jcCoPrdByPrdCBPgBind.setValue(false);
            jcCostingPrintCBPgBind.setValue(false);
            jcRejectionSummCBPgBind.setValue(false);
            jcScrapSummCBPgBind.setValue(false);
            
            jcPrint = false;
            jcStatus = false;
            jcBarcode = false;
            jcCoPrdByPrd = false;
            jcCostingPrint = false;
            jcRejectionSumm = false;
            jcScrapSumm = false;
            
            AdfFacesContext.getCurrentInstance().addPartialTarget(jcPrintCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(jcStatusCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(jcBarcodeCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(jcCoPrdByPrdCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(jcCostingPrintCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(jcRejectionSummCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(jcScrapSummCBPgBind);
        }
    }

    public void setJcPrintCBPgBind(RichSelectBooleanCheckbox jcPrintCBPgBind) {
        this.jcPrintCBPgBind = jcPrintCBPgBind;
    }

    public RichSelectBooleanCheckbox getJcPrintCBPgBind() {
        return jcPrintCBPgBind;
    }

    public void JcPrintVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(jcDocIdPgBind)) {
                    jcPrint = true;
                } else {
                    jcPrint = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    jcPrintCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(jcPrintCBPgBind);
                }
            } else {
                jcPrint = false;
            }
        }
    }

    public void setJcStatusCBPgBind(RichSelectBooleanCheckbox jcStatusCBPgBind) {
        this.jcStatusCBPgBind = jcStatusCBPgBind;
    }

    public RichSelectBooleanCheckbox getJcStatusCBPgBind() {
        return jcStatusCBPgBind;
    }

    public void JcStatusVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(jcDocIdPgBind)) {
                    jcStatus = true;
                } else {
                    jcStatus = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    jcStatusCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(jcStatusCBPgBind);
                }
            } else {
                jcStatus = false;
            }
        }
    }

    public void setJcBarcodeCBPgBind(RichSelectBooleanCheckbox jcBarcodeCBPgBind) {
        this.jcBarcodeCBPgBind = jcBarcodeCBPgBind;
    }

    public RichSelectBooleanCheckbox getJcBarcodeCBPgBind() {
        return jcBarcodeCBPgBind;
    }

    public void JcBarcodeVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(jcDocIdPgBind)) {
                    jcBarcode = true;
                } else {
                    jcBarcode = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    jcBarcodeCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(jcBarcodeCBPgBind);
                }
            } else {
                jcBarcode = false;
            }
        }
    }

    public void setJcCoPrdByPrdCBPgBind(RichSelectBooleanCheckbox jcCoPrdByPrdCBPgBind) {
        this.jcCoPrdByPrdCBPgBind = jcCoPrdByPrdCBPgBind;
    }

    public RichSelectBooleanCheckbox getJcCoPrdByPrdCBPgBind() {
        return jcCoPrdByPrdCBPgBind;
    }

    public void JcCoPrdByPrdVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(jcDocIdPgBind)) {
                    jcCoPrdByPrd = true;
                } else {
                    jcCoPrdByPrd = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    jcCoPrdByPrdCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(jcCoPrdByPrdCBPgBind);
                }
            } else {
                jcCoPrdByPrd = false;
            }
        }
    }

    public void setJcCostingPrintCBPgBind(RichSelectBooleanCheckbox jcCostingPrintCBPgBind) {
        this.jcCostingPrintCBPgBind = jcCostingPrintCBPgBind;
    }

    public RichSelectBooleanCheckbox getJcCostingPrintCBPgBind() {
        return jcCostingPrintCBPgBind;
    }

    public void JcCostingPrintVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(jcDocIdPgBind)) {
                    jcCostingPrint = true;
                } else {
                    jcCostingPrint = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    jcCostingPrintCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(jcCostingPrintCBPgBind);
                }
            } else {
                jcCostingPrint = false;
            }
        }
    }

    public void setJcRejectionSummCBPgBind(RichSelectBooleanCheckbox jcRejectionSummCBPgBind) {
        this.jcRejectionSummCBPgBind = jcRejectionSummCBPgBind;
    }

    public RichSelectBooleanCheckbox getJcRejectionSummCBPgBind() {
        return jcRejectionSummCBPgBind;
    }

    public void JcRejectionSummVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(jcDocIdPgBind)) {
                    jcRejectionSumm = true;
                } else {
                    jcRejectionSumm = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    jcRejectionSummCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(jcRejectionSummCBPgBind);
                }
            } else {
                jcRejectionSumm = false;
            }
        }
    }

    public void setJcScrapSummCBPgBind(RichSelectBooleanCheckbox jcScrapSummCBPgBind) {
        this.jcScrapSummCBPgBind = jcScrapSummCBPgBind;
    }

    public RichSelectBooleanCheckbox getJcScrapSummCBPgBind() {
        return jcScrapSummCBPgBind;
    }

    public void JcScrapSummVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(jcDocIdPgBind)) {
                    jcScrapSumm = true;
                } else {
                    jcScrapSumm = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    jcScrapSummCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(jcScrapSummCBPgBind);
                }
            } else {
                jcScrapSumm = false;
            }
        }
    }

    public void RcDispIdVCL(ValueChangeEvent vce) {
        SetDateFieldsToNull(vce);
        if(vce.getNewValue() != null && vce.getNewValue().toString().length() > 0){
            
        }else{
            rcPrintCBPgBind.setValue(false);
            rcBarcodeCBPgBind.setValue(false);
            rcCoPrdByPrdCBPgBind.setValue(false);
            rcCostingPrintCBPgBind.setValue(false);
            rcRejectionSummCBPgBind.setValue(false);
            rcScrapSummCBPgBind.setValue(false);
            
            rcPrint = false;
            rcBarcode = false;
            rcCoPrdByPrd = false;
            rcCostingPrint = false;
            rcRejectionSumm = false;
            rcScrapSumm = false;
            
            AdfFacesContext.getCurrentInstance().addPartialTarget(rcPrintCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rcBarcodeCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rcCoPrdByPrdCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rcCostingPrintCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rcRejectionSummCBPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rcScrapSummCBPgBind);
        }
    }

    public void setRcPrintCBPgBind(RichSelectBooleanCheckbox rcPrintCBPgBind) {
        this.rcPrintCBPgBind = rcPrintCBPgBind;
    }

    public RichSelectBooleanCheckbox getRcPrintCBPgBind() {
        return rcPrintCBPgBind;
    }

    public void RcPrintVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(rcDocIdPgBind)) {
                    rcPrint = true;
                } else {
                    rcPrint = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    rcPrintCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(rcPrintCBPgBind);
                }
            } else {
                rcPrint = false;
            }
        }
    }

    public void setRcBarcodeCBPgBind(RichSelectBooleanCheckbox rcBarcodeCBPgBind) {
        this.rcBarcodeCBPgBind = rcBarcodeCBPgBind;
    }

    public RichSelectBooleanCheckbox getRcBarcodeCBPgBind() {
        return rcBarcodeCBPgBind;
    }

    public void RcBarcodeVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(rcDocIdPgBind)) {
                    rcBarcode = true;
                } else {
                    rcBarcode = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    rcBarcodeCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(rcBarcodeCBPgBind);
                }
            } else {
                rcBarcode = false;
            }
        }
    }

    public void setRcCoPrdByPrdCBPgBind(RichSelectBooleanCheckbox rcCoPrdByPrdCBPgBind) {
        this.rcCoPrdByPrdCBPgBind = rcCoPrdByPrdCBPgBind;
    }

    public RichSelectBooleanCheckbox getRcCoPrdByPrdCBPgBind() {
        return rcCoPrdByPrdCBPgBind;
    }

    public void RcCoPrdByPrdVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(rcDocIdPgBind)) {
                    rcCoPrdByPrd = true;
                } else {
                    rcCoPrdByPrd = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    rcCoPrdByPrdCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(rcCoPrdByPrdCBPgBind);
                }
            } else {
                rcCoPrdByPrd = false;
            }
        }
    }

    public void setRcCostingPrintCBPgBind(RichSelectBooleanCheckbox rcCostingPrintCBPgBind) {
        this.rcCostingPrintCBPgBind = rcCostingPrintCBPgBind;
    }

    public RichSelectBooleanCheckbox getRcCostingPrintCBPgBind() {
        return rcCostingPrintCBPgBind;
    }

    public void RcCostingPrintVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(rcDocIdPgBind)) {
                    rcCostingPrint = true;
                } else {
                    rcCostingPrint = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    rcCostingPrintCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(rcCostingPrintCBPgBind);
                }
            } else {
                rcCostingPrint = false;
            }
        }
    }

    public void setRcRejectionSummCBPgBind(RichSelectBooleanCheckbox rcRejectionSummCBPgBind) {
        this.rcRejectionSummCBPgBind = rcRejectionSummCBPgBind;
    }

    public RichSelectBooleanCheckbox getRcRejectionSummCBPgBind() {
        return rcRejectionSummCBPgBind;
    }

    public void RcRejectionSummVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(rcDocIdPgBind)) {
                    rcRejectionSumm = true;
                } else {
                    rcRejectionSumm = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    rcRejectionSummCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(rcRejectionSummCBPgBind);
                }
            } else {
                rcRejectionSumm = false;
            }
        }
    }

    public void setRcScrapSummCBPgBind(RichSelectBooleanCheckbox rcScrapSummCBPgBind) {
        this.rcScrapSummCBPgBind = rcScrapSummCBPgBind;
    }

    public RichSelectBooleanCheckbox getRcScrapSummCBPgBind() {
        return rcScrapSummCBPgBind;
    }

    public void RcScrapSummVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(rcDocIdPgBind)) {
                    rcScrapSumm = true;
                } else {
                    rcScrapSumm = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    rcScrapSummCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(rcScrapSummCBPgBind);
                }
            } else {
                rcScrapSumm = false;
            }
        }
    }

    public void setStockAtShopCBPgBind(RichSelectBooleanCheckbox stockAtShopCBPgBind) {
        this.stockAtShopCBPgBind = stockAtShopCBPgBind;
    }

    public RichSelectBooleanCheckbox getStockAtShopCBPgBind() {
        return stockAtShopCBPgBind;
    }

    public void StockAtShopVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation()) {
                    stockAtShop = true;
                } else {
                    stockAtShop = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    stockAtShopCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(stockAtShopCBPgBind);
                }
            } else {
                stockAtShop = false;
            }
        }
    }

    public void setWsReadingCBPgBind(RichSelectBooleanCheckbox wsReadingCBPgBind) {
        this.wsReadingCBPgBind = wsReadingCBPgBind;
    }

    public RichSelectBooleanCheckbox getWsReadingCBPgBind() {
        return wsReadingCBPgBind;
    }

    public void WsReadingVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation()) {
                    wsReading = true;
                } else {
                    wsReading = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    wsReadingCBPgBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(wsReadingCBPgBind);
                }
            } else {
                wsReading = false;
            }
        }
    }

    public void GenerateReportAL(ActionEvent actionEvent) {
       // System.out.println("--callingb---");
         //ADFUtils.findOperation("setDate").execute();
      //  System.out.println("--calledb---");
        setBomExplosion_link(bomExplosion);
        setBomRevision_link(bomRevision);
        setPdoPrint_link(pdoPrint);
        setPdoSchldPrint_link(pdoSchldPrint);
        setPdoStatus_link(pdoStatus);
        setPdoSummary_link(pdoSummary);
        setDailyProduction_link(dailyProduction);
        setJcPrint_link(jcPrint);
        setJcStatus_link(jcStatus);
        setJcBarcode_link(jcBarcode);
        setJcCoPrdByPrd_link(jcCoPrdByPrd);
        setJcCostingPrint_link(jcCostingPrint);
        setJcRejectionSumm_link(jcRejectionSumm);
        setJcScrapSumm_link(jcScrapSumm);
        setRcPrint_link(rcPrint);
        setRcBarcode_link(rcBarcode);
        setRcCoPrdByPrd_link(rcCoPrdByPrd);
        setRcCostingPrint_link(rcCostingPrint);
        setRcRejectionSumm_link(rcRejectionSumm);
        setRcScrapSumm_link(rcScrapSumm);
        setStockAtShop_link(stockAtShop);
        setWsReading_link(wsReading);
        
        setOutSOLink(outSO);
        setSOPDOLink(SOPDO);
        setProdMoveSlipLink(prodMoveSlip);
        setProdRatioLink(prodRatio);
        setProdRej_detLink(prodRej_det);
        setProdRej_summLink(prodRej_summ);
        setPlanVSactualLink(planVSactual);
        setRcConsumptionLink(rcConsumption);
        setRcIssuedQtyLink(rcIssuedQty);
        setRcMRSLink(rcMRS);
        setJcConsumptionLink(jcConsumption);
        setJcIssueQtyLink(jcIssueQty);
        setJcMRSLink(jcMRS);
        setMachDowntimeLink(machDowntime);
        setMachNoPlanLink(machNoPlan);
        setMachPlanLink(machPlan);
        setWsDetLink(wsDet);
        setShiftListLink(shiftList);
        setRcRejectionDetLink(rcRejectionDet);
        setWsUtilizationLink(wsUtilization);
        
        setWsItmUtilizationLink(wsItmUtilization);
        
        /*   if(wsUtilItemCBBind.isSelected()){
            this.setWsItmUtilizationLink(true);
        }else this.setWsItmUtilizationLink(false); 
      */   setPlanActualRCLink(planActualRC);
        setProdMoveRCLink(prodMoveRC);
        setDownRptLink(downRpt);
        setQCTestCertLink(QCTestCert);
        setGppLink(gpp);
        setBomHierarchyLink(bomHierarchy);
        if(prodFcCBBinding.isSelected()){
            ADFUtils.findOperation("setProdFcTypeTrans").execute();
            this.setProdFcLink(true);
      //  this.prodFcLinkBinding.setVisible(true);
            System.out.println("inside true "+prodFcLink+prodFcCBBinding.getValue().toString());
        } else setProdFcLink(false);
    //    this.prodFcLinkBinding.setVisible(false);
     //   ADFUtils.findOperation("SelectDateByDefaultBasedOnLOV").execute();
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(startDatePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(endDatePgBind);

        AdfFacesContext.getCurrentInstance().addPartialTarget(bomExplosionLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bomRevisionLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoPrintLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoSchldPrintLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoStatusLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoSummaryLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(dailyProductionLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(jcPrintLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(jcStatusLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(jcBarcodeLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(jcCoPrdByPrdLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(jcCostingPrintLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(jcRejectionSummLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(jcScrapSummLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcPrintLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcBarcodeLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcCoPrdByPrdLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcCostingPrintLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcRejectionSummLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcScrapSummLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(stockAtShopLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(wsReadingLinkPgBind);
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(outSOLinkBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(planVSactualLinkBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(prodRejDetLinkBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(prodRejSummLinkBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(prodMoveSlipLinkBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoSOLinkBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(prodRatioLinkBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(jcConsumpLinkBind);
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(jcIssuedQtyLinkBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(jcMRSLinkBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcConsumpLinkBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcIssuedQtyLinkBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcMRSLinkBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(wsDetLinkBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(wsPlanLinkBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(wsNoPlanLinkBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(wsDowntimeLinkBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(listOfShiftLinkBind); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(rcRejectionDetLinkBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(wsUtilizationLinkBind); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(wsUtilizationItemLinkBind); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(planVsActRCLinkBind); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(prodMoveRCLinkBind);  
        AdfFacesContext.getCurrentInstance().addPartialTarget(dwonRptLinkPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(qcTestCertLinkBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(gpPpgLinkBiind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bomHierarchyLinkBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(prodFcLinkBinding);
           
        if(downRptCBPgBind.isSelected()){
            dwonRptLinkPgBind.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dwonRptLinkPgBind);
        }
    }

    public void setBomExplosionLinkPgBind(RichLink bomExplosionLinkPgBind) {
        this.bomExplosionLinkPgBind = bomExplosionLinkPgBind;
    }

    public RichLink getBomExplosionLinkPgBind() {
        return bomExplosionLinkPgBind;
    }

    public void setBomRevisionLinkPgBind(RichLink bomRevisionLinkPgBind) {
        this.bomRevisionLinkPgBind = bomRevisionLinkPgBind;
    }

    public RichLink getBomRevisionLinkPgBind() {
        return bomRevisionLinkPgBind;
    }

    public void setPdoPrintLinkPgBind(RichLink pdoPrintLinkPgBind) {
        this.pdoPrintLinkPgBind = pdoPrintLinkPgBind;
    }

    public RichLink getPdoPrintLinkPgBind() {
        return pdoPrintLinkPgBind;
    }

    public void setPdoSchldPrintLinkPgBind(RichLink pdoSchldPrintLinkPgBind) {
        this.pdoSchldPrintLinkPgBind = pdoSchldPrintLinkPgBind;
    }

    public RichLink getPdoSchldPrintLinkPgBind() {
        return pdoSchldPrintLinkPgBind;
    }

    public void setPdoStatusLinkPgBind(RichLink pdoStatusLinkPgBind) {
        this.pdoStatusLinkPgBind = pdoStatusLinkPgBind;
    }

    public RichLink getPdoStatusLinkPgBind() {
        return pdoStatusLinkPgBind;
    }

    public void setPdoSummaryLinkPgBind(RichLink pdoSummaryLinkPgBind) {
        this.pdoSummaryLinkPgBind = pdoSummaryLinkPgBind;
    }

    public RichLink getPdoSummaryLinkPgBind() {
        return pdoSummaryLinkPgBind;
    }

    public void setDailyProductionLinkPgBind(RichLink dailyProductionLinkPgBind) {
        this.dailyProductionLinkPgBind = dailyProductionLinkPgBind;
    }

    public RichLink getDailyProductionLinkPgBind() {
        return dailyProductionLinkPgBind;
    }

    public void setJcPrintLinkPgBind(RichLink jcPrintLinkPgBind) {
        this.jcPrintLinkPgBind = jcPrintLinkPgBind;
    }

    public RichLink getJcPrintLinkPgBind() {
        return jcPrintLinkPgBind;
    }

    public void setJcStatusLinkPgBind(RichLink jcStatusLinkPgBind) {
        this.jcStatusLinkPgBind = jcStatusLinkPgBind;
    }

    public RichLink getJcStatusLinkPgBind() {
        return jcStatusLinkPgBind;
    }

    public void setJcBarcodeLinkPgBind(RichLink jcBarcodeLinkPgBind) {
        this.jcBarcodeLinkPgBind = jcBarcodeLinkPgBind;
    }

    public RichLink getJcBarcodeLinkPgBind() {
        return jcBarcodeLinkPgBind;
    }

    public void setJcCoPrdByPrdLinkPgBind(RichLink jcCoPrdByPrdLinkPgBind) {
        this.jcCoPrdByPrdLinkPgBind = jcCoPrdByPrdLinkPgBind;
    }

    public RichLink getJcCoPrdByPrdLinkPgBind() {
        return jcCoPrdByPrdLinkPgBind;
    }

    public void setJcCostingPrintLinkPgBind(RichLink jcCostingPrintLinkPgBind) {
        this.jcCostingPrintLinkPgBind = jcCostingPrintLinkPgBind;
    }

    public RichLink getJcCostingPrintLinkPgBind() {
        return jcCostingPrintLinkPgBind;
    }

    public void setJcRejectionSummLinkPgBind(RichLink jcRejectionSummLinkPgBind) {
        this.jcRejectionSummLinkPgBind = jcRejectionSummLinkPgBind;
    }

    public RichLink getJcRejectionSummLinkPgBind() {
        return jcRejectionSummLinkPgBind;
    }

    public void setJcScrapSummLinkPgBind(RichLink jcScrapSummLinkPgBind) {
        this.jcScrapSummLinkPgBind = jcScrapSummLinkPgBind;
    }

    public RichLink getJcScrapSummLinkPgBind() {
        return jcScrapSummLinkPgBind;
    }

    public void setRcPrintLinkPgBind(RichLink rcPrintLinkPgBind) {
        this.rcPrintLinkPgBind = rcPrintLinkPgBind;
    }

    public RichLink getRcPrintLinkPgBind() {
        return rcPrintLinkPgBind;
    }

    public void setRcBarcodeLinkPgBind(RichLink rcBarcodeLinkPgBind) {
        this.rcBarcodeLinkPgBind = rcBarcodeLinkPgBind;
    }

    public RichLink getRcBarcodeLinkPgBind() {
        return rcBarcodeLinkPgBind;
    }

    public void setRcCoPrdByPrdLinkPgBind(RichLink rcCoPrdByPrdLinkPgBind) {
        this.rcCoPrdByPrdLinkPgBind = rcCoPrdByPrdLinkPgBind;
    }

    public RichLink getRcCoPrdByPrdLinkPgBind() {
        return rcCoPrdByPrdLinkPgBind;
    }

    public void setRcCostingPrintLinkPgBind(RichLink rcCostingPrintLinkPgBind) {
        this.rcCostingPrintLinkPgBind = rcCostingPrintLinkPgBind;
    }

    public RichLink getRcCostingPrintLinkPgBind() {
        return rcCostingPrintLinkPgBind;
    }

    public void setRcRejectionSummLinkPgBind(RichLink rcRejectionSummLinkPgBind) {
        this.rcRejectionSummLinkPgBind = rcRejectionSummLinkPgBind;
    }

    public RichLink getRcRejectionSummLinkPgBind() {
        return rcRejectionSummLinkPgBind;
    }

    public void setRcScrapSummLinkPgBind(RichLink rcScrapSummLinkPgBind) {
        this.rcScrapSummLinkPgBind = rcScrapSummLinkPgBind;
    }

    public RichLink getRcScrapSummLinkPgBind() {
        return rcScrapSummLinkPgBind;
    }

    public void setStockAtShopLinkPgBind(RichLink stockAtShopLinkPgBind) {
        this.stockAtShopLinkPgBind = stockAtShopLinkPgBind;
    }

    public RichLink getStockAtShopLinkPgBind() {
        return stockAtShopLinkPgBind;
    }

    public void setWsReadingLinkPgBind(RichLink wsReadingLinkPgBind) {
        this.wsReadingLinkPgBind = wsReadingLinkPgBind;
    }

    public RichLink getWsReadingLinkPgBind() {
        return wsReadingLinkPgBind;
    }

    public void EndDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (startDatePgBind.getValue() != null) {
                // System.out.println("From Date is " + operationStartDatePgBind.getValue());
                //System.out.println("To date is " + object);

                Timestamp t1 = (Timestamp)startDatePgBind.getValue();
                Timestamp t2 = (Timestamp)object;

                // System.out.println("T2 Compare to T1 " + t2.compareTo(t1));
                if (t2.compareTo(t1) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "To Date Should be greater than From Date.", null));
                }
            }
        }
    }

    public void setBomDocIdPgBind(RichOutputText bomDocIdPgBind) {
        this.bomDocIdPgBind = bomDocIdPgBind;
    }

    public RichOutputText getBomDocIdPgBind() {
        return bomDocIdPgBind;
    }

    public void setPdoDocIdPgBind(RichOutputText pdoDocIdPgBind) {
        this.pdoDocIdPgBind = pdoDocIdPgBind;
    }

    public RichOutputText getPdoDocIdPgBind() {
        return pdoDocIdPgBind;
    }

    public void setJcDocIdPgBind(RichOutputText jcDocIdPgBind) {
        this.jcDocIdPgBind = jcDocIdPgBind;
    }

    public RichOutputText getJcDocIdPgBind() {
        return jcDocIdPgBind;
    }

    public void setRcDocIdPgBind(RichOutputText rcDocIdPgBind) {
        this.rcDocIdPgBind = rcDocIdPgBind;
    }

    public RichOutputText getRcDocIdPgBind() {
        return rcDocIdPgBind;
    }

    public void setSearchReportFormPgBind(RichPanelFormLayout searchReportFormPgBind) {
        this.searchReportFormPgBind = searchReportFormPgBind;
    }

    public RichPanelFormLayout getSearchReportFormPgBind() {
        return searchReportFormPgBind;
    }

    public void BomTabDL(DisclosureEvent disclosureEvent) {
        mode = false;
        ADFUtils.findOperation("ExecuteTempData").execute();
        ADFUtils.findOperation("setDate").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchReportFormPgBind);
        
    }

    public void PdoTabDL(DisclosureEvent disclosureEvent) {
        mode = false;
        ADFUtils.findOperation("ExecuteTempData").execute();
        ADFUtils.findOperation("setDate").execute(); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchReportFormPgBind);
    }

    public void JcTabDL(DisclosureEvent disclosureEvent) {
        mode = false;
        ADFUtils.findOperation("setDate").execute(); 
        ADFUtils.findOperation("ExecuteTempData").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchReportFormPgBind);
    }

    public void RcTabDL(DisclosureEvent disclosureEvent) {
        mode = false;
        ADFUtils.findOperation("ExecuteTempData").execute();
        ADFUtils.findOperation("setDate").execute(); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchReportFormPgBind);
    }

    public void OthersTabDL(DisclosureEvent disclosureEvent) {
        mode = false;
        ADFUtils.findOperation("ExecuteTempData").execute();
        ADFUtils.findOperation("setDate").execute(); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchReportFormPgBind);
        qcTestCertCBBind.setValue(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(qcTestCertCBBind);
//        qcTestCertLinkBind.setVisible(false);
//        AdfFacesContext.getCurrentInstance().addPartialTarget(qcTestCertLinkBind);
    }

    public void setPdoSOCBBind(RichSelectBooleanCheckbox pdoSOCBBind) {
        this.pdoSOCBBind = pdoSOCBBind;
    }

    public RichSelectBooleanCheckbox getPdoSOCBBind() {
        return pdoSOCBBind;
    }

    public void pdoSOCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(pdoDocIdPgBind)) {
                    SOPDO = true;
                } else {
                    SOPDO = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    pdoSOCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(pdoSOCBBind);
                }
            } else {
                SOPDO = false;
            }
        }
    }

    public void outSOCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(pdoDocIdPgBind)) {
                    outSO = true;
                } else {
                    outSO = false;
                    
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    outSOCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(outSOCBBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(outSOLinkBind);
                }
            } else {
                outSO = false;
            }
        }
    }

    public void setOutSOCBBind(RichSelectBooleanCheckbox outSOCBBind) {
        this.outSOCBBind = outSOCBBind;
    }

    public RichSelectBooleanCheckbox getOutSOCBBind() {
        return outSOCBBind;
    }

    public void setPalnVCactualCBBind(RichSelectBooleanCheckbox palnVCactualCBBind) {
        this.palnVCactualCBBind = palnVCactualCBBind;
    }

    public RichSelectBooleanCheckbox getPalnVCactualCBBind() {
        return palnVCactualCBBind;
    }

    public void planVSactualCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(jcDocIdPgBind)) {
                    planVSactual = true;
                } else {
                    planVSactual = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    palnVCactualCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(palnVCactualCBBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(planVSactualLinkBind);
                }
            } else {
                planVSactual = false;
            }
        }
    }

    public void setProdMoveSlipCBBind(RichSelectBooleanCheckbox prodMoveSlipCBBind) {
        this.prodMoveSlipCBBind = prodMoveSlipCBBind;
    }

    public RichSelectBooleanCheckbox getProdMoveSlipCBBind() {
        return prodMoveSlipCBBind;
    }

    public void prodMoveSlipCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(jcDocIdPgBind)) {
                    prodMoveSlip = true;
                } else {
                    prodMoveSlip = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    prodMoveSlipCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(prodMoveSlipCBBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(prodMoveSlipLinkBind);
                }
            } else {
                prodMoveSlip = false;
            }
        }
    }

    public void prodRatioCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(jcDocIdPgBind)) {
                    prodRatio = true;
                } else {
                    prodRatio = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    prodRatioCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(prodRatioCBBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(prodRatioLinkBind);
                }
            } else {
                prodRatio = false;
            }
        }
    }

    public void setProdRatioCBBind(RichSelectBooleanCheckbox prodRatioCBBind) {
        this.prodRatioCBBind = prodRatioCBBind;
    }

    public RichSelectBooleanCheckbox getProdRatioCBBind() {
        return prodRatioCBBind;
    }

    public void prodRejDetCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(jcDocIdPgBind)) {
                    prodRej_det = true;
                } else {
                    prodRej_det = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    prodRejDetCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(prodRejDetCBBind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(prodRejDetLinkBind);
                }
            } else {
                prodRej_det = false;
            }
        }
    }

    public void setProdRejDetCBBind(RichSelectBooleanCheckbox prodRejDetCBBind) {
        this.prodRejDetCBBind = prodRejDetCBBind;
    }

    public RichSelectBooleanCheckbox getProdRejDetCBBind() {
        return prodRejDetCBBind;
    }

    public void setProdRejSummCBBind(RichSelectBooleanCheckbox prodRejSummCBBind) {
        this.prodRejSummCBBind = prodRejSummCBBind;
    }

    public RichSelectBooleanCheckbox getProdRejSummCBBind() {
        return prodRejSummCBBind;
    }

    public void prodRejSummCBVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(jcDocIdPgBind)) {
                    prodRej_summ = true;
                } else {
                    prodRej_summ = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    prodRejSummCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(prodRejSummCBBind);
                }
            } else {
                prodRej_summ = false;
            }
        }
    }

    public void setOutSOLinkBind(RichLink outSOLinkBind) {
        this.outSOLinkBind = outSOLinkBind;
    }

    public RichLink getOutSOLinkBind() {
        return outSOLinkBind;
    }

    public void setPlanVSactualLinkBind(RichLink planVSactualLinkBind) {
        this.planVSactualLinkBind = planVSactualLinkBind;
    }

    public RichLink getPlanVSactualLinkBind() {
        return planVSactualLinkBind;
    }

    public void setProdMoveSlipLinkBind(RichLink prodMoveSlipLinkBind) {
        this.prodMoveSlipLinkBind = prodMoveSlipLinkBind;
    }

    public RichLink getProdMoveSlipLinkBind() {
        return prodMoveSlipLinkBind;
    }

    public void setProdRatioLinkBind(RichLink prodRatioLinkBind) {
        this.prodRatioLinkBind = prodRatioLinkBind;
    }

    public RichLink getProdRatioLinkBind() {
        return prodRatioLinkBind;
    }

    public void setProdRejDetLinkBind(RichLink prodRejDetLinkBind) {
        this.prodRejDetLinkBind = prodRejDetLinkBind;
    }

    public RichLink getProdRejDetLinkBind() {
        return prodRejDetLinkBind;
    }

    public void setProdRejSummLinkBind(RichLink prodRejSummLinkBind) {
        this.prodRejSummLinkBind = prodRejSummLinkBind;
    }

    public RichLink getProdRejSummLinkBind() {
        return prodRejSummLinkBind;
    }

    public void setPdoSOLinkBind(RichLink pdoSOLinkBind) {
        this.pdoSOLinkBind = pdoSOLinkBind;
    }

    public RichLink getPdoSOLinkBind() {
        return pdoSOLinkBind;
    }

    public void jcConsumpCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
                   if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                       if (!checkBoxValidation() || chkLovSelectedOrNot(jcDocIdPgBind)) {
                           jcConsumption = true;
                       } else {
                           jcConsumption = false;
                           DisplayDateErrorOnDateConditionally();
                           //set chaeckbox  false
                           jcConsumpCBBind.setValue(false);
                           AdfFacesContext.getCurrentInstance().addPartialTarget(jcConsumpCBBind);
                       }
                   } else {
                       jcConsumption = false;
                   }
               }
    }

    public void setJcConsumpCBBind(RichSelectBooleanCheckbox jcConsumpCBBind) {
        this.jcConsumpCBBind = jcConsumpCBBind;
    }

    public RichSelectBooleanCheckbox getJcConsumpCBBind() {
        return jcConsumpCBBind;
    }

    public void jcIssuedQtyCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
                   if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                       if (!checkBoxValidation() || chkLovSelectedOrNot(jcDocIdPgBind)) {
                           jcIssueQty = true;
                       } else {
                           jcIssueQty = false;
                           DisplayDateErrorOnDateConditionally();
                           //set chaeckbox  false
                           jcIssuedQtyCBBind.setValue(false);
                           AdfFacesContext.getCurrentInstance().addPartialTarget(jcIssuedQtyCBBind);
                       }
                   } else {
                       jcIssueQty = false;
                   }
               }
    }

    public void setJcIssuedQtyCBBind(RichSelectBooleanCheckbox jcIssuedQtyCBBind) {
        this.jcIssuedQtyCBBind = jcIssuedQtyCBBind;
    }

    public RichSelectBooleanCheckbox getJcIssuedQtyCBBind() {
        return jcIssuedQtyCBBind;
    }

    public void jcMRSCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
                   if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                       if (!checkBoxValidation() || chkLovSelectedOrNot(jcDocIdPgBind)) {
                           jcMRS = true;
                       } else {
                           jcMRS = false;
                           DisplayDateErrorOnDateConditionally();
                           //set chaeckbox  false
                           jcMRSCBBind.setValue(false);
                           AdfFacesContext.getCurrentInstance().addPartialTarget(jcMRSCBBind);
                       }
                   } else {
                       jcMRS = false;
                       jcMRSLinkBind.setVisible(false);
                       AdfFacesContext.getCurrentInstance().addPartialTarget(jcMRSLinkBind);
                       
                   }
               }
    }

    public void setJcMRSCBBind(RichSelectBooleanCheckbox jcMRSCBBind) {
        this.jcMRSCBBind = jcMRSCBBind;
    }

    public RichSelectBooleanCheckbox getJcMRSCBBind() {
        return jcMRSCBBind;
    }

    public void setJcIssuedQtyLinkBind(RichLink jcIssuedQtyLinkBind) {
        this.jcIssuedQtyLinkBind = jcIssuedQtyLinkBind;
    }

    public RichLink getJcIssuedQtyLinkBind() {
        return jcIssuedQtyLinkBind;
    }

    public void setJcConsumpLinkBind(RichLink jcConsumpLinkBind) {
        this.jcConsumpLinkBind = jcConsumpLinkBind;
    }

    public RichLink getJcConsumpLinkBind() {
        return jcConsumpLinkBind;
    }

    public void setRcConsumpCBBind(RichSelectBooleanCheckbox rcConsumpCBBind) {
        this.rcConsumpCBBind = rcConsumpCBBind;
    }

    public RichSelectBooleanCheckbox getRcConsumpCBBind() {
        return rcConsumpCBBind;
    }

    public void rcConsumpCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(rcDocIdPgBind)) {
                    rcConsumption = true;
                } else {
                    rcConsumption = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    rcConsumpCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(rcConsumpCBBind);
                }
            } else {
                rcConsumption = false;
                rcConsumpLinkBind.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(rcConsumpLinkBind);
            }
        }
    }

    /*  public void setIssuedQtyCBBind(RichSelectBooleanCheckbox issuedQtyCBBind) {
        this.issuedQtyCBBind = issuedQtyCBBind;
    }

    public RichSelectBooleanCheckbox getIssuedQtyCBBind() {
        return issuedQtyCBBind;
    }

    public void issuedQtyCBVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    } */

    public void rcMRSCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(rcDocIdPgBind)) {
                    rcMRS = true;
                } else {
                    rcMRS = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    rcMRSCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(rcMRSCBBind);
                }
            } else {
                rcMRS = false;
                rcMRSLinkBind.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(rcMRSLinkBind);
            }
        }
    }

    public void setRcMRSCBBind(RichSelectBooleanCheckbox rcMRSCBBind) {
        this.rcMRSCBBind = rcMRSCBBind;
    }

    public RichSelectBooleanCheckbox getRcMRSCBBind() {
        return rcMRSCBBind;
    }

    public void rcIssuedQtyCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(rcDocIdPgBind)) {
                    rcIssuedQty = true;
                } else {
                    rcIssuedQty = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    rcIssuedQtyCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(rcIssuedQtyCBBind);
                }
            } else {
                rcIssuedQty = false;
                rcIssuedQtyLinkBind.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(rcIssuedQtyLinkBind);
            }
        }
    }

    public void setRcIssuedQtyCBBind(RichSelectBooleanCheckbox rcIssuedQtyCBBind) {
        this.rcIssuedQtyCBBind = rcIssuedQtyCBBind;
    }

    public RichSelectBooleanCheckbox getRcIssuedQtyCBBind() {
        return rcIssuedQtyCBBind;
    }

    public void setRcMRSLinkBind(RichLink rcMRSLinkBind) {
        this.rcMRSLinkBind = rcMRSLinkBind;
    }

    public RichLink getRcMRSLinkBind() {
        return rcMRSLinkBind;
    }

    public void setRcIssuedQtyLinkBind(RichLink rcIssuedQtyLinkBind) {
        this.rcIssuedQtyLinkBind = rcIssuedQtyLinkBind;
    }

    public RichLink getRcIssuedQtyLinkBind() {
        return rcIssuedQtyLinkBind;
    }

    public void setRcConsumpLinkBind(RichLink rcConsumpLinkBind) {
        this.rcConsumpLinkBind = rcConsumpLinkBind;
    }

    public RichLink getRcConsumpLinkBind() {
        return rcConsumpLinkBind;
    }

    public void WSDetCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                /*    if (!checkBoxValidation()) {
                    wsDet = true;
                } else {
                    wsDet = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    wsDetCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(wsDetCBBind);
                } */
                wsDet = true;
            } else {
                wsDet = false;
//                wsDetLinkBind.setVisible(false);
//                AdfFacesContext.getCurrentInstance().addPartialTarget(wsDetLinkBind);
            }
        }
    }

    public void setWsDetCBBind(RichSelectBooleanCheckbox wsDetCBBind) {
        this.wsDetCBBind = wsDetCBBind;
    }

    public RichSelectBooleanCheckbox getWsDetCBBind() {
        return wsDetCBBind;
    }

    public void WSDowntimeCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation()) {
                    machDowntime = true;
                } else {
                    machDowntime = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    wsDowntimeCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(wsDowntimeCBBind);
                }
            } else {
                machDowntime = false;
//                wsDowntimeLinkBind.setVisible(false);
//                AdfFacesContext.getCurrentInstance().addPartialTarget(wsDowntimeLinkBind);
            }
        }
    }

    public void setWsDowntimeCBBind(RichSelectBooleanCheckbox wsDowntimeCBBind) {
        this.wsDowntimeCBBind = wsDowntimeCBBind;
    }

    public RichSelectBooleanCheckbox getWsDowntimeCBBind() {
        return wsDowntimeCBBind;
    }

    public void WSPlanCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation()) {
                    machPlan = true;
                } else {
                    machPlan = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    wsPlanCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(wsPlanCBBind);
                }
            } else {
                machPlan = false;
//                wsPlanLinkBind.setVisible(false);
//                AdfFacesContext.getCurrentInstance().addPartialTarget(wsPlanLinkBind);
            }
        }
    }

    public void setWsPlanCBBind(RichSelectBooleanCheckbox wsPlanCBBind) {
        this.wsPlanCBBind = wsPlanCBBind;
    }

    public RichSelectBooleanCheckbox getWsPlanCBBind() {
        return wsPlanCBBind;
    }

    /*   public void setWsNoPlanCBVCL(RichSelectBooleanCheckbox wsNoPlanCBVCL) {
        this.wsNoPlanCBVCL = wsNoPlanCBVCL;
    }

    public RichSelectBooleanCheckbox getWsNoPlanCBVCL() {
        return wsNoPlanCBVCL;
    } */

    public void setWsNoPlanCBBind(RichSelectBooleanCheckbox wsNoPlanCBBind) {
        this.wsNoPlanCBBind = wsNoPlanCBBind;
    }

    public RichSelectBooleanCheckbox getWsNoPlanCBBind() {
        return wsNoPlanCBBind;
    }

    public void WSNoPlanCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation()) {
                    machNoPlan = true;
                } else {
                    machNoPlan = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    wsNoPlanCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(wsNoPlanCBBind);
                }
            } else {
                machNoPlan = false;
//                wsNoPlanLinkBind.setVisible(false);
//                AdfFacesContext.getCurrentInstance().addPartialTarget(wsNoPlanLinkBind);
            }
        }
    }

    public void setListOfShiftCBBind(RichSelectBooleanCheckbox listOfShiftCBBind) {
        this.listOfShiftCBBind = listOfShiftCBBind;
    }

    public RichSelectBooleanCheckbox getListOfShiftCBBind() {
        return listOfShiftCBBind;
    }

    public void listOfShiftCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                /*      if (!checkBoxValidation()) {
                    shiftList = true;
                } else {
                    shiftList = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    listOfShiftCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(listOfShiftCBBind);
                } */
                shiftList = true;
            } else {
                shiftList = false;
//                listOfShiftLinkBind.setVisible(false);
//                AdfFacesContext.getCurrentInstance().addPartialTarget(listOfShiftLinkBind);
            }
        }
    }

    public void setWsDetLinkBind(RichLink wsDetLinkBind) {
        this.wsDetLinkBind = wsDetLinkBind;
    }

    public RichLink getWsDetLinkBind() {
        return wsDetLinkBind;
    }

    public void setWsDowntimeLinkBind(RichLink wsDowntimeLinkBind) {
        this.wsDowntimeLinkBind = wsDowntimeLinkBind;
    }

    public RichLink getWsDowntimeLinkBind() {
        return wsDowntimeLinkBind;
    }

    public void setWsPlanLinkBind(RichLink wsPlanLinkBind) {
        this.wsPlanLinkBind = wsPlanLinkBind;
    }

    public RichLink getWsPlanLinkBind() {
        return wsPlanLinkBind;
    }

    public void setWsNoPlanLinkBind(RichLink wsNoPlanLinkBind) {
        this.wsNoPlanLinkBind = wsNoPlanLinkBind;
    }

    public RichLink getWsNoPlanLinkBind() {
        return wsNoPlanLinkBind;
    }

    public void setListOfShiftLinkBind(RichLink listOfShiftLinkBind) {
        this.listOfShiftLinkBind = listOfShiftLinkBind;
    }

    public RichLink getListOfShiftLinkBind() {
        return listOfShiftLinkBind;
    }

    public void setJcMRSLinkBind(RichLink jcMRSLinkBind) {
        this.jcMRSLinkBind = jcMRSLinkBind;
    }

    public RichLink getJcMRSLinkBind() {
        return jcMRSLinkBind;
    }

    public void RCRejectionDetCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(rcDocIdPgBind)) {
                    rcRejectionDet = true;
                } else {
                    rcRejectionDet = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    rcRejectionDetCBBinding.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(rcRejectionDetCBBinding);
                }
            } else {
                rcRejectionDet = false;
                rcRejectionDetLinkBind.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(rcRejectionDetLinkBind);
            }
        }
    }

    public void setRcRejectionDetCBBinding(RichSelectBooleanCheckbox rcRejectionDetCBBinding) {
        this.rcRejectionDetCBBinding = rcRejectionDetCBBinding;
    }

    public RichSelectBooleanCheckbox getRcRejectionDetCBBinding() {
        return rcRejectionDetCBBinding;
    }

    public void setRcRejectionDetLinkBind(RichLink rcRejectionDetLinkBind) {
        this.rcRejectionDetLinkBind = rcRejectionDetLinkBind;
    }

    public RichLink getRcRejectionDetLinkBind() {
        return rcRejectionDetLinkBind;
    }

    public void setWsUtilizationLinkBind(RichLink wsUtilizationLinkBind) {
        this.wsUtilizationLinkBind = wsUtilizationLinkBind;
    }

    public RichLink getWsUtilizationLinkBind() {
        return wsUtilizationLinkBind;
    }

    public void setWsUtilizationItemLinkBind(RichLink wsUtilizationItemLinkBind) {
        this.wsUtilizationItemLinkBind = wsUtilizationItemLinkBind;
    }

    public RichLink getWsUtilizationItemLinkBind() {
        return wsUtilizationItemLinkBind;
    }

    public void WSUtilItemCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation()) {
                    wsItmUtilization = true;
                } else {
                    wsItmUtilization = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    wsUtilItemCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(wsUtilItemCBBind);
                }
            } else {
                wsItmUtilization = false;
//                wsUtilizationItemLinkBind.setVisible(false);
//                AdfFacesContext.getCurrentInstance().addPartialTarget(wsUtilizationItemLinkBind);
            }
        }
    }

    public void setWsUtilItemCBBind(RichSelectBooleanCheckbox wsUtilItemCBBind) {
        this.wsUtilItemCBBind = wsUtilItemCBBind;
    }

    public RichSelectBooleanCheckbox getWsUtilItemCBBind() {
        return wsUtilItemCBBind;
    }

    public void setWsUtilCBBind(RichSelectBooleanCheckbox wsUtilCBBind) {
        this.wsUtilCBBind = wsUtilCBBind;
    }

    public RichSelectBooleanCheckbox getWsUtilCBBind() {
        return wsUtilCBBind;
    }

    public void WSUtilCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation()) {
                    wsUtilization = true;
                } else {
                    wsUtilization = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    wsUtilCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(wsUtilCBBind);
                }
            } else {
                wsUtilization = false;
//                wsUtilizationLinkBind.setVisible(false);
//                AdfFacesContext.getCurrentInstance().addPartialTarget(wsUtilizationLinkBind);
            }
        }
    }

    public void planVsActRCCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(rcDocIdPgBind)) {
                    prodMoveRC = true;
                } else {
                    prodMoveRC = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    prodMoveRCCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(prodMoveRCCBBind);
                }
            } else {
                prodMoveRC = false;
                prodMoveRCLinkBind.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(prodMoveRCLinkBind);
            }
        }
    }

    public void setPlanVsActRCCBBind(RichSelectBooleanCheckbox planVsActRCCBBind) {
        this.planVsActRCCBBind = planVsActRCCBBind;
    }

    public RichSelectBooleanCheckbox getPlanVsActRCCBBind() {
        return planVsActRCCBBind;
    }

    public void ProdMoveRCCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(rcDocIdPgBind)) {
                    planActualRC = true;
                } else {
                    planActualRC = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    planVsActRCCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(planVsActRCCBBind);
                }
            } else {
                planActualRC = false;
                planVsActRCLinkBind.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(planVsActRCLinkBind);
            }
        }
    }

    public void setProdMoveRCCBBind(RichSelectBooleanCheckbox prodMoveRCCBBind) {
        this.prodMoveRCCBBind = prodMoveRCCBBind;
    }

    public RichSelectBooleanCheckbox getProdMoveRCCBBind() {
        return prodMoveRCCBBind;
    }

    public void setPlanVsActRCLinkBind(RichLink planVsActRCLinkBind) {
        this.planVsActRCLinkBind = planVsActRCLinkBind;
    }

    public RichLink getPlanVsActRCLinkBind() {
        return planVsActRCLinkBind;
    }

    public void setProdMoveRCLinkBind(RichLink prodMoveRCLinkBind) {
        this.prodMoveRCLinkBind = prodMoveRCLinkBind;
    }

    public RichLink getProdMoveRCLinkBind() {
        return prodMoveRCLinkBind;
    }

    public void setMsgInfoPgBind(RichMessage msgInfoPgBind) {
        this.msgInfoPgBind = msgInfoPgBind;
    }

    public RichMessage getMsgInfoPgBind() {
        return msgInfoPgBind;
    }

    public void DownRptCBPgVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
                   if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                       if (rptNmLovPgBind.getValue() != null && rptNmLovPgBind.getValue().toString().length() > 0) {
                           System.out.println("inside true");
                           dwonRptLinkPgBind.setVisible(true);
                          // AdfFacesContext.getCurrentInstance().addPartialTarget(dwonRptLinkPgBind);
                       } else {
                           System.out.println("Inside false");
                           downRptCBPgBind.setValue(false);
                           AdfFacesContext.getCurrentInstance().addPartialTarget(downRptCBPgBind);
                           FacesMessage message = new FacesMessage("Please Select Report Name..!!!");
                           message.setSeverity(FacesMessage.SEVERITY_ERROR);
                           FacesContext fc = FacesContext.getCurrentInstance();
                           fc.addMessage(rptNmLovPgBind.getClientId(), message);
                       }
                   } else {
                       downRptCBPgBind.setValue(false);
                       //downRpt = false;
                       dwonRptLinkPgBind.setVisible(false);
                   }
               } else {
                   downRptCBPgBind.setValue(false);
                   dwonRptLinkPgBind.setVisible(false);
               }
    }

    public void setDownRptCBPgBind(RichSelectBooleanCheckbox downRptCBPgBind) {
        this.downRptCBPgBind = downRptCBPgBind;
    }

    public RichSelectBooleanCheckbox getDownRptCBPgBind() {
        return downRptCBPgBind;
    }

    public void setOrgNamCBPgBind(RichSelectBooleanCheckbox orgNamCBPgBind) {
        this.orgNamCBPgBind = orgNamCBPgBind;
    }

    public RichSelectBooleanCheckbox getOrgNamCBPgBind() {
        return orgNamCBPgBind;
    }

    public void orgNmCVPgVCL(ValueChangeEvent valueChangeEvent) {
       dwonRptLinkPgBind.setVisible(false);
       AdfFacesContext.getCurrentInstance().addPartialTarget(dwonRptLinkPgBind);
      }

    public void setRptNmLovPgBind(RichInputListOfValues rptNmLovPgBind) {
        this.rptNmLovPgBind = rptNmLovPgBind;
    }

    public RichInputListOfValues getRptNmLovPgBind() {
        return rptNmLovPgBind;
    }

    public void RptNmLovPgVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
                   System.out.println("Inside valueChangeEvent");
                   downRptCBPgBind.setValue(false);
                   AdfFacesContext.getCurrentInstance().addPartialTarget(downRptCBPgBind);
                   AdfFacesContext.getCurrentInstance().addPartialTarget(msgInfoPgBind);
               }
    }

    public void setDwonRptLinkPgBind(RichLink dwonRptLinkPgBind) {
        this.dwonRptLinkPgBind = dwonRptLinkPgBind;
    }

    public RichLink getDwonRptLinkPgBind() {
        return dwonRptLinkPgBind;
    }

    public void QCTestCertCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
                   if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                       if (qcNOLovPgBind.getValue() != null && qcNOLovPgBind.getValue().toString().length() > 0) {
                           System.out.println("inside true");
                           QCTestCert = true;
                       } else {
                           System.out.println("Inside false");
                           qcTestCertCBBind.setValue(false);
                           AdfFacesContext.getCurrentInstance().addPartialTarget(qcTestCertCBBind);
                           FacesMessage message = new FacesMessage("Please Select QC No.");
                           message.setSeverity(FacesMessage.SEVERITY_ERROR);
                           FacesContext fc = FacesContext.getCurrentInstance();
                           fc.addMessage(qcNOLovPgBind.getClientId(), message);
                       }
                   } else {
                       qcTestCertCBBind.setValue(false);
                       AdfFacesContext.getCurrentInstance().addPartialTarget(qcTestCertCBBind);
                       QCTestCert = false;
                   }
               } else {
                   qcTestCertCBBind.setValue(false);
                   AdfFacesContext.getCurrentInstance().addPartialTarget(qcTestCertCBBind);
                   QCTestCert = false;
               }       
    }

    public void setQcTestCertLinkBind(RichLink qcTestCertLinkBind) {
        this.qcTestCertLinkBind = qcTestCertLinkBind;
    }

    public RichLink getQcTestCertLinkBind() {
        return qcTestCertLinkBind;
    }

    public void setQcNOLovPgBind(RichInputListOfValues qcNOLovPgBind) {
        this.qcNOLovPgBind = qcNOLovPgBind;
    }

    public RichInputListOfValues getQcNOLovPgBind() {
        return qcNOLovPgBind;
    }

    public void setQcTestCertCBBind(RichSelectBooleanCheckbox qcTestCertCBBind) {
        this.qcTestCertCBBind = qcTestCertCBBind;
    }

    public RichSelectBooleanCheckbox getQcTestCertCBBind() {
        return qcTestCertCBBind;
    }

    public void GPPCBVCL(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(gppDocIdPgBind)) {
                    gpp = true;
                } else {
                    gpp = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    gpPCBPgBinding.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(gpPCBPgBinding);
                }
            } else {
                gpp = false;
           //    gpPpgLinkBiind.setVisible(false);
             //   AdfFacesContext.getCurrentInstance().addPartialTarget(gpPpgLinkBiind);
            }
        }
    }

    public void setGpPCBPgBinding(RichSelectBooleanCheckbox gpPCBPgBinding) {
        this.gpPCBPgBinding = gpPCBPgBinding;
    }

    public RichSelectBooleanCheckbox getGpPCBPgBinding() {
        return gpPCBPgBinding;
    }

    public void setGppLovPgBind(RichInputListOfValues gppLovPgBind) {
        this.gppLovPgBind = gppLovPgBind;
    }

    public RichInputListOfValues getGppLovPgBind() {
        return gppLovPgBind;
    }

    public void GppIdPgVCL(ValueChangeEvent valueChangeEvent) {
        SetDateFieldsToNull(valueChangeEvent);
        if(valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0){
            
        }else{
            gpPCBPgBinding.setValue(false);   
            gpp = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(gpPCBPgBinding);
        }
    }

    public void setGpPpgLinkBiind(RichLink gpPpgLinkBiind) {
        this.gpPpgLinkBiind = gpPpgLinkBiind;
    }

    public RichLink getGpPpgLinkBiind() {
        return gpPpgLinkBiind;
    }

    public void setGppDocIdPgBind(RichOutputText gppDocIdPgBind) {
        this.gppDocIdPgBind = gppDocIdPgBind;
    }

    public RichOutputText getGppDocIdPgBind() {
        return gppDocIdPgBind;
    }

    public void BOMHieraCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (!checkBoxValidation() || chkLovSelectedOrNot(bomDocIdPgBind)) {
                    bomHierarchy = true;
                } else {
                    bomHierarchy = false;
                    DisplayDateErrorOnDateConditionally();
                    //set chaeckbox  false
                    boMHieraCBBinding.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(boMHieraCBBinding);
                }
            } else {
                bomHierarchy = false;
             }
        }
    }

    public void setBoMHieraCBBinding(RichSelectBooleanCheckbox boMHieraCBBinding) {
        this.boMHieraCBBinding = boMHieraCBBinding;
    }

    public RichSelectBooleanCheckbox getBoMHieraCBBinding() {
        return boMHieraCBBinding;
    }

    public void setBomHierarchyLinkBinding(RichLink bomHierarchyLinkBinding) {
        this.bomHierarchyLinkBinding = bomHierarchyLinkBinding;
    }

    public RichLink getBomHierarchyLinkBinding() {
        return bomHierarchyLinkBinding;
    }

    public void QCNoLOVVCL(ValueChangeEvent vce) {
        SetDateFieldsToNull(vce);
    }

    public void setProdFcCBBinding(RichSelectBooleanCheckbox prodFcCBBinding) {
        this.prodFcCBBinding = prodFcCBBinding;
    }

    public RichSelectBooleanCheckbox getProdFcCBBinding() {
        return prodFcCBBinding;
    }

    public void setProdFcLinkBinding(RichLink prodFcLinkBinding) {
        this.prodFcLinkBinding = prodFcLinkBinding;
    }

    public RichLink getProdFcLinkBinding() {
        return prodFcLinkBinding;
    }
    public Map getUsrRptVisible() {
    return new HashMap<Integer, Boolean>() {
        @Override
        public Boolean get(Object key) {
            if (key != null) {
                Boolean retVal = false;
                OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkUsrRptSecAccess");
                opChk.getParamsMap().put("rptId", key);
                opChk.execute();
                if (opChk.getResult() != null)
                    retVal = (Boolean) opChk.getResult();
                return retVal;
            } else
                return true;
        }
    };
    }
}
