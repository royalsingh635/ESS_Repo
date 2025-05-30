package inventoryreportapp.model.views;

import java.sql.CallableStatement;
import java.sql.SQLException;

import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.JboException;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Apr 21 10:21:36 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class StockVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        Dummy {
            public Object get(StockVORowImpl obj) {
                return obj.getDummy();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setDummy((String)value);
            }
        }
        ,
        CldId {
            public Object get(StockVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setCldId((String)value);
            }
        }
        ,
        SlocId {
            public Object get(StockVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        OrgId {
            public Object get(StockVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setOrgId((String)value);
            }
        }
        ,
        WhId {
            public Object get(StockVORowImpl obj) {
                return obj.getWhId();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setWhId((String)value);
            }
        }
        ,
        toDate {
            public Object get(StockVORowImpl obj) {
                return obj.gettoDate();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.settoDate((Date)value);
            }
        }
        ,
        ItmId {
            public Object get(StockVORowImpl obj) {
                return obj.getItmId();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setItmId((String)value);
            }
        }
        ,
        ItmNm {
            public Object get(StockVORowImpl obj) {
                return obj.getItmNm();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setItmNm((String)value);
            }
        }
        ,
        fromDate {
            public Object get(StockVORowImpl obj) {
                return obj.getfromDate();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setfromDate((Date)value);
            }
        }
        ,
        LotId {
            public Object get(StockVORowImpl obj) {
                return obj.getLotId();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setLotId((String)value);
            }
        }
        ,
        BinId {
            public Object get(StockVORowImpl obj) {
                return obj.getBinId();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setBinId((String)value);
            }
        }
        ,
        WipItmFlg {
            public Object get(StockVORowImpl obj) {
                return obj.getWipItmFlg();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setWipItmFlg((String)value);
            }
        }
        ,
        SlsItmFlg {
            public Object get(StockVORowImpl obj) {
                return obj.getSlsItmFlg();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setSlsItmFlg((String)value);
            }
        }
        ,
        PurItmFlg {
            public Object get(StockVORowImpl obj) {
                return obj.getPurItmFlg();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setPurItmFlg((String)value);
            }
        }
        ,
        ChkSpwFlg {
            public Object get(StockVORowImpl obj) {
                return obj.getChkSpwFlg();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setChkSpwFlg((String)value);
            }
        }
        ,
        hoOrgId {
            public Object get(StockVORowImpl obj) {
                return obj.gethoOrgId();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.sethoOrgId((String)value);
            }
        }
        ,
        FyId {
            public Object get(StockVORowImpl obj) {
                return obj.getFyId();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setFyId((Integer)value);
            }
        }
        ,
        ItmGrpNm {
            public Object get(StockVORowImpl obj) {
                return obj.getItmGrpNm();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setItmGrpNm((String)value);
            }
        }
        ,
        ItmGrpId {
            public Object get(StockVORowImpl obj) {
                return obj.getItmGrpId();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setItmGrpId((String)value);
            }
        }
        ,
        LOVWarehouseVO1 {
            public Object get(StockVORowImpl obj) {
                return obj.getLOVWarehouseVO1();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LOVOrgVO1 {
            public Object get(StockVORowImpl obj) {
                return obj.getLOVOrgVO1();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovItem1 {
            public Object get(StockVORowImpl obj) {
                return obj.getLovItem1();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovLotVO1 {
            public Object get(StockVORowImpl obj) {
                return obj.getLovLotVO1();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovBinVO1 {
            public Object get(StockVORowImpl obj) {
                return obj.getLovBinVO1();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LOVFinancialYearVO1 {
            public Object get(StockVORowImpl obj) {
                return obj.getLOVFinancialYearVO1();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LOVItmGrp1 {
            public Object get(StockVORowImpl obj) {
                return obj.getLOVItmGrp1();
            }

            public void put(StockVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(StockVORowImpl object);

        public abstract void put(StockVORowImpl object, Object value);

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
    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int WHID = AttributesEnum.WhId.index();
    public static final int TODATE = AttributesEnum.toDate.index();
    public static final int ITMID = AttributesEnum.ItmId.index();
    public static final int ITMNM = AttributesEnum.ItmNm.index();
    public static final int FROMDATE = AttributesEnum.fromDate.index();
    public static final int LOTID = AttributesEnum.LotId.index();
    public static final int BINID = AttributesEnum.BinId.index();
    public static final int WIPITMFLG = AttributesEnum.WipItmFlg.index();
    public static final int SLSITMFLG = AttributesEnum.SlsItmFlg.index();
    public static final int PURITMFLG = AttributesEnum.PurItmFlg.index();
    public static final int CHKSPWFLG = AttributesEnum.ChkSpwFlg.index();
    public static final int HOORGID = AttributesEnum.hoOrgId.index();
    public static final int FYID = AttributesEnum.FyId.index();
    public static final int ITMGRPNM = AttributesEnum.ItmGrpNm.index();
    public static final int ITMGRPID = AttributesEnum.ItmGrpId.index();
    public static final int LOVWAREHOUSEVO1 = AttributesEnum.LOVWarehouseVO1.index();
    public static final int LOVORGVO1 = AttributesEnum.LOVOrgVO1.index();
    public static final int LOVITEM1 = AttributesEnum.LovItem1.index();
    public static final int LOVLOTVO1 = AttributesEnum.LovLotVO1.index();
    public static final int LOVBINVO1 = AttributesEnum.LovBinVO1.index();
    public static final int LOVFINANCIALYEARVO1 = AttributesEnum.LOVFinancialYearVO1.index();
    public static final int LOVITMGRP1 = AttributesEnum.LOVItmGrp1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public StockVORowImpl() {
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
     * Gets the attribute value for the calculated attribute CldId.
     * @return the CldId
     */
    public String getCldId() {
        String cldid;
        if(resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}")!=null){
            cldid=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
            return cldid;
        }
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
        Integer sloc;
        if(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}")!=null){
         sloc =Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
            return sloc;
        }
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
        String orgid;
        if(resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}")!=null){
         orgid =resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
            return orgid;
        }
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
     * Gets the attribute value for the calculated attribute toDate.
     * @return the toDate
     */
    public Date gettoDate() {
        return (Date) getAttributeInternal(TODATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute toDate.
     * @param value value to set the  toDate
     */
    public void settoDate(Date value) {
        setAttributeInternal(TODATE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ItmId.
     * @return the ItmId
     */
    public String getItmId() {
        return (String) getAttributeInternal(ITMID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ItmId.
     * @param value value to set the  ItmId
     */
    public void setItmId(String value) {
        setAttributeInternal(ITMID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ItmNm.
     * @return the ItmNm
     */
    public String getItmNm() {
        return (String) getAttributeInternal(ITMNM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ItmNm.
     * @param value value to set the  ItmNm
     */
    public void setItmNm(String value) {
        setAttributeInternal(ITMNM, value);
    }

    /**
     * Gets the attribute value for the calculated attribute fromDate.
     * @return the fromDate
     */
    public Date getfromDate() {
        return (Date) getAttributeInternal(FROMDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute fromDate.
     * @param value value to set the  fromDate
     */
    public void setfromDate(Date value) {
        setAttributeInternal(FROMDATE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute LotId.
     * @return the LotId
     */
    public String getLotId() {
        return (String) getAttributeInternal(LOTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute LotId.
     * @param value value to set the  LotId
     */
    public void setLotId(String value) {
        setAttributeInternal(LOTID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute BinId.
     * @return the BinId
     */
    public String getBinId() {
        return (String) getAttributeInternal(BINID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute BinId.
     * @param value value to set the  BinId
     */
    public void setBinId(String value) {
        setAttributeInternal(BINID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute WipItmFlg.
     * @return the WipItmFlg
     */
    public String getWipItmFlg() {
        return (String) getAttributeInternal(WIPITMFLG);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute WipItmFlg.
     * @param value value to set the  WipItmFlg
     */
    public void setWipItmFlg(String value) {
        setAttributeInternal(WIPITMFLG, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SlsItmFlg.
     * @return the SlsItmFlg
     */
    public String getSlsItmFlg() {
        return (String) getAttributeInternal(SLSITMFLG);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SlsItmFlg.
     * @param value value to set the  SlsItmFlg
     */
    public void setSlsItmFlg(String value) {
        setAttributeInternal(SLSITMFLG, value);
    }

    /**
     * Gets the attribute value for the calculated attribute PurItmFlg.
     * @return the PurItmFlg
     */
    public String getPurItmFlg() {
        return (String) getAttributeInternal(PURITMFLG);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute PurItmFlg.
     * @param value value to set the  PurItmFlg
     */
    public void setPurItmFlg(String value) {
        setAttributeInternal(PURITMFLG, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ChkSpwFlg.
     * @return the ChkSpwFlg
     */
    public String getChkSpwFlg() {
        return (String) getAttributeInternal(CHKSPWFLG);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ChkSpwFlg.
     * @param value value to set the  ChkSpwFlg
     */
    public void setChkSpwFlg(String value) {
        setAttributeInternal(CHKSPWFLG, value);
    }

    /**
     * Gets the attribute value for the calculated attribute hoOrgId.
     * @return the hoOrgId
     */
    public String gethoOrgId() {
        String hoorgid;
        if(resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}")!=null){
         hoorgid =resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
            return hoorgid;
        }
        return (String) getAttributeInternal(HOORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute hoOrgId.
     * @param value value to set the  hoOrgId
     */
    public void sethoOrgId(String value) {
        setAttributeInternal(HOORGID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute FyId.
     * @return the FyId
     */
    public Integer getFyId() {
        String CldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        String OrgId=resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        Integer fyId =(Integer)(callStoredFunction(Types.INTEGER, "APP.GET_ORG_FY_ID (?,?,?)", new Object[] {CldId,OrgId,new Timestamp(System.currentTimeMillis())}));
        return ((Integer) getAttributeInternal(FYID) == null)?fyId:(Integer) getAttributeInternal(FYID);
     }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute FyId.
     * @param value value to set the  FyId
     */
    public void setFyId(Integer value) {
        setAttributeInternal(FYID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ItmGrpNm.
     * @return the ItmGrpNm
     */
    public String getItmGrpNm() {
        return (String) getAttributeInternal(ITMGRPNM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ItmGrpNm.
     * @param value value to set the  ItmGrpNm
     */
    public void setItmGrpNm(String value) {
        setAttributeInternal(ITMGRPNM, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ItmGrpId.
     * @return the ItmGrpId
     */
    public String getItmGrpId() {
        return (String) getAttributeInternal(ITMGRPID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ItmGrpId.
     * @param value value to set the  ItmGrpId
     */
    public void setItmGrpId(String value) {
        setAttributeInternal(ITMGRPID, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVWarehouseVO1.
     */
    public RowSet getLOVWarehouseVO1() {
        return (RowSet)getAttributeInternal(LOVWAREHOUSEVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVOrgVO1.
     */
    public RowSet getLOVOrgVO1() {
        return (RowSet)getAttributeInternal(LOVORGVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovItem1.
     */
    public RowSet getLovItem1() {
        return (RowSet)getAttributeInternal(LOVITEM1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovLotVO1.
     */
    public RowSet getLovLotVO1() {
        return (RowSet)getAttributeInternal(LOVLOTVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovBinVO1.
     */
    public RowSet getLovBinVO1() {
        return (RowSet)getAttributeInternal(LOVBINVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVFinancialYearVO1.
     */
    public RowSet getLOVFinancialYearVO1() {
        return (RowSet)getAttributeInternal(LOVFINANCIALYEARVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVItmGrp1.
     */
    public RowSet getLOVItmGrp1() {
        return (RowSet)getAttributeInternal(LOVITMGRP1);
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
    
    public Object resolvEl(String data){
    FacesContext fc = FacesContext.getCurrentInstance();
    Application app = fc.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = fc.getELContext();
    ValueExpression valueExp = elFactory.createValueExpression(elContext, data,
    Object.class);
    Object Message=valueExp.getValue(elContext);
    return Message;
    }
    
    protected Object callStoredFunction(int sqlReturnType, String stmt,
                                          Object[] bindVars) {
        CallableStatement st = null;
        try {
          // 1. Create a JDBC CallabledStatement
          st =
              getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;",
                                                         0);
          // 2. Register the first bind variable for the return value
          st.registerOutParameter(1, sqlReturnType);
          if (bindVars != null) {
            // 3. Loop over values for the bind variables passed in, if any
            for (int z = 0; z < bindVars.length; z++) {
              // 4. Set the value of user-supplied bind vars in the stmt
              st.setObject(z + 2, bindVars[z]);
            }
          }
          // 5. Set the value of user-supplied bind vars in the stmt
          st.executeUpdate();
          // 6. Return the value of the first bind variable
          return st.getObject(1);
        } catch (SQLException e) {
          throw new JboException(e);
        } finally {
          if (st != null) {
            try {
              // 7. Close the statement
              st.close();
            } catch (SQLException e) {
            }
          }
        }
      }
}
