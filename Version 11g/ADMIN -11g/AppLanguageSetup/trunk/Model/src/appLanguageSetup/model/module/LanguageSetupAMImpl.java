package appLanguageSetup.model.module;

import appLanguageSetup.model.module.common.LanguageSetupAM;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.JboException;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewLinkImpl;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.context.RequestContext;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Jul 25 10:48:11 IST 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LanguageSetupAMImpl extends ApplicationModuleImpl implements LanguageSetupAM {
    /**
     * This is the default constructor (do not remove).
     */
    
     public static int VARCHAR = Types.VARCHAR;
        String add_mode = null;
        String edit_mode = null;
        String view_mode = null;
        String del_mode = null;
    public LanguageSetupAMImpl() {
    }

    /**
     * Container's getter for AppLang1.
     * @return AppLang1
     */
    public ViewObjectImpl getAppLang1() {
        return (ViewObjectImpl)findViewObject("AppLang1");
    }

    /**
     * Container's getter for AppCuntryLang1.
     * @return AppCuntryLang1
     */
    public ViewObjectImpl getAppCuntryLang1() {
        return (ViewObjectImpl)findViewObject("AppCuntryLang1");
    }

    /**
     * Container's getter for AppLangTOAppcntryLangVL1.
     * @return AppLangTOAppcntryLangVL1
     */
    public ViewLinkImpl getAppLangTOAppcntryLangVL1() {
        return (ViewLinkImpl)findViewLink("AppLangTOAppcntryLangVL1");
    }
    
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
            CallableStatement st = null;
            try {
                st = getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
                st.registerOutParameter(1, sqlReturnType);
                if (bindVars != null) {
                    for (int z = 0; z < bindVars.length; z++) {
                        st.setObject(z + 2, bindVars[z]);
                    }
                }
                st.registerOutParameter(7, VARCHAR);
                st.registerOutParameter(8, VARCHAR);
                st.registerOutParameter(9, VARCHAR);
                st.registerOutParameter(10, VARCHAR);
                st.executeUpdate();
                try {
                    setAdd_mode(st.getObject(7).toString());
                    setEdit_mode((st.getObject(8).toString()));
                    setView_mode(st.getObject(9).toString());
                    setDel_mode(st.getObject(10).toString());
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

                    }
                }
            }
        }


    public void setAdd_mode(String add_mode) {
        this.add_mode = add_mode;
    }

    public String getAdd_mode() {
        return add_mode;
    }

    public void setEdit_mode(String edit_mode) {
        this.edit_mode = edit_mode;
    }

    public String getEdit_mode() {
        return edit_mode;
    }

    public void setView_mode(String view_mode) {
        this.view_mode = view_mode;
    }

    public String getView_mode() {
        return view_mode;
    }

    public void setDel_mode(String del_mode) {
        this.del_mode = del_mode;
    }

    public String getDel_mode() {
        return del_mode;
    }
    
    public Integer on_load_page()
                {  int count = 0;
                String cld_id =  resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString(); 
                Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString()); 
                String org_id =  resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString(); 
                Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString()); 
                callStoredFunction(VARCHAR,"APP.fn_get_usr_doc_access_param(?,?,?,?,?,?,?,?,?)",
                                           new Object[] { cld_id, sloc_id, org_id, 9,usr_id});
                String calledFrom =  resolvEl("#{pageFlowScope.PARAM_PG_CALLED}").toString(); 
                if(add_mode.equalsIgnoreCase("Y") == false && add_mode.equalsIgnoreCase("N") == false)
                {            count = 1;
                    }
                else if(edit_mode.equalsIgnoreCase("Y") == false && edit_mode.equalsIgnoreCase("N") == false)
                {
                    count = 1;
                    }
                else if(view_mode.equalsIgnoreCase("Y") == false && view_mode.equalsIgnoreCase("N") == false)
                {
                   count = 1; 
                    }
                else if (del_mode.equalsIgnoreCase("Y") == false && del_mode.equalsIgnoreCase("N") == false)
                {
                   count = 1; 
                    }
                 else if(calledFrom.equalsIgnoreCase("P") == false && calledFrom.equalsIgnoreCase("M") == false)
                {
                   count = 1; 
                    }
                Map paramMap = RequestContext.getCurrentInstance().getPageFlowScope();
                paramMap.put("PARAM_PG_ADD_MD", add_mode); 
                paramMap.put("PARAM_PG_EDIT_MD", edit_mode); 
                paramMap.put("PARAM_PG_VIEW_MD", view_mode); 
                paramMap.put("PARAM_PG_DEL_MD", del_mode); 
             return count;
            }
    
    public String resolvEl(String data) {
           FacesContext fc = FacesContext.getCurrentInstance();
           Application app = fc.getApplication();
           ExpressionFactory elFactory = app.getExpressionFactory();
           ELContext elContext = fc.getELContext();
           ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
           String msg = valueExp.getValue(elContext).toString();
           return msg;
       }

    /**
     * Container's getter for AppLang2.
     * @return AppLang2
     */
    public ViewObjectImpl getAppLang2() {
        return (ViewObjectImpl)findViewObject("AppLang2");
    }
}
