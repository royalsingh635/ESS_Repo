package chkbook.view.bean;

import chkbook.model.module.ChkBookAMImpl;
import chkbook.view.bean.CheckBookBean;
import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;


import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.JboException;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import oracle.jbo.server.DBTransaction;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;



public class ChkBookBean {
    private RichPopup pop;
    private RichPopup deletepop;
    private RichPopup editpop;
    private RichTable chqBookTable;
    private static String flag=null;
    private RichInputText bankStartSlNo;
    private RichInputText bookEndSlNo;
    private RichInputText bankNoofChk;
    
    
    
    private RichInputText editStartSl;
    private RichInputText editNoOfChq;
    private RichInputText editEndSlNo;
   

    public ChkBookBean() {
    }
    public String getdisplayValue(Integer CHQ_NO,String CHQ_NO_EXT ,String CHQ_NO_EXT_CHAR ,String CHQ_NO_PAD,Integer LPAD_LEN ,String LPAD_CHAR,Integer RPAD_LEN,String RPAD_CHAR) {
             return (String)callStoredFunction(STRING, "FIN.PKG_FIN.FN_GET_CHQ_NO_DISP(?,?,?,?,?,?,?,?)",
                                                      new Object[]{CHQ_NO,CHQ_NO_EXT,CHQ_NO_EXT_CHAR,CHQ_NO_PAD,LPAD_LEN,LPAD_CHAR,RPAD_LEN,RPAD_CHAR });
       }
    
    public String getLastChqBookSlNo(Integer slocId,Integer P_BNK_ID,Integer P_CHQ_BUK_ID ) {
             return (String)callStoredFunction(STRING, "FIN.PKG_FIN. FN_GET_LAST_CHQBUK_SLNO_CHQ(?,?,?)",
                                                      new Object[]{slocId,P_BNK_ID,P_CHQ_BUK_ID});
       }
    public void setBankStartSlNo(RichInputText bankStartSlNo) {
        this.bankStartSlNo = bankStartSlNo;
    }

    public RichInputText getBankStartSlNo() {
        return bankStartSlNo;
    }

    public void setBookEndSlNo(RichInputText bookEndSlNo) {
        this.bookEndSlNo = bookEndSlNo;
    }

    public RichInputText getBookEndSlNo() {
        return bookEndSlNo;
    }


    public void setBankNoofChk(RichInputText bankNoofChk) {
        this.bankNoofChk = bankNoofChk;
    }

    public RichInputText getBankNoofChk() {
        return bankNoofChk;
    }

    public String getEndValueAction() {
        //String bankIdval= bankId.getValue().toString();
        String a= bankStartSlNo.getValue().toString();
         String b=bankNoofChk.getValue().toString();
        if (a!=null && b!=null){
          
           Integer c=Integer.parseInt(a);
           Integer d=Integer.parseInt(b);
           Integer e=(c+d-1);
            System.out.println("e========="+e);
            bookEndSlNo.resetValue();
        
            bookEndSlNo.setValue(e);
         
          
           
        }
        return null;
    }

    

    
    
    public void BankNoofChqChangeListner(ValueChangeEvent valueChangeEvent) {
        if( bankStartSlNo.getValue()!=null){
            
        
        if( bankNoofChk.getValue()!=null){
            
            
            bookEndSlNo.setValue(Integer.parseInt(bankStartSlNo.getValue().toString())+Integer.parseInt(bankNoofChk.getValue().toString())-1);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bookEndSlNo); 
        }
        }
    }

    

    public void StartSerialNoChangeListner(ValueChangeEvent valueChangeEvent) {
        
        if( bankStartSlNo.getValue()!=null){
            
       
        if( bankNoofChk.getValue()!=null){
            
            
            bookEndSlNo.setValue(Integer.parseInt(bankStartSlNo.getValue().toString())+Integer.parseInt(bankNoofChk.getValue().toString())-1);
            AdfFacesContext.getCurrentInstance().addPartialTarget(bookEndSlNo); 
        }
        }
    }

    public void setEditStartSl(RichInputText editStartSl) {
        this.editStartSl = editStartSl;
    }

    public RichInputText getEditStartSl() {
        return editStartSl;
    }

    public void setEditNoOfChq(RichInputText editNoOfChq) {
        this.editNoOfChq = editNoOfChq;
    }

    public RichInputText getEditNoOfChq() {
        return editNoOfChq;
    }

    public void setEditEndSlNo(RichInputText editEndSlNo) {
        this.editEndSlNo = editEndSlNo;
    }

    public RichInputText getEditEndSlNo() {
        return editEndSlNo;
    }

    public void editChqStartSl(ValueChangeEvent valueChangeEvent) {
        if(editStartSl.getValue()!=null){
            if(editNoOfChq.getValue()!=null){
                editEndSlNo.setValue(Integer.parseInt(editStartSl.getValue().toString())+Integer.parseInt(editNoOfChq.getValue().toString())-1);
                AdfFacesContext.getCurrentInstance().addPartialTarget(editEndSlNo); 
                
            }
        }
    }

    public void editNoOfChq(ValueChangeEvent valueChangeEvent) {
        if(editStartSl.getValue()!=null){
            if(editNoOfChq.getValue()!=null){
                editEndSlNo.setValue(Integer.parseInt(editStartSl.getValue().toString())+Integer.parseInt(editNoOfChq.getValue().toString())-1);
                AdfFacesContext.getCurrentInstance().addPartialTarget(editEndSlNo); 
            }
        }
    }

    public void setChqBookTable(RichTable chqBookTable) {
        this.chqBookTable = chqBookTable;
    }

    public RichTable getChqBookTable() {
        return chqBookTable;
    }

    public void ChqBookStartSlValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Integer a=Integer.parseInt(object.toString());
        String error="Start Serial Number should be positive whole number.";
        if(a<=0){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
        }
    }

    public void NoOfChq(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Integer a=Integer.parseInt(object.toString());
        String error=" Number of cheques should be positive whole number and greater than 0.";
        if(a<1){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
        }
    }
    public void create(ActionEvent actionEvent) {

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        CheckBookBean a=new CheckBookBean();
       this.setFlag("A");
        showPopup(pop, true);
    }
    
    public BindingContainer getBindings() {
            return BindingContext.getCurrent().getCurrentBindingsEntry();
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
    public void setPop(RichPopup pop) {
        this.pop = pop;
    }

    public RichPopup getPop() {
        return pop;
    }

    public void cancelListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
              OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
              OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");

              operationBinding.execute();
              operationBinding1.execute();
        this.setFlag("C");
        AdfFacesContext.getCurrentInstance().addPartialTarget(chqBookTable); 
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
    public String generateChqLine(Integer slocId,Integer P_BNK_ID,Integer P_CHQ_BUK_ID ) {
             return (String)callStoredFunction(STRING, "FIN.PKG_FIN. FN_GEN_CHQ_LINES(?,?,?)",
                                                      new Object[]{slocId,P_BNK_ID,P_CHQ_BUK_ID});
    }
    public String UpdateChqLine(Integer slocId,Integer P_BNK_ID,Integer P_CHQ_BUK_ID ) {
             return (String)callStoredFunction(STRING, "FIN.PKG_FIN. FN_UPD_CHQ_LINES(?,?,?)",
                                                      new Object[]{slocId,P_BNK_ID,P_CHQ_BUK_ID});
    }
           public String validateCheckBook(Integer P_SLOC_ID,Integer P_BNK_ID,Integer P_CHQ_BUK_ID,Integer P_NEW_START_SERIAL_NO ,Integer P_NEW_END_SERIAL_NO) {
                    return (String)callStoredFunction(STRING, "FIN.PKG_FIN.FN_VALIDATE_CHQ_BUK(?,?,?,?,?)",
                                                             new Object[]{P_SLOC_ID,P_BNK_ID,P_CHQ_BUK_ID, P_NEW_START_SERIAL_NO, P_NEW_END_SERIAL_NO});
           }
           
           
    public String deleteCheckBookAll(Integer P_SLOC_ID,Integer P_BNK_ID,Integer P_CHQ_BUK_ID ) {
             return (String)callStoredFunction(STRING, "FIN.PKG_FIN.FN_DEL_CHQ_BUK_ALL(?,?,?)",
                                                      new Object[]{P_SLOC_ID,P_BNK_ID,P_CHQ_BUK_ID});
    }
    
    private static int INTEGER = Types.NUMERIC;
        private static int DATE = Types.DATE;
        private static int STRING = Types.VARCHAR;
    protected Object callStoredFunction(int sqlReturnType, String stmt,
                                        Object[] bindVars) {
      CallableStatement st = null;
      try {
        // 1. Create a JDBC CallabledStatement  
          
        ChkBookAMImpl am = (ChkBookAMImpl)resolvElDC("ChkBookAMDataControl");
        st = am.getDBTransaction().createCallableStatement(
               "begin ? := "+stmt+";end;",0);
        // 2. Register the first bind variable for the return value
        st.registerOutParameter(1, sqlReturnType);
        if (bindVars != null) {
          // 3. Loop over values for the bind variables passed in, if any
          for (int z = 0; z < bindVars.length; z++) {
            // 4. Set the value of user-supplied bind vars in the stmt
            st.setObject(z + 2, bindVars[z]);
          }
        }
        // 5. Set the value of user-supplied bind vars in the stmt
        st.executeUpdate();
        // 6. Return the value of the first bind variable
        return st.getObject(1);
      }
      catch (SQLException e) {
        throw new JboException(e);
      }
      finally {
        if (st != null) {
          try {
            // 7. Close the statement
            st.close();
          }
          catch (SQLException e){}
        }
      }
    }

    public void DialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            
            
            
            
            
           ChkBookAMImpl am = (ChkBookAMImpl)resolvElDC("ChkBookAMDataControl");
            ViewObject v1=am.getFinChqBk1();
            ViewObject v2=am.getFinChqBkSlno1();
            am.getDBTransaction().commit();
            Row r1=v1.getCurrentRow();
            Integer slocId=  Integer.parseInt(r1.getAttribute("ChqBkSlocId").toString());
            Integer BankSlId=  Integer.parseInt(r1.getAttribute("ChqBkSlId").toString());
            Integer BankId=  Integer.parseInt(r1.getAttribute("ChqBkBnkId").toString());
            
            System.out.println("slocId====="+slocId);
            System.out.println("BankSlId====="+BankSlId);
            System.out.println("BankId====="+BankId);
            
           String a= generateChqLine(slocId,BankId,BankSlId);
          
           System.out.println("a======="+a);
           am.getDBTransaction().commit();
           v2.executeQuery();
           v1.executeQuery();
            this.setFlag("S");
            
            
            if(am.getDBTransaction().isDirty()==false){
                                        
                                        FacesMessage message = new FacesMessage("Record Saved Successfully!");
                                        message.setSeverity(FacesMessage.SEVERITY_INFO);

                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message);
                                   

            AdfFacesContext.getCurrentInstance().addPartialTarget(chqBookTable); 
            } 
            else if (dialogEvent.getOutcome().name().equals("cancel")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");
            OperationBinding operationBinding2 = bindings.getOperationBinding("Execute1");

            operationBinding2.execute();
            operationBinding.execute();
            operationBinding1.execute();
            this.setFlag("C");
            AdfFacesContext.getCurrentInstance().addPartialTarget(chqBookTable);   
        }
        
    }
    }
    public void edit(ActionEvent actionEvent) {
        showPopup(editpop, true);
        this.setFlag("E");
    }

    public void delete(ActionEvent actionEvent) {
        ChkBookAMImpl am = (ChkBookAMImpl)resolvElDC("ChkBookAMDataControl");
        
        ViewObject v1=am.getFinChqBk1();
        
        Row r1=v1.getCurrentRow();
        Integer slocId=  Integer.parseInt(r1.getAttribute("ChqBkSlocId").toString());
        Integer BankSlId=  Integer.parseInt(r1.getAttribute("ChqBkSlId").toString());
        Integer BankId=  Integer.parseInt(r1.getAttribute("ChqBkBnkId").toString());
        System.out.println("slocId====="+slocId);
        System.out.println("BankSlId====="+BankSlId);
        System.out.println("BankId====="+BankId);
        
        String a= deleteCheckBookAll(slocId,BankId,BankSlId);
                        
        System.out.println("a======="+a);
        if(a.equalsIgnoreCase("Y")){
        showPopup(deletepop, true);
        }
        else{
            FacesMessage message = new FacesMessage("Some/All Cheque Nos. Have already Been Used/Allocated for the Given Cheque Book, Cannot Delete.");
            message.setSeverity(FacesMessage.SEVERITY_INFO);

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        this.setFlag("D");

    }

    public void setDeletepop(RichPopup deletepop) {
        this.deletepop = deletepop;
    }

    public RichPopup getDeletepop() {
        return deletepop;
    }

    public void deleteDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
                     
                BindingContainer bindingsa = getBindings();
                
                OperationBinding operationBindings = bindingsa.getOperationBinding("Commit");
                operationBindings.execute();
                
                OperationBinding createOpBAddress = bindingsa.getOperationBinding("Execute");
                createOpBAddress.execute();
                OperationBinding operationBinding2 = bindingsa.getOperationBinding("Execute1");

               operationBinding2.execute();
                }
           else if (dialogEvent.getOutcome().name().equals("yes")) {
               BindingContainer bindingsa = getBindings();
               
               OperationBinding operationBindings = bindingsa.getOperationBinding("Rollback");
               operationBindings.execute();
               
               OperationBinding createOpBAddress = bindingsa.getOperationBinding("Execute");
               createOpBAddress.execute();
               OperationBinding operationBinding2 = bindingsa.getOperationBinding("Execute1");

               operationBinding2.execute();
           }
            AdfFacesContext.getCurrentInstance().addPartialTarget(chqBookTable); 
        }
    

    public void setEditpop(RichPopup editpop) {
        this.editpop = editpop;
    }

    public RichPopup getEditpop() {
        return editpop;
    }

    public void DialogListenerEdit(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            ChkBookAMImpl am = (ChkBookAMImpl)resolvElDC("ChkBookAMDataControl");
            int lockMode=am.getDBTransaction().getLockingMode();
            ViewObject v1=am.getFinChqBk1();
            ViewObject v2=am.getFinChqBkSlno1();
            
            AdfFacesContext.getCurrentInstance().addPartialTarget(editStartSl); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(editEndSlNo); 
            
            Row r1=v1.getCurrentRow();
            Integer slocId=  Integer.parseInt(r1.getAttribute("ChqBkSlocId").toString());
            Integer BankSlId=  Integer.parseInt(r1.getAttribute("ChqBkSlId").toString());
            Integer BankId=  Integer.parseInt(r1.getAttribute("ChqBkBnkId").toString());
            Integer StartSl=  Integer.parseInt(r1.getAttribute("ChqBkStrtSl").toString());
            Integer Endsl=  Integer.parseInt(r1.getAttribute("ChqBkEndSl").toString());
            
            
            String a=validateCheckBook(slocId,BankId,BankSlId,StartSl ,Endsl);
            am.getDBTransaction().setLockingMode(DBTransaction.LOCK_NONE);
            
         
           if(a.equals("Y"))
           {
               BindingContainer bindings = getBindings();
               OperationBinding operationBinding = bindings.getOperationBinding("Commit");
               operationBinding.execute();
             
           String Y=    UpdateChqLine(slocId,BankId,BankSlId);
           
              if(Y.equalsIgnoreCase("Y")){
               BindingContainer bindingsa = getBindings();
               OperationBinding operationBindings = bindingsa.getOperationBinding("Commit");
               operationBindings.execute();
               
                 v2.executeQuery();
                 v1.executeQuery();
                 if(operationBindings.getErrors().isEmpty()){
                     FacesMessage message = new FacesMessage("Record Updated Successfully!");
                     message.setSeverity(FacesMessage.SEVERITY_INFO);

                     FacesContext fc = FacesContext.getCurrentInstance();
                     fc.addMessage(null, message);
                 }
               this.setFlag("S");
           }
           }
           else
           {
                   BindingContainer bindings = getBindings();
                   OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                   OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");
                   OperationBinding operationBinding2 = bindings.getOperationBinding("Execute1");

                   operationBinding2.execute();
                   operationBinding.execute();
                   operationBinding1.execute();
                   FacesMessage message = new FacesMessage("Can not change.");
                   message.setSeverity(FacesMessage.SEVERITY_INFO);
                   FacesContext fc = FacesContext.getCurrentInstance();
                   fc.addMessage(null, message);
                               
               }
            am.getDBTransaction().setLockingMode(lockMode);  
           
            AdfFacesContext.getCurrentInstance().addPartialTarget(chqBookTable); 

        } else if (dialogEvent.getOutcome().name().equals("cancel"))
        {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");
            OperationBinding operationBinding2 = bindings.getOperationBinding("Execute1");

            operationBinding2.execute();
            operationBinding.execute();
            operationBinding1.execute();
            this.setFlag("C");
            AdfFacesContext.getCurrentInstance().addPartialTarget(chqBookTable); 
        }
    }

   

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }
}
