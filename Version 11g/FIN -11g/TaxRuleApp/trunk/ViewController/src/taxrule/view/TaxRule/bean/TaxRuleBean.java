package taxrule.view.TaxRule.bean;


import java.io.IOException;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.Types;

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
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.adf.view.rich.util.FacesMessageUtils;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import taxrule.model.module.TaxRuleAMImpl;
import taxrule.model.views.AppTaxRuleLineVOImpl;
import taxrule.model.views.AppTaxRuleLineVORowImpl;
import taxrule.model.views.AppTaxRuleVORowImpl;


public class TaxRuleBean {


    private RichPopup popupNew;
    private String flag = "V";
    private RichPopup popupTaxRule;
    private RichPopup deletePopup;


    private RichPopup deleteTaxRuleDtl;
    private RichTable taxRuleTable;
    private RichTable taxRuleLineTable;
    private RichSelectOneChoice coaId;
    private Integer onloadVal;
    private oracle.jbo.domain.Number zero = new oracle.jbo.domain.Number(0);
    private RichInputText priorityBinding;
    private String finalize = "N";
    private RichSelectBooleanCheckbox taxTypeTdsBinding;
    private String isBasicCoaExist="N";

    public TaxRuleBean() {
        isBasicCoaExist="N";
    }

    public String createTaxRule() {


        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        flag = "A";
        finalize = "N";
        showPopup(popupTaxRule, true);


        return null;
    }

    private TaxRuleAMImpl getAm() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data.TaxRuleAMDataControl.dataProvider}", Object.class);
        return (TaxRuleAMImpl)valueExp.getValue(elContext);
    }


    public void NameValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
        String msg2 = "";
        if (object != null) {
            String DbobDesc = object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;
            char[] xx = DbobDesc.toCharArray();
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
            if (openB != closeB || closeFg == true || (DbobDesc.lastIndexOf("(") > DbobDesc.lastIndexOf(")")) ||
                (DbobDesc.indexOf(")") < DbobDesc.indexOf("("))) {
                msg2 = resolvEl("#{bundle['MSG.7']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (DbobDesc.contains("()")) {
                msg2 = resolvEl("#{bundle['MSG.49']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            if (DbobDesc.contains("(.") || DbobDesc.contains("(-") || DbobDesc.contains("-)")) {
                msg2 = resolvEl("#{bundle['MSG.162']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;


            //check for valid language name.Allowed- brackets,dots,hyphen
            String expression = "^(?:(?>[A-Za-z0-9 \\(\\)]+)()(\\%|\\@|\\_|\\.|\\-|\\$(?!\\.|\\-|$))?)*$";
            CharSequence inputStr = DbobDesc;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String error = resolvEl("#{bundle['MSG.97']}");

            if (matcher.matches()) {
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }
            int count = 0;
            String[] charval = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
            for (int i = 0; i < charval.length; i++) {
                Boolean val = DbobDesc.startsWith(charval[i]);
                if (val.equals(Boolean.TRUE)) {
                    count = count + 1;
                }
            }

            if (count > 0) {
                String numNotAllowMsg = resolvEl("#{bundle['MSG.52']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, numNotAllowMsg, null));
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

    public String editTaxRule() {

        /*  AppTaxRuleVOImpl am = (AppTaxRuleVOImpl)resolvElDC("AppTaxRuleAMDataControl");
        ViewObjectImpl taxRuleViewObject = am.getAppTaxRule();
        ViewObjectImpl taxRuleViewObject = am.getTaxRuleFinalised(); */
        /*   AppTaxRuleVORowImpl am = (AppTaxRuleVORowImpl)getAm().getAppTaxRule().getCurrentRow();
        String Finaliseval = am.getTaxRuleFinalised();
        System.out.println(Finaliseval);
        if (am != null) {
            if (Finaliseval.equalsIgnoreCase("N")) {
                flag = "E";
                showPopup(popupTaxRule, true);
            } else {
                String msg = resolvEl("#{bundle['MSG.163']}");
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }


        } */

        /** Popup will open in both condition when finalize is yes or no. On yes user can edit to_date and active and on NO user can edit rest of the field. */
        flag = "E";
        AppTaxRuleVORowImpl am = (AppTaxRuleVORowImpl)getAm().getAppTaxRule().getCurrentRow();
        String Finaliseval = am.getTaxRuleFinalised();
        System.out.println("Finaliseval = " + Finaliseval);
        finalize = Finaliseval;
        showPopup(popupTaxRule, true);
        return null;
    }


    public void refreshPage() {
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
        System.err.println("Problem trying to reload the page:");
        try {
            ExternalContext eContext = fContext.getExternalContext();
            String resourceUrl = actionUrl; //eContext.encodeResourceURL(actionUrl);
            // Use the action URL directly since the encoding a resource URL will NPE in isEmailablePage()
            eContext.redirect(resourceUrl);
        } catch (IOException ioe) {
            System.err.println("Problem trying to reload the page:");
            ioe.printStackTrace();
        }
    }

    public String moveUpAction() {

        /*  TaxRuleAMImpl am = getAm();
        am.getAppTaxRuleLine();

        AppTaxRuleLineVORowImpl taxrulelineRow = (AppTaxRuleLineVORowImpl)am.getAppTaxRuleLine().getCurrentRow();

        int b = taxrulelineRow.getTaxRulePriority();



        if (b == 0) {
       String error = resolvEl("#{bundle['MSG.164']}");

            FacesContext ctx = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, error, "");

            ctx.addMessage(null, fm);


        } else {


            taxrulelineRow.setTaxRulePriority(b - 1);
            am.getTransaction().commit();
            am.getAppTaxRuleLine().executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLineTable);

        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLineTable); */
       // int i = 1;
        AppTaxRuleLineVOImpl appTaxRuleLine = getAm().getAppTaxRuleLine();
        if (appTaxRuleLine != null) {
            Row[] allRowsInRange = appTaxRuleLine.getAllRowsInRange();
            System.out.println("allRowsInRange = " + allRowsInRange.length);
            int l = allRowsInRange.length;
            Row currentRow = appTaxRuleLine.getCurrentRow();
            Integer TaxRuleSlno = 1;
            Integer priority = 0;
            if (currentRow != null) {
               // TaxRuleSlno = (Integer)currentRow.getAttribute("TaxRuleSlno");
                priority = (Integer)currentRow.getAttribute("TaxRulePriority");
                System.out.println("priority = " + priority + " ----- >>");
                if (priority > 1) {
                    Row[] filteredRows = appTaxRuleLine.getFilteredRows("TaxRulePriority", priority - 1);
                    if (filteredRows.length>0) {
                        filteredRows[0].setAttribute("TaxRulePriority", priority);
                    }
                    currentRow.setAttribute("TaxRulePriority", priority - 1);
                    appTaxRuleLine.executeQuery();
                    getAm().getTransaction().commit();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLineTable);
                } else {
                    String error = resolvEl("#{bundle['MSG.164']}");

                    FacesContext ctx = FacesContext.getCurrentInstance();
                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, error, "");

                    ctx.addMessage(null, fm);
                }
            }


        }
        return null;
    }

    public String moveDownAction() {

        /* TaxRuleAMImpl am = getAm();
        am.getAppTaxRuleLine();

        AppTaxRuleLineVORowImpl taxrulelineRow = (AppTaxRuleLineVORowImpl)am.getAppTaxRuleLine().getCurrentRow();

        int a = taxrulelineRow.getTaxRulePriority();

        oracle.jbo.Row rr[] = am.getAppTaxRuleLine().getAllRowsInRange();
        int c = rr.length;


        if (a == (c - 1)) {
            String error = resolvEl("#{bundle['MSG.160']}");
            FacesContext ctx = FacesContext.getCurrentInstance();
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, error, "");

            ctx.addMessage(null, fm);

        } else {

            taxrulelineRow.setTaxRulePriority(a + 1);
            am.getTransaction().commit();
            am.getAppTaxRuleLine().executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLineTable);

        }
        */
    //    int i = 1;
        AppTaxRuleLineVOImpl appTaxRuleLine = getAm().getAppTaxRuleLine();
        if (appTaxRuleLine != null) {
            Row[] allRowsInRange = appTaxRuleLine.getAllRowsInRange();
            System.out.println("allRowsInRange = " + allRowsInRange.length);
            int l = allRowsInRange.length;
            Row currentRow = appTaxRuleLine.getCurrentRow();
            //Integer TaxRuleSlno = 1;
            Integer priority = 0;
            if (currentRow != null) {
              //  TaxRuleSlno = (Integer)currentRow.getAttribute("TaxRuleSlno");
                priority = (Integer)currentRow.getAttribute("TaxRulePriority");
                System.out.println("priority = " + priority + " ----- >>");
                if (priority < l-1) {
                    Row[] filteredRows = appTaxRuleLine.getFilteredRows("TaxRulePriority", priority + 1);
                    if (filteredRows.length>0) {
                        filteredRows[0].setAttribute("TaxRulePriority", priority);
                    }
                    currentRow.setAttribute("TaxRulePriority", priority+1);
                    appTaxRuleLine.executeQuery();
                    getAm().getTransaction().commit();
                    AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLineTable);
                } else {
                    String error = resolvEl("#{bundle['MSG.160']}");
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, error, "");

                    ctx.addMessage(null, fm);
                }
            }


        }
        return null;
    }

    public String deleteTaxRuleLineAction() {


        TaxRuleAMImpl am = getAm();
        am.getAppTaxRuleLine();

        AppTaxRuleLineVORowImpl taxrulelineRow = (AppTaxRuleLineVORowImpl)am.getAppTaxRuleLine().getCurrentRow();

        taxrulelineRow.remove();
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLineTable);
        showPopup(deleteTaxRuleDtl, true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLineTable);


        return null;
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

    public String createTaxRuleDetails() {

        AppTaxRuleLineVOImpl appTaxRuleLine = getAm().getAppTaxRuleLine();
        //  AppTaxRuleLineVORowImpl currRow = (AppTaxRuleLineVORowImpl)getAm().getAppTaxRuleLine().getCurrentRow();
        String cld_id =  resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString(); 
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString()); 
        String ho_org_id =  resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString(); 
       // Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString()); 
        
         if(isBasicCoaExist.equals("N"))   
           isBasicCoaExist = (String) callStoredFunction(Types.VARCHAR, "fn_is_basic_coa_exist(?,?,?)", new Object[]{cld_id,sloc_id,ho_org_id});
        
        if(isBasicCoaExist.equals("Y")){
             if (appTaxRuleLine != null) {
                                Row[] allRowsInRange = appTaxRuleLine.getAllRowsInRange();
                                if (allRowsInRange != null) {
                                    int length = allRowsInRange.length;
                                    System.out.println("length after create");
                                    if (length == 0) {
                                        BindingContainer bindings = getBindings();
                                        OperationBinding operationBinding = bindings.getOperationBinding("Createwithparameters");
                                        ViewObjectImpl coaLOV1 = getAm().getCoaLOV1();
                                        coaLOV1.setNamedWhereClauseParam("SlocIdBindVar", sloc_id);
                                        coaLOV1.setNamedWhereClauseParam("CloudIdBindVar", cld_id);
                                        coaLOV1.setNamedWhereClauseParam("OrgIdBindVar", ho_org_id);
                                        coaLOV1.setNamedWhereClauseParam("NA_Id", 1);
                                        coaLOV1.executeQuery();
                                        Row[] inRange = coaLOV1.getAllRowsInRange();
                                        System.out.println("filteredRows.length = " + inRange.length);
                                        if (inRange.length > 0) {
                                            Object CoaId = inRange[0].getAttribute("CoaId");
                                            System.out.println("CoaId = " + CoaId);
                                            operationBinding.getParamsMap().put("TaxRuleCoaId", (Integer)CoaId);
                                        }
                                        // coa.setNamedWhereClauseParam("CoaId", arg1);
                    
                                        //  BindingContainer bindings = getBindings();
                                        operationBinding.execute();
                                        OperationBinding operationBinding1 = bindings.getOperationBinding("Commit");
                                        operationBinding1.execute();
                                        System.out.println("aftercommit");
                                    }
                                }
                            }
                            BindingContainer bindings = getBindings();
                            OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
                            operationBinding.execute();
                    
                    
                            TaxRuleAMImpl am = getAm();
                            AppTaxRuleVORowImpl ruleCurr = (AppTaxRuleVORowImpl)am.getAppTaxRule().getCurrentRow();
                            String tds = ruleCurr.getTaxTypeTds();
                            AppTaxRuleLineVORowImpl currRow = (AppTaxRuleLineVORowImpl)am.getAppTaxRuleLine().getCurrentRow();
                    
                            RowSet rs = currRow.getTaxNameLOV();
                            System.out.println("taxTypeTdsBinding.getValue().toString() = "+taxTypeTdsBinding.getValue().toString());
                            if (taxTypeTdsBinding.getValue().toString().equalsIgnoreCase("true")) {
                                currRow.setAttribute("TaxRuleBalType", "Cr");
                            }else{
                                currRow.setAttribute("TaxRuleBalType", "Dr");
                    
                            }
                            rs.setNamedWhereClauseParam("TYPE", tds);
                            rs.executeQuery();
                            showPopup(popupNew, true);
                            flag = "A";
        }else{
            FacesMessage message=new FacesMessage(FacesMessage.SEVERITY_ERROR,"Please First Create Basic Coa For the Current Head Office",null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        return null;
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

    public void callStoredProcedure(String stmt, Object[] bindVars) {
        PreparedStatement st = null;
        try {
            st = getAm().getDBTransaction().createPreparedStatement("begin " + stmt + "; end;", 0);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 1, bindVars[z]);
                }
            }
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if (e.getMessage().length() < 11) {

                throw new JboException(e.getMessage());
            } else {
                int end = e.getMessage().indexOf("\n");
                throw new JboException(e.getMessage().substring(11, end));
            }
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    throw new JboException(e.getMessage());
                }
            }
        }
    }

    public void okCancelDialogListener(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("ok")) {
            System.out.println("coaId------------>" + coaId.getValue());
            if (coaId.getValue() != null && coaId.getValue().toString().length() > 0) {
                /*     AppTaxRuleLineVOImpl appTaxRuleLine = getAm().getAppTaxRuleLine();
                if (appTaxRuleLine != null) {
                    Row currentRow = appTaxRuleLine.getCurrentRow();
                    if (currentRow != null) {
                        Integer TaxRuleSlocId = (Integer)currentRow.getAttribute("TaxRuleSlocId");
                        String TaxCldId = (String)currentRow.getAttribute("TaxCldId");
                        Object TaxHoOrgId = currentRow.getAttribute("TaxHoOrgId");
                        Integer TaxId = (Integer)currentRow.getAttribute("TaxId");
                        Integer TaxRulePriority = (Integer)currentRow.getAttribute("TaxRulePriority");
                        ViewObjectImpl appTax1 = getAm().getAppTax1();
                        if (appTax1 != null) {
                            appTax1.setNamedWhereClauseParam("BindSlocId", TaxRuleSlocId);
                            appTax1.setNamedWhereClauseParam("BindCldId", TaxCldId);
                            appTax1.setNamedWhereClauseParam("BindHoOrgId", TaxHoOrgId);
                            appTax1.setNamedWhereClauseParam("BindTaxId", (Integer)TaxId);
                            appTax1.executeQuery();
                            Row[] row = appTax1.getAllRowsInRange();
                            System.out.println("currentRow.length -=" + row.length);
                            if (currentRow != null) {
                                Object basic = row[0].getAttribute("Countbasic");
                                System.out.println("basic = " + basic);
                                if (basic != null) {

                                    if ((Integer)basic > 0 && TaxRulePriority != 0 && 1==0) {
                                        FacesMessage message =
                                            new FacesMessage("BASIC Tax rule line should have 0 priority");
                                        message.setSeverity(FacesMessage.SEVERITY_ERROR);

                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, message);
                                    } else { */
                BindingContainer bindings = getBindings();

                DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppTaxRuleIterator");


                oracle.jbo.Key parentKey = parentIter.getCurrentRow().getKey();


                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();
                TaxRuleAMImpl am = getAm();

                AppTaxRuleVORowImpl currRow = (AppTaxRuleVORowImpl)am.getAppTaxRule().getCurrentRow();
                Integer P_TAX_RULE_ID = currRow.getTaxRuleId();
                Integer P_SLOC_ID = currRow.getTaxRuleSlocId();
                String P_HO_ORG_ID = currRow.getTaxHoOrgId();
                String P_CLD_ID = currRow.getTaxCldId();
                callStoredProcedure("APP.APP_ARRANGE_SRNO_TAX_RULE_LINE(?,?,?,?)",
                                    new Object[] { P_TAX_RULE_ID, P_SLOC_ID, P_HO_ORG_ID, P_CLD_ID });

                if (operationBinding.getErrors().isEmpty()) {

                    OperationBinding operationBinding1 = bindings.getOperationBinding("Execute1");
                    operationBinding1.execute();
                    if (getFlag().equals("A")) {
                        String msg = resolvEl("#{bundle['MSG.91']}");
                        FacesMessage message = new FacesMessage(msg);
                        message.setSeverity(FacesMessage.SEVERITY_INFO);

                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                        flag = "V";
                    } else if (getFlag().equals("E")) {
                        String msg = resolvEl("#{bundle['MSG.92']}");
                        FacesMessage message = new FacesMessage(msg);
                        message.setSeverity(FacesMessage.SEVERITY_INFO);

                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                        flag = "V";
                    }
                    parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));

                } else {
                    String msg = resolvEl("#{bundle['LBL.212']}") + " is required.";
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);

                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            } else {
                FacesMessage message = new FacesMessage("COA is required. Please select COA!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }


        }


        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLineTable);
    }

    public void okCancelDialogListenerRule(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("ok")) {
            int countBasic = 0;
            System.out.println("flag = " + flag);
            TaxRuleAMImpl am = getAm();
            AppTaxRuleVORowImpl currRow = (AppTaxRuleVORowImpl)am.getAppTaxRule().getCurrentRow();
            String finalise = currRow.getTaxRuleFinalised();
            if (finalise.equalsIgnoreCase("N")) {
                performCommitOperation();
            } else {
                ViewObjectImpl vo = am.getAppTaxRuleLine();
                int count = 0;
                if (vo != null) {
                    count = vo.getRowCount();
                    System.out.println("count = " + count);
                    if (count < 2) {
                        System.out.println("in if when count<=2");
                        String msg =
                            resolvEl("#{bundle['MSG.161']}"); // Please add one basic line and atleast one other tax before finalising the tax rule!.
                        FacesMessage message = new FacesMessage(msg);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);

                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    } else {

                        RowSetIterator rsi = vo.createRowSetIterator(null);
                        while (rsi.hasNext() && countBasic == 0) {
                            Row next = rsi.next();
                            Object TaxId = next.getAttribute("TaxId");
                            Integer TaxRuleSlocId = (Integer)next.getAttribute("TaxRuleSlocId");
                            String TaxCldId = (String)next.getAttribute("TaxCldId");
                            String TaxHoOrgId = (String)next.getAttribute("TaxHoOrgId");
                            System.out.println("taxid = " + TaxId + "TaxRuleSlocId = " + TaxRuleSlocId +
                                               " TaxCldId = " + TaxCldId + " TaxHoOrgId = " + TaxHoOrgId);
                            if (TaxId != null) {

                                ViewObjectImpl appTax1 = am.getAppTax1();
                                if (appTax1 != null) {
                                    appTax1.setNamedWhereClauseParam("BindSlocId", TaxRuleSlocId);
                                    appTax1.setNamedWhereClauseParam("BindCldId", TaxCldId);
                                    appTax1.setNamedWhereClauseParam("BindHoOrgId", TaxHoOrgId);
                                    appTax1.setNamedWhereClauseParam("BindTaxId", (Integer)TaxId);
                                    appTax1.executeQuery();
                                    Row[] currentRow = appTax1.getAllRowsInRange();
                                    System.out.println("currentRow.length -=" + currentRow.length);
                                    if (currentRow != null) {
                                        Object basic = currentRow[0].getAttribute("Countbasic");
                                        System.out.println("basic = " + basic);
                                        if (basic != null) {
                                            if ((Integer)basic == 1) {
                                                countBasic = 1;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (countBasic == 1) {
                            performCommitOperation();
                        } else {
                            FacesMessage message = new FacesMessage("Please add one basic tax rule line");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);

                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        }
                    }
                }
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleTable);

    }

    public void performCommitOperation() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();


        if (operationBinding.getErrors().isEmpty()) {
            if (getFlag().equals("A")) {
                System.out.println("----in add mode----");
                OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");
                operationBinding1.execute();
                OperationBinding operationBinding2 = bindings.getOperationBinding("Rollback");
                operationBinding2.execute();
                String msg = resolvEl("#{bundle['MSG.91']}");
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_INFO);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                flag = "V";
                // finalize="N";
            } else if (getFlag().equals("E")) {
                DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppTaxRuleIterator");

                //   System.out.println("----in edit mode----");
                oracle.jbo.Key parentKey = parentIter.getCurrentRow().getKey();

                OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");
                operationBinding1.execute();

                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                String msg = resolvEl("#{bundle['MSG.92']}");
                FacesMessage message = new FacesMessage(msg);
                message.setSeverity(FacesMessage.SEVERITY_INFO);

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                flag = "V";
                //finalize=p_finalize;
            }
        }
    }

    public void popUpCancelListener(PopupCanceledEvent popupCanceledEvent) {


        BindingContainer bindings = getBindings();

        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppTaxRuleIterator");

        oracle.jbo.Key parentKey = parentIter.getCurrentRow().getKey();

        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();


        OperationBinding operationBinding1 = bindings.getOperationBinding("Execute1");
        operationBinding1.execute();

        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));


        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLineTable);
        popupTaxRule.setResetEditableValues(RichPopup.RESET_EDITABLE_VALUES_WHEN_CANCELED);
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLineTable);

    }


    public void popUpCancelListenerRule(PopupCanceledEvent popupCanceledEvent) {

        if (flag.equals("A")) {

            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();


            BindingContainer bindings1 = getBindings();
            OperationBinding operationBinding1 = bindings1.getOperationBinding("Execute");
            operationBinding1.execute();
            // popupTaxRule.setResetEditableValues(RichPopup.RESET_EDITABLE_VALUES_WHEN_CANCELED);
        } else if (flag.equals("E")) {
            BindingContainer bindings = getBindings();

            DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppTaxRuleIterator");


            oracle.jbo.Key parentKey = parentIter.getCurrentRow().getKey();


            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();


            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute");
            operationBinding1.execute();

            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleTable);
        flag = "V";
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleTable);
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }


    public void setPopupNew(RichPopup popupNew) {
        this.popupNew = popupNew;
    }

    public RichPopup getPopupNew() {
        return popupNew;
    }

    public String editTaxRuleDetails() {
        TaxRuleAMImpl am = getAm();
        AppTaxRuleVORowImpl ruleCurr = (AppTaxRuleVORowImpl)am.getAppTaxRule().getCurrentRow();
        String tds = ruleCurr.getTaxTypeTds();
        AppTaxRuleLineVORowImpl currRow = (AppTaxRuleLineVORowImpl)am.getAppTaxRuleLine().getCurrentRow();
        RowSet rs = currRow.getTaxNameLOV();
        rs.setNamedWhereClauseParam("TYPE", tds);
        rs.executeQuery();
        flag = "E";
        showPopup(popupNew, true);
        return null;
    }


    public void setPopupTaxRule(RichPopup popupTaxRule) {
        this.popupTaxRule = popupTaxRule;
    }

    public RichPopup getPopupTaxRule() {
        return popupTaxRule;
    }

    public String DeleteTaxRule() {

        TaxRuleAMImpl am = getAm();
        am.getAppTaxRule();

        AppTaxRuleVORowImpl taxruleRow = (AppTaxRuleVORowImpl)am.getAppTaxRule().getCurrentRow();

        Integer id = taxruleRow.getTaxRuleId();

        AppTaxRuleLineVOImpl aa = am.getAppTaxRuleLine();
        Row[] rw = aa.getFilteredRowsInRange("TaxRuleId", id);
        int count = rw.length;

        if (count == 0) {

            showPopup(deletePopup, true);

        } else {
            String error = resolvEl("#{bundle['MSG.18']}");
            FacesContext fctx = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(error);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            fctx.addMessage(null, message);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleTable);
        return null;
    }

    public void deleteDialogListenerTaxRule(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("yes")) {

            BindingContainer bindings = getBindings();
            OperationBinding deletebinding = bindings.getOperationBinding("Delete");
            deletebinding.execute();

            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();


            OperationBinding createOpBAddress = bindings.getOperationBinding("Execute");
            createOpBAddress.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleTable);


            flag = "V";

        } else if (dialogEvent.getOutcome().name().equals("no")) {


            AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleTable);

            flag = "V";
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleTable);

    }

    public void deleteTaxRuleDtlCancelListner(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();

        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppTaxRuleIterator");


        oracle.jbo.Key parentKey = parentIter.getCurrentRow().getKey();


        OperationBinding createOpBAddress = bindings.getOperationBinding("Rollback");
        createOpBAddress.execute();


        OperationBinding OpBAddress = bindings.getOperationBinding("Execute1");
        OpBAddress.execute();
        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLineTable);
        flag = "V";
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLineTable);
    }

    public void deleteTaxRuleDialogListner(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {

            BindingContainer bindings = getBindings();

            DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppTaxRuleIterator");


            oracle.jbo.Key parentKey = parentIter.getCurrentRow().getKey();


            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();

            TaxRuleAMImpl am = getAm();

            AppTaxRuleVORowImpl currRow = (AppTaxRuleVORowImpl)am.getAppTaxRule().getCurrentRow();
            Integer P_TAX_RULE_ID = currRow.getTaxRuleId();
            Integer P_SLOC_ID = currRow.getTaxRuleSlocId();
            String P_HO_ORG_ID = currRow.getTaxHoOrgId();
            String P_CLD_ID = currRow.getTaxCldId();
            callStoredProcedure("APP.APP_ARRANGE_SRNO_TAX_RULE_LINE(?,?,?,?)",
                                new Object[] { P_TAX_RULE_ID, P_SLOC_ID, P_HO_ORG_ID, P_CLD_ID });

            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Execute1");
            createOpBAddress.execute();
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
            AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLineTable);

        } else if (dialogEvent.getOutcome().name().equals("no")) {

            BindingContainer bindings = getBindings();

            DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AppTaxRuleIterator");


            oracle.jbo.Key parentKey = parentIter.getCurrentRow().getKey();


            OperationBinding createOpBAddress = bindings.getOperationBinding("Rollback");
            createOpBAddress.execute();


            OperationBinding OpBAddress = bindings.getOperationBinding("Execute1");
            OpBAddress.execute();
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
            AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLineTable);
            flag = "V";
            AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLineTable);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLineTable);
    }

    public void setDeletePopup(RichPopup deletePopup) {
        this.deletePopup = deletePopup;
    }

    public RichPopup getDeletePopup() {
        return deletePopup;
    }


    public void setDeleteTaxRuleDtl(RichPopup deleteTaxRuleDtl) {
        this.deleteTaxRuleDtl = deleteTaxRuleDtl;
    }

    public RichPopup getDeleteTaxRuleDtl() {
        return deleteTaxRuleDtl;
    }


    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setTaxRuleTable(RichTable taxRuleTable) {
        this.taxRuleTable = taxRuleTable;
    }

    public RichTable getTaxRuleTable() {
        return taxRuleTable;
    }

    public void setTaxRuleLineTable(RichTable taxRuleLineTable) {
        this.taxRuleLineTable = taxRuleLineTable;
    }

    public RichTable getTaxRuleLineTable() {
        return taxRuleLineTable;
    }

    public void duplicateTaxValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {

            Integer val = (Integer)object;
            TaxRuleAMImpl am = getAm();
            AppTaxRuleLineVOImpl v = (AppTaxRuleLineVOImpl)am.getAppTaxRuleLine();

            Row cRow = v.getCurrentRow();

            int totalCount = v.getRowCount(); //get ALL rows
            int rangeSize = v.getRangeSize(); //all in range
            v.setRangeSize(totalCount);
            Row[] rArray = v.getAllRowsInRange();

            int count = 0;
            Integer TaxId = null;

            for (Row r : rArray) {

                if (!r.equals(cRow)) {
                    try {
                        TaxId = (Integer)r.getAttribute("TaxId");
                    } catch (NullPointerException npe) {
                        System.out.println("NPE:" + npe);
                        TaxId = null;
                    }

                    if (TaxId.compareTo(val) == 0) {
                        count = count + 1;
                    }
                }

            }
            v.setRangeSize(rangeSize); //set to original range

            if (count > 0) {
                String msg2 = resolvEl("#{bundle['MSG.165']}");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }


        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleTable);

    }

    public void priorityValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Integer priority = (Integer)object;
            if (priority <= 0) {
                String error = resolvEl("#{bundle['MSG.166']}");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }
        }
    }

    public void setCoaId(RichSelectOneChoice coaId) {
        this.coaId = coaId;
    }

    public RichSelectOneChoice getCoaId() {
        return coaId;
    }

    public void setOnloadVal(Integer onloadVal) {
        this.onloadVal = onloadVal;
    }

    public Integer getOnloadVal() {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("on_load_page");
        Object ret = operationBinding.execute();

        return (Integer)ret;
    }

    public void setZero(oracle.jbo.domain.Number zero) {
        this.zero = zero;
    }

    public oracle.jbo.domain.Number getZero() {
        return zero;
    }


    public void setPriorityBinding(RichInputText priorityBinding) {
        this.priorityBinding = priorityBinding;
    }

    public RichInputText getPriorityBinding() {
        return priorityBinding;
    }

    public void setFinalize(String finalize) {
        this.finalize = finalize;
    }

    public String getFinalize() {
        System.out.println("finalize == " + finalize + "---->>>");
        return finalize;
    }

    public void activeValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
AdfFacesContext.getCurrentInstance().addPartialTarget(taxRuleLineTable);    }

    public void setTaxTypeTdsBinding(RichSelectBooleanCheckbox taxTypeTdsBinding) {
        this.taxTypeTdsBinding = taxTypeTdsBinding;
    }

    public RichSelectBooleanCheckbox getTaxTypeTdsBinding() {
        return taxTypeTdsBinding;
    }
    
    
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
                CallableStatement st = null;
                try {
                    
                    st = getAm().getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
                    st.registerOutParameter(1,sqlReturnType);
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
                        throw new JboException(e);
                        }
                    }
                }
            }

}
