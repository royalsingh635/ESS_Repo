package graphTickerApp.view.bean;

import graphTickerApp.model.module.GraphTickerAMImpl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.jbo.JboException;

public class GraphTickerBean {
    String op_curr = null;
    Integer op_val = 0;
    String op_val_type = null;
    String op_val_unit = null;
    Integer trend_val = 0;
    String trend_val_unit = null;
    String trend_val_typ = null;
    String org_id = "01";
    String stat=null;
    //private static String value;
    
    public GraphTickerBean() { 
        System.out.println("In GraphTicker Bean");
       
       // this.callStoredFunction("fin.pkg_fin_trial.calc_ticker(?,?,?,?,?,?,?,?,?,?)", new Object[] { 1, 1,org_id });
         setStatforConfigure();  
    }
    
    protected Object callStoredFunction2(int sqlReturnType, String stmt, Object[] bindVars) {
            CallableStatement st = null;
            try {
                GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
                st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
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
    public void setStatforConfigure(){
        String st="";
      
         try{
        	st=(String)this.callStoredFunction2(STRING,"APP.PKG_APP.FN_GET_TKR_DISP_STAT()", new Object[] {});      
         }catch(oracle.jbo.JboException ex){
           
         }
        this.setStat(st);
       
    }
    
    public void callTickerFunc(ActionEvent actionEvent) {
        callStoredFunction("fin.pkg_fin_trial.calc_ticker(?,?,?,?,?,?,?,?,?,?)", new Object[] { 1, 1,org_id });
       
    }
    
    private static int NUMBER = Types.INTEGER;
           private static int STRING = Types.VARCHAR;
        protected Object callStoredFunction(String stmt, Object[] bindVars) {

                CallableStatement st = null;
                try { 
                    GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
                    // 1. Create a JDBC CallabledStatement
                    st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
                    // 2. Register the first bind variable for the return value
                    st.registerOutParameter(1, NUMBER);
                    st.registerOutParameter(5, STRING);
                    st.registerOutParameter(6, NUMBER);
                    st.registerOutParameter(7, STRING);
                    st.registerOutParameter(8, STRING);
                    st.registerOutParameter(9, NUMBER);
                    st.registerOutParameter(10, STRING);
                    st.registerOutParameter(11, STRING);
                   
                    
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
                   op_curr = st.getObject(5).toString();
            op_val = Integer.parseInt(st.getObject(6).toString());
            op_val_type = st.getObject(7).toString();
            op_val_unit = st.getObject(8).toString();
            trend_val = Integer.parseInt(st.getObject(9).toString());
            trend_val_unit = st.getObject(10).toString();
            trend_val_typ = st.getObject(11).toString();
                    return null;
                } catch (SQLException e) {
                    throw new JboException(e);
                } finally {
                    if (st != null) {
                        try {
                            // 7. Close the statement
                            st.close();
                        } catch (SQLException e) {
                          
                        }
                    }
                }
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

    public void setOp_curr(String op_curr) {
        this.op_curr = op_curr;
    }

    public String getOp_curr() {
        return op_curr;
    }

    public void setOp_val(Integer op_val) {
        this.op_val = op_val;
    }

    public Integer getOp_val() {
        return op_val;
    }

    public void setOp_val_type(String op_val_type) {
        this.op_val_type = op_val_type;
    }

    public String getOp_val_type() {
        return op_val_type;
    }

    public void setOp_val_unit(String op_val_unit) {
        this.op_val_unit = op_val_unit;
    }

    public String getOp_val_unit() {
        return op_val_unit;
    }

    public void setTrend_val(Integer trend_val) {
        this.trend_val = trend_val;
    }

    public Integer getTrend_val() {
        return trend_val;
    }

    public void setTrend_val_unit(String trend_val_unit) {
        this.trend_val_unit = trend_val_unit;
    }

    public String getTrend_val_unit() {
        return trend_val_unit;
    }

    public void setTrend_val_typ(String trend_val_typ) {
        this.trend_val_typ = trend_val_typ;
    }

    public String getTrend_val_typ() {
        
        
        
        return trend_val_typ;
    }
/* 
    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
        return am.getValueOfPending();
    } */

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getStat() {
        return stat;
    }
}
