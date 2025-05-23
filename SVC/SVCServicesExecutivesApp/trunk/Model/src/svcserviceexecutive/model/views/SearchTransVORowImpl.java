package svcserviceexecutive.model.views;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.RowSet;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Oct 14 17:54:20 PDT 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class SearchTransVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Dummy,
        UserTransName,
        TransCldId,
        transslocId,
        TransHoOrgId,
        SvcExecVO1,
        LovEmployeeNameVO,
        LovSrchEmpVO1;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }
    String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
    Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
    String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
    Integer usrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
    String hoOrgId=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
    
    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }


    public static final int DUMMY = AttributesEnum.Dummy.index();
    public static final int USERTRANSNAME = AttributesEnum.UserTransName.index();
    public static final int TRANSCLDID = AttributesEnum.TransCldId.index();
    public static final int TRANSSLOCID = AttributesEnum.transslocId.index();
    public static final int TRANSHOORGID = AttributesEnum.TransHoOrgId.index();
    public static final int SVCEXECVO1 = AttributesEnum.SvcExecVO1.index();
    public static final int LOVEMPLOYEENAMEVO = AttributesEnum.LovEmployeeNameVO.index();
    public static final int LOVSRCHEMPVO1 = AttributesEnum.LovSrchEmpVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public SearchTransVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute Dummy.
     * @return the Dummy
     */
    public String getDummy() {
        return (String) getAttributeInternal(DUMMY);
    }

    /**
     * Gets the attribute value for the calculated attribute UserTransName.
     * @return the UserTransName
     */
    public String getUserTransName() {
        return (String) getAttributeInternal(USERTRANSNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UserTransName.
     * @param value value to set the  UserTransName
     */
    public void setUserTransName(String value) {
        setAttributeInternal(USERTRANSNAME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransCldId.
     * @return the TransCldId
     */
    public String getTransCldId() {
        return resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
       // return (String) getAttributeInternal(TRANSCLDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransCldId.
     * @param value value to set the  TransCldId
     */
    public void setTransCldId(String value) {
        setAttributeInternal(TRANSCLDID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute transslocId.
     * @return the transslocId
     */
    public Integer gettransslocId() {
       return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
       // return (Integer) getAttributeInternal(TRANSSLOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute transslocId.
     * @param value value to set the  transslocId
     */
    public void settransslocId(Integer value) {
        setAttributeInternal(TRANSSLOCID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransHoOrgId.
     * @return the TransHoOrgId
     */
    public String getTransHoOrgId() {
        System.out.println("-------hoorgid emp trans----"+hoOrgId);;
   return resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
    //    return (String) getAttributeInternal(TRANSHOORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransHoOrgId.
     * @param value value to set the  TransHoOrgId
     */
    public void setTransHoOrgId(String value) {
        setAttributeInternal(TRANSHOORGID, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> SvcExecVO1.
     */
    public RowSet getSvcExecVO1() {
        return (RowSet) getAttributeInternal(SVCEXECVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovEmployeeNameVO.
     */
    public RowSet getLovEmployeeNameVO() {
        return (RowSet) getAttributeInternal(LOVEMPLOYEENAMEVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovSrchEmpVO1.
     */
    public RowSet getLovSrchEmpVO1() {
        return (RowSet) getAttributeInternal(LOVSRCHEMPVO1);
    }
}

