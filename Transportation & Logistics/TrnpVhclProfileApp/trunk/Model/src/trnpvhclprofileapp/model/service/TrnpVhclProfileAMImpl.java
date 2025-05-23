package trnpvhclprofileapp.model.service;

import adf.utils.ebiz.EbizParams;
import adf.utils.ebiz.WorkFlowUtils;
import adf.utils.model.ADFModelUtils;

import java.sql.Types;

import oracle.jbo.Row;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewLinkImpl;
import oracle.jbo.server.ViewObjectImpl;

import trnpvhclprofileapp.model.service.common.TrnpVhclProfileAM;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Jul 03 16:24:07 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class TrnpVhclProfileAMImpl extends ApplicationModuleImpl implements TrnpVhclProfileAM {

    protected Integer glblDocId = 24101;
    protected Integer glblDocTypeId = 0;

    /**
     * This is the default constructor (do not remove).
     */
    public TrnpVhclProfileAMImpl() {
    }

    protected String getCldIdValue() {
        return EbizParams.GLBL_APP_CLD_ID();
    }

    protected String getHoOrgIdValue() {
        return EbizParams.GLBL_HO_ORG_ID();
    }

    protected Integer getSlocIdValue() {
        return EbizParams.GLBL_APP_SERV_LOC();
    }

    protected String getOrgIdValue() {
        return EbizParams.GLBL_APP_USR_ORG();
    }

    /**
     * to set search criteria and set Bind variables of search Vo.
     */

    public void setBindVarOnLoadSearch()

    {
        System.out.println("Inside the setBindVars method");
        this.getSearch1().setNamedWhereClauseParam("BindCldId", this.getCldIdValue());
        this.getSearch1().setNamedWhereClauseParam("BindHoOrgId", this.getHoOrgIdValue());
        this.getSearch1().setNamedWhereClauseParam("BindOrgId", this.getOrgIdValue());
        this.getSearch1().setNamedWhereClauseParam("BindSlocId", this.getSlocIdValue());
        this.getSearch1().executeQuery();
    }

    public void searchVhclProfile() {

        System.out.println("Inside searchVhclProfile method in AMImpl !");

        Row currentRow = this.getSearch1().getCurrentRow();

        if (currentRow != null) {

            System.out.println("Inside the Not null condition in AMImpl !");
            ViewObjectImpl dataVO1 = this.getSearchResult1();
            dataVO1.setNamedWhereClauseParam("BindCldId", this.getCldIdValue());
            dataVO1.setNamedWhereClauseParam("BindSlocId", this.getSlocIdValue());
            dataVO1.setNamedWhereClauseParam("BindHoOrgId", this.getHoOrgIdValue());

            if (currentRow.getAttribute("TransOwnType") != null) {
                dataVO1.setNamedWhereClauseParam("BindVhclOwnTyp", currentRow.getAttribute("TransOwnType"));
            } else {
                dataVO1.setNamedWhereClauseParam("BindVhclOwnTyp", null);
            }

            if (currentRow.getAttribute("TransVhclDesc") != null) {
                dataVO1.setNamedWhereClauseParam("BindVhclDesc", currentRow.getAttribute("TransVhclDesc"));
            } else {
                dataVO1.setNamedWhereClauseParam("BindVhclDesc", null);
            }

            if (currentRow.getAttribute("TransVhclTypeId") != null) {
                dataVO1.setNamedWhereClauseParam("BindVhclTyp", currentRow.getAttribute("TransVhclTypeId"));
            } else {
                dataVO1.setNamedWhereClauseParam("BindVhclTyp", null);
            }

            if (currentRow.getAttribute("TransGroupId") != null) {
                dataVO1.setNamedWhereClauseParam("BindGrpId", currentRow.getAttribute("TransGroupId"));
            } else {
                dataVO1.setNamedWhereClauseParam("BindGrpId", null);
            }

            if (currentRow.getAttribute("TransStatus") != null) {
                dataVO1.setNamedWhereClauseParam("BindStatus", currentRow.getAttribute("TransStatus"));
            } else {
                dataVO1.setNamedWhereClauseParam("BindStatus", null);
            }

            dataVO1.executeQuery();
        }

    }

    /**
     * to reset search panel form;
     */

    public void resetVhclProfile() {
        this.getSearch1().executeQuery();
        ViewObjectImpl vo = this.getSearchResult1();

        vo.setNamedWhereClauseParam("BindCldId", this.getCldIdValue());
        vo.setNamedWhereClauseParam("BindSlocId", -1);
        vo.setNamedWhereClauseParam("BindHoOrgId", this.getHoOrgIdValue());

        vo.setNamedWhereClauseParam("BindGrpId", null);
        vo.setNamedWhereClauseParam("BindVhclDesc", null);
        vo.setNamedWhereClauseParam("BindVhclOwnTyp", null);
        vo.setNamedWhereClauseParam("BindVhclTyp", null);
        vo.setNamedWhereClauseParam("BindStatus", null);
        vo.executeQuery();

    }

    public void filterVehiclePrf() {
        String docId = (String) ADFModelUtils.resolvEl("#{pageFlowScope.P_DOC_ID}");

        if (docId != null) {
            ViewObjectImpl vo = this.getSlsTrnpVhclPrf1();
            vo.setNamedWhereClauseParam("BindCldId", this.getCldIdValue());
            vo.setNamedWhereClauseParam("BindSlocId", this.getSlocIdValue());
            vo.setNamedWhereClauseParam("BindHoOrgId", this.getHoOrgIdValue());
            vo.setNamedWhereClauseParam("BindDocId", docId);

            vo.executeQuery();

        }

    }


    public void createSlNo() {

        /*         ViewObjectImpl vo = this.getSlsTrnpVhclRegDtl1();
        Integer slNo = 0;
        Row[] allRowsInRange = vo.getAllRowsInRange();

        System.out.println("AllRowsInRange length ::" + allRowsInRange.length);
        if (allRowsInRange.length > 0) {
            for (Row r : allRowsInRange) {
                if (r.getAttribute("ItmSrNo") != null) {
                    if (slNo < Integer.parseInt(r.getAttribute("ItmSrNo").toString())) {
                        slNo = Integer.parseInt(r.getAttribute("ItmSrNo").toString());
                    }
                }
            }

            Row currentRow = vo.getCurrentRow();
            if (currentRow != null) {
                currentRow.setAttribute("ItmSrNo", (slNo + 1));
                System.out.println("Sl.No in AMImpl ::" + slNo);
            } else {
                currentRow.setAttribute("ItmSrNo", (slNo + 1));

                System.out.println("Sl.No in AMImpl ::" + slNo);
            }

        } */

    }


    public String getWfNo() {

        System.out.println("GlblDocId :: " + glblDocId + "\n GlblDocTypeId :: " + glblDocTypeId);
        return (String) WorkFlowUtils.getWorkFlowId(this.getRootApplicationModule(), glblDocId, glblDocTypeId);
    }

    public Integer currUsrLvl(String wfNo) {
        return WorkFlowUtils.getWorkFlowCurrUsrLvl(this.getRootApplicationModule(), wfNo, glblDocId, glblDocTypeId);
    }

    public String insWfTxn(String wfId, Integer lvlFrm, Integer lvlTo, String action, String remark, Number amount) {
        Integer usrId = EbizParams.GLBL_APP_USR();
        String docTxnId = (String) this.getSlsTrnpVhclPrf1().getCurrentRow().getAttribute("DocId");
        return (String) WorkFlowUtils.insertEntriesIntoWfTxn(this.getRootApplicationModule(), glblDocId, glblDocTypeId,
                                                             docTxnId, wfId, usrId, usrId, lvlFrm, lvlTo, action,
                                                             remark, amount);
    }

    public Integer chkPendingUsr() {
        String docTxnId = (String) this.getSlsTrnpVhclPrf1().getCurrentRow().getAttribute("DocId");
        return WorkFlowUtils.docPendingAt(this.getRootApplicationModule(), glblDocId, glblDocTypeId, docTxnId);
    }

    public void wfReturnAction() {
        System.out.println("WF_RET_PARAM =" + ADFModelUtils.resolvEl("#{pageFlowScope.WF_RET_PARAM}"));
        if (ADFModelUtils.resolvEl("#{pageFlowScope.WF_RET_PARAM}") != null &&
            ADFModelUtils.resolvEl("#{pageFlowScope.WF_RET_PARAM}").equals("A")) {

            System.out.println("Inside Approved status !!");
            pushDataItmPrf();
            this.getSlsTrnpVhclPrf1().getCurrentRow().setAttribute("VhclStatus", new Integer(38));

        } else if (ADFModelUtils.resolvEl("#{pageFlowScope.WF_RET_PARAM}") != null &&
                   ADFModelUtils.resolvEl("#{pageFlowScope.WF_RET_PARAM}").equals("F")) {

            System.out.println("Inside Pending status !! ");
            this.getSlsTrnpVhclPrf1().getCurrentRow().setAttribute("VhclStatus", new Integer(37));

        }
    }

    protected void pushDataItmPrf() {
        Row currentRow = this.getSlsTrnpVhclPrf1().getCurrentRow();
        if (currentRow != null) {
            System.out.println("Current row is not null");

            Object itmId = callStoredFunction(Types.VARCHAR, "FN_TRNP_GEN_VHCL_PRF(?,?,?,?,?,?)", new Object[] {
                                              this.getCldIdValue(), this.getSlocIdValue(),
                                              EbizParams.GLBL_APP_USR_ORG(), this.getHoOrgIdValue(),
                                              currentRow.getAttribute("DocId"), currentRow.getAttribute("UsrIdCreate")
            });
            System.out.println("Item Id generated=" + itmId);
            if (itmId != null && !itmId.toString().equalsIgnoreCase("-1") && !itmId.toString().equalsIgnoreCase("0")) {
                currentRow.setAttribute("VhclItmId", itmId);
                this.getDBTransaction().commit();
            }
        }

    }

    public void updtItmId() {
        Row currentRow = this.getSlsTrnpVhclPrf1().getCurrentRow();
        if (currentRow.getAttribute("VhclItmId") == null ||
            currentRow.getAttribute("VhclItmId").toString().length() == 0) {
            String tname = "SLS$TRNP$VHCL$PRF";
            Integer slocid = (Integer) this.getSlocIdValue();
            String cldid = (String) this.getCldIdValue();
            String orgid = (String) this.getOrgIdValue();
            String dochexid = null;
            Integer docsubtypeid = 0;
            Integer fyid = EbizParams.getFyId(this, this.getHoOrgIdValue(), new Timestamp(System.currentTimeMillis()));

            String itmIdDisp =
                (String) (callStoredFunction(Types.VARCHAR, "SLS.TRNP_GEN_DISP_DOC_ID(?,?,?,?,?,?,?,?,?)", new Object[] {
                                             slocid, cldid, this.getHoOrgIdValue(), glblDocId, dochexid, glblDocTypeId,
                                             tname, fyid, docsubtypeid
            }));
            currentRow.setAttribute("VhclItmId", itmIdDisp);
        }
    }

    /**Method to call database function*/

    private Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        return ADFModelUtils.callFunction(this, new StringBuilder(stmt), bindVars, sqlReturnType);
    }

    public boolean vhclRegNoDuplicate(String vhclRegNo) {
        Boolean duplicate = false;

        System.out.println("Parameter value of vhclRegNo ::" + vhclRegNo);

        ViewObjectImpl vo = this.getSlsTrnpVhclRegDtl();
        RowQualifier rq = new RowQualifier(vo);
        rq.setWhereClause("UPPER(VhclRegNo) = UPPER('" + vhclRegNo + "')");
        Row[] filteredRows = vo.getFilteredRows(rq);

        System.out.println("Length of filteredRows :: " + filteredRows.length);

        for (Row r : filteredRows) {
            if (!r.getKey().equals(this.getSlsTrnpVhclRegDtl1().getCurrentRow().getKey()))
                return true;
        }

        /*  if (filteredRows.length > 1)
            duplicate = true; */

        return duplicate;
    }

    public boolean vhclDescDuplicate(String vhclDesc) {
        Boolean duplicate = false;

        System.out.println("Parameter value of vhclDesc ::" + vhclDesc);

        ViewObjectImpl vo = this.getSlsTrnpVhclPrf();
        RowQualifier rq = new RowQualifier(vo);
        rq.setWhereClause("UPPER(VhclDesc) = UPPER('" + vhclDesc + "')");
        Row[] filteredRows = vo.getFilteredRows(rq);

        System.out.println("Length of filteredRows :: " + filteredRows.length);

        if (filteredRows.length > 0) {
            for (Row r : filteredRows) {

                if (!r.getKey().equals(this.getSlsTrnpVhclPrf1().getCurrentRow().getKey())) {
                    return true;
                }
            }
        } else {
            ViewObjectImpl itmVo = this.getAppItmPrf1();
            itmVo.setNamedWhereClauseParam("BindCldId", this.getCldIdValue());
            itmVo.setNamedWhereClauseParam("BindSlocId", this.getSlocIdValue());
            itmVo.setNamedWhereClauseParam("BindHoOrgId", this.getHoOrgIdValue());
            itmVo.executeQuery();
            RowQualifier itmRq = new RowQualifier(itmVo);
            itmRq.setWhereClause("UPPER(ItmDesc) = UPPER('" + vhclDesc + "') AND HoOrgId = " + this.getHoOrgIdValue());

            Row[] itmFilteredRows = itmVo.getFilteredRows(itmRq);

            System.out.println("Item Filtered Rows length :: " + itmFilteredRows.length);

            if (itmFilteredRows.length > 0)
                return true;

        }


        /*  if (filteredRows.length > 1)
            duplicate = true; */

        return duplicate;
    }

    /**
     * Container's getter for SlsTrnpVhclPrf1.
     * @return SlsTrnpVhclPrf1
     */
    public ViewObjectImpl getSlsTrnpVhclPrf() {
        return (ViewObjectImpl) findViewObject("SlsTrnpVhclPrf");
    }

    /**
     * Container's getter for SlsTrnpVhclRegDtl1.
     * @return SlsTrnpVhclRegDtl1
     */
    public ViewObjectImpl getSlsTrnpVhclRegDtl() {
        return (ViewObjectImpl) findViewObject("SlsTrnpVhclRegDtl");
    }

    /**
     * Container's getter for SlsTrnpVhclPrf2.
     * @return SlsTrnpVhclPrf2
     */
    public ViewObjectImpl getSlsTrnpVhclPrf1() {
        return (ViewObjectImpl) findViewObject("SlsTrnpVhclPrf1");
    }

    /**
     * Container's getter for SlsTrnpVhclRegDtl2.
     * @return SlsTrnpVhclRegDtl2
     */
    public ViewObjectImpl getSlsTrnpVhclRegDtl1() {
        return (ViewObjectImpl) findViewObject("SlsTrnpVhclRegDtl1");
    }

    /**
     * Container's getter for VhclPrfToVhclRegDtlVL1.
     * @return VhclPrfToVhclRegDtlVL1
     */
    public ViewLinkImpl getVhclPrfToVhclRegDtlVL1() {
        return (ViewLinkImpl) findViewLink("VhclPrfToVhclRegDtlVL1");
    }

    /**
     * Container's getter for LovUomId1.
     * @return LovUomId1
     */
    public ViewObjectImpl getLovUomId1() {
        return (ViewObjectImpl) findViewObject("LovUomId1");
    }

    /**
     * Container's getter for LovGroupName1.
     * @return LovGroupName1
     */
    public ViewObjectImpl getLovGroupName1() {
        return (ViewObjectImpl) findViewObject("LovGroupName1");
    }

    /**
     * Container's getter for LovVhclType1.
     * @return LovVhclType1
     */
    public ViewObjectImpl getLovVhclType1() {
        return (ViewObjectImpl) findViewObject("LovVhclType1");
    }

    /**
     * Container's getter for RadioOwnershipType1.
     * @return RadioOwnershipType1
     */
    public ViewObjectImpl getRadioOwnershipType1() {
        return (ViewObjectImpl) findViewObject("RadioOwnershipType1");
    }

    /**
     * Container's getter for LovUsername1.
     * @return LovUsername1
     */
    public ViewObjectImpl getLovUsername1() {
        return (ViewObjectImpl) findViewObject("LovUsername1");
    }

    /**
     * Container's getter for Search1.
     * @return Search1
     */
    public ViewObjectImpl getSearch1() {
        return (ViewObjectImpl) findViewObject("Search1");
    }

    /**
     * Container's getter for SearchResult1.
     * @return SearchResult1
     */
    public ViewObjectImpl getSearchResult1() {
        return (ViewObjectImpl) findViewObject("SearchResult1");
    }

    /**
     * Container's getter for LovSrNoVo1.
     * @return LovSrNoVo1
     */
    public ViewObjectImpl getLovSrNoVo1() {
        return (ViewObjectImpl) findViewObject("LovSrNoVo1");
    }

    /**
     * Container's getter for AppItmPrf1.
     * @return AppItmPrf1
     */
    public ViewObjectImpl getAppItmPrf1() {
        return (ViewObjectImpl) findViewObject("AppItmPrf1");
    }

    /**
     * Container's getter for LovTaxCatId1.
     * @return LovTaxCatId1
     */
    public ViewObjectImpl getLovTaxCatId1() {
        return (ViewObjectImpl) findViewObject("LovTaxCatId1");
    }

    public String chkDupliEngineNo(String engineNo) {
        RowQualifier rq = new RowQualifier(this.getSlsTrnpVhclRegDtl());
        rq.setWhereClause("upper(EngineNo) ='" + engineNo.trim().toUpperCase() + "'");
        Row fr[] = this.getSlsTrnpVhclRegDtl().getFilteredRows(rq);
        for (Row row : fr) {
            if (row.getAttribute("VhclRegNo") != null &&
                this.getSlsTrnpVhclRegDtl1().getCurrentRow().getAttribute("VhclRegNo") != null &&
                row.getAttribute("VhclRegNo").toString().equals(this.getSlsTrnpVhclRegDtl1().getCurrentRow().getAttribute("VhclRegNo").toString())) {
            } else if (this.getSlsTrnpVhclRegDtl1().getCurrentRow().getAttribute("VhclRegNo") == null) {
            } else
                return "Y";
        }
        return "N";
    }

    public String chkDupliChasisNo(String chasisNo) {
        RowQualifier rq = new RowQualifier(this.getSlsTrnpVhclRegDtl());
        rq.setWhereClause("upper(ChasisNo) ='" + chasisNo.trim().toUpperCase() + "'");
        Row fr[] = this.getSlsTrnpVhclRegDtl().getFilteredRows(rq);
        System.out.println("RQ statement=" + rq.getExprStr());
        System.out.println("Duplicate Rows=" + fr.length);
        for (Row row : fr) {
            System.out.println("fr[row].VhclRegNo" + row.getAttribute("VhclRegNo"));
            System.out.println("Curr.VhclRegNo=" +
                               this.getSlsTrnpVhclRegDtl1().getCurrentRow().getAttribute("VhclRegNo"));
            if (row.getAttribute("VhclRegNo") != null &&
                this.getSlsTrnpVhclRegDtl1().getCurrentRow().getAttribute("VhclRegNo") != null &&
                row.getAttribute("VhclRegNo").toString().equals(this.getSlsTrnpVhclRegDtl1().getCurrentRow().getAttribute("VhclRegNo").toString())) {
            } else if (this.getSlsTrnpVhclRegDtl1().getCurrentRow().getAttribute("VhclRegNo") == null) {
            } else
                return "Y";
        }
        return "N";
    }

    public String getUsrNm(Integer usrId) {
        this.getLovUsername1().setNamedWhereClauseParam("BindSlocId", EbizParams.GLBL_APP_SERV_LOC());
        this.getLovUsername1().executeQuery();
        Row[] fr = this.getLovUsername1().getFilteredRows("UsrId", usrId);
        if (fr.length > 0)
            return (String) fr[0].getAttribute("UsrName");
        else
            return "Anonymous";
    }

    public boolean slNoDuplicate(String slNo) {
        Boolean duplicate = false;

        System.out.println("Parameter value of serial no. ::" + slNo);

        ViewObjectImpl vo = this.getSlsTrnpVhclRegDtl1();
        RowQualifier rq = new RowQualifier(vo);
        rq.setWhereClause("UPPER(ItmSrNo) = UPPER('" + slNo + "')");
        Row[] filteredRows = vo.getFilteredRows(rq);

        System.out.println("Length of filteredRows :: " + filteredRows.length);

        if (filteredRows.length > 0) {
            for (Row r : filteredRows) {

                if (!r.getKey().equals(this.getSlsTrnpVhclRegDtl1().getCurrentRow().getKey())) {
                    return true;
                }
            }
        }

        return duplicate;
    }

    /**
     * Container's getter for LovTransporterNm1.
     * @return LovTransporterNm1
     */
    public ViewObjectImpl getLovTransporterNm1() {
        return (ViewObjectImpl) findViewObject("LovTransporterNm1");
    }

    /**
     * Container's getter for LovRouteTypeVO1.
     * @return LovRouteTypeVO1
     */
    public ViewObjectImpl getLovRouteTypeVO1() {
        return (ViewObjectImpl) findViewObject("LovRouteTypeVO1");
    }
}

