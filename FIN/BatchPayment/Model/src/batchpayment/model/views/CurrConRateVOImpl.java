package batchpayment.model.views;

import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Oct 01 16:03:55 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CurrConRateVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public CurrConRateVOImpl() {
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
     * Returns the bind variable value for BindVou.
     * @return bind variable value for BindVou
     */
    public Integer getBindVou() {
        return (Integer)getNamedWhereClauseParam("BindVou");
    }

    /**
     * Sets <code>value</code> for bind variable BindVou.
     * @param value value to bind as BindVou
     */
    public void setBindVou(Integer value) {
        setNamedWhereClauseParam("BindVou", value);
    }

    /**
     * Returns the bind variable value for BindTxn.
     * @return bind variable value for BindTxn
     */
    public Integer getBindTxn() {
        return (Integer) getNamedWhereClauseParam("BindTxn");
    }

    /**
     * Sets <code>value</code> for bind variable BindTxn.
     * @param value value to bind as BindTxn
     */
    public void setBindTxn(Integer value) {
        setNamedWhereClauseParam("BindTxn", value);
    }

    /**
     * Returns the bind variable value for BindHoOrg.
     * @return bind variable value for BindHoOrg
     */
    public String getBindHoOrg() {
        return (String)getNamedWhereClauseParam("BindHoOrg");
    }

    /**
     * Sets <code>value</code> for bind variable BindHoOrg.
     * @param value value to bind as BindHoOrg
     */
    public void setBindHoOrg(String value) {
        setNamedWhereClauseParam("BindHoOrg", value);
    }

    /**
     * Returns the bind variable value for BindOrgId.
     * @return bind variable value for BindOrgId
     */
    public String getBindOrgId() {
        return (String) getNamedWhereClauseParam("BindOrgId");
    }

    /**
     * Sets <code>value</code> for bind variable BindOrgId.
     * @param value value to bind as BindOrgId
     */
    public void setBindOrgId(String value) {
        setNamedWhereClauseParam("BindOrgId", value);
    }
}
