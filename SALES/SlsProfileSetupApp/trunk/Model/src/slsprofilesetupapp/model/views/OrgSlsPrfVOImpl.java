package slsprofilesetupapp.model.views;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Nov 05 14:23:30 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class OrgSlsPrfVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public OrgSlsPrfVOImpl() {
    }

    /**
     * Returns the bind variable value for orgIdBindVar.
     * @return bind variable value for orgIdBindVar
     */
    public String getorgIdBindVar() {
        return (String)getNamedWhereClauseParam("orgIdBindVar");
    }

    /**
     * Sets <code>value</code> for bind variable orgIdBindVar.
     * @param value value to bind as orgIdBindVar
     */
    public void setorgIdBindVar(String value) {
        setNamedWhereClauseParam("orgIdBindVar", value);
    }


}
