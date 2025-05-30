package appEoAddress.model.views;

import java.sql.ResultSet;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Jun 04 16:34:31 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AddressForEoAddLOVImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public AddressForEoAddLOVImpl() {
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


    /**
     * Returns the variable value for AddressName.
     * @return variable value for AddressName
     */
    public String getAddressName() {
        return (String)ensureVariableManager().getVariableValue("AddressName");
    }

    /**
     * Sets <code>value</code> for variable AddressName.
     * @param value value to bind as AddressName
     */
    public void setAddressName(String value) {
        ensureVariableManager().setVariableValue("AddressName", value);
    }

    int counter=0;
       protected ViewRowImpl createRowFromResultSet(Object object, ResultSet resultSet) {
           counter++;
       ViewRowImpl row = super.createRowFromResultSet(object, resultSet);
      System.out.println("Row fetched with key: " + row.getKey() + ", for VO: " + this.getName() + " row count: " + counter);

       return row;
       }

    /**
     * Returns the bind variable value for BindCldID.
     * @return bind variable value for BindCldID
     */
    public String getBindCldID() {
        return (String)getNamedWhereClauseParam("BindCldID");
    }

    /**
     * Sets <code>value</code> for bind variable BindCldID.
     * @param value value to bind as BindCldID
     */
    public void setBindCldID(String value) {
        setNamedWhereClauseParam("BindCldID", value);
    }

    /**
     * Returns the bind variable value for BindSlocID.
     * @return bind variable value for BindSlocID
     */
    public Integer getBindSlocID() {
        return (Integer)getNamedWhereClauseParam("BindSlocID");
    }

    /**
     * Sets <code>value</code> for bind variable BindSlocID.
     * @param value value to bind as BindSlocID
     */
    public void setBindSlocID(Integer value) {
        setNamedWhereClauseParam("BindSlocID", value);
    }

    /**
     * Returns the bind variable value for BindEoId.
     * @return bind variable value for BindEoId
     */
    public Integer getBindEoId() {
        return (Integer)getNamedWhereClauseParam("BindEoId");
    }

    /**
     * Sets <code>value</code> for bind variable BindEoId.
     * @param value value to bind as BindEoId
     */
    public void setBindEoId(Integer value) {
        setNamedWhereClauseParam("BindEoId", value);
    }
}
