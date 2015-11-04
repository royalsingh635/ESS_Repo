package procurementreportapp.view.bean;

import java.sql.SQLException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;

import oracle.jbo.domain.Timestamp;

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
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichMessage;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Date;

import org.apache.myfaces.trinidad.event.DisclosureEvent;


public class ProcurementBean {
    private RichSelectBooleanCheckbox poChkBoxBinding;
    private RichSelectBooleanCheckbox poStatChkBoxBinding;
    private RichLink poLnkBinding;
    private RichLink poStatLnkBinding;
    private RichSelectOneRadio fileTypeRadioBinding;
    private RichLink poDelSchLnkBinding;
    private RichLink poSummLnkBinding;
    private RichLink poTrackLnkBinding;
    private RichSelectBooleanCheckbox poTrackCBBinding;
    private RichSelectBooleanCheckbox poDelSchdCBBinding;
    private RichSelectBooleanCheckbox poSummCBBinding;
    private RichLink rateConLnkBinding;
    private RichSelectBooleanCheckbox rateContCBBinding;
    private RichSelectBooleanCheckbox rateConItmCBBinding;
    private RichLink rateContItmLnkBinding;
    private RichSelectBooleanCheckbox openOrderCBBinding;
    private RichLink openOrderLnkBinding;
    private RichSelectBooleanCheckbox openOrderItmCBBinding;
    private RichLink openOrderItmLnkBinding;
    private RichSelectBooleanCheckbox soSuppCBbinding;
    private RichLink soSuppLnkBindig;
    private RichSelectBooleanCheckbox soCBBinding;
    private RichLink soLnkBinding;
    private RichSelectBooleanCheckbox cpoCBBinding;
    private RichSelectBooleanCheckbox cpoSummCBBinding;
    private RichLink cpoLnkBinding;
    private RichLink cpoSummLnkBinding;
    private RichSelectBooleanCheckbox rfqAllSuppCBbinding;
    private RichSelectBooleanCheckbox rfqSelectedCBBinding;
    private RichLink rfqAllSuppLnkBinding;
    private RichLink rfqSelectedSuppLnkBinding;
    private RichSelectBooleanCheckbox quotCBBinding;
    private RichSelectBooleanCheckbox quotListCBBinding;
    private RichSelectBooleanCheckbox quotaAnaCBBinding;
    private RichSelectBooleanCheckbox quotAnaConsoCBBinding;
    private RichLink quotAnaConsoLnkBinding;
    private RichLink quotAnaLnkBinding;
    private RichLink quotListLnkBinding;
    private RichLink quotLnkBinding;
    private RichMessage msgInfoPgBinding;
    private RichSelectBooleanCheckbox downCBBinding;
    private RichLink rptNmLnkBinding;
    private RichInputListOfValues rptNmLovBinding;
    private RichInputListOfValues rfqNoLovBinding;
    private RichInputListOfValues quotNoLovCBBinding;
    private RichInputListOfValues poNoLovBinding;
    private RichInputListOfValues soLovBinding;
    private RichInputListOfValues orderPoNoLovBinding;
    private RichInputDate fromDtPgBinding;
    private RichInputDate toDtPgBinding;
    private RichInputListOfValues cpoLovBBinding;
    private RichInputListOfValues evalIdLovBinding;
    private boolean POLink = false;
    private boolean POStatLink = false;
    private boolean POSummLink = false;
    private boolean PODelLink = false;
    private boolean POTrackLink = false;
    private boolean RateContLink = false;
    private boolean RateContItmLink = false;
    private boolean OpenOrdLink = false;
    private boolean OpenOrdItmLink = false;
    private boolean CPOLink = false;
    private boolean CPOSummLink = false;
    private boolean SOLink = false;
    private boolean SOSuppLink = false;
    private boolean RFQLink = false;
    private boolean QuotLink = false;
    private boolean QuotListLink = false;
    private boolean QuotAnaLink = false;
    private boolean downLink = false;
    private boolean suppDelSchedLink = false;
    private boolean poPendQtyLink = false;
    private boolean poPendQtyItmLink = false;
    private boolean impDecLink = false;
    private boolean portTrakLink = false;
    private boolean prTrackLink = false;
    private boolean soGrpPoLink = false;
    private boolean purPriceHistLink = false;

    public void setPurPriceHistLink(boolean purPriceHistLink) {
        this.purPriceHistLink = purPriceHistLink;
    }

    public boolean isPurPriceHistLink() {
        return purPriceHistLink;
    }
    private RichSelectBooleanCheckbox soGrpPoCBBinding;
    private RichSelectBooleanCheckbox purPriceHistCBBind;

    public void setSoGrpPoLink(boolean soGrpPoLink) {
        this.soGrpPoLink = soGrpPoLink;
    }

    public boolean isSoGrpPoLink() {
        return soGrpPoLink;
    }

    public void setPrTrackLink(boolean prTrackLink) {
        this.prTrackLink = prTrackLink;
    }

    public boolean isPrTrackLink() {
        return prTrackLink;
    }
    private RichInputListOfValues idFLovBinding;
    private RichSelectBooleanCheckbox prTrackCBBinding;

    public void setImpDecLink(boolean impDecLink) {
        this.impDecLink = impDecLink;
    }

    public boolean isImpDecLink() {
        return impDecLink;
    }

    public void setPortTrakLink(boolean portTrakLink) {
        this.portTrakLink = portTrakLink;
    }

    public boolean isPortTrakLink() {
        return portTrakLink;
    }

    private RichSelectBooleanCheckbox suppDelSchedCBBinding;
    private RichInputListOfValues eoNmLovBinding;
    private RichLink poPendQtyLinkBind;
    private RichSelectBooleanCheckbox poPendQtyCBBind;
    private RichSelectBooleanCheckbox poPenQtItmCBBind;
    private RichLink poPenQtItmLinkBind;
    private RichSelectBooleanCheckbox impDecCBBinding;
    private RichSelectBooleanCheckbox portTrackCBBinding;

    public void setSuppDelSchedLink(boolean suppDelSchedLink) {
        this.suppDelSchedLink = suppDelSchedLink;
    }

    public boolean isSuppDelSchedLink() {
        return suppDelSchedLink;
    }
    public void setPoPendQtyLink(boolean poPendQtyLink) {
        this.poPendQtyLink = poPendQtyLink;
    }

    public boolean isPoPendQtyLink() {
        return poPendQtyLink;
    }
    private String dateClientId;
    private RichLink suppDelSchedLinkBinding; //To Store Client Id Of Date
    //  private Timestamp CurrDate;

    public Date getCurrDate() {
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


    public void setPOLink(boolean POLink) {
        this.POLink = POLink;
    }

    public boolean isPOLink() {
        return POLink;
    }

    public void setPOStatLink(boolean POStatLink) {
        this.POStatLink = POStatLink;
    }

    public boolean isPOStatLink() {
        return POStatLink;
    }

    public void setPOSummLink(boolean POSummLink) {
        this.POSummLink = POSummLink;
    }

    public boolean isPOSummLink() {
        return POSummLink;
    }

    public void setPODelLink(boolean PODelLink) {
        this.PODelLink = PODelLink;
    }

    public boolean isPODelLink() {
        return PODelLink;
    }

    public void setPOTrackLink(boolean POTrackLink) {
        this.POTrackLink = POTrackLink;
    }

    public boolean isPOTrackLink() {
        return POTrackLink;
    }

    public void setRateContLink(boolean RateContLink) {
        this.RateContLink = RateContLink;
    }

    public boolean isRateContLink() {
        return RateContLink;
    }

    public void setRateContItmLink(boolean RateContItmLink) {
        this.RateContItmLink = RateContItmLink;
    }

    public boolean isRateContItmLink() {
        return RateContItmLink;
    }

    public void setOpenOrdLink(boolean OpenOrdLink) {
        this.OpenOrdLink = OpenOrdLink;
    }

    public boolean isOpenOrdLink() {
        return OpenOrdLink;
    }

    public void setOpenOrdItmLink(boolean OpenOrdItmLink) {
        this.OpenOrdItmLink = OpenOrdItmLink;
    }

    public boolean isOpenOrdItmLink() {
        return OpenOrdItmLink;
    }

    public void setCPOLink(boolean CPOLink) {
        this.CPOLink = CPOLink;
    }

    public boolean isCPOLink() {
        return CPOLink;
    }

    public void setCPOSummLink(boolean CPOSummLink) {
        this.CPOSummLink = CPOSummLink;
    }

    public boolean isCPOSummLink() {
        return CPOSummLink;
    }

    public void setSOLink(boolean SOLink) {
        this.SOLink = SOLink;
    }

    public boolean isSOLink() {
        return SOLink;
    }

    public void setSOSuppLink(boolean SOSuppLink) {
        this.SOSuppLink = SOSuppLink;
    }

    public boolean isSOSuppLink() {
        return SOSuppLink;
    }

    public void setRFQLink(boolean RFQLink) {
        this.RFQLink = RFQLink;
    }

    public boolean isRFQLink() {
        return RFQLink;
    }

    public void setQuotLink(boolean QuotLink) {
        this.QuotLink = QuotLink;
    }

    public boolean isQuotLink() {
        return QuotLink;
    }

    public void setQuotListLink(boolean QuotListLink) {
        this.QuotListLink = QuotListLink;
    }

    public boolean isQuotListLink() {
        return QuotListLink;
    }

    public void setQuotAnaLink(boolean QuotAnaLink) {
        this.QuotAnaLink = QuotAnaLink;
    }

    public boolean isQuotAnaLink() {
        return QuotAnaLink;
    }

    public void setQuotAnaConLink(boolean QuotAnaConLink) {
        this.QuotAnaConLink = QuotAnaConLink;
    }

    public boolean isQuotAnaConLink() {
        return QuotAnaConLink;
    }
    private boolean QuotAnaConLink = false;

    public ProcurementBean() {
    }

    /**
     *When use click on Check Box first it will check whether user entered Date or not....????
     *
     */

    public boolean checkBoxValidation() {
        System.out.println("start date------>>>" + fromDtPgBinding.getValue());
        System.out.println("End date------>>>" + toDtPgBinding.getValue());
        if (fromDtPgBinding.getValue() == null || fromDtPgBinding.getValue().toString().length() <= 0) {
            dateClientId = fromDtPgBinding.getClientId();
            return true;
        } else if (toDtPgBinding.getValue() == null || toDtPgBinding.getValue().toString().length() <= 0) {
            dateClientId = toDtPgBinding.getClientId();
            return true;
        } else {
            return false;
        }
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

    public void generateReportAct(ActionEvent actionEvent) {
        if (poStatChkBoxBinding.isSelected()) {
            poStatLnkBinding.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poStatLnkBinding);
        } else {
            poStatLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poStatLnkBinding);
        }
        if (poChkBoxBinding.isSelected()) {
            poLnkBinding.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poLnkBinding);
        } else {
            poLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poLnkBinding);
        }
        if (poDelSchdCBBinding.isSelected()) {
            poDelSchLnkBinding.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poDelSchLnkBinding);
        } else {
            poDelSchLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poDelSchLnkBinding);
        }
        if (poTrackCBBinding.isSelected()) {
            poTrackLnkBinding.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poTrackLnkBinding);
        } else {
            poTrackLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poTrackLnkBinding);
        }
        if (poSummCBBinding.isSelected()) {
            poSummLnkBinding.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poSummLnkBinding);
        } else {
            poSummLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poSummLnkBinding);
        }
        if (rateContCBBinding.isSelected()) {
            rateConLnkBinding.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rateConLnkBinding);
        } else {
            rateConLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rateConLnkBinding);
        }
        if (rateConItmCBBinding.isSelected()) {
            rateContItmLnkBinding.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rateContItmLnkBinding);
        } else {
            rateContItmLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rateContItmLnkBinding);
        }
        if (openOrderCBBinding.isSelected()) {
            openOrderLnkBinding.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(openOrderLnkBinding);
        } else {
            openOrderLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(openOrderLnkBinding);
        }
        if (openOrderItmCBBinding.isSelected()) {
            openOrderItmLnkBinding.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(openOrderItmLnkBinding);
        } else {
            openOrderItmLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(openOrderItmLnkBinding);
        }
        if (cpoCBBinding.isSelected()) {
            cpoLnkBinding.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cpoLnkBinding);
        } else {
            cpoLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cpoLnkBinding);
        }
        if (cpoSummCBBinding.isSelected()) {
            cpoSummLnkBinding.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cpoSummLnkBinding);
        } else {
            cpoSummLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cpoSummLnkBinding);
        }
        if (soCBBinding.isSelected()) {
            soLnkBinding.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soLnkBinding);
        } else {
            soLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soLnkBinding);
        }
        if (soSuppCBbinding.isSelected()) {
            soSuppLnkBindig.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soSuppLnkBindig);
        } else {
            soSuppLnkBindig.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soSuppLnkBindig);
        }
        if (rfqAllSuppCBbinding.isSelected()) {
            rfqAllSuppLnkBinding.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rfqAllSuppLnkBinding);
        } else {
            rfqAllSuppLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rfqAllSuppLnkBinding);
        }
        if (rfqSelectedCBBinding.isSelected()) {
            rfqSelectedSuppLnkBinding.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rfqSelectedSuppLnkBinding);
        } else {
            rfqSelectedSuppLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rfqSelectedSuppLnkBinding);
        }
        if (quotCBBinding.isSelected()) {
            quotLnkBinding.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotLnkBinding);
        } else {
            quotLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotLnkBinding);
        }
        if (quotListCBBinding.isSelected()) {
            quotListLnkBinding.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotListLnkBinding);
        } else {
            quotListLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotListLnkBinding);
        }
        if (quotaAnaCBBinding.isSelected()) {
            quotAnaLnkBinding.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotAnaLnkBinding);
        } else {
            quotAnaLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotAnaLnkBinding);
        }
        if (quotAnaConsoCBBinding.isSelected()) {
            quotAnaConsoLnkBinding.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotAnaConsoLnkBinding);
        } else {
            quotAnaConsoLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotAnaConsoLnkBinding);
        }
        if (downCBBinding.getValue().equals(true)) {
            rptNmLnkBinding.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rptNmLnkBinding);
        } else {
            rptNmLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rptNmLnkBinding);
        }
        if (suppDelSchedCBBinding.isSelected()) {
            suppDelSchedLink = true;
            System.out.println("suppDel value -" + suppDelSchedCBBinding.getValue());
        } else {
            suppDelSchedLink = false;
        }
        if (poPendQtyCBBind.isSelected()) {
            poPendQtyLink = true;
        } else
            poPendQtyLink = false;
        if(poPenQtItmCBBind.isSelected()){
            poPendQtyItmLink =true;
        }else  poPendQtyItmLink =false;
        if(impDecCBBinding.isSelected()){
            impDecLink = true;
        }else impDecLink = false;
        if(portTrackCBBinding.isSelected()){
            portTrakLink = true;
        }else portTrakLink = false;
        if(prTrackCBBinding.isSelected()){
            prTrackLink = true;
        }else prTrackLink = false;
        if(soGrpPoCBBinding.isSelected()){
            soGrpPoLink = true;
        }else soGrpPoLink = false;
        
        if(purPriceHistCBBind.isSelected()){
            purPriceHistLink = true;
        }else purPriceHistLink = false;
    }

    public void setDownLink(boolean downLink) {
        this.downLink = downLink;
    }

    public boolean isDownLink() {
        return downLink;
    }

    public void setPoChkBoxBinding(RichSelectBooleanCheckbox poChkBoxBinding) {
        this.poChkBoxBinding = poChkBoxBinding;
    }

    public RichSelectBooleanCheckbox getPoChkBoxBinding() {
        return poChkBoxBinding;
    }

    public void setPoStatChkBoxBinding(RichSelectBooleanCheckbox poStatChkBoxBinding) {
        this.poStatChkBoxBinding = poStatChkBoxBinding;
    }

    public RichSelectBooleanCheckbox getPoStatChkBoxBinding() {
        return poStatChkBoxBinding;
    }

    public void setPoLnkBinding(RichLink poLnkBinding) {
        this.poLnkBinding = poLnkBinding;
    }

    public RichLink getPoLnkBinding() {
        return poLnkBinding;
    }

    public void setPoStatLnkBinding(RichLink poStatLnkBinding) {
        this.poStatLnkBinding = poStatLnkBinding;
    }

    public RichLink getPoStatLnkBinding() {
        return poStatLnkBinding;
    }

    public void setFileTypeRadioBinding(RichSelectOneRadio fileTypeRadioBinding) {
        this.fileTypeRadioBinding = fileTypeRadioBinding;
    }

    public RichSelectOneRadio getFileTypeRadioBinding() {
        return fileTypeRadioBinding;
    }

    public void setPoDelSchLnkBinding(RichLink poDelSchLnkBinding) {
        this.poDelSchLnkBinding = poDelSchLnkBinding;
    }

    public RichLink getPoDelSchLnkBinding() {
        return poDelSchLnkBinding;
    }

    public void setPoSummLnkBinding(RichLink poSummLnkBinding) {
        this.poSummLnkBinding = poSummLnkBinding;
    }

    public RichLink getPoSummLnkBinding() {
        return poSummLnkBinding;
    }

    public void setPoTrackLnkBinding(RichLink poTrackLnkBinding) {
        this.poTrackLnkBinding = poTrackLnkBinding;
    }

    public RichLink getPoTrackLnkBinding() {
        return poTrackLnkBinding;
    }

    public void setPoTrackCBBinding(RichSelectBooleanCheckbox poTrackCBBinding) {
        this.poTrackCBBinding = poTrackCBBinding;
    }

    public RichSelectBooleanCheckbox getPoTrackCBBinding() {
        return poTrackCBBinding;
    }

    public void setPoDelSchdCBBinding(RichSelectBooleanCheckbox poDelSchdCBBinding) {
        this.poDelSchdCBBinding = poDelSchdCBBinding;
    }

    public RichSelectBooleanCheckbox getPoDelSchdCBBinding() {
        return poDelSchdCBBinding;
    }

    public void setPoSummCBBinding(RichSelectBooleanCheckbox poSummCBBinding) {
        this.poSummCBBinding = poSummCBBinding;
    }

    public RichSelectBooleanCheckbox getPoSummCBBinding() {
        return poSummCBBinding;
    }

    public void setRateConLnkBinding(RichLink rateConLnkBinding) {
        this.rateConLnkBinding = rateConLnkBinding;
    }

    public RichLink getRateConLnkBinding() {
        return rateConLnkBinding;
    }

    public void setRateContCBBinding(RichSelectBooleanCheckbox rateContCBBinding) {
        this.rateContCBBinding = rateContCBBinding;
    }

    public RichSelectBooleanCheckbox getRateContCBBinding() {
        return rateContCBBinding;
    }

    public void setRateConItmCBBinding(RichSelectBooleanCheckbox rateConItmCBBinding) {
        this.rateConItmCBBinding = rateConItmCBBinding;
    }

    public RichSelectBooleanCheckbox getRateConItmCBBinding() {
        return rateConItmCBBinding;
    }

    public void setRateContItmLnkBinding(RichLink rateContItmLnkBinding) {
        this.rateContItmLnkBinding = rateContItmLnkBinding;
    }

    public RichLink getRateContItmLnkBinding() {
        return rateContItmLnkBinding;
    }

    public void setOpenOrderCBBinding(RichSelectBooleanCheckbox openOrderCBBinding) {
        this.openOrderCBBinding = openOrderCBBinding;
    }

    public RichSelectBooleanCheckbox getOpenOrderCBBinding() {
        return openOrderCBBinding;
    }

    public void setOpenOrderLnkBinding(RichLink openOrderLnkBinding) {
        this.openOrderLnkBinding = openOrderLnkBinding;
    }

    public RichLink getOpenOrderLnkBinding() {
        return openOrderLnkBinding;
    }

    public void setOpenOrderItmCBBinding(RichSelectBooleanCheckbox openOrderItmCBBinding) {
        this.openOrderItmCBBinding = openOrderItmCBBinding;
    }

    public RichSelectBooleanCheckbox getOpenOrderItmCBBinding() {
        return openOrderItmCBBinding;
    }

    public void setOpenOrderItmLnkBinding(RichLink openOrderItmLnkBinding) {
        this.openOrderItmLnkBinding = openOrderItmLnkBinding;
    }

    public RichLink getOpenOrderItmLnkBinding() {
        return openOrderItmLnkBinding;
    }

    public void setSoSuppCBbinding(RichSelectBooleanCheckbox soSuppCBbinding) {
        this.soSuppCBbinding = soSuppCBbinding;
    }

    public RichSelectBooleanCheckbox getSoSuppCBbinding() {
        return soSuppCBbinding;
    }

    public void setSoSuppLnkBindig(RichLink soSuppLnkBindig) {
        this.soSuppLnkBindig = soSuppLnkBindig;
    }

    public RichLink getSoSuppLnkBindig() {
        return soSuppLnkBindig;
    }

    public void setSoCBBinding(RichSelectBooleanCheckbox soCBBinding) {
        this.soCBBinding = soCBBinding;
    }

    public RichSelectBooleanCheckbox getSoCBBinding() {
        return soCBBinding;
    }

    public void setSoLnkBinding(RichLink soLnkBinding) {
        this.soLnkBinding = soLnkBinding;
    }

    public RichLink getSoLnkBinding() {
        return soLnkBinding;
    }

    public void setCpoCBBinding(RichSelectBooleanCheckbox cpoCBBinding) {
        this.cpoCBBinding = cpoCBBinding;
    }

    public RichSelectBooleanCheckbox getCpoCBBinding() {
        return cpoCBBinding;
    }

    public void setCpoSummCBBinding(RichSelectBooleanCheckbox cpoSummCBBinding) {
        this.cpoSummCBBinding = cpoSummCBBinding;
    }

    public RichSelectBooleanCheckbox getCpoSummCBBinding() {
        return cpoSummCBBinding;
    }

    public void setCpoLnkBinding(RichLink cpoLnkBinding) {
        this.cpoLnkBinding = cpoLnkBinding;
    }

    public RichLink getCpoLnkBinding() {
        return cpoLnkBinding;
    }

    public void setCpoSummLnkBinding(RichLink cpoSummLnkBinding) {
        this.cpoSummLnkBinding = cpoSummLnkBinding;
    }

    public RichLink getCpoSummLnkBinding() {
        return cpoSummLnkBinding;
    }

    public void setRfqAllSuppCBbinding(RichSelectBooleanCheckbox rfqAllSuppCBbinding) {
        this.rfqAllSuppCBbinding = rfqAllSuppCBbinding;
    }

    public RichSelectBooleanCheckbox getRfqAllSuppCBbinding() {
        return rfqAllSuppCBbinding;
    }

    public void setRfqSelectedCBBinding(RichSelectBooleanCheckbox rfqSelectedCBBinding) {
        this.rfqSelectedCBBinding = rfqSelectedCBBinding;
    }

    public RichSelectBooleanCheckbox getRfqSelectedCBBinding() {
        return rfqSelectedCBBinding;
    }

    public void setRfqAllSuppLnkBinding(RichLink rfqAllSuppLnkBinding) {
        this.rfqAllSuppLnkBinding = rfqAllSuppLnkBinding;
    }

    public RichLink getRfqAllSuppLnkBinding() {
        return rfqAllSuppLnkBinding;
    }

    public void setRfqSelectedSuppLnkBinding(RichLink rfqSelectedSuppLnkBinding) {
        this.rfqSelectedSuppLnkBinding = rfqSelectedSuppLnkBinding;
    }

    public RichLink getRfqSelectedSuppLnkBinding() {
        return rfqSelectedSuppLnkBinding;
    }

    public void setQuotCBBinding(RichSelectBooleanCheckbox quotCBBinding) {
        this.quotCBBinding = quotCBBinding;
    }

    public RichSelectBooleanCheckbox getQuotCBBinding() {
        return quotCBBinding;
    }

    public void setQuotListCBBinding(RichSelectBooleanCheckbox quotListCBBinding) {
        this.quotListCBBinding = quotListCBBinding;
    }

    public RichSelectBooleanCheckbox getQuotListCBBinding() {
        return quotListCBBinding;
    }

    public void setQuotaAnaCBBinding(RichSelectBooleanCheckbox quotaAnaCBBinding) {
        this.quotaAnaCBBinding = quotaAnaCBBinding;
    }

    public RichSelectBooleanCheckbox getQuotaAnaCBBinding() {
        return quotaAnaCBBinding;
    }

    public void setQuotAnaConsoCBBinding(RichSelectBooleanCheckbox quotAnaConsoCBBinding) {
        this.quotAnaConsoCBBinding = quotAnaConsoCBBinding;
    }

    public RichSelectBooleanCheckbox getQuotAnaConsoCBBinding() {
        return quotAnaConsoCBBinding;
    }

    public void setQuotAnaConsoLnkBinding(RichLink quotAnaConsoLnkBinding) {
        this.quotAnaConsoLnkBinding = quotAnaConsoLnkBinding;
    }

    public RichLink getQuotAnaConsoLnkBinding() {
        return quotAnaConsoLnkBinding;
    }

    public void setQuotAnaLnkBinding(RichLink quotAnaLnkBinding) {
        this.quotAnaLnkBinding = quotAnaLnkBinding;
    }

    public RichLink getQuotAnaLnkBinding() {
        return quotAnaLnkBinding;
    }

    public void setQuotListLnkBinding(RichLink quotListLnkBinding) {
        this.quotListLnkBinding = quotListLnkBinding;
    }

    public RichLink getQuotListLnkBinding() {
        return quotListLnkBinding;
    }

    public void setQuotLnkBinding(RichLink quotLnkBinding) {
        this.quotLnkBinding = quotLnkBinding;
    }

    public RichLink getQuotLnkBinding() {
        return quotLnkBinding;
    }

    public void setMsgInfoPgBinding(RichMessage msgInfoPgBinding) {
        this.msgInfoPgBinding = msgInfoPgBinding;
    }

    public RichMessage getMsgInfoPgBinding() {
        return msgInfoPgBinding;
    }

    public void setDownCBBinding(RichSelectBooleanCheckbox downCBBinding) {
        this.downCBBinding = downCBBinding;
    }

    public RichSelectBooleanCheckbox getDownCBBinding() {
        return downCBBinding;
    }

    public void downCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (rptNmLovBinding.getValue() != null && rptNmLovBinding.getValue().toString().length() > 0) {
                    System.out.println("inside true");
                    rptNmLnkBinding.setVisible(true);
                } else {
                    System.out.println("Inside false");
                    downCBBinding.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(downCBBinding);
                    FacesMessage message = new FacesMessage("Please Select Report Name..!!!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(rptNmLovBinding.getClientId(), message);
                }
            } else {
                downCBBinding.setValue(false);
                rptNmLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(rptNmLnkBinding);
            }
        } else {
            downCBBinding.setValue(false);
            rptNmLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rptNmLnkBinding);
        }

    }

    public void setRptNmLnkBinding(RichLink rptNmLnkBinding) {
        this.rptNmLnkBinding = rptNmLnkBinding;
    }

    public RichLink getRptNmLnkBinding() {
        return rptNmLnkBinding;
    }

    public void setRptNmLovBinding(RichInputListOfValues rptNmLovBinding) {
        this.rptNmLovBinding = rptNmLovBinding;
    }

    public RichInputListOfValues getRptNmLovBinding() {
        return rptNmLovBinding;
    }

    public void rptNmLovVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            System.out.println("Inside valueChangeEvent");
            downCBBinding.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(downCBBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(msgInfoPgBinding);
            rptNmLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(rptNmLnkBinding);
        }
        //     OperationBinding ob = getBindings().getOperationBinding("resetLov");
        //   ob.execute();
    }

    public void orgNmCBVCL(ValueChangeEvent valueChangeEvent) {
        rptNmLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rptNmLnkBinding);
        System.out.println("setVisible inside margin...");
    }

    public void RFQVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (rfqNoLovBinding.getValue() != null && rfqNoLovBinding.getValue().toString().length() > 0) {
                    System.out.println("inside true");
                    rfqAllSuppLnkBinding.setVisible(true);
                } else {
                    System.out.println("Inside false");
                    rfqAllSuppCBbinding.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(rfqAllSuppCBbinding);
                    FacesMessage message = new FacesMessage("Please Select RFQ No..!!!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(rfqNoLovBinding.getClientId(), message);
                }
            } else {
                rfqAllSuppCBbinding.setValue(false);
                rfqAllSuppLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(rfqAllSuppLnkBinding);
            }
        }
    }

    public void quotCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (quotNoLovCBBinding.getValue() != null && quotNoLovCBBinding.getValue().toString().length() > 0) {
                    System.out.println("inside true");
                    quotLnkBinding.setVisible(true);
                } else {
                    System.out.println("Inside false");
                    quotCBBinding.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(quotCBBinding);
                    FacesMessage message = new FacesMessage("Please Select Quotation No..!!!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(quotNoLovCBBinding.getClientId(), message);
                }
            } else {
                quotCBBinding.setValue(false);
                quotLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(quotLnkBinding);
            }
        } else {
            quotCBBinding.setValue(false);
            quotLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotLnkBinding);
        }
    }

    public void quotListCBVCL(ValueChangeEvent valueChangeEvent) {
        quotListLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(quotListLnkBinding);
    }

    public void quotAnaCBVCL(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (evalIdLovBinding.getValue() != null && evalIdLovBinding.getValue().toString().length() > 0) {
                    System.out.println("inside true");
                    quotAnaLnkBinding.setVisible(true);
                } else {
                    System.out.println("Inside false");
                    quotaAnaCBBinding.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(quotaAnaCBBinding);
                    FacesMessage message = new FacesMessage("Please Select Evaluation No...!!!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(evalIdLovBinding.getClientId(), message);
                }
            } else {
                quotaAnaCBBinding.setValue(false);
                quotAnaLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(quotAnaLnkBinding);
            }
        } else {
            quotaAnaCBBinding.setValue(false);
            quotAnaLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(quotAnaLnkBinding);
        }
    }

    public void quotAnaConsCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (evalIdLovBinding.getValue() != null && evalIdLovBinding.getValue().toString().length() > 0) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("In po bind value-->" + evalIdLovBinding.getValue());
                    // quotAnaConsoLnkBinding.setVisible(true); // To enable GoLInk
                    //AdfFacesContext.getCurrentInstance().addPartialTarget(quotAnaConsoLnkBinding);
                } else {
                    quotAnaConsoLnkBinding.setVisible(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(quotAnaConsoLnkBinding);
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            quotAnaConsoCBBinding.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(quotAnaConsoCBBinding);
                            quotAnaConsoLnkBinding.setVisible(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(quotAnaConsoLnkBinding); // To Disable GoLink
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            quotAnaConsoLnkBinding.setVisible(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(quotAnaConsoLnkBinding); // To enable GoLInk
                        }
                    } else {
                        quotAnaConsoLnkBinding.setVisible(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(quotAnaConsoLnkBinding); // To Disable Go Link
                    }
                }
            }
        }
    }

    public void setRfqNoLovBinding(RichInputListOfValues rfqNoLovBinding) {
        this.rfqNoLovBinding = rfqNoLovBinding;
    }

    public RichInputListOfValues getRfqNoLovBinding() {
        return rfqNoLovBinding;
    }

    public void setQuotNoLovCBBinding(RichInputListOfValues quotNoLovCBBinding) {
        this.quotNoLovCBBinding = quotNoLovCBBinding;
    }

    public RichInputListOfValues getQuotNoLovCBBinding() {
        return quotNoLovCBBinding;
    }

    public void setPoNoLovBinding(RichInputListOfValues poNoLovBinding) {
        this.poNoLovBinding = poNoLovBinding;
    }

    public RichInputListOfValues getPoNoLovBinding() {
        return poNoLovBinding;
    }

    public void cpoLovBinding(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setSoLovBinding(RichInputListOfValues soLovBinding) {
        this.soLovBinding = soLovBinding;
    }

    public RichInputListOfValues getSoLovBinding() {
        return soLovBinding;
    }

    public void setOrderPoNoLovBinding(RichInputListOfValues orderPoNoLovBinding) {
        this.orderPoNoLovBinding = orderPoNoLovBinding;
    }

    public RichInputListOfValues getOrderPoNoLovBinding() {
        return orderPoNoLovBinding;
    }

    public void poCBVCL(ValueChangeEvent valueChangeEvent) {
        //poLnkBinding.setVisible(false);
        //AdfFacesContext.getCurrentInstance().addPartialTarget(poLnkBinding);
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (poNoLovBinding.getValue() != null && poNoLovBinding.getValue().toString().length() > 0) {
                    System.out.println("inside true");
                    poLnkBinding.setVisible(true);
                } else {
                    System.out.println("Inside false");
                    poChkBoxBinding.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(poChkBoxBinding);
                    FacesMessage message = new FacesMessage("Please Select Purchase Order No..!!!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(poNoLovBinding.getClientId(), message);
                }
            } else {
                poChkBoxBinding.setValue(false);
                poLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(poLnkBinding);
            }
        } else {
            poChkBoxBinding.setValue(false);
            poLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poLnkBinding);
        }
    }

    public void poDelCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (poNoLovBinding.getValue() != null && poNoLovBinding.getValue().toString().length() > 0) {
                    System.out.println("inside true");
                    poDelSchLnkBinding.setVisible(true);
                } else {
                    System.out.println("Inside false");
                    poDelSchdCBBinding.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(poDelSchdCBBinding);
                    FacesMessage message = new FacesMessage("Please Select Purchase Order No..!!!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(poNoLovBinding.getClientId(), message);
                }
            } else {
                poDelSchdCBBinding.setValue(false);
                poDelSchLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(poDelSchLnkBinding);
            }
        } else {
            poDelSchdCBBinding.setValue(false);
            poDelSchLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poDelSchLnkBinding);
        }
    }

    public void cpoCBVCL(ValueChangeEvent valueChangeEvent) {
        // cpoLnkBinding.setVisible(false);
        // AdfFacesContext.getCurrentInstance().addPartialTarget(cpoLnkBinding);
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (cpoLovBBinding.getValue() != null && cpoLovBBinding.getValue().toString().length() > 0) {
                    System.out.println("inside true");
                    cpoLnkBinding.setVisible(true);
                } else {
                    System.out.println("Inside false");
                    cpoCBBinding.setValue("fasle");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(cpoCBBinding);
                    FacesMessage message = new FacesMessage("Please Select Cash Purchase Order...!!!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(cpoLovBBinding.getClientId(), message);
                }
            } else {
                cpoCBBinding.setValue("fasle");
                cpoLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(cpoLnkBinding);
            }
        } else {
            cpoCBBinding.setValue("fasle");
            cpoLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cpoLnkBinding);
        }
    }

    public void soCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (soLovBinding.getValue() != null && soLovBinding.getValue().toString().length() > 0) {
                    System.out.println("inside true");
                    soLnkBinding.setVisible(true);
                } else {
                    System.out.println("Inside false");
                    soCBBinding.setValue("fasle");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(soCBBinding);
                    FacesMessage message = new FacesMessage("Please Select Suggested Order No..!!!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(soLovBinding.getClientId(), message);
                }
            } else {
                soCBBinding.setValue("fasle");
                soLnkBinding.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(soLnkBinding);
            }
        } else {
            soCBBinding.setValue("fasle");
            soLnkBinding.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(soLnkBinding);
        }
    }

    public void poStatCBVCL(ValueChangeEvent valueChangeEvent) {
        //   poStatLnkBinding.setVisible(false);
        // AdfFacesContext.getCurrentInstance().addPartialTarget(poStatLnkBinding);
        if (valueChangeEvent.getNewValue() != null) {
            if (poNoLovBinding.getValue() != null && poNoLovBinding.getValue().toString().length() > 0) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("In po bind value-->" + poNoLovBinding.getValue());
                    // poStatLnkBinding.setVisible(true); // To enable GoLInk
                    //AdfFacesContext.getCurrentInstance().addPartialTarget(poStatLnkBinding);
                } else {
                    poStatLnkBinding.setVisible(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(poStatLnkBinding);
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            poStatChkBoxBinding.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(poStatChkBoxBinding);
                            poStatLnkBinding.setVisible(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(poStatLnkBinding); // To Disable GoLink
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            poStatLnkBinding.setVisible(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(poStatLnkBinding); // To enable GoLInk
                        }
                    } else {
                        poStatLnkBinding.setVisible(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(poStatLnkBinding); // To Disable Go Link
                    }
                }
            }
        }

    }

    public void poSummCBVCL(ValueChangeEvent valueChangeEvent) {
        //  poSummLnkBinding.setVisible(false);
        // AdfFacesContext.getCurrentInstance().addPartialTarget(poSummLnkBinding);
        if (valueChangeEvent.getNewValue() != null) {
            if (poNoLovBinding.getValue() != null && poNoLovBinding.getValue().toString().length() > 0) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("In po bind value-->" + poNoLovBinding.getValue());
                    // poStatLnkBinding.setVisible(true); // To enable GoLInk
                    //AdfFacesContext.getCurrentInstance().addPartialTarget(poStatLnkBinding);
                } else {
                    poSummLnkBinding.setVisible(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(poSummLnkBinding);
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            poSummCBBinding.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(poSummCBBinding);
                            poSummLnkBinding.setVisible(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(poSummLnkBinding); // To Disable GoLink
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            poSummLnkBinding.setVisible(true);
                            //  AdfFacesContext.getCurrentInstance().addPartialTarget(poSummLnkBinding);// To enable GoLInk
                        }
                    } else {
                        poSummLnkBinding.setVisible(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(poSummLnkBinding); // To Disable Go Link
                    }
                }
            }
        }
    }

    public void poTrackCBVCL(ValueChangeEvent valueChangeEvent) {
        // poTrackLnkBinding.setVisible(false);
        // AdfFacesContext.getCurrentInstance().addPartialTarget(poTrackLnkBinding);
        if (valueChangeEvent.getNewValue() != null) {
            if (poNoLovBinding.getValue() != null && poNoLovBinding.getValue().toString().length() > 0) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("In po bind value-->" + poNoLovBinding.getValue());
                    // poStatLnkBinding.setVisible(true); // To enable GoLInk
                    //AdfFacesContext.getCurrentInstance().addPartialTarget(poStatLnkBinding);
                } else {
                    poTrackLnkBinding.setVisible(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(poTrackLnkBinding);
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            poTrackCBBinding.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(poTrackCBBinding);
                            poTrackLnkBinding.setVisible(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(poTrackLnkBinding); // To Disable GoLink
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            poTrackLnkBinding.setVisible(true);
                            //  AdfFacesContext.getCurrentInstance().addPartialTarget(poTrackLnkBinding);// To enable GoLInk
                        }
                    } else {
                        poTrackLnkBinding.setVisible(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(poTrackLnkBinding); // To Disable Go Link
                    }
                }
            }
        }
    }

    public void rateContCBVCL(ValueChangeEvent valueChangeEvent) {
        rateConLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rateConLnkBinding);
    }

    public void rateContItmCBVCL(ValueChangeEvent valueChangeEvent) {
        rateConLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rateContItmLnkBinding);
    }

    public void openOrdCBVCL(ValueChangeEvent valueChangeEvent) {
        openOrderLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(openOrderLnkBinding);
    }

    public void openOrdItmCBVCL(ValueChangeEvent valueChangeEvent) {
        openOrderItmLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(openOrderItmLnkBinding);
    }

    public void cpoSummCBVCL(ValueChangeEvent valueChangeEvent) {
        //cpoSummLnkBinding.setVisible(false);
        //AdfFacesContext.getCurrentInstance().addPartialTarget(cpoSummLnkBinding);
        if (valueChangeEvent.getNewValue() != null) {
            if (cpoLovBBinding.getValue() != null && cpoLovBBinding.getValue().toString().length() > 0) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("In po bind value-->" + cpoLovBBinding.getValue());
                    // cpoLnkBinding.setVisible(true); // To enable GoLInk
                    //AdfFacesContext.getCurrentInstance().addPartialTarget(cpoLnkBinding);
                } else {
                    cpoSummLnkBinding.setVisible(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(cpoSummLnkBinding);
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            cpoSummCBBinding.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(cpoSummCBBinding);
                            cpoSummLnkBinding.setVisible(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(cpoSummLnkBinding); // To Disable GoLink
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            cpoSummLnkBinding.setVisible(true);
                            //  AdfFacesContext.getCurrentInstance().addPartialTarget(cpoSummLnkBinding);// To enable GoLInk
                        }
                    } else {
                        cpoSummLnkBinding.setVisible(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(cpoSummLnkBinding); // To Disable Go Link
                    }
                }
            }
        }
    }

    public void soSuppCBVCL(ValueChangeEvent valueChangeEvent) {
        //soSuppLnkBindig.setVisible(false);
        // AdfFacesContext.getCurrentInstance().addPartialTarget(soSuppLnkBindig);
        if (valueChangeEvent.getNewValue() != null) {
            if ((soLovBinding.getValue() != null && soLovBinding.getValue().toString().length() > 0) ||
                (orderPoNoLovBinding.getValue() != null && orderPoNoLovBinding.getValue().toString().length() > 0)) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("In po bind value-->" + soLovBinding.getValue());
                    // soLnkBinding.setVisible(true); // To enable GoLInk
                    //AdfFacesContext.getCurrentInstance().addPartialTarget(soLnkBinding);
                } else {
                    soSuppLnkBindig.setVisible(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(soSuppLnkBindig);
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            soSuppCBbinding.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(soSuppCBbinding);
                            soSuppLnkBindig.setVisible(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(soSuppLnkBindig); // To Disable GoLink
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            soSuppLnkBindig.setVisible(true);
                            //  AdfFacesContext.getCurrentInstance().addPartialTarget(soSuppLnkBindig);// To enable GoLInk
                        }
                    } else {
                        soSuppLnkBindig.setVisible(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(soSuppLnkBindig); // To Disable Go Link
                    }
                }
            }
        }

    }

    public void setFromDtPgBinding(RichInputDate fromDtPgBinding) {
        this.fromDtPgBinding = fromDtPgBinding;
    }

    public RichInputDate getFromDtPgBinding() {
        return fromDtPgBinding;
    }

    public void setToDtPgBinding(RichInputDate toDtPgBinding) {
        this.toDtPgBinding = toDtPgBinding;
    }

    public RichInputDate getToDtPgBinding() {
        return toDtPgBinding;
    }

    public void setCpoLovBBinding(RichInputListOfValues cpoLovBBinding) {
        this.cpoLovBBinding = cpoLovBBinding;
    }

    public RichInputListOfValues getCpoLovBBinding() {
        return cpoLovBBinding;
    }

    public void setEvalIdLovBinding(RichInputListOfValues evalIdLovBinding) {
        this.evalIdLovBinding = evalIdLovBinding;
    }

    public RichInputListOfValues getEvalIdLovBinding() {
        return evalIdLovBinding;
    }

    public static BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void resetActionAll(ActionEvent actionEvent) {
        OperationBinding ob = getBindings().getOperationBinding("resetAll");
        ob.execute();

        poStatLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(poStatLnkBinding);
        poLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(poLnkBinding);
        poDelSchLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(poDelSchLnkBinding);
        poTrackLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(poTrackLnkBinding);
        poSummLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(poSummLnkBinding);
        rateConLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rateConLnkBinding);
        rateContItmLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rateContItmLnkBinding);
        openOrderLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(openOrderLnkBinding);
        openOrderItmLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(openOrderItmLnkBinding);
        cpoLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cpoLnkBinding);
        cpoSummLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cpoSummLnkBinding);
        soLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(soLnkBinding);
        soSuppLnkBindig.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(soSuppLnkBindig);
        rfqAllSuppLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rfqAllSuppLnkBinding);
        rfqSelectedSuppLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rfqSelectedSuppLnkBinding);
        quotLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(quotLnkBinding);
        quotListLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(quotListLnkBinding);
        quotAnaLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(quotAnaLnkBinding);
        quotAnaConsoLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(quotAnaConsoLnkBinding);
        rptNmLnkBinding.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(rptNmLnkBinding);

    }


    public void cpoVCL(ValueChangeEvent valueChangeEvent) {

    }

    public void poNoLovVCL(ValueChangeEvent valueChangeEvent) {

    }

    public void soNoLovVCL(ValueChangeEvent valueChangeEvent) {

    }

    public void qoutNoLovVCL(ValueChangeEvent valueChangeEvent) {

    }


    public void SuppDelSchedCBVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if ((poNoLovBinding.getValue() != null && poNoLovBinding.getValue().toString().length() > 0) ||
                (eoNmLovBinding.getValue() != null && eoNmLovBinding.getValue().toString().length() > 0)) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("In po bind value-->" + poNoLovBinding.getValue());
                    suppDelSchedLink = false;
                } else {
                    suppDelSchedLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            suppDelSchedCBBinding.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(suppDelSchedCBBinding);
                            suppDelSchedLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            suppDelSchedLink = false;
                        }
                    } else {
                        suppDelSchedLink = false;
                    }
                }
            }
        }
    }

    public void setSuppDelSchedLinkBinding(RichLink suppDelSchedLinkBinding) {
        this.suppDelSchedLinkBinding = suppDelSchedLinkBinding;
    }

    public RichLink getSuppDelSchedLinkBinding() {
        return suppDelSchedLinkBinding;
    }

    public void setSuppDelSchedCBBinding(RichSelectBooleanCheckbox suppDelSchedCBBinding) {
        this.suppDelSchedCBBinding = suppDelSchedCBBinding;
    }

    public RichSelectBooleanCheckbox getSuppDelSchedCBBinding() {
        return suppDelSchedCBBinding;
    }

    public void setEoNmLovBinding(RichInputListOfValues eoNmLovBinding) {
        this.eoNmLovBinding = eoNmLovBinding;
    }

    public RichInputListOfValues getEoNmLovBinding() {
        return eoNmLovBinding;
    }

    public void setPoPendQtyLinkBind(RichLink poPendQtyLinkBind) {
        this.poPendQtyLinkBind = poPendQtyLinkBind;
    }

    public RichLink getPoPendQtyLinkBind() {
        return poPendQtyLinkBind;
    }

    public void poPendQtyCBVCL(ValueChangeEvent valueChangeEvent) {
        this.setPoPendQtyLink(false);
        if (valueChangeEvent.getNewValue() != null) {
            if (poNoLovBinding.getValue() != null && poNoLovBinding.getValue().toString().length() > 0) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("In bind value-->" + eoNmLovBinding.getValue() + "--" +
                                       poNoLovBinding.getValue());
                    poPendQtyLink = false; // To enable GoLInk
                } else {
                    poPendQtyLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            poPendQtyCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(poPendQtyCBBind);
                            poPendQtyLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            poPendQtyLink = false;
                        }
                    } else {
                        poPendQtyLink = false;
                    }
                }
            }
        }
    }

    public void setPoPendQtyCBBind(RichSelectBooleanCheckbox poPendQtyCBBind) {
        this.poPendQtyCBBind = poPendQtyCBBind;
    }

    public RichSelectBooleanCheckbox getPoPendQtyCBBind() {
        return poPendQtyCBBind;
    }

    public void setPoPenQtItmCBBind(RichSelectBooleanCheckbox poPenQtItmCBBind) {
        this.poPenQtItmCBBind = poPenQtItmCBBind;
    }

    public RichSelectBooleanCheckbox getPoPenQtItmCBBind() {
        return poPenQtItmCBBind;
    }

    public void poPenQtItmCBVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            if (poNoLovBinding.getValue() != null && poNoLovBinding.getValue().toString().length() > 0) {
                if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                    System.out.println("In bind value-->" + eoNmLovBinding.getValue() + "--" +
                                       poNoLovBinding.getValue());
                    poPendQtyItmLink = false; // To enable GoLInk
                } else {
                    poPendQtyItmLink = false;
                }
            } else {
                if (valueChangeEvent.getNewValue() != null) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        if (checkBoxValidation()) {
                            poPenQtItmCBBind.setValue(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(poPenQtItmCBBind);
                            poPendQtyItmLink = false;
                            showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                        } else {
                            poPendQtyItmLink = false;
                        }
                    } else {
                        poPendQtyItmLink = false;
                    }
                }
            }
        }
    }

    public void setPoPendQtyItmLink(boolean poPendQtyItmLink) {
        this.poPendQtyItmLink = poPendQtyItmLink;
    }

    public boolean isPoPendQtyItmLink() {
        return poPendQtyItmLink;
    }

    public void setPoPenQtItmLinkBind(RichLink poPenQtItmLinkBind) {
        this.poPenQtItmLinkBind = poPenQtItmLinkBind;
    }

    public RichLink getPoPenQtItmLinkBind() {
        return poPenQtItmLinkBind;
    }

    public void impDecCBBind(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void impDecCBVCL(ValueChangeEvent valueChangeEvent) {
       impDecLink = false;
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if ((poNoLovBinding.getValue() != null && poNoLovBinding.getValue().toString().length() > 0)
                ||(idFLovBinding.getValue() != null && idFLovBinding.getValue().toString().length() > 0)){
                    System.out.println("inside true");
                    impDecLink = false;
                } else {
                    System.out.println("Inside false");
                    impDecCBBinding.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(impDecCBBinding);
                    FacesMessage message = new FacesMessage("Please Select Purchase Order No. Or IDF NO.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(poNoLovBinding.getClientId(), message);
                    fc.addMessage(idFLovBinding.getClientId(), message);
                }
            } else {
                impDecCBBinding.setValue(false);
              impDecLink = false;
            }
        } else {
            impDecCBBinding.setValue(false);
            impDecLink = false;
        }
    }

    public void setImpDecCBBinding(RichSelectBooleanCheckbox impDecCBBinding) {
        this.impDecCBBinding = impDecCBBinding;
    }

    public RichSelectBooleanCheckbox getImpDecCBBinding() {
        return impDecCBBinding;
    }

    public void setPortTrackCBBinding(RichSelectBooleanCheckbox portTrackCBBinding) {
        this.portTrackCBBinding = portTrackCBBinding;
    }

    public RichSelectBooleanCheckbox getPortTrackCBBinding() {
        return portTrackCBBinding;
    }

    public void portTrackCBVCL(ValueChangeEvent valueChangeEvent) {
        portTrakLink = false;
            if (valueChangeEvent.getNewValue() != null) {
                if (poNoLovBinding.getValue() != null && poNoLovBinding.getValue().toString().length() > 0) {
                    if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                        System.out.println("In bind value-->" + eoNmLovBinding.getValue() + "--" +
                                           poNoLovBinding.getValue());
                        portTrakLink = false; // To enable GoLInk
                    } else {
                        portTrakLink = false;
                    }
                } else {
            
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
                if (checkBoxValidation()) {
                    portTrackCBBinding.setValue(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(portTrackCBBinding);
                    portTrakLink = false;
                    showFacesMessage("Please Select Date", "E", false, "F", dateClientId);
                } else {
                    portTrakLink = false;
                }
            } else {
                portTrakLink = false;
            }
        }
        }
            }
    }

    public void setIdFLovBinding(RichInputListOfValues idFLovBinding) {
        this.idFLovBinding = idFLovBinding;
    }

    public RichInputListOfValues getIdFLovBinding() {
        return idFLovBinding;
    }
    public void RFQDCL(DisclosureEvent disclosureEvent) {
        OperationBinding ob = (OperationBinding) getBindings().getOperationBinding("Execute");
        ob.execute();
        OperationBinding ob1 = (OperationBinding) getBindings().getOperationBinding("Execute1");
        ob1.execute();
        
    }

    public void QuotDCL(DisclosureEvent disclosureEvent) {
        OperationBinding ob = (OperationBinding) getBindings().getOperationBinding("Execute");
        ob.execute();
        OperationBinding ob1 = (OperationBinding) getBindings().getOperationBinding("Execute1");
        ob1.execute();
        
    }

    public void OrderDCL(DisclosureEvent disclosureEvent) {
        OperationBinding ob = (OperationBinding) getBindings().getOperationBinding("Execute");
        ob.execute();
        OperationBinding ob1 = (OperationBinding) getBindings().getOperationBinding("Execute1");
        ob1.execute();
    }

    public void setPrTrackCBBinding(RichSelectBooleanCheckbox prTrackCBBinding) {
        this.prTrackCBBinding = prTrackCBBinding;
    }

    public RichSelectBooleanCheckbox getPrTrackCBBinding() {
        return prTrackCBBinding;
    }

    public void setSoGrpPoCBBinding(RichSelectBooleanCheckbox soGrpPoCBBinding) {
        this.soGrpPoCBBinding = soGrpPoCBBinding;
    }

    public RichSelectBooleanCheckbox getSoGrpPoCBBinding() {
        return soGrpPoCBBinding;
    }

    public void soGrpPoVCL(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null){
            soGrpPoLink= false;
        }
    }

    public void setPurPriceHistCBBind(RichSelectBooleanCheckbox purPriceHistCBBind) {
        this.purPriceHistCBBind = purPriceHistCBBind;
    }

    public RichSelectBooleanCheckbox getPurPriceHistCBBind() {
        return purPriceHistCBBind;
    }
}
