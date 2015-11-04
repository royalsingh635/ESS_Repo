package slsdailycallapp.view.bean;

import adf.utils.bean.ADFBeanUtils;
import adf.utils.bean.StaticValue;
import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import java.math.BigDecimal;

import java.util.Date;

import java.sql.SQLException;

import java.sql.Types;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import javax.faces.component.UIComponent;
import javax.faces.validator.ValidatorException;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.JboPrecisionScaleValidator;
import oracle.jbo.server.ViewObjectImpl;

import slsdailycallapp.model.services.DailyCallAMImpl;


public class SLSDailyCallDetailsBean {

    /**
     * Mode :
     * V = View Mode
     * A = Create Mode
     * E = Edit
     */
    private Timestamp currDate;
    private RichInputDate nextDateBind;
    private RichInputDate expectedDateBind;
    private Timestamp docDt;
    private RichInputDate docDtBind;

    /**
     * @param exptdDate
     */
    public void setDocDt(Timestamp docDt) {
        this.docDt = docDt;
    }

    public Timestamp getDocDt() {
        if (docDtBind.getValue() != null) {
            docDt = StaticValue.getTruncatedDt((Timestamp)docDtBind.getValue());
        }
        return docDt;
    }

    public void setCurrDate(Timestamp currDate) {
        this.currDate = currDate;
    }

    public Timestamp getCurrDate() {
        if (currDate == null) {
           long s =  StaticValue.getCurrDtWidTimestamp().getTime()+86400000;
            currDate = StaticValue.getTruncatedDt(new Timestamp(s));
            System.out.println("Curr Dt : "+currDate);
        }
        return currDate;
    }
    private StringBuffer mode = new StringBuffer("V");
    private static boolean pass = false;

    public SLSDailyCallDetailsBean() {
    }

    /**
     * Save Action
     * @param actionEvent
     */
    public void saveACTION(ActionEvent actionEvent) {
        System.out.println("Precision is save action" + pass);
        OperationBinding chk = this.getBindings().getOperationBinding("EoIdCheck");
        chk.execute();
        if (chk == null || (Boolean) chk.getResult() == true) {

            FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1729"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } /* else if (pass) {
            System.out.println("Precision is save if " + pass);
            FacesMessage message = new FacesMessage("Invalid Precision in Expected closure Amount!!! ");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } */ else {
            OperationBinding binding = this.getBindings().getOperationBinding("saveAction");
            binding.execute();
            Object r = binding.getResult();
            //System.out.println("Returnrd : "+r);
            if ((Boolean) r) {

                OperationBinding operationBinding = this.getBindings().getOperationBinding("Commit");
                operationBinding.execute();
                if (operationBinding.getErrors().isEmpty()) {
                    String mes = ADFModelUtils.resolvRsrc("MSG.1260");

                    FacesMessage message = new FacesMessage(mes);
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    mode = new StringBuffer("V");
                } else {
                    String mes1 = ADFModelUtils.resolvRsrc("MSG.1261");
                    FacesMessage message = new FacesMessage(mes1);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
        }
    }

    /**
     * Add Action
     * @param actionEvent
     */
    public void addACTION(ActionEvent actionEvent) {
        OperationBinding operationBinding = this.getBindings().getOperationBinding("CreateInsert");
        operationBinding.execute();
        mode = new StringBuffer("A");
    }

    public void addFromOutSide() {
        mode = new StringBuffer("A");
    }

    /**
     * Edit Action
     * @param actionEvent
     */
    public void editACTION(ActionEvent actionEvent) {
        mode = new StringBuffer("E");
    }

    /**
     * Cancel Action
     * @return
     */
    public String cancelACTION() {
        this.getBindings().getOperationBinding("Rollback").execute();
        mode = new StringBuffer("V");
        return "goBack";
    }

    /**  method to get Bindings
     */
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     * Back
     * @return
     */

    public String goBackACTION() {
        mode = new StringBuffer("V");
        return "goBack";
    }

    public void setMode(StringBuffer mode) {
        this.mode = mode;
    }

    public StringBuffer getMode() {
        return mode;
    }

    /**
     * Validator to check negative value of fields
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void negativeNumberVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /*  if (object != null) {

            int i = ((Number)object).compareTo((Object)new Number(0));
            if (i == -1) {
                FacesMessage message =
                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.827']}").toString() + "!", resolvElDCMsg("#{bundle['MSG.828']}").toString() +
                                     "!"); //Invalid number! && Cannot enter negative value
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }

        } */
        if (object != null) {
            int i = ((Number) object).compareTo((Object) new Number(0));

            boolean b = isPrecisionValid(26, 6, (Number) object);
            System.out.println("Precision is " + b);
            // System.out.println("b " + b);
            if (i == -1) {
                FacesMessage message =
                    new FacesMessage(ADFModelUtils.resolvRsrc("MSG.827") + "!",
                                     ADFModelUtils.resolvRsrc("MSG.828") +
                                     "!"); //Invalid number! && Cannot enter negative value
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
            if (b) {
                pass = false;
                System.out.println("Precision is in if " + b);
            } else {
                pass = true;
                System.out.println("Precision is else " + b);
                throw new ValidatorException(new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1266")));
            }

        }

    }

    public Boolean isPrecisionValid(int precision, int scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }

    public Timestamp getCurrDt() {
        Timestamp dt = new Timestamp(System.currentTimeMillis());
        Date date;
        try {
            date = dt.dateValue();
            dt = new Timestamp(date);
        } catch (SQLException e) {
        }
        return dt;
    }

    /**
     * Validator to check negative value of fields
     * @param facesContext
     * @param uIComponent
     * @param object
     */
    public void currentDateVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

        }
    }

    public void newEntityPopListener(DialogEvent dialogEvent) {
        // Add event code here...
        System.out.println("-----------------dialog");
        Object execute = getBindings().getOperationBinding("insertEoName").execute();
        if (execute != null && (Integer) execute == 1) {
            FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1779"));
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            FacesMessage message = new FacesMessage(ADFModelUtils.resolvRsrc("MSG.1780"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void setEoID(ValueChangeEvent valueChangeEvent) {
        getBindings().getOperationBinding("setEoId").execute();
    }

    public Object resolvEl(String data) {
        return ADFModelUtils.resolvEl(data);
    }

    public Integer getSlocId() {
        return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
    }

    public String getCldId() {
        return (resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString());
    }

    public Integer getUserId() {
        return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
    }

    public String getOrgId() {
        return resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
    }

    public String getHoOrgId() {
        return resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
    }

    public Integer getCurrDigit() {
        return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_CURR_DIGIT}").toString());
    }

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        return ADFModelUtils.callFunction((DailyCallAMImpl) resolvElDC("DailyCallAMDataControl"),
                                          new StringBuilder(stmt), bindVars, sqlReturnType);
    }

    public String AddsIdSetter() {
        System.out.println("IN Address---->");
        Integer eoId = null;
        String adds_Id_value = resolvEl("#{pageFlowScope.AddId}").toString();

        String address_value = resolvEl("#{pageFlowScope.RetAddress}").toString();
        System.out.println("Add Id-->" + adds_Id_value);
        DailyCallAMImpl am = (DailyCallAMImpl) resolvElDC("DailyCallAMDataControl");
        ViewObjectImpl dcVO = am.getSlsDcVO();
        ViewObjectImpl addsVO1 = am.getLovEoAddsVO1();

        addsVO1.getFilteredRows("AddsId", adds_Id_value);
        if (dcVO.getCurrentRow() != null) {
            if (dcVO.getCurrentRow().getAttribute("EoId") != null) {

                eoId = Integer.parseInt(dcVO.getCurrentRow().getAttribute("EoId").toString());
                /* addsVO1.setNamedWhereClauseParam("CldIdBind", EbizParams.GLBL_APP_CLD_ID());
                addsVO1.setNamedWhereClauseParam("SlocIdBind", EbizParams.GLBL_APP_SERV_LOC());
                addsVO1.setNamedWhereClauseParam("HoOrgIdBind", EbizParams.GLBL_HO_ORG_ID());
                addsVO1.setNamedWhereClauseParam("EoIdBind",eoId);
                Row[] filteredRows = addsVO1.getFilteredRows("AddsId", adds_Id_value);
                if(filteredRows.length>0){
                    ADFModelUtils. showFormattedFacesMessage("The Selected Address is already present in the Selected List!",
                                                             "Please Select Another Address to Attach!", FacesMessage.SEVERITY_ERROR);
                }
                else{ */
                am.getTransaction().postChanges();
                try {
                    BigDecimal exec =
                        (BigDecimal) callStoredFunction(Types.NUMERIC, "APP.fn_ins_eo_adds(?,?,?,?,?,?,?)", new Object[] {
                                                        getCldId(), getSlocId(), getHoOrgId(), getOrgId(), eoId,
                                                        adds_Id_value, getUserId()
                    });
                    System.out.println("Exec is " + exec);
                } catch (Exception e) {
                    ADFModelUtils.showFormattedFacesMessage("There Must be Some Error in Calling APP.fn_ins_eo_adds Function!",
                                                            "", FacesMessage.SEVERITY_ERROR);
                    e.printStackTrace();
                }


            }
        }


        return "topage";
    }

    public Object resolvElDC(String data) {
        return ADFModelUtils.resolvEl("#{data." + data + ".dataProvider}");
        /* FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext); */
    }

    public void nextDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        /*  System.out.println("dates are: " + object + "--> " + expectedDateBind.getValue());
        if (object != null && expectedDateBind.getValue() != null) {
            if (object.toString().equalsIgnoreCase(expectedDateBind.getValue().toString())) {
                System.out.println("Dates are same..");
            } else {
                Timestamp t=(Timestamp)expectedDateBind.getValue();
                Timestamp t2=(Timestamp)object;
                // Date d = new Date(t.dateValue().toString());
                try {
                    t = new Timestamp(t.dateValue() + " 00:00:00");
                    t2=new Timestamp(t2.dateValue()+"00:00:00");
                } catch (SQLException e) {
                }
                System.out.println("Value of Date-->" + t);
                SimpleDateFormat nxtDt = new SimpleDateFormat("dd-MM-yyyy");
                Date d1 = null, d2 = null;

                    d1 = new Date(t2.toString());
                   d2=new Date(t.toString());
                    System.out.println("Now .. " + d1 + "== " + d2);
                    if (d1.compareTo(d2) > 0) {

                        FacesMessage message =
                            new FacesMessage("Next Date Must be equal or less than the expected Date!!"); //Invalid number! && Cannot enter negative value
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(message);
                    } else {
                        System.out.println("Yes it is less..");
                    }

            } */

    }
    /* if (object != null) {
            String nxtDate = object.toString();
          Date d2=null;
            Date d1=null;
            System.out.println("Nxt Date is" + nxtDate);
            SimpleDateFormat nxtDt= new SimpleDateFormat("dd-MM-yyyy");
            try {
            d1 =(Date)  nxtDt.parse(nxtDate);
                System.out.println("Parse Date"+d1);
            } catch (ParseException e) {
            }
            System.out.println("Nxt DAte" + nxtDt);
            String exptedDt = null;
            if (expectedDateBind.getValue() != null) {
                exptedDt = expectedDateBind.getValue().toString();
                try {
             d2  =(Date)nxtDt.parse(exptedDt);
                    System.out.println("parse expected date"+d2);
                } catch (ParseException e) {
                }
            //    System.out.println("Expted  DAte" + exptedDt);

            }
            if (d2 != null) {

                String exptDt = expectedDateBind.getValue().toString();
                if (nxtDate.equals(exptedDt)) {
                        System.out.println("equal");
                } else {
                    if (d1.compareTo(d2) > 0) {
                        FacesMessage message =
                            new FacesMessage("Next Date Must be equal or less than the expected Date!!"); //Invalid number! && Cannot enter negative value
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(message);
                    } else {

                    }
                }
            }
        } */


    public void setNextDateBind(RichInputDate nextDateBind) {
        this.nextDateBind = nextDateBind;
    }

    public RichInputDate getNextDateBind() {
        return nextDateBind;
    }

    public void setExpectedDateBind(RichInputDate expectedDateBind) {
        this.expectedDateBind = expectedDateBind;
    }

    public RichInputDate getExpectedDateBind() {
        return expectedDateBind;
    }

    public void ExpectedDtVCL(ValueChangeEvent valueChangeEvent) {
        nextDateBind.setValue(null);
    }

    public void emailValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String name = object.toString();
            String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            CharSequence inputStr = null;
            if (!name.equals(" ")) {
                inputStr = name;
            }
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String msg = ADFModelUtils.resolvRsrc("MSG.283");//Email is not in Proper Format
            if (matcher.matches()) {

            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }

    }

    public void contactNoValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
    }

    public void setDocDtBind(RichInputDate docDtBind) {
        this.docDtBind = docDtBind;
    }

    public RichInputDate getDocDtBind() {
        return docDtBind;
    }
}
