package subcontractreceiptapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.bean.StaticValue;
import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.DisclosureEvent;

public class SCReceiptCreateBean {
    private RichPopup taxPop;
    private RichPopup lotPop;
    private RichPopup binBind;
    private RichLink addBin;
    private Boolean isLot = true;
    private String mode = "C";
    private String wfId = "W000";
    private String wfMode = "F";
    private RichSelectOneChoice globalTaxBind;
    private RichInputListOfValues entityBind;
    private RichInputListOfValues wareHouseBind;


    private String isRls = "N";
    private RichInputListOfValues currBind;
    private RichSelectOneChoice projectId; // This will hold tree flag: N-->No || I ----> Release Process Initited || R----->  released

    public void setIsRls(String isRls) {
        this.isRls = isRls;
    }

    public String getIsRls() {
        return isRls;
    }

    public void setWfId(String wfId) {
        this.wfId = wfId;
    }

    public String getWfId() {
        return wfId;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }


    public SCReceiptCreateBean() {

        Map paramMap = RequestContext.getCurrentInstance().getPageFlowScope();
        this.mode = (String) paramMap.get("GLBL_MODE");

    }


    public void setIsLot(Boolean isLot) {
        this.isLot = isLot;
    }

    public Boolean getIsLot() {
        return isLot;
    }

    public void showError(String clientId, String header, String message) {
        FacesContext.getCurrentInstance().addMessage(clientId,
                                                     new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      header == null ? "Error" : header, message));
    }

    public void addDcoument(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("createDocument").execute();
        this.setMode("C");
        this.setIsLot(true);
    }

    public void editDoc(ActionEvent actionEvent) {

        OperationBinding op = ADFBeanUtils.findOperation("getWfId");
        op.execute();
        Object wf = op.getResult();
        if (wf == null) {
            ADFModelUtils.showFormattedFacesMessage("Set Up Error", "Work FLow is not defined for this document",
                                                    FacesMessage.SEVERITY_ERROR);
            return;
        }
        this.setWfId((String) wf);

        op = ADFBeanUtils.findOperation("getCurUser");
        op.execute();

        Integer result = (Integer) op.getResult();

        if (result == -1 || result.equals(EbizParams.GLBL_APP_USR())) {
            this.setMode("C");
        } else {

            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                                                          "Document is pending at other user"));
        }


    }

    public void save(ActionEvent actionEvent) {
        // Validate data
        String ret = this.validateHeader();
        String res = null;
        if (this.getIsRls().equalsIgnoreCase("I")) {
            OperationBinding lot = ADFBeanUtils.getOperationBinding("validateLot");
            lot.execute();
            res = lot.getResult().toString();
            if (res.equalsIgnoreCase("N"))
                return;
        }


        if (ret.equals("N"))
            return;


        // WorkFlowRelated Function

        OperationBinding op = ADFBeanUtils.findOperation("getWfId");
        op.execute();
        Object wf = op.getResult();
        if (wf == null) {
            ADFModelUtils.showFormattedFacesMessage("Set Up Error", "Work FLow is not defined for this document",
                                                    FacesMessage.SEVERITY_ERROR);
            return;
        }
        this.setWfId((String) wf);

        op = ADFBeanUtils.findOperation("getCurUser");
        op.execute();

        Integer result = (Integer) op.getResult();

        if (result == -1) {
            ADFBeanUtils.findOperation("insertToWF").execute();
        }


        // update docDisp
        if (!this.getIsRls().equalsIgnoreCase("I")) {
            OperationBinding dispDoc = ADFBeanUtils.findOperation("updateDocDisp");
            dispDoc.execute();
        }

        // Update stock

        ADFBeanUtils.findOperation("backUpdateOrder").execute();


        // Cost Center

        ADFBeanUtils.findOperation("sendDateFromTempCcToRcptCc").execute();


        // save Data

        if (this.getIsRls().equalsIgnoreCase("I")) {
            ADFBeanUtils.findOperation("saveRls").execute();
        }


        ADFBeanUtils.findOperation("Commit").execute();

        ADFModelUtils.showFormattedFacesMessage("Successfull", "Record Save Successfully", FacesMessage.SEVERITY_INFO);

        this.setMode("V");

        // Only if Release has been started
        if (this.getIsRls().equalsIgnoreCase("I"))
            this.setIsRls("R");
    }

    public String cancel() {
        ADFBeanUtils.findOperation("Rollback").execute();
        this.setMode("V");
        if (this.getIsRls().equalsIgnoreCase("I"))
            this.setIsRls("N");
        return "Back";
    }

    public String saveAndForward() {

        //validate Forward

        System.out.println("In save and Forward");

        OperationBinding dVal = ADFBeanUtils.getOperationBinding("validateSaveAndForward");
        dVal.execute();
        Object obj = dVal.getResult();


        if (obj == null) {
            this.showError(null, null, "Error while validating ");
            return null;
        }
        if (obj.toString().equals("N"))
            return null;

        System.out.println("After validating");

        /// Insert wf function

        OperationBinding op = ADFBeanUtils.findOperation("getWfId");
        op.execute();
        Object wf = op.getResult();
        if (wf == null) {
            ADFModelUtils.showFormattedFacesMessage("Set Up Error", "Work FLow is not defined for this document",
                                                    FacesMessage.SEVERITY_ERROR);
            return null;
        }
        this.setWfId((String) wf);

        op = ADFBeanUtils.findOperation("getCurUser");
        op.execute();

        Integer result = (Integer) op.getResult();

        if (result == -1) {
            ADFBeanUtils.findOperation("insertToWF").execute();
        }


        OperationBinding dispDoc = ADFBeanUtils.findOperation("updateDocDisp");
        dispDoc.execute();


        ADFBeanUtils.findOperation("sendDateFromTempCcToRcptCc").execute();

        // Update stock

        ADFBeanUtils.findOperation("backUpdateOrder").execute();


        ADFBeanUtils.findOperation("Commit").execute();
        this.setMode("V");

        return "WF";
    }

    public void taxPopCancelListener(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
    }

    public void setTaxPop(RichPopup taxPop) {
        this.taxPop = taxPop;
    }

    public RichPopup getTaxPop() {
        return taxPop;
    }

    public void applyTax(ActionEvent actionEvent) {
        OperationBinding op = ADFBeanUtils.findOperation("applyDefaultTax");
        op.getParamsMap().put("flag", "I");
        op.execute();
        ADFBeanUtils.showPopup(this.getTaxPop(), true);
    }

    public void taxDiaListener(DialogEvent dialogEvent) {
        // Add event code here...
    }

    public void taxRuleVCL(ValueChangeEvent valueChangeEvent) {
        //  valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        OperationBinding op = ADFBeanUtils.findOperation("applyTaxForItem");
        op.getParamsMap().put("taxRule", valueChangeEvent.getNewValue());
        op.execute();
    }

    public void ocTabDisclosure(DisclosureEvent disclosureEvent) {
        ADFBeanUtils.findOperation("filterOC").execute();
    }

    public void setLotPop(RichPopup lotPop) {
        this.lotPop = lotPop;
    }

    public RichPopup getLotPop() {
        return lotPop;
    }

    public void addLot(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("CreateLot").execute();
        ADFBeanUtils.showPopup(this.getLotPop(), true);
    }

    public void editLot(ActionEvent actionEvent) {
        ADFBeanUtils.showPopup(this.getLotPop(), true);
    }

    public void addBin(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("CreateBin").execute();
        ADFBeanUtils.showPopup(this.getBinBind(), true);
    }

    public void editBin(ActionEvent actionEvent) {
        ADFBeanUtils.showPopup(this.getBinBind(), true);
    }

    public void setBinBind(RichPopup binBind) {
        this.binBind = binBind;
    }

    public RichPopup getBinBind() {
        return binBind;
    }

    public void setAddBin(RichLink addBin) {
        this.addBin = addBin;
    }

    public RichLink getAddBin() {
        return addBin;
    }

    public String getLotId() {
        OperationBinding op = ADFBeanUtils.getOperationBinding("getLotId");
        op.execute();
        String lotId = op.getResult().toString();
        System.out.println("Lot id is " + lotId);
        return lotId;
    }

    public String getLotAmount() {
        OperationBinding op = ADFBeanUtils.getOperationBinding("getLotAmt");
        op.execute();
        Number lotAmt = (Number) op.getResult();
        lotAmt = lotAmt != null ? lotAmt : StaticValue.NUMBER_ZERO;
        return lotAmt.toString();
    }

    public void showLot(ActionEvent actionEvent) {
        this.setIsLot(true);
    }

    public void viewBin(ActionEvent actionEvent) {
        this.setIsLot(false);
    }

    public void totQtyVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        OperationBinding op = ADFBeanUtils.findOperation("refreshItemQty");
        op.execute();
    }

    public void changeSpecificAmtOC(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        ADFBeanUtils.findOperation("refreshOC").execute();
    }

    public void changeCurOc(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        ADFBeanUtils.findOperation("refreshOC").execute();
    }

    public void ApplyTaxForAll(ActionEvent actionEvent) {
        Object o = this.getGlobalTaxBind().getValue();
        if (o == null || o.toString().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(this.getGlobalTaxBind().getClientId(),
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                                                          "Please select a tax rule"));
        } else {
            OperationBinding op = ADFBeanUtils.findOperation("applyTaxForAllItem");
            op.getParamsMap().put("taxRule", (Integer) o);
            op.execute();
        }
    }

    public void setGlobalTaxBind(RichSelectOneChoice globalTaxBind) {
        this.globalTaxBind = globalTaxBind;
    }

    public RichSelectOneChoice getGlobalTaxBind() {
        return globalTaxBind;
    }

    public void validateTotqty(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object == null || object.toString().trim().length() == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                                          "This can not be blank"));
        } else {
            Number value = (Number) object;
            if (value.compareTo(new Number(0)) == 1) {
                String message = this.validate("TotRcptQty", value);
                if (message.equalsIgnoreCase("Y")) {

                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", message));
                }
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                                              "This can not be zero and negative"));
            }
        }

    }

    public void validateRjctQty(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object == null || object.toString().trim().length() == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                                          "This can not be blank"));
        } else {
            Number value = (Number) object;
            if (value.compareTo(new Number(0)) != -1) {
                String message = this.validate("RejQty", value);
                if (message.equalsIgnoreCase("Y")) {

                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", message));
                }
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                                              "This can not be negative"));
            }
        }

    }

    public void validateRwkQty(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object == null || object.toString().trim().length() == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                                          "This can not be blank"));
        } else {
            Number value = (Number) object;
            if (value.compareTo(new Number(0)) != -1) {
                String message = this.validate("RwkQty", value);
                if (message.equalsIgnoreCase("Y")) {

                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", message));
                }
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                                              "This can not be negative"));
            }
        }

    }

    public void setAttrsVal(String iter, String attrs, Object val) {

        if (iter != null && attrs != null) {
            DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);

            if (dcIter.getCurrentRow() != null)
                dcIter.getCurrentRow().setAttribute(attrs, val);

        }
    }


    public Object getAttrsVal(String iter, String attrs) {
        Object val = null;
        if (iter != null && attrs != null) {
            DCIteratorBinding dcIter = ADFBeanUtils.findIterator(iter);

            if (dcIter.getCurrentRow() != null)
                val = dcIter.getCurrentRow().getAttribute(attrs);

        }
        return val;
    }

    private String validate(String att, Number value) {
        String message = "Y";
        Object penAmt = getAttrsVal("MmScRcptItm1Iterator", "PendQty");
        if (penAmt == null) {
            message = "Pending amount can not be 0 or empty";
        } else {
            Number tot = (Number) penAmt;

            Number totRcpt = (Number) getAttrsVal("MmScRcptItm1Iterator", "TotRcptQty");
            Number rej = (Number) getAttrsVal("MmScRcptItm1Iterator", "RejQty");
            Number rwk = (Number) getAttrsVal("MmScRcptItm1Iterator", "RwkQty");
            switch (att) {

            case "RejQty":
                rej = value;
                break;
            case "TotRcptQty":
                totRcpt = value;
                break;
            case "RwkQty":
                rwk = value;
                break;
            }

            if (tot.compareTo(totRcpt) < 0) {
                message = "Total Receipt quantity can not be more than Pending quantity";
                return message;
            }

            if (((rej.add(rwk)).compareTo(totRcpt)) > 0) {
                message = "Sum of rejected,rewokable quantites can not be more than total receipt quantity";
                return message;
            }


        }
        return message;
    }

    public void rejQtVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        OperationBinding op = ADFBeanUtils.findOperation("refreshItemQty");
        op.execute();
    }

    public void rwkQtyVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        OperationBinding op = ADFBeanUtils.findOperation("refreshItemQty");
        op.execute();
    }

    public void validateBinQty(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // validateBinQty

        if (object == null || object.toString().trim().length() == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                                          "This can not be blank"));
        } else {
            if (((Number) object).compareTo(new Number(0)) != 1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                                              "This should be positive"));
            }

            OperationBinding op = ADFBeanUtils.findOperation("validateBinQty");
            op.getParamsMap().put("qty", object);
            op.execute();
            String result = op.getResult().toString();
            if (result.equalsIgnoreCase("Y")) {

            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                                                              "value can not be more than " + result));
            }
        }

    }


    public void listenTypeChange(ValueChangeEvent valueChangeEvent) {

    }

    public void setEntityBind(RichInputListOfValues entityBind) {
        this.entityBind = entityBind;
    }

    public RichInputListOfValues getEntityBind() {
        return entityBind;
    }

    public void setWareHouseBind(RichInputListOfValues wareHouseBind) {
        this.wareHouseBind = wareHouseBind;
    }

    public RichInputListOfValues getWareHouseBind() {
        return wareHouseBind;
    }

    public String validateHeader() {
        String result = "Y";
        if (this.getEntityBind().getValue() == null ||
            this.getEntityBind().getValue().toString().trim().length() == 0) {
            result = "N";
            this.showError(this.getEntityBind().getClientId(), null, "Please make a selection");
            return result;
        }
        if (this.getWareHouseBind().getValue() == null || this.getWareHouseBind().getValue().toString().length() == 0) {
            result = "N";
            this.showError(this.getWareHouseBind().getClientId(), null, "Please make a selection");
            return result;
        }

        if (this.getCurrBind().getValue() == null || this.getCurrBind().getValue().toString().length() == 0) {
            result = "N";
            this.showError(this.getCurrBind().getClientId(), null, "Please make a selection");
            return result;
        }

        if (this.getProjectId().getValue() == null || this.getProjectId().getValue().toString().length() == 0) {
            result = "N";
            this.showError(this.getProjectId().getClientId(), null, "Please make a selection");
            return result;
        }

        return result;
    }

    public void addDoc(ActionEvent actionEvent) {
        // Add event code here...
        // validate header

        String ret = this.validateHeader();

        if (ret.contains("N"))
            return;
        ADFBeanUtils.findOperation("initSrcAndIItem").execute();
    }

    public void lotDiaListner(DialogEvent dialogEvent) {
        ADFBeanUtils.findOperation("updateLot").execute();
    }

    public void binListener(DialogEvent dialogEvent) {
        ADFBeanUtils.findOperation("updateBin").execute();
    }

    public void releaseDocument(ActionEvent actionEvent) {
        this.setIsRls("I");
    }

    public void setCurrBind(RichInputListOfValues currBind) {
        this.currBind = currBind;
    }

    public RichInputListOfValues getCurrBind() {
        return currBind;
    }

    public void hidePopUp(ActionEvent actionEvent) {
        // Add event code here...
        ADFBeanUtils.showPopup(this.getTaxPop(), false);
    }

    public String headCostCenterAction() {
        OperationBinding binding = ADFBeanUtils.findOperation("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            return "headCc";
        } else {
            return null;
        }
    }

    public String costCenterAction() {
        OperationBinding binding = ADFBeanUtils.findOperation("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            return "costCenter";
        } else {
            return null;
        }
    }

    public Boolean getIsCcValid() {
        OperationBinding binding = ADFBeanUtils.findOperation("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            return true;
        } else {
            return false;
        }
    }

    public void setProjectId(RichSelectOneChoice projectId) {
        this.projectId = projectId;
    }

    public RichSelectOneChoice getProjectId() {
        return projectId;
    }

    public void binPopCancel(PopupCanceledEvent popupCanceledEvent) {

        if (this.getAttrsVal("MmScRcptBin1Iterator", "RcptMode") == null) {
            return;
        }

        String value = this.getAttrsVal("MmScRcptBin1Iterator", "RcptMode").toString();

        if (value.equalsIgnoreCase("Y"))
            ADFBeanUtils.findOperation("DeleteBin").execute();
    }
}
