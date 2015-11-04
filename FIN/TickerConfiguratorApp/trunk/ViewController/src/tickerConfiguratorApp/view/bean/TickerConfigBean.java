package tickerConfiguratorApp.view.bean;


import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.server.ViewObjectImpl;

import tickerConfiguratorApp.model.Module.TickerConfigAppAMImpl;

public class TickerConfigBean {
    public TickerConfigBean() {
    }
    private static int VARCHAR = Types.VARCHAR;
    public void saveButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        TickerConfigAppAMImpl am = (TickerConfigAppAMImpl)resolvElDC("TickerConfigAppAMDataControl");
        ViewObjectImpl v = am.getFinTkrTmp1();
    Row row = v.getCurrentRow();
    Integer tkrId = Integer.parseInt(row.getAttribute("FinTkrId").toString());
    Integer slocId = Integer.parseInt(row.getAttribute("FinTkrSlocId").toString());
    String opMode = "U";
        callStoredFunction2(VARCHAR, "PKG_FIN_GUI.FN_TKR_POPULATE_FRM_TMP(?,?,?,?,?)", new Object[] { opMode,"1",slocId,tkrId,tkrId });
    
       

    }
    
    protected Object callStoredFunction2(int sqlReturnType, String stmt, Object[] bindVars) {
           TickerConfigAppAMImpl am = (TickerConfigAppAMImpl)resolvElDC("TickerConfigAppAMDataControl");
           CallableStatement st = null;
           try {
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
                       // System.out.println(e.getMessage());
                   }
               }
           }
       }


    public BindingContainer getBindings() {
           return BindingContext.getCurrent().getCurrentBindingsEntry();
       }
    public void saveasButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        TickerConfigAppAMImpl am = (TickerConfigAppAMImpl)resolvElDC("TickerConfigAppAMDataControl");
        ViewObjectImpl v = am.getFinTkrTmp1();
        Row row = v.getCurrentRow();
        Integer tkrId = Integer.parseInt(row.getAttribute("FinTkrId").toString());
        Integer slocId = Integer.parseInt(row.getAttribute("FinTkrSlocId").toString());
        String opMode = "Y";
        callStoredFunction2(VARCHAR, "PKG_FIN_GUI.FN_TKR_POPULATE_FRM_TMP(?,?,?,?,?)", new Object[] { opMode,"1",slocId,tkrId,tkrId });
        
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
    
    

}
