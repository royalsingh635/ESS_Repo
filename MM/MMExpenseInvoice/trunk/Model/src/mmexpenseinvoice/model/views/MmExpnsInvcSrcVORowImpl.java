package mmexpenseinvoice.model.views;

//import java.sql.Timestamp;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import mmexpenseinvoice.model.entities.MmExpnsInvcSrcEOImpl;

import mmexpenseinvoice.model.services.MMExpenseInvoiceAMImpl;

import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Jul 08 15:44:50 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MmExpnsInvcSrcVORowImpl extends ViewRowImpl {


    public static final int ENTITY_MMEXPNSINVCSRCEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        SlocId,
        HoOrgId,
        OrgId,
        DocId,
        DocTypeSrc,
        DocIdSrc,
        DocDtSrc,
        FileNo,
        TransDocNo,
        TransTtlAmt,
        TransTotAmtSP,
        TransTotAmtBS,
        MmExpnsInvcLines;
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
    public static final int DOCTYPESRC = AttributesEnum.DocTypeSrc.index();
    public static final int DOCIDSRC = AttributesEnum.DocIdSrc.index();
    public static final int DOCDTSRC = AttributesEnum.DocDtSrc.index();
    public static final int FILENO = AttributesEnum.FileNo.index();
    public static final int TRANSDOCNO = AttributesEnum.TransDocNo.index();
    public static final int TRANSTTLAMT = AttributesEnum.TransTtlAmt.index();
    public static final int TRANSTOTAMTSP = AttributesEnum.TransTotAmtSP.index();
    public static final int TRANSTOTAMTBS = AttributesEnum.TransTotAmtBS.index();
    public static final int MMEXPNSINVCLINES = AttributesEnum.MmExpnsInvcLines.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MmExpnsInvcSrcVORowImpl() {
    }

    /**
     * Gets MmExpnsInvcSrcEO entity object.
     * @return the MmExpnsInvcSrcEO
     */
    public MmExpnsInvcSrcEOImpl getMmExpnsInvcSrcEO() {
        return (MmExpnsInvcSrcEOImpl) getEntity(ENTITY_MMEXPNSINVCSRCEO);
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
     * Gets the attribute value for DOC_TYPE_SRC using the alias name DocTypeSrc.
     * @return the DOC_TYPE_SRC
     */
    public Integer getDocTypeSrc() {
        return (Integer) getAttributeInternal(DOCTYPESRC);
    }

    /**
     * Sets <code>value</code> as attribute value for DOC_TYPE_SRC using the alias name DocTypeSrc.
     * @param value value to set the DOC_TYPE_SRC
     */
    public void setDocTypeSrc(Integer value) {
        setAttributeInternal(DOCTYPESRC, value);
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
     * Gets the attribute value for DOC_DT_SRC using the alias name DocDtSrc.
     * @return the DOC_DT_SRC
     */
    public Timestamp getDocDtSrc() {
        return (Timestamp) getAttributeInternal(DOCDTSRC);
    }

    /**
     * Sets <code>value</code> as attribute value for DOC_DT_SRC using the alias name DocDtSrc.
     * @param value value to set the DOC_DT_SRC
     */
    public void setDocDtSrc(Timestamp value) {
        setAttributeInternal(DOCDTSRC, value);
    }

    /**
     * Gets the attribute value for FILE_NO using the alias name FileNo.
     * @return the FILE_NO
     */
    public String getFileNo() {
        return (String) getAttributeInternal(FILENO);
    }

    /**
     * Sets <code>value</code> as attribute value for FILE_NO using the alias name FileNo.
     * @param value value to set the FILE_NO
     */
    public void setFileNo(String value) {
        setAttributeInternal(FILENO, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransDocNo.
     * @return the TransDocNo
     */
    public String getTransDocNo() {
        if(getDocIdSrc()!=null){
        MMExpenseInvoiceAMImpl am=(MMExpenseInvoiceAMImpl)this.getApplicationModule();
        ViewObjectImpl lovboeno=am.getLovExpenceSrc();
        lovboeno.setNamedWhereClauseParam("bindCldId", getCldId());
        lovboeno.setNamedWhereClauseParam("bindSlocId", getSlocId());
        lovboeno.setNamedWhereClauseParam("bindOrgId", getOrgId());
        lovboeno.executeQuery();
        
        Row [] rr=lovboeno.getFilteredRows("DocId", getDocIdSrc());
        if(rr.length>0){
            return rr[0].getAttribute("TxnDocNo").toString();
        }
        }
        
        
        return (String) getAttributeInternal(TRANSDOCNO);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransDocNo.
     * @param value value to set the  TransDocNo
     */
    public void setTransDocNo(String value) {
        setAttributeInternal(TRANSDOCNO, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransTtlAmt.
     * @return the TransTtlAmt
     */
    public Number getTransTtlAmt() {
        return (Number) getAttributeInternal(TRANSTTLAMT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransTtlAmt.
     * @param value value to set the  TransTtlAmt
     */
    public void setTransTtlAmt(Number value) {
        setAttributeInternal(TRANSTTLAMT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransTotAmtSP.
     * @return the TransTotAmtSP
     */
    public Number getTransTotAmtSP() {
        return (Number) getAttributeInternal(TRANSTOTAMTSP);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransTotAmtSP.
     * @param value value to set the  TransTotAmtSP
     */
    public void setTransTotAmtSP(Number value) {
        setAttributeInternal(TRANSTOTAMTSP, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransTotAmtBS.
     * @return the TransTotAmtBS
     */
    public Number getTransTotAmtBS() {
        return (Number) getAttributeInternal(TRANSTOTAMTBS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransTotAmtBS.
     * @param value value to set the  TransTotAmtBS
     */
    public void setTransTotAmtBS(Number value) {
        setAttributeInternal(TRANSTOTAMTBS, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link MmExpnsInvcLines.
     */
    public RowIterator getMmExpnsInvcLines() {
        return (RowIterator) getAttributeInternal(MMEXPNSINVCLINES);
    }
}

