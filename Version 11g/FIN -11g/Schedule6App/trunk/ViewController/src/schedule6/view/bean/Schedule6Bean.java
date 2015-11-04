package schedule6.view.bean;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import  oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import schedule6.model.Module.Schedule6AMImpl;

public class Schedule6Bean {
    private RichInputListOfValues hdrLovBinding;
    private RichInputText hdrTextBinding;
    private RichInputText grotextBinding;
    private RichInputListOfValues grpLovBinding;
    private RichInputListOfValues accTranBind;
    private RichInputListOfValues grpTranBind;
    private RichInputText accNmTranBind;
    private RichInputText grpNmTranBind;
    private RichColumn accNmTranbinding;
    private RichColumn acctranBinding;
    private RichColumn grpNmtranBinding;
    private RichColumn grpTranBinding;
    private RichInputText headerIdBinding;

    public Schedule6Bean() {
    }
    private static int NUMBER = Types.INTEGER;
    private static String hdLov = "N";

    public void createNew(ActionEvent actionEvent) {
        
        Integer schId = Integer.parseInt(callStoredFunction(NUMBER, "get_max_schedule_id", new Object[] {}).toString());
        System.out.println(schId+"---------schId");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        Schedule6AMImpl am = (Schedule6AMImpl)resolvElDC("Schedule6AMDataControl");
        ViewObject schView = am.getFinSchedule();
        Row schrow = schView.getCurrentRow();
        schrow.setAttribute("ScheduleId", schId);
        
        
    }
    
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        Schedule6AMImpl am = (Schedule6AMImpl)resolvElDC("Schedule6AMDataControl");
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
    
    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }


    public void createLine(ActionEvent actionEvent) {
        
        Integer hdrId = Integer.parseInt(callStoredFunction(NUMBER, "fn_get_max_header_id", new Object[] {}).toString());
        System.out.println(hdrId+"---------hdrId");
        
        Integer grpId = Integer.parseInt(callStoredFunction(NUMBER, "fn_get_max_group_id", new Object[] {}).toString());
        System.out.println(grpId+"---------grpId");
        
        Integer lineId = Integer.parseInt(callStoredFunction(NUMBER, "get_max_line_id", new Object[] {}).toString());
        System.out.println(lineId+"---------lineId");
        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
        operationBinding.execute();
        
        Schedule6AMImpl am = (Schedule6AMImpl)resolvElDC("Schedule6AMDataControl");
        ViewObject schLineView = am.getFinScheduleLine();
        Row schLinerow = schLineView.getCurrentRow();
        schLinerow.setAttribute("ScheduleHeaderId", hdrId);
        schLinerow.setAttribute("ScheduleGroupId", grpId);
        schLinerow.setAttribute("ScheduleLineId", lineId);
    }

    public void setHdLov(String hdLov) {
        this.hdLov = hdLov;
    }

    public String getHdLov() {
        return hdLov;
    }

    public String makeHdLov() {
        hdrTextBinding.setVisible(false);
        hdrLovBinding.setVisible(true);
        return null;
    }

    public void setHdrLovBinding(RichInputListOfValues hdrLovBinding) {
        this.hdrLovBinding = hdrLovBinding;
    }

    public RichInputListOfValues getHdrLovBinding() {
        return hdrLovBinding;
    }

    public void setHdrTextBinding(RichInputText hdrTextBinding) {
        this.hdrTextBinding = hdrTextBinding;
    }

    public RichInputText getHdrTextBinding() {
        return hdrTextBinding;
    }

    public void makeGrpLov(ActionEvent actionEvent) {
        grotextBinding.setVisible(false);
        grpLovBinding.setVisible(true);
    }

    public void setGrotextBinding(RichInputText grotextBinding) {
        this.grotextBinding = grotextBinding;
    }

    public RichInputText getGrotextBinding() {
        return grotextBinding;
    }

    public void setGrpLovBinding(RichInputListOfValues grpLovBinding) {
        this.grpLovBinding = grpLovBinding;
    }

    public RichInputListOfValues getGrpLovBinding() {
        return grpLovBinding;
    }

    public void saveButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
    }

    public void createlineDtl(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert2");
        operationBinding.execute();
    }

    public void setAccTranBind(RichInputListOfValues accTranBind) {
        this.accTranBind = accTranBind;
    }

    public RichInputListOfValues getAccTranBind() {
        return accTranBind;
    }

    public void setGrpTranBind(RichInputListOfValues grpTranBind) {
        this.grpTranBind = grpTranBind;
    }

    public RichInputListOfValues getGrpTranBind() {
        return grpTranBind;
    }

    public void dtlLinevalueChange(ValueChangeEvent valueChangeEvent) {
     String s =   valueChangeEvent.getNewValue().toString();
     System.out.println(valueChangeEvent.getNewValue().toString()+"------------------");
     if(s.equalsIgnoreCase("A"))
     {
                  
             
            grpTranBinding.setVisible(false);
            grpNmtranBinding.setVisible(false);
            acctranBinding.setVisible(true);
            accNmTranbinding.setVisible(true);
         }
     else
     {
             grpTranBinding.setVisible(true);
             grpNmtranBinding.setVisible(true);
             acctranBinding.setVisible(false);
             accNmTranbinding.setVisible(false);
         
         }
    }

    public void setAccNmTranBind(RichInputText accNmTranBind) {
        this.accNmTranBind = accNmTranBind;
    }

    public RichInputText getAccNmTranBind() {
        return accNmTranBind;
    }

    public void setGrpNmTranBind(RichInputText grpNmTranBind) {
        this.grpNmTranBind = grpNmTranBind;
    }

    public RichInputText getGrpNmTranBind() {
        return grpNmTranBind;
    }

    public void setAccNmTranbinding(RichColumn accNmTranbinding) {
        this.accNmTranbinding = accNmTranbinding;
    }

    public RichColumn getAccNmTranbinding() {
        return accNmTranbinding;
    }

    public void setAcctranBinding(RichColumn acctranBinding) {
        this.acctranBinding = acctranBinding;
    }

    public RichColumn getAcctranBinding() {
        return acctranBinding;
    }

    public void setGrpNmtranBinding(RichColumn grpNmtranBinding) {
        this.grpNmtranBinding = grpNmtranBinding;
    }

    public RichColumn getGrpNmtranBinding() {
        return grpNmtranBinding;
    }

    public void setGrpTranBinding(RichColumn grpTranBinding) {
        this.grpTranBinding = grpTranBinding;
    }

    public RichColumn getGrpTranBinding() {
        return grpTranBinding;
    }

    public void lineTypeColumnValueChange(ValueChangeEvent valueChangeEvent) {
     System.out.println(valueChangeEvent.getNewValue().toString()+"----------------------");   
    }

    public void cancelButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
    }

    public void headerDescValueChange(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(headerIdBinding);
    }

    public void setHeaderIdBinding(RichInputText headerIdBinding) {
        this.headerIdBinding = headerIdBinding;
    }

    public RichInputText getHeaderIdBinding() {
        return headerIdBinding;
    }
}
