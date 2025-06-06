package hrjobadapp.model.entities;

import adf.utils.ebiz.EbizParams;
import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Aug 11 13:59:18 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class HcmJobAdvtSrcEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        SlocId,
        HoOrgId,
        OrgId,
        DocDt,
        JobId,
        SourceId,
        SourceEo,
        AdvertiseFreq,
        UsrIdCreate,
        UsrIdCreateDt,
        UsrIdMod,
        UsrIdModDt,
        DocId,
        JobCost,
        PageNumber,
        DeptId,
        DesigId,
        LocId;
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
    public static final int DOCDT = AttributesEnum.DocDt.index();
    public static final int JOBID = AttributesEnum.JobId.index();
    public static final int SOURCEID = AttributesEnum.SourceId.index();
    public static final int SOURCEEO = AttributesEnum.SourceEo.index();
    public static final int ADVERTISEFREQ = AttributesEnum.AdvertiseFreq.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int JOBCOST = AttributesEnum.JobCost.index();
    public static final int PAGENUMBER = AttributesEnum.PageNumber.index();
    public static final int DEPTID = AttributesEnum.DeptId.index();
    public static final int DESIGID = AttributesEnum.DesigId.index();
    public static final int LOCID = AttributesEnum.LocId.index();

    /**
     * This is the default constructor (do not remove).
     */
    public HcmJobAdvtSrcEOImpl() {
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("hrjobadapp.model.entities.HcmJobAdvtSrcEO");
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
     * Gets the attribute value for DocDt, using the alias name DocDt.
     * @return the value of DocDt
     */
    public Timestamp getDocDt() {
        return (Timestamp) getAttributeInternal(DOCDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for DocDt.
     * @param value value to set the DocDt
     */
    public void setDocDt(Timestamp value) {
        setAttributeInternal(DOCDT, value);
    }

    /**
     * Gets the attribute value for JobId, using the alias name JobId.
     * @return the value of JobId
     */
    public String getJobId() {
        return (String) getAttributeInternal(JOBID);
    }

    /**
     * Sets <code>value</code> as the attribute value for JobId.
     * @param value value to set the JobId
     */
    public void setJobId(String value) {
        setAttributeInternal(JOBID, value);
    }

    /**
     * Gets the attribute value for SourceId, using the alias name SourceId.
     * @return the value of SourceId
     */
    public Integer getSourceId() {
        return (Integer) getAttributeInternal(SOURCEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for SourceId.
     * @param value value to set the SourceId
     */
    public void setSourceId(Integer value) {
        setAttributeInternal(SOURCEID, value);
    }

    /**
     * Gets the attribute value for SourceEo, using the alias name SourceEo.
     * @return the value of SourceEo
     */
    public Integer getSourceEo() {
        return (Integer) getAttributeInternal(SOURCEEO);
    }

    /**
     * Sets <code>value</code> as the attribute value for SourceEo.
     * @param value value to set the SourceEo
     */
    public void setSourceEo(Integer value) {
        setAttributeInternal(SOURCEEO, value);
    }

    /**
     * Gets the attribute value for AdvertiseFreq, using the alias name AdvertiseFreq.
     * @return the value of AdvertiseFreq
     */
    public Integer getAdvertiseFreq() {
        return (Integer) getAttributeInternal(ADVERTISEFREQ);
    }

    /**
     * Sets <code>value</code> as the attribute value for AdvertiseFreq.
     * @param value value to set the AdvertiseFreq
     */
    public void setAdvertiseFreq(Integer value) {
        setAttributeInternal(ADVERTISEFREQ, value);
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
     * Gets the attribute value for JobCost, using the alias name JobCost.
     * @return the value of JobCost
     */
    public Number getJobCost() {
        return (Number) getAttributeInternal(JOBCOST);
    }

    /**
     * Sets <code>value</code> as the attribute value for JobCost.
     * @param value value to set the JobCost
     */
    public void setJobCost(Number value) {
        setAttributeInternal(JOBCOST, value);
    }

    /**
     * Gets the attribute value for PageNumber, using the alias name PageNumber.
     * @return the value of PageNumber
     */
    public String getPageNumber() {
        return (String) getAttributeInternal(PAGENUMBER);
    }

    /**
     * Sets <code>value</code> as the attribute value for PageNumber.
     * @param value value to set the PageNumber
     */
    public void setPageNumber(String value) {
        setAttributeInternal(PAGENUMBER, value);
    }


    /**
     * Gets the attribute value for DeptId, using the alias name DeptId.
     * @return the value of DeptId
     */
    public String getDeptId() {
        return (String) getAttributeInternal(DEPTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for DeptId.
     * @param value value to set the DeptId
     */
    public void setDeptId(String value) {
        setAttributeInternal(DEPTID, value);
    }

    /**
     * Gets the attribute value for DesigId, using the alias name DesigId.
     * @return the value of DesigId
     */
    public String getDesigId() {
        return (String) getAttributeInternal(DESIGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for DesigId.
     * @param value value to set the DesigId
     */
    public void setDesigId(String value) {
        setAttributeInternal(DESIGID, value);
    }

    /**
     * Gets the attribute value for LocId, using the alias name LocId.
     * @return the value of LocId
     */
    public String getLocId() {
        return (String) getAttributeInternal(LOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for LocId.
     * @param value value to set the LocId
     */
    public void setLocId(String value) {
        setAttributeInternal(LOCID, value);
    }

    /**
     * @param cldId key constituent
     * @param slocId key constituent
     * @param hoOrgId key constituent
     * @param orgId key constituent
     * @param docDt key constituent
     * @param jobId key constituent
     * @param sourceId key constituent
     * @param sourceEo key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String cldId, Integer slocId, String hoOrgId, String orgId, Timestamp docDt,
                                       String jobId, Integer sourceId, Integer sourceEo) {
        return new Key(new Object[] { cldId, slocId, hoOrgId, orgId, docDt, jobId, sourceId, sourceEo });
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        setCldId(EbizParams.GLBL_APP_CLD_ID());
        setSlocId(EbizParams.GLBL_APP_SERV_LOC());
        setHoOrgId(EbizParams.GLBL_HO_ORG_ID());
        setOrgId(EbizParams.GLBL_APP_USR_ORG());
        setDocDt(new oracle.jbo.domain.Timestamp(System.currentTimeMillis()));
        setUsrIdCreate(EbizParams.GLBL_APP_USR());
        setUsrIdCreateDt(new oracle.jbo.domain.Timestamp(System.currentTimeMillis()));
        setPageNumber("0");
        super.create(attributeList);
    }

    /**
     * Add locking logic here.
     */
    public void lock() {
        //super.lock();
    }

    /**
     * Custom DML update/insert/delete logic here.
     * @param operation the operation type
     * @param e the transaction event
     */
    protected void doDML(int operation, TransactionEvent e) {
        if (operation == DML_UPDATE) {
            setUsrIdMod(EbizParams.GLBL_APP_USR());
            setUsrIdModDt(new Timestamp(System.currentTimeMillis()));
        }
        super.doDML(operation, e);
    }
}

