package appschemeprofile.view.bean;

import adf.utils.bean.StaticValue;
import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import appschemeprofile.view.utils.ADFUtils;
import appschemeprofile.view.utils.JSFUtils;

import java.sql.Date;
import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.JboPrecisionScaleValidator;

import org.apache.myfaces.trinidad.event.PollEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class CreateSchemeProfileBean {
    private RichSelectOneChoice uomIdPgBind;
    private RichPanelLabelAndMessage groupNamePgBind;
    private RichPanelLabelAndMessage itemNamePgBind;
    private RichPanelLabelAndMessage itemGroupNamePgBind;
    private RichPanelLabelAndMessage nameItemPgBind;
    private RichInputText itemIdPgBind;
    private RichSelectOneChoice itemUomPgBind;
    private RichInputText freeQuantityPgBind;
    private RichSelectOneRadio freeDiscountTypePgBind;
    private RichInputText freeDiscountValuePgBind;
    private RichTable itemTablePgBind;
    private String mode = (String) JSFUtils.getExpressionObjectReference("#{pageFlowScope.SCHEME_MODE}");
    private RichInputText itemPricePgBind;
    private RichInputText minimumAmtPgBind;
    private RichInputText maximumAmtPgBind;
    private RichInputText minimumQtyPgBind;
    private RichInputText maximumQtyPgBind;
    private RichInputDate fromDatePgBind;
    private RichInputText freeItmMinAmtPgBind;
    private RichInputText freeMaxAmtPgBind;
    private RichSelectBooleanCheckbox activeCbPgBind;
    private RichInputDate inactiveDatePgBind;
    private RichInputText inactiveReasonPgBind;
    private RichSelectBooleanCheckbox unlimitedAmtPgBind;
    private RichSelectBooleanCheckbox unlimitedQtyPgBind;
    private RichSelectBooleanCheckbox freeUnlimitedAmtPgBind;
    private RichPopup detailItemPopUpPgBind;
    private RichSelectOneRadio freeItemTypePgBind;
    private RichInputText itemNameDispPgBind;
    private RichSelectOneRadio incldItmSelTypePgBind;
    private RichInputText daysLeftPgBind;
    private RichInputListOfValues itmNametransBind;
    public Timestamp currDate;
    private RichInputText itemDispNameBind;
    private String finalizeMode = "N";

    public void setFinalizeMode(String finalizeMode) {
        this.finalizeMode = finalizeMode;
    }

    public String getFinalizeMode() {
        return finalizeMode;
    }

    public Timestamp getCurrDate() {
        Timestamp t = new Timestamp(System.currentTimeMillis());
        try {
            // Date d = new Date(t.dateValue().toString());
            t = new Timestamp(t.dateValue() + " 00:00:00");
            System.out.println("Value of Date-->" + t);
        } catch (SQLException e) {
        }
        currDate = t;
        return currDate;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public CreateSchemeProfileBean() {
    }

    public void setUomIdPgBind(RichSelectOneChoice uomIdPgBind) {
        this.uomIdPgBind = uomIdPgBind;
    }

    public RichSelectOneChoice getUomIdPgBind() {
        return uomIdPgBind;
    }

    public void setGroupNamePgBind(RichPanelLabelAndMessage groupNamePgBind) {
        this.groupNamePgBind = groupNamePgBind;
    }

    public RichPanelLabelAndMessage getGroupNamePgBind() {
        return groupNamePgBind;
    }

    public void ItemNameVCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(groupNamePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(uomIdPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(minimumAmtPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(maximumAmtPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(minimumQtyPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(maximumQtyPgBind);
    }

    public void GroupNameVCL(ValueChangeEvent vce) {
        ADFUtils.findOperation("ChangeGroupNameToNull").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemNamePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(uomIdPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(minimumAmtPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(maximumAmtPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(minimumQtyPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(maximumQtyPgBind);
    }

    public void setItemNamePgBind(RichPanelLabelAndMessage itemNamePgBind) {
        this.itemNamePgBind = itemNamePgBind;
    }

    public RichPanelLabelAndMessage getItemNamePgBind() {
        return itemNamePgBind;
    }

    public void ApplicableOnVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            ADFUtils.findOperation("ChangeItemNameToNull").execute();
            itemDispNameBind = null;
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmNametransBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemNamePgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(groupNamePgBind);
            // AdfFacesContext.getCurrentInstance().addPartialTarget(itemNamePgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(uomIdPgBind);
            // AdfFacesContext.getCurrentInstance().addPartialTarget(itemDispNameBind);
        }
    }

    public void CreateSchemeAL(ActionEvent actionEvent) {
        mode = "A";
        ADFUtils.findOperation("CreateInsertSchm").execute();
    }

    public void EditSchemeButtonAL(ActionEvent actionEvent) {
        mode = "E";
        // Add event code here...
    }

    public void SaveButtonAL(ActionEvent actionEvent) {
        OperationBinding obGet = ADFUtils.findOperation("getFinalisedValue");
        obGet.execute();
        Boolean object = null;

        object = (Boolean) obGet.getResult();
        System.out.println("Result is ::: " + object);

        if (object) {
            finalizeMode = "Y";
            System.out.println("In Freeze Mode Y:::");
            AdfFacesContext.getCurrentInstance().addPartialTarget(minimumAmtPgBind);
            OperationBinding ob = ADFUtils.findOperation("chkMinAmtValidator");
            ob.execute();
            Number itmPriceVar = (Number) ob.getResult();
            System.out.println("Item Price in Bean " + itmPriceVar);
            Number pageMinPriceBind = new Number(0);
            if (minimumAmtPgBind.getValue() != null) {
                pageMinPriceBind = (Number) minimumAmtPgBind.getValue();
            }

            if (itmPriceVar.compareTo(pageMinPriceBind) > 0 && minimumAmtPgBind.getValue() != null) {
                JSFUtils.addFacesErrorMessage("Please enter the Minimum amount greater than  " + itmPriceVar + ".");
            } else if (valFlag.equals(true) && itmNametransBind.getValue() != null) {
                JSFUtils.addFacesErrorMessage("Please enter the Minimum amount greater than the Selected Item Amount.");
            } else {
                Object execute = ADFUtils.findOperation("chkDetailRow").execute();
                if (execute != null && execute.equals(false)) {
                    JSFUtils.addFacesErrorMessage("Please enter atleast one row in detail");
                    return;
                }
                mode = "V";

                // ADFUtils.findOperation("Commit").execute();
                ADFUtils.findOperation("InsertDataIntoItemFromDetail").execute();
                OperationBinding obDocId = ADFUtils.findOperation("getCurrentDocId");
                obDocId.execute();
                String object_2 = obDocId.getResult().toString();
                System.out.println("object_2 :::::: " + object_2);
                ADFUtils.findOperation("Commit").execute();
                if (!object_2.equals(" ")) {
                    OperationBinding obDocFilter = ADFUtils.findOperation("filterOnDocId");
                    obDocFilter.getParamsMap().put("docId", object_2);
                    obDocFilter.execute();

                }
                JSFUtils.addFacesInformationMessage(" Scheme Profile has been finalized and Record is Saved Successfully...!");
            }
        } else {
            AdfFacesContext.getCurrentInstance().addPartialTarget(minimumAmtPgBind);
            OperationBinding ob = ADFUtils.findOperation("chkMinAmtValidator");
            ob.execute();
            Number itmPriceVar = (Number) ob.getResult();
            System.out.println("Item Price in Bean " + itmPriceVar);
            Number pageMinPriceBind = new Number(0);
            if (minimumAmtPgBind.getValue() != null) {
                pageMinPriceBind = (Number) minimumAmtPgBind.getValue();
            }

            if (itmPriceVar.compareTo(pageMinPriceBind) > 0 && minimumAmtPgBind.getValue() != null) {
                JSFUtils.addFacesErrorMessage("Please enter the Minimum amount greater than  " + itmPriceVar + ".");
            } else if (valFlag.equals(true) && itmNametransBind.getValue() != null) {
                JSFUtils.addFacesErrorMessage("Please enter the Minimum amount greater than the Selected Item Amount.");
            } else {
                Object execute = ADFUtils.findOperation("chkDetailRow").execute();
                if (execute != null && execute.equals(false)) {
                    JSFUtils.addFacesErrorMessage("Please enter atleast one row in detail");
                    return;
                }
                mode = "V";

                // ADFUtils.findOperation("Commit").execute();
                ADFUtils.findOperation("InsertDataIntoItemFromDetail").execute();
                OperationBinding obDocId = ADFUtils.findOperation("getCurrentDocId");
                obDocId.execute();
                String object_2 = obDocId.getResult().toString();
                System.out.println("object_2 :::::: " + object_2);
                ADFUtils.findOperation("Commit").execute();
                OperationBinding obDocFilter = ADFUtils.findOperation("filterOnDocId");
                obDocFilter.getParamsMap().put("docId", object_2);
                obDocFilter.execute();
                JSFUtils.addFacesInformationMessage("Record Saved Successfully...");
            }
        }

    }

    public void CreateItemButtonAL(ActionEvent actionEvent) {
        mode = "E";

        ADFUtils.findOperation("CreateInsertItem").execute();
        /* OperationBinding ob = ADFUtils.findOperation("executeAppSchmVo");
        ob.execute(); */
    }

    public void DeleteItemButtonAL(ActionEvent actionEvent) {
        mode = "E";
        ADFUtils.findOperation("DeleteItem").execute();
    }

    public void ItemGroupNameVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            ADFUtils.findOperation("ChangeSchemeItemNameToNull").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(nameItemPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemIdPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemUomPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemPricePgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemTablePgBind);
        }
    }

    public void NameItemVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null && vce.getNewValue().toString().length() > 0) {
            //System.out.println("Inside If Part");
            OperationBinding ob = ADFUtils.findOperation("GetLatestItemPrice");
            ob.getParamsMap().put("itemDesc", vce.getNewValue());
            ob.execute();
            if (ob.getResult() != null) {
                if (ob.getResult().toString().length() > 0)
                    itemPricePgBind.setValue(ob.getResult());
            } else {
                itemPricePgBind.setValue(0);
            }

        } else {
            //System.out.println("Inside Else Part");
            ADFUtils.findOperation("ChangeSchemeItemNameToNull").execute();
            //AdfFacesContext.getCurrentInstance().addPartialTarget(itemGroupNamePgBind);
            itemPricePgBind.setValue(0);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemGroupNamePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemUomPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemPricePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTablePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemIdPgBind);
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void setItemGroupNamePgBind(RichPanelLabelAndMessage itemGroupNamePgBind) {
        this.itemGroupNamePgBind = itemGroupNamePgBind;
    }

    public RichPanelLabelAndMessage getItemGroupNamePgBind() {
        return itemGroupNamePgBind;
    }

    public void setNameItemPgBind(RichPanelLabelAndMessage nameItemPgBind) {
        this.nameItemPgBind = nameItemPgBind;
    }

    public RichPanelLabelAndMessage getNameItemPgBind() {
        return nameItemPgBind;
    }

    public void setItemIdPgBind(RichInputText itemIdPgBind) {
        this.itemIdPgBind = itemIdPgBind;
    }

    public RichInputText getItemIdPgBind() {
        return itemIdPgBind;
    }

    public void setItemUomPgBind(RichSelectOneChoice itemUomPgBind) {
        this.itemUomPgBind = itemUomPgBind;
    }

    public RichSelectOneChoice getItemUomPgBind() {
        return itemUomPgBind;
    }

    public void setFreeQuantityPgBind(RichInputText freeQuantityPgBind) {
        this.freeQuantityPgBind = freeQuantityPgBind;
    }

    public RichInputText getFreeQuantityPgBind() {
        return freeQuantityPgBind;
    }

    public void setFreeDiscountTypePgBind(RichSelectOneRadio freeDiscountTypePgBind) {
        this.freeDiscountTypePgBind = freeDiscountTypePgBind;
    }

    public RichSelectOneRadio getFreeDiscountTypePgBind() {
        return freeDiscountTypePgBind;
    }

    public void setFreeDiscountValuePgBind(RichInputText freeDiscountValuePgBind) {
        this.freeDiscountValuePgBind = freeDiscountValuePgBind;
    }

    public RichInputText getFreeDiscountValuePgBind() {
        return freeDiscountValuePgBind;
    }

    public void setItemTablePgBind(RichTable itemTablePgBind) {
        this.itemTablePgBind = itemTablePgBind;
    }

    public RichTable getItemTablePgBind() {
        return itemTablePgBind;
    }

    public void SchemeTypeVCL(ValueChangeEvent vce) {

        AdfFacesContext.getCurrentInstance().addPartialTarget(minimumAmtPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(maximumAmtPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(minimumQtyPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(maximumQtyPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(freeQuantityPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(freeDiscountTypePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(freeDiscountValuePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTablePgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(unlimitedAmtPgBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(unlimitedQtyPgBind);


    }

    public Boolean isPrecisionValid(Integer precision, Integer scale, oracle.jbo.domain.Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void ItemPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null));
            }
            if (val.compareTo(0) <= 0 && itemIdPgBind.getValue() != null) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than zero.", null));
            } else if (val.compareTo(0) != 0 && itemIdPgBind.getValue() == null) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be equal to zero.", null));
            }
        }
    }

    public void ItemQuantityValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null));
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than zero.", null));
            }
        }
    }

    public void DiscountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = new Number(0);
            val = (Number) object;
            if (!(isPrecisionValid(26, 6, val))) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision(26,6).",
                                                              null));
            }
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value should be greater than zero.", null));
            }

            Object discType = freeDiscountTypePgBind.getValue();
            if (discType != null) {
                if (discType.toString().equalsIgnoreCase("A")) {
                    if (freeItemTypePgBind.getValue().toString().equalsIgnoreCase("I")) {
                        Number price = (Number) itemPricePgBind.getValue();
                        if (val.compareTo(price) > 0) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          "Discount Value cannot be greater than Item Price.",
                                                                          null));
                        } else if (val.compareTo(price) == 0) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          "Discount Value cannot be equal to Item Price.",
                                                                          null));
                        }
                    }
                } else if (discType.toString().equalsIgnoreCase("P")) {
                    if (val.compareTo(100) > 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Please enter proper percentage.", null));
                    } else if (val.compareTo(100) == 0) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Percentage value should be less than 100.",
                                                                      null));
                    }
                }
            }
        }
    }

    public void setItemPricePgBind(RichInputText itemPricePgBind) {
        this.itemPricePgBind = itemPricePgBind;
    }

    public RichInputText getItemPricePgBind() {
        return itemPricePgBind;
    }

    public void setMinimumAmtPgBind(RichInputText minimumAmtPgBind) {
        this.minimumAmtPgBind = minimumAmtPgBind;
    }

    public RichInputText getMinimumAmtPgBind() {
        return minimumAmtPgBind;
    }

    public void setMaximumAmtPgBind(RichInputText maximumAmtPgBind) {
        this.maximumAmtPgBind = maximumAmtPgBind;
    }

    public RichInputText getMaximumAmtPgBind() {
        return maximumAmtPgBind;
    }

    public void setMinimumQtyPgBind(RichInputText minimumQtyPgBind) {
        this.minimumQtyPgBind = minimumQtyPgBind;
    }

    public RichInputText getMinimumQtyPgBind() {
        return minimumQtyPgBind;
    }

    public void setMaximumQtyPgBind(RichInputText maximumQtyPgBind) {
        this.maximumQtyPgBind = maximumQtyPgBind;
    }

    public RichInputText getMaximumQtyPgBind() {
        return maximumQtyPgBind;
    }

    public void MinQtyVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null && maximumQtyPgBind.getValue() != null &&
            maximumQtyPgBind.getValue().toString().length() > 0) {
            //  maximumQtyPgBind.setValue(0);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maximumQtyPgBind);
        }
    }

    public void MinQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            // System.out.println("Number is " + val.compareTo(0));
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value Should be greater than zero.", null));
            }
        }
    }

    public void MaxQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            // System.out.println("Number is " + val.compareTo(0));
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value Should be greater than zero.", null));
            }
            Object minQty = minimumQtyPgBind.getValue();
            if (minQty != null) {
                oracle.jbo.domain.Number minVal = (oracle.jbo.domain.Number) minQty;
                if (val.compareTo(minVal) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Value should be greater than or equal to minimum quantity.",
                                                                  null));
                }
            }
        }
    }

    public void MinAmtVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null && maximumAmtPgBind.getValue() != null &&
            maximumAmtPgBind.getValue().toString().length() > 0) {
            //  maximumAmtPgBind.setValue(0);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maximumAmtPgBind);
        }
    }

    public void MaxAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            // System.out.println("Number is " + val.compareTo(0));
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value Should be greater than zero.", null));
            }
            Object minAmt = minimumAmtPgBind.getValue();
            if (minAmt != null) {
                oracle.jbo.domain.Number minVal = (oracle.jbo.domain.Number) minAmt;
                if (val.compareTo(minVal) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Value should be greater than or equal to minimum amount.",
                                                                  null));
                }
            }
        }
    }
    Boolean valFlag = false;

    public void MinAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            // System.out.println("Number is " + val.compareTo(0));
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value Should be greater than zero.", null));
            }
            OperationBinding ob = ADFUtils.findOperation("findLatestItemPrice");
            ob.execute();
            Number itemPriceVar = (Number) ob.getResult();
            if (itemPriceVar.compareTo((Number) object) > 0) {
                valFlag = true;
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "The Minimum amount of the Item must be greater than " +
                                                              itemPriceVar +
                                                              " in order to create item wise scheme policy.", null));
            } else {
                valFlag = false;
            }
        }
    }

    public void setFromDatePgBind(RichInputDate fromDatePgBind) {
        this.fromDatePgBind = fromDatePgBind;
    }

    public RichInputDate getFromDatePgBind() {
        return fromDatePgBind;
    }

    public void ToDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
      /*  if (object != null) {
            
            if (isValidTimestamp(StaticValue.getTruncatedDt((Timestamp)object).toString())) {
                if (fromDatePgBind.getValue() != null) {
                    // System.out.println("From Date is "+fromDatePgBind.getValue());
                    // System.out.println("To date is "+object);

                    Timestamp t1 = (Timestamp) fromDatePgBind.getValue();
                    Timestamp t2 = (Timestamp) object;
                    Timestamp t3 = new Timestamp(System.currentTimeMillis());
                    Date d1 = new Date(System.currentTimeMillis());
                    Date d2 = new Date(System.currentTimeMillis());
                    Date d3 = new Date(System.currentTimeMillis());
                    try {
                        d1 = t1.dateValue();
                        d2 = t2.dateValue();
                        d3 = t3.dateValue();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    //System.out.println("T2 Compare to T1 "+t2.compareTo(t1));
                    if (t2.compareTo(t1) < 0 && !d1.toString().equals(d2.toString())) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "To Date Should be greater than From Date.",
                                                                      null));
                    }

                    if (t2.compareTo(t3) < 0 && !d3.toString().equals(d2.toString())) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "To Date Should be greater than System Date.",
                                                                      null));
                    }
                }
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Date.", null));
            }
        }*/
    }

    public void SchemeNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            //String expression = "([a-zA-Z0-9]+)(([ ])([a-zA-Z0-9]+))*"; //String with one space in middle
            //String expression = "([a-zA-Z0-9]+)(([ ])([a-zA-Z0-9]+))*"; //String with one space in middle

            //String expression = "([a-zA-Z0-9]+)(([ ])([a-zA-Z0-9]+))*([ ])*";  //String with space in middle and end

            //   String expression = "(([a-zA-Z0-9]+)([([\\s\\+])?([a-zA-Z0-9]+)])+([a-zA-Z0-9]+)]))+"; //String with one space in middle
            //  String expression = "(([a-zA-Z0-9]+)([([\\s\\+])?([a-zA-Z0-9]+)])+ ([([\\+])?([a-zA-Z0-9]+)])+)+";


            String expression = "(([a-zA-Z0-9]+)(((([\\s\\+])?([a-zA-Z0-9]+))+)?([\\+])?)*)+";
            CharSequence inputStr = object.toString();
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Special Character Not Allowed.", null));
            }

            OperationBinding ob = ADFUtils.findOperation("ChkSchemeNameDuplicate");
            ob.getParamsMap().put("name", object);
            ob.execute();
            if ((Boolean) ob.getResult()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Scheme Name.",
                                                              null));
            }
        }
    }

    public void DiscountTypeVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            freeDiscountValuePgBind.setValue(0);
            AdfFacesContext.getCurrentInstance().addPartialTarget(freeDiscountValuePgBind);
        }
    }

    public void setFreeItmMinAmtPgBind(RichInputText freeItmMinAmtPgBind) {
        this.freeItmMinAmtPgBind = freeItmMinAmtPgBind;
    }

    public RichInputText getFreeItmMinAmtPgBind() {
        return freeItmMinAmtPgBind;
    }

    public void FreeMinAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            // System.out.println("Number is " + val.compareTo(0));
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value Should be greater than zero.", null));
            }
        }
    }

    public void FreeMaxAmtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number val = (oracle.jbo.domain.Number) object;
            if (!isPrecisionValid(26, 6, val)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Invalid Precision. Please enter valid precision(26, 6).",
                                                              null));
            }
            // System.out.println("Number is " + val.compareTo(0));
            if (val.compareTo(0) <= 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Value Should be greater than zero.", null));
            }
            Object minAmt = freeItmMinAmtPgBind.getValue();
            if (minAmt != null) {
                oracle.jbo.domain.Number minVal = (oracle.jbo.domain.Number) minAmt;
                if (val.compareTo(minVal) < 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Value should be greater than or equal to minimum amount.",
                                                                  null));
                }
            }
        }
    }

    public void FreeMinAmtVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null && freeMaxAmtPgBind.getValue() != null &&
            freeMaxAmtPgBind.getValue().toString().length() > 0) {
            freeMaxAmtPgBind.setValue(0);
            AdfFacesContext.getCurrentInstance().addPartialTarget(freeMaxAmtPgBind);
        }
    }

    public void setFreeMaxAmtPgBind(RichInputText freeMaxAmtPgBind) {
        this.freeMaxAmtPgBind = freeMaxAmtPgBind;
    }

    public RichInputText getFreeMaxAmtPgBind() {
        return freeMaxAmtPgBind;
    }

    public void FreeItemGroupValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && freeItemTypePgBind.getValue() != null) {
            System.out.println("Inside Free Item Group Validator " + freeItemTypePgBind.getValue());
            if (freeItemTypePgBind.getValue().toString().equalsIgnoreCase("G")) {
                System.out.println("Inside Chk");
                OperationBinding ob = ADFUtils.findOperation("ChkItemGroupNameDuplicate");
                ob.getParamsMap().put("name", object);
                ob.execute();
                if ((Boolean) ob.getResult()) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Item Group.",
                                                                  null));
                }
            } else if (freeItemTypePgBind.getValue().toString().equalsIgnoreCase("I")) {
                if (object.toString().length() > 0 && itemNameDispPgBind.getValue() != null) {
                    if (itemNameDispPgBind.getValue().toString().length() > 0) {
                        OperationBinding ob = ADFUtils.findOperation("ChkItemNameDuplicate");
                        ob.getParamsMap().put("name", itemNameDispPgBind.getValue());
                        ob.execute();
                        if ((Boolean) ob.getResult()) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          "Duplicate Item Name.", null));
                        }

                        System.out.println("Item Group Exist or Not");
                        ob = ADFUtils.findOperation("ChkItemGroupExistOrNot");
                        ob.getParamsMap().put("name", itemNameDispPgBind.getValue());
                        ob.execute();
                        if ((Boolean) ob.getResult()) {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          "Group Name already exist. You cannot add Item which belongs to that group.",
                                                                          null));
                        }
                    }
                } else if (object.toString().length() > 0 && itemNameDispPgBind.getValue() == null) {
                    OperationBinding ob = ADFUtils.findOperation("ChkItemGroupExistOrNot");
                    ob.getParamsMap().put("name", object);
                    ob.execute();
                    if ((Boolean) ob.getResult()) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Duplicate Item Group.", null));
                    }
                }
            }
        }
    }

    public void FreeItemValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            System.out.println("Inside item Name Validator");
            OperationBinding ob = ADFUtils.findOperation("ChkItemNameDuplicate");
            ob.getParamsMap().put("name", object);
            ob.execute();
            if ((Boolean) ob.getResult()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Item Name.",
                                                              null));
            }

            System.out.println("Item Group Exist or Not");
            ob = ADFUtils.findOperation("ChkItemGroupExistOrNot");
            ob.getParamsMap().put("name", object);
            ob.execute();
            if ((Boolean) ob.getResult()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Group Name already exist. You cannot add Item which belongs to that group.",
                                                              null));
            }
        }
    }

    public void ItemTypeVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {

            if (vce.getNewValue().toString().equalsIgnoreCase("I")) {
                incldItmSelTypePgBind.setValue("I");
            } else if (vce.getNewValue().toString().equalsIgnoreCase("G")) {
                incldItmSelTypePgBind.setValue("G");
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(itemGroupNamePgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(nameItemPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemIdPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemUomPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(itemPricePgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(incldItmSelTypePgBind);
        }
    }

    public void setActiveCbPgBind(RichSelectBooleanCheckbox activeCbPgBind) {
        this.activeCbPgBind = activeCbPgBind;
    }

    public RichSelectBooleanCheckbox getActiveCbPgBind() {
        return activeCbPgBind;
    }

    public void setInactiveDatePgBind(RichInputDate inactiveDatePgBind) {
        this.inactiveDatePgBind = inactiveDatePgBind;
    }

    public RichInputDate getInactiveDatePgBind() {
        return inactiveDatePgBind;
    }

    public void setInactiveReasonPgBind(RichInputText inactiveReasonPgBind) {
        this.inactiveReasonPgBind = inactiveReasonPgBind;
    }

    public RichInputText getInactiveReasonPgBind() {
        return inactiveReasonPgBind;
    }

    public void ActiveInActiveVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            System.out.println("Vec value is " + vce.getNewValue());
            if (vce.getNewValue().toString().equalsIgnoreCase("false")) {
                inactiveDatePgBind.setValue(new Timestamp(System.currentTimeMillis()));
            } else {
                inactiveDatePgBind.setValue(null);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(inactiveDatePgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(inactiveReasonPgBind);
        }
    }

    public void UnlimitedQtyVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                //  maximumQtyPgBind.setValue(0);
                AdfFacesContext.getCurrentInstance().addPartialTarget(maximumQtyPgBind);
            } else {
                AdfFacesContext.getCurrentInstance().addPartialTarget(maximumQtyPgBind);
                // maximumQtyPgBind.setValue(null);
            }

        }
    }

    public void UnlimitedAmtVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                System.out.println("inside check:::");
                //  maximumAmtPgBind.setValue(new oracle.jbo.doamain.Number(0));
            } else {
                //  maximumAmtPgBind.setValue(0);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(maximumAmtPgBind);
        }
    }

    public void setUnlimitedAmtPgBind(RichSelectBooleanCheckbox unlimitedAmtPgBind) {
        this.unlimitedAmtPgBind = unlimitedAmtPgBind;
    }

    public RichSelectBooleanCheckbox getUnlimitedAmtPgBind() {
        return unlimitedAmtPgBind;
    }

    public void setUnlimitedQtyPgBind(RichSelectBooleanCheckbox unlimitedQtyPgBind) {
        this.unlimitedQtyPgBind = unlimitedQtyPgBind;
    }

    public RichSelectBooleanCheckbox getUnlimitedQtyPgBind() {
        return unlimitedQtyPgBind;
    }

    public void FreeUnlimitedAmtVCL(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            if (vce.getNewValue().toString().equalsIgnoreCase("true")) {
                freeMaxAmtPgBind.setValue(null);
            } else {
                freeMaxAmtPgBind.setValue(0);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(freeMaxAmtPgBind);
        }
    }

    public void setFreeUnlimitedAmtPgBind(RichSelectBooleanCheckbox freeUnlimitedAmtPgBind) {
        this.freeUnlimitedAmtPgBind = freeUnlimitedAmtPgBind;
    }

    public RichSelectBooleanCheckbox getFreeUnlimitedAmtPgBind() {
        return freeUnlimitedAmtPgBind;
    }

    public void ViewDetailItemAL(ActionEvent actionEvent) {
        ADFUtils.findOperation("FilterDetailItemData").execute();

        showPopup(detailItemPopUpPgBind, true);
    }

    public void setDetailItemPopUpPgBind(RichPopup detailItemPopUpPgBind) {
        this.detailItemPopUpPgBind = detailItemPopUpPgBind;
    }

    public RichPopup getDetailItemPopUpPgBind() {
        return detailItemPopUpPgBind;
    }

    private void showPopup(RichPopup pop, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null) {
                String popupId = pop.getClientId(context);
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

    public void setFreeItemTypePgBind(RichSelectOneRadio freeItemTypePgBind) {
        this.freeItemTypePgBind = freeItemTypePgBind;
    }

    public RichSelectOneRadio getFreeItemTypePgBind() {
        return freeItemTypePgBind;
    }

    public void setItemNameDispPgBind(RichInputText itemNameDispPgBind) {
        this.itemNameDispPgBind = itemNameDispPgBind;
    }

    public RichInputText getItemNameDispPgBind() {
        return itemNameDispPgBind;
    }

    public void setIncldItmSelTypePgBind(RichSelectOneRadio incldItmSelTypePgBind) {
        this.incldItmSelTypePgBind = incldItmSelTypePgBind;
    }

    public RichSelectOneRadio getIncldItmSelTypePgBind() {
        return incldItmSelTypePgBind;
    }

    public void DiscountValueVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemTablePgBind);
    }

    protected void refreshPage() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        String refreshpage = fctx.getViewRoot().getViewId();
        ViewHandler ViewH = fctx.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fctx, refreshpage);
        UIV.setViewId(refreshpage);
        fctx.setViewRoot(UIV);
    }

    public void RefreshTimePL(PollEvent pollEvent) {
        //refreshPage();
        AdfFacesContext.getCurrentInstance().addPartialTarget(daysLeftPgBind);
    }

    public void setDaysLeftPgBind(RichInputText daysLeftPgBind) {
        this.daysLeftPgBind = daysLeftPgBind;
    }

    public RichInputText getDaysLeftPgBind() {
        return daysLeftPgBind;
    }

    public void setItmNametransBind(RichInputListOfValues itmNametransBind) {
        this.itmNametransBind = itmNametransBind;
    }

    public RichInputListOfValues getItmNametransBind() {
        return itmNametransBind;
    }

    public void schemeTypeVCL(ValueChangeEvent vce) {


        if (vce.getNewValue() != null) {

            ADFUtils.findOperation("ChangeItemQtyAndAmtToNull").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(minimumQtyPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(maximumQtyPgBind);

        }
    }

    public void setItemDispNameBind(RichInputText itemDispNameBind) {
        this.itemDispNameBind = itemDispNameBind;
    }

    public RichInputText getItemDispNameBind() {
        return itemDispNameBind;
    }

    public void finalizeVCL(ValueChangeEvent vce) {
        if (vce != null) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            OperationBinding obGet = ADFUtils.findOperation("finalizeSchemePrf");
            obGet.execute();
        }

    }
}
