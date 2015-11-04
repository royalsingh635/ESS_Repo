package ebizframemainapplication.view.bean;

import ebizframemainapplication.model.EbizMainAppAMImpl;
import ebizframemainapplication.model.views.AppSecUsrMnuVOImpl;

import java.io.Serializable;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
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
import oracle.jbo.server.DBTransaction;
import oracle.jbo.server.ViewObjectImpl;

public class SalesBean  {
   
    private Map<String, Object> parameterMap = new HashMap<String, Object>();
    private Map<String, Object> menuVisibleMap = new HashMap<String, Object>();
    private String OrganisationId;
    String amDef = "ebizframemainapplication.model.EbizMainAppAMImpl";
    String config = "EbizMainAppAMLocal";
    FacesContext context = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
    private String taskFlowId = "/WEB-INF/SLSLandingTF.xml#SLSLandingTF";
    private static int STRING = Types.VARCHAR;
    private static int NUMERIC = Types.NUMERIC;
    private String wh_chk;
    private String bin_chk;
    private String ge_chk;
    private static ADFLogger adflog = ADFLogger.createADFLogger(SalesBean.class);

    public SalesBean() {
        System.out.println("hello sales");
        //to set the value of global parameters in sales pages
        parameterMap.put("GLBL_APP_USR", getUserId());
        parameterMap.put("GLBL_APP_USR_ROLE", 1);
        parameterMap.put("PARAM_TF_CALLED", "N");
        parameterMap.put("GLBL_APP_USR_ORG", getOrganisationId());
        parameterMap.put("GLBL_APP_VER", "1");
        parameterMap.put("GLBL_APP_REG", 1);
        parameterMap.put("GLBL_APP_DB_VER", 1);
        parameterMap.put("GLBL_APP_SESSID", "1");
        parameterMap.put("GLBL_APP_SERV_LOC", getSloc_id());
        parameterMap.put("GLBL_APP_CLD_ID", getCloud_id());
        parameterMap.put("GLBL_APP_INST_ID", 1);
        parameterMap.put("GLBL_HO_ORG_ID", getHo_Id());
        parameterMap.put("GLBL_DT_FORMAT", getDate_format());
        parameterMap.put("GLBL_NUM_FORMAT", "");
        parameterMap.put("GLBL_AMT_DIGIT", getAmt_digit());
        parameterMap.put("GLBL_RATE_DIGIT", getRate_digit());
        parameterMap.put("GLBL_CURR_DIGIT", getCurr_digit());
        parameterMap.put("GLBL_QTY_DIGIT", getQty_digit());
        parameterMap.put("GLBL_DISC_DIGIT", getDisc_digit());
        parameterMap.put("PARAM_PG_ADD_MD", "Y");
        parameterMap.put("PARAM_PG_EDIT_MD", "Y");
        parameterMap.put("PARAM_PG_VIEW_MD", "Y");
        parameterMap.put("PARAM_PG_DEL_MD", "Y");
        parameterMap.put("PARAM_PG_CALLED", "M");
        parameterMap.put("GLBL_ORG_WH_CHK", getWh_used());
        parameterMap.put("GLBL_ORG_BIN_CHK", getBin_Used());
        parameterMap.put("PARAM_USR_WH_ID", "WH00001");
        parameterMap.put("GLBL_ORG_GE_CHK", getGE_Used());
        
        //to render the the sales menu according to the defined security  
        applyMenuSecurity();
        
        //to render the module according to the defined security
        applyModuleSecurity();
    }


    public String sLSDlvSchdlTF() {
        taskFlowId = "/WEB-INF/SLSDlvSchdlTF.xml#SLSDlvSchdlTF";
        return null;
    }

    public String opportunityTF() {
        taskFlowId = "/WEB-INF/opportunityTF.xml#opportunityTF";
        return null;
    }

    public String pickPackShipTF() {
        taskFlowId = "/WEB-INF/pickPackShipTF.xml#pickPackShipTF";
        return null;
    }


    public String slsquotationsearchTF() {
        taskFlowId = "/WEB-INF/sls-quotation-searchTF.xml#sls-quotation-searchTF";
        return null;
    }
    

    public String salesOrdSearchTF() {
        taskFlowId = "/WEB-INF/salesOrdSearchTF.xml#salesOrdSearchTF";
        return null;
    }

    public String slsRmaAddEditTF() {
        taskFlowId = "/WEB-INF/SlsRmaAddEditTF.xml#SlsRmaAddEditTF";
        return null;
    }

    public String customerProfileTF() {
        taskFlowId = "/WEB-INF/CustomerProfileTF.xml#CustomerProfileTF";
        return null;
    }

    public String priceMasterTF() {
        taskFlowId = "/WEB-INF/PriceMasterTF.xml#PriceMasterTF";
        return null;
    }

    public String slsPackMasterTF() {
        taskFlowId = "/WEB-INF/SlsPackMasterTF.xml#SlsPackMasterTF";
        return null;
    }





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
            if (appSecUsrMnuRow.getAttribute("UsrMnuId") != null)
                menuVisibleMap.put(appSecUsrMnuRow.getAttribute("UsrMnuId").toString(), "Y");

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

    

    private Integer getUserId() {

        return Integer.parseInt(ADFContext.getCurrent().getSecurityContext().getUserName());

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
    public Integer getDisc_digit() {
        Integer disc_digit = 2;
        if (session.getAttribute("disc_digit") != null)
            return Integer.parseInt(session.getAttribute("disc_digit").toString());
        else
            return disc_digit;
    } 
    public String getHo_Id() {
        String ho_id = "01";
        if (session.getAttribute("ho_org") != null) {
            ho_id = session.getAttribute("ho_org").toString();
        }
        return ho_id;
    }

    public Integer getSloc_id() {
        Integer sloc_id = 1;
        if (session.getAttribute("sloc_Id") != null)
            return Integer.parseInt(session.getAttribute("sloc_Id").toString());
        else
            return sloc_id;
    }

    public String getDate_format() {
        String date_format = "dd/MM/yyyy";
        if (session.getAttribute("date_format") != null)
            return session.getAttribute("date_format").toString();
        else
            return date_format;
    }

    public String getCloud_id() {
        String cloud_id = "0000";
        if (session.getAttribute("cloud_Id") != null)
            return session.getAttribute("cloud_Id").toString();
        else
            return cloud_id;
    }

   

    public void setParameterMap(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }

    public void setMenuVisibleMap(Map<String, Object> menuVisibleMap) {
        this.menuVisibleMap = menuVisibleMap;
    }

    public Map<String, Object> getMenuVisibleMap() {
        return menuVisibleMap;
    }

    public void setOrganisationId(String OrganisationId) {
        this.OrganisationId = OrganisationId;
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

                    return "Y";
                } else
                    return "Y";
            }
        };
    }

    public String slsProfileSetupTF() {
        taskFlowId = "/WEB-INF/slsProfileSetupTF.xml#slsProfileSetupTF";
        return null;
    }

    public String slsRmaSearchTF() {
        taskFlowId = "/WEB-INF/SlsRmaSearchTF.xml#SlsRmaSearchTF";
        return null;
    }

    public String slsReportConfigTF() {
        taskFlowId = "/WEB-INF/SlsReportConfigTF.xml#SlsReportConfigTF";
        return null;
    }
   
    public String slsSalesInvoiceAppSearchTF() {
        taskFlowId = "/WEB-INF/SlsSalesInvoiceAppSearchTF.xml#SlsSalesInvoiceAppSearchTF";
        return null;
    }

    public String salesDashBoardTF() {
        taskFlowId = "/WEB-INF/SalesDashBoardTF.xml#SalesDashBoardTF";
        return null;
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
     * Created by Bharat on 13-01-2013.
     * This method is used to Check MM Profile Freezed or not for selected Organisation.
     * 
     * **/

    public String GoMaterialManagement() {
       String orgId = getOrganisationId();
       
       // change for MM profile setup------------------------------ BL
       String profile_Freez="N";
       try {
           String driver=getdrivername();
           if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
               profile_Freez =
                       (callStoredFunction(STRING, "call mm_chk_org_prf(?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "FREEZ_PRF" }).toString());   
           }
           else
           {
           profile_Freez =
                   (callStoredFunction(STRING, "MM.mm_chk_org_prf(?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "FREEZ_PRF" }).toString());
           }

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
           String driver=getdrivername();
           if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
               wh_chk =
               (callStoredFunction(STRING, "call mm_chk_org_prf(?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "USE_WH" }).toString());   
           }
           else
           {
           wh_chk =
           (callStoredFunction(STRING, "MM.mm_chk_org_prf(?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "USE_WH" }).toString());
           }
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
           String driver=getdrivername();
           if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
               bin_chk =
               (callStoredFunction(STRING, "call mm_chk_org_prf(?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "USE_BIN" }).toString());   
           }
           else
           {
           bin_chk =
           (callStoredFunction(STRING, "MM.mm_chk_org_prf(?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "USE_BIN" }).toString());
           }
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
           String driver=getdrivername();
           if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
               ge_chk =
               (callStoredFunction(STRING, "call mm_chk_org_prf(?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "USE_GATE_ENTRY" }).toString());   
           }
           else
           {
           ge_chk =
           (callStoredFunction(STRING, "MM.mm_chk_org_prf(?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "USE_GATE_ENTRY" }).toString());
           }
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
    
    
    public String getWh_used() {
            String wh_used = "Y";
            if (session.getAttribute("wh_check") != null){
                adflog.info("Wh ------ > "+session.getAttribute("wh_check"));
                return session.getAttribute("wh_check").toString();
            }else
                return wh_used;
        }
        public String getBin_Used() {
            String bin_used = "Y";
            if (session.getAttribute("bin_check") != null){
                adflog.info("bin ------ > "+session.getAttribute("bin_check"));
                return session.getAttribute("bin_check").toString();
            }else
                return bin_used;
        }
    public String getGE_Used() {
           String ge_used = "Y";
           if (session.getAttribute("ge_check") != null){
               adflog.info("bin ------ > "+session.getAttribute("ge_check"));
               return session.getAttribute("ge_check").toString();
           }else
               return ge_used;
       }

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public String sLSLandingTF() {
        taskFlowId = "/WEB-INF/SLSLandingTF.xml#SLSLandingTF";
        return null;
    }

    public String slsIntimationSlipSearchTF() {
        taskFlowId = "/WEB-INF/SlsIntimationSlipSearchTF.xml#SlsIntimationSlipSearchTF";
        return null;
    }

    public String sLSBICustomerDetailsTF() {
        taskFlowId = "/WEB-INF/SLSBICustomerDetailsTF.xml#SLSBICustomerDetailsTF";
        return null;
    }

    public String sLSBiMainTaskFlow() {
        taskFlowId = "/WEB-INF/SLSBiMainTaskFlow.xml#SLSBiMainTaskFlow";
        return null;
    }

    public String sLSBIOneCustomerDetailsTF() {
        taskFlowId = "/WEB-INF/SLSBIOneCustomerDetailsTF.xml#SLSBIOneCustomerDetailsTF";
        return null;
    }

    public String sLSBIOneOrganisationDetailsTF() {
        taskFlowId = "/WEB-INF/SLSBIOneOrganisationDetailsTF.xml#SLSBIOneOrganisationDetailsTF";
        return null;
    }

    public String sLSBIOneProductDetailsTF() {
        taskFlowId = "/WEB-INF/SLSBIOneProductDetailsTF.xml#SLSBIOneProductDetailsTF";
        return null;
    }

    public String sLSBIOrganisationDetailsTF() {
        taskFlowId = "/WEB-INF/SLSBIOrganisationDetailsTF.xml#SLSBIOrganisationDetailsTF";
        return null;
    }

    public String sLSBIProductDetailsTF() {
        taskFlowId = "/WEB-INF/SLSBIProductDetailsTF.xml#SLSBIProductDetailsTF";
        return null;
    }

    public String sLSBIProductGroupDetailsTF() {
        taskFlowId = "/WEB-INF/SLSBIProductGroupDetailsTF.xml#SLSBIProductGroupDetailsTF";
        return null;
    }

    public String sLSBIRegionDetailsTF() {
        taskFlowId = "/WEB-INF/SLSBIRegionDetailsTF.xml#SLSBIRegionDetailsTF";
        return null;
    }

    public String sLSBISalesExecutiveDetailsTF() {
        taskFlowId = "/WEB-INF/SLSBISalesExecutiveDetailsTF.xml#SLSBISalesExecutiveDetailsTF";
        return null;
    }

    public String slsBiTF() {
        taskFlowId = "/WEB-INF/SlsBiTF.xml#SlsBiTF";
        return null;
    }

    public String searchCallDcTF() {
        taskFlowId = "/WEB-INF/searchCallDcTF.xml#searchCallDcTF";
        return null;
    }

    public String slsSecondarySaleTF() {
        taskFlowId = "/WEB-INF/SlsSecondarySaleTF.xml#SlsSecondarySaleTF";
        return null;
    }
}
