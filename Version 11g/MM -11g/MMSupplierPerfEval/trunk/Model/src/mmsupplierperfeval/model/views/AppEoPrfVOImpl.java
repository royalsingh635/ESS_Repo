package mmsupplierperfeval.model.views;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import mmsupplierperfeval.model.service.MmSupplierPerfEvalAMImpl;

import mmsupplierperfeval.model.views.common.AppEoPrfVO;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Apr 20 15:02:02 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppEoPrfVOImpl extends ViewObjectImpl implements AppEoPrfVO {
    /**
     * This is the default constructor (do not remove).
     */
    public AppEoPrfVOImpl() {
    }

    /**
     * Returns the variable value for SlocIdVar.
     * @return variable value for SlocIdVar
     */
    public Integer getSlocIdVar() {
        return (Integer)ensureVariableManager().getVariableValue("SlocIdVar");
    }

    /**
     * Sets <code>value</code> for variable SlocIdVar.
     * @param value value to bind as SlocIdVar
     */
    public void setSlocIdVar(Integer value) {
        ensureVariableManager().setVariableValue("SlocIdVar", value);
        this.executeQuery();
    }

    /**
     * Returns the variable value for OrgIdVar.
     * @return variable value for OrgIdVar
     */
    public String getOrgIdVar() {
        return (String)ensureVariableManager().getVariableValue("OrgIdVar");
    }

    /**
     * Sets <code>value</code> for variable OrgIdVar.
     * @param value value to bind as OrgIdVar
     */
    public void setOrgIdVar(String value) {
        ensureVariableManager().setVariableValue("OrgIdVar", value);
        this.executeQuery();
    }

    /**
     * Returns the variable value for SupNameVar.
     * @return variable value for SupNameVar
     */
    public String getSupNameVar() {
        return (String)ensureVariableManager().getVariableValue("SupNameVar");
    }

    /**
     * Sets <code>value</code> for variable SupNameVar.
     * @param value value to bind as SupNameVar
     */
    public void setSupNameVar(String value) {
        ensureVariableManager().setVariableValue("SupNameVar", value);
    }
    
    public void setBindVarValue(Integer SlocIdVar,String CldIdVar, String HoOrgIdVar,String OrgId){
        
        System.out.println("Sloc--"+SlocIdVar+"Cld--"+CldIdVar+"Org--"+HoOrgIdVar+" org id is"+OrgId);
    String HoOrgId=HoOrgIdVar;//now this value contain Ho Org Id passed from Task Flow
        /* this.setSlocIdVar(SlocIdVar);
        this.setOrgIdVar(OrgIdVar); */
        this.setWhereClause("EO_SLOC_ID ="+SlocIdVar+" and EO_CLD_ID='" +CldIdVar+"' and EO_HO_ORG_ID='"+HoOrgId+"' and ORG_ID= '"+ OrgId +"'");
        this.executeQuery();
        System.out.println("           ............"+this.getQuery());
        MmSupplierPerfEvalAMImpl am = (MmSupplierPerfEvalAMImpl)resolvElDC("MmSupplierPerfEvalAMDataControl");
        am.getParamSetLOV1().executeQuery();
        am.getDual().executeQuery();
    }
    

    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    /**
     * Returns the variable value for CldIdVar.
     * @return variable value for CldIdVar
     */
    public String getCldIdVar() {
        return (String)ensureVariableManager().getVariableValue("CldIdVar");
    }

    /**
     * Sets <code>value</code> for variable CldIdVar.
     * @param value value to bind as CldIdVar
     */
    public void setCldIdVar(String value) {
        ensureVariableManager().setVariableValue("CldIdVar", value);
    }

    /**
     * Returns the variable value for HoOrgId.
     * @return variable value for HoOrgId
     */
    public String getHoOrgId() {
        return (String)ensureVariableManager().getVariableValue("HoOrgId");
    }

    /**
     * Sets <code>value</code> for variable HoOrgId.
     * @param value value to bind as HoOrgId
     */
    public void setHoOrgId(String value) {
        ensureVariableManager().setVariableValue("HoOrgId", value);
    }
}
