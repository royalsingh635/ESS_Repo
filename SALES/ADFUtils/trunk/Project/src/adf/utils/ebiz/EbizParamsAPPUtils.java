package adf.utils.ebiz;

import adf.utils.log.LogUtil;
import adf.utils.model.ADFModelUtils;


import java.sql.Types;

import javax.faces.application.FacesMessage;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ApplicationModuleImpl;
/**
 * This class basically contains the Methods related to App.
 */
public class EbizParamsAPPUtils {
    public EbizParamsAPPUtils() {
        super();
    }
    private static ADFLogger _log = ADFLogger.createADFLogger(EbizParamsMMUtils.class);

    /**
     * Method to fetch Tax Desc on the Basis of Tax Id for Current Ho.
     * @param am
     * @param taxId
     * @return "" : In Case of any error
     */
    public static String getTaxDescFrmTaxId(ApplicationModuleImpl am, Integer taxId) {
        Object taxDescO = null;
        try {
            taxDescO = ADFModelUtils.callFunction(am, new StringBuilder("APP.FN_GET_TAX_DESC_FRM_TAX_ID(?,?,?,?)"), new Object[] {
                                                  EbizParams.GLBL_APP_CLD_ID(),EbizParams.GLBL_APP_SERV_LOC(), 
                                                  EbizParams.GLBL_HO_ORG_ID(), taxId
            }, Types.VARCHAR);
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error fetching Tax Desc from Tax Id !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
            taxDescO = "";
        }

            if (LogUtil.enableLogger) {
                LogUtil.showInfoLog(_log, "getTaxDescFrmTaxId", new Object[] { "ApplicationModuleImpl am", "Integer taxId"}, new Object[] { am,taxId},
                                    taxDescO);
            }
        return taxDescO.toString();
    }
    
    /**
     * Method to fetch the current FyId for the Current Organisation on current Date.
     * @param am
     * @return -1 : In Case of any error
     */
    @SuppressWarnings({ "deprecation", "oracle.jdeveloper.java.semantic-warning" })
    public static Integer getFyIdOnCurrDtAndCurrOrg(ApplicationModuleImpl am) {
        return EbizParams.getFyIdOnCurrDtAndCurrOrg(am);
        
    }

    /**
     * Method to fetch the current FyId for the Passed Organisation on current Date.
     * @param am
     * @param orgId
     * @return -1 : In Case of any error
     */
    @SuppressWarnings({ "deprecation", "oracle.jdeveloper.java.semantic-warning" })
    public static Integer getFyIdOnCurrDt(ApplicationModuleImpl am, String orgId) {
        return EbizParams.getFyIdOnCurrDt(am,orgId);
    }

    /**
     * Method to fetch the current FyId.
     * @param am
     * @param orgId
     * @param dt
     * @return -1 : In Case of any error
     */
    @SuppressWarnings({ "deprecation", "oracle.jdeveloper.java.semantic-warning" })
    public static Integer getFyId(ApplicationModuleImpl am, String orgId, Timestamp dt) {
        return EbizParams.getFyId(am,orgId,dt);
    }

    /**
     * Method to fetch Base Currency of the Passed organsation.
     * @param am
     * @param orgId
     * @return -1 : In Case of any error
     */
    @SuppressWarnings({ "oracle.jdeveloper.java.tags-incorrectly-sorted", "deprecation",
                      "oracle.jdeveloper.java.semantic-warning" })
    public static Integer getCurrIdBsForPassedOrgId(ApplicationModuleImpl am, String orgId) {
        return EbizParams.getCurrIdBsForPassedOrgId(am,orgId);
    }

    /**
     * Method to fetch Base Currency of the Current organsation.
     * @param am
     * @return -1 : In Case of any error
     */
    @SuppressWarnings({ "deprecation", "oracle.jdeveloper.java.semantic-warning" })
    public static Integer getCurrIdBsForCurrOrg(ApplicationModuleImpl am) {
        return EbizParams.getCurrIdBsForCurrOrg(am);
    }

    /**
     * Method to fetch Item Desc on the Basis of Item Id for Current Ho.
     * @param am
     * @param itmId
     * @return "" : In Case of any error
     */
    @SuppressWarnings({ "deprecation", "oracle.jdeveloper.java.semantic-warning" })
    public static String getItemDescFrmItmId(ApplicationModuleImpl am, String itmId) {
        return EbizParams.getItemDescFrmItmId(am,itmId);
    }

    /**
     * Method to fetch coa name on the Basis of Item Id for Current Ho.
     * @param am
     * @param coaId
     * @return "" : In Case of any error
     */
    @SuppressWarnings({ "deprecation", "oracle.jdeveloper.java.semantic-warning" })
    public static String getCoaNameFrmCoaId(ApplicationModuleImpl am, Integer coaId) {
        return EbizParams.getCoaNameFrmCoaId(am,coaId);
    }

    /**
     * Method to fetch coa id on the Basis of Eo Id for Current Ho and Current Org.
     * @param am
     * @param eoId
     * @return -1 : In Case of any error
     */
    @SuppressWarnings({ "deprecation", "oracle.jdeveloper.java.semantic-warning" })
    public static Integer getCoaIdFrmEoId(ApplicationModuleImpl am, Integer eoId) {
        return EbizParams.getCoaIdFrmEoId(am,eoId);
    }

    /**
     * Method to fetch Currency Conversion Rate on the Basis of Currency Id for Current Ho and Current Org.
     * @param am
     * @param currId
     * @return 1 : In Case of any error (1 is default value)
     */
    public static Number getCurrRateForCurrId(ApplicationModuleImpl am, Integer currId) {
        return EbizParams.getCurrRateForCurrId(am,currId);
    }

    /**
     * Method to fetch Sales Executive Id on the Basis of EoId for Current Ho and Current Org.
     * @param am
     * @param eoId
     * @return -1 : In Case of any error (-1 is default value)
     */
    public static Integer getSalesExecForEoId(ApplicationModuleImpl am, Integer eoId) {
        return EbizParams.getSalesExecForEoId(am,eoId);
    }

    /**
     * Method to fetch Address on the Basis of AddsId for Current Cld and Sloc.
     * @param am
     * @param addsId
     * @return "" : In Case of any error ("" is default value)
     */
    public static StringBuilder getAddDescFrmAddsId(ApplicationModuleImpl am, String addsId) {
        return EbizParams.getAddDescFrmAddsId(am,addsId);
    }

    /**
     * Method to fetch default Currency Id on the Basis of EoId.
     * @param am
     * @param eoId
     * @return null : In Case of any error / Default
     */
    public static Integer getDefaultCurrIdOfEoId(ApplicationModuleImpl am, Integer eoId) {
        return EbizParams.getDefaultCurrIdOfEoId(am,eoId);
    }

    /**
     * Method to fetch Eo Desc on the Basis of Eo Id for Current Ho.
     * @param am
     * @param eoId
     * @return "" : In Case of any error
     */
    public static String getEoDescFrmEoId(ApplicationModuleImpl am, Integer eoId) {
        return EbizParams.getEoDescFrmEoId(am,eoId);
    }

    /**
     * Method to fetch User Desc on the Basis of User Id for Current Ho.
     * @param am
     * @param usrId
     * @return "" : In Case of any error
     */
    public static String getUsrDescFrmUsrId(ApplicationModuleImpl am, Integer usrId) {
        return EbizParams.getUsrDescFrmUsrId(am,usrId);
    }

    /**
     * Method to generate docTxn id on the basis of Global Doc Id
     * @param am
     * @param glblDocId - Document global doc id
     * @return
     */
    public static Object generateDocTxnId(ApplicationModuleImpl am, Integer glblDocId) {
        return EbizParams.generateDocTxnId(am,glblDocId);
    }
    
    /**
     * Method to generate docTxn id on the basis of Global Doc Id and Document type id
     * @param am
     * @param glblDocId - Document global doc id
     * @param docType - Document type id
     * @return
     */
    public static Object generateDocTxnId(ApplicationModuleImpl am, Integer glblDocId,Integer docTypeId) {
        return EbizParams.generateDocTxnId(am, glblDocId,docTypeId);
    }
    
    /**
     * Method to fetch Currency Desc on the Basis of Currency Desc from Currency Id.
     * @param am
     * @param currId
     * @return "" : In Case of any error
     */
    public static String getCurrDescFrmCurrId(ApplicationModuleImpl am, Integer currId) {
        return EbizParams.getCurrDescFrmCurrId(am,currId);
    }
    
    /**
     * Method to project on the basis of Warehouse.
     * @param am
     * @param taxId
     * @return "" : In Case of any error
     */
    public static String getPrjIdFrmWhId(ApplicationModuleImpl am, String whId) {
        Object prjIdO = null;
        try {
            prjIdO = ADFModelUtils.callFunction(am, new StringBuilder("APP.FN_APP_GET_WH_DEF_PRJ(?,?,?,?,?)"), new Object[] {
                                                  EbizParams.GLBL_APP_CLD_ID(),EbizParams.GLBL_APP_SERV_LOC(), 
                                                  EbizParams.GLBL_HO_ORG_ID(),EbizParams.GLBL_APP_USR_ORG(), whId
            }, Types.VARCHAR);
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error fetching Tax Desc from Tax Id !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
        }

            if (LogUtil.enableLogger) {
                LogUtil.showInfoLog(_log, "getPrjIdFrmWhId", new Object[] { "ApplicationModuleImpl am", "String whId"}, new Object[] { am,whId},
                                    prjIdO);
            }
            return (prjIdO == null ? null : prjIdO.toString());
    }
    
    /**
     * Method to fetch Wharehouse Desc on the Basis of Warehouse Id for Current Ho.
     * @param am
     * @param taxId
     * @return "" : In Case of any error
     */
    public static String getWhDescFrmWhId(ApplicationModuleImpl am, String whId) {
        Object whDescO = null;
        try {
            whDescO = ADFModelUtils.callFunction(am, new StringBuilder("APP.FN_GET_WH_DESC_FRM_WH_ID(?,?,?,?,?)"), new Object[] {
                                                  EbizParams.GLBL_APP_SERV_LOC(),EbizParams.GLBL_APP_CLD_ID(), 
                                                  EbizParams.GLBL_HO_ORG_ID(),EbizParams.GLBL_APP_USR_ORG(), whId
            }, Types.VARCHAR);
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error fetching Tax Desc from Tax Id !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
            whDescO = "";
        }

            if (LogUtil.enableLogger) {
                LogUtil.showInfoLog(_log, "getWhDescFrmWhId", new Object[] { "ApplicationModuleImpl am", "String whId"}, new Object[] { am,whId},
                                    whDescO);
            }
        return whDescO.toString();
    }
    


}
