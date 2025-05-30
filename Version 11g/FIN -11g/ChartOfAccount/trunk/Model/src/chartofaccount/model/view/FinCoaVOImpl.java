package chartofaccount.model.view;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.server.ViewObjectImpl;

import chartofaccount.model.helper.IndexCharacter;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.JboException;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Dec 16 16:23:01 IST 2011
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class FinCoaVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public FinCoaVOImpl() {
    }
    private static int VARCHAR = Types.VARCHAR;
    private static int BOOLEAN = Types.BOOLEAN;
    /**
     * Returns the variable value for CoaNmBindVar.
     * @return variable value for CoaNmBindVar
     */
    public String getCoaNmBindVar() {
        return (String)ensureVariableManager().getVariableValue("CoaNmBindVar");
    }

    /**
     * Sets <code>value</code> for variable CoaNmBindVar.
     * @param value value to bind as CoaNmBindVar
     */
    public void setCoaNmBindVar(String value) {
        ensureVariableManager().setVariableValue("CoaNmBindVar", value);
    }

    public List<IndexCharacter> getCharacterIndexList() {

        String[] alphabet =
        { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
          "W", "X", "Y", "Z" };

        //list that contains 26 entries
        List<IndexCharacter> list = new ArrayList<IndexCharacter>();

        //a list of all initial characters that are found in the row set last_name
        //attribute
        HashMap map = new HashMap();

        RowSet rs = this.getRowSet();

        this.executeQuery();
        Row row = rs.first();

        //query all rows and memorize the initial characters of all employees last
        //name entries
        if (row != null) {
            String character = ((String)row.getAttribute("CoaNm")).toUpperCase().substring(0, 1);
            updateMap(map, row, character);
            while (rs.hasNext()) {
                row = rs.next();
                character = ((String)row.getAttribute("CoaNm")).toUpperCase().substring(0, 1);
                updateMap(map, row, character);
            }
        }

        for (int i = 0; i < alphabet.length; ++i) {

            if (map.containsKey(alphabet[i].toUpperCase())) {
                IndexCharacter ico = new IndexCharacter();
                ico.setCharacter(alphabet[i].toUpperCase());
                ico.setFound(true);
                //get rowIndex from map
                ico.setRowIndex(((Key)map.get((alphabet[i].toUpperCase()))));
                list.add(i, ico);
            } else {
                IndexCharacter ico = new IndexCharacter();
                ico.setCharacter(alphabet[i].toUpperCase());
                ico.setFound(false);
                //get rowIndex from map
                ico.setRowIndex(null);
                list.add(i, ico);
            }

            rs.first();
        }
        return list;
    }

    private void updateMap(HashMap map, Row row, String character) {
        if (!map.containsKey(character)) {
            //remember character and first occurence in rowSet
            map.put(character, row.getKey());
        }
    }

    /**
     * Returns the variable value for CoaName.
     * @return variable value for CoaName
     */
    public String getCoaName() {
        return (String)ensureVariableManager().getVariableValue("CoaName");
    }

    /**
     * Sets <code>value</code> for variable CoaName.
     * @param value value to bind as CoaName
     */
    public void setCoaName(String value) {
        ensureVariableManager().setVariableValue("CoaName", value);
    }

    /**
     * Returns the variable value for CogId.
     * @return variable value for CogId
     */
    public Integer getCogId() {
        return (Integer)ensureVariableManager().getVariableValue("CogId");
    }

    /**
     * Sets <code>value</code> for variable CogId.
     * @param value value to bind as CogId
     */
    public void setCogId(Integer value) {
        ensureVariableManager().setVariableValue("CogId", value);
    }

    /**
     * Returns the variable value for AccId.
     * @return variable value for AccId
     */
    public Integer getAccId() {
        return (Integer)ensureVariableManager().getVariableValue("AccId");
    }

    /**
     * Sets <code>value</code> for variable AccId.
     * @param value value to bind as AccId
     */
    public void setAccId(Integer value) {
        ensureVariableManager().setVariableValue("AccId", value);
        this.executeQuery();
    }

    /**
     * Returns the variable value for Alias.
     * @return variable value for Alias
     */
    public String getAlias() {
        return (String)ensureVariableManager().getVariableValue("Alias");
    }

    /**
     * Sets <code>value</code> for variable Alias.
     * @param value value to bind as Alias
     */
    public void setAlias(String value) {
        ensureVariableManager().setVariableValue("Alias", value);
    }

    /**
     * Returns the variable value for BindCoaId.
     * @return variable value for BindCoaId
     */
    public Integer getBindCoaId() {
        return (Integer)ensureVariableManager().getVariableValue("BindCoaId");
    }

    /**
     * Sets <code>value</code> for variable BindCoaId.
     * @param value value to bind as BindCoaId
     */
    public void setBindCoaId(Integer value) {
        ensureVariableManager().setVariableValue("BindCoaId", value);
    }
    public String resolvEl(String data){
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message=valueExp.getValue(elContext).toString();
        return Message;
      }
    public void setBindParam(Integer eoId , Integer eoType)
    {
            Integer  SlocId =Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));  
                    
                    String org_id=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
                    String cld_id=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
     
     String Value = (String)callStoredFunction2(VARCHAR, "app.pkg_app$ee_eo.FN_GET_NA_ID(?,?,?,?,?)", new Object[] { SlocId,eoType,eoId,cld_id,org_id });
     Integer val = Integer.parseInt(Value);
     
            setAccId(val);
        
        }
    
    protected Object callStoredFunction2(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
      
        try {
            st = getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();
            return st.getObject(1);

        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    /**
     * Returns the variable value for SlocIdBind.
     * @return variable value for SlocIdBind
     */
    public Integer getSlocIdBind() {
        return (Integer)ensureVariableManager().getVariableValue("SlocIdBind");
    }

    /**
     * Sets <code>value</code> for variable SlocIdBind.
     * @param value value to bind as SlocIdBind
     */
    public void setSlocIdBind(Integer value) {
        ensureVariableManager().setVariableValue("SlocIdBind", value);
        this.executeQuery();
    }

    /**
     * Returns the variable value for CoaCldIdBind.
     * @return variable value for CoaCldIdBind
     */
    public String getCoaCldIdBind() {
        return (String)ensureVariableManager().getVariableValue("CoaCldIdBind");
    }

    /**
     * Sets <code>value</code> for variable CoaCldIdBind.
     * @param value value to bind as CoaCldIdBind
     */
    public void setCoaCldIdBind(String value) {
        ensureVariableManager().setVariableValue("CoaCldIdBind", value);
        this.executeQuery();
    }
    public void setBindVar(String CoaCldId,Integer slocId,String orgId){
        setCoaCldIdBind(CoaCldId);
        setSlocIdBind(slocId);
        setCoaHoOrgIdBind(orgId);
    }

    /**
     * Returns the variable value for CoaHoOrgIdBind.
     * @return variable value for CoaHoOrgIdBind
     */
    public String getCoaHoOrgIdBind() {
        return (String)ensureVariableManager().getVariableValue("CoaHoOrgIdBind");
    }

    /**
     * Sets <code>value</code> for variable CoaHoOrgIdBind.
     * @param value value to bind as CoaHoOrgIdBind
     */
    public void setCoaHoOrgIdBind(String value) {
        ensureVariableManager().setVariableValue("CoaHoOrgIdBind", value);
    }

    int counter=0;
       protected ViewRowImpl createRowFromResultSet(Object object, ResultSet resultSet) {
           counter++;
       ViewRowImpl row = super.createRowFromResultSet(object, resultSet);
     // System.out.println("Row fetched with key: " + row.getKey() + ", for VO: " + this.getName() + " row count: " + counter);

       return row;
       }

    protected void executeQueryForCollection(Object object, Object[] object2, int i) {
        super.executeQueryForCollection(object, object2, i);
        this.setFetchMode(this.FETCH_AS_NEEDED);
    }
}


