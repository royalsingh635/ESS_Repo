package mmgatepass.model.views;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.RowSet;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Jun 03 16:18:18 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class TransRcptSrchVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Dummy {
            public Object get(TransRcptSrchVORowImpl obj) {
                return obj.getDummy();
            }

            public void put(TransRcptSrchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        TransEoNm {
            public Object get(TransRcptSrchVORowImpl obj) {
                return obj.getTransEoNm();
            }

            public void put(TransRcptSrchVORowImpl obj, Object value) {
                obj.setTransEoNm((String) value);
            }
        }
        ,
        TransEoId {
            public Object get(TransRcptSrchVORowImpl obj) {
                return obj.getTransEoId();
            }

            public void put(TransRcptSrchVORowImpl obj, Object value) {
                obj.setTransEoId((Integer) value);
            }
        }
        ,
        TransWhNm {
            public Object get(TransRcptSrchVORowImpl obj) {
                return obj.getTransWhNm();
            }

            public void put(TransRcptSrchVORowImpl obj, Object value) {
                obj.setTransWhNm((String) value);
            }
        }
        ,
        TransHoOrgId {
            public Object get(TransRcptSrchVORowImpl obj) {
                return obj.getTransHoOrgId();
            }

            public void put(TransRcptSrchVORowImpl obj, Object value) {
                obj.setTransHoOrgId((String) value);
            }
        }
        ,
        TransOrgId {
            public Object get(TransRcptSrchVORowImpl obj) {
                return obj.getTransOrgId();
            }

            public void put(TransRcptSrchVORowImpl obj, Object value) {
                obj.setTransOrgId((String) value);
            }
        }
        ,
        TransCldId {
            public Object get(TransRcptSrchVORowImpl obj) {
                return obj.getTransCldId();
            }

            public void put(TransRcptSrchVORowImpl obj, Object value) {
                obj.setTransCldId((String) value);
            }
        }
        ,
        TransSlocId {
            public Object get(TransRcptSrchVORowImpl obj) {
                return obj.getTransSlocId();
            }

            public void put(TransRcptSrchVORowImpl obj, Object value) {
                obj.setTransSlocId((Integer) value);
            }
        }
        ,
        TransItmId {
            public Object get(TransRcptSrchVORowImpl obj) {
                return obj.getTransItmId();
            }

            public void put(TransRcptSrchVORowImpl obj, Object value) {
                obj.setTransItmId((String) value);
            }
        }
        ,
        TransItmNm {
            public Object get(TransRcptSrchVORowImpl obj) {
                return obj.getTransItmNm();
            }

            public void put(TransRcptSrchVORowImpl obj, Object value) {
                obj.setTransItmNm((String) value);
            }
        }
        ,
        LovEoIdVO {
            public Object get(TransRcptSrchVORowImpl obj) {
                return obj.getLovEoIdVO();
            }

            public void put(TransRcptSrchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovWhIdVO {
            public Object get(TransRcptSrchVORowImpl obj) {
                return obj.getLovWhIdVO();
            }

            public void put(TransRcptSrchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovItmIdVO1 {
            public Object get(TransRcptSrchVORowImpl obj) {
                return obj.getLovItmIdVO1();
            }

            public void put(TransRcptSrchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(TransRcptSrchVORowImpl object);

        public abstract void put(TransRcptSrchVORowImpl object, Object value);

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
    public static final int TRANSEONM = AttributesEnum.TransEoNm.index();
    public static final int TRANSEOID = AttributesEnum.TransEoId.index();
    public static final int TRANSWHNM = AttributesEnum.TransWhNm.index();
    public static final int TRANSHOORGID = AttributesEnum.TransHoOrgId.index();
    public static final int TRANSORGID = AttributesEnum.TransOrgId.index();
    public static final int TRANSCLDID = AttributesEnum.TransCldId.index();
    public static final int TRANSSLOCID = AttributesEnum.TransSlocId.index();
    public static final int TRANSITMID = AttributesEnum.TransItmId.index();
    public static final int TRANSITMNM = AttributesEnum.TransItmNm.index();
    public static final int LOVEOIDVO = AttributesEnum.LovEoIdVO.index();
    public static final int LOVWHIDVO = AttributesEnum.LovWhIdVO.index();
    public static final int LOVITMIDVO1 = AttributesEnum.LovItmIdVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public TransRcptSrchVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute Dummy.
     * @return the Dummy
     */
    public String getDummy() {
        return (String) getAttributeInternal(DUMMY);
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
     * Gets the attribute value for the calculated attribute TransEoId.
     * @return the TransEoId
     */
    public Integer getTransEoId() {
        return (Integer) getAttributeInternal(TRANSEOID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransEoId.
     * @param value value to set the  TransEoId
     */
    public void setTransEoId(Integer value) {
        setAttributeInternal(TRANSEOID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransWhNm.
     * @return the TransWhNm
     */
    public String getTransWhNm() {
        return (String) getAttributeInternal(TRANSWHNM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransWhNm.
     * @param value value to set the  TransWhNm
     */
    public void setTransWhNm(String value) {
        setAttributeInternal(TRANSWHNM, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransHoOrgId.
     * @return the TransHoOrgId
     */
    public String getTransHoOrgId() {
      return  resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
      //  return (String) getAttributeInternal(TRANSHOORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransHoOrgId.
     * @param value value to set the  TransHoOrgId
     */
    public void setTransHoOrgId(String value) {
        setAttributeInternal(TRANSHOORGID, value);
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
      return  Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
       // return (Integer) getAttributeInternal(TRANSSLOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransSlocId.
     * @param value value to set the  TransSlocId
     */
    public void setTransSlocId(Integer value) {
        setAttributeInternal(TRANSSLOCID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransItmId.
     * @return the TransItmId
     */
    public String getTransItmId() {
        return (String) getAttributeInternal(TRANSITMID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransItmId.
     * @param value value to set the  TransItmId
     */
    public void setTransItmId(String value) {
        setAttributeInternal(TRANSITMID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransItmNm.
     * @return the TransItmNm
     */
    public String getTransItmNm() {
        return (String) getAttributeInternal(TRANSITMNM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransItmNm.
     * @param value value to set the  TransItmNm
     */
    public void setTransItmNm(String value) {
        setAttributeInternal(TRANSITMNM, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovEoIdVO.
     */
    public RowSet getLovEoIdVO() {
        return (RowSet) getAttributeInternal(LOVEOIDVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovWhIdVO.
     */
    public RowSet getLovWhIdVO() {
        return (RowSet) getAttributeInternal(LOVWHIDVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovItmIdVO1.
     */
    public RowSet getLovItmIdVO1() {
        return (RowSet) getAttributeInternal(LOVITMIDVO1);
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

