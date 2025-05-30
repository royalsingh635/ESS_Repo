package tempVoucherList.model.views;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.RowSet;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;

import tempVoucherList.model.entities.TvouBillDtlEOImpl;


// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Apr 26 14:47:43 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class TvouBillDtlVORowImpl extends ViewRowImpl {


    public static final int ENTITY_TVOUBILLDTLEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        TvouHoOrgId {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouHoOrgId();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouHoOrgId((String) value);
            }
        }
        ,
        TvouOrgId {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouOrgId();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouOrgId((String) value);
            }
        }
        ,
        TvouCldId {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouCldId();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouCldId((String) value);
            }
        }
        ,
        TvouSlocId {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouSlocId();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouSlocId((Integer) value);
            }
        }
        ,
        TvouId {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouId();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouId((String) value);
            }
        }
        ,
        TvouSlNo {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouSlNo();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouSlNo((Integer) value);
            }
        }
        ,
        TvouApplInstId {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouApplInstId();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouApplInstId((Integer) value);
            }
        }
        ,
        TvouDt {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouDt();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouDt((Date) value);
            }
        }
        ,
        TvouAmtSp {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouAmtSp();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouAmtSp((Number) value);
            }
        }
        ,
        TvouAmtTyp {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouAmtTyp();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouAmtTyp((String) value);
            }
        }
        ,
        TvouCurrIdSp {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouCurrIdSp();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouCurrIdSp((Integer) value);
            }
        }
        ,
        TvouCc {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouCc();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouCc((Number) value);
            }
        }
        ,
        TvouCurrIdBs {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouCurrIdBs();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouCurrIdBs((Integer) value);
            }
        }
        ,
        TvouAmtBs {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouAmtBs();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouAmtBs((Number) value);
            }
        }
        ,
        TvouBillNo {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouBillNo();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouBillNo((String) value);
            }
        }
        ,
        TvouBillDt {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouBillDt();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouBillDt((Date) value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UsrIdCreateDt {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getUsrIdCreateDt();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UsrIdMod {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getUsrIdMod();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UsrIdModDt {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getUsrIdModDt();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        TvouCoaId {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouCoaId();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouCoaId((Integer) value);
            }
        }
        ,
        TvouSlNoDest {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouSlNoDest();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouSlNoDest((Number) value);
            }
        }
        ,
        TvouTdsFlg {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouTdsFlg();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouTdsFlg((String) value);
            }
        }
        ,
        TvouTaxFlg {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouTaxFlg();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouTaxFlg((String) value);
            }
        }
        ,
        TvouDueDt {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouDueDt();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouDueDt((Date) value);
            }
        }
        ,
        TvouBillSlNo {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouBillSlNo();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouBillSlNo((Integer) value);
            }
        }
        ,
        RowKeyTrans {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getRowKeyTrans();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setRowKeyTrans((Object) value);
            }
        }
        ,
        TvouPrjId {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getTvouPrjId();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setTvouPrjId((String) value);
            }
        }
        ,
        LovLatestCurr1 {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getLovLatestCurr1();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovVouCoaVO1 {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getLovVouCoaVO1();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        AmtTypLOV1 {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getAmtTypLOV1();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovCurrencyVO1 {
            public Object get(TvouBillDtlVORowImpl obj) {
                return obj.getLovCurrencyVO1();
            }

            public void put(TvouBillDtlVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        static AttributesEnum[] vals = null;
        ;
        private static int firstIndex = 0;

        public abstract Object get(TvouBillDtlVORowImpl object);

        public abstract void put(TvouBillDtlVORowImpl object, Object value);

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


    public Integer amt_digit = Integer.parseInt(resolvElAmt("#{pageFlowScope.GLBL_AMT_DIGIT}").toString());
    public Integer cur_digit = Integer.parseInt(resolvElAmt("#{pageFlowScope.GLBL_CURR_DIGIT}").toString());
    //public Integer amt_digit =6;


    public static final int TVOUHOORGID = AttributesEnum.TvouHoOrgId.index();
    public static final int TVOUORGID = AttributesEnum.TvouOrgId.index();
    public static final int TVOUCLDID = AttributesEnum.TvouCldId.index();
    public static final int TVOUSLOCID = AttributesEnum.TvouSlocId.index();
    public static final int TVOUID = AttributesEnum.TvouId.index();
    public static final int TVOUSLNO = AttributesEnum.TvouSlNo.index();
    public static final int TVOUAPPLINSTID = AttributesEnum.TvouApplInstId.index();
    public static final int TVOUDT = AttributesEnum.TvouDt.index();
    public static final int TVOUAMTSP = AttributesEnum.TvouAmtSp.index();
    public static final int TVOUAMTTYP = AttributesEnum.TvouAmtTyp.index();
    public static final int TVOUCURRIDSP = AttributesEnum.TvouCurrIdSp.index();
    public static final int TVOUCC = AttributesEnum.TvouCc.index();
    public static final int TVOUCURRIDBS = AttributesEnum.TvouCurrIdBs.index();
    public static final int TVOUAMTBS = AttributesEnum.TvouAmtBs.index();
    public static final int TVOUBILLNO = AttributesEnum.TvouBillNo.index();
    public static final int TVOUBILLDT = AttributesEnum.TvouBillDt.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int TVOUCOAID = AttributesEnum.TvouCoaId.index();
    public static final int TVOUSLNODEST = AttributesEnum.TvouSlNoDest.index();
    public static final int TVOUTDSFLG = AttributesEnum.TvouTdsFlg.index();
    public static final int TVOUTAXFLG = AttributesEnum.TvouTaxFlg.index();
    public static final int TVOUDUEDT = AttributesEnum.TvouDueDt.index();
    public static final int TVOUBILLSLNO = AttributesEnum.TvouBillSlNo.index();
    public static final int ROWKEYTRANS = AttributesEnum.RowKeyTrans.index();
    public static final int TVOUPRJID = AttributesEnum.TvouPrjId.index();
    public static final int LOVLATESTCURR1 = AttributesEnum.LovLatestCurr1.index();
    public static final int LOVVOUCOAVO1 = AttributesEnum.LovVouCoaVO1.index();
    public static final int AMTTYPLOV1 = AttributesEnum.AmtTypLOV1.index();
    public static final int LOVCURRENCYVO1 = AttributesEnum.LovCurrencyVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public TvouBillDtlVORowImpl() {
    }

    public Object resolvElAmt(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object digit = 2;
        if (valueExp != null) {
            digit = valueExp.getValue(elContext);
        }
        if (digit == null) {
            digit = 2;
        }
        //  System.out.println("Amount digit is--->" + digit);
        return digit;
    }

    /**
     * Gets TvouBillDtlEO entity object.
     * @return the TvouBillDtlEO
     */
    public TvouBillDtlEOImpl getTvouBillDtlEO() {
        return (TvouBillDtlEOImpl)getEntity(ENTITY_TVOUBILLDTLEO);
    }

    /**
     * Gets the attribute value for TVOU_HO_ORG_ID using the alias name TvouHoOrgId.
     * @return the TVOU_HO_ORG_ID
     */
    public String getTvouHoOrgId() {
        return (String)getAttributeInternal(TVOUHOORGID);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_HO_ORG_ID using the alias name TvouHoOrgId.
     * @param value value to set the TVOU_HO_ORG_ID
     */
    public void setTvouHoOrgId(String value) {
        setAttributeInternal(TVOUHOORGID, value);
    }

    /**
     * Gets the attribute value for TVOU_ORG_ID using the alias name TvouOrgId.
     * @return the TVOU_ORG_ID
     */
    public String getTvouOrgId() {
        return (String)getAttributeInternal(TVOUORGID);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_ORG_ID using the alias name TvouOrgId.
     * @param value value to set the TVOU_ORG_ID
     */
    public void setTvouOrgId(String value) {
        setAttributeInternal(TVOUORGID, value);
    }

    /**
     * Gets the attribute value for TVOU_CLD_ID using the alias name TvouCldId.
     * @return the TVOU_CLD_ID
     */
    public String getTvouCldId() {
        return (String)getAttributeInternal(TVOUCLDID);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_CLD_ID using the alias name TvouCldId.
     * @param value value to set the TVOU_CLD_ID
     */
    public void setTvouCldId(String value) {
        setAttributeInternal(TVOUCLDID, value);
    }

    /**
     * Gets the attribute value for TVOU_SLOC_ID using the alias name TvouSlocId.
     * @return the TVOU_SLOC_ID
     */
    public Integer getTvouSlocId() {
        return (Integer)getAttributeInternal(TVOUSLOCID);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_SLOC_ID using the alias name TvouSlocId.
     * @param value value to set the TVOU_SLOC_ID
     */
    public void setTvouSlocId(Integer value) {
        setAttributeInternal(TVOUSLOCID, value);
    }

    /**
     * Gets the attribute value for TVOU_ID using the alias name TvouId.
     * @return the TVOU_ID
     */
    public String getTvouId() {
        return (String)getAttributeInternal(TVOUID);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_ID using the alias name TvouId.
     * @param value value to set the TVOU_ID
     */
    public void setTvouId(String value) {
        setAttributeInternal(TVOUID, value);
    }

    /**
     * Gets the attribute value for TVOU_SL_NO using the alias name TvouSlNo.
     * @return the TVOU_SL_NO
     */
    public Integer getTvouSlNo() {
        return (Integer)getAttributeInternal(TVOUSLNO);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_SL_NO using the alias name TvouSlNo.
     * @param value value to set the TVOU_SL_NO
     */
    public void setTvouSlNo(Integer value) {
        setAttributeInternal(TVOUSLNO, value);
    }

    /**
     * Gets the attribute value for TVOU_APPL_INST_ID using the alias name TvouApplInstId.
     * @return the TVOU_APPL_INST_ID
     */
    public Integer getTvouApplInstId() {
        return (Integer)getAttributeInternal(TVOUAPPLINSTID);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_APPL_INST_ID using the alias name TvouApplInstId.
     * @param value value to set the TVOU_APPL_INST_ID
     */
    public void setTvouApplInstId(Integer value) {
        setAttributeInternal(TVOUAPPLINSTID, value);
    }

    /**
     * Gets the attribute value for TVOU_DT using the alias name TvouDt.
     * @return the TVOU_DT
     */
    public Date getTvouDt() {
        return (Date)getAttributeInternal(TVOUDT);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_DT using the alias name TvouDt.
     * @param value value to set the TVOU_DT
     */
    public void setTvouDt(Date value) {
        setAttributeInternal(TVOUDT, value);
    }

    /**
     * Gets the attribute value for TVOU_AMT_SP using the alias name TvouAmtSp.
     * @return the TVOU_AMT_SP
     */
    public Number getTvouAmtSp() {
        return (Number)getAttributeInternal(TVOUAMTSP);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_AMT_SP using the alias name TvouAmtSp.
     * @param value value to set the TVOU_AMT_SP
     */
    public void setTvouAmtSp(Number value) {
        if (value == null) {
            value = new Number(0);
        } else {
            value = (Number)value.round(amt_digit);
        }
        setAttributeInternal(TVOUAMTSP, value);
    }

    /**
     * Gets the attribute value for TVOU_AMT_TYP using the alias name TvouAmtTyp.
     * @return the TVOU_AMT_TYP
     */
    public String getTvouAmtTyp() {
        return (String)getAttributeInternal(TVOUAMTTYP);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_AMT_TYP using the alias name TvouAmtTyp.
     * @param value value to set the TVOU_AMT_TYP
     */
    public void setTvouAmtTyp(String value) {
        setAttributeInternal(TVOUAMTTYP, value);
    }

    /**
     * Gets the attribute value for TVOU_CURR_ID_SP using the alias name TvouCurrIdSp.
     * @return the TVOU_CURR_ID_SP
     */
    public Integer getTvouCurrIdSp() {
        return (Integer)getAttributeInternal(TVOUCURRIDSP);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_CURR_ID_SP using the alias name TvouCurrIdSp.
     * @param value value to set the TVOU_CURR_ID_SP
     */
    public void setTvouCurrIdSp(Integer value) {
        setAttributeInternal(TVOUCURRIDSP, value);
    }

    /**
     * Gets the attribute value for TVOU_CC using the alias name TvouCc.
     * @return the TVOU_CC
     */
    public Number getTvouCc() {
        return (Number)getAttributeInternal(TVOUCC);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_CC using the alias name TvouCc.
     * @param value value to set the TVOU_CC
     */
    public void setTvouCc(Number value) {
        if (value == null) {
            value = new Number(0);
        } else {
            value = (Number)value.round(cur_digit);
        }
        setAttributeInternal(TVOUCC, value);
    }

    /**
     * Gets the attribute value for TVOU_CURR_ID_BS using the alias name TvouCurrIdBs.
     * @return the TVOU_CURR_ID_BS
     */
    public Integer getTvouCurrIdBs() {
        return (Integer)getAttributeInternal(TVOUCURRIDBS);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_CURR_ID_BS using the alias name TvouCurrIdBs.
     * @param value value to set the TVOU_CURR_ID_BS
     */
    public void setTvouCurrIdBs(Integer value) {

        setAttributeInternal(TVOUCURRIDBS, value);
    }

    /**
     * Gets the attribute value for TVOU_AMT_BS using the alias name TvouAmtBs.
     * @return the TVOU_AMT_BS
     */
    public Number getTvouAmtBs() {
        if (getTvouCc() != null) {
            return getTvouCc().multiply(getTvouAmtSp());
        } else {
            return (Number)getAttributeInternal(TVOUAMTBS);
        }
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_AMT_BS using the alias name TvouAmtBs.
     * @param value value to set the TVOU_AMT_BS
     */
    public void setTvouAmtBs(Number value) {
        if (value == null) {
            value = new Number(0);
        } else {
            value = (Number)value.round(amt_digit);
        }
        System.out.println("TvouAmtBs bill iss->" + value);
        setAttributeInternal(TVOUAMTBS, value);
    }

    /**
     * Gets the attribute value for TVOU_BILL_NO using the alias name TvouBillNo.
     * @return the TVOU_BILL_NO
     */
    public String getTvouBillNo() {
        return (String)getAttributeInternal(TVOUBILLNO);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_BILL_NO using the alias name TvouBillNo.
     * @param value value to set the TVOU_BILL_NO
     */
    public void setTvouBillNo(String value) {
        setAttributeInternal(TVOUBILLNO, value);
    }

    /**
     * Gets the attribute value for TVOU_BILL_DT using the alias name TvouBillDt.
     * @return the TVOU_BILL_DT
     */
    public Date getTvouBillDt() {
        return (Date)getAttributeInternal(TVOUBILLDT);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_BILL_DT using the alias name TvouBillDt.
     * @param value value to set the TVOU_BILL_DT
     */
    public void setTvouBillDt(Date value) {
        setAttributeInternal(TVOUBILLDT, value);
    }

    /**
     * Gets the attribute value for USR_ID_CREATE using the alias name UsrIdCreate.
     * @return the USR_ID_CREATE
     */
    public Integer getUsrIdCreate() {
        return (Integer)getAttributeInternal(USRIDCREATE);
    }


    /**
     * Gets the attribute value for USR_ID_CREATE_DT using the alias name UsrIdCreateDt.
     * @return the USR_ID_CREATE_DT
     */
    public Timestamp getUsrIdCreateDt() {
        return (Timestamp)getAttributeInternal(USRIDCREATEDT);
    }


    /**
     * Gets the attribute value for USR_ID_MOD using the alias name UsrIdMod.
     * @return the USR_ID_MOD
     */
    public Integer getUsrIdMod() {
        return (Integer)getAttributeInternal(USRIDMOD);
    }


    /**
     * Gets the attribute value for USR_ID_MOD_DT using the alias name UsrIdModDt.
     * @return the USR_ID_MOD_DT
     */
    public Timestamp getUsrIdModDt() {
        return (Timestamp)getAttributeInternal(USRIDMODDT);
    }


    /**
     * Gets the attribute value for TVOU_COA_ID using the alias name TvouCoaId.
     * @return the TVOU_COA_ID
     */
    public Integer getTvouCoaId() {
        return (Integer)getAttributeInternal(TVOUCOAID);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_COA_ID using the alias name TvouCoaId.
     * @param value value to set the TVOU_COA_ID
     */
    public void setTvouCoaId(Integer value) {
        setAttributeInternal(TVOUCOAID, value);
    }

    /**
     * Gets the attribute value for TVOU_SL_NO_DEST using the alias name TvouSlNoDest.
     * @return the TVOU_SL_NO_DEST
     */
    public Number getTvouSlNoDest() {
        return (Number)getAttributeInternal(TVOUSLNODEST);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_SL_NO_DEST using the alias name TvouSlNoDest.
     * @param value value to set the TVOU_SL_NO_DEST
     */
    public void setTvouSlNoDest(Number value) {
        setAttributeInternal(TVOUSLNODEST, value);
    }

    /**
     * Gets the attribute value for TVOU_TDS_FLG using the alias name TvouTdsFlg.
     * @return the TVOU_TDS_FLG
     */
    public String getTvouTdsFlg() {
        return (String)getAttributeInternal(TVOUTDSFLG);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_TDS_FLG using the alias name TvouTdsFlg.
     * @param value value to set the TVOU_TDS_FLG
     */
    public void setTvouTdsFlg(String value) {
        setAttributeInternal(TVOUTDSFLG, value);
    }

    /**
     * Gets the attribute value for TVOU_TAX_FLG using the alias name TvouTaxFlg.
     * @return the TVOU_TAX_FLG
     */
    public String getTvouTaxFlg() {
        return (String)getAttributeInternal(TVOUTAXFLG);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_TAX_FLG using the alias name TvouTaxFlg.
     * @param value value to set the TVOU_TAX_FLG
     */
    public void setTvouTaxFlg(String value) {
        setAttributeInternal(TVOUTAXFLG, value);
    }

    /**
     * Gets the attribute value for TVOU_DUE_DT using the alias name TvouDueDt.
     * @return the TVOU_DUE_DT
     */
    public Date getTvouDueDt() {
        return (Date)getAttributeInternal(TVOUDUEDT);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_DUE_DT using the alias name TvouDueDt.
     * @param value value to set the TVOU_DUE_DT
     */
    public void setTvouDueDt(Date value) {
        setAttributeInternal(TVOUDUEDT, value);
    }

    /**
     * Gets the attribute value for TVOU_BILL_SL_NO using the alias name TvouBillSlNo.
     * @return the TVOU_BILL_SL_NO
     */
    public Integer getTvouBillSlNo() {
        return (Integer)getAttributeInternal(TVOUBILLSLNO);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_BILL_SL_NO using the alias name TvouBillSlNo.
     * @param value value to set the TVOU_BILL_SL_NO
     */
    public void setTvouBillSlNo(Integer value) {
        setAttributeInternal(TVOUBILLSLNO, value);
    }


    /**
     * Gets the attribute value for the calculated attribute RowKeyTrans.
     * @return the RowKeyTrans
     */
    public Object getRowKeyTrans() {
        
        return getKey();
        //return (Object) getAttributeInternal(ROWKEYTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute RowKeyTrans.
     * @param value value to set the  RowKeyTrans
     */
    public void setRowKeyTrans(Object value) {
        setAttributeInternal(ROWKEYTRANS, value);
    }

    /**
     * Gets the attribute value for TVOU_PRJ_ID using the alias name TvouPrjId.
     * @return the TVOU_PRJ_ID
     */
    public String getTvouPrjId() {
        return (String) getAttributeInternal(TVOUPRJID);
    }

    /**
     * Sets <code>value</code> as attribute value for TVOU_PRJ_ID using the alias name TvouPrjId.
     * @param value value to set the TVOU_PRJ_ID
     */
    public void setTvouPrjId(String value) {
        setAttributeInternal(TVOUPRJID, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovLatestCurr1.
     */
    public RowSet getLovLatestCurr1() {
        return (RowSet)getAttributeInternal(LOVLATESTCURR1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovVouCoaVO1.
     */
    public RowSet getLovVouCoaVO1() {
        return (RowSet)getAttributeInternal(LOVVOUCOAVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> AmtTypLOV1.
     */
    public RowSet getAmtTypLOV1() {
        return (RowSet)getAttributeInternal(AMTTYPLOV1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovCurrencyVO1.
     */
    public RowSet getLovCurrencyVO1() {
        return (RowSet) getAttributeInternal(LOVCURRENCYVO1);
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
