package slssalesinvoiceapp.model.module.view;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Oct 28 18:06:31 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LOVCustCoaVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public LOVCustCoaVOImpl() {
    }

    /**
     * Returns the variable value for SlocIdBind.
     * @return variable value for SlocIdBind
     */
    public Integer getSlocIdBind() {
        return (Integer)ensureVariableManager().getVariableValue("SlocIdBind");
    }

    /**
     * Sets <code>value</code> for variable SlocIdBind.
     * @param value value to bind as SlocIdBind
     */
    public void setSlocIdBind(Integer value) {
        ensureVariableManager().setVariableValue("SlocIdBind", value);
    }

    /**
     * Returns the variable value for OrgIdBind.
     * @return variable value for OrgIdBind
     */
    public String getOrgIdBind() {
        return (String)ensureVariableManager().getVariableValue("OrgIdBind");
    }

    /**
     * Sets <code>value</code> for variable OrgIdBind.
     * @param value value to bind as OrgIdBind
     */
    public void setOrgIdBind(String value) {
        ensureVariableManager().setVariableValue("OrgIdBind", value);
    }

    /**
     * Returns the variable value for HoOrgIdBind.
     * @return variable value for HoOrgIdBind
     */
    public String getHoOrgIdBind() {
        return (String)ensureVariableManager().getVariableValue("HoOrgIdBind");
    }

    /**
     * Sets <code>value</code> for variable HoOrgIdBind.
     * @param value value to bind as HoOrgIdBind
     */
    public void setHoOrgIdBind(String value) {
        ensureVariableManager().setVariableValue("HoOrgIdBind", value);
    }

    /**
     * Returns the variable value for CldIdBind.
     * @return variable value for CldIdBind
     */
    public String getCldIdBind() {
        return (String)ensureVariableManager().getVariableValue("CldIdBind");
    }

    /**
     * Sets <code>value</code> for variable CldIdBind.
     * @param value value to bind as CldIdBind
     */
    public void setCldIdBind(String value) {
        ensureVariableManager().setVariableValue("CldIdBind", value);
    }
}
