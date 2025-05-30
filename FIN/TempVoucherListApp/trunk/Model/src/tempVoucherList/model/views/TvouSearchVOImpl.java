package tempVoucherList.model.views;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jbo.ViewCriteria;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.QueryCollection;
import oracle.jbo.server.SQLBuilder;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.ViewRowSetImpl;


// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed May 08 17:08:04 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class TvouSearchVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public TvouSearchVOImpl() {
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

    /**
     * Returns the bind variable value for BindFrmDate.
     * @return bind variable value for BindFrmDate
     */
    public Date getBindFrmDate() {
        return (Date)getNamedWhereClauseParam("BindFrmDate");
    }

    /**
     * Sets <code>value</code> for bind variable BindFrmDate.
     * @param value value to bind as BindFrmDate
     */
    public void setBindFrmDate(Date value) {
        setNamedWhereClauseParam("BindFrmDate", value);
    }

    /**
     * Returns the bind variable value for BindToDate.
     * @return bind variable value for BindToDate
     */
    public Date getBindToDate() {
        return (Date)getNamedWhereClauseParam("BindToDate");
    }

    /**
     * Sets <code>value</code> for bind variable BindToDate.
     * @param value value to bind as BindToDate
     */
    public void setBindToDate(Date value) {
        setNamedWhereClauseParam("BindToDate", value);
    }

    /**
     * Returns the bind variable value for BindTvouTyp.
     * @return bind variable value for BindTvouTyp
     */
    public Integer getBindTvouTyp() {
        return (Integer)getNamedWhereClauseParam("BindTvouTyp");
    }

    /**
     * Sets <code>value</code> for bind variable BindTvouTyp.
     * @param value value to bind as BindTvouTyp
     */
    public void setBindTvouTyp(Integer value) {
        setNamedWhereClauseParam("BindTvouTyp", value);
    }

    /**
     * Returns the bind variable value for BindTvouId.
     * @return bind variable value for BindTvouId
     */
    public String getBindTvouId() {
        return (String)getNamedWhereClauseParam("BindTvouId");
    }

    /**
     * Sets <code>value</code> for bind variable BindTvouId.
     * @param value value to bind as BindTvouId
     */
    public void setBindTvouId(String value) {
        setNamedWhereClauseParam("BindTvouId", value);
    }

    /**
     * Returns the bind variable value for BindSubTyp.
     * @return bind variable value for BindSubTyp
     */
    public Integer getBindSubTyp() {
        return (Integer)getNamedWhereClauseParam("BindSubTyp");
    }

    /**
     * Sets <code>value</code> for bind variable BindSubTyp.
     * @param value value to bind as BindSubTyp
     */
    public void setBindSubTyp(Integer value) {
        setNamedWhereClauseParam("BindSubTyp", value);
    }


    /**
     * Returns the bind variable value for BindAmtFrm.
     * @return bind variable value for BindAmtFrm
     */
    public Number getBindAmtFrm() {
        return (Number)getNamedWhereClauseParam("BindAmtFrm");
    }

    /**
     * Sets <code>value</code> for bind variable BindAmtFrm.
     * @param value value to bind as BindAmtFrm
     */
    public void setBindAmtFrm(Number value) {
        setNamedWhereClauseParam("BindAmtFrm", value);
    }

    /**
     * Returns the bind variable value for BindAmtTo.
     * @return bind variable value for BindAmtTo
     */
    public Number getBindAmtTo() {
        return (Number)getNamedWhereClauseParam("BindAmtTo");
    }

    /**
     * Sets <code>value</code> for bind variable BindAmtTo.
     * @param value value to bind as BindAmtTo
     */
    public void setBindAmtTo(Number value) {
        setNamedWhereClauseParam("BindAmtTo", value);
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
     * Returns the bind variable value for BindCogId.
     * @return bind variable value for BindCogId
     */
    public String getBindCogId() {
        return (String)getNamedWhereClauseParam("BindCogId");
    }

    /**
     * Sets <code>value</code> for bind variable BindCogId.
     * @param value value to bind as BindCogId
     */
    public void setBindCogId(String value) {
        setNamedWhereClauseParam("BindCogId", value);
    }

    /**
     * Returns the bind variable value for BindNaId.
     * @return bind variable value for BindNaId
     */
    public Integer getBindNaId() {
        return (Integer)getNamedWhereClauseParam("BindNaId");
    }

    /**
     * Sets <code>value</code> for bind variable BindNaId.
     * @param value value to bind as BindNaId
     */
    public void setBindNaId(Integer value) {
        setNamedWhereClauseParam("BindNaId", value);
    }

    /**
     * Returns the bind variable value for BindEoId.
     * @return bind variable value for BindEoId
     */
    public Integer getBindEoId() {
        return (Integer)getNamedWhereClauseParam("BindEoId");
    }

    /**
     * Sets <code>value</code> for bind variable BindEoId.
     * @param value value to bind as BindEoId
     */
    public void setBindEoId(Integer value) {
        setNamedWhereClauseParam("BindEoId", value);
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
     * Returns the bind variable value for BindCurrId.
     * @return bind variable value for BindCurrId
     */
    public Integer getBindCurrId() {
        return (Integer)getNamedWhereClauseParam("BindCurrId");
    }

    /**
     * Sets <code>value</code> for bind variable BindCurrId.
     * @param value value to bind as BindCurrId
     */
    public void setBindCurrId(Integer value) {
        setNamedWhereClauseParam("BindCurrId", value);
    }

    /**
     * Returns the bind variable value for BindUsrId.
     * @return bind variable value for BindUsrId
     */
    public Integer getBindUsrId() {
        return (Integer)getNamedWhereClauseParam("BindUsrId");
    }

    /**
     * Sets <code>value</code> for bind variable BindUsrId.
     * @param value value to bind as BindUsrId
     */
    public void setBindUsrId(Integer value) {
        setNamedWhereClauseParam("BindUsrId", value);
    }

    /**
     * Returns the bind variable value for BindModId.
     * @return bind variable value for BindModId
     */
    public String getBindModId() {
        return (String)getNamedWhereClauseParam("BindModId");
    }

    /**
     * Sets <code>value</code> for bind variable BindModId.
     * @param value value to bind as BindModId
     */
    public void setBindModId(String value) {
        setNamedWhereClauseParam("BindModId", value);
    }
    
    protected boolean showSql = false;
      @Override
         public long getEstimatedRowCount() {
             long init = System.currentTimeMillis();
             long counter = super.getEstimatedRowCount();
             long end = System.currentTimeMillis() - init;
             
             //if (showSql)
             //    System.out.println("VO: " + this.getName() + ", from AM: " + this.getApplicationModule().getName() + ", executes SQL Count in: " + end + " (miliseconds)");
             
             return counter;
         }
     @Override
     protected void executeQueryForCollection(Object qc, Object[] params, int noUserParams) {
         short fetchSize = 10;
          if (this.getRangeSize() > 0) {
             fetchSize = ((short)(this.getRangeSize() + 3));
         }
         if (this.getRangeSize() < 10) {
            // System.out.println("Potentially small range size : " + this.getRangeSize() + " for table VO : " +
            //             this.getViewObject().getDefFullName());
             fetchSize = 10;
         }
    
        // System.out.println("Original fetch size : " + this.getFetchSize() + ", Updated fetch size : " + fetchSize +
        //             " for : " + this.getViewObject().getDefFullName());
         if (fetchSize > this.getFetchSize()) {
           //  System.out.println("Updating fetch size : " + fetchSize);
             this.setFetchSize(fetchSize);
         } else {
            // System.out.println("Current fetch size : " + this.getFetchSize() + " for : " +
            //             this.getViewObject().getDefFullName());
         } 
    
         long init = System.currentTimeMillis();
         super.executeQueryForCollection(qc, params, noUserParams);
         long end = System.currentTimeMillis() - init;
    
         //if (showSql)
        //     System.out.println("VO: " + this.getName() + ", from AM: " + this.getApplicationModule().getName() +
         //                  ", executes SQL in: " + end + " (miliseconds)");
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
               // System.out.println("----[Exec query for VO=" + voName + ", RS=" + vrsiName + "]----");
            } else {
               // System.out.println("----[Exec COUNT query for VO=" + voName + "]----");
            }
           // System.out.println("VO Definition Name = {0}"+ voDefName);
    
            String dbVCs = appliedCriteriaString(ViewCriteria.CRITERIA_MODE_QUERY);
            if (!dbVCs.isEmpty()) {
               // System.out.println("Applied Database VCs = {0} "+ dbVCs);
            }
    
            String memVCs = appliedCriteriaString(ViewCriteria.CRITERIA_MODE_CACHE);
            if (!memVCs.isEmpty()) {
                //System.out.println("Applied In-Memory VCs = {0} "+ memVCs);
            }
    
            String bothVCs = appliedCriteriaString(ViewCriteria.CRITERIA_MODE_QUERY | ViewCriteria.CRITERIA_MODE_CACHE);
            if (!bothVCs.isEmpty()) {
               // System.out.println("Applied 'Both' VCs = {0}"+ bothVCs);
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
         //   if (showSql)
           //    System.out.println("Fetching " + this.getViewObject().getName() + " row with the key " + row.getKey());
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
