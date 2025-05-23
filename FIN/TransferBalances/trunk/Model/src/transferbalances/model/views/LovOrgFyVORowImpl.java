package transferbalances.model.views;

import oracle.jbo.domain.Date;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Dec 23 11:01:22 PST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LovOrgFyVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        OrgId {
            public Object get(LovOrgFyVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(LovOrgFyVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        OrgFyId {
            public Object get(LovOrgFyVORowImpl obj) {
                return obj.getOrgFyId();
            }

            public void put(LovOrgFyVORowImpl obj, Object value) {
                obj.setOrgFyId((Integer) value);
            }
        }
        ,
        FyDesc {
            public Object get(LovOrgFyVORowImpl obj) {
                return obj.getFyDesc();
            }

            public void put(LovOrgFyVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        FyStrt {
            public Object get(LovOrgFyVORowImpl obj) {
                return obj.getFyStrt();
            }

            public void put(LovOrgFyVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        FyEnd {
            public Object get(LovOrgFyVORowImpl obj) {
                return obj.getFyEnd();
            }

            public void put(LovOrgFyVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        OrgFyRefSlocId {
            public Object get(LovOrgFyVORowImpl obj) {
                return obj.getOrgFyRefSlocId();
            }

            public void put(LovOrgFyVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        FyType {
            public Object get(LovOrgFyVORowImpl obj) {
                return obj.getFyType();
            }

            public void put(LovOrgFyVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        transEndDate {
            public Object get(LovOrgFyVORowImpl obj) {
                return obj.gettransEndDate();
            }

            public void put(LovOrgFyVORowImpl obj, Object value) {
                obj.settransEndDate((Date) value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(LovOrgFyVORowImpl object);

        public abstract void put(LovOrgFyVORowImpl object, Object value);

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

    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int ORGFYID = AttributesEnum.OrgFyId.index();
    public static final int FYDESC = AttributesEnum.FyDesc.index();
    public static final int FYSTRT = AttributesEnum.FyStrt.index();
    public static final int FYEND = AttributesEnum.FyEnd.index();
    public static final int ORGFYREFSLOCID = AttributesEnum.OrgFyRefSlocId.index();
    public static final int FYTYPE = AttributesEnum.FyType.index();
    public static final int TRANSENDDATE = AttributesEnum.transEndDate.index();

    /**
     * This is the default constructor (do not remove).
     */
    public LovOrgFyVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute OrgId.
     * @return the OrgId
     */
    public String getOrgId() {
        return (String) getAttributeInternal(ORGID);
    }

    /**
     * Gets the attribute value for the calculated attribute OrgFyId.
     * @return the OrgFyId
     */
    public Integer getOrgFyId() {
        return (Integer) getAttributeInternal(ORGFYID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute OrgFyId.
     * @param value value to set the  OrgFyId
     */
    public void setOrgFyId(Integer value) {
        setAttributeInternal(ORGFYID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute FyDesc.
     * @return the FyDesc
     */
    public String getFyDesc() {
        return (String) getAttributeInternal(FYDESC);
    }

    /**
     * Gets the attribute value for the calculated attribute FyStrt.
     * @return the FyStrt
     */
    public Date getFyStrt() {
        return (Date) getAttributeInternal(FYSTRT);
    }

    /**
     * Gets the attribute value for the calculated attribute FyEnd.
     * @return the FyEnd
     */
    public Date getFyEnd() {
        return (Date) getAttributeInternal(FYEND);
    }

    /**
     * Gets the attribute value for the calculated attribute OrgFyRefSlocId.
     * @return the OrgFyRefSlocId
     */
    public Integer getOrgFyRefSlocId() {
        return (Integer) getAttributeInternal(ORGFYREFSLOCID);
    }

    /**
     * Gets the attribute value for the calculated attribute FyType.
     * @return the FyType
     */
    public String getFyType() {
        return (String) getAttributeInternal(FYTYPE);
    }

    /**
     * Gets the attribute value for the calculated attribute transEndDate.
     * @return the transEndDate
     */
    public Date gettransEndDate() {
        // Date must be less than current date
        // date must be less than end date of financial year
        //date must be less than transaction date
        Date date = getFyEnd();
        Date currDate = (Date) new Date().getCurrentDate();
        if(date.dateValue().toString().compareTo(currDate.dateValue().toString())>0){
            return currDate;
        }
        return date;
       // return (Date) getAttributeInternal(TRANSENDDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute transEndDate.
     * @param value value to set the  transEndDate
     */
    public void settransEndDate(Date value) {
        
        setAttributeInternal(TRANSENDDATE, value);
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

