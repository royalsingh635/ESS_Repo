package globalAttributeSetup.view.bean;


import globalAttributeSetup.model.module.GlobalAttributeSetupAMImpl;

import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;




import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class GlobalAttributeSetup {
    private RichPopup pop;
    private RichPopup detailPop;
    private RichPopup deletePop;
    private RichPopup deletePopDetail;
    private RichCommandButton deleteButton;
    private RichCommandButton editButton;
    private RichTable table1;
    private RichDialog dial;
    private RichInputText tynm;
    private  Integer count ;

    public GlobalAttributeSetup() {
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


    public void create(ActionEvent actionEvent) {
        BindingContainer bindings1 = getBindings();
        OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute");
        operationBinding1.execute();
        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        pop.setResetEditableValues(RichPopup.RESET_EDITABLE_VALUES_WHEN_CANCELED);
        pop.clearInitialState();
        pop.clearCachedClientIds();
     //   tynm.setValue("");
        showPopup(pop, true);
    }


    public void popCancelListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();


        BindingContainer bindings1 = getBindings();
        OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute");
        operationBinding1.execute();
        pop.setResetEditableValues(RichPopup.RESET_EDITABLE_VALUES_WHEN_CANCELED);
        pop.clearInitialState();
        pop.clearCachedClientIds();
   AdfFacesContext.getCurrentInstance().addPartialTarget(popupCanceledEvent.getComponent());
    }

    public void setPop(RichPopup pop) {
        this.pop = pop;
    }


    public RichPopup getPop() {
        return pop;
    }


    public void dialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();


            BindingContainer bindings1 = getBindings();
            OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute");
            operationBinding1.execute();

            if(operationBinding.getErrors().isEmpty()){
                FacesMessage message = new FacesMessage("Record Saved Successfully!");
                message.setSeverity(FacesMessage.SEVERITY_INFO);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
            else{
                BindingContainer bindings3 = getBindings();
                OperationBinding operationBinding3 = bindings3.getOperationBinding("Rollback");
                operationBinding3.execute();
                BindingContainer bindings2 = getBindings();
                OperationBinding operationBinding2 = bindings2.getOperationBinding("Execute");
                operationBinding2.execute();
                
                /* pop.setResetEditableValues(RichPopup.RESET_EDITABLE_VALUES_WHEN_CANCELED);
                pop.clearInitialState();
                pop.clearCachedClientIds(); */
                    
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(dialogEvent.getComponent());

        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();

            pop.setResetEditableValues(RichPopup.RESET_EDITABLE_VALUES_WHEN_CANCELED);
           /*  BindingContainer bindings1 = getBindings();
            OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute");
            operationBinding1.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(pop); */
           /*  pop.clearInitialState();
            pop.clearCachedClientIds();
            refreshPage();
            reloadThePage();
            tynm.setValue(""); */
           
        }
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(dialogEvent.getComponent());


    }
    
    
    /* public static void refreshPage() {
    FacesContext fc = FacesContext.getCurrentInstance();
    String refreshpage = fc.getViewRoot().getViewId();
    ViewHandler ViewH = fc.getApplication().getViewHandler();
    UIViewRoot UIV = ViewH.createView(fc, refreshpage);
    UIV.setViewId(refreshpage);
    fc.setViewRoot(UIV);
    }
    
    public void reloadThePage() {
    FacesContext fContext = FacesContext.getCurrentInstance();
    String viewId = fContext.getViewRoot().getViewId();
    String actionUrl = fContext.getApplication().getViewHandler().getActionURL(fContext, viewId);
    System.err.println("Problem trying to reload the page:1");
    try {
    ExternalContext eContext = fContext.getExternalContext();
    String resourceUrl = actionUrl; //eContext.encodeResourceURL(actionUrl);
    // Use the action URL directly since the encoding a resource URL will NPE in isEmailablePage()
    System.out.println("resourceUrl-----"+resourceUrl);
    eContext.redirect(resourceUrl);
    } catch (IOException ioe) {
    System.err.println("Problem trying to reload the page:");
    ioe.printStackTrace();
    }

    } */




    public void edit(ActionEvent actionEvent) {
        showPopup(pop, true);
    }

    public void detailCreate(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
        operationBinding.execute();
        showPopup(detailPop, true);
        
    }

    public void detailEdit(ActionEvent actionEvent) {
        showPopup(detailPop, true);
    }


    public void setDetailPop(RichPopup detailPop) {
        this.detailPop = detailPop;
    }

    public RichPopup getDetailPop() {
        return detailPop;
    }

    public void detailPopUpCancelListener(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();


        BindingContainer bindings1 = getBindings();
        OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute1");
        operationBinding1.execute();
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(popupCanceledEvent.getComponent());

    }

    public void detailDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {

            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();



            BindingContainer bindings1 = getBindings();
            OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute1");
            operationBinding1.execute();
            if(operationBinding.getErrors().isEmpty()){
                FacesMessage message = new FacesMessage("Record Saved Successfully!");
                message.setSeverity(FacesMessage.SEVERITY_INFO);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
            


        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();


           /*  BindingContainer bindings1 = getBindings();
            OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute1");
            operationBinding1.execute(); */
        }
AdfFacesContext.getCurrentInstance().addPartialTarget(dialogEvent.getComponent());
    }


    public void delete(ActionEvent actionEvent) {

        showPopup(deletePop, true);
    }

    public void DeleteDialogListener(DialogEvent dialogEvent) {


        if (dialogEvent.getOutcome().name().equals("yes")) {
            if (rowcount().intValue() == 0) {
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Delete");
                operationBinding.execute();

                BindingContainer bindingsa = getBindings();
                OperationBinding operationBindings = bindingsa.getOperationBinding("Commit");
                operationBindings.execute();

                BindingContainer binding = getBindings();
                OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
                createOpBAddress.execute();
                
                AdfFacesContext.getCurrentInstance().addPartialTarget(deleteButton);

                if(operationBinding.getErrors().isEmpty()){
                    FacesMessage message = new FacesMessage("Record Deleted Successfully!");
                    message.setSeverity(FacesMessage.SEVERITY_INFO);

                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(deleteButton);
                } 
            } else {
                FacesMessage message = new FacesMessage("Child Record Found! Can not delete");
                message.setSeverity(FacesMessage.SEVERITY_WARN);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                //deletePop.cancel();
                deletePop.hide();
            }


        } else if (dialogEvent.getOutcome().name().equals("no")) {


        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(dialogEvent.getComponent());

    }

    public static Long rowcount() {
        return (Long)resolveElExp("#{bindings.AppDsAtt2.estimatedRowCount}");
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


    public void detailDelete(ActionEvent actionEvent) {
        showPopup(deletePopDetail, true);
    }

    public void DeleteDialogListenerDetail(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("yes")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Delete1");
            operationBinding.execute();
            BindingContainer bindingsa = getBindings();
            OperationBinding operationBindings = bindingsa.getOperationBinding("Commit");
            operationBindings.execute();
            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Execute1");
            createOpBAddress.execute();
            //AdfFacesContext.getCurrentInstance().addPartialTarget(deleteButton);
            //AdfFacesContext.getCurrentInstance().addPartialTarget(editButton);
            if(operationBinding.getErrors().isEmpty()){
                FacesMessage message = new FacesMessage("Record Deleted Successfully!");
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                AdfFacesContext.getCurrentInstance().addPartialTarget(deleteButton);
                AdfFacesContext.getCurrentInstance().addPartialTarget(editButton);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
        } else if (dialogEvent.getOutcome().name().equals("no")) {


        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(dialogEvent.getComponent());

    }

    public void save(ActionEvent actionEvent) {


        BindingContainer bindings2 = getBindings();
        OperationBinding operationBinding2 = bindings2.getOperationBinding("Commit");
        operationBinding2.execute();

        BindingContainer bindings1 = getBindings();
        OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute");
        operationBinding1.execute();

        FacesMessage message = new FacesMessage("Record Saved Successfully!");
        message.setSeverity(FacesMessage.SEVERITY_INFO);

        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message);

    }

    public void detailSave(ActionEvent actionEvent) {


        BindingContainer bindings2 = getBindings();
        OperationBinding operationBinding2 = bindings2.getOperationBinding("Commit");
        operationBinding2.execute();

        BindingContainer bindings1 = getBindings();
        OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute1");
        operationBinding1.execute();

        FacesMessage message = new FacesMessage("Record Saved Successfully!");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        // throw new ValidatorException(message);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message);


    }


    public void setDeletePop(RichPopup deletePop) {
        this.deletePop = deletePop;
    }

    public RichPopup getDeletePop() {
        return deletePop;
    }

    public void setDeletePopDetail(RichPopup deletePopDetail) {
        this.deletePopDetail = deletePopDetail;
    }

    public RichPopup getDeletePopDetail() {
        return deletePopDetail;
    }

    public void setDeleteButton(RichCommandButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    public RichCommandButton getDeleteButton() {
        return deleteButton;
    }

    public void setEditButton(RichCommandButton editButton) {
        this.editButton = editButton;
    }

    public RichCommandButton getEditButton() {
        return editButton;
    }

    public void setTable1(RichTable table1) {
        this.table1 = table1;
    }

    public RichTable getTable1() {
        return table1;
    }

    public void setDial(RichDialog dial) {
        this.dial = dial;
    }

    public RichDialog getDial() {
        return dial;
    }

    public void setTynm(RichInputText tynm) {
        this.tynm = tynm;
    }

    public RichInputText getTynm() {
        return tynm;
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
        private GlobalAttributeSetupAMImpl getAm() {
               
            FacesContext fc = FacesContext.getCurrentInstance();
            Application app = fc.getApplication();
            ExpressionFactory elFactory = app.getExpressionFactory();
            ELContext elContext = fc.getELContext();
            ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data.GlobalAttributeSetupAMDataControl.dataProvider}", Object.class);
            return (GlobalAttributeSetupAMImpl)valueExp.getValue(elContext);
            }

    
    public void AttTypeNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object!=null){
        String typNm=object.toString();
        
        
        ViewObject v=getAm().getAppDsAttType1();   //1
        Row r1=v.getCurrentRow();
        
                int totalCount=v.getRowCount();  //get ALL rows
                int rangeSize=v.getRangeSize(); //all in range
                v.setRangeSize(totalCount);
                Row[] rArray=v.getAllRowsInRange();
               
                //check for valid language name
                        String expression = "^(?:(?>[A-Za-z ]+)(\\.|\\-(?!\\.|\\-|$))?)*$";
                        CharSequence inputStr = typNm;
                        Pattern pattern = Pattern.compile(expression);
                        Matcher matcher = pattern.matcher(inputStr);
                        String error="Invalid Type Name";
                       
                        if (matcher.matches()) {
                        }else {
                                       throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
                        }
                       
               
                Row cRow=v.getCurrentRow();
                //RowSetIterator rsi=v.createRowSetIterator(null);
                int count=0;
                //System.out.println("Length before:"+rArray.length);
                String currLang="";
               
                for(Row r:rArray){
                            
                    if(!r.equals(cRow)){
                        try{
                        currLang=r.getAttribute("AttTypeNm").toString();  //2.
                        }
                        catch(NullPointerException npe){
                            System.out.println("NPE:"+npe);
                            currLang="";
                        }
                        //System.out.println("Description:::"+currLang);
                             if(currLang.equalsIgnoreCase(typNm)){
                                count=count+1;
                             }
                    } 
                               
                }
                v.setRangeSize(rangeSize);  //set to original range
                     
              
                    if(count>0){
                    String msg2="Duplicate Record found";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                    }
              
            }
    }

    public void attributeTypeNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if(object!=null){
        String typNm=object.toString();
        
        
        ViewObject v=getAm().getAppDsAtt2();   //1
        Row r1=v.getCurrentRow();
        
                int totalCount=v.getRowCount();  //get ALL rows
                int rangeSize=v.getRangeSize(); //all in range
                v.setRangeSize(totalCount);
                Row[] rArray=v.getAllRowsInRange();
               
                //check for valid language name
                       // String expression = "^(?:(?>[A-Za-z ]+)(\\.|\\-(?!\\.|\\-|$))?)*$";
                       String expression = "([a-zA-Z0-9]+)(([ ])([a-zA-Z0-9]+))*"; 
                        CharSequence inputStr = typNm;
                        Pattern pattern = Pattern.compile(expression);
                        Matcher matcher = pattern.matcher(inputStr);
                        String error="Invalid Attribute Name";
                       
                        if (matcher.matches()) {
                        }else {
                                       throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
                        }
                       
               
                Row cRow=v.getCurrentRow();
                //RowSetIterator rsi=v.createRowSetIterator(null);
                int count=0;
                //System.out.println("Length before:"+rArray.length);
                String currLang="";
               
                for(Row r:rArray){
                            
                    if(!r.equals(cRow)){
                        try{
                        currLang=r.getAttribute("AttNm").toString();  //2.
                        }
                        catch(NullPointerException npe){
                           // System.out.println("NPE:"+npe);
                            currLang="";
                        }
                        //System.out.println("Description:::"+currLang);
                             if(currLang.equalsIgnoreCase(typNm)){
                                count=count+1;
                             }
                    } 
                               
                }
                v.setRangeSize(rangeSize);  //set to original range
                     
              
                    if(count>0){
                    String msg2="Duplicate Record found";
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message2);
                    }
              
            }
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        if(count == null)
        {
                getBindings().getOperationBinding("on_load_page").execute();
                count = Integer.parseInt(getBindings().getOperationBinding("on_load_page").getResult().toString());
               // System.out.println("%%%%%%% "+count);
            }
        return count;
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

   
}

