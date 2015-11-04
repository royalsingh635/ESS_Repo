package taxtype.view.TaxType.bean;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import taxtype.model.module.TaxTypeAMImpl;
import taxtype.model.views.AppTaxTypeVOImpl;
import taxtype.model.views.AppTaxVORowImpl;


public class TaxTypeBean {


    private String flag = "";

    protected static Boolean visibleText = false;

    private RichPopup popUpTax;
    private RichPopup popupTaxType;
    private RichPopup deletePopupTaxType;

    private RichTable taxTypeTable;
    private RichTable taxTable;
    private RichOutputText dispText;
    private RichInputText taxNameBindVar;
    private Integer onloadVal;
    private RichSelectBooleanCheckbox taxRegTypeBind;
    private RichSelectBooleanCheckbox taxPartReg;
    private RichSelectOneChoice taxRegBind;
    private RichPanelFormLayout taxFormBinding;
    private RichInputText taxperBinding;
    private String authEdit = "Y";

    public void setAuthEdit(String authEdit) {
        this.authEdit = authEdit;
    }

    public String getAuthEdit() {
        return authEdit;
    }
    //private Number zero=new Number(0);
    public TaxTypeBean() {
        //  getAm().getDBTransaction().rollback();
    }

    public String createTaxType() {


        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        System.out.println(operationBinding.getErrors() + "----------------------->>>>");
        if (operationBinding.getErrors().isEmpty()) {
            System.out.println("before show popup");
            showPopup(popupTaxType, true);
            System.out.println("after show popup");

            flag = "A";
        }


        return null;
    }


    public String createTax() {
        authEdit = "Y";
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
        operationBinding.execute();
        if (operationBinding.getErrors().isEmpty()) {
            Enable = "Y";
            showPopup(popUpTax, true);
            flag = "A";

        }

        return null;
    }

    public String editTaxType() {


        showPopup(popupTaxType, true);
        flag = "E";
        return null;
    }
    private String Enable;

    public String editTax() {
        ViewObject v = getAm().getAppTax1();

        if (v != null) {
            AppTaxVORowImpl r = (AppTaxVORowImpl) v.getCurrentRow();
            if (r != null) {
                Integer SlocId = r.getTaxSlocId();
                String HoOrgId = r.getHoOrgId();
                String CldId = r.getCldId();
                Long TaxId = r.getTaxId();
                if (TaxId != null) {
                    Object res = callStoredFunction2(Types.VARCHAR, "APP.fn_chk_tax_auth_edit_allow(?,?,?,?,?)", new Object[] {
                                                     CldId, SlocId, HoOrgId, 1, TaxId
                    }).toString();
                    System.out.println("result in beann is===" + res);
                    if (res != null) {
                        authEdit = res.toString();
                    }
                    Object result = callStoredFunction2(Types.VARCHAR, "app.fn_is_tax_editable(?,?,?,?)", new Object[] {
                                                        CldId, SlocId, HoOrgId, TaxId
                    });
                    System.out.println("SlocId = " + SlocId + " HoOrgId = " + HoOrgId + " CldId = " + CldId +
                                       " TaxId = " + TaxId + " result =" + result);
                    if (result != null) {
                        Enable = result.toString();
                        showPopup(popUpTax, true);
                        flag = "E";

                    } else {
                        FacesMessage message = new FacesMessage("Error in Validating the Record.Please Contact ESS!");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);

                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                } else {
                    FacesMessage message = new FacesMessage("Error in Validating the Record.Please Contact ESS!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);

                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            }
        }
        showPopup(popUpTax, true);
        flag = "E";
        return null;
    }

    public void okCancelDialogListenerTaxType(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("ok")) {

            BindingContainer binding = getBindings();

            DCIteratorBinding parentIter = (DCIteratorBinding) binding.get("AppTaxType1Iterator");

            if (parentIter != null) {
                Key parentKey = parentIter.getCurrentRow().getKey();
                if (parentKey != null) {
                    OperationBinding createOpBAddress = binding.getOperationBinding("Commit");
                    createOpBAddress.execute();


                    OperationBinding createOpBAddress1 = binding.getOperationBinding("Execute");
                    createOpBAddress1.execute();


                    if (createOpBAddress.getErrors().isEmpty()) {
                        if (flag.equalsIgnoreCase("A")) {
                            String msg = resolvEl("#{bundle['MSG.91']}");
                            FacesMessage message = new FacesMessage(msg);
                            message.setSeverity(FacesMessage.SEVERITY_INFO);

                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(taxTypeTable);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dispText);
                            flag = "S";
                        }

                        else if (flag.equalsIgnoreCase("E")) {


                            String msg = resolvEl("#{bundle['MSG.92']}");
                            FacesMessage message = new FacesMessage(msg);
                            message.setSeverity(FacesMessage.SEVERITY_INFO);

                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(taxTypeTable);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dispText);
                            flag = "S";
                        }
                    } /* else {

                        OperationBinding rollbackOper = binding.getOperationBinding("Rollback");
                        rollbackOper.execute();
                        popupTaxType.hide();
                        String msg = resolvEl("#{bundle['MSG.154']}");
                        FacesMessage message = new FacesMessage(msg);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } */
                }
                //      parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

            }
        } /* else if (dialogEvent.getOutcome().name().equals("cancel")) {
            System.out.println("in cancel ---------------");
            if (flag.equalsIgnoreCase("E")) {
                BindingContainer bindings = getBindings();

                DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppTaxType1Iterator");

                if (parentIter != null) {
                    Key parentKey = parentIter.getCurrentRow().getKey();

                    if (parentKey != null) {
                        BindingContainer binding = getBindings();
                        OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
                        createOpBAddress.execute();


                        OperationBinding createOpBAddress1 = binding.getOperationBinding("Execute");
                        createOpBAddress1.execute();

                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                }
            } else if (flag.equalsIgnoreCase("A")) {


                BindingContainer binding = getBindings();
                OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
                createOpBAddress.execute();


                OperationBinding createOpBAddress1 = binding.getOperationBinding("Execute");
                createOpBAddress1.execute();
            }
            flag = "C";
            AdfFacesContext.getCurrentInstance().addPartialTarget(taxTypeTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dispText);

            AdfFacesContext.getCurrentInstance().addPartialTarget(taxTypeTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dispText);
        } */
    }


    protected static int INTEGER = Types.NUMERIC;
    protected static int DATE = Types.TIME;
    protected static int STRING = Types.VARCHAR;

    public void callStoredProcedure(String stmt, Object[] bindVars) {
        PreparedStatement st = null;
        try {
            st = getAm().getDBTransaction().createPreparedStatement("begin " + stmt + "; end;", 0);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 1, bindVars[z]);
                }
            }
            st.executeUpdate();
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {

            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    private String taxFlag;
    private String tdsFlag;
    private String bothFlag;

    public void callStoredProcedureWithOutParam(String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            st = (CallableStatement) getAm().getDBTransaction().createCallableStatement("begin " + stmt + "; end;", 0);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 1, bindVars[z]);
                }
            }
            st.registerOutParameter(5, Types.VARCHAR);
            st.registerOutParameter(6, Types.VARCHAR);
            st.registerOutParameter(7, Types.VARCHAR);
            st.registerOutParameter(8, Types.VARCHAR);

            st.executeUpdate();

            setTaxFlag(st.getObject(6).toString());
            setTdsFlag(st.getObject(7).toString());
            setBothFlag(st.getObject(8).toString());
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {

            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void updateTax(Integer SlocID, String OrgId, String CldId, Long TaxID) {


        callStoredProcedure("APP.APP_UPD_ACC_TAX(?,?,?,?)", new Object[] { SlocID, OrgId, CldId, TaxID });


    }

    public void okCancelDialogListenerTax(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("ok")) {

            if (flag.equalsIgnoreCase("E")) {

                /*  ViewObject v = getAm().getAppTax1();
                        Row currentRow = v.getCurrentRow();
                     currentRow.setAttribute("mode", "E"); */
                ViewObject v = getAm().getAppTax1();

                AppTaxVORowImpl r = (AppTaxVORowImpl) v.getCurrentRow();
                Integer SlocId = r.getTaxSlocId();
                String HoOrgId = r.getHoOrgId();
                String CldId = r.getCldId();
                Long TaxTypeId = new Long(5022);
                Long TypeId = r.getTaxTypeId();
                Integer naId = r.getTaxNaId();
                String naName = r.getTaxNm();
                String result = (String) callStoredFunction2(Types.VARCHAR, "fin.fn_is_na_nm_unique(?,?,?,?,?)", new Object[] {
                                                             CldId, SlocId, HoOrgId, naName, naId
                });
                if (result.toString().equalsIgnoreCase("Y")) {
                    BindingContainer binding = getBindings();
                    OperationBinding createOpBAddress = binding.getOperationBinding("Commit");
                    createOpBAddress.execute();


                    if (createOpBAddress.getErrors().isEmpty()) {

                        /*   ViewObject v = getAm().getAppTax1();
                            v.executeQuery();
                            v.executeQuery();

                            AppTaxVORowImpl r = (AppTaxVORowImpl)v.getCurrentRow();
                            Integer SlocId = r.getTaxSlocId();
                            String OrgId = r.getHoOrgId();
                            String CldId = r.getCldId();
                            Long TaxId = r.getTaxId(); */


                        createOpBAddress.execute();


                        if (createOpBAddress.getErrors().isEmpty()) {
                            String msg = resolvEl("#{bundle['MSG.91']}");
                            FacesMessage message = new FacesMessage(msg);
                            message.setSeverity(FacesMessage.SEVERITY_INFO);

                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);

                            flag = "S";
                            AdfFacesContext.getCurrentInstance().addPartialTarget(taxTable);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dispText);

                        } else {
                            BindingContainer bindings = getBindings();
                            DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppTaxType1Iterator");
                            if (parentIter != null) {
                                Key parentKey = parentIter.getCurrentRow().getKey();

                                if (parentKey != null) {
                                    OperationBinding roll = binding.getOperationBinding("Rollback");
                                    roll.execute();
                                    // parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

                                    popUpTax.hide();
                                    String msg = resolvEl("#{bundle['MSG.246']}");
                                    FacesMessage message = new FacesMessage(msg);
                                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message);

                                }
                            }


                        }
                    }

                } else {
                    FacesMessage message =
                        new FacesMessage("Duplicate NA Name already exists. Please Change the Tax Name");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }

            } else {
                ViewObject v = getAm().getAppTax1();

                AppTaxVORowImpl r = (AppTaxVORowImpl) v.getCurrentRow();
                Integer SlocId = r.getTaxSlocId();
                String OrgId = r.getHoOrgId();
                String CldId = r.getCldId();
                Long TaxTypeId = new Long(5022);
                Long TypeId = r.getTaxTypeId();
                String naName = r.getTaxNm();
                String result = (String) callStoredFunction2(Types.VARCHAR, "fin.fn_is_na_nm_unique(?,?,?,?,?)", new Object[] {
                                                             CldId, SlocId, OrgId, naName, null
                });
                if (result.toString().equalsIgnoreCase("Y")) {
                    BindingContainer bindings = getBindings();
                    OperationBinding createOpBAddress = bindings.getOperationBinding("Commit");
                    createOpBAddress.execute();
                    ViewObjectImpl appTax = getAm().getAppTax1();
                    RowQualifier rq = new RowQualifier(appTax);
                    rq.setWhereClause("TaxSlocId=" + SlocId + " AND HoOrgId ='" + OrgId + "' AND CldId='" + CldId +
                                      "' AND TaxTypeId = " + TypeId + " AND TaxNm ='" + naName + "'");
                    Row[] filteredRows = appTax.getFilteredRows(rq);
                    System.out.println("filteredRows.length = " + filteredRows.length);
                    Long TaxId = null;
                    if (filteredRows.length > 0) {
                        System.out.println("in if when row exist");
                        Object attribute = filteredRows[0].getAttribute("TaxId");
                        System.out.println("attribute = " + attribute);
                        TaxId = (Long) attribute;
                        if (TaxId != null) {
                            Integer id = getNaId(CldId, SlocId, OrgId, naName, TaxId, TaxTypeId);
                            System.out.println("naid = " + id);
                            filteredRows[0].setAttribute("TaxNaId", id);
                            //  filteredRows[0].setAttribute("mode", "A");
                            appTax.executeQuery();
                            System.out.print("before commit");
                            System.out.println("filteredRows[0].getAttribute(\"TaxNaId\") = " +
                                               filteredRows[0].getAttribute("TaxNaId"));
                            OperationBinding createOpBAddress1 = bindings.getOperationBinding("Commit");
                            createOpBAddress1.execute();
                            System.out.print("after commit");
                            System.out.println("filteredRows[0].getAttribute(\"TaxNaId\") = " +
                                               filteredRows[0].getAttribute("TaxNaId"));

                        }
                    }

                    DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppTaxType1Iterator");


                    DCIteratorBinding childIter = (DCIteratorBinding) bindings.get("AppTax1Iterator");
                    if (parentIter != null && childIter != null) {
                        Key parentKey = parentIter.getCurrentRow().getKey();


                        Key childKey = childIter.getCurrentRow().getKey();
                        if (parentKey != null && childKey != null) {
                            if (createOpBAddress.getErrors().isEmpty()) {

                                // parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                                // childIter.setCurrentRowWithKey(childKey.toStringFormat(true));

                                String msg = resolvEl("#{bundle['MSG.92']}");
                                FacesMessage message = new FacesMessage(msg);
                                message.setSeverity(FacesMessage.SEVERITY_INFO);

                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                                flag = "S";
                                AdfFacesContext.getCurrentInstance().addPartialTarget(taxTable);
                                AdfFacesContext.getCurrentInstance().addPartialTarget(dispText);
                            } else {
                                OperationBinding roll = bindings.getOperationBinding("Rollback");
                                roll.execute();
                                if (parentIter != null) {
                                    // parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                                    if (childIter != null) {
                                        //childIter.setCurrentRowWithKey(childKey.toStringFormat(true));
                                        popUpTax.hide();
                                        String msg = resolvEl("#{bundle['MSG.154']}");
                                        FacesMessage message = new FacesMessage(msg);
                                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    FacesMessage message =
                        new FacesMessage("Duplicate Record already exists. Please change the Tax Name.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(taxTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dispText);

        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            if (flag.equalsIgnoreCase("E")) {
                BindingContainer bindings = getBindings();

                DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppTaxType1Iterator");

                DCIteratorBinding childIter = (DCIteratorBinding) bindings.get("AppTax1Iterator");
                if (parentIter != null && childIter != null) {

                    Key parentKey = parentIter.getCurrentRow().getKey();

                    Key childKey = childIter.getCurrentRow().getKey();

                    if (parentKey != null && childKey != null) {
                        BindingContainer binding = getBindings();
                        OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
                        createOpBAddress.execute();


                        OperationBinding createOpBAddress1 = binding.getOperationBinding("Execute1");
                        createOpBAddress1.execute();

                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

                        childIter.setCurrentRowWithKey(childKey.toStringFormat(true));
                    }
                }

            } else if (flag.equalsIgnoreCase("A")) {


                BindingContainer bindings = getBindings();

                DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppTaxType1Iterator");
                if (parentIter != null) {

                    Key parentKey = parentIter.getCurrentRow().getKey();

                    if (parentKey != null) {
                        BindingContainer binding = getBindings();
                        OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
                        createOpBAddress.execute();


                        OperationBinding createOpBAddress1 = binding.getOperationBinding("Execute1");
                        createOpBAddress1.execute();


                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                }
            }
            flag = "C";
            AdfFacesContext.getCurrentInstance().addPartialTarget(taxTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(dispText);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxFormBinding);

        AdfFacesContext.getCurrentInstance().addPartialTarget(taxTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(dispText);
    }

    public void popUpCancelListenerTaxType(PopupCanceledEvent popupCanceledEvent) {
        if (flag.equalsIgnoreCase("E")) {
            BindingContainer bindings = getBindings();

            DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppTaxType1Iterator");

            if (parentIter != null) {
                Key parentKey = null;


                parentKey = parentIter.getCurrentRow().getKey();
                if (parentKey != null) {

                    BindingContainer binding = getBindings();
                    OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
                    createOpBAddress.execute();


                    OperationBinding createOpBAddress1 = binding.getOperationBinding("Execute");
                    createOpBAddress1.execute();

                    parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }
            }

        } else if (flag.equalsIgnoreCase("A")) {

            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
            createOpBAddress.execute();


            OperationBinding createOpBAddress1 = binding.getOperationBinding("Execute");
            createOpBAddress1.execute();
        }
        flag = "C";
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxTypeTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(dispText);


    }

    public void popUpCancelListenerTax(PopupCanceledEvent popupCanceledEvent) {

        if (flag.equalsIgnoreCase("E")) {

            BindingContainer bindings = getBindings();

            DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppTaxType1Iterator");

            DCIteratorBinding childIter = (DCIteratorBinding) bindings.get("AppTax1Iterator");
            if (parentIter != null && childIter != null) {

                Key parentKey = parentIter.getCurrentRow().getKey();

                Key childKey = childIter.getCurrentRow().getKey();
                if (parentKey != null && childKey != null) {


                    BindingContainer binding = getBindings();
                    OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
                    createOpBAddress.execute();


                    OperationBinding createOpBAddress1 = binding.getOperationBinding("Execute1");
                    createOpBAddress1.execute();

                    parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

                    childIter.setCurrentRowWithKey(childKey.toStringFormat(true));
                }
            }
        } else if (flag.equalsIgnoreCase("A")) {
            BindingContainer bindings = getBindings();

            DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppTaxType1Iterator");
            if (parentIter != null) {
                Key parentKey = parentIter.getCurrentRow().getKey();

                if (parentKey != null) {
                    BindingContainer binding = getBindings();
                    OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
                    createOpBAddress.execute();


                    OperationBinding createOpBAddress1 = binding.getOperationBinding("Execute1");
                    createOpBAddress1.execute();


                    parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }
            }
        }
        flag = "C";
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(dispText);
    }

    private TaxTypeAMImpl getAm() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data.TaxTypeAMDataControl.dataProvider}", Object.class);
        return (TaxTypeAMImpl) valueExp.getValue(elContext);
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
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

    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);

    }


    public void NameValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String msg2 = "";
        if (object != null) {
            String DbobDesc = object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = DbobDesc.toCharArray();
            for (char c : xx) {
                if (c == '(') {
                    openB = openB + 1;
                } else if (c == ')') {
                    closeB = closeB + 1;
                }

                if (closeB > openB) {
                    closeFg = true; //closed brackets will not be more than open brackets at any given time.
                }
            }


            //if openB=0 then no. of closing and opening brackets equal || opening bracket must always come before closing brackets
            //closing brackets must not come before first occurrence of openning bracket
            if (openB != closeB || closeFg == true || (DbobDesc.lastIndexOf("(") > DbobDesc.lastIndexOf(")")) ||
                (DbobDesc.indexOf(")") < DbobDesc.indexOf("("))) {
                msg2 = resolvEl("#{bundle['MSG.7']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (DbobDesc.contains("()")) {
                msg2 = resolvEl("#{bundle['MSG.49']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (DbobDesc.contains("(.") || DbobDesc.contains("(-") || DbobDesc.contains("-)")) {
                msg2 = resolvEl("#{bundle['MSG.248']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;


            //check for valid language name.Allowed- brackets,dots,hyphen
            String expression = "^(?:(?>[A-Za-z0-9 \\(\\)]+)()(\\%|\\@|\\_|\\.|\\-|\\$(?!\\.|\\-|$))?)*$";
            CharSequence inputStr = DbobDesc;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvEl("#{bundle['MSG.97']}");

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }
            int count = 0;
            String[] charval = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
            for (int i = 0; i < charval.length; i++) {
                Boolean val = DbobDesc.startsWith(charval[i]);
                if (val.equals(Boolean.TRUE)) {
                    count = count + 1;
                }
            }

            if (count > 0) {
                String numNotAllowMsg = resolvEl("#{bundle['MSG.52']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, numNotAllowMsg, null));
            }


        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxFormBinding);

    }


    public void DefValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Boolean val = (Boolean) object;

        String value = "";

        if (val == true) {
            value = "Y";
        } else if (val == false) {
            value = "N";
        }


        if (val == true) {


            ViewObject v2 = getAm().getAppTaxType1();
            int count = 0;
            String x = "";
            Row[] rowI = v2.getAllRowsInRange();
            for (Row r : rowI) {
                try {
                    x = r.getAttribute("TaxTypeDef").toString();
                } catch (NullPointerException npe) {

                    x = "";
                }

                if (value.equalsIgnoreCase(x)) {
                    count = count + 1;
                }

            }


            if (flag.equals("A")) {
                if (count > 1) {
                    String msg2 = resolvEl("#{bundle['MSG.103']}");
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                }
            } else if (flag.equals("E")) {
                if (count > 1) {
                    String msg2 = resolvEl("#{bundle['MSG.103']}");
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                }
            }

        }
    }


    public void taxPercentageValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            if (taxNameBindVar.getValue() != null) {
                String val = String.valueOf(object);
                Float a = Float.parseFloat(val);
                Float zero = new Float(0);
                Float hundred = new Float(100);

                String name = "TAX";

                if (taxNameBindVar.getValue() != null) {
                    name = taxNameBindVar.getValue().toString().trim();
                }

                String errorlessthan100 = resolvEl("#{bundle['MSG.102']}");

                if (a.compareTo(hundred) > 0) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errorlessthan100, null));
                } else if (a.compareTo(zero) < 0) {
                    System.out.println("in <0");
                    String errorlessthan0 = resolvEl("#{bundle['MSG.155']}");
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errorlessthan0, null));
                }

                else if (a.compareTo(zero) == 0) {
                    String name1 = name.toUpperCase();
                    System.out.println(name1);
                    if (name1.startsWith("BASIC")) {
                        System.out.println("in basic");

                    } else {
                        System.out.println("in else");
                        String errorlessthan0 = resolvEl("#{bundle['MSG.155']}");
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errorlessthan0,
                                                                      null));

                    }

                }

            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxFormBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxperBinding);

    }

    public void refreshPage() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String refreshpage = fc.getViewRoot().getViewId();
        ViewHandler ViewH = fc.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fc, refreshpage);
        UIV.setViewId(refreshpage);
        fc.setViewRoot(UIV);
    }


    public String deleteTaxType() {


        String error = resolvEl("#{bundle['MSG.19']}");

        ViewObject vo = getAm().getAppTax1();
        Row[] rw = vo.getAllRowsInRange();
        Integer count = rw.length;
        if (count > 0) {
            FacesContext fctx = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(error);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            fctx.addMessage(null, message);
        }

        else {
            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Delete");
            createOpBAddress.execute();
            showPopup(deletePopupTaxType, true);
        }

        return null;
    }


    public void deleteDialogListenerTaxType(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("yes")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();

            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
            createOpBAddress.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(taxTypeTable);


        } else if (dialogEvent.getOutcome().name().equals("no")) {

            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
            createOpBAddress.execute();

            BindingContainer bindings = getBindings();
            OperationBinding OpBAddress = bindings.getOperationBinding("Execute");
            OpBAddress.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(taxTypeTable);
        }

    }


    public void deleteTaxTypeCancelListner(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
        createOpBAddress.execute();

        BindingContainer bindings = getBindings();
        OperationBinding OpBAddress = bindings.getOperationBinding("Execute");
        OpBAddress.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxTypeTable);
    }


    private void showPopup(RichPopup popup, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && popup != null) {
                String popupId = popup.getClientId(context);
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


    public void setVisibleText(Boolean visibleText) {
        this.visibleText = visibleText;
    }

    public Boolean getVisibleText() {
        return visibleText;
    }


    public void setPopUpTax(RichPopup popUpTax) {
        this.popUpTax = popUpTax;
    }

    public RichPopup getPopUpTax() {
        return popUpTax;
    }

    public void setPopupTaxType(RichPopup popupTaxType) {
        this.popupTaxType = popupTaxType;
    }

    public RichPopup getPopupTaxType() {
        return popupTaxType;
    }


    public void setDeletePopupTaxType(RichPopup deletePopupTaxType) {
        this.deletePopupTaxType = deletePopupTaxType;
    }

    public RichPopup getDeletePopupTaxType() {
        return deletePopupTaxType;
    }


    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }


    public void setTaxTypeTable(RichTable taxTypeTable) {
        this.taxTypeTable = taxTypeTable;
    }

    public RichTable getTaxTypeTable() {
        return taxTypeTable;
    }

    public void setTaxTable(RichTable taxTable) {
        this.taxTable = taxTable;
    }

    public RichTable getTaxTable() {
        return taxTable;
    }

    public void setDispText(RichOutputText dispText) {
        this.dispText = dispText;
    }

    public RichOutputText getDispText() {
        return dispText;
    }

    public String createCoaAction() {

        ViewObject v = getAm().getAppTax1();
        AppTaxVORowImpl r = (AppTaxVORowImpl) v.getCurrentRow();
        Integer SlocId = r.getTaxSlocId();
        String HoOrgId = r.getHoOrgId();
        String CldId = r.getCldId();
        Long TaxId = r.getTaxId();
        String TaxNm = r.getTaxNm();
        Integer checkCoaVal = getCoaCount(TaxId, HoOrgId, CldId, SlocId);
        System.out.println("value of checkCoaVal = " + checkCoaVal);
        if (checkCoaVal == 0) {

            /**  this Function is called in order to set the naId Active field to Y when it is N*/
            /* Object callStoredFunction2 =
                callStoredFunction2(Types.VARCHAR, "fin.fn_upd_tax_act_to_na(?,?,?,?)", new Object[] {CldId,SlocId,HoOrgId,TaxId}); */
            //  System.out.println("value returned from function = "+callStoredFunction2);
            /// if (callStoredFunction2!= null && callStoredFunction2.toString().equalsIgnoreCase("Y")) {
            return "CreateCOA";
        } else {
            String msg = resolvEl("#{bundle['MSG.156']}") + TaxNm + resolvEl("#{bundle['MSG.157']}");
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_FATAL);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            return null;
        }


    }

    public Integer getCoaCount(Long P_TAX_ID, String P_HO_ORG_ID, String P_CLD_ID, Integer P_SLOC_ID) {
        Integer retVal = 0;
        BigDecimal val = (BigDecimal) callStoredFunction2(INTEGER, "APP.GET_COA_COUNT_FROM_TAX(?,?,?,?)", new Object[] {
                                                          P_TAX_ID, P_HO_ORG_ID, P_CLD_ID, P_SLOC_ID
        });


        retVal = val.intValue();


        return retVal;
    }

    public Integer getNaId(String param_cld_id, Integer param_sloc_id, String param_ho_org_id, String param_na_name,
                           Long param_tax_id, Long param_tax_type_id) {
        System.out.println("param_cld_id = " + param_cld_id + "param_sloc_id = " + param_sloc_id +
                           "param_ho_org_id = " + param_ho_org_id + "param_na_name = " + param_na_name +
                           "param_tax_id = " + param_tax_id + "param_tax_type_id = " + param_tax_type_id);

        BigDecimal val = (BigDecimal) callStoredFunction2(INTEGER, "FN_INS_TAX_TO_NA(?,?,?,?,?,?,?,?)", new Object[] {
                                                          param_cld_id, param_sloc_id, param_ho_org_id, param_na_name,
                                                          param_tax_id, param_tax_type_id, 1,
                                                          new Timestamp(System.currentTimeMillis())
        });


        Integer retVal = 0;
        retVal = val.intValue();
        System.out.println("retval of na type = " + val);
        return retVal;


    }

    protected Object callStoredFunction2(int sqlReturnType, String stmt, Object[] bindVars) {

        CallableStatement st = null;
        try {
            st = getAm().getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();
            return st.getObject(1);
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }


    public void setTaxNameBindVar(RichInputText taxNameBindVar) {
        this.taxNameBindVar = taxNameBindVar;
    }

    public RichInputText getTaxNameBindVar() {
        return taxNameBindVar;
    }

    public void setOnloadVal(Integer onloadVal) {
        this.onloadVal = onloadVal;
    }

    public Integer getOnloadVal() {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("on_load_page");
        Object ret = operationBinding.execute();
        return (Integer) ret;
    }

    public void taxDeactivationValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String actv = object.toString();
            System.out.println("actv = " + actv);
            if (actv.equalsIgnoreCase("false")) {
                ViewObjectImpl appTax1 = getAm().getAppTax1();

                if (appTax1 != null) {
                    Row currentRow = appTax1.getCurrentRow();
                    if (currentRow != null) {
                        Object CldId = currentRow.getAttribute("CldId");
                        Object HoOrgId = currentRow.getAttribute("HoOrgId");
                        Object TaxNaId = currentRow.getAttribute("TaxNaId");
                        Object TaxSlocId = currentRow.getAttribute("TaxSlocId");
                        System.out.println("CldId = " + CldId + " HoOrgId = " + HoOrgId + " TaxNaId = " + TaxNaId +
                                           " TaxSlocId = " + TaxSlocId);
                        if (CldId != null && HoOrgId != null && TaxNaId != null && TaxSlocId != null) {
                            Object callStoredFunction2 =
                                callStoredFunction2(Types.VARCHAR, "app.fn_tax_allow_deactivation(?,?,?,?)", new Object[] {
                                                    CldId, TaxSlocId, HoOrgId, TaxNaId
                            });

                            System.out.println("callStoredFunction2 = " + callStoredFunction2);
                            if (callStoredFunction2 != null) {
                                if (callStoredFunction2.toString().equalsIgnoreCase("N")) {
                                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                                  "You cannot Deactivate the Tax.Child Record Exists.",
                                                                                  null));

                                }
                            }
                        } else {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          "Error in Deactivating the tax. Please Contact ESS!",
                                                                          null));
                        }
                    }
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxFormBinding);

    }

    public void TaxActivationValueChangeListner(ValueChangeEvent valueChangeEvent) {
    }

    public void setEnable(String Enable) {
        this.Enable = Enable;
    }

    public String getEnable() {
        return Enable;
    }

    public void TaxTypeTdsValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println(" object = " + object);

        if (object != null) {
            if (flag.equalsIgnoreCase("E")) {
                AppTaxTypeVOImpl appTaxType = getAm().getAppTaxType1();

                if (appTaxType != null) {
                    Row currentRow = appTaxType.getCurrentRow();
                    if (currentRow != null) {
                        Object CldId = currentRow.getAttribute("CldId");
                        Object TaxTypeSlocId = currentRow.getAttribute("TaxTypeSlocId");
                        Object HoOrgId = currentRow.getAttribute("HoOrgId");
                        Object TaxTypeId = currentRow.getAttribute("TaxTypeId");

                        System.out.println(" before function call taxFlag = " + taxFlag + " tdsFlag = " + tdsFlag +
                                           " bothFlag = " + bothFlag + " TaxTypeId = " + TaxTypeId + " HoOrgId = " +
                                           HoOrgId + " TaxTypeSlocId = " + TaxTypeSlocId + " CldId = " + CldId);

                        callStoredProcedureWithOutParam("app.proc_get_tax_type_usage_stat(?,?,?,?,?,?,?,?)", new Object[] {
                                                        CldId.toString(), (Integer) TaxTypeSlocId, HoOrgId.toString(),
                                                        (Integer) TaxTypeId
                        });
                        System.out.println("after function call taxFlag = " + taxFlag + " tdsFlag = " + tdsFlag +
                                           " bothFlag = " + bothFlag + " TaxTypeId = " + TaxTypeId + " HoOrgId = " +
                                           HoOrgId + " TaxTypeSlocId = " + TaxTypeSlocId + " CldId = " + CldId);


                        if (object.toString().equalsIgnoreCase("TX")) {
                            if (taxFlag.equalsIgnoreCase("N")) {
                                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                              "It is already used in TDS. You can't change the type to TAX",
                                                                              null));
                            }
                        }
                        if (object.toString().equalsIgnoreCase("TD")) {
                            if (tdsFlag.equalsIgnoreCase("N")) {
                                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                              "It is already used in TAX. You can't change the type to TDS",
                                                                              null));
                            }
                        }
                        /* if (object.toString().equalsIgnoreCase("BO")) {
                            if (bothFlag.equalsIgnoreCase("N")) {
                                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                              "Can't cahneg to BOTH type", null));
                            }
                        } */

                    }
                }
            }
        }
    }

    public void setTaxFlag(String taxFlag) {
        this.taxFlag = taxFlag;
    }

    public String getTaxFlag() {
        return taxFlag;
    }

    public void setTdsFlag(String tdsFlag) {
        this.tdsFlag = tdsFlag;
    }

    public String getTdsFlag() {
        return tdsFlag;
    }

    public void setBothFlag(String bothFlag) {
        this.bothFlag = bothFlag;
    }

    public String getBothFlag() {
        return bothFlag;
    }


    public void PartOfRegisterVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setTaxRegTypeBind(RichSelectBooleanCheckbox taxRegTypeBind) {
        this.taxRegTypeBind = taxRegTypeBind;
    }

    public RichSelectBooleanCheckbox getTaxRegTypeBind() {
        return taxRegTypeBind;
    }

    public void TaxRegisterVal(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...


    }

    public void setTaxPartReg(RichSelectBooleanCheckbox taxPartReg) {
        this.taxPartReg = taxPartReg;
    }

    public RichSelectBooleanCheckbox getTaxPartReg() {
        return taxPartReg;
    }

    public void setTaxRegBind(RichSelectOneChoice taxRegBind) {
        this.taxRegBind = taxRegBind;
    }

    public RichSelectOneChoice getTaxRegBind() {
        return taxRegBind;
    }

    public String DeleteTaxAction() {
        // Add event code here...Delete2
        ViewObject v = getAm().getAppTax1();

        if (v != null) {
            AppTaxVORowImpl r = (AppTaxVORowImpl) v.getCurrentRow();
            if (r != null) {
                Integer SlocId = r.getTaxSlocId();
                String HoOrgId = r.getHoOrgId();
                String CldId = r.getCldId();
                Long TaxId = r.getTaxId();
                Object result = null;
                if (TaxId != null) {
                    try {
                        result = callStoredFunction2(Types.VARCHAR, "fin.fn_del_na_frm_tax(?,?,?,?)", new Object[] {
                                                     CldId, HoOrgId, SlocId, TaxId
                        });
                    } catch (Exception e) {
                        // TODO: Add catch code
                        System.out.println("Cannot Delete Tax");
                        e.printStackTrace();
                    } finally {
                        System.out.println("SlocId = " + SlocId + " HoOrgId = " + HoOrgId + " CldId = " + CldId +
                                           " TaxId = " + TaxId + " result =" + result);
                        if (result != null) {
                            if (result.toString().equalsIgnoreCase("Y")) {
                                BindingContainer bc = getBindings();
                                OperationBinding binding = bc.getOperationBinding("Delete2");
                                binding.execute();
                                OperationBinding binding1 = bc.getOperationBinding("Commit");
                                binding1.execute();
                                if (binding1.getErrors().isEmpty()) {

                                    FacesMessage message = new FacesMessage("Tax deleted Successfuly");
                                    message.setSeverity(FacesMessage.SEVERITY_INFO);

                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(null, message);
                                }
                            } else {
                                FacesMessage message = new FacesMessage("COA Exists! Tax Can't be Deleted. ");
                                message.setSeverity(FacesMessage.SEVERITY_ERROR);

                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                            }

                        } else {
                            FacesMessage message = new FacesMessage("COA Exists! Tax Can't be Deleted!");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);

                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }
                    }


                } else {
                    FacesMessage message = new FacesMessage("Error in Validating the Record.Please Contact ESS!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);

                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            }
        }

        return null;

    }

    public void setTaxFormBinding(RichPanelFormLayout taxFormBinding) {
        this.taxFormBinding = taxFormBinding;
    }

    public RichPanelFormLayout getTaxFormBinding() {
        return taxFormBinding;
    }

    public void aliasValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            if (object.toString().contains("&")) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Character '&'.",
                                                              null));
            }
        }
    }

    public void setTaxperBinding(RichInputText taxperBinding) {
        this.taxperBinding = taxperBinding;
    }

    public RichInputText getTaxperBinding() {
        return taxperBinding;
    }
}
