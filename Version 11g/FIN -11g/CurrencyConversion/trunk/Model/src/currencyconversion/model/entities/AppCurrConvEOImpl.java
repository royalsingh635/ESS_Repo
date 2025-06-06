package currencyconversion.model.entities;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Jan 22 13:58:23 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppCurrConvEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CcCurrId {
            public Object get(AppCurrConvEOImpl obj) {
                return obj.getCcCurrId();
            }

            public void put(AppCurrConvEOImpl obj, Object value) {
                obj.setCcCurrId((Integer)value);
            }
        }
        ,
        CcCurrFlg {
            public Object get(AppCurrConvEOImpl obj) {
                return obj.getCcCurrFlg();
            }

            public void put(AppCurrConvEOImpl obj, Object value) {
                obj.setCcCurrFlg((String)value);
            }
        }
        ,
        CcCurrIdTxn {
            public Object get(AppCurrConvEOImpl obj) {
                return obj.getCcCurrIdTxn();
            }

            public void put(AppCurrConvEOImpl obj, Object value) {
                obj.setCcCurrIdTxn((Integer)value);
            }
        }
        ,
        CcEffDate {
            public Object get(AppCurrConvEOImpl obj) {
                return obj.getCcEffDate();
            }

            public void put(AppCurrConvEOImpl obj, Object value) {
                obj.setCcEffDate((Date)value);
            }
        }
        ,
        CcBuy {
            public Object get(AppCurrConvEOImpl obj) {
                return obj.getCcBuy();
            }

            public void put(AppCurrConvEOImpl obj, Object value) {
                obj.setCcBuy((Number)value);
            }
        }
        ,
        CcSell {
            public Object get(AppCurrConvEOImpl obj) {
                return obj.getCcSell();
            }

            public void put(AppCurrConvEOImpl obj, Object value) {
                obj.setCcSell((Number)value);
            }
        }
        ,
        CcBuyRev {
            public Object get(AppCurrConvEOImpl obj) {
                return obj.getCcBuyRev();
            }

            public void put(AppCurrConvEOImpl obj, Object value) {
                obj.setCcBuyRev((Number)value);
            }
        }
        ,
        CcSellRev {
            public Object get(AppCurrConvEOImpl obj) {
                return obj.getCcSellRev();
            }

            public void put(AppCurrConvEOImpl obj, Object value) {
                obj.setCcSellRev((Number)value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(AppCurrConvEOImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(AppCurrConvEOImpl obj, Object value) {
                obj.setUsrIdCreate((Integer)value);
            }
        }
        ,
        UsrIdCreateDt {
            public Object get(AppCurrConvEOImpl obj) {
                return obj.getUsrIdCreateDt();
            }

            public void put(AppCurrConvEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UsrIdMod {
            public Object get(AppCurrConvEOImpl obj) {
                return obj.getUsrIdMod();
            }

            public void put(AppCurrConvEOImpl obj, Object value) {
                obj.setUsrIdMod((Integer)value);
            }
        }
        ,
        UsrIdModDt {
            public Object get(AppCurrConvEOImpl obj) {
                return obj.getUsrIdModDt();
            }

            public void put(AppCurrConvEOImpl obj, Object value) {
                obj.setUsrIdModDt((Date)value);
            }
        }
        ,
        CcCldId {
            public Object get(AppCurrConvEOImpl obj) {
                return obj.getCcCldId();
            }

            public void put(AppCurrConvEOImpl obj, Object value) {
                obj.setCcCldId((String)value);
            }
        }
        ,
        CcHoOrgId {
            public Object get(AppCurrConvEOImpl obj) {
                return obj.getCcHoOrgId();
            }

            public void put(AppCurrConvEOImpl obj, Object value) {
                obj.setCcHoOrgId((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(AppCurrConvEOImpl object);

        public abstract void put(AppCurrConvEOImpl object, Object value);

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
    public static final int CCCURRID = AttributesEnum.CcCurrId.index();
    public static final int CCCURRFLG = AttributesEnum.CcCurrFlg.index();
    public static final int CCCURRIDTXN = AttributesEnum.CcCurrIdTxn.index();
    public static final int CCEFFDATE = AttributesEnum.CcEffDate.index();
    public static final int CCBUY = AttributesEnum.CcBuy.index();
    public static final int CCSELL = AttributesEnum.CcSell.index();
    public static final int CCBUYREV = AttributesEnum.CcBuyRev.index();
    public static final int CCSELLREV = AttributesEnum.CcSellRev.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int CCCLDID = AttributesEnum.CcCldId.index();
    public static final int CCHOORGID = AttributesEnum.CcHoOrgId.index();

    /**
     * This is the default constructor (do not remove).
     */
    public AppCurrConvEOImpl() {
    }

    /**
     * Gets the attribute value for CcCurrId, using the alias name CcCurrId.
     * @return the value of CcCurrId
     */
    public Integer getCcCurrId() {
        return (Integer)getAttributeInternal(CCCURRID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CcCurrId.
     * @param value value to set the CcCurrId
     */
    public void setCcCurrId(Integer value) {
        setAttributeInternal(CCCURRID, value);
    }

    /**
     * Gets the attribute value for CcCurrFlg, using the alias name CcCurrFlg.
     * @return the value of CcCurrFlg
     */
    public String getCcCurrFlg() {
        return (String)getAttributeInternal(CCCURRFLG);
    }

    /**
     * Sets <code>value</code> as the attribute value for CcCurrFlg.
     * @param value value to set the CcCurrFlg
     */
    public void setCcCurrFlg(String value) {
        setAttributeInternal(CCCURRFLG, value);
    }

    /**
     * Gets the attribute value for CcCurrIdTxn, using the alias name CcCurrIdTxn.
     * @return the value of CcCurrIdTxn
     */
    public Integer getCcCurrIdTxn() {
        return (Integer)getAttributeInternal(CCCURRIDTXN);
    }

    /**
     * Sets <code>value</code> as the attribute value for CcCurrIdTxn.
     * @param value value to set the CcCurrIdTxn
     */
    public void setCcCurrIdTxn(Integer value) {
        setAttributeInternal(CCCURRIDTXN, value);
    }

    /**
     * Gets the attribute value for CcEffDate, using the alias name CcEffDate.
     * @return the value of CcEffDate
     */
    public Date getCcEffDate() {
        return (Date)getAttributeInternal(CCEFFDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for CcEffDate.
     * @param value value to set the CcEffDate
     */
    public void setCcEffDate(Date value) {
        setAttributeInternal(CCEFFDATE, value);
    }

    /**
     * Gets the attribute value for CcBuy, using the alias name CcBuy.
     * @return the value of CcBuy
     */
    public Number getCcBuy() {
        return (Number)getAttributeInternal(CCBUY);
    }

    /**
     * Sets <code>value</code> as the attribute value for CcBuy.
     * @param value value to set the CcBuy
     */
    public void setCcBuy(Number value) {
        setAttributeInternal(CCBUY, value);
    }

    /**
     * Gets the attribute value for CcSell, using the alias name CcSell.
     * @return the value of CcSell
     */
    public Number getCcSell() {
        return (Number)getAttributeInternal(CCSELL);
    }

    /**
     * Sets <code>value</code> as the attribute value for CcSell.
     * @param value value to set the CcSell
     */
    public void setCcSell(Number value) {
        setAttributeInternal(CCSELL, value);
    }

    /**
     * Gets the attribute value for CcBuyRev, using the alias name CcBuyRev.
     * @return the value of CcBuyRev
     */
    public Number getCcBuyRev() {
        return (Number)getAttributeInternal(CCBUYREV);
    }

    /**
     * Sets <code>value</code> as the attribute value for CcBuyRev.
     * @param value value to set the CcBuyRev
     */
    public void setCcBuyRev(Number value) {
        setAttributeInternal(CCBUYREV, value);
    }

    /**
     * Gets the attribute value for CcSellRev, using the alias name CcSellRev.
     * @return the value of CcSellRev
     */
    public Number getCcSellRev() {
        return (Number)getAttributeInternal(CCSELLREV);
    }

    /**
     * Sets <code>value</code> as the attribute value for CcSellRev.
     * @param value value to set the CcSellRev
     */
    public void setCcSellRev(Number value) {
        setAttributeInternal(CCSELLREV, value);
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
    public Date getUsrIdCreateDt() {
        return (Date)getAttributeInternal(USRIDCREATEDT);
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
    public Date getUsrIdModDt() {
        return (Date)getAttributeInternal(USRIDMODDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdModDt.
     * @param value value to set the UsrIdModDt
     */
    public void setUsrIdModDt(Date value) {
        setAttributeInternal(USRIDMODDT, value);
    }

    /**
     * Gets the attribute value for CcCldId, using the alias name CcCldId.
     * @return the value of CcCldId
     */
    public String getCcCldId() {
        return (String)getAttributeInternal(CCCLDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CcCldId.
     * @param value value to set the CcCldId
     */
    public void setCcCldId(String value) {
        setAttributeInternal(CCCLDID, value);
    }

    /**
     * Gets the attribute value for CcHoOrgId, using the alias name CcHoOrgId.
     * @return the value of CcHoOrgId
     */
    public String getCcHoOrgId() {
        return (String)getAttributeInternal(CCHOORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CcHoOrgId.
     * @param value value to set the CcHoOrgId
     */
    public void setCcHoOrgId(String value) {
        setAttributeInternal(CCHOORGID, value);
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
     * @param ccCurrId key constituent
     * @param ccCurrIdTxn key constituent
     * @param ccEffDate key constituent
     * @param ccCldId key constituent
     * @param ccHoOrgId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(Integer ccCurrId, Integer ccCurrIdTxn, Date ccEffDate, String ccCldId,
                                       String ccHoOrgId) {
        return new Key(new Object[]{ccCurrId, ccCurrIdTxn, ccEffDate, ccCldId, ccHoOrgId});
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("currencyconversion.model.entities.AppCurrConvEO");
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
    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        Integer  UserId =Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
                String org_id=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
                
                String cld_id=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
               
                setUsrIdCreate(UserId);
                setCcHoOrgId(org_id);
                setCcCldId(cld_id);
        super.create(attributeList);
    }

    /**
     * Add locking logic here.
     */
    public void lock() {
       // super.lock();
    }

    /**
     * Custom DML update/insert/delete logic here.
     * @param operation the operation type
     * @param e the transaction event
     */
    protected void doDML(int operation, TransactionEvent e) {
        if(operation==DML_UPDATE){
              Integer  UserId =Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
              Date currDate = (Date)Date.getCurrentDate();
             setUsrIdModDt(currDate);
              setUsrIdMod(UserId);
              }
        super.doDML(operation, e);
    }
}
