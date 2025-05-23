package appgraphsetup.model.view;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Sep 29 16:44:50 IST 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppGrphTrndTmpVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public AppGrphTrndTmpVOImpl() {
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
     * Returns the variable value for graphIdBind.
     * @return variable value for graphIdBind
     */
    public Integer getgraphIdBind() {
        return (Integer)ensureVariableManager().getVariableValue("graphIdBind");
    }

    /**
     * Sets <code>value</code> for variable graphIdBind.
     * @param value value to bind as graphIdBind
     */
    public void setgraphIdBind(Integer value) {
        ensureVariableManager().setVariableValue("graphIdBind", value);
    }

    /**
     * Returns the variable value for trendIdBind.
     * @return variable value for trendIdBind
     */
    public Integer gettrendIdBind() {
        return (Integer)ensureVariableManager().getVariableValue("trendIdBind");
    }

    /**
     * Sets <code>value</code> for variable trendIdBind.
     * @param value value to bind as trendIdBind
     */
    public void settrendIdBind(Integer value) {
        ensureVariableManager().setVariableValue("trendIdBind", value);
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
}
