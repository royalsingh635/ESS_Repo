package schedule6.model.views;

import oracle.jbo.RowSet;
import oracle.jbo.server.ViewRowImpl;

import schedule6.model.services.Schedule6AMImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Dec 04 17:23:52 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class OrgSchDualVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Selectobjects0,
        CldId,
        HoOrgId,
        OrgId,
        IncProVou,
        IncPostVou,
        IncStkEntry,
        ScheduleId,
        ProVouType,
        curHo,
        SchIdForOrg,
        OrgIdSearch,
        OrgDescForSearch,
        LovOrgVo1,
        LovOrgVo2,
        LovFinalizedSchedule1;
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
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int INCPROVOU = AttributesEnum.IncProVou.index();
    public static final int INCPOSTVOU = AttributesEnum.IncPostVou.index();
    public static final int INCSTKENTRY = AttributesEnum.IncStkEntry.index();
    public static final int SCHEDULEID = AttributesEnum.ScheduleId.index();
    public static final int PROVOUTYPE = AttributesEnum.ProVouType.index();
    public static final int CURHO = AttributesEnum.curHo.index();
    public static final int SCHIDFORORG = AttributesEnum.SchIdForOrg.index();
    public static final int ORGIDSEARCH = AttributesEnum.OrgIdSearch.index();
    public static final int ORGDESCFORSEARCH = AttributesEnum.OrgDescForSearch.index();
    public static final int LOVORGVO1 = AttributesEnum.LovOrgVo1.index();
    public static final int LOVORGVO2 = AttributesEnum.LovOrgVo2.index();
    public static final int LOVFINALIZEDSCHEDULE1 = AttributesEnum.LovFinalizedSchedule1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public OrgSchDualVORowImpl() {
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
        Schedule6AMImpl am=(Schedule6AMImpl)this.getApplicationModule();
        return (String) am.getCldId();
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CldId.
     * @param value value to set the  CldId
     */
    public void setCldId(String value) {
        setAttributeInternal(CLDID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute HoOrgId.
     * @return the HoOrgId
     */
    public String getHoOrgId() {
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
     * Gets the attribute value for the calculated attribute IncProVou.
     * @return the IncProVou
     */
    public String getIncProVou() {

        return (String) getAttributeInternal(INCPROVOU);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute IncProVou.
     * @param value value to set the  IncProVou
     */
    public void setIncProVou(String value) {
        setAttributeInternal(INCPROVOU, value);
    }

    /**
     * Gets the attribute value for the calculated attribute IncPostVou.
     * @return the IncPostVou
     */
    public String getIncPostVou() {
        
        return (String) getAttributeInternal(INCPOSTVOU);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute IncPostVou.
     * @param value value to set the  IncPostVou
     */
    public void setIncPostVou(String value) {
        setAttributeInternal(INCPOSTVOU, value);
    }

    /**
     * Gets the attribute value for the calculated attribute IncStkEntry.
     * @return the IncStkEntry
     */
    public String getIncStkEntry() {
      
        return (String) getAttributeInternal(INCSTKENTRY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute IncStkEntry.
     * @param value value to set the  IncStkEntry
     */
    public void setIncStkEntry(String value) {
        setAttributeInternal(INCSTKENTRY, value);
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
     * Gets the attribute value for the calculated attribute ProVouType.
     * @return the ProVouType
     */
    public String getProVouType() {
        return (String) getAttributeInternal(PROVOUTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ProVouType.
     * @param value value to set the  ProVouType
     */
    public void setProVouType(String value) {
        setAttributeInternal(PROVOUTYPE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute curHo.
     * @return the curHo
     */
    public String getcurHo() {
        Schedule6AMImpl am=(Schedule6AMImpl)this.getApplicationModule();
        return (String) am.getHoOrgId();
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute curHo.
     * @param value value to set the  curHo
     */
    public void setcurHo(String value) {
        setAttributeInternal(CURHO, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SchIdForOrg.
     * @return the SchIdForOrg
     */
    public Integer getSchIdForOrg() {
        return (Integer) getAttributeInternal(SCHIDFORORG);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SchIdForOrg.
     * @param value value to set the  SchIdForOrg
     */
    public void setSchIdForOrg(Integer value) {
        setAttributeInternal(SCHIDFORORG, value);
    }

    /**
     * Gets the attribute value for the calculated attribute OrgIdSearch.
     * @return the OrgIdSearch
     */
    public String getOrgIdSearch() {
        return (String) getAttributeInternal(ORGIDSEARCH);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute OrgIdSearch.
     * @param value value to set the  OrgIdSearch
     */
    public void setOrgIdSearch(String value) {
        setAttributeInternal(ORGIDSEARCH, value);
    }

    /**
     * Gets the attribute value for the calculated attribute OrgDescForSearch.
     * @return the OrgDescForSearch
     */
    public String getOrgDescForSearch() {
        return (String) getAttributeInternal(ORGDESCFORSEARCH);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute OrgDescForSearch.
     * @param value value to set the  OrgDescForSearch
     */
    public void setOrgDescForSearch(String value) {
        setAttributeInternal(ORGDESCFORSEARCH, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovOrgVo1.
     */
    public RowSet getLovOrgVo1() {
        return (RowSet) getAttributeInternal(LOVORGVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovOrgVo2.
     */
    public RowSet getLovOrgVo2() {
        return (RowSet) getAttributeInternal(LOVORGVO2);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovFinalizedSchedule1.
     */
    public RowSet getLovFinalizedSchedule1() {
        return (RowSet) getAttributeInternal(LOVFINALIZEDSCHEDULE1);
    }
}

