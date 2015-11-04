package slspicpackshipapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.ebiz.EbizParams;
import adf.utils.ebiz.EbizParamsAPPUtils;
import adf.utils.ebiz.EbizParamsMMUtils;
import adf.utils.model.ADFModelUtils;

import java.util.ArrayList;

import java.util.Iterator;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.context.RequestContext;

import slspicpackshipapp.model.datatype.PickDtlsDS;

public class ShipmntBean {
    private ArrayList<PickDtlsDS> pickDtls = new ArrayList<PickDtlsDS>();
    private RichPopup createNewRoutePopup;
    private Boolean showAddPanel = false;
    private Number itemLotAmt;
    private RichPopup cancelConfirmPopUp;

    private Boolean isPickSelected = true;

    public Boolean getIsPickSelected() {
        if (pickDtls.size() > 0) {
            isPickSelected = false;
        } else {
            isPickSelected = true;
        }
        return isPickSelected;
    }


    public void setItemLotAmt(Number itemLotAmt) {
        this.itemLotAmt = itemLotAmt;
    }

    public Number getItemLotAmt() {
        return itemLotAmt;
    }

    public void setShowAddPanel(Boolean showAddPanel) {
        this.showAddPanel = showAddPanel;
    }

    public Boolean getShowAddPanel() {
        return showAddPanel;
    }

    public void setPickDtls(ArrayList<PickDtlsDS> pickDtls) {
        this.pickDtls = pickDtls;
    }

    public ArrayList<PickDtlsDS> getPickDtls() {
        return pickDtls;
    }

    private Boolean isItmPricBasisAllow = null;

    public Boolean getIsItmPricBasisAllow() {
        if (isItmPricBasisAllow == null) {
            OperationBinding ob = ADFBeanUtils.findOperation("isItemPriceBasisApplicable");
            ob.execute();
            if (ob.getResult() != null) {
                isItmPricBasisAllow = (Boolean) ob.getResult();
            }
        }
        return isItmPricBasisAllow;
    }

    public ShipmntBean() {
    }
    Integer selectChkShiCount = 0;
    private String destGo = null;

    public String getDestGo() {
        destGo = ADFModelUtils.resolvEl("#{pageFlowScope.NAV_PARAM}").toString();
        return destGo;
    }

    private Boolean enableCostCenter = null;

    //Setting values to check cost center aplicable
    public Boolean getEnableCostCenter() {
        //isCostCenterApplicable
        if (enableCostCenter == null) {
            OperationBinding b = ADFBeanUtils.getOperationBinding("isCostCenterApplicable");
            if (b != null) {
                b.execute();
                Object o = b.getResult();
                enableCostCenter = (o == null ? false : (Boolean) o);
                System.out.println("*********************************  " + enableCostCenter);
            }
        }
        return enableCostCenter;
    }


    /**
     * Method to fetch Component client id
     * @param comp
     * @param id
     * @return
     */
    public String getComponentCliendIdFromId(UIComponent comp, String id) {
        if (comp != null) {
            StringBuilder clientId = new StringBuilder(comp.getClientId());
            clientId = new StringBuilder(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            return clientId.append(id).toString();
        } else {
            return null;
        }
    }

    /**
     *  Method to reset params for Shipment bean
     */
    public void initalizeShipmntBeanVars() {
        pickDtls.clear();
        showAddPanel = false;
    }

    /**
     * Method to generate Shipment
     * @param actionEvent
     *
     */
    public void generateShipmentACTION(ActionEvent actionEvent) {
        System.out.println("In Generate shipment function");
        OperationBinding generateShipmntOB = ADFBeanUtils.getOperationBinding("generateShipment1");
        generateShipmntOB.getParamsMap().put("pickList", pickDtls);
        generateShipmntOB.execute();
        /* 0 : Shipment Created successfully
        * 1 : Not even one pick is selected for Shipment Creation.
        * 2 : Transport route is not defined.
        * 3 : Vehical Type is not defined
        * -1 : Error */
        Object object = generateShipmntOB.getResult();
        Integer i = (object == null ? -1 : (Integer) object);
        if (i == 1) {
            ADFModelUtils.showFormattedFacesMessage("Pick have not been selected for whome Shipment will be generated.",
                                                    "Please select atleast one Pick for whome Shipment will be generated.",
                                                    FacesMessage.SEVERITY_ERROR);
        } else if (i == 2) {
            ADFModelUtils.showFormattedFacesMessage("Transport Route is not defined for the Shipment.",
                                                    "As your Current Organisation uses Transported Module, Transportation details needs to be entered for each Shipment.",
                                                    FacesMessage.SEVERITY_ERROR);
        } else if (i == 3) {
            ADFModelUtils.showFormattedFacesMessage("Vehical Type is not defined for the Shipment.",
                                                    "As your Current Organisation uses Transported Module, Transportation details needs to be entered for each Shipment.",
                                                    FacesMessage.SEVERITY_ERROR);
        }else if (i == 4) {
            ADFModelUtils.showFormattedFacesMessage("Transporter is not defined for the Shipment.",
                                                    "As your Current Organisation uses Transported Module, Transportation details needs to be entered for each Shipment.",
                                                    FacesMessage.SEVERITY_ERROR);
        } else if (i == -1) {
            ADFModelUtils.showFacesMessage("", "There have been some error while generating shipment. Please try again",
                                           FacesMessage.SEVERITY_ERROR, null);
        } else {
            pickDtls.clear();
            showAddPanel = false;
        }

    }


    public void addShipmentACE(ActionEvent actionEvent) {
        showAddPanel = true;
        Map<String, Object> attrs = actionEvent.getComponent().getAttributes();
        String pickDocId = attrs.get("pickDocId").toString();
        String pickId = attrs.get("pickDispId").toString();
        String whId = attrs.get("whId").toString();
        String shipAdds = attrs.get("shipAdds").toString();
        Integer currIdSp = (Integer) attrs.get("currIdSp");
        String prjId = attrs.get("prjId").toString();


        Integer eoId = (Integer) attrs.get("eoId");
        String eoNm = attrs.get("eoNm").toString();
        System.out.println(whId + " From the link");
        System.out.println(eoId + " From the link");
        Boolean addRow = false;
        if (pickDtls.size() == 0) {
            addRow = true;
        } else if (pickDtls.size() > 0) {
            PickDtlsDS dS = pickDtls.get(0);
            System.out.println(dS.toString());
            System.out.println(dS.getWhId());
            System.out.println(dS.getEoId() + " same customer");
            System.out.println(eoId.equals(dS.getEoId()));
            System.out.println(whId.equalsIgnoreCase(dS.getWhId()) + " Same whea");
            if (!(eoId.equals(dS.getEoId()) && whId.equals(dS.getWhId())) && currIdSp.equals(dS.getCurrIdSp())) {
                ADFModelUtils.showFormattedFacesMessage("Customer ,Warehouse and Currency not Matching!",
                                                        "The selected pick's Customer ,Warehouse and Currency dosen't match with the previously selected pick.",
                                                        FacesMessage.SEVERITY_WARN);
            } else if (!isPickIdForAddUnique(pickId)) {
                ADFModelUtils.showFormattedFacesMessage("Duplicate Pick ",
                                                        "Please Add another pick, Since this pick is already Added.",
                                                        FacesMessage.SEVERITY_WARN);
            } else {
                addRow = true;
            }
        }
        if (addRow) {
            OperationBinding trVal = ADFBeanUtils.findOperation("setValueForTransport");
            trVal.getParamsMap().put("whId", whId);
            trVal.getParamsMap().put("shipAddId", shipAdds);
            trVal.execute();
            pickDtls.add(new PickDtlsDS(pickDocId, pickId, whId, eoId, eoNm, currIdSp, prjId, shipAdds));
        }
    }

    public Boolean isPickIdForAddUnique(String pickId) {
        Boolean b = true;
        for (PickDtlsDS ds : pickDtls) {
            if (pickId.equals(ds.getPickId())) {
                b = false;
                break;
            }
        }
        return b;
    }

    public void removePickForShipACE(ActionEvent actionEvent) {
        Long res = (Long) actionEvent.getComponent().getAttributes().get("rowIndex");
        Integer index = res.intValue();
        pickDtls.remove(index - 1);
    }

    public void addNewRouteACE(ActionEvent actionEvent) {
        ADFBeanUtils.showPopup(createNewRoutePopup, true);
    }

    public void setCreateNewRoutePopup(RichPopup createNewRoutePopup) {
        this.createNewRoutePopup = createNewRoutePopup;
    }

    public RichPopup getCreateNewRoutePopup() {
        return createNewRoutePopup;
    }

    public void resetShipmentAction(ActionEvent actionEvent) {
        OperationBinding resetSearch = ADFBeanUtils.findOperation("resetShipment");
        resetSearch.execute();
        selectChkShiCount = 0;
    }

    public String backFrmShpmntAction() {
        ADFBeanUtils.findOperation("refreshAllVoOncancellation").execute();
        OperationBinding resetSearch = ADFBeanUtils.findOperation("resetShipment");
        resetSearch.execute();
        selectChkShiCount = 0;
        String flag = "N";
        if (ADFModelUtils.resolvEl("#{pageFlowScope.PARAM_PACK_VSBL_CHCK}") != null) {
            flag = ADFModelUtils.resolvEl("#{pageFlowScope.PARAM_PACK_VSBL_CHCK}").toString();
        }
        System.out.println(flag + " <<<< pack is set to visible or not");
        System.out.println(getDestGo() + " <<<< the source of the page");

        if (flag.equalsIgnoreCase("Y") && getDestGo().equalsIgnoreCase("P")) {
            return "backToPack";
        } else if (flag.equalsIgnoreCase("Y") && getDestGo().equalsIgnoreCase("S")) {
            return "shpMntToSearch";
        } else if (flag.equalsIgnoreCase("N") && getDestGo().equalsIgnoreCase("P")) {
            return "backToPick";
        } else if (flag.equalsIgnoreCase("N") && getDestGo().equalsIgnoreCase("S")) {
            return "shpMntToSearch";
        } else {
            return null;
        }
    }

    public String showShimpmentDetailACTION() {
        System.out.println("setValuesForShipmentPage  in bean");
        ADFBeanUtils.getOperationBinding("setValuesForShipmentPage").execute();
        return "viewShipment";
    }

    /**
     * cancel shipment
     * @param actionEvent
     */
    public void cancelShipmntACTION(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("cancelShipmnt");
        binding.execute();
        Object o = binding.getResult();
        ADFBeanUtils.showPopup(cancelConfirmPopUp, false);
        cancelConfirmPopUp.hide();
    }

    public String headCostCenterAction() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            return "headCc"; //costCenter
        } else {
            return null;
        }
    }

    //Event to get Item total lot amount for Cost Center
    public void ccItemAmtACE(ActionEvent actionEvent) {
        OperationBinding lot = ADFBeanUtils.getOperationBinding("sumLotAmount");
        lot.getParamsMap().put("itmId", actionEvent.getComponent().getAttributes().get("itmId"));
        lot.getParamsMap().put("shiDocId", actionEvent.getComponent().getAttributes().get("shipDocId"));
        lot.getParamsMap().put("pickId", actionEvent.getComponent().getAttributes().get("pickDocId"));
        lot.execute();
        setItemLotAmt((Number) lot.getResult());
        System.out.println(getItemLotAmt() + "  ###################### The Item Lots amount");
    }

    public String detailCostCenterAction() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("chkCcApplicableOrNot");
        binding.execute();
        if (binding.getResult() != null && (Boolean) binding.getResult()) {
            return "costCenter"; //
        } else {
            return null;
        }
    }

    public void setCancelConfirmPopUp(RichPopup cancelConfirmPopUp) {
        this.cancelConfirmPopUp = cancelConfirmPopUp;
    }

    public RichPopup getCancelConfirmPopUp() {
        return cancelConfirmPopUp;
    }
}
