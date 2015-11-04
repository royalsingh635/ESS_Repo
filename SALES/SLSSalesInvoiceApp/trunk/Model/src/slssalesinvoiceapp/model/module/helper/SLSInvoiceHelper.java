package slssalesinvoiceapp.model.module.helper;

import adf.utils.ebiz.EbizParams;
import adf.utils.ebiz.EbizParamsAPPUtils;
import adf.utils.model.ADFModelUtils;

import java.sql.Types;

import oracle.jbo.server.ApplicationModuleImpl;

public class SLSInvoiceHelper {
    public SLSInvoiceHelper() {
        super();
    }

    /**
     * Method to generate display id
     * @param P_SLOC_ID
     * @param P_CLD_ID
     * @param P_ORG_ID
     * @param p_doc_id
     * @param P_DOC_hex_ID
     * @param P_DOC_TYPE_ID
     * @param P_TableName
     * @param fyId
     * @return
     */
    public static String getDispDocId(ApplicationModuleImpl am, Object docTxnId, Object invType, Object invSubType,
                                      String mode) {
        String val = (String) ADFModelUtils.callFunction(am, new StringBuilder("GEN_DISP_DOC_ID(?,?,?,?,?,?,?,?,?,?)"), new Object[] {
                                                         EbizParams.GLBL_APP_SERV_LOC(), EbizParams.GLBL_APP_CLD_ID(),
                                                         EbizParams.GLBL_APP_USR_ORG(), 21504, docTxnId, invType,
                                                         "SLS$INV", EbizParamsAPPUtils.getFyIdOnCurrDtAndCurrOrg(am),
                                                         invSubType, mode
        }, Types.VARCHAR);
        return val;
    }
}
