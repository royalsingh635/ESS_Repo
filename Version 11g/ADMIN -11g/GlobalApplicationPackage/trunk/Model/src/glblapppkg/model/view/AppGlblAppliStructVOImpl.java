package glblapppkg.model.view;


import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Aug 07 19:32:05 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppGlblAppliStructVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public AppGlblAppliStructVOImpl() {
    }


    /**
     * Returns the variable value for bindStructParentId.
     * @return variable value for bindStructParentId
     */
    public String getbindStructParentId() {
        return (String)ensureVariableManager().getVariableValue("bindStructParentId");
    }

    /**
     * Sets <code>value</code> for variable bindStructParentId.
     * @param value value to bind as bindStructParentId
     */
    public void setbindStructParentId(String value) {
        ensureVariableManager().setVariableValue("bindStructParentId", value);
    }

    /**
     * Returns the variable value for bindAppliStructId.
     * @return variable value for bindAppliStructId
     */
    public String getbindAppliStructId() {
        return (String)ensureVariableManager().getVariableValue("bindAppliStructId");
    }

    /**
     * Sets <code>value</code> for variable bindAppliStructId.
     * @param value value to bind as bindAppliStructId
     */
    public void setbindAppliStructId(String value) {
        ensureVariableManager().setVariableValue("bindAppliStructId", value);
    }
}
