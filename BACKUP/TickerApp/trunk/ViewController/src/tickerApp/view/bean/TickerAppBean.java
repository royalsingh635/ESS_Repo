package tickerApp.view.bean;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.adf.model.OperationBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;

import oracle.jbo.JboException;

import oracle.jbo.Row;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import tickerApp.model.Module.TickerAppAMImpl;



public class TickerAppBean {
    
    Integer count =Integer.parseInt(getBindings().getOperationBinding("on_load_page").execute().toString());
    private RichInputText tickerNameBinding;
    private RichPanelLabelAndMessage tickerIdBinding;
    private RichPopup popup;
    private RichSelectOneChoice finTkrGlblIdBind;
     private String returnval;
    private RichTable tableBind;

    public TickerAppBean() {
    }
    
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    private static int VARCHAR = Types.VARCHAR;
    
    
    
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


    
    
    public String resolvEl(String data){
    FacesContext fc = FacesContext.getCurrentInstance();
    Application app = fc.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = fc.getELContext();
    ValueExpression valueExp = elFactory.createValueExpression(elContext, data,
    Object.class);
    String Message=valueExp.getValue(elContext).toString();
    return Message;
    }
    public String configureButton() {
        String cld_id =  resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString(); 
       // Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString()); 
        String org_id =  resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
      Integer slocId = 1;
      String seesionId = "1";
      
        TickerAppAMImpl am = (TickerAppAMImpl)resolvElDC("TickerAppAMDataControl");
        ViewObjectImpl v = am.getFinTkr1();
      Row row =  v.getCurrentRow();
      Integer tkrId = Integer.parseInt(row.getAttribute("FinTkrId").toString());
      System.out.println(tkrId+"-----------------------------");
           String abc = (String)callStoredFunction(VARCHAR,"FN_TKR_POPULATE_TO_TMP(?,?,?,?,?)", new Object[] { cld_id,slocId,org_id,tkrId,seesionId}); 
        
        return "config";
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

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
           CallableStatement st = null;
           TickerAppAMImpl am = (TickerAppAMImpl)resolvElDC("TickerAppAMDataControl");
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


    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setTickerNameBinding(RichInputText tickerNameBinding) {
        this.tickerNameBinding = tickerNameBinding;
    }

    public RichInputText getTickerNameBinding() {
        return tickerNameBinding;
    }

    public void setTickerIdBinding(RichPanelLabelAndMessage tickerIdBinding) {
        this.tickerIdBinding = tickerIdBinding;
    }

    public RichPanelLabelAndMessage getTickerIdBinding() {
        return tickerIdBinding;
    }




    public void glbltkavail(){
        String cld_id =  resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString(); 

        returnval = (String)callStoredFunction(VARCHAR,"fn_is_glbl_tkr_avlbl(?)", new Object[] {cld_id}); 
    }
    
    public void addTkrtoTable(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
                    OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("CreateInsert");
                    operationBinding.execute();
                     showPopup(popup, true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tableBind);

    }

    public void setPopup(RichPopup popup) {
        this.popup = popup;
    }

    public RichPopup getPopup() {
        return popup;
    }

    public void dialogok(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {



            String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            // Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
            String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
            Integer slocId = 1;
            Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
            Integer glbl_tkrId =Integer.parseInt( finTkrGlblIdBind.getValue().toString());
            if(glbl_tkrId!=null){
            String add =
                (String)callStoredFunction(VARCHAR, "fn_add_tkr_to_app(?,?,?,?,?)", new Object[] { cld_id, slocId,
                                                                                                   org_id, glbl_tkrId,
                                                                                                   usr_id });

            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("Commit");
            operationBinding.execute();

        TickerAppAMImpl am = (TickerAppAMImpl)resolvElDC("TickerAppAMDataControl");
        ViewObjectImpl v = am.getFinTkr1();
        v.executeQuery();

        AdfFacesContext.getCurrentInstance().addPartialTarget(tableBind);

    }
}
    }
    
    
    public void setFinTkrGlblIdBind(RichSelectOneChoice finTkrGlblIdBind) {
        this.finTkrGlblIdBind = finTkrGlblIdBind;
    }

    public RichSelectOneChoice getFinTkrGlblIdBind() {
        return finTkrGlblIdBind;
    }

    public void setReturnval(String returnval) {
        this.returnval = returnval;
    }

    public String getReturnval() {
        return returnval;
    }

    public void setTableBind(RichTable tableBind) {
        this.tableBind = tableBind;
    }

    public RichTable getTableBind() {
        return tableBind;
    }
}
