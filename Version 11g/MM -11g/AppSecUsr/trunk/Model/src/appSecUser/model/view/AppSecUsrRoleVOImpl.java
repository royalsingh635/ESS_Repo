package appSecUser.model.view;

import java.sql.ResultSet;

import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Jun 16 15:52:43 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppSecUsrRoleVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public AppSecUsrRoleVOImpl() {
    }
    
    int counter=0;
       protected ViewRowImpl createRowFromResultSet(Object object, ResultSet resultSet) {
           counter++;
       ViewRowImpl row = super.createRowFromResultSet(object, resultSet);
      System.out.println("Row fetched with key: " + row.getKey() + ", for VO: " + this.getName() + " row count: " + counter);

       return row;
       }

    /**
     * Returns the bind variable value for BindRoleId.
     * @return bind variable value for BindRoleId
     */
    public Integer getBindRoleId() {
        return (Integer)getNamedWhereClauseParam("BindRoleId");
    }

    /**
     * Sets <code>value</code> for bind variable BindRoleId.
     * @param value value to bind as BindRoleId
     */
    public void setBindRoleId(Integer value) {
        setNamedWhereClauseParam("BindRoleId", value);
    }
}
