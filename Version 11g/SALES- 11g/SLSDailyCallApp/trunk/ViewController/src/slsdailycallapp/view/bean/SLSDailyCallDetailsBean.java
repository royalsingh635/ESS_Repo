package slsdailycallapp.view.bean;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;

import java.sql.Types;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import javax.faces.application.Application;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.component.UIComponent;
import javax.faces.validator.ValidatorException;

import oracle.jbo.JboException;
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
        if (chk == null || (Boolean)chk.getResult() == true) {
            String mes1 = "Please enter Customer/Prospect name. ";
            FacesMessage message = new FacesMessage(mes1);
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
            if ((Boolean)r) {
            
                OperationBinding operationBinding = this.getBindings().getOperationBinding("Commit");
                operationBinding.execute();
                if (operationBinding.getErrors().isEmpty()) {
                    String mes = resolvElDCMsg("#{bundle['MSG.1260']}").toString();

                    FacesMessage message = new FacesMessage(mes);
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    mode = new StringBuffer("V");
                } else {
                    String mes1 = resolvElDCMsg("#{bundle['MSG.1261']}").toString();
                    FacesMessage message = new FacesMessage(mes1);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
        }
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
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
            int i = ((Number)object).compareTo((Object)new Number(0));

            boolean b = isPrecisionValid(26, 6, (Number)object);
            System.out.println("Precision is " + b);
            // System.out.println("b " + b);
            if (i == -1) {
                FacesMessage message =
                    new FacesMessage(resolvElDCMsg("#{bundle['MSG.827']}").toString() + "!", resolvElDCMsg("#{bundle['MSG.828']}").toString() +
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
                throw new ValidatorException(new FacesMessage("invalid Precision!!"));
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
        if (execute != null && (Integer)execute == 1) {
            FacesMessage message = new FacesMessage("new entity inserted.");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            FacesMessage message = new FacesMessage("Entity with similar name already exists !");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void setEoID(ValueChangeEvent valueChangeEvent) {
        getBindings().getOperationBinding("setEoId").execute();
    }

    public Object resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext);
        return Message;
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
        CallableStatement st = null;
        DailyCallAMImpl am = (DailyCallAMImpl)resolvElDC("DailyCallAMDataControl");
        // System.out.println("__________________________________________begin");
        try {
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                    //System.out.println(bindVars[z]);
                }
            }
            st.executeUpdate();
            //System.out.println("__________________________________________end");
            return st.getObject(1);
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                }
            }
        }

    }

    public String AddsIdSetter() {
        System.out.println("IN Address---->");
        Integer eoId = null;
        String adds_Id_value = resolvEl("#{pageFlowScope.AddId}").toString();

        String address_value = resolvEl("#{pageFlowScope.RetAddress}").toString();
        System.out.println("Add Id-->" + adds_Id_value);
        DailyCallAMImpl am = (DailyCallAMImpl)resolvElDC("DailyCallAMDataControl");
        ViewObjectImpl dcVO = am.getSlsDcVO();

        if (dcVO.getCurrentRow() != null) {
            if (dcVO.getCurrentRow().getAttribute("EoId") != null) {
               
                eoId = Integer.parseInt(dcVO.getCurrentRow().getAttribute("EoId").toString());
                BigDecimal exec =
                    (BigDecimal)callStoredFunction(Types.NUMERIC, "APP.fn_ins_eo_adds(?,?,?,?,?,?,?)", new Object[] { getCldId(),
                                                                                                                      getSlocId(),
                                                                                                                      getHoOrgId(),
                                                                                                                      getOrgId(),
                                                                                                                      eoId,
                                                                                                                      adds_Id_value,
                                                                                                                      getUserId() });
                System.out.println("Exec is " + exec);                                                                 

            }
        }
       
       

        return "topage";
    }

    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }
}
