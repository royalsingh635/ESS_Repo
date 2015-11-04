package adf.utils.ebiz;

import adf.utils.log.LogUtil;
import adf.utils.model.ADFModelUtils;
import java.sql.Types;
import javax.faces.application.FacesMessage;
import oracle.adf.share.logging.ADFLogger;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.domain.Number;

/**
 *  This class basically contains the methods that are basically used for the purpose implementing workflow.
 *  
 */
public class WorkFlowUtils {
    private static ADFLogger _log = ADFLogger.createADFLogger(WorkFlowUtils.class);


    /**
     * Method to fetch workflow Id of the given Document on given document type.
     * @param am - Application Module
     * @param DocNo - Document Number
     * @param docType - Document Sub Type
     * @return null in case of error
     */
    public static Object getWorkFlowId(ApplicationModuleImpl am, Integer DocNo, Integer docType) {
        Object o = null;
        try {
            o = ADFModelUtils.callFunction(am, new StringBuilder("APP.WF_GET_ID(?,?,?,?,?)"), new Object[] {
                                           EbizParams.GLBL_APP_SERV_LOC(), EbizParams.GLBL_APP_CLD_ID(),
                                           EbizParams.GLBL_APP_USR_ORG(), DocNo, docType
            }, Types.VARCHAR);
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been an error calling function WF_GET_ID",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
        }
        if (LogUtil.enableLogger) {
            _log.info("Method | getWorkFlowId(ApplicationModuleImpl am, Integer DocNo,Integer docType) | called with following parameters ");
            _log.info("=======> getWorkFlowId(" + am + "," + DocNo + "," + docType + ") | Returned : " + o);
        }

        return (o == null ? null : o.toString());
    }


    /**
     * Method to fetch Current UserLevel for the provided WorkFlow Id, Document No. ,Document Type
     * @param am - Instance of Application Module
     * @param WfNo - Workflow Id
     * @param DocNo - Document No.
     * @param docType - Document Subtype No.
     * @return null in case of error
     */
    public static Integer getWorkFlowCurrUsrLvl(ApplicationModuleImpl am, Object WfNo, Integer DocNo, Integer docType) {
        Object o = null;
        try {
            o = ADFModelUtils.callFunction(am, new StringBuilder("APP.WF_GET_USR_LEVEL(?,?,?,?,?,?,?)"), new Object[] {
                                           EbizParams.GLBL_APP_SERV_LOC(), EbizParams.GLBL_APP_CLD_ID(),
                                           EbizParams.GLBL_APP_USR_ORG(), EbizParams.GLBL_APP_USR(), WfNo, DocNo,
                                           docType
            }, Types.INTEGER);
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been an error calling function WF_GET_USR_LEVEL",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
        }
        if (LogUtil.enableLogger) {
            LogUtil.showInfoLog(_log, "getWorkFlowCurrUsrLvl", new Object[] { "am", "WfNo", "DocNo", docType }, new Object[] {
                                am, WfNo, DocNo, docType
            }, o);
        }
        return (o == null ? null : (Integer) o);

    }


    /**
     * Method to insert Workflow entries in Transaction table.
     * @param am - Instance of Application Modulw
     * @param DocNo - Document Id for whome Entries need to be made
     * @param docType - Document sub type Id for whome Entries need to be made
     * @param docTxnId - Transaction Id of the document for whome workflow entries need to be passed
     * @param wfId - Workflow id of the Document
     * @param usrIdFrm - User Id From
     * @param usrIdTo - User Id From
     * @param lvlFrm - Level from
     * @param lvlTo - Level to
     * @param action - such "I"
     * @param remark - Remarks that needs to be added
     * @param amount - Amount
     * @return
     */
    public static Object insertEntriesIntoWfTxn(ApplicationModuleImpl am, Integer DocNo, Integer docType, Object docTxnId,
                                         Object wfId, Integer usrIdFrm, Integer usrIdTo, Integer lvlFrm, Integer lvlTo,
                                         String action, String remark, Number amount) {
        Object o = null;
        try {
            o = ADFModelUtils.callFunction(am, new StringBuilder("APP.WF_INS_TXN(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"), new Object[] {
                                           EbizParams.GLBL_APP_SERV_LOC(), EbizParams.GLBL_APP_CLD_ID(),
                                           EbizParams.GLBL_APP_USR_ORG(), DocNo, docType, wfId, docTxnId, usrIdFrm,
                                           usrIdTo, lvlFrm, lvlTo, action, remark, amount, "S"
            }, Types.VARCHAR);
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been an error calling function WF_INS_TXN",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            e.printStackTrace();
        }
        if (LogUtil.enableLogger) {
            LogUtil.showInfoLog(_log, "insertIntoWfTxn", new Object[] {
                                "am", "DocNo", "docType", "docTxnId", "wfId", "usrIdFrm", "usrIdTo", "lvlFrm", "lvlTo",
                                "action", "remark", "amount"
            }, new Object[] {
                am, DocNo, docType, docTxnId, wfId, usrIdFrm, usrIdTo, lvlFrm, lvlTo, action, remark, amount }, o);
        }

        return o;
    }
    
    

    /**
     * Methhod to find out that on which user the current document is pending.
     * @param am - Application Module
     * @param docId - Document Number
     * @param docType - Document Sub Type
     * @param docTxnId - Document Transaction Id
     * @return
     */
    public static Integer docPendingAt(ApplicationModuleImpl am,Integer docId,Integer docType, Object docTxnId) {
        Integer i = -1;
        try {
            Object o = ADFModelUtils.callFunction(am, new StringBuilder("APP.WF_GET_CUR_USR(?,?,?,?,?,?)"), new Object[] {
                                                  EbizParams.GLBL_APP_SERV_LOC(), EbizParams.GLBL_APP_CLD_ID(),
                                                  EbizParams.GLBL_APP_USR_ORG(), docId, docTxnId.toString(), docType
            }, Types.INTEGER);
            if (o != null) {
                i = (Integer) o;
            }
        } catch (Exception e) {
            ADFModelUtils.showFormattedFacesMessage("There have been an error calling function WF_GET_CUR_USR",
                                                    e.getMessage(), FacesMessage.SEVERITY_FATAL);
            //e.printStackTrace();
        }
        if (LogUtil.enableLogger) {
            LogUtil.showInfoLog(_log, "docPendingAt", new Object[] {
            "ApplicationModuleImpl am","Integer docId","Integer docType", "Object docTxnId"
            }, new Object[] {
                am,docId,docType,docTxnId }, i);
        }
        return i;
    }
    
    
    /**
     * Method to insert workflow entries for a document.
     * @param am - Application Module
     * @param DocNo - Document Number
     * @param docType - Document Sub Type
     * @param docTxnId - Document Transaction Id
     * @param usrIdFrm - User Id From
     * @param usrIdTo - User Id To
     * @param remark - Remarks
     * @param amount - Amount
     * @return 
     *  1 -- if entries passed correctly
     * -1 -- in case of any error
     * @throws Exception
     */
    public static Integer insertWfTxnEntriesForCurrDoc(ApplicationModuleImpl am, Integer DocNo, Integer docType,
                                                       Object docTxnId, Integer usrIdFrm, Integer usrIdTo,
                                                       String remark,
                                                       oracle.jbo.domain.Number amount) throws Exception {
        Integer i = 1;
        Object wkflowId = getWorkFlowId(am, DocNo, docType);
        if (wkflowId != null) {
            Integer currUsrLvl = getWorkFlowCurrUsrLvl(am, wkflowId, DocNo, docType);
            Object entriesIntoWfTxn =
                insertEntriesIntoWfTxn(am, DocNo, docType, docTxnId, wkflowId, usrIdFrm, usrIdTo, currUsrLvl,
                                       currUsrLvl, "I", remark, amount);
            if ((entriesIntoWfTxn == null && (Integer) entriesIntoWfTxn != 1)) {
                i = -1;
            }
        } else {
            i = -1;
            throw new Exception("WorkFlow is not defined for the current document!");
        }
        if (LogUtil.enableLogger) {
            LogUtil.showInfoLog(_log, "insertWfTxnEntriesForCurrDoc", new Object[] {
                                "ApplicationModuleImpl am", "Integer DocNo", "Integer docType", "Object docTxnId",
                                "Integer usrIdFrm", "Integer usrIdTo", "String remark", "Number amount"
            }, new Object[] { am, DocNo, docType, docTxnId, usrIdFrm, usrIdTo, remark, amount }, i);
        }
        return i;
    }
}
