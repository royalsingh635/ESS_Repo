package mmreceiptmtl.model.views;

import java.math.BigDecimal;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import mmreceiptmtl.model.services.MMReceiptMtlAMImpl;

import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.domain.NClobDomain;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Nov 13 11:17:58 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MmMtlRcptTrVORowImpl extends ViewRowImpl {
    public static final int ENTITY_MMMTLRCPTTREO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CldId {
            public Object get(MmMtlRcptTrVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(MmMtlRcptTrVORowImpl obj, Object value) {
                obj.setCldId((String)value);
            }
        }
        ,
        SlocId {
            public Object get(MmMtlRcptTrVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(MmMtlRcptTrVORowImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        OrgId {
            public Object get(MmMtlRcptTrVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(MmMtlRcptTrVORowImpl obj, Object value) {
                obj.setOrgId((String)value);
            }
        }
        ,
        WhId {
            public Object get(MmMtlRcptTrVORowImpl obj) {
                return obj.getWhId();
            }

            public void put(MmMtlRcptTrVORowImpl obj, Object value) {
                obj.setWhId((String)value);
            }
        }
        ,
        DocId {
            public Object get(MmMtlRcptTrVORowImpl obj) {
                return obj.getDocId();
            }

            public void put(MmMtlRcptTrVORowImpl obj, Object value) {
                obj.setDocId((String)value);
            }
        }
        ,
        DocIdSrc {
            public Object get(MmMtlRcptTrVORowImpl obj) {
                return obj.getDocIdSrc();
            }

            public void put(MmMtlRcptTrVORowImpl obj, Object value) {
                obj.setDocIdSrc((String)value);
            }
        }
        ,
        DlvSchdlNo {
            public Object get(MmMtlRcptTrVORowImpl obj) {
                return obj.getDlvSchdlNo();
            }

            public void put(MmMtlRcptTrVORowImpl obj, Object value) {
                obj.setDlvSchdlNo((Integer)value);
            }
        }
        ,
        ItmId {
            public Object get(MmMtlRcptTrVORowImpl obj) {
                return obj.getItmId();
            }

            public void put(MmMtlRcptTrVORowImpl obj, Object value) {
                obj.setItmId((String)value);
            }
        }
        ,
        ItmUom {
            public Object get(MmMtlRcptTrVORowImpl obj) {
                return obj.getItmUom();
            }

            public void put(MmMtlRcptTrVORowImpl obj, Object value) {
                obj.setItmUom((String)value);
            }
        }
        ,
        TaxRuleId {
            public Object get(MmMtlRcptTrVORowImpl obj) {
                return obj.getTaxRuleId();
            }

            public void put(MmMtlRcptTrVORowImpl obj, Object value) {
                obj.setTaxRuleId((Integer)value);
            }
        }
        ,
        TaxRuleFlg {
            public Object get(MmMtlRcptTrVORowImpl obj) {
                return obj.getTaxRuleFlg();
            }

            public void put(MmMtlRcptTrVORowImpl obj, Object value) {
                obj.setTaxRuleFlg((String)value);
            }
        }
        ,
        TaxExmptFlg {
            public Object get(MmMtlRcptTrVORowImpl obj) {
                return obj.getTaxExmptFlg();
            }

            public void put(MmMtlRcptTrVORowImpl obj, Object value) {
                obj.setTaxExmptFlg((String)value);
            }
        }
        ,
        TaxAmt {
            public Object get(MmMtlRcptTrVORowImpl obj) {
                return obj.getTaxAmt();
            }

            public void put(MmMtlRcptTrVORowImpl obj, Object value) {
                obj.setTaxAmt((oracle.jbo.domain.Number)value);
            }
        }
        ,
        TaxAmtBs {
            public Object get(MmMtlRcptTrVORowImpl obj) {
                return obj.getTaxAmtBs();
            }

            public void put(MmMtlRcptTrVORowImpl obj, Object value) {
                obj.setTaxAmtBs((oracle.jbo.domain.Number)value);
            }
        }
        ,
        TaxableAmtSp {
            public Object get(MmMtlRcptTrVORowImpl obj) {
                return obj.getTaxableAmtSp();
            }

            public void put(MmMtlRcptTrVORowImpl obj, Object value) {
                obj.setTaxableAmtSp((oracle.jbo.domain.Number)value);
            }
        }
        ,
        TaxableAmtBs {
            public Object get(MmMtlRcptTrVORowImpl obj) {
                return obj.getTaxableAmtBs();
            }

            public void put(MmMtlRcptTrVORowImpl obj, Object value) {
                obj.setTaxableAmtBs((oracle.jbo.domain.Number)value);
            }
        }
        ,
        TransTaxRuleId {
            public Object get(MmMtlRcptTrVORowImpl obj) {
                return obj.getTransTaxRuleId();
            }

            public void put(MmMtlRcptTrVORowImpl obj, Object value) {
                obj.setTransTaxRuleId((String)value);
            }
        }
        ,
        TransHoOrgId {
            public Object get(MmMtlRcptTrVORowImpl obj) {
                return obj.getTransHoOrgId();
            }

            public void put(MmMtlRcptTrVORowImpl obj, Object value) {
                obj.setTransHoOrgId((String)value);
            }
        }
        ,
        MmMtlRcptTrLines {
            public Object get(MmMtlRcptTrVORowImpl obj) {
                return obj.getMmMtlRcptTrLines();
            }

            public void put(MmMtlRcptTrVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        MmMtlRcptTrLines1 {
            public Object get(MmMtlRcptTrVORowImpl obj) {
                return obj.getMmMtlRcptTrLines1();
            }

            public void put(MmMtlRcptTrVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovTaxRuleIdVO {
            public Object get(MmMtlRcptTrVORowImpl obj) {
                return obj.getLovTaxRuleIdVO();
            }

            public void put(MmMtlRcptTrVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(MmMtlRcptTrVORowImpl object);

        public abstract void put(MmMtlRcptTrVORowImpl object, Object value);

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
    public static final int WHID = AttributesEnum.WhId.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int DOCIDSRC = AttributesEnum.DocIdSrc.index();
    public static final int DLVSCHDLNO = AttributesEnum.DlvSchdlNo.index();
    public static final int ITMID = AttributesEnum.ItmId.index();
    public static final int ITMUOM = AttributesEnum.ItmUom.index();
    public static final int TAXRULEID = AttributesEnum.TaxRuleId.index();
    public static final int TAXRULEFLG = AttributesEnum.TaxRuleFlg.index();
    public static final int TAXEXMPTFLG = AttributesEnum.TaxExmptFlg.index();
    public static final int TAXAMT = AttributesEnum.TaxAmt.index();
    public static final int TAXAMTBS = AttributesEnum.TaxAmtBs.index();
    public static final int TAXABLEAMTSP = AttributesEnum.TaxableAmtSp.index();
    public static final int TAXABLEAMTBS = AttributesEnum.TaxableAmtBs.index();
    public static final int TRANSTAXRULEID = AttributesEnum.TransTaxRuleId.index();
    public static final int TRANSHOORGID = AttributesEnum.TransHoOrgId.index();
    public static final int MMMTLRCPTTRLINES = AttributesEnum.MmMtlRcptTrLines.index();
    public static final int MMMTLRCPTTRLINES1 = AttributesEnum.MmMtlRcptTrLines1.index();
    public static final int LOVTAXRULEIDVO = AttributesEnum.LovTaxRuleIdVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MmMtlRcptTrVORowImpl() {
    }

    /**
     * Gets MmMtlRcptTrEO entity object.
     * @return the MmMtlRcptTrEO
     */
    public EntityImpl getMmMtlRcptTrEO() {
        return (EntityImpl)getEntity(ENTITY_MMMTLRCPTTREO);
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
     * Gets the attribute value for WH_ID using the alias name WhId.
     * @return the WH_ID
     */
    public String getWhId() {
        return (String) getAttributeInternal(WHID);
    }

    /**
     * Sets <code>value</code> as attribute value for WH_ID using the alias name WhId.
     * @param value value to set the WH_ID
     */
    public void setWhId(String value) {
        setAttributeInternal(WHID, value);
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
     * Gets the attribute value for DLV_SCHDL_NO using the alias name DlvSchdlNo.
     * @return the DLV_SCHDL_NO
     */
    public Integer getDlvSchdlNo() {
        return (Integer) getAttributeInternal(DLVSCHDLNO);
    }

    /**
     * Sets <code>value</code> as attribute value for DLV_SCHDL_NO using the alias name DlvSchdlNo.
     * @param value value to set the DLV_SCHDL_NO
     */
    public void setDlvSchdlNo(Integer value) {
        setAttributeInternal(DLVSCHDLNO, value);
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
     * Gets the attribute value for TAX_RULE_ID using the alias name TaxRuleId.
     * @return the TAX_RULE_ID
     */
    public Integer getTaxRuleId() {
        return (Integer)getAttributeInternal(TAXRULEID);
    }

    /**
     * Sets <code>value</code> as attribute value for TAX_RULE_ID using the alias name TaxRuleId.
     * @param value value to set the TAX_RULE_ID
     */
    public void setTaxRuleId(Integer value) {
        setAttributeInternal(TAXRULEID, value);
    }

    /**
     * Gets the attribute value for TAX_RULE_FLG using the alias name TaxRuleFlg.
     * @return the TAX_RULE_FLG
     */
    public String getTaxRuleFlg() {
        return (String) getAttributeInternal(TAXRULEFLG);
    }

    /**
     * Sets <code>value</code> as attribute value for TAX_RULE_FLG using the alias name TaxRuleFlg.
     * @param value value to set the TAX_RULE_FLG
     */
    public void setTaxRuleFlg(String value) {
        setAttributeInternal(TAXRULEFLG, value);
    }

    /**
     * Gets the attribute value for TAX_EXMPT_FLG using the alias name TaxExmptFlg.
     * @return the TAX_EXMPT_FLG
     */
    public String getTaxExmptFlg() {
        return (String) getAttributeInternal(TAXEXMPTFLG);
    }

    /**
     * Sets <code>value</code> as attribute value for TAX_EXMPT_FLG using the alias name TaxExmptFlg.
     * @param value value to set the TAX_EXMPT_FLG
     */
    public void setTaxExmptFlg(String value) {
        setAttributeInternal(TAXEXMPTFLG, value);
    }

    /**
     * Gets the attribute value for TAX_AMT using the alias name TaxAmt.
     * @return the TAX_AMT
     */
    public oracle.jbo.domain.Number getTaxAmt() {
        return (oracle.jbo.domain.Number)getAttributeInternal(TAXAMT);
    }

    /**
     * Sets <code>value</code> as attribute value for TAX_AMT using the alias name TaxAmt.
     * @param value value to set the TAX_AMT
     */
    public void setTaxAmt(oracle.jbo.domain.Number value) {
        setAttributeInternal(TAXAMT, value);
    }

    /**
     * Gets the attribute value for TAX_AMT_BS using the alias name TaxAmtBs.
     * @return the TAX_AMT_BS
     */
    public oracle.jbo.domain.Number getTaxAmtBs() {
        return (oracle.jbo.domain.Number)getAttributeInternal(TAXAMTBS);
    }

    /**
     * Sets <code>value</code> as attribute value for TAX_AMT_BS using the alias name TaxAmtBs.
     * @param value value to set the TAX_AMT_BS
     */
    public void setTaxAmtBs(oracle.jbo.domain.Number value) {
        setAttributeInternal(TAXAMTBS, value);
    }

    /**
     * Gets the attribute value for TAXABLE_AMT_SP using the alias name TaxableAmtSp.
     * @return the TAXABLE_AMT_SP
     */
    public oracle.jbo.domain.Number getTaxableAmtSp() {
        return (oracle.jbo.domain.Number)getAttributeInternal(TAXABLEAMTSP);
    }

    /**
     * Sets <code>value</code> as attribute value for TAXABLE_AMT_SP using the alias name TaxableAmtSp.
     * @param value value to set the TAXABLE_AMT_SP
     */
    public void setTaxableAmtSp(oracle.jbo.domain.Number value) {
        setAttributeInternal(TAXABLEAMTSP, value);
    }

    /**
     * Gets the attribute value for TAXABLE_AMT_BS using the alias name TaxableAmtBs.
     * @return the TAXABLE_AMT_BS
     */
    public oracle.jbo.domain.Number getTaxableAmtBs() {
        return (oracle.jbo.domain.Number)getAttributeInternal(TAXABLEAMTBS);
    }

    /**
     * Sets <code>value</code> as attribute value for TAXABLE_AMT_BS using the alias name TaxableAmtBs.
     * @param value value to set the TAXABLE_AMT_BS
     */
    public void setTaxableAmtBs(oracle.jbo.domain.Number value) {
        setAttributeInternal(TAXABLEAMTBS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransTaxRuleId.
     * @return the TransTaxRuleId
     */
    public String getTransTaxRuleId() {
        MMReceiptMtlAMImpl am = (MMReceiptMtlAMImpl)this.getApplicationModule();
              if(getTaxRuleId()!=null){
                  am.getLovTaxRuleIdForDispVO().setNamedWhereClauseParam("BindSlocId", getSlocId());
                  am.getLovTaxRuleIdForDispVO().setNamedWhereClauseParam("BindCldId", getCldId());
                   am.getLovTaxRuleIdForDispVO().setNamedWhereClauseParam("BindHoOrgId",getTransHoOrgId() );
                  am.getLovTaxRuleIdForDispVO().setNamedWhereClauseParam("BindTaxRuleId", getTaxRuleId());
                  am.getLovTaxRuleIdForDispVO().executeQuery();
                 Row[] xx=am.getLovTaxRuleIdForDispVO().getFilteredRows("TaxRuleId", getTaxRuleId());
              // Row [] xx=am.getLovInputItmId1().getFilteredRowsInRange("ItmId", inputitm);
                 if(xx.length>0){
                    return  xx[0].getAttribute("TaxRuleDesc").toString();
                 }
                  return (String) getAttributeInternal(TRANSTAXRULEID);
              }else{
                return (String) getAttributeInternal(TRANSTAXRULEID);
              }
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransTaxRuleId.
     * @param value value to set the  TransTaxRuleId
     */
    public void setTransTaxRuleId(String value) {
        setAttributeInternal(TRANSTAXRULEID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransHoOrgId.
     * @return the TransHoOrgId
     */
    public String getTransHoOrgId() {
        return resolveEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
      //  return (String) getAttributeInternal(TRANSHOORGID);
    }
    public String resolveEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }
    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransHoOrgId.
     * @param value value to set the  TransHoOrgId
     */
    public void setTransHoOrgId(String value) {
        setAttributeInternal(TRANSHOORGID, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link MmMtlRcptTrLines.
     */
    public RowIterator getMmMtlRcptTrLines() {
        return (RowIterator)getAttributeInternal(MMMTLRCPTTRLINES);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link MmMtlRcptTrLines1.
     */
    public RowIterator getMmMtlRcptTrLines1() {
        return (RowIterator)getAttributeInternal(MMMTLRCPTTRLINES1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovTaxRuleIdVO.
     */
    public RowSet getLovTaxRuleIdVO() {
        return (RowSet)getAttributeInternal(LOVTAXRULEIDVO);
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
}
