package mnfPlanningBoardApp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.bean.StaticValue;

import adf.utils.ebiz.EbizParamsAPPUtils;
import adf.utils.ebiz.EbizParamsMMUtils;
import adf.utils.model.ADFModelUtils;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import mnfPlanningBoardApp.model.services.mnfPlanningBoardAppAMImpl;

import mnfPlanningBoardApp.view.utils.ADFUtils;

import mnfPlanningBoardApp.view.utils.JSFUtils;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.data.RichListItem;
import oracle.adf.view.rich.component.rich.data.RichListView;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.layout.RichDecorativeBox;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;

import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;

import oracle.jbo.server.ApplicationModuleImpl;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.event.SelectionEvent;

public class OverviewBean {
    private UIXSwitcher prdDetailsSwitcherBinding;
    private RichOutputFormatted outItmIdBinding;
    private RichOutputFormatted dlvDateBinding;
    private RichOutputText plnDocIdBinding;
    private RichOutputFormatted txnSrcDocIdBinding;
    private RichOutputFormatted txnPlnDocIdBinding;
    private RichOutputFormatted txnOutItmIdBinding;
    private RichOutputFormatted txnPrdDlvDtBinding;
    private RichOutputFormatted ovrPlnDocIdBinding;
    private RichListView inSourceListBinding;
    private RichListView txnRmViewListBinding;
    private RichDecorativeBox txnRmContainerBinding;
    private RichListView qtySupPerRmlistBinding;
    private RichListView suppPerRMListBinding;
    private RichListView moreSuppForRmListBinding;
    private RichInputDate estStrtDtBinding;
    private RichInputDate estEndDtBinding;
    private RichListView pdoGenViewListBinding;
    private RichListItem pdoHeaderListBinding;

    public OverviewBean() {
    }
    private StringBuffer editMode = new StringBuffer("V");
    private StringBuffer switchMode = new StringBuffer();
    private StringBuffer prevPage = new StringBuffer();
    protected String itemImageLink = "";
    private StringBuffer ovrPrdDetMode = new StringBuffer("V");
    private StringBuffer txnPrdDetMode = new StringBuffer("V");
    private StringBuffer executeMode = new StringBuffer();

    public void setExecuteMode(StringBuffer executeMode) {
        this.executeMode = executeMode;
    }

    public StringBuffer getExecuteMode() {
        return executeMode;
    }
    private Integer pageNavId = 0;

    private Timestamp currentTime = StaticValue.getTruncatedCurrDt();

    public void setPageNavId(Integer pageNavId) {
        this.pageNavId = pageNavId;
    }

    public void setCurrentTime(Timestamp currentTime) {
        this.currentTime = currentTime;
    }

    public Timestamp getCurrentTime() {
        return currentTime;
    }

    public void setEditMode(StringBuffer editMode) {
        this.editMode = editMode;
    }

    public StringBuffer getEditMode() {
        return editMode;
    }

    public Integer getPageNavId() {
        return pageNavId;
    }

    private String itmId = null;
    private String prdId = null;
    private String planId = null;
    private String srcId = null;
    private Timestamp dlvDt = null;

    public void setItmId(String itmId) {
        this.itmId = itmId;
    }

    public String getItmId() {
        return itmId;
    }

    public void setPrdId(String prdId) {
        this.prdId = prdId;
    }

    public String getPrdId() {
        return prdId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public String getSrcId() {
        return srcId;
    }

    public void setDlvDt(Timestamp dlvDt) {
        this.dlvDt = dlvDt;
    }

    public Timestamp getDlvDt() {
        return dlvDt;
    }

    OperationBinding ob = null;

    public void setOvrPrdDetMode(StringBuffer ovrPrdDetMode) {
        this.ovrPrdDetMode = ovrPrdDetMode;
    }

    public StringBuffer getOvrPrdDetMode() {
        return ovrPrdDetMode;
    }

    public void setTxnPrdDetMode(StringBuffer txnPrdDetMode) {
        this.txnPrdDetMode = txnPrdDetMode;
    }

    public StringBuffer getTxnPrdDetMode() {
        return txnPrdDetMode;
    }

    public void setItemImageLink(String itemImageLink) {
        this.itemImageLink = itemImageLink;
    }

    public String getItemImageLink() {
        if (itemImageLink == null) {
            ob = ADFUtils.findOperation("getImagePathFromServer");
            ob.execute();

            if (ob.getResult() != null) {
                itemImageLink = ob.getResult().toString();
                System.out.println(" path in bean " + itemImageLink);
            }
        }


        return itemImageLink;
    }

    public void setPrevPage(StringBuffer prevPage) {
        this.prevPage = prevPage;
    }

    public StringBuffer getPrevPage() {
        return prevPage;
    }

    public void setSwitchMode(StringBuffer switchMode) {
        this.switchMode = switchMode;
    }

    public StringBuffer getSwitchMode() {
        return switchMode;
    }


    public String srcToPrdDetails() {

        /*  System.out.println(txnOutItmIdBinding.getValue() + " ----------------------------------");
        System.out.println(txnPrdDlvDtBinding.getValue() + " ----------------------------------");
        System.out.println(txnPlnDocIdBinding.getValue() + " ----------------------------------");
        System.out.println(txnSrcDocIdBinding.getValue() + " ----------------------------------"); */
        //String out_itm_id, String src_doc_id, Timestamp dlv_dt, String pln_doc_id
        setSwitchMode(new StringBuffer("IS"));


        return "insrcToPrd";
    }

    public void txnProductSeleACE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("setViewProductTxnBind");
        ob.getParamsMap().put("out_itm_id", actionEvent.getComponent().getAttributes().get("OuputItmId").toString());
        ob.getParamsMap().put("dlv_dt", actionEvent.getComponent().getAttributes().get("dlvDt").toString());
        ob.getParamsMap().put("pln_doc_id", actionEvent.getComponent().getAttributes().get("planId").toString());
        ob.getParamsMap().put("src_doc_id", actionEvent.getComponent().getAttributes().get("srcId").toString());

        ob.execute();

        OperationBinding bin = ADFUtils.findOperation("setViewTxnRawMatBind");
        bin.getParamsMap().put("prd_id", actionEvent.getComponent().getAttributes().get("OuputItmId").toString());
        bin.getParamsMap().put("dlv_dt", actionEvent.getComponent().getAttributes().get("dlvDt").toString());
        bin.getParamsMap().put("plan_id", actionEvent.getComponent().getAttributes().get("planId").toString());
        bin.getParamsMap().put("src_id", actionEvent.getComponent().getAttributes().get("srcId").toString());
        bin.execute();
        //  System.out.println(" inside see details plan");
    }

    public void overPrdDetailsACE(ActionEvent actionEvent) {

    }

    public String prdDetailToRmAction() {

        setItmId(getAttrsVal("VIEWTXNRawMatIterator", "InptItmId").toString());
        setPrdId(getAttrsVal("VIEWTXNRawMatIterator", "OutptItmId").toString());
        setPlanId(getAttrsVal("VIEWTXNRawMatIterator", "PlnDocId").toString());
        setSrcId(getAttrsVal("VIEWTXNRawMatIterator", "DocIdSrc").toString());
        Timestamp dlv_dt = (Timestamp) getAttrsVal("VIEWTXNRawMatIterator", "TxnDocDlvDt");
        //System.out.println(dlv_dt + "    delivery date in  taskflow");
        setDlvDt(dlv_dt);
        refreshPage();

        // setting page id to go for detail page
        setPageNavId(2);
        return "prdToRawMain";
    }

    public String planRawMateAction() {
        //setItmId(getAttrsVal("MnfPlnPrdVOIterator", "InptItmId").toString());
        setPrdId(getAttrsVal("MnfPlnPrdVOIterator", "OutptItmId").toString());
        setPlanId(getAttrsVal("MnfPlnPrdVOIterator", "PlnDocId").toString());
        //setSrcId(getAttrsVal("MnfPlnPrdVOIterator", "DocIdSrc").toString());
        setDlvDt((Timestamp) getAttrsVal("MnfPlnPrdVOIterator", "TxnDocDlvDt"));
        refreshPage();

        // setting page id to go for detail page
        setPageNavId(2);
        return "prdToRawMain";

    }

    public String ovrviewToPrdDetails() {


        setSwitchMode(new StringBuffer("OV"));
        return "overToPrdDtls";
    }

    public void overViewToDetailACE(ActionEvent actionEvent) {
        ob = ADFUtils.findOperation("setViewOverViewcustomerBind");
        ob.getParamsMap().put("out_itm_id", actionEvent.getComponent().getAttributes().get("outItmId").toString());
        ob.getParamsMap().put("dlv_dt", (Timestamp) actionEvent.getComponent().getAttributes().get("dlvdt"));
        ob.getParamsMap().put("pln_doc_id", actionEvent.getComponent().getAttributes().get("planId").toString());
        ob.execute();

        ob = ADFUtils.findOperation("setViewOverViewRawMatBind");
        ob.getParamsMap().put("out_itm_id", actionEvent.getComponent().getAttributes().get("outItmId").toString());
        ob.getParamsMap().put("dlv_dt", (Timestamp) actionEvent.getComponent().getAttributes().get("dlvdt"));
        ob.getParamsMap().put("pln_doc_id", actionEvent.getComponent().getAttributes().get("planId").toString());
        ob.execute();

        ADFUtils.findOperation("setMnfPlnPrdView").execute();
    }

    public void setPrdDetailsSwitcherBinding(UIXSwitcher prdDetailsSwitcherBinding) {
        this.prdDetailsSwitcherBinding = prdDetailsSwitcherBinding;
    }

    public UIXSwitcher getPrdDetailsSwitcherBinding() {
        return prdDetailsSwitcherBinding;
    }

    public void refreshInputSrcACE(ActionEvent actionEvent) {
        ADFUtils.findOperation("refreshInputSrc").execute();
    }

    public void saveInputSrcACE(ActionEvent actionEvent) {

        ADFUtils.findOperation("Commit").execute();
        ADFUtils.findOperation("beforeSave").execute();
        ADFUtils.findOperation("Commit").execute();
        JSFUtils.addFacesInformationMessage("Record Saved.");

    }

    public void continueInputSrcACE(ActionEvent actionEvent) {


    }

    public void refreshOverviewACE(ActionEvent actionEvent) {
        ADFUtils.findOperation("refreshoverView").execute();
        ADFUtils.findOperation("setOverviewGraphParamter").execute();
    }

    public void saveOverviewACE(ActionEvent actionEvent) {
        ADFUtils.findOperation("Commit1").execute();
        ADFUtils.findOperation("beforeSave").execute();
        ADFUtils.findOperation("Commit1").execute();

        JSFUtils.addFacesInformationMessage("Record Saved.");

    }

    /**
     *Method to generate new plan
     * @param actionEvent
     */
    public void generatePlanACE(ActionEvent actionEvent) {
        ADFUtils.findOperation("beforeSave").execute();

        ADFUtils.findOperation("callSoInsertFun").execute();
        // System.out.println("function called");
    }

    /**
     *Method to plan the selected order
     * @return
     */
    public String continueAction() {
        ob = ADFUtils.findOperation("getSelectedRowCount");
        ob.execute();
        Integer result = (Integer) ob.getResult();
        if (result.compareTo(0) == 1) {
            ADFUtils.findOperation("insertPrdFromTxn").execute();
            ADFUtils.findOperation("Commit1").execute();
            ADFUtils.findOperation("beforeSave").execute();
            ADFUtils.findOperation("updateTxnState").execute();
            ADFUtils.findOperation("Commit1").execute();
            ADFUtils.findOperation("refreshInputSrc").execute();
            ADFUtils.findOperation("setOverviewGraphParamter").execute();

            AdfFacesContext.getCurrentInstance().addPartialTarget(inSourceListBinding);
            return "insrcToOver";
        } else {
            //JSFUtils.addFacesInformationMessage("Please select Order to Continue.");
            ADFModelUtils.showFormattedFacesMessage("Order not Selected!",
                                                    "Please Select atleast one Order to Continue.",
                                                    FacesMessage.SEVERITY_WARN);
        }
        return "";
    }

    public void backInputSrcACE(ActionEvent actionEvent) {
        ADFUtils.findOperation("beforeSave").execute();
    }

    public void setOutItmIdBinding(RichOutputFormatted outItmIdBinding) {
        this.outItmIdBinding = outItmIdBinding;
    }

    public RichOutputFormatted getOutItmIdBinding() {
        return outItmIdBinding;
    }

    public void setDlvDateBinding(RichOutputFormatted dlvDateBinding) {
        this.dlvDateBinding = dlvDateBinding;
    }

    public RichOutputFormatted getDlvDateBinding() {
        return dlvDateBinding;
    }

    public void setPlnDocIdBinding(RichOutputText plnDocIdBinding) {
        this.plnDocIdBinding = plnDocIdBinding;
    }

    public RichOutputText getPlnDocIdBinding() {
        return plnDocIdBinding;
    }

    public void setTxnSrcDocIdBinding(RichOutputFormatted txnSrcDocIdBinding) {
        this.txnSrcDocIdBinding = txnSrcDocIdBinding;
    }

    public RichOutputFormatted getTxnSrcDocIdBinding() {
        return txnSrcDocIdBinding;
    }

    public void setTxnPlnDocIdBinding(RichOutputFormatted txnPlnDocIdBinding) {
        this.txnPlnDocIdBinding = txnPlnDocIdBinding;
    }

    public RichOutputFormatted getTxnPlnDocIdBinding() {
        return txnPlnDocIdBinding;
    }

    public void setTxnOutItmIdBinding(RichOutputFormatted txnOutItmIdBinding) {
        this.txnOutItmIdBinding = txnOutItmIdBinding;
    }

    public RichOutputFormatted getTxnOutItmIdBinding() {
        return txnOutItmIdBinding;
    }

    public void setTxnPrdDlvDtBinding(RichOutputFormatted txnPrdDlvDtBinding) {
        this.txnPrdDlvDtBinding = txnPrdDlvDtBinding;
    }

    public RichOutputFormatted getTxnPrdDlvDtBinding() {
        return txnPrdDlvDtBinding;
    }

    public void setOvrPlnDocIdBinding(RichOutputFormatted ovrPlnDocIdBinding) {
        this.ovrPlnDocIdBinding = ovrPlnDocIdBinding;
    }

    public RichOutputFormatted getOvrPlnDocIdBinding() {
        return ovrPlnDocIdBinding;
    }


    public void searchInputSrcACE(ActionEvent actionEvent) {
        ADFUtils.findOperation("searchInputSrc").execute();
    }

    public void resetInputSrcACE(ActionEvent actionEvent) {
        ADFUtils.findOperation("resetInputSrc").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(inSourceListBinding);
    }

    public void customerSL(SelectionEvent selectionEvent) {
        ADFUtils.invokeEL("#{bindings.VIEWOverviewCustomerVO.treeModel.makeCurrent}", new Class[] {
                          SelectionEvent.class }, new Object[] { selectionEvent });

        ADFUtils.findOperation("setViewByOverCustRawMatBind").execute();
    }

    public void refreshPrdDetailsACE(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("refreshPrdDetails");
        ob.getParamsMap().put("prd_id", getAttrsVal("MnfPlnTxnVOIterator", "OutptItmId"));
        ob.getParamsMap().put("plan_id", getAttrsVal("MnfPlnTxnVOIterator", "PlnDocId"));
        ob.getParamsMap().put("dlv_dt", getAttrsVal("MnfPlnTxnVOIterator", "TxnDocDlvDt"));
        ob.execute();
    }

    public void selectAllPlanACE(ActionEvent actionEvent) {
        ADFUtils.findOperation("selectAllPdo").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(inSourceListBinding);
    }


    StringBuffer frez_status = new StringBuffer("F");

    public void releaseOverViewPrdACE(ActionEvent actionEvent) {
        setFrez_status(new StringBuffer("F"));
        // System.out.println(getFrez_status());
    }

    public void freezeOvrViewPrdACE(ActionEvent actionEvent) {
        setFrez_status(new StringBuffer("R"));
        // System.out.println(getFrez_status());
    }

    public void setFrez_status(StringBuffer frez_status) {
        this.frez_status = frez_status;
    }

    public StringBuffer getFrez_status() {
        return frez_status;
    }


    public void refreshDetailsSrcACE(ActionEvent actionEvent) {
        reInsertPlanState();
    }

    protected void reInsertPlanState() {
        ADFUtils.findOperation("updatePlanDate").execute();
        ADFUtils.findOperation("Commit").execute();
        ADFUtils.findOperation("reinsertBomNItem").execute();
        OperationBinding ob = ADFUtils.findOperation("refreshPrdDetails");
        ob.getParamsMap().put("prd_id", getAttrsVal("MnfPlnTxnVOIterator", "OutptItmId"));
        ob.getParamsMap().put("plan_id", getAttrsVal("MnfPlnTxnVOIterator", "PlnDocId"));
        ob.getParamsMap().put("dlv_dt", getAttrsVal("MnfPlnTxnVOIterator", "TxnDocDlvDt"));
        ob.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(txnRmViewListBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(txnRmContainerBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(estStrtDtBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(estEndDtBinding);
    }

    public String ProcessDetailSrcAction() {

        ADFUtils.findOperation("insertPlanFromDetail").execute();
        // System.out.println("function is called to insert plan from TXN Details");
        return "prdToOver";

    }

    /**
     * Method to set value for an attribute of current row for an Iterator
     * @param iter for iterator name
     * @param for attribute name
     * @param for attribute value
     * **/
    public void setAttrsVal(String iter, String attrs, Object val) {

        if (iter != null && attrs != null) {
            DCIteratorBinding dcIter = ADFUtils.findIterator(iter);

            if (dcIter.getCurrentRow() != null)
                dcIter.getCurrentRow().setAttribute(attrs, val);

        }
    }

    /**
     * Method to get value of attribute of current row for an Iterator
     * @param iter for iterator name
     * @param for attribute name
     * **/
    public Object getAttrsVal(String iter, String attrs) {

        if (iter != null && attrs != null) {
            DCIteratorBinding dcIter = ADFUtils.findIterator(iter);
            // System.out.println("dcIter " + dcIter.getEstimatedRowCount());
            if (dcIter.getCurrentRow() != null)
                return dcIter.getCurrentRow().getAttribute(attrs);

        }
        return null;
    }

    public void refreshPage() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String refreshpage = fc.getViewRoot().getViewId();
        ViewHandler ViewH = fc.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fc, refreshpage);
        UIV.setViewId(refreshpage);
        fc.setViewRoot(UIV);
    }

    /**
     *method to filter the overview product page
     * @param actionEvent
     */
    public void filterOverviewpageACE(ActionEvent actionEvent) {
        // Add event code herefilterOverviewPage
        //System.out.println("the Vurrent ratin");
        //System.out.println(getAttrsVal("DualOverviewPageIterator", "Prdrating"));
        ADFUtils.findOperation("filterPlanForSearch").execute();


    }

    public void setInSourceListBinding(RichListView inSourceListBinding) {
        this.inSourceListBinding = inSourceListBinding;
    }

    public RichListView getInSourceListBinding() {
        return inSourceListBinding;
    }

    public String txnRawMatPlanAction() {
        //setItmId(getAttrsVal("MnfPlnPrdVOIterator", "InptItmId").toString());
        setPrdId(getAttrsVal("MnfPlnTxnVOIterator", "OutptItmId").toString());
        setPlanId(getAttrsVal("MnfPlnTxnVOIterator", "PlnDocId").toString());
        //setSrcId(getAttrsVal("MnfPlnPrdVOIterator", "DocIdSrc").toString());
        setDlvDt((Timestamp) getAttrsVal("MnfPlnTxnVOIterator", "TxnDocDlvDt"));
        // refreshPage();
        setSwitchMode(new StringBuffer("IS"));

        // setting page id to go for detail page
        setPageNavId(2);
        return "prdToRawMain";
    }


    public void freezeACE(ActionEvent actionEvent) {
        ADFUtils.findOperation("freezeMethod").execute();
        OperationBinding ob = ADFUtils.findOperation("refreshPrdDetails");
        ob.getParamsMap().put("prd_id", getAttrsVal("MnfPlnTxnVOIterator", "OutptItmId"));
        ob.getParamsMap().put("plan_id", getAttrsVal("MnfPlnTxnVOIterator", "PlnDocId"));
        ob.getParamsMap().put("dlv_dt", getAttrsVal("MnfPlnTxnVOIterator", "TxnDocDlvDt"));
        ob.execute();
    }

    public void editTxnDetailACE(ActionEvent actionEvent) {
        setEditMode(new StringBuffer("E"));
    }

    public void saveTxnDetailACE(ActionEvent actionEvent) {
        reInsertPlanState();
        setEditMode(new StringBuffer("V"));
        ADFUtils.findOperation("Commit").execute();
        ADFUtils.findOperation("beforeSave").execute();
        ADFUtils.findOperation("Commit").execute();
        // OperationBinding ob =  ADFUtils.findOperation("refreshPrdDetails");
        //ob.getParamsMap().put("prd_id", getAttrsVal("MnfPlnTxnVOIterator", "OutptItmId"));
        //ob.getParamsMap().put("plan_id", getAttrsVal("MnfPlnTxnVOIterator", "PlnDocId"));
        //ob.getParamsMap().put("dlv_dt", getAttrsVal("MnfPlnTxnVOIterator", "TxnDocDlvDt"));
        // ob.execute();
        JSFUtils.addFacesInformationMessage("Record Saved");
    }

    public void setTxnRmViewListBinding(RichListView txnRmViewListBinding) {
        this.txnRmViewListBinding = txnRmViewListBinding;
    }

    public RichListView getTxnRmViewListBinding() {
        return txnRmViewListBinding;
    }

    public void setTxnRmContainerBinding(RichDecorativeBox txnRmContainerBinding) {
        this.txnRmContainerBinding = txnRmContainerBinding;
    }

    public RichDecorativeBox getTxnRmContainerBinding() {
        return txnRmContainerBinding;
    }

    public void deselectAllPlnACE(ActionEvent actionEvent) {
        ADFUtils.findOperation("unselectAllPdo").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(inSourceListBinding);
    }
    private StringBuffer rmPlnMode = new StringBuffer("RMV");

    public void setRmPlnMode(StringBuffer rmPlnMode) {
        this.rmPlnMode = rmPlnMode;
    }

    public StringBuffer getRmPlnMode() {
        return rmPlnMode;
    }

    public void planRmPerTxnACE(ActionEvent actionEvent) {
        setRmPlnMode(new StringBuffer("RME"));
        OperationBinding ob = ADFUtils.findOperation("setFilterForRawMaterialPerCustomer");
        ob.getParamsMap().put("pln_doc_Id", getAttrsVal("MnfPlnTxnVOIterator", "PlnDocId").toString());
        ob.getParamsMap().put("output_id", getAttrsVal("MnfPlnTxnVOIterator", "OutptItmId").toString());
        ob.getParamsMap().put("src_id", getAttrsVal("MnfPlnTxnVOIterator", "DocIdSrc").toString());
        ob.getParamsMap().put("dlv_dt", (Timestamp) getAttrsVal("MnfPlnTxnVOIterator", "TxnDocDlvDt"));
        ob.execute();
    }

    public void backRMPlnACE(ActionEvent actionEvent) {


        ADFUtils.findOperation("Commit").execute();
        setRmPlnMode(new StringBuffer("RMV"));
        // checkSupQtyValidation();
        OperationBinding bin = ADFUtils.findOperation("setViewTxnRawMatBind");
        bin.getParamsMap().put("prd_id", getAttrsVal("MnfPlnTxnVOIterator", "OutptItmId"));
        bin.getParamsMap().put("dlv_dt", getAttrsVal("MnfPlnTxnVOIterator", "TxnDocDlvDt"));
        bin.getParamsMap().put("plan_id", getAttrsVal("MnfPlnTxnVOIterator", "PlnDocId"));
        bin.getParamsMap().put("src_id", getAttrsVal("MnfPlnTxnVOIterator", "DocIdSrc"));
        bin.execute();
        setItmDescForSup(null);
        setOrder_Qty(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(txnRmViewListBinding);

    }

    private String rm_Id;
    private String pln_Id;
    private oracle.jbo.domain.Timestamp prd_dlv_Dt;
    private String BOM_ID;
    private String DOC_ID_SRC;
    private String OUTPT_ITM_ID;
    private oracle.jbo.domain.Timestamp INPT_ITM_REQ_DT;
    private oracle.jbo.domain.Number order_Qty;
    private String itmDescForSup;

    public void setItmDescForSup(String itmDescForSup) {
        this.itmDescForSup = itmDescForSup;
    }

    public String getItmDescForSup() {
        return itmDescForSup;
    }

    public void setOrder_Qty(oracle.jbo.domain.Number order_Qty) {
        this.order_Qty = order_Qty;
    }

    public oracle.jbo.domain.Number getOrder_Qty() {
        return order_Qty;
    }

    protected mnfPlanningBoardAppAMImpl getApplicationModule() {
        return (mnfPlanningBoardAppAMImpl) ADFModelUtils.resolvEl("#{data.mnfPlanningBoardAppAMDataControl.dataProvider}");
    }

    public void itmSelectionACE(ActionEvent actionEvent) {

        rm_Id = actionEvent.getComponent().getAttributes().get("itmId").toString();
        pln_Id = actionEvent.getComponent().getAttributes().get("planId").toString();
        DOC_ID_SRC = actionEvent.getComponent().getAttributes().get("srcId").toString();
        prd_dlv_Dt = (oracle.jbo.domain.Timestamp) actionEvent.getComponent().getAttributes().get("dlvDt");
        // System.out.println(prd_dlv_Dt + " Product delivery date #####################################################");
        OUTPT_ITM_ID = actionEvent.getComponent().getAttributes().get("outItmId").toString();
        BOM_ID = actionEvent.getComponent().getAttributes().get("bomId").toString();
        INPT_ITM_REQ_DT = (oracle.jbo.domain.Timestamp) actionEvent.getComponent().getAttributes().get("itmReqDt");
        order_Qty = (oracle.jbo.domain.Number) actionEvent.getComponent().getAttributes().get("orderQty");
        System.out.println("Item Id = " + rm_Id);
        Object itmDesc = EbizParamsAPPUtils.getItemDescFrmItmId(getApplicationModule(), rm_Id);
        setItmDescForSup(itmDesc == null ? null : itmDesc.toString());

        OperationBinding binding = ADFUtils.findOperation("setFilterForSuppl");
        // binding.getParamsMap().put("itm_id", actionEvent.getComponent().getAttributes().get("itmId"));
        binding.getParamsMap().put("inItm_id", rm_Id);
        binding.getParamsMap().put("plnDocId", pln_Id);
        binding.getParamsMap().put("srcId", DOC_ID_SRC);
        binding.getParamsMap().put("outItmId", OUTPT_ITM_ID);
        binding.getParamsMap().put("dlvDt", prd_dlv_Dt);
        binding.getParamsMap().put("bomId", BOM_ID);
        binding.getParamsMap().put("itmReqDt", INPT_ITM_REQ_DT);

        binding.execute();

        //        AdfFacesContext.getCurrentInstance().addPartialTarget(qtySupPerRmlistBinding);


        // OperationBinding binding1 = ADFUtils.findOperation("setParamInGraphRmMaxMinLine");
        // binding1.getParamsMap().put("itm_Id", actionEvent.getComponent().getAttributes().get("itmId"));

        // binding1.execute();

    }

    public void setQtySupPerRmlistBinding(RichListView qtySupPerRmlistBinding) {
        this.qtySupPerRmlistBinding = qtySupPerRmlistBinding;
    }

    public RichListView getQtySupPerRmlistBinding() {
        return qtySupPerRmlistBinding;
    }

    public void selectSuppToAddVCL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...






    }

    public void addSupplierACE(ActionEvent actionEvent) {
        OperationBinding binding = ADFUtils.findOperation("chckSelectedSupp");
        binding.execute();
        Integer ch = (Integer) binding.getResult();
        if (ch.compareTo(1) == 0) {
            if (order_Qty.compareTo(0) == 1) {
                setValueToAMImpl();
                //setSupQty();
                // System.out.println("Called the insert function fronm beea");
                //  ADFUtils.showPopup(quantityPopBinding, true);
            } else {
                JSFUtils.addFacesInformationMessage(" Required quantity is available.");
            }
        }

        else {
            // ADFModelUtils.showFacesMessage("Please select Supplier to Make Order", "",  null);
            JSFUtils.addFacesInformationMessage("Please select Supplier to Add");
        }
    }

    protected void setSupQty() {
        OperationBinding ob = ADFUtils.findOperation("supplierQtyDistribute");
        ob.getParamsMap().put("PLN_DOC_ID", pln_Id);
        ob.getParamsMap().put("DOC_ID_SRC", DOC_ID_SRC);
        ob.getParamsMap().put("OUTPT_ITM_ID", OUTPT_ITM_ID);

        ob.getParamsMap().put("TXN_DOC_DLV_DT", prd_dlv_Dt);
        ob.getParamsMap().put("BOM_ID", BOM_ID);
        ob.getParamsMap().put("INPT_ITM_ID", rm_Id);
        ob.getParamsMap().put("INPT_ITM_REQ_DT", INPT_ITM_REQ_DT);
        ob.getParamsMap().put("tot_order_qty", order_Qty);
        ob.execute();

    }

    public void setValueToAMImpl() {
        OperationBinding ob = ADFUtils.findOperation("addSupplier");
        //        ob.getParamsMap().put("order_qty", orderQuantityBinding.getValue());
        ob.getParamsMap().put("PLN_DOC_ID", pln_Id);
        ob.getParamsMap().put("DOC_ID_SRC", DOC_ID_SRC);
        ob.getParamsMap().put("OUTPT_ITM_ID", OUTPT_ITM_ID);
        //System.out.println(dlvDt + " this is the delivery date ###############################");
        ob.getParamsMap().put("TXN_DOC_DLV_DT", prd_dlv_Dt);
        ob.getParamsMap().put("BOM_ID", BOM_ID);
        ob.getParamsMap().put("INPT_ITM_ID", rm_Id);
        ob.getParamsMap().put("INPT_ITM_REQ_DT", INPT_ITM_REQ_DT);
        /*
         * oracle.jbo.domain.Number supp_id, oracle.jbo.domain.Number order_qty, String PLN_DOC_ID,
                        String DOC_ID_SRC, String OUTPT_ITM_ID, Timestamp TXN_DOC_DLV_DT, String BOM_ID,
                        String INPT_ITM_ID, Timestamp INPT_ITM_REQ_DT
         */

        ob.execute();


        OperationBinding binding = ADFUtils.findOperation("setFilterForSuppl");
        binding.getParamsMap().put("inItm_id", rm_Id);
        binding.getParamsMap().put("plnDocId", pln_Id);
        binding.getParamsMap().put("srcId", DOC_ID_SRC);
        binding.getParamsMap().put("outItmId", OUTPT_ITM_ID);
        binding.getParamsMap().put("dlvDt", prd_dlv_Dt);
        binding.getParamsMap().put("bomId", BOM_ID);
        binding.getParamsMap().put("itmReqDt", INPT_ITM_REQ_DT);

        binding.execute();

        //        System.out.println(orderQuantityBinding.getValue());
        ADFBeanUtils.findIterator("MnfPlnSuppVOIterator").executeQuery();
        ADFBeanUtils.findIterator("LISTSupplierFullForRMDetailIterator").executeQuery();

        AdfFacesContext.getCurrentInstance().addPartialTarget(suppPerRMListBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(moreSuppForRmListBinding);

    }

    public void setSuppPerRMListBinding(RichListView suppPerRMListBinding) {
        this.suppPerRMListBinding = suppPerRMListBinding;
    }

    public RichListView getSuppPerRMListBinding() {
        return suppPerRMListBinding;
    }

    public void setMoreSuppForRmListBinding(RichListView moreSuppForRmListBinding) {
        this.moreSuppForRmListBinding = moreSuppForRmListBinding;
    }

    public RichListView getMoreSuppForRmListBinding() {
        return moreSuppForRmListBinding;
    }

    public void removeSupplierACE(ActionEvent actionEvent) {

        Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("supRowKey");

        rowDelete(key, "MnfPlnSuppVOIterator");

        ADFUtils.findOperation("Commit").execute();

        OperationBinding binding = ADFUtils.findOperation("setFilterForSuppl");
        binding.getParamsMap().put("inItm_id", rm_Id);
        binding.getParamsMap().put("plnDocId", pln_Id);
        binding.getParamsMap().put("srcId", DOC_ID_SRC);
        binding.getParamsMap().put("outItmId", OUTPT_ITM_ID);
        binding.getParamsMap().put("dlvDt", prd_dlv_Dt);
        binding.getParamsMap().put("bomId", BOM_ID);
        binding.getParamsMap().put("itmReqDt", INPT_ITM_REQ_DT);
        binding.execute();

        ADFBeanUtils.findIterator("MnfPlnSuppVOIterator").executeQuery();
        ADFBeanUtils.findIterator("LISTSupplierFullForRMDetailIterator").executeQuery();

        AdfFacesContext.getCurrentInstance().addPartialTarget(suppPerRMListBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(moreSuppForRmListBinding);

    }


    /**
     * Method to delete selected row
     *
     * */
    public void rowDelete(Key key, String iterName) {
        if (iterName != null && key != null) {
            ViewObject vo = ADFUtils.findIterator(iterName).getViewObject();
            // ViewObject vo = ADFBeanUtils.findIterator(iterName).getViewObject();
            Row row = vo.getRow(key);

            if (row != null)
                row.remove();
        }

    }

    public void orderQuantityValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            oracle.jbo.domain.Number curQty = (oracle.jbo.domain.Number) object;

            uIComponent.processUpdates(FacesContext.getCurrentInstance());
            /*  if (order_Qty.compareTo(calculateTotalQty()) !=0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Quanity Miss Match! Total quantity For All must be equal to Order Quantity",
                                                              null));
            }  */
            if (curQty.compareTo(0) == 0) {
                throw new ValidatorException(new FacesMessage("Order Quantity must be more than 0"));

            }

            if (ADFBeanUtils.isNumberNegative(curQty)) {
                throw new ValidatorException(new FacesMessage("Negative values not allowed "));

            } else if (!ADFBeanUtils.isPrecisionValid(curQty)) {
                throw new ValidatorException(new FacesMessage("Invalid Precision "));
            }
        }


    }

    public void orderQtyVCE(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    protected oracle.jbo.domain.Number calculateTotalQty() {

        OperationBinding ob = ADFUtils.findOperation("sumTotalSuppQty");
        ob.getParamsMap().put("PLN_DOC_ID", pln_Id);
        ob.getParamsMap().put("DOC_ID_SRC", DOC_ID_SRC);
        ob.getParamsMap().put("OUTPT_ITM_ID", OUTPT_ITM_ID);

        ob.getParamsMap().put("TXN_DOC_DLV_DT", prd_dlv_Dt);
        ob.getParamsMap().put("BOM_ID", BOM_ID);
        ob.getParamsMap().put("INPT_ITM_ID", rm_Id);
        ob.getParamsMap().put("INPT_ITM_REQ_DT", INPT_ITM_REQ_DT);
        ob.getParamsMap().put("tot_order_qty", order_Qty);
        ob.execute();

        oracle.jbo.domain.Number totQty = (oracle.jbo.domain.Number) ob.getResult();
        System.out.println(totQty + " Quantity that need to be equal to  " + order_Qty);
        return totQty;
    }

    public void perCustTxnDetialsACE(ActionEvent actionEvent) {


        ob = ADFUtils.findOperation("setViewProductTxnBind");
        ob.getParamsMap().put("out_itm_id", actionEvent.getComponent().getAttributes().get("prdId").toString());
        ob.getParamsMap().put("dlv_dt",
                              (oracle.jbo.domain.Timestamp) actionEvent.getComponent().getAttributes().get("dlvDt")); //dlvDt
        ob.getParamsMap().put("pln_doc_id", actionEvent.getComponent().getAttributes().get("plnId").toString()); //plnId
        ob.getParamsMap().put("src_doc_id", actionEvent.getComponent().getAttributes().get("srcId").toString()); //srcId

        ob.execute();

        ADFUtils.findOperation("setViewTxnRawMatBind").execute();
        setSwitchMode(new StringBuffer("IS"));
    }
    private Double gaugeValue = 0.0;

    public void prdRatingVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent != null) {
            gaugeValue = (Double) valueChangeEvent.getNewValue();


            setAttrsVal("DualInputSrcConfigIterator", "Priority", gaugeValue);
        }
    }

    public void setEstStrtDtBinding(RichInputDate estStrtDtBinding) {
        this.estStrtDtBinding = estStrtDtBinding;
    }

    public RichInputDate getEstStrtDtBinding() {
        return estStrtDtBinding;
    }

    public void setEstEndDtBinding(RichInputDate estEndDtBinding) {
        this.estEndDtBinding = estEndDtBinding;
    }

    public RichInputDate getEstEndDtBinding() {
        return estEndDtBinding;
    }

    public void freezeAllPlanACE(ActionEvent actionEvent) {
        // Add event code here... freezeAllOrder
        OperationBinding ob = ADFUtils.findOperation("freezeAllOrder");
        ob.getParamsMap().put("plan_Doc_Id", actionEvent.getComponent().getAttributes().get("planDocId").toString());
        ob.execute();

    }

    public void releaseAllPlanACE(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void planIdToSrcPageFilterACE(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("filterDataOnPlanId");
        ob.getParamsMap().put("planDocId", actionEvent.getComponent().getAttributes().get("planId").toString());
        //srcId
        ob.execute();
    }


    public void showPurchaseOrderACE(ActionEvent actionEvent) {
        setExecuteMode(new StringBuffer("PO"));
        OperationBinding ob = ADFUtils.findOperation("setExecutionGlobalParam");
        ob.getParamsMap().put("plan_doc_id", exc_plan_Doc_Id);
        ob.execute();
    }

    public void showProductionOrderACE(ActionEvent actionEvent) {
        setExecuteMode(new StringBuffer("PDO"));
        System.out.println(exc_plan_Doc_Id);
        OperationBinding ob = ADFUtils.findOperation("setExecutionGlobalParam");
        ob.getParamsMap().put("plan_doc_id", exc_plan_Doc_Id);
        ob.execute();

    }

    public void showJobCardACE(ActionEvent actionEvent) {
        setExecuteMode(new StringBuffer("JC"));
        System.out.println(exc_plan_Doc_Id);
        OperationBinding ob = ADFUtils.findOperation("setExecutionGlobalParam");
        ob.getParamsMap().put("plan_doc_id", exc_plan_Doc_Id);
        ob.execute();
    }

    private String planDispId;
    private String exc_plan_Doc_Id;
    private String chkPdoGen; // variable to hold value if production order has been generated
    private String chkPurGen; // Variable to hold value if purchase order has been generated.

    public void setChkPdoGen(String chkPdoGen) {
        this.chkPdoGen = chkPdoGen;
    }

    public String getChkPdoGen() {
        return chkPdoGen;
    }

    public void setChkPurGen(String chkPurGen) {
        this.chkPurGen = chkPurGen;
    }

    public String getChkPurGen() {
        return chkPurGen;
    }

    public void setExc_plan_Doc_Id(String exc_plan_Doc_Id) {
        this.exc_plan_Doc_Id = exc_plan_Doc_Id;
    }

    public String getExc_plan_Doc_Id() {
        return exc_plan_Doc_Id;
    }

    public void setPlanDispId(String planDispId) {
        this.planDispId = planDispId;
    }

    public String getPlanDispId() {
        return planDispId;
    }

    private Integer totalOrder;
    private Integer totalRawMaterial;

    public void setTotalOrder(Integer totalOrder) {
        this.totalOrder = totalOrder;
    }

    public Integer getTotalOrder() {
        return totalOrder;
    }

    public void setTotalRawMaterial(Integer totalRawMaterial) {
        this.totalRawMaterial = totalRawMaterial;
    }

    public Integer getTotalRawMaterial() {
        return totalRawMaterial;
    }

    public void planForExecuteACE(ActionEvent actionEvent) {
        // OperationBinding ob = ADFUtils.findOperation("filterDataOnPlanId");
        // ob.getParamsMap().put("planDocId", actionEvent.getComponent().getAttributes().get("planId").toString());
        // ob.execute();

        setPlanDispId(actionEvent.getComponent().getAttributes().get("planDisp").toString());
        setExc_plan_Doc_Id(actionEvent.getComponent().getAttributes().get("planId").toString());
        System.out.println(exc_plan_Doc_Id + "   ------------------------ stage 1");
        OperationBinding ord = ADFUtils.findOperation("getTotalOrder");
        ord.getParamsMap().put("plan_Id", actionEvent.getComponent().getAttributes().get("planId").toString());
        ord.execute();
        setTotalOrder((Integer) ord.getResult());

        OperationBinding prdC = ADFUtils.findOperation("chkPdoExist");
        prdC.getParamsMap().put("planDocId", actionEvent.getComponent().getAttributes().get("planId").toString());
        prdC.execute();
        setChkPdoGen((String) prdC.getResult());
        System.out.println(getChkPdoGen() + " pdo present");

        OperationBinding itm = ADFUtils.findOperation("getTotalRawMateril");
        itm.getParamsMap().put("plan_Id", actionEvent.getComponent().getAttributes().get("planId").toString());
        itm.execute();
        setTotalRawMaterial((Integer) itm.getResult());


    }

    public void genPdoACE(ActionEvent actionEvent) {
        //   System.out.println(exc_plan_Doc_Id + "in bean");
        //  System.out.println(actionEvent.getComponent().getAttributes().get("planDispId").toString() + " In bean");
        OperationBinding obs = ADFUtils.findOperation("genProductionOrder");
        //obs.getParamsMap().put("planDispId", actionEvent.getComponent().getAttributes().get("planDispId").toString());
        obs.getParamsMap().put("planDispId", exc_plan_Doc_Id);
        obs.execute();
        setChkPdoGen("true");
        ADFBeanUtils.findIterator("LISTExcOrderIterator").executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoHeaderListBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pdoGenViewListBinding);

        //
    }

    public void genPurchaseACE(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void genJobCardACE(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void validateSupQtyACE(ActionEvent actionEvent) {
        // Add event code here...
        // checkSupQtyValidation();
    }

    protected void checkSupQtyValidation() {
        OperationBinding ob = ADFUtils.findOperation("validateSupplierQty");

        ob.getParamsMap().put("PLN_DOC_ID", pln_Id);
        ob.getParamsMap().put("DOC_ID_SRC", DOC_ID_SRC);
        ob.getParamsMap().put("OUTPT_ITM_ID", OUTPT_ITM_ID);
        ob.getParamsMap().put("TXN_DOC_DLV_DT", prd_dlv_Dt);
        ob.getParamsMap().put("BOM_ID", BOM_ID);
        ob.getParamsMap().put("INPT_ITM_ID", rm_Id);
        ob.getParamsMap().put("INPT_ITM_REQ_DT", INPT_ITM_REQ_DT);

        ob.execute();
    }


    public String toInputSrcAction() {
        //VIEWInputSrcVoIterator  refreshSrcInput
        ADFUtils.findOperation("refreshSrcInput").execute();

        //ADFBeanUtils.findIterator("VIEWInputSrcVoIterator").executeQuery();
        return "overToInputScr";
    }

    public void selectPlanACE(ActionEvent actionEvent) {
        // Add event code here...filterOverviewGraph

        OperationBinding ob = ADFUtils.findOperation("filterOverviewGraph");

        ob.getParamsMap().put("plan_doc_id", actionEvent.getComponent().getAttributes().get("planId").toString());
        ob.execute();
    }

    public void backToInputSrcACE(ActionEvent actionEvent) {
        OperationBinding ob = ADFUtils.findOperation("filterDataOnPlanId");
        ob.getParamsMap().put("planDocId", actionEvent.getComponent().getAttributes().get("planId").toString());
        //srcId
        ob.execute();
    }

    public void setPdoGenViewListBinding(RichListView pdoGenViewListBinding) {
        this.pdoGenViewListBinding = pdoGenViewListBinding;
    }

    public RichListView getPdoGenViewListBinding() {
        return pdoGenViewListBinding;
    }

    public void setPdoHeaderListBinding(RichListItem pdoHeaderListBinding) {
        this.pdoHeaderListBinding = pdoHeaderListBinding;
    }

    public RichListItem getPdoHeaderListBinding() {
        return pdoHeaderListBinding;
    }

    public void planQuantityValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            oracle.jbo.domain.Number curQty = (oracle.jbo.domain.Number) object;

            uIComponent.processUpdates(FacesContext.getCurrentInstance());
            /*  if (order_Qty.compareTo(calculateTotalQty()) !=0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Quanity Miss Match! Total quantity For All must be equal to Order Quantity",
                                                              null));
            }  */
            if (curQty.compareTo(0) == 0) {
                throw new ValidatorException(new FacesMessage("Order Quantity must be more than 0"));

            }

            if (ADFBeanUtils.isNumberNegative(curQty)) {
                throw new ValidatorException(new FacesMessage("Negative values not allowed "));

            } else if (!ADFBeanUtils.isPrecisionValid(curQty)) {
                throw new ValidatorException(new FacesMessage("Invalid Precision "));
            }
        }

    }
}
