package hcmgroupprfapp.model.modules;

import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import hcmgroupprfapp.model.modules.common.HcmGrpPrfAM;
import hcmgroupprfapp.model.views.HcmExtraTimeGrpVOImpl;
import hcmgroupprfapp.model.views.OrgHcmGrpPrfVOImpl;

import java.sql.SQLException;
import java.sql.Types;

import java.util.HashSet;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.context.FacesContext;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewLinkImpl;


import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.context.RequestContext;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Aug 21 15:43:42 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class HcmGrpPrfAMImpl extends ApplicationModuleImpl implements HcmGrpPrfAM {
    /**
     * This is the default constructor (do not remove).
     */
    public HcmGrpPrfAMImpl() {
    }


    private static ADFLogger _log = ADFLogger.createADFLogger(HcmGrpPrfAMImpl.class);

    public void setBindVarOnPageLoad() {
        String cldId = EbizParams.GLBL_APP_CLD_ID();
        Integer slocId = EbizParams.GLBL_APP_SERV_LOC();
        String hoOrgId = EbizParams.GLBL_HO_ORG_ID();
        String orgId = EbizParams.GLBL_APP_USR_ORG();
        RequestContext.getCurrentInstance().getPageFlowScope().put("ADD_EDIT_MODE", "V");
        this.getGrpVO().setNamedWhereClauseParam("cldIdBindVar", cldId);
        this.getGrpVO().setNamedWhereClauseParam("slocIdBindVar", slocId);
        this.getGrpVO().setNamedWhereClauseParam("hoOrgIdBindVar", hoOrgId);
        this.getGrpVO().setNamedWhereClauseParam("orgIdBindVar", orgId);
        this.getGrpVO().executeQuery();

        this.getOrg().setNamedWhereClauseParam("cldIdbindVar", cldId);
        this.getOrg().setNamedWhereClauseParam("hoOrgIdBindVar", hoOrgId);
        this.getOrg().executeQuery();

        getOrgHcmProfile1().setNamedWhereClauseParam("BindCldId", cldId);
        getOrgHcmProfile1().setNamedWhereClauseParam("BindOrgId", orgId);
        getOrgHcmProfile1().setNamedWhereClauseParam("BindSlocId", slocId);
        getOrgHcmProfile1().executeQuery();

    }

    public void searchGrp() {
        this.getGrpVO().setNamedWhereClauseParam("grpNameBindVar",
                                                 this.getSearchDual().getCurrentRow().getAttribute("TransEmpGrp"));
        this.getGrpVO().executeQuery();

    }

    public void generateDocId() {
        String cldId = EbizParams.GLBL_APP_CLD_ID();
        Integer slocId = EbizParams.GLBL_APP_SERV_LOC();
        String orgId = EbizParams.GLBL_APP_USR_ORG();
        Integer userId = EbizParams.GLBL_APP_USR();
        ViewObjectImpl extraTimeVo = this.getHcmExtraTimeGrp();
        StringBuilder sbul = new StringBuilder("app.get_txn_id (?,?,?,?,?,?)");
        Object[] obj = { cldId, slocId, orgId, userId, 28503, 0 };
        String docId = (String) ADFModelUtils.callFunction(this, sbul, obj, Types.VARCHAR);
        extraTimeVo.getCurrentRow().setAttribute("DocId", docId);
    }


    public String chkPrfForHoOrg() {
        String cldId = EbizParams.GLBL_APP_CLD_ID();
        Integer slocId = EbizParams.GLBL_APP_SERV_LOC();
        String hoOrgId = EbizParams.GLBL_HO_ORG_ID();
        RowQualifier rq = new RowQualifier(this.getOrgHcmGrpPrf1());
        rq.setWhereClause("CldId='" + cldId + "' and SlocId=" + slocId + " and HoOrgId='" + hoOrgId + "' and OrgId='" +
                          hoOrgId + "' and GrpId='" + this.getGrpVO().getCurrentRow().getAttribute("ParamId") + "'");
        if (this.getOrgHcmGrpPrf1().getFilteredRows(rq).length > 0)
            return "Y";
        else
            return "N";
    }

    public void insertFromHoOrg() {
        String cldId = EbizParams.GLBL_APP_CLD_ID();
        Integer slocId = EbizParams.GLBL_APP_SERV_LOC();
        String hoOrgId = EbizParams.GLBL_HO_ORG_ID();
        String orgId = EbizParams.GLBL_APP_USR_ORG();
        RowQualifier rq = new RowQualifier(this.getOrgHcmGrpPrf());
        rq.setWhereClause("CldId='" + cldId + "' and SlocId=" + slocId + " and HoOrgId='" + hoOrgId + "' and OrgId='" +
                          hoOrgId + "' and GrpId='" + this.getGrpVO().getCurrentRow().getAttribute("ParamId") + "'");
        if (this.getOrgHcmGrpPrf().getFilteredRows(rq).length > 0) {
            Row r[] = this.getOrgHcmGrpPrf().getFilteredRows(rq);
            this.getOrgHcmGrpPrf().getCurrentRow().setAttribute("CldId", cldId);
            this.getOrgHcmGrpPrf().getCurrentRow().setAttribute("SlocId", slocId);
            this.getOrgHcmGrpPrf().getCurrentRow().setAttribute("HoOrgId", hoOrgId);
            this.getOrgHcmGrpPrf().getCurrentRow().setAttribute("orgId", orgId);
            this.getOrgHcmGrpPrf().getCurrentRow().setAttribute("GrpId",
                                                                this.getGrpVO().getCurrentRow().getAttribute("ParamId"));
            this.getOrgHcmGrpPrf().getCurrentRow().setAttribute("AttenSkipCh", r[0].getAttribute("AttenSkipCh"));
            this.getOrgHcmGrpPrf().getCurrentRow().setAttribute("AttenUploadCh", r[0].getAttribute("AttenUploadCh"));
            this.getOrgHcmGrpPrf().getCurrentRow().setAttribute("ExtraTimeRule", r[0].getAttribute("ExtraTimeRule"));
            this.getOrgHcmGrpPrf().getCurrentRow().setAttribute("GrpDesc", r[0].getAttribute("GrpDesc"));
            this.getOrgHcmGrpPrf().getCurrentRow().setAttribute("SalProcFreq", r[0].getAttribute("SalProcFreq"));
        }
    }


    public String isOrgItselfHoOrg() {
        String cldId = EbizParams.GLBL_APP_CLD_ID();
        String orgId = EbizParams.GLBL_APP_USR_ORG();

        RowQualifier rq = new RowQualifier(this.getOrg());
        rq.setWhereClause("OrgCldId='" + cldId + "' and OrgId='" + orgId + "' and HoOrgId='" + orgId + "'");
        Row[] r = this.getOrg().getFilteredRows(rq);
        if (r.length > 0)
            return "Y";
        else
            return "N";
    }

    public void resetSearch() {
        this.getSearchDual().getCurrentRow().setAttribute("TransEmpGrp", null);
        this.getGrpVO().setNamedWhereClauseParam("grpNameBindVar", null);
        this.getGrpVO().executeQuery();
    }

    public Object resolvEl(String data) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, data, Object.class);
        return exp.getValue(elContext);
    }


    public String etrFrmDateValidator(Timestamp ts) {
        ViewObjectImpl orgPrf = this.getOrgHcmGrpPrf();
        if (orgPrf.getCurrentRow().getAttribute("ValidStrtDt") != null &&
            orgPrf.getCurrentRow().getAttribute("ValidStrtDt").toString().length() > 0) {
            Timestamp orgStartdate = (Timestamp) orgPrf.getCurrentRow().getAttribute("ValidStrtDt");
            try {
                if (orgStartdate.dateValue().after(ts.dateValue())) {
                    return "Y";
                }
            } catch (SQLException e) {
            }
        }
        return "N";
    }

    public String etrToDtValid(Timestamp st) {
        ViewObjectImpl orgPrf = this.getOrgHcmGrpPrf();
        if (this.getHcmExtraTimeGrp().getCurrentRow().getAttribute("ValidStrtDt") != null &&
            this.getHcmExtraTimeGrp().getCurrentRow().getAttribute("ValidStrtDt").toString().length() > 0) {
            if (orgPrf.getCurrentRow().getAttribute("ValidEndDt") != null &&
                orgPrf.getCurrentRow().getAttribute("ValidEndDt").toString().length() > 0) {
                Timestamp prfEnddate = (Timestamp) orgPrf.getCurrentRow().getAttribute("ValidEndDt");

                try {
                    if (prfEnddate.dateValue().before(st.dateValue())) {
                        return "Y";
                    }
                } catch (SQLException e) {
                }
            }
        }
        return "N";
    }

    public String chkDuplicateShift(String shiftId) {

        String cldId = EbizParams.GLBL_APP_CLD_ID();
        Integer slocId = EbizParams.GLBL_APP_SERV_LOC();
        String hoOrgId = EbizParams.GLBL_HO_ORG_ID();
        String orgId = EbizParams.GLBL_APP_USR_ORG();
        RowQualifier rq = new RowQualifier(this.getHcmShiftGrp());
        rq.setWhereClause("CldId='" + cldId + "' and SlocId=" + slocId + " and HoOrgId='" + hoOrgId + "' and OrgId='" +
                          orgId + "' and GrpId='" + this.getOrgHcmGrpPrf().getCurrentRow().getAttribute("GrpId") +
                          "' and ShiftId='" + shiftId + "'");
        Row[] fr = this.getHcmShiftGrp().getFilteredRows(rq);
        for (Row r : fr) {
            if (!(r.equals(this.getHcmShiftGrp().getCurrentRow())))
                return "N";
        }
        return "Y";
    }

    public String chkDupliSalaryId(String salId) {
        String cldId = EbizParams.GLBL_APP_CLD_ID();
        Integer slocId = EbizParams.GLBL_APP_SERV_LOC();
        String hoOrgId = EbizParams.GLBL_HO_ORG_ID();
        String orgId = EbizParams.GLBL_APP_USR_ORG();
        String doc_id = (String) this.getHcmExtraTimeGrp().getCurrentRow().getAttribute("DocId");
        String grp_id = (String) this.getHcmExtraTimeGrp().getCurrentRow().getAttribute("GrpId");
        RowQualifier rq = new RowQualifier(this.getHcmExtraTimeSal());
        rq.setWhereClause("CldId='" + cldId + "' and SlocId=" + slocId + " and HoOrgId='" + hoOrgId + "' and OrgId='" +
                          orgId + "' and SalId='" + salId + "' and GrpId='" + grp_id + "' and DocId='" + doc_id + "'");
        System.out.println("Query=" + rq.getExprStr());
        Row fr[] = this.getHcmExtraTimeSal().getFilteredRows(rq);
        for (Row r : fr) {
            if (!(r.equals(this.getHcmExtraTimeSal().getCurrentRow())))
                return "Y";
        }
        return "N";
    }


    public String chkGrpValidStrtdt(Timestamp strtDt) {
        Timestamp grpStrtDt = (Timestamp) this.getGrpVO().getCurrentRow().getAttribute("ParamStartDt");
        if (strtDt != null && strtDt.toString().length() > 0) {
            java.sql.Date strt = null;
            java.sql.Date end = null;
            if (grpStrtDt != null) {
                try {
                    strt = grpStrtDt.dateValue();
                    end = strtDt.dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strt.compareTo(end) > 0) {
                    if (strt.toString().equals(end.toString())) {
                    } else {
                        return "N";
                    }
                }
            }
        }
        return "Y";
    }


    public String chkEtrTypValidStrtdt(Timestamp strtDt) {
        /*  this.getLovExtraTimeType1().setNamedWhereClauseParam("cldIdBindVar",
                                                             this.getHcmExtraTimeGrp().getCurrentRow().getAttribute("CldId"));
        this.getLovExtraTimeType1().setNamedWhereClauseParam("hoOrgIdBindVar",
                                                             this.getHcmExtraTimeGrp().getCurrentRow().getAttribute("HoOrgId"));
        this.getLovExtraTimeType1().setNamedWhereClauseParam("orgIdBindVar",
                                                             this.getHcmExtraTimeGrp().getCurrentRow().getAttribute("OrgId"));
        this.getLovExtraTimeType1().setNamedWhereClauseParam("slocIdBindVar",
                                                             this.getHcmExtraTimeGrp().getCurrentRow().getAttribute("SlocId"));
        this.getLovExtraTimeType1().executeQuery();
        Row[] fr =
            this.getLovExtraTimeType1().getFilteredRows("ParamId",
                                                        this.getHcmExtraTimeGrp().getCurrentRow().getAttribute("RuleType"));
        Timestamp etrStrtDt = null;
        if (fr.length > 0) {
            etrStrtDt = (Timestamp) fr[0].getAttribute("ParamStartDt");
        }

        if (strtDt != null && strtDt.toString().length() > 0) {
            java.sql.Date strt = null;
            java.sql.Date end = null;
            if (etrStrtDt != null) {
                try {
                    strt = etrStrtDt.dateValue();
                    end = strtDt.dateValue();
                } catch (SQLException e) {
                    System.out.println(e.getStackTrace());
                }
                if (strt.compareTo(end) > 0) {
                    if (strt.toString().equals(end.toString())) {
                    } else {
                        return "N";
                    }
                }
            }
        } */
        return "Y";
    }


    public String chkShiftValidStrtdt(Timestamp strtDt) {
        if (this.getHcmShiftGrp().getCurrentRow().getAttribute("ShiftId") != null) {
            this.getLovShiftId().setNamedWhereClauseParam("cldIdBindVar",
                                                          this.getHcmShiftGrp().getCurrentRow().getAttribute("CldId"));
            this.getLovShiftId().setNamedWhereClauseParam("hoOrgIdBindVar",
                                                          this.getHcmShiftGrp().getCurrentRow().getAttribute("HoOrgId"));
            this.getLovShiftId().setNamedWhereClauseParam("orgIdBindVar",
                                                          this.getHcmShiftGrp().getCurrentRow().getAttribute("OrgId"));
            this.getLovShiftId().setNamedWhereClauseParam("slocIdBindVar",
                                                          this.getHcmShiftGrp().getCurrentRow().getAttribute("SlocId"));
            this.getLovShiftId().executeQuery();
            Row[] fr =
                this.getLovShiftId().getFilteredRows("ShiftId",
                                                     this.getHcmShiftGrp().getCurrentRow().getAttribute("ShiftId"));
            Timestamp validStrtDt = null;
            if (fr.length > 0) {
                validStrtDt = (Timestamp) fr[0].getAttribute("ValidStrtDt");
            }

            if (strtDt != null && strtDt.toString().length() > 0) {
                java.sql.Date strt = null;
                java.sql.Date end = null;
                if (validStrtDt != null) {
                    try {
                        strt = validStrtDt.dateValue();
                        end = strtDt.dateValue();
                    } catch (SQLException e) {
                        System.out.println(e.getStackTrace());
                    }
                    if (strt.compareTo(end) > 0) {
                        if (strt.toString().equals(end.toString())) {
                        } else {
                            return "N";
                        }
                    }
                }
            }

            Timestamp validEndDt = null;
            if (fr.length > 0) {
                validEndDt = (Timestamp) fr[0].getAttribute("ValidEndDt");
            }

            if (strtDt != null && strtDt.toString().length() > 0) {
                java.sql.Date strt = null;
                java.sql.Date end = null;
                if (validEndDt != null) {
                    try {
                        strt = strtDt.dateValue();
                        end = validEndDt.dateValue();
                    } catch (SQLException e) {
                        System.out.println(e.getStackTrace());
                    }
                    if (strt.compareTo(end) > 0) {
                        if (strt.toString().equals(end.toString())) {
                        } else {
                            return "N";
                        }
                    }
                }
            }
        }
        return "Y";
    }

    /**To Validate duplicate comp rule type*/
    public String chkDuplicateCompRuleType(Integer compRuleId) {
        String cldId = EbizParams.GLBL_APP_CLD_ID();
        Integer slocId = EbizParams.GLBL_APP_SERV_LOC();
        String hoOrgId = EbizParams.GLBL_HO_ORG_ID();
        String orgId = EbizParams.GLBL_APP_USR_ORG();
        RowQualifier rq = new RowQualifier(getHcmGrpCompOffVO1());
        rq.setWhereClause("CldId='" + cldId + "' and SlocId=" + slocId + " and HoOrgId='" + hoOrgId + "' and OrgId='" +
                          orgId + "' and CompRuleType=" + compRuleId + " and GrpId='" +
                          this.getOrgHcmGrpPrf().getCurrentRow().getAttribute("GrpId") + "'");
        System.out.println("Query=" + rq.getExprStr());
        Row fr[] = getHcmGrpCompOffVO1().getFilteredRows(rq);
        System.out.println("Total no. of same rows=" + fr.length);
        for (Row r : fr) {
            System.out.println("Row=" + r + " and current row=" + this.getHcmGrpCompOffVO1().getCurrentRow());
            if (!(r.equals(this.getHcmGrpCompOffVO1().getCurrentRow())))
                return "Y";
        }
        return "N";
    }

    /**
     * Container's getter for GrpVO.
     * @return GrpVO
     */
    public ViewObjectImpl getGrpVO() {
        return (ViewObjectImpl) findViewObject("GrpVO");
    }

    /**
     * Container's getter for SearchDual.
     * @return SearchDual
     */
    public ViewObjectImpl getSearchDual() {
        return (ViewObjectImpl) findViewObject("SearchDual");
    }

    /**
     * Container's getter for OrgHcmGrpPrf.
     * @return OrgHcmGrpPrf
     */
    public OrgHcmGrpPrfVOImpl getOrgHcmGrpPrf() {
        return (OrgHcmGrpPrfVOImpl) findViewObject("OrgHcmGrpPrf");
    }

    /**
     * Container's getter for GrpToOrgHcmGrpPrfVL1.
     * @return GrpToOrgHcmGrpPrfVL1
     */
    public ViewLinkImpl getGrpToOrgHcmGrpPrfVL1() {
        return (ViewLinkImpl) findViewLink("GrpToOrgHcmGrpPrfVL1");
    }

    /**
     * Container's getter for HcmExtraTimeGrp1.
     * @return HcmExtraTimeGrp1
     */
    public HcmExtraTimeGrpVOImpl getHcmExtraTimeGrp() {
        return (HcmExtraTimeGrpVOImpl) findViewObject("HcmExtraTimeGrp");
    }

    /**
     * Container's getter for FkHcmExtraTimeGrpVL1.
     * @return FkHcmExtraTimeGrpVL1
     */
    public ViewLinkImpl getFkHcmExtraTimeGrpVL1() {
        return (ViewLinkImpl) findViewLink("FkHcmExtraTimeGrpVL1");
    }

    /**
     * Container's getter for Org1.
     * @return Org1
     */
    public ViewObjectImpl getOrg() {
        return (ViewObjectImpl) findViewObject("Org");
    }


    /**
     * Container's getter for HcmShiftGrp2.
     * @return HcmShiftGrp2
     */
    public ViewObjectImpl getHcmShiftGrp() {
        return (ViewObjectImpl) findViewObject("HcmShiftGrp");
    }

    /**
     * Container's getter for GrpToShiftVL1.
     * @return GrpToShiftVL1
     */
    public ViewLinkImpl getGrpToShiftVL1() {
        return (ViewLinkImpl) findViewLink("GrpToShiftVL1");
    }

    /**
     * Container's getter for LovShiftId1.
     * @return LovShiftId1
     */
    public ViewObjectImpl getLovShiftId() {
        return (ViewObjectImpl) findViewObject("LovShiftId");
    }

    /**
     * Container's getter for LovExtraTimeType1.
     * @return LovExtraTimeType1
     */
    public ViewObjectImpl getLovExtraTimeType1() {
        return (ViewObjectImpl) findViewObject("LovExtraTimeType1");
    }

    /**
     * Container's getter for OrgHcmGrpPrf1.
     * @return OrgHcmGrpPrf1
     */
    public OrgHcmGrpPrfVOImpl getOrgHcmGrpPrf1() {
        return (OrgHcmGrpPrfVOImpl) findViewObject("OrgHcmGrpPrf1");
    }

    /**
     * Container's getter for GrpUsage1.
     * @return GrpUsage1
     */
    public ViewObjectImpl getGrpUsage() {
        return (ViewObjectImpl) findViewObject("GrpUsage");
    }

    /**
     * Container's getter for GrpShiftUsage1.
     * @return GrpShiftUsage1
     */
    public ViewObjectImpl getGrpShiftUsage() {
        return (ViewObjectImpl) findViewObject("GrpShiftUsage");
    }

    /**
     * Container's getter for OrgHcmProfile1.
     * @return OrgHcmProfile1
     */
    public ViewObjectImpl getOrgHcmProfile1() {
        return (ViewObjectImpl) findViewObject("OrgHcmProfile1");
    }

    /**
     * Container's getter for HcmGrpCompOffVO1.
     * @return HcmGrpCompOffVO1
     */
    public ViewObjectImpl getHcmGrpCompOffVO1() {
        return (ViewObjectImpl) findViewObject("HcmGrpCompOffVO1");
    }

    /**
     * Container's getter for GrpPrfToCompOffVL1.
     * @return GrpPrfToCompOffVL1
     */
    public ViewLinkImpl getGrpPrfToCompOffVL1() {
        return (ViewLinkImpl) findViewLink("GrpPrfToCompOffVL1");
    }

    /**
     * Container's getter for OrgHcmLeaveVO1.
     * @return OrgHcmLeaveVO1
     */
    public ViewObjectImpl getOrgHcmLeaveVO1() {
        return (ViewObjectImpl) findViewObject("OrgHcmLeaveVO1");
    }

    /**
     * Container's getter for HcmLeaveGroupVO1.
     * @return HcmLeaveGroupVO1
     */
    public ViewObjectImpl getHcmLeaveGroupVO1() {
        return (ViewObjectImpl) findViewObject("HcmLeaveGroupVO1");
    }


    /**
     * Container's getter for LovSalCompName1.
     * @return LovSalCompName1
     */
    public ViewObjectImpl getLovSalCompName1() {
        String cldId = EbizParams.GLBL_APP_CLD_ID();
        Integer slocId = EbizParams.GLBL_APP_SERV_LOC();
        String hoOrgId = EbizParams.GLBL_HO_ORG_ID();
        String orgId = EbizParams.GLBL_APP_USR_ORG();
        ViewObjectImpl vo = (ViewObjectImpl) findViewObject("LovSalCompName1");
        vo.setNamedWhereClauseParam("bindCldId", cldId);
        vo.setNamedWhereClauseParam("bindHoOrgId", hoOrgId);
        vo.setNamedWhereClauseParam("BindOrgId", orgId);
        vo.setNamedWhereClauseParam("bindSlocId", slocId);
        vo.executeQuery();
        return vo;
        //return (ViewObjectImpl) findViewObject("LovSalCompName1");
    }

    public String chkDuplicateGratSlryCmponent(String salId) {
        if (this.getOrgHcmGrpPrf().getCurrentRow() != null) {

            String hoOrgId = EbizParams.GLBL_HO_ORG_ID();
            String cldId = EbizParams.GLBL_APP_CLD_ID();
            Integer slocId = EbizParams.GLBL_APP_SERV_LOC();
            String orgId = EbizParams.GLBL_APP_USR_ORG();
            RowQualifier rq = new RowQualifier(this.getHcmGrpGrtSal1());
            rq.setWhereClause("CldId='" + cldId + "' and SlocId=" + slocId + " and HoOrgId='" + hoOrgId +
                              "' and OrgId='" + orgId + "'  and SalId='" + salId + "' and GrpId='" +
                              this.getHcmGrpGrtSal1().getCurrentRow().getAttribute("GrpId").toString() + "'");
            System.out.println("Query=" + rq.getExprStr());
            Row fr[] = this.getHcmGrpGrtSal1().getFilteredRows(rq);
            System.out.println("Total no. of same rows=" + fr.length);
            for (Row r : fr) {
                System.out.println("Row=" + r + " and current row=" + this.getHcmGrpGrtSal1().getCurrentRow());
                if (!(r.equals(this.getHcmGrpGrtSal1().getCurrentRow())))
                    return "Y";
            }
            return "N";
        }
        return "N";

    }

    public String chkDuplicateBonusSlryCmponent(String salId) {
        if (this.getOrgHcmGrpPrf().getCurrentRow() != null) {

            String hoOrgId = EbizParams.GLBL_HO_ORG_ID();
            String cldId = EbizParams.GLBL_APP_CLD_ID();
            Integer slocId = EbizParams.GLBL_APP_SERV_LOC();
            String orgId = EbizParams.GLBL_APP_USR_ORG();
            RowQualifier rq = new RowQualifier(this.getHcmGrpBonusSal1());
            rq.setWhereClause("CldId='" + cldId + "' and SlocId=" + slocId + " and HoOrgId='" + hoOrgId +
                              "' and OrgId='" + orgId + "'  and SalId='" + salId + "' and GrpId='" +
                              this.getHcmGrpBonusSal1().getCurrentRow().getAttribute("GrpId").toString() + "'");
            System.out.println("Query=" + rq.getExprStr());
            Row fr[] = this.getHcmGrpBonusSal1().getFilteredRows(rq);
            System.out.println("Total no. of same rows=" + fr.length);
            for (Row r : fr) {
                System.out.println("Row=" + r + " and current row=" + this.getHcmGrpBonusSal1().getCurrentRow());
                if (!(r.equals(this.getHcmGrpBonusSal1().getCurrentRow())))
                    return "Y";
            }
            return "N";
        }
        return "N";

    }

    public String chkDuplicateLTASlryCmponent(String salId) {
        if (this.getOrgHcmGrpPrf().getCurrentRow() != null) {

            String hoOrgId = EbizParams.GLBL_HO_ORG_ID();
            String cldId = EbizParams.GLBL_APP_CLD_ID();
            Integer slocId = EbizParams.GLBL_APP_SERV_LOC();
            String orgId = EbizParams.GLBL_APP_USR_ORG();
            RowQualifier rq = new RowQualifier(this.getHcmGrpLTASalVO1());
            rq.setWhereClause("CldId='" + cldId + "' and SlocId=" + slocId + " and HoOrgId='" + hoOrgId +
                              "' and OrgId='" + orgId + "'  and SalId='" + salId + "' and GrpId='" +
                              this.getHcmGrpLTASalVO1().getCurrentRow().getAttribute("GrpId").toString() + "'");
            System.out.println("Query=" + rq.getExprStr());
            Row fr[] = this.getHcmGrpLTASalVO1().getFilteredRows(rq);
            System.out.println("Total no. of same rows=" + fr.length);
            for (Row r : fr) {
                System.out.println("Row=" + r + " and current row=" + this.getHcmGrpLTASalVO1().getCurrentRow());
                if (!(r.equals(this.getHcmGrpLTASalVO1().getCurrentRow())))
                    return "Y";
            }
            return "N";
        }
        return "N";

    }


    public String chkDuplicateLTALeave(String leaveId) {
        if (this.getOrgHcmGrpPrf().getCurrentRow() != null) {

            String hoOrgId = EbizParams.GLBL_HO_ORG_ID();
            String cldId = EbizParams.GLBL_APP_CLD_ID();
            Integer slocId = EbizParams.GLBL_APP_SERV_LOC();
            String orgId = EbizParams.GLBL_APP_USR_ORG();
            RowQualifier rq = new RowQualifier(this.getHcmGrpLTALeaveVO1());
            rq.setWhereClause("CldId='" + cldId + "' and SlocId=" + slocId + " and HoOrgId='" + hoOrgId +
                              "' and OrgId='" + orgId + "'  and LeaveId='" + leaveId + "' and GrpId='" +
                              this.getHcmGrpLTALeaveVO1().getCurrentRow().getAttribute("GrpId").toString() + "'");
            System.out.println("Query=" + rq.getExprStr());
            Row fr[] = this.getHcmGrpLTALeaveVO1().getFilteredRows(rq);
            System.out.println("Total no. of same rows=" + fr.length);
            for (Row r : fr) {
                System.out.println("Row=" + r + " and current row=" + this.getHcmGrpLTALeaveVO1().getCurrentRow());
                if (!(r.equals(this.getHcmGrpLTALeaveVO1().getCurrentRow())))
                    return "Y";
            }
            return "N";
        }
        return "N";

    }

    public String chkDupliSalCompoForleavLibility(String salId) {

        String cldId = EbizParams.GLBL_APP_CLD_ID();
        Integer slocId = EbizParams.GLBL_APP_SERV_LOC();
        String hoOrgId = EbizParams.GLBL_HO_ORG_ID();
        String orgId = EbizParams.GLBL_APP_USR_ORG();
        RowQualifier rq = new RowQualifier(this.getHcmGrpLveLybltyCalcVO1());
        rq.setWhereClause("CldId='" + cldId + "' and SlocId=" + slocId + " and HoOrgId='" + hoOrgId + "' and OrgId='" +
                          orgId + "' and GrpId='" + this.getOrgHcmGrpPrf().getCurrentRow().getAttribute("GrpId") +
                          "' and SalId='" + salId + "'");
        Row[] fr = this.getHcmGrpLveLybltyCalcVO1().getFilteredRows(rq);
        for (Row r : fr) {
            if (!(r.equals(this.getHcmGrpLveLybltyCalcVO1().getCurrentRow())))
                return "N";
        }
        return "Y";
    }

    /**
     * Container's getter for HcmGrpGratCal1.
     * @return HcmGrpGratCal1
     */
    public ViewObjectImpl getHcmGrpGratCal1() {
        return (ViewObjectImpl) findViewObject("HcmGrpGratCal1");
    }

    /**
     * Container's getter for HcmGrpPrfToGrpGratCalVL1.
     * @return HcmGrpPrfToGrpGratCalVL1
     */
    public ViewLinkImpl getHcmGrpPrfToGrpGratCalVL1() {
        return (ViewLinkImpl) findViewLink("HcmGrpPrfToGrpGratCalVL1");
    }


    /**
     * Container's getter for HcmGrpGrtSal3.
     * @return HcmGrpGrtSal3
     */
    public ViewObjectImpl getHcmGrpGrtSal1() {
        return (ViewObjectImpl) findViewObject("HcmGrpGrtSal1");
    }

    /**
     * Container's getter for HcmGrpGratSalCompVL2.
     * @return HcmGrpGratSalCompVL2
     */
    public ViewLinkImpl getHcmGrpGratSalCompVL2() {
        return (ViewLinkImpl) findViewLink("HcmGrpGratSalCompVL2");
    }

    /**
     * Container's getter for HcmExtraTimeSal1.
     * @return HcmExtraTimeSal1
     */
    public ViewObjectImpl getHcmExtraTimeSal() {
        return (ViewObjectImpl) findViewObject("HcmExtraTimeSal");
    }

    /**
     * Container's getter for HcmGrpExtraTimeToExtraTimeSal1.
     * @return HcmGrpExtraTimeToExtraTimeSal1
     */
    public ViewLinkImpl getHcmGrpExtraTimeToExtraTimeSal1() {
        return (ViewLinkImpl) findViewLink("HcmGrpExtraTimeToExtraTimeSal1");
    }

    /**
     * Container's getter for HcmGrpLwf1.
     * @return HcmGrpLwf1
     */
    public ViewObjectImpl getHcmGrpLwf() {
        return (ViewObjectImpl) findViewObject("HcmGrpLwf");
    }

    /**
     * Container's getter for HcmGrpPrfToHcmGrpLwfVL1.
     * @return HcmGrpPrfToHcmGrpLwfVL1
     */
    public ViewLinkImpl getHcmGrpPrfToHcmGrpLwfVL1() {
        return (ViewLinkImpl) findViewLink("HcmGrpPrfToHcmGrpLwfVL1");
    }


    /**
     * Container's getter for HcmGrpBonsExgratia1.
     * @return HcmGrpBonsExgratia1
     */
    public ViewObjectImpl getHcmGrpBonsExgratia() {
        return (ViewObjectImpl) findViewObject("HcmGrpBonsExgratia");
    }

    /**
     * Container's getter for HcmGrpPrftoHcmGrpBonsGratuityVL1.
     * @return HcmGrpPrftoHcmGrpBonsGratuityVL1
     */
    public ViewLinkImpl getHcmGrpPrftoHcmGrpBonsGratuityVL1() {
        return (ViewLinkImpl) findViewLink("HcmGrpPrftoHcmGrpBonsGratuityVL1");
    }

    /**
     * Container's getter for HcmGrpBonusSal2.
     * @return HcmGrpBonusSal2
     */
    public ViewObjectImpl getHcmGrpBonusSal1() {
        return (ViewObjectImpl) findViewObject("HcmGrpBonusSal1");
    }

    /**
     * Container's getter for HcmGrpBonusSalCompVL1.
     * @return HcmGrpBonusSalCompVL1
     */
    public ViewLinkImpl getHcmGrpBonusSalCompVL1() {
        return (ViewLinkImpl) findViewLink("HcmGrpBonusSalCompVL1");
    }

    /**
     * Container's getter for OrgHcmPrf1.
     * @return OrgHcmPrf1
     */
    public ViewObjectImpl getOrgHcmPrf() {
        String cldId = EbizParams.GLBL_APP_CLD_ID();
        Integer slocId = EbizParams.GLBL_APP_SERV_LOC();
        String orgId = EbizParams.GLBL_APP_USR_ORG();
        ViewObjectImpl vo = (ViewObjectImpl) findViewObject("OrgHcmPrf");
        vo.setNamedWhereClauseParam("BINDCLD_ID", cldId);
        vo.setNamedWhereClauseParam("BINDORG_ID", orgId);
        vo.setNamedWhereClauseParam("BINDSLOC_ID", slocId);
        vo.executeQuery();
        return vo;
        //return (ViewObjectImpl) findViewObject("OrgHcmPrf");
    }

    /**
     * Container's getter for CheckForMpesaVO1.
     * @return CheckForMpesaVO1
     */
    public ViewObjectImpl getCheckForMpesaVO1() {
        return (ViewObjectImpl) findViewObject("CheckForMpesaVO1");
    }

    /**
     * Container's getter for HcmGrpLveLybltyCalcVO1.
     * @return HcmGrpLveLybltyCalcVO1
     */
    public ViewObjectImpl getHcmGrpLveLybltyCalcVO1() {
        return (ViewObjectImpl) findViewObject("HcmGrpLveLybltyCalcVO1");
    }

    /**
     * Container's getter for GroupProfileTOLeaveLiblityCalcVL1.
     * @return GroupProfileTOLeaveLiblityCalcVL1
     */
    public ViewLinkImpl getGroupProfileTOLeaveLiblityCalcVL1() {
        return (ViewLinkImpl) findViewLink("GroupProfileTOLeaveLiblityCalcVL1");
    }


    /**
     * Container's getter for LovLeaveNmVO1.
     * @return LovLeaveNmVO1
     */
    public ViewObjectImpl getLovLeaveNmVO1() {
        String cldId = EbizParams.GLBL_APP_CLD_ID();
        Integer slocId = EbizParams.GLBL_APP_SERV_LOC();
        String hoOrgId = EbizParams.GLBL_HO_ORG_ID();
        String orgId = EbizParams.GLBL_APP_USR_ORG();
        ViewObjectImpl vo = (ViewObjectImpl) findViewObject("LovLeaveNmVO1");
        vo.setNamedWhereClauseParam("cldIdBindVar", cldId);
        vo.setNamedWhereClauseParam("hoOrgIdBindVar", hoOrgId);
        vo.setNamedWhereClauseParam("orgIdBindVar", orgId);
        vo.setNamedWhereClauseParam("slocIdBindVar", slocId);
        vo.executeQuery();
        return vo;
        //return (ViewObjectImpl) findViewObject("LovLeaveNmVO1");
    }


    /**
     * Container's getter for HcmGrpLTAVO3.
     * @return HcmGrpLTAVO3
     */
    public ViewObjectImpl getHcmGrpLTAVO1() {
        return (ViewObjectImpl) findViewObject("HcmGrpLTAVO1");
    }

    /**
     * Container's getter for HcmGrpPrfToHcmGrpLtaVL2.
     * @return HcmGrpPrfToHcmGrpLtaVL2
     */
    public ViewLinkImpl getHcmGrpPrfToHcmGrpLtaVL2() {
        return (ViewLinkImpl) findViewLink("HcmGrpPrfToHcmGrpLtaVL2");
    }

    /**
     * Container's getter for HcmGrpLTALeaveVO1.
     * @return HcmGrpLTALeaveVO1
     */
    public ViewObjectImpl getHcmGrpLTALeaveVO1() {
        return (ViewObjectImpl) findViewObject("HcmGrpLTALeaveVO1");
    }

    /**
     * Container's getter for HcmGrpLtaToGrpLeaveVL1.
     * @return HcmGrpLtaToGrpLeaveVL1
     */
    public ViewLinkImpl getHcmGrpLtaToGrpLeaveVL1() {
        return (ViewLinkImpl) findViewLink("HcmGrpLtaToGrpLeaveVL1");
    }


    /**
     * Container's getter for HcmGrpLTASalVO2.
     * @return HcmGrpLTASalVO2
     */
    public ViewObjectImpl getHcmGrpLTASalVO1() {
        return (ViewObjectImpl) findViewObject("HcmGrpLTASalVO1");
    }

    /**
     * Container's getter for GrpLtaToGrpLtaSalCompVL2.
     * @return GrpLtaToGrpLtaSalCompVL2
     */
    public ViewLinkImpl getGrpLtaToGrpLtaSalCompVL2() {
        return (ViewLinkImpl) findViewLink("GrpLtaToGrpLtaSalCompVL2");
    }

    public String validateLegacyCode(String Legacy) {
        System.out.println("Legacy==" + Legacy);
        ViewObjectImpl ln = getOrgHcmGrpPrf1();
        Row currentRow = getOrgHcmGrpPrf().getCurrentRow();
        RowSetIterator rsi = ln.createRowSetIterator(null);
        HashSet legSet = new HashSet();
           while (rsi.hasNext()) {
            System.out.println("while");
            Row next = rsi.next();
            if (next.getAttribute("LegacyCode") != null) {
                if (!(next.getKey().equals(currentRow.getKey()))) {
                    if (legSet.contains(Legacy)) {
                        System.out.println("00000000000000000000000000");
                        return "Y";
                    } else
                        legSet.add(next.getAttribute("LegacyCode"));
                }
            }
        }
        System.out.println("new set iss== " + legSet);
        return "N";
    }
    //                                System.out.println("next != currentRow");
    //                                if (next.getAttribute("LegacyCode") != null) {
    //                                    System.out.println("next.getAttribute(\"LegacyCode\") != null");
    //                                    if (next.getAttribute("LegacyCode").toString().equalsIgnoreCase(Legacy)) {
    //                                       System.out.println("Duplicate");
    //                                        return "Duplicate Entry";
    //                                    }
    //                                }
}
