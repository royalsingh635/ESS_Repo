package bdglandingapp.model.services;

import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import bdglandingapp.model.ds.TickerRowDS;
import bdglandingapp.model.services.common.BDGLandingAppAM;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.StringTokenizer;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.JboException;
import oracle.jbo.NameValuePairs;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.RowFinder;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.context.RequestContext;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Apr 27 12:41:32 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class BDGLandingAppAMImpl extends ApplicationModuleImpl implements BDGLandingAppAM {
    private static ADFLogger _log = ADFLogger.createADFLogger(BDGLandingAppAMImpl.class);

    /**
     * This is the default constructor (do not remove).
     */
    public BDGLandingAppAMImpl() {
    }

    /**
     * Method to fetch all the tickers
     * @return
     */
    public ArrayList<TickerRowDS> getSelectedTickerList() {
        ArrayList<TickerRowDS> selectedSettingsList = new ArrayList<TickerRowDS>();
        ViewObjectImpl mapViewVO = this.getBDGSelectedTickerListVO();
        mapViewVO.setNamedWhereClauseParam("CldIdBind", EbizParams.GLBL_APP_CLD_ID());
        mapViewVO.setNamedWhereClauseParam("SlocIdBind", EbizParams.GLBL_APP_SERV_LOC());
        mapViewVO.setNamedWhereClauseParam("OrgIdBind", EbizParams.GLBL_APP_USR_ORG());
        mapViewVO.setNamedWhereClauseParam("UsrIdBind", EbizParams.GLBL_APP_USR());
        mapViewVO.executeQuery();
        int i_2 = mapViewVO.getRangeSize();
        mapViewVO.setRangeSize(-1);
        Row[] filteredRows = mapViewVO.getAllRowsInRange();
        Integer i = 1;
        for (Row r : filteredRows) {
            Object o = ADFModelUtils.callFunction(this, new StringBuilder("BDG.FN_GET_TKR_VAL(?,?,?,?,?)"), new Object[] {
                                                  EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(),
                                                  EbizParams.GLBL_APP_USR_ORG(), this.getfyId(), r.getAttribute("TkrId")
            }, Types.VARCHAR);
            System.out.println("Returned : " + o);
            if (o != null) {
                String s = o.toString();
                StringTokenizer d = new StringTokenizer(s, "|");
                if (d.countTokens() == 3) {
                    Object tkrNm = r.getAttribute("TkrNm");
                    TickerRowDS t = new TickerRowDS();
                    t.setTickerId(r.getAttribute("TkrId").toString());
                    t.setSeqNo((Integer) r.getAttribute("SeqNo"));
                    t.setTickerDesc(tkrNm == null ? "" : tkrNm.toString());
                    t.setArrow(d.nextToken());
                    t.setCurrentAmt(d.nextToken());
                    t.setPreviousAmt(d.nextToken());
                    t.setCurr(getCurrIdBs().toString());
                    selectedSettingsList.add(t);
                }
            }
            //Gross Profit|419530808.04686|192307731861484.01752
        }
        for (TickerRowDS r : selectedSettingsList) {
            _log.info(r.getSeqNo() + " : " + r.getTickerDesc() + " : " + r.getTickerId());
        }
        mapViewVO.setRangeSize(i_2);
        return selectedSettingsList;
    }

    public StringBuffer getCurrIdBs() {
        Integer i = 0;
        StringBuffer s = new StringBuffer("");
        try {


            i = (Integer) callStoredFunction(Types.INTEGER, "APP.PKG_APP.GET_ORG_DEF_CURR_BS1(?)", new Object[] {
                                             EbizParams.GLBL_APP_USR_ORG() });

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (i != 0) {
            ViewObjectImpl currDescFromIdVO = this.getCurrDescFromIdVO();
            currDescFromIdVO.setNamedWhereClauseParam("OrgIdBind", EbizParams.GLBL_APP_USR_ORG());
            currDescFromIdVO.setNamedWhereClauseParam("CldIdBind", EbizParams.GLBL_APP_CLD_ID());
            currDescFromIdVO.setNamedWhereClauseParam("CurrIdBind", 73);
            currDescFromIdVO.executeQuery();
            Row[] allRowsInRange = currDescFromIdVO.getAllRowsInRange();
            for (Row r : allRowsInRange) {
                s = new StringBuffer(r.getAttribute("CurrNotationAlias").toString());
            }
        }
        //_log.info(" Curr ID is : "+s);
        return s;
    }

    /**
     * Method to call a Database function
     * @param sqlReturnType
     * @param stmt
     * @param bindVars
     * @return
     */
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        //_log.info("__________________________________________begin");
        try {

            st = this.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);

            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                    // _log.info(""+bindVars[z]);
                }
            }
            st.executeUpdate();
            // _log.info("__________________________________________end");
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


    /**
     * Method used to call function to get the fy id.
     * @param CldId
     * @param OrgId
     * @return FY Id
     */
    public Integer getfyId() {
        Integer fyId = 0;
        try {
            fyId = (Integer) (callStoredFunction(Types.INTEGER, "APP.GET_ORG_FY_ID (?,?,?)", new Object[] {
                                                 EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_USR_ORG(),
                                                 new Timestamp(System.currentTimeMillis())
            }));
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        return fyId;
    }

    /**
     * Method to fetch all the tickers
     * @return
     */
    public ArrayList<TickerRowDS> getTickerList() {
        ArrayList<TickerRowDS> list = new ArrayList<TickerRowDS>();
        ViewObjectImpl mapViewVO = this.getBDGUnselectedTickerListVO();
        mapViewVO.setNamedWhereClauseParam("CldIdBind", EbizParams.GLBL_APP_CLD_ID());
        mapViewVO.setNamedWhereClauseParam("SlocIdBind", EbizParams.GLBL_APP_SERV_LOC());
        mapViewVO.setNamedWhereClauseParam("OrgIdBind", EbizParams.GLBL_APP_USR_ORG());
        mapViewVO.setNamedWhereClauseParam("UsrIdBind", EbizParams.GLBL_APP_USR());
        mapViewVO.executeQuery();
        int i_2 = mapViewVO.getRangeSize();
        mapViewVO.setRangeSize(-1);
        Row[] filteredRows = mapViewVO.getAllRowsInRange();
        _log.info("Filtered rows : " + filteredRows.length);
        Integer i = 1;
        for (Row r : filteredRows) {
            Object o = ADFModelUtils.callFunction(this, new StringBuilder("BDG.FN_GET_TKR_VAL(?,?,?,?,?)"), new Object[] {
                                                  EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(),
                                                  EbizParams.GLBL_APP_USR_ORG(), this.getfyId(), r.getAttribute("TkrId")
            }, Types.VARCHAR);
            System.out.println("Returned : " + o);
            if (o != null) {
                String s = o.toString();
                StringTokenizer d = new StringTokenizer(s, "|");
                if (d.countTokens() == 3) {
                    Object tkrNm = r.getAttribute("TkrNm");
                    TickerRowDS t = new TickerRowDS();
                    t.setTickerId(r.getAttribute("TkrId").toString());
                    t.setSeqNo(i);
                    t.setTickerDesc(tkrNm == null ? "" : tkrNm.toString());
                    t.setArrow(d.nextToken());
                    t.setCurrentAmt(d.nextToken());
                    t.setPreviousAmt(d.nextToken());
                    t.setCurr(getCurrIdBs().toString());
                    list.add(t);
                }
            }
            //Gross Profit|419530808.04686|192307731861484.01752
        }

        mapViewVO.setRangeSize(i_2);
        ViewObjectImpl impl = this.getTickerListVO();
        return list;
    }

    /**
     * Method to insert Rows in User Settings
     */
    public void saveUserSettings() {
        ViewObjectImpl impl = this.getBdgAllTckrsVO();
        impl.setNamedWhereClauseParam("CldIdBind", EbizParams.GLBL_APP_CLD_ID());
        impl.setNamedWhereClauseParam("OrgIdBind", EbizParams.GLBL_APP_USR_ORG());
        impl.setNamedWhereClauseParam("SlocIdBind", EbizParams.GLBL_APP_SERV_LOC());
        impl.setNamedWhereClauseParam("UsrIdBind", EbizParams.GLBL_APP_USR());
        impl.executeQuery();
        _log.info("Rows Removed : " + impl.getEstimatedRowCount());
        RowSetIterator ctr = impl.createRowSetIterator(null);
        ViewObjectImpl impl1 = this.getMmTkrUsrVO();

        while (ctr.hasNext()) {
            RowFinder finder = impl1.lookupRowFinder("findTckrRow");
            NameValuePairs n = new NameValuePairs();
            n.setAttribute("CldId", EbizParams.GLBL_APP_CLD_ID());
            n.setAttribute("OrgId", EbizParams.GLBL_APP_USR_ORG());
            n.setAttribute("SlocId", EbizParams.GLBL_APP_SERV_LOC());
            n.setAttribute("TkrId", ctr.next().getAttribute("TkrId").toString());
            n.setAttribute("UsrId", EbizParams.GLBL_APP_USR());
            RowIterator ri = finder.execute(n, impl1);
            _log.info("Set Default Find Row : " + ri.getFetchedRowCount());
            if (ri.getFetchedRowCount() > 0) {
                if (ri.hasNext()) {
                    ri.next().setAttribute("TkrPos", 0);
                }
            }
        }
        ctr.closeRowSetIterator();
        //this.getDBTransaction().commit();
    }

    public void insertRecord(Integer seqNo, String tickeId) {
        ViewObjectImpl impl = this.getMmTkrUsrVO();
        RowFinder finder = impl.lookupRowFinder("findTckrRow");
        NameValuePairs n = new NameValuePairs();
        n.setAttribute("CldId", EbizParams.GLBL_APP_CLD_ID());
        n.setAttribute("OrgId", EbizParams.GLBL_APP_USR_ORG());
        n.setAttribute("SlocId", EbizParams.GLBL_APP_SERV_LOC());
        n.setAttribute("TkrId", tickeId);
        n.setAttribute("UsrId", EbizParams.GLBL_APP_USR());

        RowIterator ri = finder.execute(n, impl);
        _log.info("Find Row : " + ri.getFetchedRowCount());
        if (ri.getFetchedRowCount() > 0) {
            if (ri.hasNext()) {
                ri.next().setAttribute("TkrPos", seqNo);
            }
        }
    }


    /**
     * Method that returns the WorkFlow Document Count
     * @param DocTypeId
     * @param CountType
     * @return
     */
    public Number getDocPendingCountForMyApproval(Integer DocTypeId, StringBuffer CountType, StringBuffer type) {
        Number count = new Number(0);
        if (type != null) {
        } else {
            type = new StringBuffer("X");
        }
        try {
            BigDecimal s = null;


            s = (BigDecimal) callStoredFunction(Types.NUMERIC, "BDG.BDG_DOC_WF_CNT(?,?,?,?,?,?,?)", new Object[] {
                                                EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(),
                                                EbizParams.GLBL_APP_USR_ORG(), EbizParams.GLBL_APP_USR(), DocTypeId,
                                                CountType.toString(), type.toString()
            });

            count = new Number(s);
        } catch (Exception e) {
            _log.info("There have been an error in calling function 'BDG_DOC_WF_CNT' !");
            e.printStackTrace();
        }
        return count;
    }


    /**
     * Set Initial Parameters for WFSalesTargetVo()
     * @param type
     */
    public void setParametersInWFSalesTargetVo(StringBuffer type) {
        if (type != null) {
            ViewObjectImpl quot = this.getWFSalesTargetVO();
            quot.setNamedWhereClauseParam("SlocIdBind", EbizParams.GLBL_APP_SERV_LOC());
            quot.setNamedWhereClauseParam("CldIdBind", EbizParams.GLBL_APP_CLD_ID());
            quot.setNamedWhereClauseParam("OrgIdBind", EbizParams.GLBL_APP_USR_ORG());
            quot.setNamedWhereClauseParam("HoOrgIdBind", EbizParams.GLBL_HO_ORG_ID());
            quot.setNamedWhereClauseParam("UsrIdBind", EbizParams.GLBL_APP_USR());
            quot.setNamedWhereClauseParam("WfTypeBind", type.toString());
            quot.executeQuery();


        }

    }

    /**
     * Set Initial Parameters for WFSalesForcastVo()
     * @param type
     */
    public void setParametersInWFForcastVo(StringBuffer type) {
        if (type != null) {
            ViewObjectImpl quot = this.getWfForcastVO();
            quot.setNamedWhereClauseParam("SlocIdBind", EbizParams.GLBL_APP_SERV_LOC());
            quot.setNamedWhereClauseParam("CldIdBind", EbizParams.GLBL_APP_CLD_ID());
            quot.setNamedWhereClauseParam("OrgIdBind", EbizParams.GLBL_APP_USR_ORG());
            quot.setNamedWhereClauseParam("HoOrgIdBind", EbizParams.GLBL_HO_ORG_ID());
            quot.setNamedWhereClauseParam("UsrIdBind", EbizParams.GLBL_APP_USR());
            quot.setNamedWhereClauseParam("WfTypeBind", type.toString());
            quot.executeQuery();


        }

    }

    /**
     * Set Initial Parameters for WFMaterialBudgetVO()
     * @param type
     */
    public void setParametersInWFMaterialBudgetVo(StringBuffer type) {
        if (type != null) {
            ViewObjectImpl quot = this.getWFMaterialBudgetVO();
            quot.setNamedWhereClauseParam("SlocIdBind", EbizParams.GLBL_APP_SERV_LOC());
            quot.setNamedWhereClauseParam("CldIdBind", EbizParams.GLBL_APP_CLD_ID());
            quot.setNamedWhereClauseParam("OrgIdBind", EbizParams.GLBL_APP_USR_ORG());
            quot.setNamedWhereClauseParam("HoOrgIdBind", EbizParams.GLBL_HO_ORG_ID());
            quot.setNamedWhereClauseParam("UsrIdBind", EbizParams.GLBL_APP_USR());
            quot.setNamedWhereClauseParam("WfTypeBind", type.toString());
            quot.executeQuery();


        }

    }

    /**
     * Set Initial Parameters for WFFinanceBudgetVo()
     * @param type
     */
    public void setParametersInWFFinanceBudgetVo(StringBuffer type) {
        if (type != null) {
            ViewObjectImpl quot = this.getWFFinBudgetVO();
            quot.setNamedWhereClauseParam("SlocIdBind", EbizParams.GLBL_APP_SERV_LOC());
            quot.setNamedWhereClauseParam("CldIdBind", EbizParams.GLBL_APP_CLD_ID());
            quot.setNamedWhereClauseParam("OrgIdBind", EbizParams.GLBL_APP_USR_ORG());
            quot.setNamedWhereClauseParam("HoOrgIdBind", EbizParams.GLBL_HO_ORG_ID());
            quot.setNamedWhereClauseParam("UsrIdBind", EbizParams.GLBL_APP_USR());
            quot.setNamedWhereClauseParam("WfTypeBind", type.toString());
            quot.executeQuery();
        }

    }


    /**
     * Container's getter for BDGSelectedTickerList1.
     * @return BDGSelectedTickerList1
     */
    public ViewObjectImpl getBDGSelectedTickerListVO() {
        return (ViewObjectImpl) findViewObject("BDGSelectedTickerListVO");
    }

    /**
     * Container's getter for CurrDescFromId1.
     * @return CurrDescFromId1
     */
    public ViewObjectImpl getCurrDescFromIdVO() {
        return (ViewObjectImpl) findViewObject("CurrDescFromIdVO");
    }

    /**
     * Container's getter for BDGUnselectedTickerList1.
     * @return BDGUnselectedTickerList1
     */
    public ViewObjectImpl getBDGUnselectedTickerListVO() {
        return (ViewObjectImpl) findViewObject("BDGUnselectedTickerListVO");
    }

    /**
     * Container's getter for TickerList1.
     * @return TickerList1
     */
    public ViewObjectImpl getTickerListVO() {
        return (ViewObjectImpl) findViewObject("TickerListVO");
    }

    /**
     * Container's getter for MmTkrUsr1.
     * @return MmTkrUsr1
     */
    public ViewObjectImpl getMmTkrUsrVO() {
        return (ViewObjectImpl) findViewObject("MmTkrUsrVO");
    }

    /**
     * Container's getter for BdgAllTckrs1.
     * @return BdgAllTckrs1
     */
    public ViewObjectImpl getBdgAllTckrsVO() {
        return (ViewObjectImpl) findViewObject("BdgAllTckrsVO");
    }

    /**
     * Container's getter for WFFinBudget1.
     * @return WFFinBudget1
     */
    public ViewObjectImpl getWFFinBudgetVO() {
        return (ViewObjectImpl) findViewObject("WFFinBudgetVO");
    }

    /**
     * Container's getter for WFMaterialBudget1.
     * @return WFMaterialBudget1
     */
    public ViewObjectImpl getWFMaterialBudgetVO() {
        return (ViewObjectImpl) findViewObject("WFMaterialBudgetVO");
    }

    /**
     * Container's getter for WFSalesTarget1.
     * @return WFSalesTarget1
     */
    public ViewObjectImpl getWFSalesTargetVO() {
        return (ViewObjectImpl) findViewObject("WFSalesTargetVO");
    }

    public void setEmployeeCodeToGlobalParam() {
        ViewObjectImpl detailVO1 = this.getGetEmployeeCodeVO1();
        detailVO1.setNamedWhereClauseParam("BindCldId", EbizParams.GLBL_APP_CLD_ID());
        detailVO1.setNamedWhereClauseParam("BindSlocId", EbizParams.GLBL_APP_SERV_LOC());
        detailVO1.setNamedWhereClauseParam("BindHoOrgId", EbizParams.GLBL_HO_ORG_ID());
        detailVO1.setNamedWhereClauseParam("BindOrgId", EbizParams.GLBL_APP_USR_ORG());
        detailVO1.setNamedWhereClauseParam("BindUserId", EbizParams.GLBL_APP_USR());
        detailVO1.executeQuery();

        Row[] allRowsInRange = detailVO1.getAllRowsInRange();
        _log.info("No. of Rows =" + allRowsInRange.length);
        if (allRowsInRange.length > 0) {
            RequestContext.getCurrentInstance().getPageFlowScope().put("P_EMP_CODE",
                                                                       allRowsInRange[0].getAttribute("EmpCode"));

        }

    }

    public void executeVoOnLoad() {
        /*  this.getGraphRevenueExpenseVO().setNamedWhereClauseParam("CldIdBind", EbizParams.GLBL_APP_CLD_ID());
        this.getGraphRevenueExpenseVO().setNamedWhereClauseParam("HoOrgIdBind", EbizParams.GLBL_HO_ORG_ID());
        this.getGraphRevenueExpenseVO().setNamedWhereClauseParam("OrgIdBind", EbizParams.GLBL_APP_USR_ORG());
        this.getGraphRevenueExpenseVO().setNamedWhereClauseParam("SlocIdBind", EbizParams.GLBL_APP_SERV_LOC());
        this.getGraphRevenueExpenseVO().executeQuery();
        this.getGraphTargetVsActualSalesVO().setNamedWhereClauseParam("CldIdBind", EbizParams.GLBL_APP_CLD_ID());
        this.getGraphTargetVsActualSalesVO().setNamedWhereClauseParam("HoOrgIdBind", EbizParams.GLBL_HO_ORG_ID());
        this.getGraphTargetVsActualSalesVO().setNamedWhereClauseParam("OrgIdBind", EbizParams.GLBL_APP_USR_ORG());
        this.getGraphTargetVsActualSalesVO().setNamedWhereClauseParam("SlocIdBind", EbizParams.GLBL_APP_SERV_LOC());
        this.getGraphTargetVsActualSalesVO().setNamedWhereClauseParam("EmpCodeBind",
                                                                      ADFModelUtils.resolvEl("#{pageFlowScope.P_EMP_CODE}"));
        this.getGraphTargetVsActualSalesVO().executeQuery();

        this.getGraphBudgetVsActualPercVO().setNamedWhereClauseParam("CldIdBind", EbizParams.GLBL_APP_CLD_ID());
        this.getGraphBudgetVsActualPercVO().setNamedWhereClauseParam("OrgIdBind", EbizParams.GLBL_APP_USR_ORG());
        this.getGraphBudgetVsActualPercVO().setNamedWhereClauseParam("SlocIdBind", EbizParams.GLBL_APP_SERV_LOC());
        this.getGraphBudgetVsActualPercVO().setNamedWhereClauseParam("EmpCodeBind",
                                                                     ADFModelUtils.resolvEl("#{pageFlowScope.P_EMP_CODE}"));
        this.getGraphBudgetVsActualPercVO().executeQuery();

        this.getGraphBudgetdExpVsActualExpVO().setNamedWhereClauseParam("CldIdBind", EbizParams.GLBL_APP_CLD_ID());
        this.getGraphBudgetdExpVsActualExpVO().setNamedWhereClauseParam("HoOrgIdBind", EbizParams.GLBL_HO_ORG_ID());
        this.getGraphBudgetdExpVsActualExpVO().setNamedWhereClauseParam("OrgIdBind", EbizParams.GLBL_APP_USR_ORG());
        this.getGraphBudgetdExpVsActualExpVO().setNamedWhereClauseParam("SlocIdBind", EbizParams.GLBL_APP_SERV_LOC());
        this.getGraphBudgetdExpVsActualExpVO().executeQuery();

        this.getGraphBdgIncomeVsActualIncomeVO().setNamedWhereClauseParam("CldIdBind", EbizParams.GLBL_APP_CLD_ID());
        this.getGraphBdgIncomeVsActualIncomeVO().setNamedWhereClauseParam("HoOrgIdBind", EbizParams.GLBL_HO_ORG_ID());
        this.getGraphBdgIncomeVsActualIncomeVO().setNamedWhereClauseParam("OrgIdBind", EbizParams.GLBL_APP_USR_ORG());
        this.getGraphBdgIncomeVsActualIncomeVO().setNamedWhereClauseParam("SlocIdBind", EbizParams.GLBL_APP_SERV_LOC());
        this.getGraphBdgIncomeVsActualIncomeVO().executeQuery(); */
    }

    /**
     * Container's getter for GetEmployeeCode1.
     * @return GetEmployeeCode1
     */
    public ViewObjectImpl getGetEmployeeCodeVO1() {
        return (ViewObjectImpl) findViewObject("GetEmployeeCodeVO1");
    }

    /**
     * Container's getter for GraphRevenueExpense1.
     * @return GraphRevenueExpense1
     */
    public ViewObjectImpl getGraphRevenueExpenseVO() {
        return (ViewObjectImpl) findViewObject("GraphRevenueExpenseVO");
    }

    /**
     * Container's getter for GraphTargetVsActualSales1.
     * @return GraphTargetVsActualSales1
     */
    public ViewObjectImpl getGraphTargetVsActualSalesVO() {
        return (ViewObjectImpl) findViewObject("GraphTargetVsActualSalesVO");
    }

    /**
     * Container's getter for GraphBudgetVsActualPerc1.
     * @return GraphBudgetVsActualPerc1
     */
    public ViewObjectImpl getGraphBudgetVsActualPercVO() {
        return (ViewObjectImpl) findViewObject("GraphBudgetVsActualPercVO");
    }

    /**
     * Container's getter for GraphBudgetdExpVsActualExp1.
     * @return GraphBudgetdExpVsActualExp1
     */
    public ViewObjectImpl getGraphBudgetdExpVsActualExpVO() {
        return (ViewObjectImpl) findViewObject("GraphBudgetdExpVsActualExpVO");
    }

    /**
     * Container's getter for GraphBdgIncomeVsActualIncome1.
     * @return GraphBdgIncomeVsActualIncome1
     */
    public ViewObjectImpl getGraphBdgIncomeVsActualIncomeVO() {
        return (ViewObjectImpl) findViewObject("GraphBdgIncomeVsActualIncomeVO");
    }

    /**
     * Container's getter for WfForcast1.
     * @return WfForcast1
     */
    public ViewObjectImpl getWfForcastVO() {
        return (ViewObjectImpl) findViewObject("WfForcastVO");
    }
}

