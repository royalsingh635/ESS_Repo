package tickerConfiguratorApp.view.bean;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.server.ViewObjectImpl;

import tickerConfiguratorApp.model.Module.TickerConfigAppAMImpl;

public class TickerConfiguatorBean {
    
    Integer count =Integer.parseInt(getBindings().getOperationBinding("on_load_page").execute().toString());

    
    public TickerConfiguatorBean() {
    }
    
                private static int VARCHAR = Types.VARCHAR;

    public void naTypeValuechange(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue().toString().equalsIgnoreCase("A"))
        {
        TickerConfigAppAMImpl am = (TickerConfigAppAMImpl)resolvElDC("TickerConfigAppAMDataControl");
        ViewObjectImpl v = am.getFinTkrNaTypTmp2();
        RowSetIterator createRowSetIterator = v.createRowSetIterator(null);
        while(createRowSetIterator.hasNext())
        {
            ViewObjectImpl v1 = am.getFinTkrCoaTmp3();
         RowSetIterator rit =   v1.createRowSetIterator(null);
            while(rit.hasNext())
            {
                Row row1 = rit.next();
                row1.remove();
                }
            Row row = createRowSetIterator.next();
            row.remove();
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

    public void cantainsNAValueChange(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue().toString().equalsIgnoreCase("A"))
        {
                TickerConfigAppAMImpl am = (TickerConfigAppAMImpl)resolvElDC("TickerConfigAppAMDataControl");
                ViewObjectImpl v = am.getFinTkrContainsCoaTmp2();
                RowSetIterator createRowSetIterator = v.createRowSetIterator(null);
                while(createRowSetIterator.hasNext())
                {
                Row row = createRowSetIterator.next();
                row.remove();
                
                }
        }
    }
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
                    FacesContext fc = FacesContext.getCurrentInstance();
                                  FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved", "Record Saved Successfully.");
                                  fc.addMessage("Success", msg);
                   

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
                    FacesContext fc = FacesContext.getCurrentInstance();
                                  FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved", "Record Saved Successfully.");
                                  fc.addMessage("Success", msg);
                    
                }


    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }
}


