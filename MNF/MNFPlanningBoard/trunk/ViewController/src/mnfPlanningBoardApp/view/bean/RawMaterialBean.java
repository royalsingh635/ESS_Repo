package mnfPlanningBoardApp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import adf.utils.exception.ADFUtilsException;
import adf.utils.model.ADFModelUtils;

import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import mnfPlanningBoardApp.view.utils.ADFUtils;

import mnfPlanningBoardApp.view.utils.JSFUtils;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichListView;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;

import oracle.jbo.domain.Timestamp;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class RawMaterialBean {
    private RichPanelBox totalRMAvailableBinding;
    private RichPanelBox reserveRMBinding;
    private StringBuffer boxState = new StringBuffer("T");
    private StringBuffer prdState = new StringBuffer("p1");
    private UIXSwitcher prdDlvSwitcherBinding;
    private UIXSwitcher aswitch;

    private oracle.jbo.domain.Number supplierId = new Number(0);
    private RichPopup quantityPopBinding;
    private RichInputText orderQuantityBinding;

    private Integer pageNavId = 0;
    private RichListView suppPerRMListBinding;
    private RichListView moreSuppForRmListBinding;

    public void setPageNavId(Integer pageNavId) {
        this.pageNavId = pageNavId;
    }

    public Integer getPageNavId() {
        return pageNavId;
    }

    public void setSupplierId(Number supplierId) {
        this.supplierId = supplierId;
    }

    public Number getSupplierId() {
        return supplierId;
    }

    public void setPrdState(StringBuffer prdState) {
        this.prdState = prdState;
    }

    public StringBuffer getPrdState() {
        return prdState;
    }

    public void setBoxState(StringBuffer boxState) {
        this.boxState = boxState;
    }

    public StringBuffer getBoxState() {
        return boxState;
    }

    public RawMaterialBean() {
    }

    public void refreshRMACE(ActionEvent actionEvent) {
        System.out.println(getBoxState());
        if (getBoxState().toString().equalsIgnoreCase("T")) {
            setBoxState(new StringBuffer("A"));
        } else
            setBoxState(new StringBuffer("T"));

    }

    public void setTotalRMAvailableBinding(RichPanelBox totalRMAvailableBinding) {
        this.totalRMAvailableBinding = totalRMAvailableBinding;
    }

    public RichPanelBox getTotalRMAvailableBinding() {
        return totalRMAvailableBinding;
    }

    public void setReserveRMBinding(RichPanelBox reserveRMBinding) {
        this.reserveRMBinding = reserveRMBinding;
    }

    public RichPanelBox getReserveRMBinding() {
        return reserveRMBinding;
    }

    public void listOverveiwRmSL(SelectionEvent selectionEvent) {
        ADFUtils.invokeEL("#{bindings.LISTRmOverview.treeModel.makeCurrent}", new Class[] { SelectionEvent.class }, new Object[] {
                          selectionEvent });
        setPrdState(new StringBuffer("p1"));
        aswitch.setFacetName("prd");
        ADFUtils.findOperation("filterOnRmSelection").execute();
        System.out.println(getPrdState());

        //ADFUtils.findOperation("filterViewRawMetrial").execute();
        //filterViewRawMetrial
    }

    public void listProductperRmSL(SelectionEvent selectionEvent) {
        ADFUtils.invokeEL("#{bindings.LISTProductPerRm.treeModel.makeCurrent}", new Class[] { SelectionEvent.class }, new Object[] {
                          selectionEvent });

        ADFUtils.findOperation("filterOnPrdSelection").execute();
        //
    }

    public void backToPrdListACE(ActionEvent actionEvent) {
        setPrdState(new StringBuffer("p1"));
        aswitch.setFacetName("prd");
        //prdDlvSwitcherBinding.setFacetName("prd");
        //prdDlvSwitcherBinding.setDefaultFacet("prd");
        //System.out.println(getPrdState());
    }


    public void setPrdDlvSwitcherBinding(UIXSwitcher prdDlvSwitcherBinding) {
        this.prdDlvSwitcherBinding = prdDlvSwitcherBinding;
    }

    public UIXSwitcher getPrdDlvSwitcherBinding() {
        return prdDlvSwitcherBinding;
    }

    public void setAswitch(UIXSwitcher aswitch) {
        this.aswitch = aswitch;
    }

    public UIXSwitcher getAswitch() {
        return aswitch;
    }

    public void productWithDlvSL(SelectionEvent selectionEvent) {
        ADFUtils.invokeEL("#{bindings.LISTProductWithDlvPerRm.treeModel.makeCurrent}", new Class[] {
                          SelectionEvent.class }, new Object[] { selectionEvent });

        ADFUtils.findOperation("filterOnDlvPrdSelection").execute();

    }

    public String autoScale(oracle.jbo.domain.Number x) {

        Number s = (x.log(10)).add(1);
        //System.out.println(s.floor() +"  0000000000000000000");
        if (s.compareTo(6) == 1) {

            oracle.jbo.domain.Number million = (x.divide(1000000)).mod(100);
            System.out.println(ADFBeanUtils.roundOff(million) + " M");
            System.out.println(x.getLength());
        }
        if (s.compareTo(4) == 1 && s.compareTo(7) == -1) {
            oracle.jbo.domain.Number thousand = (x.divide(1000)).mod(100);
            System.out.println(ADFBeanUtils.roundOff(thousand) + " K");
            System.out.println(x.getLength());
        }
        if (s.compareTo(8) == 1) {
            oracle.jbo.domain.Number billion = (x.divide(1000000000)).mod(100);
            System.out.println(ADFBeanUtils.roundOff(billion) + " B");
            System.out.println(x.getLength());
        }

        return "";
    }

    private String ItmId = null;

    public void setItmId(String ItmId) {
        this.ItmId = ItmId;
    }

    public String getItmId() {
        return ItmId;
    }

    public void filterBasedOnRmSelectionACE(ActionEvent actionEvent) {
        setPrdState(new StringBuffer("p1"));
        aswitch.setFacetName("prd");
        setItmId(actionEvent.getComponent().getAttributes().get("ItmId").toString());
        OperationBinding binding = ADFUtils.findOperation("filterOnRmSelection");
        binding.getParamsMap().put("itmid", getItmId());

        binding.execute();
        System.out.println(getPrdState());
    }

    public void filterGraphOnPrdSelACE(ActionEvent actionEvent) {
        if (getItmId() != null) {
            OperationBinding binding = ADFUtils.findOperation("filterOnPrdSelection");

            binding.getParamsMap().put("inputId", getItmId());
            binding.getParamsMap().put("output", actionEvent.getComponent().getAttributes().get("outpt"));

            binding.execute();
        } else {
            ADFModelUtils.showFacesMessage("Please select Raw Material",
                                           "Please select Raw Material before selecting product ",
                                           FacesMessage.SEVERITY_INFO, null);
        }
    }

    public void toPrdWithDlvACE(ActionEvent actionEvent) {
        if (getItmId() != null) {
            //ADFUtils.findOperation("filterViewRawMetrial").execute();

            OperationBinding binding = ADFUtils.findOperation("filterViewRawMetrial");

            binding.getParamsMap().put("inputId", getItmId());
            binding.getParamsMap().put("output", actionEvent.getComponent().getAttributes().get("output"));

            binding.execute();
            setPrdState(new StringBuffer("d1"));
            aswitch.setFacetName("dlv");
            // System.out.println(getPrdState()); output
            // prdDlvSwitcherBinding.setFacetName("dlv");
        } else {
            ADFModelUtils.showFacesMessage("Please select Raw Material",
                                           "Please select Raw Material before Viewing More Details about product ",
                                           FacesMessage.SEVERITY_INFO, null);
        }
    }

    public void filterBasedOnPrdDlvSelectionACE(ActionEvent actionEvent) {
        //ADFUtils.findOperation("filterOnDlvPrdSelection").execute();
        OperationBinding binding = ADFUtils.findOperation("filterOnDlvPrdSelection");
        System.out.println(getItmId());
        binding.getParamsMap().put("input_itm_id", getItmId());
        binding.getParamsMap().put("pln_doc_Id", actionEvent.getComponent().getAttributes().get("PLN"));
        binding.getParamsMap().put("src_doc_id", actionEvent.getComponent().getAttributes().get("SRC"));
        binding.getParamsMap().put("output_id", actionEvent.getComponent().getAttributes().get("OUTPT"));
        binding.getParamsMap().put("dlv_dt", actionEvent.getComponent().getAttributes().get("DLV"));

        binding.execute();
        setPrdState(new StringBuffer("d1"));
        aswitch.setFacetName("dlv");
    }

    public void serachSuppACE(ActionEvent actionEvent) {
        OperationBinding binding = ADFUtils.findOperation("searchSuppInRmOverview");
        binding.getParamsMap().put("input_itm_id", getItmId());
        binding.execute();
    }
    public void resetSearchSuppACE(ActionEvent actionEvent) {
        OperationBinding binding = ADFUtils.findOperation("resetSuppInRmOverview");
        binding.getParamsMap().put("input_itm_id", getItmId());
        binding.execute();
    }

    public void searchItemACE(ActionEvent actionEvent) {
        ADFUtils.findOperation("searchItemInRmOverview").execute();
    }
    public void resetItemSearchACE(ActionEvent actionEvent) {
     
        ADFUtils.findOperation("resetItemInRmOverview").execute();
    }

    public void searchProductACE(ActionEvent actionEvent) {
        OperationBinding binding = ADFUtils.findOperation("searchProductInRmOverview");
        binding.getParamsMap().put("input_itm_id", getItmId());
        binding.execute();
    }
    public void resetPrdSearchACE(ActionEvent actionEvent) {
        OperationBinding binding = ADFUtils.findOperation("resetProducInRmOverview");
        binding.getParamsMap().put("input_itm_id", getItmId());
        binding.execute();
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

    private String plnId;
    private oracle.jbo.domain.Timestamp dlvDt;
    private String BOM_ID;
    private String DOC_ID_SRC;
    private String OUTPT_ITM_ID;
    private oracle.jbo.domain.Timestamp INPT_ITM_REQ_DT;
    private Number orderQty;

    public void itmSelectionACE(ActionEvent actionEvent) {

        ItmId = actionEvent.getComponent().getAttributes().get("itmId").toString();
        plnId = actionEvent.getComponent().getAttributes().get("planId").toString();
        DOC_ID_SRC = actionEvent.getComponent().getAttributes().get("srcId").toString();
        dlvDt = (oracle.jbo.domain.Timestamp) actionEvent.getComponent().getAttributes().get("dlvDt");
        OUTPT_ITM_ID = actionEvent.getComponent().getAttributes().get("outItmId").toString();
        BOM_ID = actionEvent.getComponent().getAttributes().get("bomId").toString();
        INPT_ITM_REQ_DT = (oracle.jbo.domain.Timestamp) actionEvent.getComponent().getAttributes().get("itmReqDt");
        orderQty = (Number) actionEvent.getComponent().getAttributes().get("orderQty");


        OperationBinding binding = ADFUtils.findOperation("setFilterForSuppl");
        // binding.getParamsMap().put("itm_id", actionEvent.getComponent().getAttributes().get("itmId"));
        binding.getParamsMap().put("inItm_id", ItmId);
        binding.getParamsMap().put("plnDocId", plnId);
        binding.getParamsMap().put("srcId", DOC_ID_SRC);
        binding.getParamsMap().put("outItmId", OUTPT_ITM_ID);
        binding.getParamsMap().put("dlvDt", dlvDt);
        binding.getParamsMap().put("bomId", BOM_ID);
        binding.getParamsMap().put("itmReqDt", INPT_ITM_REQ_DT);

        binding.execute();

        OperationBinding binding1 = ADFUtils.findOperation("setParamInGraphRmMaxMinLine");
        binding1.getParamsMap().put("itm_Id", actionEvent.getComponent().getAttributes().get("itmId"));

        binding1.execute();

    }

    public void setOrderQty(Number orderQty) {
        this.orderQty = orderQty;
    }

    public Number getOrderQty() {
        return orderQty;
    }

    public String toSupplierDetailsAction() {

        return "toSupplier";
    }

    public void moreToSupplierACE(ActionEvent actionEvent) {

        setSupplierId((Number) actionEvent.getComponent().getAttributes().get("suppId"));

    }

    public void addSupplierACE(ActionEvent actionEvent) {

        // ADFUtils.showPopup(quantityPopBinding, true);

        //System.out.println(ItmId + "Current Item");
        OperationBinding binding = ADFUtils.findOperation("chckSelectedSupp");
        binding.execute();
        Integer ch = (Integer) binding.getResult();
        if (ch.compareTo(1) == 0) {
            if (orderQty.compareTo(0) == 1) {
                setValueToAMImpl();
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

    public void addSupplierDialogListner(DialogEvent dialogEvent) {

        if (orderQuantityBinding.getValue() != null) {

        }

    }

    public void setValueToAMImpl() {
        OperationBinding ob = ADFUtils.findOperation("addSupplier");
        //        ob.getParamsMap().put("order_qty", orderQuantityBinding.getValue());
        ob.getParamsMap().put("PLN_DOC_ID", plnId);
        ob.getParamsMap().put("DOC_ID_SRC", DOC_ID_SRC);
        ob.getParamsMap().put("OUTPT_ITM_ID", OUTPT_ITM_ID);
        ob.getParamsMap().put("TXN_DOC_DLV_DT", dlvDt);
        ob.getParamsMap().put("BOM_ID", BOM_ID);
        ob.getParamsMap().put("INPT_ITM_ID", ItmId);
        ob.getParamsMap().put("INPT_ITM_REQ_DT", INPT_ITM_REQ_DT);
        /*
         * oracle.jbo.domain.Number supp_id, oracle.jbo.domain.Number order_qty, String PLN_DOC_ID,
                        String DOC_ID_SRC, String OUTPT_ITM_ID, Timestamp TXN_DOC_DLV_DT, String BOM_ID,
                        String INPT_ITM_ID, Timestamp INPT_ITM_REQ_DT
         */

        ob.execute();


        OperationBinding binding = ADFUtils.findOperation("setFilterForSuppl");
        binding.getParamsMap().put("inItm_id", ItmId);
        binding.getParamsMap().put("plnDocId", plnId);
        binding.getParamsMap().put("srcId", DOC_ID_SRC);
        binding.getParamsMap().put("outItmId", OUTPT_ITM_ID);
        binding.getParamsMap().put("dlvDt", dlvDt);
        binding.getParamsMap().put("bomId", BOM_ID);
        binding.getParamsMap().put("itmReqDt", INPT_ITM_REQ_DT);

        binding.execute();

        //        System.out.println(orderQuantityBinding.getValue());
        ADFBeanUtils.findIterator("LISTProductForRMDetailIterator").executeQuery();
        ADFBeanUtils.findIterator("LISTSupplierFullForRMDetailIterator").executeQuery();

        AdfFacesContext.getCurrentInstance().addPartialTarget(suppPerRMListBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(moreSuppForRmListBinding);

    }

    public void removeSupplierACE(ActionEvent actionEvent) {
        // actionEvent.getComponent().getAttributes().get("planId");
        //OperationBinding ob =  ADFUtils.findOperation("removeSupplierPerItem");
        //ob.getParamsMap().put("", "removeSupplierPerItem");
        //ob.execute();
        // Key key = (oracle.jbo.Key) actionEvent.getComponent().getAttributes().get("deletKey");
        // System.out.println("Key is : " + key);
        // /DelShiftKey
        //rowDelete(key, "MnfPdoSRCVO2Iterator");
        //Number n = new oracle.jbo.domain.Number (8);
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


    public void refreshSupplierACE(ActionEvent actionEvent) {

        ADFUtils.findOperation("refreshRmDetail").execute();
    }

    public void selectToAddVCL(ValueChangeEvent valueChangeEvent) {

        /* ADFUtils.findOperation("addSupplier").execute();
         valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance()); */

    }

    public void setQuantityPopBinding(RichPopup quantityPopBinding) {
        this.quantityPopBinding = quantityPopBinding;
    }

    public RichPopup getQuantityPopBinding() {
        return quantityPopBinding;
    }

    public void setOrderQuantityBinding(RichInputText orderQuantityBinding) {
        this.orderQuantityBinding = orderQuantityBinding;
    }

    public RichInputText getOrderQuantityBinding() {
        return orderQuantityBinding;
    }


    public void supplyOrderQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println(orderQty + " order quanity =================  ");
        Number curQty = (Number) object;
        if (object != null) {
            if (orderQty.compareTo(curQty) == -1) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              "Please Provide Value less then or equals to Quantity required for Order",
                                                              null));
            }
        }

        if (ADFBeanUtils.isNumberNegative((Number) object)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Negative Number not allowed ",
                                                          null));

        }
        if (!ADFBeanUtils.isPrecisionValid((Number) object)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Precision ", null));
        }


    }

    public void filterRmPerCustSelcACE(ActionEvent actionEvent) {
        // Add event code here...setFilterForRawMaterialPerCustomer
        OperationBinding ob = ADFUtils.findOperation("setFilterForRawMaterialPerCustomer");
        ob.getParamsMap().put("pln_doc_Id", actionEvent.getComponent().getAttributes().get("planId").toString());
        ob.getParamsMap().put("output_id", actionEvent.getComponent().getAttributes().get("outItmId").toString());
        ob.getParamsMap().put("src_id", actionEvent.getComponent().getAttributes().get("srcId").toString());
        ob.getParamsMap().put("dlv_dt", (Timestamp) actionEvent.getComponent().getAttributes().get("dlvDt"));
        ob.execute();
    }

    public String backToProductDetails() {

        setPageNavId(2);

        return "toProductDetail";
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

    public void perSupOrderQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
       

    }
    public void chkOrderQuantity(){
        
    }


   
}
