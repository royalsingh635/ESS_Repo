package mmsupplierperfeval.view.bean;

import java.io.Serializable;

import javax.faces.component.UIComponent;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.jbo.domain.Number;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.event.ValueChangeEvent;

import mmsupplierperfeval.model.service.MmSupplierPerfEvalAMImpl;

import oracle.adf.model.BindingContext;


import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

import java.math.BigDecimal;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.Key;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class MmSupplierPerfEvalBean implements Serializable {
    private RichInputText perScoreBindVar;
    private RichTable perfEvalTable;

   // private RichCommandButton editButton;
  //  private RichCommandButton saveButton;
    private String setupVis = "";
    private RichSelectOneChoice setUpBindVar;
    private String reEvaluateButton = getreEvaluateButtonVal();
    private RichPopup popup;
   // private RichCommandButton createButton;
    private RichTable suppliertableBind;
    private RichCommandImageLink createButton;
    private RichCommandImageLink editButton;
    private RichCommandImageLink saveButton;
    
    private static ADFLogger adfLog = (ADFLogger)ADFLogger.createADFLogger(MmSupplierPerfEvalBean.class);

    public MmSupplierPerfEvalBean() {
    }

    private Boolean readOnlyField = true;

    private String Mode ="";
    private Number minRate;
    private Number maxRate;

    private String getreEvaluateButtonVal() {
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String CldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        MmSupplierPerfEvalAMImpl am = (MmSupplierPerfEvalAMImpl)resolvElDC("MmSupplierPerfEvalAMDataControl");
        ViewObjectImpl v1 = am.getMmEoPerfEvalViewForReEval();
        
            RowQualifier rowQualifier = new RowQualifier(v1);  
            rowQualifier.setWhereClause("OrgId = '"+OrgId+"' and SlocId = "+SlocId+" and CldId='"+CldId+"'");  
            Row[] filteredRows = v1.getFilteredRows(rowQualifier);  
           int count= filteredRows.length;
          adfLog.info("count--"+count);
      /*   v1.setWhereClause("ORG_ID='" + OrgId + "' and SLOC_ID=" + SlocId);
        v1.executeQuery();
        Long countparam = v1.getEstimatedRowCount(); */
            
        if (count > 0) {
            return "false";
        } else {
            return "true";
        }

    }

    public String getsetUpVisVal() {
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String CldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        MmSupplierPerfEvalAMImpl am = (MmSupplierPerfEvalAMImpl)resolvElDC("MmSupplierPerfEvalAMDataControl");
        ViewObjectImpl v1 = am.getMmEoPerfRest1();
       /*  v1.setWhereClause("ORG_ID='" + OrgId + "' and SLOC_ID=" + SlocId);
        v1.executeQuery(); */
       RowQualifier rowQualifier = new RowQualifier(v1);  
       rowQualifier.setWhereClause("OrgId = '"+OrgId+"' and SlocId ="+SlocId+" and CldId='"+CldId+"'");  
       Row[] filteredRows = v1.getFilteredRows(rowQualifier);  
       int count= filteredRows.length;
       // Long countparam = v1.getEstimatedRowCount();
      adfLog.info("count--------"+count);
        if (count == 0) {
           adfLog.info("true");
            return "true";
            
        } else {
           adfLog.info("false");

            return "false";
        }
        
    }

    public String CreateAction() {
        MmSupplierPerfEvalAMImpl am = (MmSupplierPerfEvalAMImpl)resolvElDC("MmSupplierPerfEvalAMDataControl");
        Integer UserId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        
        /* OperationBinding createOB1 = executeBinding("Rollback");
                createOB1.execute(); 
        FacesMessage msg1 = new FacesMessage("Again create Evaluation.this evaluation is Rollback for some criteria not follow.");
        msg1.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext ctx1 = FacesContext.getCurrentInstance();
        ctx1.addMessage(null, msg1); */
       /*  if(setUpBindVar.getValue()!=null){
            Integer SupplierId=null;
            if(am.getAppEoPrf().getCurrentRow().getAttribute("EoId")!=null){
              SupplierId=  Integer.parseInt(am.getAppEoPrf().getCurrentRow().getAttribute("EoId").toString());
            }
        ViewObjectImpl rsltNewVo12=am.getMmEoPerfRsltNew(); 
          rsltNewVo12.setNamedWhereClauseParam("CldIdBind", CldId);
          rsltNewVo12.setNamedWhereClauseParam("SlocIdBind", SlocId);
          rsltNewVo12.setNamedWhereClauseParam("OrgIdBind", OrgId);
          rsltNewVo12.setNamedWhereClauseParam("BindEoId", SupplierId);
          rsltNewVo12.setNamedWhereClauseParam("ParamSetIdBind", setUpBindVar.getValue());
          rsltNewVo12.executeQuery();
          Row [] rr1=rsltNewVo12.getAllRowsInRange();
          if(rr1.length>0){
              adfLog.info("inside if and paramset id is "+setUpBindVar.getValue() +" and going to filtered data "+rr1.length +"supplier id "+SupplierId );
              
          }else{
              adfLog.info("inside if and paramset id is "+setUpBindVar.getValue() +" and going to filtered data "+rr1.length +"supplier id "+SupplierId );
              
          }
        }else{
            adfLog.info("set id was null"+setUpBindVar.getValue());
        }
        
        if(false){ */
       /* String setId12=null;
        if(setUpBindVar.getValue()!=null){
            setId12=setUpBindVar.getValue().toString();
        }
      
        System.out.println("value of setupVis"+setupVis);
        ViewObjectImpl parmaVO=am.getMmEvalParam();
        RowQualifier pramQualifier = new RowQualifier(parmaVO);
        if(setId12!=null){
        System.out.println("inside if and paramset id is "+setId12 +" and going to filtered data" );
        pramQualifier.setWhereClause("OrgId = '" + OrgId + "' and SlocId =" + SlocId + " and CldId= '" + CldId+"'  and ParamSetId= '" + setId12+"'");
        }
        Row[] paramrows = parmaVO.getFilteredRows(pramQualifier);
        System.out.println("gettting the filtered rows is "+paramrows.length);
          if(paramrows.length<1){
            System.out.println("value of setupVis"+setupVis);
            FacesMessage message = new FacesMessage("Add Some Parameter for this Set for Supplier Evaluation");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        }else{   */
        BindingContainer bindings4 = getBindings();
        DCIteratorBinding parentIter = (DCIteratorBinding)bindings4.get("AppEoPrfIterator");
        Key parentKey = null;
       
        if (parentIter.getEstimatedRowCount() > 0) {
            parentKey = parentIter.getCurrentRow().getKey();
        }
        Integer SupplierId = null;
        if(am.getAppEoPrf().getCurrentRow().getAttribute("EoId")!=null){
          SupplierId=  Integer.parseInt(am.getAppEoPrf().getCurrentRow().getAttribute("EoId").toString());
        }
        ViewObjectImpl v1 = am.getMmEoPerfRest1();
       
        RowQualifier rowQualifier = new RowQualifier(v1);
        rowQualifier.setWhereClause("OrgId = '" + OrgId + "' and SlocId =" + SlocId + " and CldId= '" + CldId+"'");
        Row[] filteredRows = v1.getFilteredRows(rowQualifier);
       adfLog.info(rowQualifier.getExprStr()+"  row Query  : "+filteredRows.length);
        int countEoEval = filteredRows.length;
        String evalId = null;
        String setId = null;
       String setid12=null;
       adfLog.info("filtercount---------" + countEoEval);
        if (countEoEval == 0) {
            evalId = getEvalId();
            
            if(setUpBindVar.getValue()!=null && setUpBindVar.getValue()!=""){
                setid12 = setUpBindVar.getValue().toString();
                
                adfLog.info("value of setupVis"+setupVis);
                ViewObjectImpl parmaVO=am.getMmEvalParam();
                RowQualifier pramQualifier = new RowQualifier(parmaVO);
                if(setid12!=null){
              adfLog.info("inside if and paramset id is "+setId +" and going to filtered data" );
                pramQualifier.setWhereClause("OrgId = '" + OrgId + "' and SlocId =" + SlocId + " and CldId= '" + CldId+"'  and ParamSetId= '" + setid12+"'");
                }
                Row[] paramrows = parmaVO.getFilteredRows(pramQualifier);
               adfLog.info("gettting the filtered rows is "+paramrows.length);
                  if(paramrows.length<1){
                  setId=null;
                  }else{
                      Number hnd=new Number(100);
                      Number sum=new Number(0);
                      if(paramrows.length>0){
                          for(Row r:paramrows){
                              sum=sum.add((Number)r.getAttribute("ParamWtg"));
                          }
                          if(sum.compareTo(hnd)==0){
                              setId=setUpBindVar.getValue().toString();
                         }else{
                          setId=null;
                          String valMsg =resolvElDCMsg("Total Active parameters weightage not equals 100").toString();
                          FacesMessage msg = new FacesMessage(valMsg);
                          msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                          FacesContext ctx = FacesContext.getCurrentInstance();
                          ctx.addMessage(null, msg);
                      }
                          adfLog.info("total weitage value is "+sum);
                      }
                      
                  }
            
            
            }
        adfLog.info("id -------------" + evalId+ " set id is "+setId+" supplier id "+SupplierId);
        if (setId!=null && SupplierId!=null && evalId!=null) {
                adfLog.info("SlocId=" + SlocId +" CldId=" + CldId +" OrgId=" + evalId + " setId=" + setId + " SupplierId=" + SupplierId +" UserId=" + UserId );
             
             
                Mode = "A";
                readOnlyField = false;
                reEvaluateButton = getreEvaluateButtonVal();
                suppliertableBind.setRowSelection(RichTable.ROW_SELECTION_NONE);
                this.setSetupVis("");
             
                populateData(SlocId, CldId, OrgId, evalId, setId, "S", SupplierId, UserId, 1);
               /*  OperationBinding createOB = executeBinding("Commit");
                createOB.execute(); */
               adfLog.info("before post change-------------------");
                am.getDBTransaction().postChanges();
                adfLog.info("after post change-------------------");
                
            } else {
                String valMsg =resolvElDCMsg("#{bundle['MSG.385']}").toString();
                FacesMessage msg = new FacesMessage(valMsg);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, msg);

            }
        } else {
           // RowSetIterator rsi = v1.createRowSetIterator(null);
            
           // adfLog.info(" createRowSetIterator  ::   "+rsi.getAllRowsInRange().length);
            //if (rsi.getAllRowsInRange().length > 0) {
            if (filteredRows.length > 0) {
                if(filteredRows[0].getAttribute("EvalId")!=null && filteredRows[0].getAttribute("ParamSetId")!=null ){
                    
                    evalId =filteredRows[0].getAttribute("EvalId").toString();
                    setId = filteredRows[0].getAttribute("ParamSetId").toString();
                }
               // Row rw = rsi.first();
               // Row rw = rsi.next();
              adfLog.info(setId+"   row key ---- : "+evalId);
                //adfLog.info(rw.getAttribute("EvalId")+"  when filtercount grater than 0 :     "+rw.getAttribute("ParamSetId"));
               // evalId = rw.getAttribute("EvalId").toString();
                //setId = rw.getAttribute("ParamSetId").toString();
                Mode = "A";
                readOnlyField = false;
                this.setSetupVis("");
                reEvaluateButton = getreEvaluateButtonVal();

                populateData(SlocId, CldId, OrgId, evalId, setId, "S", SupplierId, UserId, 1);
               
                /*  OperationBinding createOB = executeBinding("Commit");
                createOB.execute(); */
                adfLog.info("before post change6666666666-------------------");
                am.getDBTransaction().postChanges();
                adfLog.info("after 2222 post change-------------------");
              
            }
        }
        
      /*   OperationBinding createOB1 = executeBinding("Rollback");
        createOB1.execute(); */
        if(parentKey!=null){
         //  if (parentIter.containsKey(parentKey)) {
            //   if(parentIter.getRowSetIterator().getRow(parentKey)!=null){  //check condition else gives exception in add mode.getRowSetIterator().getRow(parentKey)!=null
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
           }
        //}
        //if(parentKey != null)
    //}
        //}
        return null;
    }
    
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void populateData(Integer P_SLOCID,String P_CLDID, String P_ORGID, String P_EVALID, String Set_id, String P_CATFLG,
                             Integer P_EOID, Integer P_USRID, Integer P_ENTITYID) {
System.out.println("populateData===========");
        MmSupplierPerfEvalAMImpl am = (MmSupplierPerfEvalAMImpl)resolvElDC("MmSupplierPerfEvalAMDataControl");
        adfLog.info("P_SLOCID :  "+P_SLOCID+" P_CLDID :  "+P_CLDID+"  P_ORGID :  "+P_ORGID+" P_EVALID :  "+P_EVALID+" Set_id :  "+Set_id+" P_CATFLG : "+P_CATFLG+" P_EOID :  "+P_EOID+" P_USRID : "+P_USRID+"  P_ENTITYID : "+P_ENTITYID);
        try {
            callStoredProcedure("MM.PKG_MM_EVAL.POPULATE_EO_DATA(?,?,?,?,?,?,?,?,?)",
                                new Object[] { P_SLOCID, P_CLDID, P_ORGID, P_EVALID, Set_id, P_CATFLG, P_EOID, P_USRID,
                                               P_ENTITYID });
        } catch (Exception e) {
            
            /**
             * Rollback needed becoz if multiple user enter the same record like
             * same supplier,same parameter then one record has to roll back the transaction
             * becoz crash appears see the log.
             */
            adfLog.info(" inside else catch :::::::::::::::::::::::::::::::");
            setUpBindVar.setValue(null);
            setUpBindVar.setValue("");
            reEvaluateButton = getreEvaluateButtonVal();
            readOnlyField = true;
            this.setSetupVis("");
            this.setMode("V");
            OperationBinding createOB1 = executeBinding("Rollback");
            createOB1.execute();
            /* 
            setUpBindVar.setValue(null);
            setUpBindVar.setValue("");
            reEvaluateButton = getreEvaluateButtonVal();
            readOnlyField = true;
            this.setSetupVis("");
            this.setMode("V"); */
            suppliertableBind.setRowSelection(RichTable.ROW_SELECTION_SINGLE);
            //Again create Evaluation.this evaluation is Rollback for some criteria not follow
            FacesMessage msg = new FacesMessage("The Evaluation is Rollback because Another User Evaluate Same Supplier and Same Parameter Set.");
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, msg);
            e.printStackTrace();
        }
       
        am.getMmEoPerfEval1().executeQuery();
        am.getMmEoPerfRest1().executeQuery();
        
        

    }

    private String getEvalId() {
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String CldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        

        return (String)callStoredFunction(STRING, "MM.FN_MM_GEN_ID(?,?,?,?)", new Object[] {SlocId,CldId,OrgId, "MM$EO$PERF$RSLT" });
    }

    private Number Score(String paramSetId, String P_PARAMID, Number P_RATING) {
        Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String CldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        
        BigDecimal n =
            (BigDecimal)callStoredFunction(INTEGER, "MM.PKG_MM_EVAL.CALC_WTG(?,?,?,?,?,?)", new Object[] {SlocId,CldId,OrgId,paramSetId,
                                                                                                     P_PARAMID,
                                                                                                     P_RATING });
        Number x = new Number(0);

        try {
            if (n != null) {
                x = new Number(n);
            } else {
                x = null;
            }
         } catch (SQLException e) {
            e.printStackTrace();
        } 
        return (x);
    }
    private static int INTEGER = Types.NUMERIC;
    private static int DATE = Types.DATE;
    private static int STRING = Types.VARCHAR;

    protected void callStoredProcedure(String stmt, Object[] bindVars) {
        PreparedStatement st = null;
        MmSupplierPerfEvalAMImpl am = (MmSupplierPerfEvalAMImpl)resolvElDC("MmSupplierPerfEvalAMDataControl");

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
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Procedure Executed successfully ");
    }

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            MmSupplierPerfEvalAMImpl am = (MmSupplierPerfEvalAMImpl)resolvElDC("MmSupplierPerfEvalAMDataControl");
            // 1. Create a JDBC CallabledStatement
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            // 2. Register the first bind variable for the return value
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                // 3. Loop over values for the bind variables passed in, if any
                for (int z = 0; z < bindVars.length; z++) {
                    // 4. Set the value of user-supplied bind vars in the stmt
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            // 5. Set the value of user-supplied bind vars in the stmt
            st.executeUpdate();
            // 6. Return the value of the first bind variable
            return st.getObject(1);
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    // 7. Close the statement
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
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

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    public String EditAction() {
        suppliertableBind.setRowSelection(RichTable.ROW_SELECTION_NONE);
        reEvaluateButton = getreEvaluateButtonVal();
        readOnlyField = false;
        this.setSetupVis("");
        
        Mode = "E";
        return null;
    }

    public String SaveAction() {
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(perfEvalTable);
        MmSupplierPerfEvalAMImpl am = (MmSupplierPerfEvalAMImpl)resolvElDC("MmSupplierPerfEvalAMDataControl");
        ViewObject v = am.getMmEoPerfEval1();
        ViewObject v1 = am.getAppEoPrf();
        ViewObjectImpl rsltVo = am.getMmEoPerfRest1();
        ViewObjectImpl rsltNewVo = am.getMmEoPerfRsltNew();
        Row rr =v.getCurrentRow();
        Row rowR = v1.getCurrentRow();
        System.out.println("KEY---"+rowR.getKey());
        Integer SupplierId = null;
        if(am.getAppEoPrf().getCurrentRow().getAttribute("EoId")!=null){
          SupplierId=  Integer.parseInt(am.getAppEoPrf().getCurrentRow().getAttribute("EoId").toString());
        }
        
        Integer UserId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        Date dt = (Date)Date.getCurrentDate();
        if ("E".equals(Mode)){
            rr.setAttribute("UsrIdModDt", dt);
            rr.setAttribute("UsrIdMod", UserId);
        }
      
        Row[] rw = v.getAllRowsInRange();
        int count = 0;

            for (Row r : rw) {
                        if (r.getAttribute("TransIsValidRate") != null) {

                            if (r.getAttribute("TransIsValidRate").equals("N")) {
                                count = count + 1;
                                break;
                            }
                        } else {
                            count=0;
                        }
                    }
                    if (count > 0) {
                        String valMsg = resolvElDCMsg("#{bundle['MSG.386']}").toString()+" " + getMinRate() +" "+resolvElDCMsg("#{bundle['MSG.323']}").toString() +" "+ getMaxRate();
                        FacesMessage msg = new FacesMessage(valMsg);
                        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext ctx = FacesContext.getCurrentInstance();
                        ctx.addMessage(null, msg);
                    } 
            else {
            if (v.getEstimatedRowCount() > 0) {
                Row currRw = v.getCurrentRow();
                Integer slocId = (Integer)currRw.getAttribute("SlocId");
                String orgId = currRw.getAttribute("OrgId").toString();
                String evalId = currRw.getAttribute("EvalId").toString();
                String CldId=currRw.getAttribute("CldId").toString();
                
                String setId =null;
                if(setUpBindVar.getValue()!=null && setUpBindVar.getValue()!=""){
                    setId = setUpBindVar.getValue().toString();
                }
                adfLog.info(   setId+"   visual value ===========  "+getSetupVis());
                
                /**
                 * Check first time when user select parameter set
                 * In case of multiple user evaluate same record with same supplier and differnt parameter set
                 * Then rollback because all supplier evaluate only same parameter set.
                 */
                
             if(setId!=null){
                    rsltNewVo.setNamedWhereClauseParam("CldIdBind", CldId);
                    rsltNewVo.setNamedWhereClauseParam("SlocIdBind", slocId);
                    rsltNewVo.setNamedWhereClauseParam("OrgIdBind", orgId);
                    rsltNewVo.setNamedWhereClauseParam("BindEoId", null);
                    rsltNewVo.setNamedWhereClauseParam("ParamSetIdBind", null);
                    rsltNewVo.executeQuery();
                    int countNew =0;
                   RowSetIterator rstl = rsltNewVo.createRowSetIterator(null);
                   while(rstl.hasNext()){
                       Row r = rstl.next();
                       adfLog.info(" 1 Row set Itr    "+r.getKey());
                       if(setId.equalsIgnoreCase(r.getAttribute("ParamSetId").toString())){
                           
                       }else {
                           countNew = countNew +1;
                       }
                       
                   }
                   rstl.closeRowSetIterator();
                   if(countNew>0){
                       adfLog.info(" both set id defferent please rollback ");
                       
                       /**
                            * Rollback needed becoz if multiple user enter the same record like
                             * same supplier,same parameter then one record has to roll back the transaction
                             * becoz crash appears see the log.
                         */
                                         
                       
                       setUpBindVar.setValue(null);
                       OperationBinding createOB1 = executeBinding("Rollback");
                               createOB1.execute();
                       setUpBindVar.setValue(null);
                       setUpBindVar.setValue("");
                       reEvaluateButton = getreEvaluateButtonVal();
                       readOnlyField = true;
                       this.setSetupVis("");
                       this.setMode("V");
                       suppliertableBind.setRowSelection(RichTable.ROW_SELECTION_SINGLE);
                       
                       FacesMessage msg = new FacesMessage("The Evaluation is Rollback because Another User Evaluate Same Supplier and Same Parameter Set or Different Parameter Sat.");
                       msg.setSeverity(FacesMessage.SEVERITY_WARN);
                       FacesContext ctx = FacesContext.getCurrentInstance();
                       ctx.addMessage(null, msg);
                       return null;
                       
                   }
                    
                    
                }else if(setId==null && SupplierId!=null){
                    
                    rsltNewVo.setNamedWhereClauseParam("CldIdBind", CldId);
                    rsltNewVo.setNamedWhereClauseParam("SlocIdBind", slocId);
                    rsltNewVo.setNamedWhereClauseParam("OrgIdBind", orgId);
                    rsltNewVo.setNamedWhereClauseParam("BindEoId", null);
                    rsltNewVo.setNamedWhereClauseParam("ParamSetIdBind", null);
                    rsltNewVo.executeQuery();
                    
                    int countNew =0;
                    RowSetIterator rstl = rsltNewVo.createRowSetIterator(null);
                    while(rstl.hasNext()){
                       Row r = rstl.next();
                       adfLog.info(SupplierId+" 1 Row set Itr    "+r.getKey());
                        
                       if(SupplierId==Integer.parseInt(r.getAttribute("EoId").toString())){
                       adfLog.info(" same supplier ==========");
                           countNew = countNew +1;
                       }else {
                           
                       }
                       
                    }
                    rstl.closeRowSetIterator();
                    adfLog.info("count new value   "+countNew);
                    if(countNew>1){
                       adfLog.info(" both set id else 1111 end part defferent please rollback ");
                       
                        /**
                             * Rollback needed becoz if multiple user enter the same record like
                              * same supplier,same parameter then one record has to roll back the transaction
                              * becoz crash appears see the log.
                          */
                       
                       setUpBindVar.setValue(null);
                       OperationBinding createOB1 = executeBinding("Rollback");
                               createOB1.execute();
                       setUpBindVar.setValue(null);
                       setUpBindVar.setValue("");
                       reEvaluateButton = getreEvaluateButtonVal();
                       readOnlyField = true;
                       this.setSetupVis("");
                       this.setMode("V");
                       suppliertableBind.setRowSelection(RichTable.ROW_SELECTION_SINGLE);
                       
                       FacesMessage msg = new FacesMessage("The Evaluation is Rollback because Another User Evaluate Same Supplier and Same Parameter Set or Different Parameter Sat.");
                       msg.setSeverity(FacesMessage.SEVERITY_WARN);
                       FacesContext ctx = FacesContext.getCurrentInstance();
                       ctx.addMessage(null, msg);
                       return null;
                    
                }
                }
                
                OperationBinding createOB = executeBinding("Commit");
                        createOB.execute();

                if (createOB.getErrors().isEmpty()) {

                    callStoredProcedure("MM.PKG_MM_EVAL.UPDT_TOTSCORE_EO(?,?,?,?)",
                                        new Object[] { slocId,CldId, orgId, evalId });
                  
                    createOB.execute();

                    if (createOB.getErrors().isEmpty()) {
                        readOnlyField = true;
                        this.setSetupVis("");
                       
                        if ("A".equals(Mode)) {
                            String Defaultmsg = resolvEl("#{bundle['MSG.89']}");

                            String valMsg = Defaultmsg.format(Defaultmsg, "", "", "", "", "");
                            FacesMessage msg = new FacesMessage(valMsg);
                            msg.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext ctx = FacesContext.getCurrentInstance();
                            ctx.addMessage(null, msg);
                        } else if ("E".equals(Mode)) {
                            String Defaultmsg = resolvEl("#{bundle['MSG.90']}");

                            String valMsg = Defaultmsg.format(Defaultmsg, "", "", "", "", "");
                            FacesMessage msg = new FacesMessage(valMsg);
                            msg.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext ctx = FacesContext.getCurrentInstance();
                            ctx.addMessage(null, msg);
                        }
                    }
                }
            } else {
                Row currRw = v.getCurrentRow();
                Integer slocId = (Integer)currRw.getAttribute("SlocId");
                String orgId = currRw.getAttribute("OrgId").toString();
                String evalId = currRw.getAttribute("EvalId").toString();
                String CldId=currRw.getAttribute("CldId").toString();
                String setId =null;
                if(setUpBindVar.getValue()!=null && setUpBindVar.getValue()!=""){
                    setId = setUpBindVar.getValue().toString();
                }
                adfLog.info(   setId+"   visual value 1===========  "+getSetupVis()+"    999999         "+getsetUpVisVal());
                /**
                 * Check first time when user select parameter set
                 * In case of multiple user evaluate same record with same supplier and differnt parameter set
                 * Then rollback because all supplier evaluate only same parameter set.
                 */
                
                if(setId!=null){
                       rsltNewVo.setNamedWhereClauseParam("CldIdBind", CldId);
                       rsltNewVo.setNamedWhereClauseParam("SlocIdBind", slocId);
                       rsltNewVo.setNamedWhereClauseParam("OrgIdBind", orgId);
                       rsltNewVo.setNamedWhereClauseParam("BindEoId", null);
                       rsltNewVo.setNamedWhereClauseParam("ParamSetIdBind", null);
                       rsltNewVo.executeQuery();
                       
                       int countNew =0;
                       RowSetIterator rstl = rsltNewVo.createRowSetIterator(null);
                       while(rstl.hasNext()){
                          Row r = rstl.next();
                          adfLog.info(" 1 Row set Itr    "+r.getKey());
                          if(setId.equalsIgnoreCase(r.getAttribute("ParamSetId").toString())){
                              
                          }else {
                              countNew = countNew +1;
                          }
                          
                       }
                       rstl.closeRowSetIterator();
                       if(countNew>0){
                          adfLog.info(" both set id else end part defferent please rollback ");
                          
                           /**
                                * Rollback needed becoz if multiple user enter the same record like
                                 * same supplier,same parameter then one record has to roll back the transaction
                                 * becoz crash appears see the log.
                             */
                          
                          setUpBindVar.setValue(null);
                          OperationBinding createOB1 = executeBinding("Rollback");
                                  createOB1.execute();
                          setUpBindVar.setValue(null);
                          setUpBindVar.setValue("");
                          reEvaluateButton = getreEvaluateButtonVal();
                          readOnlyField = true;
                          this.setSetupVis("");
                          this.setMode("V");
                          suppliertableBind.setRowSelection(RichTable.ROW_SELECTION_SINGLE);
                          
                          FacesMessage msg = new FacesMessage("The Evaluation is Rollback because Another User Evaluate Same Supplier and Same Parameter Set or Different Parameter Sat.");
                          msg.setSeverity(FacesMessage.SEVERITY_WARN);
                          FacesContext ctx = FacesContext.getCurrentInstance();
                          ctx.addMessage(null, msg);
                          return null;
                          
                       }
                }else if(setId==null && SupplierId!=null){
                    
                    rsltNewVo.setNamedWhereClauseParam("CldIdBind", CldId);
                    rsltNewVo.setNamedWhereClauseParam("SlocIdBind", slocId);
                    rsltNewVo.setNamedWhereClauseParam("OrgIdBind", orgId);
                    rsltNewVo.setNamedWhereClauseParam("BindEoId", null);
                    rsltNewVo.setNamedWhereClauseParam("ParamSetIdBind", null);
                    rsltNewVo.executeQuery();
                    
                    int countNew =0;
                    RowSetIterator rstl = rsltNewVo.createRowSetIterator(null);
                    while(rstl.hasNext()){
                       Row r = rstl.next();
                       adfLog.info(SupplierId+" 1 Row set Itr    "+r.getKey());
                        
                       if(SupplierId==Integer.parseInt(r.getAttribute("EoId").toString())){
                       adfLog.info(" same supplier ==========");
                           countNew = countNew +1;
                       }else {
                           
                       }
                       
                    }
                    rstl.closeRowSetIterator();
                    adfLog.info("count new value   "+countNew);
                    if(countNew>1){
                       adfLog.info(" both set id else end part defferent please rollback ");
                       
                        /**
                             * Rollback needed becoz if multiple user enter the same record like
                              * same supplier,same parameter then one record has to roll back the transaction
                              * becoz crash appears see the log.
                          */
                       
                       setUpBindVar.setValue(null);
                       OperationBinding createOB1 = executeBinding("Rollback");
                               createOB1.execute();
                       setUpBindVar.setValue(null);
                       setUpBindVar.setValue("");
                       reEvaluateButton = getreEvaluateButtonVal();
                       readOnlyField = true;
                       this.setSetupVis("");
                       this.setMode("V");
                       suppliertableBind.setRowSelection(RichTable.ROW_SELECTION_SINGLE);
                       
                       FacesMessage msg = new FacesMessage("The Evaluation is Rollback because Another User Evaluate Same Supplier and Same Parameter Set or Different Parameter Sat.");
                       msg.setSeverity(FacesMessage.SEVERITY_WARN);
                       FacesContext ctx = FacesContext.getCurrentInstance();
                       ctx.addMessage(null, msg);
                       return null;
                    
                }
                }  
                OperationBinding createOB = executeBinding("Commit");
                        createOB.execute();
                
                if (createOB.getErrors().isEmpty()) {
                    readOnlyField = true;

                    this.setSetupVis("");
                    if ("A".equals(Mode)) {
                        String Defaultmsg = resolvEl("#{bundle['MSG.89']}");

                        String valMsg = Defaultmsg.format(Defaultmsg, "", "", "", "", "");
                        FacesMessage msg = new FacesMessage(valMsg);
                        msg.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext ctx = FacesContext.getCurrentInstance();
                        ctx.addMessage(null, msg);
                    } else if ("E".equals(Mode)) {
                        String Defaultmsg = resolvEl("#{bundle['MSG.90']}");

                        String valMsg = Defaultmsg.format(Defaultmsg, "", "", "", "", "");
                        FacesMessage msg = new FacesMessage(valMsg);
                        msg.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext ctx = FacesContext.getCurrentInstance();
                        ctx.addMessage(null, msg);
                    }
                    
                }
            }
            reEvaluateButton = getreEvaluateButtonVal();
            this.setMode("");
            suppliertableBind.setRowSelection(RichTable.ROW_SELECTION_SINGLE);
        }
        BindingContainer bindings4 = getBindings();
        DCIteratorBinding parentIter = (DCIteratorBinding)bindings4.get("AppEoPrfIterator");
        Key parentKey = parentIter.getCurrentRow().getKey();
       if(parentKey!= null){
                      parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                 }
        
        return null;
        
    }


    public void perRateChangeListner(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            String cldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            Integer SlocId=Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
            String orgId=resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
            Number rate = (Number)vce.getNewValue();
            MmSupplierPerfEvalAMImpl am = (MmSupplierPerfEvalAMImpl)resolvElDC("MmSupplierPerfEvalAMDataControl");
            ViewObjectImpl v = am.getMmEoPerfEval1();
            Row currRw = v.getCurrentRow();

            String ParamId = currRw.getAttribute("ParamId").toString();
            String ParamSetId = currRw.getAttribute("ParamSetId").toString();
            Number scoreVal=new Number(0);
            
            BigDecimal min =
                (BigDecimal)callStoredFunction(INTEGER, "MM.PKG_MM_EVAL.GET_MIN_RATE(?,?,?,?)", new Object[] { SlocId,cldId, orgId,
                                                                                                            ParamSetId });
                BigDecimal max =
                   (BigDecimal)callStoredFunction(INTEGER, "MM.PKG_MM_EVAL.GET_MAX_RATE(?,?,?,?)", new Object[] { SlocId,cldId, orgId,
                                                                                                            ParamSetId  });
                
                //if(currRw.getAttribute("TransIsValidRate")!=null && currRw.getAttribute("TransIsValidRate").toString().equals("Y"))
                //{    
                if(rate.compareTo(min)>=0 && rate.compareTo(max)<=0) {
                    scoreVal = Score(ParamSetId, ParamId, rate);
                 }
                //}
         //   System.out.println("now the trans change value is "+scoreVal);
            currRw.setAttribute("PerfScore", scoreVal);

            AdfFacesContext.getCurrentInstance().addPartialTarget(perScoreBindVar);
            AdfFacesContext.getCurrentInstance().addPartialTarget(perfEvalTable);
        }
    }

    public OperationBinding executeBinding(String var) {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding(var);
        return operationBinding;
    }

    public void setReadOnlyField(Boolean readOnlyField) {
        this.readOnlyField = readOnlyField;
    }

    public Boolean getReadOnlyField() {
        return readOnlyField;
    }


    public void setPerScoreBindVar(RichInputText perScoreBindVar) {
        this.perScoreBindVar = perScoreBindVar;
    }

    public RichInputText getPerScoreBindVar() {
        return perScoreBindVar;
    }

    public void setPerfEvalTable(RichTable perfEvalTable) {
        this.perfEvalTable = perfEvalTable;
    }

    public RichTable getPerfEvalTable() {
        return perfEvalTable;
    }


   /* public void setEditButton(RichCommandButton editButton) {
        this.editButton = editButton;
    }

    public RichCommandButton getEditButton() {
        return editButton;
    }*/

   /* public void setSaveButton(RichCommandButton saveButton) {
        this.saveButton = saveButton;
    }

    public RichCommandButton getSaveButton() {
        return saveButton;
    }*/

    public void perRateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
           /*  Number val = (Number)object;

            int comMinVal = val.compareTo(new Number(getMinRate()));
            int comMaxVal = val.compareTo(new Number(getMaxRate()));


            if (comMinVal == -1) {
                String msg = resolvEl("#{bundle['MSG.387']}").toString() + getMinRate();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));

            }
            if (comMaxVal == 1) {
                String msg = resolvEl("#{bundle['MSG.388']}").toString() + getMaxRate();
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
 */
//System.out.println("Not Null");
        } else if (object == null) {
           // System.out.println("Null value");
            String msg = resolvEl("#{bundle['MSG.389']}")  + getMinRate() + resolvEl("#{bundle['MSG.323']}")  + getMaxRate();
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        }
    }


    public void setMinRate(Number minRate) {
        this.minRate = minRate;
    }

    public Number getMinRate() {
        MmSupplierPerfEvalAMImpl am = (MmSupplierPerfEvalAMImpl)resolvElDC("MmSupplierPerfEvalAMDataControl");
        ViewObject v = am.getMmEoPerfEval1();
        if(v.getEstimatedRowCount()>0){
        Row currRw = v.getCurrentRow();
       // System.out.println("sloc id in get min rate is "+currRw.getAttribute("SlocId"));
        Integer slocid = (Integer)currRw.getAttribute("SlocId");
        String cldid = currRw.getAttribute("CldId").toString();
        String orgid = currRw.getAttribute("OrgId").toString();
        String ParameterSetId = currRw.getAttribute("ParamSetId").toString();
        BigDecimal value =
            (BigDecimal)callStoredFunction(INTEGER, "MM.PKG_MM_EVAL.GET_MIN_RATE(?,?,?,?)", new Object[] { slocid,cldid, orgid,
                                                                                                        ParameterSetId });


        try {
            minRate = new Number(value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
        else{
            minRate=null;
        }
        return minRate;
    }

    public void setMaxRate(Number maxRate) {
        this.maxRate = maxRate;
    }

    public Number getMaxRate() {
        MmSupplierPerfEvalAMImpl am = (MmSupplierPerfEvalAMImpl)resolvElDC("MmSupplierPerfEvalAMDataControl");
        ViewObjectImpl v = am.getMmEoPerfEval1();
        if(v.getEstimatedRowCount()>0){
        Row currRw = v.getCurrentRow();
        Integer slocid = (Integer)currRw.getAttribute("SlocId");
        String cldid = currRw.getAttribute("CldId").toString();
        String orgid = currRw.getAttribute("OrgId").toString();
        String ParameterSetId = currRw.getAttribute("ParamSetId").toString();
         BigDecimal value =
            ( BigDecimal)callStoredFunction(INTEGER, "MM.PKG_MM_EVAL.GET_MAX_RATE(?,?,?,?)", new Object[] { slocid,cldid, orgid,
                                                                                                     ParameterSetId });
        try {
            maxRate = new Number(value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
        else{
            maxRate=null;
        }
        return maxRate;
    }

    public void setSetUpBindVar(RichSelectOneChoice setUpBindVar) {
        this.setUpBindVar = setUpBindVar;
    }

    public RichSelectOneChoice getSetUpBindVar() {
        return setUpBindVar;
    }

    public void setSetupVis(String setupVis) {
        this.setupVis = setupVis;
    }

    public String getSetupVis() {
        if (setupVis == "") {
            return getsetUpVisVal();

        } else {
            return setupVis;
        }


    }


    public void reEvaluateDialogListner(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
            MmSupplierPerfEvalAMImpl am = (MmSupplierPerfEvalAMImpl)resolvElDC("MmSupplierPerfEvalAMDataControl");
            Integer SlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String CldId=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            String OrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
            callStoredProcedure("MM.PKG_MM_EVAL.TRNSFR2HIST_EO_EVAL(?,?,?)", new Object[] { SlocId,CldId, OrgId });
            OperationBinding createOB = executeBinding("Commit");
            createOB.execute();

            OperationBinding createexe = executeBinding("Execute");
            createexe.execute();
            suppliertableBind.setRowSelection(RichTable.ROW_SELECTION_SINGLE);
            am.getMmEoPerfRest1().executeQuery();
            this.setSetupVis("true");
            if (createOB.getErrors().isEmpty()) {
                String valMsg = resolvEl("#{bundle['MSG.390']}");
                FacesMessage msg = new FacesMessage(valMsg);
                msg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, msg);
                reEvaluateButton = getreEvaluateButtonVal();
                this.setMode("");
                AdfFacesContext.getCurrentInstance().addPartialTarget(setUpBindVar);
                AdfFacesContext.getCurrentInstance().addPartialTarget(perfEvalTable);
                AdfFacesContext.getCurrentInstance().addPartialTarget(editButton);
                AdfFacesContext.getCurrentInstance().addPartialTarget(saveButton);
                AdfFacesContext.getCurrentInstance().addPartialTarget(createButton);
            }
        }
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

    public String reEvaluationAction() {
        suppliertableBind.setRowSelection(RichTable.ROW_SELECTION_NONE);
        showPopup(popup, true);
        return null;
    }

    public void setPopup(RichPopup popup) {
        this.popup = popup;
    }

    public RichPopup getPopup() {
        return popup;
    }

    public void setReEvaluateButton(String reEvaluateButton) {
        this.reEvaluateButton = reEvaluateButton;
    }

    public String getReEvaluateButton() {
        return reEvaluateButton;
    }

    /*public void setCreateButton(RichCommandButton createButton) {
        this.createButton = createButton;
    }

    public RichCommandButton getCreateButton() {
        return createButton;
    }*/

    public void setMode(String Mode) {
        this.Mode = Mode;
    }

    public String getMode() {
        if(Mode==""){
            return "V";
        }
        else{
            return Mode;
        }
        
    }

    public void setSuppliertableBind(RichTable suppliertableBind) {
        this.suppliertableBind = suppliertableBind;
    }

    public RichTable getSuppliertableBind() {
        return suppliertableBind;
    }
    
    public Object resolvElDCMsg(String data) {
           FacesContext fc = FacesContext.getCurrentInstance();
           Application app = fc.getApplication();
           ExpressionFactory elFactory = app.getExpressionFactory();
           ELContext elContext = fc.getELContext();
           ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
           return valueExp.getValue(elContext);
       }

    public void setCreateButton(RichCommandImageLink createButton) {
        this.createButton = createButton;
    }

    public RichCommandImageLink getCreateButton() {
        return createButton;
    }

    public void setEditButton(RichCommandImageLink editButton) {
        this.editButton = editButton;
    }

    public RichCommandImageLink getEditButton() {
        return editButton;
    }

    public void setSaveButton(RichCommandImageLink saveButton) {
        this.saveButton = saveButton;
    }

    public RichCommandImageLink getSaveButton() {
        return saveButton;
    }

    public void currentRowActionListener(ActionEvent actionEvent) {
        System.out.println("command button also get fires");
        MmSupplierPerfEvalAMImpl am = (MmSupplierPerfEvalAMImpl)resolvElDC("MmSupplierPerfEvalAMDataControl");
        ViewObjectImpl v = am.getMmEoPerfEval1();
      //  System.out.println();
        adfLog.info( v.getCurrentRow().getKey().toString());
        RowSetIterator r33=v.getRowSetIterator();
            Row currRw = v.getCurrentRow();
        currRw=r33.next();
        adfLog.info( v.getCurrentRow().getKey().toString());
         
  }
}
