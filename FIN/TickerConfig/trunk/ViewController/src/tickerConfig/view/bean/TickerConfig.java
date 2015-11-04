package tickerConfig.view.bean;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.model.OperationBinding;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.server.ViewObjectImpl;

import tickerConfig.model.module.TickerConfigAMImpl;


import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import javax.faces.context.FacesContext;

import javax.faces.validator.ValidatorException;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

public class TickerConfig {
    // private String flag;
    private String mode = "V";
    private RichPopup pop;
        Integer count = Integer.parseInt(getBindings().getOperationBinding("on_load_page").execute().toString());
    private RichSelectBooleanCheckbox transSaveas;
    private RichTable tableBind;
    private RichPopup pop2;
    private RichTable tablebind2;
    private RichPopup pop3;
    private RichTable tableBind3;
    private RichPopup pop4;
    private RichTable tableBind4;
    private RichSelectOneChoice finTkrVouTypIdbind;


    public TickerConfig() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }


    public void addTickerConfigButton(ActionEvent actionEvent) {

        mode = "C";

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
    }

    public void saveButton(ActionEvent actionEvent) {

        mode = "V";

        BindingContainer bindings = getBindings();
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        // Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String ho_org_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        //Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("Commit");
        operationBinding.execute();
        String op_mode = transSaveas.getValue().toString();
        System.out.println("op_mode----" + op_mode);
        String val = null;
        if (op_mode.equalsIgnoreCase("true")) {
            val = "I";

        } else {
            val = "U";
        }


        Integer slocId = 1;
        String seesionId = "1";
        Integer tkid_tmp = 2;
        TickerConfigAMImpl am = (TickerConfigAMImpl)resolvElDC("TickerConfigAMDataControl");
        ViewObjectImpl v = am.getFinTkrTmp();
        Row row = v.getCurrentRow();
        Integer tkrId = Integer.parseInt(row.getAttribute("FinTkrId").toString());
        System.out.println(tkrId + "-----------------------------");
        
        
        System.out.println("op_mode---" + op_mode + "slocId--" + slocId + "seesionId--" + seesionId + "tkid_tmp--" +
                           tkid_tmp + "tkrId----" + tkrId + "Cldid" + cld_id + "ho_org_id" + ho_org_id);
        String abc =
            (String)callStoredFunction(Types.VARCHAR, "FN_TKR_POPULATE_FRM_TMP(?,?,?,?,?,?,?)", new Object[] { cld_id,
                                                                                                               ho_org_id,
                                                                                                               slocId,
                                                                                                               tkrId,
                                                                                                               tkid_tmp,
                                                                                                               val,
                                                                                                               seesionId });
        System.out.println("op_mode---" + op_mode + "slocId--" + slocId + "seesionId--" + seesionId + "tkid_tmp--" +
                           tkid_tmp + "tkrId----" + tkrId + "Cldid" + cld_id + "ho_org_id" + ho_org_id);
        System.out.println("value after calling the function =" + abc);

    }

    public void cancelButton(ActionEvent actionEvent) {

        mode = "V";

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("Rollback");
        operationBinding.execute();
    }


    public void dailogok(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("Commit");
            operationBinding.execute();
        }


    }

    public void finTkrCreateButton(ActionEvent actionEvent) {

        mode = "C";

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("CreateInsert1");

        operationBinding.execute();
        showPopup(pop, true);


    }

    public void finTkrDeleteButton(ActionEvent actionEvent) {

        mode = "D";

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("Delete");
        operationBinding.execute();
    }

    public void coaCreateButton(ActionEvent actionEvent) {

        mode = "C";

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("CreateInsert2");
        operationBinding.execute();
        
        showPopup(pop2, true);

    }

    public void coaDeleteButton(ActionEvent actionEvent) {

        mode = "D";

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("Delete1");
        operationBinding.execute();
    }

    public void voucherInsertButton(ActionEvent actionEvent) {

        mode = "C";

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("CreateInsert3");
        operationBinding.execute();
        showPopup(pop3, true);
    }

    public void voucherDeleteButton(ActionEvent actionEvent) {

        mode = "D";

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("Delete2");
        operationBinding.execute();
    }

    public void TkrAddButton(ActionEvent actionEvent) {

        mode = "C";

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("CreateInsert4");
        operationBinding.execute();
        showPopup(pop4, true);
    }

    public void tkrDeleteButton(ActionEvent actionEvent) {

        mode = "D";

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("Delete3");
        operationBinding.execute();
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
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
        TickerConfigAMImpl am = (TickerConfigAMImpl)resolvElDC("TickerConfigAMDataControl");
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


    public void editConfig(ActionEvent actionEvent) {

        mode = "C";


    }

    public void setTransSaveas(RichSelectBooleanCheckbox transSaveas) {

        this.transSaveas = transSaveas;
    }

    public RichSelectBooleanCheckbox getTransSaveas() {
        return transSaveas;
    }


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




        

    public void FinTkrNaTypValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String flag = "Y";
        if (object != null) {
            System.out.println("object value is " + object.toString());
            TickerConfigAMImpl am = (TickerConfigAMImpl)resolvElDC("TickerConfigAMDataControl");

            RowSetIterator rsi = am.getFinTkrNaTypTmp().createRowSetIterator(null);
            System.out.println("curr is:" + am.getFinTkrNaTypTmp().getCurrentRow());
            while (rsi.hasNext()) {
                Row next = rsi.next();
                System.out.println("next :" + next);
                if (next.getAttribute("FinTkrNaTyp") != null && next != am.getFinTkrNaTypTmp().getCurrentRow()) {
                    // System.out.println("next.getAttribute(\"WhIdSrc\") "+next.getAttribute("WhIdSrc")+" Bean value is "+whidFromBean);
                    if (next.getAttribute("FinTkrNaTyp").equals(Integer.parseInt(object.toString()))) {
                        System.out.println("Inside loop");
                       String msg="duplicate record";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,null));
                                                                                                                           
                    }
                }
            }


        }
    }

    public void setPop(RichPopup pop) {
        this.pop = pop;
    }

    public RichPopup getPop() {
        return pop;
    }

    public void PopupCancelListener(PopupCanceledEvent popupCanceledEvent) {
        mode = "V";
System.out.println("inside cancel listerner");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("Rollback");
        operationBinding.execute(); 
        
            AdfFacesContext.getCurrentInstance().addPartialTarget(tableBind);
        
        }

    public void setTableBind(RichTable tableBind) {
        this.tableBind = tableBind;
    }

    public RichTable getTableBind() {
        return tableBind;
    }

    public void FinTkrCoaValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
    
        if (object != null) {
            System.out.println("object value is " + object.toString());
            TickerConfigAMImpl am = (TickerConfigAMImpl)resolvElDC("TickerConfigAMDataControl");

            RowSetIterator rsi = am.getFinTkrCoaTmp().createRowSetIterator(null);
            System.out.println("curr is:" + am.getFinTkrCoaTmp().getCurrentRow());
            while (rsi.hasNext()) {
                Row next = rsi.next();
                System.out.println("next :" + next);
                if (next.getAttribute("FinTkrCoaId") != null && next != am.getFinTkrCoaTmp().getCurrentRow()) {
                    // System.out.println("next.getAttribute(\"WhIdSrc\") "+next.getAttribute("WhIdSrc")+" Bean value is "+whidFromBean);
                    if (next.getAttribute("FinTkrCoaId").equals(Integer.parseInt(object.toString()))) {
                        System.out.println("Inside loop");
                       String msg="duplicate record";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,null));
                                                                                                                           
                    }
                }
            }
    
    
    
    
    }
}

    public void setPop2(RichPopup pop2) {
        this.pop2 = pop2;
    }

    public RichPopup getPop2() {
        return pop2;
    }

    public void PopupcancelListener2(PopupCanceledEvent popupCanceledEvent) {
        mode = "V";
        System.out.println("inside cancel listerner");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("Rollback");
        operationBinding.execute(); 
        
            AdfFacesContext.getCurrentInstance().addPartialTarget(tablebind2);
    }

    public void setTablebind2(RichTable tablebind2) {
        this.tablebind2 = tablebind2;
    }

    public RichTable getTablebind2() {
        return tablebind2;
    }

    public void ValidatorFinTkrVouTyp(FacesContext facesContext, UIComponent uIComponent, Object object) {
     
     
        if (object != null) {
            System.out.println("object value is " + object.toString());
            TickerConfigAMImpl am = (TickerConfigAMImpl)resolvElDC("TickerConfigAMDataControl");

            RowSetIterator rsi = am.getFinTkrVouTypTmp().createRowSetIterator(null);
            System.out.println("curr is:" + am.getFinTkrVouTypTmp().getCurrentRow());
            while (rsi.hasNext()) {
                Row next = rsi.next();
                System.out.println("next :" + next);
                if (next.getAttribute("FinTkrVouTypId") != null && next != am.getFinTkrVouTypTmp().getCurrentRow()) {
                    // System.out.println("next.getAttribute(\"WhIdSrc\") "+next.getAttribute("WhIdSrc")+" Bean value is "+whidFromBean);
                    if (next.getAttribute("FinTkrVouTypId").equals(Integer.parseInt(object.toString()))) {
                        System.out.println("Inside loop");
                       String msg="duplicate record";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,null));
                                                                                                                           
                    }
                }
            }
                      
        
        }
          
         
     
       
     
     
     
     
     
     
     
    }

    public void setPop3(RichPopup pop3) {
        this.pop3 = pop3;
    }

    public RichPopup getPop3() {
        return pop3;
    }

    public void PopupCancelListener3(PopupCanceledEvent popupCanceledEvent) {
        mode = "V";
        System.out.println("inside cancel listerner");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("Rollback");
        operationBinding.execute(); 
        
            AdfFacesContext.getCurrentInstance().addPartialTarget(tableBind3);
        
    }

    public void setTableBind3(RichTable tableBind3) {
        this.tableBind3 = tableBind3;
    }

    public RichTable getTableBind3() {
        return tableBind3;
    }

    public void ValidatorFinTkrContainsCoaId(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            System.out.println("object value is " + object.toString());
            TickerConfigAMImpl am = (TickerConfigAMImpl)resolvElDC("TickerConfigAMDataControl");

            RowSetIterator rsi = am.getFinTkrContainsCoaTmp().createRowSetIterator(null);
            System.out.println("curr is:" + am.getFinTkrContainsCoaTmp().getCurrentRow());
            while (rsi.hasNext()) {
                Row next = rsi.next();
                System.out.println("next :" + next);
                if (next.getAttribute("FinTkrContainsCoaId") != null && next != am.getFinTkrContainsCoaTmp().getCurrentRow()) {
                    // System.out.println("next.getAttribute(\"WhIdSrc\") "+next.getAttribute("WhIdSrc")+" Bean value is "+whidFromBean);
                    if (next.getAttribute("FinTkrContainsCoaId").equals(Integer.parseInt(object.toString()))) {
                        System.out.println("Inside loop");
                       String msg="duplicate record";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,null));
                                                                                                                           
                    }
                }
            }
    }
    }
    public void setPop4(RichPopup pop4) {
        this.pop4 = pop4;
    }

    public RichPopup getPop4() {
        return pop4;
    }

    public void PopupCancelListener4(PopupCanceledEvent popupCanceledEvent) {
        mode = "V";
        System.out.println("inside cancel listerner");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("Rollback");
        operationBinding.execute(); 
        
            AdfFacesContext.getCurrentInstance().addPartialTarget(tableBind4);
    }

    public void setTableBind4(RichTable tableBind4) {
        this.tableBind4 = tableBind4;
    }

    public RichTable getTableBind4() {
        return tableBind4;
    }

    public void dialogVoutypId(DialogEvent dialogEvent) {
        
        if (dialogEvent.getOutcome().name().equals("ok") && finTkrVouTypIdbind.getValue()!=null) {
            System.out.println("dialogVoutypId ");
            
            TickerConfigAMImpl am = (TickerConfigAMImpl)resolvElDC("TickerConfigAMDataControl");
            
            //            BindingContainer bindings = getBindings();
//            
//            OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("Commit");
//            operationBinding.execute();
       //     System.out.println("get errors "+operationBinding.getErrors());
            AdfFacesContext.getCurrentInstance().addPartialTarget(tableBind3);
       }
       
    }

    public void dialogContainCoaId(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().equals("ok")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = (OperationBinding)bindings.getOperationBinding("Commit");
            operationBinding.execute();
        }
        
        
    }

    public void setFinTkrVouTypIdbind(RichSelectOneChoice finTkrVouTypIdbind) {
        this.finTkrVouTypIdbind = finTkrVouTypIdbind;
    }

    public RichSelectOneChoice getFinTkrVouTypIdbind() {
        return finTkrVouTypIdbind;
    }

    public void TkrNameValidator(FacesContext facesContext, UIComponent uIComponent, Object obj) {
        if (obj != null) {
            System.out.println("object value is " + obj.toString());
            TickerConfigAMImpl am = (TickerConfigAMImpl)resolvElDC("TickerConfigAMDataControl");

            RowSetIterator rsi = am.getFinTkrTmp().createRowSetIterator(null);
            System.out.println("curr is:" + am.getFinTkrTmp().getCurrentRow());
            while (rsi.hasNext()) {
                Row next = rsi.next();
                System.out.println("next :" + next);
                if (next.getAttribute("FinTkrNm") != null  && next != am.getFinTkrTmp().getCurrentRow()) {
                    
                    if (next.getAttribute("FinTkrNm").equals((obj.toString()))) {
                        System.out.println("Inside loop");
                       String msg="duplicate record";
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,null));
                                                                                                                           
                    }
                }
            }
        }
       

    }

    public void Allowmode(FacesContext facesContext, UIComponent uIComponent, Object object) {
       
    }
}

