package schedule6.model.services.utill;


import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.context.FacesContext;

import oracle.adf.share.ADFContext;

import oracle.jbo.JboException;
import oracle.jbo.server.ApplicationModuleImpl;

import org.apache.myfaces.trinidad.context.RequestContext;



public class ModelUtill {
    
    ApplicationModuleImpl am;
    
    String addMode;
    String editMode;
    String deleteMode;
    String viewMode;

    public ModelUtill(){
        super();
    }
    
    public ModelUtill(ApplicationModuleImpl am){
        this.am=am;
    }


   public  Object getGlobalModeFromDb(Object[] bindVars) {
       
        String stmt= "APP.fn_get_usr_doc_access_param(?,?,?,?,?,?,?,?,?)";
        int sqlReturnType=Types.VARCHAR;
                           
        CallableStatement st = null;
        try {
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.registerOutParameter(7, Types.VARCHAR);
            st.registerOutParameter(8, Types.VARCHAR);
            st.registerOutParameter(9, Types.VARCHAR);
            st.registerOutParameter(10, Types.VARCHAR);
            st.executeUpdate();
            try {
                 this.setAddMode(st.getObject(7).toString());
                 this.setEditMode((st.getObject(8).toString()));
                 this.setDeleteMode(st.getObject(9).toString());
                 this.setViewMode(st.getObject(10).toString());
            } catch (NullPointerException e) {
                System.out.println(e);
                e.printStackTrace();
            }
            return st.getObject(1);

        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
   
   
   // this method will set global modes
   
   public void setGlobalMode(){
     //  System.out.println("Trying to update mode");
        
           Map paramMap = RequestContext.getCurrentInstance().getPageFlowScope();
           paramMap.put("PARAM_PG_ADD_MD", this.getAddMode());
           paramMap.put("PARAM_PG_EDIT_MD", this.getEditMode());
           paramMap.put("PARAM_PG_VIEW_MD", this.getViewMode());
           paramMap.put("PARAM_PG_DEL_MD", this.getDeleteMode());
           
      // System.out.println("Mode has been setted");
   }


    public  Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
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
            System.out.println(st.getObject(1));
            return st.getObject(1);

        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    
    

    public static Object resolvEl(String data) {

        ADFContext adfCtx = ADFContext.getCurrent();
        ELContext elContext = adfCtx.getELContext();
        ValueExpression valueExp = adfCtx.getExpressionFactory().createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext).toString();

        return Message;
    }

    public void setAddMode(String addMode) {
        this.addMode = addMode;
    }

    public String getAddMode() {
        return addMode;
    }

    public void setEditMode(String editMode) {
        this.editMode = editMode;
    }

    public String getEditMode() {
        return editMode;
    }

    public void setDeleteMode(String deleteMode) {
        this.deleteMode = deleteMode;
    }

    public String getDeleteMode() {
        return deleteMode;
    }

    public void setViewMode(String viewMode) {
        this.viewMode = viewMode;
    }

    public String getViewMode() {
        return viewMode;
    }
}
