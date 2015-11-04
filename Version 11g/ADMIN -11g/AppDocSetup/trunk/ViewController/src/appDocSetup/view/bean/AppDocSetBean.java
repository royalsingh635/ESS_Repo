package appDocSetup.view.bean;

import appDocSetup.model.module.AppDocSetupAMImpl;

import appDocSetup.model.view.AppDocVOImpl;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import javax.faces.context.FacesContext;

//import oracle.adf.model.OperationBinding;
import javax.faces.validator.ValidatorException;

import javax.swing.text.View;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent.Outcome;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.server.ViewObjectImpl;

public class AppDocSetBean {
    private String mode = "V";
    private RichSelectOneChoice docMd;
    private RichSelectBooleanCheckbox partWf;
    private RichPopup pop;
    private RichPopup popreset;
    private RichPopup popadd;
    private static int VARCHAR = Types.VARCHAR;
    private RichTable glblTableBind;
    private RichSelectOneChoice packageId;
    private RichSelectOneChoice docIdType;
    private RichTable doctypetableBind;
    AppDocSetupAMImpl am = (AppDocSetupAMImpl)resolvElDC("AppDocSetupAMDataControl");
    private RichTable resetTableBind;
    private RichPopup popresetAdd;
    private RichPopup popDoctypeEdit;
    private RichDialog dialogueListenerEditDocType;
    private RichPanelFormLayout panelformBind;
    private RichSelectBooleanCheckbox resetDefault;
    private RichSelectBooleanCheckbox reserActive;
    private RichCommandImageLink editBind;

    public AppDocSetBean() {
    }

    public void addAction(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        showPopup(popadd, true);
       mode = "A";
        AdfFacesContext.getCurrentInstance().addPartialTarget(glblTableBind);
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void saveAction(ActionEvent actionEvent) {
        System.out.println("Inside save");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        if(operationBinding.getErrors().isEmpty()){
        mode = "V";
        System.out.println("Error not occured");
        }
        System.out.println("Error occured");
    }

    public void setDocMd(RichSelectOneChoice docMd) {
        this.docMd = docMd;
    }

    public RichSelectOneChoice getDocMd() {
        return docMd;
    }

    public void setPartWf(RichSelectBooleanCheckbox partWf) {
        this.partWf = partWf;
    }

    public RichSelectBooleanCheckbox getPartWf() {
        return partWf;
    }

    public void editDocumentMode(ActionEvent actionEvent) {
        /* docMd.setReadOnly(false);
        partWf.setReadOnly(false); */
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

    public void addDocumentMode(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
        operationBinding.execute();
        System.out.println("docIdType is in add document mode" + docIdType);
        showPopup(pop, true);
        //  this.mode = "A";
        /* docMd.setReadOnly(false);
        partWf.setReadOnly(false); */

    }


    public void addResetDocument(ActionEvent actionEvent) {
        // Add event code here...

        System.out.println("before createinsert");
        OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsert2");
        operationBinding.execute();
        System.out.println("after createinsert");
        showPopup(popresetAdd, true);
        // mode = "A";
        AdfFacesContext.getCurrentInstance().addPartialTarget(resetTableBind);
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
            OperationBinding op = bindings.getOperationBinding("Commit");
            System.out.println("docIdType" + docIdType);
            op.execute();
            System.out.println("Error on Commit=" + op.getErrors());
        }/*  else {

            BindingContainer bindings = getBindings();
            OperationBinding op = bindings.getOperationBinding("Rollback");
            op.execute();
        } */

        AdfFacesContext.getCurrentInstance().addPartialTarget(doctypetableBind);
    }

    public void setPopreset(RichPopup popreset) {
        this.popreset = popreset;
    }

    public RichPopup getPopreset() {
        return popreset;
    }

    public void dialogListenerResetpop(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().equals("ok")) {
            System.out.println("dialogListenerResetpop " + " ok ");

            BindingContainer bindings = getBindings();
            OperationBinding op = bindings.getOperationBinding("Commit");
                 op.execute();

            System.out.println("Commit errors" + op.getErrors());

            AdfFacesContext.getCurrentInstance().addPartialTarget(resetTableBind);

        } else {

            BindingContainer bindings = getBindings();
            OperationBinding op = bindings.getOperationBinding("Rollback");
            op.execute();

            System.out.println("Commit errors" + op.getErrors());
        }

    }

    public void setPopadd(RichPopup popadd) {
        this.popadd = popadd;
    }

    public RichPopup getPopadd() {
        return popadd;
    }

    public void dialogueListenerAdd(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("ok")) {
            BindingContainer bindings = getBindings();
            OperationBinding op = bindings.getOperationBinding("Commit");
            op.execute();
            if (op.getErrors().isEmpty()) {
                System.out.println("if");
            } else {
                System.out.println("else");
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(glblTableBind);
        }
    }

    public void setGlblTableBind(RichTable glblTableBind) {
        this.glblTableBind = glblTableBind;
    }

    public RichTable getGlblTableBind() {
        return glblTableBind;
    }

    public void CancelAction(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        OperationBinding op = bindings.getOperationBinding("Rollback");
        op.execute();
        mode = "V";
      AdfFacesContext.getCurrentInstance().addPartialTarget(glblTableBind);
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

    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    protected Object callStoredFunction2(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;

        AppDocSetupAMImpl am = (AppDocSetupAMImpl)resolvElDC("AppDocSetupAMDataControl");
        try {
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();

            return st.getObject(1);

        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    // System.out.println(e.getMessage());
                }
            }
        }
    }


    public void setPackageId(RichSelectOneChoice packageId) {
        this.packageId = packageId;
    }

    public RichSelectOneChoice getPackageId() {

        return packageId;
    }

    public void setDocIdType(RichSelectOneChoice docIdType) {
        this.docIdType = docIdType;
    }

    public RichSelectOneChoice getDocIdType() {
        return docIdType;
    }

    public void setDoctypetableBind(RichTable doctypetableBind) {
        this.doctypetableBind = doctypetableBind;
    }

    public RichTable getDoctypetableBind() {
        return doctypetableBind;
    }

    public void Delete(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        OperationBinding op = bindings.getOperationBinding("Delete");
        op.execute();
      //  mode = "V";

    }

    public void DeleteDoctypeId(ActionEvent actionEvent) {
        // Add event code here...

        BindingContainer bindings = getBindings();
        OperationBinding op = bindings.getOperationBinding("Delete1");
        op.execute();
        mode = "V";
    }

    public void EditAction(ActionEvent actionEvent) {
        // Add event code here...
        //call method to set user modified id in rowIMPL

        showPopup(popreset, true);
       // mode = "E";


    }

    /**
     * @param usrId
     */
    public void editDocTypeReset(Integer usrId) {

        //call method to set user modified id in rowIMPL
        OperationBinding op = getBindings().getOperationBinding("setUserIdModify");
        op.getParamsMap().put("value", usrId);
        
        showPopup(popreset, true);
        mode = "E";


    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setResetTableBind(RichTable resetTableBind) {
        this.resetTableBind = resetTableBind;
    }

    public RichTable getResetTableBind() {
        AdfFacesContext.getCurrentInstance().addPartialTarget(editBind);
        return resetTableBind;
    }

    public void setAm(AppDocSetupAMImpl am) {
        this.am = am;
    }

    public AppDocSetupAMImpl getAm() {
        return am;
    }

    public void EditDocType(ActionEvent actionEvent) {
        // Add event code here...
        showPopup(popDoctypeEdit, true);
    }

    public void setPopresetAdd(RichPopup popresetAdd) {
        this.popresetAdd = popresetAdd;
    }

    public RichPopup getPopresetAdd() {
        return popresetAdd;
    }

    public void popresetaddcancelListener(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
         Key key=null;
         Key key1=null;
         BindingContainer bindings = getBindings();
         DCIteratorBinding dcitr= (DCIteratorBinding)bindings.get("AppDocIterator");
         DCIteratorBinding dcitr1= (DCIteratorBinding)bindings.get("AppDocTypeVO1Iterator");
         if(dcitr.getCurrentRow()!=null) {
             key=dcitr.getCurrentRow().getKey();
             if(dcitr1.getCurrentRow()!=null) {
                 key1=dcitr1.getCurrentRow().getKey();
             }
         }
         OperationBinding op = bindings.getOperationBinding("Rollback");
        op.execute();
        if(key!=null) {
            dcitr.setCurrentRowWithKey(key.toStringFormat(true));
            if(key1!=null) {
                dcitr1.setCurrentRowWithKey(key1.toStringFormat(true)); 
            }
        }
AdfFacesContext.getCurrentInstance().addPartialTarget(resetTableBind);

    }

    public void DialogueListenerpopResetAdd(DialogEvent dialogEvent) {
        System.out.println("in dialog Litner");
        //  getAm().getDBTransaction().commit();
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("ok")) {
            System.out.println("before commit");
            BindingContainer bindings = getBindings();
            OperationBinding op = bindings.getOperationBinding("Commit");
            op.execute();
            System.out.println("Error on commit=" + op.getErrors());
            System.out.println("after commit");
            //AdfFa/cesContext.getCurrentInstance().addPartialTarget(glblTableBind);
        }

    }

    public void DocTypeCancelListener(PopupCanceledEvent popupCanceledEvent) {
        Key key=null;
        BindingContainer bindings = getBindings();
        DCIteratorBinding dcitr=(DCIteratorBinding)bindings.get("AppDocIterator");
        if(dcitr.getCurrentRow()!=null) {
            key=dcitr.getCurrentRow().getKey();
        }
        OperationBinding op = bindings.getOperationBinding("Rollback");
        op.execute();
        if(key!=null) {
            dcitr.setCurrentRowWithKey(key.toStringFormat(true));
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(doctypetableBind);
    }

    public void DeleteAddDoctype(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = getBindings();
        OperationBinding op = bindings.getOperationBinding("Delete1");
        op.execute();
        op=bindings.getOperationBinding("Commit");
        op.execute();
    }

    public void EditAddDocType(ActionEvent actionEvent) {
        // Add event code here...
        //call method to set user modified id

        showPopup(popDoctypeEdit, true);
    }

    public void setPopDoctypeEdit(RichPopup popDoctypeEdit) {
        this.popDoctypeEdit = popDoctypeEdit;
    }

    public RichPopup getPopDoctypeEdit() {
        return popDoctypeEdit;
    }

    public void setDialogueListenerEditDocType(RichDialog dialogueListenerEditDocType) {
        this.dialogueListenerEditDocType = dialogueListenerEditDocType;
    }

    public RichDialog getDialogueListenerEditDocType() {
        return dialogueListenerEditDocType;
    }

    public void DialogueListenerDocTypeEdit(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("ok")) {
            BindingContainer bindings = getBindings();
            OperationBinding op = bindings.getOperationBinding("Commit");
            op.execute();
        }
    }

    public void PopUpDocTypeEditCancelListener(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
    }

    public void EditnewDoc(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("Inside edit");
       mode = "E";
    
    }

    public void popupAddDocCancelListener(PopupCanceledEvent popupCanceledEvent) {
       
        BindingContainer bindings = getBindings();
        OperationBinding op = bindings.getOperationBinding("Rollback");
        op.execute();
        mode = "V";
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(glblTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(doctypetableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(resetTableBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelformBind);
 
        
        
        // Add event code here...
    }

    public void setPanelformBind(RichPanelFormLayout panelformBind) {
        this.panelformBind = panelformBind;
    }

    public RichPanelFormLayout getPanelformBind() {
        return panelformBind;
    }

    public void searchAction(ActionEvent actionEvent) {
        // Add event code here...
        /* AppDocVOImpl v = am.getAppDoc();
        v.applyViewCriteria(vc); */
    }
    
    public String getPakage(){
    
        String pakage=null; 
        
      
    String p_disp_mode = "T";  
        
       Integer docid =(Integer) am.getAppDoc().getCurrentRow().getAttribute("DocId");
        pakage=
        (String)callStoredFunction2(VARCHAR, "fn_get_doc_app_struct(?,?)", new Object[] { docid ,p_disp_mode});

        return pakage;
    }


    public void docResetAllowValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        ViewObjectImpl typresvo=am.getAppDocTypeResetVO2();
        Row rw[]=typresvo.getFilteredRows("DocTypResetDef", "Y");
          //  System.out.println("total no of rows in reset vo is=="+rw.length);
        if(object!=null) {
            if(object.toString().equalsIgnoreCase("true")||object.toString().equalsIgnoreCase("Y")) {
                if(rw.length>1) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "There should be only one default reset frequency",null));

                }
            }
        }
       /*  String docreset = null;
       
        String orgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        
        Integer doctypeid =( Integer) am.getAppDocTypeVO1().getCurrentRow().getAttribute("DocTypId");
        Integer docid =(Integer) am.getAppDoc().getCurrentRow().getAttribute("DocId");
       
       docreset =
        (String)callStoredFunction2(VARCHAR, "FN_DOCTYP_RESET_ALLOW(?,?,?)", new Object[] { orgId,docid,doctypeid});
        System.out.println("doctypeid"+doctypeid + "docid"  + docid + "value" + docreset);
    
        if(object != null){
            
            //ViewObjectImapl app = am.getAppDocTypeResetVO2();
            System.out.println("object "+object+" docreset "+docreset);
        if((object.toString().equalsIgnoreCase("true")&& "N".equals(docreset))){
          
        
            String msg2 = resolvEl("#{bundle['MSG.281']}").toString();

            FacesMessage message2 = new FacesMessage( msg2);

            message2.setSeverity(FacesMessage.SEVERITY_INFO);
            throw new ValidatorException(message2);
  
       
     
        }*//* else if(object.toString().equalsIgnoreCase("False") && "Y".equals(docreset)){
            
                String msg2 = "Please select atleast one default value";

                FacesMessage message2 = new FacesMessage( msg2);

                message2.setSeverity(FacesMessage.SEVERITY_INFO);
                throw new ValidatorException(message2);
                
            
            }  */       
       // } 
    }

    public void setResetDefault(RichSelectBooleanCheckbox resetDefault) {
        this.resetDefault = resetDefault;
    }

    public RichSelectBooleanCheckbox getResetDefault() {
        return resetDefault;
    }

    public void setReserActive(RichSelectBooleanCheckbox reserActive) {
        this.reserActive = reserActive;
    }

    public RichSelectBooleanCheckbox getReserActive() {
        return reserActive;
    }

    public void DocEditActionDL(DialogEvent dialogEvent) {
       BindingContainer bindings=getBindings();
       OperationBinding op=bindings.getOperationBinding("Commit");
       op.execute();
       AdfFacesContext.getCurrentInstance().addPartialTarget(doctypetableBind);
    }

    public void setEditBind(RichCommandImageLink editBind) {
        this.editBind = editBind;
    }

    public RichCommandImageLink getEditBind() {
        return editBind;
    }
}
