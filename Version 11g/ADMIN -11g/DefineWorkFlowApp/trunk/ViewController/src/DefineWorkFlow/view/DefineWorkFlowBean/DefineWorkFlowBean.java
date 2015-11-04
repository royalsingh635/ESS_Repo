package DefineWorkFlow.view.DefineWorkFlowBean;

import DefineWorkFlow.model.services.DefineWorkFlowAMImpl;

import DefineWorkFlow.model.views.AppWfUsrLvlMapVORowImpl;

import DefineWorkFlow.model.views.DualVOImpl;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import java.text.SimpleDateFormat;

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
import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.context.AdfFacesContext;


import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ValidationException;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class DefineWorkFlowBean {

    private String mode = "V";
    //   private boolean formreadonly = true;
    private Date curDate;
    DefineWorkFlowAMImpl am = (DefineWorkFlowAMImpl)resolvElDC("DefineWorkFlowAMDataControl");
    private static int VARCHAR = Types.VARCHAR;
    private RichInputText warndays;
    private RichSelectBooleanCheckbox autoskip;
    private RichTable t1;
    private RichPanelFormLayout pf1;
    private RichPanelBox pb1;
    private RichInputText authorisedays;
    private RichSelectBooleanCheckbox skipflag;
    private RichTable table2;
    private RichPopup pop;
    private RichPopup pop1;
    private RichSelectOneChoice docIdPopBind;
    private String disableDoc = "true";
    private RichTable popupaddtable;
    private RichSelectOneChoice docidpopup;
    private RichPanelFormLayout popuppanelform;
    private RichColumn popwflevel;
    private RichColumn popusrid;
    private RichDialog popadddialoglistnr;
    private RichSelectOneChoice usrIdBind;
    private String modeAaya = "V";
    private RichInputText bindWFName;

    private RichInputText wfNameBind;
    private RichSelectOneChoice transDocIdBind;

    private Integer count = (Integer)getBindings().getOperationBinding("on_load_page").execute();
    private RichInputListOfValues wfNameBind1;
    private RichCommandImageLink bindadd;
    private RichCommandImageLink cancelBind;
    private RichInputListOfValues transNameBind;
    private RichSelectOneChoice docIdBind;

    public DefineWorkFlowBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
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


    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;


        try {
            DefineWorkFlowAMImpl am = (DefineWorkFlowAMImpl)resolvElDC("DefineWorkFlowAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                    //  System.out.println(bindVars[z]);
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
                }
            }
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


    public void create(ActionEvent actionEvent) {

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();


        Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        // Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String orgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String cldid = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        String hoorgid = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        Integer usrid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        DefineWorkFlowAMImpl am = (DefineWorkFlowAMImpl)resolvElDC("DefineWorkFlowAMDataControl");
        ViewObjectImpl vo = am.getAppWf1();

        Row row = vo.getCurrentRow();
        row.setAttribute("SlocId", slocid);
        row.setAttribute("CldId", cldid);
        row.setAttribute("OrgId", orgid);
        row.setAttribute("UsrIdCreate", usrid);
        this.mode = "A";
    }

    public void edit(ActionEvent actionEvent) {
        this.mode = "E";
    }

    public void save(ActionEvent actionEvent) {

        Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String orgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String cldid = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        String hoorgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();

        ViewObjectImpl appWf1 = am.getAppWf1();
        ViewObjectImpl appWfLvl = am.getAppWfLvl1();
        RowSetIterator rit = appWfLvl.createRowSetIterator(null);
        if ("A".equalsIgnoreCase(mode)) {
            // System.out.println("entered if loop---------------");
            String wfid =
                (String)(callStoredFunction(VARCHAR, "APP.pkg_app_gen.generate_id (?,?,?,?,?)", new Object[] { slocid,
                                                                                                               cldid,
                                                                                                               hoorgId,
                                                                                                               orgid,
                                                                                                               "APP$WF" }));
            while (rit.hasNext()) {
                //  System.out.println("entered while loop--------");
                Row r = rit.next();
                r.setAttribute("WfId", wfid);

            }

            //  System.out.println("Wf id is-->" + wfid);
            //System.out.println("hhhhhhhhhh----------");
            appWf1.getCurrentRow().setAttribute("WfId", wfid);


        }

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        FacesMessage Message = new FacesMessage(resolvEl("#{bundle['MSG.91']}").toString()); //s;
        // Record Saved Successfully!
        Message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, Message);
        this.mode = "V";
    }


    public void attachdocument(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void cancel(ActionEvent actionEvent) {


        BindingContainer bindings = getBindings();
        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppWf1Iterator");
        Key parentKey = null;
        if (parentIter.getCurrentRow() != null)
            parentKey = parentIter.getCurrentRow().getKey();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        bindadd.setDisabled(false);
        cancelBind.setDisabled(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindadd);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cancelBind);

        if (parentKey != null) {
            System.out.println(parentIter.getEstimatedRowCount() + "<<parent Key--" + parentKey);
            if (parentIter.getRowSetIterator().getRow(parentKey) !=
                null) { //check condition else gives exception in add mode.
                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
            }
        }
        this.mode = "V";
    }

    public void ok(ActionEvent actionEvent) {
        DefineWorkFlowAMImpl am = (DefineWorkFlowAMImpl)resolvElDC("DefineWorkFlowAMDataControl");
        ViewObjectImpl vo = am.getAppWfLvl1();
        ViewObjectImpl vo1 = am.getAppWf1();
        Row row = vo1.getCurrentRow();
        Integer usrid1 = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        Integer maxlvl = Integer.parseInt(row.getAttribute("WfMaxLvl").toString());
        //  System.out.println("max level is----"+maxlvl);

        for (int i = 1; i <= maxlvl; i++) {
            Row row1 = vo.createRow();
            row1.setAttribute("WfLvl", i);
            row1.setAttribute("UsrIdCreate", usrid1);
            vo.insertRow(row1);
            // vo.setOrderByClause("WfLvl");
        }
    }


    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setCurDate(Date curDate) {
        this.curDate = curDate;
    }

    public Date getCurDate() {
        curDate = (Date)Date.getCurrentDate();
        return curDate;
    }


    public void setWarndays(RichInputText warndays) {
        this.warndays = warndays;
    }

    public RichInputText getWarndays() {
        return warndays;
    }


    public void autoskipvalchnglistnr(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            String value = valueChangeEvent.getNewValue().toString();

            if (value.equals("false")) {
                DefineWorkFlowAMImpl am = (DefineWorkFlowAMImpl)resolvElDC("DefineWorkFlowAMDataControl");
                ViewObjectImpl vo = am.getAppWfLvl1();
                Row row = vo.getCurrentRow();
                row.setAttribute("AutoSkipFlg", "N");
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(autoskip);
            AdfFacesContext.getCurrentInstance().addPartialTarget(skipflag);
            AdfFacesContext.getCurrentInstance().addPartialTarget(t1);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pf1);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pb1);
        }
    }

    public void setAutoskip(RichSelectBooleanCheckbox autoskip) {
        this.autoskip = autoskip;
    }

    public RichSelectBooleanCheckbox getAutoskip() {
        return autoskip;
    }

    public void fromdatevalidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        /*
        Date currDt= (Date)Date.getCurrentDate();
        System.out.println("currDt "+currDt);
        Date cpoDate=(Date)object;
        System.out.println("CPO Date: "+cpoDate);
        String pOrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        System.out.println("pOrgId  "+pOrgId);
        java.sql.Date s=(java.sql.Date)callStoredFunction(Types.DATE,"APP.PKG_APP.GET_ORG_FY_START_DATE(?,?,?)", new Object[]{currDt,pOrgId,"FY"});
        System.out.println("Date :"+s);
        Date strtDate=new Date(s);
        System.out.println("Start Date of FY:"+strtDate );
                if(strtDate!=null){
                 if(cpoDate.compareTo(strtDate)==-1){
                    String msg2 = "Invalid CPO Date.";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setDetail("Date can not be less than Financial Year Start Date.");
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
    }
}
  */
        Date currDt = (Date)Date.getCurrentDate();
        //  System.out.println("currDt "+currDt);
        Date formDate = (Date)object;
        //  System.out.println("formDate "+formDate);
        String pOrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        //                    System.out.println("pOrgId "+pOrgId);
        String pSloc = (String)resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}");
        //     System.out.println("pSloc "+pSloc);
        Object oDt =
            callStoredFunction(Types.VARCHAR, "APP.PKG_APP.GET_FY_STAT_FOR_TXN(?,?,?)", new Object[] { pSloc, pOrgId,
                                                                                                       formDate });
        //  System.out.println("oDt "+oDt);
        if (oDt == null) {
            String msg2 = resolvEl("#{bundle['MSG.552']}"); //"Financial Year is not defined."
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        } else if ("Y".equalsIgnoreCase(oDt.toString())) {
            SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");
            String msg2 = resolvEl("#{bundle['MSG.415']}");
            FacesMessage message2 = new FacesMessage(msg2);
            // message2.setDetail(resolvEl("#{bundle['MSG.463']}") + s);
            message2.setDetail(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        }


    }


    public void warndaysvaluechnglistnr(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(authorisedays);
        AdfFacesContext.getCurrentInstance().addPartialTarget(warndays);
        AdfFacesContext.getCurrentInstance().addPartialTarget(t1);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pf1);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pb1);
    }

    public void setT1(RichTable t1) {
        this.t1 = t1;
    }

    public RichTable getT1() {
        return t1;
    }

    public void setPf1(RichPanelFormLayout pf1) {
        this.pf1 = pf1;
    }

    public RichPanelFormLayout getPf1() {
        return pf1;
    }

    public void setPb1(RichPanelBox pb1) {
        this.pb1 = pb1;
    }

    public RichPanelBox getPb1() {
        return pb1;
    }

    public void wrngdaysvldtr(FacesContext facesContext, UIComponent uIComponent, Object object) {
        DefineWorkFlowAMImpl am = (DefineWorkFlowAMImpl)resolvElDC("DefineWorkFlowAMDataControl");
        ViewObjectImpl vo = am.getAppWfLvl1();
        Row row = vo.getCurrentRow();
        Integer authdays = Integer.parseInt(row.getAttribute("AuthDys").toString());
        //   System.out.println("authdays is--------"+authdays);
        if (object != null) {
            Integer value = Integer.parseInt(object.toString());
            // System.out.println("value is------"+value);
            if (value < 0) {
                // System.out.println("value is------"+value);
                FacesMessage Message =
                    new FacesMessage(resolvEl("#{bundle['MSG.553']}")); //"Negative value is not allowed"
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(Message);
            }
            if (value > authdays) {
                // System.out.println("value is------"+value);
                //  System.out.println("authdays is--------"+authdays);
                FacesMessage Message =
                    new FacesMessage(resolvEl("#{bundle['MSG.554']})")); //Warning days should be less than or equal to authorised Days
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(Message);
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(authorisedays);
        AdfFacesContext.getCurrentInstance().addPartialTarget(warndays);
        AdfFacesContext.getCurrentInstance().addPartialTarget(t1);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pf1);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pb1);

    }

    public void setAuthorisedays(RichInputText authorisedays) {
        this.authorisedays = authorisedays;
    }

    public RichInputText getAuthorisedays() {
        return authorisedays;
    }

    public void setSkipflag(RichSelectBooleanCheckbox skipflag) {
        this.skipflag = skipflag;
    }

    public RichSelectBooleanCheckbox getSkipflag() {
        return skipflag;
    }

    public void attachok(ActionEvent actionEvent) {
        DefineWorkFlowAMImpl am = (DefineWorkFlowAMImpl)resolvElDC("DefineWorkFlowAMDataControl");
        ViewObjectImpl vo = am.getAppWfUsrLvlMap1();
        ViewObjectImpl vo1 = am.getAppWf1();
        Row row = vo1.getCurrentRow();
        Integer usrid1 = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        Integer maxlvl = Integer.parseInt(row.getAttribute("WfMaxLvl").toString());
        // System.out.println("max level is----"+maxlvl);

        for (int i = 1; i <= maxlvl; i++) {
            Row row1 = vo.createRow();
            row1.setAttribute("WfLvl", i);
            row1.setAttribute("UsrIdCreate", usrid1);
            // System.out.println(row1.getAttribute("DocId")+"--user id is--------"+usrid1+"----"+row1.getAttribute("WfId"));
            vo.insertRow(row1);
        }
    }

    public void attachadd(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        this.mode = "A";
    }

    public void attachedit(ActionEvent actionEvent) {
        this.mode = "E";
    }

    public void attachcancel(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        this.mode = "V";
    }

    public void attachsave(ActionEvent actionEvent) {
        DefineWorkFlowAMImpl am = (DefineWorkFlowAMImpl)resolvElDC("DefineWorkFlowAMDataControl");
        ViewObjectImpl vo = am.getAppWfDocMap1();
        Row row = vo.getCurrentRow();
        Integer usrid1 = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        row.setAttribute("UsrIdCreate", usrid1);
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        this.mode = "V";

    }

    public void addmore(ActionEvent actionEvent) {
        DefineWorkFlowAMImpl am = (DefineWorkFlowAMImpl)resolvElDC("DefineWorkFlowAMDataControl");
        ViewObjectImpl vo = am.getAppWfDocMap1();
        Row row = vo.getCurrentRow();
        int count = 0;
        ViewObjectImpl vo1 = am.getAppWfUsrLvlMap1();
        RowSetIterator rsi = vo1.createRowSetIterator(null);
        while (rsi.hasNext()) {
            Row r = rsi.next();
            //  System.out.println("--"+r.getAttribute("DocId"));
            if ((r.getAttribute("UsrId") == null) || (r.getAttribute("WfLvl") == null)) {
                count = count + 1;
                //   System.out.println("count is-----"+count);

            }
        }
        rsi.closeRowSetIterator();
        if (count > 0) {
            //   System.out.println("inserted into loop---------");
            String msg2 = resolvEl("#{bundle['MSG.555']}"); //Fields Cann't be blank
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            //    fc.addMessage(docidpopup.getClientId(),message2);
        } else {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert3");
            operationBinding.execute();

        }

    }

    public void setTable2(RichTable table2) {
        this.table2 = table2;
    }

    public RichTable getTable2() {
        return table2;
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

    public void viewuseragainstdoc(ActionEvent actionEvent) {
        showPopup(pop, true);
    }

    public void setPop(RichPopup pop) {
        this.pop = pop;
    }

    public RichPopup getPop() {
        return pop;
    }

    public void addpopup(ActionEvent actionEvent) {
        disableDoc = "false";

        //docIdPopBind.setDisabled(false);
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert2");
        operationBinding.execute();
        showPopup(pop1, true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pop1);

    }

    public void popupok(ActionEvent actionEvent) {

        if (docidpopup.getValue() != null) {
            DefineWorkFlowAMImpl am = (DefineWorkFlowAMImpl)resolvElDC("DefineWorkFlowAMDataControl");
            ViewObjectImpl vo = am.getAppWfUsrLvlMap1();
            ViewObjectImpl vo1 = am.getAppWf1();
            Row row = vo1.getCurrentRow();
            Integer usrid1 = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
            Integer maxlvl = Integer.parseInt(row.getAttribute("WfMaxLvl").toString());
            //  System.out.println("max level is----"+maxlvl);
            modeAaya = "A";
            for (int i = 1; i <= maxlvl; i++) {
                Row row1 = vo.createRow();
                row1.setAttribute("WfLvl", i);
                row1.setAttribute("UsrIdCreate", usrid1);
                row1.setAttribute("ChkMandatory", "M");
                //   System.out.println(row1.getAttribute("DocId")+"--user id is--------"+usrid1+"----"+row1.getAttribute("WfId"));
                vo.insertRow(row1);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(popupaddtable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(pop1);
            AdfFacesContext.getCurrentInstance().addPartialTarget(popupaddtable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(docidpopup);
            AdfFacesContext.getCurrentInstance().addPartialTarget(popuppanelform);

        } else {

            String msg2 = resolvEl("#{bundle['MSG.556']}"); //Document Name cann't be Blank
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(docidpopup.getClientId(), message2);
        }

    }


    public void setPop1(RichPopup pop1) {
        this.pop1 = pop1;
    }

    public RichPopup getPop1() {
        return pop1;
    }

    public void popdialoglistnr(DialogEvent dialogEvent) {
        DefineWorkFlowAMImpl am = (DefineWorkFlowAMImpl)resolvElDC("DefineWorkFlowAMDataControl");
        ViewObjectImpl vo = am.getAppWfDocMap1();
        Row row = vo.getCurrentRow();
        int count = 0;
        ViewObjectImpl vo1 = am.getAppWfUsrLvlMap1();
        RowSetIterator rsi = vo1.createRowSetIterator(null);
        while (rsi.hasNext()) {
            Row r = rsi.next();
            //  System.out.println("--"+r.getAttribute("DocId"));
            if ((r.getAttribute("UsrId") == null) || (r.getAttribute("WfLvl") == null)) {
                count = count + 1;
                //  System.out.println("count is-----"+count);

            }
        }
        rsi.closeRowSetIterator();
        if (count > 0) {
            // System.out.println("inserted into loop---------");
            String msg2 = resolvEl("#{bundle['MSG.555']}"); //"Fields Cann't be blank";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            //    fc.addMessage(docidpopup.getClientId(),message2);
        } else {
            Row row1 = vo1.getCurrentRow();
            Integer usrid1 = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
            row.setAttribute("UsrIdCreate", usrid1);
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            modeAaya = "V";
            AdfFacesContext.getCurrentInstance().addPartialTarget(popupaddtable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(popupaddtable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(docidpopup);
            AdfFacesContext.getCurrentInstance().addPartialTarget(popuppanelform);
        }
    }

    public void popupcancel(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bc = getBindings();
        OperationBinding op = bc.getOperationBinding("Rollback");
        op.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(pop1);
        AdfFacesContext.getCurrentInstance().addPartialTarget(popupaddtable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(docidpopup);
        AdfFacesContext.getCurrentInstance().addPartialTarget(popuppanelform);
        AdfFacesContext.getCurrentInstance().addPartialTarget(popadddialoglistnr);
        AdfFacesContext.getCurrentInstance().addPartialTarget(popusrid);
        AdfFacesContext.getCurrentInstance().addPartialTarget(popwflevel);


    }

    public void EditDocButton(ActionEvent actionEvent) {
        //        docIdPopBind.setDisabled(true);
        disableDoc = "true";
        showPopup(pop1, true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(pop1);

    }

    public void delDocumentAction(ActionEvent actionEvent) {
        //        DefineWorkFlowAMImpl am = (DefineWorkFlowAMImpl)resolvElDC("DefineWorkFlowAMDataControl");
        //        ViewObjectImpl vo =am.getAppWfUsrLvlMap1();
        //        ViewObject voDoc=am.getAppWfDocMap1();
        //
        //        RowSetIterator rsi= vo.createRowSetIterator(null);
        //        while(rsi.hasNext()){
        //            Row curr=rsi.next();
        //            curr.remove();
        //        }
        //        rsi.closeRowSetIterator();
        //        voDoc.getCurrentRow().remove();
        //        am.getDBTransaction().commit();
        //
        Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String orgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String cldid = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        String deleteable = null;
        DefineWorkFlowAMImpl am = (DefineWorkFlowAMImpl)resolvElDC("DefineWorkFlowAMDataControl");
        ViewObjectImpl appWfDocMap1 = am.getAppWfDocMap1();
        String doc_id = appWfDocMap1.getCurrentRow().getAttribute("DocId").toString();
        // System.out.println("DOC ID GOING TO BE DELETED IS :"+doc_id);

        //FUNCTION IS_WF_DOC_DELETABLE(P_SLOCID NUMBER, P_CLDID VARCHAR2, P_ORGID VARCHAR2,
        //                           P_DOCID VARCHAR2)
        deleteable =
                (String)(callStoredFunction(VARCHAR, "APP.PKG_APP_WF.IS_WF_DOC_DELETABLE (?,?,?,?)", new Object[] { slocid,
                                                                                                                    cldid,
                                                                                                                    orgid,
                                                                                                                    doc_id }));
        //System.out.println("Deleteable : "+deleteable);
        if (deleteable == "Y") {
            am = (DefineWorkFlowAMImpl)resolvElDC("DefineWorkFlowAMDataControl");
            ViewObjectImpl vo = am.getAppWfUsrLvlMap1();
            ViewObject voDoc = am.getAppWfDocMap1();

            RowSetIterator rsi = vo.createRowSetIterator(null);
            while (rsi.hasNext()) {
                Row curr = rsi.next();
                curr.remove();
            }
            rsi.closeRowSetIterator();
            voDoc.getCurrentRow().remove();
            am.getDBTransaction().commit();

            FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.356']}")); //\"Deleted!MSG.365
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

        } else {
            FacesMessage message = new FacesMessage(resolvEl("#{bundle['MSG.557']}")); //Cannot delete this document!
            message.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
    }

    public void setDocIdPopBind(RichSelectOneChoice docIdPopBind) {
        this.docIdPopBind = docIdPopBind;
    }

    public RichSelectOneChoice getDocIdPopBind() {
        return docIdPopBind;
    }

    public void setDisableDoc(String disableDoc) {
        this.disableDoc = disableDoc;
    }

    public String getDisableDoc() {
        return disableDoc;
    }

    public void setPopupaddtable(RichTable popupaddtable) {
        this.popupaddtable = popupaddtable;
    }

    public RichTable getPopupaddtable() {
        return popupaddtable;
    }

    public void authdaysvalidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        DefineWorkFlowAMImpl am = (DefineWorkFlowAMImpl)resolvElDC("DefineWorkFlowAMDataControl");
        ViewObjectImpl vo = am.getAppWfLvl1();
        Row row = vo.getCurrentRow();
        if (object != null) {
            Integer value = Integer.parseInt(object.toString());
            //  System.out.println("value is------"+value);
            if (value < 0) {
                //       System.out.println("value is------"+value);
                FacesMessage Message =
                    new FacesMessage(resolvEl("#{bundle['MSG.553']}")); // Negative value is not allowed MSG.553
                Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(Message);
            }

        }

    }

    public void setDocidpopup(RichSelectOneChoice docidpopup) {
        this.docidpopup = docidpopup;
    }

    public RichSelectOneChoice getDocidpopup() {
        return docidpopup;
    }

    public void popupdocidvalidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        DefineWorkFlowAMImpl am = (DefineWorkFlowAMImpl)resolvElDC("DefineWorkFlowAMDataControl");
        ViewObjectImpl vo = am.getAppWfUsrLvlMap1();
        ViewObjectImpl vo1 = am.getAppWfDocMapValid1();
        Row row = vo1.getCurrentRow();
        int count = 0;
        if (object != null || row.getAttribute("DocId") != null) {
            //  System.out.println("Obj--"+object.toString());
            //Row[] rw=vo1.getFilteredRows("DocId", object.toString());
            RowSetIterator rsi = vo1.createRowSetIterator(null);
            while (rsi.hasNext()) {
                Row r = rsi.next();
                //   System.out.println("--"+r.getAttribute("DocId"));
                if (r.getAttribute("DocId") != null) {
                    if (object.toString().equalsIgnoreCase(r.getAttribute("DocId").toString())) {
                        count = count + 1;
                    }
                }
            }
            rsi.closeRowSetIterator();
            if (count > 0) {
                String msg2 = resolvEl("#{bundle['MSG.558']}"); //Document is currently used by another workflow
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                throw new ValidatorException(message2);
                //    fc.addMessage(docidpopup.getClientId(),message2);
            }
        }
    }

    public void setPopuppanelform(RichPanelFormLayout popuppanelform) {
        this.popuppanelform = popuppanelform;
    }

    public RichPanelFormLayout getPopuppanelform() {
        return popuppanelform;
    }

    public void setPopwflevel(RichColumn popwflevel) {
        this.popwflevel = popwflevel;
    }

    public RichColumn getPopwflevel() {
        return popwflevel;
    }

    public void setPopusrid(RichColumn popusrid) {
        this.popusrid = popusrid;
    }

    public RichColumn getPopusrid() {
        return popusrid;
    }

    public void setPopadddialoglistnr(RichDialog popadddialoglistnr) {
        this.popadddialoglistnr = popadddialoglistnr;
    }

    public RichDialog getPopadddialoglistnr() {
        return popadddialoglistnr;
    }

    public void popupusridvalidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // System.out.println("object ---->"+object);
        DefineWorkFlowAMImpl am = (DefineWorkFlowAMImpl)resolvElDC("DefineWorkFlowAMDataControl");
        ViewObjectImpl vo = am.getAppWfUsrLvlMap1();
        AppWfUsrLvlMapVORowImpl row = (AppWfUsrLvlMapVORowImpl)vo.getCurrentRow();
        int count = 0;
        RowSet rs = row.getUserLovVO1();
        Row ro = rs.getRowAtRangeIndex(Integer.parseInt(object.toString()));
        Object x = ro.getAttribute("UsrId");
        if (x != null) {
            //    System.out.println("VALUES--"+x.toString());
        }


        //     if(vo.getCurrentRow().getAttribute("UsrId")!=null){
        //     String usr=cur.getAttribute("UsrId").toString();
        if (object != null) {
            // System.out.println(usrIdBind.getValue()+"Obj--"+object.toString());
            //Row[] rw=vo1.getFilteredRows("DocId", object.toString());
            /* RowSetIterator rsi= vo.createRowSetIterator(null);
        while(rsi.hasNext()){
            Row r=rsi.next();
            System.out.println("--"+r.getAttribute("UsrId"));
            if(r.getAttribute("UsrId")!=null && r!=vo.getCurrentRow())
                {

                if(x.toString().equalsIgnoreCase(r.getAttribute("UsrId").toString())){
                  count=count+1;
                }
            }
        }
        rsi.closeRowSetIterator();
         */
            Long rangeSize = vo.getEstimatedRowCount();
            String accName = "";
            String nameDesc = x.toString();
            vo.setRangeSize(rangeSize.intValue()); //set to original range
            Row rArray[] = vo.getAllRowsInRange();

            for (Row r : rArray) {

                //   if(!r.equals(cur)){
                try {
                    accName = r.getAttribute("UsrId").toString();
                } catch (NullPointerException npe) {
                    // System.out.println("NPE:"+npe);
                    accName = "";
                }
                //  System.out.println(count);
                if (accName.equalsIgnoreCase(nameDesc)) {
                    count = count + 1;
                }
                //  }

                //       }

                //if(modeAaya.equalsIgnoreCase("A")){
                if (count > 0) {
                    String msg2 =
                        resolvEl("#{bundle['MSG.559']}"); // "User can be only assigned against a single level.";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    throw new ValidatorException(message2);
                    //    fc.addMessage(docidpopup.getClientId(),message2);
                }

            }
        }
        vo.executeQuery();
    }


    public void setUsrIdBind(RichSelectOneChoice usrIdBind) {
        this.usrIdBind = usrIdBind;
    }

    public RichSelectOneChoice getUsrIdBind() {
        return usrIdBind;
    }

    public void setModeAaya(String modeAaya) {
        this.modeAaya = modeAaya;
    }

    public String getModeAaya() {
        return modeAaya;
    }

    public void setBindWFName(RichInputText bindWFName) {
        this.bindWFName = bindWFName;
    }

    public RichInputText getBindWFName() {
        return bindWFName;
    }


    public void search(ActionEvent actionEvent) {
        //    DefineWorkFlowAMImpl am = (DefineWorkFlowAMImpl)resolvElDC("DefineWorkFlowAMDataControl");

        /*  if (wfNameBind1.getValue() != null) {
                System.out.println("wf name="+wfNameBind1.getValue().toString());
                DCBindingContainer iter = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                DCIteratorBinding dcIter = iter.findIteratorBinding("AppWf1Iterator");
                System.out.println("dcIter="+dcIter);

                ViewObject vo = dcIter.getViewObject();
                System.out.println("VO="+vo);
                vo.setNamedWhereClauseParam("BindWFName", wfNameBind1.getValue().toString());
                vo.executeQuery();
            }


            if (docIdBind.getValue() != null) {
                System.out.println("BindDocid="+docIdBind.getValue().toString());
                DCBindingContainer iter = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                DCIteratorBinding dcIter = iter.findIteratorBinding("AppWfDocMap1Iterator");
                System.out.println("dcIter="+dcIter);
                ViewObject vo = dcIter.getViewObject();
                System.out.println("VO="+vo);
                vo.setNamedWhereClauseParam("BindDocid", docIdBind.getValue().toString());
                vo.executeQuery();
            }  */


        Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String orgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String cldid = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");

        DefineWorkFlowAMImpl am = (DefineWorkFlowAMImpl)resolvElDC("DefineWorkFlowAMDataControl");


        String wfid = null;
        if (docIdBind.getValue() != null) {
            am.getAppWf1().setWhereClause(null);
            //    System.out.println("---->"+transDocIdBind.getValue().toString()+"---->");
            wfid =
(String)(callStoredFunction(VARCHAR, "APP.PKG_APP_WF.GET_WF_NAME (?,?,?,?)", new Object[] { slocid, cldid, orgid,
                                                                                            docIdBind.getValue().toString() }));
            am.getAppWf1().setWhereClause("WF_NM = '" + wfid + "'");
            am.getAppWf1().executeQuery();
            // System.out.println("DOC ID======="); */
            DualVOImpl dualVO1 = am.getDualVO1();
            Row currentRow = dualVO1.getCurrentRow();
            System.out.println("Cr is " + currentRow);
            if (currentRow.getAttribute("DocId") != null) {
                System.out.println("currentRow" + currentRow);
                Integer val = Integer.parseInt(currentRow.getAttribute("DocId").toString());
                wfid =
(String)(callStoredFunction(VARCHAR, "APP.PKG_APP_WF.GET_WF_NAME (?,?,?,?)", new Object[] { slocid, cldid, orgid,
                                                                                            val }));
                System.out.println("Value is :" + wfid);
                if (wfid != null) {
                    am.getAppWf1().setWhereClause("WF_NM = '" + wfid + "'");
                }
                /*   System.out.println("Wf name is " + wfNameBind1.getValue() + "     --- " +
                                   currentRow.getAttribute("transWfNm").toString());
                if (wfNameBind1.getValue() != null) {
                    am.getAppWf1().setWhereClause("WF_NM like '%" + currentRow.getAttribute("transWfNm").toString() +
                                                  "%'");
                } */
                am.getAppWf1().executeQuery();
            } else {
                System.out.println("In else part");
                          
               
               
                if (wfNameBind1.getValue() == null) {
                    System.out.println("inside else of if part");
                    am.getAppWf1().setWhereClause(null);
                    am.getAppWf1().executeQuery();
                    //    System.out.println("NULL DOC NM :"+wfNameBind1.getValue());
                } else {
                    System.out.println("inside else else part");





                    try {
                        System.out.println("Came in try");

                        am.getAppWf1().setWhereClause(null);
                        //  System.out.println("NAME  =======");
                        //am.getAppWf1().setWhereClause("WF_NM like '%"+wfNameBind1.getValue().toString()+"%'");
                        am.getAppWf1().setWhereClause("WF_NM like '%" +
                                                      currentRow.getAttribute("transWfNm").toString() + "%'");
                        am.getAppWf1().executeQuery();
                    } catch (Exception e) {
                        //   System.out.println("Null pointer coz no value in search field.");
                        e.printStackTrace();
                    }
                }
            }
            //  System.out.println("WF---?"+wfid);

        } //catch (Exception e) {
        // System.out.println("Null pointer exception.");
        // e.printStackTrace();

        // }

    }

    public void reset(ActionEvent actionEvent) {
        DefineWorkFlowAMImpl am = (DefineWorkFlowAMImpl)resolvElDC("DefineWorkFlowAMDataControl");
        am.getAppWf1().setWhereClause(null);
        am.getAppWf1().executeQuery();
        am.getDualVO1().executeQuery(); // System.out.println("Resetted!");
    }

    public void setWfNameBind(RichInputText wfNameBind) {
        this.wfNameBind = wfNameBind;
    }

    public RichInputText getWfNameBind() {
        return wfNameBind;
    }


    public void setTransDocIdBind(RichSelectOneChoice transDocIdBind) {
        this.transDocIdBind = transDocIdBind;
    }

    public RichSelectOneChoice getTransDocIdBind() {
        return transDocIdBind;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setWfNameBind1(RichInputListOfValues wfNameBind1) {
        this.wfNameBind1 = wfNameBind1;
    }

    public RichInputListOfValues getWfNameBind1() {
        return wfNameBind1;
    }

    public void setBindadd(RichCommandImageLink bindadd) {
        this.bindadd = bindadd;
    }

    public RichCommandImageLink getBindadd() {
        return bindadd;
    }

    public void setCancelBind(RichCommandImageLink cancelBind) {
        this.cancelBind = cancelBind;
    }

    public RichCommandImageLink getCancelBind() {
        return cancelBind;
    }

    public void setTransNameBind(RichInputListOfValues transNameBind) {
        this.transNameBind = transNameBind;
    }

    public RichInputListOfValues getTransNameBind() {
        return transNameBind;
    }

    public void setDocIdBind(RichSelectOneChoice docIdBind) {
        this.docIdBind = docIdBind;
    }

    public RichSelectOneChoice getDocIdBind() {
        return docIdBind;
    }
}
