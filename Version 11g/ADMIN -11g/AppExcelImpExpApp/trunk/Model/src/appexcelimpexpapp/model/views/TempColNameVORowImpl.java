package appexcelimpexpapp.model.views;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;


import oracle.jbo.RowSet;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Sep 30 11:40:52 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class TempColNameVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        Dummy {
            public Object get(TempColNameVORowImpl obj) {
                return obj.getDummy();
            }

            public void put(TempColNameVORowImpl obj, Object value) {
                obj.setDummy((String)value);
            }
        }
        ,
        ColId {
            public Object get(TempColNameVORowImpl obj) {
                return obj.getColId();
            }

            public void put(TempColNameVORowImpl obj, Object value) {
                obj.setColId((Integer)value);
            }
        }
        ,
        ColName {
            public Object get(TempColNameVORowImpl obj) {
                return obj.getColName();
            }

            public void put(TempColNameVORowImpl obj, Object value) {
                obj.setColName((String)value);
            }
        }
        ,
        DocId {
            public Object get(TempColNameVORowImpl obj) {
                return obj.getDocId();
            }

            public void put(TempColNameVORowImpl obj, Object value) {
                obj.setDocId((Integer)value);
            }
        }
        ,
        TabId {
            public Object get(TempColNameVORowImpl obj) {
                return obj.getTabId();
            }

            public void put(TempColNameVORowImpl obj, Object value) {
                obj.setTabId((Integer)value);
            }
        }
        ,
        AppDocDbObVO1 {
            public Object get(TempColNameVORowImpl obj) {
                return obj.getAppDocDbObVO1();
            }

            public void put(TempColNameVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(TempColNameVORowImpl object);

        public abstract void put(TempColNameVORowImpl object, Object value);

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
    public static final int COLID = AttributesEnum.ColId.index();
    public static final int COLNAME = AttributesEnum.ColName.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int TABID = AttributesEnum.TabId.index();
    public static final int APPDOCDBOBVO1 = AttributesEnum.AppDocDbObVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public TempColNameVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute Dummy.
     * @return the Dummy
     */
    public String getDummy() {
        return (String)getAttributeInternal(DUMMY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Dummy.
     * @param value value to set the  Dummy
     */
    public void setDummy(String value) {
        setAttributeInternal(DUMMY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ColId.
     * @return the ColId
     */
    public Integer getColId() {
        return (Integer)getAttributeInternal(COLID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ColId.
     * @param value value to set the  ColId
     */
    public void setColId(Integer value) {
        setAttributeInternal(COLID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ColName.
     * @return the ColName
     */
    public String getColName() {
        return (String)getAttributeInternal(COLNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ColName.
     * @param value value to set the  ColName
     */
    public void setColName(String value) {
        setAttributeInternal(COLNAME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute DocId.
     * @return the DocId
     */
    public Integer getDocId() {
        Object ob = resolvEl("#{pageFlowScope.GLBL_DOC_ID}");
        if (ob != null)
            return Integer.parseInt(ob.toString());
        
        return (Integer)getAttributeInternal(DOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute DocId.
     * @param value value to set the  DocId
     */
    public void setDocId(Integer value) {
        setAttributeInternal(DOCID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TabId.
     * @return the TabId
     */
    public Integer getTabId() {
        Object ob = resolvEl("#{pageFlowScope.GLBL_TAB_ID}");
        if (ob != null)
            return Integer.parseInt(ob.toString());
        
        return (Integer) getAttributeInternal(TABID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TabId.
     * @param value value to set the  TabId
     */
    public void setTabId(Integer value) {
        setAttributeInternal(TABID, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> AppDocDbObVO1.
     */
    public RowSet getAppDocDbObVO1() {
        return (RowSet)getAttributeInternal(APPDOCDBOBVO1);
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

    public Object resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        Object msg = valueExp.getValue(elContext);
        return msg;
    }
}
