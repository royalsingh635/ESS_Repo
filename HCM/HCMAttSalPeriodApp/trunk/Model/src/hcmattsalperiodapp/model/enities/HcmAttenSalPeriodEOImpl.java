package hcmattsalperiodapp.model.enities;

import adf.utils.bean.StaticValue;
import adf.utils.ebiz.EbizParams;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Sep 22 17:18:10 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class HcmAttenSalPeriodEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        SlocId,
        HoOrgId,
        OrgId,
        AttenFrmDt,
        AttenToDt,
        SalFrmDt,
        SalToDt,
        UsrIdCreate,
        UsrIdCreateDt,
        UsrIdMod,
        UsrIdModDt,
        GrpId;
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
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int ATTENFRMDT = AttributesEnum.AttenFrmDt.index();
    public static final int ATTENTODT = AttributesEnum.AttenToDt.index();
    public static final int SALFRMDT = AttributesEnum.SalFrmDt.index();
    public static final int SALTODT = AttributesEnum.SalToDt.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int GRPID = AttributesEnum.GrpId.index();

    /**
     * This is the default constructor (do not remove).
     */
    public HcmAttenSalPeriodEOImpl() {
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("hcmattsalperiodapp.model.enities.HcmAttenSalPeriodEO");
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
     * Gets the attribute value for AttenFrmDt, using the alias name AttenFrmDt.
     * @return the value of AttenFrmDt
     */
    public oracle.jbo.domain.Timestamp getAttenFrmDt() {
        return (oracle.jbo.domain.Timestamp) getAttributeInternal(ATTENFRMDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for AttenFrmDt.
     * @param value value to set the AttenFrmDt
     */
    public void setAttenFrmDt(oracle.jbo.domain.Timestamp value) {
        setAttributeInternal(ATTENFRMDT, value);
    }

    /**
     * Gets the attribute value for AttenToDt, using the alias name AttenToDt.
     * @return the value of AttenToDt
     */
    public oracle.jbo.domain.Timestamp getAttenToDt() {
        return (oracle.jbo.domain.Timestamp) getAttributeInternal(ATTENTODT);
    }

    /**
     * Sets <code>value</code> as the attribute value for AttenToDt.
     * @param value value to set the AttenToDt
     */
    public void setAttenToDt(oracle.jbo.domain.Timestamp value) {
        setAttributeInternal(ATTENTODT, value);
    }

    /**
     * Gets the attribute value for SalFrmDt, using the alias name SalFrmDt.
     * @return the value of SalFrmDt
     */
    public oracle.jbo.domain.Timestamp getSalFrmDt() {
        return (oracle.jbo.domain.Timestamp) getAttributeInternal(SALFRMDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for SalFrmDt.
     * @param value value to set the SalFrmDt
     */
    public void setSalFrmDt(oracle.jbo.domain.Timestamp value) {
        setAttributeInternal(SALFRMDT, value);
    }

    /**
     * Gets the attribute value for SalToDt, using the alias name SalToDt.
     * @return the value of SalToDt
     */
    public oracle.jbo.domain.Timestamp getSalToDt() {
        return (oracle.jbo.domain.Timestamp) getAttributeInternal(SALTODT);
    }

    /**
     * Sets <code>value</code> as the attribute value for SalToDt.
     * @param value value to set the SalToDt
     */
    public void setSalToDt(oracle.jbo.domain.Timestamp value) {
        setAttributeInternal(SALTODT, value);
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
    public oracle.jbo.domain.Timestamp getUsrIdCreateDt() {
        return (oracle.jbo.domain.Timestamp) getAttributeInternal(USRIDCREATEDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdCreateDt.
     * @param value value to set the UsrIdCreateDt
     */
    public void setUsrIdCreateDt(oracle.jbo.domain.Timestamp value) {
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
    public oracle.jbo.domain.Timestamp getUsrIdModDt() {
        return (oracle.jbo.domain.Timestamp) getAttributeInternal(USRIDMODDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdModDt.
     * @param value value to set the UsrIdModDt
     */
    public void setUsrIdModDt(oracle.jbo.domain.Timestamp value) {
        setAttributeInternal(USRIDMODDT, value);
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
     * @param cldId key constituent
     * @param slocId key constituent
     * @param hoOrgId key constituent
     * @param orgId key constituent
     * @param attenFrmDt key constituent
     * @param attenToDt key constituent
     * @param salFrmDt key constituent
     * @param salToDt key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String cldId, Integer slocId, String hoOrgId, String orgId, Timestamp attenFrmDt,
                                       Timestamp attenToDt, Timestamp salFrmDt, Timestamp salToDt) {
        return new Key(new Object[] { cldId, slocId, hoOrgId, orgId, attenFrmDt, attenToDt, salFrmDt, salToDt });
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
       
        setCldId(resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}"));
        setSlocId(Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString()));
        setHoOrgId( resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}"));
        setOrgId( resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}"));
       
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

        if (operation == DML_UPDATE) {
            setUsrIdMod(Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString()));
            setUsrIdModDt(new oracle.jbo.domain.Timestamp(System.currentTimeMillis()));
        }


        super.doDML(operation, e);
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String msg = valueExp.getValue(elContext).toString();
        return msg;
    }
}

