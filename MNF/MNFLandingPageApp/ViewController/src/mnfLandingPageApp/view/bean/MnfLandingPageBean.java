
package mnfLandingPageApp.view.bean;


import adf.utils.ebiz.EbizParams;

import alertupdateservice.view.bean.AlertUpdateServiceBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import mnfLandingPageApp.model.classes.TickerRowDS;

import mnfLandingPageApp.view.classes.TickerDispDS;
import mnfLandingPageApp.view.utils.ADFUtils;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;

public class MnfLandingPageBean {
    private RichPanelBox bind;
    OperationBinding ob = null;
    private RichPopup tickerPopUpBinding;
    private ArrayList<TickerRowDS> tickerList = new ArrayList<TickerRowDS>();
    private ArrayList<TickerRowDS> selectedList = new ArrayList<TickerRowDS>();
    private RichPanelBox tickerPanelBoxBinding;
    private Integer topNProductVal = 3;
    private String viewPage = null;
    private String wf_Type = null;

    public void setWf_Type(String wf_Type) {
        this.wf_Type = wf_Type;
    }

    public String getWf_Type() {
        return wf_Type;
    }

    public void setViewPage(String viewPage) {
        this.viewPage = viewPage;
    }

    public String getViewPage() {
        System.out.println(" getViewPage " + viewPage);
        return viewPage;
    }

    public void setTopNProductVal(Integer topNProductVal) {
        this.topNProductVal = topNProductVal;
    }

    public Integer getTopNProductVal() {
        return topNProductVal;
    }

    public MnfLandingPageBean() {

        OperationBinding ob = ADFUtils.findOperation("getSelectedTickerList");
        ob.execute();

        if (ob.getResult() != null) {
            selectedList = (ArrayList<TickerRowDS>) ob.getResult();
        }

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

    /**
     * Method to getBindings
     * @return
     */
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     * Move the selected ticker up
     * @param actionEvent
     */
    public void moveSelectedUpACTION(ActionEvent actionEvent) {
        Integer sequenceNo = 0;

        sequenceNo = (Integer) actionEvent.getComponent().getAttributes().get("tkrSeq");

        if (sequenceNo != 1) {
            TickerRowDS selup = this.selectedList.get(sequenceNo - 2);
            TickerRowDS selcur = this.selectedList.get(sequenceNo - 1);
            selup.setSeqNo(sequenceNo);
            selcur.setSeqNo(sequenceNo - 1);
            this.selectedList.set(sequenceNo - 2, selcur);
            this.selectedList.set(sequenceNo - 1, selup);
        }
    }

    /**
     * Move the selected ticker down
     * @param actionEvent
     */
    public void moveSelectedDownACTION(ActionEvent actionEvent) {
        Integer sequenceNo = 0;

        sequenceNo = (Integer) actionEvent.getComponent().getAttributes().get("tkrSeq");


        if (sequenceNo != 3) {
            TickerRowDS selcur = this.selectedList.get(sequenceNo - 1);
            TickerRowDS seldn = this.selectedList.get(sequenceNo);
            seldn.setSeqNo(sequenceNo);
            selcur.setSeqNo(sequenceNo + 1);
            this.selectedList.set(sequenceNo, selcur);
            this.selectedList.set(sequenceNo - 1, seldn);
        }

    }

    public ArrayList<TickerDispDS> getTickerSequence() {
        ArrayList<TickerDispDS> list = new ArrayList<TickerDispDS>();

        list.clear();

        for (TickerRowDS r : selectedList) {
            TickerDispDS t = new TickerDispDS();
            t.setSeqNo(r.getSeqNo());
            t.setTickerId(r.getTickerId());
            t.setTickerDesc(r.getTickerDesc());
            t.setArrow(r.getArrow());
            t.setCurrentAmt(r.getCurrentAmt());
            t.setPreviousAmt(r.getPreviousAmt());
            list.add(t);
        }
        return list;
    }

    public void tickerSettingACE(ActionEvent actionEvent) {

        ob = ADFUtils.findOperation("getUnSelectedTickerList");
        ob.execute();

        if (ob.getResult() != null) {
            tickerList = (ArrayList<TickerRowDS>) ob.getResult();
        }

        ADFUtils.showPopup(tickerPopUpBinding, true);
    }

    public void setTickerPopUpBinding(RichPopup tickerPopUpBinding) {
        this.tickerPopUpBinding = tickerPopUpBinding;
    }

    public RichPopup getTickerPopUpBinding() {
        return tickerPopUpBinding;
    }

    /**
     * Method to add a selected row from ticker setup page.
     * @param actionEvent
     */
    public void addTickerACE(ActionEvent actionEvent) {
        String ticker = "0";

        if (actionEvent.getComponent().getAttributes().get("tickerId") != null) {
            if (this.selectedList.size() < 4) {
                Integer seq = 0;
                for (TickerRowDS r : this.selectedList) {
                    if (seq < r.getSeqNo()) {
                        seq = r.getSeqNo();
                    }
                }
                seq = seq + 1;
                ticker = actionEvent.getComponent().getAttributes().get("tickerId").toString();
                for (TickerRowDS r : this.tickerList) {
                    if (ticker.equals(r.getTickerId())) {
                        //System.out.println("Seq : " + seq + "Ticker ID : " + ticker + " Desc : " + r.getTickerDesc());
                        TickerRowDS t = new TickerRowDS();
                        t.setTickerId(ticker);
                        t.setSeqNo(seq);
                        t.setTickerDesc(r.getTickerDesc());
                        t.setCurr(r.getCurr());
                        t.setCurrentAmt(r.getCurrentAmt());
                        t.setPreviousAmt(r.getPreviousAmt());
                        selectedList.add(t);
                        tickerList.remove(r);
                        break;
                    }
                }
            } else {

                ADFUtils.showFacesMsg("Cannot select more that Four Tickers !",
                                      "Cannot select more that Four Tickers !", FacesMessage.SEVERITY_INFO,
                                      actionEvent.getComponent().getClientId());
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tickerPanelBoxBinding);
        }

    }

    public void tickerPopUpDialogListener(DialogEvent dialogEvent) {

        ViewObject vo = ADFUtils.findIterator("MmTkrUsrVOIterator").getViewObject();

        RowSetIterator rsetIter = vo.createRowSetIterator(null);
        Row row = null;

        //reset ticker postion to 0 for all ticker
        while (rsetIter.hasNext()) {

            row = rsetIter.next();
            row.setAttribute("TkrPos", 0);

        }
        rsetIter.closeRowSetIterator();

        //set ticker postions for selected ticker
        for (TickerRowDS r : this.getSelectedList()) {

            setTickerSeq(r.getTickerId(), r.getSeqNo());
        }

        //commit data
        ADFUtils.findOperation("Commit").execute();
    }

    protected void setTickerSeq(String tkrId, Integer seqNo) {

        if (tkrId != null && seqNo != null) {
            ViewObject vo = ADFUtils.findIterator("MmTkrUsrVOIterator").getViewObject();

            RowSetIterator rsetIter = vo.createRowSetIterator(null);
            Row row = null;

            while (rsetIter.hasNext()) {

                row = rsetIter.next();
                if (tkrId.equals(row.getAttribute("TkrId"))) {
                    // System.out.println(" tkrId " + tkrId + " seqNo " + seqNo);
                    row.setAttribute("TkrPos", seqNo);
                }
            }
            rsetIter.closeRowSetIterator();
        }
    }

    public void removeTickerACE(ActionEvent actionEvent) {
        String ticker = "0";

        if (actionEvent.getComponent().getAttributes().get("tickerId") != null) {
            Integer seq = 0;
            for (TickerRowDS r : this.tickerList) {
                if (seq < r.getSeqNo()) {
                    seq = r.getSeqNo();
                }
            }
            seq = seq + 1;
            ticker = actionEvent.getComponent().getAttributes().get("tickerId").toString();
            for (TickerRowDS r : this.selectedList) {
                if (ticker.equals(r.getTickerId())) {
                    //  System.out.println("Seq : " + seq + "Ticker ID : " + ticker + " Desc : " + r.getTickerDesc());
                    TickerRowDS t = new TickerRowDS();
                    t.setTickerId(ticker);
                    t.setSeqNo(seq);
                    t.setTickerDesc(r.getTickerDesc());
                    t.setCurr(r.getCurr());
                    t.setCurrentAmt(r.getCurrentAmt());
                    t.setPreviousAmt(r.getPreviousAmt());

                    tickerList.add(t);
                    selectedList.remove(r);
                    break;
                }
            }
            seq = 1;
            for (TickerRowDS r : this.selectedList) {
                r.setSeqNo(seq);
                seq = seq + 1;
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(tickerPanelBoxBinding);
    }

    public void setTickerPanelBoxBinding(RichPanelBox tickerPanelBoxBinding) {
        this.tickerPanelBoxBinding = tickerPanelBoxBinding;
    }

    public RichPanelBox getTickerPanelBoxBinding() {
        return tickerPanelBoxBinding;
    }

    public void topNProductVCE(ValueChangeEvent vce) {

        ob = ADFUtils.findOperation("setTopNProductManufactured");
        ob.getParamsMap().put("n", vce.getNewValue());
        ob.execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(vce.getComponent().getParent());
    }

    public void documentViewACE(ActionEvent ae) {
        Object docId = ae.getComponent().getAttributes().get("docId");
        Object wfTyp = ae.getComponent().getAttributes().get("wfTyp");

        System.out.println("docId " + docId + " wfTyp " + wfTyp);

        ob = ADFUtils.findOperation("setDocumentWfTypeParam");
        ob.getParamsMap().put("document", docId);
        ob.getParamsMap().put("wf_type", wfTyp);
        ob.execute();

        if (ob.getResult() != null) {
            setViewPage(ob.getResult().toString());
        }

        if (wfTyp.equals("O")) {
            setWf_Type("Pending at Others");
        } else if (wfTyp.equals("M")) {
            setWf_Type("Pending for my approval");
        } else if (wfTyp.equals("U")) {
            setWf_Type("Unposted ");
        }

    }

    public static class ChartDataItem extends HashMap<String, Object> {
        @SuppressWarnings("compatibility")
        private static final long serialVersionUID = 1L;

        public void AdfChartBean() {

        }

        public ChartDataItem(String series, Object group, Number value) {
            put("series", series);
            put("group", group);
            put("value", value);
        }
    }

    public CollectionModel getProductionSummary() {
        return getProductData();
    }

    public CollectionModel getProductData() {
        List<ChartDataItem> dataItems = new ArrayList<ChartDataItem>();
        ViewObject vo = ADFUtils.findIterator("ProductionSummaryAnalysisVOIterator").getViewObject();
        RowSetIterator rsetIter = vo.createRowSetIterator(null);
        Row r = null;
        while (rsetIter.hasNext()) {
            r = rsetIter.next();
            //System.out.println(" item ---->"+r.getAttribute("ItmDesc"));
            dataItems.add(new ChartDataItem(r.getAttribute("ItmDesc").toString(), r.getAttribute("DocDt"),
                                            (Number) r.getAttribute("OutptItmQty")));
        }
        rsetIter.closeRowSetIterator();

        return ModelUtils.toCollectionModel(dataItems);
    }

    // Code For Alert and Update Service
    private AlertUpdateServiceBean bean = new AlertUpdateServiceBean();
    private RichPanelFormLayout panelForm;
    private RichPanelGroupLayout panelGroup;
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
                          "00403");
        this.panelForm.getChildren().add(o1);
        System.out.println("addind Refresh");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.panelForm);
        AdfFacesContext.getCurrentInstance().addPartialTarget(o1);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelGroup());


    }

    public void getUpdate(ClientEvent clientEvent) {
        System.out.println("I am in bean");
        bean.getUpdate(this.getPanelForm(), EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(),
                       EbizParams.GLBL_HO_ORG_ID(), EbizParams.GLBL_APP_USR_ORG(), EbizParams.GLBL_APP_USR(), "00403");
        //    AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelForm());
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

}
