package globalcountrysetup.view.bean;

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


import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;

import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class GlobalCountrySetupBean {
    
    private String mode = "V";
    private String deleteMode="Not Show"; // if "V" then delete option is Enabled in Page.
        
    Integer count =Integer.parseInt(getBindings().getOperationBinding("on_load_page").execute().toString());
    private RichPopup currencyPopup;
    private RichPopup languagePopup;
    private RichPopup pipPopup;
    private RichPopup registrationPopup;
    private RichTable cntryDescTableBinding;
    private RichTable pinTableBind;
    private RichInputListOfValues cntryTxtName;
    private RichTable currTable;
    private RichTable langTable;

    public GlobalCountrySetupBean() {
    }

    public BindingContainer getBindings() {
           return BindingContext.getCurrent().getCurrentBindingsEntry();
       }

    public void createCountryButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
                cntryDescTableBinding.setRowSelection("none");
        mode = "C";
        
    }

    public void editCountryButton(ActionEvent actionEvent) {
        mode = "E";
        cntryDescTableBinding.setRowSelection("none");
    }
    
    public String resolvMsg(String data) {
               FacesContext fc = FacesContext.getCurrentInstance();
               Application app = fc.getApplication();
               ExpressionFactory elFactory = app.getExpressionFactory();
               ELContext elContext = fc.getELContext();
               ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
               String Message = valueExp.getValue(elContext).toString();
               return Message;
           }

    public void saveButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();
                
                FacesMessage message = new FacesMessage(resolvMsg("#{bundle['MSG.533']}"));
                                                                    
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                             
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
        cntryDescTableBinding.setRowSelection("single");
               mode = "V";
               
    }

    public void cancelButton(ActionEvent actionEvent) {
       
        //Object o = cntryDescTableBinding.getActiveRowKey();
        
        DCIteratorBinding agciter = (DCIteratorBinding)getBindings().get("AppGlblCntry1Iterator");
        Key parentKey = agciter.getCurrentRow().getKey();
         
        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        
        //cntryDescTableBinding.setActiveRowKey(o);
        
        agciter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        cntryDescTableBinding.setRowSelection("single");       
        mode = "V";
    }
    
    private void showPopup(RichPopup popup, boolean visible) {
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                if (context != null && popup != null) {
                    String popupId = popup.getClientId(context);
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

    public void createCurrencyButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
        operationBinding.execute();
        showPopup(currencyPopup, true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(currTable);
                
        
    }

    public void editCurrencyButton(ActionEvent actionEvent) {
        showPopup(currencyPopup, true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(currTable);
       
    }

    public void createLanguageButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert2");
        operationBinding.execute();
        showPopup(languagePopup, true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(langTable);
       
    }

    public void editLanguageButton(ActionEvent actionEvent) {
        showPopup(languagePopup, true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(langTable);
       
    }

    public void createPinButton(ActionEvent actionEvent) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert3");
            operationBinding.execute();
            showPopup(pipPopup, true);       
        }
   

    public void editPinButton(ActionEvent actionEvent) {
        showPopup(pipPopup, true);
                
       
    }

    public void createRegistrationButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert4");
        operationBinding.execute();
        showPopup(registrationPopup, true);
                
        
    }

    public void editRegistrationButton(ActionEvent actionEvent) {
        showPopup(registrationPopup, true);
                
        
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

    public void setCurrencyPopup(RichPopup currencyPopup) {
        this.currencyPopup = currencyPopup;
    }

    public RichPopup getCurrencyPopup() {
        return currencyPopup;
    }

    public void currencyCancelListener(PopupCanceledEvent popupCanceledEvent) {
         DCIteratorBinding glblcntr = (DCIteratorBinding)getBindings().get("AppGlblCntry1Iterator");
        Key glblcntrykey = glblcntr.getCurrentRow().getKey();
        
       /* DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("AppGlblCntryCurr1Iterator");
        Key parentKey = parentIter.getCurrentRow().getKey(); */
        
        //System.out.println("Key="+parentKey);
        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        operationBinding = bindings.getOperationBinding("Execute");
        operationBinding.execute();
        
         glblcntr.setCurrentRowWithKey(glblcntrykey.toStringFormat(true));
        
        /*parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true)); */
        
    }

    public void currencyDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute(); 
                
              
               
                //AdfFacesContext.getCurrentInstance().addPartialTarget();
            }else if(dialogEvent.getOutcome().name().equals("cancel")){
            
                /*DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("AppGlblCntryCurr1Iterator");
                Key parentKey = parentIter.getCurrentRow().getKey();
                    
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                operationBinding.execute();
                
                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));*/
                    
             
            }
    }

    public void setLanguagePopup(RichPopup languagePopup) {
        this.languagePopup = languagePopup;
    }

    public RichPopup getLanguagePopup() {
        return languagePopup;
    }

    public void languageCancelListener(PopupCanceledEvent popupCanceledEvent) {
      
        DCIteratorBinding glblcntr = (DCIteratorBinding)getBindings().get("AppGlblCntry1Iterator");
        Key glblcntrykey = glblcntr.getCurrentRow().getKey();
        /*   
        DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("AppGlblCntryLang1Iterator");
        Key parentkey = parentIter.getCurrentRow().getKey(); */
        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        
       glblcntr.setCurrentRowWithKey(glblcntrykey.toStringFormat(true));
        /* 
        parentIter.setCurrentRowWithKey(parentkey.toStringFormat(true)); */
                
       
    }

    public void languageDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();
                
              
                
                //AdfFacesContext.getCurrentInstance().addPartialTarget();
            }else if(dialogEvent.getOutcome().name().equals("cancel")){
            
               /*  DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("AppGlblCntryLang1Iterator");
                Key parentkey = parentIter.getCurrentRow().getKey();
                    
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                operationBinding.execute();
                
                parentIter.setCurrentRowWithKey(parentkey.toStringFormat(true)); */
                    
              
                    
            }
    }

    public void setPipPopup(RichPopup pipPopup) {
        this.pipPopup = pipPopup;
    }

    public RichPopup getPipPopup() {
        return pipPopup;
    }

    public void pinCancelListener(PopupCanceledEvent popupCanceledEvent) {
        
         DCIteratorBinding glblcntr = (DCIteratorBinding)getBindings().get("AppGlblCntry1Iterator");
        Key glblcntrykey = glblcntr.getCurrentRow().getKey();
        
      /*  DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("AppGlblCntryPin1Iterator");
        Key parentkey = parentIter.getCurrentRow().getKey();
         */
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        
         glblcntr.setCurrentRowWithKey(glblcntrykey.toStringFormat(true));
        
       /* parentIter.setCurrentRowWithKey(parentkey.toStringFormat(true)); */
                
       
    }

    public void pinDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
                    
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();
                
                operationBinding = bindings.getOperationBinding("refreshpinVo");
                operationBinding.execute();
                
                AdfFacesContext.getCurrentInstance().addPartialTarget(pinTableBind);



            }else if(dialogEvent.getOutcome().name().equals("cancel")){
            
               /*  DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("AppGlblCntryPin1Iterator");
                Key parentkey = parentIter.getCurrentRow().getKey();
                    
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                operationBinding.execute();
                
                parentIter.setCurrentRowWithKey(parentkey.toStringFormat(true));
                 */
               
                    
            }
    }

    public void setRegistrationPopup(RichPopup registrationPopup) {
        this.registrationPopup = registrationPopup;
    }

    public RichPopup getRegistrationPopup() {
        return registrationPopup;
    }

    public void registrationCancelListener(PopupCanceledEvent popupCanceledEvent) {
        
         DCIteratorBinding glblcntr = (DCIteratorBinding)getBindings().get("AppGlblCntry1Iterator");
        Key glblcntrykey = glblcntr.getCurrentRow().getKey();
        /* 
        DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("AppGlblCntryOrgRegn1Iterator");
        Key parentkey = parentIter.getCurrentRow().getKey(); */
                
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        
        glblcntr.setCurrentRowWithKey(glblcntrykey.toStringFormat(true));
        /*         
        parentIter.setCurrentRowWithKey(parentkey.toStringFormat(true)); */
        
       
    }

    public void registrationDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
                   
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();

             

        }else if(dialogEvent.getOutcome().name().equals("cancel")){
          /*   
            DCIteratorBinding parentIter = (DCIteratorBinding)getBindings().get("AppGlblCntryOrgRegn1Iterator");
            Key parentkey = parentIter.getCurrentRow().getKey();
                   
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                operationBinding.execute();
                
            parentIter.setCurrentRowWithKey(parentkey.toStringFormat(true));
             */
           
                   
        }
               
           
    }

    public void setCntryDescTableBinding(RichTable cntryDescTableBinding) {
        this.cntryDescTableBinding = cntryDescTableBinding;
    }

    public RichTable getCntryDescTableBinding() {
        return cntryDescTableBinding;
    }

    public void setPinTableBind(RichTable pinTableBind) {
        this.pinTableBind = pinTableBind;
    }

    public RichTable getPinTableBind() {
        return pinTableBind;
    }

    public void currValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
       System.out.println("object"+object.toString());
       BindingContainer bindings = getBindings();
        OperationBinding operationBinding;
        operationBinding = bindings.getOperationBinding("currencyValidator");
        operationBinding.getParamsMap().put("currency", object.toString());
       operationBinding.execute();
       int i = (Integer)operationBinding.getResult();
        System.out.println("value of i:"+i);
        if(i>0) {
            System.out.println("in if of bean of curr val");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Duplicate Record",null));
        }

    }

    public void langValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println("object"+object.toString());
         BindingContainer bindings = getBindings();
         OperationBinding operationBinding = bindings.getOperationBinding("langValidator");
        operationBinding.getParamsMap().put("language", object.toString());
         operationBinding.execute();
         int i = (Integer)operationBinding.getResult();
         System.out.println("value of i:"+i);
         if(i>0) {
             System.out.println("in if of bean of lang val");
             throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Duplicate Record",uIComponent.getClientId()));
         }

        }

    public void setCntryTxtName(RichInputListOfValues cntryTxtName) {
        this.cntryTxtName = cntryTxtName;
    }

    public RichInputListOfValues getCntryTxtName() {
        return cntryTxtName;
    }

    public void searchCntryByNmAction(ActionEvent actionEvent) {
        String a=cntryTxtName.getValue().toString();
        System.out.println(cntryTxtName.getValue());
        OperationBinding binding = BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("searchByCntryNameVo");
        binding.getParamsMap().put("cntryName",(cntryTxtName.getValue()==null)?null:cntryTxtName.getValue());
        binding.execute();
        cntryTxtName.setValue(a);
    }

    public void resetAction(ActionEvent actionEvent) {
        System.out.println("Hello");
        cntryTxtName.setValue("");
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("resetVo").execute();
    }

    public void setCurrTable(RichTable currTable) {
        this.currTable = currTable;
    }

    public RichTable getCurrTable() {
        return currTable;
    }

    public void deleteCurrencyAction(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
    }

    public void setLangTable(RichTable langTable) {
        this.langTable = langTable;
    }

    public RichTable getLangTable() {
        return langTable;
    }

    public void deleteLanguageAction(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete1").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
    }

    public void deletePinAction(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete2").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
    }

    public void deleteRegAction(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete3").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
    }

    public void deleteRegACTION(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void countryNmValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String name=(String)object;
        String expression="^(?:(?>[A-Za-z ]+)(\\.(?!\\.|$))*" +"?)*$";
                    CharSequence inputStr =name;
                    Pattern pattern = Pattern.compile(expression);
                    Matcher matcher = pattern.matcher(inputStr);
                    if (matcher.matches()) {
                    } else {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalid Country Name",null));
                    }
    }

    public void oldDescValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String desc=(String)object;
        String expression="^(?:(?>[A-Za-z ]+)(\\.(?!\\.|$))*" +"?)*$";
                    CharSequence inputStr =desc;
                    Pattern pattern = Pattern.compile(expression);
                    Matcher matcher = pattern.matcher(inputStr);
                    if (matcher.matches()) {
                    } else {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalid Description",null));
                    }
    }

    public void setDeleteMode(String deleteMode) {
        this.deleteMode = deleteMode;
    }

    public String getDeleteMode() {
        return deleteMode;
    }

    public void pinLenValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        try{
        int i = Integer.parseInt(object.toString());
        }catch(Exception e){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"0-9 Value Allow",null));
        }
    }
}

