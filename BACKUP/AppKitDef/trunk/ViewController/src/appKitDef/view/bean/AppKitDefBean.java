package appKitDef.view.bean;

import appKitDef.model.service.KitDefAppAMImpl;

import appKitDef.model.views.AppKitVORowImpl;


import java.sql.SQLException;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.context.AdfFacesContext;


import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class AppKitDefBean {
    private RichCommandButton saveButton;
    private String mode = "V";
    private RichInputListOfValues inputItm;
    private RichInputListOfValues itmInput;
    private RichPopup deleteOutputPopup;
    private RichTable tableBind;
    private RichPanelFormLayout formBind;
    private boolean form_readonly=true;
    private boolean output_itm;
    private boolean outputitm_id;
    private RichPanelGroupLayout searchPanel;
    private RichInputListOfValues inputItmBind;
    private String disable_link="E";
    private String button_disable = "D";
    private RichInputListOfValues outputItmBind;
    private RichPopup deleteItemPopUp;
    private RichPanelBox panelBoxBind;
    private RichPanelFormLayout outputFormBind;
    private RichCommandImageLink backBind;

    public AppKitDefBean() {
    }
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void createOutput(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        this.output_itm=false;
        this.outputitm_id=true;
        setButton_disable("A");
       
    }

    public void cancel(ActionEvent actionEvent) {
       
    }

    public void createDetail(ActionEvent actionEvent) {
       
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

    public void setSaveButton(RichCommandButton saveButton) {
        this.saveButton = saveButton;
    }

    public RichCommandButton getSaveButton() {
        return saveButton;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public String createDtlAction() {

        // setOutMode("V");
        //inptSerializeflag

        OperationBinding editOp = getBindings().getOperationBinding("isItemDeletable");
        editOp.execute();
        String ret = "Y";
        if (editOp.getResult() != null) {
            ret = editOp.getResult().toString();
        }
        //System.out.println("ret-----------"+ret);
        if ("N".equalsIgnoreCase(ret)) {
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage msg =new FacesMessage(FacesMessage.SEVERITY_ERROR, "Output Item used in another application . You can't add more Input Item .",
                             null);
            fc.addMessage(null, msg);
        } else if ("Y".equalsIgnoreCase(ret)) {
                      AdfFacesContext.getCurrentInstance().addPartialTarget(outputItmBind);
                                      BindingContainer bindings = getBindings();
                                      OperationBinding operationBinding = bindings.getOperationBinding("Createwithparameters");
                                      operationBinding.execute();
                                      this.form_readonly = false;
                                      this.output_itm = true;
                                      this.outputitm_id = false;
                                      setMode("C");
                  }
        return null;
    }

    public void edit(ActionEvent actionEvent) {
        OperationBinding editOp = getBindings().getOperationBinding("isItemDeletable");
        editOp.execute();
        String ret = "Y";
        if(editOp.getResult()!=null){
            ret = editOp.getResult().toString();
        }
        if("N".equalsIgnoreCase(ret)){
            FacesContext fc=FacesContext.getCurrentInstance();
            FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_ERROR,"Output Item used in another application . You can't Edit it .",null);
            fc.addMessage(null, msg);
        }else if("Y".equalsIgnoreCase(ret)){
        
        this.form_readonly=false;
        this.output_itm = true;
        this.outputitm_id = false;
        setMode("C");
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
  
    public void inputItmVCL(ValueChangeEvent vCE) {
        /**------------For duplicate input item validation-----------------------*/
      //  String inputItm=(String)object;
//        String inputItm=vCE.getNewValue().toString();
//        String inputItmDesc=null;
//        KitDefAppAMImpl am = (KitDefAppAMImpl)resolvElDC("KitDefAppAMDataControl");
//        ViewObjectImpl v = am.getAppKit1();
//        Row r1 = v.getCurrentRow();
//         int totalCount=v.getRowCount();  //get ALL rows
//         int rangeSize=v.getRangeSize(); //all in range
//         v.setRangeSize(totalCount);
//         Row[] rArray=v.getAllRowsInRange();
//         Row cRow=v.getCurrentRow();
//         int count=0;
//         String itemIdCur="";
//         for(Row r:rArray){
//             if(!r.equals(cRow)){
//                 try{
//                         itemIdCur=r.getAttribute("InputItmId").toString();   
//                    }
//                 catch(NullPointerException npe){
//                         System.out.println("NPE:"+npe);
//                    itemIdCur="";
//                    }
//                 if(itemIdCur!=null){
//                        Row [] xx=am.getLovInputItmId1().getFilteredRows("ItmId", itemIdCur);
//                        if(xx.length>0){
//                        inputItmDesc= xx[0].getAttribute("ItmDesc").toString();
//                     }
//                     if(inputItm.equalsIgnoreCase(inputItmDesc)){
//                         count=count+1;
//                  }
//              }  
//                                
//          } 
//        }
         
//         
//        v.setRangeSize(rangeSize);  //set to original range
//                 if(count>0){
//                      FacesContext fc=FacesContext.getCurrentInstance();
//                      FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvElDCMsg("#{bundle['MSG.46']}").toString(),null);
//                      fc.addMessage(itmInput.getClientId(), msg);
//                  }
    }

    public void setInputItm(RichInputListOfValues inputItm) {
        this.inputItm = inputItm;
    }

    public RichInputListOfValues getInputItm() {
        return inputItm;
    }

    public void setItmInput(RichInputListOfValues itmInput) {
        this.itmInput = itmInput;
    }

    public RichInputListOfValues getItmInput() {
        return itmInput;
    }

    public void deleteInuptItm(ActionEvent actionEvent) {
        OperationBinding editOp = getBindings().getOperationBinding("isItemDeletable");
        editOp.execute();
        String ret = "Y";
        if(editOp.getResult()!=null){
            ret = editOp.getResult().toString();
        }
        System.out.println("ret1234-----------"+ret);
        if("N".equalsIgnoreCase(ret)){
            FacesContext fc=FacesContext.getCurrentInstance();
            FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_ERROR,"Output Item used in another application . You can't Delete it .",null);
            fc.addMessage(null, msg);
        }else if("Y".equalsIgnoreCase(ret)){
        KitDefAppAMImpl am = (KitDefAppAMImpl)resolvElDC("KitDefAppAMDataControl");
        ViewObject vo = am.getAppKit1();
        Long totalrows = vo.getEstimatedRowCount();
        Integer totRow = totalrows.intValue();
        System.out.println("inside input item delete 1");
        if (totRow == 1 ) {
            showPopup(deleteItemPopUp, true);
        } else {
            System.out.println("inside input item delete 2");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete1");
        BindingContainer bindings1 = getBindings();
        OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute1");
        operationBinding.execute();
        operationBinding1.execute();
        }
       // this.form_readonly = true;
       // setMode("C");
        }
    }

    public String editKitDefAction() {
        KitDefAppAMImpl am = (KitDefAppAMImpl)resolvElDC("KitDefAppAMDataControl");
        Object outputItmBind=am.getAppKitSearch1().getCurrentRow().getAttribute("OutputItmId");
        
        am.getAppKitNew1().setWhereClause("OUTPUT_ITM_ID = '"+outputItmBind.toString()+"'");
        am.getAppKitNew1().executeQuery();
        this.output_itm = true;
        this.outputitm_id = false;
        return "Search";
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
    public void DeleteDialogListener(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("yes")) {
           
     /*        BindingContainer bindings = getBindings();
                       
            KitDefAppAMImpl am = (KitDefAppAMImpl)resolvElDC("KitDefAppAMDataControl");
                       ViewObject vo=am.getAppKit1();
                       int totalCount=vo.getRowCount();  //get ALL rows
                              vo.setRangeSize(totalCount);
                              Row[] rArray=vo.getAllRowsInRange();
                       for(Row r:rArray){
                       r.remove();
                       }
                       vo.executeQuery();

            OperationBinding operationBinding = bindings.getOperationBinding("Delete");
            operationBinding.execute();
            BindingContainer bindings1 = getBindings();
            OperationBinding operationBinding2 = bindings.getOperationBinding("Commit");
            operationBinding2.execute();
            OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute");
            operationBinding1.execute();
            OperationBinding operationBinding5 = bindings.getOperationBinding("Rollback");
            operationBinding5.execute();
            OperationBinding operationBinding3 = bindings1.getOperationBinding("Execute1");
            operationBinding3.execute();
            
            //FacesContext facesContext = FacesContext.getCurrentInstance();
            //UIViewRoot root = facesContext.getViewRoot();
            /**Pass cb1(buttonId) if page is not in taskflow, else if page is inside region then pass rgionid:buttonId
            //RichCommandImageLink link=(RichCommandImageLink)root.findComponent("r1:cil3");
            //ActionEvent actionEvent = new ActionEvent(link);
            //actionEvent.queue();
            
            setMode("V");
            AdfFacesContext.getCurrentInstance().addPartialTarget(tableBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(formBind);
      */       
        } else if (dialogEvent.getOutcome().name().equals("no")) {
           

        }
        
    }


    public void setDeleteOutputPopup(RichPopup deleteOutputPopup) {
        this.deleteOutputPopup = deleteOutputPopup;
    }

    public RichPopup getDeleteOutputPopup() {
        return deleteOutputPopup;
    }

    public void saveItm(ActionEvent actionEvent) {
        String optSrFlag = null;
        OperationBinding isserialized = getBindings().getOperationBinding("optserializeflag");
        isserialized.execute();
        if (isserialized.getResult() != null) {
            optSrFlag = isserialized.getResult().toString();
        }
        
        System.out.println("output serialized is " + optSrFlag);
        if (optSrFlag != null && optSrFlag.equalsIgnoreCase("N")) {
            OperationBinding inpserialized = getBindings().getOperationBinding("inptSerializeflag");
            inpserialized.execute();
            String inptRslt = null;
            if (inpserialized.getResult() != null) {
                inptRslt = inpserialized.getResult().toString();
            }
            System.out.println("inptitem rslt is " + inptRslt);
            if (inptRslt!=null && inptRslt.equalsIgnoreCase("Y")) {
                FacesContext fc = FacesContext.getCurrentInstance();
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Input Item Must be Non-serialized", null);
                fc.addMessage(itmInput.getClientId(), msg);

            } else {
                System.out.println("input item is also non serialized i m ok with it");
                
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();
                if(operationBinding.getErrors().isEmpty()){
                    FacesContext fc=FacesContext.getCurrentInstance();
                    FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_INFO,resolvElDCMsg("#{bundle['MSG.91']}").toString(),null);
                    fc.addMessage(itmInput.getClientId(), msg);
                }
                this.form_readonly = true;
                this.output_itm = true;
                this.outputitm_id = false;
                setMode("V");
                setButton_disable("D");
            }

        } else if (optSrFlag != null && optSrFlag.equalsIgnoreCase("Y")) {
          
            
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            if(operationBinding.getErrors().isEmpty()){
                FacesContext fc=FacesContext.getCurrentInstance();
                FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_INFO,resolvElDCMsg("#{bundle['MSG.91']}").toString(),null);
                fc.addMessage(null, msg);
            }
            this.form_readonly = true;
            this.output_itm = true;
            this.outputitm_id = false;
            setMode("V");
            setButton_disable("D");      
        }
        
        
    }

    public void setTableBind(RichTable tableBind) {
        this.tableBind = tableBind;
    }

    public RichTable getTableBind() {
        return tableBind;
    }

    public String deleteMaster() {
        OperationBinding editOp = getBindings().getOperationBinding("isItemDeletable");
        editOp.execute();
        String ret = "Y";
        if(editOp.getResult()!=null){
            ret = editOp.getResult().toString();
        }
        if("N".equalsIgnoreCase(ret)){
            FacesContext fc=FacesContext.getCurrentInstance();
            FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_ERROR,"Output Item used in another application . You can't Delete it .",null);
            fc.addMessage(null, msg);
        }else if("Y".equalsIgnoreCase(ret)){
        showPopup(deleteOutputPopup, true);
        }
        return null;
    }

    public void setFormBind(RichPanelFormLayout formBind) {
        this.formBind = formBind;
    }

    public RichPanelFormLayout getFormBind() {
        return formBind;
    }

    public void setForm_readonly(boolean form_readonly) {
        this.form_readonly = form_readonly;
    }

    public boolean isForm_readonly() {
        return form_readonly;
    }

    public void setOutput_itm(boolean output_itm) {
        this.output_itm = output_itm;
    }

    public boolean isOutput_itm() {
        return output_itm;
    }

    public void inputItmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String inputItm=(String)object;
        String ouputItmDesc=null;
        String outputItm=null;
        String inputItmDesc=null;
        KitDefAppAMImpl am = (KitDefAppAMImpl)resolvElDC("KitDefAppAMDataControl");
        ViewObjectImpl v = am.getAppKit1();
        Row r1 = v.getCurrentRow();
        try{
        outputItm=(String)r1.getAttribute("OutputItmId");
            }catch(NullPointerException npe){
               
        }
        if(outputItm!=null){
           
            Row [] xx=am.getLovInputItmId1().getFilteredRows("ItmId", outputItm);
            if(xx.length>0){
            ouputItmDesc= xx[0].getAttribute("ItmDesc").toString();
            }
        }
        if(inputItm.equalsIgnoreCase(ouputItmDesc)){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvElDCMsg("#{bundle['MSG.272']}").toString(),
                                                          null));
        }
       /**------------For duplicate input item validation-----------------------*/
        int totalCount=v.getRowCount();  //get ALL rows
        int rangeSize=v.getRangeSize(); //all in range
        v.setRangeSize(totalCount);
        Row[] rArray=v.getAllRowsInRange();
        Row cRow=v.getCurrentRow();
        int count=0;
        String itemIdCur="";
        for(Row r:rArray){
            if(!r.equals(cRow)){
                try{
                        itemIdCur=r.getAttribute("InputItmId").toString();   
                   }
                catch(NullPointerException npe){
                        System.out.println("NPE:"+npe);
                   itemIdCur="";
                   }
                if(itemIdCur!=null){
                       Row [] xx=am.getLovInputItmId1().getFilteredRows("ItmId", itemIdCur);
                       if(xx.length>0){
                       inputItmDesc= xx[0].getAttribute("ItmDesc").toString();
                    }
                    if(inputItm.equalsIgnoreCase(inputItmDesc)){
                        count=count+1;
                 }
             }  
                               
         } 
     }
     v.setRangeSize(rangeSize);  //set to original range
                if(count>0){
                       String msg2=resolvElDCMsg("#{bundle['MSG.46']}").toString();
                       FacesMessage message2 = new FacesMessage(msg2);
                       message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                       throw new ValidatorException(message2);
                 }
              
               }
    

    public void setOutputitm_id(boolean outputitm_id) {
        this.outputitm_id = outputitm_id;
    }

    public boolean isOutputitm_id() {
        return outputitm_id;
    }

    public void itmQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
     Number n = (Number)object;
       Number x = new Number(0);
        if (n == null){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvElDCMsg("#{bundle['MSG.273']}").toString(),
                                                          null));
        } else if (n.compareTo(x) <=0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                          resolvElDCMsg("#{bundle['MSG.87']}").toString(), null));
        }
    }

    public void setSearchPanel(RichPanelGroupLayout searchPanel) {
        this.searchPanel = searchPanel;
    }

    public RichPanelGroupLayout getSearchPanel() {
        return searchPanel;
    }
    public void resetAction(ActionEvent actionEvent) {
        AdfFacesContext fctx = AdfFacesContext.getCurrentInstance();
        resetValueInputItems(fctx, searchPanel);
        KitDefAppAMImpl am = (KitDefAppAMImpl)resolvElDC("KitDefAppAMDataControl");
        ViewObject itemSearch = am.getAppKitSearch1();
        itemSearch.executeQuery();
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams");
        operationBinding.execute();
        
    }

    private void resetValueInputItems(AdfFacesContext adfFacesContext, UIComponent component) {
        List<UIComponent> items = component.getChildren();
        for (UIComponent item : items) {

            resetValueInputItems(adfFacesContext, item);

            if (item instanceof RichInputText) {
                RichInputText input = (RichInputText)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputDate) {
                RichInputDate input = (RichInputDate)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichSelectOneChoice) {
                RichSelectOneChoice input = (RichSelectOneChoice)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            } else if (item instanceof RichInputListOfValues) {
                RichInputListOfValues input = (RichInputListOfValues)item;
                if (!input.isDisabled()) {
                    input.resetValue();
                    input.setValue("");
                    adfFacesContext.addPartialTarget(input);
                }
                ;
            }

        }
    }

    public void setInputItmBind(RichInputListOfValues inputItmBind) {
        this.inputItmBind = inputItmBind;
    }

    public RichInputListOfValues getInputItmBind() {
        return inputItmBind;
    }

    public String goToSearch() {
        setMode("V");
        BindingContainer bindings = getBindings();
//        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
  //      operationBinding.execute();
        this.form_readonly = true;
        this.output_itm = false;
        this.outputitm_id = true;
        KitDefAppAMImpl am = (KitDefAppAMImpl)resolvElDC("KitDefAppAMDataControl");
        ViewObject itemSearch=am.getAppKitSearch1();
        itemSearch.setWhereClause(" ");
        itemSearch.executeQuery();
        setDisable_link("E");
        setButton_disable("D");
        return "back";
    }

    public void thisWeekListener(ActionEvent actionEvent) {
        KitDefAppAMImpl am = (KitDefAppAMImpl)resolvElDC("KitDefAppAMDataControl");
        ViewObject itemSearch = am.getAppKitSearch1();
     DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
               java.util.Date currDate = new java.util.Date();
            //   String dateStr = dateFormat.format(currDate);
        Calendar calendar1 = Calendar.getInstance();
           calendar1.add(Calendar.DATE,1);
        java.util.Date date1 = calendar1.getTime();
        String dateStr = dateFormat.format(date1);
        
        Calendar calendar = Calendar.getInstance();
           calendar.add(Calendar.DATE, -7);
        java.util.Date date = calendar.getTime();
        String dateStr1 = dateFormat.format(date);
        oracle.jbo.domain.Date daTime=null;
        oracle.jbo.domain.Date daTime1=null;
        try{
               java.util.Date date2 = dateFormat.parse (dateStr);
                   java.util.Date date3 = dateFormat.parse (dateStr1);
                   java.sql.Date sqldate = new java.sql.Date(date2.getTime());
                   java.sql.Date sqldate1 = new java.sql.Date(date3.getTime());
                    daTime = new  oracle.jbo.domain.Date(sqldate);
                    daTime1 = new  oracle.jbo.domain.Date(sqldate1);
               }catch(ParseException pe){
                   pe.printStackTrace();}
      //itemSearch.setWhereClause("CREATE_DT between trunc('"+date+"') and trunc('"+currDate+"')");
      itemSearch.setWhereClause("CREATE_DT >= to_date('"+daTime1+"','yyyy/mm/dd') and CREATE_DT < to_date('"+daTime+"','yyyy/mm/dd')");
        itemSearch.executeQuery();
        setDisable_link("W");
    }

    public void lastMonthListener(ActionEvent actionEvent) {
        KitDefAppAMImpl am = (KitDefAppAMImpl)resolvElDC("KitDefAppAMDataControl");
        ViewObject itemSearch = am.getAppKitSearch1();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
               java.util.Date currDate = new java.util.Date();
            //   String dateStr = dateFormat.format(currDate);
        Calendar calendar1 = Calendar.getInstance();
           calendar1.add(Calendar.DATE,1);
        java.util.Date date1 = calendar1.getTime();
        String dateStr = dateFormat.format(date1);
        
        Calendar calendar = Calendar.getInstance();
           calendar.add(Calendar.DATE, -30);
        java.util.Date date = calendar.getTime();
        String dateStr1 = dateFormat.format(date);
        oracle.jbo.domain.Date daTime=null;
        oracle.jbo.domain.Date daTime1=null;
        try{
               java.util.Date date2 = dateFormat.parse (dateStr);
                   java.util.Date date3 = dateFormat.parse (dateStr1);
                   java.sql.Date sqldate = new java.sql.Date(date2.getTime());
                   java.sql.Date sqldate1 = new java.sql.Date(date3.getTime());
                    daTime = new  oracle.jbo.domain.Date(sqldate);
                    daTime1 = new  oracle.jbo.domain.Date(sqldate1);
               }catch(ParseException pe){
                   pe.printStackTrace();
                   }
        //itemSearch.setWhereClause("CREATE_DT between trunc('"+date+"') and trunc('"+currDate+"')");
        itemSearch.setWhereClause("CREATE_DT >= to_date('"+daTime1+"','yyyy/mm/dd') and CREATE_DT < to_date('"+daTime+"','yyyy/mm/dd')");
        itemSearch.executeQuery();
        setDisable_link("M");
    }

    public void thisYearListener(ActionEvent actionEvent) {
       KitDefAppAMImpl am = (KitDefAppAMImpl)resolvElDC("KitDefAppAMDataControl");
       ViewObject itemSearch = am.getAppKitSearch1();
       DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
              java.util.Date currDate = new java.util.Date();
           //   String dateStr = dateFormat.format(currDate);
       Calendar calendar1 = Calendar.getInstance();
          calendar1.add(Calendar.DATE,1);
       java.util.Date date1 = calendar1.getTime();
       String dateStr = dateFormat.format(date1);
       
       Calendar calendar = Calendar.getInstance();
          calendar.add(Calendar.DATE, -365);
       java.util.Date date = calendar.getTime();
       String dateStr1 = dateFormat.format(date);
       oracle.jbo.domain.Date daTime=null;
       oracle.jbo.domain.Date daTime1=null;
       try{
              java.util.Date date2 = dateFormat.parse (dateStr);
                  java.util.Date date3 = dateFormat.parse (dateStr1);
                  java.sql.Date sqldate = new java.sql.Date(date2.getTime());
                  java.sql.Date sqldate1 = new java.sql.Date(date3.getTime());
                   daTime = new  oracle.jbo.domain.Date(sqldate);
                   daTime1 = new  oracle.jbo.domain.Date(sqldate1);
              }catch(ParseException pe){
                  pe.printStackTrace();
                  }
    
        //itemSearch.setWhereClause("CREATE_DT between trunc('"+date+"') and trunc('"+currDate+"')");
        itemSearch.setWhereClause("CREATE_DT >= to_date('"+daTime1+"','yyyy/mm/dd') and CREATE_DT < to_date('"+daTime+"','yyyy/mm/dd')");
        itemSearch.executeQuery();
        setDisable_link("Y");
    }


    public void setDisable_link(String disable_link) {
        this.disable_link = disable_link;
    }

    public String getDisable_link() {
        return disable_link;
    }

    public void actvValueChangeListener(ValueChangeEvent vce) {
        KitDefAppAMImpl am = (KitDefAppAMImpl)resolvElDC("KitDefAppAMDataControl");
        if (vce.getNewValue() != null) {
            String oldval = vce.getOldValue().toString();

            String newVal = vce.getNewValue().toString();


            ViewObject v1 = am.getAppKit1();
            Row row = v1.getCurrentRow();
            if (newVal.equalsIgnoreCase("true")) {
                row.setAttribute("InactvDt", null);
                row.setAttribute("InactvResn", null);
            } else if (newVal.equalsIgnoreCase("false")) {
                Date dt = (Date)Date.getCurrentDate();
                row.setAttribute("InactvDt", dt);
            }

        }
    }

    public void setButton_disable(String button_disable) {
        this.button_disable = button_disable;
    }

    public String getButton_disable() {
        return button_disable;
    }

    public void searchCreate(ActionEvent actionEvent) {
        setButton_disable("A");
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
    public void outItmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println(" in the validator");
        String OrgId= resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        System.out.println("OrgId:"+OrgId);
        String inputItm=(String)object;
        System.out.println("Entered Value is:"+inputItm);
        String inputItmDesc=null;
        KitDefAppAMImpl am = (KitDefAppAMImpl)resolvElDC("KitDefAppAMDataControl");
        ViewObjectImpl v = am.getAppKitNew1();
         RowQualifier  rq=new   RowQualifier(v);
         rq.setWhereClause("ItmDesc='"+inputItm+"' and OrgId='"+OrgId+"'");
         Row []r=v.getFilteredRows(rq);
        // System.out.println(v.getQuery());
         System.out.println(rq.getExprStr());
         System.out.println("length of array is:"+r.length);
         if(r.length>0) {
                                    String msg2="Duplicate Entry Exist";
                                    FacesMessage message2 = new FacesMessage(msg2);
                                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    throw new ValidatorException(message2);
             
         }
//        /**------------For duplicate input item validation-----------------------*/
//        int totalCount=v.getRowCount();  //get ALL rows
//        int rangeSize=v.getRangeSize(); //all in range
//        v.setRangeSize(totalCount);
//        Row[] rArray=v.getAllRowsInRange();
//        Row cRow=v.getCurrentRow();
//        int count=0;
//        String itemIdCur="";
//        for(Row r:rArray){
//            if(!r.equals(cRow)){
//                try{
//                        itemIdCur=r.getAttribute("OutputItmId").toString(); 
//                   }
//                catch(NullPointerException npe){
//                        System.out.println("NPE:"+npe);
//                   itemIdCur="";
//                   }
//                if(itemIdCur!=null){
//                       Row [] xx=am.getLovInputItmId1().getFilteredRows("ItmId", itemIdCur);
//                       if(xx.length>0){
//                       inputItmDesc= xx[0].getAttribute("ItmDesc").toString();
//                    }
//                    if(inputItm.equalsIgnoreCase(inputItmDesc)){
//                        count=count+1;
//                 }
//             }  
//                               
//         } 
//        }
//        v.setRangeSize(rangeSize);  //set to original range
//                if(count>0){     
//                       String msg2=resolvElDCMsg("#{bundle['MSG.46']}").toString();
//                       FacesMessage message2 = new FacesMessage(msg2);
//                       message2.setSeverity(FacesMessage.SEVERITY_ERROR);
//                       throw new ValidatorException(message2);
//                 }
//              
    }

    public void setOutputItmBind(RichInputListOfValues outputItmBind) {
        this.outputItmBind = outputItmBind;
    }

    public RichInputListOfValues getOutputItmBind() {
        return outputItmBind;
    }

    public void outItmVCL(ValueChangeEvent vce) {
        System.out.println(" in the value change listener");
        String OrgId= resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        System.out.println("OrgId:"+OrgId);
        String inputItm=(String)vce.getNewValue();
        System.out.println("Entered Value is:"+inputItm);
        String inputItmDesc=null;
        KitDefAppAMImpl am = (KitDefAppAMImpl)resolvElDC("KitDefAppAMDataControl");
        ViewObjectImpl v = am.getAppKitNew1();
         RowQualifier  rq=new   RowQualifier(v);
         rq.setWhereClause("ItmDesc='"+inputItm+"' and OrgId='"+OrgId+"'");
         Row []r=v.getFilteredRows(rq);
         //System.out.println(v.getQuery());
         System.out.println(rq.getExprStr());
         System.out.println("length of array is:"+r.length);
         if(r.length>0) {
                                    String msg2="Duplicate Entry Exist";
                                    FacesMessage message2 = new FacesMessage(msg2);
                                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    throw new ValidatorException(message2);
             
         }
//        String inputItm=vce.getNewValue().toString();
//        String inputItmDesc=null;
//        KitDefAppAMImpl am = (KitDefAppAMImpl)resolvElDC("KitDefAppAMDataControl");
//        ViewObjectImpl v = am.getAppKitNew1();
//        /**------------For duplicate input item validation-----------------------*/
//        int totalCount=v.getRowCount();  //get ALL rows
//        int rangeSize=v.getRangeSize(); //all in range
//        v.setRangeSize(totalCount);
//        Row[] rArray=v.getAllRowsInRange();
//        Row cRow=v.getCurrentRow();
//        int count=0;
//        String itemIdCur="";
//        for(Row r:rArray){
//            if(!r.equals(cRow)){
//                try{
//                        itemIdCur=r.getAttribute("OutputItmId").toString(); 
//                   }
//                catch(NullPointerException npe){
//                        System.out.println("NPE:"+npe);
//                   itemIdCur="";
//                   }
//                if(itemIdCur!=null){
//                       Row [] xx=am.getLovInputItmId1().getFilteredRows("ItmId", itemIdCur);
//                       if(xx.length>0){
//                       inputItmDesc= xx[0].getAttribute("ItmDesc").toString();
//                    }
//                    if(inputItm.equalsIgnoreCase(inputItmDesc)){
//                        count=count+1;
//                 }
//             }  
//                               
//         } 
//        }
//        v.setRangeSize(rangeSize);  //set to original range
//                if(count>0){
//                     FacesContext fc=FacesContext.getCurrentInstance();
//                     FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_ERROR,resolvElDCMsg("#{bundle['MSG.91']}").toString(),null);
//                     fc.addMessage(outputItmBind.getClientId(), msg);
//                 }
//        AdfFacesContext.getCurrentInstance().addPartialTarget(outputItmBind);
    }

    public void setDeleteItemPopUp(RichPopup deleteItemPopUp) {
        this.deleteItemPopUp = deleteItemPopUp;
    }

    public RichPopup getDeleteItemPopUp() {
        return deleteItemPopUp;
    }

    public void DeleteDialogListenerItem(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
            /* OperationBinding operationBinding = getBindings().getOperationBinding("Delete1");
            operationBinding.execute();
            OperationBinding operationBinding1 = getBindings().getOperationBinding("Execute1");
            operationBinding1.execute();
            OperationBinding obindparent = getBindings().getOperationBinding("Delete");
            obindparent.execute();
            
         
            OperationBinding obindcom = getBindings().getOperationBinding("Commit");
            obindcom.execute();
            OperationBinding operationBinding5 = getBindings().getOperationBinding("Rollback");
            operationBinding5.execute();
            OperationBinding executem = getBindings().getOperationBinding("Execute");
            executem.execute();
            OperationBinding operationBinding3 = getBindings().getOperationBinding("Execute1");
            operationBinding3.execute();
            System.out.println("region id for back button is "+backBind.getClientId(FacesContext.getCurrentInstance()));
            
            System.out.println("get client id only"+backBind.getClientId());
            //System.out.println("current bakc button region id is ");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            UIViewRoot root = facesContext.getViewRoot();
            /**Pass cb1(buttonId) if page is not in taskflow, else if page is inside region then pass rgionid:buttonId

            RichCommandImageLink link=(RichCommandImageLink)root.findComponent("pt1:r1:2:cil3");
            ActionEvent actionEvent = new ActionEvent(link);
            actionEvent.queue();
            setMode("V"); */
        }        else if (dialogEvent.getOutcome().name().equals("no")) {


        }
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(tableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelBoxBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(outputFormBind);
    }

    public void setPanelBoxBind(RichPanelBox panelBoxBind) {
        this.panelBoxBind = panelBoxBind;
    }

    public RichPanelBox getPanelBoxBind() {
        return panelBoxBind;
    }

    public void setOutputFormBind(RichPanelFormLayout outputFormBind) {
        this.outputFormBind = outputFormBind;
    }

    public RichPanelFormLayout getOutputFormBind() {
        return outputFormBind;
    }

    public void setBackBind(RichCommandImageLink backBind) {
        this.backBind = backBind;
    }

    public RichCommandImageLink getBackBind() {
        return backBind;
    }

    public String pop3Yes() {
        // Add event code here...
        OperationBinding operationBinding = getBindings().getOperationBinding("Delete1");
        operationBinding.execute();
        OperationBinding operationBinding1 = getBindings().getOperationBinding("Execute1");
        operationBinding1.execute();
        OperationBinding obindparent = getBindings().getOperationBinding("Delete");
        obindparent.execute();
        
        
        OperationBinding obindcom = getBindings().getOperationBinding("Commit");
        obindcom.execute();
        OperationBinding operationBinding5 = getBindings().getOperationBinding("Rollback");
        operationBinding5.execute();
        OperationBinding executem = getBindings().getOperationBinding("Execute");
        executem.execute();
        OperationBinding operationBinding3 = getBindings().getOperationBinding("Execute1");
        operationBinding3.execute();
    
        //setMode("V");
        
        RichPopup itemPopUp = getDeleteItemPopUp();
        itemPopUp.hide();
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(tableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelBoxBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(outputFormBind);
        return "back";
    }

    public String pop3NoAction() {
        // Add event code here...
        RichPopup itemPopUp = getDeleteItemPopUp();
        itemPopUp.hide();
        
        return null;
    }

    public String pop1YesAction() {
        // Add event code here...
        BindingContainer bindings = getBindings();
        KitDefAppAMImpl am = (KitDefAppAMImpl)resolvElDC("KitDefAppAMDataControl");
                   ViewObject vo=am.getAppKit1();
                   int totalCount=vo.getRowCount();  //get ALL rows
                          vo.setRangeSize(totalCount);
                          Row[] rArray=vo.getAllRowsInRange();
                   for(Row r:rArray){
                   r.remove();
                   }
                   vo.executeQuery();

        OperationBinding operationBinding = bindings.getOperationBinding("Delete");
        operationBinding.execute();
        BindingContainer bindings1 = getBindings();
        OperationBinding operationBinding2 = bindings.getOperationBinding("Commit");
        operationBinding2.execute();
        OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute");
        operationBinding1.execute();
        OperationBinding operationBinding5 = bindings.getOperationBinding("Rollback");
        operationBinding5.execute();
        OperationBinding operationBinding3 = bindings1.getOperationBinding("Execute1");
        operationBinding3.execute();
                
       // setMode("V");
        AdfFacesContext.getCurrentInstance().addPartialTarget(tableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(formBind);
        deleteOutputPopup.hide();
        return "back";
    }

    public String pop1NoAction() {
        // Add event code here...
        deleteOutputPopup.hide();
        
        return null;
    }
}
