package glApp.model.view;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.RowSet;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.NClobDomain;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.RowID;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Jul 04 21:03:18 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class GlAdjTmpDtl1VORowImpl extends ViewRowImpl {


    public static final int ENTITY_GLADJTMPDTLEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        AdjAmtAdj {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getAdjAmtAdj();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setAdjAmtAdj((Number)value);
            }
        }
        ,
        AdjAmtSp {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getAdjAmtSp();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setAdjAmtSp((Number)value);
            }
        }
        ,
        AdjApplInstId {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getAdjApplInstId();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setAdjApplInstId((Integer)value);
            }
        }
        ,
        AdjArapSlNo {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getAdjArapSlNo();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setAdjArapSlNo((Integer)value);
            }
        }
        ,
        AdjCoaId {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getAdjCoaId();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setAdjCoaId((Integer)value);
            }
        }
        ,
        AdjCurrIdSp {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getAdjCurrIdSp();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setAdjCurrIdSp((Integer)value);
            }
        }
        ,
        AdjMstSlNo {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getAdjMstSlNo();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setAdjMstSlNo((Integer)value);
            }
        }
        ,
        AdjMstVouDt {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getAdjMstVouDt();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setAdjMstVouDt((Date)value);
            }
        }
        ,
        AdjMstVouId {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getAdjMstVouId();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setAdjMstVouId((String)value);
            }
        }
        ,
        AdjNode {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getAdjNode();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setAdjNode((String)value);
            }
        }
        ,
        AdjOrgId {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getAdjOrgId();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setAdjOrgId((String)value);
            }
        }
        ,
        AdjSlNo {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getAdjSlNo();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setAdjSlNo((Integer)value);
            }
        }
        ,
        AdjSlocId {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getAdjSlocId();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setAdjSlocId((Integer)value);
            }
        }
        ,
        AdjTab {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getAdjTab();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setAdjTab((String)value);
            }
        }
        ,
        AdjTxnFlg {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getAdjTxnFlg();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setAdjTxnFlg((String)value);
            }
        }
        ,
        AdjVouDt {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getAdjVouDt();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setAdjVouDt((Date)value);
            }
        }
        ,
        AdjVouId {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getAdjVouId();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setAdjVouId((String)value);
            }
        }
        ,
        GlAdjAmtAdjThs {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getGlAdjAmtAdjThs();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setGlAdjAmtAdjThs((Integer)value);
            }
        }
        ,
        GlAdjAmtAdjTot {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getGlAdjAmtAdjTot();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setGlAdjAmtAdjTot((Integer)value);
            }
        }
        ,
        GlAdjAmtOT {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getGlAdjAmtOT();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setGlAdjAmtOT((Number)value);
            }
        }
        ,
        GlRefDt {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getGlRefDt();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setGlRefDt((Date)value);
            }
        }
        ,
        GlRefId {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getGlRefId();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setGlRefId((String)value);
            }
        }
        ,
        GlRemainOT {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getGlRemainOT();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setGlRemainOT((Number)value);
            }
        }
        ,
        GlTxnTyp {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getGlTxnTyp();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setGlTxnTyp((String)value);
            }
        }
        ,
        RadjAmtAdj {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getRadjAmtAdj();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setRadjAmtAdj((Number)value);
            }
        }
        ,
        RadjAmtSp {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getRadjAmtSp();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setRadjAmtSp((Number)value);
            }
        }
        ,
        RadjArapSlNo {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getRadjArapSlNo();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setRadjArapSlNo((Integer)value);
            }
        }
        ,
        RadjSlNo {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getRadjSlNo();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setRadjSlNo((Integer)value);
            }
        }
        ,
        RadjTab {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getRadjTab();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setRadjTab((String)value);
            }
        }
        ,
        RadjVouDt {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getRadjVouDt();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setRadjVouDt((Date)value);
            }
        }
        ,
        RadjVouId {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getRadjVouId();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setRadjVouId((String)value);
            }
        }
        ,
        Rowid {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getRowid();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setRowid((RowID)value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setUsrIdCreate((Integer)value);
            }
        }
        ,
        UsrIdCreateDt {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getUsrIdCreateDt();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setUsrIdCreateDt((Date)value);
            }
        }
        ,
        UsrIdMod {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getUsrIdMod();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setUsrIdMod((Integer)value);
            }
        }
        ,
        UsrIdModDt {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getUsrIdModDt();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setUsrIdModDt((Date)value);
            }
        }
        ,
        GlCldId {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getGlCldId();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setGlCldId((String)value);
            }
        }
        ,
        GlHoOrgId {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getGlHoOrgId();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setGlHoOrgId((String)value);
            }
        }
        ,
        LovVouIdVO1 {
            public Object get(GlAdjTmpDtl1VORowImpl obj) {
                return obj.getLovVouIdVO1();
            }

            public void put(GlAdjTmpDtl1VORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(GlAdjTmpDtl1VORowImpl object);

        public abstract void put(GlAdjTmpDtl1VORowImpl object, Object value);

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


    public static final int ADJAMTADJ = AttributesEnum.AdjAmtAdj.index();
    public static final int ADJAMTSP = AttributesEnum.AdjAmtSp.index();
    public static final int ADJAPPLINSTID = AttributesEnum.AdjApplInstId.index();
    public static final int ADJARAPSLNO = AttributesEnum.AdjArapSlNo.index();
    public static final int ADJCOAID = AttributesEnum.AdjCoaId.index();
    public static final int ADJCURRIDSP = AttributesEnum.AdjCurrIdSp.index();
    public static final int ADJMSTSLNO = AttributesEnum.AdjMstSlNo.index();
    public static final int ADJMSTVOUDT = AttributesEnum.AdjMstVouDt.index();
    public static final int ADJMSTVOUID = AttributesEnum.AdjMstVouId.index();
    public static final int ADJNODE = AttributesEnum.AdjNode.index();
    public static final int ADJORGID = AttributesEnum.AdjOrgId.index();
    public static final int ADJSLNO = AttributesEnum.AdjSlNo.index();
    public static final int ADJSLOCID = AttributesEnum.AdjSlocId.index();
    public static final int ADJTAB = AttributesEnum.AdjTab.index();
    public static final int ADJTXNFLG = AttributesEnum.AdjTxnFlg.index();
    public static final int ADJVOUDT = AttributesEnum.AdjVouDt.index();
    public static final int ADJVOUID = AttributesEnum.AdjVouId.index();
    public static final int GLADJAMTADJTHS = AttributesEnum.GlAdjAmtAdjThs.index();
    public static final int GLADJAMTADJTOT = AttributesEnum.GlAdjAmtAdjTot.index();
    public static final int GLADJAMTOT = AttributesEnum.GlAdjAmtOT.index();
    public static final int GLREFDT = AttributesEnum.GlRefDt.index();
    public static final int GLREFID = AttributesEnum.GlRefId.index();
    public static final int GLREMAINOT = AttributesEnum.GlRemainOT.index();
    public static final int GLTXNTYP = AttributesEnum.GlTxnTyp.index();
    public static final int RADJAMTADJ = AttributesEnum.RadjAmtAdj.index();
    public static final int RADJAMTSP = AttributesEnum.RadjAmtSp.index();
    public static final int RADJARAPSLNO = AttributesEnum.RadjArapSlNo.index();
    public static final int RADJSLNO = AttributesEnum.RadjSlNo.index();
    public static final int RADJTAB = AttributesEnum.RadjTab.index();
    public static final int RADJVOUDT = AttributesEnum.RadjVouDt.index();
    public static final int RADJVOUID = AttributesEnum.RadjVouId.index();
    public static final int ROWID = AttributesEnum.Rowid.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int GLCLDID = AttributesEnum.GlCldId.index();
    public static final int GLHOORGID = AttributesEnum.GlHoOrgId.index();
    public static final int LOVVOUIDVO1 = AttributesEnum.LovVouIdVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public GlAdjTmpDtl1VORowImpl() {
    }

    /**
     * Gets GlAdjTmpDtlEO entity object.
     * @return the GlAdjTmpDtlEO
     */
    public EntityImpl getGlAdjTmpDtlEO() {
        return (EntityImpl)getEntity(ENTITY_GLADJTMPDTLEO);
    }

    /**
     * Gets the attribute value for ADJ_AMT_ADJ using the alias name AdjAmtAdjTrans.
     * @return the ADJ_AMT_ADJ
     */
    public Number getAdjAmtAdj() {
        return (Number) getAttributeInternal(ADJAMTADJ);
    }

    /**
     * Sets <code>value</code> as attribute value for ADJ_AMT_ADJ using the alias name AdjAmtAdjTrans.
     * @param value value to set the ADJ_AMT_ADJ
     */
    public void setAdjAmtAdj(Number value) {
        setAttributeInternal(ADJAMTADJ, value);
    }

    /**
     * Gets the attribute value for ADJ_AMT_SP using the alias name AdjAmtSp.
     * @return the ADJ_AMT_SP
     */
    public Number getAdjAmtSp() {
        return (Number) getAttributeInternal(ADJAMTSP);
    }

    /**
     * Sets <code>value</code> as attribute value for ADJ_AMT_SP using the alias name AdjAmtSp.
     * @param value value to set the ADJ_AMT_SP
     */
    public void setAdjAmtSp(Number value) {
        setAttributeInternal(ADJAMTSP, value);
    }

    /**
     * Gets the attribute value for ADJ_APPL_INST_ID using the alias name AdjApplInstId.
     * @return the ADJ_APPL_INST_ID
     */
    public Integer getAdjApplInstId() {
        return (Integer) getAttributeInternal(ADJAPPLINSTID);
    }

    /**
     * Sets <code>value</code> as attribute value for ADJ_APPL_INST_ID using the alias name AdjApplInstId.
     * @param value value to set the ADJ_APPL_INST_ID
     */
    public void setAdjApplInstId(Integer value) {
        setAttributeInternal(ADJAPPLINSTID, value);
    }

    /**
     * Gets the attribute value for ADJ_ARAP_SL_NO using the alias name AdjArapSlNo.
     * @return the ADJ_ARAP_SL_NO
     */
    public Integer getAdjArapSlNo() {
        return (Integer) getAttributeInternal(ADJARAPSLNO);
    }

    /**
     * Sets <code>value</code> as attribute value for ADJ_ARAP_SL_NO using the alias name AdjArapSlNo.
     * @param value value to set the ADJ_ARAP_SL_NO
     */
    public void setAdjArapSlNo(Integer value) {
        setAttributeInternal(ADJARAPSLNO, value);
    }

    /**
     * Gets the attribute value for ADJ_COA_ID using the alias name AdjCoaId.
     * @return the ADJ_COA_ID
     */
    public Integer getAdjCoaId() {
        return (Integer) getAttributeInternal(ADJCOAID);
    }

    /**
     * Sets <code>value</code> as attribute value for ADJ_COA_ID using the alias name AdjCoaId.
     * @param value value to set the ADJ_COA_ID
     */
    public void setAdjCoaId(Integer value) {
        setAttributeInternal(ADJCOAID, value);
    }

    /**
     * Gets the attribute value for ADJ_CURR_ID_SP using the alias name AdjCurrIdSp.
     * @return the ADJ_CURR_ID_SP
     */
    public Integer getAdjCurrIdSp() {
        return (Integer) getAttributeInternal(ADJCURRIDSP);
    }

    /**
     * Sets <code>value</code> as attribute value for ADJ_CURR_ID_SP using the alias name AdjCurrIdSp.
     * @param value value to set the ADJ_CURR_ID_SP
     */
    public void setAdjCurrIdSp(Integer value) {
        setAttributeInternal(ADJCURRIDSP, value);
    }

    /**
     * Gets the attribute value for ADJ_MST_SL_NO using the alias name AdjMstSlNo.
     * @return the ADJ_MST_SL_NO
     */
    public Integer getAdjMstSlNo() {
        return (Integer) getAttributeInternal(ADJMSTSLNO);
    }

    /**
     * Sets <code>value</code> as attribute value for ADJ_MST_SL_NO using the alias name AdjMstSlNo.
     * @param value value to set the ADJ_MST_SL_NO
     */
    public void setAdjMstSlNo(Integer value) {
        setAttributeInternal(ADJMSTSLNO, value);
    }

    /**
     * Gets the attribute value for ADJ_MST_VOU_DT using the alias name AdjMstVouDt.
     * @return the ADJ_MST_VOU_DT
     */
    public Date getAdjMstVouDt() {
        return (Date) getAttributeInternal(ADJMSTVOUDT);
    }

    /**
     * Sets <code>value</code> as attribute value for ADJ_MST_VOU_DT using the alias name AdjMstVouDt.
     * @param value value to set the ADJ_MST_VOU_DT
     */
    public void setAdjMstVouDt(Date value) {
        setAttributeInternal(ADJMSTVOUDT, value);
    }

    /**
     * Gets the attribute value for ADJ_MST_VOU_ID using the alias name AdjMstVouId.
     * @return the ADJ_MST_VOU_ID
     */
    public String getAdjMstVouId() {
        return (String) getAttributeInternal(ADJMSTVOUID);
    }

    /**
     * Sets <code>value</code> as attribute value for ADJ_MST_VOU_ID using the alias name AdjMstVouId.
     * @param value value to set the ADJ_MST_VOU_ID
     */
    public void setAdjMstVouId(String value) {
        setAttributeInternal(ADJMSTVOUID, value);
    }

    /**
     * Gets the attribute value for ADJ_NODE using the alias name AdjNode.
     * @return the ADJ_NODE
     */
    public String getAdjNode() {
        return (String) getAttributeInternal(ADJNODE);
    }

    /**
     * Sets <code>value</code> as attribute value for ADJ_NODE using the alias name AdjNode.
     * @param value value to set the ADJ_NODE
     */
    public void setAdjNode(String value) {
        setAttributeInternal(ADJNODE, value);
    }

    /**
     * Gets the attribute value for ADJ_ORG_ID using the alias name AdjOrgId.
     * @return the ADJ_ORG_ID
     */
    public String getAdjOrgId() {
        return (String) getAttributeInternal(ADJORGID);
    }

    /**
     * Sets <code>value</code> as attribute value for ADJ_ORG_ID using the alias name AdjOrgId.
     * @param value value to set the ADJ_ORG_ID
     */
    public void setAdjOrgId(String value) {
        setAttributeInternal(ADJORGID, value);
    }

    /**
     * Gets the attribute value for ADJ_SL_NO using the alias name AdjSlNo.
     * @return the ADJ_SL_NO
     */
    public Integer getAdjSlNo() {
        return (Integer) getAttributeInternal(ADJSLNO);
    }

    /**
     * Sets <code>value</code> as attribute value for ADJ_SL_NO using the alias name AdjSlNo.
     * @param value value to set the ADJ_SL_NO
     */
    public void setAdjSlNo(Integer value) {
        setAttributeInternal(ADJSLNO, value);
    }

    /**
     * Gets the attribute value for ADJ_SLOC_ID using the alias name AdjSlocId.
     * @return the ADJ_SLOC_ID
     */
    public Integer getAdjSlocId() {
        return (Integer) getAttributeInternal(ADJSLOCID);
    }

    /**
     * Sets <code>value</code> as attribute value for ADJ_SLOC_ID using the alias name AdjSlocId.
     * @param value value to set the ADJ_SLOC_ID
     */
    public void setAdjSlocId(Integer value) {
        setAttributeInternal(ADJSLOCID, value);
    }

    /**
     * Gets the attribute value for ADJ_TAB using the alias name AdjTab.
     * @return the ADJ_TAB
     */
    public String getAdjTab() {
        return (String) getAttributeInternal(ADJTAB);
    }

    /**
     * Sets <code>value</code> as attribute value for ADJ_TAB using the alias name AdjTab.
     * @param value value to set the ADJ_TAB
     */
    public void setAdjTab(String value) {
        setAttributeInternal(ADJTAB, value);
    }

    /**
     * Gets the attribute value for ADJ_TXN_FLG using the alias name AdjTxnFlg.
     * @return the ADJ_TXN_FLG
     */
    public String getAdjTxnFlg() {
        return (String) getAttributeInternal(ADJTXNFLG);
    }

    /**
     * Sets <code>value</code> as attribute value for ADJ_TXN_FLG using the alias name AdjTxnFlg.
     * @param value value to set the ADJ_TXN_FLG
     */
    public void setAdjTxnFlg(String value) {
        setAttributeInternal(ADJTXNFLG, value);
    }

    /**
     * Gets the attribute value for ADJ_VOU_DT using the alias name AdjVouDt.
     * @return the ADJ_VOU_DT
     */
    public Date getAdjVouDt() {
        return (Date) getAttributeInternal(ADJVOUDT);
    }

    /**
     * Sets <code>value</code> as attribute value for ADJ_VOU_DT using the alias name AdjVouDt.
     * @param value value to set the ADJ_VOU_DT
     */
    public void setAdjVouDt(Date value) {
        setAttributeInternal(ADJVOUDT, value);
    }

    /**
     * Gets the attribute value for ADJ_VOU_ID using the alias name AdjVouId.
     * @return the ADJ_VOU_ID
     */
    public String getAdjVouId() {
        return (String) getAttributeInternal(ADJVOUID);
    }

    /**
     * Sets <code>value</code> as attribute value for ADJ_VOU_ID using the alias name AdjVouId.
     * @param value value to set the ADJ_VOU_ID
     */
    public void setAdjVouId(String value) {
        setAttributeInternal(ADJVOUID, value);
    }

    /**
     * Gets the attribute value for GL_ADJ_AMT_ADJ_THS using the alias name GlAdjAmtAdjThs.
     * @return the GL_ADJ_AMT_ADJ_THS
     */
    public Integer getGlAdjAmtAdjThs() {
        return (Integer) getAttributeInternal(GLADJAMTADJTHS);
    }

    /**
     * Sets <code>value</code> as attribute value for GL_ADJ_AMT_ADJ_THS using the alias name GlAdjAmtAdjThs.
     * @param value value to set the GL_ADJ_AMT_ADJ_THS
     */
    public void setGlAdjAmtAdjThs(Integer value) {
        setAttributeInternal(GLADJAMTADJTHS, value);
    }

    /**
     * Gets the attribute value for GL_ADJ_AMT_ADJ_TOT using the alias name GlAdjAmtAdjTot.
     * @return the GL_ADJ_AMT_ADJ_TOT
     */
    public Integer getGlAdjAmtAdjTot() {
        return (Integer) getAttributeInternal(GLADJAMTADJTOT);
    }

    /**
     * Sets <code>value</code> as attribute value for GL_ADJ_AMT_ADJ_TOT using the alias name GlAdjAmtAdjTot.
     * @param value value to set the GL_ADJ_AMT_ADJ_TOT
     */
    public void setGlAdjAmtAdjTot(Integer value) {
        setAttributeInternal(GLADJAMTADJTOT, value);
    }

    /**
     * Gets the attribute value for GL_ADJ_AMT_OT using the alias name GlAdjAmtOT.
     * @return the GL_ADJ_AMT_OT
     */
    public Number getGlAdjAmtOT() {
        return (Number) getAttributeInternal(GLADJAMTOT);
    }

    /**
     * Sets <code>value</code> as attribute value for GL_ADJ_AMT_OT using the alias name GlAdjAmtOT.
     * @param value value to set the GL_ADJ_AMT_OT
     */
    public void setGlAdjAmtOT(Number value) {
        if(value !=null)
        {
                          Integer decimalPlaces = 2;
                       if(resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}")!=null){
                           System.out.println("Null");
                           decimalPlaces = Integer.parseInt((resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}")).toString());   
                       }
                       
                      value = (Number)value.round(decimalPlaces );

            
        }
       
        setAttributeInternal(GLADJAMTOT, value);
    }

    /**
     * Gets the attribute value for GL_REF_DT using the alias name GlRefDt.
     * @return the GL_REF_DT
     */
    public Date getGlRefDt() {
        return (Date) getAttributeInternal(GLREFDT);
    }

    /**
     * Sets <code>value</code> as attribute value for GL_REF_DT using the alias name GlRefDt.
     * @param value value to set the GL_REF_DT
     */
    public void setGlRefDt(Date value) {
        setAttributeInternal(GLREFDT, value);
    }

    /**
     * Gets the attribute value for GL_REF_ID using the alias name GlRefId.
     * @return the GL_REF_ID
     */
    public String getGlRefId() {
        return (String) getAttributeInternal(GLREFID);
    }

    /**
     * Sets <code>value</code> as attribute value for GL_REF_ID using the alias name GlRefId.
     * @param value value to set the GL_REF_ID
     */
    public void setGlRefId(String value) {
        setAttributeInternal(GLREFID, value);
    }

    /**
     * Gets the attribute value for GL_REMAIN_OT using the alias name GlRemainOT.
     * @return the GL_REMAIN_OT
     */
    public Number getGlRemainOT() {
        return (Number) getAttributeInternal(GLREMAINOT);
    }

    /**
     * Sets <code>value</code> as attribute value for GL_REMAIN_OT using the alias name GlRemainOT.
     * @param value value to set the GL_REMAIN_OT
     */
    public void setGlRemainOT(Number value) {
        setAttributeInternal(GLREMAINOT, value);
    }

    /**
     * Gets the attribute value for GL_TXN_TYP using the alias name GlTxnTyp.
     * @return the GL_TXN_TYP
     */
    public String getGlTxnTyp() {
        return (String) getAttributeInternal(GLTXNTYP);
    }

    /**
     * Sets <code>value</code> as attribute value for GL_TXN_TYP using the alias name GlTxnTyp.
     * @param value value to set the GL_TXN_TYP
     */
    public void setGlTxnTyp(String value) {
        setAttributeInternal(GLTXNTYP, value);
    }

    /**
     * Gets the attribute value for RADJ_AMT_ADJ using the alias name RadjAmtAdj.
     * @return the RADJ_AMT_ADJ
     */
    public Number getRadjAmtAdj() {
        return (Number) getAttributeInternal(RADJAMTADJ);
    }

    /**
     * Sets <code>value</code> as attribute value for RADJ_AMT_ADJ using the alias name RadjAmtAdj.
     * @param value value to set the RADJ_AMT_ADJ
     */
    public void setRadjAmtAdj(Number value) {
        if(value !=null)
        {
                          Integer decimalPlaces = 2;
                       if(resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}")!=null){
                           System.out.println("Null");
                           decimalPlaces = Integer.parseInt((resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}")).toString());   
                       }
                       
                      value.round(decimalPlaces );

            setAttributeInternal(RADJAMTADJ, value);
        }
        
    }

    /**
     * Gets the attribute value for RADJ_AMT_SP using the alias name RadjAmtSp.
     * @return the RADJ_AMT_SP
     */
    public Number getRadjAmtSp() {
        return (Number) getAttributeInternal(RADJAMTSP);
    }

    /**
     * Sets <code>value</code> as attribute value for RADJ_AMT_SP using the alias name RadjAmtSp.
     * @param value value to set the RADJ_AMT_SP
     */
    public void setRadjAmtSp(Number value) {
        if(value !=null)
        {
                          Integer decimalPlaces = 2;
                       if(resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}")!=null){
                           System.out.println("Null");
                           decimalPlaces = Integer.parseInt((resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}")).toString());   
                       }
                       
                      value.round(decimalPlaces );

            setAttributeInternal(RADJAMTSP, value);
        }
        
    }
    public String resolvEl(String data){
         FacesContext fc = FacesContext.getCurrentInstance();
         Application app = fc.getApplication();
         ExpressionFactory elFactory = app.getExpressionFactory();
         ELContext elContext = fc.getELContext();
         ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
         String Message=valueExp.getValue(elContext).toString();
         return Message;
       }

    /**
     * Gets the attribute value for RADJ_ARAP_SL_NO using the alias name RadjArapSlNo.
     * @return the RADJ_ARAP_SL_NO
     */
    public Integer getRadjArapSlNo() {
        return (Integer) getAttributeInternal(RADJARAPSLNO);
    }

    /**
     * Sets <code>value</code> as attribute value for RADJ_ARAP_SL_NO using the alias name RadjArapSlNo.
     * @param value value to set the RADJ_ARAP_SL_NO
     */
    public void setRadjArapSlNo(Integer value) {
        setAttributeInternal(RADJARAPSLNO, value);
    }

    /**
     * Gets the attribute value for RADJ_SL_NO using the alias name RadjSlNo.
     * @return the RADJ_SL_NO
     */
    public Integer getRadjSlNo() {
        return (Integer) getAttributeInternal(RADJSLNO);
    }

    /**
     * Sets <code>value</code> as attribute value for RADJ_SL_NO using the alias name RadjSlNo.
     * @param value value to set the RADJ_SL_NO
     */
    public void setRadjSlNo(Integer value) {
        setAttributeInternal(RADJSLNO, value);
    }

    /**
     * Gets the attribute value for RADJ_TAB using the alias name RadjTab.
     * @return the RADJ_TAB
     */
    public String getRadjTab() {
        return (String) getAttributeInternal(RADJTAB);
    }

    /**
     * Sets <code>value</code> as attribute value for RADJ_TAB using the alias name RadjTab.
     * @param value value to set the RADJ_TAB
     */
    public void setRadjTab(String value) {
        setAttributeInternal(RADJTAB, value);
    }

    /**
     * Gets the attribute value for RADJ_VOU_DT using the alias name RadjVouDt.
     * @return the RADJ_VOU_DT
     */
    public Date getRadjVouDt() {
        return (Date) getAttributeInternal(RADJVOUDT);
    }

    /**
     * Sets <code>value</code> as attribute value for RADJ_VOU_DT using the alias name RadjVouDt.
     * @param value value to set the RADJ_VOU_DT
     */
    public void setRadjVouDt(Date value) {
        setAttributeInternal(RADJVOUDT, value);
    }

    /**
     * Gets the attribute value for RADJ_VOU_ID using the alias name RadjVouId.
     * @return the RADJ_VOU_ID
     */
    public String getRadjVouId() {
        return (String) getAttributeInternal(RADJVOUID);
    }

    /**
     * Sets <code>value</code> as attribute value for RADJ_VOU_ID using the alias name RadjVouId.
     * @param value value to set the RADJ_VOU_ID
     */
    public void setRadjVouId(String value) {
        setAttributeInternal(RADJVOUID, value);
    }

    /**
     * Gets the attribute value for ROWID using the alias name Rowid.
     * @return the ROWID
     */
    public RowID getRowid() {
        return (RowID) getAttributeInternal(ROWID);
    }

    /**
     * Sets <code>value</code> as attribute value for ROWID using the alias name Rowid.
     * @param value value to set the ROWID
     */
    public void setRowid(RowID value) {
        setAttributeInternal(ROWID, value);
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
    public Date getUsrIdCreateDt() {
        return (Date) getAttributeInternal(USRIDCREATEDT);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_CREATE_DT using the alias name UsrIdCreateDt.
     * @param value value to set the USR_ID_CREATE_DT
     */
    public void setUsrIdCreateDt(Date value) {
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
    public Date getUsrIdModDt() {
        return (Date) getAttributeInternal(USRIDMODDT);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_MOD_DT using the alias name UsrIdModDt.
     * @param value value to set the USR_ID_MOD_DT
     */
    public void setUsrIdModDt(Date value) {
        setAttributeInternal(USRIDMODDT, value);
    }

    /**
     * Gets the attribute value for GL_CLD_ID using the alias name GlCldId.
     * @return the GL_CLD_ID
     */
    public String getGlCldId() {
        return (String) getAttributeInternal(GLCLDID);
    }

    /**
     * Sets <code>value</code> as attribute value for GL_CLD_ID using the alias name GlCldId.
     * @param value value to set the GL_CLD_ID
     */
    public void setGlCldId(String value) {
        setAttributeInternal(GLCLDID, value);
    }

    /**
     * Gets the attribute value for GL_HO_ORG_ID using the alias name GlHoOrgId.
     * @return the GL_HO_ORG_ID
     */
    public String getGlHoOrgId() {
        return (String) getAttributeInternal(GLHOORGID);
    }

    /**
     * Sets <code>value</code> as attribute value for GL_HO_ORG_ID using the alias name GlHoOrgId.
     * @param value value to set the GL_HO_ORG_ID
     */
    public void setGlHoOrgId(String value) {
        setAttributeInternal(GLHOORGID, value);
    }


    /**
     * Gets the view accessor <code>RowSet</code> LovVouIdVO1.
     */
    public RowSet getLovVouIdVO1() {
        return (RowSet)getAttributeInternal(LOVVOUIDVO1);
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
