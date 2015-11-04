package appUsrGroup.view;

import appUsrGroup.model.module.AppSecUsrGrpAMImpl;

import appUsrGroup.model.views.AppSecUsrGrpVORowImpl;



import java.util.List;
import java.util.Map;
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
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.output.RichMessage;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import oracle.jbo.ApplicationModule;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.server.DBTransaction;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class UsrGrpBean {
    private RichPopup addpop;
    private RichPopup editpop;
    private RichPopup deletepop;
    public  static String flag=null;
    private RichTable grpTable;
    private static Boolean refreshVar;
    private RichPopup popupmsg;
    private RichMessage afmessage;
    private String fmsg;
    private static boolean changed=false;
    private static boolean changedEmail=false;
    
    public UsrGrpBean() {
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
    
    
    
    public String resolvEl(String data){
          FacesContext fc = FacesContext.getCurrentInstance();
          Application app = fc.getApplication();
          ExpressionFactory elFactory = app.getExpressionFactory();
          ELContext elContext = fc.getELContext();
          ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
          String Message=valueExp.getValue(elContext).toString();
          return Message;
        }
    public BindingContainer getBindings() {
           return BindingContext.getCurrent().getCurrentBindingsEntry();
           
       }
    
    public void addUser(ActionEvent actionEvent) {
                BindingContainer binding = getBindings();
                OperationBinding createOpBAddress = binding.getOperationBinding("CreateInsert");
                createOpBAddress.execute();
                flag="A";
                showPopup(addpop, true);
    }

    public void editUser(ActionEvent actionEvent) {
        flag="E";
        showPopup(editpop, true);
    }

    public void deleteUser(ActionEvent actionEvent) {
        /* BindingContainer bindings = getBindings();
         OperationBinding OpBAddress = bindings.getOperationBinding("Delete");
         OpBAddress.execute();  */
         
         
         
              showPopup(deletepop, true);
    }

    public void addDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
                  BindingContainer binding1 = getBindings();
                  OperationBinding createOpBAddress1 = binding1.getOperationBinding("Commit");
                  createOpBAddress1.execute();
                  AppSecUsrGrpAMImpl am=(AppSecUsrGrpAMImpl)resolvElDC("AppSecUsrGrpAMDataControl");
                  if(createOpBAddress1.getErrors().isEmpty()){
                      
                         BindingContainer binding2 = getBindings();
                         OperationBinding createOpBAddress2 = binding2.getOperationBinding("Execute");
                         createOpBAddress2.execute();
                      am.getDBTransaction().commit();
                      FacesContext fc=FacesContext.getCurrentInstance();
                      FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved" ,"Record Saved Successfully.");
                      fc.addMessage(null,msg);  
                  }
                 // System.out.println("saved");
                 AdfFacesContext.getCurrentInstance().addPartialTarget(grpTable); 
              } else if (dialogEvent.getOutcome().name().equals("cancel")) {

                  BindingContainer binding = getBindings();
                  OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
                  createOpBAddress.execute();

                   BindingContainer bindings = getBindings();
                  OperationBinding OpBAddress = bindings.getOperationBinding("Execute");
                  OpBAddress.execute(); 

                  AdfFacesContext.getCurrentInstance().addPartialTarget(grpTable); 
              }
    }
    
    public Boolean refreshCondition(){
        refreshVar=getAm().getDBTransaction().isDirty();
        return refreshVar;
    }
            
    private AppSecUsrGrpAMImpl getAm(){
       FacesContext fc = FacesContext.getCurrentInstance();
       Application app = fc.getApplication();
       ExpressionFactory elFactory = app.getExpressionFactory();
       ELContext elContext = fc.getELContext();
       ValueExpression valueExp =
       elFactory.createValueExpression(elContext, "#{data.AppSecUsrGrpAMDataControl.dataProvider}",
                                           Object.class);
       return   (AppSecUsrGrpAMImpl)valueExp.getValue(elContext);
    }

    
            
    public void addCancelListener(PopupCanceledEvent popupCanceledEvent) {
                  BindingContainer binding = getBindings();
                  OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
                  createOpBAddress.execute();
                    
                  BindingContainer bindings = getBindings();
                  OperationBinding OpBAddress = bindings.getOperationBinding("Execute");
                  OpBAddress.execute(); 
                   
                  AdfFacesContext.getCurrentInstance().addPartialTarget(grpTable); 
                     UIComponent comp = popupCanceledEvent.getComponent();
                     oracle.adf.view.rich.util.ResetUtils.reset(comp);
    }   

    public void setAddpop(RichPopup addpop) {
        this.addpop = addpop;
    }

    public RichPopup getAddpop() {
        return addpop;
    }

    public void emailValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String value = (String)object;
        System.out.println(value);
           if ((value == null) || value.length() == 0) {
           return;
           }

           String expression = "^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,4})$";
           CharSequence inputStr = value;
           Pattern pattern = Pattern.compile(expression);
           Matcher matcher = pattern.matcher(inputStr);

          // String error = resolvEl("#{bundle['usergroups.UserEmailID.Message']}");
              String error="Invalid User Email ID";
           
           if (matcher.matches()) {
           } else {

           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
           } 
           
        /*
         * For Add popup
         * */   
           int addco=0;
        AppSecUsrGrpAMImpl am=(AppSecUsrGrpAMImpl)resolvElDC("AppSecUsrGrpAMDataControl");
        ViewObject v2=am.getAppSecUsrGrp();
         String mail="";
             Row[] rowI=v2.getAllRowsInRange();
             for(Row r:rowI){
                 try{
                  mail=r.getAttribute("UsrGrpMailId").toString();
                 }catch(NullPointerException npe){
                     System.out.println("NPE"+npe);
                    mail="";
                 }
                 System.out.println("Email:"+mail);
                 if(value.equalsIgnoreCase(mail)){
                    addco=addco+1;
                 }
                 
             }
             
             if(flag.equals("A")){
             if(addco>0){
                 String msg="Duplicate Email Id exists.";
                 FacesMessage message2 = new FacesMessage(msg);
                 
                 message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                 throw new ValidatorException(message2);
             }
             }
             else if(flag.equals("E")){
                     
                     if(addco>1){
                         String msg="Duplicate Email Id exists.";
                         FacesMessage message2 = new FacesMessage(msg);
                         
                         message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                         throw new ValidatorException(message2);
                     }
                 }
        
      
    }

    private void showFacesPopUp(RichPopup pop,RichMessage rm,FacesMessage msg, boolean visible) {
           try {
               FacesContext context = FacesContext.getCurrentInstance();
               if (context != null && pop != null) {
                   String popupId = pop.getClientId(context);
                   String afMessage=rm.getClientId(context);
                
                   if (popupId != null) {
                       StringBuilder script = new StringBuilder();
                       script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                       script.append("var afmsg=AdfPage.PAGE.findComponent('").append(afMessage).append("'); ");
                       if (visible) {
                           script.append("if (!popup.isPopupVisible()) { ").append("popup.show();").append("afmsg.setSummary('"+fmsg+"');").append("}");
                           
                       } else {
                           script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                       }
                       ExtendedRenderKitService erks =
                           Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                       erks.addScript(context, script.toString());
                     /*   List<UIComponent> ui=pop.getChildren();
                      // UIComponent afMsg=ui.get(1);
                       for(UIComponent x:ui){
                            System.out.println("Ui Comp :"+x);    
                       } */
                       
                      // System.out.println("UIC=========="+afMsg.getClientId());
                      // AdfFacesContext.getCurrentInstance().addPartialTarget(afMsg);
                       
                   }
               }
           } catch (Exception e) {
               throw new RuntimeException(e);
           }
       }
    public void editDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            
                   AppSecUsrGrpAMImpl am=(AppSecUsrGrpAMImpl)resolvElDC("AppSecUsrGrpAMDataControl");
                   
                   int lockMode=am.getDBTransaction().getLockingMode();
                   
                   BindingContext btx = BindingContext.getCurrent();
                   DCBindingContainer dcbct = (DCBindingContainer)btx.getCurrentBindingsEntry();
                   DCIteratorBinding binding = dcbct.findIteratorBinding("AppSecUsrGrpIterator");
                          Row currentRow = binding.getCurrentRow();
                          Key rkey = currentRow.getKey();
                    Object xxxx=rkey.getRowHandle();
                          
                 //  binding.setCurrentRowWithKey(rkey.s);
                   
                   RowSetIterator rsi=binding.getRowSetIterator();
                   
                    
                   am.getDBTransaction().setLockingMode(DBTransaction.LOCK_NONE);
                   
                   ViewObject v=am.getAppSecUsrGrp();
                   Row cR=v.getCurrentRow();
                   if(flag=="E" && am.getDBTransaction().isDirty()==true){
                       Integer user =Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
                       
                       cR.setAttribute("UsrIdMod", user);
                   }
                   
                   BindingContainer binding1 = getBindings();
                   OperationBinding createOpBAddress1 = binding1.getOperationBinding("Commit");
                   createOpBAddress1.execute();
                   
                   if(createOpBAddress1.getErrors().isEmpty()){
                    BindingContainer binding2 = getBindings();
                    OperationBinding createOpBAddress2 = binding2.getOperationBinding("Execute");
                    createOpBAddress2.execute();
                       
                     
                   
                       FacesContext fc=FacesContext.getCurrentInstance();
                       FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved" ,"Record Updated Successfully.");
                       fc.addMessage("Success",msg);

                       rsi.setCurrentRow(currentRow);
                
                    
                 // String m = resolvEl("#{bundle['usergroups.UserEmailID.Message']}");
                 ///   FacesContext fcx=FacesContext.getCurrentInstance();
                  //  UIComponent.getCurrentComponent(fcx); 
                ///   String clientId=popupmsg.getClientId();
              ///     System.out.println("Client id is :"+clientId);
               ///    FacesMessage fm=new FacesMessage(FacesMessage.SEVERITY_INFO,"Saved","Record Updated Successfully.");
                   //fcx.addMessage(clientId,fm);
                    //var popup = AdfPage.PAGE.findComponent
                    /* ExtendedRenderKitService service = (ExtendedRenderKitService)Service.getRenderKitService(fcx, ExtendedRenderKitService.class);
                    service.addScript(fcx, ""); 
                     */
                 ///   System.out.println("Message Detail:"+fm.getDetail());
              ///   String m=fm.getDetail().toString();
                 
               //  afmessage.setMessage("Updated");
                 
            ///     fmsg=fm.getDetail();
             ///    UIComponent uicp=addpop.getCurrentComponent(fcx);
                 
                // afmessage.setFor(uicp.getClientId());
                 
                // System.out.println("Message is"+afmessage.getMessage());
                 //afmessage.setMessage(fm.getSummary());      
               ///   showFacesPopUp(popupmsg,afmessage,fm,true);
                 //   grpTable.setDisplayRow(grpTable.DISPLAY_ROW_SELECTED);
                }
                   
                   am.getDBTransaction().setLockingMode(lockMode);  
                   AdfFacesContext.getCurrentInstance().addPartialTarget(grpTable); 
               } else if (dialogEvent.getOutcome().name().equals("cancel")) {

                   BindingContainer binding = getBindings();
                   OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
                   createOpBAddress.execute();

                   BindingContainer bindings = getBindings();
                   OperationBinding OpBAddress = bindings.getOperationBinding("Execute");
                   OpBAddress.execute(); 
                    
                   AdfFacesContext.getCurrentInstance().addPartialTarget(grpTable); 
                   
               }
    }

    public void setEditpop(RichPopup editpop) {
        this.editpop = editpop;
    }

    public RichPopup getEditpop() {
        return editpop;
    }


    public static Object resolveElExp(String data) {
    FacesContext fc = FacesContext.getCurrentInstance();
    Application app = fc.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = fc.getELContext();
    ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
    Object msg = valueExp.getValue(elContext);
    return msg;
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
                    
                       
    public void deleteDialogListener(DialogEvent dialogEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding=null;
        FacesContext fc=FacesContext.getCurrentInstance();
        
        if (dialogEvent.getOutcome().name().equals("yes")) {
            
            
                    AppSecUsrGrpAMImpl am=(AppSecUsrGrpAMImpl)resolvElDC("AppSecUsrGrpAMDataControl");
                    ViewObject v1=am.getAppSecUsrGrpLnk1();
                    ViewObject v2=am.getAppSecUsrGrp();
                    ViewObject v3=am.getOrgSecUsrGrp1();
                    AppSecUsrGrpVORowImpl rowImpl=(AppSecUsrGrpVORowImpl)v2.getCurrentRow();
                    v1.executeQuery();
                    
                    
                    Row[] rows = v1.getAllRowsInRange();    //UsrGrpLnk
                    Row[] rows1=v2.getAllRowsInRange();     //UsrGrp
                    
                    Row[] rows2=v3.getAllRowsInRange();      //OrgSecUsrGrp
                 
                    int x=rows.length;           // no of rows in UsrGrpLnk
                    int y=rows2.length;          // no of rows in OrgSecUsrGrp
                    //Row r=v1.getCurrentRow();
                
               //     long x=v1.getEstimatedRowCount();
                  
            
               if(x==0 && y==0){   
                
               
                int lockMode=am.getDBTransaction().getLockingMode();
                System.out.println("PreCommit LM:"+lockMode);
                am.getDBTransaction().setLockingMode(DBTransaction.LOCK_NONE);
                System.out.println("Post Set LM:"+lockMode);
            
                rowImpl.remove();
                  
             //am.getDBTransaction().commit();
                System.out.println("PostCommit LM:"+lockMode);
               operationBinding = bindings.getOperationBinding("Commit");
               operationBinding.execute();
                    
                if(operationBinding.getErrors().isEmpty()){
                    
                     BindingContainer bindings1 = getBindings();
                     OperationBinding OpBAddress = bindings1.getOperationBinding("Execute");
                     OpBAddress.execute();
                     
                             FacesMessage msg1 = new FacesMessage(FacesMessage.SEVERITY_INFO, "Deleted" ,"Record Deleted Successfully.");
                             fc.addMessage("Success",msg1); 
                             
                 }
                am.getDBTransaction().setLockingMode(lockMode);
                BindingContainer bindings1 = getBindings();
                OperationBinding OpBAddress = bindings1.getOperationBinding("Execute");
                OpBAddress.execute();
                am.getDBTransaction().rollback();
                System.out.println("Post Rollback LM:"+lockMode);
                v2.executeQuery();
                v2.clearCache();
            
            }                                
                else{
                        
                           FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "" ,"Cannot Delete this Record.");
                           fc.addMessage("Fail",msg);  
                    }
                   /*  BindingContainer binding = getBindings();
                    OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
                    createOpBAddress.execute();
                */

                } else if (dialogEvent.getOutcome().name().equals("no")) {

                    BindingContainer binding = getBindings();
                    OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
                    createOpBAddress.execute();

                     BindingContainer bindings1 = getBindings();
                    OperationBinding OpBAddress = bindings1.getOperationBinding("Execute");
                    OpBAddress.execute();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(grpTable); 
                }
            
    }

    public void setDeletepop(RichPopup deletepop) {
        this.deletepop = deletepop;
    }

    public RichPopup getDeletepop() {
        return deletepop;
    }

   

    public void resvValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String val1=object.toString();
        if(val1.equals("true")&& flag.equals("A")){
           // String msg1 = resolvEl("#{bundle['usergroups.entities.App.UserGroupReserved.Message']}");
            String msg1="User Group Reserved";
            FacesMessage message1 = new FacesMessage(msg1);
            message1.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message1);  
        }
        else if  (val1.equals("true") && flag.equals("E")) {
            //String msg2 = resolvEl("#{bundle['usergroups.entities.App.UserGroupReserved.Message']}");
            String msg2="User Group Reserved";
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
         
            }
           
           else{
                
            }

    }

    public void actvValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String val=object.toString();
        if(val.equals("false")&& flag.equals("A")){           
               // String msg = resolvEl("#{bundle['currencymaster.mpdel.entity.ActiveFlagNull']}");
                String msg="User Group cannot be Inactive for new Group";
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);          
           
            }
        else if (val.equals("false") && flag.equals("E")) {
                //showPopup(confirmationPopup, true);
               
                }else{
                    
                }
    }

    public void GroupDefValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Boolean val=(Boolean)object;
        
        String value="";
        
        if(val==true){
             value="Y";
        }else if(val==false){
            value="N";           
        }
        
        System.out.println("*****Validator****"+value);
              /*   BindingContext btx = BindingContext.getCurrent();
                DCBindingContainer dcbct = (DCBindingContainer)btx.getCurrentBindingsEntry();
                DCIteratorBinding binding = dcbct.findIteratorBinding("AppSecUsrGrpIterator");
                
               RowSetIterator r=binding.getRowSetIterator();  */
        if(val==true){  
        AppSecUsrGrpAMImpl am=(AppSecUsrGrpAMImpl)resolvElDC("AppSecUsrGrpAMDataControl");
       
        ViewObject v2=am.getAppSecUsrGrp();   
        int count=0;
        String x="";
            Row[] rowI=v2.getAllRowsInRange();
            for(Row r:rowI){
                try{
                 x=r.getAttribute("UsrGrpDef").toString();
                }catch(NullPointerException npe){
                    System.out.println("NPE"+npe);
                    x="";
                }
                System.out.println("Def:"+x);
                if(value.equalsIgnoreCase(x)){
                   count=count+1;
                }
                
            }
        //RowSet r=v2.getRowSet();
      // Row currRow=v2.getCurrentRow();
         System.out.println("Count:"+count);
        
            if(flag.equals("A")){
            if(count>0){
                String msg2="Only One Default value allowed";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
                }
            }
            else if(flag.equals("E")){
                if(count>1){
                    String msg2="Only One Default value allowed";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                    }
            }
            
        }
       
    }
  

    public void setGrpTable(RichTable grpTable) {
        this.grpTable = grpTable;
    }

    public RichTable getGrpTable() {
        return grpTable;
    }

    public void setRefreshVar(Boolean refreshVar) {
        this.refreshVar = refreshVar;
    }

    public Boolean getRefreshVar() {
        return refreshVar;
    }

    public void setPopupmsg(RichPopup popupmsg) {
        this.popupmsg = popupmsg;
    }

    public RichPopup getPopupmsg() {
        return popupmsg;
    }

    public void setAfmessage(RichMessage afmessage) {
        this.afmessage = afmessage;
    }

    public RichMessage getAfmessage() {
        return afmessage;
    }

    public void setFmsg(String fmsg) {
        this.fmsg = fmsg;
    }

    public String getFmsg() {
        return fmsg;
    }


    public void defValChangeListener(ValueChangeEvent vce) {
        String x=vce.getOldValue().toString();
        String y=vce.getNewValue().toString();
        System.out.println(x+"*****VCE****"+y);
        
        if(!x.equalsIgnoreCase(y)){
            System.out.println("value has been changed");
            
            if(x=="false" && y=="true"){
               
                changed=true;
            }
        }
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public boolean isChanged() {
        return changed;
    }

    public void emailIdValueChangeListener(ValueChangeEvent valueChangeEvent) {
        String x="";
        String y="";
        if(valueChangeEvent.getOldValue()==null){
            x="";
        }else{
         x=valueChangeEvent.getOldValue().toString();
        }
        
        if(valueChangeEvent.getNewValue()==null){
            y="";
        }else{

            y=valueChangeEvent.getNewValue().toString();
        }
        System.out.println(x+"*****VCE****"+y);
    
        if(!x.equalsIgnoreCase(y)){
            System.out.println("value has been changed");
            changedEmail=true;
        }else if(x.equalsIgnoreCase(y)){
            
        }
        
    }

    public void setChangedEmail(boolean changedEmail) {
        this.changedEmail = changedEmail;
    }

    public boolean isChangedEmail() {
        return changedEmail;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }
}
