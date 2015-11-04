package adf.utils.ebiz;

import adf.utils.log.LogUtil;
import adf.utils.model.ADFModelUtils;
import java.sql.Types;
import javax.faces.application.FacesMessage;
import oracle.adf.share.logging.ADFLogger;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ApplicationModuleImpl;
/**
 * This class basically contains the Methods related to Materials Management.
 */
public class EbizParamsMMUtils {
    private static ADFLogger _log = ADFLogger.createADFLogger(EbizParamsMMUtils.class);

    public EbizParamsMMUtils() {
        super();
    }
    
    /**
     * Method to fetch Basic UOM of Item
     * @param am
     * @param itmId
     * @return
     */
    public static String getBaseUomOfPassedItm(ApplicationModuleImpl am,String itmId) {
        String baseUom = null;
        try {
            Object o = ADFModelUtils.callFunction(am, new StringBuilder("MM.FN_GET_ITM_UOM_BS(?,?,?,?)"), new Object[]{EbizParams.GLBL_APP_CLD_ID(),EbizParams.GLBL_APP_SERV_LOC(),EbizParams.GLBL_HO_ORG_ID(),itmId}, Types.VARCHAR);

            if(o != null){
                baseUom = o.toString();;
            }
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error while fetching Base UOM !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
        }
        if (LogUtil.enableLogger) {
            LogUtil.showInfoLog(_log, "getBaseUomOfPassedItm", new Object[] { "ApplicationModuleImpl am", "String itmId"}, new Object[] { am,itmId},
                                baseUom);
        }
        return baseUom;
    } 
    
    /**
     * Method to fetch Item UOM Conversion factor on the basis of
     * @param ItmId
     * @param ItmUom
     * @param baseUom
     * @return
     */
    public static Number getItmUomConvFactor(ApplicationModuleImpl am, String itmId, String itmUom, String baseUom) {
        Number uomFctr = new Number(1);
        try {
            Object o = ADFModelUtils.callFunction(am, new StringBuilder("APP.FN_GET_UOM_CONV_FCTR(?,?,?,?,?,?)"), new Object[] {
                                                  EbizParams.GLBL_APP_SERV_LOC(), EbizParams.GLBL_APP_CLD_ID(),
                                                  EbizParams.GLBL_APP_USR_ORG(), itmId, itmUom,baseUom
            }, Types.NUMERIC);
            try {
                uomFctr = new Number(o);
            } catch (Exception e) {
                _log.info(e);
            }
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error while fetching UOM Conversion factor !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
        }
        if (LogUtil.enableLogger) {
            LogUtil.showInfoLog(_log, "getItmUomConvFactor", new Object[] { "ApplicationModuleImpl am", "String itmId","String itmUom","String baseUom"}, new Object[] { am,itmId,itmUom,baseUom },
                                uomFctr);
        }
        return uomFctr;
    }
    
    /**
     * Method to fetch UOM Desc on the Basis of UOM Desc from UOM Id.
     * @param am
     * @param currId
     * @return "" : In Case of any error
     */
    public static String getUOMDescFrmUOMId(ApplicationModuleImpl am, String uomId) {
       return EbizParams.getUOMDescFrmUOMId(am,uomId);
    }
    
    /**
     * Method to fetch UOM of Item
     * @param am
     * @param itmId
     * @param flag -- S : [SALES UNIT]
                   -- P : [PURCHASE UNIT]
                   -- B : [BASIC UNIT]
     * @return
     */
    public static String getUomOfPassedItm(ApplicationModuleImpl am,String itmId,String flag) {
        String baseUom = null;
        try {
            Object o = ADFModelUtils.callFunction(am, new StringBuilder("MM.FN_GET_ITM_UOM(?,?,?,?,?)"), new Object[]{EbizParams.GLBL_APP_CLD_ID(),EbizParams.GLBL_APP_SERV_LOC(),EbizParams.GLBL_HO_ORG_ID(),itmId,flag}, Types.VARCHAR);

            if(o != null){
                baseUom = o.toString();;
            }
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error while fetching UOM !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
        }
        if (LogUtil.enableLogger) {
            LogUtil.showInfoLog(_log, "getUomOfPassedItm", new Object[] { "ApplicationModuleImpl am", "String itmId","String flag"}, new Object[] { am,itmId,flag},
                                baseUom);
        }
        return baseUom;
    }

}
