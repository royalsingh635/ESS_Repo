package mnfWorkStationApp.model.entities;

import adf.utils.ebiz.EbizParams;

import javax.el.ELContext;
import javax.el.ValueExpression;

import oracle.adf.share.ADFContext;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.RowInconsistentException;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Sep 02 12:14:05 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MnfWsResEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        SlocId,
        HoOrgId,
        WsId,
        WsEmpId,
        WsEmpActv,
        UsrIdCreate,
        UsrIdCreateDt,
        UsrIdMod,
        UsrIdModDt,
        ShiftId,
        PicFlg,
        DfltFlg,
        MnfWs;
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
    public static final int WSID = AttributesEnum.WsId.index();
    public static final int WSEMPID = AttributesEnum.WsEmpId.index();
    public static final int WSEMPACTV = AttributesEnum.WsEmpActv.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int SHIFTID = AttributesEnum.ShiftId.index();
    public static final int PICFLG = AttributesEnum.PicFlg.index();
    public static final int DFLTFLG = AttributesEnum.DfltFlg.index();
    public static final int MNFWS = AttributesEnum.MnfWs.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MnfWsResEOImpl() {
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("mnfWorkStationApp.model.entities.MnfWsResEO");
    }


    /**
     * Gets the attribute value for CldId, using the alias name CldId.
     * @return the value of CldId
     */
    public String getCldId() {
        return (String) getAttributeInternal(CLDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CldId.
     * @param value value to set the CldId
     */
    public void setCldId(String value) {
        setAttributeInternal(CLDID, value);
    }

    /**
     * Gets the attribute value for SlocId, using the alias name SlocId.
     * @return the value of SlocId
     */
    public Integer getSlocId() {
        return (Integer) getAttributeInternal(SLOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for SlocId.
     * @param value value to set the SlocId
     */
    public void setSlocId(Integer value) {
        setAttributeInternal(SLOCID, value);
    }

    /**
     * Gets the attribute value for HoOrgId, using the alias name HoOrgId.
     * @return the value of HoOrgId
     */
    public String getHoOrgId() {
        return (String) getAttributeInternal(HOORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for HoOrgId.
     * @param value value to set the HoOrgId
     */
    public void setHoOrgId(String value) {
        setAttributeInternal(HOORGID, value);
    }

    /**
     * Gets the attribute value for WsId, using the alias name WsId.
     * @return the value of WsId
     */
    public String getWsId() {
        return (String) getAttributeInternal(WSID);
    }

    /**
     * Sets <code>value</code> as the attribute value for WsId.
     * @param value value to set the WsId
     */
    public void setWsId(String value) {
        setAttributeInternal(WSID, value);
    }

    /**
     * Gets the attribute value for WsEmpId, using the alias name WsEmpId.
     * @return the value of WsEmpId
     */
    public Number getWsEmpId() {
        return (Number) getAttributeInternal(WSEMPID);
    }

    /**
     * Sets <code>value</code> as the attribute value for WsEmpId.
     * @param value value to set the WsEmpId
     */
    public void setWsEmpId(Number value) {
        setAttributeInternal(WSEMPID, value);
    }

    /**
     * Gets the attribute value for WsEmpActv, using the alias name WsEmpActv.
     * @return the value of WsEmpActv
     */
    public String getWsEmpActv() {
        return (String) getAttributeInternal(WSEMPACTV);
    }

    /**
     * Sets <code>value</code> as the attribute value for WsEmpActv.
     * @param value value to set the WsEmpActv
     */
    public void setWsEmpActv(String value) {
        setAttributeInternal(WSEMPACTV, value);
    }

    /**
     * Gets the attribute value for UsrIdCreate, using the alias name UsrIdCreate.
     * @return the value of UsrIdCreate
     */
    public Integer getUsrIdCreate() {
        return (Integer) getAttributeInternal(USRIDCREATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdCreate.
     * @param value value to set the UsrIdCreate
     */
    public void setUsrIdCreate(Integer value) {
        setAttributeInternal(USRIDCREATE, value);
    }

    /**
     * Gets the attribute value for UsrIdCreateDt, using the alias name UsrIdCreateDt.
     * @return the value of UsrIdCreateDt
     */
    public Timestamp getUsrIdCreateDt() {
        return (Timestamp) getAttributeInternal(USRIDCREATEDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdCreateDt.
     * @param value value to set the UsrIdCreateDt
     */
    public void setUsrIdCreateDt(Timestamp value) {
        setAttributeInternal(USRIDCREATEDT, value);
    }

    /**
     * Gets the attribute value for UsrIdMod, using the alias name UsrIdMod.
     * @return the value of UsrIdMod
     */
    public Integer getUsrIdMod() {
        return (Integer) getAttributeInternal(USRIDMOD);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdMod.
     * @param value value to set the UsrIdMod
     */
    public void setUsrIdMod(Integer value) {
        setAttributeInternal(USRIDMOD, value);
    }

    /**
     * Gets the attribute value for UsrIdModDt, using the alias name UsrIdModDt.
     * @return the value of UsrIdModDt
     */
    public Timestamp getUsrIdModDt() {
        return (Timestamp) getAttributeInternal(USRIDMODDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdModDt.
     * @param value value to set the UsrIdModDt
     */
    public void setUsrIdModDt(Timestamp value) {
        setAttributeInternal(USRIDMODDT, value);
    }

    /**
     * Gets the attribute value for ShiftId, using the alias name ShiftId.
     * @return the value of ShiftId
     */
    public String getShiftId() {
        return (String) getAttributeInternal(SHIFTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for ShiftId.
     * @param value value to set the ShiftId
     */
    public void setShiftId(String value) {
        setAttributeInternal(SHIFTID, value);
    }

    /**
     * Gets the attribute value for PicFlg, using the alias name PicFlg.
     * @return the value of PicFlg
     */
    public String getPicFlg() {
        return (String) getAttributeInternal(PICFLG);
    }

    /**
     * Sets <code>value</code> as the attribute value for PicFlg.
     * @param value value to set the PicFlg
     */
    public void setPicFlg(String value) {
        setAttributeInternal(PICFLG, value);
    }

    /**
     * Gets the attribute value for DfltFlg, using the alias name DfltFlg.
     * @return the value of DfltFlg
     */
    public String getDfltFlg() {
        return (String) getAttributeInternal(DFLTFLG);
    }

    /**
     * Sets <code>value</code> as the attribute value for DfltFlg.
     * @param value value to set the DfltFlg
     */
    public void setDfltFlg(String value) {
        setAttributeInternal(DFLTFLG, value);
    }

    /**
     * @return the associated entity MnfWsEOImpl.
     */
    public MnfWsEOImpl getMnfWs() {
        return (MnfWsEOImpl) getAttributeInternal(MNFWS);
    }

    /**
     * Sets <code>value</code> as the associated entity MnfWsEOImpl.
     */
    public void setMnfWs(MnfWsEOImpl value) {
        setAttributeInternal(MNFWS, value);
    }


    /**
     * @param cldId key constituent
     * @param slocId key constituent
     * @param hoOrgId key constituent
     * @param wsId key constituent
     * @param wsEmpId key constituent
     * @param shiftId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String cldId, Integer slocId, String hoOrgId, String wsId, Number wsEmpId,
                                       String shiftId) {
        return new Key(new Object[] { cldId, slocId, hoOrgId, wsId, wsEmpId, shiftId });
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
       // setUsrIdCreate(Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString()));
       if(EbizParams.GLBL_APP_USR() != null)
               {
                   setUsrIdCreate(EbizParams.GLBL_APP_USR());
               }
        setUsrIdCreateDt(new Timestamp(System.currentTimeMillis()));
        setWsEmpActv("Y");
        setDfltFlg("N");
        super.create(attributeList);
    }

    /**
     * Add entity remove logic in this method.
     */
    public void remove() {
        super.remove();
    }

    /**
     * Add locking logic here.
     */
    public void lock() {
        try {
            super.lock();
        } catch (RowInconsistentException e) {
            refresh(REFRESH_WITH_DB_ONLY_IF_UNCHANGED | REFRESH_CONTAINEES);
            super.lock();
        }
    }

    /**
     * Custom DML update/insert/delete logic here.
     * @param operation the operation type
     * @param e the transaction event
     */
    protected void doDML(int operation, TransactionEvent e) {
        if(operation == DML_UPDATE)
        {
           // setAttributeInternal(USRIDMOD, Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString()));
          // setUsrIdMod(Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString()));
          if(EbizParams.GLBL_APP_USR() != null)
                  {
                      setUsrIdMod(EbizParams.GLBL_APP_USR());
                  }
            setUsrIdModDt(new oracle.jbo.domain.Timestamp(System.currentTimeMillis()));
        }
        super.doDML(operation, e);
       
       
        //super.doDML(operation, e);
    }
    
    /**----------------------------------------------------------------**/
    
    /**Method to resolve page flow scope parameter.*/
    public Object resolvEl(String data) {
              ADFContext adfCtx = ADFContext.getCurrent();
              ELContext elContext = adfCtx.getELContext();
              ValueExpression valueExp = adfCtx.getExpressionFactory().createValueExpression(elContext, data,
    Object.class);
              Object Message = valueExp.getValue(elContext);
              return Message;
          }
    
    /**----------------------------------------------------------------**/ 
}

