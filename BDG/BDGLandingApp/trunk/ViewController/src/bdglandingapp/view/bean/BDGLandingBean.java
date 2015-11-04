package bdglandingapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.ebiz.EbizParams;

import alertupdateservice.view.bean.AlertUpdateServiceBean;

import bdglandingapp.model.ds.TickerRowDS;
import bdglandingapp.model.services.BDGLandingAppAMImpl;

import bdglandingapp.view.ds.TickerDispDS;

import java.util.ArrayList;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

public class BDGLandingBean {
    private static ADFLogger _log = ADFLogger.createADFLogger(BDGLandingAppAMImpl.class);
    private ArrayList<TickerRowDS> tickerList = new ArrayList<TickerRowDS>();
    private ArrayList<TickerRowDS> selectedList = new ArrayList<TickerRowDS>();
    private StringBuffer desc = new StringBuffer("");
    private RichPopup settingsPopup;
    private RichPanelGroupLayout panelGroupAlert;
    String txnIdForSalesTarget = "";
    String txnIdForMtlBudget = "";
    String txnIdForFinBudget = "";
    String txnIdForForcast = "";

    public void setTxnIdForForcast(String txnIdForForcast) {
        this.txnIdForForcast = txnIdForForcast;
    }

    public String getTxnIdForForcast() {
        return txnIdForForcast;
    }

    public void setTxnIdForSalesTarget(String txnIdForSalesTarget) {
        this.txnIdForSalesTarget = txnIdForSalesTarget;
    }

    public String getTxnIdForSalesTarget() {
        return txnIdForSalesTarget;
    }

    public void setTxnIdForMtlBudget(String txnIdForMtlBudget) {
        this.txnIdForMtlBudget = txnIdForMtlBudget;
    }

    public String getTxnIdForMtlBudget() {
        return txnIdForMtlBudget;
    }

    public void setTxnIdForFinBudget(String txnIdForFinBudget) {
        this.txnIdForFinBudget = txnIdForFinBudget;
    }

    public String getTxnIdForFinBudget() {
        return txnIdForFinBudget;
    }

    public BDGLandingBean() {
        // To get the total list of selected list of tickers
        OperationBinding bind = ADFBeanUtils.getOperationBinding("getSelectedTickerList");
        if (bind != null) {
            bind.execute();
            if (bind.getResult() != null) {
                selectedList = (ArrayList<TickerRowDS>) bind.getResult();
            }
        }
    }

    public void setDesc(StringBuffer desc) {
        this.desc = desc;
    }

    public StringBuffer getDesc() {
        return desc;
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
     * Method to add a selected row from ticker setup page.
     * @param actionEvent
     */
    public void addTickerACTION(ActionEvent actionEvent) {
        String ticker = "0";
        RichLink ob = (RichLink) actionEvent.getSource();
        ob.getAttributes().get("tickerId");
        System.out.println("Source is : " + ob.getText() + " " + ob.getAttributes().get("tickerId"));
        if (ob.getAttributes().get("tickerId") != null) {
            if (this.selectedList.size() < 4) {
                Integer seq = 0;
                for (TickerRowDS r : this.selectedList) {
                    if (seq < r.getSeqNo()) {
                        seq = r.getSeqNo();
                    }
                }
                seq = seq + 1;
                ticker = ob.getAttributes().get("tickerId").toString();
                for (TickerRowDS r : this.tickerList) {
                    if (ticker.equals(r.getTickerId())) {
                        System.out.println("Seq : " + seq + "Ticker ID : " + ticker + " Desc : " + r.getTickerDesc());
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
                FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.1990']}"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }

        }

    }

    public void settingPopupACTION(ActionEvent actionEvent) {
        _log.info("Inside Setting popup action");
        System.out.println("123-----");
        // To get the total list of tickers
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getTickerList");
        if (binding != null) {
            binding.execute();
            if (binding.getResult() != null) {
                tickerList = (ArrayList<TickerRowDS>) binding.getResult();
            }
        }
        // To get the total list of selected list of tickers
        OperationBinding bind = ADFBeanUtils.getOperationBinding("getSelectedTickerList");
        if (bind != null) {
            bind.execute();
            if (bind.getResult() != null) {
                selectedList.clear();
                selectedList = (ArrayList<TickerRowDS>) bind.getResult();
            }
        }

        // To set ticker description in select tickeres.
        /* for(TickerRowDS t : tickerList){

             for(TickerRowDS r : selectedList){
                if(r.getTickerId().equals(t.getTickerId())){
                    r.setTickerDesc(t.getTickerDesc());
                    break;
                }
            }
        }
         */ // To remove the selecetd tickers from the total list of tickers
        /* for(TickerRowDS t : selectedList){
            for(TickerRowDS r : tickerList){
                if(r.getTickerId().equals(t.getTickerId())){
                    tickerList.remove(r);
                    break;
                }
            }
        } */
        System.out.println("In PopupList :");
        for (TickerRowDS r : selectedList) {
            System.out.println(r.getSeqNo() + " : " + r.getTickerDesc() + " : " + r.getTickerId());
        }
        ADFBeanUtils.showPopup(this.settingsPopup, true);
    }

    public void setSettingsPopup(RichPopup settingsPopup) {
        this.settingsPopup = settingsPopup;
    }

    public RichPopup getSettingsPopup() {
        return settingsPopup;
    }

    public Integer getTickerCount() {
        Integer i = 0;
        if (selectedList != null) {
            i = selectedList.size();
        }
        return i;
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

    /**
     * Save ticker settings
     * @param dialogEvent
     */
    public void saveSettingsPopUpLIST(DialogEvent dialogEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("saveUserSettings");
        binding.execute();
        for (TickerRowDS r : getSelectedList()) {
            OperationBinding bind = ADFBeanUtils.getOperationBinding("insertRecord");
            bind.getParamsMap().put("seqNo", r.getSeqNo());
            bind.getParamsMap().put("tickeId", r.getTickerId());
            bind.execute();
        }
        ADFBeanUtils.getOperationBinding("Commit").execute();
    }


    /**
     * PopPup Cancel Listner
     * @param popupCanceledEvent
     */
    public void settingPopupCancelLIST(PopupCanceledEvent popupCanceledEvent) {

    }


    /**
     * Move the selected ticker up
     * @param actionEvent
     */
    public void moveSelectedUpACTION(ActionEvent actionEvent) {
        Integer sequenceNo = 0;
        RichLink ob = (RichLink) actionEvent.getSource();
        ob.getAttributes().get("sequenceNo");
        System.out.println("Source is : " + ob.getText() + " " + ob.getAttributes().get("sequenceNo"));
        sequenceNo = (Integer) ob.getAttributes().get("sequenceNo");


        if (sequenceNo != 1) {
            TickerRowDS selup = this.selectedList.get(sequenceNo - 2);
            TickerRowDS selcur = this.selectedList.get(sequenceNo - 1);
            selup.setSeqNo(sequenceNo);
            selcur.setSeqNo(sequenceNo - 1);
            this.selectedList.set(sequenceNo - 2, selcur);
            this.selectedList.set(sequenceNo - 1, selup);
        }

        /*  if(sequenceNo != 1){
            for(TickerRowDS r : this.selectedList){
                if( r.getSequenceNo() == (sequenceNo - 1) ){
                    selup = r;
                }
                if( r.getSequenceNo() == sequenceNo ){
                    selcur = r;
                }
            }
        } */
    }

    /**
     * Move the selected ticker down
     * @param actionEvent
     */
    public void moveSelectedDownACTION(ActionEvent actionEvent) {
        Integer sequenceNo = 0;
        RichLink ob = (RichLink) actionEvent.getSource();
        ob.getAttributes().get("sequenceNo");
        System.out.println("Source is : " + ob.getText() + " " + ob.getAttributes().get("sequenceNo"));
        sequenceNo = (Integer) ob.getAttributes().get("sequenceNo");


        if (sequenceNo != 3) {
            TickerRowDS selcur = this.selectedList.get(sequenceNo - 1);
            TickerRowDS seldn = this.selectedList.get(sequenceNo);
            seldn.setSeqNo(sequenceNo);
            selcur.setSeqNo(sequenceNo + 1);
            this.selectedList.set(sequenceNo, selcur);
            this.selectedList.set(sequenceNo - 1, seldn);
        }

    }

    /**
     * Method to delete a selected row from ticker setup page.
     * @param actionEvent
     */
    public void deleteTickerACTION(ActionEvent actionEvent) {
        String ticker = "0";
        RichLink ob = (RichLink) actionEvent.getSource();
        ob.getAttributes().get("tickerId");
        System.out.println("Source is : " + ob.getText() + " " + ob.getAttributes().get("tickerId"));
        if (ob.getAttributes().get("tickerId") != null) {
            Integer seq = 0;
            for (TickerRowDS r : this.tickerList) {
                if (seq < r.getSeqNo()) {
                    seq = r.getSeqNo();
                }
            }
            seq = seq + 1;
            ticker = ob.getAttributes().get("tickerId").toString();
            for (TickerRowDS r : this.selectedList) {
                if (ticker.equals(r.getTickerId())) {
                    System.out.println("Seq : " + seq + "Ticker ID : " + ticker + " Desc : " + r.getTickerDesc());
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
    }


    /********************************************* For Sales Target *********************************************************************/

    /**
     * Method to get Sales Target Count Pending for my Approval
     * @return
     */
    public Number getSalesTargetPendingForMyApprovalCount() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 34002);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.getParamsMap().put("type", "S");
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    public Number getForcastPendingForMyApprovalCount() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 34002);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.getParamsMap().put("type", "F");
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Sales Target Count Pending
     * @return
     */
    public Number getMySalesTargetPendingCount() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 34002);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.getParamsMap().put("type", "S");
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    public Number getMyForcastPendingCount() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 34002);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.getParamsMap().put("type", "F");
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get SalesTarget Pending for Others Approval
     * @return
     */
    public Number getSalesTargetPendingAtOthersCount() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 34002);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.getParamsMap().put("type", "S");
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    public Number getForcastPendingAtOthersCount() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 34002);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.getParamsMap().put("type", "F");
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * ACTION to SalesTargetPendingForMyApproval
     * @return
     */
    public String salesTargetForMyApprovalACTION() {
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = ADFBeanUtils.getOperationBinding("setParametersInWFSalesTargetVo");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        return "wfSales";
    }

    public String forcastForMyApprovalACTION() {
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = ADFBeanUtils.getOperationBinding("setParametersInWFForcastVo");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        return "wfForcast";
    }

    /**
     * ACTION to SalesTargetPendingOnOthersMyApproval
     * @return
     */
    public String salesTargetPendingWithOthersACTION() {
        desc = new StringBuffer("Pending at others");
        OperationBinding binding = ADFBeanUtils.getOperationBinding("setParametersInWFSalesTargetVo");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        return "wfSales";
    }

    public String forcastPendingWithOthersACTION() {
        desc = new StringBuffer("Pending at others");
        OperationBinding binding = ADFBeanUtils.getOperationBinding("setParametersInWFForcastVo");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        return "wfForcast";
    }

    /**
     * ACTION to SalesTargetUnposted
     * @return
     */
    public String salesTargetUnpostedACTION() {
        desc = new StringBuffer("Unposted");
        OperationBinding binding = ADFBeanUtils.getOperationBinding("setParametersInWFSalesTargetVo");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        return "wfSales";
    }

    public String forcastUnpostedACTION() {
        desc = new StringBuffer("Unposted");
        OperationBinding binding = ADFBeanUtils.getOperationBinding("setParametersInWFForcastVo");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        return "wfForcast";
    }


    /******************************************* For Material Budget **********************************************************/

    /**
     * Method to get Material Budget Count Pending for my Approval
     * @return
     */
    public Number getMaterialBudgetPendingForMyApprovalCount() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 34003);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.getParamsMap().put("type", null);
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }


    /**
     * ACTION to MaterialBudgetForMyApproval
     * @return
     */
    public String materialBudgetForMyApprovalACTION() {
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = ADFBeanUtils.getOperationBinding("setParametersInWFMaterialBudgetVo");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        return "wfMtl";
    }

    /**
     * ACTION to MaterialBudgetPendingOnOthersMyApproval
     * @return
     */
    public String materialBudgetPendingWithOthersACTION() {
        desc = new StringBuffer("Pending at others");
        OperationBinding binding = ADFBeanUtils.getOperationBinding("setParametersInWFMaterialBudgetVo");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        return "wfMtl";
    }

    /**
     * ACTION to MaterialBudgetUnposted
     * @return
     */
    public String materialBudgetUnpostedACTION() {
        desc = new StringBuffer("Unposted");
        OperationBinding binding = ADFBeanUtils.getOperationBinding("setParametersInWFMaterialBudgetVo");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        return "wfMtl";
    }

    /**
     * Method to get  MaterialBudget Count Pending for Others Approval
     * @return
     */
    public Number getMaterialBudgetPendingAtOthersCount() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 34003);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.getParamsMap().put("type", null);
        binding.execute();

        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get Material Budget Count Pending
     * @return
     */
    public Number getMyMaterialBudgetPendingCount() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 34003);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.getParamsMap().put("type", null);
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /******************************************* For Finance Budget **********************************************************/

    /**
     * Method to get Fincance Budget Count Pending for my Approval
     * @return
     */
    public Number getFinanceBudgetPendingForMyApprovalCount() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 34004);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.getParamsMap().put("type", null);
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * Method to get FinanceBudget Count Pending for Others Approval
     * @return
     */
    public Number getFinanceBudgetPendingAtOthersCount() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 34004);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.getParamsMap().put("type", null);
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    /**
     * ACTION to FinanceBudgetForMyApproval
     * @return
     */
    public String financeBudgetForMyApprovalACTION() {
        desc = new StringBuffer("Pending for My approval");
        OperationBinding binding = ADFBeanUtils.getOperationBinding("setParametersInWFFinanceBudgetVo");
        binding.getParamsMap().put("type", new StringBuffer("M"));
        binding.execute();
        return "wfFin";
    }

    /**
     * ACTION to FinanceBudgetPendingOnOthersMyApproval
     * @return
     */
    public String financeBudgetPendingWithOthersACTION() {
        desc = new StringBuffer("Pending at others");
        OperationBinding binding = ADFBeanUtils.getOperationBinding("setParametersInWFFinanceBudgetVo");
        binding.getParamsMap().put("type", new StringBuffer("O"));
        binding.execute();
        return "wfFin";
    }

    /**
     * ACTION to FinanceBudgetUnposted
     * @return
     */
    public String financeBudgetUnpostedACTION() {
        desc = new StringBuffer("Unposted");
        OperationBinding binding = ADFBeanUtils.getOperationBinding("setParametersInWFFinanceBudgetVo");
        binding.getParamsMap().put("type", new StringBuffer("U"));
        binding.execute();
        return "wfFin";
    }

    /**
     * Method to get FinanceBudget Count Pending
     * @return
     */
    public Number getMyFinanceBudgetPendingCount() {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 34004);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.getParamsMap().put("type", null);
        binding.execute();
        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    // Code For Alert and Update Service
    private AlertUpdateServiceBean bean = new AlertUpdateServiceBean();
    RichPanelFormLayout panelForm;
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
                          "00702");
        this.panelForm.getChildren().add(o1);
        System.out.println("addind Refresh");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.panelForm);
        AdfFacesContext.getCurrentInstance().addPartialTarget(o1);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelGroupAlert());
    }

    public void getUpdate(ClientEvent clientEvent) {
        System.out.println("I am in bean");
        bean.getUpdate(this.getPanelForm(), EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(),
                       EbizParams.GLBL_HO_ORG_ID(), EbizParams.GLBL_APP_USR_ORG(), EbizParams.GLBL_APP_USR(), "00702");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelForm());

    }

    public void setPanelForm(RichPanelFormLayout panelForm) {
        this.panelForm = panelForm;
    }

    public RichPanelFormLayout getPanelForm() {
        return panelForm;
    }

    public void setPanelGroupAlert(RichPanelGroupLayout panelGroupAlert) {
        this.panelGroupAlert = panelGroupAlert;
    }

    public RichPanelGroupLayout getPanelGroupAlert() {
        return panelGroupAlert;
    }

    /**
     * Action to go to SI Application
     * @return
     */
    public String viewSalesTargetACTION() {
        if (this.txnIdForSalesTarget != null && this.txnIdForSalesTarget.length() > 0)
            return "goToSalesTarget";
        else
            return null;
    }

    public String viewForcastACTION() {
        if (this.txnIdForForcast != null && this.txnIdForForcast.length() > 0)
            return "goToForcast";
        else
            return null;
    }

    /**
     * Action to go to SI Application
     * @return
     */
    public String viewMtlBudgetACTION() {
        if (this.txnIdForMtlBudget != null && this.txnIdForMtlBudget.length() > 0)
            return "goToMtlBudget";
        else
            return null;
    }

    /**
     * Action to go to SI Application
     * @return
     */
    public String viewFinBudgetACTION() {
        if (this.txnIdForFinBudget != null && this.txnIdForFinBudget.length() > 0)
            return "goToFinBudget";
        else
            return null;
    }

    public void viewFinBudgetAL(ActionEvent actionEvent) {
        RichLink ob = (RichLink) actionEvent.getSource();
        if (ob.getAttributes().get("docId") != null)
            this.txnIdForFinBudget = (String) ob.getAttributes().get("docId");
    }

    public void viewMtlBudgetAL(ActionEvent actionEvent) {
        RichLink ob = (RichLink) actionEvent.getSource();
        if (ob.getAttributes().get("docId") != null)
            this.txnIdForMtlBudget = (String) ob.getAttributes().get("docId");
    }

    public void viewSalesTargetAL(ActionEvent actionEvent) {
        RichLink ob = (RichLink) actionEvent.getSource();
        if (ob.getAttributes().get("docId") != null)
            this.txnIdForSalesTarget = (String) ob.getAttributes().get("docId");
    }

    public void viewForcastAL(ActionEvent actionEvent) {
        RichLink ob = (RichLink) actionEvent.getSource();
        if (ob.getAttributes().get("docId") != null)
            this.txnIdForForcast = (String) ob.getAttributes().get("docId");
    }

    public String misFullScreenAction() {
        // Add event code here...
        return "FullMIS";
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = "N";
        if (valueExp != null) {
            msg = valueExp.getValue(elContext).toString();
        }
        return msg;
    }
}
