package externalentityprofilesetup.model.entities;

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
// ---    Tue Nov 19 11:07:47 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppEeMstPrfEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        EeMstPrfCldId {
            public Object get(AppEeMstPrfEOImpl obj) {
                return obj.getEeMstPrfCldId();
            }

            public void put(AppEeMstPrfEOImpl obj, Object value) {
                obj.setEeMstPrfCldId((String)value);
            }
        }
        ,
        EeMstPrfSlocId {
            public Object get(AppEeMstPrfEOImpl obj) {
                return obj.getEeMstPrfSlocId();
            }

            public void put(AppEeMstPrfEOImpl obj, Object value) {
                obj.setEeMstPrfSlocId((Integer)value);
            }
        }
        ,
        EeMstAlwAlias {
            public Object get(AppEeMstPrfEOImpl obj) {
                return obj.getEeMstAlwAlias();
            }

            public void put(AppEeMstPrfEOImpl obj, Object value) {
                obj.setEeMstAlwAlias((String)value);
            }
        }
        ,
        EeMstAliasLen {
            public Object get(AppEeMstPrfEOImpl obj) {
                return obj.getEeMstAliasLen();
            }

            public void put(AppEeMstPrfEOImpl obj, Object value) {
                obj.setEeMstAliasLen((Integer)value);
            }
        }
        ,
        EeMstAliasMan {
            public Object get(AppEeMstPrfEOImpl obj) {
                return obj.getEeMstAliasMan();
            }

            public void put(AppEeMstPrfEOImpl obj, Object value) {
                obj.setEeMstAliasMan((String)value);
            }
        }
        ,
        EeMstAliasChkUnq {
            public Object get(AppEeMstPrfEOImpl obj) {
                return obj.getEeMstAliasChkUnq();
            }

            public void put(AppEeMstPrfEOImpl obj, Object value) {
                obj.setEeMstAliasChkUnq((String)value);
            }
        }
        ,
        EeMstAliasRepl {
            public Object get(AppEeMstPrfEOImpl obj) {
                return obj.getEeMstAliasRepl();
            }

            public void put(AppEeMstPrfEOImpl obj, Object value) {
                obj.setEeMstAliasRepl((String)value);
            }
        }
        ,
        EeMstAliasDt {
            public Object get(AppEeMstPrfEOImpl obj) {
                return obj.getEeMstAliasDt();
            }

            public void put(AppEeMstPrfEOImpl obj, Object value) {
                obj.setEeMstAliasDt((Integer)value);
            }
        }
        ,
        EeMstAlwLegCd {
            public Object get(AppEeMstPrfEOImpl obj) {
                return obj.getEeMstAlwLegCd();
            }

            public void put(AppEeMstPrfEOImpl obj, Object value) {
                obj.setEeMstAlwLegCd((String)value);
            }
        }
        ,
        EeMstLegCdLen {
            public Object get(AppEeMstPrfEOImpl obj) {
                return obj.getEeMstLegCdLen();
            }

            public void put(AppEeMstPrfEOImpl obj, Object value) {
                obj.setEeMstLegCdLen((Integer)value);
            }
        }
        ,
        EeMstLegCdMan {
            public Object get(AppEeMstPrfEOImpl obj) {
                return obj.getEeMstLegCdMan();
            }

            public void put(AppEeMstPrfEOImpl obj, Object value) {
                obj.setEeMstLegCdMan((String)value);
            }
        }
        ,
        EeMstLegCdChkUnq {
            public Object get(AppEeMstPrfEOImpl obj) {
                return obj.getEeMstLegCdChkUnq();
            }

            public void put(AppEeMstPrfEOImpl obj, Object value) {
                obj.setEeMstLegCdChkUnq((String)value);
            }
        }
        ,
        EeMstLegCdRepl {
            public Object get(AppEeMstPrfEOImpl obj) {
                return obj.getEeMstLegCdRepl();
            }

            public void put(AppEeMstPrfEOImpl obj, Object value) {
                obj.setEeMstLegCdRepl((String)value);
            }
        }
        ,
        EeMstLegCdDt {
            public Object get(AppEeMstPrfEOImpl obj) {
                return obj.getEeMstLegCdDt();
            }

            public void put(AppEeMstPrfEOImpl obj, Object value) {
                obj.setEeMstLegCdDt((Integer)value);
            }
        }
        ,
        EeRepRule {
            public Object get(AppEeMstPrfEOImpl obj) {
                return obj.getEeRepRule();
            }

            public void put(AppEeMstPrfEOImpl obj, Object value) {
                obj.setEeRepRule((String)value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(AppEeMstPrfEOImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(AppEeMstPrfEOImpl obj, Object value) {
                obj.setUsrIdCreate((Integer)value);
            }
        }
        ,
        UsrIdCreateDt {
            public Object get(AppEeMstPrfEOImpl obj) {
                return obj.getUsrIdCreateDt();
            }

            public void put(AppEeMstPrfEOImpl obj, Object value) {
                obj.setUsrIdCreateDt((Timestamp)value);
            }
        }
        ,
        UsrIdMod {
            public Object get(AppEeMstPrfEOImpl obj) {
                return obj.getUsrIdMod();
            }

            public void put(AppEeMstPrfEOImpl obj, Object value) {
                obj.setUsrIdMod((Integer)value);
            }
        }
        ,
        UsrIdModDt {
            public Object get(AppEeMstPrfEOImpl obj) {
                return obj.getUsrIdModDt();
            }

            public void put(AppEeMstPrfEOImpl obj, Object value) {
                obj.setUsrIdModDt((Timestamp)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(AppEeMstPrfEOImpl object);

        public abstract void put(AppEeMstPrfEOImpl object, Object value);

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
    public static final int EEMSTPRFCLDID = AttributesEnum.EeMstPrfCldId.index();
    public static final int EEMSTPRFSLOCID = AttributesEnum.EeMstPrfSlocId.index();
    public static final int EEMSTALWALIAS = AttributesEnum.EeMstAlwAlias.index();
    public static final int EEMSTALIASLEN = AttributesEnum.EeMstAliasLen.index();
    public static final int EEMSTALIASMAN = AttributesEnum.EeMstAliasMan.index();
    public static final int EEMSTALIASCHKUNQ = AttributesEnum.EeMstAliasChkUnq.index();
    public static final int EEMSTALIASREPL = AttributesEnum.EeMstAliasRepl.index();
    public static final int EEMSTALIASDT = AttributesEnum.EeMstAliasDt.index();
    public static final int EEMSTALWLEGCD = AttributesEnum.EeMstAlwLegCd.index();
    public static final int EEMSTLEGCDLEN = AttributesEnum.EeMstLegCdLen.index();
    public static final int EEMSTLEGCDMAN = AttributesEnum.EeMstLegCdMan.index();
    public static final int EEMSTLEGCDCHKUNQ = AttributesEnum.EeMstLegCdChkUnq.index();
    public static final int EEMSTLEGCDREPL = AttributesEnum.EeMstLegCdRepl.index();
    public static final int EEMSTLEGCDDT = AttributesEnum.EeMstLegCdDt.index();
    public static final int EEREPRULE = AttributesEnum.EeRepRule.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();

    /**
     * This is the default constructor (do not remove).
     */
    public AppEeMstPrfEOImpl() {
    }

    /**
     * Gets the attribute value for EeMstPrfCldId, using the alias name EeMstPrfCldId.
     * @return the value of EeMstPrfCldId
     */
    public String getEeMstPrfCldId() {
        return (String)getAttributeInternal(EEMSTPRFCLDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for EeMstPrfCldId.
     * @param value value to set the EeMstPrfCldId
     */
    public void setEeMstPrfCldId(String value) {
        setAttributeInternal(EEMSTPRFCLDID, value);
    }

    /**
     * Gets the attribute value for EeMstPrfSlocId, using the alias name EeMstPrfSlocId.
     * @return the value of EeMstPrfSlocId
     */
    public Integer getEeMstPrfSlocId() {
        return (Integer)getAttributeInternal(EEMSTPRFSLOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for EeMstPrfSlocId.
     * @param value value to set the EeMstPrfSlocId
     */
    public void setEeMstPrfSlocId(Integer value) {
        setAttributeInternal(EEMSTPRFSLOCID, value);
    }

    /**
     * Gets the attribute value for EeMstAlwAlias, using the alias name EeMstAlwAlias.
     * @return the value of EeMstAlwAlias
     */
    public String getEeMstAlwAlias() {
        return (String)getAttributeInternal(EEMSTALWALIAS);
    }

    /**
     * Sets <code>value</code> as the attribute value for EeMstAlwAlias.
     * @param value value to set the EeMstAlwAlias
     */
    public void setEeMstAlwAlias(String value) {
        setAttributeInternal(EEMSTALWALIAS, value);
    }

    /**
     * Gets the attribute value for EeMstAliasLen, using the alias name EeMstAliasLen.
     * @return the value of EeMstAliasLen
     */
    public Integer getEeMstAliasLen() {
        return (Integer)getAttributeInternal(EEMSTALIASLEN);
    }

    /**
     * Sets <code>value</code> as the attribute value for EeMstAliasLen.
     * @param value value to set the EeMstAliasLen
     */
    public void setEeMstAliasLen(Integer value) {
        setAttributeInternal(EEMSTALIASLEN, value);
    }

    /**
     * Gets the attribute value for EeMstAliasMan, using the alias name EeMstAliasMan.
     * @return the value of EeMstAliasMan
     */
    public String getEeMstAliasMan() {
        return (String)getAttributeInternal(EEMSTALIASMAN);
    }

    /**
     * Sets <code>value</code> as the attribute value for EeMstAliasMan.
     * @param value value to set the EeMstAliasMan
     */
    public void setEeMstAliasMan(String value) {
        setAttributeInternal(EEMSTALIASMAN, value);
    }

    /**
     * Gets the attribute value for EeMstAliasChkUnq, using the alias name EeMstAliasChkUnq.
     * @return the value of EeMstAliasChkUnq
     */
    public String getEeMstAliasChkUnq() {
        return (String)getAttributeInternal(EEMSTALIASCHKUNQ);
    }

    /**
     * Sets <code>value</code> as the attribute value for EeMstAliasChkUnq.
     * @param value value to set the EeMstAliasChkUnq
     */
    public void setEeMstAliasChkUnq(String value) {
        setAttributeInternal(EEMSTALIASCHKUNQ, value);
    }

    /**
     * Gets the attribute value for EeMstAliasRepl, using the alias name EeMstAliasRepl.
     * @return the value of EeMstAliasRepl
     */
    public String getEeMstAliasRepl() {
        return (String)getAttributeInternal(EEMSTALIASREPL);
    }

    /**
     * Sets <code>value</code> as the attribute value for EeMstAliasRepl.
     * @param value value to set the EeMstAliasRepl
     */
    public void setEeMstAliasRepl(String value) {
        setAttributeInternal(EEMSTALIASREPL, value);
    }

    /**
     * Gets the attribute value for EeMstAliasDt, using the alias name EeMstAliasDt.
     * @return the value of EeMstAliasDt
     */
    public Integer getEeMstAliasDt() {
        return (Integer)getAttributeInternal(EEMSTALIASDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for EeMstAliasDt.
     * @param value value to set the EeMstAliasDt
     */
    public void setEeMstAliasDt(Integer value) {
        setAttributeInternal(EEMSTALIASDT, value);
    }

    /**
     * Gets the attribute value for EeMstAlwLegCd, using the alias name EeMstAlwLegCd.
     * @return the value of EeMstAlwLegCd
     */
    public String getEeMstAlwLegCd() {
        return (String)getAttributeInternal(EEMSTALWLEGCD);
    }

    /**
     * Sets <code>value</code> as the attribute value for EeMstAlwLegCd.
     * @param value value to set the EeMstAlwLegCd
     */
    public void setEeMstAlwLegCd(String value) {
        setAttributeInternal(EEMSTALWLEGCD, value);
    }

    /**
     * Gets the attribute value for EeMstLegCdLen, using the alias name EeMstLegCdLen.
     * @return the value of EeMstLegCdLen
     */
    public Integer getEeMstLegCdLen() {
        return (Integer)getAttributeInternal(EEMSTLEGCDLEN);
    }

    /**
     * Sets <code>value</code> as the attribute value for EeMstLegCdLen.
     * @param value value to set the EeMstLegCdLen
     */
    public void setEeMstLegCdLen(Integer value) {
        setAttributeInternal(EEMSTLEGCDLEN, value);
    }

    /**
     * Gets the attribute value for EeMstLegCdMan, using the alias name EeMstLegCdMan.
     * @return the value of EeMstLegCdMan
     */
    public String getEeMstLegCdMan() {
        return (String)getAttributeInternal(EEMSTLEGCDMAN);
    }

    /**
     * Sets <code>value</code> as the attribute value for EeMstLegCdMan.
     * @param value value to set the EeMstLegCdMan
     */
    public void setEeMstLegCdMan(String value) {
        setAttributeInternal(EEMSTLEGCDMAN, value);
    }

    /**
     * Gets the attribute value for EeMstLegCdChkUnq, using the alias name EeMstLegCdChkUnq.
     * @return the value of EeMstLegCdChkUnq
     */
    public String getEeMstLegCdChkUnq() {
        return (String)getAttributeInternal(EEMSTLEGCDCHKUNQ);
    }

    /**
     * Sets <code>value</code> as the attribute value for EeMstLegCdChkUnq.
     * @param value value to set the EeMstLegCdChkUnq
     */
    public void setEeMstLegCdChkUnq(String value) {
        setAttributeInternal(EEMSTLEGCDCHKUNQ, value);
    }

    /**
     * Gets the attribute value for EeMstLegCdRepl, using the alias name EeMstLegCdRepl.
     * @return the value of EeMstLegCdRepl
     */
    public String getEeMstLegCdRepl() {
        return (String)getAttributeInternal(EEMSTLEGCDREPL);
    }

    /**
     * Sets <code>value</code> as the attribute value for EeMstLegCdRepl.
     * @param value value to set the EeMstLegCdRepl
     */
    public void setEeMstLegCdRepl(String value) {
        setAttributeInternal(EEMSTLEGCDREPL, value);
    }

    /**
     * Gets the attribute value for EeMstLegCdDt, using the alias name EeMstLegCdDt.
     * @return the value of EeMstLegCdDt
     */
    public Integer getEeMstLegCdDt() {
        return (Integer)getAttributeInternal(EEMSTLEGCDDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for EeMstLegCdDt.
     * @param value value to set the EeMstLegCdDt
     */
    public void setEeMstLegCdDt(Integer value) {
        setAttributeInternal(EEMSTLEGCDDT, value);
    }

    /**
     * Gets the attribute value for EeRepRule, using the alias name EeRepRule.
     * @return the value of EeRepRule
     */
    public String getEeRepRule() {
        return (String)getAttributeInternal(EEREPRULE);
    }

    /**
     * Sets <code>value</code> as the attribute value for EeRepRule.
     * @param value value to set the EeRepRule
     */
    public void setEeRepRule(String value) {
        setAttributeInternal(EEREPRULE, value);
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
     * @param eeMstPrfCldId key constituent
     * @param eeMstPrfSlocId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String eeMstPrfCldId, Integer eeMstPrfSlocId) {
        return new Key(new Object[]{eeMstPrfCldId, eeMstPrfSlocId});
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("externalentityprofilesetup.model.entities.AppEeMstPrfEO");
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
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        Integer UserId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        //System.out.println("------------------------> "+ UserId);
        setUsrIdCreate(UserId);
        setUsrIdCreateDt((new Timestamp(System.currentTimeMillis())));
        
        String glbl_cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer glbl_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        setEeMstPrfCldId(glbl_cld_id);
        setEeMstPrfSlocId(glbl_sloc_id);
        //setEeCldId(glbl_cld_id);
        //setOrgId(glbl_org_id);
        //setEeSlocId(glbl_sloc_id);
        
        
        super.create(attributeList);
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
        
        if(operation == DML_UPDATE){
            Integer UserId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
            setUsrIdMod(UserId);
            setUsrIdModDt((new Timestamp(System.currentTimeMillis())));
        }
        
        super.doDML(operation, e);
    }
}
