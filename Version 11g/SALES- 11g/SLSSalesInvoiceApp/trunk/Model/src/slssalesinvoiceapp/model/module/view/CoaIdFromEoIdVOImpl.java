package slssalesinvoiceapp.model.module.view;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Oct 31 10:22:43 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CoaIdFromEoIdVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public CoaIdFromEoIdVOImpl() {
    }

    /**
     * Returns the bind variable value for SlocIdBind.
     * @return bind variable value for SlocIdBind
     */
    public Integer getSlocIdBind() {
        return (Integer)getNamedWhereClauseParam("SlocIdBind");
    }

    /**
     * Sets <code>value</code> for bind variable SlocIdBind.
     * @param value value to bind as SlocIdBind
     */
    public void setSlocIdBind(Integer value) {
        setNamedWhereClauseParam("SlocIdBind", value);
    }

    /**
     * Returns the bind variable value for EoIdBind.
     * @return bind variable value for EoIdBind
     */
    public Integer getEoIdBind() {
        return (Integer)getNamedWhereClauseParam("EoIdBind");
    }

    /**
     * Sets <code>value</code> for bind variable EoIdBind.
     * @param value value to bind as EoIdBind
     */
    public void setEoIdBind(Integer value) {
        setNamedWhereClauseParam("EoIdBind", value);
    }

    /**
     * Returns the bind variable value for OrgIdBind.
     * @return bind variable value for OrgIdBind
     */
    public String getOrgIdBind() {
        return (String)getNamedWhereClauseParam("OrgIdBind");
    }

    /**
     * Sets <code>value</code> for bind variable OrgIdBind.
     * @param value value to bind as OrgIdBind
     */
    public void setOrgIdBind(String value) {
        setNamedWhereClauseParam("OrgIdBind", value);
    }

    /**
     * Returns the bind variable value for HoOrgIdBind.
     * @return bind variable value for HoOrgIdBind
     */
    public String getHoOrgIdBind() {
        return (String)getNamedWhereClauseParam("HoOrgIdBind");
    }

    /**
     * Sets <code>value</code> for bind variable HoOrgIdBind.
     * @param value value to bind as HoOrgIdBind
     */
    public void setHoOrgIdBind(String value) {
        setNamedWhereClauseParam("HoOrgIdBind", value);
    }

    /**
     * Returns the bind variable value for CldIdBind.
     * @return bind variable value for CldIdBind
     */
    public String getCldIdBind() {
        return (String)getNamedWhereClauseParam("CldIdBind");
    }

    /**
     * Sets <code>value</code> for bind variable CldIdBind.
     * @param value value to bind as CldIdBind
     */
    public void setCldIdBind(String value) {
        setNamedWhereClauseParam("CldIdBind", value);
    }
}
