package chartofaccount.view.bean;

import chartofaccount.model.service.ChartOfAccountAMImpl;

import chartofaccount.model.view.FinCoaVOImpl;

import com.sun.corba.se.impl.interceptors.SlotTable;

import java.math.BigDecimal;

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

import oracle.adf.model.BindingContext;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.fragment.RichRegion;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

//import org.apache.poi.ss.formula.functions.Rows;

public class CoaAdd {

    
    private RichSelectBooleanCheckbox balShtBind;
    private RichSelectBooleanCheckbox pnlBind;
    private boolean ceateCOADisable = false;
    private boolean flag=false;
   
    private RichSelectOneChoice coaCogId;
    private RichSelectOneChoice coaBehav;
    private RichSelectOneChoice balanceType;
    private RichInputText coaName;
    private RichSelectOneChoice budgetCalcLogic;
    private RichSelectBooleanCheckbox coaBudget;
    private Boolean viewOrEditOpBalDis = true;
    private Boolean saveCancelButtonDis = false;
    private static int VARCHAR = Types.VARCHAR;
    private RichSelectOneChoice accIdCoaAddFormBind;
    ChartOfAccountAMImpl am = null;
    private Integer count =(Integer)getBindings().getOperationBinding("on_load_page").execute();
    private RichSelectBooleanCheckbox activeBind;
    private RichSelectBooleanCheckbox cashFlowBind;
    private RichSelectBooleanCheckbox trfBalBind;
    private static ADFLogger _adfLog = ADFLogger.createADFLogger(CoaAdd.class);
    // cao add lens code on page property disable = #{pageFlowScope.CoaAdd.ceateCOADisable || pageFlowScope.CoaAdd.flag }
    
    private String coaNameRound_exchng=null;
    private String defRoudOfValue="N";            //this is default value of round of check box to use in validation.
    private String defExngFlu="N";
    private RichSelectBooleanCheckbox partOfBudget;
   //this is default value of exchange flucation checkbox to use in validation.

   
    public CoaAdd() {
         this.am = (ChartOfAccountAMImpl)resolvElDC("ChartOfAccountAMDataControl");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("++++++ from constructor +++++++ of COA Create");
        Integer SLOC_ID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        System.out.println("SLOC_ID = "+SLOC_ID);
        String HO_ORG_ID = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        System.out.println("HO_ORG_ID = "+HO_ORG_ID);
        String CLD_ID = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        System.out.println("CLD_ID = "+CLD_ID);
        //Integer NA_IDD = Integer.parseInt(resolvEl("#{pageFlowScope.Acc_ID}"));
        //System.out.println(" ACCID/NA_ID = "+ NA_IDD);
        //String tax_n= resolvEl("#{pageFlowScope.Tax_Nm}");
        //System.out.println("tax nm = "+tax_n);
        //String coa_a = resolvEl("#{pageFlowScope.Coa_Alias}");
      // System.out.println("----- coa alias = "+coa_a);
      String p_pg_call = resolvEl("#{pageFlowScope.PARAM_PG_CALLED}");
      System.out.println("===== coa_legid = "+p_pg_call);
      if(p_pg_call.equals("P")){
          flag = true;
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

    public void saveAdd(ActionEvent actionEvent) {
        
        if(this.coaName.getValue()==null){
            System.out.println("Object is null");
              String msg2 = resolvEl("#{bundle['MSG.2383']}").toString();  //Coa Name is Manadatory.
              FacesMessage message2 = new FacesMessage(msg2);
              message2.setSeverity(FacesMessage.SEVERITY_ERROR);
              throw new ValidatorException(message2);
        }
       
       
       
        System.out.println("Time During 1 :" + System.currentTimeMillis());
        Long t1=System.currentTimeMillis();
        System.out.println(">> saved button clicked");
        String a = balShtBind.getValue().toString();  // 
        String pnl1 = pnlBind.getValue().toString();
        String coaBud=coaBudget.getValue().toString();
        String actv = activeBind.getValue().toString();
        System.out.println("active = "+actv);
        
            
        Integer SLOC_ID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String HO_ORG_ID = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String CLD_ID = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer NA_ID = (Integer)am.getFinCoa1().getCurrentRow().getAttribute("CoaAccId");
        Timestamp  date = new Timestamp(System.currentTimeMillis());
        Integer usrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
       // System.out.println("SLOC_ID = "+SLOC_ID+"   HO_ORG_ID = "+HO_ORG_ID+"   CLD_ID = "+CLD_ID+"     NA_ID = "+NA_ID+"   date = "+date+"    usrId = "+usrId);
        String flag = "";
        try{    flag = (String)callStoredFunction2(VARCHAR, "CHECK_COA_CREATE_ALLOWED(?,?,?,?)", new Object[] { NA_ID, HO_ORG_ID,CLD_ID, SLOC_ID});
                    //System.out.println("MULTIPLE COA CREATE ALLOWED OR NOT :"+flag);
        } 
        catch (Exception e) {       // System.out.println("EXCEPTION IN CALLING FUNCTION :=>CHECK_COA_CREATE_ALLOWED");
        }
        Long t2=System.currentTimeMillis();
        System.out.println("Time at 2"+t2);
        System.out.println("Time taken for 1 process"+ (t2-t1));
        System.out.println();
//            System.out.println(":"+accIdCoaAddFormBind.getValue()+":");
//            System.out.println("------------------------------------------------------------------");
//            System.out.println("coaCogId.getValue() = "+coaCogId.getValue());
//            System.out.println("accIdCoaAddFormBind.getValue() = "+accIdCoaAddFormBind.getValue());
            System.out.println("coaBehav.getValue() = "+coaBehav.getValue());
            System.out.println("balanceType.getValue() = "+balanceType.getValue());
//            System.out.println("coaName.getValue() = "+coaName.getValue());
//            System.out.println("coaBud = coaBudget.getValue().toString() = "+coaBud);
//            System.out.println("budgetCalcLogic.getValue() = "+budgetCalcLogic.getValue());
            System.out.println("coaBehav = "+ coaBehav.getValue()+"     balanceType = "+balanceType.getValue()+
                "    trfBalBind = "+trfBalBind.getValue()+"  cashFlowBind = "+cashFlowBind.getValue()+"   pnlBind = "+pnlBind.getValue() );
       
       
       
       
        String saveData = "Y";
        if(coaCogId.getValue()==null){
            saveData ="N";
            String msg1 = resolvEl("#{bundle['MSG.236']}").toString();  //COG is Required.
            FacesMessage message = new FacesMessage(msg1);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //  throw new ValidatorException(message);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message); 
        }
        else if(accIdCoaAddFormBind.getValue() == null){
            saveData ="N";
            String msg1 = resolvEl("#{bundle['MSG.2630']}").toString(); 
            FacesMessage message = new FacesMessage(msg1);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //  throw new ValidatorException(message);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message); 
        }
        else if(coaBehav.getValue()==null){
            saveData ="N";
            String msg1 = resolvEl("#{bundle['MSG.237']}").toString();  //Behavior is Required.
            FacesMessage message = new FacesMessage(msg1);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //  throw new ValidatorException(message);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        else if(balanceType.getValue()==null){
            saveData ="N";
            String msg1 = resolvEl("#{bundle['MSG.238']}").toString();  //Balance Type is Required.
            FacesMessage message = new FacesMessage(msg1);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //  throw new ValidatorException(message);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        else if(coaName.getValue()==null){
            saveData ="N";
            String msg1 = resolvEl("#{bundle['MSG.239']}").toString();  //MSG.239
            FacesMessage message = new FacesMessage(msg1);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //  throw new ValidatorException(message);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } 
        else if(coaBudget.isSelected() && budgetCalcLogic.getValue()==null){
            saveData ="N";
            String msg1 = resolvEl("#{bundle['MSG.2495']}");   //Part of budgeting should be selected
            FacesMessage message = new FacesMessage(msg1);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //  throw new ValidatorException(message);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        else if(flag.equals("N")){
            saveData ="N";
            FacesMessage message = new FacesMessage(":",resolvEl("#{bundle['MSG.228']}").toString());   //Multiple Ref in COA' for the selected NA is unchecked.\n So, only one COA can be created for the selected Natural Account.\none COA have already been created, cannot create another.
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //  throw new ValidatorException(message);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        else{
            if (a.equalsIgnoreCase("true") && pnl1.equalsIgnoreCase("true")) {
                saveData ="N";
                String msg1 = resolvElDCMsg("#{bundle['MSG.1']}").toString();  //Account can not be part of both Balance sheet and Profit-loss.
                FacesMessage message = new FacesMessage(msg1);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                //  throw new ValidatorException(message);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            } 
            else if (a.equalsIgnoreCase("false") && pnl1.equalsIgnoreCase("false")) {
                saveData ="N";
                String msg2 = resolvElDCMsg("#{bundle['MSG.3']}").toString();   //Account must be part of Balance sheet or Profit-loss.
                FacesMessage message = new FacesMessage(msg2);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }       
            else if (a.equalsIgnoreCase("false") && pnl1.equalsIgnoreCase("false")) {
                saveData ="N";
                String msg2 = resolvElDCMsg("#{bundle['MSG.3']}").toString();  //Account must be part of Balance sheet or Profit-loss.
                FacesMessage message = new FacesMessage(msg2);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }        
            else if((this.coaBudget.getValue().equals(true)) && (this.budgetCalcLogic.getValue() == "")){
                saveData ="N";
                FacesMessage message = new FacesMessage(":",resolvElDCMsg("#{bundle['MSG.224']}").toString());  //Selection should be in made in Budget Calc. Logic, when part of budget is checked.
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                //  throw new ValidatorException(message);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(this.budgetCalcLogic.getClientId(), message);
            }
            
           /*  else if ( (coaBehav.getValue().equals("A")||coaBehav.getValue().equals("E")) && balanceType.getValue().equals("Cr") ){      
                    System.out.println("ERROR = 1086 = Balance type for Asset/Expences should be Dr!!");
                    String msg1 = resolvEl("#{bundle['MSG.1086']}").toString();
                    FacesMessage message = new FacesMessage(msg1);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                
            }
            else if (   (coaBehav.getValue().equals("L")||coaBehav.getValue().equals("I")) && balanceType.getValue().equals("Dr")){
                        System.out.println("ERROR = 1087 = Balance type for Income/Liabilities should be Cr!!");
                        String msg1 = resolvEl("#{bundle['MSG.1087']}").toString();
                        FacesMessage message = new FacesMessage(msg1);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
            }
            else if(  (coaBehav.getValue().equals("A") || coaBehav.getValue().equals("L"))  && balShtBind.getValue().equals("false") ){
                System.out.println("ERROR = 1088 = COA should be a part of Balance Sheet for Assets/Liabilities!!");
                String msg1 = resolvEl("#{bundle['MSG.1088']}").toString();
                FacesMessage message = new FacesMessage(msg1);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);                                  
            } 
            else if(    (coaBehav.getValue().equals("I") || coaBehav.getValue().equals("E"))    && pnlBind.getValue().equals("false") ){
                System.out.println("ERROR = 1089 = COA should be a part of Profit And loss for Income/Expences!!");
                String msg1 = resolvEl("#{bundle['MSG.1089']}").toString();
                FacesMessage message = new FacesMessage(msg1);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message); 
            }
            else if(    (coaBehav.getValue().equals("I") || coaBehav.getValue().equals("E"))    && cashFlowBind.getValue().equals("true") ){
                System.out.println("ERROR = 1090 = Income/Expences COA can not be a part of Cash Flow!!");
                String msg1 = resolvEl("#{bundle['MSG.1090']}").toString();
                FacesMessage message = new FacesMessage(msg1);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message); 
            }
            else if(    (coaBehav.getValue().equals("I") || coaBehav.getValue().equals("E"))    && trfBalBind.getValue().equals("true") ){
                System.out.println("ERROR = 1091 = Income/Expences COA can not be a Transfer Balance Account!!");
                String msg1 = resolvEl("#{bundle['MSG.1091']}").toString();
                FacesMessage message = new FacesMessage(msg1);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message); 
            } */
            
            // code change by BL to Apply  Tigger

          
            _adfLog.info(balShtBind.getValue().equals(false)+"----611------"+balShtBind.getValue().toString());
            _adfLog.info(coaBehav.getValue().toString()+"----111------"+"A".equalsIgnoreCase(coaBehav.getValue().toString()));
            _adfLog.info(balanceType.getValue().toString()+"----211------"+"Cr".equalsIgnoreCase(balanceType.getValue().toString()));
            _adfLog.info(pnlBind.getValue().equals(true)+"----311------"+"Y".equalsIgnoreCase(pnlBind.getValue().toString()));
            _adfLog.info(cashFlowBind.getValue().equals(true)+"----411------"+"Y".equalsIgnoreCase(cashFlowBind.getValue().toString()));
            _adfLog.info(trfBalBind.getValue().equals(true)+"----511------"+"Y".equalsIgnoreCase(trfBalBind.getValue().toString()));
            if((("A".equalsIgnoreCase(coaBehav.getValue().toString())) || ("E".equalsIgnoreCase(coaBehav.getValue().toString()))) && ("Cr".equalsIgnoreCase(balanceType.getValue().toString())) ){
                _adfLog.info("ERROR = 1086 = Balance type for Asset/Expences should be Dr!!");
                saveData ="N";
                String msg1 = resolvEl("#{bundle['MSG.1086']}").toString();  //Balance type for Asset/Expenses should be Dr!!
                FacesMessage message = new FacesMessage(msg1);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
                if((("L".equalsIgnoreCase(coaBehav.getValue().toString())) || ("I".equalsIgnoreCase(coaBehav.getValue().toString()))) && ("Dr".equalsIgnoreCase(balanceType.getValue().toString())) ){
                    saveData ="N";
                    _adfLog.info("ERROR = 1087 = Balance type for Income/Liabilities should be Cr!!");
                    String msg1 = resolvEl("#{bundle['MSG.1087']}").toString();  //Balance type for Income/Liabilities should be Cr!!
                    FacesMessage message = new FacesMessage(msg1);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }
            if((("A".equalsIgnoreCase(coaBehav.getValue().toString())) || ("L".equalsIgnoreCase(coaBehav.getValue().toString()))) && (balShtBind.getValue().equals(false)) ){
                saveData ="N";
               _adfLog.info("ERROR = 1088 = COA should be a part of Balance Sheet for Assets/Liabilities!!");
                String msg1 = resolvEl("#{bundle['MSG.1088']}").toString();
                FacesMessage message = new FacesMessage(msg1);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);          
            }
            if(("I".equalsIgnoreCase(coaBehav.getValue().toString())) || ("E".equalsIgnoreCase(coaBehav.getValue().toString()))){
                _adfLog.info(pnlBind.getValue().equals(true)+"---------------------------------------"+"Y".equalsIgnoreCase(pnlBind.getValue().toString()));
                if(pnlBind.getValue().equals(false) || "N".equalsIgnoreCase(pnlBind.getValue().toString())){
                    saveData ="N";
                    _adfLog.info("ERROR = 1089 = COA should be a part of Profit And loss for Income/Expences!!");
                    String msg1 = resolvEl("#{bundle['MSG.1089']}").toString();
                    FacesMessage message = new FacesMessage(msg1);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message); 
                }else if(false){
                    saveData ="N";
                    _adfLog.info("ERROR = 1090 = Income/Expences COA can not be a part of Cash Flow!!");
                    String msg1 = resolvEl("#{bundle['MSG.1090']}").toString();
                    FacesMessage message = new FacesMessage(msg1);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message); 
                }else if(trfBalBind.getValue().equals(true) || "Y".equalsIgnoreCase(trfBalBind.getValue().toString())){
                    saveData ="N";
                    _adfLog.info("ERROR = 1091 = Income/Expences COA can not be a Transfer Balance Account!!");
                    String msg1 = resolvEl("#{bundle['MSG.1091']}").toString();
                    FacesMessage message = new FacesMessage(msg1);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message); 
                }
            }
            
        Long t3=System.currentTimeMillis();
        System.out.println("Time After All Validateion"+t3);
       System.out.println("Time taken for Validation is "+ (t3-t2));
             
            if("Y".equalsIgnoreCase(saveData)) {            
                System.out.println(">>>> all validation is passed now COMMIT");
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();
                //System.out.println("Commit 1:"+operationBinding.getErrors());
                if(operationBinding.getErrors().isEmpty()){
                System.out.println(">> no error in operation bindinG COMMIT ");
                   Long t6=System.currentTimeMillis(); 
                   
                 String u="N";
                  
                  // ChartOfAccountAMImpl am = (ChartOfAccountAMImpl)resolvElDC("ChartOfAccountAMDataControl");
                   
                    ViewObjectImpl v = am.getFinCoa1();
                    if (v != null) {
                        Row currentRow = v.getCurrentRow();
                        if (currentRow != null) {
                        
                            DBSequence coaid = (DBSequence)currentRow.getAttribute("CoaId");
                            if (coaid != null) {
                                Integer coa = (Integer)coaid.getSequenceNumber().intValue();


                                if (coa != null) {
                                    System.out.println("Current Coa Id --------"+coa);
                                    u =
                                        callStoredFunction(Types.VARCHAR, "fn_coa_operations(?,?,?,?,?,?,?,?,?,?,?)", new Object[] { CLD_ID,
                                                                                                                                     SLOC_ID,
                                                                                                                                     HO_ORG_ID,
                                                                                                                                     coa,
                                                                                                                                     null,
                                                                                                                                     null,
                                                                                                                                     null,
                                                                                                                                     null,
                                                                                                                                     usrId,
                                                                                                                                     date,
                                                                                                                                     "I" }).toString();
                                }
                            }
                        }
                    }
                      
                     if(u.equals("N")) 
                       u = callStoredFunction2(Types.VARCHAR, "fn_coa_operations(?,?,?,?,?,?,?,?,?,?,?)", 
                                new Object[] { CLD_ID,SLOC_ID,HO_ORG_ID,null,null,null,null,null,usrId,date,"I"}).toString();
                   
                   Long t7=System.currentTimeMillis();
                   
                   System.out.println("Time for function fn_coa_operations is "+ (t7-t6));
                    //System.out.println("fn_coa_operations(?,?,?,?,?,?,?,?,?,?,?) = "+u);
                    if((u.equals("Y"))){
                        //System.out.println(" u.equals(Y) = COMMIT ");
                        BindingContainer bindingss = getBindings();
                        System.out.println("before in sert data");
                        OperationBinding operationBindings = bindingss.getOperationBinding("insertDataInAppCCStruct");
                        operationBindings.execute();                       
                        operationBindings = bindingss.getOperationBinding("Commit");
                        operationBindings.execute();
                        //System.out.println("after commit = operationBindings.getErrors() = "+operationBindings.getErrors());
                        if (operationBindings.getErrors().isEmpty()) {
                                FacesMessage message = new FacesMessage(resolvElDCMsg("#{bundle['MSG.241']}").toString()); //COA Saved sucessfully!
                                message.setSeverity(FacesMessage.SEVERITY_INFO);
                                FacesContext fc = FacesContext.getCurrentInstance();
                                fc.addMessage(null, message);
                                ceateCOADisable = true;             
                                viewOrEditOpBalDis = false;
                                saveCancelButtonDis = true;
                        }
                    }
                    else{
                        /*  BindingContainer binding = getBindings();
                        OperationBinding operationBindin = binding.getOperationBinding("Execute");
                        operationBindin.execute();     
                        OperationBinding operationBindin1 = binding.getOperationBinding("Rollback");
                        operationBindin1.execute();    */ 
                        String msg = (String) resolvEl("#{bundle['MSG.2496']}");  //Error in Post COA operations
                        FacesMessage message = new FacesMessage(msg);
                        message.setSeverity(FacesMessage.SEVERITY_INFO);   
                        FacesContext fc = FacesContext.getCurrentInstance();   
                        fc.addMessage(null, message); 
                    }
                }
                else{
                    System.out.println(">> Error in Operation Binding COMMIT");
                    String msg = (String) resolvEl("#{bundle['MSG.2496']}");  //Error in Post COA operations
                    FacesMessage message = new FacesMessage(msg);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);   
                    FacesContext fc = FacesContext.getCurrentInstance();   
                    fc.addMessage(null, message);
                }
                
                
              Long t4=System.currentTimeMillis();  
             System.out.println("Time After Commiting " + t4);
             
             System.out.println("time taken for Comit" + (t4-t3));
                
            }
        }
    }

    protected Object callStoredFunction2(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        ChartOfAccountAMImpl am = (ChartOfAccountAMImpl)resolvElDC("ChartOfAccountAMDataControl");
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
                   // System.out.println(e.getMessage());
                }
            }
        }
    }


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
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

    public void coaNameValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        
        System.out.println("In Validation");
        if (object != null) {
        
        System.out.println("Org Id is " +object) ;
            String coaNm = object.toString();

            //String nameDesc=object.toString();
            int openB = 0;
            int closeB = 0;
            boolean closeFg = false;

            char[] xx = coaNm.toCharArray();
            if (xx[0] == '%' || xx[0] == '$' || xx[0] == '&') {
                String msg2 = resolvElDCMsg("#{bundle['MSG.9']}").toString();  //COA name must start with an Alphanumeric character.
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            for (char c : xx) {

                if (c == '(') {

                    openB = openB + 1;

                } else if (c == ')') {

                    closeB = closeB + 1;

                }

                if (closeB > openB) {
                    closeFg =
                            true; /**1.no. of closed brackets will not be more than open brackets at any given time.*/
                }
            }

            /**2.if openB=0 then no. of closing and opening brackets equal ||
                 * 3.opening bracket must always come before closing brackets
                 * 4.closing brackets must not come before first occurrence of openning bracket
                */
            if (openB != closeB || closeFg == true || (coaNm.lastIndexOf("(") > coaNm.lastIndexOf(")")) ||
                (coaNm.indexOf(")") < coaNm.indexOf("("))) {
                String msg2 = resolvEl("#{bundle['MSG.7']}").toString();  //Brackets not closed properly.
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            /**5.check for empty brackets
                 */
            if (coaNm.contains("()")) {
                String msg2 = resolvEl("#{bundle['MSG.49']}").toString();  //Empty Brackets are not allowed
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            /**check for to dot not comes together
                 */
            if (coaNm.contains("..")) {
                String msg2 = resolvEl("#{bundle['MSG.109']}").toString();  //Two dots (..)  together not allowed.
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            /**  check for wrong combo
                 */
            if (coaNm.contains("(.") || coaNm.contains("(-") || coaNm.contains("-)")) {
                String msg2 = resolvEl("#{bundle['MSG.59']}").toString();  //Invalid content. Check content inside brackets.
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }

            openB = 0;
            closeB = 0;
            closeFg = false;


            /**check for valid coanm .. Starts with character. can have dots .No two dots/:/'/'/_/@ can be adjacent.
                         *
                         */
            /* String expression =
                "^(?:(?>[A-Za-z0-9 % $)]+)(?:\\_|\\&|\\-|\\(|\\.|\\)|\\:|\\@|\\/|\\\\(?!\\_|\\&|\\%|\\-|\\:|\\@|\\/|\\\\|$))?)*$";*/
            String expression =
            "^(?:(?>[A-Za-z0-9 \\(\\)]+)(\\.|\\-(?!\\.|\\-|$))*(\\.|\\&(?!\\.|\\&|$))*(\\.|\\:(?!\\.|\\:|$))*" +
            "(\\.|\\/(?!\\.|\\/|$))*(\\.|\\;(?!\\.|\\;|$))*(\\.|\\%(?!\\.|\\%))*(\\.|\\\\(?!\\.|\\\\|$))*(\\.|\\,(?!\\.|\\,|$))?)*$";

            CharSequence inputStr = coaNm;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);

            String error = resolvElDCMsg("#{bundle['MSG.1185']}").toString();  //Alphanumeric character Ampersand(&amp;),Brackets, Dots(.),Forward Slash(/),backward Slash(\),comma(,),colon(:), semicolon(;),Percentage(%) and hyphen(-) not allowed.

            if (matcher.matches()) {
            } else {
                FacesMessage message = new FacesMessage("", error);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);

            }

            //check for duplicate rows
            Integer SLOC_ID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String HO_ORG_ID = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            String CLD_ID = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");

            String res =
                (String)callStoredFunction(VARCHAR, "fin.fn_is_coa_nm_unique(?,?,?,?,?)", new Object[] { CLD_ID,
                                                                                                         SLOC_ID,
                                                                                                         HO_ORG_ID,
                                                                                                         coaNm,
                                                                                                         null });
            /*   BindingContainer bc = getBindings();
            OperationBinding op = bc.getOperationBinding("coaValidator");
            op.getParamsMap().put("naName", coaNm);
            op.execute();
            Integer res = (Integer)op.getResult(); */
            System.out.println("result in bean = " + res);

            if (res.equalsIgnoreCase("N")) {
                    String msg = resolvEl("#{bundle['MSG.46']}").toString();
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));  //Duplicate record found
            }

        }
        else{
              System.out.println("Object is null");
                String msg2 = resolvEl("#{bundle['MSG.239']}").toString() ;  //COA Name is Required.
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            
        }

    }


    public void exchangeFluctuationValidation(FacesContext facesContext, UIComponent uIComponent, Object object) {
       
        Boolean val = (Boolean)object;
        String value = "";
        String coaNm = "";
        if (val == true) {
            value = "Y";
        } else if (val == false) {
            value = "N";
        }

       String msg4="";
        if (val == true) {
         
         String validate="N";
          try{
            System.out.println("--------------------------------value is "+this.defExngFlu);
              if(this.defExngFlu.equalsIgnoreCase("N")){
            
               //validate = (BigDecimal) callStoredFunction(Types.NUMERIC, "fin.fn_get_coa_count_ex_fluc(?,?,?)", new Object[] {getCld(),getSloc(),getHo()});
            
              validate= (String)  callStoredFunction3(Types.VARCHAR, "fin.fn_is_exchangFlu_acc_exist(?,?,?,?)", new Object[] {getCld(),getSloc(),getHo()});
            }
          }catch(Exception e){
              e.printStackTrace();
          }

             if (validate.equalsIgnoreCase("Y")) {


                msg4 = (String)resolvEl("#{bundle['MSG.2504']}") /*"another account named*/ + this.coaNameRound_exchng +" "  + resolvEl("#{bundle['MSG.232']}").toString();  //is already selected for Exchange Fluctuation.

                 String msg2 = resolvEl("#{bundle['MSG.233']}").toString();

                 FacesMessage message2 = new FacesMessage(msg4, msg2);

                 message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                 throw new ValidatorException(message2);
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

    public void setBalShtBind(RichSelectBooleanCheckbox balShtBind) {
        this.balShtBind = balShtBind;
    }

    public RichSelectBooleanCheckbox getBalShtBind() {
        return balShtBind;
    }

    public void setPnlBind(RichSelectBooleanCheckbox pnlBind) {
        this.pnlBind = pnlBind;
    }

    public RichSelectBooleanCheckbox getPnlBind() {
        return pnlBind;
    }

    public void setCeateCOADisable(boolean ceateCOADisable) {
        this.ceateCOADisable = ceateCOADisable;
    }

    public boolean isCeateCOADisable() {
        return ceateCOADisable;
    }

   

    

    public void reservedRevenueValid(FacesContext facesContext, UIComponent uIComponent, Object object) {
      
        if (object.equals(true) && coaCogId.getValue()!=null) {
            ViewObjectImpl v = am.getFinCoa1();
            FinCoaVOImpl coa1 = am.getFinCoaVO1();
            String COGID = "";
            try{
                COGID = this.coaCogId.getValue().toString();
            }catch(NullPointerException npe){
            }
            
            if(!COGID.equals("")){
            char[] array = COGID.toCharArray();
            char cogIdFirstDigit = array[0];
        
            if (!((cogIdFirstDigit == '2') || (cogIdFirstDigit == '1'))) {

              
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                             resolvEl("#{bundle['MSG.234']}").toString(),  //Reserved revenue should be checked when Account type is Either Asset or Liability
                                                              null));

            } else {
              
            }
        }
            coa1.setNamedWhereClauseParam("CoaCldIdBind", resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
            coa1.setNamedWhereClauseParam("SlocIdBind", resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            coa1.setNamedWhereClauseParam("CoaHoOrgIdBind", resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}"));
            coa1.executeQuery();
            Row[] row = coa1.getFilteredRows("CoaRr", "Y");
                        if (row.length > 0) {
                for(Row r : row)
                {
                    if(r.getAttribute("CoaId").toString().equals(v.getCurrentRow().getAttribute("CoaId").toString())
                    && r.getAttribute("CoaSlocId").toString().equals(v.getCurrentRow().getAttribute("CoaSlocId").toString())
                    && r.getAttribute("CoaHoOrgId").toString().equals(v.getCurrentRow().getAttribute("CoaHoOrgId").toString())
                    && r.getAttribute("CoaCldId").toString().equals(v.getCurrentRow().getAttribute("CoaCldId").toString()))
                    {
                       
                        }
                    else
                    {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resolvEl("#{bundle['LBL.159']}").toString()  /* "COA- " */ +r.getAttribute("CoaNm").toString()+ resolvEl("#{bundle['MSG.2641']}").toString() /* is already selected for Reserved Revenue account */, null));

                        }
                    }      
            } 
        }

    }

    public void setCoaCogId(RichSelectOneChoice coaCogId) {
        this.coaCogId = coaCogId;
    }

    public RichSelectOneChoice getCoaCogId() {
        return coaCogId;
    }

    public void setCoaBehav(RichSelectOneChoice coaBehav) {
        this.coaBehav = coaBehav;
    }

    public RichSelectOneChoice getCoaBehav() {
        return coaBehav;
    }

    public void setBalanceType(RichSelectOneChoice balanceType) {
        this.balanceType = balanceType;
    }

    public RichSelectOneChoice getBalanceType() {
        return balanceType;
    }

    public void setCoaName(RichInputText coaName) {
        this.coaName = coaName;
    }

    public RichInputText getCoaName() {
        return coaName;
    }

    public void setBudgetCalcLogic(RichSelectOneChoice budgetCalcLogic) {
        this.budgetCalcLogic = budgetCalcLogic;
    }

    public RichSelectOneChoice getBudgetCalcLogic() {
        return budgetCalcLogic;
    }

    public void setCoaBudget(RichSelectBooleanCheckbox coaBudget) {
        this.coaBudget = coaBudget;
    }

    public RichSelectBooleanCheckbox getCoaBudget() {
        return coaBudget;
    }

    public void setViewOrEditOpBalDis(Boolean viewOrEditOpBalDis) {
        this.viewOrEditOpBalDis = viewOrEditOpBalDis;
    }

    public Boolean getViewOrEditOpBalDis() {
        return viewOrEditOpBalDis;
    }

    public void setSaveCancelButtonDis(Boolean saveCancelButtonDis) {
        this.saveCancelButtonDis = saveCancelButtonDis;
    }

    public Boolean getSaveCancelButtonDis() {
        return saveCancelButtonDis;
    }

    

    public String cancelAct() {
        ceateCOADisable = false;
        viewOrEditOpBalDis = true;
        saveCancelButtonDis = false;
        return "cancel";
    }
    
    public void coaRoundVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Boolean val = (Boolean)object;
        String value = "";
        String coaNm = "";
        if (val == true) {
            value = "Y";
        } else if (val == false) {
            value = "N";
        }
       System.out.println("VALUE : "+val+" "+value);
        if (val == true) {
          
         
            String validate="N";
            try{   
               
                 //count = (BigDecimal) callStoredFunction(Types.NUMERIC, "fin.fn_get_coa_count_roundoff(?,?,?)", new Object[] {getCld(),getSloc(),getHo()});
                System.out.println("----------------------------------------------value is " + this.defRoudOfValue); 
                if(this.defRoudOfValue.equalsIgnoreCase("N")){
                     validate=(String) callStoredFunction3(Types.VARCHAR, "fin.fn_is_roundoff_acc_exist(?,?,?,?)", new Object[] {getCld(),getSloc(),getHo()});
                }
               }
               catch(Exception e){
                       e.printStackTrace();
               } 
                 
                  String msg4 = "";
               
                   if (validate.equalsIgnoreCase("Y")) {
                       msg4 = resolvEl("#{bundle['MSG.2501']}").toString()  /* Other Coa named */+ this.getCoaNameRound_exchng()+" " + resolvEl("#{bundle['MSG.244']}").toString();  //is already selected for Round account.
                       String msg2 = resolvEl("#{bundle['MSG.243']}").toString();

                       FacesMessage message2 = new FacesMessage(msg4, msg2);

                       message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                       throw new ValidatorException(message2);
                   }
            }


    }
    /**
     * Validation of unique NA_ACC and COGTYPE
     */
    public void coaGrpIdVAL(FacesContext facesContext, UIComponent uIComponent, Object object) {
        am = (ChartOfAccountAMImpl)resolvElDC("ChartOfAccountAMDataControl");
        ViewObjectImpl v2 = am.getFinCoa1();
        Row currRow = v2.getCurrentRow();
        //System.out.println("VALIDATOR---------------------------------- : "+object);
        if(this.accIdCoaAddFormBind.getValue() == null){
            String msg1 = resolvEl("#{bundle['MSG.236']}").toString();
            FacesMessage message = new FacesMessage(msg1); //COG is Required.
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //  throw new ValidatorException(message);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        else if(object != null && this.accIdCoaAddFormBind.getValue() != null && currRow.getAttribute("CoaAccId") != null){
            FinCoaVOImpl coavo = am.getFinCoa1();
            RowQualifier rowQualifier = new RowQualifier(coavo);
            String CogId = "";
                    Row[] xx = am.getLovCog1().getFilteredRows("CogNm", object.toString());
                    // Row [] xx=am.getLovInputItmId1().getFilteredRowsInRange("ItmId", inputitm);
                    //System.out.println("xx.length : "+xx.length);
                    if(xx.length>0){
                    CogId = xx[0].getAttribute("CogId").toString();
                    }
            // filtering data using method setWhereClause
                    if(true){
                        rowQualifier.setWhereClause("CoaAccId=" + (Integer)currRow.getAttribute("CoaAccId") + " and CoaCogId='"+object.toString()+"'");
                        Row[] rowI = coavo.getFilteredRows(rowQualifier);
                        // To get the current row
                        //System.out.println("CoaAccId = " + (Integer)currRow.getAttribute("CoaAccId"));
                        //System.out.println("CoaCogId = " + Integer.parseInt(object.toString()));
                        //System.out.println("_COUNT2 : "+rowI.length);
                        if(rowI.length > 1){
                            //System.out.println("_COUNT3 : "+rowI.length);
                            FacesMessage message2 = new FacesMessage(resolvEl("#{bundle['MSG.12']}").toString());  //Can not create COA, COA already exists for the selected NA or Group
                            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                            throw new ValidatorException(message2);
                        }
                    }
            
            
        }else if(object == null){
            FacesMessage message2 = new FacesMessage("", resolvEl("#{bundle['MSG.2624']}").toString()); //Account type cannot be null.
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        }
    }

    public void setAccIdCoaAddFormBind(RichSelectOneChoice accIdCoaAddFormBind) {
        this.accIdCoaAddFormBind = accIdCoaAddFormBind;
    }

    public RichSelectOneChoice getAccIdCoaAddFormBind() {
        return accIdCoaAddFormBind;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setActiveBind(RichSelectBooleanCheckbox activeBind) {
        this.activeBind = activeBind;
    }

    public RichSelectBooleanCheckbox getActiveBind() {
        return activeBind;
    }

    public void setCashFlowBind(RichSelectBooleanCheckbox cashFlowBind) {
        this.cashFlowBind = cashFlowBind;
    }

    public RichSelectBooleanCheckbox getCashFlowBind() {
        return cashFlowBind;
    }

    public void setTrfBalBind(RichSelectBooleanCheckbox trfBalBind) {
        this.trfBalBind = trfBalBind;
    }

    public RichSelectBooleanCheckbox getTrfBalBind() {
        return trfBalBind;
    }
    
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
      
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
                }
            }
        }
    }
    
    
    public String getHo(){
        

        String HO_ORG_ID = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
   
        return HO_ORG_ID;
    
        
    }
    
    public String getCld(){
        String CLD_ID = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");  
        return CLD_ID;
    }
    
    public Integer getSloc(){
        Integer SLOC_ID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        return  SLOC_ID;
    }
    
    
    protected Object callStoredFunction3(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        am = (ChartOfAccountAMImpl)resolvElDC("ChartOfAccountAMDataControl");
        try {
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.registerOutParameter(5, VARCHAR);
            st.executeUpdate();
            try {

                if (st.getObject(5)!=null) {
                   // setResult(st.getObject(5).toString());
                    this.setCoaNameRound_exchng(st.getObject(5).toString());
                }
            } catch (NullPointerException sqle) {
                sqle.printStackTrace();
            }

            return st.getObject(1);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    // System.out.println(e.getMessage());
                }
            }
        }
    }

    public void setCoaNameRound_exchng(String coaNameRound_exchng) {
        this.coaNameRound_exchng = coaNameRound_exchng;
    }

    public String getCoaNameRound_exchng() {
        return coaNameRound_exchng;
    }

    public void setDefRoudOfValue(String defRoudOfValue) {
        this.defRoudOfValue = defRoudOfValue;
    }

    public String getDefRoudOfValue() {
        return defRoudOfValue;
    }

    public void setDefExngFlu(String defExngFlu) {
        this.defExngFlu = defExngFlu;
    }

    public String getDefExngFlu() {
        return defExngFlu;
    }

    public void budgetCalLogicVCE(ValueChangeEvent vcee) {
        
        if (vcee.getNewValue() != null) {
            if (vcee.getNewValue().toString().length() > 0) {
                this.coaBudget.setValue(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.coaBudget);
            } else {
                this.coaBudget.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.coaBudget);
            }
        }else {
                this.coaBudget.setValue(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.coaBudget);
            }
        
    }

    public void setPartOfBudget(RichSelectBooleanCheckbox partOfBudget) {
        this.partOfBudget = partOfBudget;
    }

    public RichSelectBooleanCheckbox getPartOfBudget() {
        return partOfBudget;
    }

    public void budgetVCE(ValueChangeEvent valueChangeEvent) {
        if(this.coaBudget.isSelected()){
                         
        
        }else{
            
                System.out.println("Value change listener");
                this.budgetCalcLogic.setValue(null);
                
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.budgetCalcLogic);
        }
        
        
    }

  
}

