package appglblgraph.view;

import appglblgraph.model.module.AppGlblGrphAMImpl;

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


import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.JboException;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import oracle.sql.NUMBER;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class AppGlblGrphBean {
    private Integer count=-1;
    private RichTable appGlblGraphparamTabelBinding;
   // Integer rcountTable=appGlblGraphparamTabelBinding.getRowCount();
    String Mode="";
    

    public AppGlblGrphBean() {
        count=(Integer)getBindings().getOperationBinding("on_load_page").execute();
    }
    
    private RichPopup pop;
    private RichPopup pop_param;
    private RichPopup deletepop;
    private RichPopup deletepop_param;
    private static int NUMBER = Types.INTEGER;
    public void Create(ActionEvent actionEvent) {


        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        showPopup(pop, true);
    }

    public void Edit(ActionEvent actionEvent) {

        showPopup(pop, true);
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void Delete(ActionEvent actionEvent) {

        showPopup(deletepop, true);

    }


    //Metho for showing pop up on button click

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

    public void setPop(RichPopup pop) {
        this.pop = pop;
    }

    public RichPopup getPop() {
        return pop;
    }

    //Pop Up cancel listener

    public void CancelListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");

        operationBinding.execute();
        operationBinding1.execute();

    }
// for GlblGrphParam
    public void CancelListener_param(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute1");

        operationBinding.execute();
        operationBinding1.execute();

    }
    //Dialog Listener for popup dialog

    public void DialogListener(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().equals("ok")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");
            operationBinding.execute();
            operationBinding1.execute();

        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");

            operationBinding.execute();
            operationBinding1.execute();
        }

    }


    //Pop up for delete(Delete Dialog listener)

    public void DeleteDialogListener(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("yes")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Delete");
            operationBinding.execute();
            BindingContainer bindingsa = getBindings();
            OperationBinding operationBindings = bindingsa.getOperationBinding("Commit");
            operationBindings.execute();
            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
            createOpBAddress.execute();
        } else if (dialogEvent.getOutcome().name().equals("no")) {
        }
    }
    
    // Methods for calling function for MAX_PARAM_ID
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
               CallableStatement st = null;
               try {
                   AppGlblGrphAMImpl am = (AppGlblGrphAMImpl)resolvElDC("AppGlblGrphAMDataControl");
                   st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
                   st.registerOutParameter(1, sqlReturnType);
                   if (bindVars != null) {
                       for (int z = 0; z < bindVars.length; z++) {
                           st.setObject(z + 2, bindVars[z]);
                           System.out.println(bindVars[z]+"z");
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
    
    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }
    //Methods For App$Glbl$Grph$Param

    public void Create_param(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
        operationBinding.execute();
        
        AppGlblGrphAMImpl am = (AppGlblGrphAMImpl)resolvElDC("AppGlblGrphAMDataControl");
        ViewObject v = am.getAppGlblGrphParam2();
        Row row = v.getCurrentRow();
        Integer grphId = Integer.parseInt(row.getAttribute("GlblGrphId").toString());
        
        System.out.println("graph_id----------->"+grphId);
        Integer i = (Integer)(callStoredFunction(NUMBER, "APP.PKG_GRPH.FN_GET_GLBL_MAX_GRPH_PARAM_ID (?)", new Object[] { grphId}));
        System.out.println("---------->"+i);
         row.setAttribute("GlblGrphParamId", i); 
        // am = (AppGlblGrphAMImpl)resolvElDC("AppGlblGrphAMDataControl");
        
        
        showPopup(pop_param, true);
    }

    public void setPop_param(RichPopup pop_param) {
        this.pop_param = pop_param;
    }

    public RichPopup getPop_param() {
        return pop_param;
    }

    //Dialog Listener for popup dialog AppGlblGrphParam

    public void DialogListener_param(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().equals("ok")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute1");
            operationBinding.execute();
            operationBinding1.execute();

        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute1");

            operationBinding.execute();
            operationBinding1.execute();
        }

    }

    public void Edit_param(ActionEvent actionEvent) {
        showPopup(pop_param, true);
    }

    public void Delete_param(ActionEvent actionEvent) {

        showPopup(deletepop_param, true);

    }


    //Pop up for delete(Delete Dialog listener)

    public void DeleteDialogListener_param(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("yes")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Delete1");
            operationBinding.execute();
            BindingContainer bindingsa = getBindings();
            OperationBinding operationBindings = bindingsa.getOperationBinding("Commit");
            operationBindings.execute();
            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Execute1");
            createOpBAddress.execute();
        } else if (dialogEvent.getOutcome().name().equals("no")) {
        }
    }


    public void setDeletepop(RichPopup deletepop) {
        this.deletepop = deletepop;
    }

    public RichPopup getDeletepop() {
        return deletepop;
    }

    public void setDeletepop_param(RichPopup deletepop_param) {
        this.deletepop_param = deletepop_param;
    }

    public RichPopup getDeletepop_param() {
        return deletepop_param;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setAppGlblGraphparamTabelBinding(RichTable appGlblGraphparamTabelBinding) {
        this.appGlblGraphparamTabelBinding = appGlblGraphparamTabelBinding;
    }

    public RichTable getAppGlblGraphparamTabelBinding() {
        return appGlblGraphparamTabelBinding;
    }

    public void setMode(String Mode) {
        
        
        this.Mode = Mode;
    }

    public String getMode() {
       /*  System.out.println(rcountTable);
        String mode2=null;
        if(rcountTable>=1){
            mode2="W";
        
        }
        return mode2; */
        return Mode;
    }
}
