package schedule6.model.views;

import oracle.jbo.RowSet;
import oracle.jbo.server.ViewRowImpl;

import schedule6.model.services.Schedule6AMImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Dec 05 18:44:54 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class GenDataVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Selectobjects0,
        CldId,
        HoOrgId,
        ScheduleType,
        OrgId,
        ScheduleId,
        FinYear,
        ForAll,
        LovScheduleTypeVO1,
        LovFinalizedSchedule1,
        LovOrgSchVO1,
        LovFyIdVO1;
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


    public static final int SELECTOBJECTS0 = AttributesEnum.Selectobjects0.index();
    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int SCHEDULETYPE = AttributesEnum.ScheduleType.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int SCHEDULEID = AttributesEnum.ScheduleId.index();
    public static final int FINYEAR = AttributesEnum.FinYear.index();
    public static final int FORALL = AttributesEnum.ForAll.index();
    public static final int LOVSCHEDULETYPEVO1 = AttributesEnum.LovScheduleTypeVO1.index();
    public static final int LOVFINALIZEDSCHEDULE1 = AttributesEnum.LovFinalizedSchedule1.index();
    public static final int LOVORGSCHVO1 = AttributesEnum.LovOrgSchVO1.index();
    public static final int LOVFYIDVO1 = AttributesEnum.LovFyIdVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public GenDataVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute Selectobjects0.
     * @return the Selectobjects0
     */
    public String getSelectobjects0() {
        return (String) getAttributeInternal(SELECTOBJECTS0);
    }

    /**
     * Gets the attribute value for the calculated attribute CldId.
     * @return the CldId
     */
    public String getCldId() {
        return (String) ((Schedule6AMImpl)this.getApplicationModule()).getCldId();
    }

    /**
     * Gets the attribute value for the calculated attribute HoOrgId.
     * @return the HoOrgId
     */
    public String getHoOrgId() {
        return ((Schedule6AMImpl)this.getApplicationModule()).getHoOrgId();
    }

    /**
     * Gets the attribute value for the calculated attribute ScheduleType.
     * @return the ScheduleType
     */
    public String getScheduleType() {
        return (String) getAttributeInternal(SCHEDULETYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ScheduleType.
     * @param value value to set the  ScheduleType
     */
    public void setScheduleType(String value) {
        setAttributeInternal(SCHEDULETYPE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute OrgType.
     * @return the OrgType
     */
    public String getOrgId() {
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
     * Gets the attribute value for the calculated attribute ScheduleId.
     * @return the ScheduleId
     */
    public Integer getScheduleId() {
        return (Integer) getAttributeInternal(SCHEDULEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ScheduleId.
     * @param value value to set the  ScheduleId
     */
    public void setScheduleId(Integer value) {
        setAttributeInternal(SCHEDULEID, value);
    }


    /**
     * Gets the attribute value for the calculated attribute ViewAttr.
     * @return the ViewAttr
     */
    public Integer getFinYear() {
        return (Integer) getAttributeInternal(FINYEAR);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute FinYear.
     * @param value value to set the  FinYear
     */
    public void setFinYear(Integer value) {
        setAttributeInternal(FINYEAR, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ForAll.
     * @return the ForAll
     */
    public String getForAll() {
        return (String) getAttributeInternal(FORALL);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ForAll.
     * @param value value to set the  ForAll
     */
    public void setForAll(String value) {
        setAttributeInternal(FORALL, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovScheduleTypeVO1.
     */
    public RowSet getLovScheduleTypeVO1() {
        return (RowSet) getAttributeInternal(LOVSCHEDULETYPEVO1);
    }


    /**
     * Gets the view accessor <code>RowSet</code> LovFinalizedSchedule1.
     */
    public RowSet getLovFinalizedSchedule1() {
        return (RowSet) getAttributeInternal(LOVFINALIZEDSCHEDULE1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovOrgSchVO1.
     */
    public RowSet getLovOrgSchVO1() {
        return (RowSet) getAttributeInternal(LOVORGSCHVO1);
    }


    /**
     * Gets the view accessor <code>RowSet</code> LovFyIdVO1.
     */
    public RowSet getLovFyIdVO1() {
        return (RowSet) getAttributeInternal(LOVFYIDVO1);
    }
}

