package mmexpenseinvoice.model.views;

import oracle.jbo.domain.Timestamp;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import oracle.jbo.RowSet;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Jul 06 16:07:19 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class TransSearchVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Dummy,
        TransInvcNo,
        TransDocStat,
        TransEntity,
        TransCldId,
        TransSlocId,
        TransHoOrgId,
        TranFmDate,
        TransToDate,
        TransEoNm,
        TransInvcStat,
        TransOrgId,
        TransUsrId,
        LovSupplierVO,
        LovInvcStatVO;
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


    public static final int DUMMY = AttributesEnum.Dummy.index();
    public static final int TRANSINVCNO = AttributesEnum.TransInvcNo.index();
    public static final int TRANSDOCSTAT = AttributesEnum.TransDocStat.index();
    public static final int TRANSENTITY = AttributesEnum.TransEntity.index();
    public static final int TRANSCLDID = AttributesEnum.TransCldId.index();
    public static final int TRANSSLOCID = AttributesEnum.TransSlocId.index();
    public static final int TRANSHOORGID = AttributesEnum.TransHoOrgId.index();
    public static final int TRANFMDATE = AttributesEnum.TranFmDate.index();
    public static final int TRANSTODATE = AttributesEnum.TransToDate.index();
    public static final int TRANSEONM = AttributesEnum.TransEoNm.index();
    public static final int TRANSINVCSTAT = AttributesEnum.TransInvcStat.index();
    public static final int TRANSORGID = AttributesEnum.TransOrgId.index();
    public static final int TRANSUSRID = AttributesEnum.TransUsrId.index();
    public static final int LOVSUPPLIERVO = AttributesEnum.LovSupplierVO.index();
    public static final int LOVINVCSTATVO = AttributesEnum.LovInvcStatVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public TransSearchVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute Dummy.
     * @return the Dummy
     */
    public String getDummy() {
        return (String) getAttributeInternal(DUMMY);
    }

    /**
     * Gets the attribute value for the calculated attribute TransInvcNo.
     * @return the TransInvcNo
     */
    public String getTransInvcNo() {
        return (String) getAttributeInternal(TRANSINVCNO);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransInvcNo.
     * @param value value to set the  TransInvcNo
     */
    public void setTransInvcNo(String value) {
        setAttributeInternal(TRANSINVCNO, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransDocStat.
     * @return the TransDocStat
     */
    public String getTransDocStat() {
        return (String) getAttributeInternal(TRANSDOCSTAT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransDocStat.
     * @param value value to set the  TransDocStat
     */
    public void setTransDocStat(String value) {
        setAttributeInternal(TRANSDOCSTAT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransEntity.
     * @return the TransEntity
     */
    public String getTransEntity() {
        return (String) getAttributeInternal(TRANSENTITY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransEntity.
     * @param value value to set the  TransEntity
     */
    public void setTransEntity(String value) {
        setAttributeInternal(TRANSENTITY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransCldId.
     * @return the TransCldId
     */
    public String getTransCldId() {
        return resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        //return (String) getAttributeInternal(TRANSCLDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransCldId.
     * @param value value to set the  TransCldId
     */
    public void setTransCldId(String value) {
        setAttributeInternal(TRANSCLDID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransSlocId.
     * @return the TransSlocId
     */
    public Integer getTransSlocId() {
        return Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        //    return (String) getAttributeInternal(TRANSSLOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransSlocId.
     * @param value value to set the  TransSlocId
     */
    public void setTransSlocId(Integer value) {
        setAttributeInternal(TRANSSLOCID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransHoOrgId.
     * @return the TransHoOrgId
     */
    public String getTransHoOrgId() {
        return  resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
       // return (String) getAttributeInternal(TRANSHOORGID);
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

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransHoOrgId.
     * @param value value to set the  TransHoOrgId
     */
    public void setTransHoOrgId(String value) {
        setAttributeInternal(TRANSHOORGID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TranFmDate.
     * @return the TranFmDate
     */
    public Timestamp getTranFmDate() {
        return (Timestamp) getAttributeInternal(TRANFMDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TranFmDate.
     * @param value value to set the  TranFmDate
     */
    public void setTranFmDate(Timestamp value) {
        setAttributeInternal(TRANFMDATE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransToDate.
     * @return the TransToDate
     */
    public Timestamp getTransToDate() {
        return (Timestamp) getAttributeInternal(TRANSTODATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransToDate.
     * @param value value to set the  TransToDate
     */
    public void setTransToDate(Timestamp value) {
        setAttributeInternal(TRANSTODATE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransEoNm.
     * @return the TransEoNm
     */
    public String getTransEoNm() {
        return (String) getAttributeInternal(TRANSEONM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransEoNm.
     * @param value value to set the  TransEoNm
     */
    public void setTransEoNm(String value) {
        setAttributeInternal(TRANSEONM, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransInvcStat.
     * @return the TransInvcStat
     */
    public Integer getTransInvcStat() {
        return (Integer) getAttributeInternal(TRANSINVCSTAT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransInvcStat.
     * @param value value to set the  TransInvcStat
     */
    public void setTransInvcStat(Integer value) {
        setAttributeInternal(TRANSINVCSTAT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransOrgId.
     * @return the TransOrgId
     */
    public String getTransOrgId() {
        return resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        //return (String) getAttributeInternal(TRANSORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransOrgId.
     * @param value value to set the  TransOrgId
     */
    public void setTransOrgId(String value) {
        setAttributeInternal(TRANSORGID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransUsrId.
     * @return the TransUsrId
     */
    public Integer getTransUsrId() {
        return (Integer) getAttributeInternal(TRANSUSRID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransUsrId.
     * @param value value to set the  TransUsrId
     */
    public void setTransUsrId(Integer value) {
        setAttributeInternal(TRANSUSRID, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovSupplierVO.
     */
    public RowSet getLovSupplierVO() {
        return (RowSet) getAttributeInternal(LOVSUPPLIERVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovInvcStatVO.
     */
    public RowSet getLovInvcStatVO() {
        return (RowSet) getAttributeInternal(LOVINVCSTATVO);
    }
}

