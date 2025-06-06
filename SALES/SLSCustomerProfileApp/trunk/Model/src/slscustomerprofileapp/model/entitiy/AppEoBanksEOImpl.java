package slscustomerprofileapp.model.entitiy;

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
// ---    Tue Sep 24 13:01:22 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppEoBanksEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        SlocId {
            public Object get(AppEoBanksEOImpl obj) {
                return obj.getSlocId();
            }

            public void put(AppEoBanksEOImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        EoId {
            public Object get(AppEoBanksEOImpl obj) {
                return obj.getEoId();
            }

            public void put(AppEoBanksEOImpl obj, Object value) {
                obj.setEoId((Integer)value);
            }
        }
        ,
        BankAcNo {
            public Object get(AppEoBanksEOImpl obj) {
                return obj.getBankAcNo();
            }

            public void put(AppEoBanksEOImpl obj, Object value) {
                obj.setBankAcNo((String)value);
            }
        }
        ,
        BankNm {
            public Object get(AppEoBanksEOImpl obj) {
                return obj.getBankNm();
            }

            public void put(AppEoBanksEOImpl obj, Object value) {
                obj.setBankNm((String)value);
            }
        }
        ,
        AddsId {
            public Object get(AppEoBanksEOImpl obj) {
                return obj.getAddsId();
            }

            public void put(AppEoBanksEOImpl obj, Object value) {
                obj.setAddsId((String)value);
            }
        }
        ,
        Actv {
            public Object get(AppEoBanksEOImpl obj) {
                return obj.getActv();
            }

            public void put(AppEoBanksEOImpl obj, Object value) {
                obj.setActv((String)value);
            }
        }
        ,
        InactvResn {
            public Object get(AppEoBanksEOImpl obj) {
                return obj.getInactvResn();
            }

            public void put(AppEoBanksEOImpl obj, Object value) {
                obj.setInactvResn((String)value);
            }
        }
        ,
        InactvDt {
            public Object get(AppEoBanksEOImpl obj) {
                return obj.getInactvDt();
            }

            public void put(AppEoBanksEOImpl obj, Object value) {
                obj.setInactvDt((Timestamp)value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(AppEoBanksEOImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(AppEoBanksEOImpl obj, Object value) {
                obj.setUsrIdCreate((Integer)value);
            }
        }
        ,
        UsrIdCreateDt {
            public Object get(AppEoBanksEOImpl obj) {
                return obj.getUsrIdCreateDt();
            }

            public void put(AppEoBanksEOImpl obj, Object value) {
                obj.setUsrIdCreateDt((Timestamp)value);
            }
        }
        ,
        UsrIdMod {
            public Object get(AppEoBanksEOImpl obj) {
                return obj.getUsrIdMod();
            }

            public void put(AppEoBanksEOImpl obj, Object value) {
                obj.setUsrIdMod((Integer)value);
            }
        }
        ,
        UsrIdModDt {
            public Object get(AppEoBanksEOImpl obj) {
                return obj.getUsrIdModDt();
            }

            public void put(AppEoBanksEOImpl obj, Object value) {
                obj.setUsrIdModDt((Timestamp)value);
            }
        }
        ,
        EoOrgId {
            public Object get(AppEoBanksEOImpl obj) {
                return obj.getEoOrgId();
            }

            public void put(AppEoBanksEOImpl obj, Object value) {
                obj.setEoOrgId((String)value);
            }
        }
        ,
        EoHoOrgId {
            public Object get(AppEoBanksEOImpl obj) {
                return obj.getEoHoOrgId();
            }

            public void put(AppEoBanksEOImpl obj, Object value) {
                obj.setEoHoOrgId((String)value);
            }
        }
        ,
        EoCldId {
            public Object get(AppEoBanksEOImpl obj) {
                return obj.getEoCldId();
            }

            public void put(AppEoBanksEOImpl obj, Object value) {
                obj.setEoCldId((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(AppEoBanksEOImpl object);

        public abstract void put(AppEoBanksEOImpl object, Object value);

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
    public static final int EOID = AttributesEnum.EoId.index();
    public static final int BANKACNO = AttributesEnum.BankAcNo.index();
    public static final int BANKNM = AttributesEnum.BankNm.index();
    public static final int ADDSID = AttributesEnum.AddsId.index();
    public static final int ACTV = AttributesEnum.Actv.index();
    public static final int INACTVRESN = AttributesEnum.InactvResn.index();
    public static final int INACTVDT = AttributesEnum.InactvDt.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int EOORGID = AttributesEnum.EoOrgId.index();
    public static final int EOHOORGID = AttributesEnum.EoHoOrgId.index();
    public static final int EOCLDID = AttributesEnum.EoCldId.index();

    /**
     * This is the default constructor (do not remove).
     */
    public AppEoBanksEOImpl() {
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
     * Gets the attribute value for EoId, using the alias name EoId.
     * @return the value of EoId
     */
    public Integer getEoId() {
        return (Integer)getAttributeInternal(EOID);
    }

    /**
     * Sets <code>value</code> as the attribute value for EoId.
     * @param value value to set the EoId
     */
    public void setEoId(Integer value) {
        setAttributeInternal(EOID, value);
    }

    /**
     * Gets the attribute value for BankAcNo, using the alias name BankAcNo.
     * @return the value of BankAcNo
     */
    public String getBankAcNo() {
        return (String)getAttributeInternal(BANKACNO);
    }

    /**
     * Sets <code>value</code> as the attribute value for BankAcNo.
     * @param value value to set the BankAcNo
     */
    public void setBankAcNo(String value) {
        setAttributeInternal(BANKACNO, value);
    }

    /**
     * Gets the attribute value for BankNm, using the alias name BankNm.
     * @return the value of BankNm
     */
    public String getBankNm() {
        return (String)getAttributeInternal(BANKNM);
    }

    /**
     * Sets <code>value</code> as the attribute value for BankNm.
     * @param value value to set the BankNm
     */
    public void setBankNm(String value) {
        setAttributeInternal(BANKNM, value);
    }

    /**
     * Gets the attribute value for AddsId, using the alias name AddsId.
     * @return the value of AddsId
     */
    public String getAddsId() {
        return (String)getAttributeInternal(ADDSID);
    }

    /**
     * Sets <code>value</code> as the attribute value for AddsId.
     * @param value value to set the AddsId
     */
    public void setAddsId(String value) {
        setAttributeInternal(ADDSID, value);
    }

    /**
     * Gets the attribute value for Actv, using the alias name Actv.
     * @return the value of Actv
     */
    public String getActv() {
        return (String)getAttributeInternal(ACTV);
    }

    /**
     * Sets <code>value</code> as the attribute value for Actv.
     * @param value value to set the Actv
     */
    public void setActv(String value) {
        setAttributeInternal(ACTV, value);
    }

    /**
     * Gets the attribute value for InactvResn, using the alias name InactvResn.
     * @return the value of InactvResn
     */
    public String getInactvResn() {
        return (String)getAttributeInternal(INACTVRESN);
    }

    /**
     * Sets <code>value</code> as the attribute value for InactvResn.
     * @param value value to set the InactvResn
     */
    public void setInactvResn(String value) {
        setAttributeInternal(INACTVRESN, value);
    }

    /**
     * Gets the attribute value for InactvDt, using the alias name InactvDt.
     * @return the value of InactvDt
     */
    public Timestamp getInactvDt() {
        return (Timestamp)getAttributeInternal(INACTVDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for InactvDt.
     * @param value value to set the InactvDt
     */
    public void setInactvDt(Timestamp value) {
        setAttributeInternal(INACTVDT, value);
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
     * Gets the attribute value for EoOrgId, using the alias name EoOrgId.
     * @return the value of EoOrgId
     */
    public String getEoOrgId() {
        return (String)getAttributeInternal(EOORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for EoOrgId.
     * @param value value to set the EoOrgId
     */
    public void setEoOrgId(String value) {
        setAttributeInternal(EOORGID, value);
    }

    /**
     * Gets the attribute value for EoHoOrgId, using the alias name EoHoOrgId.
     * @return the value of EoHoOrgId
     */
    public String getEoHoOrgId() {
        return (String)getAttributeInternal(EOHOORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for EoHoOrgId.
     * @param value value to set the EoHoOrgId
     */
    public void setEoHoOrgId(String value) {
        setAttributeInternal(EOHOORGID, value);
    }

    /**
     * Gets the attribute value for EoCldId, using the alias name EoCldId.
     * @return the value of EoCldId
     */
    public String getEoCldId() {
        return (String)getAttributeInternal(EOCLDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for EoCldId.
     * @param value value to set the EoCldId
     */
    public void setEoCldId(String value) {
        setAttributeInternal(EOCLDID, value);
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
     * @param slocId key constituent
     * @param eoId key constituent
     * @param bankAcNo key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(Integer slocId, Integer eoId, String bankAcNo) {
        return new Key(new Object[]{slocId, eoId, bankAcNo});
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("slscustomerprofileapp.model.entitiy.AppEoBanksEO");
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        Integer setuserid =Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}".toString()));
        System.out.println(setuserid);
        
        setUsrIdCreate(setuserid);
        setUsrIdCreateDt(new Timestamp(System.currentTimeMillis()));
        super.create(attributeList);
    }

    /**
     * Add entity remove logic in this method.
     */
    public void remove() {
        super.remove();
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
        if(operation==DML_UPDATE) {
            Integer setusermodid =Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}".toString()));
            System.out.println(setusermodid);
            
            setUsrIdMod(setusermodid);
            setUsrIdModDt(new Timestamp(System.currentTimeMillis()));
             
        }
        super.doDML(operation, e);
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
}
