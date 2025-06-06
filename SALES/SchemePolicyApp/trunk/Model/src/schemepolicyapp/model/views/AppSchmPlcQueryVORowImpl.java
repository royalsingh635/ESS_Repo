package schemepolicyapp.model.views;

import java.sql.Timestamp;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Jun 05 11:35:45 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppSchmPlcQueryVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        SlocId,
        HoOrgId,
        SchmPlcBasis,
        SchmPlcBasisDesc,
        SchmId,
        SchmNm,
        CatgId,
        CatgDesc,
        EoId,
        EoNm,
        ExecId,
        ExecDesc,
        ValidFrom,
        ValidTo,
        DelChkTrans;
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
    public static final int SCHMPLCBASIS = AttributesEnum.SchmPlcBasis.index();
    public static final int SCHMPLCBASISDESC = AttributesEnum.SchmPlcBasisDesc.index();
    public static final int SCHMID = AttributesEnum.SchmId.index();
    public static final int SCHMNM = AttributesEnum.SchmNm.index();
    public static final int CATGID = AttributesEnum.CatgId.index();
    public static final int CATGDESC = AttributesEnum.CatgDesc.index();
    public static final int EOID = AttributesEnum.EoId.index();
    public static final int EONM = AttributesEnum.EoNm.index();
    public static final int EXECID = AttributesEnum.ExecId.index();
    public static final int EXECDESC = AttributesEnum.ExecDesc.index();
    public static final int VALIDFROM = AttributesEnum.ValidFrom.index();
    public static final int VALIDTO = AttributesEnum.ValidTo.index();
    public static final int DELCHKTRANS = AttributesEnum.DelChkTrans.index();

    /**
     * This is the default constructor (do not remove).
     */
    public AppSchmPlcQueryVORowImpl() {
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
     * Gets the attribute value for the calculated attribute SchmPlcBasis.
     * @return the SchmPlcBasis
     */
    public Integer getSchmPlcBasis() {
        return (Integer) getAttributeInternal(SCHMPLCBASIS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SchmPlcBasis.
     * @param value value to set the  SchmPlcBasis
     */
    public void setSchmPlcBasis(Integer value) {
        setAttributeInternal(SCHMPLCBASIS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SchmPlcBasisDesc.
     * @return the SchmPlcBasisDesc
     */
    public String getSchmPlcBasisDesc() {
        return (String) getAttributeInternal(SCHMPLCBASISDESC);
    }

    /**
     * Gets the attribute value for the calculated attribute SchmId.
     * @return the SchmId
     */
    public String getSchmId() {
        return (String) getAttributeInternal(SCHMID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SchmId.
     * @param value value to set the  SchmId
     */
    public void setSchmId(String value) {
        setAttributeInternal(SCHMID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SchmNm.
     * @return the SchmNm
     */
    public String getSchmNm() {
        return (String) getAttributeInternal(SCHMNM);
    }

    /**
     * Gets the attribute value for the calculated attribute CatgId.
     * @return the CatgId
     */
    public Integer getCatgId() {
        return (Integer) getAttributeInternal(CATGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CatgId.
     * @param value value to set the  CatgId
     */
    public void setCatgId(Integer value) {
        setAttributeInternal(CATGID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CatgDesc.
     * @return the CatgDesc
     */
    public String getCatgDesc() {
        return (String) getAttributeInternal(CATGDESC);
    }

    /**
     * Gets the attribute value for the calculated attribute EoId.
     * @return the EoId
     */
    public Integer getEoId() {
        return (Integer) getAttributeInternal(EOID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute EoId.
     * @param value value to set the  EoId
     */
    public void setEoId(Integer value) {
        setAttributeInternal(EOID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute EoNm.
     * @return the EoNm
     */
    public String getEoNm() {
        return (String) getAttributeInternal(EONM);
    }

    /**
     * Gets the attribute value for the calculated attribute ExecId.
     * @return the ExecId
     */
    public Integer getExecId() {
        return (Integer) getAttributeInternal(EXECID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ExecId.
     * @param value value to set the  ExecId
     */
    public void setExecId(Integer value) {
        setAttributeInternal(EXECID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ExecDesc.
     * @return the ExecDesc
     */
    public String getExecDesc() {
        return (String) getAttributeInternal(EXECDESC);
    }

    /**
     * Gets the attribute value for the calculated attribute ValidFrom.
     * @return the ValidFrom
     */
    public Timestamp getValidFrom() {
        return (Timestamp) getAttributeInternal(VALIDFROM);
    }

    /**
     * Gets the attribute value for the calculated attribute ValidTo.
     * @return the ValidTo
     */
    public Timestamp getValidTo() {
        return (Timestamp) getAttributeInternal(VALIDTO);
    }

    /**
     * Gets the attribute value for the calculated attribute DelChkTrans.
     * @return the DelChkTrans
     */
    public String getDelChkTrans() {
        return (String) getAttributeInternal(DELCHKTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute DelChkTrans.
     * @param value value to set the  DelChkTrans
     */
    public void setDelChkTrans(String value) {
        System.out.println("IN setter value of check box is ::: "+value);
        setAttributeInternal(DELCHKTRANS, value);
    }
}

