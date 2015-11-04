package mnfcapplanapp.view.bean;

import adf.utils.bean.ADFBeanUtils;

import java.awt.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichCalendar;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.CalendarActivityEvent;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.event.PopupFetchEvent;
import oracle.adf.view.rich.model.CalendarActivity;
import oracle.adf.view.rich.util.CalendarActivityRamp;
import oracle.adf.view.rich.util.InstanceStyles;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

public class ShopFloorCalendarBean {
    private RichCalendar calender;
    private List _colorData = CalendarActivityRamp.getRampColorKeys();
    private List<CalendarType> _CalendarTypes = new ArrayList<CalendarType>();
    private RichPanelFormLayout createPanelFormLayout;
    private RichPanelFormLayout updatePanelFormLayout;

    /*-----------------------------------------------------------------------------------------------------------------*/

    public ShopFloorCalendarBean() {
        super();
    }

    /*-----------------------------------------------------------------------------------------------------------------*/

    public void setCalender(RichCalendar calender) {
        loadactivityStyles(); //Color activity Calling Function
        this.calender = calender;
    }

    public RichCalendar getCalender() {
        return calender;
    }

    public void setColorData(List _colorData) {
        this._colorData = _colorData;
    }

    public List getColorData() {
        return _colorData;
    }

    public void setCalendarTypes(List<CalendarType> _CalendarTypes) {
        this._CalendarTypes = _CalendarTypes;
    }

    public List<CalendarType> getCalendarTypes() {
        if (_CalendarTypes != null && !_CalendarTypes.isEmpty())
            return _CalendarTypes;
        else {
            _CalendarTypes = getCalendarTypesList();
            return _CalendarTypes;
        }
    }

    public void setCreatePanelFormLayout(RichPanelFormLayout createPanelFormLayout) {
        this.createPanelFormLayout = createPanelFormLayout;
    }

    public RichPanelFormLayout getCreatePanelFormLayout() {
        return createPanelFormLayout;
    }

    public void setUpdatePanelFormLayout(RichPanelFormLayout updatePanelFormLayout) {
        this.updatePanelFormLayout = updatePanelFormLayout;
    }

    public RichPanelFormLayout getUpdatePanelFormLayout() {
        return updatePanelFormLayout;
    }

    /*-----------------------------------------------------------------------------------------------------------------*/

    private HashMap activityStyles = new HashMap<Set<String>, InstanceStyles>();

    public void loadactivityStyles() {
        List<CalendarType> allCalendarType = getCalendarTypes();
        for (int i = 0; i < allCalendarType.size(); i++) {
            CalendarType p = allCalendarType.get(i);
            HashSet CalendarTypeColorSet = new HashSet<String>();
            CalendarTypeColorSet.add(p.id);
            activityStyles.put(CalendarTypeColorSet, CalendarActivityRamp.getActivityRamp(p.colour));
        }
    }

    public void CalendarTypeColorChange(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        loadactivityStyles();
        refreshCalendar();
    }

    public void CalendarTypeEnabledChange(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        String filterCalendarTypesString = getFitleredCalendarTypes();
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("filterCalendarByCalendarTypes_Func");
        operationBinding.getParamsMap().put("filterCalendarTypesString", filterCalendarTypesString);
        Object result = operationBinding.execute();
        if (result != null)
            System.out.println("Calender refreshed successfully!");
        AdfFacesContext.getCurrentInstance().addPartialTarget(calender);
    }

    public void refreshCalendar() {
        AdfFacesContext.getCurrentInstance().addPartialTarget(calender);
    }

    private String getFitleredCalendarTypes() {
        List<CalendarType> allCalendarTypes = _CalendarTypes;
        String filterString = "";
        for (CalendarType p : allCalendarTypes) {
            if (p.enabled) {
                filterString = filterString.equals("") ? (filterString + "" + p.id) : filterString + "," + p.id;
            }
        }
        return filterString;
    }

    public void setActivityStyles(HashMap activityStyles) {
        this.activityStyles = activityStyles;
    }

    public HashMap getActivityStyles() {
        if (activityStyles != null && !activityStyles.isEmpty())
            return activityStyles;
        else {
            //loadactivityStyles();
            return activityStyles;
        }
    }

    public List<CalendarType> getCalendarTypesList() {
        List<CalendarType> CalendarTypes = new ArrayList<CalendarType>();
        //activityStyles = new HashMap<Set<String>, InstanceStyles>();
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcIteratorBindings = bindings.findIteratorBinding("LOVCalEntryType1Iterator");

        // Get all the rows of a iterator
        Row[] rows = dcIteratorBindings.getAllRowsInRange();
        int i = 0;
        for (Row row : rows) {
            CalendarType calendarType;
            Integer AttId = (Integer) row.getAttribute("AttId");
            String stringAttid = AttId.toString();
            String AttNm = (String) row.getAttribute("AttNm");
            Boolean calendarTypeEnabled = true;
            calendarType = new CalendarType(stringAttid, AttNm, calendarTypeEnabled, (Color) _colorData.get(i));
            CalendarTypes.add(calendarType);

            //Append activity styles by provider
            HashSet calendarTypeColorSet = new HashSet<String>();
            calendarTypeColorSet.add(AttId);
            activityStyles.put(calendarTypeColorSet, CalendarActivityRamp.getActivityRamp((Color) _colorData.get(i)));

            i++;
        }
        return CalendarTypes;
    }

    /*----------------------Method for get Binding------------------*/
    OperationBinding ob = null;

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void calendarActivityListener(CalendarActivityEvent calendarActivityEvent) {
        CalendarActivity activity = calendarActivityEvent.getCalendarActivity();
        if (activity != null) {
            String id = activity.getId();
            ob = ADFBeanUtils.findOperation("getCalendarCurrentRow_Func");
            ob.getParamsMap().put("activityId", id);
            ob.execute();
        }
    }

    public void createPopupListener(PopupFetchEvent popupFetchEvent) {
        DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        Object result = operationBinding.execute();
    }

    public void createPopupDialogueListener(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
        } else {
            DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();
        }
        //TODO: Re-execute  calendar VO to avoid any errors?
        refreshCalendar();
    }

    public void updatePopupDialogueListener(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
        } else {
            DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();
        }
        refreshCalendar();
    }

    public void deleteActivityDialogueListener(DialogEvent dialogEvent) {
        Outcome outcome = dialogEvent.getOutcome();
        if (outcome == Outcome.ok) {
            DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("Delete");
            operationBinding.execute();
            operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
        } else {
            //Nothing to do
        }
        refreshCalendar();
    }

}
