package mmgateentry.view.beans;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.RowIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.rules.JboPrecisionScaleValidator;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class GateEntryAddEditBean implements Serializable {
    private RichSelectOneChoice sourceDocType;
    private RichInputListOfValues sourceDocNo;
    private RichPopup itemPopUp;
    private RichTreeTable geTreeTable;
    private RichInputDate sourceDocDt;
    private RichInputText schdlNo;
    private RichInputText geNo;
    private RichInputDate geDt;
    private RichSelectOneChoice geWhId;
    private Boolean enableHeader = true;
    private RichInputText supplierBind;
    private RichInputListOfValues itmNameBindVar;
    private RichSelectOneChoice uomBindVar;
    private RichInputText itmQty;
    private RichPopup chkStatusPopUp;
    private RichSelectOneChoice geStatus;
    private String msgpop;
    private static Integer SOURCE_DOC_TYPE_PO = 266;
    private static Integer SOURCE_DOC_TYPE_IMPORT_PO = 819;
    private static Integer SOURCE_DOC_TYPE_JITR = 938;
    //private static Integer SOURCE_DOC_TYPE_SO=268;
    private static Integer SOURCE_DOC_TYPE_WOUT_PO = 370;

    private static Integer SOURCE_DOC_TYPE_TRFO = 270;
    private static Integer SOURCE_DOC_TYPE_WOUT_SO = 269;
    private static Integer SOURCE_DOC_TYPE_CPO = 459;
    private static Integer SOURCE_DOC_TYPE_PROCS_ORD = 268;
    private static ADFLogger _log = ADFLogger.createADFLogger(GateEntryAddEditBean.class);
    private RichPopup confDlvQtyChkPopUp;
    private RichSelectBooleanCheckbox returnFlg;
    private RichInputText returnReason;
    private RichPanelLabelAndMessage supCustNameBinding;
    private RichSelectOneChoice sourceOrgBinding;
    private RichSelectOneChoice whIdSrcBinding;
    private RichSelectOneChoice rcptSrcTypeBinding;
    private RichInputText itmIdRcpt;
    private RichPopup deleteGePopUp;
    private RichColumn geNodeSrcColumnVar;
    private RichInputListOfValues eoNameBinding;
    private RichInputText totRcptQtyBinding;
    private RichPopup barCodePopBinding;
    private RichInputListOfValues itmIdOnPopBinding;
    private RichInputText itmNmOnPopBinding;
    private RichInputText delvQtyOnPopBinding;
    private RichInputText rcptQtyOnPopBinding;
    private RichInputText retQtyOnPopBinding;
    private RichOutputText itmChkOnPopBinding;
    private RichPopup geCancelPopup;


    public GateEntryAddEditBean() {
    }


    private void showPopup(RichPopup popup, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && popup != null) {
                String popupId = popup.getClientId(context);
                if (popupId != null) {
                    StringBuilder script = new StringBuilder();
                    script.append("var popup = AdfPage.PAGE.findComponent('").append(popupId).append("'); ");
                    if (visible) {
                        script.append("if (!popup.isPopupVisible()) { ").append("popup.show();}");
                    } else {
                        script.append("if (popup.isPopupVisible()) { ").append("popup.hide();}");
                    }
                    ExtendedRenderKitService erks =
                        Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
                    erks.addScript(context, script.toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String resolvEl(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = valueExp.getValue(elContext).toString();
        return Message;
    }

    /**
     *      Method to show validation message(I,E,W)
     *      mesg:Message to display
     *      sev:Severity(I,E,W)
     *      chk:true=if resource bundle is used
     *      typFlg: 'F' for FM , 'V' for VE
     * */
    public void showFacesMessage(String mesg, String sev, Boolean chk, String typFlg) {
        FacesMessage message = new FacesMessage(mesg);
        if (chk == true) {
            String msg = resolvEl("#{bundle['" + mesg + "']}");
            message = new FacesMessage(msg);
        }
        if (sev.equalsIgnoreCase("E")) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
        } else if (sev.equalsIgnoreCase("W")) {
            message.setSeverity(FacesMessage.SEVERITY_WARN);
        } else if (sev.equalsIgnoreCase("I")) {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        } else {
            message.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        if (typFlg.equals("F")) {
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else if (typFlg.equals("V")) {
            throw new ValidatorException(message);
        }
    }

    public void selectItemAction(ActionEvent actionEvent) {
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer paramUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String paramHoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));

        _log.info("Count1==" + geTreeTable.getRowCount());
        if (sourceDocType.getValue() != null) {
            if (sourceDocType.getValue().toString().trim().length() > 0) {

                if ((sourceDocType.getValue().toString().equals(SOURCE_DOC_TYPE_WOUT_PO.toString())) ||
                    (sourceDocType.getValue().toString().equals(SOURCE_DOC_TYPE_WOUT_SO.toString())) ||
                    (sourceDocType.getValue().toString().equals(SOURCE_DOC_TYPE_PROCS_ORD.toString()))) {

                    if (supplierBind.getValue() != null) {

                        String type = null;
                        if (sourceDocType.getValue().toString().equals(SOURCE_DOC_TYPE_WOUT_PO.toString()))
                            type = "WPO";
                        else if (sourceDocType.getValue().toString().equals(SOURCE_DOC_TYPE_WOUT_SO.toString()))
                            type = "WSO";
                        else if (sourceDocType.getValue().toString().equals(SOURCE_DOC_TYPE_PROCS_ORD.toString()))
                            type = "PRO";

                        //check if record present in tree
                        // if no, then call method to create entry in src table by generating temp PO no. and return TEMP_PO_NO
                        //if yes, then get TEMP_PO_NO/Temp_doc_id and add item to table..


                        _log.info("Count==" + geTreeTable.getRowCount());

                        _log.info("itm==" + itmNameBindVar.getValue());
                        if (itmNameBindVar.getValue() != null &&
                            itmNameBindVar.getValue().toString().trim().length() > 0) {
                            if (uomBindVar.getValue() != null) {
                                if (itmQty.getValue() != null) {
                                    _log.info("qty=" + itmQty.getValue());
                                    Number qty = (Number) itmQty.getValue();

                                    if (qty.compareTo(new Number(0)) == 1) {


                                        if (geTreeTable.getRowCount() == 0) {
                                            OperationBinding genDocIdOp =
                                                getBindings().getOperationBinding("generateTempDocId");
                                            genDocIdOp.getParamsMap().put("UsrId", paramUsrId);
                                            genDocIdOp.getParamsMap().put("CldId", paramCldId);
                                            genDocIdOp.getParamsMap().put("SlocId", paramSlocId);
                                            genDocIdOp.getParamsMap().put("OrgId", paramOrgId);
                                            genDocIdOp.getParamsMap().put("WhId", geWhId.getValue().toString());
                                            if (type.equals("WPO"))
                                                genDocIdOp.getParamsMap().put("DocTypeSrc", SOURCE_DOC_TYPE_WOUT_PO);
                                            else if (type.equals("WSO"))
                                                genDocIdOp.getParamsMap().put("DocTypeSrc", SOURCE_DOC_TYPE_WOUT_SO);
                                            else if (type.equals("PRO"))
                                                genDocIdOp.getParamsMap().put("DocTypeSrc", SOURCE_DOC_TYPE_PROCS_ORD);
                                            genDocIdOp.execute();

                                            if (type.equals("WPO")) {
                                                OperationBinding setCurrIdWithoutPo =
                                                    getBindings().getOperationBinding("setCurrIdSpInMtlGeWithoutPO");
                                                setCurrIdWithoutPo.execute();
                                            }
                                        }

                                        OperationBinding chkItmDup =
                                            getBindings().getOperationBinding("checkItmDuplicate");
                                        chkItmDup.getParamsMap().put("itmNm", itmNameBindVar.getValue().toString());
                                        chkItmDup.getParamsMap().put("uomId", uomBindVar.getValue().toString());
                                        chkItmDup.execute();

                                        if ("N".equalsIgnoreCase(chkItmDup.getResult().toString())) {
                                            OperationBinding addItmOp =
                                                getBindings().getOperationBinding("addItemToGe");
                                            addItmOp.getParamsMap().put("ItmName",
                                                                        itmNameBindVar.getValue().toString());
                                            addItmOp.getParamsMap().put("ItmUom", uomBindVar.getValue().toString());
                                            addItmOp.getParamsMap().put("ItmQty", (Number) itmQty.getValue());
                                            addItmOp.execute();

                                            if (addItmOp.getErrors().isEmpty()) {
                                                OperationBinding setnull =
                                                    getBindings().getOperationBinding("setNullforAll");
                                                setnull.execute();
                                                itmNameBindVar.setValue("");
                                                itmNameBindVar.setValue(null);
                                                uomBindVar.resetValue();
                                                uomBindVar.setValue("");
                                                uomBindVar.setValue(null);
                                                itmQty.setValue("");
                                                itmQty.setValue(null);
                                                AdfFacesContext.getCurrentInstance().addPartialTarget(itmQty);
                                                AdfFacesContext.getCurrentInstance().addPartialTarget(uomBindVar);
                                                AdfFacesContext.getCurrentInstance().addPartialTarget(itmNameBindVar);

                                                _log.info("Item added");
                                            } else {
                                                _log.info("Error during adding item!!!.");
                                            }
                                        } else {


                                            String exit_msg =
                                                resolvElDCMsg("#{bundle['MSG.427']}").toString(); //Item already exists.


                                            showFacesMessage(exit_msg, "E", false, "F");
                                        }
                                    } else {
                                        _log.info("Quantity must be greater than 0.");
                                        showFacesMessage(resolvElDCMsg("#{bundle['MSG.337']}").toString(), "E", false,
                                                         "F");

                                        // showFacesMessage("Quantity must be greater than 0.", "E", false, "F");
                                    }
                                } else {
                                    _log.info("Quantity Null");
                                    showFacesMessage(resolvElDCMsg("#{bundle['MSG.657']}").toString(), "E", false, "F");
                                    // showFacesMessage("Quantity must be entered.", "E", false, "F");
                                }
                            } else {
                                _log.info("UOm Null");
                                showFacesMessage(resolvElDCMsg("#{bundle['MSG.658']}").toString(), "E", false, "F");
                                //showFacesMessage("Unit must be selected.", "E", false, "F");
                            }
                        } else {
                            _log.info("Itm Null");
                            showFacesMessage(resolvElDCMsg("#{bundle['MSG.659']}").toString(), "E", false, "F");
                            //showFacesMessage("Please select the Item.", "E", false, "F");
                        }

                    } else {

                        if ((sourceDocType.getValue().toString().equals(SOURCE_DOC_TYPE_WOUT_PO.toString())))
                            showFacesMessage(resolvElDCMsg("#{bundle['MSG.660']}").toString(), "E", false,
                                             "F"); //  showFacesMessage("Please Select Supplier.", "E", false, "F");
                        else if (sourceDocType.getValue().toString().equals(SOURCE_DOC_TYPE_WOUT_SO.toString()))
                            showFacesMessage("Please Select Customer.", "E", false, "F");
                        else if (sourceDocType.getValue().toString().equals(SOURCE_DOC_TYPE_PROCS_ORD.toString()))
                            showFacesMessage("Please Select Customer.", "E", false, "F");

                    }

                } else if (Integer.parseInt(sourceDocType.getValue().toString()) == SOURCE_DOC_TYPE_PO) {
                    //check For Dups
                    if (supplierBind.getValue() != null && supplierBind.getValue().toString().trim().length() > 0) {
                        if (sourceDocNo.getValue() != null && sourceDocNo.getValue().toString().trim().length() > 0) {

                            OperationBinding chkSuppValid =
                                getBindings().getOperationBinding("chkSupplierInvcCopyReceipt");
                            chkSuppValid.execute();
                            _log.info(" value return  " + chkSuppValid.getResult());
                            if (chkSuppValid.getResult() != null &&
                                ((Integer) chkSuppValid.getResult()).compareTo(new Integer(1)) == 0) {
                                _log.info(" value true ");
                            } else {
                                showFacesMessage("Supplier document not received .Can not proceed.", "W", false, "F");
                                return;
                            }


                            OperationBinding dupChk = getBindings().getOperationBinding("checkForDuplicateDocNo");
                            dupChk.getParamsMap().put("CldId", paramCldId);
                            dupChk.getParamsMap().put("SlocId", paramSlocId);
                            dupChk.getParamsMap().put("OrgId", paramOrgId);
                            dupChk.getParamsMap().put("SrcDocNo", sourceDocNo.getValue());
                            dupChk.execute();

                            if ("Y".equals(dupChk.getResult().toString())) {
                                // String msg = resolvEl("#{bundle['MSG.375']}");
                                showFacesMessage("MSG.375", "E", true, "F");

                            } else {


                                //Check if tolerance days has to be checked.Y/N
                                OperationBinding chkTolD = getBindings().getOperationBinding("chkToleranceDays");
                                chkTolD.getParamsMap().put("CldId", paramCldId);
                                chkTolD.getParamsMap().put("SlocId", paramSlocId);
                                chkTolD.getParamsMap().put("orgId", paramOrgId);
                                chkTolD.execute();
                                String ret = "0";
                                if (chkTolD.getResult().toString().equals("Y")) {
                                    _log.info("--Tolerance to be checked as per MM$PRF--");
                                    OperationBinding chkTolrDay =
                                        getBindings().getOperationBinding("validateToleranceDays");
                                    chkTolrDay.getParamsMap().put("CldId", paramCldId);
                                    chkTolrDay.getParamsMap().put("SlocId", paramSlocId);
                                    chkTolrDay.getParamsMap().put("OrgId", paramOrgId);
                                    chkTolrDay.getParamsMap().put("p_po_no", null);
                                    chkTolrDay.getParamsMap().put("p_rcpt_date", (Timestamp) geDt.getValue());
                                    chkTolrDay.execute();
                                    if (chkTolrDay.getResult() != null)
                                        ret = chkTolrDay.getResult().toString();
                                    _log.info("Is PO acceptable as per tolerance : " + ret);
                                } else if (chkTolD.getResult().toString().equals("N")) {
                                    _log.info("--Tolerance not to be checked as per MM$PRF--");

                                    OperationBinding chkDtlr = getBindings().getOperationBinding("chkDaysTolerance");
                                    chkDtlr.execute();
                                    if (!(chkDtlr.getResult() != null))
                                        ret = "-1";
                                    else if (chkDtlr.getResult() != null && chkDtlr.getResult().toString().equals("Y"))
                                        ret = "1";
                                    else if (chkDtlr.getResult() != null && chkDtlr.getResult().toString().equals("N"))
                                        ret = "-2";
                                    else
                                        ret = "-1";
                                } else {
                                    ret = "-1";
                                }
                                if (ret.equals("1")) {


                                    OperationBinding chkCurr =
                                        getBindings().getOperationBinding("checkCurrCompatibility");
                                    chkCurr.getParamsMap().put("OrgId", paramOrgId);
                                    chkCurr.getParamsMap().put("CldId", paramCldId);
                                    chkCurr.getParamsMap().put("SlocId", paramSlocId);
                                    chkCurr.getParamsMap().put("PoDocId", sourceDocNo.getValue().toString());
                                    chkCurr.execute();

                                    if ("Y".equalsIgnoreCase(chkCurr.getResult().toString())) {

                                        OperationBinding chkWh =
                                            getBindings().getOperationBinding("isPoWarehouseCompatible");
                                        chkWh.execute();
                                        _log.info("Currency OK");
                                        if ("Y".equalsIgnoreCase(chkWh.getResult().toString())) {

                                            _log.info("Warehouse OK");

                                            OperationBinding itmPop =
                                                getBindings().getOperationBinding("getItemsForPopUp");
                                            itmPop.getParamsMap().put("docType",
                                                                      Integer.parseInt(sourceDocType.getValue().toString()));
                                            itmPop.getParamsMap().put("SlocId", paramSlocId);
                                            itmPop.getParamsMap().put("CldId", paramCldId);
                                            itmPop.getParamsMap().put("OrgId", paramOrgId);
                                            itmPop.getParamsMap().put("HoOrgId", paramHoOrgId);

                                            System.out.println("--" + sourceDocNo.getValue());
                                            itmPop.getParamsMap().put("sourceDocNo", sourceDocNo.getValue());
                                            itmPop.execute();

                                            if (itmPop.getErrors().isEmpty()) {
                                                if (sourceDocType != null && sourceDocNo != null) {
                                                    if (Integer.parseInt(sourceDocType.getValue().toString()) ==
                                                        SOURCE_DOC_TYPE_PO) {
                                                        //    _log.info("--Populate from Po call--");



                                                        /** po insertion */
                                                        OperationBinding checkAvlItm =
                                                            getBindings().getOperationBinding("checkItemAvlblOrNotPo");
                                                        checkAvlItm.execute();


                                                        _log.info("any stockable items available or not " +
                                                                  checkAvlItm.getResult());

                                                        Integer avlbl = (Integer) checkAvlItm.getResult();
                                                        if (avlbl.compareTo(new Integer(0)) == 1) {
                                                            OperationBinding pendqty =
                                                                getBindings().getOperationBinding("poItmQtyGrtrZero");
                                                            pendqty.execute();
                                                            Integer pendChk = (Integer) pendqty.getResult();

                                                            if (pendChk.compareTo(new Integer(0)) == 1) {
                                                                OperationBinding popGeItmPo =
                                                                    getBindings().getOperationBinding("populateGeItmfromPo");
                                                                popGeItmPo.execute();
                                                                Integer noOfRows = 0;
                                                                if (popGeItmPo.getErrors().size() == 0)
                                                                    noOfRows = (Integer) popGeItmPo.getResult();
                                                                else
                                                                    System.out.println("Error while populateGeItmfromPo is == " +
                                                                                       popGeItmPo.getErrors());
                                                                if (noOfRows == 0) {
                                                                    String item_msg =
                                                                        resolvElDCMsg("#{bundle['MSG.661']}").toString();
                                                                    FacesContext.getCurrentInstance().addMessage(null,
                                                                                                                 new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                                                                                                  item_msg,
                                                                                                                                  null)); //"This PO have no item for this Schedule No."
                                                                } else {
                                                                    sourceDocNo.setValue("");
                                                                    sourceDocDt.resetValue();
                                                                    sourceDocDt.setValue(null);
                                                                    schdlNo.resetValue();
                                                                    schdlNo.setValue(null);
                                                                    // add for currency first


                                                                    _log.info(" inside get select row count   ");

                                                                    OperationBinding rowCountBind =
                                                                        getBindings().getOperationBinding("getGESrcCount");
                                                                    rowCountBind.execute();
                                                                    if (rowCountBind.getResult() != null) {
                                                                        _log.info(" inside get select row count value    ::::::  " +
                                                                                  rowCountBind.getResult());
                                                                        Integer count =
                                                                            Integer.parseInt(rowCountBind.getResult().toString());
                                                                        if (count.compareTo(new Integer(1)) ==
                                                                            0) {
                                                                            /**  ---------------------- set currency id in case of purchase order ------------------------     */
                                                                            OperationBinding opcurr =
                                             getBindings().getOperationBinding("setCurrIdSpPoToMtlGe");
                                                                            opcurr.getParamsMap().put("value",
                                                                                                      SOURCE_DOC_TYPE_PO);
                                                                            opcurr.execute();
                                                                        }

                                                                    }


                                                                }

                                                                OperationBinding exec =
                                                                    getBindings().getOperationBinding("Execute1");
                                                                exec.execute();
                                                                OperationBinding execPar =
                                                                    getBindings().getOperationBinding("Execute");
                                                                execPar.execute();

                                                                AdfFacesContext.getCurrentInstance().addPartialTarget(sourceDocNo);
                                                                AdfFacesContext.getCurrentInstance().addPartialTarget(sourceDocDt);
                                                                AdfFacesContext.getCurrentInstance().addPartialTarget(schdlNo);
                                                                AdfFacesContext.getCurrentInstance().addPartialTarget(geTreeTable);
                                                            } else {
                                                                showFacesMessage("All quantity already receive for selected document ",
                                                                                 "E", false, "F");
                                                            }
                                                        } else {
                                                            showFacesMessage("No stockable item available in the selected document ",
                                                                             "E", false, "F");
                                                        }


                                                    }
                                                }
                                                //  showPopup(itemPopUp, true);
                                            }
                                        } else {
                                            //Purchase Order selected is not configured to be received in the selected Warehouse.
                                            showFacesMessage(resolvElDCMsg("#{bundle['MSG.662']}").toString(), "E",
                                                             false, "F");
                                        }
                                    } else {
                                        showFacesMessage(resolvElDCMsg("#{bundle['MSG.663']}").toString(), "E", false,
                                                         "F");
                                        //Purchase Orders with same currency can be added in a single Gate Entry.Purchase Order selected is not currency compatible.
                                    }
                                }

                                else if (ret.equals("-2")) {
                                    showFacesMessage("Cannot accept Document as it is before or after tolerance days",
                                                     "E", false, "F");

                                } else if (ret.equals("-1")) {
                                    showFacesMessage(resolvElDCMsg("#{bundle['MSG.662']}").toString(), "E", false, "F");
                                    //showFacesMessage("MSG.734", "E", true, "F");
                                }
                            }
                        } else {
                            //Please select the Document No.
                            showFacesMessage(resolvElDCMsg("#{bundle['MSG.664']}").toString(), "E", false, "F");
                        }
                    } else {

                        showFacesMessage(resolvElDCMsg("#{bundle['MSG.660']}").toString(), "E", false,
                                         "F"); //Please Select Supplier.
                    }
                } else if (Integer.parseInt(sourceDocType.getValue().toString()) == SOURCE_DOC_TYPE_IMPORT_PO) {
                    //check For Dups
                    if (supplierBind.getValue() != null && supplierBind.getValue().toString().trim().length() > 0) {
                        if (sourceDocNo.getValue() != null && sourceDocNo.getValue().toString().trim().length() > 0) {

                            OperationBinding chkSuppValid =
                                getBindings().getOperationBinding("chkSupplierInvcCopyReceipt");
                            chkSuppValid.execute();
                            _log.info(" value return  " + chkSuppValid.getResult());
                            if (chkSuppValid.getResult() != null &&
                                ((Integer) chkSuppValid.getResult()).compareTo(new Integer(1)) == 0) {
                                _log.info(" value true ");
                            } else {
                                showFacesMessage("Supplier document not received .Can not proceed.", "W", false, "F");
                                return;
                            }
                            _log.info("Inside IPO ::::: ");
                            OperationBinding ipoChk = getBindings().getOperationBinding("checkForIPODocumentValide");
                            ipoChk.getParamsMap().put("CldId", paramCldId);
                            ipoChk.getParamsMap().put("SlocId", paramSlocId);
                            ipoChk.getParamsMap().put("OrgId", paramOrgId);
                            ipoChk.getParamsMap().put("SrcDocNo", sourceDocNo.getValue());
                            ipoChk.execute();
                            _log.info("Inside IPO check result ::::: " + ipoChk.getResult());
                            if (ipoChk.getResult() != null && "Y".equalsIgnoreCase(ipoChk.getResult().toString())) {

                            } else {
                                showFacesMessage("Import PO not valid for following reason" + ipoChk.getResult(), "W",
                                                 false, "F");
                                return;
                            }


                            OperationBinding dupChk = getBindings().getOperationBinding("checkForDuplicateDocNo");
                            dupChk.getParamsMap().put("CldId", paramCldId);
                            dupChk.getParamsMap().put("SlocId", paramSlocId);
                            dupChk.getParamsMap().put("OrgId", paramOrgId);
                            dupChk.getParamsMap().put("SrcDocNo", sourceDocNo.getValue());
                            dupChk.execute();


                            if ("Y".equals(dupChk.getResult().toString())) {
                                // String msg = resolvEl("#{bundle['MSG.375']}");
                                showFacesMessage("MSG.375", "E", true, "F");

                            } else {


                                //Check if tolerance days has to be checked.Y/N
                                OperationBinding chkTolD = getBindings().getOperationBinding("chkToleranceDays");
                                chkTolD.getParamsMap().put("CldId", paramCldId);
                                chkTolD.getParamsMap().put("SlocId", paramSlocId);
                                chkTolD.getParamsMap().put("orgId", paramOrgId);
                                chkTolD.execute();
                                String ret = "0";
                                if (chkTolD.getResult().toString().equals("Y")) {
                                    _log.info("--Tolerance to be checked as per MM$PRF--");
                                    OperationBinding chkTolrDay =
                                        getBindings().getOperationBinding("validateToleranceDays");
                                    chkTolrDay.getParamsMap().put("CldId", paramCldId);
                                    chkTolrDay.getParamsMap().put("SlocId", paramSlocId);
                                    chkTolrDay.getParamsMap().put("OrgId", paramOrgId);
                                    chkTolrDay.getParamsMap().put("p_po_no", null);
                                    chkTolrDay.getParamsMap().put("p_rcpt_date", (Timestamp) geDt.getValue());
                                    chkTolrDay.execute();
                                    if (chkTolrDay.getResult() != null)
                                        ret = chkTolrDay.getResult().toString();
                                    _log.info("Is PO acceptable as per tolerance : " + ret);
                                } else if (chkTolD.getResult().toString().equals("N")) {
                                    _log.info("--Tolerance not to be checked as per MM$PRF--");

                                    OperationBinding chkDtlr = getBindings().getOperationBinding("chkDaysTolerance");
                                    chkDtlr.execute();
                                    if (!(chkDtlr.getResult() != null))
                                        ret = "-1";
                                    else if (chkDtlr.getResult() != null && chkDtlr.getResult().toString().equals("Y"))
                                        ret = "1";
                                    else if (chkDtlr.getResult() != null && chkDtlr.getResult().toString().equals("N"))
                                        ret = "-2";
                                    else
                                        ret = "-1";
                                } else {
                                    ret = "-1";
                                }
                                if (ret.equals("1")) {


                                    OperationBinding chkCurr =
                                        getBindings().getOperationBinding("checkCurrCompatibility"); // Pending Changes for Import Purchase Order (TO DO...)
                                    chkCurr.getParamsMap().put("OrgId", paramOrgId);
                                    chkCurr.getParamsMap().put("CldId", paramCldId);
                                    chkCurr.getParamsMap().put("SlocId", paramSlocId);
                                    chkCurr.getParamsMap().put("PoDocId", sourceDocNo.getValue().toString());
                                    chkCurr.execute();

                                    if ("Y".equalsIgnoreCase(chkCurr.getResult().toString())) {

                                        OperationBinding chkWh =
                                            getBindings().getOperationBinding("isPoWarehouseCompatible");
                                        chkWh.execute();
                                        _log.info("Currency OK");
                                        if ("Y".equalsIgnoreCase(chkWh.getResult().toString())) {

                                            _log.info("Warehouse OK");

                                            OperationBinding itmPop =
                                                getBindings().getOperationBinding("getItemsForPopUp");
                                            itmPop.getParamsMap().put("docType",
                                                                      Integer.parseInt(sourceDocType.getValue().toString()));
                                            itmPop.getParamsMap().put("SlocId", paramSlocId);
                                            itmPop.getParamsMap().put("CldId", paramCldId);
                                            itmPop.getParamsMap().put("OrgId", paramOrgId);
                                            itmPop.getParamsMap().put("HoOrgId", paramHoOrgId);

                                            System.out.println("--" + sourceDocNo.getValue());
                                            itmPop.getParamsMap().put("sourceDocNo", sourceDocNo.getValue());
                                            itmPop.execute();

                                            if (itmPop.getErrors().isEmpty()) {
                                                if (sourceDocType != null && sourceDocNo != null) {
                                                    if (Integer.parseInt(sourceDocType.getValue().toString()) ==
                                                        SOURCE_DOC_TYPE_IMPORT_PO) {
                                                        //    _log.info("--Populate from Po call--");



                                                        /** po insertion */
                                                        OperationBinding checkAvlItm =
                                                            getBindings().getOperationBinding("checkItemAvlblOrNotPo");
                                                        checkAvlItm.execute();


                                                        _log.info("any stockable items available or not " +
                                                                  checkAvlItm.getResult());

                                                        Integer avlbl = (Integer) checkAvlItm.getResult();
                                                        if (avlbl.compareTo(new Integer(0)) == 1) {
                                                            OperationBinding pendqty =
                                                                getBindings().getOperationBinding("poItmQtyGrtrZero");
                                                            pendqty.execute();
                                                            Integer pendChk = (Integer) pendqty.getResult();

                                                            if (pendChk.compareTo(new Integer(0)) == 1) {
                                                                OperationBinding popGeItmPo =
                                                                    getBindings().getOperationBinding("populateGeItmfromPo");
                                                                popGeItmPo.execute();
                                                                Integer noOfRows = 0;
                                                                if (popGeItmPo.getErrors().size() == 0)
                                                                    noOfRows = (Integer) popGeItmPo.getResult();
                                                                else
                                                                    System.out.println("Error while populateGeItmfromPo is == " +
                                                                                       popGeItmPo.getErrors());
                                                                if (noOfRows == 0) {
                                                                    String item_msg =
                                                                        resolvElDCMsg("#{bundle['MSG.661']}").toString();
                                                                    FacesContext.getCurrentInstance().addMessage(null,
                                                                                                                 new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                                                                                                  item_msg,
                                                                                                                                  null)); //"This PO have no item for this Schedule No."
                                                                } else {
                                                                    sourceDocNo.setValue("");
                                                                    sourceDocDt.resetValue();
                                                                    sourceDocDt.setValue(null);
                                                                    schdlNo.resetValue();
                                                                    schdlNo.setValue(null);
                                                                    // add for currency first


                                                                    _log.info(" inside get select row count   ");

                                                                    OperationBinding rowCountBind =
                                                                        getBindings().getOperationBinding("getGESrcCount");
                                                                    rowCountBind.execute();
                                                                    if (rowCountBind.getResult() != null) {
                                                                        _log.info(" inside get select row count value    ::::::  " +
                                                                                  rowCountBind.getResult());
                                                                        Integer count =
                                                                            Integer.parseInt(rowCountBind.getResult().toString());
                                                                        if (count.compareTo(new Integer(1)) ==
                                                                            0) {
                                                                            /**  ---------------------- set currency id in case of purchase order ------------------------     */
                                                                            OperationBinding opcurr =
                                             getBindings().getOperationBinding("setCurrIdSpPoToMtlGe");
                                                                            opcurr.getParamsMap().put("value",
                                                                                                      SOURCE_DOC_TYPE_IMPORT_PO);
                                                                            opcurr.execute();
                                                                        }

                                                                    }


                                                                }

                                                                OperationBinding exec =
                                                                    getBindings().getOperationBinding("Execute1");
                                                                exec.execute();
                                                                OperationBinding execPar =
                                                                    getBindings().getOperationBinding("Execute");
                                                                execPar.execute();

                                                                AdfFacesContext.getCurrentInstance().addPartialTarget(sourceDocNo);
                                                                AdfFacesContext.getCurrentInstance().addPartialTarget(sourceDocDt);
                                                                AdfFacesContext.getCurrentInstance().addPartialTarget(schdlNo);
                                                                AdfFacesContext.getCurrentInstance().addPartialTarget(geTreeTable);
                                                            } else {
                                                                showFacesMessage("All quantity already receive for selected document ",
                                                                                 "E", false, "F");
                                                            }
                                                        } else {
                                                            showFacesMessage("No stockable item available in the selected document ",
                                                                             "E", false, "F");
                                                        }


                                                    }
                                                }
                                                //  showPopup(itemPopUp, true);
                                            }
                                        } else {
                                            //Purchase Order selected is not configured to be received in the selected Warehouse.
                                            showFacesMessage(resolvElDCMsg("#{bundle['MSG.662']}").toString(), "E",
                                                             false, "F");
                                        }
                                    } else {
                                        showFacesMessage(resolvElDCMsg("#{bundle['MSG.663']}").toString(), "E", false,
                                                         "F");
                                        //Purchase Orders with same currency can be added in a single Gate Entry.Purchase Order selected is not currency compatible.
                                    }
                                }

                                else if (ret.equals("-2")) {
                                    showFacesMessage("Cannot accept Document as it is before or after tolerance days",
                                                     "E", false, "F");

                                } else if (ret.equals("-1")) {
                                    showFacesMessage(resolvElDCMsg("#{bundle['MSG.662']}").toString(), "E", false, "F");
                                    //showFacesMessage("MSG.734", "E", true, "F");
                                }
                            }
                        } else {
                            //Please select the Document No.
                            showFacesMessage(resolvElDCMsg("#{bundle['MSG.664']}").toString(), "E", false, "F");
                        }
                    } else {

                        showFacesMessage(resolvElDCMsg("#{bundle['MSG.660']}").toString(), "E", false,
                                         "F"); //Please Select Supplier.
                    }

                    /**
                     * IF Gate Entry Source Type is Tranfer Order
                     * Then check all Validation and value
                     */


                } else if (Integer.parseInt(sourceDocType.getValue().toString()) == SOURCE_DOC_TYPE_JITR) {
                    //check For Dups
                    _log.info("INside JITR >>>>>>>>>>>>>>>>>>>>");
                    if (supplierBind.getValue() != null && supplierBind.getValue().toString().trim().length() > 0) {
                        if (sourceDocNo.getValue() != null && sourceDocNo.getValue().toString().trim().length() > 0) {


                            OperationBinding chkSuppValid =
                                getBindings().getOperationBinding("chkSupplierInvcCopyReceipt");
                            chkSuppValid.execute();
                            _log.info(" value return  " + chkSuppValid.getResult());
                            if (chkSuppValid.getResult() != null &&
                                ((Integer) chkSuppValid.getResult()).compareTo(new Integer(1)) == 0) {
                                _log.info(" value true ");
                            } else {
                                showFacesMessage("Supplier document not received .Can not proceed.", "W", false, "F");
                                return;
                            }
                            _log.info("Inside JITR ::::: ");


                            OperationBinding dupChk = getBindings().getOperationBinding("checkForDuplicateDocNo");
                            dupChk.getParamsMap().put("CldId", paramCldId);
                            dupChk.getParamsMap().put("SlocId", paramSlocId);
                            dupChk.getParamsMap().put("OrgId", paramOrgId);
                            dupChk.getParamsMap().put("SrcDocNo", sourceDocNo.getValue());
                            dupChk.execute();


                            if ("Y".equals(dupChk.getResult().toString())) {
                                // String msg = resolvEl("#{bundle['MSG.375']}");
                                showFacesMessage("MSG.375", "E", true, "F");

                            } else {


                                OperationBinding insGeSrItm =
                                    getBindings().getOperationBinding("populateGeSrcItmToJITR");
                                insGeSrItm.execute();
                                if (insGeSrItm.getResult() != null &&
                                    ((Integer) insGeSrItm.getResult()).compareTo(new Integer(1)) == 0) {
                                    sourceDocNo.setValue("");
                                    sourceDocDt.resetValue();
                                    sourceDocDt.setValue(null);
                                    schdlNo.resetValue();
                                    schdlNo.setValue(null);
                                    // add for currency first
                                    _log.info(" inside get select row count   ");
                                    OperationBinding rowCountBind = getBindings().getOperationBinding("getGESrcCount");
                                    rowCountBind.execute();
                                    if (rowCountBind.getResult() != null) {
                                        _log.info(" inside get select row count value    ::::::  " +
                                                  rowCountBind.getResult());
                                        Integer count = Integer.parseInt(rowCountBind.getResult().toString());
                                        if (count.compareTo(new Integer(1)) ==
                                            0) {
                                            /**  ---------------------- set currency id in case of purchase order ------------------------     */
                                            OperationBinding opcurr = getBindings().getOperationBinding("setCurrIdSpPoToMtlGe");
                                            opcurr.getParamsMap().put("value", SOURCE_DOC_TYPE_JITR);
                                            opcurr.execute();
                                        }
                                    }
                                    
                                }else{
                                    showFacesMessage("Something wrong", "E", false, "F");
                                    return ;
                                }
                            }
                        } else {
                            //Please select the Document No.
                            showFacesMessage(resolvElDCMsg("#{bundle['MSG.664']}").toString(), "E", false, "F");
                        }
                    } else {

                        showFacesMessage(resolvElDCMsg("#{bundle['MSG.660']}").toString(), "E", false,
                                         "F"); //Please Select Supplier.
                    }

                    /**
                                    * IF Gate Entry Source Type is Tranfer Order
                                    * Then check all Validation and value
                                    */


                }


                else if (Integer.parseInt(sourceDocType.getValue().toString()) == SOURCE_DOC_TYPE_TRFO) {
                    _log.info("--Check OrgSrc or WhSrc is not null--");
                    //in case of other warehouse
                    String flg = "N";
                    if (rcptSrcTypeBinding.getValue().toString().equals("263"))
                        if (sourceOrgBinding.getValue() != null && sourceOrgBinding.getValue().toString().length() > 0)
                            if (whIdSrcBinding.getValue() != null && whIdSrcBinding.getValue().toString().length() > 0)
                                flg = "Y";
                            else
                                showFacesMessage("Source Warehouse Required.", "E", false, "F");
                        else
                            showFacesMessage("Source Organisation Required.", "E", false, "F");
                    else if (rcptSrcTypeBinding.getValue().toString().equals("262"))
                        if (whIdSrcBinding.getValue() != null && whIdSrcBinding.getValue().toString().length() > 0)
                            flg = "Y";
                        else
                            showFacesMessage("Source Warehouse Required.", "E", false, "F");

                    if (flg.equals("Y")) {
                        if (sourceDocNo.getValue() != null && sourceDocNo.getValue().toString().trim().length() > 0) {
                            OperationBinding dupChk = getBindings().getOperationBinding("checkForDuplicateDocNo");
                            dupChk.getParamsMap().put("CldId", paramCldId);
                            dupChk.getParamsMap().put("SlocId", paramSlocId);
                            dupChk.getParamsMap().put("OrgId", paramOrgId);
                            dupChk.getParamsMap().put("SrcDocNo", sourceDocNo.getValue());
                            dupChk.execute();

                            if ("Y".equals(dupChk.getResult().toString())) {
                                // String msg = resolvEl("#{bundle['MSG.375']}");
                                showFacesMessage("MSG.375", "E", true, "F");

                            } else {
                                if (sourceDocType != null && sourceDocNo != null) {
                                    if (Integer.parseInt(sourceDocType.getValue().toString()) == SOURCE_DOC_TYPE_TRFO) {

                                        //    _log.info("--Populate from Po call--")
                                        OperationBinding linkitm =
                                            getBindings().getOperationBinding("LinkItemtoOrganisation");
                                        linkitm.getParamsMap().put("CldId", paramCldId);
                                        linkitm.getParamsMap().put("SlocId", paramSlocId);
                                        linkitm.getParamsMap().put("OrgId", paramOrgId);
                                        linkitm.getParamsMap().put("HoOrgId", paramHoOrgId);
                                        linkitm.getParamsMap().put("UsrId", paramUsrId);
                                        linkitm.execute();
                                        OperationBinding chkItmExist =
                                            getBindings().getOperationBinding("chkItmTrfQty");
                                        chkItmExist.execute();
                                        if (chkItmExist.getErrors().size() == 0 && chkItmExist.getResult() != null) {
                                            if (chkItmExist.getResult().toString().equals("Y")) {

                                                OperationBinding popGeCurrCurr =
                                                    getBindings().getOperationBinding("chkCurrencyConversion");
                                                popGeCurrCurr.execute();

                                                if (popGeCurrCurr.getResult() != null &&
                                                    ((Integer) popGeCurrCurr.getResult()).compareTo(new Integer(0)) ==
                                                    0) {
                                                    showFacesMessage("Currency conversion rate not define between both organization. Can not add document.",
                                                                     "E", false, "F");
                                                    return;
                                                }

                                                OperationBinding popGeItmPo =
                                                    getBindings().getOperationBinding("populateGeItmfromTrfOrd");
                                                popGeItmPo.execute();
                                                OperationBinding exec = getBindings().getOperationBinding("Execute1");
                                                exec.execute();
                                                OperationBinding execPar = getBindings().getOperationBinding("Execute");
                                                execPar.execute();

                                                sourceDocNo.setValue("");
                                                sourceDocDt.resetValue();
                                                sourceDocDt.setValue(null);
                                                schdlNo.setValue(null);
                                                AdfFacesContext.getCurrentInstance().addPartialTarget(sourceDocNo);
                                                AdfFacesContext.getCurrentInstance().addPartialTarget(sourceDocDt);
                                                AdfFacesContext.getCurrentInstance().addPartialTarget(schdlNo);
                                                AdfFacesContext.getCurrentInstance().addPartialTarget(geTreeTable);
                                            } else {
                                                showFacesMessage("No Pending Item in this Transfer Order which have been Issued.",
                                                                 "E", false, "F");
                                            }
                                        } else {
                                            showFacesMessage("Something went Wrong. Please Contact to ESS!", "E", false,
                                                             "F");
                                        }
                                    }
                                }
                            }
                        }

                        else {
                            //Please select the Document No.
                            showFacesMessage(resolvElDCMsg("#{bundle['MSG.664']}").toString(), "E", false, "F");
                        }
                    }
                } else if (Integer.parseInt(sourceDocType.getValue().toString()) == SOURCE_DOC_TYPE_CPO) {
                    /**----------------------  In Case of CPO populate all items ----------------------------- */



                    if (sourceDocNo.getValue() != null && sourceDocNo.getValue().toString().trim().length() > 0) {

                        OperationBinding chkCurr = getBindings().getOperationBinding("checkCurrCompatibilityCPO");
                        chkCurr.getParamsMap().put("OrgId", paramOrgId);
                        chkCurr.getParamsMap().put("CldId", paramCldId);
                        chkCurr.getParamsMap().put("SlocId", paramSlocId);
                        chkCurr.getParamsMap().put("PoDocId", sourceDocNo.getValue().toString());
                        chkCurr.execute();

                        if ("N".equalsIgnoreCase(chkCurr.getResult().toString())) {
                            showFacesMessage("Cash Purchase Orders with same currency can be added in a single Gate Entry.Cash Purchase Order selected is not currency compatible.",
                                             "E", false, "F");
                            //   Purchase Orders with same currency can be added in a single Gate Entry.Purchase Order selected is not currency compatible.
                            //check cpo not valid
                            return;

                        }

                        OperationBinding dupChk = getBindings().getOperationBinding("checkForDuplicateDocNo");
                        dupChk.getParamsMap().put("CldId", paramCldId);
                        dupChk.getParamsMap().put("SlocId", paramSlocId);
                        dupChk.getParamsMap().put("OrgId", paramOrgId);
                        dupChk.getParamsMap().put("SrcDocNo", sourceDocNo.getValue());
                        dupChk.execute();

                        if ("Y".equals(dupChk.getResult().toString())) {
                            // String msg = resolvEl("#{bundle['MSG.375']}");
                            showFacesMessage("MSG.375", "E", true, "F");

                        } else {


                            OperationBinding chkWh = getBindings().getOperationBinding("isPoWarehouseCompatible");
                            chkWh.execute();
                            _log.info("Currency OK");
                            if ("Y".equalsIgnoreCase(chkWh.getResult().toString())) {

                                _log.info("Warehouse OK");


                                if (sourceDocType != null && sourceDocNo != null) {

                                    if (Integer.parseInt(sourceDocType.getValue().toString()) ==
                                        SOURCE_DOC_TYPE_CPO) {
                                        //    _log.info("--Populate from Po call--"); populateGeItmfromPo

                                        //                                        OperationBinding popGeItmCPo =
                                        //                                                                                      getBindings().getOperationBinding("populateGeItmfromCPo");
                                        //                                        popGeItmCPo.execute();
                                        OperationBinding popGeItmCPo =
                                                                                      getBindings().getOperationBinding("populateGeItmfromPo");
                                        popGeItmCPo.execute();

                                        Integer noOfRows = 0;
                                        if (popGeItmCPo.getErrors().size() == 0)
                                            noOfRows = (Integer) popGeItmCPo.getResult();
                                        else
                                            System.out.println("Error while populateGeItmfromCPo is == " +
                                                               popGeItmCPo.getErrors());
                                        if (noOfRows == 0) {
                                            String item_msg = "This CPO have no Item.";
                                            FacesContext.getCurrentInstance().addMessage(null,
                                                                                         new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                                                                          item_msg,
                                                                                                          null)); //"This PO have no item for this Schedule No."
                                        } else {
                                            sourceDocNo.setValue("");
                                            sourceDocDt.resetValue();
                                            sourceDocDt.setValue(null);
                                            schdlNo.resetValue();
                                            schdlNo.setValue(null);


                                            _log.info(" inside get select row count   ");

                                            OperationBinding rowCountBind =
                                                getBindings().getOperationBinding("getGESrcCount");
                                            rowCountBind.execute();
                                            if (rowCountBind.getResult() != null) {
                                                _log.info(" inside get select row count value    ::::::  " +
                                                          rowCountBind.getResult());
                                                Integer count = Integer.parseInt(rowCountBind.getResult().toString());
                                                if (count.compareTo(new Integer(1)) ==
                                                    0) {
                                                    /**  ---------------------- set currency id in case of Cash purchase order ------------------------     */
                                                    OperationBinding opcurr =
                     getBindings().getOperationBinding("setCurrIdSpPoToMtlGe");
                                                    opcurr.getParamsMap().put("value", SOURCE_DOC_TYPE_CPO);
                                                    opcurr.execute();
                                                }

                                            }

                                        }

                                        OperationBinding exec = getBindings().getOperationBinding("Execute1");
                                        exec.execute();
                                        OperationBinding execPar = getBindings().getOperationBinding("Execute");
                                        execPar.execute();


                                        AdfFacesContext.getCurrentInstance().addPartialTarget(sourceDocNo);
                                        AdfFacesContext.getCurrentInstance().addPartialTarget(sourceDocDt);
                                        AdfFacesContext.getCurrentInstance().addPartialTarget(schdlNo);
                                        AdfFacesContext.getCurrentInstance().addPartialTarget(geTreeTable);

                                    }
                                }

                            } else {

                                //Purchase Order selected is not configured to be received in the selected Warehouse.
                                showFacesMessage(resolvElDCMsg("#{bundle['MSG.662']}").toString(), "E", false, "F");

                            }

                        }
                    } else {
                        //Please select the Document No.
                        showFacesMessage(resolvElDCMsg("#{bundle['MSG.664']}").toString(), "E", false, "F");
                    }


                    //                    } else {
                    //                        showFacesMessage("Cash Purchase Orders with same currency can be added in a single Gate Entry.Cash Purchase Order selected is not currency compatible.",
                    //                                         "E", false, "F");
                    //                        //   Purchase Orders with same currency can be added in a single Gate Entry.Purchase Order selected is not currency compatible.
                    //                        //check cpo not valid
                    //                    }

                }
            } else {
                showFacesMessage(resolvElDCMsg("#{bundle['MSG.665']}").toString(), "E", false,
                                 "F"); //Please Select the Document Type
                _log.info("--Doc no not present--");
            }
        } else {
            showFacesMessage(resolvElDCMsg("#{bundle['MSG.665']}").toString(), "E", false,
                             "F"); //"Please Select the Document Type
            _log.info("--Doc no not present--");
        }
    }

    public String editGeAction() {
        // Add event code here...
        if (((Integer) geStatus.getValue()) == 298) {

            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                                          resolvElDCMsg("#{bundle['MSG.666']}").toString(),
                                                                          null)); //The Materials are transfered to store. Cannot Edit.

        } else if (((Integer) geStatus.getValue()) == 453) {
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                                          "Material Received at Store, Cannot Edit.",
                                                                          null)); //Material Received at Store, Cannot Edit.
        } else if (((Integer) geStatus.getValue()) == 880) {

            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                                          "Gare Entry already canecled. Cannot Edit.",
                                                                          null)); //The Materials are transfered to store. Cannot Edit.

        }

        else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.getPageFlowScope().put("ADD_EDIT_MODE", "E");
            enableHeader = true;
            AdfFacesContext.getCurrentInstance().addPartialTarget(geWhId);
        }

        return null;
    }

    public String saveGeAction() {
        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer paramUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String paramHoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        Integer fyNo = 0;
        if (geDt != null) {

            OperationBinding chkDocPr = getBindings().getOperationBinding("isDocumentPresent");
            chkDocPr.execute();

            if ("Y".equalsIgnoreCase(chkDocPr.getResult().toString())) {
                HashSet h = null;
                HashSet dh = null;
                /* OperationBinding chkDlv = getBindings().getOperationBinding("chkdlvryQtyForItem");
           chkDlv.getParamsMap().put("CldId", paramCldId);
           chkDlv.getParamsMap().put("slocId", paramSlocId);
           chkDlv.getParamsMap().put("OrgId", paramOrgId);
           chkDlv.getParamsMap().put("whId", geWhId.getValue().toString());
           chkDlv.execute();
           dh=(HashSet)chkDlv.getResult();  */
                if (dh != null && !dh.isEmpty()) {
                    _log.info("HashSet not empty" + dh);

                    String msg =
                        "<html><body><p>Some items do not have Delivery quantity for the following documents : <b>" +
                        dh + "</b> </p></body></html>";
                    showFacesMessage(msg, "E", false, "F");
                } else {
                    if (returnFlg.getValue().toString().equals("true")) {
                    } else {
                        OperationBinding chkRcvd = getBindings().getOperationBinding("checkForRcvdQtyPresent");
                        chkRcvd.getParamsMap().put("CldId", paramCldId);
                        chkRcvd.getParamsMap().put("slocId", paramSlocId);
                        chkRcvd.getParamsMap().put("OrgId", paramOrgId);
                        chkRcvd.getParamsMap().put("whId", geWhId.getValue().toString());
                        chkRcvd.execute();
                        h = (HashSet) chkRcvd.getResult();
                    }
                    if (h != null && !h.isEmpty()) {

                        _log.info("HashSet not empty" + h);

                        StringBuilder msg =
                            new StringBuilder("<html><body><p>Some items do not have received quantity for the following documents : <b>" +
                                              h + "</b> </p>"); //MSG.705
                        msg.append("<ul> <li>Click YES to delete.</li>"); //MSG.706
                        msg.append("<li>Click NO to continue.</li></ul>"); //MSG.707
                        msg.append("</body></html>");


                        msgpop = msg.toString();
                        /*    FacesMessage fm=new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg);
          FacesContext.getCurrentInstance().addMessage(null, fm); */
                        showPopup(confDlvQtyChkPopUp, true);
                        _log.info("After msg popup");


                    } else {

                        OperationBinding chkDocPrt = getBindings().getOperationBinding("isDocumentPresent");
                        chkDocPrt.execute();
                        if ("Y".equalsIgnoreCase(chkDocPr.getResult().toString())) {

                            if (geNo.getValue() == null) { //Case : GE No. not generated
                                OperationBinding geFy = getBindings().getOperationBinding("getFYid");
                                geFy.getParamsMap().put("CldId", paramCldId);
                                geFy.getParamsMap().put("OrgId", paramOrgId);
                                geFy.getParamsMap().put("geDate", (Timestamp) geDt.getValue());
                                geFy.getParamsMap().put("Mode", "V");
                                geFy.execute();

                                Object fy = geFy.getResult();
                                if (Integer.parseInt(fy.toString()) > 0) {
                                    fyNo = Integer.parseInt(fy.toString());
                                }
                                OperationBinding geNoOp = getBindings().getOperationBinding("getGeNo");
                                geNoOp.getParamsMap().put("SlocId", paramSlocId);
                                geNoOp.getParamsMap().put("CldId", paramCldId);
                                geNoOp.getParamsMap().put("OrgId", paramOrgId);
                                geNoOp.getParamsMap().put("WhId", geWhId.getValue().toString());
                                geNoOp.getParamsMap().put("fyId", fyNo);
                                geNoOp.execute();
                                Object geno = geNoOp.getResult();
                                _log.info("--GeNo--" + geno);
                            }
                            AdfFacesContext.getCurrentInstance().addPartialTarget(geNo);
                            System.out.println("retttt" + returnFlg.getValue().toString().equals("N"));
                            if (returnFlg.getValue().toString().equals("false")) {

                                OperationBinding chkQtyToRet = getBindings().getOperationBinding("checkRetQty");
                                chkQtyToRet.execute();
                                String chkqty = "N";
                                if (chkQtyToRet.getResult() != null)
                                    chkqty = (String) chkQtyToRet.getResult();
                                if (chkqty.equals("Y")) {
                                    // in case of process order
                                    if ((sourceDocType.getValue().toString().equals(SOURCE_DOC_TYPE_PROCS_ORD.toString()))) {
                                        OperationBinding setGeStatus =
                                            getBindings().getOperationBinding("setStatusforGe");
                                        setGeStatus.getParamsMap().put("stat", "N");
                                        setGeStatus.execute();
                                        OperationBinding exec1 = getBindings().getOperationBinding("Commit");
                                        exec1.execute();

                                    } else {
                                        showPopup(chkStatusPopUp, true);
                                    }
                                } else {
                                    OperationBinding setStat = getBindings().getOperationBinding("setStatusforGe");
                                    setStat.getParamsMap().put("stat", "R");
                                    setStat.execute();

                                }
                                _log.info("AFTER SET STATUS");
                            } else {
                                OperationBinding retOp = getBindings().getOperationBinding("returnGe");
                                retOp.getParamsMap().put("Flg", "Y");
                                retOp.execute();

                                OperationBinding exeItm = getBindings().getOperationBinding("Execute");
                                exeItm.execute();
                                OperationBinding exeSrc = getBindings().getOperationBinding("Execute1");
                                exeSrc.execute();
                                AdfFacesContext.getCurrentInstance().addPartialTarget(geTreeTable);

                                OperationBinding setStat = getBindings().getOperationBinding("setStatusforGe");
                                setStat.getParamsMap().put("stat", "R");
                                setStat.execute();
                            }
                            /* if(Integer.parseInt(sourceDocType.getValue().toString())==SOURCE_DOC_TYPE_PO || Integer.parseInt(sourceDocType.getValue().toString())==SOURCE_DOC_TYPE_CPO){
                      Integer srcDocTypePo = Integer.parseInt(sourceDocType.getValue().toString());
                       OperationBinding opcurr=getBindings().getOperationBinding("setCurrIdSpPoToMtlGe");
                       opcurr.getParamsMap().put("value", srcDocTypePo);
                       opcurr.execute();
                       } */
                            OperationBinding geNoOp = getBindings().getOperationBinding("getGeNo");
                            geNoOp.getParamsMap().put("SlocId", paramSlocId);
                            geNoOp.getParamsMap().put("CldId", paramCldId);
                            geNoOp.getParamsMap().put("OrgId", paramOrgId);
                            geNoOp.getParamsMap().put("WhId", geWhId.getValue().toString());
                            geNoOp.getParamsMap().put("fyId", fyNo);
                            geNoOp.execute();
                            Object geno = geNoOp.getResult();
                            _log.info("--GeNo--" + geno);
                            OperationBinding exec = getBindings().getOperationBinding("Commit");
                            exec.execute();
                            if (exec.getErrors().isEmpty()) {
                                RequestContext context = RequestContext.getCurrentInstance();
                                context.getPageFlowScope().put("ADD_EDIT_MODE", "V");
                                enableHeader = false;
                            }

                        } else {
                            showFacesMessage(resolvElDCMsg("#{bundle['MSG.667']}").toString(), "E", false,
                                             "F"); //Documents have not been added for this Gate Entry.Cannot save.
                        }
                    }
                }
            } else {
                showFacesMessage(resolvElDCMsg("#{bundle['MSG.667']}").toString(), "E", false,
                                 "F"); //"Documents have not been added for this Gate Entry.Cannot save."
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          resolvElDCMsg("#{bundle['MSG.668']}").toString(),
                                                                          null)); //"Gate Entry Date is Required"
        }
        return null;
    }

    public void ItemPopUpDialogListener(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("ok")) {
            if (sourceDocType != null && sourceDocNo != null) {
                if (Integer.parseInt(sourceDocType.getValue().toString()) == SOURCE_DOC_TYPE_PO) {
                    _log.info("--Populate from Po call--");
                    OperationBinding popGeItmPo = getBindings().getOperationBinding("populateGeItmfromPo");
                    popGeItmPo.execute();
                    OperationBinding exec = getBindings().getOperationBinding("Execute1");
                    exec.execute();
                    OperationBinding execPar = getBindings().getOperationBinding("Execute");
                    execPar.execute();

                    sourceDocNo.setValue("");
                    sourceDocDt.resetValue();
                    schdlNo.setValue(null);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(sourceDocNo);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(sourceDocDt);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(schdlNo);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(geTreeTable);
                }
            }
        } else if (dialogEvent.getOutcome().name().equals("Cancel")) {
            _log.info("Cancel");
        }
    }

    public void setSourceDocType(RichSelectOneChoice sourceDocType) {
        this.sourceDocType = sourceDocType;
    }

    public RichSelectOneChoice getSourceDocType() {
        return sourceDocType;
    }

    public void setSourceDocNo(RichInputListOfValues sourceDocNo) {
        this.sourceDocNo = sourceDocNo;
    }

    public RichInputListOfValues getSourceDocNo() {
        return sourceDocNo;
    }

    public void setItemPopUp(RichPopup itemPopUp) {
        this.itemPopUp = itemPopUp;
    }

    public RichPopup getItemPopUp() {
        return itemPopUp;
    }

    public void itmPopUpCancelListener(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
    }

    public void setGeTreeTable(RichTreeTable geTreeTable) {
        this.geTreeTable = geTreeTable;
    }

    public RichTreeTable getGeTreeTable() {
        return geTreeTable;
    }

    public RowIterator getSelectedNodeRowIterator() {
        if (getGeTreeTable() != null && getGeTreeTable().getSelectedRowKeys() != null) {
            for (Object opaqueFacesKey : getGeTreeTable().getSelectedRowKeys()) {
                getGeTreeTable().setRowKey(opaqueFacesKey);
                return ((JUCtrlHierNodeBinding) getGeTreeTable().getRowData()).getRowIterator();
            }
        }
        return null;
    }

    public Key getSelectedNodeRowKey() {
        if (getGeTreeTable() != null && getGeTreeTable().getSelectedRowKeys() != null) {
            for (Object opaqueFacesKey : getGeTreeTable().getSelectedRowKeys()) {
                getGeTreeTable().setRowKey(opaqueFacesKey);
                return ((JUCtrlHierNodeBinding) getGeTreeTable().getRowData()).getRowKey();
            }
        }
        return null;
    }

    public boolean isSelectedNodeInRowIteratorLast() {
        RowIterator ri = getSelectedNodeRowIterator();
        if (ri != null) {
            Key selectedNodeRowKey = getSelectedNodeRowKey();
            if (selectedNodeRowKey != null) {
                return selectedNodeRowKey.equals(ri.last().getKey());
            }
        }
        return false;
    }

    public boolean isSelectedNodeInRowIteratorFirst() {
        RowIterator ri = getSelectedNodeRowIterator();
        if (ri != null) {
            Key selectedNodeRowKey = getSelectedNodeRowKey();
            if (selectedNodeRowKey != null) {
                return selectedNodeRowKey.equals(ri.first().getKey());
            }
        }
        return false;
    }

    public String getSelectedNodeViewDefFullName() {
        if (getGeTreeTable() != null && getGeTreeTable().getSelectedRowKeys() != null) {
            for (Object opaqueFacesKey : getGeTreeTable().getSelectedRowKeys()) {
                getGeTreeTable().setRowKey(opaqueFacesKey);
                return ((JUCtrlHierNodeBinding) getGeTreeTable().getRowData()).getViewObject().getDefFullName();
            }
        }
        return null;
    }


    public void setSourceDocDt(RichInputDate sourceDocDt) {
        this.sourceDocDt = sourceDocDt;
    }

    public RichInputDate getSourceDocDt() {
        return sourceDocDt;
    }


    public void setSchdlNo(RichInputText schdlNo) {
        this.schdlNo = schdlNo;
    }

    public RichInputText getSchdlNo() {
        return schdlNo;
    }

    public void deliveryNoteVce(ValueChangeEvent valueChangeEvent) {
        /*  Object c=valueChangeEvent.getNewValue();

        if(c!=null){
           RowIterator rw= getSelectedNodeRowIterator();
         //  _log.info("Row :: "+rw.getCurrentRow().getKey());
        Key nodeKey=this.getSelectedNodeRowKey();
        Row curr=rw.getRow(nodeKey);
        //   _log.info("Row ->"+nodeKey);

           curr.setAttribute("RcptQty", (Number)c);
           AdfFacesContext.getCurrentInstance().addPartialTarget(geTreeTable);
        }*/
    }

    public void setGeNo(RichInputText geNo) {
        this.geNo = geNo;
    }

    public RichInputText getGeNo() {
        return geNo;
    }

    public void setGeDt(RichInputDate geDt) {
        this.geDt = geDt;
    }

    public RichInputDate getGeDt() {
        return geDt;
    }

    public void setGeWhId(RichSelectOneChoice geWhId) {
        this.geWhId = geWhId;
    }

    public RichSelectOneChoice getGeWhId() {
        return geWhId;
    }

    private RowKeySet disclosedTreeRowKeySet = new RowKeySetImpl();

    private void expandTreeTable() {

        if (this.geTreeTable != null) {
            disclosedTreeRowKeySet = new RowKeySetImpl();
            CollectionModel model = (CollectionModel) geTreeTable.getValue();
            JUCtrlHierBinding treeBinding = (JUCtrlHierBinding) model.getWrappedData();
            JUCtrlHierNodeBinding rootNode = treeBinding.getRootNodeBinding();
            if (rootNode != null) {
                disclosedTreeRowKeySet = geTreeTable.getDisclosedRowKeys();
                if (disclosedTreeRowKeySet == null) {
                    disclosedTreeRowKeySet = new RowKeySetImpl();
                }
                List<JUCtrlHierNodeBinding> firstLevelChildren = rootNode.getChildren();

                for (JUCtrlHierNodeBinding node : firstLevelChildren) {
                    ArrayList list = new ArrayList();
                    list.add(node.getRowKey());
                    disclosedTreeRowKeySet.add(list);
                    expandTreeChildrenNode(geTreeTable, node, list);
                }

                geTreeTable.setDisclosedRowKeys(disclosedTreeRowKeySet);
            }
        }
    }

    private void expandTreeChildrenNode(RichTreeTable rt, JUCtrlHierNodeBinding node, List<Key> parentRowKey) {
        ArrayList children = node.getChildren();
        List<Key> rowKey;
        if (children != null) {
            for (int i = 0; i < children.size(); i++) {
                rowKey = new ArrayList<Key>();
                rowKey.addAll(parentRowKey);
                rowKey.add(((JUCtrlHierNodeBinding) children.get(i)).getRowKey());
                disclosedTreeRowKeySet.add(rowKey);
                if (((JUCtrlHierNodeBinding) (children.get(i))).getChildren() == null)
                    continue;
                expandTreeChildrenNode(rt, (JUCtrlHierNodeBinding) (node.getChildren().get(i)), rowKey);
            }
        }
    }

    public void expandAllNodeAction(ActionEvent actionEvent) {
        expandTreeTable();
        AdfFacesContext.getCurrentInstance().addPartialTarget(geTreeTable);
    }

    public void setEnableHeader(Boolean enableHeader) {
        this.enableHeader = enableHeader;
    }

    public Boolean getEnableHeader() {
        return enableHeader;
    }

    public String addGeAction() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.getPageFlowScope().put("ADD_EDIT_MODE", "A");
        enableHeader = true;
        return "createNew";
    }

    public void setSupplierBind(RichInputText supplierBind) {
        this.supplierBind = supplierBind;
    }

    public RichInputText getSupplierBind() {
        return supplierBind;
    }

    public void setItmNameBindVar(RichInputListOfValues itmNameBindVar) {
        this.itmNameBindVar = itmNameBindVar;
    }

    public RichInputListOfValues getItmNameBindVar() {
        return itmNameBindVar;
    }

    public void setUomBindVar(RichSelectOneChoice uomBindVar) {
        this.uomBindVar = uomBindVar;
    }

    public RichSelectOneChoice getUomBindVar() {
        return uomBindVar;
    }

    public void setItmQty(RichInputText itmQty) {
        this.itmQty = itmQty;
    }

    public RichInputText getItmQty() {
        return itmQty;
    }

    public void setChkStatusPopUp(RichPopup chkStatusPopUp) {
        this.chkStatusPopUp = chkStatusPopUp;
    }

    public RichPopup getChkStatusPopUp() {
        return chkStatusPopUp;
    }

    public void confirmStatusDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
            OperationBinding opC = getBindings().getOperationBinding("setStatusforGe");
            opC.getParamsMap().put("stat", "Y");
            opC.execute();

            OperationBinding comexec = getBindings().getOperationBinding("Commit");
            comexec.execute();
            OperationBinding exec = getBindings().getOperationBinding("Execute1");
            exec.execute();
            OperationBinding execPar = getBindings().getOperationBinding("Execute");
            execPar.execute();
        } else {
            OperationBinding opC = getBindings().getOperationBinding("setStatusforGe");
            opC.getParamsMap().put("stat", "N");
            opC.execute();

            OperationBinding comexec = getBindings().getOperationBinding("Commit");
            comexec.execute();
            OperationBinding exec = getBindings().getOperationBinding("Execute1");
            exec.execute();
            OperationBinding execPar = getBindings().getOperationBinding("Execute");
            execPar.execute();
        }
    }

    public void rcptSrcTypeVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            Integer vc = (Integer) valueChangeEvent.getNewValue();


            OperationBinding opC = getBindings().getOperationBinding("resetOrgWh");
            opC.getParamsMap().put("rcptType", vc);
            opC.execute();

            eoNameBinding.setValue(null);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(supCustNameBinding);
        AdfFacesContext.getCurrentInstance().addPartialTarget(sourceOrgBinding);
    }

    public void setGeStatus(RichSelectOneChoice geStatus) {
        this.geStatus = geStatus;
    }

    public RichSelectOneChoice getGeStatus() {
        return geStatus;
    }

    public void rcptQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number zero = new Number(0);
            _log.info("Received Quantity in VAlidaor--" + (Number) object);
            if (((Number) object).compareTo(zero) == -1) {
                showFacesMessage(resolvElDCMsg("#{bundle['MSG.767']}").toString(), "E", false,
                                 "V"); //"Received Quantity cannot be less than zero."
            } else {
                Boolean is = isPrecisionValid(26, 6, ((Number) object));
                if (is.toString().equalsIgnoreCase("true")) {
                    /*
                                           String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
                                           String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
                                           Integer paramSlocId= Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));


                                                  if(Integer.parseInt(sourceDocType.getValue().toString())==(SOURCE_DOC_TYPE_PO)){
                                                   OperationBinding opc=getBindings().getOperationBinding("chkToleranceQty");
                                                   opc.getParamsMap().put("CldId", paramCldId);
                                                   opc.getParamsMap().put("SlocId", paramSlocId);
                                                   opc.getParamsMap().put("OrgId", paramOrgId);
                                                   opc.execute();
                                                   if(opc.getErrors().size()==0 && opc.getResult().toString().equals("Y")){
                                                       OperationBinding op=getBindings().getOperationBinding("validateToleranceQty");
                                                       op.getParamsMap().put("CldId", paramCldId);
                                                       op.getParamsMap().put("SlocId", paramSlocId);
                                                       op.getParamsMap().put("OrgId", paramOrgId);
                                                       op.getParamsMap().put("p_itm_id", itmIdRcpt.getValue().toString());
                                                       op.getParamsMap().put("rcpt_qty", (Number)object);
                                                       op.execute();

                                                       String res=op.getResult().toString();
                                                       _log.info("Res="+res);
                                                       if(res.equals("-2")){
                                                        // showFacesMessage("Cannot accept more than tolerance quantity", "E", false, "V");
                                                        showFacesMessage("MSG.781", "E", true, "V");

                                                   }
                                               }  else  if(opc.getErrors().size()==0 && opc.getResult().toString().equals("N"))
                                         {
                                               OperationBinding opchkPoQty=getBindings().getOperationBinding("validatePoRcptQty");
                                               opchkPoQty.getParamsMap().put("rcptQty", object);
                                               opchkPoQty.execute();
                                                   if(opchkPoQty.getResult()!=null && opchkPoQty.getResult().toString().equals("Y"))
                                                  {
                                                 _log.info("Quantity valid for PO");
                                                        }
                                                    else
                                                      {
                                                       showFacesMessage("Cannot accept more than Pending quantity", "E", false, "V");
                                                           }
                                                   }
                                                   else
                                                       _log.info("Error="+opc.getErrors());
                                             }

                                */


                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision(26,6).", null));
                }
            }
        }
    }

    public void deleteDocumentAction(ActionEvent actionEvent) {
        OperationBinding chkOp = getBindings().getOperationBinding("checkNoOfDocument");
        chkOp.execute();
        String chk = "N";
        if (chkOp.getResult() != null)
            chk = (String) chkOp.getResult();
        if (chk.equals("Y")) {
            showPopup(deleteGePopUp, true);
        } else {
            OperationBinding delOp = getBindings().getOperationBinding("deleteDocument");
            delOp.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(geTreeTable);
        }

    }

    public void setConfDlvQtyChkPopUp(RichPopup confDlvQtyChkPopUp) {
        this.confDlvQtyChkPopUp = confDlvQtyChkPopUp;
    }

    public RichPopup getConfDlvQtyChkPopUp() {
        return confDlvQtyChkPopUp;
    }

    public void setMsgpop(String msgpop) {
        this.msgpop = msgpop;
    }

    public String getMsgpop() {
        return msgpop;
    }

    public void deleteConfDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {

            String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            Integer paramUsrId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
            String paramHoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));

            OperationBinding delRwOp = getBindings().getOperationBinding("deleteItemsNonRecvd");
            delRwOp.getParamsMap().put("CldId", paramCldId);
            delRwOp.getParamsMap().put("slocId", paramSlocId);
            delRwOp.getParamsMap().put("OrgId", paramOrgId);
            delRwOp.getParamsMap().put("whId", geWhId.getValue().toString());
            delRwOp.execute();

            AdfFacesContext.getCurrentInstance().addPartialTarget(geWhId);
            AdfFacesContext.getCurrentInstance().addPartialTarget(geTreeTable);
            AdfFacesContext.getCurrentInstance().addPartialTarget(geNodeSrcColumnVar);


        } else if (dialogEvent.getOutcome().name().equals("No")) {
            _log.info("--NO--");
        }
    }

    public String statusYesAction() {
        // Add event code here...confDlvQtyChkPopUp
        chkStatusPopUp.hide();

        return "onyes";
    }

    public String statusNoAction() {
        // Add event code here...
        chkStatusPopUp.hide();

        return "onNo";
    }

    public void returnFlgVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue().equals(true)) {
            _log.info("Y");
            OperationBinding retOp = getBindings().getOperationBinding("returnGe");
            retOp.getParamsMap().put("Flg", "Y");
            retOp.execute();

            OperationBinding exeItm = getBindings().getOperationBinding("Execute");
            exeItm.execute();
            OperationBinding exeSrc = getBindings().getOperationBinding("Execute1");
            exeSrc.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(geTreeTable);

        } else {
            _log.info("Y");
            OperationBinding retOp = getBindings().getOperationBinding("returnGe");
            retOp.getParamsMap().put("Flg", "N");
            retOp.execute();

            OperationBinding exeItm = getBindings().getOperationBinding("Execute");
            exeItm.execute();
            OperationBinding exeSrc = getBindings().getOperationBinding("Execute1");
            exeSrc.execute();

            returnReason.setValue("");

            AdfFacesContext.getCurrentInstance().addPartialTarget(returnReason);
            AdfFacesContext.getCurrentInstance().addPartialTarget(geTreeTable);
        }
    }

    public void setReturnFlg(RichSelectBooleanCheckbox returnFlg) {
        this.returnFlg = returnFlg;
    }

    public RichSelectBooleanCheckbox getReturnFlg() {
        return returnFlg;
    }

    public void dlvQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number zero = new Number(0);
            //  _log.info("Delivery Quantity in VAlidaor--"+(Number)object);
            if (((Number) object).compareTo(zero) == -1) {
                showFacesMessage(resolvElDCMsg("#{bundle['MSG.767']}").toString(), "E", false,
                                 "V"); //Delivery Quantity cannot be less than zero.
                //  showFacesMessage("Delivery Note Quantity must be Greater Than Zero.", "E", false, "V");
            } else {
                Boolean is = isPrecisionValid(26, 6, ((Number) object));
                if (is.toString().equalsIgnoreCase("true")) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision(26,6).", null));
                }
            }
        }
    }

    public void returnQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        totRcptQtyBinding.processUpdates(FacesContext.getCurrentInstance());
        if (object != null) {
            Number zero = new Number(0);
            //  _log.info("Return Quantity in VAlidaor--"+(Number)object);
            if (((Number) object).compareTo(zero) == -1) {
                showFacesMessage(resolvElDCMsg("#{bundle['MSG.767']}").toString(), "E", false, "V");
            } else {
                Boolean is = isPrecisionValid(26, 6, ((Number) object));
                if (is.toString().equalsIgnoreCase("true")) {
                    //Compare to TotRcptQty
                    OperationBinding opChk = getBindings().getOperationBinding("compTotRcptQty");
                    opChk.getParamsMap().put("RetQty", ((Number) object));
                    opChk.execute();
                    if (opChk.getResult() != null && opChk.getResult().toString().equals("Y")) {
                        //Check Tolerance against RcptQty or (TotRcptQty-RetnQty)

                        String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
                        String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
                        Integer paramSlocId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));


                        if (Integer.parseInt(sourceDocType.getValue().toString()) == (SOURCE_DOC_TYPE_PO)) {
                            OperationBinding opc = getBindings().getOperationBinding("chkToleranceQty");
                            opc.getParamsMap().put("CldId", paramCldId);
                            opc.getParamsMap().put("SlocId", paramSlocId);
                            opc.getParamsMap().put("OrgId", paramOrgId);
                            opc.execute();
                            if (opc.getErrors().size() == 0 && opc.getResult().toString().equals("Y")) {
                                OperationBinding op = getBindings().getOperationBinding("callForValidateToleranceQty");
                                op.getParamsMap().put("CldId", paramCldId);
                                op.getParamsMap().put("SlocId", paramSlocId);
                                op.getParamsMap().put("OrgId", paramOrgId);
                                op.getParamsMap().put("p_itm_id", itmIdRcpt.getValue().toString());
                                op.getParamsMap().put("retn_qty", (Number) object);
                                op.execute();

                                String res = op.getResult().toString();
                                _log.info("Res=" + res);
                                if (res.equals("-2")) {
                                    // showFacesMessage("Cannot accept more than tolerance quantity", "E", false, "V");
                                    showFacesMessage("MSG.781", "E", true, "V");

                                }
                            } else if (opc.getErrors().size() == 0 && opc.getResult().toString().equals("N")) {
                                OperationBinding opchkPoQty = getBindings().getOperationBinding("validatePoRcptQty");
                                opchkPoQty.getParamsMap().put("rcptQty", object);
                                opchkPoQty.execute();
                                if (opchkPoQty.getResult() != null && opchkPoQty.getResult().toString().equals("Y")) {
                                    //     _log.info("Quantity valid for PO");






















































                                } else {
                                    showFacesMessage("Cannot accept more than Pending quantity", "E", false, "V");
                                }
                            } else
                                _log.info("Error=" + opc.getErrors());
                        }


                    } else {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                      "Return Quantity can not be Greater than Total Receipt Quantity.",
                                                                      null));
                    }
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision(26,6).", null));
                }
            }
        }
    }

    public void setReturnReason(RichInputText returnReason) {
        this.returnReason = returnReason;
    }

    public RichInputText getReturnReason() {
        return returnReason;
    }

    public void invcDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");

            Timestamp ts = (Timestamp) object;
            OperationBinding tsOp = getBindings().getOperationBinding("isFYOpenForCurrentDate");
            tsOp.getParamsMap().put("cldId", paramCldId);
            tsOp.getParamsMap().put("orgId", paramOrgId);
            tsOp.getParamsMap().put("dt", ts);
            tsOp.execute();

            if ("false".equalsIgnoreCase(tsOp.getResult().toString())) {
                showFacesMessage(resolvElDCMsg("#{bundle['MSG.671']}").toString(), "E", false, "V");
                //Invalid Invoice Date. Invoice Date must be present in an open financial year.
            }

            if (geDt.getValue() != null) {
                _log.info(ts + "    test  " + geDt.getValue());
                if (ts.toString().compareTo(geDt.getValue().toString()) == 1) {
                    showFacesMessage("Date must be before or on " + geDt.getValue(), "E", false, "V");
                }
            }

        }
    }

    public void deliveryDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");

            Timestamp ts = (Timestamp) object;
            OperationBinding tsOp = getBindings().getOperationBinding("isFYOpenForCurrentDate");
            tsOp.getParamsMap().put("cldId", paramCldId);
            tsOp.getParamsMap().put("orgId", paramOrgId);
            tsOp.getParamsMap().put("dt", ts);
            tsOp.execute();

            if ("false".equalsIgnoreCase(tsOp.getResult().toString())) {
                showFacesMessage(resolvElDCMsg("#{bundle['MSG.6772']}").toString(), "E", false, "V");
                //Invalid Delivery Date. Delivery Date must be present in an open financial year.
            }
            if (geDt.getValue() != null) {
                _log.info(ts + "    test  " + geDt.getValue());
                if (ts.toString().compareTo(geDt.getValue().toString()) == 1) {
                    showFacesMessage("Date must be before or on " + geDt.getValue(), "E", false, "V");
                }
            }

        }
    }

    public void billDateValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String paramOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String paramCldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");

            Timestamp ts = (Timestamp) object;
            OperationBinding tsOp = getBindings().getOperationBinding("isFYOpenForCurrentDate");
            tsOp.getParamsMap().put("cldId", paramCldId);
            tsOp.getParamsMap().put("orgId", paramOrgId);
            tsOp.getParamsMap().put("dt", ts);
            tsOp.execute();

            if ("false".equalsIgnoreCase(tsOp.getResult().toString())) {
                showFacesMessage(resolvElDCMsg("#{bundle['MSG.673']}").toString(), "E", false, "V");
                //Invalid Transporter Bill Date. Transporter Bill Date must be present in an open financial year.
            }
            if (geDt.getValue() != null) {
                _log.info(ts + "    test  " + geDt.getValue());
                if (ts.toString().compareTo(geDt.getValue().toString()) == 1) {
                    showFacesMessage("Date must be before or on " + geDt.getValue(), "E", false, "V");
                }
            }

        }
    }

    public void setSupCustNameBinding(RichPanelLabelAndMessage supCustNameBinding) {
        this.supCustNameBinding = supCustNameBinding;
    }

    public RichPanelLabelAndMessage getSupCustNameBinding() {
        return supCustNameBinding;
    }

    public void setSourceOrgBinding(RichSelectOneChoice sourceOrgBinding) {
        this.sourceOrgBinding = sourceOrgBinding;
    }

    public RichSelectOneChoice getSourceOrgBinding() {
        return sourceOrgBinding;
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public void itmQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number qty = (Number) object;
            if (qty.compareTo(new Number(0)) > 0) {
                Boolean is = isPrecisionValid(26, 6, qty);
                if (is.toString().equalsIgnoreCase("true")) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision(26,6).", null));
                }
            } else {
                showFacesMessage(resolvElDCMsg("#{bundle['MSG.767']}").toString(), "E", false, "V");
            }
        }
    }


    public Boolean isPrecisionValid(Integer precision, Integer scale, Number total) {
        JboPrecisionScaleValidator vc = new JboPrecisionScaleValidator();

        vc.setPrecision(precision);


        vc.setScale(scale);

        return vc.validateValue(total);
    }

    public void setWhIdSrcBinding(RichSelectOneChoice whIdSrcBinding) {
        this.whIdSrcBinding = whIdSrcBinding;
    }

    public RichSelectOneChoice getWhIdSrcBinding() {
        return whIdSrcBinding;
    }

    public void setRcptSrcTypeBinding(RichSelectOneChoice rcptSrcTypeBinding) {
        this.rcptSrcTypeBinding = rcptSrcTypeBinding;
    }

    public RichSelectOneChoice getRcptSrcTypeBinding() {
        return rcptSrcTypeBinding;
    }

    public void setItmIdRcpt(RichInputText itmIdRcpt) {
        this.itmIdRcpt = itmIdRcpt;
    }

    public RichInputText getItmIdRcpt() {
        return itmIdRcpt;
    }

    public void deleteGeDl(DialogEvent dialogEvent) {
        /*   if (dialogEvent.getOutcome().name().equals("ok"))
            {
                OperationBinding delOp = getBindings().getOperationBinding("deleteDocument");
                delOp.execute();
                OperationBinding op=getBindings().getOperationBinding("DeleteGE");
                op.execute();
                OperationBinding exec = getBindings().getOperationBinding("Commit");
                exec.execute();

                } */
    }

    public String deleteGe() {
        OperationBinding delOp = getBindings().getOperationBinding("deleteDocument");
        delOp.execute();
        OperationBinding op = getBindings().getOperationBinding("DeleteGE");
        op.execute();
        OperationBinding exec = getBindings().getOperationBinding("Commit");
        exec.execute();
        return "onExit";
    }

    public String canceDelete() {
        deleteGePopUp.hide();
        return null;
    }

    public void setDeleteGePopUp(RichPopup deleteGePopUp) {
        this.deleteGePopUp = deleteGePopUp;
    }

    public RichPopup getDeleteGePopUp() {
        return deleteGePopUp;
    }

    public void eoNameVCE(ValueChangeEvent valueChangeEvent) {
        sourceDocNo.setValue("");
        sourceDocNo.setValue(null);
        sourceDocDt.setValue("");
        sourceDocDt.setValue(null);
        itmNameBindVar.setValue("");
        itmNameBindVar.setValue(null);
        uomBindVar.setValue("");
        uomBindVar.setValue(null);
        itmQty.setValue("");
        itmQty.setValue(null);
        schdlNo.setValue("");
        schdlNo.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(sourceDocNo);
        AdfFacesContext.getCurrentInstance().addPartialTarget(sourceDocDt);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmNameBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(uomBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmQty);
        AdfFacesContext.getCurrentInstance().addPartialTarget(schdlNo);
    }

    public void srcWhVCE(ValueChangeEvent valueChangeEvent) {
        sourceDocNo.setValue("");
        sourceDocNo.setValue(null);
        sourceDocDt.setValue("");
        sourceDocDt.setValue(null);
        itmNameBindVar.setValue("");
        itmNameBindVar.setValue(null);
        uomBindVar.setValue("");
        uomBindVar.setValue(null);
        itmQty.setValue("");
        itmQty.setValue(null);
        schdlNo.setValue("");
        schdlNo.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(sourceDocNo);
        AdfFacesContext.getCurrentInstance().addPartialTarget(sourceDocDt);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmNameBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(uomBindVar);
        AdfFacesContext.getCurrentInstance().addPartialTarget(itmQty);
        AdfFacesContext.getCurrentInstance().addPartialTarget(schdlNo);
    }

    public void setGeNodeSrcColumnVar(RichColumn geNodeSrcColumnVar) {
        this.geNodeSrcColumnVar = geNodeSrcColumnVar;
    }

    public RichColumn getGeNodeSrcColumnVar() {
        return geNodeSrcColumnVar;
    }

    public void setEoNameBinding(RichInputListOfValues eoNameBinding) {
        this.eoNameBinding = eoNameBinding;
    }

    public RichInputListOfValues getEoNameBinding() {
        return eoNameBinding;
    }

    public void rcptQtyVCE(ValueChangeEvent valueChangeEvent) {
        if (Integer.parseInt(sourceDocType.getValue().toString()) == SOURCE_DOC_TYPE_PO) {
            if (valueChangeEvent.getNewValue() != null) {
                OperationBinding op = getBindings().getOperationBinding("updateTxnQty");
                op.getParamsMap().put("oldRcptQty", valueChangeEvent.getOldValue());
                op.getParamsMap().put("newRcptQty", valueChangeEvent.getNewValue());
                op.execute();
            }
        }
    }

    public void totalRcptQtyValidatior(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number zero = new Number(0);
            // _log.info("Total Received Quantity in VAlidaor--"+(Number)object);
            if (((Number) object).compareTo(zero) == -1) {
                showFacesMessage(resolvElDCMsg("#{bundle['MSG.767']}").toString(), "E", false,
                                 "V"); //"Received Quantity cannot be less than zero."
            } else {
                Boolean is = isPrecisionValid(26, 6, ((Number) object));
                if (is.toString().equalsIgnoreCase("true")) {


                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision(26,6).", null));
                }
            }
        }
    }

    public void totalRcptQtyVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            Number totQty = new Number(0);

            totQty = (Number) (valueChangeEvent.getNewValue());

            if (totQty.compareTo(new Number(0)) >= 0) {

                if (!(sourceDocType.getValue().toString().equals(SOURCE_DOC_TYPE_PO.toString()))) {
                    OperationBinding opRcpt = getBindings().getOperationBinding("updtRcptQty");
                    opRcpt.getParamsMap().put("TotRcpt", valueChangeEvent.getNewValue());
                    opRcpt.execute();
                } else {
                    //Other than Without PO
                    //1.Set PendQty to RcptQty and Set RetQty as TotRcptQty-RcptQty
                    //2.Update TmpRcptQty as per logic in RcptQty on the basis of above RcptQty.
                    OperationBinding opRcpt = getBindings().getOperationBinding("updtRcptRetnTmpRcptQty");
                    opRcpt.getParamsMap().put("TotRcpt", valueChangeEvent.getNewValue());
                    opRcpt.execute();
                    if (opRcpt.getErrors().size() > 0)
                        System.out.println("error on TotRcptQtyVCE=" + opRcpt.getErrors());
                }
            }
        }
    }

    public void returnQtyVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            Number retnQty = new Number(0);
            retnQty = (Number) valueChangeEvent.getNewValue();
            if (retnQty.compareTo(new Number(0)) >= 0) {
                OperationBinding opRcpt = getBindings().getOperationBinding("updtRcptTmpRcptQty");
                opRcpt.getParamsMap().put("RetnQty", valueChangeEvent.getNewValue());
                opRcpt.execute();
                if (opRcpt.getErrors().size() > 0)
                    System.out.println("Error on ReturnQtyVCE=" + opRcpt.getErrors());
            }
        }
    }

    public void setTotRcptQtyBinding(RichInputText totRcptQtyBinding) {
        this.totRcptQtyBinding = totRcptQtyBinding;
    }

    public RichInputText getTotRcptQtyBinding() {
        return totRcptQtyBinding;
    }


    public void receiveBarCodeItmAL(ActionEvent actionEvent) {
        showPopup(barCodePopBinding, true);
    }

    public void setBarCodePopBinding(RichPopup barCodePopBinding) {
        this.barCodePopBinding = barCodePopBinding;
    }

    public RichPopup getBarCodePopBinding() {
        return barCodePopBinding;
    }

    public void addBarCodeItmAL(ActionEvent actionEvent) {
        if (itmIdOnPopBinding.getValue() != null && itmIdOnPopBinding.getValue().toString().length() > 0) {
            if (itmNmOnPopBinding.getValue() != null && itmNmOnPopBinding.getValue().toString().length() > 0) {
                if ((itmChkOnPopBinding.getValue() != null &&
                     itmChkOnPopBinding.getValue().toString().equalsIgnoreCase("N") &&
                     delvQtyOnPopBinding.getValue() != null) ||
                    (itmChkOnPopBinding.getValue() != null &&
                     itmChkOnPopBinding.getValue().toString().equalsIgnoreCase("Y") &&
                     delvQtyOnPopBinding.getValue() == null)) {

                    if (rcptQtyOnPopBinding.getValue() != null &&
                        rcptQtyOnPopBinding.getValue().toString().length() > 0) {
                        if (retQtyOnPopBinding.getValue() != null &&
                            retQtyOnPopBinding.getValue().toString().length() > 0) {
                            OperationBinding binding = getBindings().getOperationBinding("chkItmIdInPo");
                            binding.getParamsMap().put("ItmId", itmIdOnPopBinding.getValue());
                            binding.execute();
                            if (binding.getErrors().isEmpty() && binding.getResult() != null) {
                                String rslt = binding.getResult().toString();
                                if (rslt.equalsIgnoreCase("N")) {
                                    OperationBinding op = getBindings().getOperationBinding("updateItmQty");
                                    op.getParamsMap().put("ItmId", itmIdOnPopBinding.getValue());
                                    op.execute();
                                    if (op.getResult() != null && op.getResult().toString().equals("N")) {
                                        getBindings().getOperationBinding("insertIntoBarCodeTbl").execute();

                                    }

                                } else if (rslt.equalsIgnoreCase("Y")) {
                                    FacesMessage message = new FacesMessage("This Item not found in current PO!!");
                                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fc = FacesContext.getCurrentInstance();
                                    fc.addMessage(itmIdOnPopBinding.getClientId(), message);
                                }
                            }
                        } else {
                            FacesMessage message = new FacesMessage("Returned Quantity Can not be left blank !!");
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(retQtyOnPopBinding.getClientId(), message);

                        }
                    } else {
                        FacesMessage message = new FacesMessage("Receipt Quantity Can not be left blank !!");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(rcptQtyOnPopBinding.getClientId(), message);

                    }
                } else {
                    FacesMessage message = new FacesMessage("Delivered Quantity Can not be left blank !!");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(delvQtyOnPopBinding.getClientId(), message);

                }
            } else {
                FacesMessage message = new FacesMessage("Item Name Can not be left blank !!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(itmNmOnPopBinding.getClientId(), message);
            }
        } else {
            FacesMessage message = new FacesMessage("Please Enter an Item Id !!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(itmIdOnPopBinding.getClientId(), message);
        }
    }

    public void deleteBarCodeItmAL(ActionEvent actionEvent) {
        getBindings().getOperationBinding("Delete").execute();
        getBindings().getOperationBinding("Execute2").execute();

    }

    public void setItmIdOnPopBinding(RichInputListOfValues itmIdOnPopBinding) {
        this.itmIdOnPopBinding = itmIdOnPopBinding;
    }

    public RichInputListOfValues getItmIdOnPopBinding() {
        return itmIdOnPopBinding;
    }

    public void setItmNmOnPopBinding(RichInputText itmNmOnPopBinding) {
        this.itmNmOnPopBinding = itmNmOnPopBinding;
    }

    public RichInputText getItmNmOnPopBinding() {
        return itmNmOnPopBinding;
    }

    public void setDelvQtyOnPopBinding(RichInputText delvQtyOnPopBinding) {
        this.delvQtyOnPopBinding = delvQtyOnPopBinding;
    }

    public RichInputText getDelvQtyOnPopBinding() {
        return delvQtyOnPopBinding;
    }

    public void setRcptQtyOnPopBinding(RichInputText rcptQtyOnPopBinding) {
        this.rcptQtyOnPopBinding = rcptQtyOnPopBinding;
    }

    public RichInputText getRcptQtyOnPopBinding() {
        return rcptQtyOnPopBinding;
    }

    public void setRetQtyOnPopBinding(RichInputText retQtyOnPopBinding) {
        this.retQtyOnPopBinding = retQtyOnPopBinding;
    }

    public RichInputText getRetQtyOnPopBinding() {
        return retQtyOnPopBinding;
    }

    public void setItmChkOnPopBinding(RichOutputText itmChkOnPopBinding) {
        this.itmChkOnPopBinding = itmChkOnPopBinding;
    }

    public RichOutputText getItmChkOnPopBinding() {
        return itmChkOnPopBinding;
    }

    public void barCodePopDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equalsIgnoreCase("OK")) {
            System.out.println("inside OK --");
            getBindings().getOperationBinding("updateGeItmQty").execute();
        }
    }

    public void dlvQtyPopupValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            Number qty = (Number) object;
            if (qty.compareTo(new Number(0)) > 0) {
                Boolean is = isPrecisionValid(26, 6, qty);
                if (is.toString().equalsIgnoreCase("true")) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision(26,6).", null));
                }
            } else {
                showFacesMessage(resolvElDCMsg("#{bundle['MSG.767']}").toString(), "E", false, "V");
            }
        }

    }

    public void rcptQtyPopupValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...

        if (object != null) {
            Number qty = (Number) object;
            if (qty.compareTo(new Number(0)) > 0) {
                Boolean is = isPrecisionValid(26, 6, qty);
                if (is.toString().equalsIgnoreCase("true")) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision(26,6).", null));
                }
            } else {
                showFacesMessage(resolvElDCMsg("#{bundle['MSG.767']}").toString(), "E", false, "V");
            }
        }
    }

    public void returnQtyPopupValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        if (object != null) {
            Number qty = (Number) object;
            if (qty.compareTo(new Number(0)) > 0) {
                Boolean is = isPrecisionValid(26, 6, qty);
                if (is.toString().equalsIgnoreCase("true")) {
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                  "Invalid Precision(26,6).", null));
                }
            } else {
                showFacesMessage(resolvElDCMsg("#{bundle['MSG.767']}").toString(), "E", false, "V");
            }
        }

    }

    public void gateEntryCancelAction(ActionEvent actionEvent) {
        showPopup(geCancelPopup, true);
    }

    public void setGeCancelPopup(RichPopup geCancelPopup) {
        this.geCancelPopup = geCancelPopup;
    }

    public RichPopup getGeCancelPopup() {
        return geCancelPopup;
    }

    public void geCancelYesAction(ActionEvent actionEvent) {
        OperationBinding opCnclGe = getBindings().getOperationBinding("cancelGateEntry");
        opCnclGe.execute();
        if (opCnclGe.getResult() != null) {
            Integer ret = (Integer) opCnclGe.getResult();
            if (ret.compareTo(new Integer(1)) == 0) {
                showFacesMessage("Gate Entry Cancled Successfully.", "I", false, "F");
                geCancelPopup.hide();
            } else {
                showFacesMessage("Can not cancel Gate Entry .Contact to ESS !!", "E", false, "F");
                return;
            }
        } else {
            showFacesMessage("Can not cancel Gate Entry .Contact to ESS !!", "E", false, "F");
            return;
        }


    }

    public void geCancelNoAction(ActionEvent actionEvent) {
        geCancelPopup.hide();
    }
}


