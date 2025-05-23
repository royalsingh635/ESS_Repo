package mmappuom.model.views;

import mmappuom.model.entities.AppUomConvStdEOImpl;

import oracle.jbo.RowSet;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Nov 19 14:32:00 IST 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppUomConvStdVORowImpl extends ViewRowImpl {


    public static final int ENTITY_APPUOMCONVSTDEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        SlocId {
            public Object get(AppUomConvStdVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(AppUomConvStdVORowImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        UomId {
            public Object get(AppUomConvStdVORowImpl obj) {
                return obj.getUomId();
            }

            public void put(AppUomConvStdVORowImpl obj, Object value) {
                obj.setUomId((String)value);
            }
        }
        ,
        UomNm {
            public Object get(AppUomConvStdVORowImpl obj) {
                return obj.getUomNm();
            }

            public void put(AppUomConvStdVORowImpl obj, Object value) {
                obj.setUomNm((String)value);
            }
        }
        ,
        UomDesc {
            public Object get(AppUomConvStdVORowImpl obj) {
                return obj.getUomDesc();
            }

            public void put(AppUomConvStdVORowImpl obj, Object value) {
                obj.setUomDesc((String)value);
            }
        }
        ,
        UomClass {
            public Object get(AppUomConvStdVORowImpl obj) {
                return obj.getUomClass();
            }

            public void put(AppUomConvStdVORowImpl obj, Object value) {
                obj.setUomClass((Integer)value);
            }
        }
        ,
        BaseUom {
            public Object get(AppUomConvStdVORowImpl obj) {
                return obj.getBaseUom();
            }

            public void put(AppUomConvStdVORowImpl obj, Object value) {
                obj.setBaseUom((String)value);
            }
        }
        ,
        ConvFctr {
            public Object get(AppUomConvStdVORowImpl obj) {
                return obj.getConvFctr();
            }

            public void put(AppUomConvStdVORowImpl obj, Object value) {
                obj.setConvFctr((Number)value);
            }
        }
        ,
        Actv {
            public Object get(AppUomConvStdVORowImpl obj) {
                return obj.getActv();
            }

            public void put(AppUomConvStdVORowImpl obj, Object value) {
                obj.setActv((String)value);
            }
        }
        ,
        InactvResn {
            public Object get(AppUomConvStdVORowImpl obj) {
                return obj.getInactvResn();
            }

            public void put(AppUomConvStdVORowImpl obj, Object value) {
                obj.setInactvResn((String)value);
            }
        }
        ,
        InactvDt {
            public Object get(AppUomConvStdVORowImpl obj) {
                return obj.getInactvDt();
            }

            public void put(AppUomConvStdVORowImpl obj, Object value) {
                obj.setInactvDt((Date)value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(AppUomConvStdVORowImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(AppUomConvStdVORowImpl obj, Object value) {
                obj.setUsrIdCreate((Integer)value);
            }
        }
        ,
        UsrIdCreateDt {
            public Object get(AppUomConvStdVORowImpl obj) {
                return obj.getUsrIdCreateDt();
            }

            public void put(AppUomConvStdVORowImpl obj, Object value) {
                obj.setUsrIdCreateDt((Date)value);
            }
        }
        ,
        UsrIdMod {
            public Object get(AppUomConvStdVORowImpl obj) {
                return obj.getUsrIdMod();
            }

            public void put(AppUomConvStdVORowImpl obj, Object value) {
                obj.setUsrIdMod((Integer)value);
            }
        }
        ,
        UsrIdModDt {
            public Object get(AppUomConvStdVORowImpl obj) {
                return obj.getUsrIdModDt();
            }

            public void put(AppUomConvStdVORowImpl obj, Object value) {
                obj.setUsrIdModDt((Date)value);
            }
        }
        ,
        UomStdEntId {
            public Object get(AppUomConvStdVORowImpl obj) {
                return obj.getUomStdEntId();
            }

            public void put(AppUomConvStdVORowImpl obj, Object value) {
                obj.setUomStdEntId((Integer)value);
            }
        }
        ,
        CldId {
            public Object get(AppUomConvStdVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(AppUomConvStdVORowImpl obj, Object value) {
                obj.setCldId((String)value);
            }
        }
        ,
        LovuomClassidVO1 {
            public Object get(AppUomConvStdVORowImpl obj) {
                return obj.getLovuomClassidVO1();
            }

            public void put(AppUomConvStdVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(AppUomConvStdVORowImpl object);

        public abstract void put(AppUomConvStdVORowImpl object, Object value);

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


    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int UOMID = AttributesEnum.UomId.index();
    public static final int UOMNM = AttributesEnum.UomNm.index();
    public static final int UOMDESC = AttributesEnum.UomDesc.index();
    public static final int UOMCLASS = AttributesEnum.UomClass.index();
    public static final int BASEUOM = AttributesEnum.BaseUom.index();
    public static final int CONVFCTR = AttributesEnum.ConvFctr.index();
    public static final int ACTV = AttributesEnum.Actv.index();
    public static final int INACTVRESN = AttributesEnum.InactvResn.index();
    public static final int INACTVDT = AttributesEnum.InactvDt.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int UOMSTDENTID = AttributesEnum.UomStdEntId.index();
    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int LOVUOMCLASSIDVO1 = AttributesEnum.LovuomClassidVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public AppUomConvStdVORowImpl() {
    }

    /**
     * Gets AppUomConvStdEO entity object.
     * @return the AppUomConvStdEO
     */
    public AppUomConvStdEOImpl getAppUomConvStdEO() {
        return (AppUomConvStdEOImpl)getEntity(ENTITY_APPUOMCONVSTDEO);
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
     * Gets the attribute value for UOM_ID using the alias name UomId.
     * @return the UOM_ID
     */
    public String getUomId() {
        return (String) getAttributeInternal(UOMID);
    }

    /**
     * Sets <code>value</code> as attribute value for UOM_ID using the alias name UomId.
     * @param value value to set the UOM_ID
     */
    public void setUomId(String value) {
        setAttributeInternal(UOMID, value);
    }

    /**
     * Gets the attribute value for UOM_NM using the alias name UomNm.
     * @return the UOM_NM
     */
    public String getUomNm() {
        return (String) getAttributeInternal(UOMNM);
    }

    /**
     * Sets <code>value</code> as attribute value for UOM_NM using the alias name UomNm.
     * @param value value to set the UOM_NM
     */
    public void setUomNm(String value) {
        setAttributeInternal(UOMNM, value);
    }

    /**
     * Gets the attribute value for UOM_DESC using the alias name UomDesc.
     * @return the UOM_DESC
     */
    public String getUomDesc() {
        return (String) getAttributeInternal(UOMDESC);
    }

    /**
     * Sets <code>value</code> as attribute value for UOM_DESC using the alias name UomDesc.
     * @param value value to set the UOM_DESC
     */
    public void setUomDesc(String value) {
        setAttributeInternal(UOMDESC, value);
    }

    /**
     * Gets the attribute value for UOM_CLASS using the alias name UomClass.
     * @return the UOM_CLASS
     */
    public Integer getUomClass() {
        return (Integer) getAttributeInternal(UOMCLASS);
    }

    /**
     * Sets <code>value</code> as attribute value for UOM_CLASS using the alias name UomClass.
     * @param value value to set the UOM_CLASS
     */
    public void setUomClass(Integer value) {
        setAttributeInternal(UOMCLASS, value);
    }

    /**
     * Gets the attribute value for BASE_UOM using the alias name BaseUom.
     * @return the BASE_UOM
     */
    public String getBaseUom() {
        return (String) getAttributeInternal(BASEUOM);
    }

    /**
     * Sets <code>value</code> as attribute value for BASE_UOM using the alias name BaseUom.
     * @param value value to set the BASE_UOM
     */
    public void setBaseUom(String value) {
        setAttributeInternal(BASEUOM, value);
    }

    /**
     * Gets the attribute value for CONV_FCTR using the alias name ConvFctr.
     * @return the CONV_FCTR
     */
    public oracle.jbo.domain.Number getConvFctr() {
        return (oracle.jbo.domain.Number) getAttributeInternal(CONVFCTR);
    }

    /**
     * Sets <code>value</code> as attribute value for CONV_FCTR using the alias name ConvFctr.
     * @param value value to set the CONV_FCTR
     */
    public void setConvFctr(oracle.jbo.domain.Number value) {
        if(value == null){
            value = new oracle.jbo.domain.Number(0);
        }
        setAttributeInternal(CONVFCTR, value);
    }

    /**
     * Gets the attribute value for ACTV using the alias name Actv.
     * @return the ACTV
     */
    public String getActv() {
        return (String) getAttributeInternal(ACTV);
    }

    /**
     * Sets <code>value</code> as attribute value for ACTV using the alias name Actv.
     * @param value value to set the ACTV
     */
    public void setActv(String value) {
        setAttributeInternal(ACTV, value);
    }

    /**
     * Gets the attribute value for INACTV_RESN using the alias name InactvResn.
     * @return the INACTV_RESN
     */
    public String getInactvResn() {
        return (String) getAttributeInternal(INACTVRESN);
    }

    /**
     * Sets <code>value</code> as attribute value for INACTV_RESN using the alias name InactvResn.
     * @param value value to set the INACTV_RESN
     */
    public void setInactvResn(String value) {
        setAttributeInternal(INACTVRESN, value);
    }

    /**
     * Gets the attribute value for INACTV_DT using the alias name InactvDt.
     * @return the INACTV_DT
     */
    public Date getInactvDt() {
        return (Date) getAttributeInternal(INACTVDT);
    }

    /**
     * Sets <code>value</code> as attribute value for INACTV_DT using the alias name InactvDt.
     * @param value value to set the INACTV_DT
     */
    public void setInactvDt(Date value) {
        setAttributeInternal(INACTVDT, value);
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
     * Gets the attribute value for UOM_STD_ENT_ID using the alias name UomStdEntId.
     * @return the UOM_STD_ENT_ID
     */
    public Integer getUomStdEntId() {
        return (Integer) getAttributeInternal(UOMSTDENTID);
    }

    /**
     * Sets <code>value</code> as attribute value for UOM_STD_ENT_ID using the alias name UomStdEntId.
     * @param value value to set the UOM_STD_ENT_ID
     */
    public void setUomStdEntId(Integer value) {
        setAttributeInternal(UOMSTDENTID, value);
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
     * Gets the view accessor <code>RowSet</code> LovuomClassidVO1.
     */
    public RowSet getLovuomClassidVO1() {
        return (RowSet)getAttributeInternal(LOVUOMCLASSIDVO1);
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
