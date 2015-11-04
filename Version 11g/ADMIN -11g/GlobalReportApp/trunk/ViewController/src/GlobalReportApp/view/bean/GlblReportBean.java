package GlobalReportApp.view.bean;

import GlobalReportApp.model.module.GlobalReportAppAMImpl;


import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelStretchLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.binding.BindingContainer;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;



public class GlblReportBean {
   

   private RichCommandButton createBtn;
    private  static String countryReadOnly="true";
    private String saveBtn="true";
    private String cancBtn="true";
    private String addBtn="false";
    private String editBtn="false";
    private RichPopup popUpId;
    private RichPanelStretchLayout rootElement;
    private RichOutputText rootTxtElement;
    //Integer count =(Integer)getBindings().getOperationBinding("on_load_page").execute();

    Integer count =-1;
    private RichCommandButton editButton;
    private RichInputText reportName;
    private RichSelectOneChoice reportType;
    private RichSelectOneChoice reportAccessType;
    private RichSelectOneChoice reportDocId;
    private RichInputText reportFileName;
    private RichCommandImageLink eidtButton1;
    private RichTable tableBind;

    public GlblReportBean() {
      count =(Integer)getBindings().getOperationBinding("on_load_page").execute();
        
    }

    public void setCreateBtn(RichCommandButton createBtn) {
        this.createBtn = createBtn;
    }

    public RichCommandButton getCreateBtn() {
        return createBtn;
    }

    public String createAction() {
        // Add event code here...
        countryReadOnly="false";
         addBtn="true";
         editBtn="true";
         saveBtn="false";
         cancBtn="false";
         tableBind.setRowSelection("none");;
        eidtButton1.setDisabled(true);
        GlobalReportAppAMImpl am =(GlobalReportAppAMImpl)resolvElDC("GlobalReportAppAMDataControl");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        //editButton.setDisabled(true);
        return null;
    }
    
    public Object resolvElDC(String data) 
    {
    FacesContext fc = FacesContext.getCurrentInstance();
    Application app = fc.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = fc.getELContext();
    ValueExpression valueExp = elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
    return valueExp.getValue(elContext);
    }
    
    public BindingContainer getBindings() 
    {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String cancelAction() {
        countryReadOnly="true";
        GlobalReportAppAMImpl am =(GlobalReportAppAMImpl)resolvElDC("GlobalReportAppAMDataControl");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        saveBtn="true";
        cancBtn="true";
         addBtn="false";
          editBtn="false";
        eidtButton1.setDisabled(false);
        tableBind.setRowSelection("single");
       // editButton.setDisabled(false);
        return null;
    }


    public  void setCountryReadOnly(String countryReadOnly) {
        this.countryReadOnly = countryReadOnly;
    }

    public  String getCountryReadOnly() {
        return countryReadOnly;
    }

    public String saveAction() {
          if(reportName.getValue()==null || reportName.getValue().toString().length()<=0)
          {
              FacesMessage msg=new FacesMessage(evaluateEL("#{bundle['MSG.546']}").toString());
              msg.setSeverity(FacesMessage.SEVERITY_FATAL);
              FacesContext fctx=FacesContext.getCurrentInstance();
              fctx.addMessage(reportName.getClientId(fctx), msg);
          }
       else if(reportType.getValue()==null || reportType.getValue().toString().length()<=0)
        {
            FacesMessage msg=new FacesMessage(evaluateEL("#{bundle['MSG.547']}").toString());
            msg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext fctx=FacesContext.getCurrentInstance();
            fctx.addMessage(reportType.getClientId(fctx), msg);
        }
        else if(reportAccessType.getValue()==null || reportAccessType.getValue().toString().length()<=0)
         {
             FacesMessage msg=new FacesMessage(evaluateEL("#{bundle['MSG.548']}").toString());
             msg.setSeverity(FacesMessage.SEVERITY_FATAL);
             FacesContext fctx=FacesContext.getCurrentInstance();
             fctx.addMessage(reportAccessType.getClientId(fctx), msg);
         }
        else if(reportDocId.getValue()==null || reportDocId.getValue().toString().length()<=0)
         {System.out.println(reportDocId.getValue()+"in save");
             FacesMessage msg=new FacesMessage(evaluateEL("#{bundle['MSG.549']}").toString());
             msg.setSeverity(FacesMessage.SEVERITY_FATAL);
             FacesContext fctx=FacesContext.getCurrentInstance();
             fctx.addMessage(reportDocId.getClientId(fctx), msg);
         }
        else if(reportFileName.getValue()==null || reportFileName.getValue().toString().length()<=0)
         {
             FacesMessage msg=new FacesMessage(evaluateEL("#{bundle['MSG.550']}").toString());
             msg.setSeverity(FacesMessage.SEVERITY_FATAL);
             FacesContext fctx=FacesContext.getCurrentInstance();
             fctx.addMessage(reportFileName.getClientId(fctx), msg);
         }
     else{  
              GlobalReportAppAMImpl am =(GlobalReportAppAMImpl)resolvElDC("GlobalReportAppAMDataControl");
              BindingContainer bindings = getBindings();
              OperationBinding operationBinding =bindings.getOperationBinding("Commit");
              operationBinding.execute();      
                FacesMessage msg=new FacesMessage(evaluateEL("#{bundle['MSG.91']}").toString());
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fctx=FacesContext.getCurrentInstance();
                fctx.addMessage(null, msg);
                countryReadOnly="true";
                saveBtn="true";
                cancBtn="true";
                addBtn="false";
               editBtn="false";
              eidtButton1.setDisabled(false);
              tableBind.setRowSelection("single");
          }
        
    
        return null;
    }

    public String editAction() {
        // Add event code here...
        countryReadOnly="false";
        addBtn="true";
        editBtn="true";
        saveBtn="false";
        cancBtn="false";
        eidtButton1.setDisabled(true);
        tableBind.setRowSelection("none");;

        return null;
    }

    public String createPopUpAction() {
        // Add event code here...
        
        GlobalReportAppAMImpl am =(GlobalReportAppAMImpl)resolvElDC("GlobalReportAppAMDataControl");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("CreateInsert1");
        operationBinding.execute();
        showPopup(popUpId, true);
        return null;
    }

    public void popUpCancelListener(PopupCanceledEvent popupCanceledEvent) {
        GlobalReportAppAMImpl am =(GlobalReportAppAMImpl)resolvElDC("GlobalReportAppAMDataControl");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding =bindings.getOperationBinding("Rollback");
        operationBinding.execute();
    }

    public void dialogListenerAction(DialogEvent dialogEvent) {
        // Add event code here...
        if(dialogEvent.getOutcome().name().equals("ok"))
        {
            GlobalReportAppAMImpl am =(GlobalReportAppAMImpl)resolvElDC("GlobalReportAppAMDataControl");
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =bindings.getOperationBinding("Commit");
            operationBinding.execute();  
        }
        
        if(dialogEvent.getOutcome().name().equals("cancel"))
        {
            GlobalReportAppAMImpl am =(GlobalReportAppAMImpl)resolvElDC("GlobalReportAppAMDataControl");
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =bindings.getOperationBinding("Rollback");
            operationBinding.execute(); 
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
    ExtendedRenderKitService erks = Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
    erks.addScript(context, script.toString());
    }
    }
    } catch (Exception e) {
    throw new RuntimeException(e);
    }
    }

    public void setPopUpId(RichPopup popUpId) {
        this.popUpId = popUpId;
    }

    public RichPopup getPopUpId() {
        return popUpId;
    }

    public String editPopUpAction() {
        // Add event code here...
        showPopup(popUpId, true);
        return null;
    }


    public void setSaveBtn(String saveBtn) {
        this.saveBtn = saveBtn;
    }

    public String getSaveBtn() {
        return saveBtn;
    }


    public void setAddBtn(String addBtn) {
        this.addBtn = addBtn;
    }

    public String getAddBtn() {
        return addBtn;
    }

    public void setEditBtn(String editBtn) {
        this.editBtn = editBtn;
    }

    public String getEditBtn() {
        return editBtn;
    }
    
    
    public Object evaluateEL(String el) 
    {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ELContext elContext = facesContext.getELContext();
    ExpressionFactory expressionFactory =
    facesContext.getApplication().getExpressionFactory();
    ValueExpression exp =
    expressionFactory.createValueExpression(elContext, el, Object.class);
    return exp.getValue(elContext);
    }

    public void setRootElement(RichPanelStretchLayout rootElement) {
        this.rootElement = rootElement;
    }

    public RichPanelStretchLayout getRootElement() {
        return rootElement;
    }

    public void setRootTxtElement(RichOutputText rootTxtElement) {
        this.rootTxtElement = rootTxtElement;
    }

    public RichOutputText getRootTxtElement() {
        return rootTxtElement;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setEditButton(RichCommandButton editButton) {
        this.editButton = editButton;
    }

    public RichCommandButton getEditButton() {
        return editButton;
    }

    public void setReportName(RichInputText reportName) {
        this.reportName = reportName;
    }

    public RichInputText getReportName() {
        return reportName;
    }

    public void setReportType(RichSelectOneChoice reportType) {
        this.reportType = reportType;
    }

    public RichSelectOneChoice getReportType() {
        return reportType;
    }

    public void setReportAccessType(RichSelectOneChoice reportAccessType) {
        this.reportAccessType = reportAccessType;
    }

    public RichSelectOneChoice getReportAccessType() {
        return reportAccessType;
    }

    public void setReportDocId(RichSelectOneChoice reportDocId) {
        this.reportDocId = reportDocId;
    }

    public RichSelectOneChoice getReportDocId() {
       // System.out.println("reportDocId "+reportDocId.getValue());
        return reportDocId;
    }

    public void setReportFileName(RichInputText reportFileName) {
        this.reportFileName = reportFileName;
    }

    public RichInputText getReportFileName() {
        return reportFileName;
    }

    public void setCancBtn(String cancBtn) {
        this.cancBtn = cancBtn;
    }

    public String getCancBtn() {
        return cancBtn;
    }

    public void setEidtButton1(RichCommandImageLink eidtButton1) {
        this.eidtButton1 = eidtButton1;
    }

    public RichCommandImageLink getEidtButton1() {
        return eidtButton1;
    }

    public void setTableBind(RichTable tableBind) {
        this.tableBind = tableBind;
    }

    public RichTable getTableBind() {
        return tableBind;
    }
}
