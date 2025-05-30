package mnfroutecard.model.views;

import mnfroutecard.model.entities.MnfRcParamEOImpl;

import mnfroutecard.model.services.MNFRouteCardAMImpl;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Sep 24 11:52:45 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MnfRcParamVORowImpl extends ViewRowImpl {


    public static final int ENTITY_MNFRCPARAMEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        DocId,
        EndDt,
        HoOrgId,
        OrgId,
        ParamId,
        ParamRmrk,
        ParamType,
        ParamVal,
        SlocId,
        StrtDt,
        UsrIdCreate,
        UsrIdCreateDt,
        UsrIdMod,
        UsrIdModDt,
        OverParamNameTrans,
        ParamBasisTrans,
        ParamBasisIdTrans,
        BreakParamNameTrans,
        ParamSetId,
        OpId,
        OpSrNo,
        TransOpNme,
        LOVMnfParamVO1,
        LOVMnfParamSingleVO1,
        LOVMachineDownTimeVO1,
        LOVOpDescVO1;
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
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int ENDDT = AttributesEnum.EndDt.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int PARAMID = AttributesEnum.ParamId.index();
    public static final int PARAMRMRK = AttributesEnum.ParamRmrk.index();
    public static final int PARAMTYPE = AttributesEnum.ParamType.index();
    public static final int PARAMVAL = AttributesEnum.ParamVal.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int STRTDT = AttributesEnum.StrtDt.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int OVERPARAMNAMETRANS = AttributesEnum.OverParamNameTrans.index();
    public static final int PARAMBASISTRANS = AttributesEnum.ParamBasisTrans.index();
    public static final int PARAMBASISIDTRANS = AttributesEnum.ParamBasisIdTrans.index();
    public static final int BREAKPARAMNAMETRANS = AttributesEnum.BreakParamNameTrans.index();
    public static final int PARAMSETID = AttributesEnum.ParamSetId.index();
    public static final int OPID = AttributesEnum.OpId.index();
    public static final int OPSRNO = AttributesEnum.OpSrNo.index();
    public static final int TRANSOPNME = AttributesEnum.TransOpNme.index();
    public static final int LOVMNFPARAMVO1 = AttributesEnum.LOVMnfParamVO1.index();
    public static final int LOVMNFPARAMSINGLEVO1 = AttributesEnum.LOVMnfParamSingleVO1.index();
    public static final int LOVMACHINEDOWNTIMEVO1 = AttributesEnum.LOVMachineDownTimeVO1.index();
    public static final int LOVOPDESCVO1 = AttributesEnum.LOVOpDescVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MnfRcParamVORowImpl() {
    }

    /**
     * Gets MnfRcParamEO entity object.
     * @return the MnfRcParamEO
     */
    public MnfRcParamEOImpl getMnfRcParamEO() {
        return (MnfRcParamEOImpl) getEntity(ENTITY_MNFRCPARAMEO);
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
     * Gets the attribute value for END_DT using the alias name EndDt.
     * @return the END_DT
     */
    public Timestamp getEndDt() {
        return (Timestamp) getAttributeInternal(ENDDT);
    }

    /**
     * Sets <code>value</code> as attribute value for END_DT using the alias name EndDt.
     * @param value value to set the END_DT
     */
    public void setEndDt(Timestamp value) {
        setAttributeInternal(ENDDT, value);
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
     * Gets the attribute value for PARAM_ID using the alias name ParamId.
     * @return the PARAM_ID
     */
    public String getParamId() {
        return (String) getAttributeInternal(PARAMID);
    }

    /**
     * Sets <code>value</code> as attribute value for PARAM_ID using the alias name ParamId.
     * @param value value to set the PARAM_ID
     */
    public void setParamId(String value) {
        setAttributeInternal(PARAMID, value);
    }

    /**
     * Gets the attribute value for PARAM_RMRK using the alias name ParamRmrk.
     * @return the PARAM_RMRK
     */
    public String getParamRmrk() {
        return (String) getAttributeInternal(PARAMRMRK);
    }

    /**
     * Sets <code>value</code> as attribute value for PARAM_RMRK using the alias name ParamRmrk.
     * @param value value to set the PARAM_RMRK
     */
    public void setParamRmrk(String value) {
        setAttributeInternal(PARAMRMRK, value);
    }

    /**
     * Gets the attribute value for PARAM_TYPE using the alias name ParamType.
     * @return the PARAM_TYPE
     */
    public Integer getParamType() {
        return (Integer) getAttributeInternal(PARAMTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for PARAM_TYPE using the alias name ParamType.
     * @param value value to set the PARAM_TYPE
     */
    public void setParamType(Integer value) {
        setAttributeInternal(PARAMTYPE, value);
    }

    /**
     * Gets the attribute value for PARAM_VAL using the alias name ParamVal.
     * @return the PARAM_VAL
     */
    public oracle.jbo.domain.Number getParamVal() {
        return (oracle.jbo.domain.Number) getAttributeInternal(PARAMVAL);
    }

    /**
     * Sets <code>value</code> as attribute value for PARAM_VAL using the alias name ParamVal.
     * @param value value to set the PARAM_VAL
     */
    public void setParamVal(oracle.jbo.domain.Number value) {
        setAttributeInternal(PARAMVAL, value);
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
     * Gets the attribute value for STRT_DT using the alias name StrtDt.
     * @return the STRT_DT
     */
    public Timestamp getStrtDt() {
        return (Timestamp) getAttributeInternal(STRTDT);
    }

    /**
     * Sets <code>value</code> as attribute value for STRT_DT using the alias name StrtDt.
     * @param value value to set the STRT_DT
     */
    public void setStrtDt(Timestamp value) {
        setAttributeInternal(STRTDT, value);
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
     * Gets the attribute value for the calculated attribute OverParamNameTrans.
     * @return the OverParamNameTrans
     */
    public String getOverParamNameTrans() {
        MNFRouteCardAMImpl am = (MNFRouteCardAMImpl) this.getApplicationModule();
        ViewObjectImpl impl = am.getLOVMnfParamVO1();        
        Row[] r = impl.getFilteredRows("ParamId", this.getParamId());
        if(r.length > 0){
            return r[0].getAttribute("ParamDesc").toString();
        }
        return (String) getAttributeInternal(OVERPARAMNAMETRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute OverParamNameTrans.
     * @param value value to set the  OverParamNameTrans
     */
    public void setOverParamNameTrans(String value) {
        setAttributeInternal(OVERPARAMNAMETRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ParamBasisTrans.
     * @return the ParamBasisTrans
     */
    public String getParamBasisTrans() {
        MNFRouteCardAMImpl am = (MNFRouteCardAMImpl) this.getApplicationModule();
        ViewObjectImpl impl = am.getLOVMnfParamVO1();        
        Row[] r = impl.getFilteredRows("ParamId", this.getParamId());
        if(r.length > 0){
            return r[0].getAttribute("AttNm").toString();
        }
        return (String) getAttributeInternal(PARAMBASISTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ParamBasisTrans.
     * @param value value to set the  ParamBasisTrans
     */
    public void setParamBasisTrans(String value) {
        setAttributeInternal(PARAMBASISTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ParamBasisIdTrans.
     * @return the ParamBasisIdTrans
     */
    public Integer getParamBasisIdTrans() {
        MNFRouteCardAMImpl am = (MNFRouteCardAMImpl) this.getApplicationModule();
        ViewObjectImpl impl = am.getLOVMnfParamVO1();        
        Row[] r = impl.getFilteredRows("ParamId", this.getParamId());
        if(r.length > 0){
            return (Integer)r[0].getAttribute("ParamBasis");
        }
        
        return (Integer) getAttributeInternal(PARAMBASISIDTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ParamBasisIdTrans.
     * @param value value to set the  ParamBasisIdTrans
     */
    public void setParamBasisIdTrans(Integer value) {
        setAttributeInternal(PARAMBASISIDTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute BreakParamNameTrans.
     * @return the BreakParamNameTrans
     */
    public String getBreakParamNameTrans() {
        MNFRouteCardAMImpl am = (MNFRouteCardAMImpl) this.getApplicationModule();
        ViewObjectImpl impl = am.getLOVMachineDownTimeVO1();        
        Row[] r = impl.getFilteredRows("ParamId", this.getParamId());
        if(r.length > 0){
            return r[0].getAttribute("ParamDesc").toString();
        }
        return (String) getAttributeInternal(BREAKPARAMNAMETRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute BreakParamNameTrans.
     * @param value value to set the  BreakParamNameTrans
     */
    public void setBreakParamNameTrans(String value) {
        setAttributeInternal(BREAKPARAMNAMETRANS, value);
    }

    /**
     * Gets the attribute value for PARAM_SET_ID using the alias name ParamSetId.
     * @return the PARAM_SET_ID
     */
    public String getParamSetId() {
        return (String) getAttributeInternal(PARAMSETID);
    }

    /**
     * Sets <code>value</code> as attribute value for PARAM_SET_ID using the alias name ParamSetId.
     * @param value value to set the PARAM_SET_ID
     */
    public void setParamSetId(String value) {
        setAttributeInternal(PARAMSETID, value);
    }

    /**
     * Gets the attribute value for OP_ID using the alias name OpId.
     * @return the OP_ID
     */
    public String getOpId() {
       // System.out.println("Operation Value is : 0  " + (String) getAttributeInternal(OPID));
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
    public Integer getOpSrNo() {
        return (Integer) getAttributeInternal(OPSRNO);
    }

    /**
     * Sets <code>value</code> as attribute value for OP_SR_NO using the alias name OpSrNo.
     * @param value value to set the OP_SR_NO
     */
    public void setOpSrNo(Integer value) {
        setAttributeInternal(OPSRNO, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransOpNme.
     * @return the TransOpNme
     */
    public String getTransOpNme() {
        if (getOpId() == null) {
            ViewObjectImpl impl = getAm().getLOVOpDescVO1();
           // impl.setNamedWhereClauseParam("BindOpId", this.getOpId());
            impl.setNamedWhereClauseParam("BindCldId", this.getCldId());
            impl.setNamedWhereClauseParam("BindSlcId", this.getSlocId());
            impl.setNamedWhereClauseParam("BindHoOrgId", this.getHoOrgId());
            impl.executeQuery();
            //Row[] allRowsInRange = impl.getAllRowsInRange();
            Row[] allRowsInRange = impl.getFilteredRows("DocId", getOpId() );
            if (allRowsInRange.length > 0) {
             //   System.out.println("OP DESC is : " + allRowsInRange[0].getAttribute("OpDesc"));
                return (String)allRowsInRange[0].getAttribute("OpDesc");
            }
        }
        return (String) getAttributeInternal(TRANSOPNME);
    }
    /**
     * @return
     * Generalized function to call object of application module.
     */
    public MNFRouteCardAMImpl getAm() {
        MNFRouteCardAMImpl am = (MNFRouteCardAMImpl)this.getApplicationModule();
        return am;
    }
    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransOpNme.
     * @param value value to set the  TransOpNme
     */
    public void setTransOpNme(String value) {
        setAttributeInternal(TRANSOPNME, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVMnfParamVO1.
     */
    public RowSet getLOVMnfParamVO1() {
        return (RowSet) getAttributeInternal(LOVMNFPARAMVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVMnfParamSingleVO1.
     */
    public RowSet getLOVMnfParamSingleVO1() {
        return (RowSet) getAttributeInternal(LOVMNFPARAMSINGLEVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVMachineDownTimeVO1.
     */
    public RowSet getLOVMachineDownTimeVO1() {
        return (RowSet) getAttributeInternal(LOVMACHINEDOWNTIMEVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVOpDescVO1.
     */
    public RowSet getLOVOpDescVO1() {
        return (RowSet) getAttributeInternal(LOVOPDESCVO1);
    }
}

