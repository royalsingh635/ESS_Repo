package appGlblCurr.view.bean;

import appGlblCurr.model.module.AppModuleAMImpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;



import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
import oracle.binding.OperationBinding;
import oracle.binding.BindingContainer;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;


public class GlblCurrBean {
    private RichInputText currDesc;
    private RichTable currTable;
    private static String editButton="false";
    private static String saveButton="true";
    private static String addButton="false";
    private RichPopup deletePop;
    private static String mode="V";
    private RichSelectBooleanCheckbox activeCB;
    private RichSelectBooleanCheckbox usedinappCB;
    private RichPanelFormLayout panelform5;
    private RichPanelBox panelbox;
    private RichPanelGroupLayout pg1;
    private Integer count =null; 
    private boolean isRenderedbean;
    private String deleteMode="Not Show"; //if deleteMode="V" then delete option is Enabled

    public GlblCurrBean() {
    }
    
    public String resolvElMsg(String data) {
       FacesContext fc = FacesContext.getCurrentInstance();
       Application app = fc.getApplication();
       ExpressionFactory elFactory = app.getExpressionFactory();
       ELContext elContext = fc.getELContext();
       ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
       String Message = valueExp.getValue(elContext).toString();
       return Message;
    }
    
    public static BindingContainer getBindings() {
           return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
   
    public void createActionListener(ActionEvent actionEvent) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
            operationBinding.execute();
            currDesc.setReadOnly(false);
            editButton="true";
            saveButton="false";
            addButton="true";
            mode="C";
            AdfFacesContext.getCurrentInstance().addPartialTarget(actionEvent.getComponent());
    }
    
    public void editActionListener(ActionEvent actionEvent) {
        currDesc.setReadOnly(false);
        mode="C";
        editButton="true";
        saveButton="false";
        addButton="true";
      //  mode="V";
    }

    
    public void cancelActionListener(ActionEvent actionEvent) {
        currDesc.setReadOnly(true);
        editButton="false";
        saveButton="true";
        addButton="false";
        

      BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
        OperationBinding createOpBAddress1 = binding.getOperationBinding("Execute");
      if("E".equalsIgnoreCase(mode)){
        DCIteratorBinding parentIter = (DCIteratorBinding)binding.get("AppGlblCurrIterator");
        Key parentKey = parentIter.getCurrentRow().getKey();
      
        createOpBAddress.execute();
        createOpBAddress1.execute();
       
        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
      }else{
          createOpBAddress.execute();
          createOpBAddress1.execute();
      }
      
        mode="V";
    }

    public void saveActionListener(ActionEvent actionEvent) {
        currDesc.setReadOnly(true);
        editButton="false";
        saveButton="true";
        addButton="false";
        BindingContainer binding = getBindings();
        OperationBinding createOpBAddress = binding.getOperationBinding("Commit");
        createOpBAddress.execute();
       /*  BindingContainer bindings = getBindings();
        OperationBinding createOpBAddress1 = bindings.getOperationBinding("Execute");
        createOpBAddress1.execute(); */
        if(createOpBAddress.getErrors().isEmpty()){
            
        }
        FacesMessage Message = new FacesMessage(resolvElMsg("#{bundle['MSG.91']}"));//"Record Saved Successfully!"   
              Message.setSeverity(FacesMessage.SEVERITY_INFO);   
              FacesContext fc = FacesContext.getCurrentInstance();   
              fc.addMessage(null, Message);   
        AdfFacesContext.getCurrentInstance().addPartialTarget(currTable);
        mode="V";
    }

    public void setCurrDesc(RichInputText currDesc) {
        this.currDesc = currDesc;
    }

    public RichInputText getCurrDesc() {
        return currDesc;
    }

   

    public void setCurrTable(RichTable currTable) {
        this.currTable = currTable;
    }

    public RichTable getCurrTable() {
        return currTable;
    }

    public void setEditButton(String editButton) {
        this.editButton = editButton;
    }

    public String getEditButton() {
        return editButton;
    }

    public void setSaveButton(String saveButton) {
        this.saveButton = saveButton;
    }

    public String getSaveButton() {
        return saveButton;
    }

    public void deleteActionListener(ActionEvent actionEvent) {
        currDesc.setReadOnly(true);
        editButton="false";
        saveButton="true";
        addButton="false";
        showPopup(deletePop, true);
       
    }

    public void setAddButton(String addButton) {
        this.addButton = addButton;
    }

    public String getAddButton() {
        return addButton;
    }

    public void popUpCancelListener(PopupCanceledEvent popupCanceledEvent) {
        
                       BindingContainer bindings = getBindings();
                       DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("FinCoa1Iterator");
                       Key parentKey = parentIter.getCurrentRow().getKey();
                       
                        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");
              
                        operationBinding.execute();
                        operationBinding1.execute();
                       parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        mode="V";
    }

    public void deleteDialogListener(DialogEvent dialogEvent) {
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
              mode="V";
          } else if (dialogEvent.getOutcome().name().equals("no")) {
          
        }
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

    public void setDeletePop(RichPopup deletePop) {
        this.deletePop = deletePop;
    }

    public RichPopup getDeletePop() {
        return deletePop;
    }

    public void currencyDescValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object!=null){
                String nameDesc=object.toString();
                    int openB=0;
                    int closeB=0;
                    boolean closeFg=false;
                    
                char[] xx=nameDesc.toCharArray();
                
                for(char c:xx){
                    
                        if(c=='('){
                            
                            openB=openB+1;
                            
                        }else if(c==')'){
                            
                            closeB=closeB+1;
                            
                        }
                    
                    if(closeB>openB){
                        closeFg=true; //1.no. of closed brackets will not be more than open brackets at any given time.
                    }
                }
               
                //2.if openB=0 then no. of closing and opening brackets equal || 3.opening bracket must always come before closing brackets
                //4.closing brackets must not come before first occurrence of openning bracket
                if(openB!=closeB ||closeFg==true||(nameDesc.lastIndexOf("(")>nameDesc.lastIndexOf(")")) ||(nameDesc.indexOf(")")<nameDesc.indexOf("(")) ){
                    String msg2=resolvElMsg("#{bundle['MSG.7']}");//"Brackets not closed properly."
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                }
                
                //5.check for empty brackets
                if(nameDesc.contains("()")){
                        String msg2=resolvElMsg("#{bundle['MSG.170']}");//"Empty Brackets are not allowed.";
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(message2);
                }     
                //check for wrong combo
                    if(nameDesc.contains("(.")||nameDesc.contains("(-")||nameDesc.contains("-)")){
                        String msg2=resolvElMsg("#{bundle['MSG.149']}");//"Invalid language name.Check content inside brackets.";
                        FacesMessage message2 = new FacesMessage(msg2);
                        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(message2);
                    }
                    
                    openB=0;
                    closeB=0;
                    closeFg=false; 
                     
                    AppModuleAMImpl am = (AppModuleAMImpl)resolvElDC("AppModuleAMDataControl");
                    ViewObject v=am.getAppGlblCurr();
                
                // RowSet rs=v.getRowSet();
                int totalCount=v.getRowCount();  //get ALL rows
                int rangeSize=v.getRangeSize(); //all in range
                v.setRangeSize(totalCount);
                Row[] rArray=v.getAllRowsInRange();
                
                //check for valid DESCRIPTION.. Char+Numbers allowed.Can have brackets and hyphen or dot .No two hyphens/dots/hyphen+dots can be adjacent.
                     
                     
                        String expression = "^(?:(?>[A-Za-z \\(\\)]+)(\\.(?!\\.|$))?)*$";
                      
                        CharSequence inputStr = nameDesc;
                        Pattern pattern = Pattern.compile(expression);
                        Matcher matcher = pattern.matcher(inputStr); 
                        String error=resolvElMsg("#{bundle['MSG.534']}");//"Invalid Currency Name";
                        
                        if (matcher.matches()) {  }
                        else {
                             throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
                        }  
                     
                //check for duplicate rows
                Row cRow=v.getCurrentRow();
                int count=0;
                String currName="";
                
                for(Row r:rArray){
                             
                    if(!r.equals(cRow)){
                        try{
                        currName=r.getAttribute("GlblCurrDesc").toString();   
                        }
                        catch(NullPointerException npe){
                            System.out.println("NPE:"+npe);
                            currName="";
                        }
                        
                             if(currName.equalsIgnoreCase(nameDesc)){
                                count=count+1;
                             }
                    }  
                                
                }
                v.setRangeSize(rangeSize);  //set to original range
                       
                    if(count>0){
                    String msg2=resolvElMsg("#{bundle['MSG.46']}");//"Duplicate Record found";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
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

    public void currNotationValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object!=null){
                String notation=object.toString();
                
                       String expression = "^[A-Za-z]+$";
                       CharSequence inputStr = notation;
                       Pattern pattern = Pattern.compile(expression);
                       Matcher matcher = pattern.matcher(inputStr);
                       String error=resolvElMsg("#{bundle['MSG.535']}");//"Invalid Notation";
                       
                       if (matcher.matches()) {
                       }else {
                                      throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
                       } 
             
            AppModuleAMImpl am = (AppModuleAMImpl)resolvElDC("AppModuleAMDataControl");
            ViewObject v=am.getAppGlblCurr();
            
            // RowSet rs=v.getRowSet();
            int totalCount=v.getRowCount();  //get ALL rows
            int rangeSize=v.getRangeSize(); //all in range
            v.setRangeSize(totalCount);
            Row[] rArray=v.getAllRowsInRange();
            Row cRow=v.getCurrentRow();
            int count=0;
            String currName="";
            
            for(Row r:rArray){
                         
                if(!r.equals(cRow)){
                    try{
                    currName=r.getAttribute("GlblCurrNotationAlias").toString();   
                    }
                    catch(NullPointerException npe){
                        System.out.println("NPE:"+npe);
                        currName="";
                    }
                    
                         if(currName.equalsIgnoreCase(notation)){
                            count=count+1;
                         }
                }  
                            
            }
            v.setRangeSize(rangeSize);  //set to original range
                   
                if(count>0){
                String msg2=resolvElMsg("#{bundle['MSG.46']}");//"Duplicate Record found";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
                }
                    
            }         
        }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void actvvalidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
         AppModuleAMImpl am = (AppModuleAMImpl)resolvElDC("AppModuleAMDataControl");
        ViewObjectImpl vo =am.getAppGlblCurr();
        Row row =vo.getCurrentRow();
        String usdinapp = row.getAttribute("GlblCurrUsedInApp").toString();
        System.out.println("value of used in app is--------"+usdinapp);
        System.out.println("Value of Active-->"+object.toString());
        if(object.equals(false)){
        if(usdinapp.equalsIgnoreCase("Y")) {
            System.out.println("matched");
            FacesMessage Message = new FacesMessage(resolvElMsg("#{bundle['MSG.536']}"));   //Currency is currently in used
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);   
           /*  row.setAttribute("GlblCurrActv", 'Y');
            String acb = row.getAttribute("GlblCurrActv").toString();
            System.out.println("value of acb is----"+acb); */
            throw new ValidatorException(Message);
        }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(activeCB);
        AdfFacesContext.getCurrentInstance().addPartialTarget(usedinappCB);
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelform5);
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelbox);   
        AdfFacesContext.getCurrentInstance().addPartialTarget(pg1); 
    
    }


    public void setActiveCB(RichSelectBooleanCheckbox activeCB) {
        this.activeCB = activeCB;
    }

    public RichSelectBooleanCheckbox getActiveCB() {
        return activeCB;
    }

    public void setUsedinappCB(RichSelectBooleanCheckbox usedinappCB) {
        this.usedinappCB = usedinappCB;
    }

    public RichSelectBooleanCheckbox getUsedinappCB() {
        return usedinappCB;
    }

    public void setPanelform5(RichPanelFormLayout panelform5) {
        this.panelform5 = panelform5;
    }

    public RichPanelFormLayout getPanelform5() {
        return panelform5;
    }

    public void setPanelbox(RichPanelBox panelbox) {
        this.panelbox = panelbox;
    }

    public RichPanelBox getPanelbox() {
        return panelbox;
    }

    public void setPg1(RichPanelGroupLayout pg1) {
        this.pg1 = pg1;
    }

    public RichPanelGroupLayout getPg1() {
        return pg1;
    }


    public void setIsRenderedbean(boolean isRenderedbean) {
        this.isRenderedbean = isRenderedbean;
    }

   

    public Integer getCount() {
        if (count== null)
        {
                OperationBinding op = getBindings().getOperationBinding("on_load_page");
                op.execute();
                count = Integer.parseInt(op.getResult().toString());
                }
        return count;
    }

    public void deleteCurrAction(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
    }

    public void setDeleteMode(String deleteMode) {
        this.deleteMode = deleteMode;
    }

    public String getDeleteMode() {
        return deleteMode;
    }
}

