package mmappuom.view.bean;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

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

import mmappuom.model.module.AppUOMAMImpl;
import mmappuom.model.views.AppUomConvClsVORowImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class AppUomBean {
    private RichPopup addPopup;
    private static int VARCHAR = Types.VARCHAR;
    static String mode = "A";
    private RichPopup addUomConvPopup;
    private RichTable uomtabBind;
    static String modeConv = "A";
    private RichTable uomTabConv;
    private RichPopup addTypePopup;
    private RichSelectBooleanCheckbox actvBind;
    private RichTable uomTypeTableBind;
    private RichSelectOneChoice uomIdSrc;
    private RichSelectOneChoice uomIdDest;
    private RichInputText confctrBind;
    private RichSelectBooleanCheckbox baseuomBind;
    private RichPopup deletePopup;
    private RichInputText inactiveRsnBind;
    private Integer count = -1;
    private static ADFLogger adfLog = (ADFLogger) ADFLogger.createADFLogger(AppUomBean.class);
    private RichInputText uomNmBind;
    private RichSelectBooleanCheckbox actvConChkBox;

    public AppUomBean() {
    }

    public void addUomButton(ActionEvent actionEvent) {

        AppUOMAMImpl am = (AppUOMAMImpl) resolvElDC("AppUOMAMDataControl");


        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Createwithparameters");
        operationBinding.execute();

        setMode("A");


        showPopup(addPopup, true);
    }


    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        AppUOMAMImpl am = (AppUOMAMImpl) resolvElDC("AppUOMAMDataControl");
        try {
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();
            // System.out.println(st.getObject(1));
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

    public void setAddPopup(RichPopup addPopup) {
        this.addPopup = addPopup;
    }

    public RichPopup getAddPopup() {
        return addPopup;
    }

    public void addDialogListener(DialogEvent dialogEvent) {
        AppUOMAMImpl am = (AppUOMAMImpl) resolvElDC("AppUOMAMDataControl");
        ViewObjectImpl std = am.getAppUomConvStd1();
        //Row rr1 = std.getCurrentRow();
        //  Number zero = new Number(0);
        /* if(baseuomBind.getValue().equals(false)){
            rr1.setAttribute("ConvFctr", zero);
        } */
        RowSetIterator rit = std.createRowSetIterator(null);
        String baseunit = "N";
        int count = 0;
        while (rit.hasNext()) {
            Row row = rit.next();
            baseunit = row.getAttribute("BaseUom").toString();
            if (baseunit.equalsIgnoreCase("Y")) {
                count = count + 1;
            }

        }
        adfLog.info("count  in dialog   :::::   " + count);
        if (count == 0) {
            String msg2 = resolvElDCMsg("#{bundle['LBL.1264']}").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);


        } else {
            if (dialogEvent.getOutcome().name().equals("ok")) {
                String uomId = null;
                Integer slocid = Integer.parseInt(resolveEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                String cldid = (String) resolveEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
                adfLog.info("MOde  :::::" + getMode());
                if (getMode().equalsIgnoreCase("A")) {

                    String hoOrgId = (String) resolveEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
                    String table = "APP$UOM$CONV$STD";
                    uomId = callStoredFunction(VARCHAR, "APP.FN_APP_GEN_ID(?,?,?,?,?,?)", new Object[] {
                                               slocid, cldid, hoOrgId, null, table, null
                    }).toString();
                    System.out.println("user id setted value is " + uomId);
                    ViewObjectImpl v = am.getAppUomConvStd1();
                    Row currentRow = v.getCurrentRow();
                    //Date dt=new Date();

                    currentRow.setAttribute("UomId", uomId);
                    System.out.println("usrId created is " + currentRow.getAttribute("UsrIdCreateDt") +
                                       " try value is " + (Date) Date.getCurrentDate());
                    BindingContainer bindings = getBindings();
                    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                    operationBinding.execute();

                    /*  try{
           currentRow.setAttribute("UomId", uomId);
          }
            catch(JboException e)
            {    Date curDate = (Date)Date.getCurrentDate();
                AppUomConvStdVORowImpl row = (AppUomConvStdVORowImpl)v.getCurrentRow();
                    row.setUomId(uomId);
                //    currentRow.setAttribute("UsrIdCreateDt",curDate);
                }


                 BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();*/
                } else {

                    ViewObjectImpl uom = am.getAppUomConvStd1();
                    Row uomrow = uom.getCurrentRow();
                    String actv = uomrow.getAttribute("Actv").toString();
                    String uomid = uomrow.getAttribute("UomId").toString();
                    // Integer slocid = Integer.parseInt(resolveEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                    // String cldid =(String)resolveEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
                    //FUNCTION IS_UOM_DELETABLE(P_SLOCID IN NUMBER, P_CLDID IN VARCHAR2, P_HO_ORGID IN VARCHAR2, P_UOM_ID VARCHAR2) RETURN VARCHAR2 is

                    String del = callStoredFunction(VARCHAR, "PKG_UOM.IS_UOM_DELETABLE(?,?,?,?)", new Object[] {
                                                    slocid, cldid, null, uomid
                    }).toString();
                    if (del.equalsIgnoreCase("N") && actv.equalsIgnoreCase("N")) {
                        String msg2 = resolvElDCMsg("#{bundle['LBL.1265']}").toString();
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        // fc.addMessage(getActvBind().getClientId(fc), message2);
                        fc.addMessage(actvBind.getClientId(), message2);

                    } else {
                        BindingContainer bindings = getBindings();
                        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                        operationBinding.execute();
                    }

                }
                ViewObjectImpl uom = am.getAppUomConvStd1();
                Row uomrow = uom.getCurrentRow();
                adfLog.info("base unit     ::::::::::::;   " + uomrow.getAttribute("BaseUom"));
                if (uomrow.getAttribute("BaseUom") != null) {
                    if (uomrow.getAttribute("BaseUom").toString().equalsIgnoreCase("Y")) {
                        adfLog.info(" inside update :::::::::::    ");
                        OperationBinding opr = getBindings().getOperationBinding("upadteConvertFactor");
                        uomId = (String) uomrow.getAttribute("UomId");
                        System.out.println(uomId);
                        opr.getParamsMap().put("uomId", uomId);
                        opr.execute();
                        BindingContainer bindings = getBindings();
                        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                        operationBinding.execute();

                    }
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

    public void addPopupCancellistener(PopupCanceledEvent popupCanceledEvent) {

        AppUOMAMImpl am = (AppUOMAMImpl) resolvElDC("AppUOMAMDataControl");
        if (getMode().equalsIgnoreCase("A")) {

            BindingContainer bindings = getBindings();
            DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppUomCls1Iterator");
            Key parentKey = parentIter.getCurrentRow().getKey();


            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();


            OperationBinding operationBindings = bindings.getOperationBinding("Execute");
            operationBindings.execute();


            ViewObjectImpl v = am.getAppUomConvStd1();
            v.executeQuery();


            if (parentIter.getRowSetIterator().getRow(parentKey) != null) {
                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));


            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(uomtabBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(uomTypeTableBind);

        } else {

            BindingContainer bindings = getBindings();
            DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("AppUomCls1Iterator");
            Key parentKey = parentIter.getCurrentRow().getKey();

            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();


            OperationBinding operationBindings = bindings.getOperationBinding("Execute");
            operationBindings.execute();


            ViewObjectImpl v = am.getAppUomConvStd1();
            v.executeQuery();


            if (parentIter.getRowSetIterator().getRow(parentKey) != null) {

                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(uomtabBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(uomTypeTableBind);

        }
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

    public String resolveEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }

    public void editUomButton(ActionEvent actionEvent) {
        setMode("E");
        showPopup(addPopup, true);
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setAddUomConvPopup(RichPopup addUomConvPopup) {
        this.addUomConvPopup = addUomConvPopup;
    }

    public RichPopup getAddUomConvPopup() {
        return addUomConvPopup;
    }

    public void addUomConvCancelList(PopupCanceledEvent popupCanceledEvent) {

        if (modeConv == "A") {
            BindingContainer bindings = getBindings();
            DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("Item1Iterator");
            Key parentKey = null;
            if (parentIter.getCurrentRow() != null) {
                parentKey = parentIter.getCurrentRow().getKey();

            }
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();

            AppUOMAMImpl am = (AppUOMAMImpl) resolvElDC("AppUOMAMDataControl");
            ViewObjectImpl v = am.getAppUomConvCls2();
            v.executeQuery();
            if (parentIter.getRowSetIterator().getRow(parentKey) != null) {
                //check condition else gives exception in add mode.
                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));


            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(uomTabConv);
        } else {
            BindingContainer bindings = getBindings();
            DCIteratorBinding parentIter = (DCIteratorBinding) bindings.get("Item1Iterator");
            Key parentKey = null;
            if (parentIter.getCurrentRow() != null) {
                parentKey = parentIter.getCurrentRow().getKey();

            }
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();

            AppUOMAMImpl am = (AppUOMAMImpl) resolvElDC("AppUOMAMDataControl");
            ViewObjectImpl v = am.getAppUomConvCls2();
            v.executeQuery();
            if (parentIter.getRowSetIterator().getRow(parentKey) != null) {
                //check condition else gives exception in add mode.
                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));


            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(uomTabConv);
        }
    }

    public void addUomConvDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {


            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();


        }
    }

    public void addUomConvbutton(ActionEvent actionEvent) {
        modeConv = "A";
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Createwithparameters");
        operationBinding.execute();


        showPopup(addUomConvPopup, true);
    }

    public void editConvButton(ActionEvent actionEvent) {

        modeConv = "E";
        showPopup(addUomConvPopup, true);
    }

    public void baseUomValueChange(ValueChangeEvent valueChangeEvent) {
        AppUOMAMImpl am = (AppUOMAMImpl) resolvElDC("AppUOMAMDataControl");
        ViewObjectImpl v = am.getAppUomConvStd1();
        Row currRow = v.getCurrentRow();
        Number o = new Number(0);
        if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true")) {
            Integer slocid = Integer.parseInt(resolveEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String cldid = (String) resolveEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            adfLog.info("set uom id " + currRow.getAttribute("UomId"));
            System.out.println("set uom id " + currRow.getAttribute("UomId"));
            Integer ret = (Integer) callStoredFunction(Types.INTEGER, "PKG_UOM.UPDT_CONV_FCTR(?,?,?)", new Object[] {
                                                       slocid, cldid, currRow.getAttribute("UomId")
            });
            Row row = v.getCurrentRow();
            System.out.println("return=" + ret);
            adfLog.info("return=" + ret);
            Row rw12 = v.getCurrentRow();

            Integer uomTyp = Integer.parseInt(rw12.getAttribute("UomClass").toString());
            ViewObjectImpl e = am.getAppConvForCounting1();
            e.setWhereClause("Uom_class =" + uomTyp);
            e.executeQuery();
            Number one = new Number(1);
            String y = "Y";
            Row[] rws = e.getFilteredRows("BaseUom", y);
            // row.setAttribute("ConvFctr", one);
            if (rws.length > 0) {


                //  currRow.setAttribute("ConvFctr", one);
                RowSetIterator rit = v.createRowSetIterator(null);
                while (rit.hasNext()) {

                    Row rw = rit.next();
                    if (rw != currRow) {
                        adfLog.info("inside row-------::::::  ");
                        adfLog.info("UNIT  ::::  " + rw.getAttribute("UomId"));
                        // Integer o = 0;

                        //      rw.setAttribute("ConvFctr", o);
                        rw.setAttribute("BaseUom", "N");
                    }

                }
                rit.closeRowSetIterator();

                //  Number cur = (Number)currRow.getAttribute("ConvFctr");
                //  currRow.setAttribute("ConvFctr", cur);
                /*  String msg2 = resolvElDCMsg("#{bundle['MSG.249']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2); */
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(confctrBind);
        }
    }


    public void setUomtabBind(RichTable uomtabBind) {
        this.uomtabBind = uomtabBind;
    }

    public RichTable getUomtabBind() {
        return uomtabBind;
    }

    public void setModeConv(String modeConv) {
        AppUomBean.modeConv = modeConv;
    }

    public String getModeConv() {
        return modeConv;
    }

    public void setUomTabConv(RichTable uomTabConv) {
        this.uomTabConv = uomTabConv;
    }

    public RichTable getUomTabConv() {
        return uomTabConv;
    }

    public void latestFirstLink(ActionEvent actionEvent) {
        AppUOMAMImpl am = (AppUOMAMImpl) resolvElDC("AppUOMAMDataControl");
        ViewObjectImpl v = am.getAppUomCls1();
        v.setOrderByClause("UOM_CLASS_NM");
        v.executeQuery();
    }

    public void addAttTypeButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();

        showPopup(addTypePopup, true);
    }

    public void setAddTypePopup(RichPopup addTypePopup) {
        this.addTypePopup = addTypePopup;
    }

    public RichPopup getAddTypePopup() {
        return addTypePopup;
    }

    public void addTypePopupCancel(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();

        AppUOMAMImpl am = (AppUOMAMImpl) resolvElDC("AppUOMAMDataControl");
        ViewObjectImpl v = am.getAppUomCls1();
        v.executeQuery();
        //  AdfFacesContext.getCurrentInstance().addPartialTarget(uomTypeTableBind);


    }

    public void addTypeDialogList(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            /*  String table = "APP$DS$ATT";
        String uomId = callStoredFunction(VARCHAR, "PKG_APP_GEN.GENERATE_ID(?)", new Object[] {table}).toString();

        Integer attId = Integer.parseInt(uomId);
        AppUOMAMImpl am = (AppUOMAMImpl)resolvElDC("AppUOMAMDataControl");
        ViewObjectImpl v  = am.getAppDsAtt1();
        Row row = v.getCurrentRow();
        row.setAttribute("AttId", attId); */

            OperationBinding op = getBindings().getOperationBinding("checkDuplicateName");
            if (uomNmBind.getValue() != null) {
                op.getParamsMap().put("name", uomNmBind.getValue().toString());
            }
            Object ob = op.execute();
            System.out.println("ob is: " + ob);

            if (ob != null && ob.equals(true)) {
                //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate Name", null));
                //String msg2="Duplicate name !!";

                String msg2 = resolvElDCMsg("#{bundle['MSG.2217']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                // fc.addMessage(getActvBind().getClientId(fc), message2);
                fc.addMessage(uomNmBind.getClientId(), message2);
                return;
            }


            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute2");
            operationBinding1.execute();


            //  v.executeQuery();

            AdfFacesContext.getCurrentInstance().addPartialTarget(uomTypeTableBind);

        }
    }

    public void orderByOnConvPage(ActionEvent actionEvent) {
        AppUOMAMImpl am = (AppUOMAMImpl) resolvElDC("AppUOMAMDataControl");
        ViewObjectImpl v = am.getItem1();
        v.setOrderByClause("UPPER(ITM_DESC) ASC");
        v.executeQuery();
    }

    public void deleteUomButton(ActionEvent actionEvent) {
        AppUOMAMImpl am = (AppUOMAMImpl) resolvElDC("AppUOMAMDataControl");
        ViewObjectImpl v = am.getAppUomConvStd1();
        Row row = v.getCurrentRow();
        Integer slocid = Integer.parseInt(resolveEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String cldid = (String) resolveEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        String ho_org_id = (String) resolveEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String uom = row.getAttribute("UomId").toString();

        String del = callStoredFunction(VARCHAR, "PKG_UOM.IS_UOM_DELETABLE(?,?,?,?)", new Object[] {
                                        slocid, cldid, ho_org_id, uom
        }).toString();

        if (del.equalsIgnoreCase("N")) {
            String msg2 = resolvElDCMsg("#{bundle['MSG.250']}").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);

        } else {
            showPopup(deletePopup, true);

        }


    }

    public void unitSrcValueChange(ValueChangeEvent valueChangeEvent) {

        String uomSrc = valueChangeEvent.getNewValue().toString();

        AppUOMAMImpl am = (AppUOMAMImpl) resolvElDC("AppUOMAMDataControl");
        ViewObjectImpl v = am.getLovUomId1();
        Row[] rows = v.getFilteredRows("UomId", uomSrc);
        Integer uomtype = Integer.parseInt(rows[0].getAttribute("UomClass").toString());

        ViewObjectImpl e = am.getAppUomConvCls2();
        AppUomConvClsVORowImpl r1 = (AppUomConvClsVORowImpl) e.getCurrentRow();
        r1.refreshLov(uomtype);


    }

    public void setActvBind(RichSelectBooleanCheckbox actvBind) {
        this.actvBind = actvBind;
    }

    public RichSelectBooleanCheckbox getActvBind() {
        return actvBind;
    }

    public void actvValueChangeListener(ValueChangeEvent vce) {
        AppUOMAMImpl am = (AppUOMAMImpl) resolvElDC("AppUOMAMDataControl");
        if (vce.getNewValue() != null) {
            String oldval = vce.getOldValue().toString();

            String newVal = vce.getNewValue().toString();
            ViewObjectImpl v1 = am.getAppUomConvStd1();
            Row row = v1.getCurrentRow();
            if (newVal.equalsIgnoreCase("true")) {
                row.setAttribute("InactvDt", null);
                row.setAttribute("InactvResn", null);
                inactiveRsnBind.setValue("");
                AdfFacesContext.getCurrentInstance().addPartialTarget(inactiveRsnBind);
            } else if (newVal.equalsIgnoreCase("false")) {
                Date dt = (Date) Date.getCurrentDate();
                row.setAttribute("InactvDt", dt);
            }

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(inactiveRsnBind);
    }

    public void setUomTypeTableBind(RichTable uomTypeTableBind) {
        this.uomTypeTableBind = uomTypeTableBind;
    }

    public RichTable getUomTypeTableBind() {
        return uomTypeTableBind;
    }

    public void actvConvValueChange(ValueChangeEvent vce) {
        System.out.println("inside conversion value change event");
        AppUOMAMImpl am = (AppUOMAMImpl) resolvElDC("AppUOMAMDataControl");
        if (vce.getNewValue() != null) {
            String oldval = vce.getOldValue().toString();

            String newVal = vce.getNewValue().toString();
            ViewObjectImpl v1 = am.getAppUomConvCls2();
            Row row = v1.getCurrentRow();
            if (newVal.equalsIgnoreCase("true")) {
                row.setAttribute("InactvDt", null);
                row.setAttribute("InactvResn", null);
                //row.setAttribute("Actv", true);
                //                actvConChkBox.setValue("true");
                //                AdfFacesContext.getCurrentInstance().addPartialTarget(actvConChkBox);

            } else if (newVal.equalsIgnoreCase("false")) {
                Date dt = (Date) Date.getCurrentDate();
                row.setAttribute("InactvDt", dt);
            }

        }
    }

    public void attNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String nameDesc = object.toString();
            AppUOMAMImpl am = (AppUOMAMImpl) resolvElDC("AppUOMAMDataControl");
            ViewObject v = am.getAppUomCls1();

            int totalCount = v.getRowCount(); //get ALL rows
            int rangeSize = v.getRangeSize(); //all in range
            v.setRangeSize(totalCount);
            Row[] rArray = v.getAllRowsInRange();
            int count = 0;
            String currName = "";


            Row cRow = v.getCurrentRow();
            for (Row r : rArray) {

                if (!r.equals(cRow)) {
                    try {
                        currName = r.getAttribute("UomClassNm").toString();
                    } catch (NullPointerException npe) {
                        System.out.println("NPE:" + npe);
                        currName = "";
                    }

                    if (currName.equalsIgnoreCase(nameDesc)) {
                        count = count + 1;
                    }
                }

            }
            v.setRangeSize(rangeSize); //set to original range

            if (count > 0) {
                String msg2 = resolvElDCMsg("#{bundle['MSG.46']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
        }
    }

    public void uomConvFctrValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        AppUOMAMImpl am = (AppUOMAMImpl) resolvElDC("AppUOMAMDataControl");
        ViewObject v = am.getAppUomConvCls2();
        Row curr = v.getCurrentRow();
        if (object != null) {

            //  if(curr.getAttribute("UomIdSrc")!=null && curr.getAttribute("UomIdDest")!=null) {
            if (uomIdSrc.getValue() != null && uomIdDest.getValue() != null) {
                if (uomIdSrc.getValue().toString().equalsIgnoreCase(uomIdDest.getValue().toString())) {
                    if (Integer.parseInt(object.toString()) != 1) {
                        String msg2 = resolvElDCMsg("#{bundle['MSG.251']}").toString();
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(message2);
                    }
                }
            }

            if (object != null) {
                Number zero = new Number(0);
                if (((Number) object).compareTo(zero) <= 0) {
                    //String msg2="Conversion Factor must be Greater than Zero.";

                    String msg2 = resolvElDCMsg("#{bundle['MSG.1188']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));

                }
            } else {
                //String msg2="Conversion Factor Required.";

                String msg2 = resolvElDCMsg("#{bundle['MSG.1189']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            }
        }

    }

    public void setUomIdSrc(RichSelectOneChoice uomIdSrc) {
        this.uomIdSrc = uomIdSrc;
    }

    public RichSelectOneChoice getUomIdSrc() {
        return uomIdSrc;
    }

    public void setUomIdDest(RichSelectOneChoice uomIdDest) {
        this.uomIdDest = uomIdDest;
    }

    public RichSelectOneChoice getUomIdDest() {
        return uomIdDest;
    }

    public void setConfctrBind(RichInputText confctrBind) {
        this.confctrBind = confctrBind;
    }

    public RichInputText getConfctrBind() {
        return confctrBind;
    }

    public void setBaseuomBind(RichSelectBooleanCheckbox baseuomBind) {
        this.baseuomBind = baseuomBind;
    }

    public RichSelectBooleanCheckbox getBaseuomBind() {
        return baseuomBind;
    }

    public void setDeletePopup(RichPopup deletePopup) {
        this.deletePopup = deletePopup;
    }

    public RichPopup getDeletePopup() {
        return deletePopup;
    }

    public void deleleDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Delete");
            operationBinding.execute();
            OperationBinding operationBindings = bindings.getOperationBinding("Commit");
            operationBindings.execute();
            OperationBinding operationBindingse = bindings.getOperationBinding("Execute1");
            operationBindingse.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(uomtabBind);
        }
    }

    public void setInactiveRsnBind(RichInputText inactiveRsnBind) {
        this.inactiveRsnBind = inactiveRsnBind;
    }

    public RichInputText getInactiveRsnBind() {
        return inactiveRsnBind;
    }

    public void editClass(ActionEvent actionEvent) {
        showPopup(addTypePopup, true);
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        count = (Integer) getBindings().getOperationBinding("on_load_page").execute();
        return count;
    }

    public void duplicateValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...

        //  System.out.println(object);

        if (object != null) {
            /* OperationBinding operationBinding = getBindings().getOperationBinding("isDuplicateUnit");
            operationBinding.getParamsMap().put("uom",(String)object);
            operationBinding.execute();
            System.out.println("result of uom duplicate "+operationBinding.getErrors());
            Boolean flag = false;
            if(operationBinding.getResult()!=null){
               flag= (Boolean)operationBinding.getResult();
            }

             String msg2=resolvElDCMsg("#{bundle['MSG.375']}").toString();
            if(flag)
            {throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg2,null));
            } */



        }
    }

    public void UomNmValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...

        if (object != null) {
            /*  OperationBinding operationBinding = getBindings().getOperationBinding("isDuplicateUnitClass");
            operationBinding.getParamsMap().put("uomClass",(String)object);
            operationBinding.execute();

            Boolean flag = false;
            if(operationBinding.getResult()!=null){
                flag = (Boolean)operationBinding.getResult();
            }
            String msg2=resolvElDCMsg("#{bundle['MSG.375']}").toString();
            if(flag)
            {throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg2,null));
            } */



        }
    }

    public void uomConvFctrValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number zero = new Number(0);
            Number convFact = (Number) object;
            if (convFact.compareTo(zero) == 0 || convFact.compareTo(zero) == -1) {
                //String msg2="Conversion Factor must be Greater than Zero.";

                String msg2 = resolvElDCMsg("#{bundle['MSG.1188']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));

            }
        } else {
            //String msg2="Conversion Factor Required.";

            String msg2 = resolvElDCMsg("#{bundle['MSG.1189']}").toString();
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
        }
    }

    public void uomDescValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            /*  OperationBinding operationBinding = getBindings().getOperationBinding("isDuplicateUnitDesc");
            operationBinding.getParamsMap().put("uom",(String)object);
            operationBinding.execute();

            Boolean flag = false;
            if(operationBinding.getResult()!=null){
                flag = (Boolean)operationBinding.getResult();
            }
             String msg2=resolvElDCMsg("#{bundle['MSG.375']}").toString();
            if(flag)
            {throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg2,null));
            } */
            OperationBinding operationBinding = getBindings().getOperationBinding("checkDuplicateName");
            operationBinding.getParamsMap().put("name", object.toString());
            Object ob = operationBinding.execute();
            System.out.println("ob is: " + ob);
            if (ob != null && ob.equals(true)) {
                //String msg2="Duplicate name !!";

                String msg2 = resolvElDCMsg("#{bundle['MSG.2217']}").toString();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg2, null));
            }
        }

    }

    public void setUomNmBind(RichInputText uomNmBind) {
        this.uomNmBind = uomNmBind;
    }

    public RichInputText getUomNmBind() {
        return uomNmBind;
    }

    public void setActvConChkBox(RichSelectBooleanCheckbox actvConChkBox) {
        this.actvConChkBox = actvConChkBox;
    }

    public RichSelectBooleanCheckbox getActvConChkBox() {
        return actvConChkBox;
    }
}

