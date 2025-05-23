package mnfWorkStationApp.model.views;

import java.util.Date;

import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;

import mnfWorkStationApp.model.entities.MnfWsShiftEOImpl;
import mnfWorkStationApp.model.services.MNFWorkStationAppAMImpl;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Sep 03 11:42:13 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MnfWsShiftVORowImpl extends ViewRowImpl {


    public static final int ENTITY_MNFWSSHIFTEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        HoOrgId,
        ShiftId,
        SlocId,
        UsrIdCreate,
        UsrIdCreateDt,
        UsrIdMod,
        UsrIdModDt,
        WsId,
        TransShiftName,
        TransShiftEndTime,
        TransShiftStartTime,
        ShiftActv,
        TransKey,
        ShiftDfltFlg,
        TransWcId,
        LOVUserVO1,
        LOVUserVO2,
        LOVShiftVO1;
        static AttributesEnum[] vals = null;
        ;
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

    private Timestamp shiftStarttime;
    private Timestamp shiftEndtime;


    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int SHIFTID = AttributesEnum.ShiftId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int WSID = AttributesEnum.WsId.index();
    public static final int TRANSSHIFTNAME = AttributesEnum.TransShiftName.index();
    public static final int TRANSSHIFTENDTIME = AttributesEnum.TransShiftEndTime.index();
    public static final int TRANSSHIFTSTARTTIME = AttributesEnum.TransShiftStartTime.index();
    public static final int SHIFTACTV = AttributesEnum.ShiftActv.index();
    public static final int TRANSKEY = AttributesEnum.TransKey.index();
    public static final int SHIFTDFLTFLG = AttributesEnum.ShiftDfltFlg.index();
    public static final int TRANSWCID = AttributesEnum.TransWcId.index();
    public static final int LOVUSERVO1 = AttributesEnum.LOVUserVO1.index();
    public static final int LOVUSERVO2 = AttributesEnum.LOVUserVO2.index();
    public static final int LOVSHIFTVO1 = AttributesEnum.LOVShiftVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MnfWsShiftVORowImpl() {
    }

    /**
     * Gets MnfWsShiftEO entity object.
     * @return the MnfWsShiftEO
     */
    public MnfWsShiftEOImpl getMnfWsShiftEO() {
        return (MnfWsShiftEOImpl) getEntity(ENTITY_MNFWSSHIFTEO);
    }

    /**
     * Gets the attribute value for CLD_ID using the alias name CldId.
     * @return the CLD_ID
     */
    public String getCldId() {
        return (String) getAttributeInternal(CLDID);
    }

    /**
     * Sets <code>value</code> as attribute value for CLD_ID using the alias name CldId.
     * @param value value to set the CLD_ID
     */
    public void setCldId(String value) {
        setAttributeInternal(CLDID, value);
    }

    /**
     * Gets the attribute value for HO_ORG_ID using the alias name HoOrgId.
     * @return the HO_ORG_ID
     */
    public String getHoOrgId() {
        return (String) getAttributeInternal(HOORGID);
    }

    /**
     * Sets <code>value</code> as attribute value for HO_ORG_ID using the alias name HoOrgId.
     * @param value value to set the HO_ORG_ID
     */
    public void setHoOrgId(String value) {
        setAttributeInternal(HOORGID, value);
    }

    /**
     * Gets the attribute value for SHIFT_ACTV using the alias name ShiftActv.
     * @return the SHIFT_ACTV
     */
    public String getShiftActv() {
        return (String) getAttributeInternal(SHIFTACTV);
    }

    /**
     * Sets <code>value</code> as attribute value for SHIFT_ACTV using the alias name ShiftActv.
     * @param value value to set the SHIFT_ACTV
     */
    public void setShiftActv(String value) {
        setAttributeInternal(SHIFTACTV, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransKey.
     * @return the TransKey
     */
    public Object getTransKey() {
        return getKey();
        //return (Object) getAttributeInternal(TRANSKEY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransKey.
     * @param value value to set the  TransKey
     */
    public void setTransKey(Object value) {
        setAttributeInternal(TRANSKEY, value);
    }

    /**
     * Gets the attribute value for SHIFT_DFLT_FLG using the alias name ShiftDfltFlg.
     * @return the SHIFT_DFLT_FLG
     */
    public String getShiftDfltFlg() {
        return (String) getAttributeInternal(SHIFTDFLTFLG);
    }

    /**
     * Sets <code>value</code> as attribute value for SHIFT_DFLT_FLG using the alias name ShiftDfltFlg.
     * @param value value to set the SHIFT_DFLT_FLG
     */
    public void setShiftDfltFlg(String value) {
        setAttributeInternal(SHIFTDFLTFLG, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransWcId.
     * @return the TransWcId
     */
    public String getTransWcId() {
        
       return getAm().getMnfWs1().getCurrentRow().getAttribute("WcId").toString();
        //return (String) getAttributeInternal(TRANSWCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransWcId.
     * @param value value to set the  TransWcId
     */
    public void setTransWcId(String value) {
        setAttributeInternal(TRANSWCID, value);
    }

    /**
     * Gets the attribute value for SHIFT_ID using the alias name ShiftId.
     * @return the SHIFT_ID
     */
    public String getShiftId() {
        return (String) getAttributeInternal(SHIFTID);
    }

    /**
     * Sets <code>value</code> as attribute value for SHIFT_ID using the alias name ShiftId.
     * @param value value to set the SHIFT_ID
     */
    public void setShiftId(String value) {
        if(!getAm().duplicateShiftEo(getTransShiftName())){
            setAttributeInternal(SHIFTID, value);
        }
        else {
            setAttributeInternal(TRANSSHIFTNAME, null);
            
           // showFacesMsg("Duplicate Shift!! ", " ", FacesMessage.SEVERITY_ERROR, null);
            
           FacesMessage msg = new FacesMessage("Duplicate Shift!! ");
           msg.setSeverity(FacesMessage.SEVERITY_ERROR);
           FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     * Gets the attribute value for SLOC_ID using the alias name SlocId.
     * @return the SLOC_ID
     */
    public Integer getSlocId() {
        return (Integer) getAttributeInternal(SLOCID);
    }

    /**
     * Sets <code>value</code> as attribute value for SLOC_ID using the alias name SlocId.
     * @param value value to set the SLOC_ID
     */
    public void setSlocId(Integer value) {
        setAttributeInternal(SLOCID, value);
    }

    /**
     * Gets the attribute value for USR_ID_CREATE using the alias name UsrIdCreate.
     * @return the USR_ID_CREATE
     */
    public Integer getUsrIdCreate() {
        return (Integer) getAttributeInternal(USRIDCREATE);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_CREATE using the alias name UsrIdCreate.
     * @param value value to set the USR_ID_CREATE
     */
    public void setUsrIdCreate(Integer value) {
        setAttributeInternal(USRIDCREATE, value);
    }

    /**
     * Gets the attribute value for USR_ID_CREATE_DT using the alias name UsrIdCreateDt.
     * @return the USR_ID_CREATE_DT
     */
    public Timestamp getUsrIdCreateDt() {
        return (Timestamp) getAttributeInternal(USRIDCREATEDT);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_CREATE_DT using the alias name UsrIdCreateDt.
     * @param value value to set the USR_ID_CREATE_DT
     */
    public void setUsrIdCreateDt(Timestamp value) {
        setAttributeInternal(USRIDCREATEDT, value);
    }

    /**
     * Gets the attribute value for USR_ID_MOD using the alias name UsrIdMod.
     * @return the USR_ID_MOD
     */
    public Integer getUsrIdMod() {
        return (Integer) getAttributeInternal(USRIDMOD);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_MOD using the alias name UsrIdMod.
     * @param value value to set the USR_ID_MOD
     */
    public void setUsrIdMod(Integer value) {
        setAttributeInternal(USRIDMOD, value);
    }

    /**
     * Gets the attribute value for USR_ID_MOD_DT using the alias name UsrIdModDt.
     * @return the USR_ID_MOD_DT
     */
    public Timestamp getUsrIdModDt() {
        return (Timestamp) getAttributeInternal(USRIDMODDT);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_MOD_DT using the alias name UsrIdModDt.
     * @param value value to set the USR_ID_MOD_DT
     */
    public void setUsrIdModDt(Timestamp value) {
        setAttributeInternal(USRIDMODDT, value);
    }

    /**
     * Gets the attribute value for WS_ID using the alias name WsId.
     * @return the WS_ID
     */
    public String getWsId() {
        return (String) getAttributeInternal(WSID);
    }

    /**
     * Sets <code>value</code> as attribute value for WS_ID using the alias name WsId.
     * @param value value to set the WS_ID
     */
    public void setWsId(String value) {
        setAttributeInternal(WSID, value);
    }
   
    /**
     * Gets the attribute value for the calculated attribute TransShiftName.
     * @return the TransShiftName
     */
    public String getTransShiftName() {
        
            if(getShiftId() != null)
            {                
                ViewObjectImpl voshift = getAm().getLOVAllShiftVO();
                voshift.setNamedWhereClauseParam("BindCldId", getCldId());
                voshift.setNamedWhereClauseParam("BindSlocId", getSlocId());
                voshift.setNamedWhereClauseParam("BindHoOrgId", getHoOrgId());
              //  voshift.setNamedWhereClauseParam("BindWcId", this.getTransWcId());
                voshift.executeQuery();
                //System.out.println("ShiftVOROWIML =========  " +voshift.getRowCount()  );
                Row[] rowShift =  voshift.getFilteredRows("ShiftId", getShiftId()); 
                if(rowShift.length > 0)
                {
                    if(rowShift[0].getAttribute("ShiftNm").toString() != null)    
                    {
                        shiftStarttime = (Timestamp)(rowShift[0].getAttribute("ShiftStrtTm"));
                        shiftEndtime = (Timestamp)(rowShift[0].getAttribute("ShiftEndTm"));
                        //System.out.println("Start time of shift is :" + shiftStarttime );
                        return rowShift[0].getAttribute("ShiftNm").toString();
                    }
                }
            }
            return (String) getAttributeInternal(TRANSSHIFTNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransShiftName.
     * @param value value to set the  TransShiftName
     */
    public void setTransShiftName(String value) {
        setAttributeInternal(TRANSSHIFTNAME, value);
    }


    /**
     * Gets the attribute value for the calculated attribute TransShiftEndTime.
     * @return the TransShiftEndTime
     */
    public Timestamp getTransShiftEndTime() {
        if(shiftEndtime !=null)
            return shiftEndtime; 
                else 
                return (Timestamp) getAttributeInternal(TRANSSHIFTENDTIME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransShiftEndTime.
     * @param value value to set the  TransShiftEndTime
     */
    public void setTransShiftEndTime(Timestamp value) {
        if(!getAm().duplicateShiftEo(getTransShiftName())){
            setAttributeInternal(TRANSSHIFTENDTIME, value);
        }
       
    }

    /**
     * Gets the attribute value for the calculated attribute TransShiftStartTime.
     * @return the TransShiftStartTime
     */
    public Timestamp getTransShiftStartTime() {
        if(shiftStarttime !=null)
            return shiftStarttime; 
                else 
        return (Timestamp) getAttributeInternal(TRANSSHIFTSTARTTIME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransShiftStartTime.
     * @param value value to set the  TransShiftStartTime
     */
    public void setTransShiftStartTime(Timestamp value) {
        if(!getAm().duplicateShiftEo(getTransShiftName())){
            setAttributeInternal(TRANSSHIFTSTARTTIME, value);
        }
        
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVUserVO1.
     */
    public RowSet getLOVUserVO1() {
        return (RowSet) getAttributeInternal(LOVUSERVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVUserVO2.
     */
    public RowSet getLOVUserVO2() {
        return (RowSet) getAttributeInternal(LOVUSERVO2);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVShiftVO1.
     */
    public RowSet getLOVShiftVO1() {
        return (RowSet) getAttributeInternal(LOVSHIFTVO1);
    }

    /** Tp get AM Instance*/
    private MNFWorkStationAppAMImpl getAm() {
    return (MNFWorkStationAppAMImpl) getApplicationModule();
    }
    public void showFacesMsg(String msgHdr, String msgDtl, javax.faces.application.FacesMessage.Severity msgSeverity,
                             String msgMode) {
     
        FacesMessage msg = new FacesMessage(msgHdr);
        msg.setDetail(msgDtl);
        msg.setSeverity(msgSeverity);
        FacesContext.getCurrentInstance().addMessage(null, msg);
     
    }
}

