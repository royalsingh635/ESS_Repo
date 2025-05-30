package slssalesordapp.model.views;

import oracle.jbo.Row;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.domain.Number;

import slssalesordapp.model.views.common.ViewMmStkSummSrFreeVO;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Nov 14 16:59:52 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ViewMmStkSummSrFreeVOImpl extends ViewObjectImpl implements ViewMmStkSummSrFreeVO {
    /**
     * This is the default constructor (do not remove).
     */
    public ViewMmStkSummSrFreeVOImpl() {
    }

    /**
     * Returns the variable value for whIdBind.
     * @return variable value for whIdBind
     */
    public String getwhIdBind() {
        return (String)ensureVariableManager().getVariableValue("whIdBind");
    }

    /**
     * Sets <code>value</code> for variable whIdBind.
     * @param value value to bind as whIdBind
     */
    public void setwhIdBind(String value) {
        ensureVariableManager().setVariableValue("whIdBind", value);
    }
    
    /**
     *
     * @return
     */
    public Number getTotalIssueQtySrFree() {
        int totalCount = this.getRowCount();
        this.setRangeSize(totalCount);
        Row[] rr = this.getAllRowsInRange();
        Number totIssue = new Number(0);
        for (Row r : rr) {
            if (r.getAttribute("SelectSrNoChkBxTrans") != null) {
                if ("Y".equalsIgnoreCase(r.getAttribute("SelectSrNoChkBxTrans").toString())) {
                    totIssue = totIssue.add(new Number(1));
                }
            }
        }
        return totIssue;
    }

    /**
     * Returns the bind variable value for BindCldId.
     * @return bind variable value for BindCldId
     */
    public String getBindCldId() {
        return (String) getNamedWhereClauseParam("BindCldId");
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
        return (Integer) getNamedWhereClauseParam("BindSlocId");
    }

    /**
     * Sets <code>value</code> for bind variable BindSlocId.
     * @param value value to bind as BindSlocId
     */
    public void setBindSlocId(Integer value) {
        setNamedWhereClauseParam("BindSlocId", value);
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

    /**
     * Returns the bind variable value for BindWhId.
     * @return bind variable value for BindWhId
     */
    public String getBindWhId() {
        return (String) getNamedWhereClauseParam("BindWhId");
    }

    /**
     * Sets <code>value</code> for bind variable BindWhId.
     * @param value value to bind as BindWhId
     */
    public void setBindWhId(String value) {
        setNamedWhereClauseParam("BindWhId", value);
    }

    /**
     * Returns the bind variable value for BindItmId.
     * @return bind variable value for BindItmId
     */
    public String getBindItmId() {
        return (String) getNamedWhereClauseParam("BindItmId");
    }

    /**
     * Sets <code>value</code> for bind variable BindItmId.
     * @param value value to bind as BindItmId
     */
    public void setBindItmId(String value) {
        setNamedWhereClauseParam("BindItmId", value);
    }

    /**
     * Returns the bind variable value for BindDocId.
     * @return bind variable value for BindDocId
     */
    public String getBindDocId() {
        return (String) getNamedWhereClauseParam("BindDocId");
    }

    /**
     * Sets <code>value</code> for bind variable BindDocId.
     * @param value value to bind as BindDocId
     */
    public void setBindDocId(String value) {
        setNamedWhereClauseParam("BindDocId", value);
    }
}

