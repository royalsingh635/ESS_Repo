package graphTickerApp.model.views;

import java.sql.ResultSet;

import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Apr 23 15:30:20 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class TopSupplierOsVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public TopSupplierOsVOImpl() {
    }
    int counter=0;
       protected ViewRowImpl createRowFromResultSet(Object object, ResultSet resultSet) {
           counter++;
       ViewRowImpl row = super.createRowFromResultSet(object, resultSet);
      System.out.println("Row fetched with key: " + row.getKey() + ", for VO: " + this.getName() + " row count: " + counter);

       return row;
       }
}
