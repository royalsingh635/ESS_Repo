package opbalforallcoa.view.bean;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.jbo.domain.Number;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.validator.ValidatorException;

import opbalforallcoa.model.module.OpBalCoasAMImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.ValidationException;
import oracle.jbo.rules.JboPrecisionScaleValidator;
import oracle.jbo.server.ViewObjectImpl;

public class OpbalForAllCoas {

    private RichTable tabBind;
    Integer count = Integer.parseInt(getBindings().getOperationBinding("on_load_page").execute().toString());
    private RichSelectOneChoice fyBindVar;
    private RichInputListOfValues bindCoaId;
    private String Cld_Id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();

    public OpbalForAllCoas() {
    }

    public String resetAction() {
        BindingContainer bindings = getBindings();
        OperationBinding createOpBAddrees = bindings.getOperationBinding("resetAction");
        createOpBAddrees.execute();
        fyBindVar.setValue("");
        bindCoaId.setValue("");
        return null;
    }

    public String searchAction() {
        BindingContainer bindings = getBindings();
        OperationBinding createOpBAddrees = bindings.getOperationBinding("searchAction");
        createOpBAddrees.execute();
        return null;
    }


    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();
        vc.setPrecision(precision);
        vc.setScale(scale);
        return vc.validateValue(total);
    }


    public void validatorListener(FacesContext facesContext, UIComponent uIComponent, Object object) {


        //System.out.println("Object : "+object);


        if (object != null) {
            System.out.println("Validator Initiating...............");
            Integer fyId = null;
            if (fyBindVar.getValue() != null) {
                fyId = Integer.parseInt(fyBindVar.getValue().toString());
                System.out.println("fyBindVar Value-----" + fyId);
            }

            Integer fyIdParam = null;
            if (resolvEl("#{pageFlowScope.PARAM_FY_ID}") != null) {
                fyIdParam = Integer.parseInt(resolvEl("#{pageFlowScope.PARAM_FY_ID}").toString());
                System.out.println("FY ID for param---" + fyIdParam);
            } else {
                fyIdParam = Integer.parseInt(fyId.toString());
                System.out.println("In else FY ID for param---" + fyIdParam);
            }

            String Org_Id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();


            // called function when fy_allowe_op_bal_for_next_year and prev year isn't check in Organization setup.
            Object allowForOrg =
                callStoredFunction(Types.VARCHAR, "fin.fn_CHK_OP_BAL_ALL_ORG(?,?,?)", new Object[] { Cld_Id, Org_Id,
                                                                                                     fyId });
            System.out.println("allowForOrg--------------------------------" + allowForOrg);
            oracle.jbo.domain.Number n = (oracle.jbo.domain.Number)object;
            Boolean isValid = isPrecisionValid(26, 6, n);
            if (allowForOrg != null) {
                if (allowForOrg.toString().equalsIgnoreCase("N")) {
                    String fm = "Opening Balance is not allowed for this Organizations...!!!";

                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, fm, null));

                } else if (n.compareTo(Number.zero()) < 0) {

                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  (String)resolvEl("#{bundle['MSG.553']}"), null));

                } else if (n.compareTo(Number.zero()) == 0) {

                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  (String)resolvEl("#{bundle['MSG.1332']}"), null));

                } else if (!isValid) {

                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  (String)resolvEl("#{bundle['MSG.57']}"), null));

                }
            }
        }

    }

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            OpBalCoasAMImpl am = (OpBalCoasAMImpl)resolvElDC("OpBalCoasAMDataControl");
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
                    e.printStackTrace();
                }
            }
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


    public Object resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object msg = valueExp.getValue(elContext);
        return msg;
    }


    public void setBindCoaId(RichInputListOfValues bindCoaId) {
        this.bindCoaId = bindCoaId;
    }

    public RichInputListOfValues getBindCoaId() {
        return bindCoaId;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setTabBind(RichTable tabBind) {
        this.tabBind = tabBind;
    }

    public RichTable getTabBind() {
        return tabBind;
    }

    public void setFyBindVar(RichSelectOneChoice fyBindVar) {
        this.fyBindVar = fyBindVar;
    }

    public RichSelectOneChoice getFyBindVar() {
        return fyBindVar;
    }


    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }
}
