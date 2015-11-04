package mnfcapplanapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.model.ADFModelUtils;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ValueExpression;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.share.ADFContext;
import oracle.adf.view.faces.bi.component.gantt.TaskbarFormat;
import oracle.adf.view.faces.bi.component.gantt.TaskbarFormatManager;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class AddEditPlanBean {
    private UIXSwitcher capacityPlanSwitcher;
    private String wfId = null;
    private String _EDIT_MODE = null;
    private RichPopup viewJobcardPopUpBind;

    public AddEditPlanBean() {
    }

    /*---------------------------Method for get Binding----------------------------------*/
    OperationBinding ob = null;

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /*----------------------------Resolve Expression for PafeFlowScope----------------------*/
    public Object resolvEl(String data) {
        ADFContext adfCtx = ADFContext.getCurrent();
        ELContext elContext = adfCtx.getELContext();
        ValueExpression valueExp = adfCtx.getExpressionFactory().createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext);
        return Message;
    }

    public void setEDIT_MODE(String _EDIT_MODE) {
        this._EDIT_MODE = _EDIT_MODE;
    }

    public String getEDIT_MODE() {
        return _EDIT_MODE;
    }

    public void setCapacityPlanSwitcher(UIXSwitcher capacityPlanSwitcher) {
        this.capacityPlanSwitcher = capacityPlanSwitcher;
    }

    public UIXSwitcher getCapacityPlanSwitcher() {
        return capacityPlanSwitcher;
    }

    public void setWfId(String wfId) {
        this.wfId = wfId;
    }

    public String getWfId() {
        return wfId;
    }

    /*----------------------------Get pageFlowScope Page Mode Value----------------------------------*/
    public String getPlanPageMode() {
        return resolvEl("#{pageFlowScope.PLAN_PAGE_MODE}").toString();
    }

    /*-------------------Switcher First Facet Next Operation Button Action Event--------------------*/
    public void switcherStep1Next(ActionEvent actionEvent) {
        this.capacityPlanSwitcher.setFacetName("OperationDetails");
        if ("C".equals(getPlanPageMode()) && !("E".equals(this.getEDIT_MODE()))) {
            ADFBeanUtils.findOperation("insertIntoMnfCapPlnSrc_Func").execute();
        }
    }

    /*--------------------Switcher Second Facet Back Operation Button Action Event------------------*/
    public void switcherStep2Back(ActionEvent actionEvent) {
        this.capacityPlanSwitcher.setFacetName("PdoGppView");
        ADFBeanUtils.findOperation("autoSelectPdoMppInViewMode_Func").execute();
    }

    /*---------------------------Select All PDO Mpp ---------------------------------------*/
    public void selectAllACE(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("selectAllPdo").execute();
    }

    /*--------------------------------Unselect All PDO MPP----------------------------------------*/
    public void deselectAllACE(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("unselectAllPdo").execute();
    }

    /*-------------------------------Select Operation Value Change Listner-------------------------*/
    public void selectOperationVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            ob = ADFBeanUtils.findOperation("insertIntoMnfSchedule_Func");
            ob.getParamsMap().put("checkVal", valueChangeEvent.getNewValue().toString());
            ob.execute();
        }
    }

    /*--------------------Edit Action----------------------------------------*/
    public void editBtnAction(ActionEvent actionEvent) {
        Integer usr_Id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        Integer pending = 0;
        ob = ADFBeanUtils.findOperation("getDocUsrFromWF");
        ob.execute();
        Integer chkUsr = (Integer) ob.getResult();
        if (chkUsr != null) {
            pending = chkUsr;
        }
        if (pending.compareTo(usr_Id) == 0) {
            this.setEDIT_MODE("E");
            changePageMode_Create();
            ADFBeanUtils.findOperation("autoSelectPdoMppInViewMode_Func").execute();
        } else if (pending.compareTo(new Integer(-1)) == 0) {
            StringBuilder msg = new StringBuilder();
            msg.append("<p>Capacity Plan has been Approved, You can not edit Plan.</p>");
            ADFModelUtils.showFormattedFacesMessage("Capacity Plan", msg.toString(), FacesMessage.SEVERITY_INFO);
        } else {
            ob = ADFBeanUtils.findOperation("getUserName");
            ob.getParamsMap().put("u_Id", pending);
            ob.getParamsMap().put("slc_id", sloc_id);
            ob.execute();
            if (ob.getResult() != null) {
                StringBuilder mssg = new StringBuilder();
                mssg.append("<p>This Capacity Plan Document is pending at other user<b><i> [ " +
                            ob.getResult().toString() + "] </i></b>for approval, You can not edit this.</p>");
                ADFModelUtils.showFormattedFacesMessage("Capacity Plan", mssg.toString(), FacesMessage.SEVERITY_INFO);
            }
        }
    }

    /*--------------------Save Action--------------------------------------*/
    public void saveBtnAction(ActionEvent actionEvent) {
        commitOperation_Func();
    }


    /*--------------------Save and Forward Action----------------------------------*/
    public String saveAndForwardAction() {
        commitOperation_Func();
        generateJobCard_Func();
        return "Work_Flow";
    }

    /*-------------------------------------Commit Operation----------------------------------*/
    public void commitOperation_Func() {
        ob = ADFBeanUtils.findOperation("Commit");
        Object execute = ob.execute();
        if (execute == null) {
            changePageMode_View();
            StringBuilder message = new StringBuilder();
            message.append("<P>Capacity Plan Saved Successfully.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Capacity Plan", message.toString(), FacesMessage.SEVERITY_INFO);
        }
        System.out.println("Hii");
        callFuncWf();
        ADFBeanUtils.findOperation("Commit").execute();
    }

    /*------------------------------------Generate Job Card----------------------------*/
    public void generateJobCard_Func() {
        ADFBeanUtils.findOperation("generateJobCard_Func").execute();
    }

    public void changePageMode_Create() {
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("PLAN_PAGE_MODE", "C");
    }

    public void changePageMode_View() {
        Map pageFlowScope = RequestContext.getCurrentInstance().getPageFlowScope();
        pageFlowScope.put("PLAN_PAGE_MODE", "V");
    }

    /*----------------------Call Function callWfFunction----------------------*/
    public void callFuncWf() {
        callWfId();
        ob = ADFBeanUtils.findOperation("callWfFunctions");
        ob.execute();
    }

    /*---------------------Call callWfId Function------------------------------*/
    public void callWfId() {
        ob = ADFBeanUtils.findOperation("getWfId");
        ob.execute();
        String wId = (String) ob.getResult();
        setWfId(wId);
    }

    /*------------------------Select All OPeration --------------------------*/
    public void checkAllOperationACL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("selectAllOperation_Func").execute();
    }

    /*-----------------------UnSelect All Operation-----------------------------*/
    public void unCheckAllOperationACL(ActionEvent actionEvent) {
        ADFBeanUtils.findOperation("unselectAllOperation_Func").execute();
    }

    /**
     * Method to open a popup using binding.
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

    /*----------------------Generate Schedule by Algo-------------------------*/
    public void generate_scheduleByAlgo_ACL(ActionEvent actionEvent) {
        ob = ADFBeanUtils.findOperation("generateScheduleAlgo_Func");
        ob.execute();
        Integer retVal = (Integer) ob.getResult();
        if (retVal.compareTo(new Integer(1)) == 0) {
            StringBuilder message = new StringBuilder();
            message.append("<P>Scheduling Generated Successfully.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Capacity Plan", message.toString(), FacesMessage.SEVERITY_INFO);
        }
        if (retVal.compareTo(new Integer(-1)) == 0) {
            StringBuilder message = new StringBuilder();
            message.append("<P>Sorry.!! Error Found.</P><BR>");
            ADFModelUtils.showFormattedFacesMessage("Capacity Plan", message.toString(), FacesMessage.SEVERITY_INFO);
        }
    }

    /***********************************Set color for Task Bars*****************************************/
    public static void createTaskColor(TaskbarFormatManager taskbarFormatManager, String taskType) {
        TaskbarFormat taskbarFormat = null;
        if (taskType.equals("Normal")) {
            taskbarFormat = new TaskbarFormat(taskType, "#90EE90", null, "#90EE90", 14);
        }
        taskbarFormatManager.registerTaskbarFormat(taskType, taskbarFormat);
    }

    /***********************************Set ToolTips for Task Bars*****************************************/
    public String[] getTooltipKeys() {
        return new String[] { "TaskLabel", "PlnStrtDate", "PlnEndDate" };
    }

    public String[] getTooltipKeyLabels() {
        return new String[] { "Details : ", "Start Date : ", "End Date : " };
    }

    /*---------------------------view Job Cards-------------------------------------------*/
    public void viewJobCard_ACL(ActionEvent actionEvent) {
        showPopup(viewJobcardPopUpBind, true);
    }

    public void setViewJobcardPopUpBind(RichPopup viewJobcardPopUpBind) {
        this.viewJobcardPopUpBind = viewJobcardPopUpBind;
    }

    public RichPopup getViewJobcardPopUpBind() {
        return viewJobcardPopUpBind;
    }

}
