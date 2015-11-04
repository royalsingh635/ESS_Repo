package naturalaccounts.view.bean;


import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

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
import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import naturalaccounts.model.module.NaturalAccountsAMImpl;
import naturalaccounts.model.views.FinAccNaVOImpl;
import naturalaccounts.model.views.FinAccNaVORowImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.adf.view.rich.util.ResetUtils;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class NaturalAccount {

    private  String flag="V";
    
    private RichPopup adSearchPopup;
    private RichTable naTable;
   
   
    private RichPopup msgPopup;
   
    //Enable Disable using variable
    
    private static int VARCHAR = Types.VARCHAR;
    private RichPopup seletePopUp;
    private RichInputText accNameBindVar;
    private RichSelectBooleanCheckbox accMultiInsatances;
    private RichPanelHeader panelHeader;
    private RichPanelFormLayout panelFormDtl1;
    private RichPanelFormLayout panelFormDtl2;
    private RichPanelFormLayout panelFormDtl3;
    private RichSelectBooleanCheckbox part_of_budgeting_form_bind;
    private RichSelectOneChoice budget_calc_logic_form_bind;
    private Boolean isRenderPage;
    private static Integer count;
    private RichInputText accName;
    private RichSelectOneChoice accType;
    private Boolean b = false;

    /**
     *
     **/
    public static BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    static {
        count = (Integer)getBindings().getOperationBinding("on_load_page").execute();
        //count=1;
        System.out.println(count);
        System.out.println("static block");
    }

    public NaturalAccount() {
    }
   

    public void setIsRenderPage(Boolean isRenderPage) {
        this.isRenderPage = isRenderPage;
    }

    public Boolean getIsRenderPage() {
        if (count == 1) {
            return false;
        } else {
            return true;
        }
    }
   

    public void onIndexSelected(ActionEvent actionEvent) {
        RichCommandLink linkPressed = (RichCommandLink)actionEvent.getSource();
        Key jboRowKey = (Key)linkPressed.getAttributes().get("jboRowKey");

        NaturalAccountsAMImpl am = (NaturalAccountsAMImpl)resolvElDC("NaturalAccountsAMDataControl");
        ViewObjectImpl v = am.getFinAccNa1();


        v.executeQuery();


        v.setWhereClause("Acc_nm like'" + linkPressed.getText() + "%'");
        v.executeQuery();

        AdfFacesContext.getCurrentInstance().addPartialTarget(naTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelHeader);
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelFormDtl1);
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelFormDtl2);
        AdfFacesContext.getCurrentInstance().addPartialTarget(panelFormDtl3);
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

    public void addNA(ActionEvent actionEvent) {
        flag = "A";
        //accMultiInsatances.setValue("false");
        AdfFacesContext.getCurrentInstance().addPartialTarget(actionEvent.getComponent());

        NaturalAccountsAMImpl am = (NaturalAccountsAMImpl)resolvElDC("NaturalAccountsAMDataControl");
                
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Createwithparameters");
        operationBinding.execute();      
        if (operationBinding.getErrors().isEmpty()) {
          //  flag = "A";
            am = (NaturalAccountsAMImpl)resolvElDC("NaturalAccountsAMDataControl");
            ViewObjectImpl v = am.getFinAccNa1();
            Row rw=v.getCurrentRow();
            if(rw!=null){
            rw.setAttribute("AccType", 0);
            rw.setAttribute("AccMultInst", "N");
            }
        }
    }

    /* public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    } */

    public void SaveNa(ActionEvent actionEvent) {
        System.out.println(":"+this.budget_calc_logic_form_bind.getValue()+"    :   "+this.part_of_budgeting_form_bind.getValue());
        BindingContainer bindings = getBindings();
        if(flag.equals("A")){
            
            if(this.budget_calc_logic_form_bind.getValue().equals(" ") && this.part_of_budgeting_form_bind.getValue().equals(true)){
          //  System.out.println("AAYA KYA??");
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage message2 = new FacesMessage(resolvEl("Budget calc logic is required  when Part of Budget is checked."));
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(this.budget_calc_logic_form_bind.getClientId(), message2);
            
            
            //code added by MS
              
            
        }else{
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            if(operationBinding.getErrors().isEmpty()){
                String msg ="#{bundle['MSG.91']}";
                FacesMessage message = new FacesMessage(resolvEl(msg)); 
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                flag = "V";
            }
        }
            //BindingContainer bindings = getBindings();          
            
            
        }
        else if(flag.equals("E")){
            
                //BindingContainer bindings = getBindings();
                if(this.budget_calc_logic_form_bind.getValue().equals(" ") && this.part_of_budgeting_form_bind.getValue().equals(true)){
                //this.budget_calc_logic_form_bind.setShowRequired(true);
                FacesContext fc = FacesContext.getCurrentInstance();
                FacesMessage message2 = new FacesMessage(resolvEl("Budget calc logic is required  when Part of Budget is checked."));
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(this.budget_calc_logic_form_bind.getClientId(), message2);
                } 
                else{
                    DCIteratorBinding parentIter = null;
                    Key parentKey = null;
                    
                    try {
                        parentIter = (DCIteratorBinding)bindings.get("FinAccNa1Iterator");
                        parentKey = parentIter.getCurrentRow().getKey();
                    } catch (Exception e) {
                        System.out.println("Error in getting primary key.");
                    }
                    //BindingContainer binding = getBindings();
                    OperationBinding createOpBAddress = bindings.getOperationBinding("Commit");
                    createOpBAddress.execute();
                     
                     
                    
                    if(createOpBAddress.getErrors().isEmpty()){
                        
                       /*  OperationBinding createOpBAddress1 = bindings.getOperationBinding("Execute");
                        createOpBAddress1.execute();
                         */
                        String msg ="#{bundle['MSG.92']}";
                        FacesMessage message = new FacesMessage(resolvEl(msg)); 
                        message.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                        flag = "V";
                        
                    }
                    try {
                        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    } catch (Exception e) {
                        System.out.println("Error in setting primary key.");
                    }
                }
                
            
            
           

        }
        
    }

    public void cancelNa(ActionEvent actionEvent) {
        //  naTable.setRowSelection("single");
        if(flag.equals("A")){
            flag = "V";
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();
            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
            createOpBAddress.execute();
        }
        else if(flag.equals("E")){
            flag="V";
            BindingContainer bindings = getBindings();
             
            DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("FinAccNa1Iterator");
             
                           
             
            Key parentKey = parentIter.getCurrentRow().getKey();
            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
            createOpBAddress.execute();
             
             
            OperationBinding createOpBAddress1 = binding.getOperationBinding("Execute");
            createOpBAddress1.execute();
             
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

        }
        
      
    }

    public void editNa(ActionEvent actionEvent) {
        
        flag = "E";
        
        
       AdfFacesContext.getCurrentInstance().addPartialTarget(actionEvent.getComponent());

    }


    
   

   
  
    public void setAdSearchPopup(RichPopup adSearchPopup) {
        this.adSearchPopup = adSearchPopup;
    }

    public RichPopup getAdSearchPopup() {
        return adSearchPopup;
    }

    public void showPopup(RichPopup popup, boolean visible) {
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

    public void setNaTable(RichTable naTable) {
        this.naTable = naTable;
    }

    public RichTable getNaTable() {
        return naTable;
    }

    public void resetTable(ActionEvent actionEvent) {
        accNameBindVar.setValue("");
        NaturalAccountsAMImpl am = (NaturalAccountsAMImpl)resolvElDC("NaturalAccountsAMDataControl");
        ViewObjectImpl v = am.getFinAccNa1();
        v.setWhereClause(null);
        v.setNamedWhereClauseParam("AccNameVar", null);
        accNameBindVar.setValue("");
        v.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(naTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(accNameBindVar);
     

    }

   

    

    public void multiRefChangeListner(ValueChangeEvent vce) {

        if (flag.equalsIgnoreCase("E")) {
            String oldVal = vce.getOldValue().toString();
            String newVal = vce.getNewValue().toString();

            if (oldVal == "true" && newVal == "false") {
                NaturalAccountsAMImpl am = (NaturalAccountsAMImpl)resolvElDC("NaturalAccountsAMDataControl");
                ViewObjectImpl coa = am.getCoa();

                FinAccNaVORowImpl Na = (FinAccNaVORowImpl)am.getFinAccNa1().getCurrentRow();

                Integer NaId = Integer.parseInt(Na.getAccId().toString());

                coa.setWhereClause("COA_ACC_ID=" + NaId);
                coa.executeQuery();
                Long count = coa.getEstimatedRowCount();


                if (count > 1) {
                    String error = resolvEl("#{bundle['MSG.167']}");
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, error, "");

                    ctx.addMessage(null, fm);
                    accMultiInsatances.setValue("true");
                }
            }
        }
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

   

    public void accNameChangeListner(ValueChangeEvent vce) {
        if(vce.getNewValue()!=null){
        String Val = (String)vce.getNewValue();
        NaturalAccountsAMImpl am = (NaturalAccountsAMImpl)resolvElDC("NaturalAccountsAMDataControl");
        ViewObjectImpl v = am.getFinAccNa1();
        v.setNamedWhereClauseParam("AccNameVar", Val);
        v.executeQuery();


        AdfFacesContext.getCurrentInstance().addPartialTarget(naTable);
        }
    }

   

  

  
    

    public void msgDialogListner(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            msgPopup.hide();
        }
    }

    public void msgCancelListner(PopupCanceledEvent popupCanceledEvent) {
        msgPopup.hide();
    }

    public void setMsgPopup(RichPopup msgPopup) {
        this.msgPopup = msgPopup;
    }

    public RichPopup getMsgPopup() {
        return msgPopup;
    }

   

  

    

   

  

    protected Object callStoredFunction2(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        NaturalAccountsAMImpl am = (NaturalAccountsAMImpl)resolvElDC("NaturalAccountsAMDataControl");
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
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void deleteNaButton(ActionEvent actionEvent) {
        NaturalAccountsAMImpl am = (NaturalAccountsAMImpl)resolvElDC("NaturalAccountsAMDataControl");
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String org_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer acc_Id = (Integer)am.getFinAccNa1().getCurrentRow().getAttribute("AccId");
        Integer typId = (Integer)am.getFinAccNa1().getCurrentRow().getAttribute("AccType");

        String flagDel =
            (String)callStoredFunction2(VARCHAR, "FIN.PKG_FIN.chk_coa(?,?,?,?)", new Object[] { cld_id, org_id, SlocId,
                                                                                                acc_Id });


        if (flagDel.equalsIgnoreCase("Y")) {
            FacesMessage msg =
                new FacesMessage(resolvEl("#{bundle['MSG.168']}"));
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else if (typId == 5022) {
            FacesMessage msg = new FacesMessage(resolvEl("#{bundle['MSG.169']}"));
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else if (flagDel.equalsIgnoreCase("N")) {
            showPopup(seletePopUp, true);

        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(actionEvent.getComponent());

    }

    public void setSeletePopUp(RichPopup seletePopUp) {
        this.seletePopUp = seletePopUp;
    }

    public RichPopup getSeletePopUp() {
        return seletePopUp;
    }

    public void deleteDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Delete");
            operationBinding.execute();
            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Commit");
            createOpBAddress.execute();
            OperationBinding createOpBAddress2 = binding.getOperationBinding("Rollback");
            createOpBAddress2.execute();
            OperationBinding createOpBAddress1 = binding.getOperationBinding("Execute");
            createOpBAddress1.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(naTable);
            ResetUtils.reset(naTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(naTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelHeader);
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelFormDtl1);
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelFormDtl2);
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelFormDtl3);
            
        }
    }

    public void nameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String msg2 = "";
        if (object != null) {
            String langDesc = object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = langDesc.toCharArray();
            for (char c : xx) {
                if (c == '(') {
                    openB = openB + 1;
                } else if (c == ')') {
                    closeB = closeB + 1;
                }

                if (closeB > openB) {
                    closeFg = true; //closed brackets will not be more than open brackets at any given time.
                }
            }


            //if openB=0 then no. of closing and opening brackets equal || opening bracket must always come before closing brackets
            //closing brackets must not come before first occurrence of openning bracket
            if (openB != closeB || closeFg == true || (langDesc.lastIndexOf("(") > langDesc.lastIndexOf(")")) ||
                (langDesc.indexOf(")") < langDesc.indexOf("("))) {
                msg2 = "#{bundle['MSG.7']}";
                FacesMessage message2 = new FacesMessage(resolvEl(msg2));
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("()")) {
                msg2 = "#{bundle['MSG.170']}";
                FacesMessage message2 = new FacesMessage(resolvEl(msg2));
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (langDesc.contains("(.") || langDesc.contains("(-") || langDesc.contains("-)")) {
                msg2 = "#{bundle['MSG.171']}";
                FacesMessage message2 = new FacesMessage(resolvEl(msg2));
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;

            /* String expression =
                "^(?:(?>[A-Za-z0-9 % $)]+)(?:\\_|\\&|\\-|\\(|\\.|\\)|\\:|\\@|\\/|\\\\(?!\\_|\\&|\\%|\\-|\\:|\\@|\\/|\\\\|$))?)*$";*/
            String expression =
            "^(?:(?>[A-Za-z0-9 \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))*(\\.|\\&(?!\\.|\\&|$))*(\\.|\\:(?!\\.|\\:|$))*" +
            "(\\.|\\/(?!\\.|\\/|$))*(\\.|\\;(?!\\.|\\;|$))*(\\.|\\%(?!\\.|\\%))*(\\.|\\\\(?!\\.|\\\\|$))*(\\.|\\,(?!\\.|\\,|$))?)*$";

           String inputStr=(String) object; 
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
       
            String error = resolvEl("#{bundle['MSG.1185']}").toString();

            if (matcher.matches()) {
            } else {
                FacesMessage message = new FacesMessage("", error);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }

        }
        }


    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setAccNameBindVar(RichInputText accNameBindVar) {
        this.accNameBindVar = accNameBindVar;
    }

    public RichInputText getAccNameBindVar() {
        return accNameBindVar;
    }

    public void setAccMultiInsatances(RichSelectBooleanCheckbox accMultiInsatances) {
        this.accMultiInsatances = accMultiInsatances;
    }

    public RichSelectBooleanCheckbox getAccMultiInsatances() {
        return accMultiInsatances;
    }
    
    //custom selection Listener
   /*  public void accNmTableSelectionListener(SelectionEvent selectionEvent) {
        ADFUtil.invokeEL("#{bindings.FinAccNa1.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class },
                         new Object[] { selectionEvent });
        // get the selected row , by this you can get any attribute of that row
        Row selectedRow =
            (Row)ADFUtil.evaluateEL("#{bindings.FinAccNa1Iterator.currentRow}"); // get the current selected row
        Integer typ = (Integer)selectedRow.getAttribute("AccType");
            //accountType.getValue();

        if (typ == 0) {
             RichInputText accountNameIT=selectedRow.getAttribute("")
            accountNm.setVisible(false);


        } else {

            accountNameIT.setVisible(false);
            accountNm.setVisible(true);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(accountNameIT);
        AdfFacesContext.getCurrentInstance().addPartialTarget(accountNm);
    } */

    public void setPanelHeader(RichPanelHeader panelHeader) {
        this.panelHeader = panelHeader;
    }

    public RichPanelHeader getPanelHeader() {
        return panelHeader;
    }

    public void setPanelFormDtl1(RichPanelFormLayout panelFormDtl1) {
        this.panelFormDtl1 = panelFormDtl1;
    }

    public RichPanelFormLayout getPanelFormDtl1() {
        return panelFormDtl1;
    }

    public void setPanelFormDtl2(RichPanelFormLayout panelFormDtl2) {
        this.panelFormDtl2 = panelFormDtl2;
    }

    public RichPanelFormLayout getPanelFormDtl2() {
        return panelFormDtl2;
    }

    public void setPanelFormDtl3(RichPanelFormLayout panelFormDtl3) {
        this.panelFormDtl3 = panelFormDtl3;
    }

    public RichPanelFormLayout getPanelFormDtl3() {
        return panelFormDtl3;
    }

    public String createCoaFromContextMenu() {
        NaturalAccountsAMImpl am = (NaturalAccountsAMImpl)resolvElDC("NaturalAccountsAMDataControl");
        FinAccNaVOImpl accNa = am.getFinAccNa1();
        Row currentRow = accNa.getCurrentRow();
        
        Integer SLOC_ID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String HO_ORG_ID = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String CLD_ID = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer NA_ID = (Integer)am.getFinAccNa1().getCurrentRow().getAttribute("AccId");
        Integer TYPE_ID = (Integer)am.getFinAccNa1().getCurrentRow().getAttribute("AccType");

        String flag = "";
        try {
            flag = (String)callStoredFunction2(VARCHAR, "CHECK_COA_CREATE_ALLOWED(?,?,?,?)", new Object[] { NA_ID, HO_ORG_ID,CLD_ID, SLOC_ID});

//        System.out.println("MULTIPLE COA CREATE ALLOWED OR NOT :"+flag);
        
        } catch (Exception e) {
          System.out.println("EXCEPTION IN CALLING FUNCTION :=> CHECK_COA_CREATE_ALLOWED(?,?,?,?)");
        }
        
        try {//System.out.println("coa act :"+currentRow.getAttribute("AccActv"));
            
            if(currentRow.getAttribute("AccActv").toString().equals("Y") && flag.equals("Y")){
                return "Create";
            }
            else if(flag.equals("N")){
                String msg ="#{bundle['MSG.175']}";
                FacesMessage message = new FacesMessage(resolvEl(msg)); 
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                return null;
            }
            else{
                String msg ="#{bundle['MSG.176']}";
                FacesMessage message = new FacesMessage(resolvEl(msg)); 
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                return null;
            }
        } catch (Exception e) {
            //System.out.println("exception");
        }
        return null;
    }

    public void setPart_of_budgeting_form_bind(RichSelectBooleanCheckbox part_of_budgeting_form_bind) {
        this.part_of_budgeting_form_bind = part_of_budgeting_form_bind;
    }

    public RichSelectBooleanCheckbox getPart_of_budgeting_form_bind() {
        return part_of_budgeting_form_bind;
    }

    public void setBudget_calc_logic_form_bind(RichSelectOneChoice budget_calc_logic_form_bind) {
        this.budget_calc_logic_form_bind = budget_calc_logic_form_bind;
    }

    public RichSelectOneChoice getBudget_calc_logic_form_bind() {
        return budget_calc_logic_form_bind;
    }

    public  void partOfBudgetVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        System.out.println(object+" "+this.budget_calc_logic_form_bind.getValue());
        if(object == null && this.part_of_budgeting_form_bind.getValue().equals("true")){
            this.budget_calc_logic_form_bind.setShowRequired(true);
            FacesMessage message2 = new FacesMessage(resolvEl("Budget calc logic is required  when Part of Budget is checked."));
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        }else{
            this.budget_calc_logic_form_bind.setShowRequired(false);
        }
    }

    public void budGetCalcLogicVALID(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
    }

    public void activeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (flag.equalsIgnoreCase("E")) {
            if (object != null) {
                NaturalAccountsAMImpl am = (NaturalAccountsAMImpl)resolvElDC("NaturalAccountsAMDataControl");


                if (am != null) {
                    FinAccNaVOImpl accNa = am.getFinAccNa1();
                    if (accNa != null) {
                        Row currentRow = accNa.getCurrentRow();
                        if (currentRow != null) {

                            Integer SLOC_ID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                            String HO_ORG_ID = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
                            String CLD_ID = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
                            System.out.println("object = " + object + "------>>");

                            if (object.toString().equalsIgnoreCase("false")) {
                                Object NA_ID_OBJ = currentRow.getAttribute("AccId");

                                if (NA_ID_OBJ!= null) {
                                    Integer NA_ID = (Integer)NA_ID_OBJ;

                                    Object result =
                                        callStoredFunction2(VARCHAR, "fn_na_allow_deactivation(?,?,?,?)", new Object[] { CLD_ID,
                                                                                                                         SLOC_ID,
                                                                                                                         HO_ORG_ID,
                                                                                                                         NA_ID });
                                    System.out.println("result when false is " + result + " NA_ID = " + NA_ID);
                                    if (result != null) {
                                        if (result.toString().equalsIgnoreCase("N")) {
                                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                                          "COA is Active for this NA",
                                                                                          null));
                                        }
                                    }
                                }
                                
                            } else {
                                Integer TYPE_ID = (Integer)currentRow.getAttribute("AccTypeId");
                                Object AccType = currentRow.getAttribute("AccType");
                                if (AccType != null) {
                                    Integer AccType1 = (Integer)AccType;
                                    if (AccType1 != 0) {
                                        Object result1 =
                                            callStoredFunction2(VARCHAR, "fn_na_allow_activation(?,?,?,?)",
                                                                new Object[] { CLD_ID, SLOC_ID, HO_ORG_ID, TYPE_ID });
                                        System.out.println("result1 when true is " + result1 + "TYPE_ID = " + TYPE_ID);

                                        if (result1 != null) {
                                            if (result1.toString().equalsIgnoreCase("N")) {
                                                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                                              "Entity is Inactive for this NA",
                                                                                              null));
                                            }
                                        }
                                    }
                                }
                            }


                        }
                    }
                }
            }
        }    
        }

    public void setAccName(RichInputText accName) {
        this.accName = accName;
    }

    public RichInputText getAccName() {
        return accName;
    }

    public void setAccType(RichSelectOneChoice accType) {
        this.accType = accType;
    }

    public RichSelectOneChoice getAccType() {
        return accType;
    }



    public void budgetLogicCalVCE(ValueChangeEvent valueChangeEvent) {
       System.out.println("Check box alue  ----------------"+valueChangeEvent.getNewValue());
       
        if(valueChangeEvent.getNewValue()==null || ((String) valueChangeEvent.getNewValue()).equals(" ")){
            
           // System.out.println("value is  null");
            this.part_of_budgeting_form_bind.setSelected(false);
        }else{
            this.part_of_budgeting_form_bind.setSelected(true);
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.part_of_budgeting_form_bind);
    }

    public void partOfBudgetVCE(ValueChangeEvent valueChangeEvent) {
        // Add event code here...Account.budget_calc_logic_form_bind
        
        if(this.part_of_budgeting_form_bind.isSelected()){
 
           if(this.budget_calc_logic_form_bind.getValue()==null ||this.budget_calc_logic_form_bind.getValue()==" " )
              FacesContext.getCurrentInstance().addMessage(this.budget_calc_logic_form_bind.getClientId(),new FacesMessage(FacesMessage.SEVERITY_ERROR,"This is required in case of part of Budgeting",null));
       
       
        }else{
            this.budget_calc_logic_form_bind.setValue(null);
        }
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.budget_calc_logic_form_bind);
    }
}
