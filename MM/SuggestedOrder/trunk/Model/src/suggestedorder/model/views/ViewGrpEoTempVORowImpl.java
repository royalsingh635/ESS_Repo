package suggestedorder.model.views;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Jun 20 14:59:28 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ViewGrpEoTempVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        SoGrpId {
            public Object get(ViewGrpEoTempVORowImpl obj) {
                return obj.getSoGrpId();
            }

            public void put(ViewGrpEoTempVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        EoId {
            public Object get(ViewGrpEoTempVORowImpl obj) {
                return obj.getEoId();
            }

            public void put(ViewGrpEoTempVORowImpl obj, Object value) {
                obj.setEoId((Integer) value);
            }
        },
        SlocId {
            public Object get(ViewGrpEoTempVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(ViewGrpEoTempVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        OrgId {
            public Object get(ViewGrpEoTempVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(ViewGrpEoTempVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        CldId {
            public Object get(ViewGrpEoTempVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(ViewGrpEoTempVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        TranSelectSupp {
            public Object get(ViewGrpEoTempVORowImpl obj) {
                return obj.getTranSelectSupp();
            }

            public void put(ViewGrpEoTempVORowImpl obj, Object value) {
                obj.setTranSelectSupp((Boolean) value);
            }
        },
        TranItemCount {
            public Object get(ViewGrpEoTempVORowImpl obj) {
                return obj.getTranItemCount();
            }

            public void put(ViewGrpEoTempVORowImpl obj, Object value) {
                obj.setTranItemCount((Integer) value);
            }
        },
        TransHoId {
            public Object get(ViewGrpEoTempVORowImpl obj) {
                return obj.getTransHoId();
            }

            public void put(ViewGrpEoTempVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        ViewGrpEoItmTemp {
            public Object get(ViewGrpEoTempVORowImpl obj) {
                return obj.getViewGrpEoItmTemp();
            }

            public void put(ViewGrpEoTempVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        ViewSoItemSupplierVO1 {
            public Object get(ViewGrpEoTempVORowImpl obj) {
                return obj.getViewSoItemSupplierVO1();
            }

            public void put(ViewGrpEoTempVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        ViewSuppLOV1 {
            public Object get(ViewGrpEoTempVORowImpl obj) {
                return obj.getViewSuppLOV1();
            }

            public void put(ViewGrpEoTempVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        };
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(ViewGrpEoTempVORowImpl object);

        public abstract void put(ViewGrpEoTempVORowImpl object, Object value);

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
    public static final int SOGRPID = AttributesEnum.SoGrpId.index();
    public static final int EOID = AttributesEnum.EoId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int TRANSELECTSUPP = AttributesEnum.TranSelectSupp.index();
    public static final int TRANITEMCOUNT = AttributesEnum.TranItemCount.index();
    public static final int TRANSHOID = AttributesEnum.TransHoId.index();
    public static final int VIEWGRPEOITMTEMP = AttributesEnum.ViewGrpEoItmTemp.index();
    public static final int VIEWSOITEMSUPPLIERVO1 = AttributesEnum.ViewSoItemSupplierVO1.index();
    public static final int VIEWSUPPLOV1 = AttributesEnum.ViewSuppLOV1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public ViewGrpEoTempVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute SoGrpId.
     * @return the SoGrpId
     */
    public String getSoGrpId() {
        return (String) getAttributeInternal(SOGRPID);
    }

    /**
     * Gets the attribute value for the calculated attribute EoId.
     * @return the EoId
     */
    public Integer getEoId() {
        return (Integer) getAttributeInternal(EOID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute EoId.
     * @param value value to set the  EoId
     */
    public void setEoId(Integer value) {
        setAttributeInternal(EOID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SlocId.
     * @return the SlocId
     */
    public Integer getSlocId() {
        return (Integer) getAttributeInternal(SLOCID);
    }

    /**
     * Gets the attribute value for the calculated attribute OrgId.
     * @return the OrgId
     */
    public String getOrgId() {
        return (String) getAttributeInternal(ORGID);
    }

    /**
     * Gets the attribute value for the calculated attribute CldId.
     * @return the CldId
     */
    public String getCldId() {
        return (String) getAttributeInternal(CLDID);
    }

    /**
     * Gets the attribute value for the calculated attribute TranSelectSupp.
     * @return the TranSelectSupp
     */
    public Boolean getTranSelectSupp() {
        return (Boolean) getAttributeInternal(TRANSELECTSUPP);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TranSelectSupp.
     * @param value value to set the  TranSelectSupp
     */
    public void setTranSelectSupp(Boolean value) {
        setAttributeInternal(TRANSELECTSUPP, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TranItemCount.
     * @return the TranItemCount
     */
    public Integer getTranItemCount() {
        return (Integer) getAttributeInternal(TRANITEMCOUNT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TranItemCount.
     * @param value value to set the  TranItemCount
     */
    public void setTranItemCount(Integer value) {
        setAttributeInternal(TRANITEMCOUNT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransHoId.
     * @return the TransHoId
     */
    public String getTransHoId() {
        String hoId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
        return hoId;
        //return (String) getAttributeInternal(TRANSHOID);
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link ViewGrpEoItmTemp.
     */
    public RowIterator getViewGrpEoItmTemp() {
        return (RowIterator) getAttributeInternal(VIEWGRPEOITMTEMP);
    }

    /**
     * Gets the view accessor <code>RowSet</code> ViewSoItemSupplierVO1.
     */
    public RowSet getViewSoItemSupplierVO1() {
        return (RowSet) getAttributeInternal(VIEWSOITEMSUPPLIERVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> ViewSuppLOV1.
     */
    public RowSet getViewSuppLOV1() {
        return (RowSet) getAttributeInternal(VIEWSUPPLOV1);
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

