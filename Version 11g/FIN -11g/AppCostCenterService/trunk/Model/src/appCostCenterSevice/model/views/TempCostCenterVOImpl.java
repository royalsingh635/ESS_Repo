package appCostCenterSevice.model.views;

import appCostCenterSevice.model.service.AppCostCenterServiceAMImpl;
import appCostCenterSevice.model.views.common.TempCostCenterVO;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import oracle.jbo.JboException;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Feb 14 14:36:28 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class TempCostCenterVOImpl extends ViewObjectImpl implements TempCostCenterVO {
    /**
     * This is the default constructor (do not remove).
     */
    public TempCostCenterVOImpl() {
    }

    /**
     * Returns the variable value for CldIdBindVar.
     * @return variable value for CldIdBindVar
     */
    public String getCldIdBindVar() {
        return (String)ensureVariableManager().getVariableValue("CldIdBindVar");
    }

    /**
     * Sets <code>value</code> for variable CldIdBindVar.
     * @param value value to bind as CldIdBindVar
     */
    public void setCldIdBindVar(String value) {
        ensureVariableManager().setVariableValue("CldIdBindVar", value);
        
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
        
    }

    /**
     * Returns the variable value for HoOrgIdBindVar.
     * @return variable value for HoOrgIdBindVar
     */
    public String getHoOrgIdBindVar() {
        return (String)ensureVariableManager().getVariableValue("HoOrgIdBindVar");
        
    }

    /**
     * Sets <code>value</code> for variable HoOrgIdBindVar.
     * @param value value to bind as HoOrgIdBindVar
     */
    public void setHoOrgIdBindVar(String value) {
        ensureVariableManager().setVariableValue("HoOrgIdBindVar", value);
       
    }

    /**
     * Returns the variable value for DocIdBindVar.
     * @return variable value for DocIdBindVar
     */
    public Integer getDocIdBindVar() {
        return (Integer)ensureVariableManager().getVariableValue("DocIdBindVar");
    }

    /**
     * Sets <code>value</code> for variable DocIdBindVar.
     * @param value value to bind as DocIdBindVar
     */
    public void setDocIdBindVar(Integer value) {
        ensureVariableManager().setVariableValue("DocIdBindVar", value);
        
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
        
    }

    /**
     * Returns the variable value for TempIdBindVar.
     * @return variable value for TempIdBindVar
     */
    public String getTempIdBindVar() {
        return (String)ensureVariableManager().getVariableValue("TempIdBindVar");
    }

    /**
     * Sets <code>value</code> for variable TempIdBindVar.
     * @param value value to bind as TempIdBindVar
     */
    public void setTempIdBindVar(String value) {
        ensureVariableManager().setVariableValue("TempIdBindVar", value);
        
    }

    /**
     * Returns the variable value for TempSlNoBindVar.
     * @return variable value for TempSlNoBindVar
     */
    public Integer getTempSlNoBindVar() {
        return (Integer)ensureVariableManager().getVariableValue("TempSlNoBindVar");
    }

    /**
     * Sets <code>value</code> for variable TempSlNoBindVar.
     * @param value value to bind as TempSlNoBindVar
     */
    public void setTempSlNoBindVar(Integer value) {
        ensureVariableManager().setVariableValue("TempSlNoBindVar", value);
        
    }
    
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {

            // 1. Create a JDBC CallabledStatement
            
            st = getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            // 2. Register the first bind variable for the return value
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                // 3. Loop over values for the bind variables passed in, if any
                for (int z = 0; z < bindVars.length; z++) {
                    // 4. Set the value of user-supplied bind vars in the stmt
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            // 5. Set the value of user-supplied bind vars in the stmt
            st.executeUpdate();
            // 6. Return the value of the first bind variable
            return st.getObject(1);
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    // 7. Close the statement
                    st.close();
                } catch (SQLException e) {
                    throw new JboException(e);
                }
            }
        }
    }
    public void setBindVar(String CldIdBindVar,String OrgIdBindVar,String HoOrgIdBindVar,Integer DocIdBindVar,
                           Integer SlocIdBindVar,String TempIdBindVar,Integer TempSlNoBindVar){
       
       
        
        callStoredFunction(Types.VARCHAR, "FIN.PKG_FIN_TVOU.FN_EDIT_COST_CENTER(?,?,?,?,?,?,?)", new Object[] { SlocIdBindVar,
                                                                                                              CldIdBindVar,
                                                                                                              HoOrgIdBindVar,
                                                                                                              OrgIdBindVar,
                                                                                                              DocIdBindVar,
                                                                                                              TempIdBindVar,
                                                                                                              TempSlNoBindVar});
        
        executeQuery();
       
        this.setCldIdBindVar(CldIdBindVar);
        
        this.setOrgIdBindVar(OrgIdBindVar);
        
        this.setHoOrgIdBindVar(HoOrgIdBindVar);
        
        this.setDocIdBindVar(DocIdBindVar);
        
        this.setSlocIdBindVar(SlocIdBindVar);
        
        this.setTempIdBindVar(TempIdBindVar);
        
        this.setTempSlNoBindVar(TempSlNoBindVar);
        
        executeQuery();
    }
}
