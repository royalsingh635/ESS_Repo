package mnfProductionOrderApp.model.views;

import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;

import mnfProductionOrderApp.model.entities.MnfPdoSRCEOImpl;
import mnfProductionOrderApp.model.services.MNFProductionorderAppAMImpl;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.domain.NClobDomain;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Sep 23 12:17:39 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MnfPdoSRCVORowImpl extends ViewRowImpl {


    public static final int ENTITY_MNFPDOSRCEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CldId,
        SlocId,
        HoOrgId,
        OrgId,
        DocId,
        SrcType,
        SrcDocId,
        ItmId,
        ItmQty,
        DlvDt,
        SoPriority,
        UsrIdCreate,
        UsrIdCreateDt,
        UsrIdMod,
        UsrIdModDt,
        EoId,
        TransItmId,
        TransSO,
        TransItemName,
        TransTotQty,
        TransCustName,
        TransSrcBasis,
        TransKey,
        LOVPdoBasisVO1,
        LOVPdoPriorityVO1,
        LOVSupplyorderVO1,
        LOVSoSrcBasisVO,
        LOVSOCustVO;
        static AttributesEnum[] vals = null;
        ;
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
    public static final int SRCTYPE = AttributesEnum.SrcType.index();
    public static final int SRCDOCID = AttributesEnum.SrcDocId.index();
    public static final int ITMID = AttributesEnum.ItmId.index();
    public static final int ITMQTY = AttributesEnum.ItmQty.index();
    public static final int DLVDT = AttributesEnum.DlvDt.index();
    public static final int SOPRIORITY = AttributesEnum.SoPriority.index();
    public static final int USRIDCREATE = AttributesEnum.UsrIdCreate.index();
    public static final int USRIDCREATEDT = AttributesEnum.UsrIdCreateDt.index();
    public static final int USRIDMOD = AttributesEnum.UsrIdMod.index();
    public static final int USRIDMODDT = AttributesEnum.UsrIdModDt.index();
    public static final int EOID = AttributesEnum.EoId.index();
    public static final int TRANSITMID = AttributesEnum.TransItmId.index();
    public static final int TRANSSO = AttributesEnum.TransSO.index();
    public static final int TRANSITEMNAME = AttributesEnum.TransItemName.index();
    public static final int TRANSTOTQTY = AttributesEnum.TransTotQty.index();
    public static final int TRANSCUSTNAME = AttributesEnum.TransCustName.index();
    public static final int TRANSSRCBASIS = AttributesEnum.TransSrcBasis.index();
    public static final int TRANSKEY = AttributesEnum.TransKey.index();
    public static final int LOVPDOBASISVO1 = AttributesEnum.LOVPdoBasisVO1.index();
    public static final int LOVPDOPRIORITYVO1 = AttributesEnum.LOVPdoPriorityVO1.index();
    public static final int LOVSUPPLYORDERVO1 = AttributesEnum.LOVSupplyorderVO1.index();
    public static final int LOVSOSRCBASISVO = AttributesEnum.LOVSoSrcBasisVO.index();
    public static final int LOVSOCUSTVO = AttributesEnum.LOVSOCustVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MnfPdoSRCVORowImpl() {
    }

    /**
     * Gets MnfPdoSRCEO entity object.
     * @return the MnfPdoSRCEO
     */
    public MnfPdoSRCEOImpl getMnfPdoSRCEO() {
        return (MnfPdoSRCEOImpl) getEntity(ENTITY_MNFPDOSRCEO);
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
     * Gets the attribute value for DOC_ID using the alias name DocId.
     * @return the DOC_ID
     */
    public String getDocId() {
        return (String) getAttributeInternal(DOCID);
    }

    /**
     * Sets <code>value</code> as attribute value for DOC_ID using the alias name DocId.
     * @param value value to set the DOC_ID
     */
    public void setDocId(String value) {
        setAttributeInternal(DOCID, value);
    }

    /**
     * Gets the attribute value for SRC_TYPE using the alias name SrcType.
     * @return the SRC_TYPE
     */
    public Integer getSrcType() {
        return (Integer) getAttributeInternal(SRCTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for SRC_TYPE using the alias name SrcType.
     * @param value value to set the SRC_TYPE
     */
    public void setSrcType(Integer value) {
        setAttributeInternal(SRCTYPE, value);
    }

    /**
     * Gets the attribute value for SRC_DOC_ID using the alias name SrcDocId.
     * @return the SRC_DOC_ID
     */
    public String getSrcDocId() {
     //   System.out.println("SALES order getter :" +  (String) getAttributeInternal(SRCDOCID));
        return (String) getAttributeInternal(SRCDOCID);
    }

    /**
     * Sets <code>value</code> as attribute value for SRC_DOC_ID using the alias name SrcDocId.
     * @param value value to set the SRC_DOC_ID
     */
    public void setSrcDocId(String value) {
      //  System.out.println("SALES order to be set as :" +  value);
        if (getSrcType() == 95) {
            
            if (!getAM().duplicateSalesOrder(getTransSO())) {
               
                setAttributeInternal(SRCDOCID, value);
            } else {
                String saveMsg = "Duplicate Sales Order !";
                showFacesMsg(saveMsg.toString(), null, FacesMessage.SEVERITY_ERROR, null);
                setTransSO("");
            }
        }else if (getSrcType() == 96) {
            if (!getAM().duplicateSalesForecast(getTransSO(), getDlvDt())) {
                setAttributeInternal(SRCDOCID, value);
            } else {
                String saveMsg = "Duplicate  Sales Forecast is Selected !";
                showFacesMsg(saveMsg.toString(), null, FacesMessage.SEVERITY_ERROR, null);
                setTransSO("");
            }
        }else if (getSrcType() == 147) {
            if (!getAM().duplicateSalesOrder(getTransSO())) {
                setAttributeInternal(SRCDOCID, value);
            } else {
                String saveMsg = "Duplicate  SubContracting Order is Selected !";
                showFacesMsg(saveMsg.toString(), null, FacesMessage.SEVERITY_ERROR, null);
             //   setTransSO("");
            }
        }else if (getSrcType() == 94) {
           // System.out.println("Nisha !!" + value);
            setAttributeInternal(SRCDOCID, value);
        }
        
        
        
        
    }

    /**
     * Showing an error message.
     *
     * */
    public void showFacesMsg(String msgHdr, String msgDtl, javax.faces.application.FacesMessage.Severity msgSeverity,
                             String msgMode) {

        FacesMessage msg = new FacesMessage(msgHdr);
        msg.setDetail(msgDtl);
        msg.setSeverity(msgSeverity);
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    /**
     * Gets the attribute value for ITM_ID using the alias name ItmId.
     * @return the ITM_ID
     */
    public String getItmId() {
     //   System.out.println("Getter Item " + (String) getAttributeInternal(ITMID));
        return (String) getAttributeInternal(ITMID);
    }

    /**
     * Sets <code>value</code> as attribute value for ITM_ID using the alias name ItmId.
     * @param value value to set the ITM_ID
     */
    public void setItmId(String value) {
    // System.out.println("Item to be set as :" +  value);
        if(value == null)
          setAttributeInternal(ITMID, getAM().getMnfPDOVO1().getCurrentRow().getAttribute("OutptItmId").toString());
    }

    /**
     * Gets the attribute value for ITM_QTY using the alias name ItmQty.
     * @return the ITM_QTY
     */
    public Number getItmQty() {
        return (Number) getAttributeInternal(ITMQTY);
    }

    /**
     * Sets <code>value</code> as attribute value for ITM_QTY using the alias name ItmQty.
     * @param value value to set the ITM_QTY
     */
    public void setItmQty(Number value) {
        setAttributeInternal(ITMQTY, value);
    }

    /**
     * Gets the attribute value for DLV_DT using the alias name DlvDt.
     * @return the DLV_DT
     */
    public Timestamp getDlvDt() {
        return (Timestamp) getAttributeInternal(DLVDT);
    }

    /**
     * Sets <code>value</code> as attribute value for DLV_DT using the alias name DlvDt.
     * @param value value to set the DLV_DT
     */
    public void setDlvDt(Timestamp value) {
        // if(!getAM().duplicateSalesOrder(getTransSO()))
        setAttributeInternal(DLVDT, value);
    }

    /**
     * Gets the attribute value for SO_PRIORITY using the alias name SoPriority.
     * @return the SO_PRIORITY
     */
    public Integer getSoPriority() {
        return (Integer) getAttributeInternal(SOPRIORITY);
    }

    /**
     * Sets <code>value</code> as attribute value for SO_PRIORITY using the alias name SoPriority.
     * @param value value to set the SO_PRIORITY
     */
    public void setSoPriority(Integer value) {
        setAttributeInternal(SOPRIORITY, value);
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
     * Gets the attribute value for the calculated attribute TransItmId.
     * @return the TransItmId
     */
    public String getTransItmId() {
        return (String) getAttributeInternal(TRANSITMID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransItmId.
     * @param value value to set the  TransItmId
     */
    public void setTransItmId(String value) {
        setAttributeInternal(TRANSITMID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransSO.
     * @return the TransSO
     */

    Number totalQty = new Number(0);
    String custName = null;

    public String getTransSO() {

        if (getSrcDocId() != null && getSrcType() == 95) {

            // ViewObjectImpl wsResimpl = getAM().getLOVSupplyorderVO2();
            //wsResimpl.setNamedWhereClauseParam("BindOrg", getOrgId());
            //  wsResimpl.setNamedWhereClauseParam("BindItemId", getItmId());
            // wsResimpl.setNamedWhereClauseParam("BindItemId", getItmId());
            //  wsResimpl.executeQuery();
            //System.out.println("INSIDE........ !!");
            ViewObjectImpl wsResimpl = getAM().getLOVSalesOrderIdVO1();
            wsResimpl.setNamedWhereClauseParam("BindCldId", getCldId());
            wsResimpl.setNamedWhereClauseParam("BindSlocId", getSlocId());
            wsResimpl.setNamedWhereClauseParam("BindHoOrgId", getHoOrgId());
            wsResimpl.setNamedWhereClauseParam("BindOrgId", getOrgId());
            wsResimpl.setNamedWhereClauseParam("BindDocId", getSrcDocId());
            wsResimpl.setNamedWhereClauseParam("BindItmId", getItmId());

            wsResimpl.executeQuery();

            Row[] filteredRows = wsResimpl.getFilteredRows("DocId", getSrcDocId());

            if (filteredRows.length > 0) {
                if (filteredRows[0].getAttribute("SoId") != null) {
                   // totalQty = Integer.parseInt(filteredRows[0].getAttribute("ItmQty").toString());
                   totalQty =(Number)filteredRows[0].getAttribute("ItmQty");
                    custName = filteredRows[0].getAttribute("EoNm").toString();

                    return filteredRows[0].getAttribute("SoId").toString();

                }
            }
        } else if (getSrcDocId() != null && getSrcType() == 96) {

            ViewObjectImpl wsResimpl = getAM().getLOVSalesForecastVO1();
            wsResimpl.setNamedWhereClauseParam("BindCldId", getCldId());
            wsResimpl.setNamedWhereClauseParam("BindSlocId", getSlocId());
            wsResimpl.setNamedWhereClauseParam("BindHoOrgId", getHoOrgId());
            wsResimpl.setNamedWhereClauseParam("BindOrgId", getOrgId());
            wsResimpl.setNamedWhereClauseParam("BindDocId", getSrcDocId());
            wsResimpl.setNamedWhereClauseParam("BindItmId", getItmId());
            wsResimpl.executeQuery();
            Row[] filteredRows = wsResimpl.getFilteredRows("DocId", getSrcDocId());
            if (filteredRows.length > 0) {
                if (filteredRows[0].getAttribute("SoId") != null) {
                   // totalQty = Integer.parseInt(filteredRows[0].getAttribute("ItmQty").toString());
                   totalQty = (Number)filteredRows[0].getAttribute("ItmQty");
                    custName = filteredRows[0].getAttribute("EoNm").toString();
                    return filteredRows[0].getAttribute("SoId").toString();
                }
            }
        }else if (getSrcDocId() != null && getSrcType() == 147) {

            ViewObjectImpl wsResimpl = getAM().getLOVSubContractVO1();
            wsResimpl.setNamedWhereClauseParam("BindCldId", getCldId());
            wsResimpl.setNamedWhereClauseParam("BindSlocId", getSlocId());
            wsResimpl.setNamedWhereClauseParam("BindHoOrgId", getHoOrgId());
            wsResimpl.setNamedWhereClauseParam("BindOrgId", getOrgId());
            wsResimpl.setNamedWhereClauseParam("BindDocId", getSrcDocId());
            wsResimpl.setNamedWhereClauseParam("BindItmId", getItmId());
            wsResimpl.executeQuery();
            Row[] filteredRows = wsResimpl.getFilteredRows("DocId", getSrcDocId());
            if (filteredRows.length > 0) {
                if (filteredRows[0].getAttribute("SoId") != null) {
                   // totalQty = Integer.parseInt(filteredRows[0].getAttribute("ItmQty").toString());
                   totalQty = (Number)filteredRows[0].getAttribute("ItmQty");
                    custName = filteredRows[0].getAttribute("EoNm").toString();
                    return filteredRows[0].getAttribute("SoId").toString();
                }
            }
        }


        return (String) getAttributeInternal(TRANSSO);
        //return " ";
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransSO.
     * @param value value to set the  TransSO
     */
    public void setTransSO(String value) {
        setAttributeInternal(TRANSSO, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransItemName.
     * @return the TransItemName
     */
    public String getTransItemName() {
          
         // System.out.println("Value of item id  from getter::"+getItmId());
        if (getItmId() != null) {

            if (getAM().getLOVProductVO1().getFilteredRows("ItmId", getItmId()).length > 0) {
                if ((getAM().getLOVProductVO1().getFilteredRows("ItmId", getItmId()))[0].getAttribute("ItemNme") !=
                    null) {

                    return (getAM().getLOVProductVO1().getFilteredRows("ItmId",
                                                                       getItmId()))[0].getAttribute("ItemNme").toString();

                }
            }
        }
        return (String) getAttributeInternal(TRANSITEMNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransItemName.
     * @param value value to set the  TransItemName
     */
    public void setTransItemName(String value) {
        setAttributeInternal(TRANSITEMNAME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransTotQty.
     * @return the TransTotQty
     */
    public Number getTransTotQty() {
        Number a = new Number(0);
        if (totalQty.compareTo(a) == 1)
            return totalQty;
        else
            return (Number) getAttributeInternal(TRANSTOTQTY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransTotQty.
     * @param value value to set the  TransTotQty
     */
    public void setTransTotQty(Number value) {
        setAttributeInternal(TRANSTOTQTY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransCustName.
     * @return the TransCustName
     */
    public String getTransCustName() {
        if (custName != null)
            return custName;
        else
            return (String) getAttributeInternal(TRANSCUSTNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransCustName.
     * @param value value to set the  TransCustName
     */
    public void setTransCustName(String value) {
        setAttributeInternal(TRANSCUSTNAME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransSrcBasis.
     * @return the TransSrcBasis
     */
    public String getTransSrcBasis() {
        return (String) getAttributeInternal(TRANSSRCBASIS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransSrcBasis.
     * @param value value to set the  TransSrcBasis
     */
    public void setTransSrcBasis(String value) {
        setAttributeInternal(TRANSSRCBASIS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransKey.
     * @return the TransKey
     */
    public Object getTransKey() {
        return getKey();
        //return (Object) getAttributeInternal(TRANSKEY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransKey.
     * @param value value to set the  TransKey
     */
    public void setTransKey(Object value) {
        setAttributeInternal(TRANSKEY, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVPdoBasisVO1.
     */
    public RowSet getLOVPdoBasisVO1() {
        return (RowSet) getAttributeInternal(LOVPDOBASISVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVPdoPriorityVO1.
     */
    public RowSet getLOVPdoPriorityVO1() {
        return (RowSet) getAttributeInternal(LOVPDOPRIORITYVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVSupplyorderVO1.
     */
    public RowSet getLOVSupplyorderVO1() {
        return (RowSet) getAttributeInternal(LOVSUPPLYORDERVO1);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVSoSrcBasisVO.
     */
    public RowSet getLOVSoSrcBasisVO() {
        return (RowSet) getAttributeInternal(LOVSOSRCBASISVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LOVSOCustVO.
     */
    public RowSet getLOVSOCustVO() {
        return (RowSet) getAttributeInternal(LOVSOCUSTVO);
    }

    /**
     *
     * Get AM Instance
     * **/
    public MNFProductionorderAppAMImpl getAM() {
        return (MNFProductionorderAppAMImpl) getApplicationModule();

    }


}

