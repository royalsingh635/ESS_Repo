package slssalesinvoiceapp.model.module.entity;

import adf.utils.bean.StaticValue;
import adf.utils.ebiz.EbizParams;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.SQLException;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import oracle.adf.share.ADFContext;

import oracle.jbo.AttributeList;
import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.domain.BFileDomain;
import oracle.jbo.domain.NClobDomain;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Apr 02 16:45:53 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class SlsInvShipItmEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CldId {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getCldId();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setCldId((String) value);
            }
        }
        ,
        SlocId {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getSlocId();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setSlocId((Integer) value);
            }
        }
        ,
        OrgId {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getOrgId();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setOrgId((String) value);
            }
        }
        ,
        HoOrgId {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getHoOrgId();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setHoOrgId((String) value);
            }
        }
        ,
        DocId {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getDocId();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setDocId((String) value);
            }
        }
        ,
        ShipId {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getShipId();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setShipId((String) value);
            }
        }
        ,
        SoId {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getSoId();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setSoId((String) value);
            }
        }
        ,
        ItmId {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getItmId();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setItmId((String) value);
            }
        }
        ,
        ItmRate {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getItmRate();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setItmRate((BigDecimal) value);
            }
        }
        ,
        ItmUom {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getItmUom();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setItmUom((String) value);
            }
        }
        ,
        ItmShipQty {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getItmShipQty();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setItmShipQty((BigDecimal) value);
            }
        }
        ,
        ItmDiscTyp {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getItmDiscTyp();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setItmDiscTyp((String) value);
            }
        }
        ,
        ItmDiscVal {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getItmDiscVal();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setItmDiscVal((BigDecimal) value);
            }
        }
        ,
        ItmAmtBs {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getItmAmtBs();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setItmAmtBs((BigDecimal) value);
            }
        }
        ,
        ItmAmtSp {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getItmAmtSp();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setItmAmtSp((BigDecimal) value);
            }
        }
        ,
        Remarks {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getRemarks();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setRemarks((String) value);
            }
        }
        ,
        TlrncQtyType {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getTlrncQtyType();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setTlrncQtyType((String) value);
            }
        }
        ,
        TlrncQtyVal {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getTlrncQtyVal();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setTlrncQtyVal((BigDecimal) value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setUsrIdCreate((Integer) value);
            }
        }
        ,
        UsrIdCreateDt {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getUsrIdCreateDt();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setUsrIdCreateDt((Timestamp) value);
            }
        }
        ,
        UsrIdMod {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getUsrIdMod();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setUsrIdMod((Integer) value);
            }
        }
        ,
        UsrIdModDt {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getUsrIdModDt();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setUsrIdModDt((Timestamp) value);
            }
        }
        ,
        TaxRuleFlg {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getTaxRuleFlg();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setTaxRuleFlg((String) value);
            }
        }
        ,
        SchId {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getSchId();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setSchId((String) value);
            }
        }
        ,
        SuppliItmRate {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getSuppliItmRate();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setSuppliItmRate((BigDecimal) value);
            }
        }
        ,
        OldItmRate {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getOldItmRate();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setOldItmRate((BigDecimal) value);
            }
        }
        ,
        ItmAmtGsSp {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getItmAmtGsSp();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setItmAmtGsSp((BigDecimal) value);
            }
        }
        ,
        ItmAmtGsBs {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getItmAmtGsBs();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setItmAmtGsBs((BigDecimal) value);
            }
        }
        ,
        WtyId {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getWtyId();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setWtyId((String) value);
            }
        }
        ,
        SchmFlg {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getSchmFlg();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setSchmFlg((String) value);
            }
        }
        ,
        AsblAmtBs {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getAsblAmtBs();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setAsblAmtBs((BigDecimal) value);
            }
        }
        ,
        AsblAmtSp {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getAsblAmtSp();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setAsblAmtSp((BigDecimal) value);
            }
        }
        ,
        OldItmShipQty {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getOldItmShipQty();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setOldItmShipQty((BigDecimal) value);
            }
        }
        ,
        CcId {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getCcId();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setCcId((String) value);
            }
        }
        ,
        EoPartNo {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getEoPartNo();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setEoPartNo((String) value);
            }
        }
        ,
        SlsInvDtl {
            public Object get(SlsInvShipItmEOImpl obj) {
                return obj.getSlsInvDtl();
            }

            public void put(SlsInvShipItmEOImpl obj, Object value) {
                obj.setSlsInvDtl((SlsInvDtlEOImpl) value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(SlsInvShipItmEOImpl object);

        public abstract void put(SlsInvShipItmEOImpl object, Object value);

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int SHIPID = AttributesEnum.ShipId.index();
    public static final int SOID = AttributesEnum.SoId.index();
    public static final int ITMID = AttributesEnum.ItmId.index();
    public static final int ITMRATE = AttributesEnum.ItmRate.index();
    public static final int ITMUOM = AttributesEnum.ItmUom.index();
    public static final int ITMSHIPQTY = AttributesEnum.ItmShipQty.index();
    public static final int ITMDISCTYP = AttributesEnum.ItmDiscTyp.index();
    public static final int ITMDISCVAL = AttributesEnum.ItmDiscVal.index();
    public static final int ITMAMTBS = AttributesEnum.ItmAmtBs.index();
    public static final int ITMAMTSP = AttributesEnum.ItmAmtSp.index();
    public static final int REMARKS = AttributesEnum.Remarks.index();
    public static final int TLRNCQTYTYPE = AttributesEnum.TlrncQtyType.index();
    public static final int TLRNCQTYVAL = AttributesEnum.TlrncQtyVal.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int TAXRULEFLG = AttributesEnum.TaxRuleFlg.index();
    public static final int SCHID = AttributesEnum.SchId.index();
    public static final int SUPPLIITMRATE = AttributesEnum.SuppliItmRate.index();
    public static final int OLDITMRATE = AttributesEnum.OldItmRate.index();
    public static final int ITMAMTGSSP = AttributesEnum.ItmAmtGsSp.index();
    public static final int ITMAMTGSBS = AttributesEnum.ItmAmtGsBs.index();
    public static final int WTYID = AttributesEnum.WtyId.index();
    public static final int SCHMFLG = AttributesEnum.SchmFlg.index();
    public static final int ASBLAMTBS = AttributesEnum.AsblAmtBs.index();
    public static final int ASBLAMTSP = AttributesEnum.AsblAmtSp.index();
    public static final int OLDITMSHIPQTY = AttributesEnum.OldItmShipQty.index();
    public static final int CCID = AttributesEnum.CcId.index();
    public static final int EOPARTNO = AttributesEnum.EoPartNo.index();
    public static final int SLSINVDTL = AttributesEnum.SlsInvDtl.index();

    /**
     * This is the default constructor (do not remove).
     */
    public SlsInvShipItmEOImpl() {
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("slssalesinvoiceapp.model.module.entity.SlsInvShipItmEO");
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
     * Gets the attribute value for OrgId, using the alias name OrgId.
     * @return the value of OrgId
     */
    public String getOrgId() {
        return (String) getAttributeInternal(ORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for OrgId.
     * @param value value to set the OrgId
     */
    public void setOrgId(String value) {
        setAttributeInternal(ORGID, value);
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
     * Gets the attribute value for DocId, using the alias name DocId.
     * @return the value of DocId
     */
    public String getDocId() {
        return (String) getAttributeInternal(DOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for DocId.
     * @param value value to set the DocId
     */
    public void setDocId(String value) {
        setAttributeInternal(DOCID, value);
    }

    /**
     * Gets the attribute value for ShipId, using the alias name ShipId.
     * @return the value of ShipId
     */
    public String getShipId() {
        return (String) getAttributeInternal(SHIPID);
    }

    /**
     * Sets <code>value</code> as the attribute value for ShipId.
     * @param value value to set the ShipId
     */
    public void setShipId(String value) {
        setAttributeInternal(SHIPID, value);
    }

    /**
     * Gets the attribute value for SoId, using the alias name SoId.
     * @return the value of SoId
     */
    public String getSoId() {
        return (String) getAttributeInternal(SOID);
    }

    /**
     * Sets <code>value</code> as the attribute value for SoId.
     * @param value value to set the SoId
     */
    public void setSoId(String value) {
        setAttributeInternal(SOID, value);
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
     * Gets the attribute value for ItmRate, using the alias name ItmRate.
     * @return the value of ItmRate
     */
    public BigDecimal getItmRate() {
        return (BigDecimal) getAttributeInternal(ITMRATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for ItmRate.
     * @param value value to set the ItmRate
     */
    public void setItmRate(BigDecimal value) {
        setAttributeInternal(ITMRATE, value);
    }

    /**
     * Gets the attribute value for ItmUom, using the alias name ItmUom.
     * @return the value of ItmUom
     */
    public String getItmUom() {
        return (String) getAttributeInternal(ITMUOM);
    }

    /**
     * Sets <code>value</code> as the attribute value for ItmUom.
     * @param value value to set the ItmUom
     */
    public void setItmUom(String value) {
        setAttributeInternal(ITMUOM, value);
    }

    /**
     * Gets the attribute value for ItmShipQty, using the alias name ItmShipQty.
     * @return the value of ItmShipQty
     */
    public BigDecimal getItmShipQty() {
        return (BigDecimal) getAttributeInternal(ITMSHIPQTY);
    }

    /**
     * Sets <code>value</code> as the attribute value for ItmShipQty.
     * @param value value to set the ItmShipQty
     */
    public void setItmShipQty(BigDecimal value) {
        setAttributeInternal(ITMSHIPQTY, value);
    }

    /**
     * Gets the attribute value for ItmDiscTyp, using the alias name ItmDiscTyp.
     * @return the value of ItmDiscTyp
     */
    public String getItmDiscTyp() {
        return (String) getAttributeInternal(ITMDISCTYP);
    }

    /**
     * Sets <code>value</code> as the attribute value for ItmDiscTyp.
     * @param value value to set the ItmDiscTyp
     */
    public void setItmDiscTyp(String value) {
        setAttributeInternal(ITMDISCTYP, value);
    }

    /**
     * Gets the attribute value for ItmDiscVal, using the alias name ItmDiscVal.
     * @return the value of ItmDiscVal
     */
    public BigDecimal getItmDiscVal() {
        return (BigDecimal) getAttributeInternal(ITMDISCVAL);
    }

    /**
     * Sets <code>value</code> as the attribute value for ItmDiscVal.
     * @param value value to set the ItmDiscVal
     */
    public void setItmDiscVal(BigDecimal value) {
        setAttributeInternal(ITMDISCVAL, value);
    }

    /**
     * Gets the attribute value for ItmAmtBs, using the alias name ItmAmtBs.
     * @return the value of ItmAmtBs
     */
    public BigDecimal getItmAmtBs() {
        return (BigDecimal) getAttributeInternal(ITMAMTBS);
    }

    /**
     * Sets <code>value</code> as the attribute value for ItmAmtBs.
     * @param value value to set the ItmAmtBs
     */
    public void setItmAmtBs(BigDecimal value) {
        setAttributeInternal(ITMAMTBS, value);
    }

    /**
     * Gets the attribute value for ItmAmtSp, using the alias name ItmAmtSp.
     * @return the value of ItmAmtSp
     */
    public BigDecimal getItmAmtSp() {
        return (BigDecimal) getAttributeInternal(ITMAMTSP);
    }

    /**
     * Sets <code>value</code> as the attribute value for ItmAmtSp.
     * @param value value to set the ItmAmtSp
     */
    public void setItmAmtSp(BigDecimal value) {
        setAttributeInternal(ITMAMTSP, value);
    }

    /**
     * Gets the attribute value for Remarks, using the alias name Remarks.
     * @return the value of Remarks
     */
    public String getRemarks() {
        return (String) getAttributeInternal(REMARKS);
    }

    /**
     * Sets <code>value</code> as the attribute value for Remarks.
     * @param value value to set the Remarks
     */
    public void setRemarks(String value) {
        setAttributeInternal(REMARKS, value);
    }

    /**
     * Gets the attribute value for TlrncQtyType, using the alias name TlrncQtyType.
     * @return the value of TlrncQtyType
     */
    public String getTlrncQtyType() {
        return (String) getAttributeInternal(TLRNCQTYTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for TlrncQtyType.
     * @param value value to set the TlrncQtyType
     */
    public void setTlrncQtyType(String value) {
        setAttributeInternal(TLRNCQTYTYPE, value);
    }

    /**
     * Gets the attribute value for TlrncQtyVal, using the alias name TlrncQtyVal.
     * @return the value of TlrncQtyVal
     */
    public BigDecimal getTlrncQtyVal() {
        return (BigDecimal) getAttributeInternal(TLRNCQTYVAL);
    }

    /**
     * Sets <code>value</code> as the attribute value for TlrncQtyVal.
     * @param value value to set the TlrncQtyVal
     */
    public void setTlrncQtyVal(BigDecimal value) {
        setAttributeInternal(TLRNCQTYVAL, value);
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
     * Gets the attribute value for TaxRuleFlg, using the alias name TaxRuleFlg.
     * @return the value of TaxRuleFlg
     */
    public String getTaxRuleFlg() {
        return (String) getAttributeInternal(TAXRULEFLG);
    }

    /**
     * Sets <code>value</code> as the attribute value for TaxRuleFlg.
     * @param value value to set the TaxRuleFlg
     */
    public void setTaxRuleFlg(String value) {
        setAttributeInternal(TAXRULEFLG, value);
    }

    /**
     * Gets the attribute value for SchId, using the alias name SchId.
     * @return the value of SchId
     */
    public String getSchId() {
        return (String) getAttributeInternal(SCHID);
    }

    /**
     * Sets <code>value</code> as the attribute value for SchId.
     * @param value value to set the SchId
     */
    public void setSchId(String value) {
        setAttributeInternal(SCHID, value);
    }

    /**
     * Gets the attribute value for SuppliItmRate, using the alias name SuppliItmRate.
     * @return the value of SuppliItmRate
     */
    public BigDecimal getSuppliItmRate() {
        return (BigDecimal) getAttributeInternal(SUPPLIITMRATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for SuppliItmRate.
     * @param value value to set the SuppliItmRate
     */
    public void setSuppliItmRate(BigDecimal value) {
        setAttributeInternal(SUPPLIITMRATE, value);
    }

    /**
     * Gets the attribute value for OldItmRate, using the alias name OldItmRate.
     * @return the value of OldItmRate
     */
    public BigDecimal getOldItmRate() {
        return (BigDecimal) getAttributeInternal(OLDITMRATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for OldItmRate.
     * @param value value to set the OldItmRate
     */
    public void setOldItmRate(BigDecimal value) {
        setAttributeInternal(OLDITMRATE, value);
    }

    /**
     * Gets the attribute value for ItmAmtGsSp, using the alias name ItmAmtGsSp.
     * @return the value of ItmAmtGsSp
     */
    public BigDecimal getItmAmtGsSp() {
        return (BigDecimal) getAttributeInternal(ITMAMTGSSP);
    }

    /**
     * Sets <code>value</code> as the attribute value for ItmAmtGsSp.
     * @param value value to set the ItmAmtGsSp
     */
    public void setItmAmtGsSp(BigDecimal value) {
        setAttributeInternal(ITMAMTGSSP, value);
    }

    /**
     * Gets the attribute value for ItmAmtGsBs, using the alias name ItmAmtGsBs.
     * @return the value of ItmAmtGsBs
     */
    public BigDecimal getItmAmtGsBs() {
        return (BigDecimal) getAttributeInternal(ITMAMTGSBS);
    }

    /**
     * Sets <code>value</code> as the attribute value for ItmAmtGsBs.
     * @param value value to set the ItmAmtGsBs
     */
    public void setItmAmtGsBs(BigDecimal value) {
        setAttributeInternal(ITMAMTGSBS, value);
    }

    /**
     * Gets the attribute value for WtyId, using the alias name WtyId.
     * @return the value of WtyId
     */
    public String getWtyId() {
        return (String) getAttributeInternal(WTYID);
    }

    /**
     * Sets <code>value</code> as the attribute value for WtyId.
     * @param value value to set the WtyId
     */
    public void setWtyId(String value) {
        setAttributeInternal(WTYID, value);
    }

    /**
     * Gets the attribute value for SchmFlg, using the alias name SchmFlg.
     * @return the value of SchmFlg
     */
    public String getSchmFlg() {
        return (String) getAttributeInternal(SCHMFLG);
    }

    /**
     * Sets <code>value</code> as the attribute value for SchmFlg.
     * @param value value to set the SchmFlg
     */
    public void setSchmFlg(String value) {
        setAttributeInternal(SCHMFLG, value);
    }

    /**
     * Gets the attribute value for AsblAmtBs, using the alias name AsblAmtBs.
     * @return the value of AsblAmtBs
     */
    public BigDecimal getAsblAmtBs() {
        return (BigDecimal) getAttributeInternal(ASBLAMTBS);
    }

    /**
     * Sets <code>value</code> as the attribute value for AsblAmtBs.
     * @param value value to set the AsblAmtBs
     */
    public void setAsblAmtBs(BigDecimal value) {
        setAttributeInternal(ASBLAMTBS, value);
    }

    /**
     * Gets the attribute value for AsblAmtSp, using the alias name AsblAmtSp.
     * @return the value of AsblAmtSp
     */
    public BigDecimal getAsblAmtSp() {
        return (BigDecimal) getAttributeInternal(ASBLAMTSP);
    }

    /**
     * Sets <code>value</code> as the attribute value for AsblAmtSp.
     * @param value value to set the AsblAmtSp
     */
    public void setAsblAmtSp(BigDecimal value) {
        setAttributeInternal(ASBLAMTSP, value);
    }

    /**
     * Gets the attribute value for OldItmShipQty, using the alias name OldItmShipQty.
     * @return the value of OldItmShipQty
     */
    public BigDecimal getOldItmShipQty() {
        return (BigDecimal) getAttributeInternal(OLDITMSHIPQTY);
    }

    /**
     * Sets <code>value</code> as the attribute value for OldItmShipQty.
     * @param value value to set the OldItmShipQty
     */
    public void setOldItmShipQty(BigDecimal value) {
        setAttributeInternal(OLDITMSHIPQTY, value);
    }

    /**
     * Gets the attribute value for CcId, using the alias name CcId.
     * @return the value of CcId
     */
    public String getCcId() {
        return (String) getAttributeInternal(CCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CcId.
     * @param value value to set the CcId
     */
    public void setCcId(String value) {
        setAttributeInternal(CCID, value);
    }

    /**
     * Gets the attribute value for EoPartNo, using the alias name EoPartNo.
     * @return the value of EoPartNo
     */
    public String getEoPartNo() {
        return (String) getAttributeInternal(EOPARTNO);
    }

    /**
     * Sets <code>value</code> as the attribute value for EoPartNo.
     * @param value value to set the EoPartNo
     */
    public void setEoPartNo(String value) {
        setAttributeInternal(EOPARTNO, value);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index, AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            return AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].get(this);
        }
        return super.getAttrInvokeAccessor(index, attrDef);
    }

    /**
     * setAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param value the value to assign to the attribute
     * @param attrDef the attribute

     * @throws Exception
     */
    protected void setAttrInvokeAccessor(int index, Object value, AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }

    /**
     * @return the associated entity SlsInvDtlEOImpl.
     */
    public SlsInvDtlEOImpl getSlsInvDtl() {
        return (SlsInvDtlEOImpl) getAttributeInternal(SLSINVDTL);
    }

    /**
     * Sets <code>value</code> as the associated entity SlsInvDtlEOImpl.
     */
    public void setSlsInvDtl(SlsInvDtlEOImpl value) {
        setAttributeInternal(SLSINVDTL, value);
    }


    /**
     * @param cldId key constituent
     * @param slocId key constituent
     * @param orgId key constituent
     * @param hoOrgId key constituent
     * @param docId key constituent
     * @param shipId key constituent
     * @param soId key constituent
     * @param itmId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String cldId, Integer slocId, String orgId, String hoOrgId, String docId,
                                       String shipId, String soId, String itmId) {
        return new Key(new Object[] { cldId, slocId, orgId, hoOrgId, docId, shipId, soId, itmId });
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        setUsrIdCreateDt(StaticValue.getTruncatedCurrDt());
        setUsrIdCreate(EbizParams.GLBL_APP_USR());
        /*   this.setSlocId(Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}")));
         this.setCldId(resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
         this.setHoOrgId(resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}"));
         this.setOrgId(resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}")); */

        super.create(attributeList);
    }

    /********************    STORED Function    *************************************/

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            st = this.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            st.executeUpdate();
            return st.getObject(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String resolvEl(String data) {
        ADFContext fc = ADFContext.getCurrent();
        //Application app = fc.getApplication();
        ExpressionFactory elFactory = fc.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    /**
     * Add locking logic here.
     */
    public void lock() {
        System.out.println("In Lock");
        try {

            super.lock();
        } catch (Exception e) {
            checkInconsistentAttrs();
            //e.printStackTrace();
        }
    }

    private void checkInconsistentAttrs() {
        int count = getAttributeCount();
        // Store the current values of the attributes; we're going to munge them during the check
        Object[] currValues = new Object[count];
        boolean[] attributeChanges = new boolean[count];
        for (int i = 0; i < count; i++) {
            currValues[i] = getAttribute(i);
            attributeChanges[i] = isAttributeChanged(i);
            System.out.println("Current Value is : " + currValues[i] + " and it is : " + isAttributeChanged(i));
        }
        /* // Change all attribute values to match current DB values (good thing we saved them first!)
        refresh(REFRESH_WITH_DB_FORGET_CHANGES);
        // For each attribute...
        for (int i=0; i<count; i++) {
            // compare the current value (from the DB) with the original value queried from DB.
            Object origValue = getPostedAttribute(i);
            Object currDbValue = getAttribute(i);
            // Watch your nulls! Nobody likes NPEs.
            if ((origValue == null && currDbValue != null) ||
                (origValue != null && (currDbValue == null || !origValue.equals(currDbValue))))
            {
                // If they don't match, tell us about it.
                System.out.println("Inconsistent attribute " + getAttributeNames()[i]);
                System.out.println("  Queried from DB = " + origValue);
                System.out.println("  Now in DB = " + currDbValue);
            }
            // Set this EO instance back to the way it was
             if (attributeChanges[i]) {
                 populateAttributeAsChanged(i, currValues[i]);
             } else {
                 populateAttribute(i, currValues[i]);
             }
        } */
    }

    /**
     * Custom DML update/insert/delete logic here.
     * @param operation the operation type
     * @param e the transaction event
     */
    protected void doDML(int operation, TransactionEvent e) {
        if (operation == DML_UPDATE) {
            setUsrIdModDt(StaticValue.getTruncatedCurrDt());
            setUsrIdMod(EbizParams.GLBL_APP_USR());
        }
        super.doDML(operation, e);
    }
}
