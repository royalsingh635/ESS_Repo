package graphTickerApp.view.bean;

import graphTickerApp.model.module.GraphTickerAMImpl;
import graphTickerApp.model.view.FinActivityVOImpl;
import graphTickerApp.model.view.FinActivityVORowImpl;
import graphTickerApp.model.view.FinSecTkrUsrVORowImpl;
import graphTickerApp.model.view.TickerDataVOImpl;
import graphTickerApp.model.view.TickerDataVORowImpl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class TickerDataBean {
    private String statementType;
    String val = "ajeet";
    String op_curr = null;
    String op_val = null;
    String op_val_type = null;
    String op_val_unit = null;
    String trend_val = null;
    String trend_val_unit = null;
    String trend_val_typ = null;
    String org_id = "01";
    String tkr1 = null;
    String tkr2 = null;
    String tkr3 = null;
    String op_curr_bank = null;
    String op_val_bank = null;
    String op_val_type_bank = null;
    String op_val_unit_bank = null;
    String trend_val_bank = null;
    String trend_val_unit_bank = null;
    String trend_val_typ_bank = null;
    String org_id_bank = "01";

    String show_currency=null;
    String op_curr_cash = null;
    String op_val_cash = null;
    String op_val_type_cash = null;
    String op_val_unit_cash = null;
    String trend_val_cash = null;
    String trend_val_unit_cash = null;
    String trend_val_typ_cash = null;
    String org_id_cash = "01";
    private RichPopup msgPopup;
    private static String old_msg;
    private static int NUMBER = Types.INTEGER;
    private static int STRING = Types.VARCHAR;
    private static int VARCHAR = Types.VARCHAR;
    private RichOutputText tkrName1;
    static Integer tkrid1 = 0;
    static Integer tkrid2 = 0;
    static Integer tkrid3 = 0;
    private RichPopup tickerPopup;
    private RichPopup viewOption;

    private String up1 = null;
    private String up2 = null;
    private String up3 = null;

    private static String amo = "A";
    private static Integer docId = null;
    private RichPopup tkrPopup;
    private RichPopup filterGraphPopup;
    private RichTable tickerPosTable;
    Integer ticker_id = 0;
    String curr_bank=null;
    String curr_cash;


    public TickerDataBean() {

        System.out.println("In Ticker Data Bean");
        //String OrgId = "01";
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String HoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        //Integer slocId = 1;
        Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String callTYpe = "S";
        String callVal = "01";
        Integer pusrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        callStoredFunction(NUMBER, "FN_INS_TKR_DATA(?,?,?,?,?,?,?)",
                           new Object[] { CldId, HoOrgId, slocId, OrgId, pusrId, callTYpe, callVal });
        GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
        //ViewObjectImpl ve = am.getTickerData1();
        //ve.setWhereClause("FIN_TKR_USR_ID = 1 AND FIN_TKR_POS in (1,2,3) ");
        //    ve.executeQuery();
        //  RowSetIterator rowset= ve.getRowSetIterator();

        //Integer i =Integer.parseInt(ADFContext.getCurrent().getSecurityContext().getUserName());
        TickerDataVOImpl userAccessVO = (TickerDataVOImpl)am.getTickerDataVO1();
        userAccessVO.setWhereClause("FIN_TKR_USR_ID =" + pusrId + " AND FIN_TKR_POS in (1,2,3) ");
        userAccessVO.executeQuery();


        while (userAccessVO.hasNext()) {

            TickerDataVORowImpl userAccessRow = (TickerDataVORowImpl)userAccessVO.next();
            Integer tkrPos = userAccessRow.getFinTkrPos();
            String tkrNm = userAccessRow.getFinTkrShrtNm();
            System.out.println("tkrPos = "+tkrPos);

            if (tkrPos == 1) {
                System.out.println("userAccessRow = "+userAccessRow.getFinTkrOpCurr()+"--"+userAccessRow.getFinTkrOpVal());
                this.setShow_currency(userAccessRow.getFinTkrOpCurr());
                this.setTkr1(tkrNm);
                // String dbRoleName = userAccessRow.getUsrRoleNm();
                this.setOp_val(userAccessRow.getFinTkrOpVal());
                this.setOp_curr(userAccessRow.getFinTkrOpCurr());
                this.setOp_val_type(userAccessRow.getFinTkrOpValType());
                this.setOp_val_unit(userAccessRow.getFinTkrOpValUnit());
                this.setTrend_val(userAccessRow.getFinTkrTrendVal());
                this.setTrend_val_unit(userAccessRow.getFinTkrTrandValUnit());
                this.setTrend_val_typ(userAccessRow.getFinTkrTrandValType());
                this.setUp1(userAccessRow.getFinTkrTrandValType());
                tkrid1 = Integer.parseInt(userAccessRow.getFinTkrId().toString());


            }
            if (tkrPos == 2) {
                this.setTkr2(tkrNm);
                System.out.println("userAccessRow2 = "+userAccessRow.getFinTkrOpCurr());

                this.setCurr_bank(userAccessRow.getFinTkrOpCurr());
                this.setOp_val_bank(userAccessRow.getFinTkrOpVal());
                this.setOp_curr_bank(userAccessRow.getFinTkrOpCurr());
                this.setOp_val_type_bank(userAccessRow.getFinTkrOpValType());
                this.setOp_val_unit_bank(userAccessRow.getFinTkrOpValUnit());
                this.setTrend_val_bank(userAccessRow.getFinTkrTrendVal());
                this.setTrend_val_unit_bank(userAccessRow.getFinTkrTrandValUnit());
                this.setTrend_val_typ_bank(userAccessRow.getFinTkrTrandValType());
                this.setUp2(userAccessRow.getFinTkrTrandValType());
                tkrid2 = Integer.parseInt(userAccessRow.getFinTkrId().toString());


            }
            if (tkrPos == 3) {

                this.setTkr3(tkrNm);
                System.out.println("userAccessRow3 = "+userAccessRow.getFinTkrOpCurr());

                this.setCurr_cash(userAccessRow.getFinTkrOpCurr());

                this.setOp_val_cash(userAccessRow.getFinTkrOpVal());
                this.setOp_curr_cash(userAccessRow.getFinTkrOpCurr());
                this.setOp_val_type_cash(userAccessRow.getFinTkrOpValType());
                this.setOp_val_unit_cash(userAccessRow.getFinTkrOpValUnit());
                this.setTrend_val_cash(userAccessRow.getFinTkrTrendVal());
                this.setTrend_val_unit_cash(userAccessRow.getFinTkrTrandValUnit());
                this.setTrend_val_typ_cash(userAccessRow.getFinTkrTrandValType());
                this.setUp3(userAccessRow.getFinTkrTrandValType());
                tkrid3 = Integer.parseInt(userAccessRow.getFinTkrId().toString());

            }

        }
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

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
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


    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public void fun() {


    }

    public void setOp_curr(String op_curr) {
        this.op_curr = op_curr;
    }

    public String getOp_curr() {
        return op_curr;
    }

    public void setOp_val(String op_val) {
        this.op_val = op_val;
    }

    public String getOp_val() {
        return op_val;
    }

    public void setOp_val_type(String op_val_type) {
        this.op_val_type = op_val_type;
    }

    public String getOp_val_type() {
        return op_val_type;
    }

    public void setOp_val_unit(String op_val_unit) {
        this.op_val_unit = op_val_unit;
    }

    public String getOp_val_unit() {
        return op_val_unit;
    }

    public void setTrend_val(String trend_val) {
        this.trend_val = trend_val;
    }

    public String getTrend_val() {
        return trend_val;
    }

    public void setTrend_val_unit(String trend_val_unit) {
        this.trend_val_unit = trend_val_unit;
    }

    public String getTrend_val_unit() {
        return trend_val_unit;
    }

    public void setTrend_val_typ(String trend_val_typ) {
        this.trend_val_typ = trend_val_typ;
    }

    public String getTrend_val_typ() {
        return trend_val_typ;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOp_curr_bank(String op_curr_bank) {
        this.op_curr_bank = op_curr_bank;
    }

    public String getOp_curr_bank() {
        return op_curr_bank;
    }

    public void setOp_val_bank(String op_val_bank) {
        this.op_val_bank = op_val_bank;
    }

    public String getOp_val_bank() {
        return op_val_bank;
    }

    public void setOp_val_type_bank(String op_val_type_bank) {
        this.op_val_type_bank = op_val_type_bank;
    }

    public String getOp_val_type_bank() {
        return op_val_type_bank;
    }

    public void setOp_val_unit_bank(String op_val_unit_bank) {
        this.op_val_unit_bank = op_val_unit_bank;
    }

    public String getOp_val_unit_bank() {
        return op_val_unit_bank;
    }

    public void setTrend_val_bank(String trend_val_bank) {
        this.trend_val_bank = trend_val_bank;
    }

    public String getTrend_val_bank() {
        return trend_val_bank;
    }

    public void setTrend_val_unit_bank(String trend_val_unit_bank) {
        this.trend_val_unit_bank = trend_val_unit_bank;
    }

    public String getTrend_val_unit_bank() {
        return trend_val_unit_bank;
    }

    public void setTrend_val_typ_bank(String trend_val_typ_bank) {
        this.trend_val_typ_bank = trend_val_typ_bank;
    }

    public String getTrend_val_typ_bank() {
        return trend_val_typ_bank;
    }

    public void setOrg_id_bank(String org_id_bank) {
        this.org_id_bank = org_id_bank;
    }

    public String getOrg_id_bank() {
        return org_id_bank;
    }

    public void setOp_curr_cash(String op_curr_cash) {
        this.op_curr_cash = op_curr_cash;
    }

    public String getOp_curr_cash() {
        return op_curr_cash;
    }

    public void setOp_val_cash(String op_val_cash) {
        this.op_val_cash = op_val_cash;
    }

    public String getOp_val_cash() {
        return op_val_cash;
    }

    public void setOp_val_type_cash(String op_val_type_cash) {
        this.op_val_type_cash = op_val_type_cash;
    }

    public String getOp_val_type_cash() {
        return op_val_type_cash;
    }

    public void setOp_val_unit_cash(String op_val_unit_cash) {
        this.op_val_unit_cash = op_val_unit_cash;
    }

    public String getOp_val_unit_cash() {
        return op_val_unit_cash;
    }

    public void setTrend_val_cash(String trend_val_cash) {
        this.trend_val_cash = trend_val_cash;
    }

    public String getTrend_val_cash() {
        return trend_val_cash;
    }

    public void setTrend_val_unit_cash(String trend_val_unit_cash) {
        this.trend_val_unit_cash = trend_val_unit_cash;
    }

    public String getTrend_val_unit_cash() {
        return trend_val_unit_cash;
    }

    public void setTrend_val_typ_cash(String trend_val_typ_cash) {
        this.trend_val_typ_cash = trend_val_typ_cash;
    }

    public String getTrend_val_typ_cash() {
        return trend_val_typ_cash;
    }

    public void setOrg_id_cash(String org_id_cash) {
        this.org_id_cash = org_id_cash;
    }

    public String getOrg_id_cash() {
        return org_id_cash;
    }

    public void setTkr1(String tkr1) {
        this.tkr1 = tkr1;
    }

    public String getTkr1() {
        return tkr1;
    }

    public void setTkr2(String tkr2) {
        this.tkr2 = tkr2;
    }

    public String getTkr2() {
        return tkr2;
    }

    public void setTkr3(String tkr3) {
        this.tkr3 = tkr3;
    }

    public String getTkr3() {
        return tkr3;
    }

    public String createAction() {


        GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
        //ViewObjectImpl ve = am.getTickerData1();
        //ve.setWhereClause("FIN_TKR_USR_ID = 1 AND FIN_TKR_POS in (1,2,3) ");
        //    ve.executeQuery();
        //  RowSetIterator rowset= ve.getRowSetIterator();


        FinActivityVOImpl userAccessVO = (FinActivityVOImpl)am.getFinActivity1();
        // userAccessVO.setWhereClause("FIN_TKR_USR_ID = 1 AND FIN_TKR_POS in (1,2,3) ");
        // userAccessVO.executeQuery();


        FinActivityVORowImpl userAccessRow = (FinActivityVORowImpl)userAccessVO.getCurrentRow();

        Integer slocID = userAccessRow.getActivitySlocId();
        String threadId = "1";
        String threadPrntId = userAccessRow.getActivityThreadId();
        Timestamp actvtyTS = userAccessRow.getActivityTs();
        Integer userId = 1;
        Integer docID = userAccessRow.getActivityDocId();
        String docAction = userAccessRow.getActivityDocAction();
        String doc_txn_id = userAccessRow.getActivityDocTxnId();
        Timestamp threadDT = userAccessRow.getActivityDocTxnDt();
        Integer TxnTypeId = userAccessRow.getActivityDocTxnTypId();
        old_msg = userAccessRow.getActivityMsg();


        String activity_thread_Id =
            (String)callStoredFunction(VARCHAR, "app.pkg_app.get_activity_id(?,?)", new Object[] { userId, docID });


        BindingContext bindingctx = BindingContext.getCurrent();
        BindingContainer bindings = bindingctx.getCurrentBindingsEntry();
        OperationBinding createOpBAddress = bindings.getOperationBinding("CreateInsert");
        createOpBAddress.execute();
        FinActivityVOImpl userAccessVO1 = (FinActivityVOImpl)am.getFinActivity1();
        // userAccessVO.setWhereClause("FIN_TKR_USR_ID = 1 AND FIN_TKR_POS in (1,2,3) ");
        // userAccessVO.executeQuery();


        FinActivityVORowImpl userAccessRow1 = (FinActivityVORowImpl)userAccessVO.getCurrentRow();

        userAccessRow1.setActivitySlocId(slocID);
        userAccessRow1.setActivityThreadId(activity_thread_Id);
        userAccessRow1.setActivityThreadIdParent(threadPrntId);
        //  userAccessRow.setActivityTs(actvtyTS);
        userAccessRow1.setActivityUsrId(userId);
        userAccessRow1.setActivityDocId(docID);
        userAccessRow1.setActivityDocAction(docAction);
        userAccessRow1.setActivityDocTxnId(doc_txn_id);
        userAccessRow1.setActivityDocTxnDt(threadDT);
        userAccessRow1.setActivityDocTxnTypId(TxnTypeId);

        Integer slocID1 = userAccessRow1.getActivitySlocId();
        String threadId1 = "1";
        String threadPrntId1 = userAccessRow1.getActivityThreadId();
        Timestamp actvtyTS1 = userAccessRow1.getActivityTs();
        Integer userId1 = 1;
        Integer docID1 = userAccessRow1.getActivityDocId();
        String docAction1 = userAccessRow1.getActivityDocAction();
        String doc_txn_id1 = userAccessRow1.getActivityDocTxnId();
        Timestamp threadDT1 = userAccessRow1.getActivityDocTxnDt();
        Integer TxnTypeId1 = userAccessRow1.getActivityDocTxnTypId();

        //userAccessRow.getDBTransaction().commit();


        showPopup(msgPopup, true);
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

    public void setMsgPopup(RichPopup msgPopup) {
        this.msgPopup = msgPopup;
    }

    public RichPopup getMsgPopup() {
        return msgPopup;
    }

    public void saveDialogListener(DialogEvent dialogEvent) {
        // Add event code here...

        if (dialogEvent.getOutcome().name().equals("ok")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();

            // BindingContainer binding = getBindings();
            //OperationBinding createOpBAddress = binding.getOperationBinding("Execute");
            // createOpBAddress.execute();
            //refreshPage();


        } else if (dialogEvent.getOutcome().name().equals("cancel")) {

            BindingContainer binding = getBindings();
            OperationBinding createOpBAddress = binding.getOperationBinding("Rollback");
            createOpBAddress.execute();

            BindingContainer bindings = getBindings();
            OperationBinding OpBAddress = bindings.getOperationBinding("Execute");
            OpBAddress.execute();
        }

    }


    public void popupCanceledListener(PopupCanceledEvent popupCanceledEvent) {

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();


    }

    public BindingContainer getBindings() {
        BindingContext bindingctx = BindingContext.getCurrent();
        return bindingctx.getCurrentBindingsEntry();


    }

    public void setOld_msg(String old_msg) {
        this.old_msg = old_msg;
    }

    public String getOld_msg() {
        return old_msg;
    }

    public String goToPnLStatement() {
        this.statementType = "PNL";
        return "callDss";
    }

    public String goToBalanceSheet() {
        this.statementType = "BS";
        return "callDss";
    }

    public String goToTrialBalance() {
        this.statementType = null;
        return "callDss";
    }

    public void setStatementType(String statementType) {
        this.statementType = statementType;
    }

    public String getStatementType() {
        return statementType;
    }

    public void configure1link(ActionEvent actionEvent) {

      /*   String tkrnm1 = tkrName1.getValue().toString();

        String s = "1";
        Integer i = 1;
        String abc =
            (String)callStoredFunction(VARCHAR, "PKG_FIN_GUI.FN_TKR_POPULATE_TO_TMP(?,?,?)", new Object[] { s, i,
                                                                                                            tkrid1 }); 
        showPopup(tickerPopup, true);
      */
        
    }

    public void setTkrName1(RichOutputText tkrName1) {
        this.tkrName1 = tkrName1;
    }

    public RichOutputText getTkrName1() {
        return tkrName1;
    }

    public void setTickerPopup(RichPopup tickerPopup) {
        this.tickerPopup = tickerPopup;
    }

    public RichPopup getTickerPopup() {
        return tickerPopup;
    }

    public void configure2link(ActionEvent actionEvent) {

        String s = "1";
        Integer i = 1;
        String abc =
            (String)callStoredFunction(VARCHAR, "PKG_FIN_GUI.FN_TKR_POPULATE_TO_TMP(?,?,?)", new Object[] { s, i,
                                                                                                            tkrid2 });
        showPopup(tickerPopup, true);
    }

    public void configure3link(ActionEvent actionEvent) {

        String s = "1";
        Integer i = 1;
        String abc =
            (String)callStoredFunction(VARCHAR, "PKG_FIN_GUI.FN_TKR_POPULATE_TO_TMP(?,?,?)", new Object[] { s, i,
                                                                                                            tkrid3 });
        showPopup(tickerPopup, true);
    }

    public static void setTkrid1(Integer tkrid1) {
        TickerDataBean.tkrid1 = tkrid1;
    }

    public static Integer getTkrid1() {
        return tkrid1;
    }

    public static void setTkrid2(Integer tkrid2) {
        TickerDataBean.tkrid2 = tkrid2;
    }

    public static Integer getTkrid2() {
        return tkrid2;
    }

    public static void setTkrid3(Integer tkrid3) {
        TickerDataBean.tkrid3 = tkrid3;
    }

    public static Integer getTkrid3() {
        return tkrid3;
    }

    public void viewOptionDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();

          

        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();

           
            
        }
    }

    public void viewOPtionPopupCancel(PopupCanceledEvent popupCanceledEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
    }

    public void setViewOption(RichPopup viewOption) {
        this.viewOption = viewOption;
    }

    public RichPopup getViewOption() {
        return viewOption;
    }

    public void viewOptionLink(ActionEvent actionEvent) {
        Integer pusrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());

        GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
        ViewObjectImpl v = am.getFinSecTkrUsr1();
//System.out.println("pusrId in bean ticker data bean -- "+pusrId);
        v.setWhereClause(null);
        v.executeQuery();
        v.setWhereClause(" FIN_TKR_USR_ID  =" + pusrId+ " and fin_tkr_id in (select fin_tkr_id from fin$tkr where FIN_TKR_ACTV = 'Y' and FIN_TKR_VISB = 'Y')");
        v.executeQuery();
        showPopup(viewOption, true);
    }

    public void setUp1(String up1) {
        this.up1 = up1;
    }

    public String getUp1() {
        return up1;
    }

    public void setUp2(String up2) {
        this.up2 = up2;
    }

    public String getUp2() {
        return up2;
    }

    public void setUp3(String up3) {
        this.up3 = up3;
    }

    public String getUp3() {
        return up3;
    }

    public void upButtonForPos(ActionEvent actionEvent) {
        GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
        ViewObjectImpl v = am.getFinSecTkrUsr1();
        FinSecTkrUsrVORowImpl row = (FinSecTkrUsrVORowImpl)v.getCurrentRow();

        Integer i = row.getFinTkrPos();

        if (i == 3) {
            FacesMessage message = new FacesMessage("Cannot move up.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {
            if (i == 0) {
                Integer maxPos = getMaxVal();
             
                if (maxPos==3) {
                    Row[] rows = v.getFilteredRows("FinTkrPos", maxPos);
                    FinSecTkrUsrVORowImpl rowP = (FinSecTkrUsrVORowImpl)rows[0];
                    rowP.setFinTkrPos(i);
                    row.setFinTkrPos(maxPos);
                    
                } else {
                    
                    row.setFinTkrPos(maxPos+1);
                }

            } else {
                if (getMaxVal() == i) {
                    FacesMessage message = new FacesMessage("Cannot move up.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                }

                else {
                    Row[] rows = v.getFilteredRows("FinTkrPos", i + 1);
                    if (rows.length>0) {
                        FinSecTkrUsrVORowImpl rowP = (FinSecTkrUsrVORowImpl)rows[0];

                        rowP.setFinTkrPos(i);
                        row.setFinTkrPos(i + 1);
                        v.executeQuery();
                    }else{
                        FacesMessage message = new FacesMessage("Cannot move up.No Place is availble");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                }
                v.executeQuery();
            }
            Integer pusrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());

           

            v.setWhereClause(null);
            v.executeQuery();
            v.setWhereClause(" FIN_TKR_USR_ID  =" + pusrId + " and fin_tkr_id in (select fin_tkr_id from fin$tkr where FIN_TKR_ACTV = 'Y' and FIN_TKR_VISB = 'Y')");
            v.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(tickerPosTable);

        }
    }

    private Integer getMaxVal() {
        GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
        ViewObjectImpl v = am.getFinSecTkrUsr1();
        Integer pos = 0;
        Integer max = 0;

        Row row[] = v.getAllRowsInRange();
        for (Row r : row) {
            try {
                pos = (Integer)r.getAttribute("FinTkrPos");
            } catch (NullPointerException e) {
                // TODO: Add catch code
                e.printStackTrace();
            }

            if (pos > max) {
                max = pos;
            }

        }


        return max;
    }

    public void downButtonForPos(ActionEvent actionEvent) {
        GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
        ViewObjectImpl v = am.getFinSecTkrUsr1();
        FinSecTkrUsrVORowImpl row = (FinSecTkrUsrVORowImpl)v.getCurrentRow();
        System.out.println("row count "+v.getRowCount());

        Integer i = row.getFinTkrPos();
        System.out.println("Row count is " +i);
        
        if (i == 0 || i==1) {
            FacesMessage message = new FacesMessage("Cannot move down.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {
            
                    Row[] rows = v.getFilteredRows("FinTkrPos", i - 1);
            if (rows.length>0) {
                FinSecTkrUsrVORowImpl rowP = (FinSecTkrUsrVORowImpl)rows[0];

                rowP.setFinTkrPos(i);
                row.setFinTkrPos(i - 1);
                v.executeQuery();
            }else{
                FacesMessage message = new FacesMessage("Cannot move down.No Place Availble");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
            }
         }
              
        Integer pusrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());    
        v.setWhereClause(null);
        v.executeQuery();
        v.setWhereClause("FIN_TKR_USR_ID  =" + pusrId + " and fin_tkr_id in (select fin_tkr_id from fin$tkr where FIN_TKR_ACTV = 'Y' and FIN_TKR_VISB = 'Y')");
        v.executeQuery();
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(tickerPosTable);
        

    }

    public void amovalueChange(ValueChangeEvent valueChangeEvent) {
        Integer pusrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
        ViewObjectImpl v = am.getAppActivityNew1();

        if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("A")) {
            v.setWhereClause("(activity_usr_id =" + pusrId +
                             " and activity_usr_typ = 'G') or (activity_usr_id is null and activity_usr_typ = 'O' and activity_thread_id not in (select activity_thread_id from app.app$activity$msg where activity_usr_id = " +
                             pusrId + " and activity_usr_typ = 'G'))");
            v.executeQuery();
        }
        if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("O")) {
            v.setWhereClause("ACTIVITY_USR_TYP = 'O' and activity_thread_id not in (select activity_thread_id from APP.app$activity$msg where activity_usr_typ = 'G' and activity_usr_id = " +
                             pusrId + ")");
            v.executeQuery();
        }
        if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("M")) {
            v.setWhereClause("ACTIVITY_USR_ID =" + pusrId + " AND ACTIVITY_USR_TYP = 'G'");
            v.executeQuery();
        }

    }

    public void setAmo(String amo) {
        TickerDataBean.amo = amo;
    }

    public String getAmo() {
        return amo;
    }

    public void setDocId(Integer docId) {
        TickerDataBean.docId = docId;
    }

    public Integer getDocId() {
        return docId;
    }

    public void fiterActivityLink(ActionEvent actionEvent) {
        showPopup(tkrPopup, true);
    }

    public void setTkrPopup(RichPopup tkrPopup) {
        this.tkrPopup = tkrPopup;
    }

    public RichPopup getTkrPopup() {
        return tkrPopup;
    }

    public void forDocumentValueChange(ValueChangeEvent valueChangeEvent) {
        Integer doc = Integer.parseInt(valueChangeEvent.getNewValue().toString());
        GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
        ViewObjectImpl v = am.getAppActivityNew1();
        v.setNamedWhereClauseParam("BindDocId", doc);
        v.executeQuery();

    }

    public void filterGrphLink(ActionEvent actionEvent) {
     //   showPopup(filterGraphPopup, true);

    }

    public void filterGraphDialogListener(DialogEvent dialogEvent) {
        // Add event code here...
    }

   /*  public void docIdOnFilterGraph(ValueChangeEvent valueChangeEvent) {
        Integer doc = Integer.parseInt(valueChangeEvent.getNewValue().toString());
        GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
        ViewObjectImpl v = am.getOrgtrendNew1();
        v.setNamedWhereClauseParam("BindDocId", doc);
        v.executeQuery();

    } */

   /*  public void noofDaysValueChange(ValueChangeEvent valueChangeEvent) {
        Integer days = Integer.parseInt(valueChangeEvent.getNewValue().toString());
        GraphTickerAMImpl am = (GraphTickerAMImpl)resolvElDC("GraphTickerAMDataControl");
        ViewObjectImpl v = am.getOrgtrendNew1();
        v.setNamedWhereClauseParam("Days", days);
        v.executeQuery();
    } */

    public void setFilterGraphPopup(RichPopup filterGraphPopup) {
        this.filterGraphPopup = filterGraphPopup;
    }

    public RichPopup getFilterGraphPopup() {
        return filterGraphPopup;
    }

    public void setTickerPosTable(RichTable tickerPosTable) {
        this.tickerPosTable = tickerPosTable;
    }

    public RichTable getTickerPosTable() {
        return tickerPosTable;
    }


    public String configLink1() {
       setTicker_id(tkrid1);
        return "ticker";
    }

    public void setTicker_id(Integer ticker_id) {
        this.ticker_id = ticker_id;
    }

    public Integer getTicker_id() {
        return ticker_id;
    }

    public String configLink2() {
        setTicker_id(tkrid2);
         return "ticker";
    }

    public String configLink3() {
        setTicker_id(tkrid3);
         return "ticker";
    }


    public void setShow_currency(String show_currency) {
        this.show_currency = show_currency;
    }

    public String getShow_currency() {
        return show_currency;
    }

    public void setCurr_bank(String curr_bank) {
        this.curr_bank = curr_bank;
    }

    public String getCurr_bank() {
        return curr_bank;
    }

    public void setCurr_cash(String curr_cash) {
        this.curr_cash = curr_cash;
    }

    public String getCurr_cash() {
        return curr_cash;
    }
}

