package svccalltracing.model.entities;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import oracle.jbo.domain.Timestamp;

// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Dec 22 15:58:43 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class SvcCmCallTrcEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        SlocId,
        HoOrgId,
        OrgId,
        DocId,
        TrcSeqNo,
        EventId,
        EventDesc,
        EventDt,
        UsrIdCrt,
        CrtDt,
        UsrIdMod,
        ModDt;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

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
    public static final int CLDID = AttributesEnum.CldId.index();
    public static final int SLOCID = AttributesEnum.SlocId.index();
    public static final int HOORGID = AttributesEnum.HoOrgId.index();
    public static final int ORGID = AttributesEnum.OrgId.index();
    public static final int DOCID = AttributesEnum.DocId.index();
    public static final int TRCSEQNO = AttributesEnum.TrcSeqNo.index();
    public static final int EVENTID = AttributesEnum.EventId.index();
    public static final int EVENTDESC = AttributesEnum.EventDesc.index();
    public static final int EVENTDT = AttributesEnum.EventDt.index();
    public static final int USRIDCRT = AttributesEnum.UsrIdCrt.index();
    public static final int CRTDT = AttributesEnum.CrtDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int MODDT = AttributesEnum.ModDt.index();

    /**
     * This is the default constructor (do not remove).
     */
    public SvcCmCallTrcEOImpl() {
    }

    /**
     * Gets the attribute value for CldId, using the alias name CldId.
     * @return the value of CldId
     */
    public String getCldId() {
        return (String) getAttributeInternal(CLDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CldId.
     * @param value value to set the CldId
     */
    public void setCldId(String value) {
        setAttributeInternal(CLDID, value);
    }

    /**
     * Gets the attribute value for SlocId, using the alias name SlocId.
     * @return the value of SlocId
     */
    public Integer getSlocId() {
        return (Integer) getAttributeInternal(SLOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for SlocId.
     * @param value value to set the SlocId
     */
    public void setSlocId(Integer value) {
        setAttributeInternal(SLOCID, value);
    }

    /**
     * Gets the attribute value for HoOrgId, using the alias name HoOrgId.
     * @return the value of HoOrgId
     */
    public String getHoOrgId() {
        return (String) getAttributeInternal(HOORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for HoOrgId.
     * @param value value to set the HoOrgId
     */
    public void setHoOrgId(String value) {
        setAttributeInternal(HOORGID, value);
    }

    /**
     * Gets the attribute value for OrgId, using the alias name OrgId.
     * @return the value of OrgId
     */
    public String getOrgId() {
        return (String) getAttributeInternal(ORGID);
    }

    /**
     * Sets <code>value</code> as the attribute value for OrgId.
     * @param value value to set the OrgId
     */
    public void setOrgId(String value) {
        setAttributeInternal(ORGID, value);
    }

    /**
     * Gets the attribute value for DocId, using the alias name DocId.
     * @return the value of DocId
     */
    public String getDocId() {
        return (String) getAttributeInternal(DOCID);
    }

    /**
     * Sets <code>value</code> as the attribute value for DocId.
     * @param value value to set the DocId
     */
    public void setDocId(String value) {
        setAttributeInternal(DOCID, value);
    }

    /**
     * Gets the attribute value for TrcSeqNo, using the alias name TrcSeqNo.
     * @return the value of TrcSeqNo
     */
    public Integer getTrcSeqNo() {
        return (Integer) getAttributeInternal(TRCSEQNO);
    }

    /**
     * Sets <code>value</code> as the attribute value for TrcSeqNo.
     * @param value value to set the TrcSeqNo
     */
    public void setTrcSeqNo(Integer value) {
        setAttributeInternal(TRCSEQNO, value);
    }

    /**
     * Gets the attribute value for EventId, using the alias name EventId.
     * @return the value of EventId
     */
    public Integer getEventId() {
        return (Integer) getAttributeInternal(EVENTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for EventId.
     * @param value value to set the EventId
     */
    public void setEventId(Integer value) {
        setAttributeInternal(EVENTID, value);
    }

    /**
     * Gets the attribute value for EventDesc, using the alias name EventDesc.
     * @return the value of EventDesc
     */
    public String getEventDesc() {
        return (String) getAttributeInternal(EVENTDESC);
    }

    /**
     * Sets <code>value</code> as the attribute value for EventDesc.
     * @param value value to set the EventDesc
     */
    public void setEventDesc(String value) {
        setAttributeInternal(EVENTDESC, value);
    }

    /**
     * Gets the attribute value for EventDt, using the alias name EventDt.
     * @return the value of EventDt
     */
    public Timestamp getEventDt() {
        return (Timestamp) getAttributeInternal(EVENTDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for EventDt.
     * @param value value to set the EventDt
     */
    public void setEventDt(Timestamp value) {
        setAttributeInternal(EVENTDT, value);
    }

    /**
     * Gets the attribute value for UsrIdCrt, using the alias name UsrIdCrt.
     * @return the value of UsrIdCrt
     */
    public Integer getUsrIdCrt() {
        return (Integer) getAttributeInternal(USRIDCRT);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdCrt.
     * @param value value to set the UsrIdCrt
     */
    public void setUsrIdCrt(Integer value) {
        setAttributeInternal(USRIDCRT, value);
    }

    /**
     * Gets the attribute value for CrtDt, using the alias name CrtDt.
     * @return the value of CrtDt
     */
    public Timestamp getCrtDt() {
        return (Timestamp) getAttributeInternal(CRTDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for CrtDt.
     * @param value value to set the CrtDt
     */
    public void setCrtDt(Timestamp value) {
        setAttributeInternal(CRTDT, value);
    }

    /**
     * Gets the attribute value for UsrIdMod, using the alias name UsrIdMod.
     * @return the value of UsrIdMod
     */
    public Integer getUsrIdMod() {
        return (Integer) getAttributeInternal(USRIDMOD);
    }

    /**
     * Sets <code>value</code> as the attribute value for UsrIdMod.
     * @param value value to set the UsrIdMod
     */
    public void setUsrIdMod(Integer value) {
        setAttributeInternal(USRIDMOD, value);
    }

    /**
     * Gets the attribute value for ModDt, using the alias name ModDt.
     * @return the value of ModDt
     */
    public Timestamp getModDt() {
        return (Timestamp) getAttributeInternal(MODDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for ModDt.
     * @param value value to set the ModDt
     */
    public void setModDt(Timestamp value) {
        setAttributeInternal(MODDT, value);
    }

    /**
     * @param cldId key constituent
     * @param slocId key constituent
     * @param orgId key constituent
     * @param docId key constituent
     * @param trcSeqNo key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String cldId, Integer slocId, String orgId, String docId, Integer trcSeqNo) {
        return new Key(new Object[] { cldId, slocId, orgId, docId, trcSeqNo });
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("svccalltracing.model.entities.SvcCmCallTrcEO");
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }
    protected void create(AttributeList attributeList) {
        String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}").toString();
        Integer slocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}").toString());
        String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}").toString();
        String hoOrgId=resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}").toString();
       /*   setCldId(cldId);
        setSlocId(slocId);
        setOrgId(orgId);
        setHoOrgId(hoOrgId); */
        Integer usrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        setUsrIdCrt(usrId);
        setCrtDt(new Timestamp(System.currentTimeMillis()));
        setEventDt(new Timestamp(System.currentTimeMillis()));
        /*   String docId = (String) (callStoredFunction(Types.VARCHAR, "app.get_txn_id (?,?,?,?,?,?)", new Object[] {
                                                    cldId, slocId, orgId, usrId, 23004, new Integer(0)
        }));
        setDocId(docId);
     */
        super.create(attributeList);
    }
    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
          CallableStatement st = null;
          try {
               st = this.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
               st.registerOutParameter(1, sqlReturnType);
              if (bindVars != null) {
                  for (int z = 0; z < bindVars.length; z++) {
                      st.setObject(z + 2, bindVars[z]);
                  }
              }
              st.executeUpdate();

              return st.getObject(1);
          } catch (SQLException e) {
              e.printStackTrace();
             int end = e.getMessage().indexOf("\n");
              //  throw new JboException(e.getMessage().substring(11, end));
              String msg = e.getMessage();
              FacesMessage ermsg = new FacesMessage(msg);
              ermsg.setSeverity(FacesMessage.SEVERITY_ERROR);
              FacesContext.getCurrentInstance().addMessage(null, ermsg);
            //  throw new JboException(e);
             
              return null;
          } finally {
              if (st != null) {
                  try {
                      st.close();
                  } catch (SQLException e) {
                        e.printStackTrace();}
              }
          }
      }
    
    /**
     * Add entity remove logic in this method.
     */
    public void remove() {
        super.remove();
    }

    /**
     * Add locking logic here.
     */
    public void lock() {
      //  super.lock();
    }

    /**
     * Custom DML update/insert/delete logic here.
     * @param operation the operation type
     * @param e the transaction event
     */
    protected void doDML(int operation, TransactionEvent e) {
        super.doDML(operation, e);
    }
}

