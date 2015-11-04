package graphTickerApp.view.bean;

import graphTickerApp.model.module.GraphTickerAMImpl;
import graphTickerApp.model.views.AppSecUsrMnuVOImpl;

import graphTickerApp.model.views.AppSecUsrMnuVORowImpl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import oracle.adf.model.BindingContext;
import oracle.adf.view.faces.bi.component.chart.UIBarChart;
import oracle.adf.view.faces.bi.component.graph.UIGraph;
import oracle.adf.view.faces.bi.event.ClickEvent;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import oracle.dss.dataView.ComponentHandle;
import oracle.dss.dataView.DataComponentHandle;
import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.event.DisclosureEvent;

public class GraphBean {
    private RichSelectOneChoice trendSupplierAging;
    private UIGraph supplierAging;
    private RichSelectOneChoice trendMoneyFlow;
    private UIGraph moneyFlow;
    private RichSelectOneChoice trendCashFlow;
    private UIGraph cashFlow;
    private RichSelectOneChoice trendBankFlow;
    private UIGraph bankFlow;
    private UIGraph income;
    private RichSelectOneChoice trendIncome;
    private RichSelectOneChoice trendExpense;
    private UIGraph expense;
    private RichSelectOneChoice trendIncomeVsExpense;
    private UIGraph incomeVsExpense;
    private String GraphVisible[][] = new String[50][3];
    private Integer userId = UserIdVar();
    private Integer SlocId = SlocIdVar();
    private String Cldid=(String)resolveElExp("#{pageFlowScope.GLBL_APP_CLD_ID}");
    private String OrgId = OrgIdVar();
    private Integer MaxgraphId = MaxGraphId(userId, SlocId, OrgId,Cldid);

    private RichSelectOneChoice trendSupplierAgingInvoicewise;
    private UIGraph supplierAgingInvoicewise;
    private UIGraph topSuppPie;
    Integer coa_id = 0;
    private ArrayList grphVisible;
    private Integer onloadVal;
    private Map<String, Object> menuVisibleMap = new HashMap<String, Object>();
    private RichOutputText currDescBinding;
    private UIGraph cutomerGraphBinding;
    private RichOutputText currDescBinding1;
    private RichCommandImageLink refreshButton;
    private RichCommandImageLink refresh1;
    private RichCommandImageLink refresh2;
    private UIGraph bankGraph;
    private RichCommandImageLink refresh3;
    private UIGraph cashGraph;
    private UIBarChart bankBarGraph;
    private UIBarChart cashBarGraph;
    private UIBarChart topCreditorGraphBind;
    private RichOutputText orgCurrDescBind;
    private UIBarChart graphTopDebtBind;


    public GraphBean() {
        
      //  System.out.println("In Graph Bean");
        
        
        initilizeMenu();
        
        
        callForLoopMethod(userId, SlocId, OrgId, MaxgraphId);


    }
    
    public void initilizeMenu(){
       
       try {
           
       
                GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
                
                try{
                         AppSecUsrMnuVOImpl menuVO=(AppSecUsrMnuVOImpl)am.getAppSecUsrMnu1();
                        
                        menuVO.setBindUserId(this.userId);
                        menuVO.setBindServerID((this.SlocId).toString());
                        menuVO.setBindOrgId(this.OrgId);
                        menuVO.setBindCloudId(this.Cldid);
                        
                        menuVO.executeQuery();
                    
                       if (menuVO.first() != null) {
                           // Row appSecUsrMnuRow = appSecUsrMnuRIt.getCurrentRow();
                            
                           AppSecUsrMnuVORowImpl curRow=(AppSecUsrMnuVORowImpl) menuVO.getCurrentRow();
                           if(curRow.getUsrMnuTypeId()!=null) {
                               (this.menuVisibleMap).put((curRow.getUsrMnuId()).toString(), "Y");
                             //  System.out.println("inside setMenuParam() :" + curRow.getUsrMnuId());
                               
                           }
                           
                            while (menuVO.hasNext()) {
                                menuVO.next();
                                curRow=(AppSecUsrMnuVORowImpl) menuVO.getCurrentRow();
                                if(curRow.getUsrMnuTypeId()!=null) {
                                    (this.menuVisibleMap).put((curRow.getUsrMnuId()).toString(), "Y");
                                   // System.out.println("inside setMenuParam() :" + curRow.getUsrMnuId());
                                    
                                }                                
                             
                            }          
                           
                          
                        }
                        
                }
                catch(Exception e){
                    System.out.println("there is an error in getting list of menu");
                    e.printStackTrace();
                }
       }
       catch(Exception e) {
           System.out.println("there is an error in getting AM");
       }
        
    }
    
    
    /** 
    * Created by Priyank on 13-08-2013.
    * This method is used as getter in the EL expression to get the parameter value to set menu 
    * visble property true or false. It takes in one parameter from the EL which will be used as search 
    * key to get the parameter value. Default return value return is 'N'.
    * **/
    public Map getVisibleVal() {

        return new HashMap<Integer, String>() {

            @Override
            public String get(Object key) {

                if (key != null) {
                    Object c = key;
                    String retVal = "N";
                    if (menuVisibleMap.get(key.toString()) != null)
                        retVal = menuVisibleMap.get(key.toString()).toString();
                 //   System.out.println("inside getVisible() c :" + c + " retVal :" + retVal);

                    return retVal;
                } else
                    return "N";
            }
        };
    }

    private void callForLoopMethod(Integer UsrId, Integer Slocid, String Orgid, Integer MaxGraphid) {
        for (int i = 1; i <= MaxGraphid; i++) {
            
            String visVal = getVisibleValue(Cldid, UsrId, Slocid, Orgid, i);
            GraphVisible[i][2] = visVal;

        }
    }

    public static Integer UserIdVar() {
        return Integer.parseInt(resolveElExp("#{pageFlowScope.GLBL_APP_USR}").toString());
    }

    public static Integer SlocIdVar() {
        return Integer.parseInt(resolveElExp("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());

    }

    public static String OrgIdVar() {
        return (String)resolveElExp("#{pageFlowScope.GLBL_APP_USR_ORG}");
    }

    private Integer MaxGraphId(Integer UsrId, Integer Slocid, String Orgid,String Cldid) {

        return (Integer)callStoredFunction2(NUMBER, "APP.pkg_grph.fn_get_max_usr_grph_id(?,?,?,?)",
                                            new Object[] {Cldid, Slocid,Orgid, UsrId  });
    }

    private String getVisibleValue(String CldId,Integer UsrId, Integer Slocid, String Orgid, Integer Graphid) {
        return (String)callStoredFunction2(STRING, "APP.FN_GET_GRPH_VISIBLE_PROP(?,?,?,?,?)",
                                           new Object[] {CldId, Slocid,Orgid,  UsrId, Graphid});
    }
    private static int NUMBER = Types.INTEGER;
    private static int STRING = Types.VARCHAR;


    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
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

    protected Object callStoredFunction2(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
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

    public static Object resolveElExp(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object msg = valueExp.getValue(elContext);
        return msg;
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

    public String executeSupplierAging() {
        if (trendSupplierAging.getValue() != null) {
            Integer trendId = (Integer)trendSupplierAging.getValue();

            GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
            ViewObject suppAging = am.getSupplierAgingGraphVO();
            suppAging.setNamedWhereClauseParam("TrendID", trendId);
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams");
            operationBinding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(supplierAging);
        }
        return null;
    }

    public String executeMoneyFlow() {


        if (trendMoneyFlow.getValue() != null) {
            Integer trendId = (Integer)trendMoneyFlow.getValue();

            GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
            ViewObject suppAging = am.getMonetFlowVO();
            suppAging.setNamedWhereClauseParam("MoneyTrendID", trendId);
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams1");
            operationBinding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(moneyFlow);
        }
        return null;
    }

    public String executeCashFlow() {
        if (trendCashFlow.getValue() != null) {
            Integer trendId = (Integer)trendCashFlow.getValue();

            GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
            ViewObject suppAging = am.getCashFlowVO();
            suppAging.setNamedWhereClauseParam("CashTrendID", trendId);
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams2");
            operationBinding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(cashFlow);
        }
        return null;
    }

    public String executeBankFlow() {
        if (trendBankFlow.getValue() != null) {
            Integer trendId = (Integer)trendBankFlow.getValue();

            GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
            ViewObject suppAging = am.getBankFlowVO();
            suppAging.setNamedWhereClauseParam("BankTrendID", trendId);
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams3");
            operationBinding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(bankFlow);
        }
        return null;

    }

    public String executeIncome() {
        if (trendIncome.getValue() != null) {
            Integer trendId = (Integer)trendIncome.getValue();

            GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
            ViewObject suppAging = am.getIncomeVO();
            suppAging.setNamedWhereClauseParam("IncomeTrendID", trendId);
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams4");
            operationBinding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(income);
        }
        return null;
    }

    public String executeExpense() {
        if (trendExpense.getValue() != null) {
            Integer trendId = (Integer)trendExpense.getValue();

            GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
            ViewObject suppAging = am.getExpenceVO();
            suppAging.setNamedWhereClauseParam("ExpenceTrendID", trendId);
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams5");
            operationBinding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(expense);
        }
        return null;
    }

    public String executeIncomeVsExpense() {
        if (trendIncomeVsExpense.getValue() != null) {
            Integer trendId = (Integer)trendIncomeVsExpense.getValue();

            GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
            ViewObject suppAging = am.getIncomeVsExpence();
            suppAging.setNamedWhereClauseParam("IncExpTrendID", trendId);
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams6");
            operationBinding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(incomeVsExpense);
        }
        return null;
    }

    public String executeSupplierAgingInvoicewise() {

        if (trendSupplierAgingInvoicewise.getValue() != null) {
            Integer trendId = (Integer)trendSupplierAgingInvoicewise.getValue();

            GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
            ViewObject suppAging = am.getSupplierAgingInvoiceWise1();
            suppAging.setNamedWhereClauseParam("SuppAgingIWTrendID", trendId);
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("ExecuteWithParams7");
            operationBinding.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(supplierAgingInvoicewise);
        }
        return null;
    }

    public void setTrendSupplierAging(RichSelectOneChoice trendSupplierAging) {
        this.trendSupplierAging = trendSupplierAging;
    }

    public RichSelectOneChoice getTrendSupplierAging() {
        return trendSupplierAging;
    }

    public void setSupplierAging(UIGraph supplierAging) {
        this.supplierAging = supplierAging;
    }

    public UIGraph getSupplierAging() {
        return supplierAging;
    }


    public void setTrendMoneyFlow(RichSelectOneChoice trendMoneyFlow) {
        this.trendMoneyFlow = trendMoneyFlow;
    }

    public RichSelectOneChoice getTrendMoneyFlow() {
        return trendMoneyFlow;
    }

    public void setMoneyFlow(UIGraph moneyFlow) {
        this.moneyFlow = moneyFlow;
    }

    public UIGraph getMoneyFlow() {
        return moneyFlow;
    }


    public void setTrendCashFlow(RichSelectOneChoice trendCashFlow) {
        this.trendCashFlow = trendCashFlow;
    }

    public RichSelectOneChoice getTrendCashFlow() {
        return trendCashFlow;
    }

    public void setCashFlow(UIGraph cashFlow) {
        this.cashFlow = cashFlow;
    }

    public UIGraph getCashFlow() {
        return cashFlow;
    }


    public void setTrendBankFlow(RichSelectOneChoice trendBankFlow) {
        this.trendBankFlow = trendBankFlow;
    }

    public RichSelectOneChoice getTrendBankFlow() {
        return trendBankFlow;
    }

    public void setBankFlow(UIGraph bankFlow) {
        this.bankFlow = bankFlow;
    }

    public UIGraph getBankFlow() {
        return bankFlow;
    }

    public void setIncome(UIGraph income) {
        this.income = income;
    }

    public UIGraph getIncome() {
        return income;
    }

    public void setTrendIncome(RichSelectOneChoice trendIncome) {
        this.trendIncome = trendIncome;
    }

    public RichSelectOneChoice getTrendIncome() {
        return trendIncome;
    }


    public void setTrendExpense(RichSelectOneChoice trendExpense) {
        this.trendExpense = trendExpense;
    }

    public RichSelectOneChoice getTrendExpense() {
        return trendExpense;
    }

    public void setExpense(UIGraph expense) {
        this.expense = expense;
    }

    public UIGraph getExpense() {
        return expense;
    }


    public void setTrendIncomeVsExpense(RichSelectOneChoice trendIncomeVsExpense) {
        this.trendIncomeVsExpense = trendIncomeVsExpense;
    }

    public RichSelectOneChoice getTrendIncomeVsExpense() {
        return trendIncomeVsExpense;
    }

    public void setIncomeVsExpense(UIGraph incomeVsExpense) {
        this.incomeVsExpense = incomeVsExpense;
    }

    public UIGraph getIncomeVsExpense() {
        return incomeVsExpense;
    }

    public void setGraphVisible(String[][] GraphVisible) {
        this.GraphVisible = GraphVisible;
    }

    public String[][] getGraphVisible() {
        return GraphVisible;
    }


    public void setTrendSupplierAgingInvoicewise(RichSelectOneChoice trendSupplierAgingInvoicewise) {
        this.trendSupplierAgingInvoicewise = trendSupplierAgingInvoicewise;
    }

    public RichSelectOneChoice getTrendSupplierAgingInvoicewise() {
        return trendSupplierAgingInvoicewise;
    }

    public void setSupplierAgingInvoicewise(UIGraph supplierAgingInvoicewise) {
        this.supplierAgingInvoicewise = supplierAgingInvoicewise;
    }

    public UIGraph getSupplierAgingInvoicewise() {
        return supplierAgingInvoicewise;
    }

    public String clickOnGraph() {

        return null;
    }

    public void setTopSuppPie(UIGraph topSuppPie) {
        this.topSuppPie = topSuppPie;
    }

    public UIGraph getTopSuppPie() {
        return topSuppPie;
    }

    public void clickOnGraph(ClickEvent clickEvent) {

       /*  ComponentHandle handle = clickEvent.getComponentHandle();
        if (handle instanceof DataComponentHandle) {
            DataComponentHandle dhandle = (DataComponentHandle)handle;


            Key key = (Key)dhandle.getValue(DataComponentHandle.ROW_KEY);
            GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
            ViewObject v = am.getTopSupplierOs1();
            Row row = v.getRow(key);

            setCoa_id(Integer.parseInt(row.getAttribute("CoaId").toString()));
            go(); 
        }*/
    }

    public String go() {
        return "billdetails";
    }

    public void setCoa_id(Integer coa_id) {
        this.coa_id = coa_id;
    }

    public Integer getCoa_id() {
        return coa_id;
    }


    public void setGrphVisible(ArrayList grphVisible) {
        this.grphVisible = grphVisible;
    }

    public ArrayList getGrphVisible() {
        return grphVisible;
    }
    public void setOnloadVal(Integer onloadVal) {
        this.onloadVal = onloadVal;
    }

    public Integer getOnloadVal() {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("on_load_page");
        Object ret= operationBinding.execute();
        
        return (Integer)ret;
    }

    public void setMenuVisibleMap(Map<String, Object> menuVisibleMap) {
        this.menuVisibleMap = menuVisibleMap;
    }

    public Map<String, Object> getMenuVisibleMap() {
        return menuVisibleMap;
    }

    public void Top5CustomerDisclosureListner(DisclosureEvent disclosureEvent) {
        /* if(disclosureEvent.isExpanded()){
            BindingContainer bc=BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding binding = bc.getOperationBinding("topCustomer");
            binding.execute();
        }  */
}

    public void BankInflowOutflowDL(DisclosureEvent disclosureEvent) {
        // Add event code here...bankInflowOoutflow
       /*  if(disclosureEvent.isExpanded()){
            BindingContainer bc=BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding binding = bc.getOperationBinding("bankInflowOoutflow");
            binding.execute();
        } 
        System.out.println("at the end of dl----------"); */
    }

    public void cashInflowOutflowDL(DisclosureEvent disclosureEvent) {
      /*   if(disclosureEvent.isExpanded()){
            BindingContainer bc=BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding binding = bc.getOperationBinding("cashInflowOoutflow");
            binding.execute();
        }  */    }

    public String refreshSupplierAction() {
        
            
        BindingContainer bc=BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding binding = bc.getOperationBinding("refreshSupplier");
        binding.execute();
        if(binding.getErrors().isEmpty()){
            System.out.println("00000000000000000000000");
            topCreditorGraphBind.setRendered(true);
            System.out.println("Graph rendered");
            orgCurrDescBind.setRendered(true);
            System.out.println("Currency Rendered");
           // refreshButton.setRendered(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(topCreditorGraphBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(orgCurrDescBind);
            //AdfFacesContext.getCurrentInstance().addPartialTarget(refreshButton);
            System.out.println("Exit from Function");
        }
        else{
            System.out.println("111111111111111111");
            FacesMessage message = new FacesMessage("Error in Displaying the Graph. Please Contact ESS!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        return null;
    }

    public void setCurrDescBinding(RichOutputText currDescBinding) {
        this.currDescBinding = currDescBinding;
    }

    public RichOutputText getCurrDescBinding() {
        return currDescBinding;
    }

    public void setCutomerGraphBinding(UIGraph cutomerGraphBinding) {
        this.cutomerGraphBinding = cutomerGraphBinding;
    }

    public UIGraph getCutomerGraphBinding() {
        return cutomerGraphBinding;
    }

    public void setCurrDescBinding1(RichOutputText currDescBinding1) {
        this.currDescBinding1 = currDescBinding1;
    }

    public RichOutputText getCurrDescBinding1() {
        return currDescBinding1;
    }

    public void setRefreshButton(RichCommandImageLink refreshButton) {
        this.refreshButton = refreshButton;
    }

    public RichCommandImageLink getRefreshButton() {
        return refreshButton;
    }

    public String RefreshCutomer() {
        BindingContainer bc=BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding binding = bc.getOperationBinding("topCustomer");
        binding.execute();
        if(binding.getErrors().isEmpty()){
            System.out.println("****************");
            graphTopDebtBind.setRendered(true);
            orgCurrDescBind.setRendered(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(graphTopDebtBind);
            AdfFacesContext.getCurrentInstance().addPartialTarget(orgCurrDescBind);
        }
        else{
            System.out.println("***************^^^^^");
            FacesMessage message = new FacesMessage("Error in Displaying the Graph. Please Contact ESS!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        return null;
    }

    public void setRefresh1(RichCommandImageLink refresh1) {
        this.refresh1 = refresh1;
    }

    public RichCommandImageLink getRefresh1() {
        return refresh1;
    }

    public void setRefresh2(RichCommandImageLink refresh2) {
        this.refresh2 = refresh2;
    }

    public RichCommandImageLink getRefresh2() {
        return refresh2;
    }

    public void setBankGraph(UIGraph bankGraph) {
        this.bankGraph = bankGraph;
    }

    public UIGraph getBankGraph() {
        return bankGraph;
    }

    public String refreshBank() {
        BindingContainer bc=BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding binding = bc.getOperationBinding("bankInflowOoutflow");
        binding.execute();
        if(binding.getErrors().isEmpty()){
            System.out.println("Returned to Bean");
            bankBarGraph.setRendered(true);
            System.out.println("Set Render True");
            AdfFacesContext.getCurrentInstance().addPartialTarget(bankBarGraph);
        }
        else{
            FacesMessage message = new FacesMessage("Error in Displaying the Graph. Please Contact ESS!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        return null;

    }

    public String refreshCash() {
        BindingContainer bc=BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding binding = bc.getOperationBinding("cashInflowOoutflow");
        binding.execute();
        if(binding.getErrors().isEmpty()){
            cashBarGraph.setRendered(true);
           // currDescBinding1.setRendered(true);
          //  refresh3.setRendered(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cashBarGraph);
         //   AdfFacesContext.getCurrentInstance().addPartialTarget(currDescBinding1);
            //AdfFacesContext.getCurrentInstance().addPartialTarget(refresh3);

        }
        else{
            FacesMessage message = new FacesMessage("Error in Displaying the Graph. Please Contact ESS!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }
        return null;      
    }

    public void setRefresh3(RichCommandImageLink refresh3) {
        this.refresh3 = refresh3;
    }

    public RichCommandImageLink getRefresh3() {
        return refresh3;
    }

    public void setCashGraph(UIGraph cashGraph) {
        this.cashGraph = cashGraph;
    }

    public UIGraph getCashGraph() {
        return cashGraph;
    }

    public void setBankBarGraph(UIBarChart bankBarGraph) {
        this.bankBarGraph = bankBarGraph;
    }

    public UIBarChart getBankBarGraph() {
        return bankBarGraph;
    }

    public void setCashBarGraph(UIBarChart cashBarGraph) {
        this.cashBarGraph = cashBarGraph;
    }

    public UIBarChart getCashBarGraph() {
        return cashBarGraph;
    }

    public void setTopCreditorGraphBind(UIBarChart topCreditorGraphBind) {
        this.topCreditorGraphBind = topCreditorGraphBind;
    }

    public UIBarChart getTopCreditorGraphBind() {
        return topCreditorGraphBind;
    }

    public void setOrgCurrDescBind(RichOutputText orgCurrDescBind) {
        this.orgCurrDescBind = orgCurrDescBind;
    }

    public RichOutputText getOrgCurrDescBind() {
        return orgCurrDescBind;
    }

    public void setGraphTopDebtBind(UIBarChart graphTopDebtBind) {
        this.graphTopDebtBind = graphTopDebtBind;
    }

    public UIBarChart getGraphTopDebtBind() {
        return graphTopDebtBind;
    }
}
