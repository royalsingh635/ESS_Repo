package mnfWorkStationApp.model.entities;

import adf.utils.ebiz.EbizParams;

import java.math.BigDecimal;

import javax.el.ELContext;
import javax.el.ValueExpression;

import oracle.adf.share.ADFContext;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.RowInconsistentException;
import oracle.jbo.domain.NClobDomain;
import oracle.jbo.domain.NullValue;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Aug 27 09:39:25 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MnfWsCapEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        SlocId,
        HoOrgId,
        WsId,
        ItmId,
        UsrIdCreate,
        UsrIdCreateDt,
        UsrIdMod,
        UsrIdModDt,
        ItmCapUom,
        ItmMaxCap,
        ItmAvgCap,
        ItmCapHours,
        MaxEff,
        MinEff,
        AvgEff,
        MinRunTm,
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
    public static final int ITMID = AttributesEnum.ItmId.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int ITMCAPUOM = AttributesEnum.ItmCapUom.index();
    public static final int ITMMAXCAP = AttributesEnum.ItmMaxCap.index();
    public static final int ITMAVGCAP = AttributesEnum.ItmAvgCap.index();
    public static final int ITMCAPHOURS = AttributesEnum.ItmCapHours.index();
    public static final int MAXEFF = AttributesEnum.MaxEff.index();
    public static final int MINEFF = AttributesEnum.MinEff.index();
    public static final int AVGEFF = AttributesEnum.AvgEff.index();
    public static final int MINRUNTM = AttributesEnum.MinRunTm.index();
    public static final int MNFWS = AttributesEnum.MnfWs.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MnfWsCapEOImpl() {
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("mnfWorkStationApp.model.entities.MnfWsCapEO");
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
     * Gets the attribute value for ItmId, using the alias name ItmId.
     * @return the value of ItmId
     */
    public String getItmId() {
        return (String) getAttributeInternal(ITMID);
    }

    /**
     * Sets <code>value</code> as the attribute value for ItmId.
     * @param value value to set the ItmId
     */
    public void setItmId(String value) {
        setAttributeInternal(ITMID, value);
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
     * Gets the attribute value for ItmCapUom, using the alias name ItmCapUom.
     * @return the value of ItmCapUom
     */
    public String getItmCapUom() {
        return (String) getAttributeInternal(ITMCAPUOM);
    }

    /**
     * Sets <code>value</code> as the attribute value for ItmCapUom.
     * @param value value to set the ItmCapUom
     */
    public void setItmCapUom(String value) {
        setAttributeInternal(ITMCAPUOM, value);
    }

    /**
     * Gets the attribute value for ItmMaxCap, using the alias name ItmMaxCap.
     * @return the value of ItmMaxCap
     */
    public Number getItmMaxCap() {
        return (Number) getAttributeInternal(ITMMAXCAP);
    }

    /**
     * Sets <code>value</code> as the attribute value for ItmMaxCap.
     * @param value value to set the ItmMaxCap
     */
    public void setItmMaxCap(Number value) {
        setAttributeInternal(ITMMAXCAP, value);
    }

    /**
     * Gets the attribute value for ItmAvgCap, using the alias name ItmAvgCap.
     * @return the value of ItmAvgCap
     */
    public Number getItmAvgCap() {
        return (Number) getAttributeInternal(ITMAVGCAP);
    }

    /**
     * Sets <code>value</code> as the attribute value for ItmAvgCap.
     * @param value value to set the ItmAvgCap
     */
    public void setItmAvgCap(Number value) {
        setAttributeInternal(ITMAVGCAP, value);
    }

    /**
     * Gets the attribute value for ItmCapHours, using the alias name ItmCapHours.
     * @return the value of ItmCapHours
     */
    public Number getItmCapHours() {
        return (Number) getAttributeInternal(ITMCAPHOURS);
    }

    /**
     * Sets <code>value</code> as the attribute value for ItmCapHours.
     * @param value value to set the ItmCapHours
     */
    public void setItmCapHours(Number value) {
        setAttributeInternal(ITMCAPHOURS, value);
    }

    /**
     * Gets the attribute value for MaxEff, using the alias name MaxEff.
     * @return the value of MaxEff
     */
    public Number getMaxEff() {
        return (Number) getAttributeInternal(MAXEFF);
    }

    /**
     * Sets <code>value</code> as the attribute value for MaxEff.
     * @param value value to set the MaxEff
     */
    public void setMaxEff(Number value) {
        setAttributeInternal(MAXEFF, value);
    }

    /**
     * Gets the attribute value for MinEff, using the alias name MinEff.
     * @return the value of MinEff
     */
    public Number getMinEff() {
        return (Number) getAttributeInternal(MINEFF);
    }

    /**
     * Sets <code>value</code> as the attribute value for MinEff.
     * @param value value to set the MinEff
     */
    public void setMinEff(Number value) {
        setAttributeInternal(MINEFF, value);
    }

    /**
     * Gets the attribute value for AvgEff, using the alias name AvgEff.
     * @return the value of AvgEff
     */
    public Number getAvgEff() {
        return (Number) getAttributeInternal(AVGEFF);
    }

    /**
     * Sets <code>value</code> as the attribute value for AvgEff.
     * @param value value to set the AvgEff
     */
    public void setAvgEff(Number value) {
        setAttributeInternal(AVGEFF, value);
    }

    /**
     * Gets the attribute value for MinRunTm, using the alias name MinRunTm.
     * @return the value of MinRunTm
     */
    public Number getMinRunTm() {
        return (Number) getAttributeInternal(MINRUNTM);
    }

    /**
     * Sets <code>value</code> as the attribute value for MinRunTm.
     * @param value value to set the MinRunTm
     */
    public void setMinRunTm(Number value) {
        setAttributeInternal(MINRUNTM, value);
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
     * @param itmId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String cldId, Integer slocId, String hoOrgId, String wsId, String itmId) {
        return new Key(new Object[] { cldId, slocId, hoOrgId, wsId, itmId });
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        //setAttributeInternal(USRIDCREATE, Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString()));
       // setUsrIdCreate(Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString()));
        if(EbizParams.GLBL_APP_USR() != null)
                {
                    setUsrIdCreate(EbizParams.GLBL_APP_USR());
                }
        setUsrIdCreateDt(new oracle.jbo.domain.Timestamp(new java.util.Date()));
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
        //super.doDML(operation, e);
        if(operation == DML_UPDATE)
        {
           // setAttributeInternal(USRIDMOD, Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString()));
           if(EbizParams.GLBL_APP_USR() != null)
                   {
                       setUsrIdMod(EbizParams.GLBL_APP_USR());
                   }
            setUsrIdModDt(new oracle.jbo.domain.Timestamp(System.currentTimeMillis()));
        }
        super.doDML(operation, e);
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

