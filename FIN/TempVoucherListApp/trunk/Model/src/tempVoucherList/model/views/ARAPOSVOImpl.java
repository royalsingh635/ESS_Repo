package tempVoucherList.model.views;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jbo.ViewCriteria;
import oracle.jbo.server.QueryCollection;
import oracle.jbo.server.SQLBuilder;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.ViewRowSetImpl;


// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Dec 12 15:50:28 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ARAPOSVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public ARAPOSVOImpl() {
    }

    /**
     * Returns the bind variable value for BindCldId.
     * @return bind variable value for BindCldId
     */
    public String getBindCldId() {
        return (String)getNamedWhereClauseParam("BindCldId");
    }

    /**
     * Sets <code>value</code> for bind variable BindCldId.
     * @param value value to bind as BindCldId
     */
    public void setBindCldId(String value) {
        setNamedWhereClauseParam("BindCldId", value);
    }

    /**
     * Returns the bind variable value for BindSlcId.
     * @return bind variable value for BindSlcId
     */
    public Integer getBindSlcId() {
        return (Integer)getNamedWhereClauseParam("BindSlcId");
    }

    /**
     * Sets <code>value</code> for bind variable BindSlcId.
     * @param value value to bind as BindSlcId
     */
    public void setBindSlcId(Integer value) {
        setNamedWhereClauseParam("BindSlcId", value);
    }

    /**
     * Returns the bind variable value for BindHoOrgId.
     * @return bind variable value for BindHoOrgId
     */
    public String getBindHoOrgId() {
        return (String)getNamedWhereClauseParam("BindHoOrgId");
    }

    /**
     * Sets <code>value</code> for bind variable BindHoOrgId.
     * @param value value to bind as BindHoOrgId
     */
    public void setBindHoOrgId(String value) {
        setNamedWhereClauseParam("BindHoOrgId", value);
    }

    /**
     * Returns the bind variable value for BindOrgId.
     * @return bind variable value for BindOrgId
     */
    public String getBindOrgId() {
        return (String)getNamedWhereClauseParam("BindOrgId");
    }

    /**
     * Sets <code>value</code> for bind variable BindOrgId.
     * @param value value to bind as BindOrgId
     */
    public void setBindOrgId(String value) {
        setNamedWhereClauseParam("BindOrgId", value);
    }
    protected boolean showSql = false;
    
    @Override
        public long getEstimatedRowCount() {
            long init = System.currentTimeMillis();
            long counter = super.getEstimatedRowCount();
            long end = System.currentTimeMillis() - init;
            
            if (showSql)
                System.out.println("VO: " + this.getName() + ", from AM: " + this.getApplicationModule().getName() + ", executes SQL Count in: " + end + " (miliseconds)");
            
            return counter;
        }
    @Override
    protected void executeQueryForCollection(Object qc, Object[] params, int noUserParams) {
         short fetchSize = 10;
         if (this.getRangeSize() > 0) {
            fetchSize = ((short)(this.getRangeSize() + 3));
        }
        if (this.getRangeSize() < 10) {
            
            fetchSize = 10;
        }

       
        if (fetchSize > this.getFetchSize()) {
            
            this.setFetchSize(fetchSize);
        } else {
           
        }  

        long init = System.currentTimeMillis();
        super.executeQueryForCollection(qc, params, noUserParams);
        long end = System.currentTimeMillis() - init;

         if (showSql)
            System.out.println("VO: " + this.getName() + ", from AM: " + this.getApplicationModule().getName() +
                          ", executes SQL in: " + end + " (miliseconds)");
   
        }
    @Override
       protected void bindParametersForCollection(QueryCollection queryCollection, Object[] object, PreparedStatement preparedStatement) throws SQLException, SQLException {
           // TODO Implement this method
           if (showSql)
               logQueryStatementAndBindParameters(queryCollection, object);
           
           super.bindParametersForCollection(queryCollection, object, preparedStatement);
       }
       
       /**
        * method used to introspect the query produced at runtime by the vo.
        * @param qc
        * @param params
        */
       private void logQueryStatementAndBindParameters(QueryCollection qc, Object[] params) {
           String vrsiName = null;
           if (qc != null) {
               ViewRowSetImpl vrsi = qc.getRowSetImpl();
               vrsiName = vrsi.isDefaultRS() ? "<Default>" : vrsi.getName();
           }
           String voName = getName();
           String voDefName = getDefFullName();
           if (qc != null) {
               System.out.println("----[Exec query for VO=" + voName + ", RS=" + vrsiName + "]----");
           } else {
               System.out.println("----[Exec COUNT query for VO=" + voName + "]----");
           }
           System.out.println("VO Definition Name = {0}"+ voDefName);

           String dbVCs = appliedCriteriaString(ViewCriteria.CRITERIA_MODE_QUERY);
           if (!dbVCs.isEmpty()) {
               System.out.println("Applied Database VCs = {0} "+ dbVCs);
           }

           String memVCs = appliedCriteriaString(ViewCriteria.CRITERIA_MODE_CACHE);
           if (!memVCs.isEmpty()) {
               System.out.println("Applied In-Memory VCs = {0} "+ memVCs);
           }

           String bothVCs = appliedCriteriaString(ViewCriteria.CRITERIA_MODE_QUERY | ViewCriteria.CRITERIA_MODE_CACHE);
           if (!bothVCs.isEmpty()) {
               System.out.println("Applied 'Both' VCs = {0}"+ bothVCs);
           }

           System.out.println("Generated query : {0}"+ getQuery());

           if (params != null) {
               if (getBindingStyle() == SQLBuilder.BINDING_STYLE_ORACLE_NAME) {
                   Map<String, Object> bindsMap = new HashMap<String, Object>(params.length);
                   for (Object param : params) {
                       Object[] nameValue = (Object[])param;
                       String name = (String)nameValue[0];
                       Object value = nameValue[1];
                       bindsMap.put(name, value);
                   }
                   System.out.println("Bind Variables : {0}"+ bindsMap);
               }
           }
       }

       @Override
       public ViewRowImpl createInstanceFromResultSet(QueryCollection queryCollection, ResultSet resultSet) {
           ViewRowImpl row = super.createInstanceFromResultSet(queryCollection, resultSet);
           if (showSql)
              System.out.println("Fetching " + this.getViewObject().getName() + " row with the key " + row.getKey());
           return row;

       }

       private String appliedCriteriaString(int mode) {
           ViewCriteria[] appliedCriterias = getApplyViewCriterias(mode);
           String result = "";
           if (appliedCriterias != null && appliedCriterias.length > 0) {
               List<String> list = new ArrayList<String>(appliedCriterias.length);
               for (ViewCriteria vc : appliedCriterias) {
                   list.add(vc.getName());
               }
               result = list.toString();
           }
           return result;
       }

    /**
     * Returns the bind variable value for BindCoaId.
     * @return bind variable value for BindCoaId
     */
    public Integer getBindCoaId() {
        return (Integer)getNamedWhereClauseParam("BindCoaId");
    }

    /**
     * Sets <code>value</code> for bind variable BindCoaId.
     * @param value value to bind as BindCoaId
     */
    public void setBindCoaId(Integer value) {
        setNamedWhereClauseParam("BindCoaId", value);
    }

    /**
     * Returns the bind variable value for BindEoMstId.
     * @return bind variable value for BindEoMstId
     */
    public Integer getBindEoMstId() {
        return (Integer)getNamedWhereClauseParam("BindEoMstId");
    }

    /**
     * Sets <code>value</code> for bind variable BindEoMstId.
     * @param value value to bind as BindEoMstId
     */
    public void setBindEoMstId(Integer value) {
        setNamedWhereClauseParam("BindEoMstId", value);
    }

    /**
     * Returns the bind variable value for BindArapFlg.
     * @return bind variable value for BindArapFlg
     */
    public String getBindArapFlg() {
        return (String)getNamedWhereClauseParam("BindArapFlg");
    }

    /**
     * Sets <code>value</code> for bind variable BindArapFlg.
     * @param value value to bind as BindArapFlg
     */
    public void setBindArapFlg(String value) {
        setNamedWhereClauseParam("BindArapFlg", value);
    }

    /**
     * Returns the bind variable value for BindAmtTyp.
     * @return bind variable value for BindAmtTyp
     */
    public String getBindAmtTyp() {
        return (String)getNamedWhereClauseParam("BindAmtTyp");
    }

    /**
     * Sets <code>value</code> for bind variable BindAmtTyp.
     * @param value value to bind as BindAmtTyp
     */
    public void setBindAmtTyp(String value) {
        setNamedWhereClauseParam("BindAmtTyp", value);
    }

    /**
     * Returns the bind variable value for BindFilterVal.
     * @return bind variable value for BindFilterVal
     */
    public String getBindFilterVal() {
        return (String)getNamedWhereClauseParam("BindFilterVal");
    }

    /**
     * Sets <code>value</code> for bind variable BindFilterVal.
     * @param value value to bind as BindFilterVal
     */
    public void setBindFilterVal(String value) {
        setNamedWhereClauseParam("BindFilterVal", value);
    }

    /**
     * Returns the bind variable value for BindFrmDt.
     * @return bind variable value for BindFrmDt
     */
    public String getBindFrmDt() {
        return (String)getNamedWhereClauseParam("BindFrmDt");
    }

    /**
     * Sets <code>value</code> for bind variable BindFrmDt.
     * @param value value to bind as BindFrmDt
     */
    public void setBindFrmDt(String value) {
        setNamedWhereClauseParam("BindFrmDt", value);
    }

    /**
     * Returns the bind variable value for BindToDt.
     * @return bind variable value for BindToDt
     */
    public String getBindToDt() {
        return (String)getNamedWhereClauseParam("BindToDt");
    }

    /**
     * Sets <code>value</code> for bind variable BindToDt.
     * @param value value to bind as BindToDt
     */
    public void setBindToDt(String value) {
        setNamedWhereClauseParam("BindToDt", value);
    }

    /**
     * Returns the bind variable value for BindDays.
     * @return bind variable value for BindDays
     */
    public Integer getBindDays() {
        return (Integer)getNamedWhereClauseParam("BindDays");
    }

    /**
     * Sets <code>value</code> for bind variable BindDays.
     * @param value value to bind as BindDays
     */
    public void setBindDays(Integer value) {
        setNamedWhereClauseParam("BindDays", value);
    }

    /**
     * Returns the bind variable value for BindCurVouDt.
     * @return bind variable value for BindCurVouDt
     */
    public String getBindCurVouDt() {
        return (String) getNamedWhereClauseParam("BindCurVouDt");
    }

    /**
     * Sets <code>value</code> for bind variable BindCurVouDt.
     * @param value value to bind as BindCurVouDt
     */
    public void setBindCurVouDt(String value) {
        setNamedWhereClauseParam("BindCurVouDt", value);
    }

    /**
     * Returns the bind variable value for BindPrjId.
     * @return bind variable value for BindPrjId
     */
    public String getBindPrjId() {
        return (String) getNamedWhereClauseParam("BindPrjId");
    }

    /**
     * Sets <code>value</code> for bind variable BindPrjId.
     * @param value value to bind as BindPrjId
     */
    public void setBindPrjId(String value) {
        setNamedWhereClauseParam("BindPrjId", value);
    }
}
