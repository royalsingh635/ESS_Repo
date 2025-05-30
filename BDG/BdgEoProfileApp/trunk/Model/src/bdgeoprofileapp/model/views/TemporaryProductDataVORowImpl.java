package bdgeoprofileapp.model.views;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.RowSet;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Feb 19 14:45:53 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class TemporaryProductDataVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        SlocId,
        HoOrgId,
        OrgId,
        UsrId,
        EmpNameTranns,
        EmpIdTrans,
        CustNameTrans,
        CustIdTrans,
        RgnNameTrans,
        RgnIdTrans,
        GrpNameTrans,
        GrpIdTrans,
        NameItemTrans,
        IdItemTrans,
        EmpLoginIdTrans,
        LOVCustomerNmVO1,
        LOVItmGrpActvVO1,
        LOVItemNameActvVO1,
        LOVSubOrdinateRegionVO1,
        LOVEoIdVO1;
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
    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int USRID = AttributesEnum.UsrId.index();
    public static final int EMPNAMETRANNS = AttributesEnum.EmpNameTranns.index();
    public static final int EMPIDTRANS = AttributesEnum.EmpIdTrans.index();
    public static final int CUSTNAMETRANS = AttributesEnum.CustNameTrans.index();
    public static final int CUSTIDTRANS = AttributesEnum.CustIdTrans.index();
    public static final int RGNNAMETRANS = AttributesEnum.RgnNameTrans.index();
    public static final int RGNIDTRANS = AttributesEnum.RgnIdTrans.index();
    public static final int GRPNAMETRANS = AttributesEnum.GrpNameTrans.index();
    public static final int GRPIDTRANS = AttributesEnum.GrpIdTrans.index();
    public static final int NAMEITEMTRANS = AttributesEnum.NameItemTrans.index();
    public static final int IDITEMTRANS = AttributesEnum.IdItemTrans.index();
    public static final int EMPLOGINIDTRANS = AttributesEnum.EmpLoginIdTrans.index();
    public static final int LOVCUSTOMERNMVO1 = AttributesEnum.LOVCustomerNmVO1.index();
    public static final int LOVITMGRPACTVVO1 = AttributesEnum.LOVItmGrpActvVO1.index();
    public static final int LOVITEMNAMEACTVVO1 = AttributesEnum.LOVItemNameActvVO1.index();
    public static final int LOVSUBORDINATEREGIONVO1 = AttributesEnum.LOVSubOrdinateRegionVO1.index();
    public static final int LOVEOIDVO1 = AttributesEnum.LOVEoIdVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public TemporaryProductDataVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute CldId.
     * @return the CldId
     */
    public String getCldId() {
        return (String) getAttributeInternal(CLDID);
    }

    /**
     * Gets the attribute value for the calculated attribute SlocId.
     * @return the SlocId
     */
    public Integer getSlocId() {
        return (Integer) getAttributeInternal(SLOCID);
    }

    /**
     * Gets the attribute value for the calculated attribute HoOrgId.
     * @return the HoOrgId
     */
    public String getHoOrgId() {
        return (String) getAttributeInternal(HOORGID);
    }

    /**
     * Gets the attribute value for the calculated attribute OrgId.
     * @return the OrgId
     */
    public String getOrgId() {
        return (String) getAttributeInternal(ORGID);
    }

    /**
     * Gets the attribute value for the calculated attribute UsrId.
     * @return the UsrId
     */
    public Integer getUsrId() {
        return (Integer) getAttributeInternal(USRID);
    }

    /**
     * Gets the attribute value for the calculated attribute EmpNameTranns.
     * @return the EmpNameTranns
     */
    public String getEmpNameTranns() {
        return (String) getAttributeInternal(EMPNAMETRANNS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute EmpNameTranns.
     * @param value value to set the  EmpNameTranns
     */
    public void setEmpNameTranns(String value) {
        setAttributeInternal(EMPNAMETRANNS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute EmpIdTrans.
     * @return the EmpIdTrans
     */
    public Integer getEmpIdTrans() {
        return (Integer) getAttributeInternal(EMPIDTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute EmpIdTrans.
     * @param value value to set the  EmpIdTrans
     */
    public void setEmpIdTrans(Integer value) {
        setAttributeInternal(EMPIDTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CustNameTrans.
     * @return the CustNameTrans
     */
    public String getCustNameTrans() {
        return (String) getAttributeInternal(CUSTNAMETRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CustNameTrans.
     * @param value value to set the  CustNameTrans
     */
    public void setCustNameTrans(String value) {
        setAttributeInternal(CUSTNAMETRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CustIdTrans.
     * @return the CustIdTrans
     */
    public Integer getCustIdTrans() {
        return (Integer) getAttributeInternal(CUSTIDTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CustIdTrans.
     * @param value value to set the  CustIdTrans
     */
    public void setCustIdTrans(Integer value) {
        setAttributeInternal(CUSTIDTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute RgnNameTrans.
     * @return the RgnNameTrans
     */
    public String getRgnNameTrans() {
        return (String) getAttributeInternal(RGNNAMETRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute RgnNameTrans.
     * @param value value to set the  RgnNameTrans
     */
    public void setRgnNameTrans(String value) {
        setAttributeInternal(RGNNAMETRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute RgnIdTrans.
     * @return the RgnIdTrans
     */
    public String getRgnIdTrans() {
        return (String) getAttributeInternal(RGNIDTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute RgnIdTrans.
     * @param value value to set the  RgnIdTrans
     */
    public void setRgnIdTrans(String value) {
        setAttributeInternal(RGNIDTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute GrpNameTrans.
     * @return the GrpNameTrans
     */
    public String getGrpNameTrans() {
        return (String) getAttributeInternal(GRPNAMETRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute GrpNameTrans.
     * @param value value to set the  GrpNameTrans
     */
    public void setGrpNameTrans(String value) {
        setAttributeInternal(GRPNAMETRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute GrpIdTrans.
     * @return the GrpIdTrans
     */
    public String getGrpIdTrans() {
        return (String) getAttributeInternal(GRPIDTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute GrpIdTrans.
     * @param value value to set the  GrpIdTrans
     */
    public void setGrpIdTrans(String value) {
        setAttributeInternal(GRPIDTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute NameItemTrans.
     * @return the NameItemTrans
     */
    public String getNameItemTrans() {
        return (String) getAttributeInternal(NAMEITEMTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute NameItemTrans.
     * @param value value to set the  NameItemTrans
     */
    public void setNameItemTrans(String value) {
        setAttributeInternal(NAMEITEMTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute IdItemTrans.
     * @return the IdItemTrans
     */
    public String getIdItemTrans() {
        return (String) getAttributeInternal(IDITEMTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute IdItemTrans.
     * @param value value to set the  IdItemTrans
     */
    public void setIdItemTrans(String value) {
        setAttributeInternal(IDITEMTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute EmpLoginIdTrans.
     * @return the EmpLoginIdTrans
     */
    public Integer getEmpLoginIdTrans() {
        return Integer.parseInt(resolvEl("#{pageFlowScope.P_EMP_CODE}"));
        //return (Integer) getAttributeInternal(EMPLOGINIDTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute EmpLoginIdTrans.
     * @param value value to set the  EmpLoginIdTrans
     */
    public void setEmpLoginIdTrans(Integer value) {
        setAttributeInternal(EMPLOGINIDTRANS, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVCustomerNmVO1.
     */
    public RowSet getLOVCustomerNmVO1() {
        return (RowSet) getAttributeInternal(LOVCUSTOMERNMVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVItmGrpActvVO1.
     */
    public RowSet getLOVItmGrpActvVO1() {
        return (RowSet) getAttributeInternal(LOVITMGRPACTVVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVItemNameActvVO1.
     */
    public RowSet getLOVItemNameActvVO1() {
        return (RowSet) getAttributeInternal(LOVITEMNAMEACTVVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVSubOrdinateRegionVO1.
     */
    public RowSet getLOVSubOrdinateRegionVO1() {
        return (RowSet) getAttributeInternal(LOVSUBORDINATEREGIONVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVEoIdVO1.
     */
    public RowSet getLOVEoIdVO1() {
        return (RowSet) getAttributeInternal(LOVEOIDVO1);
    }

    /**Method to resolve expression- returns String value*/
    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }
}

