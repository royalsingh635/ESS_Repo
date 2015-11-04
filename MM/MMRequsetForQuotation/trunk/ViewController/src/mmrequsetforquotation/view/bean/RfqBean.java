package mmrequsetforquotation.view.bean;

import adf.utils.model.ADFModelUtils;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import mmrequsetforquotation.model.services.mmRfqAMImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichCommandImageLink;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DropEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.RowQualifier;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class RfqBean {
    private RichPopup addItmPopUp;
    private RichPopup addSupPopUp;
    private RichPopup deleteItemPopUp;
    private RichTable itmTableBinding;
    private BigDecimal bestPrice;
    private String suppNmbest = "";
    private String Datebest = "";
    private BigDecimal latestPrice;
    private String suppNmlatest = "";
    private String Datelatest = "";
    private String itmFlag = "V";
    private RichTable supTableBinding;
    private RichSelectOneChoice uomLovBind;
    private BigDecimal avl_stk;
    private BigDecimal req_stk;
    private BigDecimal ord_stk;
    private RichPopup itemHistoryPopUp;
    // private RichCommandButton saveButtonBind;
    private RichInputDate docDtBind;
    private RichTable tncTable;
    private RichInputDate vldtDtBind;
    private RichSelectOneChoice tncidBind;
    private RichShowDetailItem deliverySchdlTab;
    private RichInputText rfqQtyBind;
    private RichInputText desiredPriceBind;


    boolean sendMail = true;
    private RichCommandImageLink saveButtonBind;
    private RichSelectOneChoice rfqStatusBind;
    private RichSelectOneChoice replyViaBind;
    private RichInputText transProspectEo;
    private RichInputListOfValues srcDocNoBind;
    private RichSelectOneChoice srcTypeBind;
    private RichSelectOneRadio eoTypeBinding;
    private RichInputListOfValues popSupBinding;
    private RichInputText eoIdBind;
    private RichPanelCollection panelcollbind;
    private RichShowDetailItem tncTab;
    private RichPanelGroupLayout scoreGroup;
    private RichInputText eoIdInVis;
    private RichPopup cancelPopBind;
    private RichTable flxTableBind;


    public RfqBean() {
        //System.out.println("Calling..");
    }

    private static int VARCHAR = Types.VARCHAR;
    private String mode = "V";
    private boolean form_readonly = true;
    private boolean createRfqbutton = true;
    private boolean saveButton = true;
    private boolean addItembutton = true;
    private boolean showSupp = true;
    private boolean Itemedit = true;
    private boolean tncReadonly = true;
    //Tocheck whether user press add or edit button of Item
    private String createMode = "Y";
    private String checkFlag = "";
    //refreshes the current row and undo the changes
    //Used instead of rollback
    private static ADFLogger adfLog = ADFLogger.createADFLogger(RfqBean.class);

    public void undoRowAction(String iterator, String operation) {

        DCBindingContainer bindings = (DCBindingContainer) getBindings();
        DCIteratorBinding dcIter = bindings.findIteratorBinding(iterator);
        Row row = dcIter.getCurrentRow();

        OperationBinding ob = bindings.getOperationBinding(operation);
        if (row != null) {
            Map param = ob.getParamsMap();
            param.put("row", row);
        }
        ob.execute();

    }


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);

    }

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    st.setObject(z + 2, bindVars[z]);
                    // System.out.println(bindVars[z] + "z");
                }
            }
            st.executeUpdate();

            return st.getObject(1);
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                }
            }
        }
    }


    public void createRfqButton(ActionEvent actionEvent) {
        mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
        this.mode = "A";
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        /*  Integer userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String orgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String cldid=resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        String doc_id = getDocId(userId, orgid, slocid, 18502,cldid);

        ViewObject v1 = am.getMmRfq1();
        Row row = v1.getCurrentRow();
        row.setAttribute("DocId", doc_id); */
        setCreateMode("Y");
        this.suppNmbest = "";
        this.suppNmlatest = "";
        this.bestPrice = new BigDecimal(0);
        this.latestPrice = new BigDecimal(0);
        this.Datebest = "";
        this.Datelatest = "";

        this.setForm_readonly(false);
        this.createRfqbutton = false;
        this.saveButton = false;
        this.addItembutton = false;
        this.showSupp = true;
    }

    public Object resolvElDCMsg(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        return valueExp.getValue(elContext);
    }

    public String getDocId(Integer userid, String OrgId, Integer slocid, Integer docId, String cldId) {
        /** call db function "pkg_app.get_txn_id" */
        return (String) callStoredFunction(VARCHAR, "app.pkg_app.get_txn_id(?,?,?,?,?)", new Object[] {
                                           userid, OrgId, slocid, docId, cldId
    });
    }

    public void saveButton(ActionEvent actionEvent) {
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer pending = null;
        String id = "";
        mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
        id = (String) am.getMmRfq1().getCurrentRow().getAttribute("RfqId");
        if (checkFlag.equalsIgnoreCase("N")) {
            String s = ADFModelUtils.resolvRsrc("MSG.324");
            FacesMessage errMsg = new FacesMessage(s);
            errMsg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, errMsg);
        } else {
            if (am.getMmRfqEo1().getEstimatedRowCount() == 0) {
                String s = ADFModelUtils.resolvRsrc("MSG.325");
                FacesMessage errMsg = new FacesMessage(s);
                errMsg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, errMsg);
            } else {
                String globalMode = resolvEl("#{pageFlowScope.mode}").toString();
                ViewObject v1 = am.getMmRfq1();
                if (mode.equalsIgnoreCase("A") || globalMode.equalsIgnoreCase("C")) {
                    Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                    String orgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
                    String cldid = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");

                    Row row = v1.getCurrentRow();
                    if (row.getAttribute("RfqId") == null) {
                        Integer fyid = 0;
                        OperationBinding fyIdOb = getBindings().getOperationBinding("getFYid");
                        fyIdOb.getParamsMap().put("CldId", cldid);
                        fyIdOb.getParamsMap().put("OrgId", orgid);
                        fyIdOb.getParamsMap().put("geDate", (Date) docDtBind.getValue());
                        fyIdOb.execute();
                        if (fyIdOb.getResult() != null) {
                            fyid = Integer.parseInt(fyIdOb.getResult().toString());

                        }
                        //   System.out.println("bean fy id "+fyid);
                        id = (String) (callStoredFunction(VARCHAR, "MM.FN_MM_GEN_ID (?,?,?,?,?,?)", new Object[] {
                                                          slocid, cldid, orgid, "MM$RFQ", null, fyid
                        }));
                        row.setAttribute("RfqId", id);
                    }
                    Integer srcType = Integer.parseInt(srcTypeBind.getValue().toString());
                    if (srcType == 462 && srcDocNoBind.getValue() != null &&
                        v1.getCurrentRow().getAttribute("DocIdSrc") != null) {

                        OperationBinding updatePrOb = getBindings().getOperationBinding("updatePrStatus");
                        updatePrOb.getParamsMap().put("CldId", cldid);
                        updatePrOb.getParamsMap().put("OrgId", orgid);
                        updatePrOb.getParamsMap().put("SlocId", sloc_id);
                        updatePrOb.getParamsMap().put("SrcDocId", v1.getCurrentRow().getAttribute("DocIdSrc"));
                        updatePrOb.getParamsMap().put("UserId", usr_id);
                        updatePrOb.execute();
                        String flag = "N";
                        if (updatePrOb.getResult() != null) {
                            flag = updatePrOb.getResult().toString();

                        }
                        System.out.println("bean pr update value " + flag);
                        if ("Y".equalsIgnoreCase(flag)) {
                            System.out.println("Succesfull PR status update.   ");
                        } else {
                            System.out.println("some error occur PR status update.   ");
                        }

                    }
                }
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();

                if (operationBinding.getErrors().isEmpty()) {
                    /**
                      *  Insert entry into WF items..
                    * */


                    ViewObject rfqVo = am.getMmRfq1();
                    String rfqDocId = rfqVo.getCurrentRow().getAttribute("DocId").toString();
                    String action = "I";
                    String remark = "A";

                    OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                    WfnoOp.getParamsMap().put("sloc_id", sloc_id);
                    WfnoOp.getParamsMap().put("cld_id", cld_id);
                    WfnoOp.getParamsMap().put("org_id", pOrgId);
                    WfnoOp.getParamsMap().put("doc_no", 18502);
                    WfnoOp.execute();

                    String WfNum = "0";
                    Integer level = 0;
                    Integer res = -1;
                    if (WfnoOp.getResult() != null) {
                        WfNum = WfnoOp.getResult().toString();
                    }
                    if (WfNum != null) {
                        OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                        usrLevelOp.getParamsMap().put("SlocId", sloc_id);
                        usrLevelOp.getParamsMap().put("CldId", cld_id);
                        usrLevelOp.getParamsMap().put("OrgId", pOrgId);
                        usrLevelOp.getParamsMap().put("usr_id", usr_id);
                        usrLevelOp.getParamsMap().put("WfNum", WfNum);
                        usrLevelOp.getParamsMap().put("RfqDocId", 18502);
                        usrLevelOp.execute();
                        level = -1;
                        if (usrLevelOp.getResult() != null) {
                            level = Integer.parseInt(usrLevelOp.getResult().toString());
                        }
                        if (level == -1) {
                            //                            Something went wrong while getting Workflow Level of this user.Contact to ESS.
                            String s = ADFModelUtils.resolvRsrc("MSG.2011");
                            FacesMessage message = new FacesMessage(s);
                            message.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fc = FacesContext.getCurrentInstance();
                            fc.addMessage(null, message);
                        } else {

                            OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                            insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                            insTxnOp.getParamsMap().put("cld_id", cld_id);
                            insTxnOp.getParamsMap().put("pOrgId", pOrgId);
                            insTxnOp.getParamsMap().put("RFQ_DOC_NO", 18502);
                            insTxnOp.getParamsMap().put("WfNum", WfNum);
                            insTxnOp.getParamsMap().put("rfqDocId", rfqDocId);
                            insTxnOp.getParamsMap().put("usr_idFrm", usr_id);
                            insTxnOp.getParamsMap().put("usr_idTo", usr_id);
                            insTxnOp.getParamsMap().put("levelFrm", level);
                            insTxnOp.getParamsMap().put("levelTo", level);
                            insTxnOp.getParamsMap().put("action", action);
                            insTxnOp.getParamsMap().put("remark", remark);
                            insTxnOp.getParamsMap().put("amount", 0);
                            insTxnOp.execute();

                            if (insTxnOp.getResult() != null) {
                                res = Integer.parseInt(insTxnOp.getResult().toString());
                            }
                            am.getDBTransaction().commit();
                            this.mode = "V";
                            RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "V");
                            this.form_readonly = true;
                            this.createRfqbutton = false;
                            this.saveButton = true;
                            this.addItembutton = true;
                            this.Itemedit = true;
                            this.tncReadonly = true;
                            this.showSupp = true;
                            // tncidBind.setReadOnly(true);
                            StringBuilder msg3 = new StringBuilder();
                            msg3.append("<html><body>");
                            //                            String s=ADFModelUtils.resolvRsrc("#{bundle['LBL.794']}");
                            //                            String st=ADFModelUtils.resolvRsrc("#{bundle['MSG.347']}");
                            String s = ADFModelUtils.resolvRsrc("LBL.794");
                            String st = ADFModelUtils.resolvRsrc("MSG.347");

                            msg3.append("<p>" + s + "<b>" + id + "</b>" + st + "</p>");
                            msg3.append("</body></html>");


                            FacesMessage saveMsg = new FacesMessage(msg3.toString());
                            saveMsg.setSeverity(FacesMessage.SEVERITY_INFO);
                            FacesContext ctx = FacesContext.getCurrentInstance();
                            ctx.addMessage(null, saveMsg);
                        }
                    } else {
                        String s = ADFModelUtils.resolvRsrc("MSG.1486");
                        //                        "Workflow not Defined for this document"
                        FacesMessage message = new FacesMessage(s);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);
                    }
                }
            }
        }
    }

    public String cancelButton(ActionEvent actionEvent) {
        return null;
    }

    public void addItemButton(ActionEvent actionEvent) {
        Date vldtDt = null;
        Date docDt = null;
        if (vldtDtBind.getValue() != null && vldtDtBind.getValue().toString().length() > 0)
            vldtDt = (Date) vldtDtBind.getValue();
        if (docDtBind.getValue() != null && docDtBind.getValue().toString().length() > 0)
            docDt = (Date) docDtBind.getValue();
        //        doc date is
        //        " validate date is "
        //        and comparison is "
        String s = ADFModelUtils.resolvRsrc("MGS.2353");
        String st = ADFModelUtils.resolvRsrc("MGS.2354");
        String stt = ADFModelUtils.resolvRsrc("MGS.2355");

        adfLog.info(s + docDt + st + vldtDt + stt);
        if (docDt != null && vldtDt != null) {
            if (docDt.dateValue().toString().compareTo(vldtDt.dateValue().toString()) == 1) {
                //                "The date must be on or after  "
                String sk = ADFModelUtils.resolvRsrc("MSG.585");
                String msg2 = sk + docDt.dateValue();
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(vldtDtBind.getClientId(), message2);
                return;
            } else {
                mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
                am.getuomLOV1().executeQuery();
                am.getItemLOV1().executeQuery();
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
                operationBinding.execute();
                adfLog.info("----------after createInsert1-----------------");
                if (itmTableBinding.getRowCount() <= 1 && flxTableBind.getRowCount() == 0) {
                    getBindings().getOperationBinding("getFlexiFieldFilter").execute();
                }
                Integer userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
                adfLog.info("----------after createInsert1111111111111111-----------------");
                if (mode.equalsIgnoreCase("E")) {

                    am.getMmRfq1().getCurrentRow().setAttribute("UsrIdMod", userId);
                    //   am.getMmRfq1().getCurrentRow().setAttribute("UsrIdMod", userId);
                }
                this.itmFlag = "A";
                adfLog.info("----------after createInsert2-----------------");
                showPopup(addItmPopUp, true);
                adfLog.info("----------after createInsert3-----------------");
            }
        } else {
            //            Please enter required date
            String msg2 = ADFModelUtils.resolvRsrc("MSG.2356");
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(vldtDtBind.getClientId(), message2);
            return;
        }


    }

    public void addSuplrButton(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert2");
        operationBinding.execute();
        //it need to be unable method return in amIMPL
        // OperationBinding operationBinding2 = getBindings().getOperationBinding("supplierLovFilter");
        //   operationBinding2.execute();
        showPopup(addSupPopUp, true);

    }

    protected void callStoredProcedure(String stmt, Object[] bindVars) {
        PreparedStatement st = null;
        mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");

        try {
            // 1. Create a JDBC PreparedStatement for
            st = am.getDBTransaction().createPreparedStatement("begin " + stmt + ";end;", 0);
            if (bindVars != null) {
                // 2. Loop over values for the bind variables passed in, if any
                for (int z = 0; z < bindVars.length; z++) {
                    // 3. Set the value of each bind variable in the statement
                    st.setObject(z + 1, bindVars[z]);
                }
            }
            // 4. Execute the statement
            st.executeUpdate();
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    // 5. Close the statement
                    st.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    private void showPopup(RichPopup pop, boolean visible) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (context != null && pop != null) {
                String popupId = pop.getClientId(context);
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

    public void DialogListenerItm(DialogEvent dialogEvent) {

        if (dialogEvent.getOutcome().name().equals("ok")) {
            BindingContainer bindings = getBindings();
            /* OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute(); */
            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute1");
            operationBinding1.execute();
            this.showSupp = false;
            this.checkFlag = "N";

        }

        else if (dialogEvent.getOutcome().name().equals("cancel")) {

        }
    }

    public void DialogListenerSup(DialogEvent dialogEvent) {
        String supplier = "";
        if (popSupBinding.getValue() != null) {
            supplier = popSupBinding.getValue().toString();
            System.out.println("supplier name in dialog ok listener is===" + supplier);
        }
        mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");

        Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String orgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String hoOrgid = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        Integer userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
        String cldid = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");

        if (dialogEvent.getOutcome().name().equals("ok")) {
            //------------------------------------------------
            Row currRow = am.getMmRfqEo1().getCurrentRow();

            String retVal = "S";
            if (currRow.getAttribute("TransEoType") != null &&
                Integer.parseInt(currRow.getAttribute("TransEoType").toString()) == 82 &&
                (transProspectEo.getValue() == null || transProspectEo.getValue() == "")) {
                //                Supplier Prospect Required!!!
                String msg2 = ADFModelUtils.resolvRsrc("MSG.2357");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(transProspectEo.getClientId(), message2);

            } else if (currRow.getAttribute("TransEoType") != null &&
                       Integer.parseInt(currRow.getAttribute("TransEoType").toString()) == 69 &&
                       (popSupBinding.getValue() == null || popSupBinding.getValue() == "")) {
                //                "Supplier Name Required!!!"
                String msg2 = ADFModelUtils.resolvRsrc("MSG.2358");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(popSupBinding.getClientId(), message2);


            }

            else {
                if (currRow.getAttribute("TransEoType") != null) {
                    Integer eoType = Integer.parseInt(currRow.getAttribute("TransEoType").toString());
                    System.out.println(eoType + "return vakue ====  ");
                    if (eoType == 69) {
                        retVal = "P";
                    } else if (eoType == 82 && transProspectEo.getValue() != null) {
                        System.out.println(slocid + "   slocid  " + cldid + "  cldid  " + orgid + "  orgid " +
                                           transProspectEo.getValue() + "  transProspectEo.getValue() " + eoType +
                                           "   eoType  " + userId);
                        BindingContainer bindings = getBindings();
                        OperationBinding insrtEo = bindings.getOperationBinding("getNewEoId");
                        insrtEo.getParamsMap().put("p_SlocId", slocid);
                        insrtEo.getParamsMap().put("p_CldId", cldid);
                        insrtEo.getParamsMap().put("p_OrgId", orgid);
                        insrtEo.getParamsMap().put("p_Name", transProspectEo.getValue());
                        insrtEo.getParamsMap().put("p_Type", eoType);
                        insrtEo.getParamsMap().put("p_UsrId", userId);
                        insrtEo.execute();
                        if (insrtEo.getResult() != null) {
                            retVal = insrtEo.getResult().toString();
                        }
                    }
                }
                Integer eoType = Integer.parseInt(currRow.getAttribute("TransEoType").toString());
                // System.out.println("return vakue ====  " + retVal);
                String s = ADFModelUtils.resolvRsrc("MSG.2359");
                adfLog.info(s + retVal);
                if (retVal.equalsIgnoreCase("P") ||
                    (eoType == 82 && retVal.equalsIgnoreCase(transProspectEo.getValue().toString()))) {

                    Integer eo_id = null;
                    if (am.getMmRfqEo1().getCurrentRow() != null) {
                        Row[] rw = am.getsupplierLOV().getFilteredRows("EoNm", supplier);
                        System.out.println("total rows in length is==" + rw.length);
                        if (rw.length > 0)
                            eo_id = Integer.parseInt(rw[0].getAttribute("EoId").toString());
                        // eo_id = Integer.parseInt(am.getMmRfqEo1().getCurrentRow().getAttribute("EoId").toString());
                        //eo_id=Integer.parseInt(eoIdBind.getValue().toString());
                        System.out.println("eoid through current row is===" + eo_id);
                    }
                    Date date = new Date();
                    date = (Date) date.getCurrentDate();
                    RowIterator rowIter = am.getMmRfqItm1().createRowSetIterator(null);
                    while (rowIter.hasNext()) {
                        Row r = rowIter.next();
                        String itm_id = (String) r.getAttribute("ItmId");
                        String uom = (String) r.getAttribute("ItmUom");
                        String Docid = (String) r.getAttribute("DocId");
                        System.out.println("supplier id in mm rfq item iterator is =======>>>>>>>>>" + eo_id);
                        /**condition apply becoz of prospect crash 10-03-2015*/
                        if (eo_id != null) {
                            BigDecimal flag =
                                (BigDecimal) (callStoredFunction(NUMBER,
                                                                 "MM.MM_INS_EO_ITM_AUTO (?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                                                 cldid, slocid, hoOrgid, orgid, eo_id, null, itm_id,
                                                                 uom, userId, 18502, Docid
                            }));
                        }
                        System.out.println("supplier id in mm rfq item iterator is <<<<<<<<<<<<<<<<<<<=======>>>>>>>>>" +
                                           eo_id);
                    }
                    /*   OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();  */
                    checkFlag = "Y";
                    /*  BindingContainer bindings = getBindings();
                    OperationBinding operationBinding1 = bindings.getOperationBinding("Execute4");
                    operationBinding1.execute();   */
                    AdfFacesContext.getCurrentInstance().addPartialTarget(panelcollbind);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(supTableBinding);

                } else {
                    //                    Some error occur please contact ESS!!
                    String msg2 = ADFModelUtils.resolvRsrc("MSG.2360");
                    FacesMessage message2 = new FacesMessage(msg2);
                    message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message2);
                }
            }
        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
            ViewObject vo = am.getMmRfqEo1();
            if (vo.getCurrentRow() != null) {
                Row r = vo.getCurrentRow();
                r.remove();
                vo.executeQuery();
                AdfFacesContext.getCurrentInstance().addPartialTarget(supTableBinding);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getScoreGroup());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSupTableBinding());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEoIdInVis());
    }

    public void popUpCancelListItm(PopupCanceledEvent popupCanceledEvent) {

        BindingContainer bindings = getBindings();
        mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");


        if (am.getDBTransaction().isDirty() == true && itmFlag.equals("E")) { //else throws NPE in Add mode
            /*    if(am.getMmRfq1().getCurrentRow().getAttribute("RfqId")!=null){
                 this.undoRowAction("MmRfqItm1Iterator", "refreshPkgItmRow");
             } */



        } else if (itmFlag.equals("A")) {

            ViewObject v = am.getMmRfqItm1();
            Row r = v.getCurrentRow();
            r.remove();
            v.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBinding);
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveButtonBind);

            OperationBinding ob = bindings.getOperationBinding("Execute1");
            ob.execute();
        }

    }


    public void setAddItmPopUp(RichPopup addItmPopUp) {
        this.addItmPopUp = addItmPopUp;
    }

    public RichPopup getAddItmPopUp() {
        return addItmPopUp;
    }

    public void CancelListenerSup(PopupCanceledEvent popupCanceledEvent) {

        mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
        // BindingContainer bindings = getBindings();
        //DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("MmRfq1Iterator");
        //  Key parentKey = parentIter.getCurrentRow().getKey();
        /*  OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute(); */
        //  OperationBinding operationBinding1 = bindings.getOperationBinding("Execute2");
        // operationBinding1.execute();

        //AdfFacesContext.getCurrentInstance().addPartialTarget(supTableBinding);
        /*  if (parentKey != null) {
            if (parentIter.getRowSetIterator().getRow(parentKey) != null) {
                parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
            }
        } */
        if (am.getMmRfqEo1().getCurrentRow() != null) {
            System.out.println("" + am.getMmRfqEo1().getCurrentRow().getAttribute("EoType"));
            String eotransId = null;
            if (am.getMmRfqEo1().getCurrentRow().getAttribute("EoType") != null) {
                eotransId = am.getMmRfqEo1().getCurrentRow().getAttribute("EoType").toString();
            }
            System.out.println("supplier type id" + eotransId);
            //AdfFacesContext.getCurrentInstance().addPartialTarget(eoTypeBinding);//
            System.out.println("eo type value is" + eoTypeBinding.getValue());
            // if(eotransId!=null && eotransId.equalsIgnoreCase("82")){
            //AdfFacesContext.getCurrentInstance().addPartialTarget(supTableBinding);
            ViewObject vo = am.getMmRfqEo1();
            if (vo.getCurrentRow() != null) {
                Row r = vo.getCurrentRow();
                r.remove();
                // vo.executeQuery();
                popSupBinding.setValue("");
                popSupBinding.setValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(supTableBinding);
                AdfFacesContext.getCurrentInstance().addPartialTarget(panelcollbind);

                //AdfFacesContext.getCurrentInstance().addPartialTarget(supTableBinding);

                //   }
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getSupTableBinding());


        /*  eoTypeBinding.setValue(null);
        eoTypeBinding.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(eoTypeBinding);//
        popSupBinding.setValue(null);
        popSupBinding.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(popSupBinding);

        transProspectEo.setValue(null);
        transProspectEo.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(transProspectEo); */

    }

    public void setAddSupPopUp(RichPopup addSupPopUp) {
        this.addSupPopUp = addSupPopUp;
    }

    public RichPopup getAddSupPopUp() {
        return addSupPopUp;
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

    public void showSupplier(ActionEvent actionEvent) {

        Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String orgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String cldid = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        String hoorgid = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String globalMode = resolvEl("#{pageFlowScope.mode}").toString();
        System.out.println("global mode value is" + globalMode);
        mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
        ViewObject v1 = am.getMmRfq1();
        if (docDtBind.getValue() != null) {

            RowSetIterator rrs = am.getMmRfqEo1().createRowSetIterator(null);
            while (rrs.hasNext()) {
                System.out.println("i am n show supplier method");
                Row rw = rrs.next();
                rw.remove();
                // rrs.next().remove();
            }
            am.getMmRfqEo1().executeQuery();
            //for checking error for some time
            System.out.println("before post changes");
            am.getDBTransaction().postChanges();
            // OperationBinding obcom = getBindings().getOperationBinding("Commit");
            // obcom.execute();
            System.out.println("after post changes");
            String RfqDocID = (String) am.getMmRfq1().getCurrentRow().getAttribute("DocId");
            System.out.println("rfqid in method isss====" + RfqDocID);

            Integer userId = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
            System.out.println("user id in show supplier method is===" + userId);
            callStoredProcedure("PKG_MM_RFQ.GET_SUPPLIER(?,?,?,?,?,?,?)", new Object[] {
                                slocid, cldid, hoorgid, orgid, RfqDocID, userId, 1
            });

            BindingContainer bindings = getBindings();
            OperationBinding operationBinding1 = bindings.getOperationBinding("Execute2");
            operationBinding1.execute();
            am.getMmRfqEo1().executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(supTableBinding);
            if (am.getMmRfqEo1().getEstimatedRowCount() == 0) {
                String s = ADFModelUtils.resolvRsrc("MSG.326");
                FacesMessage errMsg = new FacesMessage(s);
                errMsg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, errMsg);
            } else {
                this.saveButton = false;
                this.checkFlag = "Y";
            }


            // }
        } else {
            String s = ADFModelUtils.resolvRsrc("MSG.328");
            FacesMessage errMsg = new FacesMessage(s);
            errMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(docDtBind.getClientId(), errMsg);
        }
    }


    public void deleteSuplink(ActionEvent actionEvent) {

        BindingContainer bindings = getBindings();
        OperationBinding ob = bindings.getOperationBinding("Delete");
        ob.execute();
        OperationBinding ob1 = bindings.getOperationBinding("Execute2");
        ob1.execute();
        this.saveButton = false;
        // this.checkFlag = "N";
    }

    public void setForm_readonly(boolean form_readonly) {
        this.form_readonly = form_readonly;
    }

    public boolean isForm_readonly() {
        return form_readonly;
    }


    public void addTncButton(ActionEvent actionEvent) {

        BindingContext bc = BindingContext.getCurrent();
        DCBindingContainer dcb = (DCBindingContainer) bc.getCurrentBindingsEntry();

        mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
        ViewObject v1 = am.getMmRfqTnc1();

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert3");
        operationBinding.execute();
        //   Row row = (Row)operationBinding.execute();

        if (operationBinding.getErrors().size() == 0) {
            ArrayList lst = new ArrayList(1);
            lst.add(v1.getCurrentRow().getKey());
            getTncTable().setActiveRowKey(lst);
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(tncTable);

        this.saveButton = false;
        this.tncReadonly = false;
    }


    public void setCreateRfqbutton(boolean createRfqbutton) {
        this.createRfqbutton = createRfqbutton;
    }

    public boolean isCreateRfqbutton() {
        return createRfqbutton;
    }

    public void setSaveButton(boolean saveButton) {
        this.saveButton = saveButton;
    }

    public boolean isSaveButton() {
        return saveButton;
    }

    public void setAddItembutton(boolean addItembutton) {
        this.addItembutton = addItembutton;
    }

    public boolean isAddItembutton() {
        return addItembutton;
    }

    public void setShowSupp(boolean showSupp) {
        this.showSupp = showSupp;
    }

    public boolean isShowSupp() {
        return showSupp;
    }


    public void editRfqButton(ActionEvent actionEvent) {
        mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
        ViewObject mmRfq = am.getMmRfq1();
        Integer status = Integer.parseInt(mmRfq.getCurrentRow().getAttribute("RfqStatus").toString());
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer pending = null;
        //PurOrderAMImpl poAM = getAm();
        //-------------------------------------------------------
        //   System.out.println("sloc_id    "+sloc_id+"cld_id   "+cld_id+"org_id  "+pOrgId);
        OperationBinding chkUsr = getBindings().getOperationBinding("pendingRfqCheck");
        chkUsr.getParamsMap().put("SlocId", sloc_id);
        chkUsr.getParamsMap().put("CldId", cld_id);
        chkUsr.getParamsMap().put("OrgId", pOrgId);
        chkUsr.getParamsMap().put("RfqDocNo", 18502);
        chkUsr.execute();

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
        }

        //         System.out.println(usr_id+"pending user id="+pending);
        if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(usr_id) == 0) {
            String str = "";
            if (status == 151 || status == 203) {
                this.mode = "E";
                RequestContext.getCurrentInstance().getPageFlowScope().put("mode", "C");
                this.form_readonly = false;
                this.createRfqbutton = false;
                this.saveButton = false;
                this.addItembutton = false;
                this.showSupp = false;
                //   tncidBind.setReadOnly(false);
            } else {
                if (status == 152) {
                    str = ADFModelUtils.resolvRsrc("MSG.329");
                    //                    str = resolvElDCMsg("#{bundle['MSG.329']}").toString();
                } else if (status == 153) {
                    str = ADFModelUtils.resolvRsrc("MSG.330");
                    //                    str = resolvElDCMsg("#{bundle['MSG.330']}").toString();
                } else if (status == 240) {
                    str = ADFModelUtils.resolvRsrc("MSG.2361");
                    //                    str = "This RFQ is Approved.You can not Edit this RFQ.";
                }
                FacesMessage errMsg = new FacesMessage(str);
                errMsg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, errMsg);
            }
        } else {

            ViewObject vo = am.getuserLOV();
            Row[] filteredRows = vo.getFilteredRows("UsrId", pending);
            String usrName = filteredRows[0].getAttribute("UsrName").toString();
            //pending != usr_id
            StringBuilder msg3 = new StringBuilder();
            String s = ADFModelUtils.resolvRsrc("MSG.2362");
            msg3.append("<html><body>");
            msg3.append("<p>" + s + "<b>" + usrName + "</b> ] </p>");
            msg3.append("</body></html>");


            String msg2 = ADFModelUtils.resolvRsrc("MSG.2363");
            //            ""
            FacesMessage message2 = new FacesMessage(msg3.toString());
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        }
    }

    public void editItemButton(ActionEvent actionEvent) {
        itmFlag = "E";
        showPopup(addItmPopUp, true);
    }

    public void itmDeleteButton(ActionEvent actionEvent) {
        mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
        ViewObject vo = am.getMmRfqItm1();
        Long totalrows = vo.getEstimatedRowCount();
        Integer totRow = totalrows.intValue();
        System.out.println("No of rows:" + am.getMmRfqEo1().getEstimatedRowCount());

        if (totRow == 1) {
            showPopup(deleteItemPopUp, true);
        } else {
            BindingContainer bindings = getBindings();
            OperationBinding ob = bindings.getOperationBinding("Delete1");
            ob.execute();
            OperationBinding ob1 = bindings.getOperationBinding("Execute1");
            ob1.execute();
            this.checkFlag = "N";
        }
        this.saveButton = false;
    }

    public void setItemedit(boolean Itemedit) {
        this.Itemedit = Itemedit;
    }

    public boolean isItemedit() {
        return Itemedit;
    }

    public void setDeleteItemPopUp(RichPopup deleteItemPopUp) {
        this.deleteItemPopUp = deleteItemPopUp;
    }

    public RichPopup getDeleteItemPopUp() {
        return deleteItemPopUp;
    }

    public void DeleteDialogListenerItem(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("yes")) {
            BindingContainer bindings = getBindings();

            mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
            ViewObject vo = am.getMmRfqEo1();
            int totalCount = vo.getRowCount(); //get ALL rows
            int rangeSize = vo.getRangeSize();
            vo.setRangeSize(totalCount);

            Row[] rArray = vo.getAllRowsInRange();

            for (Row r : rArray) {
                r.remove();
            }
            vo.setRangeSize(rangeSize);
            vo.executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBinding);
            OperationBinding ob = bindings.getOperationBinding("Delete1");
            ob.execute();
            OperationBinding ob1 = bindings.getOperationBinding("Execute1");
            ob1.execute();
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBinding);
        }

        else if (dialogEvent.getOutcome().name().equals("no")) {


        }


    }

    public void setItmTableBinding(RichTable itmTableBinding) {
        this.itmTableBinding = itmTableBinding;
    }

    public RichTable getItmTableBinding() {
        return itmTableBinding;
    }

    public void setTncReadonly(boolean tncReadonly) {
        this.tncReadonly = tncReadonly;
    }

    public boolean isTncReadonly() {
        return tncReadonly;
    }

    public void deleteTncLink(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding ob = bindings.getOperationBinding("Delete2");
        ob.execute();
        OperationBinding ob1 = bindings.getOperationBinding("Execute3");
        ob1.execute();
        this.saveButton = false;
    }

    private static int NUMBER = Types.NUMERIC;


    protected Object callStoredFunction(String stmt, Object[] bindVars) {
        //used to get best price
        CallableStatement st = null;
        try {
            /** 1. Create a JDBC CallabledStatement */
            mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin " + stmt + ";end;", 0);
            /** 2. Register the first bind variable for the return value. and last 2 variable for output variable of function. */
            st.setObject(1, bindVars[0]);
            st.setObject(2, bindVars[1]);
            st.setObject(3, bindVars[2]);
            st.registerOutParameter(4, NUMBER);
            st.registerOutParameter(5, VARCHAR);
            st.registerOutParameter(6, VARCHAR);
            /** 5. Set the value of user-supplied bind vars in the stmt */
            st.executeUpdate();
            try {
                setBestPrice((BigDecimal) st.getObject(4));
                setSuppNmbest((st.getObject(5).toString()));
                setDatebest(st.getObject(6).toString());
            } catch (NullPointerException e) {
                System.out.println(e);
                e.printStackTrace();
            }
            return null;
        } catch (SQLException e) {
            int end = e.getMessage().indexOf("\n");

            throw new JboException(e.getMessage());
        } finally {
            if (st != null) {
                try {
                    /** 7. Close the statement */
                    st.close();
                } catch (SQLException e) {

                }
            }
        }
    }


    protected Object callStoredFunctionLatest(String stmt, Object[] bindVars) {
        //used to get latest price
        CallableStatement st = null;
        try {
            /** 1. Create a JDBC CallabledStatement */
            mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin " + stmt + ";end;", 0);
            /** 2. Register the first bind variable for the return value. and last 2 variable for output variable of function. */
            st.setObject(1, bindVars[0]);
            st.setObject(2, bindVars[1]);
            st.setObject(3, bindVars[2]);
            st.registerOutParameter(4, NUMBER);
            st.registerOutParameter(5, VARCHAR);
            st.registerOutParameter(6, VARCHAR);
            /** 5. Set the value of user-supplied bind vars in the stmt */
            st.executeUpdate();

            try {
                setLatestPrice((BigDecimal) st.getObject(4));
                setSuppNmlatest((st.getObject(5).toString()));
                setDatelatest(st.getObject(6).toString());
            } catch (NullPointerException e) {
                System.out.println(e);
                e.printStackTrace();
            }

            return null;
        } catch (SQLException e) {
            int end = e.getMessage().indexOf("\n");

            throw new JboException(e.getMessage());
        } finally {
            if (st != null) {
                try {
                    /** 7. Close the statement */
                    st.close();
                } catch (SQLException e) {

                }
            }
        }
    }

    public void ItmTableSelectionListener(SelectionEvent selectionEvent) {
        ADFUtil.invokeEL("#{bindings.MmRfqItm1.collectionModel.makeCurrent}", new Class[] { SelectionEvent.class }, new Object[] {
                         selectionEvent });
        Row selectedRow = (Row) ADFUtil.evaluateEL("#{bindings.MmRfqItm1Iterator.currentRow}");
        String itmId = (String) selectedRow.getAttribute("ItmId");
        Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String orgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        //  callStoredFunction("PKG_MM_RFQ.GET_BEST_PRICE(?,?,?,?,?,?)", new Object[] { slocid, orgid, itmId });
        //callStoredFunctionLatest("PKG_MM_RFQ.GET_LATEST_PRICE(?,?,?,?,?,?)", new Object[] { slocid, orgid, itmId });

    }


    public void setSuppNmbest(String suppNmbest) {
        this.suppNmbest = suppNmbest;
    }

    public String getSuppNmbest() {
        return suppNmbest;
    }

    public void setBestPrice(BigDecimal bestPrice) {
        this.bestPrice = bestPrice;
    }

    public BigDecimal getBestPrice() {
        return bestPrice;
    }

    public void setDatebest(String Datebest) {
        this.Datebest = Datebest;
    }

    public String getDatebest() {
        return Datebest;
    }

    public void setLatestPrice(BigDecimal latestPrice) {
        this.latestPrice = latestPrice;
    }

    public BigDecimal getLatestPrice() {
        return latestPrice;
    }

    public void setSuppNmlatest(String suppNmlatest) {
        this.suppNmlatest = suppNmlatest;
    }

    public String getSuppNmlatest() {
        return suppNmlatest;
    }

    public void setDatelatest(String Datelatest) {
        this.Datelatest = Datelatest;
    }

    public String getDatelatest() {
        return Datelatest;
    }

    public void setSupTableBinding(RichTable supTableBinding) {
        this.supTableBinding = supTableBinding;
    }

    public RichTable getSupTableBinding() {
        return supTableBinding;
    }

    public void setUomLovBind(RichSelectOneChoice uomLovBind) {
        this.uomLovBind = uomLovBind;
    }

    public RichSelectOneChoice getUomLovBind() {
        return uomLovBind;
    }

    protected Object callStoredFunctionHistory(String stmt, Object[] bindVars) {

        CallableStatement st = null;
        try {
            /** 1. Create a JDBC CallabledStatement */
            mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin " + stmt + ";end;", 0);
            /** 2. Register the first 2 and last 3 bind variable for the return value. and mid 3 variable for output variable of function. */
            st.setObject(1, bindVars[0]);
            st.setObject(2, bindVars[1]);
            st.setObject(3, bindVars[2]);
            st.setObject(4, bindVars[3]);
            st.registerOutParameter(5, NUMBER);
            st.registerOutParameter(6, NUMBER);
            st.registerOutParameter(7, NUMBER);
            st.setObject(8, bindVars[4]);
            st.setObject(9, bindVars[5]);
            st.setObject(10, bindVars[6]);
            ; //RECENTTELY UPDATED 16-03-15
            /** 5. Set the value of user-supplied bind vars in the stmt */
            st.executeUpdate();

            try {
                /*  setAvl_stk((BigDecimal)st.getObject(4));
                setReq_stk((BigDecimal)(st.getObject(5)));
                setOrd_stk((BigDecimal)st.getObject(6)); */
                setAvl_stk((BigDecimal) st.getObject(5));
                //System.out.println("avl "+st.getObject(4));
                setReq_stk((BigDecimal) (st.getObject(6)));
                // System.out.println("req "+st.getObject(5));
                setOrd_stk((BigDecimal) st.getObject(7));
                System.out.println("insida proce : " + st.getObject(5) + " st.getObject(4) : " + st.getObject(6) +
                                   "  st.getObject(5) : " + st.getObject(7));

            } catch (NullPointerException e) {
                System.out.println(e);
                e.printStackTrace();
            }

            return null;
        } catch (SQLException e) {
            int end = e.getMessage().indexOf("\n");

            throw new JboException(e.getMessage());
        } finally {
            if (st != null) {
                try {
                    /** 7. Close the statement */
                    st.close();
                } catch (SQLException e) {

                }
            }
        }
    }

    public void seeItemHistorylink(ActionEvent actionEvent) {
        mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
        String itmId = (String) am.getMmRfqItm1().getCurrentRow().getAttribute("ItmId");

        Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");

        String orgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Date date = new Date();
        date = (Date) date.getCurrentDate();
        Integer fyId = (Integer) callStoredFunction(Types.INTEGER, "APP.GET_ORG_FY_ID(?,?,?)", new Object[] {
                                                    cldId, orgid, new Timestamp(System.currentTimeMillis())
        });

        callStoredFunctionHistory("MM.PKG_MM_STK.GET_STK(?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                  slocid, cldId, itmId, fyId, orgid, date, null
        });
        /*  callStoredFunctionHistory("PKG_MM_STK.GET_STK(?,?,?,?,?,?,?,?,?,?)",
                                  new Object[] { slocid, cldId, itmId, orgid, date, null }); */
        showPopup(itemHistoryPopUp, true);
    }

    public void setAvl_stk(BigDecimal avl_stk) {
        this.avl_stk = avl_stk;
    }

    public BigDecimal getAvl_stk() {
        return avl_stk;
    }

    public void setReq_stk(BigDecimal req_stk) {
        this.req_stk = req_stk;
    }

    public BigDecimal getReq_stk() {
        return req_stk;
    }

    public void setOrd_stk(BigDecimal ord_stk) {
        this.ord_stk = ord_stk;
    }

    public BigDecimal getOrd_stk() {
        return ord_stk;
    }

    public void setItemHistoryPopUp(RichPopup itemHistoryPopUp) {
        this.itemHistoryPopUp = itemHistoryPopUp;
    }

    public RichPopup getItemHistoryPopUp() {
        return itemHistoryPopUp;
    }

    public void desiredDiscValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number disc = (Number) object;
            Number hund = new Number(100);
            Number zero = new Number(0);
            adfLog.info("object value is " + object);
            if (disc != null) {
                if (disc.compareTo(hund) == 1) {
                    String s = ADFModelUtils.resolvRsrc("MSG.331");
                    String st = ADFModelUtils.resolvRsrc("MSG.332");
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, s, st));
                } else if (disc.compareTo(zero) == -1) {
                    String s = ADFModelUtils.resolvRsrc("MSG.331");
                    String st = ADFModelUtils.resolvRsrc("MSG.332");
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, s, st));
                }
            }
        }
    }

    public void desiredPriceValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number price = (Number) object;
            Number hund = new Number(100);
            Number zero = new Number(0);
            //  if(rfqQtyBind.getValue()!=null){
            if (price.compareTo(zero) == -1 || price.compareTo(zero) == 0) {
                String s = ADFModelUtils.resolvRsrc("MSG.334");
                String st = ADFModelUtils.resolvRsrc("MSG.335");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, s, st));
            }
        }
        //   }
    }

    public void rfqQtyValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            Number qty = (Number) object;
            Number hund = new Number(100);
            Number zero = new Number(0);
            if (qty.compareTo(zero) == -1 || qty.compareTo(zero) == 0) {
                String s = ADFModelUtils.resolvRsrc("MSG.336");
                String st = ADFModelUtils.resolvRsrc("MSG.337");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, s, st));
            }
        }
    }


    public void setDocDtBind(RichInputDate docDtBind) {
        this.docDtBind = docDtBind;
    }

    public RichInputDate getDocDtBind() {
        return docDtBind;
    }

    public void deliveryScdlValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Number zero = new Number(0);
        if (object != null) {
            Number num = (Number) object;
            if (num.compareTo(zero) == -1) {
                String s = ADFModelUtils.resolvRsrc("MSG.338");
                String st = ADFModelUtils.resolvRsrc("MSG.339");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, s, st));
            }
        }
    }

    public void itmNameTransVCE(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String orgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            String hoOrgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
            ViewObjectImpl descvo = am.getItmDescTune();
            descvo.setNamedWhereClauseParam("bindItmDesc", valueChangeEvent.getNewValue().toString());
            descvo.setNamedWhereClauseParam("bindCldId", cldId);
            descvo.setNamedWhereClauseParam("bindSlocId", slocid);
            descvo.setNamedWhereClauseParam("bindHoOrgId", hoOrgid);
            descvo.executeQuery();
            Row[] itmLov = descvo.getFilteredRows("ItmDesc", valueChangeEvent.getNewValue().toString());
            adfLog.info("itmLov filtered rows found " + itmLov.length);
            String itmId = null;
            if (itmLov.length > 0) {
                itmId = itmLov[0].getAttribute("ItmId").toString();
            }
            if (itmId != null) {
                // itmId= am.getMmRfqItm1().getCurrentRow().getAttribute("ItmId").toString();
                callStoredFunctionForTransItm("PKG_MM_RFQ.GET_BEST_PRICE(?,?,?,?,?,?,?)", new Object[] {
                                              slocid, cldId, orgid, itmId
                });
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(desiredPriceBind);
    }

    protected Object callStoredFunctionForTransItm(String stmt, Object[] bindVars) {
        //used to get best price
        CallableStatement st = null;
        try {
            /** 1. Create a JDBC CallabledStatement */
            mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
            st = am.getDBTransaction().createCallableStatement("begin " + stmt + ";end;", 0);
            /** 2. Register the first bind variable for the return value. and last 2 variable for output variable of function. */
            st.setObject(1, bindVars[0]);
            st.setObject(2, bindVars[1]);
            st.setObject(3, bindVars[2]);
            st.setObject(4, bindVars[3]);
            st.registerOutParameter(5, NUMBER);
            st.registerOutParameter(6, VARCHAR);
            st.registerOutParameter(7, VARCHAR);
            /** 5. Set the value of user-supplied bind vars in the stmt */
            st.executeUpdate();
            try {
                Row curr = am.getMmRfqItm1().getCurrentRow();
                curr.setAttribute("DesiredPrice", new Number((BigDecimal) st.getObject(5)));
                /* setBestPrice((BigDecimal)st.getObject(4));
                setSuppNmbest((st.getObject(5).toString()));
                setDatebest(st.getObject(6).toString()); */
            } catch (NullPointerException e) {
                System.out.println(e);
                e.printStackTrace();
            }
            return null;
        } catch (SQLException e) {
            int end = e.getMessage().indexOf("\n");

            throw new JboException(e.getMessage());
        } finally {
            if (st != null) {
                try {
                    /** 7. Close the statement */
                    st.close();
                } catch (SQLException e) {

                }
            }
        }
    }

    public void setTncTable(RichTable tncTable) {
        this.tncTable = tncTable;
    }

    public RichTable getTncTable() {
        return tncTable;
    }

    public void tncValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // System.out.println("tnc--"+object);
        // Integer tncid = Integer.parseInt(tncidBind.getValue().toString());
        // System.out.println("tnc bind---"+tncidBind.getValue());
        /*  mmRfqAMImpl am = (mmRfqAMImpl)resolvElDC("mmRfqAMDataControl");
        Row[] r=am.getMmRfqTnc1().getFilteredRows("TncId",tncid);
        int count=0;
        RowSetIterator rsi= am.getMmRfqTnc1().createRowSetIterator(null);
        while(rsi.hasNext()){
            Row rww=rsi.next();
            if(Integer.parseInt(rww.getAttribute("TncId").toString())==Integer.parseInt(object.toString())){
                count=count+1;
            }
        }
        System.out.println(count+"length--"+r.length);
        if(r.length>0 || count>1){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Record Exists",
                                                          "Duplicate Terms"));

        } */



    }

    public void setVldtDtBind(RichInputDate vldtDtBind) {
        this.vldtDtBind = vldtDtBind;
    }

    public RichInputDate getVldtDtBind() {
        return vldtDtBind;
    }

    public void docDtVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(vldtDtBind);
    }

    public void setTncidBind(RichSelectOneChoice tncidBind) {
        this.tncidBind = tncidBind;
    }

    public RichSelectOneChoice getTncidBind() {
        return tncidBind;
    }

    public void supplierValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null && object.toString().length() > 0) {
            //Integer eoName = Integer.parseInt(object.toString());
            String eoName = object.toString();
            Integer suppid = null;
            String suppnm = "";
            String SupId = "";
            Integer count = 0;
            Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            String hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            String temp = "Y";
            mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
            ViewObjectImpl vo = am.getMmRfqEo1();
            Row currRow = vo.getCurrentRow();
            RowSetIterator itr = vo.createRowSetIterator(null);
            while (itr.hasNext()) {
                Row rw = itr.next();
                if (rw != currRow) {
                    suppid = Integer.parseInt(rw.getAttribute("EoId").toString());
                    if (suppid != null) {
                        Row[] row = am.getsupplierLOV().getFilteredRows("EoId", suppid);
                        adfLog.info("entity name is " + row.length);
                        if (row.length > 0) {
                            adfLog.info("entity name is ");
                            suppnm = row[0].getAttribute("EoNm").toString();
                        }
                    }
                    if (eoName.equalsIgnoreCase(suppnm)) {
                        count = count + 1;
                    }
                }
            }
            if (count > 0) {
                temp = "N";
            }
            /*  Row [] filteredRows=am.getsupplierLOV().getFilteredRows("EoNm", eoName);
                    System.out.println("total no. of rows found"+filteredRows.length);
                    if(filteredRows.length>0){
                        SupId=filteredRows[0].getAttribute("EoId").toString();
                    }


                    RowSetIterator rfqIterator =am.getMmRfqEo1().createRowSetIterator(null);
                              Row currentRow=am.getMmRfqEo1().getCurrentRow();
                              while(rfqIterator.hasNext()){
                                       Row r1 = rfqIterator.next();
                                       String supName="";
                                      if(r1!=currentRow){
                                     System.out.println("current row is not equals");
                                     if(r1.getAttribute("EoId")!=null){
                                          supName=r1.getAttribute("EoId").toString();

                                     }
                                    System.out.println("getting prev supplier added"+ supName+"match with current id"+SupId);
                                              if(supName.equalsIgnoreCase(SupId)) {
                                                  System.out.println("setting the temp value and duplicate record found");
                                                  temp="N";
                                              }
                                       }
                                   }  */

            itr.closeRowSetIterator();
            System.out.println("iterator value found" + temp);

            if (temp.equalsIgnoreCase("Y")) {
                Integer p_eoId = null;
                if (eoName != null) {
                    adfLog.info("going to fileter supplov " + eoName);
                    Row[] xx = am.getsupplierLOV().getFilteredRows("EoNm", eoName);
                    am.getsupplierLOV().executeQuery();
                    if (xx.length > 0) {
                        p_eoId = Integer.parseInt(xx[0].getAttribute("EoId").toString());
                    }
                    System.out.println("p_eoId------" + p_eoId);
                    String flag = "Y";
                    if (eoName != null) {
                        flag = (String) callStoredFunction(Types.VARCHAR, "APP.PKG_APP_EO.IS_SUPP_VALID(?,?,?,?,?)", new Object[] {
                                                           p_sloc_id, cldId, hoOrgId, orgId, p_eoId
                        });
                    }

                    if (flag.equalsIgnoreCase("B")) {
                        String s = ADFModelUtils.resolvRsrc("MSG.340");
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, s, null));
                    } else if (flag.equalsIgnoreCase("H")) {
                        String s = ADFModelUtils.resolvRsrc("MSG.341");
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, s, null));
                    }
                }
            } else if (temp.equalsIgnoreCase("N")) {
                String s = ADFModelUtils.resolvRsrc("MSG.2365");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, s, null));
            }
        }

    }

    public void setDeliverySchdlTab(RichShowDetailItem deliverySchdlTab) {
        this.deliverySchdlTab = deliverySchdlTab;
    }

    public RichShowDetailItem getDeliverySchdlTab() {
        return deliverySchdlTab;
    }

    public String goToAddressAction() {
        deliverySchdlTab.setDisclosed(true);
        return "gotoAdds";
    }

    public void termsDiscloseListner(DisclosureEvent disclosureEvent) {
        //String globalMode = resolvEl("#{pageFlowScope.mode}").toString();
        //if (mode.equalsIgnoreCase("E") || (mode.equalsIgnoreCase("A") || globalMode.equalsIgnoreCase("C")))
        //    tncidBind.setReadOnly(false);
    }

    /* public void docDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        Date currDt = (Date)Date.getCurrentDate();
        Date poDate = (Date)object;
        String pOrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");

        java.sql.Date s =
            (java.sql.Date)callStoredFunction(Types.DATE, "APP.PKG_APP.GET_ORG_FY_START_DATE(?,?,?)", new Object[] { currDt,
                                                                                                                     pOrgId,
                                                                                                                     "FY" });
        Date strtDate = new Date(s);
        //System.out.println(s + "Start Date of FY:" + (Date)strtDate);
        SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");

        if (strtDate != null) {
            if (poDate.compareTo(strtDate) == -1) {
                String msg2 = "Invalid RFQ Date.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setDetail(resolvElDCMsg("#{bundle['MSG.342']}").toString()+ s);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
        }
    } */

    public void docDtValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (object != null) {
            Integer p_sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
            String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
            String hoOrgId = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
            String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
            //vldtDtBind
            Date currDt = (Date) Date.getCurrentDate();
            Date formDate = (Date) object;
            Date vldtdate = null;
            if (vldtDtBind.getValue() != null && vldtDtBind.getValue().toString().length() > 0)
                vldtdate = (Date) vldtDtBind.getValue();

            if (vldtdate != null) {
                if (formDate.dateValue().toString().compareTo(vldtdate.dateValue().toString()) == 1) {
                    String s = ADFModelUtils.resolvRsrc("MSG.2366");
                    //                    The date must be on or before
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, s + vldtdate.dateValue(),
                                                                  null));
                }

            }
            mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
            ViewObjectImpl vo = am.getLovPRDocNo();
            /*   String pOrgId = (String)resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
         String pSloc = (String)resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}");
         Object oDt= callStoredFunction(Types.VARCHAR, "APP.PKG_APP.GET_FY_STAT_FOR_TXN(?,?,?)", new Object[] { pSloc,pOrgId,formDate });
         if(oDt==null){
             String msg2 = "Financial Year is not defined.";
             FacesMessage message2 = new FacesMessage(msg2);
             message2.setSeverity(FacesMessage.SEVERITY_ERROR);
             throw new ValidatorException(message2);
         }
         else if("Y".equalsIgnoreCase(oDt.toString())){
       //  java.sql.Date s =(java.sql.Date)oDt;
           //  Date strtDate = new Date(s);
             //System.out.println(s + "Start Date of FY:" + (Date)strtDate);
             SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");

          //    if (strtDate != null) {
           //      if (poDate.compareTo(strtDate) == -1) {
                     String msg2 = resolvEl("#{bundle['MSG.342']}");
                     FacesMessage message2 = new FacesMessage(msg2);
                     message2.setDetail(msg2);
                     message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                     throw new ValidatorException(message2);
                 }
       */
            Timestamp tm = new Timestamp(formDate);
            boolean flag = isFYOpenForCurrentDate(tm);

            if (!flag) {
                //                String msg2 = resolvEl("#{bundle['MSG.342']}");
                String msg2 = ADFModelUtils.resolvRsrc("MSG.342");
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setDetail(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message2);
            }
            Integer srcType = 461;
            if (srcTypeBind.getValue() != null) {
                srcType = Integer.parseInt(srcTypeBind.getValue().toString());
            }
            if (srcType == 462 && srcDocNoBind.getValue() != null) {
                String srcDocNo = srcDocNoBind.getValue().toString();
                RowQualifier rq = new RowQualifier(vo);
                rq.setWhereClause("SlocId = " + p_sloc_id + " and CldId = '" + cldId + "' and OrgId = '" + orgId +
                                  "' and PrNo ='" + srcDocNo + "' ");

                Row[] prRow = vo.getFilteredRows(rq);
                System.out.println(prRow.length + "  queryy    " + rq.getExprStr());
                Date prDt = null;
                if (prRow.length > 0) {
                    prDt = new Date(prRow[0].getAttribute("PrDt").toString());
                    System.out.println(" pr date  " + prDt);

                }
                if (prDt != null) {
                    if (formDate.compareTo(prDt.dateValue()) == 1 || formDate.compareTo(prDt.dateValue()) == 0) {
                        System.out.println("validate date --------");

                    } else {
                        //                        "RFQ Date greater than or equals to "
                        String s = ADFModelUtils.resolvRsrc("MSG.2367");
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, s + prDt.dateValue(),
                                                                      null));
                    }

                }
            }

        }
    }


    public void itmDiscloseListner(DisclosureEvent disclosureEvent) {
        String globalMode = resolvEl("#{pageFlowScope.mode}").toString();
        //if (mode.equalsIgnoreCase("E") || (mode.equalsIgnoreCase("A") || globalMode.equalsIgnoreCase("C")))
        // tncidBind.setReadOnly(true);
    }

    public void deliveryDiscloseListener(DisclosureEvent disclosureEvent) {
        String globalMode = resolvEl("#{pageFlowScope.mode}").toString();
        //  if (mode.equalsIgnoreCase("E") || (mode.equalsIgnoreCase("A") || globalMode.equalsIgnoreCase("C")))
        //   tncidBind.setReadOnly(true);
    }

    public void setRfqQtyBind(RichInputText rfqQtyBind) {
        this.rfqQtyBind = rfqQtyBind;
    }

    public RichInputText getRfqQtyBind() {
        return rfqQtyBind;
    }

    public void itemNmValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
        String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        String inputItm = (String) object;
        System.out.println("value of the object is===" + inputItm);
        String itmDesc = null;
        ViewObjectImpl v = am.getMmRfqItm1();
        int totalCount = v.getRowCount(); //get ALL rows
        int rangeSize = v.getRangeSize(); //all in range
        v.setRangeSize(totalCount);
        Row[] rArray = v.getAllRowsInRange();
        Row cRow = v.getCurrentRow();
        int count = 0;
        String itemIdCur = "";
        for (Row r : rArray) {
            if (!r.equals(cRow)) {
                try {
                    itemIdCur = r.getAttribute("ItmId").toString();
                } catch (NullPointerException npe) {
                    itemIdCur = "";
                }
                if (itemIdCur != null) {
                    ViewObjectImpl tunevo = am.getMMItmTune();
                    tunevo.setNamedWhereClauseParam("bindItmId", itemIdCur);
                    tunevo.setNamedWhereClauseParam("bindCldId", cld_id);
                    tunevo.setNamedWhereClauseParam("bindSlocId", sloc_id);
                    tunevo.setNamedWhereClauseParam("bindHoOrgId", hoOrg_id);
                    tunevo.executeQuery();
                    Row[] xx = tunevo.getFilteredRows("ItmId", itemIdCur);
                    adfLog.info("new filter rows found " + xx.length);

                    if (xx.length > 0) {

                        itmDesc = xx[0].getAttribute("ItmDesc").toString();
                        adfLog.info("new filter rows found " + itmDesc);

                    }
                    if (inputItm.equalsIgnoreCase(itmDesc)) {
                        count = count + 1;
                    }
                }

            }
        }
        v.setRangeSize(rangeSize); //set to original range
        if (count > 0) {
            String msg2 = ADFModelUtils.resolvRsrc("MSG.46");
            //            String msg2 = resolvElDCMsg("#{bundle['MSG.46']}").toString();
            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message2);
        }

    }

    public void setDesiredPriceBind(RichInputText desiredPriceBind) {
        this.desiredPriceBind = desiredPriceBind;
    }

    public RichInputText getDesiredPriceBind() {
        return desiredPriceBind;
    }

    public String saveAndForwardAction() {
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer pending = null;

        mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
        String id = (String) am.getMmRfq1().getCurrentRow().getAttribute("RfqId");
        if (checkFlag.equalsIgnoreCase("N")) {
            String s = ADFModelUtils.resolvRsrc("MSG.324");
            FacesMessage errMsg = new FacesMessage(s);
            errMsg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, errMsg);
        } else {
            if (am.getMmRfqEo1().getEstimatedRowCount() == 0) {
                String s = ADFModelUtils.resolvRsrc("MSG.325");
                FacesMessage errMsg = new FacesMessage(s);
                errMsg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, errMsg);
            }
            String globalMode = resolvEl("#{pageFlowScope.mode}").toString();
            ViewObject v1 = am.getMmRfq1();

            if (mode.equalsIgnoreCase("A") || globalMode.equalsIgnoreCase("C")) {
                Integer slocid = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
                String orgid = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
                String cldid = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");

                Row row = v1.getCurrentRow();
                if (row.getAttribute("RfqId") == null) {
                    Integer fyid = 0;
                    OperationBinding fyIdOb = getBindings().getOperationBinding("getFYid");
                    fyIdOb.getParamsMap().put("CldId", cldid);
                    fyIdOb.getParamsMap().put("OrgId", orgid);
                    fyIdOb.getParamsMap().put("geDate", (Date) docDtBind.getValue());
                    fyIdOb.execute();
                    if (fyIdOb.getResult() != null) {
                        fyid = Integer.parseInt(fyIdOb.getResult().toString());

                    }
                    //   System.out.println("bean fy id "+fyid);
                    id = (String) (callStoredFunction(VARCHAR, "MM.FN_MM_GEN_ID (?,?,?,?,?,?)", new Object[] {
                                                      slocid, cldid, orgid, "MM$RFQ", null, fyid
                    }));
                    row.setAttribute("RfqId", id);
                }

                Integer srcType = Integer.parseInt(srcTypeBind.getValue().toString());
                if (srcType == 462 && srcDocNoBind.getValue() != null &&
                    v1.getCurrentRow().getAttribute("DocIdSrc") != null) {

                    OperationBinding updatePrOb = getBindings().getOperationBinding("updatePrStatus");
                    updatePrOb.getParamsMap().put("CldId", cldid);
                    updatePrOb.getParamsMap().put("OrgId", orgid);
                    updatePrOb.getParamsMap().put("SlocId", sloc_id);
                    updatePrOb.getParamsMap().put("SrcDocId", v1.getCurrentRow().getAttribute("DocIdSrc"));
                    updatePrOb.getParamsMap().put("UserId", usr_id);
                    updatePrOb.execute();
                    String flag = "N";
                    if (updatePrOb.getResult() != null) {
                        flag = updatePrOb.getResult().toString();

                    }
                    System.out.println("bean pr update value " + flag);
                    if ("Y".equalsIgnoreCase(flag)) {
                        System.out.println("Succesfull PR status update.   ");
                    } else {
                        System.out.println("some error occur PR status update.   ");
                    }

                }
            }

            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();


            //  System.out.println("sloc_id    "+sloc_id+"cld_id   "+cld_id+"org_id  "+pOrgId);
            OperationBinding chkUsr = getBindings().getOperationBinding("pendingRfqCheck");
            chkUsr.getParamsMap().put("SlocId", sloc_id);
            chkUsr.getParamsMap().put("CldId", cld_id);
            chkUsr.getParamsMap().put("OrgId", pOrgId);
            chkUsr.getParamsMap().put("RfqDocNo", 18502);
            chkUsr.execute();

            if (chkUsr.getResult() != null) {
                pending = Integer.parseInt(chkUsr.getResult().toString());
            }

            if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(usr_id) == 0) {
                ViewObject rfqVo = am.getMmRfq1();
                String rfqDocId = rfqVo.getCurrentRow().getAttribute("DocId").toString();

                String action = "I";
                String remark = "A";

                OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
                WfnoOp.getParamsMap().put("sloc_id", sloc_id);
                WfnoOp.getParamsMap().put("cld_id", cld_id);
                WfnoOp.getParamsMap().put("org_id", pOrgId);
                WfnoOp.getParamsMap().put("doc_no", 18502);
                WfnoOp.execute();

                String WfNum = null;
                Integer level = 0;
                Integer res = -1;
                if (WfnoOp.getResult() != null) {
                    WfNum = WfnoOp.getResult().toString();
                }
                adfLog.info("get wfno . is " + WfNum);
                if (WfNum == null || "0".equalsIgnoreCase(WfNum)) {
                    //                    Workflow not Defined for this Document
                    String s = ADFModelUtils.resolvRsrc("MSG.1486");
                    FacesMessage message = new FacesMessage(s);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);
                    return null;
                } else {
                    adfLog.info("get wfno . is inside else " + WfNum);
                    OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                    usrLevelOp.getParamsMap().put("SlocId", sloc_id);
                    usrLevelOp.getParamsMap().put("CldId", cld_id);
                    usrLevelOp.getParamsMap().put("OrgId", pOrgId);
                    usrLevelOp.getParamsMap().put("usr_id", usr_id);
                    usrLevelOp.getParamsMap().put("WfNum", WfNum);
                    usrLevelOp.getParamsMap().put("RfqDocId", 18502);
                    usrLevelOp.execute();
                    level = -1;
                    if (usrLevelOp.getResult() != null) {
                        level = Integer.parseInt(usrLevelOp.getResult().toString());
                    }
                    if (level == -1) {
                        //                        Something went wrong while getting Workflow Level of this user.Contact to ESS.
                        String s = ADFModelUtils.resolvRsrc("MSG.2011");
                        FacesMessage message = new FacesMessage(s);
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, message);

                        return null;
                    } else {
                        OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                        insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                        insTxnOp.getParamsMap().put("cld_id", cld_id);
                        insTxnOp.getParamsMap().put("pOrgId", pOrgId);
                        insTxnOp.getParamsMap().put("RFQ_DOC_NO", 18502);
                        insTxnOp.getParamsMap().put("WfNum", WfNum);
                        insTxnOp.getParamsMap().put("rfqDocId", rfqDocId);
                        insTxnOp.getParamsMap().put("usr_idFrm", usr_id);
                        insTxnOp.getParamsMap().put("usr_idTo", usr_id);
                        insTxnOp.getParamsMap().put("levelFrm", level);
                        insTxnOp.getParamsMap().put("levelTo", level);
                        insTxnOp.getParamsMap().put("action", action);
                        insTxnOp.getParamsMap().put("remark", remark);
                        insTxnOp.getParamsMap().put("amount", 0);
                        insTxnOp.execute();

                        if (insTxnOp.getResult() != null) {
                            res = Integer.parseInt(insTxnOp.getResult().toString());
                        }
                        am.getDBTransaction().commit();
                        return "goToWf";
                    }
                }
            } else {
                //pending != usr_id
                //                This RFQ is pending at other user for approval, You can not forward this
                String msg2 = ADFModelUtils.resolvRsrc("MSG.583");
                //                String msg2 = "This RFQ is pending at other user for approval, You can not forward this.";
                FacesMessage message2 = new FacesMessage(msg2);
                message2.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message2);

            }
        }
        return null;
    }

    public void sendMailToSuppliers(ActionEvent actionEvent) {
        // Add event code here...
        // OperationBinding sendOp = getBindings().getOperationBinding("callReport");
        Integer replyvia = new Integer(0);
        if (replyViaBind.getValue() != null && replyViaBind.getValue().toString().length() > 0) {
            replyvia = Integer.parseInt(replyViaBind.getValue().toString());
        }
        if (replyvia.compareTo(150) == 0) {
            OperationBinding sendOp = getBindings().getOperationBinding("sendAction");
            sendOp.execute();
        }

        if (rfqStatusBind.getValue() != null) {
            Integer rfqStatus = Integer.parseInt(rfqStatusBind.getValue().toString());
            if (rfqStatus == 240) {
                OperationBinding update = getBindings().getOperationBinding("updateRFQStatus");
                update.execute();
            }
        }

        //        String msg2 = "RFQ send to Listed Supplier.";
        String msg2 = ADFModelUtils.resolvRsrc("MSG.2368");
        FacesMessage message2 = new FacesMessage(msg2);
        message2.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message2);
    }

    public void setSendMail(boolean sendMail) {
        this.sendMail = sendMail;
    }

    public boolean isSendMail() {
        return sendMail;
    }

    public void checkStatus() {
        OperationBinding statusOp = getBindings().getOperationBinding("isRFQApproved");
        statusOp.execute();
        Boolean b = (Boolean) statusOp.getResult();

        // System.out.println(b);
        sendMail = b;

    }

    public void setSaveButtonBind(RichCommandImageLink saveButtonBind) {
        this.saveButtonBind = saveButtonBind;
    }

    public RichCommandImageLink getSaveButtonBind() {
        return saveButtonBind;
    }

    public void gerarateRFQReport(ActionEvent actionEvent) {
        OperationBinding genRprtOp = getBindings().getOperationBinding("callRfqReport");
        genRprtOp.execute();

        //        String msg2 = "Report Generated Successfully.";
        String msg2 = ADFModelUtils.resolvRsrc("MSG.2369");
        FacesMessage message2 = new FacesMessage(msg2);
        message2.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message2);
    }

    public String getUpdateStatusonOReport() {
        System.out.println("method in bean is called");
        OperationBinding update = getBindings().getOperationBinding("updateRFQStatus");
        update.execute();
        return "update";
    }

    public void setRfqStatusBind(RichSelectOneChoice rfqStatusBind) {
        this.rfqStatusBind = rfqStatusBind;
    }

    public RichSelectOneChoice getRfqStatusBind() {
        return rfqStatusBind;
    }

    public String getDisableSendLink() {
        String retVal = "N";
        if (rfqStatusBind.getValue() != null && replyViaBind.getValue() != null) {
            Integer rfqStatus = Integer.parseInt(rfqStatusBind.getValue().toString());
            // Integer replyVia = Integer.parseInt(replyViaBind.getValue().toString());
            //  System.out.println(rfqStatus+"-------rfqStatus---------"+replyVia);
            // if (replyVia == 150) {
            if (rfqStatus == 240 || rfqStatus == 152) { //
                retVal = "Y";
            } else {
                retVal = "N";
            }
            // }
        }
        return retVal;

    }

    public String getDisableReportLink() {
        String retVal = "N";
        if (rfqStatusBind.getValue() != null && replyViaBind.getValue() != null) {
            Integer rfqStatus = Integer.parseInt(rfqStatusBind.getValue().toString());
            Integer replyVia = Integer.parseInt(replyViaBind.getValue().toString());
            //System.out.println(rfqStatus+"-------rfqStatus1---------"+replyVia);
            if (replyVia == 148) {
                if (rfqStatus == 240 || rfqStatus == 152) {
                    retVal = "Y";
                } else {
                    retVal = "N";
                }
            }
        }
        return retVal;

    }

    public String getDisReportLink() {
        String retVal = "N";
        if (rfqStatusBind.getValue() != null) {
            Integer rfqStatus = Integer.parseInt(rfqStatusBind.getValue().toString());
            if (rfqStatus == 240 || rfqStatus == 152) {
                retVal = "Y";
            } else {
                retVal = "N";
            }
        }
        return retVal;
    }

    public void setReplyViaBind(RichSelectOneChoice replyViaBind) {
        this.replyViaBind = replyViaBind;
    }

    public RichSelectOneChoice getReplyViaBind() {
        return replyViaBind;
    }


    public boolean isFYOpenForCurrentDate(Timestamp dt) {
        StringBuffer ck = new StringBuffer("A");
        String cldId = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String orgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer fyId = 0;
        try {
            //FUNCTION GET_ORG_FY_ID(P_CLDID VARCHAR2, P_ORG_ID VARCHAR2, P_TXN_DT DATE)
            fyId = (Integer) callStoredFunction(Types.INTEGER, "APP.GET_ORG_FY_ID(?,?,?)", new Object[] {
                                                cldId.toString(), orgId.toString(), dt
            });
        } catch (Exception e) {
            //            There have been an error in calling function 'APP.GET_ORG_FY_ID'
            String s = ADFModelUtils.resolvRsrc("MSG.2370");
            FacesMessage message = new FacesMessage(s);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);
            e.printStackTrace();
        }

        if (fyId == -1) {
            return false;
        } else {

            try {
                //FUNCTION CHK_ORG_FY_STAT(P_ORG_ID VARCHAR2, P_FY_ID NUMBER, P_FY_TYP VARCHAR2 DEFAULT 'FY',P_BC_FLG VARCHAR2 DEFAULT 'Y')
                ck = new StringBuffer(callStoredFunction(Types.VARCHAR, "APP.PKG_APP.CHK_ORG_FY_STAT(?,?,?,?)", new Object[] {
                                                         orgId.toString(), fyId, "FY", "Y"
                    }).toString());

            } catch (Exception e) {
                //                There have been an error in calling function 'APP.CHK_ORG_FY_STAT' !
                String s = ADFModelUtils.resolvRsrc("MSG.2371");
                FacesMessage message = new FacesMessage(s);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message);
                e.printStackTrace();
            }

        }
        //System.out.println("FY IS "+fyId+" FY OPEN/CLOSE CHECK :"+ck);
        if (ck.toString().equals("N")) {
            return true;
        } else {
            return false;
        }

    }


    public boolean getForwardDisLink() {
        if (rfqStatusBind.getValue() != null) {
            Integer rfqStatus = Integer.parseInt(rfqStatusBind.getValue().toString());
            if (rfqStatus == 152) {
                return true;
            }
        }
        return false;
    }

    public void srcDocNoVCL(ValueChangeEvent valueChangeEvent) {
        System.out.println("In VCL");
        if (valueChangeEvent.getNewValue() != null) {
            String prNo = valueChangeEvent.getNewValue().toString();
            OperationBinding addPrItm = getBindings().getOperationBinding("addItemForPR");
            addPrItm.getParamsMap().put("value", prNo);
            addPrItm.execute();
            this.showSupp = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(itmTableBinding);
        }
    }

    public void setTransProspectEo(RichInputText transProspectEo) {
        this.transProspectEo = transProspectEo;
    }

    public RichInputText getTransProspectEo() {
        return transProspectEo;
    }

    public void validDateValudator(FacesContext facesContext, UIComponent uIComponent, Object object) {

        if (docDtBind.getValue() != null && object != null) {
            System.out.println("bharat222");
            Date docDt = (Date) docDtBind.getValue();
            Date valDate = (Date) object;
            /** code to check current doc dt and validate  validator*/
            Date currDt = docDt;
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(docDt.dateValue());
            cal.add(Calendar.DATE, 180);
            java.util.Date dt = cal.getTime();
            String dtStr = dateFormat.format(dt);
            oracle.jbo.domain.Date daTime = null;
            adfLog.info("doc date " + docDt);
            adfLog.info("doc str date " + dtStr);
            try {
                java.util.Date date2 = dateFormat.parse(dtStr);
                java.sql.Date sqldate = new java.sql.Date(date2.getTime());
                daTime = new oracle.jbo.domain.Date(sqldate);
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
            adfLog.info("new  date " + daTime);

            /*  try {
                dt = new Date(docDt.longValue() + TimeUnit.MILLISECONDS.convert(180, TimeUnit.DAYS) );
            } catch (SQLException e) {
                e.printStackTrace();
            } */
            System.out.println("docDt    " + docDt + "     new date  " + dt + "  daTime " + daTime);
            adfLog.info("docDt    " + docDt + "     new date  " + dt + "  daTime " + daTime);
            System.out.println("val date is " + valDate.dateValue() + " compare value " + daTime.dateValue() +
                               " daTime        " + "" +
                               valDate.dateValue().toString().compareTo(daTime.dateValue().toString()));
            System.out.println("current date is " + currDt.dateValue() + " validate date is " + valDate +
                               " comparison is " +
                               currDt.dateValue().toString().compareTo(valDate.dateValue().toString()));

            if (valDate.dateValue().toString().compareTo(daTime.dateValue().toString()) == 1) {
                //                validatity Date can not more than 180 days of RFQ date
                String s = ADFModelUtils.resolvRsrc("MSG.2372");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, s, null));
            } else if (currDt.dateValue().toString().compareTo(valDate.dateValue().toString()) >= 1) {
                //                The date must be on or after
                String s = ADFModelUtils.resolvRsrc("MSG.585");
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, s + currDt.dateValue(),
                                                              null));
            }


        }
    }

    public void setSrcDocNoBind(RichInputListOfValues srcDocNoBind) {
        this.srcDocNoBind = srcDocNoBind;
    }

    public RichInputListOfValues getSrcDocNoBind() {
        return srcDocNoBind;
    }

    public void setSrcTypeBind(RichSelectOneChoice srcTypeBind) {
        this.srcTypeBind = srcTypeBind;
    }

    public RichSelectOneChoice getSrcTypeBind() {
        return srcTypeBind;
    }

    public void srcTypeVCE(ValueChangeEvent valueChangeEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(srcDocNoBind);
    }


    public void setCreateMode(String createMode) {
        this.createMode = createMode;
    }

    public String getCreateMode() {
        return createMode;
    }

    public void setEoTypeBinding(RichSelectOneRadio eoTypeBinding) {
        this.eoTypeBinding = eoTypeBinding;
    }

    public RichSelectOneRadio getEoTypeBinding() {
        return eoTypeBinding;
    }

    public void setPopSupBinding(RichInputListOfValues popSupBinding) {
        this.popSupBinding = popSupBinding;
    }

    public RichInputListOfValues getPopSupBinding() {
        return popSupBinding;
    }

    public void setEoIdBind(RichInputText eoIdBind) {
        this.eoIdBind = eoIdBind;
    }

    public RichInputText getEoIdBind() {
        return eoIdBind;
    }

    public void setPanelcollbind(RichPanelCollection panelcollbind) {
        this.panelcollbind = panelcollbind;
    }

    public RichPanelCollection getPanelcollbind() {
        return panelcollbind;
    }

    public String cancelButtonBack() {
        /*   BindingContainer bindings = getBindings();
       OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
       */
        setCreateMode("Y");
        this.form_readonly = true;
        this.createRfqbutton = false;
        this.saveButton = true;
        this.addItembutton = true;
        this.mode = "V";
        this.showSupp = true;
        suppNmbest = "";
        Datebest = "";
        suppNmlatest = "";
        Datelatest = "";
        bestPrice = new BigDecimal(0);
        latestPrice = new BigDecimal(0);
        itmFlag = "V";
        deliverySchdlTab.setDisclosed(false);
        this.Itemedit = true;
        this.tncReadonly = true;

        // tncidBind.setReadOnly(true);
        return "back";

    }

    public DnDAction dropDialogListner(DropEvent dropEvent) {
        mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
        ViewObjectImpl v1 = am.getMmRfqTnc1();
        ViewObject v2 = am.getMmRfq1();
        Row curr = v2.getCurrentRow();
        Integer tncid = (Integer) am.getTncLOV().getCurrentRow().getAttribute("TncId");
        //getAm().getLovTncId().executeQuery();
        RowQualifier rq = new RowQualifier(v1);
        rq.setWhereClause("CldId='" + curr.getAttribute("CldId") + "' and SlocId=" + curr.getAttribute("SlocId") +
                          " and OrgId='" + curr.getAttribute("OrgId") + "' and DocId='" + curr.getAttribute("DocId") +
                          "' and TncId=" + tncid + "");
        Row fr[] = v1.getFilteredRows(rq);
        System.out.println("row found length" + fr.length);
        if (fr.length > 0) {
            //            Duplicate Record Exist
            String s = ADFModelUtils.resolvRsrc("MSG.375");
            FacesMessage message = new FacesMessage(s);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message);
        } else {
            ViewObjectImpl mmrfq = am.getMmRfqTnc1();
            Integer p_user_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}"));
            Row r = mmrfq.createRow();
            System.out.println("TncId=" + tncid);
            r.setAttribute("CldId", curr.getAttribute("CldId"));
            r.setAttribute("SlocId", curr.getAttribute("SlocId"));
            r.setAttribute("OrgId", curr.getAttribute("OrgId"));
            r.setAttribute("DocId", curr.getAttribute("DocId"));
            r.setAttribute("TncId", tncid);
            r.setAttribute("UsrIdCreate", p_user_id);
            System.out.println("Inserting row key=" + r.getKey());
            mmrfq.insertRow(r);
            mmrfq.executeQuery();
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(tncTab);
        // Add event code here...
        return DnDAction.NONE;
    }

    public void setTncTab(RichShowDetailItem tncTab) {
        this.tncTab = tncTab;
    }

    public RichShowDetailItem getTncTab() {
        return tncTab;
    }

    public List getSuggestions(String input) {

        // create a new list to hold matching items
        List<SelectItem> items = new ArrayList<SelectItem>();
        OperationBinding binding = getBindings().getOperationBinding("getSuggestedItemDesc");
        binding.getParamsMap().put("itmStr", input);
        binding.execute();
        Object object = binding.getResult();
        System.out.println("Item Query Executed");
        if (object != null) {
            for (String item : (ArrayList<String>) object) {
                items.add(new SelectItem(item));
            }
        }
        return items;
    }

    public void setScoreGroup(RichPanelGroupLayout scoreGroup) {
        this.scoreGroup = scoreGroup;
    }

    public RichPanelGroupLayout getScoreGroup() {
        return scoreGroup;
    }

    public void setEoIdInVis(RichInputText eoIdInVis) {
        this.eoIdInVis = eoIdInVis;
    }

    public RichInputText getEoIdInVis() {
        return eoIdInVis;
    }

    public String cancelToWfAction() {
        // Add event code here
        mmRfqAMImpl am = (mmRfqAMImpl) resolvElDC("mmRfqAMDataControl");
        Integer usr_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_USR}").toString());
        String hoOrg_id = resolvEl("#{pageFlowScope.GLBL_HO_ORG_ID}");
        String cld_id = resolvEl("#{pageFlowScope.GLBL_APP_CLD_ID}");
        Integer sloc_id = Integer.parseInt(resolvEl("#{pageFlowScope.GLBL_APP_SERV_LOC}"));
        String pOrgId = resolvEl("#{pageFlowScope.GLBL_APP_USR_ORG}");
        Integer pending = null;

        OperationBinding chkUsr = getBindings().getOperationBinding("pendingRfqCheck");
        chkUsr.getParamsMap().put("SlocId", sloc_id);
        chkUsr.getParamsMap().put("CldId", cld_id);
        chkUsr.getParamsMap().put("OrgId", pOrgId);
        chkUsr.getParamsMap().put("RfqDocNo", 18502);
        chkUsr.execute();

        if (chkUsr.getResult() != null) {
            pending = Integer.parseInt(chkUsr.getResult().toString());
        }

        if (pending.compareTo(new Integer(-1)) == 0 || pending.compareTo(usr_id) == 0) {
            ViewObject rfqVo = am.getMmRfq1();
            String rfqDocId = rfqVo.getCurrentRow().getAttribute("DocId").toString();

            String action = "I";
            String remark = "A";

            OperationBinding WfnoOp = getBindings().getOperationBinding("getWfNo");
            WfnoOp.getParamsMap().put("sloc_id", sloc_id);
            WfnoOp.getParamsMap().put("cld_id", cld_id);
            WfnoOp.getParamsMap().put("org_id", pOrgId);
            WfnoOp.getParamsMap().put("doc_no", 18502);
            WfnoOp.execute();

            String WfNum = null;
            Integer level = 0;
            Integer res = -1;
            if (WfnoOp.getResult() != null) {
                WfNum = WfnoOp.getResult().toString();
            }
            adfLog.info("get wfno . is " + WfNum);
            if (WfNum == null || "0".equalsIgnoreCase(WfNum)) {
                //                Workflow not Defined for this Document
                String s = ADFModelUtils.resolvRsrc("MSG.1486");
                FacesMessage message = new FacesMessage(s);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, message);
                return null;
            } else {
                adfLog.info("get wfno . is inside else " + WfNum);
                OperationBinding usrLevelOp = getBindings().getOperationBinding("getUsrLvl");
                usrLevelOp.getParamsMap().put("SlocId", sloc_id);
                usrLevelOp.getParamsMap().put("CldId", cld_id);
                usrLevelOp.getParamsMap().put("OrgId", pOrgId);
                usrLevelOp.getParamsMap().put("usr_id", usr_id);
                usrLevelOp.getParamsMap().put("WfNum", WfNum);
                usrLevelOp.getParamsMap().put("RfqDocId", 18502);
                usrLevelOp.execute();
                level = -1;
                if (usrLevelOp.getResult() != null) {
                    level = Integer.parseInt(usrLevelOp.getResult().toString());
                }
                if (level == -1) {
                    //                    Something went wrong while getting Workflow Level of this user.Contact to ESS.
                    String s = ADFModelUtils.resolvRsrc("MSG.2011");
                    FacesMessage message = new FacesMessage(s);
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, message);

                    return null;
                } else {
                    OperationBinding insTxnOp = getBindings().getOperationBinding("insIntoTxn");
                    insTxnOp.getParamsMap().put("sloc_id", sloc_id);
                    insTxnOp.getParamsMap().put("cld_id", cld_id);
                    insTxnOp.getParamsMap().put("pOrgId", pOrgId);
                    insTxnOp.getParamsMap().put("RFQ_DOC_NO", 18502);
                    insTxnOp.getParamsMap().put("WfNum", WfNum);
                    insTxnOp.getParamsMap().put("rfqDocId", rfqDocId);
                    insTxnOp.getParamsMap().put("usr_idFrm", usr_id);
                    insTxnOp.getParamsMap().put("usr_idTo", usr_id);
                    insTxnOp.getParamsMap().put("levelFrm", level);
                    insTxnOp.getParamsMap().put("levelTo", level);
                    insTxnOp.getParamsMap().put("action", action);
                    insTxnOp.getParamsMap().put("remark", remark);
                    insTxnOp.getParamsMap().put("amount", 0);
                    insTxnOp.execute();

                    if (insTxnOp.getResult() != null) {
                        res = Integer.parseInt(insTxnOp.getResult().toString());
                    }
                    am.getDBTransaction().commit();
                    cancelPopBind.hide();
                    return "goToCancelWF";
                }
            }
        } else {
            //pending != usr_id
            //            This RFQ is pending at other user for approval, You can not forward this.
            String msg2 = ADFModelUtils.resolvRsrc("MSG.583");

            FacesMessage message2 = new FacesMessage(msg2);
            message2.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, message2);
        }
        return null;
    }

    public void setCancelPopBind(RichPopup cancelPopBind) {
        this.cancelPopBind = cancelPopBind;
    }

    public RichPopup getCancelPopBind() {
        return cancelPopBind;
    }

    public void cancelPopAction(ActionEvent actionEvent) {
        // Add event code here...
        cancelPopBind.hide();
    }

    public void setFlxTableBind(RichTable flxTableBind) {
        this.flxTableBind = flxTableBind;
    }

    public RichTable getFlxTableBind() {
        return flxTableBind;
    }
}
