package slssalesinvoiceapp.model.module.view;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Oct 31 10:12:03 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class EoNmEoIdVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public EoNmEoIdVOImpl() {
    }

    /**
     * Returns the bind variable value for EoNmBind.
     * @return bind variable value for EoNmBind
     */
    public String getEoNmBind() {
        return (String)getNamedWhereClauseParam("EoNmBind");
    }

    /**
     * Sets <code>value</code> for bind variable EoNmBind.
     * @param value value to bind as EoNmBind
     */
    public void setEoNmBind(String value) {
        setNamedWhereClauseParam("EoNmBind", value);
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
     * Returns the bind variable value for CldIdBInd.
     * @return bind variable value for CldIdBInd
     */
    public String getCldIdBInd() {
        return (String)getNamedWhereClauseParam("CldIdBInd");
    }

    /**
     * Sets <code>value</code> for bind variable CldIdBInd.
     * @param value value to bind as CldIdBInd
     */
    public void setCldIdBInd(String value) {
        setNamedWhereClauseParam("CldIdBInd", value);
    }

    /**
     * Returns the bind variable value for HoOrgBind.
     * @return bind variable value for HoOrgBind
     */
    public String getHoOrgBind() {
        return (String)getNamedWhereClauseParam("HoOrgBind");
    }

    /**
     * Sets <code>value</code> for bind variable HoOrgBind.
     * @param value value to bind as HoOrgBind
     */
    public void setHoOrgBind(String value) {
        setNamedWhereClauseParam("HoOrgBind", value);
    }
}
