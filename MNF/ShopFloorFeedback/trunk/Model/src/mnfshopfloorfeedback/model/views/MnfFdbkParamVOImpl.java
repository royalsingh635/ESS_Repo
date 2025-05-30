package mnfshopfloorfeedback.model.views;

import java.sql.ResultSet;

import oracle.jbo.Row;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.ViewRowSetImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Oct 09 11:57:09 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MnfFdbkParamVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public MnfFdbkParamVOImpl() {
    }

    /**
     * Returns the variable value for cldIdBind.
     * @return variable value for cldIdBind
     */
    public String getcldIdBind() {
        return (String) ensureVariableManager().getVariableValue("cldIdBind");
    }

    /**
     * Sets <code>value</code> for variable cldIdBind.
     * @param value value to bind as cldIdBind
     */
    public void setcldIdBind(String value) {
        ensureVariableManager().setVariableValue("cldIdBind", value);
    }

    /**
     * Returns the variable value for slocIdBind.
     * @return variable value for slocIdBind
     */
    public Integer getslocIdBind() {
        return (Integer) ensureVariableManager().getVariableValue("slocIdBind");
    }

    /**
     * Sets <code>value</code> for variable slocIdBind.
     * @param value value to bind as slocIdBind
     */
    public void setslocIdBind(Integer value) {
        ensureVariableManager().setVariableValue("slocIdBind", value);
    }

    /**
     * Returns the variable value for hoOrgBind.
     * @return variable value for hoOrgBind
     */
    public String gethoOrgBind() {
        return (String) ensureVariableManager().getVariableValue("hoOrgBind");
    }

    /**
     * Sets <code>value</code> for variable hoOrgBind.
     * @param value value to bind as hoOrgBind
     */
    public void sethoOrgBind(String value) {
        ensureVariableManager().setVariableValue("hoOrgBind", value);
    }

    /**
     * Returns the variable value for orgBind.
     * @return variable value for orgBind
     */
    public String getorgBind() {
        return (String) ensureVariableManager().getVariableValue("orgBind");
    }

    /**
     * Sets <code>value</code> for variable orgBind.
     * @param value value to bind as orgBind
     */
    public void setorgBind(String value) {
        ensureVariableManager().setVariableValue("orgBind", value);
    }

    /**
     * Returns the variable value for RefDocIdBind.
     * @return variable value for RefDocIdBind
     */
    public String getRefDocIdBind() {
        return (String) ensureVariableManager().getVariableValue("RefDocIdBind");
    }

    /**
     * Sets <code>value</code> for variable RefDocIdBind.
     * @param value value to bind as RefDocIdBind
     */
    public void setRefDocIdBind(String value) {
        ensureVariableManager().setVariableValue("RefDocIdBind", value);
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

