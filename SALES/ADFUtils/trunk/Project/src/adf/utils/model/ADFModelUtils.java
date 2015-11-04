package adf.utils.model;
import adf.utils.exception.InvalidArgumentException;
import adf.utils.exception.ADFUtilsException;
import adf.utils.log.LogUtil;
import adf.utils.model.dbprocsupp.ProcPrams;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.DBTransaction;
import oracle.jbo.server.ViewObjectImpl;

/**
 * This class basically contains the Methods that are commonly used in Model Part of ADF Application.
 * Although these methods can be called from anywhere.
 */
public class ADFModelUtils {
    private static String drivernme = null;
    private static ADFLogger _log = ADFLogger.createADFLogger(ADFModelUtils.class);

    public ADFModelUtils() {
        super();
    }

    /**
     * Method for Resolving any EL and returning the value as an Object.
     * Returns null of no expression is passed.
     * @param expression  The El which need to be resolved
     * @return Object  Returns the value which is obtained after resolving the El.
     */
    public static Object resolvEl(String expression) {
        Object o = null;
        if (expression != null) {
            ADFContext adfCtx = ADFContext.getCurrent();
            ELContext elContext = adfCtx.getELContext();
            ValueExpression valueExp =
                adfCtx.getExpressionFactory().createValueExpression(elContext, expression, Object.class);
            o = valueExp.getValue(elContext);
        } else {
            _log.severe("Warning : Method | resolvEl(StringBuilder expression) | called with parameter as null.");
        }
        if (LogUtil.enableLogger) {
            _log.fine("Method | resolvEl(StringBuilder expression) | called with following parameters ");
            _log.fine("=======> resolvEl(" + expression + ") | Returned : " + o);
        }
        return o;
    }

    /**
     * Method to Call Function or Procedure for ADF.
     * This Returns only one parameter.
     * @param am  ApplicationModuleImpl instance
     * @param sqlStatmnt  Sql Statement that need to be executed
     * @param params  Parameters that need to be put in the fucntion
     * @param returnTyp  Object which is the result
     * @return
     */
    public static Object callFunction(ApplicationModuleImpl am, StringBuilder sqlStatmnt, Object[] params,
                                      int returnTyp) {
        Object o = null;
        long init = System.currentTimeMillis();
        CallableStatement st = null;
        if (LogUtil.enableLogger) {
            _log.info("Info : Method | callFunction(ApplicationModuleImpl am,StringBuilder sqlStatmnt,Object[] params, int returnTyp) | called with following parameters : ");
            StringBuilder logO = new StringBuilder("Object [");
            int j = 0;
            for (Object i : params) {
                j = j + 1;
                logO.append(i);
                if (params.length > j) {
                    logO.append(",");
                }
            }
            logO.append("]");
            _log.info("===> callFunction(" + am + "," + sqlStatmnt + "," + logO + "," + returnTyp + ") ");
        }
        try {
            //if (getDrvrNm(am).equalsIgnoreCase("MySQL-AB JDBC Driver")) {
            //    sqlStatmnt = new StringBuilder(sqlStatmnt.substring(sqlStatmnt.lastIndexOf(".") + 1));
            //    st = am.getDBTransaction().createCallableStatement("{ ? = call " + sqlStatmnt + " } ; ", 0);
            //} else {
                st = am.getDBTransaction().createCallableStatement("begin ? := " + sqlStatmnt + ";end;", 0);
            //}
            st.registerOutParameter(1, returnTyp);
            if (params != null) {
                for (int z = 0; z < params.length; z++) {
                    st.setObject(z + 2, params[z]);
                }
            }
            st.executeUpdate();
            o = st.getObject(1);
        } catch (SQLException e) {
            ADFModelUtils.showFormattedFacesMessage("",e.getMessage(), FacesMessage.SEVERITY_ERROR);
            FacesMessage msg = new FacesMessage();
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            e.printStackTrace();
            throw new JboException(e.getMessage());
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    _log.info(e);
                }
            }
        }
        if (LogUtil.enableLogger) {
            _log.info(" | Returned : " + o);
            long lst = System.currentTimeMillis();
            _log.info(" Time taken to call the above function is : " + (lst - init));
        }
        return o;
    }

    /**
     * Method to check for Duplicate attribute value in a ViewObject.
     * @param vo viewObject in which duplication needs to checked.
     * @param val value for which duplication needs to be checked.
     * @param attr attribute's name in the vo for which duplication check will run.
     * @param excludeCurrentRow - flag to specify whether you want to consider current row for the check or not.
     * This param was required as sometimes when we are editing the attribute in the current row, and then if we are running duplicate check then it needs to be excluded. If
     * we are editing outside the vo and checking for a value then the check should run on all the rows.
     * @return Boolean  Returns true if the check passed and false when fails
     * @throws InvalidArgumentException This exception is thrown if any passed argument in the method is invalid.
     * @throws Exception
     */
    public static Boolean isValueForAttributeUnique(ViewObjectImpl vo, Object val, StringBuilder attr,
                                                    Boolean excludeCurrentRow) throws Exception {
        Boolean b = true;
        if (!(val == null || vo == null || attr == null || excludeCurrentRow == null)) {
            Row currentRow = (excludeCurrentRow ? vo.getCurrentRow() : null);
            if (excludeCurrentRow && currentRow == null) {
                throw new ADFUtilsException("Current Row needed for the operation in " + vo.getName() +
                                            " returned null");
            }
            RowSetIterator rowItr = vo.createRowSetIterator(null);
            if (val.getClass().getSimpleName().contains("String")) {
                while (rowItr.hasNext()) {
                    Row row = rowItr.next();
                    if (row != currentRow) {
                        Object r = null;
                        try {
                            r = row.getAttribute(attr.toString());
                        } catch (Exception e) {
                            b = false;
                            throw new ADFUtilsException("Attribute " + attr + " do not exist in " + vo.getName());
                        }
                        StringBuilder s = (r == null ? new StringBuilder("") : new StringBuilder(r.toString()));
                        if (s.toString().equalsIgnoreCase(val.toString())) {
                            b = false;
                            break;
                        }
                    }
                }
                rowItr.closeRowSetIterator();
            } else {
                b = false;
                _log.severe("Warning : Method | isValueForAttributeUnique(ViewObjectImpl vo,Object val,StringBuilder attr,Boolean excludeCurrentRow) | called to validate a non-String value." +
                            " | Returnd : " + b);
            }
        } else {
            b = false;
            throw new InvalidArgumentException("One or more Argument is passed as null.");
        }
        if (LogUtil.enableLogger) {
            _log.info("Method | isValueForAttributeUnique(ViewObjectImpl vo,Object val,StringBuilder attr,Boolean excludeCurrentRow) | called with following parameters ");
            _log.info("=======> isValueForAttributeUnique(" + vo + "," + val + "," + attr + "," + excludeCurrentRow +
                      ") | Returned : " + b);
        }
        return b;
    }

    /**
     * Method to show simple facesMessage with header,detail, severity and if clientId is passed then message will be attached to that client id if available.
     * @param header Header of the FacesMesssage
     * @param detail Detail of the FacesMesssage (Html can be passed in here)
     * @param severity Severity of FacesMessage ex : FacesMessage.SEVERITY_WARN
     * @param clientId Client id of the component needs to be passed to attach this message to a component. If null then simple message will be thrown.
     */
    public static void showFacesMessage(String header, String detail,
                                        javax.faces.application.FacesMessage.Severity severity, String clientId) {
        FacesMessage msg = new FacesMessage(header, detail);
        msg.setSeverity(severity);
        FacesContext.getCurrentInstance().addMessage(clientId, msg);
    }

    /**
     * Method to show simple facesMessage with header,detail, severity and if clientId is passed then message will be attached to that client id if available
     * Message will be formatted in html.
     * @param header  Header of the FacesMesssage. (Message format may be not look as desired if html is passed in message. However you may try Hit & Try.)
     * @param detail  Detail of the FacesMesssage. (Message format may be not look as desired if html is passed in message. However you may try Hit & Try.)
     * @param severity  Severity of FacesMessage ex : FacesMessage.SEVERITY_WARN
     */
    public static void showFormattedFacesMessage(String header, String detail,
                                                 javax.faces.application.FacesMessage.Severity severity) {
        StringBuilder saveMsg = new StringBuilder("<html><body><b><p style='color:red'>");
        saveMsg.append(header);
        saveMsg.append("</p></b>");
        saveMsg.append("<b>");
        saveMsg.append(detail);
        saveMsg.append("</b></body></html>");
        FacesMessage msg = new FacesMessage(saveMsg.toString());
        msg.setSeverity(severity);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }


    /**
     * Method to Call Procedure for ADF.
     * This Returns only one parameter.
     * @param am ApplicationModuleImpl instance
     * @param sqlStmt
     * @param procParams Parameters that need to be put in the function
     * @return
     * WIP
     * @throws SQLException
     */
    public static ArrayList callDbProcedure(ApplicationModuleImpl am, StringBuilder sqlStmt,
                                           ProcPrams[] procParams) throws SQLException {
        long init = System.currentTimeMillis();

        ArrayList al=new ArrayList();
        CallableStatement st = null;
        try {
            st = am.getDBTransaction().createCallableStatement("begin  " + sqlStmt + ";end;", 0);
            for (ProcPrams p : procParams) {
                if (p.getParamType() == ProcPrams.IN) {
                    System.out.println("in Parameter"+p.getSrNo()+" is "+p.getValue());
                    st.setObject(p.getSrNo(), p.getValue());
                } else if (p.getParamType() == ProcPrams.OUT) {
                    System.out.println("out Parameter"+p.getSrNo()+" is "+p.getDataType().toString());
                    st.registerOutParameter(p.getSrNo(), p.getDataType());
                }
            }
            st.executeUpdate();

            for (ProcPrams p : procParams) {
                if (p.getParamType() == ProcPrams.OUT) {
                    al.add(st.getObject(p.getSrNo()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            FacesMessage msg = new FacesMessage(e.getMessage());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            //throw new JboException(e.getMessage());
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    //_log.info(e);
                }
            }

        }

        if (LogUtil.enableLogger) {
            _log.info(" | Returned : " + al);
            long lst = System.currentTimeMillis();
            _log.info(" Time taken to call the above function is : " + (lst - init));
        }
        return al;
    }


    /**
     * Method to get the name of Current Database connection driver.
     * @param am - ApplicationModule
     * @return
     */
    public static String getDrvrNm(ApplicationModuleImpl am) {
        if(drivernme == null){
            DBTransaction dbTransaction = (DBTransaction) am.getTransaction();
            PreparedStatement prepStatement = dbTransaction.createPreparedStatement("select * from dual", 0);
            try {
                drivernme = prepStatement.getConnection().getMetaData().getDriverName();
            } catch (SQLException e) {
                e.printStackTrace();
            }    
        }
        return drivernme;
    } 
    
    /**
     * Method to call function on the basis of Function DBOB Id.
     * This method doesn't work for procedures.
     * @param am
     * @param fnDbObId
     * @param params
     * @param returnTyp
     * @return
     */
    public static Object callFunctionFrmId (ApplicationModuleImpl am, Integer fnDbObId, Object[] params, int returnTyp) {
        Object o = null;
        StringBuilder fnNm = new StringBuilder("");
        Object callFn = callFunction(am, new StringBuilder("APP.GET_FN_NM_FRM_ID(?)"), new Object[] { fnDbObId },
                                     Types.VARCHAR);
        if (callFn != null) {
            fnNm = fnNm.append(callFn.toString());
            fnNm = fnNm.append("(");
            Integer i = 1;
            while(params.length >= i){
                fnNm.append("?");
                if(params.length != i){
                    fnNm.append(",");
                }
                i = i+1;
            }
            fnNm.append(")");
            o = callFunction(am, fnNm, params, returnTyp);
        }
        return o;
    }
    
    /**
     * Method to call function on the basis of Function doc Id.
     * This method doesn't work for procedures.
     * @param am - Application Module
     * @param docId - Function Doc id
     * @param docLvl - Function doc Lvl
     * @param docAction -
     * @param docComp
     * @param rule
     * @param execSeq
     * @param params
     * @param returnTyp
     * @return
     */
    public static Object callFunctionFrmDocNRuleId (ApplicationModuleImpl am, Integer docId,Integer docLvl,Integer docAction,
                                            Integer docComp,Integer rule,Integer execSeq, Object[] params, int returnTyp) {
    
        Object o = null;
        StringBuilder fnNm = new StringBuilder("");
        Object callFn = callFunction(am, new StringBuilder("APP.FN#_GET_RULE_FN_W(?,?,?,?,?,?)"), new Object[] { docId,docLvl,docAction,docComp,rule,execSeq},
                                     Types.VARCHAR);
        if (callFn != null) {
            fnNm = fnNm.append(callFn.toString());
    //            fnNm = fnNm.append("(");
    //            Integer i = 1;
    //            while(params.length >= i){
    //                fnNm.append("?");
    //                if(params.length != i){
    //                    fnNm.append(",");
    //                }
    //                i = i+1;
    //            }
    //            fnNm.append(")");
            o = callFunction(am, fnNm, params, returnTyp);
        }
        return o;
    }

    /**
     * @param am
     * @param docId
     * @param docLvl
     * @param docAction
     * @param docComp
     * @param rule
     * @param execSeq
     * @param params
     * @return
     * @throws SQLException
     */
    public static ArrayList callProcedureFrmDocNRuleId (ApplicationModuleImpl am, Integer docId,Integer docLvl,Integer docAction,
                                            Integer docComp,Integer rule,Integer execSeq, Object[] params) throws SQLException {
        ArrayList al = new ArrayList();
        StringBuilder fnNm = new StringBuilder("");
        Object callFn = callFunction(am, new StringBuilder("APP.FN#_GET_RULE_FN_W(?,?,?,?,?,?)"), new Object[] { docId,docLvl,docAction,docComp,rule,execSeq},
                                     Types.VARCHAR);
        if (callFn != null) {
                fnNm = fnNm.append(callFn.toString());
    //            fnNm = fnNm.append("(");
    //            Integer i = 1;
    //            while(params.length >= i){
    //                fnNm.append("?");
    //                if(params.length != i){
    //                    fnNm.append(",");
    //                }
    //                i = i+1;
    //            }
    //            fnNm.append(")");
         //   o = callFunction(am, fnNm, params, returnTyp);
             al = callDbProcedure(am,  fnNm, (ProcPrams[]) params);
        }
        return al;
    }

    /**
     * Method to resolve resource
     * @param res : Resource Lable
     * @return resource message/lable as String. Return "" when resource not found
     */
    public static String resolvRsrc(String res) {
        Object el = null;
        if (res != null) {
            StringBuilder s = new StringBuilder("#{bundle['");
            s.append(res);
            s.append("']}");
            el = resolvEl(s.toString());
        }
        if (LogUtil.enableLogger) {
            LogUtil.showInfoLog(_log, "resolvRsrc", new Object[] { "res" }, new Object[] { res }, el);
        }
        return (el == null ? "<Resource does not Exist>" : el.toString());
    }
}
