package mnfcapplanapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import java.util.Calendar;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.faces.bi.component.gantt.TaskbarFormat;
import oracle.adf.view.faces.bi.component.gantt.TaskbarFormatManager;
import oracle.adf.view.faces.bi.component.gantt.UIProjectGantt;
import oracle.adf.view.faces.bi.component.gantt.UIResourceUtilizationGantt;
import oracle.adf.view.faces.bi.component.gantt.UISchedulingGantt;
import oracle.adf.view.faces.bi.event.DataChangeEvent;
import oracle.adf.view.faces.bi.event.DoubleClickEvent;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class GanttChartViewBean {
    private UIProjectGantt ganttChartBindVar;
    private Date _startDate;
    private Date _endDate;
    private UIXSwitcher ganttChartSwitcher;
    private UIResourceUtilizationGantt resourceGanttBind;
    private String background = "Project";
    private UISchedulingGantt scheduleGanttBind;
    private RichPopup scheduleGanttChartPopUp;

    public GanttChartViewBean() {
    }

    /*---------------------------Method for get Binding----------------------------------*/
    OperationBinding ob = null;

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getBackground() {
        return background;
    }

    public void set_startDate(Date startDate) {
        this._startDate = startDate;
    }

    public Date get_startDate() {
        if (_startDate == null) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_YEAR, 1);
            _startDate = cal.getTime();
        }
        return _startDate;
    }

    public void set_endDate(Date endDate) {
        this._endDate = endDate;
    }

    public Date get_endDate() {
        if (_endDate == null) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.add(Calendar.YEAR, 1);
            _endDate = cal.getTime();
        }
        return _endDate;
    }

    public void setGanttChartBindVar(UIProjectGantt ganttChartBindVar) {
        createTaskColor(ganttChartBindVar.getTaskbarFormatManager(), "Normal", "darkblue");
        this.ganttChartBindVar = ganttChartBindVar;
    }

    public UIProjectGantt getGanttChartBindVar() {
        return ganttChartBindVar;
    }


    public void setGanttChartSwitcher(UIXSwitcher ganttChartSwitcher) {
        this.ganttChartSwitcher = ganttChartSwitcher;
    }

    public void setScheduleGanttBind(UISchedulingGantt scheduleGanttBind) {
        this.scheduleGanttBind = scheduleGanttBind;
    }

    public UISchedulingGantt getScheduleGanttBind() {
        return scheduleGanttBind;
    }

    public UIXSwitcher getGanttChartSwitcher() {
        return ganttChartSwitcher;
    }

    public void setResourceGanttBind(UIResourceUtilizationGantt resourceGanttBind) {
        this.resourceGanttBind = resourceGanttBind;
    }

    public UIResourceUtilizationGantt getResourceGanttBind() {
        return resourceGanttBind;
    }

    /***********************************Set color for Task Bars in Project gantt chart*****************************************/
    public static void createTaskColor(TaskbarFormatManager taskbarFormatManager, String taskType, String _colorCode) {
        TaskbarFormat taskbarFormat = null;
        if (taskType.equals("Normal")) {
            taskbarFormat = new TaskbarFormat(taskType, _colorCode, null, _colorCode, 12);
        }
        taskbarFormatManager.registerTaskbarFormat(taskType, taskbarFormat);
    }

    /***********************************Set ToolTips for Task Bars In project gantt chart*****************************************/
    public String[] getTooltipKeys() {
        return new String[] { "TaskLabel", "PlnStrtDate", "PlnEndDate" };
    }

    public String[] getTooltipKeyLabels() {
        return new String[] { "Details : ", "Start Date : ", "End Date : " };
    }

    /***********************************Set ToolTips for Task Bars In Schedule gantt chart*****************************************/
    public String[] getTooltipKeys_S() {
        return new String[] { "StrtTm", "EndTm", "OpDesc" };
    }

    public String[] getTooltipLabels_S() {
        return new String[] { "Start date : ", "End Date : ", "Operation : " };
    }

    /**************************************switcher migration**************************************************/
    public void projectChartSwitcherLink_ACL(ActionEvent actionEvent) {
        this.ganttChartSwitcher.setFacetName("ProjectChart");
        this.setBackground("Project");
    }

    public void resourceChartSwitcherLink_ACL(ActionEvent actionEvent) {
        this.ganttChartSwitcher.setFacetName("ResourceChart");
        this.setBackground("Resource");
    }

    public void scheduleChartSwitcherLink_ACL(ActionEvent actionEvent) {
        this.ganttChartSwitcher.setFacetName("SchedulingChart");
        this.setBackground("Schedule");
    }

    public void ScheduleGanttChartDataChangeEvent(DataChangeEvent evt) {
        if (DataChangeEvent.TIME_CHANGED == evt.getActionType()) {
            String _fromResourceId = evt.getFromResourceId();
            String _toResourceId = evt.getToResourceId();
            String _taskId = evt.getTaskId();
            Date _NewStartdate = evt.getNewStartTime();
            Date _NewEnddate = evt.getNewEndTime();
            System.out.println("1------ " + _fromResourceId);
            System.out.println("2------ " + _toResourceId);
            System.out.println("3------ " + _taskId);
            System.out.println("4------ " + _NewStartdate);
            System.out.println("5------ " + _NewEnddate);
            ob = ADFBeanUtils.findOperation("rescheduleOpeartion_FUNC");
            ob.getParamsMap().put("taskId", _taskId);
            ob.getParamsMap().put("fromResourceId", _fromResourceId);
            ob.getParamsMap().put("toResourceId", _toResourceId);
            ob.getParamsMap().put("newStrtDate", _NewStartdate);
            ob.getParamsMap().put("newEndDate", _NewEnddate);
            ob.execute();
            if (ob.getResult() == 1) {
                AdfFacesContext.getCurrentInstance().addPartialTarget(scheduleGanttBind);
            }
        }
    }

    public void doubleClickEventfor_ScheduleGantt(DoubleClickEvent doubleClickEvent) {
        if (doubleClickEvent != null) {
            String Rnid = doubleClickEvent.getTaskId();
            ob = ADFBeanUtils.findOperation("filterScheduleOperation_Func");
            ob.getParamsMap().put("RnId", Rnid);
            ob.execute();
            showPopup(scheduleGanttChartPopUp, true);
        }
    }

    public void setScheduleGanttChartPopUp(RichPopup scheduleGanttChartPopUp) {
        this.scheduleGanttChartPopUp = scheduleGanttChartPopUp;
    }

    public RichPopup getScheduleGanttChartPopUp() {
        return scheduleGanttChartPopUp;
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

    public void scheduleGanttPopupDialogListner(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("Cancel")) {
            showPopup(scheduleGanttChartPopUp, false);
        }
    }
}
