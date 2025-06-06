package trnpfuelbillapp.model.services;

import adf.utils.ebiz.EbizParams;
import adf.utils.ebiz.WorkFlowUtils;
import adf.utils.model.ADFModelUtils;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.faces.application.FacesMessage;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewLinkImpl;
import oracle.jbo.server.ViewObjectImpl;

import trnpfuelbillapp.model.services.common.TrnpFuelBillAM;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Jun 19 11:31:16 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class TrnpFuelBillAMImpl extends ApplicationModuleImpl implements TrnpFuelBillAM {
    private Integer glblDocId = 24258;
    private Integer glblDocTypeId = 0;
    private static ADFLogger _log = ADFLogger.createADFLogger(TrnpFuelBillAMImpl.class);

    /**
     * This is the default constructor (do not remove).
     */
    public TrnpFuelBillAMImpl() {
    }

    /**
     * Container's getter for SlsTrnpFuelBill.
     * @return SlsTrnpFuelBill
     */
    public ViewObjectImpl getSlsTrnpFuelBill() {
        return (ViewObjectImpl) findViewObject("SlsTrnpFuelBill");
    }

    /**
     * Container's getter for SlsTrnpFuelBillDtl.
     * @return SlsTrnpFuelBillDtl
     */
    public ViewObjectImpl getSlsTrnpFuelBillDtl() {
        return (ViewObjectImpl) findViewObject("SlsTrnpFuelBillDtl");
    }

    /**
     * Container's getter for SlsTrnpFuelBillToBillDtlVL1.
     * @return SlsTrnpFuelBillToBillDtlVL1
     */
    public ViewLinkImpl getSlsTrnpFuelBillToBillDtlVL1() {
        return (ViewLinkImpl) findViewLink("SlsTrnpFuelBillToBillDtlVL1");
    }

    /**
     * Container's getter for LovUomId1.
     * @return LovUomId1
     */
    public ViewObjectImpl getLovUomId() {
        return (ViewObjectImpl) findViewObject("LovUomId");
    }

    public String setRequestNo() {
        String billNo = null;
        if (this.getSlsTrnpFuelBill().getCurrentRow().getAttribute("BillNo") != null)
            billNo = (String) this.getSlsTrnpFuelBill().getCurrentRow().getAttribute("BillNo");
        else {
            Integer fyid =
                EbizParams.getFyId(this.getRootApplicationModule(), EbizParams.GLBL_APP_USR_ORG(),
                                   new Timestamp(System.currentTimeMillis()));
            if (fyid != null) {
                billNo =
                    (String) ADFModelUtils.callFunction(this.getRootApplicationModule(),
                                                        new StringBuilder("SLS.TRNP_GEN_DISP_DOC_ID(?,?,?,?,?,?,?,?,?)"), new Object[] {
                                                        EbizParams.GLBL_APP_SERV_LOC(), EbizParams.GLBL_APP_CLD_ID(),
                                                        EbizParams.GLBL_APP_USR_ORG(), glblDocId, null, glblDocTypeId,
                                                        "SLS$TRNP$FUEL$BILL", fyid, 0
                }, Types.VARCHAR);
            } else
                billNo = "-1";
        }
        this.getSlsTrnpFuelBill().getCurrentRow().setAttribute("BillNo", billNo);
        return billNo;
    }

    public String genTxnId() {
        return (String) EbizParams.generateDocTxnId(this.getRootApplicationModule(), glblDocId);
    }

    public String getWfNo() {
        return (String) WorkFlowUtils.getWorkFlowId(this.getRootApplicationModule(), glblDocId, glblDocTypeId);
    }

    public Integer currUsrLvl(String wfNo) {
        return WorkFlowUtils.getWorkFlowCurrUsrLvl(this.getRootApplicationModule(), wfNo, glblDocId, glblDocTypeId);
    }

    public String insWfTxn(String wfId, Integer lvlFrm, Integer lvlTo, String action, String remark, Number amount) {
        Integer usrId = EbizParams.GLBL_APP_USR();
        String docTxnId = (String) this.getSlsTrnpFuelBill().getCurrentRow().getAttribute("DocId");
        return (String) WorkFlowUtils.insertEntriesIntoWfTxn(this.getRootApplicationModule(), glblDocId, glblDocTypeId,
                                                             docTxnId, wfId, usrId, usrId, lvlFrm, lvlTo, action,
                                                             remark, amount);
    }

    public Integer chkPendingUsr() {
        String docTxnId = (String) this.getSlsTrnpFuelBill().getCurrentRow().getAttribute("DocId");
        return WorkFlowUtils.docPendingAt(this.getRootApplicationModule(), glblDocId, glblDocTypeId, docTxnId);
    }


    public boolean DuplicateRecordValidation() {


        System.out.println("in am duplicatevalidation method ****");

        boolean flag = false;


        Row currentRow = this.getSlsTrnpFuelBill().getCurrentRow();

        Object attribute1 = currentRow.getAttribute("EoId");
        Object attribute2 = currentRow.getAttribute("EoInvDt");
        Object attribute3 = currentRow.getAttribute("CurrId");

        RowQualifier qualifier = new RowQualifier(this.getSlsTrnpFuelBillVO1());
        qualifier.setWhereClause("EoId =" + attribute1 + " and EoInvDt='" + attribute2 + "' and CurrId=" + attribute3);
        System.out.println("Expression=" + qualifier.getExprStr());
        Row[] filteredRows = this.getSlsTrnpFuelBillVO1().getFilteredRows(qualifier);
        System.out.println("no of filtered row are::::::" + filteredRows.length);

        for (Row row : filteredRows) {
            if (!(row.getAttribute("DocId").toString().equals(currentRow.getAttribute("DocId").toString())))
                return true;
        }
        return flag;

    }


    public void wfReturnAction() {
        _log.info("WF_RET_PARAM =" + ADFModelUtils.resolvEl("#{pageFlowScope.WF_RET_PARAM}"));
        if (ADFModelUtils.resolvEl("#{pageFlowScope.WF_RET_PARAM}") != null &&
            ADFModelUtils.resolvEl("#{pageFlowScope.WF_RET_PARAM}").equals("A")) {
            this.getSlsTrnpFuelBill().getCurrentRow().setAttribute("Status", new Integer(24));
            //call functionset
            ViewObjectImpl billVO1 = this.getSlsTrnpFuelBill();
            Row currentRow = billVO1.getCurrentRow();
            Object docId = currentRow.getAttribute("DocId");
            Object eoid = currentRow.getAttribute("EoId");
            Object coaid = currentRow.getAttribute("CoaId");
            Object billDetail = currentRow.getAttribute("BillDt");
            Object totalAmount = currentRow.getAttribute("TotAmtBs");
            Object currencyId = currentRow.getAttribute("CurrId");
            Object durrencyConversion = currentRow.getAttribute("CurrConv");


            String aPP_CLD_ID = EbizParams.GLBL_APP_CLD_ID();
            Integer aPP_SERV_LOC = EbizParams.GLBL_APP_SERV_LOC();
            Integer aPP_USR = EbizParams.GLBL_APP_USR();
            String aPP_USR_ORG = EbizParams.GLBL_APP_USR_ORG();
            String bL_HO_ORG_ID = EbizParams.GLBL_HO_ORG_ID();

            Object detail =
                ADFModelUtils.callFunction(this.getRootApplicationModule(),
                                           new StringBuilder("sls.fn_trnp_fb_gl_ins (?,?,?,?,?,?,?,?,?,?,?,?)"), new Object[] {
                                           docId, billDetail, totalAmount, aPP_USR, aPP_USR_ORG, aPP_SERV_LOC,
                                           bL_HO_ORG_ID, aPP_CLD_ID, eoid, coaid, currencyId, durrencyConversion
            }, Types.VARCHAR);
            _log.info("return value From GL Function=" + detail);
            Object ret =
                ADFModelUtils.callFunction(this.getRootApplicationModule(),
                                           new StringBuilder("SLS.FN_TRNP_UPDT_DOC_STATUS (?,?,?,?,?,?)"), new Object[] {
                                           aPP_CLD_ID, aPP_SERV_LOC, aPP_USR_ORG, bL_HO_ORG_ID, glblDocId, docId
            }, Types.NUMERIC);
            _log.info("return value From Fuel Outstation=" + ret);
            ADFModelUtils.showFormattedFacesMessage(ADFModelUtils.resolvRsrc("MSG.2225"), ADFModelUtils.resolvRsrc("MSG.1773")+" : " + detail,
                                                    FacesMessage.SEVERITY_INFO);
            /* ADFModelUtils.showFormattedFacesMessage("Fuel Bill Approved.", "Voucher id " + detail,
                                                    FacesMessage.SEVERITY_INFO); */
        } else if (ADFModelUtils.resolvEl("#{pageFlowScope.WF_RET_PARAM}") != null &&
                   ADFModelUtils.resolvEl("#{pageFlowScope.WF_RET_PARAM}").equals("F")) {

            this.getSlsTrnpFuelBill().getCurrentRow().setAttribute("Status", new Integer(23));

        }
    }

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {

        CallableStatement st = null;
        try {
            // 1. Create a JDBC CallabledStatement
            st = getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
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
            //adflog.info("Function return " + st.getObject(1));
            return st.getObject(1);
        } catch (SQLException e) {
            throw new JboException(e.getMessage());
        } finally {
            if (st != null) {
                try {
                    // 7. Close the statement
                    st.close();
                } catch (SQLException e) {
                    throw new JboException(e.getMessage());
                }
            }
        }
    }


    public String getUsrNm(Integer usrId) {
        this.getLovUsrNm().setNamedWhereClauseParam("slocIdBind", EbizParams.GLBL_APP_SERV_LOC());
        this.getLovUsrNm().setNamedWhereClauseParam("usrIdBind", usrId);
        this.getLovUsrNm().executeQuery();
        Row[] fr = this.getLovUsrNm().getFilteredRows("UsrId", usrId);
        if (fr.length > 0)
            return (String) fr[0].getAttribute("UsrName");
        else
            return "Anonymous";
    }

    /**
     * Container's getter for LovUsrNm1.
     * @return LovUsrNm1
     */
    public ViewObjectImpl getLovUsrNm() {
        return (ViewObjectImpl) findViewObject("LovUsrNm");
    }

    public String populateFuelDtl() {
        _log.info("in poupulate am method");

        Row currRow = this.getSlsTrnpFuelBill().getCurrentRow();
        this.getSlsTrnpOutstnFuelDtl().setNamedWhereClauseParam("cldIdBind", currRow.getAttribute("CldId"));
        this.getSlsTrnpOutstnFuelDtl().setNamedWhereClauseParam("currIdBind", currRow.getAttribute("CurrId"));
        this.getSlsTrnpOutstnFuelDtl().setNamedWhereClauseParam("hoOrgIdBind", currRow.getAttribute("HoOrgId"));
        this.getSlsTrnpOutstnFuelDtl().setNamedWhereClauseParam("orgIdBind", currRow.getAttribute("OrgId"));
        this.getSlsTrnpOutstnFuelDtl().setNamedWhereClauseParam("slocIdBind", currRow.getAttribute("SlocId"));
        this.getSlsTrnpOutstnFuelDtl().setNamedWhereClauseParam("stationIdBind", currRow.getAttribute("EoId"));
        this.getSlsTrnpOutstnFuelDtl().setNamedWhereClauseParam("suppInvStrtDtBind", currRow.getAttribute("InvStrtDt"));
        this.getSlsTrnpOutstnFuelDtl().setNamedWhereClauseParam("suppInvEndDtBind", currRow.getAttribute("InvEndDt"));
        this.getSlsTrnpOutstnFuelDtl().executeQuery();
        this.getSlsTrnpOutstnFuelDtl().setRangeSize(-1);
        Row[] outStnRows = this.getSlsTrnpOutstnFuelDtl().getAllRowsInRange();
        _log.info("No. of Filtered Rows = " + outStnRows.length);
        if (outStnRows.length == 0)
            return "N";
        Number totAmtBs = new Number(0);
        for (Row row : outStnRows) {
            Row newRow = this.getSlsTrnpFuelBillDtl().createRow();
            newRow.setAttribute("DocIdFuelDtl", row.getAttribute("DocId"));
            newRow.setAttribute("EoInvNo", row.getAttribute("InvNo"));
            newRow.setAttribute("FuelAmtBs", row.getAttribute("AmtBs"));
            newRow.setAttribute("FuelAmtSp", row.getAttribute("AmtSp"));
            newRow.setAttribute("FuelQty", row.getAttribute("FuelQty"));
            newRow.setAttribute("FuelRate", row.getAttribute("FuelRate"));
            newRow.setAttribute("Uom", row.getAttribute("Uom"));
            newRow.setAttribute("VehicleNo", row.getAttribute("VehicleNo"));
            newRow.setAttribute("VhclItmId", row.getAttribute("VhclItmId"));
            this.getSlsTrnpFuelBillDtl().insertRow(newRow);
            if (row.getAttribute("AmtBs") != null)
                totAmtBs = totAmtBs.add((Number) row.getAttribute("AmtBs"));
        }

        currRow.setAttribute("TotAmtBs", totAmtBs);
        currRow.setAttribute("TotAmtSp", totAmtBs.divide((Number) currRow.getAttribute("CurrConv")));
        return "Y";
    }

    /**
     * Container's getter for SlsTrnpOutstnFuelDtl1.
     * @return SlsTrnpOutstnFuelDtl1
     */
    public ViewObjectImpl getSlsTrnpOutstnFuelDtl() {
        return (ViewObjectImpl) findViewObject("SlsTrnpOutstnFuelDtl");
    }

    /**
     * Container's getter for SearchFuelBillVw1.
     * @return SearchFuelBillVw1
     */
    public ViewObjectImpl getSearchFuelBillVw() {
        return (ViewObjectImpl) findViewObject("SearchFuelBillVw");
    }

    /**
     * Container's getter for DualForSearch1.
     * @return DualForSearch1
     */
    public ViewObjectImpl getDualForSearch() {
        return (ViewObjectImpl) findViewObject("DualForSearch");
    }


    public void searchFuelBill(String actionType) {
        Row curr = this.getDualForSearch().getCurrentRow();
        if (actionType.equals("R")) {
            this.getDualForSearch().executeQuery();
            this.getSearchFuelBillVw().setNamedWhereClauseParam("orgIdBind", "-1");
        } else {
            this.getSearchFuelBillVw().setNamedWhereClauseParam("orgIdBind", curr.getAttribute("OrgId"));
        }

        this.getSearchFuelBillVw().setNamedWhereClauseParam("cldIdBind", curr.getAttribute("CldId"));
        this.getSearchFuelBillVw().setNamedWhereClauseParam("slocIdBind", curr.getAttribute("SlocId"));
        this.getSearchFuelBillVw().setNamedWhereClauseParam("hoOrgIdBind", curr.getAttribute("HoOrgId"));

        this.getSearchFuelBillVw().setNamedWhereClauseParam("billNoBind", curr.getAttribute("TransBillNo"));
        this.getSearchFuelBillVw().setNamedWhereClauseParam("billStrtDtBind", curr.getAttribute("TransBillStrtDt"));
        this.getSearchFuelBillVw().setNamedWhereClauseParam("billEndDtBind", curr.getAttribute("TransBillEndDt"));
        this.getSearchFuelBillVw().setNamedWhereClauseParam("vhclNoBind", curr.getAttribute("TransVhclNo"));
        this.getSearchFuelBillVw().setNamedWhereClauseParam("stnIdBind", curr.getAttribute("TransStationId"));
        this.getSearchFuelBillVw().setNamedWhereClauseParam("statusBind", curr.getAttribute("TransStatus"));
        this.getSearchFuelBillVw().setNamedWhereClauseParam("invStDtBind", curr.getAttribute("TransInvStrtDt"));
        this.getSearchFuelBillVw().setNamedWhereClauseParam("invEndDtBind", curr.getAttribute("TransInvEndDt"));
        this.getSearchFuelBillVw().setNamedWhereClauseParam("invNoBind", curr.getAttribute("TransInvNo"));
        this.getSearchFuelBillVw().setNamedWhereClauseParam("currIdBind", curr.getAttribute("TransCurrId"));
        this.getSearchFuelBillVw().executeQuery();
    }


    public void executeOnLoad() {
        this.getDualForSearch().setNamedWhereClauseParam("cldIdBind", EbizParams.GLBL_APP_CLD_ID());
        this.getDualForSearch().setNamedWhereClauseParam("slocIdBind", EbizParams.GLBL_APP_SERV_LOC());
        this.getDualForSearch().setNamedWhereClauseParam("orgIdBind", EbizParams.GLBL_APP_USR_ORG());
        this.getDualForSearch().setNamedWhereClauseParam("hoOrgIdBind", EbizParams.GLBL_HO_ORG_ID());
        this.getDualForSearch().executeQuery();
    }

    /**
     * Container's getter for SlsTrnpFuelBillVO1.
     * @return SlsTrnpFuelBillVO1
     */
    public ViewObjectImpl getSlsTrnpFuelBillVO1() {
        return (ViewObjectImpl) findViewObject("SlsTrnpFuelBillVO1");
    }

    public void filterTxnId() {
        this.getSlsTrnpFuelBill().setNamedWhereClauseParam("docIdBind",
                                                           ADFModelUtils.resolvEl("#{pageFlowScope.TXN_ID_PARAM}"));
        this.getSlsTrnpFuelBill().setNamedWhereClauseParam("cldIdBind", EbizParams.GLBL_APP_CLD_ID());
        this.getSlsTrnpFuelBill().setNamedWhereClauseParam("slocIdBind", EbizParams.GLBL_APP_SERV_LOC());
        this.getSlsTrnpFuelBill().setNamedWhereClauseParam("orgIdBind", EbizParams.GLBL_APP_USR_ORG());
        this.getSlsTrnpFuelBill().setNamedWhereClauseParam("hoOrgIdBind", EbizParams.GLBL_HO_ORG_ID());
        this.getSlsTrnpFuelBill().executeQuery();
    }


    public String chkCoaExist() {
        if (this.getSlsTrnpFuelBill().getCurrentRow().getAttribute("CoaId") != null &&
            !this.getSlsTrnpFuelBill().getCurrentRow().getAttribute("CoaId").toString().equals("-1"))
            return "Y";
        else
            return "N";
    }
}


