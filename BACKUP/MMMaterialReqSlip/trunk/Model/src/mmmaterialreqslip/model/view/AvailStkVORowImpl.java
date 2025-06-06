package mmmaterialreqslip.model.view;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import mmmaterialreqslip.model.services.MmMatReqSlipAMImpl;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Date;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Aug 23 10:47:14 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AvailStkVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CldId {
            public Object get(AvailStkVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(AvailStkVORowImpl obj, Object value) {
                obj.setCldId((String)value);
            }
        }
        ,
        SlocId {
            public Object get(AvailStkVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(AvailStkVORowImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        OrgId {
            public Object get(AvailStkVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(AvailStkVORowImpl obj, Object value) {
                obj.setOrgId((String)value);
            }
        }
        ,
        WhId {
            public Object get(AvailStkVORowImpl obj) {
                return obj.getWhId();
            }

            public void put(AvailStkVORowImpl obj, Object value) {
                obj.setWhId((String)value);
            }
        }
        ,
        WhNm {
            public Object get(AvailStkVORowImpl obj) {
                return obj.getWhNm();
            }

            public void put(AvailStkVORowImpl obj, Object value) {
                obj.setWhNm((String)value);
            }
        }
        ,
        HoOrgId {
            public Object get(AvailStkVORowImpl obj) {
                return obj.getHoOrgId();
            }

            public void put(AvailStkVORowImpl obj, Object value) {
                obj.setHoOrgId((String)value);
            }
        }
        ,
        Pk {
            public Object get(AvailStkVORowImpl obj) {
                return obj.getPk();
            }

            public void put(AvailStkVORowImpl obj, Object value) {
                obj.setPk((String)value);
            }
        }
        ,
        AvailStk {
            public Object get(AvailStkVORowImpl obj) {
                return obj.getAvailStk();
            }

            public void put(AvailStkVORowImpl obj, Object value) {
                obj.setAvailStk((String)value);
            }
        }
        ,
        WarehouseVO1 {
            public Object get(AvailStkVORowImpl obj) {
                return obj.getWarehouseVO1();
            }

            public void put(AvailStkVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        OrgVO1 {
            public Object get(AvailStkVORowImpl obj) {
                return obj.getOrgVO1();
            }

            public void put(AvailStkVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(AvailStkVORowImpl object);

        public abstract void put(AvailStkVORowImpl object, Object value);

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
    public static final int WHID = AttributesEnum.WhId.index();
    public static final int WHNM = AttributesEnum.WhNm.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int PK = AttributesEnum.Pk.index();
    public static final int AVAILSTK = AttributesEnum.AvailStk.index();
    public static final int WAREHOUSEVO1 = AttributesEnum.WarehouseVO1.index();
    public static final int ORGVO1 = AttributesEnum.OrgVO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public AvailStkVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute CldId.
     * @return the CldId
     */
    public String getCldId() {
        return (String) getAttributeInternal(CLDID);
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
        return (Integer) getAttributeInternal(SLOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SlocId.
     * @param value value to set the  SlocId
     */
    public void setSlocId(Integer value) {
        setAttributeInternal(SLOCID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute OrgId.
     * @return the OrgId
     */
    public String getOrgId() {
        return (String) getAttributeInternal(ORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute OrgId.
     * @param value value to set the  OrgId
     */
    public void setOrgId(String value) {
        setAttributeInternal(ORGID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute WhId.
     * @return the WhId
     */
    public String getWhId() {
        return (String) getAttributeInternal(WHID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute WhId.
     * @param value value to set the  WhId
     */
    public void setWhId(String value) {
        setAttributeInternal(WHID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute WhNm.
     * @return the WhNm
     */
    public String getWhNm() {
        return (String) getAttributeInternal(WHNM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute WhNm.
     * @param value value to set the  WhNm
     */
    public void setWhNm(String value) {
        setAttributeInternal(WHNM, value);
    }

    /**
     * Gets the attribute value for the calculated attribute HoOrgId.
     * @return the HoOrgId
     */
    public String getHoOrgId() {
        return (String) getAttributeInternal(HOORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute HoOrgId.
     * @param value value to set the  HoOrgId
     */
    public void setHoOrgId(String value) {
        setAttributeInternal(HOORGID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Pk.
     * @return the Pk
     */
    public String getPk() {
        return (String) getAttributeInternal(PK);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Pk.
     * @param value value to set the  Pk
     */
    public void setPk(String value) {
        setAttributeInternal(PK, value);
    }

    /**
     * Gets the attribute value for the calculated attribute AvailStk.
     * @return the AvailStk
     */
    public String getAvailStk() {
        MmMatReqSlipAMImpl am = (MmMatReqSlipAMImpl)resolvElDC("MmMatReqSlipAMDataControl");
        ViewObjectImpl voitem=am.getMmMrsItm2();
        ViewObjectImpl vo=am.getMmMrs2();
        if(voitem.getCurrentRow()!=null)
        {
      String cldid = (String)voitem.getCurrentRow().getAttribute("CldId");
      Integer slocid = (Integer)voitem.getCurrentRow().getAttribute("SlocId");
      String itemid = (String)voitem.getCurrentRow().getAttribute("ItmId");
      Integer fyid = (Integer)vo.getCurrentRow().getAttribute("FyId");
        String avlstk =(String)(callStoredFunction(Types.VARCHAR, "MM.MM_get_avail_stk(?,?,?,?,?,?,?)",new Object[] {cldid,slocid,getOrgId(),itemid,getWhId(),fyid,"18513"}));   
        return avlstk;
        }
        else{
        return (String) getAttributeInternal(AVAILSTK);
        }
    }
    public String resolvEl(String data) {
              FacesContext fc = FacesContext.getCurrentInstance();
              Application app = fc.getApplication();
              ExpressionFactory elFactory = app.getExpressionFactory();
              ELContext elContext = fc.getELContext();
              ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
              String msg = valueExp.getValue(elContext).toString();
              return msg;
          }
    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute AvailStk.
     * @param value value to set the  AvailStk
     */
    public void setAvailStk(String value) {
        setAttributeInternal(AVAILSTK, value);
    }


    /**
     * Gets the view accessor <code>RowSet</code> WarehouseVO1.
     */
    public RowSet getWarehouseVO1() {
        return (RowSet)getAttributeInternal(WAREHOUSEVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> OrgVO1.
     */
    public RowSet getOrgVO1() {
        return (RowSet)getAttributeInternal(ORGVO1);
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
    
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
            CallableStatement st = null;
            try {
                MmMatReqSlipAMImpl am = (MmMatReqSlipAMImpl)resolvElDC("MmMatReqSlipAMDataControl");
                st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
                st.registerOutParameter(1, sqlReturnType);
                if (bindVars != null) {
                    for (int z = 0; z < bindVars.length; z++) {
                        st.setObject(z + 2, bindVars[z]);
                    }
                }
                st.executeUpdate();
     
                return st.getObject(1);
            } catch (SQLException e) {
                throw new JboException(e);
            } finally {
                if (st != null) {
                    try {
                        st.close();
                    } catch (SQLException e) { throw new JboException(e);
                    }
                }
            }
        }
    public Object resolvElDC(String data) {
    FacesContext fc = FacesContext.getCurrentInstance();
    Application app = fc.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = fc.getELContext();
    ValueExpression valueExp =
    elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
    return valueExp.getValue(elContext);
    }
    
}
