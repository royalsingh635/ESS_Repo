package ebizframemainapplication.view.bean;

import ebizframemainapplication.model.EbizMainAppAMImpl;

import ebizframemainapplication.model.views.AppSecUsrMnuVOImpl;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

import java.util.Set;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.event.ValueChangeEvent;

import javax.servlet.http.HttpSession;

import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;
import oracle.jbo.client.Configuration;
import oracle.jbo.server.DBTransaction;
import oracle.jbo.server.ViewObjectImpl;


public class DashBoardBean {
    private String orgNm = "";
    private Integer user_id;
    private String _usrCldId;
    private String ho_org;
    private String date_format;
    private String num_format;
    private String lang_path;
    private String lang_file;
    private Integer lang_id;
    private Integer amt_digit;
    private Integer curr_digit;
    private Integer rate_digit;
    private Integer qty_digit;
    private Integer disc_digit;
    private static int STRING = Types.VARCHAR;
    private static int NUMERIC = Types.NUMERIC;
    private RichSelectOneChoice orgBindVar;
    private String wh_chk;
    private String bin_chk;
    private String ge_chk;
    FacesContext context = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
    private static ADFLogger adflog = ADFLogger.createADFLogger(DashBoardBean.class);
    private Map<String, Object> menuVisibleMap = new HashMap<String, Object>();
    private boolean cloudVisible = false;

    public DashBoardBean() {
        //System.out.println("inside Dashboard Page");
        getGlobalParameter();

    }

    public String finance() {
        String orgId = orgBindVar.getValue().toString();
        // System.out.println(orgId);


        if (resolvEl("#{bindings.Organisation.selectedValue.attributeValues[1]}") != null)
            orgNm = resolvEl("#{bindings.Organisation.selectedValue.attributeValues[1]}").toString();
        // System.out.println("organization nm---->"+orgNm);

        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
        session.setAttribute("OrganisationId", orgId);
        session.setAttribute("OrganisationNm", orgNm);

        getParentOrg();
        return "Finance";
    }

    public String MaterialManagement() {
        String orgId = orgBindVar.getValue().toString();
        // System.out.println(orgId);

        if (resolvEl("#{bindings.Organisation.selectedValue.attributeValues[1]}") != null)
            orgNm = resolvEl("#{bindings.Organisation.selectedValue.attributeValues[1]}").toString();
        //System.out.println("organization nm---->"+orgNm+"----Session--->"+session);

        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
        session.setAttribute("OrganisationId", orgId);
        session.setAttribute("OrganisationNm", orgNm);
        getParentOrg();

        // change for MM profile setup------------------------------ BL
        String profile_Freez = "N";
        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                profile_Freez =
                        (callProcedureOut(STRING, "call mm_chk_org_prf(?,?,?,?,?)", new Object[] { getUsrCldId(), getSloc_id(),
                                                                                                 orgId,
                                                                                                 "FREEZ_PRF" }).toString());   
          System.out.println("profile_Freez ---"+profile_Freez);  
          }
            else
            {
            profile_Freez =
                    (callStoredFunction(STRING, "MM.mm_chk_org_prf(?,?,?,?)", new Object[] { getUsrCldId(), getSloc_id(),
                                                                                             orgId,
                                                                                             "FREEZ_PRF" }).toString());
            }

        } catch (Exception e) {
            adflog.info("Error @ EbizMainApplication-->DashBoardBean-->getGlobalParameter()-->MM.mm_chk_org_prf(?,?,?,?) ");
            adflog.info("parameter values-->" + " sloc id :" + getSloc_id() + ": getCloud_id  : " + getUsrCldId() +
                        " : orgId : " + orgId);
            //setLang_path("D:\\Resource\\");
            e.printStackTrace();

        }
        adflog.info("Error @ EbizMainApplication-->DashBoardBean--> profile_Freez value : " + profile_Freez);
        if ("Y".equalsIgnoreCase(profile_Freez)) {
            getMMProfileSetup();

            return "MM";
        } else {
            FacesMessage msg = new FacesMessage("MM Profile not Create for " + orgNm);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(orgBindVar.getClientId(), msg);
            return null;
        }
    }


    // set mm profile paramerters

    public void getMMProfileSetup() {
        String orgId = orgBindVar.getValue().toString();
        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                wh_chk =
                        (callProcedureOut(STRING, "call mm_chk_org_prf(?,?,?,?,?)", new Object[] { getUsrCldId(), getSloc_id(),
                                                                                                 orgId,
                                                                                                 "USE_WH" }).toString());   
                System.out.println(" wh_ck --"+wh_chk);
            }
            else
            {
            wh_chk =
                    (callStoredFunction(STRING, "MM.mm_chk_org_prf(?,?,?,?)", new Object[] { getUsrCldId(), getSloc_id(),
                                                                                             orgId,
                                                                                             "USE_WH" }).toString());
            }
            //System.out.println("date format from funtion------>" + date_format);
            adflog.info("wh Used checked ------> " + wh_chk);
            setWh_chk(wh_chk);

        } catch (Exception e) {

            setWh_chk("Y");
            adflog.info("Error @ EbizMainApplication-->DashBoardBean-->getMMProfileSetup()-->MM.mm_chk_org_prf(?,?,?,?) ");
            adflog.info("parameter values-->" + " sloc id :" + getSloc_id() + ": getCloud_id  : " + getUsrCldId() +
                        " : orgId : " + orgId + "-USE_WH- ");
            e.printStackTrace();
        }
        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                bin_chk =
                        (callProcedureOut(STRING, "CALL mm_chk_org_prf(?,?,?,?,?)", new Object[] { getUsrCldId(), getSloc_id(),
                                                                                                 orgId,
                                                                                                 "USE_BIN" }).toString());  
                System.out.println(" bin_chk --"+bin_chk);

            }
            else
            {
            bin_chk =
                    (callStoredFunction(STRING, "MM.mm_chk_org_prf(?,?,?,?)", new Object[] { getUsrCldId(), getSloc_id(),
                                                                                             orgId,
                                                                                             "USE_BIN" }).toString());
            }
            //System.out.println("date format from funtion------>" + date_format);
            adflog.info("Bin Used checked ------> " + bin_chk);
            setBin_chk(bin_chk);

        } catch (Exception e) {

            setBin_chk("Y");
            adflog.info("Error @ EbizMainApplication-->DashBoardBean-->getMMProfileSetup()-->MM.mm_chk_org_prf(?,?,?,?) ");
            adflog.info("parameter values-->" + " sloc id :" + getSloc_id() + ": getCloud_id  : " + getUsrCldId() +
                        " : orgId : " + orgId + "-USE_BIN  ");
            e.printStackTrace();
        }
        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                ge_chk =
                        (callProcedureOut(STRING, "call mm_chk_org_prf(?,?,?,?,?)", new Object[] { getUsrCldId(), getSloc_id(),
                                                                                                 orgId,
                                                                                                 "USE_GATE_ENTRY" }).toString());   
                System.out.println(" ge_chk --"+ge_chk);

 }
            else
            {
            ge_chk =
                    (callStoredFunction(STRING, "MM.mm_chk_org_prf(?,?,?,?)", new Object[] { getUsrCldId(), getSloc_id(),
                                                                                             orgId,
                                                                                             "USE_GATE_ENTRY" }).toString());
            }
            //System.out.println("date format from funtion------>" + date_format);
            adflog.info("GE Used checked ------> " + ge_chk);
            setGe_chk(ge_chk);

        } catch (Exception e) {

            setGe_chk("Y");
            adflog.info("Error @ EbizMainApplication-->DashBoardBean-->getMMProfileSetup()-->MM.mm_chk_org_prf(?,?,?,?) ");
            adflog.info("parameter values-->" + " sloc id :" + getSloc_id() + ": getCloud_id  : " + getUsrCldId() +
                        " : orgId : " + orgId + "-USE_GATE_ENTRY  ");
            e.printStackTrace();
        }

    }

    public String Security() {
        String orgId = orgBindVar.getValue().toString();
        // System.out.println(orgId);

        if (resolvEl("#{bindings.Organisation.selectedValue.attributeValues[1]}") != null)
            orgNm = resolvEl("#{bindings.Organisation.selectedValue.attributeValues[1]}").toString();
        //System.out.println("organization nm---->"+orgNm+"----Session--->"+session);

        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
        session.setAttribute("OrganisationId", orgId);
        session.setAttribute("OrganisationNm", orgNm);
        getParentOrg();
        return "Security";
    }

    public void setOrgBindVar(RichSelectOneChoice orgBindVar) {
        this.orgBindVar = orgBindVar;
    }

    public RichSelectOneChoice getOrgBindVar() {
        return orgBindVar;
    }

    /**This Method has been added on  July 15, 2013 to get values which will be used by other beans to set the global task flow parameter,
     * the setter method called after each fucntion call will create a session variables to carry the values to other beans.
     * **/

    public void getGlobalParameter() {
        
        //System.out.println("Setting global Parameter");

        //to set the default cloud based on user
        setUsrCldId(null);
        //Enable LOV for cloud if user cloud count is greater than 1.
        if (getUsrCloudCount().compareTo(1) == 1) {
            setCloudVisible(true);

        }

        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                date_format =
                        (String)callStoredFunction(STRING, "call FN_GET_APP_DT_FORMAT(?)", new Object[] { getUsrCldId() });   
            }
            else
            {
            date_format =
                    (String)callStoredFunction(STRING, "APP.FN_GET_APP_DT_FORMAT(?)", new Object[] { getUsrCldId() });
            }
            //System.out.println("date format from funtion------>" + date_format);

            setDate_format(date_format);

        } catch (Exception e) {

            setDate_format("dd/MM/yyyy");
            System.out.println("Error @ EbizMainApplication-->DashBoardBean-->getGlobalParameter()-->APP.FN_GET_APP_DT_FORMAT(?) ");
            System.out.println("parameter values-->" + " cloud id :" + getUsrCldId());
            e.printStackTrace();
        }
        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                amt_digit =
                        Integer.parseInt(callStoredFunction(NUMERIC, "call FN_GET_APP_AMT_DIGIT(?)", new Object[] { getUsrCldId() }).toString());   
            }
            else
            {
            amt_digit =
                    Integer.parseInt(callStoredFunction(NUMERIC, "APP.FN_GET_APP_AMT_DIGIT(?)", new Object[] { getUsrCldId() }).toString());
            }
            //  System.out.println("amt digit from funtion------>" + amt_digit);
            setAmt_digit(amt_digit);

        } catch (Exception e) {

            setAmt_digit(2);
            System.out.println("Error @ EbizMainApplication-->DashBoardBean-->getGlobalParameter()-->APP.FN_GET_APP_AMT_DIGIT(?) ");
            System.out.println("parameter values-->" + " cloud id :" + getUsrCldId());
            e.printStackTrace();
        }
        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                curr_digit =
                        Integer.parseInt(callStoredFunction(NUMERIC, "call FN_GET_APP_CURR_DIGIT(?)", new Object[] { getUsrCldId() }).toString());   
            }
            else
            {
            curr_digit =
                    Integer.parseInt(callStoredFunction(NUMERIC, "APP.FN_GET_APP_CURR_DIGIT(?)", new Object[] { getUsrCldId() }).toString());
            }
            // System.out.println("curr digit from funtion------>" + curr_digit);
            setCurr_digit(curr_digit);

        } catch (Exception e) {
            setCurr_digit(4);
            System.out.println("Error @ EbizMainApplication-->DashBoardBean-->getGlobalParameter()-->FN_GET_APP_CURR_DIGIT(?) ");
            System.out.println("parameter values-->" + " cloud id :" + getUsrCldId());
            e.printStackTrace();
        }
        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                rate_digit =
                        Integer.parseInt(callStoredFunction(NUMERIC, "call FN_GET_APP_RATE_DIGIT(?)", new Object[] { getUsrCldId() }).toString());   
            }
            else
            {
            rate_digit =
                    Integer.parseInt(callStoredFunction(NUMERIC, "APP.FN_GET_APP_RATE_DIGIT(?)", new Object[] { getUsrCldId() }).toString());
            }
            // System.out.println("rate digit from funtion------>" + rate_digit);
            setRate_digit(rate_digit);

        } catch (Exception e) {
            setRate_digit(2);
            System.out.println("Error @ EbizMainApplication-->DashBoardBean-->getGlobalParameter()-->FN_GET_APP_RATE_DIGIT(?) ");
            System.out.println("parameter values-->" + " cloud id :" + getUsrCldId());
            e.printStackTrace();
        }
        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                qty_digit =
                        Integer.parseInt(callStoredFunction(NUMERIC, "call FN_GET_APP_QTY_DIGIT(?)", new Object[] { getUsrCldId() }).toString());   
            }
            else
            {
            qty_digit =
                    Integer.parseInt(callStoredFunction(NUMERIC, "APP.FN_GET_APP_QTY_DIGIT(?)", new Object[] { getUsrCldId() }).toString());
            }
            // System.out.println("qty digit from funtion------>" + qty_digit);
            setQty_digit(qty_digit);

        } catch (Exception e) {
            setQty_digit(2);
            System.out.println("Error @ EbizMainApplication-->DashBoardBean-->getGlobalParameter()-->FN_GET_APP_QTY_DIGIT(?)");
            System.out.println("parameter values-->" + " cloud id :" + getUsrCldId());
            e.printStackTrace();
        }
        try {
            
           // System.out.println("Current USer is"+getUser_id());
           String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                lang_id =
                        Integer.parseInt(callStoredFunction(NUMERIC, "call FN_GET_USR_DEF_LANG_ID(?,?,?,?)", new Object[] { getUsrCldId(),
                                                                                                                           getSloc_id(),
                                                                                                                           getUser_id(),
                                                                                                                           "R" }).toString());   
            }
            else
            {
            lang_id =
                    Integer.parseInt(callStoredFunction(NUMERIC, "APP.FN_GET_USR_DEF_LANG_ID(?,?,?,?)", new Object[] { getUsrCldId(),
                                                                                                                       getSloc_id(),
                                                                                                                       getUser_id(),
                                                                                                                       "R" }).toString());
            }
           //  System.out.println("lang_id from funtion------> by try" + lang_id);
            setLang_id(lang_id);

        } catch (Exception e) {
            setLang_id(1);
            System.out.println("Error @ EbizMainApplication-->DashBoardBean-->getGlobalParameter()-->FN_GET_USR_DEF_LANG_ID(?,?,?,?) ");
            System.out.println("parameter values-->" + " cloud id : " + getUsrCldId() + " sloc id : " + getSloc_id() +
                               " user id : " + getUser_id());
            e.printStackTrace();
        }

        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                lang_file =
                        (callStoredFunction(STRING, "call FN_GET_USR_DEF_LANG_RES_FILE(?,?,?,?)", new Object[] { getUsrCldId(),
                                                                                                              getSloc_id(),
                                                                                                              getUser_id(),"R" }).toString());   
            }
            else
            {
            lang_file =
                    (callStoredFunction(STRING, "APP.FN_GET_USR_DEF_LANG_RES_FILE(?,?,?)", new Object[] { getUsrCldId(),
                                                                                                          getSloc_id(),
                                                                                                          getUser_id() }).toString());
            }
            //  System.out.println("lang_file from funtion------>" + lang_file);
            setLang_file(lang_file);

        } catch (Exception e) {
            System.out.println("Error @ EbizMainApplication-->DashBoardBean-->getGlobalParameter()-->FN_GET_USR_DEF_LANG_RES_FILE(?,?,?) ");
            System.out.println("parameter values-->" + " cloud id : " + getUsrCldId() + " sloc id : " + getSloc_id() +
                               " user id " + getUser_id());
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                lang_file =
                        (callStoredFunction(STRING, "call FN_GET_GLBL_LANG_RES_FILE(?)", new Object[] { getLang_id() }).toString());   
            }
            else
            {
            lang_file =
                    (callStoredFunction(STRING, "APP.FN_GET_GLBL_LANG_RES_FILE(?)", new Object[] { getLang_id() }).toString());
            }
            System.out.println("Using Global lang_file from funtion------>" + lang_file);
            setLang_file(lang_file);
            e.printStackTrace();
        }

        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                lang_path =
                        (callStoredFunction(STRING, "call FN_GET_APP_RESOURCE_PATH(?,?)", new Object[] { getSloc_id(),"J" }).toString());   
            }
            else
            {
            lang_path =
                    (callStoredFunction(STRING, "APP.FN_GET_APP_RESOURCE_PATH(?)", new Object[] { getSloc_id() }).toString());
            }

            // System.out.println("lang_path from funtion------>" + lang_path);
            setLang_path(lang_path);

        } catch (Exception e) {
            System.out.println("Error @ EbizMainApplication-->DashBoardBean-->getGlobalParameter()-->FN_GET_APP_RESOURCE_PATH(?) ");
            System.out.println("parameter values-->" + " sloc id :" + getSloc_id());
            setLang_path("D:\\Resource\\");
            e.printStackTrace();
        }
        
        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                disc_digit =
                        Integer.parseInt(callStoredFunction(STRING, "call FN_GET_APP_DISC_DIGIT(?)", new Object[] { getUsrCldId() }).toString());   
            }
            else
            {
            disc_digit =
                    Integer.parseInt(callStoredFunction(STRING, "APP.FN_GET_APP_DISC_DIGIT(?)", new Object[] { getUsrCldId() }).toString());
            }

             System.out.println("disc_digit from funtion------>" + disc_digit);
            setDisc_digit(disc_digit);

        } catch (Exception e) {
            System.out.println("Error @ EbizMainApplication-->DashBoardBean-->getGlobalParameter()-->FN_GET_APP_DISC_DIGIT(?) ");
            System.out.println("parameter values-->" + " sloc id :" + getSloc_id());
            
            e.printStackTrace();
        }

        //Set the Bind variables for Organization LOV. 
        setOrganizationVOParameter(getUser_id(), getSloc_id());
    }

    public String Administrator() {
        String orgId = orgBindVar.getValue().toString();

        if (resolvEl("#{bindings.Organisation.selectedValue.attributeValues[1]}") != null)
            orgNm = resolvEl("#{bindings.Organisation.selectedValue.attributeValues[1]}").toString();
        //  System.out.println("organization nm---->"+orgNm);

        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
        session.setAttribute("OrganisationId", orgId);
        session.setAttribute("OrganisationNm", orgNm);
        getParentOrg();
        return "Administrator";
    }

    public Object resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext);
        return Message;
    }
    

    public String getdrivername()
    {         String   drivernme ="";
            // MMrmdaAMImpl am = (MMrmdaAMImpl)resolvElDC("MMrmdaAMAMDataControl");
             DBTransaction dbTransaction = (DBTransaction) getAm().getTransaction();
             PreparedStatement prepStatement =
             dbTransaction.createPreparedStatement("select * from dual", 0);
             try
             {
               System.out.println("Driver name--"+prepStatement.getConnection().getMetaData().getDriverName());
               drivernme = prepStatement.getConnection().getMetaData().getDriverName();
             }
             catch (SQLException e)
             {
                 e.printStackTrace();
             }
             return drivernme;

             }

    /**
     * Method to call a Database Stored procedure
     * @param sqlReturnType
     * @param stmt
     * @param bindVars
     * @return
     */
    protected Object callProcedureOut(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        // System.out.println("__________________________________________begin");
        try {
            
            System.out.println("STMT :-----"+stmt);
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                st = getAm().getDBTransaction().createCallableStatement("  " + stmt + ";", 0);   
                System.out.println("st are::::"+st);
            }
            else
            {
            st = getAm().getDBTransaction().createCallableStatement("begin  " + stmt + ";end;", 0);
            }
            
            ///st.registerOutParameter(1, sqlReturnType);
            System.out.println("bind var length   :::: "+bindVars.length);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++){
                    st.setObject(z + 1  , bindVars[z]);
                    //System.out.println(bindVars[z]);
                }
            }
            System.out.println(bindVars.length + 1+ " :::" +sqlReturnType);
            st.registerOutParameter(bindVars.length + 1, sqlReturnType);
            System.out.println("before execute:::"+st);
            st.executeUpdate();
            System.out.println("Hello::::::");
            //System.out.println("__________________________________________end");
            return st.getObject(bindVars.length + 1);            
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

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                st = getAm().getDBTransaction().createCallableStatement("{ ? = " + stmt + "};", 0);   
            }
            else
            {
            st = getAm().getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            }
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();
            return st.getObject(1);
        } catch (SQLException e) {
            int end = e.getMessage().indexOf("\n");

            throw new JboException(e.getMessage());
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

    /**
     * Method to get the default user cloud id to be used in called pages.
     * **/
    public String getUsrCldId() {
        return this._usrCldId;
    }

    /**
     * Method to get user cloud count.
     * Cloud count can be greater than 1 for supervisor.
     * If Cloud count >1 then enable LOV for cloud selection.
     * **/

    public Integer getUsrCloudCount() {
        Integer _usrCldCnt = 0;
        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                _usrCldCnt =
                        Integer.parseInt(callStoredFunction(NUMERIC, "call fn_get_usr_to_cld_count(?,?)", new Object[] { getSloc_id(),
                                                                                                                        getUser_id() }).toString());   
            }
            else
            {
            _usrCldCnt =
                    Integer.parseInt(callStoredFunction(NUMERIC, "APP.fn_get_usr_to_cld_count(?,?)", new Object[] { getSloc_id(),
                                                                                                                    getUser_id() }).toString());
            }
            //    System.out.println("parameter values-->" + " _usrCldId  :" + _usrCldCnt);


        } catch (Exception e) {

            System.out.println("Error @ EbizMainApplication-->LoginBean-->getGlobalParameter()-->APP.fn_get_usr_to_cld_count(?) ");
            showError("Unable to get user Cloud Count. Contact ESS!");
            e.printStackTrace();
        }

        return _usrCldCnt;
    }

    public String getOrg_id() {
        String org_id = "01";
        if (session.getAttribute("OrganisationId") != null)
            return session.getAttribute("OrganisationId").toString();
        return org_id;
    }

    public Integer getSloc_id() {
        Integer sloc_id = 1;
        if (session.getAttribute("sloc_Id") != null)
            return Integer.parseInt(session.getAttribute("sloc_Id").toString());
        else
            return sloc_id;
    }

    public void setHo_org(String ho_org) {
        session.setAttribute("ho_org", ho_org);
        //System.out.println("ho_org in sssion------->"+session.getAttribute("ho_org"));
        this.ho_org = ho_org;
    }

    public void setDate_format(String date_format) {

        //  System.out.println("date_format in sssion------->"+date_format);
        session.setAttribute("date_format", date_format);
        this.date_format = date_format;
    }

    public String getDate_format() {
        return date_format;
    }

    public void setNum_format(String num_format) {
        this.num_format = num_format;
    }

    public String getNum_format() {
        return num_format;
    }

    public void setLang_path(String lang_path) {
        session.setAttribute("lang_path", lang_path);
        // System.out.println("lang_path in sssion-------->"+session.getAttribute("lang_path"));
        this.lang_path = lang_path;
    }

    public String getLang_path() {
        return lang_path;
    }

    public void setLang_file(String lang_file) {
        session.setAttribute("lang_file", lang_file);
        //System.out.println("lang_file in sssion-------->"+session.getAttribute("lang_file"));
        this.lang_file = lang_file;
    }

    public String getLang_file() {
        return lang_file;
    }

    public void setAmt_digit(Integer amt_digit) {

        //   System.out.println("amount_digit in sssion-------->"+amt_digit);
        session.setAttribute("amount_digit", amt_digit);
        this.amt_digit = amt_digit;
    }

    public Integer getAmt_digit() {
        return amt_digit;
    }

    public void setCurr_digit(Integer curr_digit) {

        //   System.out.println(session+"--currency_digit in sssion-------->"+curr_digit);
        session.setAttribute("currency_digit", curr_digit);
        this.curr_digit = curr_digit;
    }

    public Integer getCurr_digit() {
        return curr_digit;
    }

    public void setRate_digit(Integer rate_digit) {

        //   System.out.println("rate_digit in sssion------->"+rate_digit);
        session.setAttribute("rate_digit", rate_digit);
        this.rate_digit = rate_digit;
    }

    public Integer getRate_digit() {
        return rate_digit;
    }

    public void setQty_digit(Integer qty_digit) {
        //   System.out.println("qty_digit in sssion------->"+qty_digit);
        session.setAttribute("qty_digit", qty_digit);
        this.qty_digit = qty_digit;
    }

    public Integer getQty_digit() {
        return qty_digit;
    }

    public void setLang_id(Integer lang_id) {

        session.setAttribute("lang_id", lang_id);
        //System.out.println("lang_id in sssion-------->" + session.getAttribute("lang_id"));
        this.lang_id = lang_id;
    }

    public Integer getLang_id() {
        if (lang_id != null)
            return lang_id;
        else
            return 1;
    }

    public Integer getUser_id() {
        user_id = Integer.parseInt(ADFContext.getCurrent().getSecurityContext().getUserName());
        return user_id;
    }

    public void getParentOrg() {

        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                ho_org =
                        (String)callStoredFunction(STRING, "call FN_GET_PARENT_ORG_L0(?,?)", new Object[] { getUsrCldId(), getOrg_id() });   
            }
            else
            {
            ho_org =
                    (String)callStoredFunction(STRING, "APP.FN_GET_PARENT_ORG_L0(?,?)", new Object[] { getUsrCldId(), getOrg_id() });
            }
            // System.out.println("ho_orgvfrom funtion------>" + ho_org);

            setHo_org(ho_org);

        } catch (Exception e) {

            setHo_org("01");
            System.out.println("Error @ EbizMainApplication-->DashBoardBean-->getGlobalParameter()-->APP.FN_GET_PARENT_ORG_L0(?,?) ");
            System.out.println("parameter values-->" + " cloud id :" + getUsrCldId() + " org id " + getOrg_id());
            e.printStackTrace();
        }
    }

    public EbizMainAppAMImpl getAm() {
        //System.out.println("EbizMainAppAMImpl_dashBoard");
        return (EbizMainAppAMImpl)resolvElDC("");
    }

    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data.EbizMainAppAMDataControl.dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    public String Sales() {


        String orgId = orgBindVar.getValue().toString();
        // System.out.println(orgId);


        if (resolvEl("#{bindings.Organisation.selectedValue.attributeValues[1]}") != null)
            orgNm = resolvEl("#{bindings.Organisation.selectedValue.attributeValues[1]}").toString();
        // System.out.println("organization nm---->"+orgNm);

        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
        session.setAttribute("OrganisationId", orgId);
        session.setAttribute("OrganisationNm", orgNm);


        getParentOrg();

        String profile_Freez = "N";
        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                profile_Freez =
                        (callProcedureOut(STRING, "call fn_sls_chk_org_prf(?,?,?,?,?)", new Object[] { getUsrCldId(),
                                                                                                      getSloc_id(), orgId,
                                                                                                      "FREEZE_PRF" }).toString());  
                profile_Freez="Y";
                System.out.println("PROFILER FREEZE---"+profile_Freez);
            }
            else
            {
            profile_Freez =
                    (callStoredFunction(STRING, "SLS.fn_sls_chk_org_prf(?,?,?,?)", new Object[] { getUsrCldId(),
                                                                                                  getSloc_id(), orgId,
                                                                                                  "FREEZE_PRF" }).toString());
            }

        } catch (Exception e) {
            adflog.info("Error @ EbizMainApplication-->DashBoardBean-->getGlobalParameter()-->SLS.fn_sls_chk_org_prf(?,?,?,?) ");
            adflog.info("parameter values-->" + " sloc id :" + getSloc_id() + ": getCloud_id  : " + getUsrCldId() +
                        " : orgId : " + orgId);
            //setLang_path("D:\\Resource\\");
            e.printStackTrace();

        }
        adflog.info("Error @ EbizMainApplication-->DashBoardBean--> profile_Freez value : " + profile_Freez);
        if ("Y".equalsIgnoreCase(profile_Freez)) {
            getSalesProfileSetup();

            return "Sales";
        } else {
            FacesMessage msg = new FacesMessage("Sales Profile not Create for " + orgNm);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(orgBindVar.getClientId(), msg);
          //  return null;
          return "Sales";
        }
    }


    public void getSalesProfileSetup() {
        String orgId = orgBindVar.getValue().toString();
        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                wh_chk =
                        (callProcedureOut(STRING, "call mm_chk_org_prf(?,?,?,?,?)", new Object[] { getUsrCldId(), getSloc_id(),
                                                                                                      orgId,
                                                                                                      "USE_WH" }).toString());   
                System.out.println(" wh_chk --"+wh_chk);
  }
            else
            {
            wh_chk =
                    (callStoredFunction(STRING, "MM.mm_chk_org_prf(?,?,?,?)", new Object[] { getUsrCldId(), getSloc_id(),
                                                                                                  orgId,
                                                                                                  "USE_WH" }).toString());
            }
            //System.out.println("date format from funtion------>" + date_format);
            adflog.info("wh Used checked ------> " + wh_chk);
            setWh_chk(wh_chk);

        } catch (Exception e) {

            setWh_chk("Y");
            adflog.info("Error @ EbizMainApplication-->DashBoardBean-->getMMProfileSetup()-->SLS.fn_sls_chk_org_prf(?,?,?,?) ");
            adflog.info("parameter values-->" + " sloc id :" + getSloc_id() + ": getCloud_id  : " + getUsrCldId() +
                        " : orgId : " + orgId + "-USE_WH- ");
            e.printStackTrace();
        }
        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                bin_chk =
                        (callProcedureOut(STRING, "call mm_chk_org_prf(?,?,?,?,?)", new Object[] { getUsrCldId(), getSloc_id(),
                                                                                                      orgId,
                                                                                                      "USE_BIN" }).toString());   
         System.out.println("bin_chk ---"+bin_chk);  
         }
            else
            {
            bin_chk =
                    (callStoredFunction(STRING, "MM.mm_chk_org_prf(?,?,?,?)", new Object[] { getUsrCldId(), getSloc_id(),
                                                                                                  orgId,
                                                                                                  "USE_BIN" }).toString());
            }
            //System.out.println("date format from funtion------>" + date_format);
            adflog.info("Bin Used checked ------> " + bin_chk);
            setBin_chk(bin_chk);

        } catch (Exception e) {

            setBin_chk("Y");
            adflog.info("Error @ EbizMainApplication-->DashBoardBean-->getMMProfileSetup()-->SLS.fn_sls_chk_org_prf(?,?,?,?) ");
            adflog.info("parameter values-->" + " sloc id :" + getSloc_id() + ": getCloud_id  : " + getUsrCldId() +
                        " : orgId : " + orgId + "-USE_BIN  ");
            e.printStackTrace();
        }
        try {
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                ge_chk =
                        (callProcedureOut(STRING, "call fn_sls_chk_org_prf(?,?,?,?,?)", new Object[] { getUsrCldId(), getSloc_id(),
                                                                                                      orgId,
                                                                                                      "USE_GATE_ENTRY" }).toString());   
            }
            else
            {
            ge_chk =
                    (callStoredFunction(STRING, "SLS.fn_sls_chk_org_prf(?,?,?,?)", new Object[] { getUsrCldId(), getSloc_id(),
                                                                                                  orgId,
                                                                                                  "USE_GATE_ENTRY" }).toString());
            }
            //System.out.println("date format from funtion------>" + date_format);
            adflog.info("GE Used checked ------> " + ge_chk);
            setGe_chk(ge_chk);

        } catch (Exception e) {

            setGe_chk("Y");
            adflog.info("Error @ EbizMainApplication-->DashBoardBean-->getMMProfileSetup()-->SLS.fn_sls_chk_org_prf(?,?,?,?) ");
            adflog.info("parameter values-->" + " sloc id :" + getSloc_id() + ": getCloud_id  : " + getUsrCldId() +
                        " : orgId : " + orgId + "-USE_GATE_ENTRY  ");
            e.printStackTrace();
        }

    }

    public void setWh_chk(String wh_chk) {
        session.setAttribute("wh_check", wh_chk);
        this.wh_chk = wh_chk;
    }

    public String getWh_chk() {
        return wh_chk;
    }

    public void setBin_chk(String bin_chk) {
        session.setAttribute("bin_check", bin_chk);
        this.bin_chk = bin_chk;
    }

    public String getBin_chk() {
        return bin_chk;
    }


    public void setGe_chk(String ge_chk) {
        session.setAttribute("ge_check", ge_chk);
        this.ge_chk = ge_chk;
    }

    public String getGe_chk() {
        return ge_chk;
    }

    /**
     * Created by Priyank on 22-01-2014.
     * This method is used to set the map values for module id which should remain enable for
     * the user.
     * **/
    private void applyModuleSecurity(String p_org_id) {


        //1.get the UsrAppliStructVO data
        ViewObjectImpl usrAppliStructVO = getAm().getUsrAppliStructVO();
        //System.out.println("row in appSecMenu :"+appSecUsrMnuVO.getRowCount());
        //System.out.println("cloud id :"+getCloud_id());
        //System.out.println("sloc id  :"+getSloc_id());
        //System.out.println("org  id  :"+p_org_id);
        //System.out.println("user id  :"+getUserId());

        //2. Set the required parameters
        usrAppliStructVO.setNamedWhereClauseParam("BindCldId", getUsrCldId());
        usrAppliStructVO.setNamedWhereClauseParam("BindSlcId", getSloc_id());
        usrAppliStructVO.setNamedWhereClauseParam("BindOrgId", p_org_id);
        usrAppliStructVO.setNamedWhereClauseParam("BindUsrId", getUser_id());

        //3.Set the map values
        RowSetIterator appSecUsrMnuRIt = usrAppliStructVO.createRowSetIterator(null);

        //System.out.println("row in menu row iterator :" + appSecUsrMnuRIt.getRowCount());

        if (appSecUsrMnuRIt.first() != null) {
            Row appSecUsrMnuRow = appSecUsrMnuRIt.getCurrentRow();
            if (appSecUsrMnuRow.getAttribute("AppliStructId") != null) {
                menuVisibleMap.put((appSecUsrMnuRow.getAttribute("AppliStructId").toString()), "Y");
                //System.out.println("inside setMenuParam() :" + appSecUsrMnuRow.getAttribute("AppliStructId"));
            }
        }
        while (appSecUsrMnuRIt.hasNext()) {
            appSecUsrMnuRIt.next();
            Row appSecUsrMnuRow = appSecUsrMnuRIt.getCurrentRow();
            if (appSecUsrMnuRow.getAttribute("AppliStructId") != null) {
                menuVisibleMap.put(appSecUsrMnuRow.getAttribute("AppliStructId").toString(), "Y");
                //System.out.println("inside setMenuParam() :" + appSecUsrMnuRow.getAttribute("AppliStructId"));
            }
        }
    }

    /**
     * Created by Priyank on 22-01-2014.
     * This method is used in the EL expression to get the parameter value which set module link
     * disable property true or false. It takes in one parameter from the EL which will be used as search
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
                    //System.out.println("inside getVisible() c :" + c + " retVal :" + retVal);

                    return retVal;
                } else
                    return "N";
            }
        };
    }

    public void orgChangeVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            applyModuleSecurity(valueChangeEvent.getNewValue().toString());
        } else {
            FacesMessage msg = new FacesMessage("Unable to get Organization");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }
    }

    public Map<String, Object> getMenuVisibleMap() {
        return menuVisibleMap;
    }

    /**
     * Set the Value of Bind Variables in organization vo.
     * **/
    public void setOrganizationVOParameter(Integer usr_id, Integer sloc_id) {

      //  System.out.println("usr_id " + usr_id + " sloc_id " + sloc_id);
        ViewObject vo = getAm().getDualTableForLOV1();
        vo.setNamedWhereClauseParam("BindSlcId", sloc_id);
        vo.setNamedWhereClauseParam("BindUsrId", usr_id);
        vo.executeQuery();

    }


    public void setCloudVisible(boolean cloudVisible) {
        this.cloudVisible = cloudVisible;
    }

    public boolean isCloudVisible() {
        return cloudVisible;
    }

    /**
     * Generic method to show errors
     * **/
    public void showError(String msg) {

        FacesMessage errMsg = new FacesMessage(msg);
        errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
        context.getCurrentInstance().addMessage(null, errMsg);

    }

    /**
     * Created on 05/02/2014 by Priyank Khare
     * Method to set the cloud Id of user.
     * **/

    public void setUsrCldId(String _usrCldId) {
        if (_usrCldId == null) {
            try {
                System.out.println("getUser_id()::::"+getUser_id());
                String driver=getdrivername();
                if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                    this._usrCldId =
                            callStoredFunction(STRING, "call fn_get_usr_cld(?,?)", new Object[] { getSloc_id(), getUser_id() }).toString();
   
                }
                else
                {
                this._usrCldId =
                        callStoredFunction(STRING, "APP.fn_get_usr_cld(?,?)", new Object[] { getSloc_id(), getUser_id() }).toString();
                }
              //  System.out.println("parameter values-->" + " getSloc_id " + getSloc_id() + " getUser_id " +
                //                   getUser_id() + " _usrCldId  :" + getUsrCldId());

                //  ViewObjectImpl vo = (ViewObjectImpl)getAm().getDualTableForLOV1();
                //Row r = vo.getCurrentRow();
                //r.setAttribute("UsrCldId", getUsrCldId());

            } catch (Exception e) {

                System.out.println("Error @ EbizMainApplication-->DashBoardBean-->getGlobalParameter()-->APP.fn_get_usr_cld(?) ");
                showError("Unable to get User Cloud. Contact Ess!");
                e.printStackTrace();
            }
        } else {

            this._usrCldId = _usrCldId;
        }
    }

    public void setDisc_digit(Integer disc_digit) {
        session.setAttribute("disc_digit", disc_digit);
        this.disc_digit = disc_digit;
    }

    public Integer getDisc_digit() {
        return disc_digit;
    }
}
