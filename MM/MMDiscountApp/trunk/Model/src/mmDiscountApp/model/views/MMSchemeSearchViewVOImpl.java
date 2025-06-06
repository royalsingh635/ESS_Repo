package mmDiscountApp.model.views;

import java.sql.ResultSet;

import mmDiscountApp.model.views.common.MMSchemeSearchViewVO;

import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Dec 15 14:12:51 IST 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MMSchemeSearchViewVOImpl extends ViewObjectImpl implements MMSchemeSearchViewVO {
    /**
     * This is the default constructor (do not remove).
     */
    public MMSchemeSearchViewVOImpl() {
    }

    /**
     * Returns the variable value for SlocIdBindVar.
     * @return variable value for SlocIdBindVar
     */
    public Integer getSlocIdBindVar() {
        return (Integer)ensureVariableManager().getVariableValue("SlocIdBindVar");
    }

    /**
     * Sets <code>value</code> for variable SlocIdBindVar.
     * @param value value to bind as SlocIdBindVar
     */
    public void setSlocIdBindVar(Integer value) {
        ensureVariableManager().setVariableValue("SlocIdBindVar", value);
        this.executeQuery();
    }

    /**
     * Returns the variable value for OrgIdBindVar.
     * @return variable value for OrgIdBindVar
     */
    public String getOrgIdBindVar() {
        return (String)ensureVariableManager().getVariableValue("OrgIdBindVar");
    }

    /**
     * Sets <code>value</code> for variable OrgIdBindVar.
     * @param value value to bind as OrgIdBindVar
     */
    public void setOrgIdBindVar(String value) {
        ensureVariableManager().setVariableValue("OrgIdBindVar", value);
        this.executeQuery();
    }
    
    public void setBindVarVal(Integer SlocId,String OrgId,String CldId){
         this.setSlocIdBindVar(SlocId);
        this.setOrgIdBindVar(OrgId);
        this.setCldIdBindNewVar(CldId); 
       this.executeQuery();
    }

    /**
     * Returns the bind variable value for FromDateBindVar.
     * @return bind variable value for FromDateBindVar
     */
    public Date getFromDateBindVar() {
        return (Date)getNamedWhereClauseParam("FromDateBindVar");
    }

    /**
     * Sets <code>value</code> for bind variable FromDateBindVar.
     * @param value value to bind as FromDateBindVar
     */
    public void setFromDateBindVar(Date value) {
        setNamedWhereClauseParam("FromDateBindVar", value);
    }

    /**
     * Returns the bind variable value for UptoDateBindVar.
     * @return bind variable value for UptoDateBindVar
     */
    public Date getUptoDateBindVar() {
        return (Date)getNamedWhereClauseParam("UptoDateBindVar");
    }

    /**
     * Sets <code>value</code> for bind variable UptoDateBindVar.
     * @param value value to bind as UptoDateBindVar
     */
    public void setUptoDateBindVar(Date value) {
        setNamedWhereClauseParam("UptoDateBindVar", value);
    }

    /**
     * Returns the bind variable value for SchmTypeBindVar.
     * @return bind variable value for SchmTypeBindVar
     */
    public Integer getSchmTypeBindVar() {
        return (Integer)getNamedWhereClauseParam("SchmTypeBindVar");
    }

    /**
     * Sets <code>value</code> for bind variable SchmTypeBindVar.
     * @param value value to bind as SchmTypeBindVar
     */
    public void setSchmTypeBindVar(Integer value) {
        setNamedWhereClauseParam("SchmTypeBindVar", value);
    }

    /**
     * Returns the bind variable value for SchemeNameBindVar.
     * @return bind variable value for SchemeNameBindVar
     */
    public String getSchemeNameBindVar() {
        return (String)getNamedWhereClauseParam("SchemeNameBindVar");
    }

    /**
     * Sets <code>value</code> for bind variable SchemeNameBindVar.
     * @param value value to bind as SchemeNameBindVar
     */
    public void setSchemeNameBindVar(String value) {
        setNamedWhereClauseParam("SchemeNameBindVar", value);
    }

    /**
     * Returns the variable value for CldIdBindNewVar.
     * @return variable value for CldIdBindNewVar
     */
    public String getCldIdBindNewVar() {
        return (String)ensureVariableManager().getVariableValue("CldIdBindNewVar");
    }

    /**
     * Sets <code>value</code> for variable CldIdBindNewVar.
     * @param value value to bind as CldIdBindNewVar
     */
    public void setCldIdBindNewVar(String value) {
        ensureVariableManager().setVariableValue("CldIdBindNewVar", value);
    }
   
}
