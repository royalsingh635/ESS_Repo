package mmgatepass.model.views;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Dec 18 17:00:14 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MmGpVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public MmGpVOImpl() {
    }

    /**
     * Returns the variable value for cld.
     * @return variable value for cld
     */
    public String getcld() {
        return (String)ensureVariableManager().getVariableValue("cld");
    }

    /**
     * Sets <code>value</code> for variable cld.
     * @param value value to bind as cld
     */
    public void setcld(String value) {
        ensureVariableManager().setVariableValue("cld", value);
    }

    /**
     * Returns the variable value for sloc.
     * @return variable value for sloc
     */
    public Integer getsloc() {
        return (Integer)ensureVariableManager().getVariableValue("sloc");
    }

    /**
     * Sets <code>value</code> for variable sloc.
     * @param value value to bind as sloc
     */
    public void setsloc(Integer value) {
        ensureVariableManager().setVariableValue("sloc", value);
    }

    /**
     * Returns the variable value for org.
     * @return variable value for org
     */
    public String getorg() {
        return (String)ensureVariableManager().getVariableValue("org");
    }

    /**
     * Sets <code>value</code> for variable org.
     * @param value value to bind as org
     */
    public void setorg(String value) {
        ensureVariableManager().setVariableValue("org", value);
    }

    /**
     * Returns the variable value for doc.
     * @return variable value for doc
     */
    public String getdoc() {
        return (String)ensureVariableManager().getVariableValue("doc");
    }

    /**
     * Sets <code>value</code> for variable doc.
     * @param value value to bind as doc
     */
    public void setdoc(String value) {
        ensureVariableManager().setVariableValue("doc", value);
    }

    /**
     * Returns the variable value for wh.
     * @return variable value for wh
     */
    public String getwh() {
        return (String)ensureVariableManager().getVariableValue("wh");
    }

    /**
     * Sets <code>value</code> for variable wh.
     * @param value value to bind as wh
     */
    public void setwh(String value) {
        ensureVariableManager().setVariableValue("wh", value);
    }
}
