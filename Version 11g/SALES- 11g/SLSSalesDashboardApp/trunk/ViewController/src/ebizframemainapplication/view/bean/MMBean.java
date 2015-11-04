package ebizframemainapplication.view.bean;

import ebizframemainapplication.model.EbizMainAppAMImpl;
import ebizframemainapplication.model.views.AppSecUsrMnuVOImpl;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
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

public class MMBean {
   
    private Map<String, Object> parameterMap = new HashMap<String, Object>();
    private Map<String, Object> menuVisibleMap = new HashMap<String, Object>();
    private String OrganisationId;
    String amDef = "ebizframemainapplication.model.EbizMainAppAMImpl";
    String config = "EbizMainAppAMLocal";
    FacesContext context = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
    private static ADFLogger adflog = ADFLogger.createADFLogger(MMBean.class);
    private static int STRING = Types.VARCHAR;
    private static int NUMERIC = Types.NUMERIC;
    private String wh_chk;
    private String bin_chk;
    private String ge_chk;
    private String taskFlowId = "/WEB-INF/MMDashboardTF.xml#MMDashboardTF";
    private String orgPrfQc;
    private String orgPrfGE;

    public MMBean() {
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
        parameterMap.put("PARAM_PG_ADD_MD", "Y");
        parameterMap.put("PARAM_PG_EDIT_MD", "Y");
        parameterMap.put("PARAM_PG_VIEW_MD", "Y");
        parameterMap.put("PARAM_PG_DEL_MD", "Y");
        parameterMap.put("PARAM_PG_CALLED", "M");
        parameterMap.put("GLBL_ORG_WH_ID", "Y");
        parameterMap.put("GLBL_ORG_WH_CHK", getWh_used());
        parameterMap.put("GLBL_ORG_BIN_CHK", getBin_Used());
        parameterMap.put("PARAM_USR_WH_ID", "WH00001");
        parameterMap.put("GLBL_APP_STK_TAKE","I");
        parameterMap.put("GLBL_ORG_GE_CHK", getGE_Used());
        parameterMap.put("GLBL_DISC_DIGIT", getDisc_digit());
        //to render the the MM menu according to the defined security.
        applyMenuSecurity();
        try{
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                orgPrfQc=(String) this.callStoredFunction(Types.VARCHAR, "call mm_chk_org_prf(?,?,?,?)", new Object[]{
                                                                                                      this.getCloud_id(),
                                                                                                      this.getSloc_id(),
                                                                                                      this.getOrganisationId(),
                                                                                                      "USE_QC"
                                                                                                      });   
            }
            else
            {
                orgPrfQc=(String) this.callStoredFunction(Types.VARCHAR, "mm.mm_chk_org_prf(?,?,?,?)", new Object[]{
                                                                                                      this.getCloud_id(),
                                                                                                      this.getSloc_id(),
                                                                                                      this.getOrganisationId(),
                                                                                                      "USE_QC"
                                                                                                      });
            }
           
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                orgPrfGE=(String) this.callStoredFunction(Types.VARCHAR, "call mm_chk_org_prf(?,?,?,?)", new Object[]{
                                                                                                      this.getCloud_id(),
                                                                                                      this.getSloc_id(),
                                                                                                      this.getOrganisationId(),
                                                                                                      "USE_GATE_ENTRY"
                                                                                                      });     
            }
            else
            {
                orgPrfGE=(String) this.callStoredFunction(Types.VARCHAR, "mm.mm_chk_org_prf(?,?,?,?)", new Object[]{
                                                                                                      this.getCloud_id(),
                                                                                                      this.getSloc_id(),
                                                                                                      this.getOrganisationId(),
                                                                                                      "USE_GATE_ENTRY"
                                                                                                      });  
            }
              
            System.out.println("qc "+this.getOrgPrfQc() +"GE "+this.getOrgPrfQc());
        }catch(Exception e){
            e.printStackTrace();
        }
      
        //to render the module according to the defined security.
        applyModuleSecurity();
    }
   

    public String mMSuppProfileAddTF() {
        taskFlowId = "/WEB-INF/MMSuppProfileAddTF.xml#MMSuppProfileAddTF";
        return null;
    }

    public void setParameterMap(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }


    public String perfEvalParamTF() {
        taskFlowId = "/WEB-INF/PerfEvalParamTF.xml#PerfEvalParamTF";
        return null;
    }

    public String mmRfqTF() {
        taskFlowId = "/WEB-INF/mmRfqTF.xml#mmRfqTF";
        return null;
    }

    public String quotRsltTF() {
        taskFlowId = "/WEB-INF/QuotRsltTF.xml#QuotRsltTF";
        return null;
    }


    public String eoLocTF() {
        taskFlowId = "/WEB-INF/EoLocTF.xml#EoLocTF";
        return null;
    }

    public String quotationResultTF() {
        taskFlowId = "/WEB-INF/quotationResultTF.xml#quotationResultTF";
        return null;
    }

    public String mMDiscountSearchTF() {
        taskFlowId = "/WEB-INF/MMDiscountSearchTF.xml#MMDiscountSearchTF";
        return null;
    }

    public String suggOrderViewTF() {
        taskFlowId = "/WEB-INF/suggOrderViewTF.xml#suggOrderViewTF";
        return null;
    }

    public String mmCpoSearchTF() {
        taskFlowId = "/WEB-INF/MmCpoSearchTF.xml#MmCpoSearchTF";
        return null;
    }

    public String mMQuotSearchTF() {
        taskFlowId = "/WEB-INF/MMQuotSearchTF.xml#MMQuotSearchTF";
        return null;
    }

    public String mmPurOrderSearchTF() {
        taskFlowId = "/WEB-INF/MmPurOrderSearchTF.xml#MmPurOrderSearchTF";
        return null;
    }

    public String myMailTF() {
        taskFlowId = "/WEB-INF/myMailTF.xml#myMailTF";
        return null;
    }

    public String mmSupplierPerfEvalTF() {
        taskFlowId = "/WEB-INF/MmSupplierPerfEvalTF.xml#MmSupplierPerfEvalTF";
        return null;
    }

    public String vendorpricelevelTF() {
        taskFlowId = "/WEB-INF/VendorpricelevelTF.xml#VendorpricelevelTF";
        return null;
    }

    public String procurementTF() {
        taskFlowId = "/WEB-INF/ProcurementTF.xml#ProcurementTF";
        return null;
    }

    public String itemReportTF() {
        taskFlowId = "/WEB-INF/ItemReportTF.xml#ItemReportTF";
        return null;
    }

    public String qcSetupTF() {
        taskFlowId = "/WEB-INF/QcSetupTF.xml#QcSetupTF";
        return null;
    }

   /*  public String qcProcessTF() {
        taskFlowId = "/WEB-INF/QcProcessTF.xml#QcProcessTF";
        return null;
    } */

    public String searchQcTF() {
        taskFlowId = "/WEB-INF/SearchQcTF.xml#SearchQcTF";
        return null;
    }

    public String mrsSearchTF() {
        taskFlowId = "/WEB-INF/MrsSearchTF.xml#MrsSearchTF";
        return null;
    }

    public String srchPurReqTF() {
        taskFlowId = "/WEB-INF/srchPurReqTF.xml#srchPurReqTF";
        return null;
    }

    public String gateEntrySearchTF() {
        taskFlowId = "/WEB-INF/GateEntrySearchTF.xml#GateEntrySearchTF";
        return null;
    }

    public String mMProfileSetupTF() {
        taskFlowId = "/WEB-INF/MMProfileSetupTF.xml#MMProfileSetupTF";
        return null;
    }

    public String searchMaterialIssueTF() {
        taskFlowId = "/WEB-INF/SearchMaterialIssueTF.xml#SearchMaterialIssueTF";
        return null;
    }

    public String receiptMtlSearchTF() {
        taskFlowId = "/WEB-INF/ReceiptMtlSearchTF.xml#ReceiptMtlSearchTF";
        return null;
    }
    

    public String stockProfileSetupTF() {
        taskFlowId = "/WEB-INF/StockProfileSetupTF.xml#StockProfileSetupTF";
        return null;
    }

    public String searchMMTrfOrdTF() {
        taskFlowId = "/WEB-INF/SearchMMTrfOrdTF.xml#SearchMMTrfOrdTF";
        return null;
    }

    public String srchMMStkAdjustmentTF() {
        taskFlowId = "/WEB-INF/SrchMMStkAdjustmentTF.xml#SrchMMStkAdjustmentTF";
        return null;
    }

    public String mMMtlMisTF() {
        taskFlowId = "/WEB-INF/MMMtlMisTF.xml#MMMtlMisTF";
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
                // System.out.println("inside setMenuParam() :" + appSecUsrMnuRow.getAttribute("UsrMnuId"));
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
                    //System.out.println("inside getVisible() c :" + c + " retVal :" + retVal);

                    return retVal;
                } else
                    return "N";
            }
        };
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


    public String getOrganisationId() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
        String retval = "01";
        if (session.getAttribute("OrganisationId") != null) {
            retval = session.getAttribute("OrganisationId").toString();
        }
        return retval;
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


    public String srchMMrmdaTF() {
        taskFlowId = "/WEB-INF/SrchMMrmdaTF.xml#SrchMMrmdaTF";
        return null;
    }

    public String stockTakingSearchTF() {
        taskFlowId = "/WEB-INF/StockTakingSearchTF.xml#StockTakingSearchTF";
        return null;
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

    public String mMKitProductionSearchTF() {
        taskFlowId = "/WEB-INF/MMKitProductionSearchTF.xml#MMKitProductionSearchTF";
        return null;
    }

    public String mMAppRequirementTF() {
        taskFlowId = "/WEB-INF/MMAppRequirement.xml#MMAppRequirementTF";
        return null;
    }

    public String searchMRNoteTF() {
        taskFlowId = "/WEB-INF/SearchMRNoteTF.xml#SearchMRNoteTF";
        return null;
    }

    public String searchPurchaseReturnTF() {
        taskFlowId = "/WEB-INF/searchPurchaseReturnTF.xml#searchPurchaseReturnTF";
        return null;
    }

    public String searchGatePassTF() {
        taskFlowId = "/WEB-INF/SearchGatePassTF.xml#SearchGatePassTF";
        return null;
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
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                profile_Freez =
                        (callProcedureOut(STRING, "call fn_sls_chk_org_prf(?,?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "FREEZE_PRF" }).toString());   
            }
            else
            {
            profile_Freez =
                    (callStoredFunction(STRING, "SLS.fn_sls_chk_org_prf(?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "FREEZE_PRF" }).toString());
            }

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
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                wh_chk =
                (callProcedureOut(STRING, "call fn_sls_chk_org_prf(?,?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "USE_WH" }).toString());   
            }
            else
            {
            wh_chk =
            (callStoredFunction(STRING, "SLS.fn_sls_chk_org_prf(?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "USE_WH" }).toString());
            }
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
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                bin_chk =
                (callProcedureOut(STRING, "call fn_sls_chk_org_prf(?,?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "USE_BIN" }).toString());   
            }
            else
            {
            bin_chk =
            (callStoredFunction(STRING, "SLS.fn_sls_chk_org_prf(?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "USE_BIN" }).toString());
            }
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
            String driver=getdrivername();
            if(driver.equalsIgnoreCase("MySQL-AB JDBC Driver")){
                ge_chk =
                (callProcedureOut(STRING, "call fn_sls_chk_org_prf(?,?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "USE_GATE_ENTRY" }).toString());   
            }
            else
            {
            ge_chk =
            (callStoredFunction(STRING, "SLS.fn_sls_chk_org_prf(?,?,?,?)", new Object[] {getCloud_id(), getSloc_id() ,orgId, "USE_GATE_ENTRY" }).toString());
            }
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

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public String invoiceSearchTF() {
        taskFlowId = "/WEB-INF/InvoiceSearchTF.xml#InvoiceSearchTF";
        return null;
    }

    public String mMDashboardTF() {
        taskFlowId = "/WEB-INF/MMDashboardTF.xml#MMDashboardTF";
        return null;
    }

    public String mMRfqSearchTF() {
        taskFlowId = "/WEB-INF/MMRfqSearchTF.xml#MMRfqSearchTF";
        return null;
    }

    public String inventoryReportTF() {
        taskFlowId = "/WEB-INF/InventoryReportTF.xml#InventoryReportTF";
        return null;
    }

    public String tickerTF() {
        taskFlowId = "/WEB-INF/TickerTF.xml#TickerTF";
        return null;
    }

    public String appItemAttTF() {
        taskFlowId = "/WEB-INF/AppItemAttTF.xml#AppItemAttTF";
        return null;
    }

    public void setOrgPrfQc(String orgPrfQc) {
        this.orgPrfQc = orgPrfQc;
    }

    public String getOrgPrfQc() {
        return orgPrfQc;
    }

    public void setOrgPrfGE(String orgPrfGE) {
        this.orgPrfGE = orgPrfGE;
    }

    public String getOrgPrfGE() {
        return orgPrfGE;
    }

    public String searchScrpSlsTF() {
        taskFlowId = "/WEB-INF/SearchScrpSlsTF.xml#SearchScrpSlsTF";
        return null;
    }
}
