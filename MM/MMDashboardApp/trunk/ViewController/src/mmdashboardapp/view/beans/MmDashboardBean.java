package mmdashboardapp.view.beans;

import adf.utils.ebiz.EbizParams;

import alertupdateservice.view.bean.AlertUpdateServiceBean;

import java.math.BigDecimal;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import mmdashboardapp.model.ds.TickerRowDS;

import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputNumberSlider;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class MmDashboardBean {

    private static ADFLogger adfLog = ADFLogger.createADFLogger(MmDashboardBean.class);
    private RichInputNumberSlider suppNum = new RichInputNumberSlider();
    private RichInputNumberSlider productNum = new RichInputNumberSlider();
    private RichInputNumberSlider productGrpNum = new RichInputNumberSlider();
    private StringBuffer desc = new StringBuffer("");
    private StringBuffer docIdForQuot = new StringBuffer("");
    private String facetValue = "mrs";
    private String facetValuePo = "po";
    private String facetValueIPOBoe = "boe";
    private String facetValueShpAdvc = "shpadvc";
    private String facetValueInsurance = "ins";

    public void setFacetValueInsurance(String facetValueInsurance) {
        this.facetValueInsurance = facetValueInsurance;
    }

    public String getFacetValueInsurance() {
        return facetValueInsurance;
    }


    public void setFacetValueShpAdvc(String faceValueShpAdvc) {
        this.facetValueShpAdvc = facetValueShpAdvc;
    }

    public String getFacetValueShpAdvc() {
        return facetValueShpAdvc;
    }

    public void setFacetValueIPOBoe(String facetValuePOBoe) {
        this.facetValueIPOBoe = facetValuePOBoe;
    }

    public String getFacetValueIPOBoe() {
        return facetValueIPOBoe;
    }
    private UIXSwitcher switcherBindIPoBoeViewPage;

    public void setSwitcherBindIPoBoeViewPage(UIXSwitcher switcherBindIPoBoeViewPage) {
        this.switcherBindIPoBoeViewPage = switcherBindIPoBoeViewPage;
    }

    public UIXSwitcher getSwitcherBindIPoBoeViewPage() {
        return switcherBindIPoBoeViewPage;
    }


    private String facetValueRfqPage = "rfq";
    private UIXSwitcher switcherBind;
    private Number grossCurrentTotalStock = new Number(0);
    private Number grossPreviousTotalStock = new Number(0);
    private Number grossCurrentTotalInvoiceValue = new Number(0);
    private Number grossPreviousTotalInvoiceValue = new Number(0);
    private Number grossCurrentTotalPurchase = new Number(0);
    private Number grossPreviousTotalPurchase = new Number(0);
    private RichPopup tkrSettingsPopup;
    Number million = new Number(1000000);
    Number thousand = new Number(1000);
    Number zero = new Number(0);


    private String dataRangeTkr1 = null;
    private String dataRangeTkr2 = null;
    private String dataRangeTkr3 = null;
    private String firstTkrCurrAmtNotation = "";
    private String firstTkrPrvsAmtNotation = "";
    private String secondTkrCurrAmtNotation = "";
    private String secondTkrPrvsAmtNotation = "";
    private String thirdTkrCurrAmtNotation = "";
    private String thirdTkrPrvsAmtNotation = "";
    private ArrayList<TickerRowDS> tickerList = new ArrayList<TickerRowDS>();
    private ArrayList<TickerRowDS> selectedList = new ArrayList<TickerRowDS>();
    private UIXSwitcher switcherBindRfqPage;

    private Number CpoMyApprovalCount;
    private Number ScrpMyApprovalCount;
    private Number ScrpOtherCount;
    private Number ScrpMyUnpostedCount;
    private Number CpoPndOtherCount;
    private Number CpoMyUnpostedCount;
    private Number MrnMyApprovalCount;
    private Number MrnOtherCount;
    private Number MrnMyUnpostedCount;
    private String OpType = null;

    public void setMrnMyApprovalCount(Number MrnMyApprovalCount) {
        this.MrnMyApprovalCount = MrnMyApprovalCount;
    }

    public Number getMrnMyApprovalCount() {

        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18528);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        System.out.println("Count of mrn in MyApproval :" + binding.getResult());
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
        //return MrnMyApprovalCount;
    }

    public void setMrnOtherCount(Number MrnOtherCount) {
        this.MrnOtherCount = MrnOtherCount;
    }

    public Number getMrnOtherCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18528);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        System.out.println("Count of mrn in OtherCount :" + binding.getResult());
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
        //return MrnOtherCount;
    }

    public void setMrnMyUnpostedCount(Number MrnMyUnpostedCount) {
        this.MrnMyUnpostedCount = MrnMyUnpostedCount;
    }

    public Number getMrnMyUnpostedCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18528);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        System.out.println("Mrn myunposted :" + binding.getResult());
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
        //return MrnMyUnpostedCount;
    }
    //private static ADFLogger adfLog  = ADFLogger.createADFLogger(MmDashboardBean.class);
    private RichPanelGroupLayout panelGroup;


    public MmDashboardBean() {
    }

    /**
     * Method to getBindings
     * @return
     */
    public void setDataRangeTkr1(String dataRangeTkr1) {
        this.dataRangeTkr1 = dataRangeTkr1;
    }


    public String getDataRangeTkr1() {
        OperationBinding data = this.getBindings().getOperationBinding("getTkrDataRange");
        data.getParamsMap().put("pos", new Integer(1));
        data.execute();
        String name = "No Data Range Available";
        if (data.getResult() != null) {
            name = data.getResult().toString();
        }
        return name;
        // return dataRangeTkr1;
    }

    public void setDataRangeTkr2(String dataRangeTkr2) {
        this.dataRangeTkr2 = dataRangeTkr2;
    }

    public String getDataRangeTkr2() {
        OperationBinding data2 = this.getBindings().getOperationBinding("getTkrDataRange");
        data2.getParamsMap().put("pos", new Integer(2));
        data2.execute();
        String name = "No Data Range Available";
        if (data2.getResult() != null) {
            name = data2.getResult().toString();
        }
        return name;
        //return dataRangeTkr2;
    }

    public void setDataRangeTkr3(String dataRangeTkr3) {
        this.dataRangeTkr3 = dataRangeTkr3;
    }

    public String getDataRangeTkr3() {
        OperationBinding tkrName = this.getBindings().getOperationBinding("getTkrDataRange");
        tkrName.getParamsMap().put("pos", new Integer(3));
        tkrName.execute();
        String name = "No Data Range Available";
        if (tkrName.getResult() != null) {
            name = tkrName.getResult().toString();
        }
        return name;
        //return dataRangeTkr3;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /** ###################################  Get Count Of Pending Various Documents for my Approval    ##########################

    /**
     * Method to get Quotation Count Pending for my Approval
     * @return
     */
    public Number getRFQPendingForMyApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18502);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Quotation Count Pending for my Approval
     * @return
     */
    public Number getQuotationPendingForMyApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18503);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Quatation Analysis Count Pending for my Approval
     * @return
     */
    public Number getQuatAnaPendingForMyApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18510);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Purchase Order Count Pending for my Approval
     * @return
     */
    public Number getPOPendingForMyApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18504);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Material Receipt Count Pending for my Approval
     * @return
     */
    public Number getReceiptPendingForMyApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18515);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get MRS Count Pending for my Approval
     * @return
     */
    public Number getMRSForMyApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18513);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Transfer Order Count Pending for my Approval
     * @return
     */

    public Number getTOForMyApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18519);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Stack Take Count Pending for my Approval
     * @return
     */
    public Number getStockTakePendingForMyApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18518);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Stock Adjustment Count Pending for my Approval
     * @return
     */
    public Number getStockAdjstPendingForMyApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18517);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }


    /**
     * Method to get Purchase Return Count Pending for my Approval
     * @return
     */

    public Number getPRForMyApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18529);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Material Return Note Count Pending for my Approval
     * @return
     */
    public Number getMRNPendingForMyApprovalCount() {
        /* OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18518);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{
            return new Number(0);
        }    */
        return new Number(0);
    }


    /**
     * Method to get Purchase Invoice Count Pending for my Approval
     * @return
     */
    public Number getInvoicePendingForMyApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18521);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
        //return new Number(0);
    }

    /** ###################################  Get Count Of Pending Various Documents for Other User Approval    ##########################

    /**
     * Method to get RFQ Count Pending for other Approval
     * @return
     */
    public Number getRFQPendingForOtherApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18502);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Quotation Count Pending for Other Approval
     * @return
     */
    public Number getQuotationPendingForOtherApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18503);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Quatation Analysis Count Pending for Other Approval
     * @return
     */
    public Number getQuatAnaPendingForOtherApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18510);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Purchase Order Count Pending for Other Approval
     * @return
     */
    public Number getPOPendingForOtherApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18504);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Material Receipt Count Pending for Other Approval
     * @return
     */
    public Number getReceiptPendingForOtherApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18515);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get MRS Count Pending for Other Approval
     * @return
     */
    public Number getMRSForOtherApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18513);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Transfer Order Count Pending for Other Approval
     * @return
     */

    public Number getTOForOtherApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18519);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Stack Take Count Pending for Other Approval
     * @return
     */
    public Number getStockTakePendingForOtherApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18518);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Stock Adjustment Count Pending for Other Approval
     * @return
     */
    public Number getStockAdjstPendingForOtherApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18517);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }


    /**
     * Method to get Purchase Return Count Pending for Other Approval
     * @return
     */

    public Number getPRForOtherApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18529);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Material Return Note Count Pending for Other Approval
     * @return
     */
    public Number getMRNPendingForOtherApprovalCount() {
        /*  OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18528);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{
            return new Number(0);
        } */
        return new Number(0);
    }

    /**
     * Method to get purchase Invoice Count Pending for Other Approval
     * @return
     */
    public Number getInvoicePendingForOtherApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18521);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
        //return new Number(0);
    }

    /** ###################################  Get Count Of  Various Unpostedn Documents for all User Approval    ##########################

    /**
     * Method to get RFQ Count Pending for UnpostedDocuments
     * @return
     */
    public Number getRFQUnpostedDocumentsCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18502);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Quotation Count Pending for UnpostedDocuments
     * @return
     */
    public Number getQuotationUnpostedDocumentsCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18503);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Quatation Analysis Count Pending for UnpostedDocuments
     * @return
     */
    public Number getQuatAnaUnpostedDocumentsCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18510);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Purchase Order Count Pending for UnpostedDocuments
     * @return
     */
    public Number getPOUnpostedDocumentsCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18504);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Material Receipt Count Pending for UnpostedDocuments
     * @return
     */
    public Number getReceiptUnpostedDocumentsCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18515);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get MRS Count Pending for UnpostedDocuments
     * @return
     */
    public Number getMRSUnpostedDocumentsCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18513);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Transfer Order Count Pending for UnpostedDocuments
     * @return
     */

    public Number getTOUnpostedDocumentsCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18519);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Stack Take Count Pending for UnpostedDocuments
     * @return
     */
    public Number getStockTakeUnpostedDocumentsCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18518);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Stock Adjustment Count Pending for UnpostedDocuments
     * @return
     */
    public Number getStockAdjstUnpostedDocumentsCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18517);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Purchase Return Count Pending for UnpostedDocuments
     * @return
     */

    public Number getPRUnpostedDocumentsCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18529);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Material Return Note Count Pending for UnpostedDocuments
     * @return
     */
    public Number getMRNUnpostedDocumentsCount() {
        /*  OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18528);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if(binding.getResult() != null){
            return (Number)binding.getResult();
        }else{
            return new Number(0);
        }    */
        return new Number(0);
    }

    /**
     * Method to get Purchase Invoice Count Pending for UnpostedDocuments
     * @return
     */
    public Number getInvoiceUnpostedDocumentsCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18521);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
        //return new Number(0);
    }


    public void top5SuppliersTabDiscloserList(DisclosureEvent disclosureEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("executeTopSuppliersRating");
        binding.getParamsMap().put("val", 5);
        binding.execute();
        suppNum.setValue(5);

    }

    public void top5ProductsTabDiscloserList(DisclosureEvent disclosureEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProductVO");
        binding.getParamsMap().put("val", 5);
        binding.execute();
        productNum.setValue(5);

    }


    public void top5ProductGrpsTabDiscloserList(DisclosureEvent disclosureEvent) {
        OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProductGrpVO");
        binding.getParamsMap().put("val", 5);
        binding.execute();
        productGrpNum.setValue(5);

    }


    public void setSuppNum(RichInputNumberSlider suppNum) {
        this.suppNum = suppNum;
    }

    public RichInputNumberSlider getSuppNum() {
        return suppNum;
    }

    public void noOfSupplierSliderVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent != null) {
            BigDecimal count = (BigDecimal) valueChangeEvent.getNewValue();
            int i = count.intValue();
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopSuppliersRating");
            binding.getParamsMap().put("val", i);
            binding.execute();
        }
    }

    public void noOfProductSliderVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent != null) {
            BigDecimal count = (BigDecimal) valueChangeEvent.getNewValue();
            int i = count.intValue();
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProductVO");
            binding.getParamsMap().put("val", i);
            binding.execute();
        }
    }

    public void noOfProductGrpSliderVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent != null) {
            BigDecimal count = (BigDecimal) valueChangeEvent.getNewValue();
            int i = count.intValue();
            OperationBinding binding = this.getBindings().getOperationBinding("executeTopNProductGrpVO");
            binding.getParamsMap().put("val", i);
            binding.execute();
        }
    }
    //=================Quotation Document All the three cases======================
    public String quotationForMyApprovalAction() {
        OpType = "M";
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFQuotationView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        return "wfQuotationView";

    }

    public String quotationForOtherApprovalAction() {
        OpType = "O";
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFQuotationView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        return "wfQuotationView";

    }

    public String quotationUnpostedAction() {
        OpType = "U";
        desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFQuotationView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        return "wfQuotationView";

    }
    //===========End of Quotation Document==================================================

    public void setDesc(StringBuffer desc) {
        this.desc = desc;
    }

    public StringBuffer getDesc() {
        return desc;
    }

    public void setDocIdForQuot(StringBuffer docIdForQuot) {
        this.docIdForQuot = docIdForQuot;
    }

    public StringBuffer getDocIdForQuot() {
        return docIdForQuot;
    }

    public String viewQuotationAction() {
        this.docIdForQuot = new StringBuffer("");
        OperationBinding binding = this.getBindings().getOperationBinding("getCurrDocIdfoQuot");
        binding.execute();
        if (binding.getResult() != null && !binding.getResult().toString().equals("")) {
            this.docIdForQuot = new StringBuffer(binding.getResult().toString());
        }
        if (this.docIdForQuot.toString().equals("")) {
            return null;
        } else {
            return "viewQuotationPage";
        }
    }
    // -----------------Request For Quotation Document all the three cases-----------------------------------------------
    public String rfqForMyApprovalAction() {
        OpType = "M";
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFRfqView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValueRfqPage("rfq");
        facetValueRfqPage = "rfq";
        return "wfRfqView";
    }

    public String rfqForOtherApprovalAction() {
        OpType = "O";
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFRfqView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValueRfqPage("rfq");
        facetValueRfqPage = "rfq";
        return "wfRfqView";
    }

    public String rfqForMyUnpostedAction() {
        OpType = "U";
        desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFRfqView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValueRfqPage("rfq");
        facetValueRfqPage = "rfq";
        return "wfRfqView";
    }
    //------------------END of Request of Quotation Document------------------------------------
    //-------------Purchase Order Docuement all the Threee Cases--------------------------------
    public String poForMyApprovalAction() {
        OpType = "M";
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFPurOrdView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValuePo("po");
        facetValuePo = "po";
        return "wfPurOrdView";
    }

    public String poForOtherApprovalAction() {
        OpType = "O";
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFPurOrdView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValuePo("po");
        facetValuePo = "po";
        return "wfPurOrdView";
    }

    public String poForMyUnpostedAction() {
        OpType = "U";
        desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFPurOrdView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValuePo("po");
        facetValuePo = "po";
        return "wfPurOrdView";
    }
    //----------------End of Purchase Order Document-----------------------------------------------
    public String quotAnaForMyApprovalAction() {
        // Add event code here...
        return null;
    }

    public String quotAnaForOtherApprovalAction() {
        // Add event code here...
        return null;
    }

    public String quotAnaForMyUnpostedAction() {
        // Add event code here...
        return null;
    }

    public String viewPurOrdAction() {
        return "viewPurOrdPage";
    }
    //---------------Transfer Order  Document All the Three Cases.---------------------------------
    public String trfOrdForMyApprovalAction() {
        OpType = "M";
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFTrfOrdView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValue("trfOrd");
        facetValue = "trfOrd";
        return "viewTrfOrdView";
    }

    public String trfOrdForOtherApprovalAction() {
        OpType = "O";
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFTrfOrdView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValue("trfOrd");
        facetValue = "trfOrd";
        return "viewTrfOrdView";
    }

    public String trfOrdForMyUnpostedAction() {
        OpType = "U";
        desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFTrfOrdView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValue("trfOrd");
        facetValue = "trfOrd";
        return "viewTrfOrdView";
    }
    //---------------End of Transfer Order Document--------------------------------------
    //----------Material Requistion Slip  Document All the threee Cases--------------------
    public String mrsForMyApprovalAction() {
        OpType = "M";
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFMRSView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValue("mrs");
        facetValue = "mrs";
        return "viewTrfOrdView";

    }

    public String mrsForOtherApprovalAction() {
        OpType = "O";
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFMRSView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValue("mrs");
        facetValue = "mrs";
        return "viewTrfOrdView";
    }

    public String mrsForMyUnpostedAction() {
        OpType = "U";
        desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFMRSView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValue("mrs");
        facetValue = "mrs";
        return "viewTrfOrdView";
    }
    //-------------End of MRS Document------------------------------------------------------

    public String rcptForMyApprovalAction() {
        OpType = "M";
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFRcptView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValuePo("rcpt");
        facetValuePo = "rcpt";
        return "wfPurOrdView";
    }

    public String rcptForOtherApprovalAction() {
        OpType = "O";
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFRcptView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValuePo("rcpt");
        facetValuePo = "rcpt";
        return "wfPurOrdView";
    }

    public String rcptForUnpostedAction() {
        OpType = "U";
        desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFRcptView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValuePo("rcpt");
        facetValuePo = "rcpt";
        return "wfPurOrdView";
    }
    // Stock Take Actions for all the Three Cases.
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public String stockTakeForMyApprovalAction() {
        OpType = "M";
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFStockTakeView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValueRfqPage("stockTake");
        facetValueRfqPage = "stockTake";
        return "wfRfqView";

    }

    public String stockTakeForOtherApprovalAction() {
        OpType = "O";
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFStockTakeView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValueRfqPage("stockTake");
        facetValueRfqPage = "stockTake";
        return "wfRfqView";
    }

    public String stockTakeForMyUnpostedAction() {
        OpType = "U";
        desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFStockTakeView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValueRfqPage("stockTake");
        facetValueRfqPage = "stockTake";
        return "wfRfqView";
    }
    //----------------------------------End of Stock Take Document------------------------------------------------------
    //----------------------------------Stock Adjustment Document All the three Cases(M,O,U). ---------------------------------------------------------------------
    public String stockAdjForMyApprovalAction() {
        OpType = "M";
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFStockAdjView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValueRfqPage("stockAdj");
        facetValueRfqPage = "stockAdj";
        return "wfRfqView";

    }

    public String stockAdjForOtherApprovalAction() {
        OpType = "O";
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFStockAdjView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValueRfqPage("stockAdj");
        facetValueRfqPage = "stockAdj";
        return "wfRfqView";
    }

    public String stockAdjForMyUnpostedAction() {
        OpType = "U";
        desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFStockAdjView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValueRfqPage("stockAdj");
        facetValueRfqPage = "stockAdj";
        return "wfRfqView";
    }
    //------------------------End Stock Adjusment-------------------------------------------------------
    public void setFacetValuePo(String facetValuePo) {
        this.facetValuePo = facetValuePo;
    }

    public String getFacetValuePo() {
        return facetValuePo;
    }

    public void setSwitcherBind(UIXSwitcher switcherBind) {
        this.switcherBind = switcherBind;
    }

    public UIXSwitcher getSwitcherBind() {
        return switcherBind;
    }

    public void setFacetValue(String facetValue) {
        this.facetValue = facetValue;
    }

    public String getFacetValue() {
        return facetValue;
    }


    public String tkrQuery() {
        OperationBinding tkrQuery = this.getBindings().getOperationBinding("executeTkrQuery");
        tkrQuery.getParamsMap().put("tkrId", "TKR001");
        tkrQuery.execute();
        return null;
    }

    public Number getGrossCurrentTotalStock() {
        adfLog.info(" in the getGrossCurrentTotalStock");
        OperationBinding tkridVal = this.getBindings().getOperationBinding("getTkrIdValue");
        tkridVal.getParamsMap().put("tkrPos", 1);
        tkridVal.execute();
        String tkrId = null;
        if (tkridVal.getResult() != null) {
            tkrId = tkridVal.getResult().toString();
        }
        if (tkrId != null) {
            OperationBinding tkrQuery = this.getBindings().getOperationBinding("executeTkrQuery");
            tkrQuery.getParamsMap().put("tkrId", tkrId);
            tkrQuery.execute();
            if (tkrQuery.getResult() != null) {
                ArrayList<Number> queryVal = new ArrayList<Number>();
                queryVal = (ArrayList<Number>) tkrQuery.getResult();
                adfLog.info("Size of arraylist:::" + queryVal.size());
                if (queryVal.size() > 0) {
                    grossCurrentTotalStock = queryVal.get(0);
                    adfLog.info("Value of grossCurrentTotalStock" + grossCurrentTotalStock);
                    if (grossCurrentTotalStock != null) {
                        if (grossCurrentTotalStock.compareTo(thousand) < 0) {
                            this.setFirstTkrCurrAmtNotation(" ");
                        } else if (grossCurrentTotalStock.compareTo(thousand) > 0 &&
                                   grossCurrentTotalStock.compareTo(million) < 0) {
                            this.setFirstTkrCurrAmtNotation("K");
                            grossCurrentTotalStock = (Number) grossCurrentTotalStock.divide(new Number(1000));
                        } else if (grossCurrentTotalStock.compareTo(million) > 0) {
                            this.setFirstTkrCurrAmtNotation("M");
                            grossCurrentTotalStock = (Number) grossCurrentTotalStock.divide(new Number(1000000));
                        }
                    } else {
                        grossCurrentTotalStock = zero;
                    }
                    grossPreviousTotalStock = queryVal.get(1);
                    adfLog.info("Value of grossPreviousTotalStock" + grossPreviousTotalStock);
                    if (grossPreviousTotalStock != null) {
                        if (grossPreviousTotalStock.compareTo(thousand) < 0) {
                            this.setFirstTkrPrvsAmtNotation(" ");
                        } else if (grossPreviousTotalStock.compareTo(thousand) > 0 &&
                                   grossPreviousTotalStock.compareTo(million) < 0) {
                            this.setFirstTkrPrvsAmtNotation("K");
                            grossPreviousTotalStock = (Number) grossPreviousTotalStock.divide(new Number(1000));
                        } else if (grossPreviousTotalStock.compareTo(million) > 0) {
                            this.setFirstTkrPrvsAmtNotation("M");
                            grossPreviousTotalStock = (Number) grossPreviousTotalStock.divide(new Number(1000000));
                        }
                    } else {
                        grossPreviousTotalStock = zero;
                    }
                }

            }
        } else {
            grossCurrentTotalStock = zero;
            grossPreviousTotalStock = zero;
        }
        return grossCurrentTotalStock;
    }

    public Number getGrossPreviousTotalStock() {
        return grossPreviousTotalStock;
    }

    public Number getGrossCurrentTotalInvoiceValue() {
        adfLog.info("int the GrossCurrentTotalInvoiceValue");
        OperationBinding tkridVal = this.getBindings().getOperationBinding("getTkrIdValue");
        tkridVal.getParamsMap().put("tkrPos", 2);
        tkridVal.execute();
        String tkrId = null;
        if (tkridVal.getResult() != null) {
            tkrId = tkridVal.getResult().toString();
        }
        if (tkrId != null) {

            OperationBinding tkrQuery = this.getBindings().getOperationBinding("executeTkrQuery");
            tkrQuery.getParamsMap().put("tkrId", tkrId);
            tkrQuery.execute();
            if (tkrQuery.getResult() != null) {
                ArrayList<Number> queryVal = new ArrayList<Number>();
                queryVal = (ArrayList<Number>) tkrQuery.getResult();
                adfLog.info(" Size  of ArrayList " + queryVal.size());
                if (queryVal.size() > 0) {
                    grossCurrentTotalInvoiceValue = queryVal.get(0);
                    adfLog.info("Value of grossCurrentTotalInvoiceValue " + grossCurrentTotalInvoiceValue);
                    if (grossCurrentTotalInvoiceValue != null) {
                        if (grossCurrentTotalInvoiceValue.compareTo(thousand) < 0) {
                            this.setSecondTkrCurrAmtNotation(" ");
                        } else if (grossCurrentTotalInvoiceValue.compareTo(thousand) > 0 &&
                                   grossCurrentTotalInvoiceValue.compareTo(million) < 0) {
                            this.setSecondTkrCurrAmtNotation("K");
                            grossCurrentTotalInvoiceValue =
                                (Number) grossCurrentTotalInvoiceValue.divide(new Number(1000));
                        } else if (grossCurrentTotalInvoiceValue.compareTo(million) > 0) {
                            this.setSecondTkrCurrAmtNotation("M");
                            grossCurrentTotalInvoiceValue =
                                (Number) grossCurrentTotalInvoiceValue.divide(new Number(1000000));
                        }
                    } else {
                        grossCurrentTotalInvoiceValue = zero;
                    }
                    grossPreviousTotalInvoiceValue = queryVal.get(1);
                    adfLog.info(" Value of grossPreviousTotalInvoiceValue " + grossPreviousTotalInvoiceValue);
                    if (grossPreviousTotalInvoiceValue != null) {
                        if (grossPreviousTotalInvoiceValue.compareTo(thousand) < 0) {
                            this.setSecondTkrPrvsAmtNotation(" ");
                        } else if (grossPreviousTotalInvoiceValue.compareTo(thousand) > 0 &&
                                   grossPreviousTotalInvoiceValue.compareTo(million) < 0) {
                            this.setSecondTkrPrvsAmtNotation("K");
                            grossPreviousTotalInvoiceValue =
                                (Number) grossPreviousTotalInvoiceValue.divide(new Number(1000));
                        } else if (grossPreviousTotalInvoiceValue.compareTo(million) > 0) {
                            this.setSecondTkrPrvsAmtNotation("M");
                            grossPreviousTotalInvoiceValue =
                                (Number) grossPreviousTotalInvoiceValue.divide(new Number(1000000));
                        }
                    } else {
                        grossPreviousTotalInvoiceValue = zero;
                    }

                }
            }
        } else {
            grossCurrentTotalInvoiceValue = zero;
            grossPreviousTotalInvoiceValue = zero;

        }
        return grossCurrentTotalInvoiceValue;
    }

    public Number getGrossPreviousTotalInvoiceValue() {
        return grossPreviousTotalInvoiceValue;
    }

    public Number getGrossCurrentTotalPurchase() {
        adfLog.info("in the GrossCurrentTotalPurchase");
        OperationBinding tkridVal = this.getBindings().getOperationBinding("getTkrIdValue");
        tkridVal.getParamsMap().put("tkrPos", 3);
        tkridVal.execute();
        String tkrId = null;
        if (tkridVal.getResult() != null) {
            tkrId = tkridVal.getResult().toString();
        }
        if (tkrId != null) {
            OperationBinding tkrQuery = this.getBindings().getOperationBinding("executeTkrQuery");
            tkrQuery.getParamsMap().put("tkrId", tkrId);
            tkrQuery.execute();

            if (tkrQuery.getResult() != null) {
                ArrayList<Number> queryVal = new ArrayList<Number>();
                queryVal = (ArrayList<Number>) tkrQuery.getResult();
                adfLog.info("Size of ArrayList" + queryVal.size());
                if (queryVal.size() > 0) {
                    grossCurrentTotalPurchase = queryVal.get(0);
                    adfLog.info(" Value of grossCurrentTotalPurchase" + grossCurrentTotalPurchase);
                    if (grossCurrentTotalPurchase != null) {
                        if (grossCurrentTotalPurchase.compareTo(thousand) < 0) {
                            this.setThirdTkrCurrAmtNotation(" ");
                        } else if (grossCurrentTotalPurchase.compareTo(thousand) > 0 &&
                                   grossCurrentTotalPurchase.compareTo(million) < 0) {
                            this.setThirdTkrCurrAmtNotation("K");
                            grossCurrentTotalPurchase = (Number) grossCurrentTotalPurchase.divide(new Number(1000));
                        } else if (grossCurrentTotalPurchase.compareTo(million) > 0) {
                            this.setThirdTkrCurrAmtNotation("M");
                            grossCurrentTotalPurchase = (Number) grossCurrentTotalPurchase.divide(new Number(1000000));
                        }
                    } else {
                        grossCurrentTotalPurchase = zero;
                    }
                    grossPreviousTotalPurchase = queryVal.get(1);
                    adfLog.info("Value of  grossPreviousTotalPurchase" + grossPreviousTotalPurchase);
                    if (grossPreviousTotalPurchase != null) {
                        if (grossPreviousTotalPurchase.compareTo(thousand) < 0) {
                            this.setThirdTkrPrvsAmtNotation(" ");
                        } else if (grossPreviousTotalPurchase.compareTo(thousand) > 0 &&
                                   grossPreviousTotalPurchase.compareTo(million) < 0) {
                            this.setThirdTkrPrvsAmtNotation("K");
                            grossPreviousTotalPurchase = (Number) grossPreviousTotalPurchase.divide(new Number(1000));
                        } else if (grossPreviousTotalPurchase.compareTo(million) > 0) {
                            this.setThirdTkrPrvsAmtNotation("M");
                            grossPreviousTotalPurchase =
                                (Number) grossPreviousTotalPurchase.divide(new Number(1000000));
                        }
                    } else {
                        grossPreviousTotalPurchase = zero;
                    }
                }
            }
        } else {
            grossCurrentTotalPurchase = zero;
            grossPreviousTotalPurchase = zero;
        }
        return grossCurrentTotalPurchase;
    }

    public Number getGrossPreviousTotalPurchase() {
        return grossPreviousTotalPurchase;
    }

    public void setTkrSettingsPopup(RichPopup tkrSettingsPopup) {
        this.tkrSettingsPopup = tkrSettingsPopup;
    }

    public RichPopup getTkrSettingsPopup() {
        return tkrSettingsPopup;
    }

    /**
     *  ActionEvent to show popup
     */
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

    public void tkrSettingPopupAction(ActionEvent actionEvent) {
        // To get the total list of tickers
        OperationBinding binding = this.getBindings().getOperationBinding("getTickerList");
        if (binding != null) {
            binding.execute();
            if (binding.getResult() != null) {

                tickerList = (ArrayList<TickerRowDS>) binding.getResult();

            }
        }
        // To get the total list of selected list of tickers
        OperationBinding bind = this.getBindings().getOperationBinding("getSelectedTickerList");
        if (bind != null) {
            bind.execute();
            if (bind.getResult() != null) {
                selectedList.clear();
                selectedList = (ArrayList<TickerRowDS>) bind.getResult();
            }
        }

        // To set ticker description in select tickeres.
        /*       for(TickerRowDS t : tickerList){
            for(TickerRowDS r : selectedList){
                if(r.getTickerId().equals(t.getTickerId())){
                    r.setTickerDesc(t.getTickerDesc());
                    break;
                }
            }
        }*/
        // To remove the selecetd tickers from the total list of tickers
        for (TickerRowDS t : selectedList) {
            for (TickerRowDS r : tickerList) {
                if (r.getTickerId().equals(t.getTickerId())) {
                    tickerList.remove(r);
                    break;
                }
            }
        }

        for (TickerRowDS r : selectedList) {
        }
        showPopup(tkrSettingsPopup, true);
    }


    /**
     * Move the selected ticker up
     * @param actionEvent
     */
    public void moveSelectedUpAction(ActionEvent actionEvent) {
        Integer sequenceNo = 0;
        RichCommandImageLink ob = (RichCommandImageLink) actionEvent.getSource();
        ob.getAttributes().get("sequenceNo");
        sequenceNo = (Integer) ob.getAttributes().get("sequenceNo");


        if (sequenceNo != 1) {
            TickerRowDS selup = this.selectedList.get(sequenceNo - 2);
            TickerRowDS selcur = this.selectedList.get(sequenceNo - 1);
            selup.setSequenceNo(sequenceNo);
            selcur.setSequenceNo(sequenceNo - 1);
            this.selectedList.set(sequenceNo - 2, selcur);
            this.selectedList.set(sequenceNo - 1, selup);
        }

    }

    /**
     * Move the selected ticker down
     * @param actionEvent
     */
    public void moveSelectedDownAction(ActionEvent actionEvent) {
        Integer sequenceNo = 0;
        RichCommandImageLink ob = (RichCommandImageLink) actionEvent.getSource();
        ob.getAttributes().get("sequenceNo");
        sequenceNo = (Integer) ob.getAttributes().get("sequenceNo");


        if (sequenceNo != 3) {
            TickerRowDS selcur = this.selectedList.get(sequenceNo - 1);
            TickerRowDS seldn = this.selectedList.get(sequenceNo);
            seldn.setSequenceNo(sequenceNo);
            selcur.setSequenceNo(sequenceNo + 1);
            this.selectedList.set(sequenceNo, selcur);
            this.selectedList.set(sequenceNo - 1, seldn);
        }

    }

    public String getTickerFirstLableName() {
        OperationBinding tkrName = this.getBindings().getOperationBinding("getTkrLableName");
        tkrName.getParamsMap().put("tkrPos", 1);
        tkrName.execute();
        String name = "No Ticker Selected";
        if (tkrName.getResult() != null) {
            name = tkrName.getResult().toString();
        }
        return name;
    }

    public String getTickerSecondLableName() {
        OperationBinding tkrName = this.getBindings().getOperationBinding("getTkrLableName");
        tkrName.getParamsMap().put("tkrPos", 2);
        tkrName.execute();
        String name = "No Ticker Selected";
        if (tkrName.getResult() != null) {
            name = tkrName.getResult().toString();
        }
        return name;
    }

    public String getTickerThirdLableName() {
        OperationBinding tkrName = this.getBindings().getOperationBinding("getTkrLableName");
        tkrName.getParamsMap().put("tkrPos", 3);
        tkrName.execute();
        String name = "No Ticker Selected";
        if (tkrName.getResult() != null) {
            name = tkrName.getResult().toString();
        }

        return name;
    }

    /**
     * Method to add a selected row from ticker setup page.
     * @param actionEvent
     */
    public void addTickerAction(ActionEvent actionEvent) {
        //Integer ticker = 0;
        String ticker = null;
        RichCommandImageLink ob = (RichCommandImageLink) actionEvent.getSource();
        ob.getAttributes().get("tickerId");

        if (ob.getAttributes().get("tickerId") != null) {

            if (this.selectedList.size() < 3) {
                Integer seq = 0;
                for (TickerRowDS r : this.selectedList) {
                    if (seq < r.getSequenceNo()) {
                        seq = r.getSequenceNo();
                    }
                }
                seq = seq + 1;
                ticker = (String) ob.getAttributes().get("tickerId");
                for (TickerRowDS r : this.tickerList) {
                    if (ticker.equalsIgnoreCase(r.getTickerId().toString())) {
                        selectedList.add(new TickerRowDS(ticker, seq, r.getTickerDesc()));
                        tickerList.remove(r);
                        break;
                    }
                }

            } else {
                FacesMessage message = new FacesMessage("Cannot select more that THREE Tickers !");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }

        }

    }

    /**
     * Method to delete a selected row from ticker setup page.
     * @param actionEvent
     */
    public void deleteTickerAction(ActionEvent actionEvent) {
        // Integer ticker = 0;
        String ticker = null;
        RichCommandImageLink ob = (RichCommandImageLink) actionEvent.getSource();
        ob.getAttributes().get("tickerId");

        if (ob.getAttributes().get("tickerId") != null) {
            Integer seq = 0;
            for (TickerRowDS r : this.tickerList) {
                if (seq < r.getSequenceNo()) {
                    seq = r.getSequenceNo();
                }
            }
            seq = seq + 1;
            ticker = (String) ob.getAttributes().get("tickerId");
            for (TickerRowDS r : this.selectedList) {
                if (ticker.equalsIgnoreCase(r.getTickerId().toString())) {

                    tickerList.add(new TickerRowDS(ticker, seq, r.getTickerDesc()));
                    selectedList.remove(r);
                    break;
                }
            }
            seq = 1;
            for (TickerRowDS r : this.selectedList) {
                r.setSequenceNo(seq);
                seq = seq + 1;
            }

        }

    }

    public void tickerSettingDialogListener(DialogEvent dialogEvent) {
        OperationBinding unsetPos = this.getBindings().getOperationBinding("unsetAllTkrPosForUsr");
        unsetPos.execute();
        for (TickerRowDS r : getSelectedList()) {
            OperationBinding updateTkr = this.getBindings().getOperationBinding("updateTickerSettingForUsr");
            System.out.println("sequence no" + r.getSequenceNo());
            System.out.println("Ticker ID" + r.getTickerId());
            updateTkr.getParamsMap().put("seqNo", r.getSequenceNo());
            updateTkr.getParamsMap().put("tickeId", r.getTickerId());
            updateTkr.execute();
        }
        this.getBindings().getOperationBinding("Commit").execute();
        // Add event code here...  updateTickerSettingForUsr

        /* OperationBinding updateTkr = this.getBindings().getOperationBinding("updateTickerSettingForUsr");
        updateTkr.execute(); */
    }


    public void setTickerList(ArrayList<TickerRowDS> tickerList) {
        this.tickerList = tickerList;
    }

    public ArrayList<TickerRowDS> getTickerList() {
        return tickerList;
    }

    public void setSelectedList(ArrayList<TickerRowDS> selectedList) {
        this.selectedList = selectedList;
    }

    public ArrayList<TickerRowDS> getSelectedList() {
        return selectedList;
    }

    public void setThirdTkrCurrAmtNotation(String thirdTkrCurrAmtNotation) {
        this.thirdTkrCurrAmtNotation = thirdTkrCurrAmtNotation;
    }

    public String getThirdTkrCurrAmtNotation() {
        return thirdTkrCurrAmtNotation;
    }

    public void setThirdTkrPrvsAmtNotation(String thirdTkrPrvsAmtNotation) {
        this.thirdTkrPrvsAmtNotation = thirdTkrPrvsAmtNotation;
    }

    public String getThirdTkrPrvsAmtNotation() {
        return thirdTkrPrvsAmtNotation;
    }

    public void setFirstTkrCurrAmtNotation(String firstTkrCurrAmtNotation) {
        this.firstTkrCurrAmtNotation = firstTkrCurrAmtNotation;
    }

    public String getFirstTkrCurrAmtNotation() {
        return firstTkrCurrAmtNotation;
    }

    public void setFirstTkrPrvsAmtNotation(String firstTkrPrvsAmtNotation) {
        this.firstTkrPrvsAmtNotation = firstTkrPrvsAmtNotation;
    }

    public String getFirstTkrPrvsAmtNotation() {
        return firstTkrPrvsAmtNotation;
    }

    public void setSecondTkrCurrAmtNotation(String secondTkrCurrAmtNotation) {
        this.secondTkrCurrAmtNotation = secondTkrCurrAmtNotation;
    }

    public String getSecondTkrCurrAmtNotation() {
        return secondTkrCurrAmtNotation;
    }

    public void setSecondTkrPrvsAmtNotation(String secondTkrPrvsAmtNotation) {
        this.secondTkrPrvsAmtNotation = secondTkrPrvsAmtNotation;
    }

    public String getSecondTkrPrvsAmtNotation() {
        return secondTkrPrvsAmtNotation;
    }

    public void setSwitcherBindRfqPage(UIXSwitcher switcherBindRfqPage) {
        this.switcherBindRfqPage = switcherBindRfqPage;
    }

    public UIXSwitcher getSwitcherBindRfqPage() {
        return switcherBindRfqPage;
    }

    public void setFacetValueRfqPage(String facetValueRfqPage) {
        this.facetValueRfqPage = facetValueRfqPage;
    }

    public String getFacetValueRfqPage() {
        return facetValueRfqPage;
    }


    public void setProductNum(RichInputNumberSlider productNum) {
        this.productNum = productNum;
    }

    public RichInputNumberSlider getProductNum() {
        return productNum;
    }

    public void setProductGrpNum(RichInputNumberSlider productGrpNum) {
        this.productGrpNum = productGrpNum;
    }

    public RichInputNumberSlider getProductGrpNum() {
        return productGrpNum;
    }
    //======================Invoice Document all the three cases=================================================
    public String InvoiceForMyApprovalAction() {
        // Add event code here...
        OpType = "M";
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setInvcPndView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValue("Invc");
        facetValue = "Invc";
        return "wfInvcView";


    }

    public String pendingInvcWithOther() {
        OpType = "O";
        // Add event code here...
        System.out.println("in the pending method of invoice");
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setInvcPndView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValue("Invc");
        facetValue = "Invc";
        return "wfInvcView";
    }

    public String InvoiceForMyUnPostedAction() {
        OpType = "U";
        // Add event code here...
        desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setInvcPndView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValue("Invc");
        facetValue = "Invc";
        return "wfInvcView";
    }
    //--------------End of Invoice Docuement==========================================================
    //======================CPO Document All the three Cases========================================
    public String CpoPndforMyApprovalACTION() {
        OpType = "M";
        // Add event code here...
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setCpoPndView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValuePo("Cpo");
        facetValuePo = "Cpo";
        return "wfCpoView";

    }

    public String CpoPndMyUnpostedAction() {
        OpType = "U";
        // Add event code here...
        desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setCpoPndView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValuePo("Cpo");
        facetValuePo = "Cpo";
        return "wfCpoView";
    }

    public String CpoPndwithOtherAction() {

        // Add event code here...
        OpType = "O";
        System.out.println("in the pending method of Cpo");
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setCpoPndView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValuePo("Cpo");
        facetValuePo = "Cpo";
        return "wfCpoView";
    }
    //==============================End oF CPO Document========================================================

    public void setCpoMyApprovalCount(Number CpoMyApprovalCount) {
        this.CpoMyApprovalCount = CpoMyApprovalCount;
    }

    public Number getCpoMyApprovalCount() {

        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18534);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        System.out.println("Count of Cpo :" + binding.getResult());
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
        //return CpoMyApprovalCount;
    }

    public Number getCpoPndOtherCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18534);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
        //return CpoPndOtherCount;
    }


    public void setScrpMyApprovalCount(Number ScrpMyApprovalCount) {
        this.ScrpMyApprovalCount = ScrpMyApprovalCount;
    }

    public Number getScrpMyApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18508);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        System.out.println("Count of Scrap Sales" + binding.getResult());
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
        //return ScrpMyApprovalCount;
    }

    public void setCpoPndOtherCount(Number CpoPndOtherCount) {
        this.CpoPndOtherCount = CpoPndOtherCount;
    }


    public void setCpoMyUnpostedCount(Number CpoMyUnpostedCount) {
        this.CpoMyUnpostedCount = CpoMyUnpostedCount;
    }

    public Number getCpoMyUnpostedCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18534);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
        //return CpoMyUnpostedCount;
    }


    //===================Scrap Sales Document=================================================
    public String ScrpSalePndWithOtherAction() {
        // Add event code here...
        OpType = "O";
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setScrpSlsPndView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValuePo("ScrpSls");
        facetValuePo = "ScrpSls";
        return "wfScrpView";
    }

    public String ScrpSlsMyUnpostedAction() {
        // Add event code here...
        OpType = "U";
        desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setScrpSlsPndView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValuePo("ScrpSls");
        facetValuePo = "ScrpSls";
        return "wfScrpView";
    }

    public String ScrpSlsPndForMyApproval() {
        // Add event code here...
        OpType = "M";
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setScrpSlsPndView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValuePo("ScrpSls");
        facetValuePo = "ScrpSls";
        return "wfScrpView";

    }
    //==========End of Scrap Sales Document==============================

    public void setScrpOtherCount(Number ScrpOtherCount) {
        this.ScrpOtherCount = ScrpOtherCount;
    }

    public Number getScrpOtherCount() {
        //return ScrpOtherCount;
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18508);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    public void setScrpMyUnpostedCount(Number ScrpMyUnpostedCount) {
        this.ScrpMyUnpostedCount = ScrpMyUnpostedCount;
    }

    public Number getScrpMyUnpostedCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18508);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        System.out.println("Scrap :" + binding.getResult());
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
        //return ScrpMyUnpostedCount;
    }

    // Code For Alert and Update Service
    private AlertUpdateServiceBean bean = new AlertUpdateServiceBean();
    private RichPanelFormLayout panelForm;
    private int i = 0;

    public String getText() {
        if (i == 0) {
            this.printDetail();
            i++;
        }
        return "Y";
    }

    public void printDetail() {

        UIOutput o1 =
            bean.getAlert("300", "275", EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(),
                          EbizParams.GLBL_HO_ORG_ID(), EbizParams.GLBL_APP_USR_ORG(), EbizParams.GLBL_APP_USR(),
                          "00305");
        this.panelForm.getChildren().add(o1);
        System.out.println("addind Refresh");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.panelForm);
        AdfFacesContext.getCurrentInstance().addPartialTarget(o1);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelGroup());


    }

    public void getUpdate(ClientEvent clientEvent) {
        System.out.println("I am in bean");
        bean.getUpdate(this.getPanelForm(), EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(),
                       EbizParams.GLBL_HO_ORG_ID(), EbizParams.GLBL_APP_USR_ORG(), EbizParams.GLBL_APP_USR(), "00305");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelForm());

    }

    public void setPanelForm(RichPanelFormLayout panelForm) {
        this.panelForm = panelForm;
    }

    public RichPanelFormLayout getPanelForm() {
        return panelForm;
    }

    public void setPanelGroup(RichPanelGroupLayout panelGroup) {
        this.panelGroup = panelGroup;
    }

    public RichPanelGroupLayout getPanelGroup() {
        return panelGroup;
    }

    public void serchAlrt(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("search").execute();
    }

    public void resetAlrt(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("reset").execute();
    }
    //==============Material Return Note Document All the three Action==============================================
    public String MrnDocPendForMyApprovalAction() {
        // Add event code here...
        OpType = "M";
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFMRNView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValue("mrn");
        facetValue = "mrn";
        return "viewTrfOrdView";
        //return null;
    }
    //Purchase Requistion


    public String mrnPendWithOtherAction() {
        // Add event code here...
        OpType = "O";
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFMRNView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValue("mrn");
        facetValue = "mrn";
        return "viewTrfOrdView";
        // return null;
    }

    public String mrnPendMyUnpostedAction() {
        // Add event code here...
        OpType = "U";
        desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setWFMRNView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValue("mrn");
        facetValue = "mrn";
        return "viewTrfOrdView";
        // return null;
    }


    //======================Material Return Note Document Complete====================================================
    public void refreshRfqViewsAL(ActionEvent actionEvent) {
        // Add event code here...
        if (OpType != null) {
            OperationBinding binding = this.getBindings().getOperationBinding("refreshRfqViews");
            binding.getParamsMap().put("OpType", OpType);
            binding.execute();
        }
    }

    public void refreshTrfOrdViewsAL(ActionEvent actionEvent) {
        // Add event code here...
        if (OpType != null) {
            OperationBinding binding = this.getBindings().getOperationBinding("refreshTrfOrdViews");
            binding.getParamsMap().put("OpType", OpType);
            binding.execute();
        }
    }

    public void refreshQuotViewsAL(ActionEvent actionEvent) {
        // Add event code here...
        if (OpType != null) {
            OperationBinding binding = this.getBindings().getOperationBinding("refreshQuotViews");
            binding.getParamsMap().put("OpType", OpType);
            binding.execute();
        }
    }


    public void refreshPOViewsActionListener(ActionEvent actionEvent) {
        // Add event code here...

        if (OpType != null) {
            OperationBinding binding = this.getBindings().getOperationBinding("refreshPoViews");
            binding.getParamsMap().put("OpType", OpType);
            binding.execute();
        }
    }

    public void refreshIPOBoeViewActionListener(ActionEvent actionEvent) {
        // Add event code here
        if (OpType != null) {
            OperationBinding binding = this.getBindings().getOperationBinding("refreshIPOBoeViews");
            binding.getParamsMap().put("OpType", OpType);
            binding.execute();
        }
    }


    // ----------------Purchase Requisition Documents-------------------

    public String purReqPendForMyApproval() {
        System.out.println("Inside purReqPendForMyApproval");

        // Add event code here...
        OpType = "M";
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setPurReqView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValueRfqPage("PurReq");
        facetValue = "PurReq";
        return "wfRfqView";
        //return null;
    }


    public Number getpurReqMyApprovalCount() {

        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18514);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        System.out.println("Count of mrn in MyApproval :" + binding.getResult());
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
        //return MrnMyApprovalCount;
    }


    public String purReqPendForOtherAction() {
        System.out.println("Inside purReqPendForOtherAction");
        // Add event code here...
        OpType = "O";
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setPurReqView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValueRfqPage("PurReq");
        facetValue = "PurReq";
        return "wfRfqView";
    }

    public Number getPurReqPendingForOtherApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18514);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    public String purReqUnpostedAction() {
        System.out.println("Inside purReqUnpostedAction ");
        OpType = "U";
        desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setPurReqView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValueRfqPage("PurReq");
        facetValue = "PurReq";
        return "wfRfqView";

    }

    public Number getPurReqMyUnpostedCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18514);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        System.out.println("Scrap :" + binding.getResult());
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
        //return ScrpMyUnpostedCount;
    }

    //  --------------------------End of Purchase Requisition Document--------------------

    //----------------------------------Start of BOE Document-------------------------------

    public String ipoBoePendForMyApproval() {
        System.out.println("Inside ipoBoePendForMyApproval");

        // Add event code here...
        OpType = "M";
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setIpoBoeView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValueIPOBoe("boe");
        facetValue = "boe";
        return "wfPOBoeView";
        //return null;
    }


    public Number getIpoBoeMyApprovalCount() {

        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18539);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        System.out.println("Count of boe in MyApproval :" + binding.getResult());
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
        //return MrnMyApprovalCount;
    }


    public String ipoBoePendForOtherAction() {
        System.out.println("Inside ipoBoeForOtherAction");
        // Add event code here...
        OpType = "O";
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setIpoBoeView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValueIPOBoe("boe");
        facetValue = "boe";
        return "wfPOBoeView";
    }

    public Number getIpoBoePendingForOtherApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18539);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    public String ipoBoePendUnpostedAction() {
        System.out.println("Inside ipoBoeUnpostedAction ");
        OpType = "U";
        desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setIpoBoeView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValueIPOBoe("boe");
        facetValue = "boe";
        return "wfPOBoeView";

    }

    public Number getIpoBoeMyUnpostedCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18539);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        System.out.println("Scrap :" + binding.getResult());
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
        //return ScrpMyUnpostedCount;
    }

    //public String viewIpoBoeAction() {
    //       return "viewBoePage";
    //    }

    //----------------------End of BOE Documents------------------
    //----------------------Start of Shipment Advice Documents---------
    public String shpAdvcPendForMyApproval() {
        System.out.println("Inside shpAdvcPendForMyApproval");

        // Add event code here...
        OpType = "M";
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setShpAdvcView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValueIPOBoe("shpadvc");
        //facetValue = "shpadvc";
        return "wfPOBoeView";
        //return null;
    }


    public Number getShpAdvcMyApprovalCount() {

        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18538);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        System.out.println("Count of ShpAdvc in MyApproval :" + binding.getResult());
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
        //return MrnMyApprovalCount;
    }


    public String shpAdvcPendForOtherAction() {
        System.out.println("Inside shpAdvcPendForOtherAction");
        // Add event code here...
        OpType = "O";
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setShpAdvcView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValueIPOBoe("shpadvc");

        //facetValue = "shpadvc";
        return "wfPOBoeView";
    }

    public Number getShpAdvcPendingForOtherApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18538);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    public String shpAdvcPendUnpostedAction() {
        System.out.println("Inside shpAdvcPendUnpostedAction");
        OpType = "U";
        desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setShpAdvcView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValueIPOBoe("shpadvc");
        //facetValue = "shpadvc";
        return "wfPOBoeView";

    }

    public Number getShpAdvcMyUnpostedCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18538);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        System.out.println("Scrap :" + binding.getResult());
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
        //return ScrpMyUnpostedCount;
    }

    //----------------------------End of Shipment Advice Document----------
    //-----------------------------Start of Insurance Document---------------
    public String insPendForMyApproval() {
        System.out.println("Inside insPendForMyApproval");

        // Add event code here...
        OpType = "M";
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setInsView");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        setFacetValueIPOBoe("ins");
        //facetValue = "shpadvc";
        return "wfPOBoeView";
        //return null;
    }


    public Number getInsPendingMyApprovalCount() {

        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18540);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.execute();
        System.out.println("Count of Ins in MyApproval :" + binding.getResult());
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
        //return MrnMyApprovalCount;
    }


    public String insPendForOtherAction() {
        System.out.println("Inside insPendForOtherAction");
        // Add event code here...
        OpType = "O";
        desc = new StringBuffer("Pending for Other approval");
        OperationBinding binding = this.getBindings().getOperationBinding("setInsView");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        setFacetValueIPOBoe("ins");

        //facetValue = "shpadvc";
        return "wfPOBoeView";
    }

    public Number getInsPendingForOtherApprovalCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18540);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    public String insPendUnpostedAction() {
        System.out.println("Inside insPendUnpostedAction");
        OpType = "U";
        desc = new StringBuffer("My Unposted Documents.");
        OperationBinding binding = this.getBindings().getOperationBinding("setInsView");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        setFacetValueIPOBoe("ins");
        //facetValue = "shpadvc";
        return "wfPOBoeView";

    }

    public Number getInsPendingMyUnpostedCount() {
        OperationBinding binding = this.getBindings().getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 18540);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.execute();
        System.out.println("Scrap :" + binding.getResult());
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
        //return ScrpMyUnpostedCount;
    }
}
