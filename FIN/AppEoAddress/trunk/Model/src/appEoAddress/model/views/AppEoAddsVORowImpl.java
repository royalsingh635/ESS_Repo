package appEoAddress.model.views;

import appEoAddress.model.entities.AppEoAddsEOImpl;
import appEoAddress.model.service.AppEoAddressAMImpl;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;

import javax.faces.context.FacesContext;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;



import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Date;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Dec 10 10:16:54 IST 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppEoAddsVORowImpl extends ViewRowImpl {


    public static final int ENTITY_APPEOADDSEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        SlocId {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getSlocId();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setSlocId((Integer) value);
            }
        }
        ,
        EoId {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getEoId();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setEoId((Integer) value);
            }
        }
        ,
        AddsId {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getAddsId();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setAddsId((String) value);
            }
        }
        ,
        BillAdds {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getBillAdds();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setBillAdds((String) value);
            }
        }
        ,
        ShipAdds {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getShipAdds();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setShipAdds((String) value);
            }
        }
        ,
        BillAddsDflt {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getBillAddsDflt();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setBillAddsDflt((String) value);
            }
        }
        ,
        ShipAddsDflt {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getShipAddsDflt();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setShipAddsDflt((String) value);
            }
        }
        ,
        Actv {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getActv();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setActv((String) value);
            }
        }
        ,
        InactvResn {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getInactvResn();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setInactvResn((String) value);
            }
        }
        ,
        InactvDt {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getInactvDt();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setInactvDt((Date) value);
            }
        }
        ,
        UsrIdCreate {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getUsrIdCreate();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setUsrIdCreate((Integer) value);
            }
        }
        ,
        UsrIdCreateDt {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getUsrIdCreateDt();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UsrIdMod {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getUsrIdMod();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setUsrIdMod((Integer) value);
            }
        }
        ,
        UsrIdModDt {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getUsrIdModDt();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setUsrIdModDt((Date) value);
            }
        }
        ,
        EntId {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getEntId();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setEntId((Integer) value);
            }
        }
        ,
        TransAddName {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getTransAddName();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setTransAddName((String) value);
            }
        }
        ,
        trans_AddsId {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.gettrans_AddsId();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.settrans_AddsId((String) value);
            }
        }
        ,
        CldId {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getCldId();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setCldId((String) value);
            }
        }
        ,
        HoOrgId {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getHoOrgId();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setHoOrgId((String) value);
            }
        }
        ,
        OrgId {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getOrgId();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setOrgId((String) value);
            }
        }
        ,
        SvcLoc {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getSvcLoc();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setSvcLoc((String) value);
            }
        }
        ,
        LocRefId {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getLocRefId();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setLocRefId((String) value);
            }
        }
        ,
        LocRefNm {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getLocRefNm();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setLocRefNm((String) value);
            }
        }
        ,
        CtcNo {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getCtcNo();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setCtcNo((String) value);
            }
        }
        ,
        CtcPers {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getCtcPers();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setCtcPers((String) value);
            }
        }
        ,
        UserLOV {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getUserLOV();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        AddressForEoAddLOV1 {
            public Object get(AppEoAddsVORowImpl obj) {
                return obj.getAddressForEoAddLOV1();
            }

            public void put(AppEoAddsVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        static AttributesEnum[] vals = null;
        ;
        private static int firstIndex = 0;

        public abstract Object get(AppEoAddsVORowImpl object);

        public abstract void put(AppEoAddsVORowImpl object, Object value);

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
    public static final int ADDSID = AttributesEnum.AddsId.index();
    public static final int BILLADDS = AttributesEnum.BillAdds.index();
    public static final int SHIPADDS = AttributesEnum.ShipAdds.index();
    public static final int BILLADDSDFLT = AttributesEnum.BillAddsDflt.index();
    public static final int SHIPADDSDFLT = AttributesEnum.ShipAddsDflt.index();
    public static final int ACTV = AttributesEnum.Actv.index();
    public static final int INACTVRESN = AttributesEnum.InactvResn.index();
    public static final int INACTVDT = AttributesEnum.InactvDt.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int ENTID = AttributesEnum.EntId.index();
    public static final int TRANSADDNAME = AttributesEnum.TransAddName.index();
    public static final int TRANS_ADDSID = AttributesEnum.trans_AddsId.index();
    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int SVCLOC = AttributesEnum.SvcLoc.index();
    public static final int LOCREFID = AttributesEnum.LocRefId.index();
    public static final int LOCREFNM = AttributesEnum.LocRefNm.index();
    public static final int CTCNO = AttributesEnum.CtcNo.index();
    public static final int CTCPERS = AttributesEnum.CtcPers.index();
    public static final int USERLOV = AttributesEnum.UserLOV.index();
    public static final int ADDRESSFOREOADDLOV1 = AttributesEnum.AddressForEoAddLOV1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public AppEoAddsVORowImpl() {
    }

    /**
     * Gets AppEoAddsEO entity object.
     * @return the AppEoAddsEO
     */
    public AppEoAddsEOImpl getAppEoAddsEO() {
        return (AppEoAddsEOImpl)getEntity(ENTITY_APPEOADDSEO);
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
     * Gets the attribute value for EO_ID using the alias name EoId.
     * @return the EO_ID
     */
    public Integer getEoId() {
        return (Integer) getAttributeInternal(EOID);
    }

    /**
     * Sets <code>value</code> as attribute value for EO_ID using the alias name EoId.
     * @param value value to set the EO_ID
     */
    public void setEoId(Integer value) {
        setAttributeInternal(EOID, value);
    }

    /**
     * Gets the attribute value for ADDS_ID using the alias name AddsId.
     * @return the ADDS_ID
     */
    public String getAddsId() {
        AppEoAddressAMImpl am = (AppEoAddressAMImpl)resolvElDC("AppEoAddressAMDataControl");
         return (String) getAttributeInternal(ADDSID);
    }

    /**
     * Sets <code>value</code> as attribute value for ADDS_ID using the alias name AddsId.
     * @param value value to set the ADDS_ID
     */
    public void setAddsId(String value) {
        setAttributeInternal(ADDSID, value);
    }

    /**
     * Gets the attribute value for BILL_ADDS using the alias name BillAdds.
     * @return the BILL_ADDS
     */
    public String getBillAdds() {
        return (String) getAttributeInternal(BILLADDS);
    }

    /**
     * Sets <code>value</code> as attribute value for BILL_ADDS using the alias name BillAdds.
     * @param value value to set the BILL_ADDS
     */
    public void setBillAdds(String value) {
        setAttributeInternal(BILLADDS, value);
    }

    /**
     * Gets the attribute value for SHIP_ADDS using the alias name ShipAdds.
     * @return the SHIP_ADDS
     */
    public String getShipAdds() {
        return (String) getAttributeInternal(SHIPADDS);
    }

    /**
     * Sets <code>value</code> as attribute value for SHIP_ADDS using the alias name ShipAdds.
     * @param value value to set the SHIP_ADDS
     */
    public void setShipAdds(String value) {
        setAttributeInternal(SHIPADDS, value);
    }

    /**
     * Gets the attribute value for BILL_ADDS_DFLT using the alias name BillAddsDflt.
     * @return the BILL_ADDS_DFLT
     */
    public String getBillAddsDflt() {
        return (String) getAttributeInternal(BILLADDSDFLT);
    }

    /**
     * Sets <code>value</code> as attribute value for BILL_ADDS_DFLT using the alias name BillAddsDflt.
     * @param value value to set the BILL_ADDS_DFLT
     */
    public void setBillAddsDflt(String value) {
        setAttributeInternal(BILLADDSDFLT, value);
    }

    /**
     * Gets the attribute value for SHIP_ADDS_DFLT using the alias name ShipAddsDflt.
     * @return the SHIP_ADDS_DFLT
     */
    public String getShipAddsDflt() {
        return (String) getAttributeInternal(SHIPADDSDFLT);
    }

    /**
     * Sets <code>value</code> as attribute value for SHIP_ADDS_DFLT using the alias name ShipAddsDflt.
     * @param value value to set the SHIP_ADDS_DFLT
     */
    public void setShipAddsDflt(String value) {
        setAttributeInternal(SHIPADDSDFLT, value);
    }

    /**
     * Gets the attribute value for ACTV using the alias name Actv.
     * @return the ACTV
     */
    public String getActv() {
        return (String) getAttributeInternal(ACTV);
    }

    /**
     * Sets <code>value</code> as attribute value for ACTV using the alias name Actv.
     * @param value value to set the ACTV
     */
    public void setActv(String value) {
        setAttributeInternal(ACTV, value);
    }

    /**
     * Gets the attribute value for INACTV_RESN using the alias name InactvResn.
     * @return the INACTV_RESN
     */
    public String getInactvResn() {
        return (String) getAttributeInternal(INACTVRESN);
    }

    /**
     * Sets <code>value</code> as attribute value for INACTV_RESN using the alias name InactvResn.
     * @param value value to set the INACTV_RESN
     */
    public void setInactvResn(String value) {
        setAttributeInternal(INACTVRESN, value);
    }

    /**
     * Gets the attribute value for INACTV_DT using the alias name InactvDt.
     * @return the INACTV_DT
     */
    public Date getInactvDt() {
        return (Date) getAttributeInternal(INACTVDT);
    }

    /**
     * Sets <code>value</code> as attribute value for INACTV_DT using the alias name InactvDt.
     * @param value value to set the INACTV_DT
     */
    public void setInactvDt(Date value) {
        setAttributeInternal(INACTVDT, value);
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
    public Date getUsrIdCreateDt() {
        return (Date) getAttributeInternal(USRIDCREATEDT);
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
    public Date getUsrIdModDt() {
        return (Date) getAttributeInternal(USRIDMODDT);
    }

    /**
     * Sets <code>value</code> as attribute value for USR_ID_MOD_DT using the alias name UsrIdModDt.
     * @param value value to set the USR_ID_MOD_DT
     */
    public void setUsrIdModDt(Date value) {
        setAttributeInternal(USRIDMODDT, value);
    }

    /**
     * Gets the attribute value for ENT_ID using the alias name EntId.
     * @return the ENT_ID
     */
    public Integer getEntId() {
        return (Integer) getAttributeInternal(ENTID);
    }

    /**
     * Sets <code>value</code> as attribute value for ENT_ID using the alias name EntId.
     * @param value value to set the ENT_ID
     */
    public void setEntId(Integer value) {
        setAttributeInternal(ENTID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransAddName.
     * @return the TransAddName
     */
    
     public Object resolvElDC(String data) {
         FacesContext fc = FacesContext.getCurrentInstance();
         Application app = fc.getApplication();
         ExpressionFactory elFactory = app.getExpressionFactory();
         ELContext elContext = fc.getELContext();
         ValueExpression valueExp =
             elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
         return valueExp.getValue(elContext);
     }
    
    public String getTransAddName() {
        AppEoAddressAMImpl am = (AppEoAddressAMImpl)resolvElDC("AppEoAddressAMDataControl");

              String inputAdd=null;
              String addDesc=null;
           //   try{
              inputAdd=getAddsId();
                  //System.out.println("address id in row impl is==="+inputAdd);
              
              //}catch(NullPointerException npe){

            //  }
              
              if(inputAdd!=null){
            ViewObjectImpl addressLOV = am.getAddressLOV1();
            addressLOV.setNamedWhereClauseParam("BindSlocId", getSlocId());
            addressLOV.setNamedWhereClauseParam("BindCldId", getCldId());
            System.out.println("getAddsId = "+getAddsId()+" inputAdd = "+inputAdd);
            addressLOV.setNamedWhereClauseParam("BindAddsId", inputAdd);
            addressLOV.executeQuery();
            Row[] xx = addressLOV.getAllRowsInRange();
            System.out.println("rows = "+xx.length);
            /*  am.getAddressLOV1().executeQuery();
              am.getAddressForEoAddLOV1().executeQuery(); */
            //  Row[] xx=am.getAddressLOV1().getFilteredRows("AddressId", inputAdd);
             
              if(xx.length>0){
              addDesc= xx[0].getAttribute("Address").toString();
              System.out.println("addDesc = "+addDesc);
              }
             
              return addDesc;

              }else{
              return (String) getAttributeInternal(TRANSADDNAME);
          }
        
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransAddName.
     * @param value value to set the  TransAddName
     */
    public void setTransAddName(String value) {
        setAttributeInternal(TRANSADDNAME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute trans_AddsId.
     * @return the trans_AddsId
     */
    public String gettrans_AddsId() {
        return (String) getAttributeInternal(TRANS_ADDSID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute trans_AddsId.
     * @param value value to set the  trans_AddsId
     */
    public void settrans_AddsId(String value) {
        
        setAttributeInternal(TRANS_ADDSID, value);
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
     * Gets the attribute value for HO_ORG_ID using the alias name HoOrgId.
     * @return the HO_ORG_ID
     */
    public String getHoOrgId() {
        return (String) getAttributeInternal(HOORGID);
    }

    /**
     * Sets <code>value</code> as attribute value for HO_ORG_ID using the alias name HoOrgId.
     * @param value value to set the HO_ORG_ID
     */
    public void setHoOrgId(String value) {
        setAttributeInternal(HOORGID, value);
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
     * Gets the attribute value for SVC_LOC using the alias name SvcLoc.
     * @return the SVC_LOC
     */
    public String getSvcLoc() {
        return (String) getAttributeInternal(SVCLOC);
    }

    /**
     * Sets <code>value</code> as attribute value for SVC_LOC using the alias name SvcLoc.
     * @param value value to set the SVC_LOC
     */
    public void setSvcLoc(String value) {
        setAttributeInternal(SVCLOC, value);
    }

    /**
     * Gets the attribute value for LOC_REF_ID using the alias name LocRefId.
     * @return the LOC_REF_ID
     */
    public String getLocRefId() {
        return (String) getAttributeInternal(LOCREFID);
    }

    /**
     * Sets <code>value</code> as attribute value for LOC_REF_ID using the alias name LocRefId.
     * @param value value to set the LOC_REF_ID
     */
    public void setLocRefId(String value) {
        setAttributeInternal(LOCREFID, value);
    }

    /**
     * Gets the attribute value for LOC_REF_NM using the alias name LocRefNm.
     * @return the LOC_REF_NM
     */
    public String getLocRefNm() {
        return (String) getAttributeInternal(LOCREFNM);
    }

    /**
     * Sets <code>value</code> as attribute value for LOC_REF_NM using the alias name LocRefNm.
     * @param value value to set the LOC_REF_NM
     */
    public void setLocRefNm(String value) {
        setAttributeInternal(LOCREFNM, value);
    }

    /**
     * Gets the attribute value for CTC_NO using the alias name CtcNo.
     * @return the CTC_NO
     */
    public String getCtcNo() {
        return (String) getAttributeInternal(CTCNO);
    }

    /**
     * Sets <code>value</code> as attribute value for CTC_NO using the alias name CtcNo.
     * @param value value to set the CTC_NO
     */
    public void setCtcNo(String value) {
        setAttributeInternal(CTCNO, value);
    }

    /**
     * Gets the attribute value for CTC_PERS using the alias name CtcPers.
     * @return the CTC_PERS
     */
    public String getCtcPers() {
        return (String) getAttributeInternal(CTCPERS);
    }

    /**
     * Sets <code>value</code> as attribute value for CTC_PERS using the alias name CtcPers.
     * @param value value to set the CTC_PERS
     */
    public void setCtcPers(String value) {
        setAttributeInternal(CTCPERS, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> UserLOV.
     */
    public RowSet getUserLOV() {
        return (RowSet)getAttributeInternal(USERLOV);
    }


    /**
     * Gets the view accessor <code>RowSet</code> AddressForEoAddLOV1.
     */
    public RowSet  getAddressForEoAddLOV1() {
      //  return getModifiedEoAdd();
       return (RowSet)getAttributeInternal(ADDRESSFOREOADDLOV1);
    }

    public RowSet getModifiedEoAdd(){
         AppEoAddressAMImpl am = (AppEoAddressAMImpl)resolvElDC("AppEoAddressAMDataControl");
         am.getAddressForEoAddLOV1().setWhereClause(null);
         am.getAddressForEoAddLOV1().executeQuery();
         am.getAddressForEoAddLOV1().setWhereClause("ADDRESS_ID not in (select b.adds_ID from APP$EO$ADDS b where b.eo_id=nvl("+getEoId()+",b.eo_id) and b.sloc_id=nvl("+getSlocId()+",b.sloc_id) and b.cld_id=nvl('"+getCldId()+"',b.cld_id) and b.ho_org_id=nvl('"+getHoOrgId()+"',b.ho_org_id))");
         am.getAddressForEoAddLOV1().executeQuery();
         RowSet r=am.getAddressForEoAddLOV1().getRowSet();
         return r;
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
