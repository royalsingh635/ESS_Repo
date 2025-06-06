package slssalesinvoiceapp.model.module.entity;

import java.math.BigDecimal;

import java.math.BigInteger;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Sep 13 12:47:25 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class SlsInvOcEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CldId {
            public Object get(SlsInvOcEOImpl obj) {
                return obj.getCldId();
            }

            public void put(SlsInvOcEOImpl obj, Object value) {
                obj.setCldId((String)value);
            }
        }
        ,
        SlocId {
            public Object get(SlsInvOcEOImpl obj) {
                return obj.getSlocId();
            }

            public void put(SlsInvOcEOImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        OrgId {
            public Object get(SlsInvOcEOImpl obj) {
                return obj.getOrgId();
            }

            public void put(SlsInvOcEOImpl obj, Object value) {
                obj.setOrgId((String)value);
            }
        }
        ,
        HoOrgId {
            public Object get(SlsInvOcEOImpl obj) {
                return obj.getHoOrgId();
            }

            public void put(SlsInvOcEOImpl obj, Object value) {
                obj.setHoOrgId((String)value);
            }
        }
        ,
        DocId {
            public Object get(SlsInvOcEOImpl obj) {
                return obj.getDocId();
            }

            public void put(SlsInvOcEOImpl obj, Object value) {
                obj.setDocId((String)value);
            }
        }
        ,
        CoaId {
            public Object get(SlsInvOcEOImpl obj) {
                return obj.getCoaId();
            }

            public void put(SlsInvOcEOImpl obj, Object value) {
                obj.setCoaId((Integer)value);
            }
        }
        ,
        AmtSp {
            public Object get(SlsInvOcEOImpl obj) {
                return obj.getAmtSp();
            }

            public void put(SlsInvOcEOImpl obj, Object value) {
                obj.setAmtSp((BigDecimal)value);
            }
        }
        ,
        AmtBs {
            public Object get(SlsInvOcEOImpl obj) {
                return obj.getAmtBs();
            }

            public void put(SlsInvOcEOImpl obj, Object value) {
                obj.setAmtBs((BigDecimal)value);
            }
        }
        ,
        OcDesc {
            public Object get(SlsInvOcEOImpl obj) {
                return obj.getOcDesc();
            }

            public void put(SlsInvOcEOImpl obj, Object value) {
                obj.setOcDesc((Integer)value);
            }
        }
        ,
        CurrId {
            public Object get(SlsInvOcEOImpl obj) {
                return obj.getCurrId();
            }

            public void put(SlsInvOcEOImpl obj, Object value) {
                obj.setCurrId((Integer)value);
            }
        }
        ,
        CurrRate {
            public Object get(SlsInvOcEOImpl obj) {
                return obj.getCurrRate();
            }

            public void put(SlsInvOcEOImpl obj, Object value) {
                obj.setCurrRate((BigDecimal)value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(SlsInvOcEOImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(SlsInvOcEOImpl obj, Object value) {
                obj.setUsrIdCreate((Integer)value);
            }
        }
        ,
        UsrIdCreateDt {
            public Object get(SlsInvOcEOImpl obj) {
                return obj.getUsrIdCreateDt();
            }

            public void put(SlsInvOcEOImpl obj, Object value) {
                obj.setUsrIdCreateDt((Timestamp)value);
            }
        }
        ,
        UsrIdMod {
            public Object get(SlsInvOcEOImpl obj) {
                return obj.getUsrIdMod();
            }

            public void put(SlsInvOcEOImpl obj, Object value) {
                obj.setUsrIdMod((Integer)value);
            }
        }
        ,
        UsrIdModDt {
            public Object get(SlsInvOcEOImpl obj) {
                return obj.getUsrIdModDt();
            }

            public void put(SlsInvOcEOImpl obj, Object value) {
                obj.setUsrIdModDt((Timestamp)value);
            }
        }
        ,
        TranType {
            public Object get(SlsInvOcEOImpl obj) {
                return obj.getTranType();
            }

            public void put(SlsInvOcEOImpl obj, Object value) {
                obj.setTranType((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(SlsInvOcEOImpl object);

        public abstract void put(SlsInvOcEOImpl object, Object value);

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
    public static final int COAID = AttributesEnum.CoaId.index();
    public static final int AMTSP = AttributesEnum.AmtSp.index();
    public static final int AMTBS = AttributesEnum.AmtBs.index();
    public static final int OCDESC = AttributesEnum.OcDesc.index();
    public static final int CURRID = AttributesEnum.CurrId.index();
    public static final int CURRRATE = AttributesEnum.CurrRate.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int TRANTYPE = AttributesEnum.TranType.index();

    /**
     * This is the default constructor (do not remove).
     */
    public SlsInvOcEOImpl() {
    }


    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("slssalesinvoiceapp.model.module.entity.SlsInvOcEO");
    }

    /**
     * Gets the attribute value for CldId, using the alias name CldId.
     * @return the value of CldId
     */
    public String getCldId() {
        return (String)getAttributeInternal(CLDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CldId.
     * @param value value to set the CldId
     */
    public void setCldId(String value) {
        setAttributeInternal(CLDID, value);
    }

    /**
     * Gets the attribute value for SlocId, using the alias name SlocId.
     * @return the value of SlocId
     */
    public Integer getSlocId() {
        return (Integer)getAttributeInternal(SLOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for SlocId.
     * @param value value to set the SlocId
     */
    public void setSlocId(Integer value) {
        setAttributeInternal(SLOCID, value);
    }

    /**
     * Gets the attribute value for OrgId, using the alias name OrgId.
     * @return the value of OrgId
     */
    public String getOrgId() {
        return (String)getAttributeInternal(ORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for OrgId.
     * @param value value to set the OrgId
     */
    public void setOrgId(String value) {
        setAttributeInternal(ORGID, value);
    }

    /**
     * Gets the attribute value for HoOrgId, using the alias name HoOrgId.
     * @return the value of HoOrgId
     */
    public String getHoOrgId() {
        return (String)getAttributeInternal(HOORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for HoOrgId.
     * @param value value to set the HoOrgId
     */
    public void setHoOrgId(String value) {
        setAttributeInternal(HOORGID, value);
    }

    /**
     * Gets the attribute value for DocId, using the alias name DocId.
     * @return the value of DocId
     */
    public String getDocId() {
        return (String)getAttributeInternal(DOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for DocId.
     * @param value value to set the DocId
     */
    public void setDocId(String value) {
        setAttributeInternal(DOCID, value);
    }

    /**
     * Gets the attribute value for CoaId, using the alias name CoaId.
     * @return the value of CoaId
     */
    public Integer getCoaId() {
        return (Integer)getAttributeInternal(COAID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CoaId.
     * @param value value to set the CoaId
     */
    public void setCoaId(Integer value) {
        setAttributeInternal(COAID, value);
    }

    /**
     * Gets the attribute value for AmtSp, using the alias name AmtSp.
     * @return the value of AmtSp
     */
    public BigDecimal getAmtSp() {
        return (BigDecimal)getAttributeInternal(AMTSP);
    }

    /**
     * Sets <code>value</code> as the attribute value for AmtSp.
     * @param value value to set the AmtSp
     */
    public void setAmtSp(BigDecimal value) {
        
        setAttributeInternal(AMTSP, value);
    }

    /**
     * Gets the attribute value for AmtBs, using the alias name AmtBs.
     * @return the value of AmtBs
     */
    public BigDecimal getAmtBs() {
        return (BigDecimal)getAttributeInternal(AMTBS);
    }

    /**
     * Sets <code>value</code> as the attribute value for AmtBs.
     * @param value value to set the AmtBs
     */
    public void setAmtBs(BigDecimal value) {
        setAttributeInternal(AMTBS, value);
    }

    /**
     * Gets the attribute value for OcDesc, using the alias name OcDesc.
     * @return the value of OcDesc
     */
    public Integer getOcDesc() {
        return (Integer)getAttributeInternal(OCDESC);
    }

    /**
     * Sets <code>value</code> as the attribute value for OcDesc.
     * @param value value to set the OcDesc
     */
    public void setOcDesc(Integer value) {
        setAttributeInternal(OCDESC, value);
    }

    /**
     * Gets the attribute value for CurrId, using the alias name CurrId.
     * @return the value of CurrId
     */
    public Integer getCurrId() {
        return (Integer)getAttributeInternal(CURRID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CurrId.
     * @param value value to set the CurrId
     */
    public void setCurrId(Integer value) {
        if(value != null){
            setAttributeInternal(CURRID, value);    
        }
    }

    /**
     * Gets the attribute value for CurrRate, using the alias name CurrRate.
     * @return the value of CurrRate
     */
    public BigDecimal getCurrRate() {
        return (BigDecimal)getAttributeInternal(CURRRATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for CurrRate.
     * @param value value to set the CurrRate
     */
    public void setCurrRate(BigDecimal value) {
        setAttributeInternal(CURRRATE, value);
    }

    /**
     * Gets the attribute value for UsrIdCreate, using the alias name UsrIdCreate.
     * @return the value of UsrIdCreate
     */
    public Integer getUsrIdCreate() {
        return (Integer)getAttributeInternal(USRIDCREATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdCreate.
     * @param value value to set the UsrIdCreate
     */
    public void setUsrIdCreate(Integer value) {
        setAttributeInternal(USRIDCREATE, value);
    }

    /**
     * Gets the attribute value for UsrIdCreateDt, using the alias name UsrIdCreateDt.
     * @return the value of UsrIdCreateDt
     */
    public Timestamp getUsrIdCreateDt() {
        return (Timestamp)getAttributeInternal(USRIDCREATEDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdCreateDt.
     * @param value value to set the UsrIdCreateDt
     */
    public void setUsrIdCreateDt(Timestamp value) {
        setAttributeInternal(USRIDCREATEDT, value);
    }

    /**
     * Gets the attribute value for UsrIdMod, using the alias name UsrIdMod.
     * @return the value of UsrIdMod
     */
    public Integer getUsrIdMod() {
        return (Integer)getAttributeInternal(USRIDMOD);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdMod.
     * @param value value to set the UsrIdMod
     */
    public void setUsrIdMod(Integer value) {
        setAttributeInternal(USRIDMOD, value);
    }

    /**
     * Gets the attribute value for UsrIdModDt, using the alias name UsrIdModDt.
     * @return the value of UsrIdModDt
     */
    public Timestamp getUsrIdModDt() {
        return (Timestamp)getAttributeInternal(USRIDMODDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdModDt.
     * @param value value to set the UsrIdModDt
     */
    public void setUsrIdModDt(Timestamp value) {
        setAttributeInternal(USRIDMODDT, value);
    }

    /**
     * Gets the attribute value for TranType, using the alias name TranType.
     * @return the value of TranType
     */
    public String getTranType() {
        return (String)getAttributeInternal(TRANTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for TranType.
     * @param value value to set the TranType
     */
    public void setTranType(String value) {
        setAttributeInternal(TRANTYPE, value);
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


    /**
     * @param cldId key constituent
     * @param slocId key constituent
     * @param orgId key constituent
     * @param hoOrgId key constituent
     * @param docId key constituent
     * @param coaId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String cldId, Integer slocId, String orgId, String hoOrgId, String docId,
                                       Integer coaId) {
        return new Key(new Object[]{cldId, slocId, orgId, hoOrgId, docId, coaId});
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        super.create(attributeList);

        setUsrIdCreateDt(new Timestamp(System.currentTimeMillis()));
        Integer UsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        setUsrIdCreate(UsrId);
    }

    /**
     * Add locking logic here.
     */
    public void lock() {
        //super.lock();
    }

    /**
     * Custom DML update/insert/delete logic here.
     * @param operation the operation type
     * @param e the transaction event
     */
    protected void doDML(int operation, TransactionEvent e) {

        if (operation == DML_UPDATE) {
            setUsrIdModDt(new Timestamp(System.currentTimeMillis()));
            Integer UsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
            setUsrIdMod(UsrId);
        }

        super.doDML(operation, e);
    }


    public Object resolvEl(Object data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, (String)data, Object.class);
        Object Message = valueExp.getValue(elContext);
        return Message;
    }
}
