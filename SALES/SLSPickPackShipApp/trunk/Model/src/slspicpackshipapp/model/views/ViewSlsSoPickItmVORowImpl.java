package slspicpackshipapp.model.views;

import adf.utils.bean.ADFBeanUtils;

import java.sql.Timestamp;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;

// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Nov 18 17:15:04 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ViewSlsSoPickItmVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CldId {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        SlocId {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        OrgId {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        HoOrgId {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getHoOrgId();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        DocId {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getDocId();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        DlvDt {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getDlvDt();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        SoId {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getSoId();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        ItmIdSo {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getItmIdSo();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        ItmQty {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getItmQty();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        ItmRate {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getItmRate();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        ItmUomSo {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getItmUomSo();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        DlvQtySo {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getDlvQtySo();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        ItmAvlQty {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getItmAvlQty();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        PendingQty {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getPendingQty();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        SoDt {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getSoDt();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        DlvTlrncDays {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getDlvTlrncDays();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        TlrncQtyType {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getTlrncQtyType();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        TlrncQtyVal {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getTlrncQtyVal();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        CurrIdSp {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getCurrIdSp();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        CurrConvFctr {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getCurrConvFctr();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        WhId {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getWhId();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        SerializedFlg {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getSerializedFlg();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        ItmDesc {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getItmDesc();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        SchmFlg {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getSchmFlg();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        PrjId {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getPrjId();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        OrderType {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getOrderType();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        SelectItemChkBxTrans {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getSelectItemChkBxTrans();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setSelectItemChkBxTrans((String) value);
            }
        },
        SchdlQuantTrans {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getSchdlQuantTrans();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setSchdlQuantTrans((Number) value);
            }
        },
        viewSlsSoPick {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getviewSlsSoPick();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        LovItmUomVO1 {
            public Object get(ViewSlsSoPickItmVORowImpl obj) {
                return obj.getLovItmUomVO1();
            }

            public void put(ViewSlsSoPickItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        };
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(ViewSlsSoPickItmVORowImpl object);

        public abstract void put(ViewSlsSoPickItmVORowImpl object, Object value);

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
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int DLVDT = AttributesEnum.DlvDt.index();
    public static final int SOID = AttributesEnum.SoId.index();
    public static final int ITMIDSO = AttributesEnum.ItmIdSo.index();
    public static final int ITMQTY = AttributesEnum.ItmQty.index();
    public static final int ITMRATE = AttributesEnum.ItmRate.index();
    public static final int ITMUOMSO = AttributesEnum.ItmUomSo.index();
    public static final int DLVQTYSO = AttributesEnum.DlvQtySo.index();
    public static final int ITMAVLQTY = AttributesEnum.ItmAvlQty.index();
    public static final int PENDINGQTY = AttributesEnum.PendingQty.index();
    public static final int SODT = AttributesEnum.SoDt.index();
    public static final int DLVTLRNCDAYS = AttributesEnum.DlvTlrncDays.index();
    public static final int TLRNCQTYTYPE = AttributesEnum.TlrncQtyType.index();
    public static final int TLRNCQTYVAL = AttributesEnum.TlrncQtyVal.index();
    public static final int CURRIDSP = AttributesEnum.CurrIdSp.index();
    public static final int CURRCONVFCTR = AttributesEnum.CurrConvFctr.index();
    public static final int WHID = AttributesEnum.WhId.index();
    public static final int SERIALIZEDFLG = AttributesEnum.SerializedFlg.index();
    public static final int ITMDESC = AttributesEnum.ItmDesc.index();
    public static final int SCHMFLG = AttributesEnum.SchmFlg.index();
    public static final int PRJID = AttributesEnum.PrjId.index();
    public static final int ORDERTYPE = AttributesEnum.OrderType.index();
    public static final int SELECTITEMCHKBXTRANS = AttributesEnum.SelectItemChkBxTrans.index();
    public static final int SCHDLQUANTTRANS = AttributesEnum.SchdlQuantTrans.index();
    public static final int VIEWSLSSOPICK = AttributesEnum.viewSlsSoPick.index();
    public static final int LOVITMUOMVO1 = AttributesEnum.LovItmUomVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public ViewSlsSoPickItmVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute CldId.
     * @return the CldId
     */
    public String getCldId() {
        return (String) getAttributeInternal(CLDID);
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
     * Gets the attribute value for the calculated attribute HoOrgId.
     * @return the HoOrgId
     */
    public String getHoOrgId() {
        return (String) getAttributeInternal(HOORGID);
    }


    /**
     * Gets the attribute value for the calculated attribute DocId.
     * @return the DocId
     */
    public String getDocId() {
        return (String) getAttributeInternal(DOCID);
    }


    /**
     * Gets the attribute value for the calculated attribute DlvDt.
     * @return the DlvDt
     */
    public Timestamp getDlvDt() {
        return (Timestamp) getAttributeInternal(DLVDT);
    }


    /**
     * Gets the attribute value for the calculated attribute SoId.
     * @return the SoId
     */
    public String getSoId() {
        return (String) getAttributeInternal(SOID);
    }


    /**
     * Gets the attribute value for the calculated attribute ItmIdSo.
     * @return the ItmIdSo
     */
    public String getItmIdSo() {
        return (String) getAttributeInternal(ITMIDSO);
    }


    /**
     * Gets the attribute value for the calculated attribute ItmQty.
     * @return the ItmQty
     */
    public Number getItmQty() {
        return (Number) getAttributeInternal(ITMQTY);
    }


    /**
     * Gets the attribute value for the calculated attribute ItmRate.
     * @return the ItmRate
     */
    public Number getItmRate() {
        return (Number) getAttributeInternal(ITMRATE);
    }


    /**
     * Gets the attribute value for the calculated attribute ItmUomSo.
     * @return the ItmUomSo
     */
    public String getItmUomSo() {
        return (String) getAttributeInternal(ITMUOMSO);
    }


    /**
     * Gets the attribute value for the calculated attribute DlvQtySo.
     * @return the DlvQtySo
     */
    public Number getDlvQtySo() {
        return (Number) getAttributeInternal(DLVQTYSO);
    }


    /**
     * Gets the attribute value for the calculated attribute ItmAvlQty.
     * @return the ItmAvlQty
     */
    public Number getItmAvlQty() {
        return (Number) getAttributeInternal(ITMAVLQTY);
    }


    /**
     * Gets the attribute value for the calculated attribute PendingQty.
     * @return the PendingQty
     */
    public Number getPendingQty() {
        return (Number) getAttributeInternal(PENDINGQTY);
    }


    /**
     * Gets the attribute value for the calculated attribute SoDt.
     * @return the SoDt
     */
    public Timestamp getSoDt() {
        return (Timestamp) getAttributeInternal(SODT);
    }


    /**
     * Gets the attribute value for the calculated attribute DlvTlrncDays.
     * @return the DlvTlrncDays
     */
    public Timestamp getDlvTlrncDays() {
        return (Timestamp) getAttributeInternal(DLVTLRNCDAYS);
    }


    /**
     * Gets the attribute value for the calculated attribute TlrncQtyType.
     * @return the TlrncQtyType
     */
    public String getTlrncQtyType() {
        return (String) getAttributeInternal(TLRNCQTYTYPE);
    }


    /**
     * Gets the attribute value for the calculated attribute TlrncQtyVal.
     * @return the TlrncQtyVal
     */
    public Number getTlrncQtyVal() {
        return (Number) getAttributeInternal(TLRNCQTYVAL);
    }


    /**
     * Gets the attribute value for the calculated attribute CurrIdSp.
     * @return the CurrIdSp
     */
    public Integer getCurrIdSp() {
        return (Integer) getAttributeInternal(CURRIDSP);
    }


    /**
     * Gets the attribute value for the calculated attribute CurrConvFctr.
     * @return the CurrConvFctr
     */
    public Number getCurrConvFctr() {
        return (Number) getAttributeInternal(CURRCONVFCTR);
    }


    /**
     * Gets the attribute value for the calculated attribute WhId.
     * @return the WhId
     */
    public String getWhId() {
        return (String) getAttributeInternal(WHID);
    }


    /**
     * Gets the attribute value for the calculated attribute SerializedFlg.
     * @return the SerializedFlg
     */
    public String getSerializedFlg() {
        return (String) getAttributeInternal(SERIALIZEDFLG);
    }


    /**
     * Gets the attribute value for the calculated attribute ItmDesc.
     * @return the ItmDesc
     */
    public String getItmDesc() {
        return (String) getAttributeInternal(ITMDESC);
    }


    /**
     * Gets the attribute value for the calculated attribute SchmFlg.
     * @return the SchmFlg
     */
    public String getSchmFlg() {
        return (String) getAttributeInternal(SCHMFLG);
    }

    /**
     * Gets the attribute value for the calculated attribute PrjId.
     * @return the PrjId
     */
    public String getPrjId() {
        return (String) getAttributeInternal(PRJID);
    }

    /**
     * Gets the attribute value for the calculated attribute OrderType.
     * @return the OrderType
     */
    public Integer getOrderType() {
        return (Integer) getAttributeInternal(ORDERTYPE);
    }

    /**
     * Gets the attribute value for the calculated attribute SelectItemChkBxTrans.
     * @return the SelectItemChkBxTrans
     */
    public String getSelectItemChkBxTrans() {
        return (String) getAttributeInternal(SELECTITEMCHKBXTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SelectItemChkBxTrans.
     * @param value value to set the  SelectItemChkBxTrans
     */
    public void setSelectItemChkBxTrans(String value) {
        System.out.println("Setting value in SoItm : " + value);
        setAttributeInternal(SELECTITEMCHKBXTRANS, value);
        if ("N".equals(value)) {
            setSchdlQuantTrans(null);
        } else {
            setSchdlQuantTrans(getPendingQty());
        }
    }

    /**
     * Gets the attribute value for the calculated attribute SchdlQuantTrans.
     * @return the SchdlQuantTrans
     */
    public Number getSchdlQuantTrans() {
        return (Number) getAttributeInternal(SCHDLQUANTTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SchdlQuantTrans.
     * @param value value to set the  SchdlQuantTrans
     */
    public void setSchdlQuantTrans(Number value) {
        if (value == null) {
            value = new Number(0);
        }
        if ("Y".equalsIgnoreCase(getSerializedFlg())) {
            Number tmp = ADFBeanUtils.roundOff(0, value);
            setAttributeInternal(SCHDLQUANTTRANS, tmp);
        } else {
            setAttributeInternal(SCHDLQUANTTRANS, value);
        }

    }

    /**
     * Gets the associated <code>Row</code> using master-detail link viewSlsSoPick.
     */
    public Row getviewSlsSoPick() {
        return (Row) getAttributeInternal(VIEWSLSSOPICK);
    }

    /**
     * Sets the master-detail link viewSlsSoPick between this object and <code>value</code>.
     */
    public void setviewSlsSoPick(Row value) {
        setAttributeInternal(VIEWSLSSOPICK, value);
    }


    /**
     * Gets the view accessor <code>RowSet</code> LovItmUomVO1.
     */
    public RowSet getLovItmUomVO1() {
        return (RowSet) getAttributeInternal(LOVITMUOMVO1);
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
