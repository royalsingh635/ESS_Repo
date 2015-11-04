package adf.utils.ebiz;

import adf.utils.log.LogUtil;
import adf.utils.model.ADFModelUtils;
import java.sql.Types;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.share.logging.ADFLogger;
import oracle.jbo.server.ApplicationModuleImpl;

public class EbizParamsSLSUtils {
    private static ADFLogger _log = ADFLogger.createADFLogger(EbizParamsSLSUtils.class);
    public EbizParamsSLSUtils() {
        super();
    }
    
    /**
     * Method to check For Sales Profile Values of the logged in Organisation.
     * @param am
     * @param colName
     * @return
     * N : In case of any errors
     */
    public static String checkSLSProfileValues(ApplicationModuleImpl am, String colName) {
        String ck = "N";
        try {
            Object valO =
                 ADFModelUtils.callFunction(am, new StringBuilder("SLS.fn_sls_chk_org_prf(?,?,?,?)"), new Object[] {
                                                    EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(),
                                                    EbizParams.GLBL_APP_USR_ORG(), colName
            }, Types.VARCHAR);
            if(valO == null ){
                ck = "N";
            }else{
                ck = valO.toString();
            }
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error while checking Sales Profile Values !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
        }
        if (LogUtil.enableLogger) {
            LogUtil.showInfoLog(_log, "checkSLSProfileValues", new Object[] { "ApplicationModuleImpl am", "String colName"}, new Object[] { am,colName},
                                ck);
        }
        return ck;
    }

    /**
     * Method to fetch Customer Part Number from Price Setup.
     * @param am
     * @param colName
     * @return
     * N : In case of any errors
     */
    public static String getEoPartNoFrmItm(ApplicationModuleImpl am, Object eoId, Object itmId) {
        String defPartNo = "";
        try {

            Object o = ADFModelUtils.callFunction(am, new StringBuilder("SLS.FN_GET_EOPART_NO(?,?,?,?,?)"), new Object[] {
                                                  EbizParams.GLBL_APP_SERV_LOC(), EbizParams.GLBL_APP_CLD_ID(),
                                                  EbizParams.GLBL_APP_USR_ORG(), itmId, eoId
            }, Types.VARCHAR);
            if (o != null) {
                defPartNo = o.toString();
            }

        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been some error while fetching Eo Part Number !",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
        }
        if (LogUtil.enableLogger) {
            LogUtil.showInfoLog(_log, "getEoPartNoFrmItm", new Object[] {
                                "ApplicationModuleImpl am", "Object eoId", "Object itmId" }, new Object[] {
                                am, eoId, itmId  }, defPartNo);
        }
        return defPartNo;
    }
}
