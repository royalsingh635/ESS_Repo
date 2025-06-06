package slssalesordapp.model.views;

import java.sql.ResultSet;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.ViewRowSetImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Mar 07 18:17:16 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class orderSearchVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public orderSearchVOImpl() {
    }


    /**
     * executeQueryForCollection - overridden for custom java data source support.
     */
    protected void executeQueryForCollection(Object qc, Object[] params, int noUserParams) {
        //System.out.println("This query :"+this.getQuery());
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
     * Returns the variable value for FromDtBind.
     * @return variable value for FromDtBind
     */
    public Timestamp getFromDtBind() {
        return (Timestamp)ensureVariableManager().getVariableValue("FromDtBind");
    }

    /**
     * Sets <code>value</code> for variable FromDtBind.
     * @param value value to bind as FromDtBind
     */
    public void setFromDtBind(Timestamp value) {
        ensureVariableManager().setVariableValue("FromDtBind", value);
    }

    /**
     * Returns the variable value for ToDtBind.
     * @return variable value for ToDtBind
     */
    public Timestamp getToDtBind() {
        return (Timestamp)ensureVariableManager().getVariableValue("ToDtBind");
    }

    /**
     * Sets <code>value</code> for variable ToDtBind.
     * @param value value to bind as ToDtBind
     */
    public void setToDtBind(Timestamp value) {
        ensureVariableManager().setVariableValue("ToDtBind", value);
    }

    /**
     * Returns the variable value for EoIdBind.
     * @return variable value for EoIdBind
     */
    public Integer getEoIdBind() {
        return (Integer)ensureVariableManager().getVariableValue("EoIdBind");
    }

    /**
     * Sets <code>value</code> for variable EoIdBind.
     * @param value value to bind as EoIdBind
     */
    public void setEoIdBind(Integer value) {
        ensureVariableManager().setVariableValue("EoIdBind", value);
    }

    /**
     * Returns the variable value for slocIdBind.
     * @return variable value for slocIdBind
     */
    public Integer getslocIdBind() {
        return (Integer)ensureVariableManager().getVariableValue("slocIdBind");
    }

    /**
     * Sets <code>value</code> for variable slocIdBind.
     * @param value value to bind as slocIdBind
     */
    public void setslocIdBind(Integer value) {
        ensureVariableManager().setVariableValue("slocIdBind", value);
    }

    /**
     * Returns the variable value for orgIdBind.
     * @return variable value for orgIdBind
     */
    public String getorgIdBind() {
        return (String)ensureVariableManager().getVariableValue("orgIdBind");
    }

    /**
     * Sets <code>value</code> for variable orgIdBind.
     * @param value value to bind as orgIdBind
     */
    public void setorgIdBind(String value) {
        ensureVariableManager().setVariableValue("orgIdBind", value);
    }

    /**
     * Returns the variable value for soTypeBind.
     * @return variable value for soTypeBind
     */
    public Integer getsoTypeBind() {
        return (Integer)ensureVariableManager().getVariableValue("soTypeBind");
    }

    /**
     * Sets <code>value</code> for variable soTypeBind.
     * @param value value to bind as soTypeBind
     */
    public void setsoTypeBind(Integer value) {
        ensureVariableManager().setVariableValue("soTypeBind", value);
    }

    /**
     * Returns the variable value for ordStatusBind.
     * @return variable value for ordStatusBind
     */
    public Integer getordStatusBind() {
        return (Integer)ensureVariableManager().getVariableValue("ordStatusBind");
    }

    /**
     * Sets <code>value</code> for variable ordStatusBind.
     * @param value value to bind as ordStatusBind
     */
    public void setordStatusBind(Integer value) {
        ensureVariableManager().setVariableValue("ordStatusBind", value);
    }

    /**
     * Returns the variable value for cldIdBind.
     * @return variable value for cldIdBind
     */
    public String getcldIdBind() {
        return (String)ensureVariableManager().getVariableValue("cldIdBind");
    }

    /**
     * Sets <code>value</code> for variable cldIdBind.
     * @param value value to bind as cldIdBind
     */
    public void setcldIdBind(String value) {
        ensureVariableManager().setVariableValue("cldIdBind", value);
    }

    /**
     * Returns the variable value for usrIdBind.
     * @return variable value for usrIdBind
     */
    public Integer getusrIdBind() {
        return (Integer)ensureVariableManager().getVariableValue("usrIdBind");
    }

    /**
     * Sets <code>value</code> for variable usrIdBind.
     * @param value value to bind as usrIdBind
     */
    public void setusrIdBind(Integer value) {
        ensureVariableManager().setVariableValue("usrIdBind", value);
    }

    /**
     * Returns the variable value for amtFromBind.
     * @return variable value for amtFromBind
     */
    public Number getamtFromBind() {
        return (Number)ensureVariableManager().getVariableValue("amtFromBind");
    }

    /**
     * Sets <code>value</code> for variable amtFromBind.
     * @param value value to bind as amtFromBind
     */
    public void setamtFromBind(Number value) {
        ensureVariableManager().setVariableValue("amtFromBind", value);
    }

    /**
     * Returns the variable value for amtToBind.
     * @return variable value for amtToBind
     */
    public Number getamtToBind() {
        return (Number)ensureVariableManager().getVariableValue("amtToBind");
    }

    /**
     * Sets <code>value</code> for variable amtToBind.
     * @param value value to bind as amtToBind
     */
    public void setamtToBind(Number value) {
        ensureVariableManager().setVariableValue("amtToBind", value);
    }

    /**
     * Returns the variable value for minItemBind.
     * @return variable value for minItemBind
     */
    public Integer getminItemBind() {
        return (Integer)ensureVariableManager().getVariableValue("minItemBind");
    }

    /**
     * Sets <code>value</code> for variable minItemBind.
     * @param value value to bind as minItemBind
     */
    public void setminItemBind(Integer value) {
        ensureVariableManager().setVariableValue("minItemBind", value);
    }

    /**
     * Returns the variable value for maxItemBind.
     * @return variable value for maxItemBind
     */
    public Integer getmaxItemBind() {
        return (Integer)ensureVariableManager().getVariableValue("maxItemBind");
    }

    /**
     * Sets <code>value</code> for variable maxItemBind.
     * @param value value to bind as maxItemBind
     */
    public void setmaxItemBind(Integer value) {
        ensureVariableManager().setVariableValue("maxItemBind", value);
    }

    /**
     * Returns the variable value for dispDocIdBind.
     * @return variable value for dispDocIdBind
     */
    public String getdispDocIdBind() {
        return (String)ensureVariableManager().getVariableValue("dispDocIdBind");
    }

    /**
     * Sets <code>value</code> for variable dispDocIdBind.
     * @param value value to bind as dispDocIdBind
     */
    public void setdispDocIdBind(String value) {
        ensureVariableManager().setVariableValue("dispDocIdBind", value);
    }

    /**
     * Returns the variable value for hoOrgIdBind.
     * @return variable value for hoOrgIdBind
     */
    public String gethoOrgIdBind() {
        return (String)ensureVariableManager().getVariableValue("hoOrgIdBind");
    }

    /**
     * Sets <code>value</code> for variable hoOrgIdBind.
     * @param value value to bind as hoOrgIdBind
     */
    public void sethoOrgIdBind(String value) {
        ensureVariableManager().setVariableValue("hoOrgIdBind", value);
    }

    /**
     * Returns the variable value for SoModeBind.
     * @return variable value for SoModeBind
     */
    public Integer getSoModeBind() {
        return (Integer)ensureVariableManager().getVariableValue("SoModeBind");
    }

    /**
     * Sets <code>value</code> for variable SoModeBind.
     * @param value value to bind as SoModeBind
     */
    public void setSoModeBind(Integer value) {
        ensureVariableManager().setVariableValue("SoModeBind", value);
    }
}
