package tkrAccessToUsers.model.View;

import java.sql.ResultSet;

import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Sep 22 13:14:47 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LovUserVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public LovUserVOImpl() {
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

    /**
     * Returns the bind variable value for BindTickerId.
     * @return bind variable value for BindTickerId
     */
    public Integer getBindTickerId() {
        return (Integer)getNamedWhereClauseParam("BindTickerId");
    }

    /**
     * Sets <code>value</code> for bind variable BindTickerId.
     * @param value value to bind as BindTickerId
     */
    public void setBindTickerId(Integer value) {
        setNamedWhereClauseParam("BindTickerId", value);
    }
    
    
   int counter =0;
    
    
}
