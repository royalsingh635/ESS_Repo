package tempVoucherList.model.views;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Sep 23 19:01:46 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class InstVouTypVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public InstVouTypVOImpl() {
    }

    /**
     * Returns the bind variable value for BindVouType.
     * @return bind variable value for BindVouType
     */
    public String getBindVouType() {
        return (String)getNamedWhereClauseParam("BindVouType");
    }

    /**
     * Sets <code>value</code> for bind variable BindVouType.
     * @param value value to bind as BindVouType
     */
    public void setBindVouType(String value) {
        setNamedWhereClauseParam("BindVouType", value);
    }
}
