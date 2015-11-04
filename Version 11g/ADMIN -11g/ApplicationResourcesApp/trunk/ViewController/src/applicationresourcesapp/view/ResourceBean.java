package applicationresourcesapp.view;

import applicationresourcesapp.model.module.ApplicationResourcesAMImpl;

import applicationresourcesapp.model.view.ResourceVOImpl;

import applicationresourcesapp.model.view.TransResourceVOImpl;

import applicationresourcesapp.model.view.otherLangVOImpl;

import java.sql.CallableStatement;

import java.sql.SQLException;

import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;

import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ValidationException;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;

import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.event.AttributeChangeEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class ResourceBean {
    private RichSelectBooleanCheckbox labelCB;
    private RichSelectBooleanCheckbox messageCB;
    private RichInputText textBoxKeylbl;
    private RichInputText textBoxValue;
    public String mode = "T";
    public String editmode="T";
    private RichSelectOneRadio lradio;
    private RichInputText lblautotext;
    private RichInputText value;
    private RichInputListOfValues langIdSearchBind;
    private RichInputComboboxListOfValues languageBind;
    private RichSelectOneChoice langBind;
    private RichTable tableBind;
    private RichPopup addPopup;
    private RichInputText valueOnPop;
    private RichSelectOneChoice langOnPop;
   // private RichCommandButton deleteBtn;
  //  private RichCommandButton otherEditBtn;
    private RichTable otherLangtabBind;
    private RichTable resourceTabBind;
private Integer on_load =(Integer)getBindings().getOperationBinding("on_load_page").execute();
    public ResourceBean() {
    }
    public BindingContainer getBindings() {
    return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setLabelCB(RichSelectBooleanCheckbox labelCB) {
        this.labelCB = labelCB;
    }

    public RichSelectBooleanCheckbox getLabelCB() {
        return labelCB;
    }

    public void setMessageCB(RichSelectBooleanCheckbox messageCB) {
        this.messageCB = messageCB;
    }

    public RichSelectBooleanCheckbox getMessageCB() {
        return messageCB;
    }

    public void setTextBoxKeylbl(RichInputText textBoxKeylbl) {
        this.textBoxKeylbl = textBoxKeylbl;
    }

    public RichInputText getTextBoxKeylbl() {
        return textBoxKeylbl;
    }

    public void setTextBoxValue(RichInputText textBoxValue) {
        this.textBoxValue = textBoxValue;
    }

    public RichInputText getTextBoxValue() {
        return textBoxValue;
    }
public String i="";
    public void addButton(ActionEvent actionEvent) {
        mode="F";
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert1").execute();
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert").execute();
        
        ApplicationResourcesAMImpl am =
            (ApplicationResourcesAMImpl)resolvElDC("ApplicationResourcesAMDataControl");
        ViewObject v = am.getResource1();
        v.getCurrentRow().setAttribute("LangId",1);
      //  tableBind.setRowSelection("none");
    }

    public void editButton(ActionEvent actionEvent) {
       mode="F";
        editmode="F";  
           
    }

    public void submitButton(ActionEvent actionEvent) {
        ApplicationResourcesAMImpl am =
            (ApplicationResourcesAMImpl)resolvElDC("ApplicationResourcesAMDataControl");
        ViewObject v1=am.getAppGlblResourceLbl1();
        ViewObject v = am.getResource1();
        ViewObject vt=am.getTransResource1();
        if (editmode == "F") {
        } else {
          
           
            String key_type="";
            //code for validation of KeyType
            if(v.getCurrentRow().getAttribute("KeyType")!=null)
            {key_type = (String)v.getCurrentRow().getAttribute("KeyType");
             v1.getCurrentRow().setAttribute("KeyType",key_type);}
            else
            {       
                String delmsg = "Select one Choice For Type!!!";
                FacesMessage msg = new FacesMessage(delmsg);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(lradio.getClientId(), msg);
                throw new ValidatorException(msg);
                }
           
            //code for validation of language id
            if(v.getCurrentRow().getAttribute("LangId")!=null)
            {}
            else
            {             
                String delmsg = "Enter Language!!!";
                FacesMessage msg = new FacesMessage(delmsg);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(langBind.getClientId(), msg);
                throw new ValidatorException(msg);
                }

            
            //code for check validation value
            
            
            if((String)value.getValue()==(null)||((String)value.getValue()).equals("")){   
    
                String delmsg = "NULL Value not allowed!!!"; 
                FacesMessage msg = new FacesMessage(delmsg);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(value.getClientId(),msg);
                throw new ValidatorException(msg);}
            else{
                
                String kt = (String)v.getCurrentRow().getAttribute("KeyType"); 
               String kv= (String)v.getCurrentRow().getAttribute("KeyValue");
                   Integer li=(Integer)v.getCurrentRow().getAttribute("LangId");
    
                    ViewObject vo1 = am.getResource2();
            vo1.setWhereClause("(upper(KEY_TYPE)='"+kt+"' and upper(KEY_VALUE)= upper(q'["+kv+"]') and LANG_ID = "+li+") or (upper(KEY_TYPE)='"+kt+"' and upper(KEY_VALUE)= upper(q'["+kv+"]') and LANG_ID = "+li+")");
                vo1.executeQuery();
             
  
                if (vo1.getEstimatedRowCount()>0) {
                    String delmsg = "Duplicate Value not allowed!!!";
                    FacesMessage msg = new FacesMessage(delmsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(value.getClientId(),msg);
                    throw new ValidatorException(msg);
                } else {               
                }
                } 
            //code ended for duplicate or null value
    
   
            i =
       (String)(callStoredFunction(Types.VARCHAR, "APP.PKG_APP_RESOURCE.GEN_KEY_LBL(?)", new Object[] { lradio.getValue() }));
         //   System.out.println("generated value"+i);
            v1.getCurrentRow().setAttribute(0, i);v1.executeQuery();
            v.getCurrentRow().setAttribute("KeyLbl", i);
          
        }
        
  BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
    Key k=v.getCurrentRow().getKey();
   // System.out.println("Key="+k);
    vt.executeQuery();
    Row r=vt.getRow(k);    
        vt.setCurrentRow(r);
   
        mode = "T";
        editmode = "T";
    }


    //code for Search
    public Object resolvElDC(String data) {
    FacesContext fc = FacesContext.getCurrentInstance();
    Application app = fc.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = fc.getELContext();
    ValueExpression valueExp =
    elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
    return valueExp.getValue(elContext);
    }
   
    public void searchButton(ActionEvent actionEvent) {
        ApplicationResourcesAMImpl am =
        (ApplicationResourcesAMImpl)resolvElDC("ApplicationResourcesAMDataControl");
        ResourceVOImpl vo = (ResourceVOImpl)am.getResource1();
             TransResourceVOImpl vo2 = am.getTransResource1();
        ViewObject lvo=am.getLanguage1();
         String values = ((String)textBoxValue.getValue()); 
       String lbl = (String)textBoxKeylbl.getValue();
     
   
             Integer langid=(Integer)lvo.getCurrentRow().getAttribute("TransLanguageId");
  
       StringBuilder sb=new StringBuilder();
      Integer count=0;
      if(labelCB.getValue().toString().equals("true"))
      {  if(messageCB.getValue().toString().equals("true"))
                  sb.append("(X.KEY_TYPE='L' OR X.KEY_TYPE='M')");
         else
                 sb.append("X.KEY_TYPE='L'");
          count=1;
          }

      if(messageCB.getValue().toString().equals("true"))
        {if(count==0)
            sb.append("X.KEY_TYPE='M'");
                count=1;
            }

      if(lbl==(null) || lbl.equals("")){}else
        {if(count==0)
                sb.append("upper(X.KEY_LBL) like '%"+lbl.toUpperCase()+"%'");
        else if(count==1)
       sb.append(" AND upper(X.KEY_LBL) like '%"+lbl.toUpperCase()+"%'");
                                count=1;
            }

      if(values==(null)|| values.equals("")){}else
        { if(count==0)
                sb.append("upper(X.KEY_VALUE) like q'[%"+values.trim().toUpperCase()+"%]'");
      else if(count==1)
              sb.append(" AND upper(X.KEY_VALUE) like q'[%"+values.trim().toUpperCase()+"%]'");
                count=1;
            }
      if(langid==(null)|| langid.equals("")){}else 
      { if(count==0)
            sb.append("Y.LANG_ID="+langid);
          else if(count==1)
            sb.append(" AND Y.LANG_ID="+langid);
          count=1;
       }
           //  System.out.println("Querry is="+sb);
            // vo2.setWhereClause(sb.toString());
           //  vo2.executeQuery();
            vo2.setFullSqlMode(vo.FULLSQL_MODE_AUGMENTATION);
            if(count==0){
                vo2.setQuery("SELECT DISTINCT X.KEY_LBL,X.KEY_VALUE,X.KEY_TYPE,X.LANG_ID FROM APP$GLBL$RESOURCE X,APP$GLBL$RESOURCE Y WHERE "+sb.toString()+" X.KEY_LBL=Y.KEY_LBL AND X.LANG_ID=1 ");
            }else if(count==1){
             vo2.setQuery("SELECT DISTINCT X.KEY_LBL,X.KEY_VALUE,X.KEY_TYPE,X.LANG_ID FROM APP$GLBL$RESOURCE X,APP$GLBL$RESOURCE Y WHERE "+sb.toString()+" AND X.KEY_LBL=Y.KEY_LBL AND X.LANG_ID=1");}
       // am.createViewObjectFromQueryStmt("Resource1","SELECT X.KEY_TYPE,X.KEY_LBL,X.KEY_VALUE FROM APP$GLBL$RESOURCE X,APP$GLBL$RESOURCE Y WHERE"+sb.toString()+"AND X.KEY_LBL=Y.KEY_LBL AND X.LANG_ID=1");
        vo2.executeQuery();
        if(vo2.getEstimatedRowCount()>0){
      //  System.out.println("Key Value after search="+vo2.getCurrentRow().getAttribute("KeyValue"));
             String selctedKeyLbl = (String)vo2.getCurrentRow().getAttribute("KeyLbl");
           //  ResourceVOImpl rvo=am.getResource1();
             vo.setWhereClause("KEY_LBL='"+selctedKeyLbl+"' AND LANG_ID=1");
        vo.executeQuery();    }else{          
            vo.setWhereClause("KEY_LBL='' AND LANG_ID=1");
             vo.executeQuery();}
             AdfFacesContext.getCurrentInstance().addPartialTarget(lradio);
              AdfFacesContext.getCurrentInstance().addPartialTarget(langBind);
              AdfFacesContext.getCurrentInstance().addPartialTarget(lblautotext);
              AdfFacesContext.getCurrentInstance().addPartialTarget(value);
              AdfFacesContext.getCurrentInstance().addPartialTarget(otherLangtabBind);
         }
       


    public void setLradio(RichSelectOneRadio lradio) {
        this.lradio = lradio;
    }

    public RichSelectOneRadio getLradio() {
        return lradio;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
    
    //calling a function Defination
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
            CallableStatement st = null;
            try {
                ApplicationResourcesAMImpl am = (ApplicationResourcesAMImpl)resolvElDC("ApplicationResourcesAMDataControl");
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
                    } catch (SQLException e) { throw new JboException(e);
                    }
                }
            }
        }

    public void setI(String i) {
        this.i = i;
    }

    public String getI() {
        return i;
    }



    public void setLblautotext(RichInputText lblautotext) {
        this.lblautotext = lblautotext;
    }

    public RichInputText getLblautotext() {
        return lblautotext;
    }

   
    public void valuechangelist(ValueChangeEvent valueChangeEvent) {
    }

    public void setEditmode(String editmode) {
        this.editmode = editmode;
    }

    public String getEditmode() {
        return editmode;
    }

    public String calcelbutton() {
       mode="T";
       editmode="T";
        return null;
    }

    public void setValue(RichInputText value) {
        this.value = value;
    }

    public RichInputText getValue() {
        return value;
    }

    public void valueAttributeChangeListener(AttributeChangeEvent attributeChangeEvent) {
 
    }

    public void duplicateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
       
    }

    public void setLangIdSearchBind(RichInputListOfValues langIdSearchBind) {
        this.langIdSearchBind = langIdSearchBind;
    }

    public RichInputListOfValues getLangIdSearchBind() {
        return langIdSearchBind;
    }

    public void setLanguageBind(RichInputComboboxListOfValues languageBind) {
        this.languageBind = languageBind;
    }

    public RichInputComboboxListOfValues getLanguageBind() {
        return languageBind;
    }

    public void setLangBind(RichSelectOneChoice langBind) {
        this.langBind = langBind;
    }

    public RichSelectOneChoice getLangBind() {
        return langBind;
    }

    public StringBuilder checkSpecialCharacter(String str)
    {
   StringBuilder replyStr=new StringBuilder();
    for(int i=0;i<str.length();i++)
    {
    if(str.charAt(i)=='\'')
    {
    replyStr.append('\\');
        replyStr.append('\'');
    continue;
    }
    replyStr.append(str.charAt(i));
    }
    return replyStr;
    }


    public void setTableBind(RichTable tableBind) {
        this.tableBind = tableBind;
    }

    public RichTable getTableBind() {
        return tableBind;
    }

    public void resourceTableSelectionListener(SelectionEvent selectionEvent) {
       ADFUtil.invokeEL("#{bindings.TransResource1.collectionModel.makeCurrent}", new Class[] {SelectionEvent.class},
                             new Object[] { selectionEvent });
            // get the selected row , by this you can get any attribute of that row
           // Row selectedRow =
             //   (Row)ADFUtil.evaluateEL("#{bindings.Resource1Iterator.currentRow}"); // get the current selected row
             ApplicationResourcesAMImpl am =
             (ApplicationResourcesAMImpl)resolvElDC("ApplicationResourcesAMDataControl");
             TransResourceVOImpl vo =am.getTransResource1();
             String selctedKeyLbl = (String)vo.getCurrentRow().getAttribute("KeyLbl");
          //   System.out.println(selctedKeyLbl);    
        ResourceVOImpl resvo=am.getResource1();
        otherLangVOImpl othervo = (otherLangVOImpl)am.getotherLang1();
       othervo.setFullSqlMode(othervo.FULLSQL_MODE_AUGMENTATION);
        othervo.setQuery("SELECT Lang_id,Key_value FROM APP$GLBL$RESOURCE WHERE UPPER(KEY_LBL)='"+selctedKeyLbl+"' and LANG_ID != 1");
           othervo.executeQuery();
           ResourceVOImpl rvo=am.getResource1();
           rvo.setWhereClause("KEY_LBL='"+selctedKeyLbl+"' AND LANG_ID=1");
           rvo.executeQuery();
       //   System.out.println("No. of Rows=="+othervo.getEstimatedRowCount());
       AdfFacesContext.getCurrentInstance().addPartialTarget(lradio);
        AdfFacesContext.getCurrentInstance().addPartialTarget(langBind);
        AdfFacesContext.getCurrentInstance().addPartialTarget(lblautotext);
        AdfFacesContext.getCurrentInstance().addPartialTarget(value);
        AdfFacesContext.getCurrentInstance().addPartialTarget(otherLangtabBind);
          if(othervo.getEstimatedRowCount()>new Long(0)){
           /*  System.out.println("in condition");
                  deleteBtn.setDisabled(false);
                  otherEditBtn.setDisabled(false);*/
          }else{              //    deleteBtn.setDisabled(true);
                  //otherEditBtn.setDisabled(true);
              }
    /*  ApplicationResourcesAMImpl am1 =
     (ApplicationResourcesAMImpl)resolvElDC("ApplicationResourcesAMDataControl");
             ViewObject vother=am1.getotherLang1();
             vother.remove();
             am.createViewObjectFromQueryStmt("otherLang1","SELECT * FROM APP$GLBL$RESOURCE WHERE UPPER(KEY_LBL)='"+selctedKeyLbl+"' AND LANG_ID != 1"); 
         */
    }

    public void addTranslationBtnListener(ActionEvent actionEvent) {
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("CreateInsert").execute();
        ApplicationResourcesAMImpl am =
            (ApplicationResourcesAMImpl)resolvElDC("ApplicationResourcesAMDataControl");
        ViewObject vtrans = am.getTransResource1();
        ViewObject v=am.getResource1();
        String keylbl = (String)vtrans.getCurrentRow().getAttribute("KeyLbl");
        v.getCurrentRow().setAttribute("KeyLbl",keylbl);
        String keytype=(String)vtrans.getCurrentRow().getAttribute("KeyType"); 
        v.getCurrentRow().setAttribute("KeyType",keytype);
       // System.out.println("key=="+keylbl);
       // System.out.println("type=="+keytype);
       
        showPopup(addPopup, true);
      //  v.getCurrentRow().setAttribute("","");
        
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
     

    public void editTranslationBtnListener(ActionEvent actionEvent) {
        ApplicationResourcesAMImpl am =
            (ApplicationResourcesAMImpl)resolvElDC("ApplicationResourcesAMDataControl");
        otherLangVOImpl othervo=am.getotherLang2();
        ResourceVOImpl vo=am.getResource1();
        String selctedKeyLbl = (String)othervo.getCurrentRow().getAttribute("KeyLbl");
        Integer li = (Integer)othervo.getCurrentRow().getAttribute("LangId"); 
        vo.setWhereClause("KEY_LBL='"+selctedKeyLbl+"' AND LANG_ID="+li);
        vo.executeQuery();
        showPopup(addPopup, true);
    }

    public void saveTranslationBtnListener(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void deleteSelectedTranslationListener(ActionEvent actionEvent) {
        ApplicationResourcesAMImpl am =
            (ApplicationResourcesAMImpl)resolvElDC("ApplicationResourcesAMDataControl");
        ResourceVOImpl vo=am.getResource1();   
     otherLangVOImpl othervo=am.getotherLang2();
     TransResourceVOImpl transvo=am.getTransResource1();
     String keylabel = (String)transvo.getCurrentRow().getAttribute("KeyLbl");

   String kl = (String)othervo.getCurrentRow().getAttribute("KeyLbl");Integer li = (Integer)othervo.getCurrentRow().getAttribute("LangId"); 

    //    System.out.println(" delete="+kl+" "+li);
        vo.setWhereClause("KEY_LBL='"+kl+"' AND LANG_ID="+li);
        vo.executeQuery();
        vo.setCurrentRow(vo.first());
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Delete").execute();   
     BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
        
        vo.setWhereClause("KEY_LBL='"+kl+"' AND LANG_ID=1");
        vo.setCurrentRow(vo.first());
        vo.executeQuery();
        ViewObject tvo=am.getTransResource1();
        tvo.executeQuery();
        RowSetIterator r=tvo.createRowSetIterator(null);
        r.reset();
        while(r.hasNext())
        {
           Row row=r.next();
            if(row.getAttribute("KeyLbl").toString().equals(keylabel) && row.getAttribute("LangId").toString().equals("1")){//System.out.println("RowFound");
                                                                                    tvo.setCurrentRow(row);
                                                                                    break;}
            }
        r.closeRowSetIterator();
        
    }

    public void setAddPopup(RichPopup addPopup) {
        this.addPopup = addPopup;
    }

    public RichPopup getAddPopup() {
        return addPopup;
    }

    public void dialougeListener(DialogEvent dialogEvent) {
        if(dialogEvent.getOutcome() == DialogEvent.Outcome.ok)
        {
            ApplicationResourcesAMImpl am =
                (ApplicationResourcesAMImpl)resolvElDC("ApplicationResourcesAMDataControl");
            ViewObject v = am.getResource1();
            ViewObject transvo=am.getTransResource1();
         String keylabel = (String)transvo.getCurrentRow().getAttribute("KeyLbl");
            if(v.getCurrentRow().getAttribute("LangId")!=null)
                {
               // System.out.println(v.getCurrentRow().getAttribute("LangId"));
                if(v.getCurrentRow().getAttribute("LangId")=="1")
                {
                        String delmsg = "Language already used !!!";
                        FacesMessage msg = new FacesMessage(delmsg);
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext ctx = FacesContext.getCurrentInstance();
                        ctx.addMessage(langOnPop.getClientId(), msg);
                        throw new ValidatorException(msg);
                    }
                }
                else
                {             
                    String delmsg = "Enter Language !!!";
                    FacesMessage msg = new FacesMessage(delmsg);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage(langOnPop.getClientId(), msg);
                    throw new ValidatorException(msg);
                    }
          // System.out.println("language validation has been passed...");
            if(v.getCurrentRow().getAttribute("KeyValue").toString()==(null)|| v.getCurrentRow().getAttribute("KeyValue").toString().equals(""))
            {  // System.out.println("value is null--->validation");
                String delmsg = "NULL Value not allowed!!!"; 
                FacesMessage msg = new FacesMessage(delmsg);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(valueOnPop.getClientId(),msg);
                throw new ValidatorException(msg);
                }
          //  System.out.println("value validation has been passed");
         BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Commit").execute();
            ViewObject tvo=am.getTransResource1();
            tvo.executeQuery();
            RowSetIterator r=tvo.createRowSetIterator(null);
            r.reset();
            while(r.hasNext())
            {
               Row row=r.next();
                if(row.getAttribute("KeyLbl").toString().equals(keylabel) && row.getAttribute("LangId").toString().equals("1")){//System.out.println("RowFound");
                                                                                        tvo.setCurrentRow(row);
                                                                                        break;}
                }
            r.closeRowSetIterator();
            
           // System.out.println("Data Committed....");
        }
    }

    public void setValueOnPop(RichInputText valueOnPop) {
        this.valueOnPop = valueOnPop;
    }

    public RichInputText getValueOnPop() {
        return valueOnPop;
    }

    public void setLangOnPop(RichSelectOneChoice langOnPop) {
        this.langOnPop = langOnPop;
    }

    public RichSelectOneChoice getLangOnPop() {
        return langOnPop;
    }

    public void popUpCancelListener(PopupCanceledEvent popupCanceledEvent) {
        ApplicationResourcesAMImpl am =
        (ApplicationResourcesAMImpl)resolvElDC("ApplicationResourcesAMDataControl");
        ViewObject tvo1=am.getTransResource1();
        String kl = (String)tvo1.getCurrentRow().getAttribute("KeyLbl");
        BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("Rollback").execute();
        ViewObject vo=am.getResource1();
        vo.executeQuery();
        ViewObject tvo=am.getTransResource1();
        tvo.executeQuery();
        RowSetIterator r=tvo.createRowSetIterator(null);
        r.reset();
        while(r.hasNext())
        {
           Row row=r.next();
            if(row.getAttribute("KeyLbl").toString().equals(kl) && row.getAttribute("LangId").toString().equals("1")){//System.out.println("RowFound");
                                                                                    tvo.setCurrentRow(row);
                                                                                    break;}
            }
        r.closeRowSetIterator();
        //System.out.println("Data RolledBack....");
    }

    public void otherLangSelectionListener(SelectionEvent selectionEvent) {
     ADFUtil.invokeEL("#{bindings.otherLang2.collectionModel.makeCurrent}", new Class[] {SelectionEvent.class},
                          new Object[] { selectionEvent });
        ApplicationResourcesAMImpl am =
        (ApplicationResourcesAMImpl)resolvElDC("ApplicationResourcesAMDataControl");
        otherLangVOImpl othervo=am.getotherLang2();
       // System.out.println("Current Row is==");
      //  System.out.println(othervo.getCurrentRow().getAttribute(0));
      // System.out.println(othervo.getCurrentRow().getAttribute(1));
       // System.out.println(othervo.getCurrentRow().getAttribute(2));
      //  System.out.println(othervo.getCurrentRow().getAttribute(3));
    }



    public void setOtherLangtabBind(RichTable otherLangtabBind) {
        this.otherLangtabBind = otherLangtabBind;
    }

    public RichTable getOtherLangtabBind() {
        return otherLangtabBind;
    }

    public void setResourceTabBind(RichTable resourceTabBind) {
        this.resourceTabBind = resourceTabBind;
    }

    public RichTable getResourceTabBind() {
        return resourceTabBind;
    }

    public void setOn_load(Integer on_load) {
        this.on_load = on_load;
    }

    public Integer getOn_load() {
        return on_load;
    }
}
