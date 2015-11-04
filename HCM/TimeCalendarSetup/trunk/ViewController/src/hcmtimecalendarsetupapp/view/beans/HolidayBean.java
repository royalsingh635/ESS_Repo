package hcmtimecalendarsetupapp.view.beans;

import adf.utils.bean.ADFBeanUtils;

import java.math.BigDecimal;

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
import javax.faces.validator.ValidatorException;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;

import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;

public class HolidayBean {
    private RichSelectOneRadio recurBinding;
    private UIXSwitcher switcherBinding;
    private String Mode = "V";
    private RichSelectOneChoice holiTypeBinding;
    private RichSelectBooleanCheckbox beforeChkBinding;
    private RichSelectBooleanCheckbox afterChkBinding;
    private RichInputText yearBinding;
    private RichSelectOneChoice orgBinding;
    private RichInputListOfValues locationBinding;
    private RichTreeTable treeTblBinding;
    private RichInputText holiIdBinding;
    private RichInputText holidayNameBinding;

    public void setMode(String Mode) {
        this.Mode = Mode;
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public String getMode() {
        return Mode;
    }

    public HolidayBean() {
    }


    DCIteratorBinding parentIter = ADFBeanUtils.findIterator("Holiday1Iterator");
    Key parentKey = null;

    public void createHolidayAL(ActionEvent actionEvent) {
        Object execute = ADFBeanUtils.getOperationBinding("chkDocumentInOrg").execute();
        if (execute != null && execute.equals(true)) {
            if (parentIter.getCurrentRow() != null) {
                parentKey = parentIter.getCurrentRow().getKey();
            }
            System.out.println("row key at create--" + parentKey);
            ADFBeanUtils.getOperationBinding("CreateInsert").execute();
            ADFBeanUtils.getOperationBinding("refrechRefHoliday").execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(switcherBinding);
            this.Mode = "A";
        } else {
            //  FacesMessage message = new FacesMessage(resolvElDCMsg("This Document has not been added in the Organization !!").toString());
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1868']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    public void setRecurBinding(RichSelectOneRadio recurBinding) {
        this.recurBinding = recurBinding;
    }

    public RichSelectOneRadio getRecurBinding() {
        return recurBinding;
    }

    public void setSwitcherBinding(UIXSwitcher switcherBinding) {
        this.switcherBinding = switcherBinding;
    }

    public UIXSwitcher getSwitcherBinding() {
        return switcherBinding;
    }

    public void editBtnAL(ActionEvent actionEvent) {
        if (parentIter.getCurrentRow() != null) {
            parentKey = parentIter.getCurrentRow().getKey();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(switcherBinding);
        this.Mode = "E";

    }

    public void saveBtnAL(ActionEvent actionEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(recurBinding);
        System.out.println("before--" + beforeChkBinding.getValue());
        System.out.println("after--" + afterChkBinding.getValue());
        if (holiTypeBinding.getValue() != null && holiTypeBinding.getValue().toString().length() > 0) {
            if (recurBinding.getValue() != null && recurBinding.getValue().toString().length() > 0) {
                if (((Integer) recurBinding.getValue()).compareTo(37) == 0 &&
                    beforeChkBinding.getValue().toString().equalsIgnoreCase("false") &&
                    afterChkBinding.getValue().toString().equalsIgnoreCase("false")) {
                    System.out.println("inside ref check");
                    //FacesMessage message = new FacesMessage("Please select at least one (Before/After).");
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1440']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_WARN);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                } else {
                    if (yearBinding.getValue() != null && yearBinding.getValue().toString().length() > 0) {
                        if (this.Mode == "A") {
                            System.out.println("adding new holiday going to genrate Id");
                            ADFBeanUtils.getOperationBinding("genHolidayId").execute();
                        }
                        parentKey = parentIter.getCurrentRow().getKey();
                        System.out.println("before savingkey is" + parentKey);
                        ADFBeanUtils.getOperationBinding("Commit").execute();
                        ADFBeanUtils.getOperationBinding("refrechRefHoliday").execute();
                        ADFBeanUtils.getOperationBinding("refreshTreeTbl").execute();
                        ADFBeanUtils.getOperationBinding("srchReset").execute();
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                        this.Mode = "V";
                        //  FacesMessage message = new FacesMessage("Holiday saved successfully.");
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1441']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else {
                        //FacesMessage message = new FacesMessage("Year is mandatory.");
                        FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1442']}").toString());
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(yearBinding.getClientId(), message);
                    }
                }
            } else {
                //FacesMessage message = new FacesMessage("Recurrence pattern is mandatory.");
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1443']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(recurBinding.getClientId(), message);
            }

        } else {
            //FacesMessage message = new FacesMessage("Holiday type is mandatory.");
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1444']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(holiTypeBinding.getClientId(), message);

        }
    }

    public void cancelBtnAL(ActionEvent actionEvent) {

        ADFBeanUtils.getOperationBinding("Rollback").execute();
        ADFBeanUtils.getOperationBinding("Execute").execute();
        System.out.println("row key at cancel--" + parentKey);
        System.out.println("at cancel holiId is--" + holiIdBinding.getValue());
        if (parentKey != null && holiIdBinding.getValue() != null && holiIdBinding.getValue().toString().length() > 0) {
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(switcherBinding);
        this.Mode = "V";
    }

    public void recurRadioVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            System.out.println("inside vcl---" + valueChangeEvent.getNewValue());
            System.out.println("recur value is---" + recurBinding.getValue());
            if (valueChangeEvent.getNewValue().equals(34)) {
                switcherBinding.setFacetName("34");
            } else if (valueChangeEvent.getNewValue().equals(35)) {

                switcherBinding.setFacetName("35");
            } else if (valueChangeEvent.getNewValue().equals(36)) {

                switcherBinding.setFacetName("36");
            } else if (valueChangeEvent.getNewValue().equals(37)) {

                switcherBinding.setFacetName("37");
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(switcherBinding);
        }
    }

    public void searchBtnAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("searchHoliday").execute();
        if (recurBinding.getValue() != null && recurBinding.getValue().toString().length() > 0) {
            switcherBinding.setFacetName(recurBinding.getValue().toString());
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(switcherBinding);
    }

    public void resetBtnAL(ActionEvent actionEvent) {
        ADFBeanUtils.getOperationBinding("srchReset").execute();
        if (recurBinding.getValue() != null && recurBinding.getValue().toString().length() > 0) {
            switcherBinding.setFacetName(recurBinding.getValue().toString());
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(switcherBinding);
    }

    public void holidayNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            object = object.toString().trim();
            String pt = "([a-zA-Z0-9_]+)(([ ])([a-zA-Z0-9_]+))*";
            Pattern ptt = null;
            ptt = ptt.compile(pt);
            Matcher matcher = ptt.matcher(object.toString());
            if (!matcher.matches()) {
                // String msg = "Not a valid name.Special character/spaces not allowed!";
                String msg = resolvElDCMsg("#{bundle['MSG.1399']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
            String Nm = object.toString().trim();
            OperationBinding op = ADFBeanUtils.getOperationBinding("duplicateHolidayNameValid");
            op.getParamsMap().put("HoliNm", Nm);
            op.execute();
            if (op.getErrors().isEmpty() && op.getResult() != null) {
                String rslt = op.getResult().toString();
                if (rslt.equalsIgnoreCase("Y")) {
                    // String msg = "Holiday name already exists.";
                    String msg = resolvElDCMsg("#{bundle['MSG.1445']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        } else {
            //String msg = "Holiday name is mandatory.";
            String msg = resolvElDCMsg("#{bundle['MSG.1446']}").toString();
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        }

    }

    public void yearValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            Pattern pattern = null;
            final String yearPattern = "^[1-9][0-9][0-9][0-9]$";
            pattern = pattern.compile(yearPattern);
            Matcher matcher = pattern.matcher(object.toString());
            if (object.equals(0)) {
                //String msg = "Invalid Year, Can not be zero.";
                String msg = resolvElDCMsg("#{bundle['MSG.1447']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            } else if ((Integer) object < 0) {
                //String msg = "Invalid Year, Can not be negative.";
                String msg = resolvElDCMsg("#{bundle['MSG.1448']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }

            else if (!matcher.matches()) {
                //String msg = "Invalid Year!";
                String msg = resolvElDCMsg("#{bundle['MSG.1449']}").toString();
                System.out.println("year match or not--" + matcher.matches());
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        } /*else {
            String msg = "Year is mandatory.";
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        }*/

    }

    public void setHoliTypeBinding(RichSelectOneChoice holiTypeBinding) {
        this.holiTypeBinding = holiTypeBinding;
    }

    public RichSelectOneChoice getHoliTypeBinding() {
        return holiTypeBinding;
    }

    public void weekdayCountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (recurBinding.getValue() != null) {
            System.out.println("recur binding is---" + recurBinding.getValue());
            if (((Integer) recurBinding.getValue()).compareTo(34) == 0 && (object == null || object == "")) {
                System.out.println("weekdaycount---" + object);
                //String msg = "Week day count is mandatory.";
                String msg = resolvElDCMsg("#{bundle['MSG.1450']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }

    public void weekDayValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (recurBinding.getValue() != null) {
            System.out.println("recur binding is---" + recurBinding.getValue());
            if (((Integer) recurBinding.getValue()).compareTo(34) == 0 && (object == null || object == "")) {
                System.out.println("week day value is --" + object);
                // String msg = "Week day is mandatory.";
                String msg = resolvElDCMsg("#{bundle['MSG.1451']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }

    public void weeklyWeekDayValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (recurBinding.getValue() != null) {
            System.out.println("recur binding is---" + recurBinding.getValue());
            if (((Integer) recurBinding.getValue()).compareTo(35) == 0 && (object == null || object == "")) {
                System.out.println("weekdaycount---" + object);
                //String msg = "Week day is mandatory.";
                String msg = resolvElDCMsg("#{bundle['MSG.1451']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }

    public void randomHoliDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (recurBinding.getValue() != null) {
            System.out.println("recur binding is---" + recurBinding.getValue());
            if (((Integer) recurBinding.getValue()).compareTo(36) == 0 && (object == null || object == "")) {
                System.out.println("date---" + object);
                //String msg = "Holiday Date is mandatory.";
                String msg = resolvElDCMsg("#{bundle['MSG.1452']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
            OperationBinding op = ADFBeanUtils.getOperationBinding("randomHolDateInYearValid");
            op.getParamsMap().put("randomHolDt", object);
            op.getParamsMap().put("year", yearBinding.getValue());
            op.execute();
            if (op.getErrors().isEmpty() && op.getResult() != null) {
                String rslt = op.getResult().toString();
                if (rslt.equalsIgnoreCase("N")) {
                    //String msg = "Please select a date within the year!";
                    String msg = resolvElDCMsg("#{bundle['MSG.1453']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }
    }

    public void setBeforeChkBinding(RichSelectBooleanCheckbox beforeChkBinding) {
        this.beforeChkBinding = beforeChkBinding;
    }

    public RichSelectBooleanCheckbox getBeforeChkBinding() {
        return beforeChkBinding;
    }

    public void setAfterChkBinding(RichSelectBooleanCheckbox afterChkBinding) {
        this.afterChkBinding = afterChkBinding;
    }

    public RichSelectBooleanCheckbox getAfterChkBinding() {
        return afterChkBinding;
    }

    public void beforeDaysCountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer obj = (Integer) object;
            if (obj.compareTo(new Integer(0)) < 0) {
                String msg = "Negative value not allowed!";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
        if (recurBinding.getValue() != null) {

            //System.out.println("recur binding is---" + recurBinding.getValue());
            if (((Integer) recurBinding.getValue()).compareTo(37) == 0 && (object == null || object == "") &&
                beforeChkBinding.getValue().equals(true)) {
                System.out.println("date---" + object);
                //String msg = "Before day count is mandatory.";
                String msg = resolvElDCMsg("#{bundle['MSG.1454']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }


    public void afterDaysCountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer obj = (Integer) object;
            if (obj.compareTo(new Integer(0)) < 0) {
                String msg = "Negative value not allowed!";
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }

        if (recurBinding.getValue() != null) {
            System.out.println("recur binding is---" + recurBinding.getValue());
            if (((Integer) recurBinding.getValue()).compareTo(37) == 0 && (object == null || object == "") &&
                afterChkBinding.getValue().equals(true)) {
                System.out.println("date---" + object);
                //String msg = "After day count is mandatory.";
                String msg = resolvElDCMsg("#{bundle['MSG.1455']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }

    public void referedHolidayValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (recurBinding.getValue() != null) {
            System.out.println("recur binding is---" + recurBinding.getValue());
            if (((Integer) recurBinding.getValue()).compareTo(37) == 0 && (object == null || object == "")) {
                System.out.println("date---" + object);
                // String msg = "Referred holiday is mandatory.";
                String msg = resolvElDCMsg("#{bundle['MSG.1456']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }

    public void setYearBinding(RichInputText yearBinding) {
        this.yearBinding = yearBinding;
    }

    public RichInputText getYearBinding() {
        return yearBinding;
    }

    public static Object invokeEL(String el, Class[] paramTypes, Object[] params) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        MethodExpression exp = expressionFactory.createMethodExpression(elContext, el, Object.class, paramTypes);

        return exp.invoke(elContext, params);
    }

    public static Object invokeEL(String el) {
        return invokeEL(el, new Class[0], new Object[0]);
    }

    public static Object evaluateEL(String el) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);

        return exp.getValue(elContext);
    }

    public void holTblSelectionListener(SelectionEvent selectionEvent) {
        invokeEL("#{bindings.Holiday1.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class }, new Object[] {
                 selectionEvent });
        Row selectedRow = (Row) evaluateEL("#{bindings.Holiday1Iterator.currentRow}");
        if (selectedRow.getAttribute("HolRecur") != null &&
            selectedRow.getAttribute("HolRecur").toString().length() > 0) {
            Object attribute = selectedRow.getAttribute("HolRecur");
            System.out.println("attribute--" + attribute);
            switcherBinding.setFacetName(attribute.toString());
        }
        System.out.println("in tbl selection");
        AdfFacesContext.getCurrentInstance().addPartialTarget(switcherBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(holiIdBinding);
    }

    public void linkToOrgLocAL(ActionEvent actionEvent) {
        if (orgBinding.getValue() != null && orgBinding.getValue().toString().length() > 0) {
            if (locationBinding.getValue() != null && locationBinding.getValue().toString().length() > 0) {
                OperationBinding op = ADFBeanUtils.getOperationBinding("orgLocValidator");
                op.execute();
                if (op.getErrors().isEmpty() && op.getResult() != null) {
                    String rslt = op.getResult().toString();
                    if (rslt.equalsIgnoreCase("Y")) {
                        //  String msg = "Duplicate record exists!";
                        String msg = resolvElDCMsg("#{bundle['MSG.375']}").toString();
                        FacesMessage message = new FacesMessage(msg);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(locationBinding.getClientId(), message);
                    } else {
                        ADFBeanUtils.getOperationBinding("linkToOrgloc").execute();
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                    AdfFacesContext.getCurrentInstance().addPartialTarget(treeTblBinding);

                }
            } else {
                //FacesMessage message = new FacesMessage("Location is mandatory.");
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1457']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(locationBinding.getClientId(), message);
            }
        } else {
            //FacesMessage message = new FacesMessage("Organization is mandatory.");
            FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.1458']}").toString());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(orgBinding.getClientId(), message);
        }
    }

    public void setOrgBinding(RichSelectOneChoice orgBinding) {
        this.orgBinding = orgBinding;
    }

    public RichSelectOneChoice getOrgBinding() {
        return orgBinding;
    }

    public void setLocationBinding(RichInputListOfValues locationBinding) {
        this.locationBinding = locationBinding;
    }

    public RichInputListOfValues getLocationBinding() {
        return locationBinding;
    }

    public void setTreeTblBinding(RichTreeTable treeTblBinding) {
        this.treeTblBinding = treeTblBinding;
    }

    public RichTreeTable getTreeTblBinding() {
        return treeTblBinding;
    }

    public void linkToOrgDL(DisclosureEvent disclosureEvent) {
        System.out.println("inside disclosure listener");
        if (parentIter.getCurrentRow() != null) {
            parentKey = parentIter.getCurrentRow().getKey();
        }
        System.out.println("parent key is --" + parentKey);
    }

    public void setHoliIdBinding(RichInputText holiIdBinding) {
        this.holiIdBinding = holiIdBinding;
    }

    public RichInputText getHoliIdBinding() {
        return holiIdBinding;
    }

    public void holidayTypeVCL(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(recurBinding);
    }

    public void setHolidayNameBinding(RichInputText holidayNameBinding) {
        this.holidayNameBinding = holidayNameBinding;
    }

    public RichInputText getHolidayNameBinding() {
        return holidayNameBinding;
    }

    public void holidayNameVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            holidayNameBinding.setValue(valueChangeEvent.getNewValue().toString().trim());
            AdfFacesContext.getCurrentInstance().addPartialTarget(holidayNameBinding);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(recurBinding);
    }

    public void replicateToAllBtnAL(ActionEvent actionEvent) {
        OperationBinding binding = ADFBeanUtils.getOperationBinding("replicateAll");
        binding.execute();
        if (binding.getErrors().isEmpty() && binding.getResult() != null) {
            BigDecimal rslt = (BigDecimal) binding.getResult();
            if (rslt.compareTo(new BigDecimal(1)) == 0) {
                String msg = "Replicated successfully!!Press save to commit the changes.";
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        }
    }
}
