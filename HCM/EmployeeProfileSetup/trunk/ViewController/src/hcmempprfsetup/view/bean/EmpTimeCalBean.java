package hcmempprfsetup.view.bean;

import java.awt.Color;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import java.util.Set;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputColor;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import oracle.adf.view.rich.util.CalendarActivityRamp;
import oracle.adf.view.rich.util.InstanceStyles;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.JboPrecisionScaleValidator;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;


import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;

public class EmpTimeCalBean {
    private RichTable shiftTableBinding;
    private HashMap activityStyles = new HashMap<Set<String>, InstanceStyles>();
    private RichInputDate previewStDtBinding;


    public EmpTimeCalBean() {
    }
    BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();

    public String addTimeCalAction() {
        //RequestContext.getCurrentInstance().getPageFlowScope().put("ADD_EDIT_MODE", "A");
        return null;
    }

    public String shiftUpAction() {
        OperationBinding opShift = bindings.getOperationBinding("updateShiftSeq");
        opShift.getParamsMap().put("act", "U");
        opShift.execute();

        DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();


        DCIteratorBinding departmentsIterator = (DCIteratorBinding) dcBindings.get("HcmEmpShiftIterator");
        RowSetIterator rsi = departmentsIterator.getRowSetIterator();

        Row dragRow = rsi.getCurrentRow();
        Integer seq = (Integer) dragRow.getAttribute("ShiftSeq");
        Row[] row = rsi.getFilteredRows("ShiftSeq", seq - 1);
        Row dropRow = null;
        if (row.length > 0)
            dropRow = row[0];
        if (dropRow == null)
            return null;
        System.out.println("Setting order up");

        int indexOfDropRow = rsi.getRangeIndexOf(dropRow);
        //remove dragged row from collection so it can be added back
        dragRow.removeAndRetain();

        rsi.insertRowAtRangeIndex(indexOfDropRow, dragRow);
        //make row current in ADF iterator.
        departmentsIterator.setCurrentRowIndexInRange(indexOfDropRow);

        //ppr the table
        AdfFacesContext adfctx = AdfFacesContext.getCurrentInstance();
        //note that the refresh of the table didn't work when refreshing the table
        //so I needed to refresh the container component (af:panelStretchLayout).
        adfctx.addPartialTarget(shiftTableBinding.getParent());
        rsi.closeRowSetIterator();

        return null;
    }

    public String shiftDownAction() {
        OperationBinding opShift = bindings.getOperationBinding("updateShiftSeq");
        opShift.getParamsMap().put("act", "D");
        opShift.execute();
        DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding departmentsIterator = (DCIteratorBinding) dcBindings.get("HcmEmpShiftIterator");
        RowSetIterator rsi = departmentsIterator.getRowSetIterator();

        Row dragRow = rsi.getCurrentRow();
        Integer seq = (Integer) dragRow.getAttribute("ShiftSeq");
        Row[] row = rsi.getFilteredRows("ShiftSeq", seq + 1);
        Row dropRow = null;
        if (row.length > 0)
            dropRow = row[0];
        if (dropRow == null)
            return null;
        System.out.println("Setting order down");
        int indexOfDropRow = rsi.getRangeIndexOf(dropRow);
        //remove dragged row from collection so it can be added back
        dragRow.removeAndRetain();

        rsi.insertRowAtRangeIndex(indexOfDropRow, dragRow);
        //make row current in ADF iterator.
        departmentsIterator.setCurrentRowIndexInRange(indexOfDropRow);
        AdfFacesContext adfctx = AdfFacesContext.getCurrentInstance();
        adfctx.addPartialTarget(shiftTableBinding.getParent());
        rsi.closeRowSetIterator();
        return null;
    }

    public void addShiftAction(ActionEvent actionEvent) {

        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        //access the name of the iterator the table is bound to. Its "allDepartmentsIterator"
        //in this sample
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("HcmEmpShiftIterator");
        //access the underlying RowSetIterator
        RowSetIterator rsi = dciter.getRowSetIterator();
        //get handle to the last row
        Row lastRow = rsi.last();
        //obtain the index of the last row
        int lastRowIndex = rsi.getRangeIndexOf(lastRow);
        //create a new row
        Row newRow = rsi.createRow();
        //initialize the row
        newRow.setNewRowState(Row.STATUS_INITIALIZED);
        //add row to last index + 1 so it becomes last in the range set
        rsi.insertRowAtRangeIndex(lastRowIndex + 1, newRow);
        //make row the current row so it is displayed correctly
        rsi.setCurrentRow(newRow);
        /* OperationBinding op=bindings.getOperationBinding("CreateInsert1");
        op.execute(); */
        String OrgId = (String) resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String HoOrgId = (String) resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String CldId = (String) resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String empdocId = (String) resolvEl("#{pageFlowScope.emp_doc_id}");
        rsi.getCurrentRow().setAttribute("CldId", CldId);
        rsi.getCurrentRow().setAttribute("SlocId", SlocId);
        rsi.getCurrentRow().setAttribute("HoOrgId", HoOrgId);
        rsi.getCurrentRow().setAttribute("OrgId", OrgId);
        rsi.getCurrentRow().setAttribute("DocId", empdocId);
        OperationBinding opSeq = bindings.getOperationBinding("nextSeqNoGen");
        opSeq.execute();
        RequestContext.getCurrentInstance().getPageFlowScope().put("ADD_EDIT_MODE", "S");
        Integer seq = 0;
        if (opSeq.getResult() != null) {
            seq = (Integer) opSeq.getResult();
        }
        System.out.println("New Seq No.=" + seq);
    }

    public DnDAction onRowDropAction(DropEvent dropEvent) {
        //get the table instance. This information is later used
        //to determine the tree binding and the iterator binding
        RichTable table = (RichTable) dropEvent.getDragComponent();
        List dropRowKey = (List) dropEvent.getDropSite();
        //if no dropsite then drop area was not a data area
        if (dropRowKey == null) {
            return DnDAction.NONE;
        }

        Transferable t = dropEvent.getTransferable();

        DataFlavor<RowKeySet> df = DataFlavor.getDataFlavor(RowKeySet.class, "rowmove");
        RowKeySet rks = t.getData(df);
        Iterator iter = rks.iterator();

        //for this use case the re-order of rows is one-by-one, which means that the rowKeySet
        //should only contain a single entry. If it contains more then still we only look at a
        //singe (first) row key entry
        List draggedRowKey = (List) iter.next();

        //get access to the oracle.jbo.Row instance represneting this table row
        System.out.println("Table = " + table);
        System.out.println("dragged row key=" + draggedRowKey);

        Object draggeRowNode = table.getRowData(draggedRowKey);
        Row dragRow = (Row) draggeRowNode;

        Object dropRowObject = table.getRowData(dropRowKey);
        Row dropRow = (Row) dropRowObject;

        Integer a = (Integer) dragRow.getAttribute("ShiftSeq");
        Integer b = (Integer) dropRow.getAttribute("ShiftSeq");

        dragRow.setAttribute("ShiftSeq", b);
        dropRow.setAttribute("ShiftSeq", a);

        //get the table's ADF JUCtrlHierBinding
        CollectionModel collectionModel = (CollectionModel) table.getValue();
        JUCtrlHierBinding treeBinding = (JUCtrlHierBinding) collectionModel.getWrappedData();

        //get access to the ADF iterator binding used by the table and the underlying RowSetIterator.
        //The RowSetIterator allows us to remove and re-instert the dragged row
        DCIteratorBinding departmentsIterator = treeBinding.getDCIteratorBinding();
        RowSetIterator rsi = departmentsIterator.getRowSetIterator();

        int indexOfDropRow = rsi.getRangeIndexOf(dropRow);
        //remove dragged row from collection so it can be added back
        dragRow.removeAndRetain();

        rsi.insertRowAtRangeIndex(indexOfDropRow, dragRow);
        //make row current in ADF iterator.
        departmentsIterator.setCurrentRowIndexInRange(indexOfDropRow);

        //ppr the table
        AdfFacesContext adfctx = AdfFacesContext.getCurrentInstance();
        //note that the refresh of the table didn't work when refreshing the table
        //so I needed to refresh the container component (af:panelStretchLayout).
        adfctx.addPartialTarget(table.getParent());

        return DnDAction.MOVE;
    }

    public void setShiftTableBinding(RichTable shiftTableBinding) {
        this.shiftTableBinding = shiftTableBinding;
    }

    public RichTable getShiftTableBinding() {
        return shiftTableBinding;
    }

    public void colorVCE(ValueChangeEvent valueChangeEvent) {

    }


    public String genPreviewAction() {
        DCBindingContainer dcbind = (DCBindingContainer) bindings;
        boolean isDirty = dcbind.getDataControl().isTransactionModified();
        System.out.println("Dirty in time Cal: " + isDirty);
        bindings.getOperationBinding("deleteView").execute();
        OperationBinding op = bindings.getOperationBinding("callFuncForCalendar");
        op.getParamsMap().put("flg", isDirty);
        op.execute();
        return null;
    }


    public void setActivityStyles(HashMap activityStyles) {
        this.activityStyles = activityStyles;
    }

    //code to change color of activity in calendar
    public HashMap getActivityStyles() {
        activityStyles.clear();
        DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding shiftIterator = (DCIteratorBinding) dcBindings.get("HcmEmpShiftIterator");
        Row[] rsi = shiftIterator.getAllRowsInRange();
        ArrayList<HashSet<String>> arrList = new ArrayList<HashSet<String>>();
        int i = 0;
        for (Row r : rsi) {
            if (r.getAttribute("ShiftId") != null && r.getAttribute("ShiftClrCd") != null) {

                HashSet hset = new HashSet<String>();
                hset.add(r.getAttribute("ShiftId").toString());
                arrList.add(i, hset);
                String[] str = null;
                if (r.getAttribute("ShiftClrCd") != null)
                    str = r.getAttribute("ShiftClrCd").toString().split(",");
                activityStyles.put(arrList.get(i),
                                   CalendarActivityRamp.getActivityRamp(new Color(Integer.parseInt(str[0]),
                                                                                  Integer.parseInt(str[1]),
                                                                                  Integer.parseInt(str[2]))));
                i++;
            }
        }

        return activityStyles;
    }

    public String editTimeCalAction() {
        OperationBinding opchkProc = bindings.getOperationBinding("isSalaryProcPending");
        opchkProc.execute();
        if (opchkProc.getErrors() != null && opchkProc.getResult().toString().equals("N")) {
            RequestContext.getCurrentInstance().getPageFlowScope().put("ADD_EDIT_MODE", "A");
        } else {
            showFacesMessage("Salary Process of this Employee is Pending.", "I", false, "F");
        }


        return null;
    }

    public void shiftStrtDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            OperationBinding opChkdt = bindings.getOperationBinding("validateShiftStartDate");
            java.sql.Date shiftDt = null;
            try {
                shiftDt = ((Timestamp) object).dateValue();
            } catch (SQLException e) {
                System.out.println("Exception in cast");
            }
            opChkdt.getParamsMap().put("shiftDt", shiftDt);
            opChkdt.execute();
            if (opChkdt.getErrors().size() == 0 && opChkdt.getResult() != null &&
                opChkdt.getResult().toString().equals("Y")) {
            } else if (opChkdt.getErrors().size() == 0 && opChkdt.getResult() != null &&
                       opChkdt.getResult().toString().equals("X")) {
                String msg = "Date should be in between shift start date and end date in group profile.";
                showFacesMessage(msg, "E", false, "V");
            } else {
                showFacesMessage("MSG.1516", "E", true, "V");
                //showFacesMessage("", "E", false, "V");

            }
        }

    }

    public void deleteShiftAction(ActionEvent actionEvent) {
        //Update Serial No.
        System.out.println("Deleting rows");
        DCBindingContainer dcBindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding shiftIterator = (DCIteratorBinding) dcBindings.get("HcmEmpShiftIterator");
        Row curr = shiftIterator.getCurrentRow();
        RowSetIterator rsi = shiftIterator.getRowSetIterator();
        while (rsi.hasNext()) {
            Row r = rsi.next();
            System.out.println("shift =" + r.getAttribute("ShiftId"));
            System.out.println("shift seq =" + r.getAttribute("ShiftSeq"));
            Integer shiftSeq = (Integer) r.getAttribute("ShiftSeq");
            r.setAttribute("ShiftSeq", shiftSeq - 1);
        }
        // rsi.setCurrentRow(curr);
        rsi.closeRowSetIterator();
        curr.remove();
    }

    public void shiftIdValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        //check for duplicate shift
        if (object != null && object.toString().length() > 0) {
            OperationBinding opDupli = bindings.getOperationBinding("chkDupliShiftId");
            opDupli.getParamsMap().put("shiftId", object);
            opDupli.execute();
            if (opDupli.getErrors().size() == 0 && opDupli.getResult() != null &&
                opDupli.getResult().toString().equals("N")) {
            } else {
                showFacesMessage("MSG.1517", "E", true, "V");
            }
        }

    }

    public void shiftDaysValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            try {
                if (isPrecisionValid(3, 0, new Number(object))) {
                    if ((new Number(object)).compareTo(new Number(0)) <= 0) {
                        showFacesMessage("MSG.1518", "E", true, "V");
                    }
                } else
                    showFacesMessage("MSG.1519", "E", true, "V");
            } catch (SQLException e) {
            }
        }

    }


    /**
     *      Method to show validation message(I,E,W)
     *      mesg:Message to display
     *      sev:Severity(I-Info,E-Error,W-Warning)
     *      chk:true=if resource bundle is used; false=If Resource Bundle is not used.
     *      typFlg: 'F' for FacesMessage , 'V' for ValidatorException
     * */
    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg) {
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true) {
            String msg = (String) resolvEl("#{bundle['" + mesg + "']}");
            message = new FacesMessage(msg);
        }
        if (sev.equalsIgnoreCase("E")) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        } else if (sev.equalsIgnoreCase("W")) {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
        } else if (sev.equalsIgnoreCase("I")) {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        } else {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        if (typFlg.equals("F")) {
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else if (typFlg.equals("V")) {
            throw new ValidatorException(message);
        }
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    /**
     *      Method to Apply Precision Validation(P,S,V)
     *      P- Precision
     *      S- Scale
     *      V- Value to validate
     * */
    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public void weekoffValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        //check for duplicate weekoff
        if (object != null && object.toString().length() > 0) {
            OperationBinding op = bindings.getOperationBinding("chkWeekOffType");
            op.execute();
            if (op.getErrors().isEmpty() && op.getResult() != null) {
                String rslt = op.getResult().toString();
                if (rslt.equalsIgnoreCase("N")) {
                    String msg =
                        "Can't select fixed week off for the selected employee, week off for this employee will be marked as random!!";
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                } else {
                    OperationBinding opDupli = bindings.getOperationBinding("chkDupliWeekoffId");
                    opDupli.getParamsMap().put("weekoffId", object);
                    opDupli.execute();
                    if (opDupli.getErrors().size() == 0 && opDupli.getResult() != null &&
                        opDupli.getResult().toString().equals("N")) {
                    } else {
                        showFacesMessage("MSG.1520", "E", true, "V");
                    }
                }
            }
        }
    }

    public void previewEndDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            if (previewStDtBinding.getValue() != null && previewStDtBinding.getValue().toString().length() > 0) {
                try {
                    strtDt = ((Timestamp) previewStDtBinding.getValue()).dateValue();
                    endDt = ((Timestamp) object).dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strtDt.compareTo(endDt) > 0) {
                    if (strtDt.toString().equals(endDt.toString())) {
                    } else {
                        showFacesMessage("MSG.1521", "E", true, "V");
                    }
                }

            }
        }

    }

    public void setPreviewStDtBinding(RichInputDate previewStDtBinding) {
        this.previewStDtBinding = previewStDtBinding;
    }

    public RichInputDate getPreviewStDtBinding() {
        return previewStDtBinding;
    }

    public void prevStrtDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            java.sql.Date strtDt = null;
            java.sql.Date endDt = null;
            try {
                strtDt = ((Timestamp) object).dateValue();
            } catch (SQLException e) {
                System.out.println(e.getStackTrace());
            }

            OperationBinding opchk = bindings.getOperationBinding("chkPrevStDtValid");
            opchk.getParamsMap().put("stDt", strtDt);
            opchk.execute();
            if (opchk.getErrors().size() == 0 && opchk.getResult() != null && opchk.getResult().equals("Y")) {
            } else {
                showFacesMessage("MSG.1522", "E", true, "V");
            }

        }
    }

    public void shiftIdVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void colorCodeVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());

    }

    public void wekoffVCE(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void weekOffDeleteAction(ActionEvent actionEvent) {
        // Add event code here...
        bindings.getOperationBinding("Delete").execute();
        bindings.getOperationBinding("Execute").execute();
    }
}
