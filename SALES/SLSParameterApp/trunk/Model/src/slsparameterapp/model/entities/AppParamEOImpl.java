package slsparameterapp.model.entities;

import adf.utils.bean.StaticValue;
import adf.utils.ebiz.EbizParams;

import adf.utils.model.ADFModelUtils;

import java.sql.Types;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;

import slsparameterapp.model.services.AppModuleAMImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Oct 26 12:32:14 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppParamEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        SlocId,
        HoOrgId,
        ParamId,
        ParamDesc,
        ParamType,
        ParamBasis,
        ParamVal,
        ParamCoaId,
        ParamActv,
        UsrIdCreate,
        UsrIdCreateDt,
        UsrIdMod,
        UsrIdModDt,
        ParamSetId,
        InactvReason,
        InactvDt,
        ParamUom,
        ParamValType,
        ParamRmrk,
        ParamCatg,
        AppParamSet1;
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
    public static final int PARAMID = AttributesEnum.ParamId.index();
    public static final int PARAMDESC = AttributesEnum.ParamDesc.index();
    public static final int PARAMTYPE = AttributesEnum.ParamType.index();
    public static final int PARAMBASIS = AttributesEnum.ParamBasis.index();
    public static final int PARAMVAL = AttributesEnum.ParamVal.index();
    public static final int PARAMCOAID = AttributesEnum.ParamCoaId.index();
    public static final int PARAMACTV = AttributesEnum.ParamActv.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int PARAMSETID = AttributesEnum.ParamSetId.index();
    public static final int INACTVREASON = AttributesEnum.InactvReason.index();
    public static final int INACTVDT = AttributesEnum.InactvDt.index();
    public static final int PARAMUOM = AttributesEnum.ParamUom.index();
    public static final int PARAMVALTYPE = AttributesEnum.ParamValType.index();
    public static final int PARAMRMRK = AttributesEnum.ParamRmrk.index();
    public static final int PARAMCATG = AttributesEnum.ParamCatg.index();
    public static final int APPPARAMSET1 = AttributesEnum.AppParamSet1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public AppParamEOImpl() {
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
     * Gets the attribute value for ParamId, using the alias name ParamId.
     * @return the value of ParamId
     */
    public String getParamId() {
        return (String) getAttributeInternal(PARAMID);
    }

    /**
     * Sets <code>value</code> as the attribute value for ParamId.
     * @param value value to set the ParamId
     */
    public void setParamId(String value) {
        setAttributeInternal(PARAMID, value);
    }

    /**
     * Gets the attribute value for ParamDesc, using the alias name ParamDesc.
     * @return the value of ParamDesc
     */
    public String getParamDesc() {
        return (String) getAttributeInternal(PARAMDESC);
    }

    /**
     * Sets <code>value</code> as the attribute value for ParamDesc.
     * @param value value to set the ParamDesc
     */
    public void setParamDesc(String value) {
        setAttributeInternal(PARAMDESC, value);
    }

    /**
     * Gets the attribute value for ParamType, using the alias name ParamType.
     * @return the value of ParamType
     */
    public Integer getParamType() {
        return (Integer) getAttributeInternal(PARAMTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for ParamType.
     * @param value value to set the ParamType
     */
    public void setParamType(Integer value) {
        setAttributeInternal(PARAMTYPE, value);
    }

    /**
     * Gets the attribute value for ParamBasis, using the alias name ParamBasis.
     * @return the value of ParamBasis
     */
    public Integer getParamBasis() {
        return (Integer) getAttributeInternal(PARAMBASIS);
    }

    /**
     * Sets <code>value</code> as the attribute value for ParamBasis.
     * @param value value to set the ParamBasis
     */
    public void setParamBasis(Integer value) {
        setAttributeInternal(PARAMBASIS, value);
    }

    /**
     * Gets the attribute value for ParamVal, using the alias name ParamVal.
     * @return the value of ParamVal
     */
    public Number getParamVal() {
        return (Number) getAttributeInternal(PARAMVAL);
    }

    /**
     * Sets <code>value</code> as the attribute value for ParamVal.
     * @param value value to set the ParamVal
     */
    public void setParamVal(Number value) {
        setAttributeInternal(PARAMVAL, value);
    }

    /**
     * Gets the attribute value for ParamCoaId, using the alias name ParamCoaId.
     * @return the value of ParamCoaId
     */
    public Number getParamCoaId() {
        return (Number) getAttributeInternal(PARAMCOAID);
    }

    /**
     * Sets <code>value</code> as the attribute value for ParamCoaId.
     * @param value value to set the ParamCoaId
     */
    public void setParamCoaId(Number value) {
        setAttributeInternal(PARAMCOAID, value);
    }

    /**
     * Gets the attribute value for ParamActv, using the alias name ParamActv.
     * @return the value of ParamActv
     */
    public String getParamActv() {
        return (String) getAttributeInternal(PARAMACTV);
    }

    /**
     * Sets <code>value</code> as the attribute value for ParamActv.
     * @param value value to set the ParamActv
     */
    public void setParamActv(String value) {
        setAttributeInternal(PARAMACTV, value);
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
     * Gets the attribute value for ParamSetId, using the alias name ParamSetId.
     * @return the value of ParamSetId
     */
    public String getParamSetId() {
        return (String) getAttributeInternal(PARAMSETID);
    }

    /**
     * Sets <code>value</code> as the attribute value for ParamSetId.
     * @param value value to set the ParamSetId
     */
    public void setParamSetId(String value) {
        setAttributeInternal(PARAMSETID, value);
    }

    /**
     * Gets the attribute value for InactvReason, using the alias name InactvReason.
     * @return the value of InactvReason
     */
    public String getInactvReason() {
        return (String) getAttributeInternal(INACTVREASON);
    }

    /**
     * Sets <code>value</code> as the attribute value for InactvReason.
     * @param value value to set the InactvReason
     */
    public void setInactvReason(String value) {
        setAttributeInternal(INACTVREASON, value);
    }

    /**
     * Gets the attribute value for InactvDt, using the alias name InactvDt.
     * @return the value of InactvDt
     */
    public Timestamp getInactvDt() {
        return (Timestamp) getAttributeInternal(INACTVDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for InactvDt.
     * @param value value to set the InactvDt
     */
    public void setInactvDt(Timestamp value) {
        setAttributeInternal(INACTVDT, value);
    }

    /**
     * Gets the attribute value for ParamUom, using the alias name ParamUom.
     * @return the value of ParamUom
     */
    public String getParamUom() {
        return (String) getAttributeInternal(PARAMUOM);
    }

    /**
     * Sets <code>value</code> as the attribute value for ParamUom.
     * @param value value to set the ParamUom
     */
    public void setParamUom(String value) {
        setAttributeInternal(PARAMUOM, value);
    }

    /**
     * Gets the attribute value for ParamValType, using the alias name ParamValType.
     * @return the value of ParamValType
     */
    public Integer getParamValType() {
        return (Integer) getAttributeInternal(PARAMVALTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for ParamValType.
     * @param value value to set the ParamValType
     */
    public void setParamValType(Integer value) {
        setAttributeInternal(PARAMVALTYPE, value);
    }

    /**
     * Gets the attribute value for ParamRmrk, using the alias name ParamRmrk.
     * @return the value of ParamRmrk
     */
    public String getParamRmrk() {
        return (String) getAttributeInternal(PARAMRMRK);
    }

    /**
     * Sets <code>value</code> as the attribute value for ParamRmrk.
     * @param value value to set the ParamRmrk
     */
    public void setParamRmrk(String value) {
        setAttributeInternal(PARAMRMRK, value);
    }

    /**
     * Gets the attribute value for ParamCatg, using the alias name ParamCatg.
     * @return the value of ParamCatg
     */
    public Integer getParamCatg() {
        return (Integer) getAttributeInternal(PARAMCATG);
    }

    /**
     * Sets <code>value</code> as the attribute value for ParamCatg.
     * @param value value to set the ParamCatg
     */
    public void setParamCatg(Integer value) {
        setAttributeInternal(PARAMCATG, value);
    }

    /**
     * @return the associated entity AppParamSetEOImpl.
     */
    public AppParamSetEOImpl getAppParamSet1() {
        return (AppParamSetEOImpl) getAttributeInternal(APPPARAMSET1);
    }

    /**
     * Sets <code>value</code> as the associated entity AppParamSetEOImpl.
     */
    public void setAppParamSet1(AppParamSetEOImpl value) {
        setAttributeInternal(APPPARAMSET1, value);
    }

    /**
     * @param cldId key constituent
     * @param slocId key constituent
     * @param hoOrgId key constituent
     * @param paramId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String cldId, Integer slocId, String hoOrgId, String paramId) {
        return new Key(new Object[] { cldId, slocId, hoOrgId, paramId });
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("slsparameterapp.model.entities.AppParamEO");
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        super.create(attributeList);
        setUsrIdCreate(EbizParams.GLBL_APP_USR());
        setUsrIdCreateDt(StaticValue.getCurrDtWidTimestamp());
        setParamId(getParamSetIdFromFun(getSlocId(),getCldId(),getHoOrgId(),getHoOrgId(),2078,0,"APP$PARAM",0));
        setParamActv("Y");
    }

    /**
     * Add locking logic here.
     */
    public void lock() {
       // super.lock();
    }

    /**
     * Custom DML update/insert/delete logic here.
     * @param operation the operation type
     * @param e the transaction event
     */
    protected void doDML(int operation, TransactionEvent e) {
        if(operation == DML_UPDATE){
            setUsrIdMod(EbizParams.GLBL_APP_USR());
            setUsrIdModDt(StaticValue.getCurrDtWidTimestamp());
        }
        super.doDML(operation, e);
    }
    /**
     * fn_app_get_prf_id(
        p_sloc_id         NUMBER,
        p_cld_id          VARCHAR2,
        p_ho_org_id       VARCHAR2,
        p_org_id          VARCHAR2,
        p_doc_id          NUMBER,---------DOCUMENT ID AS REGISTERED IN APP$GLBL$DOC
        p_doc_type_id     NUMBER DEFAULT NULL,---------DOCUMENT TYPE ID 
        p_tablename       VARCHAR2,
        p_doc_sub_type_id NUMBER DEFAULT 0 )
     */
    public String getParamSetIdFromFun(Integer slocId, String cldId, String hoOrgId, String orgId, Integer docId,
                                       Integer docTypeId, String tableName, Integer docSubTypeId) {
        Object o = null;
        String res = null;
        try {
            o = ADFModelUtils.callFunction(getApplicationModule(),
                                           new StringBuilder("app.fn_app_get_prf_id(?,?,?,?,?,?,?,?)"), new Object[] {
                                           slocId, cldId, hoOrgId, orgId, docId, docTypeId, tableName, docSubTypeId
            }, Types.VARCHAR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (o != null)
            res = o.toString();
        return res;
        //return (String) callStoredFunction(STRING, "mnf.fn_mnf_get_prf_id(?,?,?,?,?,?,?,?)", );
    }
    /**
     * Method to get instance of currrent Application Module
     * @return
     */
    protected AppModuleAMImpl getApplicationModule() {
        return (AppModuleAMImpl) ADFModelUtils.resolvEl("#{data.AppModuleAMDataControl.dataProvider}");
    }

}

