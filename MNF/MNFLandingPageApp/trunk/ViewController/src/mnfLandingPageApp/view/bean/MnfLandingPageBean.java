
package mnfLandingPageApp.view.bean;


import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;


import mnfLandingPageApp.model.classes.TickerRowDS;

import mnfLandingPageApp.view.classes.TickerDispDS;
import mnfLandingPageApp.view.utils.ADFUtils;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

public class MnfLandingPageBean {
    private RichPanelBox bind;
    OperationBinding ob = null;
    private RichPopup tickerPopUpBinding;
    private ArrayList<TickerRowDS> tickerList = new ArrayList<TickerRowDS>();
    private ArrayList<TickerRowDS> selectedList = new ArrayList<TickerRowDS>();
    private RichPanelBox tickerPanelBoxBinding;

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
}
