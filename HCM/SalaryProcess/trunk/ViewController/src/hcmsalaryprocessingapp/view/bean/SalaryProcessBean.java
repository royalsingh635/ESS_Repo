package hcmsalaryprocessingapp.view.bean;
/** Please Don't alter the contents of this class without permission of project manager!!
 * All comments by-Manish Kumar(Developer Of this Application)*/

import adf.utils.bean.ADFBeanUtils;

import adf.utils.model.ADFModelUtils;

import java.io.IOException;

import java.io.InputStream;

import java.io.OutputStream;

import java.math.BigDecimal;


import java.sql.SQLException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import java.util.List;
import java.util.Map;
import java.util.Set;


import java.util.regex.Matcher;

import java.util.regex.Pattern;

import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichCalendar;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.CalendarEvent;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;


import oracle.adf.view.rich.event.PopupFetchEvent;
import oracle.adf.view.rich.util.CalendarActivityRamp;
import oracle.adf.view.rich.util.InstanceStyles;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;

import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.domain.Number;

import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import oracle.jbo.uicli.binding.JUCtrlListBinding;
import oracle.jbo.uicli.binding.JUCtrlValueBindingRef;

import oracle.sql.DATE;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFSimpleShape;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.Comment;

import org.apache.poi.ss.usermodel.RichTextString;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import org.omg.CORBA.DynAnyPackage.Invalid;

public class SalaryProcessBean
{
    private RichInputListOfValues critariaValueBinding;
    private RichInputDate startDtBinding;
    private RichInputDate endDtBinding;
    private String mode = "V";
    private RichPopup updatePopBinding;
    private Row currRow = null;
    private RichCalendar calendarBinding;
    private Timestamp dt = null;
    private RichSelectOneChoice leaveOnPopBinding;
    private RichInputText extraTimeOnPopBinding;
    private Date actvDt = null;
    private HashMap activityStyles = new HashMap<Set<String>, InstanceStyles>();
    private RichInputText inTimeBinding;
    private RichInputText outTimeBinding;
    private RichPopup uploadPopupBinding;
    InputStream inputStream;
    private String chk = "N";
    private RichSelectOneChoice lwpChkBinding;
    private RichSelectBooleanCheckbox wkOffChkBinding;
    private RichSelectBooleanCheckbox halfDayLeaveChkBinding;
    private RichInputDate attDtBinding;
    private RichSelectOneChoice dedChBinding;
    private RichInputText xtraTmeBinding;
    private RichSelectOneChoice leaveidBinding;
    private RichInputText inTimeFrMultAttBinding;
    private RichInputText outTimeFrMultAttBinding;
    private RichInputText empDocIdBinding;
    private String ADD_EDIT_MODE = "D";
    private String delMode = "F";
    private String mdeFrValidator = "F";
    private String multiAttSwitchMode = "single";
    private RichInputDate dummyFromDateBinding;
    private RichInputDate dummyToDtBinding;
    private RichInputText dummyNumBerOfDayBinding;
    private RichInputText dummyInTimeBinding;
    private RichInputText dummyOutTimeBinding;
    private RichSelectOneChoice dummyDedchBinding;
    private RichSelectOneChoice dummyLeaveIdBinding;
    private RichSelectBooleanCheckbox dummyWeekOfChk;
    private RichSelectBooleanCheckbox dummyHalfDayBinding;
    private RichInputText dummyExtraTimeBinding;
    private RichInputText dummyEmpDocIdBinding;
    private RichOutputText switchingModeBinding;
    private String calMode = "Y";
    private RichSelectBooleanCheckbox qtrLeaveChkBinding;
    private RichSelectBooleanCheckbox qtrLeaveChkonMultiBinding;
    private RichSelectOneChoice compOffBinding;
    private RichInputDate attDateRangeBinding;
    private RichInputDate attDateRangeEndBinding;


    public void setCalMode(String calMode)
    {
        this.calMode = calMode;
    }

    public String getCalMode()
    {
        return calMode;
    }

    public void setMultiAttSwitchMode(String multiAttSwitchMode)
    {
        this.multiAttSwitchMode = multiAttSwitchMode;
    }

    public String getMultiAttSwitchMode()
    {
        return multiAttSwitchMode;
    }

    public void setMdeFrValidator(String mdeFrValidator)
    {
        this.mdeFrValidator = mdeFrValidator;
    }

    public String getMdeFrValidator()
    {
        return mdeFrValidator;
    }
    private RichInputText employeeDocIdBinding;

    public void setDelMode(String delMode)
    {
        this.delMode = delMode;
    }

    public String getDelMode()
    {
        return delMode;
    }
    private RichSelectBooleanCheckbox weekOffFrMultBinding;
    private RichSelectBooleanCheckbox halfDyLeaveBinding;

    private RichSelectOneChoice subSalIdBinding;
    private RichInputText subSalAmtBinding;
    private RichInputText totlAmtForTempComBinding;
    private RichSelectOneChoice salIdForTempComBinding;
    private RichInputText amtforTempComBinding;
    private RichPopup multiTempCompPopBinding;
    private RichInputText isOthrSubComBinding;

    public void setADD_EDIT_MODE(String ADD_EDIT_MODE)
    {
        this.ADD_EDIT_MODE = ADD_EDIT_MODE;
    }

    public String getADD_EDIT_MODE()
    {
        return ADD_EDIT_MODE;
    }


    public void setChk(String chk)
    {
        this.chk = chk;
    }

    public String getChk()
    {
        return chk;
    }
    UploadedFile file = null;

    private Pattern pattern;
    private Matcher matcher;
    private static final String TIME24HOURS_PATTERN = "([0-1][0-9]|2[0-3]):[0-5][0-9]";
    private RichSelectOneChoice crtBinding;
    private RichSelectOneChoice frequencyBinding;
    private RichInputText freqDaysBinding;
    private String txnDocId = null;

    public void setTxnDocId(String txnDocId)
    {
        this.txnDocId = txnDocId;
    }

    public String getTxnDocId()
    {
        return txnDocId;
    }

    public void setFile(UploadedFile file)
    {
        this.file = file;
    }

    public UploadedFile getFile()
    {
        return file;
    }


    public void setActivityStyles(HashMap activityStyles)
    {
        this.activityStyles = activityStyles;
    }

    public Object resolvElDCMsg(String data)
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public HashMap getActivityStyles()
    {
        try
        {
            HashSet event1 = new HashSet<String>();
            HashSet event2 = new HashSet<String>();
            HashSet event3 = new HashSet<String>();
            HashSet event4 = new HashSet<String>();
            HashSet event5 = new HashSet<String>();
            event1.add("NH"); //Tag Value
            event2.add("P"); //Tag Value
            event3.add("WH"); //Tag Value
            event4.add("PH");
            event5.add("L");
            activityStyles.put(event1, CalendarActivityRamp.getActivityRamp(CalendarActivityRamp.RampKey.PLUM));
            activityStyles.put(event2, CalendarActivityRamp.getActivityRamp(CalendarActivityRamp.RampKey.GREEN));
            activityStyles.put(event3, CalendarActivityRamp.getActivityRamp(CalendarActivityRamp.RampKey.ORANGE));
            activityStyles.put(event4, CalendarActivityRamp.getActivityRamp(CalendarActivityRamp.RampKey.LAVENDAR));
            activityStyles.put(event5, CalendarActivityRamp.getActivityRamp(CalendarActivityRamp.RampKey.RED));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return activityStyles;
    }


    public void setActvDt(Date actvDt)
    {
        this.actvDt = actvDt;
    }

    public Date getActvDt()
    {
        return actvDt;
    }


    public void setMode(String mode)
    {
        this.mode = mode;
    }

    public String getMode()
    {
        return mode;
    }

    public SalaryProcessBean()
    {
    }


    /*  public void enterNewAL(ActionEvent actionEvent) {
        if (critariaValueBinding.getValue() != null && critariaValueBinding.getValue().toString().length() > 0) {
            if (startDtBinding.getValue() != null && startDtBinding.getValue().toString().length() > 0) {
                if (endDtBinding.getValue() != null && endDtBinding.getValue().toString().length() > 0) {
                    ADFBeanUtils.getOperationBinding("setBindVarInMasterVO").execute();
                    OperationBinding binding = ADFBeanUtils.getOperationBinding("filterHeaderVo");
                    binding.execute();
                    if (binding.getErrors().isEmpty() && binding.getResult() != null) {
                        String rslt = binding.getResult().toString();
                        System.out.println("result is--" + rslt);
                        if (rslt.equalsIgnoreCase("N")) {
                            ADFBeanUtils.getOperationBinding("enterNewInHeader").execute();
                            this.mode = "A";
                        } else {
                            FacesMessage message =
                                new FacesMessage("Record already exists for the selected criteria, please try with edit existing!!");
                            message.setSeverity(FacesMessage.SEVERITY_WARN);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }
                    }

                } else {

                    FacesMessage message = new FacesMessage("To date is mandatory.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            } else {
                FacesMessage message = new FacesMessage("From date is mandatory.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(startDtBinding.getClientId(), message);
            }

        } else {
            FacesMessage message = new FacesMessage("Please make a selection.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(critariaValueBinding.getClientId(), message);
        }
        System.out.println("mode in enterNew is-" + mode);
    }   */

    public void editExistingAL(ActionEvent actionEvent)
    {
        if (crtBinding.getValue() != null && crtBinding.getValue().toString().length() > 0)
        {
            if (critariaValueBinding.getValue() != null && critariaValueBinding.getValue().toString().length() > 0)
            {
                if (startDtBinding.getValue() != null && startDtBinding.getValue().toString().length() > 0)
                {
                    if (endDtBinding.getValue() != null && endDtBinding.getValue().toString().length() > 0)
                    {
                        //ADFBeanUtils.getOperationBinding("setBindVarInMasterVO").execute();
                        //OperationBinding binding = ADFBeanUtils.getOperationBinding("filterHeaderVo");
                        //binding.execute();
                        /* if (binding.getErrors().isEmpty() && binding.getResult() != null) {
                        String rslt = binding.getResult().toString();
                        System.out.println("result for edit--" + rslt);
                        if (rslt.equalsIgnoreCase("Y")) {*/
                        ADFBeanUtils.getOperationBinding("filterEmp").execute();
                        //findIterator.getCurrent().getCurrentBindingsEntry()
                        DCBindingContainer dcBindings = ADFBeanUtils.getDCBindingContainer();
                        DCIteratorBinding empItr = (DCIteratorBinding) dcBindings.get("Emp2Iterator");
                        if (empItr.getCurrentRow() != null)
                        {
                            Row row = empItr.getCurrentRow();
                            currRow = row;
                            String docId = row.getAttribute("DocId").toString();
                            System.out.println("emp doc id at search--" + docId);
                            Timestamp frmdt = (Timestamp) startDtBinding.getValue();
                            this.calMode = "Y";
                            // Timestamp doj = (Timestamp) row.getAttribute("EmpDoj");
                            // System.out.println("doj is--" + doj);
                            // System.out.println("comparing " + doj.compareTo(frmdt));
                            // if (doj.compareTo(frmdt) == 0 || doj.compareTo(frmdt) == -1) {
                            OperationBinding op = ADFBeanUtils.getOperationBinding("filterCalOnEmpTblSelection");
                            op.getParamsMap().put("docId", docId);
                            op.execute();
                            if (op.getErrors().isEmpty() && op.getResult() != null)
                            {
                                BigDecimal rslt = (BigDecimal) op.getResult();
                                System.out.println("calendar data result in bean---" + rslt);
                                String result = rslt.toString();
                                if (result.equalsIgnoreCase("0"))
                                {
                                    this.calMode = "N";
                                    //  FacesMessage message =
                                    //    new FacesMessage("Calendar can not be displayed. Please check data for selected employee!!");
                                    FacesMessage message =
                                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.1460']}").toString());
                                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message);
                                }
                                else if (result.equalsIgnoreCase("-2"))
                                {
                                    this.calMode = "N";
                                    // FacesMessage message =
                                    //new FacesMessage("Calendar is being used by another user. Please Try after some time!!");
                                    FacesMessage message =
                                        new FacesMessage(resolvElDCMsg("#{bundle['MSG.1461']}").toString());
                                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message);
                                }
                            }
                            actvDt = new java.util.Date(frmdt.getTime());
                            System.out.println("Active Day is -" + actvDt);
                            /* } else if (doj.compareTo(frmdt) == 1) {
                                StringBuilder msg = new StringBuilder();
                                msg.append("From Date is less than Date Of Joining (");
                                try {
                                    msg.append(doj.dateValue());
                                } catch (SQLException e) {
                                }
                                msg.append(") for the selected employee.");
                                FacesMessage message = new FacesMessage(msg.toString());
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            }*/
                            refreshCalendar();
                        }

                        this.mode = "E";
                        /*} else {
                            FacesMessage message = new FacesMessage("Records not found for selected criteria.");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }
                    }*/
                    }
                    else
                    {
                        //FacesMessage message = new FacesMessage("To date is mandatory.");
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['LBL.3615']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                }
                else
                {
                    //FacesMessage message = new FacesMessage("From date is mandatory.");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1462']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(startDtBinding.getClientId(), message);
                }

            }
            else
            {
                // FacesMessage message = new FacesMessage("Please make a selection.");
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1463']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(critariaValueBinding.getClientId(), message);
            }

        }
        else
        {
            //FacesMessage message = new FacesMessage("Criteria is mandatory.");
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1464']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(crtBinding.getClientId(), message);
        }
        System.out.println("mode in edit is-" + mode);
    }

    public void setCritariaValueBinding(RichInputListOfValues critariaValueBinding)
    {
        this.critariaValueBinding = critariaValueBinding;
    }

    public RichInputListOfValues getCritariaValueBinding()
    {
        return critariaValueBinding;
    }

    public void setStartDtBinding(RichInputDate startDtBinding)
    {
        this.startDtBinding = startDtBinding;
    }

    public RichInputDate getStartDtBinding()
    {
        return startDtBinding;
    }

    public void setEndDtBinding(RichInputDate endDtBinding)
    {
        this.endDtBinding = endDtBinding;
    }

    public RichInputDate getEndDtBinding()
    {
        return endDtBinding;
    }

    public void doneBtnAL(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("Commit").execute();
        ADFBeanUtils.getOperationBinding("resetCalendarData").execute();
        refreshCalendar();
        //FacesMessage message = new FacesMessage("Record saved successfully.");
        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.91']}").toString());
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message);
        this.mode = "V";
    }

    public void cancelBtnAL(ActionEvent actionEvent)
    {
        System.out.println("mode at cancel-----" + mode);
        if (this.mode.equalsIgnoreCase("E"))
        {
            System.out.println("before rollback------" + mode);
            ADFBeanUtils.getOperationBinding("Rollback").execute();
        }
        ADFBeanUtils.getOperationBinding("resetCalendarData").execute();
        ADFBeanUtils.getOperationBinding("resetFilterEmp").execute();
        currRow = null;
        refreshCalendar();
        this.mode = "V";
    }

    public void empTblSelectionListener(SelectionEvent selectionEvent)
    {
        String docId = null;
        invokeEL("#{bindings.Emp2.collectionModel.makeCurrent}", new Class[]
        {
            SelectionEvent.class
        }, new Object[]
        {
            selectionEvent
        });
        Row selectedRow = (Row) evaluateEL("#{bindings.Emp2Iterator.currentRow}");
        if (selectedRow.getAttribute("DocId") != null && selectedRow.getAttribute("DocId").toString().length() > 0)
        {
            docId = selectedRow.getAttribute("DocId").toString();
            Timestamp doj = (Timestamp) selectedRow.getAttribute("EmpDoj");
            Timestamp frmdt = (Timestamp) startDtBinding.getValue();
            this.calMode = "Y";
            currRow = selectedRow;
            System.out.println("doc Id of selected row is-->>>> " + docId);
            // if (doj.compareTo(frmdt) == 0 || doj.compareTo(frmdt) == -1) {
            OperationBinding op = ADFBeanUtils.getOperationBinding("filterCalOnEmpTblSelection");
            op.getParamsMap().put("docId", docId);
            op.execute();
            if (op.getErrors().isEmpty() && op.getResult() != null)
            {
                BigDecimal rslt = (BigDecimal) op.getResult();
                String result = rslt.toString();
                if (result.equalsIgnoreCase("0"))
                {
                    this.calMode = "N";
                    //FacesMessage message =
                    //new FacesMessage("Calendar can not be displayed. Please check data for selected employee!!");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1460']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
                else if (result.equalsIgnoreCase("-2"))
                {
                    this.calMode = "N";
                    //FacesMessage message =
                    //new FacesMessage("Calendar is being used by another user. Please Try after some time!!");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1461']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }

                actvDt = new java.util.Date(frmdt.getTime());
                System.out.println("Active Day is -" + actvDt);
                refreshCalendar();
                this.mode = "S";
                System.out.println("mode value at selection --" + mode);
            }
            /* } else if (doj.compareTo(frmdt) == 1) {
                StringBuilder msg = new StringBuilder();
                msg.append("From Date is less than Date Of Joining (");
                try {
                    msg.append(doj.dateValue());
                } catch (SQLException e) {
                }
                msg.append(") for the selected employee.");
                FacesMessage message = new FacesMessage(msg.toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }*/
        }
    }

    public static Object evaluateEL(String el)
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);

        return exp.getValue(elContext);
    }

    public static Object invokeEL(String el)
    {
        return invokeEL(el, new Class[0], new Object[0]);
    }

    public static Object invokeEL(String el, Class[] paramTypes, Object[] params)
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        MethodExpression exp = expressionFactory.createMethodExpression(elContext, el, Object.class, paramTypes);

        return exp.invoke(elContext, params);
    }


    public void updateMenuItemAL(ActionEvent actionEvent)
    {
        System.out.println("inside menu al");
        System.out.println("date is--" + dt);
        UIComponent comp = actionEvent.getComponent();
        oracle.adf.view.rich.util.ResetUtils.reset(comp);

        if (currRow != null && currRow.toString().length() > 0)
        {
            System.out.println("currentRow is --" + currRow);
            OperationBinding binding = ADFBeanUtils.getOperationBinding("checkSkipAttenForEmp");
            binding.execute();
            if (binding.getErrors().isEmpty() && binding.getResult() != null)
            {
                String chkSkip = binding.getResult().toString();
                System.out.println("skip atten chk in bean before pop--" + chkSkip);
                if (chkSkip.equalsIgnoreCase("Y"))
                {
                    //FacesMessage message =
                    //new FacesMessage("Attendance for the selected employee Or employee group is marked as skipped!");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1465']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
                else
                {
                    Timestamp doj = (Timestamp) currRow.getAttribute("EmpDoj");
                    OperationBinding ob = ADFBeanUtils.getOperationBinding("chkBeforePopOpen");
                    ob.getParamsMap().put("selectedDate", dt);
                    ob.execute();
                    if (ob.getErrors().isEmpty() && ob.getResult() != null)
                    {

                        String rslt = ob.getResult().toString();
                        System.out.println("is row exists---" + rslt);
                        if (rslt.equalsIgnoreCase("Y"))
                        {
                            // execute detail vo after filtering by att date and empdocId
                        }
                        else
                        {
                            ADFBeanUtils.getOperationBinding("CreateInsert").execute();
                            OperationBinding op = ADFBeanUtils.getOperationBinding("editEmpCalendar");
                            op.getParamsMap().put("empCurrentRo", currRow);
                            op.getParamsMap().put("attenDt", dt);
                            op.execute();

                        }
                    }
                    AdfFacesContext.getCurrentInstance().addPartialTarget(updatePopBinding);
                    Date dj = null;
                    Date sdt = null;
                    Date endDt = null;
                    try
                    {
                        dj = doj.dateValue();
                        // sdt = ((Timestamp) startDtBinding.getValue()).dateValue();
                        sdt = dt.dateValue();
                        System.out.println("doj is--" + dj + "  clicked date--" + sdt);
                        // endDt = ((Timestamp) endDtBinding.getValue()).dateValue();

                    }
                    catch (SQLException e) {
                    }

                    if (sdt.equals(dj) || sdt.after(dj))
                    {
                        if (this.calMode.equalsIgnoreCase("Y"))
                        {
                            showPopup(updatePopBinding, true);
                        }
                        else
                        {
                            FacesMessage message =
                                new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2756")); //Can not edit record for this employee, Please check shift details of the employee!
                            message.setSeverity(FacesMessage.SEVERITY_WARN);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }
                    }
                    else
                    {
                        //FacesMessage message = new FacesMessage("Can not edit attendance for this date!");
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1466']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                }
            }
        }
        oracle.adf.view.rich.util.ResetUtils.reset(comp);
    }

    private void showPopup(RichPopup pop, boolean visible)
    {
        try
        {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null)
            {
                String popupId = pop.getClientId(context);
                if (popupId != null)
                {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible)
                    {
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                    }
                    else
                    {
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setUpdatePopBinding(RichPopup updatePopBinding)
    {
        this.updatePopBinding = updatePopBinding;
    }

    public RichPopup getUpdatePopBinding()
    {
        return updatePopBinding;
    }

    public void calendarListener(CalendarEvent calendarEvent)
    {

        dt = new Timestamp(calendarEvent.getTriggerDate());
        System.out.println("emp curent row is--" + currRow);
        System.out.println("inside cal listener");
        /*  if (currRow != null && currRow.toString().length() > 0) {
            OperationBinding ob = ADFBeanUtils.getOperationBinding("chkBeforePopOpen");
            System.out.println("date sent for filter--" + dt);
            ob.getParamsMap().put("selectedDate", dt);
            ob.execute();
            if (ob.getErrors().isEmpty() && ob.getResult() != null) {
                String rslt = ob.getResult().toString();
                System.out.println("is row exists---" + rslt);
                if (rslt.equalsIgnoreCase("Y")) {
                    // execute detail vo after filtering by att date and empdocId
                } else {
                    ADFBeanUtils.getOperationBinding("CreateInsert").execute();
                    OperationBinding op = ADFBeanUtils.getOperationBinding("editEmpCalendar");
                    op.getParamsMap().put("empCurrentRo", currRow);
                    op.getParamsMap().put("attenDt", dt);
                    op.execute();
                }
            }
            //showPopup(updatePopBinding, true);
        }*/
    }

    public void setCalendarBinding(RichCalendar calendarBinding)
    {
        this.calendarBinding = calendarBinding;
    }

    public RichCalendar getCalendarBinding()
    {
        return calendarBinding;
    }

    public void updatePopCancelListener(PopupCanceledEvent popupCanceledEvent)
    {
        System.out.println("inside cancel pop up");
        ADFBeanUtils.getOperationBinding("chkOnCancelPop").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(inTimeBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(outTimeBinding);
    }

    public void refreshCalendarBtnAL(ActionEvent actionEvent)
    {
        refreshCalendar();
    }

    public void refreshCalendar()
    {
        AdfFacesContext.getCurrentInstance().addPartialTarget(calendarBinding);
    }

    public void updateDialogListener(DialogEvent dialogEvent)
    {

        if (dialogEvent.getOutcome().name().toString().equalsIgnoreCase("OK"))
        {
            System.out.println("chk box lwp----" + lwpChkBinding.getValue());
            if (lwpChkBinding.getValue() != null && lwpChkBinding.getValue().toString().length() > 0)
            {
                System.out.println("lwp selected---->" + lwpChkBinding.getValue());
            }
            else if (wkOffChkBinding.getValue().equals(true))
            {
                System.out.println("week off checked--" + wkOffChkBinding.getValue());
            }
            else if (compOffBinding.getValue() != null && compOffBinding.getValue().toString().length() > 0)
            {
                System.out.println("comp off selected---" + compOffBinding.getValue());
            }
            else if (lwpChkBinding.getValue() == null || lwpChkBinding.getValue().toString().length() <= 0 ||
                     wkOffChkBinding.getValue().equals(false) || compOffBinding.getValue() == null)
            {
                System.out.println("week off chk value--" + wkOffChkBinding.getValue());
                System.out.println("intime --" + inTimeBinding.getValue());
                System.out.println("outtime --" + outTimeBinding.getValue());
                System.out.println("extratime on pop--" + extraTimeOnPopBinding.getValue());
                if ((leaveOnPopBinding.getValue() == null || leaveOnPopBinding.getValue().toString().length() <= 0) &&
                    (extraTimeOnPopBinding.getValue() == null || extraTimeOnPopBinding.getValue().equals(" ")) ||
                    wkOffChkBinding.getValue() == false)
                {

                    //  FacesMessage message = new FacesMessage("Please select leave or enter In and Out Time!");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1467']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
                else
                {
                    System.out.println("leave Id in validator----->" + leaveOnPopBinding.getValue());
                    OperationBinding op = ADFBeanUtils.getOperationBinding("chkLeaveBalOnPop");
                    op.getParamsMap().put("leaveId", leaveOnPopBinding.getValue());
                    op.getParamsMap().put("empDoc", currRow.getAttribute("DocId"));
                    op.execute();
                    if (op.getErrors().isEmpty() && op.getResult() != null)
                    {
                        String rslt = op.getResult().toString();
                        if (rslt.equalsIgnoreCase("Y"))
                        {
                            String msg =
                                ADFModelUtils.resolvRsrc("MSG.2758"); //Balance for the selected leave is not sufficient!!
                            FacesMessage message = new FacesMessage(msg);
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(leaveOnPopBinding.getClientId(), message);
                        }
                    }
                    System.out.println("inside ok dialog listener");
                    ADFBeanUtils.getOperationBinding("setInOutTime").execute();
                }
            }
        }
    }


    public void setLeaveOnPopBinding(RichSelectOneChoice leaveOnPopBinding)
    {
        this.leaveOnPopBinding = leaveOnPopBinding;
    }

    public RichSelectOneChoice getLeaveOnPopBinding()
    {
        return leaveOnPopBinding;
    }

    public void setExtraTimeOnPopBinding(RichInputText extraTimeOnPopBinding)
    {
        this.extraTimeOnPopBinding = extraTimeOnPopBinding;
    }

    public RichInputText getExtraTimeOnPopBinding()
    {
        return extraTimeOnPopBinding;
    }

    public void extraTimeOnPopVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null)
        {
            leaveOnPopBinding.setValue(null);
            lwpChkBinding.setValue(null);
            //  lwpChkBinding.setDisabled(true);

        }
        else
        {
            // lwpChkBinding.setDisabled(false);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(lwpChkBinding);
    }

    public void leaveOnPopVCL(ValueChangeEvent valueChangeEvent)
    {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (valueChangeEvent.getNewValue() != null)
        {
            extraTimeOnPopBinding.setValue(null);
            lwpChkBinding.setValue(null);
            // lwpChkBinding.setDisabled(true);
            //DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            DCBindingContainer dcBindings = ADFBeanUtils.getDCBindingContainer();
            DCIteratorBinding dtlItr = (DCIteratorBinding) dcBindings.get("TimeCalendarEditorDetail1Iterator");
            dtlItr.getCurrentRow().setAttribute("InTime", null);
            dtlItr.getCurrentRow().setAttribute("OutTime", null);
            inTimeBinding.setValue(null);
            outTimeBinding.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(inTimeBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(outTimeBinding);

        }
        else
        {
            halfDayLeaveChkBinding.setValue(false);
            qtrLeaveChkBinding.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(qtrLeaveChkBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(halfDayLeaveChkBinding);
            //lwpChkBinding.setDisabled(false);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(lwpChkBinding);

    }

    public void setInTimeBinding(RichInputText inTimeBinding)
    {
        this.inTimeBinding = inTimeBinding;
    }

    public RichInputText getInTimeBinding()
    {
        return inTimeBinding;
    }

    public void setOutTimeBinding(RichInputText outTimeBinding)
    {
        this.outTimeBinding = outTimeBinding;
    }

    public RichInputText getOutTimeBinding()
    {
        return outTimeBinding;
    }

    /**Salary Processing Action*/
    public void salaryProcessBtnAL(ActionEvent actionEvent)
    {
        if (crtBinding.getValue() != null && crtBinding.getValue().toString().length() > 0)
        {
            if (critariaValueBinding.getValue() != null && critariaValueBinding.getValue().toString().length() > 0)
            {
                if (startDtBinding.getValue() != null && startDtBinding.getValue().toString().length() > 0)
                {
                    if (endDtBinding.getValue() != null && endDtBinding.getValue().toString().length() > 0)
                    {
                        /** Put Other Validation before salry processing.*/
                        OperationBinding chkOp = ADFBeanUtils.getOperationBinding("chkSalProcStatus");
                        chkOp.execute();
                        if (chkOp.getErrors().isEmpty() && chkOp.getResult() != null)
                        {
                            BigDecimal reslt = (BigDecimal) chkOp.getResult();
                            System.out.println("result for previsous salChk--" + reslt);
                            if (reslt.compareTo(new BigDecimal(1)) == 0)
                            {
                                OperationBinding binding = ADFBeanUtils.getOperationBinding("salaryProcess");
                                binding.execute();
                                if (binding.getErrors().isEmpty() && binding.getResult() != null)
                                {
                                    BigDecimal rslt = (BigDecimal) binding.getResult();
                                    System.out.println("processing reslt in BEAN--" + rslt);
                                    if (rslt.compareTo(new BigDecimal(1)) == 0)
                                    {
                                        ADFBeanUtils.getOperationBinding("Commit").execute(); /** as discussed with gaurav sir code will commit the transaction.*/
                                        //FacesMessage message = new FacesMessage("Salary processed successfully.");
                                        FacesMessage message =
                                            new FacesMessage(resolvElDCMsg("#{bundle['MSG.1468']}").toString());
                                        message.setSeverity(FacesMessage.SEVERITY_INFO);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message);
                                    }
                                    else
                                    {
                                        //FacesMessage message =
                                        //  new FacesMessage("Salary processed unsuccessfully, Please check data!");
                                        FacesMessage message =
                                            new FacesMessage(resolvElDCMsg("#{bundle['MSG.1469']}").toString());
                                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message);
                                    }
                                }

                            }
                            else
                            {
                                //FacesMessage message =
                                //new FacesMessage("Salary can not be processed, previous salary is either pending for approval or has not been processed!");
                                FacesMessage message =
                                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.1470']}").toString());
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            }
                        }
                    }
                    else
                    {
                        System.out.println("to date is blank");
                        // FacesMessage message = new FacesMessage("To date is mandatory.");
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['LBL.3615']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                }
                else
                {
                    //FacesMessage message = new FacesMessage("From date is mandatory.");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1462']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(startDtBinding.getClientId(), message);
                }

            }
            else
            {
                //FacesMessage message = new FacesMessage("Please make a selection.");
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1463']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(critariaValueBinding.getClientId(), message);
            }
        }
        else
        {
            //FacesMessage message = new FacesMessage("Criteria is mandatory.");
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1464']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(crtBinding.getClientId(), message);
        }
    }

    public void inTimeVCL(ValueChangeEvent valueChangeEvent)
    {
        if (halfDayLeaveChkBinding.getValue() != null && halfDayLeaveChkBinding.getValue().equals(false) &&
            qtrLeaveChkBinding.getValue() != null && qtrLeaveChkBinding.getValue().equals(false))
        {
            leaveOnPopBinding.setValue(null);

            if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0)
            {
                if (outTimeBinding.getValue() != null && outTimeBinding.getValue().toString().length() > 0)
                {
                    System.out.println("new in time --" + valueChangeEvent.getNewValue());
                    System.out.println("out time----" + outTimeBinding.getValue());
                    OperationBinding op = ADFBeanUtils.getOperationBinding("calcExtraTime");
                    op.getParamsMap().put("newInTime", valueChangeEvent.getNewValue());
                    op.getParamsMap().put("newOutTime", outTimeBinding.getValue());
                    op.execute();
                    if (op.getErrors().isEmpty() && op.getResult() != null)
                    {
                        Number rslt = (Number) op.getResult();
                        extraTimeOnPopBinding.setValue(rslt);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(extraTimeOnPopBinding);
                    }
                }
                lwpChkBinding.setValue(null);
                //  lwpChkBinding.setDisabled(true);
            }
            else
            {
                //  lwpChkBinding.setDisabled(false);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(lwpChkBinding);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(lwpChkBinding);
    }

    public void outTimeVCL(ValueChangeEvent valueChangeEvent)
    {
        if (halfDayLeaveChkBinding.getValue() != null && halfDayLeaveChkBinding.getValue().equals(false) &&
            qtrLeaveChkBinding.getValue() != null && qtrLeaveChkBinding.getValue().equals(false))
        {
            leaveOnPopBinding.setValue(null);
            if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0)
            {
                if (inTimeBinding.getValue() != null && inTimeBinding.getValue().toString().length() > 0)
                {
                    System.out.println("new out time --" + valueChangeEvent.getNewValue());
                    System.out.println("in time----" + inTimeBinding.getValue());
                    OperationBinding op = ADFBeanUtils.getOperationBinding("calcExtraTime");
                    op.getParamsMap().put("newInTime", inTimeBinding.getValue());
                    op.getParamsMap().put("newOutTime", valueChangeEvent.getNewValue());
                    op.execute();
                    if (op.getErrors().isEmpty() && op.getResult() != null)
                    {
                        Number rslt = (Number) op.getResult();
                        extraTimeOnPopBinding.setValue(rslt);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(extraTimeOnPopBinding);
                    }
                }
                lwpChkBinding.setValue(null);
                // lwpChkBinding.setDisabled(true);
            }
            else
            {
                // lwpChkBinding.setDisabled(false);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(lwpChkBinding);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(lwpChkBinding);
    }

    public String uploadBtnAction()
    {
        if (crtBinding.getValue() != null && crtBinding.getValue().toString().length() > 0)
        {
            if (critariaValueBinding.getValue() != null && critariaValueBinding.getValue().toString().length() > 0)
            {
                if (startDtBinding.getValue() != null && startDtBinding.getValue().toString().length() > 0)
                {
                    if (endDtBinding.getValue() != null && endDtBinding.getValue().toString().length() > 0)
                    {
                        // ADFBeanUtils.getOperationBinding("setBindVarInMasterVO").execute();
                        // OperationBinding binding = ADFBeanUtils.getOperationBinding("filterHeaderVo");
                        // binding.execute();
                        /* if (binding.getErrors().isEmpty() && binding.getResult() != null) {
                        String rslt = binding.getResult().toString();
                        System.out.println("result is--" + rslt);
                        if (rslt.equalsIgnoreCase("N")) {
                            ADFBeanUtils.getOperationBinding("enterNewInHeader").execute();
                            this.mode = "A";*/
                        OperationBinding op = ADFBeanUtils.getOperationBinding("chkGrpForAttenUpload");
                        op.execute();
                        if (op.getErrors().isEmpty() && op.getResult() != null)
                        {
                            String rslt = op.getResult().toString();
                            System.out.println("result for upload--->" + rslt);
                            if (rslt.equalsIgnoreCase("Y"))
                            {
                                System.out.println("result yes for upload--->" + rslt);
                                showPopup(uploadPopupBinding, true);
                                this.mode = "E";
                            }
                            else if (rslt.equalsIgnoreCase("N"))
                            {
                                //FacesMessage message =
                                //new FacesMessage("Upload attendance not applicable for the selected group!");
                                FacesMessage message =
                                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.1471']}").toString());
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            }
                        }


                        /*   } else {
                            FacesMessage message =
                                new FacesMessage("Record already exists for the selected criteria,please try with edit existing!!");
                            message.setSeverity(FacesMessage.SEVERITY_WARN);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }
                    }*/

                    }
                    else
                    {

                        //FacesMessage message = new FacesMessage("To date is mandatory.");
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['LBL.3615']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                }
                else
                {
                    //FacesMessage message = new FacesMessage("From date is mandatory.");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1462']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(startDtBinding.getClientId(), message);
                }

            }
            else
            {
                //FacesMessage message = new FacesMessage("Please make a selection.");
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1463']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(critariaValueBinding.getClientId(), message);
            }

        }
        else
        {
            //FacesMessage message = new FacesMessage("Criteria is mandatory.");
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1464']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(crtBinding.getClientId(), message);
        }
        return null;
    }

    public String excelImportAction()
    {
        try
        {

            if (file == null)
            {
                //FacesMessage message = new FacesMessage("Please Select a File..");
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1472']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message);
                return null;
            }

            String type = file.getContentType();
            String name = file.getFilename();
            //            int val = name.lastIndexOf(type);
            int val = name.lastIndexOf(".");
            String fileType = name.substring(val + 1, name.length());
            System.out.println("file type is:  " + fileType);
            System.out.println("file type is:  " + type + "\n" + name);

            if (fileType.equalsIgnoreCase("XLS"))
            {
                System.out.println("File type: xls");
                HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
                HSSFSheet at = workbook.getSheetAt(0);
                int rowCount = at.getPhysicalNumberOfRows();
                System.out.println("No. of rows=" + rowCount);
                Map<List<Object>, List<Object>> map = new HashMap<List<Object>, List<Object>>();

                outer:
                for (int i = 1; i < rowCount; i++)
                {
                    HSSFRow row = at.getRow(i);
                    List<Object> list = new ArrayList<Object>();
                    List<Object> keylist = new ArrayList<Object>();
                    if (row != null)
                    {
                        if (row.getPhysicalNumberOfCells() < 6)
                        {
                            callInsertDummy(row);
                            continue outer;
                        }
                        System.out.println("physical no. of cells in row " + i + " = " +
                                           row.getPhysicalNumberOfCells());
                        inner:
                        for (int j = 0; j < row.getPhysicalNumberOfCells(); j++)
                        {
                            System.out.println("For cell index [" + i + "][" + j + "]");
                            Cell cell = row.getCell(j);
                            /* if ((!(j == (row.getPhysicalNumberOfCells() - 1))) && cell == null) {
                                System.out.println("..... null cells");
                                callInsertDummy(row);
                                continue outer;
                            } */
                            if (((j <= 2)) && cell == null)
                            {
                                System.out.println("..... null cells");
                                callInsertDummy(row);
                                continue outer;
                            }
                            //cell type 0-> Numeric, 1->String, 2->Formula, 3->Blank 4->Boolean 5->Error
                            if (j == 0 && (cell.getCellType() != 1 && cell.getCellType() != 3))
                            {
                                callInsertDummy(row);
                                continue outer;
                            }

                            /* no need to check employee name as we are not storing employee name in table
                             if (j == 1 && (cell.getCellType() != 1 && cell.getCellType() != 3)) {
                                callInsertDummy(row);
                                continue outer;
                            } */

                            if (j == 2 &&
                                ((cell.getCellType() != 1 && cell.getCellType() != 3) ||
                                 getConvertDate(cell.getStringCellValue()) == null))
                            {
                                callInsertDummy(row);
                                continue outer;
                            }
                            if (j == 3 && cell != null && (cell.getCellType() != 1 && cell.getCellType() != 3))
                            {
                                callInsertDummy(row);
                                continue outer;
                            }
                            if (j == 4 && cell != null && (cell.getCellType() != 1 && cell.getCellType() != 3))
                            {
                                callInsertDummy(row);
                                continue outer;
                            }
                            if (j == 5 && cell != null && (cell.getCellType() != 1 && cell.getCellType() != 3))
                            {
                                callInsertDummy(row);
                                continue outer;
                            }
                            if (j == 6 && cell != null && (cell.getCellType() != 0 && cell.getCellType() != 3))
                            {
                                callInsertDummy(row);
                                continue outer;
                            }


                            if (j == 0)
                            {
                                OperationBinding binding = ADFBeanUtils.getOperationBinding("checkValidEmpId");
                                binding.getParamsMap().put("empId", cell.getStringCellValue());
                                Object execute = binding.execute();
                                if (execute != null)
                                {
                                    String obj = (String) binding.getResult();
                                    System.out.println("EmpId is valid : " + obj);
                                    if (obj.equals("Y"))
                                    {
                                        keylist.add(cell.getStringCellValue());
                                    }
                                    else
                                    {
                                        callInsertDummy(row);
                                        continue outer;
                                    }
                                }
                                else
                                {
                                    callInsertDummy(row);
                                    continue outer;
                                }
                            }

                            if (j == 2)
                            {
                                OperationBinding binding = ADFBeanUtils.getOperationBinding("checkValidAttnDt");
                                binding.getParamsMap().put("attnDt", cell.getStringCellValue());
                                Object execute = binding.execute();
                                if (execute != null)
                                {
                                    String obj = (String) binding.getResult();
                                    System.out.println("AttnDt is valid : " + obj);
                                    if (obj.equals("Y"))
                                    {
                                        keylist.add(cell.getStringCellValue());
                                        //Check if Key already exist in map or not
                                        if (map.containsKey(keylist))
                                        {
                                            System.out.println("Record for= " + keylist + " already exist in map");
                                            callInsertDummy(row);
                                            continue outer;
                                        }
                                    }
                                    else
                                    {
                                        callInsertDummy(row);
                                        continue outer;
                                    }
                                }
                                else
                                {
                                    callInsertDummy(row);
                                    continue outer;
                                }
                            }

                            /*  if(j == 2){
                                keylist.add(cell.getStringCellValue());
                            //Check if Key already exist in map or not
                            if (map.containsKey(keylist)) {
                                System.out.println("Record for= "+keylist+" already exist in map");
                                callInsertDummy(row);
                                continue outer;
                            }
                            } */

                            if (j == 3 && cell != null)
                            {
                                System.out.println("Leave Name=" + cell.getStringCellValue());
                                OperationBinding binding = ADFBeanUtils.getOperationBinding("checkValidLeaveNm");
                                binding.getParamsMap().put("leaveNm", cell.getStringCellValue());
                                Object execute = binding.execute();
                                if (execute != null)
                                {
                                    String obj = (String) binding.getResult();
                                    System.out.println("LN is valid : " + obj);
                                    if (obj.equals("Y"))
                                    {
                                    }
                                    else
                                    {
                                        callInsertDummy(row);
                                        continue outer;
                                    }
                                }
                                else
                                {
                                    callInsertDummy(row);
                                    continue outer;
                                }
                            }

                            if (j == 4 && cell != null)
                            {
                                System.out.println("InT=" + cell.getStringCellValue());
                                OperationBinding binding = ADFBeanUtils.getOperationBinding("checkValidInTime");
                                binding.getParamsMap().put("inTime", cell.getStringCellValue());
                                Object execute = binding.execute();
                                if (execute != null)
                                {
                                    String obj = (String) binding.getResult();
                                    System.out.println("IT is valid : " + obj);
                                    if (obj.equals("Y"))
                                    {
                                    }
                                    else
                                    {
                                        callInsertDummy(row);
                                        continue outer;
                                    }
                                }
                                else
                                {
                                    callInsertDummy(row);
                                    continue outer;
                                }
                            }


                            if (j == 5 && cell != null)
                            {
                                System.out.println("Otime=" + cell.getStringCellValue());
                                OperationBinding binding = ADFBeanUtils.getOperationBinding("checkValidOutTime");
                                binding.getParamsMap().put("outTime", cell.getStringCellValue());
                                Object execute = binding.execute();
                                if (execute != null)
                                {
                                    String obj = (String) binding.getResult();
                                    System.out.println("OT is valid : " + obj);
                                    if (obj.equals("Y"))
                                    {
                                    }
                                    else
                                    {
                                        callInsertDummy(row);
                                        continue outer;
                                    }
                                }
                                else
                                {
                                    callInsertDummy(row);
                                    continue outer;
                                }
                            }


                            if (j == 6 && cell != null)
                            {
                                //System.out.println("Extr="+cell.getStringCellValue());
                                OperationBinding binding = ADFBeanUtils.getOperationBinding("checkValidExtraTime");
                                binding.getParamsMap().put("extraTimeHr", cell.getNumericCellValue());
                                Object execute = binding.execute();
                                if (execute != null)
                                {
                                    String obj = (String) binding.getResult();
                                    System.out.println("ET is : " + obj);
                                    if (obj.equals("Y"))
                                    {
                                    }
                                    else
                                    {
                                        callInsertDummy(row);
                                        continue outer;
                                    }
                                }
                                else
                                {
                                    callInsertDummy(row);
                                    continue outer;
                                }
                            }
                        }

                        List<Object> keyArrlist = new ArrayList<Object>();
                        for (int j = 0; j < row.getPhysicalNumberOfCells(); j++)
                        {
                            Cell cell = row.getCell(j);
                            if (cell != null)
                            {
                                switch (cell.getCellType())
                                {
                                case 0: //numeric
                                    list.add(cell.getNumericCellValue());
                                    break;
                                case 1: //String
                                    if (j == 0 || j == 2)
                                    {
                                        //System.out.println("adding empid=>" + cell.getStringCellValue());
                                        keyArrlist.add(cell.getStringCellValue());
                                        if (j == 2)
                                            list.add(cell.getStringCellValue());
                                    }
                                    else
                                    {
                                        list.add(cell.getStringCellValue());
                                    }
                                    break;
                                case 3:
                                    list.add(null);
                                    break;
                                }
                            }
                            else
                                list.add(null);
                        }

                        map.put(keyArrlist, list);
                    }
                }

                Set<List<Object>> keySet = map.keySet();
                for (List<Object> key : keySet)
                {
                    List<Object> listVal = map.get(key);
                    Map<String, Object> valMap = new HashMap<String, Object>();
                    valMap.put("empId", key.get(0));

                    for (int i = 0; i < listVal.size(); i++)
                    {
                        switch (i)
                        {
                        case 1:
                            if (listVal.get(i) != null)
                                valMap.put("attDt", getConvertDate((String) listVal.get(i)));
                            else
                                valMap.put("attDt", null);
                            break;
                        case 0:
                            valMap.put("empNm", listVal.get(i));
                            break;
                        case 2:
                            valMap.put("leaveNm", listVal.get(i));
                            break;
                        case 3:
                            valMap.put("inTime", listVal.get(i));
                            break;
                        case 4:
                            valMap.put("outTime", listVal.get(i));
                            break;
                        case 5:
                            valMap.put("extHr", listVal.get(i));
                            break;
                        }
                    }
                    /*  valMap.put("Eoid", new Integer(0));
                           // valMap.put("catagId", transAddCatagNmBVar.getValue().toString());
                           valMap.put("priceApply", "N"); */

                    OperationBinding binding = ADFBeanUtils.getOperationBinding("addEntryInDBFromExcel");
                    binding.getParamsMap().putAll(valMap);
                    binding.execute();

                }

                /*  ADFBeanUtils.getOperationBinding("Commit").execute();
                       ADFBeanUtils.getOperationBinding("executeVo").execute();
                       ADFBeanUtils.getOperationBinding("executeDistinctPnVo").execute(); */
            }
            else
            {
                //FacesMessage message = new FacesMessage("Invalid File Type!!");
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1473']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }

        }
        catch (Exception e) {
            // FacesMessage message =
            //new FacesMessage("Please Select File Again or File is corrupted..");
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1474']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);
            e.printStackTrace();

        }
        uploadPopupBinding.hide();
        return null;
    }

    public void callInsertDummy(HSSFRow row)
    {
        int count = row.getPhysicalNumberOfCells();
        boolean flag = false;
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < 7; i++)
        {
            HSSFCell cell = row.getCell(i);
            list.add(cell);
            if ((!flag) && cell != null)
            {
                flag = true;
            }
        }
        if (flag)
        {
            /* OperationBinding binding = ADFBeanUtils.getOperationBinding("insertinDumpData");
                        binding.getParamsMap().put("list", list);
                        binding.execute(); */
        }
    }

    private java.util.Date getConvertDate(String str)
    {
        java.util.Date d = null;
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        try
        {
            d = format.parse(str);
            System.out.println("Date parsed=" + d);
        }
        catch (Exception e) {
            System.out.println("Error during parse");
            e.printStackTrace();
        }
        return d;
    }

    public void setUploadPopupBinding(RichPopup uploadPopupBinding)
    {
        this.uploadPopupBinding = uploadPopupBinding;
    }

    public RichPopup getUploadPopupBinding()
    {
        return uploadPopupBinding;
    }

    public void UploadFileVCE(ValueChangeEvent valueChangeEvent)
    {
        UploadedFile myfile = (UploadedFile) valueChangeEvent.getNewValue();
        try
        {
            inputStream = myfile.getInputStream();
        }
        catch (IOException e) {
            System.out.println("Error in get input stream");
        }
    }


    public void inTimeValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            // String msg = "Invalid Time! Please enter format 00:00.";
            String msg = resolvElDCMsg("#{bundle['MSG.1475']}").toString();
            pattern = Pattern.compile(TIME24HOURS_PATTERN);
            matcher = pattern.matcher(object.toString());
            if (!matcher.matches())
            {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }

    }

    public void outTimeValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            //String msg = "Invalid Time! Please enter format 00:00.";
            String msg = resolvElDCMsg("#{bundle['MSG.1475']}").toString();
            pattern = Pattern.compile(TIME24HOURS_PATTERN);
            matcher = pattern.matcher(object.toString());
            if (!matcher.matches())
            {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }

    public void startDateVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null)
        {
            Date dateforManual = null;
            oracle.jbo.domain.Timestamp dt = (oracle.jbo.domain.Timestamp) valueChangeEvent.getNewValue();
            System.out.println("from date is-----" + dt);
            oracle.jbo.domain.Date dt2 = null;
            try
            {
                dt2 = new oracle.jbo.domain.Date(dt.dateValue());

            }
            catch (SQLException e) {
                e.printStackTrace();
            }

            if (frequencyBinding.getValue().equals(7))
            {
                Date dateBefore = new Date((dt2.addMonths(1).dateValue()).getTime() - 1 * 24 * 3600 * 1000);
                System.out.println("aft minus one day date is-- " + dateBefore);
                endDtBinding.setValue(new oracle.jbo.domain.Timestamp(dateBefore));

                AdfFacesContext.getCurrentInstance().addPartialTarget(endDtBinding);
                OperationBinding opsetDtChk = ADFBeanUtils.getOperationBinding("salaryAttendenceDateRangeAM");
                opsetDtChk.getParamsMap().put("dt", dt);
                opsetDtChk.getParamsMap().put("dateforManual", dateforManual);
                opsetDtChk.execute();
                System.out.println("result for lag date set=" + opsetDtChk.getResult());
                if (opsetDtChk.getResult() != null && opsetDtChk.getResult() != "")
                {

                    System.out.println("result for lag date set=" + opsetDtChk.getResult().toString());
                    String result = opsetDtChk.getResult().toString();
                    if (result.equals("N"))
                    {
                        FacesContext context = FacesContext.getCurrentInstance();
                        context.addMessage(startDtBinding.getClientId(context),
                                           new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                            "Please define Attendance period for this date.", null));
                        endDtBinding.setValue(null);
                        attDateRangeBinding.setValue(null);
                        attDateRangeEndBinding.setValue(null);
                    }
                }
                else
                {
                    System.out.println("error during setting lag value=" + opsetDtChk.getErrors());
                }

            }

            else if (frequencyBinding.getValue().equals(8))
            {
                Date dateforweely = new Date((dt2.dateValue()).getTime() + 6 * 24 * 3600 * 1000);

                System.out.println("weekly end date is --" + dateforweely);
                endDtBinding.setValue(new oracle.jbo.domain.Timestamp(dateforweely));
            }

            else if (frequencyBinding.getValue().equals(9))
            {
                Integer days = (Integer) freqDaysBinding.getValue();
                Date tempDate = null;
                if (days.compareTo(10) <= 0)
                    dateforManual = new Date((dt2.dateValue()).getTime() + (days - 1) * 24 * 3600 * 1000);
                else
                {
                    tempDate = new Date((dt2.dateValue()).getTime() + (days - 10 - 1) * 24 * 3600 * 1000);
                    dateforManual = new Date((tempDate).getTime() + (10 * 24 * 3600 * 1000));
                }
                System.out.println("manual end date is -" + dateforManual);
                endDtBinding.setValue(new oracle.jbo.domain.Timestamp(dateforManual));
            }


        }


    }

    public void setCrtBinding(RichSelectOneChoice crtBinding)
    {
        this.crtBinding = crtBinding;
    }

    public RichSelectOneChoice getCrtBinding()
    {
        return crtBinding;
    }

    public void criteriaVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null)
        {
            critariaValueBinding.setValue(null);
            startDtBinding.setValue(null);
            endDtBinding.setValue(null);
            attDateRangeBinding.setValue(null);
            attDateRangeEndBinding.setValue(null);
        }
    }

    public void setFrequencyBinding(RichSelectOneChoice frequencyBinding)
    {
        this.frequencyBinding = frequencyBinding;
    }

    public RichSelectOneChoice getFrequencyBinding()
    {
        return frequencyBinding;
    }

    public void setFreqDaysBinding(RichInputText freqDaysBinding)
    {
        this.freqDaysBinding = freqDaysBinding;
    }

    public RichInputText getFreqDaysBinding()
    {
        return freqDaysBinding;
    }

    public void criteriaValueVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null)
        {
            startDtBinding.setValue(null);
            endDtBinding.setValue(null);
            attDateRangeBinding.setValue(null);
            attDateRangeEndBinding.setValue(null);
            // AdfFacesContext.getCurrentInstance().addPartialTarget(startDtBinding);
            //AdfFacesContext.getCurrentInstance().addPartialTarget(endDtBinding);
        }
    }

    public void goToSalStatusTFAL(ActionEvent actionEvent)
    {
        if (crtBinding.getValue() != null && crtBinding.getValue().toString().length() > 0)
        {
            if (critariaValueBinding.getValue() != null && critariaValueBinding.getValue().toString().length() > 0)
            {
                if (startDtBinding.getValue() != null && startDtBinding.getValue().toString().length() > 0)
                {
                    if (endDtBinding.getValue() != null && endDtBinding.getValue().toString().length() > 0)
                    {
                        OperationBinding binding = ADFBeanUtils.getOperationBinding("filterSalProcTxn");
                        binding.execute();
                        if (binding.getErrors().isEmpty() && binding.getResult() != null)
                        {
                            String rslt = binding.getResult().toString();
                            if (rslt.equalsIgnoreCase("N"))
                            {
                                //FacesMessage message =
                                //new FacesMessage("No Transactions available for selected parameters, Please try with other parameters!!");
                                FacesMessage message =
                                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.1476']}").toString());
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                                ADFBeanUtils.getOperationBinding("resetFilterEmp").execute();
                                setChk("N");
                            }
                            else
                            {
                                String doc = binding.getResult().toString();
                                System.out.println("doctxnid passing is--" + doc);
                                txnDocId = doc;
                                setChk("Y");
                            }
                        }
                    }
                    else
                    {
                        //FacesMessage message = new FacesMessage("To date is mandatory.");
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['LBL.3615']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                }
                else
                {
                    //FacesMessage message = new FacesMessage("From date is mandatory.");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1462']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(startDtBinding.getClientId(), message);
                }

            }
            else
            {
                //FacesMessage message = new FacesMessage("Please make a selection.");
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1463']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(critariaValueBinding.getClientId(), message);
            }

        }
        else
        {
            //FacesMessage message = new FacesMessage("Criteria is mandatory.");
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1464']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(crtBinding.getClientId(), message);
        }

    }


    public void frmDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {

    }


    public void leaveOnPopValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        /* if (object != null) {
            String leaveId = object.toString();
            System.out.println("leave Id in validator----->" + leaveId);
            OperationBinding op = ADFBeanUtils.getOperationBinding("chkLeaveBalOnPop");
            op.getParamsMap().put("leaveId", leaveId);
            op.getParamsMap().put("empDoc", currRow.getAttribute("DocId"));
            op.execute();
            if (op.getErrors().isEmpty() && op.getResult() != null) {
                String rslt = op.getResult().toString();
                if (rslt.equalsIgnoreCase("Y")) {
                    String msg = "Balance for the selected leave is not sufficient!!";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }*/


    }

    public void setLwpChkBinding(RichSelectOneChoice lwpChkBinding)
    {
        this.lwpChkBinding = lwpChkBinding;
    }

    public RichSelectOneChoice getLwpChkBinding()
    {
        return lwpChkBinding;
    }

    public void setWkOffChkBinding(RichSelectBooleanCheckbox wkOffChkBinding)
    {
        this.wkOffChkBinding = wkOffChkBinding;
    }

    public RichSelectBooleanCheckbox getWkOffChkBinding()
    {
        return wkOffChkBinding;
    }

    public void setHalfDayLeaveChkBinding(RichSelectBooleanCheckbox halfDayLeaveChkBinding)
    {
        this.halfDayLeaveChkBinding = halfDayLeaveChkBinding;
    }

    public RichSelectBooleanCheckbox getHalfDayLeaveChkBinding()
    {
        return halfDayLeaveChkBinding;
    }

    public String goToMultipleAttPage()
    {
        if (crtBinding.getValue() != null && crtBinding.getValue().toString().length() > 0)
        {
            if (critariaValueBinding.getValue() != null && critariaValueBinding.getValue().toString().length() > 0)
            {
                if (startDtBinding.getValue() != null && startDtBinding.getValue().toString().length() > 0)
                {
                    if (endDtBinding.getValue() != null && endDtBinding.getValue().toString().length() > 0)
                    {
                        OperationBinding op = ADFBeanUtils.getOperationBinding("fltrHcmTimeEditMultAttVo");
                        op.execute();
                        this.mode = "E";
                        return "goToMultipleAttPage";
                    }

                    else
                    {
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['LBL.3615']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                }
                else
                {
                    //FacesMessage message = new FacesMessage("From date is mandatory.");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1462']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(startDtBinding.getClientId(), message);
                }

            }
            else
            {
                // FacesMessage message = new FacesMessage("Please make a selection.");
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1463']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(critariaValueBinding.getClientId(), message);
            }

        }
        else
        {
            //FacesMessage message = new FacesMessage("Criteria is mandatory.");
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1464']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(crtBinding.getClientId(), message);
        }

        return null;
    }


    public void setAttDtBinding(RichInputDate attDtBinding)
    {
        this.attDtBinding = attDtBinding;
    }

    public RichInputDate getAttDtBinding()
    {
        return attDtBinding;
    }

    public void setDedChBinding(RichSelectOneChoice dedChBinding)
    {
        this.dedChBinding = dedChBinding;
    }

    public RichSelectOneChoice getDedChBinding()
    {
        return dedChBinding;
    }

    public void setXtraTmeBinding(RichInputText xtraTmeBinding)
    {
        this.xtraTmeBinding = xtraTmeBinding;
    }

    public RichInputText getXtraTmeBinding()
    {
        return xtraTmeBinding;
    }

    public void setLeaveidBinding(RichSelectOneChoice leaveidBinding)
    {
        this.leaveidBinding = leaveidBinding;
    }

    public RichSelectOneChoice getLeaveidBinding()
    {
        return leaveidBinding;
    }

    public void setInTimeFrMultAttBinding(RichInputText inTimeFrMultAttBinding)
    {
        this.inTimeFrMultAttBinding = inTimeFrMultAttBinding;
    }

    public RichInputText getInTimeFrMultAttBinding()
    {
        return inTimeFrMultAttBinding;
    }

    public void setOutTimeFrMultAttBinding(RichInputText outTimeFrMultAttBinding)
    {
        this.outTimeFrMultAttBinding = outTimeFrMultAttBinding;
    }

    public RichInputText getOutTimeFrMultAttBinding()
    {
        return outTimeFrMultAttBinding;
    }

    public void inTimeFrMultiAttVC(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0)
        {
            if (xtraTmeBinding.getValue() != null && xtraTmeBinding.getValue().toString().length() > 0)
            {

            }
        }
        else
        {
        }
    }

    public void outTimeFrMultAttVC(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0)
        {
        }
        else
        {
        }
    }

    public void addEmployeFrAtt(ActionEvent actionEvent)
    {
        DCIteratorBinding di = ADFBeanUtils.findIterator("HcmTimeMoveEditDtlFrMultAttIterator");
        boolean result = true;
        if (di.getEstimatedRowCount() > 0)
        {
            result = chkSveMultAttValidation();
        }
        if (result)
        {
            ADFBeanUtils.getOperationBinding("CreateInsert").execute();
            ADFBeanUtils.getOperationBinding("setCriteriaValues").execute();
            setADD_EDIT_MODE("E");
            setMdeFrValidator("T");
        }

    }

    public void addTempSaalComp(ActionEvent actionEvent)
    {
        DCIteratorBinding di = ADFBeanUtils.findIterator("HcmEmpMonSalCompIterator");
        boolean result = true;
        if (di.getEstimatedRowCount() > 0)
        {
            result = chkSveMultTempCompValidation();
        }
        if (result)
        {
            ADFBeanUtils.getOperationBinding("CreateInsert").execute();
            ADFBeanUtils.getOperationBinding("setCriteriaValuesInEmpMonSal").execute();
            setADD_EDIT_MODE("E");
            setDelMode("T");
        }

    }

    public void okMultiAtt(ActionEvent actionEvent)
    {
        boolean result = true;
        String chkCallForMultiAtt = InsertMultipleAttendence();
        if (chkCallForMultiAtt.equals("Yes"))
        {
            clearDummyVo();
            setADD_EDIT_MODE("D");
            setMdeFrValidator("F");
            setMultiAttSwitchMode("single");
        }
        else if (chkCallForMultiAtt.equals("No"))
        {
            DCIteratorBinding di = ADFBeanUtils.findIterator("HcmTimeMoveEditDtlFrMultAttIterator");
            di.executeQueryIfNeeded();
            if (di.getEstimatedRowCount() > 0)
            {
                result = chkSveMultAttValidation();
            }
            if (result)
            {
                if (di.getEstimatedRowCount() > 0)
                {
                    ADFBeanUtils.getOperationBinding("setInOutTimefrMultAtt").execute();
                }
                setADD_EDIT_MODE("D");
                setMdeFrValidator("F");
            }
        }
        else if (chkCallForMultiAtt.equals("validError"))
        {

        }


    }

    public void okMultiTempComp(ActionEvent actionEvent)
    {
        DCIteratorBinding di = ADFBeanUtils.findIterator("HcmEmpMonSalCompIterator");
        di.executeQueryIfNeeded();
        boolean result = true;
        if (di.getEstimatedRowCount() > 0)
        {
            result = chkSveMultTempCompValidation();
            if (result)
            {
                OperationBinding opfixSalChk = ADFBeanUtils.getOperationBinding("chkFixSalAmount");
                opfixSalChk.execute();
                if (opfixSalChk.getErrors().isEmpty() && opfixSalChk.getResult() != null)
                {
                    String opfixSalChkResult[] = (String[]) opfixSalChk.getResult();
                    if (opfixSalChkResult[0].toString().equals("N"))
                    {
                        String emp_Sal = opfixSalChkResult[2];
                        String tot_ded = opfixSalChkResult[1];
                        showMessage("Employee fix salary is less than total temporary deduction amount." +
                                    ":Employeee fix salary is " + emp_Sal +
                                    ",and Employeee temporary deduction amount is " + tot_ded + ".", null);
                        result = false;
                    }
                }
            }
        }
        if (result)
        {
            setADD_EDIT_MODE("D");
            setDelMode("F");
        }
    }

    public boolean chkSveMultAttValidation()
    {
        String message = "";
        boolean result = true;
        /*  if (empNmBinding.getValue() == null || empNmBinding.getValue() == "")
        {
            String cid = empNmBinding.getClientId();
            message = "Please select employee name.";
            showMessage(message, cid);
            return false;
        } */
        if (attDtBinding.getValue() == null || attDtBinding.getValue() == "")
        {
            String cid = attDtBinding.getClientId();
            message = "Please select attendence date.";
            showMessage(message, cid);
            return false;
        }
        if (inTimeFrMultAttBinding.getValue() != null && inTimeFrMultAttBinding.getValue().toString().length() > 0)
        {
            if (outTimeFrMultAttBinding.getValue() == null || outTimeFrMultAttBinding.getValue() == "")
            {
                String cid = outTimeFrMultAttBinding.getClientId();
                message = "Please enter OutTime.";
                showMessage(message, cid);
                return false;
            }
        }
        if (outTimeFrMultAttBinding.getValue() != null && outTimeFrMultAttBinding.getValue().toString().length() > 0)
        {
            if (inTimeFrMultAttBinding.getValue() == null || inTimeFrMultAttBinding.getValue() == "")
            {
                String cid = inTimeFrMultAttBinding.getClientId();
                message = "Please enter InTime.";
                showMessage(message, cid);
                return false;
            }
        }

        if (dedChBinding.getValue() != null && dedChBinding.getValue().toString().length() > 0)
        {
            System.out.println("lwp selected---->" + dedChBinding.getValue());
        }
        else if (weekOffFrMultBinding.getValue().equals(true))
        {
            System.out.println("week off checked--" + weekOffFrMultBinding.getValue());
        }
        else if (dedChBinding.getValue() == null || dedChBinding.getValue().toString().length() <= 0 ||
                 dedChBinding.getValue().equals(false))
        {
            if ((leaveidBinding.getValue() == null || leaveidBinding.getValue().toString().length() <= 0) &&
                (xtraTmeBinding.getValue() == null || xtraTmeBinding.getValue().equals(" ")) ||
                weekOffFrMultBinding.getValue() == false)
            {

                FacesMessage mssg =
                    new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2759")); //Please select leave or enter In and Out Time!
                mssg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, mssg);
                result = false;
            }
        }
        if (inTimeFrMultAttBinding.getValue() != null && inTimeFrMultAttBinding.getValue().toString().length() > 0)
        {

        }
        return result;
    }

    public boolean chkSveMultAttValidationForMultiAttDummyVo()
    {
        String message = "";
        boolean result = true;
        /*  if (dummyEmpNmBinding.getValue() == null || dummyEmpNmBinding.getValue() == "")
        {
            String cid = dummyEmpNmBinding.getClientId();
            message = "Please select employee name.";
            showMessage(message, cid);
            return false;
        } */
        if (dummyFromDateBinding.getValue() == null || dummyFromDateBinding.getValue() == "")
        {
            String cid = dummyFromDateBinding.getClientId();
            message = "Please select from date.";
            showMessage(message, cid);
            return false;
        }
        if (dummyToDtBinding.getValue() == null || dummyToDtBinding.getValue() == "")
        {
            String cid = dummyToDtBinding.getClientId();
            message = "Please select to date.";
            showMessage(message, cid);
            return false;
        }


        if (dummyDedchBinding.getValue() != null && dummyDedchBinding.getValue().toString().length() > 0)
        {

        }
        else if (dummyWeekOfChk.getValue().equals(true))
        {
        }
        else if (dummyDedchBinding.getValue() == null || dummyDedchBinding.getValue().toString().length() <= 0 ||
                 dummyDedchBinding.getValue().equals(false))
        {
            if ((dummyLeaveIdBinding.getValue() == null || dummyLeaveIdBinding.getValue().toString().length() <= 0))
            {

                FacesMessage mssg = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.2760")); //Please select leave!
                mssg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, mssg);
                result = false;
            }
        }

        return result;
    }

    public void clearDummyVo()
    {
        dummyEmpDocIdBinding.setValue("");
        // dummyEmpNmBinding.setValue("");
        dummyFromDateBinding.setValue("");
        dummyToDtBinding.setValue("");
        dummyNumBerOfDayBinding.setValue(0);
        dummyDedchBinding.setValue("");
        dummyLeaveIdBinding.setValue("");
        dummyWeekOfChk.setValue("");
        dummyHalfDayBinding.setValue("");

    }

    public boolean chkSveMultTempCompValidation()
    {
        String message = "";
        boolean result = true;
        /* if (empNmForTempComBinding.getValue() == null || empNmForTempComBinding.getValue() == "")
        {
            String cid = empNmForTempComBinding.getClientId();
            message = "Please select employee name.";
            showMessage(message, cid);
            return false;
        } */
        if (salIdForTempComBinding.getValue() == null || salIdForTempComBinding.getValue() == "")
        {
            String cid = salIdForTempComBinding.getClientId();
            message = "Please select salary component.";
            showMessage(message, cid);
            return false;
        }
        if (isOthrSubComBinding.getValue().equals("Y"))
        {
            DCIteratorBinding subcom = ADFBeanUtils.findIterator("OrgHcmEmpSalSub1Iterator");
            if (subcom.getEstimatedRowCount() > 0)
            {

            }
            else
            {
                message = "Please add sub salary component.";
                showMessage(message, null);
                return false;
            }
        }
        if (amtforTempComBinding.getValue() == null || amtforTempComBinding.getValue() == "")
        {
            String cid = amtforTempComBinding.getClientId();
            message = "Please enter amount";
            showMessage(message, cid);
            return false;
        }

        return result;
    }


    public String showMessage(String message, String cid)
    {
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(cid, fm);
        return null;
    }

    public String showinfoMessage(String message, String cid)
    {

        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(cid, fm);
        return null;
    }

    public void emploeeNmVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0)
        {
            AdfFacesContext.getCurrentInstance().addPartialTarget(empDocIdBinding);
        }

    }

    public void setEmpDocIdBinding(RichInputText empDocIdBinding)
    {
        this.empDocIdBinding = empDocIdBinding;
    }

    public RichInputText getEmpDocIdBinding()
    {
        return empDocIdBinding;
    }

    public void AttDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            java.sql.Date attDt = null;

            try
            {
                System.out.println(((oracle.jbo.domain.Timestamp) object).dateValue());
                attDt = ((oracle.jbo.domain.Timestamp) object).dateValue();

            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            String rslt = "";
            OperationBinding op = ADFBeanUtils.getOperationBinding("validateAttDt");
            op.getParamsMap().put("attnDt", attDt);
            op.getParamsMap().put("fullattDt", object);
            op.execute();
            if (op.getErrors().isEmpty() && op.getResult() != null)
            {
                rslt = op.getResult().toString();
                if (rslt.equalsIgnoreCase("Y"))
                {
                    String msg = ADFModelUtils.resolvRsrc("MSG.2761"); //Please select date in between date range.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
                else if (rslt.equalsIgnoreCase("X"))
                {
                    String msg =
                        ADFModelUtils.resolvRsrc("MSG.2762"); //Employee already have attendence on this date :Please select different date.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }

    }


    public String BckToSlrProcPage()
    {
        DCIteratorBinding di = ADFBeanUtils.findIterator("HcmTimeMoveEditDtlFrMultAttIterator");
        boolean result = true;
        if (di.getEstimatedRowCount() > 0)
        {
            result = chkSveMultAttValidation();
        }
        if (result)
        {
            OperationBinding op = ADFBeanUtils.getOperationBinding("resetMultAttVo");
            op.execute();
            setADD_EDIT_MODE("D");
            clearDummyVo();
            return "goBckToSlrProcPage";
        }
        return null;
    }

    public void editMultAttAllFields(ActionEvent actionEvent)
    {
        OperationBinding op = ADFBeanUtils.getOperationBinding("chkIsMultiAttEditable");
        op.execute();
        String message = "";
        if (op.getErrors().isEmpty())
        {
            if (op.getResult().equals("-1"))
            {
                message = "Can't edit this attendence:This month Salary has been already process for this employee.";
                showinfoMessage(message, null);
            }
            else if (op.getResult().equals("0"))
            {
                message = "Can't edit this attendence:This month Salary has been already process for this employee.";
                showinfoMessage(message, null);
            }
            else if (op.getResult().equals("1"))
            {
                System.out.println("salary not yet process or pending");
                setADD_EDIT_MODE("E");
            }

        }
    }

    public void editTempCompAllields(ActionEvent actionEvent)
    {
        OperationBinding op = ADFBeanUtils.getOperationBinding("chkIsTempCompEditable");
        op.execute();
        String message = "";
        if (op.getErrors().isEmpty())
        {
            if (op.getResult().equals("-1"))
            {
                message =
                    "Can't edit this temporary salary component:This month Salary has been already process for this employee.";
                showinfoMessage(message, null);
            }
            else if (op.getResult().equals("0"))
            {
                message =
                    "Can't edit this temporary salary component:This month Salary has been already process for this employee.";
                showinfoMessage(message, null);
            }
            else if (op.getResult().equals("1"))
            {
                System.out.println("salary not yet process or pending");
                setADD_EDIT_MODE("E");
            }

        }


    }

    public void halfDyVCL(ValueChangeEvent valueChangeEvent)
    {
        setInTimeFrMultAttBinding(null);
        setOutTimeFrMultAttBinding(null);
    }

    public void setWeekOffFrMultBinding(RichSelectBooleanCheckbox weekOffFrMultBinding)
    {
        this.weekOffFrMultBinding = weekOffFrMultBinding;
    }

    public RichSelectBooleanCheckbox getWeekOffFrMultBinding()
    {
        return weekOffFrMultBinding;
    }

    public void deletMulAtt(ActionEvent actionEvent)
    {
        String message = "";
        DCIteratorBinding di = ADFBeanUtils.findIterator("HcmTimeMoveEditDtlFrMultAttIterator");
        if (di.getEstimatedRowCount() > 0)
        {
            ADFBeanUtils.getOperationBinding("DeleteMultiAttVoRow").execute();
            //  AdfFacesContext.getCurrentInstance().addPartialTarget(empNmBinding);
            message = ADFModelUtils.resolvRsrc("MSG.2767"); //Row deleted successfully.
            setMdeFrValidator("D");
            showinfoMessage(message, null);
        }
    }

    public void deletMulTempSalComp(ActionEvent actionEvent)
    {
        String message = "";
        ADFBeanUtils.getOperationBinding("Delete1").execute();
        message = ADFModelUtils.resolvRsrc("MSG.2767"); //Row deleted successfully.
        showinfoMessage(message, null);
        setDelMode("D");

    }

    public void leaveVCL(ValueChangeEvent valueChangeEvent)
    {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (valueChangeEvent.getNewValue() != null)
        {
            xtraTmeBinding.setValue(null);
            dedChBinding.setValue(null);
            inTimeFrMultAttBinding.setValue(null);
            outTimeFrMultAttBinding.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(inTimeFrMultAttBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(outTimeFrMultAttBinding);

        }
        else
        {
            halfDyLeaveBinding.setValue(false);
            qtrLeaveChkonMultiBinding.setValue(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(halfDyLeaveBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(qtrLeaveChkonMultiBinding);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(dedChBinding);
    }

    public void setHalfDyLeaveBinding(RichSelectBooleanCheckbox halfDyLeaveBinding)
    {
        this.halfDyLeaveBinding = halfDyLeaveBinding;
    }

    public RichSelectBooleanCheckbox getHalfDyLeaveBinding()
    {
        return halfDyLeaveBinding;
    }

    public void xtraTimeVCL(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null)
        {
            leaveidBinding.setValue(null);
            dedChBinding.setValue(null);

        }
        else
        {

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(dedChBinding);

    }

    public void seaarchMultAttAL(ActionEvent actionEvent)
    {
        OperationBinding op = ADFBeanUtils.getOperationBinding("searchMultAttAction");
        op.execute();
    }

    public void resetMultAttAL(ActionEvent actionEvent)
    {
        OperationBinding op = ADFBeanUtils.getOperationBinding("resetMultAttVo");
        op.execute();
    }

    public void weekOffTypOnPopValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.equals(true))
        {
            OperationBinding op = ADFBeanUtils.getOperationBinding("chkWkOffTyp");

            op.getParamsMap().put("empDoc", currRow.getAttribute("DocId"));
            op.execute();
            if (op.getErrors().isEmpty() && op.getResult() != null)
            {
                String rslt = op.getResult().toString();
                if (rslt.equalsIgnoreCase("Y"))
                {
                    String msg =
                        ADFModelUtils.resolvRsrc("MSG.2763"); //Week Off for the selected employee marked as fixed !!
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }
    }

    public void compOffChkValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null)
        {
            OperationBinding op = ADFBeanUtils.getOperationBinding("chkWkOffTyp");

            op.getParamsMap().put("empDoc", currRow.getAttribute("DocId"));
            op.execute();
            if (op.getErrors().isEmpty() && op.getResult() != null)
            {
                String rsl = op.getResult().toString();
                if (rsl.equalsIgnoreCase("Y"))
                { // Fixed week Off
                    OperationBinding ob = ADFBeanUtils.getOperationBinding("chkNotation");
                    ob.getParamsMap().put("empDoc", currRow.getAttribute("DocId"));
                    ob.execute();
                    if (ob.getErrors().isEmpty() && ob.getResult() != null)
                    {
                        String rslt = ob.getResult().toString();
                        if (rslt.equalsIgnoreCase("Y"))
                        {
                            String msg = ADFModelUtils.resolvRsrc("MSG.2764"); //Can not mark Comp Off for this Day!!
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                        }
                    }
                }
                else
                {
                    OperationBinding ob = ADFBeanUtils.getOperationBinding("chkNotation");
                    ob.getParamsMap().put("empDoc", currRow.getAttribute("DocId"));
                    ob.execute();
                    if (ob.getErrors().isEmpty() && ob.getResult() != null)
                    {
                        String rslt = ob.getResult().toString();
                        /*if (!rslt.equalsIgnoreCase("W")) {
                            String msg = "Is this a random weekly off!!";
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                        } */
                        if (wkOffChkBinding.getValue().equals(false) && rslt.equalsIgnoreCase("Y"))
                        {
                            String msg = ADFModelUtils.resolvRsrc("MSG.2764"); //Can not mark Comp Off for this Day!!
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                        }
                    }
                }
            }
        }

    }

    public String goToMultTempSaalCompPage()
    {
        if (crtBinding.getValue() != null && crtBinding.getValue().toString().length() > 0)
        {
            if (critariaValueBinding.getValue() != null && critariaValueBinding.getValue().toString().length() > 0)
            {
                if (startDtBinding.getValue() != null && startDtBinding.getValue().toString().length() > 0)
                {
                    if (endDtBinding.getValue() != null && endDtBinding.getValue().toString().length() > 0)
                    {
                        OperationBinding op = ADFBeanUtils.getOperationBinding("fltrHcmEmpMonSalComptVo");
                        op.execute();
                        this.mode = "E";
                        return "goToTemSalPage";
                    }

                    else
                    {
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['LBL.3615']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                }
                else
                {
                    //FacesMessage message = new FacesMessage("From date is mandatory.");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1462']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(startDtBinding.getClientId(), message);
                }

            }
            else
            {
                // FacesMessage message = new FacesMessage("Please make a selection.");
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1463']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(critariaValueBinding.getClientId(), message);
            }

        }
        else
        {
            //FacesMessage message = new FacesMessage("Criteria is mandatory.");
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1464']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(crtBinding.getClientId(), message);
        }

        return null;
    }

    public String goBackPrevPage()
    {
        DCIteratorBinding di = ADFBeanUtils.findIterator("HcmEmpMonSalCompIterator");
        boolean result = true;
        if (di.getEstimatedRowCount() > 0)
        {
            result = chkSveMultTempCompValidation();
        }
        if (result)
        {
            OperationBinding op = ADFBeanUtils.getOperationBinding("resetMonTemComVo");
            op.execute();
            setADD_EDIT_MODE("D");
            return "goBack";
        }
        return "goBack";
    }

    public void addSubComAction(ActionEvent actionEvent)
    {
        DCIteratorBinding salSubDci = ADFBeanUtils.findIterator("OrgHcmEmpSalSub1Iterator");
        if (salSubDci.getEstimatedRowCount() > 0)
        {
            if (chkSalSubValidation())
            {
                ADFBeanUtils.getOperationBinding("CreateInsert1").execute();
            }
        }
        else
        {
            ADFBeanUtils.getOperationBinding("CreateInsert1").execute();
        }
    }

    public boolean chkSalSubValidation()
    {
        String message = "";
        if (subSalIdBinding.getValue() == null || subSalIdBinding.getValue().toString().length() == 0)
        {
            String cid = subSalIdBinding.getClientId();
            message = "Please select sub salary component";
            showMessage(message, cid);
            return false;
        }
        if (subSalAmtBinding.getValue() == null || subSalAmtBinding.getValue().toString().length() == 0)
        {
            String cid = subSalAmtBinding.getClientId();
            message = "Please enter amount";
            showMessage(message, cid);
            return false;
        }
        return true;
    }

    public void closeSubSalPopAction(ActionEvent actionEvent)
    {
        DCIteratorBinding subcom = ADFBeanUtils.findIterator("OrgHcmEmpSalSub1Iterator");
        if (subcom.getEstimatedRowCount() > 0)
        {
            if (chkSalSubValidation())
            {
                OperationBinding opchk = ADFBeanUtils.getOperationBinding("calTotalSubDedAmnt");
                opchk.execute();
                multiTempCompPopBinding.hide();
            }
        }
        else
        {
            OperationBinding opchk = ADFBeanUtils.getOperationBinding("calTotalSubDedAmnt");
            opchk.execute();
            multiTempCompPopBinding.hide();
        }
    }


    public void deletSubComAction(ActionEvent actionEvent)
    {
        ADFBeanUtils.getOperationBinding("Delete").execute();
    }

    public void setSubSalIdBinding(RichSelectOneChoice subSalIdBinding)
    {
        this.subSalIdBinding = subSalIdBinding;
    }

    public RichSelectOneChoice getSubSalIdBinding()
    {
        return subSalIdBinding;
    }

    public void setSubSalAmtBinding(RichInputText subSalAmtBinding)
    {
        this.subSalAmtBinding = subSalAmtBinding;
    }

    public RichInputText getSubSalAmtBinding()
    {
        return subSalAmtBinding;
    }


    public void setTotlAmtForTempComBinding(RichInputText totlAmtForTempComBinding)
    {
        this.totlAmtForTempComBinding = totlAmtForTempComBinding;
    }

    public RichInputText getTotlAmtForTempComBinding()
    {
        return totlAmtForTempComBinding;
    }

    public void setSalIdForTempComBinding(RichSelectOneChoice salIdForTempComBinding)
    {
        this.salIdForTempComBinding = salIdForTempComBinding;
    }

    public RichSelectOneChoice getSalIdForTempComBinding()
    {
        return salIdForTempComBinding;
    }

    public void setAmtforTempComBinding(RichInputText amtforTempComBinding)
    {
        this.amtforTempComBinding = amtforTempComBinding;
    }

    public RichInputText getAmtforTempComBinding()
    {
        return amtforTempComBinding;
    }

    public void setMultiTempCompPopBinding(RichPopup multiTempCompPopBinding)
    {
        this.multiTempCompPopBinding = multiTempCompPopBinding;
    }

    public RichPopup getMultiTempCompPopBinding()
    {
        return multiTempCompPopBinding;
    }

    public void AmountVC(ValueChangeEvent valueChangeEvent)
    {
        if (valueChangeEvent.getNewValue() != null)
        {
            totlAmtForTempComBinding.setValue(valueChangeEvent.getNewValue());
        }
    }

    public void subSalIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            OperationBinding opchk = ADFBeanUtils.getOperationBinding("chkSubSalCompAction");
            opchk.getParamsMap().put("subSalId", object.toString());
            opchk.execute();
            if (opchk.getErrors().isEmpty())
            {
                if (opchk.getResult().toString().equals("N"))
                {
                    showFacesMessage("Duplicate Sub salary component.", "E", false, "V");
                }
            }
            //AdfFacesContext.getCurrentInstance().addPartialTarget(subSalCompTbleBinding);
        }
    }

    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg)
    {
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true)
        {
            String msg = (String) resolvEl("#{bundle['" + mesg + "']}");
            message = new FacesMessage(msg);
        }
        if (sev.equalsIgnoreCase("E"))
        {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        }
        else if (sev.equalsIgnoreCase("W"))
        {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
        }
        else if (sev.equalsIgnoreCase("I"))
        {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        else
        {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        if (typFlg.equals("F"))
        {
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        else if (typFlg.equals("V"))
        {
            throw new ValidatorException(message);
        }
    }

    public Object resolvEl(String data)
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, data, Object.class);
        return exp.getValue(elContext);
    }

    public void setIsOthrSubComBinding(RichInputText isOthrSubComBinding)
    {
        this.isOthrSubComBinding = isOthrSubComBinding;
    }

    public RichInputText getIsOthrSubComBinding()
    {
        return isOthrSubComBinding;
    }

    public void salIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            OperationBinding opchk = ADFBeanUtils.getOperationBinding("chkSalIdAction");
            opchk.getParamsMap().put("salId", object.toString());
            opchk.execute();
            if (opchk.getErrors().isEmpty())
            {
                if (opchk.getResult().toString().equals("N"))
                {
                    showFacesMessage("Duplicate temporary salary component.", "E", false, "V");
                }
            }
        }
    }

    public void salAmttValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        String msg = "";
        if (object != null)
        {
            if (ADFBeanUtils.isPrecisionValid(26, 6, (Number) object))
            {
                if (((Number) object).compareTo(new Number(0)) <= 0)
                {
                    msg = "Amount is not valid.";
                    showFacesMessage(msg, "E", false, "V");
                }
            }
            else
            {
                msg = "Amount is not valid.";
                showFacesMessage(msg, "E", false, "V");
            }

        }
    }

    public void resetTempSal(ActionEvent actionEvent)
    {
        OperationBinding op = ADFBeanUtils.getOperationBinding("resetMonTemComVo");
        op.execute();
    }

    public void searchTempSal(ActionEvent actionEvent)
    {
        OperationBinding op = ADFBeanUtils.getOperationBinding("searchTempCompAction");
        op.execute();
    }


    public void setEmployeeDocIdBinding(RichInputText employeeDocIdBinding)
    {
        this.employeeDocIdBinding = employeeDocIdBinding;
    }

    public RichInputText getEmployeeDocIdBinding()
    {
        return employeeDocIdBinding;
    }


    public void EmpNmVC(ValueChangeEvent valueChangeEvent)
    {
        AdfFacesContext.getCurrentInstance().addPartialTarget(employeeDocIdBinding);
    }

    public void empNmVlidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0 && getDelMode().equals("T"))
        {
            OperationBinding op = ADFBeanUtils.getOperationBinding("chkIsTempCompEditable");
            op.execute();
            String message = "";
            if (op.getErrors().isEmpty())
            {

                if (op.getResult().equals("-1"))
                {
                    message =
                        ADFModelUtils.resolvRsrc("MSG.2765"); //Can't create new temporary salary component:This month Salary has been already process for this employee.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                    //showinfoMessage(message, null);
                }
                else if (op.getResult().equals("0"))
                {
                    message =
                        ADFModelUtils.resolvRsrc("MSG.2765"); //Can't create new temporary salary component:This month Salary has been already process for this employee.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                    //showinfoMessage(message, null);
                }
                else if (op.getResult().equals("1"))
                {

                }
            }
        }

    }

    public void empNmFrMulttAttValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0 && getMdeFrValidator().equals("T"))
        {
            OperationBinding op = ADFBeanUtils.getOperationBinding("chkIsMultiAttEditable");
            op.execute();
            String message = "";
            if (op.getErrors().isEmpty())
            {

                if (op.getResult().equals("-1"))
                {
                    message =
                        ADFModelUtils.resolvRsrc("MSG.2766"); //Can't create new attendence:This month Salary has been already process for this employee.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                    //showinfoMessage(message, null);
                }
                else if (op.getResult().equals("0"))
                {
                    message =
                        ADFModelUtils.resolvRsrc("MSG.2766"); //Can't create new attendence:This month Salary has been already process for this employee.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                    //showinfoMessage(message, null);
                }
                else if (op.getResult().equals("1"))
                {
                    System.out.println("salary not yet process or pending");
                }
            }
        }
    }


    public void empNmFrDummyMulttAttValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0 && getMdeFrValidator().equals("T"))
        {
            OperationBinding op = ADFBeanUtils.getOperationBinding("chkIsMultiAttEditable");
            op.execute();
            String message = "";
            if (op.getErrors().isEmpty())
            {

                if (op.getResult().equals("-1"))
                {
                    message =
                        ADFModelUtils.resolvRsrc("MSG.2766"); //Can't create new attendence:This month Salary has been already process for this employee.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                    //showinfoMessage(message, null);
                }
                else if (op.getResult().equals("0"))
                {
                    message =
                        ADFModelUtils.resolvRsrc("MSG.2766"); //Can't create new attendence:This month Salary has been already process for this employee.
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                    //showinfoMessage(message, null);
                }
                else if (op.getResult().equals("1"))
                {
                    System.out.println("salary not yet process or pending");
                }
            }
        }
    }

    public void empNmMultiAttVC(ValueChangeEvent valueChangeEvent)
    {

        AdfFacesContext.getCurrentInstance().addPartialTarget(dummyEmpDocIdBinding);
        //        AdfFacesContext.getCurrentInstance().addPartialTarget(empNmBinding);
    }

    public void exportExcelActionListener(FacesContext facesContext, OutputStream outputStream)
    {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);


        HSSFRow createRow = sheet.createRow(0);
        for (int i = 0; i <= 28; i++)
        {
            Cell cell = createRow.createCell(i);
            switch (i)
            {
            case 0:
                cell.setCellValue("DOC_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 1:
                cell.setCellValue("EMP_CODE");
                cell.setCellStyle(cellStyle);
                break;
            case 2:
                cell.setCellValue("SAL_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 3:
                cell.setCellValue("SAL_VAL");
                cell.setCellStyle(cellStyle);
                break;
            case 4:
                cell.setCellValue("SAL_BEHAV");
                cell.setCellStyle(cellStyle);
                break;
            case 5:
                cell.setCellValue("SAL_AMT");
                cell.setCellStyle(cellStyle);
                break;
            case 6:
                cell.setCellValue("EMP_DEPT_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 7:
                cell.setCellValue("EMP_LOC_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 8:
                cell.setCellValue("EMP_GRP_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 9:
                cell.setCellValue("PROC_FRM_DT");
                cell.setCellStyle(cellStyle);
                break;
            case 10:
                cell.setCellValue("PROC_TO_DT");
                cell.setCellStyle(cellStyle);
                break;
            case 11:
                cell.setCellValue("OTHR_DED_CHK");
                cell.setCellStyle(cellStyle);
                break;
            case 12:
                cell.setCellValue("EMP_DOC_ID");
                cell.setCellStyle(cellStyle);
                break;
            }
        }
        String OrgId = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String HoOrgId = (String) resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String CldId = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        DCIteratorBinding dcItr = ADFBeanUtils.findIterator("HcmEmpMonSalCompIterator");
        ViewObjectImpl object = (ViewObjectImpl) dcItr.getViewObject();
        RowQualifier rq = new RowQualifier(object);
        rq.setWhereClause("CldId='" + CldId + "' and SlocId=" + SlocId + " and HoOrgId='" + HoOrgId + "' and OrgId='" +
                          OrgId + "'");
        Row[] fr = object.getFilteredRows(rq);
        int rownum = 1;
        for (Row next : fr)
        {
            // Row next = itr.next();
            HSSFRow row = sheet.createRow(rownum++);
            Object empDocIdO = next.getAttribute("DocId");
            Object empCodeO = next.getAttribute("EmpCode");
            Object salIdO = next.getAttribute("SalId");
            Object salValO = next.getAttribute("SalVal");
            Object salBehavO = next.getAttribute("SalBehav");
            Object salAmtO = next.getAttribute("SalAmt");
            Object empDeptIdO = next.getAttribute("EmpDeptId");
            Object empLocIdO = next.getAttribute("EmpLocId");
            Object empGrpIdO = next.getAttribute("EmpGrpId");
            Object procFrmDtO = next.getAttribute("ProcFrmDt");
            Object procToDtO = next.getAttribute("ProcToDt");
            Object othrDedChkO = next.getAttribute("OthrDedChk");
            Object empDocId2 = next.getAttribute("EmpDocId");
            if (next.getAttribute("ProcFrmDt") != null && next.getAttribute("ProcFrmDt").toString().length() > 0)
            {
                procFrmDtO = getConvertTimeStampToStr(next.getAttribute("ProcFrmDt").toString());
            }
            if (next.getAttribute("ProcToDt") != null && next.getAttribute("ProcToDt").toString().length() > 0)
            {
                procToDtO = getConvertTimeStampToStr(next.getAttribute("ProcToDt").toString());
            }

            StringBuilder empDocId =
                (empDocIdO == null ? new StringBuilder("") : new StringBuilder(empDocIdO.toString()));
            StringBuilder empCode = (empCodeO == null ? new StringBuilder("") : new StringBuilder(empCodeO.toString()));
            StringBuilder salId = (salIdO == null ? new StringBuilder("") : new StringBuilder(salIdO.toString()));
            StringBuilder salVal = (salValO == null ? new StringBuilder("") : new StringBuilder(salValO.toString()));
            StringBuilder salAmt = (salAmtO == null ? new StringBuilder("") : new StringBuilder(salAmtO.toString()));
            StringBuilder salBehav =
                (salBehavO == null ? new StringBuilder("") : new StringBuilder(salBehavO.toString()));
            StringBuilder empDeptId =
                (empDeptIdO == null ? new StringBuilder("") : new StringBuilder(empDeptIdO.toString()));
            StringBuilder empLocId =
                (empLocIdO == null ? new StringBuilder("") : new StringBuilder(empLocIdO.toString()));
            StringBuilder empGrpId =
                (empGrpIdO == null ? new StringBuilder("") : new StringBuilder(empGrpIdO.toString()));
            StringBuilder procFrmDt =
                (procFrmDtO == null ? new StringBuilder("") : new StringBuilder(procFrmDtO.toString()));
            StringBuilder procToDt =
                (procToDtO == null ? new StringBuilder("") : new StringBuilder(procToDtO.toString()));
            StringBuilder othrDedChk =
                (othrDedChkO == null ? new StringBuilder("") : new StringBuilder(othrDedChkO.toString()));
            StringBuilder EmpDocId =
                (empDocId2 == null ? new StringBuilder("") : new StringBuilder(empDocId2.toString()));


            Cell cell0 = row.createCell(0);
            Cell cell1 = row.createCell(1);
            Cell cell2 = row.createCell(2);
            Cell cell3 = row.createCell(3);
            Cell cell4 = row.createCell(4);
            Cell cell5 = row.createCell(5);
            Cell cell6 = row.createCell(6);
            Cell cell7 = row.createCell(7);
            Cell cell8 = row.createCell(8);
            Cell cell9 = row.createCell(9);
            Cell cell10 = row.createCell(10);
            Cell cell11 = row.createCell(11);
            Cell cell12 = row.createCell(12);
            cell0.setCellValue(empDocId.toString());
            cell1.setCellValue(empCode.toString());
            cell2.setCellValue(salId.toString());
            cell3.setCellValue(salVal.toString());
            cell4.setCellValue(salBehav.toString());
            cell5.setCellValue(salAmt.toString());
            cell6.setCellValue(empDeptId.toString());
            cell7.setCellValue(empLocId.toString());
            cell8.setCellValue(empGrpId.toString());
            cell9.setCellValue(procFrmDt.toString());
            cell10.setCellValue(procToDt.toString());
            cell11.setCellValue(othrDedChk.toString());
            cell12.setCellValue(EmpDocId.toString());
            System.out.println("Row added ___________________");
            for (int i = 0; i <= 12; i++)
            {
                sheet.autoSizeColumn(i);
            }
        }

        try
        {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportMultipleAttendenceListener(FacesContext facesContext, OutputStream outputStream)
    {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);


        HSSFRow createRow = sheet.createRow(0);
        for (int i = 0; i <= 12; i++)
        {
            Cell cell = createRow.createCell(i);


            switch (i)
            {
            case 0:
                cell.setCellValue("EmpDocId");
                cell.setCellStyle(cellStyle);
                break;
            case 1:
                cell.setCellValue("AttenDt");
                cell.setCellStyle(cellStyle);
                break;
            case 2:
                cell.setCellValue("DedCh");
                cell.setCellStyle(cellStyle);
                break;

            case 3:
                cell.setCellValue("EmpId");
                cell.setCellStyle(cellStyle);
                break;
            case 4:
                cell.setCellValue("ExtraTimeHr");
                cell.setCellStyle(cellStyle);
                break;
            case 5:
                cell.setCellValue("HlfdayLeaveChk");
                cell.setCellStyle(cellStyle);
                break;

            case 6:
                cell.setCellValue("InTime");
                cell.setCellStyle(cellStyle);
                break;
            case 7:
                cell.setCellValue("OutTime"); //HlfdayLeaveChk
                cell.setCellStyle(cellStyle);
                break;

            case 8:
                cell.setCellValue("LeaveId"); //HlfdayLeaveChk
                cell.setCellStyle(cellStyle);
                break;
            case 9:
                cell.setCellValue("WkOffChk");
                cell.setCellStyle(cellStyle);
                break;

            case 10:
                cell.setCellValue("EMP_DEPT_ID"); //InTime
                cell.setCellStyle(cellStyle);
                break;
            case 11:
                cell.setCellValue("EMP_LOC_ID"); //AttenDt//DedCh//EmpId//ExtraTimeHr//HlfdayLeaveChk//InTime//LeaveId   OutTime // WkOffChk
                cell.setCellStyle(cellStyle);
                break;
            case 12:
                cell.setCellValue("EMP_GRP_ID");
                cell.setCellStyle(cellStyle);
                break;
            case 13:
                cell.setCellValue("ADD_COMP_LEAVE_CHK");
                cell.setCellStyle(cellStyle);
                break;
            }
        }
        String OrgId = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String HoOrgId = (String) resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String CldId = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        DCIteratorBinding dcItr = ADFBeanUtils.findIterator("HcmTimeMoveEditDtlFrMultAttIterator");
        ViewObjectImpl object = (ViewObjectImpl) dcItr.getViewObject();
        RowQualifier rq = new RowQualifier(object);
        rq.setWhereClause("CldId='" + CldId + "' and SlocId=" + SlocId + " and HoOrgId='" + HoOrgId + "' and OrgId='" +
                          OrgId + "'");
        Row[] fr = object.getFilteredRows(rq);
        int rownum = 1;
        for (Row next : fr)
        {

            HSSFRow row = sheet.createRow(rownum++);
            Object EmpDocIdO = next.getAttribute("EmpDocId");
            Object AttenDtO = next.getAttribute("AttenDt");
            Object dedCHO = next.getAttribute("DedCh");
            Object EmpIdO = next.getAttribute("EmpId");
            Object ExtraTimeHrO = next.getAttribute("ExtraTimeHr");
            Object HlfdayLeaveChkO = next.getAttribute("HlfdayLeaveChk");
            Object InTimeO = next.getAttribute("InTime");
            Object OutTimeO = next.getAttribute("OutTime");
            Object LeaveIdO = next.getAttribute("LeaveId");
            Object WkOffChkO = next.getAttribute("WkOffChk");
            Object empDeptIdO = next.getAttribute("EmpDeptId");
            Object empLocIdO = next.getAttribute("EmpLocId");
            Object empGrpIdO = next.getAttribute("EmpGrpId");
            Object addCompChko = next.getAttribute("AddCompLeaveChk");


            if (next.getAttribute("AttenDt") != null && next.getAttribute("AttenDt").toString().length() > 0)
            {
                AttenDtO = getConvertTimeStampToStr(next.getAttribute("AttenDt").toString());
            }

            StringBuilder empDocId =
                (EmpDocIdO == null ? new StringBuilder("") : new StringBuilder(EmpDocIdO.toString()));
            StringBuilder AttenDt = (AttenDtO == null ? new StringBuilder("") : new StringBuilder(AttenDtO.toString()));
            StringBuilder empId = (EmpIdO == null ? new StringBuilder("") : new StringBuilder(EmpIdO.toString()));
            StringBuilder dedCH = (dedCHO == null ? new StringBuilder("") : new StringBuilder(dedCHO.toString()));
            StringBuilder ExtraTimeHr =
                (ExtraTimeHrO == null ? new StringBuilder("") : new StringBuilder(ExtraTimeHrO.toString()));
            StringBuilder HlfdayLeaveChk =
                (HlfdayLeaveChkO == null ? new StringBuilder("") : new StringBuilder(HlfdayLeaveChkO.toString()));
            StringBuilder InTime = (InTimeO == null ? new StringBuilder("") : new StringBuilder(InTimeO.toString()));
            StringBuilder OutTime = (OutTimeO == null ? new StringBuilder("") : new StringBuilder(OutTimeO.toString()));
            StringBuilder LeaveId = (LeaveIdO == null ? new StringBuilder("") : new StringBuilder(LeaveIdO.toString()));
            StringBuilder WkOffChk =
                WkOffChkO == null ? new StringBuilder("") : new StringBuilder(WkOffChkO.toString());
            StringBuilder empDeptId =
                (empDeptIdO == null ? new StringBuilder("") : new StringBuilder(empDeptIdO.toString()));
            StringBuilder empLocId =
                (empLocIdO == null ? new StringBuilder("") : new StringBuilder(empLocIdO.toString()));
            StringBuilder empGrpId =
                (empGrpIdO == null ? new StringBuilder("") : new StringBuilder(empGrpIdO.toString()));
            StringBuilder addCompChk =
                (addCompChko == null ? new StringBuilder("") : new StringBuilder(addCompChko.toString()));

            Cell cell0 = row.createCell(0);
            Cell cell1 = row.createCell(1);
            Cell cell2 = row.createCell(2);
            Cell cell3 = row.createCell(3);
            Cell cell4 = row.createCell(4);
            Cell cell5 = row.createCell(5);
            Cell cell6 = row.createCell(6);
            Cell cell7 = row.createCell(7);
            Cell cell8 = row.createCell(8);
            Cell cell9 = row.createCell(9);
            Cell cell10 = row.createCell(10);
            Cell cell11 = row.createCell(11);
            Cell cell12 = row.createCell(12);
            Cell cell13 = row.createCell(13);

            cell0.setCellValue(empDocId.toString());
            cell1.setCellValue(AttenDt.toString());
            cell2.setCellValue(dedCH.toString());
            cell3.setCellValue(empId.toString());
            cell4.setCellValue(ExtraTimeHr.toString());
            cell5.setCellValue(HlfdayLeaveChk.toString());
            cell6.setCellValue(InTime.toString());
            cell7.setCellValue(OutTime.toString());
            cell8.setCellValue(LeaveId.toString());
            cell9.setCellValue(WkOffChk.toString());
            cell10.setCellValue(empDeptId.toString());
            cell11.setCellValue(empLocId.toString());
            cell12.setCellValue(empGrpId.toString());
            cell13.setCellValue(addCompChk.toString());
            System.out.println("Row added ___________________");
            for (int i = 0; i <= 13; i++)
            {
                sheet.autoSizeColumn(i);
            }
        }

        try
        {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getConvertTimeStampToStr(String str)
    {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        //Format to match actual String to parse
        Date dt = null;
        try
        {
            dt = format.parse(str);
        }
        catch (ParseException e) {
            System.out.println("Exception Caught=" + e.getStackTrace());
        }
        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-YYY");
        // System.out.println(newFormat.format(dt));
        return newFormat.format(dt);
    }

    public void markContinuesAttendenceAction(ActionEvent actionEvent)
    {
        setMultiAttSwitchMode("multi");
        ADFBeanUtils.getOperationBinding("setCriteriaValuesInDummyMultiAtt").execute();
        setADD_EDIT_MODE("E");
        setMdeFrValidator("T");
    }

    public String InsertMultipleAttendence()
    {
        if (getMultiAttSwitchMode().equals("multi"))
        {

            boolean result = chkSveMultAttValidationForMultiAttDummyVo();

            if (result == true)
            {

                if (dummyToDtBinding.getValue() != null && dummyFromDateBinding.getValue() != null)
                {
                    java.sql.Date fromdt = null;
                    java.sql.Date todt = null;
                    String frmDtNew = null;
                    String todtNew = null;
                    try
                    {
                        fromdt = ((Timestamp) dummyFromDateBinding.getValue()).dateValue();
                        todt = ((Timestamp) dummyToDtBinding.getValue()).dateValue();
                        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                        frmDtNew = df.format(fromdt);
                        todtNew = df.format(todt);

                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    OperationBinding ob = ADFBeanUtils.getOperationBinding("insertMultipleAttenceRow");
                    ob.getParamsMap().put("fromDt", frmDtNew);
                    ob.getParamsMap().put("ToDt", todtNew);
                    ob.execute();
                    if (ob.getErrors().isEmpty() && ob.getResult() != null)
                    {
                        if (ob.getResult().equals("Y"))
                            return "Yes";
                    }
                }
            }
            else
            {
                return "validError";
            }


        }
        return "No";
    }


    public void DummyFromDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            if (dummyToDtBinding.getValue() != null)
            {
                java.sql.Date fromdt = null;
                java.sql.Date todt = null;
                String frmDtNew = null;
                String todtNew = null;
                String rslt = "";
                try
                {
                    fromdt = ((Timestamp) object).dateValue();
                    todt = ((Timestamp) dummyToDtBinding.getValue()).dateValue();
                    DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    frmDtNew = df.format(fromdt);
                    todtNew = df.format(todt);

                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                OperationBinding op = ADFBeanUtils.getOperationBinding("validateAttFromNdToDt");
                op.getParamsMap().put("attnDt", fromdt);
                op.execute();
                if (op.getErrors().isEmpty() && op.getResult() != null)
                {
                    rslt = op.getResult().toString();
                    if (rslt.equalsIgnoreCase("Y"))
                    {
                        String msg = ADFModelUtils.resolvRsrc("MSG.2761"); //Please select date in between date range.
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                    }
                    else if (rslt.equalsIgnoreCase("N"))
                    {
                        OperationBinding ob = ADFBeanUtils.getOperationBinding("setNumberOfDays");
                        ob.getParamsMap().put("fromDt", frmDtNew);
                        ob.getParamsMap().put("ToDt", todtNew);
                        ob.execute();
                    }
                }

            }
        }
    }


    public boolean validateToDate(Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (dummyFromDateBinding.getValue() != null && dummyFromDateBinding.getValue().toString().length() > 0)
            {
                try
                {
                    strtDt = ((Timestamp) dummyFromDateBinding.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                }
                catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0)
                {
                    if (strtDt.toString().equals(endDt.toString()))
                    {

                    }
                    else
                    {
                        showFacesMessage("Please select valid to date", "E", false, "V");
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public void dummyToDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object)
    {
        if (object != null && object.toString().length() > 0)
        {
            if (validateToDate(object))
            {
                if (dummyFromDateBinding.getValue() != null)
                {
                    java.sql.Date fromdt = null;
                    java.sql.Date todt = null;
                    String frmDtNew = null;
                    String todtNew = null;
                    String rslt = "";
                    try
                    {
                        fromdt = ((Timestamp) dummyFromDateBinding.getValue()).dateValue();
                        todt = ((Timestamp) object).dateValue();
                        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                        frmDtNew = df.format(fromdt);
                        todtNew = df.format(todt);
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    OperationBinding op = ADFBeanUtils.getOperationBinding("validateAttFromNdToDt");
                    op.getParamsMap().put("attnDt", todt);
                    op.execute();
                    if (op.getErrors().isEmpty() && op.getResult() != null)
                    {
                        rslt = op.getResult().toString();
                        if (rslt.equalsIgnoreCase("Y"))
                        {
                            String msg =
                                ADFModelUtils.resolvRsrc("MSG.2761"); //Please select date in between date range.
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                        }
                        else if (rslt.equalsIgnoreCase("N"))
                        {
                            OperationBinding ob = ADFBeanUtils.getOperationBinding("setNumberOfDays");
                            ob.getParamsMap().put("fromDt", frmDtNew);
                            ob.getParamsMap().put("ToDt", todtNew);
                            ob.execute();
                        }
                    }

                }
            }

        }
    }

    public void setDummyFromDateBinding(RichInputDate dummyFromDateBinding)
    {
        this.dummyFromDateBinding = dummyFromDateBinding;
    }

    public RichInputDate getDummyFromDateBinding()
    {
        return dummyFromDateBinding;
    }

    public void setDummyToDtBinding(RichInputDate dummyToDtBinding)
    {
        this.dummyToDtBinding = dummyToDtBinding;
    }

    public RichInputDate getDummyToDtBinding()
    {
        return dummyToDtBinding;
    }

    public void setDummyNumBerOfDayBinding(RichInputText dummyNumBerOfDayBinding)
    {
        this.dummyNumBerOfDayBinding = dummyNumBerOfDayBinding;
    }

    public RichInputText getDummyNumBerOfDayBinding()
    {
        return dummyNumBerOfDayBinding;
    }

    public void deleteDummyMultiAttAction(ActionEvent actionEvent)
    {
        clearDummyVo();
        String message = "";
        message = ADFModelUtils.resolvRsrc("MSG.2767"); //Row deleted successfully.
        setMultiAttSwitchMode("single");
        setMdeFrValidator("D");
        System.out.println("Switch mode is" + getMultiAttSwitchMode());
        AdfFacesContext.getCurrentInstance().addPartialTarget(switchingModeBinding);
        showinfoMessage(message, null);
    }


    public void setDummyInTimeBinding(RichInputText dummyInTimeBinding)
    {
        this.dummyInTimeBinding = dummyInTimeBinding;
    }

    public RichInputText getDummyInTimeBinding()
    {
        return dummyInTimeBinding;
    }

    public void setDummyOutTimeBinding(RichInputText dummyOutTimeBinding)
    {
        this.dummyOutTimeBinding = dummyOutTimeBinding;
    }

    public RichInputText getDummyOutTimeBinding()
    {
        return dummyOutTimeBinding;
    }

    public void setDummyDedchBinding(RichSelectOneChoice dummyDedchBinding)
    {
        this.dummyDedchBinding = dummyDedchBinding;
    }

    public RichSelectOneChoice getDummyDedchBinding()
    {
        return dummyDedchBinding;
    }

    public void setDummyLeaveIdBinding(RichSelectOneChoice dummyLeaveIdBinding)
    {
        this.dummyLeaveIdBinding = dummyLeaveIdBinding;
    }

    public RichSelectOneChoice getDummyLeaveIdBinding()
    {
        return dummyLeaveIdBinding;
    }

    public void setDummyWeekOfChk(RichSelectBooleanCheckbox dummyWeekOfChk)
    {
        this.dummyWeekOfChk = dummyWeekOfChk;
    }

    public RichSelectBooleanCheckbox getDummyWeekOfChk()
    {
        return dummyWeekOfChk;
    }

    public void setDummyHalfDayBinding(RichSelectBooleanCheckbox dummyHalfDayBinding)
    {
        this.dummyHalfDayBinding = dummyHalfDayBinding;
    }

    public RichSelectBooleanCheckbox getDummyHalfDayBinding()
    {
        return dummyHalfDayBinding;
    }

    public void setDummyExtraTimeBinding(RichInputText dummyExtraTimeBinding)
    {
        this.dummyExtraTimeBinding = dummyExtraTimeBinding;
    }

    public RichInputText getDummyExtraTimeBinding()
    {
        return dummyExtraTimeBinding;
    }

    public void setDummyEmpDocIdBinding(RichInputText dummyEmpDocIdBinding)
    {
        this.dummyEmpDocIdBinding = dummyEmpDocIdBinding;
    }

    public RichInputText getDummyEmpDocIdBinding()
    {
        return dummyEmpDocIdBinding;
    }

    public void setSwitchingModeBinding(RichOutputText switchingModeBinding)
    {
        this.switchingModeBinding = switchingModeBinding;
    }

    public RichOutputText getSwitchingModeBinding()
    {
        return switchingModeBinding;
    }

    public void exportAttSampleActionListener(FacesContext facesContext, OutputStream outputStream)
    {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        HSSFComment comment1 =
            patriarch.createComment(new HSSFClientAnchor(100, 100, 100, 100, (short) 1, 1, (short) 6, 5));
        comment1.setString(new HSSFRichTextString("Enter time in 24 hours only !"));
        System.out.println(comment1.getFillColor());
        comment1.setFillColor(245, 255, 224);
        comment1.setLineStyleColor(10, 10, 10);
        HSSFComment comment2 =
            patriarch.createComment(new HSSFClientAnchor(100, 100, 100, 100, (short) 1, 1, (short) 6, 5));
        comment2.setString(new HSSFRichTextString("Enter time in 24 hours only !"));
        System.out.println(comment1.getFillColor());
        comment2.setFillColor(245, 255, 224);
        comment2.setLineStyleColor(10, 10, 10);
        //comment1.setVisible(true);

        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);

        HSSFRow createRow = sheet.createRow(0);
        for (int i = 0; i <= 6; i++)
        {
            Cell cell = createRow.createCell(i);
            switch (i)
            {
            case 0:
                cell.setCellValue("Employee Id");
                cell.setCellStyle(cellStyle);
                break;
            case 1:
                cell.setCellValue("Employee Name");
                cell.setCellStyle(cellStyle);
                break;
            case 2:
                cell.setCellValue("Attendance Date");
                cell.setCellStyle(cellStyle);
                break;
            case 3:
                cell.setCellValue("Leave Name");
                cell.setCellStyle(cellStyle);
                break;
            case 4:
                cell.setCellValue("In Time");
                cell.setCellStyle(cellStyle);
                cell.setCellComment(comment1);
                break;
            case 5:
                cell.setCellValue("Out Time");
                cell.setCellStyle(cellStyle);
                cell.setCellComment(comment2);
                break;
            case 6:
                cell.setCellValue("Extra Time Hour");
                cell.setCellStyle(cellStyle);
                break;

            }
        }
        //createRow.getCell(4).setCellComment(comment1);
        //createRow.getCell(5).setCellComment(comment2);

        System.out.println("Row added ___________________");
        for (int i = 0; i <= 6; i++)
        {
            sheet.autoSizeColumn(i);
        }


        try
        {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String downloadSampleAction()
    {
        return null;
    }

    public String empEncashLevAction()
    {
        return "goToEnchLvPage";
    }

    public void setQtrLeaveChkBinding(RichSelectBooleanCheckbox qtrLeaveChkBinding)
    {
        this.qtrLeaveChkBinding = qtrLeaveChkBinding;
    }

    public RichSelectBooleanCheckbox getQtrLeaveChkBinding()
    {
        return qtrLeaveChkBinding;
    }

    public void setQtrLeaveChkonMultiBinding(RichSelectBooleanCheckbox qtrLeaveChkonMultiBinding)
    {
        this.qtrLeaveChkonMultiBinding = qtrLeaveChkonMultiBinding;
    }

    public RichSelectBooleanCheckbox getQtrLeaveChkonMultiBinding()
    {
        return qtrLeaveChkonMultiBinding;
    }

    public void setCompOffBinding(RichSelectOneChoice compOffBinding)
    {
        this.compOffBinding = compOffBinding;
    }

    public RichSelectOneChoice getCompOffBinding()
    {
        return compOffBinding;
    }

    public void setAttDateRangeBinding(RichInputDate attDateRangeBinding)
    {
        this.attDateRangeBinding = attDateRangeBinding;
    }

    public RichInputDate getAttDateRangeBinding()
    {
        return attDateRangeBinding;
    }

    public void setAttDateRangeEndBinding(RichInputDate attDateRangeEndBinding)
    {
        this.attDateRangeEndBinding = attDateRangeEndBinding;
    }

    public RichInputDate getAttDateRangeEndBinding()
    {
        return attDateRangeEndBinding;
    }
}

