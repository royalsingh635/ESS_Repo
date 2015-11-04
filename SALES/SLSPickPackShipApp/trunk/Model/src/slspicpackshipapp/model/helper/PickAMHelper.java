package slspicpackshipapp.model.helper;

import adf.utils.ebiz.EbizParams;
import adf.utils.model.ADFModelUtils;

import java.sql.Types;

import slspicpackshipapp.model.service.pickPackShipAMImpl;

public class PickAMHelper {
    public PickAMHelper() {
        super();
    }

    /**
     * Method to check For Sales Profile Values of the Organisation.
     * @param am
     * @param colName
     * @return
     */
    public static StringBuffer checkforProfileValues(pickPackShipAMImpl am, String colName) {
        StringBuffer ck = new StringBuffer("");
        try {
            String policyPrice =
                (String) ADFModelUtils.callFunction(am, new StringBuilder("SLS.fn_sls_chk_org_prf(?,?,?,?)"), new Object[] {
                                                    EbizParams.GLBL_APP_CLD_ID(), EbizParams.GLBL_APP_SERV_LOC(),
                                                    EbizParams.GLBL_APP_USR_ORG(), colName
            }, Types.VARCHAR);

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

}
