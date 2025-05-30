package mnfProductionOrderApp.model.views;

import mnfProductionOrderApp.model.entities.MnfPdoOpItmEOImpl;

import mnfProductionOrderApp.model.services.MNFProductionorderAppAMImpl;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Oct 09 12:27:38 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MnfPdoOpItmVORowImpl extends ViewRowImpl {


    public static final int ENTITY_MNFPDOOPITMEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        SlocId,
        HoOrgId,
        OrgId,
        DocId,
        ItmBasis,
        OpId,
        OpSrNo,
        ItmType,
        ItmId,
        ItmUom,
        IssuType,
        ItmQty,
        ItmPrice,
        ItmTotAmt,
        ItmScrapPer,
        ItmRmrk,
        ItmPriceType,
        ItmRtrnFlg,
        ItmRtrnQty,
        UsrIdCreate,
        UsrIdCreateDt,
        UsrIdMod,
        UsrIdModDt,
        TransOpId,
        TransOpDesc,
        TransUOMDesc,
        CcId,
        LOVIssueTypVO,
        LOVItemNameVO,
        LOVItmTyVO,
        LOVOpDescVO;
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
    public static final int ITMBASIS = AttributesEnum.ItmBasis.index();
    public static final int OPID = AttributesEnum.OpId.index();
    public static final int OPSRNO = AttributesEnum.OpSrNo.index();
    public static final int ITMTYPE = AttributesEnum.ItmType.index();
    public static final int ITMID = AttributesEnum.ItmId.index();
    public static final int ITMUOM = AttributesEnum.ItmUom.index();
    public static final int ISSUTYPE = AttributesEnum.IssuType.index();
    public static final int ITMQTY = AttributesEnum.ItmQty.index();
    public static final int ITMPRICE = AttributesEnum.ItmPrice.index();
    public static final int ITMTOTAMT = AttributesEnum.ItmTotAmt.index();
    public static final int ITMSCRAPPER = AttributesEnum.ItmScrapPer.index();
    public static final int ITMRMRK = AttributesEnum.ItmRmrk.index();
    public static final int ITMPRICETYPE = AttributesEnum.ItmPriceType.index();
    public static final int ITMRTRNFLG = AttributesEnum.ItmRtrnFlg.index();
    public static final int ITMRTRNQTY = AttributesEnum.ItmRtrnQty.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int TRANSOPID = AttributesEnum.TransOpId.index();
    public static final int TRANSOPDESC = AttributesEnum.TransOpDesc.index();
    public static final int TRANSUOMDESC = AttributesEnum.TransUOMDesc.index();
    public static final int CCID = AttributesEnum.CcId.index();
    public static final int LOVISSUETYPVO = AttributesEnum.LOVIssueTypVO.index();
    public static final int LOVITEMNAMEVO = AttributesEnum.LOVItemNameVO.index();
    public static final int LOVITMTYVO = AttributesEnum.LOVItmTyVO.index();
    public static final int LOVOPDESCVO = AttributesEnum.LOVOpDescVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MnfPdoOpItmVORowImpl() {
    }

    /**
     * Gets MnfPdoOpItmEO entity object.
     * @return the MnfPdoOpItmEO
     */
    public MnfPdoOpItmEOImpl getMnfPdoOpItmEO() {
        return (MnfPdoOpItmEOImpl) getEntity(ENTITY_MNFPDOOPITMEO);
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
     * Gets the attribute value for ITM_BASIS using the alias name ItmBasis.
     * @return the ITM_BASIS
     */
    public Integer getItmBasis() {
        return (Integer) getAttributeInternal(ITMBASIS);
    }

    /**
     * Sets <code>value</code> as attribute value for ITM_BASIS using the alias name ItmBasis.
     * @param value value to set the ITM_BASIS
     */
    public void setItmBasis(Integer value) {
        setAttributeInternal(ITMBASIS, value);
    }

    /**
     * Gets the attribute value for OP_ID using the alias name OpId.
     * @return the OP_ID
     */
    public String getOpId() {
        return (String) getAttributeInternal(OPID);
    }

    /**
     * Sets <code>value</code> as attribute value for OP_ID using the alias name OpId.
     * @param value value to set the OP_ID
     */
    public void setOpId(String value) {
        setAttributeInternal(OPID, value);
    }

    /**
     * Gets the attribute value for OP_SR_NO using the alias name OpSrNo.
     * @return the OP_SR_NO
     */
    public Number getOpSrNo() {
        return (Number) getAttributeInternal(OPSRNO);
    }

    /**
     * Sets <code>value</code> as attribute value for OP_SR_NO using the alias name OpSrNo.
     * @param value value to set the OP_SR_NO
     */
    public void setOpSrNo(Number value) {
        setAttributeInternal(OPSRNO, value);
    }

    /**
     * Gets the attribute value for ITM_TYPE using the alias name ItmType.
     * @return the ITM_TYPE
     */
    public Integer getItmType() {
        return (Integer) getAttributeInternal(ITMTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for ITM_TYPE using the alias name ItmType.
     * @param value value to set the ITM_TYPE
     */
    public void setItmType(Integer value) {
        setAttributeInternal(ITMTYPE, value);
    }

    /**
     * Gets the attribute value for ITM_ID using the alias name ItmId.
     * @return the ITM_ID
     */
    public String getItmId() {
        return (String) getAttributeInternal(ITMID);
    }

    /**
     * Sets <code>value</code> as attribute value for ITM_ID using the alias name ItmId.
     * @param value value to set the ITM_ID
     */
    public void setItmId(String value) {
        setAttributeInternal(ITMID, value);
    }

    /**
     * Gets the attribute value for ITM_UOM using the alias name ItmUom.
     * @return the ITM_UOM
     */
    public String getItmUom() {
        return (String) getAttributeInternal(ITMUOM);
    }

    /**
     * Sets <code>value</code> as attribute value for ITM_UOM using the alias name ItmUom.
     * @param value value to set the ITM_UOM
     */
    public void setItmUom(String value) {
        setAttributeInternal(ITMUOM, value);
    }

    /**
     * Gets the attribute value for ISSU_TYPE using the alias name IssuType.
     * @return the ISSU_TYPE
     */
    public Integer getIssuType() {
        return (Integer) getAttributeInternal(ISSUTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for ISSU_TYPE using the alias name IssuType.
     * @param value value to set the ISSU_TYPE
     */
    public void setIssuType(Integer value) {
        setAttributeInternal(ISSUTYPE, value);
    }

    /**
     * Gets the attribute value for ITM_QTY using the alias name ItmQty.
     * @return the ITM_QTY
     */
    public Number getItmQty() {
        return (Number) getAttributeInternal(ITMQTY);
    }

    /**
     * Sets <code>value</code> as attribute value for ITM_QTY using the alias name ItmQty.
     * @param value value to set the ITM_QTY
     */
    public void setItmQty(Number value) {
        setAttributeInternal(ITMQTY, value);
    }

    /**
     * Gets the attribute value for ITM_PRICE using the alias name ItmPrice.
     * @return the ITM_PRICE
     */
    public Number getItmPrice() {
        return (Number) getAttributeInternal(ITMPRICE);
    }

    /**
     * Sets <code>value</code> as attribute value for ITM_PRICE using the alias name ItmPrice.
     * @param value value to set the ITM_PRICE
     */
    public void setItmPrice(Number value) {
        setAttributeInternal(ITMPRICE, value);
    }

    /**
     * Gets the attribute value for ITM_TOT_AMT using the alias name ItmTotAmt.
     * @return the ITM_TOT_AMT
     */
    public Number getItmTotAmt() {
        return (Number) getAttributeInternal(ITMTOTAMT);
    }

    /**
     * Sets <code>value</code> as attribute value for ITM_TOT_AMT using the alias name ItmTotAmt.
     * @param value value to set the ITM_TOT_AMT
     */
    public void setItmTotAmt(Number value) {
        setAttributeInternal(ITMTOTAMT, value);
    }

    /**
     * Gets the attribute value for ITM_SCRAP_PER using the alias name ItmScrapPer.
     * @return the ITM_SCRAP_PER
     */
    public Number getItmScrapPer() {
        return (Number) getAttributeInternal(ITMSCRAPPER);
    }

    /**
     * Sets <code>value</code> as attribute value for ITM_SCRAP_PER using the alias name ItmScrapPer.
     * @param value value to set the ITM_SCRAP_PER
     */
    public void setItmScrapPer(Number value) {
        setAttributeInternal(ITMSCRAPPER, value);
    }

    /**
     * Gets the attribute value for ITM_RMRK using the alias name ItmRmrk.
     * @return the ITM_RMRK
     */
    public String getItmRmrk() {
        return (String) getAttributeInternal(ITMRMRK);
    }

    /**
     * Sets <code>value</code> as attribute value for ITM_RMRK using the alias name ItmRmrk.
     * @param value value to set the ITM_RMRK
     */
    public void setItmRmrk(String value) {
        setAttributeInternal(ITMRMRK, value);
    }

    /**
     * Gets the attribute value for ITM_PRICE_TYPE using the alias name ItmPriceType.
     * @return the ITM_PRICE_TYPE
     */
    public Integer getItmPriceType() {
        return (Integer) getAttributeInternal(ITMPRICETYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for ITM_PRICE_TYPE using the alias name ItmPriceType.
     * @param value value to set the ITM_PRICE_TYPE
     */
    public void setItmPriceType(Integer value) {
        setAttributeInternal(ITMPRICETYPE, value);
    }

    /**
     * Gets the attribute value for ITM_RTRN_FLG using the alias name ItmRtrnFlg.
     * @return the ITM_RTRN_FLG
     */
    public String getItmRtrnFlg() {
        return (String) getAttributeInternal(ITMRTRNFLG);
    }

    /**
     * Sets <code>value</code> as attribute value for ITM_RTRN_FLG using the alias name ItmRtrnFlg.
     * @param value value to set the ITM_RTRN_FLG
     */
    public void setItmRtrnFlg(String value) {
        setAttributeInternal(ITMRTRNFLG, value);
    }

    /**
     * Gets the attribute value for ITM_RTRN_QTY using the alias name ItmRtrnQty.
     * @return the ITM_RTRN_QTY
     */
    public Number getItmRtrnQty() {
        return (Number) getAttributeInternal(ITMRTRNQTY);
    }

    /**
     * Sets <code>value</code> as attribute value for ITM_RTRN_QTY using the alias name ItmRtrnQty.
     * @param value value to set the ITM_RTRN_QTY
     */
    public void setItmRtrnQty(Number value) {
        setAttributeInternal(ITMRTRNQTY, value);
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
     * Gets the attribute value for the calculated attribute TransOpId.
     * @return the TransOpId
     */
    public String getTransOpId() {
        return (String) getAttributeInternal(TRANSOPID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransOpId.
     * @param value value to set the  TransOpId
     */
    public void setTransOpId(String value) {
        setAttributeInternal(TRANSOPID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransOpDesc.
     * @return the TransOpDesc
     */
    public String getTransOpDesc() {

        if (getOpId() != null) {
            if (getAM().getLOVOpDescVO1().getFilteredRows("DocId", getOpId()).length > 0) {

                return getAM().getLOVOpDescVO1().getFilteredRows("DocId",
                                                                 getOpId())[0].getAttribute("OpDesc").toString();

            }
        }

        return (String) getAttributeInternal(TRANSOPDESC);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransOpDesc.
     * @param value value to set the  TransOpDesc
     */
    public void setTransOpDesc(String value) {
        setAttributeInternal(TRANSOPDESC, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransUOMDesc.
     * @return the TransUOMDesc
     */
    public String getTransUOMDesc() {
        if (getItmUom() != null) {
            ViewObjectImpl itemdescvoimpl = getAM().getLOVUOMDescVO1();
            // itemdescvoimpl.setNamedWhereClauseParam("BindOpDocId", getOpId());
            // itemdescvoimpl.executeQuery();
            Row[] wcRow = itemdescvoimpl.getFilteredRows("UomId", getItmUom());
            if (wcRow.length > 0) {
                if (wcRow[0].getAttribute("UomDesc") != null) {

                    try {
                        return wcRow[0].getAttribute("UomDesc").toString();
                    } catch (Exception sqle) {
                        sqle.printStackTrace();
                    }
                }
            }
        }

        return (String) getAttributeInternal(TRANSUOMDESC);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransUOMDesc.
     * @param value value to set the  TransUOMDesc
     */
    public void setTransUOMDesc(String value) {
        setAttributeInternal(TRANSUOMDESC, value);
    }

    /**
     * Gets the attribute value for CC_ID using the alias name CcId.
     * @return the CC_ID
     */
    public String getCcId() {
        return (String) getAttributeInternal(CCID);
    }

    /**
     * Sets <code>value</code> as attribute value for CC_ID using the alias name CcId.
     * @param value value to set the CC_ID
     */
    public void setCcId(String value) {
        setAttributeInternal(CCID, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVIssueTypVO.
     */
    public RowSet getLOVIssueTypVO() {
        return (RowSet) getAttributeInternal(LOVISSUETYPVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVItemNameVO.
     */
    public RowSet getLOVItemNameVO() {
        return (RowSet) getAttributeInternal(LOVITEMNAMEVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVItmTyVO.
     */
    public RowSet getLOVItmTyVO() {
        return (RowSet) getAttributeInternal(LOVITMTYVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVOpDescVO.
     */
    public RowSet getLOVOpDescVO() {
        return (RowSet) getAttributeInternal(LOVOPDESCVO);
    }

    /**
     *
     * Get AM Instance
     * **/
    public MNFProductionorderAppAMImpl getAM() {
        return (MNFProductionorderAppAMImpl) getApplicationModule();

    }
}

