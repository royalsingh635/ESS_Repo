package misARAPAnalysis.model.view;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Aug 08 12:38:04 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class OrgUsrAgeingBktVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public OrgUsrAgeingBktVOImpl() {
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
     * Returns the bind variable value for BindUsrId.
     * @return bind variable value for BindUsrId
     */
    public Integer getBindUsrId() {
        return (Integer)getNamedWhereClauseParam("BindUsrId");
    }

    /**
     * Sets <code>value</code> for bind variable BindUsrId.
     * @param value value to bind as BindUsrId
     */
    public void setBindUsrId(Integer value) {
        setNamedWhereClauseParam("BindUsrId", value);
    }

    /**
     * Returns the bind variable value for BindBktTyp.
     * @return bind variable value for BindBktTyp
     */
    public String getBindBktTyp() {
        return (String)getNamedWhereClauseParam("BindBktTyp");
    }

    /**
     * Sets <code>value</code> for bind variable BindBktTyp.
     * @param value value to bind as BindBktTyp
     */
    public void setBindBktTyp(String value) {
        setNamedWhereClauseParam("BindBktTyp", value);
    }
}
