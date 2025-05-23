package svcworkorderapp.model.views;

import java.sql.Timestamp;

import oracle.adf.share.logging.ADFLogger;
//import oracle.adf.share.logging.ADFLogger;
import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;

import svcworkorderapp.model.entities.SvcCmWoAsgnEOImpl;
import svcworkorderapp.model.services.SVCWorkOrderAMImpl;
import svcworkorderapp.model.views.common.SvcCmWoAsgnVORow;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Nov 07 15:23:45 PST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class SvcCmWoAsgnVORowImpl extends ViewRowImpl implements SvcCmWoAsgnVORow {

    public static final int ENTITY_SVCCMWOASGNEO = 0;
    private static ADFLogger adfLog = (ADFLogger)ADFLogger.createADFLogger(SvcCmWoAsgnVORowImpl.class);

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        SlocId,
        HoOrgId,
        OrgId,
        DocId,
        RqmtAreaId,
        EmpCode,
        UsrIdCrt,
        CrtDt,
        UsrIdMod,
        ModDt,
        DfctIdSrc,
        DocIdSrc,
        ItmIdSrc,
        TransEmpNm,
        TransDfctIdSrc,
        TransItmNmSrc,
        LovReqtAreaVO1,
        LovEmpNameVO1;
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
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int RQMTAREAID = AttributesEnum.RqmtAreaId.index();
    public static final int EMPCODE = AttributesEnum.EmpCode.index();
    public static final int USRIDCRT = AttributesEnum.UsrIdCrt.index();
    public static final int CRTDT = AttributesEnum.CrtDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int MODDT = AttributesEnum.ModDt.index();
    public static final int DFCTIDSRC = AttributesEnum.DfctIdSrc.index();
    public static final int DOCIDSRC = AttributesEnum.DocIdSrc.index();
    public static final int ITMIDSRC = AttributesEnum.ItmIdSrc.index();
    public static final int TRANSEMPNM = AttributesEnum.TransEmpNm.index();
    public static final int TRANSDFCTIDSRC = AttributesEnum.TransDfctIdSrc.index();
    public static final int TRANSITMNMSRC = AttributesEnum.TransItmNmSrc.index();
    public static final int LOVREQTAREAVO1 = AttributesEnum.LovReqtAreaVO1.index();
    public static final int LOVEMPNAMEVO1 = AttributesEnum.LovEmpNameVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public SvcCmWoAsgnVORowImpl() {
    }

    /**
     * Gets SvcCmWoAsgnEO entity object.
     * @return the SvcCmWoAsgnEO
     */
    public SvcCmWoAsgnEOImpl getSvcCmWoAsgnEO() {
        return (SvcCmWoAsgnEOImpl) getEntity(ENTITY_SVCCMWOASGNEO);
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
     * Gets the attribute value for DOC_ID using the alias name DocId.
     * @return the DOC_ID
     */
    public String getDocId() {
        return (String) getAttributeInternal(DOCID);
    }

    /**
     * Sets <code>value</code> as attribute value for DOC_ID using the alias name DocId.
     * @param value value to set the DOC_ID
     */
    public void setDocId(String value) {
        setAttributeInternal(DOCID, value);
    }

    /**
     * Gets the attribute value for RQMT_AREA_ID using the alias name RqmtAreaId.
     * @return the RQMT_AREA_ID
     */
    public Integer getRqmtAreaId() {
        return (Integer) getAttributeInternal(RQMTAREAID);
    }

    /**
     * Sets <code>value</code> as attribute value for RQMT_AREA_ID using the alias name RqmtAreaId.
     * @param value value to set the RQMT_AREA_ID
     */
    public void setRqmtAreaId(Integer value) {
        setAttributeInternal(RQMTAREAID, value);
    }

    /**
     * Gets the attribute value for EMP_CODE using the alias name EmpCode.
     * @return the EMP_CODE
     */
    public Integer getEmpCode() {
        return (Integer) getAttributeInternal(EMPCODE);
    }

    /**
     * Sets <code>value</code> as attribute value for EMP_CODE using the alias name EmpCode.
     * @param value value to set the EMP_CODE
     */
    public void setEmpCode(Integer value) {
        setAttributeInternal(EMPCODE, value);
    }

    /**
     * Gets the attribute value for USR_ID_CRT using the alias name UsrIdCrt.
     * @return the USR_ID_CRT
     */
    public Integer getUsrIdCrt() {
        return (Integer) getAttributeInternal(USRIDCRT);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_CRT using the alias name UsrIdCrt.
     * @param value value to set the USR_ID_CRT
     */
    public void setUsrIdCrt(Integer value) {
        setAttributeInternal(USRIDCRT, value);
    }

    /**
     * Gets the attribute value for CRT_DT using the alias name CrtDt.
     * @return the CRT_DT
     */
    public Timestamp getCrtDt() {
        return (Timestamp) getAttributeInternal(CRTDT);
    }

    /**
     * Sets <code>value</code> as attribute value for CRT_DT using the alias name CrtDt.
     * @param value value to set the CRT_DT
     */
    public void setCrtDt(Timestamp value) {
        setAttributeInternal(CRTDT, value);
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
     * Gets the attribute value for MOD_DT using the alias name ModDt.
     * @return the MOD_DT
     */
    public Timestamp getModDt() {
        return (Timestamp) getAttributeInternal(MODDT);
    }

    /**
     * Sets <code>value</code> as attribute value for MOD_DT using the alias name ModDt.
     * @param value value to set the MOD_DT
     */
    public void setModDt(Timestamp value) {
        setAttributeInternal(MODDT, value);
    }

    /**
     * Gets the attribute value for DFCT_ID_SRC using the alias name DfctIdSrc.
     * @return the DFCT_ID_SRC
     */
    public Integer getDfctIdSrc() {
        return (Integer) getAttributeInternal(DFCTIDSRC);
    }

    /**
     * Sets <code>value</code> as attribute value for DFCT_ID_SRC using the alias name DfctIdSrc.
     * @param value value to set the DFCT_ID_SRC
     */
    public void setDfctIdSrc(Integer value) {
        setAttributeInternal(DFCTIDSRC, value);
    }

    /**
     * Gets the attribute value for DOC_ID_SRC using the alias name DocIdSrc.
     * @return the DOC_ID_SRC
     */
    public String getDocIdSrc() {
        return (String) getAttributeInternal(DOCIDSRC);
    }

    /**
     * Sets <code>value</code> as attribute value for DOC_ID_SRC using the alias name DocIdSrc.
     * @param value value to set the DOC_ID_SRC
     */
    public void setDocIdSrc(String value) {
        setAttributeInternal(DOCIDSRC, value);
    }

    /**
     * Gets the attribute value for ITM_ID_SRC using the alias name ItmIdSrc.
     * @return the ITM_ID_SRC
     */
    public String getItmIdSrc() {
        return (String) getAttributeInternal(ITMIDSRC);
    }

    /**
     * Sets <code>value</code> as attribute value for ITM_ID_SRC using the alias name ItmIdSrc.
     * @param value value to set the ITM_ID_SRC
     */
    public void setItmIdSrc(String value) {
        setAttributeInternal(ITMIDSRC, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransEmpNm.
     * @return the TransEmpNm
     */
    public String getTransEmpNm() {
        SVCWorkOrderAMImpl am = (SVCWorkOrderAMImpl) this.getApplicationModule();
        // AppModuleAMImpl am = (AppModuleAMImpl) this.getApplicationModule();
        ViewObjectImpl emp = am.getLovEmpName1();
        if (getEmpCode() != null) {
            RowQualifier rq=new RowQualifier(emp);
            rq.setWhereClause("EmpCode="+getEmpCode()+" and OrgId='"+ getOrgId() +"' and HoOrgId='"+getHoOrgId()+"'");
            Row r[] = emp.getFilteredRows(rq);
            if (r.length > 0) {
                String nm = r[0].getAttribute("EmpNm").toString();
                return nm;
            }
        }
        return (String) getAttributeInternal(TRANSEMPNM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransEmpNm.
     * @param value value to set the  TransEmpNm
     */
    public void setTransEmpNm(String value) {
        setAttributeInternal(TRANSEMPNM, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransDfctIdSrc.
     * @return the TransDfctIdSrc
     */
    public String getTransDfctIdSrc() {
      //  adfLog.info(" in the getter of transdefectidsrc"+getDfctIdSrc());
        SVCWorkOrderAMImpl am =
                (SVCWorkOrderAMImpl)this.getApplicationModule();
        if(getDfctIdSrc()!=null) {
          //  System.out.println("Defect id:"+getDfctIdSrc());
            Row r[]=am.getLovDfctNm2().getFilteredRows("DfctId",getDfctIdSrc());
         //   System.out.println(r.length);
            if(r.length>0) {
                String name=r[0].getAttribute("DfctNm").toString();
           //     System.out.println("Defect name:"+name);
            return name;
            }
        }
        return (String) getAttributeInternal(TRANSDFCTIDSRC);
    }

    /**
     * Gets the attribute value for the calculated attribute TransItmNmSrc.
     * @return the TransItmNmSrc
     */
    public String getTransItmNmSrc() {
        SVCWorkOrderAMImpl am =
                        (SVCWorkOrderAMImpl)this.getApplicationModule();
                if(getItmIdSrc()!=null) {
                    ViewObjectImpl itm=am.getLovItmDesc1();
                    itm.setNamedWhereClauseParam("BindCldIdVar", getCldId());
                    itm.setNamedWhereClauseParam("BindHoOrgId", getHoOrgId());
                    itm.setNamedWhereClauseParam("BindOrgIdVar", getOrgId());
                    itm.setNamedWhereClauseParam("BindSlocIdVar", getSlocId());
                    itm.executeQuery();
                    Row r[]=am.getLovItmDesc1().getFilteredRows("ItmId",getItmIdSrc());
                    if(r.length>0) {
                        String name=r[0].getAttribute("ItmDesc").toString();
                    return name;
                    }
                   
                }
        return (String) getAttributeInternal(TRANSITMNMSRC);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovReqtAreaVO1.
     */
    public RowSet getLovReqtAreaVO1() {
        return (RowSet) getAttributeInternal(LOVREQTAREAVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovEmpNameVO1.
     */
    public RowSet getLovEmpNameVO1() {
        return (RowSet) getAttributeInternal(LOVEMPNAMEVO1);
    }
}

