package inventoryreportapp.view.bean;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import java.util.Date;

import java.sql.Statement;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;

import javax.el.ELContext;

import javax.el.ValueExpression;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import javax.naming.Context;

import javax.naming.InitialContext;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichMessage;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.domain.Timestamp;
import oracle.jbo.domain.Date;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;

import org.apache.myfaces.trinidad.event.DisclosureEvent;

public class InventoryRptBean {
    private RichSelectOneRadio fileTypBinding;
    private RichSelectBooleanCheckbox rcptRegCBBind;
    private RichSelectBooleanCheckbox rcptNoWiseCBBind;
    private RichSelectBooleanCheckbox rcptRegItmCBBind;
    private RichInputListOfValues stkAdjLovBinding;
    private RichSelectBooleanCheckbox stkAdjCBBinding;
    private RichSelectBooleanCheckbox prodAgLotCBBindig;
    private RichInputText range1Binding;
    private RichInputText range2Binding;
    private RichInputText range3Binding;
    private RichInputText range4Binding;
    private RichInputText range5Binding;
    private RichSelectBooleanCheckbox stkResvCBBinding;
    private RichSelectBooleanCheckbox stkResvItmWsCBBind;
    private RichButton saveBtnBinding;
    private RichInputListOfValues qcnoLOVPgBind;
    private RichSelectBooleanCheckbox testCertCBBind;
    private RichSelectBooleanCheckbox stkAdjSummCBBind;
    private RichSelectBooleanCheckbox issuRegReqWsCBBind;
    private RichSelectBooleanCheckbox issuRegItmWsCbBind;
    private RichSelectBooleanCheckbox purInvcItemCBBind;
    private RichSelectBooleanCheckbox purInvcSuppCBBind;
    private RichSelectBooleanCheckbox purInvcDetCBBind;
    private RichSelectBooleanCheckbox toTrackCBBind;
    private RichSelectBooleanCheckbox purReqTrackCBBind;

    public void setPurInvcRegLink(Boolean purInvcRegLink) {
        this.purInvcRegLink = purInvcRegLink;
    }

    public Boolean getPurInvcRegLink() {
        return purInvcRegLink;
    }
    private RichSelectBooleanCheckbox geCBBind;
    private RichSelectBooleanCheckbox rcptRegPrtyCBBind;

    private Boolean RcptRegLink = false;
    private Boolean RcptNoWiseLink = false;
    private Boolean RcptRegItmLink = false;
    private Boolean GELink = false;
    private Boolean RcptRegPrtyLink = false;
    private RichSelectBooleanCheckbox issueNoWiseCBBind;
    private RichSelectBooleanCheckbox consumRegCBBind;
    private RichSelectBooleanCheckbox gpCBBind;
    private RichSelectBooleanCheckbox gpSummCBBind;
    private RichSelectBooleanCheckbox gbRetOutCBBind;
    private RichSelectBooleanCheckbox mrNCBBind;
    private RichSelectBooleanCheckbox prSuppWiseCBBind;
    private RichSelectBooleanCheckbox prRetItmCBBind;
    private RichSelectBooleanCheckbox rmDACBBind;
    private RichSelectBooleanCheckbox stkRegCBBind;
    private RichSelectBooleanCheckbox itmWiseLedgCBBind;
    private RichSelectBooleanCheckbox serialWiseCBBind;
    private RichSelectBooleanCheckbox currStkSummCBBind;
    private RichSelectBooleanCheckbox itmMovDtCBBind;
    private RichSelectBooleanCheckbox binWiseCBBind;
    private RichSelectBooleanCheckbox lotWiseCBBind;
    private RichSelectBooleanCheckbox stkTkCBBind;
    private RichSelectBooleanCheckbox stkTkDtCBBind;
    private RichSelectBooleanCheckbox stkTkSummCBBind;
    private RichSelectBooleanCheckbox kitWrkShopCBBind;
    private RichSelectBooleanCheckbox indentCBBind;
    private RichSelectBooleanCheckbox mrSCBBind;
    private RichSelectBooleanCheckbox purReqCBBind;
    private RichSelectBooleanCheckbox qcCBBind;
    private RichSelectBooleanCheckbox trnsferOrdCBBind;
    private RichSelectBooleanCheckbox stkTrnsferInvCBBind;
    private RichSelectBooleanCheckbox purInvCBBind;


    private Boolean issueNoWiseLink = false;
    private Boolean consumRegLink = false;
    private Boolean gpLink = false;
    private Boolean gpSummLink = false;
    private Boolean gbRetOutLink = false;
    private Boolean mrNLink = false;
    private Boolean prSuppWiseLink = false;
    private Boolean prRetItmLink = false;
    private Boolean rmDALink = false;
    private Boolean stkRegLink = false;
    private Boolean itmWiseLedgLink = false;
    private Boolean serialWiseLink = false;
    private Boolean currStkSummLink = false;
    private Boolean itmMovDtLink = false;
    private Boolean binWiseLink = false;
    private Boolean lotWiseLink = false;
    private Boolean stkTkLink = false;
    private Boolean stkTkDtLink = false;
    private Boolean stkTkSummLink = false;
    private Boolean kitWrkShopLink = false;
    private Boolean indentLink = false;
    private Boolean mrSLink = false;
    private Boolean purReqLink = false;
    private Boolean TestCertLink = false;
    private Boolean stkAdjSummLink = false;
    private Boolean purInvcItemLink = false;
    private Boolean purInvcSuppLink = false;
    private Boolean purInvcDetLink = false;
    private boolean toTrackLink = false;
    private boolean purReqTrackLink = false;

    public void setPurReqTrackLink(boolean purReqTrackLink) {
        this.purReqTrackLink = purReqTrackLink;
    }

    public boolean isPurReqTrackLink() {
        return purReqTrackLink;
    }

    public void setToTrackLink(boolean toTrackLink) {
        this.toTrackLink = toTrackLink;
    }

    public boolean isToTrackLink() {
        return toTrackLink;
    }

    public void setPurInvcDetLink(Boolean purInvcDetLink) {
        this.purInvcDetLink = purInvcDetLink;
    }

    public Boolean getPurInvcDetLink() {
        return purInvcDetLink;
    }

    public void setPurInvcItemLink(Boolean purInvcItemLink) {
        this.purInvcItemLink = purInvcItemLink;
    }

    public Boolean getPurInvcItemLink() {
        return purInvcItemLink;
    }

    public void setPurInvcSuppLink(Boolean purInvcSuppLink) {
        this.purInvcSuppLink = purInvcSuppLink;
    }

    public Boolean getPurInvcSuppLink() {
        return purInvcSuppLink;
    }

    public void setStkAdjSummLink(Boolean stkAdjSummLink) {
        this.stkAdjSummLink = stkAdjSummLink;
    }

    public Boolean getStkAdjSummLink() {
        return stkAdjSummLink;
    }

    public void setTestCertLink(Boolean TestCertLink) {
        this.TestCertLink = TestCertLink;
    }

    public Boolean getTestCertLink() {
        return TestCertLink;
    }


    private Boolean qcLink = false;
    private Boolean trnsferOrdLink = false;
    private Boolean stkTrnsferInvLink = false;
    private Boolean purInvLink = false;
    private RichSelectBooleanCheckbox downCBBinding;
    private RichInputListOfValues downLOVBindind;
    private RichLink rptNmLnkBinding;
    private RichSelectBooleanCheckbox orgViewCBBinding;
    private RichMessage msgBinding;
    private Boolean geWorkJobLink = false;
    private Boolean stockAgeingLink = false;
    private Boolean purInvcRegLink = false;
    private boolean stkAdjLink = false;
    private boolean prodAgLotLink = false;
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    CallableStatement cstmt = null;
    private Integer range1 = 0;
    private Integer range2 = 0;
    private Integer range3 = 0;
    private Integer range4 = 0;
    private Integer range5 = 0;
    private boolean stkResvLink = false;
    private boolean stkResvItmWsLink = false;
    private boolean issuRegItmWsLink = false;
    private boolean issuRegReqWsLink = false;

    public void setIssuRegItmWsLink(boolean issuRegItmWsLink) {
        this.issuRegItmWsLink = issuRegItmWsLink;
    }

    public boolean isIssuRegItmWsLink() {
        return issuRegItmWsLink;
    }

    public void setIssuRegReqWsLink(boolean issuRegReqWsLink) {
        this.issuRegReqWsLink = issuRegReqWsLink;
    }

    public boolean isIssuRegReqWsLink() {
        return issuRegReqWsLink;
    }

    public void setRange2(Integer range2) {
        this.range2 = range2;
    }

    public Integer getRange2() {
        return range2;
    }

    public void setRange3(Integer range3) {
        this.range3 = range3;
    }

    public Integer getRange3() {
        return range3;
    }

    public void setRange4(Integer range4) {
        this.range4 = range4;
    }

    public Integer getRange4() {
        return range4;
    }

    public void setRange5(Integer range5) {
        this.range5 = range5;
    }

    public Integer getRange5() {
        return range5;
    }


    public void setRange1(Integer range1) {
        this.range1 = range1;
    }

    public Integer getRange1() {
        return range1;
    }

    public void setProdAgLotLink(boolean prodAgLotLink) {
        this.prodAgLotLink = prodAgLotLink;
    }

    public boolean isProdAgLotLink() {
        return prodAgLotLink;
    }

    public void setStkAdjLink(boolean stkAdjLink) {
        this.stkAdjLink = stkAdjLink;
    }

    public boolean isStkAdjLink() {
        return stkAdjLink;
    }


    private RichSelectBooleanCheckbox geWorkJobCBBinding;
    private RichSelectBooleanCheckbox stockAgeingCBBinding;
    private RichInputListOfValues rcptNoLovPgBinding;
    private RichInputListOfValues qcRcptNoLovPgBinding;
    private RichInputDate fromDatePgBinding;
    private RichInputDate toDatePgBinding;
    private RichInputListOfValues issueNoLovPgBinding;
    private RichInputListOfValues gatePassNoLovPgBinding;
    private RichInputListOfValues mrNLovPgBinding;
    private RichInputListOfValues purRetrnLovPgBinding;
    private RichInputListOfValues rmDALovPgBinding;
    private RichInputListOfValues stockTakeNoLovPgBinding;
    private RichInputListOfValues kitLovPgBinding;
    private RichInputListOfValues mrSLovPgBinding;
    private RichInputListOfValues purReqLovPgBinding;
    private RichInputListOfValues transfOrdLovPgBinding;
    private RichInputListOfValues invoiceNoLovPgBinding;
    private String dateClientId;
    private RichInputListOfValues itmLovPgBinding;
    private RichInputListOfValues suppLovPgBinding;
    private RichSelectBooleanCheckbox purInvcRegCBBinding;

    public Date getCurrDateDt() {
        Date dt = (Date) new Date().getCurrentDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt.dateValue());
        cal.add(Calendar.DATE, 1);
        java.util.Date dtt = cal.getTime();
        String dtStr = dateFormat.format(dtt);
        oracle.jbo.domain.Date daTime = null;
        try {
            java.util.Date date2 = dateFormat.parse(dtStr);
            java.sql.Date sqldate = new java.sql.Date(date2.getTime());
            daTime = new oracle.jbo.domain.Date(sqldate);
            System.out.println("daTime " + daTime);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return daTime;
    }

    public void setGeWorkJobLink(Boolean geWorkJobLink) {
        this.geWorkJobLink = geWorkJobLink;
    }

    public Boolean getGeWorkJobLink() {
        return geWorkJobLink;
    }

    public void setStockAgeingLink(Boolean stockAgeingLink) {
        this.stockAgeingLink = stockAgeingLink;
    }

    public Boolean getStockAgeingLink() {
        return stockAgeingLink;
    }


    public InventoryRptBean() {
        this.fetchData();
        System.out.println("call fetch");
    }

    public void generateRptAction(ActionEvent actionEvent) {
        if (rcptRegCBBind.isSelected()) {
            RcptRegLink = true;
        }
        if (rcptNoWiseCBBind.isSelected()) {
            RcptNoWiseLink = true;
        }
        if (rcptRegItmCBBind.isSelected()) {
            RcptRegItmLink = true;
        }
        if (geCBBind.isSelected()) {
            GELink = true;
        }
        if (rcptRegPrtyCBBind.isSelected()) {
            RcptRegPrtyLink = true;
        }
        if (issueNoWiseCBBind.isSelected()) {
            issueNoWiseLink = true;
        }
        if (consumRegCBBind.isSelected()) {
            consumRegLink = true;
        }
        if (gpCBBind.isSelected()) {
            setGpLink(true);
        }
        if (gpSummCBBind.isSelected()) {
            setGpSummLink(true);
        }
        if (gbRetOutCBBind.isSelected()) {
            setGbRetOutLink(true);
        }
        if (mrNCBBind.isSelected()) {
            setMrNLink(true);
        }
        if (prSuppWiseCBBind.isSelected()) {
            setPrSuppWiseLink(true);
        }
        if (prRetItmCBBind.isSelected()) {
            setPrRetItmLink(true);
        }
        if (rmDACBBind.isSelected()) {
            setRmDALink(true);
        }
        if (stkRegCBBind.isSelected()) {
            setStkRegLink(true);
        }
        if (itmWiseLedgCBBind.isSelected()) {
            setItmWiseLedgLink(true);
        }
        if (serialWiseCBBind.isSelected()) {
            setSerialWiseLink(true);
        }
        if (currStkSummCBBind.isSelected()) {
            setCurrStkSummLink(true);
        }
        if (itmMovDtCBBind.isSelected()) {
            setItmMovDtLink(true);
        }
        if (binWiseCBBind.isSelected()) {
            setBinWiseLink(true);
        }
        if (lotWiseCBBind.isSelected()) {
            setLotWiseLink(true);
        }
        if (stkTkCBBind.isSelected()) {
            setStkTkLink(true);
        }
        if (stkTkDtCBBind.isSelected()) {
            setStkTkDtLink(true);
        }
        if (stkTkSummCBBind.isSelected()) {
            setStkTkSummLink(true);
        }
        if (kitWrkShopCBBind.isSelected()) {
            setKitWrkShopLink(true);
        }
        if (indentCBBind.isSelected()) {
            setIndentLink(true);
        }
        if (mrSCBBind.isSelected()) {
            setMrSLink(true);
        }
        if (purReqCBBind.isSelected()) {
            setPurReqLink(true);
        }
        if (qcCBBind.isSelected()) {
            setQcLink(true);
        }
        if (trnsferOrdCBBind.isSelected()) {
            setTrnsferOrdLink(true);
        }
        if (stkTrnsferInvCBBind.isSelected()) {
            setStkTrnsferInvLink(true);
        }
        if (purInvCBBind.isSelected()) {
            setPurInvLink(true);
        }
        if (downCBBinding.getValue().equals(true)) {
            rptNmLnkBinding.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rptNmLnkBinding);
        } else {
            rptNmLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rptNmLnkBinding);
        }
        if (geWorkJobCBBinding.isSelected()) {
            setGeWorkJobLink(true);
        } else
            setGeWorkJobLink(false);
        if (stockAgeingCBBinding.isSelected()) {
            setStockAgeingLink(true);
        } else
            setStockAgeingLink(false);
        if (purInvcRegCBBinding.isSelected()) {
            purInvcRegLink = true;
        } else
            purInvcRegLink = false;
        if (stkAdjCBBinding.isSelected()) {
            stkAdjLink = true;
        } else
            stkAdjLink = false;
        if (prodAgLotCBBindig.isSelected()) {
            prodAgLotLink = true;
        } else
            prodAgLotLink = false;
        if (stkResvCBBinding.isSelected()) {
            stkResvLink = true;
        } else
            stkResvLink = false;
        if (stkResvItmWsCBBind.isSelected()) {
            stkResvItmWsLink = true;
        } else
            stkResvItmWsLink = false;
        if (testCertCBBind.isSelected()) {
            TestCertLink = true;
        } else
            TestCertLink = false;
        if (stkAdjSummCBBind.isSelected()) {
            stkAdjSummLink = true;
        } else
            stkAdjSummLink = false;
        if(issuRegItmWsCbBind.isSelected()){
            issuRegItmWsLink = true;
        }else issuRegItmWsLink = false;
        if (issuRegReqWsCBBind.isSelected()){
            issuRegReqWsLink = true;
        }else issuRegReqWsLink = false;
        
        if(purInvcItemCBBind.isSelected()){
            this.setPurInvcItemLink(true);
        }else  this.setPurInvcItemLink(false);
        
        if(purInvcSuppCBBind.isSelected()){
            this.setPurInvcSuppLink(true);
        }else this.setPurInvcSuppLink(false);
        
        if(purInvcDetCBBind.isSelected()){
            this.setPurInvcDetLink(true);
        }else this.setPurInvcDetLink(false);
        
        if(toTrackCBBind.isSelected()){
            this.setToTrackLink(true);
        }else this.setToTrackLink(false);
        
        if(purReqTrackCBBind.isSelected()){
            System.out.println("value inside selected");
           this.setPurReqTrackLink(true);
        }else this.setPurReqTrackLink(false);
        
    }

    public void setStkResvLink(boolean stkResvLink) {
        this.stkResvLink = stkResvLink;
    }

    public boolean isStkResvLink() {
        return stkResvLink;
    }

    public void setStkResvItmWsLink(boolean stkResvItmWsLink) {
        this.stkResvItmWsLink = stkResvItmWsLink;
    }

    public boolean isStkResvItmWsLink() {
        return stkResvItmWsLink;
    }

    public void setRcptRegLink(Boolean RcptRegLink) {
        this.RcptRegLink = RcptRegLink;
    }

    public Boolean getRcptRegLink() {
        return RcptRegLink;
    }

    public void setRcptNoWiseLink(Boolean RcptNoWiseLink) {
        this.RcptNoWiseLink = RcptNoWiseLink;
    }

    public Boolean getRcptNoWiseLink() {
        return RcptNoWiseLink;
    }

    public void setRcptRegItmLink(Boolean RcptRegItmLink) {
        this.RcptRegItmLink = RcptRegItmLink;
    }

    public Boolean getRcptRegItmLink() {
        return RcptRegItmLink;
    }

    public void setGELink(Boolean GELink) {
        this.GELink = GELink;
    }

    public Boolean getGELink() {
        return GELink;
    }

    public void setRcptRegPrtyLink(Boolean RcptRegPrtyLink) {
        this.RcptRegPrtyLink = RcptRegPrtyLink;
    }

    public Boolean getRcptRegPrtyLink() {
        return RcptRegPrtyLink;
    }

    public void setIssueNoWiseLink(Boolean issueNoWiseLink) {
        this.issueNoWiseLink = issueNoWiseLink;
    }

    public Boolean getIssueNoWiseLink() {
        return issueNoWiseLink;
    }

    public void setConsumRegLink(Boolean consumRegLink) {
        this.consumRegLink = consumRegLink;
    }

    public Boolean getConsumRegLink() {
        return consumRegLink;
    }

    public void setGpLink(Boolean gpLink) {
        this.gpLink = gpLink;
    }

    public Boolean getGpLink() {
        return gpLink;
    }

    public void setGpSummLink(Boolean gpSummLink) {
        this.gpSummLink = gpSummLink;
    }

    public Boolean getGpSummLink() {
        return gpSummLink;
    }

    public void setGbRetOutLink(Boolean gbRetOutLink) {
        this.gbRetOutLink = gbRetOutLink;
    }

    public Boolean getGbRetOutLink() {
        return gbRetOutLink;
    }

    public void setMrNLink(Boolean mrNLink) {
        this.mrNLink = mrNLink;
    }

    public Boolean getMrNLink() {
        return mrNLink;
    }

    public void setPrSuppWiseLink(Boolean prSuppWiseLink) {
        this.prSuppWiseLink = prSuppWiseLink;
    }

    public Boolean getPrSuppWiseLink() {
        return prSuppWiseLink;
    }

    public void setPrRetItmLink(Boolean prRetItmLink) {
        this.prRetItmLink = prRetItmLink;
    }

    public Boolean getPrRetItmLink() {
        return prRetItmLink;
    }

    public void setRmDALink(Boolean rmDALink) {
        this.rmDALink = rmDALink;
    }

    public Boolean getRmDALink() {
        return rmDALink;
    }

    public void setStkRegLink(Boolean stkRegLink) {
        this.stkRegLink = stkRegLink;
    }

    public Boolean getStkRegLink() {
        return stkRegLink;
    }

    public void setItmWiseLedgLink(Boolean itmWiseLedgLink) {
        this.itmWiseLedgLink = itmWiseLedgLink;
    }

    public Boolean getItmWiseLedgLink() {
        return itmWiseLedgLink;
    }

    public void setSerialWiseLink(Boolean serialWiseLink) {
        this.serialWiseLink = serialWiseLink;
    }

    public Boolean getSerialWiseLink() {
        return serialWiseLink;
    }

    public void setCurrStkSummLink(Boolean currStkSummLink) {
        this.currStkSummLink = currStkSummLink;
    }

    public Boolean getCurrStkSummLink() {
        return currStkSummLink;
    }

    public void setItmMovDtLink(Boolean itmMovDtLink) {
        this.itmMovDtLink = itmMovDtLink;
    }

    public Boolean getItmMovDtLink() {
        return itmMovDtLink;
    }

    public void setBinWiseLink(Boolean binWiseLink) {
        this.binWiseLink = binWiseLink;
    }

    public Boolean getBinWiseLink() {
        return binWiseLink;
    }

    public void setLotWiseLink(Boolean lotWiseLink) {
        this.lotWiseLink = lotWiseLink;
    }

    public Boolean getLotWiseLink() {
        return lotWiseLink;
    }

    public void setStkTkLink(Boolean stkTkLink) {
        this.stkTkLink = stkTkLink;
    }

    public Boolean getStkTkLink() {
        return stkTkLink;
    }

    public void setStkTkDtLink(Boolean stkTkDtLink) {
        this.stkTkDtLink = stkTkDtLink;
    }

    public Boolean getStkTkDtLink() {
        return stkTkDtLink;
    }

    public void setStkTkSummLink(Boolean stkTkSummLink) {
        this.stkTkSummLink = stkTkSummLink;
    }

    public Boolean getStkTkSummLink() {
        return stkTkSummLink;
    }

    public void setKitWrkShopLink(Boolean kitWrkShopLink) {
        this.kitWrkShopLink = kitWrkShopLink;
    }

    public Boolean getKitWrkShopLink() {
        return kitWrkShopLink;
    }

    public void setIndentLink(Boolean indentLink) {
        this.indentLink = indentLink;
    }

    public Boolean getIndentLink() {
        return indentLink;
    }

    public void setMrSLink(Boolean mrSLink) {
        this.mrSLink = mrSLink;
    }

    public Boolean getMrSLink() {
        return mrSLink;
    }

    public void setPurReqLink(Boolean purReqLink) {
        this.purReqLink = purReqLink;
    }

    public Boolean getPurReqLink() {
        return purReqLink;
    }

    public void setQcLink(Boolean qcLink) {
        this.qcLink = qcLink;
    }

    public Boolean getQcLink() {
        return qcLink;
    }

    public void setTrnsferOrdLink(Boolean trnsferOrdLink) {
        this.trnsferOrdLink = trnsferOrdLink;
    }

    public Boolean getTrnsferOrdLink() {
        return trnsferOrdLink;
    }

    public void setStkTrnsferInvLink(Boolean stkTrnsferInvLink) {
        this.stkTrnsferInvLink = stkTrnsferInvLink;
    }

    public Boolean getStkTrnsferInvLink() {
        return stkTrnsferInvLink;
    }

    public void setPurInvLink(Boolean purInvLink) {
        this.purInvLink = purInvLink;
    }

    public Boolean getPurInvLink() {
        return purInvLink;
    }

    public void setFileTypBinding(RichSelectOneRadio fileTypBinding) {
        this.fileTypBinding = fileTypBinding;
    }

    public RichSelectOneRadio getFileTypBinding() {
        return fileTypBinding;
    }

    public void setRcptRegCBBind(RichSelectBooleanCheckbox rcptRegCBBind) {
        this.rcptRegCBBind = rcptRegCBBind;
    }

    public RichSelectBooleanCheckbox getRcptRegCBBind() {
        return rcptRegCBBind;
    }

    public void setRcptNoWiseCBBind(RichSelectBooleanCheckbox rcptNoWiseCBBind) {
        this.rcptNoWiseCBBind = rcptNoWiseCBBind;
    }

    public RichSelectBooleanCheckbox getRcptNoWiseCBBind() {
        return rcptNoWiseCBBind;
    }

    public void setRcptRegItmCBBind(RichSelectBooleanCheckbox rcptRegItmCBBind) {
        this.rcptRegItmCBBind = rcptRegItmCBBind;
    }

    public RichSelectBooleanCheckbox getRcptRegItmCBBind() {
        return rcptRegItmCBBind;
    }

    public void setGeCBBind(RichSelectBooleanCheckbox geCBBind) {
        this.geCBBind = geCBBind;
    }

    public RichSelectBooleanCheckbox getGeCBBind() {
        return geCBBind;
    }

    public void setRcptRegPrtyCBBind(RichSelectBooleanCheckbox rcptRegPrtyCBBind) {
        this.rcptRegPrtyCBBind = rcptRegPrtyCBBind;
    }

    public RichSelectBooleanCheckbox getRcptRegPrtyCBBind() {
        return rcptRegPrtyCBBind;
    }

    public void setIssueNoWiseCBBind(RichSelectBooleanCheckbox issueNoWiseCBBind) {
        this.issueNoWiseCBBind = issueNoWiseCBBind;
    }

    public RichSelectBooleanCheckbox getIssueNoWiseCBBind() {
        return issueNoWiseCBBind;
    }

    public void setConsumRegCBBind(RichSelectBooleanCheckbox consumRegCBBind) {
        this.consumRegCBBind = consumRegCBBind;
    }

    public RichSelectBooleanCheckbox getConsumRegCBBind() {
        return consumRegCBBind;
    }

    public void setGpCBBind(RichSelectBooleanCheckbox gpCBBind) {
        this.gpCBBind = gpCBBind;
    }

    public RichSelectBooleanCheckbox getGpCBBind() {
        return gpCBBind;
    }

    public void setGpSummCBBind(RichSelectBooleanCheckbox gpSummCBBind) {
        this.gpSummCBBind = gpSummCBBind;
    }

    public RichSelectBooleanCheckbox getGpSummCBBind() {
        return gpSummCBBind;
    }

    public void setGbRetOutCBBind(RichSelectBooleanCheckbox gbRetOutCBBind) {
        this.gbRetOutCBBind = gbRetOutCBBind;
    }

    public RichSelectBooleanCheckbox getGbRetOutCBBind() {
        return gbRetOutCBBind;
    }

    public void setMrNCBBind(RichSelectBooleanCheckbox mrNCBBind) {
        this.mrNCBBind = mrNCBBind;
    }

    public RichSelectBooleanCheckbox getMrNCBBind() {
        return mrNCBBind;
    }

    public void setPrSuppWiseCBBind(RichSelectBooleanCheckbox prSuppWiseCBBind) {
        this.prSuppWiseCBBind = prSuppWiseCBBind;
    }

    public RichSelectBooleanCheckbox getPrSuppWiseCBBind() {
        return prSuppWiseCBBind;
    }

    public void setPrRetItmCBBind(RichSelectBooleanCheckbox prRetItmCBBind) {
        this.prRetItmCBBind = prRetItmCBBind;
    }

    public RichSelectBooleanCheckbox getPrRetItmCBBind() {
        return prRetItmCBBind;
    }

    public void setRmDACBBind(RichSelectBooleanCheckbox rmDACBBind) {
        this.rmDACBBind = rmDACBBind;
    }

    public RichSelectBooleanCheckbox getRmDACBBind() {
        return rmDACBBind;
    }

    public void setStkRegCBBind(RichSelectBooleanCheckbox stkRegCBBind) {
        this.stkRegCBBind = stkRegCBBind;
    }

    public RichSelectBooleanCheckbox getStkRegCBBind() {
        return stkRegCBBind;
    }

    public void setItmWiseLedgCBBind(RichSelectBooleanCheckbox itmWiseLedgCBBind) {
        this.itmWiseLedgCBBind = itmWiseLedgCBBind;
    }

    public RichSelectBooleanCheckbox getItmWiseLedgCBBind() {
        return itmWiseLedgCBBind;
    }

    public void setSerialWiseCBBind(RichSelectBooleanCheckbox serialWiseCBBind) {
        this.serialWiseCBBind = serialWiseCBBind;
    }

    public RichSelectBooleanCheckbox getSerialWiseCBBind() {
        return serialWiseCBBind;
    }

    public void setCurrStkSummCBBind(RichSelectBooleanCheckbox currStkSummCBBind) {
        this.currStkSummCBBind = currStkSummCBBind;
    }

    public RichSelectBooleanCheckbox getCurrStkSummCBBind() {
        return currStkSummCBBind;
    }

    public void setItmMovDtCBBind(RichSelectBooleanCheckbox itmMovDtCBBind) {
        this.itmMovDtCBBind = itmMovDtCBBind;
    }

    public RichSelectBooleanCheckbox getItmMovDtCBBind() {
        return itmMovDtCBBind;
    }

    public void setBinWiseCBBind(RichSelectBooleanCheckbox binWiseCBBind) {
        this.binWiseCBBind = binWiseCBBind;
    }

    public RichSelectBooleanCheckbox getBinWiseCBBind() {
        return binWiseCBBind;
    }

    public void setLotWiseCBBind(RichSelectBooleanCheckbox lotWiseCBBind) {
        this.lotWiseCBBind = lotWiseCBBind;
    }

    public RichSelectBooleanCheckbox getLotWiseCBBind() {
        return lotWiseCBBind;
    }

    public void setStkTkCBBind(RichSelectBooleanCheckbox stkTkCBBind) {
        this.stkTkCBBind = stkTkCBBind;
    }

    public RichSelectBooleanCheckbox getStkTkCBBind() {
        return stkTkCBBind;
    }

    public void setStkTkDtCBBind(RichSelectBooleanCheckbox stkTkDtCBBind) {
        this.stkTkDtCBBind = stkTkDtCBBind;
    }

    public RichSelectBooleanCheckbox getStkTkDtCBBind() {
        return stkTkDtCBBind;
    }

    public void setStkTkSummCBBind(RichSelectBooleanCheckbox stkTkSummCBBind) {
        this.stkTkSummCBBind = stkTkSummCBBind;
    }

    public RichSelectBooleanCheckbox getStkTkSummCBBind() {
        return stkTkSummCBBind;
    }

    public void setKitWrkShopCBBind(RichSelectBooleanCheckbox kitWrkShopCBBind) {
        this.kitWrkShopCBBind = kitWrkShopCBBind;
    }

    public RichSelectBooleanCheckbox getKitWrkShopCBBind() {
        return kitWrkShopCBBind;
    }

    public void setIndentCBBind(RichSelectBooleanCheckbox indentCBBind) {
        this.indentCBBind = indentCBBind;
    }

    public RichSelectBooleanCheckbox getIndentCBBind() {
        return indentCBBind;
    }

    public void setMrSCBBind(RichSelectBooleanCheckbox mrSCBBind) {
        this.mrSCBBind = mrSCBBind;
    }

    public RichSelectBooleanCheckbox getMrSCBBind() {
        return mrSCBBind;
    }

    public void setPurReqCBBind(RichSelectBooleanCheckbox purReqCBBind) {
        this.purReqCBBind = purReqCBBind;
    }

    public RichSelectBooleanCheckbox getPurReqCBBind() {
        return purReqCBBind;
    }

    public void setQcCBBind(RichSelectBooleanCheckbox qcCBBind) {
        this.qcCBBind = qcCBBind;
    }

    public RichSelectBooleanCheckbox getQcCBBind() {
        return qcCBBind;
    }

    public void setTrnsferOrdCBBind(RichSelectBooleanCheckbox trnsferOrdCBBind) {
        this.trnsferOrdCBBind = trnsferOrdCBBind;
    }

    public RichSelectBooleanCheckbox getTrnsferOrdCBBind() {
        return trnsferOrdCBBind;
    }

    public void setStkTrnsferInvCBBind(RichSelectBooleanCheckbox stkTrnsferInvCBBind) {
        this.stkTrnsferInvCBBind = stkTrnsferInvCBBind;
    }

    public RichSelectBooleanCheckbox getStkTrnsferInvCBBind() {
        return stkTrnsferInvCBBind;
    }

    public void setPurInvCBBind(RichSelectBooleanCheckbox purInvCBBind) {
        this.purInvCBBind = purInvCBBind;
    }

    public RichSelectBooleanCheckbox getPurInvCBBind() {
        return purInvCBBind;
    }

    public void donwVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (downLOVBindind.getValue() != null && downLOVBindind.getValue().toString().length() > 0) {
                    System.out.println("inside true");
                    rptNmLnkBinding.setVisible(true);
                } else {
                    System.out.println("Inside false");
                    downCBBinding.setValue("fasle");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(downCBBinding);
                    FacesMessage message = new FacesMessage("Please Select Report Name..!!!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(downLOVBindind.getClientId(), message);
                }
            } else {
                downCBBinding.setValue("fasle");
                rptNmLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(rptNmLnkBinding);
            }
        } else {
            downCBBinding.setValue("fasle");
            rptNmLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rptNmLnkBinding);
        }
    }

    public void setDownCBBinding(RichSelectBooleanCheckbox downCBBinding) {
        this.downCBBinding = downCBBinding;
    }

    public RichSelectBooleanCheckbox getDownCBBinding() {
        return downCBBinding;
    }

    public void setDownLOVBindind(RichInputListOfValues downLOVBindind) {
        this.downLOVBindind = downLOVBindind;
    }

    public RichInputListOfValues getDownLOVBindind() {
        return downLOVBindind;
    }

    public void setRptNmLnkBinding(RichLink rptNmLnkBinding) {
        this.rptNmLnkBinding = rptNmLnkBinding;
    }

    public RichLink getRptNmLnkBinding() {
        return rptNmLnkBinding;
    }

    public void setOrgViewCBBinding(RichSelectBooleanCheckbox orgViewCBBinding) {
        this.orgViewCBBinding = orgViewCBBinding;
    }

    public RichSelectBooleanCheckbox getOrgViewCBBinding() {
        return orgViewCBBinding;
    }

    public void orgViewCBVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        rptNmLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rptNmLnkBinding);
    }

    public void downLovVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue() != null) {
            System.out.println("Inside valueChangeEvent");
            downCBBinding.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(downCBBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(msgBinding);
            rptNmLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rptNmLnkBinding);
        }
    }

    public void setMsgBinding(RichMessage msgBinding) {
        this.msgBinding = msgBinding;
    }

    public RichMessage getMsgBinding() {
        return msgBinding;
    }

    public void setGeWorkJobCBBinding(RichSelectBooleanCheckbox geWorkJobCBBinding) {
        this.geWorkJobCBBinding = geWorkJobCBBinding;
    }

    public RichSelectBooleanCheckbox getGeWorkJobCBBinding() {
        return geWorkJobCBBinding;
    }

    public void setStockAgeingCBBinding(RichSelectBooleanCheckbox stockAgeingCBBinding) {
        this.stockAgeingCBBinding = stockAgeingCBBinding;
    }

    public RichSelectBooleanCheckbox getStockAgeingCBBinding() {
        return stockAgeingCBBinding;
    }

    /**
     * Code for Resolve El
     */
    /*     public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    } */
    public String resolvEl(String data) {
        ADFContext adfCtx = ADFContext.getCurrent();
        ELContext elContext = adfCtx.getELContext();
        ValueExpression valueExp = adfCtx.getExpressionFactory().createValueExpression(elContext, data, Object.class);
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
        if (fromDatePgBinding.getValue() == null || fromDatePgBinding.getValue().toString().length() <= 0) {
            dateClientId = fromDatePgBinding.getClientId();
            return true;
        } else if (toDatePgBinding.getValue() == null || toDatePgBinding.getValue().toString().length() <= 0) {
            dateClientId = toDatePgBinding.getClientId();
            return true;
        } else {
            return false;
        }
    }

    public void rcptRegCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (rcptNoLovPgBinding.getValue() != null && rcptNoLovPgBinding.getValue().toString().length() > 0) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("In po bind value-->" + rcptNoLovPgBinding.getValue());
                    RcptRegLink = false;
                } else {
                    System.out.println("in else of receipt");
                    RcptRegLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            rcptRegCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(rcptRegCBBind);
                            RcptRegLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            RcptRegLink = false;
                        }
                    } else {
                        RcptRegLink = false;
                    }
                }
            }
        }
    }

    public void rcptNowiseCBVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void rcptRegisterItmwiseCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (rcptNoLovPgBinding.getValue() != null && rcptNoLovPgBinding.getValue().toString().length() > 0) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("In po bind value-->" + rcptNoLovPgBinding.getValue());
                    RcptRegItmLink = false;
                } else {
                    System.out.println("in else of receipt");
                    RcptRegItmLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            rcptRegItmCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(rcptRegItmCBBind);
                            RcptRegItmLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            RcptRegItmLink = false;
                        }
                    } else {
                        RcptRegItmLink = false;
                    }
                }
            }
        }
    }

    public void rcptRegisterPartywiseCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (rcptNoLovPgBinding.getValue() != null && rcptNoLovPgBinding.getValue().toString().length() > 0) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("In po bind value-->" + rcptNoLovPgBinding.getValue());
                    RcptRegPrtyLink = false; // To enable GoLInk
                } else {
                    System.out.println("in else of receipt");
                    RcptRegPrtyLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            rcptRegPrtyCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(rcptRegPrtyCBBind);
                            RcptRegPrtyLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            RcptRegPrtyLink = false;
                        }
                    } else {
                        RcptRegPrtyLink = false;
                    }
                }
            }
        }
    }

    public void gateEntryCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (rcptNoLovPgBinding.getValue() != null && rcptNoLovPgBinding.getValue().toString().length() > 0) {
                    System.out.println("inside true");
                    GELink = false;
                } else {
                    System.out.println("Inside false");
                    geCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(geCBBind);
                    FacesMessage message = new FacesMessage("Please Select Receipt No.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(rcptNoLovPgBinding.getClientId(), message);
                }
            } else {
                geCBBind.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(geCBBind);
                GELink = false;
            }
        } else {
            geCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(geCBBind);
            GELink = false;
        }
    }

    public void gateEntryWorkCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (rcptNoLovPgBinding.getValue() != null && rcptNoLovPgBinding.getValue().toString().length() > 0) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("In po bind value-->" + rcptNoLovPgBinding.getValue());
                    geWorkJobLink = false;
                } else {
                    System.out.println("in else of receipt");
                    geWorkJobLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            geWorkJobCBBinding.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(geWorkJobCBBinding);
                            geWorkJobLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            geWorkJobLink = false;
                        }
                    } else {
                        geWorkJobLink = false;
                    }
                }
            }
        }
    }

    public void qcProcCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if ((qcRcptNoLovPgBinding.getValue() != null && qcRcptNoLovPgBinding.getValue().toString().length() > 0) ||
                (qcnoLOVPgBind.getValue() != null && qcnoLOVPgBind.getValue().toString().length() > 0)) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("bind value-->" + qcRcptNoLovPgBinding.getValue());
                    qcLink = false;
                } else {
                    qcLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            qcCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(qcCBBind);
                            qcLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            qcLink = false;
                        }
                    } else {
                        qcLink = false;
                    }
                }
            }
        }
    }

    public void setRcptNoLovPgBinding(RichInputListOfValues rcptNoLovPgBinding) {
        this.rcptNoLovPgBinding = rcptNoLovPgBinding;
    }

    public RichInputListOfValues getRcptNoLovPgBinding() {
        return rcptNoLovPgBinding;
    }

    public void setQcRcptNoLovPgBinding(RichInputListOfValues qcRcptNoLovPgBinding) {
        this.qcRcptNoLovPgBinding = qcRcptNoLovPgBinding;
    }

    public RichInputListOfValues getQcRcptNoLovPgBinding() {
        return qcRcptNoLovPgBinding;
    }

    public void setFromDatePgBinding(RichInputDate fromDatePgBinding) {
        this.fromDatePgBinding = fromDatePgBinding;
    }

    public RichInputDate getFromDatePgBinding() {
        return fromDatePgBinding;
    }

    public void setToDatePgBinding(RichInputDate toDatePgBinding) {
        this.toDatePgBinding = toDatePgBinding;
    }

    public RichInputDate getToDatePgBinding() {
        return toDatePgBinding;
    }

    public void issueNowiseCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (issueNoLovPgBinding.getValue() != null && issueNoLovPgBinding.getValue().toString().length() > 0) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("bind value-->" + issueNoLovPgBinding.getValue());
                    issueNoWiseLink = false;
                } else {
                    issueNoWiseLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            issueNoWiseCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(issueNoWiseCBBind);
                            issueNoWiseLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            issueNoWiseLink = false;
                        }
                    } else {
                        issueNoWiseLink = false;
                    }
                }
            }
        }
    }

    public void consumptionRegCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (issueNoLovPgBinding.getValue() != null && issueNoLovPgBinding.getValue().toString().length() > 0) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("bind value-->" + issueNoLovPgBinding.getValue());
                    consumRegLink = false;
                } else {
                    consumRegLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            consumRegCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(consumRegCBBind);
                            consumRegLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            consumRegLink = false;
                        }
                    } else {
                        consumRegLink = false;
                    }
                }
            }
        }
    }

    public void setIssueNoLovPgBinding(RichInputListOfValues issueNoLovPgBinding) {
        this.issueNoLovPgBinding = issueNoLovPgBinding;
    }

    public RichInputListOfValues getIssueNoLovPgBinding() {
        return issueNoLovPgBinding;
    }

    public void setGatePassNoLovPgBinding(RichInputListOfValues gatePassNoLovPgBinding) {
        this.gatePassNoLovPgBinding = gatePassNoLovPgBinding;
    }

    public RichInputListOfValues getGatePassNoLovPgBinding() {
        return gatePassNoLovPgBinding;
    }

    public void gatePassCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (gatePassNoLovPgBinding.getValue() != null &&
                    gatePassNoLovPgBinding.getValue().toString().length() > 0) {
                    System.out.println("inside true");
                    gpLink = true;
                } else {
                    System.out.println("Inside false");
                    gpCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(gpCBBind);
                    FacesMessage message = new FacesMessage("Please Select Gate Pass No.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(gatePassNoLovPgBinding.getClientId(), message);
                }
            } else {
                gpCBBind.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gpCBBind);
                gpLink = false;
            }
        } else {
            gpCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(gpCBBind);
            gpLink = false;
        }
    }

    public void gatePassSummCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (gatePassNoLovPgBinding.getValue() != null &&
                gatePassNoLovPgBinding.getValue().toString().length() > 0) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("bind value-->" + gatePassNoLovPgBinding.getValue());
                    gpSummLink = false;
                } else {
                    gpSummLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            gpSummCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(gpSummCBBind);
                            gpSummLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            gpSummLink = false;
                        }
                    } else {
                        gpSummLink = false;
                    }
                }
            }
        }
    }

    public void gatePassRetOutsCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if ((gatePassNoLovPgBinding.getValue() != null &&
                gatePassNoLovPgBinding.getValue().toString().length() > 0)|| (suppLovPgBinding.getValue() != null && suppLovPgBinding.getValue().toString().length() > 0)) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("bind value-->" + gatePassNoLovPgBinding.getValue());
                    gbRetOutLink = false;
                } else {
                    gbRetOutLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            gbRetOutCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(gbRetOutCBBind);
                            gbRetOutLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            gbRetOutLink = false;
                        }
                    } else {
                        gbRetOutLink = false;
                    }
                }
            }
        }
        
        
       /* if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (gatePassNoLovPgBinding.getValue() != null &&
                    gatePassNoLovPgBinding.getValue().toString().length() > 0) {
                    System.out.println("inside true");
                    gbRetOutLink = true;
                } else {
                    System.out.println("Inside false");
                    gbRetOutCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(gbRetOutCBBind);
                    FacesMessage message = new FacesMessage("Please Select Gate Pass No.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(gatePassNoLovPgBinding.getClientId(), message);
                }
            } else {
                gbRetOutCBBind.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(gbRetOutCBBind);
                gbRetOutLink = false;
            }
        } else {
            gbRetOutCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(gbRetOutCBBind);
            gbRetOutLink = false;
        }*/
    }

    public void setMrNLovPgBinding(RichInputListOfValues mrNLovPgBinding) {
        this.mrNLovPgBinding = mrNLovPgBinding;
    }

    public RichInputListOfValues getMrNLovPgBinding() {
        return mrNLovPgBinding;
    }

    public void MRNCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (mrNLovPgBinding.getValue() != null && mrNLovPgBinding.getValue().toString().length() > 0) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("bind value-->" + mrNLovPgBinding.getValue());
                    mrNLink = false;
                } else {
                    mrNLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            mrNCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(mrNCBBind);
                            mrNLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            mrNLink = false;
                        }
                    } else {
                        mrNLink = false;
                    }
                }
            }
        }
    }

    public void setPurRetrnLovPgBinding(RichInputListOfValues purRetrnLovPgBinding) {
        this.purRetrnLovPgBinding = purRetrnLovPgBinding;
    }

    public RichInputListOfValues getPurRetrnLovPgBinding() {
        return purRetrnLovPgBinding;
    }

    public void pRetrnSuppCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if ((purRetrnLovPgBinding.getValue() != null && purRetrnLovPgBinding.getValue().toString().length() > 0) ||
                (suppLovPgBinding.getValue() != null && suppLovPgBinding.getValue().toString().length() > 0)) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("bind value-->" + purRetrnLovPgBinding.getValue());
                    prSuppWiseLink = false;
                } else {
                    prSuppWiseLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            prSuppWiseCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(prSuppWiseCBBind);
                            prSuppWiseLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            prSuppWiseLink = false;
                        }
                    } else {
                        prSuppWiseLink = false;
                    }
                }
            }
        }
    }

    public void purRetrnItmWiseCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if ((purRetrnLovPgBinding.getValue() != null && purRetrnLovPgBinding.getValue().toString().length() > 0) ||
                (itmLovPgBinding.getValue() != null && itmLovPgBinding.getValue().toString().length() > 0)) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("bind value-->" + purRetrnLovPgBinding.getValue());
                    prRetItmLink = false;
                } else {
                    prRetItmLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            prRetItmCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(prRetItmCBBind);
                            prRetItmLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            prRetItmLink = false;
                        }
                    } else {
                        prRetItmLink = false;
                    }
                }
            }
        }
    }

    public void setRmDALovPgBinding(RichInputListOfValues rmDALovPgBinding) {
        this.rmDALovPgBinding = rmDALovPgBinding;
    }

    public RichInputListOfValues getRmDALovPgBinding() {
        return rmDALovPgBinding;
    }

    public void RMDACBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (rmDALovPgBinding.getValue() != null && rmDALovPgBinding.getValue().toString().length() > 0) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("bind value-->" + rmDALovPgBinding.getValue());
                    rmDALink = false;
                } else {
                    rmDALink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            rmDACBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(rmDACBBind);
                            rmDALink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            rmDALink = false;
                        }
                    } else {
                        rmDALink = false;
                    }
                }
            }
        }
    }

    public void stockRegCBVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void ItmwiseLedgerCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (checkBoxValidation()) {
                    itmWiseLedgCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itmWiseLedgCBBind);
                    itmWiseLedgLink = false;
                    showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                } else {
                    itmWiseLedgLink = false;
                }
            } else {
                itmWiseLedgLink = false;
            }
        }
    }

    public void serialwiseCBVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void stockSummCBVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void ItmMovementDetailCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (checkBoxValidation()) {
                    itmMovDtCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(itmMovDtCBBind);
                    itmMovDtLink = false;
                    showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                } else {
                    itmMovDtLink = false;
                }
            } else {
                itmMovDtLink = false;
            }
        }
    }

    public void binWiseCBVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void lotWiseCBVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void stockAgeingCBVCL(ValueChangeEvent valueChangeEvent) {
        /* if (valueChangeEvent.getNewValue() != null) {
         if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
         if(toDatePgBinding.getValue()==null||toDatePgBinding.getValue().equals("")) {
              stockAgeingCBBinding.setValue(false);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(stockAgeingCBBinding);
                 stockAgeingLink = false;
                 showFacesMessage("Please Select Date", "E", false, "F", toDatePgBinding.getClientId());
                 } else {
                                         stockAgeingLink = false;
                                      }
                                  } else {
                                      stockAgeingLink = false;
                                  }
                              }   */
    }

    public void setStockTakeNoLovPgBinding(RichInputListOfValues stockTakeNoLovPgBinding) {
        this.stockTakeNoLovPgBinding = stockTakeNoLovPgBinding;
    }

    public RichInputListOfValues getStockTakeNoLovPgBinding() {
        return stockTakeNoLovPgBinding;
    }

    public void stockTkProfCBVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void stockTkDetailCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (stockTakeNoLovPgBinding.getValue() != null &&
                    stockTakeNoLovPgBinding.getValue().toString().length() > 0) {
                    System.out.println("inside true");
                    stkTkDtLink = true;
                } else {
                    System.out.println("Inside false");
                    stkTkDtCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(stkTkDtCBBind);
                    FacesMessage message = new FacesMessage("Please Select Stock Take No.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(stockTakeNoLovPgBinding.getClientId(), message);
                }
            } else {
                stkTkDtCBBind.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(stkTkDtCBBind);
                stkTkDtLink = false;
            }
        } else {
            stkTkDtCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(stkTkDtCBBind);
            stkTkDtLink = false;
        }
    }

    public void stockTkSummCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (stockTakeNoLovPgBinding.getValue() != null &&
                    stockTakeNoLovPgBinding.getValue().toString().length() > 0) {
                    System.out.println("inside true");
                    stkTkSummLink = true;
                } else {
                    System.out.println("Inside false");
                    stkTkSummCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(stkTkSummCBBind);
                    FacesMessage message = new FacesMessage("Please Select Stock Take No.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(stockTakeNoLovPgBinding.getClientId(), message);
                }
            } else {
                stkTkSummCBBind.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(stkTkSummCBBind);
                stkTkSummLink = false;
            }
        } else {
            stkTkSummCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(stkTkSummCBBind);
            stkTkSummLink = false;
        }
    }

    public void setKitLovPgBinding(RichInputListOfValues kitLovPgBinding) {
        this.kitLovPgBinding = kitLovPgBinding;
    }

    public RichInputListOfValues getKitLovPgBinding() {
        return kitLovPgBinding;
    }

    public void kitWorkshopCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (kitLovPgBinding.getValue() != null && kitLovPgBinding.getValue().toString().length() > 0) {
                    System.out.println("inside true");
                    kitWrkShopLink = false;
                } else {
                    System.out.println("Inside false");
                    kitWrkShopCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(kitWrkShopCBBind);
                    FacesMessage message = new FacesMessage("Please Select Kit Production No.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(kitLovPgBinding.getClientId(), message);
                }
            } else {
                kitWrkShopCBBind.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(kitWrkShopCBBind);
                kitWrkShopLink = false;
            }
        } else {
            kitWrkShopCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(kitWrkShopCBBind);
            kitWrkShopLink = false;
        }
    }

    public void setMrSLovPgBinding(RichInputListOfValues mrSLovPgBinding) {
        this.mrSLovPgBinding = mrSLovPgBinding;
    }

    public RichInputListOfValues getMrSLovPgBinding() {
        return mrSLovPgBinding;
    }

    public void MRSCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (mrSLovPgBinding.getValue() != null && mrSLovPgBinding.getValue().toString().length() > 0) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("bind value-->" + mrSLovPgBinding.getValue());
                    mrSLink = false;
                } else {
                    mrSLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            mrSCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(mrSCBBind);
                            mrSLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            mrSLink = false;
                        }
                    } else {
                        mrSLink = false;
                    }
                }
            }
        }
    }

    public void indentRptCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (mrSLovPgBinding.getValue() != null && mrSLovPgBinding.getValue().toString().length() > 0) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("bind value-->" + mrSLovPgBinding.getValue());
                    indentLink = false;
                } else {
                    indentLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            indentCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(indentCBBind);
                            indentLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            indentLink = false;
                        }
                    } else {
                        indentLink = false;
                    }
                }
            }
        }
    }

    public void PurReqCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if ((purReqLovPgBinding.getValue() != null && purReqLovPgBinding.getValue().toString().length() > 0)) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("bind value-->" + purReqLovPgBinding.getValue());
                    purReqLink = false;
                } else {
                    purReqLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            purReqCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(purReqCBBind);
                            purReqLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            purReqLink = false;
                        }
                    } else {
                        purReqLink = false;
                    }
                }
            }
        }
    }

    public void setPurReqLovPgBinding(RichInputListOfValues purReqLovPgBinding) {
        this.purReqLovPgBinding = purReqLovPgBinding;
    }

    public RichInputListOfValues getPurReqLovPgBinding() {
        return purReqLovPgBinding;
    }

    public void setTransfOrdLovPgBinding(RichInputListOfValues transfOrdLovPgBinding) {
        this.transfOrdLovPgBinding = transfOrdLovPgBinding;
    }

    public RichInputListOfValues getTransfOrdLovPgBinding() {
        return transfOrdLovPgBinding;
    }

    public void transfOrdCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (transfOrdLovPgBinding.getValue() != null &&
                    transfOrdLovPgBinding.getValue().toString().length() > 0) {
                    System.out.println("inside true");
                    trnsferOrdLink = false;
                } else {
                    System.out.println("Inside false");
                    trnsferOrdCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(trnsferOrdCBBind);
                    FacesMessage message = new FacesMessage("Please Select Transfer Order No.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(transfOrdLovPgBinding.getClientId(), message);
                }
            } else {
                trnsferOrdCBBind.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(trnsferOrdCBBind);
                trnsferOrdLink = false;
            }
        } else {
            trnsferOrdCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(trnsferOrdCBBind);
            trnsferOrdLink = false;
        }
    }

    public void setInvoiceNoLovPgBinding(RichInputListOfValues invoiceNoLovPgBinding) {
        this.invoiceNoLovPgBinding = invoiceNoLovPgBinding;
    }

    public RichInputListOfValues getInvoiceNoLovPgBinding() {
        return invoiceNoLovPgBinding;
    }

    public void stockTransfInvcCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (invoiceNoLovPgBinding.getValue() != null &&
                    invoiceNoLovPgBinding.getValue().toString().length() > 0) {
                    System.out.println("inside true");
                    stkTrnsferInvLink = false;
                } else {
                    System.out.println("Inside false");
                    stkTrnsferInvCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(stkTrnsferInvCBBind);
                    FacesMessage message = new FacesMessage("Please Select Invoice No.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(invoiceNoLovPgBinding.getClientId(), message);
                }
            } else {
                stkTrnsferInvCBBind.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(stkTrnsferInvCBBind);
                stkTrnsferInvLink = false;
            }
        } else {
            stkTrnsferInvCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(stkTrnsferInvCBBind);
            stkTrnsferInvLink = false;
        }
    }

    public void purchInvCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (invoiceNoLovPgBinding.getValue() != null &&
                    invoiceNoLovPgBinding.getValue().toString().length() > 0) {
                    System.out.println("inside true");
                    purInvLink = false;
                } else {
                    System.out.println("Inside false");
                    purInvCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(purInvCBBind);
                    FacesMessage message = new FacesMessage("Please Select Invoice No.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(invoiceNoLovPgBinding.getClientId(), message);
                }
            } else {
                purInvCBBind.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(purInvCBBind);
                purInvLink = false;
            }
        } else {
            purInvCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(purInvCBBind);
            purInvLink = false;
        }
    }

    public void setItmLovPgBinding(RichInputListOfValues itmLovPgBinding) {
        this.itmLovPgBinding = itmLovPgBinding;
    }

    public RichInputListOfValues getItmLovPgBinding() {
        return itmLovPgBinding;
    }

    public void setSuppLovPgBinding(RichInputListOfValues suppLovPgBinding) {
        this.suppLovPgBinding = suppLovPgBinding;
    }

    public RichInputListOfValues getSuppLovPgBinding() {
        return suppLovPgBinding;
    }

    public void purInvRegCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if ((invoiceNoLovPgBinding.getValue() != null &&
                 invoiceNoLovPgBinding.getValue().toString().length() > 0) ||
                (suppLovPgBinding.getValue() != null && suppLovPgBinding.getValue().toString().length() > 0)) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("bind value-->" + invoiceNoLovPgBinding.getValue());
                    purInvcRegLink = false;
                } else {
                    purInvcRegLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            purInvcRegCBBinding.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(purInvcRegCBBinding);
                            purInvcRegLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            purInvcRegLink = false;
                        }
                    } else {
                        purInvcRegLink = false;
                    }
                }
            }
        }
    }

    public void setPurInvcRegCBBinding(RichSelectBooleanCheckbox purInvcRegCBBinding) {
        this.purInvcRegCBBinding = purInvcRegCBBinding;
    }

    public RichSelectBooleanCheckbox getPurInvcRegCBBinding() {
        return purInvcRegCBBinding;
    }

    public void setStkAdjLovBinding(RichInputListOfValues stkAdjLovBinding) {
        this.stkAdjLovBinding = stkAdjLovBinding;
    }

    public RichInputListOfValues getStkAdjLovBinding() {
        return stkAdjLovBinding;
    }

    public void setStkAdjCBBinding(RichSelectBooleanCheckbox stkAdjCBBinding) {
        this.stkAdjCBBinding = stkAdjCBBinding;
    }

    public RichSelectBooleanCheckbox getStkAdjCBBinding() {
        return stkAdjCBBinding;
    }

    public void stkAdjCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (stkAdjLovBinding.getValue() != null && stkAdjLovBinding.getValue().toString().length() > 0) {
                    System.out.println("inside true");
                    stkAdjLink = false;
                } else {
                    System.out.println("Inside false");
                    stkAdjCBBinding.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(stkAdjCBBinding);
                    FacesMessage message = new FacesMessage("Please Select Stock Adjustment No.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(stkAdjLovBinding.getClientId(), message);
                }
            } else {
                stkAdjCBBinding.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(stkAdjCBBinding);
                stkAdjLink = false;
            }
        } else {
            stkAdjCBBinding.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(stkAdjCBBinding);
            stkAdjLink = false;
        }
    }

    public void setProdAgLotCBBindig(RichSelectBooleanCheckbox prodAgLotCBBindig) {
        this.prodAgLotCBBindig = prodAgLotCBBindig;
    }

    public RichSelectBooleanCheckbox getProdAgLotCBBindig() {
        return prodAgLotCBBindig;
    }

    String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString(); ;
    int userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
    String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
    int slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());

    public void fetchData() {
        System.out.println(orgId + userId);
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MMDS");
            conn = ds.getConnection();
            //   stmt = conn.createStatement();
            String selectStmt =
                "select usr_id, BKT_DAYS_TO, BKT_RANGE2, BKT_RANGE3, BKT_RANGE4, BKT_RANGE5 from mm.mm$ag$bkt " +
                "where cld_id = ? and sloc_id = ? and org_id = ? and usr_id = ? and BKT_FOR = 'P'";
            pstmt = conn.prepareStatement(selectStmt);
            pstmt.setString(1, cldId);
            pstmt.setInt(2, slocId);
            pstmt.setString(3, orgId);
            pstmt.setInt(4, userId);
            System.out.println("The SQL query is: " + selectStmt + "--" + orgId + userId);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("The records selected are:");
            int rowCount = 0;
            while (rs.next()) { // Move the cursor to the next row
                userId = rs.getInt("usr_id");
                range1 = rs.getInt("BKT_DAYS_TO");
                range2 = rs.getInt("BKT_RANGE2");
                range3 = rs.getInt("BKT_RANGE3");
                range4 = rs.getInt("BKT_RANGE4");
                range5 = rs.getInt("BKT_RANGE5");
                System.out.println(range1 + ", " + userId + ", " + range2 + "," + range3 + "," + range4 + "," + range5);
                ++rowCount;
            }
            System.out.println("Total number of records = " + rowCount);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    int rangeVal1;
    int rangeVal2;
    int rangeVal3;
    int rangeVal4;
    int rangeVal5;

    public void savedData() {
        System.out.println(orgId + userId);
        rangeVal1 = Integer.parseInt(range1Binding.getValue().toString());
        rangeVal2 = Integer.parseInt(range2Binding.getValue().toString());
        rangeVal3 = Integer.parseInt(range3Binding.getValue().toString());
        rangeVal4 = Integer.parseInt(range4Binding.getValue().toString());
        rangeVal5 = Integer.parseInt(range5Binding.getValue().toString());
        try {
            cstmt = conn.prepareCall("{?=call MM.MM_INS_AG_BKT(?,?,?,?,?,?,?,?,?,?)}");
            cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
            cstmt.setString(2, cldId);
            cstmt.setInt(3, slocId);
            cstmt.setString(4, orgId);
            cstmt.setInt(5, userId);
            cstmt.setString(6, "P");
            cstmt.setInt(7, rangeVal1);
            cstmt.setInt(8, rangeVal2);
            cstmt.setInt(9, rangeVal3);
            cstmt.setInt(10, rangeVal4);
            cstmt.setInt(11, rangeVal5);
            System.out.println("inside save data insert values--" + cldId + slocId + orgId + userId + rangeVal1 +
                               rangeVal2 + rangeVal3 + rangeVal4 + rangeVal5);
            cstmt.executeUpdate();
            conn.setAutoCommit(true);
            System.out.println("After execute.");
            FacesMessage message = new FacesMessage("Record Saved Successfully.");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (cstmt != null) {
                    cstmt.close();
                }
                /*    if( conn!=null){
                       conn.close();
                       }
                 */
            } catch (Exception ex) {
            }
        }
    }
    int rangeVal1Bind = 0;
    int rangeVal2Bind = 0;
    int rangeVal3Bind = 0;
    int rangeVal4Bind = 0;
    int rangeVal5Bind = 0;

    public void ProdAgSaveAction(ActionEvent actionEvent) {
        rangeVal1Bind = Integer.parseInt(range1Binding.getValue().toString());
        rangeVal2Bind = Integer.parseInt(range2Binding.getValue().toString());
        rangeVal3Bind = Integer.parseInt(range3Binding.getValue().toString());
        rangeVal4Bind = Integer.parseInt(range4Binding.getValue().toString());
        rangeVal5Bind = Integer.parseInt(range5Binding.getValue().toString());

        if (rangeVal1Bind < rangeVal2Bind) {
            if ((rangeVal2Bind > rangeVal1Bind) && (rangeVal2Bind < rangeVal3Bind)) {
                if ((rangeVal3Bind > rangeVal2Bind) && (rangeVal3Bind < rangeVal4Bind)) {
                    if ((rangeVal4Bind > rangeVal3Bind) && (rangeVal4Bind < rangeVal5Bind)) {
                        if (rangeVal5Bind > rangeVal4Bind) {
                            this.savedData();
                            saveBtnBinding.setDisabled(true);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(saveBtnBinding);
                        } else {
                            FacesMessage message =
                                new FacesMessage("Please enter the valid value, greater than previous value.");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(range5Binding.getClientId(), message);
                            saveBtnBinding.setDisabled(true);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(saveBtnBinding);
                        }
                    } else {
                        FacesMessage message =
                            new FacesMessage("Please enter the valid value between the previous and next value.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(range4Binding.getClientId(), message);
                        saveBtnBinding.setDisabled(true);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(saveBtnBinding);
                    }
                } else {
                    FacesMessage message =
                        new FacesMessage("Please enter the valid value between the previous and next value.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(range3Binding.getClientId(), message);
                    saveBtnBinding.setDisabled(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(saveBtnBinding);
                }
            } else {
                FacesMessage message =
                    new FacesMessage("Please enter the valid value between the previous and next value.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(range2Binding.getClientId(), message);
                saveBtnBinding.setDisabled(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(saveBtnBinding);
            }
        } else {
            FacesMessage message = new FacesMessage("Please enter the valid value, less than the next value");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(range1Binding.getClientId(), message);
            saveBtnBinding.setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveBtnBinding);
        }
    }

    public void setRange1Binding(RichInputText range1Binding) {
        this.range1Binding = range1Binding;
    }

    public RichInputText getRange1Binding() {
        return range1Binding;
    }

    public void setRange2Binding(RichInputText range2Binding) {
        this.range2Binding = range2Binding;
    }

    public RichInputText getRange2Binding() {
        return range2Binding;
    }

    public void setRange3Binding(RichInputText range3Binding) {
        this.range3Binding = range3Binding;
    }

    public RichInputText getRange3Binding() {
        return range3Binding;
    }

    public void setRange4Binding(RichInputText range4Binding) {
        this.range4Binding = range4Binding;
    }

    public RichInputText getRange4Binding() {
        return range4Binding;
    }

    public void setRange5Binding(RichInputText range5Binding) {
        this.range5Binding = range5Binding;
    }

    public RichInputText getRange5Binding() {
        return range5Binding;
    }

    public void setStkResvCBBinding(RichSelectBooleanCheckbox stkResvCBBinding) {
        this.stkResvCBBinding = stkResvCBBinding;
    }

    public RichSelectBooleanCheckbox getStkResvCBBinding() {
        return stkResvCBBinding;
    }

    public void setStkResvItmWsCBBind(RichSelectBooleanCheckbox stkResvItmWsCBBind) {
        this.stkResvItmWsCBBind = stkResvItmWsCBBind;
    }

    public RichSelectBooleanCheckbox getStkResvItmWsCBBind() {
        return stkResvItmWsCBBind;
    }

    public void range1VCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            rangeVal1Bind = Integer.parseInt(range1Binding.getValue().toString());
            saveBtnBinding.setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveBtnBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range1Binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range2Binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range3Binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range4Binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range5Binding);
        }

    }

    public void range2VCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            rangeVal2Bind = Integer.parseInt(range2Binding.getValue().toString());
            saveBtnBinding.setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveBtnBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range1Binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range2Binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range3Binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range4Binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range5Binding);
        }
    }

    public void range3VCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            rangeVal3Bind = Integer.parseInt(range3Binding.getValue().toString());
            saveBtnBinding.setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveBtnBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range1Binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range2Binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range3Binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range4Binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range5Binding);
        }

    }

    public void range4VCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            rangeVal4Bind = Integer.parseInt(range4Binding.getValue().toString());
            saveBtnBinding.setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveBtnBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range1Binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range2Binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range3Binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range4Binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range5Binding);
        }
    }

    public void range5VCL(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent.getNewValue() != null) {
            rangeVal5Bind = Integer.parseInt(range5Binding.getValue().toString());
            saveBtnBinding.setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveBtnBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range1Binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range2Binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range3Binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range4Binding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(range5Binding);
        }
    }

    public void setSaveBtnBinding(RichButton saveBtnBinding) {
        this.saveBtnBinding = saveBtnBinding;
    }

    public RichButton getSaveBtnBinding() {
        return saveBtnBinding;
    }

    public void range2Validator(FacesContext facesContext, UIComponent uIComponent, Object object) {
    }

    public void setQcnoLOVPgBind(RichInputListOfValues qcnoLOVPgBind) {
        this.qcnoLOVPgBind = qcnoLOVPgBind;
    }

    public RichInputListOfValues getQcnoLOVPgBind() {
        return qcnoLOVPgBind;
    }

    public void setTestCertCBBind(RichSelectBooleanCheckbox testCertCBBind) {
        this.testCertCBBind = testCertCBBind;
    }

    public RichSelectBooleanCheckbox getTestCertCBBind() {
        return testCertCBBind;
    }

    public void TestCertCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (qcnoLOVPgBind.getValue() != null && qcnoLOVPgBind.getValue().toString().length() > 0) {
                    System.out.println("inside true");
                    TestCertLink = false;
                } else {
                    System.out.println("Inside false");
                    testCertCBBind.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(testCertCBBind);
                    FacesMessage message = new FacesMessage("Please Select QC No.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(qcnoLOVPgBind.getClientId(), message);
                }
            } else {
                testCertCBBind.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(testCertCBBind);
                TestCertLink = false;
            }
        } else {
            testCertCBBind.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(testCertCBBind);
            TestCertLink = false;
        }
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void materialInwardDCL(DisclosureEvent disclosureEvent) {
        OperationBinding ob = (OperationBinding) getBindings().getOperationBinding("Execute");
        ob.execute();
        OperationBinding ob1 = (OperationBinding) getBindings().getOperationBinding("Execute1");
        ob1.execute();
    }

    public void materialOutDCL(DisclosureEvent disclosureEvent) {
        OperationBinding ob = (OperationBinding) getBindings().getOperationBinding("Execute");
        ob.execute();
        OperationBinding ob1 = (OperationBinding) getBindings().getOperationBinding("Execute1");
        ob1.execute();
    }

    public void stockDCL(DisclosureEvent disclosureEvent) {
        OperationBinding ob = (OperationBinding) getBindings().getOperationBinding("Execute");
        ob.execute();
        OperationBinding ob1 = (OperationBinding) getBindings().getOperationBinding("Execute1");
        ob1.execute();
    }

    public void inventoryContDCL(DisclosureEvent disclosureEvent) {
        OperationBinding ob = (OperationBinding) getBindings().getOperationBinding("Execute");
        ob.execute();
        OperationBinding ob1 = (OperationBinding) getBindings().getOperationBinding("Execute1");
        ob1.execute();
    }

    public void StkAdjSummCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (stkAdjLovBinding.getValue() != null && stkAdjLovBinding.getValue().toString().length() > 0) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("bind value-->" + stkAdjLovBinding.getValue());
                    stkAdjSummLink = false;
                } else {
                    stkAdjSummLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            stkAdjSummCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(stkAdjSummCBBind);
                            stkAdjSummLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            stkAdjSummLink = false;
                        }
                    } else {
                        stkAdjSummLink = false;
                    }
                }
            }
        }
    }

    public void setStkAdjSummCBBind(RichSelectBooleanCheckbox stkAdjSummCBBind) {
        this.stkAdjSummCBBind = stkAdjSummCBBind;
    }

    public RichSelectBooleanCheckbox getStkAdjSummCBBind() {
        return stkAdjSummCBBind;
    }

    public void IssuRegReqWsCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (issueNoLovPgBinding.getValue() != null && issueNoLovPgBinding.getValue().toString().length() > 0) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("bind value-->" + issueNoLovPgBinding.getValue());
                    issuRegReqWsLink = false;
                } else {
                    issuRegReqWsLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            issuRegReqWsCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(issuRegReqWsCBBind);
                            issuRegReqWsLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            issuRegReqWsLink = false;
                        }
                    } else {
                        issuRegReqWsLink = false;
                    }
                }
            }
        }
    }

    public void setIssuRegReqWsCBBind(RichSelectBooleanCheckbox issuRegReqWsCBBind) {
        this.issuRegReqWsCBBind = issuRegReqWsCBBind;
    }

    public RichSelectBooleanCheckbox getIssuRegReqWsCBBind() {
        return issuRegReqWsCBBind;
    }

    public void setIssuRegItmWsCbBind(RichSelectBooleanCheckbox issuRegItmWsCbBind) {
        this.issuRegItmWsCbBind = issuRegItmWsCbBind;
    }

    public RichSelectBooleanCheckbox getIssuRegItmWsCbBind() {
        return issuRegItmWsCbBind;
    }

    public void IssuRegItmWsCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (issueNoLovPgBinding.getValue() != null && issueNoLovPgBinding.getValue().toString().length() > 0) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("bind value-->" + issueNoLovPgBinding.getValue());
                    issuRegItmWsLink = false;
                } else {
                    issuRegItmWsLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            issuRegItmWsCbBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(issuRegItmWsCbBind);
                            issuRegItmWsLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            issuRegItmWsLink = false;
                        }
                    } else {
                        issuRegItmWsLink = false;
                    }
                }
            }
        }
    }

    public void PurInvItmCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if ((invoiceNoLovPgBinding.getValue() != null &&
                 invoiceNoLovPgBinding.getValue().toString().length() > 0) ||
                (suppLovPgBinding.getValue() != null && suppLovPgBinding.getValue().toString().length() > 0)) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("bind value-->" + invoiceNoLovPgBinding.getValue());
                    purInvcItemLink = false;
                } else {
                     purInvcItemLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            purInvcItemCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(purInvcItemCBBind);
                             purInvcItemLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                             purInvcItemLink = false;
                        }
                    } else {
                         purInvcItemLink = false;
                    }
                }
            }
        }
    }

    public void PurInvSuppCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if ((invoiceNoLovPgBinding.getValue() != null &&
                 invoiceNoLovPgBinding.getValue().toString().length() > 0) ||
                (suppLovPgBinding.getValue() != null && suppLovPgBinding.getValue().toString().length() > 0)) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("bind value-->" + invoiceNoLovPgBinding.getValue());
                    purInvcSuppLink = false;
                } else {
                     purInvcSuppLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            purInvcSuppCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(purInvcSuppCBBind);
                             purInvcSuppLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                             purInvcSuppLink = false;
                        }
                    } else {
                         purInvcSuppLink = false;
                    }
                }
            }
        }
    }

    public void setPurInvcItemCBBind(RichSelectBooleanCheckbox purInvcItemCBBind) {
        this.purInvcItemCBBind = purInvcItemCBBind;
    }

    public RichSelectBooleanCheckbox getPurInvcItemCBBind() {
        return purInvcItemCBBind;
    }

    public void setPurInvcSuppCBBind(RichSelectBooleanCheckbox purInvcSuppCBBind) {
        this.purInvcSuppCBBind = purInvcSuppCBBind;
    }

    public RichSelectBooleanCheckbox getPurInvcSuppCBBind() {
        return purInvcSuppCBBind;
    }

    public void setPurInvcDetCBBind(RichSelectBooleanCheckbox purInvcDetCBBind) {
        this.purInvcDetCBBind = purInvcDetCBBind;
    }

    public RichSelectBooleanCheckbox getPurInvcDetCBBind() {
        return purInvcDetCBBind;
    }

    public void purInvcDetCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if ((invoiceNoLovPgBinding.getValue() != null &&
                 invoiceNoLovPgBinding.getValue().toString().length() > 0) ||
                (suppLovPgBinding.getValue() != null && suppLovPgBinding.getValue().toString().length() > 0)) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("bind value-->" + invoiceNoLovPgBinding.getValue());
                    purInvcDetLink = false;
                } else {
                     purInvcDetLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            purInvcDetCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(purInvcDetCBBind);
                             purInvcDetLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                             purInvcDetLink = false;
                        }
                    } else {
                         purInvcDetLink = false;
                    }
                }
            }
        }
    }

    public void setToTrackCBBind(RichSelectBooleanCheckbox toTrackCBBind) {
        this.toTrackCBBind = toTrackCBBind;
    }

    public RichSelectBooleanCheckbox getToTrackCBBind() {
        return toTrackCBBind;
    }

    public void setPurReqTrackCBBind(RichSelectBooleanCheckbox purReqTrackCBBind) {
        this.purReqTrackCBBind = purReqTrackCBBind;
    }

    public RichSelectBooleanCheckbox getPurReqTrackCBBind() {
        return purReqTrackCBBind;
    }
}
