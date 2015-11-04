package appCostCenterSevice.view.bean;


import adf.utils.bean.ADFBeanUtils;
import adf.utils.ebiz.EbizParams;

import appCostCenterSevice.model.service.AppCostCenterServiceAMImpl;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.text.DecimalFormat;

import java.util.HashMap;
import java.util.Map;

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
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichToolbar;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class AppCostCenterServiceBean {


    private RichSelectOneChoice level1PgBind;
    private RichSelectOneChoice level2PgBind;
    private RichSelectOneChoice level3PgBind;
    private RichSelectOneChoice level4PgBind;
    private RichSelectOneChoice level5PgBind;
    private Integer DOC_ID = Integer.parseInt(resolvElO("#{pageFlowScope.DOC_ID}").toString());
    private Integer SLOC_ID = Integer.parseInt(resolvElO("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
    private String CLD_ID = (String) resolvElO("#{pageFlowScope.GLBL_APP_CLD_ID}");
    private String HO_ORG_ID = (String) resolvElO("#{pageFlowScope.GLBL_HO_ORG_ID}");
    private String ORG_ID = (String) resolvElO("#{pageFlowScope.GLBL_APP_USR_ORG}");
    private RichPanelFormLayout panelFormPgBind;
    private RichPanelFormLayout summAmtFormPgBind;
    private RichToolbar toolbarBinding;
    private Boolean isValid = Boolean.FALSE;

  
    // private String p_mode = resolvElO("#{pageFlowScope.PARAM_MODE}");

    public AppCostCenterServiceBean() {
        try {
            /// this function is used to enable the fields present on Page
            /// Never remove/comment this function ******************
            callStoredProc_For_Level("APP.PROC_GET_CC_TYPE_NM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                     DOC_ID, SLOC_ID, CLD_ID, ORG_ID, HO_ORG_ID
            });
        } catch (Exception e) {
            e.printStackTrace();

            FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.2346']}")); //Please create Profit Center profile for current document.
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        getGlobalVal();
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
    
    public void getGlobalVal() {
        Integer aMT_DIGIT = EbizParams.GLBL_AMT_DIGIT();
        if (aMT_DIGIT != null) {
        } else {
            Object val = callStoredFunction(Types.NUMERIC, "APP.FN_GET_APP_AMT_DIGIT(?)", new Object[] {
                                            EbizParams.GLBL_APP_CLD_ID() });

            if (val != null) {
                RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_AMT_DIGIT", val);
            }
        }
    }


    private RichPopup tvoupopup;
    private RichTable table;
    private String mode = "V";
    private RichPopup tvoudeletePopup;

    private String level1 = "";
    private String level2 = "";
    private String level3 = "";
    private String level4 = "";
    private String level5 = "";
    private String level1_man = "N";
    private String level2_man = "N";
    private String level3_man = "N";
    private String level4_man = "N";
    private String level5_man = "N";

    private String ProfileName = "";
    private Integer countval = 0;
    private String P_DOC_NAME = "";

    private Integer getDocIdValue() {
        return Integer.parseInt(resolvElO("#{pageFlowScope.DOC_ID}").toString());
    }

    private String getCldIdValue() {
        return resolvElO("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
    }

    private Integer getSlocIdValue() {
        return Integer.parseInt(resolvElO("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
    }

    private String getHoOrgIdValue() {
        return resolvElO("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
    }

    private String getOrgIdValue() {
        return resolvElO("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }


    private AppCostCenterServiceAMImpl getAm() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data.AppCostCenterServiceAMDataControl.dataProvider}",
                                            Object.class);
        return (AppCostCenterServiceAMImpl) valueExp.getValue(elContext);

    }

    /**Method to resolve expression- returns String value*/
    public Object resolvElO(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext);
        return Message;
    }

    public String Create() {
        System.out.println("In create");
        chekBlank();
        if (check.equalsIgnoreCase("docommit")) {
            System.out.println("After checkblank, docommit");
            String res = checkDuplicate();
            if (res.toString().equalsIgnoreCase("noduplicate")) {
                System.out.println("No duplicate");
                Integer SlNo = 0;
                String AmtType = "E";
                Number amt = new Number(0);
                ViewObjectImpl v = getAm().getTempCostCenter();
                String Src = (String) resolvElO("#{pageFlowScope.TVOU_SRC}");
                if (Src.equalsIgnoreCase("H")) {
                    SlNo = 0;
                    AmtType = "H";

                } else {
                    System.out.println("src is L");
                    SlNo = Integer.parseInt(resolvElO("#{pageFlowScope.TVOU_SL_NO}").toString());
                    int count = v.getRowCount();
                    if (count > 0) {
                        RowSetIterator rsi = v.createRowSetIterator(null);
                        Row rw = rsi.first();
                        if (rw != null) {
                            if (rw.getAttribute("TempAmountType") != null) {
                                AmtType = rw.getAttribute("TempAmountType").toString();

                            }
                        }
                        rsi.closeRowSetIterator();
                    } else {
                        AmtType = "E";
                    }
                }
                Integer maxSlNo = getMaxSlNo();
                System.out.println("maxSlNo" +maxSlNo);

                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("CreateWithParams");
                operationBinding.execute();


                ////// Following code will set if default values were exist for the curent document
                Object ccPrfId = null;
                ViewObjectImpl appCcDocVO1 = this.getAm().getAppCcDocVO1();

                appCcDocVO1.setNamedWhereClauseParam("BindCldId", this.getCldIdValue());
                appCcDocVO1.setNamedWhereClauseParam("BindSlocId", this.getSlocIdValue());
                appCcDocVO1.setNamedWhereClauseParam("BindHoOrgId", this.getHoOrgIdValue());
                appCcDocVO1.setNamedWhereClauseParam("BindOrgId", this.getOrgIdValue());
                appCcDocVO1.setNamedWhereClauseParam("BindDocId", this.getDocIdValue());

                appCcDocVO1.executeQuery();

                Row[] allRowsInRange = appCcDocVO1.getAllRowsInRange();
                if (allRowsInRange.length > 0) {
                    ccPrfId = allRowsInRange[0].getAttribute("CcPrfId");
                }
                Row currrw = v.getCurrentRow();
                if (ccPrfId != null) {
                    /// Following VO will filter Default data present for document Id or not
                    /// If present then it will fetch default data from that VO and insert into TempcostCenter Table
                    //// in Label wise  manner
                    ViewObjectImpl appCcDocDefaultDtlVO1 = this.getAm().getAppCcDocDefaultDtlVO1();
                    appCcDocDefaultDtlVO1.setNamedWhereClauseParam("BindCldId", this.getCldIdValue());
                    appCcDocDefaultDtlVO1.setNamedWhereClauseParam("BindSlocId", this.getSlocIdValue());
                    appCcDocDefaultDtlVO1.setNamedWhereClauseParam("BindHoOrgId", this.getHoOrgIdValue());
                    appCcDocDefaultDtlVO1.setNamedWhereClauseParam("BindOrgId", this.getOrgIdValue());
                    appCcDocDefaultDtlVO1.setNamedWhereClauseParam("BindCcPrfId", ccPrfId);
                    appCcDocDefaultDtlVO1.setNamedWhereClauseParam("BindDocId", this.getDocIdValue());
                    /// Level 1 Value will be obtained
                    if (resolvElO("#{pageFlowScope.P_CC_LEVEL1}") != null) {
                    } else {
                        appCcDocDefaultDtlVO1.setNamedWhereClauseParam("BindCcPos", 1);
                        appCcDocDefaultDtlVO1.executeQuery();
                        Row[] inRange = appCcDocDefaultDtlVO1.getAllRowsInRange();
                        if (inRange.length > 0) {
                            //level1PgBind.setValue(inRange[0].getAttribute("CcDefaultColId"));
                            currrw.setAttribute("TempCcidLvl1", inRange[0].getAttribute("CcDefaultColId"));
                        }
                    }
                    /// Level 2 Value will be obtained
                    if (resolvElO("#{pageFlowScope.P_CC_LEVEL2}") != null) {

                    } else {
                        appCcDocDefaultDtlVO1.setNamedWhereClauseParam("BindCcPos", 2);
                        appCcDocDefaultDtlVO1.executeQuery();

                        Row[] inRange = appCcDocDefaultDtlVO1.getAllRowsInRange();
                        if (inRange.length > 0) {
                            //level2PgBind.setValue(inRange[0].getAttribute("CcDefaultColId"));
                            currrw.setAttribute("TempCcidLvl2", inRange[0].getAttribute("CcDefaultColId"));
                        }
                    }

                    /// Level 3 Value will be obtained
                    if (resolvElO("#{pageFlowScope.P_CC_LEVEL3}") != null) {

                    } else {
                        appCcDocDefaultDtlVO1.setNamedWhereClauseParam("BindCcPos", 3);
                        appCcDocDefaultDtlVO1.executeQuery();

                        Row[] inRange = appCcDocDefaultDtlVO1.getAllRowsInRange();
                        if (inRange.length > 0) {
                            //level3PgBind.setValue(inRange[0].getAttribute("CcDefaultColId"));
                            currrw.setAttribute("TempCcidLvl3", inRange[0].getAttribute("CcDefaultColId"));
                        }
                    }

                    /// Level 4 Value will be obtained
                    if (resolvElO("#{pageFlowScope.P_CC_LEVEL4}") != null) {

                    } else {
                        appCcDocDefaultDtlVO1.setNamedWhereClauseParam("BindCcPos", 4);
                        appCcDocDefaultDtlVO1.executeQuery();

                        Row[] inRange = appCcDocDefaultDtlVO1.getAllRowsInRange();
                        if (inRange.length > 0) {
                            //level4PgBind.setValue(inRange[0].getAttribute("CcDefaultColId"));
                            currrw.setAttribute("TempCcidLvl4", inRange[0].getAttribute("CcDefaultColId"));
                        }
                    }

                    /// Level 5 Value will be obtained
                    if (resolvElO("#{pageFlowScope.P_CC_LEVEL5}") != null) {

                    } else {
                        appCcDocDefaultDtlVO1.setNamedWhereClauseParam("BindCcPos", 5);
                        appCcDocDefaultDtlVO1.executeQuery();

                        Row[] inRange = appCcDocDefaultDtlVO1.getAllRowsInRange();
                        if (inRange.length > 0) {
                            //level5PgBind.setValue(inRange[0].getAttribute("CcDefaultColId"));
                            currrw.setAttribute("TempCcidLvl5", inRange[0].getAttribute("CcDefaultColId"));
                        }
                    }
                }

                currrw.setAttribute("TempCcSlNo", maxSlNo);
                currrw.setAttribute("TempSlNo", SlNo);

                if (Src != null && Src.equalsIgnoreCase("L")) {
                    System.out.println("In create, src is L");
                    //chekBlank();
                    Number amount = new Number(0);
                    try {
                        amount = new Number(resolvElO("#{pageFlowScope.AMOUNT_VALUE}").toString());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    if (AmtType.equals("E") && v.getRowCount() > 0) {
                        //Number equalval =Amount.divide(new Number(count),new MathContext(2));
                        Double db = amount.doubleValue() / v.getRowCount();

                        DecimalFormat df = new DecimalFormat(".000000");
                        String outStr = df.format(db);

                        Number equalval = new Number(0);
                        try {
                            equalval = new Number(outStr);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        RowSetIterator rsi = v.createRowSetIterator(null);
                        while (rsi.hasNext()) {
                            Row rw = rsi.next();

                            rw.setAttribute("TempAmountType", AmtType);
                            rw.setAttribute("TempCcAmount", equalval);
                        }
                        rsi.closeRowSetIterator();
                    } else {

                        currrw.setAttribute("TempAmountType", AmtType);
                        currrw.setAttribute("TempCcAmount", amt);
                    }
                } else {

                    currrw.setAttribute("TempAmountType", AmtType);
                    currrw.setAttribute("TempCcAmount", amt);
                }
                System.out.println("Set attribute");
                mode = "A";
            } else {
                String msg1 = resolvElDCMsg("#{bundle['MSG.1699']}").toString();
                FacesMessage message = new FacesMessage(msg1);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        } else {
            // String msg1 = "A selection is required!";
            String msg1 = resolvElDCMsg("#{bundle['MSG.555']}").toString();
            FacesMessage message = new FacesMessage(msg1);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        return null;
    }

    public String Delete() {

        showPopup(tvoudeletePopup, true);

        //mode = "D";
        return null;
    }

    public String Edit() {
        mode = "E";
        return null;
    }

    public void setTvoupopup(RichPopup tvoupopup) {
        this.tvoupopup = tvoupopup;
    }

    public RichPopup getTvoupopup() {
        return tvoupopup;
    }

    public void tvouPopupCancelListner(PopupCanceledEvent pce) {

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();

        OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
        operationBindingexe.execute();
        tvoudeletePopup.setResetEditableValues(RichPopup.RESET_EDITABLE_VALUES_WHEN_CANCELED);
        mode = "V";
        AdfFacesContext.getCurrentInstance().addPartialTarget(table);

    }

    // For Manual Check of blank entries------------
    String check = "null";

    public String chekBlank() {
        System.out.println("In checkblank");
        check = "docommit";
        String src = resolvEl("#{pageFlowScope.TVOU_SRC}");
        ViewObjectImpl v = getAm().getTempCostCenter();
        RowSetIterator rsi = v.createRowSetIterator(null);
        while (rsi.hasNext()) {
            Row rw = rsi.next();
            if (rw != null) {
                System.out.println("row iterator amount value" +rw.getAttribute("TempCcAmount"));
                if (rw.getAttribute("TempCcidLvl1") == "N" && rw.getAttribute("TempCcidLvl2") == "N" &&
                    rw.getAttribute("TempCcidLvl3") == "N" && rw.getAttribute("TempCcidLvl4") == "N" &&
                    rw.getAttribute("TempCcidLvl5") == "N")
                    check = "dontcommit";
                else {
                    if(src=="L")
                    {
                    if(rw.getAttribute("TempCcAmount").equals(0.00)) 
                    check = "dontcommit";
                    }
                }
                System.out.println("check is " +check);
                }
}
        rsi.closeRowSetIterator();
        return check;
    }

    public void tvouDialogListner(DialogEvent de) {
        try {
            if (de.getOutcome().name().equals("ok")) {

                ViewObjectImpl v = getAm().getTempCostCenter();
                Row curr = v.getCurrentRow();
                int count = v.getRowCount();
                String Src = (String) resolvElO("#{pageFlowScope.TVOU_SRC}");
                Number Amount = new Number(0);

                if (Src.equals("H")) {
                    chekBlank();
                    if (check.equalsIgnoreCase("docommit")) {
                        RowSetIterator rsi = v.createRowSetIterator(null);
                        while (rsi.hasNext()) {
                            Row rw = rsi.next();
                            rw.setAttribute("TempAmountType", "H");
                            rw.setAttribute("TempCcAmount", new Number(0));
                        }
                        rsi.closeRowSetIterator();
                        BindingContainer bindings = getBindings();
                        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                        operationBinding.execute();

                        if (operationBinding.getErrors().isEmpty()) {
                            OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                            operationBindingexe.execute();
                            mode = "V";
                            tvoupopup.hide();
                            AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                            //String msg1 = "Record saved successfully.";
                            String msg1 = resolvElDCMsg("#{bundle['MSG.142']}").toString();
                            FacesMessage message = new FacesMessage(msg1);
                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        } else {

                            OperationBinding operationBindingrollback = bindings.getOperationBinding("Rollback");
                            operationBindingrollback.execute();

                            OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                            operationBindingexe.execute();
                            mode = "V";
                            tvoupopup.hide();
                            AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                            String msg1 = resolvElDCMsg("#{bundle['MSG.120']}").toString();  //Something went wrong. Contact ESS
                            FacesMessage message = new FacesMessage(msg1);
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }

                    } else {
                        // String msg1 = "A selection is required!";
                        String msg1 = resolvElDCMsg("#{bundle['MSG.252']}").toString();
                        FacesMessage message = new FacesMessage(msg1);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                }

                else if (Src.equals("L")) {
                    chekBlank();
                    if (check.equalsIgnoreCase("docommit")) {
                        Amount = new Number(resolvElO("#{pageFlowScope.AMOUNT_VALUE}").toString());
                        if (curr.getAttribute("TempAmountType") != null) {
                            String amtType = curr.getAttribute("TempAmountType").toString();
                            if (amtType.equals("E")) {
                                //Number equalval =Amount.divide(new Number(count),new MathContext(2));
                                Double db = Amount.doubleValue() / count;

                                DecimalFormat df = new DecimalFormat(".000000");
                                String outStr = df.format(db);
                                Number equalval;

                                equalval = new Number(outStr);


                                RowSetIterator rsi = v.createRowSetIterator(null);
                                while (rsi.hasNext()) {
                                    Row rw = rsi.next();
                                    rw.setAttribute("TempAmountType", amtType);
                                    rw.setAttribute("TempCcAmount", equalval);
                                }
                                rsi.closeRowSetIterator();
                                BindingContainer bindings = getBindings();
                                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                                operationBinding.execute();

                                if (operationBinding.getErrors().isEmpty()) {


                                    OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                                    operationBindingexe.execute();
                                    mode = "V";
                                    tvoupopup.hide();
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                    //String msg1 = "Record saved successfully.";
                                    String msg1 = resolvElDCMsg("#{bundle['MSG.91']}").toString();
                                    FacesMessage message = new FacesMessage(msg1);
                                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message);

                                } else {
                                    OperationBinding operationBindingrollback =
                                        bindings.getOperationBinding("Rollback");
                                    operationBindingrollback.execute();

                                    OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                                    operationBindingexe.execute();
                                    mode = "V";
                                    tvoupopup.hide();
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                    String msg1 = resolvElDCMsg("#{bundle['MSG.120']}").toString();  //Something went wrong. Contact ESS
                                    FacesMessage message = new FacesMessage(msg1);
                                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message);
                                }

                            }

                            else if (amtType.equals("N")) {
                                Number sum = new Number(0);
                                Number amountTot = Amount;
                                RowSetIterator rsi = v.createRowSetIterator(null);
                                while (rsi.hasNext()) {
                                    Row rw = rsi.next();
                                    rw.setAttribute("TempAmountType", amtType);
                                    sum = sum.add((Number) (rw.getAttribute("TempCcAmount")));

                                }
                                rsi.closeRowSetIterator();
                                DecimalFormat df = new DecimalFormat(".00");
                                String outStr = df.format(sum);
                                String totAmt = df.format(amountTot);
                                sum = new Number(outStr);
                                amountTot = new Number(totAmt);
                                if (sum.compareTo(amountTot) ==
                                    1) {
                                    // String msg1 =
                                    //  "Total Amount ( " + sum.toString() + " ) should be less than or equal to " + Amount.toString();
                                    String msg1 =
            resolvElDCMsg("#{bundle['LBL.1036']}").toString() + "( " + sum.toString() + " )" +
            resolvElDCMsg("#{bundle['MSG.1182']}").toString() + Amount.toString();

                                    FacesMessage message = new FacesMessage(msg1);
                                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    //  throw new ValidatorException(message);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message);
                                } else if (sum.compareTo(amountTot) == -1) {
                                    BindingContainer bindings = getBindings();
                                    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                                    operationBinding.execute();

                                    if (operationBinding.getErrors().isEmpty()) {
                                        OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                                        operationBindingexe.execute();
                                        mode = "V";
                                        tvoupopup.hide();
                                        AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                        // String msg1 ="Record saved successfully. Total Amount( " + sum.toString() + " ) should equal to " + Amount.toString();
                                        /* String msg1 =
                                            resolvElDCMsg("#{bundle['MSG.91']}").toString() +
                                            resolvElDCMsg("#{bundle['LBL.1036']}").toString() + "( " + sum.toString() +
                                            " )" + resolvElDCMsg("#{bundle['MSG.1183']}").toString() +
                                            Amount.toString();
                                        FacesMessage message = new FacesMessage(msg1);
                                        message.setSeverity(FacesMessage.SEVERITY_WARN);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message); */
                                        
                                    } else {
                                        OperationBinding operationBindingrollback =
                                            bindings.getOperationBinding("Rollback");
                                        operationBindingrollback.execute();

                                        OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                                        operationBindingexe.execute();
                                        mode = "V";
                                        tvoupopup.hide();
                                        AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                        String msg1 = resolvElDCMsg("#{bundle['MSG.120']}").toString();  //Something went wrong. Contact ESS
                                        FacesMessage message = new FacesMessage(msg1);
                                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message);
                                    }


                                } else {
                                    BindingContainer bindings = getBindings();
                                    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                                    operationBinding.execute();

                                    if (operationBinding.getErrors().isEmpty()) {
                                        OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                                        operationBindingexe.execute();
                                        mode = "V";
                                        tvoupopup.hide();
                                        AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                        // String msg1 = "Record saved successfully.";
                                        String msg1 = resolvElDCMsg("#{bundle['MSG.91']}").toString();
                                        FacesMessage message = new FacesMessage(msg1);
                                        message.setSeverity(FacesMessage.SEVERITY_INFO);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message);
                                    } else {
                                        OperationBinding operationBindingrollback =
                                            bindings.getOperationBinding("Rollback");
                                        operationBindingrollback.execute();

                                        OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                                        operationBindingexe.execute();
                                        mode = "V";
                                        tvoupopup.hide();
                                        AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                        String msg1 = resolvElDCMsg("#{bundle['MSG.120']}").toString();  //Something went wrong. Contact ESS
                                        FacesMessage message = new FacesMessage(msg1);
                                        message.setSeverity(FacesMessage.SEVERITY_ERROR);

                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message);
                                    }


                                }
                            }
                            //
                        }

                    } else {
                        // String msg1 = "A selection is required!";
                        String msg1 = resolvElDCMsg("#{bundle['MSG.252']}").toString();
                        FacesMessage message = new FacesMessage(msg1);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(table);

            } else if (de.getOutcome().name().equals("cancel")) {
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                operationBinding.execute();

                OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                operationBindingexe.execute();
                mode = "V";
                AdfFacesContext.getCurrentInstance().addPartialTarget(table);
            } else if (de.getOutcome().name().equals("yes")) {


                BindingContainer bindings = getBindings();
                OperationBinding operationBindingdel = bindings.getOperationBinding("Delete");
                operationBindingdel.execute();


                if (operationBindingdel.getErrors().isEmpty()) {

                    ViewObjectImpl v = getAm().getTempCostCenter();

                    String Src = (String) resolvElO("#{pageFlowScope.TVOU_SRC}");
                    Number Amount = new Number(0);

                    if (Src.equals("H")) {
                        RowSetIterator rsi = v.createRowSetIterator(null);
                        while (rsi.hasNext()) {
                            Row rw = rsi.next();
                            rw.setAttribute("TempAmountType", "H");
                            rw.setAttribute("TempCcAmount", new Number(0));
                        }
                        rsi.closeRowSetIterator();

                        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                        operationBinding.execute();


                        if (operationBinding.getErrors().isEmpty()) {
                            OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                            operationBindingexe.execute();
                            //mode = "V";
                            tvoupopup.hide();
                            AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                            //String msg1 = "Record deleted successfully.";
                            String msg1 = resolvElDCMsg("#{bundle['MSG.142']}").toString();
                            FacesMessage message = new FacesMessage(msg1);
                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        } else {
                            OperationBinding operationBindingrollback = bindings.getOperationBinding("Rollback");
                            operationBindingrollback.execute();

                            OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                            operationBindingexe.execute();
                            //mode = "V";
                            tvoupopup.hide();
                            AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                            String msg1 = resolvElDCMsg("#{bundle['MSG.120']}").toString();  //Something went wrong. Contact ESS
                            FacesMessage message = new FacesMessage(msg1);
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);

                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }


                    } else if (Src.equals("L")) {

                        Amount = new Number(resolvElO("#{pageFlowScope.AMOUNT_VALUE}").toString());
                        int count = v.getRowCount();

                        if (count > 0) {
                            RowSetIterator rsi = v.createRowSetIterator(null);

                            Row curr = rsi.first();
                            if (curr.getAttribute("TempAmountType") != null) {
                                String amtType = curr.getAttribute("TempAmountType").toString();
                                if (amtType.equals("E")) {
                                    //Number equalval =Amount.divide(new Number(count),new MathContext(2));

                                    Double db = Amount.doubleValue() / count;

                                    DecimalFormat df = new DecimalFormat(".000000");
                                    String outStr = df.format(db);
                                    Number equalval = new Number(outStr);

                                    RowSetIterator rsiEN = v.createRowSetIterator(null);

                                    while (rsiEN.hasNext()) {
                                        Row rw = rsiEN.next();

                                        rw.setAttribute("TempCcAmount", equalval);
                                    }

                                    rsiEN.closeRowSetIterator();
                                    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                                    operationBinding.execute();

                                    if (operationBinding.getErrors().isEmpty()) {
                                        OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                                        operationBindingexe.execute();
                                        //          mode = "V";
                                        tvoupopup.hide();
                                        AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                        // String msg1 = "Record deleted successfully.";
                                        String msg1 = resolvElDCMsg("#{bundle['MSG.142']}").toString();
                                        FacesMessage message = new FacesMessage(msg1);
                                        message.setSeverity(FacesMessage.SEVERITY_INFO);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message);
                                    } else {
                                        OperationBinding operationBindingrollback =
                                            bindings.getOperationBinding("Rollback");
                                        operationBindingrollback.execute();

                                        OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                                        operationBindingexe.execute();
                                        //        mode = "V";
                                        tvoupopup.hide();
                                        AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                        String msg1 = resolvElDCMsg("#{bundle['MSG.120']}").toString(); //Something went wrong. Contact ESS
                                        FacesMessage message = new FacesMessage(msg1);
                                        message.setSeverity(FacesMessage.SEVERITY_ERROR);

                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message);
                                    }


                                }

                                else if (amtType.equals("N")) {
                                    Number sum = new Number(0);
                                    Number amountTot = Amount;
                                    RowSetIterator rsiEN = v.createRowSetIterator(null);
                                    while (rsiEN.hasNext()) {
                                        Row rw = rsiEN.next();

                                        sum = sum.add((Number) rw.getAttribute("TempCcAmount"));

                                    }
                                    rsiEN.closeRowSetIterator();
                                    /* DecimalFormat df = new DecimalFormat(".00");
                                    String outStr = df.format(sum);
                                    String totAmt = df.format(amountTot);
                                    sum = new Number(outStr);
                                    amountTot = new Number(totAmt);
                                    */
                                    if (sum != null && amountTot != null) {
                                        if (sum.compareTo(amountTot) ==
                                            1) {
                                            //String msg1 =
                                            //    "Total Amount( " + sum.toString() + " )should be less equal to " + Amount.toString();

                                            String msg1 =
                    resolvElDCMsg("#{bundle['LBL.1036']}").toString() + "( " + sum.toString() + " )" + //Total Amount
                    resolvElDCMsg("#{bundle['MSG.1182']}").toString() + Amount.toString();  //should be less than or equal to

                                            FacesMessage message = new FacesMessage(msg1);
                                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                            FacesContext fc = FacesContext.getCurrentInstance();
                                            fc.addMessage(null, message);
                                        } else if (sum.compareTo(amountTot) == -1) {
                                            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                                            operationBinding.execute();

                                            if (operationBinding.getErrors().isEmpty()) {
                                                OperationBinding operationBindingexe =
                                                    bindings.getOperationBinding("Execute");
                                                operationBindingexe.execute();
                                                //          mode = "V";
                                                tvoupopup.hide();
                                                AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                                //  String msg1 =
                                                //   "Record deleted successfully. Total Amount( " + sum.toString() + " )should equal to " + Amount.toString();

                                               /*  String msg1 =
                                                    resolvElDCMsg("#{bundle['MSG.142']}").toString() +  //Record deleted successfully
                                                    resolvElDCMsg("#{bundle['LBL.1036']}").toString() + "( " +  //Total Amount
                                                    sum.toString() + " )" +
                                                    resolvElDCMsg("#{bundle['MSG.1183']}").toString() +  //should equal to
                                                    Amount.toString();

                                                FacesMessage message = new FacesMessage(msg1);
                                                message.setSeverity(FacesMessage.SEVERITY_WARN);
                                                FacesContext fc = FacesContext.getCurrentInstance();
                                                fc.addMessage(null, message); */
                                            } else {
                                                OperationBinding operationBindingrollback =
                                                    bindings.getOperationBinding("Rollback");
                                                operationBindingrollback.execute();

                                                OperationBinding operationBindingexe =
                                                    bindings.getOperationBinding("Execute");
                                                operationBindingexe.execute();
                                                //        mode = "V";
                                                tvoupopup.hide();
                                                AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                                String msg1 = resolvEl("#{bundle['MSG.120']}"); //Something went wrong. Contact ESS
                                                FacesMessage message = new FacesMessage(msg1);
                                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                                FacesContext fc = FacesContext.getCurrentInstance();
                                                fc.addMessage(null, message);
                                            }

                                        } else {

                                            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                                            operationBinding.execute();

                                            if (operationBinding.getErrors().isEmpty()) {
                                                OperationBinding operationBindingexe =
                                                    bindings.getOperationBinding("Execute");
                                                operationBindingexe.execute();
                                                //      mode = "V";
                                                tvoupopup.hide();
                                                AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                                // String msg1 = "Record deleted successfully.";
                                                String msg1 = resolvElDCMsg("#{bundle['MSG.142']}").toString();
                                                FacesMessage message = new FacesMessage(msg1);
                                                message.setSeverity(FacesMessage.SEVERITY_INFO);
                                                FacesContext fc = FacesContext.getCurrentInstance();
                                                fc.addMessage(null, message);
                                            } else {
                                                OperationBinding operationBindingrollback =
                                                    bindings.getOperationBinding("Rollback");
                                                operationBindingrollback.execute();

                                                OperationBinding operationBindingexe =
                                                    bindings.getOperationBinding("Execute");
                                                operationBindingexe.execute();
                                                //    mode = "V";
                                                tvoupopup.hide();
                                                AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                                String msg1 = resolvElDCMsg("#{bundle['MSG.120']}").toString();  //Something went wrong. Contact ESS
                                                FacesMessage message = new FacesMessage(msg1);
                                                message.setSeverity(FacesMessage.SEVERITY_ERROR);

                                                FacesContext fc = FacesContext.getCurrentInstance();
                                                fc.addMessage(null, message);
                                            }


                                        }
                                    }
                                }
                                //
                            }
                            rsi.closeRowSetIterator();

                        }
                    }
                }
                //mode = "V";
                AdfFacesContext.getCurrentInstance().addPartialTarget(table);

                OperationBinding operationBindingexe = bindings.getOperationBinding("Commit");
                operationBindingexe.execute();
            } else if (de.getOutcome().name().equals("no")) {
                /* BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();

            OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
            operationBindingexe.execute();
            mode = "V";
            AdfFacesContext.getCurrentInstance().addPartialTarget(table); */
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelFormPgBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(toolbarBinding);
    }

    private Integer getMaxSlNo() {

        ViewObjectImpl v = getAm().getTempCostCenter();
        int count = v.getRowCount();

        Integer maxNumber = 0;
        Integer val = 0;
        if (count > 0) {


            RowSetIterator rsi = v.createRowSetIterator(null);
            while (rsi.hasNext()) {
                Row rw = rsi.next();
                val = (Integer) rw.getAttribute("TempCcSlNo");

                if (val > maxNumber) {

                    maxNumber = val;
                }
            }

            rsi.closeRowSetIterator();
        }
        System.out.println("max number" +maxNumber);
        return maxNumber + 1;
    }

    public String setToDocCC(Integer P_TEMP_SLOC_ID, String P_TEMP_CLD_ID, String P_TEMP_HO_ORG_ID,
                             String P_TEMP_ORG_ID, Integer P_TEMP_DOC_ID, String P_TEMP_ID, Integer P_TEMP_SL_NO,
                             Integer P_TEMP_CC_SL_NO, String P_MODE) {

        //String ret = null;
        /* ret = (String) callStoredFunction(VARCHAR, "FIN.fn_ins_cost_center(?,?,?,?,?,?,?,?,?)", new Object[] {
                                          P_TEMP_SLOC_ID, P_TEMP_CLD_ID, P_TEMP_HO_ORG_ID, P_TEMP_ORG_ID, P_TEMP_DOC_ID,
                                          P_TEMP_ID, P_TEMP_SL_NO, P_TEMP_CC_SL_NO, P_MODE
        }); */

        return null;
    }

    private static int VARCHAR = Types.VARCHAR;
    private static int NUMBER = Types.NUMERIC;

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {

            // 1. Create a JDBC CallabledStatement

            st = getAm().getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            // 2. Register the first bind variable for the return value
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                // 3. Loop over values for the bind variables passed in, if any
                for (int z = 0; z < bindVars.length; z++) {
                    // 4. Set the value of user-supplied bind vars in the stmt
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            // 5. Set the value of user-supplied bind vars in the stmt
            st.executeUpdate();
            // 6. Return the value of the first bind variable
            return st.getObject(1);
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    // 7. Close the statement
                    st.close();
                } catch (SQLException e) {
                    throw new JboException(e);
                }
            }
        }
    }

    protected void callStoredProc_For_Level(String stmt, Object[] bindVars) {

        CallableStatement st = null;

        try {
            /** 1. Create a JDBC CallabledStatement */

            st = getAm().getDBTransaction().createCallableStatement("begin " + stmt + ";end;", 0);
            /** 2. Register the first bind variable for the return value. and last 2 variable for output variable of function. */


            st.setObject(1, bindVars[0]);
            st.setObject(2, bindVars[1]);
            st.setObject(3, bindVars[2]);
            st.setObject(4, bindVars[3]);
            st.setObject(5, bindVars[4]);


            st.registerOutParameter(6, VARCHAR);
            st.registerOutParameter(7, VARCHAR);
            st.registerOutParameter(8, VARCHAR);
            st.registerOutParameter(9, VARCHAR);
            st.registerOutParameter(10, VARCHAR);
            st.registerOutParameter(11, VARCHAR);
            st.registerOutParameter(12, VARCHAR);
            st.registerOutParameter(13, VARCHAR);

            st.registerOutParameter(14, VARCHAR);
            st.registerOutParameter(15, VARCHAR);
            st.registerOutParameter(16, VARCHAR);
            st.registerOutParameter(17, VARCHAR);
            st.registerOutParameter(18, NUMBER);
            /** 5. Set the value of user-supplied bind vars in the stmt */

            st.executeUpdate();

            try {


                if (st.getObject(6) != null) {

                    setLevel1(st.getObject(6).toString());

                }


                if (st.getObject(7) != null) {

                    setLevel2(st.getObject(7).toString());

                }

                if (st.getObject(8) != null) {

                    setLevel3(st.getObject(8).toString());

                }

                if (st.getObject(9) != null) {

                    setLevel4(st.getObject(9).toString());

                }

                if (st.getObject(10) != null) {

                    setLevel5(st.getObject(10).toString());

                }

                if (st.getObject(11) != null) {

                    level1_man = (st.getObject(11).toString());

                }

                if (st.getObject(12) != null) {

                    level2_man = (st.getObject(12).toString());

                }

                if (st.getObject(13) != null) {

                    level3_man = (st.getObject(13).toString());

                }

                if (st.getObject(14) != null) {

                    level4_man = (st.getObject(14).toString());

                }

                if (st.getObject(15) != null) {

                    level5_man = (st.getObject(15).toString());

                }

                if (st.getObject(16) != null) {


                    setProfileName(st.getObject(16).toString());

                    ProfileName = ProfileName + " Cost Center";
                }
                if (st.getObject(17) != null) {

                    setP_DOC_NAME(st.getObject(17).toString());
                    P_DOC_NAME = P_DOC_NAME + " Cost Center";


                }
                if (st.getObject(18) != null) {

                    BigDecimal tempval = (BigDecimal) st.getObject(18);
                    setCountval(tempval.intValue());


                }


            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
                throw new JboException(e);

            }

        } catch (SQLException e) {

            throw new JboException(e);

        } finally {
            if (st != null) {
                try {
                    /** 7. Close the statement */
                    st.close();
                } catch (SQLException e) {
                    throw new JboException(e);

                }
            }
        }


    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

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

    public void setTvoudeletePopup(RichPopup tvoudeletePopup) {
        this.tvoudeletePopup = tvoudeletePopup;
    }

    public RichPopup getTvoudeletePopup() {
        return tvoudeletePopup;
    }


    public void setLevel1(String level1) {
        this.level1 = level1;
    }

    public String getLevel1() {
        return level1;
    }

    public void setLevel2(String level2) {
        this.level2 = level2;
    }

    public String getLevel2() {
        return level2;
    }

    public void setLevel3(String level3) {
        this.level3 = level3;
    }

    public String getLevel3() {
        return level3;
    }

    public void setLevel4(String level4) {
        this.level4 = level4;
    }

    public String getLevel4() {
        return level4;
    }

    public void setLevel5(String level5) {
        this.level5 = level5;
    }

    public String getLevel5() {
        return level5;
    }

    public void setCountval(Integer countval) {
        this.countval = countval;
    }

    public Integer getCountval() {
        return countval;
    }


    public void setProfileName(String ProfileName) {
        this.ProfileName = ProfileName;
    }

    public String getProfileName() {
        return ProfileName;
    }

    public void setP_DOC_NAME(String P_DOC_NAME) {
        this.P_DOC_NAME = P_DOC_NAME;
    }

    public String getP_DOC_NAME() {
        return P_DOC_NAME;
    }

    public void setTable(RichTable table) {
        this.table = table;
    }

    public RichTable getTable() {
        return table;
    }

    public void setLevel1_man(String level1_man) {
        this.level1_man = level1_man;
    }

    public String getLevel1_man() {
        return level1_man;
    }

    public void setLevel2_man(String level2_man) {
        this.level2_man = level2_man;
    }

    public String getLevel2_man() {
        return level2_man;
    }

    public void setLevel3_man(String level3_man) {
        this.level3_man = level3_man;
    }

    public String getLevel3_man() {
        return level3_man;
    }

    public void setLevel4_man(String level4_man) {
        this.level4_man = level4_man;
    }

    public String getLevel4_man() {
        return level4_man;
    }

    public void setLevel5_man(String level5_man) {
        this.level5_man = level5_man;
    }

    public String getLevel5_man() {
        return level5_man;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void AmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number val = (Number) object;
            if (val.compareTo(new Number(0)) ==
                -1) {
                // throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Amount must be greater than equal to Zero.",null));
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.1184']}").toString(), null));
            }
            if (ADFBeanUtils.isPrecisionValid(26, 6, val)) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                              resolvElDCMsg("#{bundle['MSG.129']}").toString(), null));  //Invalid Precision for Amount.
            }
        }
    }

    public String checkDuplicate() {
        System.out.println("In checkduplicate");
        ViewObjectImpl vo = getAm().getTempCostCenter();
        if (vo.getCurrentRow() != null) {
            Row currentRow = vo.getCurrentRow();
            String level1 = vo.getCurrentRow().getAttribute("TempCcidLvl1").toString();
            String level2 = vo.getCurrentRow().getAttribute("TempCcidLvl2").toString();
            String level3 = vo.getCurrentRow().getAttribute("TempCcidLvl3").toString();
            String level4 = vo.getCurrentRow().getAttribute("TempCcidLvl4").toString();
            String level5 = vo.getCurrentRow().getAttribute("TempCcidLvl5").toString();
            RowQualifier rq = new RowQualifier(vo);
            rq.setWhereClause("TempCcidLvl1 ='" + level1 + "' and TempCcidLvl2 ='" + level2 + "' and TempCcidLvl3='" +
                              level3 + "' and TempCcidLvl4='" + level4 + "' and TempCcidLvl5= '" + level5 + "'");
            Row[] filteredRows = vo.getFilteredRows(rq);
            if (filteredRows.length > 0) {
                for (Integer i = 0; i < filteredRows.length; i++) {
                    if (currentRow != filteredRows[i]) {
                        return "duplicate";
                    } else
                        System.out.println("selected roew is ciurrent rowww");
                }
            }
            return "noduplicate";
        }
        return "noduplicate";
    }

    public void commitData(ActionEvent actionEvent) {
        ViewObjectImpl v = getAm().getTempCostCenter();
        Row curr = v.getCurrentRow();
        Row tempRow = getAm().getTemporaryVO1().getCurrentRow();     
        if (curr != null) {
            int count = v.getRowCount();
            String Src = (String) resolvElO("#{pageFlowScope.TVOU_SRC}");
            Number Amount = new Number(0);

            if (Src.equals("H")) {
                chekBlank();
                if (check.equalsIgnoreCase("docommit")) {
                    String res = checkDuplicate();
                    if (res.toString().equalsIgnoreCase("noduplicate")) {
                        RowSetIterator rsi = v.createRowSetIterator(null);
                        while (rsi.hasNext()) {
                            Row rw = rsi.next();
                            rw.setAttribute("TempAmountType", "H");
                            rw.setAttribute("TempCcAmount", new Number(0));
                        }
                        rsi.closeRowSetIterator();
                        BindingContainer bindings = getBindings();
                        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                        operationBinding.execute();

                        if (operationBinding.getErrors().isEmpty()) {
                            OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                            operationBindingexe.execute();

                            mode = "V";

                            AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                            String msg1 = resolvElDCMsg("#{bundle['MSG.91']}").toString();  //Record saved successfully
                            FacesMessage message = new FacesMessage(msg1);
                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        } else {
                            OperationBinding operationBindingrollback = bindings.getOperationBinding("Rollback");
                            operationBindingrollback.execute();

                            OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                            operationBindingexe.execute();

                            mode = "V";

                            AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                            String msg1 = resolvElDCMsg("#{bundle['MSG.120']}").toString();  //Something went wrong. Contact ESS
                            FacesMessage message = new FacesMessage(msg1);
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }
                    } else {
                        String msg1 = resolvElDCMsg("#{bundle['MSG.46']}").toString();
                        FacesMessage message = new FacesMessage(msg1);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                } else {
                    // String msg1 = "A selection is required!";
                    String msg1 = resolvElDCMsg("#{bundle['MSG.252']}").toString();
                    FacesMessage message = new FacesMessage(msg1);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            }

            else if (Src.equals("L")) {
                chekBlank();
                if (check.equalsIgnoreCase("docommit")) {  
                    if(tempRow.getAttribute("ActualAmtTrans")!=null){
                         Number actAmt = (Number) ((Number) tempRow.getAttribute("ActualAmtTrans")).round(EbizParams.GLBL_AMT_DIGIT());
                            Number tempTotAmt = (Number) ((Number) tempRow.getAttribute("TempCcTotAmtTrans")).round(EbizParams.GLBL_AMT_DIGIT());
                            System.out.println("ActualAmtTrans is "+actAmt + "TempCcTotAmtTrans" +tempTotAmt);
                            if(actAmt.equals(tempTotAmt)) 
                                {  
                    String res = checkDuplicate();
                    if (res.toString().equalsIgnoreCase("noduplicate")) {

                        try {
                            Amount = new Number(resolvElO("#{pageFlowScope.AMOUNT_VALUE}").toString());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        if (curr.getAttribute("TempAmountType") != null) {
                            String amtType = curr.getAttribute("TempAmountType").toString();
                            if (amtType.equals("E")) {
                                //Number equalval =Amount.divide(new Number(count),new MathContext(2));
                                Double db = Amount.doubleValue() / count;

                                DecimalFormat df = new DecimalFormat(".000000");
                                String outStr = df.format(db);

                                Number equalval = new Number(0);
                                try {
                                    equalval = new Number(outStr);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }

                                RowSetIterator rsi = v.createRowSetIterator(null);
                                while (rsi.hasNext()) {
                                    Row rw = rsi.next();
                                    rw.setAttribute("TempAmountType", amtType);
                                    rw.setAttribute("TempCcAmount", equalval);
                                }
                                rsi.closeRowSetIterator();
                                BindingContainer bindings = getBindings();
                                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                                operationBinding.execute();

                                if (operationBinding.getErrors().isEmpty()) {


                                    OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                                    operationBindingexe.execute();

                                    mode = "V";

                                    AdfFacesContext.getCurrentInstance().addPartialTarget(table);

                                    //String msg1 = "Record saved successfully.";
                                    /* String msg1 = resolvElDCMsg("#{bundle['MSG.91']}").toString();
                                    FacesMessage message = new FacesMessage(msg1);
                                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message); */

                                } else {
                                    OperationBinding operationBindingrollback =
                                        bindings.getOperationBinding("Rollback");
                                    operationBindingrollback.execute();

                                    OperationBinding operationBindingexe = bindings.getOperationBinding("Execute");
                                    operationBindingexe.execute();

                                    mode = "V";
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(table);

                                    String msg1 = resolvElDCMsg("#{bundle['MSG.120']}").toString();  //Something went wrong. Contact ESS
                                    FacesMessage message = new FacesMessage(msg1);
                                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message);
                                }

                            }

                            else if (amtType.equals("N")) {
                                try {
                                    Number sum = new Number(0);
                                    Number amountTot = Amount;
                                    RowSetIterator rsi = v.createRowSetIterator(null);
                                    while (rsi.hasNext()) {
                                        Row rw = rsi.next();
                                        rw.setAttribute("TempAmountType", amtType);

                                        sum = sum.add((Number) (rw.getAttribute("TempCcAmount")));

                                    }
                                    rsi.closeRowSetIterator();

                                    /* DecimalFormat df = new DecimalFormat(".00");
                                String outStr = df.format(sum);
                                String totAmt = df.format(amountTot);
                                sum = new Number(outStr);
                                amountTot = new Number(totAmt); */

                                    if (sum.compareTo(amountTot) ==
                                        1) {
                                        // String msg1 =
                                        //  "Total Amount ( " + sum.toString() + " ) should be less than or equal to " + Amount.toString();
                                        String msg1 =
                resolvElDCMsg("#{bundle['LBL.1036']}").toString() + "( " + sum.toString() + " )" +
                resolvElDCMsg("#{bundle['MSG.1182']}").toString() + Amount.toString();

                                        FacesMessage message = new FacesMessage(msg1);
                                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                        //  throw new ValidatorException(message);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message);
                                    } else if (sum.compareTo(amountTot) == -1) {
                                        BindingContainer bindings = getBindings();
                                        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                                        operationBinding.execute();

                                        if (operationBinding.getErrors().isEmpty()) {
                                            OperationBinding operationBindingexe =
                                                bindings.getOperationBinding("Execute");
                                            operationBindingexe.execute();
                                            mode = "V";

                                            AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                            // String msg1 ="Record saved successfully. Total Amount( " + sum.toString() + " ) should equal to " + Amount.toString();
                                            /* String msg1 =
                                                resolvElDCMsg("#{bundle['MSG.91']}").toString() +  //Record Saved Successfully
                                                resolvElDCMsg("#{bundle['LBL.1036']}").toString() + "( " + //Total Amount
                                                sum.toString() + " )" +
                                                resolvElDCMsg("#{bundle['MSG.1183']}").toString() + Amount.toString();  //should equal to
                                            FacesMessage message = new FacesMessage(msg1);
                                            message.setSeverity(FacesMessage.SEVERITY_WARN);
                                            FacesContext fc = FacesContext.getCurrentInstance();
                                            fc.addMessage(null, message); */
                                        } else {
                                            OperationBinding operationBindingrollback =
                                                bindings.getOperationBinding("Rollback");
                                            operationBindingrollback.execute();

                                            OperationBinding operationBindingexe =
                                                bindings.getOperationBinding("Execute");
                                            operationBindingexe.execute();
                                            mode = "V";

                                            AdfFacesContext.getCurrentInstance().addPartialTarget(table);
                                            String msg1 = resolvElDCMsg("#{bundle['MSG.120']}").toString();  //Something went wrong. Contact ESS
                                            FacesMessage message = new FacesMessage(msg1);
                                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                            FacesContext fc = FacesContext.getCurrentInstance();
                                            fc.addMessage(null, message);
                                        }


                                    } else {
                                        BindingContainer bindings = getBindings();
                                        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                                        operationBinding.execute();

                                        if (operationBinding.getErrors().isEmpty()) {
                                            OperationBinding operationBindingexe =
                                                bindings.getOperationBinding("Execute");
                                            operationBindingexe.execute();

                                            mode = "V";
                                            AdfFacesContext.getCurrentInstance().addPartialTarget(table);

                                            // String msg1 = "Record saved successfully.";
                                            /* String msg1 = resolvElDCMsg("#{bundle['MSG.91']}").toString();
                                            FacesMessage message = new FacesMessage(msg1);
                                            message.setSeverity(FacesMessage.SEVERITY_INFO);
                                            FacesContext fc = FacesContext.getCurrentInstance();
                                            fc.addMessage(null, message); */
                                        } else {
                                            OperationBinding operationBindingrollback =
                                                bindings.getOperationBinding("Rollback");
                                            operationBindingrollback.execute();

                                            OperationBinding operationBindingexe =
                                                bindings.getOperationBinding("Execute");
                                            operationBindingexe.execute();

                                            mode = "V";
                                            AdfFacesContext.getCurrentInstance().addPartialTarget(table);

                                            String msg1 = resolvElDCMsg("#{bundle['MSG.120']}").toString();  //Something went wrong. Contact ESS
                                            FacesMessage message = new FacesMessage(msg1);
                                            message.setSeverity(FacesMessage.SEVERITY_ERROR);

                                            FacesContext fc = FacesContext.getCurrentInstance();
                                            fc.addMessage(null, message);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    } else {
                        String msg1 = resolvElDCMsg("#{bundle['MSG.46']}").toString();
                        FacesMessage message = new FacesMessage(msg1);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                    String msg1 = resolvElDCMsg("#{bundle['MSG.91']}").toString();
                    FacesMessage message = new FacesMessage(msg1);
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                    
                }
                                
                                else
                                {
                                    String msg1 = resolvElDCMsg("#{bundle['LBL.1036']}").toString() + "  " + //Total Amount
                                  tempTotAmt.toString() + resolvElDCMsg("#{bundle['MSG.1183']}").toString() + "  " +actAmt.toString(); //should equal to
                                FacesMessage message = new FacesMessage(msg1);
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                                }
                            }}

                else {
                    // String msg1 = "A selection is required!";
                    String msg1 = resolvElDCMsg("#{bundle['MSG.252']}").toString();
                    FacesMessage message = new FacesMessage(msg1);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(table);
    }

    public void level1VCL(ValueChangeEvent vce) {
        // vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(level1PgBind);
        if (level2PgBind != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(level2PgBind);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(table);
    }

    public void level2VCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(level2PgBind);

        // vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (level3PgBind != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(level3PgBind);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(table);
    }

    public void level3VCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(level3PgBind);

        //vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (level4PgBind != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(level4PgBind);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(table);
    }

    public void level4VCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(level4PgBind);

        //vce.getComponent().processUpdates(FacesContext.getCurrentInstance());

        if (level5PgBind != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(level5PgBind);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(table);
    }

    public void level5VCL(ValueChangeEvent vce) {
        // vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(level5PgBind);

        AdfFacesContext.getCurrentInstance().addPartialTarget(table);
    }

    public void setLevel1PgBind(RichSelectOneChoice level1PgBind) {
        this.level1PgBind = level1PgBind;
    }

    public RichSelectOneChoice getLevel1PgBind() {
        return level1PgBind;
    }

    public void setLevel2PgBind(RichSelectOneChoice level2PgBind) {
        this.level2PgBind = level2PgBind;
    }

    public RichSelectOneChoice getLevel2PgBind() {
        return level2PgBind;
    }

    public void setLevel3PgBind(RichSelectOneChoice level3PgBind) {
        this.level3PgBind = level3PgBind;
    }

    public RichSelectOneChoice getLevel3PgBind() {
        return level3PgBind;
    }

    public void setLevel4PgBind(RichSelectOneChoice level4PgBind) {
        this.level4PgBind = level4PgBind;
    }

    public RichSelectOneChoice getLevel4PgBind() {
        return level4PgBind;
    }

    public void setLevel5PgBind(RichSelectOneChoice level5PgBind) {
        this.level5PgBind = level5PgBind;
    }

    public RichSelectOneChoice getLevel5PgBind() {
        return level5PgBind;
    }

    public String backAction() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();

        return "back";
    }

    public void amountVCL(ValueChangeEvent vce) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(table);
        AdfFacesContext.getCurrentInstance().addPartialTarget(summAmtFormPgBind);
    }

    public void amountTypeVCL(ValueChangeEvent vce) {
        vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
        AdfFacesContext.getCurrentInstance().addPartialTarget(table);
        AdfFacesContext.getCurrentInstance().addPartialTarget(summAmtFormPgBind);
    }

    public void setPanelFormPgBind(RichPanelFormLayout panelFormPgBind) {
        this.panelFormPgBind = panelFormPgBind;
    }

    public RichPanelFormLayout getPanelFormPgBind() {
        return panelFormPgBind;
    }

    public void setSummAmtFormPgBind(RichPanelFormLayout summAmtFormPgBind) {
        this.summAmtFormPgBind = summAmtFormPgBind;
    }

    public RichPanelFormLayout getSummAmtFormPgBind() {
        return summAmtFormPgBind;
    }

    public void setToolbarBinding(RichToolbar toolbarBinding) {
        this.toolbarBinding = toolbarBinding;
    }

    public RichToolbar getToolbarBinding() {
        return toolbarBinding;
    }

    //GlblDocId
    public void applyHeadCc(ActionEvent action) {
        UIComponent comp = (UIComponent) action.getSource();
        Map att = comp.getAttributes();
        Object ob = att.get("GlblDocId");
        if (ob == null || ob.toString().trim().length() == 0) {
            FacesMessage message = new FacesMessage("Document Not Valid");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            this.isValid = false;

        } else {

            if (ob instanceof java.lang.Integer) {
                this.isValid = this.getAm().chkCcApplicableOrNot((Integer) ob);

            } else {
                FacesMessage message = new FacesMessage("Invalid Object Type");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        }

    }

    public String applyHeadCc() {
        String result = null;

        if (this.isValid) {
            result = "headCc";
        } else {
            result = null;
        }

        return result;
    }

    public Map getTypeBasisLvlMan() {
        System.out.println("Check for Mendatoryu");
        return new HashMap<Integer, Boolean>() {
            @Override
            public Boolean get(Object key) {
                if (key != null) {
                    Boolean retVal = false;
                    OperationBinding opChk = ADFBeanUtils.getOperationBinding("chkLvlPosManForType");
                    opChk.getParamsMap().put("lvlPos", key);
                    opChk.execute();
                    if (opChk.getResult() != null)
                        retVal = (Boolean) opChk.getResult();
                    return retVal;
                } else
                    return true;
            }
        };
    }

}
