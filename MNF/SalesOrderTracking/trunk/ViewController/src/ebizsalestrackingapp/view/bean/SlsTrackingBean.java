package ebizsalestrackingapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.nav.RichLink;

import oracle.binding.OperationBinding;


public class SlsTrackingBean {
    private String mode = "V";
    private StringBuffer showDetail = new StringBuffer("S");
    private Integer stage = (Integer) ADFModelUtils.resolvEl("#{pageFlowScope.SLS_STAGE}");
    private Integer soSrc =
        ADFModelUtils.resolvEl("#{pageFlowScope.SLS_SO_SOURCE}") == null ? 2 :
        (Integer) ADFModelUtils.resolvEl("#{pageFlowScope.SLS_SO_SOURCE}");
    private Boolean checkMnf = null;
    private Boolean checkTrnspotation = null;
    private RichLink shipIdBind;
    private RichInputText shipDocIdBind;

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public Boolean getCheckTrnspotation() {
        if (checkTrnspotation == null) {
            OperationBinding ob = ADFBeanUtils.findOperation("checkProfileValues");
            ob.execute();
            Object res = ob.getResult();

            System.out.println("Check Transopation return value is ******" + res);
            if (res != null) {
                checkTrnspotation = (Boolean) res;
            }
        }
        return checkTrnspotation;
    }

    public Boolean getCheckMnf() {
        System.out.println(" check mnf method in bean");
        if (checkMnf == null) {
            if (soSrc != null) {
                OperationBinding ob = ADFBeanUtils.findOperation("isMnfCycleExist");
                ob.execute();
                Object res = ob.getResult();
                System.out.println("result is ************" + res);
                if (res != null) {
                    checkMnf = "N".equalsIgnoreCase(res.toString()) ? true : false;
                }
            }
        }

        return checkMnf;
    }

    public void setSoSrc(Integer soSrc) {
        this.soSrc = soSrc;

    }

    public Integer getSoSrc() {
        return soSrc;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Integer getStage() {
        return stage;
    }

    public void setShowDetail(StringBuffer showDetail) {
        this.showDetail = showDetail;
    }

    public StringBuffer getShowDetail() {
        return showDetail;
    }

    public SlsTrackingBean() {
    }

    public void backToSearchACE(ActionEvent actionEvent) {
        OperationBinding ob = ADFBeanUtils.findOperation("searchByDefaultSetting");
        ob.getParamsMap().put("slsDocId", actionEvent.getComponent().getAttributes().get("slsDocId"));
        ob.execute();
    }

    public void slsOrderACE(ActionEvent actionEvent) {
        System.out.println("in salesorder action listner");
        OperationBinding ob = ADFBeanUtils.findOperation("filterSalesDeliverySchedule");
        ob.getParamsMap().put("slsDocId", actionEvent.getComponent().getAttributes().get("slsDocId"));
        ob.execute();
        setShowDetail(new StringBuffer("S"));
        System.out.println(getShowDetail());
    }


    public void pdoDetailsACE(ActionEvent actionEvent) {
        setShowDetail(new StringBuffer("P"));
        System.out.println(getShowDetail());
        OperationBinding ob = ADFBeanUtils.findOperation("setPdoParameterDetails");
        ob.getParamsMap().put("slsDocId", actionEvent.getComponent().getAttributes().get("slsDocId"));
        ob.execute();
    }

    public void mnfProcessACE(ActionEvent actionEvent) {
        OperationBinding ob = ADFBeanUtils.findOperation("showInProcessDetails");
        ob.getParamsMap().put("slsDocId", actionEvent.getComponent().getAttributes().get("slsDocId"));
        ob.execute();
        setShowDetail(new StringBuffer("M"));
        System.out.println(getShowDetail());
    }

    public void mnfCompletedACE(ActionEvent actionEvent) {
        OperationBinding ob = ADFBeanUtils.findOperation("showCompletedMNF");
        ob.getParamsMap().put("slsDocId", actionEvent.getComponent().getAttributes().get("slsDocId"));
        ob.execute();

        setShowDetail(new StringBuffer("C"));
        System.out.println(getShowDetail());
    }

    public void qcDetailsACE(ActionEvent actionEvent) {
        OperationBinding ob = ADFBeanUtils.findOperation("showQcProcess");
        ob.getParamsMap().put("slsDocId", actionEvent.getComponent().getAttributes().get("slsDocId"));
        ob.execute();

        setShowDetail(new StringBuffer("Q"));
        System.out.println(getShowDetail());
    }

    public void stockUpdateACE(ActionEvent actionEvent) {
        OperationBinding ob = ADFBeanUtils.findOperation("showreceptDetail");
        ob.getParamsMap().put("slsDocId", actionEvent.getComponent().getAttributes().get("slsDocId"));
        ob.execute();

        setShowDetail(new StringBuffer("U"));
        System.out.println(getShowDetail());
    }

    public void prdDeliveredACE(ActionEvent actionEvent) {
        System.out.println("********* in shipment action Listner *********");
        this.setMode("V");
        OperationBinding ob = ADFBeanUtils.findOperation("showShipDetails");
        ob.getParamsMap().put("slsDocId", actionEvent.getComponent().getAttributes().get("slsDocId"));
        ob.execute();

        setShowDetail(new StringBuffer("D"));
        System.out.println(getShowDetail());
    }

    public void slsInvoiceACE(ActionEvent actionEvent) {
        OperationBinding ob = ADFBeanUtils.findOperation("showInvoiceDetails");
        ob.getParamsMap().put("slsDocId", actionEvent.getComponent().getAttributes().get("slsDocId"));
        ob.execute();
        setShowDetail(new StringBuffer("I"));
        System.out.println(getShowDetail());
    }

    public void refreshTrackingACE(ActionEvent actionEvent) {
        System.out.println(getStage() + " the previous stage");
        String soId = actionEvent.getComponent().getAttributes().get("slsDocId").toString();
        OperationBinding ob = ADFBeanUtils.findOperation("getCurrentOrderStage");
        ob.getParamsMap().put("soDocId", soId);
        ob.execute();
        setStage((Integer) ob.getResult());
        System.out.println(getStage() + "Current stage");

        OperationBinding ob2 = ADFBeanUtils.findOperation("oppQuotTracking");
        ob2.getParamsMap().put("soDocId", soId);
        ob2.execute();
        setSoSrc((Integer) ob2.getResult());


    }

    public void itemFilteronTypeACE(ActionEvent actionEvent) {
        OperationBinding ob = ADFBeanUtils.findOperation("showInputItm");
        ob.getParamsMap().put("pdoDocId", actionEvent.getComponent().getAttributes().get("pdoDocId"));
        ob.getParamsMap().put("itemType", actionEvent.getComponent().getAttributes().get("itmType"));
        ob.execute();
    }

    public void showItemPerOpACE(ActionEvent actionEvent) {
        OperationBinding ob = ADFBeanUtils.findOperation("showOperationItm");
        ob.getParamsMap().put("pdoDocId", actionEvent.getComponent().getAttributes().get("pdoDocId"));
        ob.getParamsMap().put("opDocId", actionEvent.getComponent().getAttributes().get("oprDocId"));
        ob.execute();
    }

    public void oprViseJCRCDetailsACE(ActionEvent actionEvent) {
        OperationBinding ob = ADFBeanUtils.findOperation("showJcPerOper");
        ob.getParamsMap().put("oprDocId", actionEvent.getComponent().getAttributes().get("opDocId"));
        ob.getParamsMap().put("oprSrNo", actionEvent.getComponent().getAttributes().get("oprSrNo"));
        ob.execute();

    }

    public void opportunityACE(ActionEvent actionEvent) {
        System.out.println("in opportunity action listner");
        OperationBinding ob1 = ADFBeanUtils.findOperation("filterOpportunity");
        ob1.execute();
        OperationBinding ob2 = ADFBeanUtils.findOperation("displayOppGraph");
        ob2.execute();
        setShowDetail(new StringBuffer("Op"));
        System.out.println(getShowDetail());
    }

    public void qutationACE(ActionEvent actionEvent) {
        System.out.println("in Quotation action listner");
        OperationBinding ob = ADFBeanUtils.findOperation("filterQuotation");
        ob.execute();
        setShowDetail(new StringBuffer("Qt"));
        System.out.println(getShowDetail());
    }


    public void shipmentACE(ActionEvent actionEvent) {
        System.out.println("in shipment action listner");
        System.out.println("actionEvent.getComponent().getAttributes().get(\"shipDocId\") :::: " +
                           actionEvent.getComponent().getAttributes().get("shipDocId"));
        OperationBinding ob = ADFBeanUtils.findOperation("filterCompleteShipmentDetail");
        ob.getParamsMap().put("shipDocId", actionEvent.getComponent().getAttributes().get("shipDocId"));
        ob.execute();

    }

    public String filterShipmentIdAction() {
        System.out.println("In Action Of shipment ID ::::");
        this.setMode("A");
        OperationBinding ob = ADFBeanUtils.findOperation("completeShipmentFilterDetail");
        System.out.println("shipDocIdBind.getValue().toString() ::: " + shipDocIdBind.getValue().toString());
        ob.getParamsMap().put("shipDocId",
                              shipDocIdBind.getValue() == null ? " " : shipDocIdBind.getValue().toString());
        ob.execute();
        return null;
    }

    public void setShipIdBind(RichLink shipIdBind) {
        this.shipIdBind = shipIdBind;
    }

    public RichLink getShipIdBind() {
        return shipIdBind;
    }

    public void setShipDocIdBind(RichInputText shipDocIdBind) {
        this.shipDocIdBind = shipDocIdBind;
    }

    public RichInputText getShipDocIdBind() {
        return shipDocIdBind;
    }
}
