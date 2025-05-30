package hcmtimecalendarsetupapp.model.views;

import oracle.jbo.domain.Timestamp;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;


import oracle.adf.share.ADFContext;

import oracle.jbo.RowSet;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Sep 10 09:58:40 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class SearchVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Dummy,
        HolidayDesc,
        HolidayType,
        RecurrPattern,
        Year,
        CldId,
        SlocId,
        HoOrgId,
        OrgId,
        HolidayDate,
        LovRecurPatternVO1,
        LovHolidayTypeVO1;
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


    public static final int DUMMY = AttributesEnum.Dummy.index();
    public static final int HOLIDAYDESC = AttributesEnum.HolidayDesc.index();
    public static final int HOLIDAYTYPE = AttributesEnum.HolidayType.index();
    public static final int RECURRPATTERN = AttributesEnum.RecurrPattern.index();
    public static final int YEAR = AttributesEnum.Year.index();
    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int HOLIDAYDATE = AttributesEnum.HolidayDate.index();
    public static final int LOVRECURPATTERNVO1 = AttributesEnum.LovRecurPatternVO1.index();
    public static final int LOVHOLIDAYTYPEVO1 = AttributesEnum.LovHolidayTypeVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public SearchVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute Dummy.
     * @return the Dummy
     */
    public String getDummy() {
        return (String) getAttributeInternal(DUMMY);
    }

    /**
     * Gets the attribute value for the calculated attribute HolidayDesc.
     * @return the HolidayDesc
     */
    public String getHolidayDesc() {
        return (String) getAttributeInternal(HOLIDAYDESC);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute HolidayDesc.
     * @param value value to set the  HolidayDesc
     */
    public void setHolidayDesc(String value) {
        setAttributeInternal(HOLIDAYDESC, value);
    }

    /**
     * Gets the attribute value for the calculated attribute HolidayType.
     * @return the HolidayType
     */
    public Integer getHolidayType() {
        return (Integer) getAttributeInternal(HOLIDAYTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute HolidayType.
     * @param value value to set the  HolidayType
     */
    public void setHolidayType(Integer value) {
        setAttributeInternal(HOLIDAYTYPE, value);
    }


    /**
     * Gets the attribute value for the calculated attribute RecurrPattern.
     * @return the RecurrPattern
     */
    public Integer getRecurrPattern() {
        return (Integer) getAttributeInternal(RECURRPATTERN);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute RecurrPattern.
     * @param value value to set the  RecurrPattern
     */
    public void setRecurrPattern(Integer value) {
        setAttributeInternal(RECURRPATTERN, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Year.
     * @return the Year
     */
    public Integer getYear() {
        return (Integer) getAttributeInternal(YEAR);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Year.
     * @param value value to set the  Year
     */
    public void setYear(Integer value) {
        setAttributeInternal(YEAR, value);
    }


    public Object resolvEl(String data) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, data, Object.class);
        return exp.getValue(elContext);
    }

    /**
     * Gets the attribute value for the calculated attribute CldId.
     * @return the CldId
     */
    public String getCldId() {
        //return (resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString());
        return (String) getAttributeInternal(CLDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CldId.
     * @param value value to set the  CldId
     */
    public void setCldId(String value) {
        setAttributeInternal(CLDID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SlocId.
     * @return the SlocId
     */
    public Integer getSlocId() {
        //return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        return (Integer) getAttributeInternal(SLOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SlocId.
     * @param value value to set the  SlocId
     */
    public void setSlocId(Integer value) {
        setAttributeInternal(SLOCID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute HoOrgId.
     * @return the HoOrgId
     */
    public String getHoOrgId() {
        // return resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        return (String) getAttributeInternal(HOORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute HoOrgId.
     * @param value value to set the  HoOrgId
     */
    public void setHoOrgId(String value) {
        setAttributeInternal(HOORGID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute OrgId.
     * @return the OrgId
     */
    public String getOrgId() {
        // return resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        return (String) getAttributeInternal(ORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute OrgId.
     * @param value value to set the  OrgId
     */
    public void setOrgId(String value) {
        setAttributeInternal(ORGID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute HolidayDate.
     * @return the HolidayDate
     */
    public Timestamp getHolidayDate() {
        return (Timestamp) getAttributeInternal(HOLIDAYDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute HolidayDate.
     * @param value value to set the  HolidayDate
     */
    public void setHolidayDate(Timestamp value) {
        setAttributeInternal(HOLIDAYDATE, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovRecurPatternVO1.
     */
    public RowSet getLovRecurPatternVO1() {
        return (RowSet) getAttributeInternal(LOVRECURPATTERNVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovHolidayTypeVO1.
     */
    public RowSet getLovHolidayTypeVO1() {
        return (RowSet) getAttributeInternal(LOVHOLIDAYTYPEVO1);
    }
}

