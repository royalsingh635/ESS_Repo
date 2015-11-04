package trnpreportapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

public class TRNPReportBean {
    private RichSelectBooleanCheckbox fuelConsDetCBBind;
    private RichSelectBooleanCheckbox fuelConsDetVehCBBind;
    private RichSelectBooleanCheckbox fuelRcptPendCBBind;
    private RichSelectBooleanCheckbox fuelAnalysisCBBind;
    private RichSelectBooleanCheckbox petrolStatnAnalysisCBBind;
    private RichSelectBooleanCheckbox tripAnalysisCBBind;
    private Boolean fuelConsDetLink = false;
    private Boolean fuelConsDetVehLink  = false;
    private boolean fuelRcptPendLink = false;
    private boolean fuelAnalysisLink = false;
    private boolean petrolStatnAnalysisLink = false;
    private boolean tripAnalysisLin = false;

    public void setFuelConsDetLink(Boolean fuelConsDetLink) {
        this.fuelConsDetLink = fuelConsDetLink;
    }

    public Boolean getFuelConsDetLink() {
        return fuelConsDetLink;
    }

    public void setFuelConsDetVehLink(Boolean fuelConsDetVehLink) {
        this.fuelConsDetVehLink = fuelConsDetVehLink;
    }

    public Boolean getFuelConsDetVehLink() {
        return fuelConsDetVehLink;
    }

    public void setFuelRcptPendLink(boolean fuelRcptPendLink) {
        this.fuelRcptPendLink = fuelRcptPendLink;
    }

    public boolean isFuelRcptPendLink() {
        return fuelRcptPendLink;
    }

    public void setFuelAnalysisLink(boolean fuelAnalysisLink) {
        this.fuelAnalysisLink = fuelAnalysisLink;
    }

    public boolean isFuelAnalysisLink() {
        return fuelAnalysisLink;
    }

    public void setPetrolStatnAnalysisLink(boolean petrolStatnAnalysisLink) {
        this.petrolStatnAnalysisLink = petrolStatnAnalysisLink;
    }

    public boolean isPetrolStatnAnalysisLink() {
        return petrolStatnAnalysisLink;
    }

    public void setTripAnalysisLin(boolean tripAnalysisLin) {
        this.tripAnalysisLin = tripAnalysisLin;
    }

    public boolean isTripAnalysisLin() {
        return tripAnalysisLin;
    }

    public TRNPReportBean() {
    }

    public void generateReportAE(ActionEvent actionEvent) {
        if(fuelConsDetCBBind.isSelected()){
            this.setFuelConsDetLink(true);
        }else this.setFuelConsDetLink(false);
        if(fuelConsDetVehCBBind.isSelected()){
            this.setFuelConsDetVehLink(true);
        }else this.setFuelConsDetVehLink(false);
        if(fuelAnalysisCBBind.isSelected()){
            this.setFuelAnalysisLink(true);
        }else this.setFuelAnalysisLink(false);
        if(fuelRcptPendCBBind.isSelected()){
            this.setFuelRcptPendLink(true);
        }else this.setFuelRcptPendLink(false);
        if(petrolStatnAnalysisCBBind.isSelected()){
            this.setPetrolStatnAnalysisLink(true);
        }else this.setPetrolStatnAnalysisLink(false);
        if(tripAnalysisCBBind.isSelected()){
            this.setTripAnalysisLin(true);
        }else this.setTripAnalysisLin(false);
    }

    public void setFuelConsDetCBBind(RichSelectBooleanCheckbox fuelConsDetCBBind) {
        this.fuelConsDetCBBind = fuelConsDetCBBind;
    }

    public RichSelectBooleanCheckbox getFuelConsDetCBBind() {
        return fuelConsDetCBBind;
    }

    public void setFuelConsDetVehCBBind(RichSelectBooleanCheckbox fuelConsDetVehCBBind) {
        this.fuelConsDetVehCBBind = fuelConsDetVehCBBind;
    }

    public RichSelectBooleanCheckbox getFuelConsDetVehCBBind() {
        return fuelConsDetVehCBBind;
    }

    public void setFuelRcptPendCBBind(RichSelectBooleanCheckbox fuelRcptPendCBBind) {
        this.fuelRcptPendCBBind = fuelRcptPendCBBind;
    }

    public RichSelectBooleanCheckbox getFuelRcptPendCBBind() {
        return fuelRcptPendCBBind;
    }

    public void setFuelAnalysisCBBind(RichSelectBooleanCheckbox fuelAnalysisCBBind) {
        this.fuelAnalysisCBBind = fuelAnalysisCBBind;
    }

    public RichSelectBooleanCheckbox getFuelAnalysisCBBind() {
        return fuelAnalysisCBBind;
    }

    public void setPetrolStatnAnalysisCBBind(RichSelectBooleanCheckbox petrolStatnAnalysisCBBind) {
        this.petrolStatnAnalysisCBBind = petrolStatnAnalysisCBBind;
    }

    public RichSelectBooleanCheckbox getPetrolStatnAnalysisCBBind() {
        return petrolStatnAnalysisCBBind;
    }

    public void setTripAnalysisCBBind(RichSelectBooleanCheckbox tripAnalysisCBBind) {
        this.tripAnalysisCBBind = tripAnalysisCBBind;
    }

    public RichSelectBooleanCheckbox getTripAnalysisCBBind() {
        return tripAnalysisCBBind;
    }

    public void resetAE(ActionEvent actionEvent) {
      ADFBeanUtils.getOperationBinding("ExecuteRptCB").execute();
      ADFBeanUtils.getOperationBinding("ExecuteRptTrans").execute();
    }
}
