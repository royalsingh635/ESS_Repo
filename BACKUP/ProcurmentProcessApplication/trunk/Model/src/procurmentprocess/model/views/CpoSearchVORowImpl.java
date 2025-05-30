package procurmentprocess.model.views;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import oracle.adf.share.ADFContext;

import oracle.jbo.RowSet;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Mar 28 12:47:35 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CpoSearchVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        Dummy {
            public Object get(CpoSearchVORowImpl obj) {
                return obj.getDummy();
            }

            public void put(CpoSearchVORowImpl obj, Object value) {
                obj.setDummy((String)value);
            }
        }
        ,
        CpoId {
            public Object get(CpoSearchVORowImpl obj) {
                return obj.getCpoId();
            }

            public void put(CpoSearchVORowImpl obj, Object value) {
                obj.setCpoId((String)value);
            }
        }
        ,
        CpoDocId {
            public Object get(CpoSearchVORowImpl obj) {
                return obj.getCpoDocId();
            }

            public void put(CpoSearchVORowImpl obj, Object value) {
                obj.setCpoDocId((String)value);
            }
        }
        ,
        FrmAmt {
            public Object get(CpoSearchVORowImpl obj) {
                return obj.getFrmAmt();
            }

            public void put(CpoSearchVORowImpl obj, Object value) {
                obj.setFrmAmt((Number)value);
            }
        }
        ,
        ToAmt {
            public Object get(CpoSearchVORowImpl obj) {
                return obj.getToAmt();
            }

            public void put(CpoSearchVORowImpl obj, Object value) {
                obj.setToAmt((Number)value);
            }
        }
        ,
        ItemId {
            public Object get(CpoSearchVORowImpl obj) {
                return obj.getItemId();
            }

            public void put(CpoSearchVORowImpl obj, Object value) {
                obj.setItemId((String)value);
            }
        }
        ,
        CoaId {
            public Object get(CpoSearchVORowImpl obj) {
                return obj.getCoaId();
            }

            public void put(CpoSearchVORowImpl obj, Object value) {
                obj.setCoaId((Number)value);
            }
        }
        ,
        CoaName {
            public Object get(CpoSearchVORowImpl obj) {
                return obj.getCoaName();
            }

            public void put(CpoSearchVORowImpl obj, Object value) {
                obj.setCoaName((String)value);
            }
        }
        ,
        ItemName {
            public Object get(CpoSearchVORowImpl obj) {
                return obj.getItemName();
            }

            public void put(CpoSearchVORowImpl obj, Object value) {
                obj.setItemName((String)value);
            }
        }
        ,
        CpoFrmDate {
            public Object get(CpoSearchVORowImpl obj) {
                return obj.getCpoFrmDate();
            }

            public void put(CpoSearchVORowImpl obj, Object value) {
                obj.setCpoFrmDate((Date)value);
            }
        }
        ,
        CpoToDate {
            public Object get(CpoSearchVORowImpl obj) {
                return obj.getCpoToDate();
            }

            public void put(CpoSearchVORowImpl obj, Object value) {
                obj.setCpoToDate((Date)value);
            }
        }
        ,
        CldId {
            public Object get(CpoSearchVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(CpoSearchVORowImpl obj, Object value) {
                obj.setCldId((String)value);
            }
        }
        ,
        SlocId {
            public Object get(CpoSearchVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(CpoSearchVORowImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        OrgId {
            public Object get(CpoSearchVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(CpoSearchVORowImpl obj, Object value) {
                obj.setOrgId((String)value);
            }
        }
        ,
        LOVCpoIdVO1 {
            public Object get(CpoSearchVORowImpl obj) {
                return obj.getLOVCpoIdVO1();
            }

            public void put(CpoSearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LOVCoaNmVO1 {
            public Object get(CpoSearchVORowImpl obj) {
                return obj.getLOVCoaNmVO1();
            }

            public void put(CpoSearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LOVItemNameVO1 {
            public Object get(CpoSearchVORowImpl obj) {
                return obj.getLOVItemNameVO1();
            }

            public void put(CpoSearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CashPurchaseOrderVO1 {
            public Object get(CpoSearchVORowImpl obj) {
                return obj.getCashPurchaseOrderVO1();
            }

            public void put(CpoSearchVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(CpoSearchVORowImpl object);

        public abstract void put(CpoSearchVORowImpl object, Object value);

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


    public static final int DUMMY = AttributesEnum.Dummy.index();
    public static final int CPOID = AttributesEnum.CpoId.index();
    public static final int CPODOCID = AttributesEnum.CpoDocId.index();
    public static final int FRMAMT = AttributesEnum.FrmAmt.index();
    public static final int TOAMT = AttributesEnum.ToAmt.index();
    public static final int ITEMID = AttributesEnum.ItemId.index();
    public static final int COAID = AttributesEnum.CoaId.index();
    public static final int COANAME = AttributesEnum.CoaName.index();
    public static final int ITEMNAME = AttributesEnum.ItemName.index();
    public static final int CPOFRMDATE = AttributesEnum.CpoFrmDate.index();
    public static final int CPOTODATE = AttributesEnum.CpoToDate.index();
    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int LOVCPOIDVO1 = AttributesEnum.LOVCpoIdVO1.index();
    public static final int LOVCOANMVO1 = AttributesEnum.LOVCoaNmVO1.index();
    public static final int LOVITEMNAMEVO1 = AttributesEnum.LOVItemNameVO1.index();
    public static final int CASHPURCHASEORDERVO1 = AttributesEnum.CashPurchaseOrderVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public CpoSearchVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute Dummy.
     * @return the Dummy
     */
    public String getDummy() {
        return (String) getAttributeInternal(DUMMY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Dummy.
     * @param value value to set the  Dummy
     */
    public void setDummy(String value) {
        setAttributeInternal(DUMMY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CpoId.
     * @return the CpoId
     */
    public String getCpoId() {
        return (String) getAttributeInternal(CPOID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CpoId.
     * @param value value to set the  CpoId
     */
    public void setCpoId(String value) {
        setAttributeInternal(CPOID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CpoDocId.
     * @return the CpoDocId
     */
    public String getCpoDocId() {
        return (String) getAttributeInternal(CPODOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CpoDocId.
     * @param value value to set the  CpoDocId
     */
    public void setCpoDocId(String value) {
        setAttributeInternal(CPODOCID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute FrmAmt.
     * @return the FrmAmt
     */
    public Number getFrmAmt() {
        return (Number) getAttributeInternal(FRMAMT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute FrmAmt.
     * @param value value to set the  FrmAmt
     */
    public void setFrmAmt(Number value) {
        setAttributeInternal(FRMAMT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ToAmt.
     * @return the ToAmt
     */
    public Number getToAmt() {
        return (Number) getAttributeInternal(TOAMT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ToAmt.
     * @param value value to set the  ToAmt
     */
    public void setToAmt(Number value) {
        setAttributeInternal(TOAMT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ItemId.
     * @return the ItemId
     */
    public String getItemId() {
        return (String) getAttributeInternal(ITEMID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ItemId.
     * @param value value to set the  ItemId
     */
    public void setItemId(String value) {
        setAttributeInternal(ITEMID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CoaId.
     * @return the CoaId
     */
    public Number getCoaId() {
        return (Number) getAttributeInternal(COAID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CoaId.
     * @param value value to set the  CoaId
     */
    public void setCoaId(Number value) {
        setAttributeInternal(COAID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CoaName.
     * @return the CoaName
     */
    public String getCoaName() {
        return (String) getAttributeInternal(COANAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CoaName.
     * @param value value to set the  CoaName
     */
    public void setCoaName(String value) {
        setAttributeInternal(COANAME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ItemName.
     * @return the ItemName
     */
    public String getItemName() {
        return (String) getAttributeInternal(ITEMNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ItemName.
     * @param value value to set the  ItemName
     */
    public void setItemName(String value) {
        setAttributeInternal(ITEMNAME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CpoFrmDate.
     * @return the CpoFrmDate
     */
    public Date getCpoFrmDate() {
        return (Date) getAttributeInternal(CPOFRMDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CpoFrmDate.
     * @param value value to set the  CpoFrmDate
     */
    public void setCpoFrmDate(Date value) {
        setAttributeInternal(CPOFRMDATE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CpoToDate.
     * @return the CpoToDate
     */
    public Date getCpoToDate() {
        return (Date) getAttributeInternal(CPOTODATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CpoToDate.
     * @param value value to set the  CpoToDate
     */
    public void setCpoToDate(Date value) {
        setAttributeInternal(CPOTODATE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CldId.
     * @return the CldId
     */
    public String getCldId() {
        String cld= resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
                return cld;
        //return (String) getAttributeInternal(CLDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CldId.
     * @param value value to set the  CldId
     */
    public void setCldId(String value) {
        setAttributeInternal(CLDID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SlocId.
     * @return the SlocId
     */
    public Integer getSlocId() {
        Integer sloc= Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
                return sloc;
        //return (Integer) getAttributeInternal(SLOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SlocId.
     * @param value value to set the  SlocId
     */
    public void setSlocId(Integer value) {
        setAttributeInternal(SLOCID, value);
    }
    public String resolvEl(String data){
        ADFContext adfCtx = ADFContext.getCurrent();
        ELContext elContext = adfCtx.getELContext();
        ValueExpression valueExp = adfCtx.getExpressionFactory().createValueExpression(elContext, data, Object.class);
        Object Message = valueExp.getValue(elContext).toString();
        return Message.toString();
    }
    /**
     * Gets the attribute value for the calculated attribute OrgId.
     * @return the OrgId
     */
    public String getOrgId() {
        String org= resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        return org;
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute OrgId.
     * @param value value to set the  OrgId
     */
    public void setOrgId(String value) {
        setAttributeInternal(ORGID, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVCpoIdVO1.
     */
    public RowSet getLOVCpoIdVO1() {
        return (RowSet)getAttributeInternal(LOVCPOIDVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVCoaNmVO1.
     */
    public RowSet getLOVCoaNmVO1() {
        return (RowSet)getAttributeInternal(LOVCOANMVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVItemNameVO1.
     */
    public RowSet getLOVItemNameVO1() {
        return (RowSet)getAttributeInternal(LOVITEMNAMEVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> CashPurchaseOrderVO1.
     */
    public RowSet getCashPurchaseOrderVO1() {
        return (RowSet)getAttributeInternal(CASHPURCHASEORDERVO1);
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
