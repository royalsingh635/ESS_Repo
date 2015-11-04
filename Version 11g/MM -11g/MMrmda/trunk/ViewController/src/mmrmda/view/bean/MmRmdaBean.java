package mmrmda.view.bean;

import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.ReturnPopupEvent;

import oracle.adf.view.rich.model.ListOfValuesModel;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;


public class MmRmdaBean {


    private RichPanelGroupLayout bindPanelCrtl;
    private boolean flag=false;
    private boolean view=true;
    private RichSelectOneChoice bindRmdaType;
    private RichCommandImageLink addBtn;
    private RichCommandImageLink addBtnBind;

    public MmRmdaBean() {
        setCurrentRowType();
        String mode=(String)evaluateEL("#{pageFlowScope.GLBL_TRANS_MOD}");
        if(mode.equalsIgnoreCase("V"))
        {view=true;}
        else if(mode.equalsIgnoreCase("A"))
        {view=false;}
    }

    public void onValueChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
       
    }
    
    
    public BindingContainer getBindings()
    {return BindingContext.getCurrent().getCurrentBindingsEntry();}


    

    public String copyItmAction() {
        // Add event code here...
        OperationBinding operationBinding =getBindings().getOperationBinding("copyItemsByRcpt");
        operationBinding.getParamsMap().put("rcptNo","");
        operationBinding.execute();
        return null;
    }

    public void setBindPanelCrtl(RichPanelGroupLayout bindPanelCrtl) {
        this.bindPanelCrtl = bindPanelCrtl;
    }

    public RichPanelGroupLayout getBindPanelCrtl() {
        return bindPanelCrtl;
    }

    public void onChangeListenerType(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        
        Integer typeId = (Integer)valueChangeEvent.getNewValue();
        if(typeId == 418)
         {
          bindPanelCrtl.setVisible(true);flag=true;}
     else
         {bindPanelCrtl.setVisible(false);flag=false;}
        AdfFacesContext.getCurrentInstance().addPartialTarget(bindPanelCrtl);
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void saveAction(ActionEvent actionEvent) {
        // Add event code here...
        
        OperationBinding operationBinding =getBindings().getOperationBinding("getItmCount");
        operationBinding.execute();
        Integer items=(Integer)operationBinding.getResult();
        
        if(items<=0)
        {
             FacesMessage message = new FacesMessage("Add atleast one Item.");
             message.setSeverity(FacesMessage.SEVERITY_ERROR);
             FacesContext fc = FacesContext.getCurrentInstance();
             fc.addMessage(null, message); 
        }
        else{
            
        if(Integer.parseInt(bindRmdaType.getValue().toString()) == 418)
        {
            operationBinding =getBindings().getOperationBinding("setDebitNoteNo");
            operationBinding.execute();
        }
            
        operationBinding =getBindings().getOperationBinding("beforeCommitAction");
        operationBinding.execute();
        Object obj=operationBinding.getResult();
            
        operationBinding =getBindings().getOperationBinding("updateRcptStatus");
        operationBinding.execute();   
        Integer status=(Integer)operationBinding.getResult();    
        
        if(status == -1)
        {obj=null;}
            
        if(obj!=null)
        {
            operationBinding =getBindings().getOperationBinding("Commit");
            operationBinding.execute();
        
            FacesMessage message = new FacesMessage(obj+" has been Saved Successfully!");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
            view=true;
            RequestContext.getCurrentInstance().getPageFlowScope().put("GLBL_TRANS_MOD", "C");
            AdfFacesContext.getCurrentInstance().addPartialTarget(addBtnBind);
        }
        else
        {
            FacesMessage message = new FacesMessage("Record not Saved.");
             message.setSeverity(FacesMessage.SEVERITY_INFO);
             FacesContext fc = FacesContext.getCurrentInstance();
             fc.addMessage(null, message);
        }
    }}
    
    
    public void setCurrentRowType()
    {
        
        OperationBinding operationBinding =getBindings().getOperationBinding("getCurrentType");
        operationBinding.execute();
        Object obj=operationBinding.getResult();
        
        if(obj !=null)
        {
         Integer iValue = (Integer)obj;
          if(iValue == 418)
              {flag=true;}
          else
                {flag=false;}
        }
    }

    public void onCreateRmda(ActionEvent actionEvent) {
        // Add event code here...
        flag=false;
        view=false;
    }

    public void setView(boolean view) {
        this.view = view;
    }

    public boolean isView() {
        return view;
    }
    
    
    public static Object evaluateEL(String el) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ELContext elContext = facesContext.getELContext();
    ExpressionFactory expressionFactory =
    facesContext.getApplication().getExpressionFactory();
    ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);
    return exp.getValue(elContext);
    }

    public void setBindRmdaType(RichSelectOneChoice bindRmdaType) {
        this.bindRmdaType = bindRmdaType;
    }

    public RichSelectOneChoice getBindRmdaType() {
        return bindRmdaType;
    }

    public void setAddBtn(RichCommandImageLink addBtn) {
        this.addBtn = addBtn;
    }

    public RichCommandImageLink getAddBtn() {
        return addBtn;
    }

    public void setAddBtnBind(RichCommandImageLink addBtnBind) {
        this.addBtnBind = addBtnBind;
    }

    public RichCommandImageLink getAddBtnBind() {
        return addBtnBind;
    }
}
