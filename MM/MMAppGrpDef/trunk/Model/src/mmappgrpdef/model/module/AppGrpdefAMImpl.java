package mmappgrpdef.model.module;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import mmappgrpdef.model.module.common.AppGrpdefAM;
import mmappgrpdef.model.views.AppGrpVOImpl;
import mmappgrpdef.model.views.FormGrpVOImpl;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewLinkImpl;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.context.RequestContext;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Nov 05 15:50:02 IST 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppGrpdefAMImpl extends ApplicationModuleImpl implements AppGrpdefAM {
    /**
     * This is the default constructor (do not remove).
     */
    public static int VARCHAR = Types.VARCHAR;
    String add_mode = null;
    String edit_mode = null;
    String view_mode = null;
    String del_mode = null;

    public AppGrpdefAMImpl() {
    }
    ADFLogger adf_log = (ADFLogger) ADFLogger.createADFLogger(AppGrpdefAMImpl.class);

    /**
     * Container's getter for AppGrp1.
     * @return AppGrp1
     */
    public ViewObjectImpl getAppGrp1() {
        return (ViewObjectImpl) findViewObject("AppGrp1");
    }

    /**
     * Container's getter for AppGrp2.
     * @return AppGrp2
     */
    public ViewObjectImpl getAppGrp2() {
        return (ViewObjectImpl) findViewObject("AppGrp2");
    }

    /**
     * Container's getter for FormGrp1.
     * @return FormGrp1
     */
    public ViewObjectImpl getFormGrp1() {
        return (ViewObjectImpl) findViewObject("FormGrp1");
    }

    /**
     * Container's getter for GrpToGrpVL1.
     * @return GrpToGrpVL1
     */
    public ViewLinkImpl getGrpToGrpVL1() {
        return (ViewLinkImpl) findViewLink("GrpToGrpVL1");
    }

    /**
     * Container's getter for LovGroupIdParent.
     * @return LovGroupIdParent
     */
    public ViewObjectImpl getLovGroupIdParent() {
        return (ViewObjectImpl) findViewObject("LovGroupIdParent");
    }

    public void setBindVar(String cldId, String hoOrgId, Integer slocID) {
        getAppGrp1().setWhereClause("CLD_ID= '" + cldId + "' AND HO_ORG_ID='" + hoOrgId + "' AND SLOC_ID =" + slocID);
        getAppGrp1().executeQuery();
        getFormGrp1().setWhereClause("CLD_ID= '" + cldId + "' AND HO_ORG_ID='" + hoOrgId + "' AND SLOC_ID =" + slocID);
        getFormGrp1().executeQuery();
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            st = getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.registerOutParameter(7, VARCHAR);
            st.registerOutParameter(8, VARCHAR);
            st.registerOutParameter(9, VARCHAR);
            st.registerOutParameter(10, VARCHAR);
            st.executeUpdate();
            try {
                setAdd_mode(st.getObject(7).toString());
                setEdit_mode((st.getObject(8).toString()));
                setView_mode(st.getObject(9).toString());
                setDel_mode(st.getObject(10).toString());
            } catch (NullPointerException e) {
                System.out.println(e);
                e.printStackTrace();
            }
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


    public Integer on_load_page() {
        int count = 0;
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        callStoredFunction(VARCHAR, "APP.fn_get_usr_doc_access_param(?,?,?,?,?,?,?,?,?)", new Object[] {
                           cld_id, sloc_id, org_id, 1, usr_id
        });
        String calledFrom = resolvEl("#{pageFlowScope.PARAM_PG_CALLED}").toString();
        if (add_mode.equalsIgnoreCase("Y") == false && add_mode.equalsIgnoreCase("N") == false) {
            count = 1;
        } else if (edit_mode.equalsIgnoreCase("Y") == false && edit_mode.equalsIgnoreCase("N") == false) {
            count = 1;
        } else if (view_mode.equalsIgnoreCase("Y") == false && view_mode.equalsIgnoreCase("N") == false) {
            count = 1;
        } else if (del_mode.equalsIgnoreCase("Y") == false && del_mode.equalsIgnoreCase("N") == false) {
            count = 1;
        } else if (calledFrom.equalsIgnoreCase("P") == false && calledFrom.equalsIgnoreCase("M") == false) {
            count = 1;
        }
        Map paramMap = RequestContext.getCurrentInstance().getPageFlowScope();
        paramMap.put("PARAM_PG_ADD_MD", add_mode);
        paramMap.put("PARAM_PG_EDIT_MD", edit_mode);
        paramMap.put("PARAM_PG_VIEW_MD", view_mode);
        paramMap.put("PARAM_PG_DEL_MD", del_mode);

        return count;
    }


    public void setAdd_mode(String add_mode) {
        this.add_mode = add_mode;
    }

    public String getAdd_mode() {
        return add_mode;
    }

    public void setEdit_mode(String edit_mode) {
        this.edit_mode = edit_mode;
    }

    public String getEdit_mode() {
        return edit_mode;
    }

    public void setView_mode(String view_mode) {
        this.view_mode = view_mode;
    }

    public String getView_mode() {
        return view_mode;
    }

    public void setDel_mode(String del_mode) {
        this.del_mode = del_mode;
    }

    public String getDel_mode() {
        return del_mode;
    }

    /**
     * Container's getter for AppItmCoaLink1.
     * @return AppItmCoaLink1
     */
    public ViewObjectImpl getAppItmCoaLink1() {
        return (ViewObjectImpl) findViewObject("AppItmCoaLink1");
    }

    /**
     * Container's getter for AppItmGrpCoa1.
     * @return AppItmGrpCoa1
     */
    public ViewObjectImpl getAppItmGrpCoa1() {
        return (ViewObjectImpl) findViewObject("AppItmGrpCoa1");
    }

    /**
     * Container's getter for FormGrpCoaVL1.
     * @return FormGrpCoaVL1
     */
    public ViewLinkImpl getFormGrpCoaVL1() {
        return (ViewLinkImpl) findViewLink("FormGrpCoaVL1");
    }

    /**
     * Container's getter for Coa1.
     * @return Coa1
     */
    public ViewObjectImpl getCoa1() {
        return (ViewObjectImpl) findViewObject("Coa1");
    }

    public ViewObjectImpl getCoaVo() {
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String org_id = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");

        ViewObjectImpl coaVo = this.getCoa1();
        ViewCriteria vc = coaVo.getViewCriteria("CoaVOCriteria");
        coaVo.applyViewCriteria(vc);
        coaVo.setNamedWhereClauseParam("bindCldId", cld_id);
        coaVo.setNamedWhereClauseParam("bindSlocId", sloc_id);
        coaVo.setNamedWhereClauseParam("bindOrgId", org_id);
        coaVo.setNamedWhereClauseParam("bindHoOrgId", hoOrgId);
        return coaVo;
    }

    /**
     * Container's getter for PickOrder1.
     * @return PickOrder1
     */
    public ViewObjectImpl getPickOrder1() {
        return (ViewObjectImpl) findViewObject("PickOrder1");
    }

    /**
     * Container's getter for CostMthd1.
     * @return CostMthd1
     */
    public ViewObjectImpl getCostMthd1() {
        return (ViewObjectImpl) findViewObject("CostMthd1");
    }

    /**
     * Container's getter for CostMthd2.
     * @return CostMthd2
     */
    public ViewObjectImpl getCostMthd2() {
        return (ViewObjectImpl) findViewObject("CostMthd2");
    }

    /**
     * Container's getter for LovOrgVO1.
     * @return LovOrgVO1
     */
    public ViewObjectImpl getLovOrgVO1() {
        return (ViewObjectImpl) findViewObject("LovOrgVO1");
    }


    public String getCurrentOrgId() {
        ViewObjectImpl vo = this.getFormGrp1();
        Row row = vo.getCurrentRow();
        String orgId = (String) row.getAttribute("OrgId");
        return orgId;
    }


    public String getOrganisationName(String orgId) {
        ViewObjectImpl vo = this.getLovOrgVO1();
        Row rows[] = vo.getFilteredRows("OrgId", orgId);

        if (rows.length > 0) {
            return (String) rows[0].getAttribute("OrgDesc");
        }

        return null;
    }


    protected Object callDBFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            st = this.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
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
            //  throw new JboException(e.getMessage().substring(11, end));
            String msg = e.getMessage();
            FacesMessage ermsg = new FacesMessage(msg);
            ermsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, ermsg);
            //  throw new JboException(e);
            return null;
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {


                }
            }
        }
    }


    public boolean isGroupNameValid(String grpNm) {
        System.out.println("group name=" + grpNm);
        ViewObjectImpl vo = this.getFormGrp1();
        Row row = vo.getCurrentRow();
        String CldId = row.getAttribute("CldId").toString();
        Integer SlocId = Integer.parseInt(row.getAttribute("SlocId").toString());
        String HoOrgId = row.getAttribute("HoOrgId").toString();
        String parentId = "0";
        if (row.getAttribute("GrpIdParent") != null)
            parentId = (String) row.getAttribute("GrpIdParent");
        String grpId = (String) row.getAttribute("GrpId");
        System.out.println("All param=" + CldId + " " + SlocId + " " + HoOrgId + " " + parentId + " " + grpNm.trim() +
                           " " + grpId);
        String chk = (String) (callDBFunction(Types.VARCHAR, "APP.CHK_GRP_NM(?,?,?,?,?,?)", new Object[] {
                                              CldId, SlocId, HoOrgId, parentId, grpNm.trim(), grpId
        }));

        System.out.println("Chk=" + chk + grpId);
        if (chk.equals("Y")) {
            return Boolean.TRUE;
        } else
            return Boolean.FALSE;
    }


    public boolean isShortDescValid() {
        try {
            ViewObjectImpl vo = this.getFormGrp1();
            Row row = vo.getCurrentRow();
            RowSetIterator rst = vo.createRowSetIterator(null);
            while (rst.hasNext()) {
                Row r = rst.next();
                if (r != row) {
                    String parentId = (String) r.getAttribute("GrpIdParent");
                    String shortNm = (String) r.getAttribute("GrpShortCode");
                    if ((parentId.trim().equalsIgnoreCase(row.getAttribute("GrpIdParent").toString().trim())) &&
                        (shortNm.trim().equalsIgnoreCase(row.getAttribute("GrpShortCode").toString().trim()))) {
                        return Boolean.TRUE;
                    }
                }
            }
            rst.closeRowSetIterator();

        } catch (NullPointerException e) {
            System.out.println("Null");
        }
        return Boolean.FALSE;
    }


    public boolean isShortDescCorrect(String shortCode) {

        ViewObjectImpl vo = this.getFormGrp1();
        Row row = vo.getCurrentRow();
        String CldId = row.getAttribute("CldId").toString();
        Integer SlocId = Integer.parseInt(row.getAttribute("SlocId").toString());
        String HoOrgId = row.getAttribute("HoOrgId").toString();
        String parentId = "0";
        if (row.getAttribute("GrpIdParent") != null)
            parentId = (String) row.getAttribute("GrpIdParent");
        String grpId = (String) row.getAttribute("GrpId");
        String chk = (String) (callDBFunction(Types.VARCHAR, "APP.FN_CHK_GRP_SHORT_CODE(?,?,?,?,?,?)", new Object[] {
                                              CldId, SlocId, HoOrgId, parentId, shortCode.trim(), grpId
        }));

        if (chk.equals("Y")) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }


    /**
     * Container's getter for FormGrpVO1.
     * @return FormGrpVO1
     */
    public FormGrpVOImpl getFormGrpVO1() {
        return (FormGrpVOImpl) findViewObject("FormGrpVO1");
    }

    public void setValueAsParent(String CldId, String HoOrgId, Integer SlocId, String GrpId, String GrpIdParent) {
        System.out.println("Setting values as parent");
        RowQualifier rq = new RowQualifier(this.getAppGrp1());
        rq.setWhereClause("CldId='" + CldId + "' and SlocId=" + SlocId + " and HoOrgId='" + HoOrgId + "' and GrpId='" +
                          GrpIdParent + "'");
        Row[] rows = this.getAppGrp1().getFilteredRows(rq);
        Row curr = this.getFormGrp1().getCurrentRow();
        if (rows.length > 0) {
            curr.setAttribute("PickOrder", rows[0].getAttribute("PickOrder"));
            curr.setAttribute("SrvcItmFlg", rows[0].getAttribute("SrvcItmFlg"));
            curr.setAttribute("StockableFlg", rows[0].getAttribute("StockableFlg"));
            curr.setAttribute("SlsItmFlg", rows[0].getAttribute("SlsItmFlg"));
            curr.setAttribute("PurItmFlg", rows[0].getAttribute("PurItmFlg"));
            curr.setAttribute("WipItmFlg", rows[0].getAttribute("WipItmFlg"));
            curr.setAttribute("CostMthd", rows[0].getAttribute("CostMthd"));
            curr.setAttribute("TaxExmptFlg", rows[0].getAttribute("TaxExmptFlg"));
            curr.setAttribute("CapitalGdFlg", rows[0].getAttribute("CapitalGdFlg"));
            curr.setAttribute("QcReqdFlg", rows[0].getAttribute("QcReqdFlg"));
            curr.setAttribute("CashPurFlg", rows[0].getAttribute("CashPurFlg"));
            curr.setAttribute("ConsumableFlg", rows[0].getAttribute("ConsumableFlg"));
            curr.setAttribute("SerializedFlg", rows[0].getAttribute("SerializedFlg"));

        } else {
            RowQualifier rqform = new RowQualifier(this.getFormGrp1());
            rqform.setWhereClause("CldId='" + CldId + "' and SlocId=" + SlocId + " and HoOrgId='" + HoOrgId +
                                  "' and GrpId='" + GrpIdParent + "'");
            Row[] row = this.getFormGrp1().getFilteredRows(rqform);
            curr.setAttribute("PickOrder", row[0].getAttribute("PickOrder"));
            curr.setAttribute("SrvcItmFlg", row[0].getAttribute("SrvcItmFlg"));
            curr.setAttribute("StockableFlg", row[0].getAttribute("StockableFlg"));
            curr.setAttribute("SlsItmFlg", row[0].getAttribute("SlsItmFlg"));
            curr.setAttribute("PurItmFlg", row[0].getAttribute("PurItmFlg"));
            curr.setAttribute("WipItmFlg", row[0].getAttribute("WipItmFlg"));
            curr.setAttribute("CostMthd", row[0].getAttribute("CostMthd"));
            curr.setAttribute("TaxExmptFlg", row[0].getAttribute("TaxExmptFlg"));
            curr.setAttribute("CapitalGdFlg", row[0].getAttribute("CapitalGdFlg"));
            curr.setAttribute("QcReqdFlg", row[0].getAttribute("QcReqdFlg"));
            curr.setAttribute("CashPurFlg", row[0].getAttribute("CashPurFlg"));
            curr.setAttribute("ConsumableFlg", row[0].getAttribute("ConsumableFlg"));
            curr.setAttribute("SerializedFlg", row[0].getAttribute("SerializedFlg"));
        }
        System.out.println("Values setted as parent exiting");
    }


    public void addCoa() {
        ViewObjectImpl objLink = this.getAppItmCoaLink1();
        ViewObjectImpl coaVo = this.getAppItmGrpCoa1();

        Integer usrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        System.out.println("User Id : " + usrId);

        Row rows[] = objLink.getAllRowsInRange();
        for (Row row : rows) {
            Row currRow = coaVo.createRow();
            currRow.setAttribute("CoaFor", row.getAttribute("AttId"));
            currRow.setAttribute("UsrIdCreate", usrId);
            currRow.setAttribute("UsrIdCreateDt", new Timestamp(System.currentTimeMillis()));
            coaVo.insertRow(currRow);
        }

    }

    public String nameValidator(String Pname) {
        ViewObjectImpl grpVo = this.getFormGrp1();
        Row curr = grpVo.getCurrentRow();
        RowSetIterator cr = grpVo.createRowSetIterator(null);
        String hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String flag = null;
        while (cr.hasNext()) {
            Row rr1 = cr.next();
            adf_log.info("current row key  " + curr.getKey());
            adf_log.info("iterator key is " + rr1.getKey());
            if (rr1 != curr) {
                String phoOrg = rr1.getAttribute("HoOrgId").toString();
                String pcldId = rr1.getAttribute("CldId").toString();
                Integer pslocId = Integer.parseInt(rr1.getAttribute("SlocId").toString());
                adf_log.info("both parent IDsvalue is " + rr1.getAttribute("GrpIdParent") + "     " +
                             curr.getAttribute("GrpIdParent"));


                if (hoOrgId.equalsIgnoreCase(phoOrg) && cldId.equalsIgnoreCase(pcldId) && slocId.equals(pslocId)) {
                    if (((String) rr1.getAttribute("GrpNm")).equalsIgnoreCase(Pname) &&
                        ((String) rr1.getAttribute("GrpIdParent")).equalsIgnoreCase("0")) {
                        adf_log.info("both value matched " + (String) rr1.getAttribute("GrpNm") + " current pname is " +
                                     Pname);
                        flag = "N";
                    }
                }
            }
        }
        cr.closeRowSetIterator();
        if ("N".equalsIgnoreCase(flag)) {

        } else {
            String chksib = chkSiblings(Pname);
            adf_log.info(" sibling " + chksib);
            if ("N".equalsIgnoreCase(chksib)) {
                flag = "N";
            } else {
                flag = "Y";
            }
        }
        adf_log.info("flag value is " + flag);
        return flag;
    }

    public String chkSiblings(String Pname) {
        ViewObjectImpl grpVo = this.getAppGrp3();
        Row curr = getFormGrp1().getCurrentRow();
        String flag = null;
        adf_log.info("                   " + curr.getAttribute("GrpIdParent") + " " + Pname);
        RowQualifier r1 = new RowQualifier(grpVo);
        r1.setWhereClause("HoOrgId ='" + curr.getAttribute("HoOrgId") + "' and  GrpIdParent ='" +
                          curr.getAttribute("GrpIdParent") + "' and upper(GrpNm)= upper('" + Pname + "')"); //)
        Row[] filter = grpVo.getFilteredRows(r1);
        adf_log.info("total no. of rows found" + filter.length + r1.getExprStr());
        if (filter.length > 1) {
            flag = "N";
        } else if (curr.getAttribute("GrpIdParent") != null) {
            RowQualifier r2 = new RowQualifier(grpVo);
            r2.setWhereClause("HoOrgId ='" + curr.getAttribute("HoOrgId") + "' and GrpId ='" +
                              curr.getAttribute("GrpIdParent") + "' and upper(GrpNm)= upper('" + Pname + "')"); //)
            Row[] rr1 = grpVo.getFilteredRows(r2);
            adf_log.info("total no. of rr1 length found" + rr1.length + r2.getExprStr());
            if (rr1.length > 0) {
                flag = "N";
            }
        } else {
            flag = "Y";
        }

        return flag;
    }


    /**
     * Container's getter for AppGrp3.
     * @return AppGrp3
     */
    public AppGrpVOImpl getAppGrp3() {
        return (AppGrpVOImpl) findViewObject("AppGrp3");
    }

    /**
     * Container's getter for OrgMmPrf1.
     * @return OrgMmPrf1
     */
    public ViewObjectImpl getOrgMmPrf1() {
        return (ViewObjectImpl) findViewObject("OrgMmPrf1");
    }

    public String chkcoaFor(Integer coaFor) {

        String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        System.out.println("Cld Id in chkcoaFor" + cldId);
        Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        System.out.println("Sloc Id in chkcoaFor" + slocId);
        String hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        System.out.println("hoOrgId in chkcoaFor" + hoOrgId);
        String grpId = this.getAppItmGrpCoa1().getCurrentRow().getAttribute("GrpId").toString();
        System.out.println("Group Id in coa For" + grpId);

        //        Integer coaFor = Integer.parseInt(this.getAppItmGrpCoa1().getCurrentRow().getAttribute("CoaFor").toString());
        System.out.println("Coa For in chk coa for" + coaFor);
        String grpIdReq = callStoredFunction1(Types.VARCHAR, "MM.MM_ITM_IS_COA_REQ(?,?,?,?,?,?)", new Object[] {
                                              cldId, slocId, hoOrgId, grpId, coaFor, "GRP"
        }).toString();
        System.out.println("Value of function is" + grpIdReq);
        return grpIdReq;
    }

    public Object callStoredFunction1(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            AppGrpdefAMImpl am = (AppGrpdefAMImpl) resolvElDC("AppGrpdefAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    System.out.println("z " + z + "Val " + bindVars[z]);
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();

            return st.getObject(1);
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    throw new JboException(e);
                }
            }
        }
    }

    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }
}
