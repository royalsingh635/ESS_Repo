package trnpdashboardapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import alertupdateservice.view.bean.AlertUpdateServiceBean;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.faces.bi.component.chart.UIDataItem;
import oracle.adf.view.faces.bi.component.treemap.UITreemap;
import oracle.adf.view.faces.bi.component.treemap.UITreemapNode;
import oracle.adf.view.faces.bi.event.DrillReplaceEvent;
import oracle.adf.view.rich.component.rich.RichNoteWindow;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichImage;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.SelectionEvent;

import trnpdashboardapp.model.ds.TickerRowDS;

import trnpdashboardapp.view.ds.TickerDispDS;

public class TrnpDashboardBean {

    String type = null;
    String olDocId = null;
    String liDocId = null;
    String loDocId = null;
    String lrDocId = null;
    String status = null;
    Number capCc = null;
    String releaseDt = null;
    String vhclRegno = null;
    String vhclDesc = null;
    Integer vhclId = null;
    Number grossWt = null;
    Number mileageLoad = null;
    String chassisNo = null;
    String engineNo = null;
    
    ArrayList<TickerRowDS> selectedList = new ArrayList<TickerRowDS>();
    private RichPopup settingPopup;
    private UITreemap vhclStatusTreemapBind;
    private RichOutputText cldIdBind;
    private RichOutputText hoOrgIdBind;
    private RichOutputText slocIdBind;
    private RichOutputText statusIdBind;
    String visible = "N";
  //  private RichPanelGroupLayout vhclDetailPGBind;
    private RichOutputText vhclRegNoBind;
    private RichOutputText statusBind;
    private RichOutputText releaseDtBind;
    private RichOutputText capacityBind;
    private RichOutputText categoryBind;
    private RichImage vhclImageBind;
    private RichPopup bubbleInfoPopup;
    private RichNoteWindow bubbleNoteBind;
    private RichPopup bubblePopupBind;
    private UIDataItem bubbleDataItemBind;
    private UITreemapNode treeMapNodeBinding;

    public void setSelectedList(ArrayList<TickerRowDS> selectedList) {
        this.selectedList = selectedList;
    }

    public ArrayList<TickerRowDS> getSelectedList() {
        return selectedList;
    }

    public void setTickerList(ArrayList<TickerRowDS> tickerList) {
        this.tickerList = tickerList;
    }

    public ArrayList<TickerRowDS> getTickerList() {
        return tickerList;
    }
    ArrayList<TickerRowDS> tickerList = new ArrayList<TickerRowDS>();

    public void setLrDocId(String lrDocId) {
        this.lrDocId = lrDocId;
    }

    public String getLrDocId() {
        return lrDocId;
    }

    public void setLoDocId(String loDocId) {
        this.loDocId = loDocId;
    }

    public String getLoDocId() {
        return loDocId;
    }


    public void setLiDocId(String liDocId) {
        this.liDocId = liDocId;
    }

    public String getLiDocId() {
        return liDocId;
    }


    public void setOlDocId(String olDocId) {
        this.olDocId = olDocId;
    }

    public String getOlDocId() {
        return olDocId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public TrnpDashboardBean() {

        // To get the total list of selected list of tickers
        OperationBinding bind = ADFBeanUtils.getOperationBinding("getSelectedTickerList");
        if (bind != null) {
            bind.execute();
            if (bind.getResult() != null) {
                selectedList = (ArrayList<TickerRowDS>) bind.getResult();
            }
        }
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

    public void settingPopupAL(ActionEvent actionEvent) {

        System.out.println("Inside Setting popup action");
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

        System.out.println("In PopupList :");

        for (TickerRowDS r : selectedList) {
            System.out.println(r.getSeqNo() + " : " + r.getTickerDesc() + " : " + r.getTickerId());
        }

        ADFBeanUtils.showPopup(this.settingPopup, true);

    }


    public Number getLoadingReqPendingForMyApprovalCount() {

        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 24252);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.getParamsMap().put("type", "R");
        binding.execute();

        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    public Number getLoadingReqPendingAtOthersCount() {

        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 24252);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.getParamsMap().put("type", "R");
        binding.execute();

        if (binding.getResult().toString() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }

    }

    public Number getMyLoadingReqPending() {

        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 24252);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.getParamsMap().put("type", "R");
        binding.execute();

        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    public Number getLoadingOrderPendingForMyApproval() {

        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 24255);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.getParamsMap().put("type", "O");
        binding.execute();

        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    public Number getLoadingOrderPendingAtOthers() {

        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 24255);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.getParamsMap().put("type", "O");
        binding.execute();

        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    public Number getMyLoadingOrderPending() {

        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 24255);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.getParamsMap().put("type", "O");
        binding.execute();

        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    public Number getOffloadingPendingForMyApproval() {

        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 24256);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.getParamsMap().put("type", "F");
        binding.execute();

        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    public Number getOffloadingPendingAtOthers() {

        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 24256);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.getParamsMap().put("type", "F");
        binding.execute();

        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    public Number getMyOffloadingPending() {

        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 24256);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.getParamsMap().put("type", "F");
        binding.execute();

        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    public Number getLoadingInvoicePendingForMyApproval() {

        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 24257);
        binding.getParamsMap().put("CountType", new StringBuffer("M"));
        binding.getParamsMap().put("type", "I");
        binding.execute();

        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    public Number getLoadingInvoicePendingAtOthers() {

        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 24257);
        binding.getParamsMap().put("CountType", new StringBuffer("O"));
        binding.getParamsMap().put("type", "I");
        binding.execute();

        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    public Number getMyLoadingInvoicePending() {

        OperationBinding binding = ADFBeanUtils.getOperationBinding("getDocPendingCountForMyApproval");
        binding.getParamsMap().put("DocTypeId", 24257);
        binding.getParamsMap().put("CountType", new StringBuffer("U"));
        binding.getParamsMap().put("type", "I");
        binding.execute();

        if (binding.getResult() != null) {
            return (Number) binding.getResult();
        } else {
            return new Number(0);
        }
    }

    public Number getDocumentPendingMyApproval() {
        Number val =
            this.getLoadingReqPendingForMyApprovalCount().add(this.getLoadingOrderPendingForMyApproval().add(this.getOffloadingPendingForMyApproval().add(this.getLoadingInvoicePendingForMyApproval())));

        return val;
    }

    public Number getDocumentPendingAtOthers() {
        Number val =
            this.getLoadingReqPendingAtOthersCount().add(this.getLoadingOrderPendingAtOthers().add(this.getOffloadingPendingAtOthers().add(this.getLoadingInvoicePendingAtOthers())));

        return val;
    }

    public Number getMyDocumentsPending() {
        Number val =
            this.getMyLoadingReqPending().add(this.getMyLoadingOrderPending().add(this.getMyOffloadingPending().add(this.getMyLoadingInvoicePending())));

        return val;
    }

    public String lrPendingMyApprovalAction() {

        RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_WF_TYPE", "M");

        return "LR";
    }

    public String lrPendingOthersApproval() {

        RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_WF_TYPE", "O");

        return "LR";
    }

    public String lrMyPendingApproval() {

        RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_WF_TYPE", "U");

        return "LR";
    }

    public String loPendingMyApproval() {

        RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_WF_TYPE", "M");

        return "LO";
    }

    public String loPendingOthersApproval() {

        RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_WF_TYPE", "O");

        return "LO";
    }

    public String loMyPendingApproval() {

        RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_WF_TYPE", "U");

        return "LO";
    }

    public String ofPendingMyApproval() {

        RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_WF_TYPE", "M");

        return "OL";
    }

    public String olPendingOthersApproval() {

        RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_WF_TYPE", "O");

        return "OL";
    }

    public String olMyPendingApproval() {

        RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_WF_TYPE", "U");

        return "OL";
    }

    public String liPendingMyApproval() {

        RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_WF_TYPE", "M");

        return "LI";
    }

    public String liPendingOthersApproval() {

        RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_WF_TYPE", "O");

        return "LI";
    }

    public String liMyPendingApproval() {

        RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_WF_TYPE", "U");

        return "LI";
    }

    public String viewOffloadingAction() {

        if (olDocId != null && olDocId.length() > 0)
            return "viewOffloading";
        else
            return null;

    }

    public void viewOffloadingAL(ActionEvent actionEvent) {

        RichLink ob = (RichLink) actionEvent.getSource();

        if (ob.getAttributes().get("olDocId") != null)
            this.olDocId = (String) ob.getAttributes().get("olDocId");

    }

    public String viewLoadingInvAction() {

        if (liDocId != null && liDocId.length() > 0)
            return "viewLoadingInvoice";
        else
            return null;
    }

    public void viewLoadingInvAL(ActionEvent actionEvent) {

        RichLink ob = (RichLink) actionEvent.getSource();

        if (ob.getAttributes().get("liDocId") != null)
            this.liDocId = (String) ob.getAttributes().get("liDocId");

    }

    public String viewLoadingOrderAction() {

        if (loDocId != null && loDocId.length() > 0)
            return "viewLoadingOrder";
        else
            return null;
    }

    public void viewLoadingOrderAL(ActionEvent actionEvent) {

        RichLink ob = (RichLink) actionEvent.getSource();

        if (ob.getAttributes().get("loDocId") != null)
            this.loDocId = (String) ob.getAttributes().get("loDocId");

    }

    public String viewLoadingReqAction() {

        if (lrDocId != null && lrDocId.length() > 0)
            return "viewLoadingRequest";
        else
            return null;
    }

    public void viewLoadingReqAL(ActionEvent actionEvent) {

        RichLink ob = (RichLink) actionEvent.getSource();

        if (ob.getAttributes().get("lrDocId") != null)
            this.lrDocId = (String) ob.getAttributes().get("lrDocId");

    }

    public void setSettingPopup(RichPopup settingPopup) {
        this.settingPopup = settingPopup;
    }

    public RichPopup getSettingPopup() {
        return settingPopup;
    }

    public void addTickerAL(ActionEvent actionEvent) {

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
                FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1990"));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }

        }
    }

    public void deleteTickerAL(ActionEvent actionEvent) {

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

    public void moveSelectedTickerUpAL(ActionEvent actionEvent) {
        Integer sequenceNo = 0;
        RichLink ob = (RichLink) actionEvent.getSource();
        ob.getAttributes().get("sequenceNo");
        System.out.println("Source is : " + ob.getText() + " " + ob.getAttributes().get("sequenceNo"));
        sequenceNo = (Integer) ob.getAttributes().get("sequenceNo");

        System.out.println("selectedList size :: "+selectedList.size());

        if (sequenceNo != 1) {
            TickerRowDS selup = this.selectedList.get(sequenceNo - 2);
            TickerRowDS selcur = this.selectedList.get(sequenceNo - 1);
            selup.setSeqNo(sequenceNo);
            selcur.setSeqNo(sequenceNo - 1);
            this.selectedList.set(sequenceNo - 2, selcur);
            this.selectedList.set(sequenceNo - 1, selup);
        }
    }

    public void moveSelectedTickerDownAL(ActionEvent actionEvent) {
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

    public void tickerSettingsDialogEvent(DialogEvent dialogEvent) {

        System.out.println("Inside dialog event with outcome :: "+dialogEvent.getOutcome());
        
        OperationBinding binding = ADFBeanUtils.getOperationBinding("saveUserSettings");
        binding.execute();

        for (TickerRowDS r : getSelectedList()) {
            OperationBinding bind = ADFBeanUtils.getOperationBinding("insertRecord");
            bind.getParamsMap().put("seqNo", r.getSeqNo());
            bind.getParamsMap().put("tickerId", r.getTickerId());
            bind.execute();
        }
        
        OperationBinding commit = ADFBeanUtils.getOperationBinding("commitAll");
        commit.execute();

        ADFBeanUtils.getOperationBinding("Commit").execute();
    }

    public void settingPopupCancelListener(PopupCanceledEvent popupCanceledEvent) {

    }


    //   For Alert and Update Service

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
                          "00309");
        this.panelForm.getChildren().add(o1);
        System.out.println("adding Refresh");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.panelForm);
        AdfFacesContext.getCurrentInstance().addPartialTarget(o1);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanelGroup());


    }

    public void getUpdate(ClientEvent clientEvent) {
        System.out.println("I am in bean");
        bean.getUpdate(this.getPanelForm(), EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(),
                       EbizParams.GLBL_HO_ORG_ID(), EbizParams.GLBL_APP_USR_ORG(), EbizParams.GLBL_APP_USR(), "00309");
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

    public void resetAL(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("reset").execute();
    }

    public void searchAL(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("search").execute();
    }

    // get a value as object from an expression
    private Object getValueFromExpression(String name) {
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        Application app = facesCtx.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesCtx.getELContext();
        Object obj = elFactory.createValueExpression(elContext, name, Object.class).getValue(elContext);
        return obj;
    }

    public void vhclStatusTreemapDL(DrillReplaceEvent drillReplaceEvent) throws SQLException {

        System.out.println("Drill details :: " + drillReplaceEvent.toString());
        System.out.println("Drill details :: " + drillReplaceEvent.getComponent());
        System.out.println("Drill details :: " + drillReplaceEvent.getNewKey());
        System.out.println("Drill details :: " + drillReplaceEvent.getOldKey());
        
        if (drillReplaceEvent.getNewKey() != null && drillReplaceEvent.getNewKey().toString().length() > 34 ) {

           // vhclDetailPGBind.setInlineStyle("padding:30px; font-size:13px;");
            //   vhclImageBind.setInlineStyle("width:60px; height:60px; border:solid 3px #999999; margin-bottom:10px;");
            
            Object key = drillReplaceEvent.getNewKey();
            String key2 = key.toString();
            String first = key2.substring(34);
            String[] column = first.split(" ");
            
            for(i=0; i < column.length; i++)
                System.out.println("column value["+i+"] ::"+column[i]);
            
            vhclId = Integer.parseInt(column[0]);
            vhclRegno = column[1];
            try {
                capCc = new Number(column[2]);
            } catch (SQLException e) {
            }
            status = column[3];
            releaseDt = column[5];

            String[] values = null;
    
            OperationBinding opFilter = ADFBeanUtils.getOperationBinding("getVehicleDesc");
            opFilter.getParamsMap().put("itmSrNo", vhclRegno);
            opFilter.execute();
            
            if(opFilter.getResult() != null && opFilter.getResult().toString().length() > 0)
            {
                values = opFilter.getResult().toString().split("-"); 
                vhclDesc = values[0];
                grossWt = new Number(values[1]);
                engineNo = values[2];
                chassisNo = values[3];
                mileageLoad = new Number(values[4]);
                
            }
            
            this.visible = "Y";

            System.out.println("Value of vhclId :: "+vhclId);            
            System.out.println("Value of vhclDesc :: "+vhclDesc);            
            System.out.println("Value of status :: "+status);            
            System.out.println("Value of capCc :: "+capCc);            
            System.out.println("Value of vhclRegNo :: "+vhclRegno);            
            System.out.println("Value of grossWt :: "+grossWt);           
            System.out.println("Value of engineNo :: "+engineNo);           
            System.out.println("Value of chassisNo :: "+chassisNo);           
            System.out.println("Value of mileageLoad :: "+mileageLoad);           
            System.out.println("Value of releaseDt :: "+releaseDt);
            System.out.println("Value of visible :: "+visible);

        } else {
           //  vhclDetailPGBind.setInlineStyle("padding:5px; font-size:11px;");
            //    vhclImageBind.setInlineStyle("width:30px; height:30px; border:solid 2px #999999; margin-bottom:5px;");
            this.visible = "N";
        }

    }

    public void setGrossWt(Number grossWt) {
        this.grossWt = grossWt;
    }

    public Number getGrossWt() {
        return grossWt;
    }

    public void setMileageLoad(Number mileageLoad) {
        this.mileageLoad = mileageLoad;
    }

    public Number getMileageLoad() {
        return mileageLoad;
    }

    public void setChassisNo(String chassisNo) {
        this.chassisNo = chassisNo;
    }

    public String getChassisNo() {
        return chassisNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setCapCc(Number capCc) {
        this.capCc = capCc;
    }

    public Number getCapCc() {
        return capCc;
    }

    public void setReleaseDt(String releaseDt) {
        this.releaseDt = releaseDt;
    }

    public String getReleaseDt() {
        return releaseDt;
    }

    public void setVhclRegno(String vhclRegno) {
        this.vhclRegno = vhclRegno;
    }

    public String getVhclRegno() {
        return vhclRegno;
    }

    public void setVhclDesc(String vhclDesc) {
        this.vhclDesc = vhclDesc;
    }

    public String getVhclDesc() {
        return vhclDesc;
    }

    public void setVhclId(Integer vhclId) {
        this.vhclId = vhclId;
    }

    public Integer getVhclId() {
        return vhclId;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getVisible() {
        return visible;
    }

    public void setVhclStatusTreemapBind(UITreemap vhclStatusTreemapBind) {
        this.vhclStatusTreemapBind = vhclStatusTreemapBind;
    }

    public UITreemap getVhclStatusTreemapBind() {
        return vhclStatusTreemapBind;
    }

    public void setCldIdBind(RichOutputText cldIdBind) {
        this.cldIdBind = cldIdBind;
    }

    public RichOutputText getCldIdBind() {
        return cldIdBind;
    }

    public void setHoOrgIdBind(RichOutputText hoOrgIdBind) {
        this.hoOrgIdBind = hoOrgIdBind;
    }

    public RichOutputText getHoOrgIdBind() {
        return hoOrgIdBind;
    }

    public void setSlocIdBind(RichOutputText slocIdBind) {
        this.slocIdBind = slocIdBind;
    }

    public RichOutputText getSlocIdBind() {
        return slocIdBind;
    }

    public void setStatusIdBind(RichOutputText statusIdBind) {
        this.statusIdBind = statusIdBind;
    }

    public RichOutputText getStatusIdBind() {
        return statusIdBind;
    }

//    public void setVhclDetailPGBind(RichPanelGroupLayout vhclDetailPGBind) {
//        this.vhclDetailPGBind = vhclDetailPGBind;
//    }
//
//    public RichPanelGroupLayout getVhclDetailPGBind() {
//        return vhclDetailPGBind;
//    }

    public void setVhclRegNoBind(RichOutputText vhclRegNoBind) {
        this.vhclRegNoBind = vhclRegNoBind;
    }

    public RichOutputText getVhclRegNoBind() {
        return vhclRegNoBind;
    }

    public void setStatusBind(RichOutputText statusBind) {
        this.statusBind = statusBind;
    }

    public RichOutputText getStatusBind() {
        return statusBind;
    }

    public void setReleaseDtBind(RichOutputText releaseDtBind) {
        this.releaseDtBind = releaseDtBind;
    }

    public RichOutputText getReleaseDtBind() {
        return releaseDtBind;
    }

    public void setCapacityBind(RichOutputText capacityBind) {
        this.capacityBind = capacityBind;
    }

    public RichOutputText getCapacityBind() {
        return capacityBind;
    }

    public void setCategoryBind(RichOutputText categoryBind) {
        this.categoryBind = categoryBind;
    }

    public RichOutputText getCategoryBind() {
        return categoryBind;
    }

    public void setVhclImageBind(RichImage vhclImageBind) {
        this.vhclImageBind = vhclImageBind;
    }

    public RichImage getVhclImageBind() {
        return vhclImageBind;
    }

    public void bubbleSelectionListener(SelectionEvent selectionEvent) {
        System.out.println("Bubble Selection listener called !!");
        ADFBeanUtils.showPopup(bubblePopupBind, true);
        System.out.println("Client Id :" + bubbleNoteBind.getClientId());
        bubbleNoteBind.setParent(bubbleDataItemBind);

    }

    public void scatterSelectionListener(SelectionEvent selectionEvent) {

        System.out.println("Scatter Selection listener called !!");
        ADFBeanUtils.showPopup(bubblePopupBind, true);
    }

    public void setBubbleInfoPopup(RichPopup bubbleInfoPopup) {
        this.bubbleInfoPopup = bubbleInfoPopup;
    }

    public void setBubbleNoteBind(RichNoteWindow bubbleNoteBind) {
        this.bubbleNoteBind = bubbleNoteBind;
    }

    public RichNoteWindow getBubbleNoteBind() {
        return bubbleNoteBind;
    }

    public void setBubblePopupBind(RichPopup bubblePopupBind) {
        this.bubblePopupBind = bubblePopupBind;
    }

    public RichPopup getBubblePopupBind() {
        return bubblePopupBind;
    }

    public void setBubbleDataItemBind(UIDataItem bubbleDataItemBind) {
        this.bubbleDataItemBind = bubbleDataItemBind;
    }

    public UIDataItem getBubbleDataItemBind() {
        return bubbleDataItemBind;
    }

    public void setTreeMapNodeBinding(UITreemapNode treeMapNodeBinding) {
        this.treeMapNodeBinding = treeMapNodeBinding;
    }

    public UITreemapNode getTreeMapNodeBinding() {
        return treeMapNodeBinding;
    }
}
