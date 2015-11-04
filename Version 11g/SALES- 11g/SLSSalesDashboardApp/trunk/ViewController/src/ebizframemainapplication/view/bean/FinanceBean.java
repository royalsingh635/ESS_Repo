package ebizframemainapplication.view.bean;

import ebizframemainapplication.model.EbizMainAppAMImpl;

import ebizframemainapplication.model.views.AppSecUsrMnuVOImpl;

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
import oracle.jbo.client.Configuration;
import oracle.jbo.server.DBTransaction;
import oracle.jbo.server.ViewObjectImpl;


public class FinanceBean {

    private String taskFlowId = "/WEB-INF/GraphTickerTF.xml#GraphTickerTF";
    private Map<String, Object> parameterMap = new HashMap<String, Object>();
    private Map<String, Object> menuVisibleMap = new HashMap<String, Object>();
    private String OrganisationId;
    String amDef = "ebizframemainapplication.model.EbizMainAppAMImpl";
    String config = "EbizMainAppAMLocal";
    FacesContext context = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
    private static int STRING = Types.VARCHAR;
    private static int NUMERIC = Types.NUMERIC;
    private String wh_chk;
    private String bin_chk;
    private String ge_chk;
    private static ADFLogger adflog = ADFLogger.createADFLogger(FinanceBean.class);

    public FinanceBean() {

        System.out.println("inside finance bean");
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
       
        //to render the the finance menu according to the defined security.
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
              // System.out.println("inside setMenuParam() :" + appSecUsrMnuRow.getAttribute("UsrMnuId"));
            }
        }
        while (appSecUsrMnuRIt.hasNext()) {
            appSecUsrMnuRIt.next();
            Row appSecUsrMnuRow = appSecUsrMnuRIt.getCurrentRow();
            if (appSecUsrMnuRow.getAttribute("UsrMnuId") != null){
                menuVisibleMap.put(appSecUsrMnuRow.getAttribute("UsrMnuId").toString(), "Y");
               // System.out.println("inside setMenuParam() :" + appSecUsrMnuRow.getAttribute("UsrMnuId"));
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
     * This method is used in the EL expression to get the parameter value which set menu 
     * render property true or false. It takes in one parameter from the EL which will be used as search 
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


    public void setParameterMap(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public Map<String, Object> getParameterMap() {
        Integer i = 1;
        return parameterMap;
    }

    public String appDbobTF() {

        taskFlowId = "/WEB-INF/AppDbobTF.xml#AppDbobTF";
        return null;
    }

    public String appGlblEEMstPrfTF() {

        taskFlowId = "/WEB-INF/AppGlblEEMstPrfTF.xml#AppGlblEEMstPrfTF";
        return null;
    }

    public String appGlblEEPrf() {

        taskFlowId = "/WEB-INF/AppGlblEEPrf.xml#AppGlblEEPrf";
        return null;
    }

    public String appEnt() {

        taskFlowId = "/WEB-INF/AppEnt.xml#AppEnt";
        return null;
    }

    public String bankAccountTF() {

        taskFlowId = "/WEB-INF/BankAccountTF.xml#BankAccountTF";
        return null;
    }

    public String batchPayment() {

        taskFlowId = "/WEB-INF/BatchPayment.xml#BatchPayment";
        return null;
    }

    public String cashAccountTF() {

        taskFlowId = "/WEB-INF/CashAccountTF.xml#CashAccountTF";
        return null;
    }

    public String chartOfGroupForCoa() {

        taskFlowId = "/WEB-INF/ChartOfGroupForCoa.xml#ChartOfGroupForCoa";
        return null;
    }

    public String currConversion() {

        taskFlowId = "/WEB-INF/CurrConversion.xml#CurrConversion";
        return null;
    }

    public String naturalaccountTF() {

        taskFlowId = "/WEB-INF/natural-accountTF.xml#natural-accountTF";
        return null;
    }

    public String entities() {

        taskFlowId = "/WEB-INF/Entities.xml#Entities";
        return null;
    }

    public String supplierAgeing() {

        taskFlowId = "/WEB-INF/SupplierAgeing.xml#SupplierAgeing";
        return null;
    }

    public String taxType() {

        taskFlowId = "/WEB-INF/TaxType.xml#TaxType";
        return null;
    }

    public String coaTF() {

        taskFlowId = "/WEB-INF/CoaTF.xml#CoaTF";
        return null;
    }


    public String orgUsrGrpTF() {

        taskFlowId = "/WEB-INF/OrgUsrGrpTF.xml#OrgUsrGrpTF";
        return null;
    }


    public String currencyMasterTF() {

        taskFlowId = "/WEB-INF/CurrencyMasterTF.xml#CurrencyMasterTF";
        return null;
    }


    public String templateVoucher() {

        taskFlowId = "/WEB-INF/TemplateVoucher.xml#TemplateVoucher";
        return null;
    }

    public String templateVoucherSearchTF() {

        taskFlowId = "/WEB-INF/TemplateVoucherSearchTF.xml#TemplateVoucherSearchTF";
        return null;
    }

    public String tempVocherHdrAddViewEditTF() {

        taskFlowId = "/WEB-INF/taskFlows/TempVocherHdrAddViewEditTF.xml#TempVocherHdrAddViewEditTF";
        return null;
    }

    public String tvouSearchTF() {

        taskFlowId = "/WEB-INF/TvouSearchTF.xml#TvouSearchTF";
        return null;
    }


    public String ledgerAppPage() {
        taskFlowId = "/WEB-INF/LedgerAppPage.xml#LedgerAppPage";
        return null;
    }

    public String apReportsTF() {
        taskFlowId = "/WEB-INF/ApReportsTF.xml#ApReportsTF";
        return null;
    }

    public String aRReportTF() {
        taskFlowId = "/WEB-INF/ARReportTF.xml#ARReportTF";
        return null;
    }


    public String globalCurrencyTF() {
        taskFlowId = "/WEB-INF/GlobalCurrencyTF.xml#GlobalCurrencyTF";
        return null;
    }

    public String globalDocumentSetupTF() {
        taskFlowId = "/WEB-INF/GlobalDocumentSetupTF.xml#GlobalDocumentSetupTF";
        return null;
    }

    public String glReports() {
        taskFlowId = "/WEB-INF/GlReports.xml#GlReports";
        return null;
    }


    public String graphTickerTF() {
        taskFlowId = "/WEB-INF/GraphTickerTF.xml#GraphTickerTF";
        return null;
    }


    public String glSearchTF() {
        taskFlowId = "/WEB-INF/GlSearchTF.xml#GlSearchTF";
        return null;
    }

    public String wfTF() {
        taskFlowId = "/WEB-INF/WfTF.xml#WfTF";
        return null;
    }


    public String createOrgTF() {
        taskFlowId = "/WEB-INF/CreateOrgTF.xml#CreateOrgTF";
        return null;
    }


    public String glblDocSetupTF() {
        taskFlowId = "/WEB-INF/GlblDocSetupTF.xml#GlblDocSetupTF";
        return null;
    }

    public String appDocSetupTF() {
        taskFlowId = "/WEB-INF/AppDocSetupTF.xml#AppDocSetupTF";
        return null;
    }

    public String searchOrderTF() {
        taskFlowId = "/WEB-INF/searchOrderTF.xml#searchOrderTF";
        return null;
    }

    public String searchIndentServiceTF() {
        taskFlowId = "/WEB-INF/SearchIndentServiceTF.xml#SearchIndentServiceTF";
        return null;
    }

    public String invoiceSearchTF() {
        taskFlowId = "/WEB-INF/InvoiceSearchTF.xml#InvoiceSearchTF";
        return null;
    }

    public String tmplSearchTF() {
        taskFlowId = "/WEB-INF/TmplSearchTF.xml#TmplSearchTF";
        return null;
    }


    public String createIndentServiceTF() {
        taskFlowId = "/WEB-INF/CreateIndentServiceTF.xml#CreateIndentServiceTF";
        return null;
    }

    public String invoiceTF() {
        taskFlowId = "/WEB-INF/InvoiceTF.xml#InvoiceTF";
        return null;
    }

    public String orderTF() {
        taskFlowId = "/WEB-INF/OrderTF.xml#OrderTF";
        return null;
    }

    public String opBalVe() {
        taskFlowId = "/WEB-INF/OpBalVe.xml#OpBalVe";
        return null;
    }

    public String appDsEntTypeTF() {
        taskFlowId = "/WEB-INF/AppDsEntTypeTF.xml#AppDsEntTypeTF";
        return null;
    }


    public String periodSetupTF() {
        taskFlowId = "/WEB-INF/PeriodSetupTF.xml#PeriodSetupTF";
        return null;
    }


    public String chkBookTF() {
        taskFlowId = "/WEB-INF/ChkBookTF.xml#ChkBookTF";
        return null;
    }

    public String appGlblErrMsgTF() {
        taskFlowId = "/AppGlblErrMsgTF.xml#AppGlblErrMsgTF";
        return null;
    }

    public String glblMsgTF() {
        taskFlowId = "/WEB-INF/GlblMsgTF.xml#GlblMsgTF";
        return null;
    }


    public String blRptAppTF() {
        taskFlowId = "/WEB-INF/BlRptAppTF.xml#BlRptAppTF";
        return null;
    }

    public String schedule6TF() {
        taskFlowId = "/WEB-INF/Schedule6TF.xml#Schedule6TF";
        return null;
    }

    public String transferBalances() {
        taskFlowId = "/WEB-INF/TransferBalances.xml#TransferBalances";
        return null;
    }

    public String voucherResetTF() {
        taskFlowId = "/WEB-INF/VoucherResetTF.xml#VoucherResetTF";
        return null;
    }


    public String tickerConfigTF() {
        taskFlowId = "/WEB-INF/TickerConfigTF.xml#TickerConfigTF";
        return null;
    }

    public String tickerAppTF() {
        taskFlowId = "/WEB-INF/TickerAppTF.xml#TickerAppTF";
        return null;
    }

    public String appGraphSetupTF() {
        taskFlowId = "/WEB-INF/AppGraphSetupTF.xml#AppGraphSetupTF";
        return null;
    }

    public String wfConfigTF() {
        taskFlowId = "/WEB-INF/WfConfigTF.xml#WfConfigTF";
        return null;
    }


    public String myMailTF() {
        taskFlowId = "/WEB-INF/myMailTF.xml#myMailTF";
        return null;
    }


    public String batchPayTF() {
        taskFlowId = "/WEB-INF/BatchPayTF.xml#BatchPayTF";
        return null;
    }

    public String reportConfigTF() {
        taskFlowId = "/WEB-INF/ReportConfigTF.xml#ReportConfigTF";
        return null;
    }

    public String finBudgetSearchTF() {
        taskFlowId = "/WEB-INF/FinBudgetSearchTF.xml#FinBudgetSearchTF";
        return null;
    }

    public String mISReportTF() {
        taskFlowId = "/WEB-INF/MISReportTF.xml#MISReportTF";
        return null;
    }


    
    public String openingForAllCoas() {
        taskFlowId = "/WEB-INF/OpeningForAllCoas.xml#OpeningForAllCoas";
        return null;
    }

    public String glblTickerAppTF() {
        taskFlowId = "/WEB-INF/GlblTickerAppTF.xml#GlblTickerAppTF";
        return null;
    }

   

    public String bookNVouherPrintReportTF() {
        taskFlowId = "/WEB-INF/BookNVouherPrintReportTF.xml#BookNVouherPrintReportTF";
        return null;
    }
    
    
    
    
    
    
    public void setOrganisationId(String OrganisationId) {
        this.OrganisationId = OrganisationId;
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
    public Integer getDisc_digit() {
           Integer disc_digit = 2;
           if (session.getAttribute("disc_digit") != null)
               return Integer.parseInt(session.getAttribute("disc_digit").toString());
           else
               return disc_digit;
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

    public void setMenuVisibleMap(Map<String, Object> menuVisibleMap) {
        this.menuVisibleMap = menuVisibleMap;
    }

    public Map<String, Object> getMenuVisibleMap() {
        return menuVisibleMap;
    }

    public String misReportConfigTF() {
        taskFlowId = "/WEB-INF/misReportConfigTF.xml#misReportConfigTF";
        return null;
    }
    
    public String getdrivername()
    {         String   drivernme ="";
            // MMrmdaAMImpl am = (MMrmdaAMImpl)resolvElDC("MMrmdaAMAMDataControl");
             DBTransaction dbTransaction = (DBTransaction)getAm().getTransaction();
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
                st = getAm().getDBTransaction().createCallableStatement("{? = " + stmt + "};", 0);   
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

    public String brsListingTF() {
        taskFlowId = "/WEB-INF/BrsListingTF.xml#BrsListingTF";
        return null;
    }

    public String pdcTF() {
        taskFlowId = "/WEB-INF/pdcTF.xml#pdcTF";
        return null;
    }

    public String voucherReversalAppTF() {
        taskFlowId = "/WEB-INF/VoucherReversalAppTF.xml#VoucherReversalAppTF";
        return null;
    }
}
