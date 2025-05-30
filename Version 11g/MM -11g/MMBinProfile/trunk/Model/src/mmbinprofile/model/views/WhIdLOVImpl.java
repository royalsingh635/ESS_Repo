package mmbinprofile.model.views;

import mmbinprofile.model.views.common.WhIdLOV;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri May 24 11:28:46 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class WhIdLOVImpl extends ViewObjectImpl implements WhIdLOV {
    /**
     * This is the default constructor (do not remove).
     */
    public WhIdLOVImpl() {
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
    public void setBindVar(Integer Slocid,String Cldid){
        System.out.println("-----------"+Slocid+"---"+Cldid);
         this.setWhereClause("SLOC_ID="+Slocid+" and CLD_ID = '"+Cldid+"'");
         this.executeQuery();
     }

    /**
     * Returns the variable value for orgIdBind.
     * @return variable value for orgIdBind
     */
    public String getorgIdBind() {
        return (String)ensureVariableManager().getVariableValue("orgIdBind");
    }

    /**
     * Sets <code>value</code> for variable orgIdBind.
     * @param value value to bind as orgIdBind
     */
    public void setorgIdBind(String value) {
        ensureVariableManager().setVariableValue("orgIdBind", value);
    }

    /**
     * Returns the variable value for hoOrgIdbind.
     * @return variable value for hoOrgIdbind
     */
    public String gethoOrgIdbind() {
        return (String)ensureVariableManager().getVariableValue("hoOrgIdbind");
    }

    /**
     * Sets <code>value</code> for variable hoOrgIdbind.
     * @param value value to bind as hoOrgIdbind
     */
    public void sethoOrgIdbind(String value) {
        ensureVariableManager().setVariableValue("hoOrgIdbind", value);
    }
}
