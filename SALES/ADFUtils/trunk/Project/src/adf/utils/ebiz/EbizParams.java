package adf.utils.ebiz;

import adf.utils.bean.StaticValue;
import adf.utils.log.LogUtil;
import adf.utils.model.ADFModelUtils;

import java.lang.annotation.Documented;

import java.math.BigDecimal;

import java.sql.SQLException;
import java.sql.Types;

import javax.faces.application.FacesMessage;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ApplicationModuleImpl;

/**
 * This class basically contains the Standard parameters that can be used for development in EbizFrame.
 * It also contains functionality related most commonly used methods
 */
public class EbizParams {
    private static ADFLogger _log = ADFLogger.createADFLogger(EbizParams.class);

    public EbizParams() {
        super();
    }

    /**
     * Method to get GLBL_AMT_DIGIT.
     * @return if GLBL_AMT_DIGIT returns null , then returns 2
     */
    public static Integer GLBL_AMT_DIGIT() {
        Integer i = 2;
        Object el = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_AMT_DIGIT}");
        if (el != null) {
            i = Integer.parseInt(el.toString());
        } else {
            _log.severe("Warning : Method | GLBL_AMT_DIGIT() | GLBL_AMT_DIGIT not defined.");
        }
        if (LogUtil.enableLogger) {
            _log.fine("Method | GLBL_AMT_DIGIT() | called with following parameters :");
            _log.fine("=======> GLBL_AMT_DIGIT()  | Returned : " + i);
        }
        return i;
    }

    /**
     * Method to get GLBL_CURR_DIGIT.
     * @return if GLBL_CURR_DIGIT returns null , then returns 2
     */
    public static Integer GLBL_CURR_DIGIT() {
        Integer i = 2;
        Object el = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_CURR_DIGIT}");
        if (el != null) {
            i = Integer.parseInt(el.toString());
        } else {
            _log.severe("Warning : Method | GLBL_CURR_DIGIT() | GLBL_CURR_DIGIT not defined.");
        }
        if (LogUtil.enableLogger) {
            _log.fine("Method | GLBL_CURR_DIGIT() | called with following parameters :");
            _log.fine("=======> GLBL_CURR_DIGIT()  | Returned : " + i);
        }
        return i;
    }

    /**
     * Method to get GLBL_DT_FORMAT.
     * @return if GLBL_DT_FORMAT returns null , then returns dd-MM-yyyy
     */
    public static String GLBL_DT_FORMAT() {
        StringBuilder i = new StringBuilder("dd-MM-yyyy");
        Object el = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_DT_FORMAT}");
        if (el != null) {
            i = new StringBuilder(el.toString());
        } else {
            _log.severe("Warning : Method | GLBL_DT_FORMAT() | GLBL_DT_FORMAT not defined.");
        }
        if (LogUtil.enableLogger) {
            _log.fine("Method | GLBL_DT_FORMAT() | called with following parameters :");
            _log.fine("=======> GLBL_DT_FORMAT()  | Returned : " + i);
        }
        return i.toString();
    }

    /**
     * Method to get GLBL_QTY_DIGIT.
     * @return if GLBL_QTY_DIGIT returns null , then returns 2
     */
    public static Integer GLBL_QTY_DIGIT() {
        Integer i = 2;
        Object el = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_QTY_DIGIT}");
        if (el != null) {
            i = Integer.parseInt(el.toString());
        } else {
            _log.severe("Warning : Method | GLBL_QTY_DIGIT() | GLBL_QTY_DIGIT not defined.");
        }
        if (LogUtil.enableLogger) {
            _log.fine("Method | GLBL_QTY_DIGIT() | called with following parameters :");
            _log.fine("=======> GLBL_QTY_DIGIT()  | Returned : " + i);
        }
        return i;
    }

    /**
     * Method to get GLBL_RATE_DIGIT.
     * @return if GLBL_RATE_DIGIT returns null , then returns 2
     */
    public static Integer GLBL_RATE_DIGIT() {
        Integer i = 2;
        Object el = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_RATE_DIGIT}");
        if (el != null) {
            i = Integer.parseInt(el.toString());
        } else {
            _log.severe("Warning : Method | GLBL_RATE_DIGIT() | GLBL_RATE_DIGIT not defined.");
        }
        if (LogUtil.enableLogger) {
            _log.fine("Method | GLBL_RATE_DIGIT() | called with following parameters :");
            _log.fine("=======> GLBL_RATE_DIGIT()  | Returned : " + i);
        }
        return i;
    }

    /**
     * Method to get GLBL_DISC_DIGIT.
     * @return if GLBL_DISC_DIGIT returns null , then returns 2
     */
    public static Integer GLBL_DISC_DIGIT() {
        Integer i = 2;
        Object el = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_DISC_DIGIT}");
        if (el != null) {
            i = Integer.parseInt(el.toString());
        } else {
            _log.severe("Warning : Method | GLBL_DISC_DIGIT() | GLBL_DISC_DIGIT not defined.");
        }
        if (LogUtil.enableLogger) {
            _log.fine("Method | GLBL_DISC_DIGIT() | called with following parameters :");
            _log.fine("=======> GLBL_DISC_DIGIT()  | Returned : " + i);
        }
        return i;
    }

    /**
     * Method to get GLBL_APP_SERV_LOC.
     * @return Integer
     */
    public static Integer GLBL_APP_SERV_LOC() {
        Integer i = null;
        Object el = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}");
        if (el != null) {
            i = Integer.parseInt(el.toString());
        } else {
            _log.severe("Warning : Method | GLBL_APP_SERV_LOC() | GLBL_APP_SERV_LOC not defined.");
        }
        if (LogUtil.enableLogger) {
            _log.fine("Method | GLBL_APP_SERV_LOC() | called with following parameters :");
            _log.fine("=======> GLBL_APP_SERV_LOC()  | Returned : " + i);
        }
        return i;
    }

    /**
     * Method to get GLBL_APP_USR.
     * @return Integer
     */
    public static Integer GLBL_APP_USR() {
        Integer i = null;
        Object el = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_APP_USR}");
        if (el != null) {
            i = Integer.parseInt(el.toString());
        } else {
            _log.severe("Warning : Method | GLBL_APP_USR() | GLBL_APP_USR not defined.");
        }
        if (LogUtil.enableLogger) {
            _log.fine("Method | GLBL_APP_USR() | called with following parameters :");
            _log.fine("=======> GLBL_APP_USR()  | Returned : " + i);
        }
        return i;
    }

    /**
     * Method to get GLBL_APP_USR_ORG.
     * @return String
     */
    public static String GLBL_APP_USR_ORG() {
        Object el = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        if (el != null) {
            if (LogUtil.enableLogger) {
                _log.fine("Method | GLBL_APP_USR_ORG() | called with following parameters :");
                _log.fine("=======> GLBL_APP_USR_ORG()  | Returned : " + el);
            }
            return el.toString();
        } else {
            _log.severe("Warning : Method | GLBL_APP_USR() | GLBL_APP_USR not defined.");
            if (LogUtil.enableLogger) {
                _log.fine("Method | GLBL_APP_USR_ORG() | called with following parameters :");
                _log.fine("=======> GLBL_APP_USR_ORG()  | Returned : " + el);
            }
            return null;
        }
    }

    /**
     * Method to get GLBL_APP_CLD_ID.
     * @return String
     */
    public static String GLBL_APP_CLD_ID() {
        Object el = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        if (el != null) {
            if (LogUtil.enableLogger) {
                _log.fine("Method | GLBL_APP_CLD_ID() | called with following parameters :");
                _log.fine("=======> GLBL_APP_CLD_ID()  | Returned : " + el);
            }
            return el.toString();
        } else {
            _log.severe("Warning : Method | GLBL_APP_CLD_ID() | GLBL_APP_CLD_ID not defined.");
            if (LogUtil.enableLogger) {
                _log.info("Method | GLBL_APP_CLD_ID() | called with following parameters :");
                _log.info("=======> GLBL_APP_CLD_ID()  | Returned : " + el);
            }
            return null;
        }
    }

    /**
     * Method to get GLBL_HO_ORG_ID.
     * @return String
     */
    public static String GLBL_HO_ORG_ID() {
        Object el = ADFModelUtils.resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        if (el != null) {
            if (LogUtil.enableLogger) {
                _log.fine("Method | GLBL_HO_ORG_ID() | called with following parameters :");
                _log.fine("=======> GLBL_HO_ORG_ID()  | Returned : " + el);
            }
            return el.toString();
        } else {
            _log.severe("Warning : Method | GLBL_HO_ORG_ID() | GLBL_HO_ORG_ID not defined.");
            if (LogUtil.enableLogger) {
                _log.info("Method | GLBL_HO_ORG_ID() | called with following parameters :");
                _log.info("=======> GLBL_HO_ORG_ID()  | Returned : " + el);
            }
            return null;
        }
    }

    /**
     * Method to fetch the current FyId for the Current Organisation on current Date.
     * @param am
     * @return -1 : In Case of any error
     * @deprecated
     */
    @Deprecated
    public static Integer getFyIdOnCurrDtAndCurrOrg(ApplicationModuleImpl am) {
        Integer i = -1;
        try {
            Object o = ADFModelUtils.callFunction(am, new StringBuilder("APP.GET_ORG_FY_ID (?,?,?)"), new Object[] {
                                                  EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_USR_ORG(),
                                                  StaticValue.getTruncatedCurrDt()
            }, Types.INTEGER);
            if (o != null) {
                i = Integer.parseInt(o.toString());
            }
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error while fetching Current Financial Year Id !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
        }
        if (LogUtil.enableLogger) {
            _log.info("Method | getFyIdOnCurrDtAndCurrOrg(ApplicationModuleImpl am) | called with following parameters ");
            _log.info("=======> getFyIdOnCurrDtAndCurrOrg(" + am + ") | Returned : " + i);
        }
        return i;
    }

    /**
     * Method to fetch the current FyId for the Passed Organisation on current Date.
     * @param am
     * @param orgId
     * @return -1 : In Case of any error
     * @deprecated
     */
    @Deprecated
    public static Integer getFyIdOnCurrDt(ApplicationModuleImpl am, String orgId) {
        Integer i = -1;
        try {
            Object o = ADFModelUtils.callFunction(am, new StringBuilder("APP.GET_ORG_FY_ID (?,?,?)"), new Object[] {
                                                  EbizParams.GLBL_APP_CLD_ID(), orgId, StaticValue.getTruncatedCurrDt()
            }, Types.INTEGER);
            if (o != null) {
                i = Integer.parseInt(o.toString());
            }
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error while fetching Current Financial Year Id !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
        }
        if (LogUtil.enableLogger) {
            _log.info("Method | getFyIdOnCurrDt(ApplicationModuleImpl am, String orgId) | called with following parameters ");
            _log.info("=======> getFyIdOnCurrDt(" + am + "," + orgId + ") | Returned : " + i);
        }
        return i;
    }

    /**
     * Method to fetch the current FyId.
     * @param am
     * @param orgId
     * @param dt
     * @return -1 : In Case of any error
     * @deprecated
     */
    @Deprecated
    public static Integer getFyId(ApplicationModuleImpl am, String orgId, Timestamp dt) {
        Integer i = -1;
        try {
            Object o = ADFModelUtils.callFunction(am, new StringBuilder("APP.GET_ORG_FY_ID (?,?,?)"), new Object[] {
                                                  EbizParams.GLBL_APP_CLD_ID(), orgId, dt
            }, Types.INTEGER);
            if (o != null) {
                i = Integer.parseInt(o.toString());
            }
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error while fetching Current Financial Year Id !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
        }
        if (LogUtil.enableLogger) {
            _log.info("Method | getFyId(ApplicationModuleImpl am, String orgId,Timestamp dt) | called with following parameters ");
            _log.info("=======> getFyId(" + am + "," + orgId + "," + dt + ") | Returned : " + i);
        }
        return i;
    }

    /**
     * Method to fetch Base Currency of the Passed organsation.
     * @param am
     * @param orgId
     * @return -1 : In Case of any error
     * @deprecated
     */
    @SuppressWarnings("oracle.jdeveloper.java.tags-incorrectly-sorted")
    @Deprecated
    public static Integer getCurrIdBsForPassedOrgId(ApplicationModuleImpl am, String orgId) {
        Integer i = -1;
        try {
            Object o = ADFModelUtils.callFunction(am, new StringBuilder("APP.GET_ORG_DEF_CURR_BS1 (?,?,?)"), new Object[] {
                                                  EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(), orgId
            }, Types.INTEGER);
            if (o != null) {
                i = Integer.parseInt(o.toString());
            }
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error while fetching Current Financial Year Id !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
        }
        if (LogUtil.enableLogger) {
            _log.info("Method | getCurrIdBsForPassedOrgId(ApplicationModuleImpl am, String orgId) | called with following parameters ");
            _log.info("=======> getCurrIdBsForPassedOrgId(" + am + "," + orgId + ") | Returned : " + i);
        }
        return i;
    }

    /**
     * Method to fetch Base Currency of the Current organsation.
     * @param am
     * @return -1 : In Case of any error
     * @deprecated
     */
    @Deprecated
    public static Integer getCurrIdBsForCurrOrg(ApplicationModuleImpl am) {
        Integer i = -1;
        try {
            Object o = ADFModelUtils.callFunction(am, new StringBuilder("APP.GET_ORG_DEF_CURR_BS1 (?,?,?)"), new Object[] {
                                                  EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(), EbizParams.GLBL_APP_USR_ORG()
            }, Types.INTEGER);
            if (o != null) {
                i = Integer.parseInt(o.toString());
            }
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error while fetching Current Financial Year Id !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
        }
        if (LogUtil.enableLogger) {
            _log.info("Method | getCurrIdBsForCurrOrg(ApplicationModuleImpl am, String orgId,Timestamp dt) | called with following parameters ");
            _log.info("=======> getCurrIdBsForCurrOrg(" + am + ") | Returned : " + i);

        }
        return i;
    }

    /**
     * Method to fetch Item Desc on the Basis of Item Id for Current Ho.
     * @param am
     * @param itmId
     * @return "" : In Case of any error
     * @deprecated
     */
    @Deprecated
    public static String getItemDescFrmItmId(ApplicationModuleImpl am, String itmId) {
        Object itmDescO = null;
        try {
            itmDescO = ADFModelUtils.callFunction(am, new StringBuilder("SLS.FN_GET_ITM_DESC_FRM_ID(?,?,?,?)"), new Object[] {
                                                  EbizParams.GLBL_APP_SERV_LOC(), EbizParams.GLBL_APP_CLD_ID(),
                                                  EbizParams.GLBL_HO_ORG_ID(), itmId
            }, Types.VARCHAR);
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error fetching Item Desc from Item Id !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
            itmDescO = "";
        }

        if (LogUtil.enableLogger) {
            _log.info("Method | getItemDescFrmItmId(ApplicationModuleImpl am, String itmId) | called with following parameters ");
            _log.info("=======> getItemDescFrmItmId(" + am + "," + itmId + ") | Returned : " + itmDescO);

        }
        return itmDescO.toString();
    }

    /**
     * Method to fetch coa name on the Basis of Item Id for Current Ho.
     * @param am
     * @param coaId
     * @return "" : In Case of any error
     * @deprecated
     */
    @Deprecated
    public static String getCoaNameFrmCoaId(ApplicationModuleImpl am, Integer coaId) {
        Object coaDescO = null;
        try {
            coaDescO = ADFModelUtils.callFunction(am, new StringBuilder("SLS.FN_GET_COA_NM_ID_FRM_COA_ID(?,?,?,?)"), new Object[] {
                                                  EbizParams.GLBL_APP_SERV_LOC(), EbizParams.GLBL_APP_CLD_ID(),
                                                  EbizParams.GLBL_HO_ORG_ID(), coaId
            }, Types.VARCHAR);
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error fetching Coa Desc from Coa Id !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
            coaDescO = "";
        }

        if (LogUtil.enableLogger) {
            _log.info("Method | getCoaNameFrmCoaId(ApplicationModuleImpl am, Integer coaId) | called with following parameters ");
            _log.info("=======> getCoaNameFrmCoaId(" + am + "," + coaId + ") | Returned : " + coaDescO);

        }
        return coaDescO.toString();
    }

    /**
     * Method to fetch coa id on the Basis of Eo Id for Current Ho and Current Org.
     * @param am
     * @param eoId
     * @return -1 : In Case of any error
     * @deprecated
     */
    @Deprecated
    public static Integer getCoaIdFrmEoId(ApplicationModuleImpl am, Integer eoId) {
        Object coaIdO = null;
        Integer coaId = -1;
        try {
            coaIdO = ADFModelUtils.callFunction(am, new StringBuilder("SLS.FN_GET_COA_ID_FRM_EO_ID(?,?,?,?,?)"), new Object[] {
                                                EbizParams.GLBL_APP_SERV_LOC(), EbizParams.GLBL_APP_CLD_ID(),
                                                EbizParams.GLBL_HO_ORG_ID(), EbizParams.GLBL_APP_USR_ORG(), eoId
            }, Types.INTEGER);
            if (coaIdO != null) {
                coaId = (Integer) coaIdO;
            }
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error fetching Coa Id from Eo Id !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
            coaIdO = -1;
        }

        if (LogUtil.enableLogger) {
            _log.info("Method | getCoaIdFrmEoId(ApplicationModuleImpl am, Integer eoId) | called with following parameters ");
            _log.info("=======> getCoaIdFrmEoId(" + am + "," + eoId + ") | Returned : " + coaIdO);

        }
        return coaId;
    }

    /**
     * Method to fetch Currency Conversion Rate on the Basis of Currency Id for Current Ho and Current Org.
     * @param am
     * @param currId
     * @return 1 : In Case of any error (1 is default value)
     * @deprecated
     */
    @Deprecated
    public static Number getCurrRateForCurrId(ApplicationModuleImpl am, Integer currId) {
        Number currRate = new Number(1);
        try {
            //FN_GET_CURR_RATE_FRM_CURR_ID
            Object currRateO =
                ADFModelUtils.callFunction(am, new StringBuilder("SLS.FN_GET_CURR_RATE_FRM_CURR_ID(?,?,?,?,?)"), new Object[] {
                                           EbizParams.GLBL_APP_SERV_LOC(), EbizParams.GLBL_APP_CLD_ID(),
                                           EbizParams.GLBL_HO_ORG_ID(), EbizParams.GLBL_APP_USR_ORG(), currId
            }, Types.NUMERIC);

            currRate = (currRateO == null ? new Number(1) : new Number((BigDecimal) currRateO));
        } catch (SQLException e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error fetching Currency Rate from Currency Id !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
        }
        if (LogUtil.enableLogger) {
            _log.info("Method | getCurrRateForCurrId(ApplicationModuleImpl am, Integer currId) | called with following parameters ");
            _log.info("=======> getCurrRateForCurrId(" + am + "," + currId + ") | Returned : " + currRate);

        }
        return currRate;
    }

    /**
     * Method to fetch Sales Executive Id on the Basis of EoId for Current Ho and Current Org.
     * @param am
     * @param eoId
     * @return -1 : In Case of any error (-1 is default value)
     * @deprecated
     */
    @Deprecated
    public static Integer getSalesExecForEoId(ApplicationModuleImpl am, Integer eoId) {
        Integer excId = -1;
        Object execO = null;
        try {
            execO = ADFModelUtils.callFunction(am, new StringBuilder("SLS.FN_GET_EXEC_ID_FRM_EO_ID(?,?,?,?)"), new Object[] {
                                               EbizParams.GLBL_APP_SERV_LOC(), EbizParams.GLBL_APP_CLD_ID(),
                                               EbizParams.GLBL_HO_ORG_ID(), eoId
            }, Types.INTEGER);
            if (execO != null) {
                excId = (Integer) execO;
            }
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error fetching Sales Executive id from Eo Id !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
        }
        if (LogUtil.enableLogger) {
            LogUtil.showInfoLog(_log, "getSalesExecForEoId", new Object[] { "am", "eoId" }, new Object[] { am, eoId },
                                excId);
            _log.info("Method | getSalesExecForEoId(ApplicationModuleImpl am, Integer eoId) | called with following parameters ");
            _log.info("=======> getSalesExecForEoId(" + am + "," + eoId + ") | Returned : " + excId);

        }
        return excId;
    }

    /**
     * Method to fetch Address on the Basis of AddsId for Current Cld and Sloc.
     * @param am
     * @param addsId
     * @return "" : In Case of any error ("" is default value)
     * @deprecated
     */
    @Deprecated
    public static StringBuilder getAddDescFrmAddsId(ApplicationModuleImpl am, String addsId) {
        StringBuilder adds = new StringBuilder("");
        try {
            Object addsO = ADFModelUtils.callFunction(am, new StringBuilder("SLS.FN_GET_ADDS_FRM_ADDS_ID(?,?,?)"), new Object[] {
                                                      EbizParams.GLBL_APP_SERV_LOC(), EbizParams.GLBL_APP_CLD_ID(),
                                                      addsId
            }, Types.VARCHAR);
            if (addsO != null) {
                adds = (addsO == null ? new StringBuilder("") : new StringBuilder(addsO.toString()));
            }
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error fetching Address from Address Id !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            adds = new StringBuilder("");
            e.printStackTrace();
        }

        if (LogUtil.enableLogger) {
            _log.info("Method | getAddDescFrmAddsId(ApplicationModuleImpl am, Integer eoId) | called with following parameters ");
            _log.info("=======> getAddDescFrmAddsId(" + am.getName() + "," + addsId + ") | Returned : " + adds);
        }
        return adds;
    }

    /**
     * Method to fetch default Currency Id on the Basis of EoId.
     * @param am
     * @param eoId
     * @return null : In Case of any error / Default
     * @deprecated
     */
    @Deprecated
    public static Integer getDefaultCurrIdOfEoId(ApplicationModuleImpl am, Integer eoId) {
        Integer currId = null;
        try {
            Object currIdO = ADFModelUtils.callFunction(am, new StringBuilder("SLS.FN_GET_CURR_ID_FRM_EO_ID(?,?,?,?,?)"), new Object[] {
                                                        EbizParams.GLBL_APP_SERV_LOC(), EbizParams.GLBL_APP_CLD_ID(),
                                                        EbizParams.GLBL_HO_ORG_ID(), eoId,EbizParams.GLBL_APP_USR_ORG()
            }, Types.INTEGER);
            if (currIdO != null) {
                currId = (Integer) currIdO;
            }
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error fetching Currency Id id from Eo Id !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
        }
        if (LogUtil.enableLogger) {
            _log.info("Method | getDefaultCurrIdOfEoId(ApplicationModuleImpl am, Integer eoId) | called with following parameters ");
            _log.info("=======> getDefaultCurrIdOfEoId(" + am.getName() + "," + eoId + ") | Returned : " + currId);

        }
        return currId;
    }

    /**
     * Method to fetch Eo Desc on the Basis of Eo Id for Current Ho.
     * @param am
     * @param eoId
     * @return "" : In Case of any error
     */
    public static String getEoDescFrmEoId(ApplicationModuleImpl am, Integer eoId) {
        Object eoDescO = null;
        try {
            eoDescO = ADFModelUtils.callFunction(am, new StringBuilder("SLS.FN_GET_EO_DESC_FRM_EO_ID(?,?,?,?)"), new Object[] {
                                                 EbizParams.GLBL_APP_SERV_LOC(), EbizParams.GLBL_APP_CLD_ID(),
                                                 EbizParams.GLBL_HO_ORG_ID(), eoId
            }, Types.VARCHAR);
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error fetching Eo Desc from Eo Id !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
            eoDescO = "";
        }

        if (LogUtil.enableLogger) {
            _log.info("Method | getEoDescFrmEoId(ApplicationModuleImpl am, String eoId) | called with following parameters ");
            _log.info("=======> getEoDescFrmEoId(" + am + "," + eoId + ") | Returned : " + eoDescO);

        }
        return eoDescO.toString();
    }

    /**
     * Method to fetch User Desc on the Basis of User Id for Current Ho.
     * @param am
     * @param usrId
     * @return "" : In Case of any error
     */
    public static String getUsrDescFrmUsrId(ApplicationModuleImpl am, Integer usrId) {
        Object usrDescO = null;
        try {
            usrDescO = ADFModelUtils.callFunction(am, new StringBuilder("SLS.FN_GET_USR_DESC_FRM_USR_ID(?,?)"), new Object[] {
                                                  EbizParams.GLBL_APP_SERV_LOC(), usrId
            }, Types.VARCHAR);
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error fetching User Desc from User Id !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
            usrDescO = "";
        }

        if (LogUtil.enableLogger) {
            _log.info("Method | getUsrDescFrmUsrId(ApplicationModuleImpl am, String usrId) | called with following parameters ");
            _log.info("=======> getUsrDescFrmUsrId(" + am + "," + usrId + ") | Returned : " + usrDescO);

        }
        return usrDescO == null ? null : usrDescO.toString();
    }

    /**
     * Method to generate docTxn id on the basis of Global Doc Id.
     * @param am
     * @param glblDocId - Document global doc id
     * @return
     */
    public static Object generateDocTxnId(ApplicationModuleImpl am, Integer glblDocId) {
        Object o = null;
        try {
            o = ADFModelUtils.callFunction(am, new StringBuilder("APP.GET_TXN_ID(?,?,?,?,?)"), new Object[] {
                                           EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(),
                                           EbizParams.GLBL_APP_USR_ORG(), EbizParams.GLBL_APP_USR(), glblDocId
            }, Types.VARCHAR);
        } catch (Exception e) {
            // TODO: Add catch code
            ADFModelUtils.showFormattedFacesMessage("There have been some error while generating Transcation Id !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
        }
        if (LogUtil.enableLogger) {
            _log.info("Method | generateDocTxnId(ApplicationModuleImpl am, Integer glblDocId) | called with following parameters ");
            _log.info("=======> generateDocTxnId(" + am + "," + glblDocId + ") | Returned : " + o);

        }
        return o;
    }
    
    /**
     * Method to generate docTxn id on the basis of Global Doc Id and Document type id.
     * @param am
     * @param glblDocId - Document global doc id
     * @param docType - Document type id
     * @return
     */
    public static Object generateDocTxnId(ApplicationModuleImpl am, Integer glblDocId,Integer docTypeId) {
        Object o = null;
        try {
            o = ADFModelUtils.callFunction(am, new StringBuilder("APP.GET_TXN_ID(?,?,?,?,?,?)"), new Object[] {
                                           EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(),
                                           EbizParams.GLBL_APP_USR_ORG(), EbizParams.GLBL_APP_USR(), glblDocId,docTypeId
            }, Types.VARCHAR);
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
            ADFModelUtils.showFormattedFacesMessage("There have been some error while generating Transcation Id !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
        }
        if (LogUtil.enableLogger) {
            _log.info("Method | generateDocTxnId(ApplicationModuleImpl am, Integer glblDocId,Integer docTypeId) | called with following parameters ");
            _log.info("=======> generateDocTxnId(" + am + "," + glblDocId + ","+docTypeId+") | Returned : +" + o);

        }
        return o;
    }
    
    /**
     * Method to fetch Currency Desc on the Basis of Currency Desc from Currency Id.
     * @param am
     * @param currId
     * @return "" : In Case of any error
     */
    public static String getCurrDescFrmCurrId(ApplicationModuleImpl am, Integer currId) {
        Object currDescO = null;
        try {
            currDescO = ADFModelUtils.callFunction(am, new StringBuilder("APP.FN_GET_CURR_DESC_FRM_CURR_ID(?,?,?)"), new Object[] {
                                                  EbizParams.GLBL_APP_SERV_LOC(),EbizParams.GLBL_APP_CLD_ID(), currId
            }, Types.VARCHAR);
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error fetching Currency Desc from Currency Id !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
            currDescO = "";
        }

            if (LogUtil.enableLogger) {
                LogUtil.showInfoLog(_log, "getCurrDescFrmCurrId", new Object[] { "ApplicationModuleImpl am", "String currId"}, new Object[] { am,currId},
                                    currDescO);
            }
        return currDescO == null ? null : currDescO.toString();
    }
    
    /**
     * Method to fetch UOM Desc on the Basis of UOM Desc from UOM Id.
     * @param am
     * @param currId
     * @return "" : In Case of any error
     */
    public static String getUOMDescFrmUOMId(ApplicationModuleImpl am, String uomId) {
        Object currDescO = null;
        try {
            currDescO = ADFModelUtils.callFunction(am, new StringBuilder("APP.FN_GET_UOM_DESC(?,?,?)"), new Object[] {
                                                  EbizParams.GLBL_APP_CLD_ID(),EbizParams.GLBL_APP_SERV_LOC(), uomId
            }, Types.VARCHAR);
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error fetching Currency Desc from Currency Id !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
            currDescO = "";
        }

            if (LogUtil.enableLogger) {
                LogUtil.showInfoLog(_log, "getUOMDescFrmUOMId", new Object[] { "ApplicationModuleImpl am", "String uomId"}, new Object[] { am,uomId},
                                    currDescO);
            }
        return currDescO == null ? null : currDescO.toString();
    }
    
    
}
