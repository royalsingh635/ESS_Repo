package slspicpackshipapp.model.views;

import java.sql.Timestamp;

import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Dec 16 12:32:47 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class viewSlsSoPickVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId {
            public Object get(viewSlsSoPickVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(viewSlsSoPickVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        SlocId {
            public Object get(viewSlsSoPickVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(viewSlsSoPickVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        OrgId {
            public Object get(viewSlsSoPickVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(viewSlsSoPickVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        HoOrgId {
            public Object get(viewSlsSoPickVORowImpl obj) {
                return obj.getHoOrgId();
            }

            public void put(viewSlsSoPickVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        DocId {
            public Object get(viewSlsSoPickVORowImpl obj) {
                return obj.getDocId();
            }

            public void put(viewSlsSoPickVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        SoId {
            public Object get(viewSlsSoPickVORowImpl obj) {
                return obj.getSoId();
            }

            public void put(viewSlsSoPickVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        SoDt {
            public Object get(viewSlsSoPickVORowImpl obj) {
                return obj.getSoDt();
            }

            public void put(viewSlsSoPickVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        OrderType {
            public Object get(viewSlsSoPickVORowImpl obj) {
                return obj.getOrderType();
            }

            public void put(viewSlsSoPickVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        EoId {
            public Object get(viewSlsSoPickVORowImpl obj) {
                return obj.getEoId();
            }

            public void put(viewSlsSoPickVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        CurrIdSp {
            public Object get(viewSlsSoPickVORowImpl obj) {
                return obj.getCurrIdSp();
            }

            public void put(viewSlsSoPickVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        CurrConvFctr {
            public Object get(viewSlsSoPickVORowImpl obj) {
                return obj.getCurrConvFctr();
            }

            public void put(viewSlsSoPickVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        ShipAdds {
            public Object get(viewSlsSoPickVORowImpl obj) {
                return obj.getShipAdds();
            }

            public void put(viewSlsSoPickVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        PrjId {
            public Object get(viewSlsSoPickVORowImpl obj) {
                return obj.getPrjId();
            }

            public void put(viewSlsSoPickVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        ItmNmTrans {
            public Object get(viewSlsSoPickVORowImpl obj) {
                return obj.getItmNmTrans();
            }

            public void put(viewSlsSoPickVORowImpl obj, Object value) {
                obj.setItmNmTrans((String) value);
            }
        },
        SoNmTrans {
            public Object get(viewSlsSoPickVORowImpl obj) {
                return obj.getSoNmTrans();
            }

            public void put(viewSlsSoPickVORowImpl obj, Object value) {
                obj.setSoNmTrans((String) value);
            }
        },
        SelectedTrans {
            public Object get(viewSlsSoPickVORowImpl obj) {
                return obj.getSelectedTrans();
            }

            public void put(viewSlsSoPickVORowImpl obj, Object value) {
                obj.setSelectedTrans((String) value);
            }
        },
        ViewSlsSoPickItm {
            public Object get(viewSlsSoPickVORowImpl obj) {
                return obj.getViewSlsSoPickItm();
            }

            public void put(viewSlsSoPickVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        LovOrderTypVO1 {
            public Object get(viewSlsSoPickVORowImpl obj) {
                return obj.getLovOrderTypVO1();
            }

            public void put(viewSlsSoPickVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        LovCurrIdVO1 {
            public Object get(viewSlsSoPickVORowImpl obj) {
                return obj.getLovCurrIdVO1();
            }

            public void put(viewSlsSoPickVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        };
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(viewSlsSoPickVORowImpl object);

        public abstract void put(viewSlsSoPickVORowImpl object, Object value);

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
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int SOID = AttributesEnum.SoId.index();
    public static final int SODT = AttributesEnum.SoDt.index();
    public static final int ORDERTYPE = AttributesEnum.OrderType.index();
    public static final int EOID = AttributesEnum.EoId.index();
    public static final int CURRIDSP = AttributesEnum.CurrIdSp.index();
    public static final int CURRCONVFCTR = AttributesEnum.CurrConvFctr.index();
    public static final int SHIPADDS = AttributesEnum.ShipAdds.index();
    public static final int PRJID = AttributesEnum.PrjId.index();
    public static final int ITMNMTRANS = AttributesEnum.ItmNmTrans.index();
    public static final int SONMTRANS = AttributesEnum.SoNmTrans.index();
    public static final int SELECTEDTRANS = AttributesEnum.SelectedTrans.index();
    public static final int VIEWSLSSOPICKITM = AttributesEnum.ViewSlsSoPickItm.index();
    public static final int LOVORDERTYPVO1 = AttributesEnum.LovOrderTypVO1.index();
    public static final int LOVCURRIDVO1 = AttributesEnum.LovCurrIdVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public viewSlsSoPickVORowImpl() {
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
     * Gets the attribute value for the calculated attribute SoId.
     * @return the SoId
     */
    public String getSoId() {
        return (String) getAttributeInternal(SOID);
    }

    /**
     * Gets the attribute value for the calculated attribute SoDt.
     * @return the SoDt
     */
    public Timestamp getSoDt() {
        return (Timestamp) getAttributeInternal(SODT);
    }

    /**
     * Gets the attribute value for the calculated attribute OrderType.
     * @return the OrderType
     */
    public Integer getOrderType() {
        return (Integer) getAttributeInternal(ORDERTYPE);
    }

    /**
     * Gets the attribute value for the calculated attribute EoId.
     * @return the EoId
     */
    public Integer getEoId() {
        return (Integer) getAttributeInternal(EOID);
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
     * Gets the attribute value for the calculated attribute ShipAdds.
     * @return the ShipAdds
     */
    public String getShipAdds() {
        return (String) getAttributeInternal(SHIPADDS);
    }

    /**
     * Gets the attribute value for the calculated attribute PrjId.
     * @return the PrjId
     */
    public String getPrjId() {
        return (String) getAttributeInternal(PRJID);
    }

    /**
     * Gets the attribute value for the calculated attribute ItmNmTrans.
     * @return the ItmNmTrans
     */
    public String getItmNmTrans() {
        return (String) getAttributeInternal(ITMNMTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ItmNmTrans.
     * @param value value to set the  ItmNmTrans
     */
    public void setItmNmTrans(String value) {
        setAttributeInternal(ITMNMTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SoNmTrans.
     * @return the SoNmTrans
     */
    public String getSoNmTrans() {
        return (String) getAttributeInternal(SONMTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SoNmTrans.
     * @param value value to set the  SoNmTrans
     */
    public void setSoNmTrans(String value) {
        setAttributeInternal(SONMTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SelectedTrans.
     * @return the SelectedTrans
     */
    public String getSelectedTrans() {
        return (String) getAttributeInternal(SELECTEDTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SelectedTrans.
     * @param value value to set the  SelectedTrans
     */
    public void setSelectedTrans(String value) {
        System.out.println("Setting SelectedTrans to : " + value);
        setAttributeInternal(SELECTEDTRANS, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link ViewSlsSoPickItm.
     */
    public RowIterator getViewSlsSoPickItm() {
        return (RowIterator) getAttributeInternal(VIEWSLSSOPICKITM);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovOrderTypVO1.
     */
    public RowSet getLovOrderTypVO1() {
        return (RowSet) getAttributeInternal(LOVORDERTYPVO1);
    }


    /**
     * Gets the view accessor <code>RowSet</code> LovCurrIdVO1.
     */
    public RowSet getLovCurrIdVO1() {
        return (RowSet) getAttributeInternal(LOVCURRIDVO1);
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

