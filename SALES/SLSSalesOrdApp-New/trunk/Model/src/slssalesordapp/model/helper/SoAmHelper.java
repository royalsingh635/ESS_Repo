package slssalesordapp.model.helper;

import adf.utils.bean.StaticValue;
import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import java.math.BigDecimal;

import java.sql.Types;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ViewObjectImpl;

import slssalesordapp.model.service.salesOrderAMImpl;

public class SoAmHelper {
    private static ADFLogger _log = ADFLogger.createADFLogger(SoAmHelper.class);
    
    public SoAmHelper() {
        super();
    }
    /**
     * Method to check For Sales Profile Values of the Organisation.
     * @param am
     * @param colName
     * @return
     */
    public static StringBuffer checkforProfileValues(salesOrderAMImpl am,StringBuffer colName) {
        colName = (colName == null ? new StringBuffer("") : colName);
        StringBuffer ck = new StringBuffer("");
        try {
            String policyPrice = (String) ADFModelUtils.callFunction(am, new StringBuilder("SLS.fn_sls_chk_org_prf(?,?,?,?)"), new Object[] {
                                                             EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(), EbizParams.GLBL_APP_USR_ORG(), colName.toString()
            },Types.VARCHAR);

            if (policyPrice == null || policyPrice.equals("")) {
                ck = new StringBuffer("N");
            } else {
                ck = new StringBuffer(policyPrice.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ck;
    }
    /**
     * Method to add Item Trf line to Sales Order.
     * @return
     * -1 : Some Error Occured
     * 1 : Item is not Selected.
     * 2 : Duplicate Item is Selected.
     * 3 : Source Organisation is not Selected.
     * 4 : Warehouse Id Scource is not Selected.
     * 5 : Destination Organisation is not Selected.
     * 6 : Warehouse Id Destination is not Selected.
     * 7 : Source and Destination Warehouse Id are same.
     * 8 : Transfer Quantity should be greater than zero.
     * 9 : UOM is not defined for this Item.
     * 10 : UOM Conversion factor with the Base UOM is not Defined.
     *
     */
    /**
     * Method to add Item to the Transfer order 
     * @param am
     * @return
     */
    public static Integer addItmToTrf(salesOrderAMImpl am){
        Integer i = 0;
        Row tempTrfRow = am.getTempTrfVO1().getCurrentRow();
        Object itmIdO = tempTrfRow.getAttribute("ItmIdTrans");
        Object orgSrcIdO = tempTrfRow.getAttribute("OrgSrcIdTrans");
        Object whSrcIdO = tempTrfRow.getAttribute("WhSrcIdTrans");
        Object orgDestIdO = tempTrfRow.getAttribute("OrgDestIdTrans");
        Object whIdDestO = tempTrfRow.getAttribute("WhDestIdTrans");
        Object availQtyO = tempTrfRow.getAttribute("ItmAvailQtyTrans");
        Object itmTrfQtyO = tempTrfRow.getAttribute("ItmTrfQtyTrans");
        Object uomTransO = tempTrfRow.getAttribute("UomTrans");
        
        
        StringBuilder itmId = (itmIdO == null ? new StringBuilder("") : new StringBuilder(itmIdO.toString()));
        StringBuilder orgSrcId = (orgSrcIdO == null ? new StringBuilder("") : new StringBuilder(orgSrcIdO.toString()));
        StringBuilder whIdSrc = (whSrcIdO == null ? new StringBuilder("") : new StringBuilder(whSrcIdO.toString()));
        StringBuilder orgDestId = (orgDestIdO == null ? new StringBuilder("") : new StringBuilder(orgDestIdO.toString()));
        StringBuilder whIdDest = (whIdDestO == null ? new StringBuilder("") : new StringBuilder(whIdDestO.toString()));
        Number availQty = (availQtyO == null ? StaticValue.NUMBER_ZERO : (Number)availQtyO);
        Number itmTrfQty = (itmTrfQtyO == null ? StaticValue.NUMBER_ZERO : (Number)itmTrfQtyO);
        
        
        if("".equals(itmId.toString())){
            i = 1;
        }else if(!validateTrfItmId(am,itmId.toString())){
            i = 2;
        }else if("".equals(orgSrcId.toString())){
            i = 3;
        }else if("".equals(whIdSrc.toString())){
            i = 4;
        }else if("".equals(orgDestId.toString())){
            i = 5;
        }else if("".equals(whIdDest.toString())){
            i = 6;
        }else if(whIdSrc.toString().equals(whIdDest.toString()) && orgSrcId.toString().equals(orgDestId.toString())){
            i = 7;
        }else if(itmTrfQty.compareTo(StaticValue.NUMBER_ZERO) <= 0){
            i = 8;
        }else if(uomTransO == null){
            i = 9;
        }else{
            Number uomConvFctr = SoAmHelper.getUomConvFactor(am,itmId.toString(), (uomTransO == null ? null : uomTransO.toString()));
            uomConvFctr = (uomConvFctr == null ? new Number(-1) : uomConvFctr);
            _log.info("UOM Conversion factor : "+uomConvFctr);
            if(uomConvFctr.compareTo(-1) <= 0){
                i = 10;
            }else{
                Row soRow = am.getSlsSo1().getCurrentRow();
                ViewObjectImpl trfVo = am.getSlsSoItmTrfVO1();
                Row row = trfVo.createRow();
                trfVo.insertRow(row);
                row.setAttribute("ItmId", itmIdO);
                row.setAttribute("FyId", soRow.getAttribute("FyId"));
                row.setAttribute("OrgIdSrc", orgSrcIdO);
                row.setAttribute("WhIdSrc", whSrcIdO);
                row.setAttribute("OrgIdDest", orgDestIdO);
                row.setAttribute("WhIdDest", whIdDestO);
                row.setAttribute("ItmUom", uomTransO);
                row.setAttribute("ItmUomBs", tempTrfRow.getAttribute("UomBasicTrans"));
                row.setAttribute("UomConvFctr", uomConvFctr);
                row.setAttribute("ItmTrfQty", itmTrfQty);
                row.setAttribute("AvlQty", availQty);
                Number baseQty = itmTrfQty.multiply(uomConvFctr);
                baseQty = baseQty.multiply(EbizParams.GLBL_QTY_DIGIT()); 
                row.setAttribute("ItmTrfQtyBs", baseQty);
                row.setAttribute("DlvSchdlNo", 1);
                row.setAttribute("DlvDt", StaticValue.getTruncatedCurrDt());
                row.setAttribute("TrfStat", "N");
                i = 0;
                am.getTempTrfVO1().executeQuery();
            }
        }
        return i;
    } 
    
    /**
     * Method to check if Item Being Added is not duplicate.
     * @param itmId
     * @return
     */
    public static Boolean validateTrfItmId(salesOrderAMImpl am,String itmId){
        Boolean b = true;
        RowSetIterator ctr = am.getSlsSoItmTrfVO1().createRowSetIterator(null);
        while(ctr.hasNext()){
            Object itmIdO = ctr.next().getAttribute("ItmId");
            if(itmIdO == null){
                b = false;
                break;
            }else if(itmIdO != null && itmId.equals(itmIdO.toString())){
                b = false;
                break;
            }
        }
        ctr.closeRowSetIterator();

        return b;
    } 
    
    /**Get Basic UOM of Item**.
     * @param itmId
     * @return
     */

    public static String getBaseUom(salesOrderAMImpl am,String itmId) {
        String baseUom = null;
        ViewObjectImpl impl = am.getLovItmNmVo1();
        impl.setNamedWhereClauseParam("ItmIdBind", itmId);
        impl.setNamedWhereClauseParam("CldIdBind", EbizParams.GLBL_APP_CLD_ID());
        impl.setNamedWhereClauseParam("SlocIdBind", EbizParams.GLBL_APP_SERV_LOC());
        impl.setNamedWhereClauseParam("HoOrgIdBind", EbizParams.GLBL_HO_ORG_ID());
        impl.setNamedWhereClauseParam("ItmDescBind", null);
        impl.executeQuery();
        Row[] allRowsInRange = impl.getAllRowsInRange();
        if (allRowsInRange.length > 0) {
            baseUom = allRowsInRange[0].getAttribute("UomBasic").toString();
        }

        _log.info("Base Uom is-" + baseUom);
        return baseUom;
    }
    
    /**
     * Method to fetch UOM Conversion factor on the basis of EoId.
     * @param ItmId
     * @param ItmUom
     * @return
     * @param eoId
     */
    public static Number getUomConvFactor(salesOrderAMImpl am,String ItmId, String ItmUom) {
        Number uomFctr = new Number(1);
        String baseUom = SoAmHelper.getBaseUom(am,ItmId);
        BigDecimal convFctr = (BigDecimal) ADFModelUtils.callFunction(am, new StringBuilder("APP.FN_GET_UOM_CONV_FCTR(?,?,?,?,?,?)"), new Object[] {
                                                              EbizParams.GLBL_APP_SERV_LOC(),
                                                              EbizParams.GLBL_APP_CLD_ID(),
                                                              EbizParams.GLBL_APP_USR_ORG(), ItmId, ItmUom,baseUom
        },Types.NUMERIC);

        try {
            uomFctr = new Number(convFctr);
        } catch (Exception e) {
            _log.info(e);
        }
        return uomFctr;
    }
    
    /**
     * @param colName
     * @return
     */
    public static Object checkforProfileValuesObjectReturnV(salesOrderAMImpl am,StringBuffer colName) {
        colName = (colName == null ? new StringBuffer("") : colName);
        Object obj = null;
        try {
            obj = ADFModelUtils.callFunction(am, new StringBuilder("SLS.fn_sls_chk_org_prf(?,?,?,?)"), new Object[] {
                                     EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(),
                                     EbizParams.GLBL_APP_USR_ORG(), colName.toString()
            },Types.VARCHAR);


        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        // _log.info(colName+" : "+ck);
        return obj;
    }
    
    /**
     * Method to fetch Item Lot Price
     * @param am
     * @param itmId
     * @param lotId
     * @param whId
     * @return
     */
    public Number getLotPrice(salesOrderAMImpl am, Object itmId, Object lotId, Object whId) {
        Number n = StaticValue.NUMBER_ZERO;
        try {
           Object o = ADFModelUtils.callFunction(am, new StringBuilder("SLS.FN_GET_ITM_LOT_PRICE(?,?,?,?,?,?,?)"), new Object[] {
                                                 EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(),
                                                 EbizParams.GLBL_APP_USR_ORG(), whId, itmId, lotId,
                                                 EbizParams.getFyIdOnCurrDtAndCurrOrg(am)
           }, Types.NUMERIC);
            if( o != null){
                n = new Number(o); 
            }
       } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        return n;
    }
    
    /**
     * Method to get Available Stock of an Item
     * @param itmId
     * @param whId
     * @return
     */
    public static Number getItemAvailableStock(salesOrderAMImpl am,Object itmId, Object whId) {
        Number n = StaticValue.NUMBER_ZERO;

        try {
            Object v = ADFModelUtils.callFunction(am, new StringBuilder("SLS.fn_mm_get_avail_stk(?,?,?,?,?,?)"), new Object[] {
                                                  EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(),
                                                  EbizParams.GLBL_APP_USR_ORG(), itmId, whId,
                                                  new Timestamp(System.currentTimeMillis())
            }, Types.NUMERIC);
            n = new Number(v);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }

}
