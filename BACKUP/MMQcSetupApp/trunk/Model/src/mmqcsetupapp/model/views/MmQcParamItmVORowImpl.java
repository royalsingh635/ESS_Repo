package mmqcsetupapp.model.views;

import java.sql.SQLException;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import mmqcsetupapp.model.entities.MmQcParamItmEOImpl;

import mmqcsetupapp.model.service.QcSetupAMImpl;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Aug 08 12:54:26 IST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MmQcParamItmVORowImpl extends ViewRowImpl {


    public static final int ENTITY_MMQCPARAMITMEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CldId {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setCldId((String)value);
            }
        }
        ,
        SlocId {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setSlocId((Integer)value);
            }
        }
        ,
        OrgId {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setOrgId((String)value);
            }
        }
        ,
        ItmId {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getItmId();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setItmId((String)value);
            }
        }
        ,
        ParamId {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getParamId();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setParamId((String)value);
            }
        }
        ,
        StdVal {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getStdVal();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setStdVal((Number)value);
            }
        }
        ,
        TlrncLower {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getTlrncLower();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setTlrncLower((Number)value);
            }
        }
        ,
        TlrncUpper {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getTlrncUpper();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setTlrncUpper((Number)value);
            }
        }
        ,
        TlrncType {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getTlrncType();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setTlrncType((String)value);
            }
        }
        ,
        FailActn {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getFailActn();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setFailActn((Integer)value);
            }
        }
        ,
        LowerLimit {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getLowerLimit();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setLowerLimit((Number)value);
            }
        }
        ,
        UpperLimit {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getUpperLimit();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setUpperLimit((Number)value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setUsrIdCreate((Integer)value);
            }
        }
        ,
        UsrIdCreateDt {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getUsrIdCreateDt();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setUsrIdCreateDt((Timestamp)value);
            }
        }
        ,
        UsrIdMod {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getUsrIdMod();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setUsrIdMod((Integer)value);
            }
        }
        ,
        UsrIdModDt {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getUsrIdModDt();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setUsrIdModDt((Timestamp)value);
            }
        }
        ,
        ApldFrmGp {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getApldFrmGp();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setApldFrmGp((String)value);
            }
        }
        ,
        TranParamType {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getTranParamType();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setTranParamType((Integer)value);
            }
        }
        ,
        ParamSpec {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getParamSpec();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setParamSpec((String)value);
            }
        }
        ,
        LovParamIdVO {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getLovParamIdVO();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovItemIdVO {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getLovItemIdVO();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        TransTlrncTypeVO {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getTransTlrncTypeVO();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovParamTypeVO {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getLovParamTypeVO();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LovFailActnVO {
            public Object get(MmQcParamItmVORowImpl obj) {
                return obj.getLovFailActnVO();
            }

            public void put(MmQcParamItmVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(MmQcParamItmVORowImpl object);

        public abstract void put(MmQcParamItmVORowImpl object, Object value);

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
    public static final int ITMID = AttributesEnum.ItmId.index();
    public static final int PARAMID = AttributesEnum.ParamId.index();
    public static final int STDVAL = AttributesEnum.StdVal.index();
    public static final int TLRNCLOWER = AttributesEnum.TlrncLower.index();
    public static final int TLRNCUPPER = AttributesEnum.TlrncUpper.index();
    public static final int TLRNCTYPE = AttributesEnum.TlrncType.index();
    public static final int FAILACTN = AttributesEnum.FailActn.index();
    public static final int LOWERLIMIT = AttributesEnum.LowerLimit.index();
    public static final int UPPERLIMIT = AttributesEnum.UpperLimit.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int APLDFRMGP = AttributesEnum.ApldFrmGp.index();
    public static final int TRANPARAMTYPE = AttributesEnum.TranParamType.index();
    public static final int PARAMSPEC = AttributesEnum.ParamSpec.index();
    public static final int LOVPARAMIDVO = AttributesEnum.LovParamIdVO.index();
    public static final int LOVITEMIDVO = AttributesEnum.LovItemIdVO.index();
    public static final int TRANSTLRNCTYPEVO = AttributesEnum.TransTlrncTypeVO.index();
    public static final int LOVPARAMTYPEVO = AttributesEnum.LovParamTypeVO.index();
    public static final int LOVFAILACTNVO = AttributesEnum.LovFailActnVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MmQcParamItmVORowImpl() {
    }

    /**
     * Gets MmQcParamItmEO entity object.
     * @return the MmQcParamItmEO
     */
    public MmQcParamItmEOImpl getMmQcParamItmEO() {
        return (MmQcParamItmEOImpl)getEntity(ENTITY_MMQCPARAMITMEO);
    }

    /**
     * Gets the attribute value for CLD_ID using the alias name CldId.
     * @return the CLD_ID
     */
    public String getCldId() {
        return (String) getAttributeInternal(CLDID);
    }

    /**
     * Sets <code>value</code> as attribute value for CLD_ID using the alias name CldId.
     * @param value value to set the CLD_ID
     */
    public void setCldId(String value) {
        setAttributeInternal(CLDID, value);
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
     * Gets the attribute value for ORG_ID using the alias name OrgId.
     * @return the ORG_ID
     */
    public String getOrgId() {
        return (String) getAttributeInternal(ORGID);
    }

    /**
     * Sets <code>value</code> as attribute value for ORG_ID using the alias name OrgId.
     * @param value value to set the ORG_ID
     */
    public void setOrgId(String value) {
        setAttributeInternal(ORGID, value);
    }

    /**
     * Gets the attribute value for ITM_ID using the alias name ItmId.
     * @return the ITM_ID
     */
    public String getItmId() {
        return (String) getAttributeInternal(ITMID);
    }

    /**
     * Sets <code>value</code> as attribute value for ITM_ID using the alias name ItmId.
     * @param value value to set the ITM_ID
     */
    public void setItmId(String value) {
        setAttributeInternal(ITMID, value);
    }

    /**
     * Gets the attribute value for PARAM_ID using the alias name ParamId.
     * @return the PARAM_ID
     */
    public String getParamId() {
        return (String) getAttributeInternal(PARAMID);
    }

    /**
     * Sets <code>value</code> as attribute value for PARAM_ID using the alias name ParamId.
     * @param value value to set the PARAM_ID
     */
    public void setParamId(String value) {
        setAttributeInternal(PARAMID, value);
    }

    /**
     * Gets the attribute value for STD_VAL using the alias name StdVal.
     * @return the STD_VAL
     */
    public Number getStdVal() {
        return (Number) getAttributeInternal(STDVAL);
    }

    /**
     * Sets <code>value</code> as attribute value for STD_VAL using the alias name StdVal.
     * @param value value to set the STD_VAL
     */
    public void setStdVal(Number value) {
        if(value == null){
            value = new Number(0);
            }
        if(value != null && getTlrncType() != null && getTlrncLower()!=null && getTlrncUpper()!=null){
            if(getTlrncType().equalsIgnoreCase("A") && getStdVal() != null){
                try{
                  Number  upprLimit = (Number)value.plus(getTlrncUpper());
                  Number  lowerLimit = (Number)value.minus(getTlrncLower());
                setUpperLimit(upprLimit);
                setLowerLimit(lowerLimit);
                }catch (NullPointerException e) {
                        }
            }else if(getTlrncType().equalsIgnoreCase("P") && getStdVal() != null)
            {
                            try{
                              Number  upprLimit = (Number)value.plus(getTlrncUpper().multiply(value).divide(new Number(100)));
                                Number  lowerLimit = (Number)value.minus(getTlrncLower().multiply(value).divide(new Number(100)));
                            setUpperLimit(upprLimit);
                                setLowerLimit(lowerLimit);
                            }catch (NullPointerException e) {
                                    }
                        }
        
        }
        setAttributeInternal(STDVAL, value);
    }

    /**
     * Gets the attribute value for TLRNC_LOWER using the alias name TlrncLower.
     * @return the TLRNC_LOWER
     */
    public Number getTlrncLower() {
        return (Number) getAttributeInternal(TLRNCLOWER);
    }

    /**
     * Sets <code>value</code> as attribute value for TLRNC_LOWER using the alias name TlrncLower.
     * @param value value to set the TLRNC_LOWER
     */
    public void setTlrncLower(Number value) {
        System.out.println("setTlrncLower---------------------------------------------------------");
        if(value == null){
            value = new Number(0);
            }
        if(value != null && getTlrncType() != null){
            if(getTlrncType().equalsIgnoreCase("A") && getStdVal() != null){
                try{
                  Number  lwrLimit = (Number)getStdVal().minus(value.abs());     // change value to abs Value           
                setLowerLimit(lwrLimit);
                
                
                }catch (NullPointerException e) {
                        }
            }else if(getTlrncType().equalsIgnoreCase("P") && getStdVal() != null)
            {
                            try{
                              Number  lwrLimit = (Number)getStdVal().minus((value.multiply(getStdVal()).divide(new Number(100))).abs()); // change value to abs Value     
                            setLowerLimit(lwrLimit);
                            
                            }catch (NullPointerException e) {
                                    }
                        }
        
        }

        setAttributeInternal(TLRNCLOWER, value);
    }

    /**
     * Gets the attribute value for TLRNC_UPPER using the alias name TlrncUpper.
     * @return the TLRNC_UPPER
     */
    public Number getTlrncUpper() {
        return (Number) getAttributeInternal(TLRNCUPPER);
    }

    /**
     * Sets <code>value</code> as attribute value for TLRNC_UPPER using the alias name TlrncUpper.
     * @param value value to set the TLRNC_UPPER
     */
    public void setTlrncUpper(Number value) {
        System.out.println("setTlrncUpper---------------------------------------------------------");
               if(value == null){
                   value = new Number(0);
                   }
               if(value != null && getTlrncType() != null){
                   if(getTlrncType().equalsIgnoreCase("A") && getStdVal() != null){
                       try{
                         Number  upprLimit = (Number)getStdVal().plus(value);
                       setUpperLimit(upprLimit);
                       }catch (NullPointerException e) {
                               }
                   }else if(getTlrncType().equalsIgnoreCase("P") && getStdVal() != null)
                   {
                                   Number stdVal = (Number)getStdVal().abs();
                                   try{
                                     Number  upprLimit = (Number)getStdVal().plus(value.multiply(stdVal).divide(new Number(100)));
                                   setUpperLimit(upprLimit);
                                   }catch (NullPointerException e) {
                                           }
                               }
               
               }
        setAttributeInternal(TLRNCUPPER, value);
    }

    /**
     * Gets the attribute value for TLRNC_TYPE using the alias name TlrncType.
     * @return the TLRNC_TYPE
     */
    public String getTlrncType() {
        return (String) getAttributeInternal(TLRNCTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for TLRNC_TYPE using the alias name TlrncType.
     * @param value value to set the TLRNC_TYPE
     */
    public void setTlrncType(String value) {
        if(value !=null && getTlrncLower()!=null && getTlrncUpper()!=null){
            System.out.println("setTlrncType---------------------------------------------------------");
            setTlrncLower(new Number(0));
            setTlrncUpper(new Number(0));
        }
        setAttributeInternal(TLRNCTYPE, value);
    }

    /**
     * Gets the attribute value for FAIL_ACTN using the alias name FailActn.
     * @return the FAIL_ACTN
     */
    public Integer getFailActn() {
        return (Integer) getAttributeInternal(FAILACTN);
    }

    /**
     * Sets <code>value</code> as attribute value for FAIL_ACTN using the alias name FailActn.
     * @param value value to set the FAIL_ACTN
     */
    public void setFailActn(Integer value) {
        setAttributeInternal(FAILACTN, value);
    }

    /**
     * Gets the attribute value for LOWER_LIMIT using the alias name LowerLimit.
     * @return the LOWER_LIMIT
     */
    public Number getLowerLimit() {
        return (Number) getAttributeInternal(LOWERLIMIT);
    }

    /**
     * Sets <code>value</code> as attribute value for LOWER_LIMIT using the alias name LowerLimit.
     * @param value value to set the LOWER_LIMIT
     */
    public void setLowerLimit(Number value) {
        setAttributeInternal(LOWERLIMIT, value);
    }

    /**
     * Gets the attribute value for UPPER_LIMIT using the alias name UpperLimit.
     * @return the UPPER_LIMIT
     */
    public Number getUpperLimit() {
        return (Number) getAttributeInternal(UPPERLIMIT);
    }

    /**
     * Sets <code>value</code> as attribute value for UPPER_LIMIT using the alias name UpperLimit.
     * @param value value to set the UPPER_LIMIT
     */
    public void setUpperLimit(Number value) {
        setAttributeInternal(UPPERLIMIT, value);
    }

    /**
     * Gets the attribute value for USR_ID_CREATE using the alias name UsrIdCreate.
     * @return the USR_ID_CREATE
     */
    public Integer getUsrIdCreate() {
        return (Integer) getAttributeInternal(USRIDCREATE);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_CREATE using the alias name UsrIdCreate.
     * @param value value to set the USR_ID_CREATE
     */
    public void setUsrIdCreate(Integer value) {
        setAttributeInternal(USRIDCREATE, value);
    }

    /**
     * Gets the attribute value for USR_ID_CREATE_DT using the alias name UsrIdCreateDt.
     * @return the USR_ID_CREATE_DT
     */
    public Timestamp getUsrIdCreateDt() {
        return (Timestamp) getAttributeInternal(USRIDCREATEDT);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_CREATE_DT using the alias name UsrIdCreateDt.
     * @param value value to set the USR_ID_CREATE_DT
     */
    public void setUsrIdCreateDt(Timestamp value) {
        setAttributeInternal(USRIDCREATEDT, value);
    }

    /**
     * Gets the attribute value for USR_ID_MOD using the alias name UsrIdMod.
     * @return the USR_ID_MOD
     */
    public Integer getUsrIdMod() {
        return (Integer) getAttributeInternal(USRIDMOD);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_MOD using the alias name UsrIdMod.
     * @param value value to set the USR_ID_MOD
     */
    public void setUsrIdMod(Integer value) {
        setAttributeInternal(USRIDMOD, value);
    }

    /**
     * Gets the attribute value for USR_ID_MOD_DT using the alias name UsrIdModDt.
     * @return the USR_ID_MOD_DT
     */
    public Timestamp getUsrIdModDt() {
        return (Timestamp) getAttributeInternal(USRIDMODDT);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_MOD_DT using the alias name UsrIdModDt.
     * @param value value to set the USR_ID_MOD_DT
     */
    public void setUsrIdModDt(Timestamp value) {
        setAttributeInternal(USRIDMODDT, value);
    }

    /**
     * Gets the attribute value for APLD_FRM_GP using the alias name ApldFrmGp.
     * @return the APLD_FRM_GP
     */
    public String getApldFrmGp() {
        return (String) getAttributeInternal(APLDFRMGP);
    }

    /**
     * Sets <code>value</code> as attribute value for APLD_FRM_GP using the alias name ApldFrmGp.
     * @param value value to set the APLD_FRM_GP
     */
    public void setApldFrmGp(String value) {
        setAttributeInternal(APLDFRMGP, value);
    }

    public Object resolvElDC(String data) {
      FacesContext fc = FacesContext.getCurrentInstance();
      Application app = fc.getApplication();
      ExpressionFactory elFactory = app.getExpressionFactory();
      ELContext elContext = fc.getELContext();
      ValueExpression valueExp =
     elFactory.createValueExpression
    (elContext, "#{data." + data + ".dataProvider}", Object.class);
            return valueExp.getValue(elContext);
        }
    /**
     * Gets the attribute value for TRAN_PARAM_TYPE using the alias name TranParamType.
     * @return the TRAN_PARAM_TYPE
     */
    public Integer getTranParamType() {
        QcSetupAMImpl am = (QcSetupAMImpl)resolvElDC("QcSetupAMDataControl");

        String inputParmId=null;
        Integer paramType=null;
        try{
        inputParmId=getParamId();
        }catch(NullPointerException npe){

        }
        if(inputParmId!=null){
        RowQualifier rq = new RowQualifier(am.getLovParamId());
         rq.setWhereClause("OrgId ='"+getOrgId()+"' and CldId = '"+getCldId()+"' and SlocId = "+getSlocId()+" and ParamId='"+inputParmId+"'");
        Row [] xx = am.getLovParamId().getFilteredRows(rq);
      //  Row[] xx=am.getLovParamId().getFilteredRows("ParamId", inputParmId);
        if(xx.length>0){
        paramType = (Integer)xx[0].getAttribute("ParamType");
        }
        return paramType;

        }else{
        return (Integer) getAttributeInternal(TRANPARAMTYPE);
            }
    }

    /**
     * Sets <code>value</code> as attribute value for TRAN_PARAM_TYPE using the alias name TranParamType.
     * @param value value to set the TRAN_PARAM_TYPE
     */
    public void setTranParamType(Integer value) {
        if(value ==275){
            setStdVal(new Number(1));
            setTlrncLower(new Number(0));
            setTlrncUpper(new Number(0));
        }else if(value==274){
            setStdVal(new Number(0));
            setTlrncLower(new Number(0));
            setTlrncUpper(new Number(0));
        }
        setAttributeInternal(TRANPARAMTYPE, value);
    }

    /**
     * Gets the attribute value for PARAM_SPEC using the alias name ParamSpec.
     * @return the PARAM_SPEC
     */
    public String getParamSpec() {
        return (String) getAttributeInternal(PARAMSPEC);
    }

    /**
     * Sets <code>value</code> as attribute value for PARAM_SPEC using the alias name ParamSpec.
     * @param value value to set the PARAM_SPEC
     */
    public void setParamSpec(String value) {
        setAttributeInternal(PARAMSPEC, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovParamIdVO.
     */
    public RowSet getLovParamIdVO() {
        return (RowSet)getAttributeInternal(LOVPARAMIDVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovItemIdVO.
     */
    public RowSet getLovItemIdVO() {
        return (RowSet)getAttributeInternal(LOVITEMIDVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> TransTlrncTypeVO.
     */
    public RowSet getTransTlrncTypeVO() {
        return (RowSet)getAttributeInternal(TRANSTLRNCTYPEVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovParamTypeVO.
     */
    public RowSet getLovParamTypeVO() {
        return (RowSet)getAttributeInternal(LOVPARAMTYPEVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LovFailActnVO.
     */
    public RowSet getLovFailActnVO() {
        return (RowSet)getAttributeInternal(LOVFAILACTNVO);
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
