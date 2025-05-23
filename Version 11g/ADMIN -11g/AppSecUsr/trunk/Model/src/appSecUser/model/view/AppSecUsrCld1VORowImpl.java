package appSecUser.model.view;

import appSecUser.model.entity.AppSecUsrCld1EOImpl;

import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Jun 17 10:48:31 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppSecUsrCld1VORowImpl extends ViewRowImpl {

    public static final int ENTITY_APPSECUSRCLD1EO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        SlocId {
            public Object get(AppSecUsrCld1VORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(AppSecUsrCld1VORowImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        UsrCldId {
            public Object get(AppSecUsrCld1VORowImpl obj) {
                return obj.getUsrCldId();
            }

            public void put(AppSecUsrCld1VORowImpl obj, Object value) {
                obj.setUsrCldId((String)value);
            }
        }
        ,
        UsrId {
            public Object get(AppSecUsrCld1VORowImpl obj) {
                return obj.getUsrId();
            }

            public void put(AppSecUsrCld1VORowImpl obj, Object value) {
                obj.setUsrId((Long)value);
            }
        }
        ,
        UsrPrfId {
            public Object get(AppSecUsrCld1VORowImpl obj) {
                return obj.getUsrPrfId();
            }

            public void put(AppSecUsrCld1VORowImpl obj, Object value) {
                obj.setUsrPrfId((Integer)value);
            }
        }
        ,
        OrgSecUsr {
            public Object get(AppSecUsrCld1VORowImpl obj) {
                return obj.getOrgSecUsr();
            }

            public void put(AppSecUsrCld1VORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        AppSecPrfVO1 {
            public Object get(AppSecUsrCld1VORowImpl obj) {
                return obj.getAppSecPrfVO1();
            }

            public void put(AppSecUsrCld1VORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(AppSecUsrCld1VORowImpl object);

        public abstract void put(AppSecUsrCld1VORowImpl object, Object value);

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
    public static final int USRCLDID = AttributesEnum.UsrCldId.index();
    public static final int USRID = AttributesEnum.UsrId.index();
    public static final int USRPRFID = AttributesEnum.UsrPrfId.index();
    public static final int ORGSECUSR = AttributesEnum.OrgSecUsr.index();
    public static final int APPSECPRFVO1 = AttributesEnum.AppSecPrfVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public AppSecUsrCld1VORowImpl() {
    }

    /**
     * Gets AppSecUsrCld1EO entity object.
     * @return the AppSecUsrCld1EO
     */
    public AppSecUsrCld1EOImpl getAppSecUsrCld1EO() {
        return (AppSecUsrCld1EOImpl)getEntity(ENTITY_APPSECUSRCLD1EO);
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
     * Gets the attribute value for USR_CLD_ID using the alias name UsrCldId.
     * @return the USR_CLD_ID
     */
    public String getUsrCldId() {
        return (String) getAttributeInternal(USRCLDID);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_CLD_ID using the alias name UsrCldId.
     * @param value value to set the USR_CLD_ID
     */
    public void setUsrCldId(String value) {
        setAttributeInternal(USRCLDID, value);
    }

    /**
     * Gets the attribute value for USR_ID using the alias name UsrId.
     * @return the USR_ID
     */
    public Long getUsrId() {
        return (Long) getAttributeInternal(USRID);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID using the alias name UsrId.
     * @param value value to set the USR_ID
     */
    public void setUsrId(Long value) {
        setAttributeInternal(USRID, value);
    }

    /**
     * Gets the attribute value for USR_PRF_ID using the alias name UsrPrfId.
     * @return the USR_PRF_ID
     */
    public Integer getUsrPrfId() {
        return (Integer) getAttributeInternal(USRPRFID);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_PRF_ID using the alias name UsrPrfId.
     * @param value value to set the USR_PRF_ID
     */
    public void setUsrPrfId(Integer value) {
        setAttributeInternal(USRPRFID, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link OrgSecUsr.
     */
    public RowIterator getOrgSecUsr() {
        return (RowIterator)getAttributeInternal(ORGSECUSR);
    }

    /**
     * Gets the view accessor <code>RowSet</code> AppSecPrfVO1.
     */
    public RowSet getAppSecPrfVO1() {
        
        System.out.println("getting app profile");
        System.out.println("Row count is"+ ((RowSet)getAttributeInternal(APPSECPRFVO1)).getRowCount());
        return (RowSet)getAttributeInternal(APPSECPRFVO1);
    }
    
    
    /**
     * Code is Added By Mayank Shukla
     * 
     */
    
    public void setAppSecPrfVO1(AppSecPrfVOImpl vo){
        System.out.println("In seeter of view accessor");
        setAttributeInternal(APPSECPRFVO1, vo);
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
