package glApp.model.view;

//import glApp.model.view.common.GlVO;

import java.sql.ResultSet;

import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Mar 15 11:59:46 IST 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class GlVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public GlVOImpl() {
    }

    /**
     * Returns the variable value for BindVouId.
     * @return variable value for BindVouId
     */
    public String getBindVouId() {
        return (String)ensureVariableManager().getVariableValue("BindVouId");
    }

    /**
     * Sets <code>value</code> for variable BindVouId.
     * @param value value to bind as BindVouId
     */
    public void setBindVouId(String value) {
        ensureVariableManager().setVariableValue("BindVouId", value);
    }


    /**
     * Returns the variable value for BindHoOrgId.
     * @return variable value for BindHoOrgId
     */
    public String getBindHoOrgId() {
        return (String) ensureVariableManager().getVariableValue("BindHoOrgId");
    }

    /**
     * Sets <code>value</code> for variable BindHoOrgId.
     * @param value value to bind as BindHoOrgId
     */
    public void setBindHoOrgId(String value) {
        ensureVariableManager().setVariableValue("BindHoOrgId", value);
    }

    /**
     * Returns the variable value for BindCldId.
     * @return variable value for BindCldId
     */
    public String getBindCldId() {
        return (String) ensureVariableManager().getVariableValue("BindCldId");
    }

    /**
     * Sets <code>value</code> for variable BindCldId.
     * @param value value to bind as BindCldId
     */
    public void setBindCldId(String value) {
        ensureVariableManager().setVariableValue("BindCldId", value);
    }

    /**
     * Returns the variable value for BindOrgId.
     * @return variable value for BindOrgId
     */
    public String getBindOrgId() {
        return (String) ensureVariableManager().getVariableValue("BindOrgId");
    }

    /**
     * Sets <code>value</code> for variable BindOrgId.
     * @param value value to bind as BindOrgId
     */
    public void setBindOrgId(String value) {
        ensureVariableManager().setVariableValue("BindOrgId", value);
    }

    /**
     * Returns the variable value for BindSlocId.
     * @return variable value for BindSlocId
     */
    public Integer getBindSlocId() {
        return (Integer) ensureVariableManager().getVariableValue("BindSlocId");
    }

    /**
     * Sets <code>value</code> for variable BindSlocId.
     * @param value value to bind as BindSlocId
     */
    public void setBindSlocId(Integer value) {
        ensureVariableManager().setVariableValue("BindSlocId", value);
    }
}
