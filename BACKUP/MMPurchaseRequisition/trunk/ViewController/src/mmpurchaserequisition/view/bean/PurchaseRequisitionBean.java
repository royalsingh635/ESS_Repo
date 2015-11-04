package mmpurchaserequisition.view.bean;

import java.math.BigDecimal;

import java.util.concurrent.locks.Lock;

import java.util.concurrent.locks.ReentrantLock;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

import org.apache.myfaces.trinidad.context.RequestContext;

public class PurchaseRequisitionBean {
    
   private boolean flag=true;
   private String txnId=null;
   private boolean readny=true;
   private boolean disablebtn=true;
   private boolean createbtn=false;
   private boolean disableEditBtn=true;
    private RichSelectOneChoice srcDoc;
    private RichSelectOneChoice reqArea;
    private RichSelectOneChoice prType;
    private RichInputDate prDate;
    private RichSelectOneChoice prStatus;
    private String saveUpdateMessage;
    private Integer count=-1;
    private RichSelectBooleanCheckbox itemCancel;
    private Integer prevStatus=-1;
    private boolean itemCancelVisbl=true;
    private boolean cancelItems=true;
    private RichTable tblBind;
    private RichSelectBooleanCheckbox cancelChkBox;

    public PurchaseRequisitionBean() {
     
        count=(Integer)getBindings().getOperationBinding("on_load_page").execute();
        OperationBinding op=  getBindings().getOperationBinding("getCurrentPrTnxIdTF");
         op.execute();
         String str=(String)op.getResult();
         if(str!=null)
         {    setTxnId(str);
              disablebtn=true;
              createbtn=false;
              flag=true;
              readny=true;
              disableEditBtn=false;
              itemCancelVisbl=true;
          } 
         else
         {setTxnId(null);
           disablebtn=false;flag=false;readny=false;disableEditBtn=true;
                 saveUpdateMessage=evaluateEL("#{bundle['MSG.91']}").toString();
                 createbtn=true;
                 itemCancelVisbl=false;
             }
         
        
        
    }

    public String saveAction() { 
        // Add event code here...
        
        if(reqArea.getValue()==null || reqArea.getValue().toString().length()<=0)
        {
            FacesMessage msg=new FacesMessage(evaluateEL("#{bundle['MSG.573']}").toString());
            msg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext fctx=FacesContext.getCurrentInstance();
            fctx.addMessage(reqArea.getClientId(fctx), msg);
        }
        else if (prType.getValue()==null || prType.getValue().toString().length()<=0)
        { 
            FacesMessage msg=new FacesMessage(evaluateEL("#{bundle['MSG.576']}").toString());
            msg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext fctx=FacesContext.getCurrentInstance();
            fctx.addMessage(prType.getClientId(fctx), msg);
        }
        else if (srcDoc.getValue()==null || srcDoc.getValue().toString().length()<=0)
        {
            FacesMessage msg=new FacesMessage(evaluateEL("#{bundle['MSG.580']}").toString());
            msg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext fctx=FacesContext.getCurrentInstance();
            fctx.addMessage(srcDoc.getClientId(fctx), msg);
        }
        else if (prDate.getValue()==null || prDate.getValue().toString().length()<=0)
        {
            FacesMessage msg=new FacesMessage(evaluateEL("#{bundle['MSG.578']}").toString());
            msg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext fctx=FacesContext.getCurrentInstance();
            fctx.addMessage(prDate.getClientId(fctx), msg);
        }
        else if (prStatus.getValue()==null || prStatus.getValue().toString().length()<=0)
        {
            FacesMessage msg=new FacesMessage(evaluateEL("#{bundle['MSG.579']}").toString());
            msg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext fctx=FacesContext.getCurrentInstance();
            fctx.addMessage(prStatus.getClientId(fctx), msg);
        }
        
        else if (prDate.getValue()!=null || prDate.getValue().toString().length()>0)
        {
          
             OperationBinding op=  getBindings().getOperationBinding("getFYid");
             op.execute();
             Integer fyId=(Integer)op.getResult();
 
          
          if(fyId!=null && fyId<0)
          {
            FacesMessage msg=new FacesMessage(evaluateEL("#{bundle['MSG.575']}").toString());
            msg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext fctx=FacesContext.getCurrentInstance();
            fctx.addMessage(prDate.getClientId(fctx), msg);
          }
          else
          {commit();}
        }
        
    else {
            commit();
        }
      return null;
    }
    
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
  /*  public String createAction() {
        // Add event code here...
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        flag=false;
        disableEditBtn=true;
        createbtn=true;
        readOnly=false;
        disablebtn=false;
        return null;
    }
*/
    public String cancelAction() {
        // Add event code here...
        
        return null;
    }

    public void getSrcDocItems(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblBind);
        OperationBinding op=  getBindings().getOperationBinding("setPrItems");
        op.getParamsMap().put("txnId",valueChangeEvent.getNewValue());
        op.execute();
    
    }

    public void checkValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        OperationBinding op=  getBindings().getOperationBinding("checkPendingQty");
        op.getParamsMap().put("qty",new BigDecimal((object.toString())));
        op.execute();
        
        Boolean x=(Boolean)op.getResult();
        if(!x)
        {  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,evaluateEL("#{bundle['MSG.577']}").toString(),null)); 
        }
        
    }

    public void updatePendingQty(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(valueChangeEvent.getNewValue()!=null){
            OperationBinding op=  getBindings().getOperationBinding("updatePendingQty");
            op.getParamsMap().put("quantity",new BigDecimal(valueChangeEvent.getNewValue().toString()));
            op.execute();
                
        }
        
    }


    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void cancelAction(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        flag=true;
    }


    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setDisablebtn(boolean disablebtn) {
        this.disablebtn = disablebtn;
    }

    public boolean isDisablebtn() {
        return disablebtn;
    }

    public void editAction(ActionEvent actionEvent) {
       // Add event code here...
      //  srcDoc.processUpdates(FacesContext.getCurrentInstance());
     //  AdfFacesContext.getCurrentInstance().addPartialTarget(srcDoc);
     OperationBinding op=  getBindings().getOperationBinding("getPRStatus");
     op.execute();
     Integer prStatus = (Integer)op.getResult();
     setPrevStatus(prStatus);
     
        String OrgId=evaluateEL("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer SlocId=Integer.parseInt(evaluateEL("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String CldId= evaluateEL("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String currUser = evaluateEL("#{pageFlowScope.GLBL_APP_USR}").toString();
        
         OperationBinding chkUsr = getBindings().getOperationBinding("getModifiedUser");
        chkUsr.execute();
        Object pendUser= chkUsr.getResult();
        if(pendUser != null)
        {
        if(!currUser.equals(pendUser.toString()))
          {
                String msg2 = evaluateEL("#{bundle['MSG.612']}").toString();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
             return;
            }
        
        }
     
        
     srcDoc.processUpdates(FacesContext.getCurrentInstance());
    AdfFacesContext.getCurrentInstance().addPartialTarget(srcDoc);
        saveUpdateMessage=evaluateEL("#{bundle['MSG.92']}").toString();
        disablebtn=false;
        readny=false;
        disableEditBtn=true;
        flag=true;
        cancelItems=false;
        
    }


    public void setCreatebtn(boolean createbtn) {
        this.createbtn = createbtn;
    }

    public boolean isCreatebtn() {
        return createbtn;
    }

   


    public void setDisableEditBtn(boolean disableEditBtn) {
        this.disableEditBtn = disableEditBtn;
    }

    public boolean isDisableEditBtn() {
        return disableEditBtn;
    }

    public void setSrcDoc(RichSelectOneChoice srcDoc) {
        this.srcDoc = srcDoc;
    }

    public RichSelectOneChoice getSrcDoc() {
        return srcDoc;
    }

    public void updateSrcDoc(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
       // srcDoc.updateModel(FacesContext.getCurrentInstance());
        srcDoc.processUpdates(FacesContext.getCurrentInstance());
       AdfFacesContext.getCurrentInstance().addPartialTarget(srcDoc);
          }
    
    private boolean srcDocDisable = true;
    public void setSrcDocDisable(boolean srcDocDisable) {
        this.srcDocDisable = srcDocDisable;
    }

    public boolean isSrcDocDisable() {
        return srcDocDisable;
    }

    public void wareHouseChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        srcDocDisable=false;
    }

    public void setReqArea(RichSelectOneChoice reqArea) {
        this.reqArea = reqArea;
    }

    public RichSelectOneChoice getReqArea() {
        return reqArea;
    }

    public void setPrType(RichSelectOneChoice prType) {
        this.prType = prType;
    }

    public RichSelectOneChoice getPrType() {
        return prType;
    }

    public void setPrDate(RichInputDate prDate) {
        this.prDate = prDate;
    }

    public RichInputDate getPrDate() {
        return prDate;
    }

    public void setPrStatus(RichSelectOneChoice prStatus) {
        this.prStatus = prStatus;
    }

    public RichSelectOneChoice getPrStatus() {
        return prStatus;
    }
    
    public void commit()
    {
         OperationBinding op=  getBindings().getOperationBinding("Commit");
         op.execute();
  
        if(op.getErrors().isEmpty()){
        createbtn=false;
        readny=true;
        disableEditBtn=false;
        flag=true;
        disablebtn=true;
        itemCancelVisbl=true;
            cancelItems=true;
        FacesMessage msg=new FacesMessage(saveUpdateMessage);
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext fctx=FacesContext.getCurrentInstance();
        fctx.addMessage(null, msg);
        }
    }
    
    public static Object evaluateEL(String el) 
    {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ELContext elContext = facesContext.getELContext();
    ExpressionFactory expressionFactory =
    facesContext.getApplication().getExpressionFactory();
    ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);
    return exp.getValue(elContext);
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void createActionListener(ActionEvent actionEvent) {
        // Add event code here...
        srcDoc.processUpdates(FacesContext.getCurrentInstance());
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(srcDoc);
        flag=false;
        disableEditBtn=true;
        createbtn=true;
        readny=false;
        disablebtn=false;
        saveUpdateMessage=evaluateEL("#{bundle['MSG.91']}").toString();
        itemCancelVisbl=false;
        cancelItems=true;
//        RequestContext context = RequestContext.getCurrentInstance();
//        context.getPageFlowScope().put("", "C");
    }

    public void checkUncheckListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        
           
        Boolean checked =(Boolean)valueChangeEvent.getNewValue();
        
       if(checked)
       {  
           OperationBinding op=  getBindings().getOperationBinding("checkUncheckAll");
           op.getParamsMap().put("tnxId",txnId);
           op.getParamsMap().put("b",true);
           op.getParamsMap().put("prStatus",342);
           op.execute();
           itemCancel.setDisabled(true);
          
       }
        if(!checked)
        {
            OperationBinding op=  getBindings().getOperationBinding("checkUncheckAll");
            op.getParamsMap().put("tnxId",txnId);
            op.getParamsMap().put("b",false);
            op.getParamsMap().put("prStatus",getPrevStatus());
            op.execute();
            itemCancel.setDisabled(false);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(prStatus);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itemCancel);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tblBind);
        
    }

    public void setItemCancel(RichSelectBooleanCheckbox itemCancel) {
        this.itemCancel = itemCancel;
    }

    public RichSelectBooleanCheckbox getItemCancel() {
        return itemCancel;
    }

    public void setPrevStatus(Integer prevStatus) {
        this.prevStatus = prevStatus;
    }

    public Integer getPrevStatus() {
        return prevStatus;
    }

    public void setItemCancelVisbl(boolean itemCancelVisbl) {
        this.itemCancelVisbl = itemCancelVisbl;
    }

    public boolean isItemCancelVisbl() {
        return itemCancelVisbl;
    }

    public void setCancelItems(boolean cancelItems) {
        this.cancelItems = cancelItems;
    }

    public boolean isCancelItems() {
        return cancelItems;
    }

    public void setTblBind(RichTable tblBind) {
        this.tblBind = tblBind;
    }

    public RichTable getTblBind() {
        return tblBind;
    }

    public void setReadny(boolean readny) {
        this.readny = readny;
    }

    public boolean isReadny() {
        return readny;
    }

    public void checkBoxListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean checked =(Boolean)valueChangeEvent.getNewValue();
       if(checked)
       {    OperationBinding op=  getBindings().getOperationBinding("isAllCheckBoxMark");
            op.execute();
            Boolean flag = (Boolean)op.getResult();
            if(flag)
            {
                  op=  getBindings().getOperationBinding("setStatus");
                  op.getParamsMap().put("prStatus", 342);
                  op.execute();
                  cancelChkBox.setValue(true);}
            else
            {cancelChkBox.setValue(false);}
       }
       else
       {          OperationBinding op=  getBindings().getOperationBinding("setStatus");
                  op.getParamsMap().put("prStatus", prevStatus);
                  op.execute();
                  cancelChkBox.setValue(false);
       }
       AdfFacesContext.getCurrentInstance().addPartialTarget(cancelChkBox);
    }

    public void setCancelChkBox(RichSelectBooleanCheckbox cancelChkBox) {
        this.cancelChkBox = cancelChkBox;
    }

    public RichSelectBooleanCheckbox getCancelChkBox() {
        return cancelChkBox;
    }
}
