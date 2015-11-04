package screportapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import java.util.HashMap;
import java.util.Map;

import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.event.DisclosureEvent;

public class SCReportBean {
    private RichInputListOfValues ordNoLovBinding;
    private RichSelectBooleanCheckbox scORegCBBind;
    private boolean scoRegLink = false;
    private boolean scoRegSummLink = false;
    private boolean scIssuRegSCLink = false;
    private boolean scIssuRegSummLink = false;
    private boolean scInvRegLink = false;
    private boolean scInvRegSummLink = false;
    private boolean scInvPrintLink = false;
    private boolean scRcptRegScLink = false;
    private boolean scRcptRegSummLink = false;
    private boolean scIssuPrintLink=false;

    private RichSelectBooleanCheckbox invDetCBBind;
    private RichSelectBooleanCheckbox invRegCBBind;
    private RichSelectBooleanCheckbox despRegCBBind;
    private RichSelectBooleanCheckbox rcptRegSCCBBind;
    private RichSelectBooleanCheckbox stockRegCBTrans;
    private RichSelectBooleanCheckbox scoSummCBBind;
    private RichSelectBooleanCheckbox scInvPrintCVBind;

    public void setScoRegLink(boolean scoRegLink) {
        this.scoRegLink = scoRegLink;
    }

    public boolean isScoRegLink() {
        return scoRegLink;
    }

    public void setScoRegSummLink(boolean scoRegSummLink) {
        this.scoRegSummLink = scoRegSummLink;
    }

    public boolean isScoRegSummLink() {
        return scoRegSummLink;
    }

    public void setScIssuRegSCLink(boolean scIssuRegSCLink) {
        this.scIssuRegSCLink = scIssuRegSCLink;
    }

    public boolean isScIssuRegSCLink() {
        return scIssuRegSCLink;
    }

    public void setScIssuRegSummLink(boolean scIssuRegSummLink) {
        this.scIssuRegSummLink = scIssuRegSummLink;
    }

    public boolean isScIssuRegSummLink() {
        return scIssuRegSummLink;
    }

    public void setScInvRegLink(boolean scInvRegLink) {
        this.scInvRegLink = scInvRegLink;
    }

    public boolean isScInvRegLink() {
        return scInvRegLink;
    }

    public void setScInvRegSummLink(boolean scInvRegSummLink) {
        this.scInvRegSummLink = scInvRegSummLink;
    }

    public boolean isScInvRegSummLink() {
        return scInvRegSummLink;
    }

    public void setScInvPrintLink(boolean scInvPrintLink) {
        this.scInvPrintLink = scInvPrintLink;
    }

    public boolean isScInvPrintLink() {
        return scInvPrintLink;
    }

    public void setScRcptRegScLink(boolean scRcptRegScLink) {
        this.scRcptRegScLink = scRcptRegScLink;
    }

    public boolean isScRcptRegScLink() {
        return scRcptRegScLink;
    }

    public void setScRcptRegSummLink(boolean scRcptRegSummLink) {
        this.scRcptRegSummLink = scRcptRegSummLink;
    }

    public boolean isScRcptRegSummLink() {
        return scRcptRegSummLink;
    }

    public void setScIssuPrintLink(boolean scIssuPrintLink) {
        this.scIssuPrintLink = scIssuPrintLink;
    }

    public boolean isScIssuPrintLink() {
        return scIssuPrintLink;
    }

    public SCReportBean() {
    }

    public void generateReportAE(ActionEvent actionEvent) {
        if(scORegCBBind.isSelected()){
            this.setScoRegLink(true);
        }else this.setScoRegLink(false);
        
        if(scoSummCBBind.isSelected()){
            this.setScoRegSummLink(true);
        }else    this.setScoRegSummLink(false);
        
        if(despRegCBBind.isSelected()){
            scIssuRegSCLink= true;
        }else  scIssuRegSCLink= false;
        
        if(rcptRegSCCBBind.isSelected()){
            scRcptRegScLink = true;
        }else scRcptRegScLink = false;
        if(stockRegCBTrans.isSelected()){
            scRcptRegSummLink=true;
        }else scRcptRegSummLink = true;
       
        
        if(invRegCBBind.isSelected()){
            scInvRegLink = true;
        }else scInvRegLink = false;
        
        if(scInvPrintCVBind.isSelected()){
            scInvPrintLink = true;
        }else scInvPrintLink = false;
        
    }

    public void setOrdNoLovBinding(RichInputListOfValues ordNoLovBinding) {
        this.ordNoLovBinding = ordNoLovBinding;
    }

    public RichInputListOfValues getOrdNoLovBinding() {
        return ordNoLovBinding;
    }

    public void ordrNoLovVCL(ValueChangeEvent valueChangeEvent) {
    
    }

    public void setScORegCBBind(RichSelectBooleanCheckbox scORegCBBind) {
        this.scORegCBBind = scORegCBBind;
    }

    public RichSelectBooleanCheckbox getScORegCBBind() {
        return scORegCBBind;
    }

    public void setInvDetCBBind(RichSelectBooleanCheckbox invDetCBBind) {
        this.invDetCBBind = invDetCBBind;
    }

    public RichSelectBooleanCheckbox getInvDetCBBind() {
        return invDetCBBind;
    }

    public void setInvRegCBBind(RichSelectBooleanCheckbox invRegCBBind) {
        this.invRegCBBind = invRegCBBind;
    }

    public RichSelectBooleanCheckbox getInvRegCBBind() {
        return invRegCBBind;
    }

    public void setDespRegCBBind(RichSelectBooleanCheckbox despRegCBBind) {
        this.despRegCBBind = despRegCBBind;
    }

    public RichSelectBooleanCheckbox getDespRegCBBind() {
        return despRegCBBind;
    }

    public void setRcptRegSCCBBind(RichSelectBooleanCheckbox rcptRegSCCBBind) {
        this.rcptRegSCCBBind = rcptRegSCCBBind;
    }

    public RichSelectBooleanCheckbox getRcptRegSCCBBind() {
        return rcptRegSCCBBind;
    }

    public void setStockRegCBTrans(RichSelectBooleanCheckbox stockRegCBTrans) {
        this.stockRegCBTrans = stockRegCBTrans;
    }

    public RichSelectBooleanCheckbox getStockRegCBTrans() {
        return stockRegCBTrans;
    }

    public void setScoSummCBBind(RichSelectBooleanCheckbox scoSummCBBind) {
        this.scoSummCBBind = scoSummCBBind;
    }

    public RichSelectBooleanCheckbox getScoSummCBBind() {
        return scoSummCBBind;
    }

    public void orderDCL(DisclosureEvent disclosureEvent) {
        ADFBeanUtils.getOperationBinding("ExecuteRptCB").execute();
    }

    public void issueDCL(DisclosureEvent disclosureEvent) {
        ADFBeanUtils.getOperationBinding("ExecuteRptCB").execute();
    }

    public void rcptDCL(DisclosureEvent disclosureEvent) {
        ADFBeanUtils.getOperationBinding("ExecuteRptCB").execute();
    }

    public void invoiceDCL(DisclosureEvent disclosureEvent) {
        ADFBeanUtils.getOperationBinding("ExecuteRptCB").execute();
    }

    public void resetAE(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("ExecuteRptTrans").execute();
          ADFBeanUtils.getOperationBinding("ExecuteRptCB").execute();
    }

    public void setScInvPrintCVBind(RichSelectBooleanCheckbox scInvPrintCVBind) {
        this.scInvPrintCVBind = scInvPrintCVBind;
    }

    public RichSelectBooleanCheckbox getScInvPrintCVBind() {
        return scInvPrintCVBind;
    }
    /**
     * For adding User wise Security...
     */
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
