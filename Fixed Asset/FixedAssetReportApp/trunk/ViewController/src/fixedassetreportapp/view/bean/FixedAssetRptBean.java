package fixedassetreportapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import java.util.HashMap;
import java.util.Map;

import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.event.DisclosureEvent;

public class FixedAssetRptBean {
    private boolean faRegLink = false;
    private boolean faRegSummLink = false;
    private Boolean itDepLink = false;
    private Boolean cotDepLink = false;
    private boolean mode = false;
    private boolean faRegDetCOLink = false;
    private boolean faRegDetITLink = false;

    public void setFaRegDetCOLink(boolean faRegDetCOLink) {
        this.faRegDetCOLink = faRegDetCOLink;
    }

    public boolean isFaRegDetCOLink() {
        return faRegDetCOLink;
    }

    public void setFaRegDetITLink(boolean faRegDetITLink) {
        this.faRegDetITLink = faRegDetITLink;
    }

    public boolean isFaRegDetITLink() {
        return faRegDetITLink;
    }
    private RichSelectBooleanCheckbox faRegDetCOCBBind;
    private RichSelectBooleanCheckbox faRegDetITCBBind;

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public boolean isMode() {
        return mode;
    }
    private RichInputListOfValues depIdLovBinding;
    private RichInputListOfValues faIdLovBinding;

    public void setFaRegLink(boolean faRegLink) {
        this.faRegLink = faRegLink;
    }

    public boolean isFaRegLink() {
        return faRegLink;
    }

    public void setFaRegSummLink(boolean faRegSummLink) {
        this.faRegSummLink = faRegSummLink;
    }

    public boolean isFaRegSummLink() {
        return faRegSummLink;
    }

    public void setItDepLink(Boolean itDepLink) {
        this.itDepLink = itDepLink;
    }

    public Boolean getItDepLink() {
        return itDepLink;
    }

    public void setCotDepLink(Boolean cotDepLink) {
        this.cotDepLink = cotDepLink;
    }

    public Boolean getCotDepLink() {
        return cotDepLink;
    }
    private RichSelectBooleanCheckbox faRegCBBinding;
    private RichSelectBooleanCheckbox faRegSummCBinding;
    private RichSelectBooleanCheckbox itDepCBBinding;
    private RichSelectBooleanCheckbox cotDepCBBinding;

    public FixedAssetRptBean() {
    }

    public void generateRptAE(ActionEvent actionEvent) {
        if (faRegCBBinding.isSelected()) {
            this.setFaRegLink(true);
        } else
            this.setFaRegLink(false);

        if (faRegSummCBinding.isSelected()) {
            this.setFaRegSummLink(true);
        } else
            this.setFaRegSummLink(false);
        if (itDepCBBinding.isSelected()) {
            this.setItDepLink(true);
        } else
            this.setItDepLink(false);
        if (cotDepCBBinding.isSelected()) {
            this.setCotDepLink(true);
        } else
            this.setCotDepLink(false);
        if (faRegDetCOCBBind.isSelected()) {
            this.setFaRegDetCOLink(true);
        } else
            this.setFaRegDetCOLink(false);

        if (faRegDetITCBBind.isSelected()) {
            this.setFaRegDetITLink(true);
        } else
            this.setFaRegDetITLink(false);
    }

    public void setFaRegCBBinding(RichSelectBooleanCheckbox faRegCBBinding) {
        this.faRegCBBinding = faRegCBBinding;
    }

    public RichSelectBooleanCheckbox getFaRegCBBinding() {
        return faRegCBBinding;
    }

    public void setFaRegSummCBinding(RichSelectBooleanCheckbox faRegSummCBinding) {
        this.faRegSummCBinding = faRegSummCBinding;
    }

    public RichSelectBooleanCheckbox getFaRegSummCBinding() {
        return faRegSummCBinding;
    }

    public void setItDepCBBinding(RichSelectBooleanCheckbox itDepCBBinding) {
        this.itDepCBBinding = itDepCBBinding;
    }

    public RichSelectBooleanCheckbox getItDepCBBinding() {
        return itDepCBBinding;
    }

    public void setCotDepCBBinding(RichSelectBooleanCheckbox cotDepCBBinding) {
        this.cotDepCBBinding = cotDepCBBinding;
    }

    public RichSelectBooleanCheckbox getCotDepCBBinding() {
        return cotDepCBBinding;
    }

    public void fixedAssetDCL(DisclosureEvent disclosureEvent) {
        mode = false;
        ADFBeanUtils.findOperation("Execute").execute();
        ADFBeanUtils.findOperation("ExecuteTemp").execute();
        ADFBeanUtils.getOperationBinding("setDefaultDate").execute();
        System.out.println("executed inside the fadcl");
    }

    public void DepDCL(DisclosureEvent disclosureEvent) {
        mode = false;
        ADFBeanUtils.findOperation("Execute").execute();
        ADFBeanUtils.findOperation("ExecuteTemp").execute();
        ADFBeanUtils.getOperationBinding("setDefaultDate").execute();
        System.out.println("executed inside the depcl");
    }


    /**
     *Will Set Date Fields To null or Blank
     */
    public void setSearchFieldNull(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().length() > 0) {
                mode = true;
            } else {
                mode = false;
            }
            ADFBeanUtils.findOperation("ExecuteTemp").execute();
        } else {
            mode = false;
        }

    }


    public void depIdLovVCL(ValueChangeEvent valueChangeEvent) {

        //    setSearchFieldNull(valueChangeEvent);

    }

    public void setDepIdLovBinding(RichInputListOfValues depIdLovBinding) {
        this.depIdLovBinding = depIdLovBinding;
    }

    public RichInputListOfValues getDepIdLovBinding() {
        return depIdLovBinding;
    }

    public void faIdLOVVCL(ValueChangeEvent valueChangeEvent) {
        //  setSearchFieldNull(valueChangeEvent);

    }

    public void setFaIdLovBinding(RichInputListOfValues faIdLovBinding) {
        this.faIdLovBinding = faIdLovBinding;
    }

    public RichInputListOfValues getFaIdLovBinding() {
        return faIdLovBinding;
    }

    public void setFaRegDetCOCBBind(RichSelectBooleanCheckbox faRegDetCOCBBind) {
        this.faRegDetCOCBBind = faRegDetCOCBBind;
    }

    public RichSelectBooleanCheckbox getFaRegDetCOCBBind() {
        return faRegDetCOCBBind;
    }

    public void setFaRegDetITCBBind(RichSelectBooleanCheckbox faRegDetITCBBind) {
        this.faRegDetITCBBind = faRegDetITCBBind;
    }

    public RichSelectBooleanCheckbox getFaRegDetITCBBind() {
        return faRegDetITCBBind;
    }

    /**
     * For implementing Security.
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
