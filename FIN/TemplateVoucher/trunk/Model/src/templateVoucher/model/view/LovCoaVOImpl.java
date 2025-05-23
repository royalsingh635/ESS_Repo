package templateVoucher.model.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jbo.ViewCriteria;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.QueryCollection;
import oracle.jbo.server.SQLBuilder;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.ViewRowSetImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Dec 30 17:55:40 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LovCoaVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public LovCoaVOImpl() {
    }

    /**
     * Returns the variable value for BindCogNm.
     * @return variable value for BindCogNm
     */
    public String getBindCogNm() {
        return (String)ensureVariableManager().getVariableValue("BindCogNm");
    }

    /**
     * Sets <code>value</code> for variable BindCogNm.
     * @param value value to bind as BindCogNm
     */
    public void setBindCogNm(String value) {
        ensureVariableManager().setVariableValue("BindCogNm", value);
    }

    /**
     * Returns the variable value for BindAccTyp1.
     * @return variable value for BindAccTyp1
     */
    public Integer getBindAccTyp1() {
        return (Integer)ensureVariableManager().getVariableValue("BindAccTyp1");
    }

    /**
     * Sets <code>value</code> for variable BindAccTyp1.
     * @param value value to bind as BindAccTyp1
     */
    public void setBindAccTyp1(Integer value) {
        ensureVariableManager().setVariableValue("BindAccTyp1", value);
    }

    /**
     * Returns the variable value for BindCoaNm.
     * @return variable value for BindCoaNm
     */
    public String getBindCoaNm() {
        return (String)ensureVariableManager().getVariableValue("BindCoaNm");
    }

    /**
     * Sets <code>value</code> for variable BindCoaNm.
     * @param value value to bind as BindCoaNm
     */
    public void setBindCoaNm(String value) {
        ensureVariableManager().setVariableValue("BindCoaNm", value);
    }

    /**
     * Returns the bind variable value for BindOrgId.
     * @return bind variable value for BindOrgId
     */
    public String getBindOrgId() {
        return (String)getNamedWhereClauseParam("BindOrgId");
    }

    /**
     * Sets <code>value</code> for bind variable BindOrgId.
     * @param value value to bind as BindOrgId
     */
    public void setBindOrgId(String value) {
        setNamedWhereClauseParam("BindOrgId", value);
    }

    /**
     * Returns the variable value for AccTypBind.
     * @return variable value for AccTypBind
     */
    public Integer getAccTypBind() {
        return (Integer)ensureVariableManager().getVariableValue("AccTypBind");
    }

    /**
     * Sets <code>value</code> for variable AccTypBind.
     * @param value value to bind as AccTypBind
     */
    public void setAccTypBind(Integer value) {
        ensureVariableManager().setVariableValue("AccTypBind", value);
    }

    /**
     * Returns the variable value for BindAccTyp2.
     * @return variable value for BindAccTyp2
     */
    public Integer getBindAccTyp2() {
        return (Integer)ensureVariableManager().getVariableValue("BindAccTyp2");
    }

    /**
     * Sets <code>value</code> for variable BindAccTyp2.
     * @param value value to bind as BindAccTyp2
     */
    public void setBindAccTyp2(Integer value) {
        ensureVariableManager().setVariableValue("BindAccTyp2", value);
    }

    /**
     * Returns the bind variable value for BindHoOrgId.
     * @return bind variable value for BindHoOrgId
     */
    public String getBindHoOrgId() {
        return (String)getNamedWhereClauseParam("BindHoOrgId");
    }

    /**
     * Sets <code>value</code> for bind variable BindHoOrgId.
     * @param value value to bind as BindHoOrgId
     */
    public void setBindHoOrgId(String value) {
        setNamedWhereClauseParam("BindHoOrgId", value);
    }

    /**
     * Returns the bind variable value for BindCldId.
     * @return bind variable value for BindCldId
     */
    public String getBindCldId() {
        return (String)getNamedWhereClauseParam("BindCldId");
    }

    /**
     * Sets <code>value</code> for bind variable BindCldId.
     * @param value value to bind as BindCldId
     */
    public void setBindCldId(String value) {
        setNamedWhereClauseParam("BindCldId", value);
    }

    /**
     * Returns the bind variable value for BindSlocId.
     * @return bind variable value for BindSlocId
     */
    public Integer getBindSlocId() {
        return (Integer)getNamedWhereClauseParam("BindSlocId");
    }

    /**
     * Sets <code>value</code> for bind variable BindSlocId.
     * @param value value to bind as BindSlocId
     */
    public void setBindSlocId(Integer value) {
        setNamedWhereClauseParam("BindSlocId", value);
    }
   /*  int counter=0;
        protected ViewRowImpl createRowFromResultSet(Object object, ResultSet resultSet) {
            counter++;
        ViewRowImpl row = super.createRowFromResultSet(object, resultSet);
       System.out.println("Row fetched with key: " + row.getKey() + ", for VO: " + this.getName() + " row count: " + counter);

        return row;
        } */
    /**
     * Returns the variable value for BindAccTypeDesc.
     * @return variable value for BindAccTypeDesc
     */
    public String getBindAccTypeDesc() {
        return (String)ensureVariableManager().getVariableValue("BindAccTypeDesc");
    }

    /**
     * Sets <code>value</code> for variable BindAccTypeDesc.
     * @param value value to bind as BindAccTypeDesc
     */
    public void setBindAccTypeDesc(String value) {
        ensureVariableManager().setVariableValue("BindAccTypeDesc", value);
    }

    /**
     * Returns the bind variable value for BindCoaId.
     * @return bind variable value for BindCoaId
     */
    public Integer getBindCoaId() {
        return (Integer)getNamedWhereClauseParam("BindCoaId");
    }

    /**
     * Sets <code>value</code> for bind variable BindCoaId.
     * @param value value to bind as BindCoaId
     */
    public void setBindCoaId(Integer value) {
        setNamedWhereClauseParam("BindCoaId", value);
    }
}
