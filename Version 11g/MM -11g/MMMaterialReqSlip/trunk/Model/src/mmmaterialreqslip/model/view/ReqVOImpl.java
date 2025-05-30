package mmmaterialreqslip.model.view;

import java.sql.ResultSet;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;


import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.ViewRowSetImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Aug 16 12:00:53 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ReqVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public ReqVOImpl() {
    }


    /**
     * executeQueryForCollection - overridden for custom java data source support.
     */
    protected void executeQueryForCollection(Object qc, Object[] params, int noUserParams) {
        super.executeQueryForCollection(qc, params, noUserParams);
    }

    /**
     * hasNextForCollection - overridden for custom java data source support.
     */
    protected boolean hasNextForCollection(Object qc) {
        boolean bRet = super.hasNextForCollection(qc);
        return bRet;
    }

    /**
     * createRowFromResultSet - overridden for custom java data source support.
     */
    protected ViewRowImpl createRowFromResultSet(Object qc, ResultSet resultSet) {
        ViewRowImpl value = super.createRowFromResultSet(qc, resultSet);
        return value;
    }

    /**
     * getQueryHitCount - overridden for custom java data source support.
     */
    public long getQueryHitCount(ViewRowSetImpl viewRowSet) {
        long value = super.getQueryHitCount(viewRowSet);
        return value;
    }


    /**
     * Returns the bind variable value for cldIdBind.
     * @return bind variable value for cldIdBind
     */
    public String getcldIdBind() {
        return (String)getNamedWhereClauseParam("cldIdBind");
    }

    /**
     * Sets <code>value</code> for bind variable cldIdBind.
     * @param value value to bind as cldIdBind
     */
    public void setcldIdBind(String value) {
        setNamedWhereClauseParam("cldIdBind", value);
    }

    /**
     * Returns the bind variable value for slocIdBind.
     * @return bind variable value for slocIdBind
     */
    public Integer getslocIdBind() {
        return (Integer)getNamedWhereClauseParam("slocIdBind");
    }

    /**
     * Sets <code>value</code> for bind variable slocIdBind.
     * @param value value to bind as slocIdBind
     */
    public void setslocIdBind(Integer value) {
        setNamedWhereClauseParam("slocIdBind", value);
    }

    /**
     * Returns the bind variable value for orgIdBind.
     * @return bind variable value for orgIdBind
     */
    public String getorgIdBind() {
        return (String)getNamedWhereClauseParam("orgIdBind");
    }

    /**
     * Sets <code>value</code> for bind variable orgIdBind.
     * @param value value to bind as orgIdBind
     */
    public void setorgIdBind(String value) {
        setNamedWhereClauseParam("orgIdBind", value);
    }
}
