package trnpvhclschdlapp.model.views;

import java.sql.ResultSet;

import oracle.jbo.Row;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.ViewRowSetImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Oct 29 17:22:48 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class VhclSchdlCalendarVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public VhclSchdlCalendarVOImpl() {
    }

    /**
     * Returns the bind variable value for cldIdBind.
     * @return bind variable value for cldIdBind
     */
    public String getcldIdBind() {
        return (String) getNamedWhereClauseParam("cldIdBind");
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
        return (Integer) getNamedWhereClauseParam("slocIdBind");
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
        return (String) getNamedWhereClauseParam("orgIdBind");
    }

    /**
     * Sets <code>value</code> for bind variable orgIdBind.
     * @param value value to bind as orgIdBind
     */
    public void setorgIdBind(String value) {
        setNamedWhereClauseParam("orgIdBind", value);
    }

    /**
     * Returns the bind variable value for hoOrgIdBind.
     * @return bind variable value for hoOrgIdBind
     */
    public String gethoOrgIdBind() {
        return (String) getNamedWhereClauseParam("hoOrgIdBind");
    }

    /**
     * Sets <code>value</code> for bind variable hoOrgIdBind.
     * @param value value to bind as hoOrgIdBind
     */
    public void sethoOrgIdBind(String value) {
        setNamedWhereClauseParam("hoOrgIdBind", value);
    }

    /**
     * Returns the bind variable value for strtDtBind.
     * @return bind variable value for strtDtBind
     */
    public Timestamp getstrtDtBind() {
        return (Timestamp) getNamedWhereClauseParam("strtDtBind");
    }

    /**
     * Sets <code>value</code> for bind variable strtDtBind.
     * @param value value to bind as strtDtBind
     */
    public void setstrtDtBind(Timestamp value) {
        System.out.println("Value of strtDtBind" + value);
        setNamedWhereClauseParam("strtDtBind", value);
    }

    /**
     * Returns the bind variable value for endDtBind.
     * @return bind variable value for endDtBind
     */
    public Timestamp getendDtBind() {
        return (Timestamp) getNamedWhereClauseParam("endDtBind");
    }

    /**
     * Sets <code>value</code> for bind variable endDtBind.
     * @param value value to bind as endDtBind
     */
    public void setendDtBind(Timestamp value) {
        System.out.println("Value of endDtBind" + value);
        setNamedWhereClauseParam("endDtBind", value);
    }

    /**
     * Returns the bind variable value for BindTimeZone.
     * @return bind variable value for BindTimeZone
     */
    public String getBindTimeZone() {
        return (String) getNamedWhereClauseParam("BindTimeZone");
    }

    /**
     * Sets <code>value</code> for bind variable BindTimeZone.
     * @param value value to bind as BindTimeZone
     */
    public void setBindTimeZone(String value) {
        setNamedWhereClauseParam("BindTimeZone", value);
    }

    /**
     * executeQueryForCollection - overridden for custom java data source support.
     */
    @Override
    protected void executeQueryForCollection(Object qc, Object[] params, int noUserParams) {
        super.executeQueryForCollection(qc, params, noUserParams);
    }

    /**
     * hasNextForCollection - overridden for custom java data source support.
     */
    @Override
    protected boolean hasNextForCollection(Object qc) {
        boolean bRet = super.hasNextForCollection(qc);
        return bRet;
    }

    /**
     * createRowFromResultSet - overridden for custom java data source support.
     */
    @Override
    protected ViewRowImpl createRowFromResultSet(Object qc, ResultSet resultSet) {
        ViewRowImpl value = super.createRowFromResultSet(qc, resultSet);
        return value;
    }

    /**
     * getQueryHitCount - overridden for custom java data source support.
     */
    @Override
    public long getQueryHitCount(ViewRowSetImpl viewRowSet) {
        long value = super.getQueryHitCount(viewRowSet);
        return value;
    }

    /**
     * getCappedQueryHitCount - overridden for custom java data source support.
     */
    @Override
    public long getCappedQueryHitCount(ViewRowSetImpl viewRowSet, Row[] masterRows, long oldCap, long cap) {
        long value = super.getCappedQueryHitCount(viewRowSet, masterRows, oldCap, cap);
        return value;
    }
}

