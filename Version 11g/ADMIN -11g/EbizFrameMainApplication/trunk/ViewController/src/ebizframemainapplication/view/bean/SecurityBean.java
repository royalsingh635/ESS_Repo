package ebizframemainapplication.view.bean;

import ebizframemainapplication.model.EbizMainAppAMImpl;
import ebizframemainapplication.model.views.AppSecUsrMnuVOImpl;

import java.io.Serializable;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.share.ADFContext;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.server.ViewObjectImpl;

public class SecurityBean {
    
    private String taskFlowId = "/WEB-INF/defaultRegionTF.xml#defaultRegionTF";
    private Map<String, Object> parameterMap = new HashMap<String, Object>();
    private Map<String, Object> menuVisibleMap = new HashMap<String, Object>();
    FacesContext context = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
    private static int STRING = Types.VARCHAR;
    private static int NUMERIC = Types.NUMERIC;
    private String wh_chk;
    private String bin_chk;
    private String ge_chk;
    private static ADFLogger adflog = ADFLogger.createADFLogger(SecurityBean.class);

    public SecurityBean() {
        
        parameterMap.put("GLBL_APP_USR", getUserId());
       // System.out.println("GLBL_APP_USR: "+parameterMap.get("GLBL_APP_USR"));
        parameterMap.put("GLBL_APP_USR_ROLE", 1);
        parameterMap.put("PARAM_TF_CALLED", "N");
        parameterMap.put("GLBL_APP_USR_ORG", getOrganisationId());
        parameterMap.put("GLBL_APP_VER", "1");
        parameterMap.put("GLBL_APP_REG", 1);
        parameterMap.put("GLBL_APP_DB_VER", 1);
        parameterMap.put("GLBL_APP_SESSID", "1");
        parameterMap.put("GLBL_APP_SERV_LOC", getSloc_id());
        //System.out.println("sloc :"+parameterMap.get("GLBL_APP_SERV_LOC"));
        parameterMap.put("GLBL_APP_CLD_ID", getCloud_id());
        //System.out.println(" cloud :"+parameterMap.get("GLBL_APP_CLD_ID"));
        parameterMap.put("GLBL_APP_INST_ID", 1);
        parameterMap.put("GLBL_HO_ORG_ID", getHo_Id());
        //System.out.println(" ho org :"+parameterMap.get("GLBL_HO_ORG_ID"));
        parameterMap.put("GLBL_DT_FORMAT", getDate_format());
        parameterMap.put("GLBL_NUM_FORMAT", "");
        parameterMap.put("GLBL_AMT_DIGIT", getAmt_digit());
        parameterMap.put("GLBL_RATE_DIGIT", getRate_digit());
        parameterMap.put("GLBL_CURR_DIGIT", getCurr_digit());
        parameterMap.put("GLBL_QTY_DIGIT", getQty_digit());
        parameterMap.put("PARAM_PG_ADD_MD", "Y");
        parameterMap.put("PARAM_PG_EDIT_MD", "Y");
        parameterMap.put("PARAM_PG_VIEW_MD", "Y");
        parameterMap.put("PARAM_PG_DEL_MD", "Y");
        parameterMap.put("PARAM_PG_CALLED", "M");
           
       
        //to render the the Security module menus according to the defined security.
        applyMenuSecurity();
        
        //to render the module according to the defined security.
        applyModuleSecurity();
    }
    /**
     * Created by Priyank Khare on 13/08/2013. This method is called in the default constructor to intantiate 
     * the menuVisibleMap which will be used to set menus visible according to USER ID defined in app$sec$usr$mnu.
     * **/
    private void applyMenuSecurity() {
        
        //1.get the app$sec$usr$menu_vw data
        AppSecUsrMnuVOImpl appSecUsrMnuVO = (AppSecUsrMnuVOImpl)getAm().getAppSecUsrMnuVO();
        
        //System.out.println("row in appSecMenu :"+appSecUsrMnuVO.getRowCount());
        //System.out.println("cloud id :"+getCloud_id());
        //System.out.println("sloc id  :"+getSloc_id());
        //System.out.println("org  id  :"+getOrganisationId());
        //System.out.println("user id  :"+getUserId());
        
        //2. Set the defined view criteria
        ViewCriteria vc = appSecUsrMnuVO.getViewCriteria("AppSecUsrMnuVOCriteria");
        appSecUsrMnuVO.setNamedWhereClauseParam("BindCldId", getCloud_id());
        appSecUsrMnuVO.setNamedWhereClauseParam("BindSlocId", getSloc_id());
        appSecUsrMnuVO.setNamedWhereClauseParam("BindOrgId", getOrganisationId());
        appSecUsrMnuVO.setNamedWhereClauseParam("BindUsrId", getUserId());
        appSecUsrMnuVO.applyViewCriteria(vc);
        appSecUsrMnuVO.executeQuery();

        //3.Set the map values 
        RowSetIterator appSecUsrMnuRIt = appSecUsrMnuVO.createRowSetIterator(null);
        
        //System.out.println("row in menu row iterator :" + appSecUsrMnuRIt.getRowCount());
        
        if (appSecUsrMnuRIt.first() != null) {
            Row appSecUsrMnuRow = appSecUsrMnuRIt.getCurrentRow();
            if (appSecUsrMnuRow.getAttribute("UsrMnuId") != null) {
                menuVisibleMap.put((appSecUsrMnuRow.getAttribute("UsrMnuId").toString()), "Y");
                System.out.println("inside setMenuParam() :" + appSecUsrMnuRow.getAttribute("UsrMnuId"));
            }
        }
        while (appSecUsrMnuRIt.hasNext()) {
            appSecUsrMnuRIt.next();
            Row appSecUsrMnuRow = appSecUsrMnuRIt.getCurrentRow();
            if (appSecUsrMnuRow.getAttribute("UsrMnuId") != null){
                System.out.println("inside setMenuParam() :" + appSecUsrMnuRow.getAttribute("UsrMnuId"));
                menuVisibleMap.put(appSecUsrMnuRow.getAttribute("UsrMnuId").toString(), "Y");
            }
        }
    }
    /** 
     * Created by Priyank on 22-01-2014.
     * This method is used to set the map values for module id which should remain enable for 
     * the user.
     * **/
    private void applyModuleSecurity() {

       
        //1.get the UsrAppliStructVO data
        ViewObjectImpl usrAppliStructVO = getAm().getUsrAppliStructVO();
        //System.out.println("row in appSecMenu :"+appSecUsrMnuVO.getRowCount());
        //System.out.println("cloud id :"+getCloud_id());
        //System.out.println("sloc id  :"+getSloc_id());
        //System.out.println("org  id  :"+p_org_id);
        //System.out.println("user id  :"+getUserId());
        
        //2. Set the required parameters
        usrAppliStructVO.setNamedWhereClauseParam("BindCldId", getCloud_id());
        usrAppliStructVO.setNamedWhereClauseParam("BindSlcId", getSloc_id());
        usrAppliStructVO.setNamedWhereClauseParam("BindOrgId", getOrganisationId());
        usrAppliStructVO.setNamedWhereClauseParam("BindUsrId", getUserId());

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
            if (appSecUsrMnuRow.getAttribute("AppliStructId") != null){
                menuVisibleMap.put(appSecUsrMnuRow.getAttribute("AppliStructId").toString(), "Y");
                //System.out.println("inside setMenuParam() :" + appSecUsrMnuRow.getAttribute("AppliStructId"));
            }
        }
    }
    /** 
     * Created by Priyank on 13-08-2013.
     * This method is used as getter in the EL expression to get the parameters value to set menu 
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
                   // System.out.println("inside getVisible() c :" + c + " retVal :" + retVal);

                    return retVal;
                } else
                    return "N";
            }
        };
    }

    private Integer getUserId(){
        
        return Integer.parseInt(ADFContext.getCurrent().getSecurityContext().getUserName());
        
        }
    public String getOrganisationId() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
        String retval = "01";
        if (session.getAttribute("OrganisationId") != null) {
            retval = session.getAttribute("OrganisationId").toString();
        }
        return retval;
    }

    public String getHo_Id() {
        String ho_id = "01";
        if (session.getAttribute("ho_org") != null) {
            ho_id = session.getAttribute("ho_org").toString();
        }
        return ho_id;
    }

    public String getDate_format() {
        String date_format = "dd/MM/yyyy";
        if (session.getAttribute("date_format") != null)
            return session.getAttribute("date_format").toString();
        else
            return date_format;
    }

    public Integer getAmt_digit() {
        Integer amt_digit = 2;
        if (session.getAttribute("amount_digit") != null)
            return Integer.parseInt(session.getAttribute("amount_digit").toString());
        else
            return amt_digit;
    }

    public Integer getCurr_digit() {
        Integer curr_digit = 4;
        if (session.getAttribute("currency_digit") != null)
            return Integer.parseInt(session.getAttribute("currency_digit").toString());
        else
            return curr_digit;
    }

    public Integer getRate_digit() {
        Integer rate_digit = 4;
        if (session.getAttribute("rate_digit") != null)
            return Integer.parseInt(session.getAttribute("rate_digit").toString());
        else
            return rate_digit;
    }

    public Integer getQty_digit() {
        Integer qty_digit = 4;
        if (session.getAttribute("qty_digit") != null)
            return Integer.parseInt(session.getAttribute("qty_digit").toString());
        else
            return qty_digit;
    }

    public String getCloud_id() {
        String cloud_id = "0000";
        if (session.getAttribute("cloud_Id") != null)
            return session.getAttribute("cloud_Id").toString();
        else
            return cloud_id;
    }

    public Integer getSloc_id() {
        Integer sloc_id = 1;
        if (session.getAttribute("sloc_Id") != null)
            return Integer.parseInt(session.getAttribute("sloc_Id").toString());
        else
            return sloc_id;
    }
    public EbizMainAppAMImpl getAm() {
        //System.out.println("EbizMainAppAMImpl_fin");
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

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public String changePswdTF() {
        taskFlowId = "/WEB-INF/ChangePswdTF.xml#ChangePswdTF";
        return null;
    }

  
    public String applicationUserTF() {
        taskFlowId = "/WEB-INF/ApplicationUserTF.xml#ApplicationUserTF";
        return null;
    }

    public String userprofileTF() {
        taskFlowId = "/WEB-INF/UserprofileTF.xml#UserprofileTF";
        return null;
    }

    public String secProfileTF() {
        taskFlowId = "/WEB-INF/SecProfileTF.xml#SecProfileTF";
        return null;
    }

    public String userProfileTF() {
        taskFlowId = "/WEB-INF/userProfileTF.xml#userProfileTF";
        return null;
    }

    public void setParameterMap(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }

    public String applicationRoleTF() {
        taskFlowId = "/WEB-INF/ApplicationRoleTF.xml#ApplicationRoleTF";
        return null;
    }
    
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {

            st = getAm().getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
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
     * Created by Bharat on 13-01-2013.
     * This method is used to Check MM Profile Freezed or not for selected Organisation.
     * 
     * **/

    public String GoMaterialManagement() {
       String orgId = getOrganisationId();
       String profile_Freez="N";
       try {
           profile_Freez =
                   (callStoredFunction(STRING, "MM.mm_chk_org_prf(?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "FREEZ_PRF" }).toString());

       } catch (Exception e) {
           adflog.info("Error @ EbizMainApplication-->DashBoardBean-->getGlobalParameter()-->MM.mm_chk_org_prf(?,?,?,?) ");
           adflog.info("parameter values-->" + " sloc id :" + getSloc_id() +": getCloud_id  : "+getCloud_id()+" : orgId : "+orgId);
           //setLang_path("D:\\Resource\\");
           e.printStackTrace();
          
       }
       adflog.info("Error @ EbizMainApplication-->DashBoardBean--> profile_Freez value : "+profile_Freez);
       if("Y".equalsIgnoreCase(profile_Freez)){
           getMMProfileSetup();
       
       return "MM";
       }else{
           FacesMessage msg = new FacesMessage("MM Profile not Freez for Selected Orgnisation .");
           msg.setSeverity(FacesMessage.SEVERITY_ERROR);
           FacesContext.getCurrentInstance().addMessage(null, msg);
           return null;
       }
    }
    /**
    * Created by Bharat on 13-01-2013.
    * This method is used to set the global variable value USE_WH , USE_BIN and USE_GATE_ENTRY for selected Organisation.
    * 
    * **/
    
    public void getMMProfileSetup(){
       String orgId = getOrganisationId();
       try {
           wh_chk =
           (callStoredFunction(STRING, "MM.mm_chk_org_prf(?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "USE_WH" }).toString());
           //System.out.println("date format from funtion------>" + date_format);
           adflog.info("wh Used checked ------> "+wh_chk);
           setWh_chk(wh_chk);

       } catch (Exception e) {

           setWh_chk("Y");
           adflog.info("Error @ EbizMainApplication-->DashBoardBean-->getMMProfileSetup()-->MM.mm_chk_org_prf(?,?,?,?) ");
           adflog.info("parameter values-->" + " sloc id :" + getSloc_id() +": getCloud_id  : "+getCloud_id()+" : orgId : "+orgId+"-USE_WH- ");
           e.printStackTrace();
       }
       try {
           bin_chk =
           (callStoredFunction(STRING, "MM.mm_chk_org_prf(?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "USE_BIN" }).toString());
           //System.out.println("date format from funtion------>" + date_format);
           adflog.info("Bin Used checked ------> "+bin_chk);
           setBin_chk(bin_chk);

       } catch (Exception e) {

           setBin_chk("Y");
           adflog.info("Error @ EbizMainApplication-->DashBoardBean-->getMMProfileSetup()-->MM.mm_chk_org_prf(?,?,?,?) ");
           adflog.info("parameter values-->" + " sloc id :" + getSloc_id() +": getCloud_id  : "+getCloud_id()+" : orgId : "+orgId+ "-USE_BIN  ");
           e.printStackTrace();
       }
       try {
           ge_chk =
           (callStoredFunction(STRING, "MM.mm_chk_org_prf(?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "USE_GATE_ENTRY" }).toString());
           //System.out.println("date format from funtion------>" + date_format);
           adflog.info("GE Used checked ------> "+ge_chk);
           setGe_chk(ge_chk);

       } catch (Exception e) {

           setGe_chk("Y");
           adflog.info("Error @ EbizMainApplication-->DashBoardBean-->getMMProfileSetup()-->MM.mm_chk_org_prf(?,?,?,?) ");
           adflog.info("parameter values-->" + " sloc id :" + getSloc_id() +": getCloud_id  : "+getCloud_id()+" : orgId : "+orgId+ "-USE_GATE_ENTRY  ");
           e.printStackTrace();
       }
      
    }
    
    
    /** 
     * Created by Bharat on 13-01-2013.
     * This method is used to Check Sales Profile Freezed or not for selected Organisation.
     * 
     * **/
    
    public String goToSales() {
          String orgId = getOrganisationId();
          adflog.info(orgId);
        String profile_Freez="N";
        try {
            profile_Freez =
                    (callStoredFunction(STRING, "SLS.fn_sls_chk_org_prf(?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "FREEZE_PRF" }).toString());

        } catch (Exception e) {
            adflog.info("Error @ EbizMainApplication-->DashBoardBean-->getGlobalParameter()-->SLS.fn_sls_chk_org_prf(?,?,?,?) ");
            adflog.info("parameter values-->" + " sloc id :" + getSloc_id() +": getCloud_id  : "+getCloud_id()+" : orgId : "+orgId);
            //setLang_path("D:\\Resource\\");
            e.printStackTrace();
           
        }
        adflog.info("Error @ EbizMainApplication-->DashBoardBean--> profile_Freez value : "+profile_Freez);
        if("Y".equalsIgnoreCase(profile_Freez)){
            getSalesProfileSetup();
        
        return "Sales";
        }else{
            FacesMessage msg = new FacesMessage("Sales Profile not Freez for Selected Orgnisation .");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
    }


    /** 
     * Created by Bharat on 13-01-2013.
     * This method is used to set the global variable value USE_WH , USE_BIN and USE_GATE_ENTRY for selected Organisation.
     * 
     * **/
    public void getSalesProfileSetup(){
        String orgId = getOrganisationId();
        try {
            wh_chk =
            (callStoredFunction(STRING, "SLS.fn_sls_chk_org_prf(?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "USE_WH" }).toString());
            //System.out.println("date format from funtion------>" + date_format);
            adflog.info("wh Used checked ------> "+wh_chk);
            setWh_chk(wh_chk);

        } catch (Exception e) {

            setWh_chk("Y");
            adflog.info("Error @ EbizMainApplication-->DashBoardBean-->getMMProfileSetup()-->SLS.fn_sls_chk_org_prf(?,?,?,?) ");
            adflog.info("parameter values-->" + " sloc id :" + getSloc_id() +": getCloud_id  : "+getCloud_id()+" : orgId : "+orgId+"-USE_WH- ");
            e.printStackTrace();
        }
        try {
            bin_chk =
            (callStoredFunction(STRING, "SLS.fn_sls_chk_org_prf(?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "USE_BIN" }).toString());
            //System.out.println("date format from funtion------>" + date_format);
            adflog.info("Bin Used checked ------> "+bin_chk);
            setBin_chk(bin_chk);

        } catch (Exception e) {

            setBin_chk("Y");
            adflog.info("Error @ EbizMainApplication-->DashBoardBean-->getMMProfileSetup()-->SLS.fn_sls_chk_org_prf(?,?,?,?) ");
            adflog.info("parameter values-->" + " sloc id :" + getSloc_id() +": getCloud_id  : "+getCloud_id()+" : orgId : "+orgId+ "-USE_BIN  ");
            e.printStackTrace();
        }
        try {
            ge_chk =
            (callStoredFunction(STRING, "SLS.fn_sls_chk_org_prf(?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "USE_GATE_ENTRY" }).toString());
            //System.out.println("date format from funtion------>" + date_format);
            adflog.info("GE Used checked ------> "+ge_chk);
            setGe_chk(ge_chk);

        } catch (Exception e) {

            setGe_chk("Y");
            adflog.info("Error @ EbizMainApplication-->DashBoardBean-->getMMProfileSetup()-->SLS.fn_sls_chk_org_prf(?,?,?,?) ");
            adflog.info("parameter values-->" + " sloc id :" + getSloc_id() +": getCloud_id  : "+getCloud_id()+" : orgId : "+orgId+ "-USE_GATE_ENTRY  ");
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
}
