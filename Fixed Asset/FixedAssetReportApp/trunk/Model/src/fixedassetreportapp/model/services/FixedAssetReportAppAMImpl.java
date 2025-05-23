package fixedassetreportapp.model.services;

import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import fixedassetreportapp.model.services.common.FixedAssetReportAppAM;

import fixedassetreportapp.model.views.FARptTransVOImpl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Date;

import javax.el.ELContext;
import javax.el.ValueExpression;

import oracle.adf.share.ADFContext;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri May 29 11:55:08 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class FixedAssetReportAppAMImpl extends ApplicationModuleImpl implements FixedAssetReportAppAM {
    /**
     * This is the default constructor (do not remove).
     */
    public FixedAssetReportAppAMImpl() {
    }

    /**
     * Container's getter for FARptTrans1.
     * @return FARptTrans1
     */
    public FARptTransVOImpl getFARptTrans1() {
        return (FARptTransVOImpl) findViewObject("FARptTrans1");
    }

    /**
     * Container's getter for FARptChkBx1.
     * @return FARptChkBx1
     */
    public ViewObjectImpl getFARptChkBx1() {
        return (ViewObjectImpl) findViewObject("FARptChkBx1");
    }

    public Object resolvEl(String data) {
        ADFContext adfCtx = ADFContext.getCurrent();
        ELContext elContext = adfCtx.getELContext();
        ValueExpression valueExp = adfCtx.getExpressionFactory().createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext).toString();

        return Message;
    }

    /*getting page flow scope parameter methods.*/

    public Integer getSlocId() {
        return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC }").toString());
    }

    public StringBuffer getCldId() {
        return new StringBuffer(resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString());
    }

    public StringBuffer getOrgId() {
        return new StringBuffer(resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString());
    }

    public StringBuffer getHoOrgId() {
        return new StringBuffer(resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString());
    }

    public Integer getUsrId() {
        return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
    }
    //setting CldId, SlocId, OrgId, HoOrgId from pageflowscope.
    public void setGlblParam() {
        ViewObjectImpl transVo = this.getFARptTrans1();
        transVo.setNamedWhereClauseParam("BindCldId", getCldId());
        transVo.setNamedWhereClauseParam("BindSlocId", getSlocId());
        transVo.setNamedWhereClauseParam("BindOrgId", getOrgId());
        transVo.setNamedWhereClauseParam("BindHoOrgId", getHoOrgId());
        // transVo.executeQuery();
    }

    public void reset() {
        this.getFARptTrans1().executeQuery();
        this.getFARptChkBx1().executeQuery();
        this.setDefaultDate();
    }

    //user defined Method to call SQL function or procedure.
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
            return st.getObject(1);
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    // 7. Close the statement
                    st.close();
                } catch (SQLException e) {
                }
            }
        }
    }
    //For Setting the from date as FY Start Date and To date as current system date by default.
    public void setDefaultDate() {
        Date dt = new Date();
        Timestamp ts = null;
        try {
            dt = (Date) (callStoredFunction(Types.DATE, "APP.fn_get_fy_start_date(?,?,?,?)", new Object[] {
                                            getCldId().toString(), getOrgId().toString(),
                                            new Timestamp(System.currentTimeMillis()), "FY"
            }));

            ts = new Timestamp(dt.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("ts value is " + ts);
        ViewObjectImpl transVO2 = (ViewObjectImpl) this.getFARptTrans1();
        transVO2.getCurrentRow().setAttribute("FromDtTrans", ts);
        transVO2.getCurrentRow().setAttribute("ToDtTrans", new Timestamp(System.currentTimeMillis()));
    }

    public Boolean chkUsrRptSecAccess(Integer rptId) {
        String ret =
            (String) ADFModelUtils.callFunction(this.getRootApplicationModule(),
                                                new StringBuilder("APP.FN_CHK_USR_RPT_SEC(?,?,?,?,?)"), new Object[] {
                                                EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(),
                                                EbizParams.GLBL_APP_USR_ORG(), EbizParams.GLBL_APP_USR(), rptId
        }, Types.VARCHAR);
        if (ret != null && ret.length() > 0 && ret.toUpperCase().equals("Y"))
            return true;
        else
            return false;
    }
}

