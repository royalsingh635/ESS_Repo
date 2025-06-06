package hcmgroupprfapp.model.views;

import hcmgroupprfapp.model.entities.HcmExtraTimeGrpEOImpl;

import hcmgroupprfapp.model.modules.HcmGrpPrfAMImpl;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Oct 31 12:40:29 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class HcmExtraTimeGrpVORowImpl extends ViewRowImpl {


    public static final int ENTITY_HCMEXTRATIMEGRPEO = 0;

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
        transMode,
        DocId,
        TransExtraTimeRate,
        HcmExtraTimeSal,
        LovExtraTimeType,
        LovExtraTimeRate;
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
    public static final int TRANSMODE = AttributesEnum.transMode.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int TRANSEXTRATIMERATE = AttributesEnum.TransExtraTimeRate.index();
    public static final int HCMEXTRATIMESAL = AttributesEnum.HcmExtraTimeSal.index();
    public static final int LOVEXTRATIMETYPE = AttributesEnum.LovExtraTimeType.index();
    public static final int LOVEXTRATIMERATE = AttributesEnum.LovExtraTimeRate.index();

    /**
     * This is the default constructor (do not remove).
     */
    public HcmExtraTimeGrpVORowImpl() {
    }

    /**
     * Gets HcmExtraTimeGrpEO entity object.
     * @return the HcmExtraTimeGrpEO
     */
    public HcmExtraTimeGrpEOImpl getHcmExtraTimeGrpEO() {
        return (HcmExtraTimeGrpEOImpl) getEntity(ENTITY_HCMEXTRATIMEGRPEO);
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
     * Gets the attribute value for ORG_ID using the alias name OrgId.
     * @return the ORG_ID
     */
    public String getOrgId() {
        return (String) getAttributeInternal(ORGID);
    }

    /**
     * Sets <code>value</code> as attribute value for ORG_ID using the alias name OrgId.
     * @param value value to set the ORG_ID
     */
    public void setOrgId(String value) {
        setAttributeInternal(ORGID, value);
    }

    /**
     * Gets the attribute value for RULE_TYPE using the alias name RuleType.
     * @return the RULE_TYPE
     */
    public String getRuleType() {
        return (String) getAttributeInternal(RULETYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for RULE_TYPE using the alias name RuleType.
     * @param value value to set the RULE_TYPE
     */
    public void setRuleType(String value) {
        setAttributeInternal(RULETYPE, value);
    }

    /**
     * Gets the attribute value for GRP_ID using the alias name GrpId.
     * @return the GRP_ID
     */
    public String getGrpId() {
        return (String) getAttributeInternal(GRPID);
    }

    /**
     * Sets <code>value</code> as attribute value for GRP_ID using the alias name GrpId.
     * @param value value to set the GRP_ID
     */
    public void setGrpId(String value) {
        setAttributeInternal(GRPID, value);
    }


    /**
     * Gets the attribute value for RULE_RATE using the alias name RuleRate.
     * @return the RULE_RATE
     */
    public Number getRuleRate() {
        return (Number) getAttributeInternal(RULERATE);
    }

    /**
     * Sets <code>value</code> as attribute value for RULE_RATE using the alias name RuleRate.
     * @param value value to set the RULE_RATE
     */
    public void setRuleRate(Number value) {
        setAttributeInternal(RULERATE, value);
    }

    /**
     * Gets the attribute value for RULE_RATE_UNIT using the alias name RuleRateUnit.
     * @return the RULE_RATE_UNIT
     */
    public Integer getRuleRateUnit() {
        return (Integer) getAttributeInternal(RULERATEUNIT);
    }

    /**
     * Sets <code>value</code> as attribute value for RULE_RATE_UNIT using the alias name RuleRateUnit.
     * @param value value to set the RULE_RATE_UNIT
     */
    public void setRuleRateUnit(Integer value) {
        setAttributeInternal(RULERATEUNIT, value);
    }

    /**
     * Gets the attribute value for VALID_STRT_DT using the alias name ValidStrtDt.
     * @return the VALID_STRT_DT
     */
    public Timestamp getValidStrtDt() {
        return (Timestamp) getAttributeInternal(VALIDSTRTDT);
    }

    /**
     * Sets <code>value</code> as attribute value for VALID_STRT_DT using the alias name ValidStrtDt.
     * @param value value to set the VALID_STRT_DT
     */
    public void setValidStrtDt(Timestamp value) {
        setAttributeInternal(VALIDSTRTDT, value);
    }

    /**
     * Gets the attribute value for VALID_END_DT using the alias name ValidEndDt.
     * @return the VALID_END_DT
     */
    public Timestamp getValidEndDt() {
        return (Timestamp) getAttributeInternal(VALIDENDDT);
    }

    /**
     * Sets <code>value</code> as attribute value for VALID_END_DT using the alias name ValidEndDt.
     * @param value value to set the VALID_END_DT
     */
    public void setValidEndDt(Timestamp value) {
        setAttributeInternal(VALIDENDDT, value);
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
     * Gets the attribute value for the calculated attribute transMode.
     * @return the transMode
     */
    public String gettransMode() {
        if(resolvEl("#{pageFlowScope.ADD_EDIT_MODE}") != null)
               return (String) resolvEl("#{pageFlowScope.ADD_EDIT_MODE}");
        return (String) getAttributeInternal(TRANSMODE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute transMode.
     * @param value value to set the  transMode
     */
    public void settransMode(String value) {
        setAttributeInternal(TRANSMODE, value);
    }

    /**
     * Gets the attribute value for DOC_ID using the alias name DocId.
     * @return the DOC_ID
     */
    public String getDocId()
    {
        return (String) getAttributeInternal(DOCID);
    }

    /**
     * Sets <code>value</code> as attribute value for DOC_ID using the alias name DocId.
     * @param value value to set the DOC_ID
     */
    public void setDocId(String value)
    {
        setAttributeInternal(DOCID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransExtraTimeRate.
     * @return the TransExtraTimeRate
     */
    public String getTransExtraTimeRate() {
        System.out.println("In Getter");
            HcmGrpPrfAMImpl am = (HcmGrpPrfAMImpl)this.getApplicationModule();
            ViewObjectImpl hcmPrf = am.getOrgHcmPrf();
            hcmPrf.setNamedWhereClauseParam("BINDCLD_ID", this.getCldId());
            hcmPrf.setNamedWhereClauseParam("BINDORG_ID", this.getOrgId());
            hcmPrf.setNamedWhereClauseParam("BINDSLOC_ID", this.getSlocId());
            hcmPrf.executeQuery();
            Row[] rs = hcmPrf.getAllRowsInRange();
            System.out.println("Getter Range : "+rs.length);
            if(rs.length>0){
                System.out.println("Return value : "+rs[0].getAttribute("ExtRuleRateType"));
                return (String)rs[0].getAttribute("ExtRuleRateType");
            }
        return (String) getAttributeInternal(TRANSEXTRATIMERATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransExtraTimeRate.
     * @param value value to set the  TransExtraTimeRate
     */
    public void setTransExtraTimeRate(String value) {
        setAttributeInternal(TRANSEXTRATIMERATE, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link HcmExtraTimeSal.
     */
    public RowIterator getHcmExtraTimeSal()
    {
        return (RowIterator) getAttributeInternal(HCMEXTRATIMESAL);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovExtraTimeType.
     */
    public RowSet getLovExtraTimeType() {
        return (RowSet) getAttributeInternal(LOVEXTRATIMETYPE);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovExtraTimeRate.
     */
    public RowSet getLovExtraTimeRate() {
        return (RowSet) getAttributeInternal(LOVEXTRATIMERATE);
    }


    public String resolvEl(String data) {
                 FacesContext fc = FacesContext.getCurrentInstance();
                 Application app = fc.getApplication();
                 ExpressionFactory elFactory = app.getExpressionFactory();
                 ELContext elContext = fc.getELContext();
                 ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
                 String msg = (String)valueExp.getValue(elContext);
                 return msg;
             }     
}

