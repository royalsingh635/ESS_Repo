package tkrAccessToUsers.view.bean;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.Set;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ComponentObjectImpl;
import oracle.jbo.server.ViewObjectImpl;

import tkrAccessToUsers.model.Module.TkrAccessToUserAMImpl;

public class TkrAccessToUsrBean {
    
    private String modeNewRecord="S";
    private String modeDeleted="S";
    private RichSelectOneChoice userTypeidBind;
    private RichSelectOneChoice tickerIdBind;
    private RichSelectOneChoice groupidBind;
    private RichSelectOneChoice roleIdBind;
    private RichSelectOneRadio userTypeRadioGrpBind;
    private RichSelectOneRadio positionTypeRadioGrpBind;
    private RichSelectOneChoice userInSearch;
    private RichInputText tickerPosInSearch;
    private RichInputText tickerposPgBind;
    private RichSelectOneChoice userlabelPgBind;
    
    Integer count =Integer.parseInt(getBindings().getOperationBinding("on_load_page").execute().toString());
    private RichSelectOneChoice finTkrBindPgBind;
    private RichSelectOneChoice bindOrgId;
    
    


    public TkrAccessToUsrBean() {
    }
    private static int VARCHAR = Types.VARCHAR;
    public void deleteSelectedButton(ActionEvent actionEvent) {
        TkrAccessToUserAMImpl am = (TkrAccessToUserAMImpl)resolvElDC("TkrAccessToUserAMDataControl");
        ViewObjectImpl v = am.getFinSecTkrUsr1();
        RowSetIterator createRowSetIterator = v.createRowSetIterator(null);
        while(createRowSetIterator.hasNext())
        {
            Row row = createRowSetIterator.next();
            if(row.getAttribute("DelFlg")!= null)
            {
            if(row.getAttribute("DelFlg").toString().equalsIgnoreCase("Y"))
            {
                row.remove();
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


    public void selectAllButton(ActionEvent actionEvent) {
        TkrAccessToUserAMImpl am = (TkrAccessToUserAMImpl)resolvElDC("TkrAccessToUserAMDataControl");
        ViewObjectImpl v = am.getFinSecTkrUsr1();
        RowSetIterator createRowSetIterator = v.createRowSetIterator(null);
        while(createRowSetIterator.hasNext())
        {
            Row row = createRowSetIterator.next();
            String s = "Y";
            row.setAttribute("DelFlg", s);
            
            
            }
    }

    public void deselectAllButton(ActionEvent actionEvent) {
        TkrAccessToUserAMImpl am = (TkrAccessToUserAMImpl)resolvElDC("TkrAccessToUserAMDataControl");
        ViewObjectImpl v = am.getFinSecTkrUsr1();
        RowSetIterator createRowSetIterator = v.createRowSetIterator(null);
        while(createRowSetIterator.hasNext())
        {
            Row row = createRowSetIterator.next();
            String s = "N";
            row.setAttribute("DelFlg", s);
            
            
            }
    }

    public void rguValueChange(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue().toString().equalsIgnoreCase("R"))
        {
            userTypeidBind.setValue(null);
            }
        if(valueChangeEvent.getNewValue().toString().equalsIgnoreCase("U"))
        {
            roleIdBind.setValue(null);
         }
        
      AdfFacesContext.getCurrentInstance().addPartialTarget(this.roleIdBind);
      AdfFacesContext.getCurrentInstance().addPartialTarget(this.getUserTypeidBind());
        
    }

    public void zmvalueChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void generateButton(ActionEvent actionEvent) {
       
        List inValid=this.inValid();  
        
          
        if (inValid.size()==0) {
            OperationBinding op = this.getBindings().getOperationBinding("generateData");
            op.execute();

            String result = op.getResult().toString();


            if (result.equalsIgnoreCase("Y")) {

                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();

                FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.617']}").toString());
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, msg);


                operationBinding = bindings.getOperationBinding("refreshVo");
                operationBinding.execute();

                this.modeNewRecord = "S";


            } else {


                if (result.equalsIgnoreCase("E")) {

                    FacesMessage msg =
                        new FacesMessage(resolvElDCMsg("Selected user does not have any role for selected organisation").toString());
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(null, msg);
                }


            }
        }else{
            
            Iterator iter=inValid.iterator();
            String clientId;
            while(iter.hasNext()){
               clientId=(String) iter.next();
               FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mandatory","This is Required field")); 
               break;
            }
        }
      
    }
    
    
    public List inValid(){
        ArrayList valid=new ArrayList();
        
        if(this.getBindOrgId().getValue()== null){
         //   System.out.println("In Bind Org");
            valid.add( getBindOrgId().getClientId());
         }
        
       //System.out.println("User Value ="+this.getUserTypeidBind().getValue() + "Class is "+this.getUserTypeidBind().getValue().getClass().getName() );
        if((! this.getUserTypeidBind().isDisabled()) && (this.getUserTypeidBind().getValue()==null || ( this.getUserTypeidBind().getValue().getClass().getName().equalsIgnoreCase("java.lang.String") &&(((String) this.getUserTypeidBind().getValue()).trim().length()==0 )) )){
            //System.out.println("In Bind User");
            valid.add(getUserTypeidBind().getClientId());
        }
        if((! this.getRoleIdBind().isDisabled()) && (this.getRoleIdBind().getValue() ==null || (this.getRoleIdBind().getValue().getClass().getName().equalsIgnoreCase("java.lang.String")  && ((String)this.getRoleIdBind().getValue()).trim().length()==0 ) )){
           // System.out.println("In Bind Role");           
            valid.add(getRoleIdBind().getClientId());
        }
        
        return valid;
    }
    
    public Object resolvElDCMsg(String data) {
    FacesContext fc = FacesContext.getCurrentInstance();
    Application app = fc.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = fc.getELContext();
    ValueExpression valueExp = elFactory.createValueExpression(elContext, data,
    Object.class);
    return valueExp.getValue(elContext);
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
    
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        TkrAccessToUserAMImpl am = (TkrAccessToUserAMImpl)resolvElDC("TkrAccessToUserAMDataControl");
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

    public void setUserTypeidBind(RichSelectOneChoice userTypeidBind) {
        this.userTypeidBind = userTypeidBind;
    }

    public RichSelectOneChoice getUserTypeidBind() {
        return userTypeidBind;
    }

    public void setTickerIdBind(RichSelectOneChoice tickerIdBind) {
        this.tickerIdBind = tickerIdBind;
    }

    public RichSelectOneChoice getTickerIdBind() {
        return tickerIdBind;
    }

    public void setGroupidBind(RichSelectOneChoice groupidBind) {
        this.groupidBind = groupidBind;
    }

    public RichSelectOneChoice getGroupidBind() {
        return groupidBind;
    }

    public void setRoleIdBind(RichSelectOneChoice roleIdBind) {
        this.roleIdBind = roleIdBind;
    }

    public RichSelectOneChoice getRoleIdBind() {
        return roleIdBind;
    }

    public BindingContainer getContainer()
    {
     return BindingContext.getCurrent().getCurrentBindingsEntry();    
    }
    
    public void addButton(ActionEvent actionEvent) {
        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Createwithparameters");
        operationBinding.execute();
        
        this.setModeNewRecord("A");
        String userType = "U";
        String pos = "Z";
        userTypeRadioGrpBind.setValue(userType);
        positionTypeRadioGrpBind.setValue(pos);
        
    }
    public BindingContainer getBindings() {
           return BindingContext.getCurrent().getCurrentBindingsEntry();
       }

    public void setUserTypeRadioGrpBind(RichSelectOneRadio userTypeRadioGrpBind) {
        this.userTypeRadioGrpBind = userTypeRadioGrpBind;
    }

    public RichSelectOneRadio getUserTypeRadioGrpBind() {
        return userTypeRadioGrpBind;
    }

    public void setPositionTypeRadioGrpBind(RichSelectOneRadio positionTypeRadioGrpBind) {
        this.positionTypeRadioGrpBind = positionTypeRadioGrpBind;
    }

    public RichSelectOneRadio getPositionTypeRadioGrpBind() {
        return positionTypeRadioGrpBind;
    }

    public void saveButton(ActionEvent actionEvent) {
        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        
        FacesMessage msg = new FacesMessage(resolvElDCMsg("#{bundle['MSG.91']}").toString());
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.addMessage(null, msg);
        
        
    }

   
    public void setUserInSearch(RichSelectOneChoice userInSearch) {
        this.userInSearch = userInSearch;
    }

    public RichSelectOneChoice getUserInSearch() {
        return userInSearch;
    }

    public void setTickerPosInSearch(RichInputText tickerPosInSearch) {
        this.tickerPosInSearch = tickerPosInSearch;
    }

    public RichInputText getTickerPosInSearch() {
        return tickerPosInSearch;
    }


    public void deleteRow(ActionEvent actionEvent) {

        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("Delete");
        operationBinding.execute();
        
        this.setModeDeleted("D");
        
    }

    public void RollbackButton(ActionEvent actionEvent) {

        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("Rollback");
        operationBinding.execute();
  
        this.setModeDeleted("S");
    }

    public void ResetButton(ActionEvent actionEvent) {
    
        this.getBindings().getOperationBinding("resetTkrTable").execute();
    }

    public void setTickerposPgBind(RichInputText tickerposPgBind) {
        this.tickerposPgBind = tickerposPgBind;
    }

    public RichInputText getTickerposPgBind() {
        return tickerposPgBind;
    }

    public void setUserlabelPgBind(RichSelectOneChoice userlabelPgBind) {
        this.userlabelPgBind = userlabelPgBind;
    }

    public RichSelectOneChoice getUserlabelPgBind() {
        return userlabelPgBind;
    }
    public void searchButton(ActionEvent actionEvent) {
         
         this.getBindings().getOperationBinding("searchTkrTable").execute();

    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void tableCommitButton(ActionEvent actionEvent) {

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("Commit");
        operationBinding.execute();
        

        
        bindings.getOperationBinding("deleteRows").execute();
        bindings.getOperationBinding("Commit").execute();
        
        operationBinding =bindings.getOperationBinding("refreshVo");
        operationBinding.execute();
   
        this.setModeDeleted("S");
    }

    public void setFinTkrBindPgBind(RichSelectOneChoice finTkrBindPgBind) {
        this.finTkrBindPgBind = finTkrBindPgBind;
    }

    public RichSelectOneChoice getFinTkrBindPgBind() {
        return finTkrBindPgBind;
    }

    public void setBindOrgId(RichSelectOneChoice bindOrgId) {
        this.bindOrgId = bindOrgId;
    }

    public RichSelectOneChoice getBindOrgId() {
        return bindOrgId;
    }


    public void setModeNewRecord(String modeNewRecord) {
        this.modeNewRecord = modeNewRecord;
    }

    public String getModeNewRecord() {
        return modeNewRecord;
    }

    public void setModeDeleted(String modeDeleted) {
        this.modeDeleted = modeDeleted;
    }

    public String getModeDeleted() {
        return modeDeleted;
    }

    public void cancelNewRecord(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        
        this.setModeNewRecord("S");
        
    }
}
