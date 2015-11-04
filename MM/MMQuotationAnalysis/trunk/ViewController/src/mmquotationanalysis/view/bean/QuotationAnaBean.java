package mmquotationanalysis.view.bean;

import adf.utils.model.ADFModelUtils;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
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

import mmquotationanalysis.model.services.quotationAnaAMImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import oracle.jbo.domain.Number;
public class QuotationAnaBean {
    private RichPopup rfqPopUp;
    private RichSelectBooleanCheckbox autoRateCheckBox;
    private RichInputText perfScoreBind;
    private RichSelectOneChoice paramSetIdBind;
    private RichSelectOneChoice paramSetIdNewBind;
    private RichTable qabindTab;
    private RichInputText perfEvalBind;

    public QuotationAnaBean() {
    }
    private static int NUMBER = Types.NUMERIC;
    private static int DATE = Types.DATE;
    private static int STRING = Types.VARCHAR;
    private String reEvaluateRfq = "F";
    private boolean rateReadonly = false;
    private Integer minRate;
    private Integer maxRate;
    String currSetId = null;
ADFLogger adfLog=(ADFLogger)ADFLogger.createADFLogger(QuotationAnaBean.class);
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

    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            quotationAnaAMImpl am = (quotationAnaAMImpl)resolvElDC("quotationAnaAMDataControl");
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

    protected Object[] callStoredFunctionOut(String stmt, Object[] bindVars) {
        quotationAnaAMImpl am = (quotationAnaAMImpl)resolvElDC("quotationAnaAMDataControl");
        CallableStatement st = null;
        try {
            /** 1. Create a JDBC CallabledStatement */

            st = am.getDBTransaction().createCallableStatement("begin " + stmt + ";end;", 0);
            /** 2. Register the first bind variable for the return value. and last 2 variable for output variable of function. */

            st.setObject(1, bindVars[0]);
            st.setObject(2, bindVars[1]);
            st.setObject(3, bindVars[2]);
            st.setObject(4, bindVars[3]);
            st.registerOutParameter(5, STRING);
            st.registerOutParameter(6, STRING);
            /** 5. Set the value of user-supplied bind vars in the stmt */
            st.executeUpdate();

            /** 6. set value of eoid and eomstid */


            Object arr[] = { st.getObject(5), st.getObject(6) };

            //  System.out.println("Values=--->" + st.getObject(4).toString());
            return arr;
        } catch (SQLException e) {
            int end = e.getMessage().indexOf("\n");

            throw new JboException(e.getMessage());
        } finally {
            if (st != null) {
                try {
                    /** 7. Close the statement */
                    st.close();
                } catch (SQLException e) {

                }
            }
        }
    }


    protected void callStoredProcedure(String stmt, Object[] bindVars) {
        quotationAnaAMImpl am = (quotationAnaAMImpl)resolvElDC("quotationAnaAMDataControl");
        PreparedStatement st = null;


        try {
            // 1. Create a JDBC PreparedStatement for
            st = am.getDBTransaction().createPreparedStatement("begin " + stmt + ";end;", 0);
            if (bindVars != null) {
                // 2. Loop over values for the bind variables passed in, if any
                for (int z = 0; z < bindVars.length; z++) {
                    // 3. Set the value of each bind variable in the statement
                    st.setObject(z + 1, bindVars[z]);
                }
            }
            // 4. Execute the statement
            st.executeUpdate();
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    // 5. Close the statement
                    st.close();
                } catch (SQLException e) {
                }
            }
        }
    }


    public void RfqValueChangeListener(ValueChangeEvent vce) {
        quotationAnaAMImpl am = (quotationAnaAMImpl)resolvElDC("quotationAnaAMDataControl");
        String rfqId = vce.getNewValue().toString();

        this.reEvaluateRfq = "F";
        am.getMmQuotAnaRslt2().executeQuery();
        ViewObject v3 = am.getMmQuotAnaRslt2();
        Row row[] = v3.getFilteredRows("RfqDocId", rfqId);

        if (row.length > 0) {
            showPopup(rfqPopUp, true);
        }
    }

    public void DialogListenerRfqPopup(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("yes")) {
            this.reEvaluateRfq = "Y";

        } else if (dialogEvent.getOutcome().name().equals("no")) {
            this.reEvaluateRfq = "N";
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
    public String saveButton() {
        quotationAnaAMImpl am = (quotationAnaAMImpl)resolvElDC("quotationAnaAMDataControl");
        Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        Integer P_USERID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        String P_ORGID = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"); 
        String P_CldID = (String)resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        String P_HOORGID = (String)resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String rfqId = null;
        String paramSetId = null;
        
        if(paramSetIdNewBind.getValue()==null){
//            The Parameter Set not selected or Created for Quotation Analysis . Please Select Parameter Set First
            String msg2 = ADFModelUtils.resolvRsrc("MSG.2398");
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            return null;
        }else if(paramSetIdNewBind.getValue()!=null){
        
        ViewObjectImpl paramVo=am.getLovParamSet();
        RowQualifier rq=new RowQualifier(paramVo);
        rq.setWhereClause("CldId= '"+P_CldID+"' and SlocId="+P_SLOCID+" and OrgId='"+P_ORGID+"' and ParamSetId='"+paramSetIdNewBind.getValue().toString()+"'");
        Row [] row1=paramVo.getFilteredRows(rq);
        System.out.println("row1.length   "+row1.length);
        if(row1.length>0){
            Number count=new Number(0);
            
        for(Row r : row1){
          
           Number wtg= (Number)r.getAttribute("ParamWtg");
            if(wtg!=null){
                count =count.add(wtg);
            }
        }
            System.out.println("count.compareTo(new Number(100)) "+count.compareTo(new Number(100)));
            
            if(count.compareTo(new Number(100))==0){
                System.out.println("count.compareTo(new Number(100)) "+count.compareTo(new Number(100)));
                
            }else{
//                Total Active parameters weightage not equals 100
                String msg2 = ADFModelUtils.resolvRsrc("MSG.2233");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);
                return null;

            }
        
        }else{
//            This Parameter Set Does't have any Active Parameter
            String msg2 = ADFModelUtils.resolvRsrc("MSG.2399");
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
            return null;
        }
       // return null;
        
        try {
           
            rfqId = am.getRfqId1().getCurrentRow().getAttribute("RefDocIdTrans").toString();
            System.out.println("bharat:");
            //System.out.println("param id---:"+am.getEvalParamSet1().getCurrentRow().getAttribute("ParamSetId").toString());
            paramSetId = am.getEvalParamSet1().getCurrentRow().getAttribute("ParamSetIdTrans").toString();
              System.out.println("paramSetId--------:"+paramSetId);

        } catch (NullPointerException e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        String autoRate = autoRateCheckBox.getValue().toString();

        if (reEvaluateRfq.equalsIgnoreCase("Y")) {

            callStoredProcedure("PKG_MM_EVAL.TRNSFR2HIST_QUOT(?,?,?,?)", new Object[] { P_SLOCID, P_CldID , P_ORGID, rfqId });
            Object arr[] =
                callStoredFunctionOut("PKG_MM_EVAL.GET_QUOT_EVALID(?,?,?,?,?,?)", new Object[] {  P_SLOCID, P_CldID , P_ORGID, rfqId });  //
            String evali_id = (String)arr[0];

            String set_id = (String)arr[1];
            set_id = paramSetId;

            callStoredProcedure("PKG_MM_EVAL.POPULATE_QUOT_DATA(?,?,?,?,?,?,?,?)",
                                new Object[] { P_SLOCID, P_CldID , P_ORGID, evali_id, set_id, rfqId, P_USERID, 1 });
            if (autoRate.equalsIgnoreCase("true")) {

                callStoredProcedure("PKG_MM_EVAL.AUTO_EVAL(?,?,?,?,?)", new Object[] { P_SLOCID, P_CldID , P_ORGID, evali_id, set_id });
            }
            am.getDBTransaction().commit();
            am.getMmQuot1().setWhereClause("REF_DOC_ID = '" + rfqId + "'");
            am.getMmQuot1().executeQuery();
            am.getMmQuotAna3().setWhereClause("PARAM_SET_ID = '" + set_id + "'");
            am.getMmQuotAna3().executeQuery();
            am.getQuotAnaRsltRFQID1().executeQuery();
            am.getMmQuotAnaRslt1().executeQuery();

        } else if (reEvaluateRfq.equalsIgnoreCase("N")) {

            Object arr[] =
                callStoredFunctionOut("PKG_MM_EVAL.GET_QUOT_EVALID(?,?,?,?,?,?)", new Object[] {  P_SLOCID, P_CldID , P_ORGID, rfqId });
            String Eval_id = (String)arr[0];
            String set_id = (String)arr[1];

            callStoredProcedure("PKG_MM_EVAL.POPULATE_QUOT_DATA(?,?,?,?,?,?,?,?)",
                                new Object[] { P_SLOCID, P_CldID , P_ORGID, Eval_id, set_id, rfqId, P_USERID, 1 });

            if (autoRate.equalsIgnoreCase("true")) {

                callStoredProcedure("PKG_MM_EVAL.AUTO_EVAL(?,?,?,?,?)", new Object[] { P_SLOCID, P_CldID , P_ORGID, Eval_id, set_id });
            }
            am.getDBTransaction().commit();
            am.getMmQuot1().setWhereClause("REF_DOC_ID = '" + rfqId + "'");
            am.getMmQuot1().executeQuery();
            am.getMmQuotAna3().setWhereClause("PARAM_SET_ID = '" + set_id + "'");
            am.getMmQuotAna3().executeQuery();
            am.getQuotAnaRsltRFQID1().executeQuery();
            am.getMmQuotAnaRslt1().executeQuery();
        } else {

            Object arr[] =
                callStoredFunctionOut("PKG_MM_EVAL.GET_QUOT_EVALID(?,?,?,?,?,?)", new Object[] {  P_SLOCID, P_CldID , P_ORGID, rfqId });
            String Eval_id = (String)arr[0];
            String set_id = (String)arr[1];
            set_id = paramSetId;

            callStoredProcedure("PKG_MM_EVAL.POPULATE_QUOT_DATA(?,?,?,?,?,?,?,?)",
                                new Object[] {P_SLOCID, P_CldID , P_ORGID, Eval_id, set_id, rfqId, P_USERID, 1 });

            if (autoRate.equalsIgnoreCase("true")) {

                callStoredProcedure("PKG_MM_EVAL.AUTO_EVAL(?,?,?,?,?)", new Object[] {P_SLOCID, P_CldID , P_ORGID, Eval_id, set_id });
            }
            am.getDBTransaction().commit();
            am.getMmQuot1().setWhereClause("REF_DOC_ID = '" + rfqId + "'");
            am.getMmQuot1().executeQuery();
            am.getMmQuotAna3().setWhereClause("PARAM_SET_ID = '" + set_id + "'");
            am.getMmQuotAna3().executeQuery();
            am.getQuotAnaRsltRFQID1().executeQuery();
            am.getMmQuotAnaRslt1().executeQuery();

        }
            /*    ViewObjectImpl quotvo=am.getMmQuot1();
            quotvo.setNamedWhereClauseParam("bindQuotStatus", 235);
            quotvo.executeQuery(); */
     
     
        return "analyzeParam";
        }
        return null;
    }

    public void setRfqPopUp(RichPopup rfqPopUp) {
        this.rfqPopUp = rfqPopUp;
    }

    public RichPopup getRfqPopUp() {
        return rfqPopUp;
    }

    public void perfRateValueChangeListener(ValueChangeEvent valueChangeEvent) {
        quotationAnaAMImpl am = (quotationAnaAMImpl)resolvElDC("quotationAnaAMDataControl");
        Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        Integer P_USERID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        String P_ORGID = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"); 
        String P_CldID = (String)resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        String P_HOORGID = (String)resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        ViewObject v1 = am.getMmQuotAna3();
        Row curr = v1.getCurrentRow();
        am.getEvalParamSet1().executeQuery();

        String paramId = curr.getAttribute("ParamId").toString();
        String paramSetId = curr.getAttribute("ParamSetId").toString();
        oracle.jbo.domain.Number rate = (oracle.jbo.domain.Number)valueChangeEvent.getNewValue();
        try {
            BigDecimal wtscore =
                (BigDecimal)callStoredFunction(Types.NUMERIC, "PKG_MM_EVAL.CALC_WTG(?,?,?,?,?,?)", new Object[] { P_SLOCID, P_CldID , P_ORGID,paramSetId,
                                                                                                            paramId,
                                                                                                            rate });
           

            curr.setAttribute("PerfScore", wtscore);
            v1.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(perfScoreBind);
        } catch (NumberFormatException nfe) {
            // TODO: Add catch code
            nfe.printStackTrace();
        }
    }
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    

    public String performAnalysisButton() {
    System.out.println("performance evluation button call");
        AdfFacesContext.getCurrentInstance().addPartialTarget(qabindTab);
        AdfFacesContext.getCurrentInstance().addPartialTarget(perfEvalBind);
        quotationAnaAMImpl am = (quotationAnaAMImpl)resolvElDC("quotationAnaAMDataControl");
        Integer P_SLOCID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        Integer P_USERID = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        String P_ORGID = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"); 
        String P_CldID = (String)resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        String P_HOORGID = (String)resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        
        Boolean flg=false;
        ViewObjectImpl anavo=am.getMmQuotAna2();
        RowSetIterator qtitr=am.getMmQuot1().createRowSetIterator(null);
        outer:
        //Integer count=new Integer(0);
        while(qtitr.hasNext()){
            Row rr=qtitr.next();
            RowQualifier rq=new RowQualifier(anavo);
            rq.setWhereClause("CldId='"+P_CldID+"' and SlocId="+P_SLOCID+" and OrgId= '"+P_ORGID+"' and QuotDocId='"+rr.getAttribute("DocId")+"'");
            Row [] filter=anavo.getFilteredRows(rq);
            adfLog.info("perf value to check "+filter.length);
            if(filter.length>0){
                for( Row temp : filter)
                    if(temp.getAttribute("PerfRate")==null){
                       flg=true;
                       break outer;
                       // count++;
                    }
            }
        }
        qtitr.closeRowSetIterator();
        adfLog.info("perf value to check "+flg);
        Integer zero=new Integer(0);
        /* RowSetIterator rsi= am.getMmQuotAna3().createRowSetIterator(null);
        
        while(rsi.hasNext()){
            Row curr=rsi.next();
            if(curr.getAttribute("PerfRate")==null){
                flg=true;
            }
        }rsi.closeRowSetIterator();
         */
      //  adfLog.info(count.equals(zero)+" new count value found is "+count);
        if(flg==false){
        
        am.getDBTransaction().commit();
        String ana_id = "";
        try {
            ana_id = (String)am.getMmQuotAna3().getCurrentRow().getAttribute("EvalId");
        } catch (NullPointerException e) {
            System.out.println(e);
            e.printStackTrace();
        }

        callStoredProcedure("PKG_MM_EVAL.UPDT_TOTSCORE_QUOT(?,?,?,?)", new Object[] { P_SLOCID, P_CldID , P_ORGID, ana_id });
        
        /**
         * Insert data work flow txn table 
         */
        ViewObject quotAnaRslt = am.getMmQuotAna3();
        Row currPo = quotAnaRslt.getCurrentRow();
        if(currPo.getAttribute("EvalId") != null){
            System.out.println("inside if condition   ----");
            System.out.println("eval id in if   "+currPo.getAttribute("EvalId").toString());
        }
        String quotAnaEvalId=quotAnaRslt.getCurrentRow().getAttribute("EvalId").toString();
        System.out.println("------------------------"+quotAnaEvalId);
        String wf_id = "W037";
        String action = "I";
        String remark = "A";
        
            OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                             WfnoOp.getParamsMap().put("sloc_id", P_SLOCID);
                             WfnoOp.getParamsMap().put("cld_id", P_CldID);
                             WfnoOp.getParamsMap().put("org_id", P_ORGID);
                             WfnoOp.getParamsMap().put("doc_no", 18510);
                             WfnoOp.execute();
                             
            String WfNum=null;    
            Integer level =-1;
            Integer res =-1;
                            if(WfnoOp.getErrors().size()==0 && WfnoOp.getResult()!=null){
                               WfNum= WfnoOp.getResult().toString();
                            }
                            
                            if(WfnoOp!=null){
            OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                             usrLevelOp.getParamsMap().put("SlocId", P_SLOCID);
                             usrLevelOp.getParamsMap().put("CldId", P_CldID);
                             usrLevelOp.getParamsMap().put("OrgId", P_ORGID);
                             usrLevelOp.getParamsMap().put("usr_id", P_USERID);
                             usrLevelOp.getParamsMap().put("WfNum", WfNum);
                             usrLevelOp.getParamsMap().put("QuotAnaDocId", 18510);
                             usrLevelOp.execute();
                             
            if(usrLevelOp.getErrors().size()==0 && usrLevelOp.getResult()!=null){
               level= Integer.parseInt(usrLevelOp.getResult().toString());
            }                  
                 
            if(level>=0){           
            System.out.println("sloc_1id "+P_SLOCID+"cld_1id   "+P_CldID+"pOrg1Id   "+P_ORGID+"WfNum "+WfNum+"poDocId "+quotAnaEvalId+"usr_idFrm  "+P_USERID+"usr_idTo   "+P_USERID+"levelFrm "+level+"levelTo  "+level+"action  "+action+"remark  "+remark);                  
        OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                         insTxnOp.getParamsMap().put("sloc_id", P_SLOCID);
                         insTxnOp.getParamsMap().put("cld_id", P_CldID);
                         insTxnOp.getParamsMap().put("pOrgId", P_ORGID);
                         insTxnOp.getParamsMap().put("PO_DOC_NO", 18510);
                         insTxnOp.getParamsMap().put("WfNum", WfNum);
                         insTxnOp.getParamsMap().put("poDocId", quotAnaEvalId);
          insTxnOp.getParamsMap().put("usr_idFrm", P_USERID);
          insTxnOp.getParamsMap().put("usr_idTo", P_USERID);
          insTxnOp.getParamsMap().put("levelFrm", level);
          insTxnOp.getParamsMap().put("levelTo", level);
          insTxnOp.getParamsMap().put("action", action);
          insTxnOp.getParamsMap().put("remark", remark);
          insTxnOp.getParamsMap().put("amount", 0);
          insTxnOp.execute();
         
          if(insTxnOp.getResult()!=null){
             res= Integer.parseInt(insTxnOp.getResult().toString());
          }  
        
        
        
        am.getDBTransaction().commit();
        am.getQuotAnaRsltRFQID1().executeQuery();
        am.getMmQuotAnaRslt1().executeQuery();
       // AdfFacesContext.getCurrentInstance().addPartialTarget(ana);
        return "Back";
            }
            else
            {
//                    Something went wrong with user level in Workflow.Please Contact to ESS!
                String s=ADFModelUtils.resolvRsrc("MSG.2400");
                    FacesMessage message2 = new FacesMessage(s);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, message2);
                return null;
                }
                            }
                            else
                            {
//                                    Workflow not define for this Organisation.
                                    String s=ADFModelUtils.resolvRsrc("MSG.2401");
                                    FacesMessage message2 = new FacesMessage(s);
                                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext.getCurrentInstance().addMessage(null, message2);
                                return null;
                                }
        }        else{
            String msg2 = resolvElDCMsg("#{bundle['MSG.320']}").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
           FacesContext.getCurrentInstance().addMessage(null, message2);
            return null;
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


    public void RateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        quotationAnaAMImpl am = (quotationAnaAMImpl)resolvElDC("quotationAnaAMDataControl");
        oracle.jbo.domain.Number rate = new oracle.jbo.domain.Number(0);
        try {
            rate = (oracle.jbo.domain.Number)object;
        } catch (NullPointerException e) {
            rate = new oracle.jbo.domain.Number(0);
            e.printStackTrace();
        }
        ViewObject v1 = am.getMmQuotAna3();
        Row curr = v1.getCurrentRow();
        String set_id = (String)curr.getAttribute("ParamSetId");
        am.getEvalParamSet1().executeQuery();
        Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String orgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"); 
        String cldId = (String)resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        ViewObjectImpl pramVo = am.getEvalParamSetFltr();
        pramVo.setNamedWhereClauseParam("cldIdBind", cldId);
        pramVo.setNamedWhereClauseParam("slocIdBind", slocId);
        pramVo.setNamedWhereClauseParam("orgIdBind", orgId);
        pramVo.setNamedWhereClauseParam("bindParamSetId", currSetId);
        pramVo.executeQuery();
        RowQualifier rq1=new RowQualifier( pramVo);
        rq1.setWhereClause("CldId= '"+cldId +"' and SlocId= "+slocId+" and OrgId = '"+orgId+"' and ParamSetId= '"+currSetId+"'");
        Row row[] =pramVo.getFilteredRows(rq1);
        Integer minRate = (Integer)row[0].getAttribute("MinRate");
        Integer maxRate = (Integer)row[0].getAttribute("MaxRate");
    
        String s=ADFModelUtils.resolvRsrc("MSG.321");
        String st=ADFModelUtils.resolvRsrc("MSG.322");
        if (rate.compareTo(maxRate) == 1 || rate.compareTo(minRate) == -1) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                         s,
                                                          st + minRate + " "+resolvElDCMsg("#{bundle['LBL.323']}").toString()+" " + maxRate));
        }
    }

    public void setAutoRateCheckBox(RichSelectBooleanCheckbox autoRateCheckBox) {
        this.autoRateCheckBox = autoRateCheckBox;
    }

    public RichSelectBooleanCheckbox getAutoRateCheckBox() {
        return autoRateCheckBox;
    }

    public void setRateReadonly(boolean rateReadonly) {
        this.rateReadonly = rateReadonly;
    }

    public boolean isRateReadonly() {
        return rateReadonly;
    }

    public void setPerfScoreBind(RichInputText perfScoreBind) {
        this.perfScoreBind = perfScoreBind;
    }

    public RichInputText getPerfScoreBind() {
        return perfScoreBind;
    }


    public void setParamSetIdBind(RichSelectOneChoice paramSetIdBind) {
        this.paramSetIdBind = paramSetIdBind;
    }

    public RichSelectOneChoice getParamSetIdBind() {
        return paramSetIdBind;
    }

    public void setMinRate(Integer minRate) {
        this.minRate = minRate;
    }

    public Integer getMinRate() {
        Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String orgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"); 
        String cldId = (String)resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
     quotationAnaAMImpl am = (quotationAnaAMImpl)resolvElDC("quotationAnaAMDataControl");
      //  oracle.jbo.domain.Number rate = new oracle.jbo.domain.Number(0);
        try {
          //  rate = (oracle.jbo.domain.Number)object;
        } catch (NullPointerException e) {
           // rate = new oracle.jbo.domain.Number(0);
            e.printStackTrace();
        }
       ViewObject v1 = am.getMmQuotAna3();
       ViewObject v2 = am.getQuotAnaRsltRFQID1();
       ViewObject v3 = am.getEvalParamSet1();
        Row curr = v1.getCurrentRow();
        Row currRow = v2.getCurrentRow();
        Row currRowSetId = v3.getCurrentRow();
       // if(currRowSetId != null){
            //String setcurrid = currRowSetId.getAttribute("ParamSetIdTrans").toString();
           // System.out.println("set id curr min  "+setcurrid);
       // }
        String set_id = null;
        Integer minRate = 0;
        if(curr != null ){
        if(curr.getAttribute("ParamSetId") != null){
            System.out.println(" second curr-----");
           // set_id = (String)curr.getAttribute("ParamSetId");
           currSetId = (String)curr.getAttribute("ParamSetId");
        }
        }else if(currRow != null){
            if(currRow.getAttribute("ParamSetId") != null){
                System.out.println(" second curr-----1111");
                currSetId = (String)currRow.getAttribute("ParamSetId");
            }
        }else if(currRowSetId != null){
            if(currRowSetId.getAttribute("ParamSetIdTrans")!= null){
            currSetId = currRowSetId.getAttribute("ParamSetIdTrans").toString();
            }
        }   
        System.out.println("ParamSetId  11111  "+currSetId);
        
        //am.getEvalParamSet2().executeQuery();
        ViewObjectImpl pramVo=am.getEvalParamSetFltr();
          pramVo.setNamedWhereClauseParam("cldIdBind", cldId);
          pramVo.setNamedWhereClauseParam("slocIdBind", slocId);
          pramVo.setNamedWhereClauseParam("orgIdBind", orgId);
          pramVo.setNamedWhereClauseParam("bindParamSetId", currSetId);
        pramVo.executeQuery();
        RowQualifier rq1=new RowQualifier(pramVo);
        rq1.setWhereClause("CldId= '"+cldId +"' and SlocId= "+slocId+" and OrgId = '"+orgId+"' and ParamSetId= '"+currSetId+"'");
        Row row[] = pramVo.getFilteredRows(rq1);
        System.out.println("lenght----2-----p "+row.length+"  "+rq1.getExprStr());
         minRate = (Integer)row[0].getAttribute("MinRate");
        System.out.println("minRate------------------"+minRate);
          
        return minRate;
    }

    public void setMaxRate(Integer maxRate) {
        this.maxRate = maxRate;
    }

    public Integer getMaxRate() {
        Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String orgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"); 
        String cldId = (String)resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        quotationAnaAMImpl am = (quotationAnaAMImpl)resolvElDC("quotationAnaAMDataControl");
         //  oracle.jbo.domain.Number rate = new oracle.jbo.domain.Number(0);
           try {
             //  rate = (oracle.jbo.domain.Number)object;
           } catch (NullPointerException e) {
              // rate = new oracle.jbo.domain.Number(0);
               e.printStackTrace();
           }
        /*   ViewObject v1 = am.getMmQuotAna3();
          ViewObject v2 = am.getQuotAnaRsltRFQID1();
          ViewObject v3 = am.getEvalParamSet1();
           Row curr = v1.getCurrentRow();
           Row currRow = v2.getCurrentRow();
          Row currRowSetId = v3.getCurrentRow();
        
           String set_id = null;
           Integer maxRate = 0;
           if(curr != null ){
           if(curr.getAttribute("ParamSetId") != null){
               System.out.println(" 1 --------");
               set_id = (String)curr.getAttribute("ParamSetId");
           }
           }else if(currRow != null){
               if(currRow.getAttribute("ParamSetId") != null){
                   System.out.println(" 2 --------");
                   set_id = (String)currRow.getAttribute("ParamSetId");
               }
           }else if(currRowSetId != null){
            if(currRowSetId.getAttribute("ParamSetIdTrans")!= null){
                System.out.println(" 3 --------");
               set_id = currRowSetId.getAttribute("ParamSetIdTrans").toString();
            }
        }  */
        ViewObjectImpl pramVo=am.getEvalParamSetFltr();
          pramVo.setNamedWhereClauseParam("cldIdBind", cldId);
          pramVo.setNamedWhereClauseParam("slocIdBind", slocId);
          pramVo.setNamedWhereClauseParam("orgIdBind", orgId);
          pramVo.setNamedWhereClauseParam("bindParamSetId", currSetId);
          pramVo.executeQuery();
          RowQualifier rq1=new RowQualifier(pramVo);
          rq1.setWhereClause("CldId= '"+cldId +"' and SlocId= "+slocId+" and OrgId = '"+orgId+"' and ParamSetId= '"+currSetId+"'");
          Row row[] = pramVo.getFilteredRows(rq1);
            
            maxRate = (Integer)row[0].getAttribute("MaxRate");   
        System.out.println("lenght-----1----p "+row.length+" max rate is "+maxRate+" "+rq1.getExprStr());
         
        return maxRate;
    }

    public void setParamSetIdNewBind(RichSelectOneChoice paramSetIdNewBind) {
        this.paramSetIdNewBind = paramSetIdNewBind;
    }

    public RichSelectOneChoice getParamSetIdNewBind() {
        return paramSetIdNewBind;
    }

    public void editAnalysisAction(ActionEvent actionEvent) {      
    quotationAnaAMImpl am = (quotationAnaAMImpl)resolvElDC("quotationAnaAMDataControl");
        Row rr =  am.getMmQuotAna3().getCurrentRow();
       // String doc_txn_id = rr.getAttribute("DocId").toString();
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        Integer pending =null;
     
        String wf_id = "W037";
        Integer level = 0;
        String action = "I";
        String remark = "A";
        //  System.out.println(sloc_id + "---" + org_id + "---" + cld_id + "-----" + doc_id + "----" + doc_txn_id);
     //   am.getDBTransaction().commit();
  /*   pending =
            Integer.parseInt(callStoredFunction(NUMBER, "APP.PKG_APP_WF.GET_DOC_WF_CUR_USR(?,?,?,?,?)", new Object[] { sloc_id,
                                                                                                                       cld_id,
                                                                                                                       org_id,
                                                                                                                       PO_DOC_NO,
                                                                                                                       doc_txn_id }).toString());
       */  //   System.out.println(pending + "--------" + usr_id); 
System.out.println("sloc_id    "+sloc_id+"cld_id   "+cld_id+"org_id  "+org_id);
 OperationBinding chkUsr = getBindings().getOperationBinding("pendingPOCheck");
                  chkUsr.getParamsMap().put("SlocId", sloc_id);
                  chkUsr.getParamsMap().put("CldId", cld_id);
                  chkUsr.getParamsMap().put("OrgId", org_id);
                  chkUsr.getParamsMap().put("QuotAnaDocNo", 18510);
                  chkUsr.execute();
                 
                 if(chkUsr.getResult()!=null){
                    pending= Integer.parseInt(chkUsr.getResult().toString());
                 }
                 
                System.out.println(usr_id+"pending user id="+pending);          

        if(pending.compareTo(new Integer(-1))==0 || pending.compareTo(usr_id)==0) {        
                    RequestContext.getCurrentInstance().getPageFlowScope().put("ANALYSIS_TYPE", "N");
            }else{ 
           
                            Row[] r=am.getLovUsrId1().getFilteredRows("UsrId",pending);
//                    String name="Anonymous";
                         String name=ADFModelUtils.resolvRsrc("MSG.2402");
                    if(r.length>0)
                        if(r[0].getAttribute("UsrName")!=null)
                            name = (String)r[0].getAttribute("UsrName");//pending != usr_id 
//                            This Quotation Analysis is pending at [
//            ] for approval, You can not edit this.
            String s1=ADFModelUtils.resolvRsrc("MSG.2404");
            String s2=ADFModelUtils.resolvRsrc("MSG.2405");
                    String msg2 = s1 +name+ s2;
            System.out.println("String is:::::::::::::::::::::::::::::::"+msg2 );
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message2);
        }
}
      
    public String getQuotAnaStatus(){
        quotationAnaAMImpl am = (quotationAnaAMImpl)resolvElDC("quotationAnaAMDataControl");
        ViewObjectImpl vo = am.getMmQuotAnaRslt1();
        Row curr=vo.getCurrentRow();
        String staus ="N";
        if(curr!=null){
            if(curr.getAttribute("QuotAnaStatus")!=null){
                Integer quotAnaStatus = Integer.parseInt(curr.getAttribute("QuotAnaStatus").toString());
                    if(quotAnaStatus==234){
                        staus="Y";
                    }
            }
        }
        return staus;
    }


    public void setQabindTab(RichTable qabindTab) {
        this.qabindTab = qabindTab;
    }

    public RichTable getQabindTab() {
        return qabindTab;
    }

    public void setPerfEvalBind(RichInputText perfEvalBind) {
        this.perfEvalBind = perfEvalBind;
    }

    public RichInputText getPerfEvalBind() {
        return perfEvalBind;
    }

    public void TabButtonActionListener(ActionEvent actionEvent) {
        // Add event code here...
        quotationAnaAMImpl am = (quotationAnaAMImpl)resolvElDC("quotationAnaAMDataControl");
       ViewObjectImpl quot3= am.getMmQuotAna3();
        
        System.out.println("current key is "+quot3.getCurrentRow().getKey());
        RowSetIterator rwset=quot3.getRowSetIterator();
        Row curr=quot3.getCurrentRow();
       curr= rwset.next();
        System.out.println("current key is "+curr.getKey());
        
    }
}
