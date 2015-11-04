package appcntrysetup.view.bean;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class AppCntrySetupBean {
    private RichPopup popupbind;
    private RichInputText cntrydescBind;
    private RichPopup popupbindLang;
    private RichPanelLabelAndMessage langidBind;
    private RichSelectOneChoice langBind;
      private String mode="V";
      private String CurrMode="V";
      private String CNMode = "F";
      private String LangMode="V";
    private RichTable tableBind;
    private RichPopup popupBindDelete;
    private RichCommandImageLink deleteBind;
    private RichInputListOfValues transContryNameBind;
    private RichInputListOfValues countryVarTrans;
    private RichTable currTableBind;
    private RichTable lanTableBind;

    public AppCntrySetupBean() {
    }

    public String createCntry() {
        mode="C";
        CNMode ="T";
        countryVarTrans.setDisabled(false);
        tableBind.setRowSelection("none");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        return null;
    }
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String save() {
        countryVarTrans.setDisabled(true);
        mode="V";
        CNMode ="F";
        tableBind.setRowSelection("single");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        if (operationBinding.getErrors().isEmpty()) {
            String message1 = resolvElDCMsg("#{bundle['MSG.91']}").toString();
            FacesMessage message = new FacesMessage(message1);
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {
            String message1 = resolvElDCMsg("#{bundle['MSG.660']}").toString();
            FacesMessage message = new FacesMessage(message1);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        getBindings().getOperationBinding("searchCriteria").execute();
        return null;
    }
    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void cntryDescValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object != null)
        {
            System.out.println("object : "+object);
       
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =bindings.getOperationBinding("validateCntryAction");
            operationBinding.getParamsMap().put("Name",object.toString());
            Object execute = operationBinding.execute();
        
            if(execute.equals(true)) 
            {
                String msg=resolvElDCMsg("#{bundle['MSG.1093']}").toString();
                System.out.println("\n\n--------------Came in bean and show validation ----------\n\n");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,""));
            }
        }
        
       /* System.out.println("result is:  "+Integer.parseInt(execute.toString()));
       int j= Integer.parseInt(execute.toString());
     
       if(j>0) {
           String msg="Country with the same name found.Please Select other country!";
           
           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,null));
       }*/
    }

    public String deleteCntry() {
      
        
        showPopup(popupBindDelete, true);
        return null;
    }

    public String cancel() {
        mode="V";
        CNMode="F";
        countryVarTrans.setDisabled(true);
        tableBind.setRowSelection("single");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        return null;
    }

    public String addCurr() {
      
       
       // tableBind.setRowSelection("none");
      /*  BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("CreateInsert1");
        operationBinding.execute();
*/
  //      getBindings().getOperationBinding("Execute").execute();
        getBindings().getOperationBinding("CreateInsert1").execute();
       showPopup(popupbind, true);
        return null;
    }

    public String editCurr() {
      
        //tableBind.setRowSelection("none");
        showPopup(popupbind, true);
        return null;
    }

    public String deleteCurr() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("Delete1");
        operationBinding.execute();
        
      //  BindingContainer bindings = getBindings();
        OperationBinding operationBinding1 =bindings.getOperationBinding("Execute");
        operationBinding1.execute();
        
        showPopup(popupBindDelete, true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(deleteBind);
        return null;
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

    public void setPopupbind(RichPopup popupbind) {
        this.popupbind = popupbind;
    }

    public RichPopup getPopupbind() {
        return popupbind;
    }

    public void popupDialogListnerDL(DialogEvent dialogEvent) {
       // CurrMode="V";
        //LangMode="V";
        if(dialogEvent.getOutcome().name().equals("ok")){
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("Commit");
        operationBinding.execute();
        getBindings().getOperationBinding("Execute2").execute();
        getBindings().getOperationBinding("Execute").execute();
        AdfFacesContext.getCurrentInstance().addPartialTarget(currTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lanTableBind);

        }
       
      
    }

    public void currencyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("CurrencyValidator1");
        System.out.println("Object is:"+object.toString());
        operationBinding.getParamsMap().put("currDesc", object.toString());
        operationBinding.execute();
        int j= Integer.parseInt(operationBinding.getResult().toString());
        System.out.println("value of j is:"+j);
        if(j==1) {
           String msg=resolvElDCMsg("#{bundle['MSG.1094']}").toString();
         System.out.println("in if of bean");
           throw new ValidatorException(new
           FacesMessage(FacesMessage.SEVERITY_ERROR,msg,""));
           
        }
    }

    public void setCntrydescBind(RichInputText cntrydescBind) {
        this.cntrydescBind = cntrydescBind;
    }

    public RichInputText getCntrydescBind() {
        return cntrydescBind;
    }

    public String searchAction() {
        if(transContryNameBind.getValue() != null)
        {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =bindings.getOperationBinding("searchCriteria");
            
            operationBinding.getParamsMap().put("cntryDesc", transContryNameBind.getValue().toString());
            //operationBinding.getParamsMap().put("cntryDesc", cntrydescBind.getValue().toString());
            operationBinding.execute();
        }
        return null;
    }

    public String resetAction() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("searchCriteria");
        //operationBinding.getParamsMap().put("cntryDesc", cntrydescBind.getValue().toString());
        operationBinding.execute();
        cntrydescBind.setValue("");
        transContryNameBind.setValue("");
        return null;
    }

    public String addLang() {
       // LangMode="C";
       // tableBind.setRowSelection("none");
       /* BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("CreateInsert2");
        operationBinding.execute();
        getBindings().getOperationBinding("Execute2").execute();
        */
        getBindings().getOperationBinding("CreateInsert2").execute();
        showPopup(popupbindLang, true);
        return null;
    } 

    public String deleteLang() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("Delete2");
        operationBinding.execute();
        showPopup(popupBindDelete, true);
        return null;
    }

    public String editlang() {
     //   tableBind.setRowSelection("none");
        showPopup(popupbindLang, true);
        return null;
    }

    public void setPopupbindLang(RichPopup popupbindLang) {
        this.popupbindLang = popupbindLang;
    }

    public RichPopup getPopupbindLang() {
        return popupbindLang;
    }

    public void langaugeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("languageValidator1");
        System.out.println("langid in bean"+"----"+object+"---"+bindings);
        System.out.println("ob : "+operationBinding);
        operationBinding.getParamsMap().put("langid",object.toString());                   
        operationBinding.execute();
        System.out.println(operationBinding.getResult());
        int j= Integer.parseInt(operationBinding.getResult().toString());
        System.out.println("ddd"+j);
        if(j==1) {
           String msg= resolvElDCMsg("#{bundle['MSG.1095']}").toString();
         
           throw new ValidatorException(new
           FacesMessage(FacesMessage.SEVERITY_ERROR,msg,""));
           
        }
    }

    public void setLangidBind(RichPanelLabelAndMessage langidBind) {
        this.langidBind = langidBind;
    }

    public RichPanelLabelAndMessage getLangidBind() {
        return langidBind;
    }

    public void setLangBind(RichSelectOneChoice langBind) {
        this.langBind = langBind;
    }

    public RichSelectOneChoice getLangBind() {
        return langBind;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public String editCntry() {
        mode="C";
        countryVarTrans.setDisabled(true);
        CNMode="F";
        tableBind.setRowSelection("none");
        return null;
    }

    public void setCurrMode(String CurrMode) {
        this.CurrMode = CurrMode;
    }

    public String getCurrMode() {
        return CurrMode;
    }

    public void setLangMode(String LangMode) {
        this.LangMode = LangMode;
    }

    public String getLangMode() {
        return LangMode;
    }

    public void setTableBind(RichTable tableBind) {
        this.tableBind = tableBind;
    }

    public RichTable getTableBind() {
        return tableBind;
    }

    public void popupDeleteDL(DialogEvent dialogEvent) {
        System.out.println("\n-------Came in Dialog Listener------\n");
      /*  if (dialogEvent.getOutcome().name().equalsIgnoreCase("Yes")) {

            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();


            if (operationBinding.getErrors().isEmpty()) {
                String message1 = resolvElDCMsg("#{bundle['MSG.142']}").toString();

                FacesMessage message = new FacesMessage(message1);
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } else {
               String message1 = resolvElDCMsg("#{bundle['MSG.662']}").toString();
                FacesMessage message = new FacesMessage(message1);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);

            }

        } 
        if(dialogEvent.getOutcome().name().equalsIgnoreCase("No")){
                
            BindingContainer bindings = getBindings();
             
            DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppCntry1Iterator");
            oracle.jbo.Key parentKey = parentIter.getCurrentRow().getKey();
            OperationBinding createOpBAddress = bindings.getOperationBinding("Rollback");
            createOpBAddress.execute();
            OperationBinding OpBAddress = bindings.getOperationBinding("Execute1");
            OpBAddress.execute();
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

            
            

        }*/
      if(dialogEvent.getOutcome().name().equalsIgnoreCase("Yes")){
          System.out.println("\n\n---------Came in OK-----------\n\n");
          BindingContainer bindings = getBindings();
          OperationBinding operationBinding =bindings.getOperationBinding("Delete");
          operationBinding.execute();
          getBindings().getOperationBinding("Commit").execute();
          getBindings().getOperationBinding("searchCriteria").execute();
          getBindings().getOperationBinding("Execute1").execute();
          
          String message1 = resolvElDCMsg("#{bundle['MSG.142']}").toString();
           FacesMessage message = new FacesMessage(message1);
           message.setSeverity(FacesMessage.SEVERITY_INFO);
           FacesContext fc = FacesContext.getCurrentInstance();
           fc.addMessage(null, message);
      }
    }

    public void setPopupBindDelete(RichPopup popupBindDelete) {
        this.popupBindDelete = popupBindDelete;
    }

    public RichPopup getPopupBindDelete() {
        return popupBindDelete;
    }

    public void setDeleteBind(RichCommandImageLink deleteBind) {
        this.deleteBind = deleteBind;
    }

    public RichCommandImageLink getDeleteBind() {
        return deleteBind;
    }

    public void popupCancelListner(PopupCanceledEvent popupCanceledEvent) {
        System.out.println("in popup cancel listner"+popupCanceledEvent+"---"+popupCanceledEvent.toString());
     
       // BindingContainer bindings = getBindings();
        //OperationBinding operationBinding =bindings.getOperationBinding("Rollback");
        //operationBinding.execute();
        //
    /*    
        BindingContainer bindings = getBindings();
         if(bindings!=null)
         {
        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppCntry1Iterator");
       oracle.jbo.Key parentKey = parentIter.getCurrentRow().getKey();
        OperationBinding createOpBAddress = bindings.getOperationBinding("Rollback");
        createOpBAddress.execute();
        OperationBinding OpBAddress = bindings.getOperationBinding("Execute1");
        OpBAddress.execute();
        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

        
        
        
         }
      */
    BindingContainer bindings = getBindings();
    OperationBinding operationBinding =bindings.getOperationBinding("Rollback");
    operationBinding.execute();
    getBindings().getOperationBinding("Execute1").execute();
      
    }

    public void setTransContryNameBind(RichInputListOfValues transContryNameBind) {
        this.transContryNameBind = transContryNameBind;
    }

    public RichInputListOfValues getTransContryNameBind() {
        return transContryNameBind;
    }

    public void transCountyryNameValidate(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object != null)
        {
            System.out.println("object : "+object);
        
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =bindings.getOperationBinding("validateCntryAction");
            operationBinding.getParamsMap().put("Name",object.toString());
            operationBinding.execute();
            Object execute =  operationBinding.getResult();
            if(execute.equals(true)) 
            {
                String msg= resolvElDCMsg("#{bundle['MSG.1093']}").toString();
                System.out.println("\n\n--------------Came in bean and show validation ----------\n\n");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,""));
            }
        }
    }

    public void setCNMode(String CNMode) {
        this.CNMode = CNMode;
    }

    public String getCNMode() {
        return CNMode;
    }

    public void setCountryVarTrans(RichInputListOfValues countryVarTrans) {
        this.countryVarTrans = countryVarTrans;
    }

    public RichInputListOfValues getCountryVarTrans() {
        return countryVarTrans;
    }

    public void setCurrTableBind(RichTable currTableBind) {
        this.currTableBind = currTableBind;
    }

    public RichTable getCurrTableBind() {
        return currTableBind;
    }

    public void setLanTableBind(RichTable lanTableBind) {
        this.lanTableBind = lanTableBind;
    }

    public RichTable getLanTableBind() {
        return lanTableBind;
    }
}
