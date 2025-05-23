package hcmgroupprfapp.model.entities;

import java.math.BigDecimal;

import oracle.jbo.domain.NClobDomain;
import oracle.jbo.domain.Timestamp;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.context.FacesContext;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.domain.Number;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Aug 25 11:40:30 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class HcmExtraTimeGrpEOImpl extends EntityImpl
{
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        SlocId,
        HoOrgId,
        OrgId,
        RuleType,
        GrpId,
        RuleRate,
        RuleRateUnit,
        ValidStrtDt,
        ValidEndDt,
        UsrIdCreate,
        UsrIdCreateDt,
        UsrIdMod,
        UsrIdModDt,
        DocId,
        OrgHcmGrpPrf1;
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


    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int RULETYPE = AttributesEnum.RuleType.index();
    public static final int GRPID = AttributesEnum.GrpId.index();
    public static final int RULERATE = AttributesEnum.RuleRate.index();
    public static final int RULERATEUNIT = AttributesEnum.RuleRateUnit.index();
    public static final int VALIDSTRTDT = AttributesEnum.ValidStrtDt.index();
    public static final int VALIDENDDT = AttributesEnum.ValidEndDt.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int ORGHCMGRPPRF1 = AttributesEnum.OrgHcmGrpPrf1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public HcmExtraTimeGrpEOImpl() {
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject()
    {
        return EntityDefImpl.findDefObject("hcmgroupprfapp.model.entities.HcmExtraTimeGrpEO");
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
     * Gets the attribute value for RuleType, using the alias name RuleType.
     * @return the value of RuleType
     */
    public String getRuleType() {
        return (String) getAttributeInternal(RULETYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for RuleType.
     * @param value value to set the RuleType
     */
    public void setRuleType(String value) {
        setAttributeInternal(RULETYPE, value);
    }

    /**
     * Gets the attribute value for GrpId, using the alias name GrpId.
     * @return the value of GrpId
     */
    public String getGrpId() {
        return (String) getAttributeInternal(GRPID);
    }

    /**
     * Sets <code>value</code> as the attribute value for GrpId.
     * @param value value to set the GrpId
     */
    public void setGrpId(String value) {
        setAttributeInternal(GRPID, value);
    }


    /**
     * Gets the attribute value for RuleRate, using the alias name RuleRate.
     * @return the value of RuleRate
     */
    public Number getRuleRate() {
        return (Number) getAttributeInternal(RULERATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for RuleRate.
     * @param value value to set the RuleRate
     */
    public void setRuleRate(Number value) {
        if(value == null)
            value = new Number(0);
        setAttributeInternal(RULERATE, value);
    }

    /**
     * Gets the attribute value for RuleRateUnit, using the alias name RuleRateUnit.
     * @return the value of RuleRateUnit
     */
    public Integer getRuleRateUnit() {
        return (Integer) getAttributeInternal(RULERATEUNIT);
    }

    /**
     * Sets <code>value</code> as the attribute value for RuleRateUnit.
     * @param value value to set the RuleRateUnit
     */
    public void setRuleRateUnit(Integer value) {
        setAttributeInternal(RULERATEUNIT, value);
    }

    /**
     * Gets the attribute value for ValidStrtDt, using the alias name ValidStrtDt.
     * @return the value of ValidStrtDt
     */
    public Timestamp getValidStrtDt() {
        return (Timestamp) getAttributeInternal(VALIDSTRTDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for ValidStrtDt.
     * @param value value to set the ValidStrtDt
     */
    public void setValidStrtDt(Timestamp value) {
        setAttributeInternal(VALIDSTRTDT, value);
    }

    /**
     * Gets the attribute value for ValidEndDt, using the alias name ValidEndDt.
     * @return the value of ValidEndDt
     */
    public Timestamp getValidEndDt() {
        return (Timestamp) getAttributeInternal(VALIDENDDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for ValidEndDt.
     * @param value value to set the ValidEndDt
     */
    public void setValidEndDt(Timestamp value) {
        setAttributeInternal(VALIDENDDT, value);
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
     * Gets the attribute value for DocId, using the alias name DocId.
     * @return the value of DocId
     */
    public String getDocId()
    {
        return (String) getAttributeInternal(DOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for DocId.
     * @param value value to set the DocId
     */
    public void setDocId(String value)
    {
        setAttributeInternal(DOCID, value);
    }

    /**
     * @return the associated entity OrgHcmGrpPrfEOImpl.
     */
    public OrgHcmGrpPrfEOImpl getOrgHcmGrpPrf1() {
        return (OrgHcmGrpPrfEOImpl) getAttributeInternal(ORGHCMGRPPRF1);
    }

    /**
     * Sets <code>value</code> as the associated entity OrgHcmGrpPrfEOImpl.
     */
    public void setOrgHcmGrpPrf1(OrgHcmGrpPrfEOImpl value) {
        setAttributeInternal(ORGHCMGRPPRF1, value);
    }


    /**
     * @param cldId key constituent
     * @param slocId key constituent
     * @param hoOrgId key constituent
     * @param orgId key constituent
     * @param ruleType key constituent
     * @param grpId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String cldId, Integer slocId, String hoOrgId, String orgId, String ruleType,
                                       String grpId)
    {
        return new Key(new Object[]
        {
            cldId, slocId, hoOrgId, orgId, ruleType, grpId
        });
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        setValidStrtDt(new oracle.jbo.domain.Timestamp(System.currentTimeMillis()));
        setUsrIdCreate(Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString()));
        setUsrIdCreateDt(new oracle.jbo.domain.Timestamp(System.currentTimeMillis()));
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
                setUsrIdMod(Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString()));
                setUsrIdModDt(new oracle.jbo.domain.Timestamp(System.currentTimeMillis()));
            } 
        super.doDML(operation, e);
    }
    
    public Object resolvEl(String data) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ELContext elContext = facesContext.getELContext();
    ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
    ValueExpression exp = expressionFactory.createValueExpression(elContext, data, Object.class);
    return exp.getValue(elContext);
    }
    
}

