package slspicpackshipapp.model.views;

import adf.utils.ebiz.EbizParamsSLSUtils;

import java.sql.Types;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;

import slspicpackshipapp.model.service.pickPackShipAMImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Mar 03 14:12:31 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class searchPickPackShipVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CldId {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        OrgId {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        HoOrgId {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getHoOrgId();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        SlocId {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        EoId {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getEoId();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setEoId((Integer) value);
            }
        },
        SoDocId {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getSoDocId();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        SoId {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getSoId();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        DlvMode {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getDlvMode();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        DlvAddsId {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getDlvAddsId();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        PickdocId {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getPickdocId();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        PickId {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getPickId();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        PickDt {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getPickDt();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        ShipmntId {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getShipmntId();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        PickFlg {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getPickFlg();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        PackFlg {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getPackFlg();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        ShipFlg {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getShipFlg();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        WhId {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getWhId();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        ShipmntDocId {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getShipmntDocId();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        ExtDocNo {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getExtDocNo();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        EoNmTrans {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getEoNmTrans();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setEoNmTrans((String) value);
            }
        },
        isPackAllowed {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getisPackAllowed();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setisPackAllowed((String) value);
            }
        },
        LovDlvModeVO1 {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getLovDlvModeVO1();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        LovWhIdVO1 {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getLovWhIdVO1();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        LovShipmentIdVO1 {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getLovShipmentIdVO1();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        },
        LovEoNmFromEoIdVO1 {
            public Object get(searchPickPackShipVORowImpl obj) {
                return obj.getLovEoNmFromEoIdVO1();
            }

            public void put(searchPickPackShipVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        };
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(searchPickPackShipVORowImpl object);

        public abstract void put(searchPickPackShipVORowImpl object, Object value);

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
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int EOID = AttributesEnum.EoId.index();
    public static final int SODOCID = AttributesEnum.SoDocId.index();
    public static final int SOID = AttributesEnum.SoId.index();
    public static final int DLVMODE = AttributesEnum.DlvMode.index();
    public static final int DLVADDSID = AttributesEnum.DlvAddsId.index();
    public static final int PICKDOCID = AttributesEnum.PickdocId.index();
    public static final int PICKID = AttributesEnum.PickId.index();
    public static final int PICKDT = AttributesEnum.PickDt.index();
    public static final int SHIPMNTID = AttributesEnum.ShipmntId.index();
    public static final int PICKFLG = AttributesEnum.PickFlg.index();
    public static final int PACKFLG = AttributesEnum.PackFlg.index();
    public static final int SHIPFLG = AttributesEnum.ShipFlg.index();
    public static final int WHID = AttributesEnum.WhId.index();
    public static final int SHIPMNTDOCID = AttributesEnum.ShipmntDocId.index();
    public static final int EXTDOCNO = AttributesEnum.ExtDocNo.index();
    public static final int EONMTRANS = AttributesEnum.EoNmTrans.index();
    public static final int ISPACKALLOWED = AttributesEnum.isPackAllowed.index();
    public static final int LOVDLVMODEVO1 = AttributesEnum.LovDlvModeVO1.index();
    public static final int LOVWHIDVO1 = AttributesEnum.LovWhIdVO1.index();
    public static final int LOVSHIPMENTIDVO1 = AttributesEnum.LovShipmentIdVO1.index();
    public static final int LOVEONMFROMEOIDVO1 = AttributesEnum.LovEoNmFromEoIdVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public searchPickPackShipVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute CldId.
     * @return the CldId
     */
    public String getCldId() {
        return (String) getAttributeInternal(CLDID);
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
     * Gets the attribute value for the calculated attribute SlocId.
     * @return the SlocId
     */
    public Integer getSlocId() {
        return (Integer) getAttributeInternal(SLOCID);
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
     * Gets the attribute value for the calculated attribute SoDocId.
     * @return the SoDocId
     */
    public String getSoDocId() {
        return (String) getAttributeInternal(SODOCID);
    }


    /**
     * Gets the attribute value for the calculated attribute SoId.
     * @return the SoId
     */
    public String getSoId() {
        return (String) getAttributeInternal(SOID);
    }


    /**
     * Gets the attribute value for the calculated attribute DlvMode.
     * @return the DlvMode
     */
    public Integer getDlvMode() {
        return (Integer) getAttributeInternal(DLVMODE);
    }


    /**
     * Gets the attribute value for the calculated attribute DlvAddsId.
     * @return the DlvAddsId
     */
    public String getDlvAddsId() {
        return (String) getAttributeInternal(DLVADDSID);
    }


    /**
     * Gets the attribute value for the calculated attribute PickdocId.
     * @return the PickdocId
     */
    public String getPickdocId() {
        return (String) getAttributeInternal(PICKDOCID);
    }


    /**
     * Gets the attribute value for the calculated attribute PickId.
     * @return the PickId
     */
    public String getPickId() {
        return (String) getAttributeInternal(PICKID);
    }


    /**
     * Gets the attribute value for the calculated attribute PickDt.
     * @return the PickDt
     */
    public Timestamp getPickDt() {
        return (Timestamp) getAttributeInternal(PICKDT);
    }


    /**
     * Gets the attribute value for the calculated attribute ShipmntId.
     * @return the ShipmntId
     */
    public String getShipmntId() {
        return (String) getAttributeInternal(SHIPMNTID);
    }


    /**
     * Gets the attribute value for the calculated attribute PickFlg.
     * @return the PickFlg
     */
    public String getPickFlg() {
        return (String) getAttributeInternal(PICKFLG);
    }


    /**
     * Gets the attribute value for the calculated attribute PackFlg.
     * @return the PackFlg
     */
    public String getPackFlg() {
        return (String) getAttributeInternal(PACKFLG);
    }


    /**
     * Gets the attribute value for the calculated attribute ShipFlg.
     * @return the ShipFlg
     */
    public String getShipFlg() {
        return (String) getAttributeInternal(SHIPFLG);
    }


    /**
     * Gets the attribute value for the calculated attribute WhId.
     * @return the WhId
     */
    public String getWhId() {
        return (String) getAttributeInternal(WHID);
    }


    /**
     * Gets the attribute value for the calculated attribute ShipmntDocId.
     * @return the ShipmntDocId
     */
    public String getShipmntDocId() {
        return (String) getAttributeInternal(SHIPMNTDOCID);
    }


    /**
     * Gets the attribute value for the calculated attribute ExtDocNo.
     * @return the ExtDocNo
     */
    public String getExtDocNo() {
        return (String) getAttributeInternal(EXTDOCNO);
    }

    /**
     * Gets the attribute value for the calculated attribute EoNmTrans.
     * @return the EoNmTrans
     */
    public String getEoNmTrans() {
        if (getAttributeInternal(EONMTRANS) == null) {
            RowSet eoIdVO1 = this.getLovEoNmFromEoIdVO1();
            // System.out.println("getEoId " + getEoId());
            eoIdVO1.setNamedWhereClauseParam("EoIdBind", this.getEoId());
            //   eoIdVO1.setNamedWhereClauseParam("OrgIdBind", this.getOrgId());
            eoIdVO1.setNamedWhereClauseParam("HoOrgIdBind", this.getHoOrgId());
            eoIdVO1.setNamedWhereClauseParam("CldIdBind", this.getCldId());
            eoIdVO1.setNamedWhereClauseParam("SlocIdBind", this.getSlocId());
            eoIdVO1.executeQuery();
            Row[] allRowsInRange = eoIdVO1.getAllRowsInRange();
            StringBuffer s = new StringBuffer("");
            for (Row r : allRowsInRange) {
                s = new StringBuffer(r.getAttribute("EoNm").toString());
                break;
            }
            setEoNmTrans(s.toString());
        }
        // System.out.println("EoNm is  : " + getAttributeInternal(EONMTRANS));
        return (String) getAttributeInternal(EONMTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute EoNmTrans.
     * @param value value to set the  EoNmTrans
     */
    public void setEoNmTrans(String value) {
        setAttributeInternal(EONMTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute isPackAllowed.
     * @return the isPackAllowed
     */
    public String getisPackAllowed() {
        return (String) getAttributeInternal(ISPACKALLOWED);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute isPackAllowed.
     * @param value value to set the  isPackAllowed
     */
    public void setisPackAllowed(String value) {
        setAttributeInternal(ISPACKALLOWED, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovDlvModeVO1.
     */
    public RowSet getLovDlvModeVO1() {
        return (RowSet) getAttributeInternal(LOVDLVMODEVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovWhIdVO1.
     */
    public RowSet getLovWhIdVO1() {
        return (RowSet) getAttributeInternal(LOVWHIDVO1);
    }


    /**
     * Gets the view accessor <code>RowSet</code> LovShipmentIdVO1.
     */
    public RowSet getLovShipmentIdVO1() {
        return (RowSet) getAttributeInternal(LOVSHIPMENTIDVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovEoNmFromEoIdVO1.
     */
    public RowSet getLovEoNmFromEoIdVO1() {
        return (RowSet) getAttributeInternal(LOVEONMFROMEOIDVO1);
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
