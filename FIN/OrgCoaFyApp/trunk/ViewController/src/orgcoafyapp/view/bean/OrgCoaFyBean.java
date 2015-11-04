package orgcoafyapp.view.bean;

//import appOpeningBalance.model.service.AppOpeningBalanceAMImpl;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.jbo.JboException;

import orgcoafyapp.model.service.OrgCoaFyAMImpl;

public class OrgCoaFyBean {
    private String paramVal = "A";
    private RichInputText orgFyIdPgBind;

    public OrgCoaFyBean() {
    }

    private String Cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        OrgCoaFyAMImpl am = (OrgCoaFyAMImpl)resolvElDC("OrgCoaFyAMDataControl");
        try {
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
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
                    throw new JboException(e);
                }
            }
        }
    }

    /* public Object resolvEl(Object data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, (String)data, Object.class);
        Object Message = valueExp.getValue(elContext);
        return Message;
    } */

    public Object resolvEl(String data) {

        ADFContext adfCtx = ADFContext.getCurrent();
        ELContext elContext = adfCtx.getELContext();
        ValueExpression valueExp = adfCtx.getExpressionFactory().createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext).toString();

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

    public void setOrgFyIdPgBind(RichInputText orgFyIdPgBind) {
        this.orgFyIdPgBind = orgFyIdPgBind;
    }

    public RichInputText getOrgFyIdPgBind() {
        return orgFyIdPgBind;
    }

    public void setParamVal(String paramVal) {
        this.paramVal = paramVal;
    }

    public String getParamVal() {
        return paramVal;
    }

    public String opBalAction() {
        System.out.println("6875675564543543");
        System.out.println("Inside chkOpBalAllowedForOrg-------");

        String Org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Object fyIdParam = orgFyIdPgBind.getValue().toString();
        /* if (resolvEl("#{bindings.OrgCoaFy}") != null) {
            fyIdParam = resolvEl("#{bindings.OrgCoaFy}");
        } */


        // called function when FY_ORG_ID isn't check in Organization setup.

        System.out.println("Cld_Id = " + Cld_Id + " Org_Id = " + Org_Id + " fyId = " + fyIdParam);

        Object allowForOrg =
            callStoredFunction(Types.VARCHAR, "fin.fn_CHK_OP_BAL_ALL_ORG(?,?,?)", new Object[] { Cld_Id, Org_Id,
                                                                                                 fyIdParam });
        /*  if (allowForOrg.toString().equalsIgnoreCase("N")) {
            paramVal = "V";
        } else {
            paramVal = "A";
        } */
        System.out.println("allowForOrg--------------------------------" + allowForOrg);

        if (allowForOrg.toString().equalsIgnoreCase("N")) {
            paramVal = "V";
            FacesMessage fm = new FacesMessage("Opening Balance is not allowed for this Organizations...!!!");
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, fm);
        } else {
            paramVal = "A";
            System.out.println("OP Bal is allowed....!!!!");
        }
        System.out.println("Going to task flow---------");
        return "summary";
    }
}
