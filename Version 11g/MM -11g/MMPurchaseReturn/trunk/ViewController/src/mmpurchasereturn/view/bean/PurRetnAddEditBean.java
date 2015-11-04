package mmpurchasereturn.view.bean;

import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.layout.RichToolbar;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class PurRetnAddEditBean {


    private RichInputListOfValues mrnNoBinding;
    private RichInputDate mrnDocDtBinding;
    private String mode=resolvEl("#{pageFlowScope.Page_Mode}").toString();
    private RichSelectOneChoice whIdBinding;
    private RichInputListOfValues eoIdBinding;
    private RichPopup viewdetailpop;
    private RichToolbar toolbaarbind;

    public PurRetnAddEditBean() {
    }

    public void createButtonAL(ActionEvent actionEvent) {
      OperationBinding op=getBindings().getOperationBinding("CreateInsert");
      op.execute();
      mode="C";
    }

    public void editButtonAL(ActionEvent actionEvent) {
        Integer pending=0;
        Integer UsrId=Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String OrgId=resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer SlocId=Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String CldId= resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
           OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
                    chkUsr.getParamsMap().put("SlocId", SlocId);
                    chkUsr.getParamsMap().put("CldId", CldId);
                    chkUsr.getParamsMap().put("OrgId", OrgId);
                    chkUsr.getParamsMap().put("DocNo", 18529);
                    chkUsr.execute();
                   
                   if(chkUsr.getResult()!=null){
                      pending= Integer.parseInt(chkUsr.getResult().toString());
                   } 
                  if(pending.compareTo(new Integer(-1))==0 || pending.compareTo(UsrId)==0) 
                  {
                        
                        mode="E";
                    }
                   else {
                      String name="Anonymous";
                      OperationBinding op=getBindings().getOperationBinding("getUserName");
                      op.getParamsMap().put("usrId",pending);
                      op.execute();
                      if(op.getErrors().size()==0 && op.getResult()!=null)
                          name = (String)op.getResult();
                      String msg="Document is Pending at ["+name+"]. you can't Edit this.";
                       FacesMessage message = new FacesMessage(msg);
                       message.setSeverity(FacesMessage.SEVERITY_ERROR);
                       FacesContext fc = FacesContext.getCurrentInstance();
                       fc.addMessage(null, message); 
                       
                   }
       
    }

    public void saveButtonAL(ActionEvent actionEvent) {
        //generate PR no
       
        OperationBinding genprop=getBindings().getOperationBinding("generatePRNo");
        genprop.execute();
       
            Integer UsrId=Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
            String OrgId=resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
            Integer SlocId=Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
            String CldId= resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            //get Wf Id

            String action = "I";
            String remark = "A";
            String WfNum=null;    
            Integer level =0;
            Integer res =-1;  
            
                           OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                                           WfnoOp.getParamsMap().put("SlocId", SlocId);
                                           WfnoOp.getParamsMap().put("CldId", CldId);
                                           WfnoOp.getParamsMap().put("OrgId", OrgId);
                                           WfnoOp.getParamsMap().put("DocNo", 18529);
                                           WfnoOp.execute();
                          if(WfnoOp.getErrors().size()==0 &&  WfnoOp.getResult()!=null){
                             WfNum= WfnoOp.getResult().toString();
                          }

                          if(WfNum!=null){
                        //get user level
                         
                          OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                                           usrLevelOp.getParamsMap().put("SlocId", SlocId);
                                           usrLevelOp.getParamsMap().put("CldId", CldId);
                                           usrLevelOp.getParamsMap().put("OrgId", OrgId);
                                           usrLevelOp.getParamsMap().put("UsrId", UsrId);
                                           usrLevelOp.getParamsMap().put("WfNo", WfNum);
                                           usrLevelOp.getParamsMap().put("DocNo", 18529);
                                           usrLevelOp.execute();
                                           level=-1;
                          if(usrLevelOp.getErrors().size()==0 && usrLevelOp.getResult()!=null){
                             level= Integer.parseInt(usrLevelOp.getResult().toString());
                          }

                          if(level>=0){
                       //insert into txn
                          OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                                         insTxnOp.getParamsMap().put("SlocId", SlocId);
                                         insTxnOp.getParamsMap().put("CldId", CldId);
                                         insTxnOp.getParamsMap().put("OrgId", OrgId);
                                         insTxnOp.getParamsMap().put("DocNo", 18529);
                                         insTxnOp.getParamsMap().put("WfNo", WfNum);
                          insTxnOp.getParamsMap().put("usr_idFrm", UsrId);
                          insTxnOp.getParamsMap().put("usr_idTo", UsrId);
                          insTxnOp.getParamsMap().put("levelFrm", level);
                          insTxnOp.getParamsMap().put("levelTo", level);
                          insTxnOp.getParamsMap().put("action", action);
                          insTxnOp.getParamsMap().put("remark", remark);
                          insTxnOp.getParamsMap().put("amount", 0);
                          insTxnOp.execute();
                          
                          if(insTxnOp.getResult()!=null){
                             res= Integer.parseInt(insTxnOp.getResult().toString());
                          }      
        
            OperationBinding op=getBindings().getOperationBinding("Commit");
            op.execute();
            if(op.getErrors().size()==0)
            {
                    mode="V";
                    FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.802']}").toString());
                    message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);   
                }
                          }
                          else
                          {
                                  FacesMessage message = new FacesMessage("Something went wrong (User level in Workflow).Please Contact to ESS!");
                                  message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                  FacesContext fc = FacesContext.getCurrentInstance();
                                  fc.addMessage(null, message); 
                              }
                          }
                          else
                          {
                                  FacesMessage message = new FacesMessage("Workflow not Created for Purchase Return.");
                                  message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                  FacesContext fc = FacesContext.getCurrentInstance();
                                  fc.addMessage(null, message);   
                              }
        }

    public void cancelButtonAL(ActionEvent actionEvent) {
        OperationBinding op=getBindings().getOperationBinding("Rollback");
        op.execute();
        mode="V";
    }
    
   
    public void addMrnButtonAL(ActionEvent actionEvent) {
        OperationBinding opfy=getBindings().getOperationBinding("GenerateFyId");
        opfy.execute();
      
        if(opfy.getErrors().size()==0 && opfy.getResult()!=null && opfy.getResult().toString().equals("Y"))
        {   
        if(whIdBinding.getValue()!=null && !(whIdBinding.getValue().toString().equals("")))
        {
        if(eoIdBinding.getValue()!=null && !(eoIdBinding.getValue().toString().equals("")))
        {
        if(mrnNoBinding.getValue()!=null && !(mrnNoBinding.getValue().toString().equals("")))
        {
        OperationBinding chkDupli =getBindings().getOperationBinding("ChkDuplicateMrn");
        chkDupli.execute();
        String dupli="N";
        if(chkDupli.getErrors().size()==0 && chkDupli.getResult()!=null)
                dupli = (String)chkDupli.getResult();
        System.out.println("Is dupli? "+ dupli);
           if(dupli.equals("N"))
           {
       OperationBinding addMrn =getBindings().getOperationBinding("AddMrnNoToSrc");
       addMrn.execute();
        OperationBinding addItm=getBindings().getOperationBinding("AddItmToPurRetItm");
        addItm.execute();
        OperationBinding resetTrans =getBindings().getOperationBinding("ResetTrans");
        resetTrans.execute();
        mrnNoBinding.setValue("");
        mrnNoBinding.setValue(null);
        mrnNoBinding.resetValue();
        mrnDocDtBinding.setValue("");
        mrnDocDtBinding.setValue(null);
        mrnDocDtBinding.resetValue();
        mode="A";
        
        }
           else if(dupli.equals("Y"))
           {
                   FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.796']}").toString());
                   message.setSeverity(FacesMessage.SEVERITY_ERROR);
                   FacesContext fc = FacesContext.getCurrentInstance();
                   fc.addMessage(mrnNoBinding.getClientId(), message);   
               }
           else if(dupli.equals("D"))
           {
                   FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.798']}").toString());
                   message.setSeverity(FacesMessage.SEVERITY_ERROR);
                   FacesContext fc = FacesContext.getCurrentInstance();
                   fc.addMessage(mrnNoBinding.getClientId(), message);   
               }
        }
        else
        {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.799']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(mrnNoBinding.getClientId(), message);  
            }
        }
        else
        {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.660']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(eoIdBinding.getClientId(), message); 
            }
        }
        else
        {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.800']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(whIdBinding.getClientId(), message);
            }
        }
        else
        {
            if(opfy.getErrors().size()>0)
                System.out.println("error calculating Fy="+opfy.getErrors());
            else
            {
                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.801']}").toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);  
            }
            }
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

    public void setMrnNoBinding(RichInputListOfValues mrnNoBinding) {
        this.mrnNoBinding = mrnNoBinding;
    }

    public RichInputListOfValues getMrnNoBinding() {
        return mrnNoBinding;
    }

    public void setMrnDocDtBinding(RichInputDate mrnDocDtBinding) {
        this.mrnDocDtBinding = mrnDocDtBinding;
    }

    public RichInputDate getMrnDocDtBinding() {
        return mrnDocDtBinding;
    }

    public void setWhIdBinding(RichSelectOneChoice whIdBinding) {
        this.whIdBinding = whIdBinding;
    }

    public RichSelectOneChoice getWhIdBinding() {
        return whIdBinding;
    }

    public void setEoIdBinding(RichInputListOfValues eoIdBinding) {
        this.eoIdBinding = eoIdBinding;
    }

    public RichInputListOfValues getEoIdBinding() {
        return eoIdBinding;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public String saveAndFwdbuttonAL() {
        OperationBinding genprop=getBindings().getOperationBinding("generatePRNo");
        genprop.execute();
        
        Integer UsrId=Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String OrgId=resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer SlocId=Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String CldId= resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        //get Wf Id

        String action = "I";
        String remark = "A";
        String WfNum=null;    
        Integer level =0;
        Integer res =-1;  
        
        OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                        WfnoOp.getParamsMap().put("SlocId", SlocId);
                        WfnoOp.getParamsMap().put("CldId", CldId);
                        WfnoOp.getParamsMap().put("OrgId", OrgId);
                        WfnoOp.getParamsMap().put("DocNo", 18529);
                        WfnoOp.execute();
        if(WfnoOp.getErrors().size()==0 &&  WfnoOp.getResult()!=null){
          WfNum= WfnoOp.getResult().toString();
        }
        if(WfNum!=null && !"0".equalsIgnoreCase(WfNum)){

        //get user level
         
          OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                           usrLevelOp.getParamsMap().put("SlocId", SlocId);
                           usrLevelOp.getParamsMap().put("CldId", CldId);
                           usrLevelOp.getParamsMap().put("OrgId", OrgId);
                           usrLevelOp.getParamsMap().put("UsrId", UsrId);
                           usrLevelOp.getParamsMap().put("WfNo", WfNum);
                           usrLevelOp.getParamsMap().put("DocNo", 18529);
                           usrLevelOp.execute();
                           level=-1;
          if(usrLevelOp.getErrors().size()==0 && usrLevelOp.getResult()!=null){
             level= Integer.parseInt(usrLevelOp.getResult().toString());
          }

          if(level>=0){
        //insert into txn
          OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                         insTxnOp.getParamsMap().put("SlocId", SlocId);
                         insTxnOp.getParamsMap().put("CldId", CldId);
                         insTxnOp.getParamsMap().put("OrgId", OrgId);
                         insTxnOp.getParamsMap().put("DocNo", 18529);
                         insTxnOp.getParamsMap().put("WfNo", WfNum);
          insTxnOp.getParamsMap().put("usr_idFrm", UsrId);
          insTxnOp.getParamsMap().put("usr_idTo", UsrId);
          insTxnOp.getParamsMap().put("levelFrm", level);
          insTxnOp.getParamsMap().put("levelTo", level);
          insTxnOp.getParamsMap().put("action", action);
          insTxnOp.getParamsMap().put("remark", remark);
          insTxnOp.getParamsMap().put("amount", 0);
          insTxnOp.execute();
          
                      if(insTxnOp.getResult()!=null){
                         res= Integer.parseInt(insTxnOp.getResult().toString());
                      }      
       
        //Check Pending
                      Integer pending=0;
                         OperationBinding chkUsr = getBindings().getOperationBinding("pendingCheck");
                                  chkUsr.getParamsMap().put("SlocId", SlocId);
                                  chkUsr.getParamsMap().put("CldId", CldId);
                                  chkUsr.getParamsMap().put("OrgId", OrgId);
                                  chkUsr.getParamsMap().put("DocNo", 18529);
                                  chkUsr.execute();
                                 
                                 if(chkUsr.getResult()!=null){
                                    pending= Integer.parseInt(chkUsr.getResult().toString());
                                 } 
                                if(pending.compareTo(new Integer(-1))==0 || pending.compareTo(UsrId)==0) 
                                {
                                      OperationBinding op=getBindings().getOperationBinding("Commit");
                                      op.execute();
                                      if(op.getErrors().isEmpty()){
                                          mode="V";
                                      }
                                return "toWf";
                                  }
                                 else {
                                    String name="Anonymous";
                                    OperationBinding op=getBindings().getOperationBinding("getUserName");
                                    op.getParamsMap().put("usrId",pending);
                                    op.execute();
                                    if(op.getErrors().size()==0 && op.getResult()!=null)
                                        name = (String)op.getResult();
                                    String msg="Document is Pending at ["+name+"]. you can't Forward this.";
                                     FacesMessage message = new FacesMessage(msg);
                                     message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                     FacesContext fc = FacesContext.getCurrentInstance();
                                     fc.addMessage(null, message); 
                                    return null;  
                                 }
          }
          else
          {
                  FacesMessage message = new FacesMessage("Something went wrong (User level in Workflow). Please Contact to ESS!");
                  message.setSeverity(FacesMessage.SEVERITY_ERROR);
                  FacesContext fc = FacesContext.getCurrentInstance();
                  fc.addMessage(null, message);  
                  return null;
              }
        }
        else
        {
                FacesMessage message = new FacesMessage("Workflow not Created for Purchase Return.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);  
            return null;
            }
      
    }
    public Object resolvElDCMsg(String data) {
           FacesContext fc = FacesContext.getCurrentInstance();
           Application app = fc.getApplication();
           ExpressionFactory elFactory = app.getExpressionFactory();
           ELContext elContext = fc.getELContext();
           ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
           return valueExp.getValue(elContext);
       }

    public void viewStockAL(ActionEvent actionEvent) {
        OperationBinding exe = getBindings().getOperationBinding("executeVO"); 
        exe.execute();
        showPopup(viewdetailpop,true);
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

    public void setViewdetailpop(RichPopup viewdetailpop) {
        this.viewdetailpop = viewdetailpop;
    }

    public RichPopup getViewdetailpop() {
        return viewdetailpop;
    }

    public void setToolbaarbind(RichToolbar toolbaarbind) {
        this.toolbaarbind = toolbaarbind;
    }

    public RichToolbar getToolbaarbind() {
        return toolbaarbind;
    }
}
