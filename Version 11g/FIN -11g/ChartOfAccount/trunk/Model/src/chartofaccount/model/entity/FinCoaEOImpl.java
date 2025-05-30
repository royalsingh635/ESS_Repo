package chartofaccount.model.entity;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Jan 22 14:28:00 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class FinCoaEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CoaId {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCoaId();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCoaId((DBSequence)value);
            }
        }
        ,
        CoaSlocId {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCoaSlocId();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCoaSlocId((Integer)value);
            }
        }
        ,
        CoaNm {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCoaNm();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCoaNm((String)value);
            }
        }
        ,
        CoaAlias {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCoaAlias();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCoaAlias((String)value);
            }
        }
        ,
        CoaPrfId {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCoaPrfId();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCoaPrfId((Integer)value);
            }
        }
        ,
        CoaCogId {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCoaCogId();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCoaCogId((String)value);
            }
        }
        ,
        CoaAccId {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCoaAccId();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCoaAccId((Integer)value);
            }
        }
        ,
        CogAltCogId {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCogAltCogId();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCogAltCogId((String)value);
            }
        }
        ,
        CoaGrpType {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCoaGrpType();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCoaGrpType((String)value);
            }
        }
        ,
        CoaGrpBalType {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCoaGrpBalType();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCoaGrpBalType((String)value);
            }
        }
        ,
        CoaBsItem {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCoaBsItem();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCoaBsItem((String)value);
            }
        }
        ,
        CoaPnlItem {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCoaPnlItem();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCoaPnlItem((String)value);
            }
        }
        ,
        CoaCfItem {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCoaCfItem();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCoaCfItem((String)value);
            }
        }
        ,
        CoaTrfBal {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCoaTrfBal();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCoaTrfBal((String)value);
            }
        }
        ,
        PartOfBdgt {
            public Object get(FinCoaEOImpl obj) {
                return obj.getPartOfBdgt();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setPartOfBdgt((String)value);
            }
        }
        ,
        BdgtCalcLogic {
            public Object get(FinCoaEOImpl obj) {
                return obj.getBdgtCalcLogic();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setBdgtCalcLogic((String)value);
            }
        }
        ,
        CoaResv {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCoaResv();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCoaResv((String)value);
            }
        }
        ,
        CoaActv {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCoaActv();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCoaActv((String)value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(FinCoaEOImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setUsrIdCreate((Integer)value);
            }
        }
        ,
        UsrIdCreateDt {
            public Object get(FinCoaEOImpl obj) {
                return obj.getUsrIdCreateDt();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UsrIdMod {
            public Object get(FinCoaEOImpl obj) {
                return obj.getUsrIdMod();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setUsrIdMod((Integer)value);
            }
        }
        ,
        UsrIdModDt {
            public Object get(FinCoaEOImpl obj) {
                return obj.getUsrIdModDt();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setUsrIdModDt((Date)value);
            }
        }
        ,
        CoaLegCd {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCoaLegCd();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCoaLegCd((String)value);
            }
        }
        ,
        CoaFlucAcc {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCoaFlucAcc();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCoaFlucAcc((String)value);
            }
        }
        ,
        CoaCldId {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCoaCldId();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCoaCldId((String)value);
            }
        }
        ,
        CoaHoOrgId {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCoaHoOrgId();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCoaHoOrgId((String)value);
            }
        }
        ,
        CoaRr {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCoaRr();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCoaRr((String)value);
            }
        }
        ,
        CoaRoundAcc {
            public Object get(FinCoaEOImpl obj) {
                return obj.getCoaRoundAcc();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setCoaRoundAcc((String)value);
            }
        }
        ,
        OrgCoa {
            public Object get(FinCoaEOImpl obj) {
                return obj.getOrgCoa();
            }

            public void put(FinCoaEOImpl obj, Object value) {
                obj.setOrgCoa((OrgCoaEOImpl)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(FinCoaEOImpl object);

        public abstract void put(FinCoaEOImpl object, Object value);

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


    public static final int COAID = AttributesEnum.CoaId.index();
    public static final int COASLOCID = AttributesEnum.CoaSlocId.index();
    public static final int COANM = AttributesEnum.CoaNm.index();
    public static final int COAALIAS = AttributesEnum.CoaAlias.index();
    public static final int COAPRFID = AttributesEnum.CoaPrfId.index();
    public static final int COACOGID = AttributesEnum.CoaCogId.index();
    public static final int COAACCID = AttributesEnum.CoaAccId.index();
    public static final int COGALTCOGID = AttributesEnum.CogAltCogId.index();
    public static final int COAGRPTYPE = AttributesEnum.CoaGrpType.index();
    public static final int COAGRPBALTYPE = AttributesEnum.CoaGrpBalType.index();
    public static final int COABSITEM = AttributesEnum.CoaBsItem.index();
    public static final int COAPNLITEM = AttributesEnum.CoaPnlItem.index();
    public static final int COACFITEM = AttributesEnum.CoaCfItem.index();
    public static final int COATRFBAL = AttributesEnum.CoaTrfBal.index();
    public static final int PARTOFBDGT = AttributesEnum.PartOfBdgt.index();
    public static final int BDGTCALCLOGIC = AttributesEnum.BdgtCalcLogic.index();
    public static final int COARESV = AttributesEnum.CoaResv.index();
    public static final int COAACTV = AttributesEnum.CoaActv.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int COALEGCD = AttributesEnum.CoaLegCd.index();
    public static final int COAFLUCACC = AttributesEnum.CoaFlucAcc.index();
    public static final int COACLDID = AttributesEnum.CoaCldId.index();
    public static final int COAHOORGID = AttributesEnum.CoaHoOrgId.index();
    public static final int COARR = AttributesEnum.CoaRr.index();
    public static final int COAROUNDACC = AttributesEnum.CoaRoundAcc.index();
    public static final int ORGCOA = AttributesEnum.OrgCoa.index();

    /**
     * This is the default constructor (do not remove).
     */
    public FinCoaEOImpl() {
    }


    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("chartofaccount.model.entity.FinCoaEO");
    }

    /**
     * Gets the attribute value for CoaId, using the alias name CoaId.
     * @return the value of CoaId
     */
    public DBSequence getCoaId() {
        return (DBSequence)getAttributeInternal(COAID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaId.
     * @param value value to set the CoaId
     */
    public void setCoaId(DBSequence value) {
        setAttributeInternal(COAID, value);
    }

    /**
     * Gets the attribute value for CoaSlocId, using the alias name CoaSlocId.
     * @return the value of CoaSlocId
     */
    public Integer getCoaSlocId() {
        return (Integer)getAttributeInternal(COASLOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaSlocId.
     * @param value value to set the CoaSlocId
     */
    public void setCoaSlocId(Integer value) {
        setAttributeInternal(COASLOCID, value);
    }

    /**
     * Gets the attribute value for CoaNm, using the alias name CoaNm.
     * @return the value of CoaNm
     */
    public String getCoaNm() {
        return (String)getAttributeInternal(COANM);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaNm.
     * @param value value to set the CoaNm
     */
    public void setCoaNm(String value) {
        setAttributeInternal(COANM, value);
    }

    /**
     * Gets the attribute value for CoaAlias, using the alias name CoaAlias.
     * @return the value of CoaAlias
     */
    public String getCoaAlias() {
        return (String)getAttributeInternal(COAALIAS);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaAlias.
     * @param value value to set the CoaAlias
     */
    public void setCoaAlias(String value) {
        setAttributeInternal(COAALIAS, value);
    }

    /**
     * Gets the attribute value for CoaPrfId, using the alias name CoaPrfId.
     * @return the value of CoaPrfId
     */
    public Integer getCoaPrfId() {
        return (Integer)getAttributeInternal(COAPRFID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaPrfId.
     * @param value value to set the CoaPrfId
     */
    public void setCoaPrfId(Integer value) {
        setAttributeInternal(COAPRFID, value);
    }

    /**
     * Gets the attribute value for CoaCogId, using the alias name CoaCogId.
     * @return the value of CoaCogId
     */
    public String getCoaCogId() {
        return (String)getAttributeInternal(COACOGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaCogId.
     * @param value value to set the CoaCogId
     */
    public void setCoaCogId(String value) {
        if(value!=null){
        setAttributeInternal(COACOGID, value);
        }
    }

    /**
     * Gets the attribute value for CoaAccId, using the alias name CoaAccId.
     * @return the value of CoaAccId
     */
    public Integer getCoaAccId() {
        return (Integer)getAttributeInternal(COAACCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaAccId.
     * @param value value to set the CoaAccId
     */
    public void setCoaAccId(Integer value) {
        setAttributeInternal(COAACCID, value);
    }

    /**
     * Gets the attribute value for CogAltCogId, using the alias name CogAltCogId.
     * @return the value of CogAltCogId
     */
    public String getCogAltCogId() {
        return (String)getAttributeInternal(COGALTCOGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CogAltCogId.
     * @param value value to set the CogAltCogId
     */
    public void setCogAltCogId(String value) {
        setAttributeInternal(COGALTCOGID, value);
    }

    /**
     * Gets the attribute value for CoaGrpType, using the alias name CoaGrpType.
     * @return the value of CoaGrpType
     */
    public String getCoaGrpType() {
        return (String)getAttributeInternal(COAGRPTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaGrpType.
     * @param value value to set the CoaGrpType
     */
    public void setCoaGrpType(String value) {
        setAttributeInternal(COAGRPTYPE, value);
    }

    /**
     * Gets the attribute value for CoaGrpBalType, using the alias name CoaGrpBalType.
     * @return the value of CoaGrpBalType
     */
    public String getCoaGrpBalType() {
        return (String)getAttributeInternal(COAGRPBALTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaGrpBalType.
     * @param value value to set the CoaGrpBalType
     */
    public void setCoaGrpBalType(String value) {
        setAttributeInternal(COAGRPBALTYPE, value);
    }

    /**
     * Gets the attribute value for CoaBsItem, using the alias name CoaBsItem.
     * @return the value of CoaBsItem
     */
    public String getCoaBsItem() {
        return (String)getAttributeInternal(COABSITEM);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaBsItem.
     * @param value value to set the CoaBsItem
     */
    public void setCoaBsItem(String value) {
        setAttributeInternal(COABSITEM, value);
    }

    /**
     * Gets the attribute value for CoaPnlItem, using the alias name CoaPnlItem.
     * @return the value of CoaPnlItem
     */
    public String getCoaPnlItem() {
        return (String)getAttributeInternal(COAPNLITEM);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaPnlItem.
     * @param value value to set the CoaPnlItem
     */
    public void setCoaPnlItem(String value) {
        setAttributeInternal(COAPNLITEM, value);
    }

    /**
     * Gets the attribute value for CoaCfItem, using the alias name CoaCfItem.
     * @return the value of CoaCfItem
     */
    public String getCoaCfItem() {
        return (String)getAttributeInternal(COACFITEM);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaCfItem.
     * @param value value to set the CoaCfItem
     */
    public void setCoaCfItem(String value) {
        setAttributeInternal(COACFITEM, value);
    }

    /**
     * Gets the attribute value for CoaTrfBal, using the alias name CoaTrfBal.
     * @return the value of CoaTrfBal
     */
    public String getCoaTrfBal() {
        return (String)getAttributeInternal(COATRFBAL);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaTrfBal.
     * @param value value to set the CoaTrfBal
     */
    public void setCoaTrfBal(String value) {
        setAttributeInternal(COATRFBAL, value);
    }

    /**
     * Gets the attribute value for PartOfBdgt, using the alias name PartOfBdgt.
     * @return the value of PartOfBdgt
     */
    public String getPartOfBdgt() {
        return (String)getAttributeInternal(PARTOFBDGT);
    }

    /**
     * Sets <code>value</code> as the attribute value for PartOfBdgt.
     * @param value value to set the PartOfBdgt
     */
    public void setPartOfBdgt(String value) {
        setAttributeInternal(PARTOFBDGT, value);
    }

    /**
     * Gets the attribute value for BdgtCalcLogic, using the alias name BdgtCalcLogic.
     * @return the value of BdgtCalcLogic
     */
    public String getBdgtCalcLogic() {
        return (String)getAttributeInternal(BDGTCALCLOGIC);
    }

    /**
     * Sets <code>value</code> as the attribute value for BdgtCalcLogic.
     * @param value value to set the BdgtCalcLogic
     */
    public void setBdgtCalcLogic(String value) {
        setAttributeInternal(BDGTCALCLOGIC, value);
    }

    /**
     * Gets the attribute value for CoaResv, using the alias name CoaResv.
     * @return the value of CoaResv
     */
    public String getCoaResv() {
        return (String)getAttributeInternal(COARESV);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaResv.
     * @param value value to set the CoaResv
     */
    public void setCoaResv(String value) {
        setAttributeInternal(COARESV, value);
    }

    /**
     * Gets the attribute value for CoaActv, using the alias name CoaActv.
     * @return the value of CoaActv
     */
    public String getCoaActv() {
        return (String)getAttributeInternal(COAACTV);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaActv.
     * @param value value to set the CoaActv
     */
    public void setCoaActv(String value) {
        setAttributeInternal(COAACTV, value);
    }

    /**
     * Gets the attribute value for UsrIdCreate, using the alias name UsrIdCreate.
     * @return the value of UsrIdCreate
     */
    public Integer getUsrIdCreate() {
        return (Integer)getAttributeInternal(USRIDCREATE);
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
        return (Timestamp)getAttributeInternal(USRIDCREATEDT);
    }

    /**
     * Gets the attribute value for UsrIdMod, using the alias name UsrIdMod.
     * @return the value of UsrIdMod
     */
    public Integer getUsrIdMod() {
        return (Integer)getAttributeInternal(USRIDMOD);
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
    public Date getUsrIdModDt() {
        return (Date)getAttributeInternal(USRIDMODDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdModDt.
     * @param value value to set the UsrIdModDt
     */
    public void setUsrIdModDt(Date value) {
        setAttributeInternal(USRIDMODDT, value);
    }

    /**
     * Gets the attribute value for CoaLegCd, using the alias name CoaLegCd.
     * @return the value of CoaLegCd
     */
    public String getCoaLegCd() {
        return (String)getAttributeInternal(COALEGCD);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaLegCd.
     * @param value value to set the CoaLegCd
     */
    public void setCoaLegCd(String value) {
        setAttributeInternal(COALEGCD, value);
    }

    /**
     * Gets the attribute value for CoaFlucAcc, using the alias name CoaFlucAcc.
     * @return the value of CoaFlucAcc
     */
    public String getCoaFlucAcc() {
        return (String)getAttributeInternal(COAFLUCACC);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaFlucAcc.
     * @param value value to set the CoaFlucAcc
     */
    public void setCoaFlucAcc(String value) {
        setAttributeInternal(COAFLUCACC, value);
    }

    /**
     * Gets the attribute value for CoaCldId, using the alias name CoaCldId.
     * @return the value of CoaCldId
     */
    public String getCoaCldId() {
        return (String)getAttributeInternal(COACLDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaCldId.
     * @param value value to set the CoaCldId
     */
    public void setCoaCldId(String value) {
        setAttributeInternal(COACLDID, value);
    }

    /**
     * Gets the attribute value for CoaHoOrgId, using the alias name CoaHoOrgId.
     * @return the value of CoaHoOrgId
     */
    public String getCoaHoOrgId() {
        return (String)getAttributeInternal(COAHOORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaHoOrgId.
     * @param value value to set the CoaHoOrgId
     */
    public void setCoaHoOrgId(String value) {
        setAttributeInternal(COAHOORGID, value);
    }

    /**
     * Gets the attribute value for CoaRr, using the alias name CoaRr.
     * @return the value of CoaRr
     */
    public String getCoaRr() {
        return (String)getAttributeInternal(COARR);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaRr.
     * @param value value to set the CoaRr
     */
    public void setCoaRr(String value) {
        setAttributeInternal(COARR, value);
    }

    /**
     * Gets the attribute value for CoaRoundAcc, using the alias name CoaRoundAcc.
     * @return the value of CoaRoundAcc
     */
    public String getCoaRoundAcc() {
        return (String)getAttributeInternal(COAROUNDACC);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaRoundAcc.
     * @param value value to set the CoaRoundAcc
     */
    public void setCoaRoundAcc(String value) {
        setAttributeInternal(COAROUNDACC, value);
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
     * @return the associated entity OrgCoaEOImpl.
     */
    public OrgCoaEOImpl getOrgCoa() {
        return (OrgCoaEOImpl)getAttributeInternal(ORGCOA);
    }

    /**
     * Sets <code>value</code> as the associated entity OrgCoaEOImpl.
     */
    public void setOrgCoa(OrgCoaEOImpl value) {
        setAttributeInternal(ORGCOA, value);
    }


    /**
     * @param coaId key constituent
     * @param coaSlocId key constituent
     * @param coaCldId key constituent
     * @param coaHoOrgId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(DBSequence coaId, Integer coaSlocId, String coaCldId, String coaHoOrgId) {
        return new Key(new Object[]{coaId, coaSlocId, coaCldId, coaHoOrgId});
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
     public String resolvEl(String data){
         FacesContext fc = FacesContext.getCurrentInstance();
         Application app = fc.getApplication();
         ExpressionFactory elFactory = app.getExpressionFactory();
         ELContext elContext = fc.getELContext();
         ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
         String Message=valueExp.getValue(elContext).toString();
         return Message;
       }
    protected void create(AttributeList attributeList) {
        Integer  SlocId =Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));  
                Integer  UserId =Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
                String org_id=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
                String cld_id=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
                System.out.println("SlocId = "+SlocId+"     UserId = "+UserId+"     org_id = "+org_id+"     cld_id = "+cld_id);
                setCoaHoOrgId(org_id);
               setCoaSlocId(SlocId);
               setUsrIdCreate(UserId);
               setCoaCldId(cld_id);
        super.create(attributeList);
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
        if(operation == DML_UPDATE)
                {
                        Integer  UserId =Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}")); 
                        Date currDate = (Date)Date.getCurrentDate();
                        setUsrIdModDt(currDate);
                        setUsrIdMod(UserId);
                    }
        super.doDML(operation, e);
    }
}
