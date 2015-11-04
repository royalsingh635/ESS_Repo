package appgraphaccesstousr.view.bean;

import appgraphaccesstousr.model.module.AppGraphAccessToUsrAMImpl;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.server.ViewObjectImpl;

public class AppGrphAccessToUsrBean {
    private RichTable tableBindVar;
    private RichSelectOneChoice userTypeIdBindVar;
    private RichSelectOneChoice roleIdBindVar;
    private RichCommandButton generateButton;
    private RichCommandButton addButton;
    private RichSelectOneChoice trendId;
    private RichSelectOneChoice roleId;
    private RichSelectOneChoice userid;

    private RichSelectOneRadio userTypeBindVar;
    private RichCommandButton cancelButton;
    private String Mode = "V";
    private String usrId = "V";
    private String rleId = "V";
    private String deletemode = "N";
    private Integer onloadVal;
    private RichSelectOneChoice graphNewId;

    public AppGrphAccessToUsrBean() {
    }

    public String SearchAction() {
        AppGraphAccessToUsrAMImpl am = (AppGraphAccessToUsrAMImpl)resolvElDC("AppGraphAccessToUsrAMDataControl");
        ViewObjectImpl v = am.getAppSecUsrGrph();
        Integer Slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        Long Grphid = Long.parseLong(resolvEl("#{pageFlowScope.GRAPH_ID}").toString());
        
        
        v.setNamedWhereClauseParam("SlocIdVar", Slocid);
        v.setNamedWhereClauseParam("GraphId", Grphid);
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams");
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tableBindVar);
        return null;
    }

    public String ResetAction() {
        AppGraphAccessToUsrAMImpl am = (AppGraphAccessToUsrAMImpl)resolvElDC("AppGraphAccessToUsrAMDataControl");
        ViewObjectImpl v = am.getAppSecUsrGrph();
        v.executeQuery();
        Integer Slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        Long Grphid = Long.parseLong(resolvEl("#{pageFlowScope.GRAPH_ID}").toString());

        trendId.setValue("");
        roleId.setValue("");
        userid.setValue("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(trendId);
        AdfFacesContext.getCurrentInstance().addPartialTarget(roleId);
        AdfFacesContext.getCurrentInstance().addPartialTarget(userid);


        v.setNamedWhereClauseParam("SlocIdVar", Slocid);
        v.setNamedWhereClauseParam("GraphId", Grphid);


        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams");
        operationBinding.execute();
        
        
        
        operationBinding.execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tableBindVar);

        return "Reset";
    }

    public void setTableBindVar(RichTable tableBindVar) {
        this.tableBindVar = tableBindVar;
    }

    public RichTable getTableBindVar() {
        return tableBindVar;
    }

    public String DeleteSelectedAction() {
        AppGraphAccessToUsrAMImpl am = (AppGraphAccessToUsrAMImpl)resolvElDC("AppGraphAccessToUsrAMDataControl");
        ViewObjectImpl v = am.getAppSecUsrGrph();
        RowSetIterator createRowSetIterator = v.createRowSetIterator(null);
        while (createRowSetIterator.hasNext()) {
            Row row = createRowSetIterator.next();
            if (row.getAttribute("DeleteFlag") != null) {
                if (row.getAttribute("DeleteFlag").toString().equalsIgnoreCase("Y")) {
                    row.remove();
                }

            }
        }
        createRowSetIterator.closeRowSetIterator();
        deletemode = "Y";
        AdfFacesContext.getCurrentInstance().addPartialTarget(tableBindVar);
        return null;
    }

    public String SelectAllAction() {
        AppGraphAccessToUsrAMImpl am = (AppGraphAccessToUsrAMImpl)resolvElDC("AppGraphAccessToUsrAMDataControl");
        ViewObjectImpl v = am.getAppSecUsrGrph();
        RowSetIterator createRowSetIterator = v.createRowSetIterator(null);
        while (createRowSetIterator.hasNext()) {
            Row row = createRowSetIterator.next();
            String s = "Y";
            row.setAttribute("DeleteFlag", s);


        }
        createRowSetIterator.closeRowSetIterator();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tableBindVar);
        return null;

    }

    public String DeselectAllAction() {
        AppGraphAccessToUsrAMImpl am = (AppGraphAccessToUsrAMImpl)resolvElDC("AppGraphAccessToUsrAMDataControl");
        ViewObjectImpl v = am.getAppSecUsrGrph();
        RowSetIterator createRowSetIterator = v.createRowSetIterator(null);
        while (createRowSetIterator.hasNext()) {
            Row row = createRowSetIterator.next();
            String s = "N";
            row.setAttribute("DeleteFlag", s);


        }
        createRowSetIterator.closeRowSetIterator();
        AdfFacesContext.getCurrentInstance().addPartialTarget(tableBindVar);
        return null;

    }

    public String SaveAction() {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();


        if (operationBinding.getErrors().isEmpty()) {
            OperationBinding operationBindingroll = bindings.getOperationBinding("Execute");
            operationBindingroll.execute();
            String msg = resolvElDCMsg("#{bundle['MSG.91']}").toString();
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_INFO);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        deletemode = "N";
        AdfFacesContext.getCurrentInstance().addPartialTarget(tableBindVar);

        return null;
    }

    public String CancelAction() {
        AppGraphAccessToUsrAMImpl am = (AppGraphAccessToUsrAMImpl)resolvElDC("AppGraphAccessToUsrAMDataControl");
        ViewObjectImpl v = am.getAddRecord();
        am.getDBTransaction().rollback();
        v.executeQuery();
        deletemode = "N";
        AdfFacesContext.getCurrentInstance().addPartialTarget(tableBindVar);
        return null;
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

    public void UserTypeChangeListner(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("R")) {

            userTypeIdBindVar.setValue("");
            rleId = "E";
            usrId = "V";

        }

        if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("U")) {

            roleIdBindVar.setValue("");
            rleId = "V";
            usrId = "E";
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(roleIdBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(userTypeIdBindVar);
    }

    public String AddAction() {
        /* BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        AppGraphAccessToUsrAMImpl am = (AppGraphAccessToUsrAMImpl)resolvElDC("AppGraphAccessToUsrAMDataControl"); */
        //ViewObjectImpl v = am.getAddRecord();
        //Row currentRow = v.getCurrentRow();

        Long Graphid = Long.parseLong(resolvEl("#{pageFlowScope.GRAPH_ID}").toString());


        userTypeBindVar.setValue("U");
        graphNewId.setValue(Graphid);

        Mode = "E";
        usrId = "E";
        rleId = "V";

        AdfFacesContext.getCurrentInstance().addPartialTarget(generateButton);
        AdfFacesContext.getCurrentInstance().addPartialTarget(addButton);
        AdfFacesContext.getCurrentInstance().addPartialTarget(graphNewId);
        AdfFacesContext.getCurrentInstance().addPartialTarget(userTypeBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(userTypeIdBindVar);

        AdfFacesContext.getCurrentInstance().addPartialTarget(roleIdBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cancelButton);
        return null;
    }

    public String GenerateAction() {

        AppGraphAccessToUsrAMImpl am = (AppGraphAccessToUsrAMImpl)resolvElDC("AppGraphAccessToUsrAMDataControl");
        

        String posType = null;
        Integer puserTypeid = null;
        String usertype = null;
        Integer userid = null;

        Integer roleid = null;
        Long GraphId = null;


        userTypeBindVar.setValue(null);
        graphNewId.setValue(null);
        roleIdBindVar.setValue(null);

        if (userTypeBindVar.getValue() != null) {
            usertype = userTypeBindVar.getValue().toString();

            if (userTypeIdBindVar.getValue() != null && userTypeBindVar.getValue() == "U") {
                userid = Integer.parseInt(userTypeIdBindVar.getValue().toString());

                puserTypeid = userid;

            }

            if (roleIdBindVar.getValue() != null && userTypeBindVar.getValue()=="R") {
                roleid = Integer.parseInt(roleIdBindVar.getValue().toString());

                puserTypeid = roleid;
            }

        }


        if (resolvEl("#{pageFlowScope.GRAPH_ID}") != null) {
            GraphId = Long.parseLong(resolvEl("#{pageFlowScope.GRAPH_ID}").toString());
        }
        Integer pusrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String Orgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();

        String commitFlg = "Y";

        String returnVal =
            callStoredFunction(VARCHAR, "app.pkg_grph.fn_gen_grph_usr_lines(?,?,?,?,?,?,?,?,?)", new Object[] { Orgid,
                                                                                                                slocId,
                                                                                                                usertype,
                                                                                                                puserTypeid,
                                                                                                                posType,
                                                                                                                GraphId,
                                                                                                                pusrId,
                                                                                                                null,
                                                                                                                commitFlg }).toString();
        if (returnVal.equals("Y")) {
            ViewObjectImpl e = am.getAppSecUsrGrph();
            e.executeQuery();

            String msg = resolvElDCMsg("#{bundle['MSG.545']}").toString();
            FacesMessage message = new FacesMessage(msg);
            message.setSeverity(FacesMessage.SEVERITY_INFO);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);

            Mode = "V";
            usrId = "V";
            rleId = "V";
            AdfFacesContext.getCurrentInstance().addPartialTarget(generateButton);
            AdfFacesContext.getCurrentInstance().addPartialTarget(addButton);

            AdfFacesContext.getCurrentInstance().addPartialTarget(userTypeBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(userTypeIdBindVar);

            AdfFacesContext.getCurrentInstance().addPartialTarget(roleIdBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cancelButton);
        }

        return null;
    }

    private static int VARCHAR = Types.VARCHAR;

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        AppGraphAccessToUsrAMImpl am = (AppGraphAccessToUsrAMImpl)resolvElDC("AppGraphAccessToUsrAMDataControl");
        try {
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();
            System.out.println(st.getObject(1));
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

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }

    public void setUserTypeIdBindVar(RichSelectOneChoice userTypeIdBindVar) {
        this.userTypeIdBindVar = userTypeIdBindVar;
    }

    public RichSelectOneChoice getUserTypeIdBindVar() {
        return userTypeIdBindVar;
    }


    public void setRoleIdBindVar(RichSelectOneChoice roleIdBindVar) {
        this.roleIdBindVar = roleIdBindVar;
    }

    public RichSelectOneChoice getRoleIdBindVar() {
        return roleIdBindVar;
    }


    public void setGenerateButton(RichCommandButton generateButton) {
        this.generateButton = generateButton;
    }

    public RichCommandButton getGenerateButton() {
        return generateButton;
    }

    public void setAddButton(RichCommandButton addButton) {
        this.addButton = addButton;
    }

    public RichCommandButton getAddButton() {
        return addButton;
    }


    public void setTrendId(RichSelectOneChoice trendId) {
        this.trendId = trendId;
    }

    public RichSelectOneChoice getTrendId() {
        return trendId;
    }

    public void setRoleId(RichSelectOneChoice roleId) {
        this.roleId = roleId;
    }

    public RichSelectOneChoice getRoleId() {
        return roleId;
    }

    public void setUserid(RichSelectOneChoice userid) {
        this.userid = userid;
    }

    public RichSelectOneChoice getUserid() {
        return userid;
    }


    public String cancelNewRecord() {
        AppGraphAccessToUsrAMImpl am = (AppGraphAccessToUsrAMImpl)resolvElDC("AppGraphAccessToUsrAMDataControl");
        //ViewObjectImpl v = am.getAddRecord();
        am.getDBTransaction().rollback();
        //v.executeQuery();
        //Long Graphid = Long.parseLong(resolvEl("#{pageFlowScope.GRAPH_ID}").toString());
        userTypeBindVar.setValue(null);
        graphNewId.setValue(null);
        roleIdBindVar.setValue(null);
        Mode = "V";
        usrId = "V";
        rleId = "V";

        AdfFacesContext.getCurrentInstance().addPartialTarget(generateButton);
        AdfFacesContext.getCurrentInstance().addPartialTarget(addButton);

        AdfFacesContext.getCurrentInstance().addPartialTarget(userTypeBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(userTypeIdBindVar);

        AdfFacesContext.getCurrentInstance().addPartialTarget(roleIdBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cancelButton);
        return null;
    }


    public void setCancelButton(RichCommandButton cancelButton) {
        this.cancelButton = cancelButton;
    }

    public RichCommandButton getCancelButton() {
        return cancelButton;
    }


    public void setUserTypeBindVar(RichSelectOneRadio userTypeBindVar) {
        this.userTypeBindVar = userTypeBindVar;
    }

    public RichSelectOneRadio getUserTypeBindVar() {
        return userTypeBindVar;
    }

    public void setMode(String Mode) {
        this.Mode = Mode;
    }

    public String getMode() {
        return Mode;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setRleId(String rleId) {
        this.rleId = rleId;
    }

    public String getRleId() {
        return rleId;
    }

    public void setDeletemode(String deletemode) {
        this.deletemode = deletemode;
    }

    public String getDeletemode() {
        return deletemode;
    }

    public void setOnloadVal(Integer onloadVal) {
        this.onloadVal = onloadVal;
    }

    public Integer getOnloadVal() {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("on_load_page");
        Object ret = operationBinding.execute();

        return (Integer)ret;
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void setGraphNewId(RichSelectOneChoice graphNewId) {
        this.graphNewId = graphNewId;
    }

    public RichSelectOneChoice getGraphNewId() {
        return graphNewId;
    }
}
