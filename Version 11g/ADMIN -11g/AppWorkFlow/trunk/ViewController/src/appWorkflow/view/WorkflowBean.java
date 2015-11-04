package appWorkflow.view;

import appWorkflow.model.module.AppWfAMImpl;
import appWorkflow.view.AddPageBean;
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
import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.JboException;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class WorkflowBean {


    private RichPopup pop;
    private RichPopup deletepop;
    private RichPopup savepop;
    private RichPopup editpop;
    private RichPopup poplvl;
    private RichPopup editpoplvl;
    private RichPopup deletepoplvl;
    private RichPopup popLvlUser;
    private RichPopup deletePopLvlUser;
    private static String cancelButtonAdd="false";
   
    private static String saveButtonAdd="false";
  

    public WorkflowBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    // to call the reqd popup

    private void showPopup(RichPopup pop, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null) {
                String popupId = pop.getClientId(context);
                if (popupId != null) {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible) {
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                    } else {
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // ----------------------------------------------------------------------

    public void DialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {


            BindingContainer bindings3 = getBindings();
            OperationBinding operationBinding3 = bindings3.getOperationBinding("Commit");
            operationBinding3.execute();
            BindingContainer bindings4 = getBindings();
            OperationBinding operationBinding4 = bindings4.getOperationBinding("Execute");
            operationBinding4.execute();
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("getSavetoWf");
            operationBinding.execute();
            BindingContainer bindings10 = getBindings();
            OperationBinding operationBinding10 = bindings10.getOperationBinding("Commit");
            operationBinding10.execute();
            BindingContainer bindings12 = getBindings();
            OperationBinding operationBinding12 = bindings12.getOperationBinding("Execute");
            operationBinding12.execute();


            BindingContainer bindings11 = getBindings();
            OperationBinding operationBinding11 = bindings11.getOperationBinding("Commit");
            operationBinding11.execute();
            BindingContainer bindings2 = getBindings();
            OperationBinding operationBinding2 = bindings2.getOperationBinding("Execute3");
            operationBinding2.execute();

        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");

            operationBinding.execute();
            operationBinding1.execute();
        }
    }

    public void SaveDialogListener(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("yes")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();

            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
            createOpBAddress.execute();
        } else if (dialogEvent.getOutcome().name().equals("no")) {
            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
            createOpBAddress.execute();

            BindingContainer bindings = getBindings();
            OperationBinding createOpBAddress1 = bindings.getOperationBinding("Execute");
            createOpBAddress1.execute();

        }
    }


    public void DeleteDialogListener(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("yes")) {
            
            
            AppWfAMImpl am=(AppWfAMImpl)resolvElDC("AppWfAMDataControl");
            ViewObject v1=am.getWfView1();
            Row r1=v1.getCurrentRow();
            ViewObject v2=am.getWfLvl1();
            ViewObject v3=am.getWfLvlUsr1();
            Integer WfId=Integer.parseInt(r1.getAttribute("WfId").toString());
            Integer WfSlocId=Integer.parseInt(r1.getAttribute("WfSlocId").toString());
            String WfOrgId=r1.getAttribute("WfOrgId").toString();
            
        String a= getDeleteWf(WfSlocId,WfOrgId,WfId );
            System.out.println(a+"=aaaaaaaaaaaaa");
           
            am.getDBTransaction().commit();
           v1.executeQuery();
            v2.executeQuery();
            v3.executeQuery();
           
            
            
        } else if (dialogEvent.getOutcome().name().equals("no")) {

        }
    }

    public void Create(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();

        showPopup(pop, true);
    }

    public void Edit(ActionEvent actionEvent) {

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("getPopulateToTemp");
        operationBinding.execute();
      

        AppWfAMImpl am = (AppWfAMImpl)resolvElDC("AppWfAMDataControl");

        ViewObjectImpl v = am.getAppWf1();
        Row r1 = v.getCurrentRow();
        Integer usrID = Integer.parseInt(r1.getAttribute("UsrIdCreate").toString());
        Integer wfID = Integer.parseInt(r1.getAttribute("WfId").toString());
        String SessionID = getSessionIdFunc(usrID, wfID);
        r1.setAttribute("WfSessId", SessionID);
        showPopup(pop, true);
    }

    public void Save(ActionEvent actionEvent) {

        AppWfAMImpl am = (AppWfAMImpl)resolvElDC("AppWfAMDataControl");
        ViewObjectImpl v = am.getAppWf1();
        Row r1 = v.getCurrentRow();
        Integer usrID = Integer.parseInt(r1.getAttribute("UsrIdCreate").toString());
        Integer wfID = Integer.parseInt(r1.getAttribute("WfId").toString());


        String SessionID = getSessionIdFunc(usrID, wfID);
        r1.setAttribute("WfSessId", SessionID);
        BindingContainer bindings3 = getBindings();
        OperationBinding operationBinding3 = bindings3.getOperationBinding("Commit");
        operationBinding3.execute();
        BindingContainer bindings4 = getBindings();
        OperationBinding operationBinding4 = bindings4.getOperationBinding("Execute");
        operationBinding4.execute();
        BindingContainer bindings5 = getBindings();
        OperationBinding operationBinding5 = bindings5.getOperationBinding("Execute1");
        operationBinding5.execute();
        BindingContainer bindings6 = getBindings();
        OperationBinding operationBinding6 = bindings6.getOperationBinding("Execute2");
        operationBinding6.execute();

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("getSavetoWf");
        operationBinding.execute();

        System.out.println("getSavetoWf------------Called--------without error");

        BindingContainer bindings1 = getBindings();
        OperationBinding operationBinding1 = bindings1.getOperationBinding("getSavetoWfLvl");
        operationBinding1.execute();


        System.out.println("getSavetoWfLvl------------Called--------without error");


        BindingContainer bindings2 = getBindings();
        OperationBinding operationBinding2 = bindings2.getOperationBinding("getSavetoWfLvlUsr");
        operationBinding2.execute();

        System.out.println("getSavetoWfLvlUsr------------Called--------without error");

        BindingContainer bindings10 = getBindings();
        OperationBinding operationBinding10 = bindings10.getOperationBinding("Commit");
        operationBinding10.execute();

    }

    public void Delete(ActionEvent actionEvent) {
        showPopup(deletepop, true);
    }


    public void setPop(RichPopup pop) {
        this.pop = pop;
    }

    public RichPopup getPop() {
        return pop;
    }


    public void setDeletepop(RichPopup deletepop) {
        this.deletepop = deletepop;
    }

    public RichPopup getDeletepop() {
        return deletepop;
    }

    public void setSavepop(RichPopup savepop) {
        this.savepop = savepop;
    }

    public RichPopup getSavepop() {
        return savepop;
    }


    public void CancelListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute3");

        operationBinding.execute();
        operationBinding1.execute();
    }

    public void setEditpop(RichPopup editpop) {
        this.editpop = editpop;
    }

    public RichPopup getEditpop() {
        return editpop;
    }

    /*  --------------------  For App$lvl  -----------------------   */

    public void CreateLvl(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
        operationBinding.execute();
        showPopup(poplvl, true);
    }

    public void EditLvl(ActionEvent actionEvent) {
        showPopup(poplvl, true);
    }

    public void DeleteLvl(ActionEvent actionEvent) {
        showPopup(deletepoplvl, true);
    }

    public void DialogLvlListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            BindingContainer bindings3 = getBindings();
            OperationBinding operationBinding3 = bindings3.getOperationBinding("Commit");
            operationBinding3.execute();
            BindingContainer bindings4 = getBindings();
            OperationBinding operationBinding4 = bindings4.getOperationBinding("Execute1");
            operationBinding4.execute();
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("getSavetoWfLvl");
            operationBinding.execute();
            BindingContainer bindings10 = getBindings();
            OperationBinding operationBinding10 = bindings10.getOperationBinding("Commit");
            operationBinding10.execute();

        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute1");

            operationBinding.execute();
            operationBinding1.execute();
        }
    }

    public void setPoplvl(RichPopup poplvl) {
        this.poplvl = poplvl;
    }

    public RichPopup getPoplvl() {
        return poplvl;
    }

    public void CancelLvlListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute4");

        operationBinding.execute();
        operationBinding1.execute();
    }

    public void setEditpoplvl(RichPopup editpoplvl) {
        this.editpoplvl = editpoplvl;
    }

    public RichPopup getEditpoplvl() {
        return editpoplvl;
    }

    public void DeleteDialogLvlListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
            
            AppWfAMImpl am=(AppWfAMImpl)resolvElDC("AppWfAMDataControl");
          //  ViewObject v1=am.getWfView1();
            
            ViewObject v2=am.getWfLvl1();
            Row r1=v2.getCurrentRow();
            ViewObject v3=am.getWfLvlUsr1();
            
            Integer WfSlocId=Integer.parseInt(r1.getAttribute("WfSlocId").toString());
            String WfOrgId=r1.getAttribute("WfOrgId").toString();
            Integer WfId=Integer.parseInt(r1.getAttribute("WfId").toString());
            Integer WfLvl=Integer.parseInt(r1.getAttribute("WfLvl").toString());
            
            
            String a= getDeleteWfLvl(WfSlocId,WfOrgId,WfId,WfLvl);
            System.out.println(a+"=DleteLvl");
            
            am.getDBTransaction().commit();
         //   v1.executeQuery();
            v2.executeQuery();
            v3.executeQuery();
            
            
          /*  BindingContainer bindings1 = getBindings();
            OperationBinding operationBinding1 = bindings1.getOperationBinding("getDeleteWfLvl");
            operationBinding1.execute();
            BindingContainer bindings2 = getBindings();
            OperationBinding operationBinding2 = bindings2.getOperationBinding("Commit");
            operationBinding2.execute();
            BindingContainer bindings3 = getBindings();
            OperationBinding operationBinding3 = bindings3.getOperationBinding("Execute4");
            operationBinding3.execute();
            */
            
        } else if (dialogEvent.getOutcome().name().equals("no")) {

        }
    }

    public void setDeletepoplvl(RichPopup deletepoplvl) {
        this.deletepoplvl = deletepoplvl;
    }

    public RichPopup getDeletepoplvl() {
        return deletepoplvl;
    }

    /*-----------------------------------For App$Lvl$Usr---------------------------------*/

    public void createLvlUser(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert2");
        operationBinding.execute();
        showPopup(popLvlUser, true);
    }

    public void editLvlUser(ActionEvent actionEvent) {
        showPopup(popLvlUser, true);
    }

    public void deleteLvlUser(ActionEvent actionEvent) {
        showPopup(popLvlUser, true);
    }

    public void setPopLvlUser(RichPopup popLvlUser) {
        this.popLvlUser = popLvlUser;
    }

    public RichPopup getPopLvlUser() {
        return popLvlUser;
    }

    public void DialogLvlUserListner(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            BindingContainer bindings3 = getBindings();
            OperationBinding operationBinding3 = bindings3.getOperationBinding("Commit");
            operationBinding3.execute();
            BindingContainer bindings4 = getBindings();
            OperationBinding operationBinding4 = bindings4.getOperationBinding("Execute2");
            operationBinding4.execute();
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("getSavetoWfLvlUsr");
            operationBinding.execute();
            BindingContainer bindings10 = getBindings();
            OperationBinding operationBinding10 = bindings10.getOperationBinding("Commit");
            operationBinding10.execute();

        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute2");

            operationBinding.execute();
            operationBinding1.execute();
        }
    }

    public void CancelLvlUserListner(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute5");

        operationBinding.execute();
        operationBinding1.execute();
    }

    public void setDeletePopLvlUser(RichPopup deletePopLvlUser) {
        this.deletePopLvlUser = deletePopLvlUser;
    }

    public RichPopup getDeletePopLvlUser() {
        return deletePopLvlUser;
    }

    public void DeleteDialogLvlUserListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
        
            AppWfAMImpl am=(AppWfAMImpl)resolvElDC("AppWfAMDataControl");
            //  ViewObject v1=am.getWfView1();
            
           // ViewObject v2=am.getWfLvl1();
            ViewObject v3=am.getWfLvlUsr1();
            
            Row r1=v3.getCurrentRow();
           
            
            Integer WfSlocId=Integer.parseInt(r1.getAttribute("WfSlocId").toString());
            String WfOrgId=r1.getAttribute("WfOrgId").toString();
            Integer WfId=Integer.parseInt(r1.getAttribute("WfId").toString());
            Integer WfLvl=Integer.parseInt(r1.getAttribute("WfLvl").toString());
            Integer WfLvlUsrId=Integer.parseInt(r1.getAttribute("WfLvlUsrId").toString());
            
            String a= getDeleteWfLvlUsr(WfSlocId,WfOrgId,WfId,WfLvl,WfLvlUsrId);
            System.out.println(a+"=DleteLvl");
            
            am.getDBTransaction().commit();
            //   v1.executeQuery();
           // v2.executeQuery();
            v3.executeQuery();
            
        
          /*  BindingContainer bindings1 = getBindings();
            OperationBinding operationBinding1 = bindings1.getOperationBinding("getDeleteWfLvlUsr");
            operationBinding1.execute();
            BindingContainer bindings2 = getBindings();
            OperationBinding operationBinding2 = bindings2.getOperationBinding("Commit");
            operationBinding2.execute();
            BindingContainer bindings3 = getBindings();
            OperationBinding operationBinding3 = bindings3.getOperationBinding("Execute5");
            operationBinding3.execute();
*/
        } else if (dialogEvent.getOutcome().name().equals("no")) {

        }
    }

    private static int INTEGER = Types.NUMERIC;
    private static int DATE = Types.DATE;
    private static int STRING = Types.VARCHAR;

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            AppWfAMImpl am = (AppWfAMImpl)resolvElDC("AppWfAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                    System.out.println(bindVars[z] + "z");
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
    public String getDeleteWf(Integer P_SLOC_ID,String P_ORG_ID, Integer WfID) {
           System.out.println("Values:"+P_SLOC_ID+"--"+P_ORG_ID+"--"+WfID+"--");
             return (String)callStoredFunction(STRING, "APP.PKG_WF.FN_WF_DEL_cascade(?,?,?)",
                                                      new Object[]{P_SLOC_ID,P_ORG_ID,WfID});
       }
    
    public String getDeleteWfLvl(Integer P_SLOC_ID,String P_ORG_ID, Integer WfID,Integer p_lvl_id) {
             return (String)callStoredFunction(STRING, "APP.PKG_WF.FN_WF_LVL_DEL(?,?,?,?)",
                                                      new Object[]{P_SLOC_ID,P_ORG_ID,WfID,p_lvl_id});
       }
    public String getDeleteWfLvlUsr(Integer P_SLOC_ID,String P_ORG_ID, Integer WfID,Integer p_lvl_id,Integer p_lvl_usr_id) {
             return (String)callStoredFunction(STRING, "APP.PKG_WF.FN_WF_LVL_USR_DEL(?,?,?,?,?)",
                                                      new Object[]{P_SLOC_ID,P_ORG_ID,WfID,p_lvl_id,p_lvl_usr_id});
       }
    public String getSessionIdFunc(Integer UserID, Integer WfID) {
        return (String)callStoredFunction(STRING, "APP.PKG_WF.FN_WF_GEN_SESSION_ID(?,?)",
                                          new Object[] { UserID, WfID });
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

  
    public void SaveAll(ActionEvent actionEvent) {
        
               BindingContainer bindings = getBindings();
               OperationBinding operationBinding = bindings.getOperationBinding("getDeleteAll");
               operationBinding.execute();
               BindingContainer bindingsa = getBindings();
               OperationBinding operationBindings = bindingsa.getOperationBinding("Commit");
               operationBindings.execute();
               BindingContainer bindingex = getBindings();
               OperationBinding operationBindingex = bindingex.getOperationBinding("Execute");
               operationBindingex.execute();
               BindingContainer bindingex1 = getBindings();
               OperationBinding operationBindingex1 = bindingex1.getOperationBinding("Execute1");
               operationBindingex1.execute();
               BindingContainer bindingex2 = getBindings();
               OperationBinding operationBindingex2 = bindingex2.getOperationBinding("Execute2");
               operationBindingex2.execute();
    }

    public String SaveActionAll() {
        if (saveButtonAdd.equalsIgnoreCase("false")){
           System.out.println("false");
        }
        else if(saveButtonAdd.equalsIgnoreCase("true"))
        {
        AppWfAMImpl am=(AppWfAMImpl)resolvElDC("AppWfAMDataControl");
        ViewObject v1=am.getAppWf1();
        Row r1=v1.getCurrentRow();
        ViewObject v2=am.getAppWfLvl1();
        ViewObject v3=am.getAppWfLvlUsr();
        
        Integer WfSlocId=Integer.parseInt(r1.getAttribute("WfSlocId").toString());
        String SessionID=r1.getAttribute("WfSessId").toString();
        String WfOrgId=r1.getAttribute("WfOrgId").toString();
        Integer WfId=Integer.parseInt(r1.getAttribute("WfId").toString());
        String p_op_mode="N";
              
        //save from app to wf 
        String b=am.getSaveFromTemp(p_op_mode, WfSlocId, WfOrgId, WfId, WfId, SessionID);
          am.getDBTransaction().commit();
          
        //delete all from appwf
       String a= am.getDeleteAll(WfSlocId, SessionID, WfOrgId, WfId);
        System.out.println(a+"del app wf");
          
        am.getDBTransaction().commit();
        
        v1.executeQuery();
        v2.executeQuery();
        v3.executeQuery();
        this.setCancelButtonAdd("false");
        this.setSaveButtonAdd("false");
        }
        return "BackWithoutsave";
    }


    public String EditAction() {
       BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("getPopulateToTemp");
        operationBinding.execute();
        BindingContainer bindingsa = getBindings();
        OperationBinding operationBindings = bindingsa.getOperationBinding("Commit");
        operationBindings.execute();
        
        
        return "edit";
    }

    public String EditSaveAllAction() {
        
       
        AppWfAMImpl am=(AppWfAMImpl)resolvElDC("AppWfAMDataControl");
        ViewObject v1=am.getAppWf1();
        Row r1=v1.getCurrentRow();
        ViewObject v2=am.getAppWfLvl1();
        ViewObject v3=am.getAppWfLvlUsr();
        
        Integer WfSlocId=Integer.parseInt(r1.getAttribute("WfSlocId").toString());
        String SessionID=r1.getAttribute("WfSessId").toString();
        String WfOrgId=r1.getAttribute("WfOrgId").toString();
        Integer WfId=Integer.parseInt(r1.getAttribute("WfId").toString());
        String p_op_mode="O";
              
        //save from app to wf 
        String b=am.getSaveFromTemp(p_op_mode, WfSlocId, WfOrgId, WfId, WfId, SessionID);
          am.getDBTransaction().commit();
          
        //delete all from appwf
        String a= am.getDeleteAll(WfSlocId, SessionID, WfOrgId, WfId);
        System.out.println(a+"del app wf");
          
        am.getDBTransaction().commit();
        
        v1.executeQuery();
        v2.executeQuery();
        v3.executeQuery();
        
        return "backToSearch"; 
       
    }

    public void DeleteLvlUsr(ActionEvent actionEvent) {
        showPopup(deletePopLvlUser, true);
    }

    public String EditCancelAction() {
        
        
        AppWfAMImpl am=(AppWfAMImpl)resolvElDC("AppWfAMDataControl");
        ViewObject v1=am.getAppWf1();
        Row r1=v1.getCurrentRow();
        ViewObject v2=am.getAppWfLvl1();
        ViewObject v3=am.getAppWfLvlUsr();
        
        Integer WfSlocId=Integer.parseInt(r1.getAttribute("WfSlocId").toString());
        String SessionID=r1.getAttribute("WfSessId").toString();
        String WfOrgId=r1.getAttribute("WfOrgId").toString();
        Integer WfId=Integer.parseInt(r1.getAttribute("WfId").toString());
       
              
      
          
        //delete all from appwf
        String a= am.getDeleteAll(WfSlocId, SessionID, WfOrgId, WfId);
        System.out.println(a+"del app wf");
          
        am.getDBTransaction().commit();
        
        v1.executeQuery();
        v2.executeQuery();
        v3.executeQuery();
        return "backToSearch";
    }

    public String AddCancelAction() {
        
        if (cancelButtonAdd.equalsIgnoreCase("false")){
           System.out.println("false");
        }
        else if(cancelButtonAdd.equalsIgnoreCase("true"))
        { 
        
            
            
        AppWfAMImpl am=(AppWfAMImpl)resolvElDC("AppWfAMDataControl");
        ViewObject v1=am.getAppWf1();
        Row r1=v1.getCurrentRow();
        ViewObject v2=am.getAppWfLvl1();
        ViewObject v3=am.getAppWfLvlUsr();
        
        Integer WfSlocId=Integer.parseInt(r1.getAttribute("WfSlocId").toString());
        String SessionID=r1.getAttribute("WfSessId").toString();
        String WfOrgId=r1.getAttribute("WfOrgId").toString();
        Integer WfId=Integer.parseInt(r1.getAttribute("WfId").toString());
        
              
        
          
        //delete all from appwf
        String a= am.getDeleteAll(WfSlocId, SessionID, WfOrgId, WfId);
        System.out.println(a+"del app wf");
          
        am.getDBTransaction().commit();
        
        v1.executeQuery();
        v2.executeQuery();
        v3.executeQuery();
            this.setCancelButtonAdd("false");
            this.setSaveButtonAdd("false");
        }
        return "BackWithoutsave";
    }

    public void setCancelButtonAdd(String cancelButtonAdd) {
        this.cancelButtonAdd = cancelButtonAdd;
    }

    public String getCancelButtonAdd() {
        return cancelButtonAdd;
    }

    public void setSaveButtonAdd(String saveButtonAdd) {
        this.saveButtonAdd = saveButtonAdd;
    }

    public String getSaveButtonAdd() {
        return saveButtonAdd;
    }
}
